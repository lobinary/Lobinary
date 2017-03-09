/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.lang.annotation.Native;

/**
 * Package-private abstract class for the local filesystem abstraction.
 * <p>
 *  用于本地文件系统抽象的Package-private抽象类。
 * 
 */

abstract class FileSystem {

    /* -- Normalization and construction -- */

    /**
     * Return the local filesystem's name-separator character.
     * <p>
     *  返回本地文件系统的名称分隔符。
     * 
     */
    public abstract char getSeparator();

    /**
     * Return the local filesystem's path-separator character.
     * <p>
     *  返回本地文件系统的路径分隔符字符。
     * 
     */
    public abstract char getPathSeparator();

    /**
     * Convert the given pathname string to normal form.  If the string is
     * already in normal form then it is simply returned.
     * <p>
     *  将给定的路径名​​字符串转换为正常形式。如果字符串已经是正常形式,那么它被简单地返回。
     * 
     */
    public abstract String normalize(String path);

    /**
     * Compute the length of this pathname string's prefix.  The pathname
     * string must be in normal form.
     * <p>
     *  计算此路径名字符串的前缀的长度。路径名字符串必须为正常形式。
     * 
     */
    public abstract int prefixLength(String path);

    /**
     * Resolve the child pathname string against the parent.
     * Both strings must be in normal form, and the result
     * will be in normal form.
     * <p>
     *  根据父项解析子路径名字符串。两个字符串必须是正常形式,结果将是正常形式。
     * 
     */
    public abstract String resolve(String parent, String child);

    /**
     * Return the parent pathname string to be used when the parent-directory
     * argument in one of the two-argument File constructors is the empty
     * pathname.
     * <p>
     *  返回当两参数File构造函数之一中的parent-directory参数为空路径名时要使用的父路径名字符串。
     * 
     */
    public abstract String getDefaultParent();

    /**
     * Post-process the given URI path string if necessary.  This is used on
     * win32, e.g., to transform "/c:/foo" into "c:/foo".  The path string
     * still has slash separators; code in the File class will translate them
     * after this method returns.
     * <p>
     *  如果需要,后处理给定的URI路径字符串。这在win32上使用,例如,将"/ c：/ foo"转换为"c：/ foo"。路径字符串仍然具有斜杠分隔符; File类中的代码将在此方法返回后翻译它们。
     * 
     */
    public abstract String fromURIPath(String path);


    /* -- Path operations -- */

    /**
     * Tell whether or not the given abstract pathname is absolute.
     * <p>
     *  告诉给定的抽象路径名是否是绝对的。
     * 
     */
    public abstract boolean isAbsolute(File f);

    /**
     * Resolve the given abstract pathname into absolute form.  Invoked by the
     * getAbsolutePath and getCanonicalPath methods in the File class.
     * <p>
     *  将给定的抽象路径名解析为绝对形式。由File类中的getAbsolutePath和getCanonicalPath方法调用。
     * 
     */
    public abstract String resolve(File f);

    public abstract String canonicalize(String path) throws IOException;


    /* -- Attribute accessors -- */

    /* Constants for simple boolean attributes */
    @Native public static final int BA_EXISTS    = 0x01;
    @Native public static final int BA_REGULAR   = 0x02;
    @Native public static final int BA_DIRECTORY = 0x04;
    @Native public static final int BA_HIDDEN    = 0x08;

    /**
     * Return the simple boolean attributes for the file or directory denoted
     * by the given abstract pathname, or zero if it does not exist or some
     * other I/O error occurs.
     * <p>
     *  返回由给定抽象路径名表示的文件或目录的简单布尔属性,如果不存在或发生其他I / O错误,则返回零。
     * 
     */
    public abstract int getBooleanAttributes(File f);

    @Native public static final int ACCESS_READ    = 0x04;
    @Native public static final int ACCESS_WRITE   = 0x02;
    @Native public static final int ACCESS_EXECUTE = 0x01;

    /**
     * Check whether the file or directory denoted by the given abstract
     * pathname may be accessed by this process.  The second argument specifies
     * which access, ACCESS_READ, ACCESS_WRITE or ACCESS_EXECUTE, to check.
     * Return false if access is denied or an I/O error occurs
     * <p>
     * 检查由给定抽象路径名指示的文件或目录是否可以被此进程访问。第二个参数指定要检查哪个访问,ACCESS_READ,ACCESS_WRITE或ACCESS_EXECUTE。
     * 如果访问被拒绝或发生I / O错误,则返回false。
     * 
     */
    public abstract boolean checkAccess(File f, int access);
    /**
     * Set on or off the access permission (to owner only or to all) to the file
     * or directory denoted by the given abstract pathname, based on the parameters
     * enable, access and oweronly.
     * <p>
     *  根据参数enable,access和oweronly,设置打开或关闭由给定抽象路径名表示的文件或目录的访问权限(仅属于所有者或全部)。
     * 
     */
    public abstract boolean setPermission(File f, int access, boolean enable, boolean owneronly);

    /**
     * Return the time at which the file or directory denoted by the given
     * abstract pathname was last modified, or zero if it does not exist or
     * some other I/O error occurs.
     * <p>
     *  返回由给定抽象路径名表示的文件或目录的上次修改时间,如果不存在或发生其他I / O错误,则返回0。
     * 
     */
    public abstract long getLastModifiedTime(File f);

    /**
     * Return the length in bytes of the file denoted by the given abstract
     * pathname, or zero if it does not exist, is a directory, or some other
     * I/O error occurs.
     * <p>
     *  返回由给定抽象路径名表示的文件的长度(以字节为单位),如果不存在则返回0,是目录,或发生其他I / O错误。
     * 
     */
    public abstract long getLength(File f);


    /* -- File operations -- */

    /**
     * Create a new empty file with the given pathname.  Return
     * <code>true</code> if the file was created and <code>false</code> if a
     * file or directory with the given pathname already exists.  Throw an
     * IOException if an I/O error occurs.
     * <p>
     *  使用给定的路径名​​创建一个新的空文件。如果创建了文件,则返回<code> true </code>,如果具有给定路径名的文件或目录已存在,则返回<code> false </code>。
     * 如果发生I / O错误,则抛出IOException。
     * 
     */
    public abstract boolean createFileExclusively(String pathname)
        throws IOException;

    /**
     * Delete the file or directory denoted by the given abstract pathname,
     * returning <code>true</code> if and only if the operation succeeds.
     * <p>
     *  删除由给定抽象路径名指示的文件或目录,如果且仅当操作成功时返回<code> true </code>。
     * 
     */
    public abstract boolean delete(File f);

    /**
     * List the elements of the directory denoted by the given abstract
     * pathname.  Return an array of strings naming the elements of the
     * directory if successful; otherwise, return <code>null</code>.
     * <p>
     *  列出由给定抽象路径名指示的目录的元素。返回一个字符串数组,如果成功则命名目录的元素;否则返回<code> null </code>。
     * 
     */
    public abstract String[] list(File f);

    /**
     * Create a new directory denoted by the given abstract pathname,
     * returning <code>true</code> if and only if the operation succeeds.
     * <p>
     *  创建一个由给定抽象路径名表示的新目录,如果且仅当操作成功时返回<code> true </code>。
     * 
     */
    public abstract boolean createDirectory(File f);

    /**
     * Rename the file or directory denoted by the first abstract pathname to
     * the second abstract pathname, returning <code>true</code> if and only if
     * the operation succeeds.
     * <p>
     * 将由第一个抽象路径名表示的文件或目录重命名为第二个抽象路径名,如果且仅当操作成功时返回<code> true </code>。
     * 
     */
    public abstract boolean rename(File f1, File f2);

    /**
     * Set the last-modified time of the file or directory denoted by the
     * given abstract pathname, returning <code>true</code> if and only if the
     * operation succeeds.
     * <p>
     *  设置由给定抽象路径名表示的文件或目录的最后修改时间,如果且仅当操作成功时返回<code> true </code>。
     * 
     */
    public abstract boolean setLastModifiedTime(File f, long time);

    /**
     * Mark the file or directory denoted by the given abstract pathname as
     * read-only, returning <code>true</code> if and only if the operation
     * succeeds.
     * <p>
     *  将由给定抽象路径名表示的文件或目录标记为只读,如果且仅当操作成功时返回<code> true </code>。
     * 
     */
    public abstract boolean setReadOnly(File f);


    /* -- Filesystem interface -- */

    /**
     * List the available filesystem roots.
     * <p>
     *  列出可用的文件系统根。
     * 
     */
    public abstract File[] listRoots();

    /* -- Disk usage -- */
    @Native public static final int SPACE_TOTAL  = 0;
    @Native public static final int SPACE_FREE   = 1;
    @Native public static final int SPACE_USABLE = 2;

    public abstract long getSpace(File f, int t);

    /* -- Basic infrastructure -- */

    /**
     * Compare two abstract pathnames lexicographically.
     * <p>
     *  按字典顺序比较两个抽象路径名。
     * 
     */
    public abstract int compare(File f1, File f2);

    /**
     * Compute the hash code of an abstract pathname.
     * <p>
     *  计算抽象路径名的哈希码。
     */
    public abstract int hashCode(File f);

    // Flags for enabling/disabling performance optimizations for file
    // name canonicalization
    static boolean useCanonCaches      = true;
    static boolean useCanonPrefixCache = true;

    private static boolean getBooleanProperty(String prop, boolean defaultVal) {
        String val = System.getProperty(prop);
        if (val == null) return defaultVal;
        if (val.equalsIgnoreCase("true")) {
            return true;
        } else {
            return false;
        }
    }

    static {
        useCanonCaches      = getBooleanProperty("sun.io.useCanonCaches",
                                                 useCanonCaches);
        useCanonPrefixCache = getBooleanProperty("sun.io.useCanonPrefixCache",
                                                 useCanonPrefixCache);
    }
}
