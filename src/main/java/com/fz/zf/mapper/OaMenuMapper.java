package com.fz.zf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fz.zf.model.el.OaMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author
 * @since 2021-02-03
 */
public interface OaMenuMapper extends BaseMapper<OaMenu> {

    List<OaMenu> listMenuByUid(@Param("userId") String userId);
}
