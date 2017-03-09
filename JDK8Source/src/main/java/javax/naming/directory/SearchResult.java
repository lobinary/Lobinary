/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2000, Oracle and/or its affiliates. All rights reserved.
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

package javax.naming.directory;

import javax.naming.Binding;

/**
  * This class represents an item in the NamingEnumeration returned as a
  * result of the DirContext.search() methods.
  *<p>
  * A SearchResult instance is not synchronized against concurrent
  * multithreaded access. Multiple threads trying to access and modify
  * a single SearchResult instance should lock the object.
  *
  * <p>
  *  此类表示作为DirContext.search()方法的结果返回的NamingEnumeration中的项。
  * p>
  *  SearchResult实例未与并发多线程访问同步。尝试访问和修改单个SearchResult实例的多个线程应锁定该对象。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  *
  * @see DirContext#search
  * @since 1.3
  */

public class SearchResult extends Binding {
    /**
     * Contains the attributes returned with the object.
     * <p>
     *  包含与对象一起返回的属性。
     * 
     * 
     * @serial
     */
    private Attributes attrs;

    /**
      * Constructs a search result using the result's name, its bound object, and
      * its attributes.
      *<p>
      * <tt>getClassName()</tt> will return the class name of <tt>obj</tt>
      * (or null if <tt>obj</tt> is null) unless the class name has been
      * explicitly set using <tt>setClassName()</tt>.
      *
      * <p>
      *  使用结果的名称,其绑定对象及其属性构造搜索结果。
      * p>
      *  <tt> getClassName()</tt>将返回<tt> obj </tt>的类名称(如果<tt> obj </tt>为null,则返回null),除非类名已使用<tt > setClassNa
      * me()</tt>。
      * 
      * 
      * @param name The non-null name of the search item. It is relative
      *             to the <em>target context</em> of the search (which is
      * named by the first parameter of the <code>search()</code> method)
      *
      * @param obj  The object bound to name. Can be null.
      * @param attrs The attributes that were requested to be returned with
      * this search item. Cannot be null.
      * @see javax.naming.NameClassPair#setClassName
      * @see javax.naming.NameClassPair#getClassName
      */
    public SearchResult(String name, Object obj, Attributes attrs) {
        super(name, obj);
        this.attrs = attrs;
    }

    /**
      * Constructs a search result using the result's name, its bound object, and
      * its attributes, and whether the name is relative.
      *<p>
      * <tt>getClassName()</tt> will return the class name of <tt>obj</tt>
      * (or null if <tt>obj</tt> is null) unless the class name has been
      * explicitly set using <tt>setClassName()</tt>
      *
      * <p>
      *  使用结果的名称,其绑定对象及其属性以及名称是否是相对的来构造搜索结果。
      * p>
      *  <tt> getClassName()</tt>将返回<tt> obj </tt>的类名称(如果<tt> obj </tt>为null,则返回null),除非类名已使用<tt > setClassNa
      * me()</tt>。
      * 
      * 
      * @param name The non-null name of the search item.
      * @param obj  The object bound to name. Can be null.
      * @param attrs The attributes that were requested to be returned with
      * this search item. Cannot be null.
      * @param isRelative true if <code>name</code> is relative
      *         to the target context of the search (which is named by
      *         the first parameter of the <code>search()</code> method);
      *         false if <code>name</code> is a URL string.
      * @see javax.naming.NameClassPair#setClassName
      * @see javax.naming.NameClassPair#getClassName
      */
    public SearchResult(String name, Object obj, Attributes attrs,
        boolean isRelative) {
        super(name, obj, isRelative);
        this.attrs = attrs;
    }

    /**
      * Constructs a search result using the result's name, its class name,
      * its bound object, and its attributes.
      *
      * <p>
      *  使用结果的名称,其类名称,其绑定对象及其属性构造搜索结果。
      * 
      * 
      * @param name The non-null name of the search item. It is relative
      *             to the <em>target context</em> of the search (which is
      * named by the first parameter of the <code>search()</code> method)
      *
      * @param  className       The possibly null class name of the object
      *         bound to <tt>name</tt>. If null, the class name of <tt>obj</tt> is
      *         returned by <tt>getClassName()</tt>. If <tt>obj</tt> is also null,
      *         <tt>getClassName()</tt> will return null.
      * @param obj  The object bound to name. Can be null.
      * @param attrs The attributes that were requested to be returned with
      * this search item. Cannot be null.
      * @see javax.naming.NameClassPair#setClassName
      * @see javax.naming.NameClassPair#getClassName
      */
    public SearchResult(String name, String className,
        Object obj, Attributes attrs) {
        super(name, className, obj);
        this.attrs = attrs;
    }

    /**
      * Constructs a search result using the result's name, its class name,
      * its bound object, its attributes, and whether the name is relative.
      *
      * <p>
      *  使用结果的名称,其类名称,其绑定对象,其属性以及名称是否是相对的来构造搜索结果。
      * 
      * 
      * @param name The non-null name of the search item.
      * @param  className       The possibly null class name of the object
      *         bound to <tt>name</tt>. If null, the class name of <tt>obj</tt> is
      *         returned by <tt>getClassName()</tt>. If <tt>obj</tt> is also null,
      *         <tt>getClassName()</tt> will return null.
      * @param obj  The object bound to name. Can be null.
      * @param attrs The attributes that were requested to be returned with
      * this search item. Cannot be null.
      * @param isRelative true if <code>name</code> is relative
      *         to the target context of the search (which is named by
      *         the first parameter of the <code>search()</code> method);
      *         false if <code>name</code> is a URL string.
      * @see javax.naming.NameClassPair#setClassName
      * @see javax.naming.NameClassPair#getClassName
      */
    public SearchResult(String name, String className, Object obj,
        Attributes attrs, boolean isRelative) {
        super(name, className, obj, isRelative);
        this.attrs = attrs;
    }

    /**
     * Retrieves the attributes in this search result.
     *
     * <p>
     *  检索此搜索结果中的属性。
     * 
     * 
     * @return The non-null attributes in this search result. Can be empty.
     * @see #setAttributes
     */
    public Attributes getAttributes() {
        return attrs;
    }


    /**
     * Sets the attributes of this search result to <code>attrs</code>.
     * <p>
     *  将此搜索结果的属性设置为<code> attrs </code>。
     * 
     * 
     * @param attrs The non-null attributes to use. Can be empty.
     * @see #getAttributes
     */
    public void setAttributes(Attributes attrs) {
        this.attrs = attrs;
        // ??? check for null?
    }


    /**
      * Generates the string representation of this SearchResult.
      * The string representation consists of the string representation
      * of the binding and the string representation of
      * this search result's attributes, separated by ':'.
      * The contents of this string is useful
      * for debugging and is not meant to be interpreted programmatically.
      *
      * <p>
      * 生成此SearchResult的字符串表示形式。字符串表示形式由绑定的字符串表示形式和此搜索结果属性的字符串表示形式组成,用"："分隔。这个字符串的内容对于调试是有用的,并不意味着以编程方式解释。
      * 
      * 
      * @return The string representation of this SearchResult. Cannot be null.
      */
    public String toString() {
        return super.toString() + ":" + getAttributes();
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = -9158063327699723172L;
}
