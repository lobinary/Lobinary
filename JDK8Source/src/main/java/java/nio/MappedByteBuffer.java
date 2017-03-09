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

package java.nio;

import java.io.FileDescriptor;
import sun.misc.Unsafe;


/**
 * A direct byte buffer whose content is a memory-mapped region of a file.
 *
 * <p> Mapped byte buffers are created via the {@link
 * java.nio.channels.FileChannel#map FileChannel.map} method.  This class
 * extends the {@link ByteBuffer} class with operations that are specific to
 * memory-mapped file regions.
 *
 * <p> A mapped byte buffer and the file mapping that it represents remain
 * valid until the buffer itself is garbage-collected.
 *
 * <p> The content of a mapped byte buffer can change at any time, for example
 * if the content of the corresponding region of the mapped file is changed by
 * this program or another.  Whether or not such changes occur, and when they
 * occur, is operating-system dependent and therefore unspecified.
 *
 * <a name="inaccess"></a><p> All or part of a mapped byte buffer may become
 * inaccessible at any time, for example if the mapped file is truncated.  An
 * attempt to access an inaccessible region of a mapped byte buffer will not
 * change the buffer's content and will cause an unspecified exception to be
 * thrown either at the time of the access or at some later time.  It is
 * therefore strongly recommended that appropriate precautions be taken to
 * avoid the manipulation of a mapped file by this program, or by a
 * concurrently running program, except to read or write the file's content.
 *
 * <p> Mapped byte buffers otherwise behave no differently than ordinary direct
 * byte buffers. </p>
 *
 *
 * <p>
 *  直接字节缓冲器,其内容是文件的内存映射区域。
 * 
 *  <p>映射字节缓冲区通过{@link java.nio.channels.FileChannel#map FileChannel.map}方法创建。
 * 此类使用特定于内存映射文件区域的操作扩展{@link ByteBuffer}类。
 * 
 *  <p>映射的字节缓冲区及其表示的文件映射保持有效,直到缓冲区本身被垃圾回收。
 * 
 *  <p>映射字节缓冲区的内容可以随时更改,例如,如果映射文件的相应区域的内容由此程序或其他程序更改。无论这些改变是否发生,以及何时发生,都是与操作系统相关的,因此未指定。
 * 
 *  <a name="inaccess"> </a> <p>映射字节缓冲区的全部或部分可能随时无法访问,例如,如果映射文件被截断。
 * 尝试访问映射字节缓冲区的不可访问区域将不会更改缓冲区的内容,并会导致在访问时或稍后时间抛出未指定的异常。
 * 因此,强烈建议采取适当的预防措施,以避免该程序或同时运行的程序对映射文件的操作,但读取或写入文件的内容除外。
 * 
 * <p>映射字节缓冲区的行为与普通直接字节​​缓冲区不同。 </p>
 * 
 * 
 * @author Mark Reinhold
 * @author JSR-51 Expert Group
 * @since 1.4
 */

public abstract class MappedByteBuffer
    extends ByteBuffer
{

    // This is a little bit backwards: By rights MappedByteBuffer should be a
    // subclass of DirectByteBuffer, but to keep the spec clear and simple, and
    // for optimization purposes, it's easier to do it the other way around.
    // This works because DirectByteBuffer is a package-private class.

    // For mapped buffers, a FileDescriptor that may be used for mapping
    // operations if valid; null if the buffer is not mapped.
    private final FileDescriptor fd;

    // This should only be invoked by the DirectByteBuffer constructors
    //
    MappedByteBuffer(int mark, int pos, int lim, int cap, // package-private
                     FileDescriptor fd)
    {
        super(mark, pos, lim, cap);
        this.fd = fd;
    }

    MappedByteBuffer(int mark, int pos, int lim, int cap) { // package-private
        super(mark, pos, lim, cap);
        this.fd = null;
    }

    private void checkMapped() {
        if (fd == null)
            // Can only happen if a luser explicitly casts a direct byte buffer
            throw new UnsupportedOperationException();
    }

    // Returns the distance (in bytes) of the buffer from the page aligned address
    // of the mapping. Computed each time to avoid storing in every direct buffer.
    private long mappingOffset() {
        int ps = Bits.pageSize();
        long offset = address % ps;
        return (offset >= 0) ? offset : (ps + offset);
    }

    private long mappingAddress(long mappingOffset) {
        return address - mappingOffset;
    }

    private long mappingLength(long mappingOffset) {
        return (long)capacity() + mappingOffset;
    }

    /**
     * Tells whether or not this buffer's content is resident in physical
     * memory.
     *
     * <p> A return value of <tt>true</tt> implies that it is highly likely
     * that all of the data in this buffer is resident in physical memory and
     * may therefore be accessed without incurring any virtual-memory page
     * faults or I/O operations.  A return value of <tt>false</tt> does not
     * necessarily imply that the buffer's content is not resident in physical
     * memory.
     *
     * <p> The returned value is a hint, rather than a guarantee, because the
     * underlying operating system may have paged out some of the buffer's data
     * by the time that an invocation of this method returns.  </p>
     *
     * <p>
     *  告诉这个缓冲区的内容是否驻留在物理内存中。
     * 
     *  <p>返回值<tt> true </tt>意味着该缓冲区中的所有数据很可能驻留在物理内存中,因此可以访问,而不会导致任何虚拟内存页错误或I / O操作。
     * 返回值<tt> false </tt>并不一定意味着缓冲区的内容不驻留在物理内存中。
     * 
     *  <p>返回的值是一个提示,而不是保证,因为底层操作系统可能在调用此方法的时候调出了一些缓冲区的数据。 </p>
     * 
     * 
     * @return  <tt>true</tt> if it is likely that this buffer's content
     *          is resident in physical memory
     */
    public final boolean isLoaded() {
        checkMapped();
        if ((address == 0) || (capacity() == 0))
            return true;
        long offset = mappingOffset();
        long length = mappingLength(offset);
        return isLoaded0(mappingAddress(offset), length, Bits.pageCount(length));
    }

    // not used, but a potential target for a store, see load() for details.
    private static byte unused;

    /**
     * Loads this buffer's content into physical memory.
     *
     * <p> This method makes a best effort to ensure that, when it returns,
     * this buffer's content is resident in physical memory.  Invoking this
     * method may cause some number of page faults and I/O operations to
     * occur. </p>
     *
     * <p>
     *  将此缓冲区的内容加载到物理内存中。
     * 
     *  <p>此方法尽力确保在返回时,此缓冲区的内容驻留在物理内存中。调用此方法可能会导致发生一定数量的页面错误和I / O操作。 </p>
     * 
     * 
     * @return  This buffer
     */
    public final MappedByteBuffer load() {
        checkMapped();
        if ((address == 0) || (capacity() == 0))
            return this;
        long offset = mappingOffset();
        long length = mappingLength(offset);
        load0(mappingAddress(offset), length);

        // Read a byte from each page to bring it into memory. A checksum
        // is computed as we go along to prevent the compiler from otherwise
        // considering the loop as dead code.
        Unsafe unsafe = Unsafe.getUnsafe();
        int ps = Bits.pageSize();
        int count = Bits.pageCount(length);
        long a = mappingAddress(offset);
        byte x = 0;
        for (int i=0; i<count; i++) {
            x ^= unsafe.getByte(a);
            a += ps;
        }
        if (unused != 0)
            unused = x;

        return this;
    }

    /**
     * Forces any changes made to this buffer's content to be written to the
     * storage device containing the mapped file.
     *
     * <p> If the file mapped into this buffer resides on a local storage
     * device then when this method returns it is guaranteed that all changes
     * made to the buffer since it was created, or since this method was last
     * invoked, will have been written to that device.
     *
     * <p> If the file does not reside on a local device then no such guarantee
     * is made.
     *
     * <p> If this buffer was not mapped in read/write mode ({@link
     * java.nio.channels.FileChannel.MapMode#READ_WRITE}) then invoking this
     * method has no effect. </p>
     *
     * <p>
     *  强制对此缓冲区内容所做的任何更改将写入包含映射文件的存储设备。
     * 
     *  <p>如果映射到此缓冲区的文件驻留在本地存储设备上,那么当此方法返回时,它保证自从创建以来对缓冲区所做的所有更改,或者自上次调用此方法以来,将被写入到设备。
     * 
     * 
     * @return  This buffer
     */
    public final MappedByteBuffer force() {
        checkMapped();
        if ((address != 0) && (capacity() != 0)) {
            long offset = mappingOffset();
            force0(fd, mappingAddress(offset), mappingLength(offset));
        }
        return this;
    }

    private native boolean isLoaded0(long address, long length, int pageCount);
    private native void load0(long address, long length);
    private native void force0(FileDescriptor fd, long address, long length);
}
