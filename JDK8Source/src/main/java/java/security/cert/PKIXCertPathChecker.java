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

import java.util.Collection;
import java.util.Set;

/**
 * An abstract class that performs one or more checks on an
 * {@code X509Certificate}.
 *
 * <p>A concrete implementation of the {@code PKIXCertPathChecker} class
 * can be created to extend the PKIX certification path validation algorithm.
 * For example, an implementation may check for and process a critical private
 * extension of each certificate in a certification path.
 *
 * <p>Instances of {@code PKIXCertPathChecker} are passed as parameters
 * using the {@link PKIXParameters#setCertPathCheckers setCertPathCheckers}
 * or {@link PKIXParameters#addCertPathChecker addCertPathChecker} methods
 * of the {@code PKIXParameters} and {@code PKIXBuilderParameters}
 * class. Each of the {@code PKIXCertPathChecker}s {@link #check check}
 * methods will be called, in turn, for each certificate processed by a PKIX
 * {@code CertPathValidator} or {@code CertPathBuilder}
 * implementation.
 *
 * <p>A {@code PKIXCertPathChecker} may be called multiple times on
 * successive certificates in a certification path. Concrete subclasses
 * are expected to maintain any internal state that may be necessary to
 * check successive certificates. The {@link #init init} method is used
 * to initialize the internal state of the checker so that the certificates
 * of a new certification path may be checked. A stateful implementation
 * <b>must</b> override the {@link #clone clone} method if necessary in
 * order to allow a PKIX {@code CertPathBuilder} to efficiently
 * backtrack and try other paths. In these situations, the
 * {@code CertPathBuilder} is able to restore prior path validation
 * states by restoring the cloned {@code PKIXCertPathChecker}s.
 *
 * <p>The order in which the certificates are presented to the
 * {@code PKIXCertPathChecker} may be either in the forward direction
 * (from target to most-trusted CA) or in the reverse direction (from
 * most-trusted CA to target). A {@code PKIXCertPathChecker} implementation
 * <b>must</b> support reverse checking (the ability to perform its checks when
 * it is presented with certificates in the reverse direction) and <b>may</b>
 * support forward checking (the ability to perform its checks when it is
 * presented with certificates in the forward direction). The
 * {@link #isForwardCheckingSupported isForwardCheckingSupported} method
 * indicates whether forward checking is supported.
 * <p>
 * Additional input parameters required for executing the check may be
 * specified through constructors of concrete implementations of this class.
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
 *  对{@code X509Certificate}执行一个或多个检查的抽象类。
 * 
 *  <p>可以创建{@code PKIXCertPathChecker}类的具体实现,以扩展PKIX认证路径验证算法。例如,实现可以检查和处理认证路径中的每个证书的关键私有扩展。
 * 
 *  <p> {@code PKIXCertPathChecker}的实例使用{@code PKIXParameters}和{@code PKIXBuilderParameters}类的{@link PKIXParameters#setCertPathCheckers setCertPathCheckers}
 * 或{@link PKIXParameters#addCertPathChecker addCertPathChecker}方法作为参数传递。
 * 对于由PKIX {@code CertPathValidator}或{@code CertPathBuilder}实施处理的每个证书,每个{@code PKIXCertPathChecker}的{@link #check检查}
 * 方法将被调用。
 * 
 * <p> {@code PKIXCertPathChecker}可能在认证路径中的连续证书上被多次调用。预期具体子类保持检查连续证书所必需的任何内部状态。
 *  {@link #init init}方法用于初始化检查设备的内部状态,以便可以检查新的认证路径的证书。
 * 有状态实现<b>必须</b>覆盖{@link #clone clone}方法,以便允许PKIX {@code CertPathBuilder}有效地回溯并尝试其他路径。
 * 在这些情况下,{@code CertPathBuilder}能够通过恢复克隆的{@code PKIXCertPathChecker}来恢复先前的路径验证状态。
 * 
 *  <p>证书呈现给{@code PKIXCertPathChecker}的顺序可以是正向(从目标到最可信的CA)或反向(从最可信的CA到目标)。
 *  {@code PKIXCertPathChecker}实施<b>必须</b>支持反向检查(当证书以相反方向显示时执行检查的能力),<b>可</b>支持前向检查在向前方向上呈现证书时执行检查的能力)。
 *  {@link #isForwardCheckingSupported isForwardCheckingSupported}方法表示是否支持转发检查。
 * <p>
 * 执行检查所需的附加输入参数可以通过该类的具体实现的构造器来指定。
 * <p>
 *  <b>并行访问</b>
 * <p>
 *  除非另有说明,否则此类中定义的方法不是线程安全的。需要并发访问单个对象的多个线程应在它们之间同步并提供必要的锁定。每个操作单独对象的多个线程不需要同步。
 * 
 * 
 * @see PKIXParameters
 * @see PKIXBuilderParameters
 *
 * @since       1.4
 * @author      Yassir Elley
 * @author      Sean Mullan
 */
public abstract class PKIXCertPathChecker
    implements CertPathChecker, Cloneable {

    /**
     * Default constructor.
     * <p>
     *  默认构造函数。
     * 
     */
    protected PKIXCertPathChecker() {}

    /**
     * Initializes the internal state of this {@code PKIXCertPathChecker}.
     * <p>
     * The {@code forward} flag specifies the order that
     * certificates will be passed to the {@link #check check} method
     * (forward or reverse). A {@code PKIXCertPathChecker} <b>must</b>
     * support reverse checking and <b>may</b> support forward checking.
     *
     * <p>
     *  初始化此{@code PKIXCertPathChecker}的内部状态。
     * <p>
     *  {@code forward}标志指定证书传递到{@link #check check}方法的顺序(正向或反向)。
     *  {@code PKIXCertPathChecker} <b>必须</b>支持反向检查,<b>可</b>支持转发检查。
     * 
     * 
     * @param forward the order that certificates are presented to
     * the {@code check} method. If {@code true}, certificates
     * are presented from target to most-trusted CA (forward); if
     * {@code false}, from most-trusted CA to target (reverse).
     * @throws CertPathValidatorException if this
     * {@code PKIXCertPathChecker} is unable to check certificates in
     * the specified order; it should never be thrown if the forward flag
     * is false since reverse checking must be supported
     */
    @Override
    public abstract void init(boolean forward)
        throws CertPathValidatorException;

    /**
     * Indicates if forward checking is supported. Forward checking refers
     * to the ability of the {@code PKIXCertPathChecker} to perform
     * its checks when certificates are presented to the {@code check}
     * method in the forward direction (from target to most-trusted CA).
     *
     * <p>
     *  指示是否支持转发检查。转发检查是指{@code PKIXCertPathChecker}在向前(从目标到最可信CA)向{@code check}方法提交证书时执行检查的能力。
     * 
     * 
     * @return {@code true} if forward checking is supported,
     * {@code false} otherwise
     */
    @Override
    public abstract boolean isForwardCheckingSupported();

    /**
     * Returns an immutable {@code Set} of X.509 certificate extensions
     * that this {@code PKIXCertPathChecker} supports (i.e. recognizes, is
     * able to process), or {@code null} if no extensions are supported.
     * <p>
     * Each element of the set is a {@code String} representing the
     * Object Identifier (OID) of the X.509 extension that is supported.
     * The OID is represented by a set of nonnegative integers separated by
     * periods.
     * <p>
     * All X.509 certificate extensions that a {@code PKIXCertPathChecker}
     * might possibly be able to process should be included in the set.
     *
     * <p>
     *  返回此{@code PKIXCertPathChecker}支持(即识别为能够处理)的X.509证书扩展的不可修改的{@code Set},如果不支持扩展,则返回{@code null}。
     * <p>
     *  集合的每个元素是一个{@code String},表示支持的X.509扩展的对象标识符(OID)。 OID由一组由周期分隔的非负整数表示。
     * <p>
     * {@code PKIXCertPathChecker}可能能够处理的所有X.509证书扩展名都应包含在集合中。
     * 
     * 
     * @return an immutable {@code Set} of X.509 extension OIDs (in
     * {@code String} format) supported by this
     * {@code PKIXCertPathChecker}, or {@code null} if no
     * extensions are supported
     */
    public abstract Set<String> getSupportedExtensions();

    /**
     * Performs the check(s) on the specified certificate using its internal
     * state and removes any critical extensions that it processes from the
     * specified collection of OID strings that represent the unresolved
     * critical extensions. The certificates are presented in the order
     * specified by the {@code init} method.
     *
     * <p>
     *  使用其内部状态对指定的证书执行检查,并从表示未解决的关键扩展的指定OID字符串集合中删除它处理的任何关键扩展。证书按{@code init}方法指定的顺序显示。
     * 
     * 
     * @param cert the {@code Certificate} to be checked
     * @param unresolvedCritExts a {@code Collection} of OID strings
     * representing the current set of unresolved critical extensions
     * @exception CertPathValidatorException if the specified certificate does
     * not pass the check
     */
    public abstract void check(Certificate cert,
            Collection<String> unresolvedCritExts)
            throws CertPathValidatorException;

    /**
     * {@inheritDoc}
     *
     * <p>This implementation calls
     * {@code check(cert, java.util.Collections.<String>emptySet())}.
     * <p>
     *  {@inheritDoc}
     * 
     *  <p>此实现调用{@code check(cert,java.util.Collections。<String> emptySet())}。
     * 
     */
    @Override
    public void check(Certificate cert) throws CertPathValidatorException {
        check(cert, java.util.Collections.<String>emptySet());
    }

    /**
     * Returns a clone of this object. Calls the {@code Object.clone()}
     * method.
     * All subclasses which maintain state must support and
     * override this method, if necessary.
     *
     * <p>
     * 
     * @return a copy of this {@code PKIXCertPathChecker}
     */
    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            /* Cannot happen */
            throw new InternalError(e.toString(), e);
        }
    }
}
