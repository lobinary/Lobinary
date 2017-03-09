/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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
  * This class represents a name-to-object binding found in a context.
  *<p>
  * A context consists of name-to-object bindings.
  * The Binding class represents such a binding.  It consists
  * of a name and an object. The <code>Context.listBindings()</code>
  * method returns an enumeration of Binding.
  *<p>
  * Use subclassing for naming systems that generate contents of
  * a binding dynamically.
  *<p>
  * A Binding instance is not synchronized against concurrent access by multiple
  * threads. Threads that need to access a Binding concurrently should
  * synchronize amongst themselves and provide the necessary locking.
  *
  * <p>
  *  此类表示在上下文中找到的名称到对象绑定。
  * p>
  *  上下文由名称到对象绑定组成。 Binding类表示这样的绑定。它由一个名称和一个对象组成。 <code> Context.listBindings()</code>方法返回Binding的枚举。
  * p>
  *  对于动态生成绑定内容的命名系统,使用子类。
  * p>
  *  绑定实例不会与多个线程的并发访问同步。需要同时访问Binding的线程应该在它们之间同步并提供必要的锁定。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  * @since 1.3
  */

public class Binding extends NameClassPair {
    /**
     * Contains this binding's object.
     * It is initialized by the constructor and can be updated using
     * <tt>setObject</tt>.
     * <p>
     *  包含此绑定的对象。它由构造函数初始化,可以使用<tt> setObject </tt>更新。
     * 
     * 
     * @serial
     * @see #getObject
     * @see #setObject
     */
    private Object boundObj;

    /**
      * Constructs an instance of a Binding given its name and object.
      *<p>
      * <tt>getClassName()</tt> will return
      * the class name of <tt>obj</tt> (or null if <tt>obj</tt> is null)
      * unless the class name has been explicitly set using <tt>setClassName()</tt>
      *
      * <p>
      *  构造一个Binding的实例给它的名称和对象。
      * p>
      *  <tt> getClassName()</tt>将返回<tt> obj </tt>的类名称(如果<tt> obj </tt>为null,则返回null),除非类名已使用<tt > setClassNa
      * me()</tt>。
      * 
      * 
      * @param  name    The non-null name of the object. It is relative
      *             to the <em>target context</em> (which is
      * named by the first parameter of the <code>listBindings()</code> method)
      * @param  obj     The possibly null object bound to name.
      * @see NameClassPair#setClassName
      */
    public Binding(String name, Object obj) {
        super(name, null);
        this.boundObj = obj;
    }

    /**
      * Constructs an instance of a Binding given its name, object, and whether
      * the name is relative.
      *<p>
      * <tt>getClassName()</tt> will return the class name of <tt>obj</tt>
      * (or null if <tt>obj</tt> is null) unless the class name has been
      * explicitly set using <tt>setClassName()</tt>
      *
      * <p>
      *  构造一个Binding的实例,给定它的名称,对象,以及名称是否是相对的。
      * p>
      *  <tt> getClassName()</tt>将返回<tt> obj </tt>的类名称(如果<tt> obj </tt>为null,则返回null),除非类名已使用<tt > setClassNa
      * me()</tt>。
      * 
      * 
      * @param  name    The non-null string name of the object.
      * @param  obj     The possibly null object bound to name.
      * @param isRelative true if <code>name</code> is a name relative
      *         to the target context (which is named by
      *         the first parameter of the <code>listBindings()</code> method);
      *         false if <code>name</code> is a URL string.
      * @see NameClassPair#isRelative
      * @see NameClassPair#setRelative
      * @see NameClassPair#setClassName
      */
    public Binding(String name, Object obj, boolean isRelative) {
        super(name, null, isRelative);
        this.boundObj = obj;
    }

    /**
      * Constructs an instance of a Binding given its name, class name, and object.
      *
      * <p>
      *  构造一个Binding的实例给它的名称,类名和对象。
      * 
      * 
      * @param  name    The non-null name of the object. It is relative
      *             to the <em>target context</em> (which is
      * named by the first parameter of the <code>listBindings()</code> method)
      * @param  className       The possibly null class name of the object
      *         bound to <tt>name</tt>. If null, the class name of <tt>obj</tt> is
      *         returned by <tt>getClassName()</tt>. If <tt>obj</tt> is also
      *         null, <tt>getClassName()</tt> will return null.
      * @param  obj     The possibly null object bound to name.
      * @see NameClassPair#setClassName
      */
    public Binding(String name, String className, Object obj) {
        super(name, className);
        this.boundObj = obj;
    }

    /**
      * Constructs an instance of a Binding given its
      * name, class name, object, and whether the name is relative.
      *
      * <p>
      * 构造一个Binding的实例,给定它的名称,类名,对象,以及名称是否是相对的。
      * 
      * 
      * @param  name    The non-null string name of the object.
      * @param  className       The possibly null class name of the object
      *         bound to <tt>name</tt>. If null, the class name of <tt>obj</tt> is
      *         returned by <tt>getClassName()</tt>. If <tt>obj</tt> is also
      *         null, <tt>getClassName()</tt> will return null.
      * @param  obj     The possibly null object bound to name.
      * @param isRelative true if <code>name</code> is a name relative
      *         to the target context (which is named by
      *         the first parameter of the <code>listBindings()</code> method);
      *         false if <code>name</code> is a URL string.
      * @see NameClassPair#isRelative
      * @see NameClassPair#setRelative
      * @see NameClassPair#setClassName
      */
    public Binding(String name, String className, Object obj, boolean isRelative) {
        super(name, className, isRelative);
        this.boundObj = obj;
    }

    /**
      * Retrieves the class name of the object bound to the name of this binding.
      * If the class name has been set explicitly, return it.
      * Otherwise, if this binding contains a non-null object,
      * that object's class name is used. Otherwise, null is returned.
      *
      * <p>
      *  检索绑定到此绑定的名称的对象的类名。如果类名已明确设置,则返回它。否则,如果此绑定包含非空对象,则使用该对象的类名。否则返回null。
      * 
      * 
      * @return A possibly null string containing class name of object bound.
      */
    public String getClassName() {
        String cname = super.getClassName();
        if (cname != null) {
            return cname;
        }
        if (boundObj != null)
            return boundObj.getClass().getName();
        else
            return null;
    }

    /**
      * Retrieves the object bound to the name of this binding.
      *
      * <p>
      *  检索绑定到此绑定的名称的对象。
      * 
      * 
      * @return The object bound; null if this binding does not contain an object.
      * @see #setObject
      */

    public Object getObject() {
        return boundObj;
    }

    /**
     * Sets the object associated with this binding.
     * <p>
     *  设置与此绑定关联的对象。
     * 
     * 
     * @param obj The possibly null object to use.
     * @see #getObject
     */
    public void setObject(Object obj) {
        boundObj = obj;
    }

    /**
      * Generates the string representation of this binding.
      * The string representation consists of the string representation
      * of the name/class pair and the string representation of
      * this binding's object, separated by ':'.
      * The contents of this string is useful
      * for debugging and is not meant to be interpreted programmatically.
      *
      * <p>
      *  生成此绑定的字符串表示形式。字符串表示形式由名称/类对的字符串表示形式和此绑定对象的字符串表示形式组成,用"："分隔。这个字符串的内容对于调试是有用的,并不意味着以编程方式解释。
      * 
      * 
      * @return The non-null string representation of this binding.
      */

    public String toString() {
        return super.toString() + ":" + getObject();
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = 8839217842691845890L;
};
