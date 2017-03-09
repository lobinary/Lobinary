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
 *  The <code>CSSStyleDeclaration</code> interface represents a single CSS
 * declaration block. This interface may be used to determine the style
 * properties currently set in a block or to set style properties explicitly
 * within the block.
 * <p> While an implementation may not recognize all CSS properties within a
 * CSS declaration block, it is expected to provide access to all specified
 * properties in the style sheet through the <code>CSSStyleDeclaration</code>
 *  interface. Furthermore, implementations that support a specific level of
 * CSS should correctly handle CSS shorthand properties for that level. For
 * a further discussion of shorthand properties, see the
 * <code>CSS2Properties</code> interface.
 * <p> This interface is also used to provide a read-only access to the
 * computed values of an element. See also the <code>ViewCSS</code>
 * interface.  The CSS Object Model doesn't provide an access to the
 * specified or actual values of the CSS cascade.
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>Document Object Model (DOM) Level 2 Style Specification</a>.
 * <p>
 * <code> CSSStyleDeclaration </code>接口表示单个CSS声明块。此接口可用于确定当前在块中设置的样式属性或在块内显式设置样式属性。
 *  <p>虽然实现可能无法识别CSS声明块中的所有CSS属性,但希望通过<code> CSSStyleDeclaration </code>界面提供对样式表中所有指定属性的访问。
 * 此外,支持特定级别的CSS的实现应该正确地处理该级别的CSS速记属性。有关速记属性的进一步讨论,请参阅<code> CSS2Properties </code>界面。
 *  <p>此接口还用于对元素的计算值提供只读访问。另见<code> ViewCSS </code>界面。 CSS对象模型不提供对CSS级联的指定值或实际值的访问。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>文档对象模型(DOM)2级样式规范< a>。
 * 
 * 
 * @since DOM Level 2
 */
public interface CSSStyleDeclaration {
    /**
     *  The parsable textual representation of the declaration block
     * (excluding the surrounding curly braces). Setting this attribute will
     * result in the parsing of the new value and resetting of all the
     * properties in the declaration block including the removal or addition
     * of properties.
     * <p>
     *  声明块的可解析文本表示(不包括周围的大括号)。设置此属性将导致解析新值并重置声明块中的所有属性,包括删除或添加属性。
     * 
     */
    public String getCssText();
    /**
     *  The parsable textual representation of the declaration block
     * (excluding the surrounding curly braces). Setting this attribute will
     * result in the parsing of the new value and resetting of all the
     * properties in the declaration block including the removal or addition
     * of properties.
     * <p>
     * 声明块的可解析文本表示(不包括周围的大括号)。设置此属性将导致解析新值并重置声明块中的所有属性,包括删除或添加属性。
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the specified CSS string value has a syntax
     *   error and is unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this declaration is
     *   readonly or a property is readonly.
     */
    public void setCssText(String cssText)
                       throws DOMException;

    /**
     *  Used to retrieve the value of a CSS property if it has been explicitly
     * set within this declaration block.
     * <p>
     *  用于检索CSS属性的值(如果已在此声明块中显式设置)。
     * 
     * 
     * @param propertyName  The name of the CSS property. See the CSS
     *   property index.
     * @return  Returns the value of the property if it has been explicitly
     *   set for this declaration block. Returns the empty string if the
     *   property has not been set.
     */
    public String getPropertyValue(String propertyName);

    /**
     *  Used to retrieve the object representation of the value of a CSS
     * property if it has been explicitly set within this declaration block.
     * This method returns <code>null</code> if the property is a shorthand
     * property. Shorthand property values can only be accessed and modified
     * as strings, using the <code>getPropertyValue</code> and
     * <code>setProperty</code> methods.
     * <p>
     *  用于检索CSS属性的值的对象表示,如果它已在此声明块中显式设置。如果属性是缩写属性,此方法返回<code> null </code>。
     * 速记属性值只能使用<code> getPropertyValue </code>和<code> setProperty </code>方法访问和修改为字符串。
     * 
     * 
     * @param propertyName  The name of the CSS property. See the CSS
     *   property index.
     * @return  Returns the value of the property if it has been explicitly
     *   set for this declaration block. Returns <code>null</code> if the
     *   property has not been set.
     */
    public CSSValue getPropertyCSSValue(String propertyName);

    /**
     *  Used to remove a CSS property if it has been explicitly set within
     * this declaration block.
     * <p>
     *  用于删除CSS属性(如果已在此声明块中显式设置)。
     * 
     * 
     * @param propertyName  The name of the CSS property. See the CSS
     *   property index.
     * @return  Returns the value of the property if it has been explicitly
     *   set for this declaration block. Returns the empty string if the
     *   property has not been set or the property name does not correspond
     *   to a known CSS property.
     * @exception DOMException
     *   NO_MODIFICATION_ALLOWED_ERR: Raised if this declaration is readonly
     *   or the property is readonly.
     */
    public String removeProperty(String propertyName)
                                 throws DOMException;

    /**
     *  Used to retrieve the priority of a CSS property (e.g. the
     * <code>"important"</code> qualifier) if the priority has been
     * explicitly set in this declaration block.
     * <p>
     *  用于检索CSS属性的优先级(例如<code>"important"</code>限定符),如果优先级已在此声明块中显式设置。
     * 
     * 
     * @param propertyName  The name of the CSS property. See the CSS
     *   property index.
     * @return  A string representing the priority (e.g.
     *   <code>"important"</code>) if the property has been explicitly set
     *   in this declaration block and has a priority specified. The empty
     *   string otherwise.
     */
    public String getPropertyPriority(String propertyName);

    /**
     *  Used to set a property value and priority within this declaration
     * block. <code>setProperty</code> permits to modify a property or add a
     * new one in the declaration block. Any call to this method may modify
     * the order of properties in the <code>item</code> method.
     * <p>
     *  用于在此声明块中设置属性值和优先级。 <code> setProperty </code>允许在声明块中修改属性或添加一个新属性。
     * 对此方法的任何调用都可以修改<code> item </code>方法中的属性顺序。
     * 
     * 
     * @param propertyName  The name of the CSS property. See the CSS
     *   property index.
     * @param value  The new value of the property.
     * @param priority  The new priority of the property (e.g.
     *   <code>"important"</code>) or the empty string if none.
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the specified value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this declaration is
     *   readonly or the property is readonly.
     */
    public void setProperty(String propertyName,
                            String value,
                            String priority)
                            throws DOMException;

    /**
     *  The number of properties that have been explicitly set in this
     * declaration block. The range of valid indices is 0 to length-1
     * inclusive.
     * <p>
     *  在此声明块中显式设置的属性数。有效索引的范围是0到长度-1。
     * 
     */
    public int getLength();

    /**
     *  Used to retrieve the properties that have been explicitly set in this
     * declaration block. The order of the properties retrieved using this
     * method does not have to be the order in which they were set. This
     * method can be used to iterate over all properties in this declaration
     * block.
     * <p>
     * 用于检索在此声明块中显式设置的属性。使用此方法检索的属性的顺序不一定是它们设置的顺序。此方法可用于遍历此声明块中的所有属性。
     * 
     * 
     * @param index  Index of the property name to retrieve.
     * @return  The name of the property at this ordinal position. The empty
     *   string if no property exists at this position.
     */
    public String item(int index);

    /**
     *  The CSS rule that contains this declaration block or <code>null</code>
     * if this <code>CSSStyleDeclaration</code> is not attached to a
     * <code>CSSRule</code>.
     * <p>
     *  如果此<css> CSSStyleDeclaration </code>未附加到<code> CSSRule </code>,则包含此声明块或<code> null </code>的CSS规则。
     */
    public CSSRule getParentRule();

}
