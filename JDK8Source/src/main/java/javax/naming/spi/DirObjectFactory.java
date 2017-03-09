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
import javax.naming.directory.Attributes;

/**
  * This interface represents a factory for creating an object given
  * an object and attributes about the object.
  *<p>
  * The JNDI framework allows for object implementations to
  * be loaded in dynamically via <em>object factories</em>. See
  * <tt>ObjectFactory</tt> for details.
  * <p>
  * A <tt>DirObjectFactory</tt> extends <tt>ObjectFactory</tt> by allowing
  * an <tt>Attributes</tt> instance
  * to be supplied to the <tt>getObjectInstance()</tt> method.
  * <tt>DirObjectFactory</tt> implementations are intended to be used by <tt>DirContext</tt>
  * service providers. The service provider, in addition reading an
  * object from the directory, might already have attributes that
  * are useful for the object factory to check to see whether the
  * factory is supposed to process the object. For instance, an LDAP-style
  * service provider might have read the "objectclass" of the object.
  * A CORBA object factory might be interested only in LDAP entries
  * with "objectclass=corbaObject". By using the attributes supplied by
  * the LDAP service provider, the CORBA object factory can quickly
  * eliminate objects that it need not worry about, and non-CORBA object
  * factories can quickly eliminate CORBA-related LDAP entries.
  *
  * <p>
  *  该接口表示给定对象的对象和属性的创建对象的工厂。
  * p>
  *  JNDI框架允许通过<em>对象工厂</em>动态加载对象实现。有关详细信息,请参阅<tt> ObjectFactory </tt>。
  * <p>
  *  通过允许将<tt> Attributes </tt>实例提供给<tt> getObjectInstance()</tt>方法,<tt> DirObjectFactory </tt>扩展<tt> Obj
  * ectFactory </tt> <tt> DirObjectFactory </tt>实施旨在由<tt> DirContext </tt>服务提供商使用。
  * 除了从目录中读取对象之外,服务提供者可能已经具有对于对象工厂检查以查看工厂是否应该处理对象有用的属性。例如,LDAP式服务提供程序可能已读取对象的"objectclass"。
  *  CORBA对象工厂可能只对具有"objectclass = corbaObject"的LDAP条目感兴趣。
  * 通过使用LDAP服务提供程序提供的属性,CORBA对象工厂可以快速删除不需要担心的对象,非CORBA对象工厂可以快速消除CORBA相关的LDAP条目。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  *
  * @see NamingManager#getObjectInstance
  * @see DirectoryManager#getObjectInstance
  * @see ObjectFactory
  * @since 1.3
  */

public interface DirObjectFactory extends ObjectFactory {
/**
 * Creates an object using the location or reference information, and attributes
 * specified.
 * <p>
 * Special requirements of this object are supplied
 * using <code>environment</code>.
 * An example of such an environment property is user identity
 * information.
 *<p>
 * <tt>DirectoryManager.getObjectInstance()</tt>
 * successively loads in object factories. If it encounters a <tt>DirObjectFactory</tt>,
 * it will invoke <tt>DirObjectFactory.getObjectInstance()</tt>;
 * otherwise, it invokes
 * <tt>ObjectFactory.getObjectInstance()</tt>. It does this until a factory
 * produces a non-null answer.
 * <p> When an exception
 * is thrown by an object factory, the exception is passed on to the caller
 * of <tt>DirectoryManager.getObjectInstance()</tt>. The search for other factories
 * that may produce a non-null answer is halted.
 * An object factory should only throw an exception if it is sure that
 * it is the only intended factory and that no other object factories
 * should be tried.
 * If this factory cannot create an object using the arguments supplied,
 * it should return null.
  *<p>Since <tt>DirObjectFactory</tt> extends <tt>ObjectFactory</tt>, it
  * effectively
  * has two <tt>getObjectInstance()</tt> methods, where one differs from the other by
  * the attributes argument. Given a factory that implements <tt>DirObjectFactory</tt>,
  * <tt>DirectoryManager.getObjectInstance()</tt> will only
  * use the method that accepts the attributes argument, while
  * <tt>NamingManager.getObjectInstance()</tt> will only use the one that does not accept
  * the attributes argument.
 *<p>
 * See <tt>ObjectFactory</tt> for a description URL context factories and other
 * properties of object factories that apply equally to <tt>DirObjectFactory</tt>.
 *<p>
 * The <tt>name</tt>, <tt>attrs</tt>, and <tt>environment</tt> parameters
 * are owned by the caller.
 * The implementation will not modify these objects or keep references
 * to them, although it may keep references to clones or copies.
 *
 * <p>
 *  使用位置或引用信息和指定的属性创建对象。
 * <p>
 * 此对象的特殊要求使用<code> environment </code>提供。这样的环境属性的示例是用户身份信息。
 * p>
 *  <tt> DirectoryManager.getObjectInstance()</tt>连续加载对象工厂。
 * 如果遇到<tt> DirObjectFactory </tt>,它将调用<tt> DirObjectFactory.getObjectInstance()</tt>;否则,它调用<tt> ObjectF
 * actory.getObjectInstance()</tt>。
 *  <tt> DirectoryManager.getObjectInstance()</tt>连续加载对象工厂。它这样做,直到工厂产生一个非null的答案。
 *  <p>当对象工厂抛出异常时,异常将传递给<tt> DirectoryManager.getObjectInstance()</tt>的调用者。对可能产生非空答案的其他工厂的搜索被停止。
 * 对象工厂只应该抛出一个异常,如果它确定它是唯一的目标工厂,并且没有其他对象工厂应该尝试。如果此工厂无法使用提供的参数创建对象,则应返回null。
 *  p>由于<tt> DirObjectFactory </tt>扩展<tt> ObjectFactory </tt>,它有效地具有两个<tt> getObjectInstance()</tt>方法,其中
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
 * @param attrs The possibly null attributes containing some of <tt>obj</tt>'s
 * attributes. <tt>attrs</tt> might not necessarily have all of <tt>obj</tt>'s
 * attributes. If the object factory requires more attributes, it needs
 * to get it, either using <tt>obj</tt>, or <tt>name</tt> and <tt>nameCtx</tt>.
 *      The factory must not modify attrs.
 * @return The object created; null if an object cannot be created.
 * @exception Exception If this object factory encountered an exception
 * while attempting to create an object, and no other object factories are
 * to be tried.
 *
 * @see DirectoryManager#getObjectInstance
 * @see NamingManager#getURLContext
 */
    public Object getObjectInstance(Object obj, Name name, Context nameCtx,
                                    Hashtable<?,?> environment,
                                    Attributes attrs)
        throws Exception;
}
