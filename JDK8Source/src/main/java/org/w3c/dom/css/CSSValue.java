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
 * Copyright (c) 2000 World Wide Web Consortium,
 * (Massachusetts Institute of Technology, Institut National de
 * Recherche en Informatique et en Automatique, Keio University). All
 * Rights Reserved. This program is distributed under the W3C's Software
 * Intellectual Property License. This program is distributed in the
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.
 * See W3C License http://www.w3.org/Consortium/Legal/ for more details.
 * <p>
 *  版权所有(c)2000万维网联盟,(马萨诸塞理工学院,庆应义藩大学信息自动化研究所)。版权所有。该程序根据W3C的软件知识产权许可证分发。
 * 这个程序是分发的,希望它将是有用的,但没有任何保证;甚至没有对适销性或适用于特定用途的隐含保证。有关详细信息,请参阅W3C许可证http://www.w3.org/Consortium/Legal/。
 * 
 */

package org.w3c.dom.css;

import org.w3c.dom.DOMException;

/**
 *  The <code>CSSValue</code> interface represents a simple or a complex
 * value. A <code>CSSValue</code> object only occurs in a context of a CSS
 * property.
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>Document Object Model (DOM) Level 2 Style Specification</a>.
 * <p>
 *  <code> CSSValue </code>接口表示一个简单或复杂的值。 <code> CSSValue </code>对象只出现在CSS属性的上下文中。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>文档对象模型(DOM)2级样式规范< a>。
 * 
 * 
 * @since DOM Level 2
 */
public interface CSSValue {
    // UnitTypes
    /**
     * The value is inherited and the <code>cssText</code> contains "inherit".
     * <p>
     *  该值是继承的,而<code> cssText </code>包含"inherit"。
     * 
     */
    public static final short CSS_INHERIT               = 0;
    /**
     * The value is a primitive value and an instance of the
     * <code>CSSPrimitiveValue</code> interface can be obtained by using
     * binding-specific casting methods on this instance of the
     * <code>CSSValue</code> interface.
     * <p>
     *  该值是一个原始值,并且可以通过在<code> CSSValue </code>接口的此实例上使用特定于绑定的转换方法来获取<code> CSSPrimitiveValue </code>接口的实例。
     * 
     */
    public static final short CSS_PRIMITIVE_VALUE       = 1;
    /**
     * The value is a <code>CSSValue</code> list and an instance of the
     * <code>CSSValueList</code> interface can be obtained by using
     * binding-specific casting methods on this instance of the
     * <code>CSSValue</code> interface.
     * <p>
     *  值是一个<code> CSSValue </code>列表,并且<code> CSSValueList </code>接口的实例可以通过使用特定于绑定的转换方法在<code> CSSValue </code>
     * 接口。
     * 
     */
    public static final short CSS_VALUE_LIST            = 2;
    /**
     * The value is a custom value.
     * <p>
     *  该值是自定义值。
     * 
     */
    public static final short CSS_CUSTOM                = 3;

    /**
     *  A string representation of the current value.
     * <p>
     *  当前值的字符串表示形式。
     * 
     */
    public String getCssText();
    /**
     *  A string representation of the current value.
     * <p>
     * 当前值的字符串表示形式。
     * 
     * 
     * @exception DOMException
     *    SYNTAX_ERR: Raised if the specified CSS string value has a syntax
     *   error (according to the attached property) or is unparsable.
     *   <br>INVALID_MODIFICATION_ERR: Raised if the specified CSS string
     *   value represents a different type of values than the values allowed
     *   by the CSS property.
     *   <br> NO_MODIFICATION_ALLOWED_ERR: Raised if this value is readonly.
     */
    public void setCssText(String cssText)
                       throws DOMException;

    /**
     *  A code defining the type of the value as defined above.
     * <p>
     *  定义如上定义的值的类型的代码。
     */
    public short getCssValueType();

}
