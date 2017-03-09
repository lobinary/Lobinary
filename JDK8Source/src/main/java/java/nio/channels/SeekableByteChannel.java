/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2011, Oracle and/or its affiliates. All rights reserved.
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

import java.nio.ByteBuffer;
import java.io.IOException;

/**
 * A byte channel that maintains a current <i>position</i> and allows the
 * position to be changed.
 *
 * <p> A seekable byte channel is connected to an entity, typically a file,
 * that contains a variable-length sequence of bytes that can be read and
 * written. The current position can be {@link #position() <i>queried</i>} and
 * {@link #position(long) <i>modified</i>}. The channel also provides access to
 * the current <i>size</i> of the entity to which the channel is connected. The
 * size increases when bytes are written beyond its current size; the size
 * decreases when it is {@link #truncate <i>truncated</i>}.
 *
 * <p> The {@link #position(long) position} and {@link #truncate truncate} methods
 * which do not otherwise have a value to return are specified to return the
 * channel upon which they are invoked. This allows method invocations to be
 * chained. Implementations of this interface should specialize the return type
 * so that method invocations on the implementation class can be chained.
 *
 * <p>
 *  保持当前位置并允许改变位置的字节通道。
 * 
 *  <p>可查找的字节通道连接到实体,通常为文件,其中包含可读取和写入的可变长度字节序列。
 * 当前位置可以是{@link #position()查询</i>}和{@link #position(长)<i>已修改</i>}。该信道还提供对该信道所连接的实体的当前大小的访问。
 * 当字节写入超出其当前大小时,大小增加;当{@link #truncate <i>截断</i>}时,大小减小。
 * 
 *  <p>指定了没有返回值的{@link #position(long)position}和{@link #truncate truncate}方法返回调用它们的通道。这允许方法调用链接。
 * 此接口的实现应专门化返回类型,以便可以链接对实现类的方法调用。
 * 
 * 
 * @since 1.7
 * @see java.nio.file.Files#newByteChannel
 */

public interface SeekableByteChannel
    extends ByteChannel
{
    /**
     * Reads a sequence of bytes from this channel into the given buffer.
     *
     * <p> Bytes are read starting at this channel's current position, and
     * then the position is updated with the number of bytes actually read.
     * Otherwise this method behaves exactly as specified in the {@link
     * ReadableByteChannel} interface.
     * <p>
     *  从该通道读取一个字节序列到给定的缓冲区。
     * 
     *  <p>从此通道的当前位置开始读取字节,然后使用实际读取的字节数更新位置。否则,此方法的行为与{@link ReadableByteChannel}接口中指定的完全相同。
     * 
     */
    @Override
    int read(ByteBuffer dst) throws IOException;

    /**
     * Writes a sequence of bytes to this channel from the given buffer.
     *
     * <p> Bytes are written starting at this channel's current position, unless
     * the channel is connected to an entity such as a file that is opened with
     * the {@link java.nio.file.StandardOpenOption#APPEND APPEND} option, in
     * which case the position is first advanced to the end. The entity to which
     * the channel is connected is grown, if necessary, to accommodate the
     * written bytes, and then the position is updated with the number of bytes
     * actually written. Otherwise this method behaves exactly as specified by
     * the {@link WritableByteChannel} interface.
     * <p>
     *  从给定缓冲区向此通道写入一个字节序列。
     * 
     * <p>从此频道的当前位置开始写入字节,除非频道已连接到某个实体,例如使用{@link java.nio.file.StandardOpenOption#APPEND APPEND}选项打开的文件,在这种
     * 情况下,位置首先前进到结束。
     * 如果需要,生长通道所连接的实体以容纳写入的字节,然后用实际写入的字节数来更新位置。否则,此方法的行为与{@link WritableByteChannel}接口指定的完全相同。
     * 
     */
    @Override
    int write(ByteBuffer src) throws IOException;

    /**
     * Returns this channel's position.
     *
     * <p>
     *  返回此频道的位置。
     * 
     * 
     * @return  This channel's position,
     *          a non-negative integer counting the number of bytes
     *          from the beginning of the entity to the current position
     *
     * @throws  ClosedChannelException
     *          If this channel is closed
     * @throws  IOException
     *          If some other I/O error occurs
     */
    long position() throws IOException;

    /**
     * Sets this channel's position.
     *
     * <p> Setting the position to a value that is greater than the current size
     * is legal but does not change the size of the entity.  A later attempt to
     * read bytes at such a position will immediately return an end-of-file
     * indication.  A later attempt to write bytes at such a position will cause
     * the entity to grow to accommodate the new bytes; the values of any bytes
     * between the previous end-of-file and the newly-written bytes are
     * unspecified.
     *
     * <p> Setting the channel's position is not recommended when connected to
     * an entity, typically a file, that is opened with the {@link
     * java.nio.file.StandardOpenOption#APPEND APPEND} option. When opened for
     * append, the position is first advanced to the end before writing.
     *
     * <p>
     *  设置此频道的位置。
     * 
     *  <p>将位置设置为大于当前大小的值是合法的,但不会更改实体的大小。稍后在这种位置读取字节的尝试将立即返回文件结束指示。
     * 以后在这样的位置写入字节的尝试将导致实体增长以容纳新的字节;在上一个文件结束和新写入的字节之间的任何字节的值是未指定的。
     * 
     *  <p>当连接到使用{@link java.nio.file.StandardOpenOption#APPEND APPEND}选项打开的实体(通常是文件)时,不建议设置渠道位置。
     * 当打开附加时,位置首先前进到结束,然后写入。
     * 
     * 
     * @param  newPosition
     *         The new position, a non-negative integer counting
     *         the number of bytes from the beginning of the entity
     *
     * @return  This channel
     *
     * @throws  ClosedChannelException
     *          If this channel is closed
     * @throws  IllegalArgumentException
     *          If the new position is negative
     * @throws  IOException
     *          If some other I/O error occurs
     */
    SeekableByteChannel position(long newPosition) throws IOException;

    /**
     * Returns the current size of entity to which this channel is connected.
     *
     * <p>
     *  返回此通道所连接的实体的当前大小。
     * 
     * 
     * @return  The current size, measured in bytes
     *
     * @throws  ClosedChannelException
     *          If this channel is closed
     * @throws  IOException
     *          If some other I/O error occurs
     */
    long size() throws IOException;

    /**
     * Truncates the entity, to which this channel is connected, to the given
     * size.
     *
     * <p> If the given size is less than the current size then the entity is
     * truncated, discarding any bytes beyond the new end. If the given size is
     * greater than or equal to the current size then the entity is not modified.
     * In either case, if the current position is greater than the given size
     * then it is set to that size.
     *
     * <p> An implementation of this interface may prohibit truncation when
     * connected to an entity, typically a file, opened with the {@link
     * java.nio.file.StandardOpenOption#APPEND APPEND} option.
     *
     * <p>
     *  将与此通道连接的实体截断为给定大小。
     * 
     * <p>如果给定的大小小于当前大小,则实体被截断,丢弃超出新端的任何字节。如果给定的大小大于或等于当前大小,则不修改实体。在任一情况下,如果当前位置大于给定大小,则将其设置为该大小。
     * 
     * @param  size
     *         The new size, a non-negative byte count
     *
     * @return  This channel
     *
     * @throws  NonWritableChannelException
     *          If this channel was not opened for writing
     * @throws  ClosedChannelException
     *          If this channel is closed
     * @throws  IllegalArgumentException
     *          If the new size is negative
     * @throws  IOException
     *          If some other I/O error occurs
     */
    SeekableByteChannel truncate(long size) throws IOException;
}
