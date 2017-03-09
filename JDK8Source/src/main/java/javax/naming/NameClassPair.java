/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2003, Oracle and/or its affiliates. All rights reserved.
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

package javax.naming;

/**
 * This class represents the object name and class name pair of a binding
 * found in a context.
 *<p>
 * A context consists of name-to-object bindings.
 * The NameClassPair class represents the name and the
 * class of the bound object. It consists
 * of a name and a string representing the
 * package-qualified class name.
 *<p>
 * Use subclassing for naming systems that generate contents of
 * a name/class pair dynamically.
 *<p>
 * A NameClassPair instance is not synchronized against concurrent
 * access by multiple threads. Threads that need to access a NameClassPair
 * concurrently should synchronize amongst themselves and provide
 * the necessary locking.
 *
 * <p>
 *  此类表示在上下文中找到的绑定的对象名称和类名称对。
 * p>
 *  上下文由名称到对象绑定组成。 NameClassPair类表示绑定对象的名称和类。它由名称和表示程序包限定类名的字符串组成。
 * p>
 *  对于动态生成名称/类对的内容的命名系统,使用子类。
 * p>
 *  NameClassPair实例不会与多个线程的并发访问同步。需要同时访问NameClassPair的线程应该在它们之间同步并提供必要的锁定。
 * 
 * 
 * @author Rosanna Lee
 * @author Scott Seligman
 *
 * @see Context#list
 * @since 1.3
 */

 /*
  * <p>
  * The serialized form of a NameClassPair object consists of the name (a
  * String), class name (a String), and isRelative flag (a boolean).
  * <p>
  * <p>
  *  NameClassPair对象的序列化形式由名称(String),类名(String)和isRelative标志(布尔)组成。
  * 
  */

public class NameClassPair implements java.io.Serializable {
    /**
     * Contains the name of this NameClassPair.
     * It is initialized by the constructor and can be updated using
     * <tt>setName()</tt>.
     * <p>
     *  包含此NameClassPair的名称。它由构造函数初始化,可以使用<tt> setName()</tt>更新。
     * 
     * 
     * @serial
     * @see #getName
     * @see #setName
     */
    private String name;

    /**
     *Contains the class name contained in this NameClassPair.
     * It is initialized by the constructor and can be updated using
     * <tt>setClassName()</tt>.
     * <p>
     *  保存此NameClassPair中包含的类名。它由构造函数初始化,可以使用<tt> setClassName()</tt>更新。
     * 
     * 
     * @serial
     * @see #getClassName
     * @see #setClassName
     */
    private String className;

    /**
     * Contains the full name of this NameClassPair within its
     * own namespace.
     * It is initialized using <tt>setNameInNamespace()</tt>
     * <p>
     *  在其自己的命名空间中包含此NameClassPair的全名。它使用<tt> setNameInNamespace()</tt>初始化
     * 
     * 
     * @serial
     * @see #getNameInNamespace
     * @see #setNameInNamespace
     */
    private String fullName = null;


    /**
     * Records whether the name of this <tt>NameClassPair</tt>
     * is relative to the target context.
     * It is initialized by the constructor and can be updated using
     * <tt>setRelative()</tt>.
     * <p>
     *  记录此<tt> NameClassPair </tt>的名称是否与目标上下文相关。它由构造函数初始化,可以使用<tt> setRelative()</tt>更新。
     * 
     * 
     * @serial
     * @see #isRelative
     * @see #setRelative
     * @see #getName
     * @see #setName
     */
    private boolean isRel = true;

    /**
     * Constructs an instance of a NameClassPair given its
     * name and class name.
     *
     * <p>
     * 构造一个NameClassPair的实例,给定它的名称和类名。
     * 
     * 
     * @param   name    The non-null name of the object. It is relative
     *                  to the <em>target context</em> (which is
     * named by the first parameter of the <code>list()</code> method)
     * @param   className       The possibly null class name of the object
     *          bound to name. It is null if the object bound is null.
     * @see #getClassName
     * @see #setClassName
     * @see #getName
     * @see #setName
     */
    public NameClassPair(String name, String className) {
        this.name = name;
        this.className = className;
    }

    /**
     * Constructs an instance of a NameClassPair given its
     * name, class name, and whether it is relative to the listing context.
     *
     * <p>
     *  构造一个NameClassPair的实例,给定它的名称,类名称,以及它是否相对于列表上下文。
     * 
     * 
     * @param   name    The non-null name of the object.
     * @param   className       The possibly null class name of the object
     *  bound to name.  It is null if the object bound is null.
     * @param isRelative true if <code>name</code> is a name relative
     *          to the target context (which is named by the first parameter
     *          of the <code>list()</code> method); false if <code>name</code>
     *          is a URL string.
     * @see #getClassName
     * @see #setClassName
     * @see #getName
     * @see #setName
     * @see #isRelative
     * @see #setRelative
     */
    public NameClassPair(String name, String className, boolean isRelative) {
        this.name = name;
        this.className = className;
        this.isRel = isRelative;
    }

    /**
     * Retrieves the class name of the object bound to the name of this binding.
     * If a reference or some other indirect information is bound,
     * retrieves the class name of the eventual object that
     * will be returned by <tt>Binding.getObject()</tt>.
     *
     * <p>
     *  检索绑定到此绑定的名称的对象的类名。如果引用或某些其他间接信息被绑定,则检索将由<tt> Binding.getObject()</tt>返回的最终对象的类名。
     * 
     * 
     * @return  The possibly null class name of object bound.
     *          It is null if the object bound is null.
     * @see Binding#getObject
     * @see Binding#getClassName
     * @see #setClassName
     */
    public String getClassName() {
        return className;
    }

    /**
     * Retrieves the name of this binding.
     * If <tt>isRelative()</tt> is true, this name is relative to the
     * target context (which is named by the first parameter of the
     * <tt>list()</tt>).
     * If <tt>isRelative()</tt> is false, this name is a URL string.
     *
     * <p>
     *  检索此绑定的名称。如果<tt> isRelative()</tt>为true,则此名称相对于目标上下文(由<tt> list()</tt>的第一个参数命名)。
     * 如果<tt> isRelative()</tt>为false,则此名称是一个URL字符串。
     * 
     * 
     * @return  The non-null name of this binding.
     * @see #isRelative
     * @see #setName
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this binding.
     *
     * <p>
     *  设置此绑定的名称。
     * 
     * 
     * @param   name the non-null string to use as the name.
     * @see #getName
     * @see #setRelative
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the class name of this binding.
     *
     * <p>
     *  设置此绑定的类名。
     * 
     * 
     * @param   name the possibly null string to use as the class name.
     * If null, <tt>Binding.getClassName()</tt> will return
     * the actual class name of the object in the binding.
     * The class name will be null if the object bound is null.
     * @see #getClassName
     * @see Binding#getClassName
     */
    public void setClassName(String name) {
        this.className = name;
    }

    /**
     * Determines whether the name of this binding is
     * relative to the target context (which is named by
     * the first parameter of the <code>list()</code> method).
     *
     * <p>
     *  确定此绑定的名称是否相对于目标上下文(由<code> list()</code>方法的第一个参数命名)。
     * 
     * 
     * @return true if the name of this binding is relative to the
     *          target context;
     *          false if the name of this binding is a URL string.
     * @see #setRelative
     * @see #getName
     */
    public boolean isRelative() {
        return isRel;
    }

    /**
     * Sets whether the name of this binding is relative to the target
     * context (which is named by the first parameter of the <code>list()</code>
     * method).
     *
     * <p>
     *  设置此绑定的名称是否相对于目标上下文(由<code> list()</code>方法的第一个参数命名)。
     * 
     * 
     * @param r If true, the name of binding is relative to the target context;
     *          if false, the name of binding is a URL string.
     * @see #isRelative
     * @see #setName
     */
    public void setRelative(boolean r) {
        isRel = r;
    }

    /**
     * Retrieves the full name of this binding.
     * The full name is the absolute name of this binding within
     * its own namespace. See {@link Context#getNameInNamespace()}.
     * <p>
     *
     * In naming systems for which the notion of full name does not
     * apply to this binding an <tt>UnsupportedOperationException</tt>
     * is thrown.
     * This exception is also thrown when a service provider written before
     * the introduction of the method is in use.
     * <p>
     * The string returned by this method is not a JNDI composite name and
     * should not be passed directly to context methods.
     *
     * <p>
     *  检索此绑定的全名。全名是此绑定在其自己的命名空间中的绝对名称。参见{@link Context#getNameInNamespace()}。
     * <p>
     * 
     *  在命名系统中,全名的概念不适用于此绑定,则会抛出<tt> UnsupportedOperationException </tt>。当在引入方法之前写入的服务提供程序正在使用时,也会抛出此异常。
     * <p>
     * 此方法返回的字符串不是JNDI复合名称,不应直接传递给上下文方法。
     * 
     * 
     * @return The full name of this binding.
     * @throws UnsupportedOperationException if the notion of full name
     *         does not apply to this binding in the naming system.
     * @since 1.5
     * @see #setNameInNamespace
     * @see #getName
     */
    public String getNameInNamespace() {
        if (fullName == null) {
            throw new UnsupportedOperationException();
        }
        return fullName;
    }

    /**
     * Sets the full name of this binding.
     * This method must be called to set the full name whenever a
     * <tt>NameClassPair</tt> is created and a full name is
     * applicable to this binding.
     * <p>
     * Setting the full name to null, or not setting it at all, will
     * cause <tt>getNameInNamespace()</tt> to throw an exception.
     *
     * <p>
     *  设置此绑定的全名。每当创建<tt> NameClassPair </tt>并且全名适用于此绑定时,必须调用此方法以设置全名。
     * <p>
     *  将全名设置为null或不设置它将会导致<tt> getNameInNamespace()</tt>抛出异常。
     * 
     * 
     * @param fullName The full name to use.
     * @since 1.5
     * @see #getNameInNamespace
     * @see #setName
     */
    public void setNameInNamespace(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Generates the string representation of this name/class pair.
     * The string representation consists of the name and class name separated
     * by a colon (':').
     * The contents of this string is useful
     * for debugging and is not meant to be interpreted programmatically.
     *
     * <p>
     *  生成此名称/类对的字符串表示形式。字符串表示由名称和类名称组成,用冒号('：')分隔。这个字符串的内容对于调试是有用的,并不意味着以编程方式解释。
     * 
     * 
     * @return The string representation of this name/class pair.
     */
    public String toString() {
        return (isRelative() ? "" : "(not relative)") + getName() + ": " +
                getClassName();
    }


    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = 5620776610160863339L;
}
