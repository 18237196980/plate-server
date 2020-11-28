package com.fz.zf.app;

import com.ex.framework.data.Record;
import com.ex.framework.util.mapper.JsonMapper;
import com.ex.framework.web.ExPage;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BaseController {

    /**
     * 默认的错误页面
     */
    protected static final String DEFAULT_ERROR_VIEW = "error";

    /**
     * 默认的Form错误页面
     */
    protected static final String DEFAULT_ERROR_FORM = "error/error-form";

    /**
     * 获取当前Request
     *
     * @return HttpServletRequest
     */
    protected HttpServletRequest getCurrentRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    /**
     * 获取当前Session
     *
     * @return HttpSession
     */
    protected HttpSession getCurrentSession() {
        HttpServletRequest request = getCurrentRequest();
        if (request == null)
            return null;

        HttpSession session = request.getSession();
        return session;
    }


    protected ExPage parseExPage(Record record) {
        if (record == null) {
            return new ExPage(1, 10, "id", "asc");
        }

        Integer pageNumber = record.tryGetInteger("pageNumber", 1);
        Integer pageSize = record.tryGetInteger("pageSize", 10);
        String sort = record.getString("sort", "id");
        String order = record.getString("order", "asc");

        return new ExPage(pageNumber, pageSize, sort, order);
    }

    protected String toJson(Object object) {
        return JsonMapper.defaultMapper()
                         .toJson(object);
    }

    protected <T> T fromJson(String json, Class<T> clazz) {
        return JsonMapper.defaultMapper()
                         .fromJson(json, clazz);
    }
}
