package com.fz.zf.config.xss;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * XSS过滤：如 <script>for(var i=0;i<3;i++){alert("弹死你"+i);}</script>
 */
public class XSSEscapeFilter implements Filter {

    public List<String> excludes = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            XssHttpServletRequestWrapper xssRequestWrapper = new XssHttpServletRequestWrapper(httpServletRequest);

            if (handleExcludeURL(httpServletRequest)) {
                chain.doFilter(request, response);
                return;
            }

            //进行XSS处理
            chain.doFilter(xssRequestWrapper, response);
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean handleExcludeURL(HttpServletRequest request) {

        if (excludes == null || excludes.isEmpty()) {
            return false;
        }

        String url = request.getServletPath();
        for (String pattern : excludes) {
            Pattern p = Pattern.compile("^" + pattern);
            Matcher m = p.matcher(url);
            if (m.find()) {
                return true;
            }
        }

        return false;
    }

}
