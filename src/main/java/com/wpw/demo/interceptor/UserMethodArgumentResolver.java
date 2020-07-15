package com.wpw.demo.interceptor;

import com.wpw.demo.annotation.CurrentUser;
import com.wpw.demo.consts.UserConsts;
import com.wpw.demo.entity.UserInfo;
import com.wpw.demo.exception.RestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * 登录参数解析
 *
 * @author limengyang
 * create  2019-01-21
 **/
@Component
public class UserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Value("${user.username}")
    private String username;

    @Value("${user.password}")
    private String password;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserMethodArgumentResolver.class);

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return (methodParameter.getParameterType().isAssignableFrom(UserInfo.class) && methodParameter.hasParameterAnnotation(CurrentUser.class));
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        //处理登录用户
        Object userId = nativeWebRequest.getAttribute(UserConsts.PLATFORM_USERID, RequestAttributes.SCOPE_REQUEST);
        if (userId != null) {
            if (!username.equals(userId)) {
                LOGGER.error("platformUserId={}", userId);
                throw new RestException(404, "登录用户不存在");
            }
            UserInfo userInfo = new UserInfo();
            userInfo.setUserName( String.valueOf(userId));
            userInfo.setPassword( String.valueOf(password));
            return userInfo;
        }
        throw new MissingServletRequestPartException("用户ID属性不存在 检查是否在类或方法上使用ExcludeAccessToken");
    }

}
