package com.wpw.demo.pojo;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * payload字段
 **/
@Setter
@Getter
public class JWTPayload {


    /**
     * 签发人 暂时不用
     */
    public String iss;

    /**
     * 过期时间
     */
    public long exp;

    /**
     * 主题 暂时不用
     */
    public String sub;

    /**
     * 受众 ZT  LK
     */
    public String aud;

    /**
     * 生效时间 暂时不用
     */
    public long nbf;

    /**
     * 签发时间
     */
    public long iat = System.currentTimeMillis();

    /**
     * 编号
     */
    public String jti;


    /* *************下面为额外添加的字段 字段名称尽量简写********************** */

    /**
     * 签发的jwt 版本
     */
    public String v;

    /**
     * 用户ID
     */
    public String uid;


    public JWTPayload() {
    }


    public JWTPayload(long exp, String aud, String uid, String v) {
        this.aud = aud;
        this.exp = this.iat + exp;
        this.uid = uid;
        this.v = v;
    }


    public Map<String, Object> makeMap() {
        Preconditions.checkArgument(exp > iat, "过期时间必须大于签发时间");
        Preconditions.checkArgument(StringUtils.isNotBlank(aud), "签发的对象不存在");
        Preconditions.checkArgument(StringUtils.isNotBlank(uid), "用户ID不存在");
        Preconditions.checkArgument(StringUtils.isNotBlank(v), "签发版本不存在");

        Map<String, Object> map = Maps.newHashMap();
        if (StringUtils.isNotBlank(this.iss)) map.put("iss", this.iss);
        if (StringUtils.isNotBlank(this.sub)) map.put("sub", this.sub);
        if (nbf > iat) map.put("nbf", this.nbf);
        if (StringUtils.isNotBlank(this.jti)) map.put("jti", this.jti);
        map.put("exp", exp);
        map.put("aud", aud);
        map.put("iat", iat);
        map.put("v", v);
        map.put("uid", uid);
        return map;
    }


}
