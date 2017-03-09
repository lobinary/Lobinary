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

package java.security;

/**
 * This class defines the <i>Service Provider Interface</i> (<b>SPI</b>)
 * for the {@code SecureRandom} class.
 * All the abstract methods in this class must be implemented by each
 * service provider who wishes to supply the implementation
 * of a cryptographically strong pseudo-random number generator.
 *
 *
 * <p>
 *  此类为{@code SecureRandom}类定义了<i>服务提供程序接口</i>(<b> SPI </b>)。该类中的所有抽象方法必须由希望提供加密强伪随机数生成器的实现的每个服务提供者实现。
 * 
 * 
 * @see SecureRandom
 * @since 1.2
 */

public abstract class SecureRandomSpi implements java.io.Serializable {

    private static final long serialVersionUID = -2991854161009191830L;

    /**
     * Reseeds this random object. The given seed supplements, rather than
     * replaces, the existing seed. Thus, repeated calls are guaranteed
     * never to reduce randomness.
     *
     * <p>
     *  重新采样此随机对象。给定的种子补充,而不是取代现有的种子。因此,重复的调用保证从不减少随机性。
     * 
     * 
     * @param seed the seed.
     */
    protected abstract void engineSetSeed(byte[] seed);

    /**
     * Generates a user-specified number of random bytes.
     *
     * <p> If a call to {@code engineSetSeed} had not occurred previously,
     * the first call to this method forces this SecureRandom implementation
     * to seed itself.  This self-seeding will not occur if
     * {@code engineSetSeed} was previously called.
     *
     * <p>
     *  生成用户指定数量的随机字节。
     * 
     *  <p>如果先前没有发生对{@code engineSetSeed}的调用,则对此方法的第一次调用将强制此SecureRandom实现为其自身设置种子。
     * 如果先前调用了{@code engineSetSeed},则不会发生这种自我种子。
     * 
     * @param bytes the array to be filled in with random bytes.
     */
    protected abstract void engineNextBytes(byte[] bytes);

    /**
     * Returns the given number of seed bytes.  This call may be used to
     * seed other random number generators.
     *
     * <p>
     * 
     * 
     * @param numBytes the number of seed bytes to generate.
     *
     * @return the seed bytes.
     */
     protected abstract byte[] engineGenerateSeed(int numBytes);
}
