/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2012, Oracle and/or its affiliates. All rights reserved.
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
import java.text.*;
import java.util.Date;
import sun.util.logging.LoggingSupport;

/**
 * Print a brief summary of the {@code LogRecord} in a human readable
 * format.  The summary will typically be 1 or 2 lines.
 *
 * <p>
 * <a name="formatting">
 * <b>Configuration:</b></a>
 * The {@code SimpleFormatter} is initialized with the
 * <a href="../Formatter.html#syntax">format string</a>
 * specified in the {@code java.util.logging.SimpleFormatter.format}
 * property to {@linkplain #format format} the log messages.
 * This property can be defined
 * in the {@linkplain LogManager#getProperty logging properties}
 * configuration file
 * or as a system property.  If this property is set in both
 * the logging properties and system properties,
 * the format string specified in the system property will be used.
 * If this property is not defined or the given format string
 * is {@linkplain java.util.IllegalFormatException illegal},
 * the default format is implementation-specific.
 *
 * <p>
 *  以人类可读的格式打印{@code LogRecord}的简要摘要。摘要通常为1或2行。
 * 
 * <p>
 * <a name="formatting">
 *  <b>配置：</b> </a> {@code SimpleFormatter}是使用{@code}中指定的<a href="../Formatter.html#syntax">格式字符串</a>初始化
 * 的java.util.logging.SimpleFormatter.format}属性设置为{@linkplain #format format}日志消息。
 * 此属性可以在{@linkplain LogManager#getProperty logging properties}配置文件中定义或作为系统属性。
 * 如果在日志记录属性和系统属性中设置此属性,则将使用系统属性中指定的格式字符串。
 * 如果未定义此属性或给定的格式字符串为{@linkplain java.util.IllegalFormatException illegal},则默认格式为实现特定的。
 * 
 * 
 * @since 1.4
 * @see java.util.Formatter
 */

public class SimpleFormatter extends Formatter {

    // format string for printing the log record
    private static final String format = LoggingSupport.getSimpleFormat();
    private final Date dat = new Date();

    /**
     * Format the given LogRecord.
     * <p>
     * The formatting can be customized by specifying the
     * <a href="../Formatter.html#syntax">format string</a>
     * in the <a href="#formatting">
     * {@code java.util.logging.SimpleFormatter.format}</a> property.
     * The given {@code LogRecord} will be formatted as if by calling:
     * <pre>
     *    {@link String#format String.format}(format, date, source, logger, level, message, thrown);
     * </pre>
     * where the arguments are:<br>
     * <ol>
     * <li>{@code format} - the {@link java.util.Formatter
     *     java.util.Formatter} format string specified in the
     *     {@code java.util.logging.SimpleFormatter.format} property
     *     or the default format.</li>
     * <li>{@code date} - a {@link Date} object representing
     *     {@linkplain LogRecord#getMillis event time} of the log record.</li>
     * <li>{@code source} - a string representing the caller, if available;
     *     otherwise, the logger's name.</li>
     * <li>{@code logger} - the logger's name.</li>
     * <li>{@code level} - the {@linkplain Level#getLocalizedName
     *     log level}.</li>
     * <li>{@code message} - the formatted log message
     *     returned from the {@link Formatter#formatMessage(LogRecord)}
     *     method.  It uses {@link java.text.MessageFormat java.text}
     *     formatting and does not use the {@code java.util.Formatter
     *     format} argument.</li>
     * <li>{@code thrown} - a string representing
     *     the {@linkplain LogRecord#getThrown throwable}
     *     associated with the log record and its backtrace
     *     beginning with a newline character, if any;
     *     otherwise, an empty string.</li>
     * </ol>
     *
     * <p>Some example formats:<br>
     * <ul>
     * <li> {@code java.util.logging.SimpleFormatter.format="%4$s: %5$s [%1$tc]%n"}
     *     <p>This prints 1 line with the log level ({@code 4$}),
     *     the log message ({@code 5$}) and the timestamp ({@code 1$}) in
     *     a square bracket.
     *     <pre>
     *     WARNING: warning message [Tue Mar 22 13:11:31 PDT 2011]
     *     </pre></li>
     * <li> {@code java.util.logging.SimpleFormatter.format="%1$tc %2$s%n%4$s: %5$s%6$s%n"}
     *     <p>This prints 2 lines where the first line includes
     *     the timestamp ({@code 1$}) and the source ({@code 2$});
     *     the second line includes the log level ({@code 4$}) and
     *     the log message ({@code 5$}) followed with the throwable
     *     and its backtrace ({@code 6$}), if any:
     *     <pre>
     *     Tue Mar 22 13:11:31 PDT 2011 MyClass fatal
     *     SEVERE: several message with an exception
     *     java.lang.IllegalArgumentException: invalid argument
     *             at MyClass.mash(MyClass.java:9)
     *             at MyClass.crunch(MyClass.java:6)
     *             at MyClass.main(MyClass.java:3)
     *     </pre></li>
     * <li> {@code java.util.logging.SimpleFormatter.format="%1$tb %1$td, %1$tY %1$tl:%1$tM:%1$tS %1$Tp %2$s%n%4$s: %5$s%n"}
     *      <p>This prints 2 lines similar to the example above
     *         with a different date/time formatting and does not print
     *         the throwable and its backtrace:
     *     <pre>
     *     Mar 22, 2011 1:11:31 PM MyClass fatal
     *     SEVERE: several message with an exception
     *     </pre></li>
     * </ul>
     * <p>This method can also be overridden in a subclass.
     * It is recommended to use the {@link Formatter#formatMessage}
     * convenience method to localize and format the message field.
     *
     * <p>
     *  格式化给定的LogRecord。
     * <p>
     *  可以通过在<a href="#formatting"> {@code java.util.logging.SimpleFormatter)中指定<a href="../Formatter.html#syntax">格式字符串</a>来自定义格式.format}
     *  </a>属性。
     * 给定的{@code LogRecord}将被格式化,如同通过调用：。
     * <pre>
     *  {@link String#format String.format}(format,date,source,logger,level,message,thrown);
     * </pre>
     *  其中参数为：<br>
     * <ol>
     * <li> {@ code format}  -  {@code java.util.logging.SimpleFormatter.format}属性中指定的{@link java.util.Formatter java.util.Formatter}
     * 格式字符串或默认格式。
     *  li> <li> {@ code date}  - 表示日志记录的{@linkplain LogRecord#getMillis事件时间}的{@link Date}对象。
     * </li> <li> {@ code source}呼叫者(如果可用) </li> <li> {@ code level}  -  {@linkplain Level#getLocalizedName log level}
     * 。
     *  li> <li> {@ code date}  - 表示日志记录的{@linkplain LogRecord#getMillis事件时间}的{@link Date}对象。
     * </li> </li> > <li> {@ code message}  - 从{@link Formatter#formatMessage(LogRecord)}方法返回的格式化日志消息。
     * 它使用{@link java.text.MessageFormat java.text}格式,不使用{@code java.util.Formatter format}参数。
     * </li> <li> {@ code thrown}  - 一个字符串, @linkplain LogRecord#getThrown throwable}与日志记录及其回溯相关联,以换行符开头,如果有
     * 的话;否则为空字符串。
     * 它使用{@link java.text.MessageFormat java.text}格式,不使用{@code java.util.Formatter format}参数。</li>。
     * 
     * @param record the log record to be formatted.
     * @return a formatted log record
     */
    public synchronized String format(LogRecord record) {
        dat.setTime(record.getMillis());
        String source;
        if (record.getSourceClassName() != null) {
            source = record.getSourceClassName();
            if (record.getSourceMethodName() != null) {
               source += " " + record.getSourceMethodName();
            }
        } else {
            source = record.getLoggerName();
        }
        String message = formatMessage(record);
        String throwable = "";
        if (record.getThrown() != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            pw.println();
            record.getThrown().printStackTrace(pw);
            pw.close();
            throwable = sw.toString();
        }
        return String.format(format,
                             dat,
                             source,
                             record.getLoggerName(),
                             record.getLevel().getLocalizedLevelName(),
                             message,
                             throwable);
    }
}
