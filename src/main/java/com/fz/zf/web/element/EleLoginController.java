package com.fz.zf.web.element;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ex.framework.data.Record;
import com.ex.framework.data.RecordBody;
import com.ex.framework.web.ApiResult;
import com.ex.framework.web.ExPage;
import com.ex.framework.web.ExPageResult;
import com.ex.framework.web.TableResult;
import com.fz.zf.app.BaseController;
import com.fz.zf.model.app.SysAdmin;
import com.fz.zf.model.app.User;
import com.fz.zf.model.common.Constast;
import com.fz.zf.service.app.SysAdminService;
import com.fz.zf.service.el.OaMenuService;
import com.fz.zf.util.MD5;
import com.fz.zf.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * elementUI
 */
@RestController
@RequestMapping("/ele")
@Slf4j
@SuppressWarnings("all")
public class EleLoginController extends BaseController {

    @Autowired
    SysAdminService sysAdminService;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    OaMenuService oaMenuService;
    @Autowired
    SysAdminService adminService;

    /**
     * elementUI 登陆
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

        // 登陆成功
        String token = UUID.randomUUID()
                           .toString();
        // 保存token到redis
        saveUserInRedis(sysAdmin, token);
        log.info("--------------redis存token----------------：" + token);

        Record record = Record.build()
                              .set("id", sysAdmin.getId())
                              .set("name", username)
                              .set("token", token);

        return ApiResult.success(record);
    }


    /**
     * 获取主页菜单
     *
     * @return
     */
    @RequestMapping("menus")
    public ApiResult menus() {
        try {
            List list = oaMenuService.listMenus();
            return ApiResult.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.error("获取菜单失败");
        }
    }

    /**
     * 用户列表
     *
     * @param record
     * @return
     */
    @PostMapping("users")
    public Object users(@RecordBody Record record) {
        try {
            String cnname = record.getString("cnname");
            QueryWrapper<SysAdmin> wrapper = new QueryWrapper<>();
            wrapper.likeRight(StringUtils.isNotEmpty(cnname), "cnname", cnname);
            wrapper.orderByDesc("id");
            ExPage page = parseExPage(record);
            ExPageResult<SysAdmin> pageResult = adminService.list(page, wrapper);
            return TableResult.success(pageResult);
        } catch (Exception ex) {
            return TableResult.error(ex.getLocalizedMessage());
        }

    }

    /**
     * 添加用户
     *
     * @param record
     * @return
     */
    @PostMapping("user/add")
    public ApiResult addUser(@RequestBody SysAdmin sysAdmin) {
        try {
            return sysAdminService.addWithCheck(sysAdmin);
        } catch (Exception ex) {
            return ApiResult.error("添加失败");
        }
    }

    /**
     * 根据用户id查询用户
     *
     * @param record
     * @return
     */
    @GetMapping("user/getById")
    public ApiResult getUserById(@RequestParam("id") String id) {
        try {
            // String id = record.getString("id");
            if (StringUtils.isEmpty(id)) {
                return ApiResult.error("用户id不能为空");
            }
            SysAdmin sysAdmin = sysAdminService.get(id);
            if (sysAdmin == null) {
                return ApiResult.error("用户不存在");
            }
            return ApiResult.success(sysAdmin);
        } catch (Exception ex) {
            return ApiResult.error("添加失败");
        }
    }

    /**
     * 编辑用户
     *
     * @param record
     * @return
     */
    @PostMapping("user/edit")
    public ApiResult editUser(@RequestBody SysAdmin sysAdmin) {
        try {
            return sysAdminService.editWithCheck(sysAdmin);
        } catch (Exception ex) {
            return ApiResult.error("添加失败");
        }
    }

    /**
     * 删除用户
     *
     * @param record
     * @return
     */
    @PostMapping("user/del")
    public ApiResult delUser(@RequestBody SysAdmin sysAdmin) {
        try {
            return sysAdminService.delWithCheck(sysAdmin.getId());
        } catch (Exception ex) {
            return ApiResult.error("添加失败");
        }
    }

    // 存储到redis 格式zf-token_uid,token,当前时间
    private void saveUserInRedis(SysAdmin user, String token) {
        redisUtil.set(Constast.ZF_TOKEN + user.getId(), token, System.currentTimeMillis());
    }

}
