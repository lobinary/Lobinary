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

package java.nio.channels;

import java.io.IOException;
import java.io.Closeable;


/**
 * A nexus for I/O operations.
 *
 * <p> A channel represents an open connection to an entity such as a hardware
 * device, a file, a network socket, or a program component that is capable of
 * performing one or more distinct I/O operations, for example reading or
 * writing.
 *
 * <p> A channel is either open or closed.  A channel is open upon creation,
 * and once closed it remains closed.  Once a channel is closed, any attempt to
 * invoke an I/O operation upon it will cause a {@link ClosedChannelException}
 * to be thrown.  Whether or not a channel is open may be tested by invoking
 * its {@link #isOpen isOpen} method.
 *
 * <p> Channels are, in general, intended to be safe for multithreaded access
 * as described in the specifications of the interfaces and classes that extend
 * and implement this interface.
 *
 *
 * <p>
 *  I / O操作的关联。
 * 
 *  信道表示到诸如硬件设备,文件,网络套接字或能够执行一个或多个不同的I / O操作(例如读或写)的程序组件的实体的打开连接。
 * 
 *  <p>频道已开启或已关闭。通道在创建时打开,一旦关闭,它保持关闭。一旦频道关闭,任何调用I / O操作的尝试都会导致{@link ClosedChannelException}被抛出。
 * 可以通过调用其{@link #isOpen isOpen}方法来测试频道是否打开。
 * 
 *  <p>一般来说,通道对于多线程访问是安全的,如扩展和实现此接口的接口和类的规范中所述。
 * 
 * 
 * @author Mark Reinhold
 * @author JSR-51 Expert Group
 * @since 1.4
 */

public interface Channel extends Closeable {

    /**
     * Tells whether or not this channel is open.
     *
     * <p>
     *  指出此频道是否已打开。
     * 
     * 
     * @return <tt>true</tt> if, and only if, this channel is open
     */
    public boolean isOpen();

    /**
     * Closes this channel.
     *
     * <p> After a channel is closed, any further attempt to invoke I/O
     * operations upon it will cause a {@link ClosedChannelException} to be
     * thrown.
     *
     * <p> If this channel is already closed then invoking this method has no
     * effect.
     *
     * <p> This method may be invoked at any time.  If some other thread has
     * already invoked it, however, then another invocation will block until
     * the first invocation is complete, after which it will return without
     * effect. </p>
     *
     * <p>
     *  关闭此频道。
     * 
     *  <p>频道关闭后,任何进一步尝试调用I / O操作会导致{@link ClosedChannelException}被抛出。
     * 
     * 
     * @throws  IOException  If an I/O error occurs
     */
    public void close() throws IOException;

}
