/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1994, 2008, Oracle and/or its affiliates. All rights reserved.
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


/**
 * Signals that an attempt to open the file denoted by a specified pathname
 * has failed.
 *
 * <p> This exception will be thrown by the {@link FileInputStream}, {@link
 * FileOutputStream}, and {@link RandomAccessFile} constructors when a file
 * with the specified pathname does not exist.  It will also be thrown by these
 * constructors if the file does exist but for some reason is inaccessible, for
 * example when an attempt is made to open a read-only file for writing.
 *
 * <p>
 *  表示尝试打开由指定路径名指示的文件失败。
 * 
 *  <p>当具有指定路径名的文件不存在时,{@link FileInputStream},{@link FileOutputStream}和{@link RandomAccessFile}构造函数将抛出此
 * 异常。
 * 如果文件确实存在,但由于某些原因是不可访问的,例如当试图打开只读文件进行写入时,这些构造函数也会抛出它。
 * 
 * 
 * @author  unascribed
 * @since   JDK1.0
 */

public class FileNotFoundException extends IOException {
    private static final long serialVersionUID = -897856973823710492L;

    /**
     * Constructs a <code>FileNotFoundException</code> with
     * <code>null</code> as its error detail message.
     * <p>
     *  构造具有<code> null </code>的<code> FileNotFoundException </code>作为其错误详细信息。
     * 
     */
    public FileNotFoundException() {
        super();
    }

    /**
     * Constructs a <code>FileNotFoundException</code> with the
     * specified detail message. The string <code>s</code> can be
     * retrieved later by the
     * <code>{@link java.lang.Throwable#getMessage}</code>
     * method of class <code>java.lang.Throwable</code>.
     *
     * <p>
     *  使用指定的详细消息构造<code> FileNotFoundException </code>。
     * 稍后可以通过<code> java.lang.Throwable </code>类的<code> {@ link java.lang.Throwable#getMessage} </code>方法检索字
     * 符串<code> s </code>。
     *  使用指定的详细消息构造<code> FileNotFoundException </code>。
     * 
     * 
     * @param   s   the detail message.
     */
    public FileNotFoundException(String s) {
        super(s);
    }

    /**
     * Constructs a <code>FileNotFoundException</code> with a detail message
     * consisting of the given pathname string followed by the given reason
     * string.  If the <code>reason</code> argument is <code>null</code> then
     * it will be omitted.  This private constructor is invoked only by native
     * I/O methods.
     *
     * <p>
     * 
     * @since 1.2
     */
    private FileNotFoundException(String path, String reason) {
        super(path + ((reason == null)
                      ? ""
                      : " (" + reason + ")"));
    }

}
