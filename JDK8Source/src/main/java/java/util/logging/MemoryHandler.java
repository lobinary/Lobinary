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

/**
 * <tt>Handler</tt> that buffers requests in a circular buffer in memory.
 * <p>
 * Normally this <tt>Handler</tt> simply stores incoming <tt>LogRecords</tt>
 * into its memory buffer and discards earlier records.  This buffering
 * is very cheap and avoids formatting costs.  On certain trigger
 * conditions, the <tt>MemoryHandler</tt> will push out its current buffer
 * contents to a target <tt>Handler</tt>, which will typically publish
 * them to the outside world.
 * <p>
 * There are three main models for triggering a push of the buffer:
 * <ul>
 * <li>
 * An incoming <tt>LogRecord</tt> has a type that is greater than
 * a pre-defined level, the <tt>pushLevel</tt>. </li>
 * <li>
 * An external class calls the <tt>push</tt> method explicitly. </li>
 * <li>
 * A subclass overrides the <tt>log</tt> method and scans each incoming
 * <tt>LogRecord</tt> and calls <tt>push</tt> if a record matches some
 * desired criteria. </li>
 * </ul>
 * <p>
 * <b>Configuration:</b>
 * By default each <tt>MemoryHandler</tt> is initialized using the following
 * <tt>LogManager</tt> configuration properties where <tt>&lt;handler-name&gt;</tt>
 * refers to the fully-qualified class name of the handler.
 * If properties are not defined
 * (or have invalid values) then the specified default values are used.
 * If no default value is defined then a RuntimeException is thrown.
 * <ul>
 * <li>   &lt;handler-name&gt;.level
 *        specifies the level for the <tt>Handler</tt>
 *        (defaults to <tt>Level.ALL</tt>). </li>
 * <li>   &lt;handler-name&gt;.filter
 *        specifies the name of a <tt>Filter</tt> class to use
 *        (defaults to no <tt>Filter</tt>). </li>
 * <li>   &lt;handler-name&gt;.size
 *        defines the buffer size (defaults to 1000). </li>
 * <li>   &lt;handler-name&gt;.push
 *        defines the <tt>pushLevel</tt> (defaults to <tt>level.SEVERE</tt>). </li>
 * <li>   &lt;handler-name&gt;.target
 *        specifies the name of the target <tt>Handler </tt> class.
 *        (no default). </li>
 * </ul>
 * <p>
 * For example, the properties for {@code MemoryHandler} would be:
 * <ul>
 * <li>   java.util.logging.MemoryHandler.level=INFO </li>
 * <li>   java.util.logging.MemoryHandler.formatter=java.util.logging.SimpleFormatter </li>
 * </ul>
 * <p>
 * For a custom handler, e.g. com.foo.MyHandler, the properties would be:
 * <ul>
 * <li>   com.foo.MyHandler.level=INFO </li>
 * <li>   com.foo.MyHandler.formatter=java.util.logging.SimpleFormatter </li>
 * </ul>
 * <p>
 * <p>
 *  <tt>处理程序</tt>,用于在内存中的循环缓冲区中缓冲请求。
 * <p>
 *  通常,此<tt>处理程序</tt>只是将传入的<tt> LogRecords </tt>存储到其内存缓冲区中,并丢弃更早的记录。这种缓冲非常便宜,并且避免了格式化成本。
 * 在某些触发条件下,<tt> MemoryHandler </tt>会将其当前缓冲区内容推送到目标<tt>处理程序</tt>,通常将其发布到外部世界。
 * <p>
 *  有三种主要模型用于触发缓冲区的推送：
 * <ul>
 * <li>
 *  传入的<tt> LogRecord </tt>的类型大于预定义级别<tt> pushLevel </tt>。 </li>
 * <li>
 *  外部类显式调用<tt> push </tt>方法。 </li>
 * <li>
 *  子类覆盖<tt> log </tt>方法,并扫描每个传入的<tt> LogRecord </tt>并调用<tt> push </tt> </li>
 * </ul>
 * <p>
 *  <b>配置：</b>默认情况下,每个<tt> MemoryHandler </tt>都使用以下<tt> LogManager </tt>配置属性进行初始化,其中<tt>&lt; handler-nam
 * e&gt; </tt>到处理程序的完全限定类名。
 * 如果未定义属性(或具有无效值),则使用指定的默认值。如果没有定义默认值,那么抛出RuntimeException。
 * <ul>
 * <li>&lt; handler-name&gt; .level指定<tt>处理程序</tt>(默认为<tt> Level.ALL </tt>)的级别。
 *  </li> <li>&lt; handler-name&gt; .filter指定要使用的<tt> Filter </tt>类的名称(默认为<tt> Filter </tt>)。
 *  </li> <li>&lt; handler-name&gt; .size定义了缓冲区大小(默认为1000)。
 *  </li> <li>&lt; handler-name&gt; .push定义<tt> pushLevel </tt>(默认为<tt> level.SEVERE </tt>)。
 *  </li> <li>&lt; handler-name&gt; .target指定目标<tt>处理程序</tt>类的名称。 (无默认值)。 </li>。
 * </ul>
 * <p>
 *  例如,{@code MemoryHandler}的属性将是：
 * <ul>
 *  <li> java.util.logging.MemoryHandler.level = INFO </li> <li> java.util.logging.MemoryHandler.formatt
 * er = java.util.logging.SimpleFormatter </li>。
 * </ul>
 * <p>
 *  对于自定义处理程序,例如com.foo.MyHandler,属性将是：
 * <ul>
 *  <li> com.foo.MyHandler.level = INFO </li> <li> com.foo.MyHandler.formatter = java.util.logging.Simpl
 * eFormatter </li>。
 * </ul>
 * <p>
 * 
 * @since 1.4
 */

public class MemoryHandler extends Handler {
    private final static int DEFAULT_SIZE = 1000;
    private volatile Level pushLevel;
    private int size;
    private Handler target;
    private LogRecord buffer[];
    int start, count;

    // Private method to configure a MemoryHandler from LogManager
    // properties and/or default values as specified in the class
    // javadoc.
    private void configure() {
        LogManager manager = LogManager.getLogManager();
        String cname = getClass().getName();

        pushLevel = manager.getLevelProperty(cname +".push", Level.SEVERE);
        size = manager.getIntProperty(cname + ".size", DEFAULT_SIZE);
        if (size <= 0) {
            size = DEFAULT_SIZE;
        }
        setLevel(manager.getLevelProperty(cname +".level", Level.ALL));
        setFilter(manager.getFilterProperty(cname +".filter", null));
        setFormatter(manager.getFormatterProperty(cname +".formatter", new SimpleFormatter()));
    }

    /**
     * Create a <tt>MemoryHandler</tt> and configure it based on
     * <tt>LogManager</tt> configuration properties.
     * <p>
     *  创建<tt> MemoryHandler </tt>并根据<tt> LogManager </tt>配置属性进行配置。
     * 
     */
    public MemoryHandler() {
        sealed = false;
        configure();
        sealed = true;

        LogManager manager = LogManager.getLogManager();
        String handlerName = getClass().getName();
        String targetName = manager.getProperty(handlerName+".target");
        if (targetName == null) {
            throw new RuntimeException("The handler " + handlerName
                    + " does not specify a target");
        }
        Class<?> clz;
        try {
            clz = ClassLoader.getSystemClassLoader().loadClass(targetName);
            target = (Handler) clz.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("MemoryHandler can't load handler target \"" + targetName + "\"" , e);
        }
        init();
    }

    // Initialize.  Size is a count of LogRecords.
    private void init() {
        buffer = new LogRecord[size];
        start = 0;
        count = 0;
    }

    /**
     * Create a <tt>MemoryHandler</tt>.
     * <p>
     * The <tt>MemoryHandler</tt> is configured based on <tt>LogManager</tt>
     * properties (or their default values) except that the given <tt>pushLevel</tt>
     * argument and buffer size argument are used.
     *
     * <p>
     *  创建<tt> MemoryHandler </tt>。
     * <p>
     *  除了使用给定的<tt> pushLevel </tt>参数和缓冲区大小参数,<tt> MemoryHandler </tt>是基于<tt> LogManager </tt>属性(或其默认值)配置的。
     * 
     * 
     * @param target  the Handler to which to publish output.
     * @param size    the number of log records to buffer (must be greater than zero)
     * @param pushLevel  message level to push on
     *
     * @throws IllegalArgumentException if {@code size is <= 0}
     */
    public MemoryHandler(Handler target, int size, Level pushLevel) {
        if (target == null || pushLevel == null) {
            throw new NullPointerException();
        }
        if (size <= 0) {
            throw new IllegalArgumentException();
        }
        sealed = false;
        configure();
        sealed = true;
        this.target = target;
        this.pushLevel = pushLevel;
        this.size = size;
        init();
    }

    /**
     * Store a <tt>LogRecord</tt> in an internal buffer.
     * <p>
     * If there is a <tt>Filter</tt>, its <tt>isLoggable</tt>
     * method is called to check if the given log record is loggable.
     * If not we return.  Otherwise the given record is copied into
     * an internal circular buffer.  Then the record's level property is
     * compared with the <tt>pushLevel</tt>. If the given level is
     * greater than or equal to the <tt>pushLevel</tt> then <tt>push</tt>
     * is called to write all buffered records to the target output
     * <tt>Handler</tt>.
     *
     * <p>
     *  在内部缓冲区中存储<tt> LogRecord </tt>。
     * <p>
     * 如果有<tt>过滤器</tt>,则会调用<tt> isLoggable </tt>方法来检查给定的日志记录是否可以记录。如果不是我们回来。否则,给定的记录被复制到内部循环缓冲器中。
     * 然后将记录的level属性与<tt> pushLevel </tt>进行比较。
     * 如果给定级别大于或等于<tt> pushLevel </tt>,则调用<tt> push </tt>将所有缓冲的记录写入目标输出<tt> Handler </tt>。
     * 
     * 
     * @param  record  description of the log event. A null record is
     *                 silently ignored and is not published
     */
    @Override
    public synchronized void publish(LogRecord record) {
        if (!isLoggable(record)) {
            return;
        }
        int ix = (start+count)%buffer.length;
        buffer[ix] = record;
        if (count < buffer.length) {
            count++;
        } else {
            start++;
            start %= buffer.length;
        }
        if (record.getLevel().intValue() >= pushLevel.intValue()) {
            push();
        }
    }

    /**
     * Push any buffered output to the target <tt>Handler</tt>.
     * <p>
     * The buffer is then cleared.
     * <p>
     *  将任何缓冲输出推送到目标<tt>处理程序</tt>。
     * <p>
     *  然后清除缓冲区。
     * 
     */
    public synchronized void push() {
        for (int i = 0; i < count; i++) {
            int ix = (start+i)%buffer.length;
            LogRecord record = buffer[ix];
            target.publish(record);
        }
        // Empty the buffer.
        start = 0;
        count = 0;
    }

    /**
     * Causes a flush on the target <tt>Handler</tt>.
     * <p>
     * Note that the current contents of the <tt>MemoryHandler</tt>
     * buffer are <b>not</b> written out.  That requires a "push".
     * <p>
     *  导致目标<tt>处理程序</tt>冲刷。
     * <p>
     *  请注意,<tt> MemoryHandler </tt>缓冲区的当前内容为<b>不是</b>。这需要一个"推"。
     * 
     */
    @Override
    public void flush() {
        target.flush();
    }

    /**
     * Close the <tt>Handler</tt> and free all associated resources.
     * This will also close the target <tt>Handler</tt>.
     *
     * <p>
     *  关闭<tt>处理程序</tt>并释放所有相关资源。这也将关闭目标<tt>处理程序</tt>。
     * 
     * 
     * @exception  SecurityException  if a security manager exists and if
     *             the caller does not have <tt>LoggingPermission("control")</tt>.
     */
    @Override
    public void close() throws SecurityException {
        target.close();
        setLevel(Level.OFF);
    }

    /**
     * Set the <tt>pushLevel</tt>.  After a <tt>LogRecord</tt> is copied
     * into our internal buffer, if its level is greater than or equal to
     * the <tt>pushLevel</tt>, then <tt>push</tt> will be called.
     *
     * <p>
     *  设置<tt> pushLevel </tt>。
     * 将<tt> LogRecord </tt>复制到内部缓冲区后,如果其级别大于或等于<tt> pushLevel </tt>,则将调用<tt> push </tt>。
     * 
     * 
     * @param newLevel the new value of the <tt>pushLevel</tt>
     * @exception  SecurityException  if a security manager exists and if
     *             the caller does not have <tt>LoggingPermission("control")</tt>.
     */
    public synchronized void setPushLevel(Level newLevel) throws SecurityException {
        if (newLevel == null) {
            throw new NullPointerException();
        }
        checkPermission();
        pushLevel = newLevel;
    }

    /**
     * Get the <tt>pushLevel</tt>.
     *
     * <p>
     *  获取<tt> pushLevel </tt>。
     * 
     * 
     * @return the value of the <tt>pushLevel</tt>
     */
    public Level getPushLevel() {
        return pushLevel;
    }

    /**
     * Check if this <tt>Handler</tt> would actually log a given
     * <tt>LogRecord</tt> into its internal buffer.
     * <p>
     * This method checks if the <tt>LogRecord</tt> has an appropriate level and
     * whether it satisfies any <tt>Filter</tt>.  However it does <b>not</b>
     * check whether the <tt>LogRecord</tt> would result in a "push" of the
     * buffer contents. It will return false if the <tt>LogRecord</tt> is null.
     * <p>
     * <p>
     *  检查此<tt>处理程序</tt>是否会将给定的<tt> LogRecord </tt>记录到其内部缓冲区中。
     * <p>
     *  此方法检查<tt> LogRecord </tt>是否具有适当的级别,以及是否满足任何<tt>过滤器</tt>。
     * 
     * @param record  a <tt>LogRecord</tt>
     * @return true if the <tt>LogRecord</tt> would be logged.
     *
     */
    @Override
    public boolean isLoggable(LogRecord record) {
        return super.isLoggable(record);
    }
}
