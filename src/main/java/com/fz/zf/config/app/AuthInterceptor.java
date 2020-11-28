package com.fz.zf.config.app;

import com.ex.framework.util.SpringUtils;
import com.fz.zf.model.app.SysAdmin;
import com.fz.zf.model.common.Constast;
import com.fz.zf.service.app.SysAdminService;
import com.fz.zf.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * app请求鉴权token
 */
@Slf4j
@SuppressWarnings("all")
public class AuthInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(Constast.TOKEN);
        String uid = request.getHeader(Constast.UID);

        if (StringUtils.isEmpty(uid)) {
            throw new AuthException("请先登录");
        }
        if (StringUtils.isEmpty(token)) {
            throw new AuthException("token不能为空");
        }
        if (!validToken(uid, token)) {
            throw new AuthException("token过期");
        }

        return true;
    }

    // 校验token
    private boolean validToken(String uid, String token) {
        SysAdminService adminService = SpringUtils.getBean(SysAdminService.class);
        SysAdmin admin = adminService.get(uid);
        if (admin == null) {
            return false;
        }

        RedisUtil redisUtil = SpringUtils.getBean(RedisUtil.class);
        String redis_token = (String) redisUtil.get(Constast.ZF_TOKEN + uid);
        long old_time = redisUtil.getExpire(Constast.ZF_TOKEN + uid);

        log.info("----------------前端传过来token----------------:" + token);
        log.info("----------------redis取出token-----------------:" + redis_token);
        log.info("----------------token再有-----------------:" + old_time + "s过期");

        if (StringUtils.isEmpty(redis_token)) {
            return false;
        } else {
            if (StringUtils.equals(redis_token, token)) {
                // 如果token没有过期，此时延长token有效期
                redisUtil.set(Constast.ZF_TOKEN + uid, token, Constast.TOKEN_EXPIRE);
                return true;
            }
        }
        return false;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }

}
