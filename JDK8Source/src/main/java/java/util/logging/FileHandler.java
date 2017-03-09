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

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static java.nio.file.StandardOpenOption.WRITE;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.OverlappingFileLockException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashSet;
import java.util.Set;

/**
 * Simple file logging <tt>Handler</tt>.
 * <p>
 * The <tt>FileHandler</tt> can either write to a specified file,
 * or it can write to a rotating set of files.
 * <p>
 * For a rotating set of files, as each file reaches a given size
 * limit, it is closed, rotated out, and a new file opened.
 * Successively older files are named by adding "0", "1", "2",
 * etc. into the base filename.
 * <p>
 * By default buffering is enabled in the IO libraries but each log
 * record is flushed out when it is complete.
 * <p>
 * By default the <tt>XMLFormatter</tt> class is used for formatting.
 * <p>
 * <b>Configuration:</b>
 * By default each <tt>FileHandler</tt> is initialized using the following
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
 *        (defaults to <tt>java.util.logging.XMLFormatter</tt>) </li>
 * <li>   &lt;handler-name&gt;.encoding
 *        the name of the character set encoding to use (defaults to
 *        the default platform encoding). </li>
 * <li>   &lt;handler-name&gt;.limit
 *        specifies an approximate maximum amount to write (in bytes)
 *        to any one file.  If this is zero, then there is no limit.
 *        (Defaults to no limit). </li>
 * <li>   &lt;handler-name&gt;.count
 *        specifies how many output files to cycle through (defaults to 1). </li>
 * <li>   &lt;handler-name&gt;.pattern
 *        specifies a pattern for generating the output file name.  See
 *        below for details. (Defaults to "%h/java%u.log"). </li>
 * <li>   &lt;handler-name&gt;.append
 *        specifies whether the FileHandler should append onto
 *        any existing files (defaults to false). </li>
 * </ul>
 * <p>
 * For example, the properties for {@code FileHandler} would be:
 * <ul>
 * <li>   java.util.logging.FileHandler.level=INFO </li>
 * <li>   java.util.logging.FileHandler.formatter=java.util.logging.SimpleFormatter </li>
 * </ul>
 * <p>
 * For a custom handler, e.g. com.foo.MyHandler, the properties would be:
 * <ul>
 * <li>   com.foo.MyHandler.level=INFO </li>
 * <li>   com.foo.MyHandler.formatter=java.util.logging.SimpleFormatter </li>
 * </ul>
 * <p>
 * A pattern consists of a string that includes the following special
 * components that will be replaced at runtime:
 * <ul>
 * <li>    "/"    the local pathname separator </li>
 * <li>     "%t"   the system temporary directory </li>
 * <li>     "%h"   the value of the "user.home" system property </li>
 * <li>     "%g"   the generation number to distinguish rotated logs </li>
 * <li>     "%u"   a unique number to resolve conflicts </li>
 * <li>     "%%"   translates to a single percent sign "%" </li>
 * </ul>
 * If no "%g" field has been specified and the file count is greater
 * than one, then the generation number will be added to the end of
 * the generated filename, after a dot.
 * <p>
 * Thus for example a pattern of "%t/java%g.log" with a count of 2
 * would typically cause log files to be written on Solaris to
 * /var/tmp/java0.log and /var/tmp/java1.log whereas on Windows 95 they
 * would be typically written to C:\TEMP\java0.log and C:\TEMP\java1.log
 * <p>
 * Generation numbers follow the sequence 0, 1, 2, etc.
 * <p>
 * Normally the "%u" unique field is set to 0.  However, if the <tt>FileHandler</tt>
 * tries to open the filename and finds the file is currently in use by
 * another process it will increment the unique number field and try
 * again.  This will be repeated until <tt>FileHandler</tt> finds a file name that
 * is  not currently in use. If there is a conflict and no "%u" field has
 * been specified, it will be added at the end of the filename after a dot.
 * (This will be after any automatically added generation number.)
 * <p>
 * Thus if three processes were all trying to log to fred%u.%g.txt then
 * they  might end up using fred0.0.txt, fred1.0.txt, fred2.0.txt as
 * the first file in their rotating sequences.
 * <p>
 * Note that the use of unique ids to avoid conflicts is only guaranteed
 * to work reliably when using a local disk file system.
 *
 * <p>
 *  简单文件记录<tt>处理程序</tt>。
 * <p>
 *  <tt> FileHandler </tt>可以写入指定的文件,也可以写入一组旋转的文件。
 * <p>
 *  对于一组旋转的文件,由于每个文件达到一个给定的大小限制,它被关闭,旋转出来,并打开一个新文件。通过将"0","1","2"等添加到基本文件名中来命名连续的较旧文件。
 * <p>
 *  默认情况下,在IO库中启用缓冲,但每个日志记录在完成后将被刷新。
 * <p>
 *  默认情况下,<tt> XMLFormatter </tt>类用于格式化。
 * <p>
 *  <b>配置：</b>默认情况下,每个<tt> FileHandler </tt>使用以下<tt> LogManager </tt>配置属性初始化,其中<tt>&lt; handler-name&gt;
 *  </tt>到处理程序的完全限定类名。
 * 如果未定义属性(或具有无效值),则使用指定的默认值。
 * <ul>
 * <li>&lt; handler-name&gt; .level指定<tt>处理程序</tt>(默认为<tt> Level.ALL </tt>)的默认级别。
 *  </li> <li>&lt; handler-name&gt; .filter指定要使用的<tt> Filter </tt>类的名称(默认为<tt> Filter </tt>)。
 *  </li> <li>&lt; handler-name&gt; .formatter指定要使用的<tt> Formatter </tt>类的名称(默认为<tt> java.util.logging.X
 * MLFormatter </tt>)< / li> <li>&lt; handler-name&gt; .encoding要使用的字符集编码的名称(默认为默认平台编码)。
 *  </li> <li>&lt; handler-name&gt; .filter指定要使用的<tt> Filter </tt>类的名称(默认为<tt> Filter </tt>)。
 *  </li> <li>&lt; handler-name&gt; .limit指定要写入任何一个文件的大致最大值(以字节为单位)。如果这是零,那么没有限制。 (默认为无限制)。
 *  </li> <li>&lt; handler-name&gt; .count指定要循环的输出文件数量(默认为1)。
 *  </li> <li>&lt; handler-name&gt; .pattern指定用于生成输出文件名的模式。详情请参阅下文。 (默认为"％h / java％u.log")。
 *  </li> <li>&lt; handler-name&gt; .append指定FileHandler是否应附加到任何现有文件(默认为false)。 </li>。
 * </ul>
 * <p>
 *  例如,{@code FileHandler}的属性将是：
 * <ul>
 *  <li> java.util.logging.FileHandler.level = INFO </li> <li> java.util.logging.FileHandler.formatter =
 *  java.util.logging.SimpleFormatter </li>。
 * </ul>
 * <p>
 *  对于自定义处理程序,例如com.foo.MyHandler,属性将是：
 * <ul>
 * <li> com.foo.MyHandler.level = INFO </li> <li> com.foo.MyHandler.formatter = java.util.logging.Simple
 * Formatter </li>。
 * </ul>
 * <p>
 *  模式由一个字符串组成,该字符串包含将在运行时替换的以下特殊组件：
 * <ul>
 *  </li> <li>"％h"系统属性的"user.home"值</li> </li> > <li>"％g"用于区分旋转日志的世代号</li> <li>"％u"用于解决冲突的唯一号码</li> ％"</li>
 * 。
 * </ul>
 *  如果没有指定"％g"字段,并且文件计数大于1,则生成编号将添加到生成的文件名的末尾。
 * <p>
 *  因此,例如,计数为2的"％t / java％g.log"模式通常会导致日志文件在Solaris上写入/var/tmp/java0.log和/var/tmp/java1.log,而在Windows 95
 * 上,他们通常会写入到C：\ TEMP \ java0.log和C：\ TEMP \ java1.log。
 * <p>
 *  生成数字遵循序列0,1,2等。
 * <p>
 * 通常,"％u"唯一字段设置为0.但是,如果<tt> FileHandler </tt>尝试打开文件名,并发现文件当前正由另一个进程使用,它将增加唯一编号字段,并尝试再次。
 * 这将重复,直到<tt> FileHandler </tt>找到当前未使用的文件名。如果存在冲突并且没有指定"％u"字段,则将在点后面的文件名末尾添加。 (这将在任何自动添加的世代号码之后。)。
 * <p>
 *  因此,如果三个进程都试图记录到fred％u。％g.txt,则它们可能最终使用fred0.0.txt,fred1.0.txt,fred2.0.txt作为它们的旋转序列中的第一个文件。
 * <p>
 *  请注意,使用唯一ID以避免冲突只能在使用本地磁盘文件系统时可靠地工作。
 * 
 * 
 * @since 1.4
 */

public class FileHandler extends StreamHandler {
    private MeteredStream meter;
    private boolean append;
    private int limit;       // zero => no limit.
    private int count;
    private String pattern;
    private String lockFileName;
    private FileChannel lockFileChannel;
    private File files[];
    private static final int MAX_LOCKS = 100;
    private static final Set<String> locks = new HashSet<>();

    /**
     * A metered stream is a subclass of OutputStream that
     * (a) forwards all its output to a target stream
     * (b) keeps track of how many bytes have been written
     * <p>
     *  计量流是OutputStream的子类,(a)将其所有输出转发到目标流(b)跟踪已经写入了多少字节
     * 
     */
    private class MeteredStream extends OutputStream {
        final OutputStream out;
        int written;

        MeteredStream(OutputStream out, int written) {
            this.out = out;
            this.written = written;
        }

        @Override
        public void write(int b) throws IOException {
            out.write(b);
            written++;
        }

        @Override
        public void write(byte buff[]) throws IOException {
            out.write(buff);
            written += buff.length;
        }

        @Override
        public void write(byte buff[], int off, int len) throws IOException {
            out.write(buff,off,len);
            written += len;
        }

        @Override
        public void flush() throws IOException {
            out.flush();
        }

        @Override
        public void close() throws IOException {
            out.close();
        }
    }

    private void open(File fname, boolean append) throws IOException {
        int len = 0;
        if (append) {
            len = (int)fname.length();
        }
        FileOutputStream fout = new FileOutputStream(fname.toString(), append);
        BufferedOutputStream bout = new BufferedOutputStream(fout);
        meter = new MeteredStream(bout, len);
        setOutputStream(meter);
    }

    /**
     * Configure a FileHandler from LogManager properties and/or default values
     * as specified in the class javadoc.
     * <p>
     *  从LogManager属性和/或类javadoc中指定的默认值配置FileHandler。
     * 
     */
    private void configure() {
        LogManager manager = LogManager.getLogManager();

        String cname = getClass().getName();

        pattern = manager.getStringProperty(cname + ".pattern", "%h/java%u.log");
        limit = manager.getIntProperty(cname + ".limit", 0);
        if (limit < 0) {
            limit = 0;
        }
        count = manager.getIntProperty(cname + ".count", 1);
        if (count <= 0) {
            count = 1;
        }
        append = manager.getBooleanProperty(cname + ".append", false);
        setLevel(manager.getLevelProperty(cname + ".level", Level.ALL));
        setFilter(manager.getFilterProperty(cname + ".filter", null));
        setFormatter(manager.getFormatterProperty(cname + ".formatter", new XMLFormatter()));
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
    }


    /**
     * Construct a default <tt>FileHandler</tt>.  This will be configured
     * entirely from <tt>LogManager</tt> properties (or their default values).
     * <p>
     * <p>
     *  构造默认的<tt> FileHandler </tt>。这将完全从<tt> LogManager </tt>属性(或其默认值)进行配置。
     * <p>
     * 
     * @exception  IOException if there are IO problems opening the files.
     * @exception  SecurityException  if a security manager exists and if
     *             the caller does not have <tt>LoggingPermission("control"))</tt>.
     * @exception  NullPointerException if pattern property is an empty String.
     */
    public FileHandler() throws IOException, SecurityException {
        checkPermission();
        configure();
        openFiles();
    }

    /**
     * Initialize a <tt>FileHandler</tt> to write to the given filename.
     * <p>
     * The <tt>FileHandler</tt> is configured based on <tt>LogManager</tt>
     * properties (or their default values) except that the given pattern
     * argument is used as the filename pattern, the file limit is
     * set to no limit, and the file count is set to one.
     * <p>
     * There is no limit on the amount of data that may be written,
     * so use this with care.
     *
     * <p>
     *  初始化<tt> FileHandler </tt>以写入给定的文件名。
     * <p>
     * <tt> FileHandler </tt>是基于<tt> LogManager </tt>属性(或其默认值)配置的,除了将给定模式参数用作文件名模式,文件限制设置为无限制,并且文件计数设置为1。
     * <p>
     *  对可以写入的数据量没有限制,因此请谨慎使用。
     * 
     * 
     * @param pattern  the name of the output file
     * @exception  IOException if there are IO problems opening the files.
     * @exception  SecurityException  if a security manager exists and if
     *             the caller does not have <tt>LoggingPermission("control")</tt>.
     * @exception  IllegalArgumentException if pattern is an empty string
     */
    public FileHandler(String pattern) throws IOException, SecurityException {
        if (pattern.length() < 1 ) {
            throw new IllegalArgumentException();
        }
        checkPermission();
        configure();
        this.pattern = pattern;
        this.limit = 0;
        this.count = 1;
        openFiles();
    }

    /**
     * Initialize a <tt>FileHandler</tt> to write to the given filename,
     * with optional append.
     * <p>
     * The <tt>FileHandler</tt> is configured based on <tt>LogManager</tt>
     * properties (or their default values) except that the given pattern
     * argument is used as the filename pattern, the file limit is
     * set to no limit, the file count is set to one, and the append
     * mode is set to the given <tt>append</tt> argument.
     * <p>
     * There is no limit on the amount of data that may be written,
     * so use this with care.
     *
     * <p>
     *  初始化<tt> FileHandler </tt>以写入给定的文件名,带可选的附加。
     * <p>
     *  <tt> FileHandler </tt>是基于<tt> LogManager </tt>属性(或其默认值)配置的,除了将给定模式参数用作文件名模式,文件限制设置为无限制,文件计数设置为1,并且附加
     * 模式设置为给定的<tt> append </tt>参数。
     * <p>
     *  对可以写入的数据量没有限制,因此请谨慎使用。
     * 
     * 
     * @param pattern  the name of the output file
     * @param append  specifies append mode
     * @exception  IOException if there are IO problems opening the files.
     * @exception  SecurityException  if a security manager exists and if
     *             the caller does not have <tt>LoggingPermission("control")</tt>.
     * @exception  IllegalArgumentException if pattern is an empty string
     */
    public FileHandler(String pattern, boolean append) throws IOException,
            SecurityException {
        if (pattern.length() < 1 ) {
            throw new IllegalArgumentException();
        }
        checkPermission();
        configure();
        this.pattern = pattern;
        this.limit = 0;
        this.count = 1;
        this.append = append;
        openFiles();
    }

    /**
     * Initialize a <tt>FileHandler</tt> to write to a set of files.  When
     * (approximately) the given limit has been written to one file,
     * another file will be opened.  The output will cycle through a set
     * of count files.
     * <p>
     * The <tt>FileHandler</tt> is configured based on <tt>LogManager</tt>
     * properties (or their default values) except that the given pattern
     * argument is used as the filename pattern, the file limit is
     * set to the limit argument, and the file count is set to the
     * given count argument.
     * <p>
     * The count must be at least 1.
     *
     * <p>
     *  初始化<tt> FileHandler </tt>以写入一组文件。当(大约)给定限制已写入一个文件时,将打开另一个文件。输出将循环通过一组计数文件。
     * <p>
     *  <tt> FileHandler </tt>是基于<tt> LogManager </tt>属性(或其默认值)配置的,除了将给定模式参数用作文件名模式,文件限制设置为limit参数,并且文件计数设置为
     * 给定的count参数。
     * <p>
     *  计数必须至少为1。
     * 
     * 
     * @param pattern  the pattern for naming the output file
     * @param limit  the maximum number of bytes to write to any one file
     * @param count  the number of files to use
     * @exception  IOException if there are IO problems opening the files.
     * @exception  SecurityException  if a security manager exists and if
     *             the caller does not have <tt>LoggingPermission("control")</tt>.
     * @exception  IllegalArgumentException if {@code limit < 0}, or {@code count < 1}.
     * @exception  IllegalArgumentException if pattern is an empty string
     */
    public FileHandler(String pattern, int limit, int count)
                                        throws IOException, SecurityException {
        if (limit < 0 || count < 1 || pattern.length() < 1) {
            throw new IllegalArgumentException();
        }
        checkPermission();
        configure();
        this.pattern = pattern;
        this.limit = limit;
        this.count = count;
        openFiles();
    }

    /**
     * Initialize a <tt>FileHandler</tt> to write to a set of files
     * with optional append.  When (approximately) the given limit has
     * been written to one file, another file will be opened.  The
     * output will cycle through a set of count files.
     * <p>
     * The <tt>FileHandler</tt> is configured based on <tt>LogManager</tt>
     * properties (or their default values) except that the given pattern
     * argument is used as the filename pattern, the file limit is
     * set to the limit argument, and the file count is set to the
     * given count argument, and the append mode is set to the given
     * <tt>append</tt> argument.
     * <p>
     * The count must be at least 1.
     *
     * <p>
     * 初始化<tt> FileHandler </tt>以写入具有可选附加的一组文件。当(大约)给定限制已写入一个文件时,将打开另一个文件。输出将循环通过一组计数文件。
     * <p>
     *  <tt> FileHandler </tt>是基于<tt> LogManager </tt>属性(或其默认值)配置的,除了将给定模式参数用作文件名模式,文件限制设置为limit参数,并且文件计数设置为
     * 给定的count参数,并且附加模式设置为给定的<tt> append </tt>参数。
     * <p>
     *  计数必须至少为1。
     * 
     * 
     * @param pattern  the pattern for naming the output file
     * @param limit  the maximum number of bytes to write to any one file
     * @param count  the number of files to use
     * @param append  specifies append mode
     * @exception  IOException if there are IO problems opening the files.
     * @exception  SecurityException  if a security manager exists and if
     *             the caller does not have <tt>LoggingPermission("control")</tt>.
     * @exception  IllegalArgumentException if {@code limit < 0}, or {@code count < 1}.
     * @exception  IllegalArgumentException if pattern is an empty string
     *
     */
    public FileHandler(String pattern, int limit, int count, boolean append)
                                        throws IOException, SecurityException {
        if (limit < 0 || count < 1 || pattern.length() < 1) {
            throw new IllegalArgumentException();
        }
        checkPermission();
        configure();
        this.pattern = pattern;
        this.limit = limit;
        this.count = count;
        this.append = append;
        openFiles();
    }

    private  boolean isParentWritable(Path path) {
        Path parent = path.getParent();
        if (parent == null) {
            parent = path.toAbsolutePath().getParent();
        }
        return parent != null && Files.isWritable(parent);
    }

    /**
     * Open the set of output files, based on the configured
     * instance variables.
     * <p>
     *  根据配置的实例变量打开输出文件集。
     * 
     */
    private void openFiles() throws IOException {
        LogManager manager = LogManager.getLogManager();
        manager.checkPermission();
        if (count < 1) {
           throw new IllegalArgumentException("file count = " + count);
        }
        if (limit < 0) {
            limit = 0;
        }

        // We register our own ErrorManager during initialization
        // so we can record exceptions.
        InitializationErrorManager em = new InitializationErrorManager();
        setErrorManager(em);

        // Create a lock file.  This grants us exclusive access
        // to our set of output files, as long as we are alive.
        int unique = -1;
        for (;;) {
            unique++;
            if (unique > MAX_LOCKS) {
                throw new IOException("Couldn't get lock for " + pattern);
            }
            // Generate a lock file name from the "unique" int.
            lockFileName = generate(pattern, 0, unique).toString() + ".lck";
            // Now try to lock that filename.
            // Because some systems (e.g., Solaris) can only do file locks
            // between processes (and not within a process), we first check
            // if we ourself already have the file locked.
            synchronized(locks) {
                if (locks.contains(lockFileName)) {
                    // We already own this lock, for a different FileHandler
                    // object.  Try again.
                    continue;
                }

                final Path lockFilePath = Paths.get(lockFileName);
                FileChannel channel = null;
                int retries = -1;
                boolean fileCreated = false;
                while (channel == null && retries++ < 1) {
                    try {
                        channel = FileChannel.open(lockFilePath,
                                CREATE_NEW, WRITE);
                        fileCreated = true;
                    } catch (FileAlreadyExistsException ix) {
                        // This may be a zombie file left over by a previous
                        // execution. Reuse it - but only if we can actually
                        // write to its directory.
                        // Note that this is a situation that may happen,
                        // but not too frequently.
                        if (Files.isRegularFile(lockFilePath, LinkOption.NOFOLLOW_LINKS)
                            && isParentWritable(lockFilePath)) {
                            try {
                                channel = FileChannel.open(lockFilePath,
                                    WRITE, APPEND);
                            } catch (NoSuchFileException x) {
                                // Race condition - retry once, and if that
                                // fails again just try the next name in
                                // the sequence.
                                continue;
                            } catch(IOException x) {
                                // the file may not be writable for us.
                                // try the next name in the sequence
                                break;
                            }
                        } else {
                            // at this point channel should still be null.
                            // break and try the next name in the sequence.
                            break;
                        }
                    }
                }

                if (channel == null) continue; // try the next name;
                lockFileChannel = channel;

                boolean available;
                try {
                    available = lockFileChannel.tryLock() != null;
                    // We got the lock OK.
                    // At this point we could call File.deleteOnExit().
                    // However, this could have undesirable side effects
                    // as indicated by JDK-4872014. So we will instead
                    // rely on the fact that close() will remove the lock
                    // file and that whoever is creating FileHandlers should
                    // be responsible for closing them.
                } catch (IOException ix) {
                    // We got an IOException while trying to get the lock.
                    // This normally indicates that locking is not supported
                    // on the target directory.  We have to proceed without
                    // getting a lock.   Drop through, but only if we did
                    // create the file...
                    available = fileCreated;
                } catch (OverlappingFileLockException x) {
                    // someone already locked this file in this VM, through
                    // some other channel - that is - using something else
                    // than new FileHandler(...);
                    // continue searching for an available lock.
                    available = false;
                }
                if (available) {
                    // We got the lock.  Remember it.
                    locks.add(lockFileName);
                    break;
                }

                // We failed to get the lock.  Try next file.
                lockFileChannel.close();
            }
        }

        files = new File[count];
        for (int i = 0; i < count; i++) {
            files[i] = generate(pattern, i, unique);
        }

        // Create the initial log file.
        if (append) {
            open(files[0], true);
        } else {
            rotate();
        }

        // Did we detect any exceptions during initialization?
        Exception ex = em.lastException;
        if (ex != null) {
            if (ex instanceof IOException) {
                throw (IOException) ex;
            } else if (ex instanceof SecurityException) {
                throw (SecurityException) ex;
            } else {
                throw new IOException("Exception: " + ex);
            }
        }

        // Install the normal default ErrorManager.
        setErrorManager(new ErrorManager());
    }

    /**
     * Generate a file based on a user-supplied pattern, generation number,
     * and an integer uniqueness suffix
     * <p>
     *  基于用户提供的模式,世代号和整数唯一性后缀生成文件
     * 
     * 
     * @param pattern the pattern for naming the output file
     * @param generation the generation number to distinguish rotated logs
     * @param unique a unique number to resolve conflicts
     * @return the generated File
     * @throws IOException
     */
    private File generate(String pattern, int generation, int unique)
            throws IOException {
        File file = null;
        String word = "";
        int ix = 0;
        boolean sawg = false;
        boolean sawu = false;
        while (ix < pattern.length()) {
            char ch = pattern.charAt(ix);
            ix++;
            char ch2 = 0;
            if (ix < pattern.length()) {
                ch2 = Character.toLowerCase(pattern.charAt(ix));
            }
            if (ch == '/') {
                if (file == null) {
                    file = new File(word);
                } else {
                    file = new File(file, word);
                }
                word = "";
                continue;
            } else  if (ch == '%') {
                if (ch2 == 't') {
                    String tmpDir = System.getProperty("java.io.tmpdir");
                    if (tmpDir == null) {
                        tmpDir = System.getProperty("user.home");
                    }
                    file = new File(tmpDir);
                    ix++;
                    word = "";
                    continue;
                } else if (ch2 == 'h') {
                    file = new File(System.getProperty("user.home"));
                    if (isSetUID()) {
                        // Ok, we are in a set UID program.  For safety's sake
                        // we disallow attempts to open files relative to %h.
                        throw new IOException("can't use %h in set UID program");
                    }
                    ix++;
                    word = "";
                    continue;
                } else if (ch2 == 'g') {
                    word = word + generation;
                    sawg = true;
                    ix++;
                    continue;
                } else if (ch2 == 'u') {
                    word = word + unique;
                    sawu = true;
                    ix++;
                    continue;
                } else if (ch2 == '%') {
                    word = word + "%";
                    ix++;
                    continue;
                }
            }
            word = word + ch;
        }
        if (count > 1 && !sawg) {
            word = word + "." + generation;
        }
        if (unique > 0 && !sawu) {
            word = word + "." + unique;
        }
        if (word.length() > 0) {
            if (file == null) {
                file = new File(word);
            } else {
                file = new File(file, word);
            }
        }
        return file;
    }

    /**
     * Rotate the set of output files
     * <p>
     *  旋转输出文件集
     * 
     */
    private synchronized void rotate() {
        Level oldLevel = getLevel();
        setLevel(Level.OFF);

        super.close();
        for (int i = count-2; i >= 0; i--) {
            File f1 = files[i];
            File f2 = files[i+1];
            if (f1.exists()) {
                if (f2.exists()) {
                    f2.delete();
                }
                f1.renameTo(f2);
            }
        }
        try {
            open(files[0], false);
        } catch (IOException ix) {
            // We don't want to throw an exception here, but we
            // report the exception to any registered ErrorManager.
            reportError(null, ix, ErrorManager.OPEN_FAILURE);

        }
        setLevel(oldLevel);
    }

    /**
     * Format and publish a <tt>LogRecord</tt>.
     *
     * <p>
     *  格式化并发布<tt> LogRecord </tt>。
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
        if (limit > 0 && meter.written >= limit) {
            // We performed access checks in the "init" method to make sure
            // we are only initialized from trusted code.  So we assume
            // it is OK to write the target files, even if we are
            // currently being called from untrusted code.
            // So it is safe to raise privilege here.
            AccessController.doPrivileged(new PrivilegedAction<Object>() {
                @Override
                public Object run() {
                    rotate();
                    return null;
                }
            });
        }
    }

    /**
     * Close all the files.
     *
     * <p>
     *  关闭所有文件。
     * 
     * 
     * @exception  SecurityException  if a security manager exists and if
     *             the caller does not have <tt>LoggingPermission("control")</tt>.
     */
    @Override
    public synchronized void close() throws SecurityException {
        super.close();
        // Unlock any lock file.
        if (lockFileName == null) {
            return;
        }
        try {
            // Close the lock file channel (which also will free any locks)
            lockFileChannel.close();
        } catch (Exception ex) {
            // Problems closing the stream.  Punt.
        }
        synchronized(locks) {
            locks.remove(lockFileName);
        }
        new File(lockFileName).delete();
        lockFileName = null;
        lockFileChannel = null;
    }

    private static class InitializationErrorManager extends ErrorManager {
        Exception lastException;
        @Override
        public void error(String msg, Exception ex, int code) {
            lastException = ex;
        }
    }

    /**
     * check if we are in a set UID program.
     * <p>
     *  检查我们是否在一个设置的UID程序。
     */
    private static native boolean isSetUID();
}
