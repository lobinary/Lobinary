/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2006, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.namespace;

import java.io.Serializable;
import java.security.AccessController;
import java.security.PrivilegedAction;

import javax.xml.XMLConstants;

/**
 * <p><code>QName</code> represents a <strong>qualified name</strong>
 * as defined in the XML specifications: <a
 * href="http://www.w3.org/TR/xmlschema-2/#QName">XML Schema Part2:
 * Datatypes specification</a>, <a
 * href="http://www.w3.org/TR/REC-xml-names/#ns-qualnames">Namespaces
 * in XML</a>, <a
 * href="http://www.w3.org/XML/xml-names-19990114-errata">Namespaces
 * in XML Errata</a>.</p>
 *
 * <p>The value of a <code>QName</code> contains a <strong>Namespace
 * URI</strong>, <strong>local part</strong> and
 * <strong>prefix</strong>.</p>
 *
 * <p>The prefix is included in <code>QName</code> to retain lexical
 * information <strong><em>when present</em></strong> in an {@link
 * javax.xml.transform.Source XML input source}. The prefix is
 * <strong><em>NOT</em></strong> used in {@link #equals(Object)
 * QName.equals(Object)} or to compute the {@link #hashCode()
 * QName.hashCode()}.  Equality and the hash code are defined using
 * <strong><em>only</em></strong> the Namespace URI and local part.</p>
 *
 * <p>If not specified, the Namespace URI is set to {@link
 * javax.xml.XMLConstants#NULL_NS_URI XMLConstants.NULL_NS_URI}.
 * If not specified, the prefix is set to {@link
 * javax.xml.XMLConstants#DEFAULT_NS_PREFIX
 * XMLConstants.DEFAULT_NS_PREFIX}.</p>
 *
 * <p><code>QName</code> is immutable.</p>
 *
 * <p>
 *  <p> <code> QName </code>代表XML规范中定义的<strong>限定名称</strong>：<a href ="http://www.w3.org/TR/xmlschema-2/ #QName">
 *  XML模式Part2：数据类型规范</a>,<a href="http://www.w3.org/TR/REC-xml-names/#ns-qualnames"> XML中的命名空间</a >,<a href="http://www.w3.org/XML/xml-names-19990114-errata">
 *  XML勘误中的命名空间</a>。
 * </p>。
 * 
 *  <p> <code> QName </code>的值包含<strong>命名空间URI </strong>,<strong>本地部分</strong>和<strong>前缀</strong>。
 * </p>。
 * 
 *  <p>前缀包含在<code> QName </code>中,用于在{@link javax.xml.transform.Source XML输入中保留词汇信息<strong> <em>存在时</strong> </资源}。
 * 前缀是在{@link #equals(Object)QName.equals(Object)}中使用的<strong> <em> NOT </em> </strong>或计算{@link #hashCode()QName.hashCode )}
 * 。
 * 平等和散列码是使用<strong> </em> </strong>命名空间URI和本地部分定义的。</p>。
 * 
 *  <p>如果未指定,则命名空间URI将设置为{@link javax.xml.XMLConstants#NULL_NS_URI XMLConstants.NULL_NS_URI}。
 * 如果未指定,则前缀设置为{@link javax.xml.XMLConstants#DEFAULT_NS_PREFIX XMLConstants.DEFAULT_NS_PREFIX}。</p>。
 * 
 *  <p> <code> QName </code>是不可变的。</p>
 * 
 * 
 * @author <a href="mailto:Jeff.Suttor@Sun.com">Jeff Suttor</a>
 * @version $Revision: 1.8 $, $Date: 2010/03/18 03:06:17 $
 * @see <a href="http://www.w3.org/TR/xmlschema-2/#QName">
 *   XML Schema Part2: Datatypes specification</a>
 * @see <a href="http://www.w3.org/TR/REC-xml-names/#ns-qualnames">
 *   Namespaces in XML</a>
 * @see <a href="http://www.w3.org/XML/xml-names-19990114-errata">
 *   Namespaces in XML Errata</a>
 * @since 1.5
 */

public class QName implements Serializable {

    /**
     * <p>Stream Unique Identifier.</p>
     *
     * <p>Due to a historical defect, QName was released with multiple
     * serialVersionUID values even though its serialization was the
     * same.</p>
     *
     * <p>To workaround this issue, serialVersionUID is set with either
     * a default value or a compatibility value.  To use the
     * compatiblity value, set the system property:</p>
     *
     * <code>com.sun.xml.namespace.QName.useCompatibleSerialVersionUID=1.0</code>
     *
     * <p>This workaround was inspired by classes in the javax.management
     * package, e.g. ObjectName, etc.
     * See CR6267224 for original defect report.</p>
     * <p>
     *  <p>流唯一标识符</p>
     * 
     * <p>由于历史缺陷,QName被释放时带有多个serialVersionUID值,即使它的序列化是相同的。</p>
     * 
     *  <p>要解决此问题,serialVersionUID设置为默认值或兼容性值。要使用兼容性值,请设置系统属性：</p>
     * 
     *  <code> com.sun.xml.namespace.QName.useCompatibleSerialVersionUID = 1.0 </code>
     * 
     *  <p>此解决方法的灵感来自javax.management软件包中的类,例如ObjectName等,参见CR6267224的原始缺陷报告。</p>
     * 
     */
    private static final long serialVersionUID;
    /**
     * <p>Default <code>serialVersionUID</code> value.</p>
     * <p>
     *  <p>默认<code> serialVersionUID </code>值。</p>
     * 
     */
    private static final long defaultSerialVersionUID = -9120448754896609940L;
    /**
     * <p>Compatibility <code>serialVersionUID</code> value.</p>
     * <p>
     *  <p>兼容性<code> serialVersionUID </code>值。</p>
     * 
     */
    private static final long compatibleSerialVersionUID = 4418622981026545151L;
    /**
     * <p>Flag to use default or campatible serialVersionUID.</p>
     * <p>
     *  <p>标记为使用默认或可持续的serialVersionUID。</p>
     * 
     */
    private static boolean useDefaultSerialVersionUID = true;
    static {
        try {
            // use a privileged block as reading a system property
            String valueUseCompatibleSerialVersionUID = (String) AccessController.doPrivileged(
                    new PrivilegedAction() {
                        public Object run() {
                            return System.getProperty("com.sun.xml.namespace.QName.useCompatibleSerialVersionUID");
                        }
                    }
            );
            useDefaultSerialVersionUID = (valueUseCompatibleSerialVersionUID != null && valueUseCompatibleSerialVersionUID.equals("1.0")) ? false : true;
        } catch (Exception exception) {
            // use default if any Exceptions
            useDefaultSerialVersionUID = true;
        }

        // set serialVersionUID to desired value
        if (useDefaultSerialVersionUID) {
            serialVersionUID = defaultSerialVersionUID;
        } else {
            serialVersionUID = compatibleSerialVersionUID;
        }
    }

    /**
     * <p>Namespace URI of this <code>QName</code>.</p>
     * <p>
     *  <p>此<code> QName </code>的命名空间URI。</p>
     * 
     */
    private final String namespaceURI;

    /**
     * <p>local part of this <code>QName</code>.</p>
     * <p>
     *  <p>此<code> QName </code>的本地部分。</p>
     * 
     */
    private final String localPart;

    /**
     * <p>prefix of this <code>QName</code>.</p>
     * <p>
     *  <p>此<code> QName </code>的前缀。</p>
     * 
     */
    private final String prefix;

    /**
     * <p><code>QName</code> constructor specifying the Namespace URI
     * and local part.</p>
     *
     * <p>If the Namespace URI is <code>null</code>, it is set to
     * {@link javax.xml.XMLConstants#NULL_NS_URI
     * XMLConstants.NULL_NS_URI}.  This value represents no
     * explicitly defined Namespace as defined by the <a
     * href="http://www.w3.org/TR/REC-xml-names/#ns-qualnames">Namespaces
     * in XML</a> specification.  This action preserves compatible
     * behavior with QName 1.0.  Explicitly providing the {@link
     * javax.xml.XMLConstants#NULL_NS_URI
     * XMLConstants.NULL_NS_URI} value is the preferred coding
     * style.</p>
     *
     * <p>If the local part is <code>null</code> an
     * <code>IllegalArgumentException</code> is thrown.
     * A local part of "" is allowed to preserve
     * compatible behavior with QName 1.0. </p>
     *
     * <p>When using this constructor, the prefix is set to {@link
     * javax.xml.XMLConstants#DEFAULT_NS_PREFIX
     * XMLConstants.DEFAULT_NS_PREFIX}.</p>
     *
     * <p>The Namespace URI is not validated as a
     * <a href="http://www.ietf.org/rfc/rfc2396.txt">URI reference</a>.
     * The local part is not validated as a
     * <a href="http://www.w3.org/TR/REC-xml-names/#NT-NCName">NCName</a>
     * as specified in <a href="http://www.w3.org/TR/REC-xml-names/">Namespaces
     * in XML</a>.</p>
     *
     * <p>
     *  <p> <code> QName </code>构造函数,用于指定命名空间URI和本地部分。</p>
     * 
     *  <p>如果命名空间URI为<code> null </code>,则设置为{@link javax.xml.XMLConstants#NULL_NS_URI XMLConstants.NULL_NS_URI}
     * 。
     * 此值不表示<a href="http://www.w3.org/TR/REC-xml-names/#ns-qualnames"> XML中的命名空间</a>规范中定义的明确定义的命名空间。
     * 此操作保留与QName 1.0的兼容行为。
     * 明确提供{@link javax.xml.XMLConstants#NULL_NS_URI XMLConstants.NULL_NS_URI}值是首选的编码样式。</p>。
     * 
     * <p>如果本地部分是<code> null </code>,则会抛出<code> IllegalArgumentException </code>。
     *  ""的本地部分允许保留与QName 1.0的兼容行为。 </p>。
     * 
     *  <p>使用此构造函数时,前缀设置为{@link javax.xml.XMLConstants#DEFAULT_NS_PREFIX XMLConstants.DEFAULT_NS_PREFIX}。
     * </p>。
     * 
     *  <p>名称空间URI未经验证为<a href="http://www.ietf.org/rfc/rfc2396.txt"> URI参考</a>。
     * 本地部分未验证为<a href ="中指定的<a href="http://www.w3.org/TR/REC-xml-names/#NT-NCName"> NCName </a> http://www
     * .w3.org/TR/REC-xml-names/"> XML中的命名空间</a>。
     *  <p>名称空间URI未经验证为<a href="http://www.ietf.org/rfc/rfc2396.txt"> URI参考</a>。</p>。
     * 
     * 
     * @param namespaceURI Namespace URI of the <code>QName</code>
     * @param localPart    local part of the <code>QName</code>
     *
     * @throws IllegalArgumentException When <code>localPart</code> is
     *   <code>null</code>
     *
     * @see #QName(String namespaceURI, String localPart, String
     * prefix) QName(String namespaceURI, String localPart, String
     * prefix)
     */
    public QName(final String namespaceURI, final String localPart) {
        this(namespaceURI, localPart, XMLConstants.DEFAULT_NS_PREFIX);
    }

    /**
     * <p><code>QName</code> constructor specifying the Namespace URI,
     * local part and prefix.</p>
     *
     * <p>If the Namespace URI is <code>null</code>, it is set to
     * {@link javax.xml.XMLConstants#NULL_NS_URI
     * XMLConstants.NULL_NS_URI}.  This value represents no
     * explicitly defined Namespace as defined by the <a
     * href="http://www.w3.org/TR/REC-xml-names/#ns-qualnames">Namespaces
     * in XML</a> specification.  This action preserves compatible
     * behavior with QName 1.0.  Explicitly providing the {@link
     * javax.xml.XMLConstants#NULL_NS_URI
     * XMLConstants.NULL_NS_URI} value is the preferred coding
     * style.</p>
     *
     * <p>If the local part is <code>null</code> an
     * <code>IllegalArgumentException</code> is thrown.
     * A local part of "" is allowed to preserve
     * compatible behavior with QName 1.0. </p>
     *
     * <p>If the prefix is <code>null</code>, an
     * <code>IllegalArgumentException</code> is thrown.  Use {@link
     * javax.xml.XMLConstants#DEFAULT_NS_PREFIX
     * XMLConstants.DEFAULT_NS_PREFIX} to explicitly indicate that no
     * prefix is present or the prefix is not relevant.</p>
     *
     * <p>The Namespace URI is not validated as a
     * <a href="http://www.ietf.org/rfc/rfc2396.txt">URI reference</a>.
     * The local part and prefix are not validated as a
     * <a href="http://www.w3.org/TR/REC-xml-names/#NT-NCName">NCName</a>
     * as specified in <a href="http://www.w3.org/TR/REC-xml-names/">Namespaces
     * in XML</a>.</p>
     *
     * <p>
     *  <p> <code> QName </code>构造函数,用于指定命名空间URI,本地部分和前缀。</p>
     * 
     *  <p>如果命名空间URI为<code> null </code>,则设置为{@link javax.xml.XMLConstants#NULL_NS_URI XMLConstants.NULL_NS_URI}
     * 。
     * 此值不表示<a href="http://www.w3.org/TR/REC-xml-names/#ns-qualnames"> XML中的命名空间</a>规范中定义的明确定义的命名空间。
     * 此操作保留与QName 1.0的兼容行为。
     * 明确提供{@link javax.xml.XMLConstants#NULL_NS_URI XMLConstants.NULL_NS_URI}值是首选的编码样式。</p>。
     * 
     *  <p>如果本地部分是<code> null </code>,则会抛出<code> IllegalArgumentException </code>。
     *  ""的本地部分允许保留与QName 1.0的兼容行为。 </p>。
     * 
     * <p>如果前缀是<code> null </code>,则会抛出<code> IllegalArgumentException </code>。
     * 使用{@link javax.xml.XMLConstants#DEFAULT_NS_PREFIX XMLConstants.DEFAULT_NS_PREFIX}显式指示不存在前缀或前缀不相关。
     * </p>。
     * 
     *  <p>名称空间URI未经验证为<a href="http://www.ietf.org/rfc/rfc2396.txt"> URI参考</a>。
     * 本地部分和前缀不会验证为<a href中指定的<a href="http://www.w3.org/TR/REC-xml-names/#NT-NCName"> NCName </a> ="http://
     * www.w3.org/TR/REC-xml-names/"> XML中的命名空间</a>。
     *  <p>名称空间URI未经验证为<a href="http://www.ietf.org/rfc/rfc2396.txt"> URI参考</a>。</p>。
     * 
     * 
     * @param namespaceURI Namespace URI of the <code>QName</code>
     * @param localPart    local part of the <code>QName</code>
     * @param prefix       prefix of the <code>QName</code>
     *
     * @throws IllegalArgumentException When <code>localPart</code>
     *   or <code>prefix</code> is <code>null</code>
     */
    public QName(String namespaceURI, String localPart, String prefix) {

        // map null Namespace URI to default
        // to preserve compatibility with QName 1.0
        if (namespaceURI == null) {
            this.namespaceURI = XMLConstants.NULL_NS_URI;
        } else {
            this.namespaceURI = namespaceURI;
        }

        // local part is required.
        // "" is allowed to preserve compatibility with QName 1.0
        if (localPart == null) {
            throw new IllegalArgumentException(
                    "local part cannot be \"null\" when creating a QName");
        }
        this.localPart = localPart;

        // prefix is required
        if (prefix == null) {
            throw new IllegalArgumentException(
                    "prefix cannot be \"null\" when creating a QName");
        }
        this.prefix = prefix;
    }

    /**
     * <p><code>QName</code> constructor specifying the local part.</p>
     *
     * <p>If the local part is <code>null</code> an
     * <code>IllegalArgumentException</code> is thrown.
     * A local part of "" is allowed to preserve
     * compatible behavior with QName 1.0. </p>
     *
     * <p>When using this constructor, the Namespace URI is set to
     * {@link javax.xml.XMLConstants#NULL_NS_URI
     * XMLConstants.NULL_NS_URI} and the prefix is set to {@link
     * javax.xml.XMLConstants#DEFAULT_NS_PREFIX
     * XMLConstants.DEFAULT_NS_PREFIX}.</p>
     *
     * <p><em>In an XML context, all Element and Attribute names exist
     * in the context of a Namespace.  Making this explicit during the
     * construction of a <code>QName</code> helps prevent hard to
     * diagnosis XML validity errors.  The constructors {@link
     * #QName(String namespaceURI, String localPart) QName(String
     * namespaceURI, String localPart)} and
     * {@link #QName(String namespaceURI, String localPart, String prefix)}
     * are preferred.</em></p>
     *
     * <p>The local part is not validated as a
     * <a href="http://www.w3.org/TR/REC-xml-names/#NT-NCName">NCName</a>
     * as specified in <a href="http://www.w3.org/TR/REC-xml-names/">Namespaces
     * in XML</a>.</p>
     *
     * <p>
     *  <p> <code> QName </code>构造函数指定本地部分。</p>
     * 
     *  <p>如果本地部分是<code> null </code>,则会抛出<code> IllegalArgumentException </code>。
     *  ""的本地部分允许保留与QName 1.0的兼容行为。 </p>。
     * 
     *  <p>使用此构造函数时,命名空间URI设置为{@link javax.xml.XMLConstants#NULL_NS_URI XMLConstants.NULL_NS_URI},前缀设置为{@link javax.xml.XMLConstants#DEFAULT_NS_PREFIX XMLConstants.DEFAULT_NS_PREFIX}
     * 。
     *  / p>。
     * 
     *  <p> <em>在XML上下文中,所有元素和属性名称都存在于命名空间的上下文中。在构建<code> QName </code>期间明确这一点有助于防止难以诊断XML有效性错误。
     * 首选构造函数{@link #QName(String namespaceURI,String localPart)QName(String namespaceURI,String localPart)}
     * 和{@link #QName(String namespaceURI,String localPart,String prefix)}。
     *  <p> <em>在XML上下文中,所有元素和属性名称都存在于命名空间的上下文中。在构建<code> QName </code>期间明确这一点有助于防止难以诊断XML有效性错误。</p> >。
     * 
     * <p>本地部分不会验证为<a中指定的<a href="http://www.w3.org/TR/REC-xml-names/#NT-NCName"> NCName </a> href ="http://
     * www.w3.org/TR/REC-xml-names/"> XML中的命名空间</a>。
     * </p>。
     * 
     * 
     * @param localPart local part of the <code>QName</code>
     *
     * @throws IllegalArgumentException When <code>localPart</code> is
     *   <code>null</code>
     *
     * @see #QName(String namespaceURI, String localPart) QName(String
     * namespaceURI, String localPart)
     * @see #QName(String namespaceURI, String localPart, String
     * prefix) QName(String namespaceURI, String localPart, String
     * prefix)
     */
    public QName(String localPart) {
        this(
            XMLConstants.NULL_NS_URI,
            localPart,
            XMLConstants.DEFAULT_NS_PREFIX);
    }

    /**
     * <p>Get the Namespace URI of this <code>QName</code>.</p>
     *
     * <p>
     *  <p>获取此<code> QName </code>的命名空间URI。</p>
     * 
     * 
     * @return Namespace URI of this <code>QName</code>
     */
    public String getNamespaceURI() {
        return namespaceURI;
    }

    /**
     * <p>Get the local part of this <code>QName</code>.</p>
     *
     * <p>
     *  <p>获取此<code> QName </code>的本地部分。</p>
     * 
     * 
     *  @return local part of this <code>QName</code>
     */
    public String getLocalPart() {
        return localPart;
    }

    /**
     * <p>Get the prefix of this <code>QName</code>.</p>
     *
     * <p>The prefix assigned to a <code>QName</code> might
     * <strong><em>NOT</em></strong> be valid in a different
     * context. For example, a <code>QName</code> may be assigned a
     * prefix in the context of parsing a document but that prefix may
     * be invalid in the context of a different document.</p>
     *
     * <p>
     *  <p>获取此<code> QName </code>的前缀。</p>
     * 
     *  <p>分配给<code> QName </code>的前缀可能<strong> <em> </em> </strong>在不同的上下文中有效。
     * 例如,可以在解析文档的上下文中为<code> QName </code>分配前缀,但该前缀在不同文档的上下文中可能是无效的。</p>。
     * 
     * 
     *  @return prefix of this <code>QName</code>
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * <p>Test this <code>QName</code> for equality with another
     * <code>Object</code>.</p>
     *
     * <p>If the <code>Object</code> to be tested is not a
     * <code>QName</code> or is <code>null</code>, then this method
     * returns <code>false</code>.</p>
     *
     * <p>Two <code>QName</code>s are considered equal if and only if
     * both the Namespace URI and local part are equal. This method
     * uses <code>String.equals()</code> to check equality of the
     * Namespace URI and local part. The prefix is
     * <strong><em>NOT</em></strong> used to determine equality.</p>
     *
     * <p>This method satisfies the general contract of {@link
     * java.lang.Object#equals(Object) Object.equals(Object)}</p>
     *
     * <p>
     *  <p>测试此<code> QName </code>是否与另一个<code> Object </code>相同。</p>
     * 
     *  <p>如果要测试的<code> Object </code>不是<code> QName </code>或<code> null </code>,则此方法返回<code> false </code> 
     * 。
     * </p>。
     * 
     *  <p>如果且仅当命名空间URI和本地部分相等时,两个<code> QName </code>被视为相等。
     * 此方法使用<code> String.equals()</code>来检查命名空间URI和本地部分的相等性。前缀是<strong> </em> </strong>用于确定平等。</p>。
     * 
     *  <p>此方法满足{@link java.lang.Object#equals(Object)Object.equals(Object)}的一般合同} </p>
     * 
     * 
     * @param objectToTest the <code>Object</code> to test for
     * equality with this <code>QName</code>
     * @return <code>true</code> if the given <code>Object</code> is
     * equal to this <code>QName</code> else <code>false</code>
     */
    public final boolean equals(Object objectToTest) {
        if (objectToTest == this) {
            return true;
        }

        if (objectToTest == null || !(objectToTest instanceof QName)) {
            return false;
        }

        QName qName = (QName) objectToTest;

        return localPart.equals(qName.localPart)
            && namespaceURI.equals(qName.namespaceURI);
    }

    /**
     * <p>Generate the hash code for this <code>QName</code>.</p>
     *
     * <p>The hash code is calculated using both the Namespace URI and
     * the local part of the <code>QName</code>.  The prefix is
     * <strong><em>NOT</em></strong> used to calculate the hash
     * code.</p>
     *
     * <p>This method satisfies the general contract of {@link
     * java.lang.Object#hashCode() Object.hashCode()}.</p>
     *
     * <p>
     *  <p>为此<code> QName </code>生成哈希码。</p>
     * 
     * <p>使用命名空间URI和<code> QName </code>的本地部分计算哈希码。前缀是<strong> </em> </strong>用于计算哈希码。</p>
     * 
     *  <p>此方法满足{@link java.lang.Object#hashCode()Object.hashCode()}的一般合同。</p>
     * 
     * 
     * @return hash code for this <code>QName</code> <code>Object</code>
     */
    public final int hashCode() {
        return namespaceURI.hashCode() ^ localPart.hashCode();
    }

    /**
     * <p><code>String</code> representation of this
     * <code>QName</code>.</p>
     *
     * <p>The commonly accepted way of representing a <code>QName</code>
     * as a <code>String</code> was
     * <a href="http://jclark.com/xml/xmlns.htm">defined</a>
     * by James Clark.  Although this is not a <em>standard</em>
     * specification, it is in common use, e.g. {@link
     * javax.xml.transform.Transformer#setParameter(String name, Object value)}.
     * This implementation represents a <code>QName</code> as:
     * "{" + Namespace URI + "}" + local part.  If the Namespace URI
     * <code>.equals(XMLConstants.NULL_NS_URI)</code>, only the
     * local part is returned.  An appropriate use of this method is
     * for debugging or logging for human consumption.</p>
     *
     * <p>Note the prefix value is <strong><em>NOT</em></strong>
     * returned as part of the <code>String</code> representation.</p>
     *
     * <p>This method satisfies the general contract of {@link
     * java.lang.Object#toString() Object.toString()}.</p>
     *
     * <p>
     *  此<code> QName </code>的<p> <code> String </code>表示。</p>
     * 
     *  <p>将<code> QName </code>表示为<code> String </code>的常见方法是<a href="http://jclark.com/xml/xmlns.htm">定义</a>
     *  by James Clark。
     * 虽然这不是<em>标准</em>规范,但它是常用的,例如。
     *  {@link javax.xml.transform.Transformer#setParameter(String name,Object value)}。
     * 此实现将<code> QName </code>表示为："{"+ Namespace URI +"}"+本地部分。
     * 如果命名空间URI <code> .equals(XMLConstants.NULL_NS_URI)</code>,则只返回本地部分。此方法的适当使用是用于调试或记录人类消费。</p>。
     * 
     *  <p>请注意,前缀值为<strong> </em> </strong>作为<code> String </code>表示形式的一部分返回。</p>
     * 
     *  <p>此方法满足{@link java.lang.Object#toString()Object.toString()}的一般合同。</p>
     * 
     * 
     *  @return <code>String</code> representation of this <code>QName</code>
     */
    public String toString() {
        if (namespaceURI.equals(XMLConstants.NULL_NS_URI)) {
            return localPart;
        } else {
            return "{" + namespaceURI + "}" + localPart;
        }
    }

    /**
     * <p><code>QName</code> derived from parsing the formatted
     * <code>String</code>.</p>
     *
     * <p>If the <code>String</code> is <code>null</code> or does not conform to
     * {@link #toString() QName.toString()} formatting, an
     * <code>IllegalArgumentException</code> is thrown.</p>
     *
     * <p><em>The <code>String</code> <strong>MUST</strong> be in the
     * form returned by {@link #toString() QName.toString()}.</em></p>
     *
     * <p>The commonly accepted way of representing a <code>QName</code>
     * as a <code>String</code> was
     * <a href="http://jclark.com/xml/xmlns.htm">defined</a>
     * by James Clark.  Although this is not a <em>standard</em>
     * specification, it is in common use, e.g. {@link
     * javax.xml.transform.Transformer#setParameter(String name, Object value)}.
     * This implementation parses a <code>String</code> formatted
     * as: "{" + Namespace URI + "}" + local part.  If the Namespace
     * URI <code>.equals(XMLConstants.NULL_NS_URI)</code>, only the
     * local part should be provided.</p>
     *
     * <p>The prefix value <strong><em>CANNOT</em></strong> be
     * represented in the <code>String</code> and will be set to
     * {@link javax.xml.XMLConstants#DEFAULT_NS_PREFIX
     * XMLConstants.DEFAULT_NS_PREFIX}.</p>
     *
     * <p>This method does not do full validation of the resulting
     * <code>QName</code>.
     * <p>The Namespace URI is not validated as a
     * <a href="http://www.ietf.org/rfc/rfc2396.txt">URI reference</a>.
     * The local part is not validated as a
     * <a href="http://www.w3.org/TR/REC-xml-names/#NT-NCName">NCName</a>
     * as specified in
     * <a href="http://www.w3.org/TR/REC-xml-names/">Namespaces in XML</a>.</p>
     *
     * <p>
     *  <p> <code> QName </code>源自解析格式化的<code> String </code>。</p>
     * 
     *  <p>如果<code> String </code>是<code> null </code>或不符合{@link #toString()QName.toString()}格式,<code> Illeg
     * alArgumentException </code >被抛出。
     * </p>。
     * 
     * <p> <em> <code> String </code> <strong>必须</strong>使用{@link #toString()QName.toString()}返回的表单。
     * </em> </p >。
     * 
     *  <p>将<code> QName </code>表示为<code> String </code>的常见方法是<a href="http://jclark.com/xml/xmlns.htm">定义</a>
     *  by James Clark。
     * 虽然这不是<em>标准</em>规范,但它是常用的,例如。
     *  {@link javax.xml.transform.Transformer#setParameter(String name,Object value)}。
     * 
     * @param qNameAsString <code>String</code> representation
     * of the <code>QName</code>
     *
     * @throws IllegalArgumentException When <code>qNameAsString</code> is
     *   <code>null</code> or malformed
     *
     * @return <code>QName</code> corresponding to the given <code>String</code>
     * @see #toString() QName.toString()
     */
    public static QName valueOf(String qNameAsString) {

        // null is not valid
        if (qNameAsString == null) {
            throw new IllegalArgumentException(
                    "cannot create QName from \"null\" or \"\" String");
        }

        // "" local part is valid to preserve compatible behavior with QName 1.0
        if (qNameAsString.length() == 0) {
            return new QName(
                XMLConstants.NULL_NS_URI,
                qNameAsString,
                XMLConstants.DEFAULT_NS_PREFIX);
        }

        // local part only?
        if (qNameAsString.charAt(0) != '{') {
            return new QName(
                XMLConstants.NULL_NS_URI,
                qNameAsString,
                XMLConstants.DEFAULT_NS_PREFIX);
        }

        // Namespace URI improperly specified?
        if (qNameAsString.startsWith("{" + XMLConstants.NULL_NS_URI + "}")) {
            throw new IllegalArgumentException(
                "Namespace URI .equals(XMLConstants.NULL_NS_URI), "
                + ".equals(\"" + XMLConstants.NULL_NS_URI + "\"), "
                + "only the local part, "
                + "\""
                + qNameAsString.substring(2 + XMLConstants.NULL_NS_URI.length())
                + "\", "
                + "should be provided.");
        }

        // Namespace URI and local part specified
        int endOfNamespaceURI = qNameAsString.indexOf('}');
        if (endOfNamespaceURI == -1) {
            throw new IllegalArgumentException(
                "cannot create QName from \""
                    + qNameAsString
                    + "\", missing closing \"}\"");
        }
        return new QName(
            qNameAsString.substring(1, endOfNamespaceURI),
            qNameAsString.substring(endOfNamespaceURI + 1),
            XMLConstants.DEFAULT_NS_PREFIX);
    }
}
