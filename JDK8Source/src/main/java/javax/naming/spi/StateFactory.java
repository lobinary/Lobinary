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

import javax.naming.*;
import java.util.Hashtable;

/**
  * This interface represents a factory for obtaining the state of an
  * object for binding.
  *<p>
  * The JNDI framework allows for object implementations to
  * be loaded in dynamically via <em>object factories</em>.
  * For example, when looking up a printer bound in the name space,
  * if the print service binds printer names to <tt>Reference</tt>s, the printer
  * <tt>Reference</tt> could be used to create a printer object, so that
  * the caller of lookup can directly operate on the printer object
  * after the lookup.
  * <p>An <tt>ObjectFactory</tt> is responsible
  * for creating objects of a specific type.  In the above example,
  * you may have a <tt>PrinterObjectFactory</tt> for creating
  * <tt>Printer</tt> objects.
  * <p>
  * For the reverse process, when an object is bound into the namespace,
  * JNDI provides <em>state factories</em>.
  * Continuing with the printer example, suppose the printer object is
  * updated and rebound:
  * <blockquote><pre>
  * ctx.rebind("inky", printer);
  * </pre></blockquote>
  * The service provider for <tt>ctx</tt> uses a state factory
  * to obtain the state of <tt>printer</tt> for binding into its namespace.
  * A state factory for the <tt>Printer</tt> type object might return
  * a more compact object for storage in the naming system.
  *<p>
  * A state factory must implement the <tt>StateFactory</tt> interface.
  * In addition, the factory class must be public and must have a
  * public constructor that accepts no parameters.
  *<p>
  * The <tt>getStateToBind()</tt> method of a state factory may
  * be invoked multiple times, possibly using different parameters.
  * The implementation is thread-safe.
  *<p>
  * <tt>StateFactory</tt> is intended for use with service providers
  * that implement only the <tt>Context</tt> interface.
  * <tt>DirStateFactory</tt> is intended for use with service providers
  * that implement the <tt>DirContext</tt> interface.
  *
  * <p>
  *  该接口表示用于获得绑定对象的状态的工厂。
  * p>
  *  JNDI框架允许对象实现通过<em>对象工厂</em>动态加载。
  * 例如,当查找命名空间中绑定的打印机时,如果打印服务将打印机名称绑定到<tt>引用</tt>,则打印机<tt>引用</tt>可用于创建打印机对象,使得查找的调用者可以在查找之后直接对打印机对象进行操作。
  *  <p> <tt> ObjectFactory </tt>负责创建特定类型的对象。
  * 在上面的示例中,您可以使用<tt> PrinterObjectFactory </tt>创建<tt>打印机</tt>对象。
  * <p>
  *  对于反向过程,当一个对象绑定到命名空间中时,JNDI提供<em>状态工厂</em>。
  * 继续打印机示例,假设打印机对象已更新并反弹：<blockquote> <pre> ctx.rebind("inky",printer); </pre> </blockquote> <tt> ctx </tt>
  * 的服务提供商使用状态工厂获取<tt>打印机</tt>的状态以绑定到其命名空间。
  *  对于反向过程,当一个对象绑定到命名空间中时,JNDI提供<em>状态工厂</em>。 <tt>打印机</tt>类型对象的状态工厂可能会返回一个更紧凑的对象以存储在命名系统中。
  * p>
  * 状态工厂必须实现<tt> StateFactory </tt>接口。此外,工厂类必须是public的,并且必须具有不接受参数的公共构造函数。
  * p>
  *  状态工厂的<tt> getStateToBind()</tt>方法可能被调用多次,可能使用不同的参数。实现是线程安全的。
  * p>
  *  <tt> StateFactory </tt>仅适用于只实现<tt> Context </tt>接口的服务提供商。
  *  <tt> DirStateFactory </tt>旨在与实现<tt> DirContext </tt>接口的服务提供商配合使用。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  *
  * @see NamingManager#getStateToBind
  * @see DirectoryManager#getStateToBind
  * @see ObjectFactory
  * @see DirStateFactory
  * @since 1.3
  */
public interface StateFactory {
/**
 * Retrieves the state of an object for binding.
 *<p>
 * <tt>NamingManager.getStateToBind()</tt>
 * successively loads in state factories and invokes this method
 * on them until one produces a non-null answer.
 * <tt>DirectoryManager.getStateToBind()</tt>
 * successively loads in state factories.  If a factory implements
 * <tt>DirStateFactory</tt>, then <tt>DirectoryManager</tt>
 * invokes <tt>DirStateFactory.getStateToBind()</tt>; otherwise
 * it invokes <tt>StateFactory.getStateToBind()</tt>.
 *<p> When an exception
 * is thrown by a factory, the exception is passed on to the caller
 * of <tt>NamingManager.getStateToBind()</tt> and
 * <tt>DirectoryManager.getStateToBind()</tt>.
 * The search for other factories
 * that may produce a non-null answer is halted.
 * A factory should only throw an exception if it is sure that
 * it is the only intended factory and that no other factories
 * should be tried.
 * If this factory cannot create an object using the arguments supplied,
 * it should return null.
 * <p>
 * The <code>name</code> and <code>nameCtx</code> parameters may
 * optionally be used to specify the name of the object being created.
 * See the description of "Name and Context Parameters" in
 * {@link ObjectFactory#getObjectInstance ObjectFactory.getObjectInstance()}
 * for details.
 * If a factory uses <code>nameCtx</code> it should synchronize its use
 * against concurrent access, since context implementations are not
 * guaranteed to be thread-safe.
 * <p>
 * The <tt>name</tt> and <tt>environment</tt> parameters
 * are owned by the caller.
 * The implementation will not modify these objects or keep references
 * to them, although it may keep references to clones or copies.
 *
 * <p>
 * 
 * @param obj A non-null object whose state is to be retrieved.
 * @param name The name of this object relative to <code>nameCtx</code>,
 *              or null if no name is specified.
 * @param nameCtx The context relative to which the <code>name</code>
 *              parameter is specified, or null if <code>name</code> is
 *              relative to the default initial context.
 * @param environment The possibly null environment to
 *              be used in the creation of the object's state.
 * @return The object's state for binding;
 *              null if the factory is not returning any changes.
 * @exception NamingException if this factory encountered an exception
 * while attempting to get the object's state, and no other factories are
 * to be tried.
 *
 * @see NamingManager#getStateToBind
 * @see DirectoryManager#getStateToBind
 */
    public Object getStateToBind(Object obj, Name name, Context nameCtx,
                                 Hashtable<?,?> environment)
        throws NamingException;
}
