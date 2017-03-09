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
import java.io.InputStream;
import java.io.FilterInputStream;
import java.io.PrintStream;
import java.io.ByteArrayInputStream;

/**
 * A transparent stream that updates the associated message digest using
 * the bits going through the stream.
 *
 * <p>To complete the message digest computation, call one of the
 * {@code digest} methods on the associated message
 * digest after your calls to one of this digest input stream's
 * {@link #read() read} methods.
 *
 * <p>It is possible to turn this stream on or off (see
 * {@link #on(boolean) on}). When it is on, a call to one of the
 * {@code read} methods
 * results in an update on the message digest.  But when it is off,
 * the message digest is not updated. The default is for the stream
 * to be on.
 *
 * <p>Note that digest objects can compute only one digest (see
 * {@link MessageDigest}),
 * so that in order to compute intermediate digests, a caller should
 * retain a handle onto the digest object, and clone it for each
 * digest to be computed, leaving the orginal digest untouched.
 *
 * <p>
 *  透明流,使用通过流的位更新相关的消息摘要。
 * 
 *  <p>要完成消息摘要计算,请在调用此摘要输入流的{@link #read()read}方法之后,调用相关消息摘要中的{@code digest}方法之一。
 * 
 *  <p>可以打开或关闭此流(请参阅{@link #on(boolean)on})。当它打开时,调用其中一个{@code read}方法将导致消息摘要的更新。但是当它关​​闭时,消息摘要不会更新。
 * 默认值为流开启。
 * 
 *  <p>请注意,digest对象只能计算一个摘要(参见{@link MessageDigest}),因此为了计算中间摘要,调用者应该保留对摘要对象的句柄,并对每个摘要进行克隆,保持原始消化不变。
 * 
 * 
 * @see MessageDigest
 *
 * @see DigestOutputStream
 *
 * @author Benjamin Renaud
 */

public class DigestInputStream extends FilterInputStream {

    /* NOTE: This should be made a generic UpdaterInputStream */

    /* Are we on or off? */
    private boolean on = true;

    /**
     * The message digest associated with this stream.
     * <p>
     *  与此流关联的消息摘要。
     * 
     */
    protected MessageDigest digest;

    /**
     * Creates a digest input stream, using the specified input stream
     * and message digest.
     *
     * <p>
     *  使用指定的输入流和消息摘要创建摘要输入流。
     * 
     * 
     * @param stream the input stream.
     *
     * @param digest the message digest to associate with this stream.
     */
    public DigestInputStream(InputStream stream, MessageDigest digest) {
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
     * Reads a byte, and updates the message digest (if the digest
     * function is on).  That is, this method reads a byte from the
     * input stream, blocking until the byte is actually read. If the
     * digest function is on (see {@link #on(boolean) on}), this method
     * will then call {@code update} on the message digest associated
     * with this stream, passing it the byte read.
     *
     * <p>
     * 读取一个字节,并更新消息摘要(如果摘要函数打开)。也就是说,该方法从输入流读取一个字节,阻塞直到该字节被实际读取。
     * 如果摘要函数打开(参见{@link #on(boolean)on}),此方法将调用与此流相关联的消息摘要上的{@code update},将其读取的字节传递给它。
     * 
     * 
     * @return the byte read.
     *
     * @exception IOException if an I/O error occurs.
     *
     * @see MessageDigest#update(byte)
     */
    public int read() throws IOException {
        int ch = in.read();
        if (on && ch != -1) {
            digest.update((byte)ch);
        }
        return ch;
    }

    /**
     * Reads into a byte array, and updates the message digest (if the
     * digest function is on).  That is, this method reads up to
     * {@code len} bytes from the input stream into the array
     * {@code b}, starting at offset {@code off}. This method
     * blocks until the data is actually
     * read. If the digest function is on (see
     * {@link #on(boolean) on}), this method will then call {@code update}
     * on the message digest associated with this stream, passing it
     * the data.
     *
     * <p>
     *  读取字节数组,并更新消息摘要(如果摘要函数打开)。也就是说,这个方法从输入流读取到{@code len}字节到数组{@code b},从offset {@code off}开始。
     * 此方法阻塞,直到数据实际读取。如果摘要函数打开(参见{@link #on(boolean)on}),此方法将调用与此流相关联的消息摘要上的{@code update},并传递数据。
     * 
     * 
     * @param b the array into which the data is read.
     *
     * @param off the starting offset into {@code b} of where the
     * data should be placed.
     *
     * @param len the maximum number of bytes to be read from the input
     * stream into b, starting at offset {@code off}.
     *
     * @return  the actual number of bytes read. This is less than
     * {@code len} if the end of the stream is reached prior to
     * reading {@code len} bytes. -1 is returned if no bytes were
     * read because the end of the stream had already been reached when
     * the call was made.
     *
     * @exception IOException if an I/O error occurs.
     *
     * @see MessageDigest#update(byte[], int, int)
     */
    public int read(byte[] b, int off, int len) throws IOException {
        int result = in.read(b, off, len);
        if (on && result != -1) {
            digest.update(b, off, result);
        }
        return result;
    }

    /**
     * Turns the digest function on or off. The default is on.  When
     * it is on, a call to one of the {@code read} methods results in an
     * update on the message digest.  But when it is off, the message
     * digest is not updated.
     *
     * <p>
     *  打开或关闭摘要功能。默认值为on。当它打开时,调用其中一个{@code read}方法将导致消息摘要的更新。但是当它关​​闭时,消息摘要不会更新。
     * 
     * 
     * @param on true to turn the digest function on, false to turn
     * it off.
     */
    public void on(boolean on) {
        this.on = on;
    }

    /**
     * Prints a string representation of this digest input stream and
     * its associated message digest object.
     * <p>
     *  打印此摘要输入流及其关联的消息摘要对象的字符串表示。
     */
     public String toString() {
         return "[Digest Input Stream] " + digest.toString();
     }
}
