package com.fz.zf.pay.zfb;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ex.framework.data.IDUtils;
import com.ex.framework.data.Record;
import com.ex.framework.data.RecordBody;
import com.ex.framework.util.Lang;
import com.fz.zf.config.PropertiesBean;
import com.fz.zf.mapper.Q;
import com.fz.zf.model.app.GoodOrder;
import com.fz.zf.model.app.SysAdmin;
import com.fz.zf.service.app.GoodOrderService;
import com.fz.zf.service.app.SysAdminService;
import com.fz.zf.util.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/ali")
@RestController
@Slf4j
@SuppressWarnings("all")
public class AlipayController {
    @Autowired
    PropertiesBean prop;
    @Autowired
    SysAdminService sysAdminService;
    @Autowired
    GoodOrderService goodOrderService;

    /**
     * 支付宝 app支付
     *
     * @param record
     * @return
     */
    @PostMapping("getPerOrderId")
    public ApiResult getPerOrderId(@RecordBody Record record) {
        String order_num = record.getString("order_num");
        String order_amount = record.getString("order_amount");

        GoodOrder goodOrder = goodOrderService.get(Q.GoodOrder()
                                                    .eq("order_num", order_num)
                                                    .eq("status", 1));
        boolean exists = goodOrderService.exists(Q.GoodOrder()
                                                  .eq("order_num", order_num));
        if (!exists) {
            return ApiResult.error("订单不存在");
        }
        if (goodOrder != null) {
            log.info("订单已经支付过--------------");
            return ApiResult.error("订单已经支付过");
        }

        if (StringUtils.isEmpty(order_amount)) {
            log.info("支付金额为空----------------------------");
        }

        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(prop.alipayGateway, prop.alipayAppAppid, prop.alipayAppPrivateKey, "json", "UTF-8", prop.alipayAppPublicKey, "RSA2");
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("测试测试");
        model.setSubject("App支付测试");
        model.setOutTradeNo(order_num);
        model.setTimeoutExpress("30m");
        model.setTotalAmount(order_amount);
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl("http://39.99.161.198:9876/ali/notifyOrder");
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            String pid = response.getBody();
            Record r = Record.build()
                             .set("pid", pid)
                             .set("orderId", order_num);
            log.info("获取支付宝userid:" + pid); // pid 可以直接给客户端请求，无需再做处理。
            return ApiResult.success(r);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return ApiResult.error();
        }
    }

    /**
     * 支付宝的支付结果回调接口
     *
     * @param request
     */
    @RequestMapping("/notifyOrder")
    public String notifyOrderInfo(HttpServletRequest request) {
        String tradeStatus = request.getParameter("trade_status");

        log.info("进入回调通知------------------------------------------");
        if ("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISH".equals(tradeStatus)) {
            log.info("TRADE_SUCCESS------------------------------------------");

            Enumeration<?> pNames = request.getParameterNames();
            Map<String, String> param = new HashMap<String, String>();
            try {
                while (pNames.hasMoreElements()) {
                    String pName = (String) pNames.nextElement();
                    param.put(pName, request.getParameter(pName));
                }

                boolean signVerified = AlipaySignature.rsaCheckV1(param, prop.alipayAppPublicKey,
                        AlipayConstants.CHARSET_UTF8, "RSA2"); // 校验签名是否正确

                if (signVerified) {
                    // TODO 验签成功后
                    // 按照支付结果异步通知中的描述，对支付结果中的业务内容进行1\2\3\4二次校验，校验成功后在response中返回success，校验失败返回failure
                    System.out.println("订单支付成功：" + JSON.toJSONString(param));

                    log.info("可以根据订单id更新订单状态了--------------");
                    String orderId = param.get("out_trade_no");
                    GoodOrder goodOrder = goodOrderService.get(Q.GoodOrder()
                                                                .eq("order_num", orderId)
                                                                .eq("status", 0));
                    if (goodOrder == null) {
                        log.info("订单不存在--------------");
                        return "failure";
                    } else {
                        goodOrder.setStatus(1);
                        goodOrderService.updateById(goodOrder);
                        return "success";
                    }
                } else {
                    // TODO 验签失败则记录异常日志，并在response中返回failure.
                    log.info("验签失败--------");
                    return "failure";
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.info("验签失败2--------");
                return "failure";
            }
        }
        return "failure";
    }

    /**
     * 支付宝 app查询订单支付状态(主动去支付宝查询支付结果)
     *
     * @param record
     * @return
     */
    @PostMapping("queryAppOrder")
    public ApiResult queryAppOrder(@RecordBody Record record) {
        String orderId = record.getString("orderId");

        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(prop.alipayGateway, prop.alipayAppAppid, prop.alipayAppPrivateKey, "json", "UTF-8", prop.alipayAppPublicKey, "RSA2");

        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent("{" +
                "\"out_trade_no\":\"" + orderId + "\"," +
                "      \"query_options\":[" +
                "        \"trade_settle_info\"" +
                "      ]" +
                "  }");
        try {
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                String tradeStatus = response.getTradeStatus();
                log.info("tradeStatus：----------" + tradeStatus);

                if ("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISH".equals(tradeStatus)) {
                    log.info("查询订单[{}]支付成功", orderId);
                    log.info("可以根据订单id更新订单状态了--------------");
                    GoodOrder goodOrder = goodOrderService.get(Q.GoodOrder()
                                                                .eq("order_num", orderId));
                    if (goodOrder == null) {
                        return ApiResult.error("订单不存在");
                    } else {
                        goodOrder.setStatus(1);
                        goodOrderService.updateById(goodOrder);
                        return ApiResult.success();
                    }
                } else {
                    return ApiResult.error("查询订单失败");
                }
            } else {
                log.info("调用失败");
                return ApiResult.error();
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return ApiResult.error();
    }

    @PostMapping("getOpenid")
    public ApiResult getOpenid(String code) {
        AlipayClient client = new DefaultAlipayClient(
                prop.alipayGateway,
                prop.alipayXcxAppid,
                prop.alipayXcxPrivateKey,
                "json",
                "UTF-8",
                prop.alipayXcxPublicKey,
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

    /**
     * 查询订单列表
     *
     * @param record
     * @return
     */
    @PostMapping("getOrderList")
    public ApiResult getOrderList(@RecordBody Record record) {
        String uid = record.getString("uid");
        List<GoodOrder> list = Lang.list();
        if (StringUtils.isEmpty(uid)) {
            list = goodOrderService.list();
        } else {
            list = goodOrderService.list(Q.GoodOrder()
                                          .eq("uid", uid));
        }
        return ApiResult.success(list);
    }
}
