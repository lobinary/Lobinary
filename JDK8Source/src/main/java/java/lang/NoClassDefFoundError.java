/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1994, 2008, Oracle and/or its affiliates. All rights reserved.
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
 * Thrown if the Java Virtual Machine or a <code>ClassLoader</code> instance
 * tries to load in the definition of a class (as part of a normal method call
 * or as part of creating a new instance using the <code>new</code> expression)
 * and no definition of the class could be found.
 * <p>
 * The searched-for class definition existed when the currently
 * executing class was compiled, but the definition can no longer be
 * found.
 *
 * <p>
 *  如果Java虚拟机或<code> ClassLoader </code>实例尝试在类的定义中加载(作为正常方法调用的一部分或作为使用<code> new </code>创建新实例的一部分)代码>表达式
 * ),并且没有找到类的定义。
 * <p>
 *  当编译当前执行的类时,搜索到的类定义存在,但是不能再找到该定义。
 * 
 * 
 * @author  unascribed
 * @since   JDK1.0
 */
public
class NoClassDefFoundError extends LinkageError {
    private static final long serialVersionUID = 9095859863287012458L;

    /**
     * Constructs a <code>NoClassDefFoundError</code> with no detail message.
     * <p>
     *  构造一个没有详细消息的<code> NoClassDefFoundError </code>。
     * 
     */
    public NoClassDefFoundError() {
        super();
    }

    /**
     * Constructs a <code>NoClassDefFoundError</code> with the specified
     * detail message.
     *
     * <p>
     *  使用指定的详细消息构造<code> NoClassDefFoundError </code>。
     * 
     * @param   s   the detail message.
     */
    public NoClassDefFoundError(String s) {
        super(s);
    }
}
