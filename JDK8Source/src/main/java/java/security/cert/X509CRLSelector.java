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

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

import javax.security.auth.x500.X500Principal;

import sun.security.util.Debug;
import sun.security.util.DerInputStream;
import sun.security.x509.CRLNumberExtension;
import sun.security.x509.X500Name;

/**
 * A {@code CRLSelector} that selects {@code X509CRLs} that
 * match all specified criteria. This class is particularly useful when
 * selecting CRLs from a {@code CertStore} to check revocation status
 * of a particular certificate.
 * <p>
 * When first constructed, an {@code X509CRLSelector} has no criteria
 * enabled and each of the {@code get} methods return a default
 * value ({@code null}). Therefore, the {@link #match match} method
 * would return {@code true} for any {@code X509CRL}. Typically,
 * several criteria are enabled (by calling {@link #setIssuers setIssuers}
 * or {@link #setDateAndTime setDateAndTime}, for instance) and then the
 * {@code X509CRLSelector} is passed to
 * {@link CertStore#getCRLs CertStore.getCRLs} or some similar
 * method.
 * <p>
 * Please refer to <a href="http://www.ietf.org/rfc/rfc3280.txt">RFC 3280:
 * Internet X.509 Public Key Infrastructure Certificate and CRL Profile</a>
 * for definitions of the X.509 CRL fields and extensions mentioned below.
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
 *  用于选择与所有指定条件匹配的{@code X509CRLs}的{@code CRLSelector}。当从{@code CertStore}中选择CRL以检查特定证书的撤销状态时,此类尤其有用。
 * <p>
 *  当第一次构造时,{@code X509CRLSelector}没有启用标准,每个{@code get}方法都返回一个默认值({@code null})。
 * 因此,对于任何{@code X509CRL},{@link #match match}方法将返回{@code true}。
 * 通常,启用几个条件(例如调用{@link #setIssuers setIssuers}或{@link #setDateAndTime setDateAndTime}),然后将{@code X509CRLSelector}
 * 传递给{@link CertStore#getCRLs CertStore.getCRLs}或一些类似的方法。
 * 因此,对于任何{@code X509CRL},{@link #match match}方法将返回{@code true}。
 * <p>
 *  有关X.509的定义,请参阅<a href="http://www.ietf.org/rfc/rfc3280.txt"> RFC 3280：Internet X.509公钥基础结构证书和CRL配置文件
 * </a> CRL字段和扩展名。
 * <p>
 *  <b>并行访问</b>
 * <p>
 *  除非另有说明,否则此类中定义的方法不是线程安全的。需要并发访问单个对象的多个线程应在它们之间同步并提供必要的锁定。每个操作单独对象的多个线程不需要同步。
 * 
 * 
 * @see CRLSelector
 * @see X509CRL
 *
 * @since       1.4
 * @author      Steve Hanna
 */
public class X509CRLSelector implements CRLSelector {

    static {
        CertPathHelperImpl.initialize();
    }

    private static final Debug debug = Debug.getInstance("certpath");
    private HashSet<Object> issuerNames;
    private HashSet<X500Principal> issuerX500Principals;
    private BigInteger minCRL;
    private BigInteger maxCRL;
    private Date dateAndTime;
    private X509Certificate certChecking;
    private long skew = 0;

    /**
     * Creates an {@code X509CRLSelector}. Initially, no criteria are set
     * so any {@code X509CRL} will match.
     * <p>
     * 创建{@code X509CRLSelector}。最初,没有设置条件,因此任何{@code X509CRL}都会匹配。
     * 
     */
    public X509CRLSelector() {}

    /**
     * Sets the issuerNames criterion. The issuer distinguished name in the
     * {@code X509CRL} must match at least one of the specified
     * distinguished names. If {@code null}, any issuer distinguished name
     * will do.
     * <p>
     * This method allows the caller to specify, with a single method call,
     * the complete set of issuer names which {@code X509CRLs} may contain.
     * The specified value replaces the previous value for the issuerNames
     * criterion.
     * <p>
     * The {@code names} parameter (if not {@code null}) is a
     * {@code Collection} of {@code X500Principal}s.
     * <p>
     * Note that the {@code names} parameter can contain duplicate
     * distinguished names, but they may be removed from the
     * {@code Collection} of names returned by the
     * {@link #getIssuers getIssuers} method.
     * <p>
     * Note that a copy is performed on the {@code Collection} to
     * protect against subsequent modifications.
     *
     * <p>
     *  设置issuerNames条件。 {@code X509CRL}中的发行商专有名称必须与指定的专有名称中的至少一个匹配。如果{@code null},任何发行者专有名称都会做。
     * <p>
     *  此方法允许调用者通过一个方法调用指定{@code X509CRLs}可能包含的完整的发行者名称集合。指定的值替换issuerNames条件的先前值。
     * <p>
     *  {@code names}参数(如果不是{@code null})是{@code X500Principal}的{@code集合}。
     * <p>
     *  请注意,{@code names}参数可以包含重复的专有名称,但可以从{@link #getIssuers getIssuers}方法返回的名称的{@code Collection}中删除。
     * <p>
     *  请注意,在{@code集合}上执行复制以防止后续修改。
     * 
     * 
     * @param issuers a {@code Collection} of X500Principals
     *   (or {@code null})
     * @see #getIssuers
     * @since 1.5
     */
    public void setIssuers(Collection<X500Principal> issuers) {
        if ((issuers == null) || issuers.isEmpty()) {
            issuerNames = null;
            issuerX500Principals = null;
        } else {
            // clone
            issuerX500Principals = new HashSet<X500Principal>(issuers);
            issuerNames = new HashSet<Object>();
            for (X500Principal p : issuerX500Principals) {
                issuerNames.add(p.getEncoded());
            }
        }
    }

    /**
     * <strong>Note:</strong> use {@linkplain #setIssuers(Collection)} instead
     * or only specify the byte array form of distinguished names when using
     * this method. See {@link #addIssuerName(String)} for more information.
     * <p>
     * Sets the issuerNames criterion. The issuer distinguished name in the
     * {@code X509CRL} must match at least one of the specified
     * distinguished names. If {@code null}, any issuer distinguished name
     * will do.
     * <p>
     * This method allows the caller to specify, with a single method call,
     * the complete set of issuer names which {@code X509CRLs} may contain.
     * The specified value replaces the previous value for the issuerNames
     * criterion.
     * <p>
     * The {@code names} parameter (if not {@code null}) is a
     * {@code Collection} of names. Each name is a {@code String}
     * or a byte array representing a distinguished name (in
     * <a href="http://www.ietf.org/rfc/rfc2253.txt">RFC 2253</a> or
     * ASN.1 DER encoded form, respectively). If {@code null} is supplied
     * as the value for this argument, no issuerNames check will be performed.
     * <p>
     * Note that the {@code names} parameter can contain duplicate
     * distinguished names, but they may be removed from the
     * {@code Collection} of names returned by the
     * {@link #getIssuerNames getIssuerNames} method.
     * <p>
     * If a name is specified as a byte array, it should contain a single DER
     * encoded distinguished name, as defined in X.501. The ASN.1 notation for
     * this structure is as follows.
     * <pre>{@code
     * Name ::= CHOICE {
     *   RDNSequence }
     *
     * RDNSequence ::= SEQUENCE OF RelativeDistinguishedName
     *
     * RelativeDistinguishedName ::=
     *   SET SIZE (1 .. MAX) OF AttributeTypeAndValue
     *
     * AttributeTypeAndValue ::= SEQUENCE {
     *   type     AttributeType,
     *   value    AttributeValue }
     *
     * AttributeType ::= OBJECT IDENTIFIER
     *
     * AttributeValue ::= ANY DEFINED BY AttributeType
     * ....
     * DirectoryString ::= CHOICE {
     *       teletexString           TeletexString (SIZE (1..MAX)),
     *       printableString         PrintableString (SIZE (1..MAX)),
     *       universalString         UniversalString (SIZE (1..MAX)),
     *       utf8String              UTF8String (SIZE (1.. MAX)),
     *       bmpString               BMPString (SIZE (1..MAX)) }
     * }</pre>
     * <p>
     * Note that a deep copy is performed on the {@code Collection} to
     * protect against subsequent modifications.
     *
     * <p>
     *  <strong>请注意</strong>：使用{@linkplain #setIssuers(Collection)},或者仅使用此方法指定可分辨名称的字节数组形式。
     * 有关详细信息,请参阅{@link #addIssuerName(String)}。
     * <p>
     *  设置issuerNames条件。 {@code X509CRL}中的发行商专有名称必须与指定的专有名称中的至少一个匹配。如果{@code null},任何发行者专有名称都会做。
     * <p>
     * 此方法允许调用者通过一个方法调用指定{@code X509CRLs}可能包含的完整的发行者名称集合。指定的值替换issuerNames条件的先前值。
     * <p>
     *  {@code names}参数(如果不是{@code null})是一个{@code Collection}的名称。
     * 每个名称都是{@code String}或字节数组,表示专有名称(在<a href="http://www.ietf.org/rfc/rfc2253.txt"> RFC 2253 </a>或ASN中)。
     *  {@code names}参数(如果不是{@code null})是一个{@code Collection}的名称。 1 DER编码形式)。
     * 如果提供{@code null}作为此参数的值,将不执行issuerNames检查。
     * <p>
     *  请注意,{@code names}参数可以包含重复的专有名称,但它们可能会从{@link #getIssuerNames getIssuerNames}方法返回的名称的{@code Collection}
     * 中删除。
     * <p>
     *  如果一个名称被指定为一个字节数组,它应该包含一个单独的DER编码的识别名,如X.501中定义的。该结构的ASN.1表示法如下。
     *  <pre> {@ code Name :: = CHOICE {RDNSequence}。
     * 
     *  RDNSequence :: = SEQUENCE OF RelativeDistinguishedName
     * 
     *  RelativeDistinguishedName :: = SET SIZE(1.. MAX)of AttributeTypeAndValue
     * 
     *  AttributeTypeAndValue :: = SEQUENCE {type AttributeType,value AttributeValue}
     * 
     *  AttributeType :: = OBJECT IDENTIFIER
     * 
     * AttributeValue :: = ANY DEFINED BY AttributeType .... DirectoryString :: = CHOICE {teletexString TeletexString(SIZE(1..MAX)),printableString PrintableString(SIZE(1..MAX)),universalString UniversalString(SIZE MAX)),utf8String UTF8String(SIZE(1..MAX)),bmpString BMPString(SIZE(1..MAX))}
     * } </pre>。
     * <p>
     *  请注意,在{@code集合}上执行深层复制,以防止后续修改。
     * 
     * 
     * @param names a {@code Collection} of names (or {@code null})
     * @throws IOException if a parsing error occurs
     * @see #getIssuerNames
     */
    public void setIssuerNames(Collection<?> names) throws IOException {
        if (names == null || names.size() == 0) {
            issuerNames = null;
            issuerX500Principals = null;
        } else {
            HashSet<Object> tempNames = cloneAndCheckIssuerNames(names);
            // Ensure that we either set both of these or neither
            issuerX500Principals = parseIssuerNames(tempNames);
            issuerNames = tempNames;
        }
    }

    /**
     * Adds a name to the issuerNames criterion. The issuer distinguished
     * name in the {@code X509CRL} must match at least one of the specified
     * distinguished names.
     * <p>
     * This method allows the caller to add a name to the set of issuer names
     * which {@code X509CRLs} may contain. The specified name is added to
     * any previous value for the issuerNames criterion.
     * If the specified name is a duplicate, it may be ignored.
     *
     * <p>
     *  向issuerNames条件添加名称。 {@code X509CRL}中的发行商专有名称必须与指定的专有名称中的至少一个匹配。
     * <p>
     *  此方法允许调用者为{@code X509CRLs}可能包含的发布者名称集添加一个名称。指定的名称将添加到issuerNames条件的任何先前值。如果指定的名称是重复的,则可以忽略它。
     * 
     * 
     * @param issuer the issuer as X500Principal
     * @since 1.5
     */
    public void addIssuer(X500Principal issuer) {
        addIssuerNameInternal(issuer.getEncoded(), issuer);
    }

    /**
     * <strong>Denigrated</strong>, use
     * {@linkplain #addIssuer(X500Principal)} or
     * {@linkplain #addIssuerName(byte[])} instead. This method should not be
     * relied on as it can fail to match some CRLs because of a loss of
     * encoding information in the RFC 2253 String form of some distinguished
     * names.
     * <p>
     * Adds a name to the issuerNames criterion. The issuer distinguished
     * name in the {@code X509CRL} must match at least one of the specified
     * distinguished names.
     * <p>
     * This method allows the caller to add a name to the set of issuer names
     * which {@code X509CRLs} may contain. The specified name is added to
     * any previous value for the issuerNames criterion.
     * If the specified name is a duplicate, it may be ignored.
     *
     * <p>
     *  <strong>已拒绝</strong>,请改用{@linkplain #addIssuer(X500Principal)}或{@linkplain #addIssuerName(byte [])}。
     * 不应该依赖此方法,因为它可能无法匹配某些CRL,因为某些可分辨名称的RFC 2253 String形式中的编码信息丢失。
     * <p>
     *  向issuerNames条件添加名称。 {@code X509CRL}中的发行商专有名称必须与指定的专有名称中的至少一个匹配。
     * <p>
     * 此方法允许调用者为{@code X509CRLs}可能包含的发布者名称集添加一个名称。指定的名称将添加到issuerNames条件的任何先前值。如果指定的名称是重复的,则可以忽略它。
     * 
     * 
     * @param name the name in RFC 2253 form
     * @throws IOException if a parsing error occurs
     */
    public void addIssuerName(String name) throws IOException {
        addIssuerNameInternal(name, new X500Name(name).asX500Principal());
    }

    /**
     * Adds a name to the issuerNames criterion. The issuer distinguished
     * name in the {@code X509CRL} must match at least one of the specified
     * distinguished names.
     * <p>
     * This method allows the caller to add a name to the set of issuer names
     * which {@code X509CRLs} may contain. The specified name is added to
     * any previous value for the issuerNames criterion. If the specified name
     * is a duplicate, it may be ignored.
     * If a name is specified as a byte array, it should contain a single DER
     * encoded distinguished name, as defined in X.501. The ASN.1 notation for
     * this structure is as follows.
     * <p>
     * The name is provided as a byte array. This byte array should contain
     * a single DER encoded distinguished name, as defined in X.501. The ASN.1
     * notation for this structure appears in the documentation for
     * {@link #setIssuerNames setIssuerNames(Collection names)}.
     * <p>
     * Note that the byte array supplied here is cloned to protect against
     * subsequent modifications.
     *
     * <p>
     *  向issuerNames条件添加名称。 {@code X509CRL}中的发行商专有名称必须与指定的专有名称中的至少一个匹配。
     * <p>
     *  此方法允许调用者为{@code X509CRLs}可能包含的发布者名称集添加一个名称。指定的名称将添加到issuerNames条件的任何先前值。如果指定的名称是重复的,则可以忽略它。
     * 如果一个名称被指定为一个字节数组,它应该包含一个单独的DER编码的识别名,如X.501中定义的。该结构的ASN.1表示法如下。
     * <p>
     *  名称以字节数组形式提供。此字节数组应包含单个DER编码的可分辨名称,如X.501中定义。
     * 此结构的ASN.1表示法出现在{@link #setIssuerNames setIssuerNames(集合名称)}的文档中。
     * <p>
     *  请注意,此处提供的字节数组被克隆以防止后续修改。
     * 
     * 
     * @param name a byte array containing the name in ASN.1 DER encoded form
     * @throws IOException if a parsing error occurs
     */
    public void addIssuerName(byte[] name) throws IOException {
        // clone because byte arrays are modifiable
        addIssuerNameInternal(name.clone(), new X500Name(name).asX500Principal());
    }

    /**
     * A private method that adds a name (String or byte array) to the
     * issuerNames criterion. The issuer distinguished
     * name in the {@code X509CRL} must match at least one of the specified
     * distinguished names.
     *
     * <p>
     *  向issuerNames条件添加名称(字符串或字节数组)的私有方法。 {@code X509CRL}中的发行商专有名称必须与指定的专有名称中的至少一个匹配。
     * 
     * 
     * @param name the name in string or byte array form
     * @param principal the name in X500Principal form
     * @throws IOException if a parsing error occurs
     */
    private void addIssuerNameInternal(Object name, X500Principal principal) {
        if (issuerNames == null) {
            issuerNames = new HashSet<Object>();
        }
        if (issuerX500Principals == null) {
            issuerX500Principals = new HashSet<X500Principal>();
        }
        issuerNames.add(name);
        issuerX500Principals.add(principal);
    }

    /**
     * Clone and check an argument of the form passed to
     * setIssuerNames. Throw an IOException if the argument is malformed.
     *
     * <p>
     *  克隆并检查传递给setIssuerNames的表单的参数。如果参数格式不正确,则抛出IOException。
     * 
     * 
     * @param names a {@code Collection} of names. Each entry is a
     *              String or a byte array (the name, in string or ASN.1
     *              DER encoded form, respectively). {@code null} is
     *              not an acceptable value.
     * @return a deep copy of the specified {@code Collection}
     * @throws IOException if a parsing error occurs
     */
    private static HashSet<Object> cloneAndCheckIssuerNames(Collection<?> names)
        throws IOException
    {
        HashSet<Object> namesCopy = new HashSet<Object>();
        Iterator<?> i = names.iterator();
        while (i.hasNext()) {
            Object nameObject = i.next();
            if (!(nameObject instanceof byte []) &&
                !(nameObject instanceof String))
                throw new IOException("name not byte array or String");
            if (nameObject instanceof byte [])
                namesCopy.add(((byte []) nameObject).clone());
            else
                namesCopy.add(nameObject);
        }
        return(namesCopy);
    }

    /**
     * Clone an argument of the form passed to setIssuerNames.
     * Throw a RuntimeException if the argument is malformed.
     * <p>
     * This method wraps cloneAndCheckIssuerNames, changing any IOException
     * into a RuntimeException. This method should be used when the object being
     * cloned has already been checked, so there should never be any exceptions.
     *
     * <p>
     * 克隆传递给setIssuerNames的表单的参数。如果参数格式不正确,则抛出RuntimeException。
     * <p>
     *  此方法包装cloneAndCheckIssuerNames,将任何IOException更改为RuntimeException。当克隆的对象已经被检查时,应该使用此方法,因此不应该有任何异常。
     * 
     * 
     * @param names a {@code Collection} of names. Each entry is a
     *              String or a byte array (the name, in string or ASN.1
     *              DER encoded form, respectively). {@code null} is
     *              not an acceptable value.
     * @return a deep copy of the specified {@code Collection}
     * @throws RuntimeException if a parsing error occurs
     */
    private static HashSet<Object> cloneIssuerNames(Collection<Object> names) {
        try {
            return cloneAndCheckIssuerNames(names);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    /**
     * Parse an argument of the form passed to setIssuerNames,
     * returning a Collection of issuerX500Principals.
     * Throw an IOException if the argument is malformed.
     *
     * <p>
     *  解析传递给setIssuerNames的表单的参数,返回issuerX500Principals的集合。如果参数格式不正确,则抛出IOException。
     * 
     * 
     * @param names a {@code Collection} of names. Each entry is a
     *              String or a byte array (the name, in string or ASN.1
     *              DER encoded form, respectively). <Code>Null</Code> is
     *              not an acceptable value.
     * @return a HashSet of issuerX500Principals
     * @throws IOException if a parsing error occurs
     */
    private static HashSet<X500Principal> parseIssuerNames(Collection<Object> names)
    throws IOException {
        HashSet<X500Principal> x500Principals = new HashSet<X500Principal>();
        for (Iterator<Object> t = names.iterator(); t.hasNext(); ) {
            Object nameObject = t.next();
            if (nameObject instanceof String) {
                x500Principals.add(new X500Name((String)nameObject).asX500Principal());
            } else {
                try {
                    x500Principals.add(new X500Principal((byte[])nameObject));
                } catch (IllegalArgumentException e) {
                    throw (IOException)new IOException("Invalid name").initCause(e);
                }
            }
        }
        return x500Principals;
    }

    /**
     * Sets the minCRLNumber criterion. The {@code X509CRL} must have a
     * CRL number extension whose value is greater than or equal to the
     * specified value. If {@code null}, no minCRLNumber check will be
     * done.
     *
     * <p>
     *  设置minCRLNumber条件。 {@code X509CRL}必须有一个CRL号码扩展,其值大于或等于指定的值。如果{@code null},将不进行minCRLNumber检查。
     * 
     * 
     * @param minCRL the minimum CRL number accepted (or {@code null})
     */
    public void setMinCRLNumber(BigInteger minCRL) {
        this.minCRL = minCRL;
    }

    /**
     * Sets the maxCRLNumber criterion. The {@code X509CRL} must have a
     * CRL number extension whose value is less than or equal to the
     * specified value. If {@code null}, no maxCRLNumber check will be
     * done.
     *
     * <p>
     *  设置maxCRLNumber条件。 {@code X509CRL}必须有一个CRL号码扩展,其值小于或等于指定的值。如果{@code null},将不会执行maxCRLNumber检查。
     * 
     * 
     * @param maxCRL the maximum CRL number accepted (or {@code null})
     */
    public void setMaxCRLNumber(BigInteger maxCRL) {
        this.maxCRL = maxCRL;
    }

    /**
     * Sets the dateAndTime criterion. The specified date must be
     * equal to or later than the value of the thisUpdate component
     * of the {@code X509CRL} and earlier than the value of the
     * nextUpdate component. There is no match if the {@code X509CRL}
     * does not contain a nextUpdate component.
     * If {@code null}, no dateAndTime check will be done.
     * <p>
     * Note that the {@code Date} supplied here is cloned to protect
     * against subsequent modifications.
     *
     * <p>
     *  设置dateAndTime标准。指定的日期必须等于或晚于{@code X509CRL}的thisUpdate组件的值,且早于nextUpdate组件的值。
     * 如果{@code X509CRL}不包含nextUpdate组件,则不存在匹配项。如果{@code null},将不会执行dateAndTime检查。
     * <p>
     *  请注意,此处提供的{@code Date}已克隆,以防后续修改。
     * 
     * 
     * @param dateAndTime the {@code Date} to match against
     *                    (or {@code null})
     * @see #getDateAndTime
     */
    public void setDateAndTime(Date dateAndTime) {
        if (dateAndTime == null)
            this.dateAndTime = null;
        else
            this.dateAndTime = new Date(dateAndTime.getTime());
        this.skew = 0;
    }

    /**
     * Sets the dateAndTime criterion and allows for the specified clock skew
     * (in milliseconds) when checking against the validity period of the CRL.
     * <p>
     *  设置dateAndTime标准,并允许在检查CRL的有效期时指定的时钟偏差(以毫秒为单位)。
     * 
     */
    void setDateAndTime(Date dateAndTime, long skew) {
        this.dateAndTime =
            (dateAndTime == null ? null : new Date(dateAndTime.getTime()));
        this.skew = skew;
    }

    /**
     * Sets the certificate being checked. This is not a criterion. Rather,
     * it is optional information that may help a {@code CertStore}
     * find CRLs that would be relevant when checking revocation for the
     * specified certificate. If {@code null} is specified, then no
     * such optional information is provided.
     *
     * <p>
     * 设置要检查的证书。这不是一个标准。相反,它是可选信息,可以帮助{@code CertStore}查找在检查指定证书的撤销时相关的CRL。如果指定了{@code null},则不提供此类可选信息。
     * 
     * 
     * @param cert the {@code X509Certificate} being checked
     *             (or {@code null})
     * @see #getCertificateChecking
     */
    public void setCertificateChecking(X509Certificate cert) {
        certChecking = cert;
    }

    /**
     * Returns the issuerNames criterion. The issuer distinguished
     * name in the {@code X509CRL} must match at least one of the specified
     * distinguished names. If the value returned is {@code null}, any
     * issuer distinguished name will do.
     * <p>
     * If the value returned is not {@code null}, it is a
     * unmodifiable {@code Collection} of {@code X500Principal}s.
     *
     * <p>
     *  返回issuerNames条件。 {@code X509CRL}中的发行商专有名称必须与指定的专有名称中的至少一个匹配。如果返回的值是{@code null},任何发行者专有名称都会做。
     * <p>
     *  如果返回的值不是{@code null},则它是{@code X500Principal}的不可修改的{@code集合}。
     * 
     * 
     * @return an unmodifiable {@code Collection} of names
     *   (or {@code null})
     * @see #setIssuers
     * @since 1.5
     */
    public Collection<X500Principal> getIssuers() {
        if (issuerX500Principals == null) {
            return null;
        }
        return Collections.unmodifiableCollection(issuerX500Principals);
    }

    /**
     * Returns a copy of the issuerNames criterion. The issuer distinguished
     * name in the {@code X509CRL} must match at least one of the specified
     * distinguished names. If the value returned is {@code null}, any
     * issuer distinguished name will do.
     * <p>
     * If the value returned is not {@code null}, it is a
     * {@code Collection} of names. Each name is a {@code String}
     * or a byte array representing a distinguished name (in RFC 2253 or
     * ASN.1 DER encoded form, respectively).  Note that the
     * {@code Collection} returned may contain duplicate names.
     * <p>
     * If a name is specified as a byte array, it should contain a single DER
     * encoded distinguished name, as defined in X.501. The ASN.1 notation for
     * this structure is given in the documentation for
     * {@link #setIssuerNames setIssuerNames(Collection names)}.
     * <p>
     * Note that a deep copy is performed on the {@code Collection} to
     * protect against subsequent modifications.
     *
     * <p>
     *  返回issuerNames条件的副本。 {@code X509CRL}中的发行商专有名称必须与指定的专有名称中的至少一个匹配。如果返回的值是{@code null},任何发行者专有名称都会做。
     * <p>
     *  如果返回的值不是{@code null},它是一个{@code集合}的名称。
     * 每个名称是一个{@code String}或一个表示可分辨名称的字节数组(分别在RFC 2253或ASN.1 DER编码形式中)。请注意,返回的{@code Collection}可能包含重复的名称。
     * <p>
     *  如果一个名称被指定为一个字节数组,它应该包含一个单独的DER编码的识别名,如X.501中定义的。
     * 此结构的ASN.1表示法在{@link #setIssuerNames setIssuerNames(集合名称)}的文档中给出。
     * <p>
     * 请注意,在{@code集合}上执行深层复制,以防止后续修改。
     * 
     * 
     * @return a {@code Collection} of names (or {@code null})
     * @see #setIssuerNames
     */
    public Collection<Object> getIssuerNames() {
        if (issuerNames == null) {
            return null;
        }
        return cloneIssuerNames(issuerNames);
    }

    /**
     * Returns the minCRLNumber criterion. The {@code X509CRL} must have a
     * CRL number extension whose value is greater than or equal to the
     * specified value. If {@code null}, no minCRLNumber check will be done.
     *
     * <p>
     *  返回minCRLNumber条件。 {@code X509CRL}必须有一个CRL号码扩展,其值大于或等于指定的值。如果{@code null},将不进行minCRLNumber检查。
     * 
     * 
     * @return the minimum CRL number accepted (or {@code null})
     */
    public BigInteger getMinCRL() {
        return minCRL;
    }

    /**
     * Returns the maxCRLNumber criterion. The {@code X509CRL} must have a
     * CRL number extension whose value is less than or equal to the
     * specified value. If {@code null}, no maxCRLNumber check will be
     * done.
     *
     * <p>
     *  返回maxCRLNumber条件。 {@code X509CRL}必须有一个CRL号码扩展,其值小于或等于指定的值。如果{@code null},将不会执行maxCRLNumber检查。
     * 
     * 
     * @return the maximum CRL number accepted (or {@code null})
     */
    public BigInteger getMaxCRL() {
        return maxCRL;
    }

    /**
     * Returns the dateAndTime criterion. The specified date must be
     * equal to or later than the value of the thisUpdate component
     * of the {@code X509CRL} and earlier than the value of the
     * nextUpdate component. There is no match if the
     * {@code X509CRL} does not contain a nextUpdate component.
     * If {@code null}, no dateAndTime check will be done.
     * <p>
     * Note that the {@code Date} returned is cloned to protect against
     * subsequent modifications.
     *
     * <p>
     *  返回dateAndTime条件。指定的日期必须等于或晚于{@code X509CRL}的thisUpdate组件的值,且早于nextUpdate组件的值。
     * 如果{@code X509CRL}不包含nextUpdate组件,则不存在匹配项。如果{@code null},将不会执行dateAndTime检查。
     * <p>
     *  请注意,所返回的{@code Date}已克隆,以防止后续修改。
     * 
     * 
     * @return the {@code Date} to match against (or {@code null})
     * @see #setDateAndTime
     */
    public Date getDateAndTime() {
        if (dateAndTime == null)
            return null;
        return (Date) dateAndTime.clone();
    }

    /**
     * Returns the certificate being checked. This is not a criterion. Rather,
     * it is optional information that may help a {@code CertStore}
     * find CRLs that would be relevant when checking revocation for the
     * specified certificate. If the value returned is {@code null}, then
     * no such optional information is provided.
     *
     * <p>
     *  返回要检查的证书。这不是一个标准。相反,它是可选信息,可以帮助{@code CertStore}查找在检查指定证书的撤销时相关的CRL。如果返回的值是{@code null},则不提供此类可选信息。
     * 
     * 
     * @return the certificate being checked (or {@code null})
     * @see #setCertificateChecking
     */
    public X509Certificate getCertificateChecking() {
        return certChecking;
    }

    /**
     * Returns a printable representation of the {@code X509CRLSelector}.
     *
     * <p>
     *  返回{@code X509CRLSelector}的可打印表示。
     * 
     * 
     * @return a {@code String} describing the contents of the
     *         {@code X509CRLSelector}.
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("X509CRLSelector: [\n");
        if (issuerNames != null) {
            sb.append("  IssuerNames:\n");
            Iterator<Object> i = issuerNames.iterator();
            while (i.hasNext())
                sb.append("    " + i.next() + "\n");
        }
        if (minCRL != null)
            sb.append("  minCRLNumber: " + minCRL + "\n");
        if (maxCRL != null)
            sb.append("  maxCRLNumber: " + maxCRL + "\n");
        if (dateAndTime != null)
            sb.append("  dateAndTime: " + dateAndTime + "\n");
        if (certChecking != null)
            sb.append("  Certificate being checked: " + certChecking + "\n");
        sb.append("]");
        return sb.toString();
    }

    /**
     * Decides whether a {@code CRL} should be selected.
     *
     * <p>
     *  决定是否应选择{@code CRL}。
     * 
     * 
     * @param crl the {@code CRL} to be checked
     * @return {@code true} if the {@code CRL} should be selected,
     *         {@code false} otherwise
     */
    public boolean match(CRL crl) {
        if (!(crl instanceof X509CRL)) {
            return false;
        }
        X509CRL xcrl = (X509CRL)crl;

        /* match on issuer name */
        if (issuerNames != null) {
            X500Principal issuer = xcrl.getIssuerX500Principal();
            Iterator<X500Principal> i = issuerX500Principals.iterator();
            boolean found = false;
            while (!found && i.hasNext()) {
                if (i.next().equals(issuer)) {
                    found = true;
                }
            }
            if (!found) {
                if (debug != null) {
                    debug.println("X509CRLSelector.match: issuer DNs "
                        + "don't match");
                }
                return false;
            }
        }

        if ((minCRL != null) || (maxCRL != null)) {
            /* Get CRL number extension from CRL */
            byte[] crlNumExtVal = xcrl.getExtensionValue("2.5.29.20");
            if (crlNumExtVal == null) {
                if (debug != null) {
                    debug.println("X509CRLSelector.match: no CRLNumber");
                }
            }
            BigInteger crlNum;
            try {
                DerInputStream in = new DerInputStream(crlNumExtVal);
                byte[] encoded = in.getOctetString();
                CRLNumberExtension crlNumExt =
                    new CRLNumberExtension(Boolean.FALSE, encoded);
                crlNum = crlNumExt.get(CRLNumberExtension.NUMBER);
            } catch (IOException ex) {
                if (debug != null) {
                    debug.println("X509CRLSelector.match: exception in "
                        + "decoding CRL number");
                }
                return false;
            }

            /* match on minCRLNumber */
            if (minCRL != null) {
                if (crlNum.compareTo(minCRL) < 0) {
                    if (debug != null) {
                        debug.println("X509CRLSelector.match: CRLNumber too small");
                    }
                    return false;
                }
            }

            /* match on maxCRLNumber */
            if (maxCRL != null) {
                if (crlNum.compareTo(maxCRL) > 0) {
                    if (debug != null) {
                        debug.println("X509CRLSelector.match: CRLNumber too large");
                    }
                    return false;
                }
            }
        }


        /* match on dateAndTime */
        if (dateAndTime != null) {
            Date crlThisUpdate = xcrl.getThisUpdate();
            Date nextUpdate = xcrl.getNextUpdate();
            if (nextUpdate == null) {
                if (debug != null) {
                    debug.println("X509CRLSelector.match: nextUpdate null");
                }
                return false;
            }
            Date nowPlusSkew = dateAndTime;
            Date nowMinusSkew = dateAndTime;
            if (skew > 0) {
                nowPlusSkew = new Date(dateAndTime.getTime() + skew);
                nowMinusSkew = new Date(dateAndTime.getTime() - skew);
            }
            if (nowMinusSkew.after(nextUpdate)
                || nowPlusSkew.before(crlThisUpdate)) {
                if (debug != null) {
                    debug.println("X509CRLSelector.match: update out of range");
                }
                return false;
            }
        }

        return true;
    }

    /**
     * Returns a copy of this object.
     *
     * <p>
     *  返回此对象的副本。
     * 
     * @return the copy
     */
    public Object clone() {
        try {
            X509CRLSelector copy = (X509CRLSelector)super.clone();
            if (issuerNames != null) {
                copy.issuerNames =
                        new HashSet<Object>(issuerNames);
                copy.issuerX500Principals =
                        new HashSet<X500Principal>(issuerX500Principals);
            }
            return copy;
        } catch (CloneNotSupportedException e) {
            /* Cannot happen */
            throw new InternalError(e.toString(), e);
        }
    }
}
