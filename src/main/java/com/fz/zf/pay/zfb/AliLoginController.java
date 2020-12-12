package com.fz.zf.pay.zfb;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ex.framework.data.IDUtils;
import com.fz.zf.config.PropertiesBean;
import com.fz.zf.model.app.SysAdmin;
import com.fz.zf.service.app.SysAdminService;
import com.fz.zf.util.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequestMapping("/ali")
@RestController
@Slf4j
public class AliLoginController {
    @Autowired
    PropertiesBean prop;
    @Autowired
    SysAdminService sysAdminService;

    @PostMapping("getOpenid")
    public ApiResult getOpenid(String code) {
        AlipayClient client = new DefaultAlipayClient(
                "https://openapi.alipaydev.com/gateway.do",
                "2021000116669117",
                prop.alipayXcxPrivateKey,
                "json",
                "UTF-8",
                prop.alipayXcxPublicKey,
                "RSA2");
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setCode(code);
        request.setGrantType("authorization_code");

        try {
            AlipaySystemOauthTokenResponse response = client.execute(request);
            if (response.isSuccess()) {
                String userId = response.getUserId();

                log.info("userId---------------------:" + userId);

                SysAdmin model = sysAdminService.get(new QueryWrapper<SysAdmin>().eq("openid", userId));
                if (model == null) {
                    // 添加
                    SysAdmin sysAdmin = new SysAdmin();
                    sysAdmin.setId(IDUtils.getSequenceStr());
                    sysAdmin.setOpenid(userId);
                    sysAdmin.setCreate_time(LocalDateTime.now());
                    sysAdminService.add(sysAdmin);
                }
                return ApiResult.success(userId);
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return ApiResult.error();
        }
        return ApiResult.error();
    }
}
