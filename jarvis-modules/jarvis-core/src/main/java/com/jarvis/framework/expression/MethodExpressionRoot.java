package com.jarvis.framework.expression;

import java.lang.reflect.Method;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年7月21日
 */
public class MethodExpressionRoot {

    private Object target;

    private Method method;

    private Object[] args;

    private Object result;

    private Throwable error;

    /**
     * @return the target
     */
    public Object getTarget() {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(Object target) {
        this.target = target;
    }

    /**
     * @return the method
     */
    public Method getMethod() {
        return method;
    }

    /**
     * @param method the method to set
     */
    public void setMethod(Method method) {
        this.method = method;
    }

    /**
     * @return the args
     */
    public Object[] getArgs() {
        return args;
    }

    /**
     * @param args the args to set
     */
    public void setArgs(Object[] args) {
        this.args = args;
    }

    /**
     * @return the result
     */
    public Object getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(Object result) {
        this.result = result;
    }

    /**
     * @return the error
     */
    public Throwable getError() {
        return error;
    }

    /**
     * @param error the error to set
     */
    public void setError(Throwable error) {
        this.error = error;
    }

}
