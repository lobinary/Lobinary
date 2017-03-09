/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.security.cert;

import java.security.InvalidAlgorithmParameterException;

/**
 * The <i>Service Provider Interface</i> (<b>SPI</b>)
 * for the {@link CertPathBuilder CertPathBuilder} class. All
 * {@code CertPathBuilder} implementations must include a class (the
 * SPI class) that extends this class ({@code CertPathBuilderSpi}) and
 * implements all of its methods. In general, instances of this class should
 * only be accessed through the {@code CertPathBuilder} class. For
 * details, see the Java Cryptography Architecture.
 * <p>
 * <b>Concurrent Access</b>
 * <p>
 * Instances of this class need not be protected against concurrent
 * access from multiple threads. Threads that need to access a single
 * {@code CertPathBuilderSpi} instance concurrently should synchronize
 * amongst themselves and provide the necessary locking before calling the
 * wrapping {@code CertPathBuilder} object.
 * <p>
 * However, implementations of {@code CertPathBuilderSpi} may still
 * encounter concurrency issues, since multiple threads each
 * manipulating a different {@code CertPathBuilderSpi} instance need not
 * synchronize.
 *
 * <p>
 *  {@link CertPathBuilder CertPathBuilder}类的<i>服务提供程序接口</i>(<b> SPI </b>)。
 * 所有{@code CertPathBuilder}实现都必须包括一个类(SPI类),它扩展了这个类({@code CertPathBuilderSpi})并实现了所有的方法。
 * 一般来说,这个类的实例只能通过{@code CertPathBuilder}类访问。有关详细信息,请参阅Java加密体系结构。
 * <p>
 *  <b>并行访问</b>
 * <p>
 *  这个类的实例不需要被保护以防止来自多个线程的并发访问。
 * 需要并发访问单个{@code CertPathBuilderSpi}实例的线程应在它们之间同步,并在调用包装{@code CertPathBuilder}对象之前提供必要的锁定。
 * <p>
 *  然而,{@code CertPathBuilderSpi}的实现可能仍然遇到并发问题,因为每个操纵不同{@code CertPathBuilderSpi}实例的多个线程不需要同步。
 * 
 * 
 * @since       1.4
 * @author      Sean Mullan
 */
public abstract class CertPathBuilderSpi {

    /**
     * The default constructor.
     * <p>
     *  默认构造函数。
     * 
     */
    public CertPathBuilderSpi() { }

    /**
     * Attempts to build a certification path using the specified
     * algorithm parameter set.
     *
     * <p>
     *  尝试使用指定的算法参数集构建认证路径。
     * 
     * 
     * @param params the algorithm parameters
     * @return the result of the build algorithm
     * @throws CertPathBuilderException if the builder is unable to construct
     * a certification path that satisfies the specified parameters
     * @throws InvalidAlgorithmParameterException if the specified parameters
     * are inappropriate for this {@code CertPathBuilder}
     */
    public abstract CertPathBuilderResult engineBuild(CertPathParameters params)
        throws CertPathBuilderException, InvalidAlgorithmParameterException;

    /**
     * Returns a {@code CertPathChecker} that this implementation uses to
     * check the revocation status of certificates. A PKIX implementation
     * returns objects of type {@code PKIXRevocationChecker}.
     *
     * <p>The primary purpose of this method is to allow callers to specify
     * additional input parameters and options specific to revocation checking.
     * See the class description of {@code CertPathBuilder} for an example.
     *
     * <p>This method was added to version 1.8 of the Java Platform Standard
     * Edition. In order to maintain backwards compatibility with existing
     * service providers, this method cannot be abstract and by default throws
     * an {@code UnsupportedOperationException}.
     *
     * <p>
     *  返回此实现用于检查证书吊销状态的{@code CertPathChecker}。 PKIX实现返回{@code PKIXRevocationChecker}类型的对象。
     * 
     * <p>此方法的主要目的是允许调用者指定特定于撤消检查的其他输入参数和选项。有关示例,请参见{@code CertPathBuilder}的类描述。
     * 
     * 
     * @return a {@code CertPathChecker} that this implementation uses to
     * check the revocation status of certificates
     * @throws UnsupportedOperationException if this method is not supported
     * @since 1.8
     */
    public CertPathChecker engineGetRevocationChecker() {
        throw new UnsupportedOperationException();
    }
}
