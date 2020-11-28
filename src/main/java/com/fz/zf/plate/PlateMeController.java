package com.fz.zf.plate;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ex.framework.data.Record;
import com.ex.framework.data.RecordBody;
import com.ex.framework.web.ApiResult;
import com.fz.zf.model.app.SysAdmin;
import com.fz.zf.model.common.Constast;
import com.fz.zf.mq.MsgProducer;
import com.fz.zf.service.app.SysAdminService;
import com.fz.zf.util.DateUtil;
import com.fz.zf.util.RedisUtil;
import com.fz.zf.util.RegexUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/plate/me")
@Slf4j
public class PlateMeController {

    @Autowired
    SysAdminService sysAdminService;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    MsgProducer msgProducer;

    /**
     * 根据用户id查询用户
     *
     * @param id
     * @return
     */
    @PostMapping("me")
    public ApiResult me(String id) {
        try {
            SysAdmin sysAdmin = sysAdminService.get(id);
            return ApiResult.success(sysAdmin);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.error();
        }
    }

    /**
     * 修改密码
     *
     * @param record
     * @return
     */
    @PostMapping("updatePwd")
    public ApiResult updatePwd(@RecordBody Record record) {
        try {
            return sysAdminService.updatePwd(record);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.error();
        }
    }

    /**
     * 完善个人资料
     *
     * @param record
     * @return
     */
    @PostMapping("fullUserInfo")
    public ApiResult fullUserInfo(@RecordBody Record record) {
        SysAdmin sysAdmin = record.toObject(SysAdmin.class);
        String str_birth = record.getString("birth");
        try {
            sysAdmin.setBirth(DateUtil.parse(str_birth, DateUtil.YMD));
        } catch (ParseException e) {
            e.printStackTrace();
            return ApiResult.error("修改信息失败");
        }

        String selectCode = record.getString("selectCode");

        if (StringUtils.isNotEmpty(selectCode)) {
            String[] codes = selectCode.split(",");
            if (codes.length == 3) {
                sysAdmin.setCity_code1(codes[0].replaceAll("\\[", ""));
                sysAdmin.setCity_code2(codes[1]);
                sysAdmin.setCity_code3(codes[2].replaceAll("]", ""));

                boolean b = sysAdminService.updateById(sysAdmin);
                if (b) {
                    return ApiResult.success();
                } else {
                    return ApiResult.error("修改信息失败");
                }
            }
            return ApiResult.error("请选择完整地址");
        }
        return ApiResult.error("修改信息失败");
    }

    /**
     * 修改头像
     *
     * @param sysAdmin
     * @return
     */
    @PostMapping("changeHeader")
    public ApiResult changeHeader(@RequestBody SysAdmin sysAdmin) {
        try {
            return sysAdminService.changeHeader(sysAdmin);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.error();
        }
    }

    /**
     * 获取手机验证码(存入redis)
     * code_flag=1说明是登陆时需要发送验证码，需校验是否已注册，没注册提示去注册
     *
     * @param phone
     * @return
     */
    @PostMapping("getPhoneCode")
    public ApiResult getPhoneCode(String phone, Integer code_flag) {
        try {
            if (StringUtils.isEmpty(phone)) {
                return ApiResult.error("手机号不能为空");
            }
            if (!RegexUtils.validateMobilePhone(phone)) {
                return ApiResult.error("手机号格式不正确");
            }

            if (code_flag == 1) {
                SysAdmin model = sysAdminService.get(new QueryWrapper<SysAdmin>().eq("mobile", phone));
                if (model == null) {
                    return ApiResult.error("手机号暂不能登陆，请先注册");
                }
            }
            if (code_flag == 0) {
                boolean mobile = sysAdminService.exists(new QueryWrapper<SysAdmin>().eq("mobile", phone));
                if (mobile) {
                    return ApiResult.error("手机号已经被注册");
                }
            }

            // 发送消息(手机号)到消息队列(生产者)，然后消费者取出消息，
            // 根据手机号生成验证码，将验证码存入redis，最后执行发送短信
            Object code = "";
            msgProducer.sendMsg(phone);
            try {
                Thread.sleep(500);
                code = redisUtil.get(Constast.PHONE_CODE + phone);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ApiResult.success(code);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.error("生成验证码失败");
        }
    }
}
