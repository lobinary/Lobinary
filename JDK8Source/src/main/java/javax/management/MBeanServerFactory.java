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

package javax.management;

import com.sun.jmx.defaults.JmxProperties;
import static com.sun.jmx.defaults.JmxProperties.JMX_INITIAL_BUILDER;
import static com.sun.jmx.defaults.JmxProperties.MBEANSERVER_LOGGER;
import com.sun.jmx.mbeanserver.GetPropertyAction;
import java.security.AccessController;
import java.security.Permission;
import java.util.ArrayList;
import java.util.logging.Level;
import javax.management.loading.ClassLoaderRepository;
import sun.reflect.misc.ReflectUtil;


/**
 * <p>Provides MBean server references.  There are no instances of
 * this class.</p>
 *
 * <p>Since JMX 1.2 this class makes it possible to replace the default
 * MBeanServer implementation. This is done using the
 * {@link javax.management.MBeanServerBuilder} class.
 * The class of the initial MBeanServerBuilder to be
 * instantiated can be specified through the
 * <b>javax.management.builder.initial</b> system property.
 * The specified class must be a public subclass of
 * {@link javax.management.MBeanServerBuilder}, and must have a public
 * empty constructor.
 * <p>By default, if no value for that property is specified, an instance of
 * {@link
 * javax.management.MBeanServerBuilder javax.management.MBeanServerBuilder}
 * is created. Otherwise, the MBeanServerFactory attempts to load the
 * specified class using
 * {@link java.lang.Thread#getContextClassLoader()
 *   Thread.currentThread().getContextClassLoader()}, or if that is null,
 * {@link java.lang.Class#forName(java.lang.String) Class.forName()}. Then
 * it creates an initial instance of that Class using
 * {@link java.lang.Class#newInstance()}. If any checked exception
 * is raised during this process (e.g.
 * {@link java.lang.ClassNotFoundException},
 * {@link java.lang.InstantiationException}) the MBeanServerFactory
 * will propagate this exception from within a RuntimeException.</p>
 *
 * <p>The <b>javax.management.builder.initial</b> system property is
 * consulted every time a new MBeanServer needs to be created, and the
 * class pointed to by that property is loaded. If that class is different
 * from that of the current MBeanServerBuilder, then a new MBeanServerBuilder
 * is created. Otherwise, the MBeanServerFactory may create a new
 * MBeanServerBuilder or reuse the current one.</p>
 *
 * <p>If the class pointed to by the property cannot be
 * loaded, or does not correspond to a valid subclass of MBeanServerBuilder
 * then an exception is propagated, and no MBeanServer can be created until
 * the <b>javax.management.builder.initial</b> system property is reset to
 * valid value.</p>
 *
 * <p>The MBeanServerBuilder makes it possible to wrap the MBeanServers
 * returned by the default MBeanServerBuilder implementation, for the purpose
 * of e.g. adding an additional security layer.</p>
 *
 * <p>
 *  <p>提供MBean服务器引用。没有此类的实例。</p>
 * 
 *  <p>由于JMX 1.2这个类可以替换默认的MBeanServer实现。这是使用{@link javax.management.MBeanServerBuilder}类完成的。
 * 要实例化的初始MBeanServerBuilder的类可以通过<b> javax.management.builder.initial </b>系统属性指定。
 * 指定的类必须是{@link javax.management.MBeanServerBuilder}的公共子类,并且必须具有公共的空构造函数。
 *  <p>默认情况下,如果未指定该属性的值,则会创建{@link javax.management.MBeanServerBuilder javax.management.MBeanServerBuilder}
 * 的实例。
 * 指定的类必须是{@link javax.management.MBeanServerBuilder}的公共子类,并且必须具有公共的空构造函数。
 * 否则,MBeanServerFactory尝试使用{@link java.lang.Thread#getContextClassLoader()Thread.currentThread()。
 * getContextClassLoader()}加载指定的类,或者如果它为null,{@link java.lang.Class#forName (java.lang.String)Class.forName()}
 * 。
 * 否则,MBeanServerFactory尝试使用{@link java.lang.Thread#getContextClassLoader()Thread.currentThread()。
 * 然后使用{@link java.lang.Class#newInstance()}创建该类的初始实例。
 * 如果在此过程中引发任何检查异常(例如{@link java.lang.ClassNotFoundException},{@link java.lang.InstantiationException}),
 * MBeanServerFactory将在RuntimeException中传播此异常。
 * 然后使用{@link java.lang.Class#newInstance()}创建该类的初始实例。</p>。
 * 
 * <p>每次需要创建一个新的MBeanServer时都会查询<b> javax.management.builder.initial </b>系统属性,并加载该属性指向的类。
 * 如果该类与当前MBeanServerBuilder的类不同,则会创建一个新的MBeanServerBuilder。
 * 否则,MBeanServerFactory可能会创建一个新的MBeanServerBuilder或重用当前的MBeanServerBuilder。</p>。
 * 
 *  <p>如果属性指向的类无法加载,或者不对应于MBeanServerBuilder的有效子类,那么会传播异常,并且不能创建MBeanServer,直到<b> javax.management.build
 * er.initial < / b>系统属性重置为有效值。
 * </p>。
 * 
 *  <p> MBeanServerBuilder可以包装由默认MBeanServerBuilder实现返回的MBeanServers,例如添加额外的安全层。</p>
 * 
 * 
 * @since 1.5
 */
public class MBeanServerFactory {

    /*
     * There are no instances of this class so don't generate the
     * default public constructor.
     * <p>
     *  没有这个类的实例,所以不生成默认的公共构造函数。
     * 
     */
    private MBeanServerFactory() {

    }

    /**
     * The builder that will be used to construct MBeanServers.
     *
     * <p>
     *  将用于构建MBeanServers的构建器。
     * 
     * 
     **/
    private static MBeanServerBuilder builder = null;

    /**
     * Provide a new {@link javax.management.MBeanServerBuilder}.
     * <p>
     *  提供一个新的{@link javax.management.MBeanServerBuilder}。
     * 
     * 
     * @param builder The new MBeanServerBuilder that will be used to
     *        create {@link javax.management.MBeanServer}s.
     * @exception IllegalArgumentException if the given builder is null.
     *
     * @exception SecurityException if there is a SecurityManager and
     * the caller's permissions do not include or imply <code>{@link
     * MBeanServerPermission}("setMBeanServerBuilder")</code>.
     *
     **/
    // public static synchronized void
    //    setMBeanServerBuilder(MBeanServerBuilder builder) {
    //    checkPermission("setMBeanServerBuilder");
    //    MBeanServerFactory.builder = builder;
    // }

    /**
     * Get the current {@link javax.management.MBeanServerBuilder}.
     *
     * <p>
     *  获取当前的{@link javax.management.MBeanServerBuilder}。
     * 
     * 
     * @return the current {@link javax.management.MBeanServerBuilder}.
     *
     * @exception SecurityException if there is a SecurityManager and
     * the caller's permissions do not include or imply <code>{@link
     * MBeanServerPermission}("getMBeanServerBuilder")</code>.
     *
     **/
    // public static synchronized MBeanServerBuilder getMBeanServerBuilder() {
    //     checkPermission("getMBeanServerBuilder");
    //     return builder;
    // }

    /**
     * Remove internal MBeanServerFactory references to a created
     * MBeanServer. This allows the garbage collector to remove the
     * MBeanServer object.
     *
     * <p>
     *  删除对创建的MBeanServer的内部MBeanServerFactory引用。这允许垃圾收集器删除MBeanServer对象。
     * 
     * 
     * @param mbeanServer the MBeanServer object to remove.
     *
     * @exception java.lang.IllegalArgumentException if
     * <code>mbeanServer</code> was not generated by one of the
     * <code>createMBeanServer</code> methods, or if
     * <code>releaseMBeanServer</code> was already called on it.
     *
     * @exception SecurityException if there is a SecurityManager and
     * the caller's permissions do not include or imply <code>{@link
     * MBeanServerPermission}("releaseMBeanServer")</code>.
     */
    public static void releaseMBeanServer(MBeanServer mbeanServer) {
        checkPermission("releaseMBeanServer");

        removeMBeanServer(mbeanServer);
    }

    /**
     * <p>Return a new object implementing the MBeanServer interface
     * with a standard default domain name.  The default domain name
     * is used as the domain part in the ObjectName of MBeans when the
     * domain is specified by the user is null.</p>
     *
     * <p>The standard default domain name is
     * <code>DefaultDomain</code>.</p>
     *
     * <p>The MBeanServer reference is internally kept. This will
     * allow <CODE>findMBeanServer</CODE> to return a reference to
     * this MBeanServer object.</p>
     *
     * <p>This method is equivalent to <code>createMBeanServer(null)</code>.
     *
     * <p>
     * <p>使用标准默认域名返回实现MBeanServer接口的新对象。当用户指定的域为空时,默认域名用作MBeans的ObjectName中的域部分。</p>
     * 
     *  <p>标准默认域名为<code> DefaultDomain </code>。</p>
     * 
     *  <p> MBeanServer引用内部保留。这将允许<CODE> findMBeanServer </CODE>返回对此MBeanServer对象的引用。</p>
     * 
     *  <p>此方法等效于<code> createMBeanServer(null)</code>。
     * 
     * 
     * @return the newly created MBeanServer.
     *
     * @exception SecurityException if there is a SecurityManager and the
     * caller's permissions do not include or imply <code>{@link
     * MBeanServerPermission}("createMBeanServer")</code>.
     *
     * @exception JMRuntimeException if the property
     * <code>javax.management.builder.initial</code> exists but the
     * class it names cannot be instantiated through a public
     * no-argument constructor; or if the instantiated builder returns
     * null from its {@link MBeanServerBuilder#newMBeanServerDelegate
     * newMBeanServerDelegate} or {@link
     * MBeanServerBuilder#newMBeanServer newMBeanServer} methods.
     *
     * @exception ClassCastException if the property
     * <code>javax.management.builder.initial</code> exists and can be
     * instantiated but is not assignment compatible with {@link
     * MBeanServerBuilder}.
     */
    public static MBeanServer createMBeanServer() {
        return createMBeanServer(null);
    }

    /**
     * <p>Return a new object implementing the {@link MBeanServer}
     * interface with the specified default domain name.  The given
     * domain name is used as the domain part in the ObjectName of
     * MBeans when the domain is specified by the user is null.</p>
     *
     * <p>The MBeanServer reference is internally kept. This will
     * allow <CODE>findMBeanServer</CODE> to return a reference to
     * this MBeanServer object.</p>
     *
     * <p>
     *  <p>返回实现具有指定默认域名的{@link MBeanServer}接口的新对象。当用户指定的域为空时,给定的域名用作MBeans的ObjectName中的域部分。</p>
     * 
     *  <p> MBeanServer引用内部保留。这将允许<CODE> findMBeanServer </CODE>返回对此MBeanServer对象的引用。</p>
     * 
     * 
     * @param domain the default domain name for the created
     * MBeanServer.  This is the value that will be returned by {@link
     * MBeanServer#getDefaultDomain}.
     *
     * @return the newly created MBeanServer.
     *
     * @exception SecurityException if there is a SecurityManager and
     * the caller's permissions do not include or imply <code>{@link
     * MBeanServerPermission}("createMBeanServer")</code>.
     *
     * @exception JMRuntimeException if the property
     * <code>javax.management.builder.initial</code> exists but the
     * class it names cannot be instantiated through a public
     * no-argument constructor; or if the instantiated builder returns
     * null from its {@link MBeanServerBuilder#newMBeanServerDelegate
     * newMBeanServerDelegate} or {@link
     * MBeanServerBuilder#newMBeanServer newMBeanServer} methods.
     *
     * @exception ClassCastException if the property
     * <code>javax.management.builder.initial</code> exists and can be
     * instantiated but is not assignment compatible with {@link
     * MBeanServerBuilder}.
     */
    public static MBeanServer createMBeanServer(String domain)  {
        checkPermission("createMBeanServer");

        final MBeanServer mBeanServer = newMBeanServer(domain);
        addMBeanServer(mBeanServer);
        return mBeanServer;
    }

    /**
     * <p>Return a new object implementing the MBeanServer interface
     * with a standard default domain name, without keeping an
     * internal reference to this new object.  The default domain name
     * is used as the domain part in the ObjectName of MBeans when the
     * domain is specified by the user is null.</p>
     *
     * <p>The standard default domain name is
     * <code>DefaultDomain</code>.</p>
     *
     * <p>No reference is kept. <CODE>findMBeanServer</CODE> will not
     * be able to return a reference to this MBeanServer object, but
     * the garbage collector will be able to remove the MBeanServer
     * object when it is no longer referenced.</p>
     *
     * <p>This method is equivalent to <code>newMBeanServer(null)</code>.</p>
     *
     * <p>
     *  <p>使用标准默认域名返回实现MBeanServer接口的新对象,而不保留对此新对象的内部引用。当用户指定的域为空时,默认域名用作MBeans的ObjectName中的域部分。</p>
     * 
     *  <p>标准默认域名为<code> DefaultDomain </code>。</p>
     * 
     * <p>不保留引用。 <CODE> findMBeanServer </CODE>将无法返回对此MBeanServer对象的引用,但是当不再引用MBeanServer对象时,垃圾回收器将能够删除它。
     * </p>。
     * 
     *  <p>此方法等效于<code> newMBeanServer(null)</code>。</p>
     * 
     * 
     * @return the newly created MBeanServer.
     *
     * @exception SecurityException if there is a SecurityManager and the
     * caller's permissions do not include or imply <code>{@link
     * MBeanServerPermission}("newMBeanServer")</code>.
     *
     * @exception JMRuntimeException if the property
     * <code>javax.management.builder.initial</code> exists but the
     * class it names cannot be instantiated through a public
     * no-argument constructor; or if the instantiated builder returns
     * null from its {@link MBeanServerBuilder#newMBeanServerDelegate
     * newMBeanServerDelegate} or {@link
     * MBeanServerBuilder#newMBeanServer newMBeanServer} methods.
     *
     * @exception ClassCastException if the property
     * <code>javax.management.builder.initial</code> exists and can be
     * instantiated but is not assignment compatible with {@link
     * MBeanServerBuilder}.
     */
    public static MBeanServer newMBeanServer() {
        return newMBeanServer(null);
    }

    /**
     * <p>Return a new object implementing the MBeanServer interface
     * with the specified default domain name, without keeping an
     * internal reference to this new object.  The given domain name
     * is used as the domain part in the ObjectName of MBeans when the
     * domain is specified by the user is null.</p>
     *
     * <p>No reference is kept. <CODE>findMBeanServer</CODE> will not
     * be able to return a reference to this MBeanServer object, but
     * the garbage collector will be able to remove the MBeanServer
     * object when it is no longer referenced.</p>
     *
     * <p>
     *  <p>返回实现具有指定默认域名的MBeanServer接口的新对象,而不保留对此新对象的内部引用。当用户指定的域为空时,给定的域名用作MBeans的ObjectName中的域部分。</p>
     * 
     *  <p>不保留引用。 <CODE> findMBeanServer </CODE>将无法返回对此MBeanServer对象的引用,但是当不再引用MBeanServer对象时,垃圾回收器将能够删除它。
     * </p>。
     * 
     * 
     * @param domain the default domain name for the created
     * MBeanServer.  This is the value that will be returned by {@link
     * MBeanServer#getDefaultDomain}.
     *
     * @return the newly created MBeanServer.
     *
     * @exception SecurityException if there is a SecurityManager and the
     * caller's permissions do not include or imply <code>{@link
     * MBeanServerPermission}("newMBeanServer")</code>.
     *
     * @exception JMRuntimeException if the property
     * <code>javax.management.builder.initial</code> exists but the
     * class it names cannot be instantiated through a public
     * no-argument constructor; or if the instantiated builder returns
     * null from its {@link MBeanServerBuilder#newMBeanServerDelegate
     * newMBeanServerDelegate} or {@link
     * MBeanServerBuilder#newMBeanServer newMBeanServer} methods.
     *
     * @exception ClassCastException if the property
     * <code>javax.management.builder.initial</code> exists and can be
     * instantiated but is not assignment compatible with {@link
     * MBeanServerBuilder}.
     */
    public static MBeanServer newMBeanServer(String domain)  {
        checkPermission("newMBeanServer");

        // Get the builder. Creates a new one if necessary.
        //
        final MBeanServerBuilder mbsBuilder = getNewMBeanServerBuilder();
        // Returned value cannot be null.  NullPointerException if violated.

        synchronized(mbsBuilder) {
            final MBeanServerDelegate delegate  =
                    mbsBuilder.newMBeanServerDelegate();
            if (delegate == null) {
                final String msg =
                        "MBeanServerBuilder.newMBeanServerDelegate() " +
                        "returned null";
                throw new JMRuntimeException(msg);
            }
            final MBeanServer mbeanServer =
                    mbsBuilder.newMBeanServer(domain,null,delegate);
            if (mbeanServer == null) {
                final String msg =
                        "MBeanServerBuilder.newMBeanServer() returned null";
                throw new JMRuntimeException(msg);
            }
            return mbeanServer;
        }
    }

    /**
     * <p>Return a list of registered MBeanServer objects.  A
     * registered MBeanServer object is one that was created by one of
     * the <code>createMBeanServer</code> methods and not subsequently
     * released with <code>releaseMBeanServer</code>.</p>
     *
     * <p>
     *  <p>返回已注册的MBeanServer对象的列表。
     * 注册的MBeanServer对象是由<code> createMBeanServer </code>方法之一创建的对象,随后不会随<code> releaseMBeanServer </code>发布。
     *  <p>返回已注册的MBeanServer对象的列表。
     * 
     * 
     * @param agentId The agent identifier of the MBeanServer to
     * retrieve.  If this parameter is null, all registered
     * MBeanServers in this JVM are returned.  Otherwise, only
     * MBeanServers whose id is equal to <code>agentId</code> are
     * returned.  The id of an MBeanServer is the
     * <code>MBeanServerId</code> attribute of its delegate MBean.
     *
     * @return A list of MBeanServer objects.
     *
     * @exception SecurityException if there is a SecurityManager and the
     * caller's permissions do not include or imply <code>{@link
     * MBeanServerPermission}("findMBeanServer")</code>.
     */
    public synchronized static
            ArrayList<MBeanServer> findMBeanServer(String agentId) {

        checkPermission("findMBeanServer");

        if (agentId == null)
            return new ArrayList<MBeanServer>(mBeanServerList);

        ArrayList<MBeanServer> result = new ArrayList<MBeanServer>();
        for (MBeanServer mbs : mBeanServerList) {
            String name = mBeanServerId(mbs);
            if (agentId.equals(name))
                result.add(mbs);
        }
        return result;
    }

    /**
     * Return the ClassLoaderRepository used by the given MBeanServer.
     * This method is equivalent to {@link
     * MBeanServer#getClassLoaderRepository() server.getClassLoaderRepository()}.
     * <p>
     *  返回给定MBeanServer使用的ClassLoaderRepository。
     * 此方法等效于{@link MBeanServer#getClassLoaderRepository()server.getClassLoaderRepository()}。
     * 
     * 
     * @param server The MBeanServer under examination. Since JMX 1.2,
     * if <code>server</code> is <code>null</code>, the result is a
     * {@link NullPointerException}.  This behavior differs from what
     * was implemented in JMX 1.1 - where the possibility to use
     * <code>null</code> was deprecated.
     * @return The Class Loader Repository used by the given MBeanServer.
     * @exception SecurityException if there is a SecurityManager and
     * the caller's permissions do not include or imply <code>{@link
     * MBeanPermission}("getClassLoaderRepository")</code>.
     *
     * @exception NullPointerException if <code>server</code> is null.
     *
     **/
    public static ClassLoaderRepository getClassLoaderRepository(
            MBeanServer server) {
        return server.getClassLoaderRepository();
    }

    private static String mBeanServerId(MBeanServer mbs) {
        try {
            return (String) mbs.getAttribute(MBeanServerDelegate.DELEGATE_NAME,
                    "MBeanServerId");
        } catch (JMException e) {
            JmxProperties.MISC_LOGGER.finest(
                    "Ignoring exception while getting MBeanServerId: "+e);
            return null;
        }
    }

    private static void checkPermission(String action)
    throws SecurityException {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            Permission perm = new MBeanServerPermission(action);
            sm.checkPermission(perm);
        }
    }

    private static synchronized void addMBeanServer(MBeanServer mbs) {
        mBeanServerList.add(mbs);
    }

    private static synchronized void removeMBeanServer(MBeanServer mbs) {
        boolean removed = mBeanServerList.remove(mbs);
        if (!removed) {
            MBEANSERVER_LOGGER.logp(Level.FINER,
                    MBeanServerFactory.class.getName(),
                    "removeMBeanServer(MBeanServer)",
                    "MBeanServer was not in list!");
            throw new IllegalArgumentException("MBeanServer was not in list!");
        }
    }

    private static final ArrayList<MBeanServer> mBeanServerList =
            new ArrayList<MBeanServer>();

    /**
     * Load the builder class through the context class loader.
     * <p>
     *  通过上下文类加载器加载构建器类。
     * 
     * 
     * @param builderClassName The name of the builder class.
     **/
    private static Class<?> loadBuilderClass(String builderClassName)
    throws ClassNotFoundException {
        final ClassLoader loader =
                Thread.currentThread().getContextClassLoader();

        if (loader != null) {
            // Try with context class loader
            return loader.loadClass(builderClassName);
        }

        // No context class loader? Try with Class.forName()
        return ReflectUtil.forName(builderClassName);
    }

    /**
     * Creates the initial builder according to the
     * javax.management.builder.initial System property - if specified.
     * If any checked exception needs to be thrown, it is embedded in
     * a JMRuntimeException.
     * <p>
     *  根据javax.management.builder.initial创建初始构建器系统属性 - 如果指定。如果需要抛出任何已检查的异常,它将嵌入在JMRuntimeException中。
     * 
     * 
     **/
    private static MBeanServerBuilder newBuilder(Class<?> builderClass) {
        try {
            final Object abuilder = builderClass.newInstance();
            return (MBeanServerBuilder)abuilder;
        } catch (RuntimeException x) {
            throw x;
        } catch (Exception x) {
            final String msg =
                    "Failed to instantiate a MBeanServerBuilder from " +
                    builderClass + ": " + x;
            throw new JMRuntimeException(msg, x);
        }
    }

    /**
     * Instantiate a new builder according to the
     * javax.management.builder.initial System property - if needed.
     * <p>
     * 根据javax.management.builder.initial系统属性实例化新构建器(如果需要)。
     * 
     * 
     **/
    private static synchronized void checkMBeanServerBuilder() {
        try {
            GetPropertyAction act =
                    new GetPropertyAction(JMX_INITIAL_BUILDER);
            String builderClassName = AccessController.doPrivileged(act);

            try {
                final Class<?> newBuilderClass;
                if (builderClassName == null || builderClassName.length() == 0)
                    newBuilderClass = MBeanServerBuilder.class;
                else
                    newBuilderClass = loadBuilderClass(builderClassName);

                // Check whether a new builder needs to be created
                if (builder != null) {
                    final Class<?> builderClass = builder.getClass();
                    if (newBuilderClass == builderClass)
                        return; // no need to create a new builder...
                }

                // Create a new builder
                builder = newBuilder(newBuilderClass);
            } catch (ClassNotFoundException x) {
                final String msg =
                        "Failed to load MBeanServerBuilder class " +
                        builderClassName + ": " + x;
                throw new JMRuntimeException(msg, x);
            }
        } catch (RuntimeException x) {
            if (MBEANSERVER_LOGGER.isLoggable(Level.FINEST)) {
                StringBuilder strb = new StringBuilder()
                .append("Failed to instantiate MBeanServerBuilder: ").append(x)
                .append("\n\t\tCheck the value of the ")
                .append(JMX_INITIAL_BUILDER).append(" property.");
                MBEANSERVER_LOGGER.logp(Level.FINEST,
                        MBeanServerFactory.class.getName(),
                        "checkMBeanServerBuilder",
                        strb.toString());
            }
            throw x;
        }
    }

    /**
     * Get the current {@link javax.management.MBeanServerBuilder},
     * as specified by the current value of the
     * javax.management.builder.initial property.
     *
     * This method consults the property and instantiates a new builder
     * if needed.
     *
     * <p>
     *  获取由javax.management.builder.initial属性的当前值指定的当前{@link javax.management.MBeanServerBuilder}。
     * 
     * 
     * @return the new current {@link javax.management.MBeanServerBuilder}.
     *
     * @exception SecurityException if there is a SecurityManager and
     * the caller's permissions do not make it possible to instantiate
     * a new builder.
     * @exception JMRuntimeException if the builder instantiation
     *   fails with a checked exception -
     *   {@link java.lang.ClassNotFoundException} etc...
     *
     **/
    private static synchronized MBeanServerBuilder getNewMBeanServerBuilder() {
        checkMBeanServerBuilder();
        return builder;
    }

}
