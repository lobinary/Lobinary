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

/**
 * Stream based logging <tt>Handler</tt>.
 * <p>
 * This is primarily intended as a base class or support class to
 * be used in implementing other logging <tt>Handlers</tt>.
 * <p>
 * <tt>LogRecords</tt> are published to a given <tt>java.io.OutputStream</tt>.
 * <p>
 * <b>Configuration:</b>
 * By default each <tt>StreamHandler</tt> is initialized using the following
 * <tt>LogManager</tt> configuration properties where <tt>&lt;handler-name&gt;</tt>
 * refers to the fully-qualified class name of the handler.
 * If properties are not defined
 * (or have invalid values) then the specified default values are used.
 * <ul>
 * <li>   &lt;handler-name&gt;.level
 *        specifies the default level for the <tt>Handler</tt>
 *        (defaults to <tt>Level.INFO</tt>). </li>
 * <li>   &lt;handler-name&gt;.filter
 *        specifies the name of a <tt>Filter</tt> class to use
 *         (defaults to no <tt>Filter</tt>). </li>
 * <li>   &lt;handler-name&gt;.formatter
 *        specifies the name of a <tt>Formatter</tt> class to use
 *        (defaults to <tt>java.util.logging.SimpleFormatter</tt>). </li>
 * <li>   &lt;handler-name&gt;.encoding
 *        the name of the character set encoding to use (defaults to
 *        the default platform encoding). </li>
 * </ul>
 * <p>
 * For example, the properties for {@code StreamHandler} would be:
 * <ul>
 * <li>   java.util.logging.StreamHandler.level=INFO </li>
 * <li>   java.util.logging.StreamHandler.formatter=java.util.logging.SimpleFormatter </li>
 * </ul>
 * <p>
 * For a custom handler, e.g. com.foo.MyHandler, the properties would be:
 * <ul>
 * <li>   com.foo.MyHandler.level=INFO </li>
 * <li>   com.foo.MyHandler.formatter=java.util.logging.SimpleFormatter </li>
 * </ul>
 * <p>
 * <p>
 *  基于流的日志<tt>处理程序</tt>。
 * <p>
 *  这主要是作为用于实现其他日志<tt>处理程序</tt>的基类或支持类。
 * <p>
 *  <tt> LogRecords </tt>发布到给定的<tt> java.io.OutputStream </tt>。
 * <p>
 *  <b>配置：</b>默认情况下,每个<tt> StreamHandler </tt>都是使用以下<tt> LogManager </tt>配置属性初始化的,其中<tt>&lt; handler-nam
 * e&gt; </tt>到处理程序的完全限定类名。
 * 如果未定义属性(或具有无效值),则使用指定的默认值。
 * <ul>
 *  <li>&lt; handler-name&gt; .level指定<tt>处理程序</tt>(默认为<tt> Level.INFO </tt>)的默认级别。
 *  </li> <li>&lt; handler-name&gt; .filter指定要使用的<tt> Filter </tt>类的名称(默认为<tt> Filter </tt>)。
 *  </li> <li>&lt; handler-name&gt; .formatter指定要使用的<tt> Formatter </tt>类的名称(默认为<tt> java.util.logging.S
 * impleFormatter </tt>)。
 *  </li> <li>&lt; handler-name&gt; .filter指定要使用的<tt> Filter </tt>类的名称(默认为<tt> Filter </tt>)。
 *  </li> <li>&lt; handler-name&gt; .encoding要使用的字符集编码的名称(默认为默认平台编码)。 </li>。
 * </ul>
 * <p>
 *  例如,{@code StreamHandler}的属性将是：
 * <ul>
 *  <li> java.util.logging.StreamHandler.level = INFO </li> <li> java.util.logging.StreamHandler.formatt
 * er = java.util.logging.SimpleFormatter </li>。
 * </ul>
 * <p>
 * 对于自定义处理程序,例如com.foo.MyHandler,属性将是：
 * <ul>
 *  <li> com.foo.MyHandler.level = INFO </li> <li> com.foo.MyHandler.formatter = java.util.logging.Simpl
 * eFormatter </li>。
 * </ul>
 * <p>
 * 
 * @since 1.4
 */

public class StreamHandler extends Handler {
    private OutputStream output;
    private boolean doneHeader;
    private volatile Writer writer;

    // Private method to configure a StreamHandler from LogManager
    // properties and/or default values as specified in the class
    // javadoc.
    private void configure() {
        LogManager manager = LogManager.getLogManager();
        String cname = getClass().getName();

        setLevel(manager.getLevelProperty(cname +".level", Level.INFO));
        setFilter(manager.getFilterProperty(cname +".filter", null));
        setFormatter(manager.getFormatterProperty(cname +".formatter", new SimpleFormatter()));
        try {
            setEncoding(manager.getStringProperty(cname +".encoding", null));
        } catch (Exception ex) {
            try {
                setEncoding(null);
            } catch (Exception ex2) {
                // doing a setEncoding with null should always work.
                // assert false;
            }
        }
    }

    /**
     * Create a <tt>StreamHandler</tt>, with no current output stream.
     * <p>
     *  创建<tt> StreamHandler </tt>,没有当前输出流。
     * 
     */
    public StreamHandler() {
        sealed = false;
        configure();
        sealed = true;
    }

    /**
     * Create a <tt>StreamHandler</tt> with a given <tt>Formatter</tt>
     * and output stream.
     * <p>
     * <p>
     *  使用给定的<tt>格式化程序</tt>和输出流创建<tt> StreamHandler </tt>。
     * <p>
     * 
     * @param out         the target output stream
     * @param formatter   Formatter to be used to format output
     */
    public StreamHandler(OutputStream out, Formatter formatter) {
        sealed = false;
        configure();
        setFormatter(formatter);
        setOutputStream(out);
        sealed = true;
    }

    /**
     * Change the output stream.
     * <P>
     * If there is a current output stream then the <tt>Formatter</tt>'s
     * tail string is written and the stream is flushed and closed.
     * Then the output stream is replaced with the new output stream.
     *
     * <p>
     *  更改输出流。
     * <P>
     *  如果有当前输出流,则会写入<tt> Formatter </tt>的尾字符串,并且流被刷新并关闭。然后,输出流被替换为新的输出流。
     * 
     * 
     * @param out   New output stream.  May not be null.
     * @exception  SecurityException  if a security manager exists and if
     *             the caller does not have <tt>LoggingPermission("control")</tt>.
     */
    protected synchronized void setOutputStream(OutputStream out) throws SecurityException {
        if (out == null) {
            throw new NullPointerException();
        }
        flushAndClose();
        output = out;
        doneHeader = false;
        String encoding = getEncoding();
        if (encoding == null) {
            writer = new OutputStreamWriter(output);
        } else {
            try {
                writer = new OutputStreamWriter(output, encoding);
            } catch (UnsupportedEncodingException ex) {
                // This shouldn't happen.  The setEncoding method
                // should have validated that the encoding is OK.
                throw new Error("Unexpected exception " + ex);
            }
        }
    }

    /**
     * Set (or change) the character encoding used by this <tt>Handler</tt>.
     * <p>
     * The encoding should be set before any <tt>LogRecords</tt> are written
     * to the <tt>Handler</tt>.
     *
     * <p>
     *  设置(或更改)此<tt>处理程序</tt>使用的字符编码。
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
    @Override
    public synchronized void setEncoding(String encoding)
                        throws SecurityException, java.io.UnsupportedEncodingException {
        super.setEncoding(encoding);
        if (output == null) {
            return;
        }
        // Replace the current writer with a writer for the new encoding.
        flush();
        if (encoding == null) {
            writer = new OutputStreamWriter(output);
        } else {
            writer = new OutputStreamWriter(output, encoding);
        }
    }

    /**
     * Format and publish a <tt>LogRecord</tt>.
     * <p>
     * The <tt>StreamHandler</tt> first checks if there is an <tt>OutputStream</tt>
     * and if the given <tt>LogRecord</tt> has at least the required log level.
     * If not it silently returns.  If so, it calls any associated
     * <tt>Filter</tt> to check if the record should be published.  If so,
     * it calls its <tt>Formatter</tt> to format the record and then writes
     * the result to the current output stream.
     * <p>
     * If this is the first <tt>LogRecord</tt> to be written to a given
     * <tt>OutputStream</tt>, the <tt>Formatter</tt>'s "head" string is
     * written to the stream before the <tt>LogRecord</tt> is written.
     *
     * <p>
     *  格式化并发布<tt> LogRecord </tt>。
     * <p>
     *  <tt> StreamHandler </tt>首先检查是否有<tt> OutputStream </tt>,以及给定的<tt> LogRecord </tt>是否至少具有所需的日志级别。
     * 如果不是,它默默地返回。如果是,则调用任何关联的<tt>过滤器</tt>来检查是否应该发布记录。如果是,则调用<tt> Formatter </tt>来格式化记录,然后将结果写入当前输出流。
     * <p>
     *  如果这是要写入给定<tt> OutputStream </tt>的第一个<tt> LogRecord </tt>,则<tt> Formatter </tt>的"head" <tt> LogRecord
     *  </tt>已写入。
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
        String msg;
        try {
            msg = getFormatter().format(record);
        } catch (Exception ex) {
            // We don't want to throw an exception here, but we
            // report the exception to any registered ErrorManager.
            reportError(null, ex, ErrorManager.FORMAT_FAILURE);
            return;
        }

        try {
            if (!doneHeader) {
                writer.write(getFormatter().getHead(this));
                doneHeader = true;
            }
            writer.write(msg);
        } catch (Exception ex) {
            // We don't want to throw an exception here, but we
            // report the exception to any registered ErrorManager.
            reportError(null, ex, ErrorManager.WRITE_FAILURE);
        }
    }


    /**
     * Check if this <tt>Handler</tt> would actually log a given <tt>LogRecord</tt>.
     * <p>
     * This method checks if the <tt>LogRecord</tt> has an appropriate level and
     * whether it satisfies any <tt>Filter</tt>.  It will also return false if
     * no output stream has been assigned yet or the LogRecord is null.
     * <p>
     * <p>
     *  检查此<tt>处理程序</tt>是否实际记录给定的<tt> LogRecord </tt>。
     * <p>
     * 此方法检查<tt> LogRecord </tt>是否具有适当的级别,以及是否满足任何<tt>过滤器</tt>。如果尚未分配输出流或LogRecord为null,它也将返回false。
     * <p>
     * 
     * @param record  a <tt>LogRecord</tt>
     * @return true if the <tt>LogRecord</tt> would be logged.
     *
     */
    @Override
    public boolean isLoggable(LogRecord record) {
        if (writer == null || record == null) {
            return false;
        }
        return super.isLoggable(record);
    }

    /**
     * Flush any buffered messages.
     * <p>
     *  刷新任何缓冲的消息。
     * 
     */
    @Override
    public synchronized void flush() {
        if (writer != null) {
            try {
                writer.flush();
            } catch (Exception ex) {
                // We don't want to throw an exception here, but we
                // report the exception to any registered ErrorManager.
                reportError(null, ex, ErrorManager.FLUSH_FAILURE);
            }
        }
    }

    private synchronized void flushAndClose() throws SecurityException {
        checkPermission();
        if (writer != null) {
            try {
                if (!doneHeader) {
                    writer.write(getFormatter().getHead(this));
                    doneHeader = true;
                }
                writer.write(getFormatter().getTail(this));
                writer.flush();
                writer.close();
            } catch (Exception ex) {
                // We don't want to throw an exception here, but we
                // report the exception to any registered ErrorManager.
                reportError(null, ex, ErrorManager.CLOSE_FAILURE);
            }
            writer = null;
            output = null;
        }
    }

    /**
     * Close the current output stream.
     * <p>
     * The <tt>Formatter</tt>'s "tail" string is written to the stream before it
     * is closed.  In addition, if the <tt>Formatter</tt>'s "head" string has not
     * yet been written to the stream, it will be written before the
     * "tail" string.
     *
     * <p>
     *  关闭当前输出流。
     * <p>
     * 
     * @exception  SecurityException  if a security manager exists and if
     *             the caller does not have LoggingPermission("control").
     */
    @Override
    public synchronized void close() throws SecurityException {
        flushAndClose();
    }
}
