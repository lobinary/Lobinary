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
 * The <code>Attr</code> interface represents an attribute in an
 * <code>Element</code> object. Typically the allowable values for the
 * attribute are defined in a schema associated with the document.
 * <p><code>Attr</code> objects inherit the <code>Node</code> interface, but
 * since they are not actually child nodes of the element they describe, the
 * DOM does not consider them part of the document tree. Thus, the
 * <code>Node</code> attributes <code>parentNode</code>,
 * <code>previousSibling</code>, and <code>nextSibling</code> have a
 * <code>null</code> value for <code>Attr</code> objects. The DOM takes the
 * view that attributes are properties of elements rather than having a
 * separate identity from the elements they are associated with; this should
 * make it more efficient to implement such features as default attributes
 * associated with all elements of a given type. Furthermore,
 * <code>Attr</code> nodes may not be immediate children of a
 * <code>DocumentFragment</code>. However, they can be associated with
 * <code>Element</code> nodes contained within a
 * <code>DocumentFragment</code>. In short, users and implementors of the
 * DOM need to be aware that <code>Attr</code> nodes have some things in
 * common with other objects inheriting the <code>Node</code> interface, but
 * they also are quite distinct.
 * <p>The attribute's effective value is determined as follows: if this
 * attribute has been explicitly assigned any value, that value is the
 * attribute's effective value; otherwise, if there is a declaration for
 * this attribute, and that declaration includes a default value, then that
 * default value is the attribute's effective value; otherwise, the
 * attribute does not exist on this element in the structure model until it
 * has been explicitly added. Note that the <code>Node.nodeValue</code>
 * attribute on the <code>Attr</code> instance can also be used to retrieve
 * the string version of the attribute's value(s).
 * <p> If the attribute was not explicitly given a value in the instance
 * document but has a default value provided by the schema associated with
 * the document, an attribute node will be created with
 * <code>specified</code> set to <code>false</code>. Removing attribute
 * nodes for which a default value is defined in the schema generates a new
 * attribute node with the default value and <code>specified</code> set to
 * <code>false</code>. If validation occurred while invoking
 * <code>Document.normalizeDocument()</code>, attribute nodes with
 * <code>specified</code> equals to <code>false</code> are recomputed
 * according to the default attribute values provided by the schema. If no
 * default value is associate with this attribute in the schema, the
 * attribute node is discarded.
 * <p>In XML, where the value of an attribute can contain entity references,
 * the child nodes of the <code>Attr</code> node may be either
 * <code>Text</code> or <code>EntityReference</code> nodes (when these are
 * in use; see the description of <code>EntityReference</code> for
 * discussion).
 * <p>The DOM Core represents all attribute values as simple strings, even if
 * the DTD or schema associated with the document declares them of some
 * specific type such as tokenized.
 * <p>The way attribute value normalization is performed by the DOM
 * implementation depends on how much the implementation knows about the
 * schema in use. Typically, the <code>value</code> and
 * <code>nodeValue</code> attributes of an <code>Attr</code> node initially
 * returns the normalized value given by the parser. It is also the case
 * after <code>Document.normalizeDocument()</code> is called (assuming the
 * right options have been set). But this may not be the case after
 * mutation, independently of whether the mutation is performed by setting
 * the string value directly or by changing the <code>Attr</code> child
 * nodes. In particular, this is true when <a href='http://www.w3.org/TR/2004/REC-xml-20040204#dt-charref'>character
 * references</a> are involved, given that they are not represented in the DOM and they
 * impact attribute value normalization. On the other hand, if the
 * implementation knows about the schema in use when the attribute value is
 * changed, and it is of a different type than CDATA, it may normalize it
 * again at that time. This is especially true of specialized DOM
 * implementations, such as SVG DOM implementations, which store attribute
 * values in an internal form different from a string.
 * <p>The following table gives some examples of the relations between the
 * attribute value in the original document (parsed attribute), the value as
 * exposed in the DOM, and the serialization of the value:
 * <table border='1' cellpadding='3'>
 * <tr>
 * <th>Examples</th>
 * <th>Parsed
 * attribute value</th>
 * <th>Initial <code>Attr.value</code></th>
 * <th>Serialized attribute value</th>
 * </tr>
 * <tr>
 * <td valign='top' rowspan='1' colspan='1'>
 * Character reference</td>
 * <td valign='top' rowspan='1' colspan='1'>
 * <pre>"x&amp;#178;=5"</pre>
 * </td>
 * <td valign='top' rowspan='1' colspan='1'>
 * <pre>"x\u00b2=5"</pre>
 * </td>
 * <td valign='top' rowspan='1' colspan='1'>
 * <pre>"x&amp;#178;=5"</pre>
 * </td>
 * </tr>
 * <tr>
 * <td valign='top' rowspan='1' colspan='1'>Built-in
 * character entity</td>
 * <td valign='top' rowspan='1' colspan='1'>
 * <pre>"y&amp;lt;6"</pre>
 * </td>
 * <td valign='top' rowspan='1' colspan='1'>
 * <pre>"y&lt;6"</pre>
 * </td>
 * <td valign='top' rowspan='1' colspan='1'>
 * <pre>"y&amp;lt;6"</pre>
 * </td>
 * </tr>
 * <tr>
 * <td valign='top' rowspan='1' colspan='1'>Literal newline between</td>
 * <td valign='top' rowspan='1' colspan='1'>
 * <pre>
 * "x=5&amp;#10;y=6"</pre>
 * </td>
 * <td valign='top' rowspan='1' colspan='1'>
 * <pre>"x=5 y=6"</pre>
 * </td>
 * <td valign='top' rowspan='1' colspan='1'>
 * <pre>"x=5&amp;#10;y=6"</pre>
 * </td>
 * </tr>
 * <tr>
 * <td valign='top' rowspan='1' colspan='1'>Normalized newline between</td>
 * <td valign='top' rowspan='1' colspan='1'>
 * <pre>"x=5
 * y=6"</pre>
 * </td>
 * <td valign='top' rowspan='1' colspan='1'>
 * <pre>"x=5 y=6"</pre>
 * </td>
 * <td valign='top' rowspan='1' colspan='1'>
 * <pre>"x=5 y=6"</pre>
 * </td>
 * </tr>
 * <tr>
 * <td valign='top' rowspan='1' colspan='1'>Entity <code>e</code> with literal newline</td>
 * <td valign='top' rowspan='1' colspan='1'>
 * <pre>
 * &lt;!ENTITY e '...&amp;#10;...'&gt; [...]&gt; "x=5&amp;e;y=6"</pre>
 * </td>
 * <td valign='top' rowspan='1' colspan='1'><em>Dependent on Implementation and Load Options</em></td>
 * <td valign='top' rowspan='1' colspan='1'><em>Dependent on Implementation and Load/Save Options</em></td>
 * </tr>
 * </table>
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>Document Object Model (DOM) Level 3 Core Specification</a>.
 * <p>
 * <code> Attr </code>接口代表<code> Element </code>对象中的属性。
 * 通常,属性的允许值在与文档<p> <code> Attr </code >对象继承<code> Node </code>接口,但由于它们实际上不是它们描述的元素的子节点,DOM不认为它们是文档树的一部分
 * 。
 * <code> Attr </code>接口代表<code> Element </code>对象中的属性。
 * 因此,<code> Node </code> <code> attr </code>对象的<code> parentNode </code>,<code> previousSibling </code>
 * 和<code> nextSibling </code>DOM认为属性是元素的属性,而不是与它们相关联的元素具有单独的标识;这将使得更有效地实现与给定类型的所有元素相关联的默认属性这样的特征。
 * <code> Attr </code>接口代表<code> Element </code>对象中的属性。
 * 此外,<code> Attr </code>节点可能不是<code> DocumentFragment </code>的直接子节点。
 * 可以与<code> DocumentFragment </code>中包含的<code>元素</code>节点相关联。
 * 简而言之,DOM的用户和实现者需要知道<code> Attr </code>与继承<code> Node </code>接口的其他对象相同,但它们也是相当不同的<p>属性的有效值确定如下：如果此属性已显
 * 式分配任何值,则该值是属性的有效值;否则,如果此属性有声明,并且该声明包括默认值,则该默认值是属性的有效值;否则,该属性在结构模型中的此元素上不存在,直到它被显式地添加注意,在<code> Attr </code>
 * 实例上的<code> NodenodeValue </code>属性也可以用于检索属性值的字符串版本<p>如果属性没有在实例文档中显式地给定一个值,但是具有由与文档相关联的模式提供的默认值,则将创建一个
 * 属性节点,其中<code>指定</code>设置为<code> false </code>删除在架构中定义了默认值的属性节点生成具有默认值和<code>指定</code>的新属性节点</code>设置为
 * <code> false </code>调用<code> DocumentnormalizeDocument()</code>,根据由模式提供的默认属性值重新计算具有<code>指定</code>等于<code>
 *  false </code>的属性节点如果没有默认值在模式中与此属性关联,属性节点将被丢弃<p>在XML中,如果属性的值可以包含实体引用,则<code> Attr </code>节点的子节点可以是<code>
 *  Text </code>或<code> EntityReference </code >节点(当这些正在使用时;参见<code> EntityReference </code>的说明)<p> DOM 
 * Core将所有属性值表示为简单字符串,即使与文档相关联的DTD或模式声明它们DOM实现方法属性值归一化的方式取决于实现对使用的模式的了解程度通常,<code> value </code>和<code> 
 * nodeValue < / code> <code> Attr </code>节点的属性最初返回由解析器给出的归一化值这也是在调用<code> DocumentnormalizeDocument()</code>
 * 之后的情况(假设已经设置了正确的选项),但是这可能不是突变后的情况,与突变是通过直接设置字符串值或通过更改<code> Attr </code>子节点。
 * 可以与<code> DocumentFragment </code>中包含的<code>元素</code>节点相关联。
 * 特别是,当<a href='http://wwww3org/TR/2004/REC-xml-20040204#dt-charref'>字符引用< / a>,因为它们不在DOM中表示,并且它们影响属性值规
 * 范化。
 * 可以与<code> DocumentFragment </code>中包含的<code>元素</code>节点相关联。
 * 另一方面,如果实现在属性值改变时知道正在使用的模式,并且它的类型不同于CDATA,它可能在那时再次正常化这对于诸如SVG DOM实现的专用DOM实现尤其如此,其以不同于字符串<p>的内部形式存储属性值。
 * 可以与<code> DocumentFragment </code>中包含的<code>元素</code>节点相关联。
 * 下表给出了原始文档中的属性值之间的关系的一些示例(解析的属性),DOM中公开的值,以及值的序列化：。
 * <table border='1' cellpadding='3'>
 * <tr>
 * <th>示例</th> <th>解析的属性值</th> <th>初始<code> Attrvalue </code> </th> <th>序列化属性值</th>
 * </tr>
 * <tr>
 * <td valign='top' rowspan='1' colspan='1'>
 *  字符引用</td>
 * <td valign='top' rowspan='1' colspan='1'>
 *  <pre>"x&amp;#178; = 5"</pre>
 * </td>
 * <td valign='top' rowspan='1' colspan='1'>
 *  <pre>"x \\ u00b2 = 5"</pre>
 * </td>
 * <td valign='top' rowspan='1' colspan='1'>
 *  <pre>"x&amp;#178; = 5"</pre>
 * </td>
 * </tr>
 * <tr>
 *  <td valign ='top'rowspan ='1'colspan ='1'>内置字符实体</td>
 * <td valign='top' rowspan='1' colspan='1'>
 *  <pre>"y&amp; lt; 6"</pre>
 * </td>
 * <td valign='top' rowspan='1' colspan='1'>
 *  <pre>"y <6"</pre>
 * </td>
 * <td valign='top' rowspan='1' colspan='1'>
 *  <pre>"y&amp; lt; 6"</pre>
 * </td>
 * </tr>
 * <tr>
 *  <td valign ='top'rowspan ='1'colspan ='1'>之间的文字换行符</td>
 * <td valign='top' rowspan='1' colspan='1'>
 * <pre>
 *  "x = 5&amp;#10; y = 6"</pre>
 * </td>
 * <td valign='top' rowspan='1' colspan='1'>
 *  <pre>"x = 5 y = 6"</pre>
 * </td>
 * <td valign='top' rowspan='1' colspan='1'>
 *  <pre>"x = 5&amp;#10; y = 6"</pre>
 * </td>
 * </tr>
 * <tr>
 *  <td valign ='top'rowspan ='1'colspan ='1'>标准化的换行符</td>
 * <td valign='top' rowspan='1' colspan='1'>
 */
public interface Attr extends Node {
    /**
     * Returns the name of this attribute. If <code>Node.localName</code> is
     * different from <code>null</code>, this attribute is a qualified name.
     * <p>
     *  <pre>"x = 5 y = 6"</pre>
     * </td>
     * <td valign='top' rowspan='1' colspan='1'>
     *  <pre>"x = 5 y = 6"</pre>
     * </td>
     * <td valign='top' rowspan='1' colspan='1'>
     *  <pre>"x = 5 y = 6"</pre>
     * </td>
     * </tr>
     * <tr>
     *  <td valign ='top'rowspan ='1'colspan ='1'>实体<code> e </code>
     * <td valign='top' rowspan='1' colspan='1'>
     * <pre>
     * &lt;！ENTITY e'&amp;#10;'&gt; [] "x = 5&amp; e; y = 6"</pre>
     * </td>
     *  <td valign ='top'rowspan ='1'colspan ='1'> <em>取决于实现和加载选项</td> <td valign ='top'rowspan ='1'colspan = 1'>
     *  <em>取决于实现和加载/保存选项</em> </td>。
     * </tr>
     * </table>
     *  <p>另请参阅<a href='http://wwww3org/TR/2004/REC-DOM-Level-3-Core-20040407'>文档对象模型(DOM)3级核心规范</a>
     * 
     */
    public String getName();

    /**
     *  <code>True</code> if this attribute was explicitly given a value in
     * the instance document, <code>false</code> otherwise. If the
     * application changed the value of this attribute node (even if it ends
     * up having the same value as the default value) then it is set to
     * <code>true</code>. The implementation may handle attributes with
     * default values from other schemas similarly but applications should
     * use <code>Document.normalizeDocument()</code> to guarantee this
     * information is up-to-date.
     * <p>
     *  返回此属性的名称如果<code> NodelocalName </code>与<code> null </code>不同,则此属性是限定名称
     * 
     */
    public boolean getSpecified();

    /**
     * On retrieval, the value of the attribute is returned as a string.
     * Character and general entity references are replaced with their
     * values. See also the method <code>getAttribute</code> on the
     * <code>Element</code> interface.
     * <br>On setting, this creates a <code>Text</code> node with the unparsed
     * contents of the string, i.e. any characters that an XML processor
     * would recognize as markup are instead treated as literal text. See
     * also the method <code>Element.setAttribute()</code>.
     * <br> Some specialized implementations, such as some [<a href='http://www.w3.org/TR/2003/REC-SVG11-20030114/'>SVG 1.1</a>]
     * implementations, may do normalization automatically, even after
     * mutation; in such case, the value on retrieval may differ from the
     * value on setting.
     * <p>
     * <code> True </code>如果此属性在实例文档中显式指定了一个值,则<code> false </code>否则如果应用程序更改了此属性节点的值(即使它最终具有相同的值作为默认值)然后它被设
     * 置为<code> true </code>实现可以处理来自其他模式的默认值的属性,但应用程序应该使用<code> DocumentnormalizeDocument()</code> -至今。
     * 
     */
    public String getValue();
    /**
     * On retrieval, the value of the attribute is returned as a string.
     * Character and general entity references are replaced with their
     * values. See also the method <code>getAttribute</code> on the
     * <code>Element</code> interface.
     * <br>On setting, this creates a <code>Text</code> node with the unparsed
     * contents of the string, i.e. any characters that an XML processor
     * would recognize as markup are instead treated as literal text. See
     * also the method <code>Element.setAttribute()</code>.
     * <br> Some specialized implementations, such as some [<a href='http://www.w3.org/TR/2003/REC-SVG11-20030114/'>SVG 1.1</a>]
     * implementations, may do normalization automatically, even after
     * mutation; in such case, the value on retrieval may differ from the
     * value on setting.
     * <p>
     * 在检索时,属性的值作为字符串返回。字符和一般实体引用被它们的值替换。
     * 另见<code> Element </code>接口上的方法<code> getAttribute </code> <br> On设置,这将创建一个带有未解析的字符串内容的<code> Text </code>
     * 节点,即XML处理器识别为标记的任何字符将被视为文本文本另请参见方法<code> ElementsetAttribute / code> <br>某些特定的实现,例如某些[<a href='http://wwww3org/TR/2003/REC-SVG11-20030114/'>
     *  SVG 11 </a>]实现,可以自动进行规范化,甚至突变后;在这种情况下,检索时的值可能与设置时的值不同。
     * 在检索时,属性的值作为字符串返回。字符和一般实体引用被它们的值替换。
     * 
     * 
     * @exception DOMException
     *   NO_MODIFICATION_ALLOWED_ERR: Raised when the node is readonly.
     */
    public void setValue(String value)
                            throws DOMException;

    /**
     * The <code>Element</code> node this attribute is attached to or
     * <code>null</code> if this attribute is not in use.
     * <p>
     * 在检索时,属性的值作为字符串返回。字符和一般实体引用被它们的值替换。
     * 另见<code> Element </code>接口上的方法<code> getAttribute </code> <br> On设置,这将创建一个带有未解析的字符串内容的<code> Text </code>
     * 节点,即XML处理器识别为标记的任何字符将被视为文本文本另请参见方法<code> ElementsetAttribute / code> <br>某些特定的实现,例如某些[<a href='http://wwww3org/TR/2003/REC-SVG11-20030114/'>
     *  SVG 11 </a>]实现,可以自动进行规范化,甚至突变后;在这种情况下,检索时的值可能与设置时的值不同。
     * 在检索时,属性的值作为字符串返回。字符和一般实体引用被它们的值替换。
     * 
     * 
     * @since DOM Level 2
     */
    public Element getOwnerElement();

    /**
     *  The type information associated with this attribute. While the type
     * information contained in this attribute is guarantee to be correct
     * after loading the document or invoking
     * <code>Document.normalizeDocument()</code>, <code>schemaTypeInfo</code>
     *  may not be reliable if the node was moved.
     * <p>
     * 如果此属性未使用,则此属性附加到<code> Element </code>节点或<code> null </code>
     * 
     * 
     * @since DOM Level 3
     */
    public TypeInfo getSchemaTypeInfo();

    /**
     *  Returns whether this attribute is known to be of type ID (i.e. to
     * contain an identifier for its owner element) or not. When it is and
     * its value is unique, the <code>ownerElement</code> of this attribute
     * can be retrieved using the method <code>Document.getElementById</code>
     * . The implementation could use several ways to determine if an
     * attribute node is known to contain an identifier:
     * <ul>
     * <li> If validation
     * occurred using an XML Schema [<a href='http://www.w3.org/TR/2001/REC-xmlschema-1-20010502/'>XML Schema Part 1</a>]
     *  while loading the document or while invoking
     * <code>Document.normalizeDocument()</code>, the post-schema-validation
     * infoset contributions (PSVI contributions) values are used to
     * determine if this attribute is a schema-determined ID attribute using
     * the <a href='http://www.w3.org/TR/2003/REC-xptr-framework-20030325/#term-sdi'>
     * schema-determined ID</a> definition in [<a href='http://www.w3.org/TR/2003/REC-xptr-framework-20030325/'>XPointer</a>]
     * .
     * </li>
     * <li> If validation occurred using a DTD while loading the document or
     * while invoking <code>Document.normalizeDocument()</code>, the infoset <b>[type definition]</b> value is used to determine if this attribute is a DTD-determined ID
     * attribute using the <a href='http://www.w3.org/TR/2003/REC-xptr-framework-20030325/#term-ddi'>
     * DTD-determined ID</a> definition in [<a href='http://www.w3.org/TR/2003/REC-xptr-framework-20030325/'>XPointer</a>]
     * .
     * </li>
     * <li> from the use of the methods <code>Element.setIdAttribute()</code>,
     * <code>Element.setIdAttributeNS()</code>, or
     * <code>Element.setIdAttributeNode()</code>, i.e. it is an
     * user-determined ID attribute;
     * <p ><b>Note:</b>  XPointer framework (see section 3.2 in [<a href='http://www.w3.org/TR/2003/REC-xptr-framework-20030325/'>XPointer</a>]
     * ) consider the DOM user-determined ID attribute as being part of the
     * XPointer externally-determined ID definition.
     * </li>
     * <li> using mechanisms that
     * are outside the scope of this specification, it is then an
     * externally-determined ID attribute. This includes using schema
     * languages different from XML schema and DTD.
     * </li>
     * </ul>
     * <br> If validation occurred while invoking
     * <code>Document.normalizeDocument()</code>, all user-determined ID
     * attributes are reset and all attribute nodes ID information are then
     * reevaluated in accordance to the schema used. As a consequence, if
     * the <code>Attr.schemaTypeInfo</code> attribute contains an ID type,
     * <code>isId</code> will always return true.
     * <p>
     *  与此属性相关联的类型信息虽然此属性中包含的类型信息在加载文档或调用<code> DocumentnormalizeDocument()</code>后保证正确,但<code> schemaTypeIn
     * fo </code>可能不可靠节点已移动。
     * 
     * 
     * @since DOM Level 3
     */
    public boolean isId();

}
