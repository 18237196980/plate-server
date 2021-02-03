package com.fz.zf.model.el;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import com.ex.framework.util.Lang;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @author
 * @since 2021-02-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oa_menu")
public class OaMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 父id
     */
    private String parent_id;

    /**
     * 菜单标题
     */
    private String name;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 路径
     */
    private String path;

    /**
     * 组件
     */
    private String component;

    /**
     * 组件名字
     */
    private String component_name;

    /**
     * 一级菜单跳转地址
     */
    private String redirect;

    /**
     * 菜单排序
     */
    private Double sort_no;

    /**
     * 菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)
     */
    private Integer menu_type;

    /**
     * 菜单权限编码
     */
    private String perms;

    /**
     * 是否在菜单中显示: 0否,1是（默认值1）
     */
    private Boolean hidden;

    /**
     * 是否为固定标签: 0:不是  1:是（默认值0）
     */
    private Boolean is_affix;

    /**
     * 是否缓存该页面:    1:是   0:不是
     */
    private Boolean keep_alive;

    /**
     * 创建人
     */
    private String create_user;

    /**
     * 创建时间
     */
    private LocalDateTime create_time;

    /**
     * 更新人
     */
    private String update_user;

    /**
     * 更新时间
     */
    private LocalDateTime update_time;

    /**
     * 描述
     */
    private String remark;

    @TableField(value = "false")
    List<OaMenu> children = Lang.list();


}
