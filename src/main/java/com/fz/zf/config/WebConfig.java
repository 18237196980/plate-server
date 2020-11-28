package com.fz.zf.config;

import com.ex.framework.util.Lang;
import com.fz.zf.config.app.AuthInterceptor;
import com.fz.zf.config.app.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.util.List;

/**
 * 使注解@RecordBody生效
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    PropertiesBean propertiesBean;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {

        //添加@RecordBody解析
        resolvers.add(new RecordResolver());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String downMapping = "/download/**";

        //添加下载地址映射，需要在shiro中开启此地址访问
        if (!registry.hasMappingForPattern(downMapping)) {
            registry.addResourceHandler(downMapping)
                    .addResourceLocations(getDownloadFilePath());
        }

    }

    private String getDownloadFilePath() {
        String uploadPath = propertiesBean.uploadPath;
        if (!uploadPath.endsWith(File.separator)) {
            uploadPath += File.separator;
        }

        //String filePath = "file:F:\temp\";
        String filePath = "file:" + uploadPath;
        return filePath;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean b = new FilterRegistrationBean();
        b.addUrlPatterns("/*");
        b.setFilter(new CorsFilter());

        return b;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> list = Lang.list(
                "/user/login",
                "/user/register",
                "/download/**",
                "/plate/user/loginByPwd",
                "/plate/user/loginByPhone",
                "/plate/user/loginWeiXin",
                "/plate/user/register",
                "/plate/me/getPhoneCode");
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(list);
    }

}
