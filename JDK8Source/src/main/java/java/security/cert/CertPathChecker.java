/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
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

/**
 * <p>Performs one or more checks on each {@code Certificate} of a
 * {@code CertPath}.
 *
 * <p>A {@code CertPathChecker} implementation is typically created to extend
 * a certification path validation algorithm. For example, an implementation
 * may check for and process a critical private extension of each certificate
 * in a certification path.
 *
 * <p>
 *  <p>对{@code CertPath}的每个{@code Certificate}执行一个或多个检查。
 * 
 *  <p>通常会创建一个{@code CertPathChecker}实现来扩展认证路径验证算法。例如,实现可以检查和处理认证路径中的每个证书的关键私有扩展。
 * 
 * 
 * @since 1.8
 */
public interface CertPathChecker {

    /**
     * Initializes the internal state of this {@code CertPathChecker}.
     *
     * <p>The {@code forward} flag specifies the order that certificates will
     * be passed to the {@link #check check} method (forward or reverse).
     *
     * <p>
     *  初始化此{@code CertPathChecker}的内部状态。
     * 
     *  <p> {@code forward}标志指定证书传递到{@link #check check}方法的顺序(正向或反向)。
     * 
     * 
     * @param forward the order that certificates are presented to the
     *        {@code check} method. If {@code true}, certificates are
     *        presented from target to trust anchor (forward); if
     *        {@code false}, from trust anchor to target (reverse).
     * @throws CertPathValidatorException if this {@code CertPathChecker} is
     *         unable to check certificates in the specified order
     */
    void init(boolean forward) throws CertPathValidatorException;

    /**
     * Indicates if forward checking is supported. Forward checking refers
     * to the ability of the {@code CertPathChecker} to perform its checks
     * when certificates are presented to the {@code check} method in the
     * forward direction (from target to trust anchor).
     *
     * <p>
     *  指示是否支持转发检查。转发检查是指{@code CertPathChecker}在向前(从目标到信任锚点)向{@code check}方法提交证书时执行检查的能力。
     * 
     * 
     * @return {@code true} if forward checking is supported, {@code false}
     *         otherwise
     */
    boolean isForwardCheckingSupported();

    /**
     * Performs the check(s) on the specified certificate using its internal
     * state. The certificates are presented in the order specified by the
     * {@code init} method.
     *
     * <p>
     *  使用其内部状态对指定的证书执行检查。证书按{@code init}方法指定的顺序显示。
     * 
     * @param cert the {@code Certificate} to be checked
     * @throws CertPathValidatorException if the specified certificate does
     *         not pass the check
     */
    void check(Certificate cert) throws CertPathValidatorException;
}
