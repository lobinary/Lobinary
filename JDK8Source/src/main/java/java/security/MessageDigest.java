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

import java.util.*;
import java.lang.*;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

import java.nio.ByteBuffer;

import sun.security.util.Debug;

/**
 * This MessageDigest class provides applications the functionality of a
 * message digest algorithm, such as SHA-1 or SHA-256.
 * Message digests are secure one-way hash functions that take arbitrary-sized
 * data and output a fixed-length hash value.
 *
 * <p>A MessageDigest object starts out initialized. The data is
 * processed through it using the {@link #update(byte) update}
 * methods. At any point {@link #reset() reset} can be called
 * to reset the digest. Once all the data to be updated has been
 * updated, one of the {@link #digest() digest} methods should
 * be called to complete the hash computation.
 *
 * <p>The {@code digest} method can be called once for a given number
 * of updates. After {@code digest} has been called, the MessageDigest
 * object is reset to its initialized state.
 *
 * <p>Implementations are free to implement the Cloneable interface.
 * Client applications can test cloneability by attempting cloning
 * and catching the CloneNotSupportedException:
 *
 * <pre>{@code
 * MessageDigest md = MessageDigest.getInstance("SHA");
 *
 * try {
 *     md.update(toChapter1);
 *     MessageDigest tc1 = md.clone();
 *     byte[] toChapter1Digest = tc1.digest();
 *     md.update(toChapter2);
 *     ...etc.
 * } catch (CloneNotSupportedException cnse) {
 *     throw new DigestException("couldn't make digest of partial content");
 * }
 * }</pre>
 *
 * <p>Note that if a given implementation is not cloneable, it is
 * still possible to compute intermediate digests by instantiating
 * several instances, if the number of digests is known in advance.
 *
 * <p>Note that this class is abstract and extends from
 * {@code MessageDigestSpi} for historical reasons.
 * Application developers should only take notice of the methods defined in
 * this {@code MessageDigest} class; all the methods in
 * the superclass are intended for cryptographic service providers who wish to
 * supply their own implementations of message digest algorithms.
 *
 * <p> Every implementation of the Java platform is required to support
 * the following standard {@code MessageDigest} algorithms:
 * <ul>
 * <li>{@code MD5}</li>
 * <li>{@code SHA-1}</li>
 * <li>{@code SHA-256}</li>
 * </ul>
 * These algorithms are described in the <a href=
 * "{@docRoot}/../technotes/guides/security/StandardNames.html#MessageDigest">
 * MessageDigest section</a> of the
 * Java Cryptography Architecture Standard Algorithm Name Documentation.
 * Consult the release documentation for your implementation to see if any
 * other algorithms are supported.
 *
 * <p>
 *  此MessageDigest类为应用程序提供消息摘要算法的功能,如SHA-1或SHA-256。消息摘要是获取任意大小的数据并输出固定长度的散列值的安全单向散列函数。
 * 
 *  <p> MessageDigest对象开始初始化。使用{@link #update(byte)update}方法处理数据。在任何时候,可以调用{@link #reset()reset}来重置摘要。
 * 一旦更新了所有要更新的数据,就应该调用{@link #digest()digest}方法之一来完成散列计算。
 * 
 *  <p>对于给定数量的更新,可以调用{@code digest}方法一次。调用{@code digest}后,MessageDigest对象将重置为其初始化状态。
 * 
 *  <p>实现可以自由实现Cloneable接口。客户端应用程序可以通过尝试克隆和捕获CloneNotSupportedException来测试克隆性：
 * 
 *  <pre> {@ code MessageDigest md = MessageDigest.getInstance("SHA");
 * 
 *  try {md.update(toChapter1); MessageDigest tc1 = md.clone(); byte [] toChapter1Digest = tc1.digest(); md.update(toChapter2); ...等等。
 *  } catch(CloneNotSupportedException cnse){throw new DigestException("can not make digest of partial content"); }
 * } </pre>。
 * 
 * <p>请注意,如果给定的实现不可克隆,则仍然可以通过实例化几个实例来计算中间概要,如果提前知道概要的数量。
 * 
 *  <p>请注意,这个类是抽象的,并且从{@code MessageDigestSpi}扩展为历史原因。
 * 应用程序开发人员应该只注意这个{@code MessageDigest}类中定义的方法;超类中的所有方法都面向希望提供自己的消息摘要算法实现的加密服务提供者。
 * 
 *  <p>每个Java平台的实现都需要支持以下标准{@code MessageDigest}算法：
 * <ul>
 *  <li> {@ code MD5} </li> <li> {@ code SHA-1} </li> <li> {@ code SHA-256}
 * </ul>
 *  这些算法在<a href =中描述
 * "{@docRoot}/../technotes/guides/security/StandardNames.html#MessageDigest">
 *  Java密码体系结构标准算法名称文档的MessageDigest部分</a>。有关实现的信息,请参阅发行文档,以了解是否支持任何其他算法。
 * 
 * 
 * @author Benjamin Renaud
 *
 * @see DigestInputStream
 * @see DigestOutputStream
 */

public abstract class MessageDigest extends MessageDigestSpi {

    private static final Debug pdebug =
                        Debug.getInstance("provider", "Provider");
    private static final boolean skipDebug =
        Debug.isOn("engine=") && !Debug.isOn("messagedigest");

    private String algorithm;

    // The state of this digest
    private static final int INITIAL = 0;
    private static final int IN_PROGRESS = 1;
    private int state = INITIAL;

    // The provider
    private Provider provider;

    /**
     * Creates a message digest with the specified algorithm name.
     *
     * <p>
     *  使用指定的算法名称创建消息摘要。
     * 
     * 
     * @param algorithm the standard name of the digest algorithm.
     * See the MessageDigest section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#MessageDigest">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard algorithm names.
     */
    protected MessageDigest(String algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * Returns a MessageDigest object that implements the specified digest
     * algorithm.
     *
     * <p> This method traverses the list of registered security Providers,
     * starting with the most preferred Provider.
     * A new MessageDigest object encapsulating the
     * MessageDigestSpi implementation from the first
     * Provider that supports the specified algorithm is returned.
     *
     * <p> Note that the list of registered providers may be retrieved via
     * the {@link Security#getProviders() Security.getProviders()} method.
     *
     * <p>
     *  返回实现指定摘要算法的MessageDigest对象。
     * 
     *  <p>此方法遍历注册的安全提供程序列表,从最常用的提供程序开始。
     * 将返回一个新的MessageDigest对象,该对象封装来自支持指定算法的第一个Provider的MessageDigestSpi实现。
     * 
     * <p>请注意,可以通过{@link Security#getProviders()Security.getProviders()}方法检索注册提供商的列表。
     * 
     * 
     * @param algorithm the name of the algorithm requested.
     * See the MessageDigest section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#MessageDigest">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard algorithm names.
     *
     * @return a Message Digest object that implements the specified algorithm.
     *
     * @exception NoSuchAlgorithmException if no Provider supports a
     *          MessageDigestSpi implementation for the
     *          specified algorithm.
     *
     * @see Provider
     */
    public static MessageDigest getInstance(String algorithm)
    throws NoSuchAlgorithmException {
        try {
            MessageDigest md;
            Object[] objs = Security.getImpl(algorithm, "MessageDigest",
                                             (String)null);
            if (objs[0] instanceof MessageDigest) {
                md = (MessageDigest)objs[0];
            } else {
                md = new Delegate((MessageDigestSpi)objs[0], algorithm);
            }
            md.provider = (Provider)objs[1];

            if (!skipDebug && pdebug != null) {
                pdebug.println("MessageDigest." + algorithm +
                    " algorithm from: " + md.provider.getName());
            }

            return md;

        } catch(NoSuchProviderException e) {
            throw new NoSuchAlgorithmException(algorithm + " not found");
        }
    }

    /**
     * Returns a MessageDigest object that implements the specified digest
     * algorithm.
     *
     * <p> A new MessageDigest object encapsulating the
     * MessageDigestSpi implementation from the specified provider
     * is returned.  The specified provider must be registered
     * in the security provider list.
     *
     * <p> Note that the list of registered providers may be retrieved via
     * the {@link Security#getProviders() Security.getProviders()} method.
     *
     * <p>
     *  返回实现指定摘要算法的MessageDigest对象。
     * 
     *  <p>返回一个新的MessageDigest对象,用于封装来自指定提供者的MessageDigestSpi实现。指定的提供程序必须在安全提供程序列表中注册。
     * 
     *  <p>请注意,可以通过{@link Security#getProviders()Security.getProviders()}方法检索注册提供商的列表。
     * 
     * 
     * @param algorithm the name of the algorithm requested.
     * See the MessageDigest section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#MessageDigest">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard algorithm names.
     *
     * @param provider the name of the provider.
     *
     * @return a MessageDigest object that implements the specified algorithm.
     *
     * @exception NoSuchAlgorithmException if a MessageDigestSpi
     *          implementation for the specified algorithm is not
     *          available from the specified provider.
     *
     * @exception NoSuchProviderException if the specified provider is not
     *          registered in the security provider list.
     *
     * @exception IllegalArgumentException if the provider name is null
     *          or empty.
     *
     * @see Provider
     */
    public static MessageDigest getInstance(String algorithm, String provider)
        throws NoSuchAlgorithmException, NoSuchProviderException
    {
        if (provider == null || provider.length() == 0)
            throw new IllegalArgumentException("missing provider");
        Object[] objs = Security.getImpl(algorithm, "MessageDigest", provider);
        if (objs[0] instanceof MessageDigest) {
            MessageDigest md = (MessageDigest)objs[0];
            md.provider = (Provider)objs[1];
            return md;
        } else {
            MessageDigest delegate =
                new Delegate((MessageDigestSpi)objs[0], algorithm);
            delegate.provider = (Provider)objs[1];
            return delegate;
        }
    }

    /**
     * Returns a MessageDigest object that implements the specified digest
     * algorithm.
     *
     * <p> A new MessageDigest object encapsulating the
     * MessageDigestSpi implementation from the specified Provider
     * object is returned.  Note that the specified Provider object
     * does not have to be registered in the provider list.
     *
     * <p>
     *  返回实现指定摘要算法的MessageDigest对象。
     * 
     *  <p>返回一个新的MessageDigest对象,该对象封装来自指定的Provider对象的MessageDigestSpi实现。请注意,指定的Provider对象不必在提供程序列表中注册。
     * 
     * 
     * @param algorithm the name of the algorithm requested.
     * See the MessageDigest section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#MessageDigest">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard algorithm names.
     *
     * @param provider the provider.
     *
     * @return a MessageDigest object that implements the specified algorithm.
     *
     * @exception NoSuchAlgorithmException if a MessageDigestSpi
     *          implementation for the specified algorithm is not available
     *          from the specified Provider object.
     *
     * @exception IllegalArgumentException if the specified provider is null.
     *
     * @see Provider
     *
     * @since 1.4
     */
    public static MessageDigest getInstance(String algorithm,
                                            Provider provider)
        throws NoSuchAlgorithmException
    {
        if (provider == null)
            throw new IllegalArgumentException("missing provider");
        Object[] objs = Security.getImpl(algorithm, "MessageDigest", provider);
        if (objs[0] instanceof MessageDigest) {
            MessageDigest md = (MessageDigest)objs[0];
            md.provider = (Provider)objs[1];
            return md;
        } else {
            MessageDigest delegate =
                new Delegate((MessageDigestSpi)objs[0], algorithm);
            delegate.provider = (Provider)objs[1];
            return delegate;
        }
    }

    /**
     * Returns the provider of this message digest object.
     *
     * <p>
     *  返回此消息摘要对象的提供程序。
     * 
     * 
     * @return the provider of this message digest object
     */
    public final Provider getProvider() {
        return this.provider;
    }

    /**
     * Updates the digest using the specified byte.
     *
     * <p>
     *  使用指定的字节更新摘要。
     * 
     * 
     * @param input the byte with which to update the digest.
     */
    public void update(byte input) {
        engineUpdate(input);
        state = IN_PROGRESS;
    }

    /**
     * Updates the digest using the specified array of bytes, starting
     * at the specified offset.
     *
     * <p>
     *  使用指定的字节数组更新摘要,从指定的偏移量开始。
     * 
     * 
     * @param input the array of bytes.
     *
     * @param offset the offset to start from in the array of bytes.
     *
     * @param len the number of bytes to use, starting at
     * {@code offset}.
     */
    public void update(byte[] input, int offset, int len) {
        if (input == null) {
            throw new IllegalArgumentException("No input buffer given");
        }
        if (input.length - offset < len) {
            throw new IllegalArgumentException("Input buffer too short");
        }
        engineUpdate(input, offset, len);
        state = IN_PROGRESS;
    }

    /**
     * Updates the digest using the specified array of bytes.
     *
     * <p>
     *  使用指定的字节数更新摘要。
     * 
     * 
     * @param input the array of bytes.
     */
    public void update(byte[] input) {
        engineUpdate(input, 0, input.length);
        state = IN_PROGRESS;
    }

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
    public final void update(ByteBuffer input) {
        if (input == null) {
            throw new NullPointerException();
        }
        engineUpdate(input);
        state = IN_PROGRESS;
    }

    /**
     * Completes the hash computation by performing final operations
     * such as padding. The digest is reset after this call is made.
     *
     * <p>
     *  通过执行最终操作(如填充)来完成哈希计算。在进行此调用后,摘要将重置。
     * 
     * 
     * @return the array of bytes for the resulting hash value.
     */
    public byte[] digest() {
        /* Resetting is the responsibility of implementors. */
        byte[] result = engineDigest();
        state = INITIAL;
        return result;
    }

    /**
     * Completes the hash computation by performing final operations
     * such as padding. The digest is reset after this call is made.
     *
     * <p>
     * 通过执行最终操作(如填充)来完成哈希计算。在进行此调用后,摘要将重置。
     * 
     * 
     * @param buf output buffer for the computed digest
     *
     * @param offset offset into the output buffer to begin storing the digest
     *
     * @param len number of bytes within buf allotted for the digest
     *
     * @return the number of bytes placed into {@code buf}
     *
     * @exception DigestException if an error occurs.
     */
    public int digest(byte[] buf, int offset, int len) throws DigestException {
        if (buf == null) {
            throw new IllegalArgumentException("No output buffer given");
        }
        if (buf.length - offset < len) {
            throw new IllegalArgumentException
                ("Output buffer too small for specified offset and length");
        }
        int numBytes = engineDigest(buf, offset, len);
        state = INITIAL;
        return numBytes;
    }

    /**
     * Performs a final update on the digest using the specified array
     * of bytes, then completes the digest computation. That is, this
     * method first calls {@link #update(byte[]) update(input)},
     * passing the <i>input</i> array to the {@code update} method,
     * then calls {@link #digest() digest()}.
     *
     * <p>
     *  使用指定的字节数对摘要执行最后更新,然后完成摘要计算。
     * 也就是说,该方法首先调用{@link #update(byte [])update(input)},将<i>输入</i>数组传递给{@code update}方法,然后调用{@link #digest () 消化()}
     * 。
     *  使用指定的字节数对摘要执行最后更新,然后完成摘要计算。
     * 
     * 
     * @param input the input to be updated before the digest is
     * completed.
     *
     * @return the array of bytes for the resulting hash value.
     */
    public byte[] digest(byte[] input) {
        update(input);
        return digest();
    }

    /**
     * Returns a string representation of this message digest object.
     * <p>
     *  返回此消息摘要对象的字符串表示形式。
     * 
     */
    public String toString() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream p = new PrintStream(baos);
        p.print(algorithm+" Message Digest from "+provider.getName()+", ");
        switch (state) {
        case INITIAL:
            p.print("<initialized>");
            break;
        case IN_PROGRESS:
            p.print("<in progress>");
            break;
        }
        p.println();
        return (baos.toString());
    }

    /**
     * Compares two digests for equality. Does a simple byte compare.
     *
     * <p>
     *  比较两个摘要的平等性。一个简单的字节比较。
     * 
     * 
     * @param digesta one of the digests to compare.
     *
     * @param digestb the other digest to compare.
     *
     * @return true if the digests are equal, false otherwise.
     */
    public static boolean isEqual(byte[] digesta, byte[] digestb) {
        if (digesta.length != digestb.length) {
            return false;
        }

        int result = 0;
        // time-constant comparison
        for (int i = 0; i < digesta.length; i++) {
            result |= digesta[i] ^ digestb[i];
        }
        return result == 0;
    }

    /**
     * Resets the digest for further use.
     * <p>
     *  重置摘要以供进一步使用。
     * 
     */
    public void reset() {
        engineReset();
        state = INITIAL;
    }

    /**
     * Returns a string that identifies the algorithm, independent of
     * implementation details. The name should be a standard
     * Java Security name (such as "SHA", "MD5", and so on).
     * See the MessageDigest section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#MessageDigest">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard algorithm names.
     *
     * <p>
     *  返回标识算法的字符串,与实现详细信息无关。名称应为标准Java安全名称(例如"SHA","MD5"等)。请参阅<a href =中的MessageDigest部分
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#MessageDigest">
     *  Java加密架构标准算法名称文档</a>以获取有关标准算法名称的信息。
     * 
     * 
     * @return the name of the algorithm
     */
    public final String getAlgorithm() {
        return this.algorithm;
    }

    /**
     * Returns the length of the digest in bytes, or 0 if this operation is
     * not supported by the provider and the implementation is not cloneable.
     *
     * <p>
     *  返回摘要的长度(以字节为单位),如果此操作不受提供程序支持并且实现不可克隆,则返回0。
     * 
     * 
     * @return the digest length in bytes, or 0 if this operation is not
     * supported by the provider and the implementation is not cloneable.
     *
     * @since 1.2
     */
    public final int getDigestLength() {
        int digestLen = engineGetDigestLength();
        if (digestLen == 0) {
            try {
                MessageDigest md = (MessageDigest)clone();
                byte[] digest = md.digest();
                return digest.length;
            } catch (CloneNotSupportedException e) {
                return digestLen;
            }
        }
        return digestLen;
    }

    /**
     * Returns a clone if the implementation is cloneable.
     *
     * <p>
     *  如果实现是可克隆的,则返回一个克隆。
     * 
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




    /*
     * The following class allows providers to extend from MessageDigestSpi
     * rather than from MessageDigest. It represents a MessageDigest with an
     * encapsulated, provider-supplied SPI object (of type MessageDigestSpi).
     * If the provider implementation is an instance of MessageDigestSpi,
     * the getInstance() methods above return an instance of this class, with
     * the SPI object encapsulated.
     *
     * Note: All SPI methods from the original MessageDigest class have been
     * moved up the hierarchy into a new class (MessageDigestSpi), which has
     * been interposed in the hierarchy between the API (MessageDigest)
     * and its original parent (Object).
     * <p>
     *  以下类允许提供程序从MessageDigestSpi而不是从MessageDigest扩展。
     * 它表示具有封装的,由提供程序提供的SPI对象(类型为MessageDigestSpi)的MessageDigest。
     * 如果提供程序实现是MessageDigestSpi的实例,上面的getInstance()方法返回此类的实例,并封装了SPI对象。
     * 
     * 注意：来自原始MessageDigest类的所有SPI方法已经从层次结构向上移动到新类(MessageDigestSpi)中,该类已经插入在API(MessageDigest)和其原始父(Object)
     */

    static class Delegate extends MessageDigest {

        // The provider implementation (delegate)
        private MessageDigestSpi digestSpi;

        // constructor
        public Delegate(MessageDigestSpi digestSpi, String algorithm) {
            super(algorithm);
            this.digestSpi = digestSpi;
        }

        /**
         * Returns a clone if the delegate is cloneable.
         *
         * <p>
         * 之间的层次结构中。
         * 
         * 
         * @return a clone if the delegate is cloneable.
         *
         * @exception CloneNotSupportedException if this is called on a
         * delegate that does not support {@code Cloneable}.
         */
        public Object clone() throws CloneNotSupportedException {
            if (digestSpi instanceof Cloneable) {
                MessageDigestSpi digestSpiClone =
                    (MessageDigestSpi)digestSpi.clone();
                // Because 'algorithm', 'provider', and 'state' are private
                // members of our supertype, we must perform a cast to
                // access them.
                MessageDigest that =
                    new Delegate(digestSpiClone,
                                 ((MessageDigest)this).algorithm);
                that.provider = ((MessageDigest)this).provider;
                that.state = ((MessageDigest)this).state;
                return that;
            } else {
                throw new CloneNotSupportedException();
            }
        }

        protected int engineGetDigestLength() {
            return digestSpi.engineGetDigestLength();
        }

        protected void engineUpdate(byte input) {
            digestSpi.engineUpdate(input);
        }

        protected void engineUpdate(byte[] input, int offset, int len) {
            digestSpi.engineUpdate(input, offset, len);
        }

        protected void engineUpdate(ByteBuffer input) {
            digestSpi.engineUpdate(input);
        }

        protected byte[] engineDigest() {
            return digestSpi.engineDigest();
        }

        protected int engineDigest(byte[] buf, int offset, int len)
            throws DigestException {
                return digestSpi.engineDigest(buf, offset, len);
        }

        protected void engineReset() {
            digestSpi.engineReset();
        }
    }
}
