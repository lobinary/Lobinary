/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml;

/**
 * <p>Utility class to contain basic XML values as constants.</p>
 *
 * <p>
 *  <p>实用程序类包含基本XML值作为常量。</p>
 * 
 * 
 * @author <a href="mailto:Jeff.Suttor@Sun.com">Jeff Suttor</a>
 * @version $Revision: 1.8 $, $Date: 2010/05/25 16:19:45 $
 * @see <a href="http://www.w3.org/TR/xml11/">Extensible Markup Language (XML) 1.1</a>
 * @see <a href="http://www.w3.org/TR/REC-xml">Extensible Markup Language (XML) 1.0 (Second Edition)</a>
 * @see <a href="http://www.w3.org/XML/xml-V10-2e-errata">XML 1.0 Second Edition Specification Errata</a>
 * @see <a href="http://www.w3.org/TR/xml-names11/">Namespaces in XML 1.1</a>
 * @see <a href="http://www.w3.org/TR/REC-xml-names">Namespaces in XML</a>
 * @see <a href="http://www.w3.org/XML/xml-names-19990114-errata">Namespaces in XML Errata</a>
 * @see <a href="http://www.w3.org/TR/xmlschema-1/">XML Schema Part 1: Structures</a>
 * @since 1.5
 **/

public final class XMLConstants {

    /**
     * <p>Private constructor to prevent instantiation.</p>
     * <p>
     *  <p>私人构造函数阻止实例化。</p>
     * 
     */
        private XMLConstants() {
        }

    /**
     * <p>Namespace URI to use to represent that there is no Namespace.</p>
     *
     * <p>Defined by the Namespace specification to be "".</p>
     *
     * <p>
     *  <p>用于表示没有命名空间的命名空间URI。</p>
     * 
     *  <p>由命名空间规范定义为""。</p>
     * 
     * 
     * @see <a href="http://www.w3.org/TR/REC-xml-names/#defaulting">
     * Namespaces in XML, 5.2 Namespace Defaulting</a>
     */
    public static final String NULL_NS_URI = "";

    /**
     * <p>Prefix to use to represent the default XML Namespace.</p>
     *
     * <p>Defined by the XML specification to be "".</p>
     *
     * <p>
     *  <p>用于表示默认XML命名空间的前缀。</p>
     * 
     *  <p>由XML规范定义为""。</p>
     * 
     * 
     * @see <a
     * href="http://www.w3.org/TR/REC-xml-names/#ns-qualnames">
     * Namespaces in XML, 3. Qualified Names</a>
     */
    public static final String DEFAULT_NS_PREFIX = "";

    /**
     * <p>The official XML Namespace name URI.</p>
     *
     * <p>Defined by the XML specification to be
     * "{@code http://www.w3.org/XML/1998/namespace}".</p>
     *
     * <p>
     *  <p>官方XML命名空间名称URI。</p>
     * 
     *  <p>由XML规范定义为"{@code http://www.w3.org/XML/1998/namespace}"。</p>
     * 
     * 
     * @see <a
     * href="http://www.w3.org/TR/REC-xml-names/#ns-qualnames">
     * Namespaces in XML, 3. Qualified Names</a>
     */
    public static final String XML_NS_URI =
        "http://www.w3.org/XML/1998/namespace";

    /**
     * <p>The official XML Namespace prefix.</p>
     *
     * <p>Defined by the XML specification to be "{@code xml}".</p>
     *
     * <p>
     *  <p>官方XML命名空间前缀。</p>
     * 
     *  <p>由XML规范定义为"{@code xml}"。</p>
     * 
     * 
     * @see <a
     * href="http://www.w3.org/TR/REC-xml-names/#ns-qualnames">
     * Namespaces in XML, 3. Qualified Names<</a>
     */
    public static final String XML_NS_PREFIX = "xml";

    /**
     * <p>The official XML attribute used for specifying XML Namespace
     * declarations, {@link #XMLNS_ATTRIBUTE
     * XMLConstants.XMLNS_ATTRIBUTE}, Namespace name URI.</p>
     *
     * <p>Defined by the XML specification to be
     * "{@code http://www.w3.org/2000/xmlns/}".</p>
     *
     * <p>
     *  <p>用于指定XML命名空间声明的官方XML属性,{@link #XMLNS_ATTRIBUTE XMLConstants.XMLNS_ATTRIBUTE},命名空间名称URI。</p>
     * 
     *  <p>由XML规范定义为"{@code http://www.w3.org/2000/xmlns/}"。</p>
     * 
     * 
     * @see <a
     * href="http://www.w3.org/TR/REC-xml-names/#ns-qualnames">
     * Namespaces in XML, 3. Qualified Names</a>
     * @see <a
     * href="http://www.w3.org/XML/xml-names-19990114-errata">
     * Namespaces in XML Errata</a>
     */
    public static final String XMLNS_ATTRIBUTE_NS_URI =
        "http://www.w3.org/2000/xmlns/";

    /**
     * <p>The official XML attribute used for specifying XML Namespace
     * declarations.</p>
     *
     * <p>It is <strong><em>NOT</em></strong> valid to use as a
     * prefix.  Defined by the XML specification to be
     * "{@code xmlns}".</p>
     *
     * <p>
     *  <p>用于指定XML命名空间声明的官方XML属性。</p>
     * 
     *  <p> <strong> </em> </strong>有效作为前缀使用。由XML规范定义为"{@code xmlns}"。</p>
     * 
     * 
     * @see <a
     * href="http://www.w3.org/TR/REC-xml-names/#ns-qualnames">
     * Namespaces in XML, 3. Qualified Names</a>
     */
    public static final String XMLNS_ATTRIBUTE = "xmlns";

    /**
     * <p>W3C XML Schema Namespace URI.</p>
     *
     * <p>Defined to be "{@code http://www.w3.org/2001/XMLSchema}".
     *
     * <p>
     *  <p> W3C XML模式名称空间URI。</p>
     * 
     *  <p>定义为"{@code http://www.w3.org/2001/XMLSchema}"。
     * 
     * 
     * @see <a href=
     *  "http://www.w3.org/TR/xmlschema-1/#Instance_Document_Constructions">
     *  XML Schema Part 1:
     *  Structures, 2.6 Schema-Related Markup in Documents Being Validated</a>
     */
    public static final String W3C_XML_SCHEMA_NS_URI =
        "http://www.w3.org/2001/XMLSchema";

    /**
     * <p>W3C XML Schema Instance Namespace URI.</p>
     *
     * <p>Defined to be "{@code http://www.w3.org/2001/XMLSchema-instance}".</p>
     *
     * <p>
     *  <p> W3C XML模式实例命名空间URI。</p>
     * 
     *  <p>定义为"{@code http://www.w3.org/2001/XMLSchema-instance}"。</p>
     * 
     * 
     * @see <a href=
     *  "http://www.w3.org/TR/xmlschema-1/#Instance_Document_Constructions">
     *  XML Schema Part 1:
     *  Structures, 2.6 Schema-Related Markup in Documents Being Validated</a>
     */
    public static final String W3C_XML_SCHEMA_INSTANCE_NS_URI =
        "http://www.w3.org/2001/XMLSchema-instance";

        /**
         * <p>W3C XPath Datatype Namespace URI.</p>
         *
         * <p>Defined to be "{@code http://www.w3.org/2003/11/xpath-datatypes}".</p>
         *
         * <p>
         *  <p> W3C XPath数据类型命名空间URI。</p>
         * 
         * <p>定义为"{@code http://www.w3.org/2003/11/xpath-datatypes}"。</p>
         * 
         * 
         * @see <a href="http://www.w3.org/TR/xpath-datamodel">XQuery 1.0 and XPath 2.0 Data Model</a>
         */
        public static final String W3C_XPATH_DATATYPE_NS_URI = "http://www.w3.org/2003/11/xpath-datatypes";

    /**
     * <p>XML Document Type Declaration Namespace URI as an arbitrary value.</p>
     *
     * <p>Since not formally defined by any existing standard, arbitrarily define to be "{@code http://www.w3.org/TR/REC-xml}".
     * <p>
     *  <p> XML文档类型声明命名空间URI为任意值。</p>
     * 
     *  <p>由于没有任何现有标准正式定义,任意定义为"{@code http://www.w3.org/TR/REC-xml}"。
     * 
     */
    public static final String XML_DTD_NS_URI = "http://www.w3.org/TR/REC-xml";

        /**
         * <p>RELAX NG Namespace URI.</p>
         *
         * <p>Defined to be "{@code http://relaxng.org/ns/structure/1.0}".</p>
         *
         * <p>
         *  <p> RELAX NG命名空间URI。</p>
         * 
         *  <p>定义为"{@code http://relaxng.org/ns/structure/1.0}"。</p>
         * 
         * 
         * @see <a href="http://relaxng.org/spec-20011203.html">RELAX NG Specification</a>
         */
        public static final String RELAXNG_NS_URI = "http://relaxng.org/ns/structure/1.0";

        /**
         * <p>Feature for secure processing.</p>
         *
         * <ul>
         *   <li>
         *     {@code true} instructs the implementation to process XML securely.
         *     This may set limits on XML constructs to avoid conditions such as denial of service attacks.
         *   </li>
         *   <li>
         *     {@code false} instructs the implementation to process XML in accordance with the XML specifications
         *     ignoring security issues such as limits on XML constructs to avoid conditions such as denial of service attacks.
         *   </li>
         * </ul>
         * <p>
         *  <p>安全处理功能。</p>
         * 
         * <ul>
         * <li>
         *  {@code true}指示实现安全地处理XML。这可能对XML构造设置限制,以避免诸如拒绝服务攻击的条件。
         * </li>
         * <li>
         *  {@code false}指示实施根据XML规范处理XML,忽略诸如对XML结构的限制等安全问题,以避免诸如拒绝服务攻击的情况。
         * </li>
         * </ul>
         */
        public static final String FEATURE_SECURE_PROCESSING = "http://javax.xml.XMLConstants/feature/secure-processing";


        /**
         * <p>Property: accessExternalDTD</p>
         *
         * <p>
         * Restrict access to external DTDs and external Entity References to the protocols specified.
         * If access is denied due to the restriction of this property, a runtime exception that
         * is specific to the context is thrown. In the case of {@link javax.xml.parsers.SAXParser}
         * for example, {@link org.xml.sax.SAXException} is thrown.
         * </p>
         *
         * <p>
         * <b>Value: </b> a list of protocols separated by comma. A protocol is the scheme portion of a
         * {@link java.net.URI}, or in the case of the JAR protocol, "jar" plus the scheme portion
         * separated by colon.
         * A scheme is defined as:
         *
         * <blockquote>
         * scheme = alpha *( alpha | digit | "+" | "-" | "." )<br>
         * where alpha = a-z and A-Z.<br><br>
         *
         * And the JAR protocol:<br>
         *
         * jar[:scheme]<br><br>
         *
         * Protocols including the keyword "jar" are case-insensitive. Any whitespaces as defined by
         * {@link java.lang.Character#isSpaceChar } in the value will be ignored.
         * Examples of protocols are file, http, jar:file.
         *
         * </blockquote>
         *</p>
         *
         *<p>
         * <b>Default value:</b> The default value is implementation specific and therefore not specified.
         * The following options are provided for consideration:
         * <blockquote>
         * <UL>
         *     <LI>an empty string to deny all access to external references;</LI>
         *     <LI>a specific protocol, such as file, to give permission to only the protocol;</LI>
         *     <LI>the keyword "all" to grant  permission to all protocols.</LI>
         *</UL><br>
         *      When FEATURE_SECURE_PROCESSING is enabled,  it is recommended that implementations
         *      restrict external connections by default, though this may cause problems for applications
         *      that process XML/XSD/XSL with external references.
         * </blockquote>
         * </p>
         *
         * <p>
         * <b>Granting all access:</b>  the keyword "all" grants permission to all protocols.
         * </p>
         * <p>
         * <b>System Property:</b> The value of this property can be set or overridden by
         * system property {@code javax.xml.accessExternalDTD}.
         * </p>
         *
         * <p>
         * <b>${JAVA_HOME}/lib/jaxp.properties:</b> This configuration file is in standard
         * {@link java.util.Properties} format. If the file exists and the system property is specified,
         * its value will be used to override the default of the property.
         * </p>
         *
         * <p>
         *
         * </p>
         * <p>
         *  <p>属性：accessExternalDTD </p>
         * 
         * <p>
         *  限制对外部DTD和外部实体的访问对指定协议的引用。如果由于此属性的限制而拒绝访问,则会抛出特定于上下文的运行时异常。
         * 例如,在{@link javax.xml.parsers.SAXParser}的情况下,会抛出{@link org.xml.sax.SAXException}。
         * </p>
         * 
         * <p>
         *  <b>值：</b>由逗号分隔的协议列表。协议是{@link java.net.URI}的方案部分,或者在JAR协议的情况下,"jar"加上由冒号分隔的方案部分。方案定义为：
         * 
         * <blockquote>
         *  scheme = alpha *(alpha | digit |"+"|" - "|"。)<br>其中alpha = a-z和A-Z。
         * 
         *  和JAR协议：<br>
         * 
         * jar [：scheme] <br> <br>
         * 
         *  包括关键字"jar"的协议不区分大小写。值中的{@link java.lang.Character#isSpaceChar}定义的任何空格都将被忽略。
         * 协议的示例是file,http,jar：file。
         * 
         * </blockquote>
         * /p>
         * 
         * p>
         *  <b>默认值：</b>默认值是特定于实施的,因此未指定。提供以下选项供考虑：
         * <blockquote>
         * <UL>
         *  <LI>空字符串以拒绝对外部引用的所有访问; </LI> <LI>特定协议(例如文件)仅授予协议权限; </LI> <LI>关键字"all" </LI> / UL> <br>启用FEATURE_SEC
         * URE_PROCESSING时,建议实施限制外部连接,但这可能会对使用外部引用处理XML / XSD / XSL的应用程序造成问题。
         * </blockquote>
         * </p>
         * 
         * <p>
         *  <b>授予所有访问权限：</b>关键字"all"授予所有协议的权限。
         * </p>
         * <p>
         *  <b>系统属性：</b>此属性的值可以由系统属性{@code javax.xml.accessExternalDTD}设置或覆盖。
         * </p>
         * 
         * <p>
         *  <b> $ {JAVA_HOME} /lib/jaxp.properties：</b>此配置文件采用标准{@link java.util.Properties}格式。
         * 如果文件存在并且指定了系统属性,则其值将用于覆盖属性的默认值。
         * </p>
         * 
         * <p>
         * 
         * </p>
         * 
         * @since 1.7
         */
        public static final String ACCESS_EXTERNAL_DTD = "http://javax.xml.XMLConstants/property/accessExternalDTD";

        /**
         * <p>Property: accessExternalSchema</p>
         *
         * <p>
         * Restrict access to the protocols specified for external reference set by the
         * schemaLocation attribute, Import and Include element. If access is denied
         * due to the restriction of this property, a runtime exception that is specific
         * to the context is thrown. In the case of {@link javax.xml.validation.SchemaFactory}
         * for example, org.xml.sax.SAXException is thrown.
         * </p>
         * <p>
         * <b>Value:</b> a list of protocols separated by comma. A protocol is the scheme portion of a
         * {@link java.net.URI}, or in the case of the JAR protocol, "jar" plus the scheme portion
         * separated by colon.
         * A scheme is defined as:
         *
         * <blockquote>
         * scheme = alpha *( alpha | digit | "+" | "-" | "." )<br>
         * where alpha = a-z and A-Z.<br><br>
         *
         * And the JAR protocol:<br>
         *
         * jar[:scheme]<br><br>
         *
         * Protocols including the keyword "jar" are case-insensitive. Any whitespaces as defined by
         * {@link java.lang.Character#isSpaceChar } in the value will be ignored.
         * Examples of protocols are file, http, jar:file.
         *
         * </blockquote>
         *</p>
         *
         *<p>
         * <b>Default value:</b> The default value is implementation specific and therefore not specified.
         * The following options are provided for consideration:
         * <blockquote>
         * <UL>
         *     <LI>an empty string to deny all access to external references;</LI>
         *     <LI>a specific protocol, such as file, to give permission to only the protocol;</LI>
         *     <LI>the keyword "all" to grant  permission to all protocols.</LI>
         *</UL><br>
         *      When FEATURE_SECURE_PROCESSING is enabled,  it is recommended that implementations
         *      restrict external connections by default, though this may cause problems for applications
         *      that process XML/XSD/XSL with external references.
         * </blockquote>
         * </p>
         * <p>
         * <b>Granting all access:</b>  the keyword "all" grants permission to all protocols.
         * </p>
         *
         * <p>
         * <b>System Property:</b> The value of this property can be set or overridden by
         * system property {@code javax.xml.accessExternalSchema}
         * </p>
         *
         * <p>
         * <b>${JAVA_HOME}/lib/jaxp.properties:</b> This configuration file is in standard
         * java.util.Properties format. If the file exists and the system property is specified,
         * its value will be used to override the default of the property.
         *
         * <p>
         *  <p>属性：accessExternalSchema </p>
         * 
         * <p>
         * 限制对由schemaLocation属性(Import和Include元素)设置的外部引用指定的协议的访问。如果由于此属性的限制而拒绝访问,则会抛出特定于上下文的运行时异常。
         * 例如,在{@link javax.xml.validation.SchemaFactory}的情况下,抛出org.xml.sax.SAXException。
         * </p>
         * <p>
         *  <b>值：</b>由逗号分隔的协议列表。协议是{@link java.net.URI}的方案部分,或者在JAR协议的情况下,"jar"加上由冒号分隔的方案部分。方案定义为：
         * 
         * <blockquote>
         *  scheme = alpha *(alpha | digit |"+"|" - "|"。)<br>其中alpha = a-z和A-Z。
         * 
         *  和JAR协议：<br>
         * 
         *  jar [：scheme] <br> <br>
         * 
         *  包括关键字"jar"的协议不区分大小写。值中的{@link java.lang.Character#isSpaceChar}定义的任何空格都将被忽略。
         * 协议的示例是file,http,jar：file。
         * 
         * </blockquote>
         * /p>
         * 
         * p>
         *  <b>默认值：</b>默认值是特定于实施的,因此未指定。提供以下选项供考虑：
         * <blockquote>
         * <UL>
         * <LI>空字符串以拒绝对外部引用的所有访问; </LI> <LI>特定协议(例如文件)仅授予协议权限; </LI> <LI>关键字"all" </LI> / UL> <br>启用FEATURE_SECU
         * RE_PROCESSING时,建议实施限制外部连接,但这可能会对使用外部引用处理XML / XSD / XSL的应用程序造成问题。
         * </blockquote>
         * </p>
         * <p>
         *  <b>授予所有访问权限：</b>关键字"all"授予所有协议的权限。
         * </p>
         * 
         * <p>
         *  <b>系统属性：</b>此属性的值可以由系统属性设置或覆盖{@code javax.xml.accessExternalSchema}
         * </p>
         * 
         * <p>
         *  <b> $ {JAVA_HOME} /lib/jaxp.properties：</b>此配置文件采用标准java.util.Properties格式。
         * 如果文件存在并且指定了系统属性,则其值将用于覆盖属性的默认值。
         * 
         * 
         * @since 1.7
         * </p>
         */
        public static final String ACCESS_EXTERNAL_SCHEMA = "http://javax.xml.XMLConstants/property/accessExternalSchema";

        /**
         * <p>Property: accessExternalStylesheet</p>
         *
         * <p>
         * Restrict access to the protocols specified for external references set by the
         * stylesheet processing instruction, Import and Include element, and document function.
         * If access is denied due to the restriction of this property, a runtime exception
         * that is specific to the context is thrown. In the case of constructing new
         * {@link javax.xml.transform.Transformer} for example,
         * {@link javax.xml.transform.TransformerConfigurationException}
         * will be thrown by the {@link javax.xml.transform.TransformerFactory}.
         * </p>
         * <p>
         * <b>Value:</b> a list of protocols separated by comma. A protocol is the scheme portion of a
         * {@link java.net.URI}, or in the case of the JAR protocol, "jar" plus the scheme portion
         * separated by colon.
         * A scheme is defined as:
         *
         * <blockquote>
         * scheme = alpha *( alpha | digit | "+" | "-" | "." )<br>
         * where alpha = a-z and A-Z.<br><br>
         *
         * And the JAR protocol:<br>
         *
         * jar[:scheme]<br><br>
         *
         * Protocols including the keyword "jar" are case-insensitive. Any whitespaces as defined by
         * {@link java.lang.Character#isSpaceChar } in the value will be ignored.
         * Examples of protocols are file, http, jar:file.
         *
         * </blockquote>
         *</p>
         *
         *<p>
         * <b>Default value:</b> The default value is implementation specific and therefore not specified.
         * The following options are provided for consideration:
         * <blockquote>
         * <UL>
         *     <LI>an empty string to deny all access to external references;</LI>
         *     <LI>a specific protocol, such as file, to give permission to only the protocol;</LI>
         *     <LI>the keyword "all" to grant  permission to all protocols.</LI>
         *</UL><br>
         *      When FEATURE_SECURE_PROCESSING is enabled,  it is recommended that implementations
         *      restrict external connections by default, though this may cause problems for applications
         *      that process XML/XSD/XSL with external references.
         * </blockquote>
         * </p>
         * <p>
         * <b>Granting all access:</b>  the keyword "all" grants permission to all protocols.
         * </p>
         *
         * <p>
         * <b>System Property:</b> The value of this property can be set or overridden by
         * system property {@code javax.xml.accessExternalStylesheet}
         * </p>
         *
         * <p>
         * <b>${JAVA_HOME}/lib/jaxp.properties: </b> This configuration file is in standard
         * java.util.Properties format. If the file exists and the system property is specified,
         * its value will be used to override the default of the property.
         *
         * <p>
         *  <p>属性：accessExternalStylesheet </p>
         * 
         * <p>
         *  限制对样式表处理指令,导入和包含元素和文档函数设置的外部引用指定的协议的访问。如果由于此属性的限制而拒绝访问,则会抛出特定于上下文的运行时异常。
         * 例如,在构造新的{@link javax.xml.transform.Transformer}的情况下,{@link javax.xml.transform.TransformerConfigurationException}
         * 将由{@link javax.xml.transform.TransformerFactory}抛出。
         *  限制对样式表处理指令,导入和包含元素和文档函数设置的外部引用指定的协议的访问。如果由于此属性的限制而拒绝访问,则会抛出特定于上下文的运行时异常。
         * </p>
         * <p>
         * <b>值：</b>由逗号分隔的协议列表。协议是{@link java.net.URI}的方案部分,或者在JAR协议的情况下,"jar"加上由冒号分隔的方案部分。方案定义为：
         * 
         * <blockquote>
         *  scheme = alpha *(alpha | digit |"+"|" - "|"。)<br>其中alpha = a-z和A-Z。
         * 
         *  和JAR协议：<br>
         * 
         *  jar [：scheme] <br> <br>
         * 
         *  包括关键字"jar"的协议不区分大小写。值中的{@link java.lang.Character#isSpaceChar}定义的任何空格都将被忽略。
         * 协议的示例是file,http,jar：file。
         * 
         * </blockquote>
         * 
         * @since 1.7
         */
        public static final String ACCESS_EXTERNAL_STYLESHEET = "http://javax.xml.XMLConstants/property/accessExternalStylesheet";

}
