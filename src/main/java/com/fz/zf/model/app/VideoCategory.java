package com.fz.zf.model.app;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-10-20
 */
@Data
@Accessors(chain = true)
@TableName("video_category")
public class VideoCategory {

    private static final long serialVersionUID = 1L;

    private Integer category_id;

    private String category_name;


}
