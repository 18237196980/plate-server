package com.fz.zf.model.el;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * OA系统用户角色关系表
 * </p>
 *
 * @author
 * @since 2021-02-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oa_user_role")
public class OaUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String role_id;

    private String user_id;


}
