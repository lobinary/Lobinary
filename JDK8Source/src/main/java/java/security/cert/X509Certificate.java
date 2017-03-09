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

import java.math.BigInteger;
import java.security.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.security.auth.x500.X500Principal;

import sun.security.x509.X509CertImpl;

/**
 * <p>
 * Abstract class for X.509 certificates. This provides a standard
 * way to access all the attributes of an X.509 certificate.
 * <p>
 * In June of 1996, the basic X.509 v3 format was completed by
 * ISO/IEC and ANSI X9, which is described below in ASN.1:
 * <pre>
 * Certificate  ::=  SEQUENCE  {
 *     tbsCertificate       TBSCertificate,
 *     signatureAlgorithm   AlgorithmIdentifier,
 *     signature            BIT STRING  }
 * </pre>
 * <p>
 * These certificates are widely used to support authentication and
 * other functionality in Internet security systems. Common applications
 * include Privacy Enhanced Mail (PEM), Transport Layer Security (SSL),
 * code signing for trusted software distribution, and Secure Electronic
 * Transactions (SET).
 * <p>
 * These certificates are managed and vouched for by <em>Certificate
 * Authorities</em> (CAs). CAs are services which create certificates by
 * placing data in the X.509 standard format and then digitally signing
 * that data. CAs act as trusted third parties, making introductions
 * between principals who have no direct knowledge of each other.
 * CA certificates are either signed by themselves, or by some other
 * CA such as a "root" CA.
 * <p>
 * More information can be found in
 * <a href="http://www.ietf.org/rfc/rfc3280.txt">RFC 3280: Internet X.509
 * Public Key Infrastructure Certificate and CRL Profile</a>.
 * <p>
 * The ASN.1 definition of {@code tbsCertificate} is:
 * <pre>
 * TBSCertificate  ::=  SEQUENCE  {
 *     version         [0]  EXPLICIT Version DEFAULT v1,
 *     serialNumber         CertificateSerialNumber,
 *     signature            AlgorithmIdentifier,
 *     issuer               Name,
 *     validity             Validity,
 *     subject              Name,
 *     subjectPublicKeyInfo SubjectPublicKeyInfo,
 *     issuerUniqueID  [1]  IMPLICIT UniqueIdentifier OPTIONAL,
 *                          -- If present, version must be v2 or v3
 *     subjectUniqueID [2]  IMPLICIT UniqueIdentifier OPTIONAL,
 *                          -- If present, version must be v2 or v3
 *     extensions      [3]  EXPLICIT Extensions OPTIONAL
 *                          -- If present, version must be v3
 *     }
 * </pre>
 * <p>
 * Certificates are instantiated using a certificate factory. The following is
 * an example of how to instantiate an X.509 certificate:
 * <pre>
 * try (InputStream inStream = new FileInputStream("fileName-of-cert")) {
 *     CertificateFactory cf = CertificateFactory.getInstance("X.509");
 *     X509Certificate cert = (X509Certificate)cf.generateCertificate(inStream);
 * }
 * </pre>
 *
 * <p>
 * <p>
 *  X.509证书的抽象类。这提供了访问X.509证书的所有属性的标准方法。
 * <p>
 *  在1996年6月,基本的X.509 v3格式由ISO / IEC和ANSI X9完成,其在ASN.1中描述如下：
 * <pre>
 *  Certificate :: = SEQUENCE {tbsCertificate TBSCertificate,signatureAlgorithm AlgorithmIdentifier,signature BIT STRING}
 * 。
 * </pre>
 * <p>
 *  这些证书广泛用于支持Internet安全系统中的身份验证和其他功能。常见的应用包括隐私增强邮件(PEM),传输层安全(SSL),可信软件分发的代码签名和安全电子交易(SET)。
 * <p>
 *  这些证书由<em>证书颁发机构</em>(CA)管理和支持。 CA是通过以X.509标准格式放置数据然后对该数据进行数字签名来创建证书的服务。
 *  CA作为受信任的第三方,在没有彼此直接了解的校长之间进行介绍。 CA证书由它们自己签名,或由一些其他CA签名,例如"根"CA。
 * <p>
 *  有关详情,请参阅<a href="http://www.ietf.org/rfc/rfc3280.txt"> RFC 3280：Internet X.509公钥基础结构证书和CRL配置文件</a>。
 * <p>
 *  {@code tbsCertificate}的ASN.1定义是：
 * <pre>
 * TBSCertificate :: = SEQUENCE {version [0] EXPLICIT版本DEFAULT v1,serialNumber CertificateSerialNumber,签名AlgorithmIdentifier,发布者名称,有效性有效性,主题名称,subjectPublicKeyInfo SubjectPublicKeyInfo,issuerUniqueID [1] IMPLICIT UniqueIdentifier可选, - 如果存在,版本必须为v2或v3 subjectUniqueID [2] IMPLICIT UniqueIdentifier可选, - 如果存在,版本必须是v2或v3扩展[3] EXPLICIT扩展可选 - 如果存在,版本必须为v3}
 * 。
 * </pre>
 * <p>
 *  证书使用证书工厂实例化。以下是如何实例化X.509证书的示例：
 * <pre>
 *  try(InputStream inStream = new FileInputStream("fileName-of-cert")){CertificateFactory cf = CertificateFactory.getInstance("X.509"); X509Certificate cert =(X509Certificate)cf.generateCertificate(inStream); }
 * }。
 * </pre>
 * 
 * 
 * @author Hemma Prafullchandra
 *
 *
 * @see Certificate
 * @see CertificateFactory
 * @see X509Extension
 */

public abstract class X509Certificate extends Certificate
implements X509Extension {

    private static final long serialVersionUID = -2491127588187038216L;

    private transient X500Principal subjectX500Principal, issuerX500Principal;

    /**
     * Constructor for X.509 certificates.
     * <p>
     *  X.509证书的构造函数。
     * 
     */
    protected X509Certificate() {
        super("X.509");
    }

    /**
     * Checks that the certificate is currently valid. It is if
     * the current date and time are within the validity period given in the
     * certificate.
     * <p>
     * The validity period consists of two date/time values:
     * the first and last dates (and times) on which the certificate
     * is valid. It is defined in
     * ASN.1 as:
     * <pre>
     * validity             Validity
     *
     * Validity ::= SEQUENCE {
     *     notBefore      CertificateValidityDate,
     *     notAfter       CertificateValidityDate }
     *
     * CertificateValidityDate ::= CHOICE {
     *     utcTime        UTCTime,
     *     generalTime    GeneralizedTime }
     * </pre>
     *
     * <p>
     *  检查证书当前是否有效。这是如果当前日期和时间在证书中给出的有效期内。
     * <p>
     *  有效期由两个日期/时间值组成：证书有效的第一个和最后一个日期(和时间)。它在ASN.1中定义为：
     * <pre>
     *  有效性
     * 
     *  Validity :: = SEQUENCE {不是CertificateValidityDate之前,不是CertificateValidityDate之后}
     * 
     *  CertificateValidityDate :: = CHOICE {utcTime UTCTime,generalTime GeneralizedTime}
     * </pre>
     * 
     * 
     * @exception CertificateExpiredException if the certificate has expired.
     * @exception CertificateNotYetValidException if the certificate is not
     * yet valid.
     */
    public abstract void checkValidity()
        throws CertificateExpiredException, CertificateNotYetValidException;

    /**
     * Checks that the given date is within the certificate's
     * validity period. In other words, this determines whether the
     * certificate would be valid at the given date/time.
     *
     * <p>
     * 检查给定日期是否在证书的有效期内。换句话说,这确定证书在给定日期/时间是否有效。
     * 
     * 
     * @param date the Date to check against to see if this certificate
     *        is valid at that date/time.
     *
     * @exception CertificateExpiredException if the certificate has expired
     * with respect to the {@code date} supplied.
     * @exception CertificateNotYetValidException if the certificate is not
     * yet valid with respect to the {@code date} supplied.
     *
     * @see #checkValidity()
     */
    public abstract void checkValidity(Date date)
        throws CertificateExpiredException, CertificateNotYetValidException;

    /**
     * Gets the {@code version} (version number) value from the
     * certificate.
     * The ASN.1 definition for this is:
     * <pre>
     * version  [0] EXPLICIT Version DEFAULT v1
     *
     * Version ::=  INTEGER  {  v1(0), v2(1), v3(2)  }
     * </pre>
     * <p>
     *  从证书获取{@code版本}(版本号)值。对此的ASN.1定义是：
     * <pre>
     *  version [0] EXPLICIT版本DEFAULT v1
     * 
     *  Version :: = INTEGER {v1(0),v2(1),v3(2)}
     * </pre>
     * 
     * @return the version number, i.e. 1, 2 or 3.
     */
    public abstract int getVersion();

    /**
     * Gets the {@code serialNumber} value from the certificate.
     * The serial number is an integer assigned by the certification
     * authority to each certificate. It must be unique for each
     * certificate issued by a given CA (i.e., the issuer name and
     * serial number identify a unique certificate).
     * The ASN.1 definition for this is:
     * <pre>
     * serialNumber     CertificateSerialNumber
     *
     * CertificateSerialNumber  ::=  INTEGER
     * </pre>
     *
     * <p>
     *  从证书获取{@code serialNumber}值。序列号是证书颁发机构为每个证书分配的整数。它必须对于由给定CA发布的每个证书是唯一的(即,发行者名称和序列号标识唯一证书)。
     * 对此的ASN.1定义是：。
     * <pre>
     *  serialNumber CertificateSerialNumber
     * 
     *  CertificateSerialNumber :: = INTEGER
     * </pre>
     * 
     * 
     * @return the serial number.
     */
    public abstract BigInteger getSerialNumber();

    /**
     * <strong>Denigrated</strong>, replaced by {@linkplain
     * #getIssuerX500Principal()}. This method returns the {@code issuer}
     * as an implementation specific Principal object, which should not be
     * relied upon by portable code.
     *
     * <p>
     * Gets the {@code issuer} (issuer distinguished name) value from
     * the certificate. The issuer name identifies the entity that signed (and
     * issued) the certificate.
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
     *  从证书获取{@code issuer}(颁发者专有名称)值。发放者名称标识签署(并颁发)证书的实体。
     * 
     *  <p>发行者名称字段包含X.500可分辨名称(DN)。对此的ASN.1定义是：
     * <pre>
     *  发行人名称
     * 
     *  Name :: = CHOICE {RDNSequence} RDNSequence :: = SEQUENCE OF RelativeDistinguishedName RelativeDistin
     * guishedName :: = SET OF AttributeValueAssertion。
     * 
     * AttributeValueAssertion :: = SEQUENCE {AttributeType,AttributeValue} AttributeType :: = OBJECT IDENTI
     * FIER AttributeValue :: = ANY。
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
     * certificate as an {@code X500Principal}.
     * <p>
     * It is recommended that subclasses override this method.
     *
     * <p>
     *  将证书中的颁发者(颁发者专有名称)值作为{@code X500Principal}返回。
     * <p>
     *  建议子类重写此方法。
     * 
     * 
     * @return an {@code X500Principal} representing the issuer
     *          distinguished name
     * @since 1.4
     */
    public X500Principal getIssuerX500Principal() {
        if (issuerX500Principal == null) {
            issuerX500Principal = X509CertImpl.getIssuerX500Principal(this);
        }
        return issuerX500Principal;
    }

    /**
     * <strong>Denigrated</strong>, replaced by {@linkplain
     * #getSubjectX500Principal()}. This method returns the {@code subject}
     * as an implementation specific Principal object, which should not be
     * relied upon by portable code.
     *
     * <p>
     * Gets the {@code subject} (subject distinguished name) value
     * from the certificate.  If the {@code subject} value is empty,
     * then the {@code getName()} method of the returned
     * {@code Principal} object returns an empty string ("").
     *
     * <p> The ASN.1 definition for this is:
     * <pre>
     * subject    Name
     * </pre>
     *
     * <p>See {@link #getIssuerDN() getIssuerDN} for {@code Name}
     * and other relevant definitions.
     *
     * <p>
     *  <strong>已拒绝</strong>,替换为{@linkplain#getSubjectX500Principal()}。
     * 此方法返回{@code subject}作为实现特定的Principal对象,不应依赖于可移植代码。
     * 
     * <p>
     *  从证书获取{@code subject}(主题专有名称)值。
     * 如果{@code subject}值为空,则返回的{@code Principal}对象的{@code getName()}方法返回一个空字符串("")。
     * 
     *  <p> ASN.1的定义是：
     * <pre>
     *  主题名称
     * </pre>
     * 
     *  <p>有关{@code Name}和其他相关定义,请参阅{@link #getIssuerDN()getIssuerDN}。
     * 
     * 
     * @return a Principal whose name is the subject name.
     */
    public abstract Principal getSubjectDN();

    /**
     * Returns the subject (subject distinguished name) value from the
     * certificate as an {@code X500Principal}.  If the subject value
     * is empty, then the {@code getName()} method of the returned
     * {@code X500Principal} object returns an empty string ("").
     * <p>
     * It is recommended that subclasses override this method.
     *
     * <p>
     * 将证书中的主题(主题可分辨名称)值作为{@code X500Principal}返回。
     * 如果主题值为空,则返回的{@code X500Principal}对象的{@code getName()}方法返回一个空字符串("")。
     * <p>
     *  建议子类重写此方法。
     * 
     * 
     * @return an {@code X500Principal} representing the subject
     *          distinguished name
     * @since 1.4
     */
    public X500Principal getSubjectX500Principal() {
        if (subjectX500Principal == null) {
            subjectX500Principal = X509CertImpl.getSubjectX500Principal(this);
        }
        return subjectX500Principal;
    }

    /**
     * Gets the {@code notBefore} date from the validity period of
     * the certificate.
     * The relevant ASN.1 definitions are:
     * <pre>
     * validity             Validity
     *
     * Validity ::= SEQUENCE {
     *     notBefore      CertificateValidityDate,
     *     notAfter       CertificateValidityDate }
     *
     * CertificateValidityDate ::= CHOICE {
     *     utcTime        UTCTime,
     *     generalTime    GeneralizedTime }
     * </pre>
     *
     * <p>
     *  从证书的有效期获取{@code notBefore}日期。相关的ASN.1定义是：
     * <pre>
     *  有效性
     * 
     *  Validity :: = SEQUENCE {不是CertificateValidityDate之前,不是CertificateValidityDate之后}
     * 
     *  CertificateValidityDate :: = CHOICE {utcTime UTCTime,generalTime GeneralizedTime}
     * </pre>
     * 
     * 
     * @return the start date of the validity period.
     * @see #checkValidity
     */
    public abstract Date getNotBefore();

    /**
     * Gets the {@code notAfter} date from the validity period of
     * the certificate. See {@link #getNotBefore() getNotBefore}
     * for relevant ASN.1 definitions.
     *
     * <p>
     *  从证书的有效期获取{@code notAfter}日期。有关ASN.1定义,请参见{@link #getNotBefore()getNotBefore}。
     * 
     * 
     * @return the end date of the validity period.
     * @see #checkValidity
     */
    public abstract Date getNotAfter();

    /**
     * Gets the DER-encoded certificate information, the
     * {@code tbsCertificate} from this certificate.
     * This can be used to verify the signature independently.
     *
     * <p>
     *  获取DER编码的证书信息,即此证书的{@code tbsCertificate}。这可以用于独立地验证签名。
     * 
     * 
     * @return the DER-encoded certificate information.
     * @exception CertificateEncodingException if an encoding error occurs.
     */
    public abstract byte[] getTBSCertificate()
        throws CertificateEncodingException;

    /**
     * Gets the {@code signature} value (the raw signature bits) from
     * the certificate.
     * The ASN.1 definition for this is:
     * <pre>
     * signature     BIT STRING
     * </pre>
     *
     * <p>
     *  从证书获取{@code signature}值(原始签名位)。对此的ASN.1定义是：
     * <pre>
     *  签名BIT STRING
     * </pre>
     * 
     * 
     * @return the signature.
     */
    public abstract byte[] getSignature();

    /**
     * Gets the signature algorithm name for the certificate
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
     *  获取证书签名算法的签名算法名称。一个例子是字符串"SHA256withRSA"。对此的ASN.1定义是：
     * <pre>
     *  signatureAlgorithm AlgorithmIdentifier
     * 
     * AlgorithmIdentifier :: = SEQUENCE {algorithm OBJECT IDENTIFIER,parameters ANY DEFINED BY algorithm OPTIONAL}
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
     * Gets the signature algorithm OID string from the certificate.
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
     *  从证书获取签名算法OID字符串。 OID由一组由周期分隔的非负整数表示。
     * 例如,字符串"1.2.840.10040.4.3"标识了在<a href="http://www.ietf.org/rfc/rfc3279.txt"> RFC 3279：算法中定义的具有DSA签名算法的
     * SHA-1, Internet的X.509公钥基础设施证书和CRL配置文件的标识符</a>。
     *  从证书获取签名算法OID字符串。 OID由一组由周期分隔的非负整数表示。
     * 
     *  <p>有关ASN.1定义,请参阅{@link #getSigAlgName()getSigAlgName}。
     * 
     * 
     * @return the signature algorithm OID string.
     */
    public abstract String getSigAlgOID();

    /**
     * Gets the DER-encoded signature algorithm parameters from this
     * certificate's signature algorithm. In most cases, the signature
     * algorithm parameters are null; the parameters are usually
     * supplied with the certificate's public key.
     * If access to individual parameter values is needed then use
     * {@link java.security.AlgorithmParameters AlgorithmParameters}
     * and instantiate with the name returned by
     * {@link #getSigAlgName() getSigAlgName}.
     *
     * <p>See {@link #getSigAlgName() getSigAlgName} for
     * relevant ASN.1 definitions.
     *
     * <p>
     *  从此证书的签名算法获取DER编码的签名算法参数。在大多数情况下,签名算法参数为null;这些参数通常随证书的公钥提供。
     * 如果需要访问单个参数值,请使用{@link java.security.AlgorithmParameters AlgorithmParameters}并使用{@link #getSigAlgName()getSigAlgName}
     * 返回的名称实例化。
     *  从此证书的签名算法获取DER编码的签名算法参数。在大多数情况下,签名算法参数为null;这些参数通常随证书的公钥提供。
     * 
     *  <p>有关ASN.1定义,请参阅{@link #getSigAlgName()getSigAlgName}。
     * 
     * 
     * @return the DER-encoded signature algorithm parameters, or
     *         null if no parameters are present.
     */
    public abstract byte[] getSigAlgParams();

    /**
     * Gets the {@code issuerUniqueID} value from the certificate.
     * The issuer unique identifier is present in the certificate
     * to handle the possibility of reuse of issuer names over time.
     * RFC 3280 recommends that names not be reused and that
     * conforming certificates not make use of unique identifiers.
     * Applications conforming to that profile should be capable of
     * parsing unique identifiers and making comparisons.
     *
     * <p>The ASN.1 definition for this is:
     * <pre>
     * issuerUniqueID  [1]  IMPLICIT UniqueIdentifier OPTIONAL
     *
     * UniqueIdentifier  ::=  BIT STRING
     * </pre>
     *
     * <p>
     * 从证书获取{@code issuerUniqueID}值。发行者唯一标识符存在于证书中以处理随时间重用发行者名称的可能性。 RFC 3280建议不要重复使用名称,并且符合证书不使用唯一标识符。
     * 符合该配置文件的应用程序应该能够解析唯一标识符并进行比较。
     * 
     *  <p> ASN.1的定义是：
     * <pre>
     *  issuerUniqueID [1] IMPLICIT UniqueIdentifier可选
     * 
     *  UniqueIdentifier :: = BIT STRING
     * </pre>
     * 
     * 
     * @return the issuer unique identifier or null if it is not
     * present in the certificate.
     */
    public abstract boolean[] getIssuerUniqueID();

    /**
     * Gets the {@code subjectUniqueID} value from the certificate.
     *
     * <p>The ASN.1 definition for this is:
     * <pre>
     * subjectUniqueID  [2]  IMPLICIT UniqueIdentifier OPTIONAL
     *
     * UniqueIdentifier  ::=  BIT STRING
     * </pre>
     *
     * <p>
     *  从证书获取{@code subjectUniqueID}值。
     * 
     *  <p> ASN.1的定义是：
     * <pre>
     *  subjectUniqueID [2] IMPLICIT UniqueIdentifier可选
     * 
     *  UniqueIdentifier :: = BIT STRING
     * </pre>
     * 
     * 
     * @return the subject unique identifier or null if it is not
     * present in the certificate.
     */
    public abstract boolean[] getSubjectUniqueID();

    /**
     * Gets a boolean array representing bits of
     * the {@code KeyUsage} extension, (OID = 2.5.29.15).
     * The key usage extension defines the purpose (e.g., encipherment,
     * signature, certificate signing) of the key contained in the
     * certificate.
     * The ASN.1 definition for this is:
     * <pre>
     * KeyUsage ::= BIT STRING {
     *     digitalSignature        (0),
     *     nonRepudiation          (1),
     *     keyEncipherment         (2),
     *     dataEncipherment        (3),
     *     keyAgreement            (4),
     *     keyCertSign             (5),
     *     cRLSign                 (6),
     *     encipherOnly            (7),
     *     decipherOnly            (8) }
     * </pre>
     * RFC 3280 recommends that when used, this be marked
     * as a critical extension.
     *
     * <p>
     *  获取表示{@code KeyUsage}扩展名(OID = 2.5.29.15)的位的布尔数组。密钥使用扩展定义证书中包含的密钥的目的(例如,加密,签名,证书签名)。对此的ASN.1定义是：
     * <pre>
     *  KeyUsage :: = BIT STRING {digitalSignature(0),nonRepudiation(1),keyEncipherment(2),dataEncipherment(3),keyAgreement(4),keyCertSign(5),cRLSign(6),encipherOnly(7),decipherOnly )}
     * 。
     * </pre>
     *  RFC 3280建议在使用时将其标记为关键扩展。
     * 
     * 
     * @return the KeyUsage extension of this certificate, represented as
     * an array of booleans. The order of KeyUsage values in the array is
     * the same as in the above ASN.1 definition. The array will contain a
     * value for each KeyUsage defined above. If the KeyUsage list encoded
     * in the certificate is longer than the above list, it will not be
     * truncated. Returns null if this certificate does not
     * contain a KeyUsage extension.
     */
    public abstract boolean[] getKeyUsage();

    /**
     * Gets an unmodifiable list of Strings representing the OBJECT
     * IDENTIFIERs of the {@code ExtKeyUsageSyntax} field of the
     * extended key usage extension, (OID = 2.5.29.37).  It indicates
     * one or more purposes for which the certified public key may be
     * used, in addition to or in place of the basic purposes
     * indicated in the key usage extension field.  The ASN.1
     * definition for this is:
     * <pre>
     * ExtKeyUsageSyntax ::= SEQUENCE SIZE (1..MAX) OF KeyPurposeId
     *
     * KeyPurposeId ::= OBJECT IDENTIFIER
     * </pre>
     *
     * Key purposes may be defined by any organization with a
     * need. Object identifiers used to identify key purposes shall be
     * assigned in accordance with IANA or ITU-T Rec. X.660 |
     * ISO/IEC/ITU 9834-1.
     * <p>
     * This method was added to version 1.4 of the Java 2 Platform Standard
     * Edition. In order to maintain backwards compatibility with existing
     * service providers, this method is not {@code abstract}
     * and it provides a default implementation. Subclasses
     * should override this method with a correct implementation.
     *
     * <p>
     * 获取表示扩展密钥使用扩展的{@code ExtKeyUsageSyntax}字段(OID = 2.5.29.37)的OBJECT标识符的字符串的不可修改列表。
     * 它指示除了或替代密钥使用扩展字段中指示的基本目的,可以使用经认证的公钥的一个或多个目的。对此的ASN.1定义是：。
     * <pre>
     *  ExtKeyUsageSyntax :: = SEQUENCE SIZE(1..MAX)OF KeyPurposeId
     * 
     *  KeyPurposeId :: = OBJECT IDENTIFIER
     * </pre>
     * 
     *  主要目的可以由任何需要的组织定义。用于识别关键目的的对象标识符应根据IANA或ITU-T Rec。 X.660 | ISO / IEC / ITU 9834-1。
     * <p>
     *  此方法已添加到Java 2平台标准版的1.4版本。为了保持与现有服务提供程序的向后兼容性,此方法不是{@code abstract},它提供了一个默认实现。子类应该使用正确的实现覆盖此方法。
     * 
     * 
     * @return the ExtendedKeyUsage extension of this certificate,
     *         as an unmodifiable list of object identifiers represented
     *         as Strings. Returns null if this certificate does not
     *         contain an ExtendedKeyUsage extension.
     * @throws CertificateParsingException if the extension cannot be decoded
     * @since 1.4
     */
    public List<String> getExtendedKeyUsage() throws CertificateParsingException {
        return X509CertImpl.getExtendedKeyUsage(this);
    }

    /**
     * Gets the certificate constraints path length from the
     * critical {@code BasicConstraints} extension, (OID = 2.5.29.19).
     * <p>
     * The basic constraints extension identifies whether the subject
     * of the certificate is a Certificate Authority (CA) and
     * how deep a certification path may exist through that CA. The
     * {@code pathLenConstraint} field (see below) is meaningful
     * only if {@code cA} is set to TRUE. In this case, it gives the
     * maximum number of CA certificates that may follow this certificate in a
     * certification path. A value of zero indicates that only an end-entity
     * certificate may follow in the path.
     * <p>
     * The ASN.1 definition for this is:
     * <pre>
     * BasicConstraints ::= SEQUENCE {
     *     cA                  BOOLEAN DEFAULT FALSE,
     *     pathLenConstraint   INTEGER (0..MAX) OPTIONAL }
     * </pre>
     *
     * <p>
     *  从临界{@code BasicConstraints}扩展(OID = 2.5.29.19)获取证书约束路径长度。
     * <p>
     * 基本约束扩展标识证书的主题是否是证书颁发机构(CA)以及证书路径可能通过该CA存在多深。
     * 只有{@code cA}设置为TRUE,{@code pathLenConstraint}字段(见下文)才有意义。在这种情况下,它会在认证路径中提供此证书后面的CA证书的最大数量。
     * 值为零表示在路径中只能有一个终端实体证书。
     * <p>
     *  对此的ASN.1定义是：
     * <pre>
     *  BasicConstraints :: = SEQUENCE {cA BOOLEAN DEFAULT FALSE,pathLenConstraint INTEGER(0..MAX)OPTIONAL}。
     * </pre>
     * 
     * 
     * @return the value of {@code pathLenConstraint} if the
     * BasicConstraints extension is present in the certificate and the
     * subject of the certificate is a CA, otherwise -1.
     * If the subject of the certificate is a CA and
     * {@code pathLenConstraint} does not appear,
     * {@code Integer.MAX_VALUE} is returned to indicate that there is no
     * limit to the allowed length of the certification path.
     */
    public abstract int getBasicConstraints();

    /**
     * Gets an immutable collection of subject alternative names from the
     * {@code SubjectAltName} extension, (OID = 2.5.29.17).
     * <p>
     * The ASN.1 definition of the {@code SubjectAltName} extension is:
     * <pre>
     * SubjectAltName ::= GeneralNames
     *
     * GeneralNames :: = SEQUENCE SIZE (1..MAX) OF GeneralName
     *
     * GeneralName ::= CHOICE {
     *      otherName                       [0]     OtherName,
     *      rfc822Name                      [1]     IA5String,
     *      dNSName                         [2]     IA5String,
     *      x400Address                     [3]     ORAddress,
     *      directoryName                   [4]     Name,
     *      ediPartyName                    [5]     EDIPartyName,
     *      uniformResourceIdentifier       [6]     IA5String,
     *      iPAddress                       [7]     OCTET STRING,
     *      registeredID                    [8]     OBJECT IDENTIFIER}
     * </pre>
     * <p>
     * If this certificate does not contain a {@code SubjectAltName}
     * extension, {@code null} is returned. Otherwise, a
     * {@code Collection} is returned with an entry representing each
     * {@code GeneralName} included in the extension. Each entry is a
     * {@code List} whose first entry is an {@code Integer}
     * (the name type, 0-8) and whose second entry is a {@code String}
     * or a byte array (the name, in string or ASN.1 DER encoded form,
     * respectively).
     * <p>
     * <a href="http://www.ietf.org/rfc/rfc822.txt">RFC 822</a>, DNS, and URI
     * names are returned as {@code String}s,
     * using the well-established string formats for those types (subject to
     * the restrictions included in RFC 3280). IPv4 address names are
     * returned using dotted quad notation. IPv6 address names are returned
     * in the form "a1:a2:...:a8", where a1-a8 are hexadecimal values
     * representing the eight 16-bit pieces of the address. OID names are
     * returned as {@code String}s represented as a series of nonnegative
     * integers separated by periods. And directory names (distinguished names)
     * are returned in <a href="http://www.ietf.org/rfc/rfc2253.txt">
     * RFC 2253</a> string format. No standard string format is
     * defined for otherNames, X.400 names, EDI party names, or any
     * other type of names. They are returned as byte arrays
     * containing the ASN.1 DER encoded form of the name.
     * <p>
     * Note that the {@code Collection} returned may contain more
     * than one name of the same type. Also, note that the returned
     * {@code Collection} is immutable and any entries containing byte
     * arrays are cloned to protect against subsequent modifications.
     * <p>
     * This method was added to version 1.4 of the Java 2 Platform Standard
     * Edition. In order to maintain backwards compatibility with existing
     * service providers, this method is not {@code abstract}
     * and it provides a default implementation. Subclasses
     * should override this method with a correct implementation.
     *
     * <p>
     *  从{@code SubjectAltName}扩展名(OID = 2.5.29.17)获取主题备用名称的不可变集合。
     * <p>
     *  {@code SubjectAltName}扩展的ASN.1定义是：
     * <pre>
     *  SubjectAltName :: = GeneralNames
     * 
     *  GeneralName：= GeneralName的SEQUENCE SIZE(1..MAX)
     * 
     *  GeneralName :: = CHOICE {otherName [0] OtherName,rfc822Name [1] IA5String,dNSName [2] IA5String,x400Address [3] ORAddress,directoryName [4] Name,ediPartyName [5] EDIPartyName,uniformResourceIdentifier [6] IA5String,iPAddress [ 。
     * </pre>
     * <p>
     * 如果此证书不包含{@code SubjectAltName}扩展名,则返回{@code null}。
     * 否则,会返回一个{@code Collection},其中包含代表扩展程序中包含的每个{@code GeneralName}的条目。
     * 每个条目是{@code List},其第一个条目是{@code Integer}(名称类型,0-8),其第二个条目是{@code String}或字节数组(名称,字符串或ASN.1 DER编码形式)。
     * <p>
     *  <a href="http://www.ietf.org/rfc/rfc822.txt"> RFC 822 </a>,DNS和URI名称将作为{@code String}返回,使用完备的字符串这些类型
     * 的格式(受RFC 3280中包含的限制)。
     *  IPv4地址名称使用点分四进制符号返回。 IPv6地址名称以"a1：a2：...：a8"的形式返回,其中a1-a8是表示该8个16位地址的十六进制值。
     *  OID名称作为{@code String}返回,表示为以句点分隔的一系列非负整数。
     * 目录名称(专有名称)以<a href="http://www.ietf.org/rfc/rfc2253.txt"> RFC 2253 </a>字符串格式返回。
     * 没有为otherNames,X.400名称,EDI方名称或任何其他类型的名称定义标准字符串格式。它们作为包含名称的ASN.1 DER编码形式的字节数组返回。
     * <p>
     * 请注意,返回的{@code Collection}可能包含多个相同类型的名称。另外,注意返回的{@code Collection}是不可变的,并且包含字节数组的任何条目都被克隆以防止后续修改。
     * <p>
     *  此方法已添加到Java 2平台标准版的1.4版本。为了保持与现有服务提供程序的向后兼容性,此方法不是{@code abstract},它提供了一个默认实现。子类应该使用正确的实现覆盖此方法。
     * 
     * @return an immutable {@code Collection} of subject alternative
     * names (or {@code null})
     * @throws CertificateParsingException if the extension cannot be decoded
     * @since 1.4
     */
    public Collection<List<?>> getSubjectAlternativeNames()
        throws CertificateParsingException {
        return X509CertImpl.getSubjectAlternativeNames(this);
    }

    /**
     * Gets an immutable collection of issuer alternative names from the
     * {@code IssuerAltName} extension, (OID = 2.5.29.18).
     * <p>
     * The ASN.1 definition of the {@code IssuerAltName} extension is:
     * <pre>
     * IssuerAltName ::= GeneralNames
     * </pre>
     * The ASN.1 definition of {@code GeneralNames} is defined
     * in {@link #getSubjectAlternativeNames getSubjectAlternativeNames}.
     * <p>
     * If this certificate does not contain an {@code IssuerAltName}
     * extension, {@code null} is returned. Otherwise, a
     * {@code Collection} is returned with an entry representing each
     * {@code GeneralName} included in the extension. Each entry is a
     * {@code List} whose first entry is an {@code Integer}
     * (the name type, 0-8) and whose second entry is a {@code String}
     * or a byte array (the name, in string or ASN.1 DER encoded form,
     * respectively). For more details about the formats used for each
     * name type, see the {@code getSubjectAlternativeNames} method.
     * <p>
     * Note that the {@code Collection} returned may contain more
     * than one name of the same type. Also, note that the returned
     * {@code Collection} is immutable and any entries containing byte
     * arrays are cloned to protect against subsequent modifications.
     * <p>
     * This method was added to version 1.4 of the Java 2 Platform Standard
     * Edition. In order to maintain backwards compatibility with existing
     * service providers, this method is not {@code abstract}
     * and it provides a default implementation. Subclasses
     * should override this method with a correct implementation.
     *
     * <p>
     * 
     * 
     * @return an immutable {@code Collection} of issuer alternative
     * names (or {@code null})
     * @throws CertificateParsingException if the extension cannot be decoded
     * @since 1.4
     */
    public Collection<List<?>> getIssuerAlternativeNames()
        throws CertificateParsingException {
        return X509CertImpl.getIssuerAlternativeNames(this);
    }

     /**
     * Verifies that this certificate was signed using the
     * private key that corresponds to the specified public key.
     * This method uses the signature verification engine
     * supplied by the specified provider. Note that the specified
     * Provider object does not have to be registered in the provider list.
     *
     * This method was added to version 1.8 of the Java Platform Standard
     * Edition. In order to maintain backwards compatibility with existing
     * service providers, this method is not {@code abstract}
     * and it provides a default implementation.
     *
     * <p>
     *  从{@code IssuerAltName}扩展程序(OID = 2.5.29.18)获取不可变的发布者替代名称集合。
     * <p>
     *  {@code IssuerAltName}扩展的ASN.1定义是：
     * <pre>
     *  IssuerAltName :: = GeneralNames
     * </pre>
     *  {@code GeneralNames}的ASN.1定义在{@link #getSubjectAlternativeNames getSubjectAlternativeNames}中定义。
     * <p>
     *  如果此证书不包含{@code IssuerAltName}扩展,则返回{@code null}。
     * 否则,会返回一个{@code Collection},其中包含代表扩展程序中包含的每个{@code GeneralName}的条目。
     * 每个条目是{@code List},其第一个条目是{@code Integer}(名称类型,0-8),其第二个条目是{@code String}或字节数组(名称,字符串或ASN.1 DER编码形式)。
     * 
     * @param key the PublicKey used to carry out the verification.
     * @param sigProvider the signature provider.
     *
     * @exception NoSuchAlgorithmException on unsupported signature
     * algorithms.
     * @exception InvalidKeyException on incorrect key.
     * @exception SignatureException on signature errors.
     * @exception CertificateException on encoding errors.
     * @exception UnsupportedOperationException if the method is not supported
     * @since 1.8
     */
    public void verify(PublicKey key, Provider sigProvider)
        throws CertificateException, NoSuchAlgorithmException,
        InvalidKeyException, SignatureException {
        X509CertImpl.verify(this, key, sigProvider);
    }
}
