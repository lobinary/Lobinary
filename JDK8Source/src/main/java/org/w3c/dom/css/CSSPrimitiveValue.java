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
 *  The <code>CSSPrimitiveValue</code> interface represents a single CSS value
 * . This interface may be used to determine the value of a specific style
 * property currently set in a block or to set a specific style property
 * explicitly within the block. An instance of this interface might be
 * obtained from the <code>getPropertyCSSValue</code> method of the
 * <code>CSSStyleDeclaration</code> interface. A
 * <code>CSSPrimitiveValue</code> object only occurs in a context of a CSS
 * property.
 * <p> Conversions are allowed between absolute values (from millimeters to
 * centimeters, from degrees to radians, and so on) but not between relative
 * values. (For example, a pixel value cannot be converted to a centimeter
 * value.) Percentage values can't be converted since they are relative to
 * the parent value (or another property value). There is one exception for
 * color percentage values: since a color percentage value is relative to
 * the range 0-255, a color percentage value can be converted to a number;
 * (see also the <code>RGBColor</code> interface).
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>Document Object Model (DOM) Level 2 Style Specification</a>.
 * <p>
 * <code> CSSPrimitiveValue </code>接口表示单个CSS值。此接口可用于确定当前在块中设置的特定样式属性的值或在块内显式设置特定样式属性。
 * 该接口的实例可以从<code> CSSStyleDeclaration </code>接口的<code> getPropertyCSSValue </code>方法中获取。
 *  <code> CSSPrimitiveValue </code>对象仅出现在CSS属性的上下文中。 <p>允许在绝对值(从毫米到厘米,从度到弧度等)之间转换,但不允许在相对值之间转换。
 *  (例如,像素值不能转换为厘米值。)百分比值不能转换,因为它们是相对于父值(或另一个属性值)。
 * 颜色百分比值有一个例外：因为颜色百分比值相对于范围0-255,所以可以将颜色百分比值转换为数字; (另见<code> RGBColor </code>界面)。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>文档对象模型(DOM)2级样式规范< a>。
 * 
 * 
 * @since DOM Level 2
 */
public interface CSSPrimitiveValue extends CSSValue {
    // UnitTypes
    /**
     * The value is not a recognized CSS2 value. The value can only be
     * obtained by using the <code>cssText</code> attribute.
     * <p>
     *  该值不是可识别的CSS2值。该值只能通过使用<code> cssText </code>属性获取。
     * 
     */
    public static final short CSS_UNKNOWN               = 0;
    /**
     * The value is a simple number. The value can be obtained by using the
     * <code>getFloatValue</code> method.
     * <p>
     *  该值是一个简单的数字。该值可以通过使用<code> getFloatValue </code>方法获取。
     * 
     */
    public static final short CSS_NUMBER                = 1;
    /**
     * The value is a percentage. The value can be obtained by using the
     * <code>getFloatValue</code> method.
     * <p>
     * 该值为百分比。该值可以通过使用<code> getFloatValue </code>方法获取。
     * 
     */
    public static final short CSS_PERCENTAGE            = 2;
    /**
     * The value is a length (ems). The value can be obtained by using the
     * <code>getFloatValue</code> method.
     * <p>
     *  该值为长度(ems)。该值可以通过使用<code> getFloatValue </code>方法获取。
     * 
     */
    public static final short CSS_EMS                   = 3;
    /**
     * The value is a length (exs). The value can be obtained by using the
     * <code>getFloatValue</code> method.
     * <p>
     *  该值为长度(exs)。该值可以通过使用<code> getFloatValue </code>方法获取。
     * 
     */
    public static final short CSS_EXS                   = 4;
    /**
     * The value is a length (px). The value can be obtained by using the
     * <code>getFloatValue</code> method.
     * <p>
     *  该值为长度(px)。该值可以通过使用<code> getFloatValue </code>方法获取。
     * 
     */
    public static final short CSS_PX                    = 5;
    /**
     * The value is a length (cm). The value can be obtained by using the
     * <code>getFloatValue</code> method.
     * <p>
     *  该值为长度(cm)。该值可以通过使用<code> getFloatValue </code>方法获取。
     * 
     */
    public static final short CSS_CM                    = 6;
    /**
     * The value is a length (mm). The value can be obtained by using the
     * <code>getFloatValue</code> method.
     * <p>
     *  该值为长度(mm)。该值可以通过使用<code> getFloatValue </code>方法获取。
     * 
     */
    public static final short CSS_MM                    = 7;
    /**
     * The value is a length (in). The value can be obtained by using the
     * <code>getFloatValue</code> method.
     * <p>
     *  该值为长度(in)。该值可以通过使用<code> getFloatValue </code>方法获取。
     * 
     */
    public static final short CSS_IN                    = 8;
    /**
     * The value is a length (pt). The value can be obtained by using the
     * <code>getFloatValue</code> method.
     * <p>
     *  该值为长度(pt)。该值可以通过使用<code> getFloatValue </code>方法获取。
     * 
     */
    public static final short CSS_PT                    = 9;
    /**
     * The value is a length (pc). The value can be obtained by using the
     * <code>getFloatValue</code> method.
     * <p>
     *  该值为长度(pc)。该值可以通过使用<code> getFloatValue </code>方法获取。
     * 
     */
    public static final short CSS_PC                    = 10;
    /**
     * The value is an angle (deg). The value can be obtained by using the
     * <code>getFloatValue</code> method.
     * <p>
     *  该值为角度(度)。该值可以通过使用<code> getFloatValue </code>方法获取。
     * 
     */
    public static final short CSS_DEG                   = 11;
    /**
     * The value is an angle (rad). The value can be obtained by using the
     * <code>getFloatValue</code> method.
     * <p>
     *  该值为角(rad)。该值可以通过使用<code> getFloatValue </code>方法获取。
     * 
     */
    public static final short CSS_RAD                   = 12;
    /**
     * The value is an angle (grad). The value can be obtained by using the
     * <code>getFloatValue</code> method.
     * <p>
     *  该值为角度(grad)。该值可以通过使用<code> getFloatValue </code>方法获取。
     * 
     */
    public static final short CSS_GRAD                  = 13;
    /**
     * The value is a time (ms). The value can be obtained by using the
     * <code>getFloatValue</code> method.
     * <p>
     *  该值为时间(ms)。该值可以通过使用<code> getFloatValue </code>方法获取。
     * 
     */
    public static final short CSS_MS                    = 14;
    /**
     * The value is a time (s). The value can be obtained by using the
     * <code>getFloatValue</code> method.
     * <p>
     *  该值是一个时间。该值可以通过使用<code> getFloatValue </code>方法获取。
     * 
     */
    public static final short CSS_S                     = 15;
    /**
     * The value is a frequency (Hz). The value can be obtained by using the
     * <code>getFloatValue</code> method.
     * <p>
     * 该值为频率(Hz)。该值可以通过使用<code> getFloatValue </code>方法获取。
     * 
     */
    public static final short CSS_HZ                    = 16;
    /**
     * The value is a frequency (kHz). The value can be obtained by using the
     * <code>getFloatValue</code> method.
     * <p>
     *  该值为频率(kHz)。该值可以通过使用<code> getFloatValue </code>方法获取。
     * 
     */
    public static final short CSS_KHZ                   = 17;
    /**
     * The value is a number with an unknown dimension. The value can be
     * obtained by using the <code>getFloatValue</code> method.
     * <p>
     *  该值是具有未知维度的数字。该值可以通过使用<code> getFloatValue </code>方法获取。
     * 
     */
    public static final short CSS_DIMENSION             = 18;
    /**
     * The value is a STRING. The value can be obtained by using the
     * <code>getStringValue</code> method.
     * <p>
     *  值为STRING。该值可以通过使用<code> getStringValue </code>方法获得。
     * 
     */
    public static final short CSS_STRING                = 19;
    /**
     * The value is a URI. The value can be obtained by using the
     * <code>getStringValue</code> method.
     * <p>
     *  该值是一个URI。该值可以通过使用<code> getStringValue </code>方法获得。
     * 
     */
    public static final short CSS_URI                   = 20;
    /**
     * The value is an identifier. The value can be obtained by using the
     * <code>getStringValue</code> method.
     * <p>
     *  该值是一个标识符。该值可以通过使用<code> getStringValue </code>方法获得。
     * 
     */
    public static final short CSS_IDENT                 = 21;
    /**
     * The value is a attribute function. The value can be obtained by using
     * the <code>getStringValue</code> method.
     * <p>
     *  该值是一个属性函数。该值可以通过使用<code> getStringValue </code>方法获得。
     * 
     */
    public static final short CSS_ATTR                  = 22;
    /**
     * The value is a counter or counters function. The value can be obtained
     * by using the <code>getCounterValue</code> method.
     * <p>
     *  该值是计数器或计数器函数。该值可以通过使用<code> getCounterValue </code>方法获得。
     * 
     */
    public static final short CSS_COUNTER               = 23;
    /**
     * The value is a rect function. The value can be obtained by using the
     * <code>getRectValue</code> method.
     * <p>
     *  该值是一个rect函数。该值可以通过使用<code> getRectValue </code>方法获得。
     * 
     */
    public static final short CSS_RECT                  = 24;
    /**
     * The value is a RGB color. The value can be obtained by using the
     * <code>getRGBColorValue</code> method.
     * <p>
     *  该值是RGB颜色。该值可以通过使用<code> getRGBColorValue </code>方法获取。
     * 
     */
    public static final short CSS_RGBCOLOR              = 25;

    /**
     * The type of the value as defined by the constants specified above.
     * <p>
     *  由上面指定的常量定义的值的类型。
     * 
     */
    public short getPrimitiveType();

    /**
     *  A method to set the float value with a specified unit. If the property
     * attached with this value can not accept the specified unit or the
     * float value, the value will be unchanged and a
     * <code>DOMException</code> will be raised.
     * <p>
     *  用指定单位设置浮点值的方法。如果与此值附加的属性不能接受指定的单位或浮点值,该值将保持不变,并会引发<code> DOMException </code>。
     * 
     * 
     * @param unitType  A unit code as defined above. The unit code can only
     *   be a float unit type (i.e. <code>CSS_NUMBER</code>,
     *   <code>CSS_PERCENTAGE</code>, <code>CSS_EMS</code>,
     *   <code>CSS_EXS</code>, <code>CSS_PX</code>, <code>CSS_CM</code>,
     *   <code>CSS_MM</code>, <code>CSS_IN</code>, <code>CSS_PT</code>,
     *   <code>CSS_PC</code>, <code>CSS_DEG</code>, <code>CSS_RAD</code>,
     *   <code>CSS_GRAD</code>, <code>CSS_MS</code>, <code>CSS_S</code>,
     *   <code>CSS_HZ</code>, <code>CSS_KHZ</code>,
     *   <code>CSS_DIMENSION</code>).
     * @param floatValue  The new float value.
     * @exception DOMException
     *    INVALID_ACCESS_ERR: Raised if the attached property doesn't support
     *   the float value or the unit type.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setFloatValue(short unitType,
                              float floatValue)
                              throws DOMException;

    /**
     *  This method is used to get a float value in a specified unit. If this
     * CSS value doesn't contain a float value or can't be converted into
     * the specified unit, a <code>DOMException</code> is raised.
     * <p>
     * 此方法用于获取指定单位中的浮点值。如果此CSS值不包含浮点值或无法转换为指定的单位,则会引发<code> DOMException </code>。
     * 
     * 
     * @param unitType  A unit code to get the float value. The unit code can
     *   only be a float unit type (i.e. <code>CSS_NUMBER</code>,
     *   <code>CSS_PERCENTAGE</code>, <code>CSS_EMS</code>,
     *   <code>CSS_EXS</code>, <code>CSS_PX</code>, <code>CSS_CM</code>,
     *   <code>CSS_MM</code>, <code>CSS_IN</code>, <code>CSS_PT</code>,
     *   <code>CSS_PC</code>, <code>CSS_DEG</code>, <code>CSS_RAD</code>,
     *   <code>CSS_GRAD</code>, <code>CSS_MS</code>, <code>CSS_S</code>,
     *   <code>CSS_HZ</code>, <code>CSS_KHZ</code>,
     *   <code>CSS_DIMENSION</code>).
     * @return  The float value in the specified unit.
     * @exception DOMException
     *    INVALID_ACCESS_ERR: Raised if the CSS value doesn't contain a float
     *   value or if the float value can't be converted into the specified
     *   unit.
     */
    public float getFloatValue(short unitType)
                               throws DOMException;

    /**
     *  A method to set the string value with the specified unit. If the
     * property attached to this value can't accept the specified unit or
     * the string value, the value will be unchanged and a
     * <code>DOMException</code> will be raised.
     * <p>
     *  用指定单位设置字符串值的方法。如果附加到此值的属性不能接受指定的单位或字符串值,则该值将保持不变,并会引发<code> DOMException </code>。
     * 
     * 
     * @param stringType  A string code as defined above. The string code can
     *   only be a string unit type (i.e. <code>CSS_STRING</code>,
     *   <code>CSS_URI</code>, <code>CSS_IDENT</code>, and
     *   <code>CSS_ATTR</code>).
     * @param stringValue  The new string value.
     * @exception DOMException
     *    INVALID_ACCESS_ERR: Raised if the CSS value doesn't contain a string
     *   value or if the string value can't be converted into the specified
     *   unit.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setStringValue(short stringType,
                               String stringValue)
                               throws DOMException;

    /**
     *  This method is used to get the string value. If the CSS value doesn't
     * contain a string value, a <code>DOMException</code> is raised.  Some
     * properties (like 'font-family' or 'voice-family') convert a
     * whitespace separated list of idents to a string.
     * <p>
     *  此方法用于获取字符串值。如果CSS值不包含字符串值,则会引发<code> DOMException </code>。
     * 一些属性(如'font-family'或'voice-family')将空格分隔的idents列表转换为字符串。
     * 
     * 
     * @return  The string value in the current unit. The current
     *   <code>primitiveType</code> can only be a string unit type (i.e.
     *   <code>CSS_STRING</code>, <code>CSS_URI</code>,
     *   <code>CSS_IDENT</code> and <code>CSS_ATTR</code>).
     * @exception DOMException
     *    INVALID_ACCESS_ERR: Raised if the CSS value doesn't contain a string
     *   value.
     */
    public String getStringValue()
                                 throws DOMException;

    /**
     *  This method is used to get the Counter value. If this CSS value
     * doesn't contain a counter value, a <code>DOMException</code> is
     * raised. Modification to the corresponding style property can be
     * achieved using the <code>Counter</code> interface.
     * <p>
     *  此方法用于获取计数器值。如果此CSS值不包含计数器值,则会引发<code> DOMException </code>。
     * 使用<code> Counter </code>接口可以实现对相应样式属性的修改。
     * 
     * 
     * @return The Counter value.
     * @exception DOMException
     *    INVALID_ACCESS_ERR: Raised if the CSS value doesn't contain a
     *   Counter value (e.g. this is not <code>CSS_COUNTER</code>).
     */
    public Counter getCounterValue()
                                   throws DOMException;

    /**
     *  This method is used to get the Rect value. If this CSS value doesn't
     * contain a rect value, a <code>DOMException</code> is raised.
     * Modification to the corresponding style property can be achieved
     * using the <code>Rect</code> interface.
     * <p>
     *  此方法用于获取Rect值。如果此CSS值不包含rect值,则会引发<code> DOMException </code>。使用<code> Rect </code>界面可以实现对相应样式属性的修改。
     * 
     * 
     * @return The Rect value.
     * @exception DOMException
     *    INVALID_ACCESS_ERR: Raised if the CSS value doesn't contain a Rect
     *   value. (e.g. this is not <code>CSS_RECT</code>).
     */
    public Rect getRectValue()
                             throws DOMException;

    /**
     *  This method is used to get the RGB color. If this CSS value doesn't
     * contain a RGB color value, a <code>DOMException</code> is raised.
     * Modification to the corresponding style property can be achieved
     * using the <code>RGBColor</code> interface.
     * <p>
     *  此方法用于获取RGB颜色。如果此CSS值不包含RGB颜色值,则会引发<code> DOMException </code>。
     * 使用<code> RGBColor </code>接口可以实现对相应样式属性的修改。
     * 
     * @return the RGB color value.
     * @exception DOMException
     *    INVALID_ACCESS_ERR: Raised if the attached property can't return a
     *   RGB color value (e.g. this is not <code>CSS_RGBCOLOR</code>).
     */
    public RGBColor getRGBColorValue()
                                     throws DOMException;

}
