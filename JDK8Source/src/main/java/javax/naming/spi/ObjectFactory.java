/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2004, Oracle and/or its affiliates. All rights reserved.
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

package javax.naming.spi;

import java.util.Hashtable;

import javax.naming.*;

/**
  * This interface represents a factory for creating an object.
  *<p>
  * The JNDI framework allows for object implementations to
  * be loaded in dynamically via <em>object factories</em>.
  * For example, when looking up a printer bound in the name space,
  * if the print service binds printer names to References, the printer
  * Reference could be used to create a printer object, so that
  * the caller of lookup can directly operate on the printer object
  * after the lookup.
  * <p>An <tt>ObjectFactory</tt> is responsible
  * for creating objects of a specific type.  In the above example,
  * you may have a PrinterObjectFactory for creating Printer objects.
  *<p>
  * An object factory must implement the <tt>ObjectFactory</tt> interface.
  * In addition, the factory class must be public and must have a
  * public constructor that accepts no parameters.
  *<p>
  * The <tt>getObjectInstance()</tt> method of an object factory may
  * be invoked multiple times, possibly using different parameters.
  * The implementation is thread-safe.
  *<p>
  * The mention of URL in the documentation for this class refers to
  * a URL string as defined by RFC 1738 and its related RFCs. It is
  * any string that conforms to the syntax described therein, and
  * may not always have corresponding support in the java.net.URL
  * class or Web browsers.
  *
  * <p>
  *  此接口代表创建对象的工厂。
  * p>
  *  JNDI框架允许对象实现通过<em>对象工厂</em>动态加载。
  * 例如,当查找命名空间中绑定的打印机时,如果打印服务将打印机名称绑定到引用,则打印机引用可以用于创建打印机对象,以便查找的调用者可以直接对打印机对象进行操作查找。
  *  <p> <tt> ObjectFactory </tt>负责创建特定类型的对象。在上面的示例中,您可能有一个PrinterObjectFactory用于创建打印机对象。
  * p>
  *  对象工厂必须实现<tt> ObjectFactory </tt>接口。此外,工厂类必须是public的,并且必须具有不接受参数的公共构造函数。
  * p>
  *  对象工厂的<tt> getObjectInstance()</tt>方法可能被调用多次,可能使用不同的参数。实现是线程安全的。
  * p>
  *  在此类的文档中提及URL是指由RFC 1738及其相关RFC定义的URL字符串。它是符合其中描述的语法的任何字符串,并且可能不总是在java.net.URL类或Web浏览器中具有相应的支持。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  *
  * @see NamingManager#getObjectInstance
  * @see NamingManager#getURLContext
  * @see ObjectFactoryBuilder
  * @see StateFactory
  * @since 1.3
  */

public interface ObjectFactory {
/**
 * Creates an object using the location or reference information
 * specified.
 * <p>
 * Special requirements of this object are supplied
 * using <code>environment</code>.
 * An example of such an environment property is user identity
 * information.
 *<p>
 * <tt>NamingManager.getObjectInstance()</tt>
 * successively loads in object factories and invokes this method
 * on them until one produces a non-null answer.  When an exception
 * is thrown by an object factory, the exception is passed on to the caller
 * of <tt>NamingManager.getObjectInstance()</tt>
 * (and no search is made for other factories
 * that may produce a non-null answer).
 * An object factory should only throw an exception if it is sure that
 * it is the only intended factory and that no other object factories
 * should be tried.
 * If this factory cannot create an object using the arguments supplied,
 * it should return null.
 *<p>
 * A <em>URL context factory</em> is a special ObjectFactory that
 * creates contexts for resolving URLs or objects whose locations
 * are specified by URLs.  The <tt>getObjectInstance()</tt> method
 * of a URL context factory will obey the following rules.
 * <ol>
 * <li>If <code>obj</code> is null, create a context for resolving URLs of the
 * scheme associated with this factory. The resulting context is not tied
 * to a specific URL:  it is able to handle arbitrary URLs with this factory's
 * scheme id.  For example, invoking <tt>getObjectInstance()</tt> with
 * <code>obj</code> set to null on an LDAP URL context factory would return a
 * context that can resolve LDAP URLs
 * such as "ldap://ldap.wiz.com/o=wiz,c=us" and
 * "ldap://ldap.umich.edu/o=umich,c=us".
 * <li>
 * If <code>obj</code> is a URL string, create an object (typically a context)
 * identified by the URL.  For example, suppose this is an LDAP URL context
 * factory.  If <code>obj</code> is "ldap://ldap.wiz.com/o=wiz,c=us",
 * getObjectInstance() would return the context named by the distinguished
 * name "o=wiz, c=us" at the LDAP server ldap.wiz.com.  This context can
 * then be used to resolve LDAP names (such as "cn=George")
 * relative to that context.
 * <li>
 * If <code>obj</code> is an array of URL strings, the assumption is that the
 * URLs are equivalent in terms of the context to which they refer.
 * Verification of whether the URLs are, or need to be, equivalent is up
 * to the context factory. The order of the URLs in the array is
 * not significant.
 * The object returned by getObjectInstance() is like that of the single
 * URL case.  It is the object named by the URLs.
 * <li>
 * If <code>obj</code> is of any other type, the behavior of
 * <tt>getObjectInstance()</tt> is determined by the context factory
 * implementation.
 * </ol>
 *
 * <p>
 * The <tt>name</tt> and <tt>environment</tt> parameters
 * are owned by the caller.
 * The implementation will not modify these objects or keep references
 * to them, although it may keep references to clones or copies.
 *
 * <p>
 * <b>Name and Context Parameters.</b> &nbsp;&nbsp;&nbsp;
 * <a name=NAMECTX></a>
 *
 * The <code>name</code> and <code>nameCtx</code> parameters may
 * optionally be used to specify the name of the object being created.
 * <code>name</code> is the name of the object, relative to context
 * <code>nameCtx</code>.
 * If there are several possible contexts from which the object
 * could be named -- as will often be the case -- it is up to
 * the caller to select one.  A good rule of thumb is to select the
 * "deepest" context available.
 * If <code>nameCtx</code> is null, <code>name</code> is relative
 * to the default initial context.  If no name is being specified, the
 * <code>name</code> parameter should be null.
 * If a factory uses <code>nameCtx</code> it should synchronize its use
 * against concurrent access, since context implementations are not
 * guaranteed to be thread-safe.
 * <p>
 *
 * <p>
 *  使用指定的位置或参考信息创建对象。
 * <p>
 * 此对象的特殊要求使用<code> environment </code>提供。这样的环境属性的示例是用户身份信息。
 * p>
 *  <tt> NamingManager.getObjectInstance()</tt>相继加载对象工厂,并对它们调用此方法,直到产生非空答案。
 * 当对象工厂抛出异常时,异常将传递给<tt> NamingManager.getObjectInstance()</tt>的调用者(并且不会搜索可能产生非null答案的其他工厂)。
 * 对象工厂只应该抛出一个异常,如果它确定它是唯一的目标工厂,并且没有其他对象工厂应该尝试。如果此工厂无法使用提供的参数创建对象,则应返回null。
 * p>
 *  URL上下文工厂</em>是一个特殊的ObjectFactory,它创建用于解析URL或其位置由URL指定的对象的上下文。
 *  URL上下文工厂的<tt> getObjectInstance()</tt>方法将遵守以下规则。
 * <ol>
 * <li>如果<code> obj </code>为null,请创建用于解析与此工厂相关联的方案的URL的上下文。生成的上下文不绑定到一个特定的URL：它能够处理任意URL与这个工厂的方案id。
 * 例如,在LDAP URL上下文工厂上调用<tt> getObjectInstance()</tt>和<code> obj </code>设置为null将返回一个上下文,可以解析LDAP URL,例如"l
 * dap：// ldap。
 * <li>如果<code> obj </code>为null,请创建用于解析与此工厂相关联的方案的URL的上下文。生成的上下文不绑定到一个特定的URL：它能够处理任意URL与这个工厂的方案id。
 *  wiz.com/o=wiz,c=us"和"ldap：//ldap.umich.edu/o=umich,c=us"。
 * <li>
 *  如果<code> obj </code>是URL字符串,请创建由URL标识的对象(通常为上下文)。例如,假设这是一个LDAP URL上下文工厂。
 * 如果<code> obj </code>是"ldap：//ldap.wiz.com/o=wiz,c=us",getObjectInstance()将返回由可分辨名称命名的上下文"o = wiz,c = 
 * us "在LDAP服务器ldap.wiz.com。
 *  如果<code> obj </code>是URL字符串,请创建由URL标识的对象(通常为上下文)。例如,假设这是一个LDAP URL上下文工厂。
 * 
 * @param obj The possibly null object containing location or reference
 *              information that can be used in creating an object.
 * @param name The name of this object relative to <code>nameCtx</code>,
 *              or null if no name is specified.
 * @param nameCtx The context relative to which the <code>name</code>
 *              parameter is specified, or null if <code>name</code> is
 *              relative to the default initial context.
 * @param environment The possibly null environment that is used in
 *              creating the object.
 * @return The object created; null if an object cannot be created.
 * @exception Exception if this object factory encountered an exception
 * while attempting to create an object, and no other object factories are
 * to be tried.
 *
 * @see NamingManager#getObjectInstance
 * @see NamingManager#getURLContext
 */
    public Object getObjectInstance(Object obj, Name name, Context nameCtx,
                                    Hashtable<?,?> environment)
        throws Exception;
}
