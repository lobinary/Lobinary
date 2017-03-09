/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2009, 2011, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.management;

/**
 * The management interface for the {@linkplain java.util.logging logging} facility.
 *
 * <p>There is a single global instance of the <tt>PlatformLoggingMXBean</tt>.
 * The {@link java.lang.management.ManagementFactory#getPlatformMXBean(Class)
 * ManagementFactory.getPlatformMXBean} method can be used to obtain
 * the {@code PlatformLoggingMXBean} object as follows:
 * <pre>
 *     PlatformLoggingMXBean logging = ManagementFactory.getPlatformMXBean(PlatformLoggingMXBean.class);
 * </pre>
 * The {@code PlatformLoggingMXBean} object is also registered with the
 * platform {@linkplain java.lang.management.ManagementFactory#getPlatformMBeanServer
 * MBeanServer}.
 * The {@link javax.management.ObjectName ObjectName} for uniquely
 * identifying the {@code PlatformLoggingMXBean} within an MBeanServer is:
 * <pre>
 *      {@link java.util.logging.LogManager#LOGGING_MXBEAN_NAME java.util.logging:type=Logging}
 * </pre>
 *
 * <p>The instance registered in the platform <tt>MBeanServer</tt> with
 * this {@code ObjectName} implements all attributes defined by
 * {@link java.util.logging.LoggingMXBean}.
 *
 * <p>
 *  {@linkplain java.util.logging logging}工具的管理界面。
 * 
 *  <p> <tt> PlatformLoggingMXBean </tt>有一个全局实例。
 *  {@link java.lang.management.ManagementFactory#getPlatformMXBean(Class)ManagementFactory.getPlatformMXBean}
 * 方法可用于获取{@code PlatformLoggingMXBean}对象,如下所示：。
 *  <p> <tt> PlatformLoggingMXBean </tt>有一个全局实例。
 * <pre>
 *  PlatformLoggingMXBean logging = ManagementFactory.getPlatformMXBean(PlatformLoggingMXBean.class);
 * </pre>
 *  {@code PlatformLoggingMXBean}对象也在平台{@linkplain java.lang.management.ManagementFactory#getPlatformMBeanServer MBeanServer}
 * 中注册。
 * 用于唯一标识MBeanServer中的{@code PlatformLoggingMXBean}的{@link javax.management.ObjectName ObjectName}是：。
 * <pre>
 *  {@link java.util.logging.LogManager#LOGGING_MXBEAN_NAME java.util.logging：type = Logging}
 * </pre>
 * 
 *  <p>在平台<tt> MBeanServer </tt>中注册的实例{@code ObjectName}实现了{@link java.util.logging.LoggingMXBean}定义的所有属
 * 性。
 * 
 * 
 * @since   1.7
 */
public interface PlatformLoggingMXBean extends PlatformManagedObject {

    /**
     * Returns the list of the currently registered
     * {@linkplain java.util.logging.Logger logger} names. This method
     * calls {@link java.util.logging.LogManager#getLoggerNames} and
     * returns a list of the logger names.
     *
     * <p>
     *  返回当前注册的{@linkplain java.util.logging.Logger logger}名称的列表。
     * 此方法调用{@link java.util.logging.LogManager#getLoggerNames}并返回记录器名称列表。
     * 
     * 
     * @return A list of {@code String} each of which is a
     *         currently registered {@code Logger} name.
     */
    java.util.List<String> getLoggerNames();

    /**
     * Gets the name of the log {@linkplain java.util.logging.Logger#getLevel
     * level} associated with the specified logger.
     * If the specified logger does not exist, {@code null}
     * is returned.
     * This method first finds the logger of the given name and
     * then returns the name of the log level by calling:
     * <blockquote>
     *   {@link java.util.logging.Logger#getLevel
     *    Logger.getLevel()}.{@link java.util.logging.Level#getName getName()};
     * </blockquote>
     *
     * <p>
     * If the {@code Level} of the specified logger is {@code null},
     * which means that this logger's effective level is inherited
     * from its parent, an empty string will be returned.
     *
     * <p>
     * 获取与指定记录器关联的日志{@linkplain java.util.logging.Logger#getLevel level}的名称。如果指定的记录器不存在,则返回{@code null}。
     * 此方法首先查找给定名称的记录器,然后通过调用返回日志级别的名称：。
     * <blockquote>
     *  {@link java.util.logging.Logger#getLevel Logger.getLevel()}。
     * {@ link java.util.logging.Level#getName getName()};。
     * </blockquote>
     * 
     * <p>
     *  如果指定记录器的{@code Level}为{@code null},这意味着此记录器的有效级别从其父代继承,则将返回一个空字符串。
     * 
     * 
     * @param loggerName The name of the {@code Logger} to be retrieved.
     *
     * @return The name of the log level of the specified logger; or
     *         an empty string if the log level of the specified logger
     *         is {@code null}.  If the specified logger does not
     *         exist, {@code null} is returned.
     *
     * @see java.util.logging.Logger#getLevel
     */
    String getLoggerLevel(String loggerName);

    /**
     * Sets the specified logger to the specified new
     * {@linkplain java.util.logging.Logger#setLevel level}.
     * If the {@code levelName} is not {@code null}, the level
     * of the specified logger is set to the parsed
     * {@link java.util.logging.Level Level}
     * matching the {@code levelName}.
     * If the {@code levelName} is {@code null}, the level
     * of the specified logger is set to {@code null} and
     * the effective level of the logger is inherited from
     * its nearest ancestor with a specific (non-null) level value.
     *
     * <p>
     *  将指定的记录器设置为指定的新{@linkplain java.util.logging.Logger#setLevel level}。
     * 如果{@code levelName}不是{@code null},则指定记录器的级别设置为与{@code levelName}匹配的已解析的{@link java.util.logging.Level Level}
     * 。
     *  将指定的记录器设置为指定的新{@linkplain java.util.logging.Logger#setLevel level}。
     * 如果{@code levelName}是{@code null},则指定记录器的级别设置为{@code null},并且记录器的有效级别从具有特定(非空)级别的最近祖先继承值。
     * 
     * @param loggerName The name of the {@code Logger} to be set.
     *                   Must be non-null.
     * @param levelName The name of the level to set on the specified logger,
     *                 or  {@code null} if setting the level to inherit
     *                 from its nearest ancestor.
     *
     * @throws IllegalArgumentException if the specified logger
     * does not exist, or {@code levelName} is not a valid level name.
     *
     * @throws SecurityException if a security manager exists and if
     * the caller does not have LoggingPermission("control").
     *
     * @see java.util.logging.Logger#setLevel
     */
    void setLoggerLevel(String loggerName, String levelName);

    /**
     * Returns the name of the
     * {@linkplain java.util.logging.Logger#getParent parent}
     * for the specified logger.
     * If the specified logger does not exist, {@code null} is returned.
     * If the specified logger is the root {@code Logger} in the namespace,
     * the result will be an empty string.
     *
     * <p>
     * 
     * 
     * @param loggerName The name of a {@code Logger}.
     *
     * @return the name of the nearest existing parent logger;
     *         an empty string if the specified logger is the root logger.
     *         If the specified logger does not exist, {@code null}
     *         is returned.
     */
    String getParentLoggerName(String loggerName);
}
