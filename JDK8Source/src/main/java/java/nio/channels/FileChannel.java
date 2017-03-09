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

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.spi.AbstractInterruptibleChannel;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.spi.*;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

/**
 * A channel for reading, writing, mapping, and manipulating a file.
 *
 * <p> A file channel is a {@link SeekableByteChannel} that is connected to
 * a file. It has a current <i>position</i> within its file which can
 * be both {@link #position() <i>queried</i>} and {@link #position(long)
 * <i>modified</i>}.  The file itself contains a variable-length sequence
 * of bytes that can be read and written and whose current {@link #size
 * <i>size</i>} can be queried.  The size of the file increases
 * when bytes are written beyond its current size; the size of the file
 * decreases when it is {@link #truncate <i>truncated</i>}.  The
 * file may also have some associated <i>metadata</i> such as access
 * permissions, content type, and last-modification time; this class does not
 * define methods for metadata access.
 *
 * <p> In addition to the familiar read, write, and close operations of byte
 * channels, this class defines the following file-specific operations: </p>
 *
 * <ul>
 *
 *   <li><p> Bytes may be {@link #read(ByteBuffer, long) read} or
 *   {@link #write(ByteBuffer, long) <i>written</i>} at an absolute
 *   position in a file in a way that does not affect the channel's current
 *   position.  </p></li>
 *
 *   <li><p> A region of a file may be {@link #map <i>mapped</i>}
 *   directly into memory; for large files this is often much more efficient
 *   than invoking the usual <tt>read</tt> or <tt>write</tt> methods.
 *   </p></li>
 *
 *   <li><p> Updates made to a file may be {@link #force <i>forced
 *   out</i>} to the underlying storage device, ensuring that data are not
 *   lost in the event of a system crash.  </p></li>
 *
 *   <li><p> Bytes can be transferred from a file {@link #transferTo <i>to
 *   some other channel</i>}, and {@link #transferFrom <i>vice
 *   versa</i>}, in a way that can be optimized by many operating systems
 *   into a very fast transfer directly to or from the filesystem cache.
 *   </p></li>
 *
 *   <li><p> A region of a file may be {@link FileLock <i>locked</i>}
 *   against access by other programs.  </p></li>
 *
 * </ul>
 *
 * <p> File channels are safe for use by multiple concurrent threads.  The
 * {@link Channel#close close} method may be invoked at any time, as specified
 * by the {@link Channel} interface.  Only one operation that involves the
 * channel's position or can change its file's size may be in progress at any
 * given time; attempts to initiate a second such operation while the first is
 * still in progress will block until the first operation completes.  Other
 * operations, in particular those that take an explicit position, may proceed
 * concurrently; whether they in fact do so is dependent upon the underlying
 * implementation and is therefore unspecified.
 *
 * <p> The view of a file provided by an instance of this class is guaranteed
 * to be consistent with other views of the same file provided by other
 * instances in the same program.  The view provided by an instance of this
 * class may or may not, however, be consistent with the views seen by other
 * concurrently-running programs due to caching performed by the underlying
 * operating system and delays induced by network-filesystem protocols.  This
 * is true regardless of the language in which these other programs are
 * written, and whether they are running on the same machine or on some other
 * machine.  The exact nature of any such inconsistencies are system-dependent
 * and are therefore unspecified.
 *
 * <p> A file channel is created by invoking one of the {@link #open open}
 * methods defined by this class. A file channel can also be obtained from an
 * existing {@link java.io.FileInputStream#getChannel FileInputStream}, {@link
 * java.io.FileOutputStream#getChannel FileOutputStream}, or {@link
 * java.io.RandomAccessFile#getChannel RandomAccessFile} object by invoking
 * that object's <tt>getChannel</tt> method, which returns a file channel that
 * is connected to the same underlying file. Where the file channel is obtained
 * from an existing stream or random access file then the state of the file
 * channel is intimately connected to that of the object whose <tt>getChannel</tt>
 * method returned the channel.  Changing the channel's position, whether
 * explicitly or by reading or writing bytes, will change the file position of
 * the originating object, and vice versa. Changing the file's length via the
 * file channel will change the length seen via the originating object, and vice
 * versa.  Changing the file's content by writing bytes will change the content
 * seen by the originating object, and vice versa.
 *
 * <a name="open-mode"></a> <p> At various points this class specifies that an
 * instance that is "open for reading," "open for writing," or "open for
 * reading and writing" is required.  A channel obtained via the {@link
 * java.io.FileInputStream#getChannel getChannel} method of a {@link
 * java.io.FileInputStream} instance will be open for reading.  A channel
 * obtained via the {@link java.io.FileOutputStream#getChannel getChannel}
 * method of a {@link java.io.FileOutputStream} instance will be open for
 * writing.  Finally, a channel obtained via the {@link
 * java.io.RandomAccessFile#getChannel getChannel} method of a {@link
 * java.io.RandomAccessFile} instance will be open for reading if the instance
 * was created with mode <tt>"r"</tt> and will be open for reading and writing
 * if the instance was created with mode <tt>"rw"</tt>.
 *
 * <a name="append-mode"></a><p> A file channel that is open for writing may be in
 * <i>append mode</i>, for example if it was obtained from a file-output stream
 * that was created by invoking the {@link
 * java.io.FileOutputStream#FileOutputStream(java.io.File,boolean)
 * FileOutputStream(File,boolean)} constructor and passing <tt>true</tt> for
 * the second parameter.  In this mode each invocation of a relative write
 * operation first advances the position to the end of the file and then writes
 * the requested data.  Whether the advancement of the position and the writing
 * of the data are done in a single atomic operation is system-dependent and
 * therefore unspecified.
 *
 * <p>
 *  用于读取,写入,映射和操作文件的通道。
 * 
 *  <p>文件通道是连接到文件的{@link SeekableByteChannel}。
 * 它在其文件中具有当前<i>位置</i>,其可以是{@link #position()查询</i>}和{@link #position(long)<i> i>}。
 * 文件本身包含可读长度的可读长度字节序列,可以查询其当前的{@link #size <i> size </i>}。
 * 当字节被写入超出当前大小时,文件的大小增加;当{@link #truncate <i>截断</i>}时,文件的大小减小。
 * 文件还可以具有一些相关联的元数据,诸如访问许可,内容类型和最后修改时间;这个类不定义元数据访问的方法。
 * 
 *  <p>除了熟悉的字节通道的读,写和关闭操作之外,此类还定义了以下特定于文件的操作：</p>
 * 
 * <ul>
 * 
 *  <li> <p>字元可以是档案中绝对位置的{@link #read(ByteBuffer,long)read}或{@link #write(ByteBuffer,long)<i>写入</i>}方式不影
 * 响通道的当前位置。
 *  </p> </li>。
 * 
 * <li> <p>文件的某个区域可能会{@link #map <i>映射</i>}直接进入内存;对于大文件,这通常比调用通常的<tt>读</tt>或<tt>写</tt>方法更有效率。
 *  </p> </li>。
 * 
 *  <li> <p>对文件所做的更新可能会{@link #force <i>强制退出</i>}到底层存储设备,以确保在系统崩溃时数据不会丢失。 </p> </li>
 * 
 *  <li> <p>字节可以从文件{@link #transferTo <i>转移到其他某个渠道</i>},而{@link #transferFrom <i>反之亦然</i>方式可以由许多操作系统优化到非常快速的直接传输到文件系统缓存或从文件系统缓存直接传输。
 *  </p> </li>。
 * 
 *  <li> <p>文件的某个区域可能被{@link FileLock <i>锁定</i>}阻止其他程序访问。 </p> </li>
 * 
 * </ul>
 * 
 *  <p>文件通道可安全地用于多个并发线程。 {@link Channel#close close}方法可以在{@link Channel}界面指定的任何时间调用。
 * 在任何给定时间只有一个涉及频道位置或可以改变其文件大小的操作正在进行;在第一个仍然在进行中尝试启动第二个这样的操作将阻塞直到第一个操作完成。
 * 其他操作,特别是那些采取明确位置的操作,可以同时进行;它们是否实际上这样做取决于基本实现,因此未指定。
 * 
 * <p>此类的实例提供的文件的视图保证与同一程序中其他实例提供的同一文件的其他视图一致。
 * 然而,由于由底层操作系统执行的缓存和由网络文件系统协议引起的延迟,由该类的实例提供的视图可以或者可以不与由其它并发运行的程序看到的视图一致。
 * 无论这些其他程序的编写语言,以及它们是在同一台机器上运行还是在其他机器上运行,都是如此。任何这种不一致的确切性质是系统依赖的,因此未指定。
 * 
 * <p>通过调用此类定义的{@link #open open}方法之一创建文件通道。
 * 还可以从现有的{@link java.io.FileInputStream#getChannel FileInputStream},{@link java.io.FileOutputStream#getChannel FileOutputStream}
 * 或{@link java.io.RandomAccessFile#getChannel RandomAccessFile}对象中获取文件通道通过调用该对象的<tt> getChannel </tt>方法
 * ,该方法返回连接到同一底层文件的文件通道。
 * <p>通过调用此类定义的{@link #open open}方法之一创建文件通道。
 * 在从现有流或随机存取文件获得文件通道的情况下,文件通道的状态与其<tt> getChannel </tt>方法返回通道的对象的状态密切相关。
 * 改变通道的位置,无论是明确地还是通过读取或写入字节,将改变原始对象的文件位置,反之亦然。通过文件通道更改文件的长度将改变通过原始对象看到的长度,反之亦然。
 * 通过写入字节来更改文件的内容将改变由原始对象看到的内容,反之亦然。
 * 
 * <a name="open-mode"> </a> <p>在各个点,此类指定需要"开放阅读","开放写作"或"开放阅读和写作"的实例。
 * 通过{@link java.io.FileInputStream}实例的{@link java.io.FileInputStream#getChannel getChannel}方法获取的渠道将开放供阅
 * 读。
 * <a name="open-mode"> </a> <p>在各个点,此类指定需要"开放阅读","开放写作"或"开放阅读和写作"的实例。
 * 通过{@link java.io.FileOutputStream}实例的{@link java.io.FileOutputStream#getChannel getChannel}方法获得的渠道将开放
 * 供撰写。
 * <a name="open-mode"> </a> <p>在各个点,此类指定需要"开放阅读","开放写作"或"开放阅读和写作"的实例。
 * 最后,通过{@link java.io.RandomAccessFile}实例的{@link java.io.RandomAccessFile#getChannel getChannel}方法获得的通道
 * 将被读取,如果实例是使用模式<tt>"r"创建的, </tt>,如果实例是使用模式<tt>"rw"</tt>创建的,将会打开进行读取和写入。
 * <a name="open-mode"> </a> <p>在各个点,此类指定需要"开放阅读","开放写作"或"开放阅读和写作"的实例。
 * 
 *  <a name="append-mode"> </a> <p>开放写入的文件通道可能处于<i>附加模式</i>,例如,如果它是从文件输出流获取的通过调用{@link java.io.FileOutputStream#FileOutputStream(java.io.File,boolean)FileOutputStream(File,boolean)}
 * 构造函数并传递<tt> true </tt>来创建第二个参数。
 * 在该模式中,相对写入操作的每次调用首先将位置提前到文件的结尾,然后写入所请求的数据。数据的位置的前进和数据的写入是否在单个原子操作中完成是系统相关的,因此是未指定的。
 * 
 * 
 * @see java.io.FileInputStream#getChannel()
 * @see java.io.FileOutputStream#getChannel()
 * @see java.io.RandomAccessFile#getChannel()
 *
 * @author Mark Reinhold
 * @author Mike McCloskey
 * @author JSR-51 Expert Group
 * @since 1.4
 */

public abstract class FileChannel
    extends AbstractInterruptibleChannel
    implements SeekableByteChannel, GatheringByteChannel, ScatteringByteChannel
{
    /**
     * Initializes a new instance of this class.
     * <p>
     * 初始化此类的新实例。
     * 
     */
    protected FileChannel() { }

    /**
     * Opens or creates a file, returning a file channel to access the file.
     *
     * <p> The {@code options} parameter determines how the file is opened.
     * The {@link StandardOpenOption#READ READ} and {@link StandardOpenOption#WRITE
     * WRITE} options determine if the file should be opened for reading and/or
     * writing. If neither option (or the {@link StandardOpenOption#APPEND APPEND}
     * option) is contained in the array then the file is opened for reading.
     * By default reading or writing commences at the beginning of the file.
     *
     * <p> In the addition to {@code READ} and {@code WRITE}, the following
     * options may be present:
     *
     * <table border=1 cellpadding=5 summary="">
     * <tr> <th>Option</th> <th>Description</th> </tr>
     * <tr>
     *   <td> {@link StandardOpenOption#APPEND APPEND} </td>
     *   <td> If this option is present then the file is opened for writing and
     *     each invocation of the channel's {@code write} method first advances
     *     the position to the end of the file and then writes the requested
     *     data. Whether the advancement of the position and the writing of the
     *     data are done in a single atomic operation is system-dependent and
     *     therefore unspecified. This option may not be used in conjunction
     *     with the {@code READ} or {@code TRUNCATE_EXISTING} options. </td>
     * </tr>
     * <tr>
     *   <td> {@link StandardOpenOption#TRUNCATE_EXISTING TRUNCATE_EXISTING} </td>
     *   <td> If this option is present then the existing file is truncated to
     *   a size of 0 bytes. This option is ignored when the file is opened only
     *   for reading. </td>
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
     * <p> The {@code attrs} parameter is an optional array of file {@link
     * FileAttribute file-attributes} to set atomically when creating the file.
     *
     * <p> The new channel is created by invoking the {@link
     * FileSystemProvider#newFileChannel newFileChannel} method on the
     * provider that created the {@code Path}.
     *
     * <p>
     *  打开或创建文件,返回文件通道以访问文件。
     * 
     *  <p> {@code options}参数确定如何打开文件。
     *  {@link StandardOpenOption#READ READ}和{@link StandardOpenOption#WRITE WRITE}选项可确定是否应打开文件进行读取和/或写入。
     * 如果数组中不包含任何选项(或{@link StandardOpenOption#APPEND APPEND}选项),则会打开文件进行读取。默认情况下,读取或写入在文件的开头开始。
     * 
     *  <p>除了{@code READ}和{@code WRITE}之外,还可能有以下选项：
     * 
     * <table border=1 cellpadding=5 summary="">
     *  <tr> <th>选项</th> <th>描述</th> </tr>
     * <tr>
     *  <td> {@link StandardOpenOption#APPEND APPEND} </td> <td>如果此选项存在,则文件将打开以进行写入,并且每次调用通道的{@code write}方法
     * 首先将位置提前到然后写入所请求的数据。
     * 数据的位置的前进和数据的写入是否在单个原子操作中完成是系统相关的,因此是未指定的。此选项不能与{@code READ}或{@code TRUNCATE_EXISTING}选项结合使用。 </td>。
     * </tr>
     * <tr>
     *  <td> {@link StandardOpenOption#TRUNCATE_EXISTING TRUNCATE_EXISTING} </td> <td>如果此选项存在,则现有文件将被截断为0字节大
     * 小。
     * 仅当打开文件以进行读取时,将忽略此选项。 </td>。
     * </tr>
     * <tr>
     * <td> {@link StandardOpenOption#CREATE_NEW CREATE_NEW} </td> <td>如果此选项存在,则会创建一个新文件,如果该文件已存在,则会失败。
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
     *  <td> {@ link StandardOpenOption#SPARSE SPARSE} </td> <td>在创建新文件时,此选项是一个<em>提示</em>,新文件将是稀疏的。
     * 不创建新文件时忽略此选项。 </td>。
     * </tr>
     * <tr>
     * <td> {@link StandardOpenOption#SYNC SYNC} </td> <td>需要将对文件内容或元数据的每次更新与底层存储设备同步写入。
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
     *  <p> {@code attrs}参数是文件{@link FileAttribute文件属性}的可选数组,用于在创建文件时自动设置。
     * 
     *  <p>新频道是通过在创建{@code Path}的提供商上调用{@link FileSystemProvider#newFileChannel newFileChannel}方法创建的。
     * 
     * 
     * @param   path
     *          The path of the file to open or create
     * @param   options
     *          Options specifying how the file is opened
     * @param   attrs
     *          An optional list of file attributes to set atomically when
     *          creating the file
     *
     * @return  A new file channel
     *
     * @throws  IllegalArgumentException
     *          If the set contains an invalid combination of options
     * @throws  UnsupportedOperationException
     *          If the {@code path} is associated with a provider that does not
     *          support creating file channels, or an unsupported open option is
     *          specified, or the array contains an attribute that cannot be set
     *          atomically when creating the file
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
     *
     * @since   1.7
     */
    public static FileChannel open(Path path,
                                   Set<? extends OpenOption> options,
                                   FileAttribute<?>... attrs)
        throws IOException
    {
        FileSystemProvider provider = path.getFileSystem().provider();
        return provider.newFileChannel(path, options, attrs);
    }

    @SuppressWarnings({"unchecked", "rawtypes"}) // generic array construction
    private static final FileAttribute<?>[] NO_ATTRIBUTES = new FileAttribute[0];

    /**
     * Opens or creates a file, returning a file channel to access the file.
     *
     * <p> An invocation of this method behaves in exactly the same way as the
     * invocation
     * <pre>
     *     fc.{@link #open(Path,Set,FileAttribute[]) open}(file, opts, new FileAttribute&lt;?&gt;[0]);
     * </pre>
     * where {@code opts} is a set of the options specified in the {@code
     * options} array.
     *
     * <p>
     *  打开或创建文件,返回文件通道以访问文件。
     * 
     *  <p>此方法的调用行为与调用完全相同
     * <pre>
     *  fc.{@link #open(Path,Set,FileAttribute [])open}(file,opts,new FileAttribute&lt;?&gt; [0]);
     * </pre>
     *  其中{@code opts}是在{@code options}数组中指定的一组选项。
     * 
     * 
     * @param   path
     *          The path of the file to open or create
     * @param   options
     *          Options specifying how the file is opened
     *
     * @return  A new file channel
     *
     * @throws  IllegalArgumentException
     *          If the set contains an invalid combination of options
     * @throws  UnsupportedOperationException
     *          If the {@code path} is associated with a provider that does not
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
     *
     * @since   1.7
     */
    public static FileChannel open(Path path, OpenOption... options)
        throws IOException
    {
        Set<OpenOption> set = new HashSet<OpenOption>(options.length);
        Collections.addAll(set, options);
        return open(path, set, NO_ATTRIBUTES);
    }

    // -- Channel operations --

    /**
     * Reads a sequence of bytes from this channel into the given buffer.
     *
     * <p> Bytes are read starting at this channel's current file position, and
     * then the file position is updated with the number of bytes actually
     * read.  Otherwise this method behaves exactly as specified in the {@link
     * ReadableByteChannel} interface. </p>
     * <p>
     *  从该通道读取一个字节序列到给定的缓冲区。
     * 
     * <p>从此通道当前文件位置开始读取字节,然后使用实际读取的字节数更新文件位置。否则,此方法的行为与{@link ReadableByteChannel}接口中指定的完全相同。 </p>
     * 
     */
    public abstract int read(ByteBuffer dst) throws IOException;

    /**
     * Reads a sequence of bytes from this channel into a subsequence of the
     * given buffers.
     *
     * <p> Bytes are read starting at this channel's current file position, and
     * then the file position is updated with the number of bytes actually
     * read.  Otherwise this method behaves exactly as specified in the {@link
     * ScatteringByteChannel} interface.  </p>
     * <p>
     *  将来自该通道的字节序列读取到给定缓冲器的子序列中。
     * 
     *  <p>从此通道当前文件位置开始读取字节,然后使用实际读取的字节数更新文件位置。否则,此方法的行为与{@link ScatteringByteChannel}接口中指定的完全相同。 </p>
     * 
     */
    public abstract long read(ByteBuffer[] dsts, int offset, int length)
        throws IOException;

    /**
     * Reads a sequence of bytes from this channel into the given buffers.
     *
     * <p> Bytes are read starting at this channel's current file position, and
     * then the file position is updated with the number of bytes actually
     * read.  Otherwise this method behaves exactly as specified in the {@link
     * ScatteringByteChannel} interface.  </p>
     * <p>
     *  从该通道读取一个字节序列到给定的缓冲区。
     * 
     *  <p>从此通道当前文件位置开始读取字节,然后使用实际读取的字节数更新文件位置。否则,此方法的行为与{@link ScatteringByteChannel}接口中指定的完全相同。 </p>
     * 
     */
    public final long read(ByteBuffer[] dsts) throws IOException {
        return read(dsts, 0, dsts.length);
    }

    /**
     * Writes a sequence of bytes to this channel from the given buffer.
     *
     * <p> Bytes are written starting at this channel's current file position
     * unless the channel is in append mode, in which case the position is
     * first advanced to the end of the file.  The file is grown, if necessary,
     * to accommodate the written bytes, and then the file position is updated
     * with the number of bytes actually written.  Otherwise this method
     * behaves exactly as specified by the {@link WritableByteChannel}
     * interface. </p>
     * <p>
     *  从给定缓冲区向此通道写入一个字节序列。
     * 
     *  <p>从此通道的当前文件位置开始写入字节,除非通道处于附加模式,在这种情况下,位置首先前进到文件结尾。如果需要,生成文件以容纳写入的字节,然后用实际写入的字节数更新文件位置。
     * 否则,此方法的行为与{@link WritableByteChannel}接口指定的完全相同。 </p>。
     * 
     */
    public abstract int write(ByteBuffer src) throws IOException;

    /**
     * Writes a sequence of bytes to this channel from a subsequence of the
     * given buffers.
     *
     * <p> Bytes are written starting at this channel's current file position
     * unless the channel is in append mode, in which case the position is
     * first advanced to the end of the file.  The file is grown, if necessary,
     * to accommodate the written bytes, and then the file position is updated
     * with the number of bytes actually written.  Otherwise this method
     * behaves exactly as specified in the {@link GatheringByteChannel}
     * interface.  </p>
     * <p>
     *  从给定缓冲区的子序列向该通道写入一个字节序列。
     * 
     * <p>从此通道的当前文件位置开始写入字节,除非通道处于附加模式,在这种情况下,位置首先前进到文件结尾。如果需要,生成文件以容纳写入的字节,然后用实际写入的字节数更新文件位置。
     * 否则,此方法的行为与{@link GatheringByteChannel}接口中指定的完全相同。 </p>。
     * 
     */
    public abstract long write(ByteBuffer[] srcs, int offset, int length)
        throws IOException;

    /**
     * Writes a sequence of bytes to this channel from the given buffers.
     *
     * <p> Bytes are written starting at this channel's current file position
     * unless the channel is in append mode, in which case the position is
     * first advanced to the end of the file.  The file is grown, if necessary,
     * to accommodate the written bytes, and then the file position is updated
     * with the number of bytes actually written.  Otherwise this method
     * behaves exactly as specified in the {@link GatheringByteChannel}
     * interface.  </p>
     * <p>
     *  从给定的缓冲区向此通道写入一个字节序列。
     * 
     *  <p>从此通道的当前文件位置开始写入字节,除非通道处于附加模式,在这种情况下,位置首先前进到文件结尾。如果需要,生成文件以容纳写入的字节,然后用实际写入的字节数更新文件位置。
     * 否则,此方法的行为与{@link GatheringByteChannel}接口中指定的完全相同。 </p>。
     * 
     */
    public final long write(ByteBuffer[] srcs) throws IOException {
        return write(srcs, 0, srcs.length);
    }


    // -- Other operations --

    /**
     * Returns this channel's file position.
     *
     * <p>
     *  返回此频道的文件位置。
     * 
     * 
     * @return  This channel's file position,
     *          a non-negative integer counting the number of bytes
     *          from the beginning of the file to the current position
     *
     * @throws  ClosedChannelException
     *          If this channel is closed
     *
     * @throws  IOException
     *          If some other I/O error occurs
     */
    public abstract long position() throws IOException;

    /**
     * Sets this channel's file position.
     *
     * <p> Setting the position to a value that is greater than the file's
     * current size is legal but does not change the size of the file.  A later
     * attempt to read bytes at such a position will immediately return an
     * end-of-file indication.  A later attempt to write bytes at such a
     * position will cause the file to be grown to accommodate the new bytes;
     * the values of any bytes between the previous end-of-file and the
     * newly-written bytes are unspecified.  </p>
     *
     * <p>
     *  设置此通道的文件位置。
     * 
     *  <p>将位置设置为大于文件当前大小的值是合法的,但不会更改文件的大小。稍后在这种位置读取字节的尝试将立即返回文件结束指示。
     * 以后尝试在这样的位置写入字节将导致文件增长以容纳新的字节;在上一个文件结束和新写入的字节之间的任何字节的值是未指定的。 </p>。
     * 
     * 
     * @param  newPosition
     *         The new position, a non-negative integer counting
     *         the number of bytes from the beginning of the file
     *
     * @return  This file channel
     *
     * @throws  ClosedChannelException
     *          If this channel is closed
     *
     * @throws  IllegalArgumentException
     *          If the new position is negative
     *
     * @throws  IOException
     *          If some other I/O error occurs
     */
    public abstract FileChannel position(long newPosition) throws IOException;

    /**
     * Returns the current size of this channel's file.
     *
     * <p>
     * 返回此频道文件的当前大小。
     * 
     * 
     * @return  The current size of this channel's file,
     *          measured in bytes
     *
     * @throws  ClosedChannelException
     *          If this channel is closed
     *
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
     * the file is not modified.  In either case, if this channel's file
     * position is greater than the given size then it is set to that size.
     * </p>
     *
     * <p>
     *  将此频道的文件截断为指定大小。
     * 
     *  <p>如果给定的大小小于文件的当前大小,则文件将被截断,丢弃超出文件新结尾的任何字节。如果给定的大小大于或等于文件的当前大小,则不会修改文件。
     * 在任一情况下,如果该通道的文件位置大于给定大小,则将其设置为该大小。
     * </p>
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
    public abstract FileChannel truncate(long size) throws IOException;

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
     * <p> The <tt>metaData</tt> parameter can be used to limit the number of
     * I/O operations that this method is required to perform.  Passing
     * <tt>false</tt> for this parameter indicates that only updates to the
     * file's content need be written to storage; passing <tt>true</tt>
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
     * this channel's file via the methods defined in this class.  It may or
     * may not force changes that were made by modifying the content of a
     * {@link MappedByteBuffer <i>mapped byte buffer</i>} obtained by
     * invoking the {@link #map map} method.  Invoking the {@link
     * MappedByteBuffer#force force} method of the mapped byte buffer will
     * force changes made to the buffer's content to be written.  </p>
     *
     * <p>
     *  强制将此频道文件的所有更新写入包含该频道的存储设备。
     * 
     *  <p>如果此频道的文件位于本地存储设备上,则当此方法返回时,将确保自此频道创建以来对文件所做的所有更改或自上次调用此方法以来已写入该设备。这有助于确保关键信息在系统崩溃的情况下不会丢失。
     * 
     *  <p>如果文件不驻留在本地设备上,则不进行此类保证。
     * 
     * <p> <tt> metaData </tt>参数可用于限制此方法需要执行的I / O操作数。
     * 对此参数传递<tt> false </tt>表示只有对文件内容的更新才需要写入存储;传递<tt> true </tt>表示必须写入对文件内容和元数据的更新,这通常需要至少一个I / O操作。
     * 此参数是否实际上具有任何效果取决于底层操作系统,因此未指定。
     * 
     *  <p>调用此方法可能会导致发生I / O操作,即使只打开通道进行读取。例如,一些操作系统将最后访问时间保持为文件的元数据的一部分,并且每当读取文件时都更新该时间。
     * 这是否实际完成是系统依赖的,因此未指定。
     * 
     *  <p>此方法仅保证通过此类中定义的方法强制对此通道文件所做的更改。
     * 它可以或可以不强制通过修改通过调用{@link #map map}方法获得的{@link MappedByteBuffer <i>映射的字节缓冲区</i>}的内容而做出的改变。
     * 调用映射字节缓冲区的{@link MappedByteBuffer#force force}方法将强制写入对缓冲区内容所做的更改。 </p>。
     * 
     * 
     * @param   metaData
     *          If <tt>true</tt> then this method is required to force changes
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
     * Transfers bytes from this channel's file to the given writable byte
     * channel.
     *
     * <p> An attempt is made to read up to <tt>count</tt> bytes starting at
     * the given <tt>position</tt> in this channel's file and write them to the
     * target channel.  An invocation of this method may or may not transfer
     * all of the requested bytes; whether or not it does so depends upon the
     * natures and states of the channels.  Fewer than the requested number of
     * bytes are transferred if this channel's file contains fewer than
     * <tt>count</tt> bytes starting at the given <tt>position</tt>, or if the
     * target channel is non-blocking and it has fewer than <tt>count</tt>
     * bytes free in its output buffer.
     *
     * <p> This method does not modify this channel's position.  If the given
     * position is greater than the file's current size then no bytes are
     * transferred.  If the target channel has a position then bytes are
     * written starting at that position and then the position is incremented
     * by the number of bytes written.
     *
     * <p> This method is potentially much more efficient than a simple loop
     * that reads from this channel and writes to the target channel.  Many
     * operating systems can transfer bytes directly from the filesystem cache
     * to the target channel without actually copying them.  </p>
     *
     * <p>
     *  将字节从此通道的文件传输到给定的可写字节通道。
     * 
     * <p>尝试从此频道文件中的给定<tt>位置</tt>开始读取<tt>计数</tt>字节,并将其写入目标频道。该方法的调用可以或可以不传送所有请求的字节;是否这样做取决于渠道的性质和状态。
     * 如果此通道的文件包含少于<tt>计数</tt>的字节开始于给定的<tt>位置</tt>,或者目标通道是非阻塞的,并且它具有在其输出缓冲区中少于<tt> count </tt>个字节。
     * 
     *  <p>此方法不会修改此频道的位置。如果给定位置大于文件的当前大小,则不传输任何字节。如果目标通道具有位置,则从该位置开始写入字节,然后该位置增加写入的字节数。
     * 
     *  <p>此方法比从此通道读取并写入目标通道的简单循环更有效率。许多操作系统可以将字节直接从文件系统缓存传输到目标通道,而不实际复制它们。 </p>
     * 
     * 
     * @param  position
     *         The position within the file at which the transfer is to begin;
     *         must be non-negative
     *
     * @param  count
     *         The maximum number of bytes to be transferred; must be
     *         non-negative
     *
     * @param  target
     *         The target channel
     *
     * @return  The number of bytes, possibly zero,
     *          that were actually transferred
     *
     * @throws IllegalArgumentException
     *         If the preconditions on the parameters do not hold
     *
     * @throws  NonReadableChannelException
     *          If this channel was not opened for reading
     *
     * @throws  NonWritableChannelException
     *          If the target channel was not opened for writing
     *
     * @throws  ClosedChannelException
     *          If either this channel or the target channel is closed
     *
     * @throws  AsynchronousCloseException
     *          If another thread closes either channel
     *          while the transfer is in progress
     *
     * @throws  ClosedByInterruptException
     *          If another thread interrupts the current thread while the
     *          transfer is in progress, thereby closing both channels and
     *          setting the current thread's interrupt status
     *
     * @throws  IOException
     *          If some other I/O error occurs
     */
    public abstract long transferTo(long position, long count,
                                    WritableByteChannel target)
        throws IOException;

    /**
     * Transfers bytes into this channel's file from the given readable byte
     * channel.
     *
     * <p> An attempt is made to read up to <tt>count</tt> bytes from the
     * source channel and write them to this channel's file starting at the
     * given <tt>position</tt>.  An invocation of this method may or may not
     * transfer all of the requested bytes; whether or not it does so depends
     * upon the natures and states of the channels.  Fewer than the requested
     * number of bytes will be transferred if the source channel has fewer than
     * <tt>count</tt> bytes remaining, or if the source channel is non-blocking
     * and has fewer than <tt>count</tt> bytes immediately available in its
     * input buffer.
     *
     * <p> This method does not modify this channel's position.  If the given
     * position is greater than the file's current size then no bytes are
     * transferred.  If the source channel has a position then bytes are read
     * starting at that position and then the position is incremented by the
     * number of bytes read.
     *
     * <p> This method is potentially much more efficient than a simple loop
     * that reads from the source channel and writes to this channel.  Many
     * operating systems can transfer bytes directly from the source channel
     * into the filesystem cache without actually copying them.  </p>
     *
     * <p>
     *  将字节从给定的可读字节通道传输到此通道的文件中。
     * 
     * <p>尝试从源通道读取最多<tt>个</tt>个字节,并将它们从给定的<tt>位置</tt>开始写入此通道的文件。该方法的调用可以或可以不传送所有请求的字节;是否这样做取决于渠道的性质和状态。
     * 如果源通道少于<tt>计数</tt>剩余字节,或源通道为非阻塞且少于<tt>计数</tt>字节,则传输的字节数少于请求的字节数立即在其输入缓冲区中可用。
     * 
     *  <p>此方法不会修改此频道的位置。如果给定位置大于文件的当前大小,则不传输任何字节。如果源通道具有位置,则从该位置开始读取字节,然后该位置增加读取的字节数。
     * 
     *  <p>此方法比从源通道读取并写入此通道的简单循环更有效率。许多操作系统可以将字节直接从源通道传输到文件系统缓存中,而不实际复制它们。 </p>
     * 
     * 
     * @param  src
     *         The source channel
     *
     * @param  position
     *         The position within the file at which the transfer is to begin;
     *         must be non-negative
     *
     * @param  count
     *         The maximum number of bytes to be transferred; must be
     *         non-negative
     *
     * @return  The number of bytes, possibly zero,
     *          that were actually transferred
     *
     * @throws IllegalArgumentException
     *         If the preconditions on the parameters do not hold
     *
     * @throws  NonReadableChannelException
     *          If the source channel was not opened for reading
     *
     * @throws  NonWritableChannelException
     *          If this channel was not opened for writing
     *
     * @throws  ClosedChannelException
     *          If either this channel or the source channel is closed
     *
     * @throws  AsynchronousCloseException
     *          If another thread closes either channel
     *          while the transfer is in progress
     *
     * @throws  ClosedByInterruptException
     *          If another thread interrupts the current thread while the
     *          transfer is in progress, thereby closing both channels and
     *          setting the current thread's interrupt status
     *
     * @throws  IOException
     *          If some other I/O error occurs
     */
    public abstract long transferFrom(ReadableByteChannel src,
                                      long position, long count)
        throws IOException;

    /**
     * Reads a sequence of bytes from this channel into the given buffer,
     * starting at the given file position.
     *
     * <p> This method works in the same manner as the {@link
     * #read(ByteBuffer)} method, except that bytes are read starting at the
     * given file position rather than at the channel's current position.  This
     * method does not modify this channel's position.  If the given position
     * is greater than the file's current size then no bytes are read.  </p>
     *
     * <p>
     *  从给定的文件位置开始,从该通道读取一个字节序列到给定的缓冲区。
     * 
     * <p>此方法的工作方式与{@link #read(ByteBuffer)}方法相同,除了字节是从给定文件位置开始读取,而不是在通道的当前位置读取。此方法不会修改此通道的位置。
     * 如果给定位置大于文件的当前大小,则不读取任何字节。 </p>。
     * 
     * 
     * @param  dst
     *         The buffer into which bytes are to be transferred
     *
     * @param  position
     *         The file position at which the transfer is to begin;
     *         must be non-negative
     *
     * @return  The number of bytes read, possibly zero, or <tt>-1</tt> if the
     *          given position is greater than or equal to the file's current
     *          size
     *
     * @throws  IllegalArgumentException
     *          If the position is negative
     *
     * @throws  NonReadableChannelException
     *          If this channel was not opened for reading
     *
     * @throws  ClosedChannelException
     *          If this channel is closed
     *
     * @throws  AsynchronousCloseException
     *          If another thread closes this channel
     *          while the read operation is in progress
     *
     * @throws  ClosedByInterruptException
     *          If another thread interrupts the current thread
     *          while the read operation is in progress, thereby
     *          closing the channel and setting the current thread's
     *          interrupt status
     *
     * @throws  IOException
     *          If some other I/O error occurs
     */
    public abstract int read(ByteBuffer dst, long position) throws IOException;

    /**
     * Writes a sequence of bytes to this channel from the given buffer,
     * starting at the given file position.
     *
     * <p> This method works in the same manner as the {@link
     * #write(ByteBuffer)} method, except that bytes are written starting at
     * the given file position rather than at the channel's current position.
     * This method does not modify this channel's position.  If the given
     * position is greater than the file's current size then the file will be
     * grown to accommodate the new bytes; the values of any bytes between the
     * previous end-of-file and the newly-written bytes are unspecified.  </p>
     *
     * <p>
     *  从给定的文件位置开始,从给定缓冲区向该通道写入一个字节序列。
     * 
     *  <p>此方法的工作方式与{@link #write(ByteBuffer)}方法相同,除了字节是从给定文件位置开始写入,而不是在通道的当前位置。此方法不会修改此通道的位置。
     * 如果给定位置大于文件的当前大小,则文件将增长以容纳新字节;在上一个文件结束和新写入的字节之间的任何字节的值是未指定的。 </p>。
     * 
     * 
     * @param  src
     *         The buffer from which bytes are to be transferred
     *
     * @param  position
     *         The file position at which the transfer is to begin;
     *         must be non-negative
     *
     * @return  The number of bytes written, possibly zero
     *
     * @throws  IllegalArgumentException
     *          If the position is negative
     *
     * @throws  NonWritableChannelException
     *          If this channel was not opened for writing
     *
     * @throws  ClosedChannelException
     *          If this channel is closed
     *
     * @throws  AsynchronousCloseException
     *          If another thread closes this channel
     *          while the write operation is in progress
     *
     * @throws  ClosedByInterruptException
     *          If another thread interrupts the current thread
     *          while the write operation is in progress, thereby
     *          closing the channel and setting the current thread's
     *          interrupt status
     *
     * @throws  IOException
     *          If some other I/O error occurs
     */
    public abstract int write(ByteBuffer src, long position) throws IOException;


    // -- Memory-mapped buffers --

    /**
     * A typesafe enumeration for file-mapping modes.
     *
     * <p>
     *  文件映射模式的类型安全枚举。
     * 
     * 
     * @since 1.4
     *
     * @see java.nio.channels.FileChannel#map
     */
    public static class MapMode {

        /**
         * Mode for a read-only mapping.
         * <p>
         *  只读映射的模式。
         * 
         */
        public static final MapMode READ_ONLY
            = new MapMode("READ_ONLY");

        /**
         * Mode for a read/write mapping.
         * <p>
         *  读/写映射的模式。
         * 
         */
        public static final MapMode READ_WRITE
            = new MapMode("READ_WRITE");

        /**
         * Mode for a private (copy-on-write) mapping.
         * <p>
         *  私人(写时复制)映射的模式。
         * 
         */
        public static final MapMode PRIVATE
            = new MapMode("PRIVATE");

        private final String name;

        private MapMode(String name) {
            this.name = name;
        }

        /**
         * Returns a string describing this file-mapping mode.
         *
         * <p>
         *  返回描述此文件映射模式的字符串。
         * 
         * 
         * @return  A descriptive string
         */
        public String toString() {
            return name;
        }

    }

    /**
     * Maps a region of this channel's file directly into memory.
     *
     * <p> A region of a file may be mapped into memory in one of three modes:
     * </p>
     *
     * <ul>
     *
     *   <li><p> <i>Read-only:</i> Any attempt to modify the resulting buffer
     *   will cause a {@link java.nio.ReadOnlyBufferException} to be thrown.
     *   ({@link MapMode#READ_ONLY MapMode.READ_ONLY}) </p></li>
     *
     *   <li><p> <i>Read/write:</i> Changes made to the resulting buffer will
     *   eventually be propagated to the file; they may or may not be made
     *   visible to other programs that have mapped the same file.  ({@link
     *   MapMode#READ_WRITE MapMode.READ_WRITE}) </p></li>
     *
     *   <li><p> <i>Private:</i> Changes made to the resulting buffer will not
     *   be propagated to the file and will not be visible to other programs
     *   that have mapped the same file; instead, they will cause private
     *   copies of the modified portions of the buffer to be created.  ({@link
     *   MapMode#PRIVATE MapMode.PRIVATE}) </p></li>
     *
     * </ul>
     *
     * <p> For a read-only mapping, this channel must have been opened for
     * reading; for a read/write or private mapping, this channel must have
     * been opened for both reading and writing.
     *
     * <p> The {@link MappedByteBuffer <i>mapped byte buffer</i>}
     * returned by this method will have a position of zero and a limit and
     * capacity of <tt>size</tt>; its mark will be undefined.  The buffer and
     * the mapping that it represents will remain valid until the buffer itself
     * is garbage-collected.
     *
     * <p> A mapping, once established, is not dependent upon the file channel
     * that was used to create it.  Closing the channel, in particular, has no
     * effect upon the validity of the mapping.
     *
     * <p> Many of the details of memory-mapped files are inherently dependent
     * upon the underlying operating system and are therefore unspecified.  The
     * behavior of this method when the requested region is not completely
     * contained within this channel's file is unspecified.  Whether changes
     * made to the content or size of the underlying file, by this program or
     * another, are propagated to the buffer is unspecified.  The rate at which
     * changes to the buffer are propagated to the file is unspecified.
     *
     * <p> For most operating systems, mapping a file into memory is more
     * expensive than reading or writing a few tens of kilobytes of data via
     * the usual {@link #read read} and {@link #write write} methods.  From the
     * standpoint of performance it is generally only worth mapping relatively
     * large files into memory.  </p>
     *
     * <p>
     *  将此频道文件的某个区域直接映射到内存中。
     * 
     *  <p>文件的区域可以以三种模式之一映射到存储器：
     * </p>
     * 
     * <ul>
     * 
     *  <li> <p> <i>只读：</i>任何修改结果缓冲区的尝试都会导致{@link java.nio.ReadOnlyBufferException}被抛出。
     *  ({@link MapMode#READ_ONLY MapMode.READ_ONLY})</p> </li>。
     * 
     * <li> <p> <i>读/写：</i>对生成的缓冲区所做的更改将最终传播到文件;它们可以或者可以不被映射相同文件的其他程序可见。
     *  ({@link MapMode#READ_WRITE MapMode.READ_WRITE})</p> </li>。
     * 
     *  <li> <p> <i>私人：</i>对生成的缓冲区所做的更改不会传播到该文件,并且对映射了相同文件的其他程序不可见;相反,它们将导致创建缓冲区的修改部分的私有副本。
     *  ({@link MapMode#PRIVATE MapMode.PRIVATE})</p> </li>。
     * 
     * </ul>
     * 
     *  <p>对于只读映射,此通道必须已打开以进行读取;对于读/写或私有映射,该通道必须已经被打开用于读取和写入。
     * 
     *  <p>此方法返回的{@link MappedByteBuffer <i>映射字节缓冲区</i>}将具有零位置和<tt>大小</tt>的限制和容量;其标记将未定义。
     * 缓冲区及其所表示的映射将保持有效,直到缓冲区本身被垃圾回收。
     * 
     *  <p>映射一旦建立,就不依赖于用于创建它的文件通道。特别地,关闭通道对映射的有效性没有影响。
     * 
     * <p>内存映射文件的许多细节固有地依赖于底层操作系统,因此未指定。当请求的区域未完全包含在此通道的文件中时,此方法的行为未指定。
     * 未指定对由此程序或另一程序对底层文件的内容或大小进行的修改是否被传播到缓冲区。未指定将缓冲区的更改传播到文件的速率。
     * 
     *  <p>对于大多数操作系统,将文件映射到内存比通过通常的{@link #read read}和{@link #write write}方法读取或写入几十千字节的数据更为昂贵。
     * 从性能的角度来看,它通常只值得将相对较大的文件映射到内存中。 </p>。
     * 
     * 
     * @param  mode
     *         One of the constants {@link MapMode#READ_ONLY READ_ONLY}, {@link
     *         MapMode#READ_WRITE READ_WRITE}, or {@link MapMode#PRIVATE
     *         PRIVATE} defined in the {@link MapMode} class, according to
     *         whether the file is to be mapped read-only, read/write, or
     *         privately (copy-on-write), respectively
     *
     * @param  position
     *         The position within the file at which the mapped region
     *         is to start; must be non-negative
     *
     * @param  size
     *         The size of the region to be mapped; must be non-negative and
     *         no greater than {@link java.lang.Integer#MAX_VALUE}
     *
     * @return  The mapped byte buffer
     *
     * @throws NonReadableChannelException
     *         If the <tt>mode</tt> is {@link MapMode#READ_ONLY READ_ONLY} but
     *         this channel was not opened for reading
     *
     * @throws NonWritableChannelException
     *         If the <tt>mode</tt> is {@link MapMode#READ_WRITE READ_WRITE} or
     *         {@link MapMode#PRIVATE PRIVATE} but this channel was not opened
     *         for both reading and writing
     *
     * @throws IllegalArgumentException
     *         If the preconditions on the parameters do not hold
     *
     * @throws IOException
     *         If some other I/O error occurs
     *
     * @see java.nio.channels.FileChannel.MapMode
     * @see java.nio.MappedByteBuffer
     */
    public abstract MappedByteBuffer map(MapMode mode,
                                         long position, long size)
        throws IOException;


    // -- Locks --

    /**
     * Acquires a lock on the given region of this channel's file.
     *
     * <p> An invocation of this method will block until the region can be
     * locked, this channel is closed, or the invoking thread is interrupted,
     * whichever comes first.
     *
     * <p> If this channel is closed by another thread during an invocation of
     * this method then an {@link AsynchronousCloseException} will be thrown.
     *
     * <p> If the invoking thread is interrupted while waiting to acquire the
     * lock then its interrupt status will be set and a {@link
     * FileLockInterruptionException} will be thrown.  If the invoker's
     * interrupt status is set when this method is invoked then that exception
     * will be thrown immediately; the thread's interrupt status will not be
     * changed.
     *
     * <p> The region specified by the <tt>position</tt> and <tt>size</tt>
     * parameters need not be contained within, or even overlap, the actual
     * underlying file.  Lock regions are fixed in size; if a locked region
     * initially contains the end of the file and the file grows beyond the
     * region then the new portion of the file will not be covered by the lock.
     * If a file is expected to grow in size and a lock on the entire file is
     * required then a region starting at zero, and no smaller than the
     * expected maximum size of the file, should be locked.  The zero-argument
     * {@link #lock()} method simply locks a region of size {@link
     * Long#MAX_VALUE}.
     *
     * <p> Some operating systems do not support shared locks, in which case a
     * request for a shared lock is automatically converted into a request for
     * an exclusive lock.  Whether the newly-acquired lock is shared or
     * exclusive may be tested by invoking the resulting lock object's {@link
     * FileLock#isShared() isShared} method.
     *
     * <p> File locks are held on behalf of the entire Java virtual machine.
     * They are not suitable for controlling access to a file by multiple
     * threads within the same virtual machine.  </p>
     *
     * <p>
     *  在此频道文件的给定区域获取锁定。
     * 
     *  <p>此方法的调用将阻塞,直到该区域可以锁定,此通道关闭,或调用线程中断,以先到者为准。
     * 
     *  <p>如果此通道在调用此方法期间被另一个线程关闭,那么将抛出{@link AsynchronousCloseException}。
     * 
     * <p>如果调用线程在等待获取锁期间被中断,那么它的中断状态将被设置,并且将抛出一个{@link FileLockInterruptionException}。
     * 如果调用此方法时调用者的中断状态被设置,那么该异常将立即抛出;线程的中断状态将不会改变。
     * 
     *  <p>由<tt>位置</tt>和<tt>大小</tt>参数指定的区域不需要包含在实际底层文件中,甚至不会重叠。
     * 锁定区域大小固定;如果锁定区域最初包含文件的结尾并且文件增长超过该区域,则该文件的新部分将不被锁所覆盖。
     * 如果文件预计在大小上增长并且需要对整个文件进行锁定,则应该锁定从零开始并且不小于文件的预期最大大小的区域。
     * 零参数{@link #lock()}方法只锁定大小为{@link Long#MAX_VALUE}的区域。
     * 
     *  <p>某些操作系统不支持共享锁,在这种情况下,共享锁的请求会自动转换为独占锁的请求。
     * 可以通过调用所得锁对象的{@link FileLock#isShared()isShared}方法来测试新获取的锁是共享还是排他。
     * 
     *  <p>文件锁定代表整个Java虚拟机。它们不适合控制同一虚拟机中的多个线程对文件的访问。 </p>
     * 
     * 
     * @param  position
     *         The position at which the locked region is to start; must be
     *         non-negative
     *
     * @param  size
     *         The size of the locked region; must be non-negative, and the sum
     *         <tt>position</tt>&nbsp;+&nbsp;<tt>size</tt> must be non-negative
     *
     * @param  shared
     *         <tt>true</tt> to request a shared lock, in which case this
     *         channel must be open for reading (and possibly writing);
     *         <tt>false</tt> to request an exclusive lock, in which case this
     *         channel must be open for writing (and possibly reading)
     *
     * @return  A lock object representing the newly-acquired lock
     *
     * @throws  IllegalArgumentException
     *          If the preconditions on the parameters do not hold
     *
     * @throws  ClosedChannelException
     *          If this channel is closed
     *
     * @throws  AsynchronousCloseException
     *          If another thread closes this channel while the invoking
     *          thread is blocked in this method
     *
     * @throws  FileLockInterruptionException
     *          If the invoking thread is interrupted while blocked in this
     *          method
     *
     * @throws  OverlappingFileLockException
     *          If a lock that overlaps the requested region is already held by
     *          this Java virtual machine, or if another thread is already
     *          blocked in this method and is attempting to lock an overlapping
     *          region
     *
     * @throws  NonReadableChannelException
     *          If <tt>shared</tt> is <tt>true</tt> this channel was not
     *          opened for reading
     *
     * @throws  NonWritableChannelException
     *          If <tt>shared</tt> is <tt>false</tt> but this channel was not
     *          opened for writing
     *
     * @throws  IOException
     *          If some other I/O error occurs
     *
     * @see     #lock()
     * @see     #tryLock()
     * @see     #tryLock(long,long,boolean)
     */
    public abstract FileLock lock(long position, long size, boolean shared)
        throws IOException;

    /**
     * Acquires an exclusive lock on this channel's file.
     *
     * <p> An invocation of this method of the form <tt>fc.lock()</tt> behaves
     * in exactly the same way as the invocation
     *
     * <pre>
     *     fc.{@link #lock(long,long,boolean) lock}(0L, Long.MAX_VALUE, false) </pre>
     *
     * <p>
     * 获取此频道文件的独占锁。
     * 
     *  <p>以<tt> fc.lock()</tt>的形式调用此方法的行为与调用的方式完全相同
     * 
     * <pre>
     *  fc.{@link #lock(long,long,boolean)lock}(0L,Long.MAX_VALUE,false)</pre>
     * 
     * 
     * @return  A lock object representing the newly-acquired lock
     *
     * @throws  ClosedChannelException
     *          If this channel is closed
     *
     * @throws  AsynchronousCloseException
     *          If another thread closes this channel while the invoking
     *          thread is blocked in this method
     *
     * @throws  FileLockInterruptionException
     *          If the invoking thread is interrupted while blocked in this
     *          method
     *
     * @throws  OverlappingFileLockException
     *          If a lock that overlaps the requested region is already held by
     *          this Java virtual machine, or if another thread is already
     *          blocked in this method and is attempting to lock an overlapping
     *          region of the same file
     *
     * @throws  NonWritableChannelException
     *          If this channel was not opened for writing
     *
     * @throws  IOException
     *          If some other I/O error occurs
     *
     * @see     #lock(long,long,boolean)
     * @see     #tryLock()
     * @see     #tryLock(long,long,boolean)
     */
    public final FileLock lock() throws IOException {
        return lock(0L, Long.MAX_VALUE, false);
    }

    /**
     * Attempts to acquire a lock on the given region of this channel's file.
     *
     * <p> This method does not block.  An invocation always returns
     * immediately, either having acquired a lock on the requested region or
     * having failed to do so.  If it fails to acquire a lock because an
     * overlapping lock is held by another program then it returns
     * <tt>null</tt>.  If it fails to acquire a lock for any other reason then
     * an appropriate exception is thrown.
     *
     * <p> The region specified by the <tt>position</tt> and <tt>size</tt>
     * parameters need not be contained within, or even overlap, the actual
     * underlying file.  Lock regions are fixed in size; if a locked region
     * initially contains the end of the file and the file grows beyond the
     * region then the new portion of the file will not be covered by the lock.
     * If a file is expected to grow in size and a lock on the entire file is
     * required then a region starting at zero, and no smaller than the
     * expected maximum size of the file, should be locked.  The zero-argument
     * {@link #tryLock()} method simply locks a region of size {@link
     * Long#MAX_VALUE}.
     *
     * <p> Some operating systems do not support shared locks, in which case a
     * request for a shared lock is automatically converted into a request for
     * an exclusive lock.  Whether the newly-acquired lock is shared or
     * exclusive may be tested by invoking the resulting lock object's {@link
     * FileLock#isShared() isShared} method.
     *
     * <p> File locks are held on behalf of the entire Java virtual machine.
     * They are not suitable for controlling access to a file by multiple
     * threads within the same virtual machine.  </p>
     *
     * <p>
     *  尝试在此频道文件的给定区域上获取锁定。
     * 
     *  <p>此方法不阻止。调用总是立即返回,或者已经获得了对所请求区域的锁定,或者没有这样做。如果由于重叠锁由另一个程序持有而无法获取锁,则它返回<tt> null </tt>。
     * 如果由于任何其他原因无法获取锁,那么会抛出一个适当的异常。
     * 
     *  <p>由<tt>位置</tt>和<tt>大小</tt>参数指定的区域不需要包含在实际底层文件中,甚至不会重叠。
     * 锁定区域大小固定;如果锁定区域最初包含文件的结尾并且文件增长超过该区域,则该文件的新部分将不被锁所覆盖。
     * 如果文件预计在大小上增长并且需要对整个文件进行锁定,则应该锁定从零开始并且不小于文件的预期最大大小的区域。
     * 零参数{@link #tryLock()}方法只锁定大小为{@link Long#MAX_VALUE}的区域。
     * 
     * <p>某些操作系统不支持共享锁,在这种情况下,共享锁的请求将自动转换为独占锁的请求。
     * 可以通过调用所得锁对象的{@link FileLock#isShared()isShared}方法来测试新获取的锁是共享还是排他。
     * 
     * @param  position
     *         The position at which the locked region is to start; must be
     *         non-negative
     *
     * @param  size
     *         The size of the locked region; must be non-negative, and the sum
     *         <tt>position</tt>&nbsp;+&nbsp;<tt>size</tt> must be non-negative
     *
     * @param  shared
     *         <tt>true</tt> to request a shared lock,
     *         <tt>false</tt> to request an exclusive lock
     *
     * @return  A lock object representing the newly-acquired lock,
     *          or <tt>null</tt> if the lock could not be acquired
     *          because another program holds an overlapping lock
     *
     * @throws  IllegalArgumentException
     *          If the preconditions on the parameters do not hold
     *
     * @throws  ClosedChannelException
     *          If this channel is closed
     *
     * @throws  OverlappingFileLockException
     *          If a lock that overlaps the requested region is already held by
     *          this Java virtual machine, or if another thread is already
     *          blocked in this method and is attempting to lock an overlapping
     *          region of the same file
     *
     * @throws  IOException
     *          If some other I/O error occurs
     *
     * @see     #lock()
     * @see     #lock(long,long,boolean)
     * @see     #tryLock()
     */
    public abstract FileLock tryLock(long position, long size, boolean shared)
        throws IOException;

    /**
     * Attempts to acquire an exclusive lock on this channel's file.
     *
     * <p> An invocation of this method of the form <tt>fc.tryLock()</tt>
     * behaves in exactly the same way as the invocation
     *
     * <pre>
     *     fc.{@link #tryLock(long,long,boolean) tryLock}(0L, Long.MAX_VALUE, false) </pre>
     *
     * <p>
     * 
     *  <p>文件锁定代表整个Java虚拟机。它们不适合控制同一虚拟机中的多个线程对文件的访问。 </p>
     * 
     * 
     * @return  A lock object representing the newly-acquired lock,
     *          or <tt>null</tt> if the lock could not be acquired
     *          because another program holds an overlapping lock
     *
     * @throws  ClosedChannelException
     *          If this channel is closed
     *
     * @throws  OverlappingFileLockException
     *          If a lock that overlaps the requested region is already held by
     *          this Java virtual machine, or if another thread is already
     *          blocked in this method and is attempting to lock an overlapping
     *          region
     *
     * @throws  IOException
     *          If some other I/O error occurs
     *
     * @see     #lock()
     * @see     #lock(long,long,boolean)
     * @see     #tryLock(long,long,boolean)
     */
    public final FileLock tryLock() throws IOException {
        return tryLock(0L, Long.MAX_VALUE, false);
    }

}
