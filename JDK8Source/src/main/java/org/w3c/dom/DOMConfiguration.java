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

package org.w3c.dom;

/**
 *  The <code>DOMConfiguration</code> interface represents the configuration
 * of a document and maintains a table of recognized parameters. Using the
 * configuration, it is possible to change
 * <code>Document.normalizeDocument()</code> behavior, such as replacing the
 * <code>CDATASection</code> nodes with <code>Text</code> nodes or
 * specifying the type of the schema that must be used when the validation
 * of the <code>Document</code> is requested. <code>DOMConfiguration</code>
 * objects are also used in [<a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-LS-20040407'>DOM Level 3 Load and Save</a>]
 *  in the <code>DOMParser</code> and <code>DOMSerializer</code> interfaces.
 * <p> The parameter names used by the <code>DOMConfiguration</code> object
 * are defined throughout the DOM Level 3 specifications. Names are
 * case-insensitive. To avoid possible conflicts, as a convention, names
 * referring to parameters defined outside the DOM specification should be
 * made unique. Because parameters are exposed as properties in names
 * are recommended to follow the section 5.16 Identifiers of [Unicode] with the addition of the character '-' (HYPHEN-MINUS) but it is not
 * enforced by the DOM implementation. DOM Level 3 Core Implementations are
 * required to recognize all parameters defined in this specification. Some
 * parameter values may also be required to be supported by the
 * implementation. Refer to the definition of the parameter to know if a
 * value must be supported or not.
 * <p ><b>Note:</b>  Parameters are similar to features and properties used in
 * SAX2 [<a href='http://www.saxproject.org/'>SAX</a>].
 * <p> The following list of parameters defined in the DOM:
 * <dl>
 * <dt>
 * <code>"canonical-form"</code></dt>
 * <dd>
 * <dl>
 * <dt><code>true</code></dt>
 * <dd>[<em>optional</em>] Canonicalize the document according to the rules specified in [<a href='http://www.w3.org/TR/2001/REC-xml-c14n-20010315'>Canonical XML</a>],
 * such as removing the <code>DocumentType</code> node (if any) from the
 * tree, or removing superfluous namespace declarations from each element.
 * Note that this is limited to what can be represented in the DOM; in
 * particular, there is no way to specify the order of the attributes in the
 * DOM. In addition,  Setting this parameter to <code>true</code> will also
 * set the state of the parameters listed below. Later changes to the state
 * of one of those parameters will revert "canonical-form" back to
 * <code>false</code>. Parameters set to <code>false</code>: "entities", "
 * normalize-characters", "cdata-sections". Parameters set to
 * <code>true</code>: "namespaces", "namespace-declarations", "well-formed",
 * "element-content-whitespace". Other parameters are not changed unless
 * explicitly specified in the description of the parameters.</dd>
 * <dt>
 * <code>false</code></dt>
 * <dd>[<em>required</em>] (<em>default</em>)Do not canonicalize the document.</dd>
 * </dl></dd>
 * <dt><code>"cdata-sections"</code></dt>
 * <dd>
 * <dl>
 * <dt>
 * <code>true</code></dt>
 * <dd>[<em>required</em>] (<em>default</em>)Keep <code>CDATASection</code> nodes in the document.</dd>
 * <dt><code>false</code></dt>
 * <dd>[<em>required</em>]Transform <code>CDATASection</code> nodes in the document into
 * <code>Text</code> nodes. The new <code>Text</code> node is then combined
 * with any adjacent <code>Text</code> node.</dd>
 * </dl></dd>
 * <dt>
 * <code>"check-character-normalization"</code></dt>
 * <dd>
 * <dl>
 * <dt><code>true</code></dt>
 * <dd>[<em>optional</em>] Check if the characters in the document are <a href='http://www.w3.org/TR/2004/REC-xml11-20040204/#dt-fullnorm'>fully
 * normalized</a>, as defined in appendix B of [<a href='http://www.w3.org/TR/2004/REC-xml11-20040204/'>XML 1.1</a>]. When a
 * sequence of characters is encountered that fails normalization checking,
 * an error with the <code>DOMError.type</code> equals to
 * "check-character-normalization-failure" is issued. </dd>
 * <dt><code>false</code></dt>
 * <dd>[<em>required</em>] (<em>default</em>)Do not check if characters are normalized.</dd>
 * </dl></dd>
 * <dt><code>"comments"</code></dt>
 * <dd>
 * <dl>
 * <dt>
 * <code>true</code></dt>
 * <dd>[<em>required</em>] (<em>default</em>)Keep <code>Comment</code> nodes in the document.</dd>
 * <dt><code>false</code></dt>
 * <dd>[<em>required</em>]Discard <code>Comment</code> nodes in the document.</dd>
 * </dl></dd>
 * <dt>
 * <code>"datatype-normalization"</code></dt>
 * <dd>
 * <dl>
 * <dt><code>true</code></dt>
 * <dd>[<em>optional</em>] Expose schema normalized values in the tree, such as <a href='http://www.w3.org/TR/2001/REC-xmlschema-1-20010502/#key-nv'>XML
 * Schema normalized values</a> in the case of XML Schema. Since this parameter requires to have schema
 * information, the "validate" parameter will also be set to
 * <code>true</code>. Having this parameter activated when "validate" is
 * <code>false</code> has no effect and no schema-normalization will happen.
 * <p ><b>Note:</b>  Since the document contains the result of the XML 1.0
 * processing, this parameter does not apply to attribute value
 * normalization as defined in section 3.3.3 of [<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>] and is only
 * meant for schema languages other than Document Type Definition (DTD). </dd>
 * <dt>
 * <code>false</code></dt>
 * <dd>[<em>required</em>] (<em>default</em>) Do not perform schema normalization on the tree. </dd>
 * </dl></dd>
 * <dt>
 * <code>"element-content-whitespace"</code></dt>
 * <dd>
 * <dl>
 * <dt><code>true</code></dt>
 * <dd>[<em>required</em>] (<em>default</em>)Keep all whitespaces in the document.</dd>
 * <dt><code>false</code></dt>
 * <dd>[<em>optional</em>] Discard all <code>Text</code> nodes that contain whitespaces in element
 * content, as described in <a href='http://www.w3.org/TR/2004/REC-xml-infoset-20040204#infoitem.character'>
 * [element content whitespace]</a>. The implementation is expected to use the attribute
 * <code>Text.isElementContentWhitespace</code> to determine if a
 * <code>Text</code> node should be discarded or not.</dd>
 * </dl></dd>
 * <dt><code>"entities"</code></dt>
 * <dd>
 * <dl>
 * <dt>
 * <code>true</code></dt>
 * <dd>[<em>required</em>] (<em>default</em>)Keep <code>EntityReference</code> nodes in the document.</dd>
 * <dt>
 * <code>false</code></dt>
 * <dd>[<em>required</em>] Remove all <code>EntityReference</code> nodes from the document,
 * putting the entity expansions directly in their place. <code>Text</code>
 * nodes are normalized, as defined in <code>Node.normalize</code>. Only <a href='http://www.w3.org/TR/2004/REC-xml-infoset-20040204/#infoitem.rse'>
 * unexpanded entity references</a> are kept in the document. </dd>
 * </dl>
 * <p ><b>Note:</b>  This parameter does not affect <code>Entity</code> nodes. </dd>
 * <dt>
 * <code>"error-handler"</code></dt>
 * <dd>[<em>required</em>] Contains a <code>DOMErrorHandler</code> object. If an error is
 * encountered in the document, the implementation will call back the
 * <code>DOMErrorHandler</code> registered using this parameter. The
 * implementation may provide a default <code>DOMErrorHandler</code> object.
 *  When called, <code>DOMError.relatedData</code> will contain the closest
 * node to where the error occurred. If the implementation is unable to
 * determine the node where the error occurs,
 * <code>DOMError.relatedData</code> will contain the <code>Document</code>
 * node. Mutations to the document from within an error handler will result
 * in implementation dependent behavior. </dd>
 * <dt><code>"infoset"</code></dt>
 * <dd>
 * <dl>
 * <dt>
 * <code>true</code></dt>
 * <dd>[<em>required</em>]Keep in the document the information defined in the XML Information Set [<a href='http://www.w3.org/TR/2004/REC-xml-infoset-20040204/'>XML Information Set</a>]
 * .This forces the following parameters to <code>false</code>: "
 * validate-if-schema", "entities", "datatype-normalization", "cdata-sections
 * ".This forces the following parameters to <code>true</code>: "
 * namespace-declarations", "well-formed", "element-content-whitespace", "
 * comments", "namespaces".Other parameters are not changed unless
 * explicitly specified in the description of the parameters. Note that
 * querying this parameter with <code>getParameter</code> returns
 * <code>true</code> only if the individual parameters specified above are
 * appropriately set.</dd>
 * <dt><code>false</code></dt>
 * <dd>Setting <code>infoset</code> to
 * <code>false</code> has no effect.</dd>
 * </dl></dd>
 * <dt><code>"namespaces"</code></dt>
 * <dd>
 * <dl>
 * <dt>
 * <code>true</code></dt>
 * <dd>[<em>required</em>] (<em>default</em>) Perform the namespace processing as defined in . </dd>
 * <dt><code>false</code></dt>
 * <dd>[<em>optional</em>] Do not perform the namespace processing. </dd>
 * </dl></dd>
 * <dt>
 * <code>"namespace-declarations"</code></dt>
 * <dd> This parameter has no effect if the
 * parameter "namespaces" is set to <code>false</code>.
 * <dl>
 * <dt><code>true</code></dt>
 * <dd>[<em>required</em>] (<em>default</em>) Include namespace declaration attributes, specified or defaulted from
 * the schema, in the document. See also the sections "Declaring Namespaces"
 * in [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML Namespaces</a>]
 *  and [<a href='http://www.w3.org/TR/2004/REC-xml-names11-20040204/'>XML Namespaces 1.1</a>]
 * .</dd>
 * <dt><code>false</code></dt>
 * <dd>[<em>required</em>]Discard all namespace declaration attributes. The namespace prefixes (
 * <code>Node.prefix</code>) are retained even if this parameter is set to
 * <code>false</code>.</dd>
 * </dl></dd>
 * <dt><code>"normalize-characters"</code></dt>
 * <dd>
 * <dl>
 * <dt><code>true</code></dt>
 * <dd>[<em>optional</em>] <a href='http://www.w3.org/TR/2004/REC-xml11-20040204/#dt-fullnorm'>Fully
 * normalized</a> the characters in the document as defined in appendix B of [<a href='http://www.w3.org/TR/2004/REC-xml11-20040204/'>XML 1.1</a>]. </dd>
 * <dt>
 * <code>false</code></dt>
 * <dd>[<em>required</em>] (<em>default</em>)Do not perform character normalization.</dd>
 * </dl></dd>
 * <dt><code>"schema-location"</code></dt>
 * <dd>[<em>optional</em>] Represent a <code>DOMString</code> object containing a list of URIs,
 * separated by whitespaces (characters matching the <a href='http://www.w3.org/TR/2004/REC-xml-20040204#NT-S'>nonterminal
 * production S</a> defined in section 2.3 [<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>]), that
 * represents the schemas against which validation should occur, i.e. the
 * current schema. The types of schemas referenced in this list must match
 * the type specified with <code>schema-type</code>, otherwise the behavior
 * of an implementation is undefined.  The schemas specified using this
 * property take precedence to the schema information specified in the
 * document itself. For namespace aware schema, if a schema specified using
 * this property and a schema specified in the document instance (i.e. using
 * the <code>schemaLocation</code> attribute) in a schema document (i.e.
 * using schema <code>import</code> mechanisms) share the same
 * <code>targetNamespace</code>, the schema specified by the user using this
 * property will be used. If two schemas specified using this property share
 * the same <code>targetNamespace</code> or have no namespace, the behavior
 * is implementation dependent.  If no location has been provided, this
 * parameter is <code>null</code>.
 * <p ><b>Note:</b>  The <code>"schema-location"</code> parameter is ignored
 * unless the "schema-type" parameter value is set. It is strongly
 * recommended that <code>Document.documentURI</code> will be set so that an
 * implementation can successfully resolve any external entities referenced. </dd>
 * <dt>
 * <code>"schema-type"</code></dt>
 * <dd>[<em>optional</em>] Represent a <code>DOMString</code> object containing an absolute URI
 * and representing the type of the schema language used to validate a
 * document against. Note that no lexical checking is done on the absolute
 * URI.  If this parameter is not set, a default value may be provided by
 * the implementation, based on the schema languages supported and on the
 * schema language used at load time. If no value is provided, this
 * parameter is <code>null</code>.
 * <p ><b>Note:</b>  For XML Schema [<a href='http://www.w3.org/TR/2001/REC-xmlschema-1-20010502/'>XML Schema Part 1</a>]
 * , applications must use the value
 * <code>"http://www.w3.org/2001/XMLSchema"</code>. For XML DTD [<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>],
 * applications must use the value
 * <code>"http://www.w3.org/TR/REC-xml"</code>. Other schema languages are
 * outside the scope of the W3C and therefore should recommend an absolute
 * URI in order to use this method. </dd>
 * <dt><code>"split-cdata-sections"</code></dt>
 * <dd>
 * <dl>
 * <dt>
 * <code>true</code></dt>
 * <dd>[<em>required</em>] (<em>default</em>)Split CDATA sections containing the CDATA section termination marker
 * ']]&gt;'. When a CDATA section is split a warning is issued with a
 * <code>DOMError.type</code> equals to
 * <code>"cdata-sections-splitted"</code> and
 * <code>DOMError.relatedData</code> equals to the first
 * <code>CDATASection</code> node in document order resulting from the split.</dd>
 * <dt>
 * <code>false</code></dt>
 * <dd>[<em>required</em>]Signal an error if a <code>CDATASection</code> contains an
 * unrepresentable character.</dd>
 * </dl></dd>
 * <dt><code>"validate"</code></dt>
 * <dd>
 * <dl>
 * <dt><code>true</code></dt>
 * <dd>[<em>optional</em>] Require the validation against a schema (i.e. XML schema, DTD, any
 * other type or representation of schema) of the document as it is being
 * normalized as defined by [<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>]. If
 * validation errors are found, or no schema was found, the error handler is
 * notified. Schema-normalized values will not be exposed according to the
 * schema in used unless the parameter "datatype-normalization" is
 * <code>true</code>.  This parameter will reevaluate:
 * <ul>
 * <li> Attribute nodes with
 * <code>Attr.specified</code> equals to <code>false</code>, as specified in
 * the description of the <code>Attr</code> interface;
 * </li>
 * <li> The value of the
 * attribute <code>Text.isElementContentWhitespace</code> for all
 * <code>Text</code> nodes;
 * </li>
 * <li> The value of the attribute
 * <code>Attr.isId</code> for all <code>Attr</code> nodes;
 * </li>
 * <li> The attributes
 * <code>Element.schemaTypeInfo</code> and <code>Attr.schemaTypeInfo</code>.
 * </li>
 * </ul>
 * <p ><b>Note:</b>  "validate-if-schema" and "validate" are mutually
 * exclusive, setting one of them to <code>true</code> will set the other
 * one to <code>false</code>. Applications should also consider setting the
 * parameter "well-formed" to <code>true</code>, which is the default for
 * that option, when validating the document. </dd>
 * <dt><code>false</code></dt>
 * <dd>[<em>required</em>] (<em>default</em>) Do not accomplish schema processing, including the internal subset
 * processing. Default attribute values information are kept. Note that
 * validation might still happen if "validate-if-schema" is <code>true</code>
 * . </dd>
 * </dl></dd>
 * <dt><code>"validate-if-schema"</code></dt>
 * <dd>
 * <dl>
 * <dt><code>true</code></dt>
 * <dd>[<em>optional</em>]Enable validation only if a declaration for the document element can be
 * found in a schema (independently of where it is found, i.e. XML schema,
 * DTD, or any other type or representation of schema). If validation is
 * enabled, this parameter has the same behavior as the parameter "validate"
 * set to <code>true</code>.
 * <p ><b>Note:</b>  "validate-if-schema" and "validate" are mutually
 * exclusive, setting one of them to <code>true</code> will set the other
 * one to <code>false</code>. </dd>
 * <dt><code>false</code></dt>
 * <dd>[<em>required</em>] (<em>default</em>) No schema processing should be performed if the document has a schema,
 * including internal subset processing. Default attribute values
 * information are kept. Note that validation must still happen if "validate
 * " is <code>true</code>. </dd>
 * </dl></dd>
 * <dt><code>"well-formed"</code></dt>
 * <dd>
 * <dl>
 * <dt><code>true</code></dt>
 * <dd>[<em>required</em>] (<em>default</em>) Check if all nodes are XML well formed according to the XML version in
 * use in <code>Document.xmlVersion</code>:
 * <ul>
 * <li> check if the attribute
 * <code>Node.nodeName</code> contains invalid characters according to its
 * node type and generate a <code>DOMError</code> of type
 * <code>"wf-invalid-character-in-node-name"</code>, with a
 * <code>DOMError.SEVERITY_ERROR</code> severity, if necessary;
 * </li>
 * <li> check if
 * the text content inside <code>Attr</code>, <code>Element</code>,
 * <code>Comment</code>, <code>Text</code>, <code>CDATASection</code> nodes
 * for invalid characters and generate a <code>DOMError</code> of type
 * <code>"wf-invalid-character"</code>, with a
 * <code>DOMError.SEVERITY_ERROR</code> severity, if necessary;
 * </li>
 * <li> check if
 * the data inside <code>ProcessingInstruction</code> nodes for invalid
 * characters and generate a <code>DOMError</code> of type
 * <code>"wf-invalid-character"</code>, with a
 * <code>DOMError.SEVERITY_ERROR</code> severity, if necessary;
 * </li>
 * </ul></dd>
 * <dt>
 * <code>false</code></dt>
 * <dd>[<em>optional</em>] Do not check for XML well-formedness. </dd>
 * </dl></dd>
 * </dl>
 * <p> The resolution of the system identifiers associated with entities is
 * done using <code>Document.documentURI</code>. However, when the feature
 * "LS" defined in [<a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-LS-20040407'>DOM Level 3 Load and Save</a>]
 *  is supported by the DOM implementation, the parameter
 * "resource-resolver" can also be used on <code>DOMConfiguration</code>
 * objects attached to <code>Document</code> nodes. If this parameter is
 * set, <code>Document.normalizeDocument()</code> will invoke the resource
 * resolver instead of using <code>Document.documentURI</code>.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>Document Object Model (DOM) Level 3 Core Specification</a>.
 * <p>
 * <code> DOMConfiguration </code>接口表示文档的配置并维护一个已识别参数的表。
 * 使用配置,可以更改<code> DocumentnormalizeDocument()</code>行为,例如替换<code > </>> </code>节点或指定在请求验证<code> Documen
 * t </code>时必须使用的模式类型的</> CDATASection </code>节点<code> DOMConfiguration </code >对象也用于<代码中的[<a href='http://wwww3org/TR/2004/REC-DOM-Level-3-LS-20040407'>
 *  DOM 3级加载和保存</a>] > DOMParser </code>和<code> DOMSerializer </code> interfaces <p> <code> DOMConfigura
 * tion </code>对象使用的参数名称在DOM Level 3规范中定义名称不区分大小写为了避免可能的冲突,作为约定,引用在DOM规范之外定义的参数的名称应该是唯一的因为参数被公开为名称中的属性,建
 * 议遵循[Unicode]的标识符添加字符' - '(HYPHEN-MINUS),但它不是由DOM实现DOM强制实现DOM级3核心实现需要识别本规范中定义的所有参数某些参数值也可能需要被实现支持参考参数的
 * 定义知道一个值是否必须被支持<p> <b>注意：</b>参数类似于SAX2中使用的特性和属性[<a href ='http：// wwwsaxprojectorg /' > SAX </a>]<p>以下
 * DOM中定义的参数列表：。
 * <code> DOMConfiguration </code>接口表示文档的配置并维护一个已识别参数的表。
 * <dl>
 * <dt>
 * <code>"canonical-form"</code> </dt>
 * <dd>
 * <dl>
 * <dt> <code> true </code> </dt> <dd> [<em>可选</em>]根据[<a href ='http：// wwww3org / TR / 2001 / REC-xml-c14n-20010315'>
 *  Canonical XML </a>],例如从树中删除<code> DocumentType </code>节点(如果有),或从每个元素中删除多余的命名空间声明这限制在DOM中可以表示的内容;特别是没
 * 有办法在DOM中指定属性的顺序另外,将此参数设置为<code> true </code>也会设置下面列出的参数的状态。
 * 那些参数将把"canonical-form"恢复为<code> false </code>参数设置为<code> false </code>："entities","normalize-characte
 * rs","cdata-sections"参数设置为<code> true </code>："namespaces","namespace-declarations","well-formed","ele
 * ment-content-whitespace"除非在参数说明中明确指定, / dd>。
 * <dt>
 * <code> false </code> </em> </em> <em> </dd> <dt> <code>"cdata-sections"</code> </dt>
 * <dd>
 * <dl>
 * <dt>
 *  <code> true </code> </em> <em> </em> <em> </dd> <dt> <code> false </code> </dt> <dd> [<em>必需</em>]将文
 * 档中的<code> CDATASection </code>代码>节点然后将新的<code> Text </code>节点与任何相邻的<code> Text </code>节点</dd> </dl>。
 * <dt>
 *  <code>"check-character-normalization"</code> </dt>
 * <dd>
 * <dl>
 * <dt> <code> true </code> </dt> <dd> [<em>可选</em>]检查文档中的字符是否<a href ='http：// wwww3org / TR / 2004 / REC-xml11-20040204 /#dt-fullnorm'>
 * 完全规范化</a>,如[<a href='http://wwww3org/TR/2004/REC-xml11-20040204/'>]的附录B中所定义XML 11 </a>]当遇到标准化检查失败的一系列
 * 字符时,发出<code> DOMErrortype </code>等于"check-character-normalization-failure"的错误</dd> <dt > <code> false
 *  </code> </em> </em>(<em>默认</em>)不检查字符是否被归一化</dd> > </dd> <dt> <code>"comments"</code> </dt>。
 * <dd>
 * <dl>
 * <dt>
 * <code> true </code> </em> <em> </em>(<em> </em> dd </code> </d> </em>在文件中舍弃<code>注释</code>节点</dd> > </dd>
 * 。
 * <dt>
 *  <code>"datatype-normalization"</code> </dt>
 * <dd>
 * <dl>
 * <dt> <code> true </code> </dt> <dd> [<em>可选</em>]在树中公开模式规范化值,例如<a href ='http：// wwww3org / TR / 2001 / REC-xmlschema-1-20010502 /#key-nv'>
 *  XML模式情况下的XML模式规范化值</a>由于此参数需要具有模式信息,因此"validate"参数也将设置为<code> true </code>当"validate"为<code> false </code>
 * 时激活此参数没有效果,并且不会发生模式归一化<p> <b>注意：</b>包含XML 10处理的结果,此参数不适用于[<a href='http://wwww3org/TR/2004/REC-xml-20040204'>
 *  XML 10]第333节中定义的属性值规范化, / a>],并且仅适用于文档类型定义(DTD)以外的模式语言</DD>。
 * <dt>
 * <code> false </code> </dt> </em>(<em> </em>)不要在树上执行模式归一化</dd> > </dd>
 * <dt>
 *  <code>"element-content-whitespace"</code> </dt>
 * <dd>
 * <dl>
 *  <dt> <code> true </code> </em>(<em> </em> > <code> false </code> </dt> </em>]放弃元素内容中包含空格的所有<code> Te
 * xt </code>节点,如<a href ='http：// wwww3org / TR / 2004 / REC-xml-infoset-20040204#infoitemcharacter'> [
 * element content whitespace] </a>实现期望使用属性<code> TextisElementContentWhitespace </code>如果应该丢弃<code> Tex
 * t </code>节点</dd> </dl> </dd> <dt> <code>"entities"</code>。
 * <dd>
 * <dl>
 * <dt>
 * <code> true </code> </em> </em>(<em> </em>)在文档中保留<code> EntityReference </code> dd
 * <dt>
 *  <code> false </code> </dt> <dd> [<em>必需</em>]从文档中删除所有<code> EntityReference </code>节点,将实体扩展直接放置在< > 
 * Text </code>节点被规范化,如<code> Nodenormalize </code>中定义。
 * 只有<a href='http://wwww3org/TR/2004/REC-xml-infoset-20040204/#infoitemrse'>未展开实体引用</a>保存在文档</dd>中。
 * </dl>
 *  <p> <b>注意：</b>此参数不影响<code>实体</code>节点</dd>
 * <dt>
 * <code>"error-handler"</code> </dt> </em>包含<code> DOMErrorHandler </code>对象如果在文档中遇到错误,实现将调用使用此参数注册的<code>
 *  DOMErrorHandler </code>实现可以提供一个默认的<code> DOMErrorHandler </code>对象当被调用时,<code> DOMErrorrelatedData </code>
 * 错误发生如果实现无法确定发生错误的节点,<code> DOMErrorrelatedData </code>将包含<code> Document </code>节点从错误处理程序内对文档的突变将导致实现
 * 依赖行为</dd> <dt> <code>"infoset"</code> </dt>。
 * <dd>
 * <dl>
 * <dt>
 * <code> true </code> </dt> <dd> [<em>必需</em>]在文档中保留XML信息集中定义的信息[<a href ='http：// wwww3org / TR / 2004 / REC-xml-infoset-20040204 /'>
 *  XML信息集</a>]这将强制以下参数为<code> false </code>："validate-if-schema","entities","datatype -normalization","
 * cdata-sections"这将强制以下参数为<code> true </code>："namespace-declarations","well-formed","element-content-w
 * hitespace","comments" "除非在参数的描述中明确指定,否则不改变其他参数注意,只有当上面指定的各个参数适当设置时,使用<code> getParameter </code>查询此参数
 * 才会返回<code> true </code></dd> <dt> <code> false </code> </dt> <dd>将<code>信息集</code>设置为<code> false </code>
 *  > </dd> <dt> <code>"namespaces"</code> </dt>。
 * <dd>
 * <dl>
 * <dt>
 * <code> true </code> </dt> </em> <em> </em>执行</dd> <dt>代码> false </code> </dt> </em>不要执行命名空间处理</dd> </dl>
 * 。
 * <dt>
 *  <code>"namespace-declarations"</code> </dt> <dd>如果参数"namespaces"设置为<code> false </code>
 * <dl>
 * <dt> <code> true </code> </em> <em> </em>包含名称空间声明属性,请参阅[<a href='http://wwww3org/TR/1999/REC-xml-names-19990114/'>
 *  XML命名空间</a>]中的"声明命名空间"部分和[<a href] ='http：// wwww3org / TR / 2004 / REC-xml-names11-20040204 /'> XML
 * 命名空间11 </a>] </dd> <dt> <code> false </code> </dd> [<em> required </em>]丢弃所有命名空间声明属性即使此参数设置为<code> fa
 * lse </code> </dd,命名空间前缀(<code> Nodeprefix </code> > </dl> </dd> <dt> <code>"normalize-characters"</code>
 *  </dt>。
 * 
 * @since DOM Level 3
 */
public interface DOMConfiguration {
    /**
     * Set the value of a parameter.
     * <p>
     * <dd>
     * <dl>
     * <dt> <code> true </code> </dt> <dd> [<em>可选</em>] <a href ='http：// wwww3org / TR / 2004 / REC-xml11-20040204 /# dt-fullnorm'>
     * 将[<a href='http://wwww3org/TR/2004/REC-xml11-20040204/'> XML 11]的附录B中定义的文档中的字符完全标准化</a> a>] </dd>。
     * <dt>
     * <code> false </code> </dt> </em> <em> </em>不要执行字符规范化</dd> dd> <dt> <code>"schema-location"</code> </dt>
     *  </em>代表包含URI列表的<code> DOMString </code> ,由空格分隔(符合第23节中定义的<a href='http://wwww3org/TR/2004/REC-xml-20040204#NT-S'>
     * 非终端产品S </a>的字符[<a href = 'http：// wwww3org / TR / 2004 / REC-xml-20040204'> XML 10 </a>]),表示验证应该发生的模式
     * ,即当前模式此列表中引用的模式类型必须匹配<code> schema-type </code>指定的类型,否则实现的行为是未定义的使用此属性指定的模式优先于文档本身中指定的模式信息对于命名空间感知模式,
     * 如果使用此属性指定的模式和文档实例中指定的模式(即使用<code> schemaLocation </code>属性)在模式文档中(即使用模式<code> import </code>机制)共享相同的<code>
     *  targetNamespace </code>,将使用由用户使用此属性指定的模式如果使用此属性指定的两个模式共享相同的<code> targetNamespace </code>或没有命名空间,行为是
     * 依赖于实现如果没有提供位置,此参数是<code> null </code><p> <b>注意：</b>除非设置了"schema-type"参数值,否则将忽略<code>"schema-location"
     * </code>参数强烈建议<code> DocumentdocumentURI <代码>,以便实现可以成功解析引用的任何外部实体</dd>。
     * <dt>
     * <code>"schema-type"</code> </dt> <dd> [<em>可选</em>]表示一个包含绝对URI并表示类型的<code> DOMString </code>用于验证文档的模式
     * 语言注意,没有对绝对URI进行词法检查如果未设置此参数,则实现可以基于支持的模式语言和负载上使用的模式语言提供默认值time如果没有提供值,此参数为<code> null </code> <p> <b>
     * 注：</b>对于XML模式[<a href ='http：// wwww3org / TR / 2001 / REC -xmlschema-1-20010502 /'> XML Schema Part 
     * 1 </a>],应用程序必须使用值<code>"http：// wwww3org / 2001 / XMLSchema"</code>对于XML DTD [<a href = 'http：// wwww3org / TR / 2004 / REC-xml-20040204'>
     *  XML 10 </a>],应用程序必须使用值<code>"http：// wwww3org / TR / REC-xml"</code>其他模式语言超出了W3C的范围,因此应该推荐一个绝对URI使用此
     * 方法的顺序</dd> <dt> <code>"split-cdata-sections"</code> </。
     * <dd>
     * <dl>
     * <dt>
     * <code> true </code> </em> <em> </em>(<em> </em>)</em>分割CDATA区段包含CDATA区段终止符号']]&gt;当CDATA段被拆分时,发出一个警告,
     * 其中<code> DOMErrortype </code>等于<code>"cdata-sections-splitted"</code>和<code> DOMErrorrelatedData </code>
     * 代码> CDATASection </code>节点在分割</dd>中生成的文档顺序。
     * <dt>
     *  <code> false </code> </dt> </em>如果<code> CDATASection </code>包含不​​可表示的字符, </dd> <dt> <code>"validate
     * "</code> </dt>。
     * <dd>
     * <dl>
     * <dt> <code> true </code> </dt> <dd> [<em>可选</em>]需要对模式(即XML模式,DTD,任何其他类型或模式表示)该文档按[<a href='http://wwww3org/TR/2004/REC-xml-20040204'>
     *  XML 10 </a>]定义进行规范化。
     * 如果发现验证错误,或者没有模式发现,错误处理程序被通知除非参数"datatype-normalization"是<code> true </code>,此模式标准化值将不会根据使用的模式公开此参数将重新
     * 评估：。
     * <ul>
     *  <li>属性节点<code> Attrspecified </code>等于<code> Attr </code>接口的说明中规定的<code> false </code>
     * </li>
     * <li>所有<code> Text </code>节点的属性<code> TextisElementContentWhitespace </code>的值;
     * </li>
     *  <li>所有<code> Attr </code>节点的属性<code> AttrisId </code>的值;
     * </li>
     *  <li>属性<code> ElementschemaTypeInfo </code>和<code> AttrschemaTypeInfo </code>
     * </li>
     * </ul>
     * <p> <b>注意：</b>"validate-if-schema"和"validate"是互斥的,将其中一个设置为<code> true </code>会将另一个设置为<code> false </code>
     * 应用程序还应考虑在验证文档时将"well-formed"参数设置为<code> true </code>(该选项的默认值)</dd> <dt> <code> false < / code> </em>]
     *  <em> <em> </em> <em> </em>不需要完成模式处理,包括内部子集处理。
     * 
     * @param name The name of the parameter to set.
     * @param value  The new value or <code>null</code> if the user wishes to
     *   unset the parameter. While the type of the value parameter is
     *   defined as <code>DOMUserData</code>, the object type must match the
     *   type defined by the definition of the parameter. For example, if
     *   the parameter is "error-handler", the value must be of type
     *   <code>DOMErrorHandler</code>.
     * @exception DOMException
     *    NOT_FOUND_ERR: Raised when the parameter name is not recognized.
     *   <br> NOT_SUPPORTED_ERR: Raised when the parameter name is recognized
     *   but the requested value cannot be set.
     *   <br> TYPE_MISMATCH_ERR: Raised if the value type for this parameter
     *   name is incompatible with the expected value type.
     */
    public void setParameter(String name,
                             Object value)
                             throws DOMException;

    /**
     *  Return the value of a parameter if known.
     * <p>
     * 如果"validate-if-schema"是<code> true </code> </dd> </dl> </dd> <dt> <code>"validate-if-schema"</code> /
     *  dt>。
     * <dd>
     * <dl>
     * <dt> <code> true </code> </dt> <dd> [<em>可选</em>]只有在文档元素的声明可以在模式中找到如果启用了验证,此参数的行为与参数"validate"设置为<code>
     *  true </code> <p> <b>的行为相同。
     * 注意：</b>"validate-if-schema"和"validate"是互斥的,将其中一个设置为<code> true </code>会将另一个设置为<code> false </code> dd
     * > <dt> <code> false </code> </dt> </em>(<em>默认</em>包括内部子集处理的模式保留默认属性值信息注意,如果"validate"是<code> true </code>
     *  </dd> </dl> </dd> <dt> <code>"格式正确的"</code>。
     * <dd>
     * <dl>
     * <dt> <code> true </code> </em> </em>(<em>默认</em>)检查所有节点是否根据XML版本在<code> DocumentxmlVersion </code>中使用
     * ：。
     * <ul>
     *  <li>检查属性<code> NodenodeName </code>是否包含根据其节点类型的无效字符,并生成<code> <code>类型的<code> DOMError </code>"wf-in
     * valid-character-in-node- name"</code>,如果需要,可以使用<code> DOMErrorSEVERITY_ERROR </code>严重性;。
     * </li>
     *  <li>检查<code> Attr </code>,<code> Element </code>,<code> Comment </code>,<code> Text </code>,<code> C
     * DATASection < / code>节点生成无效字符,并生成<code>"wf-invalid-character"</code>类型的<code> DOMError </code>,如果必要,使
     * 用<code> DOMErrorSEVERITY_ERROR </code>。
     * </li>
     * <li>检查<code> ProcessingInstruction </code>节点中无效字符的数据,并生成<code>"wf-invalid-character"</code>类型的<code> 
     * DOMError </code> <code> DOMErrorSEVERITY_ERROR </code>严重性;。
     * </li>
     * 
     * @param name  The name of the parameter.
     * @return  The current object associated with the specified parameter or
     *   <code>null</code> if no object has been associated or if the
     *   parameter is not supported.
     * @exception DOMException
     *    NOT_FOUND_ERR: Raised when the parameter name is not recognized.
     */
    public Object getParameter(String name)
                               throws DOMException;

    /**
     * Check if setting a parameter to a specific value is supported.
     * <p>
     *  </ul> </dd>
     * <dt>
     *  <code> false </code> </dt> </em>不检查XML格式</dd> </dl>
     * </dl>
     * <p>与实体相关联的系统标识符的分辨率使用<code> DocumentdocumentURI </code>来完成。
     * 但是,当在[<a href ='http：// wwww3org / TR / 2004 / REC DOM实现中支持"DOM Level 3-LS-20040407'> DOM Level 3 Loa
     * d and Save </a>],参数"resource-resolver"也可以在<code> DOMConfiguration </code>对象附加到<code> Document </code>
     * 节点如果设置此参数,<code> DocumentnormalizeDocument()</code>将调用资源解析器,而不是使用<code> DocumentdocumentURI </code> <p>
     *  <a href='http://wwww3org/TR/2004/REC-DOM-Level-3-Core-20040407'>文档对象模型(DOM)3级核心规范</a>。
     * <p>与实体相关联的系统标识符的分辨率使用<code> DocumentdocumentURI </code>来完成。
     * 
     * 
     * @param name The name of the parameter to check.
     * @param value  An object. if <code>null</code>, the returned value is
     *   <code>true</code>.
     * @return  <code>true</code> if the parameter could be successfully set
     *   to the specified value, or <code>false</code> if the parameter is
     *   not recognized or the requested value is not supported. This does
     *   not change the current value of the parameter itself.
     */
    public boolean canSetParameter(String name,
                                   Object value);

    /**
     *  The list of the parameters supported by this
     * <code>DOMConfiguration</code> object and for which at least one value
     * can be set by the application. Note that this list can also contain
     * parameter names defined outside this specification.
     * <p>
     *  设置参数的值
     * 
     */
    public DOMStringList getParameterNames();

}
