package com.fz.zf.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ex.framework.data.Record;
import com.ex.framework.data.RecordBody;
import com.ex.framework.web.ExPage;
import com.ex.framework.web.ExPageResult;
import com.ex.framework.web.TableResult;
import com.fz.zf.model.app.VideoCategory;
import com.fz.zf.service.app.VideoCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/video/category")
public class VideoCategoryController extends BaseController {

    protected static Logger logger = LoggerFactory.getLogger(VideoCategoryController.class);

    @Autowired
    VideoCategoryService videoCategoryService;

    @RequestMapping("list")
    @ResponseBody
    public Object list(@RecordBody Record record) {
        try {
            ExPage page = parseExPage(record);
            page.setSort("category_id");
            ExPageResult<VideoCategory> pageResult = videoCategoryService.list(page, new QueryWrapper<>());

            return TableResult.success(pageResult);
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage(), ex);

            return TableResult.error(ex.getLocalizedMessage());
        }
    }

}
