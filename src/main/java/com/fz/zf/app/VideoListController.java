package com.fz.zf.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ex.framework.data.Record;
import com.ex.framework.data.RecordBody;
import com.ex.framework.web.ExPage;
import com.ex.framework.web.ExPageResult;
import com.ex.framework.web.TableResult;
import com.fz.zf.model.app.DianZan;
import com.fz.zf.model.app.VideoList;
import com.fz.zf.model.common.Constast;
import com.fz.zf.service.app.DianZanService;
import com.fz.zf.service.app.VideoListService;
import com.fz.zf.util.ApiResult;
import com.fz.zf.util.UserInfoUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/video")
@Slf4j
public class VideoListController extends BaseController {

    @Autowired
    VideoListService videoListService;
    @Autowired
    DianZanService dianZanService;

    @PostMapping("list")
    @ResponseBody
    public Object list(@RecordBody Record record) {

        try {
            String author = record.getString("author");
            Integer category_id = record.tryGetInteger("category_id");

            QueryWrapper<VideoList> wrapper = new QueryWrapper<>();
            wrapper.eq(category_id != null, "category_id", category_id);
            wrapper.likeRight(StringUtils.isNotEmpty(author), "author", author);

            ExPage page = parseExPage(record);
            page.setSort("vid");
            ExPageResult<VideoList> pageResult = videoListService.list(page, wrapper);
            process(pageResult);

            return TableResult.success(pageResult);
        } catch (Exception ex) {
            return TableResult.error(ex.getLocalizedMessage());
        }

    }

    @PostMapping("saveList")
    @ResponseBody
    public Object saveList(@RecordBody Record record) {

        try {
            QueryWrapper<VideoList> wrapper = new QueryWrapper<>();

            ExPage page = parseExPage(record);
            page.setSort("vid");
            ExPageResult pageResult = videoListService.list(page, wrapper);
            return TableResult.success(pageResult);
        } catch (Exception ex) {
            return TableResult.error(ex.getLocalizedMessage());
        }

    }

    private void process(ExPageResult<VideoList> pageResult) {
        List<VideoList> rows = pageResult.getRows();
        for (VideoList model : rows) {
            boolean dian = dianZanService.exists(new QueryWrapper<DianZan>().eq("vid", model.getVid())
                                                                            .eq("type", Constast.DIAN_ZAN)
                                                                            .eq("uid", UserInfoUtils.getCurrentUserId()));
            model.setDian(dian);
            boolean shou = dianZanService.exists(new QueryWrapper<DianZan>().eq("vid", model.getVid())
                                                                            .eq("type", Constast.SHOU_CANG)
                                                                            .eq("uid", UserInfoUtils.getCurrentUserId()));
            model.setShou(shou);
        }
    }

    /**
     * 点赞、收藏
     *
     * @param record
     * @return
     */
    @PostMapping("updateByVid")
    @ResponseBody
    public Object updateByVid(@RecordBody Record record) {
        return videoListService.updateByVid(record);
    }

}
