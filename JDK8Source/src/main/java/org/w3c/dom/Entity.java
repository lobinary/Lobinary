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
 * This interface represents a known entity, either parsed or unparsed, in an
 * XML document. Note that this models the entity itself <em>not</em> the entity declaration.
 * <p>The <code>nodeName</code> attribute that is inherited from
 * <code>Node</code> contains the name of the entity.
 * <p>An XML processor may choose to completely expand entities before the
 * structure model is passed to the DOM; in this case there will be no
 * <code>EntityReference</code> nodes in the document tree.
 * <p>XML does not mandate that a non-validating XML processor read and
 * process entity declarations made in the external subset or declared in
 * parameter entities. This means that parsed entities declared in the
 * external subset need not be expanded by some classes of applications, and
 * that the replacement text of the entity may not be available. When the <a href='http://www.w3.org/TR/2004/REC-xml-20040204#intern-replacement'>
 * replacement text</a> is available, the corresponding <code>Entity</code> node's child list
 * represents the structure of that replacement value. Otherwise, the child
 * list is empty.
 * <p>DOM Level 3 does not support editing <code>Entity</code> nodes; if a
 * user wants to make changes to the contents of an <code>Entity</code>,
 * every related <code>EntityReference</code> node has to be replaced in the
 * structure model by a clone of the <code>Entity</code>'s contents, and
 * then the desired changes must be made to each of those clones instead.
 * <code>Entity</code> nodes and all their descendants are readonly.
 * <p>An <code>Entity</code> node does not have any parent.
 * <p ><b>Note:</b> If the entity contains an unbound namespace prefix, the
 * <code>namespaceURI</code> of the corresponding node in the
 * <code>Entity</code> node subtree is <code>null</code>. The same is true
 * for <code>EntityReference</code> nodes that refer to this entity, when
 * they are created using the <code>createEntityReference</code> method of
 * the <code>Document</code> interface.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>Document Object Model (DOM) Level 3 Core Specification</a>.
 * <p>
 * 此接口表示XML文档中已解析或未解析的已知实体请注意,这将对实体本身建模<em>不是实体声明<p>继承的<code> nodeName </code>属性从<code> Node </code>包含实体
 * 的名称<p> XML处理器可以选择在将结构模型传递给DOM之前完全展开实体;在这种情况下,在文档树中不会有<code> EntityReference </code>节点。
 * XML不要求非验证XML处理器读取和处理在外部子集中做出的或在参数实体中声明的实体声明这意味着在外部子集中声明的解析实体不需要由某些应用程序类扩展,并且实体的替换文本可能不可用。
 * 当<a href ='http：// wwww3org / TR / 2004 / REC -xml-20040204#intern-replacement'>替换文本</a>,则相应的<code> E
 * ntity </code>节点的子列表表示该替换值的结构。
 * XML不要求非验证XML处理器读取和处理在外部子集中做出的或在参数实体中声明的实体声明这意味着在外部子集中声明的解析实体不需要由某些应用程序类扩展,并且实体的替换文本可能不可用。
 * 否则,子列表为空<p> DOM级别3不支持编辑<code>实体</code>节点;如果用户想要改变<code> Entity </code>的内容,则每个相关的<code> EntityReferenc
 * e </code>节点必须在结构模型中被<code> Entity < / code>的内容,然后必须对每个克隆进行所需的更改<code>实体</code>节点及其所有后代只读<p> <code>实体</code>
 * 节点没有任何父<p> <b>注：</b>如果实体包含未绑定的命名空间前缀,<code> Entity </code>节点子树中对应节点的<code> namespaceURI </code>是<code>
 *  null </code> <code> EntityReference </code使用<code> Document </code>接口<p>的<code> createEntityReferenc
 * e </code>方法创建时,引用此实体的节点参见<a href ='http：// wwww3org / TR / 2004 / REC-DOM-Level-3-Core-20040407'>文档对象
 * 模型(DOM)3级核心规范</a>。
 * XML不要求非验证XML处理器读取和处理在外部子集中做出的或在参数实体中声明的实体声明这意味着在外部子集中声明的解析实体不需要由某些应用程序类扩展,并且实体的替换文本可能不可用。
 * 
 */
public interface Entity extends Node {
    /**
     * The public identifier associated with the entity if specified, and
     * <code>null</code> otherwise.
     * <p>
     * 与实体相关联的公共标识符(如果指定),否则为<code> null </code>
     * 
     */
    public String getPublicId();

    /**
     * The system identifier associated with the entity if specified, and
     * <code>null</code> otherwise. This may be an absolute URI or not.
     * <p>
     *  与实体相关联的系统标识符(如果指定)和<code> null </code>,否则可以是绝对URI或不是
     * 
     */
    public String getSystemId();

    /**
     * For unparsed entities, the name of the notation for the entity. For
     * parsed entities, this is <code>null</code>.
     * <p>
     *  对于未解析的实体,实体的符号名称对于解析的实体,这是<code> null </code>
     * 
     */
    public String getNotationName();

    /**
     * An attribute specifying the encoding used for this entity at the time
     * of parsing, when it is an external parsed entity. This is
     * <code>null</code> if it an entity from the internal subset or if it
     * is not known.
     * <p>
     *  一个属性,指定在解析时用于此实体的编码,当它是一个外部解析实体时这是<code> null </code>如果它是来自内部子集的实体,或者如果它是未知的
     * 
     * 
     * @since DOM Level 3
     */
    public String getInputEncoding();

    /**
     * An attribute specifying, as part of the text declaration, the encoding
     * of this entity, when it is an external parsed entity. This is
     * <code>null</code> otherwise.
     * <p>
     *  作为文本声明的一部分的属性,当它是外部解析实体时,指定此实体的编码这是<code> null </code>,否则
     * 
     * 
     * @since DOM Level 3
     */
    public String getXmlEncoding();

    /**
     * An attribute specifying, as part of the text declaration, the version
     * number of this entity, when it is an external parsed entity. This is
     * <code>null</code> otherwise.
     * <p>
     * 作为文本声明的一部分的属性,当它是外部解析实体时,指定此实体的版本号这是<code> null </code>,否则
     * 
     * @since DOM Level 3
     */
    public String getXmlVersion();

}
