package com.fz.zf.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertiesBean {

    /**
     * 文件上传路径
     */
    @Value("${upload.file.path}")
    public String uploadPath;

    /**
     * 应用部署的url
     */
    @Value("${site.url}")
    public String siteUrl;

    /**
     * appID
     */
    @Value("${appid}")
    public String appid;

    /**
     * appSecret
     */
    @Value("${secret}")
    public String secret;

    /**
     * 支付宝gateway
     */
    @Value("${alipay-gateway}")
    public String alipayGateway;
    /**
     * 支付宝小程序appid
     */
    @Value("${alipay-xcx-appid}")
    public String alipayXcxAppid;
    /**
     * 支付宝小程序公钥
     */
    @Value("${alipay-xcx-public-key}")
    public String alipayXcxPublicKey;
    /**
     * 支付宝小程序应用私钥：
     */
    @Value("${alipay-xcx-private-key}")
    public String alipayXcxPrivateKey;

    /**
     * app支付宝支付appid
     */
    @Value("${alipay-app-appid}")
    public String alipayAppAppid;
    /**
     * 支付宝app支付公钥
     */
    @Value("${alipay-app-public-key}")
    public String alipayAppPublicKey;
    /**
     * 支付宝app支付应用私钥：
     */
    @Value("${alipay-app-private-key}")
    public String alipayAppPrivateKey;

    /**
     * 微信公众号token
     */
    @Value("${gzh-token}")
    public String gzhToken;

    /**
     * 微信公众号appid
     */
    @Value("${gzh-appid}")
    public String gzhAppid;

    /**
     * 微信公众号appsecret
     */
    @Value("${gzh-appsecret}")
    public String gzhAppsecret;

}
