/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.security;

import java.io.IOException;
import java.io.EOFException;
import java.io.OutputStream;
import java.io.FilterOutputStream;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

/**
 * A transparent stream that updates the associated message digest using
 * the bits going through the stream.
 *
 * <p>To complete the message digest computation, call one of the
 * {@code digest} methods on the associated message
 * digest after your calls to one of this digest output stream's
 * {@link #write(int) write} methods.
 *
 * <p>It is possible to turn this stream on or off (see
 * {@link #on(boolean) on}). When it is on, a call to one of the
 * {@code write} methods results in
 * an update on the message digest.  But when it is off, the message
 * digest is not updated. The default is for the stream to be on.
 *
 * <p>
 *  透明流,使用通过流的位更新相关的消息摘要。
 * 
 *  <p>要完成消息摘要计算,请在调用此摘要输出流的{@link #write(int)write}方法之后,调用相关消息摘要中的{@code digest}方法之一。
 * 
 *  <p>可以打开或关闭此流(请参阅{@link #on(boolean)on})。当它打开时,调用其中一个{@code write}方法将导致消息摘要的更新。但是当它关​​闭时,消息摘要不会更新。
 * 默认值为流开启。
 * 
 * 
 * @see MessageDigest
 * @see DigestInputStream
 *
 * @author Benjamin Renaud
 */
public class DigestOutputStream extends FilterOutputStream {

    private boolean on = true;

    /**
     * The message digest associated with this stream.
     * <p>
     *  与此流关联的消息摘要。
     * 
     */
    protected MessageDigest digest;

    /**
     * Creates a digest output stream, using the specified output stream
     * and message digest.
     *
     * <p>
     *  使用指定的输出流和消息摘要创建摘要输出流。
     * 
     * 
     * @param stream the output stream.
     *
     * @param digest the message digest to associate with this stream.
     */
    public DigestOutputStream(OutputStream stream, MessageDigest digest) {
        super(stream);
        setMessageDigest(digest);
    }

    /**
     * Returns the message digest associated with this stream.
     *
     * <p>
     *  返回与此流相关联的消息摘要。
     * 
     * 
     * @return the message digest associated with this stream.
     * @see #setMessageDigest(java.security.MessageDigest)
     */
    public MessageDigest getMessageDigest() {
        return digest;
    }

    /**
     * Associates the specified message digest with this stream.
     *
     * <p>
     *  将指定的消息摘要与此流相关联。
     * 
     * 
     * @param digest the message digest to be associated with this stream.
     * @see #getMessageDigest()
     */
    public void setMessageDigest(MessageDigest digest) {
        this.digest = digest;
    }

    /**
     * Updates the message digest (if the digest function is on) using
     * the specified byte, and in any case writes the byte
     * to the output stream. That is, if the digest function is on
     * (see {@link #on(boolean) on}), this method calls
     * {@code update} on the message digest associated with this
     * stream, passing it the byte {@code b}. This method then
     * writes the byte to the output stream, blocking until the byte
     * is actually written.
     *
     * <p>
     *  使用指定的字节更新消息摘要(如果摘要函数打开),并且在任何情况下将字节写入输出流。
     * 也就是说,如果摘要函数打开(参见{@link #on(boolean)on}),此方法调用与此流相关联的消息摘要上的{@code update},并传递字节{@code b}。
     * 然后,该方法将字节写入输出流,阻塞直到字节实际写入。
     * 
     * 
     * @param b the byte to be used for updating and writing to the
     * output stream.
     *
     * @exception IOException if an I/O error occurs.
     *
     * @see MessageDigest#update(byte)
     */
    public void write(int b) throws IOException {
        out.write(b);
        if (on) {
            digest.update((byte)b);
        }
    }

    /**
     * Updates the message digest (if the digest function is on) using
     * the specified subarray, and in any case writes the subarray to
     * the output stream. That is, if the digest function is on (see
     * {@link #on(boolean) on}), this method calls {@code update}
     * on the message digest associated with this stream, passing it
     * the subarray specifications. This method then writes the subarray
     * bytes to the output stream, blocking until the bytes are actually
     * written.
     *
     * <p>
     * 使用指定的子数组更新消息摘要(如果摘要函数打开),并且在任何情况下将子数组写入输出流。
     * 也就是说,如果摘要函数打开(参见{@link #on(boolean)on}),此方法将调用与此流相关联的消息摘要上的{@code update},并将其传递给子阵列规范。
     * 然后,该方法将子阵列字节写入输出流,阻塞直到字节实际写入。
     * 
     * 
     * @param b the array containing the subarray to be used for updating
     * and writing to the output stream.
     *
     * @param off the offset into {@code b} of the first byte to
     * be updated and written.
     *
     * @param len the number of bytes of data to be updated and written
     * from {@code b}, starting at offset {@code off}.
     *
     * @exception IOException if an I/O error occurs.
     *
     * @see MessageDigest#update(byte[], int, int)
     */
    public void write(byte[] b, int off, int len) throws IOException {
        out.write(b, off, len);
        if (on) {
            digest.update(b, off, len);
        }
    }

    /**
     * Turns the digest function on or off. The default is on.  When
     * it is on, a call to one of the {@code write} methods results in an
     * update on the message digest.  But when it is off, the message
     * digest is not updated.
     *
     * <p>
     *  打开或关闭摘要功能。默认值为on。当它打开时,调用其中一个{@code write}方法将导致消息摘要的更新。但是当它关​​闭时,消息摘要不会更新。
     * 
     * 
     * @param on true to turn the digest function on, false to turn it
     * off.
     */
    public void on(boolean on) {
        this.on = on;
    }

    /**
     * Prints a string representation of this digest output stream and
     * its associated message digest object.
     * <p>
     *  打印此摘要输出流及其关联的消息摘要对象的字符串表示形式。
     */
     public String toString() {
         return "[Digest Output Stream] " + digest.toString();
     }
}
