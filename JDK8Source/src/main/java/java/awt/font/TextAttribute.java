/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * (C) Copyright Taligent, Inc. 1996 - 1997, All Rights Reserved
 * (C) Copyright IBM Corp. 1996 - 1998, All Rights Reserved
 *
 * The original version of this source code and documentation is
 * copyrighted and owned by Taligent, Inc., a wholly-owned subsidiary
 * of IBM. These materials are provided under terms of a License
 * Agreement between Taligent and Sun. This technology is protected
 * by multiple US and International patents.
 *
 * This notice and attribution to Taligent may not be removed.
 * Taligent is a registered trademark of Taligent, Inc.
 *
 * <p>
 *  (C)版权所有Taligent,Inc 1996  -  1997,保留所有权利(C)版权所有IBM Corp 1996  -  1998,保留所有权利
 * 
 *  此源代码和文档的原始版本受版权保护,并由Taligent,Inc(IBM的全资子公司)拥有。这些资料根据Taligent与Sun之间的许可协议的条款提供此技术受多项美国和国际专利保护
 * 
 *  本通知和Taligent的归属不得删除Taligent是Taligent,Inc的注册商标
 * 
 */

package java.awt.font;

import java.io.InvalidObjectException;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.Map;
import java.util.HashMap;

/**
 * The <code>TextAttribute</code> class defines attribute keys and
 * attribute values used for text rendering.
 * <p>
 * <code>TextAttribute</code> instances are used as attribute keys to
 * identify attributes in
 * {@link java.awt.Font Font},
 * {@link java.awt.font.TextLayout TextLayout},
 * {@link java.text.AttributedCharacterIterator AttributedCharacterIterator},
 * and other classes handling text attributes. Other constants defined
 * in this class can be used as attribute values.
 * <p>
 * For each text attribute, the documentation provides:
 * <UL>
 *   <LI>the type of its value,
 *   <LI>the relevant predefined constants, if any
 *   <LI>the default effect if the attribute is absent
 *   <LI>the valid values if there are limitations
 *   <LI>a description of the effect.
 * </UL>
 * <p>
 * <H3>Values</H3>
 * <UL>
 *   <LI>The values of attributes must always be immutable.
 *   <LI>Where value limitations are given, any value outside of that
 *   set is reserved for future use; the value will be treated as
 *   the default.
 *   <LI>The value <code>null</code> is treated the same as the
 *   default value and results in the default behavior.
 *   <li>If the value is not of the proper type, the attribute
 *   will be ignored.
 *   <li>The identity of the value does not matter, only the actual
 *   value.  For example, <code>TextAttribute.WEIGHT_BOLD</code> and
 *   <code>new Float(2.0)</code>
 *   indicate the same <code>WEIGHT</code>.
 *   <li>Attribute values of type <code>Number</code> (used for
 *   <code>WEIGHT</code>, <code>WIDTH</code>, <code>POSTURE</code>,
 *   <code>SIZE</code>, <code>JUSTIFICATION</code>, and
 *   <code>TRACKING</code>) can vary along their natural range and are
 *   not restricted to the predefined constants.
 *   <code>Number.floatValue()</code> is used to get the actual value
 *   from the <code>Number</code>.
 *   <li>The values for <code>WEIGHT</code>, <code>WIDTH</code>, and
 *   <code>POSTURE</code> are interpolated by the system, which
 *   can select the 'nearest available' font or use other techniques to
 *   approximate the user's request.
 *
 * </UL>
 *
 * <h4>Summary of attributes</h4>
 * <p>
 * <table style="float:center" border="0" cellspacing="0" cellpadding="2" width="%95"
 *     summary="Key, value type, principal constants, and default value
 *     behavior of all TextAttributes">
 * <tr style="background-color:#ccccff">
 * <th valign="TOP" align="CENTER">Key</th>
 * <th valign="TOP" align="CENTER">Value Type</th>
 * <th valign="TOP" align="CENTER">Principal Constants</th>
 * <th valign="TOP" align="CENTER">Default Value</th>
 * </tr>
 * <tr>
 * <td valign="TOP">{@link #FAMILY}</td>
 * <td valign="TOP">String</td>
 * <td valign="TOP">See Font {@link java.awt.Font#DIALOG DIALOG},
 * {@link java.awt.Font#DIALOG_INPUT DIALOG_INPUT},<br> {@link java.awt.Font#SERIF SERIF},
 * {@link java.awt.Font#SANS_SERIF SANS_SERIF}, and {@link java.awt.Font#MONOSPACED MONOSPACED}.
 * </td>
 * <td valign="TOP">"Default" (use platform default)</td>
 * </tr>
 * <tr style="background-color:#eeeeff">
 * <td valign="TOP">{@link #WEIGHT}</td>
 * <td valign="TOP">Number</td>
 * <td valign="TOP">WEIGHT_REGULAR, WEIGHT_BOLD</td>
 * <td valign="TOP">WEIGHT_REGULAR</td>
 * </tr>
 * <tr>
 * <td valign="TOP">{@link #WIDTH}</td>
 * <td valign="TOP">Number</td>
 * <td valign="TOP">WIDTH_CONDENSED, WIDTH_REGULAR,<br>WIDTH_EXTENDED</td>
 * <td valign="TOP">WIDTH_REGULAR</td>
 * </tr>
 * <tr style="background-color:#eeeeff">
 * <td valign="TOP">{@link #POSTURE}</td>
 * <td valign="TOP">Number</td>
 * <td valign="TOP">POSTURE_REGULAR, POSTURE_OBLIQUE</td>
 * <td valign="TOP">POSTURE_REGULAR</td>
 * </tr>
 * <tr>
 * <td valign="TOP">{@link #SIZE}</td>
 * <td valign="TOP">Number</td>
 * <td valign="TOP">none</td>
 * <td valign="TOP">12.0</td>
 * </tr>
 * <tr style="background-color:#eeeeff">
 * <td valign="TOP">{@link #TRANSFORM}</td>
 * <td valign="TOP">{@link TransformAttribute}</td>
 * <td valign="TOP">See TransformAttribute {@link TransformAttribute#IDENTITY IDENTITY}</td>
 * <td valign="TOP">TransformAttribute.IDENTITY</td>
 * </tr>
 * <tr>
 * <td valign="TOP">{@link #SUPERSCRIPT}</td>
 * <td valign="TOP">Integer</td>
 * <td valign="TOP">SUPERSCRIPT_SUPER, SUPERSCRIPT_SUB</td>
 * <td valign="TOP">0 (use the standard glyphs and metrics)</td>
 * </tr>
 * <tr style="background-color:#eeeeff">
 * <td valign="TOP">{@link #FONT}</td>
 * <td valign="TOP">{@link java.awt.Font}</td>
 * <td valign="TOP">none</td>
 * <td valign="TOP">null (do not override font resolution)</td>
 * </tr>
 * <tr>
 * <td valign="TOP">{@link #CHAR_REPLACEMENT}</td>
 * <td valign="TOP">{@link GraphicAttribute}</td>
 * <td valign="TOP">none</td>
 * <td valign="TOP">null (draw text using font glyphs)</td>
 * </tr>
 * <tr style="background-color:#eeeeff">
 * <td valign="TOP">{@link #FOREGROUND}</td>
 * <td valign="TOP">{@link java.awt.Paint}</td>
 * <td valign="TOP">none</td>
 * <td valign="TOP">null (use current graphics paint)</td>
 * </tr>
 * <tr>
 * <td valign="TOP">{@link #BACKGROUND}</td>
 * <td valign="TOP">{@link java.awt.Paint}</td>
 * <td valign="TOP">none</td>
 * <td valign="TOP">null (do not render background)</td>
 * </tr>
 * <tr style="background-color:#eeeeff">
 * <td valign="TOP">{@link #UNDERLINE}</td>
 * <td valign="TOP">Integer</td>
 * <td valign="TOP">UNDERLINE_ON</td>
 * <td valign="TOP">-1 (do not render underline)</td>
 * </tr>
 * <tr>
 * <td valign="TOP">{@link #STRIKETHROUGH}</td>
 * <td valign="TOP">Boolean</td>
 * <td valign="TOP">STRIKETHROUGH_ON</td>
 * <td valign="TOP">false (do not render strikethrough)</td>
 * </tr>
 * <tr style="background-color:#eeeeff">
 * <td valign="TOP">{@link #RUN_DIRECTION}</td>
 * <td valign="TOP">Boolean</td>
 * <td valign="TOP">RUN_DIRECTION_LTR<br>RUN_DIRECTION_RTL</td>
 * <td valign="TOP">null (use {@link java.text.Bidi} standard default)</td>
 * </tr>
 * <tr>
 * <td valign="TOP">{@link #BIDI_EMBEDDING}</td>
 * <td valign="TOP">Integer</td>
 * <td valign="TOP">none</td>
 * <td valign="TOP">0 (use base line direction)</td>
 * </tr>
 * <tr style="background-color:#eeeeff">
 * <td valign="TOP">{@link #JUSTIFICATION}</td>
 * <td valign="TOP">Number</td>
 * <td valign="TOP">JUSTIFICATION_FULL</td>
 * <td valign="TOP">JUSTIFICATION_FULL</td>
 * </tr>
 * <tr>
 * <td valign="TOP">{@link #INPUT_METHOD_HIGHLIGHT}</td>
 * <td valign="TOP">{@link java.awt.im.InputMethodHighlight},<br>{@link java.text.Annotation}</td>
 * <td valign="TOP">(see class)</td>
 * <td valign="TOP">null (do not apply input highlighting)</td>
 * </tr>
 * <tr style="background-color:#eeeeff">
 * <td valign="TOP">{@link #INPUT_METHOD_UNDERLINE}</td>
 * <td valign="TOP">Integer</td>
 * <td valign="TOP">UNDERLINE_LOW_ONE_PIXEL,<br>UNDERLINE_LOW_TWO_PIXEL</td>
 * <td valign="TOP">-1 (do not render underline)</td>
 * </tr>
 * <tr>
 * <td valign="TOP">{@link #SWAP_COLORS}</td>
 * <td valign="TOP">Boolean</td>
 * <td valign="TOP">SWAP_COLORS_ON</td>
 * <td valign="TOP">false (do not swap colors)</td>
 * </tr>
 * <tr style="background-color:#eeeeff">
 * <td valign="TOP">{@link #NUMERIC_SHAPING}</td>
 * <td valign="TOP">{@link java.awt.font.NumericShaper}</td>
 * <td valign="TOP">none</td>
 * <td valign="TOP">null (do not shape digits)</td>
 * </tr>
 * <tr>
 * <td valign="TOP">{@link #KERNING}</td>
 * <td valign="TOP">Integer</td>
 * <td valign="TOP">KERNING_ON</td>
 * <td valign="TOP">0 (do not request kerning)</td>
 * </tr>
 * <tr style="background-color:#eeeeff">
 * <td valign="TOP">{@link #LIGATURES}</td>
 * <td valign="TOP">Integer</td>
 * <td valign="TOP">LIGATURES_ON</td>
 * <td valign="TOP">0 (do not form optional ligatures)</td>
 * </tr>
 * <tr>
 * <td valign="TOP">{@link #TRACKING}</td>
 * <td valign="TOP">Number</td>
 * <td valign="TOP">TRACKING_LOOSE, TRACKING_TIGHT</td>
 * <td valign="TOP">0 (do not add tracking)</td>
 * </tr>
 * </table>
 *
 * <p>
 *  <code> TextAttribute </code>类定义用于文本呈现的属性键和属性值
 * <p>
 * <code> TextAttribute </code>实例用作属性键来标识{@link javaawtFont Font},{@link javaawtfontTextLayout TextLayout}
 * ,{@link javatextAttributedCharacterIterator AttributedCharacterIterator}和其他处理文本属性的类中的属性。
 * 类可以用作属性值。
 * <p>
 *  对于每个文本属性,文档提供：
 * <UL>
 *  <LI>其值的类型,<LI>相关的预定义常数,如果存在<LI>如果属性不存在则为默认效果<LI>如果存在限制,则为有效值<LI>对效果的描述
 * </UL>
 * <p>
 *  <H3>值</H3>
 * <UL>
 * <LI>属性的值必须始终是不可变的<LI>在给出值限制的情况下,该集合之外的任何值保留供将来使用;该值将被视为默认值<LI>值<code> null </code>被视为与默认值相同,并导致默认行为<li>
 * 如果值不是正确的类型,则属性将被忽略<li>该值的标识无关紧要,只有实际值例如,<code> TextAttributeWEIGHT_BOLD </code>和<code> new Float(20)</code>
 *  </code><li> <code> </code>类型的属性值(用于<code> WEIGHT </code>,<code> WIDTH </code>,<code> POSTURE </code>
 *  / code>,<code> JUSTIFICATION </code>和<code> TRACKING </code>)可以沿着它们的自然范围变化,而不限于预定义的常量<code> Numberfl
 * oatValue获取<code> Number </code> <li>的实际值<code> WEIGHT </code>,<code> WIDTH </code>和<code> POSTURE </code>
 * 的值由该系统可以选择"最近可用"字体或使用其他技术来近似用户的请求。
 * 
 * </UL>
 * 
 * <h4>属性摘要</h4>
 * <p>
 *  <table style ="float：center"border ="0"cellspacing ="0"cellpadding ="2"width ="％95"summary ="键,值类型,主体常数和默认值。
 * behavior of all TextAttributes">
 * <tr style="background-color:#ccccff">
 *  <th valign ="TOP"align ="CENTER">键</th> <th valign ="TOP"align ="CENTER">值类型</th> <th valign ="TOP"align ="CENTER">
 * 主要常数</th> <th valign ="TOP"align ="CENTER">默认值</th>。
 * </tr>
 * <tr>
 *  <td valign ="TOP"> {@ link #FAMILY} </td> <td valign ="TOP">字符串</td> <td valign ="TOP">查看字体{@link javaawtFont#DIALOG DIALOG}
 *  {@link javaawtFont#DIALOG_INPUT DIALOG_INPUT},<br> {@link javaawtFont#SERIF SERIF},{@link javaawtFont#SANS_SERIF SANS_SERIF}
 * 和{@link javaawtFont#MONOSPACED MONOSPACED}。
 * </td>
 *  <td valign ="TOP">"默认"(使用平台默认)</td>
 * </tr>
 * <tr style="background-color:#eeeeff">
 * <td valign ="TOP"> {@ link #WEIGHT} </td> <td valign ="TOP">号码</td> <td valign ="TOP"> WEIGHT_REGULAR
 * ,WEIGHT_BOLD </td> <td valign = "TOP"> WEIGHT_REGULAR </td>。
 * </tr>
 * <tr>
 *  <td valign ="TOP"> {@ link #WIDTH} </td> <td valign ="TOP">号码</td> <td valign ="TOP"> WIDTH_CONDENSE
 * D,WIDTH_REGULAR,<br> WIDTH_EXTENDED </td > <td valign ="TOP"> WIDTH_REGULAR </td>。
 * </tr>
 * <tr style="background-color:#eeeeff">
 *  <td valign ="TOP"> {@ link #POSTURE} </td> <td valign ="TOP">号码</td> <td valign ="TOP"> POSTURE_REGU
 * LAR,POSTURE_OBLIQUE </td> <td valign = "TOP"> POSTURE_REGULAR </td>。
 * </tr>
 * <tr>
 *  <td valign ="TOP"> {@ link #SIZE} </td> <td valign ="TOP">数字</td> <td valign ="TOP">无</td> <td valign = ">
 *  120 </td>。
 * </tr>
 * <tr style="background-color:#eeeeff">
 * <td valign ="TOP"> {@ link #TRANSFORM} </td> <td valign ="TOP"> {@ link TransformAttribute} </td> <td valign ="TOP">
 * 请参阅TransformAttribute {@link TransformAttribute# IDENTITY IDENTITY} </td> <td valign ="TOP"> Transfor
 * mAttributeIDENTITY </td>。
 * </tr>
 * <tr>
 *  <td valign ="TOP"> {@ link #SUPERSCRIPT} </td> <td valign ="TOP">整数</td> <td valign ="TOP"> SUPERSCR
 * IPT_SUPER,SUPERSCRIPT_SUB </td> <td valign = "TOP"> 0(使用标准字形和指标)</td>。
 * </tr>
 * <tr style="background-color:#eeeeff">
 *  <td valign ="TOP"> {@ link #FONT} </td> <td valign ="TOP"> {@ link javaawtFont} </td> <td valign ="TOP">
 * 无</td> <td valign ="TOP"> null(不覆盖字体分辨率)</td>。
 * </tr>
 * <tr>
 *  <td valign ="TOP"> {@ link #CHAR_REPLACEMENT} </td> <td valign ="TOP"> {@ link GraphicAttribute} </td>
 *  <td valign ="TOP">无</td> <td valign ="TOP"> null(使用字形字形绘制文本)</td>。
 * </tr>
 * <tr style="background-color:#eeeeff">
 * <td valign ="TOP"> {@ link #FOREGROUND} </td> <td valign ="TOP"> {@ link javaawtPaint} </td> <td valign ="TOP">
 * 无</td> <td valign ="TOP"> null(使用当前图形绘制)</td>。
 * </tr>
 * <tr>
 *  <td valign ="TOP"> {@ link #BACKGROUND} </td> <td valign ="TOP"> {@ link javaawtPaint} </td> <td valign ="TOP">
 * 无</td> <td valign ="TOP"> null(不呈现背景)</td>。
 * </tr>
 * <tr style="background-color:#eeeeff">
 *  <td valign ="TOP"> {@ link #UNDERLINE} </td> <td valign ="TOP">整数</td> <td valign ="TOP"> UNDERLINE_
 * ON </td> <td valign = "> -1(不显示下划线)</td>。
 * </tr>
 * <tr>
 *  <td valign ="TOP"> {@ link #STRIKETHROUGH} </td> <td valign ="TOP">布尔</td> <td valign ="TOP"> STRIKE
 * THROUGH_ON </td> <td valign = "> false(不显示删除线)</td>。
 * </tr>
 * <tr style="background-color:#eeeeff">
 * <td valign ="TOP"> {@ link #RUN_DIRECTION} </td> <td valign ="TOP">布尔</td> <td valign ="TOP"> RUN_DIR
 * ECTION_LTR <br> RUN_DIRECTION_RTL </td> <td valign ="TOP"> null(使用{@link javatextBidi}标准默认值)</td>。
 * </tr>
 * <tr>
 *  <td valign ="TOP"> {@ link #BIDI_EMBEDDING} </td> <td valign ="TOP">整数</td> <td valign ="TOP">无</td>
 *  <td valign = "> 0(使用基线方向)</td>。
 * </tr>
 * <tr style="background-color:#eeeeff">
 *  <td valign ="TOP"> {@ link #JUSTIFICATION} </td> <td valign ="TOP">号码</td> <td valign ="TOP"> JUSTIF
 * ICATION_FULL </td> <td valign = "> JUSTIFICATION_FULL </td>。
 * </tr>
 * <tr>
 *  <td valign ="TOP"> {@ link #INPUT_METHOD_HIGHLIGHT} </td> <td valign ="TOP"> {@ link javaawtimInputMethodHighlight}
 * ,<br> {@link javatextAnnotation} </td> <td valign ="TOP ">(见类)</td> <td valign ="TOP"> null(不应用输入突出显示
 * )</td>。
 * </tr>
 * <tr style="background-color:#eeeeff">
 * <td valign ="TOP"> {@ link #INPUT_METHOD_UNDERLINE} </td> <td valign ="TOP">整数</td> <td valign ="TOP">
 *  UNDERLINE_LOW_ONE_PIXEL,<br> UNDERLINE_LOW_TWO_PIXEL </td> < td valign ="TOP">  -  1(不显示下划线)</td>。
 * </tr>
 * <tr>
 *  <td valign ="TOP"> {@ link #SWAP_COLORS} </td> <td valign ="TOP">布尔</td> <td valign ="TOP"> SWAP_COL
 * ORS_ON </td> <td valign = "> false(不交换颜色)</td>。
 * </tr>
 * <tr style="background-color:#eeeeff">
 *  <td valign ="TOP"> {@ link #NUMERIC_SHAPING} </td> <td valign ="TOP"> {@ link javaawtfontNumericShaper}
 *  </td> <td valign ="TOP">无</td> <td valign ="TOP"> null(不要输入数字)</td>。
 * </tr>
 * <tr>
 *  <td valign ="TOP"> {@ link #KERNING} </td> <td valign ="TOP">整数</td> <td valign ="TOP"> KERNING_ON </td>
 *  <td valign = "> 0(不要求字距调整)</td>。
 * </tr>
 * <tr style="background-color:#eeeeff">
 * <td valign ="TOP"> {@ link #LIGATURES} </td> <td valign ="TOP">整数</td> <td valign ="TOP"> LIGATURES_O
 * N </td> <td valign = "> 0(不要形成可选连字)</td>。
 * </tr>
 * <tr>
 *  <td valign ="TOP"> {@ link #TRACKING} </td> <td valign ="TOP">号码</td> <td valign ="TOP"> TRACKING_LO
 * OSE,TRACKING_TIGHT </td> <td valign = "TOP"> 0(不添加跟踪)</td>。
 * </tr>
 * </table>
 * 
 * 
 * @see java.awt.Font
 * @see java.awt.font.TextLayout
 * @see java.text.AttributedCharacterIterator
 */
public final class TextAttribute extends Attribute {

    // table of all instances in this class, used by readResolve
    private static final Map<String, TextAttribute>
            instanceMap = new HashMap<String, TextAttribute>(29);

    /**
     * Constructs a <code>TextAttribute</code> with the specified name.
     * <p>
     *  构造具有指定名称的<code> TextAttribute </code>
     * 
     * 
     * @param name the attribute name to assign to this
     * <code>TextAttribute</code>
     */
    protected TextAttribute(String name) {
        super(name);
        if (this.getClass() == TextAttribute.class) {
            instanceMap.put(name, this);
        }
    }

    /**
     * Resolves instances being deserialized to the predefined constants.
     * <p>
     *  解析反序列化为预定义常量的实例
     * 
     */
    protected Object readResolve() throws InvalidObjectException {
        if (this.getClass() != TextAttribute.class) {
            throw new InvalidObjectException(
                "subclass didn't correctly implement readResolve");
        }

        TextAttribute instance = instanceMap.get(getName());
        if (instance != null) {
            return instance;
        } else {
            throw new InvalidObjectException("unknown attribute name");
        }
    }

    // Serialization compatibility with Java 2 platform v1.2.
    // 1.2 will throw an InvalidObjectException if ever asked to
    // deserialize INPUT_METHOD_UNDERLINE.
    // This shouldn't happen in real life.
    static final long serialVersionUID = 7744112784117861702L;

    //
    // For use with Font.
    //

    /**
     * Attribute key for the font name.  Values are instances of
     * <b><code>String</code></b>.  The default value is
     * <code>"Default"</code>, which causes the platform default font
     * family to be used.
     *
     * <p> The <code>Font</code> class defines constants for the logical
     * font names
     * {@link java.awt.Font#DIALOG DIALOG},
     * {@link java.awt.Font#DIALOG_INPUT DIALOG_INPUT},
     * {@link java.awt.Font#SANS_SERIF SANS_SERIF},
     * {@link java.awt.Font#SERIF SERIF}, and
     * {@link java.awt.Font#MONOSPACED MONOSPACED}.
     *
     * <p>This defines the value passed as <code>name</code> to the
     * <code>Font</code> constructor.  Both logical and physical
     * font names are allowed. If a font with the requested name
     * is not found, the default font is used.
     *
     * <p><em>Note:</em> This attribute is unfortunately misnamed, as
     * it specifies the face name and not just the family.  Thus
     * values such as "Lucida Sans Bold" will select that face if it
     * exists.  Note, though, that if the requested face does not
     * exist, the default will be used with <em>regular</em> weight.
     * The "Bold" in the name is part of the face name, not a separate
     * request that the font's weight be bold.</p>
     * <p>
     *  字体名称的属性键值是<b> <code> String </code> </b>的实例默认值为<code>"Default"</code>,这将导致使用平台默认字体族
     * 
     * <p> <code> Font </code>类定义逻辑字体名称的常量{@link javaawtFont#DIALOG DIALOG},{@link javaawtFont#DIALOG_INPUT DIALOG_INPUT}
     * ,{@link javaawtFont#SANS_SERIF SANS_SERIF},{@link javaawtFont#SERIF SERIF}和{@link javaawtFont#MONOSPACED MONOSPACED}
     * 。
     * 
     *  <p>定义作为<code> name </code>传递给<code> Font </code>构造函数的值。允许使用逻辑和物理字体名称如果找不到具有请求的名称的字体,用来
     * 
     * <p> <em>注意：</em>不幸的是,这个属性错误地命名,因为它指定了面部名称,而不只是家庭。
     * 因此,诸如"Lucida Sans Bold"这样的值将选择那个面部,如果存在注意,如果请求的面部不存在,默认值将与<em> regular </em> weight一起使用。
     * 名称中的"Bold"是面部名称的一部分,而不是字体的粗体为粗体的单独请求</p >。
     * 
     */
    public static final TextAttribute FAMILY =
        new TextAttribute("family");

    /**
     * Attribute key for the weight of a font.  Values are instances
     * of <b><code>Number</code></b>.  The default value is
     * <code>WEIGHT_REGULAR</code>.
     *
     * <p>Several constant values are provided, see {@link
     * #WEIGHT_EXTRA_LIGHT}, {@link #WEIGHT_LIGHT}, {@link
     * #WEIGHT_DEMILIGHT}, {@link #WEIGHT_REGULAR}, {@link
     * #WEIGHT_SEMIBOLD}, {@link #WEIGHT_MEDIUM}, {@link
     * #WEIGHT_DEMIBOLD}, {@link #WEIGHT_BOLD}, {@link #WEIGHT_HEAVY},
     * {@link #WEIGHT_EXTRABOLD}, and {@link #WEIGHT_ULTRABOLD}.  The
     * value <code>WEIGHT_BOLD</code> corresponds to the
     * style value <code>Font.BOLD</code> as passed to the
     * <code>Font</code> constructor.
     *
     * <p>The value is roughly the ratio of the stem width to that of
     * the regular weight.
     *
     * <p>The system can interpolate the provided value.
     * <p>
     *  字体的权重的属性键值是<b> <code> Number </code> </b>的实例默认值为<code> WEIGHT_REGULAR </code>
     * 
     * <p>提供了几个常数值,请参阅{@link #WEIGHT_EXTRA_LIGHT},{@link #WEIGHT_LIGHT},{@link #WEIGHT_DEMILIGHT},{@link #WEIGHT_REGULAR}
     * ,{@link #WEIGHT_SEMIBOLD},{@link #WEIGHT_MEDIUM },{@link #WEIGHT_DEMIBOLD},{@link #WEIGHT_BOLD},{@link #WEIGHT_HEAVY}
     * ,{@link #WEIGHT_EXTRABOLD}和{@link #WEIGHT_ULTRABOLD}值<code> WEIGHT_BOLD </code>样式值<code> FontBOLD </code>
     * 传递给<code> Font </code>构造函数。
     * 
     *  <p>该值大致为茎宽度与常规重量的比率
     * 
     *  <p>系统可以内插所提供的值
     * 
     */
    public static final TextAttribute WEIGHT =
        new TextAttribute("weight");

    /**
     * The lightest predefined weight.
     * <p>
     *  最轻的预定义重量
     * 
     * 
     * @see #WEIGHT
     */
    public static final Float WEIGHT_EXTRA_LIGHT =
        Float.valueOf(0.5f);

    /**
     * The standard light weight.
     * <p>
     *  标准重量轻
     * 
     * 
     * @see #WEIGHT
     */
    public static final Float WEIGHT_LIGHT =
        Float.valueOf(0.75f);

    /**
     * An intermediate weight between <code>WEIGHT_LIGHT</code> and
     * <code>WEIGHT_STANDARD</code>.
     * <p>
     *  <code> WEIGHT_LIGHT </code>和<code> WEIGHT_STANDARD </code>之间的中间权重
     * 
     * 
     * @see #WEIGHT
     */
    public static final Float WEIGHT_DEMILIGHT =
        Float.valueOf(0.875f);

    /**
     * The standard weight. This is the default value for <code>WEIGHT</code>.
     * <p>
     * 标准权重这是<code> WEIGHT </code>的默认值
     * 
     * 
     * @see #WEIGHT
     */
    public static final Float WEIGHT_REGULAR =
        Float.valueOf(1.0f);

    /**
     * A moderately heavier weight than <code>WEIGHT_REGULAR</code>.
     * <p>
     *  重量比<code> WEIGHT_REGULAR </code>稍重
     * 
     * 
     * @see #WEIGHT
     */
    public static final Float WEIGHT_SEMIBOLD =
        Float.valueOf(1.25f);

    /**
     * An intermediate weight between <code>WEIGHT_REGULAR</code> and
     * <code>WEIGHT_BOLD</code>.
     * <p>
     *  <code> WEIGHT_REGULAR </code>和<code> WEIGHT_BOLD </code>之间的中间权重
     * 
     * 
     * @see #WEIGHT
     */
    public static final Float WEIGHT_MEDIUM =
        Float.valueOf(1.5f);

    /**
     * A moderately lighter weight than <code>WEIGHT_BOLD</code>.
     * <p>
     *  重量轻于<code> WEIGHT_BOLD </code>
     * 
     * 
     * @see #WEIGHT
     */
    public static final Float WEIGHT_DEMIBOLD =
        Float.valueOf(1.75f);

    /**
     * The standard bold weight.
     * <p>
     *  标准粗体重量
     * 
     * 
     * @see #WEIGHT
     */
    public static final Float WEIGHT_BOLD =
        Float.valueOf(2.0f);

    /**
     * A moderately heavier weight than <code>WEIGHT_BOLD</code>.
     * <p>
     *  重量比<code> WEIGHT_BOLD </code>稍重
     * 
     * 
     * @see #WEIGHT
     */
    public static final Float WEIGHT_HEAVY =
        Float.valueOf(2.25f);

    /**
     * An extra heavy weight.
     * <p>
     *  额外的重量
     * 
     * 
     * @see #WEIGHT
     */
    public static final Float WEIGHT_EXTRABOLD =
        Float.valueOf(2.5f);

    /**
     * The heaviest predefined weight.
     * <p>
     *  最重的预定义重量
     * 
     * 
     * @see #WEIGHT
     */
    public static final Float WEIGHT_ULTRABOLD =
        Float.valueOf(2.75f);

    /**
     * Attribute key for the width of a font.  Values are instances of
     * <b><code>Number</code></b>.  The default value is
     * <code>WIDTH_REGULAR</code>.
     *
     * <p>Several constant values are provided, see {@link
     * #WIDTH_CONDENSED}, {@link #WIDTH_SEMI_CONDENSED}, {@link
     * #WIDTH_REGULAR}, {@link #WIDTH_SEMI_EXTENDED}, {@link
     * #WIDTH_EXTENDED}.
     *
     * <p>The value is roughly the ratio of the advance width to that
     * of the regular width.
     *
     * <p>The system can interpolate the provided value.
     * <p>
     *  字体宽度的属性键值是<b> <code> Number </code> </b>的实例默认值为<code> WIDTH_REGULAR </code>
     * 
     *  <p>提供了几个常数值,请参阅{@link #WIDTH_CONDENSED},{@link #WIDTH_SEMI_CONDENSED},{@link #WIDTH_REGULAR},{@link #WIDTH_SEMI_EXTENDED}
     * ,{@link #WIDTH_EXTENDED}。
     * 
     * <p>该值大致为提前宽度与常规宽度的比率
     * 
     *  <p>系统可以内插所提供的值
     * 
     */
    public static final TextAttribute WIDTH =
        new TextAttribute("width");

    /**
     * The most condensed predefined width.
     * <p>
     *  最稠密的预定义宽度
     * 
     * 
     * @see #WIDTH
     */
    public static final Float WIDTH_CONDENSED =
        Float.valueOf(0.75f);

    /**
     * A moderately condensed width.
     * <p>
     *  中度凝结宽度
     * 
     * 
     * @see #WIDTH
     */
    public static final Float WIDTH_SEMI_CONDENSED =
        Float.valueOf(0.875f);

    /**
     * The standard width. This is the default value for
     * <code>WIDTH</code>.
     * <p>
     *  标准宽度这是<code> WIDTH </code>的默认值
     * 
     * 
     * @see #WIDTH
     */
    public static final Float WIDTH_REGULAR =
        Float.valueOf(1.0f);

    /**
     * A moderately extended width.
     * <p>
     *  适度延伸宽度
     * 
     * 
     * @see #WIDTH
     */
    public static final Float WIDTH_SEMI_EXTENDED =
        Float.valueOf(1.25f);

    /**
     * The most extended predefined width.
     * <p>
     *  最大扩展的预定义宽度
     * 
     * 
     * @see #WIDTH
     */
    public static final Float WIDTH_EXTENDED =
        Float.valueOf(1.5f);

    /**
     * Attribute key for the posture of a font.  Values are instances
     * of <b><code>Number</code></b>. The default value is
     * <code>POSTURE_REGULAR</code>.
     *
     * <p>Two constant values are provided, {@link #POSTURE_REGULAR}
     * and {@link #POSTURE_OBLIQUE}. The value
     * <code>POSTURE_OBLIQUE</code> corresponds to the style value
     * <code>Font.ITALIC</code> as passed to the <code>Font</code>
     * constructor.
     *
     * <p>The value is roughly the slope of the stems of the font,
     * expressed as the run over the rise.  Positive values lean right.
     *
     * <p>The system can interpolate the provided value.
     *
     * <p>This will affect the font's italic angle as returned by
     * <code>Font.getItalicAngle</code>.
     *
     * <p>
     *  字体姿势的属性键值是<b> <code> Number </code> </b>的实例默认值为<code> POSTURE_REGULAR </code>
     * 
     *  <p>提供两个常数值{@link #POSTURE_REGULAR}和{@link #POSTURE_OBLIQUE}值<code> POSTURE_OBLIQUE </code>对应于传递给<code>
     * 的样式值<code> FontITALIC </code>代码>字体</code>构造函数。
     * 
     * <p>值大致是字体的茎的斜率,表示为上升时的运行正值向右
     * 
     *  <p>系统可以内插所提供的值
     * 
     *  <p>这将影响由<code> FontgetItalicAngle </code>返回的字体的斜体角度。
     * 
     * 
     * @see java.awt.Font#getItalicAngle()
     */
    public static final TextAttribute POSTURE =
        new TextAttribute("posture");

    /**
     * The standard posture, upright.  This is the default value for
     * <code>POSTURE</code>.
     * <p>
     *  标准姿势,直立这是<code> POSTURE </code>的默认值
     * 
     * 
     * @see #POSTURE
     */
    public static final Float POSTURE_REGULAR =
        Float.valueOf(0.0f);

    /**
     * The standard italic posture.
     * <p>
     *  标准斜体姿势
     * 
     * 
     * @see #POSTURE
     */
    public static final Float POSTURE_OBLIQUE =
        Float.valueOf(0.20f);

    /**
     * Attribute key for the font size.  Values are instances of
     * <b><code>Number</code></b>.  The default value is 12pt.
     *
     * <p>This corresponds to the <code>size</code> parameter to the
     * <code>Font</code> constructor.
     *
     * <p>Very large or small sizes will impact rendering performance,
     * and the rendering system might not render text at these sizes.
     * Negative sizes are illegal and result in the default size.
     *
     * <p>Note that the appearance and metrics of a 12pt font with a
     * 2x transform might be different than that of a 24 point font
     * with no transform.
     * <p>
     *  字体大小的属性键值是<b> <code> Number </code> </b>的实例默认值为12pt
     * 
     *  <p>这对应于<code> Font </code>构造函数的<code> size </code>参数
     * 
     *  <p>非常大或小的大小会影响呈现效果,并且呈现系统可能无法以这些大小呈现文字。负大小是非法的,并导致默认大小
     * 
     * <p>请注意,具有2x变换的12pt字体的外观和度量可能与不带变换的24点字体的外观和度量不同
     * 
     */
    public static final TextAttribute SIZE =
        new TextAttribute("size");

    /**
     * Attribute key for the transform of a font.  Values are
     * instances of <b><code>TransformAttribute</code></b>.  The
     * default value is <code>TransformAttribute.IDENTITY</code>.
     *
     * <p>The <code>TransformAttribute</code> class defines the
     * constant {@link TransformAttribute#IDENTITY IDENTITY}.
     *
     * <p>This corresponds to the transform passed to
     * <code>Font.deriveFont(AffineTransform)</code>.  Since that
     * transform is mutable and <code>TextAttribute</code> values must
     * not be, the <code>TransformAttribute</code> wrapper class is
     * used.
     *
     * <p>The primary intent is to support scaling and skewing, though
     * other effects are possible.</p>
     *
     * <p>Some transforms will cause the baseline to be rotated and/or
     * shifted.  The text and the baseline are transformed together so
     * that the text follows the new baseline.  For example, with text
     * on a horizontal baseline, the new baseline follows the
     * direction of the unit x vector passed through the
     * transform. Text metrics are measured against this new baseline.
     * So, for example, with other things being equal, text rendered
     * with a rotated TRANSFORM and an unrotated TRANSFORM will measure as
     * having the same ascent, descent, and advance.</p>
     *
     * <p>In styled text, the baselines for each such run are aligned
     * one after the other to potentially create a non-linear baseline
     * for the entire run of text. For more information, see {@link
     * TextLayout#getLayoutPath}.</p>
     *
     * <p>
     *  字体变换的属性键值是<b> <code> TransformAttribute </code> </b>的实例。
     * 默认值为<code> TransformAttributeIDENTITY </code>。
     * 
     *  <p> <code> TransformAttribute </code>类定义常量{@link TransformAttribute#IDENTITY IDENTITY}
     * 
     *  <p>这对应于传递给<code> FontderiveFont(AffineTransform)的变换</code>由于该变换是可变的,并且<code> TextAttribute </code>值不
     * 能包含,因此<code> TransformAttribute </code>类被使用。
     * 
     *  <p>主要目的是支持缩放和倾斜,尽管其他效果也是可能的</p>
     * 
     * <p>一些变换将导致基线被旋转和/或偏移文本和基线被一起变换,使得文本跟随新的基线。例如,对于水平基线上的文本,新的基线沿着通过变换的单位x向量相对于该新的基准测量文本度量。
     * 例如,在其他条件相等的情况下,利用旋转的TRANSFORM和未旋转的TRANSFORM呈现的文本将测量为具有相同的上升,下降, p>。
     * 
     *  <p>在样式文本中,每个此类运行的基线一个接一个地对齐,以有可能为整个文本运行创建非线性基线。有关详细信息,请参阅{@link TextLayout#getLayoutPath} </p>
     * 
     * 
     * @see TransformAttribute
     * @see java.awt.geom.AffineTransform
     */
     public static final TextAttribute TRANSFORM =
        new TextAttribute("transform");

    /**
     * Attribute key for superscripting and subscripting.  Values are
     * instances of <b><code>Integer</code></b>.  The default value is
     * 0, which means that no superscript or subscript is used.
     *
     * <p>Two constant values are provided, see {@link
     * #SUPERSCRIPT_SUPER} and {@link #SUPERSCRIPT_SUB}.  These have
     * the values 1 and -1 respectively.  Values of
     * greater magnitude define greater levels of superscript or
     * subscripting, for example, 2 corresponds to super-superscript,
     * 3 to super-super-superscript, and similarly for negative values
     * and subscript, up to a level of 7 (or -7).  Values beyond this
     * range are reserved; behavior is platform-dependent.
     *
     * <p><code>SUPERSCRIPT</code> can
     * impact the ascent and descent of a font.  The ascent
     * and descent can never become negative, however.
     * <p>
     * 上标和下标的属性键值是<b> <code> Integer </code> </b>的实例。默认值为0,这意味着不使用上标或下标
     * 
     *  <p>提供了两个常量值,请参见{@link #SUPERSCRIPT_SUPER}和{@link #SUPERSCRIPT_SUB}这些值的值分别为1和-1。
     * 较大值的值定义较大级别的上标或下标,例如,2对应于超上标,3到超超上标,以及类似地,对于负值和下标,高达7(或-7)的水平保留超出该范围的值;行为是平台相关的。
     * 
     *  <p> <code> SUPERSCRIPT </code>可以影响字体的上升和下降上升和下降永远不会变为负值,但是
     * 
     */
    public static final TextAttribute SUPERSCRIPT =
        new TextAttribute("superscript");

    /**
     * Standard superscript.
     * <p>
     *  标准上标
     * 
     * 
     * @see #SUPERSCRIPT
     */
    public static final Integer SUPERSCRIPT_SUPER =
        Integer.valueOf(1);

    /**
     * Standard subscript.
     * <p>
     * 标准下标
     * 
     * 
     * @see #SUPERSCRIPT
     */
    public static final Integer SUPERSCRIPT_SUB =
        Integer.valueOf(-1);

    /**
     * Attribute key used to provide the font to use to render text.
     * Values are instances of {@link java.awt.Font}.  The default
     * value is null, indicating that normal resolution of a
     * <code>Font</code> from attributes should be performed.
     *
     * <p><code>TextLayout</code> and
     * <code>AttributedCharacterIterator</code> work in terms of
     * <code>Maps</code> of <code>TextAttributes</code>.  Normally,
     * all the attributes are examined and used to select and
     * configure a <code>Font</code> instance.  If a <code>FONT</code>
     * attribute is present, though, its associated <code>Font</code>
     * will be used.  This provides a way for users to override the
     * resolution of font attributes into a <code>Font</code>, or
     * force use of a particular <code>Font</code> instance.  This
     * also allows users to specify subclasses of <code>Font</code> in
     * cases where a <code>Font</code> can be subclassed.
     *
     * <p><code>FONT</code> is used for special situations where
     * clients already have a <code>Font</code> instance but still
     * need to use <code>Map</code>-based APIs.  Typically, there will
     * be no other attributes in the <code>Map</code> except the
     * <code>FONT</code> attribute.  With <code>Map</code>-based APIs
     * the common case is to specify all attributes individually, so
     * <code>FONT</code> is not needed or desireable.
     *
     * <p>However, if both <code>FONT</code> and other attributes are
     * present in the <code>Map</code>, the rendering system will
     * merge the attributes defined in the <code>Font</code> with the
     * additional attributes.  This merging process classifies
     * <code>TextAttributes</code> into two groups.  One group, the
     * 'primary' group, is considered fundamental to the selection and
     * metric behavior of a font.  These attributes are
     * <code>FAMILY</code>, <code>WEIGHT</code>, <code>WIDTH</code>,
     * <code>POSTURE</code>, <code>SIZE</code>,
     * <code>TRANSFORM</code>, <code>SUPERSCRIPT</code>, and
     * <code>TRACKING</code>. The other group, the 'secondary' group,
     * consists of all other defined attributes, with the exception of
     * <code>FONT</code> itself.
     *
     * <p>To generate the new <code>Map</code>, first the
     * <code>Font</code> is obtained from the <code>FONT</code>
     * attribute, and <em>all</em> of its attributes extracted into a
     * new <code>Map</code>.  Then only the <em>secondary</em>
     * attributes from the original <code>Map</code> are added to
     * those in the new <code>Map</code>.  Thus the values of primary
     * attributes come solely from the <code>Font</code>, and the
     * values of secondary attributes originate with the
     * <code>Font</code> but can be overridden by other values in the
     * <code>Map</code>.
     *
     * <p><em>Note:</em><code>Font's</code> <code>Map</code>-based
     * constructor and <code>deriveFont</code> methods do not process
     * the <code>FONT</code> attribute, as these are used to create
     * new <code>Font</code> objects.  Instead, {@link
     * java.awt.Font#getFont(Map) Font.getFont(Map)} should be used to
     * handle the <code>FONT</code> attribute.
     *
     * <p>
     *  用于提供用于渲染文本的字体的属性键值是{@link javaawtFont}的实例默认值为null,表示应该执行属性中<code> Font </code>的正常解析
     * 
     * <p> <code> TextLayout </code>和<code> AttributedCharacterIterator </code>按照<code> TextAttributes </code>
     * 的<code> Maps </code>工作。
     * 通常,所有属性都被检查和使用选择并配置<code> Font </code>实例如果存在<code> FONT </code>属性,则将使用与其关联的<code> Font </code>将字体属性的分
     * 辨率覆盖为<code> Font </code>或强制使用特定的<code> Font </code>实例。
     * 这还允许用户在case中指定<code> Font </code>其中<code> Font </code>可以被子类化。
     * 
     * <p> <code> FONT </code>用于客户端已经具有<code> Font </code>实例但仍需要使用<code> Map </code>的API的特殊情况通常,除了<code> FON
     * T </code>属性,<code> Map </code>中没有其他属性使用基于<code> Map </code>的API,通常的情况是单独指定所有属性,因此<code > FONT </code>
     * 不需要或不可取。
     * 
     * <p>但是,如果<code> Map </code>中同时存在<code> FONT </code>和其他属性,则呈现系统会将<code> Font </code>中定义的属性附加属性此合并过程将<code>
     *  TextAttributes </code>分为两组一个组,"主"组,被认为是字体的选择和度量行为的基础这些属性是<code> FAMILY </code> ,<code> WEIGHT </code>
     * ,<code> WIDTH </code>,<code> POSTURE </code>,<code> SIZE </code>,<code> TRANSFORM </code> </code>和<code>
     *  TRACKING </code>另一个组"secondary"组由所有其他定义的属性组成,除了<code> FONT </code>。
     * 
     * <p>要生成新的<code> Map </code>,请先从<code> FONT </code>属性中获取<code> Font </code>,然后<em>其属性被提取到新的<code> Map </code>
     * 中。
     * 然后,只有来自原始<code> Map </code>的<em>代码>因此,主要属性的值完全来自于<code> Font </code>,次要属性的值来源于<code> Font </code>,但是可
     * 以被<code>地图</code>。
     * 
     * <p> <em>注意：</em> <code> Font的</code> <code>基于地图</code>的构造函数和<code> derivFont </code>方法不处理<code> FONT 
     * < / code>属性,因为这些用于创建新的<code> Font </code>对象而应使用{@link javaawtFont#getFont(Map)FontgetFont(Map)}来处理<code>
     *  FONT </code >属性。
     * 
     * 
     * @see java.awt.Font
     */
    public static final TextAttribute FONT =
        new TextAttribute("font");

    /**
     * Attribute key for a user-defined glyph to display in lieu
     * of the font's standard glyph for a character.  Values are
     * intances of GraphicAttribute.  The default value is null,
     * indicating that the standard glyphs provided by the font
     * should be used.
     *
     * <p>This attribute is used to reserve space for a graphic or
     * other component embedded in a line of text.  It is required for
     * correct positioning of 'inline' components within a line when
     * bidirectional reordering (see {@link java.text.Bidi}) is
     * performed.  Each character (Unicode code point) will be
     * rendered using the provided GraphicAttribute. Typically, the
     * characters to which this attribute is applied should be
     * <code>&#92;uFFFC</code>.
     *
     * <p>The GraphicAttribute determines the logical and visual
     * bounds of the text; the actual Font values are ignored.
     *
     * <p>
     *  属性键用于显示用户定义字形以替代字符的字体的标准字形值是GraphicAttribute的内部缺省值为null,表示应使用字体提供的标准字形
     * 
     * <p>此属性用于为嵌入在文本行中的图形或其他组件保留空间在执行双向重新排序(请参阅{@link javatextBidi})时,需要在行内正确定位"内联"组件每个字符(Unicode代码点)将使用提供的
     * GraphicAttribute呈现。
     * 通常,应用此属性的字符应为<code> \\ uFFFC </code>。
     * 
     *  <p> GraphicAttribute确定文本的逻辑和视觉边界;实际的字体值将被忽略
     * 
     * 
     * @see GraphicAttribute
     */
    public static final TextAttribute CHAR_REPLACEMENT =
        new TextAttribute("char_replacement");

    //
    // Adornments added to text.
    //

    /**
     * Attribute key for the paint used to render the text.  Values are
     * instances of <b><code>Paint</code></b>.  The default value is
     * null, indicating that the <code>Paint</code> set on the
     * <code>Graphics2D</code> at the time of rendering is used.
     *
     * <p>Glyphs will be rendered using this
     * <code>Paint</code> regardless of the <code>Paint</code> value
     * set on the <code>Graphics</code> (but see {@link #SWAP_COLORS}).
     *
     * <p>
     *  用于渲染文本的颜色的属性键值是<b> <code> Paint </code> </b>的实例默认值为null,表示<code> Paint </code>代码> Graphics2D </code>
     * 。
     * 
     * <p>不管<code> Graphics </code>(但参见{@link #SWAP_COLORS})上设置的<code> Paint </code>值,使用<code> Paint </code>
     * 。
     * 
     * 
     * @see java.awt.Paint
     * @see #SWAP_COLORS
     */
    public static final TextAttribute FOREGROUND =
        new TextAttribute("foreground");

    /**
     * Attribute key for the paint used to render the background of
     * the text.  Values are instances of <b><code>Paint</code></b>.
     * The default value is null, indicating that the background
     * should not be rendered.
     *
     * <p>The logical bounds of the text will be filled using this
     * <code>Paint</code>, and then the text will be rendered on top
     * of it (but see {@link #SWAP_COLORS}).
     *
     * <p>The visual bounds of the text is extended to include the
     * logical bounds, if necessary.  The outline is not affected.
     *
     * <p>
     *  用于渲染文本背景的颜色的属性键值是<b> <code> Paint </code> </b>的实例默认值为null,表示不应渲染背景
     * 
     *  <p>文本的逻辑边界将使用<code> Paint </code>填充,然后文本将显示在其顶部(但请参阅{@link #SWAP_COLORS})
     * 
     *  <p>如果需要,文本的视觉边界会扩展为包括逻辑边界。大纲不受影响
     * 
     * 
     * @see java.awt.Paint
     * @see #SWAP_COLORS
     */
    public static final TextAttribute BACKGROUND =
        new TextAttribute("background");

    /**
     * Attribute key for underline.  Values are instances of
     * <b><code>Integer</code></b>.  The default value is -1, which
     * means no underline.
     *
     * <p>The constant value {@link #UNDERLINE_ON} is provided.
     *
     * <p>The underline affects both the visual bounds and the outline
     * of the text.
     * <p>
     *  下划线的属性键值为<b> <code> Integer </code> </b>的实例默认值为-1,表示没有下划线
     * 
     * <p>提供常量值{@link #UNDERLINE_ON}
     * 
     *  <p>下划线同时影响文本的视觉边界和轮廓
     * 
     */
    public static final TextAttribute UNDERLINE =
        new TextAttribute("underline");

    /**
     * Standard underline.
     *
     * <p>
     *  标准下划线
     * 
     * 
     * @see #UNDERLINE
     */
    public static final Integer UNDERLINE_ON =
        Integer.valueOf(0);

    /**
     * Attribute key for strikethrough.  Values are instances of
     * <b><code>Boolean</code></b>.  The default value is
     * <code>false</code>, which means no strikethrough.
     *
     * <p>The constant value {@link #STRIKETHROUGH_ON} is provided.
     *
     * <p>The strikethrough affects both the visual bounds and the
     * outline of the text.
     * <p>
     *  删除的属性键值是<b> <code> Boolean </code> </b>的实例默认值为<code> false </code>,表示无删除线
     * 
     *  <p>提供常数值{@link #STRIKETHROUGH_ON}
     * 
     *  <p>删除线同时影响文本的视觉边界和轮廓
     * 
     */
    public static final TextAttribute STRIKETHROUGH =
        new TextAttribute("strikethrough");

    /**
     * A single strikethrough.
     *
     * <p>
     *  一个删除线
     * 
     * 
     * @see #STRIKETHROUGH
     */
    public static final Boolean STRIKETHROUGH_ON =
        Boolean.TRUE;

    //
    // Attributes use to control layout of text on a line.
    //

    /**
     * Attribute key for the run direction of the line.  Values are
     * instances of <b><code>Boolean</code></b>.  The default value is
     * null, which indicates that the standard Bidi algorithm for
     * determining run direction should be used with the value {@link
     * java.text.Bidi#DIRECTION_DEFAULT_LEFT_TO_RIGHT}.
     *
     * <p>The constants {@link #RUN_DIRECTION_RTL} and {@link
     * #RUN_DIRECTION_LTR} are provided.
     *
     * <p>This determines the value passed to the {@link
     * java.text.Bidi} constructor to select the primary direction of
     * the text in the paragraph.
     *
     * <p><em>Note:</em> This attribute should have the same value for
     * all the text in a paragraph, otherwise the behavior is
     * undetermined.
     *
     * <p>
     *  线的运行方向的属性键值是<b> <code> Boolean </code> </b>的实例。
     * 默认值为null,表示用于确定运行方向的标准Bidi算法应与值{@link javatextBidi#DIRECTION_DEFAULT_LEFT_TO_RIGHT}。
     * 
     * <p>提供常数{@link #RUN_DIRECTION_RTL}和{@link #RUN_DIRECTION_LTR}
     * 
     *  <p>这决定传递给{@link javatextBidi}构造函数的值,以选择段落中文本的主要方向
     * 
     *  <p> <em>注意：</em>此属性应该对段落中的所有文本具有相同的值,否则行为不确定
     * 
     * 
     * @see java.text.Bidi
     */
    public static final TextAttribute RUN_DIRECTION =
        new TextAttribute("run_direction");

    /**
     * Left-to-right run direction.
     * <p>
     *  从左到右的运行方向
     * 
     * 
     * @see #RUN_DIRECTION
     */
    public static final Boolean RUN_DIRECTION_LTR =
        Boolean.FALSE;

    /**
     * Right-to-left run direction.
     * <p>
     *  从右到左的运行方向
     * 
     * 
     * @see #RUN_DIRECTION
     */
    public static final Boolean RUN_DIRECTION_RTL =
        Boolean.TRUE;

    /**
     * Attribute key for the embedding level of the text.  Values are
     * instances of <b><code>Integer</code></b>.  The default value is
     * <code>null</code>, indicating that the the Bidirectional
     * algorithm should run without explicit embeddings.
     *
     * <p>Positive values 1 through 61 are <em>embedding</em> levels,
     * negative values -1 through -61 are <em>override</em> levels.
     * The value 0 means that the base line direction is used.  These
     * levels are passed in the embedding levels array to the {@link
     * java.text.Bidi} constructor.
     *
     * <p><em>Note:</em> When this attribute is present anywhere in
     * a paragraph, then any Unicode bidi control characters (RLO,
     * LRO, RLE, LRE, and PDF) in the paragraph are
     * disregarded, and runs of text where this attribute is not
     * present are treated as though it were present and had the value
     * 0.
     *
     * <p>
     *  文本嵌入级别的属性键值是<b> <code> Integer </code> </b>的实例默认值为<code> null </code>,表示双向算法应该显式嵌入
     * 
     * <p>正值1到61是<em>嵌入</em>级别,负值-1到-61是<em>覆盖</em>级别值0表示使用基线方向这些级别在embeding levels数组中传递给{@link javatextBidi}
     * 构造函数。
     * 
     *  <p> <em>注意：</em>当此属性存在于段落中的任何位置时,段落中的任何Unicode双向控制字符(RLO,LRO,RLE,LRE和PDF)其中此属性不存在被视为存在并具有值0
     * 
     * 
     * @see java.text.Bidi
     */
    public static final TextAttribute BIDI_EMBEDDING =
        new TextAttribute("bidi_embedding");

    /**
     * Attribute key for the justification of a paragraph.  Values are
     * instances of <b><code>Number</code></b>.  The default value is
     * 1, indicating that justification should use the full width
     * provided.  Values are pinned to the range [0..1].
     *
     * <p>The constants {@link #JUSTIFICATION_FULL} and {@link
     * #JUSTIFICATION_NONE} are provided.
     *
     * <p>Specifies the fraction of the extra space to use when
     * justification is requested on a <code>TextLayout</code>. For
     * example, if the line is 50 points wide and it is requested to
     * justify to 70 points, a value of 0.75 will pad to use
     * three-quarters of the remaining space, or 15 points, so that
     * the resulting line will be 65 points in length.
     *
     * <p><em>Note:</em> This should have the same value for all the
     * text in a paragraph, otherwise the behavior is undetermined.
     *
     * <p>
     * 段落对齐的属性键值是<b> <code> Number </code> </b>的实例默认值为1,表示对齐方式应使用提供的全宽度值固定在范围[01 ]]
     * 
     *  <p>提供常数{@link #JUSTIFICATION_FULL}和{@link #JUSTIFICATION_NONE}
     * 
     *  <p>指定当在<code> TextLayout </code>上请求对齐时要使用的额外空间的分数。
     * 例如,如果线宽为50点,并且请求对齐为70点,值为075将填充到使用剩余空间的四分之三,或15点,使得得到的线将是65个点的长度。
     * 
     *  <p> <em>注意：</em>这应该对段落中的所有文本具有相同的值,否则行为是不确定的
     * 
     * 
     * @see TextLayout#getJustifiedLayout
     */
    public static final TextAttribute JUSTIFICATION =
        new TextAttribute("justification");

    /**
     * Justify the line to the full requested width.  This is the
     * default value for <code>JUSTIFICATION</code>.
     * <p>
     * 将该行对齐到请求的完整宽度这是<code> JUSTIFICATION </code>的默认值
     * 
     * 
     * @see #JUSTIFICATION
     */
    public static final Float JUSTIFICATION_FULL =
        Float.valueOf(1.0f);

    /**
     * Do not allow the line to be justified.
     * <p>
     *  不要允许线对齐
     * 
     * 
     * @see #JUSTIFICATION
     */
    public static final Float JUSTIFICATION_NONE =
        Float.valueOf(0.0f);

    //
    // For use by input method.
    //

    /**
     * Attribute key for input method highlight styles.
     *
     * <p>Values are instances of {@link
     * java.awt.im.InputMethodHighlight} or {@link
     * java.text.Annotation}.  The default value is <code>null</code>,
     * which means that input method styles should not be applied
     * before rendering.
     *
     * <p>If adjacent runs of text with the same
     * <code>InputMethodHighlight</code> need to be rendered
     * separately, the <code>InputMethodHighlights</code> should be
     * wrapped in <code>Annotation</code> instances.
     *
     * <p>Input method highlights are used while text is being
     * composed by an input method. Text editing components should
     * retain them even if they generally only deal with unstyled
     * text, and make them available to the drawing routines.
     *
     * <p>
     *  输入法突出显示样式的属性键
     * 
     *  <p>值是{@link javaawtimInputMethodHighlight}或{@link javatextAnnotation}的实例默认值为<code> null </code>,这意味着
     * 输入法样式不应在渲染之前应用。
     * 
     *  <p>如果需要单独渲染具有相同<code> InputMethodHighlight </code>的文本的相邻运行,则<code> InputMethodHighlights </code>应包含在
     * <code>注释</code>实例。
     * 
     * <p>在通过输入法编写文本时使用输入法突出显示。文本编辑组件应保留它们,即使它们通常只处理未修饰的文本,并使它们可用于绘图程序
     * 
     * 
     * @see java.awt.Font
     * @see java.awt.im.InputMethodHighlight
     * @see java.text.Annotation
     */
    public static final TextAttribute INPUT_METHOD_HIGHLIGHT =
        new TextAttribute("input method highlight");

    /**
     * Attribute key for input method underlines.  Values
     * are instances of <b><code>Integer</code></b>.  The default
     * value is <code>-1</code>, which means no underline.
     *
     * <p>Several constant values are provided, see {@link
     * #UNDERLINE_LOW_ONE_PIXEL}, {@link #UNDERLINE_LOW_TWO_PIXEL},
     * {@link #UNDERLINE_LOW_DOTTED}, {@link #UNDERLINE_LOW_GRAY}, and
     * {@link #UNDERLINE_LOW_DASHED}.
     *
     * <p>This may be used in conjunction with {@link #UNDERLINE} if
     * desired.  The primary purpose is for use by input methods.
     * Other use of these underlines for simple ornamentation might
     * confuse users.
     *
     * <p>The input method underline affects both the visual bounds and
     * the outline of the text.
     *
     * <p>
     *  输入法的属性键下划线值是<b> <code> Integer </code> </b>的实例默认值为<code> -1 </code>
     * 
     *  <p>提供了几个常数值,请参阅{@link #UNDERLINE_LOW_ONE_PIXEL},{@link #UNDERLINE_LOW_TWO_PIXEL},{@link #UNDERLINE_LOW_DOTTED}
     * ,{@link #UNDERLINE_LOW_GRAY}和{@link #UNDERLINE_LOW_DASHED}。
     * 
     *  <p>这可以与{@link #UNDERLINE}结合使用(如果需要)主要目的是供输入法使用其他使用这些下划线的简单装饰可能会混淆用户
     * 
     * <p>输入法下划线会影响文本的视觉边界和轮廓
     * 
     * 
     * @since 1.3
     */
    public static final TextAttribute INPUT_METHOD_UNDERLINE =
        new TextAttribute("input method underline");

    /**
     * Single pixel solid low underline.
     * <p>
     *  单像素实线下划线
     * 
     * 
     * @see #INPUT_METHOD_UNDERLINE
     * @since 1.3
     */
    public static final Integer UNDERLINE_LOW_ONE_PIXEL =
        Integer.valueOf(1);

    /**
     * Double pixel solid low underline.
     * <p>
     *  双像素实线下划线
     * 
     * 
     * @see #INPUT_METHOD_UNDERLINE
     * @since 1.3
     */
    public static final Integer UNDERLINE_LOW_TWO_PIXEL =
        Integer.valueOf(2);

    /**
     * Single pixel dotted low underline.
     * <p>
     *  单像素点缀低下划线
     * 
     * 
     * @see #INPUT_METHOD_UNDERLINE
     * @since 1.3
     */
    public static final Integer UNDERLINE_LOW_DOTTED =
        Integer.valueOf(3);

    /**
     * Double pixel gray low underline.
     * <p>
     *  双像素灰色低下划线
     * 
     * 
     * @see #INPUT_METHOD_UNDERLINE
     * @since 1.3
     */
    public static final Integer UNDERLINE_LOW_GRAY =
        Integer.valueOf(4);

    /**
     * Single pixel dashed low underline.
     * <p>
     *  单像素虚线低下划线
     * 
     * 
     * @see #INPUT_METHOD_UNDERLINE
     * @since 1.3
     */
    public static final Integer UNDERLINE_LOW_DASHED =
        Integer.valueOf(5);

    /**
     * Attribute key for swapping foreground and background
     * <code>Paints</code>.  Values are instances of
     * <b><code>Boolean</code></b>.  The default value is
     * <code>false</code>, which means do not swap colors.
     *
     * <p>The constant value {@link #SWAP_COLORS_ON} is defined.
     *
     * <p>If the {@link #FOREGROUND} attribute is set, its
     * <code>Paint</code> will be used as the background, otherwise
     * the <code>Paint</code> currently on the <code>Graphics</code>
     * will be used.  If the {@link #BACKGROUND} attribute is set, its
     * <code>Paint</code> will be used as the foreground, otherwise
     * the system will find a contrasting color to the
     * (resolved) background so that the text will be visible.
     *
     * <p>
     *  用于交换前景和背景的属性键<code> Paints </code>值是<b> <code> Boolean </code> </b>的实例默认值为<code> false </code>不交换颜色。
     * 
     *  <p>定义了常数值{@link #SWAP_COLORS_ON}
     * 
     * <p>如果设置了{@link #FOREGROUND}属性,则其<code> Paint </code>将用作背景,否则<code> Paint </code>代码>将被使用如果设置了{@link #BACKGROUND}
     * 属性,它的<code> Paint </code>将被用作前景,否则系统会找到一个对比的颜色到文本将可见。
     * 
     * 
     * @see #FOREGROUND
     * @see #BACKGROUND
     */
    public static final TextAttribute SWAP_COLORS =
        new TextAttribute("swap_colors");

    /**
     * Swap foreground and background.
     * <p>
     *  交换前景和背景
     * 
     * 
     * @see #SWAP_COLORS
     * @since 1.3
     */
    public static final Boolean SWAP_COLORS_ON =
        Boolean.TRUE;

    /**
     * Attribute key for converting ASCII decimal digits to other
     * decimal ranges.  Values are instances of {@link NumericShaper}.
     * The default is <code>null</code>, which means do not perform
     * numeric shaping.
     *
     * <p>When a numeric shaper is defined, the text is first
     * processed by the shaper before any other analysis of the text
     * is performed.
     *
     * <p><em>Note:</em> This should have the same value for all the
     * text in the paragraph, otherwise the behavior is undetermined.
     *
     * <p>
     *  用于将ASCII十进制数字转换为其他十进制范围的属性键值是{@link NumericShaper}的实例默认值为<code> null </code>,表示不执行数字整形
     * 
     *  <p>当定义数字整形器时,文本在执行文本的任何其他分析之前首先由整形器处理
     * 
     * <p> <em>注意：</em>这应该对段落中的所有文本具有相同的值,否则行为是不确定的
     * 
     * 
     * @see NumericShaper
     * @since 1.4
     */
    public static final TextAttribute NUMERIC_SHAPING =
        new TextAttribute("numeric_shaping");

    /**
     * Attribute key to request kerning. Values are instances of
     * <b><code>Integer</code></b>.  The default value is
     * <code>0</code>, which does not request kerning.
     *
     * <p>The constant value {@link #KERNING_ON} is provided.
     *
     * <p>The default advances of single characters are not
     * appropriate for some character sequences, for example "To" or
     * "AWAY".  Without kerning the adjacent characters appear to be
     * separated by too much space.  Kerning causes selected sequences
     * of characters to be spaced differently for a more pleasing
     * visual appearance.
     *
     * <p>
     *  请求kerning的属性键值是<b> <code> Integer </code> </b>的实例。默认值为<code> 0 </code>
     * 
     *  <p>提供常量值{@link #KERNING_ON}
     * 
     *  <p>单个字符的默认前进不适用于某些字符序列,例如"To"或"AWAY"。不加边距,相邻的字符显示为由太多的空格分隔。字符间距导致所选的字符序列以不同的方式分隔更令人愉快的视觉外观
     * 
     * 
     * @since 1.6
     */
    public static final TextAttribute KERNING =
        new TextAttribute("kerning");

    /**
     * Request standard kerning.
     * <p>
     *  请求标准字距
     * 
     * 
     * @see #KERNING
     * @since 1.6
     */
    public static final Integer KERNING_ON =
        Integer.valueOf(1);


    /**
     * Attribute key for enabling optional ligatures. Values are
     * instances of <b><code>Integer</code></b>.  The default value is
     * <code>0</code>, which means do not use optional ligatures.
     *
     * <p>The constant value {@link #LIGATURES_ON} is defined.
     *
     * <p>Ligatures required by the writing system are always enabled.
     *
     * <p>
     * 启用可选连字的属性键值是<b> <code> Integer </code> </b>的实例默认值为<code> 0 </code>,表示不使用可选连字
     * 
     *  <p>定义了常数值{@link #LIGATURES_ON}
     * 
     *  <p>写入系统所需的连接始终处于启用状态
     * 
     * 
     * @since 1.6
     */
    public static final TextAttribute LIGATURES =
        new TextAttribute("ligatures");

    /**
     * Request standard optional ligatures.
     * <p>
     *  请求标准可选连字
     * 
     * 
     * @see #LIGATURES
     * @since 1.6
     */
    public static final Integer LIGATURES_ON =
        Integer.valueOf(1);

    /**
     * Attribute key to control tracking.  Values are instances of
     * <b><code>Number</code></b>.  The default value is
     * <code>0</code>, which means no additional tracking.
     *
     * <p>The constant values {@link #TRACKING_TIGHT} and {@link
     * #TRACKING_LOOSE} are provided.
     *
     * <p>The tracking value is multiplied by the font point size and
     * passed through the font transform to determine an additional
     * amount to add to the advance of each glyph cluster.  Positive
     * tracking values will inhibit formation of optional ligatures.
     * Tracking values are typically between <code>-0.1</code> and
     * <code>0.3</code>; values outside this range are generally not
     * desireable.
     *
     * <p>
     *  属性键控制跟踪值是<b> <code> Number </code> </b>的实例默认值为<code> 0 </code>,表示没有额外的跟踪
     * 
     *  <p>提供常数值{@link #TRACKING_TIGHT}和{@link #TRACKING_LOOSE}
     * 
     * <p>跟踪值乘以字体点大小并通过字体变换,以确定要添加到每个字形集的提前的附加量。
     * 正跟踪值将抑制可选连字的形成跟踪值通常在<code> -01 </code>和<code> 03 </code>;超出该范围的值通常是不希望的。
     * 
     * 
     * @since 1.6
     */
    public static final TextAttribute TRACKING =
        new TextAttribute("tracking");

    /**
     * Perform tight tracking.
     * <p>
     * 
     * @see #TRACKING
     * @since 1.6
     */
    public static final Float TRACKING_TIGHT =
        Float.valueOf(-.04f);

    /**
     * Perform loose tracking.
     * <p>
     *  执行严格跟踪
     * 
     * 
     * @see #TRACKING
     * @since 1.6
     */
    public static final Float TRACKING_LOOSE =
        Float.valueOf(.04f);
}
