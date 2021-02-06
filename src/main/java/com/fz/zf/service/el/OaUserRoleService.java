package com.fz.zf.service.el;

import com.ex.framework.base.BaseCRUDService;
import com.ex.framework.data.IDUtils;
import com.ex.framework.data.Record;
import com.ex.framework.web.ApiResult;
import com.fz.zf.mapper.OaUserRoleMapper;
import com.fz.zf.model.el.OaUserRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * OA系统用户角色关系表 服务实现类
 * </p>
 *
 * @author
 * @since 2021-02-06
 */
@Service
public class OaUserRoleService extends BaseCRUDService<OaUserRoleMapper, OaUserRole> {

    @Transactional
    public ApiResult assignRoleForUser(Record record) {
        String u_id = record.getString("u_id");
        String role_id = record.getString("role_id");
        if (StringUtils.isEmpty(u_id)) {
            return ApiResult.error("分配角色失败");
        }
        if (StringUtils.isEmpty(role_id)) {
            return ApiResult.error("请选择角色");
        }
        delete(query().eq("user_id", u_id));
        OaUserRole oaUserRole = new OaUserRole();
        oaUserRole.setId(IDUtils.getSequenceStr());
        oaUserRole.setRole_id(role_id);
        oaUserRole.setUser_id(u_id);

        add(oaUserRole);
        return ApiResult.success("分配角色成功");
    }
}
