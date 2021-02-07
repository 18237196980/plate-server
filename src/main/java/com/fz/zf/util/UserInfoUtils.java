package com.fz.zf.util;

import com.fz.zf.model.common.Constast;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class UserInfoUtils {

    public static String getCurrentUserId() {
        // header中取uid字段
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            return request.getHeader(Constast.UID);
        }
        return "";
    }
}
