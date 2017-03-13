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

import org.w3c.dom.Document;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.Node;
import org.w3c.dom.DOMException;

/**
 *  An interface to an object that is able to build, or augment, a DOM tree
 * from various input sources.
 * <p> <code>LSParser</code> provides an API for parsing XML and building the
 * corresponding DOM document structure. A <code>LSParser</code> instance
 * can be obtained by invoking the
 * <code>DOMImplementationLS.createLSParser()</code> method.
 * <p> As specified in [<a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>DOM Level 3 Core</a>]
 * , when a document is first made available via the LSParser:
 * <ul>
 * <li> there will
 * never be two adjacent nodes of type NODE_TEXT, and there will never be
 * empty text nodes.
 * </li>
 * <li> it is expected that the <code>value</code> and
 * <code>nodeValue</code> attributes of an <code>Attr</code> node initially
 * return the <a href='http://www.w3.org/TR/2004/REC-xml-20040204#AVNormalize'>XML 1.0
 * normalized value</a>. However, if the parameters "<a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#parameter-validate-if-schema'>
 * validate-if-schema</a>" and "<a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#parameter-datatype-normalization'>
 * datatype-normalization</a>" are set to <code>true</code>, depending on the attribute normalization
 * used, the attribute values may differ from the ones obtained by the XML
 * 1.0 attribute normalization. If the parameters "<a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#parameter-datatype-normalization'>
 * datatype-normalization</a>" is set to <code>false</code>, the XML 1.0 attribute normalization is
 * guaranteed to occur, and if the attributes list does not contain
 * namespace declarations, the <code>attributes</code> attribute on
 * <code>Element</code> node represents the property <b>[attributes]</b> defined in [<a href='http://www.w3.org/TR/2004/REC-xml-infoset-20040204/'>XML Information Set</a>]
 * .
 * </li>
 * </ul>
 * <p> Asynchronous <code>LSParser</code> objects are expected to also
 * implement the <code>events::EventTarget</code> interface so that event
 * listeners can be registered on asynchronous <code>LSParser</code>
 * objects.
 * <p> Events supported by asynchronous <code>LSParser</code> objects are:
 * <dl>
 * <dt>load</dt>
 * <dd>
 *  The <code>LSParser</code> finishes to load the document. See also the
 * definition of the <code>LSLoadEvent</code> interface. </dd>
 * <dt>progress</dt>
 * <dd> The
 * <code>LSParser</code> signals progress as data is parsed.  This
 * specification does not attempt to define exactly when progress events
 * should be dispatched. That is intentionally left as
 * implementation-dependent. Here is one example of how an application might
 * dispatch progress events: Once the parser starts receiving data, a
 * progress event is dispatched to indicate that the parsing starts. From
 * there on, a progress event is dispatched for every 4096 bytes of data
 * that is received and processed. This is only one example, though, and
 * implementations can choose to dispatch progress events at any time while
 * parsing, or not dispatch them at all.  See also the definition of the
 * <code>LSProgressEvent</code> interface. </dd>
 * </dl>
 * <p ><b>Note:</b>  All events defined in this specification use the
 * namespace URI <code>"http://www.w3.org/2002/DOMLS"</code>.
 * <p> While parsing an input source, errors are reported to the application
 * through the error handler (<code>LSParser.domConfig</code>'s "<a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#parameter-error-handler'>
 * error-handler</a>" parameter). This specification does in no way try to define all possible
 * errors that can occur while parsing XML, or any other markup, but some
 * common error cases are defined. The types (<code>DOMError.type</code>) of
 * errors and warnings defined by this specification are:
 * <dl>
 * <dt>
 * <code>"check-character-normalization-failure" [error]</code> </dt>
 * <dd> Raised if
 * the parameter "<a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#parameter-check-character-normalization'>
 * check-character-normalization</a>" is set to true and a string is encountered that fails normalization
 * checking. </dd>
 * <dt><code>"doctype-not-allowed" [fatal]</code></dt>
 * <dd> Raised if the
 * configuration parameter "disallow-doctype" is set to <code>true</code>
 * and a doctype is encountered. </dd>
 * <dt><code>"no-input-specified" [fatal]</code></dt>
 * <dd>
 * Raised when loading a document and no input is specified in the
 * <code>LSInput</code> object. </dd>
 * <dt>
 * <code>"pi-base-uri-not-preserved" [warning]</code></dt>
 * <dd> Raised if a processing
 * instruction is encountered in a location where the base URI of the
 * processing instruction can not be preserved.  One example of a case where
 * this warning will be raised is if the configuration parameter "<a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#parameter-entities'>
 * entities</a>" is set to <code>false</code> and the following XML file is parsed:
 * <pre>
 * &lt;!DOCTYPE root [ &lt;!ENTITY e SYSTEM 'subdir/myentity.ent' ]&gt;
 * &lt;root&gt; &amp;e; &lt;/root&gt;</pre>
 *  And <code>subdir/myentity.ent</code>
 * contains:
 * <pre>&lt;one&gt; &lt;two/&gt; &lt;/one&gt; &lt;?pi 3.14159?&gt;
 * &lt;more/&gt;</pre>
 * </dd>
 * <dt><code>"unbound-prefix-in-entity" [warning]</code></dt>
 * <dd> An
 * implementation dependent warning that may be raised if the configuration
 * parameter "<a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#parameter-namespaces'>
 * namespaces</a>" is set to <code>true</code> and an unbound namespace prefix is
 * encountered in an entity's replacement text. Raising this warning is not
 * enforced since some existing parsers may not recognize unbound namespace
 * prefixes in the replacement text of entities. </dd>
 * <dt>
 * <code>"unknown-character-denormalization" [fatal]</code></dt>
 * <dd> Raised if the
 * configuration parameter "ignore-unknown-character-denormalizations" is
 * set to <code>false</code> and a character is encountered for which the
 * processor cannot determine the normalization properties. </dd>
 * <dt>
 * <code>"unsupported-encoding" [fatal]</code></dt>
 * <dd> Raised if an unsupported
 * encoding is encountered. </dd>
 * <dt><code>"unsupported-media-type" [fatal]</code></dt>
 * <dd>
 * Raised if the configuration parameter "supported-media-types-only" is set
 * to <code>true</code> and an unsupported media type is encountered. </dd>
 * </dl>
 * <p> In addition to raising the defined errors and warnings, implementations
 * are expected to raise implementation specific errors and warnings for any
 * other error and warning cases such as IO errors (file not found,
 * permission denied,...), XML well-formedness errors, and so on.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-LS-20040407'>Document Object Model (DOM) Level 3 Load
and Save Specification</a>.
 * <p>
 * 到能够从各种输入源<p> <code> LSParser </code>构建或扩充DOM树的对象的接口提供了一个用于解析XML并构建相应的DOM文档结构的API A <code> LSParser通过调
 * 用<code> DOMImplementationLScreateLSParser()</code>方法<p>可以获得</code>实例</href ="http：// wwww3org / TR / 3-Core-20040407'>
 *  DOM Level 3 Core </a>],当文档首次通过LSParser时可用：。
 * <ul>
 *  <li>永远不会有两个相邻的节点类型NODE_TEXT,并且永远不会有空文本节点
 * </li>
 * <li>希望<code> Attr </code>节点的<code> value </code>和<code> nodeValue </code>属性最初返回<a href ='http：// wwww3org / TR / 2004 / REC-xml-20040204#AVNormalize'>
 *  XML 10规范化值</a>但是,如果参数"<a href ='http：// wwww3org / TR / DOM-Level-3-Core / corehtml #parameter-validate-if-schema'>
 *  validate-if-schema </a>"和"<a href ='http：// wwww3org / TR / DOM-Level-3-Core / corehtml#parameter-datatype-normalization '数据类型归一化</a>
 * "设置为<code> true </code>,则根据使用的属性规范化,属性值可能不同于XML 10属性规范化获得的值。
 * 如果参数"<a href ='http：// wwww3org / TR / DOM-Level-3-Core / corehtml#parameter-datatype-normalization'>
 *  datatype-normalization </a>"设置为<code> false </code>,则保证发生XML 10属性规范化,如果属性列表不包含命名空间声明, <code> Element
 *  </code>节点上的<code> attributes </code>属性表示在<a href ='http：// wwww3org / TR / 2004中定义的属性<b> [attributes
 * ] <// REC-xml-infoset-20040204 /'XML信息集</a>]。
 * </li>
 * </ul>
 * <P>异步<代码>的LSParser </code>的对象,预计也实施<代码>事件::事件目标</code>的接口,以便事件侦听器能够在异步注册的<code>的LSParser </code>的对象< p>
 * 异步<code> LSParser </code>对象支持的事件是：。
 * <dl>
 *  <dt>负载</dt>
 * <dd>
 * <code> LSParser </code>完成加载文档参见<code> LSLoadEvent </code>接口的定义</dd> <dt> progress </dt> <dd> / code>信
 * 号在数据被解析时的进度此规范没有尝试定义何时应该调度progress事件。
 * 有意地保留为依赖于实现以下是应用程序可能如何分派进度事件的一个示例：一旦解析器开始接收数据,则调度progress事件以指示解析开始从那里开始,为接收和处理的每个4096字节的数据分派进度事件。
 * 但这仅是一个示例,并且实现可以选择在任何时间分派进度事件分析时,或根本不分派它们另见<code> LSProgressEvent </code>接口</dd>的定义。
 * </dl>
 * <p> <b>注意：</b>本规范中定义的所有事件都使用命名空间URI <code>"http：// wwww3org / 2002 / DOMLS"</code> <p>通过错误处理程序(<code>
 *  LSParserdomConfig </code>的"<a href ='http：// wwww3org / TR / DOM-Level-3-Core / corehtml#parameter-error-handler' >
 *  error-handler </a>"参数)此规范没有以任何方式尝试定义在解析XML或任何其他标记时可能发生的所有错误,但定义了一些常见的错误情况类型(<code> DOMErrortype < / code>
 * )的错误和警告定义为：。
 * <dl>
 * <dt>
 * <code>"check-character-normalization-failure"错误</code> </dt> <dd>如果参数"<a href ='http：// wwww3org / TR / DOM-Level- Core / corehtml#parameter-check-character-normalization'>
 *  check-character-normalization </a>"设置为true,遇到标准化检查失败的字符串</dd> <dt> <code>"doctype-not -allowed"[fata
 * l] </code> </dt> <dd>如果配置参数"disallow-doctype"设置为<code> true </code>且遇到一个doctype </d> <code>"no-input-
 * specified"[fatal] </code> </dt>。
 * <dd>
 *  在加载文档时出现,并且在<code> LSInput </code>对象</dd>中未指定输入
 * <dt>
 * <code>"pi-base-uri-not-preserved"[warning] </code> </dt> <dd>在处理指令的基本URI无法保存的位置遇到处理指令时引发将提出此警告的情况的一个示
 * 例是,如果配置参数"<a href='http://wwww3org/TR/DOM-Level-3-Core/corehtml#parameter-entities'>实体</a> "设置为<code>
 *  false </code>,并解析以下XML文件：。
 * <pre>
 *  &lt;！DOCTYPE root [&lt;！ENTITY e SYSTEM'subdir / myentityent']&gt; &lt; root&gt; &amp; e; &lt; / roo
 * t&gt; </pre>且<code> subdir / myentityent </code>包含：<pre>&lt; one&gt; &lt; two /&gt; &lt; / one&gt; &l
 * t;?pi 314159?&gt; &lt; more /&gt; </pre>。
 * </dd>
 * <dt> <code>"unbound-prefix-in-entity"[warning] </code> </dt> <dd>如果配置参数"<a href = / wwww3org / TR / DOM-Level-3-Core / corehtml#parameter-namespaces'>
 * 命名空间</a>"设置为<code> true </code>,并且在实体的替换文本中遇到未绑定的命名空间前缀不会强制执行此警告,因为某些现有的解析器可能无法识别实体替换文本中的未绑定命名空间前缀</dd>
 * 。
 * <dt>
 *  <code>"未知字符反规范化"[fatal] </code> </dt> <dd>如果配置参数"ignore-unknown-character-denormalizations"设置为<code>
 *  false <遇到处理器无法确定规范化属性</dd>的字符。
 * <dt>
 * <code>"unsupported-encoding"[fatal] </code> </dt> <dd>遇到不支持的编码时引发</dd> <dt> <code>"unsupported-media-
 * type" / code> </dt>。
 * <dd>
 *  在配置参数"supported-media-types-only"设置为<code> true </code>且遇到不支持的媒体类型时提示</dd>
 * </dl>
 *  <p>除了提高定义的错误和警告之外,实现还会针对任何其他错误和警告情况(如IO错误(找不到文件,权限被拒绝)),XML格式错误等引发实现特定的错误和警告。
 *  <p>另请参阅<a href='http://wwww3org/TR/2004/REC-DOM-Level-3-LS-20040407'>文档对象模型(DOM)3级加载和保存规范< / a>。
 * 
 */
public interface LSParser {
    /**
     *  The <code>DOMConfiguration</code> object used when parsing an input
     * source. This <code>DOMConfiguration</code> is specific to the parse
     * operation. No parameter values from this <code>DOMConfiguration</code>
     *  object are passed automatically to the <code>DOMConfiguration</code>
     * object on the <code>Document</code> that is created, or used, by the
     * parse operation. The DOM application is responsible for passing any
     * needed parameter values from this <code>DOMConfiguration</code>
     * object to the <code>DOMConfiguration</code> object referenced by the
     * <code>Document</code> object.
     * <br> In addition to the parameters recognized in on the <a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#DOMConfiguration'>
     * DOMConfiguration</a> interface defined in [<a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>DOM Level 3 Core</a>]
     * , the <code>DOMConfiguration</code> objects for <code>LSParser</code>
     * add or modify the following parameters:
     * <dl>
     * <dt>
     * <code>"charset-overrides-xml-encoding"</code></dt>
     * <dd>
     * <dl>
     * <dt><code>true</code></dt>
     * <dd>[<em>optional</em>] (<em>default</em>) If a higher level protocol such as HTTP [<a href='http://www.ietf.org/rfc/rfc2616.txt'>IETF RFC 2616</a>] provides an
     * indication of the character encoding of the input stream being
     * processed, that will override any encoding specified in the XML
     * declaration or the Text declaration (see also section 4.3.3,
     * "Character Encoding in Entities", in [<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>]).
     * Explicitly setting an encoding in the <code>LSInput</code> overrides
     * any encoding from the protocol. </dd>
     * <dt><code>false</code></dt>
     * <dd>[<em>required</em>] The parser ignores any character set encoding information from
     * higher-level protocols. </dd>
     * </dl></dd>
     * <dt><code>"disallow-doctype"</code></dt>
     * <dd>
     * <dl>
     * <dt>
     * <code>true</code></dt>
     * <dd>[<em>optional</em>] Throw a fatal <b>"doctype-not-allowed"</b> error if a doctype node is found while parsing the document. This is
     * useful when dealing with things like SOAP envelopes where doctype
     * nodes are not allowed. </dd>
     * <dt><code>false</code></dt>
     * <dd>[<em>required</em>] (<em>default</em>) Allow doctype nodes in the document. </dd>
     * </dl></dd>
     * <dt>
     * <code>"ignore-unknown-character-denormalizations"</code></dt>
     * <dd>
     * <dl>
     * <dt>
     * <code>true</code></dt>
     * <dd>[<em>required</em>] (<em>default</em>) If, while verifying full normalization when [<a href='http://www.w3.org/TR/2004/REC-xml11-20040204/'>XML 1.1</a>] is
     * supported, a processor encounters characters for which it cannot
     * determine the normalization properties, then the processor will
     * ignore any possible denormalizations caused by these characters.
     * This parameter is ignored for [<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>]. </dd>
     * <dt>
     * <code>false</code></dt>
     * <dd>[<em>optional</em>] Report an fatal <b>"unknown-character-denormalization"</b> error if a character is encountered for which the processor cannot
     * determine the normalization properties. </dd>
     * </dl></dd>
     * <dt><code>"infoset"</code></dt>
     * <dd> See
     * the definition of <code>DOMConfiguration</code> for a description of
     * this parameter. Unlike in [<a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>DOM Level 3 Core</a>]
     * , this parameter will default to <code>true</code> for
     * <code>LSParser</code>. </dd>
     * <dt><code>"namespaces"</code></dt>
     * <dd>
     * <dl>
     * <dt><code>true</code></dt>
     * <dd>[<em>required</em>] (<em>default</em>) Perform the namespace processing as defined in [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML Namespaces</a>]
     *  and [<a href='http://www.w3.org/TR/2004/REC-xml-names11-20040204/'>XML Namespaces 1.1</a>]
     * . </dd>
     * <dt><code>false</code></dt>
     * <dd>[<em>optional</em>] Do not perform the namespace processing. </dd>
     * </dl></dd>
     * <dt>
     * <code>"resource-resolver"</code></dt>
     * <dd>[<em>required</em>] A reference to a <code>LSResourceResolver</code> object, or null. If
     * the value of this parameter is not null when an external resource
     * (such as an external XML entity or an XML schema location) is
     * encountered, the implementation will request that the
     * <code>LSResourceResolver</code> referenced in this parameter resolves
     * the resource. </dd>
     * <dt><code>"supported-media-types-only"</code></dt>
     * <dd>
     * <dl>
     * <dt>
     * <code>true</code></dt>
     * <dd>[<em>optional</em>] Check that the media type of the parsed resource is a supported media
     * type. If an unsupported media type is encountered, a fatal error of
     * type <b>"unsupported-media-type"</b> will be raised. The media types defined in [<a href='http://www.ietf.org/rfc/rfc3023.txt'>IETF RFC 3023</a>] must always
     * be accepted. </dd>
     * <dt><code>false</code></dt>
     * <dd>[<em>required</em>] (<em>default</em>) Accept any media type. </dd>
     * </dl></dd>
     * <dt><code>"validate"</code></dt>
     * <dd> See the definition of
     * <code>DOMConfiguration</code> for a description of this parameter.
     * Unlike in [<a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>DOM Level 3 Core</a>]
     * , the processing of the internal subset is always accomplished, even
     * if this parameter is set to <code>false</code>. </dd>
     * <dt>
     * <code>"validate-if-schema"</code></dt>
     * <dd> See the definition of
     * <code>DOMConfiguration</code> for a description of this parameter.
     * Unlike in [<a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>DOM Level 3 Core</a>]
     * , the processing of the internal subset is always accomplished, even
     * if this parameter is set to <code>false</code>. </dd>
     * <dt>
     * <code>"well-formed"</code></dt>
     * <dd> See the definition of
     * <code>DOMConfiguration</code> for a description of this parameter.
     * Unlike in [<a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>DOM Level 3 Core</a>]
     * , this parameter cannot be set to <code>false</code>. </dd>
     * </dl>
     * <p>
     * 解析输入源时使用的<code> DOMConfiguration </code>对象此<code> DOMConfiguration </code>特定于解析操作此<code> DOMConfigura
     * tion </code>对象中的参数值不会自动传递到由解析操作创建或使用的<code> Document </code>上的<code> DOMConfiguration </code>对象DOM应用程
     * 序负责从此<code> DOMConfiguration </code >对象引用到<code> Document </code>对象引用的<code> DOMConfiguration </code>
     * 对象<br>除了在<a href ='http：// wwww3org / TR / DOM-Level-3-Core / corehtml#DOMConfiguration'> DOMConfigur
     * ation </a> interface defined in [<a href ='http：// wwww3org / TR / 2004 / REC-DOM-Level-3-Core-20040407'>
     *  DOM Level 3 Core </a>]中,<code> LSParser </code>的<code> DOMConfiguration </code>修改以下参数：。
     * <dl>
     * <dt>
     * <code>"charset-overrides-xml-encoding"</code> </dt>
     * <dd>
     * <dl>
     * <dt> <code> true </code> </em> </em>如果使用较高级别的协议,例如HTTP [<a href ='http：// wwwietforg / rfc / rfc2616txt'>
     *  IETF RFC 2616 </a>]提供正在处理的输入流的字符编码的指示,将覆盖XML声明或Text声明中指定的任何编码另请参见[<a href='http://wwww3org/TR/2004/REC-xml-20040204'>
     *  XML 10 </a>]中的第433节"实体中的字符编码")。
     * 在<code> LSInput </code>覆盖协议中的任何编码</dd> <dt> <code> false </code> </来自较高级协议的字符集编码信息</dd> </dl> </dd> <dt>
     *  <code>"disallow-doctype"</code>。
     * <dd>
     * <dl>
     * <dt>
     * <code> true </code> </dt> <dd> [<em>可选</em>]如果发现doctype节点,则抛出致命的<b>"doctype-not-allowed"</解析文档这在处理诸如不允许使用doctype节点的SOAP信封时非常有用</dd>
     *  <dt> <code> false </code> </dt> <dd> ](<em>默认</em>)允许文档中的doctype节点</dd> </dl> </dd>。
     * <dt>
     *  <code>"ignore-unknown-character-denormalizations"</code> </dt>
     * <dd>
     * <dl>
     * <dt>
     * 
     * <dt>
     * <code> false </code> </dt> <dd> [<em>可选</em>]遇到致命的<b>"unknown-character-denormalization"</处理器无法确定规范化属性</dd>
     *  </dl> </dd> <dt> <code>"infoset"</code> </dt> <dd>请参阅<code> DOMConfiguration </code >此参数的说明与[<a href='http://wwww3org/TR/2004/REC-DOM-Level-3-Core-20040407'>
     *  DOM 3级核心</a>]不同,此参数对于<code> LSParser </code> </dd> <dt> <code>"namespaces"</code>将默认为<code> true </code>
     * 。
     * <dd>
     * <dl>
     * <d> </em> <em> </em>执行命名空间处理,如[<a href = 'http：// wwww3org / TR / 1999 / REC-xml-names-19990114 /'> X
     * ML命名空间</a>]和[<a href ='http：// wwww3org / TR / 2004 / REC-xml-names11 -20040204 /'> XML命名空间11 </a>] </dd>
     *  <dt> <code> false </code> </处理</dd> </dl> </dd>。
     * <dt>
     * <code>"resource-resolver"</code> </dt> </em>对<code> LSResourceResolver </code>对象的引用或null如果此值参数在遇到外部资源
     * (例如外部XML实体或XML模式位置)时不为空,实现将请求此参数中引用的<code> LSResourceResolver </code>解析资源</dd> dt> <code>"supported-m
     * edia-types-only"</code> </dt>。
     * <dd>
     * <dl>
     * <dt>
     * <code> true </code> </em> </em>检查解析资源的媒体类型是否支持媒体类型如果遇到不支持的媒体类型,则会出现致命错误将会引发<b>"unsupported-media-type
     * "</b>类型在[<a href='http://wwwietforg/rfc/rfc3023txt'> IETF RFC 3023 </a>]中定义的媒体类型必须始终接受</dd> <dt> <code>
     *  false </code> </em> <em> </em> / dd> </dl> </dd> </dt> </code> </code> </code>请参阅<code> DOMConfigura
     * tion </code>的定义。
     * 在[<a href ='http：// wwww3org / TR / 2004 / REC-DOM-Level-3-Core-20040407'> DOM Level 3 Core </a>],内部子
     * 集的处理总是完成,即使此参数设置为<code> false </code> </dd>。
     * <dt>
     * <code>"validate-if-schema"</code> </dt> <dd>有关此参数的描述,请参阅<code> DOMConfiguration </code>的定义。
     * 与[<a href ='http： / wwww3org / TR / 2004 / REC-DOM-Level-3-Core-20040407'> DOM Level 3 Core </a>],内部子
     * 集的处理总是完成,即使此参数设置为<code> false </code> </dd>。
     * <code>"validate-if-schema"</code> </dt> <dd>有关此参数的描述,请参阅<code> DOMConfiguration </code>的定义。
     * <dt>
     *  <code>"well-formed"</code> </dt> <dd>有关此参数的描述,请参阅<code> DOMConfiguration </code>的定义与[<a href ='http：// wwww3org / TR / 2004 / REC-DOM-Level-3-Core-20040407'>
     *  DOM Level 3 Core </a>],此参数不能设置为<code> false </code> </dd>。
     */
    public DOMConfiguration getDomConfig();

    /**
     *  When a filter is provided, the implementation will call out to the
     * filter as it is constructing the DOM tree structure. The filter can
     * choose to remove elements from the document being constructed, or to
     * terminate the parsing early.
     * <br> The filter is invoked after the operations requested by the
     * <code>DOMConfiguration</code> parameters have been applied. For
     * example, if "<a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#parameter-validate'>
     * validate</a>" is set to <code>true</code>, the validation is done before invoking the
     * filter.
     * <p>
     * </dl>
     */
    public LSParserFilter getFilter();
    /**
     *  When a filter is provided, the implementation will call out to the
     * filter as it is constructing the DOM tree structure. The filter can
     * choose to remove elements from the document being constructed, or to
     * terminate the parsing early.
     * <br> The filter is invoked after the operations requested by the
     * <code>DOMConfiguration</code> parameters have been applied. For
     * example, if "<a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#parameter-validate'>
     * validate</a>" is set to <code>true</code>, the validation is done before invoking the
     * filter.
     * <p>
     * 当提供了过滤器时,实现将调用过滤器,因为它正在构造DOM树结构过滤器可以选择从正在构建的文档中删除元素,或者尽早终止解析<br>过滤器在已应用由<code> DOMConfiguration </code>
     * 参数请求的操作例如,如果"<a href='http://wwww3org/TR/DOM-Level-3-Core/corehtml#parameter-validate'>验证</a>"设置为<code>
     *  true </code>,则验证在调用过滤器之前完成。
     * 
     */
    public void setFilter(LSParserFilter filter);

    /**
     *  <code>true</code> if the <code>LSParser</code> is asynchronous,
     * <code>false</code> if it is synchronous.
     * <p>
     * 当提供了过滤器时,实现将调用过滤器,因为它正在构造DOM树结构过滤器可以选择从正在构建的文档中删除元素,或者尽早终止解析<br>过滤器在已应用由<code> DOMConfiguration </code>
     * 参数请求的操作例如,如果"<a href='http://wwww3org/TR/DOM-Level-3-Core/corehtml#parameter-validate'>验证</a>"设置为<code>
     *  true </code>,则验证在调用过滤器之前完成。
     * 
     */
    public boolean getAsync();

    /**
     *  <code>true</code> if the <code>LSParser</code> is currently busy
     * loading a document, otherwise <code>false</code>.
     * <p>
     *  <code> true </code>如果<code> LSParser </code>是异步的,<code> false </code>
     * 
     */
    public boolean getBusy();

    /**
     * Parse an XML document from a resource identified by a
     * <code>LSInput</code>.
     * <p>
     *  <code> true </code>如果<code> LSParser </code>当前正在加载文档,<code> false </code>
     * 
     * 
     * @param input  The <code>LSInput</code> from which the source of the
     *   document is to be read.
     * @return  If the <code>LSParser</code> is a synchronous
     *   <code>LSParser</code>, the newly created and populated
     *   <code>Document</code> is returned. If the <code>LSParser</code> is
     *   asynchronous, <code>null</code> is returned since the document
     *   object may not yet be constructed when this method returns.
     * @exception DOMException
     *    INVALID_STATE_ERR: Raised if the <code>LSParser</code>'s
     *   <code>LSParser.busy</code> attribute is <code>true</code>.
     * @exception LSException
     *    PARSE_ERR: Raised if the <code>LSParser</code> was unable to load
     *   the XML document. DOM applications should attach a
     *   <code>DOMErrorHandler</code> using the parameter "<a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#parameter-error-handler'>
     *   error-handler</a>" if they wish to get details on the error.
     */
    public Document parse(LSInput input)
                          throws DOMException, LSException;

    /**
     *  Parse an XML document from a location identified by a URI reference [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]. If the URI
     * contains a fragment identifier (see section 4.1 in [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]), the
     * behavior is not defined by this specification, future versions of
     * this specification may define the behavior.
     * <p>
     * 从由<code> LSInput </code>标识的资源解析XML文档
     * 
     * 
     * @param uri The location of the XML document to be read.
     * @return  If the <code>LSParser</code> is a synchronous
     *   <code>LSParser</code>, the newly created and populated
     *   <code>Document</code> is returned, or <code>null</code> if an error
     *   occured. If the <code>LSParser</code> is asynchronous,
     *   <code>null</code> is returned since the document object may not yet
     *   be constructed when this method returns.
     * @exception DOMException
     *    INVALID_STATE_ERR: Raised if the <code>LSParser.busy</code>
     *   attribute is <code>true</code>.
     * @exception LSException
     *    PARSE_ERR: Raised if the <code>LSParser</code> was unable to load
     *   the XML document. DOM applications should attach a
     *   <code>DOMErrorHandler</code> using the parameter "<a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#parameter-error-handler'>
     *   error-handler</a>" if they wish to get details on the error.
     */
    public Document parseURI(String uri)
                             throws DOMException, LSException;

    // ACTION_TYPES
    /**
     *  Append the result of the parse operation as children of the context
     * node. For this action to work, the context node must be an
     * <code>Element</code> or a <code>DocumentFragment</code>.
     * <p>
     *  从由URI引用标识的位置解析XML文档[<a href='http://wwwietforg/rfc/rfc2396txt'> IETF RFC 2396 </a>]如果URI包含片段标识符(请参阅[
     *  <a href='http://wwwietforg/rfc/rfc2396txt'> IETF RFC 2396 </a>]),行为未由此规范定义,本规范的未来版本可能定义行为。
     * 
     */
    public static final short ACTION_APPEND_AS_CHILDREN = 1;
    /**
     *  Replace all the children of the context node with the result of the
     * parse operation. For this action to work, the context node must be an
     * <code>Element</code>, a <code>Document</code>, or a
     * <code>DocumentFragment</code>.
     * <p>
     *  将解析操作的结果作为上下文节点的子节点附加为了使该动作起作用,上下文节点必须是<code> Element </code>或<code> DocumentFragment </code>
     * 
     */
    public static final short ACTION_REPLACE_CHILDREN   = 2;
    /**
     *  Insert the result of the parse operation as the immediately preceding
     * sibling of the context node. For this action to work the context
     * node's parent must be an <code>Element</code> or a
     * <code>DocumentFragment</code>.
     * <p>
     * 使用解析操作的结果替换上下文节点的所有子节点。
     * 要使此操作正常工作,上下文节点必须是<code> Element </code>,<code> Document </code>或<code > DocumentFragment </code>。
     * 
     */
    public static final short ACTION_INSERT_BEFORE      = 3;
    /**
     *  Insert the result of the parse operation as the immediately following
     * sibling of the context node. For this action to work the context
     * node's parent must be an <code>Element</code> or a
     * <code>DocumentFragment</code>.
     * <p>
     *  将解析操作的结果作为上下文节点的前一个兄弟节点插入。对于此操作,上下文节点的父代必须是<code> Element </code>或<code> DocumentFragment </code>
     * 
     */
    public static final short ACTION_INSERT_AFTER       = 4;
    /**
     *  Replace the context node with the result of the parse operation. For
     * this action to work, the context node must have a parent, and the
     * parent must be an <code>Element</code> or a
     * <code>DocumentFragment</code>.
     * <p>
     *  将解析操作的结果作为上下文节点的紧接在后面的兄弟节点插入。
     * 对于此操作,上下文节点的父代必须是<code> Element </code>或<code> DocumentFragment </code>。
     * 
     */
    public static final short ACTION_REPLACE            = 5;

    /**
     *  Parse an XML fragment from a resource identified by a
     * <code>LSInput</code> and insert the content into an existing document
     * at the position specified with the <code>context</code> and
     * <code>action</code> arguments. When parsing the input stream, the
     * context node (or its parent, depending on where the result will be
     * inserted) is used for resolving unbound namespace prefixes. The
     * context node's <code>ownerDocument</code> node (or the node itself if
     * the node of type <code>DOCUMENT_NODE</code>) is used to resolve
     * default attributes and entity references.
     * <br> As the new data is inserted into the document, at least one
     * mutation event is fired per new immediate child or sibling of the
     * context node.
     * <br> If the context node is a <code>Document</code> node and the action
     * is <code>ACTION_REPLACE_CHILDREN</code>, then the document that is
     * passed as the context node will be changed such that its
     * <code>xmlEncoding</code>, <code>documentURI</code>,
     * <code>xmlVersion</code>, <code>inputEncoding</code>,
     * <code>xmlStandalone</code>, and all other such attributes are set to
     * what they would be set to if the input source was parsed using
     * <code>LSParser.parse()</code>.
     * <br> This method is always synchronous, even if the
     * <code>LSParser</code> is asynchronous (<code>LSParser.async</code> is
     * <code>true</code>).
     * <br> If an error occurs while parsing, the caller is notified through
     * the <code>ErrorHandler</code> instance associated with the "<a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#parameter-error-handler'>
     * error-handler</a>" parameter of the <code>DOMConfiguration</code>.
     * <br> When calling <code>parseWithContext</code>, the values of the
     * following configuration parameters will be ignored and their default
     * values will always be used instead: "<a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#parameter-validate'>
     * validate</a>", "<a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#parameter-validate-if-schema'>
     * validate-if-schema</a>", and "<a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#parameter-element-content-whitespace'>
     * element-content-whitespace</a>". Other parameters will be treated normally, and the parser is expected
     * to call the <code>LSParserFilter</code> just as if a whole document
     * was parsed.
     * <p>
     * 使用解析操作的结果替换上下文节点要使此操作起作用,上下文节点必须具有父级,父级必须是<code> Element </code>或<code> DocumentFragment </code>
     * 
     * 
     * @param input  The <code>LSInput</code> from which the source document
     *   is to be read. The source document must be an XML fragment, i.e.
     *   anything except a complete XML document (except in the case where
     *   the context node of type <code>DOCUMENT_NODE</code>, and the action
     *   is <code>ACTION_REPLACE_CHILDREN</code>), a DOCTYPE (internal
     *   subset), entity declaration(s), notation declaration(s), or XML or
     *   text declaration(s).
     * @param contextArg  The node that is used as the context for the data
     *   that is being parsed. This node must be a <code>Document</code>
     *   node, a <code>DocumentFragment</code> node, or a node of a type
     *   that is allowed as a child of an <code>Element</code> node, e.g. it
     *   cannot be an <code>Attribute</code> node.
     * @param action  This parameter describes which action should be taken
     *   between the new set of nodes being inserted and the existing
     *   children of the context node. The set of possible actions is
     *   defined in <code>ACTION_TYPES</code> above.
     * @return  Return the node that is the result of the parse operation. If
     *   the result is more than one top-level node, the first one is
     *   returned.
     * @exception DOMException
     *   HIERARCHY_REQUEST_ERR: Raised if the content cannot replace, be
     *   inserted before, after, or as a child of the context node (see also
     *   <code>Node.insertBefore</code> or <code>Node.replaceChild</code> in [<a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>DOM Level 3 Core</a>]
     *   ).
     *   <br> NOT_SUPPORTED_ERR: Raised if the <code>LSParser</code> doesn't
     *   support this method, or if the context node is of type
     *   <code>Document</code> and the DOM implementation doesn't support
     *   the replacement of the <code>DocumentType</code> child or
     *   <code>Element</code> child.
     *   <br> NO_MODIFICATION_ALLOWED_ERR: Raised if the context node is a
     *   read only node and the content is being appended to its child list,
     *   or if the parent node of the context node is read only node and the
     *   content is being inserted in its child list.
     *   <br> INVALID_STATE_ERR: Raised if the <code>LSParser.busy</code>
     *   attribute is <code>true</code>.
     * @exception LSException
     *    PARSE_ERR: Raised if the <code>LSParser</code> was unable to load
     *   the XML fragment. DOM applications should attach a
     *   <code>DOMErrorHandler</code> using the parameter "<a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#parameter-error-handler'>
     *   error-handler</a>" if they wish to get details on the error.
     */
    public Node parseWithContext(LSInput input,
                                 Node contextArg,
                                 short action)
                                 throws DOMException, LSException;

    /**
     *  Abort the loading of the document that is currently being loaded by
     * the <code>LSParser</code>. If the <code>LSParser</code> is currently
     * not busy, a call to this method does nothing.
     * <p>
     * 从由<code> LSInput </code>标识的资源解析XML片段,并将内容插入到现有文档中由<code> context </code>和<code> action </code>参数指定的位置
     * 当解析输入流时,上下文节点(或其父节点,取决于将插入结果的位置)用于解析未绑定的命名空间前缀上下文节点的<code> ownerDocument </code>节点的类型<code> DOCUMENT_
     * NODE </code>)用于解析默认属性和实体引用<br>当新数据插入到文档中时,每个新的直接子节点或上下文节点的兄弟节点触发至少一个突变事件<br>如果上下文节点是<code> Document </code>
     * 节点,并且操作是<code> ACTION_REPLACE_CHILDREN </code>,则作为上下文节点传递的文档将被更改, xmlEncoding </code>,<code> document
     * URI </code>,<code> xmlVersion </code>,<code> inputEncoding </code>,<code> xmlStandalone </code>如果使用<code>
     *  LSParserparse()</code>解析输入源,那么它们将被设置为什么<br>此方法总是同步的,即使<code> LSParser </code>是异步的(<code> LSParserasy
     * nc </code> <code> true </code>)<br>如果解析时出现错误,调用者将通过与<a href ='http： // wwww3org / TR / DOM-Level-3-Core / core<code>
     *  DOMConfiguration </code>的<html#parameter-error-handler'> error-handler </a>参数<br>当调用<code> parseWith
     */
    public void abort();

}
