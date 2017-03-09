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

import java.awt.Font;
import java.awt.Color;

/**
 * Interface for a generic styled document.
 *
 * <p>
 *  通用样式文档的界面。
 * 
 * 
 * @author  Timothy Prinzing
 */
public interface StyledDocument extends Document {

    /**
     * Adds a new style into the logical style hierarchy.  Style attributes
     * resolve from bottom up so an attribute specified in a child
     * will override an attribute specified in the parent.
     *
     * <p>
     *  在逻辑样式层次结构中添加新样式。样式属性从下到上解析,因此子级中指定的属性将覆盖父级中指定的属性。
     * 
     * 
     * @param nm   the name of the style (must be unique within the
     *   collection of named styles).  The name may be null if the style
     *   is unnamed, but the caller is responsible
     *   for managing the reference returned as an unnamed style can't
     *   be fetched by name.  An unnamed style may be useful for things
     *   like character attribute overrides such as found in a style
     *   run.
     * @param parent the parent style.  This may be null if unspecified
     *   attributes need not be resolved in some other style.
     * @return the style
     */
    public Style addStyle(String nm, Style parent);

    /**
     * Removes a named style previously added to the document.
     *
     * <p>
     *  删除先前添加到文档中的命名样式。
     * 
     * 
     * @param nm  the name of the style to remove
     */
    public void removeStyle(String nm);

    /**
     * Fetches a named style previously added.
     *
     * <p>
     *  获取先前添加的命名样式。
     * 
     * 
     * @param nm  the name of the style
     * @return the style
     */
    public Style getStyle(String nm);

    /**
     * Changes the content element attributes used for the given range of
     * existing content in the document.  All of the attributes
     * defined in the given Attributes argument are applied to the
     * given range.  This method can be used to completely remove
     * all content level attributes for the given range by
     * giving an Attributes argument that has no attributes defined
     * and setting replace to true.
     *
     * <p>
     *  更改用于文档中现有内容的给定范围的内容元素属性。在给定的Attributes参数中定义的所有属性都应用于给定的范围。
     * 此方法可用于通过提供未定义属性的Attributes参数并将replace设置为true来完全删除给定范围的所有内容级别属性。
     * 
     * 
     * @param offset the start of the change &gt;= 0
     * @param length the length of the change &gt;= 0
     * @param s    the non-null attributes to change to.  Any attributes
     *  defined will be applied to the text for the given range.
     * @param replace indicates whether or not the previous
     *  attributes should be cleared before the new attributes
     *  as set.  If true, the operation will replace the
     *  previous attributes entirely.  If false, the new
     *  attributes will be merged with the previous attributes.
     */
    public void setCharacterAttributes(int offset, int length, AttributeSet s, boolean replace);

    /**
     * Sets paragraph attributes.
     *
     * <p>
     *  设置段落属性。
     * 
     * 
     * @param offset the start of the change &gt;= 0
     * @param length the length of the change &gt;= 0
     * @param s    the non-null attributes to change to.  Any attributes
     *  defined will be applied to the text for the given range.
     * @param replace indicates whether or not the previous
     *  attributes should be cleared before the new attributes
     *  are set.  If true, the operation will replace the
     *  previous attributes entirely.  If false, the new
     *  attributes will be merged with the previous attributes.
     */
    public void setParagraphAttributes(int offset, int length, AttributeSet s, boolean replace);

    /**
     * Sets the logical style to use for the paragraph at the
     * given position.  If attributes aren't explicitly set
     * for character and paragraph attributes they will resolve
     * through the logical style assigned to the paragraph, which
     * in turn may resolve through some hierarchy completely
     * independent of the element hierarchy in the document.
     *
     * <p>
     *  设置用于给定位置的段落的逻辑样式。如果没有为字符和段落属性显式设置属性,则它们将通过分配给段落的逻辑样式解析,这又可以通过一些层次结构完全独立于文档中的元素层次结构来解析。
     * 
     * 
     * @param pos the starting position &gt;= 0
     * @param s the style to set
     */
    public void setLogicalStyle(int pos, Style s);

    /**
     * Gets a logical style for a given position in a paragraph.
     *
     * <p>
     *  获取段落中给定位置的逻辑样式。
     * 
     * 
     * @param p the position &gt;= 0
     * @return the style
     */
    public Style getLogicalStyle(int p);

    /**
     * Gets the element that represents the paragraph that
     * encloses the given offset within the document.
     *
     * <p>
     *  获取表示在文档中包含给定偏移量的段落的元素。
     * 
     * 
     * @param pos the offset &gt;= 0
     * @return the element
     */
    public Element getParagraphElement(int pos);

    /**
     * Gets the element that represents the character that
     * is at the given offset within the document.
     *
     * <p>
     *  获取表示文档中给定偏移处的字符的元素。
     * 
     * 
     * @param pos the offset &gt;= 0
     * @return the element
     */
    public Element getCharacterElement(int pos);


    /**
     * Takes a set of attributes and turn it into a foreground color
     * specification.  This might be used to specify things
     * like brighter, more hue, etc.
     *
     * <p>
     * 获取一组属性并将其转换为前景颜色规范。这可能用于指定更亮,更多的色调等。
     * 
     * 
     * @param attr the set of attributes
     * @return the color
     */
    public Color getForeground(AttributeSet attr);

    /**
     * Takes a set of attributes and turn it into a background color
     * specification.  This might be used to specify things
     * like brighter, more hue, etc.
     *
     * <p>
     *  获取一组属性并将其转换为背景颜色规范。这可能用于指定更亮,更多的色调等。
     * 
     * 
     * @param attr the set of attributes
     * @return the color
     */
    public Color getBackground(AttributeSet attr);

    /**
     * Takes a set of attributes and turn it into a font
     * specification.  This can be used to turn things like
     * family, style, size, etc into a font that is available
     * on the system the document is currently being used on.
     *
     * <p>
     *  获取一组属性并将其转换为字体规范。这可以用来将诸如家庭,样式,大小等的东西转换成在文档当前正被使用的系统上可用的字体。
     * 
     * @param attr the set of attributes
     * @return the font
     */
    public Font getFont(AttributeSet attr);

}
