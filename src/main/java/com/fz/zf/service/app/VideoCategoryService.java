package com.fz.zf.service.app;


import com.ex.framework.base.BaseCRUDService;
import com.fz.zf.mapper.VideoCategoryMapper;
import com.fz.zf.model.app.VideoCategory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * VideoCategoryService
 */
@Service
@Transactional(readOnly = true)
public class VideoCategoryService extends BaseCRUDService<VideoCategoryMapper, VideoCategory> {

}
