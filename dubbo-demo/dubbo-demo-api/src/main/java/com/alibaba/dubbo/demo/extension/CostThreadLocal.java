package com.alibaba.dubbo.demo.extension;

/**
 * Created by lenovo on 2015/8/29.
 */
public class CostThreadLocal {

    private final static ThreadLocal<Long> studentLocal = new ThreadLocal<Long>();

    public static void setCurrent() {
        studentLocal.set(System.currentTimeMillis());
    }

    public static Long get() {
        return studentLocal.get();
    }
}
