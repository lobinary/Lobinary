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
 *  (马萨诸塞理工学院,欧洲研究联合会信息学和数学,庆应大学)。版权所有。这项工作根据W3C(r)软件许可证[1]分发,希望它有用,但没有任何保证;甚至没有对适销性或适用于特定用途的隐含保证。
 * 
 *  [1] http://www.w3.org/Consortium/Legal/2002/copyright-software-20021231
 * 
 */

package org.w3c.dom;

/**
 * Each <code>Document</code> has a <code>doctype</code> attribute whose value
 * is either <code>null</code> or a <code>DocumentType</code> object. The
 * <code>DocumentType</code> interface in the DOM Core provides an interface
 * to the list of entities that are defined for the document, and little
 * else because the effect of namespaces and the various XML schema efforts
 * on DTD representation are not clearly understood as of this writing.
 * <p>DOM Level 3 doesn't support editing <code>DocumentType</code> nodes.
 * <code>DocumentType</code> nodes are read-only.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>Document Object Model (DOM) Level 3 Core Specification</a>.
 * <p>
 *  每个<code> Document </code>都有<code> doctype </code>属性,其值为<code> null </code>或<code> DocumentType </code>
 * 对象。
 *  DOM Core中的<code> DocumentType </code>接口为为文档定义的实体列表提供了一个接口,很少有其他原因,因为命名空间和各种XML模式对DTD表示的影响尚不清楚截至本文。
 *  <p> DOM 3级不支持编辑<code> DocumentType </code>节点。 <code> DocumentType </code>节点是只读的。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>文档对象模型(DOM)3级核心规范< a>。
 * 
 */
public interface DocumentType extends Node {
    /**
     * The name of DTD; i.e., the name immediately following the
     * <code>DOCTYPE</code> keyword.
     * <p>
     *  DTD的名称;即紧跟<code> DOCTYPE </code>关键字后面的名称。
     * 
     */
    public String getName();

    /**
     * A <code>NamedNodeMap</code> containing the general entities, both
     * external and internal, declared in the DTD. Parameter entities are
     * not contained. Duplicates are discarded. For example in:
     * <pre>&lt;!DOCTYPE
     * ex SYSTEM "ex.dtd" [ &lt;!ENTITY foo "foo"&gt; &lt;!ENTITY bar
     * "bar"&gt; &lt;!ENTITY bar "bar2"&gt; &lt;!ENTITY % baz "baz"&gt;
     * ]&gt; &lt;ex/&gt;</pre>
     *  the interface provides access to <code>foo</code>
     * and the first declaration of <code>bar</code> but not the second
     * declaration of <code>bar</code> or <code>baz</code>. Every node in
     * this map also implements the <code>Entity</code> interface.
     * <br>The DOM Level 2 does not support editing entities, therefore
     * <code>entities</code> cannot be altered in any way.
     * <p>
     * 包含在DTD中声明的外部和内部的一般实体的<code> NamedNodeMap </code>。不包含参数实体。丢弃重复项。
     * 例如：<pre>&lt;！DOCTYPE ex SYSTEM"ex.dtd"[&lt;！ENTITY foo"foo"&gt; &lt; ex /&gt; </pre>接口提供对<code> foo </code>
     * 和<code> bar </code>的第一个声明的访问,或<code> baz </code>。
     * 包含在DTD中声明的外部和内部的一般实体的<code> NamedNodeMap </code>。不包含参数实体。丢弃重复项。此映射中的每个节点还实现<code> Entity </code>接口。
     *  <br> DOM Level 2不支持编辑实体,因此不能以任何方式更改<code>实体</code>。
     * 
     */
    public NamedNodeMap getEntities();

    /**
     * A <code>NamedNodeMap</code> containing the notations declared in the
     * DTD. Duplicates are discarded. Every node in this map also implements
     * the <code>Notation</code> interface.
     * <br>The DOM Level 2 does not support editing notations, therefore
     * <code>notations</code> cannot be altered in any way.
     * <p>
     *  包含在DTD中声明的符号的<code> NamedNodeMap </code>。丢弃重复项。此映射中的每个节点还实现<code>符号</code>接口。
     *  <br> DOM Level 2不支持编辑符号,因此不能以任何方式更改<code>符号</code>。
     * 
     */
    public NamedNodeMap getNotations();

    /**
     * The public identifier of the external subset.
     * <p>
     *  外部子集的公共标识符。
     * 
     * 
     * @since DOM Level 2
     */
    public String getPublicId();

    /**
     * The system identifier of the external subset. This may be an absolute
     * URI or not.
     * <p>
     *  外部子集的系统标识符。这可以是绝对URI或不是。
     * 
     * 
     * @since DOM Level 2
     */
    public String getSystemId();

    /**
     * The internal subset as a string, or <code>null</code> if there is none.
     * This is does not contain the delimiting square brackets.
     * <p ><b>Note:</b> The actual content returned depends on how much
     * information is available to the implementation. This may vary
     * depending on various parameters, including the XML processor used to
     * build the document.
     * <p>
     *  内部子集作为字符串,或<code> null </code>(如果没有)。这不包含分隔的方括号。 <p> <b>注意：</b>返回的实际内容取决于实施可用的信息量。
     * 这可以根据各种参数而变化,包括用于构建文档的XML处理器。
     * 
     * @since DOM Level 2
     */
    public String getInternalSubset();

}
