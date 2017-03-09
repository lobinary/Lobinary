/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.sql.rowset.spi;

import java.util.logging.*;
import java.util.*;

import java.sql.*;
import javax.sql.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import javax.naming.*;
import sun.reflect.misc.ReflectUtil;

/**
 * The Service Provider Interface (SPI) mechanism that generates <code>SyncProvider</code>
 * instances to be used by disconnected <code>RowSet</code> objects.
 * The <code>SyncProvider</code> instances in turn provide the
 * <code>javax.sql.RowSetReader</code> object the <code>RowSet</code> object
 * needs to populate itself with data and the
 * <code>javax.sql.RowSetWriter</code> object it needs to
 * propagate changes to its
 * data back to the underlying data source.
 * <P>
 * Because the methods in the <code>SyncFactory</code> class are all static,
 * there is only one <code>SyncFactory</code> object
 * per Java VM at any one time. This ensures that there is a single source from which a
 * <code>RowSet</code> implementation can obtain its <code>SyncProvider</code>
 * implementation.
 *
 * <h3>1.0 Overview</h3>
 * The <code>SyncFactory</code> class provides an internal registry of available
 * synchronization provider implementations (<code>SyncProvider</code> objects).
 * This registry may be queried to determine which
 * synchronization providers are available.
 * The following line of code gets an enumeration of the providers currently registered.
 * <PRE>
 *     java.util.Enumeration e = SyncFactory.getRegisteredProviders();
 * </PRE>
 * All standard <code>RowSet</code> implementations must provide at least two providers:
 * <UL>
 *  <LI>an optimistic provider for use with a <code>CachedRowSet</code> implementation
 *     or an implementation derived from it
 *  <LI>an XML provider, which is used for reading and writing XML, such as with
 *       <code>WebRowSet</code> objects
 * </UL>
 * Note that the JDBC RowSet Implementations include the <code>SyncProvider</code>
 * implementations <code>RIOptimisticProvider</code> and <code>RIXmlProvider</code>,
 * which satisfy this requirement.
 * <P>
 * The <code>SyncFactory</code> class provides accessor methods to assist
 * applications in determining which synchronization providers are currently
 * registered with the <code>SyncFactory</code>.
 * <p>
 * Other methods let <code>RowSet</code> persistence providers be
 * registered or de-registered with the factory mechanism. This
 * allows additional synchronization provider implementations to be made
 * available to <code>RowSet</code> objects at run time.
 * <p>
 * Applications can apply a degree of filtering to determine the level of
 * synchronization that a <code>SyncProvider</code> implementation offers.
 * The following criteria determine whether a provider is
 * made available to a <code>RowSet</code> object:
 * <ol>
 * <li>If a particular provider is specified by a <code>RowSet</code> object, and
 * the <code>SyncFactory</code> does not contain a reference to this provider,
 * a <code>SyncFactoryException</code> is thrown stating that the synchronization
 * provider could not be found.
 *
 * <li>If a <code>RowSet</code> implementation is instantiated with a specified
 * provider and the specified provider has been properly registered, the
 * requested provider is supplied. Otherwise a <code>SyncFactoryException</code>
 * is thrown.
 *
 * <li>If a <code>RowSet</code> object does not specify a
 * <code>SyncProvider</code> implementation and no additional
 * <code>SyncProvider</code> implementations are available, the reference
 * implementation providers are supplied.
 * </ol>
 * <h3>2.0 Registering <code>SyncProvider</code> Implementations</h3>
 * <p>
 * Both vendors and developers can register <code>SyncProvider</code>
 * implementations using one of the following mechanisms.
 * <ul>
 * <LI><B>Using the command line</B><BR>
 * The name of the provider is supplied on the command line, which will add
 * the provider to the system properties.
 * For example:
 * <PRE>
 *    -Drowset.provider.classname=com.fred.providers.HighAvailabilityProvider
 * </PRE>
 * <li><b>Using the Standard Properties File</b><BR>
 * The reference implementation is targeted
 * to ship with J2SE 1.5, which will include an additional resource file
 * that may be edited by hand. Here is an example of the properties file
 * included in the reference implementation:
 * <PRE>
 *   #Default JDBC RowSet sync providers listing
 *   #
 *
 *   # Optimistic synchronization provider
 *   rowset.provider.classname.0=com.sun.rowset.providers.RIOptimisticProvider
 *   rowset.provider.vendor.0=Oracle Corporation
 *   rowset.provider.version.0=1.0
 *
 *   # XML Provider using standard XML schema
 *   rowset.provider.classname.1=com.sun.rowset.providers.RIXMLProvider
 *   rowset.provider.vendor.1=Oracle Corporation
 *   rowset.provider.version.1=1.0
 * </PRE>
 * The <code>SyncFactory</code> checks this file and registers the
 * <code>SyncProvider</code> implementations that it contains. A
 * developer or vendor can add other implementations to this file.
 * For example, here is a possible addition:
 * <PRE>
 *     rowset.provider.classname.2=com.fred.providers.HighAvailabilityProvider
 *     rowset.provider.vendor.2=Fred, Inc.
 *     rowset.provider.version.2=1.0
 * </PRE>
 *
 * <li><b>Using a JNDI Context</b><BR>
 * Available providers can be registered on a JNDI
 * context, and the <code>SyncFactory</code> will attempt to load
 * <code>SyncProvider</code> implementations from that JNDI context.
 * For example, the following code fragment registers a provider implementation
 * on a JNDI context.  This is something a deployer would normally do. In this
 * example, <code>MyProvider</code> is being registered on a CosNaming
 * namespace, which is the namespace used by J2EE resources.
 * <PRE>
 *    import javax.naming.*;
 *
 *    Hashtable svrEnv = new  Hashtable();
 *    srvEnv.put(Context.INITIAL_CONTEXT_FACTORY, "CosNaming");
 *
 *    Context ctx = new InitialContext(svrEnv);
 *    com.fred.providers.MyProvider = new MyProvider();
 *    ctx.rebind("providers/MyProvider", syncProvider);
 * </PRE>
 * </ul>
 * Next, an application will register the JNDI context with the
 * <code>SyncFactory</code> instance.  This allows the <code>SyncFactory</code>
 * to browse within the JNDI context looking for <code>SyncProvider</code>
 * implementations.
 * <PRE>
 *    Hashtable appEnv = new Hashtable();
 *    appEnv.put(Context.INITIAL_CONTEXT_FACTORY, "CosNaming");
 *    appEnv.put(Context.PROVIDER_URL, "iiop://hostname/providers");
 *    Context ctx = new InitialContext(appEnv);
 *
 *    SyncFactory.registerJNDIContext(ctx);
 * </PRE>
 * If a <code>RowSet</code> object attempts to obtain a <code>MyProvider</code>
 * object, the <code>SyncFactory</code> will try to locate it. First it searches
 * for it in the system properties, then it looks in the resource files, and
 * finally it checks the JNDI context that has been set. The <code>SyncFactory</code>
 * instance verifies that the requested provider is a valid extension of the
 * <code>SyncProvider</code> abstract class and then gives it to the
 * <code>RowSet</code> object. In the following code fragment, a new
 * <code>CachedRowSet</code> object is created and initialized with
 * <i>env</i>, which contains the binding to <code>MyProvider</code>.
 * <PRE>
 *    Hashtable env = new Hashtable();
 *    env.put(SyncFactory.ROWSET_SYNC_PROVIDER, "com.fred.providers.MyProvider");
 *    CachedRowSet crs = new com.sun.rowset.CachedRowSetImpl(env);
 * </PRE>
 * Further details on these mechanisms are available in the
 * <code>javax.sql.rowset.spi</code> package specification.
 *
 * <p>
 *  生成要由断开的<code> RowSet </code>对象使用的<code> SyncProvider </code>实例的服务提供程序接口(SPI)机制。
 *  <code> SyncProvider </code>实例反过来提供<code> javax.sql.RowSetReader </code>对象,<code> RowSet </code>对象需要用
 * 数据和<code> javax填充自身。
 *  生成要由断开的<code> RowSet </code>对象使用的<code> SyncProvider </code>实例的服务提供程序接口(SPI)机制。
 *  sql.RowSetWriter </code>对象,它需要将对其数据的更改传播回基础数据源。
 * <P>
 *  因为<code> SyncFactory </code>类中的方法都是静态的,所以每个Java VM只能有一个<code> SyncFactory </code>对象。
 * 这确保存在单个源,其中<code> RowSet </code>实现可以获得其<code> SyncProvider </code>实现。
 * 
 *  <h3> 1.0概述</h3> <code> SyncFactory </code>类提供可用的同步提供程序实现(<code> SyncProvider </code>对象)的内部注册表。
 * 可以查询此注册表以确定哪些同步提供程序可用。以下代码行获取当前注册的提供程序的枚举。
 * <PRE>
 *  java.util.Enumeration e = SyncFactory.getRegisteredProviders();
 * </PRE>
 *  所有标准<code> RowSet </code>实现必须至少提供两个提供程序：
 * <UL>
 * <LI>用于<code> CachedRowSet </code>实现或从其派生的实现的乐观提供者</li> XML提供程序,用于读取和写入XML,例如使用<code> WebRowSet <代码>对象
 * 。
 * </UL>
 *  注意,JDBC RowSet实现包括满足这个要求的<code> SyncProvider </code>实现<code> RIOptimisticProvider </code>和<code> RIX
 * mlProvider </code>。
 * <P>
 *  <code> SyncFactory </code>类提供了访问器方法来帮助应用程序确定哪些同步提供程序当前注册到<code> SyncFactory </code>。
 * <p>
 *  其他方法让<code> RowSet </code>持久性提供程序使用工厂机制注册或注销。这允许额外的同步提供程序实现在运行时可用于<code> RowSet </code>对象。
 * <p>
 *  应用程序可以应用一定程度的过滤以确定<code> SyncProvider </code>实现提供的同步水平。以下标准确定提供程序是否可用于<code> RowSet </code>对象：
 * <ol>
 *  <li>如果某个提供者由<code> RowSet </code>对象指定,而<code> SyncFactory </code>不包含对此提供者的引用,则<code> SyncFactoryExce
 * ption </code>抛出表明无法找到同步提供程序。
 * 
 * <li>如果<code> RowSet </code>实现由指定的提供程序实例化,并且指定的提供程序已正确注册,则提供所请求的提供程序。
 * 否则会抛出<code> SyncFactoryException </code>。
 * 
 *  <li>如果<code> RowSet </code>对象未指定<code> SyncProvider </code>实现,并且没有其他<code> SyncProvider </code>实现可用,
 * 则提供引用实现提供程序。
 * </ol>
 *  <h3> 2.0注册<code> SyncProvider </code>实现</h3>
 * <p>
 *  供应商和开发人员都可以使用以下机制之一注册<code> SyncProvider </code>实现。
 * <ul>
 *  <LI> <B>使用命令行</B> <BR>提供程序的名称在命令行中提供,这将将提供程序添加到系统属性。例如：
 * <PRE>
 *  -Drowset.provider.classname = com.fred.providers.HighAvailabilityProvider
 * </PRE>
 *  <li> <b>使用标准属性文件</b> <BR>参考实现的目标是使用J2SE 1.5发布,其中包括可以手动编辑的其他资源文件。下面是参考实现中包含的属性文件的示例：
 * <PRE>
 *  #Default JDBC RowSet同步提供程序列表#
 * 
 *  #乐观同步提供程序rowset.provider.classname.0 = com.sun.rowset.providers.RIOptimisticProvider rowset.provider
 * .vendor.0 = Oracle公司rowset.provider.version.0 = 1.0。
 * 
 * #XML Provider使用标准XML模式rowset.provider.classname.1 = com.sun.rowset.providers.RIXMLProvider rowset.pro
 * vider.vendor.1 = Oracle公司rowset.provider.version.1 = 1.0。
 * </PRE>
 *  <code> SyncFactory </code>检查此文件并注册它包含的<code> SyncProvider </code>实现。开发人员或供应商可以向此文件添加其他实现。
 * 例如,这里是可能的添加：。
 * <PRE>
 *  rowset.provider.classname.2 = com.fred.providers.HighAvailabilityProvider rowset.provider.vendor.2 =
 *  Fred,Inc. rowset.provider.version.2 = 1.0。
 * </PRE>
 * 
 *  <li> <b>使用JNDI上下文</b> <BR>可以在JNDI上下文中注册可用的提供程序,并且<code> SyncFactory </code>将尝试加载<code> SyncProvider 
 * </code>实现从JNDI上下文。
 * 例如,以下代码片段在JNDI上下文中注册提供程序实现。这是部署者通常会做的。
 * 在这个例子中,<code> MyProvider </code>正在CosNaming命名空间上注册,这是J2EE资源使用的命名空间。
 * <PRE>
 *  import javax.naming。*;
 * 
 *  Hashtable svrEnv = new Hashtable(); srvEnv.put(Context.INITIAL_CONTEXT_FACTORY,"CosNaming");
 * 
 *  上下文ctx = new InitialContext(svrEnv); com.fred.providers.MyProvider = new MyProvider(); ctx.rebind("p
 * roviders / MyProvider",syncProvider);。
 * </PRE>
 * </ul>
 * 接下来,应用程序将向<code> SyncFactory </code>实例注册JNDI上下文。
 * 这允许<code> SyncFactory </code>在JNDI上下文中浏览,寻找<code> SyncProvider </code>实现。
 * <PRE>
 *  Hashtable appEnv = new Hashtable(); appEnv.put(Context.INITIAL_CONTEXT_FACTORY,"CosNaming"); appEnv.
 * put(Context.PROVIDER_URL,"iiop：// hostname / providers");上下文ctx = new InitialContext(appEnv);。
 * 
 *  SyncFactory.registerJNDIContext(ctx);
 * </PRE>
 *  如果<code> RowSet </code>对象尝试获取<code> MyProvider </code>对象,则<code> SyncFactory </code>会尝试找到它。
 * 首先它在系统属性中搜索它,然后查找资源文件,最后它检查已设置的JNDI上下文。
 *  <code> SyncFactory </code>实例验证所请求的提供程序是<code> SyncProvider </code>抽象类的有效扩展,然后将其提供给<code> RowSet </code>
 * 对象。
 * 首先它在系统属性中搜索它,然后查找资源文件,最后它检查已设置的JNDI上下文。
 * 在下面的代码片段中,创建一个新的<code> CachedRowSet </code>对象并使用<i> env </i>进行初始化,它包含对<code> MyProvider </code>的绑定。
 * <PRE>
 *  Hashtable env = new Hashtable(); env.put(SyncFactory.ROWSET_SYNC_PROVIDER,"com.fred.providers.MyProv
 * ider"); CachedRowSet crs = new com.sun.rowset.CachedRowSetImpl(env);。
 * </PRE>
 *  有关这些机制的更多详细信息,请参阅<code> javax.sql.rowset.spi </code>包规范。
 * 
 * 
 * @author  Jonathan Bruce
 * @see javax.sql.rowset.spi.SyncProvider
 * @see javax.sql.rowset.spi.SyncFactoryException
 */
public class SyncFactory {

    /**
     * Creates a new <code>SyncFactory</code> object, which is the singleton
     * instance.
     * Having a private constructor guarantees that no more than
     * one <code>SyncProvider</code> object can exist at a time.
     * <p>
     * 创建一个新的<code> SyncFactory </code>对象,这是单例实例。具有私有构造函数保证一次只能存在一个<code> SyncProvider </code>对象。
     * 
     */
    private SyncFactory() {
    }

    /**
     * The standard property-id for a synchronization provider implementation
     * name.
     * <p>
     *  同步提供程序实现名称的标准属性ID。
     * 
     */
    public static final String ROWSET_SYNC_PROVIDER =
            "rowset.provider.classname";
    /**
     * The standard property-id for a synchronization provider implementation
     * vendor name.
     * <p>
     *  同步提供程序实现供应商名称的标准属性ID。
     * 
     */
    public static final String ROWSET_SYNC_VENDOR =
            "rowset.provider.vendor";
    /**
     * The standard property-id for a synchronization provider implementation
     * version tag.
     * <p>
     *  同步提供程序实现版本标记的标准属性ID。
     * 
     */
    public static final String ROWSET_SYNC_PROVIDER_VERSION =
            "rowset.provider.version";
    /**
     * The standard resource file name.
     * <p>
     *  标准资源文件名。
     * 
     */
    private static String ROWSET_PROPERTIES = "rowset.properties";

    /**
     *  Permission required to invoke setJNDIContext and setLogger
     * <p>
     *  调用setJNDIContext和setLogger所需的权限
     * 
     */
    private static final SQLPermission SET_SYNCFACTORY_PERMISSION =
            new SQLPermission("setSyncFactory");
    /**
     * The initial JNDI context where <code>SyncProvider</code> implementations can
     * be stored and from which they can be invoked.
     * <p>
     *  可以存储并从中调用<code> SyncProvider </code>实现的初始JNDI上下文。
     * 
     */
    private static Context ic;
    /**
     * The <code>Logger</code> object to be used by the <code>SyncFactory</code>.
     * <p>
     *  SyncFactory </code>使用的<code> Logger </code>对象。
     * 
     */
    private static volatile Logger rsLogger;

    /**
     * The registry of available <code>SyncProvider</code> implementations.
     * See section 2.0 of the class comment for <code>SyncFactory</code> for an
     * explanation of how a provider can be added to this registry.
     * <p>
     *  可用的<code> SyncProvider </code>实现的注册表。有关如何将提供程序添加到此注册表的说明,请参阅<code> SyncFactory </code>的类注释的2.0节。
     * 
     */
    private static Hashtable<String, SyncProvider> implementations;

    /**
     * Adds the the given synchronization provider to the factory register. Guidelines
     * are provided in the <code>SyncProvider</code> specification for the
     * required naming conventions for <code>SyncProvider</code>
     * implementations.
     * <p>
     * Synchronization providers bound to a JNDI context can be
     * registered by binding a SyncProvider instance to a JNDI namespace.
     *
     * <pre>
     * {@code
     * SyncProvider p = new MySyncProvider();
     * InitialContext ic = new InitialContext();
     * ic.bind ("jdbc/rowset/MySyncProvider", p);
     * } </pre>
     *
     * Furthermore, an initial JNDI context should be set with the
     * <code>SyncFactory</code> using the <code>setJNDIContext</code> method.
     * The <code>SyncFactory</code> leverages this context to search for
     * available <code>SyncProvider</code> objects bound to the JNDI
     * context and its child nodes.
     *
     * <p>
     *  将给定的同步提供程序添加到出厂寄存器。在<code> SyncProvider </code>规范中为<code> SyncProvider </code>实现所需的命名约定提供了指导。
     * <p>
     *  可以通过将SyncProvider实例绑定到JNDI命名空间来注册绑定到JNDI上下文的同步提供程序。
     * 
     * <pre>
     *  {@code SyncProvider p = new MySyncProvider(); InitialContext ic = new InitialContext(); ic.bind("jdbc / rowset / MySyncProvider",p); }
     *  </pre>。
     * 
     * 此外,应当使用<code> setJNDIContext </code>方法用<code> SyncFactory </code>来设置初始JNDI上下文。
     *  <code> SyncFactory </code>利用此上下文来搜索绑定到JNDI上下文及其子节点的可用<code> SyncProvider </code>对象。
     * 
     * 
     * @param providerID A <code>String</code> object with the unique ID of the
     *             synchronization provider being registered
     * @throws SyncFactoryException if an attempt is made to supply an empty
     *         or null provider name
     * @see #setJNDIContext
     */
    public static synchronized void registerProvider(String providerID)
            throws SyncFactoryException {

        ProviderImpl impl = new ProviderImpl();
        impl.setClassname(providerID);
        initMapIfNecessary();
        implementations.put(providerID, impl);

    }

    /**
     * Returns the <code>SyncFactory</code> singleton.
     *
     * <p>
     *  返回<code> SyncFactory </code>单例。
     * 
     * 
     * @return the <code>SyncFactory</code> instance
     */
    public static SyncFactory getSyncFactory() {
        /*
         * Using Initialization on Demand Holder idiom as
         * Effective Java 2nd Edition,ITEM 71, indicates it is more performant
         * than the Double-Check Locking idiom.
         * <p>
         *  使用Initialization on Demand Holder惯用法作为有效的Java第2版,ITEM 71,表明它比双重检查锁定习语更有效。
         * 
         */
        return SyncFactoryHolder.factory;
    }

    /**
     * Removes the designated currently registered synchronization provider from the
     * Factory SPI register.
     *
     * <p>
     *  从工厂SPI寄存器中删除指定的当前注册的同步提供程序。
     * 
     * 
     * @param providerID The unique-id of the synchronization provider
     * @throws SyncFactoryException If an attempt is made to
     * unregister a SyncProvider implementation that was not registered.
     */
    public static synchronized void unregisterProvider(String providerID)
            throws SyncFactoryException {
        initMapIfNecessary();
        if (implementations.containsKey(providerID)) {
            implementations.remove(providerID);
        }
    }
    private static String colon = ":";
    private static String strFileSep = "/";

    private static synchronized void initMapIfNecessary() throws SyncFactoryException {

        // Local implementation class names and keys from Properties
        // file, translate names into Class objects using Class.forName
        // and store mappings
        final Properties properties = new Properties();

        if (implementations == null) {
            implementations = new Hashtable<>();

            try {

                // check if user is supplying his Synchronisation Provider
                // Implementation if not using Oracle's implementation.
                // properties.load(new FileInputStream(ROWSET_PROPERTIES));

                // The rowset.properties needs to be in jdk/jre/lib when
                // integrated with jdk.
                // else it should be picked from -D option from command line.

                // -Drowset.properties will add to standard properties. Similar
                // keys will over-write

                /*
                 * Dependent on application
                 * <p>
                 *  取决于应用程序
                 * 
                 */
                String strRowsetProperties;
                try {
                    strRowsetProperties = AccessController.doPrivileged(new PrivilegedAction<String>() {
                        public String run() {
                            return System.getProperty("rowset.properties");
                        }
                    }, null, new PropertyPermission("rowset.properties", "read"));
                } catch (Exception ex) {
                    System.out.println("errorget rowset.properties: " + ex);
                    strRowsetProperties = null;
                };

                if (strRowsetProperties != null) {
                    // Load user's implementation of SyncProvider
                    // here. -Drowset.properties=/abc/def/pqr.txt
                    ROWSET_PROPERTIES = strRowsetProperties;
                    try (FileInputStream fis = new FileInputStream(ROWSET_PROPERTIES)) {
                        properties.load(fis);
                    }
                    parseProperties(properties);
                }

                /*
                 * Always available
                 * <p>
                 *  始终可用
                 * 
                 */
                ROWSET_PROPERTIES = "javax" + strFileSep + "sql" +
                        strFileSep + "rowset" + strFileSep +
                        "rowset.properties";

                ClassLoader cl = Thread.currentThread().getContextClassLoader();

                try {
                    AccessController.doPrivileged((PrivilegedExceptionAction<Void>) () -> {
                        try (InputStream stream = (cl == null) ?
                                ClassLoader.getSystemResourceAsStream(ROWSET_PROPERTIES)
                                : cl.getResourceAsStream(ROWSET_PROPERTIES)) {
                            if (stream == null) {
                                throw new SyncFactoryException("Resource " + ROWSET_PROPERTIES + " not found");
                            }
                            properties.load(stream);
                        }
                        return null;
                    });
                } catch (PrivilegedActionException ex) {
                    Throwable e = ex.getException();
                    if (e instanceof SyncFactoryException) {
                      throw (SyncFactoryException) e;
                    } else {
                        SyncFactoryException sfe = new SyncFactoryException();
                        sfe.initCause(ex.getException());
                        throw sfe;
                    }
                }

                parseProperties(properties);

            // removed else, has properties should sum together

            } catch (FileNotFoundException e) {
                throw new SyncFactoryException("Cannot locate properties file: " + e);
            } catch (IOException e) {
                throw new SyncFactoryException("IOException: " + e);
            }

            /*
             * Now deal with -Drowset.provider.classname
             * load additional properties from -D command line
             * <p>
             *  现在处理-Drowset.provider.classname从-D命令行加载额外的属性
             * 
             */
            properties.clear();
            String providerImpls;
            try {
                providerImpls = AccessController.doPrivileged(new PrivilegedAction<String>() {
                    public String run() {
                        return System.getProperty(ROWSET_SYNC_PROVIDER);
                    }
                }, null, new PropertyPermission(ROWSET_SYNC_PROVIDER, "read"));
            } catch (Exception ex) {
                providerImpls = null;
            }

            if (providerImpls != null) {
                int i = 0;
                if (providerImpls.indexOf(colon) > 0) {
                    StringTokenizer tokenizer = new StringTokenizer(providerImpls, colon);
                    while (tokenizer.hasMoreElements()) {
                        properties.put(ROWSET_SYNC_PROVIDER + "." + i, tokenizer.nextToken());
                        i++;
                    }
                } else {
                    properties.put(ROWSET_SYNC_PROVIDER, providerImpls);
                }
                parseProperties(properties);
            }
        }
    }

    /**
     * The internal debug switch.
     * <p>
     *  内部调试开关。
     * 
     */
    private static boolean debug = false;
    /**
     * Internal registry count for the number of providers contained in the
     * registry.
     * <p>
     *  注册表中包含的提供程序数量的内部注册表计数。
     * 
     */
    private static int providerImplIndex = 0;

    /**
     * Internal handler for all standard property parsing. Parses standard
     * ROWSET properties and stores lazy references into the the internal registry.
     * <p>
     *  所有标准属性解析的内部处理程序。解析标准ROWSET属性并将延迟引用存储到内部注册表中。
     * 
     */
    private static void parseProperties(Properties p) {

        ProviderImpl impl = null;
        String key = null;
        String[] propertyNames = null;

        for (Enumeration<?> e = p.propertyNames(); e.hasMoreElements();) {

            String str = (String) e.nextElement();

            int w = str.length();

            if (str.startsWith(SyncFactory.ROWSET_SYNC_PROVIDER)) {

                impl = new ProviderImpl();
                impl.setIndex(providerImplIndex++);

                if (w == (SyncFactory.ROWSET_SYNC_PROVIDER).length()) {
                    // no property index has been set.
                    propertyNames = getPropertyNames(false);
                } else {
                    // property index has been set.
                    propertyNames = getPropertyNames(true, str.substring(w - 1));
                }

                key = p.getProperty(propertyNames[0]);
                impl.setClassname(key);
                impl.setVendor(p.getProperty(propertyNames[1]));
                impl.setVersion(p.getProperty(propertyNames[2]));
                implementations.put(key, impl);
            }
        }
    }

    /**
     * Used by the parseProperties methods to disassemble each property tuple.
     * <p>
     *  由parseProperties方法使用来反汇编每个属性元组。
     * 
     */
    private static String[] getPropertyNames(boolean append) {
        return getPropertyNames(append, null);
    }

    /**
     * Disassembles each property and its associated value. Also handles
     * overloaded property names that contain indexes.
     * <p>
     *  拆分每个属性及其关联值。还处理包含索引的重载属性名称。
     * 
     */
    private static String[] getPropertyNames(boolean append,
            String propertyIndex) {
        String dot = ".";
        String[] propertyNames =
                new String[]{SyncFactory.ROWSET_SYNC_PROVIDER,
            SyncFactory.ROWSET_SYNC_VENDOR,
            SyncFactory.ROWSET_SYNC_PROVIDER_VERSION};
        if (append) {
            for (int i = 0; i < propertyNames.length; i++) {
                propertyNames[i] = propertyNames[i] +
                        dot +
                        propertyIndex;
            }
            return propertyNames;
        } else {
            return propertyNames;
        }
    }

    /**
     * Internal debug method that outputs the registry contents.
     * <p>
     *  输出注册表内容的内部调试方法。
     * 
     */
    private static void showImpl(ProviderImpl impl) {
        System.out.println("Provider implementation:");
        System.out.println("Classname: " + impl.getClassname());
        System.out.println("Vendor: " + impl.getVendor());
        System.out.println("Version: " + impl.getVersion());
        System.out.println("Impl index: " + impl.getIndex());
    }

    /**
     * Returns the <code>SyncProvider</code> instance identified by <i>providerID</i>.
     *
     * <p>
     *  返回由<i> providerID </i>标识的<code> SyncProvider </code>实例。
     * 
     * 
     * @param providerID the unique identifier of the provider
     * @return a <code>SyncProvider</code> implementation
     * @throws SyncFactoryException If the SyncProvider cannot be found,
     * the providerID is {@code null}, or
     * some error was encountered when trying to invoke this provider.
     */
    public static SyncProvider getInstance(String providerID)
            throws SyncFactoryException {

        if(providerID == null) {
            throw new SyncFactoryException("The providerID cannot be null");
        }

        initMapIfNecessary(); // populate HashTable
        initJNDIContext();    // check JNDI context for any additional bindings

        ProviderImpl impl = (ProviderImpl) implementations.get(providerID);

        if (impl == null) {
            // Requested SyncProvider is unavailable. Return default provider.
            return new com.sun.rowset.providers.RIOptimisticProvider();
        }

        try {
            ReflectUtil.checkPackageAccess(providerID);
        } catch (java.security.AccessControlException e) {
            SyncFactoryException sfe = new SyncFactoryException();
            sfe.initCause(e);
            throw sfe;
        }

        // Attempt to invoke classname from registered SyncProvider list
        Class<?> c = null;
        try {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();

            /**
             * The SyncProvider implementation of the user will be in
             * the classpath. We need to find the ClassLoader which loads
             * this SyncFactory and try to load the SyncProvider class from
             * there.
             * <p>
             *  用户的SyncProvider实现将在类路径中。我们需要找到加载此SyncFactory并尝试从那里加载SyncProvider类的ClassLoader。
             * 
             * 
             **/
            c = Class.forName(providerID, true, cl);

            if (c != null) {
                return (SyncProvider) c.newInstance();
            } else {
                return new com.sun.rowset.providers.RIOptimisticProvider();
            }

        } catch (IllegalAccessException e) {
            throw new SyncFactoryException("IllegalAccessException: " + e.getMessage());
        } catch (InstantiationException e) {
            throw new SyncFactoryException("InstantiationException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new SyncFactoryException("ClassNotFoundException: " + e.getMessage());
        }
    }

    /**
     * Returns an Enumeration of currently registered synchronization
     * providers.  A <code>RowSet</code> implementation may use any provider in
     * the enumeration as its <code>SyncProvider</code> object.
     * <p>
     * At a minimum, the reference synchronization provider allowing
     * RowSet content data to be stored using a JDBC driver should be
     * possible.
     *
     * <p>
     * 返回当前注册的同步提供程序的枚举。 <code> RowSet </code>实现可以使用枚举中的任何提供程序作为其<code> SyncProvider </code>对象。
     * <p>
     *  至少,允许使用JDBC驱动程序存储RowSet内容数据的引用同步提供程序应该是可能的。
     * 
     * 
     * @return Enumeration  A enumeration of available synchronization
     * providers that are registered with this Factory
     * @throws SyncFactoryException If an error occurs obtaining the registered
     * providers
     */
    public static Enumeration<SyncProvider> getRegisteredProviders()
            throws SyncFactoryException {
        initMapIfNecessary();
        // return a collection of classnames
        // of type SyncProvider
        return implementations.elements();
    }

    /**
     * Sets the logging object to be used by the <code>SyncProvider</code>
     * implementation provided by the <code>SyncFactory</code>. All
     * <code>SyncProvider</code> implementations can log their events to
     * this object and the application can retrieve a handle to this
     * object using the <code>getLogger</code> method.
     * <p>
     * This method checks to see that there is an {@code SQLPermission}
     * object  which grants the permission {@code setSyncFactory}
     * before allowing the method to succeed.  If a
     * {@code SecurityManager} exists and its
     * {@code checkPermission} method denies calling {@code setLogger},
     * this method throws a
     * {@code java.lang.SecurityException}.
     *
     * <p>
     *  设置由<code> SyncFactory </code>提供的<code> SyncProvider </code>实现使用的日志对象。
     * 所有<code> SyncProvider </code>实现都可以将它们的事件记录到这个对象,应用程序可以使用<code> getLogger </code>方法检索这个对象的句柄。
     * <p>
     *  此方法检查以查看是否有一个{@code SQLPermission}对象,在允许方法成功之前授予{@code setSyncFactory}权限。
     * 如果存在{@code SecurityManager}并且其{@code checkPermission}方法拒绝调用{@code setLogger},则此方法会抛出{@code java.lang.SecurityException}
     * 。
     *  此方法检查以查看是否有一个{@code SQLPermission}对象,在允许方法成功之前授予{@code setSyncFactory}权限。
     * 
     * 
     * @param logger A Logger object instance
     * @throws java.lang.SecurityException if a security manager exists and its
     *   {@code checkPermission} method denies calling {@code setLogger}
     * @throws NullPointerException if the logger is null
     * @see SecurityManager#checkPermission
     */
    public static void setLogger(Logger logger) {

        SecurityManager sec = System.getSecurityManager();
        if (sec != null) {
            sec.checkPermission(SET_SYNCFACTORY_PERMISSION);
        }

        if(logger == null){
            throw new NullPointerException("You must provide a Logger");
        }
        rsLogger = logger;
    }

    /**
     * Sets the logging object that is used by <code>SyncProvider</code>
     * implementations provided by the <code>SyncFactory</code> SPI. All
     * <code>SyncProvider</code> implementations can log their events
     * to this object and the application can retrieve a handle to this
     * object using the <code>getLogger</code> method.
     * <p>
     * This method checks to see that there is an {@code SQLPermission}
     * object  which grants the permission {@code setSyncFactory}
     * before allowing the method to succeed.  If a
     * {@code SecurityManager} exists and its
     * {@code checkPermission} method denies calling {@code setLogger},
     * this method throws a
     * {@code java.lang.SecurityException}.
     *
     * <p>
     *  设置由<code> SyncFactory </code> SPI提供的<code> SyncProvider </code>实现使用的日志对象。
     * 所有<code> SyncProvider </code>实现都可以将它们的事件记录到这个对象,应用程序可以使用<code> getLogger </code>方法检索这个对象的句柄。
     * <p>
     * 此方法检查以查看是否有一个{@code SQLPermission}对象,在允许方法成功之前授予{@code setSyncFactory}权限。
     * 如果存在{@code SecurityManager}并且其{@code checkPermission}方法拒绝调用{@code setLogger},则此方法会抛出{@code java.lang.SecurityException}
     * 。
     * 此方法检查以查看是否有一个{@code SQLPermission}对象,在允许方法成功之前授予{@code setSyncFactory}权限。
     * 
     * 
     * @param logger a Logger object instance
     * @param level a Level object instance indicating the degree of logging
     * required
     * @throws java.lang.SecurityException if a security manager exists and its
     *   {@code checkPermission} method denies calling {@code setLogger}
     * @throws NullPointerException if the logger is null
     * @see SecurityManager#checkPermission
     * @see LoggingPermission
     */
    public static void setLogger(Logger logger, Level level) {
        // singleton
        SecurityManager sec = System.getSecurityManager();
        if (sec != null) {
            sec.checkPermission(SET_SYNCFACTORY_PERMISSION);
        }

        if(logger == null){
            throw new NullPointerException("You must provide a Logger");
        }
        logger.setLevel(level);
        rsLogger = logger;
    }

    /**
     * Returns the logging object for applications to retrieve
     * synchronization events posted by SyncProvider implementations.
     * <p>
     *  返回应用程序检索由SyncProvider实现发布的同步事件的日志对象。
     * 
     * 
     * @return The {@code Logger} that has been specified for use by
     * {@code SyncProvider} implementations
     * @throws SyncFactoryException if no logging object has been set.
     */
    public static Logger getLogger() throws SyncFactoryException {

        Logger result = rsLogger;
        // only one logger per session
        if (result == null) {
            throw new SyncFactoryException("(SyncFactory) : No logger has been set");
        }

        return result;
    }

    /**
     * Sets the initial JNDI context from which SyncProvider implementations
     * can be retrieved from a JNDI namespace
     * <p>
     *  This method checks to see that there is an {@code SQLPermission}
     * object  which grants the permission {@code setSyncFactory}
     * before allowing the method to succeed.  If a
     * {@code SecurityManager} exists and its
     * {@code checkPermission} method denies calling {@code setJNDIContext},
     * this method throws a
     * {@code java.lang.SecurityException}.
     *
     * <p>
     *  设置可以从JNDI命名空间检索SyncProvider实现的初始JNDI上下文
     * <p>
     *  此方法检查以查看是否有一个{@code SQLPermission}对象,在允许方法成功之前授予{@code setSyncFactory}权限。
     * 如果{@code SecurityManager}存在且其{@code checkPermission}方法拒绝调用{@code setJNDIContext},此方法会抛出{@code java.lang.SecurityException}
     * 。
     *  此方法检查以查看是否有一个{@code SQLPermission}对象,在允许方法成功之前授予{@code setSyncFactory}权限。
     * 
     * 
     * @param ctx a valid JNDI context
     * @throws SyncFactoryException if the supplied JNDI context is null
     * @throws java.lang.SecurityException if a security manager exists and its
     *  {@code checkPermission} method denies calling {@code setJNDIContext}
     * @see SecurityManager#checkPermission
     */
    public static synchronized void setJNDIContext(javax.naming.Context ctx)
            throws SyncFactoryException {
        SecurityManager sec = System.getSecurityManager();
        if (sec != null) {
            sec.checkPermission(SET_SYNCFACTORY_PERMISSION);
        }
        if (ctx == null) {
            throw new SyncFactoryException("Invalid JNDI context supplied");
        }
        ic = ctx;
    }

    /**
     * Controls JNDI context initialization.
     *
     * <p>
     *  控制JNDI上下文初始化。
     * 
     * 
     * @throws SyncFactoryException if an error occurs parsing the JNDI context
     */
    private static synchronized void initJNDIContext() throws SyncFactoryException {

        if ((ic != null) && (lazyJNDICtxRefresh == false)) {
            try {
                parseProperties(parseJNDIContext());
                lazyJNDICtxRefresh = true; // touch JNDI namespace once.
            } catch (NamingException e) {
                e.printStackTrace();
                throw new SyncFactoryException("SPI: NamingException: " + e.getExplanation());
            } catch (Exception e) {
                e.printStackTrace();
                throw new SyncFactoryException("SPI: Exception: " + e.getMessage());
            }
        }
    }
    /**
     * Internal switch indicating whether the JNDI namespace should be re-read.
     * <p>
     *  内部开关,指示是否应重新读取JNDI命名空间。
     * 
     */
    private static boolean lazyJNDICtxRefresh = false;

    /**
     * Parses the set JNDI Context and passes bindings to the enumerateBindings
     * method when complete.
     * <p>
     *  解析集合JNDI上下文,并在完成后将绑定传递给enumerateBindings方法。
     * 
     */
    private static Properties parseJNDIContext() throws NamingException {

        NamingEnumeration<?> bindings = ic.listBindings("");
        Properties properties = new Properties();

        // Hunt one level below context for available SyncProvider objects
        enumerateBindings(bindings, properties);

        return properties;
    }

    /**
     * Scans each binding on JNDI context and determines if any binding is an
     * instance of SyncProvider, if so, add this to the registry and continue to
     * scan the current context using a re-entrant call to this method until all
     * bindings have been enumerated.
     * <p>
     *  扫描JNDI上下文中的每个绑定,并确定是否有任何绑定是SyncProvider的实例,如果是,则将其添加到注册表中,并使用对此方法的重入调用继续扫描当前上下文,直到所有绑定都已枚举。
     * 
     */
    private static void enumerateBindings(NamingEnumeration<?> bindings,
            Properties properties) throws NamingException {

        boolean syncProviderObj = false; // move to parameters ?

        try {
            Binding bd = null;
            Object elementObj = null;
            String element = null;
            while (bindings.hasMore()) {
                bd = (Binding) bindings.next();
                element = bd.getName();
                elementObj = bd.getObject();

                if (!(ic.lookup(element) instanceof Context)) {
                    // skip directories/sub-contexts
                    if (ic.lookup(element) instanceof SyncProvider) {
                        syncProviderObj = true;
                    }
                }

                if (syncProviderObj) {
                    SyncProvider sync = (SyncProvider) elementObj;
                    properties.put(SyncFactory.ROWSET_SYNC_PROVIDER,
                            sync.getProviderID());
                    syncProviderObj = false; // reset
                }

            }
        } catch (javax.naming.NotContextException e) {
            bindings.next();
            // Re-entrant call into method
            enumerateBindings(bindings, properties);
        }
    }

    /**
     * Lazy initialization Holder class used by {@code getSyncFactory}
     * <p>
     *  Lazy初始化{@code getSyncFactory}使用的Holder类
     * 
     */
    private static class SyncFactoryHolder {
        static final SyncFactory factory = new SyncFactory();
    }
}

/**
 * Internal class that defines the lazy reference construct for each registered
 * SyncProvider implementation.
 * <p>
 * 为每个注册的SyncProvider实现定义延迟引用构造的内部类。
 * 
 */
class ProviderImpl extends SyncProvider {

    private String className = null;
    private String vendorName = null;
    private String ver = null;
    private int index;

    public void setClassname(String classname) {
        className = classname;
    }

    public String getClassname() {
        return className;
    }

    public void setVendor(String vendor) {
        vendorName = vendor;
    }

    public String getVendor() {
        return vendorName;
    }

    public void setVersion(String providerVer) {
        ver = providerVer;
    }

    public String getVersion() {
        return ver;
    }

    public void setIndex(int i) {
        index = i;
    }

    public int getIndex() {
        return index;
    }

    public int getDataSourceLock() throws SyncProviderException {

        int dsLock = 0;
        try {
            dsLock = SyncFactory.getInstance(className).getDataSourceLock();
        } catch (SyncFactoryException sfEx) {

            throw new SyncProviderException(sfEx.getMessage());
        }

        return dsLock;
    }

    public int getProviderGrade() {

        int grade = 0;

        try {
            grade = SyncFactory.getInstance(className).getProviderGrade();
        } catch (SyncFactoryException sfEx) {
            //
        }

        return grade;
    }

    public String getProviderID() {
        return className;
    }

    /*
    public javax.sql.RowSetInternal getRowSetInternal() {
    try
    {
    return SyncFactory.getInstance(className).getRowSetInternal();
    } catch(SyncFactoryException sfEx) {
    //
    }
    }
    /* <p>
    /*  public javax.sql.RowSetInternal getRowSetInternal(){try {return SyncFactory.getInstance(className).getRowSetInternal(); }
    /*  catch(SyncFactoryException sfEx){//}}。
     */
    public javax.sql.RowSetReader getRowSetReader() {

        RowSetReader rsReader = null;

        try {
            rsReader = SyncFactory.getInstance(className).getRowSetReader();
        } catch (SyncFactoryException sfEx) {
            //
        }

        return rsReader;

    }

    public javax.sql.RowSetWriter getRowSetWriter() {

        RowSetWriter rsWriter = null;
        try {
            rsWriter = SyncFactory.getInstance(className).getRowSetWriter();
        } catch (SyncFactoryException sfEx) {
            //
        }

        return rsWriter;
    }

    public void setDataSourceLock(int param)
            throws SyncProviderException {

        try {
            SyncFactory.getInstance(className).setDataSourceLock(param);
        } catch (SyncFactoryException sfEx) {

            throw new SyncProviderException(sfEx.getMessage());
        }
    }

    public int supportsUpdatableView() {

        int view = 0;

        try {
            view = SyncFactory.getInstance(className).supportsUpdatableView();
        } catch (SyncFactoryException sfEx) {
            //
        }

        return view;
    }
}
