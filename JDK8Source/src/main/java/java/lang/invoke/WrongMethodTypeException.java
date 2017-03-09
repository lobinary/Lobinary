/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2008, 2012, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.invoke;

/**
 * Thrown to indicate that code has attempted to call a method handle
 * via the wrong method type.  As with the bytecode representation of
 * normal Java method calls, method handle calls are strongly typed
 * to a specific type descriptor associated with a call site.
 * <p>
 * This exception may also be thrown when two method handles are
 * composed, and the system detects that their types cannot be
 * matched up correctly.  This amounts to an early evaluation
 * of the type mismatch, at method handle construction time,
 * instead of when the mismatched method handle is called.
 *
 * <p>
 *  抛出以指示代码已尝试通过错误的方法类型调用方法句柄。与正常Java方法调用的字节码表示一样,方法句柄调用被强类型化到与调用点相关联的特定类型描述符。
 * <p>
 *  当组合两个方法句柄,并且系统检测到它们的类型无法正确匹配时,也可能抛出此异常。这相当于在方法句柄构造时对类型不匹配的早期评估,而不是当调用不匹配的方法句柄时。
 * 
 * 
 * @author John Rose, JSR 292 EG
 * @since 1.7
 */
public class WrongMethodTypeException extends RuntimeException {
    private static final long serialVersionUID = 292L;

    /**
     * Constructs a {@code WrongMethodTypeException} with no detail message.
     * <p>
     *  构造一个没有详细消息的{@code WrongMethodTypeException}。
     * 
     */
    public WrongMethodTypeException() {
        super();
    }

    /**
     * Constructs a {@code WrongMethodTypeException} with the specified
     * detail message.
     *
     * <p>
     *  构造具有指定详细消息的{@code WrongMethodTypeException}。
     * 
     * 
     * @param s the detail message.
     */
    public WrongMethodTypeException(String s) {
        super(s);
    }

    /**
     * Constructs a {@code WrongMethodTypeException} with the specified
     * detail message and cause.
     *
     * <p>
     *  构造具有指定的详细消息和原因的{@code WrongMethodTypeException}。
     * 
     * 
     * @param s the detail message.
     * @param cause the cause of the exception, or null.
     */
    //FIXME: make this public in MR1
    /*non-public*/ WrongMethodTypeException(String s, Throwable cause) {
        super(s, cause);
    }

    /**
     * Constructs a {@code WrongMethodTypeException} with the specified
     * cause.
     *
     * <p>
     *  超级(s,原因); }}
     * 
     * 
     * @param cause the cause of the exception, or null.
     */
    //FIXME: make this public in MR1
    /*non-public*/ WrongMethodTypeException(Throwable cause) {
        super(cause);
    }
}
