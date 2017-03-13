/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2013, Oracle and/or its affiliates. All rights reserved.
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

/**
 * Defines channels, which represent connections to entities that are capable of
 * performing I/O operations, such as files and sockets; defines selectors, for
 * multiplexed, non-blocking I/O operations.
 *
 * <a name="channels"></a>
 *
 * <blockquote><table cellspacing=1 cellpadding=0 summary="Lists channels and their descriptions">
 * <tr><th align="left">Channels</th><th align="left">Description</th></tr>
 * <tr><td valign=top><tt><i>{@link java.nio.channels.Channel}</i></tt></td>
 *     <td>A nexus for I/O operations</td></tr>
 * <tr><td valign=top><tt>&nbsp;&nbsp;<i>{@link java.nio.channels.ReadableByteChannel}</i></tt></td>
 *     <td>Can read into a buffer</td></tr>
 * <tr><td valign=top><tt>&nbsp;&nbsp;&nbsp;&nbsp;<i>{@link java.nio.channels.ScatteringByteChannel}&nbsp;&nbsp;</i></tt></td>
 *     <td>Can read into a sequence of&nbsp;buffers</td></tr>
 * <tr><td valign=top><tt>&nbsp;&nbsp;<i>{@link java.nio.channels.WritableByteChannel}</i></tt></td>
 *     <td>Can write from a buffer</td></tr>
 * <tr><td valign=top><tt>&nbsp;&nbsp;&nbsp;&nbsp;<i>{@link java.nio.channels.GatheringByteChannel}</i></tt></td>
 *     <td>Can write from a sequence of&nbsp;buffers</td></tr>
 * <tr><td valign=top><tt>&nbsp;&nbsp;<i>{@link java.nio.channels.ByteChannel}</i></tt></td>
 *     <td>Can read/write to/from a&nbsp;buffer</td></tr>
 * <tr><td valign=top><tt>&nbsp;&nbsp;&nbsp;&nbsp;<i>{@link java.nio.channels.SeekableByteChannel}</i></tt></td>
 *     <td>A {@code ByteChannel} connected to an entity that contains a variable-length sequence of bytes</td></tr>
 * <tr><td valign=top><tt>&nbsp;&nbsp;<i>{@link java.nio.channels.AsynchronousChannel}</i></tt></td>
 *     <td>Supports asynchronous I/O operations.</td></tr>
 * <tr><td valign=top><tt>&nbsp;&nbsp;&nbsp;&nbsp;<i>{@link java.nio.channels.AsynchronousByteChannel}</i></tt></td>
 *     <td>Can read and write bytes asynchronously</td></tr>
 * <tr><td valign=top><tt>&nbsp;&nbsp;<i>{@link java.nio.channels.NetworkChannel}</i></tt></td>
 *     <td>A channel to a network socket</td></tr>
 * <tr><td valign=top><tt>&nbsp;&nbsp;&nbsp;&nbsp;<i>{@link java.nio.channels.MulticastChannel}</i></tt></td>
 *     <td>Can join Internet Protocol (IP) multicast groups</td></tr>
 * <tr><td valign=top><tt>{@link java.nio.channels.Channels}</tt></td>
 *     <td>Utility methods for channel/stream interoperation</td></tr>
 * </table></blockquote>
 *
 * <p> A <i>channel</i> represents an open connection to an entity such as a
 * hardware device, a file, a network socket, or a program component that is
 * capable of performing one or more distinct I/O operations, for example reading
 * or writing.  As specified in the {@link java.nio.channels.Channel} interface,
 * channels are either open or closed, and they are both <i>asynchronously
 * closeable</i> and <i>interruptible</i>.
 *
 * <p> The {@link java.nio.channels.Channel} interface is extended by several
 * other interfaces.
 *
 * <p> The {@link java.nio.channels.ReadableByteChannel} interface specifies a
 * {@link java.nio.channels.ReadableByteChannel#read read} method that reads bytes
 * from the channel into a buffer; similarly, the {@link
 * java.nio.channels.WritableByteChannel} interface specifies a {@link
 * java.nio.channels.WritableByteChannel#write write} method that writes bytes
 * from a buffer to the channel. The {@link java.nio.channels.ByteChannel}
 * interface unifies these two interfaces for the common case of channels that can
 * both read and write bytes. The {@link java.nio.channels.SeekableByteChannel}
 * interface extends the {@code ByteChannel} interface with methods to {@link
 * java.nio.channels.SeekableByteChannel#position() query} and {@link
 * java.nio.channels.SeekableByteChannel#position(long) modify} the channel's
 * current position, and its {@link java.nio.channels.SeekableByteChannel#size
 * size}.
 *
 * <p> The {@link java.nio.channels.ScatteringByteChannel} and {@link
 * java.nio.channels.GatheringByteChannel} interfaces extend the {@link
 * java.nio.channels.ReadableByteChannel} and {@link
 * java.nio.channels.WritableByteChannel} interfaces, respectively, adding {@link
 * java.nio.channels.ScatteringByteChannel#read read} and {@link
 * java.nio.channels.GatheringByteChannel#write write} methods that take a
 * sequence of buffers rather than a single buffer.
 *
 * <p> The {@link java.nio.channels.NetworkChannel} interface specifies methods
 * to {@link java.nio.channels.NetworkChannel#bind bind} the channel's socket,
 * obtain the address to which the socket is bound, and methods to {@link
 * java.nio.channels.NetworkChannel#getOption get} and {@link
 * java.nio.channels.NetworkChannel#setOption set} socket options. The {@link
 * java.nio.channels.MulticastChannel} interface specifies methods to join
 * Internet Protocol (IP) multicast groups.
 *
 * <p> The {@link java.nio.channels.Channels} utility class defines static methods
 * that support the interoperation of the stream classes of the <tt>{@link
 * java.io}</tt> package with the channel classes of this package.  An appropriate
 * channel can be constructed from an {@link java.io.InputStream} or an {@link
 * java.io.OutputStream}, and conversely an {@link java.io.InputStream} or an
 * {@link java.io.OutputStream} can be constructed from a channel.  A {@link
 * java.io.Reader} can be constructed that uses a given charset to decode bytes
 * from a given readable byte channel, and conversely a {@link java.io.Writer} can
 * be constructed that uses a given charset to encode characters into bytes and
 * write them to a given writable byte channel.
 *
 * <blockquote><table cellspacing=1 cellpadding=0 summary="Lists file channels and their descriptions">
 * <tr><th align="left">File channels</th><th align="left">Description</th></tr>
 * <tr><td valign=top><tt>{@link java.nio.channels.FileChannel}</tt></td>
 *     <td>Reads, writes, maps, and manipulates files</td></tr>
 * <tr><td valign=top><tt>{@link java.nio.channels.FileLock}</tt></td>
 *     <td>A lock on a (region of a) file</td></tr>
 * <tr><td valign=top><tt>{@link java.nio.MappedByteBuffer}&nbsp;&nbsp;</tt></td>
 *     <td>A direct byte buffer mapped to a region of a&nbsp;file</td></tr>
 * </table></blockquote>
 *
 * <p> The {@link java.nio.channels.FileChannel} class supports the usual
 * operations of reading bytes from, and writing bytes to, a channel connected to
 * a file, as well as those of querying and modifying the current file position
 * and truncating the file to a specific size.  It defines methods for acquiring
 * locks on the whole file or on a specific region of a file; these methods return
 * instances of the {@link java.nio.channels.FileLock} class.  Finally, it defines
 * methods for forcing updates to the file to be written to the storage device that
 * contains it, for efficiently transferring bytes between the file and other
 * channels, and for mapping a region of the file directly into memory.
 *
 * <p> A {@code FileChannel} is created by invoking one of its static {@link
 * java.nio.channels.FileChannel#open open} methods, or by invoking the {@code
 * getChannel} method of a {@link java.io.FileInputStream}, {@link
 * java.io.FileOutputStream}, or {@link java.io.RandomAccessFile} to return a
 * file channel connected to the same underlying file as the <tt>{@link java.io}</tt>
 * class.
 *
 * <a name="multiplex"></a>
 * <blockquote><table cellspacing=1 cellpadding=0 summary="Lists multiplexed, non-blocking channels and their descriptions">
 * <tr><th align="left">Multiplexed, non-blocking I/O</th><th align="left"><p>Description</th></tr>
 * <tr><td valign=top><tt>{@link java.nio.channels.SelectableChannel}</tt></td>
 *     <td>A channel that can be multiplexed</td></tr>
 * <tr><td valign=top><tt>&nbsp;&nbsp;{@link java.nio.channels.DatagramChannel}</tt></td>
 *     <td>A channel to a datagram-oriented socket</td></tr>
 * <tr><td valign=top><tt>&nbsp;&nbsp;{@link java.nio.channels.Pipe.SinkChannel}</tt></td>
 *     <td>The write end of a pipe</td></tr>
 * <tr><td valign=top><tt>&nbsp;&nbsp;{@link java.nio.channels.Pipe.SourceChannel}</tt></td>
 *     <td>The read end of a pipe</td></tr>
 * <tr><td valign=top><tt>&nbsp;&nbsp;{@link java.nio.channels.ServerSocketChannel}&nbsp;&nbsp;</tt></td>
 *     <td>A channel to a stream-oriented listening socket</td></tr>
 * <tr><td valign=top><tt>&nbsp;&nbsp;{@link java.nio.channels.SocketChannel}</tt></td>
 *     <td>A channel for a stream-oriented connecting socket</td></tr>
 * <tr><td valign=top><tt>{@link java.nio.channels.Selector}</tt></td>
 *     <td>A multiplexor of selectable channels</td></tr>
 * <tr><td valign=top><tt>{@link java.nio.channels.SelectionKey}</tt></td>
 *     <td>A token representing the registration <br> of a channel
 *     with&nbsp;a&nbsp;selector</td></tr>
 * <tr><td valign=top><tt>{@link java.nio.channels.Pipe}</tt></td>
 *     <td>Two channels that form a unidirectional&nbsp;pipe</td></tr>
 * </table></blockquote>
 *
 * <p> Multiplexed, non-blocking I/O, which is much more scalable than
 * thread-oriented, blocking I/O, is provided by <i>selectors</i>, <i>selectable
 * channels</i>, and <i>selection keys</i>.
 *
 * <p> A <a href="Selector.html"><i>selector</i></a> is a multiplexor of <a
 * href="SelectableChannel.html"><i>selectable channels</i></a>, which in turn are
 * a special type of channel that can be put into <a
 * href="SelectableChannel.html#bm"><i>non-blocking mode</i></a>.  To perform
 * multiplexed I/O operations, one or more selectable channels are first created,
 * put into non-blocking mode, and {@link
 * java.nio.channels.SelectableChannel#register <i>registered</i>}
 * with a selector.  Registering a channel specifies the set of I/O operations
 * that will be tested for readiness by the selector, and returns a <a
 * href="SelectionKey.html"><i>selection key</i></a> that represents the
 * registration.
 *
 * <p> Once some channels have been registered with a selector, a <a
 * href="Selector.html#selop"><i>selection operation</i></a> can be performed in
 * order to discover which channels, if any, have become ready to perform one or
 * more of the operations in which interest was previously declared.  If a channel
 * is ready then the key returned when it was registered will be added to the
 * selector's <i>selected-key set</i>.  The key set, and the keys within it, can
 * be examined in order to determine the operations for which each channel is
 * ready.  From each key one can retrieve the corresponding channel in order to
 * perform whatever I/O operations are required.
 *
 * <p> That a selection key indicates that its channel is ready for some operation
 * is a hint, but not a guarantee, that such an operation can be performed by a
 * thread without causing the thread to block.  It is imperative that code that
 * performs multiplexed I/O be written so as to ignore these hints when they prove
 * to be incorrect.
 *
 * <p> This package defines selectable-channel classes corresponding to the {@link
 * java.net.DatagramSocket}, {@link java.net.ServerSocket}, and {@link
 * java.net.Socket} classes defined in the <tt>{@link java.net}</tt> package.
 * Minor changes to these classes have been made in order to support sockets that
 * are associated with channels.  This package also defines a simple class that
 * implements unidirectional pipes.  In all cases, a new selectable channel is
 * created by invoking the static <tt>open</tt> method of the corresponding class.
 * If a channel needs an associated socket then a socket will be created as a side
 * effect of this operation.
 *
 * <p> The implementation of selectors, selectable channels, and selection keys
 * can be replaced by "plugging in" an alternative definition or instance of the
 * {@link java.nio.channels.spi.SelectorProvider} class defined in the <tt>{@link
 * java.nio.channels.spi}</tt> package.  It is not expected that many developers
 * will actually make use of this facility; it is provided primarily so that
 * sophisticated users can take advantage of operating-system-specific
 * I/O-multiplexing mechanisms when very high performance is required.
 *
 * <p> Much of the bookkeeping and synchronization required to implement the
 * multiplexed-I/O abstractions is performed by the {@link
 * java.nio.channels.spi.AbstractInterruptibleChannel}, {@link
 * java.nio.channels.spi.AbstractSelectableChannel}, {@link
 * java.nio.channels.spi.AbstractSelectionKey}, and {@link
 * java.nio.channels.spi.AbstractSelector} classes in the <tt>{@link
 * java.nio.channels.spi}</tt> package.  When defining a custom selector provider,
 * only the {@link java.nio.channels.spi.AbstractSelector} and {@link
 * java.nio.channels.spi.AbstractSelectionKey} classes should be subclassed
 * directly; custom channel classes should extend the appropriate {@link
 * java.nio.channels.SelectableChannel} subclasses defined in this package.
 *
 * <a name="async"></a>
 *
 * <blockquote><table cellspacing=1 cellpadding=0 summary="Lists asynchronous channels and their descriptions">
 * <tr><th align="left">Asynchronous I/O</th><th align="left">Description</th></tr>
 * <tr><td valign=top><tt>{@link java.nio.channels.AsynchronousFileChannel}</tt></td>
 *     <td>An asynchronous channel for reading, writing, and manipulating a file</td></tr>
 * <tr><td valign=top><tt>{@link java.nio.channels.AsynchronousSocketChannel}</tt></td>
 *     <td>An asynchronous channel to a stream-oriented connecting socket</td></tr>
 * <tr><td valign=top><tt>{@link java.nio.channels.AsynchronousServerSocketChannel}&nbsp;&nbsp;</tt></td>
 *     <td>An asynchronous channel to a stream-oriented listening socket</td></tr>
 * <tr><td valign=top><tt>{@link java.nio.channels.CompletionHandler}</tt></td>
 *     <td>A handler for consuming the result of an asynchronous operation</td></tr>
 * <tr><td valign=top><tt>{@link java.nio.channels.AsynchronousChannelGroup}</tt></td>
 *     <td>A grouping of asynchronous channels for the purpose of resource sharing</td></tr>
 * </table></blockquote>
 *
 * <p> {@link java.nio.channels.AsynchronousChannel Asynchronous channels} are a
 * special type of channel capable of asynchronous I/O operations. Asynchronous
 * channels are non-blocking and define methods to initiate asynchronous
 * operations, returning a {@link java.util.concurrent.Future} representing the
 * pending result of each operation. The {@code Future} can be used to poll or
 * wait for the result of the operation. Asynchronous I/O operations can also
 * specify a {@link java.nio.channels.CompletionHandler} to invoke when the
 * operation completes. A completion handler is user provided code that is executed
 * to consume the result of I/O operation.
 *
 * <p> This package defines asynchronous-channel classes that are connected to
 * a stream-oriented connecting or listening socket, or a datagram-oriented socket.
 * It also defines the {@link java.nio.channels.AsynchronousFileChannel} class
 * for asynchronous reading, writing, and manipulating a file. As with the {@link
 * java.nio.channels.FileChannel} it supports operations to truncate the file
 * to a specific size, force updates to the file to be written to the storage
 * device, or acquire locks on the whole file or on a specific region of the file.
 * Unlike the {@code FileChannel} it does not define methods for mapping a
 * region of the file directly into memory. Where memory mapped I/O is required,
 * then a {@code FileChannel} can be used.
 *
 * <p> Asynchronous channels are bound to an asynchronous channel group for the
 * purpose of resource sharing. A group has an associated {@link
 * java.util.concurrent.ExecutorService} to which tasks are submitted to handle
 * I/O events and dispatch to completion handlers that consume the result of
 * asynchronous operations performed on channels in the group. The group can
 * optionally be specified when creating the channel or the channel can be bound
 * to a <em>default group</em>. Sophisticated users may wish to create their
 * own asynchronous channel groups or configure the {@code ExecutorService}
 * that will be used for the default group.
 *
 * <p> As with selectors, the implementation of asynchronous channels can be
 * replaced by "plugging in" an alternative definition or instance of the {@link
 * java.nio.channels.spi.AsynchronousChannelProvider} class defined in the
 * <tt>{@link java.nio.channels.spi}</tt> package.  It is not expected that many
 * developers will actually make use of this facility; it is provided primarily
 * so that sophisticated users can take advantage of operating-system-specific
 * asynchronous I/O mechanisms when very high performance is required.
 *
 * <hr width="80%">
 * <p> Unless otherwise noted, passing a <tt>null</tt> argument to a constructor
 * or method in any class or interface in this package will cause a {@link
 * java.lang.NullPointerException NullPointerException} to be thrown.
 *
 * <p>
 *  定义通道,表示与能够执行I / O操作的实体的连接,例如文件和套接字;定义选择器,用于多路复用的非阻塞I / O操作
 * 
 *  <a name=\"channels\"> </a>
 * 
 * <blockquote> <table cellspacing = 1 cellpadding = 0 summary ="列出频道及其说明"> <tr> <th align ="left">频道</th>
 *  <th align ="left">说明</th> </td> I / O操作的关联</td> </td> </td> </td> </t> </t> <tr> <td valign = top> <tt>
 * &nbsp;&nbsp; <i> {@ link javaniochannelsReadableByteChannel} </i> </tt> td> </tr> <tr> <td valign = top>
 *  <tt>&nbsp;&nbsp;&nbsp;&nbsp; <i> {@ link javaniochannelsScatteringByteChannel}&nbsp;&nbsp; </i> </tt>
 *  <td>可读入缓冲区序列</td> </tr> <tr> <td valign = top> <tt>&nbsp;&nbsp; <i> {@ link javaniochannelsWritableByteChannel}
 *  </i> > </td> <td>可以从缓冲区写入</td> </tr> <tr> <td valign = top> <tt>&nbsp;&nbsp;&nbsp;&nbsp; <i> {@ link javaniochannelsGatheringByteChannel}
 *  </i> </tt> </td> <td>可以从一系列缓冲区写入</td> </tr> <tr> <td valign = top> <tt>&nbsp;&nbsp; < i> {@ link javaniochannelsByteChannel}
 *  </i> </tt> </td> <td>可以读取/写入缓冲区</td> </tr> <tr> <td valign = top> tt>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&
 * nbsp;&nbsp; <i> {@ link javaniochannelsSeekableByteChannel} </i> </tt> </td> <td> A {@code ByteChannel}
 * 的字节</td> </tr> <tr> <td valign = top> <tt>&nbsp;&nbsp; <i> {@ link javaniochannelsAsynchronousChannel}
 *  </i> </tt> </td> <td>异步I / O操作</td> </tr> <tr> <td valign = top> <tt>&nbsp;&nbsp;&nbsp;&nbsp; <i> {@ link javaniochannelsAsynchronousByteChannel}
 *  </i> </tt> </td> <td>可以异步读取和写入字节</td> </tr> <tr> <td valign = top> <tt>&nbsp; {@link javaniochannelsNetworkChannel}
 *  </i> </tt> </td> <td>通向网络套接字的频道</td> </tr> <tr> <td valign = top> <tt>&nbsp;&nbsp; &nbsp;&nbsp; <i> 
 * {@ link javaniochannels MulticastChannel} </i> </tt> </td> <td>可以加入互联网协议(IP)多播群组</td> </tr> <tr> <td valign = top>
 *  <tt> {@ link javaniochannelsChannels} </tt> </td> <td>频道/流互操作的实用方法</td> </tr> </table> </blockquote>
 * 。
 * 
 * </>> </i>表示到诸如硬件设备,文件,网络套接字或程序组件之类的能够执行一个或多个不同的I / O操作的实体的打开连接,例如读取或写入如{@link javaniochannelsChannel}
 * 接口中所述,通道可以是打开或关闭的,并且它们都可以异步关闭</i>和<i>可中断</i>。
 * 
 *  <p> {@link javaniochannelsChannel}接口由其他几个接口扩展
 * 
 * <p> {@link javaniochannelsReadableByteChannel}接口指定了一个{@link javaniochannelsReadableByteChannel#read read}
 * 方法,将字节从通道读入缓冲区;类似地,{@link javaniochannelsWritableByteChannel}接口指定了一个将字节从缓冲区写入通道的{@link javaniochannelsWritableByteChannel#write write}
 * 方法。
 * {@link javaniochannelsByteChannel}接口将这两个接口统一为通道的通用情况,和写字节{@link javaniochannelsSeekableByteChannel}接口
 * 将{@code ByteChannel}接口扩展到{@link javaniochannelsSeekableByteChannel#position()query}和{@link javaniochannelsSeekableByteChannel#position(long)modify}
 * 渠道的当前位置及其{@link javaniochannelsSeekableByteChannel#size size}。
 * 
 * 
 * <p> {@link javaniochannelsNetworkChannel}接口指定了{@link javaniochannelsNetworkChannel#bind bind}渠道套接字的方法
 * ,获取套接字绑定到的地址,以及{@link javaniochannelsNetworkChannel#getOption get}和{@link javaniochannelsNetworkChannel#setOption set}
 * 套接字选项{@link javaniochannelsMulticastChannel}接口规定了加入互联网协议(IP)多播组的方法。
 * 
 * <p> {@link javaniochannelsChannels}实用程序类定义了支持<tt> {@ link javaio} </tt>包的流类与此包的通道类的互操作的静态方法可以构造合适的通道来
 * 自{@link javaioInputStream}或{@link javaioOutputStream},相反,可以从通道构造{@link javaioInputStream}或{@link javaioOutputStream}
 *  A可以构造使用给定字符集的{@link javaioReader}以解码来自给定可读字节通道的字节,并且相反地,可以构造{@link javaioWriter},其使用给定的字符集将字符编码为字节并将
 * 其写入给定的可写字节通道。
 * 
 * <blockquote> <table cellspacing = 1 cellpadding = 0 summary ="列出文件通道及其描述"> <tr> <th align ="left">文件通
 * 道</th> <th align ="left">描述< th> </tr> <tr> <td valign = top> <tt> {@ link javaniochannelsFileChannel}
 *  </tt> </td> <td>读取,写入,映射和操作文件</td> </tr > <tr> <td valign = top> <tt> {@ link javaniochannelsFileLock}
 *  </tt> </td> <td>锁定(区域)文件</td> <td valign = top> <tt> {@ link javanioMappedByteBuffer}&nbsp;&nbsp; </tt>
 *  </td> <td>映射到文件区域的直接字节缓冲区</td> <// table> </blockquote>。
 * 
 * <p> {@link javaniochannelsFileChannel}类支持从连接到文件的通道读取字节和写入字节的常见操作,以及查询和修改当前文件位置并将文件截断为特定size定义用于获取整个文件
 * 或文件特定区域上的锁的方法;这些方法返回{@link javaniochannelsFileLock}类的实例。
 * 最后,它定义了用于强制更新文件以被写入包含它的存储设备的方法,用于在文件和其它通道之间有效地传输字节,以及用于映射区域的文件直接进入内存。
 * 
 * <p>通过调用其中一个静态{@link javaniochannelsFileChannel#open open}方法或调用{@link javaioFileInputStream} {@link javaioFileOutputStream}
 * 的{@code getChannel}方法来创建{@code FileChannel} ,或{@link javaioRandomAccessFile}返回连接到与<tt> {@ link javaio}
 *  </tt>类相同的底层文件的文件通道。
 * 
 * <a name=\"multiplex\"> </a> <blockquote> <table cellspacing = 1 cellpadding = 0 summary ="列出多路复用,非阻塞通道及其描述">
 *  <tr> <th align ="left">多路复用,非阻塞I / O </th> <th align ="left"> <p>描述</th> </tr> <tr> <td valign = top>
 *  <tt> {@ link javaniochannelsSelectableChannel} tt> </td> <td>可以复用的频道</td> </tr> <tr> <td valign = top>
 *  <tt>&nbsp;&nbsp; {@ link javaniochannelsDatagramChannel} </td> <td>通往数据报导向插座的通道</td> </tr> <tr> <td valign = top>
 *  <tt>&nbsp; {@ link javaniochannelsPipeSinkChannel} </td> <td>管道的写入结束</td> </tr> <tr> <td valign = top>
 *  <tt>&nbsp;&nbsp; {@ link javaniochannelsPipeSourceChannel} </tt> </td> <td>读取管道末端</td> </tr> <tr> <td valign = top>
 *  <tt>&nbsp;&nbsp; {@ link javaniochannelsServerSocketChannel}&nbsp;&nbsp; </tt> </td> <td>流向面向流的侦听套接字
 * 
 * @since 1.4
 * @author Mark Reinhold
 * @author JSR-51 Expert Group
 */

package java.nio.channels;
