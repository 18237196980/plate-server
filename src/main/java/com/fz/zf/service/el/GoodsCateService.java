package com.fz.zf.service.el;

import com.ex.framework.base.BaseCRUDService;
import com.ex.framework.util.Lang;
import com.fz.zf.mapper.GoodsCateMapper;
import com.fz.zf.model.app.Column;
import com.fz.zf.model.el.GoodsCate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author
 * @since 2021-02-07
 */
@Service
public class GoodsCateService extends BaseCRUDService<GoodsCateMapper, GoodsCate> {

    protected Column.GoodsCate c = new Column.GoodsCate();

    public List<GoodsCate> goodsCateLevel(Integer type) {
        List<GoodsCate> list = list();
        List<GoodsCate> rootMenus = list(query().eq(c.pid, 0));
        for (GoodsCate menu : rootMenus) {
            addChildren(list, menu);
        }
        return rootMenus;
    }

    private void addChildren(List<GoodsCate> list, GoodsCate menu) {
        List<GoodsCate> child = Lang.list();
        for (GoodsCate oaMenu : list) {
            if (StringUtils.equals(oaMenu.getPid(), menu.getId())) {
                child.add(oaMenu);
                addChildren(list, oaMenu);
                menu.setChildren(child);
            }
        }
    }
}
