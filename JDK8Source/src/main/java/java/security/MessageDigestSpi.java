/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.nio.ByteBuffer;

import sun.security.jca.JCAUtil;

/**
 * This class defines the <i>Service Provider Interface</i> (<b>SPI</b>)
 * for the {@code MessageDigest} class, which provides the functionality
 * of a message digest algorithm, such as MD5 or SHA. Message digests are
 * secure one-way hash functions that take arbitrary-sized data and output a
 * fixed-length hash value.
 *
 * <p> All the abstract methods in this class must be implemented by a
 * cryptographic service provider who wishes to supply the implementation
 * of a particular message digest algorithm.
 *
 * <p> Implementations are free to implement the Cloneable interface.
 *
 * <p>
 *  此类为{@code MessageDigest}类定义了<i>服务提供程序接口</i>(<b> SPI </b>),该类提供了消息摘要算法(如MD5或SHA)的功能。
 * 消息摘要是获取任意大小的数据并输出固定长度的散列值的安全单向散列函数。
 * 
 *  <p>此类中的所有抽象方法必须由希望提供特定消息摘要算法实现的加密服务提供者实现。
 * 
 *  <p>实现可以自由实现Cloneable接口。
 * 
 * 
 * @author Benjamin Renaud
 *
 *
 * @see MessageDigest
 */

public abstract class MessageDigestSpi {

    // for re-use in engineUpdate(ByteBuffer input)
    private byte[] tempArray;

    /**
     * Returns the digest length in bytes.
     *
     * <p>This concrete method has been added to this previously-defined
     * abstract class. (For backwards compatibility, it cannot be abstract.)
     *
     * <p>The default behavior is to return 0.
     *
     * <p>This method may be overridden by a provider to return the digest
     * length.
     *
     * <p>
     *  返回摘要长度(以字节为单位)。
     * 
     *  <p>这个具体方法已添加到此前定义的抽象类中。 (为了向后兼容,它不能是抽象的。)
     * 
     *  <p>默认行为是返回0。
     * 
     *  <p>此方法可能会被提供程序覆盖以返回摘要长度。
     * 
     * 
     * @return the digest length in bytes.
     *
     * @since 1.2
     */
    protected int engineGetDigestLength() {
        return 0;
    }

    /**
     * Updates the digest using the specified byte.
     *
     * <p>
     *  使用指定的字节更新摘要。
     * 
     * 
     * @param input the byte to use for the update.
     */
    protected abstract void engineUpdate(byte input);

    /**
     * Updates the digest using the specified array of bytes,
     * starting at the specified offset.
     *
     * <p>
     *  使用指定的字节数组更新摘要,从指定的偏移量开始。
     * 
     * 
     * @param input the array of bytes to use for the update.
     *
     * @param offset the offset to start from in the array of bytes.
     *
     * @param len the number of bytes to use, starting at
     * {@code offset}.
     */
    protected abstract void engineUpdate(byte[] input, int offset, int len);

    /**
     * Update the digest using the specified ByteBuffer. The digest is
     * updated using the {@code input.remaining()} bytes starting
     * at {@code input.position()}.
     * Upon return, the buffer's position will be equal to its limit;
     * its limit will not have changed.
     *
     * <p>
     *  使用指定的ByteBuffer更新摘要。摘要使用{@code input.remaining()}字节从{@code input.position()}开始更新。
     * 返回时,缓冲区的位置将等于其限制;其极限不会改变。
     * 
     * 
     * @param input the ByteBuffer
     * @since 1.5
     */
    protected void engineUpdate(ByteBuffer input) {
        if (input.hasRemaining() == false) {
            return;
        }
        if (input.hasArray()) {
            byte[] b = input.array();
            int ofs = input.arrayOffset();
            int pos = input.position();
            int lim = input.limit();
            engineUpdate(b, ofs + pos, lim - pos);
            input.position(lim);
        } else {
            int len = input.remaining();
            int n = JCAUtil.getTempArraySize(len);
            if ((tempArray == null) || (n > tempArray.length)) {
                tempArray = new byte[n];
            }
            while (len > 0) {
                int chunk = Math.min(len, tempArray.length);
                input.get(tempArray, 0, chunk);
                engineUpdate(tempArray, 0, chunk);
                len -= chunk;
            }
        }
    }

    /**
     * Completes the hash computation by performing final
     * operations such as padding. Once {@code engineDigest} has
     * been called, the engine should be reset (see
     * {@link #engineReset() engineReset}).
     * Resetting is the responsibility of the
     * engine implementor.
     *
     * <p>
     * 通过执行最终操作(如填充)来完成哈希计算。调用{@code engineDigest}后,应重置引擎(请参阅{@link #engineReset()engineReset})。
     * 重置是发动机实施者的责任。
     * 
     * 
     * @return the array of bytes for the resulting hash value.
     */
    protected abstract byte[] engineDigest();

    /**
     * Completes the hash computation by performing final
     * operations such as padding. Once {@code engineDigest} has
     * been called, the engine should be reset (see
     * {@link #engineReset() engineReset}).
     * Resetting is the responsibility of the
     * engine implementor.
     *
     * This method should be abstract, but we leave it concrete for
     * binary compatibility.  Knowledgeable providers should override this
     * method.
     *
     * <p>
     *  通过执行最终操作(如填充)来完成哈希计算。调用{@code engineDigest}后,应重置引擎(请参阅{@link #engineReset()engineReset})。
     * 重置是发动机实施者的责任。
     * 
     *  这个方法应该是抽象的,但我们把它具体的二进制兼容性。知识渊博的提供者应该覆盖此方法
     * 
     * 
     * @param buf the output buffer in which to store the digest
     *
     * @param offset offset to start from in the output buffer
     *
     * @param len number of bytes within buf allotted for the digest.
     * Both this default implementation and the SUN provider do not
     * return partial digests.  The presence of this parameter is solely
     * for consistency in our API's.  If the value of this parameter is less
     * than the actual digest length, the method will throw a DigestException.
     * This parameter is ignored if its value is greater than or equal to
     * the actual digest length.
     *
     * @return the length of the digest stored in the output buffer.
     *
     * @exception DigestException if an error occurs.
     *
     * @since 1.2
     */
    protected int engineDigest(byte[] buf, int offset, int len)
                                                throws DigestException {

        byte[] digest = engineDigest();
        if (len < digest.length)
                throw new DigestException("partial digests not returned");
        if (buf.length - offset < digest.length)
                throw new DigestException("insufficient space in the output "
                                          + "buffer to store the digest");
        System.arraycopy(digest, 0, buf, offset, digest.length);
        return digest.length;
    }

    /**
     * Resets the digest for further use.
     * <p>
     *  重置摘要以供进一步使用。
     * 
     */
    protected abstract void engineReset();

    /**
     * Returns a clone if the implementation is cloneable.
     *
     * <p>
     *  如果实现是可克隆的,则返回一个克隆。
     * 
     * @return a clone if the implementation is cloneable.
     *
     * @exception CloneNotSupportedException if this is called on an
     * implementation that does not support {@code Cloneable}.
     */
    public Object clone() throws CloneNotSupportedException {
        if (this instanceof Cloneable) {
            return super.clone();
        } else {
            throw new CloneNotSupportedException();
        }
    }
}
