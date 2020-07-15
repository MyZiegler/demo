package com.wpw.demo.enums;

import lombok.Getter;

/**
 * 用户来源
 *
 * @author limengyang
 * create  2019-01-16
 **/

@Getter
public enum UserSourceEnum {

    USER_MANAGER(1, "系统用户");

    /**
     * 用户来源
     */
    private int source;

    /** 描述信息 */
    private String desc;

    UserSourceEnum(int source, String desc) {
        this.source = source;
        this.desc = desc;
    }

}
