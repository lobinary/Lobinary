/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.security.Principal;
import java.security.Provider;
import java.security.PublicKey;
import javax.security.auth.x500.X500Principal;

import java.math.BigInteger;
import java.util.Date;
import java.util.Set;
import java.util.Arrays;

import sun.security.x509.X509CRLImpl;

/**
 * <p>
 * Abstract class for an X.509 Certificate Revocation List (CRL).
 * A CRL is a time-stamped list identifying revoked certificates.
 * It is signed by a Certificate Authority (CA) and made freely
 * available in a public repository.
 *
 * <p>Each revoked certificate is
 * identified in a CRL by its certificate serial number. When a
 * certificate-using system uses a certificate (e.g., for verifying a
 * remote user's digital signature), that system not only checks the
 * certificate signature and validity but also acquires a suitably-
 * recent CRL and checks that the certificate serial number is not on
 * that CRL.  The meaning of "suitably-recent" may vary with local
 * policy, but it usually means the most recently-issued CRL.  A CA
 * issues a new CRL on a regular periodic basis (e.g., hourly, daily, or
 * weekly).  Entries are added to CRLs as revocations occur, and an
 * entry may be removed when the certificate expiration date is reached.
 * <p>
 * The X.509 v2 CRL format is described below in ASN.1:
 * <pre>
 * CertificateList  ::=  SEQUENCE  {
 *     tbsCertList          TBSCertList,
 *     signatureAlgorithm   AlgorithmIdentifier,
 *     signature            BIT STRING  }
 * </pre>
 * <p>
 * More information can be found in
 * <a href="http://www.ietf.org/rfc/rfc3280.txt">RFC 3280: Internet X.509
 * Public Key Infrastructure Certificate and CRL Profile</a>.
 * <p>
 * The ASN.1 definition of {@code tbsCertList} is:
 * <pre>
 * TBSCertList  ::=  SEQUENCE  {
 *     version                 Version OPTIONAL,
 *                             -- if present, must be v2
 *     signature               AlgorithmIdentifier,
 *     issuer                  Name,
 *     thisUpdate              ChoiceOfTime,
 *     nextUpdate              ChoiceOfTime OPTIONAL,
 *     revokedCertificates     SEQUENCE OF SEQUENCE  {
 *         userCertificate         CertificateSerialNumber,
 *         revocationDate          ChoiceOfTime,
 *         crlEntryExtensions      Extensions OPTIONAL
 *                                 -- if present, must be v2
 *         }  OPTIONAL,
 *     crlExtensions           [0]  EXPLICIT Extensions OPTIONAL
 *                                  -- if present, must be v2
 *     }
 * </pre>
 * <p>
 * CRLs are instantiated using a certificate factory. The following is an
 * example of how to instantiate an X.509 CRL:
 * <pre>{@code
 * try (InputStream inStream = new FileInputStream("fileName-of-crl")) {
 *     CertificateFactory cf = CertificateFactory.getInstance("X.509");
 *     X509CRL crl = (X509CRL)cf.generateCRL(inStream);
 * }
 * }</pre>
 *
 * <p>
 * <p>
 *  X.509证书吊销列表(CRL)的抽象类。 CRL是标识撤销的证书的带时间戳的列表。它由证书颁发机构(CA)签名并在公共存储库中免费提供。
 * 
 *  <p>每个撤销的证书在CRL中通过其证书序列号进行标识。
 * 当证书使用系统使用证书(例如,用于验证远程用户的数字签名)时,该系统不仅检查证书签名和有效性,而且还获取适当最近的CRL,并检查证书序列号不在CRL。
 *  "适当地最近"的含义可能随着本地策略而变化,但它通常意味着最近发布的CRL。 CA定期(例如,每小时,每天或每周)发布新的CRL。
 * 当撤消发生时,条目将添加到CRL中,并且在达到证书到期日期时可能会删除条目。
 * <p>
 *  X.509 v2 CRL格式在ASN.1中描述如下：
 * <pre>
 *  CertificateList :: = SEQUENCE {tbsCertList TBSCertList,signatureAlgorithm AlgorithmIdentifier,signature BIT STRING}
 * 。
 * </pre>
 * <p>
 *  有关详情,请参阅<a href="http://www.ietf.org/rfc/rfc3280.txt"> RFC 3280：Internet X.509公钥基础结构证书和CRL配置文件</a>。
 * <p>
 *  {@code tbsCertList}的ASN.1定义是：
 * <pre>
 * TBSCertList :: = SEQUENCE {版本版本可选,如果存在,必须是v2签名AlgorithmIdentifier,issuer名称,thisUpdate ChoiceOfTime,nextUpdate ChoiceOfTime可选,revokedCertificates SEQUENCE OF SEQUENCE {userCertificate CertificateSerialNumber,revocationDate ChoiceOfTime,crlEntryExtensions Extensions可选 - 如果存在,必须是v2}
 * 可选,crlExtensions [0] EXPLICIT扩展可选 - 如果存在,必须是v2}。
 * </pre>
 * <p>
 *  CRL使用证书工厂实例化。
 * 下面是如何实例化X.509 CRL的示例：<pre> {@ code try(InputStream inStream = new FileInputStream("fileName-of-crl")){CertificateFactory cf = CertificateFactory.getInstance("X.509 "); X509CRL crl =(X509CRL)cf.enerateCRL(inStream); }
 * } </pre>。
 *  CRL使用证书工厂实例化。
 * 
 * 
 * @author Hemma Prafullchandra
 *
 *
 * @see CRL
 * @see CertificateFactory
 * @see X509Extension
 */

public abstract class X509CRL extends CRL implements X509Extension {

    private transient X500Principal issuerPrincipal;

    /**
     * Constructor for X.509 CRLs.
     * <p>
     *  X.509 CRL的构造函数。
     * 
     */
    protected X509CRL() {
        super("X.509");
    }

    /**
     * Compares this CRL for equality with the given
     * object. If the {@code other} object is an
     * {@code instanceof} {@code X509CRL}, then
     * its encoded form is retrieved and compared with the
     * encoded form of this CRL.
     *
     * <p>
     *  比较此CRL与给定对象的相等性。如果{@code other}对象是{@code X509CRL}的{@code instanceof},则会检索其编码形式,并与此CRL的编码形式进行比较。
     * 
     * 
     * @param other the object to test for equality with this CRL.
     *
     * @return true iff the encoded forms of the two CRLs
     * match, false otherwise.
     */
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof X509CRL)) {
            return false;
        }
        try {
            byte[] thisCRL = X509CRLImpl.getEncodedInternal(this);
            byte[] otherCRL = X509CRLImpl.getEncodedInternal((X509CRL)other);

            return Arrays.equals(thisCRL, otherCRL);
        } catch (CRLException e) {
            return false;
        }
    }

    /**
     * Returns a hashcode value for this CRL from its
     * encoded form.
     *
     * <p>
     *  从其编码形式返回此CRL的哈希码值。
     * 
     * 
     * @return the hashcode value.
     */
    public int hashCode() {
        int retval = 0;
        try {
            byte[] crlData = X509CRLImpl.getEncodedInternal(this);
            for (int i = 1; i < crlData.length; i++) {
                 retval += crlData[i] * i;
            }
            return retval;
        } catch (CRLException e) {
            return retval;
        }
    }

    /**
     * Returns the ASN.1 DER-encoded form of this CRL.
     *
     * <p>
     *  返回此CRL的ASN.1 DER编码形式。
     * 
     * 
     * @return the encoded form of this certificate
     * @exception CRLException if an encoding error occurs.
     */
    public abstract byte[] getEncoded()
        throws CRLException;

    /**
     * Verifies that this CRL was signed using the
     * private key that corresponds to the given public key.
     *
     * <p>
     *  验证此CRL是否使用与给定公钥相对应的私钥进行签名。
     * 
     * 
     * @param key the PublicKey used to carry out the verification.
     *
     * @exception NoSuchAlgorithmException on unsupported signature
     * algorithms.
     * @exception InvalidKeyException on incorrect key.
     * @exception NoSuchProviderException if there's no default provider.
     * @exception SignatureException on signature errors.
     * @exception CRLException on encoding errors.
     */
    public abstract void verify(PublicKey key)
        throws CRLException,  NoSuchAlgorithmException,
        InvalidKeyException, NoSuchProviderException,
        SignatureException;

    /**
     * Verifies that this CRL was signed using the
     * private key that corresponds to the given public key.
     * This method uses the signature verification engine
     * supplied by the given provider.
     *
     * <p>
     * 验证此CRL是否使用与给定公钥相对应的私钥进行签名。此方法使用由给定提供者提供的签名验证引擎。
     * 
     * 
     * @param key the PublicKey used to carry out the verification.
     * @param sigProvider the name of the signature provider.
     *
     * @exception NoSuchAlgorithmException on unsupported signature
     * algorithms.
     * @exception InvalidKeyException on incorrect key.
     * @exception NoSuchProviderException on incorrect provider.
     * @exception SignatureException on signature errors.
     * @exception CRLException on encoding errors.
     */
    public abstract void verify(PublicKey key, String sigProvider)
        throws CRLException, NoSuchAlgorithmException,
        InvalidKeyException, NoSuchProviderException,
        SignatureException;

    /**
     * Verifies that this CRL was signed using the
     * private key that corresponds to the given public key.
     * This method uses the signature verification engine
     * supplied by the given provider. Note that the specified Provider object
     * does not have to be registered in the provider list.
     *
     * This method was added to version 1.8 of the Java Platform Standard
     * Edition. In order to maintain backwards compatibility with existing
     * service providers, this method is not {@code abstract}
     * and it provides a default implementation.
     *
     * <p>
     *  验证此CRL是否使用与给定公钥相对应的私钥进行签名。此方法使用由给定提供者提供的签名验证引擎。请注意,指定的Provider对象不必在提供程序列表中注册。
     * 
     *  此方法已添加到Java Platform Standard Edition的1.8版本中。为了保持与现有服务提供程序的向后兼容性,此方法不是{@code abstract},它提供了一个默认实现。
     * 
     * 
     * @param key the PublicKey used to carry out the verification.
     * @param sigProvider the signature provider.
     *
     * @exception NoSuchAlgorithmException on unsupported signature
     * algorithms.
     * @exception InvalidKeyException on incorrect key.
     * @exception SignatureException on signature errors.
     * @exception CRLException on encoding errors.
     * @since 1.8
     */
    public void verify(PublicKey key, Provider sigProvider)
        throws CRLException, NoSuchAlgorithmException,
        InvalidKeyException, SignatureException {
        X509CRLImpl.verify(this, key, sigProvider);
    }

    /**
     * Gets the {@code version} (version number) value from the CRL.
     * The ASN.1 definition for this is:
     * <pre>
     * version    Version OPTIONAL,
     *             -- if present, must be v2
     *
     * Version  ::=  INTEGER  {  v1(0), v2(1), v3(2)  }
     *             -- v3 does not apply to CRLs but appears for consistency
     *             -- with definition of Version for certs
     * </pre>
     *
     * <p>
     *  从CRL获取{@code版本}(版本号)值。对此的ASN.1定义是：
     * <pre>
     *  version版本可选, - 如果存在,必须是v2
     * 
     *  Version :: = INTEGER {v1(0),v2(1),v3(2)}  -  v3不适用于CRL,但出现一致性 - 
     * </pre>
     * 
     * 
     * @return the version number, i.e. 1 or 2.
     */
    public abstract int getVersion();

    /**
     * <strong>Denigrated</strong>, replaced by {@linkplain
     * #getIssuerX500Principal()}. This method returns the {@code issuer}
     * as an implementation specific Principal object, which should not be
     * relied upon by portable code.
     *
     * <p>
     * Gets the {@code issuer} (issuer distinguished name) value from
     * the CRL. The issuer name identifies the entity that signed (and
     * issued) the CRL.
     *
     * <p>The issuer name field contains an
     * X.500 distinguished name (DN).
     * The ASN.1 definition for this is:
     * <pre>
     * issuer    Name
     *
     * Name ::= CHOICE { RDNSequence }
     * RDNSequence ::= SEQUENCE OF RelativeDistinguishedName
     * RelativeDistinguishedName ::=
     *     SET OF AttributeValueAssertion
     *
     * AttributeValueAssertion ::= SEQUENCE {
     *                               AttributeType,
     *                               AttributeValue }
     * AttributeType ::= OBJECT IDENTIFIER
     * AttributeValue ::= ANY
     * </pre>
     * The {@code Name} describes a hierarchical name composed of
     * attributes,
     * such as country name, and corresponding values, such as US.
     * The type of the {@code AttributeValue} component is determined by
     * the {@code AttributeType}; in general it will be a
     * {@code directoryString}. A {@code directoryString} is usually
     * one of {@code PrintableString},
     * {@code TeletexString} or {@code UniversalString}.
     *
     * <p>
     *  <strong>已拒绝</strong>,替换为{@linkplain#getIssuerX500Principal()}。
     * 此方法返回{@code issuer}作为实现特定的Principal对象,不应依赖于可移植代码。
     * 
     * <p>
     *  从CRL获取{@code issuer}(颁发者专有名称)值。发放者名称标识签署(并颁发)CRL的实体。
     * 
     *  <p>发行者名称字段包含X.500可分辨名称(DN)。对此的ASN.1定义是：
     * <pre>
     *  发行人名称
     * 
     * Name :: = CHOICE {RDNSequence} RDNSequence :: = SEQUENCE OF RelativeDistinguishedName RelativeDisting
     * uishedName :: = SET OF AttributeValueAssertion。
     * 
     *  AttributeValueAssertion :: = SEQUENCE {AttributeType,AttributeValue} AttributeType :: = OBJECT IDENT
     * IFIER AttributeValue :: = ANY。
     * </pre>
     *  {@code Name}描述了由属性组成的分层名称,例如国家/地区名称和相应的值,例如US。
     *  {@code AttributeValue}组件的类型由{@code AttributeType}确定;一般来说它会是一个{@code directoryString}。
     *  {@code directoryString}通常是{@code PrintableString},{@code TeletexString}或{@code UniversalString}之一。
     * 
     * 
     * @return a Principal whose name is the issuer distinguished name.
     */
    public abstract Principal getIssuerDN();

    /**
     * Returns the issuer (issuer distinguished name) value from the
     * CRL as an {@code X500Principal}.
     * <p>
     * It is recommended that subclasses override this method.
     *
     * <p>
     *  将CRL中的颁发者(签发者可分辨名称)值作为{@code X500Principal}返回。
     * <p>
     *  建议子类重写此方法。
     * 
     * 
     * @return an {@code X500Principal} representing the issuer
     *          distinguished name
     * @since 1.4
     */
    public X500Principal getIssuerX500Principal() {
        if (issuerPrincipal == null) {
            issuerPrincipal = X509CRLImpl.getIssuerX500Principal(this);
        }
        return issuerPrincipal;
    }

    /**
     * Gets the {@code thisUpdate} date from the CRL.
     * The ASN.1 definition for this is:
     * <pre>
     * thisUpdate   ChoiceOfTime
     * ChoiceOfTime ::= CHOICE {
     *     utcTime        UTCTime,
     *     generalTime    GeneralizedTime }
     * </pre>
     *
     * <p>
     *  从CRL获取{@code thisUpdate}日期。对此的ASN.1定义是：
     * <pre>
     *  thisUpdate ChoiceOfTime ChoiceOfTime :: = CHOICE {utcTime UTCTime,generalTime GeneralizedTime}
     * </pre>
     * 
     * 
     * @return the {@code thisUpdate} date from the CRL.
     */
    public abstract Date getThisUpdate();

    /**
     * Gets the {@code nextUpdate} date from the CRL.
     *
     * <p>
     *  从CRL获取{@code nextUpdate}日期。
     * 
     * 
     * @return the {@code nextUpdate} date from the CRL, or null if
     * not present.
     */
    public abstract Date getNextUpdate();

    /**
     * Gets the CRL entry, if any, with the given certificate serialNumber.
     *
     * <p>
     *  获取具有给定证书serialNumber的CRL条目(如果有)。
     * 
     * 
     * @param serialNumber the serial number of the certificate for which a CRL entry
     * is to be looked up
     * @return the entry with the given serial number, or null if no such entry
     * exists in this CRL.
     * @see X509CRLEntry
     */
    public abstract X509CRLEntry
        getRevokedCertificate(BigInteger serialNumber);

    /**
     * Get the CRL entry, if any, for the given certificate.
     *
     * <p>This method can be used to lookup CRL entries in indirect CRLs,
     * that means CRLs that contain entries from issuers other than the CRL
     * issuer. The default implementation will only return entries for
     * certificates issued by the CRL issuer. Subclasses that wish to
     * support indirect CRLs should override this method.
     *
     * <p>
     *  获取给定证书的CRL条目(如果有)。
     * 
     * <p>此方法可用于在间接CRL中查找CRL条目,这意味着包含来自CRL发布者以外的发布者的条目的CRL。默认实现将仅返回由CRL颁发者颁发的证书的条目。希望支持间接CRL的子类应该覆盖此方法。
     * 
     * 
     * @param certificate the certificate for which a CRL entry is to be looked
     *   up
     * @return the entry for the given certificate, or null if no such entry
     *   exists in this CRL.
     * @exception NullPointerException if certificate is null
     *
     * @since 1.5
     */
    public X509CRLEntry getRevokedCertificate(X509Certificate certificate) {
        X500Principal certIssuer = certificate.getIssuerX500Principal();
        X500Principal crlIssuer = getIssuerX500Principal();
        if (certIssuer.equals(crlIssuer) == false) {
            return null;
        }
        return getRevokedCertificate(certificate.getSerialNumber());
    }

    /**
     * Gets all the entries from this CRL.
     * This returns a Set of X509CRLEntry objects.
     *
     * <p>
     *  获取此CRL的所有条目。这将返回一组X509CRLEntry对象。
     * 
     * 
     * @return all the entries or null if there are none present.
     * @see X509CRLEntry
     */
    public abstract Set<? extends X509CRLEntry> getRevokedCertificates();

    /**
     * Gets the DER-encoded CRL information, the
     * {@code tbsCertList} from this CRL.
     * This can be used to verify the signature independently.
     *
     * <p>
     *  获取DER编码的CRL信息,来自此CRL的{@code tbsCertList}。这可以用于独立地验证签名。
     * 
     * 
     * @return the DER-encoded CRL information.
     * @exception CRLException if an encoding error occurs.
     */
    public abstract byte[] getTBSCertList() throws CRLException;

    /**
     * Gets the {@code signature} value (the raw signature bits) from
     * the CRL.
     * The ASN.1 definition for this is:
     * <pre>
     * signature     BIT STRING
     * </pre>
     *
     * <p>
     *  从CRL获取{@code signature}值(原始签名位)。对此的ASN.1定义是：
     * <pre>
     *  签名BIT STRING
     * </pre>
     * 
     * 
     * @return the signature.
     */
    public abstract byte[] getSignature();

    /**
     * Gets the signature algorithm name for the CRL
     * signature algorithm. An example is the string "SHA256withRSA".
     * The ASN.1 definition for this is:
     * <pre>
     * signatureAlgorithm   AlgorithmIdentifier
     *
     * AlgorithmIdentifier  ::=  SEQUENCE  {
     *     algorithm               OBJECT IDENTIFIER,
     *     parameters              ANY DEFINED BY algorithm OPTIONAL  }
     *                             -- contains a value of the type
     *                             -- registered for use with the
     *                             -- algorithm object identifier value
     * </pre>
     *
     * <p>The algorithm name is determined from the {@code algorithm}
     * OID string.
     *
     * <p>
     *  获取CRL签名算法的签名算法名称。一个例子是字符串"SHA256withRSA"。对此的ASN.1定义是：
     * <pre>
     *  signatureAlgorithm AlgorithmIdentifier
     * 
     *  AlgorithmIdentifier :: = SEQUENCE {algorithm OBJECT IDENTIFIER,parameters ANY DEFINED BY algorithm OPTIONAL}
     *   - 包含类型的值 - 注册用于 - 算法对象标识符值。
     * </pre>
     * 
     *  <p>算法名称由{@code algorithm} OID字符串确定。
     * 
     * 
     * @return the signature algorithm name.
     */
    public abstract String getSigAlgName();

    /**
     * Gets the signature algorithm OID string from the CRL.
     * An OID is represented by a set of nonnegative whole numbers separated
     * by periods.
     * For example, the string "1.2.840.10040.4.3" identifies the SHA-1
     * with DSA signature algorithm defined in
     * <a href="http://www.ietf.org/rfc/rfc3279.txt">RFC 3279: Algorithms and
     * Identifiers for the Internet X.509 Public Key Infrastructure Certificate
     * and CRL Profile</a>.
     *
     * <p>See {@link #getSigAlgName() getSigAlgName} for
     * relevant ASN.1 definitions.
     *
     * <p>
     * 从CRL获取签名算法OID字符串。 OID由一组由周期分隔的非负整数表示。
     * 例如,字符串"1.2.840.10040.4.3"标识了在<a href="http://www.ietf.org/rfc/rfc3279.txt"> RFC 3279：算法中定义的具有DSA签名算法的
     * SHA-1, Internet的X.509公钥基础设施证书和CRL配置文件的标识符</a>。
     * 从CRL获取签名算法OID字符串。 OID由一组由周期分隔的非负整数表示。
     * 
     *  <p>有关ASN.1定义,请参阅{@link #getSigAlgName()getSigAlgName}。
     * 
     * 
     * @return the signature algorithm OID string.
     */
    public abstract String getSigAlgOID();

    /**
     * Gets the DER-encoded signature algorithm parameters from this
     * CRL's signature algorithm. In most cases, the signature
     * algorithm parameters are null; the parameters are usually
     * supplied with the public key.
     * If access to individual parameter values is needed then use
     * {@link java.security.AlgorithmParameters AlgorithmParameters}
     * and instantiate with the name returned by
     * {@link #getSigAlgName() getSigAlgName}.
     *
     * <p>See {@link #getSigAlgName() getSigAlgName} for
     * relevant ASN.1 definitions.
     *
     * <p>
     *  从此CRL的签名算法获取DER编码的签名算法参数。在大多数情况下,签名算法参数为null;这些参数通常与公钥一起提供。
     * 如果需要访问单个参数值,请使用{@link java.security.AlgorithmParameters AlgorithmParameters}并使用{@link #getSigAlgName()getSigAlgName}
     * 返回的名称实例化。
     *  从此CRL的签名算法获取DER编码的签名算法参数。在大多数情况下,签名算法参数为null;这些参数通常与公钥一起提供。
     * 
     * @return the DER-encoded signature algorithm parameters, or
     *         null if no parameters are present.
     */
    public abstract byte[] getSigAlgParams();
}
