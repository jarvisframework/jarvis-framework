package com.jarvis.framework.core;


/**
 * @author Administrator
 */

public enum BusCodeEnum implements IBusCode {

    /**
     * 成功（1000）
     */
    SUCCESS(1000, "成功"),
    /**
     * 业务失败（1001）
     */
    FAIL(1001, "业务失败"),

    /**
     * 无效token（1002）
     */
    TOKEN_INVALID(1002, "无效token"),

    /**
     * 未获取到token（1003）
     */
    TOKEN_LOSE(1003, "未获取到token"),

    /**
     * token过期（1004）
     */
    TOKEN_EXPIRATION(1004, "token过期"),

    /**
     * 权限不足（1005）
     */
    PERMISSION_DENIED(1005, "权限不足"),

    /**
     * 签名无效（1006）
     */
    INVALID_SIGNATURE(1006, ""),

    /**
     * 参数绑定失败（1007）
     */
    PARAMETER_BIND_ERROR(1007, "参数绑定失败"),
    /**
     * 参数验证失败（1008）
     */
    PARAMETER_VALID_ERROR(1008, "参数验证失败"),

    /**
     * 重复提交（1100）
     */
    REPEAT_SUBMIT(1100, "重复提交"),

    /**
     * 无效的资源（2001）
     */
    ILLEGALARGUMENT(2001, "无效的资源"),

    /**
     * 资源被关联（2002）
     */
    ASSOCIATIVE(2002, "资源被关联"),

    /**
     * 无效请求地址（2003）
     */
    NOT_FOUND(2003, "无效请求地址"),

    /**
     * 无效的枚举类型（2004）
     */
    INVALID_ENUM(2004, "无效的枚举类型"),

    /**
     * 违反唯一约束（2005）
     */
    UNIQUE_ERROR(2005, "违反唯一约束"),

    /**
     * 已在其他设备登录(5000)
     */
    OTHER_LOGIN_ERROR(5000, "已在其他设备登录"),

    /**
     * 服务器异常(9999)
     */
    SYS_EXP(9999, "未知异常，请联系管理员！");


    private int code;

    private String msg;


    BusCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 方法描述: 枚举转换
     *
     * @param code code
     * @return BusCode BusCode
     */
    public static IBusCode parseOf(int code) {
        for (BusCodeEnum item : values()) {
            if (item.getCode() == code) {
                return item;
            }
        }

        return null;
    }

    public static IBusCode parseOf(String key) {
        return BusCodeEnum.parseOf(Integer.parseInt(key));
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessages() {
        return this.msg;
    }

    @Override
    public String toString() {
        return "BusCode{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
