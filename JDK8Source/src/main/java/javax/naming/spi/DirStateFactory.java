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
import javax.naming.directory.Attributes;
import java.util.Hashtable;

/**
  * This interface represents a factory for obtaining the state of an
  * object and corresponding attributes for binding.
  *<p>
  * The JNDI framework allows for object implementations to
  * be loaded in dynamically via <tt>object factories</tt>.
  * <p>
  * A <tt>DirStateFactory</tt> extends <tt>StateFactory</tt>
  * by allowing an <tt>Attributes</tt> instance
  * to be supplied to and be returned by the <tt>getStateToBind()</tt> method.
  * <tt>DirStateFactory</tt> implementations are intended to be used by
  * <tt>DirContext</tt> service providers.
  * When a caller binds an object using <tt>DirContext.bind()</tt>,
  * he might also specify a set of attributes to be bound with the object.
  * The object and attributes to be bound are passed to
  * the <tt>getStateToBind()</tt> method of a factory.
  * If the factory processes the object and attributes, it returns
  * a corresponding pair of object and attributes to be bound.
  * If the factory does not process the object, it must return null.
  *<p>
  * For example, a caller might bind a printer object with some printer-related
  * attributes.
  *<blockquote><pre>
  * ctx.rebind("inky", printer, printerAttrs);
  *</pre></blockquote>
  * An LDAP service provider for <tt>ctx</tt> uses a <tt>DirStateFactory</tt>
  * (indirectly via <tt>DirectoryManager.getStateToBind()</tt>)
  * and gives it <tt>printer</tt> and <tt>printerAttrs</tt>. A factory for
  * an LDAP directory might turn <tt>printer</tt> into a set of attributes
  * and merge that with <tt>printerAttrs</tt>. The service provider then
  * uses the resulting attributes to create an LDAP entry and updates
  * the directory.
  *
  * <p> Since <tt>DirStateFactory</tt> extends <tt>StateFactory</tt>, it
  * has two <tt>getStateToBind()</tt> methods, where one
  * differs from the other by the attributes
  * argument. <tt>DirectoryManager.getStateToBind()</tt> will only use
  * the form that accepts the attributes argument, while
  * <tt>NamingManager.getStateToBind()</tt> will only use the form that
  * does not accept the attributes argument.
  *
  * <p> Either form of the <tt>getStateToBind()</tt> method of a
  * DirStateFactory may be invoked multiple times, possibly using different
  * parameters.  The implementation is thread-safe.
  *
  * <p>
  *  该接口表示用于获得对象的状态和用于绑定的对应属性的工厂。
  * p>
  *  JNDI框架允许通过<tt>对象工厂</tt>动态加载对象实现。
  * <p>
  *  通过允许<tt> Attributes </tt>实例提供给<tt> getStateToBind()</tt>方法并返回的<tt> DirStateFactory </tt>扩展<tt> State
  * Factory </tt> 。
  *  <tt> DirStateFactory </tt>实施旨在由<tt> DirContext </tt>服务提供商使用。
  * 当调用者使用<tt> DirContext.bind()</tt>绑定对象时,他还可以指定要与对象绑定的一组属性。
  * 要绑定的对象和属性传递到工厂的<tt> getStateToBind()</tt>方法。如果工厂处理对象和属性,则它返回一对相应的要绑定的对象和属性。如果工厂不处理对象,它必须返回null。
  * p>
  * 例如,调用者可以使用一些与打印机相关的属性来绑定打印机对象。
  *  blockquote> <pre> ctx.rebind("inky",printer,printerAttrs); / pre> </blockquote> <tt> ctx </tt>的LDAP服
  * 务提供程序使用<tt> DirStateFactory </tt>(通过<tt> DirectoryManager.getStateToBind()</tt>间接使用) tt> printer </tt>
  * 和<tt> printerAttrs </tt>。
  * 例如,调用者可以使用一些与打印机相关的属性来绑定打印机对象。 LDAP目录的工厂可能会将<tt>打印机</tt>变为一组属性,并与<tt> printerAttrs </tt>合并。
  * 然后,服务提供者使用生成的属性创建LDAP条目并更新目录。
  * 
  *  <p>由于<tt> DirStateFactory </tt>扩展了<tt> StateFactory </tt>,它有两个<tt> getStateToBind()</tt>方法,其中一个与attr
  * ibutes不同。
  *  <tt> DirectoryManager.getStateToBind()</tt>只使用接受attributes参数的形式,而<tt> NamingManager.getStateToBind()
  * </tt>只使用不接受attributes参数的形式。
  * 
  *  <p> DirStateFactory的<tt> getStateToBind()</tt>方法的任一形式都可能被调用多次,可能使用不同的参数。实现是线程安全的。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  *
  * @see DirectoryManager#getStateToBind
  * @see DirObjectFactory
  * @since 1.3
  */
public interface DirStateFactory extends StateFactory {
/**
 * Retrieves the state of an object for binding given the object and attributes
 * to be transformed.
 *<p>
 * <tt>DirectoryManager.getStateToBind()</tt>
 * successively loads in state factories. If a factory implements
 * <tt>DirStateFactory</tt>, <tt>DirectoryManager</tt> invokes this method;
 * otherwise, it invokes <tt>StateFactory.getStateToBind()</tt>.
 * It does this until a factory produces a non-null answer.
 *<p>
 * When an exception is thrown by a factory,
 * the exception is passed on to the caller
 * of <tt>DirectoryManager.getStateToBind()</tt>. The search for other factories
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
 *<p>
 * The <tt>name</tt>, <tt>inAttrs</tt>, and <tt>environment</tt> parameters
 * are owned by the caller.
 * The implementation will not modify these objects or keep references
 * to them, although it may keep references to clones or copies.
 * The object returned by this method is owned by the caller.
 * The implementation will not subsequently modify it.
 * It will contain either a new <tt>Attributes</tt> object that is
 * likewise owned by the caller, or a reference to the original
 * <tt>inAttrs</tt> parameter.
 *
 * <p>
 *  在给定要转换的对象和属性的情况下检索对象的状态。
 * p>
 * <tt> DirectoryManager.getStateToBind()</tt>连续加载状态工厂。
 * 如果工厂实现<tt> DirStateFactory </tt>,<tt> DirectoryManager </tt>调用此方法;否则,它调用<tt> StateFactory.getStateToB
 * ind()</tt>。
 * <tt> DirectoryManager.getStateToBind()</tt>连续加载状态工厂。它这样做,直到工厂产生一个非null的答案。
 * p>
 *  当工厂抛出异常时,异常将传递给<tt> DirectoryManager.getStateToBind()</tt>的调用者。对可能产生非空答案的其他工厂的搜索被停止。
 * 一个工厂只应该抛出一个例外,如果它确定它是唯一的工厂,不应该尝试其他工厂。如果此工厂无法使用提供的参数创建对象,则应返回null。
 * <p>
 *  可以可选地使用<code> name </code>和<code> nameCtx </code>参数来指定正在创建的对象的名称。
 * 有关详细信息,请参阅{@link ObjectFactory#getObjectInstance ObjectFactory.getObjectInstance()}中的"名称和上下文参数"的描述。
 * 如果工厂使用<code> nameCtx </code>,它应该将其使用与同时访问同步,因为上下文实现不能保证是线程安全的。
 * p>
 * <tt>名称</tt>,<tt> inAttrs </tt>和<tt>环境</tt>参数由调用者拥有。实现不会修改这些对象或保留对它们的引用,尽管它可能保留对克隆或副本的引用。
 * 此方法返回的对象由调用者拥有。实现将不会随后修改它。它将包含同样由调用者拥有的一个新<tt> Attributes </tt>对象,或者是对原始<tt> inAttrs </tt>参数的引用。
 * 
 * 
 * @param obj A possibly null object whose state is to be retrieved.
 * @param name The name of this object relative to <code>nameCtx</code>,
 *              or null if no name is specified.
 * @param nameCtx The context relative to which the <code>name</code>
 *              parameter is specified, or null if <code>name</code> is
 *              relative to the default initial context.
 * @param environment The possibly null environment to
 *              be used in the creation of the object's state.
 * @param inAttrs The possibly null attributes to be bound with the object.
 *      The factory must not modify <tt>inAttrs</tt>.
 * @return A <tt>Result</tt> containing the object's state for binding
 * and the corresponding
 * attributes to be bound; null if the object don't use this factory.
 * @exception NamingException If this factory encountered an exception
 * while attempting to get the object's state, and no other factories are
 * to be tried.
 *
 * @see DirectoryManager#getStateToBind
 */
    public Result getStateToBind(Object obj, Name name, Context nameCtx,
                                 Hashtable<?,?> environment,
                                 Attributes inAttrs)
        throws NamingException;


        /**
         * An object/attributes pair for returning the result of
         * DirStateFactory.getStateToBind().
         * <p>
         *  用于返回DirStateFactory.getStateToBind()的结果的对象/属性对。
         * 
         */
    public static class Result {
        /**
         * The possibly null object to be bound.
         * <p>
         *  要绑定的可能为null的对象。
         * 
         */
        private Object obj;


        /**
         * The possibly null attributes to be bound.
         * <p>
         *  要绑定的可能为null的属性。
         * 
         */
        private Attributes attrs;

        /**
          * Constructs an instance of Result.
          *
          * <p>
          *  构造Result的实例。
          * 
          * 
          * @param obj The possibly null object to be bound.
          * @param outAttrs The possibly null attributes to be bound.
          */
        public Result(Object obj, Attributes outAttrs) {
            this.obj = obj;
            this.attrs = outAttrs;
        }

        /**
         * Retrieves the object to be bound.
         * <p>
         *  检索要绑定的对象。
         * 
         * 
         * @return The possibly null object to be bound.
         */
        public Object getObject() { return obj; };

        /**
         * Retrieves the attributes to be bound.
         * <p>
         *  检索要绑定的属性。
         * 
         * @return The possibly null attributes to be bound.
         */
        public Attributes getAttributes() { return attrs; };

    }
}
