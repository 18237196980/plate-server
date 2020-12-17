package com.fz.zf.service.app;


import com.ex.framework.base.BaseCRUDService;
import com.fz.zf.mapper.GoodOrderMapper;
import com.fz.zf.model.app.GoodOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * VideoCategoryService
 */
@Service
@Transactional(readOnly = true)
public class GoodOrderService extends BaseCRUDService<GoodOrderMapper, GoodOrder> {

}
