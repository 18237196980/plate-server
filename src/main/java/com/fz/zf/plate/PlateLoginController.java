package com.fz.zf.plate;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ex.framework.data.IDUtils;
import com.ex.framework.data.Record;
import com.ex.framework.data.RecordBody;
import com.ex.framework.util.SpringUtils;
import com.fz.zf.config.PropertiesBean;
import com.fz.zf.model.app.SysAdmin;
import com.fz.zf.model.app.User;
import com.fz.zf.model.common.Constast;
import com.fz.zf.service.app.SysAdminService;
import com.fz.zf.util.ApiResult;
import com.fz.zf.util.MD5;
import com.fz.zf.util.RedisUtil;
import com.fz.zf.util.RegexUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * 车牌(登陆、注册)
 */
@RestController
@RequestMapping("/plate/user")
@Slf4j
@SuppressWarnings("all")
public class PlateLoginController {
    @Autowired
    SysAdminService sysAdminService;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    PropertiesBean propertiesBean;

    /**
     * android 车牌 账号密码登陆
     *
     * @return
     */
    @PostMapping("loginByPwd")
    public ApiResult loginByPwd(@RequestBody User user) {
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

        Record record = Record.build()
                              .set("user", sysAdmin)
                              .set("token", token);

        return ApiResult.success(record);
    }

    /**
     * android 车牌 token登陆
     *
     * @return
     */
    @PostMapping("loginByToken")
    public ApiResult loginByToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        String uid = request.getHeader("uid");

        if (StringUtils.isEmpty(token)) {
            return ApiResult.error();
        }

        if (StringUtils.isEmpty(uid)) {
            return ApiResult.error();
        }

        SysAdmin sysAdmin = sysAdminService.get(new QueryWrapper<SysAdmin>()
                .eq("id", uid));
        if (sysAdmin == null) {
            return ApiResult.error("用户不存在");
        }

        boolean flag = checkToken(uid, token);
        if (flag) {
            return ApiResult.success();
        } else {
            return ApiResult.error("token错误");
        }
    }

    /**
     * android 车牌 退出登陆
     *
     * @return
     */
    @PostMapping("logout")
    public ApiResult logout(String uid) {
        try {
            if (StringUtils.isEmpty(uid)) {
                return ApiResult.error("用户id为空");
            }
            redisUtil.del(Constast.ZF_TOKEN + uid);
            return ApiResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.error("退出登陆失败");
        }
    }

    /**
     * 注册
     *
     * @param record
     * @return
     */
    @PostMapping("register")
    public ApiResult register(@RecordBody Record record) {
        String phone = record.getString("phone");
        String code = record.getString("code");
        String name = record.getString("name");
        String pwd = record.getString("pwd");
        String re_pwd = record.getString("re_pwd");
        if (StringUtils.isEmpty(phone)) {
            return ApiResult.error("手机号不能为空");
        }
        if (StringUtils.isEmpty(code)) {
            return ApiResult.error("验证码不能为空");
        }
        if (StringUtils.isEmpty(name)) {
            return ApiResult.error("用户名不能为空");
        }
        if (StringUtils.isEmpty(pwd)) {
            return ApiResult.error("密码不能为空");
        }
        if (StringUtils.isEmpty(re_pwd)) {
            return ApiResult.error("重复密码不能为空");
        }

        if (!RegexUtils.validateMobilePhone(phone)) {
            return ApiResult.error("手机号格式不正确");
        }

        SysAdmin model = sysAdminService.get(new QueryWrapper<SysAdmin>().eq("name", name));
        if (model != null) {
            return ApiResult.error("用户名已存在");
        }

        if (!StringUtils.equals(pwd, re_pwd)) {
            return ApiResult.error("两次密码不一致");
        }

        String phone_code = (String) redisUtil.get(Constast.PHONE_CODE + phone);

        if (StringUtils.isNotEmpty(phone_code)) {
            if (StringUtils.equals(phone_code, code)) {
                long expire = redisUtil.getExpire(Constast.PHONE_CODE + phone);
                log.info("验证码再有-----------------------------:" + expire + "s过期");
                redisUtil.del(Constast.PHONE_CODE + phone);

                // 添加用户
                SysAdmin user = new SysAdmin();
                user.setId(IDUtils.getSequenceStr());
                user.setName(name);
                user.setPwd(MD5.getMD5(pwd));
                user.setMobile(phone);
                user.setCreate_time(LocalDateTime.now());
                user.setDelete_flag(0);
                user.setEnable_flag(1);

                boolean add = sysAdminService.add(user);
                if (add) {
                    return ApiResult.success();
                } else {
                    return ApiResult.error("注册失败");
                }

            } else {
                return ApiResult.error("验证码错误");
            }
        } else {
            return ApiResult.error("验证码无效,请获取验证码");
        }

    }

    /**
     * 注册时，判断用户名是否可用
     *
     * @param name
     * @return
     */
    @PostMapping("canUseName")
    public ApiResult canUseName(@RequestParam String name) {
        SysAdmin model = sysAdminService.get(new QueryWrapper<SysAdmin>().eq("name", name));
        return model == null ? ApiResult.success() : ApiResult.error();
    }


    /**
     * 手机号登陆
     *
     * @param record
     * @return
     */
    @PostMapping("loginByPhone")
    public ApiResult loginByPhone(@RecordBody Record record) {
        String phone = record.getString("phone");
        String code = record.getString("code");
        if (StringUtils.isEmpty(phone)) {
            return ApiResult.error("手机号不能为空");
        }
        if (!RegexUtils.validateMobilePhone(phone)) {
            return ApiResult.error("手机号格式不正确");
        }

        if (StringUtils.isEmpty(code)) {
            return ApiResult.error("验证码不能为空");
        }

        SysAdmin model = sysAdminService.get(new QueryWrapper<SysAdmin>().eq("mobile", phone));
        if (model == null) {
            return ApiResult.error("手机号暂不能登陆，请先注册");
        }

        String phone_code = (String) redisUtil.get(Constast.PHONE_CODE + phone);

        if (StringUtils.isNotEmpty(phone_code)) {
            if (StringUtils.equals(phone_code, code)) {
                long expire = redisUtil.getExpire(Constast.PHONE_CODE + phone);
                log.info("验证码再有-----------------------------:" + expire + "s过期");
                redisUtil.del(Constast.PHONE_CODE + phone);
                String new_token = UUID.randomUUID()
                                       .toString();
                redisUtil.set(Constast.ZF_TOKEN + model.getId(), new_token, Constast.TOKEN_EXPIRE);
                Record r = Record.build()
                                 .set("user", model)
                                 .set("token", new_token);
                return ApiResult.success(r);
            } else {
                return ApiResult.error("验证码错误");
            }
        } else {
            return ApiResult.error("验证码无效,请获取验证码");
        }

    }


    /**
     * android 车牌 微信登陆
     *
     * @return
     */
    @PostMapping("loginWeiXin")
    public ApiResult loginWeiXin(@RecordBody Record record) {
        String openid = record.getString("openid");
        String nickName = record.getString("nickName");
        String avatarUrl = record.getString("avatarUrl");
        String unionId = record.getString("unionId");

        if (StringUtils.isEmpty(openid)) {
            return ApiResult.error("openid不能为空");
        }
        if (StringUtils.isEmpty(nickName)) {
            return ApiResult.error("nickName不能为空");
        }
        if (StringUtils.isEmpty(avatarUrl)) {
            return ApiResult.error("avatarUrl不能为空");
        }

        SysAdmin sysAdmin = sysAdminService.get(new QueryWrapper<SysAdmin>()
                .eq("openid", openid));
        if (sysAdmin != null) {
            if (!StringUtils.equals(sysAdmin.getHeader_path(), avatarUrl)) {
                sysAdmin.setHeader_path(avatarUrl);
            }
            if (!StringUtils.equals(sysAdmin.getNickName(), nickName)) {
                sysAdmin.setNickName(nickName);
            }

            sysAdminService.updateById(sysAdmin);
            Record r = getReturnData(sysAdmin);

            return ApiResult.success(r);
        } else {
            // 先注册再登录
            SysAdmin model = new SysAdmin();
            String id = IDUtils.getSequenceStr();
            model.setId(id);
            model.setOpenid(openid);
            model.setNickName(nickName);
            model.setHeader_path(avatarUrl);
            model.setDelete_flag(0);
            model.setEnable_flag(1);
            model.setCreate_time(LocalDateTime.now());

            boolean add = sysAdminService.add(model);
            if (add) {
                Record r = getReturnData(model);
                return ApiResult.success(r);
            } else {
                return ApiResult.error("登录失败");
            }
        }
    }

    /**
     * 微信小程序授权登陆
     *
     * @return
     */
    @PostMapping("getOpedId")
    public ApiResult getOpedId(String code) {
        try {
            if (StringUtils.isEmpty(code)) {
                return ApiResult.error("code不能为空");
            }
            String url = "https://api.weixin.qq.com/sns/jscode2session";
            Map<String, Object> params = new HashMap<>();
            params.put("appid", propertiesBean.appid);
            params.put("secret", propertiesBean.secret);
            params.put("js_code", code);
            params.put("grant_type", "authorization_code");

            String res = HttpUtil.post(url, params);
            JSONObject json = JSONUtil.parseObj(res);
            String openid = json.getStr("openid");
            log.info("openid---------------------------：" + openid);
            return ApiResult.success(openid);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.error("退出登陆失败");
        }
    }

    private Record getReturnData(SysAdmin sysAdmin) {
        // 直接登录，保存token到数据库，并返回user,token
        String token = UUID.randomUUID()
                           .toString();
        // 保存token到redis
        saveUserInRedis(sysAdmin, token);
        log.info("--------------redis存token----------------：" + token);

        return Record.build()
                     .set("user", sysAdmin)
                     .set("token", token);
    }

    // 校验token
    private boolean checkToken(String uid, String token) {
        RedisUtil redisUtil = SpringUtils.getBean(RedisUtil.class);
        String redis_token = (String) redisUtil.get(Constast.ZF_TOKEN + uid);
        long old_time = redisUtil.getExpire(Constast.ZF_TOKEN + uid);

        log.info("----------------前端传过来token----------------:" + token);
        log.info("----------------redis取出token-----------------:" + redis_token);
        log.info("----------------token再有-----------------:" + old_time + "s过期");

        if (StringUtils.isEmpty(redis_token)) {
            return false;
        } else {
            if (StringUtils.equals(redis_token, token)) {
                // 如果token没有过期，此时延长token有效期
                redisUtil.set(Constast.ZF_TOKEN + uid, token, Constast.TOKEN_EXPIRE);
                return true;
            }
        }
        return false;
    }

    // 存储到redis 格式zf-token_uid,token,过期时间
    private void saveUserInRedis(SysAdmin user, String token) {
        redisUtil.set(Constast.ZF_TOKEN + user.getId(), token, Constast.TOKEN_EXPIRE);
    }

}
