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

package java.nio.channels;

import java.io.IOException;

/**
 * A token representing a lock on a region of a file.
 *
 * <p> A file-lock object is created each time a lock is acquired on a file via
 * one of the {@link FileChannel#lock(long,long,boolean) lock} or {@link
 * FileChannel#tryLock(long,long,boolean) tryLock} methods of the
 * {@link FileChannel} class, or the {@link
 * AsynchronousFileChannel#lock(long,long,boolean,Object,CompletionHandler) lock}
 * or {@link AsynchronousFileChannel#tryLock(long,long,boolean) tryLock}
 * methods of the {@link AsynchronousFileChannel} class.
 *
 * <p> A file-lock object is initially valid.  It remains valid until the lock
 * is released by invoking the {@link #release release} method, by closing the
 * channel that was used to acquire it, or by the termination of the Java
 * virtual machine, whichever comes first.  The validity of a lock may be
 * tested by invoking its {@link #isValid isValid} method.
 *
 * <p> A file lock is either <i>exclusive</i> or <i>shared</i>.  A shared lock
 * prevents other concurrently-running programs from acquiring an overlapping
 * exclusive lock, but does allow them to acquire overlapping shared locks.  An
 * exclusive lock prevents other programs from acquiring an overlapping lock of
 * either type.  Once it is released, a lock has no further effect on the locks
 * that may be acquired by other programs.
 *
 * <p> Whether a lock is exclusive or shared may be determined by invoking its
 * {@link #isShared isShared} method.  Some platforms do not support shared
 * locks, in which case a request for a shared lock is automatically converted
 * into a request for an exclusive lock.
 *
 * <p> The locks held on a particular file by a single Java virtual machine do
 * not overlap.  The {@link #overlaps overlaps} method may be used to test
 * whether a candidate lock range overlaps an existing lock.
 *
 * <p> A file-lock object records the file channel upon whose file the lock is
 * held, the type and validity of the lock, and the position and size of the
 * locked region.  Only the validity of a lock is subject to change over time;
 * all other aspects of a lock's state are immutable.
 *
 * <p> File locks are held on behalf of the entire Java virtual machine.
 * They are not suitable for controlling access to a file by multiple
 * threads within the same virtual machine.
 *
 * <p> File-lock objects are safe for use by multiple concurrent threads.
 *
 *
 * <a name="pdep"></a><h2> Platform dependencies </h2>
 *
 * <p> This file-locking API is intended to map directly to the native locking
 * facility of the underlying operating system.  Thus the locks held on a file
 * should be visible to all programs that have access to the file, regardless
 * of the language in which those programs are written.
 *
 * <p> Whether or not a lock actually prevents another program from accessing
 * the content of the locked region is system-dependent and therefore
 * unspecified.  The native file-locking facilities of some systems are merely
 * <i>advisory</i>, meaning that programs must cooperatively observe a known
 * locking protocol in order to guarantee data integrity.  On other systems
 * native file locks are <i>mandatory</i>, meaning that if one program locks a
 * region of a file then other programs are actually prevented from accessing
 * that region in a way that would violate the lock.  On yet other systems,
 * whether native file locks are advisory or mandatory is configurable on a
 * per-file basis.  To ensure consistent and correct behavior across platforms,
 * it is strongly recommended that the locks provided by this API be used as if
 * they were advisory locks.
 *
 * <p> On some systems, acquiring a mandatory lock on a region of a file
 * prevents that region from being {@link java.nio.channels.FileChannel#map
 * <i>mapped into memory</i>}, and vice versa.  Programs that combine
 * locking and mapping should be prepared for this combination to fail.
 *
 * <p> On some systems, closing a channel releases all locks held by the Java
 * virtual machine on the underlying file regardless of whether the locks were
 * acquired via that channel or via another channel open on the same file.  It
 * is strongly recommended that, within a program, a unique channel be used to
 * acquire all locks on any given file.
 *
 * <p> Some network filesystems permit file locking to be used with
 * memory-mapped files only when the locked regions are page-aligned and a
 * whole multiple of the underlying hardware's page size.  Some network
 * filesystems do not implement file locks on regions that extend past a
 * certain position, often 2<sup>30</sup> or 2<sup>31</sup>.  In general, great
 * care should be taken when locking files that reside on network filesystems.
 *
 *
 * <p>
 *  表示文件区域上的锁定的令牌。
 * 
 *  <p>每次通过{@link FileChannel#lock(long,long,boolean)lock}或{@link FileChannel#tryLock(long,long, boolean)tryLock(long,long,boolean){@link FileChannel}
 * 类或{@link AsynchronousFileChannel#lock(long,long,boolean,Object,CompletionHandler)锁}或{@link AsynchronousFileChannel }
 *  {@link AsynchronousFileChannel}类的方法。
 * 
 *  <p>文件锁对象最初有效。它通过调用{@link #release release}方法,通过关闭用于获取它的通道或者通过终止Java虚拟机(以先到者为准)来释放锁定之前保持有效。
 * 可以通过调用其{@link #isValid isValid}方法来测试锁的有效性。
 * 
 *  <p>档案锁定为<i>独占</i>或<i>共享</i>。共享锁防止其他并发运行的程序获取重叠的互斥锁,但允许它们获取重叠的共享锁。独占锁防止其他程序获取任一类型的重叠锁。
 * 一旦它被释放,锁对其他程序可能获得的锁没有进一步的影响。
 * 
 * <p>锁是排他还是共享可以通过调用其{@link #isShared isShared}方法来确定。某些平台不支持共享锁,在这种情况下,共享锁的请求会自动转换为独占锁的请求。
 * 
 *  <p>由单个Java虚拟机保存在特定文件上的锁不重叠。 {@link #overlaps overlapaps}方法可用于测试候选锁定范围是否与现有锁定重叠。
 * 
 *  <p>文件锁对象记录保存锁的文件通道,锁的类型和有效性,锁定区域的位置和大小。只有锁的有效性随时间而改变;锁的状态的所有其他方面是不变的。
 * 
 *  <p>文件锁定代表整个Java虚拟机。它们不适合控制同一虚拟机中的多个线程对文件的访问。
 * 
 *  <p>文件锁对象可以安全地被多个并发线程使用。
 * 
 *  <a name="pdep"> </a> <h2>平台相关性</h2>
 * 
 *  <p>此文件锁定API旨在直接映射到底层操作系统的本机锁定设施。因此,保存在文件上的锁应该对具有访问该文件的所有程序是可见的,而不管这些程序被写入的语言。
 * 
 * <p>锁是否实际阻止其他程序访问锁定区域的内容是系统相关的,因此未指定。一些系统的本地文件锁定设施仅仅是<i>咨询</i>,意味着程序必须合作地观察已知的锁定协议以便保证数据完整性。
 * 在其他系统上,本机文件锁是<i>强制的</i>,意味着如果一个程序锁定文件的区域,则实际上阻止其他程序以违反锁的方式访问该区域。在其他系统上,无论本地文件锁是建议还是强制都可以在每个文件的基础上配置。
 * 为了确保跨平台的一致和正确的行为,强烈建议使用此API提供的锁,就像它们是咨询锁一样。
 * 
 *  <p>在某些系统上,获取文件区域的强制性锁定会阻止该区域{@link java.nio.channels.FileChannel#map <i>映射到内存</i>},反之亦然。
 * 应该准备结合锁定和映射的程序,以使此组合失败。
 * 
 *  <p>在某些系统上,关闭通道会释放底层文件上的Java虚拟机所拥有的所有锁,而不管锁是通过该通道还是通过在同一文件上打开的另一个通道获取的。
 * 强烈建议在程序中,使用唯一的通道来获取任何给定文件上的所有锁。
 * 
 * <p>某些网络文件系统只有在锁定的区域是页面对齐和底层硬件页面大小的整数倍时才允许将文件锁定用于内存映射文件。
 * 某些网络文件系统不会在延伸超过某个位置(通常为2 <sup> 30 </sup>或2 <sup> 31 </sup>)的区域上实施文件锁定。一般来说,当锁定驻留在网络文件系统上的文件时应该格外小心。
 * 
 * 
 * @author Mark Reinhold
 * @author JSR-51 Expert Group
 * @since 1.4
 */

public abstract class FileLock implements AutoCloseable {

    private final Channel channel;
    private final long position;
    private final long size;
    private final boolean shared;

    /**
     * Initializes a new instance of this class.
     *
     * <p>
     *  初始化此类的新实例。
     * 
     * 
     * @param  channel
     *         The file channel upon whose file this lock is held
     *
     * @param  position
     *         The position within the file at which the locked region starts;
     *         must be non-negative
     *
     * @param  size
     *         The size of the locked region; must be non-negative, and the sum
     *         <tt>position</tt>&nbsp;+&nbsp;<tt>size</tt> must be non-negative
     *
     * @param  shared
     *         <tt>true</tt> if this lock is shared,
     *         <tt>false</tt> if it is exclusive
     *
     * @throws IllegalArgumentException
     *         If the preconditions on the parameters do not hold
     */
    protected FileLock(FileChannel channel,
                       long position, long size, boolean shared)
    {
        if (position < 0)
            throw new IllegalArgumentException("Negative position");
        if (size < 0)
            throw new IllegalArgumentException("Negative size");
        if (position + size < 0)
            throw new IllegalArgumentException("Negative position + size");
        this.channel = channel;
        this.position = position;
        this.size = size;
        this.shared = shared;
    }

    /**
     * Initializes a new instance of this class.
     *
     * <p>
     *  初始化此类的新实例。
     * 
     * 
     * @param  channel
     *         The channel upon whose file this lock is held
     *
     * @param  position
     *         The position within the file at which the locked region starts;
     *         must be non-negative
     *
     * @param  size
     *         The size of the locked region; must be non-negative, and the sum
     *         <tt>position</tt>&nbsp;+&nbsp;<tt>size</tt> must be non-negative
     *
     * @param  shared
     *         <tt>true</tt> if this lock is shared,
     *         <tt>false</tt> if it is exclusive
     *
     * @throws IllegalArgumentException
     *         If the preconditions on the parameters do not hold
     *
     * @since 1.7
     */
    protected FileLock(AsynchronousFileChannel channel,
                       long position, long size, boolean shared)
    {
        if (position < 0)
            throw new IllegalArgumentException("Negative position");
        if (size < 0)
            throw new IllegalArgumentException("Negative size");
        if (position + size < 0)
            throw new IllegalArgumentException("Negative position + size");
        this.channel = channel;
        this.position = position;
        this.size = size;
        this.shared = shared;
    }

    /**
     * Returns the file channel upon whose file this lock was acquired.
     *
     * <p> This method has been superseded by the {@link #acquiredBy acquiredBy}
     * method.
     *
     * <p>
     *  返回获取此锁的文件通道。
     * 
     *  <p>此方法已被{@link #acquiredBy acquiredBy}方法取代。
     * 
     * 
     * @return  The file channel, or {@code null} if the file lock was not
     *          acquired by a file channel.
     */
    public final FileChannel channel() {
        return (channel instanceof FileChannel) ? (FileChannel)channel : null;
    }

    /**
     * Returns the channel upon whose file this lock was acquired.
     *
     * <p>
     *  返回获取此锁的文件所在的通道。
     * 
     * 
     * @return  The channel upon whose file this lock was acquired.
     *
     * @since 1.7
     */
    public Channel acquiredBy() {
        return channel;
    }

    /**
     * Returns the position within the file of the first byte of the locked
     * region.
     *
     * <p> A locked region need not be contained within, or even overlap, the
     * actual underlying file, so the value returned by this method may exceed
     * the file's current size.  </p>
     *
     * <p>
     *  返回锁定区域的第一个字节在文件中的位置。
     * 
     *  <p>锁定区域不需要包含在实际底层文件中,甚至不会重叠,因此此方法返回的值可能会超出文件的当前大小。 </p>
     * 
     * 
     * @return  The position
     */
    public final long position() {
        return position;
    }

    /**
     * Returns the size of the locked region in bytes.
     *
     * <p> A locked region need not be contained within, or even overlap, the
     * actual underlying file, so the value returned by this method may exceed
     * the file's current size.  </p>
     *
     * <p>
     *  返回锁定区域的大小(以字节为单位)。
     * 
     *  <p>锁定区域不需要包含在实际底层文件中,甚至不会重叠,因此此方法返回的值可能会超出文件的当前大小。 </p>
     * 
     * 
     * @return  The size of the locked region
     */
    public final long size() {
        return size;
    }

    /**
     * Tells whether this lock is shared.
     *
     * <p>
     *  告诉这个锁是否被共享。
     * 
     * 
     * @return <tt>true</tt> if lock is shared,
     *         <tt>false</tt> if it is exclusive
     */
    public final boolean isShared() {
        return shared;
    }

    /**
     * Tells whether or not this lock overlaps the given lock range.
     *
     * <p>
     *  告诉这个锁是否与给定的锁定范围重叠。
     * 
     * 
     * @param   position
     *          The starting position of the lock range
     * @param   size
     *          The size of the lock range
     *
     * @return  <tt>true</tt> if, and only if, this lock and the given lock
     *          range overlap by at least one byte
     */
    public final boolean overlaps(long position, long size) {
        if (position + size <= this.position)
            return false;               // That is below this
        if (this.position + this.size <= position)
            return false;               // This is below that
        return true;
    }

    /**
     * Tells whether or not this lock is valid.
     *
     * <p> A lock object remains valid until it is released or the associated
     * file channel is closed, whichever comes first.  </p>
     *
     * <p>
     *  告诉这个锁是否有效。
     * 
     *  <p>锁定对象保持有效,直到其释放或关闭相关联的文件通道(以先到为准)。 </p>
     * 
     * 
     * @return  <tt>true</tt> if, and only if, this lock is valid
     */
    public abstract boolean isValid();

    /**
     * Releases this lock.
     *
     * <p> If this lock object is valid then invoking this method releases the
     * lock and renders the object invalid.  If this lock object is invalid
     * then invoking this method has no effect.  </p>
     *
     * <p>
     *  释放此锁。
     * 
     * <p>如果此锁对象有效,则调用此方法会释放锁并使对象无效。如果此锁对象无效,则调用此方法不起作用。 </p>
     * 
     * 
     * @throws  ClosedChannelException
     *          If the channel that was used to acquire this lock
     *          is no longer open
     *
     * @throws  IOException
     *          If an I/O error occurs
     */
    public abstract void release() throws IOException;

    /**
     * This method invokes the {@link #release} method. It was added
     * to the class so that it could be used in conjunction with the
     * automatic resource management block construct.
     *
     * <p>
     *  此方法调用{@link #release}方法。它被添加到类,以便它可以与自动资源管理块结构一起使用。
     * 
     * 
     * @since 1.7
     */
    public final void close() throws IOException {
        release();
    }

    /**
     * Returns a string describing the range, type, and validity of this lock.
     *
     * <p>
     *  返回描述此锁定的范围,类型和有效性的字符串。
     * 
     * @return  A descriptive string
     */
    public final String toString() {
        return (this.getClass().getName()
                + "[" + position
                + ":" + size
                + " " + (shared ? "shared" : "exclusive")
                + " " + (isValid() ? "valid" : "invalid")
                + "]");
    }

}
