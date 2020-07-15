package com.wpw.demo.aop;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wpw.demo.annotation.IgnoreLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 打印参数日志
 **/
@Aspect
@Component
public class ParamAspect {

    public static String DATE_FULL_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final Logger LOGGER = LoggerFactory.getLogger(ParamAspect.class);

    ParamAspect() {
        LOGGER.warn("### param ####");
    }

    @Pointcut("execution(* com.wpw.demo.controller..*.*(..)) || execution(* com.wpw.demo.service..*.*(..))")
    private void printParam() {
    }

    private void  getRequestInfo(){
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        StringBuffer url = request.getRequestURL();
        LOGGER.info("请求信息:");
        // request.getHeader("");
    }

    @Around("com.wpw.demo.aop.ParamAspect.printParam()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        getRequestInfo();
        String params = "";
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Class<?> clazz = method.getDeclaringClass();

            params = clazz.getSimpleName() + "-" + method.getName() + " ###  参数列表: ";
            //如果存在参数
            if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
                try {
                    Object[] args = joinPoint.getArgs();
                    Parameter[] parameter = method.getParameters();
                    for (int i = 0, size = parameter.length; i < size; i++) {
                        Parameter param = parameter[i];
                        if (param.isAnnotationPresent(IgnoreLog.class) || isIgnore(param.getType())) {
                            args[i] = null;
                        }
                    }
                    ObjectMapper objectMapper  =new ObjectMapper();
                    params = params +   objectMapper.writeValueAsString(args);
                   // params = params + JSONObject.toJSONStringWithDateFormat(args, DateUtils.DATE_FULL_FORMAT, SerializerFeature.WriteMapNullValue);
                } catch (Exception e) {
                    try {
                        params = params + getParamStr(joinPoint);
                    } catch (Exception e1) {
                        LOGGER.error("获取参数错误 {}", e1.getMessage());
                    }
                }
            }
            long start = System.currentTimeMillis();
            Object result = joinPoint.proceed();
            long end = System.currentTimeMillis() - start;
            //参数结果
            String rs = "";

            if (!method.getReturnType().equals(Void.TYPE) && !method.isAnnotationPresent(IgnoreLog.class)
                    && result != null) {
                try {
                    rs = JSONObject.toJSONStringWithDateFormat(result, DATE_FULL_FORMAT, SerializerFeature.WriteMapNullValue);
                } catch (Exception e) {
                    try {
                        rs = result.toString();
                    } catch (Exception e1) {
                        LOGGER.error("获取参数错误 {}", e1.getMessage());
                    }
                }
            }
            if ("".equals(rs)) {
                LOGGER.info("\n---> {}|T={}ms", params, end);
            } else {
                LOGGER.info("\n---> {}\n<--- {}|T={}ms", params, rs, end);
            }
            return result;
        } catch (Throwable e) {
            LOGGER.error("error={}, param={}", e.getMessage(), params);
           // throw e;
            return  null;
        }
    }


    /**
     * 获取参数列表
     */
    private String getParamStr(ProceedingJoinPoint joinPoint) {
        String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        Class[] types = ((CodeSignature) joinPoint.getSignature()).getParameterTypes();
        Object[] objects = joinPoint.getArgs();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < paramNames.length; i++) {
            if (!types[i].isAnnotationPresent(IgnoreLog.class) && !isIgnore(types[i])) {
                sb.append(paramNames[i]);
                sb.append("=");
                sb.append(objects[i]);
                sb.append(",");
            }
        }
        return sb.toString();
    }

    /**
     * 忽略参数类型
     */
    private boolean isIgnore(Class<?> clazz) {
        return clazz.equals(BindingResult.class) || clazz.equals(HttpServletRequest.class) || clazz.equals(HttpServletResponse.class);
    }


    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     *
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     *
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     *
     * 用户真实IP为： 192.168.1.110
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}

