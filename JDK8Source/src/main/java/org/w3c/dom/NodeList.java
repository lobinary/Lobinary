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
 * The <code>NodeList</code> interface provides the abstraction of an ordered
 * collection of nodes, without defining or constraining how this collection
 * is implemented. <code>NodeList</code> objects in the DOM are live.
 * <p>The items in the <code>NodeList</code> are accessible via an integral
 * index, starting from 0.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>Document Object Model (DOM) Level 3 Core Specification</a>.
 * <p>
 *  <code> NodeList </code>接口提供了有序节点集合的抽象,而不定义或约束如何实现此集合。 DOM中的<code> NodeList </code>对象是活的。
 *  <p> <code> NodeList </code>中的项目可以通过整数索引从0开始访问。
 * <p>另请参阅<a href ='http：//www.w3.org/TR/2004 / REC-DOM-Level-3-Core-20040407'>文档对象模型(DOM)3级核心规范</a>。
 * 
 */
public interface NodeList {
    /**
     * Returns the <code>index</code>th item in the collection. If
     * <code>index</code> is greater than or equal to the number of nodes in
     * the list, this returns <code>null</code>.
     * <p>
     *  返回集合中的<code> index </code>项。如果<code> index </code>大于或等于列表中的节点数,则返回<code> null </code>。
     * 
     * 
     * @param index Index into the collection.
     * @return The node at the <code>index</code>th position in the
     *   <code>NodeList</code>, or <code>null</code> if that is not a valid
     *   index.
     */
    public Node item(int index);

    /**
     * The number of nodes in the list. The range of valid child node indices
     * is 0 to <code>length-1</code> inclusive.
     * <p>
     *  列表中的节点数。有效子节点索引的范围是0到<code> length-1 </code>。
     */
    public int getLength();

}
