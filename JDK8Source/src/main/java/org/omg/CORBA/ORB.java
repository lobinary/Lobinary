/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2014, Oracle and/or its affiliates. All rights reserved.
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

package org.omg.CORBA;

import org.omg.CORBA.portable.*;
import org.omg.CORBA.ORBPackage.InvalidName;

import java.util.Properties;
import java.applet.Applet;
import java.io.File;
import java.io.FileInputStream;

import java.security.AccessController;
import java.security.PrivilegedAction;

import sun.reflect.misc.ReflectUtil;

/**
 * A class providing APIs for the CORBA Object Request Broker
 * features.  The <code>ORB</code> class also provides
 * "pluggable ORB implementation" APIs that allow another vendor's ORB
 * implementation to be used.
 * <P>
 * An ORB makes it possible for CORBA objects to communicate
 * with each other by connecting objects making requests (clients) with
 * objects servicing requests (servers).
 * <P>
 *
 * The <code>ORB</code> class, which
 * encapsulates generic CORBA functionality, does the following:
 * (Note that items 5 and 6, which include most of the methods in
 * the class <code>ORB</code>, are typically used with the <code>Dynamic Invocation
 * Interface</code> (DII) and the <code>Dynamic Skeleton Interface</code>
 * (DSI).
 * These interfaces may be used by a developer directly, but
 * most commonly they are used by the ORB internally and are
 * not seen by the general programmer.)
 * <OL>
 * <li> initializes the ORB implementation by supplying values for
 *      predefined properties and environmental parameters
 * <li> obtains initial object references to services such as
 * the NameService using the method <code>resolve_initial_references</code>
 * <li> converts object references to strings and back
 * <li> connects the ORB to a servant (an instance of a CORBA object
 * implementation) and disconnects the ORB from a servant
 * <li> creates objects such as
 *   <ul>
 *   <li><code>TypeCode</code>
 *   <li><code>Any</code>
 *   <li><code>NamedValue</code>
 *   <li><code>Context</code>
 *   <li><code>Environment</code>
 *   <li>lists (such as <code>NVList</code>) containing these objects
 *   </ul>
 * <li> sends multiple messages in the DII
 * </OL>
 *
 * <P>
 * The <code>ORB</code> class can be used to obtain references to objects
 * implemented anywhere on the network.
 * <P>
 * An application or applet gains access to the CORBA environment
 * by initializing itself into an <code>ORB</code> using one of
 * three <code>init</code> methods.  Two of the three methods use the properties
 * (associations of a name with a value) shown in the
 * table below.<BR>
 * <TABLE BORDER=1 SUMMARY="Standard Java CORBA Properties">
 * <TR><TH>Property Name</TH>   <TH>Property Value</TH></TR>
 * <CAPTION>Standard Java CORBA Properties:</CAPTION>
 *     <TR><TD>org.omg.CORBA.ORBClass</TD>
 *     <TD>class name of an ORB implementation</TD></TR>
 *     <TR><TD>org.omg.CORBA.ORBSingletonClass</TD>
 *     <TD>class name of the ORB returned by <code>init()</code></TD></TR>
 * </TABLE>
 * <P>
 * These properties allow a different vendor's <code>ORB</code>
 * implementation to be "plugged in."
 * <P>
 * When an ORB instance is being created, the class name of the ORB
 * implementation is located using
 * the following standard search order:<P>
 *
 * <OL>
 *     <LI>check in Applet parameter or application string array, if any
 *
 *     <LI>check in properties parameter, if any
 *
 *     <LI>check in the System properties
 *
 *     <LI>check in the orb.properties file located in the user.home
 *         directory (if any)
 *
 *     <LI>check in the orb.properties file located in the java.home/lib
 *         directory (if any)
 *
 *     <LI>fall back on a hardcoded default behavior (use the Java&nbsp;IDL
 *         implementation)
 * </OL>
 * <P>
 * Note that Java&nbsp;IDL provides a default implementation for the
 * fully-functional ORB and for the Singleton ORB.  When the method
 * <code>init</code> is given no parameters, the default Singleton
 * ORB is returned.  When the method <code>init</code> is given parameters
 * but no ORB class is specified, the Java&nbsp;IDL ORB implementation
 * is returned.
 * <P>
 * The following code fragment creates an <code>ORB</code> object
 * initialized with the default ORB Singleton.
 * This ORB has a
 * restricted implementation to prevent malicious applets from doing
 * anything beyond creating typecodes.
 * It is called a singleton
 * because there is only one instance for an entire virtual machine.
 * <PRE>
 *    ORB orb = ORB.init();
 * </PRE>
 * <P>
 * The following code fragment creates an <code>ORB</code> object
 * for an application.  The parameter <code>args</code>
 * represents the arguments supplied to the application's <code>main</code>
 * method.  Since the property specifies the ORB class to be
 * "SomeORBImplementation", the new ORB will be initialized with
 * that ORB implementation.  If p had been null,
 * and the arguments had not specified an ORB class,
 * the new ORB would have been
 * initialized with the default Java&nbsp;IDL implementation.
 * <PRE>
 *    Properties p = new Properties();
 *    p.put("org.omg.CORBA.ORBClass", "SomeORBImplementation");
 *    ORB orb = ORB.init(args, p);
 * </PRE>
 * <P>
 * The following code fragment creates an <code>ORB</code> object
 * for the applet supplied as the first parameter.  If the given
 * applet does not specify an ORB class, the new ORB will be
 * initialized with the default Java&nbsp;IDL implementation.
 * <PRE>
 *    ORB orb = ORB.init(myApplet, null);
 * </PRE>
 * <P>
 * An application or applet can be initialized in one or more ORBs.
 * ORB initialization is a bootstrap call into the CORBA world.
 * <p>
 *  一个为CORBA对象请求代理提供API的类。 <code> ORB </code>类还提供了"可插入ORB实现"API,允许使用另一个供应商的ORB实现。
 * <P>
 *  ORB使得CORBA对象可以通过将请求(客户端)与用于请求(服务器)的对象相连接的对象来进行通信。
 * <P>
 * 
 *  封装通用CORBA功能的<code> ORB </code>类执行以下操作：(注意,包含类<code> ORB </code>中大多数方法的项目5和6通常用于与动态调用接口</code>(DII)和<code>
 * 动态骨架接口</code>(DSI)</code>这些接口可以由开发人员直接使用,但最常见的是它们被ORB内部并且不被一般程序员看到。
 * )。
 * <OL>
 *  <li>通过为预定义属性和环境参数提供值来初始化ORB实现<li>使用方法<code> resolve_initial_references </code> <li>将对象引用转换为字符串并返回<li>
 * 将ORB连接到服务方(CORBA对象实现的实例),并将ORB与服务方断开连接<li>创建对象,例如。
 * <ul>
 * <li> <code> <code> <code> </code> <li> <code> </code> <li> </环境</code> <li>包含这些对象的列表(例如<code> NVList 
 * </code>)。
 * </ul>
 *  <li>在DII中发送多个消息
 * </OL>
 * 
 * <P>
 *  <code> ORB </code>类可用于获取对网络上任何位置实现的对象的引用。
 * <P>
 *  应用程序或小应用程序通过使用三个<code> init </code>方法之一将自身初始化为<code> ORB </code>来访问CORBA环境。
 * 三个方法中的两个使用下表中所示的属性(名称与值的关联)。<BR>。
 * <TABLE BORDER=1 SUMMARY="Standard Java CORBA Properties">
 *  <TR> <TH>属性名称</TH> <TH>属性值</TH> </TR> <CAPTION>标准Java CORBA属性：</CAPTION> <TR> <TD> org.omg.CORBA.ORB
 * Class </TD> <TD> ORB实现的类名</TD> </TR> <TR> <TD> org.omg.CORBA.ORBSingletonClass </TD> <TD> <code> > in
 * it()</code> </TD> </TR>。
 * </TABLE>
 * <P>
 *  这些属性允许不同的供应商的<code> ORB </code>实现"插入"。
 * <P>
 *  当创建ORB实例时,使用以下标准搜索顺序找到ORB实现的类名：<P>
 * 
 * <OL>
 *  <LI>检入Applet参数或应用程序字符串数组(如果有)
 * 
 *  <LI>签入属性参数(如果有)
 * 
 *  <LI>签入系统属性
 * 
 *  <LI>检入位于user.home目录中的orb.properties文件(如果有)
 * 
 *  <LI>检入位于java.home / lib目录中的orb.properties文件(如果有)
 * 
 * <LI>回退硬编码的默认行为(使用Java的IDL实现)
 * </OL>
 * <P>
 *  请注意,Java IDL为完全功能的ORB和Singleton ORB提供了默认实现。当方法<code> init </code>没有给出参数时,返回默认的Singleton ORB。
 * 当方法<code> init </code>被赋予参数但没有指定ORB类时,将返回Java&ID; IDL ORB实现。
 * <P>
 *  以下代码片段创建使用默认ORB Singleton初始化的<code> ORB </code>对象。这个ORB有一个受限制的实现,以防止恶意小程序做任何事情,除了创建类型代码。
 * 它被称为单例,因为整个虚拟机只有一个实例。
 * <PRE>
 *  ORB orb = ORB.init();
 * </PRE>
 * <P>
 *  以下代码片段为应用程序创建一个<code> ORB </code>对象。参数<code> args </code>表示提供给应用程序的<code> main </code>方法的参数。
 * 由于该属性将ORB类指定为"SomeORBImplementation",因此将使用该ORB实现初始化新的ORB。
 * 如果p为null,并且参数没有指定ORB类,则新的ORB将使用默认的Java&ID; IDL实现初始化。
 * <PRE>
 *  属性p = new Properties(); p.put("org.omg.CORBA.ORBClass","SomeORBImplementation"); ORB orb = ORB.init(
 * args,p);。
 * </PRE>
 * <P>
 * 以下代码片段为作为第一个参数提供的小程序创建了一个<code> ORB </code>对象。如果给定的小程序没有指定ORB类,则新的ORB将使用默认的Java IDL实现初始化。
 * <PRE>
 *  ORB orb = ORB.init(myApplet,null);
 * </PRE>
 * <P>
 *  应用程序或小应用程序可以在一个或多个ORB中初始化。 ORB初始化是一个引导调用进入CORBA世界。
 * 
 * 
 * @since   JDK1.2
 */
abstract public class ORB {

    //
    // This is the ORB implementation used when nothing else is specified.
    // Whoever provides this class customizes this string to
    // point at their ORB implementation.
    //
    private static final String ORBClassKey = "org.omg.CORBA.ORBClass";
    private static final String ORBSingletonClassKey = "org.omg.CORBA.ORBSingletonClass";

    //
    // The global instance of the singleton ORB implementation which
    // acts as a factory for typecodes for generated Helper classes.
    // TypeCodes should be immutable since they may be shared across
    // different security contexts (applets). There should be no way to
    // use a TypeCode as a storage depot for illicitly passing
    // information or Java objects between different security contexts.
    //
    static private ORB singleton;

    // Get System property
    private static String getSystemProperty(final String name) {

        // This will not throw a SecurityException because this
        // class was loaded from rt.jar using the bootstrap classloader.
        String propValue = (String) AccessController.doPrivileged(
            new PrivilegedAction() {
                public java.lang.Object run() {
                    return System.getProperty(name);
                }
            }
        );

        return propValue;
    }

    // Get property from orb.properties in either <user.home> or <java-home>/lib
    // directories.
    private static String getPropertyFromFile(final String name) {
        // This will not throw a SecurityException because this
        // class was loaded from rt.jar using the bootstrap classloader.

        String propValue = (String) AccessController.doPrivileged(
            new PrivilegedAction() {
                private Properties getFileProperties( String fileName ) {
                    try {
                        File propFile = new File( fileName ) ;
                        if (!propFile.exists())
                            return null ;

                        Properties props = new Properties() ;
                        FileInputStream fis = new FileInputStream(propFile);
                        try {
                            props.load( fis );
                        } finally {
                            fis.close() ;
                        }

                        return props ;
                    } catch (Exception exc) {
                        return null ;
                    }
                }

                public java.lang.Object run() {
                    String userHome = System.getProperty("user.home");
                    String fileName = userHome + File.separator +
                        "orb.properties" ;
                    Properties props = getFileProperties( fileName ) ;

                    if (props != null) {
                        String value = props.getProperty( name ) ;
                        if (value != null)
                            return value ;
                    }

                    String javaHome = System.getProperty("java.home");
                    fileName = javaHome + File.separator
                        + "lib" + File.separator + "orb.properties";
                    props = getFileProperties( fileName ) ;

                    if (props == null)
                        return null ;
                    else
                        return props.getProperty( name ) ;
                }
            }
        );

        return propValue;
    }

    /**
     * Returns the <code>ORB</code> singleton object. This method always returns the
     * same ORB instance, which is an instance of the class described by the
     * <code>org.omg.CORBA.ORBSingletonClass</code> system property.
     * <P>
     * This no-argument version of the method <code>init</code> is used primarily
     * as a factory for <code>TypeCode</code> objects, which are used by
     * <code>Helper</code> classes to implement the method <code>type</code>.
     * It is also used to create <code>Any</code> objects that are used to
     * describe <code>union</code> labels (as part of creating a <code>
     * TypeCode</code> object for a <code>union</code>).
     * <P>
     * This method is not intended to be used by applets, and in the event
     * that it is called in an applet environment, the ORB it returns
     * is restricted so that it can be used only as a factory for
     * <code>TypeCode</code> objects.  Any <code>TypeCode</code> objects
     * it produces can be safely shared among untrusted applets.
     * <P>
     * If an ORB is created using this method from an applet,
     * a system exception will be thrown if
     * methods other than those for
     * creating <code>TypeCode</code> objects are invoked.
     *
     * <p>
     *  返回<code> ORB </code>单例对象。
     * 此方法总是返回相同的ORB实例,它是由<code> org.omg.CORBA.ORBSingletonClass </code>系统属性描述的类的实例。
     * <P>
     *  方法<code> init </code>的无参版本主要用作<code> TypeCode </code>对象的工厂,它由<code> Helper </code>类用来实现方法<code> type
     *  </code>。
     * 它还用于创建用于描述<code> union </code>标签的<code>任何</code>对象(作为为<code>联合创建<code> TypeCode </code>对象的一部分) </code>
     * )。
     * <P>
     *  这个方法不是为applet使用的,如果在applet环境中调用它,它返回的ORB是受限的,因此它只能用作<code> TypeCode </code>对象的工厂。
     * 它产生的任何<code> TypeCode </code>对象可以安全地在不受信任的applet之间共享。
     * <P>
     * 如果从applet中使用这个方法创建一个ORB,如果调用不是创建<code> TypeCode </code>对象的方法,系统异常将被抛出。
     * 
     * 
     * @return the singleton ORB
     */
    public static synchronized ORB init() {
        if (singleton == null) {
            String className = getSystemProperty(ORBSingletonClassKey);
            if (className == null)
                className = getPropertyFromFile(ORBSingletonClassKey);
            if ((className == null) ||
                    (className.equals("com.sun.corba.se.impl.orb.ORBSingleton"))) {
                singleton = new com.sun.corba.se.impl.orb.ORBSingleton();
            } else {
                singleton = create_impl(className);
            }
        }
        return singleton;
    }

    private static ORB create_impl(String className) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if (cl == null)
            cl = ClassLoader.getSystemClassLoader();

        try {
            ReflectUtil.checkPackageAccess(className);
            Class<org.omg.CORBA.ORB> orbBaseClass = org.omg.CORBA.ORB.class;
            Class<?> orbClass = Class.forName(className, true, cl).asSubclass(orbBaseClass);
            return (ORB)orbClass.newInstance();
        } catch (Throwable ex) {
            SystemException systemException = new INITIALIZE(
               "can't instantiate default ORB implementation " + className);
            systemException.initCause(ex);
            throw systemException;
        }
    }

    /**
     * Creates a new <code>ORB</code> instance for a standalone
     * application.  This method may be called from applications
     * only and returns a new fully functional <code>ORB</code> object
     * each time it is called.
     * <p>
     *  为独立应用程序创建新的<code> ORB </code>实例。此方法可以从应用程序中调用,并且每次调用时都返回一个新的完全功能的<code> ORB </code>对象。
     * 
     * 
     * @param args command-line arguments for the application's <code>main</code>
     *             method; may be <code>null</code>
     * @param props application-specific properties; may be <code>null</code>
     * @return the newly-created ORB instance
     */
    public static ORB init(String[] args, Properties props) {
        //
        // Note that there is no standard command-line argument for
        // specifying the default ORB implementation. For an
        // application you can choose an implementation either by
        // setting the CLASSPATH to pick a different org.omg.CORBA
        // and it's baked-in ORB implementation default or by
        // setting an entry in the properties object or in the
        // system properties.
        //
        String className = null;
        ORB orb;

        if (props != null)
            className = props.getProperty(ORBClassKey);
        if (className == null)
            className = getSystemProperty(ORBClassKey);
        if (className == null)
            className = getPropertyFromFile(ORBClassKey);
        if ((className == null) ||
                    (className.equals("com.sun.corba.se.impl.orb.ORBImpl"))) {
            orb = new com.sun.corba.se.impl.orb.ORBImpl();
        } else {
            orb = create_impl(className);
        }
        orb.set_parameters(args, props);
        return orb;
    }


    /**
     * Creates a new <code>ORB</code> instance for an applet.  This
     * method may be called from applets only and returns a new
     * fully-functional <code>ORB</code> object each time it is called.
     * <p>
     *  为小程序创建新的<code> ORB </code>实例。这个方法只能从applet中调用,每次调用时都会返回一个新的全功能的<code> ORB </code>对象。
     * 
     * 
     * @param app the applet; may be <code>null</code>
     * @param props applet-specific properties; may be <code>null</code>
     * @return the newly-created ORB instance
     */
    public static ORB init(Applet app, Properties props) {
        String className;
        ORB orb;

        className = app.getParameter(ORBClassKey);
        if (className == null && props != null)
            className = props.getProperty(ORBClassKey);
        if (className == null)
            className = getSystemProperty(ORBClassKey);
        if (className == null)
            className = getPropertyFromFile(ORBClassKey);
        if ((className == null) ||
                    (className.equals("com.sun.corba.se.impl.orb.ORBImpl"))) {
            orb = new com.sun.corba.se.impl.orb.ORBImpl();
        } else {
            orb = create_impl(className);
        }
        orb.set_parameters(app, props);
        return orb;
    }

    /**
     * Allows the ORB implementation to be initialized with the given
     * parameters and properties. This method, used in applications only,
     * is implemented by subclass ORB implementations and called
     * by the appropriate <code>init</code> method to pass in its parameters.
     *
     * <p>
     *  允许使用给定的参数和属性初始化ORB实现。此方法仅在应用程序中使用,由子类ORB实现实现,并通过相应的<code> init </code>方法调用以传递其参数。
     * 
     * 
     * @param args command-line arguments for the application's <code>main</code>
     *             method; may be <code>null</code>
     * @param props application-specific properties; may be <code>null</code>
     */
    abstract protected void set_parameters(String[] args, Properties props);

    /**
     * Allows the ORB implementation to be initialized with the given
     * applet and parameters. This method, used in applets only,
     * is implemented by subclass ORB implementations and called
     * by the appropriate <code>init</code> method to pass in its parameters.
     *
     * <p>
     *  允许使用给定的小程序和参数初始化ORB实现。这个方法仅在applet中使用,通过子类ORB实现来实现,并由适当的<code> init </code>方法调用以传递其参数。
     * 
     * 
     * @param app the applet; may be <code>null</code>
     * @param props applet-specific properties; may be <code>null</code>
     */
    abstract protected void set_parameters(Applet app, Properties props);

    /**
     * Connects the given servant object (a Java object that is
     * an instance of the server implementation class)
     * to the ORB. The servant class must
     * extend the <code>ImplBase</code> class corresponding to the interface that is
     * supported by the server. The servant must thus be a CORBA object
     * reference, and inherit from <code>org.omg.CORBA.Object</code>.
     * Servants created by the user can start receiving remote invocations
     * after the method <code>connect</code> has been called. A servant may also be
     * automatically and implicitly connected to the ORB if it is passed as
     * an IDL parameter in an IDL method invocation on a non-local object,
     * that is, if the servant object has to be marshalled and sent outside of the
     * process address space.
     * <P>
     * Calling the method <code>connect</code> has no effect
     * when the servant object is already connected to the ORB.
     * <P>
     * Deprecated by the OMG in favor of the Portable Object Adapter APIs.
     *
     * <p>
     * 将给定的servant对象(作为服务器实现类的实例的Java对象)连接到ORB。 servant类必须扩展与服务器支持的接口相对应的<code> ImplBase </code>类。
     * 因此,servant必须是一个CORBA对象引用,并且继承自<code> org.omg.CORBA.Object </code>。
     * 在调用<code> connect </code>方法后,用户创建的服务器可以开始接收远程调用。
     * 如果服务方在非本地对象的IDL方法调用中作为IDL参数传递,也就是说,如果服务方对象必须被编组并发送到进程地址之外,那么服务方也可以自动和隐式地连接到ORB空间。
     * <P>
     *  当servant对象已经连接到ORB时,调用方法<code> connect </code>不起作用。
     * <P>
     *  由OMG弃用,支持便携式对象适配器API。
     * 
     * 
     * @param obj The servant object reference
     */
    public void connect(org.omg.CORBA.Object obj) {
        throw new NO_IMPLEMENT();
    }

    /**
     * Destroys the ORB so that its resources can be reclaimed.
     * Any operation invoked on a destroyed ORB reference will throw the
     * <code>OBJECT_NOT_EXIST</code> exception.
     * Once an ORB has been destroyed, another call to <code>init</code>
     * with the same ORBid will return a reference to a newly constructed ORB.<p>
     * If <code>destroy</code> is called on an ORB that has not been shut down,
     * it will start the shut down process and block until the ORB has shut down
     * before it destroys the ORB.<br>
     * If an application calls <code>destroy</code> in a thread that is currently servicing
     * an invocation, the <code>BAD_INV_ORDER</code> system exception will be thrown
     * with the OMG minor code 3, since blocking would result in a deadlock.<p>
     * For maximum portability and to avoid resource leaks, an application should
     * always call <code>shutdown</code> and <code>destroy</code>
     * on all ORB instances before exiting.
     *
     * <p>
     * 销毁ORB,以便其资源可以回收。在被破坏的ORB引用上调用的任何操作都将抛出<code> OBJECT_NOT_EXIST </code>异常。
     * 一旦ORB被销毁,使用相同ORBid对<code> init </code>的另一个调用将返回对新构造的ORB的引用。
     * <p>如果在ORB上调用<code> destroy </code>尚未关闭,它将启动关闭进程并阻止,直到ORB在其销毁ORB之前已关闭。
     * <br>如果应用程序在当前正在服务于某个线程的线程中调用<code> destroy </code>调用,因为阻塞会导致死锁,因此将会使用OMG次代码3抛出<code> BAD_INV_ORDER </code>
     * 系统异常。
     * <p>如果在ORB上调用<code> destroy </code>尚未关闭,它将启动关闭进程并阻止,直到ORB在其销毁ORB之前已关闭。
     * <p>为了获得最大的可移植性并避免资源泄露,应用程序应始终调用<code > shutdown </code>和<code> destroy </code>,然后退出。
     * 
     * 
     * @throws org.omg.CORBA.BAD_INV_ORDER if the current thread is servicing an invocation
     */
    public void destroy( ) {
        throw new NO_IMPLEMENT();
    }

    /**
     * Disconnects the given servant object from the ORB. After this method returns,
     * the ORB will reject incoming remote requests for the disconnected
     * servant and will send the exception
     * <code>org.omg.CORBA.OBJECT_NOT_EXIST</code> back to the
     * remote client. Thus the object appears to be destroyed from the
     * point of view of remote clients. Note, however, that local requests issued
     * using the servant  directly do not
     * pass through the ORB; hence, they will continue to be processed by the
     * servant.
     * <P>
     * Calling the method <code>disconnect</code> has no effect
     * if the servant is not connected to the ORB.
     * <P>
     * Deprecated by the OMG in favor of the Portable Object Adapter APIs.
     *
     * <p>
     *  断开给定的servant对象与ORB的连接。
     * 此方法返回后,ORB将拒绝为断开连接的servant传入的远程请求,并将异常<code> org.omg.CORBA.OBJECT_NOT_EXIST </code>发送回远程客户端。
     * 因此,对象看起来从远程客户端的角度被销毁。但是,请注意,使用servant直接发出的本地请求不通过ORB;因此,他们将继续由仆人处理。
     * <P>
     *  如果服务方未连接到ORB,则调用方法<code> disconnect </code>没有效果。
     * <P>
     * 由OMG弃用,支持便携式对象适配器API。
     * 
     * 
     * @param obj The servant object to be disconnected from the ORB
     */
    public void disconnect(org.omg.CORBA.Object obj) {
        throw new NO_IMPLEMENT();
    }

    //
    // ORB method implementations.
    //
    // We are trying to accomplish 2 things at once in this class.
    // It can act as a default ORB implementation front-end,
    // creating an actual ORB implementation object which is a
    // subclass of this ORB class and then delegating the method
    // implementations.
    //
    // To accomplish the delegation model, the 'delegate' private instance
    // variable is set if an instance of this class is created directly.
    //

    /**
     * Returns a list of the initially available CORBA object references,
     * such as "NameService" and "InterfaceRepository".
     *
     * <p>
     *  返回最初可用的CORBA对象引用的列表,例如"NameService"和"InterfaceRepository"。
     * 
     * 
     * @return an array of <code>String</code> objects that represent
     *         the object references for CORBA services
     *         that are initially available with this ORB
     */
    abstract public String[] list_initial_services();

    /**
     * Resolves a specific object reference from the set of available
     * initial service names.
     *
     * <p>
     *  从可用的初始服务名称集合中解析特定的对象引用。
     * 
     * 
     * @param object_name the name of the initial service as a string
     * @return  the object reference associated with the given name
     * @exception InvalidName if the given name is not associated with a
     *                         known service
     */
    abstract public org.omg.CORBA.Object resolve_initial_references(String object_name)
        throws InvalidName;

    /**
     * Converts the given CORBA object reference to a string.
     * Note that the format of this string is predefined by IIOP, allowing
     * strings generated by a different ORB to be converted back into an object
     * reference.
     * <P>
     * The resulting <code>String</code> object may be stored or communicated
     * in any way that a <code>String</code> object can be manipulated.
     *
     * <p>
     *  将给定的CORBA对象引用转换为字符串。请注意,此字符串的格式由IIOP预定义,允许由不同ORB生成的字符串转换回对象引用。
     * <P>
     *  生成的<code> String </code>对象可以以可以操作<code> String </code>对象的任何方式存储或传递。
     * 
     * 
     * @param obj the object reference to stringify
     * @return the string representing the object reference
     */
    abstract public String object_to_string(org.omg.CORBA.Object obj);

    /**
     * Converts a string produced by the method <code>object_to_string</code>
     * back to a CORBA object reference.
     *
     * <p>
     *  将由方法<code> object_to_string </code>生成的字符串转换回CORBA对象引用。
     * 
     * 
     * @param str the string to be converted back to an object reference.  It must
     * be the result of converting an object reference to a string using the
     * method <code>object_to_string</code>.
     * @return the object reference
     */
    abstract public org.omg.CORBA.Object string_to_object(String str);

    /**
     * Allocates an <code>NVList</code> with (probably) enough
     * space for the specified number of <code>NamedValue</code> objects.
     * Note that the specified size is only a hint to help with
     * storage allocation and does not imply the maximum size of the list.
     *
     * <p>
     *  为(可能)为指定数量的<code> NamedValue </code>对象分配<code> NVList </code>足够的空间。
     * 请注意,指定的大小仅仅是帮助存储分配的提示,并不意味着列表的最大大小。
     * 
     * 
     * @param count  suggested number of <code>NamedValue</code> objects for
     *               which to allocate space
     * @return the newly-created <code>NVList</code>
     *
     * @see NVList
     */
    abstract public NVList create_list(int count);

    /**
     * Creates an <code>NVList</code> initialized with argument
     * descriptions for the operation described in the given
     * <code>OperationDef</code> object.  This <code>OperationDef</code> object
     * is obtained from an Interface Repository. The arguments in the
     * returned <code>NVList</code> object are in the same order as in the
     * original IDL operation definition, which makes it possible for the list
     * to be used in dynamic invocation requests.
     *
     * <p>
     *  创建用给定的<code> OperationDef </code>对象中描述的操作的参数描述初始化的<code> NVList </code>。
     * 这个<code> OperationDef </code>对象从接口存储库获得。
     * 返回的<code> NVList </code>对象中的参数与原始IDL操作定义中的参数顺序相同,这使得该列表可以在动态调用请求中使用。
     * 
     * 
     * @param oper      the <code>OperationDef</code> object to use to create the list
     * @return          a newly-created <code>NVList</code> object containing
     * descriptions of the arguments to the method described in the given
     * <code>OperationDef</code> object
     *
     * @see NVList
     */
    public NVList create_operation_list(org.omg.CORBA.Object oper)
    {
        // If we came here, it means that the actual ORB implementation
        // did not have a create_operation_list(...CORBA.Object oper) method,
        // so lets check if it has a create_operation_list(OperationDef oper)
        // method.
        try {
            // First try to load the OperationDef class
            String opDefClassName = "org.omg.CORBA.OperationDef";
            Class<?> opDefClass = null;

            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            if ( cl == null )
                cl = ClassLoader.getSystemClassLoader();
            // if this throws a ClassNotFoundException, it will be caught below.
            opDefClass = Class.forName(opDefClassName, true, cl);

            // OK, we loaded OperationDef. Now try to get the
            // create_operation_list(OperationDef oper) method.
            Class<?>[] argc = { opDefClass };
            java.lang.reflect.Method meth =
                this.getClass().getMethod("create_operation_list", argc);

            // OK, the method exists, so invoke it and be happy.
            java.lang.Object[] argx = { oper };
            return (org.omg.CORBA.NVList)meth.invoke(this, argx);
        }
        catch( java.lang.reflect.InvocationTargetException exs ) {
            Throwable t = exs.getTargetException();
            if (t instanceof Error) {
                throw (Error) t;
            }
            else if (t instanceof RuntimeException) {
                throw (RuntimeException) t;
            }
            else {
                throw new org.omg.CORBA.NO_IMPLEMENT();
            }
        }
        catch( RuntimeException ex ) {
            throw ex;
        }
        catch( Exception exr ) {
            throw new org.omg.CORBA.NO_IMPLEMENT();
        }
    }


    /**
     * Creates a <code>NamedValue</code> object
     * using the given name, value, and argument mode flags.
     * <P>
     * A <code>NamedValue</code> object serves as (1) a parameter or return
     * value or (2) a context property.
     * It may be used by itself or
     * as an element in an <code>NVList</code> object.
     *
     * <p>
     *  使用给定的名称,值和参数模式标志创建<code> NamedValue </code>对象。
     * <P>
     * <code> NamedValue </code>对象用作(1)参数或返回值或(2)上下文属性。它可以单独使用或作为<code> NVList </code>对象中的元素使用。
     * 
     * 
     * @param s  the name of the <code>NamedValue</code> object
     * @param any  the <code>Any</code> value to be inserted into the
     *             <code>NamedValue</code> object
     * @param flags  the argument mode flags for the <code>NamedValue</code>: one of
     * <code>ARG_IN.value</code>, <code>ARG_OUT.value</code>,
     * or <code>ARG_INOUT.value</code>.
     *
     * @return  the newly-created <code>NamedValue</code> object
     * @see NamedValue
     */
    abstract public NamedValue create_named_value(String s, Any any, int flags);

    /**
     * Creates an empty <code>ExceptionList</code> object.
     *
     * <p>
     *  创建一个空的<code> ExceptionList </code>对象。
     * 
     * 
     * @return  the newly-created <code>ExceptionList</code> object
     */
    abstract public ExceptionList create_exception_list();

    /**
     * Creates an empty <code>ContextList</code> object.
     *
     * <p>
     *  创建一个空的<code> ContextList </code>对象。
     * 
     * 
     * @return  the newly-created <code>ContextList</code> object
     * @see ContextList
     * @see Context
     */
    abstract public ContextList create_context_list();

    /**
     * Gets the default <code>Context</code> object.
     *
     * <p>
     *  获取默认的<code> Context </code>对象。
     * 
     * 
     * @return the default <code>Context</code> object
     * @see Context
     */
    abstract public Context get_default_context();

    /**
     * Creates an <code>Environment</code> object.
     *
     * <p>
     *  创建<code>环境</code>对象。
     * 
     * 
     * @return  the newly-created <code>Environment</code> object
     * @see Environment
     */
    abstract public Environment create_environment();

    /**
     * Creates a new <code>org.omg.CORBA.portable.OutputStream</code> into which
     * IDL method parameters can be marshalled during method invocation.
     * <p>
     *  创建一个新的<code> org.omg.CORBA.portable.OutputStream </code>,其中IDL方法参数可以在方法调用期间编组。
     * 
     * 
     * @return          the newly-created
     *              <code>org.omg.CORBA.portable.OutputStream</code> object
     */
    abstract public org.omg.CORBA.portable.OutputStream create_output_stream();

    /**
     * Sends multiple dynamic (DII) requests asynchronously without expecting
     * any responses. Note that oneway invocations are not guaranteed to
     * reach the server.
     *
     * <p>
     *  以异步方式发送多个动态(DII)请求,而不需要任何响应。请注意,单向调用不能保证到达服务器。
     * 
     * 
     * @param req               an array of request objects
     */
    abstract public void send_multiple_requests_oneway(Request[] req);

    /**
     * Sends multiple dynamic (DII) requests asynchronously.
     *
     * <p>
     *  异步发送多个动态(DII)请求。
     * 
     * 
     * @param req               an array of <code>Request</code> objects
     */
    abstract public void send_multiple_requests_deferred(Request[] req);

    /**
     * Finds out if any of the deferred (asynchronous) invocations have
     * a response yet.
     * <p>
     *  找出任何延迟(异步)调用是否有响应。
     * 
     * 
     * @return <code>true</code> if there is a response available;
     *         <code> false</code> otherwise
     */
    abstract public boolean poll_next_response();

    /**
     * Gets the next <code>Request</code> instance for which a response
     * has been received.
     *
     * <p>
     *  获取已收到响应的下一个<code>请求</code>实例。
     * 
     * 
     * @return          the next <code>Request</code> object ready with a response
     * @exception WrongTransaction if the method <code>get_next_response</code>
     * is called from a transaction scope different
     * from the one from which the original request was sent. See the
     * OMG Transaction Service specification for details.
     */
    abstract public Request get_next_response() throws WrongTransaction;

    /**
     * Retrieves the <code>TypeCode</code> object that represents
     * the given primitive IDL type.
     *
     * <p>
     *  检索表示给定基本IDL类型的<code> TypeCode </code>对象。
     * 
     * 
     * @param tcKind    the <code>TCKind</code> instance corresponding to the
     *                  desired primitive type
     * @return          the requested <code>TypeCode</code> object
     */
    abstract public TypeCode get_primitive_tc(TCKind tcKind);

    /**
     * Creates a <code>TypeCode</code> object representing an IDL <code>struct</code>.
     * The <code>TypeCode</code> object is initialized with the given id,
     * name, and members.
     *
     * <p>
     *  创建表示IDL <code> struct </code>的<code> TypeCode </code>对象。
     * 使用给定的id,name和成员来初始化<code> TypeCode </code>对象。
     * 
     * 
     * @param id        the repository id for the <code>struct</code>
     * @param name      the name of the <code>struct</code>
     * @param members   an array describing the members of the <code>struct</code>
     * @return          a newly-created <code>TypeCode</code> object describing
     *              an IDL <code>struct</code>
     */
    abstract public TypeCode create_struct_tc(String id, String name,
                                              StructMember[] members);

    /**
     * Creates a <code>TypeCode</code> object representing an IDL <code>union</code>.
     * The <code>TypeCode</code> object is initialized with the given id,
     * name, discriminator type, and members.
     *
     * <p>
     *  创建表示IDL <code> union </code>的<code> TypeCode </code>对象。
     * 使用给定的id,name,discriminator类型和成员来初始化<code> TypeCode </code>对象。
     * 
     * 
     * @param id        the repository id of the <code>union</code>
     * @param name      the name of the <code>union</code>
     * @param discriminator_type        the type of the <code>union</code> discriminator
     * @param members   an array describing the members of the <code>union</code>
     * @return          a newly-created <code>TypeCode</code> object describing
     *              an IDL <code>union</code>
     */
    abstract public TypeCode create_union_tc(String id, String name,
                                             TypeCode discriminator_type,
                                             UnionMember[] members);

    /**
     * Creates a <code>TypeCode</code> object representing an IDL <code>enum</code>.
     * The <code>TypeCode</code> object is initialized with the given id,
     * name, and members.
     *
     * <p>
     * 创建表示IDL <code>枚举</code>的<code> TypeCode </code>对象。使用给定的id,name和成员来初始化<code> TypeCode </code>对象。
     * 
     * 
     * @param id        the repository id for the <code>enum</code>
     * @param name      the name for the <code>enum</code>
     * @param members   an array describing the members of the <code>enum</code>
     * @return          a newly-created <code>TypeCode</code> object describing
     *              an IDL <code>enum</code>
     */
    abstract public TypeCode create_enum_tc(String id, String name, String[] members);

    /**
     * Creates a <code>TypeCode</code> object representing an IDL <code>alias</code>
     * (<code>typedef</code>).
     * The <code>TypeCode</code> object is initialized with the given id,
     * name, and original type.
     *
     * <p>
     *  创建表示IDL <code>别名</code>(<code> typedef </code>)的<code> TypeCode </code>对象。
     * 使用给定的id,name和original类型初始化<code> TypeCode </code>对象。
     * 
     * 
     * @param id        the repository id for the alias
     * @param name      the name for the alias
     * @param original_type
     *                  the <code>TypeCode</code> object describing the original type
     *          for which this is an alias
     * @return          a newly-created <code>TypeCode</code> object describing
     *              an IDL <code>alias</code>
     */
    abstract public TypeCode create_alias_tc(String id, String name,
                                             TypeCode original_type);

    /**
     * Creates a <code>TypeCode</code> object representing an IDL <code>exception</code>.
     * The <code>TypeCode</code> object is initialized with the given id,
     * name, and members.
     *
     * <p>
     *  创建代表IDL <code>异常</code>的<code> TypeCode </code>对象。使用给定的id,name和成员来初始化<code> TypeCode </code>对象。
     * 
     * 
     * @param id        the repository id for the <code>exception</code>
     * @param name      the name for the <code>exception</code>
     * @param members   an array describing the members of the <code>exception</code>
     * @return          a newly-created <code>TypeCode</code> object describing
     *              an IDL <code>exception</code>
     */
    abstract public TypeCode create_exception_tc(String id, String name,
                                                 StructMember[] members);

    /**
     * Creates a <code>TypeCode</code> object representing an IDL <code>interface</code>.
     * The <code>TypeCode</code> object is initialized with the given id
     * and name.
     *
     * <p>
     *  创建代表IDL <code>接口</code>的<code> TypeCode </code>对象。使用给定的id和名称初始化<code> TypeCode </code>对象。
     * 
     * 
     * @param id        the repository id for the interface
     * @param name      the name for the interface
     * @return          a newly-created <code>TypeCode</code> object describing
     *              an IDL <code>interface</code>
     */

    abstract public TypeCode create_interface_tc(String id, String name);

    /**
     * Creates a <code>TypeCode</code> object representing a bounded IDL
     * <code>string</code>.
     * The <code>TypeCode</code> object is initialized with the given bound,
     * which represents the maximum length of the string. Zero indicates
     * that the string described by this type code is unbounded.
     *
     * <p>
     *  创建代表有界IDL <code> string </code>的<code> TypeCode </code>对象。
     * 使用给定的bound来初始化<code> TypeCode </code>对象,它表示字符串的最大长度。零表示此类型代码描述的字符串是无界的。
     * 
     * 
     * @param bound     the bound for the <code>string</code>; cannot be negative
     * @return          a newly-created <code>TypeCode</code> object describing
     *              a bounded IDL <code>string</code>
     * @exception BAD_PARAM if bound is a negative value
     */

    abstract public TypeCode create_string_tc(int bound);

    /**
     * Creates a <code>TypeCode</code> object representing a bounded IDL
     * <code>wstring</code> (wide string).
     * The <code>TypeCode</code> object is initialized with the given bound,
     * which represents the maximum length of the wide string. Zero indicates
     * that the string described by this type code is unbounded.
     *
     * <p>
     *  创建代表有界IDL <code> wstring </code>(宽字符串)的<code> TypeCode </code>对象。
     * 使用给定的bound来初始化<code> TypeCode </code>对象,它表示宽字符串的最大长度。零表示此类型代码描述的字符串是无界的。
     * 
     * 
     * @param bound     the bound for the <code>wstring</code>; cannot be negative
     * @return          a newly-created <code>TypeCode</code> object describing
     *              a bounded IDL <code>wstring</code>
     * @exception BAD_PARAM if bound is a negative value
     */
    abstract public TypeCode create_wstring_tc(int bound);

    /**
     * Creates a <code>TypeCode</code> object representing an IDL <code>sequence</code>.
     * The <code>TypeCode</code> object is initialized with the given bound and
     * element type.
     *
     * <p>
     *  创建表示IDL <code>序列</code>的<code> TypeCode </code>对象。使用给定的bound和element类型初始化<code> TypeCode </code>对象。
     * 
     * 
     * @param bound     the bound for the <code>sequence</code>, 0 if unbounded
     * @param element_type
     *                  the <code>TypeCode</code> object describing the elements
     *          contained in the <code>sequence</code>
     * @return          a newly-created <code>TypeCode</code> object describing
     *              an IDL <code>sequence</code>
     */
    abstract public TypeCode create_sequence_tc(int bound, TypeCode element_type);

    /**
     * Creates a <code>TypeCode</code> object representing a
     * a recursive IDL <code>sequence</code>.
     * <P>
     * For the IDL <code>struct</code> Node in following code fragment,
     * the offset parameter for creating its sequence would be 1:
     * <PRE>
     *    Struct Node {
     *        long value;
     *        Sequence &lt;Node&gt; subnodes;
     *    };
     * </PRE>
     *
     * <p>
     * 创建表示递归IDL <code>序列</code>的<code> TypeCode </code>对象。
     * <P>
     *  对于以下代码片段中的IDL <code> struct </code> Node,用于创建其序列的offset参数将为1：
     * <PRE>
     *  结构节点{long value;序列&lt;节点&gt;子节点; };
     * </PRE>
     * 
     * 
     * @param bound     the bound for the sequence, 0 if unbounded
     * @param offset    the index to the enclosing <code>TypeCode</code> object
     *                  that describes the elements of this sequence
     * @return          a newly-created <code>TypeCode</code> object describing
     *                   a recursive sequence
     * @deprecated Use a combination of create_recursive_tc and create_sequence_tc instead
     * @see #create_recursive_tc(String) create_recursive_tc
     * @see #create_sequence_tc(int, TypeCode) create_sequence_tc
     */
    @Deprecated
    abstract public TypeCode create_recursive_sequence_tc(int bound, int offset);

    /**
     * Creates a <code>TypeCode</code> object representing an IDL <code>array</code>.
     * The <code>TypeCode</code> object is initialized with the given length and
     * element type.
     *
     * <p>
     *  创建表示IDL <code>数组</code>的<code> TypeCode </code>对象。使用给定的长度和元素类型初始化<code> TypeCode </code>对象。
     * 
     * 
     * @param length    the length of the <code>array</code>
     * @param element_type  a <code>TypeCode</code> object describing the type
     *                      of element contained in the <code>array</code>
     * @return          a newly-created <code>TypeCode</code> object describing
     *              an IDL <code>array</code>
     */
    abstract public TypeCode create_array_tc(int length, TypeCode element_type);

    /**
     * Create a <code>TypeCode</code> object for an IDL native type.
     *
     * <p>
     *  为IDL本机类型创建一个<code> TypeCode </code>对象。
     * 
     * 
     * @param id        the logical id for the native type.
     * @param name      the name of the native type.
     * @return          the requested TypeCode.
     */
    public org.omg.CORBA.TypeCode create_native_tc(String id,
                                                   String name)
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * Create a <code>TypeCode</code> object for an IDL abstract interface.
     *
     * <p>
     *  为IDL抽象接口创建一个<code> TypeCode </code>对象。
     * 
     * 
     * @param id        the logical id for the abstract interface type.
     * @param name      the name of the abstract interface type.
     * @return          the requested TypeCode.
     */
    public org.omg.CORBA.TypeCode create_abstract_interface_tc(
                                                               String id,
                                                               String name)
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }


    /**
     * Create a <code>TypeCode</code> object for an IDL fixed type.
     *
     * <p>
     *  为IDL固定类型创建<code> TypeCode </code>对象。
     * 
     * 
     * @param digits    specifies the total number of decimal digits in the number
     *                  and must be from 1 to 31 inclusive.
     * @param scale     specifies the position of the decimal point.
     * @return          the requested TypeCode.
     */
    public org.omg.CORBA.TypeCode create_fixed_tc(short digits, short scale)
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }


    // orbos 98-01-18: Objects By Value -- begin


    /**
     * Create a <code>TypeCode</code> object for an IDL value type.
     * The concrete_base parameter is the TypeCode for the immediate
     * concrete valuetype base of the valuetype for which the TypeCode
     * is being created.
     * It may be null if the valuetype does not have a concrete base.
     *
     * <p>
     *  为IDL值类型创建一个<code> TypeCode </code>对象。 concrete_base参数是要为其创建TypeCode的值类型的立即具体值类型基础的TypeCode。
     * 如果值类型没有具体的基础,它可以为null。
     * 
     * 
     * @param id                 the logical id for the value type.
     * @param name               the name of the value type.
     * @param type_modifier      one of the value type modifier constants:
     *                           VM_NONE, VM_CUSTOM, VM_ABSTRACT or VM_TRUNCATABLE
     * @param concrete_base      a <code>TypeCode</code> object
     *                           describing the concrete valuetype base
     * @param members            an array containing the members of the value type
     * @return                   the requested TypeCode
     */
    public org.omg.CORBA.TypeCode create_value_tc(String id,
                                                  String name,
                                                  short type_modifier,
                                                  TypeCode concrete_base,
                                                  ValueMember[] members)
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * Create a recursive <code>TypeCode</code> object which
     * serves as a placeholder for a concrete TypeCode during the process of creating
     * TypeCodes which contain recursion. The id parameter specifies the repository id of
     * the type for which the recursive TypeCode is serving as a placeholder. Once the
     * recursive TypeCode has been properly embedded in the enclosing TypeCode which
     * corresponds to the specified repository id, it will function as a normal TypeCode.
     * Invoking operations on the recursive TypeCode before it has been embedded in the
     * enclosing TypeCode will result in a <code>BAD_TYPECODE</code> exception.
     * <P>
     * For example, the following IDL type declaration contains recursion:
     * <PRE>
     *    Struct Node {
     *        Sequence&lt;Node&gt; subnodes;
     *    };
     * </PRE>
     * <P>
     * To create a TypeCode for struct Node, you would invoke the TypeCode creation
     * operations as shown below:
     * <PRE>
     * String nodeID = "IDL:Node:1.0";
     * TypeCode recursiveSeqTC = orb.create_sequence_tc(0, orb.create_recursive_tc(nodeID));
     * StructMember[] members = { new StructMember("subnodes", recursiveSeqTC, null) };
     * TypeCode structNodeTC = orb.create_struct_tc(nodeID, "Node", members);
     * </PRE>
     * <P>
     * Also note that the following is an illegal IDL type declaration:
     * <PRE>
     *    Struct Node {
     *        Node next;
     *    };
     * </PRE>
     * <P>
     * Recursive types can only appear within sequences which can be empty.
     * That way marshaling problems, when transmitting the struct in an Any, are avoided.
     * <P>
     * <p>
     * 创建递归的<code> TypeCode </code>对象,在创建包含递归的TypeCode的过程中,该对象充当具体TypeCode的占位符。
     *  id参数指定了递归TypeCode用作占位符的类型的存储库ID。一旦递归TypeCode已经被适当地嵌入到对应于指定的存储库ID的封装类型代码中,它将作为正常的类型代码。
     * 在嵌入到封装的TypeCode之前,在递归TypeCode上调用操作将导致<code> BAD_TYPECODE </code>异常。
     * <P>
     *  例如,以下IDL类型声明包含递归：
     * <PRE>
     *  结构节点{Sequence&lt; Node&gt;子节点; };
     * </PRE>
     * <P>
     *  要为struct Node创建TypeCode,您将调用TypeCode创建操作,如下所示：
     * <PRE>
     *  String nodeID ="IDL：Node：1.0"; TypeCode recursiveSeqTC = orb.create_sequence_tc(0,orb.create_recursi
     * ve_tc(nodeID)); StructMember [] members = {new StructMember("subnodes",recursiveSeqTC,null)}; TypeCod
     * e structNodeTC = orb.create_struct_tc(nodeID,"Node",members);。
     * </PRE>
     * <P>
     *  还要注意以下是一个非法的IDL类型声明：
     * <PRE>
     *  结构节点{Node next; };
     * </PRE>
     * <P>
     *  递归类型只能出现在可以为空的序列中。这样,当在Any中传送结构体时,可以避免调度问题。
     * <P>
     * 
     * @param id                 the logical id of the referenced type
     * @return                   the requested TypeCode
     */
    public org.omg.CORBA.TypeCode create_recursive_tc(String id) {
        // implemented in subclass
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * Creates a <code>TypeCode</code> object for an IDL value box.
     *
     * <p>
     *  为IDL值框创建<code> TypeCode </code>对象。
     * 
     * 
     * @param id                 the logical id for the value type
     * @param name               the name of the value type
     * @param boxed_type         the TypeCode for the type
     * @return                   the requested TypeCode
     */
    public org.omg.CORBA.TypeCode create_value_box_tc(String id,
                                                      String name,
                                                      TypeCode boxed_type)
    {
        // implemented in subclass
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    // orbos 98-01-18: Objects By Value -- end

    /**
     * Creates an IDL <code>Any</code> object initialized to
     * contain a <code>Typecode</code> object whose <code>kind</code> field
     * is set to <code>TCKind.tc_null</code>.
     *
     * <p>
     * 创建一个IDL <code>任何初始化为包含<code>类型代码</code>对象的</code>对象,其<code> kind </code>字段设置为<code> TCKind.tc_null </code>
     * 。
     * 
     * 
     * @return          a newly-created <code>Any</code> object
     */
    abstract public Any create_any();




    /**
     * Retrieves a <code>Current</code> object.
     * The <code>Current</code> interface is used to manage thread-specific
     * information for use by services such as transactions and security.
     *
     * <p>
     *  检索<code>当前</code>对象。 <code>当前</code>接口用于管理线程特定的信息供服务使用,如事务和安全。
     * 
     * 
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     *
     * @return          a newly-created <code>Current</code> object
     * @deprecated      use <code>resolve_initial_references</code>.
     */
    @Deprecated
    public org.omg.CORBA.Current get_current()
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * This operation blocks the current thread until the ORB has
     * completed the shutdown process, initiated when some thread calls
     * <code>shutdown</code>. It may be used by multiple threads which
     * get all notified when the ORB shuts down.
     *
     * <p>
     *  此操作阻塞当前线程,直到ORB完成关闭过程,在某些线程调用<code> shutdown </code>时启动。它可以由多个线程使用,当ORB关闭时获得所有通知。
     * 
     */
    public void run()
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * Instructs the ORB to shut down, which causes all
     * object adapters to shut down, in preparation for destruction.<br>
     * If the <code>wait_for_completion</code> parameter
     * is true, this operation blocks until all ORB processing (including
     * processing of currently executing requests, object deactivation,
     * and other object adapter operations) has completed.
     * If an application does this in a thread that is currently servicing
     * an invocation, the <code>BAD_INV_ORDER</code> system exception
     * will be thrown with the OMG minor code 3,
     * since blocking would result in a deadlock.<br>
     * If the <code>wait_for_completion</code> parameter is <code>FALSE</code>,
     * then shutdown may not have completed upon return.<p>
     * While the ORB is in the process of shutting down, the ORB operates as normal,
     * servicing incoming and outgoing requests until all requests have been completed.
     * Once an ORB has shutdown, only object reference management operations
     * may be invoked on the ORB or any object reference obtained from it.
     * An application may also invoke the <code>destroy</code> operation on the ORB itself.
     * Invoking any other operation will throw the <code>BAD_INV_ORDER</code>
     * system exception with the OMG minor code 4.<p>
     * The <code>ORB.run</code> method will return after
     * <code>shutdown</code> has been called.
     *
     * <p>
     * 指示ORB关闭,这会导致所有对象适配器关闭,以准备销毁。
     * <br>如果<code> wait_for_completion </code>参数为true,则此操作将阻塞,直到所有ORB处理(包括当前处理执行请求,对象去激活和其他对象适配器操作)已经完成。
     * 如果应用程序在当前正在为调用服务的线程中执行此操作,则将使用OMG次代码3抛出<code> BAD_INV_ORDER </code>系统异常,因为阻塞将导致死锁。
     * <br>如果<代码> wait_for_completion </code>参数是<code> FALSE </code>,那么关机可能在返回时没有完成。
     * <p>当ORB正在关闭的过程中,ORB正常运行,直到所有请求都已完成。一旦ORB关闭,只有对象引用管理操作可以在ORB或从其获得的任何对象引用上被调用。
     * 应用程序也可以调用ORB本身的<code> destroy </code>操作。
     * 调用任何其他操作会将<code> BAD_INV_ORDER </code>系统异常抛出OMG次要代码4. <p> <code> ORB.run </code>方法将在<code> shutdown </code>
     * 已被调用。
     * 应用程序也可以调用ORB本身的<code> destroy </code>操作。
     * 
     * 
     * @param wait_for_completion <code>true</code> if the call
     *        should block until the shutdown is complete;
     *        <code>false</code> if it should return immediately
     * @throws org.omg.CORBA.BAD_INV_ORDER if the current thread is servicing
     *         an invocation
     */
    public void shutdown(boolean wait_for_completion)
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * Returns <code>true</code> if the ORB needs the main thread to
     * perform some work, and <code>false</code> if the ORB does not
     * need the main thread.
     *
     * <p>
     *  如果ORB需要主线程来执行一些工作,则返回<code> true </code>,如果ORB不需要主线程,则返回<code> false </code>。
     * 
     * 
     * @return <code>true</code> if there is work pending, meaning that the ORB
     *         needs the main thread to perform some work; <code>false</code>
     *         if there is no work pending and thus the ORB does not need the
     *         main thread
     *
     */
    public boolean work_pending()
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * Performs an implementation-dependent unit of work if called
     * by the main thread. Otherwise it does nothing.
     * The methods <code>work_pending</code> and <code>perform_work</code>
     * can be used in
     * conjunction to implement a simple polling loop that multiplexes
     * the main thread among the ORB and other activities.
     *
     * <p>
     * 如果由主线程调用,则执行与实现相关的工作单元。否则它什么也不做。
     * 可以结合使用方法<code> work_pending </code>和<code> perform_work </code>来实现在ORB和其他活动之间复用主线程的简单轮询循环。
     * 
     */
    public void perform_work()
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * Used to obtain information about CORBA facilities and services
     * that are supported by this ORB. The service type for which
     * information is being requested is passed in as the in
     * parameter <tt>service_type</tt>, the values defined by
     * constants in the CORBA module. If service information is
     * available for that type, that is returned in the out parameter
     * <tt>service_info</tt>, and the operation returns the
     * value <tt>true</tt>. If no information for the requested
     * services type is available, the operation returns <tt>false</tt>
     *  (i.e., the service is not supported by this ORB).
     * <P>
     * <p>
     *  用于获取此ORB支持的CORBA设施和服务的信息。正在请求信息的服务类型作为参数<tt> service_type </tt>传递,这是CORBA模块中的常量定义的值。
     * 如果该类型的服务信息可用,则在out参数<tt> service_info </tt>中返回,操作返回值<tt> true </tt>。
     * 如果没有所请求的服务类型的信息可用,则操作返回<tt> false </tt>(即,该ORB不支持该服务)。
     * <P>
     * 
     * @param service_type a <code>short</code> indicating the
     *        service type for which information is being requested
     * @param service_info a <code>ServiceInformationHolder</code> object
     *        that will hold the <code>ServiceInformation</code> object
     *        produced by this method
     * @return <code>true</code> if service information is available
     *        for the <tt>service_type</tt>;
     *         <tt>false</tt> if no information for the
     *         requested services type is available
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     */
    public boolean get_service_information(short service_type,
                                           ServiceInformationHolder service_info)
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    // orbos 98-01-18: Objects By Value -- begin

    /**
     * Creates a new <code>DynAny</code> object from the given
     * <code>Any</code> object.
     * <P>
     * <p>
     *  从给定的<code> Any </code>对象创建一个新的<code> DynAny </code>对象。
     * <P>
     * 
     * @param value the <code>Any</code> object from which to create a new
     *        <code>DynAny</code> object
     * @return the new <code>DynAny</code> object created from the given
     *         <code>Any</code> object
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     * @deprecated Use the new <a href="../DynamicAny/DynAnyFactory.html">DynAnyFactory</a> API instead
     */
    @Deprecated
    public org.omg.CORBA.DynAny create_dyn_any(org.omg.CORBA.Any value)
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * Creates a basic <code>DynAny</code> object from the given
     * <code>TypeCode</code> object.
     * <P>
     * <p>
     *  从给定的<code> TypeCode </code>对象创建一个基本的<code> DynAny </code>对象。
     * <P>
     * 
     * @param type the <code>TypeCode</code> object from which to create a new
     *        <code>DynAny</code> object
     * @return the new <code>DynAny</code> object created from the given
     *         <code>TypeCode</code> object
     * @throws org.omg.CORBA.ORBPackage.InconsistentTypeCode if the given
     *         <code>TypeCode</code> object is not consistent with the operation.
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     * @deprecated Use the new <a href="../DynamicAny/DynAnyFactory.html">DynAnyFactory</a> API instead
     */
    @Deprecated
    public org.omg.CORBA.DynAny create_basic_dyn_any(org.omg.CORBA.TypeCode type) throws org.omg.CORBA.ORBPackage.InconsistentTypeCode
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * Creates a new <code>DynStruct</code> object from the given
     * <code>TypeCode</code> object.
     * <P>
     * <p>
     *  从给定的<code> TypeCode </code>对象创建一个新的<code> DynStruct </code>对象。
     * <P>
     * 
     * @param type the <code>TypeCode</code> object from which to create a new
     *        <code>DynStruct</code> object
     * @return the new <code>DynStruct</code> object created from the given
     *         <code>TypeCode</code> object
     * @throws org.omg.CORBA.ORBPackage.InconsistentTypeCode if the given
     *         <code>TypeCode</code> object is not consistent with the operation.
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     * @deprecated Use the new <a href="../DynamicAny/DynAnyFactory.html">DynAnyFactory</a> API instead
     */
    @Deprecated
    public org.omg.CORBA.DynStruct create_dyn_struct(org.omg.CORBA.TypeCode type) throws org.omg.CORBA.ORBPackage.InconsistentTypeCode
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * Creates a new <code>DynSequence</code> object from the given
     * <code>TypeCode</code> object.
     * <P>
     * <p>
     *  从给定的<code> TypeCode </code>对象创建一个新的<code> DynSequence </code>对象。
     * <P>
     * 
     * @param type the <code>TypeCode</code> object from which to create a new
     *        <code>DynSequence</code> object
     * @return the new <code>DynSequence</code> object created from the given
     *         <code>TypeCode</code> object
     * @throws org.omg.CORBA.ORBPackage.InconsistentTypeCode if the given
     *         <code>TypeCode</code> object is not consistent with the operation.
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     * @deprecated Use the new <a href="../DynamicAny/DynAnyFactory.html">DynAnyFactory</a> API instead
     */
    @Deprecated
    public org.omg.CORBA.DynSequence create_dyn_sequence(org.omg.CORBA.TypeCode type) throws org.omg.CORBA.ORBPackage.InconsistentTypeCode
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }


    /**
     * Creates a new <code>DynArray</code> object from the given
     * <code>TypeCode</code> object.
     * <P>
     * <p>
     *  从给定的<code> TypeCode </code>对象创建一个新的<code> DynArray </code>对象。
     * <P>
     * 
     * @param type the <code>TypeCode</code> object from which to create a new
     *        <code>DynArray</code> object
     * @return the new <code>DynArray</code> object created from the given
     *         <code>TypeCode</code> object
     * @throws org.omg.CORBA.ORBPackage.InconsistentTypeCode if the given
     *         <code>TypeCode</code> object is not consistent with the operation.
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     * @deprecated Use the new <a href="../DynamicAny/DynAnyFactory.html">DynAnyFactory</a> API instead
     */
    @Deprecated
    public org.omg.CORBA.DynArray create_dyn_array(org.omg.CORBA.TypeCode type) throws org.omg.CORBA.ORBPackage.InconsistentTypeCode
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * Creates a new <code>DynUnion</code> object from the given
     * <code>TypeCode</code> object.
     * <P>
     * <p>
     *  从给定的<code> TypeCode </code>对象创建一个新的<code> DynUnion </code>对象。
     * <P>
     * 
     * @param type the <code>TypeCode</code> object from which to create a new
     *        <code>DynUnion</code> object
     * @return the new <code>DynUnion</code> object created from the given
     *         <code>TypeCode</code> object
     * @throws org.omg.CORBA.ORBPackage.InconsistentTypeCode if the given
     *         <code>TypeCode</code> object is not consistent with the operation.
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     * @deprecated Use the new <a href="../DynamicAny/DynAnyFactory.html">DynAnyFactory</a> API instead
     */
    @Deprecated
    public org.omg.CORBA.DynUnion create_dyn_union(org.omg.CORBA.TypeCode type) throws org.omg.CORBA.ORBPackage.InconsistentTypeCode
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * Creates a new <code>DynEnum</code> object from the given
     * <code>TypeCode</code> object.
     * <P>
     * <p>
     * 从给定的<code> TypeCode </code>对象创建一个新的<code> DynEnum </code>对象。
     * <P>
     * 
     * @param type the <code>TypeCode</code> object from which to create a new
     *        <code>DynEnum</code> object
     * @return the new <code>DynEnum</code> object created from the given
     *         <code>TypeCode</code> object
     * @throws org.omg.CORBA.ORBPackage.InconsistentTypeCode if the given
     *         <code>TypeCode</code> object is not consistent with the operation.
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     * @deprecated Use the new <a href="../DynamicAny/DynAnyFactory.html">DynAnyFactory</a> API instead
     */
    @Deprecated
    public org.omg.CORBA.DynEnum create_dyn_enum(org.omg.CORBA.TypeCode type) throws org.omg.CORBA.ORBPackage.InconsistentTypeCode
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
    * Can be invoked to create new instances of policy objects
    * of a specific type with specified initial state. If
    * <tt>create_policy</tt> fails to instantiate a new Policy
    * object due to its inability to interpret the requested type
    * and content of the policy, it raises the <tt>PolicyError</tt>
    * exception with the appropriate reason.
    * <p>
    *  可以调用以创建具有指定初始状态的特定类型的策略对象的新实例。
    * 如果<tt> create_policy </tt>由于无法解释所请求的策略类型和内容而无法实例化新的Policy对象,则会引发具有相应原因的<tt> PolicyError </tt>异常。
    * 
    * @param type the <tt>PolicyType</tt> of the policy object to
    *        be created
    * @param val the value that will be used to set the initial
    *        state of the <tt>Policy</tt> object that is created
    * @return Reference to a newly created <tt>Policy</tt> object
    *        of type specified by the <tt>type</tt> parameter and
    *        initialized to a state specified by the <tt>val</tt>
    *        parameter
    * @throws <tt>org.omg.CORBA.PolicyError</tt> when the requested
    *        policy is not supported or a requested initial state
    *        for the policy is not supported.
    */
    public org.omg.CORBA.Policy create_policy(int type, org.omg.CORBA.Any val)
        throws org.omg.CORBA.PolicyError
    {
        // Currently not implemented until PIORB.
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }
}
