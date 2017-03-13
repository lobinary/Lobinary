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

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.util.Set;

/**
 * Parameters used as input for the PKIX {@code CertPathBuilder}
 * algorithm.
 * <p>
 * A PKIX {@code CertPathBuilder} uses these parameters to {@link
 * CertPathBuilder#build build} a {@code CertPath} which has been
 * validated according to the PKIX certification path validation algorithm.
 *
 * <p>To instantiate a {@code PKIXBuilderParameters} object, an
 * application must specify one or more <i>most-trusted CAs</i> as defined by
 * the PKIX certification path validation algorithm. The most-trusted CA
 * can be specified using one of two constructors. An application
 * can call {@link #PKIXBuilderParameters(Set, CertSelector)
 * PKIXBuilderParameters(Set, CertSelector)}, specifying a
 * {@code Set} of {@code TrustAnchor} objects, each of which
 * identifies a most-trusted CA. Alternatively, an application can call
 * {@link #PKIXBuilderParameters(KeyStore, CertSelector)
 * PKIXBuilderParameters(KeyStore, CertSelector)}, specifying a
 * {@code KeyStore} instance containing trusted certificate entries, each
 * of which will be considered as a most-trusted CA.
 *
 * <p>In addition, an application must specify constraints on the target
 * certificate that the {@code CertPathBuilder} will attempt
 * to build a path to. The constraints are specified as a
 * {@code CertSelector} object. These constraints should provide the
 * {@code CertPathBuilder} with enough search criteria to find the target
 * certificate. Minimal criteria for an {@code X509Certificate} usually
 * include the subject name and/or one or more subject alternative names.
 * If enough criteria is not specified, the {@code CertPathBuilder}
 * may throw a {@code CertPathBuilderException}.
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
 *  用作PKIX {@code CertPathBuilder}算法输入的参数
 * <p>
 *  PKIX {@code CertPathBuilder}使用这些参数{@link CertPathBuilder#build build}已经根据PKIX认证路径验证算法验证的{@code CertPath}
 * 。
 * 
 * <p>要实例化{@code PKIXBuilderParameters}对象,应用程序必须指定一个或多个由PKIX认证路径验证算法定义的最可信CA. </i>最可信的CA可以使用一个一个应用程序可以调用
 * {@link #PKIXBuilderParameters(Set,CertSelector)PKIXBuilderParameters(Set,CertSelector)},指定{@code Set}
 *  {@code TrustAnchor}对象,每个对象都标识一个最可信的CA ,应用程序可以调用{@link #PKIXBuilderParameters(KeyStore,CertSelector)PKIXBuilderParameters(KeyStore,CertSelector)}
 * ,指定包含受信任证书条目的{@code KeyStore}实例,每个条目都将被视为最受信任的CA。
 * 
 * <p>此外,应用程序必须指定目标证书上的约束,{@code CertPathBuilder}将尝试构建一个路径。
 * 约束被指定为{@code CertSelector}对象这些约束应提供{@code CertPathBuilder }具有足够的搜索条件以查找目标证书{@code X509Certificate}的最小
 * 标准通常包括主题名称和/或一个或多个主题备用名称如果未指定足够的条件,{@code CertPathBuilder}可能会抛出{ @code CertPathBuilderException}。
 * <p>此外,应用程序必须指定目标证书上的约束,{@code CertPathBuilder}将尝试构建一个路径。
 * <p>
 *  <b>并行访问</b>
 * <p>
 * 除非另有说明,否则此类中定义的方法不是线程安全的需要并发访问单个对象的多个线程应在它们之间同步并提供必要的锁定多个线程,每个操作单独的对象不需要同步
 * 
 * 
 * @see CertPathBuilder
 *
 * @since       1.4
 * @author      Sean Mullan
 */
public class PKIXBuilderParameters extends PKIXParameters {

    private int maxPathLength = 5;

    /**
     * Creates an instance of {@code PKIXBuilderParameters} with
     * the specified {@code Set} of most-trusted CAs.
     * Each element of the set is a {@link TrustAnchor TrustAnchor}.
     *
     * <p>Note that the {@code Set} is copied to protect against
     * subsequent modifications.
     *
     * <p>
     *  使用指定的{@code Set}最受信任的CA创建{@code PKIXBuilderParameters}的实例集合的每个元素是{@link TrustAnchor TrustAnchor}
     * 
     *  <p>请注意,系统会复制{@code Set}以防止后续修改
     * 
     * 
     * @param trustAnchors a {@code Set} of {@code TrustAnchor}s
     * @param targetConstraints a {@code CertSelector} specifying the
     * constraints on the target certificate
     * @throws InvalidAlgorithmParameterException if {@code trustAnchors}
     * is empty {@code (trustAnchors.isEmpty() == true)}
     * @throws NullPointerException if {@code trustAnchors} is
     * {@code null}
     * @throws ClassCastException if any of the elements of
     * {@code trustAnchors} are not of type
     * {@code java.security.cert.TrustAnchor}
     */
    public PKIXBuilderParameters(Set<TrustAnchor> trustAnchors, CertSelector
        targetConstraints) throws InvalidAlgorithmParameterException
    {
        super(trustAnchors);
        setTargetCertConstraints(targetConstraints);
    }

    /**
     * Creates an instance of {@code PKIXBuilderParameters} that
     * populates the set of most-trusted CAs from the trusted
     * certificate entries contained in the specified {@code KeyStore}.
     * Only keystore entries that contain trusted {@code X509Certificate}s
     * are considered; all other certificate types are ignored.
     *
     * <p>
     * 创建{@code PKIXBuilderParameters}的实例,该实例从指定的{@code KeyStore}中包含的受信任证书条目填充一组最受信任的CA.仅考虑包含受信任的{@code X509Certificate}
     * 的密钥库条目;所有其他证书类型将被忽略。
     * 
     * 
     * @param keystore a {@code KeyStore} from which the set of
     * most-trusted CAs will be populated
     * @param targetConstraints a {@code CertSelector} specifying the
     * constraints on the target certificate
     * @throws KeyStoreException if {@code keystore} has not been
     * initialized
     * @throws InvalidAlgorithmParameterException if {@code keystore} does
     * not contain at least one trusted certificate entry
     * @throws NullPointerException if {@code keystore} is
     * {@code null}
     */
    public PKIXBuilderParameters(KeyStore keystore,
        CertSelector targetConstraints)
        throws KeyStoreException, InvalidAlgorithmParameterException
    {
        super(keystore);
        setTargetCertConstraints(targetConstraints);
    }

    /**
     * Sets the value of the maximum number of non-self-issued intermediate
     * certificates that may exist in a certification path. A certificate
     * is self-issued if the DNs that appear in the subject and issuer
     * fields are identical and are not empty. Note that the last certificate
     * in a certification path is not an intermediate certificate, and is not
     * included in this limit. Usually the last certificate is an end entity
     * certificate, but it can be a CA certificate. A PKIX
     * {@code CertPathBuilder} instance must not build
     * paths longer than the length specified.
     *
     * <p> A value of 0 implies that the path can only contain
     * a single certificate. A value of -1 implies that the
     * path length is unconstrained (i.e. there is no maximum).
     * The default maximum path length, if not specified, is 5.
     * Setting a value less than -1 will cause an exception to be thrown.
     *
     * <p> If any of the CA certificates contain the
     * {@code BasicConstraintsExtension}, the value of the
     * {@code pathLenConstraint} field of the extension overrides
     * the maximum path length parameter whenever the result is a
     * certification path of smaller length.
     *
     * <p>
     * 设置可能存在于认证路径中的非自颁发中间证书的最大数值如果主体和颁发者字段中显示的DN相同且不为空,则证书是自签发的注意,最后一个证书在证书路径中不是中间证书,并且不包括在此限制中通常最后一个证书是终端实
     * 体证书,但它可以是CA证书PKIX {@code CertPathBuilder}实例必须不能构建长度超过长度指定。
     * 
     * <p>值为0表示路径只能包含单个证书值为-1表示路径长度不受约束(即没有最大值)默认最大路径长度(如果未指定)为5设置值小于-1将导致抛出异常
     * 
     *  <p>如果任何CA证书包含{@code BasicConstraintsExtension},则只要结果是较小长度的证书路径,扩展的{@code pathLenConstraint}字段的值就会覆盖最
     * 大路径长度参数。
     * 
     * @param maxPathLength the maximum number of non-self-issued intermediate
     *  certificates that may exist in a certification path
     * @throws InvalidParameterException if {@code maxPathLength} is set
     *  to a value less than -1
     *
     * @see #getMaxPathLength
     */
    public void setMaxPathLength(int maxPathLength) {
        if (maxPathLength < -1) {
            throw new InvalidParameterException("the maximum path "
                + "length parameter can not be less than -1");
        }
        this.maxPathLength = maxPathLength;
    }

    /**
     * Returns the value of the maximum number of intermediate non-self-issued
     * certificates that may exist in a certification path. See
     * the {@link #setMaxPathLength} method for more details.
     *
     * <p>
     * 
     * 
     * @return the maximum number of non-self-issued intermediate certificates
     *  that may exist in a certification path, or -1 if there is no limit
     *
     * @see #setMaxPathLength
     */
    public int getMaxPathLength() {
        return maxPathLength;
    }

    /**
     * Returns a formatted string describing the parameters.
     *
     * <p>
     *  返回认证路径中可能存在的中间非自颁发证书的最大数值。有关更多详细信息,请参阅{@link #setMaxPathLength}方法
     * 
     * 
     * @return a formatted string describing the parameters
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[\n");
        sb.append(super.toString());
        sb.append("  Maximum Path Length: " + maxPathLength + "\n");
        sb.append("]\n");
        return sb.toString();
    }
}
