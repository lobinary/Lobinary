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
 * The <code>Document</code> interface represents the entire HTML or XML
 * document. Conceptually, it is the root of the document tree, and provides
 * the primary access to the document's data.
 * <p>Since elements, text nodes, comments, processing instructions, etc.
 * cannot exist outside the context of a <code>Document</code>, the
 * <code>Document</code> interface also contains the factory methods needed
 * to create these objects. The <code>Node</code> objects created have a
 * <code>ownerDocument</code> attribute which associates them with the
 * <code>Document</code> within whose context they were created.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>Document Object Model (DOM) Level 3 Core Specification</a>.
 * <p>
 * <code> Document </code>接口表示整个HTML或XML文档概念上,它是文档树的根,并提供对文档数据的主访问<p>由于元素,文本节点,注释,处理指令,etc不能存在于<code> Do
 * cument </code>的上下文之外,<code> Document </code>接口也包含创建这些对象所需的工厂方法。
 * 创建的<code> Node </code>一个<code> ownerDocument </code>属性,它将它们与<code> Document </code>中的<code>文档相关联<p>另请
 * 参阅<a href ='http：// wwww3org / TR / REC-DOM-Level-3-Core-20040407'>文档对象模型(DOM)3级核心规范</a>。
 * 
 */
public interface Document extends Node {
    /**
     * The Document Type Declaration (see <code>DocumentType</code>)
     * associated with this document. For XML documents without a document
     * type declaration this returns <code>null</code>. For HTML documents,
     * a <code>DocumentType</code> object may be returned, independently of
     * the presence or absence of document type declaration in the HTML
     * document.
     * <br>This provides direct access to the <code>DocumentType</code> node,
     * child node of this <code>Document</code>. This node can be set at
     * document creation time and later changed through the use of child
     * nodes manipulation methods, such as <code>Node.insertBefore</code>,
     * or <code>Node.replaceChild</code>. Note, however, that while some
     * implementations may instantiate different types of
     * <code>Document</code> objects supporting additional features than the
     * "Core", such as "HTML" [<a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>DOM Level 2 HTML</a>]
     * , based on the <code>DocumentType</code> specified at creation time,
     * changing it afterwards is very unlikely to result in a change of the
     * features supported.
     *
     * <p>
     * 与此文档关联的文档类型声明(参见<code> DocumentType </code>)对于没有文档类型声明的XML文档,返回<code> null </code>对于HTML文档,<code> Doc
     * umentType </code>对象可以返回,而与HTML文档中存在或不存在文档类型声明无关<br>这提供对<code> DocumentType </code>节点,该<code>文档</code>
     * 子节点的直接访问,该节点可以在文档创建时设置,并且随后通过使用子节点处理方法(例如<code> NodeinsertBefore </code>或<code> NodereplaceChild </code>
     * 然而,注意,尽管一些实现可以实例化支持除了"Core"之外的附加特征的不同类型的<code> Document </code>对象,例如"HTML"[<a href ='http：// wwww3org / TR / 2003 / REC-DOM-Level-2-HTML-20030109'>
     *  DOM Level 2 HTML </a>],基于在创建时指定的<code> DocumentType </code>,以后更改它不太可能导致支持的功能的更改。
     * 
     * 
     * @since DOM Level 3
     */
    public DocumentType getDoctype();

    /**
     * The <code>DOMImplementation</code> object that handles this document. A
     * DOM application may use objects from multiple implementations.
     * <p>
     * 处理此文档的<code> DOMImplementation </code>对象DOM应用程序可以使用多个实现中的对象
     * 
     */
    public DOMImplementation getImplementation();

    /**
     * This is a convenience attribute that allows direct access to the child
     * node that is the document element of the document.
     * <p>
     *  这是一个方便的属性,允许直接访问作为文档的文档元素的子节点
     * 
     */
    public Element getDocumentElement();

    /**
     * Creates an element of the type specified. Note that the instance
     * returned implements the <code>Element</code> interface, so attributes
     * can be specified directly on the returned object.
     * <br>In addition, if there are known attributes with default values,
     * <code>Attr</code> nodes representing them are automatically created
     * and attached to the element.
     * <br>To create an element with a qualified name and namespace URI, use
     * the <code>createElementNS</code> method.
     * <p>
     *  创建指定类型的元素注意,返回的实例实现了<code> Element </code>接口,因此可以直接在返回的对象上指定属性<br>此外,如果存在具有默认值的已知属性,代码> Attr </code>
     * 代表的节点将自动创建并附加到元素<br>要创建具有限定名称和命名空间URI的元素,请使用<code> createElementNS </code>方法。
     * 
     * 
     * @param tagName The name of the element type to instantiate. For XML,
     *   this is case-sensitive, otherwise it depends on the
     *   case-sensitivity of the markup language in use. In that case, the
     *   name is mapped to the canonical form of that markup by the DOM
     *   implementation.
     * @return A new <code>Element</code> object with the
     *   <code>nodeName</code> attribute set to <code>tagName</code>, and
     *   <code>localName</code>, <code>prefix</code>, and
     *   <code>namespaceURI</code> set to <code>null</code>.
     * @exception DOMException
     *   INVALID_CHARACTER_ERR: Raised if the specified name is not an XML
     *   name according to the XML version in use specified in the
     *   <code>Document.xmlVersion</code> attribute.
     */
    public Element createElement(String tagName)
                                 throws DOMException;

    /**
     * Creates an empty <code>DocumentFragment</code> object.
     * <p>
     *  创建一个空的<code> DocumentFragment </code>对象
     * 
     * 
     * @return A new <code>DocumentFragment</code>.
     */
    public DocumentFragment createDocumentFragment();

    /**
     * Creates a <code>Text</code> node given the specified string.
     * <p>
     * 给定指定的字符串,创建一个<code> Text </code>节点
     * 
     * 
     * @param data The data for the node.
     * @return The new <code>Text</code> object.
     */
    public Text createTextNode(String data);

    /**
     * Creates a <code>Comment</code> node given the specified string.
     * <p>
     *  给定指定的字符串,创建一个<code> Comment </code>节点
     * 
     * 
     * @param data The data for the node.
     * @return The new <code>Comment</code> object.
     */
    public Comment createComment(String data);

    /**
     * Creates a <code>CDATASection</code> node whose value is the specified
     * string.
     * <p>
     *  创建其值为指定字符串的<code> CDATASection </code>节点
     * 
     * 
     * @param data The data for the <code>CDATASection</code> contents.
     * @return The new <code>CDATASection</code> object.
     * @exception DOMException
     *   NOT_SUPPORTED_ERR: Raised if this document is an HTML document.
     */
    public CDATASection createCDATASection(String data)
                                           throws DOMException;

    /**
     * Creates a <code>ProcessingInstruction</code> node given the specified
     * name and data strings.
     * <p>
     *  给定指定的名称和数据字符串,创建一个<code> ProcessingInstruction </code>节点
     * 
     * 
     * @param target The target part of the processing instruction.Unlike
     *   <code>Document.createElementNS</code> or
     *   <code>Document.createAttributeNS</code>, no namespace well-formed
     *   checking is done on the target name. Applications should invoke
     *   <code>Document.normalizeDocument()</code> with the parameter "
     *   namespaces" set to <code>true</code> in order to ensure that the
     *   target name is namespace well-formed.
     * @param data The data for the node.
     * @return The new <code>ProcessingInstruction</code> object.
     * @exception DOMException
     *   INVALID_CHARACTER_ERR: Raised if the specified target is not an XML
     *   name according to the XML version in use specified in the
     *   <code>Document.xmlVersion</code> attribute.
     *   <br>NOT_SUPPORTED_ERR: Raised if this document is an HTML document.
     */
    public ProcessingInstruction createProcessingInstruction(String target,
                                                             String data)
                                                             throws DOMException;

    /**
     * Creates an <code>Attr</code> of the given name. Note that the
     * <code>Attr</code> instance can then be set on an <code>Element</code>
     * using the <code>setAttributeNode</code> method.
     * <br>To create an attribute with a qualified name and namespace URI, use
     * the <code>createAttributeNS</code> method.
     * <p>
     *  创建给定名称的<code> Attr </code>请注意,然后可以使用<code> setAttributeNode </code>在<code> Element </code>方法<br>要创建具
     * 有限定名称和命名空间URI的属性,请使用<code> createAttributeNS </code>方法。
     * 
     * 
     * @param name The name of the attribute.
     * @return A new <code>Attr</code> object with the <code>nodeName</code>
     *   attribute set to <code>name</code>, and <code>localName</code>,
     *   <code>prefix</code>, and <code>namespaceURI</code> set to
     *   <code>null</code>. The value of the attribute is the empty string.
     * @exception DOMException
     *   INVALID_CHARACTER_ERR: Raised if the specified name is not an XML
     *   name according to the XML version in use specified in the
     *   <code>Document.xmlVersion</code> attribute.
     */
    public Attr createAttribute(String name)
                                throws DOMException;

    /**
     * Creates an <code>EntityReference</code> object. In addition, if the
     * referenced entity is known, the child list of the
     * <code>EntityReference</code> node is made the same as that of the
     * corresponding <code>Entity</code> node.
     * <p ><b>Note:</b> If any descendant of the <code>Entity</code> node has
     * an unbound namespace prefix, the corresponding descendant of the
     * created <code>EntityReference</code> node is also unbound; (its
     * <code>namespaceURI</code> is <code>null</code>). The DOM Level 2 and
     * 3 do not support any mechanism to resolve namespace prefixes in this
     * case.
     * <p>
     * 创建<code> EntityReference </code>对象此外,如果引用的实体是已知的,则使<code> EntityReference </code>节点的子列表与相应的<code> Ent
     * ity <注意：</b>如果<code> Entity </code>节点的任何后代有一个未绑定的命名空间前缀,则创建的<code> EntityReference </code>节点也不绑定; (它的
     * <code> namespaceURI </code>是<code> null </code>)DOM Level 2和3不支持在这种情况下解析命名空间前缀的任何机制。
     * 
     * 
     * @param name The name of the entity to reference.Unlike
     *   <code>Document.createElementNS</code> or
     *   <code>Document.createAttributeNS</code>, no namespace well-formed
     *   checking is done on the entity name. Applications should invoke
     *   <code>Document.normalizeDocument()</code> with the parameter "
     *   namespaces" set to <code>true</code> in order to ensure that the
     *   entity name is namespace well-formed.
     * @return The new <code>EntityReference</code> object.
     * @exception DOMException
     *   INVALID_CHARACTER_ERR: Raised if the specified name is not an XML
     *   name according to the XML version in use specified in the
     *   <code>Document.xmlVersion</code> attribute.
     *   <br>NOT_SUPPORTED_ERR: Raised if this document is an HTML document.
     */
    public EntityReference createEntityReference(String name)
                                                 throws DOMException;

    /**
     * Returns a <code>NodeList</code> of all the <code>Elements</code> in
     * document order with a given tag name and are contained in the
     * document.
     * <p>
     *  返回具有给定标记名称的文档顺序中所有<code> Elements </code>的<code> NodeList </code>,并包含在文档中
     * 
     * 
     * @param tagname  The name of the tag to match on. The special value "*"
     *   matches all tags. For XML, the <code>tagname</code> parameter is
     *   case-sensitive, otherwise it depends on the case-sensitivity of the
     *   markup language in use.
     * @return A new <code>NodeList</code> object containing all the matched
     *   <code>Elements</code>.
     */
    public NodeList getElementsByTagName(String tagname);

    /**
     * Imports a node from another document to this document, without altering
     * or removing the source node from the original document; this method
     * creates a new copy of the source node. The returned node has no
     * parent; (<code>parentNode</code> is <code>null</code>).
     * <br>For all nodes, importing a node creates a node object owned by the
     * importing document, with attribute values identical to the source
     * node's <code>nodeName</code> and <code>nodeType</code>, plus the
     * attributes related to namespaces (<code>prefix</code>,
     * <code>localName</code>, and <code>namespaceURI</code>). As in the
     * <code>cloneNode</code> operation, the source node is not altered.
     * User data associated to the imported node is not carried over.
     * However, if any <code>UserDataHandlers</code> has been specified
     * along with the associated data these handlers will be called with the
     * appropriate parameters before this method returns.
     * <br>Additional information is copied as appropriate to the
     * <code>nodeType</code>, attempting to mirror the behavior expected if
     * a fragment of XML or HTML source was copied from one document to
     * another, recognizing that the two documents may have different DTDs
     * in the XML case. The following list describes the specifics for each
     * type of node.
     * <dl>
     * <dt>ATTRIBUTE_NODE</dt>
     * <dd>The <code>ownerElement</code> attribute
     * is set to <code>null</code> and the <code>specified</code> flag is
     * set to <code>true</code> on the generated <code>Attr</code>. The
     * descendants of the source <code>Attr</code> are recursively imported
     * and the resulting nodes reassembled to form the corresponding subtree.
     * Note that the <code>deep</code> parameter has no effect on
     * <code>Attr</code> nodes; they always carry their children with them
     * when imported.</dd>
     * <dt>DOCUMENT_FRAGMENT_NODE</dt>
     * <dd>If the <code>deep</code> option
     * was set to <code>true</code>, the descendants of the source
     * <code>DocumentFragment</code> are recursively imported and the
     * resulting nodes reassembled under the imported
     * <code>DocumentFragment</code> to form the corresponding subtree.
     * Otherwise, this simply generates an empty
     * <code>DocumentFragment</code>.</dd>
     * <dt>DOCUMENT_NODE</dt>
     * <dd><code>Document</code>
     * nodes cannot be imported.</dd>
     * <dt>DOCUMENT_TYPE_NODE</dt>
     * <dd><code>DocumentType</code>
     * nodes cannot be imported.</dd>
     * <dt>ELEMENT_NODE</dt>
     * <dd><em>Specified</em> attribute nodes of the source element are imported, and the generated
     * <code>Attr</code> nodes are attached to the generated
     * <code>Element</code>. Default attributes are <em>not</em> copied, though if the document being imported into defines default
     * attributes for this element name, those are assigned. If the
     * <code>importNode</code> <code>deep</code> parameter was set to
     * <code>true</code>, the descendants of the source element are
     * recursively imported and the resulting nodes reassembled to form the
     * corresponding subtree.</dd>
     * <dt>ENTITY_NODE</dt>
     * <dd><code>Entity</code> nodes can be
     * imported, however in the current release of the DOM the
     * <code>DocumentType</code> is readonly. Ability to add these imported
     * nodes to a <code>DocumentType</code> will be considered for addition
     * to a future release of the DOM.On import, the <code>publicId</code>,
     * <code>systemId</code>, and <code>notationName</code> attributes are
     * copied. If a <code>deep</code> import is requested, the descendants
     * of the the source <code>Entity</code> are recursively imported and
     * the resulting nodes reassembled to form the corresponding subtree.</dd>
     * <dt>
     * ENTITY_REFERENCE_NODE</dt>
     * <dd>Only the <code>EntityReference</code> itself is
     * copied, even if a <code>deep</code> import is requested, since the
     * source and destination documents might have defined the entity
     * differently. If the document being imported into provides a
     * definition for this entity name, its value is assigned.</dd>
     * <dt>NOTATION_NODE</dt>
     * <dd>
     * <code>Notation</code> nodes can be imported, however in the current
     * release of the DOM the <code>DocumentType</code> is readonly. Ability
     * to add these imported nodes to a <code>DocumentType</code> will be
     * considered for addition to a future release of the DOM.On import, the
     * <code>publicId</code> and <code>systemId</code> attributes are copied.
     * Note that the <code>deep</code> parameter has no effect on this type
     * of nodes since they cannot have any children.</dd>
     * <dt>
     * PROCESSING_INSTRUCTION_NODE</dt>
     * <dd>The imported node copies its
     * <code>target</code> and <code>data</code> values from those of the
     * source node.Note that the <code>deep</code> parameter has no effect
     * on this type of nodes since they cannot have any children.</dd>
     * <dt>TEXT_NODE,
     * CDATA_SECTION_NODE, COMMENT_NODE</dt>
     * <dd>These three types of nodes inheriting
     * from <code>CharacterData</code> copy their <code>data</code> and
     * <code>length</code> attributes from those of the source node.Note
     * that the <code>deep</code> parameter has no effect on these types of
     * nodes since they cannot have any children.</dd>
     * </dl>
     * <p>
     * 将节点从另一个文档导入到此文档,而不会从原始文档中更改或删除源节点;此方法创建源节点的新副本返回的节点没有父节点; (<code> parentNode </code> <code> null </code>
     * )<br>对于所有节点,导入节点将创建一个由导入文档拥有的节点对象,属性值与源节点的<code> nodeName </code>和<code> nodeType </code>,以及与命名空间相关的属
     * 性(<code> prefix </code>,<code> localName </code>和<code> namespaceURI </code>)如在<code> cloneNode </code>
     * 操作中,源节点不被改变与导入的节点相关联的用户数据不被转移但是,如果已经与相关数据一起指定了任何<code> UserDataHandlers </code>,则在此方法返回之前,将使用适当的参数调用这
     * 些处理程序<br>将附加信息适当复制到<code> nodeType <代码>,尝试镜像将XML或HTML源代码片段从一个文档复制到另一个文档时所期望的行为,认识到这两个文档在XML案例中可能具有不同的
     * DTD以下列表描述了每种类型的节点。
     * <dl>
     * <dt> ATTRIBUTE_NODE </dt> <dd> <code> ownerElement </code>属性设置为<code> null </code>,并且<code>指定</code>标
     * 志设置为<code> true <code> Attr </code>的后代被递归导入,并且所得到的节点被重新组装以形成相应的子树。
     * 注意<code> deep </code> code>参数对<code> Attr </code>节点没有影响;它们在导入时总是携带子元素</dd> </span> </span>如果<code> de
     * ep </code>选项设置为<code> true </code>,则后代源</code> DocumentFragment </code>被递归导入,并且在导入的<code> DocumentFra
     * gment </code>下重新组合生成的节点以形成相应的子树否则,这只会​​生成一个空的<code> DocumentFragment </code> </dd> <dt> DOCUMENT_NODE
     *  </dt> <dd> <code> Document </code>节点。
     * </dd> <dt> DOCUMENT_TYPE_NODE </dt> <dd> </em>属性节点不能导入</dt> <dd> </dt> <dd> ,并且生成的<code> Attr </code>
     * 节点附加到所生成的<code> Element </code> Default属性,但不是</em>被复制,虽然如果要导入的文档定义了默认属性这个元素名称,那些被分配如果<code> importNod
     * e </code> <code> deep </code>参数设置为<code> true </code>,则会递归导入源元素的后代,并重新组合生成的节点以形成相应的子树可以导入</dd> <dt> E
     * NTITY_NODE </dt> <dd>实体</code>节点,但是在当前版本的DOM中,<code> DocumentType </code>这些导入到<code> DocumentType </code>
     * 的节点将被考虑用于添加到未来版本的DOMOn导入,<code> publicId </code>,<code> systemId </code>和<code> notationName </code>属
     * 性被复制如果请求了<code> deep </code> import,则递归地导入源<code> Entity </code>的后代,并重新组合生成的节点以形成相应的子树</dd>。
     * <dt>
     * ENTITY_REFERENCE_NODE </dt> <dd>仅复制<code> EntityReference </code>本身,即使请求了<code> deep </code> import,因
     * 为源文档和目标文档可能定义了不同的实体。
     * 被导入的文档提供了该实体名称的定义,其值被赋值</dd> <dt> NOTATION_NODE </dt>。
     * <dd>
     * <code>符号</code>节点可以导入,但是在当前版本的DOM中,<code> DocumentType </code>是只读的能够将这些导入的节点添加到<code> DocumentType </code>
     * 考虑添加到未来的DOMOn导入版本中,复制<code> publicId </code>和<code> systemId </code>属性注意,<code> deep </code>参数对此没有影响节
     * 点类型,因为它们不能有任何子</dd>。
     * <dt>
     * PROCESSING_INSTRUCTION_NODE </dt> <dd>导入的节点将其<code>目标</code>和<code>数据</code>值与源节点的值进行复制注意<code> deep 
     * </code>对这类节点的影响,因为它们不能有任何子</dd> <dt> TEXT_NODE,CDATA_SECTION_NODE,COMMENT_NODE </dt> <dd>这三种类型的节点继承自<code>
     *  CharacterData </code> > data </code>和源代码节点的<code> length </code>属性注意<code> deep </code>参数对这些类型的节点没有影
     * 响,因为它们不能有任何子</dd >。
     * </dl>
     * 
     * @param importedNode The node to import.
     * @param deep If <code>true</code>, recursively import the subtree under
     *   the specified node; if <code>false</code>, import only the node
     *   itself, as explained above. This has no effect on nodes that cannot
     *   have any children, and on <code>Attr</code>, and
     *   <code>EntityReference</code> nodes.
     * @return The imported node that belongs to this <code>Document</code>.
     * @exception DOMException
     *   NOT_SUPPORTED_ERR: Raised if the type of node being imported is not
     *   supported.
     *   <br>INVALID_CHARACTER_ERR: Raised if one of the imported names is not
     *   an XML name according to the XML version in use specified in the
     *   <code>Document.xmlVersion</code> attribute. This may happen when
     *   importing an XML 1.1 [<a href='http://www.w3.org/TR/2004/REC-xml11-20040204/'>XML 1.1</a>] element
     *   into an XML 1.0 document, for instance.
     * @since DOM Level 2
     */
    public Node importNode(Node importedNode,
                           boolean deep)
                           throws DOMException;

    /**
     * Creates an element of the given qualified name and namespace URI.
     * <br>Per [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML Namespaces</a>]
     * , applications must use the value <code>null</code> as the
     * namespaceURI parameter for methods if they wish to have no namespace.
     * <p>
     * 创建给定限定名称和命名空间URI的元素<br>按[<a href='http://wwww3org/TR/1999/REC-xml-names-19990114/'> XML命名空间</a>],应用程序
     * 必须使用值<code> null </code>作为方法的namespaceURI参数,如果他们希望没有命名空间。
     * 
     * 
     * @param namespaceURI The namespace URI of the element to create.
     * @param qualifiedName The qualified name of the element type to
     *   instantiate.
     * @return A new <code>Element</code> object with the following
     *   attributes:
     * <table border='1' cellpadding='3'>
     * <tr>
     * <th>Attribute</th>
     * <th>Value</th>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'><code>Node.nodeName</code></td>
     * <td valign='top' rowspan='1' colspan='1'>
     *   <code>qualifiedName</code></td>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'><code>Node.namespaceURI</code></td>
     * <td valign='top' rowspan='1' colspan='1'>
     *   <code>namespaceURI</code></td>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'><code>Node.prefix</code></td>
     * <td valign='top' rowspan='1' colspan='1'>prefix, extracted
     *   from <code>qualifiedName</code>, or <code>null</code> if there is
     *   no prefix</td>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'><code>Node.localName</code></td>
     * <td valign='top' rowspan='1' colspan='1'>local name, extracted from
     *   <code>qualifiedName</code></td>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'><code>Element.tagName</code></td>
     * <td valign='top' rowspan='1' colspan='1'>
     *   <code>qualifiedName</code></td>
     * </tr>
     * </table>
     * @exception DOMException
     *   INVALID_CHARACTER_ERR: Raised if the specified
     *   <code>qualifiedName</code> is not an XML name according to the XML
     *   version in use specified in the <code>Document.xmlVersion</code>
     *   attribute.
     *   <br>NAMESPACE_ERR: Raised if the <code>qualifiedName</code> is a
     *   malformed qualified name, if the <code>qualifiedName</code> has a
     *   prefix and the <code>namespaceURI</code> is <code>null</code>, or
     *   if the <code>qualifiedName</code> has a prefix that is "xml" and
     *   the <code>namespaceURI</code> is different from "<a href='http://www.w3.org/XML/1998/namespace'>
     *   http://www.w3.org/XML/1998/namespace</a>" [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML Namespaces</a>]
     *   , or if the <code>qualifiedName</code> or its prefix is "xmlns" and
     *   the <code>namespaceURI</code> is different from "<a href='http://www.w3.org/2000/xmlns/'>http://www.w3.org/2000/xmlns/</a>", or if the <code>namespaceURI</code> is "<a href='http://www.w3.org/2000/xmlns/'>http://www.w3.org/2000/xmlns/</a>" and neither the <code>qualifiedName</code> nor its prefix is "xmlns".
     *   <br>NOT_SUPPORTED_ERR: Always thrown if the current document does not
     *   support the <code>"XML"</code> feature, since namespaces were
     *   defined by XML.
     * @since DOM Level 2
     */
    public Element createElementNS(String namespaceURI,
                                   String qualifiedName)
                                   throws DOMException;

    /**
     * Creates an attribute of the given qualified name and namespace URI.
     * <br>Per [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML Namespaces</a>]
     * , applications must use the value <code>null</code> as the
     * <code>namespaceURI</code> parameter for methods if they wish to have
     * no namespace.
     * <p>
     *  创建给定限定名称和命名空间URI的属性<br>根据[<a href='http://wwww3org/TR/1999/REC-xml-names-19990114/'> XML命名空间</a>],应用
     * 程序必须使用值<code> null </code>作为方法的<code> namespaceURI </code>参数,如果他们希望没有命名空间。
     * 
     * 
     * @param namespaceURI The namespace URI of the attribute to create.
     * @param qualifiedName The qualified name of the attribute to
     *   instantiate.
     * @return A new <code>Attr</code> object with the following attributes:
     * <table border='1' cellpadding='3'>
     * <tr>
     * <th>
     *   Attribute</th>
     * <th>Value</th>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'><code>Node.nodeName</code></td>
     * <td valign='top' rowspan='1' colspan='1'>qualifiedName</td>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'>
     *   <code>Node.namespaceURI</code></td>
     * <td valign='top' rowspan='1' colspan='1'><code>namespaceURI</code></td>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'>
     *   <code>Node.prefix</code></td>
     * <td valign='top' rowspan='1' colspan='1'>prefix, extracted from
     *   <code>qualifiedName</code>, or <code>null</code> if there is no
     *   prefix</td>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'><code>Node.localName</code></td>
     * <td valign='top' rowspan='1' colspan='1'>local name, extracted from
     *   <code>qualifiedName</code></td>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'><code>Attr.name</code></td>
     * <td valign='top' rowspan='1' colspan='1'>
     *   <code>qualifiedName</code></td>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'><code>Node.nodeValue</code></td>
     * <td valign='top' rowspan='1' colspan='1'>the empty
     *   string</td>
     * </tr>
     * </table>
     * @exception DOMException
     *   INVALID_CHARACTER_ERR: Raised if the specified
     *   <code>qualifiedName</code> is not an XML name according to the XML
     *   version in use specified in the <code>Document.xmlVersion</code>
     *   attribute.
     *   <br>NAMESPACE_ERR: Raised if the <code>qualifiedName</code> is a
     *   malformed qualified name, if the <code>qualifiedName</code> has a
     *   prefix and the <code>namespaceURI</code> is <code>null</code>, if
     *   the <code>qualifiedName</code> has a prefix that is "xml" and the
     *   <code>namespaceURI</code> is different from "<a href='http://www.w3.org/XML/1998/namespace'>
     *   http://www.w3.org/XML/1998/namespace</a>", if the <code>qualifiedName</code> or its prefix is "xmlns" and the
     *   <code>namespaceURI</code> is different from "<a href='http://www.w3.org/2000/xmlns/'>http://www.w3.org/2000/xmlns/</a>", or if the <code>namespaceURI</code> is "<a href='http://www.w3.org/2000/xmlns/'>http://www.w3.org/2000/xmlns/</a>" and neither the <code>qualifiedName</code> nor its prefix is "xmlns".
     *   <br>NOT_SUPPORTED_ERR: Always thrown if the current document does not
     *   support the <code>"XML"</code> feature, since namespaces were
     *   defined by XML.
     * @since DOM Level 2
     */
    public Attr createAttributeNS(String namespaceURI,
                                  String qualifiedName)
                                  throws DOMException;

    /**
     * Returns a <code>NodeList</code> of all the <code>Elements</code> with a
     * given local name and namespace URI in document order.
     * <p>
     *  以文档顺序返回给定本地名称和命名空间URI的所有<code> Elements </code>中的<code> NodeList </code>
     * 
     * 
     * @param namespaceURI The namespace URI of the elements to match on. The
     *   special value <code>"*"</code> matches all namespaces.
     * @param localName The local name of the elements to match on. The
     *   special value "*" matches all local names.
     * @return A new <code>NodeList</code> object containing all the matched
     *   <code>Elements</code>.
     * @since DOM Level 2
     */
    public NodeList getElementsByTagNameNS(String namespaceURI,
                                           String localName);

    /**
     * Returns the <code>Element</code> that has an ID attribute with the
     * given value. If no such element exists, this returns <code>null</code>
     * . If more than one element has an ID attribute with that value, what
     * is returned is undefined.
     * <br> The DOM implementation is expected to use the attribute
     * <code>Attr.isId</code> to determine if an attribute is of type ID.
     * <p ><b>Note:</b> Attributes with the name "ID" or "id" are not of type
     * ID unless so defined.
     * <p>
     * 返回具有给定值的ID属性的<code> Element </code>如果没有这样的元素存在,则返回<code> null </code>如果有多个元素具有该属性的ID,返回未定义<br> DOM实现应
     * 使用属性<code> AttrisId </code>来确定属性是否属于ID <p> <b>注意：</b>名称为"ID"的属性"或"id"不是类型ID,除非这样定义。
     * 
     * 
     * @param elementId The unique <code>id</code> value for an element.
     * @return The matching element or <code>null</code> if there is none.
     * @since DOM Level 2
     */
    public Element getElementById(String elementId);

    /**
     * An attribute specifying the encoding used for this document at the time
     * of the parsing. This is <code>null</code> when it is not known, such
     * as when the <code>Document</code> was created in memory.
     * <p>
     *  指定在解析时用于此文档的编码的属性当不知道时,这是<code> null </code>,例如当<code> Document </code>在内存中创建时
     * 
     * 
     * @since DOM Level 3
     */
    public String getInputEncoding();

    /**
     * An attribute specifying, as part of the <a href='http://www.w3.org/TR/2004/REC-xml-20040204#NT-XMLDecl'>XML declaration</a>, the encoding of this document. This is <code>null</code> when
     * unspecified or when it is not known, such as when the
     * <code>Document</code> was created in memory.
     * <p>
     * 作为<a href='http://wwww3org/TR/2004/REC-xml-20040204#NT-XMLDecl'> XML声明</a>一部分的属性,指定此文档的编码这是<code > nu
     * ll </code>当未指定或不知道时,例如当<code> Document </code>在内存中创建时。
     * 
     * 
     * @since DOM Level 3
     */
    public String getXmlEncoding();

    /**
     * An attribute specifying, as part of the <a href='http://www.w3.org/TR/2004/REC-xml-20040204#NT-XMLDecl'>XML declaration</a>, whether this document is standalone. This is <code>false</code> when
     * unspecified.
     * <p ><b>Note:</b>  No verification is done on the value when setting
     * this attribute. Applications should use
     * <code>Document.normalizeDocument()</code> with the "validate"
     * parameter to verify if the value matches the <a href='http://www.w3.org/TR/2004/REC-xml-20040204#sec-rmd'>validity
     * constraint for standalone document declaration</a> as defined in [<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>].
     * <p>
     * 作为<a href='http://wwww3org/TR/2004/REC-xml-20040204#NT-XMLDecl'> XML声明</a>一部分的属性,说明此文档是独立的这是<code > f
     * alse </code>当未指定<p> <b>注意：</b>设置此属性时不对值进行验证应用程序应使用<code> DocumentnormalizeDocument()</code>以验证该值是否与<a href='http://wwww3org/TR/2004/REC-xml-20040204#sec-rmd'>
     * 独立文档声明的有效性约束</a>匹配,如[<a href ='http：// wwww3org / TR / 2004 / REC-xml-20040204'> XML 10 </a>]。
     * 
     * 
     * @since DOM Level 3
     */
    public boolean getXmlStandalone();
    /**
     * An attribute specifying, as part of the <a href='http://www.w3.org/TR/2004/REC-xml-20040204#NT-XMLDecl'>XML declaration</a>, whether this document is standalone. This is <code>false</code> when
     * unspecified.
     * <p ><b>Note:</b>  No verification is done on the value when setting
     * this attribute. Applications should use
     * <code>Document.normalizeDocument()</code> with the "validate"
     * parameter to verify if the value matches the <a href='http://www.w3.org/TR/2004/REC-xml-20040204#sec-rmd'>validity
     * constraint for standalone document declaration</a> as defined in [<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>].
     * <p>
     * 作为<a href='http://wwww3org/TR/2004/REC-xml-20040204#NT-XMLDecl'> XML声明</a>一部分的属性,说明此文档是独立的这是<code > f
     * alse </code>当未指定<p> <b>注意：</b>设置此属性时不对值进行验证应用程序应使用<code> DocumentnormalizeDocument()</code>以验证该值是否与<a href='http://wwww3org/TR/2004/REC-xml-20040204#sec-rmd'>
     * 独立文档声明的有效性约束</a>匹配,如[<a href ='http：// wwww3org / TR / 2004 / REC-xml-20040204'> XML 10 </a>]。
     * 
     * 
     * @exception DOMException
     *    NOT_SUPPORTED_ERR: Raised if this document does not support the
     *   "XML" feature.
     * @since DOM Level 3
     */
    public void setXmlStandalone(boolean xmlStandalone)
                                  throws DOMException;

    /**
     *  An attribute specifying, as part of the <a href='http://www.w3.org/TR/2004/REC-xml-20040204#NT-XMLDecl'>XML declaration</a>, the version number of this document. If there is no declaration and if
     * this document supports the "XML" feature, the value is
     * <code>"1.0"</code>. If this document does not support the "XML"
     * feature, the value is always <code>null</code>. Changing this
     * attribute will affect methods that check for invalid characters in
     * XML names. Application should invoke
     * <code>Document.normalizeDocument()</code> in order to check for
     * invalid characters in the <code>Node</code>s that are already part of
     * this <code>Document</code>.
     * <br> DOM applications may use the
     * <code>DOMImplementation.hasFeature(feature, version)</code> method
     * with parameter values "XMLVersion" and "1.0" (respectively) to
     * determine if an implementation supports [<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>]. DOM
     * applications may use the same method with parameter values
     * "XMLVersion" and "1.1" (respectively) to determine if an
     * implementation supports [<a href='http://www.w3.org/TR/2004/REC-xml11-20040204/'>XML 1.1</a>]. In both
     * cases, in order to support XML, an implementation must also support
     * the "XML" feature defined in this specification. <code>Document</code>
     *  objects supporting a version of the "XMLVersion" feature must not
     * raise a <code>NOT_SUPPORTED_ERR</code> exception for the same version
     * number when using <code>Document.xmlVersion</code>.
     * <p>
     * 作为<a href='http://wwww3org/TR/2004/REC-xml-20040204#NT-XMLDecl'> XML声明</a>一部分的属性,指定此文档的版本号如果存在没有声明,如果
     * 此文档支持"XML"功能,则值为<code>"10"</code>如果此文档不支持"XML"功能,则该值始终为<code> null </code>更改此属性将影响在XML名称中检查无效字符的方法应用程
     * 序应调用<code> DocumentnormalizeDocument()</code>以检查已经作为一部分的<code> Node </code>中的无效字符此<code>文档</code> <br>
     *  DOM应用程序可以使用参数值为"XMLVersion"和"1"的<code> DOMImplementationhasFeature(feature,version)</0"(分别),以确定实现是否支持[<a href='http://wwww3org/TR/2004/REC-xml-20040204'>
     *  XML 10 </a>] DOM应用程序可以使用与参数相同的方法值"XMLVersion"和"11"(分别),以确定实施是否支持[<a href='http://wwww3org/TR/2004/REC-xml11-20040204/'>
     *  XML 11 </a>]。
     * 情况下,为了支持XML,实现还必须支持本规范中定义的"XML"特性。
     * <code> Document </code>对象支持一个版本的"XMLVersion"特性不能引发一个<code> NOT_SUPPORTED_ERR <代码>使用<code> Documentxml
     * Version </code>时,同一版本号的异常。
     * 情况下,为了支持XML,实现还必须支持本规范中定义的"XML"特性。
     * 
     * 
     * @since DOM Level 3
     */
    public String getXmlVersion();
    /**
     *  An attribute specifying, as part of the <a href='http://www.w3.org/TR/2004/REC-xml-20040204#NT-XMLDecl'>XML declaration</a>, the version number of this document. If there is no declaration and if
     * this document supports the "XML" feature, the value is
     * <code>"1.0"</code>. If this document does not support the "XML"
     * feature, the value is always <code>null</code>. Changing this
     * attribute will affect methods that check for invalid characters in
     * XML names. Application should invoke
     * <code>Document.normalizeDocument()</code> in order to check for
     * invalid characters in the <code>Node</code>s that are already part of
     * this <code>Document</code>.
     * <br> DOM applications may use the
     * <code>DOMImplementation.hasFeature(feature, version)</code> method
     * with parameter values "XMLVersion" and "1.0" (respectively) to
     * determine if an implementation supports [<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>]. DOM
     * applications may use the same method with parameter values
     * "XMLVersion" and "1.1" (respectively) to determine if an
     * implementation supports [<a href='http://www.w3.org/TR/2004/REC-xml11-20040204/'>XML 1.1</a>]. In both
     * cases, in order to support XML, an implementation must also support
     * the "XML" feature defined in this specification. <code>Document</code>
     *  objects supporting a version of the "XMLVersion" feature must not
     * raise a <code>NOT_SUPPORTED_ERR</code> exception for the same version
     * number when using <code>Document.xmlVersion</code>.
     * <p>
     * 作为<a href='http://wwww3org/TR/2004/REC-xml-20040204#NT-XMLDecl'> XML声明</a>一部分的属性,指定此文档的版本号如果存在没有声明,如果
     * 此文档支持"XML"功能,则值为<code>"10"</code>如果此文档不支持"XML"功能,则该值始终为<code> null </code>更改此属性将影响在XML名称中检查无效字符的方法应用程
     * 序应调用<code> DocumentnormalizeDocument()</code>以检查已经作为一部分的<code> Node </code>中的无效字符此<code>文档</code> <br>
     *  DOM应用程序可以使用参数值为"XMLVersion"和"1"的<code> DOMImplementationhasFeature(feature,version)</0"(分别),以确定实现是否支持[<a href='http://wwww3org/TR/2004/REC-xml-20040204'>
     *  XML 10 </a>] DOM应用程序可以使用与参数相同的方法值"XMLVersion"和"11"(分别),以确定实施是否支持[<a href='http://wwww3org/TR/2004/REC-xml11-20040204/'>
     *  XML 11 </a>]。
     * 情况下,为了支持XML,实现还必须支持本规范中定义的"XML"特性。
     * <code> Document </code>对象支持一个版本的"XMLVersion"特性不能引发一个<code> NOT_SUPPORTED_ERR <代码>使用<code> Documentxml
     * Version </code>时,同一版本号的异常。
     * 情况下,为了支持XML,实现还必须支持本规范中定义的"XML"特性。
     * 
     * 
     * @exception DOMException
     *    NOT_SUPPORTED_ERR: Raised if the version is set to a value that is
     *   not supported by this <code>Document</code> or if this document
     *   does not support the "XML" feature.
     * @since DOM Level 3
     */
    public void setXmlVersion(String xmlVersion)
                                  throws DOMException;

    /**
     * An attribute specifying whether error checking is enforced or not. When
     * set to <code>false</code>, the implementation is free to not test
     * every possible error case normally defined on DOM operations, and not
     * raise any <code>DOMException</code> on DOM operations or report
     * errors while using <code>Document.normalizeDocument()</code>. In case
     * of error, the behavior is undefined. This attribute is
     * <code>true</code> by default.
     * <p>
     * 指定是否强制执行错误检查的属性设置为<code> false </code>时,实现可以自由测试通常在DOM操作上定义的每个可能的错误情况,而不会引发任何<code> DOMException </code >
     * 在DOM操作或报告错误时使用<code> DocumentnormalizeDocument()</code>在错误的情况下,行为是未定义的此属性默认为<code> true </code>。
     * 
     * 
     * @since DOM Level 3
     */
    public boolean getStrictErrorChecking();
    /**
     * An attribute specifying whether error checking is enforced or not. When
     * set to <code>false</code>, the implementation is free to not test
     * every possible error case normally defined on DOM operations, and not
     * raise any <code>DOMException</code> on DOM operations or report
     * errors while using <code>Document.normalizeDocument()</code>. In case
     * of error, the behavior is undefined. This attribute is
     * <code>true</code> by default.
     * <p>
     * 指定是否强制执行错误检查的属性设置为<code> false </code>时,实现可以自由测试通常在DOM操作上定义的每个可能的错误情况,而不会引发任何<code> DOMException </code >
     * 在DOM操作或报告错误时使用<code> DocumentnormalizeDocument()</code>在错误的情况下,行为是未定义的此属性默认为<code> true </code>。
     * 
     * 
     * @since DOM Level 3
     */
    public void setStrictErrorChecking(boolean strictErrorChecking);

    /**
     *  The location of the document or <code>null</code> if undefined or if
     * the <code>Document</code> was created using
     * <code>DOMImplementation.createDocument</code>. No lexical checking is
     * performed when setting this attribute; this could result in a
     * <code>null</code> value returned when using <code>Node.baseURI</code>
     * .
     * <br> Beware that when the <code>Document</code> supports the feature
     * "HTML" [<a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>DOM Level 2 HTML</a>]
     * , the href attribute of the HTML BASE element takes precedence over
     * this attribute when computing <code>Node.baseURI</code>.
     * <p>
     * 文档的位置或<code> null </code>如果未定义或如果<code> Document </code>是使用<code> DOMImplementationcreateDocument </code>
     * 创建的,则在设置此属性时不执行词法检查;这可能会导致使用<code> NodebaseURI </code>时返回的<code> null </code>值<br>注意<code> Document </code>
     * 支持特性"HTML" ='http：// wwww3org / TR / 2003 / REC-DOM-Level-2-HTML-20030109'> DOM Level 2 HTML </a>],HT
     * ML BASE元素的href属性优先于此属性<code> NodebaseURI </code>。
     * 
     * 
     * @since DOM Level 3
     */
    public String getDocumentURI();
    /**
     *  The location of the document or <code>null</code> if undefined or if
     * the <code>Document</code> was created using
     * <code>DOMImplementation.createDocument</code>. No lexical checking is
     * performed when setting this attribute; this could result in a
     * <code>null</code> value returned when using <code>Node.baseURI</code>
     * .
     * <br> Beware that when the <code>Document</code> supports the feature
     * "HTML" [<a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>DOM Level 2 HTML</a>]
     * , the href attribute of the HTML BASE element takes precedence over
     * this attribute when computing <code>Node.baseURI</code>.
     * <p>
     * 文档的位置或<code> null </code>如果未定义或如果<code> Document </code>是使用<code> DOMImplementationcreateDocument </code>
     * 创建的,则在设置此属性时不执行词法检查;这可能会导致使用<code> NodebaseURI </code>时返回的<code> null </code>值<br>注意<code> Document </code>
     * 支持特性"HTML" ='http：// wwww3org / TR / 2003 / REC-DOM-Level-2-HTML-20030109'> DOM Level 2 HTML </a>],HT
     * ML BASE元素的href属性优先于此属性<code> NodebaseURI </code>。
     * 
     * 
     * @since DOM Level 3
     */
    public void setDocumentURI(String documentURI);

    /**
     *  Attempts to adopt a node from another document to this document. If
     * supported, it changes the <code>ownerDocument</code> of the source
     * node, its children, as well as the attached attribute nodes if there
     * are any. If the source node has a parent it is first removed from the
     * child list of its parent. This effectively allows moving a subtree
     * from one document to another (unlike <code>importNode()</code> which
     * create a copy of the source node instead of moving it). When it
     * fails, applications should use <code>Document.importNode()</code>
     * instead. Note that if the adopted node is already part of this
     * document (i.e. the source and target document are the same), this
     * method still has the effect of removing the source node from the
     * child list of its parent, if any. The following list describes the
     * specifics for each type of node.
     * <dl>
     * <dt>ATTRIBUTE_NODE</dt>
     * <dd>The
     * <code>ownerElement</code> attribute is set to <code>null</code> and
     * the <code>specified</code> flag is set to <code>true</code> on the
     * adopted <code>Attr</code>. The descendants of the source
     * <code>Attr</code> are recursively adopted.</dd>
     * <dt>DOCUMENT_FRAGMENT_NODE</dt>
     * <dd>The
     * descendants of the source node are recursively adopted.</dd>
     * <dt>DOCUMENT_NODE</dt>
     * <dd>
     * <code>Document</code> nodes cannot be adopted.</dd>
     * <dt>DOCUMENT_TYPE_NODE</dt>
     * <dd>
     * <code>DocumentType</code> nodes cannot be adopted.</dd>
     * <dt>ELEMENT_NODE</dt>
     * <dd><em>Specified</em> attribute nodes of the source element are adopted. Default attributes
     * are discarded, though if the document being adopted into defines
     * default attributes for this element name, those are assigned. The
     * descendants of the source element are recursively adopted.</dd>
     * <dt>ENTITY_NODE</dt>
     * <dd>
     * <code>Entity</code> nodes cannot be adopted.</dd>
     * <dt>ENTITY_REFERENCE_NODE</dt>
     * <dd>Only
     * the <code>EntityReference</code> node itself is adopted, the
     * descendants are discarded, since the source and destination documents
     * might have defined the entity differently. If the document being
     * imported into provides a definition for this entity name, its value
     * is assigned.</dd>
     * <dt>NOTATION_NODE</dt>
     * <dd><code>Notation</code> nodes cannot be
     * adopted.</dd>
     * <dt>PROCESSING_INSTRUCTION_NODE, TEXT_NODE, CDATA_SECTION_NODE,
     * COMMENT_NODE</dt>
     * <dd>These nodes can all be adopted. No specifics.</dd>
     * </dl>
     * <p ><b>Note:</b>  Since it does not create new nodes unlike the
     * <code>Document.importNode()</code> method, this method does not raise
     * an <code>INVALID_CHARACTER_ERR</code> exception, and applications
     * should use the <code>Document.normalizeDocument()</code> method to
     * check if an imported name is not an XML name according to the XML
     * version in use.
     * <p>
     * 尝试采用从另一个文档到本文档的节点如果支持,它会更改源节点及其子节点的<code> ownerDocument </code>以及附加的属性节点(如果有)如果源节点有父代它首先从其父代的子代列表中删除这
     * 有效地允许将一个子文件从一个文档移动到另一个(不像<code> importNode()</code>,它创建源节点的副本而不是移动它)应用程序应该使用<code> DocumentimportNode
     * ()</code>。
     * 注意,如果采用的节点已经是本文档的一部分(即源文档和目标文档相同),则此方法仍然具有删除源节点从其父节点的子节点列表(如果有)以下列表描述了每种类型节点的详细信息。
     * <dl>
     * <dt> ATTRIBUTE_NODE </dt> <dd> <code> ownerElement </code>属性设置为<code> null </code>,并且<code>指定</code>标
     * 志设置为<code> true </code> </code> </code>在<code> Attr </code>的后代被递归地采用</>> </>> DOCUMENT_FRAGMENT_NODE 
     * </dt>源节点被递归采用</dd> <dt> DOCUMENT_NODE </dt>。
     * <dd>
     *  <code>无法采用文档</code>节点</dd> </span> </b> </b> DOCUMENT_TYPE_NODE </dt>
     * <dd>
     * <code>无法采用DocumentType </code>节点</em> </em> </em>源元素的指定</em>属性节点默认属性被丢弃,被采用的文档定义了该元素名称的默认属性,那些被分配。
     * 源元素的后代被递归地采用</dd> <dt> ENTITY_NODE </dt>。
     * <dd>
     * <code>实体</code>节点不被采用</dd> <dt> ENTITY_REFERENCE_NODE </dt> <dd>只有<code> EntityReference </code>节点本身被
     * 采用,后代被丢弃,和目标文档可能已定义了不同的实体。
     * 如果要导入的文档提供此实体名称的定义,则其值将分配</dd> <dt> NOTATION_NODE </dt> <dd> <code>记法</code>节点不能采用</dd> <dt> PROCESSI
     * NG_INSTRUCTION_NODE,TEXT_NODE,CDATA_SECTION_NODE,COMMENT_NODE </dt> <dd>这些节点都可以采用无细节</dd>。
     * </dl>
     * <p> <b>注意：</b>由于与<code> DocumentimportNode()</code>方法不同,它不会创建新节点,因此此方法不会引发<code> INVALID_CHARACTER_ER
     * R </code>异常,应用程序应使用<code> DocumentnormalizeDocument()</code>方法来检查导入的名称是否不是根据所使用的XML版本的XML名称。
     * 
     * 
     * @param source The node to move into this document.
     * @return The adopted node, or <code>null</code> if this operation
     *   fails, such as when the source node comes from a different
     *   implementation.
     * @exception DOMException
     *   NOT_SUPPORTED_ERR: Raised if the source node is of type
     *   <code>DOCUMENT</code>, <code>DOCUMENT_TYPE</code>.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised when the source node is
     *   readonly.
     * @since DOM Level 3
     */
    public Node adoptNode(Node source)
                          throws DOMException;

    /**
     *  The configuration used when <code>Document.normalizeDocument()</code>
     * is invoked.
     * <p>
     *  调用<code> DocumentnormalizeDocument()</code>时使用的配置
     * 
     * 
     * @since DOM Level 3
     */
    public DOMConfiguration getDomConfig();

    /**
     *  This method acts as if the document was going through a save and load
     * cycle, putting the document in a "normal" form. As a consequence,
     * this method updates the replacement tree of
     * <code>EntityReference</code> nodes and normalizes <code>Text</code>
     * nodes, as defined in the method <code>Node.normalize()</code>.
     * <br> Otherwise, the actual result depends on the features being set on
     * the <code>Document.domConfig</code> object and governing what
     * operations actually take place. Noticeably this method could also
     * make the document namespace well-formed according to the algorithm
     * described in , check the character normalization, remove the
     * <code>CDATASection</code> nodes, etc. See
     * <code>DOMConfiguration</code> for details.
     * <pre>// Keep in the document
     * the information defined // in the XML Information Set (Java example)
     * DOMConfiguration docConfig = myDocument.getDomConfig();
     * docConfig.setParameter("infoset", Boolean.TRUE);
     * myDocument.normalizeDocument();</pre>
     *
     * <br>Mutation events, when supported, are generated to reflect the
     * changes occurring on the document.
     * <br> If errors occur during the invocation of this method, such as an
     * attempt to update a read-only node or a <code>Node.nodeName</code>
     * contains an invalid character according to the XML version in use,
     * errors or warnings (<code>DOMError.SEVERITY_ERROR</code> or
     * <code>DOMError.SEVERITY_WARNING</code>) will be reported using the
     * <code>DOMErrorHandler</code> object associated with the "error-handler
     * " parameter. Note this method might also report fatal errors (
     * <code>DOMError.SEVERITY_FATAL_ERROR</code>) if an implementation
     * cannot recover from an error.
     * <p>
     * 此方法的作用就像文档正在经历保存和加载循环,将文档置于"正常"形式。
     * 因此,此方法更新<code> EntityReference </code>节点的替换树,并规范化<code> <code> Nodenormalize()</code>中定义的</code>节点</code>
     *  <br>否则,实际结果取决于在<code> DocumentdomConfig </code>对象上设置的功能,操作实际上发生。
     * 此方法的作用就像文档正在经历保存和加载循环,将文档置于"正常"形式。
     * 明显地,该方法还可以使文档命名空间根据描述的算法良好地形成,检查字符规范化,去除<code> CDATASection </code>节点等等。
     * 参见<code> DOMConfiguration </code>详情<pre> //在文档中保留在XML信息集中定义的信息(Java示例)DOMConfiguration docConfig = my
     * DocumentgetDomConfig(); docConfigsetParameter("infoset",BooleanTRUE); myDocumentnormalizeDocument(); 
     * </pre>。
     * 明显地,该方法还可以使文档命名空间根据描述的算法良好地形成,检查字符规范化,去除<code> CDATASection </code>节点等等。
     * 
     * <br>生成支持的突变事件以反映文档上发生的更改<br>如果在调用此方法期间发生错误,例如尝试更新只读节点或<code> NodenodeName < / code>包含根据正在使用的XML版本的无效字
     * 符,将使用<code> DOMErrorHandler </code>报告错误或警告(<code> DOMErrorSEVERITY_ERROR </code>或<code> DOMErrorSEVER
     * ITY_WARNING </code>与"错误处理程序"参数相关联的对象注意,如果实现无法从错误中恢复,则此方法也可能报告致命错误(<code> DOMErrorSEVERITY_FATAL_ERROR
     *  </code>)。
     * 
     * 
     * @since DOM Level 3
     */
    public void normalizeDocument();

    /**
     * Rename an existing node of type <code>ELEMENT_NODE</code> or
     * <code>ATTRIBUTE_NODE</code>.
     * <br>When possible this simply changes the name of the given node,
     * otherwise this creates a new node with the specified name and
     * replaces the existing node with the new node as described below.
     * <br>If simply changing the name of the given node is not possible, the
     * following operations are performed: a new node is created, any
     * registered event listener is registered on the new node, any user
     * data attached to the old node is removed from that node, the old node
     * is removed from its parent if it has one, the children are moved to
     * the new node, if the renamed node is an <code>Element</code> its
     * attributes are moved to the new node, the new node is inserted at the
     * position the old node used to have in its parent's child nodes list
     * if it has one, the user data that was attached to the old node is
     * attached to the new node.
     * <br>When the node being renamed is an <code>Element</code> only the
     * specified attributes are moved, default attributes originated from
     * the DTD are updated according to the new element name. In addition,
     * the implementation may update default attributes from other schemas.
     * Applications should use <code>Document.normalizeDocument()</code> to
     * guarantee these attributes are up-to-date.
     * <br>When the node being renamed is an <code>Attr</code> that is
     * attached to an <code>Element</code>, the node is first removed from
     * the <code>Element</code> attributes map. Then, once renamed, either
     * by modifying the existing node or creating a new one as described
     * above, it is put back.
     * <br>In addition,
     * <ul>
     * <li> a user data event <code>NODE_RENAMED</code> is fired,
     * </li>
     * <li>
     * when the implementation supports the feature "MutationNameEvents",
     * each mutation operation involved in this method fires the appropriate
     * event, and in the end the event {
     * <code>http://www.w3.org/2001/xml-events</code>,
     * <code>DOMElementNameChanged</code>} or {
     * <code>http://www.w3.org/2001/xml-events</code>,
     * <code>DOMAttributeNameChanged</code>} is fired.
     * </li>
     * </ul>
     * <p>
     * 
     * @param n The node to rename.
     * @param namespaceURI The new namespace URI.
     * @param qualifiedName The new qualified name.
     * @return The renamed node. This is either the specified node or the new
     *   node that was created to replace the specified node.
     * @exception DOMException
     *   NOT_SUPPORTED_ERR: Raised when the type of the specified node is
     *   neither <code>ELEMENT_NODE</code> nor <code>ATTRIBUTE_NODE</code>,
     *   or if the implementation does not support the renaming of the
     *   document element.
     *   <br>INVALID_CHARACTER_ERR: Raised if the new qualified name is not an
     *   XML name according to the XML version in use specified in the
     *   <code>Document.xmlVersion</code> attribute.
     *   <br>WRONG_DOCUMENT_ERR: Raised when the specified node was created
     *   from a different document than this document.
     *   <br>NAMESPACE_ERR: Raised if the <code>qualifiedName</code> is a
     *   malformed qualified name, if the <code>qualifiedName</code> has a
     *   prefix and the <code>namespaceURI</code> is <code>null</code>, or
     *   if the <code>qualifiedName</code> has a prefix that is "xml" and
     *   the <code>namespaceURI</code> is different from "<a href='http://www.w3.org/XML/1998/namespace'>
     *   http://www.w3.org/XML/1998/namespace</a>" [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML Namespaces</a>]
     *   . Also raised, when the node being renamed is an attribute, if the
     *   <code>qualifiedName</code>, or its prefix, is "xmlns" and the
     *   <code>namespaceURI</code> is different from "<a href='http://www.w3.org/2000/xmlns/'>http://www.w3.org/2000/xmlns/</a>".
     * @since DOM Level 3
     */
    public Node renameNode(Node n,
                           String namespaceURI,
                           String qualifiedName)
                           throws DOMException;

}
