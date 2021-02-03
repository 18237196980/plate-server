package com.fz.zf.service.el;

import com.ex.framework.base.BaseCRUDService;
import com.ex.framework.util.Lang;
import com.fz.zf.mapper.OaMenuMapper;
import com.fz.zf.model.app.Column;
import com.fz.zf.model.el.OaMenu;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author
 * @since 2021-02-03
 */
@Service
public class OaMenuService extends BaseCRUDService<OaMenuMapper, OaMenu> {
    protected Column.OaMenu c = new Column.OaMenu();

    public List listMenus() {
        List<OaMenu> list = list();
        List<OaMenu> rootMenus = list(query().eq(c.parent_id, 1));
        for (OaMenu menu : rootMenus) {
            addChildren(list, menu);
        }
        return rootMenus;
    }

    private void addChildren(List<OaMenu> list, OaMenu menu) {
        List<OaMenu> child = Lang.list();
        for (OaMenu oaMenu : list) {
            if (StringUtils.equals(oaMenu.getParent_id(), menu.getId())) {
                child.add(oaMenu);
                addChildren(list, oaMenu);
                menu.setChildren(child);
            }
        }
    }
}
