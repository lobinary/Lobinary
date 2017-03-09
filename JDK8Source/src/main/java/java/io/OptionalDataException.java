/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2005, Oracle and/or its affiliates. All rights reserved.
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
 * Exception indicating the failure of an object read operation due to
 * unread primitive data, or the end of data belonging to a serialized
 * object in the stream.  This exception may be thrown in two cases:
 *
 * <ul>
 *   <li>An attempt was made to read an object when the next element in the
 *       stream is primitive data.  In this case, the OptionalDataException's
 *       length field is set to the number of bytes of primitive data
 *       immediately readable from the stream, and the eof field is set to
 *       false.
 *
 *   <li>An attempt was made to read past the end of data consumable by a
 *       class-defined readObject or readExternal method.  In this case, the
 *       OptionalDataException's eof field is set to true, and the length field
 *       is set to 0.
 * </ul>
 *
 * <p>
 *  指示由于未读原始数据导致的对象读取操作失败的异常,或者属于流中序列化对象的数据的结束。此异常可能会在两种情况下抛出：
 * 
 * <ul>
 *  <li>尝试在流中的下一个元素是原始数据时读取对象。
 * 在这种情况下,OptionalDataException的length字段设置为可从流中立即读取的基本数据的字节数,并且eof字段设置为false。
 * 
 *  <li>尝试读取类定义的readObject或readExternal方法可消耗的数据结尾。在这种情况下,OptionalDataException的eof字段设置为true,长度字段设置为0。
 * </ul>
 * 
 * 
 * @author  unascribed
 * @since   JDK1.1
 */
public class OptionalDataException extends ObjectStreamException {

    private static final long serialVersionUID = -8011121865681257820L;

    /*
     * Create an <code>OptionalDataException</code> with a length.
     * <p>
     *  创建一个带有长度的<code> OptionalDataException </code>。
     * 
     */
    OptionalDataException(int len) {
        eof = false;
        length = len;
    }

    /*
     * Create an <code>OptionalDataException</code> signifying no
     * more primitive data is available.
     * <p>
     *  创建<code> OptionalDataException </code>,表示没有更多的原始数据可用。
     * 
     */
    OptionalDataException(boolean end) {
        length = 0;
        eof = end;
    }

    /**
     * The number of bytes of primitive data available to be read
     * in the current buffer.
     *
     * <p>
     *  可用于在当前缓冲区中读取的基本数据的字节数。
     * 
     * 
     * @serial
     */
    public int length;

    /**
     * True if there is no more data in the buffered part of the stream.
     *
     * <p>
     *  如果流的缓冲部分中没有更多数据,则为true。
     * 
     * @serial
     */
    public boolean eof;
}
