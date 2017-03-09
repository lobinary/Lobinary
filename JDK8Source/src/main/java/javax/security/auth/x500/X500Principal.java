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

package javax.security.auth.x500;

import java.io.*;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import sun.security.x509.X500Name;
import sun.security.util.*;

/**
 * <p> This class represents an X.500 {@code Principal}.
 * {@code X500Principal}s are represented by distinguished names such as
 * "CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US".
 *
 * <p> This class can be instantiated by using a string representation
 * of the distinguished name, or by using the ASN.1 DER encoded byte
 * representation of the distinguished name.  The current specification
 * for the string representation of a distinguished name is defined in
 * <a href="http://www.ietf.org/rfc/rfc2253.txt">RFC 2253: Lightweight
 * Directory Access Protocol (v3): UTF-8 String Representation of
 * Distinguished Names</a>. This class, however, accepts string formats from
 * both RFC 2253 and <a href="http://www.ietf.org/rfc/rfc1779.txt">RFC 1779:
 * A String Representation of Distinguished Names</a>, and also recognizes
 * attribute type keywords whose OIDs (Object Identifiers) are defined in
 * <a href="http://www.ietf.org/rfc/rfc3280.txt">RFC 3280: Internet X.509
 * Public Key Infrastructure Certificate and CRL Profile</a>.
 *
 * <p> The string representation for this {@code X500Principal}
 * can be obtained by calling the {@code getName} methods.
 *
 * <p> Note that the {@code getSubjectX500Principal} and
 * {@code getIssuerX500Principal} methods of
 * {@code X509Certificate} return X500Principals representing the
 * issuer and subject fields of the certificate.
 *
 * <p>
 *  <p>此类别代表X.500 {@code Principal}。
 *  {@code X500Principal}由可分辨名称表示,例如"CN = Duke,OU = JavaSoft,O = Sun Microsystems,C = US"。
 * 
 *  <p>此类可以通过使用可分辨名称的字符串表示形式或使用可分辨名称的ASN.1 DER编码字节表示形式来实例化。
 * 专有名称的字符串表示的当前规范在<a href="http://www.ietf.org/rfc/rfc2253.txt"> RFC 2253：轻量级目录访问协议(v3)中定义：UTF-8可分辨名称的字
 * 符串表示</a>。
 *  <p>此类可以通过使用可分辨名称的字符串表示形式或使用可分辨名称的ASN.1 DER编码字节表示形式来实例化。
 * 但是,此类接受来自RFC 2253和<a href="http://www.ietf.org/rfc/rfc1779.txt"> RFC 1779：可识别名称的字符串表示形式</a>的字符串格式,以及还
 * 可以识别<a href="http://www.ietf.org/rfc/rfc3280.txt"> RFC 3280：Internet X.509公钥基础结构证书和CRL配置文件中定义的OID(对象标
 * 识符)的属性类型关键字</a>。
 *  <p>此类可以通过使用可分辨名称的字符串表示形式或使用可分辨名称的ASN.1 DER编码字节表示形式来实例化。
 * 
 *  <p>此{@code X500Principal}的字符串表示形式可以通过调用{@code getName}方法获得。
 * 
 *  <p>请注意,{@code X509Certificate}的{@code getSubjectX500Principal}和{@code getIssuerX500Principal}方法返回代表证
 * 书的发行者和主题字段的X500Principals。
 * 
 * 
 * @see java.security.cert.X509Certificate
 * @since 1.4
 */
public final class X500Principal implements Principal, java.io.Serializable {

    private static final long serialVersionUID = -500463348111345721L;

    /**
     * RFC 1779 String format of Distinguished Names.
     * <p>
     * RFC 1779可分辨名称的字符串格式。
     * 
     */
    public static final String RFC1779 = "RFC1779";
    /**
     * RFC 2253 String format of Distinguished Names.
     * <p>
     *  RFC 2253可分辨名称的字符串格式。
     * 
     */
    public static final String RFC2253 = "RFC2253";
    /**
     * Canonical String format of Distinguished Names.
     * <p>
     *  可分辨名称的规范字符串格式。
     * 
     */
    public static final String CANONICAL = "CANONICAL";

    /**
     * The X500Name representing this principal.
     *
     * NOTE: this field is reflectively accessed from within X500Name.
     * <p>
     *  代表此主体的X500Name。
     * 
     *  注意：此字段从X500Name内反射访问。
     * 
     */
    private transient X500Name thisX500Name;

    /**
     * Creates an X500Principal by wrapping an X500Name.
     *
     * NOTE: The constructor is package private. It is intended to be accessed
     * using privileged reflection from classes in sun.security.*.
     * Currently referenced from sun.security.x509.X500Name.asX500Principal().
     * <p>
     *  通过包装X500Name创建X500Principal。
     * 
     *  注意：构造函数是包私有的。它旨在使用sun.security。*中类的特权反射访问。目前从sun.security.x509.X500Name.asX500Principal()引用。
     * 
     */
    X500Principal(X500Name x500Name) {
        thisX500Name = x500Name;
    }

    /**
     * Creates an {@code X500Principal} from a string representation of
     * an X.500 distinguished name (ex:
     * "CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US").
     * The distinguished name must be specified using the grammar defined in
     * RFC 1779 or RFC 2253 (either format is acceptable).
     *
     * <p>This constructor recognizes the attribute type keywords
     * defined in RFC 1779 and RFC 2253
     * (and listed in {@link #getName(String format) getName(String format)}),
     * as well as the T, DNQ or DNQUALIFIER, SURNAME, GIVENNAME, INITIALS,
     * GENERATION, EMAILADDRESS, and SERIALNUMBER keywords whose Object
     * Identifiers (OIDs) are defined in RFC 3280 and its successor.
     * Any other attribute type must be specified as an OID.
     *
     * <p>This implementation enforces a more restrictive OID syntax than
     * defined in RFC 1779 and 2253. It uses the more correct syntax defined in
     * <a href="http://www.ietf.org/rfc/rfc4512.txt">RFC 4512</a>, which
     * specifies that OIDs contain at least 2 digits:
     *
     * <p>{@code numericoid = number 1*( DOT number ) }
     *
     * <p>
     *  从X.500可分辨名称的字符串表示形式创建{@code X500Principal}(例如："CN = Duke,OU = JavaSoft,O = Sun Microsystems,C = US")
     * 。
     * 可分辨名称必须使用RFC 1779或RFC 2253中定义的语法指定(格式是可以接受的)。
     * 
     *  <p>此构造函数识别在RFC 1779和RFC 2253(并列在{@link #getName(String format)getName(String format)})中定义的属性类型关键字,以及
     * T,DNQ或DNQUALIFIER,SURNAME, GIVENNAME,INITIALS,GENERATION,EMAILADDRESS和SERIALNUMBER关键字,其对象标识符(OID)在RFC
     *  3280及其后续版本中定义。
     * 任何其他属性类型必须指定为OID。
     * 
     *  <p>此实施强制实施比RFC 1779和2253中定义的更严格的OID语法。
     * 它使用在<a href="http://www.ietf.org/rfc/rfc4512.txt"> RFC中定义的更正确的语法4512 </a>,它指定OID至少包含2个数字：。
     * 
     *  <p> {@ code numericoid = number 1 *(DOT number)}
     * 
     * 
     * @param name an X.500 distinguished name in RFC 1779 or RFC 2253 format
     * @exception NullPointerException if the {@code name}
     *                  is {@code null}
     * @exception IllegalArgumentException if the {@code name}
     *                  is improperly specified
     */
    public X500Principal(String name) {
        this(name, Collections.<String, String>emptyMap());
    }

    /**
     * Creates an {@code X500Principal} from a string representation of
     * an X.500 distinguished name (ex:
     * "CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US").
     * The distinguished name must be specified using the grammar defined in
     * RFC 1779 or RFC 2253 (either format is acceptable).
     *
     * <p> This constructor recognizes the attribute type keywords specified
     * in {@link #X500Principal(String)} and also recognizes additional
     * keywords that have entries in the {@code keywordMap} parameter.
     * Keyword entries in the keywordMap take precedence over the default
     * keywords recognized by {@code X500Principal(String)}. Keywords
     * MUST be specified in all upper-case, otherwise they will be ignored.
     * Improperly specified keywords are ignored; however if a keyword in the
     * name maps to an improperly specified Object Identifier (OID), an
     * {@code IllegalArgumentException} is thrown. It is permissible to
     * have 2 different keywords that map to the same OID.
     *
     * <p>This implementation enforces a more restrictive OID syntax than
     * defined in RFC 1779 and 2253. It uses the more correct syntax defined in
     * <a href="http://www.ietf.org/rfc/rfc4512.txt">RFC 4512</a>, which
     * specifies that OIDs contain at least 2 digits:
     *
     * <p>{@code numericoid = number 1*( DOT number ) }
     *
     * <p>
     * 从X.500可分辨名称的字符串表示形式创建{@code X500Principal}(例如："CN = Duke,OU = JavaSoft,O = Sun Microsystems,C = US")。
     * 可分辨名称必须使用RFC 1779或RFC 2253中定义的语法指定(格式是可以接受的)。
     * 
     *  <p>此构造函数识别{@link#X500Principal(String)}中指定的属性类型关键字,并识别在{@code keywordMap}参数中包含条目的其他关键字。
     *  keywordMap中的关键字条目优先于{@code X500Principal(String)}识别的默认关键字。关键字必须在所有大写字母中指定,否则它们将被忽略。
     * 错误指定的关键字将被忽略;但是如果名称中的关键字映射到不正确指定的对象标识符(OID),则会抛出{@code IllegalArgumentException}。
     * 允许有2个不同的关键字映射到同一OID。
     * 
     *  <p>此实施强制实施比RFC 1779和2253中定义的更严格的OID语法。
     * 它使用在<a href="http://www.ietf.org/rfc/rfc4512.txt"> RFC中定义的更正确的语法4512 </a>,它指定OID至少包含2个数字：。
     * 
     *  <p> {@ code numericoid = number 1 *(DOT number)}
     * 
     * 
     * @param name an X.500 distinguished name in RFC 1779 or RFC 2253 format
     * @param keywordMap an attribute type keyword map, where each key is a
     *   keyword String that maps to a corresponding object identifier in String
     *   form (a sequence of nonnegative integers separated by periods). The map
     *   may be empty but never {@code null}.
     * @exception NullPointerException if {@code name} or
     *   {@code keywordMap} is {@code null}
     * @exception IllegalArgumentException if the {@code name} is
     *   improperly specified or a keyword in the {@code name} maps to an
     *   OID that is not in the correct form
     * @since 1.6
     */
    public X500Principal(String name, Map<String, String> keywordMap) {
        if (name == null) {
            throw new NullPointerException
                (sun.security.util.ResourcesMgr.getString
                ("provided.null.name"));
        }
        if (keywordMap == null) {
            throw new NullPointerException
                (sun.security.util.ResourcesMgr.getString
                ("provided.null.keyword.map"));
        }

        try {
            thisX500Name = new X500Name(name, keywordMap);
        } catch (Exception e) {
            IllegalArgumentException iae = new IllegalArgumentException
                        ("improperly specified input name: " + name);
            iae.initCause(e);
            throw iae;
        }
    }

    /**
     * Creates an {@code X500Principal} from a distinguished name in
     * ASN.1 DER encoded form. The ASN.1 notation for this structure is as
     * follows.
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
     *
     * <p>
     *  以ASN.1 DER编码形式的专有名称创建{@code X500Principal}。该结构的ASN.1表示法如下。
     *  <pre> {@ code Name :: = CHOICE {RDNSequence}。
     * 
     *  RDNSequence :: = SEQUENCE OF RelativeDistinguishedName
     * 
     * RelativeDistinguishedName :: = SET SIZE(1.. MAX)of AttributeTypeAndValue
     * 
     *  AttributeTypeAndValue :: = SEQUENCE {type AttributeType,value AttributeValue}
     * 
     *  AttributeType :: = OBJECT IDENTIFIER
     * 
     *  AttributeValue :: = ANY DEFINED BY AttributeType .... DirectoryString :: = CHOICE {teletexString TeletexString(SIZE(1..MAX)),printableString PrintableString(SIZE(1..MAX)),universalString UniversalString(SIZE MAX)),utf8String UTF8String(SIZE(1..MAX)),bmpString BMPString(SIZE(1..MAX))}
     * } </pre>。
     * 
     * 
     * @param name a byte array containing the distinguished name in ASN.1
     * DER encoded form
     * @throws IllegalArgumentException if an encoding error occurs
     *          (incorrect form for DN)
     */
    public X500Principal(byte[] name) {
        try {
            thisX500Name = new X500Name(name);
        } catch (Exception e) {
            IllegalArgumentException iae = new IllegalArgumentException
                        ("improperly specified input name");
            iae.initCause(e);
            throw iae;
        }
    }

    /**
     * Creates an {@code X500Principal} from an {@code InputStream}
     * containing the distinguished name in ASN.1 DER encoded form.
     * The ASN.1 notation for this structure is supplied in the
     * documentation for
     * {@link #X500Principal(byte[] name) X500Principal(byte[] name)}.
     *
     * <p> The read position of the input stream is positioned
     * to the next available byte after the encoded distinguished name.
     *
     * <p>
     *  从包含ASN.1 DER编码形式的专有名称的{@code InputStream}中创建{@code X500Principal}。
     * 此结构的ASN.1符号在{@link#X500Principal(byte [] name)X500Principal(byte [] name}}的文档中提供。
     * 
     *  <p>输入流的读取位置位于编码的可分辨名称后的下一个可用字节。
     * 
     * 
     * @param is an {@code InputStream} containing the distinguished
     *          name in ASN.1 DER encoded form
     *
     * @exception NullPointerException if the {@code InputStream}
     *          is {@code null}
     * @exception IllegalArgumentException if an encoding error occurs
     *          (incorrect form for DN)
     */
    public X500Principal(InputStream is) {
        if (is == null) {
            throw new NullPointerException("provided null input stream");
        }

        try {
            if (is.markSupported())
                is.mark(is.available() + 1);
            DerValue der = new DerValue(is);
            thisX500Name = new X500Name(der.data);
        } catch (Exception e) {
            if (is.markSupported()) {
                try {
                    is.reset();
                } catch (IOException ioe) {
                    IllegalArgumentException iae = new IllegalArgumentException
                        ("improperly specified input stream " +
                        ("and unable to reset input stream"));
                    iae.initCause(e);
                    throw iae;
                }
            }
            IllegalArgumentException iae = new IllegalArgumentException
                        ("improperly specified input stream");
            iae.initCause(e);
            throw iae;
        }
    }

    /**
     * Returns a string representation of the X.500 distinguished name using
     * the format defined in RFC 2253.
     *
     * <p>This method is equivalent to calling
     * {@code getName(X500Principal.RFC2253)}.
     *
     * <p>
     *  使用RFC 2253中定义的格式返回X.500可分辨名称的字符串表示形式。
     * 
     *  <p>此方法相当于调用{@code getName(X500Principal.RFC2253)}。
     * 
     * 
     * @return the distinguished name of this {@code X500Principal}
     */
    public String getName() {
        return getName(X500Principal.RFC2253);
    }

    /**
     * Returns a string representation of the X.500 distinguished name
     * using the specified format. Valid values for the format are
     * "RFC1779", "RFC2253", and "CANONICAL" (case insensitive).
     *
     * <p> If "RFC1779" is specified as the format,
     * this method emits the attribute type keywords defined in
     * RFC 1779 (CN, L, ST, O, OU, C, STREET).
     * Any other attribute type is emitted as an OID.
     *
     * <p> If "RFC2253" is specified as the format,
     * this method emits the attribute type keywords defined in
     * RFC 2253 (CN, L, ST, O, OU, C, STREET, DC, UID).
     * Any other attribute type is emitted as an OID.
     * Under a strict reading, RFC 2253 only specifies a UTF-8 string
     * representation. The String returned by this method is the
     * Unicode string achieved by decoding this UTF-8 representation.
     *
     * <p> If "CANONICAL" is specified as the format,
     * this method returns an RFC 2253 conformant string representation
     * with the following additional canonicalizations:
     *
     * <ol>
     * <li> Leading zeros are removed from attribute types
     *          that are encoded as dotted decimal OIDs
     * <li> DirectoryString attribute values of type
     *          PrintableString and UTF8String are not
     *          output in hexadecimal format
     * <li> DirectoryString attribute values of types
     *          other than PrintableString and UTF8String
     *          are output in hexadecimal format
     * <li> Leading and trailing white space characters
     *          are removed from non-hexadecimal attribute values
     *          (unless the value consists entirely of white space characters)
     * <li> Internal substrings of one or more white space characters are
     *          converted to a single space in non-hexadecimal
     *          attribute values
     * <li> Relative Distinguished Names containing more than one
     *          Attribute Value Assertion (AVA) are output in the
     *          following order: an alphabetical ordering of AVAs
     *          containing standard keywords, followed by a numeric
     *          ordering of AVAs containing OID keywords.
     * <li> The only characters in attribute values that are escaped are
     *          those which section 2.4 of RFC 2253 states must be escaped
     *          (they are escaped using a preceding backslash character)
     * <li> The entire name is converted to upper case
     *          using {@code String.toUpperCase(Locale.US)}
     * <li> The entire name is converted to lower case
     *          using {@code String.toLowerCase(Locale.US)}
     * <li> The name is finally normalized using normalization form KD,
     *          as described in the Unicode Standard and UAX #15
     * </ol>
     *
     * <p> Additional standard formats may be introduced in the future.
     *
     * <p>
     *  返回使用指定格式的X.500可分辨名称的字符串表示形式。格式的有效值为"RFC1779","RFC2253"和"CANONICAL"(不区分大小写)。
     * 
     * <p>如果指定"RFC1779"作为格式,则此方法将发出在RFC 1779(CN,L,ST,O,OU,C,STREET)中定义的属性类型关键字。任何其他属性类型作为OID发出。
     * 
     *  <p>如果指定"RFC2253"作为格式,则此方法发出在RFC 2253(CN,L,ST,O,OU,C,STREET,DC,UID)中定义的属性类型关键字。任何其他属性类型作为OID发出。
     * 在严格的阅读下,RFC 2253只指定一个UTF-8字符串表示。此方法返回的字符串是通过解码此UTF-8表示形式实现的Unicode字符串。
     * 
     *  <p>如果指定了"CANONICAL"作为格式,此方法将返回具有以下附加规范化的RFC 2253一致性字符串表示形式：
     * 
     * <ol>
     * <li>前导零会从以点分十进制OID编码的属性类型中删除<li>类型为PrintableString和UTF8String的DirectoryString属性值不以十六进制格式输出<li> Direct
     * oryString输出除PrintableString和UTF8String之外的类型的属性值十六进制格式<li>从非十六进制属性值中除去前导和尾随空格字符(除非该值完全由空格字符组成)<li>一个或多
     * 个空格字符的内部子字符串转换为非空格字符的单个空格十六进制属性值<li>包含多个属性值断言(AVA)的相对区分名称按以下顺序输出：包含标准关键字的AVA的字母排序,后面是包含OID关键字的AVA的数字排
     * 序。
     *  <li>转义的属性值中的唯一字符是RFC 2253状态的第2.4节必须转义的字符(使用前面的反斜杠字符进行转义)<li>整个名称使用{@code String .toUpperCase(Locale.US)}
     *  <li>使用{@code String.toLowerCase(Locale.US)}将整个名称转换为小写。
     * <li>最后使用规范化形式KD对名称进行规范化,如Unicode中所述标准和UAX#15。
     * </ol>
     * 
     *  <p>将来可能会推出其他标准格式。
     * 
     * 
     * @param format the format to use
     *
     * @return a string representation of this {@code X500Principal}
     *          using the specified format
     * @throws IllegalArgumentException if the specified format is invalid
     *          or null
     */
    public String getName(String format) {
        if (format != null) {
            if (format.equalsIgnoreCase(RFC1779)) {
                return thisX500Name.getRFC1779Name();
            } else if (format.equalsIgnoreCase(RFC2253)) {
                return thisX500Name.getRFC2253Name();
            } else if (format.equalsIgnoreCase(CANONICAL)) {
                return thisX500Name.getRFC2253CanonicalName();
            }
        }
        throw new IllegalArgumentException("invalid format specified");
    }

    /**
     * Returns a string representation of the X.500 distinguished name
     * using the specified format. Valid values for the format are
     * "RFC1779" and "RFC2253" (case insensitive). "CANONICAL" is not
     * permitted and an {@code IllegalArgumentException} will be thrown.
     *
     * <p>This method returns Strings in the format as specified in
     * {@link #getName(String)} and also emits additional attribute type
     * keywords for OIDs that have entries in the {@code oidMap}
     * parameter. OID entries in the oidMap take precedence over the default
     * OIDs recognized by {@code getName(String)}.
     * Improperly specified OIDs are ignored; however if an OID
     * in the name maps to an improperly specified keyword, an
     * {@code IllegalArgumentException} is thrown.
     *
     * <p> Additional standard formats may be introduced in the future.
     *
     * <p> Warning: additional attribute type keywords may not be recognized
     * by other implementations; therefore do not use this method if
     * you are unsure if these keywords will be recognized by other
     * implementations.
     *
     * <p>
     * 返回使用指定格式的X.500可分辨名称的字符串表示形式。格式的有效值为"RFC1779"和"RFC2253"(不区分大小写)。
     * 不允许使用"CANONICAL",并且将抛出{@code IllegalArgumentException}。
     * 
     *  <p>此方法以{@link #getName(String)}中指定的格式返回字符串,并为在{@code oidMap}参数中具有条目的OID发出其他属性类型关键字。
     *  oidMap中的OID条目优先于{@code getName(String)}识别的默认OID。
     * 忽略不正确指定的OID;但是如果名称中的OID映射到不正确指定的关键字,则会抛出{@code IllegalArgumentException}。
     * 
     *  <p>将来可能会推出其他标准格式。
     * 
     *  <p>警告：其他实现可能无法识别其他属性类型关键字;因此如果您不确定这些关键字是否会被其他实现识别,请不要使用此方法。
     * 
     * 
     * @param format the format to use
     * @param oidMap an OID map, where each key is an object identifier in
     *  String form (a sequence of nonnegative integers separated by periods)
     *  that maps to a corresponding attribute type keyword String.
     *  The map may be empty but never {@code null}.
     * @return a string representation of this {@code X500Principal}
     *          using the specified format
     * @throws IllegalArgumentException if the specified format is invalid,
     *  null, or an OID in the name maps to an improperly specified keyword
     * @throws NullPointerException if {@code oidMap} is {@code null}
     * @since 1.6
     */
    public String getName(String format, Map<String, String> oidMap) {
        if (oidMap == null) {
            throw new NullPointerException
                (sun.security.util.ResourcesMgr.getString
                ("provided.null.OID.map"));
        }
        if (format != null) {
            if (format.equalsIgnoreCase(RFC1779)) {
                return thisX500Name.getRFC1779Name(oidMap);
            } else if (format.equalsIgnoreCase(RFC2253)) {
                return thisX500Name.getRFC2253Name(oidMap);
            }
        }
        throw new IllegalArgumentException("invalid format specified");
    }

    /**
     * Returns the distinguished name in ASN.1 DER encoded form. The ASN.1
     * notation for this structure is supplied in the documentation for
     * {@link #X500Principal(byte[] name) X500Principal(byte[] name)}.
     *
     * <p>Note that the byte array returned is cloned to protect against
     * subsequent modifications.
     *
     * <p>
     *  以ASN.1 DER编码形式返回可分辨名称。
     * 此结构的ASN.1符号在{@link#X500Principal(byte [] name)X500Principal(byte [] name}}的文档中提供。
     * 
     *  <p>请注意,将返回的字节数组进行克隆以防止后续修改。
     * 
     * 
     * @return a byte array containing the distinguished name in ASN.1 DER
     * encoded form
     */
    public byte[] getEncoded() {
        try {
            return thisX500Name.getEncoded();
        } catch (IOException e) {
            throw new RuntimeException("unable to get encoding", e);
        }
    }

    /**
     * Return a user-friendly string representation of this
     * {@code X500Principal}.
     *
     * <p>
     *  返回此{@code X500Principal}的用户友好的字符串表示形式。
     * 
     * 
     * @return a string representation of this {@code X500Principal}
     */
    public String toString() {
        return thisX500Name.toString();
    }

    /**
     * Compares the specified {@code Object} with this
     * {@code X500Principal} for equality.
     *
     * <p> Specifically, this method returns {@code true} if
     * the {@code Object} <i>o</i> is an {@code X500Principal}
     * and if the respective canonical string representations
     * (obtained via the {@code getName(X500Principal.CANONICAL)} method)
     * of this object and <i>o</i> are equal.
     *
     * <p> This implementation is compliant with the requirements of RFC 3280.
     *
     * <p>
     *  比较指定的{@code Object}与此{@code X500Principal}的相等性。
     * 
     * <p>具体来说,如果{@code Object} <i> o </i>是{@code X500Principal},并且如果相应的规范字符串表示(通过{@code getName (X500Principal.CANONICAL)}
     * 方法)和<i> o </i>是相等的。
     * 
     *  <p>此实施符合RFC 3280的要求。
     * 
     * 
     * @param o Object to be compared for equality with this
     *          {@code X500Principal}
     *
     * @return {@code true} if the specified {@code Object} is equal
     *          to this {@code X500Principal}, {@code false} otherwise
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof X500Principal == false) {
            return false;
        }
        X500Principal other = (X500Principal)o;
        return this.thisX500Name.equals(other.thisX500Name);
    }

    /**
     * Return a hash code for this {@code X500Principal}.
     *
     * <p> The hash code is calculated via:
     * {@code getName(X500Principal.CANONICAL).hashCode()}
     *
     * <p>
     *  返回此{@code X500Principal}的哈希码。
     * 
     *  <p>哈希码通过以下方式计算：{@code getName(X500Principal.CANONICAL).hashCode()}
     * 
     * 
     * @return a hash code for this {@code X500Principal}
     */
    public int hashCode() {
        return thisX500Name.hashCode();
    }

    /**
     * Save the X500Principal object to a stream.
     *
     * <p>
     *  将X500Principal对象保存到流。
     * 
     * 
     * @serialData this {@code X500Principal} is serialized
     *          by writing out its DER-encoded form
     *          (the value of {@code getEncoded} is serialized).
     */
    private void writeObject(java.io.ObjectOutputStream s)
        throws IOException {
        s.writeObject(thisX500Name.getEncodedInternal());
    }

    /**
     * Reads this object from a stream (i.e., deserializes it).
     * <p>
     *  从流中读取此对象(即,对其进行反序列化)。
     */
    private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException,
               java.io.NotActiveException,
               ClassNotFoundException {

        // re-create thisX500Name
        thisX500Name = new X500Name((byte[])s.readObject());
    }
}
