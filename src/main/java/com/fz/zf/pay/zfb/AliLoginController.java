package com.fz.zf.pay.zfb;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ex.framework.data.IDUtils;
import com.ex.framework.data.Record;
import com.ex.framework.data.RecordBody;
import com.fz.zf.config.PropertiesBean;
import com.fz.zf.model.app.SysAdmin;
import com.fz.zf.service.app.SysAdminService;
import com.fz.zf.util.ApiResult;
import com.fz.zf.util.CodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequestMapping("/ali")
@RestController
@Slf4j
@SuppressWarnings("all")
public class AliLoginController {
    @Autowired
    PropertiesBean prop;
    @Autowired
    SysAdminService sysAdminService;

    @PostMapping("getPerOrderId")
    public String getPerOrderId(@RecordBody Record record) {
        String gateway = "https://openapi.alipaydev.com/gateway.do";
        String appid = "2021000116669117";
        String private_id = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC+coaCh62Mxq0w6PUF33aEXRrTzeot3Gfi5SGPX9YqTbZPavyNr/4OGmc8Fl99xItdh+MNTKItXjUzBrofAM1P0hjDiCoba7ctHvU85cvTNa3wjGHko4+S3ZAjg5Kc1nbsN4NrOWxgl3cv57wfRR2RhJ8oXw0WNLBNFTN6BLv8EJb/OUy4Miy0vD38ldXhMDC+CzL5pcqLJYMw1W4sawFowNQeuQeZqXDciaOCvdEjTSMH4pVBlKiTFBn+2edYqHYgzHNd4Iu963wDOdOSaxdiLHAscswLQUNqA6+VSQbZaJSnTNeB0zSISupQ/MO9DeQfz7KFs9Sx/AYB2iEzOuZVAgMBAAECggEBAJ5TMXXEe8gjxwl5MXGfc7TYfhwE/KEBsa/UEtFRuAbVMjHV3H2iCNAKrTYr11kEE2Q/OsfspIqY3hs8hXCOxyCLpiCvy2meY9rz+KaW15sP7jmjSyK7wvkyjsincjRGqvj3sZ5rT6kBYomF2kQYvlsiaPo0Avop9UMv0+qlku4aIKTATrhputuT3a7nC/DbGd1FKlLxiLhv4u2qtg2N2tYO/T6EoYmZP6cwxztoilSuvn6f44EtCwn5puzJxGsbBoqImwDGSB/cxmv1fCUy+nH4uCKRNhDsSmPEZNlCD2vizvmMmp0BJssEt5leBMuTr7ZWycQ+fMPYvynPjBXUMYUCgYEA7Em/M9X18Hl+ehy7e9VV4U7QHmwMueSWa5pS4RSGlKMDgaXr2I3OC7uH9GscXoVZBVIM6cwKwV8omxKvikw5sncLWPpeFaxNYyM54o5VaC3Jp9Y/bff94VxTdpRJg+uRDm/h+3OWVb7Tv1I5+Hav9hp3gXjAi/z1oDF1M1KRVZsCgYEAzlXJsiXOLRE1N4n+nzFqAfXafaFDP9NqKv2zOfeWzOFcAXRLx+wt5ggH6GM15TjnFyPHLaaZnovo0pv272GV2sTMpmYw9TJPyrHBZq7AM86ob5AFehQ17w/EJtceb2f6qqc/r+KFpRc2dFF0kuRn514MlYhMvPJwgQbAFbFV6s8CgYAMPYnFIqlZ6sFNjB1+Pb/0KwQG/2vtWVUdf+IZPNn36zXsSm8cScRJwU46sC3JC7lf2C9JEUPhXo+Y8O/dBzPZ9ebFljq3frSHlTSTcvIjsnOG2Udx5+3j9cdxFh900QgzqkIXp5Y7HOaVO6o7H9LfueFE+L9owRLsWIOMxBM5TwKBgBSNdwnHukHC9QoTNMmmomiPlLPu3EP/SxP9MV0UzHCcAg4jB2linS/MnP0I5NVwVZR4e96QOb0RMp/H6VDu535jG/93Lmk+GbSXACy6O2rtqtlx/xmp6bVmXKOBygZO1Skqf62FYsqPBV2Qv4viNHdXZul+Kian/4zan0eJ4oUBAoGAJa4B2L2qQIdYRZPPKDth81lO8CU22iebCzEnH1YkZ2bZG9Kyll2khofjuXy9yiui518ZQb8qyj9Cd7Z3jlagBxSleh92uR7eFLmK2N9NSBZb1oNQf/HA3ZJ1VdZvV9wGUgOogGIUgOUUXtJ0VvolclSYPjx+EQCCIH5EtWGZSJE=";
        String ali_public = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgkkRH8D6KzdzUtUY4Rhpog7bjcFPMY6+Uu3OVT7QEmsDmkQ6u56qxG6RGLKe9id8au1cGtj/j8NFnhfnEiwdbfrQs9cgXUKwDneLPaIBkefIwXGK7zk/2O0uggyXcBCkfniBJsBsrGvv8cOUyv1c0DhaUGRaY0ob2RQ9zGSL/967nk97xt9/fMGFVzzLCQ+7+5SRstr7KPapX+Mf+f8yPMV7r/DLf9gCjzTuKvAtu6B8nPIJCzQeEXgWWHIQ9MNRQsqBJkGUpujpPJxHOQvIDN1j5t1RStzOw631LTY4hU6/DhTZuXxZ14GnESwzbur6OmI8LlVDjfJ67Y14v2sE7wIDAQAB";

        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(gateway, appid, private_id, "json", "UTF-8", ali_public, "RSA2");
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("测试测试");
        model.setSubject("App支付测试");
        model.setOutTradeNo(CodeUtils.getTel());
        model.setTimeoutExpress("30m");
        model.setTotalAmount("0.02");
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl("http://www.demo.com");
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            String pid = response.getBody();
            log.info("response.getBody():" + pid);//就是orderString 可以直接给客户端请求，无需再做处理。

            return pid;
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return "";
    }

    @PostMapping("getOpenid")
    public ApiResult getOpenid(String code) {
        String ali_pub = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgkkRH8D6KzdzUtUY4Rhpog7bjcFPMY6+Uu3OVT7QEmsDmkQ6u56qxG6RGLKe9id8au1cGtj/j8NFnhfnEiwdbfrQs9cgXUKwDneLPaIBkefIwXGK7zk/2O0uggyXcBCkfniBJsBsrGvv8cOUyv1c0DhaUGRaY0ob2RQ9zGSL/967nk97xt9/fMGFVzzLCQ+7+5SRstr7KPapX+Mf+f8yPMV7r/DLf9gCjzTuKvAtu6B8nPIJCzQeEXgWWHIQ9MNRQsqBJkGUpujpPJxHOQvIDN1j5t1RStzOw631LTY4hU6/DhTZuXxZ14GnESwzbur6OmI8LlVDjfJ67Y14v2sE7wIDAQAB";
        String pri = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDISwBNMJD9CcjeuJOA+ICTz6Wnjf8jVh6zq6d5LeG2Gml8oQ01A8Qibiv7pFvu/JweP8Rz45h/9heTlcajJQFxEge0JwfPg5jy+fQp+A4R9tQybdFub3V+1t70IDC9Cd4/JM+SptkCjc6Gz128yZdQNMb0AQSs0CAkGavsDHEg/soqwdRn48wYXv56UBCRytPyVj/jMvn2XEuL3w3WTNxvSTg05j4dm0RqFwW6v3nszKFqQH+6Sl95EBTXjO2SvD2TdwW3jQF8x95kciQ+pZf97cnk7ag/Sxia996fHat+GtFlKvoGnxjjMrsmX9paI3+o6mgrH+d6Z9rdarbL3gALAgMBAAECggEAdWKHbbW9856ta+KPCDIb4QT8WAO/eO07Fx/OChYax4gZNKKKw/xOEcOx4UxFWP4RXSUQNL85nB0VWjiYbvr8KlFf1HRffn3owNY/A+3OCTc4y8lSyb9nBAxYJIlNJy1GZIllYdSJMJb9aELV9BPNq4MS6uRB4ioj/CRTfwcXsja3m/k+BEP3ER4euGAM6+R19KwlUWm0FpgPIuOM5IZbfKVKzrhx5xwmzvCmkC5zlfPgz7Qt+gCn5fKxPHts9InB45zFqruaDm57cgg0xA4pF7dbUVV8ax61DGc8ZfnO9EjnJqLShGgRmzds8kPIACMxAByO8WJIg52yVCMnSD3qmQKBgQDqsq4n+kIUPce8mwYaQOKSy3gRilb2Fo2CZIppEH4oGHG2AzRUGEtO1R/bxNKg1+kvDBMXbAfFQZKpHGo//YI2NMXkiUmiEokVZPiho2XivjafvwtqTIOrILTqh+zPMgoBZZHrS2PTFaWwZelqs8l8aqVCrXOzjrcI6j4iqMx+XwKBgQDaeOeBA9mW68kbUWWj74wp7E0KssqiyBzBhb5ybIo/QNk0hNcpSD/nUxrCRdHXr5bQHYhXcsz75YFU/sMJnWuFYL2IEQqkCDRjboEYrs+6AjNDEiSdYBKTWRSsHNrVoaPQrLetDzU750IO+Q7P3ihBugEJDtZOlO38dFIWSl8F1QKBgQCOLOHtm9/tzSMzcSH4Q3g9/v5kec6GU1E0PV3Pmcsi1PQFk9leXD06q0caZhhGkaVT99eU57+pUjjbMzh2PUdlOQDsmfI+OUZL7Jflq0tsWVhT23K52VImF7EhJmhhd+mxwOrthAUb+VAfFQ2aswEK2rv0MYkWrAzbXLN5YZJHtwKBgEH88NfsJMXMFCNXM58PlMN451BK7p+6V80wK3T+ScePO8v8L8z9UKf7VwLE+fvuwlu7/9mCCF4xVR4qERkPXlGcBcNael9PRxudXgrpij7BuWAR09NhGFs/NdpCoOqT9xnpgVUZWdftIuvI2Gt6y6gGmZd1zs9Pdo6hR8YJ0qe9AoGBAMiW3I4kXzAuYBvoAqQaQ84DxrGQsOOvNLMxsB2flFkYyTdxm78nUB8QWsHp53nnhNMDcDybZpNXELLHFqrv1YoOfvQPo57tG1MQp8bh8p+oiCGzr5TAjq1pSpRa1FppytE37QQWYaPAV9IOCOTDM1z9ARoRZ9YyqBCb72QHoMJz";

        AlipayClient client = new DefaultAlipayClient(
                "https://openapi.alipaydev.com/gateway.do",
                "2021000117601007",
                pri,
                "json",
                "UTF-8",
                ali_pub,
                "RSA2");

        log.info("code---------------------:" + code);

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
