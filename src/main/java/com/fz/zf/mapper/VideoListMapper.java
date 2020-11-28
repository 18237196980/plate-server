package com.fz.zf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fz.zf.model.app.VideoList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author
 * @since 2020-10-14
 */
@Repository
public interface VideoListMapper extends BaseMapper<VideoList> {

}
