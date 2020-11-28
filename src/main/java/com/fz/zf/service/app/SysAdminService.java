package com.fz.zf.service.app;

import com.ex.framework.base.BaseCRUDService;
import com.ex.framework.data.Record;
import com.ex.framework.web.ApiResult;
import com.fz.zf.mapper.SystemAdminMapper;
import com.fz.zf.model.app.SysAdmin;
import com.fz.zf.util.MD5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SysAdminService extends BaseCRUDService<SystemAdminMapper, SysAdmin> {

    @Transactional
    public ApiResult updatePwd(Record record) {
        String uid = record.getString("uid");
        String old_pwd = record.getString("old_pwd");
        String pwd = record.getString("pwd");
        String re_pwd = record.getString("re_pwd");

        if (StringUtils.isEmpty(uid)) {
            return ApiResult.error("用户id不能为空");
        }
        if (StringUtils.isEmpty(old_pwd)) {
            return ApiResult.error("旧密码不能为空");
        }
        if (StringUtils.isEmpty(pwd)) {
            return ApiResult.error("新密码不能为空");
        }
        if (StringUtils.isEmpty(re_pwd)) {
            return ApiResult.error("确认密码不能为空");
        }

        SysAdmin model = get(uid);

        if (model == null) {
            return ApiResult.error("用户不存在");
        }
        if (!StringUtils.equals(model.getPwd(), MD5.getMD5(old_pwd))) {
            return ApiResult.error("旧密码不正确");
        }

        if (!StringUtils.equals(pwd, re_pwd)) {
            return ApiResult.error("两次密码不一致");
        }

        model.setPwd(MD5.getMD5(pwd));
        boolean b = updateById(model);
        if (b) {
            return ApiResult.success();
        } else {
            return ApiResult.error("修改密码失败");
        }
    }

    @Transactional
    public ApiResult changeHeader(SysAdmin model) {
        boolean b = updateById(model);
        if (b) {
            return ApiResult.success();
        } else {
            return ApiResult.error();
        }

    }
}
