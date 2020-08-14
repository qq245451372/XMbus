package com.example.xmbus.bus;

import java.lang.reflect.Method;

public class MethodParams {
    private Method method ;
    private Class<?> type;
    private ThreadMode threadMode;

    public ThreadMode getThreadMode() {
        return threadMode;
    }

    public void setThreadMode(ThreadMode threadMode) {
        this.threadMode = threadMode;
    }

    public MethodParams(Method method, Class<?> type,ThreadMode threadMode)
    {
        this.method = method;
        this.type = type;
        this.threadMode = threadMode;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }
}
