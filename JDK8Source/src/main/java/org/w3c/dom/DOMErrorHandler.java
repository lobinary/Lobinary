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
 *  <code>DOMErrorHandler</code> is a callback interface that the DOM
 * implementation can call when reporting errors that happens while
 * processing XML data, or when doing some other processing (e.g. validating
 * a document). A <code>DOMErrorHandler</code> object can be attached to a
 * <code>Document</code> using the "error-handler" on the
 * <code>DOMConfiguration</code> interface. If more than one error needs to
 * be reported during an operation, the sequence and numbers of the errors
 * passed to the error handler are implementation dependent.
 * <p> The application that is using the DOM implementation is expected to
 * implement this interface.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>Document Object Model (DOM) Level 3 Core Specification</a>.
 * <p>
 *  <code> DOMErrorHandler </code>是一个回调接口,当处理XML数据时或执行其他处理(例如验证文档)时发生错误时,DOM实现可以调用此接口。
 * 可以使用<code> DOMConfiguration </code>接口上的"错误处理程序"将<code> DOMErrorHandler </code>对象附加到<code> Document </code>
 * 。
 *  <code> DOMErrorHandler </code>是一个回调接口,当处理XML数据时或执行其他处理(例如验证文档)时发生错误时,DOM实现可以调用此接口。
 * 如果在操作期间需要报告多于一个错误,则传递给错误处理程序的错误的序列和数量是依赖于实现的。 <p>使用DOM实现的应用程序应该实现此接口。
 * 
 * @since DOM Level 3
 */
public interface DOMErrorHandler {
    /**
     * This method is called on the error handler when an error occurs.
     * <br> If an exception is thrown from this method, it is considered to be
     * equivalent of returning <code>true</code>.
     * <p>
     *  <p>另请参阅<a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>文档对象模型(DOM)3级核心规范< a>。
     * 
     * 
     * @param error  The error object that describes the error. This object
     *   may be reused by the DOM implementation across multiple calls to
     *   the <code>handleError</code> method.
     * @return  If the <code>handleError</code> method returns
     *   <code>false</code>, the DOM implementation should stop the current
     *   processing when possible. If the method returns <code>true</code>,
     *   the processing may continue depending on
     *   <code>DOMError.severity</code>.
     */
    public boolean handleError(DOMError error);

}
