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

import java.io.InvalidObjectException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.GeneralSecurityException;

/**
 * An exception indicating one of a variety of problems encountered when
 * validating a certification path.
 * <p>
 * A {@code CertPathValidatorException} provides support for wrapping
 * exceptions. The {@link #getCause getCause} method returns the throwable,
 * if any, that caused this exception to be thrown.
 * <p>
 * A {@code CertPathValidatorException} may also include the
 * certification path that was being validated when the exception was thrown,
 * the index of the certificate in the certification path that caused the
 * exception to be thrown, and the reason that caused the failure. Use the
 * {@link #getCertPath getCertPath}, {@link #getIndex getIndex}, and
 * {@link #getReason getReason} methods to retrieve this information.
 *
 * <p>
 * <b>Concurrent Access</b>
 * <p>
 * Unless otherwise specified, the methods defined in this class are not
 * thread-safe. Multiple threads that need to access a single
 * object concurrently should synchronize amongst themselves and
 * provide the necessary locking. Multiple threads each manipulating
 * separate objects need not synchronize.
 *
 * <p>
 *  指示验证认证路径时遇到的各种问题之一的异常。
 * <p>
 *  {@code CertPathValidatorException}提供对包装异常的支持。 {@link #getCause getCause}方法返回引发此异常的throwable(如果有)。
 * <p>
 *  {@code CertPathValidatorException}还可以包括在抛出异常时正在验证的证书路径,导致引发异常的证书路径中的证书索引,以及导致失败的原因。
 * 使用{@link #getCertPath getCertPath},{@link #getIndex getIndex}和{@link #getReason getReason}方法检索此信息。
 * 
 * <p>
 *  <b>并行访问</b>
 * <p>
 *  除非另有说明,否则此类中定义的方法不是线程安全的。需要并发访问单个对象的多个线程应在它们之间同步并提供必要的锁定。每个操作单独对象的多个线程不需要同步。
 * 
 * 
 * @see CertPathValidator
 *
 * @since       1.4
 * @author      Yassir Elley
 */
public class CertPathValidatorException extends GeneralSecurityException {

    private static final long serialVersionUID = -3083180014971893139L;

    /**
    /* <p>
    /* 
     * @serial the index of the certificate in the certification path
     * that caused the exception to be thrown
     */
    private int index = -1;

    /**
    /* <p>
    /* 
     * @serial the {@code CertPath} that was being validated when
     * the exception was thrown
     */
    private CertPath certPath;

    /**
    /* <p>
    /* 
     * @serial the reason the validation failed
     */
    private Reason reason = BasicReason.UNSPECIFIED;

    /**
     * Creates a {@code CertPathValidatorException} with
     * no detail message.
     * <p>
     *  创建没有详细消息的{@code CertPathValidatorException}。
     * 
     */
    public CertPathValidatorException() {
        this(null, null);
    }

    /**
     * Creates a {@code CertPathValidatorException} with the given
     * detail message. A detail message is a {@code String} that
     * describes this particular exception.
     *
     * <p>
     *  使用给定的详细信息创建{@code CertPathValidatorException}。详细消息是描述此特殊异常的{@code String}。
     * 
     * 
     * @param msg the detail message
     */
    public CertPathValidatorException(String msg) {
        this(msg, null);
    }

    /**
     * Creates a {@code CertPathValidatorException} that wraps the
     * specified throwable. This allows any exception to be converted into a
     * {@code CertPathValidatorException}, while retaining information
     * about the wrapped exception, which may be useful for debugging. The
     * detail message is set to ({@code cause==null ? null : cause.toString()})
     * (which typically contains the class and detail message of
     * cause).
     *
     * <p>
     * 创建包装指定的throwable的{@code CertPathValidatorException}。
     * 这允许任何异常转换为{@code CertPathValidatorException},同时保留关于被包装的异常的信息,这对于调试可能是有用的。
     * 详细消息设置为({@code cause == null?null：cause.toString()})(通常包含原因的类和详细消息)。
     * 
     * 
     * @param cause the cause (which is saved for later retrieval by the
     * {@link #getCause getCause()} method). (A {@code null} value is
     * permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public CertPathValidatorException(Throwable cause) {
        this((cause == null ? null : cause.toString()), cause);
    }

    /**
     * Creates a {@code CertPathValidatorException} with the specified
     * detail message and cause.
     *
     * <p>
     *  使用指定的详细消息和原因创建{@code CertPathValidatorException}。
     * 
     * 
     * @param msg the detail message
     * @param cause the cause (which is saved for later retrieval by the
     * {@link #getCause getCause()} method). (A {@code null} value is
     * permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public CertPathValidatorException(String msg, Throwable cause) {
        this(msg, cause, null, -1);
    }

    /**
     * Creates a {@code CertPathValidatorException} with the specified
     * detail message, cause, certification path, and index.
     *
     * <p>
     *  使用指定的详细消息,原因,认证路径和索引创建{@code CertPathValidatorException}。
     * 
     * 
     * @param msg the detail message (or {@code null} if none)
     * @param cause the cause (or {@code null} if none)
     * @param certPath the certification path that was in the process of
     * being validated when the error was encountered
     * @param index the index of the certificate in the certification path
     * that caused the error (or -1 if not applicable). Note that
     * the list of certificates in a {@code CertPath} is zero based.
     * @throws IndexOutOfBoundsException if the index is out of range
     * {@code (index < -1 || (certPath != null && index >=
     * certPath.getCertificates().size()) }
     * @throws IllegalArgumentException if {@code certPath} is
     * {@code null} and {@code index} is not -1
     */
    public CertPathValidatorException(String msg, Throwable cause,
            CertPath certPath, int index) {
        this(msg, cause, certPath, index, BasicReason.UNSPECIFIED);
    }

    /**
     * Creates a {@code CertPathValidatorException} with the specified
     * detail message, cause, certification path, index, and reason.
     *
     * <p>
     *  使用指定的详细消息,原因,认证路径,索引和原因创建{@code CertPathValidatorException}。
     * 
     * 
     * @param msg the detail message (or {@code null} if none)
     * @param cause the cause (or {@code null} if none)
     * @param certPath the certification path that was in the process of
     * being validated when the error was encountered
     * @param index the index of the certificate in the certification path
     * that caused the error (or -1 if not applicable). Note that
     * the list of certificates in a {@code CertPath} is zero based.
     * @param reason the reason the validation failed
     * @throws IndexOutOfBoundsException if the index is out of range
     * {@code (index < -1 || (certPath != null && index >=
     * certPath.getCertificates().size()) }
     * @throws IllegalArgumentException if {@code certPath} is
     * {@code null} and {@code index} is not -1
     * @throws NullPointerException if {@code reason} is {@code null}
     *
     * @since 1.7
     */
    public CertPathValidatorException(String msg, Throwable cause,
            CertPath certPath, int index, Reason reason) {
        super(msg, cause);
        if (certPath == null && index != -1) {
            throw new IllegalArgumentException();
        }
        if (index < -1 ||
            (certPath != null && index >= certPath.getCertificates().size())) {
            throw new IndexOutOfBoundsException();
        }
        if (reason == null) {
            throw new NullPointerException("reason can't be null");
        }
        this.certPath = certPath;
        this.index = index;
        this.reason = reason;
    }

    /**
     * Returns the certification path that was being validated when
     * the exception was thrown.
     *
     * <p>
     *  返回在抛出异常时正在验证的认证路径。
     * 
     * 
     * @return the {@code CertPath} that was being validated when
     * the exception was thrown (or {@code null} if not specified)
     */
    public CertPath getCertPath() {
        return this.certPath;
    }

    /**
     * Returns the index of the certificate in the certification path
     * that caused the exception to be thrown. Note that the list of
     * certificates in a {@code CertPath} is zero based. If no
     * index has been set, -1 is returned.
     *
     * <p>
     *  返回在导致抛出异常的认证路径中的证书的索引。请注意,{@code CertPath}中的证书列表为零。如果没有设置索引,则返回-1。
     * 
     * 
     * @return the index that has been set, or -1 if none has been set
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * Returns the reason that the validation failed. The reason is
     * associated with the index of the certificate returned by
     * {@link #getIndex}.
     *
     * <p>
     *  返回验证失败的原因。原因与{@link #getIndex}返回的证书的索引相关联。
     * 
     * 
     * @return the reason that the validation failed, or
     *    {@code BasicReason.UNSPECIFIED} if a reason has not been
     *    specified
     *
     * @since 1.7
     */
    public Reason getReason() {
        return this.reason;
    }

    private void readObject(ObjectInputStream stream)
        throws ClassNotFoundException, IOException {
        stream.defaultReadObject();
        if (reason == null) {
            reason = BasicReason.UNSPECIFIED;
        }
        if (certPath == null && index != -1) {
            throw new InvalidObjectException("certpath is null and index != -1");
        }
        if (index < -1 ||
            (certPath != null && index >= certPath.getCertificates().size())) {
            throw new InvalidObjectException("index out of range");
        }
    }

    /**
     * The reason the validation algorithm failed.
     *
     * <p>
     *  验证算法失败的原因。
     * 
     * 
     * @since 1.7
     */
    public static interface Reason extends java.io.Serializable { }


    /**
     * The BasicReason enumerates the potential reasons that a certification
     * path of any type may be invalid.
     *
     * <p>
     *  BasicReason列举了任何类型的认证路径可能无效的潜在原因。
     * 
     * 
     * @since 1.7
     */
    public static enum BasicReason implements Reason {
        /**
         * Unspecified reason.
         * <p>
         *  未指定的原因。
         * 
         */
        UNSPECIFIED,

        /**
         * The certificate is expired.
         * <p>
         *  证书已过期。
         * 
         */
        EXPIRED,

        /**
         * The certificate is not yet valid.
         * <p>
         *  证书尚未生效。
         * 
         */
        NOT_YET_VALID,

        /**
         * The certificate is revoked.
         * <p>
         *  证书被撤销。
         * 
         */
        REVOKED,

        /**
         * The revocation status of the certificate could not be determined.
         * <p>
         * 无法确定证书的吊销状态。
         * 
         */
        UNDETERMINED_REVOCATION_STATUS,

        /**
         * The signature is invalid.
         * <p>
         *  签名无效。
         * 
         */
        INVALID_SIGNATURE,

        /**
         * The public key or the signature algorithm has been constrained.
         * <p>
         *  公共密钥或签名算法已被约束。
         */
        ALGORITHM_CONSTRAINED
    }
}
