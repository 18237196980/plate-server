package com.fz.zf.service.app;

import com.ex.framework.base.BaseCRUDService;
import com.ex.framework.data.IDUtils;
import com.ex.framework.data.Record;
import com.ex.framework.web.ApiResult;
import com.fz.zf.mapper.SysAdminMapper;
import com.fz.zf.model.app.Column;
import com.fz.zf.model.app.SysAdmin;
import com.fz.zf.util.MD5;
import com.fz.zf.util.UserInfoUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
public class SysAdminService extends BaseCRUDService<SysAdminMapper, SysAdmin> {

    protected Column.SysAdmin c = new Column.SysAdmin();

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

    @Transactional
    public ApiResult addWithCheck(SysAdmin sysAdmin) {
        SysAdmin admin = get(query().eq(c.name, sysAdmin.getName()));
        if (admin != null) {
            return ApiResult.error("用户名已存在");
        }
        sysAdmin.setId(IDUtils.getSequenceStr());
        sysAdmin.setCreate_time(LocalDateTime.now());
        sysAdmin.setDelete_flag(0);

        sysAdmin.setCreate_user(UserInfoUtils.getCurrentUserId());

        boolean add = add(sysAdmin);
        if (add) {
            return ApiResult.success("添加成功");
        } else {
            return ApiResult.error("添加失败");
        }
    }

    @Transactional
    public ApiResult editWithCheck(SysAdmin admin) {
        if (admin == null) {
            return ApiResult.error("编辑失败");
        }
        if (StringUtils.isEmpty(admin.getId())) {
            return ApiResult.error("用户id不能为空");
        }
        admin.setUpdate_time(LocalDateTime.now());
        admin.setUpdate_user(UserInfoUtils.getCurrentUserId());

        boolean update = updateById(admin);
        if (update) {
            return ApiResult.success("编辑成功");
        } else {
            return ApiResult.error("编辑失败");
        }
    }

    @Transactional
    public ApiResult delWithCheck(String id) {
        if (StringUtils.isEmpty(id)) {
            return ApiResult.error("用户id不能为空");
        }
        SysAdmin admin = get(id);
        if (admin == null) {
            return ApiResult.error("用户不存在");
        }

        admin.setDelete_time(LocalDateTime.now());
        admin.setDelete_flag(1);

        boolean del = deleteById(id);
        if (del) {
            return ApiResult.success("删除成功");
        } else {
            return ApiResult.error("删除失败");
        }
    }
}
