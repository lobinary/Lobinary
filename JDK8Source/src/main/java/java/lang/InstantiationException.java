/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
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
 * Thrown when an application tries to create an instance of a class
 * using the {@code newInstance} method in class
 * {@code Class}, but the specified class object cannot be
 * instantiated.  The instantiation can fail for a variety of
 * reasons including but not limited to:
 *
 * <ul>
 * <li> the class object represents an abstract class, an interface,
 *      an array class, a primitive type, or {@code void}
 * <li> the class has no nullary constructor
 *</ul>
 *
 * <p>
 *  当应用程序尝试使用类{@code Class}中的{@code newInstance}方法创建类的实例时抛出,但指定的类对象无法实例化。实例化可能由于各种原因而失败,包括但不限于：
 * 
 * <ul>
 *  <li>类对象表示抽象类,接口,数组类,原始类型或{@code void} <li>类没有nullary构造函数
 * /ul>
 * 
 * 
 * @author  unascribed
 * @see     java.lang.Class#newInstance()
 * @since   JDK1.0
 */
public
class InstantiationException extends ReflectiveOperationException {
    private static final long serialVersionUID = -8441929162975509110L;

    /**
     * Constructs an {@code InstantiationException} with no detail message.
     * <p>
     */
    public InstantiationException() {
        super();
    }

    /**
     * Constructs an {@code InstantiationException} with the
     * specified detail message.
     *
     * <p>
     *  构造一个没有详细消息的{@code InstantiationException}。
     * 
     * 
     * @param   s   the detail message.
     */
    public InstantiationException(String s) {
        super(s);
    }
}
