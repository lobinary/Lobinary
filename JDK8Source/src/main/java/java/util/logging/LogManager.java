/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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


package java.util.logging;

import java.io.*;
import java.util.*;
import java.security.*;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.beans.PropertyChangeListener;
import sun.misc.JavaAWTAccess;
import sun.misc.SharedSecrets;

/**
 * There is a single global LogManager object that is used to
 * maintain a set of shared state about Loggers and log services.
 * <p>
 * This LogManager object:
 * <ul>
 * <li> Manages a hierarchical namespace of Logger objects.  All
 *      named Loggers are stored in this namespace.
 * <li> Manages a set of logging control properties.  These are
 *      simple key-value pairs that can be used by Handlers and
 *      other logging objects to configure themselves.
 * </ul>
 * <p>
 * The global LogManager object can be retrieved using LogManager.getLogManager().
 * The LogManager object is created during class initialization and
 * cannot subsequently be changed.
 * <p>
 * At startup the LogManager class is located using the
 * java.util.logging.manager system property.
 * <p>
 * The LogManager defines two optional system properties that allow control over
 * the initial configuration:
 * <ul>
 * <li>"java.util.logging.config.class"
 * <li>"java.util.logging.config.file"
 * </ul>
 * These two properties may be specified on the command line to the "java"
 * command, or as system property definitions passed to JNI_CreateJavaVM.
 * <p>
 * If the "java.util.logging.config.class" property is set, then the
 * property value is treated as a class name.  The given class will be
 * loaded, an object will be instantiated, and that object's constructor
 * is responsible for reading in the initial configuration.  (That object
 * may use other system properties to control its configuration.)  The
 * alternate configuration class can use <tt>readConfiguration(InputStream)</tt>
 * to define properties in the LogManager.
 * <p>
 * If "java.util.logging.config.class" property is <b>not</b> set,
 * then the "java.util.logging.config.file" system property can be used
 * to specify a properties file (in java.util.Properties format). The
 * initial logging configuration will be read from this file.
 * <p>
 * If neither of these properties is defined then the LogManager uses its
 * default configuration. The default configuration is typically loaded from the
 * properties file "{@code lib/logging.properties}" in the Java installation
 * directory.
 * <p>
 * The properties for loggers and Handlers will have names starting
 * with the dot-separated name for the handler or logger.
 * <p>
 * The global logging properties may include:
 * <ul>
 * <li>A property "handlers".  This defines a whitespace or comma separated
 * list of class names for handler classes to load and register as
 * handlers on the root Logger (the Logger named "").  Each class
 * name must be for a Handler class which has a default constructor.
 * Note that these Handlers may be created lazily, when they are
 * first used.
 *
 * <li>A property "&lt;logger&gt;.handlers". This defines a whitespace or
 * comma separated list of class names for handlers classes to
 * load and register as handlers to the specified logger. Each class
 * name must be for a Handler class which has a default constructor.
 * Note that these Handlers may be created lazily, when they are
 * first used.
 *
 * <li>A property "&lt;logger&gt;.useParentHandlers". This defines a boolean
 * value. By default every logger calls its parent in addition to
 * handling the logging message itself, this often result in messages
 * being handled by the root logger as well. When setting this property
 * to false a Handler needs to be configured for this logger otherwise
 * no logging messages are delivered.
 *
 * <li>A property "config".  This property is intended to allow
 * arbitrary configuration code to be run.  The property defines a
 * whitespace or comma separated list of class names.  A new instance will be
 * created for each named class.  The default constructor of each class
 * may execute arbitrary code to update the logging configuration, such as
 * setting logger levels, adding handlers, adding filters, etc.
 * </ul>
 * <p>
 * Note that all classes loaded during LogManager configuration are
 * first searched on the system class path before any user class path.
 * That includes the LogManager class, any config classes, and any
 * handler classes.
 * <p>
 * Loggers are organized into a naming hierarchy based on their
 * dot separated names.  Thus "a.b.c" is a child of "a.b", but
 * "a.b1" and a.b2" are peers.
 * <p>
 * All properties whose names end with ".level" are assumed to define
 * log levels for Loggers.  Thus "foo.level" defines a log level for
 * the logger called "foo" and (recursively) for any of its children
 * in the naming hierarchy.  Log Levels are applied in the order they
 * are defined in the properties file.  Thus level settings for child
 * nodes in the tree should come after settings for their parents.
 * The property name ".level" can be used to set the level for the
 * root of the tree.
 * <p>
 * All methods on the LogManager object are multi-thread safe.
 *
 * <p>
 *  有一个单一的全局LogManager对象,用于维护一组关于记录器和日志服务的共享状态。
 * <p>
 *  这个LogManager对象：
 * <ul>
 *  <li>管理Logger对象的分层命名空间。所有命名的日志记录都存储在此命名空间中。 <li>管理一组日志记录控制属性。这些是简单的键值对,可以由Handlers和其他日志对象使用来配置自身。
 * </ul>
 * <p>
 *  可以使用LogManager.getLogManager()检索全局LogManager对象。 LogManager对象在类初始化期间创建,随后不能更改。
 * <p>
 *  在启动时,LogManager类使用java.util.logging.manager系统属性进行定位。
 * <p>
 *  LogManager定义了两个可选的系统属性,允许控制初始配置：
 * <ul>
 *  <li>"java.util.logging.config.class"<li>"java.util.logging.config.file"
 * </ul>
 *  这两个属性可以在命令行上指定为"java"命令,或者作为系统属性定义传递给JNI_CreateJavaVM。
 * <p>
 * 如果设置了"java.util.logging.config.class"属性,那么属性值将被视为类名。给定的类将被加载,一个对象将被实例化,并且该对象的构造函数负责读取初始配置。
 *  (该对象可以使用其他系统属性来控制其配置。)备用配置类可以使用<tt> readConfiguration(InputStream)</tt>在LogManager中定义属性。
 * <p>
 *  如果"java.util.logging.config.class"属性设置为<b>不</b>,那么可以使用"java.util.logging.config.file"系统属性来指定属性文件.uti
 * l.Properties格式)。
 * 将从此文件读取初始日志记录配置。
 * <p>
 *  如果这些属性都未定义,则LogManager使用其默认配置。默认配置通常从Java安装目录中的属性文件"{@code lib / logging.properties}"中加载。
 * <p>
 *  记录器和处理程序的属性将以处理程序或记录器的点分隔名称开头。
 * <p>
 *  全局日志记录属性可以包括：
 * <ul>
 *  <li>属性"处理程序"。这定义了处理程序类的空格或逗号分隔的类名称列表,以加载和注册为根记录器(Logger命名为"")上的处理程序。每个类名都必须是具有默认构造函数的Handler类。
 * 请注意,这些处理程序可能会在首次使用时懒散地创建。
 * 
 * <li>属性"&lt; logger&gt; .handlers"。这定义了一个空格或逗号分隔的类名称列表,用于处理程序类加载和注册为指定的记录器的处理程序。
 * 每个类名都必须是具有默认构造函数的Handler类。请注意,这些处理程序可能会在首次使用时懒散地创建。
 * 
 *  <li>属性"&lt; logger&gt; .useParentHandlers"。这定义了一个布尔值。
 * 默认情况下,每个记录器除了处理日志消息本身之外还调用它的父节点,这通常导致消息被根记录器处理。将此属性设置为false时,需要为此记录器配置处理程序,否则不会传递日志记录消息。
 * 
 *  <li>属性"config"。此属性旨在允许运行任意配置代码。该属性定义了一个空格或逗号分隔的类名称列表。将为每个命名类创建一个新实例。
 * 每个类的默认构造函数可以执行任意代码来更新日志记录配置,例如设置记录器级别,添加处理程序,添加过滤器等。
 * </ul>
 * <p>
 *  请注意,在LogManager配置期间加载的所有类首先在任何用户类路径之前的系统类路径上搜索。这包括LogManager类,任何配置类和任何处理程序类。
 * <p>
 *  记录器被组织成基于它们的点分隔名称的命名层次结构。因此,"a.b.c"是"a.b"的子代,但"a.b1"和a.b2"是同位体。
 * <p>
 * 假设名称以".level"结尾的所有属性都定义记录器的日志级别。因此,"foo.level"为称为"foo"的日志记录定义日志级别,并为其命名层次结构中的任何子级定义(递归)。
 * 日志级别按照在属性文件中定义的顺序应用。因此,树中子节点的级别设置应该在其父级的设置之后。属性名称".level"可用于设置树根的级别。
 * <p>
 *  LogManager对象上的所有方法都是多线程安全的。
 * 
 * 
 * @since 1.4
*/

public class LogManager {
    // The global LogManager object
    private static final LogManager manager;

    // 'props' is assigned within a lock but accessed without it.
    // Declaring it volatile makes sure that another thread will not
    // be able to see a partially constructed 'props' object.
    // (seeing a partially constructed 'props' object can result in
    // NPE being thrown in Hashtable.get(), because it leaves the door
    // open for props.getProperties() to be called before the construcor
    // of Hashtable is actually completed).
    private volatile Properties props = new Properties();
    private final static Level defaultLevel = Level.INFO;

    // The map of the registered listeners. The map value is the registration
    // count to allow for cases where the same listener is registered many times.
    private final Map<Object,Integer> listenerMap = new HashMap<>();

    // LoggerContext for system loggers and user loggers
    private final LoggerContext systemContext = new SystemLoggerContext();
    private final LoggerContext userContext = new LoggerContext();
    // non final field - make it volatile to make sure that other threads
    // will see the new value once ensureLogManagerInitialized() has finished
    // executing.
    private volatile Logger rootLogger;
    // Have we done the primordial reading of the configuration file?
    // (Must be done after a suitable amount of java.lang.System
    // initialization has been done)
    private volatile boolean readPrimordialConfiguration;
    // Have we initialized global (root) handlers yet?
    // This gets set to false in readConfiguration
    private boolean initializedGlobalHandlers = true;
    // True if JVM death is imminent and the exit hook has been called.
    private boolean deathImminent;

    static {
        manager = AccessController.doPrivileged(new PrivilegedAction<LogManager>() {
            @Override
            public LogManager run() {
                LogManager mgr = null;
                String cname = null;
                try {
                    cname = System.getProperty("java.util.logging.manager");
                    if (cname != null) {
                        try {
                            Class<?> clz = ClassLoader.getSystemClassLoader()
                                    .loadClass(cname);
                            mgr = (LogManager) clz.newInstance();
                        } catch (ClassNotFoundException ex) {
                            Class<?> clz = Thread.currentThread()
                                    .getContextClassLoader().loadClass(cname);
                            mgr = (LogManager) clz.newInstance();
                        }
                    }
                } catch (Exception ex) {
                    System.err.println("Could not load Logmanager \"" + cname + "\"");
                    ex.printStackTrace();
                }
                if (mgr == null) {
                    mgr = new LogManager();
                }
                return mgr;

            }
        });
    }


    // This private class is used as a shutdown hook.
    // It does a "reset" to close all open handlers.
    private class Cleaner extends Thread {

        private Cleaner() {
            /* Set context class loader to null in order to avoid
             * keeping a strong reference to an application classloader.
             * <p>
             *  保持对应用程序类加载器的强引用。
             * 
             */
            this.setContextClassLoader(null);
        }

        @Override
        public void run() {
            // This is to ensure the LogManager.<clinit> is completed
            // before synchronized block. Otherwise deadlocks are possible.
            LogManager mgr = manager;

            // If the global handlers haven't been initialized yet, we
            // don't want to initialize them just so we can close them!
            synchronized (LogManager.this) {
                // Note that death is imminent.
                deathImminent = true;
                initializedGlobalHandlers = true;
            }

            // Do a reset to close all active handlers.
            reset();
        }
    }


    /**
     * Protected constructor.  This is protected so that container applications
     * (such as J2EE containers) can subclass the object.  It is non-public as
     * it is intended that there only be one LogManager object, whose value is
     * retrieved by calling LogManager.getLogManager.
     * <p>
     *  受保护的构造函数。这是受保护的,以便容器应用程序(如J2EE容器)可以子类化对象。
     * 它是非公开的,因为它只打算有一个LogManager对象,其值通过调用LogManager.getLogManager检索。
     * 
     */
    protected LogManager() {
        this(checkSubclassPermissions());
    }

    private LogManager(Void checked) {

        // Add a shutdown hook to close the global handlers.
        try {
            Runtime.getRuntime().addShutdownHook(new Cleaner());
        } catch (IllegalStateException e) {
            // If the VM is already shutting down,
            // We do not need to register shutdownHook.
        }
    }

    private static Void checkSubclassPermissions() {
        final SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            // These permission will be checked in the LogManager constructor,
            // in order to register the Cleaner() thread as a shutdown hook.
            // Check them here to avoid the penalty of constructing the object
            // etc...
            sm.checkPermission(new RuntimePermission("shutdownHooks"));
            sm.checkPermission(new RuntimePermission("setContextClassLoader"));
        }
        return null;
    }

    /**
     * Lazy initialization: if this instance of manager is the global
     * manager then this method will read the initial configuration and
     * add the root logger and global logger by calling addLogger().
     *
     * Note that it is subtly different from what we do in LoggerContext.
     * In LoggerContext we're patching up the logger context tree in order to add
     * the root and global logger *to the context tree*.
     *
     * For this to work, addLogger() must have already have been called
     * once on the LogManager instance for the default logger being
     * added.
     *
     * This is why ensureLogManagerInitialized() needs to be called before
     * any logger is added to any logger context.
     *
     * <p>
     *  延迟初始化：如果这个管理器实例是全局管理器,那么这个方法将读取初始配置,并通过调用addLogger()添加根记录器和全局记录器。
     * 
     *  注意,它与我们在LoggerContext中做的非常不同。在LoggerContext中,我们修补记录器上下文树以便将根和全局记录器*添加到上下文树*。
     * 
     *  为了这个工作,addLogger()必须已经在LogManager实例上被调用一次,以添加默认的记录器。
     * 
     *  这就是为什么在将任何记录器添加到任何记录器上下文之前,需要调用ensureLogManagerInitialized()。
     * 
     */
    private boolean initializedCalled = false;
    private volatile boolean initializationDone = false;
    final void ensureLogManagerInitialized() {
        final LogManager owner = this;
        if (initializationDone || owner != manager) {
            // we don't want to do this twice, and we don't want to do
            // this on private manager instances.
            return;
        }

        // Maybe another thread has called ensureLogManagerInitialized()
        // before us and is still executing it. If so we will block until
        // the log manager has finished initialized, then acquire the monitor,
        // notice that initializationDone is now true and return.
        // Otherwise - we have come here first! We will acquire the monitor,
        // see that initializationDone is still false, and perform the
        // initialization.
        //
        synchronized(this) {
            // If initializedCalled is true it means that we're already in
            // the process of initializing the LogManager in this thread.
            // There has been a recursive call to ensureLogManagerInitialized().
            final boolean isRecursiveInitialization = (initializedCalled == true);

            assert initializedCalled || !initializationDone
                    : "Initialization can't be done if initialized has not been called!";

            if (isRecursiveInitialization || initializationDone) {
                // If isRecursiveInitialization is true it means that we're
                // already in the process of initializing the LogManager in
                // this thread. There has been a recursive call to
                // ensureLogManagerInitialized(). We should not proceed as
                // it would lead to infinite recursion.
                //
                // If initializationDone is true then it means the manager
                // has finished initializing; just return: we're done.
                return;
            }
            // Calling addLogger below will in turn call requiresDefaultLogger()
            // which will call ensureLogManagerInitialized().
            // We use initializedCalled to break the recursion.
            initializedCalled = true;
            try {
                AccessController.doPrivileged(new PrivilegedAction<Object>() {
                    @Override
                    public Object run() {
                        assert rootLogger == null;
                        assert initializedCalled && !initializationDone;

                        // Read configuration.
                        owner.readPrimordialConfiguration();

                        // Create and retain Logger for the root of the namespace.
                        owner.rootLogger = owner.new RootLogger();
                        owner.addLogger(owner.rootLogger);
                        if (!owner.rootLogger.isLevelInitialized()) {
                            owner.rootLogger.setLevel(defaultLevel);
                        }

                        // Adding the global Logger.
                        // Do not call Logger.getGlobal() here as this might trigger
                        // subtle inter-dependency issues.
                        @SuppressWarnings("deprecation")
                        final Logger global = Logger.global;

                        // Make sure the global logger will be registered in the
                        // global manager
                        owner.addLogger(global);
                        return null;
                    }
                });
            } finally {
                initializationDone = true;
            }
        }
    }

    /**
     * Returns the global LogManager object.
     * <p>
     * 返回全局LogManager对象。
     * 
     * 
     * @return the global LogManager object
     */
    public static LogManager getLogManager() {
        if (manager != null) {
            manager.ensureLogManagerInitialized();
        }
        return manager;
    }

    private void readPrimordialConfiguration() {
        if (!readPrimordialConfiguration) {
            synchronized (this) {
                if (!readPrimordialConfiguration) {
                    // If System.in/out/err are null, it's a good
                    // indication that we're still in the
                    // bootstrapping phase
                    if (System.out == null) {
                        return;
                    }
                    readPrimordialConfiguration = true;

                    try {
                        AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() {
                                @Override
                                public Void run() throws Exception {
                                    readConfiguration();

                                    // Platform loggers begin to delegate to java.util.logging.Logger
                                    sun.util.logging.PlatformLogger.redirectPlatformLoggers();
                                    return null;
                                }
                            });
                    } catch (Exception ex) {
                        assert false : "Exception raised while reading logging configuration: " + ex;
                    }
                }
            }
        }
    }

    /**
     * Adds an event listener to be invoked when the logging
     * properties are re-read. Adding multiple instances of
     * the same event Listener results in multiple entries
     * in the property event listener table.
     *
     * <p><b>WARNING:</b> This method is omitted from this class in all subset
     * Profiles of Java SE that do not include the {@code java.beans} package.
     * </p>
     *
     * <p>
     *  添加在重新读取日志记录属性时要调用的事件侦听器。添加同一事件的多个实例侦听器会在属性事件侦听器表中产生多个条目。
     * 
     *  <p> <b>警告：</b>此方法在Java SE的所有子集配置文件中不包含{@code java.beans}包的此类中省略。
     * </p>
     * 
     * 
     * @param l  event listener
     * @exception  SecurityException  if a security manager exists and if
     *             the caller does not have LoggingPermission("control").
     * @exception NullPointerException if the PropertyChangeListener is null.
     * @deprecated The dependency on {@code PropertyChangeListener} creates a
     *             significant impediment to future modularization of the Java
     *             platform. This method will be removed in a future release.
     *             The global {@code LogManager} can detect changes to the
     *             logging configuration by overridding the {@link
     *             #readConfiguration readConfiguration} method.
     */
    @Deprecated
    public void addPropertyChangeListener(PropertyChangeListener l) throws SecurityException {
        PropertyChangeListener listener = Objects.requireNonNull(l);
        checkPermission();
        synchronized (listenerMap) {
            // increment the registration count if already registered
            Integer value = listenerMap.get(listener);
            value = (value == null) ? 1 : (value + 1);
            listenerMap.put(listener, value);
        }
    }

    /**
     * Removes an event listener for property change events.
     * If the same listener instance has been added to the listener table
     * through multiple invocations of <CODE>addPropertyChangeListener</CODE>,
     * then an equivalent number of
     * <CODE>removePropertyChangeListener</CODE> invocations are required to remove
     * all instances of that listener from the listener table.
     * <P>
     * Returns silently if the given listener is not found.
     *
     * <p><b>WARNING:</b> This method is omitted from this class in all subset
     * Profiles of Java SE that do not include the {@code java.beans} package.
     * </p>
     *
     * <p>
     *  删除属性更改事件的事件侦听器。
     * 如果通过多次调用<CODE> addPropertyChangeListener </CODE>将相同的侦听器实例添加到侦听器实例中,则需要等效数量的<CODE> removePropertyChang
     * eListener </CODE>调用来从该侦听器中删除所有实例监听器表。
     *  删除属性更改事件的事件侦听器。
     * <P>
     *  如果找不到给定的侦听器,则以静默方式返回。
     * 
     *  <p> <b>警告：</b>此方法在Java SE的所有子集配置文件中不包含{@code java.beans}包的此类中省略。
     * </p>
     * 
     * 
     * @param l  event listener (can be null)
     * @exception  SecurityException  if a security manager exists and if
     *             the caller does not have LoggingPermission("control").
     * @deprecated The dependency on {@code PropertyChangeListener} creates a
     *             significant impediment to future modularization of the Java
     *             platform. This method will be removed in a future release.
     *             The global {@code LogManager} can detect changes to the
     *             logging configuration by overridding the {@link
     *             #readConfiguration readConfiguration} method.
     */
    @Deprecated
    public void removePropertyChangeListener(PropertyChangeListener l) throws SecurityException {
        checkPermission();
        if (l != null) {
            PropertyChangeListener listener = l;
            synchronized (listenerMap) {
                Integer value = listenerMap.get(listener);
                if (value != null) {
                    // remove from map if registration count is 1, otherwise
                    // just decrement its count
                    int i = value.intValue();
                    if (i == 1) {
                        listenerMap.remove(listener);
                    } else {
                        assert i > 1;
                        listenerMap.put(listener, i - 1);
                    }
                }
            }
        }
    }

    // LoggerContext maps from AppContext
    private WeakHashMap<Object, LoggerContext> contextsMap = null;

    // Returns the LoggerContext for the user code (i.e. application or AppContext).
    // Loggers are isolated from each AppContext.
    private LoggerContext getUserContext() {
        LoggerContext context = null;

        SecurityManager sm = System.getSecurityManager();
        JavaAWTAccess javaAwtAccess = SharedSecrets.getJavaAWTAccess();
        if (sm != null && javaAwtAccess != null) {
            // for each applet, it has its own LoggerContext isolated from others
            final Object ecx = javaAwtAccess.getAppletContext();
            if (ecx != null) {
                synchronized (javaAwtAccess) {
                    // find the AppContext of the applet code
                    // will be null if we are in the main app context.
                    if (contextsMap == null) {
                        contextsMap = new WeakHashMap<>();
                    }
                    context = contextsMap.get(ecx);
                    if (context == null) {
                        // Create a new LoggerContext for the applet.
                        context = new LoggerContext();
                        contextsMap.put(ecx, context);
                    }
                }
            }
        }
        // for standalone app, return userContext
        return context != null ? context : userContext;
    }

    // The system context.
    final LoggerContext getSystemContext() {
        return systemContext;
    }

    private List<LoggerContext> contexts() {
        List<LoggerContext> cxs = new ArrayList<>();
        cxs.add(getSystemContext());
        cxs.add(getUserContext());
        return cxs;
    }

    // Find or create a specified logger instance. If a logger has
    // already been created with the given name it is returned.
    // Otherwise a new logger instance is created and registered
    // in the LogManager global namespace.
    // This method will always return a non-null Logger object.
    // Synchronization is not required here. All synchronization for
    // adding a new Logger object is handled by addLogger().
    //
    // This method must delegate to the LogManager implementation to
    // add a new Logger or return the one that has been added previously
    // as a LogManager subclass may override the addLogger, getLogger,
    // readConfiguration, and other methods.
    Logger demandLogger(String name, String resourceBundleName, Class<?> caller) {
        Logger result = getLogger(name);
        if (result == null) {
            // only allocate the new logger once
            Logger newLogger = new Logger(name, resourceBundleName, caller, this, false);
            do {
                if (addLogger(newLogger)) {
                    // We successfully added the new Logger that we
                    // created above so return it without refetching.
                    return newLogger;
                }

                // We didn't add the new Logger that we created above
                // because another thread added a Logger with the same
                // name after our null check above and before our call
                // to addLogger(). We have to refetch the Logger because
                // addLogger() returns a boolean instead of the Logger
                // reference itself. However, if the thread that created
                // the other Logger is not holding a strong reference to
                // the other Logger, then it is possible for the other
                // Logger to be GC'ed after we saw it in addLogger() and
                // before we can refetch it. If it has been GC'ed then
                // we'll just loop around and try again.
                result = getLogger(name);
            } while (result == null);
        }
        return result;
    }

    Logger demandSystemLogger(String name, String resourceBundleName) {
        // Add a system logger in the system context's namespace
        final Logger sysLogger = getSystemContext().demandLogger(name, resourceBundleName);

        // Add the system logger to the LogManager's namespace if not exist
        // so that there is only one single logger of the given name.
        // System loggers are visible to applications unless a logger of
        // the same name has been added.
        Logger logger;
        do {
            // First attempt to call addLogger instead of getLogger
            // This would avoid potential bug in custom LogManager.getLogger
            // implementation that adds a logger if does not exist
            if (addLogger(sysLogger)) {
                // successfully added the new system logger
                logger = sysLogger;
            } else {
                logger = getLogger(name);
            }
        } while (logger == null);

        // LogManager will set the sysLogger's handlers via LogManager.addLogger method.
        if (logger != sysLogger && sysLogger.accessCheckedHandlers().length == 0) {
            // if logger already exists but handlers not set
            final Logger l = logger;
            AccessController.doPrivileged(new PrivilegedAction<Void>() {
                @Override
                public Void run() {
                    for (Handler hdl : l.accessCheckedHandlers()) {
                        sysLogger.addHandler(hdl);
                    }
                    return null;
                }
            });
        }
        return sysLogger;
    }

    // LoggerContext maintains the logger namespace per context.
    // The default LogManager implementation has one system context and user
    // context.  The system context is used to maintain the namespace for
    // all system loggers and is queried by the system code.  If a system logger
    // doesn't exist in the user context, it'll also be added to the user context.
    // The user context is queried by the user code and all other loggers are
    // added in the user context.
    class LoggerContext {
        // Table of named Loggers that maps names to Loggers.
        private final Hashtable<String,LoggerWeakRef> namedLoggers = new Hashtable<>();
        // Tree of named Loggers
        private final LogNode root;
        private LoggerContext() {
            this.root = new LogNode(null, this);
        }


        // Tells whether default loggers are required in this context.
        // If true, the default loggers will be lazily added.
        final boolean requiresDefaultLoggers() {
            final boolean requiresDefaultLoggers = (getOwner() == manager);
            if (requiresDefaultLoggers) {
                getOwner().ensureLogManagerInitialized();
            }
            return requiresDefaultLoggers;
        }

        // This context's LogManager.
        final LogManager getOwner() {
            return LogManager.this;
        }

        // This context owner's root logger, which if not null, and if
        // the context requires default loggers, will be added to the context
        // logger's tree.
        final Logger getRootLogger() {
            return getOwner().rootLogger;
        }

        // The global logger, which if not null, and if
        // the context requires default loggers, will be added to the context
        // logger's tree.
        final Logger getGlobalLogger() {
            @SuppressWarnings("deprecated") // avoids initialization cycles.
            final Logger global = Logger.global;
            return global;
        }

        Logger demandLogger(String name, String resourceBundleName) {
            // a LogManager subclass may have its own implementation to add and
            // get a Logger.  So delegate to the LogManager to do the work.
            final LogManager owner = getOwner();
            return owner.demandLogger(name, resourceBundleName, null);
        }


        // Due to subtle deadlock issues getUserContext() no longer
        // calls addLocalLogger(rootLogger);
        // Therefore - we need to add the default loggers later on.
        // Checks that the context is properly initialized
        // This is necessary before calling e.g. find(name)
        // or getLoggerNames()
        //
        private void ensureInitialized() {
            if (requiresDefaultLoggers()) {
                // Ensure that the root and global loggers are set.
                ensureDefaultLogger(getRootLogger());
                ensureDefaultLogger(getGlobalLogger());
            }
        }


        synchronized Logger findLogger(String name) {
            // ensure that this context is properly initialized before
            // looking for loggers.
            ensureInitialized();
            LoggerWeakRef ref = namedLoggers.get(name);
            if (ref == null) {
                return null;
            }
            Logger logger = ref.get();
            if (logger == null) {
                // Hashtable holds stale weak reference
                // to a logger which has been GC-ed.
                ref.dispose();
            }
            return logger;
        }

        // This method is called before adding a logger to the
        // context.
        // 'logger' is the context that will be added.
        // This method will ensure that the defaults loggers are added
        // before adding 'logger'.
        //
        private void ensureAllDefaultLoggers(Logger logger) {
            if (requiresDefaultLoggers()) {
                final String name = logger.getName();
                if (!name.isEmpty()) {
                    ensureDefaultLogger(getRootLogger());
                    if (!Logger.GLOBAL_LOGGER_NAME.equals(name)) {
                        ensureDefaultLogger(getGlobalLogger());
                    }
                }
            }
        }

        private void ensureDefaultLogger(Logger logger) {
            // Used for lazy addition of root logger and global logger
            // to a LoggerContext.

            // This check is simple sanity: we do not want that this
            // method be called for anything else than Logger.global
            // or owner.rootLogger.
            if (!requiresDefaultLoggers() || logger == null
                    || logger != Logger.global && logger != LogManager.this.rootLogger) {

                // the case where we have a non null logger which is neither
                // Logger.global nor manager.rootLogger indicates a serious
                // issue - as ensureDefaultLogger should never be called
                // with any other loggers than one of these two (or null - if
                // e.g manager.rootLogger is not yet initialized)...
                assert logger == null;

                return;
            }

            // Adds the logger if it's not already there.
            if (!namedLoggers.containsKey(logger.getName())) {
                // It is important to prevent addLocalLogger to
                // call ensureAllDefaultLoggers when we're in the process
                // off adding one of those default loggers - as this would
                // immediately cause a stack overflow.
                // Therefore we must pass addDefaultLoggersIfNeeded=false,
                // even if requiresDefaultLoggers is true.
                addLocalLogger(logger, false);
            }
        }

        boolean addLocalLogger(Logger logger) {
            // no need to add default loggers if it's not required
            return addLocalLogger(logger, requiresDefaultLoggers());
        }

        // Add a logger to this context.  This method will only set its level
        // and process parent loggers.  It doesn't set its handlers.
        synchronized boolean addLocalLogger(Logger logger, boolean addDefaultLoggersIfNeeded) {
            // addDefaultLoggersIfNeeded serves to break recursion when adding
            // default loggers. If we're adding one of the default loggers
            // (we're being called from ensureDefaultLogger()) then
            // addDefaultLoggersIfNeeded will be false: we don't want to
            // call ensureAllDefaultLoggers again.
            //
            // Note: addDefaultLoggersIfNeeded can also be false when
            //       requiresDefaultLoggers is false - since calling
            //       ensureAllDefaultLoggers would have no effect in this case.
            if (addDefaultLoggersIfNeeded) {
                ensureAllDefaultLoggers(logger);
            }

            final String name = logger.getName();
            if (name == null) {
                throw new NullPointerException();
            }
            LoggerWeakRef ref = namedLoggers.get(name);
            if (ref != null) {
                if (ref.get() == null) {
                    // It's possible that the Logger was GC'ed after a
                    // drainLoggerRefQueueBounded() call above so allow
                    // a new one to be registered.
                    ref.dispose();
                } else {
                    // We already have a registered logger with the given name.
                    return false;
                }
            }

            // We're adding a new logger.
            // Note that we are creating a weak reference here.
            final LogManager owner = getOwner();
            logger.setLogManager(owner);
            ref = owner.new LoggerWeakRef(logger);
            namedLoggers.put(name, ref);

            // Apply any initial level defined for the new logger, unless
            // the logger's level is already initialized
            Level level = owner.getLevelProperty(name + ".level", null);
            if (level != null && !logger.isLevelInitialized()) {
                doSetLevel(logger, level);
            }

            // instantiation of the handler is done in the LogManager.addLogger
            // implementation as a handler class may be only visible to LogManager
            // subclass for the custom log manager case
            processParentHandlers(logger, name);

            // Find the new node and its parent.
            LogNode node = getNode(name);
            node.loggerRef = ref;
            Logger parent = null;
            LogNode nodep = node.parent;
            while (nodep != null) {
                LoggerWeakRef nodeRef = nodep.loggerRef;
                if (nodeRef != null) {
                    parent = nodeRef.get();
                    if (parent != null) {
                        break;
                    }
                }
                nodep = nodep.parent;
            }

            if (parent != null) {
                doSetParent(logger, parent);
            }
            // Walk over the children and tell them we are their new parent.
            node.walkAndSetParent(logger);
            // new LogNode is ready so tell the LoggerWeakRef about it
            ref.setNode(node);
            return true;
        }

        synchronized void removeLoggerRef(String name, LoggerWeakRef ref) {
            namedLoggers.remove(name, ref);
        }

        synchronized Enumeration<String> getLoggerNames() {
            // ensure that this context is properly initialized before
            // returning logger names.
            ensureInitialized();
            return namedLoggers.keys();
        }

        // If logger.getUseParentHandlers() returns 'true' and any of the logger's
        // parents have levels or handlers defined, make sure they are instantiated.
        private void processParentHandlers(final Logger logger, final String name) {
            final LogManager owner = getOwner();
            AccessController.doPrivileged(new PrivilegedAction<Void>() {
                @Override
                public Void run() {
                    if (logger != owner.rootLogger) {
                        boolean useParent = owner.getBooleanProperty(name + ".useParentHandlers", true);
                        if (!useParent) {
                            logger.setUseParentHandlers(false);
                        }
                    }
                    return null;
                }
            });

            int ix = 1;
            for (;;) {
                int ix2 = name.indexOf(".", ix);
                if (ix2 < 0) {
                    break;
                }
                String pname = name.substring(0, ix2);
                if (owner.getProperty(pname + ".level") != null ||
                    owner.getProperty(pname + ".handlers") != null) {
                    // This pname has a level/handlers definition.
                    // Make sure it exists.
                    demandLogger(pname, null);
                }
                ix = ix2+1;
            }
        }

        // Gets a node in our tree of logger nodes.
        // If necessary, create it.
        LogNode getNode(String name) {
            if (name == null || name.equals("")) {
                return root;
            }
            LogNode node = root;
            while (name.length() > 0) {
                int ix = name.indexOf(".");
                String head;
                if (ix > 0) {
                    head = name.substring(0, ix);
                    name = name.substring(ix + 1);
                } else {
                    head = name;
                    name = "";
                }
                if (node.children == null) {
                    node.children = new HashMap<>();
                }
                LogNode child = node.children.get(head);
                if (child == null) {
                    child = new LogNode(node, this);
                    node.children.put(head, child);
                }
                node = child;
            }
            return node;
        }
    }

    final class SystemLoggerContext extends LoggerContext {
        // Add a system logger in the system context's namespace as well as
        // in the LogManager's namespace if not exist so that there is only
        // one single logger of the given name.  System loggers are visible
        // to applications unless a logger of the same name has been added.
        @Override
        Logger demandLogger(String name, String resourceBundleName) {
            Logger result = findLogger(name);
            if (result == null) {
                // only allocate the new system logger once
                Logger newLogger = new Logger(name, resourceBundleName, null, getOwner(), true);
                do {
                    if (addLocalLogger(newLogger)) {
                        // We successfully added the new Logger that we
                        // created above so return it without refetching.
                        result = newLogger;
                    } else {
                        // We didn't add the new Logger that we created above
                        // because another thread added a Logger with the same
                        // name after our null check above and before our call
                        // to addLogger(). We have to refetch the Logger because
                        // addLogger() returns a boolean instead of the Logger
                        // reference itself. However, if the thread that created
                        // the other Logger is not holding a strong reference to
                        // the other Logger, then it is possible for the other
                        // Logger to be GC'ed after we saw it in addLogger() and
                        // before we can refetch it. If it has been GC'ed then
                        // we'll just loop around and try again.
                        result = findLogger(name);
                    }
                } while (result == null);
            }
            return result;
        }
    }

    // Add new per logger handlers.
    // We need to raise privilege here. All our decisions will
    // be made based on the logging configuration, which can
    // only be modified by trusted code.
    private void loadLoggerHandlers(final Logger logger, final String name,
                                    final String handlersPropertyName)
    {
        AccessController.doPrivileged(new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                String names[] = parseClassNames(handlersPropertyName);
                for (int i = 0; i < names.length; i++) {
                    String word = names[i];
                    try {
                        Class<?> clz = ClassLoader.getSystemClassLoader().loadClass(word);
                        Handler hdl = (Handler) clz.newInstance();
                        // Check if there is a property defining the
                        // this handler's level.
                        String levs = getProperty(word + ".level");
                        if (levs != null) {
                            Level l = Level.findLevel(levs);
                            if (l != null) {
                                hdl.setLevel(l);
                            } else {
                                // Probably a bad level. Drop through.
                                System.err.println("Can't set level for " + word);
                            }
                        }
                        // Add this Handler to the logger
                        logger.addHandler(hdl);
                    } catch (Exception ex) {
                        System.err.println("Can't load log handler \"" + word + "\"");
                        System.err.println("" + ex);
                        ex.printStackTrace();
                    }
                }
                return null;
            }
        });
    }


    // loggerRefQueue holds LoggerWeakRef objects for Logger objects
    // that have been GC'ed.
    private final ReferenceQueue<Logger> loggerRefQueue
        = new ReferenceQueue<>();

    // Package-level inner class.
    // Helper class for managing WeakReferences to Logger objects.
    //
    // LogManager.namedLoggers
    //     - has weak references to all named Loggers
    //     - namedLoggers keeps the LoggerWeakRef objects for the named
    //       Loggers around until we can deal with the book keeping for
    //       the named Logger that is being GC'ed.
    // LogManager.LogNode.loggerRef
    //     - has a weak reference to a named Logger
    //     - the LogNode will also keep the LoggerWeakRef objects for
    //       the named Loggers around; currently LogNodes never go away.
    // Logger.kids
    //     - has a weak reference to each direct child Logger; this
    //       includes anonymous and named Loggers
    //     - anonymous Loggers are always children of the rootLogger
    //       which is a strong reference; rootLogger.kids keeps the
    //       LoggerWeakRef objects for the anonymous Loggers around
    //       until we can deal with the book keeping.
    //
    final class LoggerWeakRef extends WeakReference<Logger> {
        private String                name;       // for namedLoggers cleanup
        private LogNode               node;       // for loggerRef cleanup
        private WeakReference<Logger> parentRef;  // for kids cleanup
        private boolean disposed = false;         // avoid calling dispose twice

        LoggerWeakRef(Logger logger) {
            super(logger, loggerRefQueue);

            name = logger.getName();  // save for namedLoggers cleanup
        }

        // dispose of this LoggerWeakRef object
        void dispose() {
            // Avoid calling dispose twice. When a Logger is gc'ed, its
            // LoggerWeakRef will be enqueued.
            // However, a new logger of the same name may be added (or looked
            // up) before the queue is drained. When that happens, dispose()
            // will be called by addLocalLogger() or findLogger().
            // Later when the queue is drained, dispose() will be called again
            // for the same LoggerWeakRef. Marking LoggerWeakRef as disposed
            // avoids processing the data twice (even though the code should
            // now be reentrant).
            synchronized(this) {
                // Note to maintainers:
                // Be careful not to call any method that tries to acquire
                // another lock from within this block - as this would surely
                // lead to deadlocks, given that dispose() can be called by
                // multiple threads, and from within different synchronized
                // methods/blocks.
                if (disposed) return;
                disposed = true;
            }

            final LogNode n = node;
            if (n != null) {
                // n.loggerRef can only be safely modified from within
                // a lock on LoggerContext. removeLoggerRef is already
                // synchronized on LoggerContext so calling
                // n.context.removeLoggerRef from within this lock is safe.
                synchronized (n.context) {
                    // if we have a LogNode, then we were a named Logger
                    // so clear namedLoggers weak ref to us
                    n.context.removeLoggerRef(name, this);
                    name = null;  // clear our ref to the Logger's name

                    // LogNode may have been reused - so only clear
                    // LogNode.loggerRef if LogNode.loggerRef == this
                    if (n.loggerRef == this) {
                        n.loggerRef = null;  // clear LogNode's weak ref to us
                    }
                    node = null;            // clear our ref to LogNode
                }
            }

            if (parentRef != null) {
                // this LoggerWeakRef has or had a parent Logger
                Logger parent = parentRef.get();
                if (parent != null) {
                    // the parent Logger is still there so clear the
                    // parent Logger's weak ref to us
                    parent.removeChildLogger(this);
                }
                parentRef = null;  // clear our weak ref to the parent Logger
            }
        }

        // set the node field to the specified value
        void setNode(LogNode node) {
            this.node = node;
        }

        // set the parentRef field to the specified value
        void setParentRef(WeakReference<Logger> parentRef) {
            this.parentRef = parentRef;
        }
    }

    // Package-level method.
    // Drain some Logger objects that have been GC'ed.
    //
    // drainLoggerRefQueueBounded() is called by addLogger() below
    // and by Logger.getAnonymousLogger(String) so we'll drain up to
    // MAX_ITERATIONS GC'ed Loggers for every Logger we add.
    //
    // On a WinXP VMware client, a MAX_ITERATIONS value of 400 gives
    // us about a 50/50 mix in increased weak ref counts versus
    // decreased weak ref counts in the AnonLoggerWeakRefLeak test.
    // Here are stats for cleaning up sets of 400 anonymous Loggers:
    //   - test duration 1 minute
    //   - sample size of 125 sets of 400
    //   - average: 1.99 ms
    //   - minimum: 0.57 ms
    //   - maximum: 25.3 ms
    //
    // The same config gives us a better decreased weak ref count
    // than increased weak ref count in the LoggerWeakRefLeak test.
    // Here are stats for cleaning up sets of 400 named Loggers:
    //   - test duration 2 minutes
    //   - sample size of 506 sets of 400
    //   - average: 0.57 ms
    //   - minimum: 0.02 ms
    //   - maximum: 10.9 ms
    //
    private final static int MAX_ITERATIONS = 400;
    final void drainLoggerRefQueueBounded() {
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            if (loggerRefQueue == null) {
                // haven't finished loading LogManager yet
                break;
            }

            LoggerWeakRef ref = (LoggerWeakRef) loggerRefQueue.poll();
            if (ref == null) {
                break;
            }
            // a Logger object has been GC'ed so clean it up
            ref.dispose();
        }
    }

    /**
     * Add a named logger.  This does nothing and returns false if a logger
     * with the same name is already registered.
     * <p>
     * The Logger factory methods call this method to register each
     * newly created Logger.
     * <p>
     * The application should retain its own reference to the Logger
     * object to avoid it being garbage collected.  The LogManager
     * may only retain a weak reference.
     *
     * <p>
     *  添加命名的记录器。如果已注册具有相同名称的记录器,则此操作不执行任何操作,并返回false。
     * <p>
     *  Logger工厂方法调用此方法来注册每个新创建的Logger。
     * <p>
     *  应用程序应保留自己对Logger对象的引用,以避免其被垃圾回收。 LogManager只能保留弱引用。
     * 
     * 
     * @param   logger the new logger.
     * @return  true if the argument logger was registered successfully,
     *          false if a logger of that name already exists.
     * @exception NullPointerException if the logger name is null.
     */
    public boolean addLogger(Logger logger) {
        final String name = logger.getName();
        if (name == null) {
            throw new NullPointerException();
        }
        drainLoggerRefQueueBounded();
        LoggerContext cx = getUserContext();
        if (cx.addLocalLogger(logger)) {
            // Do we have a per logger handler too?
            // Note: this will add a 200ms penalty
            loadLoggerHandlers(logger, name, name + ".handlers");
            return true;
        } else {
            return false;
        }
    }

    // Private method to set a level on a logger.
    // If necessary, we raise privilege before doing the call.
    private static void doSetLevel(final Logger logger, final Level level) {
        SecurityManager sm = System.getSecurityManager();
        if (sm == null) {
            // There is no security manager, so things are easy.
            logger.setLevel(level);
            return;
        }
        // There is a security manager.  Raise privilege before
        // calling setLevel.
        AccessController.doPrivileged(new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                logger.setLevel(level);
                return null;
            }});
    }

    // Private method to set a parent on a logger.
    // If necessary, we raise privilege before doing the setParent call.
    private static void doSetParent(final Logger logger, final Logger parent) {
        SecurityManager sm = System.getSecurityManager();
        if (sm == null) {
            // There is no security manager, so things are easy.
            logger.setParent(parent);
            return;
        }
        // There is a security manager.  Raise privilege before
        // calling setParent.
        AccessController.doPrivileged(new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                logger.setParent(parent);
                return null;
            }});
    }

    /**
     * Method to find a named logger.
     * <p>
     * Note that since untrusted code may create loggers with
     * arbitrary names this method should not be relied on to
     * find Loggers for security sensitive logging.
     * It is also important to note that the Logger associated with the
     * String {@code name} may be garbage collected at any time if there
     * is no strong reference to the Logger. The caller of this method
     * must check the return value for null in order to properly handle
     * the case where the Logger has been garbage collected.
     * <p>
     * <p>
     *  找到命名记录器的方法。
     * <p>
     * 请注意,由于不受信任的代码可能创建具有任意名称的记录器,因此不应依赖此方法来查找用于安全敏感日志记录的记录器。
     * 还需要注意的是,与字符串{@code name}相关联的记录器可以在任何时候被垃圾收集,如果没有对记录器的强引用。该方法的调用者必须检查null的返回值,以便正确处理Logger已被垃圾回收的情况。
     * <p>
     * 
     * @param name name of the logger
     * @return  matching logger or null if none is found
     */
    public Logger getLogger(String name) {
        return getUserContext().findLogger(name);
    }

    /**
     * Get an enumeration of known logger names.
     * <p>
     * Note:  Loggers may be added dynamically as new classes are loaded.
     * This method only reports on the loggers that are currently registered.
     * It is also important to note that this method only returns the name
     * of a Logger, not a strong reference to the Logger itself.
     * The returned String does nothing to prevent the Logger from being
     * garbage collected. In particular, if the returned name is passed
     * to {@code LogManager.getLogger()}, then the caller must check the
     * return value from {@code LogManager.getLogger()} for null to properly
     * handle the case where the Logger has been garbage collected in the
     * time since its name was returned by this method.
     * <p>
     * <p>
     *  获取已知记录器名称的枚举。
     * <p>
     *  注意：记录器可以在加载新类时动态添加。此方法仅报告当前注册的记录器。还需要注意的是,这个方法只返回一个Logger的名字,而不是对Logger本身的强引用。
     * 返回的String不会阻止Logger被垃圾回收。
     * 特别是,如果返回的名称被传递给{@code LogManager.getLogger()},那么调用者必须检查来自{@code LogManager.getLogger()}的返回值为null,以正确处
     * 理Logger已经在自此名称返回之后的时间内收集的垃圾。
     * 返回的String不会阻止Logger被垃圾回收。
     * <p>
     * 
     * @return  enumeration of logger name strings
     */
    public Enumeration<String> getLoggerNames() {
        return getUserContext().getLoggerNames();
    }

    /**
     * Reinitialize the logging properties and reread the logging configuration.
     * <p>
     * The same rules are used for locating the configuration properties
     * as are used at startup.  So normally the logging properties will
     * be re-read from the same file that was used at startup.
     * <P>
     * Any log level definitions in the new configuration file will be
     * applied using Logger.setLevel(), if the target Logger exists.
     * <p>
     * A PropertyChangeEvent will be fired after the properties are read.
     *
     * <p>
     *  重新初始化日志记录属性并重新读取日志记录配置。
     * <p>
     *  相同的规则用于定位启动时使用的配置属性。因此通常,日志属性将从启动时使用的同一个文件中重新读取。
     * <P>
     * 如果目标Logger存在,将使用Logger.setLevel()应用新配置文件中的任何日志级定义。
     * <p>
     *  PropertyChangeEvent将在读取属性后触发。
     * 
     * 
     * @exception  SecurityException  if a security manager exists and if
     *             the caller does not have LoggingPermission("control").
     * @exception  IOException if there are IO problems reading the configuration.
     */
    public void readConfiguration() throws IOException, SecurityException {
        checkPermission();

        // if a configuration class is specified, load it and use it.
        String cname = System.getProperty("java.util.logging.config.class");
        if (cname != null) {
            try {
                // Instantiate the named class.  It is its constructor's
                // responsibility to initialize the logging configuration, by
                // calling readConfiguration(InputStream) with a suitable stream.
                try {
                    Class<?> clz = ClassLoader.getSystemClassLoader().loadClass(cname);
                    clz.newInstance();
                    return;
                } catch (ClassNotFoundException ex) {
                    Class<?> clz = Thread.currentThread().getContextClassLoader().loadClass(cname);
                    clz.newInstance();
                    return;
                }
            } catch (Exception ex) {
                System.err.println("Logging configuration class \"" + cname + "\" failed");
                System.err.println("" + ex);
                // keep going and useful config file.
            }
        }

        String fname = System.getProperty("java.util.logging.config.file");
        if (fname == null) {
            fname = System.getProperty("java.home");
            if (fname == null) {
                throw new Error("Can't find java.home ??");
            }
            File f = new File(fname, "lib");
            f = new File(f, "logging.properties");
            fname = f.getCanonicalPath();
        }
        try (final InputStream in = new FileInputStream(fname)) {
            final BufferedInputStream bin = new BufferedInputStream(in);
            readConfiguration(bin);
        }
    }

    /**
     * Reset the logging configuration.
     * <p>
     * For all named loggers, the reset operation removes and closes
     * all Handlers and (except for the root logger) sets the level
     * to null.  The root logger's level is set to Level.INFO.
     *
     * <p>
     *  重置日志记录配置。
     * <p>
     *  对于所有命名的日志记录器,复位操作将删除并关闭所有处理程序,并且(根记录器除外)将级别设置为null。根记录器的级别设置为Level.INFO。
     * 
     * 
     * @exception  SecurityException  if a security manager exists and if
     *             the caller does not have LoggingPermission("control").
     */

    public void reset() throws SecurityException {
        checkPermission();
        synchronized (this) {
            props = new Properties();
            // Since we are doing a reset we no longer want to initialize
            // the global handlers, if they haven't been initialized yet.
            initializedGlobalHandlers = true;
        }
        for (LoggerContext cx : contexts()) {
            Enumeration<String> enum_ = cx.getLoggerNames();
            while (enum_.hasMoreElements()) {
                String name = enum_.nextElement();
                Logger logger = cx.findLogger(name);
                if (logger != null) {
                    resetLogger(logger);
                }
            }
        }
    }

    // Private method to reset an individual target logger.
    private void resetLogger(Logger logger) {
        // Close all the Logger's handlers.
        Handler[] targets = logger.getHandlers();
        for (int i = 0; i < targets.length; i++) {
            Handler h = targets[i];
            logger.removeHandler(h);
            try {
                h.close();
            } catch (Exception ex) {
                // Problems closing a handler?  Keep going...
            }
        }
        String name = logger.getName();
        if (name != null && name.equals("")) {
            // This is the root logger.
            logger.setLevel(defaultLevel);
        } else {
            logger.setLevel(null);
        }
    }

    // get a list of whitespace separated classnames from a property.
    private String[] parseClassNames(String propertyName) {
        String hands = getProperty(propertyName);
        if (hands == null) {
            return new String[0];
        }
        hands = hands.trim();
        int ix = 0;
        final List<String> result = new ArrayList<>();
        while (ix < hands.length()) {
            int end = ix;
            while (end < hands.length()) {
                if (Character.isWhitespace(hands.charAt(end))) {
                    break;
                }
                if (hands.charAt(end) == ',') {
                    break;
                }
                end++;
            }
            String word = hands.substring(ix, end);
            ix = end+1;
            word = word.trim();
            if (word.length() == 0) {
                continue;
            }
            result.add(word);
        }
        return result.toArray(new String[result.size()]);
    }

    /**
     * Reinitialize the logging properties and reread the logging configuration
     * from the given stream, which should be in java.util.Properties format.
     * A PropertyChangeEvent will be fired after the properties are read.
     * <p>
     * Any log level definitions in the new configuration file will be
     * applied using Logger.setLevel(), if the target Logger exists.
     *
     * <p>
     *  重新初始化日志记录属性并从给定流重新读取日志记录配置,它应该是java.util.Properties格式。 PropertyChangeEvent将在读取属性后触发。
     * <p>
     *  如果目标Logger存在,将使用Logger.setLevel()应用新配置文件中的任何日志级定义。
     * 
     * 
     * @param ins       stream to read properties from
     * @exception  SecurityException  if a security manager exists and if
     *             the caller does not have LoggingPermission("control").
     * @exception  IOException if there are problems reading from the stream.
     */
    public void readConfiguration(InputStream ins) throws IOException, SecurityException {
        checkPermission();
        reset();

        // Load the properties
        props.load(ins);
        // Instantiate new configuration objects.
        String names[] = parseClassNames("config");

        for (int i = 0; i < names.length; i++) {
            String word = names[i];
            try {
                Class<?> clz = ClassLoader.getSystemClassLoader().loadClass(word);
                clz.newInstance();
            } catch (Exception ex) {
                System.err.println("Can't load config class \"" + word + "\"");
                System.err.println("" + ex);
                // ex.printStackTrace();
            }
        }

        // Set levels on any pre-existing loggers, based on the new properties.
        setLevelsOnExistingLoggers();

        // Notify any interested parties that our properties have changed.
        // We first take a copy of the listener map so that we aren't holding any
        // locks when calling the listeners.
        Map<Object,Integer> listeners = null;
        synchronized (listenerMap) {
            if (!listenerMap.isEmpty())
                listeners = new HashMap<>(listenerMap);
        }
        if (listeners != null) {
            assert Beans.isBeansPresent();
            Object ev = Beans.newPropertyChangeEvent(LogManager.class, null, null, null);
            for (Map.Entry<Object,Integer> entry : listeners.entrySet()) {
                Object listener = entry.getKey();
                int count = entry.getValue().intValue();
                for (int i = 0; i < count; i++) {
                    Beans.invokePropertyChange(listener, ev);
                }
            }
        }


        // Note that we need to reinitialize global handles when
        // they are first referenced.
        synchronized (this) {
            initializedGlobalHandlers = false;
        }
    }

    /**
     * Get the value of a logging property.
     * The method returns null if the property is not found.
     * <p>
     *  获取logging属性的值。如果未找到该属性,该方法将返回null。
     * 
     * 
     * @param name      property name
     * @return          property value
     */
    public String getProperty(String name) {
        return props.getProperty(name);
    }

    // Package private method to get a String property.
    // If the property is not defined we return the given
    // default value.
    String getStringProperty(String name, String defaultValue) {
        String val = getProperty(name);
        if (val == null) {
            return defaultValue;
        }
        return val.trim();
    }

    // Package private method to get an integer property.
    // If the property is not defined or cannot be parsed
    // we return the given default value.
    int getIntProperty(String name, int defaultValue) {
        String val = getProperty(name);
        if (val == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(val.trim());
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    // Package private method to get a boolean property.
    // If the property is not defined or cannot be parsed
    // we return the given default value.
    boolean getBooleanProperty(String name, boolean defaultValue) {
        String val = getProperty(name);
        if (val == null) {
            return defaultValue;
        }
        val = val.toLowerCase();
        if (val.equals("true") || val.equals("1")) {
            return true;
        } else if (val.equals("false") || val.equals("0")) {
            return false;
        }
        return defaultValue;
    }

    // Package private method to get a Level property.
    // If the property is not defined or cannot be parsed
    // we return the given default value.
    Level getLevelProperty(String name, Level defaultValue) {
        String val = getProperty(name);
        if (val == null) {
            return defaultValue;
        }
        Level l = Level.findLevel(val.trim());
        return l != null ? l : defaultValue;
    }

    // Package private method to get a filter property.
    // We return an instance of the class named by the "name"
    // property. If the property is not defined or has problems
    // we return the defaultValue.
    Filter getFilterProperty(String name, Filter defaultValue) {
        String val = getProperty(name);
        try {
            if (val != null) {
                Class<?> clz = ClassLoader.getSystemClassLoader().loadClass(val);
                return (Filter) clz.newInstance();
            }
        } catch (Exception ex) {
            // We got one of a variety of exceptions in creating the
            // class or creating an instance.
            // Drop through.
        }
        // We got an exception.  Return the defaultValue.
        return defaultValue;
    }


    // Package private method to get a formatter property.
    // We return an instance of the class named by the "name"
    // property. If the property is not defined or has problems
    // we return the defaultValue.
    Formatter getFormatterProperty(String name, Formatter defaultValue) {
        String val = getProperty(name);
        try {
            if (val != null) {
                Class<?> clz = ClassLoader.getSystemClassLoader().loadClass(val);
                return (Formatter) clz.newInstance();
            }
        } catch (Exception ex) {
            // We got one of a variety of exceptions in creating the
            // class or creating an instance.
            // Drop through.
        }
        // We got an exception.  Return the defaultValue.
        return defaultValue;
    }

    // Private method to load the global handlers.
    // We do the real work lazily, when the global handlers
    // are first used.
    private synchronized void initializeGlobalHandlers() {
        if (initializedGlobalHandlers) {
            return;
        }

        initializedGlobalHandlers = true;

        if (deathImminent) {
            // Aaargh...
            // The VM is shutting down and our exit hook has been called.
            // Avoid allocating global handlers.
            return;
        }
        loadLoggerHandlers(rootLogger, null, "handlers");
    }

    private final Permission controlPermission = new LoggingPermission("control", null);

    void checkPermission() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null)
            sm.checkPermission(controlPermission);
    }

    /**
     * Check that the current context is trusted to modify the logging
     * configuration.  This requires LoggingPermission("control").
     * <p>
     * If the check fails we throw a SecurityException, otherwise
     * we return normally.
     *
     * <p>
     *  检查当前上下文是否受信任以修改日志记录配置。这需要LoggingPermission("control")。
     * <p>
     *  如果检查失败,我们抛出一个SecurityException异常,否则我们正常返回。
     * 
     * 
     * @exception  SecurityException  if a security manager exists and if
     *             the caller does not have LoggingPermission("control").
     */
    public void checkAccess() throws SecurityException {
        checkPermission();
    }

    // Nested class to represent a node in our tree of named loggers.
    private static class LogNode {
        HashMap<String,LogNode> children;
        LoggerWeakRef loggerRef;
        LogNode parent;
        final LoggerContext context;

        LogNode(LogNode parent, LoggerContext context) {
            this.parent = parent;
            this.context = context;
        }

        // Recursive method to walk the tree below a node and set
        // a new parent logger.
        void walkAndSetParent(Logger parent) {
            if (children == null) {
                return;
            }
            Iterator<LogNode> values = children.values().iterator();
            while (values.hasNext()) {
                LogNode node = values.next();
                LoggerWeakRef ref = node.loggerRef;
                Logger logger = (ref == null) ? null : ref.get();
                if (logger == null) {
                    node.walkAndSetParent(parent);
                } else {
                    doSetParent(logger, parent);
                }
            }
        }
    }

    // We use a subclass of Logger for the root logger, so
    // that we only instantiate the global handlers when they
    // are first needed.
    private final class RootLogger extends Logger {
        private RootLogger() {
            // We do not call the protected Logger two args constructor here,
            // to avoid calling LogManager.getLogManager() from within the
            // RootLogger constructor.
            super("", null, null, LogManager.this, true);
        }

        @Override
        public void log(LogRecord record) {
            // Make sure that the global handlers have been instantiated.
            initializeGlobalHandlers();
            super.log(record);
        }

        @Override
        public void addHandler(Handler h) {
            initializeGlobalHandlers();
            super.addHandler(h);
        }

        @Override
        public void removeHandler(Handler h) {
            initializeGlobalHandlers();
            super.removeHandler(h);
        }

        @Override
        Handler[] accessCheckedHandlers() {
            initializeGlobalHandlers();
            return super.accessCheckedHandlers();
        }
    }


    // Private method to be called when the configuration has
    // changed to apply any level settings to any pre-existing loggers.
    synchronized private void setLevelsOnExistingLoggers() {
        Enumeration<?> enum_ = props.propertyNames();
        while (enum_.hasMoreElements()) {
            String key = (String)enum_.nextElement();
            if (!key.endsWith(".level")) {
                // Not a level definition.
                continue;
            }
            int ix = key.length() - 6;
            String name = key.substring(0, ix);
            Level level = getLevelProperty(key, null);
            if (level == null) {
                System.err.println("Bad level value for property: " + key);
                continue;
            }
            for (LoggerContext cx : contexts()) {
                Logger l = cx.findLogger(name);
                if (l == null) {
                    continue;
                }
                l.setLevel(level);
            }
        }
    }

    // Management Support
    private static LoggingMXBean loggingMXBean = null;
    /**
     * String representation of the
     * {@link javax.management.ObjectName} for the management interface
     * for the logging facility.
     *
     * <p>
     *  日志工具管理接口的{@link javax.management.ObjectName}的字符串表示形式。
     * 
     * 
     * @see java.lang.management.PlatformLoggingMXBean
     * @see java.util.logging.LoggingMXBean
     *
     * @since 1.5
     */
    public final static String LOGGING_MXBEAN_NAME
        = "java.util.logging:type=Logging";

    /**
     * Returns <tt>LoggingMXBean</tt> for managing loggers.
     * An alternative way to manage loggers is through the
     * {@link java.lang.management.PlatformLoggingMXBean} interface
     * that can be obtained by calling:
     * <pre>
     *     PlatformLoggingMXBean logging = {@link java.lang.management.ManagementFactory#getPlatformMXBean(Class)
     *         ManagementFactory.getPlatformMXBean}(PlatformLoggingMXBean.class);
     * </pre>
     *
     * <p>
     *  返回<tt> LoggingMXBean </tt>用于管理记录器。
     * 管理记录器的另一种方法是通过{@link java.lang.management.PlatformLoggingMXBean}接口,可以通过调用：。
     * <pre>
     * PlatformLoggingMXBean logging = {@link java.lang.management.ManagementFactory#getPlatformMXBean(Class)ManagementFactory.getPlatformMXBean}
     * (PlatformLoggingMXBean.class);。
     * </pre>
     * 
     * 
     * @return a {@link LoggingMXBean} object.
     *
     * @see java.lang.management.PlatformLoggingMXBean
     * @since 1.5
     */
    public static synchronized LoggingMXBean getLoggingMXBean() {
        if (loggingMXBean == null) {
            loggingMXBean =  new Logging();
        }
        return loggingMXBean;
    }

    /**
     * A class that provides access to the java.beans.PropertyChangeListener
     * and java.beans.PropertyChangeEvent without creating a static dependency
     * on java.beans. This class can be removed once the addPropertyChangeListener
     * and removePropertyChangeListener methods are removed.
     * <p>
     *  一个类,提供对java.beans.PropertyChangeListener和java.beans.PropertyChangeEvent的访问,而不对java.beans创建静态依赖关系。
     * 除去addPropertyChangeListener和removePropertyChangeListener方法后,可以删除此类。
     * 
     */
    private static class Beans {
        private static final Class<?> propertyChangeListenerClass =
            getClass("java.beans.PropertyChangeListener");

        private static final Class<?> propertyChangeEventClass =
            getClass("java.beans.PropertyChangeEvent");

        private static final Method propertyChangeMethod =
            getMethod(propertyChangeListenerClass,
                      "propertyChange",
                      propertyChangeEventClass);

        private static final Constructor<?> propertyEventCtor =
            getConstructor(propertyChangeEventClass,
                           Object.class,
                           String.class,
                           Object.class,
                           Object.class);

        private static Class<?> getClass(String name) {
            try {
                return Class.forName(name, true, Beans.class.getClassLoader());
            } catch (ClassNotFoundException e) {
                return null;
            }
        }
        private static Constructor<?> getConstructor(Class<?> c, Class<?>... types) {
            try {
                return (c == null) ? null : c.getDeclaredConstructor(types);
            } catch (NoSuchMethodException x) {
                throw new AssertionError(x);
            }
        }

        private static Method getMethod(Class<?> c, String name, Class<?>... types) {
            try {
                return (c == null) ? null : c.getMethod(name, types);
            } catch (NoSuchMethodException e) {
                throw new AssertionError(e);
            }
        }

        /**
         * Returns {@code true} if java.beans is present.
         * <p>
         *  如果java.beans存在,返回{@code true}。
         * 
         */
        static boolean isBeansPresent() {
            return propertyChangeListenerClass != null &&
                   propertyChangeEventClass != null;
        }

        /**
         * Returns a new PropertyChangeEvent with the given source, property
         * name, old and new values.
         * <p>
         *  返回一个新的PropertyChangeEvent与给定的源,属性名称,旧的和新的值。
         * 
         */
        static Object newPropertyChangeEvent(Object source, String prop,
                                             Object oldValue, Object newValue)
        {
            try {
                return propertyEventCtor.newInstance(source, prop, oldValue, newValue);
            } catch (InstantiationException | IllegalAccessException x) {
                throw new AssertionError(x);
            } catch (InvocationTargetException x) {
                Throwable cause = x.getCause();
                if (cause instanceof Error)
                    throw (Error)cause;
                if (cause instanceof RuntimeException)
                    throw (RuntimeException)cause;
                throw new AssertionError(x);
            }
        }

        /**
         * Invokes the given PropertyChangeListener's propertyChange method
         * with the given event.
         * <p>
         *  使用给定的事件调用给定的PropertyChangeListener的propertyChange方法。
         */
        static void invokePropertyChange(Object listener, Object ev) {
            try {
                propertyChangeMethod.invoke(listener, ev);
            } catch (IllegalAccessException x) {
                throw new AssertionError(x);
            } catch (InvocationTargetException x) {
                Throwable cause = x.getCause();
                if (cause instanceof Error)
                    throw (Error)cause;
                if (cause instanceof RuntimeException)
                    throw (RuntimeException)cause;
                throw new AssertionError(x);
            }
        }
    }
}
