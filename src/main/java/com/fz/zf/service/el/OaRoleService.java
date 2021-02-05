package com.fz.zf.service.el;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ex.framework.base.BaseCRUDService;
import com.ex.framework.data.Record;
import com.ex.framework.util.Lang;
import com.ex.framework.web.ExPage;
import com.ex.framework.web.ExPageResult;
import com.fz.zf.mapper.OaRoleMapper;
import com.fz.zf.model.el.OaMenu;
import com.fz.zf.model.el.OaRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.ex.framework.mybatisplus.MyBatisPlusUtils.toExPageResult;
import static com.ex.framework.mybatisplus.MyBatisPlusUtils.toPage;

/**
 * <p>
 * OA系统角色表 服务实现类
 * </p>
 *
 * @author
 * @since 2021-02-05
 */
@Service
@SuppressWarnings("all")
public class OaRoleService extends BaseCRUDService<OaRoleMapper, OaRole> {

    @Autowired
    OaRoleMenuService oaRoleMenuService;

    public ExPageResult<OaRole> listRoles(ExPage page, Record record) {
        Page mPage = toPage(page);
        String name = record.getString("name");
        IPage<OaRole> result = list(mPage, query().like(StringUtils.isNotEmpty(name), "name", name));
        for (OaRole oaRole : result.getRecords()) {
            processRole(oaRole);
        }
        return toExPageResult(result);
    }

    protected void processRole(OaRole oaRole) {
        List<OaMenu> list = oaRoleMenuService.listMenus(oaRole.getId());
        List<OaMenu> rootMenus = list.stream()
                                     .filter(t -> t.getParent_id()
                                                   .equals("1"))
                                     .collect(Collectors.toList());
        for (OaMenu menu : rootMenus) {
            addChildren(list, menu);
        }
        oaRole.setPerms(rootMenus);
    }

    protected void addChildren(List<OaMenu> list, OaMenu menu) {
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
