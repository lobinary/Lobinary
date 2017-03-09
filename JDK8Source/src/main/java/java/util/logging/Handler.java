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

import java.io.UnsupportedEncodingException;
/**
 * A <tt>Handler</tt> object takes log messages from a <tt>Logger</tt> and
 * exports them.  It might for example, write them to a console
 * or write them to a file, or send them to a network logging service,
 * or forward them to an OS log, or whatever.
 * <p>
 * A <tt>Handler</tt> can be disabled by doing a <tt>setLevel(Level.OFF)</tt>
 * and can  be re-enabled by doing a <tt>setLevel</tt> with an appropriate level.
 * <p>
 * <tt>Handler</tt> classes typically use <tt>LogManager</tt> properties to set
 * default values for the <tt>Handler</tt>'s <tt>Filter</tt>, <tt>Formatter</tt>,
 * and <tt>Level</tt>.  See the specific documentation for each concrete
 * <tt>Handler</tt> class.
 *
 *
 * <p>
 *  <tt>处理程序</tt>对象从<tt> Logger </tt>接收日志消息,并将其导出。
 * 例如,可以将它们写入控制台或将其写入文件,或将其发送到网络日志记录服务,或将它们转发到操作系统日志或其他任何文件。
 * <p>
 *  通过执行<tt> setLevel(Level.OFF)</tt>可以禁用<tt>处理程序</tt>,并可以通过在适当级别执行<tt> setLevel </tt>来重新启用。
 * <p>
 *  <tt> Handler </tt>类通常使用<tt> LogManager </tt>属性为<tt>处理程序</tt>的<tt>过滤器</tt> / tt>和<tt>级别</tt>。
 * 请参阅每个具体<tt> Handler </tt>类的具体文档。
 * 
 * 
 * @since 1.4
 */

public abstract class Handler {
    private static final int offValue = Level.OFF.intValue();
    private final LogManager manager = LogManager.getLogManager();

    // We're using volatile here to avoid synchronizing getters, which
    // would prevent other threads from calling isLoggable()
    // while publish() is executing.
    // On the other hand, setters will be synchronized to exclude concurrent
    // execution with more complex methods, such as StreamHandler.publish().
    // We wouldn't want 'level' to be changed by another thread in the middle
    // of the execution of a 'publish' call.
    private volatile Filter filter;
    private volatile Formatter formatter;
    private volatile Level logLevel = Level.ALL;
    private volatile ErrorManager errorManager = new ErrorManager();
    private volatile String encoding;

    // Package private support for security checking.  When sealed
    // is true, we access check updates to the class.
    boolean sealed = true;

    /**
     * Default constructor.  The resulting <tt>Handler</tt> has a log
     * level of <tt>Level.ALL</tt>, no <tt>Formatter</tt>, and no
     * <tt>Filter</tt>.  A default <tt>ErrorManager</tt> instance is installed
     * as the <tt>ErrorManager</tt>.
     * <p>
     *  默认构造函数。生成的<tt>处理程序</tt>的日志级别为<tt> Level.ALL </tt>,无<tt>格式化程序</tt>,且没有<tt>过滤器</tt>。
     * 默认的<tt> ErrorManager </tt>实例安装为<tt> ErrorManager </tt>。
     * 
     */
    protected Handler() {
    }

    /**
     * Publish a <tt>LogRecord</tt>.
     * <p>
     * The logging request was made initially to a <tt>Logger</tt> object,
     * which initialized the <tt>LogRecord</tt> and forwarded it here.
     * <p>
     * The <tt>Handler</tt>  is responsible for formatting the message, when and
     * if necessary.  The formatting should include localization.
     *
     * <p>
     *  发布<tt> LogRecord </tt>。
     * <p>
     *  记录请求最初发送到一个<tt> Logger </tt>对象,该对象初始化了<tt> LogRecord </tt>并在此处转发。
     * <p>
     *  <tt>处理程序</tt>负责在必要时格式化邮件。格式化应包括本地化。
     * 
     * 
     * @param  record  description of the log event. A null record is
     *                 silently ignored and is not published
     */
    public abstract void publish(LogRecord record);

    /**
     * Flush any buffered output.
     * <p>
     *  清除任何缓冲输出。
     * 
     */
    public abstract void flush();

    /**
     * Close the <tt>Handler</tt> and free all associated resources.
     * <p>
     * The close method will perform a <tt>flush</tt> and then close the
     * <tt>Handler</tt>.   After close has been called this <tt>Handler</tt>
     * should no longer be used.  Method calls may either be silently
     * ignored or may throw runtime exceptions.
     *
     * <p>
     *  关闭<tt>处理程序</tt>并释放所有相关资源。
     * <p>
     * close方法将执行<tt> flush </tt>,然后关闭<tt>处理程序</tt>。在关闭之后,这个<tt>处理程序</tt>应该不再使用。方法调用可能会被静默忽略或抛出运行时异常。
     * 
     * 
     * @exception  SecurityException  if a security manager exists and if
     *             the caller does not have <tt>LoggingPermission("control")</tt>.
     */
    public abstract void close() throws SecurityException;

    /**
     * Set a <tt>Formatter</tt>.  This <tt>Formatter</tt> will be used
     * to format <tt>LogRecords</tt> for this <tt>Handler</tt>.
     * <p>
     * Some <tt>Handlers</tt> may not use <tt>Formatters</tt>, in
     * which case the <tt>Formatter</tt> will be remembered, but not used.
     * <p>
     * <p>
     *  设置<tt>格式化程序</tt>。此<tt>格式化程序</tt>将用于格式化此<tt>处理程序</tt>的<tt> LogRecords </tt>。
     * <p>
     *  某些<tt>处理程序</tt>不能使用<tt>格式化程序</tt>,在这种情况下,<tt>格式化程序</tt>将被记住,但不会使用。
     * <p>
     * 
     * @param newFormatter the <tt>Formatter</tt> to use (may not be null)
     * @exception  SecurityException  if a security manager exists and if
     *             the caller does not have <tt>LoggingPermission("control")</tt>.
     */
    public synchronized void setFormatter(Formatter newFormatter) throws SecurityException {
        checkPermission();
        // Check for a null pointer:
        newFormatter.getClass();
        formatter = newFormatter;
    }

    /**
     * Return the <tt>Formatter</tt> for this <tt>Handler</tt>.
     * <p>
     *  返回此<tt>处理程序</tt>的<tt>格式化程序</tt>。
     * 
     * 
     * @return the <tt>Formatter</tt> (may be null).
     */
    public Formatter getFormatter() {
        return formatter;
    }

    /**
     * Set the character encoding used by this <tt>Handler</tt>.
     * <p>
     * The encoding should be set before any <tt>LogRecords</tt> are written
     * to the <tt>Handler</tt>.
     *
     * <p>
     *  设置此<tt>处理程序</tt>使用的字符编码。
     * <p>
     *  应在任何<tt> LogRecords </tt>写入<tt>处理程序</tt>之前设置编码。
     * 
     * 
     * @param encoding  The name of a supported character encoding.
     *        May be null, to indicate the default platform encoding.
     * @exception  SecurityException  if a security manager exists and if
     *             the caller does not have <tt>LoggingPermission("control")</tt>.
     * @exception  UnsupportedEncodingException if the named encoding is
     *          not supported.
     */
    public synchronized void setEncoding(String encoding)
                        throws SecurityException, java.io.UnsupportedEncodingException {
        checkPermission();
        if (encoding != null) {
            try {
                if(!java.nio.charset.Charset.isSupported(encoding)) {
                    throw new UnsupportedEncodingException(encoding);
                }
            } catch (java.nio.charset.IllegalCharsetNameException e) {
                throw new UnsupportedEncodingException(encoding);
            }
        }
        this.encoding = encoding;
    }

    /**
     * Return the character encoding for this <tt>Handler</tt>.
     *
     * <p>
     *  返回此<tt>处理程序</tt>的字符编码。
     * 
     * 
     * @return  The encoding name.  May be null, which indicates the
     *          default encoding should be used.
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * Set a <tt>Filter</tt> to control output on this <tt>Handler</tt>.
     * <P>
     * For each call of <tt>publish</tt> the <tt>Handler</tt> will call
     * this <tt>Filter</tt> (if it is non-null) to check if the
     * <tt>LogRecord</tt> should be published or discarded.
     *
     * <p>
     *  设置<tt>过滤器</tt>可控制此<tt>处理程序</tt>上的输出。
     * <P>
     *  对于<tt> publish </tt>的每个调用,<tt>处理程序</tt>将调用<tt>过滤器</tt>(如果它为非null),以检查<tt> LogRecord < tt>应该被发布或丢弃。
     * 
     * 
     * @param   newFilter  a <tt>Filter</tt> object (may be null)
     * @exception  SecurityException  if a security manager exists and if
     *             the caller does not have <tt>LoggingPermission("control")</tt>.
     */
    public synchronized void setFilter(Filter newFilter) throws SecurityException {
        checkPermission();
        filter = newFilter;
    }

    /**
     * Get the current <tt>Filter</tt> for this <tt>Handler</tt>.
     *
     * <p>
     *  获取此<tt>处理程序</tt>的当前<tt>过滤器</tt>。
     * 
     * 
     * @return  a <tt>Filter</tt> object (may be null)
     */
    public Filter getFilter() {
        return filter;
    }

    /**
     * Define an ErrorManager for this Handler.
     * <p>
     * The ErrorManager's "error" method will be invoked if any
     * errors occur while using this Handler.
     *
     * <p>
     *  为此处理程序定义一个ErrorManager。
     * <p>
     *  如果在使用此处理程序时发生任何错误,将调用ErrorManager的"错误"方法。
     * 
     * 
     * @param em  the new ErrorManager
     * @exception  SecurityException  if a security manager exists and if
     *             the caller does not have <tt>LoggingPermission("control")</tt>.
     */
    public synchronized void setErrorManager(ErrorManager em) {
        checkPermission();
        if (em == null) {
           throw new NullPointerException();
        }
        errorManager = em;
    }

    /**
     * Retrieves the ErrorManager for this Handler.
     *
     * <p>
     *  检索此处理程序的ErrorManager。
     * 
     * 
     * @return the ErrorManager for this Handler
     * @exception  SecurityException  if a security manager exists and if
     *             the caller does not have <tt>LoggingPermission("control")</tt>.
     */
    public ErrorManager getErrorManager() {
        checkPermission();
        return errorManager;
    }

   /**
     * Protected convenience method to report an error to this Handler's
     * ErrorManager.  Note that this method retrieves and uses the ErrorManager
     * without doing a security check.  It can therefore be used in
     * environments where the caller may be non-privileged.
     *
     * <p>
     * 受保护的方便方法向此处理程序的错误管理器报告错误。请注意,此方法检索并使用ErrorManager,而不进行安全检查。因此,它可以在调用者可以是非特权的环境中使用。
     * 
     * 
     * @param msg    a descriptive string (may be null)
     * @param ex     an exception (may be null)
     * @param code   an error code defined in ErrorManager
     */
    protected void reportError(String msg, Exception ex, int code) {
        try {
            errorManager.error(msg, ex, code);
        } catch (Exception ex2) {
            System.err.println("Handler.reportError caught:");
            ex2.printStackTrace();
        }
    }

    /**
     * Set the log level specifying which message levels will be
     * logged by this <tt>Handler</tt>.  Message levels lower than this
     * value will be discarded.
     * <p>
     * The intention is to allow developers to turn on voluminous
     * logging, but to limit the messages that are sent to certain
     * <tt>Handlers</tt>.
     *
     * <p>
     *  设置日志级别,指定此<tt>处理程序</tt>将记录哪些消息级别。低于此值的邮件级别将被丢弃。
     * <p>
     *  目的是允许开发人员打开大量日志记录,但限制发送到某些<tt>处理程序</tt>的消息。
     * 
     * 
     * @param newLevel   the new value for the log level
     * @exception  SecurityException  if a security manager exists and if
     *             the caller does not have <tt>LoggingPermission("control")</tt>.
     */
    public synchronized void setLevel(Level newLevel) throws SecurityException {
        if (newLevel == null) {
            throw new NullPointerException();
        }
        checkPermission();
        logLevel = newLevel;
    }

    /**
     * Get the log level specifying which messages will be
     * logged by this <tt>Handler</tt>.  Message levels lower
     * than this level will be discarded.
     * <p>
     *  获取日志级别,指定此<tt>处理程序</tt>将记录哪些消息。低于此级别的邮件级别将被丢弃。
     * 
     * 
     * @return  the level of messages being logged.
     */
    public Level getLevel() {
        return logLevel;
    }

    /**
     * Check if this <tt>Handler</tt> would actually log a given <tt>LogRecord</tt>.
     * <p>
     * This method checks if the <tt>LogRecord</tt> has an appropriate
     * <tt>Level</tt> and  whether it satisfies any <tt>Filter</tt>.  It also
     * may make other <tt>Handler</tt> specific checks that might prevent a
     * handler from logging the <tt>LogRecord</tt>. It will return false if
     * the <tt>LogRecord</tt> is null.
     * <p>
     * <p>
     *  检查此<tt>处理程序</tt>是否实际记录给定的<tt> LogRecord </tt>。
     * <p>
     *  此方法检查<tt> LogRecord </tt>是否具有适当的<tt>级别</tt>,以及是否满足任何<tt>过滤器</tt>。
     * 
     * @param record  a <tt>LogRecord</tt>
     * @return true if the <tt>LogRecord</tt> would be logged.
     *
     */
    public boolean isLoggable(LogRecord record) {
        final int levelValue = getLevel().intValue();
        if (record.getLevel().intValue() < levelValue || levelValue == offValue) {
            return false;
        }
        final Filter filter = getFilter();
        if (filter == null) {
            return true;
        }
        return filter.isLoggable(record);
    }

    // Package-private support method for security checks.
    // If "sealed" is true, we check that the caller has
    // appropriate security privileges to update Handler
    // state and if not throw a SecurityException.
    void checkPermission() throws SecurityException {
        if (sealed) {
            manager.checkPermission();
        }
    }
}
