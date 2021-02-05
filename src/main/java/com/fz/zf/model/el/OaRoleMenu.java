package com.fz.zf.model.el;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * OA系统角色菜单表
 * </p>
 *
 * @author
 * @since 2021-02-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oa_role_menu")
public class OaRoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String role_id;

    private String menu_id;


}
