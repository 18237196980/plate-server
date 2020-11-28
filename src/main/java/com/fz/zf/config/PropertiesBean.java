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
    @Value("${appID}")
    public String appID;

    /**
     * appSecret
     */
    @Value("${appSecret}")
    public String appSecret;

}
