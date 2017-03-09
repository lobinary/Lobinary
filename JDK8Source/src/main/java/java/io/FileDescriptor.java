/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2011, Oracle and/or its affiliates. All rights reserved.
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

package java.io;

import java.util.ArrayList;
import java.util.List;

/**
 * Instances of the file descriptor class serve as an opaque handle
 * to the underlying machine-specific structure representing an
 * open file, an open socket, or another source or sink of bytes.
 * The main practical use for a file descriptor is to create a
 * {@link FileInputStream} or {@link FileOutputStream} to contain it.
 *
 * <p>Applications should not create their own file descriptors.
 *
 * <p>
 *  文件描述符类的实例用作表示打开文件,开放套接字或另一个字节源或接收器的底层机器特定结构的不透明句柄。
 * 文件描述符的主要实际用途是创建一个{@link FileInputStream}或{@link FileOutputStream}来包含它。
 * 
 *  <p>应用程序不应创建自己的文件描述符。
 * 
 * 
 * @author  Pavani Diwanji
 * @since   JDK1.0
 */
public final class FileDescriptor {

    private int fd;

    private long handle;

    private Closeable parent;
    private List<Closeable> otherParents;
    private boolean closed;

    /**
     * Constructs an (invalid) FileDescriptor
     * object.
     * <p>
     *  构造一个(无效)FileDescriptor对象。
     * 
     */
    public /**/ FileDescriptor() {
        fd = -1;
        handle = -1;
    }

    static {
        initIDs();
    }

    // Set up JavaIOFileDescriptorAccess in SharedSecrets
    static {
        sun.misc.SharedSecrets.setJavaIOFileDescriptorAccess(
            new sun.misc.JavaIOFileDescriptorAccess() {
                public void set(FileDescriptor obj, int fd) {
                    obj.fd = fd;
                }

                public int get(FileDescriptor obj) {
                    return obj.fd;
                }

                public void setHandle(FileDescriptor obj, long handle) {
                    obj.handle = handle;
                }

                public long getHandle(FileDescriptor obj) {
                    return obj.handle;
                }
            }
        );
    }

    /**
     * A handle to the standard input stream. Usually, this file
     * descriptor is not used directly, but rather via the input stream
     * known as {@code System.in}.
     *
     * <p>
     *  fd = -1; handle = -1; }}
     * 
     *  static {initIDs(); }}
     * 
     *  //在SharedSecrets中设置JavaIOFileDescriptorAccess static {sun.misc.SharedSecrets.setJavaIOFileDescriptorAccess(new sun.misc.JavaIOFileDescriptorAccess(){public void set(FileDescriptor obj,int fd){obj.fd = fd;}
     * 。
     * 
     *  public int get(FileDescriptor obj){return obj.fd; }}
     * 
     *  public void setHandle(FileDescriptor obj,long handle){obj.handle = handle; }}
     * 
     *  public long getHandle(FileDescriptor obj){return obj.handle; }}); }}
     * 
     *  / **标准输入流的句柄。通常,这个文件描述符不直接使用,而是通过称为{@code System.in}的输入流。
     * 
     * 
     * @see     java.lang.System#in
     */
    public static final FileDescriptor in = standardStream(0);

    /**
     * A handle to the standard output stream. Usually, this file
     * descriptor is not used directly, but rather via the output stream
     * known as {@code System.out}.
     * <p>
     *  标准输出流的句柄。通常,这个文件描述符不直接使用,而是通过称为{@code System.out}的输出流。
     * 
     * 
     * @see     java.lang.System#out
     */
    public static final FileDescriptor out = standardStream(1);

    /**
     * A handle to the standard error stream. Usually, this file
     * descriptor is not used directly, but rather via the output stream
     * known as {@code System.err}.
     *
     * <p>
     * 标准错误流的句柄。通常,这个文件描述符不直接使用,而是通过称为{@code System.err}的输出流。
     * 
     * 
     * @see     java.lang.System#err
     */
    public static final FileDescriptor err = standardStream(2);

    /**
     * Tests if this file descriptor object is valid.
     *
     * <p>
     *  测试此文件描述符对象是否有效。
     * 
     * 
     * @return  {@code true} if the file descriptor object represents a
     *          valid, open file, socket, or other active I/O connection;
     *          {@code false} otherwise.
     */
    public boolean valid() {
        return ((handle != -1) || (fd != -1));
    }

    /**
     * Force all system buffers to synchronize with the underlying
     * device.  This method returns after all modified data and
     * attributes of this FileDescriptor have been written to the
     * relevant device(s).  In particular, if this FileDescriptor
     * refers to a physical storage medium, such as a file in a file
     * system, sync will not return until all in-memory modified copies
     * of buffers associated with this FileDesecriptor have been
     * written to the physical medium.
     *
     * sync is meant to be used by code that requires physical
     * storage (such as a file) to be in a known state  For
     * example, a class that provided a simple transaction facility
     * might use sync to ensure that all changes to a file caused
     * by a given transaction were recorded on a storage medium.
     *
     * sync only affects buffers downstream of this FileDescriptor.  If
     * any in-memory buffering is being done by the application (for
     * example, by a BufferedOutputStream object), those buffers must
     * be flushed into the FileDescriptor (for example, by invoking
     * OutputStream.flush) before that data will be affected by sync.
     *
     * <p>
     *  强制所有系统缓冲区与底层设备同步。此方法在所有已修改的数据和此FileDescriptor的属性已写入相关设备后返回。
     * 特别地,如果该FileDescriptor引用诸如文件系统中的文件之类的物理存储介质,则直到与该FileDesecriptor相关联的缓冲器的所有内存中修改的拷贝都被写入物理介质之前,同步才会返回。
     * 
     *  同步意味着需要物理存储(例如文件)的代码处于已知状态。例如,提供简单事务功能的类可以使用同步来确保由给定的文件引起的文件的所有更改交易记录在存储介质上。
     * 
     *  同步仅影响此FileDescriptor下游的缓冲区。
     * 如果应用程序正在执行任何内存中缓冲(例如,通过BufferedOutputStream对象),则这些缓冲区必须刷新到FileDescriptor中(例如,通过调用OutputStream.flush),
     * 然后该数据将受同步影响。
     *  同步仅影响此FileDescriptor下游的缓冲区。
     * 
     * 
     * @exception SyncFailedException
     *        Thrown when the buffers cannot be flushed,
     *        or because the system cannot guarantee that all the
     *        buffers have been synchronized with physical media.
     * @since     JDK1.1
     */
    public native void sync() throws SyncFailedException;

    /* This routine initializes JNI field offsets for the class */
    private static native void initIDs();

    private static native long set(int d);

    private static FileDescriptor standardStream(int fd) {
        FileDescriptor desc = new FileDescriptor();
        desc.handle = set(fd);
        return desc;
    }

    /*
     * Package private methods to track referents.
     * If multiple streams point to the same FileDescriptor, we cycle
     * through the list of all referents and call close()
     * <p>
     *  包私人方法来跟踪指示。如果多个流指向同一个FileDescriptor,我们循环遍历所有指示对象的列表,并调用close()
     * 
     */

    /**
     * Attach a Closeable to this FD for tracking.
     * parent reference is added to otherParents when
     * needed to make closeAll simpler.
     * <p>
     * 将一个Closeable附加到此FD用于跟踪。父引用添加到otherParents时需要使closeAll更简单。
     * 
     */
    synchronized void attach(Closeable c) {
        if (parent == null) {
            // first caller gets to do this
            parent = c;
        } else if (otherParents == null) {
            otherParents = new ArrayList<>();
            otherParents.add(parent);
            otherParents.add(c);
        } else {
            otherParents.add(c);
        }
    }

    /**
     * Cycle through all Closeables sharing this FD and call
     * close() on each one.
     *
     * The caller closeable gets to call close0().
     * <p>
     *  循环通过所有关闭共享此FD和调用close()在每一个。
     * 
     *  调用者可以调用close0()。
     * 
     */
    @SuppressWarnings("try")
    synchronized void closeAll(Closeable releaser) throws IOException {
        if (!closed) {
            closed = true;
            IOException ioe = null;
            try (Closeable c = releaser) {
                if (otherParents != null) {
                    for (Closeable referent : otherParents) {
                        try {
                            referent.close();
                        } catch(IOException x) {
                            if (ioe == null) {
                                ioe = x;
                            } else {
                                ioe.addSuppressed(x);
                            }
                        }
                    }
                }
            } catch(IOException ex) {
                /*
                 * If releaser close() throws IOException
                 * add other exceptions as suppressed.
                 * <p>
                 */
                if (ioe != null)
                    ex.addSuppressed(ioe);
                ioe = ex;
            } finally {
                if (ioe != null)
                    throw ioe;
            }
        }
    }
}
