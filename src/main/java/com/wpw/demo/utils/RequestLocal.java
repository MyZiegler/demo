package com.wpw.demo.utils;

/**
 * 当前线程持有用户的ID
 **/
public class RequestLocal {

    private static ThreadLocal<String> userLocal = new ThreadLocal<>();

    public static String getUid() {
        return userLocal.get();
    }

    public static void setUid(String uid) {
        userLocal.set(uid);
    }

    public static void remove() {
        userLocal.remove();
    }
}
