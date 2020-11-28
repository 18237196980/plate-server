package com.fz.zf.service.app;


import com.ex.framework.base.BaseCRUDService;
import com.ex.framework.data.IDUtils;
import com.ex.framework.data.Record;
import com.fz.zf.mapper.VideoListMapper;
import com.fz.zf.model.app.DianZan;
import com.fz.zf.model.app.VideoList;
import com.fz.zf.model.common.Constast;
import com.fz.zf.util.ApiResult;
import com.fz.zf.util.UserInfoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * VideoListService
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class VideoListService extends BaseCRUDService<VideoListMapper, VideoList> {

    @Autowired
    DianZanService dianZanService;

    @Transactional
    public ApiResult updateByVid(Record record) {
        Integer vid = record.tryGetInteger("vid");
        Integer type = record.tryGetInteger("type"); // type=1 点赞 type=2 收藏
        boolean flag = record.tryGetBoolean("flag"); // flag=true 点赞或收藏 flag=false 取消点赞或取消收藏

        if (vid == null) {
            return ApiResult.error("id不能为空");
        }
        if (type == null) {
            return ApiResult.error("type不能为空");
        }

        VideoList video = get(vid);

        if (type == 1) {
            if (flag) {
                // 用户是否对该视频 已点赞或已收藏 后操作默认失败
                boolean check = dianZanService.checkDianZan(vid,UserInfoUtils.getCurrentUserId(),Constast.DIAN_ZAN);
                if (check) {
                    log.info("用户已经点过赞-----------------------------");
                    return ApiResult.error("已经点过赞啦");
                }

                // 该视频点赞数加一
                video.setLike_num(video.getLike_num() + 1);
                boolean b = updateById(video);
                if (b) {
                    // 增加一条点赞记录
                    DianZan dianZan = new DianZan();
                    dianZan.setId(IDUtils.getSequenceStr());
                    dianZan.setVid(vid + "");
                    dianZan.setCreate_time(LocalDateTime.now());
                    dianZan.setUid(UserInfoUtils.getCurrentUserId());
                    dianZan.setType(Constast.DIAN_ZAN);

                    dianZanService.addOne(dianZan);
                    return ApiResult.success();
                }
            } else {
                // 如果取消点赞，则删除点赞记录
                dianZanService.delOne(vid,UserInfoUtils.getCurrentUserId(),Constast.DIAN_ZAN);

                // 点赞数量减1
                video.setLike_num(video.getLike_num() > 0 ? video.getLike_num() - 1 : 0);
                boolean b = updateById(video);
                if (b) {
                    return ApiResult.success();
                }
            }
        }
        if (type == 2) {
            if (flag) {
                video.setCollect_num(video.getCollect_num() + 1);
                boolean b = updateById(video);
                if (b) {
                    // 增加一条点赞记录
                    DianZan dianZan = new DianZan();
                    dianZan.setId(IDUtils.getSequenceStr());
                    dianZan.setVid(vid + "");
                    dianZan.setCreate_time(LocalDateTime.now());
                    dianZan.setUid(UserInfoUtils.getCurrentUserId());
                    dianZan.setType(Constast.SHOU_CANG);

                    dianZanService.addOne(dianZan);
                    return ApiResult.success();
                }
            } else {
                // 如果取消点赞，则删除点赞记录
                dianZanService.delOne(vid,UserInfoUtils.getCurrentUserId(),Constast.SHOU_CANG);

                video.setCollect_num(video.getCollect_num() > 0 ? video.getCollect_num() - 1 : 0);
                boolean b = updateById(video);
                if (b) {
                    return ApiResult.success();
                }
            }
        }
        return ApiResult.error("操作失败");
    }

}
