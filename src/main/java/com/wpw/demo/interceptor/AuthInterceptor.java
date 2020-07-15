package com.wpw.demo.interceptor;


import com.wpw.demo.annotation.ExcludeAccessToken;
import com.wpw.demo.consts.UserConsts;
import com.wpw.demo.enums.UserSourceEnum;
import com.wpw.demo.exception.RestException;
import com.wpw.demo.utils.JwtUtil;
import com.wpw.demo.utils.RequestLocal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 验证用户jwtToken
 *
 * @author limengyang
 * create  2019-01-21
 **/
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private static final String ACCESS_TOKEN = "AccessToken";
//
//    @Resource
//    private RedisService redisService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        ExcludeAccessToken token = handlerMethod.getBeanType().getAnnotation(ExcludeAccessToken.class);
        if (token != null) {
            return true;
        }
        token = method.getAnnotation(ExcludeAccessToken.class);
        if (token != null) {
            return true;
        }
        String accessToken = request.getHeader(ACCESS_TOKEN);

        if (StringUtils.isBlank(accessToken)) {
            throw new RestException(1100, "AccessToken为空，验证不通过!");
        }

        //校验token
        Map<String, Object> tokenMap = JwtUtil.validToken(accessToken);
        int state = Integer.parseInt(tokenMap.get("state").toString());
        if (state == JwtUtil.VALID) {
            Map<String, Object> dataMap = (Map<String, Object>) tokenMap.get("data");
            String aud = String.valueOf(dataMap.get("aud"));
            String uid = String.valueOf(dataMap.get("uid"));

            if (StringUtils.isBlank(aud) || StringUtils.isBlank(uid)) {
                throw new RestException(1100, "AccessToken为空，验证不通过!");
            }
            if (UserSourceEnum.USER_MANAGER.name().equals(aud)) {
                request.setAttribute(UserConsts.PLATFORM_USERID, uid);
            }
            request.setAttribute("uid", uid);
            RequestLocal.setUid(uid);
            return true;
        }

        //过期
        if (state == JwtUtil.EXPIRED) {
            throw new RestException(1100, "AccessToken为空，验证不通过!");
        }

        // 验证不通过
        if (state == JwtUtil.INVALID) {
            throw new RestException(1100, "AccessToken为空，验证不通过!");
        }
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestLocal.remove();
    }






}
