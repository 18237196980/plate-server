package com.fz.zf.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ex.framework.data.IDUtils;
import com.ex.framework.data.Record;
import com.ex.framework.util.security.Md5;
import com.fz.zf.model.app.SysAdmin;
import com.fz.zf.model.app.User;
import com.fz.zf.model.common.Constast;
import com.fz.zf.service.app.SysAdminService;
import com.fz.zf.util.ApiResult;
import com.fz.zf.util.MD5;
import com.fz.zf.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@Slf4j
public class LoginController {
    @Autowired
    SysAdminService sysAdminService;
    @Autowired
    RedisUtil redisUtil;

    /**
     * android 登陆
     *
     * @return
     */
    @RequestMapping("login")
    public ApiResult login(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        if (StringUtils.isEmpty(username)) {
            return ApiResult.error("请输入账号");
        }

        if (StringUtils.isEmpty(password)) {
            return ApiResult.error("请输入密码");
        }

        SysAdmin sysAdmin = sysAdminService.get(new QueryWrapper<SysAdmin>()
                .eq("name", username));
        if (sysAdmin == null) {
            return ApiResult.error("用户不存在");
        }


        String md5 = MD5.getMD5(password);

        if (!md5.equals(sysAdmin.getPwd())) {
            return ApiResult.error("密码错误");
        }

        // 登陆成功，保存token到数据库，并返回uid,name,token
        String token = UUID.randomUUID()
                           .toString();
        // 保存token到redis
        saveUserInRedis(sysAdmin, token);
        log.info("--------------redis存token----------------：" + token);

        sysAdminService.updateById(sysAdmin);

        Record record = Record.build()
                              .set("id", sysAdmin.getId())
                              .set("name", username)
                              .set("token", token);

        return ApiResult.success(record);
    }

    // 存储到redis 格式zf-token_uid,token,当前时间
    private void saveUserInRedis(SysAdmin user, String token) {
        redisUtil.set(Constast.ZF_TOKEN + user.getId(), token, System.currentTimeMillis());
    }

    @RequestMapping("register")
    public ApiResult register(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        if (StringUtils.isEmpty(username)) {
            return ApiResult.error("请输入账号");
        }

        if (StringUtils.isEmpty(password)) {
            return ApiResult.error("请输入密码");
        }

        SysAdmin sysAdmin = sysAdminService.get(new QueryWrapper<SysAdmin>()
                .eq("name", username));
        if (sysAdmin != null) {
            return ApiResult.error("用户已存在");
        }
        // 注册成功
        SysAdmin admin = new SysAdmin();
        admin.setId(IDUtils.getSequenceStr());
        admin.setName(username);
        admin.setPwd(Md5.encode(password));
        sysAdminService.add(admin);

        return ApiResult.success("注册成功");
    }
}
