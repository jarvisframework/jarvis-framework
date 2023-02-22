package com.jarvis.framework.core.exception;

/**
 * 业务处理异常统一使用类
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月27日
 */
public class BusinessException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 5057887406611670380L;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message. The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public BusinessException() {
        super();
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *            later retrieval by the {@link #getMessage()} method.
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause. <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *            by the {@link #getMessage()} method).
     * @param cause the cause (which is saved for later retrieval by the
     *            {@link #getCause()} method). (A <tt>null</tt> value is
     *            permitted, and indicates that the cause is nonexistent or
     *            unknown.)
     * @since 1.4
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new runtime exception with the specified cause and a
     * detail message of <tt>(cause==null ? null : cause.toString())</tt>
     * (which typically contains the class and detail message of
     * <tt>cause</tt>). This constructor is useful for runtime exceptions
     * that are little more than wrappers for other throwables.
     *
     * @param cause the cause (which is saved for later retrieval by the
     *            {@link #getCause()} method). (A <tt>null</tt> value is
     *            permitted, and indicates that the cause is nonexistent or
     *            unknown.)
     * @since 1.4
     */
    public BusinessException(Throwable cause) {
        super(cause);
    }

    /**
     * 创建对象
     *
     * @return BusinessException
     */
    public static BusinessException create() {
        return new BusinessException();
    }

    /**
     * 创建对象例：<br>
     * 通常使用：create("this is %s for %s", "a", "b") => this is a for b<br>
     *
     * @param format 格式化字符串
     * @param objs 参数列表
     * @return BusinessException
     */
    public static BusinessException create(String format, Object... objs) {
        return new BusinessException(String.format(format, objs));
    }

    /**
     * 创建对象例：<br>
     * 通常使用：create("this is %s for %s", "a", "b") => this is a for b<br>
     *
     * @param cause 异常
     * @param format 格式化字符串
     * @param objs 参数列表
     * @return BusinessException
     */
    public static BusinessException create(Throwable cause, String format, Object... objs) {
        return new BusinessException(String.format(format, objs), cause);
    }

    /**
     * 创建对象
     *
     * @param cause 异常
     * @return BusinessException
     */
    public static BusinessException create(Throwable cause) {
        return new BusinessException(cause);
    }
}
