/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2011, Oracle and/or its affiliates. All rights reserved.
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
 * The management interface for the logging facility. It is recommended
 * to use the {@link java.lang.management.PlatformLoggingMXBean} management
 * interface that implements all attributes defined in this
 * {@code LoggingMXBean}.  The
 * {@link java.lang.management.ManagementFactory#getPlatformMXBean(Class)
 * ManagementFactory.getPlatformMXBean} method can be used to obtain
 * the {@code PlatformLoggingMXBean} object representing the management
 * interface for logging.
 *
 * <p>There is a single global instance of the <tt>LoggingMXBean</tt>.
 * This instance is an {@link javax.management.MXBean MXBean} that
 * can be obtained by calling the {@link LogManager#getLoggingMXBean}
 * method or from the
 * {@linkplain java.lang.management.ManagementFactory#getPlatformMBeanServer
 * platform <tt>MBeanServer</tt>}.
 * <p>
 * The {@link javax.management.ObjectName ObjectName} that uniquely identifies
 * the management interface for logging within the {@code MBeanServer} is:
 * <pre>
 *    {@link LogManager#LOGGING_MXBEAN_NAME java.util.logging:type=Logging}
 * </pre>
 * <p>
 * The instance registered in the platform {@code MBeanServer}
 * is also a {@link java.lang.management.PlatformLoggingMXBean}.
 *
 * <p>
 *  日志记录工具的管理界面。
 * 建议使用{@link java.lang.management.PlatformLoggingMXBean}管理接口,该接口实现此{@code LoggingMXBean}中定义的所有属性。
 *  {@link java.lang.management.ManagementFactory#getPlatformMXBean(Class)ManagementFactory.getPlatformMXBean}
 * 方法可用于获取表示用于日志记录的管理界面的{@code PlatformLoggingMXBean}对象。
 * 建议使用{@link java.lang.management.PlatformLoggingMXBean}管理接口,该接口实现此{@code LoggingMXBean}中定义的所有属性。
 * 
 *  <p> <tt> LoggingMXBean </tt>有一个全局实例。
 * 此实例是{@link javax.management.MXBean MXBean},可以通过调用{@link LogManager#getLoggingMXBean}方法或从{@linkplain java.lang.management.ManagementFactory#getPlatformMBeanServer platform <tt> MBeanServer < / tt>}
 * 。
 *  <p> <tt> LoggingMXBean </tt>有一个全局实例。
 * <p>
 *  唯一标识用于在{@code MBeanServer}中进行日志记录的管理界面的{@link javax.management.ObjectName ObjectName}是：
 * <pre>
 *  {@link LogManager#LOGGING_MXBEAN_NAME java.util.logging：type = Logging}
 * </pre>
 * <p>
 *  在平台{@code MBeanServer}中注册的实例也是一个{@link java.lang.management.PlatformLoggingMXBean}。
 * 
 * 
 * @author  Ron Mann
 * @author  Mandy Chung
 * @since   1.5
 *
 * @see java.lang.management.PlatformLoggingMXBean
 */
public interface LoggingMXBean {

    /**
     * Returns the list of currently registered logger names. This method
     * calls {@link LogManager#getLoggerNames} and returns a list
     * of the logger names.
     *
     * <p>
     *  返回当前注册的记录器名称列表。此方法调用{@link LogManager#getLoggerNames}并返回记录器名称列表。
     * 
     * 
     * @return A list of <tt>String</tt> each of which is a
     *         currently registered <tt>Logger</tt> name.
     */
    public java.util.List<String> getLoggerNames();

    /**
     * Gets the name of the log level associated with the specified logger.
     * If the specified logger does not exist, <tt>null</tt>
     * is returned.
     * This method first finds the logger of the given name and
     * then returns the name of the log level by calling:
     * <blockquote>
     *   {@link Logger#getLevel Logger.getLevel()}.{@link Level#getName getName()};
     * </blockquote>
     *
     * <p>
     * If the <tt>Level</tt> of the specified logger is <tt>null</tt>,
     * which means that this logger's effective level is inherited
     * from its parent, an empty string will be returned.
     *
     * <p>
     * 获取与指定记录器关联的日志级别的名称。如果指定的记录器不存在,则返回<tt> null </tt>。此方法首先查找给定名称的记录器,然后通过调用返回日志级别的名称：
     * <blockquote>
     *  {@link Logger#getLevel Logger.getLevel()}。{@ link Level#getName getName()};
     * </blockquote>
     * 
     * <p>
     *  如果指定记录器的<tt> Level </tt>为<tt> null </tt>,表示此记录器的有效级别从其父级继承,则将返回一个空字符串。
     * 
     * 
     * @param loggerName The name of the <tt>Logger</tt> to be retrieved.
     *
     * @return The name of the log level of the specified logger; or
     *         an empty string if the log level of the specified logger
     *         is <tt>null</tt>.  If the specified logger does not
     *         exist, <tt>null</tt> is returned.
     *
     * @see Logger#getLevel
     */
    public String getLoggerLevel(String loggerName);

    /**
     * Sets the specified logger to the specified new level.
     * If the <tt>levelName</tt> is not <tt>null</tt>, the level
     * of the specified logger is set to the parsed <tt>Level</tt>
     * matching the <tt>levelName</tt>.
     * If the <tt>levelName</tt> is <tt>null</tt>, the level
     * of the specified logger is set to <tt>null</tt> and
     * the effective level of the logger is inherited from
     * its nearest ancestor with a specific (non-null) level value.
     *
     * <p>
     * 
     * @param loggerName The name of the <tt>Logger</tt> to be set.
     *                   Must be non-null.
     * @param levelName The name of the level to set on the specified logger,
     *                 or <tt>null</tt> if setting the level to inherit
     *                 from its nearest ancestor.
     *
     * @throws IllegalArgumentException if the specified logger
     * does not exist, or <tt>levelName</tt> is not a valid level name.
     *
     * @throws SecurityException if a security manager exists and if
     * the caller does not have LoggingPermission("control").
     *
     * @see Logger#setLevel
     */
    public void setLoggerLevel(String loggerName, String levelName);

    /**
     * Returns the name of the parent for the specified logger.
     * If the specified logger does not exist, <tt>null</tt> is returned.
     * If the specified logger is the root <tt>Logger</tt> in the namespace,
     * the result will be an empty string.
     *
     * <p>
     *  将指定的记录器设置为指定的新级别。如果<tt> levelName </tt>不是<tt> null </tt>,则指定记录器的级别设置为匹配<tt> levelName </tt> 。
     * 如果<tt> levelName </tt>为<tt> null </tt>,则指定记录器的级别设置为<tt> null </tt>,并且记录器的有效级别从其最近的祖先与特定(非空)级别值。
     * 
     * 
     * @param loggerName The name of a <tt>Logger</tt>.
     *
     * @return the name of the nearest existing parent logger;
     *         an empty string if the specified logger is the root logger.
     *         If the specified logger does not exist, <tt>null</tt>
     *         is returned.
     */
    public String getParentLoggerName(String loggerName);
}
