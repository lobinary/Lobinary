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

/**
 *  The <code>RGBColor</code> interface is used to represent any RGB color
 * value. This interface reflects the values in the underlying style
 * property. Hence, modifications made to the <code>CSSPrimitiveValue</code>
 * objects modify the style property.
 * <p> A specified RGB color is not clipped (even if the number is outside the
 * range 0-255 or 0%-100%). A computed RGB color is clipped depending on the
 * device.
 * <p> Even if a style sheet can only contain an integer for a color value,
 * the internal storage of this integer is a float, and this can be used as
 * a float in the specified or the computed style.
 * <p> A color percentage value can always be converted to a number and vice
 * versa.
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>Document Object Model (DOM) Level 2 Style Specification</a>.
 * <p>
 *  <code> RGBColor </code>接口用于表示任何RGB颜色值。此界面反映底层样式属性中的值。
 * 因此,对<code> CSSPrimitiveValue </code>对象所做的修改会修改style属性。 <p>不剪裁指定的RGB颜色(即使数字超出范围0-255或0％-100％)。
 * 根据设备裁剪计算的RGB颜色。 <p>即使样式表只能包含颜色值的整数,此整数的内部存储也是一个浮点型,并且可以在指定的或计算的样式中用作浮点型。 <p>颜色百分比值总是可以转换为数字,反之亦然。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>文档对象模型(DOM)2级样式规范< a>。
 * 
 * 
 * @since DOM Level 2
 */
public interface RGBColor {
    /**
     *  This attribute is used for the red value of the RGB color.
     * <p>
     *  此属性用于RGB颜色的红色值。
     * 
     */
    public CSSPrimitiveValue getRed();

    /**
     *  This attribute is used for the green value of the RGB color.
     * <p>
     * 此属性用于RGB颜色的绿色值。
     * 
     */
    public CSSPrimitiveValue getGreen();

    /**
     *  This attribute is used for the blue value of the RGB color.
     * <p>
     *  此属性用于RGB颜色的蓝色值。
     */
    public CSSPrimitiveValue getBlue();

}
