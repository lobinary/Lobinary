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


package javax.security.cert;

import java.io.InputStream;
import java.lang.Class;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.Security;

import java.math.BigInteger;
import java.security.AccessController;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.security.PublicKey;
import java.util.BitSet;
import java.util.Date;

/**
 * Abstract class for X.509 v1 certificates. This provides a standard
 * way to access all the version 1 attributes of an X.509 certificate.
 * Attributes that are specific to X.509 v2 or v3 are not available
 * through this interface. Future API evolution will provide full access to
 * complete X.509 v3 attributes.
 * <p>
 * The basic X.509 format was defined by
 * ISO/IEC and ANSI X9 and is described below in ASN.1:
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
 *     }
 * </pre>
 * <p>
 * Here is sample code to instantiate an X.509 certificate:
 * <pre>
 * InputStream inStream = new FileInputStream("fileName-of-cert");
 * X509Certificate cert = X509Certificate.getInstance(inStream);
 * inStream.close();
 * </pre>
 * OR
 * <pre>
 * byte[] certData = &lt;certificate read from a file, say&gt;
 * X509Certificate cert = X509Certificate.getInstance(certData);
 * </pre>
 * <p>
 * In either case, the code that instantiates an X.509 certificate
 * consults the value of the {@code cert.provider.x509v1} security property
 * to locate the actual implementation or instantiates a default implementation.
 * <p>
 * The {@code cert.provider.x509v1} property is set to a default
 * implementation for X.509 such as:
 * <pre>
 * cert.provider.x509v1=com.sun.security.cert.internal.x509.X509V1CertImpl
 * </pre>
 * <p>
 * The value of this {@code cert.provider.x509v1} property has to be
 * changed to instantiate another implementation. If this security
 * property is not set, a default implementation will be used.
 * Currently, due to possible security restrictions on access to
 * Security properties, this value is looked up and cached at class
 * initialization time and will fallback on a default implementation if
 * the Security property is not accessible.
 *
 * <p><em>Note: The classes in the package {@code javax.security.cert}
 * exist for compatibility with earlier versions of the
 * Java Secure Sockets Extension (JSSE). New applications should instead
 * use the standard Java SE certificate classes located in
 * {@code java.security.cert}.</em></p>
 *
 * <p>
 *  X.509 v1证书的抽象类。这提供了访问X.509证书的所有版本1属性的标准方法。特定于X.509 v2或v3的属性不可通过此接口使用。未来的API演进将提供完整的访问完整的X.509 v3属性。
 * <p>
 *  基本的X.509格式由ISO / IEC和ANSI X9定义,并在ASN.1中描述如下：
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
 *  {@code tbsCertificate}的ASN.1定义是：
 * <pre>
 * TBSCertificate :: = SEQUENCE {version [0] EXPLICIT Version DEFAULT v1,serialNumber CertificateSerialNumber,signature AlgorithmIdentifier,issuer Name,validity有效性,主题名,subjectPublicKeyInfo SubjectPublicKeyInfo,。
 * </pre>
 * <p>
 *  以下是实例化X.509证书的示例代码：
 * <pre>
 *  InputStream inStream = new FileInputStream("fileName-of-cert"); X509Certificate cert = X509Certifica
 * te.getInstance(inStream); inStream.close();。
 * </pre>
 *  要么
 * <pre>
 *  byte [] certData =&lt;证书从文件读取,说&gt; X509Certificate cert = X509Certificate.getInstance(certData);
 * </pre>
 * <p>
 *  在任何一种情况下,实例化X.509证书的代码都会查阅{@code cert.provider.x509v1}安全属性的值来定位实际实现或实例化默认实现。
 * <p>
 *  {@code cert.provider.x509v1}属性设置为X.509的默认实现,例如：
 * <pre>
 *  cert.provider.x509v1 = com.sun.security.cert.internal.x509.X509V1CertImpl
 * </pre>
 * <p>
 *  此{@code cert.provider.x509v1}属性的值必须更改为实例化另一个实现。如果未设置此安全属性,将使用默认实现。
 * 目前,由于对访问安全属性的可能的安全限制,在类初始化时查找和缓存此值,如果Security属性不可访问,则将默认实现。
 * 
 * <p> <em>注意：包{@code javax.security.cert}中的类是为了与早期版本的Java安全套接字扩展(JSSE)兼容而存在。
 * 新应用程序应使用位于{@code java.security.cert}中的标准Java SE证书类。</em> </p>。
 * 
 * 
 * @author Hemma Prafullchandra
 * @since 1.4
 * @see Certificate
 * @see java.security.cert.X509Extension
 * @see java.security.Security security properties
 */
public abstract class X509Certificate extends Certificate {

    /*
     * Constant to lookup in the Security properties file.
     * In the Security properties file the default implementation
     * for X.509 v3 is given as:
     * <pre>
     * cert.provider.x509v1=com.sun.security.cert.internal.x509.X509V1CertImpl
     * </pre>
     * <p>
     *  在安全属性文件中查找的常量。在安全属性文件中,X.509 v3的默认实现为：
     * <pre>
     *  cert.provider.x509v1 = com.sun.security.cert.internal.x509.X509V1CertImpl
     * </pre>
     */
    private static final String X509_PROVIDER = "cert.provider.x509v1";
    private static String X509Provider;

    static {
        X509Provider = AccessController.doPrivileged(
            new PrivilegedAction<String>() {
                public String run() {
                    return Security.getProperty(X509_PROVIDER);
                }
            }
        );
    }

    /**
     * Instantiates an X509Certificate object, and initializes it with
     * the data read from the input stream {@code inStream}.
     * The implementation (X509Certificate is an abstract class) is
     * provided by the class specified as the value of the
     * {@code cert.provider.x509v1} security property.
     *
     * <p>Note: Only one DER-encoded
     * certificate is expected to be in the input stream.
     * Also, all X509Certificate
     * subclasses must provide a constructor of the form:
     * <pre>{@code
     * public <subClass>(InputStream inStream) ...
     * }</pre>
     *
     * <p>
     *  实例化X509Certificate对象,并使用从输入流{@code inStream}读取的数据对其进行初始化。
     * 实现(X509Certificate是一个抽象类)由指定为{@code cert.provider.x509v1}安全属性值的类提供。
     * 
     *  <p>注意：预期在输入流中只有一个DER编码的证书。
     * 此外,所有X509Certificate子类必须提供一个形式的构造函数：<pre> {@ code public <subClass>(InputStream inStream)...} </pre>。
     *  <p>注意：预期在输入流中只有一个DER编码的证书。
     * 
     * 
     * @param inStream an input stream with the data to be read to
     *        initialize the certificate.
     * @return an X509Certificate object initialized with the data
     *         from the input stream.
     * @exception CertificateException if a class initialization
     *            or certificate parsing error occurs.
     */
    public static final X509Certificate getInstance(InputStream inStream)
    throws CertificateException {
        return getInst((Object)inStream);
    }

    /**
     * Instantiates an X509Certificate object, and initializes it with
     * the specified byte array.
     * The implementation (X509Certificate is an abstract class) is
     * provided by the class specified as the value of the
     * {@code cert.provider.x509v1} security property.
     *
     * <p>Note: All X509Certificate
     * subclasses must provide a constructor of the form:
     * <pre>{@code
     * public <subClass>(InputStream inStream) ...
     * }</pre>
     *
     * <p>
     *  实例化X509Certificate对象,并使用指定的字节数组初始化它。
     * 实现(X509Certificate是一个抽象类)由指定为{@code cert.provider.x509v1}安全属性值的类提供。
     * 
     *  <p>注意：所有X509Certificate子类必须提供一个形式的构造函数：<pre> {@ code public <subClass>(InputStream inStream)...} </pre>
     * 。
     * 
     * 
     * @param certData a byte array containing the DER-encoded
     *        certificate.
     * @return an X509Certificate object initialized with the data
     *         from {@code certData}.
     * @exception CertificateException if a class initialization
     *            or certificate parsing error occurs.
     */
    public static final X509Certificate getInstance(byte[] certData)
    throws CertificateException {
        return getInst((Object)certData);
    }

    private static final X509Certificate getInst(Object value)
    throws CertificateException {
        /*
         * This turns out not to work for now. To run under JDK1.2 we would
         * need to call beginPrivileged() but we can't do that and run
         * under JDK1.1.
         * <p>
         * 这结果现在不工作。要在JDK1.2下运行,我们需要调用beginPrivileged(),但是我们不能这样做,并在JDK1.1下运行。
         * 
         */
        String className = X509Provider;
        if (className == null || className.length() == 0) {
            // shouldn't happen, but assume corrupted properties file
            // provide access to sun implementation
            className = "com.sun.security.cert.internal.x509.X509V1CertImpl";
        }
        try {
            Class<?>[] params = null;
            if (value instanceof InputStream) {
                params = new Class<?>[] { InputStream.class };
            } else if (value instanceof byte[]) {
                params = new Class<?>[] { value.getClass() };
            } else
                throw new CertificateException("Unsupported argument type");
            Class<?> certClass = Class.forName(className);

            // get the appropriate constructor and instantiate it
            Constructor<?> cons = certClass.getConstructor(params);

            // get a new instance
            Object obj = cons.newInstance(new Object[] {value});
            return (X509Certificate)obj;

        } catch (ClassNotFoundException e) {
          throw new CertificateException("Could not find class: " + e);
        } catch (IllegalAccessException e) {
          throw new CertificateException("Could not access class: " + e);
        } catch (InstantiationException e) {
          throw new CertificateException("Problems instantiating: " + e);
        } catch (InvocationTargetException e) {
          throw new CertificateException("InvocationTargetException: "
                                         + e.getTargetException());
        } catch (NoSuchMethodException e) {
          throw new CertificateException("Could not find class method: "
                                          + e.getMessage());
        }
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
     *            yet valid.
     */
    public abstract void checkValidity()
        throws CertificateExpiredException, CertificateNotYetValidException;

    /**
     * Checks that the specified date is within the certificate's
     * validity period. In other words, this determines whether the
     * certificate would be valid at the specified date/time.
     *
     * <p>
     *  检查指定的日期是否在证书的有效期内。换句话说,这决定了证书在指定的日期/时间是否有效。
     * 
     * 
     * @param date the Date to check against to see if this certificate
     *        is valid at that date/time.
     * @exception CertificateExpiredException if the certificate has expired
     *            with respect to the {@code date} supplied.
     * @exception CertificateNotYetValidException if the certificate is not
     *            yet valid with respect to the {@code date} supplied.
     * @see #checkValidity()
     */
    public abstract void checkValidity(Date date)
        throws CertificateExpiredException, CertificateNotYetValidException;

    /**
     * Gets the {@code version} (version number) value from the
     * certificate. The ASN.1 definition for this is:
     * <pre>
     * version         [0]  EXPLICIT Version DEFAULT v1
     *
     * Version  ::=  INTEGER  {  v1(0), v2(1), v3(2)  }
     * </pre>
     *
     * <p>
     *  从证书获取{@code版本}(版本号)值。对此的ASN.1定义是：
     * <pre>
     *  version [0] EXPLICIT版本DEFAULT v1
     * 
     *  Version :: = INTEGER {v1(0),v2(1),v3(2)}
     * </pre>
     * 
     * 
     * @return the version number from the ASN.1 encoding, i.e. 0, 1 or 2.
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
     * attributes, such as country name, and corresponding values, such as US.
     * The type of the {@code AttributeValue} component is determined by
     * the {@code AttributeType}; in general it will be a
     * {@code directoryString}. A {@code directoryString} is usually
     * one of {@code PrintableString},
     * {@code TeletexString} or {@code UniversalString}.
     *
     * <p>
     * 从证书获取{@code issuer}(颁发者专有名称)值。发放者名称标识签署(并颁发)证书的实体。
     * 
     *  <p>发行者名称字段包含X.500可分辨名称(DN)。对此的ASN.1定义是：
     * <pre>
     *  发行人名称
     * 
     *  Name :: = CHOICE {RDNSequence} RDNSequence :: = SEQUENCE OF RelativeDistinguishedName RelativeDistin
     * guishedName :: = SET OF AttributeValueAssertion。
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
     * Gets the {@code subject} (subject distinguished name) value
     * from the certificate.
     * The ASN.1 definition for this is:
     * <pre>
     * subject    Name
     * </pre>
     *
     * <p>See {@link #getIssuerDN() getIssuerDN} for {@code Name}
     * and other relevant definitions.
     *
     * <p>
     *  从证书获取{@code subject}(主题专有名称)值。对此的ASN.1定义是：
     * <pre>
     *  主题名称
     * </pre>
     * 
     *  <p>有关{@code Name}和其他相关定义,请参阅{@link #getIssuerDN()getIssuerDN}。
     * 
     * 
     * @return a Principal whose name is the subject name.
     * @see #getIssuerDN()
     */
    public abstract Principal getSubjectDN();

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
     * CertificateValidityDate :: = CHOICE {utcTime UTCTime,generalTime GeneralizedTime}
     * </pre>
     * 
     * 
     * @return the start date of the validity period.
     * @see #checkValidity()
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
     * @see #checkValidity()
     */
    public abstract Date getNotAfter();

    /**
     * Gets the signature algorithm name for the certificate
     * signature algorithm. An example is the string "SHA-1/DSA".
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
     *  获取证书签名算法的签名算法名称。一个例子是字符串"SHA-1 / DSA"。对此的ASN.1定义是：
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
     * Gets the signature algorithm OID string from the certificate.
     * An OID is represented by a set of positive whole numbers separated
     * by periods.
     * For example, the string "1.2.840.10040.4.3" identifies the SHA-1
     * with DSA signature algorithm, as per the PKIX part I.
     *
     * <p>See {@link #getSigAlgName() getSigAlgName} for
     * relevant ASN.1 definitions.
     *
     * <p>
     * 
     * @return the signature algorithm OID string.
     */
    public abstract String getSigAlgOID();

    /**
     * Gets the DER-encoded signature algorithm parameters from this
     * certificate's signature algorithm. In most cases, the signature
     * algorithm parameters are null; the parameters are usually
     * supplied with the certificate's public key.
     *
     * <p>See {@link #getSigAlgName() getSigAlgName} for
     * relevant ASN.1 definitions.
     *
     * <p>
     *  从证书获取签名算法OID字符串。 OID由用句点分隔的一组正整数表示。例如,字符串"1.2.840.10040.4.3"根据PKIX第一部分标识具有DSA签名算法的SHA-1。
     * 
     *  <p>有关ASN.1定义,请参阅{@link #getSigAlgName()getSigAlgName}。
     * 
     * 
     * @return the DER-encoded signature algorithm parameters, or
     *         null if no parameters are present.
     */
    public abstract byte[] getSigAlgParams();
}
