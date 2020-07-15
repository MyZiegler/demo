package com.wpw.demo.utils;

import com.google.common.collect.Maps;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.wpw.demo.enums.UserSourceEnum;
import com.wpw.demo.pojo.JWTPayload;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * jwt token
 **/
public class JwtUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);

    /**
     * 密钥
     */
    private static final byte[] SECRET = "63508e7a50f642349ce971956bec3ade".getBytes();

    /**
     * 过期时间
     */
    private static final int EXPIRE_TIME = 60 * 60 * 1000 * 24;

    /**
     * 可用  过期  不可用
     */
    public static final int VALID = 1;
    public static final int EXPIRED = 2;
    public static final int INVALID = 3;

    /**
     * 生成乐卡的TOKEN
     */
    public static String createUserToken(String uid) {
        JWTPayload payload = new JWTPayload(EXPIRE_TIME, UserSourceEnum.USER_MANAGER.name(), uid, "v1");
        return createToken(payload.makeMap());
    }



    /**
     * 生产jwt token
     */
    private static String createToken(Map<String, Object> map) {
        //header
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256).type(JOSEObjectType.JWT).build();
        // sign
        Payload payload = new Payload(new JSONObject(map));
        JWSObject jwsObject = new JWSObject(header, payload);
        JWSSigner jwsSigner = null;
        try {
            jwsSigner = new MACSigner(SECRET);
            jwsObject.sign(jwsSigner);
        } catch (JOSEException e) {
            LOGGER.error("生成jwt token 异常", e);
            throw new RuntimeException(e);
        }
        String token = jwsObject.serialize();
        LOGGER.info("生成token={}", token);
        return token;
    }


    /**
     * 验证token
     * {"data": "{}", "state":1}
     */
    public static Map<String, Object> validToken(String token) {
        Map<String, Object> resultMap = Maps.newHashMap();
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            Payload payload = jwsObject.getPayload();
            JWSVerifier verifier = new MACVerifier(SECRET);
            if (jwsObject.verify(verifier)) {
                JSONObject payloadJson = payload.toJSONObject();
                resultMap.put("state", VALID);
                if (payloadJson.containsKey("exp")) {
                    long extTime = Long.valueOf(payloadJson.get("exp").toString());
                    long curTime = System.currentTimeMillis();
                    if (curTime > extTime) {
                        resultMap.clear();
                        resultMap.put("state", EXPIRED);
                    }
                }
                resultMap.put("data", payloadJson);
            } else {
                resultMap.put("state", INVALID);
            }
        } catch (Exception e) {
            LOGGER.error("JWT格式异常", e);
            resultMap.clear();
            resultMap.put("state", INVALID);
        }
        return resultMap;
    }

}
