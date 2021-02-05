package com.fz.zf.model.el;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * OA系统角色表
 * </p>
 *
 * @author
 * @since 2021-02-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oa_role")
public class OaRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 是否可用
     */
    private Integer enable_flag;

    private String remark;

    private LocalDateTime create_time;

    private String create_user;

    private LocalDateTime update_time;

    private String update_user;

    @TableField(value = "false")
    private List<OaMenu> perms;


}
