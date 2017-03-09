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
import java.util.regex.*;

import java.security.Provider.Service;

import sun.security.jca.*;
import sun.security.jca.GetInstance.Instance;
import sun.security.util.Debug;

/**
 * This class provides a cryptographically strong random number
 * generator (RNG).
 *
 * <p>A cryptographically strong random number
 * minimally complies with the statistical random number generator tests
 * specified in <a href="http://csrc.nist.gov/cryptval/140-2.htm">
 * <i>FIPS 140-2, Security Requirements for Cryptographic Modules</i></a>,
 * section 4.9.1.
 * Additionally, SecureRandom must produce non-deterministic output.
 * Therefore any seed material passed to a SecureRandom object must be
 * unpredictable, and all SecureRandom output sequences must be
 * cryptographically strong, as described in
 * <a href="http://www.ietf.org/rfc/rfc1750.txt">
 * <i>RFC 1750: Randomness Recommendations for Security</i></a>.
 *
 * <p>A caller obtains a SecureRandom instance via the
 * no-argument constructor or one of the {@code getInstance} methods:
 *
 * <pre>
 *      SecureRandom random = new SecureRandom();
 * </pre>
 *
 * <p> Many SecureRandom implementations are in the form of a pseudo-random
 * number generator (PRNG), which means they use a deterministic algorithm
 * to produce a pseudo-random sequence from a true random seed.
 * Other implementations may produce true random numbers,
 * and yet others may use a combination of both techniques.
 *
 * <p> Typical callers of SecureRandom invoke the following methods
 * to retrieve random bytes:
 *
 * <pre>
 *      SecureRandom random = new SecureRandom();
 *      byte bytes[] = new byte[20];
 *      random.nextBytes(bytes);
 * </pre>
 *
 * <p> Callers may also invoke the {@code generateSeed} method
 * to generate a given number of seed bytes (to seed other random number
 * generators, for example):
 * <pre>
 *      byte seed[] = random.generateSeed(20);
 * </pre>
 *
 * Note: Depending on the implementation, the {@code generateSeed} and
 * {@code nextBytes} methods may block as entropy is being gathered,
 * for example, if they need to read from /dev/random on various Unix-like
 * operating systems.
 *
 * <p>
 *  这个类提供了一个密码强的随机数生成器(RNG)。
 * 
 *  <p>密码强的随机数最低限度符合<a href="http://csrc.nist.gov/cryptval/140-2.htm"> FIPS 140-2中指定的统计随机数生成器测试,加密模块的安全要
 * 求</i> </a>,第4.9.1节。
 * 此外,SecureRandom必须产生非确定性输出。因此,传递给SecureRandom对象的任何种子资料必须是不可预测的,并且所有SecureRandom输出序列必须具有加密强度,如。
 * <a href="http://www.ietf.org/rfc/rfc1750.txt">
 *  <i> RFC 1750：Randomness Recommendations for Security </i> </a>。
 * 
 *  <p>调用者通过无参构造函数或{@code getInstance}方法之一获取SecureRandom实例：
 * 
 * <pre>
 *  SecureRandom random = new SecureRandom();
 * </pre>
 * 
 *  许多SecureRandom实现是以伪随机数生成器(PRNG)的形式,这意味着它们使用确定性算法从真随机种子产生伪随机序列。其他实现可以产生真随机数,并且其他实现可以使用两种技术的组合。
 * 
 *  <p> SecureRandom的典型调用者调用以下方法来检索随机字节：
 * 
 * <pre>
 *  SecureRandom random = new SecureRandom(); byte byte [] = new byte [20]; random.nextBytes(bytes);
 * </pre>
 * 
 * <p>调用者还可以调用{@code generateSeed}方法来生成给定数量的种子字节(例如,为其他随机数生成器生成种子)：
 * <pre>
 *  byte seed [] = random.generateSeed(20);
 * </pre>
 * 
 *  注意：根据实现,{@code generateSeed}和{@code nextBytes}方法可能会在熵被收集时阻塞,例如,如果他们需要在各种类Unix操作系统上从/ dev / random读取数
 * 据。
 * 
 * 
 * @see java.security.SecureRandomSpi
 * @see java.util.Random
 *
 * @author Benjamin Renaud
 * @author Josh Bloch
 */

public class SecureRandom extends java.util.Random {

    private static final Debug pdebug =
                        Debug.getInstance("provider", "Provider");
    private static final boolean skipDebug =
        Debug.isOn("engine=") && !Debug.isOn("securerandom");

    /**
     * The provider.
     *
     * <p>
     *  提供者。
     * 
     * 
     * @serial
     * @since 1.2
     */
    private Provider provider = null;

    /**
     * The provider implementation.
     *
     * <p>
     *  提供程序实现。
     * 
     * 
     * @serial
     * @since 1.2
     */
    private SecureRandomSpi secureRandomSpi = null;

    /*
     * The algorithm name of null if unknown.
     *
     * <p>
     *  如果未知,则为null的算法名称。
     * 
     * 
     * @serial
     * @since 1.5
     */
    private String algorithm;

    // Seed Generator
    private static volatile SecureRandom seedGenerator = null;

    /**
     * Constructs a secure random number generator (RNG) implementing the
     * default random number algorithm.
     *
     * <p> This constructor traverses the list of registered security Providers,
     * starting with the most preferred Provider.
     * A new SecureRandom object encapsulating the
     * SecureRandomSpi implementation from the first
     * Provider that supports a SecureRandom (RNG) algorithm is returned.
     * If none of the Providers support a RNG algorithm,
     * then an implementation-specific default is returned.
     *
     * <p> Note that the list of registered providers may be retrieved via
     * the {@link Security#getProviders() Security.getProviders()} method.
     *
     * <p> See the SecureRandom section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#SecureRandom">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard RNG algorithm names.
     *
     * <p> The returned SecureRandom object has not been seeded.  To seed the
     * returned object, call the {@code setSeed} method.
     * If {@code setSeed} is not called, the first call to
     * {@code nextBytes} will force the SecureRandom object to seed itself.
     * This self-seeding will not occur if {@code setSeed} was
     * previously called.
     * <p>
     *  构造实现默认随机数算法的安全随机数生成器(RNG)。
     * 
     *  <p>此构造函数遍历注册的安全提供程序列表,从最常用的提供程序开始。
     * 将返回一个新的SecureRandom对象,该对象封装来自支持SecureRandom(RNG)算法的第一个Provider的SecureRandomSpi实现。
     * 如果没有提供商支持RNG算法,则返回实现特定的默认值。
     * 
     *  <p>请注意,可以通过{@link Security#getProviders()Security.getProviders()}方法检索注册提供商的列表。
     * 
     *  <p>请参阅<a href =中的SecureRandom部分
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#SecureRandom">
     *  Java加密架构标准算法名称文档</a>,以获取有关标准RNG算法名称的信息。
     * 
     * <p>返回的SecureRandom对象尚未设为种子。要播种返回的对象,请调用{@code setSeed}方法。
     * 如果没有调用{@code setSeed},第一次调用{@code nextBytes}将强制SecureRandom对象进行种子本身。
     * 如果之前调用了{@code setSeed},则不会发生这种自我种子。
     * 
     */
    public SecureRandom() {
        /*
         * This call to our superclass constructor will result in a call
         * to our own {@code setSeed} method, which will return
         * immediately when it is passed zero.
         * <p>
         *  对我们的超类构造函数的这个调用将导致对我们自己的{@code setSeed}方法的调用,当它传递为零时,它立即返回。
         * 
         */
        super(0);
        getDefaultPRNG(false, null);
    }

    /**
     * Constructs a secure random number generator (RNG) implementing the
     * default random number algorithm.
     * The SecureRandom instance is seeded with the specified seed bytes.
     *
     * <p> This constructor traverses the list of registered security Providers,
     * starting with the most preferred Provider.
     * A new SecureRandom object encapsulating the
     * SecureRandomSpi implementation from the first
     * Provider that supports a SecureRandom (RNG) algorithm is returned.
     * If none of the Providers support a RNG algorithm,
     * then an implementation-specific default is returned.
     *
     * <p> Note that the list of registered providers may be retrieved via
     * the {@link Security#getProviders() Security.getProviders()} method.
     *
     * <p> See the SecureRandom section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#SecureRandom">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard RNG algorithm names.
     *
     * <p>
     *  构造实现默认随机数算法的安全随机数生成器(RNG)。 SecureRandom实例使用指定的种子字节进行种子化。
     * 
     *  <p>此构造函数遍历注册的安全提供程序列表,从最常用的提供程序开始。
     * 将返回一个新的SecureRandom对象,该对象封装来自支持SecureRandom(RNG)算法的第一个Provider的SecureRandomSpi实现。
     * 如果没有提供商支持RNG算法,则返回实现特定的默认值。
     * 
     *  <p>请注意,可以通过{@link Security#getProviders()Security.getProviders()}方法检索注册提供商的列表。
     * 
     *  <p>请参阅<a href =中的SecureRandom部分
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#SecureRandom">
     *  Java加密架构标准算法名称文档</a>,以获取有关标准RNG算法名称的信息。
     * 
     * 
     * @param seed the seed.
     */
    public SecureRandom(byte seed[]) {
        super(0);
        getDefaultPRNG(true, seed);
    }

    private void getDefaultPRNG(boolean setSeed, byte[] seed) {
        String prng = getPrngAlgorithm();
        if (prng == null) {
            // bummer, get the SUN implementation
            prng = "SHA1PRNG";
            this.secureRandomSpi = new sun.security.provider.SecureRandom();
            this.provider = Providers.getSunProvider();
            if (setSeed) {
                this.secureRandomSpi.engineSetSeed(seed);
            }
        } else {
            try {
                SecureRandom random = SecureRandom.getInstance(prng);
                this.secureRandomSpi = random.getSecureRandomSpi();
                this.provider = random.getProvider();
                if (setSeed) {
                    this.secureRandomSpi.engineSetSeed(seed);
                }
            } catch (NoSuchAlgorithmException nsae) {
                // never happens, because we made sure the algorithm exists
                throw new RuntimeException(nsae);
            }
        }
        // JDK 1.1 based implementations subclass SecureRandom instead of
        // SecureRandomSpi. They will also go through this code path because
        // they must call a SecureRandom constructor as it is their superclass.
        // If we are dealing with such an implementation, do not set the
        // algorithm value as it would be inaccurate.
        if (getClass() == SecureRandom.class) {
            this.algorithm = prng;
        }
    }

    /**
     * Creates a SecureRandom object.
     *
     * <p>
     *  创建SecureRandom对象。
     * 
     * 
     * @param secureRandomSpi the SecureRandom implementation.
     * @param provider the provider.
     */
    protected SecureRandom(SecureRandomSpi secureRandomSpi,
                           Provider provider) {
        this(secureRandomSpi, provider, null);
    }

    private SecureRandom(SecureRandomSpi secureRandomSpi, Provider provider,
            String algorithm) {
        super(0);
        this.secureRandomSpi = secureRandomSpi;
        this.provider = provider;
        this.algorithm = algorithm;

        if (!skipDebug && pdebug != null) {
            pdebug.println("SecureRandom." + algorithm +
                " algorithm from: " + this.provider.getName());
        }
    }

    /**
     * Returns a SecureRandom object that implements the specified
     * Random Number Generator (RNG) algorithm.
     *
     * <p> This method traverses the list of registered security Providers,
     * starting with the most preferred Provider.
     * A new SecureRandom object encapsulating the
     * SecureRandomSpi implementation from the first
     * Provider that supports the specified algorithm is returned.
     *
     * <p> Note that the list of registered providers may be retrieved via
     * the {@link Security#getProviders() Security.getProviders()} method.
     *
     * <p> The returned SecureRandom object has not been seeded.  To seed the
     * returned object, call the {@code setSeed} method.
     * If {@code setSeed} is not called, the first call to
     * {@code nextBytes} will force the SecureRandom object to seed itself.
     * This self-seeding will not occur if {@code setSeed} was
     * previously called.
     *
     * <p>
     *  返回实现指定的随机数生成器(RNG)算法的SecureRandom对象。
     * 
     * <p>此方法遍历注册的安全提供程序列表,从最常用的提供程序开始。将返回一个新的SecureRandom对象,该对象封装来自支持指定算法的第一个Provider的SecureRandomSpi实现。
     * 
     *  <p>请注意,可以通过{@link Security#getProviders()Security.getProviders()}方法检索注册提供商的列表。
     * 
     *  <p>返回的SecureRandom对象尚未设为种子。要播种返回的对象,请调用{@code setSeed}方法。
     * 如果没有调用{@code setSeed},第一次调用{@code nextBytes}将强制SecureRandom对象进行种子本身。
     * 如果之前调用了{@code setSeed},则不会发生这种自我种子。
     * 
     * 
     * @param algorithm the name of the RNG algorithm.
     * See the SecureRandom section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#SecureRandom">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard RNG algorithm names.
     *
     * @return the new SecureRandom object.
     *
     * @exception NoSuchAlgorithmException if no Provider supports a
     *          SecureRandomSpi implementation for the
     *          specified algorithm.
     *
     * @see Provider
     *
     * @since 1.2
     */
    public static SecureRandom getInstance(String algorithm)
            throws NoSuchAlgorithmException {
        Instance instance = GetInstance.getInstance("SecureRandom",
            SecureRandomSpi.class, algorithm);
        return new SecureRandom((SecureRandomSpi)instance.impl,
            instance.provider, algorithm);
    }

    /**
     * Returns a SecureRandom object that implements the specified
     * Random Number Generator (RNG) algorithm.
     *
     * <p> A new SecureRandom object encapsulating the
     * SecureRandomSpi implementation from the specified provider
     * is returned.  The specified provider must be registered
     * in the security provider list.
     *
     * <p> Note that the list of registered providers may be retrieved via
     * the {@link Security#getProviders() Security.getProviders()} method.
     *
     * <p> The returned SecureRandom object has not been seeded.  To seed the
     * returned object, call the {@code setSeed} method.
     * If {@code setSeed} is not called, the first call to
     * {@code nextBytes} will force the SecureRandom object to seed itself.
     * This self-seeding will not occur if {@code setSeed} was
     * previously called.
     *
     * <p>
     *  返回实现指定的随机数生成器(RNG)算法的SecureRandom对象。
     * 
     *  <p>返回一个新的SecureRandom对象,用于封装来自指定提供者的SecureRandomSpi实现。指定的提供程序必须在安全提供程序列表中注册。
     * 
     *  <p>请注意,可以通过{@link Security#getProviders()Security.getProviders()}方法检索注册提供商的列表。
     * 
     *  <p>返回的SecureRandom对象尚未设为种子。要播种返回的对象,请调用{@code setSeed}方法。
     * 如果没有调用{@code setSeed},第一次调用{@code nextBytes}将强制SecureRandom对象进行种子本身。
     * 如果之前调用了{@code setSeed},则不会发生这种自我种子。
     * 
     * 
     * @param algorithm the name of the RNG algorithm.
     * See the SecureRandom section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#SecureRandom">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard RNG algorithm names.
     *
     * @param provider the name of the provider.
     *
     * @return the new SecureRandom object.
     *
     * @exception NoSuchAlgorithmException if a SecureRandomSpi
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
     *
     * @since 1.2
     */
    public static SecureRandom getInstance(String algorithm, String provider)
            throws NoSuchAlgorithmException, NoSuchProviderException {
        Instance instance = GetInstance.getInstance("SecureRandom",
            SecureRandomSpi.class, algorithm, provider);
        return new SecureRandom((SecureRandomSpi)instance.impl,
            instance.provider, algorithm);
    }

    /**
     * Returns a SecureRandom object that implements the specified
     * Random Number Generator (RNG) algorithm.
     *
     * <p> A new SecureRandom object encapsulating the
     * SecureRandomSpi implementation from the specified Provider
     * object is returned.  Note that the specified Provider object
     * does not have to be registered in the provider list.
     *
     * <p> The returned SecureRandom object has not been seeded.  To seed the
     * returned object, call the {@code setSeed} method.
     * If {@code setSeed} is not called, the first call to
     * {@code nextBytes} will force the SecureRandom object to seed itself.
     * This self-seeding will not occur if {@code setSeed} was
     * previously called.
     *
     * <p>
     * 返回实现指定的随机数生成器(RNG)算法的SecureRandom对象。
     * 
     *  <p>返回一个新的SecureRandom对象,该对象封装来自指定的Provider对象的SecureRandomSpi实现。请注意,指定的Provider对象不必在提供程序列表中注册。
     * 
     *  <p>返回的SecureRandom对象尚未设为种子。要播种返回的对象,请调用{@code setSeed}方法。
     * 如果没有调用{@code setSeed},第一次调用{@code nextBytes}将强制SecureRandom对象进行种子本身。
     * 如果之前调用了{@code setSeed},则不会发生这种自我种子。
     * 
     * 
     * @param algorithm the name of the RNG algorithm.
     * See the SecureRandom section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#SecureRandom">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard RNG algorithm names.
     *
     * @param provider the provider.
     *
     * @return the new SecureRandom object.
     *
     * @exception NoSuchAlgorithmException if a SecureRandomSpi
     *          implementation for the specified algorithm is not available
     *          from the specified Provider object.
     *
     * @exception IllegalArgumentException if the specified provider is null.
     *
     * @see Provider
     *
     * @since 1.4
     */
    public static SecureRandom getInstance(String algorithm,
            Provider provider) throws NoSuchAlgorithmException {
        Instance instance = GetInstance.getInstance("SecureRandom",
            SecureRandomSpi.class, algorithm, provider);
        return new SecureRandom((SecureRandomSpi)instance.impl,
            instance.provider, algorithm);
    }

    /**
     * Returns the SecureRandomSpi of this SecureRandom object.
     * <p>
     *  返回此SecureRandom对象的SecureRandomSpi。
     * 
     */
    SecureRandomSpi getSecureRandomSpi() {
        return secureRandomSpi;
    }

    /**
     * Returns the provider of this SecureRandom object.
     *
     * <p>
     *  返回此SecureRandom对象的提供程序。
     * 
     * 
     * @return the provider of this SecureRandom object.
     */
    public final Provider getProvider() {
        return provider;
    }

    /**
     * Returns the name of the algorithm implemented by this SecureRandom
     * object.
     *
     * <p>
     *  返回此SecureRandom对象实现的算法的名称。
     * 
     * 
     * @return the name of the algorithm or {@code unknown}
     *          if the algorithm name cannot be determined.
     * @since 1.5
     */
    public String getAlgorithm() {
        return (algorithm != null) ? algorithm : "unknown";
    }

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
     *
     * @see #getSeed
     */
    synchronized public void setSeed(byte[] seed) {
        secureRandomSpi.engineSetSeed(seed);
    }

    /**
     * Reseeds this random object, using the eight bytes contained
     * in the given {@code long seed}. The given seed supplements,
     * rather than replaces, the existing seed. Thus, repeated calls
     * are guaranteed never to reduce randomness.
     *
     * <p>This method is defined for compatibility with
     * {@code java.util.Random}.
     *
     * <p>
     *  使用给定的{@code long seed}中包含的8个字节重新设定这个随机对象。给定的种子补充,而不是取代现有的种子。因此,重复的调用保证从不减少随机性。
     * 
     *  <p>此方法的定义与{@code java.util.Random}的兼容性。
     * 
     * 
     * @param seed the seed.
     *
     * @see #getSeed
     */
    @Override
    public void setSeed(long seed) {
        /*
         * Ignore call from super constructor (as well as any other calls
         * unfortunate enough to be passing 0).  It's critical that we
         * ignore call from superclass constructor, as digest has not
         * yet been initialized at that point.
         * <p>
         * 忽略从超级构造函数调用(以及任何其他调用不幸足够传递0)。至关重要的是,我们忽略来自超类构造函数的调用,因为摘要尚未在该点初始化。
         * 
         */
        if (seed != 0) {
            secureRandomSpi.engineSetSeed(longToByteArray(seed));
        }
    }

    /**
     * Generates a user-specified number of random bytes.
     *
     * <p> If a call to {@code setSeed} had not occurred previously,
     * the first call to this method forces this SecureRandom object
     * to seed itself.  This self-seeding will not occur if
     * {@code setSeed} was previously called.
     *
     * <p>
     *  生成用户指定数量的随机字节。
     * 
     *  <p>如果之前没有发生对{@code setSeed}的调用,则对该方法的第一次调用将强制此SecureRandom对象进行种子本身。
     * 如果之前调用了{@code setSeed},则不会发生这种自我种子。
     * 
     * 
     * @param bytes the array to be filled in with random bytes.
     */
    @Override
    synchronized public void nextBytes(byte[] bytes) {
        secureRandomSpi.engineNextBytes(bytes);
    }

    /**
     * Generates an integer containing the user-specified number of
     * pseudo-random bits (right justified, with leading zeros).  This
     * method overrides a {@code java.util.Random} method, and serves
     * to provide a source of random bits to all of the methods inherited
     * from that class (for example, {@code nextInt},
     * {@code nextLong}, and {@code nextFloat}).
     *
     * <p>
     *  生成包含用户指定数量的伪随机位的整数(右对齐,带前导零)。
     * 此方法覆盖{@code java.util.Random}方法,并用于为从该类继承的所有方法(例如,{@code nextInt},{@code nextLong})提供随机位元来源, {@code nextFloat}
     * )。
     *  生成包含用户指定数量的伪随机位的整数(右对齐,带前导零)。
     * 
     * 
     * @param numBits number of pseudo-random bits to be generated, where
     * {@code 0 <= numBits <= 32}.
     *
     * @return an {@code int} containing the user-specified number
     * of pseudo-random bits (right justified, with leading zeros).
     */
    @Override
    final protected int next(int numBits) {
        int numBytes = (numBits+7)/8;
        byte b[] = new byte[numBytes];
        int next = 0;

        nextBytes(b);
        for (int i = 0; i < numBytes; i++) {
            next = (next << 8) + (b[i] & 0xFF);
        }

        return next >>> (numBytes*8 - numBits);
    }

    /**
     * Returns the given number of seed bytes, computed using the seed
     * generation algorithm that this class uses to seed itself.  This
     * call may be used to seed other random number generators.
     *
     * <p>This method is only included for backwards compatibility.
     * The caller is encouraged to use one of the alternative
     * {@code getInstance} methods to obtain a SecureRandom object, and
     * then call the {@code generateSeed} method to obtain seed bytes
     * from that object.
     *
     * <p>
     *  返回给定数量的种子字节,使用种子生成算法计算,该类用于对种子本身进行种子。此调用可用于种子其他随机数生成器。
     * 
     *  <p>此方法仅用于向后兼容性。
     * 鼓励调用者使用一个替代的{@code getInstance}方法来获取SecureRandom对象,然后调用{@code generateSeed}方法从该对象获取种子字节。
     * 
     * 
     * @param numBytes the number of seed bytes to generate.
     *
     * @return the seed bytes.
     *
     * @see #setSeed
     */
    public static byte[] getSeed(int numBytes) {
        if (seedGenerator == null) {
            seedGenerator = new SecureRandom();
        }
        return seedGenerator.generateSeed(numBytes);
    }

    /**
     * Returns the given number of seed bytes, computed using the seed
     * generation algorithm that this class uses to seed itself.  This
     * call may be used to seed other random number generators.
     *
     * <p>
     *  返回给定数量的种子字节,使用种子生成算法计算,该类用于对种子本身进行种子。此调用可用于种子其他随机数生成器。
     * 
     * 
     * @param numBytes the number of seed bytes to generate.
     *
     * @return the seed bytes.
     */
    public byte[] generateSeed(int numBytes) {
        return secureRandomSpi.engineGenerateSeed(numBytes);
    }

    /**
     * Helper function to convert a long into a byte array (least significant
     * byte first).
     * <p>
     * 辅助函数将long转换为字节数组(最低有效字节优先)。
     * 
     */
    private static byte[] longToByteArray(long l) {
        byte[] retVal = new byte[8];

        for (int i = 0; i < 8; i++) {
            retVal[i] = (byte) l;
            l >>= 8;
        }

        return retVal;
    }

    /**
     * Gets a default PRNG algorithm by looking through all registered
     * providers. Returns the first PRNG algorithm of the first provider that
     * has registered a SecureRandom implementation, or null if none of the
     * registered providers supplies a SecureRandom implementation.
     * <p>
     *  通过查看所有注册的提供程序,获取默认的PRNG算法。
     * 返回已注册SecureRandom实现的第一个提供程序的第一个PRNG算法,如果没有注册的提供程序提供SecureRandom实现,则返回null。
     * 
     */
    private static String getPrngAlgorithm() {
        for (Provider p : Providers.getProviderList().providers()) {
            for (Service s : p.getServices()) {
                if (s.getType().equals("SecureRandom")) {
                    return s.getAlgorithm();
                }
            }
        }
        return null;
    }

    /*
     * Lazily initialize since Pattern.compile() is heavy.
     * Effective Java (2nd Edition), Item 71.
     * <p>
     *  由于Pattern.compile()很重,因此需要初始化。 Effective Java(2nd Edition),Item 71。
     * 
     */
    private static final class StrongPatternHolder {
        /*
         * Entries are alg:prov separated by ,
         * Allow for prepended/appended whitespace between entries.
         *
         * Capture groups:
         *     1 - alg
         *     2 - :prov (optional)
         *     3 - prov (optional)
         *     4 - ,nextEntry (optional)
         *     5 - nextEntry (optional)
         * <p>
         *  条目是alg：prov由,允许在条目之间添加前缀/附加的空格。
         * 
         *  捕获组：1  -  alg 2  - ：prov(可选)3  -  prov(可选)4  - ,nextEntry(可选)5  -  nextEntry
         * 
         */
        private static Pattern pattern =
            Pattern.compile(
                "\\s*([\\S&&[^:,]]*)(\\:([\\S&&[^,]]*))?\\s*(\\,(.*))?");
    }

    /**
     * Returns a {@code SecureRandom} object that was selected by using
     * the algorithms/providers specified in the {@code
     * securerandom.strongAlgorithms} {@link Security} property.
     * <p>
     * Some situations require strong random values, such as when
     * creating high-value/long-lived secrets like RSA public/private
     * keys.  To help guide applications in selecting a suitable strong
     * {@code SecureRandom} implementation, Java distributions
     * include a list of known strong {@code SecureRandom}
     * implementations in the {@code securerandom.strongAlgorithms}
     * Security property.
     * <p>
     * Every implementation of the Java platform is required to
     * support at least one strong {@code SecureRandom} implementation.
     *
     * <p>
     *  返回通过使用{@code securerandom.strongAlgorithms} {@link Security}属性中指定的算法/提供程序选择的{@code SecureRandom}对象。
     * <p>
     *  某些情况需要强随机值,例如在创建高价值/长寿命秘密(如RSA公共/私有密钥)时。
     * 为了帮助指导应用程序选择合适的强{@code SecureRandom}实现,Java发行版包括{@code securerandom.strongAlgorithms}安全属性中已知的强{@code SecureRandom}
     * 实现的列表。
     *  某些情况需要强随机值,例如在创建高价值/长寿命秘密(如RSA公共/私有密钥)时。
     * <p>
     * 
     * @return a strong {@code SecureRandom} implementation as indicated
     * by the {@code securerandom.strongAlgorithms} Security property
     *
     * @throws NoSuchAlgorithmException if no algorithm is available
     *
     * @see Security#getProperty(String)
     *
     * @since 1.8
     */
    public static SecureRandom getInstanceStrong()
            throws NoSuchAlgorithmException {

        String property = AccessController.doPrivileged(
            new PrivilegedAction<String>() {
                @Override
                public String run() {
                    return Security.getProperty(
                        "securerandom.strongAlgorithms");
                }
            });

        if ((property == null) || (property.length() == 0)) {
            throw new NoSuchAlgorithmException(
                "Null/empty securerandom.strongAlgorithms Security Property");
        }

        String remainder = property;
        while (remainder != null) {
            Matcher m;
            if ((m = StrongPatternHolder.pattern.matcher(
                    remainder)).matches()) {

                String alg = m.group(1);
                String prov = m.group(3);

                try {
                    if (prov == null) {
                        return SecureRandom.getInstance(alg);
                    } else {
                        return SecureRandom.getInstance(alg, prov);
                    }
                } catch (NoSuchAlgorithmException |
                        NoSuchProviderException e) {
                }
                remainder = m.group(5);
            } else {
                remainder = null;
            }
        }

        throw new NoSuchAlgorithmException(
            "No strong SecureRandom impls available: " + property);
    }

    // Declare serialVersionUID to be compatible with JDK1.1
    static final long serialVersionUID = 4940670005562187L;

    // Retain unused values serialized from JDK1.1
    /**
    /* <p>
    /*  每个Java平台的实现都需要至少支持一个强大的{@code SecureRandom}实现。
    /* 
    /* 
     * @serial
     */
    private byte[] state;
    /**
    /* <p>
    /* 
     * @serial
     */
    private MessageDigest digest = null;
    /**
    /* <p>
    /* 
     * @serial
     *
     * We know that the MessageDigest class does not implement
     * java.io.Serializable.  However, since this field is no longer
     * used, it will always be NULL and won't affect the serialization
     * of the SecureRandom class itself.
     */
    private byte[] randomBytes;
    /**
    /* <p>
    /* 
     * @serial
     */
    private int randomBytesUsed;
    /**
    /* <p>
    /* 
     * @serial
     */
    private long counter;
}
