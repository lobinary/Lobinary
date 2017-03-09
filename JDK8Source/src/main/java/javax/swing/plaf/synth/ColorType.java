/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2005, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.plaf.synth;

/**
 * A typesafe enumeration of colors that can be fetched from a style.
 * <p>
 * Each <code>SynthStyle</code> has a set of <code>ColorType</code>s that
 * are accessed by way of the
 * {@link SynthStyle#getColor(SynthContext, ColorType)} method.
 * <code>SynthStyle</code>'s <code>installDefaults</code> will install
 * the <code>FOREGROUND</code> color
 * as the foreground of
 * the Component, and the <code>BACKGROUND</code> color to the background of
 * the component (assuming that you have not explicitly specified a
 * foreground and background color). Some components
 * support more color based properties, for
 * example <code>JList</code> has the property
 * <code>selectionForeground</code> which will be mapped to
 * <code>FOREGROUND</code> with a component state of
 * <code>SynthConstants.SELECTED</code>.
 * <p>
 * The following example shows a custom <code>SynthStyle</code> that returns
 * a red Color for the <code>DISABLED</code> state, otherwise a black color.
 * <pre>
 * class MyStyle extends SynthStyle {
 *     private Color disabledColor = new ColorUIResource(Color.RED);
 *     private Color color = new ColorUIResource(Color.BLACK);
 *     protected Color getColorForState(SynthContext context, ColorType type){
 *         if (context.getComponentState() == SynthConstants.DISABLED) {
 *             return disabledColor;
 *         }
 *         return color;
 *     }
 * }
 * </pre>
 *
 * <p>
 *  可以从样式中提取的颜色的类型安全枚举。
 * <p>
 *  每个<code> SynthStyle </code>具有通过{@link SynthStyle#getColor(SynthContext,ColorType)}方法访问的一组<code> Colo
 * rType </code>。
 *  <code> SynthStyle </code>的<code> installDefaults </code>会将<code> FOREGROUND </code>颜色安装为组件的前景,并将<code>
 * 组件的背景(假设您没有明确指定前景和背景颜色)。
 * 一些组件支持更多基于颜色的属性,例如<code> JList </code>具有<code> selectionForeground </code>属性,它将映射到<code> FOREGROUND </code>
 *  SynthConstants.SELECTED </code>。
 * <p>
 *  以下示例显示了为<code> DISABLED </code>状态返回红色的自定义<code> SynthStyle </code>,否则为黑色。
 * <pre>
 *  class MyStyle extends SynthStyle {private Color disabledColor = new ColorUIResource(Color.RED); private color color = new ColorUIResource(Color.BLACK); protected Color getColorForState(SynthContext context,ColorType type){if(context.getComponentState()== SynthConstants.DISABLED){return disabledColor; }
 *  return color; }}。
 * </pre>
 * 
 * 
 * @since 1.5
 * @author Scott Violet
 */
public class ColorType {
    /**
     * ColorType for the foreground of a region.
     * <p>
     *  区域的前景的ColorType。
     * 
     */
    public static final ColorType FOREGROUND = new ColorType("Foreground");

    /**
     * ColorType for the background of a region.
     * <p>
     *  区域背景的ColorType。
     * 
     */
    public static final ColorType BACKGROUND = new ColorType("Background");

    /**
     * ColorType for the foreground of a region.
     * <p>
     * 区域的前景的ColorType。
     * 
     */
    public static final ColorType TEXT_FOREGROUND = new ColorType(
                                       "TextForeground");

    /**
     * ColorType for the background of a region.
     * <p>
     *  区域背景的ColorType。
     * 
     */
    public static final ColorType TEXT_BACKGROUND =new ColorType(
                                       "TextBackground");

    /**
     * ColorType for the focus.
     * <p>
     *  焦点的ColorType。
     * 
     */
    public static final ColorType FOCUS = new ColorType("Focus");

    /**
     * Maximum number of <code>ColorType</code>s.
     * <p>
     *  <code> ColorType </code>的最大数量。
     * 
     */
    public static final int MAX_COUNT;

    private static int nextID;

    private String description;
    private int index;

    static {
        MAX_COUNT = Math.max(FOREGROUND.getID(), Math.max(
                                 BACKGROUND.getID(), FOCUS.getID())) + 1;
    }

    /**
     * Creates a new ColorType with the specified description.
     *
     * <p>
     *  创建具有指定描述的新ColorType。
     * 
     * 
     * @param description String description of the ColorType.
     */
    protected ColorType(String description) {
        if (description == null) {
            throw new NullPointerException(
                          "ColorType must have a valid description");
        }
        this.description = description;
        synchronized(ColorType.class) {
            this.index = nextID++;
        }
    }

    /**
     * Returns a unique id, as an integer, for this ColorType.
     *
     * <p>
     *  返回此ColorType的唯一ID(作为整数)。
     * 
     * 
     * @return a unique id, as an integer, for this ColorType.
     */
    public final int getID() {
        return index;
    }

    /**
     * Returns the textual description of this <code>ColorType</code>.
     * This is the same value that the <code>ColorType</code> was created
     * with.
     *
     * <p>
     *  返回此<code> ColorType </code>的文本描述。这与使用<code> ColorType </code>创建的值相同。
     * 
     * @return the description of the string
     */
    public String toString() {
        return description;
    }
}
