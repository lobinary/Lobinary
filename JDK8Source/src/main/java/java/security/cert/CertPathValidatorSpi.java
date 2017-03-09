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
 *
 * The <i>Service Provider Interface</i> (<b>SPI</b>)
 * for the {@link CertPathValidator CertPathValidator} class. All
 * {@code CertPathValidator} implementations must include a class (the
 * SPI class) that extends this class ({@code CertPathValidatorSpi})
 * and implements all of its methods. In general, instances of this class
 * should only be accessed through the {@code CertPathValidator} class.
 * For details, see the Java Cryptography Architecture.
 * <p>
 * <b>Concurrent Access</b>
 * <p>
 * Instances of this class need not be protected against concurrent
 * access from multiple threads. Threads that need to access a single
 * {@code CertPathValidatorSpi} instance concurrently should synchronize
 * amongst themselves and provide the necessary locking before calling the
 * wrapping {@code CertPathValidator} object.
 * <p>
 * However, implementations of {@code CertPathValidatorSpi} may still
 * encounter concurrency issues, since multiple threads each
 * manipulating a different {@code CertPathValidatorSpi} instance need not
 * synchronize.
 *
 * <p>
 *  {@link CertPathValidator CertPathValidator}类的<i>服务提供程序接口</i>(<b> SPI </b>)。
 * 所有{@code CertPathValidator}实现必须包括一个类(SPI类),它扩展了这个类({@code CertPathValidatorSpi})并实现所有的方法。
 * 通常,此类的实例只应通过{@code CertPathValidator}类访问。有关详细信息,请参阅Java加密体系结构。
 * <p>
 *  <b>并行访问</b>
 * <p>
 *  这个类的实例不需要被保护以防止来自多个线程的并发访问。
 * 需要同时访问单个{@code CertPathValidatorSpi}实例的线程应在它们之间同步,并在调用包装{@code CertPathValidator}对象之前提供必要的锁定。
 * <p>
 *  然而,{@code CertPathValidatorSpi}的实现可能仍然遇到并发问题,因为每个操纵不同{@code CertPathValidatorSpi}实例的多个线程不需要同步。
 * 
 * 
 * @since       1.4
 * @author      Yassir Elley
 */
public abstract class CertPathValidatorSpi {

    /**
     * The default constructor.
     * <p>
     *  默认构造函数。
     * 
     */
    public CertPathValidatorSpi() {}

    /**
     * Validates the specified certification path using the specified
     * algorithm parameter set.
     * <p>
     * The {@code CertPath} specified must be of a type that is
     * supported by the validation algorithm, otherwise an
     * {@code InvalidAlgorithmParameterException} will be thrown. For
     * example, a {@code CertPathValidator} that implements the PKIX
     * algorithm validates {@code CertPath} objects of type X.509.
     *
     * <p>
     *  使用指定的算法参数集验证指定的认证路径。
     * <p>
     * 指定的{@code CertPath}必须是验证算法支持的类型,否则将抛出{@code InvalidAlgorithmParameterException}。
     * 例如,实现PKIX算法的{@code CertPathValidator}会验证类型X.509的{@code CertPath}对象。
     * 
     * 
     * @param certPath the {@code CertPath} to be validated
     * @param params the algorithm parameters
     * @return the result of the validation algorithm
     * @exception CertPathValidatorException if the {@code CertPath}
     * does not validate
     * @exception InvalidAlgorithmParameterException if the specified
     * parameters or the type of the specified {@code CertPath} are
     * inappropriate for this {@code CertPathValidator}
     */
    public abstract CertPathValidatorResult
        engineValidate(CertPath certPath, CertPathParameters params)
        throws CertPathValidatorException, InvalidAlgorithmParameterException;

    /**
     * Returns a {@code CertPathChecker} that this implementation uses to
     * check the revocation status of certificates. A PKIX implementation
     * returns objects of type {@code PKIXRevocationChecker}.
     *
     * <p>The primary purpose of this method is to allow callers to specify
     * additional input parameters and options specific to revocation checking.
     * See the class description of {@code CertPathValidator} for an example.
     *
     * <p>This method was added to version 1.8 of the Java Platform Standard
     * Edition. In order to maintain backwards compatibility with existing
     * service providers, this method cannot be abstract and by default throws
     * an {@code UnsupportedOperationException}.
     *
     * <p>
     *  返回此实现用于检查证书吊销状态的{@code CertPathChecker}。 PKIX实现返回{@code PKIXRevocationChecker}类型的对象。
     * 
     *  <p>此方法的主要目的是允许调用者指定特定于撤消检查的其他输入参数和选项。有关示例,请参见{@code CertPathValidator}的类描述。
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
