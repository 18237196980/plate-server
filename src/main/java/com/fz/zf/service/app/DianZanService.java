package com.fz.zf.service.app;


import com.ex.framework.base.BaseCRUDService;
import com.fz.zf.mapper.DianZanMapper;
import com.fz.zf.model.app.DianZan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * DianZanService
 */
@Service
@Transactional(readOnly = true)
public class DianZanService extends BaseCRUDService<DianZanMapper, DianZan> {

    @Transactional
    public void addOne(DianZan dianZan) {
        add(dianZan);
    }

    public boolean checkDianZan(Integer vid, String currentUserId, Integer type) {
        return exists(query().eq("vid", vid)
                             .eq("uid", currentUserId)
                             .eq("type", type));
    }

    @Transactional
    public void delOne(Integer vid, String currentUserId, Integer type) {
        delete(query().eq("vid", vid)
                      .eq("uid", currentUserId)
                      .eq("type", type));
    }
}
