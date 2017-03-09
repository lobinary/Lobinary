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
 *  (马萨诸塞理工学院,欧洲研究联合会信息学和数学,庆应大学)。版权所有。这项工作是根据W3C(r)软件许可证[1]分发的,希望它将是有用的,但没有任何保证;甚至没有对适销性或适用于特定用途的隐含保证。
 * 
 *  [1] http://www.w3.org/Consortium/Legal/2002/copyright-software-20021231
 * 
 */

package org.w3c.dom;

/**
 * This interface represents a notation declared in the DTD. A notation either
 * declares, by name, the format of an unparsed entity (see <a href='http://www.w3.org/TR/2004/REC-xml-20040204#Notations'>section 4.7</a> of the XML 1.0 specification [<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>]), or is
 * used for formal declaration of processing instruction targets (see <a href='http://www.w3.org/TR/2004/REC-xml-20040204#sec-pi'>section 2.6</a> of the XML 1.0 specification [<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>]). The
 * <code>nodeName</code> attribute inherited from <code>Node</code> is set
 * to the declared name of the notation.
 * <p>The DOM Core does not support editing <code>Notation</code> nodes; they
 * are therefore readonly.
 * <p>A <code>Notation</code> node does not have any parent.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>Document Object Model (DOM) Level 3 Core Specification</a>.
 * <p>
 * 此接口表示在DTD中声明的符号。
 * 符号表示根据名称声明未分析实体的格式(请参阅<a href='http://www.w3.org/TR/2004/REC-xml-20040204#Notations'>第4.7节</a> XML 1.
 * 0规范[<a href='http://www.w3.org/TR/2004/REC-xml-20040204'> XML 1.0 </a>]),或用于正式声明处理指令目标(请参阅XML 1.0规范的<a href='http://www.w3.org/TR/2004/REC-xml-20040204#sec-pi'>
 * 第2.6节</a> [<a href =' 继承自<code> Node </code>的<code> nodeName </code>属性设置为符号的声明名称。
 * 此接口表示在DTD中声明的符号。 <p> DOM Core不支持编辑<code>符号</code>节点;因此它们是唯一的。 <p> <code>符号</code>节点没有任何父级。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>文档对象模型(DOM)3级核心规范< a>。
 * 
 */
public interface Notation extends Node {
    /**
     * The public identifier of this notation. If the public identifier was
     * not specified, this is <code>null</code>.
     * <p>
     */
    public String getPublicId();

    /**
     * The system identifier of this notation. If the system identifier was
     * not specified, this is <code>null</code>. This may be an absolute URI
     * or not.
     * <p>
     *  此符号的公共标识符。如果没有指定公共标识符,这是<code> null </code>。
     * 
     */
    public String getSystemId();

}
