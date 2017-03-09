/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1994, 2011, Oracle and/or its affiliates. All rights reserved.
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

package java.lang;

/**
 * Thrown when an application attempts to use {@code null} in a
 * case where an object is required. These include:
 * <ul>
 * <li>Calling the instance method of a {@code null} object.
 * <li>Accessing or modifying the field of a {@code null} object.
 * <li>Taking the length of {@code null} as if it were an array.
 * <li>Accessing or modifying the slots of {@code null} as if it
 *     were an array.
 * <li>Throwing {@code null} as if it were a {@code Throwable}
 *     value.
 * </ul>
 * <p>
 * Applications should throw instances of this class to indicate
 * other illegal uses of the {@code null} object.
 *
 * {@code NullPointerException} objects may be constructed by the
 * virtual machine as if {@linkplain Throwable#Throwable(String,
 * Throwable, boolean, boolean) suppression were disabled and/or the
 * stack trace was not writable}.
 *
 * <p>
 *  当应用程序在需要对象的情况下尝试使用{@code null}时抛出。这些包括：
 * <ul>
 *  <li>调用{@code null}对象的实例方法。 <li>访问或修改{@code null}对象的字段。 <li>将{@code null}的长度视为数组。
 *  <li>访问或修改{@code null}的插槽,就像它是一个数组一样。 <li>投掷{@code null}就像是{@code Throwable}值。
 * </ul>
 * <p>
 *  应用程序应该抛出此类的实例以指示{@code null}对象的其他非法使用。
 * 
 *  {@code NullPointerException}对象可以由虚拟机构造,如同禁用{@linkplain Throwable#Throwable(String,Throwable,boolean,boolean)抑制和/或堆栈跟踪不可写}
 * 
 * @author  unascribed
 * @since   JDK1.0
 */
public
class NullPointerException extends RuntimeException {
    private static final long serialVersionUID = 5162710183389028792L;

    /**
     * Constructs a {@code NullPointerException} with no detail message.
     * <p>
     * 。
     * 
     */
    public NullPointerException() {
        super();
    }

    /**
     * Constructs a {@code NullPointerException} with the specified
     * detail message.
     *
     * <p>
     *  构造一个没有详细消息的{@code NullPointerException}。
     * 
     * 
     * @param   s   the detail message.
     */
    public NullPointerException(String s) {
        super(s);
    }
}
