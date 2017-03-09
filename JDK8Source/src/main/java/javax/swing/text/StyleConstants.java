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
package javax.swing.text;

import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import javax.swing.Icon;

/**
 * <p>
 * A collection of <em>well known</em> or common attribute keys
 * and methods to apply to an AttributeSet or MutableAttributeSet
 * to get/set the properties in a typesafe manner.
 * <p>
 * The paragraph attributes form the definition of a paragraph to be rendered.
 * All sizes are specified in points (such as found in postscript), a
 * device independent measure.
 * </p>
 * <p style="text-align:center"><img src="doc-files/paragraph.gif"
 * alt="Diagram shows SpaceAbove, FirstLineIndent, LeftIndent, RightIndent,
 *      and SpaceBelow a paragraph."></p>
 * <p>
 *
 * <p>
 * <p>
 *  应用于AttributeSet或MutableAttributeSet的<em>众所周知的</em>或公共属性键和方法的集合,以类型安全的方式获取/设置属性。
 * <p>
 *  段属性形成要渲染的段的定义。所有大小都以点(例如在后记中找到)指定,这是一种独立于设备的度量。
 * </p>
 *  <> </p> <p style ="text-align：center"> <img src ="doc-files / paragraph.gif"alt ="图表显示了SpaceAbove,FirstLineIndent,LeftIndent,RightIndent和SpaceBelow。
 * <p>
 * 
 * 
 * @author  Timothy Prinzing
 */
public class StyleConstants {

    /**
     * Name of elements used to represent components.
     * <p>
     *  用于表示组件的元素的名称。
     * 
     */
    public static final String ComponentElementName = "component";

    /**
     * Name of elements used to represent icons.
     * <p>
     *  用于表示图标的元素的名称。
     * 
     */
    public static final String IconElementName = "icon";

    /**
     * Attribute name used to name the collection of
     * attributes.
     * <p>
     *  用于命名属性集合的属性名称。
     * 
     */
    public static final Object NameAttribute = new StyleConstants("name");

    /**
     * Attribute name used to identify the resolving parent
     * set of attributes, if one is defined.
     * <p>
     *  用于标识解析父属性集的属性名(如果定义了属性集)。
     * 
     */
    public static final Object ResolveAttribute = new StyleConstants("resolver");

    /**
     * Attribute used to identify the model for embedded
     * objects that have a model view separation.
     * <p>
     *  用于标识具有模型视图分隔的嵌入对象的模型的属性。
     * 
     */
    public static final Object ModelAttribute = new StyleConstants("model");

    /**
     * Returns the string representation.
     *
     * <p>
     *  返回字符串表示形式。
     * 
     * 
     * @return the string
     */
    public String toString() {
        return representation;
    }

    // ---- character constants -----------------------------------

    /**
     * Bidirectional level of a character as assigned by the Unicode bidi
     * algorithm.
     * <p>
     *  由Unicode双向算法分配的字符的双向级别。
     * 
     */
    public static final Object BidiLevel = new CharacterConstants("bidiLevel");

    /**
     * Name of the font family.
     * <p>
     *  字体系列的名称。
     * 
     */
    public static final Object FontFamily = new FontConstants("family");

    /**
     * Name of the font family.
     *
     * <p>
     *  字体系列的名称。
     * 
     * 
     * @since 1.5
     */
    public static final Object Family = FontFamily;

    /**
     * Name of the font size.
     * <p>
     *  字体大小的名称。
     * 
     */
    public static final Object FontSize = new FontConstants("size");

    /**
     * Name of the font size.
     *
     * <p>
     *  字体大小的名称。
     * 
     * 
     * @since 1.5
     */
    public static final Object Size = FontSize;

    /**
     * Name of the bold attribute.
     * <p>
     *  粗体属性的名称。
     * 
     */
    public static final Object Bold = new FontConstants("bold");

    /**
     * Name of the italic attribute.
     * <p>
     *  斜体属性的名称。
     * 
     */
    public static final Object Italic = new FontConstants("italic");

    /**
     * Name of the underline attribute.
     * <p>
     *  下划线属性的名称。
     * 
     */
    public static final Object Underline = new CharacterConstants("underline");

    /**
     * Name of the Strikethrough attribute.
     * <p>
     *  "删除"属性的名称。
     * 
     */
    public static final Object StrikeThrough = new CharacterConstants("strikethrough");

    /**
     * Name of the Superscript attribute.
     * <p>
     *  Superscript属性的名称。
     * 
     */
    public static final Object Superscript = new CharacterConstants("superscript");

    /**
     * Name of the Subscript attribute.
     * <p>
     *  下标属性的名称。
     * 
     */
    public static final Object Subscript = new CharacterConstants("subscript");

    /**
     * Name of the foreground color attribute.
     * <p>
     *  前景色属性的名称。
     * 
     */
    public static final Object Foreground = new ColorConstants("foreground");

    /**
     * Name of the background color attribute.
     * <p>
     *  背景颜色属性的名称。
     * 
     */
    public static final Object Background = new ColorConstants("background");

    /**
     * Name of the component attribute.
     * <p>
     * 组件属性的名称。
     * 
     */
    public static final Object ComponentAttribute = new CharacterConstants("component");

    /**
     * Name of the icon attribute.
     * <p>
     *  图标属性的名称。
     * 
     */
    public static final Object IconAttribute = new CharacterConstants("icon");

    /**
     * Name of the input method composed text attribute. The value of
     * this attribute is an instance of AttributedString which represents
     * the composed text.
     * <p>
     *  组成文本属性的输入法的名称。此属性的值是表示组合文本的AttributedString的实例。
     * 
     */
    public static final Object ComposedTextAttribute = new StyleConstants("composed text");

    /**
     * The amount of space to indent the first
     * line of the paragraph.  This value may be negative
     * to offset in the reverse direction.  The type
     * is Float and specifies the size of the space
     * in points.
     * <p>
     *  缩进段落第一行的空格量。该值可以为负,以在相反方向上偏移。类型为Float,以点为单位指定空间大小。
     * 
     */
    public static final Object FirstLineIndent = new ParagraphConstants("FirstLineIndent");

    /**
     * The amount to indent the left side
     * of the paragraph.
     * Type is float and specifies the size in points.
     * <p>
     *  缩进段落左侧的量。类型为float,以磅为单位指定大小。
     * 
     */
    public static final Object LeftIndent = new ParagraphConstants("LeftIndent");

    /**
     * The amount to indent the right side
     * of the paragraph.
     * Type is float and specifies the size in points.
     * <p>
     *  缩进段落右侧的数量。类型为float,以磅为单位指定大小。
     * 
     */
    public static final Object RightIndent = new ParagraphConstants("RightIndent");

    /**
     * The amount of space between lines
     * of the paragraph.
     * Type is float and specifies the size as a factor of the line height
     * <p>
     *  段落行之间的空格大小。类型为float,并将大小指定为线高的一个因子
     * 
     */
    public static final Object LineSpacing = new ParagraphConstants("LineSpacing");

    /**
     * The amount of space above the paragraph.
     * Type is float and specifies the size in points.
     * <p>
     *  段以上的空间量。类型为float,以磅为单位指定大小。
     * 
     */
    public static final Object SpaceAbove = new ParagraphConstants("SpaceAbove");

    /**
     * The amount of space below the paragraph.
     * Type is float and specifies the size in points.
     * <p>
     *  段落下面的空间量。类型为float,以磅为单位指定大小。
     * 
     */
    public static final Object SpaceBelow = new ParagraphConstants("SpaceBelow");

    /**
     * Alignment for the paragraph.  The type is
     * Integer.  Valid values are:
     * <ul>
     * <li>ALIGN_LEFT
     * <li>ALIGN_RIGHT
     * <li>ALIGN_CENTER
     * <li>ALIGN_JUSTIFED
     * </ul>
     *
     * <p>
     *  段落的对齐。类型为整数。有效值为：
     * <ul>
     *  <li> ALIGN_LEFT <li> ALIGN_RIGHT <li> ALIGN_CENTER <li> ALIGN_JUSTIFED
     * </ul>
     * 
     */
    public static final Object Alignment = new ParagraphConstants("Alignment");

    /**
     * TabSet for the paragraph, type is a TabSet containing
     * TabStops.
     * <p>
     *  TabSet为段落,type是一个包含TabStops的TabSet。
     * 
     */
    public static final Object TabSet = new ParagraphConstants("TabSet");

    /**
     * Orientation for a paragraph.
     * <p>
     *  段落的方向。
     * 
     */
    public static final Object Orientation = new ParagraphConstants("Orientation");
    /**
     * A possible value for paragraph alignment.  This
     * specifies that the text is aligned to the left
     * indent and extra whitespace should be placed on
     * the right.
     * <p>
     *  段落对齐的可能值。这指定文本对齐到左缩进和额外的空格应该放在右边。
     * 
     */
    public static final int ALIGN_LEFT = 0;

    /**
     * A possible value for paragraph alignment.  This
     * specifies that the text is aligned to the center
     * and extra whitespace should be placed equally on
     * the left and right.
     * <p>
     *  段落对齐的可能值。这指定文本与中心对齐,并且多余的空白应同样放在左侧和右侧。
     * 
     */
    public static final int ALIGN_CENTER = 1;

    /**
     * A possible value for paragraph alignment.  This
     * specifies that the text is aligned to the right
     * indent and extra whitespace should be placed on
     * the left.
     * <p>
     * 段落对齐的可能值。这指定文本对齐到右缩进和额外的空格应该放在左边。
     * 
     */
    public static final int ALIGN_RIGHT = 2;

    /**
     * A possible value for paragraph alignment.  This
     * specifies that extra whitespace should be spread
     * out through the rows of the paragraph with the
     * text lined up with the left and right indent
     * except on the last line which should be aligned
     * to the left.
     * <p>
     *  段落对齐的可能值。这指定了额外的空白应该通过段落的行来扩展,文本与左和右缩进排列,除了最后一行,应该对齐到左边。
     * 
     */
    public static final int ALIGN_JUSTIFIED = 3;

    // --- character attribute accessors ---------------------------

    /**
     * Gets the BidiLevel setting.
     *
     * <p>
     *  获取BidiLevel设置。
     * 
     * 
     * @param a the attribute set
     * @return the value
     */
    public static int getBidiLevel(AttributeSet a) {
        Integer o = (Integer) a.getAttribute(BidiLevel);
        if (o != null) {
            return o.intValue();
        }
        return 0;  // Level 0 is base level (non-embedded) left-to-right
    }

    /**
     * Sets the BidiLevel.
     *
     * <p>
     *  设置BidiLevel。
     * 
     * 
     * @param a the attribute set
     * @param o the bidi level value
     */
    public static void setBidiLevel(MutableAttributeSet a, int o) {
        a.addAttribute(BidiLevel, Integer.valueOf(o));
    }

    /**
     * Gets the component setting from the attribute list.
     *
     * <p>
     *  从属性列表中获取组件设置。
     * 
     * 
     * @param a the attribute set
     * @return the component, null if none
     */
    public static Component getComponent(AttributeSet a) {
        return (Component) a.getAttribute(ComponentAttribute);
    }

    /**
     * Sets the component attribute.
     *
     * <p>
     *  设置组件属性。
     * 
     * 
     * @param a the attribute set
     * @param c the component
     */
    public static void setComponent(MutableAttributeSet a, Component c) {
        a.addAttribute(AbstractDocument.ElementNameAttribute, ComponentElementName);
        a.addAttribute(ComponentAttribute, c);
    }

    /**
     * Gets the icon setting from the attribute list.
     *
     * <p>
     *  从属性列表中获取图标设置。
     * 
     * 
     * @param a the attribute set
     * @return the icon, null if none
     */
    public static Icon getIcon(AttributeSet a) {
        return (Icon) a.getAttribute(IconAttribute);
    }

    /**
     * Sets the icon attribute.
     *
     * <p>
     *  设置图标属性。
     * 
     * 
     * @param a the attribute set
     * @param c the icon
     */
    public static void setIcon(MutableAttributeSet a, Icon c) {
        a.addAttribute(AbstractDocument.ElementNameAttribute, IconElementName);
        a.addAttribute(IconAttribute, c);
    }

    /**
     * Gets the font family setting from the attribute list.
     *
     * <p>
     *  从属性列表中获取字体系列设置。
     * 
     * 
     * @param a the attribute set
     * @return the font family, "Monospaced" as the default
     */
    public static String getFontFamily(AttributeSet a) {
        String family = (String) a.getAttribute(FontFamily);
        if (family == null) {
            family = "Monospaced";
        }
        return family;
    }

    /**
     * Sets the font attribute.
     *
     * <p>
     *  设置字体属性。
     * 
     * 
     * @param a the attribute set
     * @param fam the font
     */
    public static void setFontFamily(MutableAttributeSet a, String fam) {
        a.addAttribute(FontFamily, fam);
    }

    /**
     * Gets the font size setting from the attribute list.
     *
     * <p>
     *  从属性列表获取字体大小设置。
     * 
     * 
     * @param a the attribute set
     * @return the font size, 12 as the default
     */
    public static int getFontSize(AttributeSet a) {
        Integer size = (Integer) a.getAttribute(FontSize);
        if (size != null) {
            return size.intValue();
        }
        return 12;
    }

    /**
     * Sets the font size attribute.
     *
     * <p>
     *  设置字体大小属性。
     * 
     * 
     * @param a the attribute set
     * @param s the font size
     */
    public static void setFontSize(MutableAttributeSet a, int s) {
        a.addAttribute(FontSize, Integer.valueOf(s));
    }

    /**
     * Checks whether the bold attribute is set.
     *
     * <p>
     *  检查是否设置了粗体属性。
     * 
     * 
     * @param a the attribute set
     * @return true if set else false
     */
    public static boolean isBold(AttributeSet a) {
        Boolean bold = (Boolean) a.getAttribute(Bold);
        if (bold != null) {
            return bold.booleanValue();
        }
        return false;
    }

    /**
     * Sets the bold attribute.
     *
     * <p>
     *  设置粗体属性。
     * 
     * 
     * @param a the attribute set
     * @param b specifies true/false for setting the attribute
     */
    public static void setBold(MutableAttributeSet a, boolean b) {
        a.addAttribute(Bold, Boolean.valueOf(b));
    }

    /**
     * Checks whether the italic attribute is set.
     *
     * <p>
     *  检查是否设置了斜体属性。
     * 
     * 
     * @param a the attribute set
     * @return true if set else false
     */
    public static boolean isItalic(AttributeSet a) {
        Boolean italic = (Boolean) a.getAttribute(Italic);
        if (italic != null) {
            return italic.booleanValue();
        }
        return false;
    }

    /**
     * Sets the italic attribute.
     *
     * <p>
     *  设置斜体属性。
     * 
     * 
     * @param a the attribute set
     * @param b specifies true/false for setting the attribute
     */
    public static void setItalic(MutableAttributeSet a, boolean b) {
        a.addAttribute(Italic, Boolean.valueOf(b));
    }

    /**
     * Checks whether the underline attribute is set.
     *
     * <p>
     *  检查是否设置了下划线属性。
     * 
     * 
     * @param a the attribute set
     * @return true if set else false
     */
    public static boolean isUnderline(AttributeSet a) {
        Boolean underline = (Boolean) a.getAttribute(Underline);
        if (underline != null) {
            return underline.booleanValue();
        }
        return false;
    }

    /**
     * Checks whether the strikethrough attribute is set.
     *
     * <p>
     *  检查是否设置了删除线属性。
     * 
     * 
     * @param a the attribute set
     * @return true if set else false
     */
    public static boolean isStrikeThrough(AttributeSet a) {
        Boolean strike = (Boolean) a.getAttribute(StrikeThrough);
        if (strike != null) {
            return strike.booleanValue();
        }
        return false;
    }


    /**
     * Checks whether the superscript attribute is set.
     *
     * <p>
     *  检查是否设置了superscript属性。
     * 
     * 
     * @param a the attribute set
     * @return true if set else false
     */
    public static boolean isSuperscript(AttributeSet a) {
        Boolean superscript = (Boolean) a.getAttribute(Superscript);
        if (superscript != null) {
            return superscript.booleanValue();
        }
        return false;
    }


    /**
     * Checks whether the subscript attribute is set.
     *
     * <p>
     *  检查是否设置了下标属性。
     * 
     * 
     * @param a the attribute set
     * @return true if set else false
     */
    public static boolean isSubscript(AttributeSet a) {
        Boolean subscript = (Boolean) a.getAttribute(Subscript);
        if (subscript != null) {
            return subscript.booleanValue();
        }
        return false;
    }


    /**
     * Sets the underline attribute.
     *
     * <p>
     *  设置下划线属性。
     * 
     * 
     * @param a the attribute set
     * @param b specifies true/false for setting the attribute
     */
    public static void setUnderline(MutableAttributeSet a, boolean b) {
        a.addAttribute(Underline, Boolean.valueOf(b));
    }

    /**
     * Sets the strikethrough attribute.
     *
     * <p>
     *  设置删除线属性。
     * 
     * 
     * @param a the attribute set
     * @param b specifies true/false for setting the attribute
     */
    public static void setStrikeThrough(MutableAttributeSet a, boolean b) {
        a.addAttribute(StrikeThrough, Boolean.valueOf(b));
    }

    /**
     * Sets the superscript attribute.
     *
     * <p>
     *  设置superscript属性。
     * 
     * 
     * @param a the attribute set
     * @param b specifies true/false for setting the attribute
     */
    public static void setSuperscript(MutableAttributeSet a, boolean b) {
        a.addAttribute(Superscript, Boolean.valueOf(b));
    }

    /**
     * Sets the subscript attribute.
     *
     * <p>
     *  设置下标属性。
     * 
     * 
     * @param a the attribute set
     * @param b specifies true/false for setting the attribute
     */
    public static void setSubscript(MutableAttributeSet a, boolean b) {
        a.addAttribute(Subscript, Boolean.valueOf(b));
    }


    /**
     * Gets the foreground color setting from the attribute list.
     *
     * <p>
     *  从属性列表获取前景颜色设置。
     * 
     * 
     * @param a the attribute set
     * @return the color, Color.black as the default
     */
    public static Color getForeground(AttributeSet a) {
        Color fg = (Color) a.getAttribute(Foreground);
        if (fg == null) {
            fg = Color.black;
        }
        return fg;
    }

    /**
     * Sets the foreground color.
     *
     * <p>
     *  设置前景颜色。
     * 
     * 
     * @param a the attribute set
     * @param fg the color
     */
    public static void setForeground(MutableAttributeSet a, Color fg) {
        a.addAttribute(Foreground, fg);
    }

    /**
     * Gets the background color setting from the attribute list.
     *
     * <p>
     *  从属性列表获取背景颜色设置。
     * 
     * 
     * @param a the attribute set
     * @return the color, Color.black as the default
     */
    public static Color getBackground(AttributeSet a) {
        Color fg = (Color) a.getAttribute(Background);
        if (fg == null) {
            fg = Color.black;
        }
        return fg;
    }

    /**
     * Sets the background color.
     *
     * <p>
     *  设置背景颜色。
     * 
     * 
     * @param a the attribute set
     * @param fg the color
     */
    public static void setBackground(MutableAttributeSet a, Color fg) {
        a.addAttribute(Background, fg);
    }


    // --- paragraph attribute accessors ----------------------------

    /**
     * Gets the first line indent setting.
     *
     * <p>
     *  获取第一行缩进设置。
     * 
     * 
     * @param a the attribute set
     * @return the value, 0 if not set
     */
    public static float getFirstLineIndent(AttributeSet a) {
        Float indent = (Float) a.getAttribute(FirstLineIndent);
        if (indent != null) {
            return indent.floatValue();
        }
        return 0;
    }

    /**
     * Sets the first line indent.
     *
     * <p>
     *  设置第一行缩进。
     * 
     * 
     * @param a the attribute set
     * @param i the value
     */
    public static void setFirstLineIndent(MutableAttributeSet a, float i) {
        a.addAttribute(FirstLineIndent, new Float(i));
    }

    /**
     * Gets the right indent setting.
     *
     * <p>
     * 获取右缩进设置。
     * 
     * 
     * @param a the attribute set
     * @return the value, 0 if not set
     */
    public static float getRightIndent(AttributeSet a) {
        Float indent = (Float) a.getAttribute(RightIndent);
        if (indent != null) {
            return indent.floatValue();
        }
        return 0;
    }

    /**
     * Sets right indent.
     *
     * <p>
     *  设置右缩进。
     * 
     * 
     * @param a the attribute set
     * @param i the value
     */
    public static void setRightIndent(MutableAttributeSet a, float i) {
        a.addAttribute(RightIndent, new Float(i));
    }

    /**
     * Gets the left indent setting.
     *
     * <p>
     *  获取左缩进设置。
     * 
     * 
     * @param a the attribute set
     * @return the value, 0 if not set
     */
    public static float getLeftIndent(AttributeSet a) {
        Float indent = (Float) a.getAttribute(LeftIndent);
        if (indent != null) {
            return indent.floatValue();
        }
        return 0;
    }

    /**
     * Sets left indent.
     *
     * <p>
     *  设置左缩进。
     * 
     * 
     * @param a the attribute set
     * @param i the value
     */
    public static void setLeftIndent(MutableAttributeSet a, float i) {
        a.addAttribute(LeftIndent, new Float(i));
    }

    /**
     * Gets the line spacing setting.
     *
     * <p>
     *  获取行间距设置。
     * 
     * 
     * @param a the attribute set
     * @return the value, 0 if not set
     */
    public static float getLineSpacing(AttributeSet a) {
        Float space = (Float) a.getAttribute(LineSpacing);
        if (space != null) {
            return space.floatValue();
        }
        return 0;
    }

    /**
     * Sets line spacing.
     *
     * <p>
     *  设置行间距。
     * 
     * 
     * @param a the attribute set
     * @param i the value
     */
    public static void setLineSpacing(MutableAttributeSet a, float i) {
        a.addAttribute(LineSpacing, new Float(i));
    }

    /**
     * Gets the space above setting.
     *
     * <p>
     *  获取上面的空格。
     * 
     * 
     * @param a the attribute set
     * @return the value, 0 if not set
     */
    public static float getSpaceAbove(AttributeSet a) {
        Float space = (Float) a.getAttribute(SpaceAbove);
        if (space != null) {
            return space.floatValue();
        }
        return 0;
    }

    /**
     * Sets space above.
     *
     * <p>
     *  设置上面的空格。
     * 
     * 
     * @param a the attribute set
     * @param i the value
     */
    public static void setSpaceAbove(MutableAttributeSet a, float i) {
        a.addAttribute(SpaceAbove, new Float(i));
    }

    /**
     * Gets the space below setting.
     *
     * <p>
     *  获取以下设置空间。
     * 
     * 
     * @param a the attribute set
     * @return the value, 0 if not set
     */
    public static float getSpaceBelow(AttributeSet a) {
        Float space = (Float) a.getAttribute(SpaceBelow);
        if (space != null) {
            return space.floatValue();
        }
        return 0;
    }

    /**
     * Sets space below.
     *
     * <p>
     *  设置下面的空格。
     * 
     * 
     * @param a the attribute set
     * @param i the value
     */
    public static void setSpaceBelow(MutableAttributeSet a, float i) {
        a.addAttribute(SpaceBelow, new Float(i));
    }

    /**
     * Gets the alignment setting.
     *
     * <p>
     *  获取对齐设置。
     * 
     * 
     * @param a the attribute set
     * @return the value <code>StyleConstants.ALIGN_LEFT</code> if not set
     */
    public static int getAlignment(AttributeSet a) {
        Integer align = (Integer) a.getAttribute(Alignment);
        if (align != null) {
            return align.intValue();
        }
        return ALIGN_LEFT;
    }

    /**
     * Sets alignment.
     *
     * <p>
     *  设置对齐。
     * 
     * 
     * @param a the attribute set
     * @param align the alignment value
     */
    public static void setAlignment(MutableAttributeSet a, int align) {
        a.addAttribute(Alignment, Integer.valueOf(align));
    }

    /**
     * Gets the TabSet.
     *
     * <p>
     *  获取TabSet。
     * 
     * 
     * @param a the attribute set
     * @return the <code>TabSet</code>
     */
    public static TabSet getTabSet(AttributeSet a) {
        TabSet tabs = (TabSet)a.getAttribute(TabSet);
        // PENDING: should this return a default?
        return tabs;
    }

    /**
     * Sets the TabSet.
     *
     * <p>
     *  设置TabSet。
     * 
     * 
     * @param a the attribute set.
     * @param tabs the TabSet
     */
    public static void setTabSet(MutableAttributeSet a, TabSet tabs) {
        a.addAttribute(TabSet, tabs);
    }

    // --- privates ---------------------------------------------

    static Object[] keys = {
        NameAttribute, ResolveAttribute, BidiLevel,
        FontFamily, FontSize, Bold, Italic, Underline,
        StrikeThrough, Superscript, Subscript, Foreground,
        Background, ComponentAttribute, IconAttribute,
        FirstLineIndent, LeftIndent, RightIndent, LineSpacing,
        SpaceAbove, SpaceBelow, Alignment, TabSet, Orientation,
        ModelAttribute, ComposedTextAttribute
    };

    StyleConstants(String representation) {
        this.representation = representation;
    }

    private String representation;

    /**
     * This is a typesafe enumeration of the <em>well-known</em>
     * attributes that contribute to a paragraph style.  These are
     * aliased by the outer class for general presentation.
     * <p>
     *  这是有助于段落样式的<em>知名</em>属性的类型安全枚举。这些都是外部类别的一般呈现。
     * 
     */
    public static class ParagraphConstants extends StyleConstants
        implements AttributeSet.ParagraphAttribute {

        private ParagraphConstants(String representation) {
            super(representation);
        }
    }

    /**
     * This is a typesafe enumeration of the <em>well-known</em>
     * attributes that contribute to a character style.  These are
     * aliased by the outer class for general presentation.
     * <p>
     *  这是有助于字符风格的<em>众所周知的属性的类型安全枚举。这些都是外部类别的一般呈现。
     * 
     */
    public static class CharacterConstants extends StyleConstants
        implements AttributeSet.CharacterAttribute {

        private CharacterConstants(String representation) {
            super(representation);
        }
    }

    /**
     * This is a typesafe enumeration of the <em>well-known</em>
     * attributes that contribute to a color.  These are aliased
     * by the outer class for general presentation.
     * <p>
     *  这是对颜色有贡献的<em>众所周知的</em>属性的类型安全枚举。这些都是外部类别的一般呈现。
     * 
     */
    public static class ColorConstants extends StyleConstants
        implements AttributeSet.ColorAttribute,  AttributeSet.CharacterAttribute {

        private ColorConstants(String representation) {
            super(representation);
        }
    }

    /**
     * This is a typesafe enumeration of the <em>well-known</em>
     * attributes that contribute to a font.  These are aliased
     * by the outer class for general presentation.
     * <p>
     *  这是对字体有贡献的<em>知名</em>属性的类型安全枚举。这些都是外部类别的一般呈现。
     */
    public static class FontConstants extends StyleConstants
        implements AttributeSet.FontAttribute, AttributeSet.CharacterAttribute {

        private FontConstants(String representation) {
            super(representation);
        }
    }


}
