package com.fz.zf.service.el;

import com.ex.framework.base.BaseCRUDService;
import com.ex.framework.data.Record;
import com.ex.framework.web.ApiResult;
import com.fz.zf.mapper.OaRoleMenuMapper;
import com.fz.zf.model.app.Column;
import com.fz.zf.model.el.OaMenu;
import com.fz.zf.model.el.OaRole;
import com.fz.zf.model.el.OaRoleMenu;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * OA系统角色菜单表 服务实现类
 * </p>
 *
 * @author
 * @since 2021-02-05
 */
@Service
public class OaRoleMenuService extends BaseCRUDService<OaRoleMenuMapper, OaRoleMenu> {
    protected Column.OaRoleMenu c = new Column.OaRoleMenu();

    @Autowired
    OaRoleService oaRoleService;

    public List<OaMenu> listMenus(String id) {
        return baseMapper.listMenus(id);
    }

    @Transactional
    public ApiResult delRoleMenu(Record record) {
        String role_id = record.getString("role_id");
        String menu_id = record.getString("menu_id");

        if (StringUtils.isEmpty(role_id)) {
            return ApiResult.error("角色id为空");
        }
        if (StringUtils.isEmpty(menu_id)) {
            return ApiResult.error("权限id为空");
        }

        OaRoleMenu roleMenu = get(query().eq(c.role_id, role_id)
                                         .eq(c.menu_id, menu_id));
        if (roleMenu == null) {
            return ApiResult.error("删除失败");
        }
        baseMapper.deleteById(roleMenu.getId());
        // 返回该角色下所有新权限
        OaRole oaRole = oaRoleService.get(role_id);
        oaRoleService.processRole(oaRole);
        return ApiResult.success(oaRole.getPerms());
    }
}
