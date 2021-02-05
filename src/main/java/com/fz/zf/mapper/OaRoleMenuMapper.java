package com.fz.zf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fz.zf.model.el.OaMenu;
import com.fz.zf.model.el.OaRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * OA系统角色菜单表 Mapper 接口
 * </p>
 *
 * @author
 * @since 2021-02-05
 */
public interface OaRoleMenuMapper extends BaseMapper<OaRoleMenu> {

    List<OaMenu> listMenus(@Param("id") String id);
}
