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
 *  The <code>NameList</code> interface provides the abstraction of an ordered
 * collection of parallel pairs of name and namespace values (which could be
 * null values), without defining or constraining how this collection is
 * implemented. The items in the <code>NameList</code> are accessible via an
 * integral index, starting from 0.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>Document Object Model (DOM) Level 3 Core Specification</a>.
 * <p>
 *  <code> NameList </code>接口提供了名称和命名空间值(可以是空值)的并行对的有序集合的抽象,而没有定义或约束如何实现该集合。
 *  <code> NameList </code>中的项可通过整数索引从0开始访问。
 * <p>另请参阅<a href ='http：//www.w3.org/TR/2004/REC- DOM-Level-3-Core-20040407'>文档对象模型(DOM)3级核心规范</a>。
 * 
 * 
 * @since DOM Level 3
 */
public interface NameList {
    /**
     *  Returns the <code>index</code>th name item in the collection.
     * <p>
     *  返回集合中的<code> index </code>名称项。
     * 
     * 
     * @param index Index into the collection.
     * @return  The name at the <code>index</code>th position in the
     *   <code>NameList</code>, or <code>null</code> if there is no name for
     *   the specified index or if the index is out of range.
     */
    public String getName(int index);

    /**
     *  Returns the <code>index</code>th namespaceURI item in the collection.
     * <p>
     *  返回集合中的<code> index </code> th namespaceURI项目。
     * 
     * 
     * @param index Index into the collection.
     * @return  The namespace URI at the <code>index</code>th position in the
     *   <code>NameList</code>, or <code>null</code> if there is no name for
     *   the specified index or if the index is out of range.
     */
    public String getNamespaceURI(int index);

    /**
     *  The number of pairs (name and namespaceURI) in the list. The range of
     * valid child node indices is 0 to <code>length-1</code> inclusive.
     * <p>
     *  列表中的对数(name和namespaceURI)。有效子节点索引的范围是0到<code> length-1 </code>。
     * 
     */
    public int getLength();

    /**
     *  Test if a name is part of this <code>NameList</code>.
     * <p>
     *  测试名称是否是此<code> NameList </code>的一部分。
     * 
     * 
     * @param str  The name to look for.
     * @return  <code>true</code> if the name has been found,
     *   <code>false</code> otherwise.
     */
    public boolean contains(String str);

    /**
     *  Test if the pair namespaceURI/name is part of this
     * <code>NameList</code>.
     * <p>
     *  测试对namespaceURI / name是否为<code> NameList </code>的一部分。
     * 
     * @param namespaceURI  The namespace URI to look for.
     * @param name  The name to look for.
     * @return  <code>true</code> if the pair namespaceURI/name has been
     *   found, <code>false</code> otherwise.
     */
    public boolean containsNS(String namespaceURI,
                              String name);

}
