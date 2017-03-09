/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package javax.xml.bind;

/**
 * This exception indicates that a violation of a dynamically checked type
 * constraint was detected.
 *
 * <p>
 * This exception can be thrown by the generated setter methods of the schema
 * derived Java content classes.  However, since fail-fast validation is
 * an optional feature for JAXB Providers to support, not all setter methods
 * will throw this exception when a type constraint is violated.
 *
 * <p>
 * If this exception is throw while invoking a fail-fast setter, the value of
 * the property is guaranteed to remain unchanged, as if the setter were never
 * called.
 *
 * <p>
 *  此异常指示检测到违反动态检查的类型约束。
 * 
 * <p>
 *  此异常可以由生成的模式派生的Java内容类的setter方法抛出。但是,由于故障快速验证是JAXB提供程序支持的可选功能,当违反类型约束时,并非所有setter方法都将抛出此异常。
 * 
 * <p>
 *  如果在调用失败快速设置器时抛出此异常,则属性的值保证保持不变,如同从未调用setter。
 * 
 * 
 * @author <ul><li>Ryan Shoemaker, Sun Microsystems, Inc.</li><li>Joe Fialli, Sun Microsystems, Inc.</li></ul>
 * @see ValidationEvent
 * @since JAXB1.0
 */

public class TypeConstraintException extends java.lang.RuntimeException {

    /**
     * Vendor specific error code
     *
     * <p>
     *  供应商特定的错误代码
     * 
     */
    private String errorCode;

    /**
     * Exception reference
     *
     * <p>
     *  异常引用
     * 
     */
    private volatile Throwable linkedException;

    static final long serialVersionUID = -3059799699420143848L;

    /**
     * Construct a TypeConstraintException with the specified detail message.  The
     * errorCode and linkedException will default to null.
     *
     * <p>
     *  使用指定的详细消息构造TypeConstraintException。 errorCode和linkedException将默认为null。
     * 
     * 
     * @param message a description of the exception
     */
    public TypeConstraintException(String message) {
        this( message, null, null );
    }

    /**
     * Construct a TypeConstraintException with the specified detail message and vendor
     * specific errorCode.  The linkedException will default to null.
     *
     * <p>
     *  构造具有指定的详细消息和供应商特定的errorCode的TypeConstraintException。 linkedException将默认为null。
     * 
     * 
     * @param message a description of the exception
     * @param errorCode a string specifying the vendor specific error code
     */
    public TypeConstraintException(String message, String errorCode) {
        this( message, errorCode, null );
    }

    /**
     * Construct a TypeConstraintException with a linkedException.  The detail message and
     * vendor specific errorCode will default to null.
     *
     * <p>
     *  使用linkedException构造TypeConstraintException。详细消息和供应商特定的errorCode将默认为null。
     * 
     * 
     * @param exception the linked exception
     */
    public TypeConstraintException(Throwable exception) {
        this( null, null, exception );
    }

    /**
     * Construct a TypeConstraintException with the specified detail message and
     * linkedException.  The errorCode will default to null.
     *
     * <p>
     *  构造具有指定的详细消息和linkedException的TypeConstraintException。 errorCode将默认为null。
     * 
     * 
     * @param message a description of the exception
     * @param exception the linked exception
     */
    public TypeConstraintException(String message, Throwable exception) {
        this( message, null, exception );
    }

    /**
     * Construct a TypeConstraintException with the specified detail message,
     * vendor specific errorCode, and linkedException.
     *
     * <p>
     *  构造具有指定的详细消息,供应商特定的errorCode和linkedException的TypeConstraintException。
     * 
     * 
     * @param message a description of the exception
     * @param errorCode a string specifying the vendor specific error code
     * @param exception the linked exception
     */
    public TypeConstraintException(String message, String errorCode, Throwable exception) {
        super( message );
        this.errorCode = errorCode;
        this.linkedException = exception;
    }

    /**
     * Get the vendor specific error code
     *
     * <p>
     *  获取供应商特定的错误代码
     * 
     * 
     * @return a string specifying the vendor specific error code
     */
    public String getErrorCode() {
        return this.errorCode;
    }

    /**
     * Get the linked exception
     *
     * <p>
     *  获取链接的异常
     * 
     * 
     * @return the linked Exception, null if none exists
     */
    public Throwable getLinkedException() {
        return linkedException;
    }

    /**
     * Add a linked Exception.
     *
     * <p>
     *  添加链接异常。
     * 
     * 
     * @param exception the linked Exception (A null value is permitted and
     *                  indicates that the linked exception does not exist or
     *                  is unknown).
     */
    public void setLinkedException( Throwable exception ) {
        this.linkedException = exception;
    }

    /**
     * Returns a short description of this TypeConstraintException.
     *
     * <p>
     * 返回此TypeConstraintException的简短描述。
     * 
     */
    public String toString() {
        return linkedException == null ?
            super.toString() :
            super.toString() + "\n - with linked exception:\n[" +
                                linkedException.toString()+ "]";
    }

    /**
     * Prints this TypeConstraintException and its stack trace (including the stack trace
     * of the linkedException if it is non-null) to the PrintStream.
     *
     * <p>
     *  打印此TypeConstraintException及其堆栈跟踪(包括linkedException的堆栈跟踪,如果它不为null)到PrintStream。
     * 
     * 
     * @param s PrintStream to use for output
     */
    public void printStackTrace( java.io.PrintStream s ) {
        if( linkedException != null ) {
          linkedException.printStackTrace(s);
          s.println("--------------- linked to ------------------");
        }

        super.printStackTrace(s);
    }

    /**
     * Prints this TypeConstraintException and its stack trace (including the stack trace
     * of the linkedException if it is non-null) to <tt>System.err</tt>.
     *
     * <p>
     *  打印此TypeConstraintException及其堆栈跟踪(包括linkedException的堆栈跟踪,如果非空)到<tt> System.err </tt>。
     */
    public void printStackTrace() {
        printStackTrace(System.err);
    }

}
