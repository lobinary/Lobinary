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
 *  版权所有(c)2000万维网联盟(马萨诸塞理工学院,庆应义藩大学信息自动化研究所)保留所有权利本程序根据W3C的软件知识产权许可分发本程序分发于希望这将是有用的,但没有任何保证;甚至没有对适销性或适用
 * 于特定用途的隐含保证,请参阅W3C许可证http：// wwww3org / Consortium / Legal /了解更多详情。
 * 
 */

package org.w3c.dom.css;

import org.w3c.dom.DOMException;

/**
 *  The <code>CSS2Properties</code> interface represents a convenience
 * mechanism for retrieving and setting properties within a
 * <code>CSSStyleDeclaration</code>. The attributes of this interface
 * correspond to all the properties specified in CSS2. Getting an attribute
 * of this interface is equivalent to calling the
 * <code>getPropertyValue</code> method of the
 * <code>CSSStyleDeclaration</code> interface. Setting an attribute of this
 * interface is equivalent to calling the <code>setProperty</code> method of
 * the <code>CSSStyleDeclaration</code> interface.
 * <p> A conformant implementation of the CSS module is not required to
 * implement the <code>CSS2Properties</code> interface. If an implementation
 * does implement this interface, the expectation is that language-specific
 * methods can be used to cast from an instance of the
 * <code>CSSStyleDeclaration</code> interface to the
 * <code>CSS2Properties</code> interface.
 * <p> If an implementation does implement this interface, it is expected to
 * understand the specific syntax of the shorthand properties, and apply
 * their semantics; when the <code>margin</code> property is set, for
 * example, the <code>marginTop</code>, <code>marginRight</code>,
 * <code>marginBottom</code> and <code>marginLeft</code> properties are
 * actually being set by the underlying implementation.
 * <p> When dealing with CSS "shorthand" properties, the shorthand properties
 * should be decomposed into their component longhand properties as
 * appropriate, and when querying for their value, the form returned should
 * be the shortest form exactly equivalent to the declarations made in the
 * ruleset. However, if there is no shorthand declaration that could be
 * added to the ruleset without changing in any way the rules already
 * declared in the ruleset (i.e., by adding longhand rules that were
 * previously not declared in the ruleset), then the empty string should be
 * returned for the shorthand property.
 * <p> For example, querying for the <code>font</code> property should not
 * return "normal normal normal 14pt/normal Arial, sans-serif", when "14pt
 * Arial, sans-serif" suffices. (The normals are initial values, and are
 * implied by use of the longhand property.)
 * <p> If the values for all the longhand properties that compose a particular
 * string are the initial values, then a string consisting of all the
 * initial values should be returned (e.g. a <code>border-width</code> value
 * of "medium" should be returned as such, not as "").
 * <p> For some shorthand properties that take missing values from other
 * sides, such as the <code>margin</code>, <code>padding</code>, and
 * <code>border-[width|style|color]</code> properties, the minimum number of
 * sides possible should be used; i.e., "0px 10px" will be returned instead
 * of "0px 10px 0px 10px".
 * <p> If the value of a shorthand property can not be decomposed into its
 * component longhand properties, as is the case for the <code>font</code>
 * property with a value of "menu", querying for the values of the component
 * longhand properties should return the empty string.
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>Document Object Model (DOM) Level 2 Style Specification</a>.
 * <p>
 * <code> CSS2Properties </code>接口代表在<code> CSSStyleDeclaration </code>中检索和设置属性的方便机制。
 * 此接口的属性对应于CSS2中指定的所有属性。
 * 获取此接口的属性是等效的调用<code> CSSStyleDeclaration </code>接口的<code> getPropertyValue </code>方法设置此接口的属性等效于调用<code>
 *  CSSStyleDeclaration </code>方法的<code> setProperty </code> / code> interface <p> CSS模块的一致性实现不需要实现<code>
 *  CSS2Properties </code>接口如果一个实现不实现这个接口,期望的是特定于语言的方法可以用于从<code> CSSStyleDeclaration </code>接口的实例转换到<code>
 *  CSS2Properties </code>接口<p> If一个实现确实实现了这个接口,它期望理解缩写属性的特定语法,并应用它们的语义;当设置<code> margin </code>属性时,例如<code>
 *  marginTop </code>,<code> marginRight </code>,<code> marginBottom </code> / code>属性实际上是由底层实现设置的<p>当处理
 * CSS"速记"属性时,速记属性应该被适当地分解为它们的组件长括号属性,并且当查询它们的值时,返回的表单应该是与规则集中做出的声明完全相同的最短形式但是,如果没有可以添加到规则集的速记声明,而不以任何方式
 * 改变规则集中已经声明的规则(即,通过添加以前未在规则集中声明的长括号规则),则空字符串应该是例如,查询<code> font </code>属性不应该返回"normal normal 14pt / no
 * rmal Arial,sans-serif",当"14pt Arial,sans-serif"(法线是初始值,并通过使用longhand属性隐含)<p>如果组成一个特定字符串的所有longhand属性的
 * 值都是初始值,那么应该返回一个由所有初始值组成的字符串(例如,"medium"的<code> border-width </code>值应该返回,而不是"")<p>对于从其他方面获取缺失值的一些速记属性
 * ,边缘</code>,<code> padding </code>和<code> border- [width | style | color] </code>属性,应尽可能使用最小的边数;即,将返回"
 * 0px 10px"而不是"0px 10px 0px 10px"<p>如果速记属性的值无法分解为其组件长整型属性,如值为"menu"的<code> font </code>属性的情况,请查询组件的值lon
 * ghand属性应返回空字符串<p>另请参见<a href='http://wwww3org/TR/2000/REC-DOM-Level-2-Style-20001113'>文档对象模型(DOM)2级样式
 * 规格</a>。
 * 此接口的属性对应于CSS2中指定的所有属性。
 * 
 * 
 * @since DOM Level 2
 */
public interface CSS2Properties {
    /**
     *  See the azimuth property definition in CSS2.
     * <p>
     * 请参阅CSS2中的azimuth属性定义
     * 
     */
    public String getAzimuth();
    /**
     *  See the azimuth property definition in CSS2.
     * <p>
     *  请参阅CSS2中的azimuth属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setAzimuth(String azimuth)
                                             throws DOMException;

    /**
     *  See the background property definition in CSS2.
     * <p>
     *  请参阅CSS2中的背景属性定义
     * 
     */
    public String getBackground();
    /**
     *  See the background property definition in CSS2.
     * <p>
     *  请参阅CSS2中的背景属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setBackground(String background)
                                             throws DOMException;

    /**
     *  See the background-attachment property definition in CSS2.
     * <p>
     *  请参阅CSS2中的background-attachment属性定义
     * 
     */
    public String getBackgroundAttachment();
    /**
     *  See the background-attachment property definition in CSS2.
     * <p>
     *  请参阅CSS2中的background-attachment属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setBackgroundAttachment(String backgroundAttachment)
                                             throws DOMException;

    /**
     *  See the background-color property definition in CSS2.
     * <p>
     *  请参阅CSS2中的background-color属性定义
     * 
     */
    public String getBackgroundColor();
    /**
     *  See the background-color property definition in CSS2.
     * <p>
     *  请参阅CSS2中的background-color属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setBackgroundColor(String backgroundColor)
                                             throws DOMException;

    /**
     *  See the background-image property definition in CSS2.
     * <p>
     *  请参阅CSS2中的background-image属性定义
     * 
     */
    public String getBackgroundImage();
    /**
     *  See the background-image property definition in CSS2.
     * <p>
     *  请参阅CSS2中的background-image属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setBackgroundImage(String backgroundImage)
                                             throws DOMException;

    /**
     *  See the background-position property definition in CSS2.
     * <p>
     *  请参阅CSS2中的background-position属性定义
     * 
     */
    public String getBackgroundPosition();
    /**
     *  See the background-position property definition in CSS2.
     * <p>
     *  请参阅CSS2中的background-position属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setBackgroundPosition(String backgroundPosition)
                                             throws DOMException;

    /**
     *  See the background-repeat property definition in CSS2.
     * <p>
     *  请参阅CSS2中的background-repeat属性定义
     * 
     */
    public String getBackgroundRepeat();
    /**
     *  See the background-repeat property definition in CSS2.
     * <p>
     *  请参阅CSS2中的background-repeat属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setBackgroundRepeat(String backgroundRepeat)
                                             throws DOMException;

    /**
     *  See the border property definition in CSS2.
     * <p>
     * 请参阅CSS2中的border属性定义
     * 
     */
    public String getBorder();
    /**
     *  See the border property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setBorder(String border)
                                             throws DOMException;

    /**
     *  See the border-collapse property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-collapse属性定义
     * 
     */
    public String getBorderCollapse();
    /**
     *  See the border-collapse property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-collapse属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setBorderCollapse(String borderCollapse)
                                             throws DOMException;

    /**
     *  See the border-color property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-color属性定义
     * 
     */
    public String getBorderColor();
    /**
     *  See the border-color property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-color属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setBorderColor(String borderColor)
                                             throws DOMException;

    /**
     *  See the border-spacing property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-spacing属性定义
     * 
     */
    public String getBorderSpacing();
    /**
     *  See the border-spacing property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-spacing属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setBorderSpacing(String borderSpacing)
                                             throws DOMException;

    /**
     *  See the border-style property definition in CSS2.
     * <p>
     *  请参阅CSS2中的边框样式属性定义
     * 
     */
    public String getBorderStyle();
    /**
     *  See the border-style property definition in CSS2.
     * <p>
     *  请参阅CSS2中的边框样式属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setBorderStyle(String borderStyle)
                                             throws DOMException;

    /**
     *  See the border-top property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-top属性定义
     * 
     */
    public String getBorderTop();
    /**
     *  See the border-top property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-top属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setBorderTop(String borderTop)
                                             throws DOMException;

    /**
     *  See the border-right property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-right属性定义
     * 
     */
    public String getBorderRight();
    /**
     *  See the border-right property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-right属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setBorderRight(String borderRight)
                                             throws DOMException;

    /**
     *  See the border-bottom property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-bottom属性定义
     * 
     */
    public String getBorderBottom();
    /**
     *  See the border-bottom property definition in CSS2.
     * <p>
     * 请参阅CSS2中的border-bottom属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setBorderBottom(String borderBottom)
                                             throws DOMException;

    /**
     *  See the border-left property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-left属性定义
     * 
     */
    public String getBorderLeft();
    /**
     *  See the border-left property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-left属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setBorderLeft(String borderLeft)
                                             throws DOMException;

    /**
     *  See the border-top-color property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-top-color属性定义
     * 
     */
    public String getBorderTopColor();
    /**
     *  See the border-top-color property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-top-color属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setBorderTopColor(String borderTopColor)
                                             throws DOMException;

    /**
     *  See the border-right-color property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-right-color属性定义
     * 
     */
    public String getBorderRightColor();
    /**
     *  See the border-right-color property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-right-color属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setBorderRightColor(String borderRightColor)
                                             throws DOMException;

    /**
     *  See the border-bottom-color property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-bottom-color属性定义
     * 
     */
    public String getBorderBottomColor();
    /**
     *  See the border-bottom-color property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-bottom-color属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setBorderBottomColor(String borderBottomColor)
                                             throws DOMException;

    /**
     *  See the border-left-color property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-left-color属性定义
     * 
     */
    public String getBorderLeftColor();
    /**
     *  See the border-left-color property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-left-color属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setBorderLeftColor(String borderLeftColor)
                                             throws DOMException;

    /**
     *  See the border-top-style property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-top样式属性定义
     * 
     */
    public String getBorderTopStyle();
    /**
     *  See the border-top-style property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-top样式属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setBorderTopStyle(String borderTopStyle)
                                             throws DOMException;

    /**
     *  See the border-right-style property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-right-style属性定义
     * 
     */
    public String getBorderRightStyle();
    /**
     *  See the border-right-style property definition in CSS2.
     * <p>
     * 请参阅CSS2中的border-right-style属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setBorderRightStyle(String borderRightStyle)
                                             throws DOMException;

    /**
     *  See the border-bottom-style property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-bottom样式属性定义
     * 
     */
    public String getBorderBottomStyle();
    /**
     *  See the border-bottom-style property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-bottom样式属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setBorderBottomStyle(String borderBottomStyle)
                                             throws DOMException;

    /**
     *  See the border-left-style property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-left样式属性定义
     * 
     */
    public String getBorderLeftStyle();
    /**
     *  See the border-left-style property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-left样式属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setBorderLeftStyle(String borderLeftStyle)
                                             throws DOMException;

    /**
     *  See the border-top-width property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-top-width属性定义
     * 
     */
    public String getBorderTopWidth();
    /**
     *  See the border-top-width property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-top-width属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setBorderTopWidth(String borderTopWidth)
                                             throws DOMException;

    /**
     *  See the border-right-width property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-right-width属性定义
     * 
     */
    public String getBorderRightWidth();
    /**
     *  See the border-right-width property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-right-width属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setBorderRightWidth(String borderRightWidth)
                                             throws DOMException;

    /**
     *  See the border-bottom-width property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-bottom-width属性定义
     * 
     */
    public String getBorderBottomWidth();
    /**
     *  See the border-bottom-width property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-bottom-width属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setBorderBottomWidth(String borderBottomWidth)
                                             throws DOMException;

    /**
     *  See the border-left-width property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-left-width属性定义
     * 
     */
    public String getBorderLeftWidth();
    /**
     *  See the border-left-width property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-left-width属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setBorderLeftWidth(String borderLeftWidth)
                                             throws DOMException;

    /**
     *  See the border-width property definition in CSS2.
     * <p>
     *  请参阅CSS2中的border-width属性定义
     * 
     */
    public String getBorderWidth();
    /**
     *  See the border-width property definition in CSS2.
     * <p>
     * 请参阅CSS2中的border-width属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setBorderWidth(String borderWidth)
                                             throws DOMException;

    /**
     *  See the bottom property definition in CSS2.
     * <p>
     *  请参阅CSS2中的底部属性定义
     * 
     */
    public String getBottom();
    /**
     *  See the bottom property definition in CSS2.
     * <p>
     *  请参阅CSS2中的底部属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setBottom(String bottom)
                                             throws DOMException;

    /**
     *  See the caption-side property definition in CSS2.
     * <p>
     *  请参阅CSS2中的标题侧属性定义
     * 
     */
    public String getCaptionSide();
    /**
     *  See the caption-side property definition in CSS2.
     * <p>
     *  请参阅CSS2中的标题侧属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setCaptionSide(String captionSide)
                                             throws DOMException;

    /**
     *  See the clear property definition in CSS2.
     * <p>
     *  请参阅CSS2中的清除属性定义
     * 
     */
    public String getClear();
    /**
     *  See the clear property definition in CSS2.
     * <p>
     *  请参阅CSS2中的清除属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setClear(String clear)
                                             throws DOMException;

    /**
     *  See the clip property definition in CSS2.
     * <p>
     *  请参阅CSS2中的剪辑属性定义
     * 
     */
    public String getClip();
    /**
     *  See the clip property definition in CSS2.
     * <p>
     *  请参阅CSS2中的剪辑属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setClip(String clip)
                                             throws DOMException;

    /**
     *  See the color property definition in CSS2.
     * <p>
     *  请参阅CSS2中的颜色属性定义
     * 
     */
    public String getColor();
    /**
     *  See the color property definition in CSS2.
     * <p>
     *  请参阅CSS2中的颜色属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setColor(String color)
                                             throws DOMException;

    /**
     *  See the content property definition in CSS2.
     * <p>
     *  请参阅CSS2中的内容属性定义
     * 
     */
    public String getContent();
    /**
     *  See the content property definition in CSS2.
     * <p>
     *  请参阅CSS2中的内容属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setContent(String content)
                                             throws DOMException;

    /**
     *  See the counter-increment property definition in CSS2.
     * <p>
     *  请参阅CSS2中的counter-increment属性定义
     * 
     */
    public String getCounterIncrement();
    /**
     *  See the counter-increment property definition in CSS2.
     * <p>
     *  请参阅CSS2中的counter-increment属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setCounterIncrement(String counterIncrement)
                                             throws DOMException;

    /**
     *  See the counter-reset property definition in CSS2.
     * <p>
     *  请参阅CSS2中的counter-reset属性定义
     * 
     */
    public String getCounterReset();
    /**
     *  See the counter-reset property definition in CSS2.
     * <p>
     * 请参阅CSS2中的counter-reset属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setCounterReset(String counterReset)
                                             throws DOMException;

    /**
     *  See the cue property definition in CSS2.
     * <p>
     *  请参阅CSS2中的cue属性定义
     * 
     */
    public String getCue();
    /**
     *  See the cue property definition in CSS2.
     * <p>
     *  请参阅CSS2中的cue属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setCue(String cue)
                                             throws DOMException;

    /**
     *  See the cue-after property definition in CSS2.
     * <p>
     *  请参阅CSS2中的提示后属性定义
     * 
     */
    public String getCueAfter();
    /**
     *  See the cue-after property definition in CSS2.
     * <p>
     *  请参阅CSS2中的提示后属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setCueAfter(String cueAfter)
                                             throws DOMException;

    /**
     *  See the cue-before property definition in CSS2.
     * <p>
     *  请参阅CSS2中的cue-before属性定义
     * 
     */
    public String getCueBefore();
    /**
     *  See the cue-before property definition in CSS2.
     * <p>
     *  请参阅CSS2中的cue-before属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setCueBefore(String cueBefore)
                                             throws DOMException;

    /**
     *  See the cursor property definition in CSS2.
     * <p>
     *  请参阅CSS2中的游标属性定义
     * 
     */
    public String getCursor();
    /**
     *  See the cursor property definition in CSS2.
     * <p>
     *  请参阅CSS2中的游标属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setCursor(String cursor)
                                             throws DOMException;

    /**
     *  See the direction property definition in CSS2.
     * <p>
     *  请参阅CSS2中的direction属性定义
     * 
     */
    public String getDirection();
    /**
     *  See the direction property definition in CSS2.
     * <p>
     *  请参阅CSS2中的direction属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setDirection(String direction)
                                             throws DOMException;

    /**
     *  See the display property definition in CSS2.
     * <p>
     *  请参阅CSS2中的display属性定义
     * 
     */
    public String getDisplay();
    /**
     *  See the display property definition in CSS2.
     * <p>
     *  请参阅CSS2中的display属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setDisplay(String display)
                                             throws DOMException;

    /**
     *  See the elevation property definition in CSS2.
     * <p>
     *  请参阅CSS2中的高度属性定义
     * 
     */
    public String getElevation();
    /**
     *  See the elevation property definition in CSS2.
     * <p>
     *  请参阅CSS2中的高度属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setElevation(String elevation)
                                             throws DOMException;

    /**
     *  See the empty-cells property definition in CSS2.
     * <p>
     *  请参阅CSS2中的empty-cells属性定义
     * 
     */
    public String getEmptyCells();
    /**
     *  See the empty-cells property definition in CSS2.
     * <p>
     * 请参阅CSS2中的empty-cells属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setEmptyCells(String emptyCells)
                                             throws DOMException;

    /**
     *  See the float property definition in CSS2.
     * <p>
     *  请参阅CSS2中的float属性定义
     * 
     */
    public String getCssFloat();
    /**
     *  See the float property definition in CSS2.
     * <p>
     *  请参阅CSS2中的float属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setCssFloat(String cssFloat)
                                             throws DOMException;

    /**
     *  See the font property definition in CSS2.
     * <p>
     *  请参阅CSS2中的字体属性定义
     * 
     */
    public String getFont();
    /**
     *  See the font property definition in CSS2.
     * <p>
     *  请参阅CSS2中的字体属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setFont(String font)
                                             throws DOMException;

    /**
     *  See the font-family property definition in CSS2.
     * <p>
     *  请参阅CSS2中的font-family属性定义
     * 
     */
    public String getFontFamily();
    /**
     *  See the font-family property definition in CSS2.
     * <p>
     *  请参阅CSS2中的font-family属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setFontFamily(String fontFamily)
                                             throws DOMException;

    /**
     *  See the font-size property definition in CSS2.
     * <p>
     *  请参阅CSS2中的font-size属性定义
     * 
     */
    public String getFontSize();
    /**
     *  See the font-size property definition in CSS2.
     * <p>
     *  请参阅CSS2中的font-size属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setFontSize(String fontSize)
                                             throws DOMException;

    /**
     *  See the font-size-adjust property definition in CSS2.
     * <p>
     *  请参阅CSS2中的font-size-adjust属性定义
     * 
     */
    public String getFontSizeAdjust();
    /**
     *  See the font-size-adjust property definition in CSS2.
     * <p>
     *  请参见CSS2中的font-size-adjust属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setFontSizeAdjust(String fontSizeAdjust)
                                             throws DOMException;

    /**
     *  See the font-stretch property definition in CSS2.
     * <p>
     *  请参阅CSS2中的font-stretch属性定义
     * 
     */
    public String getFontStretch();
    /**
     *  See the font-stretch property definition in CSS2.
     * <p>
     *  请参阅CSS2中的font-stretch属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setFontStretch(String fontStretch)
                                             throws DOMException;

    /**
     *  See the font-style property definition in CSS2.
     * <p>
     *  请参阅CSS2中的字体样式属性定义
     * 
     */
    public String getFontStyle();
    /**
     *  See the font-style property definition in CSS2.
     * <p>
     *  请参阅CSS2中的字体样式属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setFontStyle(String fontStyle)
                                             throws DOMException;

    /**
     *  See the font-variant property definition in CSS2.
     * <p>
     *  请参阅CSS2中的font-variant属性定义
     * 
     */
    public String getFontVariant();
    /**
     *  See the font-variant property definition in CSS2.
     * <p>
     * 请参阅CSS2中的font-variant属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setFontVariant(String fontVariant)
                                             throws DOMException;

    /**
     *  See the font-weight property definition in CSS2.
     * <p>
     *  请参阅CSS2中的font-weight属性定义
     * 
     */
    public String getFontWeight();
    /**
     *  See the font-weight property definition in CSS2.
     * <p>
     *  请参阅CSS2中的font-weight属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setFontWeight(String fontWeight)
                                             throws DOMException;

    /**
     *  See the height property definition in CSS2.
     * <p>
     *  请参阅CSS2中的height属性定义
     * 
     */
    public String getHeight();
    /**
     *  See the height property definition in CSS2.
     * <p>
     *  请参阅CSS2中的height属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setHeight(String height)
                                             throws DOMException;

    /**
     *  See the left property definition in CSS2.
     * <p>
     *  请参阅CSS2中的左侧属性定义
     * 
     */
    public String getLeft();
    /**
     *  See the left property definition in CSS2.
     * <p>
     *  请参阅CSS2中的左侧属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setLeft(String left)
                                             throws DOMException;

    /**
     *  See the letter-spacing property definition in CSS2.
     * <p>
     *  请参阅CSS2中的letter-spacing属性定义
     * 
     */
    public String getLetterSpacing();
    /**
     *  See the letter-spacing property definition in CSS2.
     * <p>
     *  请参阅CSS2中的letter-spacing属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setLetterSpacing(String letterSpacing)
                                             throws DOMException;

    /**
     *  See the line-height property definition in CSS2.
     * <p>
     *  请参阅CSS2中的行高属性定义
     * 
     */
    public String getLineHeight();
    /**
     *  See the line-height property definition in CSS2.
     * <p>
     *  请参阅CSS2中的行高属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setLineHeight(String lineHeight)
                                             throws DOMException;

    /**
     *  See the list-style property definition in CSS2.
     * <p>
     *  请参阅CSS2中的列表样式属性定义
     * 
     */
    public String getListStyle();
    /**
     *  See the list-style property definition in CSS2.
     * <p>
     *  请参阅CSS2中的列表样式属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setListStyle(String listStyle)
                                             throws DOMException;

    /**
     *  See the list-style-image property definition in CSS2.
     * <p>
     *  请参阅CSS2中的list-style-image属性定义
     * 
     */
    public String getListStyleImage();
    /**
     *  See the list-style-image property definition in CSS2.
     * <p>
     *  请参阅CSS2中的list-style-image属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setListStyleImage(String listStyleImage)
                                             throws DOMException;

    /**
     *  See the list-style-position property definition in CSS2.
     * <p>
     *  请参阅CSS2中的list-style-position属性定义
     * 
     */
    public String getListStylePosition();
    /**
     *  See the list-style-position property definition in CSS2.
     * <p>
     * 请参阅CSS2中的list-style-position属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setListStylePosition(String listStylePosition)
                                             throws DOMException;

    /**
     *  See the list-style-type property definition in CSS2.
     * <p>
     *  请参阅CSS2中的list-style-type属性定义
     * 
     */
    public String getListStyleType();
    /**
     *  See the list-style-type property definition in CSS2.
     * <p>
     *  请参阅CSS2中的list-style-type属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setListStyleType(String listStyleType)
                                             throws DOMException;

    /**
     *  See the margin property definition in CSS2.
     * <p>
     *  请参阅CSS2中的margin属性定义
     * 
     */
    public String getMargin();
    /**
     *  See the margin property definition in CSS2.
     * <p>
     *  请参阅CSS2中的margin属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setMargin(String margin)
                                             throws DOMException;

    /**
     *  See the margin-top property definition in CSS2.
     * <p>
     *  请参阅CSS2中的margin-top属性定义
     * 
     */
    public String getMarginTop();
    /**
     *  See the margin-top property definition in CSS2.
     * <p>
     *  请参阅CSS2中的margin-top属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setMarginTop(String marginTop)
                                             throws DOMException;

    /**
     *  See the margin-right property definition in CSS2.
     * <p>
     *  请参阅CSS2中的margin-right属性定义
     * 
     */
    public String getMarginRight();
    /**
     *  See the margin-right property definition in CSS2.
     * <p>
     *  请参阅CSS2中的margin-right属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setMarginRight(String marginRight)
                                             throws DOMException;

    /**
     *  See the margin-bottom property definition in CSS2.
     * <p>
     *  请参阅CSS2中的margin-bottom属性定义
     * 
     */
    public String getMarginBottom();
    /**
     *  See the margin-bottom property definition in CSS2.
     * <p>
     *  请参阅CSS2中的margin-bottom属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setMarginBottom(String marginBottom)
                                             throws DOMException;

    /**
     *  See the margin-left property definition in CSS2.
     * <p>
     *  请参阅CSS2中的margin-left属性定义
     * 
     */
    public String getMarginLeft();
    /**
     *  See the margin-left property definition in CSS2.
     * <p>
     *  请参阅CSS2中的margin-left属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setMarginLeft(String marginLeft)
                                             throws DOMException;

    /**
     *  See the marker-offset property definition in CSS2.
     * <p>
     *  请参阅CSS2中的marker-offset属性定义
     * 
     */
    public String getMarkerOffset();
    /**
     *  See the marker-offset property definition in CSS2.
     * <p>
     *  请参阅CSS2中的marker-offset属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setMarkerOffset(String markerOffset)
                                             throws DOMException;

    /**
     *  See the marks property definition in CSS2.
     * <p>
     * 请参阅CSS2中的标记属性定义
     * 
     */
    public String getMarks();
    /**
     *  See the marks property definition in CSS2.
     * <p>
     *  请参阅CSS2中的标记属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setMarks(String marks)
                                             throws DOMException;

    /**
     *  See the max-height property definition in CSS2.
     * <p>
     *  请参阅CSS2中的max-height属性定义
     * 
     */
    public String getMaxHeight();
    /**
     *  See the max-height property definition in CSS2.
     * <p>
     *  请参阅CSS2中的max-height属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setMaxHeight(String maxHeight)
                                             throws DOMException;

    /**
     *  See the max-width property definition in CSS2.
     * <p>
     *  请参阅CSS2中的max-width属性定义
     * 
     */
    public String getMaxWidth();
    /**
     *  See the max-width property definition in CSS2.
     * <p>
     *  请参阅CSS2中的max-width属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setMaxWidth(String maxWidth)
                                             throws DOMException;

    /**
     *  See the min-height property definition in CSS2.
     * <p>
     *  请参阅CSS2中的min-height属性定义
     * 
     */
    public String getMinHeight();
    /**
     *  See the min-height property definition in CSS2.
     * <p>
     *  请参阅CSS2中的min-height属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setMinHeight(String minHeight)
                                             throws DOMException;

    /**
     *  See the min-width property definition in CSS2.
     * <p>
     *  请参阅CSS2中的最小宽度属性定义
     * 
     */
    public String getMinWidth();
    /**
     *  See the min-width property definition in CSS2.
     * <p>
     *  请参阅CSS2中的最小宽度属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setMinWidth(String minWidth)
                                             throws DOMException;

    /**
     *  See the orphans property definition in CSS2.
     * <p>
     *  请参阅CSS2中的孤立属性定义
     * 
     */
    public String getOrphans();
    /**
     *  See the orphans property definition in CSS2.
     * <p>
     *  请参阅CSS2中的孤立属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setOrphans(String orphans)
                                             throws DOMException;

    /**
     *  See the outline property definition in CSS2.
     * <p>
     *  请参阅CSS2中的大纲属性定义
     * 
     */
    public String getOutline();
    /**
     *  See the outline property definition in CSS2.
     * <p>
     *  请参阅CSS2中的大纲属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setOutline(String outline)
                                             throws DOMException;

    /**
     *  See the outline-color property definition in CSS2.
     * <p>
     *  请参阅CSS2中的outline-color属性定义
     * 
     */
    public String getOutlineColor();
    /**
     *  See the outline-color property definition in CSS2.
     * <p>
     *  请参阅CSS2中的outline-color属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setOutlineColor(String outlineColor)
                                             throws DOMException;

    /**
     *  See the outline-style property definition in CSS2.
     * <p>
     * 请参阅CSS2中的outline-style属性定义
     * 
     */
    public String getOutlineStyle();
    /**
     *  See the outline-style property definition in CSS2.
     * <p>
     *  请参阅CSS2中的outline-style属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setOutlineStyle(String outlineStyle)
                                             throws DOMException;

    /**
     *  See the outline-width property definition in CSS2.
     * <p>
     *  请参阅CSS2中的outline-width属性定义
     * 
     */
    public String getOutlineWidth();
    /**
     *  See the outline-width property definition in CSS2.
     * <p>
     *  请参阅CSS2中的outline-width属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setOutlineWidth(String outlineWidth)
                                             throws DOMException;

    /**
     *  See the overflow property definition in CSS2.
     * <p>
     *  请参阅CSS2中的overflow属性定义
     * 
     */
    public String getOverflow();
    /**
     *  See the overflow property definition in CSS2.
     * <p>
     *  请参阅CSS2中的overflow属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setOverflow(String overflow)
                                             throws DOMException;

    /**
     *  See the padding property definition in CSS2.
     * <p>
     *  请参阅CSS2中的padding属性定义
     * 
     */
    public String getPadding();
    /**
     *  See the padding property definition in CSS2.
     * <p>
     *  请参阅CSS2中的padding属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setPadding(String padding)
                                             throws DOMException;

    /**
     *  See the padding-top property definition in CSS2.
     * <p>
     *  请参阅CSS2中的padding-top属性定义
     * 
     */
    public String getPaddingTop();
    /**
     *  See the padding-top property definition in CSS2.
     * <p>
     *  请参阅CSS2中的padding-top属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setPaddingTop(String paddingTop)
                                             throws DOMException;

    /**
     *  See the padding-right property definition in CSS2.
     * <p>
     *  请参阅CSS2中的padding-right属性定义
     * 
     */
    public String getPaddingRight();
    /**
     *  See the padding-right property definition in CSS2.
     * <p>
     *  请参阅CSS2中的padding-right属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setPaddingRight(String paddingRight)
                                             throws DOMException;

    /**
     *  See the padding-bottom property definition in CSS2.
     * <p>
     *  请参阅CSS2中的padding-bottom属性定义
     * 
     */
    public String getPaddingBottom();
    /**
     *  See the padding-bottom property definition in CSS2.
     * <p>
     *  请参阅CSS2中的padding-bottom属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setPaddingBottom(String paddingBottom)
                                             throws DOMException;

    /**
     *  See the padding-left property definition in CSS2.
     * <p>
     *  请参阅CSS2中的padding-left属性定义
     * 
     */
    public String getPaddingLeft();
    /**
     *  See the padding-left property definition in CSS2.
     * <p>
     * 请参阅CSS2中的padding-left属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setPaddingLeft(String paddingLeft)
                                             throws DOMException;

    /**
     *  See the page property definition in CSS2.
     * <p>
     *  请参阅CSS2中的页面属性定义
     * 
     */
    public String getPage();
    /**
     *  See the page property definition in CSS2.
     * <p>
     *  请参阅CSS2中的页面属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setPage(String page)
                                             throws DOMException;

    /**
     *  See the page-break-after property definition in CSS2.
     * <p>
     *  请参阅CSS2中的page-break-after属性定义
     * 
     */
    public String getPageBreakAfter();
    /**
     *  See the page-break-after property definition in CSS2.
     * <p>
     *  请参阅CSS2中的page-break-after属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setPageBreakAfter(String pageBreakAfter)
                                             throws DOMException;

    /**
     *  See the page-break-before property definition in CSS2.
     * <p>
     *  请参阅CSS2中的page-break-before属性定义
     * 
     */
    public String getPageBreakBefore();
    /**
     *  See the page-break-before property definition in CSS2.
     * <p>
     *  请参阅CSS2中的page-break-before属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setPageBreakBefore(String pageBreakBefore)
                                             throws DOMException;

    /**
     *  See the page-break-inside property definition in CSS2.
     * <p>
     *  请参阅CSS2中的page-break-inside属性定义
     * 
     */
    public String getPageBreakInside();
    /**
     *  See the page-break-inside property definition in CSS2.
     * <p>
     *  请参阅CSS2中的page-break-inside属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setPageBreakInside(String pageBreakInside)
                                             throws DOMException;

    /**
     *  See the pause property definition in CSS2.
     * <p>
     *  请参阅CSS2中的pause属性定义
     * 
     */
    public String getPause();
    /**
     *  See the pause property definition in CSS2.
     * <p>
     *  请参阅CSS2中的pause属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setPause(String pause)
                                             throws DOMException;

    /**
     *  See the pause-after property definition in CSS2.
     * <p>
     *  请参阅CSS2中的pause-after属性定义
     * 
     */
    public String getPauseAfter();
    /**
     *  See the pause-after property definition in CSS2.
     * <p>
     *  请参阅CSS2中的pause-after属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setPauseAfter(String pauseAfter)
                                             throws DOMException;

    /**
     *  See the pause-before property definition in CSS2.
     * <p>
     *  请参阅CSS2中的pause-before属性定义
     * 
     */
    public String getPauseBefore();
    /**
     *  See the pause-before property definition in CSS2.
     * <p>
     *  请参阅CSS2中的pause-before属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setPauseBefore(String pauseBefore)
                                             throws DOMException;

    /**
     *  See the pitch property definition in CSS2.
     * <p>
     * 请参阅CSS2中的pitch属性定义
     * 
     */
    public String getPitch();
    /**
     *  See the pitch property definition in CSS2.
     * <p>
     *  请参阅CSS2中的pitch属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setPitch(String pitch)
                                             throws DOMException;

    /**
     *  See the pitch-range property definition in CSS2.
     * <p>
     *  请参阅CSS2中的pitch-range属性定义
     * 
     */
    public String getPitchRange();
    /**
     *  See the pitch-range property definition in CSS2.
     * <p>
     *  请参阅CSS2中的pitch-range属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setPitchRange(String pitchRange)
                                             throws DOMException;

    /**
     *  See the play-during property definition in CSS2.
     * <p>
     *  请参阅CSS2中的play-during属性定义
     * 
     */
    public String getPlayDuring();
    /**
     *  See the play-during property definition in CSS2.
     * <p>
     *  请参阅CSS2中的play-during属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setPlayDuring(String playDuring)
                                             throws DOMException;

    /**
     *  See the position property definition in CSS2.
     * <p>
     *  请参阅CSS2中的position属性定义
     * 
     */
    public String getPosition();
    /**
     *  See the position property definition in CSS2.
     * <p>
     *  请参阅CSS2中的position属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setPosition(String position)
                                             throws DOMException;

    /**
     *  See the quotes property definition in CSS2.
     * <p>
     *  请参阅CSS2中的quotes属性定义
     * 
     */
    public String getQuotes();
    /**
     *  See the quotes property definition in CSS2.
     * <p>
     *  请参阅CSS2中的quotes属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setQuotes(String quotes)
                                             throws DOMException;

    /**
     *  See the richness property definition in CSS2.
     * <p>
     *  请参阅CSS2中的richness属性定义
     * 
     */
    public String getRichness();
    /**
     *  See the richness property definition in CSS2.
     * <p>
     *  请参阅CSS2中的richness属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setRichness(String richness)
                                             throws DOMException;

    /**
     *  See the right property definition in CSS2.
     * <p>
     *  在CSS2中查看正确的属性定义
     * 
     */
    public String getRight();
    /**
     *  See the right property definition in CSS2.
     * <p>
     *  在CSS2中查看正确的属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setRight(String right)
                                             throws DOMException;

    /**
     *  See the size property definition in CSS2.
     * <p>
     *  请参阅CSS2中的size属性定义
     * 
     */
    public String getSize();
    /**
     *  See the size property definition in CSS2.
     * <p>
     *  请参阅CSS2中的size属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setSize(String size)
                                             throws DOMException;

    /**
     *  See the speak property definition in CSS2.
     * <p>
     *  请参阅CSS2中的speak属性定义
     * 
     */
    public String getSpeak();
    /**
     *  See the speak property definition in CSS2.
     * <p>
     * 请参阅CSS2中的speak属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setSpeak(String speak)
                                             throws DOMException;

    /**
     *  See the speak-header property definition in CSS2.
     * <p>
     *  请参阅CSS2中的speak-header属性定义
     * 
     */
    public String getSpeakHeader();
    /**
     *  See the speak-header property definition in CSS2.
     * <p>
     *  请参阅CSS2中的speak-header属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setSpeakHeader(String speakHeader)
                                             throws DOMException;

    /**
     *  See the speak-numeral property definition in CSS2.
     * <p>
     *  请参阅CSS2中的speak-numeral属性定义
     * 
     */
    public String getSpeakNumeral();
    /**
     *  See the speak-numeral property definition in CSS2.
     * <p>
     *  请参阅CSS2中的speak-numeral属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setSpeakNumeral(String speakNumeral)
                                             throws DOMException;

    /**
     *  See the speak-punctuation property definition in CSS2.
     * <p>
     *  请参阅CSS2中的speak-punctuation属性定义
     * 
     */
    public String getSpeakPunctuation();
    /**
     *  See the speak-punctuation property definition in CSS2.
     * <p>
     *  请参阅CSS2中的speak-punctuation属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setSpeakPunctuation(String speakPunctuation)
                                             throws DOMException;

    /**
     *  See the speech-rate property definition in CSS2.
     * <p>
     *  请参阅CSS2中的语速属性定义
     * 
     */
    public String getSpeechRate();
    /**
     *  See the speech-rate property definition in CSS2.
     * <p>
     *  请参阅CSS2中的语速属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setSpeechRate(String speechRate)
                                             throws DOMException;

    /**
     *  See the stress property definition in CSS2.
     * <p>
     *  请参阅CSS2中的stress属性定义
     * 
     */
    public String getStress();
    /**
     *  See the stress property definition in CSS2.
     * <p>
     *  请参阅CSS2中的stress属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setStress(String stress)
                                             throws DOMException;

    /**
     *  See the table-layout property definition in CSS2.
     * <p>
     *  请参阅CSS2中的table-layout属性定义
     * 
     */
    public String getTableLayout();
    /**
     *  See the table-layout property definition in CSS2.
     * <p>
     *  请参阅CSS2中的table-layout属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setTableLayout(String tableLayout)
                                             throws DOMException;

    /**
     *  See the text-align property definition in CSS2.
     * <p>
     *  请参阅CSS2中的text-align属性定义
     * 
     */
    public String getTextAlign();
    /**
     *  See the text-align property definition in CSS2.
     * <p>
     *  请参阅CSS2中的text-align属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setTextAlign(String textAlign)
                                             throws DOMException;

    /**
     *  See the text-decoration property definition in CSS2.
     * <p>
     * 请参阅CSS2中的text-decoration属性定义
     * 
     */
    public String getTextDecoration();
    /**
     *  See the text-decoration property definition in CSS2.
     * <p>
     *  请参阅CSS2中的text-decoration属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setTextDecoration(String textDecoration)
                                             throws DOMException;

    /**
     *  See the text-indent property definition in CSS2.
     * <p>
     *  请参阅CSS2中的text-indent属性定义
     * 
     */
    public String getTextIndent();
    /**
     *  See the text-indent property definition in CSS2.
     * <p>
     *  请参阅CSS2中的text-indent属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setTextIndent(String textIndent)
                                             throws DOMException;

    /**
     *  See the text-shadow property definition in CSS2.
     * <p>
     *  请参阅CSS2中的text-shadow属性定义
     * 
     */
    public String getTextShadow();
    /**
     *  See the text-shadow property definition in CSS2.
     * <p>
     *  请参阅CSS2中的text-shadow属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setTextShadow(String textShadow)
                                             throws DOMException;

    /**
     *  See the text-transform property definition in CSS2.
     * <p>
     *  请参阅CSS2中的text-transform属性定义
     * 
     */
    public String getTextTransform();
    /**
     *  See the text-transform property definition in CSS2.
     * <p>
     *  请参阅CSS2中的text-transform属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setTextTransform(String textTransform)
                                             throws DOMException;

    /**
     *  See the top property definition in CSS2.
     * <p>
     *  请参阅CSS2中的顶层属性定义
     * 
     */
    public String getTop();
    /**
     *  See the top property definition in CSS2.
     * <p>
     *  请参阅CSS2中的顶层属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setTop(String top)
                                             throws DOMException;

    /**
     *  See the unicode-bidi property definition in CSS2.
     * <p>
     *  请参阅CSS2中的unicode-bidi属性定义
     * 
     */
    public String getUnicodeBidi();
    /**
     *  See the unicode-bidi property definition in CSS2.
     * <p>
     *  请参阅CSS2中的unicode-bidi属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setUnicodeBidi(String unicodeBidi)
                                             throws DOMException;

    /**
     *  See the vertical-align property definition in CSS2.
     * <p>
     *  请参阅CSS2中的vertical-align属性定义
     * 
     */
    public String getVerticalAlign();
    /**
     *  See the vertical-align property definition in CSS2.
     * <p>
     *  请参阅CSS2中的vertical-align属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setVerticalAlign(String verticalAlign)
                                             throws DOMException;

    /**
     *  See the visibility property definition in CSS2.
     * <p>
     *  请参阅CSS2中的可见性属性定义
     * 
     */
    public String getVisibility();
    /**
     *  See the visibility property definition in CSS2.
     * <p>
     * 请参阅CSS2中的可见性属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setVisibility(String visibility)
                                             throws DOMException;

    /**
     *  See the voice-family property definition in CSS2.
     * <p>
     *  请参阅CSS2中的voice-family属性定义
     * 
     */
    public String getVoiceFamily();
    /**
     *  See the voice-family property definition in CSS2.
     * <p>
     *  请参阅CSS2中的voice-family属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setVoiceFamily(String voiceFamily)
                                             throws DOMException;

    /**
     *  See the volume property definition in CSS2.
     * <p>
     *  请参阅CSS2中的卷属性定义
     * 
     */
    public String getVolume();
    /**
     *  See the volume property definition in CSS2.
     * <p>
     *  请参阅CSS2中的卷属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setVolume(String volume)
                                             throws DOMException;

    /**
     *  See the white-space property definition in CSS2.
     * <p>
     *  请参阅CSS2中的空白空间属性定义
     * 
     */
    public String getWhiteSpace();
    /**
     *  See the white-space property definition in CSS2.
     * <p>
     *  请参阅CSS2中的空白空间属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setWhiteSpace(String whiteSpace)
                                             throws DOMException;

    /**
     *  See the widows property definition in CSS2.
     * <p>
     *  请参阅CSS2中的widows属性定义
     * 
     */
    public String getWidows();
    /**
     *  See the widows property definition in CSS2.
     * <p>
     *  请参阅CSS2中的widows属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setWidows(String widows)
                                             throws DOMException;

    /**
     *  See the width property definition in CSS2.
     * <p>
     *  请参阅CSS2中的width属性定义
     * 
     */
    public String getWidth();
    /**
     *  See the width property definition in CSS2.
     * <p>
     *  请参阅CSS2中的width属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setWidth(String width)
                                             throws DOMException;

    /**
     *  See the word-spacing property definition in CSS2.
     * <p>
     *  请参阅CSS2中的字间距属性定义
     * 
     */
    public String getWordSpacing();
    /**
     *  See the word-spacing property definition in CSS2.
     * <p>
     *  请参阅CSS2中的字间距属性定义
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setWordSpacing(String wordSpacing)
                                             throws DOMException;

    /**
     *  See the z-index property definition in CSS2.
     * <p>
     *  请参阅CSS2中的z-index属性定义
     * 
     */
    public String getZIndex();
    /**
     *  See the z-index property definition in CSS2.
     * <p>
     *  请参阅CSS2中的z-index属性定义
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setZIndex(String zIndex)
                                             throws DOMException;

}
