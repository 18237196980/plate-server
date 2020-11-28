package com.fz.zf.config;

import com.ex.framework.data.Record;
import com.ex.framework.data.RecordBody;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 使注解@RecordBody生效
 * RecordResolver
 * 注意：Record不能继承Map，否则Spring优先使用内置的Map处理器MapMethodProcessor
 */
public class RecordResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {

        RecordBody recordBody = methodParameter.getParameterAnnotation(RecordBody.class);
        return recordBody != null;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) {

        RecordBody recordBody = methodParameter.getParameterAnnotation(RecordBody.class);
        if (recordBody == null)
            return null;

        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        String method = request.getMethod();
        if (StringUtils.isEmpty(method))
            return null;

        method = method.toLowerCase();
        if (StringUtils.equals(method, "get")) {
            return processQueryString(request);
        } else {
            String contentType = request.getHeader("content-type")
                                        .toLowerCase();

            //application/x-www-form-urlencoded
            if (StringUtils.contains(contentType, "form")) {
                //form提交
                return processFormSubmit(request);
            }

            //application/json
            if (StringUtils.contains(contentType, "json")) {
                //json提交
                Record body = processJsonSubmit(request);

                //url参数
                Record urlParams = processQueryString(request);

                //如果是Post Json，那么同时解析url参数和json，一起返回到一个Record中
                return body.append(urlParams);
            }

            //multipart/form-data, text/xml这两种不处理
            return null;
        }
    }


    /**
     * 处理Url中的参数
     *
     * @param request
     * @return
     */
    private Record processQueryString(HttpServletRequest request) {
        //从parameters解析
        Map<String, Object> map = new HashMap<>();
        Enumeration paramNames = request.getParameterNames();
        while ((paramNames != null) && paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] values = request.getParameterValues(paramName);

            if (values == null || values.length == 0) {
                //值为空的是否也加入？
                map.put(paramName, null);
                continue;
            }

            //参数值只有1个
            if (values.length == 1) {
                map.put(paramName, values[0]);
                continue;
            }

            //参数值有多个，直接把参数值数组放进去，由使用者解析
            map.put(paramName, values);
        }

        Record record = Record.fromMap(map);
        if(record == null)
            record = new Record();

        return record;
    }

    /**
     * 处理Form提交数据
     *
     * @param request
     * @return
     */
    private Record processFormSubmit(HttpServletRequest request) {

        //一般Form提交，application/x-www-form-urlencoded，也是拼接成QueryString在Url中
        return processQueryString(request);
    }

    /**
     * 处理Json提交数据
     *
     * @param request
     * @return
     */
    private Record processJsonSubmit(HttpServletRequest request) {
        Record record = null;
        try {
            //从body解析
            String body = IOUtils.toString(request.getInputStream(), "utf-8");
            record = Record.fromJson(body);
        } catch (IOException e) {

        }

        if (record == null)
            record = new Record();

        return record;
    }
}
