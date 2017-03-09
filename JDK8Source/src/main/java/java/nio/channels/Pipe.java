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
import java.nio.channels.spi.*;


/**
 * A pair of channels that implements a unidirectional pipe.
 *
 * <p> A pipe consists of a pair of channels: A writable {@link
 * Pipe.SinkChannel sink} channel and a readable {@link Pipe.SourceChannel source}
 * channel.  Once some bytes are written to the sink channel they can be read
 * from source channel in exactlyAthe order in which they were written.
 *
 * <p> Whether or not a thread writing bytes to a pipe will block until another
 * thread reads those bytes, or some previously-written bytes, from the pipe is
 * system-dependent and therefore unspecified.  Many pipe implementations will
 * buffer up to a certain number of bytes between the sink and source channels,
 * but such buffering should not be assumed.  </p>
 *
 *
 * <p>
 *  实现单向管道的一对通道。
 * 
 *  <p>管道由一对通道组成：可写的{@link Pipe.SinkChannel sink}通道和可读的{@link Pipe.SourceChannel源}通道。
 * 一旦一些字节被写入宿信道,它们就可以按照它们被写入的顺序从源信道读取。
 * 
 *  <p>在向另一个线程读取这些字节或某些先前写入的字节之前,向管道写入字节的线程是否会阻塞,这取决于系统,因此未指定。许多管道实现将在宿和源通道之间缓冲达一定数量的字节,但是不应该假定这样的缓冲。
 *  </p>。
 * 
 * 
 * @author Mark Reinhold
 * @author JSR-51 Expert Group
 * @since 1.4
 */

public abstract class Pipe {

    /**
     * A channel representing the readable end of a {@link Pipe}.
     *
     * <p>
     *  表示{@link Pipe}的可读端的频道。
     * 
     * 
     * @since 1.4
     */
    public static abstract class SourceChannel
        extends AbstractSelectableChannel
        implements ReadableByteChannel, ScatteringByteChannel
    {
        /**
         * Constructs a new instance of this class.
         *
         * <p>
         *  构造此类的新实例。
         * 
         * 
         * @param  provider
         *         The selector provider
         */
        protected SourceChannel(SelectorProvider provider) {
            super(provider);
        }

        /**
         * Returns an operation set identifying this channel's supported
         * operations.
         *
         * <p> Pipe-source channels only support reading, so this method
         * returns {@link SelectionKey#OP_READ}.  </p>
         *
         * <p>
         *  返回标识此通道支持的操作的操作集。
         * 
         *  <p>管道源通道只支持读取,因此此方法返回{@link SelectionKey#OP_READ}。 </p>
         * 
         * 
         * @return  The valid-operation set
         */
        public final int validOps() {
            return SelectionKey.OP_READ;
        }

    }

    /**
     * A channel representing the writable end of a {@link Pipe}.
     *
     * <p>
     *  表示{@link Pipe}的可写端的通道。
     * 
     * 
     * @since 1.4
     */
    public static abstract class SinkChannel
        extends AbstractSelectableChannel
        implements WritableByteChannel, GatheringByteChannel
    {
        /**
         * Initializes a new instance of this class.
         *
         * <p>
         *  初始化此类的新实例。
         * 
         * 
         * @param  provider
         *         The selector provider
         */
        protected SinkChannel(SelectorProvider provider) {
            super(provider);
        }

        /**
         * Returns an operation set identifying this channel's supported
         * operations.
         *
         * <p> Pipe-sink channels only support writing, so this method returns
         * {@link SelectionKey#OP_WRITE}.  </p>
         *
         * <p>
         *  返回标识此通道支持的操作的操作集。
         * 
         *  <p>管道接收通道只支持写入,因此此方法返回{@link SelectionKey#OP_WRITE}。 </p>
         * 
         * 
         * @return  The valid-operation set
         */
        public final int validOps() {
            return SelectionKey.OP_WRITE;
        }

    }

    /**
     * Initializes a new instance of this class.
     * <p>
     *  初始化此类的新实例。
     * 
     */
    protected Pipe() { }

    /**
     * Returns this pipe's source channel.
     *
     * <p>
     *  返回此管道的源渠道。
     * 
     * 
     * @return  This pipe's source channel
     */
    public abstract SourceChannel source();

    /**
     * Returns this pipe's sink channel.
     *
     * <p>
     * 返回此管道的sink通道。
     * 
     * 
     * @return  This pipe's sink channel
     */
    public abstract SinkChannel sink();

    /**
     * Opens a pipe.
     *
     * <p> The new pipe is created by invoking the {@link
     * java.nio.channels.spi.SelectorProvider#openPipe openPipe} method of the
     * system-wide default {@link java.nio.channels.spi.SelectorProvider}
     * object.  </p>
     *
     * <p>
     *  打开管道。
     * 
     *  <p>新管道是通过调用系统级默认{@link java.nio.channels.spi.SelectorProvider}对象的{@link java.nio.channels.spi.SelectorProvider#openPipe openPipe}
     * 
     * @return  A new pipe
     *
     * @throws  IOException
     *          If an I/O error occurs
     */
    public static Pipe open() throws IOException {
        return SelectorProvider.provider().openPipe();
    }

}
