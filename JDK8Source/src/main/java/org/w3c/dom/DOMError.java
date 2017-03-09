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
 * <code>DOMError</code> is an interface that describes an error.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>Document Object Model (DOM) Level 3 Core Specification</a>.
 * <p>
 *  <code> DOMError </code>是描述错误的接口。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>文档对象模型(DOM)3级核心规范< a>。
 * 
 * 
 * @since DOM Level 3
 */
public interface DOMError {
    // ErrorSeverity
    /**
     * The severity of the error described by the <code>DOMError</code> is
     * warning. A <code>SEVERITY_WARNING</code> will not cause the
     * processing to stop, unless <code>DOMErrorHandler.handleError()</code>
     * returns <code>false</code>.
     * <p>
     *  由<code> DOMError </code>描述的错误的严重性是警告。
     * 除非<code> DOMErrorHandler.handleError()</code>返回<code> false </code>,否则<code> SEVERITY_WARNING </code>
     * 不会导致处理停止。
     *  由<code> DOMError </code>描述的错误的严重性是警告。
     * 
     */
    public static final short SEVERITY_WARNING          = 1;
    /**
     * The severity of the error described by the <code>DOMError</code> is
     * error. A <code>SEVERITY_ERROR</code> may not cause the processing to
     * stop if the error can be recovered, unless
     * <code>DOMErrorHandler.handleError()</code> returns <code>false</code>.
     * <p>
     *  由<code> DOMError </code>描述的错误的严重性是错误。
     * 除非<code> DOMErrorHandler.handleError()</code>返回<code> false </code>,否则<code> SEVERITY_ERROR </code>可能
     * 不会导致处理停止,。
     *  由<code> DOMError </code>描述的错误的严重性是错误。
     * 
     */
    public static final short SEVERITY_ERROR            = 2;
    /**
     * The severity of the error described by the <code>DOMError</code> is
     * fatal error. A <code>SEVERITY_FATAL_ERROR</code> will cause the
     * normal processing to stop. The return value of
     * <code>DOMErrorHandler.handleError()</code> is ignored unless the
     * implementation chooses to continue, in which case the behavior
     * becomes undefined.
     * <p>
     * 由<code> DOMError </code>描述的错误的严重性是致命错误。 <code> SEVERITY_FATAL_ERROR </code>会导致正常处理停止。
     * 除非实现选择继续,否则忽略<code> DOMErrorHandler.handleError()</code>的返回值,在这种情况下行为变为未定义。
     * 
     */
    public static final short SEVERITY_FATAL_ERROR      = 3;

    /**
     * The severity of the error, either <code>SEVERITY_WARNING</code>,
     * <code>SEVERITY_ERROR</code>, or <code>SEVERITY_FATAL_ERROR</code>.
     * <p>
     *  错误的严重性,<code> SEVERITY_WARNING </code>,<code> SEVERITY_ERROR </code>或<code> SEVERITY_FATAL_ERROR </code>
     * 。
     * 
     */
    public short getSeverity();

    /**
     * An implementation specific string describing the error that occurred.
     * <p>
     *  描述发生的错误的实现特定字符串。
     * 
     */
    public String getMessage();

    /**
     *  A <code>DOMString</code> indicating which related data is expected in
     * <code>relatedData</code>. Users should refer to the specification of
     * the error in order to find its <code>DOMString</code> type and
     * <code>relatedData</code> definitions if any.
     * <p ><b>Note:</b>  As an example,
     * <code>Document.normalizeDocument()</code> does generate warnings when
     * the "split-cdata-sections" parameter is in use. Therefore, the method
     * generates a <code>SEVERITY_WARNING</code> with <code>type</code>
     * <code>"cdata-sections-splitted"</code> and the first
     * <code>CDATASection</code> node in document order resulting from the
     * split is returned by the <code>relatedData</code> attribute.
     * <p>
     *  指示<code> relatedData </code>中预期相关数据的<code> DOMString </code>。
     * 用户应参考错误的规范,以找到其<code> DOMString </code>类型和<code> relatedData </code>定义(如果有)。
     *  <p> <b>注意：</b>例如,当使用"split-cdata-sections"参数时,<code> Document.normalizeDocument()</code>会生成警告。
     * 因此,该方法使用<code> type </code> <code>"cdata-sections-splitted"</code>和第一个<code> CDATASection </code>节点生成
     * <code> SEVERITY_WARNING </code>由分割产生的文档顺序由<code> relatedData </code>属性返回。
     *  <p> <b>注意：</b>例如,当使用"split-cdata-sections"参数时,<code> Document.normalizeDocument()</code>会生成警告。
     * 
     */
    public String getType();

    /**
     * The related platform dependent exception if any.
     * <p>
     *  相关平台相关异常(如果有)。
     * 
     */
    public Object getRelatedException();

    /**
     *  The related <code>DOMError.type</code> dependent data if any.
     * <p>
     *  相关的<code> DOMError.type </code>依赖数据(如果有)。
     * 
     */
    public Object getRelatedData();

    /**
     * The location of the error.
     * <p>
     *  错误的位置。
     */
    public DOMLocator getLocation();

}
