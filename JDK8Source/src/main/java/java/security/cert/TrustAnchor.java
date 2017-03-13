/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.io.IOException;
import java.security.PublicKey;

import javax.security.auth.x500.X500Principal;

import sun.security.x509.NameConstraintsExtension;
import sun.security.x509.X500Name;

/**
 * A trust anchor or most-trusted Certification Authority (CA).
 * <p>
 * This class represents a "most-trusted CA", which is used as a trust anchor
 * for validating X.509 certification paths. A most-trusted CA includes the
 * public key of the CA, the CA's name, and any constraints upon the set of
 * paths which may be validated using this key. These parameters can be
 * specified in the form of a trusted {@code X509Certificate} or as
 * individual parameters.
 * <p>
 * <b>Concurrent Access</b>
 * <p>All {@code TrustAnchor} objects must be immutable and
 * thread-safe. That is, multiple threads may concurrently invoke the
 * methods defined in this class on a single {@code TrustAnchor}
 * object (or more than one) with no ill effects. Requiring
 * {@code TrustAnchor} objects to be immutable and thread-safe
 * allows them to be passed around to various pieces of code without
 * worrying about coordinating access. This stipulation applies to all
 * public fields and methods of this class and any added or overridden
 * by subclasses.
 *
 * <p>
 *  信任锚或最可信的证书颁发机构(CA)
 * <p>
 *  此类表示"最可信CA",用作验证X509证书路径的信任锚最可信CA包括CA的公钥,CA的名称和对路径集合的任何约束,这可能使用此键验证这些参数可以以受信任的{@code X509Certificate}
 * 或单个参数的形式指定。
 * <p>
 * <b>并发访问</b> <p>所有{@code TrustAnchor}对象必须是不可变的,线程安全的。
 * 多线程可以同时调用这个类中定义的方法在一个{@code TrustAnchor}或多个)没有不良影响要求{@code TrustAnchor}对象不可变和线程安全允许它们传递到各种代码段,而不用担心协调
 * 访问此规定适用于所有公共字段和方法类和由子类添加或覆盖的任何类。
 * <b>并发访问</b> <p>所有{@code TrustAnchor}对象必须是不可变的,线程安全的。
 * 
 * 
 * @see PKIXParameters#PKIXParameters(Set)
 * @see PKIXBuilderParameters#PKIXBuilderParameters(Set, CertSelector)
 *
 * @since       1.4
 * @author      Sean Mullan
 */
public class TrustAnchor {

    private final PublicKey pubKey;
    private final String caName;
    private final X500Principal caPrincipal;
    private final X509Certificate trustedCert;
    private byte[] ncBytes;
    private NameConstraintsExtension nc;

    /**
     * Creates an instance of {@code TrustAnchor} with the specified
     * {@code X509Certificate} and optional name constraints, which
     * are intended to be used as additional constraints when validating
     * an X.509 certification path.
     * <p>
     * The name constraints are specified as a byte array. This byte array
     * should contain the DER encoded form of the name constraints, as they
     * would appear in the NameConstraints structure defined in
     * <a href="http://www.ietf.org/rfc/rfc3280">RFC 3280</a>
     * and X.509. The ASN.1 definition of this structure appears below.
     *
     * <pre>{@code
     *  NameConstraints ::= SEQUENCE {
     *       permittedSubtrees       [0]     GeneralSubtrees OPTIONAL,
     *       excludedSubtrees        [1]     GeneralSubtrees OPTIONAL }
     *
     *  GeneralSubtrees ::= SEQUENCE SIZE (1..MAX) OF GeneralSubtree
     *
     *  GeneralSubtree ::= SEQUENCE {
     *       base                    GeneralName,
     *       minimum         [0]     BaseDistance DEFAULT 0,
     *       maximum         [1]     BaseDistance OPTIONAL }
     *
     *  BaseDistance ::= INTEGER (0..MAX)
     *
     *  GeneralName ::= CHOICE {
     *       otherName                       [0]     OtherName,
     *       rfc822Name                      [1]     IA5String,
     *       dNSName                         [2]     IA5String,
     *       x400Address                     [3]     ORAddress,
     *       directoryName                   [4]     Name,
     *       ediPartyName                    [5]     EDIPartyName,
     *       uniformResourceIdentifier       [6]     IA5String,
     *       iPAddress                       [7]     OCTET STRING,
     *       registeredID                    [8]     OBJECT IDENTIFIER}
     * }</pre>
     * <p>
     * Note that the name constraints byte array supplied is cloned to protect
     * against subsequent modifications.
     *
     * <p>
     *  使用指定的{@code X509Certificate}和可选的名称约束创建{@code TrustAnchor}的实例,这些实例在验证X509认证路径时用作额外的约束
     * <p>
     * 名称约束被指定为字节数组此字节数组应包含名称约束的DER编码形式,因为它们将出现在<a href=\"http://wwwietforg/rfc/rfc3280\"> RFC中定义的NameConstr
     * aints结构中3280 </a>和X509此结构的ASN1定义如下所示。
     * 
     *  <pre> {@ code NameConstraints :: = SEQUENCE {permittedSubtrees [0] GeneralSubtrees OPTIONAL,excludedSubtrees [1] GeneralSubtrees OPTIONAL}
     * 。
     * 
     *  GeneralSubtrees :: =序列大小(1MAX)的GeneralSubtree
     * 
     *  GeneralSubtree :: = SEQUENCE {base GeneralName,minimum [0] BaseDistance DEFAULT 0,maximum [1] BaseDistance OPTIONAL}
     * 。
     * 
     *  BaseDistance :: = INTEGER(0MAX)
     * 
     * GeneralName :: = CHOICE {otherName [0] OtherName,rfc822Name [1] IA5String,dNSName [2] IA5String,x400Address [3] ORAddress,directoryName [4] Name,ediPartyName [5] EDIPartyName,uniformResourceIdentifier [6] IA5String,iPAddress [ 7] OCTET STRING,registeredID [8] OBJECT IDENTIFIER}
     * } </pre>。
     * <p>
     *  请注意,提供的名称约束字节数组被克隆以防止后续修改
     * 
     * 
     * @param trustedCert a trusted {@code X509Certificate}
     * @param nameConstraints a byte array containing the ASN.1 DER encoding of
     * a NameConstraints extension to be used for checking name constraints.
     * Only the value of the extension is included, not the OID or criticality
     * flag. Specify {@code null} to omit the parameter.
     * @throws IllegalArgumentException if the name constraints cannot be
     * decoded
     * @throws NullPointerException if the specified
     * {@code X509Certificate} is {@code null}
     */
    public TrustAnchor(X509Certificate trustedCert, byte[] nameConstraints)
    {
        if (trustedCert == null)
            throw new NullPointerException("the trustedCert parameter must " +
                "be non-null");
        this.trustedCert = trustedCert;
        this.pubKey = null;
        this.caName = null;
        this.caPrincipal = null;
        setNameConstraints(nameConstraints);
    }

    /**
     * Creates an instance of {@code TrustAnchor} where the
     * most-trusted CA is specified as an X500Principal and public key.
     * Name constraints are an optional parameter, and are intended to be used
     * as additional constraints when validating an X.509 certification path.
     * <p>
     * The name constraints are specified as a byte array. This byte array
     * contains the DER encoded form of the name constraints, as they
     * would appear in the NameConstraints structure defined in RFC 3280
     * and X.509. The ASN.1 notation for this structure is supplied in the
     * documentation for
     * {@link #TrustAnchor(X509Certificate, byte[])
     * TrustAnchor(X509Certificate trustedCert, byte[] nameConstraints) }.
     * <p>
     * Note that the name constraints byte array supplied here is cloned to
     * protect against subsequent modifications.
     *
     * <p>
     * 创建{@code TrustAnchor}的实例,其中将最受信任的CA指定为X500Principal和公用密钥名称约束是可选参数,用于在验证X509认证路径时用作附加约束
     * <p>
     *  名称约束被指定为字节数组此字节数组包含名称约束的DER编码形式,因为它们将出现在RFC 3280和X509中定义的NameConstraints结构中。
     * 此结构的ASN1符号在{@ link #TrustAnchor(X509Certificate,byte [])TrustAnchor(X509Certificate trustedCert,byte [] nameConstraints)}
     * 。
     *  名称约束被指定为字节数组此字节数组包含名称约束的DER编码形式,因为它们将出现在RFC 3280和X509中定义的NameConstraints结构中。
     * <p>
     *  请注意,此处提供的名称约束字节数组被克隆以防止后续修改
     * 
     * 
     * @param caPrincipal the name of the most-trusted CA as X500Principal
     * @param pubKey the public key of the most-trusted CA
     * @param nameConstraints a byte array containing the ASN.1 DER encoding of
     * a NameConstraints extension to be used for checking name constraints.
     * Only the value of the extension is included, not the OID or criticality
     * flag. Specify {@code null} to omit the parameter.
     * @throws NullPointerException if the specified {@code caPrincipal} or
     * {@code pubKey} parameter is {@code null}
     * @since 1.5
     */
    public TrustAnchor(X500Principal caPrincipal, PublicKey pubKey,
            byte[] nameConstraints) {
        if ((caPrincipal == null) || (pubKey == null)) {
            throw new NullPointerException();
        }
        this.trustedCert = null;
        this.caPrincipal = caPrincipal;
        this.caName = caPrincipal.getName();
        this.pubKey = pubKey;
        setNameConstraints(nameConstraints);
    }

    /**
     * Creates an instance of {@code TrustAnchor} where the
     * most-trusted CA is specified as a distinguished name and public key.
     * Name constraints are an optional parameter, and are intended to be used
     * as additional constraints when validating an X.509 certification path.
     * <p>
     * The name constraints are specified as a byte array. This byte array
     * contains the DER encoded form of the name constraints, as they
     * would appear in the NameConstraints structure defined in RFC 3280
     * and X.509. The ASN.1 notation for this structure is supplied in the
     * documentation for
     * {@link #TrustAnchor(X509Certificate, byte[])
     * TrustAnchor(X509Certificate trustedCert, byte[] nameConstraints) }.
     * <p>
     * Note that the name constraints byte array supplied here is cloned to
     * protect against subsequent modifications.
     *
     * <p>
     * 创建{@code TrustAnchor}的实例,其中最受信任的CA被指定为可分辨名称和公用密钥名称约束是可选参数,并且用于在验证X509认证路径时用作附加约束
     * <p>
     *  名称约束被指定为字节数组此字节数组包含名称约束的DER编码形式,因为它们将出现在RFC 3280和X509中定义的NameConstraints结构中。
     * 此结构的ASN1符号在{@ link #TrustAnchor(X509Certificate,byte [])TrustAnchor(X509Certificate trustedCert,byte [] nameConstraints)}
     * 。
     *  名称约束被指定为字节数组此字节数组包含名称约束的DER编码形式,因为它们将出现在RFC 3280和X509中定义的NameConstraints结构中。
     * <p>
     *  请注意,此处提供的名称约束字节数组被克隆以防止后续修改
     * 
     * 
     * @param caName the X.500 distinguished name of the most-trusted CA in
     * <a href="http://www.ietf.org/rfc/rfc2253.txt">RFC 2253</a>
     * {@code String} format
     * @param pubKey the public key of the most-trusted CA
     * @param nameConstraints a byte array containing the ASN.1 DER encoding of
     * a NameConstraints extension to be used for checking name constraints.
     * Only the value of the extension is included, not the OID or criticality
     * flag. Specify {@code null} to omit the parameter.
     * @throws IllegalArgumentException if the specified
     * {@code caName} parameter is empty {@code (caName.length() == 0)}
     * or incorrectly formatted or the name constraints cannot be decoded
     * @throws NullPointerException if the specified {@code caName} or
     * {@code pubKey} parameter is {@code null}
     */
    public TrustAnchor(String caName, PublicKey pubKey, byte[] nameConstraints)
    {
        if (pubKey == null)
            throw new NullPointerException("the pubKey parameter must be " +
                "non-null");
        if (caName == null)
            throw new NullPointerException("the caName parameter must be " +
                "non-null");
        if (caName.length() == 0)
            throw new IllegalArgumentException("the caName " +
                "parameter must be a non-empty String");
        // check if caName is formatted correctly
        this.caPrincipal = new X500Principal(caName);
        this.pubKey = pubKey;
        this.caName = caName;
        this.trustedCert = null;
        setNameConstraints(nameConstraints);
    }

    /**
     * Returns the most-trusted CA certificate.
     *
     * <p>
     * 返回最受信任的CA证书
     * 
     * 
     * @return a trusted {@code X509Certificate} or {@code null}
     * if the trust anchor was not specified as a trusted certificate
     */
    public final X509Certificate getTrustedCert() {
        return this.trustedCert;
    }

    /**
     * Returns the name of the most-trusted CA as an X500Principal.
     *
     * <p>
     *  以X500Principal返回最受信任的CA的名称
     * 
     * 
     * @return the X.500 distinguished name of the most-trusted CA, or
     * {@code null} if the trust anchor was not specified as a trusted
     * public key and name or X500Principal pair
     * @since 1.5
     */
    public final X500Principal getCA() {
        return this.caPrincipal;
    }

    /**
     * Returns the name of the most-trusted CA in RFC 2253 {@code String}
     * format.
     *
     * <p>
     *  返回RFC 2253 {@code String}格式中最受信任的CA的名称
     * 
     * 
     * @return the X.500 distinguished name of the most-trusted CA, or
     * {@code null} if the trust anchor was not specified as a trusted
     * public key and name or X500Principal pair
     */
    public final String getCAName() {
        return this.caName;
    }

    /**
     * Returns the public key of the most-trusted CA.
     *
     * <p>
     *  返回最受信任的CA的公钥
     * 
     * 
     * @return the public key of the most-trusted CA, or {@code null}
     * if the trust anchor was not specified as a trusted public key and name
     * or X500Principal pair
     */
    public final PublicKey getCAPublicKey() {
        return this.pubKey;
    }

    /**
     * Decode the name constraints and clone them if not null.
     * <p>
     *  解码名称约束,如果非空则克隆它们
     * 
     */
    private void setNameConstraints(byte[] bytes) {
        if (bytes == null) {
            ncBytes = null;
            nc = null;
        } else {
            ncBytes = bytes.clone();
            // validate DER encoding
            try {
                nc = new NameConstraintsExtension(Boolean.FALSE, bytes);
            } catch (IOException ioe) {
                IllegalArgumentException iae =
                    new IllegalArgumentException(ioe.getMessage());
                iae.initCause(ioe);
                throw iae;
            }
        }
    }

    /**
     * Returns the name constraints parameter. The specified name constraints
     * are associated with this trust anchor and are intended to be used
     * as additional constraints when validating an X.509 certification path.
     * <p>
     * The name constraints are returned as a byte array. This byte array
     * contains the DER encoded form of the name constraints, as they
     * would appear in the NameConstraints structure defined in RFC 3280
     * and X.509. The ASN.1 notation for this structure is supplied in the
     * documentation for
     * {@link #TrustAnchor(X509Certificate, byte[])
     * TrustAnchor(X509Certificate trustedCert, byte[] nameConstraints) }.
     * <p>
     * Note that the byte array returned is cloned to protect against
     * subsequent modifications.
     *
     * <p>
     *  返回名称约束参数指定的名称约束与此信任锚相关联,并且用于在验证X509认证路径时用作附加约束
     * <p>
     * 名称约束作为字节数组返回此字节数组包含名称约束的DER编码形式,因为它们将出现在RFC 3280和X509中定义的NameConstraints结构中。
     * 此结构的ASN1符号在{@ link #TrustAnchor(X509Certificate,byte [])TrustAnchor(X509Certificate trustedCert,byte [] nameConstraints)}
     * 。
     * 名称约束作为字节数组返回此字节数组包含名称约束的DER编码形式,因为它们将出现在RFC 3280和X509中定义的NameConstraints结构中。
     * 
     * @return a byte array containing the ASN.1 DER encoding of
     *         a NameConstraints extension used for checking name constraints,
     *         or {@code null} if not set.
     */
    public final byte [] getNameConstraints() {
        return ncBytes == null ? null : ncBytes.clone();
    }

    /**
     * Returns a formatted string describing the {@code TrustAnchor}.
     *
     * <p>
     * <p>
     *  注意,返回的字节数组被克隆以防止后续修改
     * 
     * 
     * @return a formatted string describing the {@code TrustAnchor}
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[\n");
        if (pubKey != null) {
            sb.append("  Trusted CA Public Key: " + pubKey.toString() + "\n");
            sb.append("  Trusted CA Issuer Name: "
                + String.valueOf(caName) + "\n");
        } else {
            sb.append("  Trusted CA cert: " + trustedCert.toString() + "\n");
        }
        if (nc != null)
            sb.append("  Name Constraints: " + nc.toString() + "\n");
        return sb.toString();
    }
}
