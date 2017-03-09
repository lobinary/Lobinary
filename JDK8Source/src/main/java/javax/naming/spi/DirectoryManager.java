/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2011, Oracle and/or its affiliates. All rights reserved.
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

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.naming.NamingException;
import javax.naming.CannotProceedException;
import javax.naming.directory.DirContext;
import javax.naming.directory.Attributes;

import com.sun.naming.internal.ResourceManager;
import com.sun.naming.internal.FactoryEnumeration;


/**
  * This class contains methods for supporting <tt>DirContext</tt>
  * implementations.
  *<p>
  * This class is an extension of <tt>NamingManager</tt>.  It contains methods
  * for use by service providers for accessing object factories and
  * state factories, and for getting continuation contexts for
  * supporting federation.
  *<p>
  * <tt>DirectoryManager</tt> is safe for concurrent access by multiple threads.
  *<p>
  * Except as otherwise noted,
  * a <tt>Name</tt>, <tt>Attributes</tt>, or environment parameter
  * passed to any method is owned by the caller.
  * The implementation will not modify the object or keep a reference
  * to it, although it may keep a reference to a clone or copy.
  *
  * <p>
  *  此类包含支持<tt> DirContext </tt>实现的方法。
  * p>
  *  此类是<tt> NamingManager </tt>的扩展。它包含服务提供程序用于访问对象工厂和状态工厂以及获取用于支持联合的继续上下文的方法。
  * p>
  *  <tt> DirectoryManager </tt>对于多线程的并发访问是安全的。
  * p>
  *  除非另有说明,传递给任何方法的<tt> Name </tt>,<tt> Attributes </tt>或环境参数由调用者拥有。实现不会修改对象或保留对它的引用,虽然它可能保留对克隆或副本的引用。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  *
  * @see DirObjectFactory
  * @see DirStateFactory
  * @since 1.3
  */

public class DirectoryManager extends NamingManager {

    /*
     * Disallow anyone from creating one of these.
     * <p>
     *  禁止任何人创建其中的一个。
     * 
     */
    DirectoryManager() {}

    /**
      * Creates a context in which to continue a <tt>DirContext</tt> operation.
      * Operates just like <tt>NamingManager.getContinuationContext()</tt>,
      * only the continuation context returned is a <tt>DirContext</tt>.
      *
      * <p>
      *  创建用于继续执行<tt> DirContext </tt>操作的上下文。
      * 像<tt> NamingManager.getContinuationContext()</tt>一样操作,只有返回的继续上下文是一个<tt> DirContext </tt>。
      * 
      * 
      * @param cpe
      *         The non-null exception that triggered this continuation.
      * @return A non-null <tt>DirContext</tt> object for continuing the operation.
      * @exception NamingException If a naming exception occurred.
      *
      * @see NamingManager#getContinuationContext(CannotProceedException)
      */
    @SuppressWarnings("unchecked")
    public static DirContext getContinuationDirContext(
            CannotProceedException cpe) throws NamingException {

        Hashtable<Object,Object> env = (Hashtable<Object,Object>)cpe.getEnvironment();
        if (env == null) {
            env = new Hashtable<>(7);
        } else {
            // Make a (shallow) copy of the environment.
            env = (Hashtable<Object,Object>) env.clone();
        }
        env.put(CPE, cpe);

        return (new ContinuationDirContext(cpe, env));
    }

    /**
      * Creates an instance of an object for the specified object,
      * attributes, and environment.
      * <p>
      * This method is the same as <tt>NamingManager.getObjectInstance</tt>
      * except for the following differences:
      *<ul>
      *<li>
      * It accepts an <tt>Attributes</tt> parameter that contains attributes
      * associated with the object. The <tt>DirObjectFactory</tt> might use these
      * attributes to save having to look them up from the directory.
      *<li>
      * The object factories tried must implement either
      * <tt>ObjectFactory</tt> or <tt>DirObjectFactory</tt>.
      * If it implements <tt>DirObjectFactory</tt>,
      * <tt>DirObjectFactory.getObjectInstance()</tt> is used, otherwise,
      * <tt>ObjectFactory.getObjectInstance()</tt> is used.
      *</ul>
      * Service providers that implement the <tt>DirContext</tt> interface
      * should use this method, not <tt>NamingManager.getObjectInstance()</tt>.
      *<p>
      *
      * <p>
      *  为指定的对象,属性和环境创建对象的实例。
      * <p>
      *  此方法与<tt> NamingManager.getObjectInstance </tt>相同,但以下区别除外：
      * ul>
      * li>
      *  它接受包含与对象关联的属性的<tt> Attributes </tt>参数。 <tt> DirObjectFactory </tt>可能使用这些属性来保存,不得不从目录中查找它们。
      * li>
      * 对象工厂必须实现<tt> ObjectFactory </tt>或<tt> DirObjectFactory </tt>。
      * 如果它实现<tt> DirObjectFactory </tt>,则使用<tt> DirObjectFactory.getObjectInstance()</tt>,否则使用<tt> ObjectFac
      * tory.getObjectInstance()</tt>。
      * 对象工厂必须实现<tt> ObjectFactory </tt>或<tt> DirObjectFactory </tt>。
      * /ul>
      *  实现<tt> DirContext </tt>接口的服务提供者应该使用此方法,而不是<tt> NamingManager.getObjectInstance()</tt>。
      * p>
      * 
      * 
      * @param refInfo The possibly null object for which to create an object.
      * @param name The name of this object relative to <code>nameCtx</code>.
      *         Specifying a name is optional; if it is
      *         omitted, <code>name</code> should be null.
      * @param nameCtx The context relative to which the <code>name</code>
      *         parameter is specified.  If null, <code>name</code> is
      *         relative to the default initial context.
      * @param environment The possibly null environment to
      *         be used in the creation of the object factory and the object.
      * @param attrs The possibly null attributes associated with refInfo.
      *         This might not be the complete set of attributes for refInfo;
      *         you might be able to read more attributes from the directory.
      * @return An object created using <code>refInfo</code> and <tt>attrs</tt>; or
      *         <code>refInfo</code> if an object cannot be created by
      *         a factory.
      * @exception NamingException If a naming exception was encountered
      *         while attempting to get a URL context, or if one of the
      *         factories accessed throws a NamingException.
      * @exception Exception If one of the factories accessed throws an
      *         exception, or if an error was encountered while loading
      *         and instantiating the factory and object classes.
      *         A factory should only throw an exception if it does not want
      *         other factories to be used in an attempt to create an object.
      *         See <tt>DirObjectFactory.getObjectInstance()</tt>.
      * @see NamingManager#getURLContext
      * @see DirObjectFactory
      * @see DirObjectFactory#getObjectInstance
      * @since 1.3
      */
    public static Object
        getObjectInstance(Object refInfo, Name name, Context nameCtx,
                          Hashtable<?,?> environment, Attributes attrs)
        throws Exception {

            ObjectFactory factory;

            ObjectFactoryBuilder builder = getObjectFactoryBuilder();
            if (builder != null) {
                // builder must return non-null factory
                factory = builder.createObjectFactory(refInfo, environment);
                if (factory instanceof DirObjectFactory) {
                    return ((DirObjectFactory)factory).getObjectInstance(
                        refInfo, name, nameCtx, environment, attrs);
                } else {
                    return factory.getObjectInstance(refInfo, name, nameCtx,
                        environment);
                }
            }

            // use reference if possible
            Reference ref = null;
            if (refInfo instanceof Reference) {
                ref = (Reference) refInfo;
            } else if (refInfo instanceof Referenceable) {
                ref = ((Referenceable)(refInfo)).getReference();
            }

            Object answer;

            if (ref != null) {
                String f = ref.getFactoryClassName();
                if (f != null) {
                    // if reference identifies a factory, use exclusively

                    factory = getObjectFactoryFromReference(ref, f);
                    if (factory instanceof DirObjectFactory) {
                        return ((DirObjectFactory)factory).getObjectInstance(
                            ref, name, nameCtx, environment, attrs);
                    } else if (factory != null) {
                        return factory.getObjectInstance(ref, name, nameCtx,
                                                         environment);
                    }
                    // No factory found, so return original refInfo.
                    // Will reach this point if factory class is not in
                    // class path and reference does not contain a URL for it
                    return refInfo;

                } else {
                    // if reference has no factory, check for addresses
                    // containing URLs
                    // ignore name & attrs params; not used in URL factory

                    answer = processURLAddrs(ref, name, nameCtx, environment);
                    if (answer != null) {
                        return answer;
                    }
                }
            }

            // try using any specified factories
            answer = createObjectFromFactories(refInfo, name, nameCtx,
                                               environment, attrs);
            return (answer != null) ? answer : refInfo;
    }

    private static Object createObjectFromFactories(Object obj, Name name,
            Context nameCtx, Hashtable<?,?> environment, Attributes attrs)
        throws Exception {

        FactoryEnumeration factories = ResourceManager.getFactories(
            Context.OBJECT_FACTORIES, environment, nameCtx);

        if (factories == null)
            return null;

        ObjectFactory factory;
        Object answer = null;
        // Try each factory until one succeeds
        while (answer == null && factories.hasMore()) {
            factory = (ObjectFactory)factories.next();
            if (factory instanceof DirObjectFactory) {
                answer = ((DirObjectFactory)factory).
                    getObjectInstance(obj, name, nameCtx, environment, attrs);
            } else {
                answer =
                    factory.getObjectInstance(obj, name, nameCtx, environment);
            }
        }
        return answer;
    }

    /**
      * Retrieves the state of an object for binding when given the original
      * object and its attributes.
      * <p>
      * This method is like <tt>NamingManager.getStateToBind</tt> except
      * for the following differences:
      *<ul>
      *<li>It accepts an <tt>Attributes</tt> parameter containing attributes
      *    that were passed to the <tt>DirContext.bind()</tt> method.
      *<li>It returns a non-null <tt>DirStateFactory.Result</tt> instance
      *    containing the object to be bound, and the attributes to
      *    accompany the binding. Either the object or the attributes may be null.
      *<li>
      * The state factories tried must each implement either
      * <tt>StateFactory</tt> or <tt>DirStateFactory</tt>.
      * If it implements <tt>DirStateFactory</tt>, then
      * <tt>DirStateFactory.getStateToBind()</tt> is called; otherwise,
      * <tt>StateFactory.getStateToBind()</tt> is called.
      *</ul>
      *
      * Service providers that implement the <tt>DirContext</tt> interface
      * should use this method, not <tt>NamingManager.getStateToBind()</tt>.
      *<p>
      * See NamingManager.getStateToBind() for a description of how
      * the list of state factories to be tried is determined.
      *<p>
      * The object returned by this method is owned by the caller.
      * The implementation will not subsequently modify it.
      * It will contain either a new <tt>Attributes</tt> object that is
      * likewise owned by the caller, or a reference to the original
      * <tt>attrs</tt> parameter.
      *
      * <p>
      *  当给定原始对象及其属性时,检索要绑定的对象的状态。
      * <p>
      *  此方法类似于<tt> NamingManager.getStateToBind </tt>,但以下区别除外：
      * ul>
      *  li>它接受包含传递给<tt> DirContext.bind()</tt>方法的属性的<tt> Attributes </tt>参数。
      *  li>它返回一个非空的<tt> DirStateFactory.Result </tt>实例,包含要绑定的对象和绑定的属性。对象或属性可以为null。
      * li>
      *  所尝试的状态工厂必须分别实现<tt> StateFactory </tt>或<tt> DirStateFactory </tt>。
      * 如果它实现<tt> DirStateFactory </tt>,则调用<tt> DirStateFactory.getStateToBind()</tt>否则,将调用<tt> StateFactory.
      * getStateToBind()</tt>。
      * 
      * @param obj The non-null object for which to get state to bind.
      * @param name The name of this object relative to <code>nameCtx</code>,
      *         or null if no name is specified.
      * @param nameCtx The context relative to which the <code>name</code>
      *         parameter is specified, or null if <code>name</code> is
      *         relative to the default initial context.
      * @param environment The possibly null environment to
      *         be used in the creation of the state factory and
      *         the object's state.
      * @param attrs The possibly null Attributes that is to be bound with the
      *         object.
      * @return A non-null DirStateFactory.Result containing
      *  the object and attributes to be bound.
      *  If no state factory returns a non-null answer, the result will contain
      *  the object (<tt>obj</tt>) itself with the original attributes.
      * @exception NamingException If a naming exception was encountered
      *         while using the factories.
      *         A factory should only throw an exception if it does not want
      *         other factories to be used in an attempt to create an object.
      *         See <tt>DirStateFactory.getStateToBind()</tt>.
      * @see DirStateFactory
      * @see DirStateFactory#getStateToBind
      * @see NamingManager#getStateToBind
      * @since 1.3
      */
    public static DirStateFactory.Result
        getStateToBind(Object obj, Name name, Context nameCtx,
                       Hashtable<?,?> environment, Attributes attrs)
        throws NamingException {

        // Get list of state factories
        FactoryEnumeration factories = ResourceManager.getFactories(
            Context.STATE_FACTORIES, environment, nameCtx);

        if (factories == null) {
            // no factories to try; just return originals
            return new DirStateFactory.Result(obj, attrs);
        }

        // Try each factory until one succeeds
        StateFactory factory;
        Object objanswer;
        DirStateFactory.Result answer = null;
        while (answer == null && factories.hasMore()) {
            factory = (StateFactory)factories.next();
            if (factory instanceof DirStateFactory) {
                answer = ((DirStateFactory)factory).
                    getStateToBind(obj, name, nameCtx, environment, attrs);
            } else {
                objanswer =
                    factory.getStateToBind(obj, name, nameCtx, environment);
                if (objanswer != null) {
                    answer = new DirStateFactory.Result(objanswer, attrs);
                }
            }
        }

        return (answer != null) ? answer :
            new DirStateFactory.Result(obj, attrs); // nothing new
    }
}
