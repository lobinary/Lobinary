/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
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
 * Signals that a malformed string in
 * <a href="DataInput.html#modified-utf-8">modified UTF-8</a>
 * format has been read in a data
 * input stream or by any class that implements the data input
 * interface.
 * See the
 * <a href="DataInput.html#modified-utf-8"><code>DataInput</code></a>
 * class description for the format in
 * which modified UTF-8 strings are read and written.
 *
 * <p>
 *  表示在数据输入流或任何实现数据输入界面的类中读取了<a href="DataInput.html#modified-utf-8">修改的UTF-8 </a>格式的格式错误的字符串。
 * 有关读取和写入修改的UTF-8字符串的格式,请参见<a href="DataInput.html#modified-utf-8"> <code> DataInput </code> </a>类描述。
 * 
 * 
 * @author  Frank Yellin
 * @see     java.io.DataInput
 * @see     java.io.DataInputStream#readUTF(java.io.DataInput)
 * @see     java.io.IOException
 * @since   JDK1.0
 */
public
class UTFDataFormatException extends IOException {
    private static final long serialVersionUID = 420743449228280612L;

    /**
     * Constructs a <code>UTFDataFormatException</code> with
     * <code>null</code> as its error detail message.
     * <p>
     *  构造具有<code> null </code>的<code> UTFDataFormatException </code>作为其错误详细信息。
     * 
     */
    public UTFDataFormatException() {
        super();
    }

    /**
     * Constructs a <code>UTFDataFormatException</code> with the
     * specified detail message. The string <code>s</code> can be
     * retrieved later by the
     * <code>{@link java.lang.Throwable#getMessage}</code>
     * method of class <code>java.lang.Throwable</code>.
     *
     * <p>
     *  使用指定的详细消息构造一个<code> UTFDataFormatException </code>。
     * 稍后可以通过<code> java.lang.Throwable </code>类的<code> {@ link java.lang.Throwable#getMessage} </code>方法检索字
     * 符串<code> s </code>。
     * 
     * @param   s   the detail message.
     */
    public UTFDataFormatException(String s) {
        super(s);
    }
}
