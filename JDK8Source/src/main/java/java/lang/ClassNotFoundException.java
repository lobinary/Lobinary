/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2004, Oracle and/or its affiliates. All rights reserved.
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
 * Thrown when an application tries to load in a class through its
 * string name using:
 * <ul>
 * <li>The <code>forName</code> method in class <code>Class</code>.
 * <li>The <code>findSystemClass</code> method in class
 *     <code>ClassLoader</code> .
 * <li>The <code>loadClass</code> method in class <code>ClassLoader</code>.
 * </ul>
 * <p>
 * but no definition for the class with the specified name could be found.
 *
 * <p>As of release 1.4, this exception has been retrofitted to conform to
 * the general purpose exception-chaining mechanism.  The "optional exception
 * that was raised while loading the class" that may be provided at
 * construction time and accessed via the {@link #getException()} method is
 * now known as the <i>cause</i>, and may be accessed via the {@link
 * Throwable#getCause()} method, as well as the aforementioned "legacy method."
 *
 * <p>
 *  当应用程序尝试通过其字符串名称在类中加载时抛出：
 * <ul>
 *  <li> <code>类</code>中的<code> forName </code>方法。
 *  <li> <code> ClassLoader </code>类中的<code> findSystemClass </code>方法。
 *  <li> <code> ClassLoader </code>类中的<code> loadClass </code>方法。
 * </ul>
 * <p>
 *  但没有找到具有指定名称的类的定义。
 * 
 *  <p>自版本1.4起,此异常已经过改进以符合通用的异常链接机制。
 * 在构建时可以提供并通过{@link #getException()}方法访问的"加载类时产生的可选异常"现在称为<i>原因</i>,可以访问通过{@link Throwable#getCause()}方
 * 法,以及上述"传统方法"。
 *  <p>自版本1.4起,此异常已经过改进以符合通用的异常链接机制。
 * 
 * 
 * @author  unascribed
 * @see     java.lang.Class#forName(java.lang.String)
 * @see     java.lang.ClassLoader#findSystemClass(java.lang.String)
 * @see     java.lang.ClassLoader#loadClass(java.lang.String, boolean)
 * @since   JDK1.0
 */
public class ClassNotFoundException extends ReflectiveOperationException {
    /**
     * use serialVersionUID from JDK 1.1.X for interoperability
     * <p>
     *  使用JDK 1.1.X中的serialVersionUID实现互操作性
     * 
     */
     private static final long serialVersionUID = 9176873029745254542L;

    /**
     * This field holds the exception ex if the
     * ClassNotFoundException(String s, Throwable ex) constructor was
     * used to instantiate the object
     * <p>
     *  如果ClassNotFoundException(String s,Throwable ex)构造函数用于实例化对象,此字段保存异常
     * 
     * 
     * @serial
     * @since 1.2
     */
    private Throwable ex;

    /**
     * Constructs a <code>ClassNotFoundException</code> with no detail message.
     * <p>
     *  构造一个没有详细消息的<code> ClassNotFoundException </code>。
     * 
     */
    public ClassNotFoundException() {
        super((Throwable)null);  // Disallow initCause
    }

    /**
     * Constructs a <code>ClassNotFoundException</code> with the
     * specified detail message.
     *
     * <p>
     *  用指定的详细消息构造一个<code> ClassNotFoundException </code>。
     * 
     * 
     * @param   s   the detail message.
     */
    public ClassNotFoundException(String s) {
        super(s, null);  //  Disallow initCause
    }

    /**
     * Constructs a <code>ClassNotFoundException</code> with the
     * specified detail message and optional exception that was
     * raised while loading the class.
     *
     * <p>
     *  构造具有指定的详细消息和在加载类时引发的可选异常的<code> ClassNotFoundException </code>。
     * 
     * 
     * @param s the detail message
     * @param ex the exception that was raised while loading the class
     * @since 1.2
     */
    public ClassNotFoundException(String s, Throwable ex) {
        super(s, null);  //  Disallow initCause
        this.ex = ex;
    }

    /**
     * Returns the exception that was raised if an error occurred while
     * attempting to load the class. Otherwise, returns <tt>null</tt>.
     *
     * <p>This method predates the general-purpose exception chaining facility.
     * The {@link Throwable#getCause()} method is now the preferred means of
     * obtaining this information.
     *
     * <p>
     * 返回在尝试加载类时发生错误时引发的异常。否则,返回<tt> null </tt>。
     * 
     *  <p>此方法早于通用异常链接设施。 {@link Throwable#getCause()}方法现在是获取此信息的首选方法。
     * 
     * 
     * @return the <code>Exception</code> that was raised while loading a class
     * @since 1.2
     */
    public Throwable getException() {
        return ex;
    }

    /**
     * Returns the cause of this exception (the exception that was raised
     * if an error occurred while attempting to load the class; otherwise
     * <tt>null</tt>).
     *
     * <p>
     * 
     * @return  the cause of this exception.
     * @since   1.4
     */
    public Throwable getCause() {
        return ex;
    }
}
