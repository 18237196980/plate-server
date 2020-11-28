package com.fz.zf.model.app;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 点赞
 *
 * @author
 * @since 2020-10-14
 */
@Data
@Accessors(chain = true)
@TableName("dian_zan")
public class DianZan {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 视频id
     */
    private String vid;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 创建时间
     */
    private LocalDateTime create_time;

    /**
     * 1(点赞)，2(收藏),3(评论)
     */
    private Integer type;

}
