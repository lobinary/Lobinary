/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2014, Oracle and/or its affiliates. All rights reserved.
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

import java.lang.ref.WeakReference;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;
import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

/**
 * A Logger object is used to log messages for a specific
 * system or application component.  Loggers are normally named,
 * using a hierarchical dot-separated namespace.  Logger names
 * can be arbitrary strings, but they should normally be based on
 * the package name or class name of the logged component, such
 * as java.net or javax.swing.  In addition it is possible to create
 * "anonymous" Loggers that are not stored in the Logger namespace.
 * <p>
 * Logger objects may be obtained by calls on one of the getLogger
 * factory methods.  These will either create a new Logger or
 * return a suitable existing Logger. It is important to note that
 * the Logger returned by one of the {@code getLogger} factory methods
 * may be garbage collected at any time if a strong reference to the
 * Logger is not kept.
 * <p>
 * Logging messages will be forwarded to registered Handler
 * objects, which can forward the messages to a variety of
 * destinations, including consoles, files, OS logs, etc.
 * <p>
 * Each Logger keeps track of a "parent" Logger, which is its
 * nearest existing ancestor in the Logger namespace.
 * <p>
 * Each Logger has a "Level" associated with it.  This reflects
 * a minimum Level that this logger cares about.  If a Logger's
 * level is set to <tt>null</tt>, then its effective level is inherited
 * from its parent, which may in turn obtain it recursively from its
 * parent, and so on up the tree.
 * <p>
 * The log level can be configured based on the properties from the
 * logging configuration file, as described in the description
 * of the LogManager class.  However it may also be dynamically changed
 * by calls on the Logger.setLevel method.  If a logger's level is
 * changed the change may also affect child loggers, since any child
 * logger that has <tt>null</tt> as its level will inherit its
 * effective level from its parent.
 * <p>
 * On each logging call the Logger initially performs a cheap
 * check of the request level (e.g., SEVERE or FINE) against the
 * effective log level of the logger.  If the request level is
 * lower than the log level, the logging call returns immediately.
 * <p>
 * After passing this initial (cheap) test, the Logger will allocate
 * a LogRecord to describe the logging message.  It will then call a
 * Filter (if present) to do a more detailed check on whether the
 * record should be published.  If that passes it will then publish
 * the LogRecord to its output Handlers.  By default, loggers also
 * publish to their parent's Handlers, recursively up the tree.
 * <p>
 * Each Logger may have a {@code ResourceBundle} associated with it.
 * The {@code ResourceBundle} may be specified by name, using the
 * {@link #getLogger(java.lang.String, java.lang.String)} factory
 * method, or by value - using the {@link
 * #setResourceBundle(java.util.ResourceBundle) setResourceBundle} method.
 * This bundle will be used for localizing logging messages.
 * If a Logger does not have its own {@code ResourceBundle} or resource bundle
 * name, then it will inherit the {@code ResourceBundle} or resource bundle name
 * from its parent, recursively up the tree.
 * <p>
 * Most of the logger output methods take a "msg" argument.  This
 * msg argument may be either a raw value or a localization key.
 * During formatting, if the logger has (or inherits) a localization
 * {@code ResourceBundle} and if the {@code ResourceBundle} has a mapping for
 * the msg string, then the msg string is replaced by the localized value.
 * Otherwise the original msg string is used.  Typically, formatters use
 * java.text.MessageFormat style formatting to format parameters, so
 * for example a format string "{0} {1}" would format two parameters
 * as strings.
 * <p>
 * A set of methods alternatively take a "msgSupplier" instead of a "msg"
 * argument.  These methods take a {@link Supplier}{@code <String>} function
 * which is invoked to construct the desired log message only when the message
 * actually is to be logged based on the effective log level thus eliminating
 * unnecessary message construction. For example, if the developer wants to
 * log system health status for diagnosis, with the String-accepting version,
 * the code would look like:
 <pre><code>

   class DiagnosisMessages {
     static String systemHealthStatus() {
       // collect system health information
       ...
     }
   }
   ...
   logger.log(Level.FINER, DiagnosisMessages.systemHealthStatus());
</code></pre>
 * With the above code, the health status is collected unnecessarily even when
 * the log level FINER is disabled. With the Supplier-accepting version as
 * below, the status will only be collected when the log level FINER is
 * enabled.
 <pre><code>

   logger.log(Level.FINER, DiagnosisMessages::systemHealthStatus);
</code></pre>
 * <p>
 * When looking for a {@code ResourceBundle}, the logger will first look at
 * whether a bundle was specified using {@link
 * #setResourceBundle(java.util.ResourceBundle) setResourceBundle}, and then
 * only whether a resource bundle name was specified through the {@link
 * #getLogger(java.lang.String, java.lang.String) getLogger} factory method.
 * If no {@code ResourceBundle} or no resource bundle name is found,
 * then it will use the nearest {@code ResourceBundle} or resource bundle
 * name inherited from its parent tree.<br>
 * When a {@code ResourceBundle} was inherited or specified through the
 * {@link
 * #setResourceBundle(java.util.ResourceBundle) setResourceBundle} method, then
 * that {@code ResourceBundle} will be used. Otherwise if the logger only
 * has or inherited a resource bundle name, then that resource bundle name
 * will be mapped to a {@code ResourceBundle} object, using the default Locale
 * at the time of logging.
 * <br id="ResourceBundleMapping">When mapping resource bundle names to
 * {@code ResourceBundle} objects, the logger will first try to use the
 * Thread's {@linkplain java.lang.Thread#getContextClassLoader() context class
 * loader} to map the given resource bundle name to a {@code ResourceBundle}.
 * If the thread context class loader is {@code null}, it will try the
 * {@linkplain java.lang.ClassLoader#getSystemClassLoader() system class loader}
 * instead.  If the {@code ResourceBundle} is still not found, it will use the
 * class loader of the first caller of the {@link
 * #getLogger(java.lang.String, java.lang.String) getLogger} factory method.
 * <p>
 * Formatting (including localization) is the responsibility of
 * the output Handler, which will typically call a Formatter.
 * <p>
 * Note that formatting need not occur synchronously.  It may be delayed
 * until a LogRecord is actually written to an external sink.
 * <p>
 * The logging methods are grouped in five main categories:
 * <ul>
 * <li><p>
 *     There are a set of "log" methods that take a log level, a message
 *     string, and optionally some parameters to the message string.
 * <li><p>
 *     There are a set of "logp" methods (for "log precise") that are
 *     like the "log" methods, but also take an explicit source class name
 *     and method name.
 * <li><p>
 *     There are a set of "logrb" method (for "log with resource bundle")
 *     that are like the "logp" method, but also take an explicit resource
 *     bundle object for use in localizing the log message.
 * <li><p>
 *     There are convenience methods for tracing method entries (the
 *     "entering" methods), method returns (the "exiting" methods) and
 *     throwing exceptions (the "throwing" methods).
 * <li><p>
 *     Finally, there are a set of convenience methods for use in the
 *     very simplest cases, when a developer simply wants to log a
 *     simple string at a given log level.  These methods are named
 *     after the standard Level names ("severe", "warning", "info", etc.)
 *     and take a single argument, a message string.
 * </ul>
 * <p>
 * For the methods that do not take an explicit source name and
 * method name, the Logging framework will make a "best effort"
 * to determine which class and method called into the logging method.
 * However, it is important to realize that this automatically inferred
 * information may only be approximate (or may even be quite wrong!).
 * Virtual machines are allowed to do extensive optimizations when
 * JITing and may entirely remove stack frames, making it impossible
 * to reliably locate the calling class and method.
 * <P>
 * All methods on Logger are multi-thread safe.
 * <p>
 * <b>Subclassing Information:</b> Note that a LogManager class may
 * provide its own implementation of named Loggers for any point in
 * the namespace.  Therefore, any subclasses of Logger (unless they
 * are implemented in conjunction with a new LogManager class) should
 * take care to obtain a Logger instance from the LogManager class and
 * should delegate operations such as "isLoggable" and "log(LogRecord)"
 * to that instance.  Note that in order to intercept all logging
 * output, subclasses need only override the log(LogRecord) method.
 * All the other logging methods are implemented as calls on this
 * log(LogRecord) method.
 *
 * <p>
 *  Logger对象用于记录特定系统或应用程序组件的消息。记录器通常使用分层的点分隔命名空间命名。
 * 记录器名称可以是任意字符串,但它们通常应基于记录的组件的包名称或类名称,例如java.net或javax.swing。此外,可以创建不存储在Logger命名空间中的"匿名"Logger。
 * <p>
 *  记录器对象可以通过对getLogger工厂方法之一的调用来获得。这些将创建一个新的Logger或返回一个合适的现有Logger。
 * 重要的是注意,如果没有保存对Logger的强引用,那么由{@code getLogger}工厂方法之一返回的Logger可能在任何时间被垃圾收集。
 * <p>
 *  日志消息将转发到已注册的Handler对象,该对象可将消息转发到各种目标,包括控制台,文件,操作系统日志等。
 * <p>
 *  每个记录器跟踪"父"记录器,它是Logger命名空间中最接近的现有祖先。
 * <p>
 *  每个记录器具有与其相关联的"级别"。这反映了这个记录器关心的最低水平。如果记录器的级别设置为<tt> null </tt>,那么它的有效级别从其父级继承,这反过来可以从其父级递归地获取它,等等。
 * <p>
 * 日志级别可以基于日志配置文件的属性进行配置,如LogManager类的描述中所述。但是,它也可以通过Logger.setLevel方法的调用动态更改。
 * 如果记录器级别更改,则更改也可能会影响子记录器,因为任何具有<tt> null </tt>作为其级别的子记录器将从其父级继承其有效级别。
 * <p>
 *  在每个记录调用中,记录器首先针对记录器的有效日志级别执行请求级别的便宜检查(例如,SEVERE或FINE)。如果请求级别低于日志级别,则日志调用立即返回。
 * <p>
 *  在通过这个初始(便宜)测试之后,Logger将分配一个LogRecord来描述日志消息。然后它将调用过滤器(如果存在),以便对是否应该发布该记录进行更详细的检查。
 * 如果它通过,然后将LogRecord发布到其输出处理程序。默认情况下,日志记录器还向树上递归地发布到其父级的处理程序。
 * <p>
 * 每个记录器可能有一个{@code ResourceBundle}与它相关联。
 *  {@code ResourceBundle}可以通过名称指定,使用{@link #getLogger(java.lang.String,java.lang.String)}工厂方法,或者通过使用{@link #setResourceBundle(java。
 * 每个记录器可能有一个{@code ResourceBundle}与它相关联。 util.ResourceBundle)setResourceBundle}方法。此捆绑包将用于本地化日志记录消息。
 * 如果Logger没有自己的{@code ResourceBundle}或资源包名称,那么它将从父对象继承树的{@code ResourceBundle}或资源包名称。
 * <p>
 *  大多数记录器输出方法采用"msg"参数。这个msg参数可以是原始值或本地化键。
 * 在格式化期间,如果记录器具有(或继承)本地化{@code ResourceBundle},并且如果{@code ResourceBundle}具有msg字符串的映射,则msg字符串被本地化值替代。
 * 否则使用原始的msg字符串。通常,格式化程序使用java.text.MessageFormat样式格式化参数,因此例如格式化字符串"{0} {1}"会将两个参数格式化为字符串。
 * <p>
 * 一组方法交替地采用"msgSupplier"而不是"msg"参数。
 * 这些方法采用{@link Supplier} {@ code <String>}函数,只有当根据有效日志级别记录消息时,才调用该函数来构造所需的日志消息,从而消除不必要的消息构造。
 * 例如,如果开发人员想要记录系统健康状态以进行诊断,使用字符串接受版本,代码将如下所示：<pre> <code>。
 * 
 *  class DiagnosisMessages {static String systemHealthStatus(){//收集系统健康信息...}} ... logger.log(Level.FIN
 * ER,DiagnosisMessages.systemHealthStatus()); </code> </pre>使用上面的代码,健康状态被不必要地收集,即使日志级别FINER被禁用。
 * 使用以下供应商接受版本,只有在启用日志级别FINER时才会收集状态。 <pre> <code>。
 * 
 *  logger.log(Level.FINER,DiagnosisMessages :: systemHealthStatus); </code> </pre>
 * <p>
 * 当查找{@code ResourceBundle}时,记录器将首先查看是否使用{@link #setResourceBundle(java.util.ResourceBundle)setResourceBundle}
 * 指定了包,然后仅查看是否通过{ @link #getLogger(java.lang.String,java.lang.String)getLogger}工厂方法。
 * 如果没有找到{@code ResourceBundle}或没有找到资源包名称,那么它将使用从其父树继承的最近的{@code ResourceBundle}或资源包名称。
 * <br>当{@code ResourceBundle}被继承或指定时通过{@link #setResourceBundle(java.util.ResourceBundle)setResourceBundle}
 * 方法,那么将使用{@code ResourceBundle}。
 * 如果没有找到{@code ResourceBundle}或没有找到资源包名称,那么它将使用从其父树继承的最近的{@code ResourceBundle}或资源包名称。
 * 否则,如果记录器仅具有或继承了资源束名称,那么该资源束名称将被映射到{@code ResourceBundle}对象,在记录时使用默认的区域设置。
 *  <br id="ResourceBundleMapping">当将资源包名称映射到{@code ResourceBundle}对象时,记录器将首先尝试使用线程的{@linkplain java.lang.Thread#getContextClassLoader()上下文类加载器}
 * 来映射给定的资源包名称到{@code ResourceBundle}。
 * 否则,如果记录器仅具有或继承了资源束名称,那么该资源束名称将被映射到{@code ResourceBundle}对象,在记录时使用默认的区域设置。
 * 如果线程上下文类加载器是{@code null},它将尝试使用{@linkplain java.lang.ClassLoader#getSystemClassLoader()系统类加载器}。
 * 如果仍然找不到{@code ResourceBundle},它将使用{@link #getLogger(java.lang.String,java.lang.String)getLogger}工厂方法的
 * 第一个调用者的类加载器。
 * 如果线程上下文类加载器是{@code null},它将尝试使用{@linkplain java.lang.ClassLoader#getSystemClassLoader()系统类加载器}。
 * <p>
 * 格式化(包括本地化)是输出处理程序的责任,它通常调用一个格式化程序。
 * <p>
 *  请注意,格式化不需要同步进行。它可能会被延迟,直到LogRecord实际写入外部接收器。
 * <p>
 *  记录方法分为五大类：
 * <ul>
 *  <li> <p>有一组"日志"方法,它们将日志级别,消息字符串和可选的消息字符串的一些参数。
 *  <li> <p>有一组"logp"方法(对于"日志精确"),类似于"log"方法,但也需要一个明确的源类名称和方法名称。
 *  <li> <p>有一组"logrb"方法(对于"使用资源包的日志"),类似于"logp"方法,但也使用一个显式资源包对象用于本地化日志消息。
 *  <li> <p>有一些方便的方法用于跟踪方法条目("输入"方法),方法返回("exiting"方法)和抛出异常("throwing"方法)。
 *  <li> <p>最后,当开发人员只想在给定日志级别记录一个简单的字符串时,在最简单的情况下使用一组方便的方法。
 * 这些方法以标准级别名称("severe","warning","info"等)命名,并采用单个参数,即消息字符串。
 * </ul>
 * <p>
 * 对于没有显式的源名称和方法名的方法,Logging框架将做出"尽力而为"来确定在日志记录方法中调用哪个类和方法。然而,重要的是意识到,这种自动推断的信息可能仅仅是近似的(或者甚至可能是相当错误的！)。
 * 虚拟机允许在JITing时进行广泛的优化,并且可能完全删除堆栈帧,使得不可能可靠地定位调用类和方法。
 * <P>
 *  Logger上的所有方法都是多线程安全的。
 * <p>
 *  <b>子类信息：</b>请注意,LogManager类可以为命名空间中的任何点提供其自己的命名Loggers实现。
 * 因此,Logger的任何子类(除非它们与新的LogManager类一起实现)应注意从LogManager类获取Logger实例,并应将诸如"isLoggable"和"log(LogRecord)"之类的
 * 操作委托给该实例。
 *  <b>子类信息：</b>请注意,LogManager类可以为命名空间中的任何点提供其自己的命名Loggers实现。注意,为了拦截所有日志输出,子类只需要覆盖日志(LogRecord)方法。
 * 所有其他日志记录方法都实现为对此日志(LogRecord)方法的调用。
 * 
 * 
 * @since 1.4
 */
public class Logger {
    private static final Handler emptyHandlers[] = new Handler[0];
    private static final int offValue = Level.OFF.intValue();

    static final String SYSTEM_LOGGER_RB_NAME = "sun.util.logging.resources.logging";

    // This class is immutable and it is important that it remains so.
    private static final class LoggerBundle {
        final String resourceBundleName; // Base name of the bundle.
        final ResourceBundle userBundle; // Bundle set through setResourceBundle.
        private LoggerBundle(String resourceBundleName, ResourceBundle bundle) {
            this.resourceBundleName = resourceBundleName;
            this.userBundle = bundle;
        }
        boolean isSystemBundle() {
            return SYSTEM_LOGGER_RB_NAME.equals(resourceBundleName);
        }
        static LoggerBundle get(String name, ResourceBundle bundle) {
            if (name == null && bundle == null) {
                return NO_RESOURCE_BUNDLE;
            } else if (SYSTEM_LOGGER_RB_NAME.equals(name) && bundle == null) {
                return SYSTEM_BUNDLE;
            } else {
                return new LoggerBundle(name, bundle);
            }
        }
    }

    // This instance will be shared by all loggers created by the system
    // code
    private static final LoggerBundle SYSTEM_BUNDLE =
            new LoggerBundle(SYSTEM_LOGGER_RB_NAME, null);

    // This instance indicates that no resource bundle has been specified yet,
    // and it will be shared by all loggers which have no resource bundle.
    private static final LoggerBundle NO_RESOURCE_BUNDLE =
            new LoggerBundle(null, null);

    private volatile LogManager manager;
    private String name;
    private final CopyOnWriteArrayList<Handler> handlers =
        new CopyOnWriteArrayList<>();
    private volatile LoggerBundle loggerBundle = NO_RESOURCE_BUNDLE;
    private volatile boolean useParentHandlers = true;
    private volatile Filter filter;
    private boolean anonymous;

    // Cache to speed up behavior of findResourceBundle:
    private ResourceBundle catalog;     // Cached resource bundle
    private String catalogName;         // name associated with catalog
    private Locale catalogLocale;       // locale associated with catalog

    // The fields relating to parent-child relationships and levels
    // are managed under a separate lock, the treeLock.
    private static final Object treeLock = new Object();
    // We keep weak references from parents to children, but strong
    // references from children to parents.
    private volatile Logger parent;    // our nearest parent.
    private ArrayList<LogManager.LoggerWeakRef> kids;   // WeakReferences to loggers that have us as parent
    private volatile Level levelObject;
    private volatile int levelValue;  // current effective level value
    private WeakReference<ClassLoader> callersClassLoaderRef;
    private final boolean isSystemLogger;

    /**
     * GLOBAL_LOGGER_NAME is a name for the global logger.
     *
     * <p>
     *  GLOBAL_LOGGER_NAME是全局记录器的名称。
     * 
     * 
     * @since 1.6
     */
    public static final String GLOBAL_LOGGER_NAME = "global";

    /**
     * Return global logger object with the name Logger.GLOBAL_LOGGER_NAME.
     *
     * <p>
     *  使用名称Logger.GLOBAL_LOGGER_NAME返回全局日志记录器对象。
     * 
     * 
     * @return global logger object
     * @since 1.7
     */
    public static final Logger getGlobal() {
        // In order to break a cyclic dependence between the LogManager
        // and Logger static initializers causing deadlocks, the global
        // logger is created with a special constructor that does not
        // initialize its log manager.
        //
        // If an application calls Logger.getGlobal() before any logger
        // has been initialized, it is therefore possible that the
        // LogManager class has not been initialized yet, and therefore
        // Logger.global.manager will be null.
        //
        // In order to finish the initialization of the global logger, we
        // will therefore call LogManager.getLogManager() here.
        //
        // To prevent race conditions we also need to call
        // LogManager.getLogManager() unconditionally here.
        // Indeed we cannot rely on the observed value of global.manager,
        // because global.manager will become not null somewhere during
        // the initialization of LogManager.
        // If two threads are calling getGlobal() concurrently, one thread
        // will see global.manager null and call LogManager.getLogManager(),
        // but the other thread could come in at a time when global.manager
        // is already set although ensureLogManagerInitialized is not finished
        // yet...
        // Calling LogManager.getLogManager() unconditionally will fix that.

        LogManager.getLogManager();

        // Now the global LogManager should be initialized,
        // and the global logger should have been added to
        // it, unless we were called within the constructor of a LogManager
        // subclass installed as LogManager, in which case global.manager
        // would still be null, and global will be lazily initialized later on.

        return global;
    }

    /**
     * The "global" Logger object is provided as a convenience to developers
     * who are making casual use of the Logging package.  Developers
     * who are making serious use of the logging package (for example
     * in products) should create and use their own Logger objects,
     * with appropriate names, so that logging can be controlled on a
     * suitable per-Logger granularity. Developers also need to keep a
     * strong reference to their Logger objects to prevent them from
     * being garbage collected.
     * <p>
     * <p>
     * 提供"全局"Logger对象是为了方便开发人员随意使用Logging包。
     * 严重使用日志记录包(例如在产品中)的开发人员应该使用适当的名称创建和使用自己的Logger对象,以便可以在合适的每个Logger粒度上控制日志记录。
     * 开发人员还需要保持对其Logger对象的强引用,以防止它们被垃圾回收。
     * <p>
     * 
     * @deprecated Initialization of this field is prone to deadlocks.
     * The field must be initialized by the Logger class initialization
     * which may cause deadlocks with the LogManager class initialization.
     * In such cases two class initialization wait for each other to complete.
     * The preferred way to get the global logger object is via the call
     * <code>Logger.getGlobal()</code>.
     * For compatibility with old JDK versions where the
     * <code>Logger.getGlobal()</code> is not available use the call
     * <code>Logger.getLogger(Logger.GLOBAL_LOGGER_NAME)</code>
     * or <code>Logger.getLogger("global")</code>.
     */
    @Deprecated
    public static final Logger global = new Logger(GLOBAL_LOGGER_NAME);

    /**
     * Protected method to construct a logger for a named subsystem.
     * <p>
     * The logger will be initially configured with a null Level
     * and with useParentHandlers set to true.
     *
     * <p>
     *  用于为命名的子系统构造记录器的受保护方法。
     * <p>
     *  记录器最初将配置为空级别,并将useParentHandlers设置为true。
     * 
     * 
     * @param   name    A name for the logger.  This should
     *                          be a dot-separated name and should normally
     *                          be based on the package name or class name
     *                          of the subsystem, such as java.net
     *                          or javax.swing.  It may be null for anonymous Loggers.
     * @param   resourceBundleName  name of ResourceBundle to be used for localizing
     *                          messages for this logger.  May be null if none
     *                          of the messages require localization.
     * @throws MissingResourceException if the resourceBundleName is non-null and
     *             no corresponding resource can be found.
     */
    protected Logger(String name, String resourceBundleName) {
        this(name, resourceBundleName, null, LogManager.getLogManager(), false);
    }

    Logger(String name, String resourceBundleName, Class<?> caller, LogManager manager, boolean isSystemLogger) {
        this.manager = manager;
        this.isSystemLogger = isSystemLogger;
        setupResourceInfo(resourceBundleName, caller);
        this.name = name;
        levelValue = Level.INFO.intValue();
    }

    private void setCallersClassLoaderRef(Class<?> caller) {
        ClassLoader callersClassLoader = ((caller != null)
                                         ? caller.getClassLoader()
                                         : null);
        if (callersClassLoader != null) {
            this.callersClassLoaderRef = new WeakReference<>(callersClassLoader);
        }
    }

    private ClassLoader getCallersClassLoader() {
        return (callersClassLoaderRef != null)
                ? callersClassLoaderRef.get()
                : null;
    }

    // This constructor is used only to create the global Logger.
    // It is needed to break a cyclic dependence between the LogManager
    // and Logger static initializers causing deadlocks.
    private Logger(String name) {
        // The manager field is not initialized here.
        this.name = name;
        this.isSystemLogger = true;
        levelValue = Level.INFO.intValue();
    }

    // It is called from LoggerContext.addLocalLogger() when the logger
    // is actually added to a LogManager.
    void setLogManager(LogManager manager) {
        this.manager = manager;
    }

    private void checkPermission() throws SecurityException {
        if (!anonymous) {
            if (manager == null) {
                // Complete initialization of the global Logger.
                manager = LogManager.getLogManager();
            }
            manager.checkPermission();
        }
    }

    // Until all JDK code converted to call sun.util.logging.PlatformLogger
    // (see 7054233), we need to determine if Logger.getLogger is to add
    // a system logger or user logger.
    //
    // As an interim solution, if the immediate caller whose caller loader is
    // null, we assume it's a system logger and add it to the system context.
    // These system loggers only set the resource bundle to the given
    // resource bundle name (rather than the default system resource bundle).
    private static class SystemLoggerHelper {
        static boolean disableCallerCheck = getBooleanProperty("sun.util.logging.disableCallerCheck");
        private static boolean getBooleanProperty(final String key) {
            String s = AccessController.doPrivileged(new PrivilegedAction<String>() {
                @Override
                public String run() {
                    return System.getProperty(key);
                }
            });
            return Boolean.valueOf(s);
        }
    }

    private static Logger demandLogger(String name, String resourceBundleName, Class<?> caller) {
        LogManager manager = LogManager.getLogManager();
        SecurityManager sm = System.getSecurityManager();
        if (sm != null && !SystemLoggerHelper.disableCallerCheck) {
            if (caller.getClassLoader() == null) {
                return manager.demandSystemLogger(name, resourceBundleName);
            }
        }
        return manager.demandLogger(name, resourceBundleName, caller);
        // ends up calling new Logger(name, resourceBundleName, caller)
        // iff the logger doesn't exist already
    }

    /**
     * Find or create a logger for a named subsystem.  If a logger has
     * already been created with the given name it is returned.  Otherwise
     * a new logger is created.
     * <p>
     * If a new logger is created its log level will be configured
     * based on the LogManager configuration and it will configured
     * to also send logging output to its parent's Handlers.  It will
     * be registered in the LogManager global namespace.
     * <p>
     * Note: The LogManager may only retain a weak reference to the newly
     * created Logger. It is important to understand that a previously
     * created Logger with the given name may be garbage collected at any
     * time if there is no strong reference to the Logger. In particular,
     * this means that two back-to-back calls like
     * {@code getLogger("MyLogger").log(...)} may use different Logger
     * objects named "MyLogger" if there is no strong reference to the
     * Logger named "MyLogger" elsewhere in the program.
     *
     * <p>
     *  为命名的子系统查找或创建记录器。如果已经创建了具有给定名称的记录器,则返回该记录器。否则,将创建一个新的记录器。
     * <p>
     *  如果创建了一个新的日志记录器,它的日志级别将基于LogManager配置进行配置,并且它将配置为还将日志输出发送到其父级的处理程序。它将在LogManager全局命名空间中注册。
     * <p>
     * 注意：LogManager只能保留对新创建的Logger的弱引用。重要的是要知道,如果没有对记录器的强引用,那么以前创建的具有给定名称的记录器可以在任何时间被垃圾收集。
     * 特别是,这意味着如果没有对名为"Logger"的Logger对象的强引用,那么像{@code getLogger("MyLogger")。
     * log(...)}这样的两个背对背调用可以使用不同的Logger对象, "MyLogger"在程序的其他地方。
     * 
     * 
     * @param   name            A name for the logger.  This should
     *                          be a dot-separated name and should normally
     *                          be based on the package name or class name
     *                          of the subsystem, such as java.net
     *                          or javax.swing
     * @return a suitable Logger
     * @throws NullPointerException if the name is null.
     */

    // Synchronization is not required here. All synchronization for
    // adding a new Logger object is handled by LogManager.addLogger().
    @CallerSensitive
    public static Logger getLogger(String name) {
        // This method is intentionally not a wrapper around a call
        // to getLogger(name, resourceBundleName). If it were then
        // this sequence:
        //
        //     getLogger("Foo", "resourceBundleForFoo");
        //     getLogger("Foo");
        //
        // would throw an IllegalArgumentException in the second call
        // because the wrapper would result in an attempt to replace
        // the existing "resourceBundleForFoo" with null.
        return demandLogger(name, null, Reflection.getCallerClass());
    }

    /**
     * Find or create a logger for a named subsystem.  If a logger has
     * already been created with the given name it is returned.  Otherwise
     * a new logger is created.
     * <p>
     * If a new logger is created its log level will be configured
     * based on the LogManager and it will configured to also send logging
     * output to its parent's Handlers.  It will be registered in
     * the LogManager global namespace.
     * <p>
     * Note: The LogManager may only retain a weak reference to the newly
     * created Logger. It is important to understand that a previously
     * created Logger with the given name may be garbage collected at any
     * time if there is no strong reference to the Logger. In particular,
     * this means that two back-to-back calls like
     * {@code getLogger("MyLogger", ...).log(...)} may use different Logger
     * objects named "MyLogger" if there is no strong reference to the
     * Logger named "MyLogger" elsewhere in the program.
     * <p>
     * If the named Logger already exists and does not yet have a
     * localization resource bundle then the given resource bundle
     * name is used.  If the named Logger already exists and has
     * a different resource bundle name then an IllegalArgumentException
     * is thrown.
     * <p>
     * <p>
     *  为命名的子系统查找或创建记录器。如果已经创建了具有给定名称的记录器,则返回该记录器。否则,将创建一个新的记录器。
     * <p>
     *  如果创建了一个新的日志记录器,它的日志级别将基于LogManager进行配置,并且它将配置为还将日志输出发送到其父级的处理器。它将在LogManager全局命名空间中注册。
     * <p>
     *  注意：LogManager只能保留对新创建的Logger的弱引用。重要的是要知道,如果没有对记录器的强引用,那么以前创建的具有给定名称的记录器可以在任何时间被垃圾收集。
     * 特别是,这意味着如果没有强引用,像{@code getLogger("MyLogger",...)。
     * log(...)}这样两个背对背调用可以使用不同的Logger对象,名为"MyLogger"到程序中其他地方的名为"MyLogger"的Logger。
     * <p>
     * 如果命名的Logger已经存在,并且还没有本地化资源包,则使用给定的资源包名称。如果指定的Logger已经存在并且具有不同的资源束名称,那么将抛出IllegalArgumentException。
     * <p>
     * 
     * @param   name    A name for the logger.  This should
     *                          be a dot-separated name and should normally
     *                          be based on the package name or class name
     *                          of the subsystem, such as java.net
     *                          or javax.swing
     * @param   resourceBundleName  name of ResourceBundle to be used for localizing
     *                          messages for this logger. May be {@code null}
     *                          if none of the messages require localization.
     * @return a suitable Logger
     * @throws MissingResourceException if the resourceBundleName is non-null and
     *             no corresponding resource can be found.
     * @throws IllegalArgumentException if the Logger already exists and uses
     *             a different resource bundle name; or if
     *             {@code resourceBundleName} is {@code null} but the named
     *             logger has a resource bundle set.
     * @throws NullPointerException if the name is null.
     */

    // Synchronization is not required here. All synchronization for
    // adding a new Logger object is handled by LogManager.addLogger().
    @CallerSensitive
    public static Logger getLogger(String name, String resourceBundleName) {
        Class<?> callerClass = Reflection.getCallerClass();
        Logger result = demandLogger(name, resourceBundleName, callerClass);

        // MissingResourceException or IllegalArgumentException can be
        // thrown by setupResourceInfo().
        // We have to set the callers ClassLoader here in case demandLogger
        // above found a previously created Logger.  This can happen, for
        // example, if Logger.getLogger(name) is called and subsequently
        // Logger.getLogger(name, resourceBundleName) is called.  In this case
        // we won't necessarily have the correct classloader saved away, so
        // we need to set it here, too.

        result.setupResourceInfo(resourceBundleName, callerClass);
        return result;
    }

    // package-private
    // Add a platform logger to the system context.
    // i.e. caller of sun.util.logging.PlatformLogger.getLogger
    static Logger getPlatformLogger(String name) {
        LogManager manager = LogManager.getLogManager();

        // all loggers in the system context will default to
        // the system logger's resource bundle
        Logger result = manager.demandSystemLogger(name, SYSTEM_LOGGER_RB_NAME);
        return result;
    }

    /**
     * Create an anonymous Logger.  The newly created Logger is not
     * registered in the LogManager namespace.  There will be no
     * access checks on updates to the logger.
     * <p>
     * This factory method is primarily intended for use from applets.
     * Because the resulting Logger is anonymous it can be kept private
     * by the creating class.  This removes the need for normal security
     * checks, which in turn allows untrusted applet code to update
     * the control state of the Logger.  For example an applet can do
     * a setLevel or an addHandler on an anonymous Logger.
     * <p>
     * Even although the new logger is anonymous, it is configured
     * to have the root logger ("") as its parent.  This means that
     * by default it inherits its effective level and handlers
     * from the root logger. Changing its parent via the
     * {@link #setParent(java.util.logging.Logger) setParent} method
     * will still require the security permission specified by that method.
     * <p>
     *
     * <p>
     *  创建匿名记录器。新创建的Logger未在LogManager命名空间中注册。对记录器的更新不会进行访问检查。
     * <p>
     *  这种工厂方法主要用于applet。因为生成的Logger是匿名的,所以可以通过创建类保持私有。这消除了对正常安全检查的需要,这又允许不可信的小应用程序代码更新记录器的控制状态。
     * 例如,applet可以在匿名记录器上执行setLevel或addHandler。
     * <p>
     *  即使新的记录器是匿名的,它被配置为具有根记录器("")作为其父。这意味着默认情况下,它从根记录器继承其有效级别和处理程序。
     * 通过{@link #setParent(java.util.logging.Logger)setParent}方法更改其父级仍需要该方法指定的安全权限。
     * <p>
     * 
     * 
     * @return a newly created private Logger
     */
    public static Logger getAnonymousLogger() {
        return getAnonymousLogger(null);
    }

    /**
     * Create an anonymous Logger.  The newly created Logger is not
     * registered in the LogManager namespace.  There will be no
     * access checks on updates to the logger.
     * <p>
     * This factory method is primarily intended for use from applets.
     * Because the resulting Logger is anonymous it can be kept private
     * by the creating class.  This removes the need for normal security
     * checks, which in turn allows untrusted applet code to update
     * the control state of the Logger.  For example an applet can do
     * a setLevel or an addHandler on an anonymous Logger.
     * <p>
     * Even although the new logger is anonymous, it is configured
     * to have the root logger ("") as its parent.  This means that
     * by default it inherits its effective level and handlers
     * from the root logger.  Changing its parent via the
     * {@link #setParent(java.util.logging.Logger) setParent} method
     * will still require the security permission specified by that method.
     * <p>
     * <p>
     *  创建匿名记录器。新创建的Logger未在LogManager命名空间中注册。对记录器的更新不会进行访问检查。
     * <p>
     * 这种工厂方法主要用于applet。因为生成的Logger是匿名的,所以可以通过创建类保持私有。这消除了对正常安全检查的需要,这又允许不可信的小应用程序代码更新记录器的控制状态。
     * 例如,applet可以在匿名记录器上执行setLevel或addHandler。
     * <p>
     *  即使新的记录器是匿名的,它被配置为具有根记录器("")作为其父。这意味着默认情况下,它从根记录器继承其有效级别和处理程序。
     * 通过{@link #setParent(java.util.logging.Logger)setParent}方法更改其父级仍需要该方法指定的安全权限。
     * <p>
     * 
     * @param   resourceBundleName  name of ResourceBundle to be used for localizing
     *                          messages for this logger.
     *          May be null if none of the messages require localization.
     * @return a newly created private Logger
     * @throws MissingResourceException if the resourceBundleName is non-null and
     *             no corresponding resource can be found.
     */

    // Synchronization is not required here. All synchronization for
    // adding a new anonymous Logger object is handled by doSetParent().
    @CallerSensitive
    public static Logger getAnonymousLogger(String resourceBundleName) {
        LogManager manager = LogManager.getLogManager();
        // cleanup some Loggers that have been GC'ed
        manager.drainLoggerRefQueueBounded();
        Logger result = new Logger(null, resourceBundleName,
                                   Reflection.getCallerClass(), manager, false);
        result.anonymous = true;
        Logger root = manager.getLogger("");
        result.doSetParent(root);
        return result;
    }

    /**
     * Retrieve the localization resource bundle for this
     * logger.
     * This method will return a {@code ResourceBundle} that was either
     * set by the {@link
     * #setResourceBundle(java.util.ResourceBundle) setResourceBundle} method or
     * <a href="#ResourceBundleMapping">mapped from the
     * the resource bundle name</a> set via the {@link
     * Logger#getLogger(java.lang.String, java.lang.String) getLogger} factory
     * method for the current default locale.
     * <br>Note that if the result is {@code null}, then the Logger will use a resource
     * bundle or resource bundle name inherited from its parent.
     *
     * <p>
     *  检索此记录器的本地化资源包。
     * 此方法将返回由{@link #setResourceBundle(java.util.ResourceBundle)setResourceBundle}方法或<a href="#ResourceBundleMapping">
     * 从资源包名称映射的方法设置的{@code ResourceBundle} a>通过{@link Logger#getLogger(java.lang.String,java.lang.String)getLogger}
     * 工厂方法为当前默认语言环境设置。
     *  检索此记录器的本地化资源包。 <br>请注意,如果结果是{@code null},那么Logger将使用从其父代继承的资源束或资源束名称。
     * 
     * 
     * @return localization bundle (may be {@code null})
     */
    public ResourceBundle getResourceBundle() {
        return findResourceBundle(getResourceBundleName(), true);
    }

    /**
     * Retrieve the localization resource bundle name for this
     * logger.
     * This is either the name specified through the {@link
     * #getLogger(java.lang.String, java.lang.String) getLogger} factory method,
     * or the {@linkplain ResourceBundle#getBaseBundleName() base name} of the
     * ResourceBundle set through {@link
     * #setResourceBundle(java.util.ResourceBundle) setResourceBundle} method.
     * <br>Note that if the result is {@code null}, then the Logger will use a resource
     * bundle or resource bundle name inherited from its parent.
     *
     * <p>
     * 检索此记录器的本地化资源包名称。
     * 这是通过{@link #getLogger(java.lang.String,java.lang.String)getLogger}工厂方法指定的名称,或者是通过{@link}设置的ResourceBu
     * ndle的{@linkplain ResourceBundle#getBaseBundleName @link #setResourceBundle(java.util.ResourceBundle)setResourceBundle}
     * 方法。
     * 检索此记录器的本地化资源包名称。 <br>请注意,如果结果是{@code null},那么Logger将使用从其父代继承的资源束或资源束名称。
     * 
     * 
     * @return localization bundle name (may be {@code null})
     */
    public String getResourceBundleName() {
        return loggerBundle.resourceBundleName;
    }

    /**
     * Set a filter to control output on this Logger.
     * <P>
     * After passing the initial "level" check, the Logger will
     * call this Filter to check if a log record should really
     * be published.
     *
     * <p>
     *  设置过滤器以控制此记录器上的输出。
     * <P>
     *  在通过初始"级别"检查后,记录器将调用此过滤器来检查日志记录是否应该真正发布。
     * 
     * 
     * @param   newFilter  a filter object (may be null)
     * @throws  SecurityException if a security manager exists,
     *          this logger is not anonymous, and the caller
     *          does not have LoggingPermission("control").
     */
    public void setFilter(Filter newFilter) throws SecurityException {
        checkPermission();
        filter = newFilter;
    }

    /**
     * Get the current filter for this Logger.
     *
     * <p>
     *  获取此Logger的当前过滤器。
     * 
     * 
     * @return  a filter object (may be null)
     */
    public Filter getFilter() {
        return filter;
    }

    /**
     * Log a LogRecord.
     * <p>
     * All the other logging methods in this class call through
     * this method to actually perform any logging.  Subclasses can
     * override this single method to capture all log activity.
     *
     * <p>
     *  记录LogRecord。
     * <p>
     *  此类中的所有其他日志记录方法通过此方法调用,以实际执行任何日志记录。子类可以覆盖此单个方法以捕获所有日志活动。
     * 
     * 
     * @param record the LogRecord to be published
     */
    public void log(LogRecord record) {
        if (!isLoggable(record.getLevel())) {
            return;
        }
        Filter theFilter = filter;
        if (theFilter != null && !theFilter.isLoggable(record)) {
            return;
        }

        // Post the LogRecord to all our Handlers, and then to
        // our parents' handlers, all the way up the tree.

        Logger logger = this;
        while (logger != null) {
            final Handler[] loggerHandlers = isSystemLogger
                ? logger.accessCheckedHandlers()
                : logger.getHandlers();

            for (Handler handler : loggerHandlers) {
                handler.publish(record);
            }

            final boolean useParentHdls = isSystemLogger
                ? logger.useParentHandlers
                : logger.getUseParentHandlers();

            if (!useParentHdls) {
                break;
            }

            logger = isSystemLogger ? logger.parent : logger.getParent();
        }
    }

    // private support method for logging.
    // We fill in the logger name, resource bundle name, and
    // resource bundle and then call "void log(LogRecord)".
    private void doLog(LogRecord lr) {
        lr.setLoggerName(name);
        final LoggerBundle lb = getEffectiveLoggerBundle();
        final ResourceBundle  bundle = lb.userBundle;
        final String ebname = lb.resourceBundleName;
        if (ebname != null && bundle != null) {
            lr.setResourceBundleName(ebname);
            lr.setResourceBundle(bundle);
        }
        log(lr);
    }


    //================================================================
    // Start of convenience methods WITHOUT className and methodName
    //================================================================

    /**
     * Log a message, with no arguments.
     * <p>
     * If the logger is currently enabled for the given message
     * level then the given message is forwarded to all the
     * registered output Handler objects.
     * <p>
     * <p>
     *  记录一个没有参数的消息。
     * <p>
     *  如果记录器当前对于给定的消息级别被启用,则给定消息被转发到所有注册的输出处理器对象。
     * <p>
     * 
     * @param   level   One of the message level identifiers, e.g., SEVERE
     * @param   msg     The string message (or a key in the message catalog)
     */
    public void log(Level level, String msg) {
        if (!isLoggable(level)) {
            return;
        }
        LogRecord lr = new LogRecord(level, msg);
        doLog(lr);
    }

    /**
     * Log a message, which is only to be constructed if the logging level
     * is such that the message will actually be logged.
     * <p>
     * If the logger is currently enabled for the given message
     * level then the message is constructed by invoking the provided
     * supplier function and forwarded to all the registered output
     * Handler objects.
     * <p>
     * <p>
     *  记录消息,只有在记录级别为实际记录消息时才构建消息。
     * <p>
     *  如果记录器当前对于给定的消息级别被启用,则通过调用所提供的供应商功能并且转发到所有注册的输出处理器对象来构造消息。
     * <p>
     * 
     * @param   level   One of the message level identifiers, e.g., SEVERE
     * @param   msgSupplier   A function, which when called, produces the
     *                        desired log message
     */
    public void log(Level level, Supplier<String> msgSupplier) {
        if (!isLoggable(level)) {
            return;
        }
        LogRecord lr = new LogRecord(level, msgSupplier.get());
        doLog(lr);
    }

    /**
     * Log a message, with one object parameter.
     * <p>
     * If the logger is currently enabled for the given message
     * level then a corresponding LogRecord is created and forwarded
     * to all the registered output Handler objects.
     * <p>
     * <p>
     *  记录一条消息,带有一个对象参数。
     * <p>
     * 如果记录器当前对于给定的消息级别被启用,则创建相应的LogRecord并将其转发到所有注册的输出Handler对象。
     * <p>
     * 
     * @param   level   One of the message level identifiers, e.g., SEVERE
     * @param   msg     The string message (or a key in the message catalog)
     * @param   param1  parameter to the message
     */
    public void log(Level level, String msg, Object param1) {
        if (!isLoggable(level)) {
            return;
        }
        LogRecord lr = new LogRecord(level, msg);
        Object params[] = { param1 };
        lr.setParameters(params);
        doLog(lr);
    }

    /**
     * Log a message, with an array of object arguments.
     * <p>
     * If the logger is currently enabled for the given message
     * level then a corresponding LogRecord is created and forwarded
     * to all the registered output Handler objects.
     * <p>
     * <p>
     *  记录一条消息,带有一个对象参数数组。
     * <p>
     *  如果记录器当前对于给定的消息级别被启用,则创建相应的LogRecord并将其转发到所有注册的输出Handler对象。
     * <p>
     * 
     * @param   level   One of the message level identifiers, e.g., SEVERE
     * @param   msg     The string message (or a key in the message catalog)
     * @param   params  array of parameters to the message
     */
    public void log(Level level, String msg, Object params[]) {
        if (!isLoggable(level)) {
            return;
        }
        LogRecord lr = new LogRecord(level, msg);
        lr.setParameters(params);
        doLog(lr);
    }

    /**
     * Log a message, with associated Throwable information.
     * <p>
     * If the logger is currently enabled for the given message
     * level then the given arguments are stored in a LogRecord
     * which is forwarded to all registered output handlers.
     * <p>
     * Note that the thrown argument is stored in the LogRecord thrown
     * property, rather than the LogRecord parameters property.  Thus it is
     * processed specially by output Formatters and is not treated
     * as a formatting parameter to the LogRecord message property.
     * <p>
     * <p>
     *  记录一条消息,其中包含关联的Throwable信息。
     * <p>
     *  如果记录器当前对于给定的消息级别被启用,则给定的参数被存储在LogRecord中,LogRecord被转发到所有注册的输出处理器。
     * <p>
     *  请注意,thrown参数存储在LogRecord的thrown属性中,而不是LogRecord参数属性中。因此,它由输出格式化程序专门处理,不会被视为LogRecord消息属性的格式化参数。
     * <p>
     * 
     * @param   level   One of the message level identifiers, e.g., SEVERE
     * @param   msg     The string message (or a key in the message catalog)
     * @param   thrown  Throwable associated with log message.
     */
    public void log(Level level, String msg, Throwable thrown) {
        if (!isLoggable(level)) {
            return;
        }
        LogRecord lr = new LogRecord(level, msg);
        lr.setThrown(thrown);
        doLog(lr);
    }

    /**
     * Log a lazily constructed message, with associated Throwable information.
     * <p>
     * If the logger is currently enabled for the given message level then the
     * message is constructed by invoking the provided supplier function. The
     * message and the given {@link Throwable} are then stored in a {@link
     * LogRecord} which is forwarded to all registered output handlers.
     * <p>
     * Note that the thrown argument is stored in the LogRecord thrown
     * property, rather than the LogRecord parameters property.  Thus it is
     * processed specially by output Formatters and is not treated
     * as a formatting parameter to the LogRecord message property.
     * <p>
     * <p>
     *  记录一条懒惰构造的消息,带有相关的Throwable信息。
     * <p>
     *  如果记录器当前对于给定的消息级别被启用,则通过调用所提供的供应商功能来构造消息。
     * 然后消息和给定的{@link Throwable}存储在{@link LogRecord}中,它被转发到所有注册的输出处理程序。
     * <p>
     *  请注意,thrown参数存储在LogRecord的thrown属性中,而不是LogRecord参数属性中。因此,它由输出格式化程序专门处理,不会被视为LogRecord消息属性的格式化参数。
     * <p>
     * 
     * @param   level   One of the message level identifiers, e.g., SEVERE
     * @param   thrown  Throwable associated with log message.
     * @param   msgSupplier   A function, which when called, produces the
     *                        desired log message
     * @since   1.8
     */
    public void log(Level level, Throwable thrown, Supplier<String> msgSupplier) {
        if (!isLoggable(level)) {
            return;
        }
        LogRecord lr = new LogRecord(level, msgSupplier.get());
        lr.setThrown(thrown);
        doLog(lr);
    }

    //================================================================
    // Start of convenience methods WITH className and methodName
    //================================================================

    /**
     * Log a message, specifying source class and method,
     * with no arguments.
     * <p>
     * If the logger is currently enabled for the given message
     * level then the given message is forwarded to all the
     * registered output Handler objects.
     * <p>
     * <p>
     * 记录一条消息,指定没有参数的源类和方法。
     * <p>
     *  如果记录器当前对于给定的消息级别被启用,则给定消息被转发到所有注册的输出处理器对象。
     * <p>
     * 
     * @param   level   One of the message level identifiers, e.g., SEVERE
     * @param   sourceClass    name of class that issued the logging request
     * @param   sourceMethod   name of method that issued the logging request
     * @param   msg     The string message (or a key in the message catalog)
     */
    public void logp(Level level, String sourceClass, String sourceMethod, String msg) {
        if (!isLoggable(level)) {
            return;
        }
        LogRecord lr = new LogRecord(level, msg);
        lr.setSourceClassName(sourceClass);
        lr.setSourceMethodName(sourceMethod);
        doLog(lr);
    }

    /**
     * Log a lazily constructed message, specifying source class and method,
     * with no arguments.
     * <p>
     * If the logger is currently enabled for the given message
     * level then the message is constructed by invoking the provided
     * supplier function and forwarded to all the registered output
     * Handler objects.
     * <p>
     * <p>
     *  记录一个懒惰构造的消息,指定没有参数的源类和方法。
     * <p>
     *  如果记录器当前对于给定的消息级别被启用,则通过调用所提供的供应商功能并且转发到所有注册的输出处理器对象来构造消息。
     * <p>
     * 
     * @param   level   One of the message level identifiers, e.g., SEVERE
     * @param   sourceClass    name of class that issued the logging request
     * @param   sourceMethod   name of method that issued the logging request
     * @param   msgSupplier   A function, which when called, produces the
     *                        desired log message
     * @since   1.8
     */
    public void logp(Level level, String sourceClass, String sourceMethod,
                     Supplier<String> msgSupplier) {
        if (!isLoggable(level)) {
            return;
        }
        LogRecord lr = new LogRecord(level, msgSupplier.get());
        lr.setSourceClassName(sourceClass);
        lr.setSourceMethodName(sourceMethod);
        doLog(lr);
    }

    /**
     * Log a message, specifying source class and method,
     * with a single object parameter to the log message.
     * <p>
     * If the logger is currently enabled for the given message
     * level then a corresponding LogRecord is created and forwarded
     * to all the registered output Handler objects.
     * <p>
     * <p>
     *  记录消息,指定源类和方法,并在日志消息中使用单个对象参数。
     * <p>
     *  如果记录器当前对于给定的消息级别被启用,则创建相应的LogRecord并将其转发到所有注册的输出Handler对象。
     * <p>
     * 
     * @param   level   One of the message level identifiers, e.g., SEVERE
     * @param   sourceClass    name of class that issued the logging request
     * @param   sourceMethod   name of method that issued the logging request
     * @param   msg      The string message (or a key in the message catalog)
     * @param   param1    Parameter to the log message.
     */
    public void logp(Level level, String sourceClass, String sourceMethod,
                                                String msg, Object param1) {
        if (!isLoggable(level)) {
            return;
        }
        LogRecord lr = new LogRecord(level, msg);
        lr.setSourceClassName(sourceClass);
        lr.setSourceMethodName(sourceMethod);
        Object params[] = { param1 };
        lr.setParameters(params);
        doLog(lr);
    }

    /**
     * Log a message, specifying source class and method,
     * with an array of object arguments.
     * <p>
     * If the logger is currently enabled for the given message
     * level then a corresponding LogRecord is created and forwarded
     * to all the registered output Handler objects.
     * <p>
     * <p>
     *  记录一个消息,指定源类和方法,使用对象参数数组。
     * <p>
     *  如果记录器当前对于给定的消息级别被启用,则创建相应的LogRecord并将其转发到所有注册的输出Handler对象。
     * <p>
     * 
     * @param   level   One of the message level identifiers, e.g., SEVERE
     * @param   sourceClass    name of class that issued the logging request
     * @param   sourceMethod   name of method that issued the logging request
     * @param   msg     The string message (or a key in the message catalog)
     * @param   params  Array of parameters to the message
     */
    public void logp(Level level, String sourceClass, String sourceMethod,
                                                String msg, Object params[]) {
        if (!isLoggable(level)) {
            return;
        }
        LogRecord lr = new LogRecord(level, msg);
        lr.setSourceClassName(sourceClass);
        lr.setSourceMethodName(sourceMethod);
        lr.setParameters(params);
        doLog(lr);
    }

    /**
     * Log a message, specifying source class and method,
     * with associated Throwable information.
     * <p>
     * If the logger is currently enabled for the given message
     * level then the given arguments are stored in a LogRecord
     * which is forwarded to all registered output handlers.
     * <p>
     * Note that the thrown argument is stored in the LogRecord thrown
     * property, rather than the LogRecord parameters property.  Thus it is
     * processed specially by output Formatters and is not treated
     * as a formatting parameter to the LogRecord message property.
     * <p>
     * <p>
     *  记录消息,指定源类和方法,以及关联的Throwable信息。
     * <p>
     *  如果记录器当前对于给定的消息级别被启用,则给定的参数被存储在LogRecord中,LogRecord被转发到所有注册的输出处理器。
     * <p>
     * 请注意,thrown参数存储在LogRecord的thrown属性中,而不是LogRecord参数属性中。因此,它由输出格式化程序专门处理,不会被视为LogRecord消息属性的格式化参数。
     * <p>
     * 
     * @param   level   One of the message level identifiers, e.g., SEVERE
     * @param   sourceClass    name of class that issued the logging request
     * @param   sourceMethod   name of method that issued the logging request
     * @param   msg     The string message (or a key in the message catalog)
     * @param   thrown  Throwable associated with log message.
     */
    public void logp(Level level, String sourceClass, String sourceMethod,
                     String msg, Throwable thrown) {
        if (!isLoggable(level)) {
            return;
        }
        LogRecord lr = new LogRecord(level, msg);
        lr.setSourceClassName(sourceClass);
        lr.setSourceMethodName(sourceMethod);
        lr.setThrown(thrown);
        doLog(lr);
    }

    /**
     * Log a lazily constructed message, specifying source class and method,
     * with associated Throwable information.
     * <p>
     * If the logger is currently enabled for the given message level then the
     * message is constructed by invoking the provided supplier function. The
     * message and the given {@link Throwable} are then stored in a {@link
     * LogRecord} which is forwarded to all registered output handlers.
     * <p>
     * Note that the thrown argument is stored in the LogRecord thrown
     * property, rather than the LogRecord parameters property.  Thus it is
     * processed specially by output Formatters and is not treated
     * as a formatting parameter to the LogRecord message property.
     * <p>
     * <p>
     *  记录一条懒惰构造的消息,指定源类和方法,以及相关的Throwable信息。
     * <p>
     *  如果记录器当前对于给定的消息级别被启用,则通过调用所提供的供应商功能来构造消息。
     * 然后消息和给定的{@link Throwable}存储在{@link LogRecord}中,它被转发到所有注册的输出处理程序。
     * <p>
     *  请注意,thrown参数存储在LogRecord的thrown属性中,而不是LogRecord参数属性中。因此,它由输出格式化程序专门处理,不会被视为LogRecord消息属性的格式化参数。
     * <p>
     * 
     * @param   level   One of the message level identifiers, e.g., SEVERE
     * @param   sourceClass    name of class that issued the logging request
     * @param   sourceMethod   name of method that issued the logging request
     * @param   thrown  Throwable associated with log message.
     * @param   msgSupplier   A function, which when called, produces the
     *                        desired log message
     * @since   1.8
     */
    public void logp(Level level, String sourceClass, String sourceMethod,
                     Throwable thrown, Supplier<String> msgSupplier) {
        if (!isLoggable(level)) {
            return;
        }
        LogRecord lr = new LogRecord(level, msgSupplier.get());
        lr.setSourceClassName(sourceClass);
        lr.setSourceMethodName(sourceMethod);
        lr.setThrown(thrown);
        doLog(lr);
    }


    //=========================================================================
    // Start of convenience methods WITH className, methodName and bundle name.
    //=========================================================================

    // Private support method for logging for "logrb" methods.
    // We fill in the logger name, resource bundle name, and
    // resource bundle and then call "void log(LogRecord)".
    private void doLog(LogRecord lr, String rbname) {
        lr.setLoggerName(name);
        if (rbname != null) {
            lr.setResourceBundleName(rbname);
            lr.setResourceBundle(findResourceBundle(rbname, false));
        }
        log(lr);
    }

    // Private support method for logging for "logrb" methods.
    private void doLog(LogRecord lr, ResourceBundle rb) {
        lr.setLoggerName(name);
        if (rb != null) {
            lr.setResourceBundleName(rb.getBaseBundleName());
            lr.setResourceBundle(rb);
        }
        log(lr);
    }

    /**
     * Log a message, specifying source class, method, and resource bundle name
     * with no arguments.
     * <p>
     * If the logger is currently enabled for the given message
     * level then the given message is forwarded to all the
     * registered output Handler objects.
     * <p>
     * The msg string is localized using the named resource bundle.  If the
     * resource bundle name is null, or an empty String or invalid
     * then the msg string is not localized.
     * <p>
     * <p>
     *  记录消息,指定不带参数的源类,方法和资源束名称。
     * <p>
     *  如果记录器当前对于给定的消息级别被启用,则给定消息被转发到所有注册的输出处理器对象。
     * <p>
     *  msg字符串使用指定的资源束进行本地化。如果资源束名称为null,或空字符串或无效,则msg字符串未本地化。
     * <p>
     * 
     * @param   level   One of the message level identifiers, e.g., SEVERE
     * @param   sourceClass    name of class that issued the logging request
     * @param   sourceMethod   name of method that issued the logging request
     * @param   bundleName     name of resource bundle to localize msg,
     *                         can be null
     * @param   msg     The string message (or a key in the message catalog)
     * @deprecated Use {@link #logrb(java.util.logging.Level, java.lang.String,
     * java.lang.String, java.util.ResourceBundle, java.lang.String,
     * java.lang.Object...)} instead.
     */
    @Deprecated
    public void logrb(Level level, String sourceClass, String sourceMethod,
                                String bundleName, String msg) {
        if (!isLoggable(level)) {
            return;
        }
        LogRecord lr = new LogRecord(level, msg);
        lr.setSourceClassName(sourceClass);
        lr.setSourceMethodName(sourceMethod);
        doLog(lr, bundleName);
    }

    /**
     * Log a message, specifying source class, method, and resource bundle name,
     * with a single object parameter to the log message.
     * <p>
     * If the logger is currently enabled for the given message
     * level then a corresponding LogRecord is created and forwarded
     * to all the registered output Handler objects.
     * <p>
     * The msg string is localized using the named resource bundle.  If the
     * resource bundle name is null, or an empty String or invalid
     * then the msg string is not localized.
     * <p>
     * <p>
     *  记录消息,指定源类,方法和资源束名称,并在日志消息中使用单个对象参数。
     * <p>
     * 如果记录器当前对于给定的消息级别被启用,则创建相应的LogRecord并将其转发到所有注册的输出Handler对象。
     * <p>
     *  msg字符串使用指定的资源束进行本地化。如果资源束名称为null,或空字符串或无效,则msg字符串未本地化。
     * <p>
     * 
     * @param   level   One of the message level identifiers, e.g., SEVERE
     * @param   sourceClass    name of class that issued the logging request
     * @param   sourceMethod   name of method that issued the logging request
     * @param   bundleName     name of resource bundle to localize msg,
     *                         can be null
     * @param   msg      The string message (or a key in the message catalog)
     * @param   param1    Parameter to the log message.
     * @deprecated Use {@link #logrb(java.util.logging.Level, java.lang.String,
     *   java.lang.String, java.util.ResourceBundle, java.lang.String,
     *   java.lang.Object...)} instead
     */
    @Deprecated
    public void logrb(Level level, String sourceClass, String sourceMethod,
                                String bundleName, String msg, Object param1) {
        if (!isLoggable(level)) {
            return;
        }
        LogRecord lr = new LogRecord(level, msg);
        lr.setSourceClassName(sourceClass);
        lr.setSourceMethodName(sourceMethod);
        Object params[] = { param1 };
        lr.setParameters(params);
        doLog(lr, bundleName);
    }

    /**
     * Log a message, specifying source class, method, and resource bundle name,
     * with an array of object arguments.
     * <p>
     * If the logger is currently enabled for the given message
     * level then a corresponding LogRecord is created and forwarded
     * to all the registered output Handler objects.
     * <p>
     * The msg string is localized using the named resource bundle.  If the
     * resource bundle name is null, or an empty String or invalid
     * then the msg string is not localized.
     * <p>
     * <p>
     *  记录一条消息,指定源类,方法和资源包名称,以及一个对象参数数组。
     * <p>
     *  如果记录器当前对于给定的消息级别被启用,则创建相应的LogRecord并将其转发到所有注册的输出Handler对象。
     * <p>
     *  msg字符串使用指定的资源束进行本地化。如果资源束名称为null,或空字符串或无效,则msg字符串未本地化。
     * <p>
     * 
     * @param   level   One of the message level identifiers, e.g., SEVERE
     * @param   sourceClass    name of class that issued the logging request
     * @param   sourceMethod   name of method that issued the logging request
     * @param   bundleName     name of resource bundle to localize msg,
     *                         can be null.
     * @param   msg     The string message (or a key in the message catalog)
     * @param   params  Array of parameters to the message
     * @deprecated Use {@link #logrb(java.util.logging.Level, java.lang.String,
     *      java.lang.String, java.util.ResourceBundle, java.lang.String,
     *      java.lang.Object...)} instead.
     */
    @Deprecated
    public void logrb(Level level, String sourceClass, String sourceMethod,
                                String bundleName, String msg, Object params[]) {
        if (!isLoggable(level)) {
            return;
        }
        LogRecord lr = new LogRecord(level, msg);
        lr.setSourceClassName(sourceClass);
        lr.setSourceMethodName(sourceMethod);
        lr.setParameters(params);
        doLog(lr, bundleName);
    }

    /**
     * Log a message, specifying source class, method, and resource bundle,
     * with an optional list of message parameters.
     * <p>
     * If the logger is currently enabled for the given message
     * level then a corresponding LogRecord is created and forwarded
     * to all the registered output Handler objects.
     * <p>
     * The {@code msg} string is localized using the given resource bundle.
     * If the resource bundle is {@code null}, then the {@code msg} string is not
     * localized.
     * <p>
     * <p>
     *  记录消息,指定源类,方法和资源束,以及消息参数的可选列表。
     * <p>
     *  如果记录器当前对于给定的消息级别被启用,则创建相应的LogRecord并将其转发到所有注册的输出Handler对象。
     * <p>
     *  {@code msg}字符串使用给定的资源包进行本地化。如果资源束是{@code null},那么{@code msg}字符串未本地化。
     * <p>
     * 
     * @param   level   One of the message level identifiers, e.g., SEVERE
     * @param   sourceClass    Name of the class that issued the logging request
     * @param   sourceMethod   Name of the method that issued the logging request
     * @param   bundle         Resource bundle to localize {@code msg},
     *                         can be {@code null}.
     * @param   msg     The string message (or a key in the message catalog)
     * @param   params  Parameters to the message (optional, may be none).
     * @since 1.8
     */
    public void logrb(Level level, String sourceClass, String sourceMethod,
                      ResourceBundle bundle, String msg, Object... params) {
        if (!isLoggable(level)) {
            return;
        }
        LogRecord lr = new LogRecord(level, msg);
        lr.setSourceClassName(sourceClass);
        lr.setSourceMethodName(sourceMethod);
        if (params != null && params.length != 0) {
            lr.setParameters(params);
        }
        doLog(lr, bundle);
    }

    /**
     * Log a message, specifying source class, method, and resource bundle name,
     * with associated Throwable information.
     * <p>
     * If the logger is currently enabled for the given message
     * level then the given arguments are stored in a LogRecord
     * which is forwarded to all registered output handlers.
     * <p>
     * The msg string is localized using the named resource bundle.  If the
     * resource bundle name is null, or an empty String or invalid
     * then the msg string is not localized.
     * <p>
     * Note that the thrown argument is stored in the LogRecord thrown
     * property, rather than the LogRecord parameters property.  Thus it is
     * processed specially by output Formatters and is not treated
     * as a formatting parameter to the LogRecord message property.
     * <p>
     * <p>
     *  记录消息,指定源类,方法和资源包名称以及相关的Throwable信息。
     * <p>
     *  如果记录器当前对于给定的消息级别被启用,则给定的参数被存储在LogRecord中,LogRecord被转发到所有注册的输出处理器。
     * <p>
     * msg字符串使用指定的资源束进行本地化。如果资源束名称为null,或空字符串或无效,则msg字符串未本地化。
     * <p>
     *  请注意,thrown参数存储在LogRecord的thrown属性中,而不是LogRecord参数属性中。因此,它由输出格式化程序专门处理,不会被视为LogRecord消息属性的格式化参数。
     * <p>
     * 
     * @param   level   One of the message level identifiers, e.g., SEVERE
     * @param   sourceClass    name of class that issued the logging request
     * @param   sourceMethod   name of method that issued the logging request
     * @param   bundleName     name of resource bundle to localize msg,
     *                         can be null
     * @param   msg     The string message (or a key in the message catalog)
     * @param   thrown  Throwable associated with log message.
     * @deprecated Use {@link #logrb(java.util.logging.Level, java.lang.String,
     *     java.lang.String, java.util.ResourceBundle, java.lang.String,
     *     java.lang.Throwable)} instead.
     */
    @Deprecated
    public void logrb(Level level, String sourceClass, String sourceMethod,
                                        String bundleName, String msg, Throwable thrown) {
        if (!isLoggable(level)) {
            return;
        }
        LogRecord lr = new LogRecord(level, msg);
        lr.setSourceClassName(sourceClass);
        lr.setSourceMethodName(sourceMethod);
        lr.setThrown(thrown);
        doLog(lr, bundleName);
    }

    /**
     * Log a message, specifying source class, method, and resource bundle,
     * with associated Throwable information.
     * <p>
     * If the logger is currently enabled for the given message
     * level then the given arguments are stored in a LogRecord
     * which is forwarded to all registered output handlers.
     * <p>
     * The {@code msg} string is localized using the given resource bundle.
     * If the resource bundle is {@code null}, then the {@code msg} string is not
     * localized.
     * <p>
     * Note that the thrown argument is stored in the LogRecord thrown
     * property, rather than the LogRecord parameters property.  Thus it is
     * processed specially by output Formatters and is not treated
     * as a formatting parameter to the LogRecord message property.
     * <p>
     * <p>
     *  记录消息,指定源类,方法和资源束以及相关联的Throwable信息。
     * <p>
     *  如果记录器当前对于给定的消息级别被启用,则给定的参数被存储在LogRecord中,LogRecord被转发到所有注册的输出处理器。
     * <p>
     *  {@code msg}字符串使用给定的资源包进行本地化。如果资源束是{@code null},那么{@code msg}字符串未本地化。
     * <p>
     *  请注意,thrown参数存储在LogRecord的thrown属性中,而不是LogRecord参数属性中。因此,它由输出格式化程序专门处理,不会被视为LogRecord消息属性的格式化参数。
     * <p>
     * 
     * @param   level   One of the message level identifiers, e.g., SEVERE
     * @param   sourceClass    Name of the class that issued the logging request
     * @param   sourceMethod   Name of the method that issued the logging request
     * @param   bundle         Resource bundle to localize {@code msg},
     *                         can be {@code null}
     * @param   msg     The string message (or a key in the message catalog)
     * @param   thrown  Throwable associated with the log message.
     * @since 1.8
     */
    public void logrb(Level level, String sourceClass, String sourceMethod,
                      ResourceBundle bundle, String msg, Throwable thrown) {
        if (!isLoggable(level)) {
            return;
        }
        LogRecord lr = new LogRecord(level, msg);
        lr.setSourceClassName(sourceClass);
        lr.setSourceMethodName(sourceMethod);
        lr.setThrown(thrown);
        doLog(lr, bundle);
    }

    //======================================================================
    // Start of convenience methods for logging method entries and returns.
    //======================================================================

    /**
     * Log a method entry.
     * <p>
     * This is a convenience method that can be used to log entry
     * to a method.  A LogRecord with message "ENTRY", log level
     * FINER, and the given sourceMethod and sourceClass is logged.
     * <p>
     * <p>
     *  记录方法条目。
     * <p>
     *  这是一个方便的方法,可用于记录对方法的输入。具有消息"ENTRY"的日志记录,日志级别FINER,并且记录给定的sourceMethod和sourceClass。
     * <p>
     * 
     * @param   sourceClass    name of class that issued the logging request
     * @param   sourceMethod   name of method that is being entered
     */
    public void entering(String sourceClass, String sourceMethod) {
        logp(Level.FINER, sourceClass, sourceMethod, "ENTRY");
    }

    /**
     * Log a method entry, with one parameter.
     * <p>
     * This is a convenience method that can be used to log entry
     * to a method.  A LogRecord with message "ENTRY {0}", log level
     * FINER, and the given sourceMethod, sourceClass, and parameter
     * is logged.
     * <p>
     * <p>
     *  使用一个参数记录方法条目。
     * <p>
     * 这是一个方便的方法,可用于记录对方法的输入。具有消息"ENTRY {0}"的日志记录,日志级别FINER,并且记录给定的sourceMethod,sourceClass和参数。
     * <p>
     * 
     * @param   sourceClass    name of class that issued the logging request
     * @param   sourceMethod   name of method that is being entered
     * @param   param1         parameter to the method being entered
     */
    public void entering(String sourceClass, String sourceMethod, Object param1) {
        logp(Level.FINER, sourceClass, sourceMethod, "ENTRY {0}", param1);
    }

    /**
     * Log a method entry, with an array of parameters.
     * <p>
     * This is a convenience method that can be used to log entry
     * to a method.  A LogRecord with message "ENTRY" (followed by a
     * format {N} indicator for each entry in the parameter array),
     * log level FINER, and the given sourceMethod, sourceClass, and
     * parameters is logged.
     * <p>
     * <p>
     *  使用参数数组记录方法条目。
     * <p>
     *  这是一个方便的方法,可用于记录对方法的输入。
     * 具有消息"ENTRY"的LogRecord(后面是参数数组中每个条目的格式{N}指示符),日志级别FINER,以及给定的sourceMethod,sourceClass和参数。
     * <p>
     * 
     * @param   sourceClass    name of class that issued the logging request
     * @param   sourceMethod   name of method that is being entered
     * @param   params         array of parameters to the method being entered
     */
    public void entering(String sourceClass, String sourceMethod, Object params[]) {
        String msg = "ENTRY";
        if (params == null ) {
           logp(Level.FINER, sourceClass, sourceMethod, msg);
           return;
        }
        if (!isLoggable(Level.FINER)) return;
        for (int i = 0; i < params.length; i++) {
            msg = msg + " {" + i + "}";
        }
        logp(Level.FINER, sourceClass, sourceMethod, msg, params);
    }

    /**
     * Log a method return.
     * <p>
     * This is a convenience method that can be used to log returning
     * from a method.  A LogRecord with message "RETURN", log level
     * FINER, and the given sourceMethod and sourceClass is logged.
     * <p>
     * <p>
     *  记录方法返回。
     * <p>
     *  这是一个方便的方法,可用于记录从方法返回。具有消息"RETURN"的日志记录,日志级别FINER,并且记录给定的sourceMethod和sourceClass。
     * <p>
     * 
     * @param   sourceClass    name of class that issued the logging request
     * @param   sourceMethod   name of the method
     */
    public void exiting(String sourceClass, String sourceMethod) {
        logp(Level.FINER, sourceClass, sourceMethod, "RETURN");
    }


    /**
     * Log a method return, with result object.
     * <p>
     * This is a convenience method that can be used to log returning
     * from a method.  A LogRecord with message "RETURN {0}", log level
     * FINER, and the gives sourceMethod, sourceClass, and result
     * object is logged.
     * <p>
     * <p>
     *  记录一个方法返回,带有result对象。
     * <p>
     *  这是一个方便的方法,可用于记录从方法返回。具有消息"RETURN {0}"的日志记录,日志级别FINER,并且记录给出sourceMethod,sourceClass和result对象。
     * <p>
     * 
     * @param   sourceClass    name of class that issued the logging request
     * @param   sourceMethod   name of the method
     * @param   result  Object that is being returned
     */
    public void exiting(String sourceClass, String sourceMethod, Object result) {
        logp(Level.FINER, sourceClass, sourceMethod, "RETURN {0}", result);
    }

    /**
     * Log throwing an exception.
     * <p>
     * This is a convenience method to log that a method is
     * terminating by throwing an exception.  The logging is done
     * using the FINER level.
     * <p>
     * If the logger is currently enabled for the given message
     * level then the given arguments are stored in a LogRecord
     * which is forwarded to all registered output handlers.  The
     * LogRecord's message is set to "THROW".
     * <p>
     * Note that the thrown argument is stored in the LogRecord thrown
     * property, rather than the LogRecord parameters property.  Thus it is
     * processed specially by output Formatters and is not treated
     * as a formatting parameter to the LogRecord message property.
     * <p>
     * <p>
     *  日志抛出异常。
     * <p>
     *  这是一个方便的方法来记录方法通过抛出异常而终止。日志记录使用FINER级别完成。
     * <p>
     *  如果记录器当前对于给定的消息级别被启用,则给定的参数被存储在LogRecord中,LogRecord被转发到所有注册的输出处理器。 LogRecord的消息设置为"THROW"。
     * <p>
     * 请注意,thrown参数存储在LogRecord的thrown属性中,而不是LogRecord参数属性中。因此,它由输出格式化程序专门处理,不会被视为LogRecord消息属性的格式化参数。
     * <p>
     * 
     * @param   sourceClass    name of class that issued the logging request
     * @param   sourceMethod  name of the method.
     * @param   thrown  The Throwable that is being thrown.
     */
    public void throwing(String sourceClass, String sourceMethod, Throwable thrown) {
        if (!isLoggable(Level.FINER)) {
            return;
        }
        LogRecord lr = new LogRecord(Level.FINER, "THROW");
        lr.setSourceClassName(sourceClass);
        lr.setSourceMethodName(sourceMethod);
        lr.setThrown(thrown);
        doLog(lr);
    }

    //=======================================================================
    // Start of simple convenience methods using level names as method names
    //=======================================================================

    /**
     * Log a SEVERE message.
     * <p>
     * If the logger is currently enabled for the SEVERE message
     * level then the given message is forwarded to all the
     * registered output Handler objects.
     * <p>
     * <p>
     *  记录SEVERE消息。
     * <p>
     *  如果记录器当前启用了SEVERE消息级别,则给定消息被转发到所有注册的输出Handler对象。
     * <p>
     * 
     * @param   msg     The string message (or a key in the message catalog)
     */
    public void severe(String msg) {
        log(Level.SEVERE, msg);
    }

    /**
     * Log a WARNING message.
     * <p>
     * If the logger is currently enabled for the WARNING message
     * level then the given message is forwarded to all the
     * registered output Handler objects.
     * <p>
     * <p>
     *  记录警告消息。
     * <p>
     *  如果记录器当前启用了WARNING消息级别,则给定消息将转发到所有注册的输出Handler对象。
     * <p>
     * 
     * @param   msg     The string message (or a key in the message catalog)
     */
    public void warning(String msg) {
        log(Level.WARNING, msg);
    }

    /**
     * Log an INFO message.
     * <p>
     * If the logger is currently enabled for the INFO message
     * level then the given message is forwarded to all the
     * registered output Handler objects.
     * <p>
     * <p>
     *  记录INFO消息。
     * <p>
     *  如果记录器当前为INFO消息级别启用,则给定消息被转发到所有注册的输出Handler对象。
     * <p>
     * 
     * @param   msg     The string message (or a key in the message catalog)
     */
    public void info(String msg) {
        log(Level.INFO, msg);
    }

    /**
     * Log a CONFIG message.
     * <p>
     * If the logger is currently enabled for the CONFIG message
     * level then the given message is forwarded to all the
     * registered output Handler objects.
     * <p>
     * <p>
     *  记录CONFIG消息。
     * <p>
     *  如果记录器当前启用了CONFIG消息级别,则给定的消息被转发到所有注册的输出Handler对象。
     * <p>
     * 
     * @param   msg     The string message (or a key in the message catalog)
     */
    public void config(String msg) {
        log(Level.CONFIG, msg);
    }

    /**
     * Log a FINE message.
     * <p>
     * If the logger is currently enabled for the FINE message
     * level then the given message is forwarded to all the
     * registered output Handler objects.
     * <p>
     * <p>
     *  记录一条FINE消息。
     * <p>
     *  如果记录器当前启用了FINE消息级别,则给定消息将转发到所有注册的输出处理程序对象。
     * <p>
     * 
     * @param   msg     The string message (or a key in the message catalog)
     */
    public void fine(String msg) {
        log(Level.FINE, msg);
    }

    /**
     * Log a FINER message.
     * <p>
     * If the logger is currently enabled for the FINER message
     * level then the given message is forwarded to all the
     * registered output Handler objects.
     * <p>
     * <p>
     *  记录FINER消息。
     * <p>
     *  如果记录器当前启用了FINER消息级别,则给定消息被转发到所有注册的输出Handler对象。
     * <p>
     * 
     * @param   msg     The string message (or a key in the message catalog)
     */
    public void finer(String msg) {
        log(Level.FINER, msg);
    }

    /**
     * Log a FINEST message.
     * <p>
     * If the logger is currently enabled for the FINEST message
     * level then the given message is forwarded to all the
     * registered output Handler objects.
     * <p>
     * <p>
     *  记录FINEST消息。
     * <p>
     *  如果记录器当前启用了FINEST消息级别,则给定的消息被转发到所有注册的输出Handler对象。
     * <p>
     * 
     * @param   msg     The string message (or a key in the message catalog)
     */
    public void finest(String msg) {
        log(Level.FINEST, msg);
    }

    //=======================================================================
    // Start of simple convenience methods using level names as method names
    // and use Supplier<String>
    //=======================================================================

    /**
     * Log a SEVERE message, which is only to be constructed if the logging
     * level is such that the message will actually be logged.
     * <p>
     * If the logger is currently enabled for the SEVERE message
     * level then the message is constructed by invoking the provided
     * supplier function and forwarded to all the registered output
     * Handler objects.
     * <p>
     * <p>
     * 记录一个SEVERE消息,该消息只有在日志记录级别为消息实际被记录时才被构造。
     * <p>
     *  如果记录器当前启用了SEVERE消息级别,则通过调用所提供的供应商函数并转发到所有注册的输出Handler对象来构造消息。
     * <p>
     * 
     * @param   msgSupplier   A function, which when called, produces the
     *                        desired log message
     * @since   1.8
     */
    public void severe(Supplier<String> msgSupplier) {
        log(Level.SEVERE, msgSupplier);
    }

    /**
     * Log a WARNING message, which is only to be constructed if the logging
     * level is such that the message will actually be logged.
     * <p>
     * If the logger is currently enabled for the WARNING message
     * level then the message is constructed by invoking the provided
     * supplier function and forwarded to all the registered output
     * Handler objects.
     * <p>
     * <p>
     *  记录警告消息,只有在日志记录级别为实际记录消息时才构建此消息。
     * <p>
     *  如果记录器当前启用了WARNING消息级别,则通过调用所提供的供应商函数并转发到所有注册的输出Handler对象来构造消息。
     * <p>
     * 
     * @param   msgSupplier   A function, which when called, produces the
     *                        desired log message
     * @since   1.8
     */
    public void warning(Supplier<String> msgSupplier) {
        log(Level.WARNING, msgSupplier);
    }

    /**
     * Log a INFO message, which is only to be constructed if the logging
     * level is such that the message will actually be logged.
     * <p>
     * If the logger is currently enabled for the INFO message
     * level then the message is constructed by invoking the provided
     * supplier function and forwarded to all the registered output
     * Handler objects.
     * <p>
     * <p>
     *  记录INFO消息,只有在日志记录级别为实际记录消息时才构造该消息。
     * <p>
     *  如果当前对INFO消息级别启用记录器,则通过调用所提供的供应商功能并转发到所有注册的输出Handler对象来构造消息。
     * <p>
     * 
     * @param   msgSupplier   A function, which when called, produces the
     *                        desired log message
     * @since   1.8
     */
    public void info(Supplier<String> msgSupplier) {
        log(Level.INFO, msgSupplier);
    }

    /**
     * Log a CONFIG message, which is only to be constructed if the logging
     * level is such that the message will actually be logged.
     * <p>
     * If the logger is currently enabled for the CONFIG message
     * level then the message is constructed by invoking the provided
     * supplier function and forwarded to all the registered output
     * Handler objects.
     * <p>
     * <p>
     *  记录一个CONFIG消息,该消息只有在日志记录级别为实际记录消息时才被构造。
     * <p>
     *  如果记录器当前为CONFIG消息级别启用,则通过调用所提供的供应商功能并转发到所有注册的输出Handler对象来构造消息。
     * <p>
     * 
     * @param   msgSupplier   A function, which when called, produces the
     *                        desired log message
     * @since   1.8
     */
    public void config(Supplier<String> msgSupplier) {
        log(Level.CONFIG, msgSupplier);
    }

    /**
     * Log a FINE message, which is only to be constructed if the logging
     * level is such that the message will actually be logged.
     * <p>
     * If the logger is currently enabled for the FINE message
     * level then the message is constructed by invoking the provided
     * supplier function and forwarded to all the registered output
     * Handler objects.
     * <p>
     * <p>
     *  记录一条FINE消息,只有在日志记录级别为实际记录消息时才构造该消息。
     * <p>
     * 如果记录器当前启用了FINE消息级别,则通过调用所提供的供应商函数并转发到所有注册的输出Handler对象来构造消息。
     * <p>
     * 
     * @param   msgSupplier   A function, which when called, produces the
     *                        desired log message
     * @since   1.8
     */
    public void fine(Supplier<String> msgSupplier) {
        log(Level.FINE, msgSupplier);
    }

    /**
     * Log a FINER message, which is only to be constructed if the logging
     * level is such that the message will actually be logged.
     * <p>
     * If the logger is currently enabled for the FINER message
     * level then the message is constructed by invoking the provided
     * supplier function and forwarded to all the registered output
     * Handler objects.
     * <p>
     * <p>
     *  记录一条FINER消息,它只有在日志记录级别为实际记录消息时才被构造。
     * <p>
     *  如果记录器当前启用了FINER消息级别,则通过调用提供的供应商函数并转发到所有注册的输出Handler对象来构造消息。
     * <p>
     * 
     * @param   msgSupplier   A function, which when called, produces the
     *                        desired log message
     * @since   1.8
     */
    public void finer(Supplier<String> msgSupplier) {
        log(Level.FINER, msgSupplier);
    }

    /**
     * Log a FINEST message, which is only to be constructed if the logging
     * level is such that the message will actually be logged.
     * <p>
     * If the logger is currently enabled for the FINEST message
     * level then the message is constructed by invoking the provided
     * supplier function and forwarded to all the registered output
     * Handler objects.
     * <p>
     * <p>
     *  记录一条FINEST消息,它只有在日志记录级别为实际记录消息时才被构造。
     * <p>
     *  如果记录器当前启用了FINEST消息级别,则通过调用所提供的供应商函数并转发到所有注册的输出Handler对象来构造消息。
     * <p>
     * 
     * @param   msgSupplier   A function, which when called, produces the
     *                        desired log message
     * @since   1.8
     */
    public void finest(Supplier<String> msgSupplier) {
        log(Level.FINEST, msgSupplier);
    }

    //================================================================
    // End of convenience methods
    //================================================================

    /**
     * Set the log level specifying which message levels will be
     * logged by this logger.  Message levels lower than this
     * value will be discarded.  The level value Level.OFF
     * can be used to turn off logging.
     * <p>
     * If the new level is null, it means that this node should
     * inherit its level from its nearest ancestor with a specific
     * (non-null) level value.
     *
     * <p>
     *  设置日志级别,指定此记录器将记录哪些消息级别。低于此值的邮件级别将被丢弃。级别值Level.OFF可用于关闭日志记录。
     * <p>
     *  如果新级别为null,则意味着此节点应从具有特定(非空)级别值的最近祖先继承其级别。
     * 
     * 
     * @param newLevel   the new value for the log level (may be null)
     * @throws  SecurityException if a security manager exists,
     *          this logger is not anonymous, and the caller
     *          does not have LoggingPermission("control").
     */
    public void setLevel(Level newLevel) throws SecurityException {
        checkPermission();
        synchronized (treeLock) {
            levelObject = newLevel;
            updateEffectiveLevel();
        }
    }

    final boolean isLevelInitialized() {
        return levelObject != null;
    }

    /**
     * Get the log Level that has been specified for this Logger.
     * The result may be null, which means that this logger's
     * effective level will be inherited from its parent.
     *
     * <p>
     *  获取为此记录器指定的日志级别。结果可能为null,这意味着此记录器的有效级别将从其父级继承。
     * 
     * 
     * @return  this Logger's level
     */
    public Level getLevel() {
        return levelObject;
    }

    /**
     * Check if a message of the given level would actually be logged
     * by this logger.  This check is based on the Loggers effective level,
     * which may be inherited from its parent.
     *
     * <p>
     * 检查给定级别的消息是否实际上将被此记录器记录。此检查基于记录器有效级别,可以从其父级继承。
     * 
     * 
     * @param   level   a message logging level
     * @return  true if the given message level is currently being logged.
     */
    public boolean isLoggable(Level level) {
        if (level.intValue() < levelValue || levelValue == offValue) {
            return false;
        }
        return true;
    }

    /**
     * Get the name for this logger.
     * <p>
     *  获取此记录器的名称。
     * 
     * 
     * @return logger name.  Will be null for anonymous Loggers.
     */
    public String getName() {
        return name;
    }

    /**
     * Add a log Handler to receive logging messages.
     * <p>
     * By default, Loggers also send their output to their parent logger.
     * Typically the root Logger is configured with a set of Handlers
     * that essentially act as default handlers for all loggers.
     *
     * <p>
     *  添加日志处理程序以接收日志消息。
     * <p>
     *  默认情况下,记录器还将其输出发送到其父记录器。通常,根Logger配置有一组处理程序,它们基本上充当所有记录器的默认处理程序。
     * 
     * 
     * @param   handler a logging Handler
     * @throws  SecurityException if a security manager exists,
     *          this logger is not anonymous, and the caller
     *          does not have LoggingPermission("control").
     */
    public void addHandler(Handler handler) throws SecurityException {
        // Check for null handler
        handler.getClass();
        checkPermission();
        handlers.add(handler);
    }

    /**
     * Remove a log Handler.
     * <P>
     * Returns silently if the given Handler is not found or is null
     *
     * <p>
     *  删除日志处理程序。
     * <P>
     *  如果未找到给定的处理程序或为空,则以静默方式返回
     * 
     * 
     * @param   handler a logging Handler
     * @throws  SecurityException if a security manager exists,
     *          this logger is not anonymous, and the caller
     *          does not have LoggingPermission("control").
     */
    public void removeHandler(Handler handler) throws SecurityException {
        checkPermission();
        if (handler == null) {
            return;
        }
        handlers.remove(handler);
    }

    /**
     * Get the Handlers associated with this logger.
     * <p>
     * <p>
     *  获取与此记录器相关联的处理程序。
     * <p>
     * 
     * @return  an array of all registered Handlers
     */
    public Handler[] getHandlers() {
        return accessCheckedHandlers();
    }

    // This method should ideally be marked final - but unfortunately
    // it needs to be overridden by LogManager.RootLogger
    Handler[] accessCheckedHandlers() {
        return handlers.toArray(emptyHandlers);
    }

    /**
     * Specify whether or not this logger should send its output
     * to its parent Logger.  This means that any LogRecords will
     * also be written to the parent's Handlers, and potentially
     * to its parent, recursively up the namespace.
     *
     * <p>
     *  指定此记录器是否应将其输出发送到其父记录器。这意味着任何LogRecords也将写入父的处理程序,并且可能地写入其父项,递归到命名空间。
     * 
     * 
     * @param useParentHandlers   true if output is to be sent to the
     *          logger's parent.
     * @throws  SecurityException if a security manager exists,
     *          this logger is not anonymous, and the caller
     *          does not have LoggingPermission("control").
     */
    public void setUseParentHandlers(boolean useParentHandlers) {
        checkPermission();
        this.useParentHandlers = useParentHandlers;
    }

    /**
     * Discover whether or not this logger is sending its output
     * to its parent logger.
     *
     * <p>
     *  发现此记录器是否正在将其输出发送到其父记录器。
     * 
     * 
     * @return  true if output is to be sent to the logger's parent
     */
    public boolean getUseParentHandlers() {
        return useParentHandlers;
    }

    private static ResourceBundle findSystemResourceBundle(final Locale locale) {
        // the resource bundle is in a restricted package
        return AccessController.doPrivileged(new PrivilegedAction<ResourceBundle>() {
            @Override
            public ResourceBundle run() {
                try {
                    return ResourceBundle.getBundle(SYSTEM_LOGGER_RB_NAME,
                                                    locale,
                                                    ClassLoader.getSystemClassLoader());
                } catch (MissingResourceException e) {
                    throw new InternalError(e.toString());
                }
            }
        });
    }

    /**
     * Private utility method to map a resource bundle name to an
     * actual resource bundle, using a simple one-entry cache.
     * Returns null for a null name.
     * May also return null if we can't find the resource bundle and
     * there is no suitable previous cached value.
     *
     * <p>
     *  私有实用程序方法使用简单的单项缓存将资源束名称映射到实际的资源束。为空名称返回null。如果我们找不到资源束,并且没有合适的以前的缓存值,也可能返回null。
     * 
     * 
     * @param name the ResourceBundle to locate
     * @param userCallersClassLoader if true search using the caller's ClassLoader
     * @return ResourceBundle specified by name or null if not found
     */
    private synchronized ResourceBundle findResourceBundle(String name,
                                                           boolean useCallersClassLoader) {
        // For all lookups, we first check the thread context class loader
        // if it is set.  If not, we use the system classloader.  If we
        // still haven't found it we use the callersClassLoaderRef if it
        // is set and useCallersClassLoader is true.  We set
        // callersClassLoaderRef initially upon creating the logger with a
        // non-null resource bundle name.

        // Return a null bundle for a null name.
        if (name == null) {
            return null;
        }

        Locale currentLocale = Locale.getDefault();
        final LoggerBundle lb = loggerBundle;

        // Normally we should hit on our simple one entry cache.
        if (lb.userBundle != null &&
                name.equals(lb.resourceBundleName)) {
            return lb.userBundle;
        } else if (catalog != null && currentLocale.equals(catalogLocale)
                && name.equals(catalogName)) {
            return catalog;
        }

        if (name.equals(SYSTEM_LOGGER_RB_NAME)) {
            catalog = findSystemResourceBundle(currentLocale);
            catalogName = name;
            catalogLocale = currentLocale;
            return catalog;
        }

        // Use the thread's context ClassLoader.  If there isn't one, use the
        // {@linkplain java.lang.ClassLoader#getSystemClassLoader() system ClassLoader}.
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if (cl == null) {
            cl = ClassLoader.getSystemClassLoader();
        }
        try {
            catalog = ResourceBundle.getBundle(name, currentLocale, cl);
            catalogName = name;
            catalogLocale = currentLocale;
            return catalog;
        } catch (MissingResourceException ex) {
            // We can't find the ResourceBundle in the default
            // ClassLoader.  Drop through.
        }

        if (useCallersClassLoader) {
            // Try with the caller's ClassLoader
            ClassLoader callersClassLoader = getCallersClassLoader();

            if (callersClassLoader == null || callersClassLoader == cl) {
                return null;
            }

            try {
                catalog = ResourceBundle.getBundle(name, currentLocale,
                                                   callersClassLoader);
                catalogName = name;
                catalogLocale = currentLocale;
                return catalog;
            } catch (MissingResourceException ex) {
                return null; // no luck
            }
        } else {
            return null;
        }
    }

    // Private utility method to initialize our one entry
    // resource bundle name cache and the callers ClassLoader
    // Note: for consistency reasons, we are careful to check
    // that a suitable ResourceBundle exists before setting the
    // resourceBundleName field.
    // Synchronized to prevent races in setting the fields.
    private synchronized void setupResourceInfo(String name,
                                                Class<?> callersClass) {
        final LoggerBundle lb = loggerBundle;
        if (lb.resourceBundleName != null) {
            // this Logger already has a ResourceBundle

            if (lb.resourceBundleName.equals(name)) {
                // the names match so there is nothing more to do
                return;
            }

            // cannot change ResourceBundles once they are set
            throw new IllegalArgumentException(
                lb.resourceBundleName + " != " + name);
        }

        if (name == null) {
            return;
        }

        setCallersClassLoaderRef(callersClass);
        if (isSystemLogger && getCallersClassLoader() != null) {
            checkPermission();
        }
        if (findResourceBundle(name, true) == null) {
            // We've failed to find an expected ResourceBundle.
            // unset the caller's ClassLoader since we were unable to find the
            // the bundle using it
            this.callersClassLoaderRef = null;
            throw new MissingResourceException("Can't find " + name + " bundle",
                                                name, "");
        }

        // if lb.userBundle is not null we won't reach this line.
        assert lb.userBundle == null;
        loggerBundle = LoggerBundle.get(name, null);
    }

    /**
     * Sets a resource bundle on this logger.
     * All messages will be logged using the given resource bundle for its
     * specific {@linkplain ResourceBundle#getLocale locale}.
     * <p>
     *  在此记录器上设置资源束。所有消息将使用给定的资源包来记录其特定的{@linkplain ResourceBundle#getLocale locale}。
     * 
     * 
     * @param bundle The resource bundle that this logger shall use.
     * @throws NullPointerException if the given bundle is {@code null}.
     * @throws IllegalArgumentException if the given bundle doesn't have a
     *         {@linkplain ResourceBundle#getBaseBundleName base name},
     *         or if this logger already has a resource bundle set but
     *         the given bundle has a different base name.
     * @throws SecurityException if a security manager exists,
     *         this logger is not anonymous, and the caller
     *         does not have LoggingPermission("control").
     * @since 1.8
     */
    public void setResourceBundle(ResourceBundle bundle) {
        checkPermission();

        // Will throw NPE if bundle is null.
        final String baseName = bundle.getBaseBundleName();

        // bundle must have a name
        if (baseName == null || baseName.isEmpty()) {
            throw new IllegalArgumentException("resource bundle must have a name");
        }

        synchronized (this) {
            LoggerBundle lb = loggerBundle;
            final boolean canReplaceResourceBundle = lb.resourceBundleName == null
                    || lb.resourceBundleName.equals(baseName);

            if (!canReplaceResourceBundle) {
                throw new IllegalArgumentException("can't replace resource bundle");
            }


            loggerBundle = LoggerBundle.get(baseName, bundle);
        }
    }

    /**
     * Return the parent for this Logger.
     * <p>
     * This method returns the nearest extant parent in the namespace.
     * Thus if a Logger is called "a.b.c.d", and a Logger called "a.b"
     * has been created but no logger "a.b.c" exists, then a call of
     * getParent on the Logger "a.b.c.d" will return the Logger "a.b".
     * <p>
     * The result will be null if it is called on the root Logger
     * in the namespace.
     *
     * <p>
     *  返回此记录器的父级。
     * <p>
     * 此方法返回命名空间中最近的现有父代。
     * 因此,如果记录器被称为"a.b.c.d",并且已经创建了称为"a.b"的记录器,但是不存在记录器"a.b.c",则在记录器"a.b.c.d"上的getParent的调用将返回记录器"a.b"。
     * <p>
     *  如果在命名空间中的根Logger上调用,结果将为null。
     * 
     * @return nearest existing parent Logger
     */
    public Logger getParent() {
        // Note: this used to be synchronized on treeLock.  However, this only
        // provided memory semantics, as there was no guarantee that the caller
        // would synchronize on treeLock (in fact, there is no way for external
        // callers to so synchronize).  Therefore, we have made parent volatile
        // instead.
        return parent;
    }

    /**
     * Set the parent for this Logger.  This method is used by
     * the LogManager to update a Logger when the namespace changes.
     * <p>
     * It should not be called from application code.
     * <p>
     * <p>
     * 
     * 
     * @param  parent   the new parent logger
     * @throws  SecurityException  if a security manager exists and if
     *          the caller does not have LoggingPermission("control").
     */
    public void setParent(Logger parent) {
        if (parent == null) {
            throw new NullPointerException();
        }

        // check permission for all loggers, including anonymous loggers
        if (manager == null) {
            manager = LogManager.getLogManager();
        }
        manager.checkPermission();

        doSetParent(parent);
    }

    // Private method to do the work for parenting a child
    // Logger onto a parent logger.
    private void doSetParent(Logger newParent) {

        // System.err.println("doSetParent \"" + getName() + "\" \""
        //                              + newParent.getName() + "\"");

        synchronized (treeLock) {

            // Remove ourself from any previous parent.
            LogManager.LoggerWeakRef ref = null;
            if (parent != null) {
                // assert parent.kids != null;
                for (Iterator<LogManager.LoggerWeakRef> iter = parent.kids.iterator(); iter.hasNext(); ) {
                    ref = iter.next();
                    Logger kid =  ref.get();
                    if (kid == this) {
                        // ref is used down below to complete the reparenting
                        iter.remove();
                        break;
                    } else {
                        ref = null;
                    }
                }
                // We have now removed ourself from our parents' kids.
            }

            // Set our new parent.
            parent = newParent;
            if (parent.kids == null) {
                parent.kids = new ArrayList<>(2);
            }
            if (ref == null) {
                // we didn't have a previous parent
                ref = manager.new LoggerWeakRef(this);
            }
            ref.setParentRef(new WeakReference<>(parent));
            parent.kids.add(ref);

            // As a result of the reparenting, the effective level
            // may have changed for us and our children.
            updateEffectiveLevel();

        }
    }

    // Package-level method.
    // Remove the weak reference for the specified child Logger from the
    // kid list. We should only be called from LoggerWeakRef.dispose().
    final void removeChildLogger(LogManager.LoggerWeakRef child) {
        synchronized (treeLock) {
            for (Iterator<LogManager.LoggerWeakRef> iter = kids.iterator(); iter.hasNext(); ) {
                LogManager.LoggerWeakRef ref = iter.next();
                if (ref == child) {
                    iter.remove();
                    return;
                }
            }
        }
    }

    // Recalculate the effective level for this node and
    // recursively for our children.

    private void updateEffectiveLevel() {
        // assert Thread.holdsLock(treeLock);

        // Figure out our current effective level.
        int newLevelValue;
        if (levelObject != null) {
            newLevelValue = levelObject.intValue();
        } else {
            if (parent != null) {
                newLevelValue = parent.levelValue;
            } else {
                // This may happen during initialization.
                newLevelValue = Level.INFO.intValue();
            }
        }

        // If our effective value hasn't changed, we're done.
        if (levelValue == newLevelValue) {
            return;
        }

        levelValue = newLevelValue;

        // System.err.println("effective level: \"" + getName() + "\" := " + level);

        // Recursively update the level on each of our kids.
        if (kids != null) {
            for (int i = 0; i < kids.size(); i++) {
                LogManager.LoggerWeakRef ref = kids.get(i);
                Logger kid =  ref.get();
                if (kid != null) {
                    kid.updateEffectiveLevel();
                }
            }
        }
    }


    // Private method to get the potentially inherited
    // resource bundle and resource bundle name for this Logger.
    // This method never returns null.
    private LoggerBundle getEffectiveLoggerBundle() {
        final LoggerBundle lb = loggerBundle;
        if (lb.isSystemBundle()) {
            return SYSTEM_BUNDLE;
        }

        // first take care of this logger
        final ResourceBundle b = getResourceBundle();
        if (b != null && b == lb.userBundle) {
            return lb;
        } else if (b != null) {
            // either lb.userBundle is null or getResourceBundle() is
            // overriden
            final String rbName = getResourceBundleName();
            return LoggerBundle.get(rbName, b);
        }

        // no resource bundle was specified on this logger, look up the
        // parent stack.
        Logger target = this.parent;
        while (target != null) {
            final LoggerBundle trb = target.loggerBundle;
            if (trb.isSystemBundle()) {
                return SYSTEM_BUNDLE;
            }
            if (trb.userBundle != null) {
                return trb;
            }
            final String rbName = isSystemLogger
                // ancestor of a system logger is expected to be a system logger.
                // ignore resource bundle name if it's not.
                ? (target.isSystemLogger ? trb.resourceBundleName : null)
                : target.getResourceBundleName();
            if (rbName != null) {
                return LoggerBundle.get(rbName,
                        findResourceBundle(rbName, true));
            }
            target = isSystemLogger ? target.parent : target.getParent();
        }
        return NO_RESOURCE_BUNDLE;
    }

}
