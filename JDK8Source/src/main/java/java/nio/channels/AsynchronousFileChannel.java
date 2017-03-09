/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.spi.*;
import java.nio.ByteBuffer;
import java.io.IOException;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutorService;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

/**
 * An asynchronous channel for reading, writing, and manipulating a file.
 *
 * <p> An asynchronous file channel is created when a file is opened by invoking
 * one of the {@link #open open} methods defined by this class. The file contains
 * a variable-length sequence of bytes that can be read and written and whose
 * current size can be {@link #size() queried}. The size of the file increases
 * when bytes are written beyond its  current size; the size of the file decreases
 * when it is {@link #truncate truncated}.
 *
 * <p> An asynchronous file channel does not have a <i>current position</i>
 * within the file. Instead, the file position is specified to each read and
 * write method that initiates asynchronous operations. A {@link CompletionHandler}
 * is specified as a parameter and is invoked to consume the result of the I/O
 * operation. This class also defines read and write methods that initiate
 * asynchronous operations, returning a {@link Future} to represent the pending
 * result of the operation. The {@code Future} may be used to check if the
 * operation has completed, wait for its completion, and retrieve the result.
 *
 * <p> In addition to read and write operations, this class defines the
 * following operations: </p>
 *
 * <ul>
 *
 *   <li><p> Updates made to a file may be {@link #force <i>forced
 *   out</i>} to the underlying storage device, ensuring that data are not
 *   lost in the event of a system crash.  </p></li>
 *
 *   <li><p> A region of a file may be {@link #lock <i>locked</i>} against
 *   access by other programs.  </p></li>
 *
 * </ul>
 *
 * <p> An {@code AsynchronousFileChannel} is associated with a thread pool to
 * which tasks are submitted to handle I/O events and dispatch to completion
 * handlers that consume the results of I/O operations on the channel. The
 * completion handler for an I/O operation initiated on a channel is guaranteed
 * to be invoked by one of the threads in the thread pool (This ensures that the
 * completion handler is run by a thread with the expected <em>identity</em>).
 * Where an I/O operation completes immediately, and the initiating thread is
 * itself a thread in the thread pool, then the completion handler may be invoked
 * directly by the initiating thread. When an {@code AsynchronousFileChannel} is
 * created without specifying a thread pool then the channel is associated with
 * a system-dependent default thread pool that may be shared with other
 * channels. The default thread pool is configured by the system properties
 * defined by the {@link AsynchronousChannelGroup} class.
 *
 * <p> Channels of this type are safe for use by multiple concurrent threads. The
 * {@link Channel#close close} method may be invoked at any time, as specified
 * by the {@link Channel} interface. This causes all outstanding asynchronous
 * operations on the channel to complete with the exception {@link
 * AsynchronousCloseException}. Multiple read and write operations may be
 * outstanding at the same time. When multiple read and write operations are
 * outstanding then the ordering of the I/O operations, and the order that the
 * completion handlers are invoked, is not specified; they are not, in particular,
 * guaranteed to execute in the order that the operations were initiated. The
 * {@link java.nio.ByteBuffer ByteBuffers} used when reading or writing are not
 * safe for use by multiple concurrent I/O operations. Furthermore, after an I/O
 * operation is initiated then care should be taken to ensure that the buffer is
 * not accessed until after the operation has completed.
 *
 * <p> As with {@link FileChannel}, the view of a file provided by an instance of
 * this class is guaranteed to be consistent with other views of the same file
 * provided by other instances in the same program.  The view provided by an
 * instance of this class may or may not, however, be consistent with the views
 * seen by other concurrently-running programs due to caching performed by the
 * underlying operating system and delays induced by network-filesystem protocols.
 * This is true regardless of the language in which these other programs are
 * written, and whether they are running on the same machine or on some other
 * machine.  The exact nature of any such inconsistencies are system-dependent
 * and are therefore unspecified.
 *
 * <p>
 *  用于读取,写入和操作文件的异步通道。
 * 
 *  <p>当通过调用此类定义的{@link #open open}方法之一打开文件时,将创建异步文件通道。该文件包含可读长度可变长度的字节序列,其当前大小可以是{@link #size()查询}。
 * 当字节被写入超出当前大小时,文件的大小增加;当{@link #truncate truncated}时,文件的大小减小。
 * 
 *  <p>异步文件通道在文件中没有<i>当前位置</i>。而是,为启动异步操作的每个读取和写入方法指定文件位置。
 *  {@link CompletionHandler}被指定为参数,并被调用以使用I / O操作的结果。该类还定义了启动异步操作的读写方法,返回{@link Future}以表示操作的未决结果。
 *  {@code Future}可用于检查操作是否已完成,等待其完成,并检索结果。
 * 
 *  <p>除了读取和写入操作之外,此类还定义了以下操作：</p>
 * 
 * <ul>
 * 
 * <li> <p>对文件所做的更新可能会{@link #force <i>强制退出</i>}到底层存储设备,以确保在系统崩溃时数据不会丢失。 </p> </li>
 * 
 *  <li> <p>文件的某个区域可能被{@link #lock <i>锁定</i>}阻止其他程序访问。 </p> </li>
 * 
 * </ul>
 * 
 *  <p> {@code AsynchronousFileChannel}与提交任务以处理I / O事件并分派到消耗I / O操作在通道上的结果的完成处理程序的线程池相关联。
 * 在通道上发起的I / O操作的完成处理程序保证被线程池中的一个线程调用(这确保完成处理程序由具有期望的<em>标识</em>的线程运行) )。
 * 当I / O操作立即完成,并且发起线程本身是线程池中的线程时,则完成处理程序可以由发起线程直接调用。
 * 当创建{@code AsynchronousFileChannel}而不指定线程池时,该通道与可以与其他通道共享的系统相关的默认线程池相关联。
 * 默认线程池由{@link AsynchronousChannelGroup}类定义的系统属性配置。
 * 
 * <p>此类型的通道可安全地用于多个并发线程。 {@link Channel#close close}方法可以在{@link Channel}界面指定的任何时间调用。
 * 这会导致通道上的所有未完成的异步操作都使用异常{@link AsynchronousCloseException}完成。多个读取和写入操作可以同时是未完成的。
 * 当多个读取和写入操作未完成时,未指定I / O操作的排序和调用完成处理程序的顺序;特别地,它们不被保证以操作被启动的顺序执行。
 * 在读取或写入时使用的{@link java.nio.ByteBuffer ByteBuffers}不能安全地用于多个并发I / O操作。
 * 此外,在启动I / O操作之后,应当注意确保在操作完成之后不访问缓冲器。
 * 
 * <p>与{@link FileChannel}一样,此类的实例提供的文件的视图也保证与同一程序中其他实例提供的同一文件的其他视图一致。
 * 然而,由于由底层操作系统执行的缓存和由网络文件系统协议引起的延迟,由该类的实例提供的视图可以或者可以不与由其它并发运行的程序看到的视图一致。
 * 无论这些其他程序的编写语言,以及它们是在同一台机器上运行还是在其他机器上运行,都是如此。任何这种不一致的确切性质是系统依赖性的,因此未指定。
 * 
 * 
 * @since 1.7
 */

public abstract class AsynchronousFileChannel
    implements AsynchronousChannel
{
    /**
     * Initializes a new instance of this class.
     * <p>
     *  初始化此类的新实例。
     * 
     */
    protected AsynchronousFileChannel() {
    }

    /**
     * Opens or creates a file for reading and/or writing, returning an
     * asynchronous file channel to access the file.
     *
     * <p> The {@code options} parameter determines how the file is opened.
     * The {@link StandardOpenOption#READ READ} and {@link StandardOpenOption#WRITE
     * WRITE} options determines if the file should be opened for reading and/or
     * writing. If neither option is contained in the array then an existing file
     * is opened for  reading.
     *
     * <p> In addition to {@code READ} and {@code WRITE}, the following options
     * may be present:
     *
     * <table border=1 cellpadding=5 summary="">
     * <tr> <th>Option</th> <th>Description</th> </tr>
     * <tr>
     *   <td> {@link StandardOpenOption#TRUNCATE_EXISTING TRUNCATE_EXISTING} </td>
     *   <td> When opening an existing file, the file is first truncated to a
     *   size of 0 bytes. This option is ignored when the file is opened only
     *   for reading.</td>
     * </tr>
     * <tr>
     *   <td> {@link StandardOpenOption#CREATE_NEW CREATE_NEW} </td>
     *   <td> If this option is present then a new file is created, failing if
     *   the file already exists. When creating a file the check for the
     *   existence of the file and the creation of the file if it does not exist
     *   is atomic with respect to other file system operations. This option is
     *   ignored when the file is opened only for reading. </td>
     * </tr>
     * <tr>
     *   <td > {@link StandardOpenOption#CREATE CREATE} </td>
     *   <td> If this option is present then an existing file is opened if it
     *   exists, otherwise a new file is created. When creating a file the check
     *   for the existence of the file and the creation of the file if it does
     *   not exist is atomic with respect to other file system operations. This
     *   option is ignored if the {@code CREATE_NEW} option is also present or
     *   the file is opened only for reading. </td>
     * </tr>
     * <tr>
     *   <td > {@link StandardOpenOption#DELETE_ON_CLOSE DELETE_ON_CLOSE} </td>
     *   <td> When this option is present then the implementation makes a
     *   <em>best effort</em> attempt to delete the file when closed by the
     *   the {@link #close close} method. If the {@code close} method is not
     *   invoked then a <em>best effort</em> attempt is made to delete the file
     *   when the Java virtual machine terminates. </td>
     * </tr>
     * <tr>
     *   <td>{@link StandardOpenOption#SPARSE SPARSE} </td>
     *   <td> When creating a new file this option is a <em>hint</em> that the
     *   new file will be sparse. This option is ignored when not creating
     *   a new file. </td>
     * </tr>
     * <tr>
     *   <td> {@link StandardOpenOption#SYNC SYNC} </td>
     *   <td> Requires that every update to the file's content or metadata be
     *   written synchronously to the underlying storage device. (see <a
     *   href="../file/package-summary.html#integrity"> Synchronized I/O file
     *   integrity</a>). </td>
     * </tr>
     * <tr>
     *   <td> {@link StandardOpenOption#DSYNC DSYNC} </td>
     *   <td> Requires that every update to the file's content be written
     *   synchronously to the underlying storage device. (see <a
     *   href="../file/package-summary.html#integrity"> Synchronized I/O file
     *   integrity</a>). </td>
     * </tr>
     * </table>
     *
     * <p> An implementation may also support additional options.
     *
     * <p> The {@code executor} parameter is the {@link ExecutorService} to
     * which tasks are submitted to handle I/O events and dispatch completion
     * results for operations initiated on resulting channel.
     * The nature of these tasks is highly implementation specific and so care
     * should be taken when configuring the {@code Executor}. Minimally it
     * should support an unbounded work queue and should not run tasks on the
     * caller thread of the {@link ExecutorService#execute execute} method.
     * Shutting down the executor service while the channel is open results in
     * unspecified behavior.
     *
     * <p> The {@code attrs} parameter is an optional array of file {@link
     * FileAttribute file-attributes} to set atomically when creating the file.
     *
     * <p> The new channel is created by invoking the {@link
     * FileSystemProvider#newFileChannel newFileChannel} method on the
     * provider that created the {@code Path}.
     *
     * <p>
     *  打开或创建用于读取和/或写入的文件,返回异步文件通道以访问文件。
     * 
     *  <p> {@code options}参数确定如何打开文件。
     *  {@link StandardOpenOption#READ READ}和{@link StandardOpenOption#WRITE WRITE}选项决定文件是否应该打开以进行读取和/或写入。
     * 如果数组中不包含任何选项,则会打开一个现有文件进行读取。
     * 
     *  <p>除了{@code READ}和{@code WRITE}之外,还可能有以下选项：
     * 
     * <table border=1 cellpadding=5 summary="">
     *  <tr> <th>选项</th> <th>描述</th> </tr>
     * <tr>
     * <td> {@link StandardOpenOption#TRUNCATE_EXISTING TRUNCATE_EXISTING} </td> <td>打开现有文件时,首先将文件截断为0字节大小。
     * 当打开文件仅用于阅读时,将忽略此选项。</td>。
     * </tr>
     * <tr>
     *  <td> {@link StandardOpenOption#CREATE_NEW CREATE_NEW} </td> <td>如果此选项存在,则会创建一个新文件,如果该文件已存在,则会失败。
     * 当创建文件时,检查文件的存在以及文件的创建(如果不存在)相对于其他文件系统操作是原子的。仅当打开文件以进行读取时,将忽略此选项。 </td>。
     * </tr>
     * <tr>
     *  <td> {@link StandardOpenOption#CREATE CREATE} </td> <td>如果存在此选项,则会打开现有文件(如果存在),否则将创建一个新文件。
     * 当创建文件时,检查文件的存在以及文件的创建(如果不存在)相对于其他文件系统操作是原子的。如果还存在{@code CREATE_NEW}选项或打开文件仅用于读取,将忽略此选项。 </td>。
     * </tr>
     * <tr>
     *  <td> {@link StandardOpenOption#DELETE_ON_CLOSE DELETE_ON_CLOSE} </td> <td>当此选项存在时,该实施会尝试在{@link关闭时</em> #close close}
     * 方法。
     * 如果未调用{@code close}方法,则会尝试在Java虚拟机终止时尝试删除该文件。<em> </em> </td>。
     * </tr>
     * <tr>
     * <td> {@ link StandardOpenOption#SPARSE SPARSE} </td> <td>在创建新文件时,此选项是一个<em>提示</em>,新文件将是稀疏的。
     * 不创建新文件时忽略此选项。 </td>。
     * </tr>
     * <tr>
     *  <td> {@link StandardOpenOption#SYNC SYNC} </td> <td>需要将对文件内容或元数据的每次更新与底层存储设备同步写入。
     *  (请参见<a href="../file/package-summary.html#integrity">同步I / O文件完整性</a>)。 </td>。
     * </tr>
     * <tr>
     *  <td> {@link StandardOpenOption#DSYNC DSYNC} </td> <td>需要对文件内容的每次更新都与底层存储设备同步写入。
     *  (请参见<a href="../file/package-summary.html#integrity">同步I / O文件完整性</a>)。 </td>。
     * </tr>
     * </table>
     * 
     *  <p>实施也可以支持其他选项。
     * 
     *  <p> {@code executor}参数是{@link ExecutorService},提交任务以处理I / O事件,并针对在结果通道上启动的操作发送完成结果。
     * 这些任务的性质是高度实现特定的,因此在配置{@code Executor}时应该小心。
     * 最低限度应该支持无界工作队列,不应该在{@link ExecutorService#execute execute}方法的调用者线程上运行任务。通道打开时关闭执行器服务会导致未指定的行为。
     * 
     * <p> {@code attrs}参数是文件{@link FileAttribute文件属性}的可选数组,用于在创建文件时自动设置。
     * 
     *  <p>新频道是通过在创建{@code Path}的提供商上调用{@link FileSystemProvider#newFileChannel newFileChannel}方法创建的。
     * 
     * 
     * @param   file
     *          The path of the file to open or create
     * @param   options
     *          Options specifying how the file is opened
     * @param   executor
     *          The thread pool or {@code null} to associate the channel with
     *          the default thread pool
     * @param   attrs
     *          An optional list of file attributes to set atomically when
     *          creating the file
     *
     * @return  A new asynchronous file channel
     *
     * @throws  IllegalArgumentException
     *          If the set contains an invalid combination of options
     * @throws  UnsupportedOperationException
     *          If the {@code file} is associated with a provider that does not
     *          support creating asynchronous file channels, or an unsupported
     *          open option is specified, or the array contains an attribute that
     *          cannot be set atomically when creating the file
     * @throws  IOException
     *          If an I/O error occurs
     * @throws  SecurityException
     *          If a security manager is installed and it denies an
     *          unspecified permission required by the implementation.
     *          In the case of the default provider, the {@link
     *          SecurityManager#checkRead(String)} method is invoked to check
     *          read access if the file is opened for reading. The {@link
     *          SecurityManager#checkWrite(String)} method is invoked to check
     *          write access if the file is opened for writing
     */
    public static AsynchronousFileChannel open(Path file,
                                               Set<? extends OpenOption> options,
                                               ExecutorService executor,
                                               FileAttribute<?>... attrs)
        throws IOException
    {
        FileSystemProvider provider = file.getFileSystem().provider();
        return provider.newAsynchronousFileChannel(file, options, executor, attrs);
    }

    @SuppressWarnings({"unchecked", "rawtypes"}) // generic array construction
    private static final FileAttribute<?>[] NO_ATTRIBUTES = new FileAttribute[0];

    /**
     * Opens or creates a file for reading and/or writing, returning an
     * asynchronous file channel to access the file.
     *
     * <p> An invocation of this method behaves in exactly the same way as the
     * invocation
     * <pre>
     *     ch.{@link #open(Path,Set,ExecutorService,FileAttribute[])
     *       open}(file, opts, null, new FileAttribute&lt;?&gt;[0]);
     * </pre>
     * where {@code opts} is a {@code Set} containing the options specified to
     * this method.
     *
     * <p> The resulting channel is associated with default thread pool to which
     * tasks are submitted to handle I/O events and dispatch to completion
     * handlers that consume the result of asynchronous operations performed on
     * the resulting channel.
     *
     * <p>
     *  打开或创建用于读取和/或写入的文件,返回异步文件通道以访问文件。
     * 
     *  <p>此方法的调用行为与调用完全相同
     * <pre>
     *  ch.{@link #open(Path,Set,ExecutorService,FileAttribute [])open}(file,opts,null,new FileAttribute&lt;
     * ?&gt; [0]);。
     * </pre>
     *  其中{@code opts}是一个{@code Set},其中包含为此方法指定的选项。
     * 
     *  <p>生成的通道与默认线程池相关联,任务被提交到该线程池以处理I / O事件,并分发到完成处理程序,完成处理程序消耗对生成的通道执行的异步操作的结果。
     * 
     * 
     * @param   file
     *          The path of the file to open or create
     * @param   options
     *          Options specifying how the file is opened
     *
     * @return  A new asynchronous file channel
     *
     * @throws  IllegalArgumentException
     *          If the set contains an invalid combination of options
     * @throws  UnsupportedOperationException
     *          If the {@code file} is associated with a provider that does not
     *          support creating file channels, or an unsupported open option is
     *          specified
     * @throws  IOException
     *          If an I/O error occurs
     * @throws  SecurityException
     *          If a security manager is installed and it denies an
     *          unspecified permission required by the implementation.
     *          In the case of the default provider, the {@link
     *          SecurityManager#checkRead(String)} method is invoked to check
     *          read access if the file is opened for reading. The {@link
     *          SecurityManager#checkWrite(String)} method is invoked to check
     *          write access if the file is opened for writing
     */
    public static AsynchronousFileChannel open(Path file, OpenOption... options)
        throws IOException
    {
        Set<OpenOption> set = new HashSet<OpenOption>(options.length);
        Collections.addAll(set, options);
        return open(file, set, null, NO_ATTRIBUTES);
    }

    /**
     * Returns the current size of this channel's file.
     *
     * <p>
     *  返回此频道文件的当前大小。
     * 
     * 
     * @return  The current size of this channel's file, measured in bytes
     *
     * @throws  ClosedChannelException
     *          If this channel is closed
     * @throws  IOException
     *          If some other I/O error occurs
     */
    public abstract long size() throws IOException;

    /**
     * Truncates this channel's file to the given size.
     *
     * <p> If the given size is less than the file's current size then the file
     * is truncated, discarding any bytes beyond the new end of the file.  If
     * the given size is greater than or equal to the file's current size then
     * the file is not modified. </p>
     *
     * <p>
     *  将此频道的文件截断为指定大小。
     * 
     *  <p>如果给定的大小小于文件的当前大小,则文件将被截断,丢弃超出文件新结尾的任何字节。如果给定的大小大于或等于文件的当前大小,则不会修改文件。 </p>
     * 
     * 
     * @param  size
     *         The new size, a non-negative byte count
     *
     * @return  This file channel
     *
     * @throws  NonWritableChannelException
     *          If this channel was not opened for writing
     *
     * @throws  ClosedChannelException
     *          If this channel is closed
     *
     * @throws  IllegalArgumentException
     *          If the new size is negative
     *
     * @throws  IOException
     *          If some other I/O error occurs
     */
    public abstract AsynchronousFileChannel truncate(long size) throws IOException;

    /**
     * Forces any updates to this channel's file to be written to the storage
     * device that contains it.
     *
     * <p> If this channel's file resides on a local storage device then when
     * this method returns it is guaranteed that all changes made to the file
     * since this channel was created, or since this method was last invoked,
     * will have been written to that device.  This is useful for ensuring that
     * critical information is not lost in the event of a system crash.
     *
     * <p> If the file does not reside on a local device then no such guarantee
     * is made.
     *
     * <p> The {@code metaData} parameter can be used to limit the number of
     * I/O operations that this method is required to perform.  Passing
     * {@code false} for this parameter indicates that only updates to the
     * file's content need be written to storage; passing {@code true}
     * indicates that updates to both the file's content and metadata must be
     * written, which generally requires at least one more I/O operation.
     * Whether this parameter actually has any effect is dependent upon the
     * underlying operating system and is therefore unspecified.
     *
     * <p> Invoking this method may cause an I/O operation to occur even if the
     * channel was only opened for reading.  Some operating systems, for
     * example, maintain a last-access time as part of a file's metadata, and
     * this time is updated whenever the file is read.  Whether or not this is
     * actually done is system-dependent and is therefore unspecified.
     *
     * <p> This method is only guaranteed to force changes that were made to
     * this channel's file via the methods defined in this class.
     *
     * <p>
     *  强制将此频道文件的所有更新写入包含该频道的存储设备。
     * 
     * <p>如果此频道的文件位于本地存储设备上,则当此方法返回时,将确保自此频道创建以来对文件所做的所有更改或自上次调用此方法以来已写入该设备。这有助于确保关键信息在系统崩溃的情况下不会丢失。
     * 
     *  <p>如果文件不驻留在本地设备上,则不进行此类保证。
     * 
     *  <p> {@code metaData}参数可用于限制此方法需要执行的I / O操作数。
     * 对此参数传递{@code false}表示只有对文件内容的更新才需要写入存储器;传递{@code true}表示必须写入对文件内容和元数据的更新,这通常需要至少一个I / O操作。
     * 此参数是否实际上具有任何效果取决于底层操作系统,因此未指定。
     * 
     *  <p>调用此方法可能会导致发生I / O操作,即使只打开通道进行读取。例如,一些操作系统将最后访问时间保持为文件的元数据的一部分,并且每当读取文件时都更新该时间。
     * 这是否实际完成是系统依赖的,因此未指定。
     * 
     *  <p>此方法仅保证通过此类中定义的方法强制对此通道文件所做的更改。
     * 
     * 
     * @param   metaData
     *          If {@code true} then this method is required to force changes
     *          to both the file's content and metadata to be written to
     *          storage; otherwise, it need only force content changes to be
     *          written
     *
     * @throws  ClosedChannelException
     *          If this channel is closed
     *
     * @throws  IOException
     *          If some other I/O error occurs
     */
    public abstract void force(boolean metaData) throws IOException;

    /**
     * Acquires a lock on the given region of this channel's file.
     *
     * <p> This method initiates an operation to acquire a lock on the given
     * region of this channel's file. The {@code handler} parameter is a
     * completion handler that is invoked when the lock is acquired (or the
     * operation fails). The result passed to the completion handler is the
     * resulting {@code FileLock}.
     *
     * <p> The region specified by the {@code position} and {@code size}
     * parameters need not be contained within, or even overlap, the actual
     * underlying file.  Lock regions are fixed in size; if a locked region
     * initially contains the end of the file and the file grows beyond the
     * region then the new portion of the file will not be covered by the lock.
     * If a file is expected to grow in size and a lock on the entire file is
     * required then a region starting at zero, and no smaller than the
     * expected maximum size of the file, should be locked.  The two-argument
     * {@link #lock(Object,CompletionHandler)} method simply locks a region
     * of size {@link Long#MAX_VALUE}. If a lock that overlaps the requested
     * region is already held by this Java virtual machine, or this method has
     * been invoked to lock an overlapping region and that operation has not
     * completed, then this method throws {@link OverlappingFileLockException}.
     *
     * <p> Some operating systems do not support a mechanism to acquire a file
     * lock in an asynchronous manner. Consequently an implementation may
     * acquire the file lock in a background thread or from a task executed by
     * a thread in the associated thread pool. If there are many lock operations
     * outstanding then it may consume threads in the Java virtual machine for
     * indefinite periods.
     *
     * <p> Some operating systems do not support shared locks, in which case a
     * request for a shared lock is automatically converted into a request for
     * an exclusive lock.  Whether the newly-acquired lock is shared or
     * exclusive may be tested by invoking the resulting lock object's {@link
     * FileLock#isShared() isShared} method.
     *
     * <p> File locks are held on behalf of the entire Java virtual machine.
     * They are not suitable for controlling access to a file by multiple
     * threads within the same virtual machine.
     *
     * <p>
     * 在此频道文件的给定区域获取锁定。
     * 
     *  <p>此方法启动操作以获取此频道文件的给定区域上的锁定。 {@code handler}参数是在获取锁时(或操作失败)调用的完成处理程序。
     * 传递给完成处理程序的结果是生成的{@code FileLock}。
     * 
     *  <p>由{@code position}和{@code size}参数指定的区域不需要包含在实际底层文件中,甚至不会重叠。
     * 锁定区域大小固定;如果锁定区域最初包含文件的结尾并且文件增长超过该区域,则该文件的新部分将不被锁所覆盖。
     * 如果文件预计在大小上增长并且需要对整个文件进行锁定,则应该锁定从零开始并且不小于文件的预期最大大小的区域。
     * 双参数{@link #lock(Object,CompletionHandler)}方法只锁定大小为{@link Long#MAX_VALUE}的区域。
     * 如果与所请求区域重叠的锁已由此Java虚拟机保留,或者该方法已被调用以锁定重叠区域,并且该操作尚未完成,则此方法将抛出{@link OverlappingFileLockException}。
     * 
     * <p>某些操作系统不支持以异步方式获取文件锁的机制。因此,实现可以在后台线程中或从由相关联线程池中的线程执行的任务获取文件锁定。如果有很多锁定操作未完成,那么它可能会在Java虚拟机中使用线程无限期。
     * 
     *  <p>某些操作系统不支持共享锁,在这种情况下,共享锁的请求会自动转换为独占锁的请求。
     * 可以通过调用所得锁对象的{@link FileLock#isShared()isShared}方法来测试新获取的锁是共享还是排他。
     * 
     *  <p>文件锁定代表整个Java虚拟机。它们不适合控制同一虚拟机中的多个线程对文件的访问。
     * 
     * 
     * @param   <A>
     *          The type of the attachment
     * @param   position
     *          The position at which the locked region is to start; must be
     *          non-negative
     * @param   size
     *          The size of the locked region; must be non-negative, and the sum
     *          {@code position}&nbsp;+&nbsp;{@code size} must be non-negative
     * @param   shared
     *          {@code true} to request a shared lock, in which case this
     *          channel must be open for reading (and possibly writing);
     *          {@code false} to request an exclusive lock, in which case this
     *          channel must be open for writing (and possibly reading)
     * @param   attachment
     *          The object to attach to the I/O operation; can be {@code null}
     * @param   handler
     *          The handler for consuming the result
     *
     * @throws  OverlappingFileLockException
     *          If a lock that overlaps the requested region is already held by
     *          this Java virtual machine, or there is already a pending attempt
     *          to lock an overlapping region
     * @throws  IllegalArgumentException
     *          If the preconditions on the parameters do not hold
     * @throws  NonReadableChannelException
     *          If {@code shared} is true but this channel was not opened for reading
     * @throws  NonWritableChannelException
     *          If {@code shared} is false but this channel was not opened for writing
     */
    public abstract <A> void lock(long position,
                                  long size,
                                  boolean shared,
                                  A attachment,
                                  CompletionHandler<FileLock,? super A> handler);

    /**
     * Acquires an exclusive lock on this channel's file.
     *
     * <p> This method initiates an operation to acquire a lock on the given
     * region of this channel's file. The {@code handler} parameter is a
     * completion handler that is invoked when the lock is acquired (or the
     * operation fails). The result passed to the completion handler is the
     * resulting {@code FileLock}.
     *
     * <p> An invocation of this method of the form {@code ch.lock(att,handler)}
     * behaves in exactly the same way as the invocation
     * <pre>
     *     ch.{@link #lock(long,long,boolean,Object,CompletionHandler) lock}(0L, Long.MAX_VALUE, false, att, handler)
     * </pre>
     *
     * <p>
     *  获取此频道文件的独占锁。
     * 
     *  <p>此方法启动操作以获取此频道文件的给定区域上的锁定。 {@code handler}参数是在获取锁时(或操作失败)调用的完成处理程序。
     * 传递给完成处理程序的结果是生成的{@code FileLock}。
     * 
     *  <p>表单{@code ch.lock(att,handler)}的这个方法的调用行为与调用的方式完全相同
     * <pre>
     *  ch.{@link #lock(long,long,boolean,Object,CompletionHandler)lock}(0L,Long.MAX_VALUE,false,att,handler
     * )。
     * </pre>
     * 
     * 
     * @param   <A>
     *          The type of the attachment
     * @param   attachment
     *          The object to attach to the I/O operation; can be {@code null}
     * @param   handler
     *          The handler for consuming the result
     *
     * @throws  OverlappingFileLockException
     *          If a lock is already held by this Java virtual machine, or there
     *          is already a pending attempt to lock a region
     * @throws  NonWritableChannelException
     *          If this channel was not opened for writing
     */
    public final <A> void lock(A attachment,
                               CompletionHandler<FileLock,? super A> handler)
    {
        lock(0L, Long.MAX_VALUE, false, attachment, handler);
    }

    /**
     * Acquires a lock on the given region of this channel's file.
     *
     * <p> This method initiates an operation to acquire a lock on the given
     * region of this channel's file.  The method behaves in exactly the same
     * manner as the {@link #lock(long, long, boolean, Object, CompletionHandler)}
     * method except that instead of specifying a completion handler, this
     * method returns a {@code Future} representing the pending result. The
     * {@code Future}'s {@link Future#get() get} method returns the {@link
     * FileLock} on successful completion.
     *
     * <p>
     * 在此频道文件的给定区域获取锁定。
     * 
     *  <p>此方法启动操作以获取此频道文件的给定区域上的锁定。
     * 该方法的行为方式与{@link #lock(long,long,boolean,Object,CompletionHandler)}方法完全相同,不同的是此方法不是指定完成处理程序,而是返回表示未决结果
     * 的{@code Future} 。
     *  <p>此方法启动操作以获取此频道文件的给定区域上的锁定。 {@code Future}的{@link Future#get()get}方法会在成功完成时返回{@link FileLock}。
     * 
     * 
     * @param   position
     *          The position at which the locked region is to start; must be
     *          non-negative
     * @param   size
     *          The size of the locked region; must be non-negative, and the sum
     *          {@code position}&nbsp;+&nbsp;{@code size} must be non-negative
     * @param   shared
     *          {@code true} to request a shared lock, in which case this
     *          channel must be open for reading (and possibly writing);
     *          {@code false} to request an exclusive lock, in which case this
     *          channel must be open for writing (and possibly reading)
     *
     * @return  a {@code Future} object representing the pending result
     *
     * @throws  OverlappingFileLockException
     *          If a lock is already held by this Java virtual machine, or there
     *          is already a pending attempt to lock a region
     * @throws  IllegalArgumentException
     *          If the preconditions on the parameters do not hold
     * @throws  NonReadableChannelException
     *          If {@code shared} is true but this channel was not opened for reading
     * @throws  NonWritableChannelException
     *          If {@code shared} is false but this channel was not opened for writing
     */
    public abstract Future<FileLock> lock(long position, long size, boolean shared);

    /**
     * Acquires an exclusive lock on this channel's file.
     *
     * <p> This method initiates an operation to acquire an exclusive lock on this
     * channel's file. The method returns a {@code Future} representing the
     * pending result of the operation. The {@code Future}'s {@link Future#get()
     * get} method returns the {@link FileLock} on successful completion.
     *
     * <p> An invocation of this method behaves in exactly the same way as the
     * invocation
     * <pre>
     *     ch.{@link #lock(long,long,boolean) lock}(0L, Long.MAX_VALUE, false)
     * </pre>
     *
     * <p>
     *  获取此频道文件的独占锁。
     * 
     *  <p>此方法启动操作以获取此频道文件上的排他锁。该方法返回表示操作的未决结果的{@code Future}。
     *  {@code Future}的{@link Future#get()get}方法会在成功完成时返回{@link FileLock}。
     * 
     *  <p>此方法的调用行为与调用完全相同
     * <pre>
     *  ch.{@link #lock(long,long,boolean)lock}(0L,Long.MAX_VALUE,false)
     * </pre>
     * 
     * 
     * @return  a {@code Future} object representing the pending result
     *
     * @throws  OverlappingFileLockException
     *          If a lock is already held by this Java virtual machine, or there
     *          is already a pending attempt to lock a region
     * @throws  NonWritableChannelException
     *          If this channel was not opened for writing
     */
    public final Future<FileLock> lock() {
        return lock(0L, Long.MAX_VALUE, false);
    }

    /**
     * Attempts to acquire a lock on the given region of this channel's file.
     *
     * <p> This method does not block. An invocation always returns immediately,
     * either having acquired a lock on the requested region or having failed to
     * do so.  If it fails to acquire a lock because an overlapping lock is held
     * by another program then it returns {@code null}.  If it fails to acquire
     * a lock for any other reason then an appropriate exception is thrown.
     *
     * <p>
     *  尝试在此频道文件的给定区域上获取锁定。
     * 
     *  <p>此方法不阻止。调用总是立即返回,或者已经获得了对所请求区域的锁定,或者没有这样做。如果由于重叠锁由另一个程序保持而无法获取锁,则它返回{@code null}。
     * 如果由于任何其他原因无法获取锁,那么会抛出一个适当的异常。
     * 
     * 
     * @param  position
     *         The position at which the locked region is to start; must be
     *         non-negative
     *
     * @param  size
     *         The size of the locked region; must be non-negative, and the sum
     *         {@code position}&nbsp;+&nbsp;{@code size} must be non-negative
     *
     * @param  shared
     *         {@code true} to request a shared lock,
     *         {@code false} to request an exclusive lock
     *
     * @return  A lock object representing the newly-acquired lock,
     *          or {@code null} if the lock could not be acquired
     *          because another program holds an overlapping lock
     *
     * @throws  IllegalArgumentException
     *          If the preconditions on the parameters do not hold
     * @throws  ClosedChannelException
     *          If this channel is closed
     * @throws  OverlappingFileLockException
     *          If a lock that overlaps the requested region is already held by
     *          this Java virtual machine, or if another thread is already
     *          blocked in this method and is attempting to lock an overlapping
     *          region of the same file
     * @throws  NonReadableChannelException
     *          If {@code shared} is true but this channel was not opened for reading
     * @throws  NonWritableChannelException
     *          If {@code shared} is false but this channel was not opened for writing
     *
     * @throws  IOException
     *          If some other I/O error occurs
     *
     * @see     #lock(Object,CompletionHandler)
     * @see     #lock(long,long,boolean,Object,CompletionHandler)
     * @see     #tryLock()
     */
    public abstract FileLock tryLock(long position, long size, boolean shared)
        throws IOException;

    /**
     * Attempts to acquire an exclusive lock on this channel's file.
     *
     * <p> An invocation of this method of the form {@code ch.tryLock()}
     * behaves in exactly the same way as the invocation
     *
     * <pre>
     *     ch.{@link #tryLock(long,long,boolean) tryLock}(0L, Long.MAX_VALUE, false) </pre>
     *
     * <p>
     * 尝试获取此频道文件的独占锁。
     * 
     *  <p> {@code ch.tryLock()}形式的此方法的调用行为与调用的方式完全相同
     * 
     * <pre>
     *  ch.{@link #tryLock(long,long,boolean)tryLock}(0L,Long.MAX_VALUE,false)</pre>
     * 
     * 
     * @return  A lock object representing the newly-acquired lock,
     *          or {@code null} if the lock could not be acquired
     *          because another program holds an overlapping lock
     *
     * @throws  ClosedChannelException
     *          If this channel is closed
     * @throws  OverlappingFileLockException
     *          If a lock that overlaps the requested region is already held by
     *          this Java virtual machine, or if another thread is already
     *          blocked in this method and is attempting to lock an overlapping
     *          region
     * @throws  NonWritableChannelException
     *          If {@code shared} is false but this channel was not opened for writing
     *
     * @throws  IOException
     *          If some other I/O error occurs
     *
     * @see     #lock(Object,CompletionHandler)
     * @see     #lock(long,long,boolean,Object,CompletionHandler)
     * @see     #tryLock(long,long,boolean)
     */
    public final FileLock tryLock() throws IOException {
        return tryLock(0L, Long.MAX_VALUE, false);
    }

    /**
     * Reads a sequence of bytes from this channel into the given buffer,
     * starting at the given file position.
     *
     * <p> This method initiates the reading of a sequence of bytes from this
     * channel into the given buffer, starting at the given file position. The
     * result of the read is the number of bytes read or {@code -1} if the given
     * position is greater than or equal to the file's size at the time that the
     * read is attempted.
     *
     * <p> This method works in the same manner as the {@link
     * AsynchronousByteChannel#read(ByteBuffer,Object,CompletionHandler)}
     * method, except that bytes are read starting at the given file position.
     * If the given file position is greater than the file's size at the time
     * that the read is attempted then no bytes are read.
     *
     * <p>
     *  从给定的文件位置开始,从该通道读取一个字节序列到给定的缓冲区。
     * 
     *  <p>此方法启动从给定文件位置开始从该通道读取给定缓冲区中的字节序列。读取的结果是读取的字节数或{@code -1},如果给定位置大于或等于尝试读取时的文件大小。
     * 
     *  <p>此方法的工作方式与{@link AsynchronousByteChannel#read(ByteBuffer,Object,CompletionHandler)}方法相同,除了从给定文件位置开
     * 始读取字节。
     * 如果给定文件位置大于尝试读取时的文件大小,则不读取任何字节。
     * 
     * 
     * @param   <A>
     *          The type of the attachment
     * @param   dst
     *          The buffer into which bytes are to be transferred
     * @param   position
     *          The file position at which the transfer is to begin;
     *          must be non-negative
     * @param   attachment
     *          The object to attach to the I/O operation; can be {@code null}
     * @param   handler
     *          The handler for consuming the result
     *
     * @throws  IllegalArgumentException
     *          If the position is negative or the buffer is read-only
     * @throws  NonReadableChannelException
     *          If this channel was not opened for reading
     */
    public abstract <A> void read(ByteBuffer dst,
                                  long position,
                                  A attachment,
                                  CompletionHandler<Integer,? super A> handler);

    /**
     * Reads a sequence of bytes from this channel into the given buffer,
     * starting at the given file position.
     *
     * <p> This method initiates the reading of a sequence of bytes from this
     * channel into the given buffer, starting at the given file position. This
     * method returns a {@code Future} representing the pending result of the
     * operation. The {@code Future}'s {@link Future#get() get} method returns
     * the number of bytes read or {@code -1} if the given position is greater
     * than or equal to the file's size at the time that the read is attempted.
     *
     * <p> This method works in the same manner as the {@link
     * AsynchronousByteChannel#read(ByteBuffer)} method, except that bytes are
     * read starting at the given file position. If the given file position is
     * greater than the file's size at the time that the read is attempted then
     * no bytes are read.
     *
     * <p>
     *  从给定的文件位置开始,从该通道读取一个字节序列到给定的缓冲区。
     * 
     * <p>此方法启动从给定文件位置开始从该通道读取给定缓冲区中的字节序列。此方法返回表示操作的未决结果的{@code Future}。
     * 如果给定位置大于或等于文件大小,则{@code Future}的{@link Future#get()get}方法返回读取的字节数或{@code -1}读取。
     * 
     *  <p>此方法的工作方式与{@link AsynchronousByteChannel#read(ByteBuffer)}方法相同,除了字节在给定文件位置开始读取。
     * 如果给定文件位置大于尝试读取时的文件大小,则不读取任何字节。
     * 
     * 
     * @param   dst
     *          The buffer into which bytes are to be transferred
     * @param   position
     *          The file position at which the transfer is to begin;
     *          must be non-negative
     *
     * @return  A {@code Future} object representing the pending result
     *
     * @throws  IllegalArgumentException
     *          If the position is negative or the buffer is read-only
     * @throws  NonReadableChannelException
     *          If this channel was not opened for reading
     */
    public abstract Future<Integer> read(ByteBuffer dst, long position);

    /**
     * Writes a sequence of bytes to this channel from the given buffer, starting
     * at the given file position.
     *
     * <p> This method works in the same manner as the {@link
     * AsynchronousByteChannel#write(ByteBuffer,Object,CompletionHandler)}
     * method, except that bytes are written starting at the given file position.
     * If the given position is greater than the file's size, at the time that
     * the write is attempted, then the file will be grown to accommodate the new
     * bytes; the values of any bytes between the previous end-of-file and the
     * newly-written bytes are unspecified.
     *
     * <p>
     *  从给定的文件位置开始,从给定缓冲区向该通道写入一个字节序列。
     * 
     *  <p>此方法的工作方式与{@link AsynchronousByteChannel#write(ByteBuffer,Object,CompletionHandler)}方法相同,除了字节是从给定文
     * 件位置开始写入的。
     * 如果给定位置大于文件大小,则在尝试写入时,文件将增长以容纳新的字节;在上一个文件结束和新写入的字节之间的任何字节的值是未指定的。
     * 
     * 
     * @param   <A>
     *          The type of the attachment
     * @param   src
     *          The buffer from which bytes are to be transferred
     * @param   position
     *          The file position at which the transfer is to begin;
     *          must be non-negative
     * @param   attachment
     *          The object to attach to the I/O operation; can be {@code null}
     * @param   handler
     *          The handler for consuming the result
     *
     * @throws  IllegalArgumentException
     *          If the position is negative
     * @throws  NonWritableChannelException
     *          If this channel was not opened for writing
     */
    public abstract <A> void write(ByteBuffer src,
                                   long position,
                                   A attachment,
                                   CompletionHandler<Integer,? super A> handler);

    /**
     * Writes a sequence of bytes to this channel from the given buffer, starting
     * at the given file position.
     *
     * <p> This method initiates the writing of a sequence of bytes to this
     * channel from the given buffer, starting at the given file position. The
     * method returns a {@code Future} representing the pending result of the
     * write operation. The {@code Future}'s {@link Future#get() get} method
     * returns the number of bytes written.
     *
     * <p> This method works in the same manner as the {@link
     * AsynchronousByteChannel#write(ByteBuffer)} method, except that bytes are
     * written starting at the given file position. If the given position is
     * greater than the file's size, at the time that the write is attempted,
     * then the file will be grown to accommodate the new bytes; the values of
     * any bytes between the previous end-of-file and the newly-written bytes
     * are unspecified.
     *
     * <p>
     *  从给定的文件位置开始,从给定缓冲区向该通道写入一个字节序列。
     * 
     * <p>该方法从给定的文件位置开始,从给定的缓冲区开始向该通道写入一个字节序列。该方法返回表示写操作的未决结果的{@code Future}。
     *  {@code Future}的{@link Future#get()get}方法返回写入的字节数。
     * 
     * @param   src
     *          The buffer from which bytes are to be transferred
     * @param   position
     *          The file position at which the transfer is to begin;
     *          must be non-negative
     *
     * @return  A {@code Future} object representing the pending result
     *
     * @throws  IllegalArgumentException
     *          If the position is negative
     * @throws  NonWritableChannelException
     *          If this channel was not opened for writing
     */
    public abstract Future<Integer> write(ByteBuffer src, long position);
}
