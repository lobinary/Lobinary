/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2005, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.transform;

/**
 * Provides string constants that can be used to set
 * output properties for a Transformer, or to retrieve
 * output properties from a Transformer or Templates object.
 * <p>All the fields in this class are read-only.</p>
 *
 * <p>
 *  提供可用于设置Transformer的输出属性或从Transformer或Templates对象检索输出属性的字符串常量。 <p>此类别中的所有字段都是只读的。</p>
 * 
 * 
 * @see <a href="http://www.w3.org/TR/xslt#output">
 *  section 16 of the XSL Transformations (XSLT) W3C Recommendation</a>
 */
public class OutputKeys {

    /**
     * Default constructor is private on purpose.  This class is
     * only for static variable access, and should never be constructed.
     * <p>
     *  默认构造函数是私有的。这个类只用于静态变量访问,不应该被构造。
     * 
     */
    private OutputKeys() { }

    /**
     * method = "xml" | "html" | "text" | <var>expanded name</var>.
     *
     * <p>The value of the method property identifies the overall method that
     * should be used for outputting the result tree.  Other non-namespaced
     * values may be used, such as "xhtml", but, if accepted, the handling
     * of such values is implementation defined.  If any of the method values
     * are not accepted and are not namespace qualified,
     * then {@link javax.xml.transform.Transformer#setOutputProperty}
     * or {@link javax.xml.transform.Transformer#setOutputProperties} will
     * throw a {@link java.lang.IllegalArgumentException}.</p>
     *
     * <p>
     *  method ="xml"| "html"| "text"| <var> expanded name </var>。
     * 
     *  <p> method属性的值标识应用于输出结果树的总体方法。可以使用其他非命名空间值,例如"xhtml",但是如果被接受,则这些值的处理是实现定义的。
     * 如果任何方法值未被接受且未命名空间限定,则{@link javax.xml.transform.Transformer#setOutputProperty}或{@link javax.xml.transform.Transformer#setOutputProperties}
     * 将抛出一个{@link java .lang.IllegalArgumentException}。
     *  <p> method属性的值标识应用于输出结果树的总体方法。可以使用其他非命名空间值,例如"xhtml",但是如果被接受,则这些值的处理是实现定义的。</p>。
     * 
     * 
     * @see <a href="http://www.w3.org/TR/xslt#output">
     *  section 16 of the XSL Transformations (XSLT) W3C Recommendation</a>
     */
    public static final String METHOD = "method";

    /**
     * version = <var>nmtoken</var>.
     *
     * <p><code>version</code> specifies the version of the output
     * method.</p>
     * <p>When the output method is "xml", the version value specifies the
     * version of XML to be used for outputting the result tree. The default
     * value for the xml output method is 1.0. When the output method is
     * "html", the version value indicates the version of the HTML.
     * The default value for the xml output method is 4.0, which specifies
     * that the result should be output as HTML conforming to the HTML 4.0
     * Recommendation [HTML].  If the output method is "text", the version
     * property is ignored.</p>
     * <p>
     *  version = <var> nmtoken </var>。
     * 
     * <p> <code> version </code>指定输出方法的版本。</p> <p>当输出方法是"xml"时,版本值指定用于输出结果的XML版本树。 xml输出方法的默认值为1.0。
     * 当输出方法是"html"时,版本值表示HTML的版本。 xml输出方法的默认值为4.0,它指定结果应输出为HTML,符合HTML 4.0建议[HTML]。如果输出方法是"text",则会忽略版本属性。
     * </p>。
     * 
     * 
     * @see <a href="http://www.w3.org/TR/xslt#output">
     *  section 16 of the XSL Transformations (XSLT) W3C Recommendation</a>
     */
    public static final String VERSION = "version";

    /**
     * encoding = <var>string</var>.
     *
     * <p><code>encoding</code> specifies the preferred character
     * encoding that the Transformer should use to encode sequences of
     * characters as sequences of bytes. The value of the encoding property should be
     * treated case-insensitively. The value must only contain characters in
     * the range #x21 to #x7E (i.e., printable ASCII characters). The value
     * should either be a <code>charset</code> registered with the Internet
     * Assigned Numbers Authority <a href="http://www.iana.org/">[IANA]</a>,
     * <a href="http://www.ietf.org/rfc/rfc2278.txt">[RFC2278]</a>
     * or start with <code>X-</code>.</p>
     * <p>
     *  encoding = <var> string </var>。
     * 
     *  <p> <code> encoding </code>指定了Transformer应该用来将字符序列编码为字节序列的首选字符编码。编码属性的值应该不区分大小写。
     * 该值只能包含#x21到#x7E范围内的字符(即,可打印的ASCII字符)。
     * 该值应为通过互联网号码分配机构<a href="http://www.iana.org/"> [IANA] </a>注册的<code>字符集</code>,<a href = "http://www.ietf.org/rfc/rfc2278.txt">
     *  [RFC2278] </a>或以<code> X  -  </code>开头。
     * 该值只能包含#x21到#x7E范围内的字符(即,可打印的ASCII字符)。</p>。
     * 
     * 
     * @see <a href="http://www.w3.org/TR/xslt#output">
     * section 16 of the XSL Transformations (XSLT) W3C Recommendation</a>
     */
    public static final String ENCODING = "encoding";

    /**
     * omit-xml-declaration = "yes" | "no".
     *
     * <p><code>omit-xml-declaration</code> specifies whether the XSLT
     * processor should output an XML declaration; the value must be
     * <code>yes</code> or <code>no</code>.</p>
     * <p>
     *  omit-xml-declaration ="yes"| "没有"。
     * 
     *  <p> <code> omit-xml-declaration </code>指定XSLT处理器是否应输出XML声明;该值必须为<code> yes </code>或<code>否</code>。
     * </p>。
     * 
     * 
     * @see <a href="http://www.w3.org/TR/xslt#output">
     *  section 16 of the XSL Transformations (XSLT) W3C Recommendation</a>
     */
    public static final String OMIT_XML_DECLARATION = "omit-xml-declaration";

    /**
     * standalone = "yes" | "no".
     *
     * <p><code>standalone</code> specifies whether the Transformer
     * should output a standalone document declaration; the value must be
     * <code>yes</code> or <code>no</code>.</p>
     * <p>
     *  standalone ="yes"| "没有"。
     * 
     * <p> <code> standalone </code>指定Transformer是否应输出独立文档声明;该值必须为<code> yes </code>或<code>否</code>。</p>
     * 
     * 
     * @see <a href="http://www.w3.org/TR/xslt#output">
     *  section 16 of the XSL Transformations (XSLT) W3C Recommendation</a>
     */
    public static final String STANDALONE = "standalone";

    /**
     * doctype-public = <var>string</var>.
     * <p>See the documentation for the {@link #DOCTYPE_SYSTEM} property
     * for a description of what the value of the key should be.</p>
     *
     * <p>
     *  doctype-public = <var> string </var>。 <p>请参阅{@link #DOCTYPE_SYSTEM}属性的文档,了解键的值应该是什么。</p>
     * 
     * 
     * @see <a href="http://www.w3.org/TR/xslt#output">
     *  section 16 of the XSL Transformations (XSLT) W3C Recommendation</a>
     */
    public static final String DOCTYPE_PUBLIC = "doctype-public";

    /**
     * doctype-system = <var>string</var>.
     * <p><code>doctype-system</code> specifies the system identifier
     * to be used in the document type declaration.</p>
     * <p>If the doctype-system property is specified, the xml output method
     * should output a document type declaration immediately before the first
     * element. The name following &lt;!DOCTYPE should be the name of the first
     * element. If doctype-public property is also specified, then the xml
     * output method should output PUBLIC followed by the public identifier
     * and then the system identifier; otherwise, it should output SYSTEM
     * followed by the system identifier. The internal subset should be empty.
     * The value of the doctype-public property should be ignored unless the doctype-system
     * property is specified.</p>
     * <p>If the doctype-public or doctype-system properties are specified,
     * then the html output method should output a document type declaration
     * immediately before the first element. The name following &lt;!DOCTYPE
     * should be HTML or html. If the doctype-public property is specified,
     * then the output method should output PUBLIC followed by the specified
     * public identifier; if the doctype-system property is also specified,
     * it should also output the specified system identifier following the
     * public identifier. If the doctype-system property is specified but
     * the doctype-public property is not specified, then the output method
     * should output SYSTEM followed by the specified system identifier.</p>
     *
     * <p><code>doctype-system</code> specifies the system identifier
     * to be used in the document type declaration.</p>
     * <p>
     * doctype-system = <var> string </var>。 <p> <code> doctype-system </code>指定要在文档类型声明中使用的系统标识符。
     * </p> <p>如果指定了doctype-system属性,xml输出方法应输出文档类型声明紧接在第一个元素之前。 &lt;！DOCTYPE之后的名称应该是第一个元素的名称。
     * 如果还指定了doctype-public属性,那么xml输出方法应该输出PUBLIC,后跟公共标识符,然后是系统标识符;否则,它应该输出SYSTEM后跟系统标识符。内部子集应为空。
     * 除非指定了doctype-system属性,否则应忽略doctype-public属性的值。
     * </p> <p>如果指定了doctype-public或doctype-system属性,则html输出方法应输出文档类型声明紧接在第一个元素之前。
     * 以下&lt;！DOCTYPE的名称应为HTML或html。
     * 如果指定了doctype-public属性,那么输出方法应该输出PUBLIC,后跟指定的公共标识符;如果还指定了doctype-system属性,它还应该在公共标识符后输出指定的系统标识符。
     * 如果指定了doctype-system属性但未指定doctype-public属性,则输出方法应输出SYSTEM,后跟指定的系统标识符。</p>。
     * 
     * <p> <code> doctype-system </code>指定要在文档类型声明中使用的系统标识符。</p>
     * 
     * 
     * @see <a href="http://www.w3.org/TR/xslt#output">
     *  section 16 of the XSL Transformations (XSLT) W3C Recommendation</a>
     */
    public static final String DOCTYPE_SYSTEM = "doctype-system";

    /**
     * cdata-section-elements = <var>expanded names</var>.
     *
     * <p><code>cdata-section-elements</code> specifies a whitespace delimited
     * list of the names of elements whose text node children should be output
     * using CDATA sections. Note that these names must use the format
     * described in the section Qualfied Name Representation in
     * {@link javax.xml.transform}.</p>
     *
     * <p>
     *  cdata-section-elements = <var> expanded names </var>。
     * 
     *  <p> <code> cdata-section-elements </code>指定使用CDATA节段输出文本节点子元素的元素名称的空格分隔列表。
     * 请注意,这些名称必须使用{@link javax.xml.transform}中的Qualfied Name Representation部分中描述的格式。</p>。
     * 
     * 
     * @see <a href="http://www.w3.org/TR/xslt#output">
     *  section 16 of the XSL Transformations (XSLT) W3C Recommendation.</a>
     */
    public static final String CDATA_SECTION_ELEMENTS =
        "cdata-section-elements";

    /**
     * indent = "yes" | "no".
     *
     * <p><code>indent</code> specifies whether the Transformer may
     * add additional whitespace when outputting the result tree; the value
     * must be <code>yes</code> or <code>no</code>.  </p>
     * <p>
     *  indent ="yes"| "没有"。
     * 
     *  <p> <code>缩进</code>指定在输出结果树时Transformer是否可以添加额外的空格;该值必须为<code> yes </code>或<code>否</code>。 </p>
     * 
     * 
     * @see <a href="http://www.w3.org/TR/xslt#output">
     *  section 16 of the XSL Transformations (XSLT) W3C Recommendation</a>
     */
    public static final String INDENT = "indent";

    /**
     * media-type = <var>string</var>.
     *
     * <p><code>media-type</code> specifies the media type (MIME
     * content type) of the data that results from outputting the result
     * tree. The <code>charset</code> parameter should not be specified
     * explicitly; instead, when the top-level media type is
     * <code>text</code>, a <code>charset</code> parameter should be added
     * according to the character encoding actually used by the output
     * method.  </p>
     * <p>
     *  media-type = <var> string </var>。
     * 
     *  <p> <code> media-type </code>指定输出结果树所产生的数据的媒体类型(MIME内容类型)。
     * 不应明确指定<code> charset </code>参数;而是,当顶级媒体类型是<code> text </code>时,应根据输出方法实际使用的字符编码添加<code> charset </code>
     * 
     * @see <a href="http://www.w3.org/TR/xslt#output">s
     * ection 16 of the XSL Transformations (XSLT) W3C Recommendation</a>
     */
    public static final String MEDIA_TYPE = "media-type";
}
