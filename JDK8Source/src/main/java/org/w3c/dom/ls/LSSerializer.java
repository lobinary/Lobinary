/***** Lobxxx Translate Finished ******/
/*
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

/*
 *
 *
 *
 *
 *
 * Copyright (c) 2004 World Wide Web Consortium,
 *
 * (Massachusetts Institute of Technology, European Research Consortium for
 * Informatics and Mathematics, Keio University). All Rights Reserved. This
 * work is distributed under the W3C(r) Software License [1] in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * [1] http://www.w3.org/Consortium/Legal/2002/copyright-software-20021231
 * <p>
 *  版权所有(c)2004万维网联盟,
 * 
 *  (马萨诸塞理工学院,欧洲研究信息学和数学联合会,庆应大学)保留所有权利本作品根据W3C(r)软件许可证[1]分发,希望它有用,但没有任何保证;甚至没有对适销性或适用于特定用途的隐含保证
 * 
 *  [1] http：// wwww3org / Consortium / Legal / 2002 / copyright-software-20021231
 * 
 */

package org.w3c.dom.ls;

import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.Node;
import org.w3c.dom.DOMException;

/**
 *  A <code>LSSerializer</code> provides an API for serializing (writing) a
 * DOM document out into XML. The XML data is written to a string or an
 * output stream. Any changes or fixups made during the serialization affect
 * only the serialized data. The <code>Document</code> object and its
 * children are never altered by the serialization operation.
 * <p> During serialization of XML data, namespace fixup is done as defined in [<a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>DOM Level 3 Core</a>]
 * , Appendix B. [<a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Core-20001113'>DOM Level 2 Core</a>]
 *  allows empty strings as a real namespace URI. If the
 * <code>namespaceURI</code> of a <code>Node</code> is empty string, the
 * serialization will treat them as <code>null</code>, ignoring the prefix
 * if any.
 * <p> <code>LSSerializer</code> accepts any node type for serialization. For
 * nodes of type <code>Document</code> or <code>Entity</code>, well-formed
 * XML will be created when possible (well-formedness is guaranteed if the
 * document or entity comes from a parse operation and is unchanged since it
 * was created). The serialized output for these node types is either as a
 * XML document or an External XML Entity, respectively, and is acceptable
 * input for an XML parser. For all other types of nodes the serialized form
 * is implementation dependent.
 * <p>Within a <code>Document</code>, <code>DocumentFragment</code>, or
 * <code>Entity</code> being serialized, <code>Nodes</code> are processed as
 * follows
 * <ul>
 * <li> <code>Document</code> nodes are written, including the XML
 * declaration (unless the parameter "xml-declaration" is set to
 * <code>false</code>) and a DTD subset, if one exists in the DOM. Writing a
 * <code>Document</code> node serializes the entire document.
 * </li>
 * <li>
 * <code>Entity</code> nodes, when written directly by
 * <code>LSSerializer.write</code>, outputs the entity expansion but no
 * namespace fixup is done. The resulting output will be valid as an
 * external entity.
 * </li>
 * <li> If the parameter "<a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#parameter-entities'>
 * entities</a>" is set to <code>true</code>, <code>EntityReference</code> nodes are
 * serialized as an entity reference of the form "
 * <code>&amp;entityName;</code>" in the output. Child nodes (the expansion)
 * of the entity reference are ignored. If the parameter "<a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#parameter-entities'>
 * entities</a>" is set to <code>false</code>, only the children of the entity reference
 * are serialized. <code>EntityReference</code> nodes with no children (no
 * corresponding <code>Entity</code> node or the corresponding
 * <code>Entity</code> nodes have no children) are always serialized.
 * </li>
 * <li>
 * <code>CDATAsections</code> containing content characters that cannot be
 * represented in the specified output encoding are handled according to the
 * "<a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#parameter-split-cdata-sections'>
 * split-cdata-sections</a>" parameter.  If the parameter is set to <code>true</code>,
 * <code>CDATAsections</code> are split, and the unrepresentable characters
 * are serialized as numeric character references in ordinary content. The
 * exact position and number of splits is not specified.  If the parameter
 * is set to <code>false</code>, unrepresentable characters in a
 * <code>CDATAsection</code> are reported as
 * <code>"wf-invalid-character"</code> errors if the parameter "<a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#parameter-well-formed'>
 * well-formed</a>" is set to <code>true</code>. The error is not recoverable - there is no
 * mechanism for supplying alternative characters and continuing with the
 * serialization.
 * </li>
 * <li> <code>DocumentFragment</code> nodes are serialized by
 * serializing the children of the document fragment in the order they
 * appear in the document fragment.
 * </li>
 * <li> All other node types (Element, Text,
 * etc.) are serialized to their corresponding XML source form.
 * </li>
 * </ul>
 * <p ><b>Note:</b>  The serialization of a <code>Node</code> does not always
 * generate a well-formed XML document, i.e. a <code>LSParser</code> might
 * throw fatal errors when parsing the resulting serialization.
 * <p> Within the character data of a document (outside of markup), any
 * characters that cannot be represented directly are replaced with
 * character references. Occurrences of '&lt;' and '&amp;' are replaced by
 * the predefined entities &amp;lt; and &amp;amp;. The other predefined
 * entities (&amp;gt;, &amp;apos;, and &amp;quot;) might not be used, except
 * where needed (e.g. using &amp;gt; in cases such as ']]&gt;'). Any
 * characters that cannot be represented directly in the output character
 * encoding are serialized as numeric character references (and since
 * character encoding standards commonly use hexadecimal representations of
 * characters, using the hexadecimal representation when serializing
 * character references is encouraged).
 * <p> To allow attribute values to contain both single and double quotes, the
 * apostrophe or single-quote character (') may be represented as
 * "&amp;apos;", and the double-quote character (")  as "&amp;quot;". New
 * line characters and other characters that cannot be represented directly
 * in attribute values in the output character encoding are serialized as a
 * numeric character reference.
 * <p> Within markup, but outside of attributes, any occurrence of a character
 * that cannot be represented in the output character encoding is reported
 * as a <code>DOMError</code> fatal error. An example would be serializing
 * the element &lt;LaCa\u00f1ada/&gt; with <code>encoding="us-ascii"</code>.
 * This will result with a generation of a <code>DOMError</code>
 * "wf-invalid-character-in-node-name" (as proposed in "<a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#parameter-well-formed'>
 * well-formed</a>").
 * <p> When requested by setting the parameter "<a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#parameter-normalize-characters'>
 * normalize-characters</a>" on <code>LSSerializer</code> to true, character normalization is
 * performed according to the definition of <a href='http://www.w3.org/TR/2004/REC-xml11-20040204/#dt-fullnorm'>fully
 * normalized</a> characters included in appendix E of [<a href='http://www.w3.org/TR/2004/REC-xml11-20040204/'>XML 1.1</a>] on all
 * data to be serialized, both markup and character data. The character
 * normalization process affects only the data as it is being written; it
 * does not alter the DOM's view of the document after serialization has
 * completed.
 * <p> Implementations are required to support the encodings "UTF-8",
 * "UTF-16", "UTF-16BE", and "UTF-16LE" to guarantee that data is
 * serializable in all encodings that are required to be supported by all
 * XML parsers. When the encoding is UTF-8, whether or not a byte order mark
 * is serialized, or if the output is big-endian or little-endian, is
 * implementation dependent. When the encoding is UTF-16, whether or not the
 * output is big-endian or little-endian is implementation dependent, but a
 * Byte Order Mark must be generated for non-character outputs, such as
 * <code>LSOutput.byteStream</code> or <code>LSOutput.systemId</code>. If
 * the Byte Order Mark is not generated, a "byte-order-mark-needed" warning
 * is reported. When the encoding is UTF-16LE or UTF-16BE, the output is
 * big-endian (UTF-16BE) or little-endian (UTF-16LE) and the Byte Order Mark
 * is not be generated. In all cases, the encoding declaration, if
 * generated, will correspond to the encoding used during the serialization
 * (e.g. <code>encoding="UTF-16"</code> will appear if UTF-16 was
 * requested).
 * <p> Namespaces are fixed up during serialization, the serialization process
 * will verify that namespace declarations, namespace prefixes and the
 * namespace URI associated with elements and attributes are consistent. If
 * inconsistencies are found, the serialized form of the document will be
 * altered to remove them. The method used for doing the namespace fixup
 * while serializing a document is the algorithm defined in Appendix B.1,
 * "Namespace normalization", of [<a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>DOM Level 3 Core</a>]
 * .
 * <p> While serializing a document, the parameter "discard-default-content"
 * controls whether or not non-specified data is serialized.
 * <p> While serializing, errors and warnings are reported to the application
 * through the error handler (<code>LSSerializer.domConfig</code>'s "<a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#parameter-error-handler'>
 * error-handler</a>" parameter). This specification does in no way try to define all possible
 * errors and warnings that can occur while serializing a DOM node, but some
 * common error and warning cases are defined. The types (
 * <code>DOMError.type</code>) of errors and warnings defined by this
 * specification are:
 * <dl>
 * <dt><code>"no-output-specified" [fatal]</code></dt>
 * <dd> Raised when
 * writing to a <code>LSOutput</code> if no output is specified in the
 * <code>LSOutput</code>. </dd>
 * <dt>
 * <code>"unbound-prefix-in-entity-reference" [fatal]</code> </dt>
 * <dd> Raised if the
 * configuration parameter "<a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#parameter-namespaces'>
 * namespaces</a>" is set to <code>true</code> and an entity whose replacement text
 * contains unbound namespace prefixes is referenced in a location where
 * there are no bindings for the namespace prefixes. </dd>
 * <dt>
 * <code>"unsupported-encoding" [fatal]</code></dt>
 * <dd> Raised if an unsupported
 * encoding is encountered. </dd>
 * </dl>
 * <p> In addition to raising the defined errors and warnings, implementations
 * are expected to raise implementation specific errors and warnings for any
 * other error and warning cases such as IO errors (file not found,
 * permission denied,...) and so on.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-LS-20040407'>Document Object Model (DOM) Level 3 Load
and Save Specification</a>.
 * <p>
 * <code> LSSerializer </code>提供了一个用于将DOM文档序列化(写入)为XML的API XML数据被写入字符串或输出流在序列化期间进行的任何更改或修正只影响序列化数据<代码>文档
 * </code>对象及其子对象不会被序列化操作改变<p>在XML数据序列化期间,命名空间修复按照[<a href ='http：// wwww3org / TR / 2004 / REC-DOM-Level-3-Core-20040407'>
 *  DOM Level 3 Core </a>],附录B [<a href ='http：// wwww3org / TR / 2000 / REC-DOM-Level-2-Core -20001113'>
 *  DOM Level 2 Core </a>]允许空字符串作为真实命名空间URI如果<code> Node </code>的<code> namespaceURI </code>是空字符串,那么序列化将
 * 把它们视为<code> null </code>,如果任何<p> <code> LSSerializer </code>接受用于序列化的任何节点类型对于类型<code> Document </code>
 * 或<code> Entity </code>的节点,将在可能时创建格式良好的XML文档或实体来自解析操作,并且自创建以来保持不变)这些节点类型的序列化输出分别作为XML文档或外部XML实体,并且是XML
 * 解析器可接受的输入对于所有其他类型节点的序列化形式是依赖于实现的<p>在<code>文档</code>,<code> DocumentFragment </code>或<code>实体</code>序列
 * 化之前,<code> Nodes </code>。
 * <ul>
 * <li>编写</code>文档</code>节点,包括XML声明(除非参数"xml-declaration"设置为<code> false </code>)和DTD子集DOM编写一个<code> Doc
 * ument </code>节点序列化整个文档。
 * </li>
 * <li>
 *  <code>实体</code>节点直接由<code> LSserializerwrite </code>写入时,会输出实体扩展,但不会进行命名空间修复。生成的输出将作为外部实体有效
 * </li>
 * <li>如果参数"<a href='http://wwww3org/TR/DOM-Level-3-Core/corehtml#parameter-entities'>实体</a>"设置为<code> t
 * rue </代码>,<code> EntityReference </code>节点被序列化为输出的子节点(扩展)中的形式为"<code>&amp; entityName; </code>"的实体引用。
 * 参数"<a href='http://wwww3org/TR/DOM-Level-3-Core/corehtml#parameter-entities'>实体</a>"设置为<code> false </code>
 * ,只有实体引用的子节点没有子节点(没有对应的<code> Entity </code>节点或相应的<code> Entity </code>节点没有子节点)的序列化的<code> EntityRefer
 * ence </code>序列化。
 * </li>
 * <li>
 * 根据"<a href ='http：// wwww3org / TR / DOM-Level-3-Core / corehtml#"参数处理包含无法在指定输出编码中表示的内容字符的<code> CDAT
 * Asections </code> -split-cdata-sections'> split-cdata-sections </a>"参数如果参数设置为<code> true </code>,则会拆分
 * <code> CDATAsections </code>,并且不可表示的字符序列化为普通内容中的数字字符引用未指定拆分的确切位置和数量如果参数设置为<code> false </code>,则<code>
 *  CDATAsection </code>中的无法表示的字符将报告为<code >如果参数"<a href ='http：// wwww3org / TR / DOM-Level-3-Core / core">
 * "wf-invalid-character"</html#parameter-well-formed'>良好的</a>"设置为<code> true </code>错误不可恢复 - 没有提供备用字符和继
 * 续序列化的机制。
 * </li>
 * <li> <code> DocumentFragment </code>节点通过按文档片段在文档片段中显示的顺序序列化来序列化
 * </li>
 *  <li>所有其他节点类型(元素,文本等)都会序列化为其对应的XML源格式
 * </li>
 * </ul>
 * <p> <b>注意：</b> <code> Node </code>的序列化并不总是生成格式良好的XML文档,即<code> LSParser </code>解析所得到的序列化<p>在文档的字符数据内(
 * 在标记外部),任何不能直接表示的字符被替换为字符引用出现'&lt;和'&amp;'被预定义实体&amp;和&amp; amp;可以不使用其他预定义实体(&amp; gt;,&amp;'和&amp;;),
 * 除非在需要的地方(例如在诸如"]]>的情况下使用&amp;不能在输出字符编码中直接表示的任何字符都会被序列化为数字字符引用(并且由于字符编码标准通常使用十六进制表示形式,在鼓励序列化字符引用时使用十六进
 * 制表示)<p>要允许属性值包含单引号和双引号,撇号或单引号字符(')可以表示为"&amp;''",双引号字符(")作为"&amp; quot;"新行字符和其他字符不能在输出字符编码的属性值中直接表示的字
 * 符串被序列化为数字字符引用<p>在标记内部,但在属性外,任何不能在输出字符编码中表示的字符的出现都会报告为<code> DOMError </code>致命错误示例将序列化元素&lt; LaCa \\ 
 * u00f1ada /&gt;与<code> encoding ="us-ascii"</code>这将导致生成一个<code> DOMError </code>"wf-invalid-character
 * -in-node-name" a href ='http：// wwww3org / TR / DOM-Level-3-Core / corehtml#parameter-well-formed'>形式
 * 良好</a>")<p>当设置参数"<a <code> LSSerializer </code>上的"href ='http：// wwww3org / TR / DOM-Level-3-Core / c
 * orehtml#parameter-normalize-characters'> normalize-characters </a>"为true,字符规范化根据<a href ='http：// wwww3的定义执行包括在[<a href ='http：// wwww3org / TR / 2004 / REC-xml11-html']附录E中的"完全归一化"字符的"org / TR / 2004 / REC-xml11-20040204 / 20040204 /'>
 *  XML 11 </a>]对所有要序列化的数据,包括标记和字符数据进行字符归一化过程仅影响正在写入的数据;它不会在序列化完成后更改文档的DOM视图<p>需要实现来支持编码"UTF-8","UTF-16"
 * ,"UTF-16BE"和"UTF-16LE"该数据可以在所有XML解析器需要支持的所有编码中串行化当编码为UTF-8时,无论字节顺序标记是否序列化,或者如果输出是大端或小端,则是实现依赖<p>命名空间在
 * 序列化期间固定,序列化过程将验证命名空间声明,命名空间前缀和与元素和属性关联的命名空间URI是否一致如果发现不一致,则将更改文档的序列化形式以删除它们。
 * 用于在序列化文档时进行命名空间修复的方法是在[<a href ='http：// wwww3org / TR / 2004 / REC-DOM-Level-3-Core]的附录B1"命名空间规范化"中定义的算法-20040407'>
 *  DOM Level 3 Core </a>] <p>在序列化文档时,参数"discard-default-content"控制是否序列化非指定数据<p>序列化时,错误和警告通过错误处理程序(<code>
 *  LSSerializer)向应用程序报告错误domConfig </code>'""<a href='http://wwww3org/TR/DOM-Level-3-Core/corehtml#parameter-error-handler'>
 *  error-handler </a>"参数)本规范不以任何方式尝试定义在序列化DOM节点时可能发生的所有可能的错误和警告,但定义了一些常见的错误和警告情况本规范定义的错误和警告的类型(<code> D
 * OMErrortype </code>)是：。
 * <dl>
 * <code> <code>"no-output-specified"[fatal] </code> </dt> <dd>当写入<code> LSOutput </code> LSOutput </code>
 *  </dd>。
 * <dt>
 *  <code>"unbound-prefix-in-entity-reference"[fatal] </code> </dt> <dd>如果配置参数"<a href ='http：// wwww3org / TR / DOM- -3-Core / corehtml#parameter-namespaces'>
 *  namespaces </a>"设置为<code> true </code>,并且替换文本包含未绑定命名空间前缀的实体在没有绑定的位置引用命名空间前缀</dd>。
 * <dt>
 *  <code>"unsupported-encoding"[fatal] </code> </dt> <dd>遇到不支持的编码时引发</dd>
 * </dl>
 * <p>除了提高定义的错误和警告之外,实现还会针对任何其他错误和警告情况(例如IO错误(找不到文件,权限被拒绝)等)提高实现特定的错误和警告。
 * 还有<a href='http://wwww3org/TR/2004/REC-DOM-Level-3-LS-20040407'>文档对象模型(DOM)3级加载和保存规范</a>。
 * 
 */
public interface LSSerializer {
    /**
     *  The <code>DOMConfiguration</code> object used by the
     * <code>LSSerializer</code> when serializing a DOM node.
     * <br> In addition to the parameters recognized by the <a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#DOMConfiguration'>
     * DOMConfiguration</a> interface defined in [<a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>DOM Level 3 Core</a>]
     * , the <code>DOMConfiguration</code> objects for
     * <code>LSSerializer</code> adds, or modifies, the following
     * parameters:
     * <dl>
     * <dt><code>"canonical-form"</code></dt>
     * <dd>
     * <dl>
     * <dt><code>true</code></dt>
     * <dd>[<em>optional</em>] Writes the document according to the rules specified in [<a href='http://www.w3.org/TR/2001/REC-xml-c14n-20010315'>Canonical XML</a>].
     * In addition to the behavior described in "<a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#parameter-canonical-form'>
     * canonical-form</a>" [<a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>DOM Level 3 Core</a>]
     * , setting this parameter to <code>true</code> will set the parameters
     * "format-pretty-print", "discard-default-content", and "xml-declaration
     * ", to <code>false</code>. Setting one of those parameters to
     * <code>true</code> will set this parameter to <code>false</code>.
     * Serializing an XML 1.1 document when "canonical-form" is
     * <code>true</code> will generate a fatal error. </dd>
     * <dt><code>false</code></dt>
     * <dd>[<em>required</em>] (<em>default</em>) Do not canonicalize the output. </dd>
     * </dl></dd>
     * <dt><code>"discard-default-content"</code></dt>
     * <dd>
     * <dl>
     * <dt>
     * <code>true</code></dt>
     * <dd>[<em>required</em>] (<em>default</em>) Use the <code>Attr.specified</code> attribute to decide what attributes
     * should be discarded. Note that some implementations might use
     * whatever information available to the implementation (i.e. XML
     * schema, DTD, the <code>Attr.specified</code> attribute, and so on) to
     * determine what attributes and content to discard if this parameter is
     * set to <code>true</code>. </dd>
     * <dt><code>false</code></dt>
     * <dd>[<em>required</em>]Keep all attributes and all content.</dd>
     * </dl></dd>
     * <dt><code>"format-pretty-print"</code></dt>
     * <dd>
     * <dl>
     * <dt>
     * <code>true</code></dt>
     * <dd>[<em>optional</em>] Formatting the output by adding whitespace to produce a pretty-printed,
     * indented, human-readable form. The exact form of the transformations
     * is not specified by this specification. Pretty-printing changes the
     * content of the document and may affect the validity of the document,
     * validating implementations should preserve validity. </dd>
     * <dt>
     * <code>false</code></dt>
     * <dd>[<em>required</em>] (<em>default</em>) Don't pretty-print the result. </dd>
     * </dl></dd>
     * <dt>
     * <code>"ignore-unknown-character-denormalizations"</code> </dt>
     * <dd>
     * <dl>
     * <dt>
     * <code>true</code></dt>
     * <dd>[<em>required</em>] (<em>default</em>) If, while verifying full normalization when [<a href='http://www.w3.org/TR/2004/REC-xml11-20040204/'>XML 1.1</a>] is
     * supported, a character is encountered for which the normalization
     * properties cannot be determined, then raise a
     * <code>"unknown-character-denormalization"</code> warning (instead of
     * raising an error, if this parameter is not set) and ignore any
     * possible denormalizations caused by these characters. </dd>
     * <dt>
     * <code>false</code></dt>
     * <dd>[<em>optional</em>] Report a fatal error if a character is encountered for which the
     * processor cannot determine the normalization properties. </dd>
     * </dl></dd>
     * <dt>
     * <code>"normalize-characters"</code></dt>
     * <dd> This parameter is equivalent to
     * the one defined by <code>DOMConfiguration</code> in [<a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>DOM Level 3 Core</a>]
     * . Unlike in the Core, the default value for this parameter is
     * <code>true</code>. While DOM implementations are not required to
     * support <a href='http://www.w3.org/TR/2004/REC-xml11-20040204/#dt-fullnorm'>fully
     * normalizing</a> the characters in the document according to appendix E of [<a href='http://www.w3.org/TR/2004/REC-xml11-20040204/'>XML 1.1</a>], this
     * parameter must be activated by default if supported. </dd>
     * <dt>
     * <code>"xml-declaration"</code></dt>
     * <dd>
     * <dl>
     * <dt><code>true</code></dt>
     * <dd>[<em>required</em>] (<em>default</em>) If a <code>Document</code>, <code>Element</code>, or <code>Entity</code>
     *  node is serialized, the XML declaration, or text declaration, should
     * be included. The version (<code>Document.xmlVersion</code> if the
     * document is a Level 3 document and the version is non-null, otherwise
     * use the value "1.0"), and the output encoding (see
     * <code>LSSerializer.write</code> for details on how to find the output
     * encoding) are specified in the serialized XML declaration. </dd>
     * <dt>
     * <code>false</code></dt>
     * <dd>[<em>required</em>] Do not serialize the XML and text declarations. Report a
     * <code>"xml-declaration-needed"</code> warning if this will cause
     * problems (i.e. the serialized data is of an XML version other than [<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>], or an
     * encoding would be needed to be able to re-parse the serialized data). </dd>
     * </dl></dd>
     * </dl>
     * <p>
     * 序列化DOM节点时,<code> LSSerializer </code>使用的<code> DOMConfiguration </code>对象<br>除了<a href ='http：// wwww3org / TR /在[<a href='http://wwww3org/TR/2004/REC-DOM-Level-3-Core-20040407'>
     * ]中定义的DOM-Level-3-Core / corehtml#DOMConfiguration'> DOMConfiguration </a>界面DOM Level 3 Core </a>],<code>
     *  LSSerializer </code>的<code> DOMConfiguration </code>对象添加或修改以下参数：。
     * <dl>
     *  <dt> <code>"canonical-form"</code> </dt>
     * <dd>
     * <dl>
     * <dt> <code> true </code> </dt> <dd> [<em>可选</em>]根据[<a href ='http：// wwww3org / TR / 2001 / REC-xml-c14n-20010315'>
     * 规范XML </a>]除了"<a href ='http：// wwww3org / TR / DOM-Level-3-Core / corehtml# parameter-canonical-form'>
     *  canonical-form </a>"[<a href='http://wwww3org/TR/2004/REC-DOM-Level-3-Core-20040407'> DOM 3级核心< a>],
     * 将此参数设置为<code> true </code>会将参数"format-pretty-print","discard-default-content"和"xml- declaration"设置为<code>
     *  false <代码>将这些参数之一设置为<code> true </code>会将此参数设置为<code> false </code>当"canonical-form"为<code> true </code>
     * 时,序列化XML 11文档产生致命错误</dd> </em>(<em> </em>)</dd>不规范输出</dd> > </dl> </dd> <dt> <code>"discard-default-c
     * ontent"</code>。
     * <dd>
     * <dl>
     * <dt>
     * <code> true </code> </em> <em> </em>使用<code> Attrspecified </code>属性来决定什么属性应该被丢弃。
     * 注意,一些实现可以使用实现可用的任何信息(即,XML模式,DTD,<code> Attrspecified </code>属性等)来确定如果设置了该参数,丢弃哪些属性和内容到<code> true </code>
     *  </em> </em> </em> </dd>保留所有属性和所有内容</dd > </dl> </dd> <dt> <code>"format-pretty-print"</code>。
     * <code> true </code> </em> <em> </em>使用<code> Attrspecified </code>属性来决定什么属性应该被丢弃。
     * <dd>
     * <dl>
     * <dt>
     * <code> true </code> </dt> </em>通过添加空白格式化输出格式,以生成漂亮打印,缩进,人类可读的形式转换的确切形式不受本规范指定美丽打印更改文档的内容,并可能影响文档的有效性,
     * 验证实施应保留有效性</dd>。
     * <dt>
     *  <code> false </code> </dt> </em>(<em> </em> > </dd>
     * <dt>
     *  <code>"ignore-unknown-character-denormalizations"</code> </dt>
     * <dd>
     * <dl>
     * <dt>
     * <code> true </code> </em> <em> </em>(<em> // wwww3org / TR / 2004 / REC-xml11-20040204 /'> XML 11 </a>
     * ],遇到无法确定规范化属性的字符,然后提出<code>"unknown-character- denormalization"</code>警告(而不是提出错误,如果此参数未设置),并忽略由这些字符引起
     * 的任何可能的非规范化</dd>。
     * <dt>
     *  <code> false </code> </dt> </em>如果遇到处理器无法确定归一化属性的字符,则报告致命错误</dd> </> </dd>
     * <dt>
     * <code>"normalize-characters"</code> </dt> <dd>此参数等同于由<code> DOMConfiguration </code>在[<a href ='http：// wwww3org / TR / 2004 / REC-DOM-Level-3-Core-20040407'>
     *  DOM Level 3 Core </a>]与Core不同,此参数的默认值为<code> true </code>需要根据[<a href]附录E支持<a href='http://wwww3org/TR/2004/REC-xml11-20040204/#dt-fullnorm'>
     * 完全规范化</a>文档中的字符='http：// wwww3org / TR / 2004 / REC-xml11-20040204 /'> XML 11 </a>],如果支持此参数,必须默认激活</dd>
     * 。
     * <dt>
     *  <code>"xml-declaration"</code> </dt>
     * <dd>
     * <dl>
     * <dt> <code> </em> <em> </em>如果<code>文档</code>代码>元素</code>或<code>实体</code>节点序列化,应包括XML声明或文本声明版本(<code>
     *  DocumentxmlVersion </code>如果文档是Level 3文档和版本是非空的,否则使用值"10"),并且输出编码(参见<code> LSSerializerwrite </code>
     * 有关如何查找输出编码的详细信息)在序列化XML声明中指定< / dd>。
     * <dt>
     */
    public DOMConfiguration getDomConfig();

    /**
     *  The end-of-line sequence of characters to be used in the XML being
     * written out. Any string is supported, but XML treats only a certain
     * set of characters sequence as end-of-line (See section 2.11,
     * "End-of-Line Handling" in [<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>], if the
     * serialized content is XML 1.0 or section 2.11, "End-of-Line Handling"
     * in [<a href='http://www.w3.org/TR/2004/REC-xml11-20040204/'>XML 1.1</a>], if the
     * serialized content is XML 1.1). Using other character sequences than
     * the recommended ones can result in a document that is either not
     * serializable or not well-formed).
     * <br> On retrieval, the default value of this attribute is the
     * implementation specific default end-of-line sequence. DOM
     * implementations should choose the default to match the usual
     * convention for text files in the environment being used.
     * Implementations must choose a default sequence that matches one of
     * those allowed by XML 1.0 or XML 1.1, depending on the serialized
     * content. Setting this attribute to <code>null</code> will reset its
     * value to the default value.
     * <br>
     * <p>
     * <code> false </code> </dt> </em>不要序列化XML和文本声明报告<code>"xml-declaration-needed"如果这会导致问题(即序列化数据是除[<a href='http://wwww3org/TR/2004/REC-xml-20040204'>
     *  XML 10 </a>]之外的XML版本)或编码将能够重新解析序列化数据)</dd> </dl> </dd>。
     * </dl>
     */
    public String getNewLine();
    /**
     *  The end-of-line sequence of characters to be used in the XML being
     * written out. Any string is supported, but XML treats only a certain
     * set of characters sequence as end-of-line (See section 2.11,
     * "End-of-Line Handling" in [<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>], if the
     * serialized content is XML 1.0 or section 2.11, "End-of-Line Handling"
     * in [<a href='http://www.w3.org/TR/2004/REC-xml11-20040204/'>XML 1.1</a>], if the
     * serialized content is XML 1.1). Using other character sequences than
     * the recommended ones can result in a document that is either not
     * serializable or not well-formed).
     * <br> On retrieval, the default value of this attribute is the
     * implementation specific default end-of-line sequence. DOM
     * implementations should choose the default to match the usual
     * convention for text files in the environment being used.
     * Implementations must choose a default sequence that matches one of
     * those allowed by XML 1.0 or XML 1.1, depending on the serialized
     * content. Setting this attribute to <code>null</code> will reset its
     * value to the default value.
     * <br>
     * <p>
     * 在要写入的XML中使用的字符的行尾序列支持任何字符串,但XML仅将某一组字符序列作为行尾(请参见第211节"行尾(end-of-line)在[<a href='http://wwww3org/TR/2004/REC-xml-20040204'>
     *  XML 10 </a>]中,如果序列化内容是XML 10或第211节"处理",则"如果序列化的内容是XML,则在[<a href='http://wwww3org/TR/2004/REC-xml11-20040204/'>
     *  XML 11 </a>]中使用"行处理")11)使用除推荐的文档可能会导致文档不可序列化或格式不正确)<br>在检索时,此属性的默认值是实现特定的默认行尾序列DOM实现应该选择默认值来匹配正在使用的环
     * 境中文本文件的通常约定实现必须选择与XML 10或XML 11允许的默认序列匹配的默认序列,具体取决于序列化的内容将此属性设置为<code> null </code>将其值重置为默认值。
     * <br>
     */
    public void setNewLine(String newLine);

    /**
     *  When the application provides a filter, the serializer will call out
     * to the filter before serializing each Node. The filter implementation
     * can choose to remove the node from the stream or to terminate the
     * serialization early.
     * <br> The filter is invoked after the operations requested by the
     * <code>DOMConfiguration</code> parameters have been applied. For
     * example, CDATA sections won't be passed to the filter if "<a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#parameter-cdata-sections'>
     * cdata-sections</a>" is set to <code>false</code>.
     * <p>
     * 在要写入的XML中使用的字符的行尾序列支持任何字符串,但XML仅将某一组字符序列作为行尾(请参见第211节"行尾(end-of-line)在[<a href='http://wwww3org/TR/2004/REC-xml-20040204'>
     *  XML 10 </a>]中,如果序列化内容是XML 10或第211节"处理",则"如果序列化的内容是XML,则在[<a href='http://wwww3org/TR/2004/REC-xml11-20040204/'>
     *  XML 11 </a>]中使用"行处理")11)使用除推荐的文档可能会导致文档不可序列化或格式不正确)<br>在检索时,此属性的默认值是实现特定的默认行尾序列DOM实现应该选择默认值来匹配正在使用的环
     * 境中文本文件的通常约定实现必须选择与XML 10或XML 11允许的默认序列匹配的默认序列,具体取决于序列化的内容将此属性设置为<code> null </code>将其值重置为默认值。
     * <br>
     */
    public LSSerializerFilter getFilter();
    /**
     *  When the application provides a filter, the serializer will call out
     * to the filter before serializing each Node. The filter implementation
     * can choose to remove the node from the stream or to terminate the
     * serialization early.
     * <br> The filter is invoked after the operations requested by the
     * <code>DOMConfiguration</code> parameters have been applied. For
     * example, CDATA sections won't be passed to the filter if "<a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#parameter-cdata-sections'>
     * cdata-sections</a>" is set to <code>false</code>.
     * <p>
     * 当应用程序提供一个过滤器时,序列化器将在序列化每个节点之前调用过滤器。过滤器实现可以选择从流中移除该节点,或者提前终止序列化。
     * <br>在过滤器被请求的操作<code> DOMConfiguration </code>参数已被应用例如,如果"<a href ='http：// wwww3org / TR / DOM-Level-3-Core / corehtml# parameter-cdata-sections'>
     *  cdata-sections </a>"设置为<code> false </code>。
     * 当应用程序提供一个过滤器时,序列化器将在序列化每个节点之前调用过滤器。过滤器实现可以选择从流中移除该节点,或者提前终止序列化。
     * 
     */
    public void setFilter(LSSerializerFilter filter);

    /**
     *  Serialize the specified node as described above in the general
     * description of the <code>LSSerializer</code> interface. The output is
     * written to the supplied <code>LSOutput</code>.
     * <br> When writing to a <code>LSOutput</code>, the encoding is found by
     * looking at the encoding information that is reachable through the
     * <code>LSOutput</code> and the item to be written (or its owner
     * document) in this order:
     * <ol>
     * <li> <code>LSOutput.encoding</code>,
     * </li>
     * <li>
     * <code>Document.inputEncoding</code>,
     * </li>
     * <li>
     * <code>Document.xmlEncoding</code>.
     * </li>
     * </ol>
     * <br> If no encoding is reachable through the above properties, a
     * default encoding of "UTF-8" will be used. If the specified encoding
     * is not supported an "unsupported-encoding" fatal error is raised.
     * <br> If no output is specified in the <code>LSOutput</code>, a
     * "no-output-specified" fatal error is raised.
     * <br> The implementation is responsible of associating the appropriate
     * media type with the serialized data.
     * <br> When writing to a HTTP URI, a HTTP PUT is performed. When writing
     * to other types of URIs, the mechanism for writing the data to the URI
     * is implementation dependent.
     * <p>
     * 当应用程序提供一个过滤器时,序列化器将在序列化每个节点之前调用过滤器。过滤器实现可以选择从流中移除该节点,或者提前终止序列化。
     * <br>在过滤器被请求的操作<code> DOMConfiguration </code>参数已被应用例如,如果"<a href ='http：// wwww3org / TR / DOM-Level-3-Core / corehtml# parameter-cdata-sections'>
     *  cdata-sections </a>"设置为<code> false </code>。
     * 当应用程序提供一个过滤器时,序列化器将在序列化每个节点之前调用过滤器。过滤器实现可以选择从流中移除该节点,或者提前终止序列化。
     * 
     * 
     * @param nodeArg  The node to serialize.
     * @param destination The destination for the serialized DOM.
     * @return  Returns <code>true</code> if <code>node</code> was
     *   successfully serialized. Return <code>false</code> in case the
     *   normal processing stopped but the implementation kept serializing
     *   the document; the result of the serialization being implementation
     *   dependent then.
     * @exception LSException
     *    SERIALIZE_ERR: Raised if the <code>LSSerializer</code> was unable to
     *   serialize the node. DOM applications should attach a
     *   <code>DOMErrorHandler</code> using the parameter "<a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#parameter-error-handler'>
     *   error-handler</a>" if they wish to get details on the error.
     */
    public boolean write(Node nodeArg,
                         LSOutput destination)
                         throws LSException;

    /**
     *  A convenience method that acts as if <code>LSSerializer.write</code>
     * was called with a <code>LSOutput</code> with no encoding specified
     * and <code>LSOutput.systemId</code> set to the <code>uri</code>
     * argument.
     * <p>
     * 按照<code> LSSerializer </code>接口的一般描述中所述,将输出写入提供的<code> LSOutput </code> <br>当写入<code> LSOutput </code>
     *  code>,通过查看通过<code> LSOutput </code>和要写入的项目(或其所有者文档)可按照以下顺序到达的编码信息找到编码：。
     * <ol>
     *  <li> <code> LSOutputencoding </code>,
     * </li>
     * <li>
     *  <code> DocumentinputEncoding </code>,
     * </li>
     * <li>
     *  <code> DocumentxmlEncoding </code>
     * </li>
     * </ol>
     * 
     * @param nodeArg  The node to serialize.
     * @param uri The URI to write to.
     * @return  Returns <code>true</code> if <code>node</code> was
     *   successfully serialized. Return <code>false</code> in case the
     *   normal processing stopped but the implementation kept serializing
     *   the document; the result of the serialization being implementation
     *   dependent then.
     * @exception LSException
     *    SERIALIZE_ERR: Raised if the <code>LSSerializer</code> was unable to
     *   serialize the node. DOM applications should attach a
     *   <code>DOMErrorHandler</code> using the parameter "<a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#parameter-error-handler'>
     *   error-handler</a>" if they wish to get details on the error.
     */
    public boolean writeToURI(Node nodeArg,
                              String uri)
                              throws LSException;

    /**
     *  Serialize the specified node as described above in the general
     * description of the <code>LSSerializer</code> interface. The output is
     * written to a <code>DOMString</code> that is returned to the caller.
     * The encoding used is the encoding of the <code>DOMString</code> type,
     * i.e. UTF-16. Note that no Byte Order Mark is generated in a
     * <code>DOMString</code> object.
     * <p>
     * <br>如果通过上述属性无法访问编码,则将使用"UTF-8"的默认编码如果不支持指定的编码,则会出现"不支持的编码"致命错误<br>如果未指定输出在<code> LSOutput </code>中,会出
     * 现"没有输出指定"的致命错误<br>实现负责将相应的媒体类型与序列化数据关联<br>写入HTTP URI时,执行HTTP PUT当写入其他类型的URI时,将数据写入URI的机制是与实现相关的。
     * 
     * 
     * @param nodeArg  The node to serialize.
     * @return  Returns the serialized data.
     * @exception DOMException
     *    DOMSTRING_SIZE_ERR: Raised if the resulting string is too long to
     *   fit in a <code>DOMString</code>.
     * @exception LSException
     *    SERIALIZE_ERR: Raised if the <code>LSSerializer</code> was unable to
     *   serialize the node. DOM applications should attach a
     *   <code>DOMErrorHandler</code> using the parameter "<a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#parameter-error-handler'>
     *   error-handler</a>" if they wish to get details on the error.
     */
    public String writeToString(Node nodeArg)
                                throws DOMException, LSException;

}
