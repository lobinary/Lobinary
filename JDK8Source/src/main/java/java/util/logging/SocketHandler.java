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
import java.net.*;

/**
 * Simple network logging <tt>Handler</tt>.
 * <p>
 * <tt>LogRecords</tt> are published to a network stream connection.  By default
 * the <tt>XMLFormatter</tt> class is used for formatting.
 * <p>
 * <b>Configuration:</b>
 * By default each <tt>SocketHandler</tt> is initialized using the following
 * <tt>LogManager</tt> configuration properties where <tt>&lt;handler-name&gt;</tt>
 * refers to the fully-qualified class name of the handler.
 * If properties are not defined
 * (or have invalid values) then the specified default values are used.
 * <ul>
 * <li>   &lt;handler-name&gt;.level
 *        specifies the default level for the <tt>Handler</tt>
 *        (defaults to <tt>Level.ALL</tt>). </li>
 * <li>   &lt;handler-name&gt;.filter
 *        specifies the name of a <tt>Filter</tt> class to use
 *        (defaults to no <tt>Filter</tt>). </li>
 * <li>   &lt;handler-name&gt;.formatter
 *        specifies the name of a <tt>Formatter</tt> class to use
 *        (defaults to <tt>java.util.logging.XMLFormatter</tt>). </li>
 * <li>   &lt;handler-name&gt;.encoding
 *        the name of the character set encoding to use (defaults to
 *        the default platform encoding). </li>
 * <li>   &lt;handler-name&gt;.host
 *        specifies the target host name to connect to (no default). </li>
 * <li>   &lt;handler-name&gt;.port
 *        specifies the target TCP port to use (no default). </li>
 * </ul>
 * <p>
 * For example, the properties for {@code SocketHandler} would be:
 * <ul>
 * <li>   java.util.logging.SocketHandler.level=INFO </li>
 * <li>   java.util.logging.SocketHandler.formatter=java.util.logging.SimpleFormatter </li>
 * </ul>
 * <p>
 * For a custom handler, e.g. com.foo.MyHandler, the properties would be:
 * <ul>
 * <li>   com.foo.MyHandler.level=INFO </li>
 * <li>   com.foo.MyHandler.formatter=java.util.logging.SimpleFormatter </li>
 * </ul>
 * <p>
 * The output IO stream is buffered, but is flushed after each
 * <tt>LogRecord</tt> is written.
 *
 * <p>
 *  简单网络日志<tt>处理程序</tt>。
 * <p>
 *  <tt> LogRecords </tt>发布到网络流连接。默认情况下,<tt> XMLFormatter </tt>类用于格式化。
 * <p>
 *  <b>配置：</b>默认情况下,每个<tt> SocketHandler </tt>都使用以下<tt> LogManager </tt>配置属性进行初始化,其中<tt>&lt; handler-nam
 * e&gt; </tt>到处理程序的完全限定类名。
 * 如果未定义属性(或具有无效值),则使用指定的默认值。
 * <ul>
 *  <li>&lt; handler-name&gt; .level指定<tt>处理程序</tt>(默认为<tt> Level.ALL </tt>)的默认级别。
 *  </li> <li>&lt; handler-name&gt; .filter指定要使用的<tt> Filter </tt>类的名称(默认为<tt> Filter </tt>)。
 *  </li> <li>&lt; handler-name&gt; .formatter指定要使用的<tt> Formatter </tt>类的名称(默认为<tt> java.util.logging.X
 * MLFormatter </tt>)。
 *  </li> <li>&lt; handler-name&gt; .filter指定要使用的<tt> Filter </tt>类的名称(默认为<tt> Filter </tt>)。
 *  </li> <li>&lt; handler-name&gt; .encoding要使用的字符集编码的名称(默认为默认平台编码)。
 *  </li> <li>&lt; handler-name&gt; .host指定要连接到的目标主机名(无默认值)。
 *  </li> <li>&lt; handler-name&gt; .port指定要使用的目标TCP端口(无默认值)。 </li>。
 * </ul>
 * <p>
 *  例如,{@code SocketHandler}的属性将是：
 * <ul>
 * <li> java.util.logging.SocketHandler.level = INFO </li> <li> java.util.logging.SocketHandler.formatte
 * r = java.util.logging.SimpleFormatter </li>。
 * </ul>
 * <p>
 * 
 * @since 1.4
 */

public class SocketHandler extends StreamHandler {
    private Socket sock;
    private String host;
    private int port;

    // Private method to configure a SocketHandler from LogManager
    // properties and/or default values as specified in the class
    // javadoc.
    private void configure() {
        LogManager manager = LogManager.getLogManager();
        String cname = getClass().getName();

        setLevel(manager.getLevelProperty(cname +".level", Level.ALL));
        setFilter(manager.getFilterProperty(cname +".filter", null));
        setFormatter(manager.getFormatterProperty(cname +".formatter", new XMLFormatter()));
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
        port = manager.getIntProperty(cname + ".port", 0);
        host = manager.getStringProperty(cname + ".host", null);
    }


    /**
     * Create a <tt>SocketHandler</tt>, using only <tt>LogManager</tt> properties
     * (or their defaults).
     * <p>
     *  对于自定义处理程序,例如com.foo.MyHandler,属性将是：
     * <ul>
     *  <li> com.foo.MyHandler.level = INFO </li> <li> com.foo.MyHandler.formatter = java.util.logging.Simpl
     * eFormatter </li>。
     * </ul>
     * <p>
     *  输出IO流被缓冲,但在每个<tt> LogRecord </tt>写入后刷新。
     * 
     * 
     * @throws IllegalArgumentException if the host or port are invalid or
     *          are not specified as LogManager properties.
     * @throws IOException if we are unable to connect to the target
     *         host and port.
     */
    public SocketHandler() throws IOException {
        // We are going to use the logging defaults.
        sealed = false;
        configure();

        try {
            connect();
        } catch (IOException ix) {
            System.err.println("SocketHandler: connect failed to " + host + ":" + port);
            throw ix;
        }
        sealed = true;
    }

    /**
     * Construct a <tt>SocketHandler</tt> using a specified host and port.
     *
     * The <tt>SocketHandler</tt> is configured based on <tt>LogManager</tt>
     * properties (or their default values) except that the given target host
     * and port arguments are used. If the host argument is empty, but not
     * null String then the localhost is used.
     *
     * <p>
     *  只使用<tt> LogManager </tt>属性(或其默认值)创建<tt> SocketHandler </tt>。
     * 
     * 
     * @param host target host.
     * @param port target port.
     *
     * @throws IllegalArgumentException if the host or port are invalid.
     * @throws IOException if we are unable to connect to the target
     *         host and port.
     */
    public SocketHandler(String host, int port) throws IOException {
        sealed = false;
        configure();
        sealed = true;
        this.port = port;
        this.host = host;
        connect();
    }

    private void connect() throws IOException {
        // Check the arguments are valid.
        if (port == 0) {
            throw new IllegalArgumentException("Bad port: " + port);
        }
        if (host == null) {
            throw new IllegalArgumentException("Null host name: " + host);
        }

        // Try to open a new socket.
        sock = new Socket(host, port);
        OutputStream out = sock.getOutputStream();
        BufferedOutputStream bout = new BufferedOutputStream(out);
        setOutputStream(bout);
    }

    /**
     * Close this output stream.
     *
     * <p>
     *  使用指定的主机和端口构造<tt> SocketHandler </tt>。
     * 
     *  <tt> SocketHandler </tt>是根据<tt> LogManager </tt>属性(或其默认值)配置的,但使用给定的目标主机和端口参数除外。
     * 如果host参数为空,但不是null String,则使用localhost。
     * 
     * 
     * @exception  SecurityException  if a security manager exists and if
     *             the caller does not have <tt>LoggingPermission("control")</tt>.
     */
    @Override
    public synchronized void close() throws SecurityException {
        super.close();
        if (sock != null) {
            try {
                sock.close();
            } catch (IOException ix) {
                // drop through.
            }
        }
        sock = null;
    }

    /**
     * Format and publish a <tt>LogRecord</tt>.
     *
     * <p>
     *  关闭此输出流。
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
        super.publish(record);
        flush();
    }
}
