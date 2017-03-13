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

import org.w3c.dom.Node;
import org.w3c.dom.Element;

/**
 *  <code>LSParserFilter</code>s provide applications the ability to examine
 * nodes as they are being constructed while parsing. As each node is
 * examined, it may be modified or removed, or the entire parse may be
 * terminated early.
 * <p> At the time any of the filter methods are called by the parser, the
 * owner Document and DOMImplementation objects exist and are accessible.
 * The document element is never passed to the <code>LSParserFilter</code>
 * methods, i.e. it is not possible to filter out the document element.
 * <code>Document</code>, <code>DocumentType</code>, <code>Notation</code>,
 * <code>Entity</code>, and <code>Attr</code> nodes are never passed to the
 * <code>acceptNode</code> method on the filter. The child nodes of an
 * <code>EntityReference</code> node are passed to the filter if the
 * parameter "<a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#parameter-entities'>
 * entities</a>" is set to <code>false</code>. Note that, as described by the parameter "<a href='http://www.w3.org/TR/DOM-Level-3-Core/core.html#parameter-entities'>
 * entities</a>", unexpanded entity reference nodes are never discarded and are always
 * passed to the filter.
 * <p> All validity checking while parsing a document occurs on the source
 * document as it appears on the input stream, not on the DOM document as it
 * is built in memory. With filters, the document in memory may be a subset
 * of the document on the stream, and its validity may have been affected by
 * the filtering.
 * <p> All default attributes must be present on elements when the elements
 * are passed to the filter methods. All other default content must be
 * passed to the filter methods.
 * <p> DOM applications must not raise exceptions in a filter. The effect of
 * throwing exceptions from a filter is DOM implementation dependent.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-LS-20040407'>Document Object Model (DOM) Level 3 Load
and Save Specification</a>.
 * <p>
 * <code> LSParserFilter </code>在解析时为应用程序提供检查节点的能力随着每个节点被检查,它可以被修改或删除,或者整个解析可以早期终止<p>任何过滤器方法由解析器调用,所有者Do
 * cument和DOMImplementation对象存在并且可访问文档元素从不传递到<code> LSParserFilter </code>方法,即不可能过滤出文档元素<code>文档</code>,
 * <code> DocumentType </code>,<code>记法</code>,<code> Entity </code>和<code> Attr </code>过滤器上的<code> acce
 * ptNode </code>方法如果参数"<a href ='http：// wwww3org / TR / DOM-Level-3-Core / corehtml#parameter-entities",则将<code>
 *  EntityReference </code>节点的子节点传递给过滤器, > entity </a>"设置为<code> false </code>请注意,如参数"<a href ='http：// wwww3org / TR / DOM-Level-3-Core / corehtml#参数实体'>
 * 实体</a>",未展开的实体引用节点从不被丢弃并且总是被传递给过滤器。
 * 当解析文档时,在源文档上出现所有有效性检查,因为它出现在输入流上,而不是在DOM文档上,因为它被内置在内存中使用过滤器,内存中的文档可能是流上文档的一个子集,其有效性可能已受到过滤的影响<p>当元素传递
 * 给过滤器方法时,所有默认属性必须存在于元素上所有其他默认内容必须传递给过滤器方法<p> DOM应用程序不能在过滤器中引发异常从一个过滤器抛出异常的效果过滤器是DOM实现相关的<p>另请参见<a href='http://wwww3org/TR/2004/REC-DOM-Level-3-LS-20040407'>
 * 文档对象模型(DOM)3级加载和保存规格</a>。
 * 
 */
public interface LSParserFilter {
    // Constants returned by startElement and acceptNode
    /**
     * Accept the node.
     * <p>
     * 接受节点
     * 
     */
    public static final short FILTER_ACCEPT             = 1;
    /**
     * Reject the node and its children.
     * <p>
     *  拒绝节点及其子节点
     * 
     */
    public static final short FILTER_REJECT             = 2;
    /**
     * Skip this single node. The children of this node will still be
     * considered.
     * <p>
     *  跳过此单个节点此节点的子节点仍将被考虑
     * 
     */
    public static final short FILTER_SKIP               = 3;
    /**
     *  Interrupt the normal processing of the document.
     * <p>
     *  中断文档的正常处理
     * 
     */
    public static final short FILTER_INTERRUPT          = 4;

    /**
     *  The parser will call this method after each <code>Element</code> start
     * tag has been scanned, but before the remainder of the
     * <code>Element</code> is processed. The intent is to allow the
     * element, including any children, to be efficiently skipped. Note that
     * only element nodes are passed to the <code>startElement</code>
     * function.
     * <br>The element node passed to <code>startElement</code> for filtering
     * will include all of the Element's attributes, but none of the
     * children nodes. The Element may not yet be in place in the document
     * being constructed (it may not have a parent node.)
     * <br>A <code>startElement</code> filter function may access or change
     * the attributes for the Element. Changing Namespace declarations will
     * have no effect on namespace resolution by the parser.
     * <br>For efficiency, the Element node passed to the filter may not be
     * the same one as is actually placed in the tree if the node is
     * accepted. And the actual node (node object identity) may be reused
     * during the process of reading in and filtering a document.
     * <p>
     * 解析器将在扫描每个<code> Element </code>开始标记之后,但在处理<code> Element </code>的剩余部分之前调用此方法意图是允许元素要有效地跳过注意只有元素节点被传递到
     * <code> startElement </code>函数<br>传递给<code> startElement </code>的元素节点将包括所有Element的属性,的子节点元素可能尚未在正在构造的文
     * 档中(它可能没有父节点)。
     * <br> A <code> startElement </code>过滤器函数可以访问或更改元素的属性更改命名空间声明对解析器的命名空间解析没有影响<br>为了效率,传递给过滤器的Element节点可能
     * 与实际放置在树中的节点不同(如果节点被接受),并且实际节点(节点对象标识)可能在读取过程中被重用,过滤文档。
     * 
     * 
     * @param elementArg The newly encountered element. At the time this
     *   method is called, the element is incomplete - it will have its
     *   attributes, but no children.
     * @return
     * <ul>
     * <li> <code>FILTER_ACCEPT</code> if the <code>Element</code> should
     *   be included in the DOM document being built.
     * </li>
     * <li>
     *   <code>FILTER_REJECT</code> if the <code>Element</code> and all of
     *   its children should be rejected.
     * </li>
     * <li> <code>FILTER_SKIP</code> if the
     *   <code>Element</code> should be skipped. All of its children are
     *   inserted in place of the skipped <code>Element</code> node.
     * </li>
     * <li>
     *   <code>FILTER_INTERRUPT</code> if the filter wants to stop the
     *   processing of the document. Interrupting the processing of the
     *   document does no longer guarantee that the resulting DOM tree is
     *   XML well-formed. The <code>Element</code> is rejected.
     * </li>
     * </ul> Returning
     *   any other values will result in unspecified behavior.
     */
    public short startElement(Element elementArg);

    /**
     * This method will be called by the parser at the completion of the
     * parsing of each node. The node and all of its descendants will exist
     * and be complete. The parent node will also exist, although it may be
     * incomplete, i.e. it may have additional children that have not yet
     * been parsed. Attribute nodes are never passed to this function.
     * <br>From within this method, the new node may be freely modified -
     * children may be added or removed, text nodes modified, etc. The state
     * of the rest of the document outside this node is not defined, and the
     * affect of any attempt to navigate to, or to modify any other part of
     * the document is undefined.
     * <br>For validating parsers, the checks are made on the original
     * document, before any modification by the filter. No validity checks
     * are made on any document modifications made by the filter.
     * <br>If this new node is rejected, the parser might reuse the new node
     * and any of its descendants.
     * <p>
     * 此方法将在解析每个节点时由解析器调用。
     * 节点及其所有后代将存在并完成父节点也将存在,虽然它可能不完整,即它可能有额外的子节点尚未解析的属性节点永远不会传递到此函数<br>从此方法中,可以自由修改新节点 - 可以添加或删除子节点,修改文本节点等
     * 等。
     * 此方法将在解析每个节点时由解析器调用。
     * 文档外其余部分的状态节点未定义,并且任何尝试导航到或修改文档的任何其他部分的影响未定义<br>为了验证解析器,在过滤器进行任何修改之前对原始文档进行检查对过滤器进行的任何文档修改都不进行有效性检查<br>
     * 如果此新节点被拒绝,则解析器可能会重新使用新节点及其任何后代。
     * 此方法将在解析每个节点时由解析器调用。
     * 
     * 
     * @param nodeArg The newly constructed element. At the time this method
     *   is called, the element is complete - it has all of its children
     *   (and their children, recursively) and attributes, and is attached
     *   as a child to its parent.
     * @return
     * <ul>
     * <li> <code>FILTER_ACCEPT</code> if this <code>Node</code> should
     *   be included in the DOM document being built.
     * </li>
     * <li>
     *   <code>FILTER_REJECT</code> if the <code>Node</code> and all of its
     *   children should be rejected.
     * </li>
     * <li> <code>FILTER_SKIP</code> if the
     *   <code>Node</code> should be skipped and the <code>Node</code>
     *   should be replaced by all the children of the <code>Node</code>.
     * </li>
     * <li>
     *   <code>FILTER_INTERRUPT</code> if the filter wants to stop the
     *   processing of the document. Interrupting the processing of the
     *   document does no longer guarantee that the resulting DOM tree is
     *   XML well-formed. The <code>Node</code> is accepted and will be the
     *   last completely parsed node.
     * </li>
     * </ul>
     */
    public short acceptNode(Node nodeArg);

    /**
     *  Tells the <code>LSParser</code> what types of nodes to show to the
     * method <code>LSParserFilter.acceptNode</code>. If a node is not shown
     * to the filter using this attribute, it is automatically included in
     * the DOM document being built. See <code>NodeFilter</code> for
     * definition of the constants. The constants <code>SHOW_ATTRIBUTE</code>
     * , <code>SHOW_DOCUMENT</code>, <code>SHOW_DOCUMENT_TYPE</code>,
     * <code>SHOW_NOTATION</code>, <code>SHOW_ENTITY</code>, and
     * <code>SHOW_DOCUMENT_FRAGMENT</code> are meaningless here. Those nodes
     * will never be passed to <code>LSParserFilter.acceptNode</code>.
     * <br> The constants used here are defined in [<a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Traversal-Range-20001113'>DOM Level 2 Traversal and      Range</a>]
     * .
     * <p>
     */
    public int getWhatToShow();

}
