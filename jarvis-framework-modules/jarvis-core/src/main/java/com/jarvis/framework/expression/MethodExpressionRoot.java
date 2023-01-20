package com.jarvis.framework.expression;

import java.lang.reflect.Method;

public class MethodExpressionRoot {
    private Object target;
    private Method method;
    private Object[] args;
    private Object result;
    private Throwable error;

    public MethodExpressionRoot() {
    }

    public Object getTarget() {
        return this.target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Method getMethod() {
        return this.method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return this.args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Object getResult() {
        return this.result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Throwable getError() {
        return this.error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
