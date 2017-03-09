/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2012, Oracle and/or its affiliates. All rights reserved.
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

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.net.MalformedURLException;

import javax.naming.*;
import com.sun.naming.internal.VersionHelper;
import com.sun.naming.internal.ResourceManager;
import com.sun.naming.internal.FactoryEnumeration;

/**
 * This class contains methods for creating context objects
 * and objects referred to by location information in the naming
 * or directory service.
 *<p>
 * This class cannot be instantiated.  It has only static methods.
 *<p>
 * The mention of URL in the documentation for this class refers to
 * a URL string as defined by RFC 1738 and its related RFCs. It is
 * any string that conforms to the syntax described therein, and
 * may not always have corresponding support in the java.net.URL
 * class or Web browsers.
 *<p>
 * NamingManager is safe for concurrent access by multiple threads.
 *<p>
 * Except as otherwise noted,
 * a <tt>Name</tt> or environment parameter
 * passed to any method is owned by the caller.
 * The implementation will not modify the object or keep a reference
 * to it, although it may keep a reference to a clone or copy.
 *
 * <p>
 *  此类包含用于创建上下文对象和由命名或目录服务中的位置信息引用的对象的方法。
 * p>
 *  此类不能实例化。它只有静态方法。
 * p>
 *  在此类的文档中提及URL是指由RFC 1738及其相关RFC定义的URL字符串。它是符合其中描述的语法的任何字符串,并且可能不总是在java.net.URL类或Web浏览器中具有相应的支持。
 * p>
 *  NamingManager对于多线程的并发访问是安全的。
 * p>
 *  除非另有说明,传递给任何方法的<tt> Name </tt>或环境参数由调用者拥有。实现不会修改对象或保留对它的引用,虽然它可能保留对克隆或副本的引用。
 * 
 * 
 * @author Rosanna Lee
 * @author Scott Seligman
 * @since 1.3
 */

public class NamingManager {

    /*
     * Disallow anyone from creating one of these.
     * Made package private so that DirectoryManager can subclass.
     * <p>
     *  禁止任何人创建其中的一个。使包是私有的,使DirectoryManager可以子类化。
     * 
     */

    NamingManager() {}

    // should be protected and package private
    static final VersionHelper helper = VersionHelper.getVersionHelper();

// --------- object factory stuff

    /**
     * Package-private; used by DirectoryManager and NamingManager.
     * <p>
     *  包私有;由DirectoryManager和NamingManager使用。
     * 
     */
    private static ObjectFactoryBuilder object_factory_builder = null;

    /**
     * The ObjectFactoryBuilder determines the policy used when
     * trying to load object factories.
     * See getObjectInstance() and class ObjectFactory for a description
     * of the default policy.
     * setObjectFactoryBuilder() overrides this default policy by installing
     * an ObjectFactoryBuilder. Subsequent object factories will
     * be loaded and created using the installed builder.
     *<p>
     * The builder can only be installed if the executing thread is allowed
     * (by the security manager's checkSetFactory() method) to do so.
     * Once installed, the builder cannot be replaced.
     *<p>
     * <p>
     *  ObjectFactoryBuilder确定尝试加载对象工厂时使用的策略。有关默认策略的描述,请参阅getObjectInstance()和类ObjectFactory。
     *  setObjectFactoryBuilder()通过安装ObjectFactoryBuilder来覆盖此默认策略。将使用已安装的构建器加载和创建后续对象工厂。
     * p>
     * 只有允许执行线程(通过安全管理器的checkSetFactory()方法)才能安装该构建器。安装后,无法替换构建器。
     * p>
     * 
     * @param builder The factory builder to install. If null, no builder
     *                  is installed.
     * @exception SecurityException builder cannot be installed
     *          for security reasons.
     * @exception NamingException builder cannot be installed for
     *         a non-security-related reason.
     * @exception IllegalStateException If a factory has already been installed.
     * @see #getObjectInstance
     * @see ObjectFactory
     * @see ObjectFactoryBuilder
     * @see java.lang.SecurityManager#checkSetFactory
     */
    public static synchronized void setObjectFactoryBuilder(
            ObjectFactoryBuilder builder) throws NamingException {
        if (object_factory_builder != null)
            throw new IllegalStateException("ObjectFactoryBuilder already set");

        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkSetFactory();
        }
        object_factory_builder = builder;
    }

    /**
     * Used for accessing object factory builder.
     * <p>
     *  用于访问对象工厂构建器。
     * 
     */
    static synchronized ObjectFactoryBuilder getObjectFactoryBuilder() {
        return object_factory_builder;
    }


    /**
     * Retrieves the ObjectFactory for the object identified by a reference,
     * using the reference's factory class name and factory codebase
     * to load in the factory's class.
     * <p>
     *  检索由引用标识的对象的ObjectFactory,使用引用的工厂类名称和工厂代码库加载到工厂的类中。
     * 
     * 
     * @param ref The non-null reference to use.
     * @param factoryName The non-null class name of the factory.
     * @return The object factory for the object identified by ref; null
     * if unable to load the factory.
     */
    static ObjectFactory getObjectFactoryFromReference(
        Reference ref, String factoryName)
        throws IllegalAccessException,
        InstantiationException,
        MalformedURLException {
        Class<?> clas = null;

        // Try to use current class loader
        try {
             clas = helper.loadClass(factoryName);
        } catch (ClassNotFoundException e) {
            // ignore and continue
            // e.printStackTrace();
        }
        // All other exceptions are passed up.

        // Not in class path; try to use codebase
        String codebase;
        if (clas == null &&
                (codebase = ref.getFactoryClassLocation()) != null) {
            try {
                clas = helper.loadClass(factoryName, codebase);
            } catch (ClassNotFoundException e) {
            }
        }

        return (clas != null) ? (ObjectFactory) clas.newInstance() : null;
    }


    /**
     * Creates an object using the factories specified in the
     * <tt>Context.OBJECT_FACTORIES</tt> property of the environment
     * or of the provider resource file associated with <tt>nameCtx</tt>.
     *
     * <p>
     *  使用环境的<tt> Context.OBJECT_FACTORIES </tt>属性中指定的工厂或与<tt> nameCtx </tt>关联的提供程序资源文件中指定的工厂创建对象。
     * 
     * 
     * @return factory created; null if cannot create
     */
    private static Object createObjectFromFactories(Object obj, Name name,
            Context nameCtx, Hashtable<?,?> environment) throws Exception {

        FactoryEnumeration factories = ResourceManager.getFactories(
            Context.OBJECT_FACTORIES, environment, nameCtx);

        if (factories == null)
            return null;

        // Try each factory until one succeeds
        ObjectFactory factory;
        Object answer = null;
        while (answer == null && factories.hasMore()) {
            factory = (ObjectFactory)factories.next();
            answer = factory.getObjectInstance(obj, name, nameCtx, environment);
        }
        return answer;
    }

    private static String getURLScheme(String str) {
        int colon_posn = str.indexOf(':');
        int slash_posn = str.indexOf('/');

        if (colon_posn > 0 && (slash_posn == -1 || colon_posn < slash_posn))
            return str.substring(0, colon_posn);
        return null;
    }

    /**
     * Creates an instance of an object for the specified object
     * and environment.
     * <p>
     * If an object factory builder has been installed, it is used to
     * create a factory for creating the object.
     * Otherwise, the following rules are used to create the object:
     *<ol>
     * <li>If <code>refInfo</code> is a <code>Reference</code>
     *    or <code>Referenceable</code> containing a factory class name,
     *    use the named factory to create the object.
     *    Return <code>refInfo</code> if the factory cannot be created.
     *    Under JDK 1.1, if the factory class must be loaded from a location
     *    specified in the reference, a <tt>SecurityManager</tt> must have
     *    been installed or the factory creation will fail.
     *    If an exception is encountered while creating the factory,
     *    it is passed up to the caller.
     * <li>If <tt>refInfo</tt> is a <tt>Reference</tt> or
     *    <tt>Referenceable</tt> with no factory class name,
     *    and the address or addresses are <tt>StringRefAddr</tt>s with
     *    address type "URL",
     *    try the URL context factory corresponding to each URL's scheme id
     *    to create the object (see <tt>getURLContext()</tt>).
     *    If that fails, continue to the next step.
     * <li> Use the object factories specified in
     *    the <tt>Context.OBJECT_FACTORIES</tt> property of the environment,
     *    and of the provider resource file associated with
     *    <tt>nameCtx</tt>, in that order.
     *    The value of this property is a colon-separated list of factory
     *    class names that are tried in order, and the first one that succeeds
     *    in creating an object is the one used.
     *    If none of the factories can be loaded,
     *    return <code>refInfo</code>.
     *    If an exception is encountered while creating the object, the
     *    exception is passed up to the caller.
     *</ol>
     *<p>
     * Service providers that implement the <tt>DirContext</tt>
     * interface should use
     * <tt>DirectoryManager.getObjectInstance()</tt>, not this method.
     * Service providers that implement only the <tt>Context</tt>
     * interface should use this method.
     * <p>
     * Note that an object factory (an object that implements the ObjectFactory
     * interface) must be public and must have a public constructor that
     * accepts no arguments.
     * <p>
     * The <code>name</code> and <code>nameCtx</code> parameters may
     * optionally be used to specify the name of the object being created.
     * <code>name</code> is the name of the object, relative to context
     * <code>nameCtx</code>.  This information could be useful to the object
     * factory or to the object implementation.
     *  If there are several possible contexts from which the object
     *  could be named -- as will often be the case -- it is up to
     *  the caller to select one.  A good rule of thumb is to select the
     * "deepest" context available.
     * If <code>nameCtx</code> is null, <code>name</code> is relative
     * to the default initial context.  If no name is being specified, the
     * <code>name</code> parameter should be null.
     *
     * <p>
     *  为指定的对象和环境创建对象的实例。
     * <p>
     *  如果已经安装了对象工厂构建器,则用于创建用于创建对象的工厂。否则,将使用以下规则创建对象：
     * ol>
     * <li>如果<code> refInfo </code>是包含工厂类名称的<code>引用</code>或<code>引用</code>,请使用命名工厂创建对象。
     * 如果无法创建工厂,则返回<code> refInfo </code>。
     * 在JDK 1.1下,如果必须从引用中指定的位置加载工厂类,则必须安装<tt> SecurityManager </tt>,否则工厂创建将失败。如果在创建工厂时遇到异常,它会传递给调用者。
     *  <li>如果<tt> refInfo </tt>是没有工厂类名称的<tt>引用</tt>或<tt>可引用</tt>,并且一个或多个地址为<tt> StringRefAddr </tt > s,地址类型
     * 为"URL",请尝试对应于每个URL的方案id的URL上下文工厂来创建对象(参见<tt> getURLContext()</tt>)。
     * 在JDK 1.1下,如果必须从引用中指定的位置加载工厂类,则必须安装<tt> SecurityManager </tt>,否则工厂创建将失败。如果在创建工厂时遇到异常,它会传递给调用者。
     * 如果失败,请继续下一步。
     *  <li>使用环境的<tt> Context.OBJECT_FACTORIES </tt>属性中指定的对象工厂,以及与<tt> nameCtx </tt>关联的提供程序资源文件中指定的对象工厂。
     * 此属性的值是以冒号分隔的工厂类名称列表,按顺序尝试,并且成功创建对象的第一个是使用的对象。如果没有工厂可以加载,返回<code> refInfo </code>。
     * 如果在创建对象时遇到异常,则将异常传递给调用者。
     * /ol>
     * p>
     * 实现<tt> DirContext </tt>界面的服务提供程序应使用<tt> DirectoryManager.getObjectInstance()</tt>,而不是此方法。
     * 仅实现<tt> Context </tt>接口的服务提供者应该使用此方法。
     * <p>
     *  注意,对象工厂(实现ObjectFactory接口的对象)必须是public的,并且必须有一个不接受参数的公共构造函数。
     * <p>
     *  可以可选地使用<code> name </code>和<code> nameCtx </code>参数来指定正在创建的对象的名称。
     *  <code> name </code>是对象的名称,相对于上下文<code> nameCtx </code>。此信息可能对对象工厂或对象实现有用。
     * 如果有几个可能的上下文从中可以命名对象 - 正如通常的情况 - 由调用者选择一个。一个好的经验法则是选择"最深的"上下文。
     * 如果<code> nameCtx </code>为null,则<code> name </code>是相对于默认的初始上下文。如果未指定名称,则<code> name </code>参数应为null。
     * 
     * 
     * @param refInfo The possibly null object for which to create an object.
     * @param name The name of this object relative to <code>nameCtx</code>.
     *          Specifying a name is optional; if it is
     *          omitted, <code>name</code> should be null.
     * @param nameCtx The context relative to which the <code>name</code>
     *          parameter is specified.  If null, <code>name</code> is
     *          relative to the default initial context.
     * @param environment The possibly null environment to
     *          be used in the creation of the object factory and the object.
     * @return An object created using <code>refInfo</code>; or
     *          <code>refInfo</code> if an object cannot be created using
     *          the algorithm described above.
     * @exception NamingException if a naming exception was encountered
     *  while attempting to get a URL context, or if one of the
     *          factories accessed throws a NamingException.
     * @exception Exception if one of the factories accessed throws an
     *          exception, or if an error was encountered while loading
     *          and instantiating the factory and object classes.
     *          A factory should only throw an exception if it does not want
     *          other factories to be used in an attempt to create an object.
     *  See ObjectFactory.getObjectInstance().
     * @see #getURLContext
     * @see ObjectFactory
     * @see ObjectFactory#getObjectInstance
     */
    public static Object
        getObjectInstance(Object refInfo, Name name, Context nameCtx,
                          Hashtable<?,?> environment)
        throws Exception
    {

        ObjectFactory factory;

        // Use builder if installed
        ObjectFactoryBuilder builder = getObjectFactoryBuilder();
        if (builder != null) {
            // builder must return non-null factory
            factory = builder.createObjectFactory(refInfo, environment);
            return factory.getObjectInstance(refInfo, name, nameCtx,
                environment);
        }

        // Use reference if possible
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
                if (factory != null) {
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

                answer = processURLAddrs(ref, name, nameCtx, environment);
                if (answer != null) {
                    return answer;
                }
            }
        }

        // try using any specified factories
        answer =
            createObjectFromFactories(refInfo, name, nameCtx, environment);
        return (answer != null) ? answer : refInfo;
    }

    /*
     * Ref has no factory.  For each address of type "URL", try its URL
     * context factory.  Returns null if unsuccessful in creating and
     * invoking a factory.
     * <p>
     *  Ref没有工厂。对于类型"URL"的每个地址,请尝试其URL上下文工厂。如果在创建和调用工厂时不成功,则返回null。
     * 
     */
    static Object processURLAddrs(Reference ref, Name name, Context nameCtx,
                                  Hashtable<?,?> environment)
            throws NamingException {

        for (int i = 0; i < ref.size(); i++) {
            RefAddr addr = ref.get(i);
            if (addr instanceof StringRefAddr &&
                addr.getType().equalsIgnoreCase("URL")) {

                String url = (String)addr.getContent();
                Object answer = processURL(url, name, nameCtx, environment);
                if (answer != null) {
                    return answer;
                }
            }
        }
        return null;
    }

    private static Object processURL(Object refInfo, Name name,
                                     Context nameCtx, Hashtable<?,?> environment)
            throws NamingException {
        Object answer;

        // If refInfo is a URL string, try to use its URL context factory
        // If no context found, continue to try object factories.
        if (refInfo instanceof String) {
            String url = (String)refInfo;
            String scheme = getURLScheme(url);
            if (scheme != null) {
                answer = getURLObject(scheme, refInfo, name, nameCtx,
                                      environment);
                if (answer != null) {
                    return answer;
                }
            }
        }

        // If refInfo is an array of URL strings,
        // try to find a context factory for any one of its URLs.
        // If no context found, continue to try object factories.
        if (refInfo instanceof String[]) {
            String[] urls = (String[])refInfo;
            for (int i = 0; i <urls.length; i++) {
                String scheme = getURLScheme(urls[i]);
                if (scheme != null) {
                    answer = getURLObject(scheme, refInfo, name, nameCtx,
                                          environment);
                    if (answer != null)
                        return answer;
                }
            }
        }
        return null;
    }


    /**
     * Retrieves a context identified by <code>obj</code>, using the specified
     * environment.
     * Used by ContinuationContext.
     *
     * <p>
     *  使用指定的环境检索由<code> obj </code>标识的上下文。由ContinuationContext使用。
     * 
     * 
     * @param obj       The object identifying the context.
     * @param name      The name of the context being returned, relative to
     *                  <code>nameCtx</code>, or null if no name is being
     *                  specified.
     *                  See the <code>getObjectInstance</code> method for
     *                  details.
     * @param nameCtx   The context relative to which <code>name</code> is
     *                  specified, or null for the default initial context.
     *                  See the <code>getObjectInstance</code> method for
     *                  details.
     * @param environment Environment specifying characteristics of the
     *                  resulting context.
     * @return A context identified by <code>obj</code>.
     *
     * @see #getObjectInstance
     */
    static Context getContext(Object obj, Name name, Context nameCtx,
                              Hashtable<?,?> environment) throws NamingException {
        Object answer;

        if (obj instanceof Context) {
            // %%% Ignore environment for now.  OK since method not public.
            return (Context)obj;
        }

        try {
            answer = getObjectInstance(obj, name, nameCtx, environment);
        } catch (NamingException e) {
            throw e;
        } catch (Exception e) {
            NamingException ne = new NamingException();
            ne.setRootCause(e);
            throw ne;
        }

        return (answer instanceof Context)
            ? (Context)answer
            : null;
    }

    // Used by ContinuationContext
    static Resolver getResolver(Object obj, Name name, Context nameCtx,
                                Hashtable<?,?> environment) throws NamingException {
        Object answer;

        if (obj instanceof Resolver) {
            // %%% Ignore environment for now.  OK since method not public.
            return (Resolver)obj;
        }

        try {
            answer = getObjectInstance(obj, name, nameCtx, environment);
        } catch (NamingException e) {
            throw e;
        } catch (Exception e) {
            NamingException ne = new NamingException();
            ne.setRootCause(e);
            throw ne;
        }

        return (answer instanceof Resolver)
            ? (Resolver)answer
            : null;
    }


    /***************** URL Context implementations ***************/

    /**
     * Creates a context for the given URL scheme id.
     * <p>
     * The resulting context is for resolving URLs of the
     * scheme <code>scheme</code>. The resulting context is not tied
     * to a specific URL. It is able to handle arbitrary URLs with
     * the specified scheme.
     *<p>
     * The class name of the factory that creates the resulting context
     * has the naming convention <i>scheme-id</i>URLContextFactory
     * (e.g. "ftpURLContextFactory" for the "ftp" scheme-id),
     * in the package specified as follows.
     * The <tt>Context.URL_PKG_PREFIXES</tt> environment property (which
     * may contain values taken from applet parameters, system properties,
     * or application resource files)
     * contains a colon-separated list of package prefixes.
     * Each package prefix in
     * the property is tried in the order specified to load the factory class.
     * The default package prefix is "com.sun.jndi.url" (if none of the
     * specified packages work, this default is tried).
     * The complete package name is constructed using the package prefix,
     * concatenated with the scheme id.
     *<p>
     * For example, if the scheme id is "ldap", and the
     * <tt>Context.URL_PKG_PREFIXES</tt> property
     * contains "com.widget:com.wiz.jndi",
     * the naming manager would attempt to load the following classes
     * until one is successfully instantiated:
     *<ul>
     * <li>com.widget.ldap.ldapURLContextFactory
     *  <li>com.wiz.jndi.ldap.ldapURLContextFactory
     *  <li>com.sun.jndi.url.ldap.ldapURLContextFactory
     *</ul>
     * If none of the package prefixes work, null is returned.
     *<p>
     * If a factory is instantiated, it is invoked with the following
     * parameters to produce the resulting context.
     * <p>
     * <code>factory.getObjectInstance(null, environment);</code>
     * <p>
     * For example, invoking getObjectInstance() as shown above
     * on a LDAP URL context factory would return a
     * context that can resolve LDAP urls
     * (e.g. "ldap://ldap.wiz.com/o=wiz,c=us",
     * "ldap://ldap.umich.edu/o=umich,c=us", ...).
     *<p>
     * Note that an object factory (an object that implements the ObjectFactory
     * interface) must be public and must have a public constructor that
     * accepts no arguments.
     *
     * <p>
     *  为给定的URL方案ID创建上下文。
     * <p>
     * 结果上下文用于解决方案<code> scheme </code>的URL。生成的上下文不绑定到特定的URL。它能够处理具有指定方案的任意URL。
     * p>
     *  创建生成的上下文的工厂的类名在包中指定的包中具有命名约定<i> scheme-id </i> URLContextFactory(例如,"ftpURLContextFactory"为"ftp"sche
     * me-id)。
     *  <tt> Context.URL_PKG_PREFIXES </tt>环境属性(可能包含从applet参数,系统属性或应用程序资源文件中获取的值)包含冒号分隔的软件包前缀列表。
     * 属性中的每个包前缀按指定的顺序尝试加载工厂类。默认包前缀是"com.sun.jndi.url"(如果没有指定的包工作,则此默认值被尝试)。完整的包名称使用包前缀与方案id连接构造。
     * p>
     *  例如,如果方案id是"ldap",并且<tt> Context.URL_PKG_PREFIXES </tt>属性包含"com.widget：com.wiz.jndi",命名管理器将尝试加载以下类,直到
     * 一个已成功实例化：。
     * ul>
     *  <li> com.widget.ldap.ldapURLContextFactory <li> com.wiz.jndi.ldap.ldapURLContextFactory <li> com.sun
     * .jndi.url.ldap.ldapURLContextFactory。
     * /ul>
     *  如果没有任何包前缀工作,则返回null。
     * p>
     *  如果工厂被实例化,则使用以下参数调用它以产生结果上下文。
     * <p>
     * <code> factory.getObjectInstance(null,environment); </code>
     * <p>
     *  例如,如上所示在LDAP URL上下文工厂上调用getObjectInstance()将返回一个可以解析LDAP网址的上下文(例如"ldap：//ldap.wiz.com/o=wiz,c=us","l
     * dap： //ldap.umich.edu/o=umich,c=us",...)。
     * p>
     *  注意,对象工厂(实现ObjectFactory接口的对象)必须是public的,并且必须有一个不接受参数的公共构造函数。
     * 
     * 
     * @param scheme    The non-null scheme-id of the URLs supported by the context.
     * @param environment The possibly null environment properties to be
     *           used in the creation of the object factory and the context.
     * @return A context for resolving URLs with the
     *         scheme id <code>scheme</code>;
     *  <code>null</code> if the factory for creating the
     *         context is not found.
     * @exception NamingException If a naming exception occurs while creating
     *          the context.
     * @see #getObjectInstance
     * @see ObjectFactory#getObjectInstance
     */
    public static Context getURLContext(String scheme,
                                        Hashtable<?,?> environment)
        throws NamingException
    {
        // pass in 'null' to indicate creation of generic context for scheme
        // (i.e. not specific to a URL).

            Object answer = getURLObject(scheme, null, null, null, environment);
            if (answer instanceof Context) {
                return (Context)answer;
            } else {
                return null;
            }
    }

    private static final String defaultPkgPrefix = "com.sun.jndi.url";

    /**
     * Creates an object for the given URL scheme id using
     * the supplied urlInfo.
     * <p>
     * If urlInfo is null, the result is a context for resolving URLs
     * with the scheme id 'scheme'.
     * If urlInfo is a URL, the result is a context named by the URL.
     * Names passed to this context is assumed to be relative to this
     * context (i.e. not a URL). For example, if urlInfo is
     * "ldap://ldap.wiz.com/o=Wiz,c=us", the resulting context will
     * be that pointed to by "o=Wiz,c=us" on the server 'ldap.wiz.com'.
     * Subsequent names that can be passed to this context will be
     * LDAP names relative to this context (e.g. cn="Barbs Jensen").
     * If urlInfo is an array of URLs, the URLs are assumed
     * to be equivalent in terms of the context to which they refer.
     * The resulting context is like that of the single URL case.
     * If urlInfo is of any other type, that is handled by the
     * context factory for the URL scheme.
     * <p>
     *  使用提供的urlInfo为给定的URL scheme id创建一个对象。
     * <p>
     *  如果urlInfo为null,则结果是用于解析具有方案id"scheme"的URL的上下文。如果urlInfo是URL,则结果是由URL命名的上下文。
     * 传递给此上下文的名称假定为相对于此上下文(即不是URL)。
     * 例如,如果urlInfo是"ldap：//ldap.wiz.com/o=Wiz,c=us",则生成的上下文将是服务器'ldap上的"o = Wiz,c = us"指向的上下文。 wiz.com"。
     * 可以传递到此上下文的后续名称将是相对于此上下文的LDAP名称(例如cn ="Barbs Jensen")。如果urlInfo是一个URL数组,则假定URL在它们所引用的上下文方面是等价的。
     * 生成的上下文类似于单个URL的情况。如果urlInfo是任何其他类型,由URL方案的上下文工厂处理。
     * 
     * 
     * @param scheme the URL scheme id for the context
     * @param urlInfo information used to create the context
     * @param name name of this object relative to <code>nameCtx</code>
     * @param nameCtx Context whose provider resource file will be searched
     *          for package prefix values (or null if none)
     * @param environment Environment properties for creating the context
     * @see javax.naming.InitialContext
     */
    private static Object getURLObject(String scheme, Object urlInfo,
                                       Name name, Context nameCtx,
                                       Hashtable<?,?> environment)
            throws NamingException {

        // e.g. "ftpURLContextFactory"
        ObjectFactory factory = (ObjectFactory)ResourceManager.getFactory(
            Context.URL_PKG_PREFIXES, environment, nameCtx,
            "." + scheme + "." + scheme + "URLContextFactory", defaultPkgPrefix);

        if (factory == null)
          return null;

        // Found object factory
        try {
            return factory.getObjectInstance(urlInfo, name, nameCtx, environment);
        } catch (NamingException e) {
            throw e;
        } catch (Exception e) {
            NamingException ne = new NamingException();
            ne.setRootCause(e);
            throw ne;
        }

    }


// ------------ Initial Context Factory Stuff
    private static InitialContextFactoryBuilder initctx_factory_builder = null;

    /**
     * Use this method for accessing initctx_factory_builder while
     * inside an unsynchronized method.
     * <p>
     *  在非同步方法中使用此方法访问initctx_factory_builder。
     * 
     */
    private static synchronized InitialContextFactoryBuilder
    getInitialContextFactoryBuilder() {
        return initctx_factory_builder;
    }

    /**
     * Creates an initial context using the specified environment
     * properties.
     *<p>
     * If an InitialContextFactoryBuilder has been installed,
     * it is used to create the factory for creating the initial context.
     * Otherwise, the class specified in the
     * <tt>Context.INITIAL_CONTEXT_FACTORY</tt> environment property is used.
     * Note that an initial context factory (an object that implements the
     * InitialContextFactory interface) must be public and must have a
     * public constructor that accepts no arguments.
     *
     * <p>
     *  使用指定的环境属性创建初始上下文。
     * p>
     * 如果已经安装了InitialContextFactoryBuilder,它将用于创建用于创建初始上下文的工厂。
     * 否则,将使用在<tt> Context.INITIAL_CONTEXT_FACTORY </tt>环境属性中指定的类。
     * 注意,初始上下文工厂(实现InitialContextFactory接口的对象)必须是public的,并且必须有一个不接受参数的公共构造函数。
     * 
     * 
     * @param env The possibly null environment properties used when
     *                  creating the context.
     * @return A non-null initial context.
     * @exception NoInitialContextException If the
     *          <tt>Context.INITIAL_CONTEXT_FACTORY</tt> property
     *         is not found or names a nonexistent
     *         class or a class that cannot be instantiated,
     *          or if the initial context could not be created for some other
     *          reason.
     * @exception NamingException If some other naming exception was encountered.
     * @see javax.naming.InitialContext
     * @see javax.naming.directory.InitialDirContext
     */
    public static Context getInitialContext(Hashtable<?,?> env)
        throws NamingException {
        InitialContextFactory factory;

        InitialContextFactoryBuilder builder = getInitialContextFactoryBuilder();
        if (builder == null) {
            // No factory installed, use property
            // Get initial context factory class name

            String className = env != null ?
                (String)env.get(Context.INITIAL_CONTEXT_FACTORY) : null;
            if (className == null) {
                NoInitialContextException ne = new NoInitialContextException(
                    "Need to specify class name in environment or system " +
                    "property, or as an applet parameter, or in an " +
                    "application resource file:  " +
                    Context.INITIAL_CONTEXT_FACTORY);
                throw ne;
            }

            try {
                factory = (InitialContextFactory)
                    helper.loadClass(className).newInstance();
            } catch(Exception e) {
                NoInitialContextException ne =
                    new NoInitialContextException(
                        "Cannot instantiate class: " + className);
                ne.setRootCause(e);
                throw ne;
            }
        } else {
            factory = builder.createInitialContextFactory(env);
        }

        return factory.getInitialContext(env);
    }


    /**
     * Sets the InitialContextFactory builder to be builder.
     *
     *<p>
     * The builder can only be installed if the executing thread is allowed by
     * the security manager to do so. Once installed, the builder cannot
     * be replaced.
     * <p>
     *  将InitialContextFactory构建器设置为builder。
     * 
     * p>
     *  只有在安全管理器允许执行线程这样做时,才能安装构建器。安装后,无法替换构建器。
     * 
     * 
     * @param builder The initial context factory builder to install. If null,
     *                no builder is set.
     * @exception SecurityException builder cannot be installed for security
     *                  reasons.
     * @exception NamingException builder cannot be installed for
     *         a non-security-related reason.
     * @exception IllegalStateException If a builder was previous installed.
     * @see #hasInitialContextFactoryBuilder
     * @see java.lang.SecurityManager#checkSetFactory
     */
    public static synchronized void setInitialContextFactoryBuilder(
        InitialContextFactoryBuilder builder)
        throws NamingException {
            if (initctx_factory_builder != null)
                throw new IllegalStateException(
                    "InitialContextFactoryBuilder already set");

            SecurityManager security = System.getSecurityManager();
            if (security != null) {
                security.checkSetFactory();
            }
            initctx_factory_builder = builder;
    }

    /**
     * Determines whether an initial context factory builder has
     * been set.
     * <p>
     *  确定是否已设置初始上下文工厂构建器。
     * 
     * 
     * @return true if an initial context factory builder has
     *           been set; false otherwise.
     * @see #setInitialContextFactoryBuilder
     */
    public static boolean hasInitialContextFactoryBuilder() {
        return (getInitialContextFactoryBuilder() != null);
    }

// -----  Continuation Context Stuff

    /**
     * Constant that holds the name of the environment property into
     * which <tt>getContinuationContext()</tt> stores the value of its
     * <tt>CannotProceedException</tt> parameter.
     * This property is inherited by the continuation context, and may
     * be used by that context's service provider to inspect the
     * fields of the exception.
     *<p>
     * The value of this constant is "java.naming.spi.CannotProceedException".
     *
     * <p>
     *  包含<tt> getContinuationContext()</tt>的环境属性名称的常量,用于存储<tt> CannotProceedException </tt>参数的值。
     * 此属性由继承上下文继承,并且可由该上下文的服务提供程序用于检查异常的字段。
     * p>
     *  此常量的值为"java.naming.spi.CannotProceedException"。
     * 
     * 
     * @see #getContinuationContext
     * @since 1.3
     */
    public static final String CPE = "java.naming.spi.CannotProceedException";

    /**
     * Creates a context in which to continue a context operation.
     *<p>
     * In performing an operation on a name that spans multiple
     * namespaces, a context from one naming system may need to pass
     * the operation on to the next naming system.  The context
     * implementation does this by first constructing a
     * <code>CannotProceedException</code> containing information
     * pinpointing how far it has proceeded.  It then obtains a
     * continuation context from JNDI by calling
     * <code>getContinuationContext</code>.  The context
     * implementation should then resume the context operation by
     * invoking the same operation on the continuation context, using
     * the remainder of the name that has not yet been resolved.
     *<p>
     * Before making use of the <tt>cpe</tt> parameter, this method
     * updates the environment associated with that object by setting
     * the value of the property <a href="#CPE"><tt>CPE</tt></a>
     * to <tt>cpe</tt>.  This property will be inherited by the
     * continuation context, and may be used by that context's
     * service provider to inspect the fields of this exception.
     *
     * <p>
     *  创建用于继续上下文操作的上下文。
     * p>
     * 在对跨多个命名空间的名称执行操作时,来自一个命名系统的上下文可能需要将操作传递到下一个命名系统。
     * 上下文实现通过首先构造包含定位其已经进行了多远的信息的<code> CannotProceedException </code>来实现。
     * 然后通过调用<code> getContinuationContext </code>从JNDI获取一个继承上下文。
     * 然后,上下文实现应该通过使用尚未被解析的名称的其余部分在连续上下文上调用相同的操作来恢复上下文操作。
     * p>
     *  在使用<tt> cpe </tt>参数之前,此方法会更新与该对象相关联的环境,方法是设置属性<a href="#CPE"> <tt> CPE </tt> </a>到<tt> cpe </tt>。
     * 此属性将由继承上下文继承,并且可由该上下文的服务提供程序用于检查此异常的字段。
     * 
     * 
     * @param cpe
     *          The non-null exception that triggered this continuation.
     * @return A non-null Context object for continuing the operation.
     * @exception NamingException If a naming exception occurred.
     */
    @SuppressWarnings("unchecked")
    public static Context getContinuationContext(CannotProceedException cpe)
            throws NamingException {

        Hashtable<Object,Object> env = (Hashtable<Object,Object>)cpe.getEnvironment();
        if (env == null) {
            env = new Hashtable<>(7);
        } else {
            // Make a (shallow) copy of the environment.
            env = (Hashtable<Object,Object>)env.clone();
        }
        env.put(CPE, cpe);

        ContinuationContext cctx = new ContinuationContext(cpe, env);
        return cctx.getTargetContext();
    }

// ------------ State Factory Stuff

    /**
     * Retrieves the state of an object for binding.
     * <p>
     * Service providers that implement the <tt>DirContext</tt> interface
     * should use <tt>DirectoryManager.getStateToBind()</tt>, not this method.
     * Service providers that implement only the <tt>Context</tt> interface
     * should use this method.
     *<p>
     * This method uses the specified state factories in
     * the <tt>Context.STATE_FACTORIES</tt> property from the environment
     * properties, and from the provider resource file associated with
     * <tt>nameCtx</tt>, in that order.
     *    The value of this property is a colon-separated list of factory
     *    class names that are tried in order, and the first one that succeeds
     *    in returning the object's state is the one used.
     * If no object's state can be retrieved in this way, return the
     * object itself.
     *    If an exception is encountered while retrieving the state, the
     *    exception is passed up to the caller.
     * <p>
     * Note that a state factory
     * (an object that implements the StateFactory
     * interface) must be public and must have a public constructor that
     * accepts no arguments.
     * <p>
     * The <code>name</code> and <code>nameCtx</code> parameters may
     * optionally be used to specify the name of the object being created.
     * See the description of "Name and Context Parameters" in
     * {@link ObjectFactory#getObjectInstance
     *          ObjectFactory.getObjectInstance()}
     * for details.
     * <p>
     * This method may return a <tt>Referenceable</tt> object.  The
     * service provider obtaining this object may choose to store it
     * directly, or to extract its reference (using
     * <tt>Referenceable.getReference()</tt>) and store that instead.
     *
     * <p>
     *  检索要绑定的对象的状态。
     * <p>
     *  实现<tt> DirContext </tt>界面的服务提供者应使用<tt> DirectoryManager.getStateToBind()</tt>,而不是此方法。
     * 仅实现<tt> Context </tt>接口的服务提供者应该使用此方法。
     * p>
     * 此方法使用环境属性中的<tt> Context.STATE_FACTORIES </tt>属性中的指定状态工厂,以及与<tt> nameCtx </tt>相关联的提供程序资源文件中的指定状态工厂。
     * 此属性的值是按顺序尝试的工厂类名称的冒号分隔列表,并且成功返回对象状态的第一个是使用的。如果没有以这种方式检索对象的状态,则返回对象本身。如果在检索状态时遇到异常,则将异常传递给调用者。
     * <p>
     * 
     * @param obj The non-null object for which to get state to bind.
     * @param name The name of this object relative to <code>nameCtx</code>,
     *          or null if no name is specified.
     * @param nameCtx The context relative to which the <code>name</code>
     *          parameter is specified, or null if <code>name</code> is
     *          relative to the default initial context.
     *  @param environment The possibly null environment to
     *          be used in the creation of the state factory and
     *  the object's state.
     * @return The non-null object representing <tt>obj</tt>'s state for
     *  binding.  It could be the object (<tt>obj</tt>) itself.
     * @exception NamingException If one of the factories accessed throws an
     *          exception, or if an error was encountered while loading
     *          and instantiating the factory and object classes.
     *          A factory should only throw an exception if it does not want
     *          other factories to be used in an attempt to create an object.
     *  See <tt>StateFactory.getStateToBind()</tt>.
     * @see StateFactory
     * @see StateFactory#getStateToBind
     * @see DirectoryManager#getStateToBind
     * @since 1.3
     */
    public static Object
        getStateToBind(Object obj, Name name, Context nameCtx,
                       Hashtable<?,?> environment)
        throws NamingException
    {

        FactoryEnumeration factories = ResourceManager.getFactories(
            Context.STATE_FACTORIES, environment, nameCtx);

        if (factories == null) {
            return obj;
        }

        // Try each factory until one succeeds
        StateFactory factory;
        Object answer = null;
        while (answer == null && factories.hasMore()) {
            factory = (StateFactory)factories.next();
            answer = factory.getStateToBind(obj, name, nameCtx, environment);
        }

        return (answer != null) ? answer : obj;
    }
}
