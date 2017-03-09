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
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.io.*;

import sun.misc.JavaLangAccess;
import sun.misc.SharedSecrets;

/**
 * LogRecord objects are used to pass logging requests between
 * the logging framework and individual log Handlers.
 * <p>
 * When a LogRecord is passed into the logging framework it
 * logically belongs to the framework and should no longer be
 * used or updated by the client application.
 * <p>
 * Note that if the client application has not specified an
 * explicit source method name and source class name, then the
 * LogRecord class will infer them automatically when they are
 * first accessed (due to a call on getSourceMethodName or
 * getSourceClassName) by analyzing the call stack.  Therefore,
 * if a logging Handler wants to pass off a LogRecord to another
 * thread, or to transmit it over RMI, and if it wishes to subsequently
 * obtain method name or class name information it should call
 * one of getSourceClassName or getSourceMethodName to force
 * the values to be filled in.
 * <p>
 * <b> Serialization notes:</b>
 * <ul>
 * <li>The LogRecord class is serializable.
 *
 * <li> Because objects in the parameters array may not be serializable,
 * during serialization all objects in the parameters array are
 * written as the corresponding Strings (using Object.toString).
 *
 * <li> The ResourceBundle is not transmitted as part of the serialized
 * form, but the resource bundle name is, and the recipient object's
 * readObject method will attempt to locate a suitable resource bundle.
 *
 * </ul>
 *
 * <p>
 *  LogRecord对象用于在日志记录框架和各个日志处理程序之间传递日志记录请求。
 * <p>
 *  当LogRecord传递到日志框架中时,它在逻辑上属于框架,并且不再由客户端应用程序使用或更新。
 * <p>
 *  请注意,如果客户端应用程序没有指定显式源方法名和源类名,那么LogRecord类将在第一次访问时(由于对getSourceMethodName或getSourceClassName的调用)通过分析调用
 * 堆栈来自动推断它们。
 * 因此,如果日志处理程序想要将LogRecord传递给另一个线程,或者通过RMI传输它,并且如果它希望随后获取方法名或类名信息,它应该调用getSourceClassName或getSourceMetho
 * dName之一来强制值为填写。
 * <p>
 *  <b>序列化说明：</b>
 * <ul>
 *  <li> LogRecord类是可序列化的。
 * 
 *  <li>因为参数数组中的对象可能无法序列化,因此在序列化期间,参数数组中的所有对象都将作为对应的字符串(使用Object.toString)写入。
 * 
 *  <li> ResourceBundle不作为序列化表单的一部分传输,但资源包名称为,而收件人对象的readObject方法将尝试查找合适的资源包。
 * 
 * </ul>
 * 
 * 
 * @since 1.4
 */

public class LogRecord implements java.io.Serializable {
    private static final AtomicLong globalSequenceNumber
        = new AtomicLong(0);

    /**
     * The default value of threadID will be the current thread's
     * thread id, for ease of correlation, unless it is greater than
     * MIN_SEQUENTIAL_THREAD_ID, in which case we try harder to keep
     * our promise to keep threadIDs unique by avoiding collisions due
     * to 32-bit wraparound.  Unfortunately, LogRecord.getThreadID()
     * returns int, while Thread.getId() returns long.
     * <p>
     * threadID的默认值将是当前线程的线程ID,以便于相关,除非它大于MIN_SEQUENTIAL_THREAD_ID,在这种情况下,我们更努力地保持我们的承诺,通过避免由于32位换行造成的冲突来保持t
     * hreadID唯一。
     * 不幸的是,LogRecord.getThreadID()返回int,而Thread.getId()返回long。
     * 
     */
    private static final int MIN_SEQUENTIAL_THREAD_ID = Integer.MAX_VALUE / 2;

    private static final AtomicInteger nextThreadId
        = new AtomicInteger(MIN_SEQUENTIAL_THREAD_ID);

    private static final ThreadLocal<Integer> threadIds = new ThreadLocal<>();

    /**
    /* <p>
    /* 
     * @serial Logging message level
     */
    private Level level;

    /**
    /* <p>
    /* 
     * @serial Sequence number
     */
    private long sequenceNumber;

    /**
    /* <p>
    /* 
     * @serial Class that issued logging call
     */
    private String sourceClassName;

    /**
    /* <p>
    /* 
     * @serial Method that issued logging call
     */
    private String sourceMethodName;

    /**
    /* <p>
    /* 
     * @serial Non-localized raw message text
     */
    private String message;

    /**
    /* <p>
    /* 
     * @serial Thread ID for thread that issued logging call.
     */
    private int threadID;

    /**
    /* <p>
    /* 
     * @serial Event time in milliseconds since 1970
     */
    private long millis;

    /**
    /* <p>
    /* 
     * @serial The Throwable (if any) associated with log message
     */
    private Throwable thrown;

    /**
    /* <p>
    /* 
     * @serial Name of the source Logger.
     */
    private String loggerName;

    /**
    /* <p>
    /* 
     * @serial Resource bundle name to localized log message.
     */
    private String resourceBundleName;

    private transient boolean needToInferCaller;
    private transient Object parameters[];
    private transient ResourceBundle resourceBundle;

    /**
     * Returns the default value for a new LogRecord's threadID.
     * <p>
     *  返回新LogRecord的threadID的默认值。
     * 
     */
    private int defaultThreadID() {
        long tid = Thread.currentThread().getId();
        if (tid < MIN_SEQUENTIAL_THREAD_ID) {
            return (int) tid;
        } else {
            Integer id = threadIds.get();
            if (id == null) {
                id = nextThreadId.getAndIncrement();
                threadIds.set(id);
            }
            return id;
        }
    }

    /**
     * Construct a LogRecord with the given level and message values.
     * <p>
     * The sequence property will be initialized with a new unique value.
     * These sequence values are allocated in increasing order within a VM.
     * <p>
     * The millis property will be initialized to the current time.
     * <p>
     * The thread ID property will be initialized with a unique ID for
     * the current thread.
     * <p>
     * All other properties will be initialized to "null".
     *
     * <p>
     *  使用给定的级别和消息值构造LogRecord。
     * <p>
     *  序列属性将使用一个新的唯一值初始化。这些序列值在VM内按升序分配。
     * <p>
     *  millis属性将被初始化为当前时间。
     * <p>
     *  线程ID属性将使用当前线程的唯一ID初始化。
     * <p>
     *  所有其他属性将初始化为"null"。
     * 
     * 
     * @param level  a logging level value
     * @param msg  the raw non-localized logging message (may be null)
     */
    public LogRecord(Level level, String msg) {
        // Make sure level isn't null, by calling random method.
        level.getClass();
        this.level = level;
        message = msg;
        // Assign a thread ID and a unique sequence number.
        sequenceNumber = globalSequenceNumber.getAndIncrement();
        threadID = defaultThreadID();
        millis = System.currentTimeMillis();
        needToInferCaller = true;
   }

    /**
     * Get the source Logger's name.
     *
     * <p>
     *  获取源记录器的名称。
     * 
     * 
     * @return source logger name (may be null)
     */
    public String getLoggerName() {
        return loggerName;
    }

    /**
     * Set the source Logger's name.
     *
     * <p>
     *  设置源记录器的名称。
     * 
     * 
     * @param name   the source logger name (may be null)
     */
    public void setLoggerName(String name) {
        loggerName = name;
    }

    /**
     * Get the localization resource bundle
     * <p>
     * This is the ResourceBundle that should be used to localize
     * the message string before formatting it.  The result may
     * be null if the message is not localizable, or if no suitable
     * ResourceBundle is available.
     * <p>
     *  获取本地化资源包
     * <p>
     *  这是ResourceBundle,应该用于本地化消息字符串格式化之前。如果消息不可本地化,或者如果没有合适的ResourceBundle可用,则结果可以为null。
     * 
     * 
     * @return the localization resource bundle
     */
    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    /**
     * Set the localization resource bundle.
     *
     * <p>
     *  设置本地化资源包。
     * 
     * 
     * @param bundle  localization bundle (may be null)
     */
    public void setResourceBundle(ResourceBundle bundle) {
        resourceBundle = bundle;
    }

    /**
     * Get the localization resource bundle name
     * <p>
     * This is the name for the ResourceBundle that should be
     * used to localize the message string before formatting it.
     * The result may be null if the message is not localizable.
     * <p>
     *  获取本地化资源包名称
     * <p>
     *  这是ResourceBundle的名称,应在格式化之前用于本地化消息字符串。如果消息不可本地化,则结果可以为null。
     * 
     * 
     * @return the localization resource bundle name
     */
    public String getResourceBundleName() {
        return resourceBundleName;
    }

    /**
     * Set the localization resource bundle name.
     *
     * <p>
     *  设置本地化资源包名称。
     * 
     * 
     * @param name  localization bundle name (may be null)
     */
    public void setResourceBundleName(String name) {
        resourceBundleName = name;
    }

    /**
     * Get the logging message level, for example Level.SEVERE.
     * <p>
     *  获取日志消息级别,例如Level.SEVERE。
     * 
     * 
     * @return the logging message level
     */
    public Level getLevel() {
        return level;
    }

    /**
     * Set the logging message level, for example Level.SEVERE.
     * <p>
     * 设置日志消息级别,例如Level.SEVERE。
     * 
     * 
     * @param level the logging message level
     */
    public void setLevel(Level level) {
        if (level == null) {
            throw new NullPointerException();
        }
        this.level = level;
    }

    /**
     * Get the sequence number.
     * <p>
     * Sequence numbers are normally assigned in the LogRecord
     * constructor, which assigns unique sequence numbers to
     * each new LogRecord in increasing order.
     * <p>
     *  获取序列号。
     * <p>
     *  序列号通常在LogRecord构造函数中分配,它以递增的顺序为每个新的LogRecord分配唯一的序列号。
     * 
     * 
     * @return the sequence number
     */
    public long getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Set the sequence number.
     * <p>
     * Sequence numbers are normally assigned in the LogRecord constructor,
     * so it should not normally be necessary to use this method.
     * <p>
     *  设置序列号。
     * <p>
     *  序列号通常在LogRecord构造函数中分配,因此通常不需要使用此方法。
     * 
     * 
     * @param seq the sequence number
     */
    public void setSequenceNumber(long seq) {
        sequenceNumber = seq;
    }

    /**
     * Get the  name of the class that (allegedly) issued the logging request.
     * <p>
     * Note that this sourceClassName is not verified and may be spoofed.
     * This information may either have been provided as part of the
     * logging call, or it may have been inferred automatically by the
     * logging framework.  In the latter case, the information may only
     * be approximate and may in fact describe an earlier call on the
     * stack frame.
     * <p>
     * May be null if no information could be obtained.
     *
     * <p>
     *  获取(据称)发出日志记录请求的类的名称。
     * <p>
     *  请注意,此sourceClassName未经验证,可能会被欺骗。此信息可能已作为日志记录调用的一部分提供,或者可能已由日志记录框架自动推断。
     * 在后一种情况下,信息可以仅是近似的,并且实际上可以描述对栈帧的较早调用。
     * <p>
     *  如果无法获取信息,可以为null。
     * 
     * 
     * @return the source class name
     */
    public String getSourceClassName() {
        if (needToInferCaller) {
            inferCaller();
        }
        return sourceClassName;
    }

    /**
     * Set the name of the class that (allegedly) issued the logging request.
     *
     * <p>
     *  设置(据称)发出日志记录请求的类的名称。
     * 
     * 
     * @param sourceClassName the source class name (may be null)
     */
    public void setSourceClassName(String sourceClassName) {
        this.sourceClassName = sourceClassName;
        needToInferCaller = false;
    }

    /**
     * Get the  name of the method that (allegedly) issued the logging request.
     * <p>
     * Note that this sourceMethodName is not verified and may be spoofed.
     * This information may either have been provided as part of the
     * logging call, or it may have been inferred automatically by the
     * logging framework.  In the latter case, the information may only
     * be approximate and may in fact describe an earlier call on the
     * stack frame.
     * <p>
     * May be null if no information could be obtained.
     *
     * <p>
     *  获取(据称)发出日志记录请求的方法的名称。
     * <p>
     *  请注意,此sourceMethodName未经验证,可能会被欺骗。此信息可能已作为日志记录调用的一部分提供,或者可能已由日志记录框架自动推断。
     * 在后一种情况下,信息可以仅是近似的,并且实际上可以描述对栈帧的较早调用。
     * <p>
     *  如果无法获取信息,可以为null。
     * 
     * 
     * @return the source method name
     */
    public String getSourceMethodName() {
        if (needToInferCaller) {
            inferCaller();
        }
        return sourceMethodName;
    }

    /**
     * Set the name of the method that (allegedly) issued the logging request.
     *
     * <p>
     *  设置(据称)发出日志记录请求的方法的名称。
     * 
     * 
     * @param sourceMethodName the source method name (may be null)
     */
    public void setSourceMethodName(String sourceMethodName) {
        this.sourceMethodName = sourceMethodName;
        needToInferCaller = false;
    }

    /**
     * Get the "raw" log message, before localization or formatting.
     * <p>
     * May be null, which is equivalent to the empty string "".
     * <p>
     * This message may be either the final text or a localization key.
     * <p>
     * During formatting, if the source logger has a localization
     * ResourceBundle and if that ResourceBundle has an entry for
     * this message string, then the message string is replaced
     * with the localized value.
     *
     * <p>
     * 在本地化或格式化之前获取"原始"日志消息。
     * <p>
     *  可以为null,这相当于空字符串""。
     * <p>
     *  此消息可以是最终文本或本地化键。
     * <p>
     *  在格式化期间,如果源记录器具有本地化ResourceBundle,并且如果ResourceBundle有此消息字符串的条目,则消息字符串将替换为本地化值。
     * 
     * 
     * @return the raw message string
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the "raw" log message, before localization or formatting.
     *
     * <p>
     *  在本地化或格式化之前设置"原始"日志消息。
     * 
     * 
     * @param message the raw message string (may be null)
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Get the parameters to the log message.
     *
     * <p>
     *  获取日志消息的参数。
     * 
     * 
     * @return the log message parameters.  May be null if
     *                  there are no parameters.
     */
    public Object[] getParameters() {
        return parameters;
    }

    /**
     * Set the parameters to the log message.
     *
     * <p>
     *  将参数设置为日志消息。
     * 
     * 
     * @param parameters the log message parameters. (may be null)
     */
    public void setParameters(Object parameters[]) {
        this.parameters = parameters;
    }

    /**
     * Get an identifier for the thread where the message originated.
     * <p>
     * This is a thread identifier within the Java VM and may or
     * may not map to any operating system ID.
     *
     * <p>
     *  获取消息产生的线程的标识符。
     * <p>
     *  这是Java VM中的线程标识符,可以映射也可以不映射到任何操作系统ID。
     * 
     * 
     * @return thread ID
     */
    public int getThreadID() {
        return threadID;
    }

    /**
     * Set an identifier for the thread where the message originated.
     * <p>
     *  设置消息发起的线程的标识符。
     * 
     * 
     * @param threadID  the thread ID
     */
    public void setThreadID(int threadID) {
        this.threadID = threadID;
    }

    /**
     * Get event time in milliseconds since 1970.
     *
     * <p>
     *  从1970年以来以毫秒为单位获取事件时间。
     * 
     * 
     * @return event time in millis since 1970
     */
    public long getMillis() {
        return millis;
    }

    /**
     * Set event time.
     *
     * <p>
     *  设置事件时间。
     * 
     * 
     * @param millis event time in millis since 1970
     */
    public void setMillis(long millis) {
        this.millis = millis;
    }

    /**
     * Get any throwable associated with the log record.
     * <p>
     * If the event involved an exception, this will be the
     * exception object. Otherwise null.
     *
     * <p>
     *  获取与日志记录相关联的任何throwable。
     * <p>
     *  如果事件涉及异常,这将是异常对象。否则为null。
     * 
     * 
     * @return a throwable
     */
    public Throwable getThrown() {
        return thrown;
    }

    /**
     * Set a throwable associated with the log event.
     *
     * <p>
     * 
     * @param thrown  a throwable (may be null)
     */
    public void setThrown(Throwable thrown) {
        this.thrown = thrown;
    }

    private static final long serialVersionUID = 5372048053134512534L;

    /**
    /* <p>
    /*  设置与日志事件关联的throwable。
    /* 
    /* 
     * @serialData Default fields, followed by a two byte version number
     * (major byte, followed by minor byte), followed by information on
     * the log record parameter array.  If there is no parameter array,
     * then -1 is written.  If there is a parameter array (possible of zero
     * length) then the array length is written as an integer, followed
     * by String values for each parameter.  If a parameter is null, then
     * a null String is written.  Otherwise the output of Object.toString()
     * is written.
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        // We have to call defaultWriteObject first.
        out.defaultWriteObject();

        // Write our version number.
        out.writeByte(1);
        out.writeByte(0);
        if (parameters == null) {
            out.writeInt(-1);
            return;
        }
        out.writeInt(parameters.length);
        // Write string values for the parameters.
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i] == null) {
                out.writeObject(null);
            } else {
                out.writeObject(parameters[i].toString());
            }
        }
    }

    private void readObject(ObjectInputStream in)
                        throws IOException, ClassNotFoundException {
        // We have to call defaultReadObject first.
        in.defaultReadObject();

        // Read version number.
        byte major = in.readByte();
        byte minor = in.readByte();
        if (major != 1) {
            throw new IOException("LogRecord: bad version: " + major + "." + minor);
        }
        int len = in.readInt();
        if (len == -1) {
            parameters = null;
        } else {
            parameters = new Object[len];
            for (int i = 0; i < parameters.length; i++) {
                parameters[i] = in.readObject();
            }
        }
        // If necessary, try to regenerate the resource bundle.
        if (resourceBundleName != null) {
            try {
                // use system class loader to ensure the ResourceBundle
                // instance is a different instance than null loader uses
                final ResourceBundle bundle =
                        ResourceBundle.getBundle(resourceBundleName,
                                Locale.getDefault(),
                                ClassLoader.getSystemClassLoader());
                resourceBundle = bundle;
            } catch (MissingResourceException ex) {
                // This is not a good place to throw an exception,
                // so we simply leave the resourceBundle null.
                resourceBundle = null;
            }
        }

        needToInferCaller = false;
    }

    // Private method to infer the caller's class and method names
    private void inferCaller() {
        needToInferCaller = false;
        JavaLangAccess access = SharedSecrets.getJavaLangAccess();
        Throwable throwable = new Throwable();
        int depth = access.getStackTraceDepth(throwable);

        boolean lookingForLogger = true;
        for (int ix = 0; ix < depth; ix++) {
            // Calling getStackTraceElement directly prevents the VM
            // from paying the cost of building the entire stack frame.
            StackTraceElement frame =
                access.getStackTraceElement(throwable, ix);
            String cname = frame.getClassName();
            boolean isLoggerImpl = isLoggerImplFrame(cname);
            if (lookingForLogger) {
                // Skip all frames until we have found the first logger frame.
                if (isLoggerImpl) {
                    lookingForLogger = false;
                }
            } else {
                if (!isLoggerImpl) {
                    // skip reflection call
                    if (!cname.startsWith("java.lang.reflect.") && !cname.startsWith("sun.reflect.")) {
                       // We've found the relevant frame.
                       setSourceClassName(cname);
                       setSourceMethodName(frame.getMethodName());
                       return;
                    }
                }
            }
        }
        // We haven't found a suitable frame, so just punt.  This is
        // OK as we are only committed to making a "best effort" here.
    }

    private boolean isLoggerImplFrame(String cname) {
        // the log record could be created for a platform logger
        return (cname.equals("java.util.logging.Logger") ||
                cname.startsWith("java.util.logging.LoggingProxyImpl") ||
                cname.startsWith("sun.util.logging."));
    }
}
