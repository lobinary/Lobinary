/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2006, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.*;
import javax.swing.event.*;

/**
 * A <code>LabelView</code> is a styled chunk of text
 * that represents a view mapped over an element in the
 * text model.  It caches the character level attributes
 * used for rendering.
 *
 * <p>
 *  <code> LabelView </code>是一个样式化的文本块,代表在文本模型中的元素上映射的视图。它缓存用于呈现的字符级属性。
 * 
 * 
 * @author Timothy Prinzing
 */
public class LabelView extends GlyphView implements TabableView {

    /**
     * Constructs a new view wrapped on an element.
     *
     * <p>
     *  构造包裹在元素上的新视图。
     * 
     * 
     * @param elem the element
     */
    public LabelView(Element elem) {
        super(elem);
    }

    /**
     * Synchronize the view's cached values with the model.
     * This causes the font, metrics, color, etc to be
     * re-cached if the cache has been invalidated.
     * <p>
     *  将视图的缓存值与模型同步。这导致如果缓存已失效,则重新缓存字体,指标,颜色等。
     * 
     */
    final void sync() {
        if (font == null) {
            setPropertiesFromAttributes();
        }
    }

    /**
     * Sets whether or not the view is underlined.
     * Note that this setter is protected and is really
     * only meant if you need to update some additional
     * state when set.
     *
     * <p>
     *  设置视图是否带下划线。注意,这个setter是受保护的,只是当你需要更新一些额外的状态时设置。
     * 
     * 
     * @param u true if the view is underlined, otherwise
     *          false
     * @see #isUnderline
     */
    protected void setUnderline(boolean u) {
        underline = u;
    }

    /**
     * Sets whether or not the view has a strike/line
     * through it.
     * Note that this setter is protected and is really
     * only meant if you need to update some additional
     * state when set.
     *
     * <p>
     *  设置视图是否有行程/行。注意,这个setter是受保护的,只是当你需要更新一些额外的状态时设置。
     * 
     * 
     * @param s true if the view has a strike/line
     *          through it, otherwise false
     * @see #isStrikeThrough
     */
    protected void setStrikeThrough(boolean s) {
        strike = s;
    }


    /**
     * Sets whether or not the view represents a
     * superscript.
     * Note that this setter is protected and is really
     * only meant if you need to update some additional
     * state when set.
     *
     * <p>
     *  设置视图是否表示上标。注意,这个setter是受保护的,只是当你需要更新一些额外的状态时设置。
     * 
     * 
     * @param s true if the view represents a
     *          superscript, otherwise false
     * @see #isSuperscript
     */
    protected void setSuperscript(boolean s) {
        superscript = s;
    }

    /**
     * Sets whether or not the view represents a
     * subscript.
     * Note that this setter is protected and is really
     * only meant if you need to update some additional
     * state when set.
     *
     * <p>
     *  设置视图是否表示下标。注意,这个setter是受保护的,只是当你需要更新一些额外的状态时设置。
     * 
     * 
     * @param s true if the view represents a
     *          subscript, otherwise false
     * @see #isSubscript
     */
    protected void setSubscript(boolean s) {
        subscript = s;
    }

    /**
     * Sets the background color for the view. This method is typically
     * invoked as part of configuring this <code>View</code>. If you need
     * to customize the background color you should override
     * <code>setPropertiesFromAttributes</code> and invoke this method. A
     * value of null indicates no background should be rendered, so that the
     * background of the parent <code>View</code> will show through.
     *
     * <p>
     * 设置视图的背景颜色。此方法通常作为配置此<code> View </code>的一部分来调用。
     * 如果您需要自定义背景颜色,您应该覆盖<code> setPropertiesFromAttributes </code>并调用此方法。
     * 值为null表示不应渲染任何背景,以便父</b> View </code>的背景显示。
     * 
     * 
     * @param bg background color, or null
     * @see #setPropertiesFromAttributes
     * @since 1.5
     */
    protected void setBackground(Color bg) {
        this.bg = bg;
    }

    /**
     * Sets the cached properties from the attributes.
     * <p>
     *  从属性设置缓存的属性。
     * 
     */
    protected void setPropertiesFromAttributes() {
        AttributeSet attr = getAttributes();
        if (attr != null) {
            Document d = getDocument();
            if (d instanceof StyledDocument) {
                StyledDocument doc = (StyledDocument) d;
                font = doc.getFont(attr);
                fg = doc.getForeground(attr);
                if (attr.isDefined(StyleConstants.Background)) {
                    bg = doc.getBackground(attr);
                } else {
                    bg = null;
                }
                setUnderline(StyleConstants.isUnderline(attr));
                setStrikeThrough(StyleConstants.isStrikeThrough(attr));
                setSuperscript(StyleConstants.isSuperscript(attr));
                setSubscript(StyleConstants.isSubscript(attr));
            } else {
                throw new StateInvariantError("LabelView needs StyledDocument");
            }
        }
     }

    /**
     * Fetches the <code>FontMetrics</code> used for this view.
     * <p>
     *  获取用于此视图的<code> FontMetrics </code>。
     * 
     * 
     * @deprecated FontMetrics are not used for glyph rendering
     *  when running in the JDK.
     */
    @Deprecated
    protected FontMetrics getFontMetrics() {
        sync();
        Container c = getContainer();
        return (c != null) ? c.getFontMetrics(font) :
            Toolkit.getDefaultToolkit().getFontMetrics(font);
    }

    /**
     * Fetches the background color to use to render the glyphs.
     * This is implemented to return a cached background color,
     * which defaults to <code>null</code>.
     *
     * <p>
     *  获取用于渲染字形的背景颜色。这是实现返回缓存的背景颜色,默认为<code> null </code>。
     * 
     * 
     * @return the cached background color
     * @since 1.3
     */
    public Color getBackground() {
        sync();
        return bg;
    }

    /**
     * Fetches the foreground color to use to render the glyphs.
     * This is implemented to return a cached foreground color,
     * which defaults to <code>null</code>.
     *
     * <p>
     *  获取用于渲染字形的前景颜色。这被实现为返回一个缓存的前景色,默认为<code> null </code>。
     * 
     * 
     * @return the cached foreground color
     * @since 1.3
     */
    public Color getForeground() {
        sync();
        return fg;
    }

    /**
     * Fetches the font that the glyphs should be based upon.
     * This is implemented to return a cached font.
     *
     * <p>
     *  获取字形应基于的字体。这是实现返回一个缓存字体。
     * 
     * 
     * @return the cached font
     */
     public Font getFont() {
        sync();
        return font;
    }

    /**
     * Determines if the glyphs should be underlined.  If true,
     * an underline should be drawn through the baseline.  This
     * is implemented to return the cached underline property.
     *
     * <p>When you request this property, <code>LabelView</code>
     * re-syncs its state with the properties of the
     * <code>Element</code>'s <code>AttributeSet</code>.
     * If <code>Element</code>'s <code>AttributeSet</code>
     * does not have this property set, it will revert to false.
     *
     * <p>
     *  确定字形是否应加下划线。如果为true,则应在基线中绘制下划线。这被实现为返回缓存的下划线属性。
     * 
     *  <p>当您请求此属性时,<code> LabelView </code>会将其状态与<code> Element </code>的<code> AttributeSet </code>的属性重新同步。
     * 如果<code> Element </code>的<code> AttributeSet </code>没有设置此属性,它将恢复为false。
     * 
     * 
     * @return the value of the cached
     *     <code>underline</code> property
     * @since 1.3
     */
    public boolean isUnderline() {
        sync();
        return underline;
    }

    /**
     * Determines if the glyphs should have a strikethrough
     * line.  If true, a line should be drawn through the center
     * of the glyphs.  This is implemented to return the
     * cached <code>strikeThrough</code> property.
     *
     * <p>When you request this property, <code>LabelView</code>
     * re-syncs its state with the properties of the
     * <code>Element</code>'s <code>AttributeSet</code>.
     * If <code>Element</code>'s <code>AttributeSet</code>
     * does not have this property set, it will revert to false.
     *
     * <p>
     * 确定字形是否应该有删除线。如果为true,则应通过字形的中心绘制一条线。这是实现返回缓存的<code> strikeThrough </code>属性。
     * 
     *  <p>当您请求此属性时,<code> LabelView </code>会将其状态与<code> Element </code>的<code> AttributeSet </code>的属性重新同步。
     * 如果<code> Element </code>的<code> AttributeSet </code>没有设置此属性,它将恢复为false。
     * 
     * 
     * @return the value of the cached
     *     <code>strikeThrough</code> property
     * @since 1.3
     */
    public boolean isStrikeThrough() {
        sync();
        return strike;
    }

    /**
     * Determines if the glyphs should be rendered as superscript.
     * <p>
     *  确定字形是否应该渲染为上标。
     * 
     * 
     * @return the value of the cached subscript property
     *
     * <p>When you request this property, <code>LabelView</code>
     * re-syncs its state with the properties of the
     * <code>Element</code>'s <code>AttributeSet</code>.
     * If <code>Element</code>'s <code>AttributeSet</code>
     * does not have this property set, it will revert to false.
     *
     * @return the value of the cached
     *     <code>subscript</code> property
     * @since 1.3
     */
    public boolean isSubscript() {
        sync();
        return subscript;
    }

    /**
     * Determines if the glyphs should be rendered as subscript.
     *
     * <p>When you request this property, <code>LabelView</code>
     * re-syncs its state with the properties of the
     * <code>Element</code>'s <code>AttributeSet</code>.
     * If <code>Element</code>'s <code>AttributeSet</code>
     * does not have this property set, it will revert to false.
     *
     * <p>
     *  确定是否应将字形呈现为下标。
     * 
     *  <p>当您请求此属性时,<code> LabelView </code>会将其状态与<code> Element </code>的<code> AttributeSet </code>的属性重新同步。
     * 如果<code> Element </code>的<code> AttributeSet </code>没有设置此属性,它将恢复为false。
     * 
     * @return the value of the cached
     *     <code>superscript</code> property
     * @since 1.3
     */
    public boolean isSuperscript() {
        sync();
        return superscript;
    }

    // --- View methods ---------------------------------------------

    /**
     * Gives notification from the document that attributes were changed
     * in a location that this view is responsible for.
     *
     * <p>
     * 
     * 
     * @param e the change information from the associated document
     * @param a the current allocation of the view
     * @param f the factory to use to rebuild if the view has children
     * @see View#changedUpdate
     */
    public void changedUpdate(DocumentEvent e, Shape a, ViewFactory f) {
        font = null;
        super.changedUpdate(e, a, f);
    }

    // --- variables ------------------------------------------------

    private Font font;
    private Color fg;
    private Color bg;
    private boolean underline;
    private boolean strike;
    private boolean superscript;
    private boolean subscript;

}
