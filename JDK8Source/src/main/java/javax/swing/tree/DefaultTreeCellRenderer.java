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

package javax.swing.tree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import sun.swing.DefaultLookup;

/**
 * Displays an entry in a tree.
 * <code>DefaultTreeCellRenderer</code> is not opaque and
 * unless you subclass paint you should not change this.
 * See <a
 href="https://docs.oracle.com/javase/tutorial/uiswing/components/tree.html">How to Use Trees</a>
 * in <em>The Java Tutorial</em>
 * for examples of customizing node display using this class.
 * <p>
 * The set of icons and colors used by {@code DefaultTreeCellRenderer}
 * can be configured using the various setter methods. The value for
 * each property is initialized from the defaults table. When the
 * look and feel changes ({@code updateUI} is invoked), any properties
 * that have a value of type {@code UIResource} are refreshed from the
 * defaults table. The following table lists the mapping between
 * {@code DefaultTreeCellRenderer} property and defaults table key:
 * <table border="1" cellpadding="1" cellspacing="0" summary="">
 *   <tr valign="top"  align="left">
 *     <th style="background-color:#CCCCFF" align="left">Property:
 *     <th style="background-color:#CCCCFF" align="left">Key:
 *   <tr><td>"leafIcon"<td>"Tree.leafIcon"
 *   <tr><td>"closedIcon"<td>"Tree.closedIcon"
 *   <tr><td>"openIcon"<td>"Tree.openIcon"
 *   <tr><td>"textSelectionColor"<td>"Tree.selectionForeground"
 *   <tr><td>"textNonSelectionColor"<td>"Tree.textForeground"
 *   <tr><td>"backgroundSelectionColor"<td>"Tree.selectionBackground"
 *   <tr><td>"backgroundNonSelectionColor"<td>"Tree.textBackground"
 *   <tr><td>"borderSelectionColor"<td>"Tree.selectionBorderColor"
 * </table>
 * <p>
 * <strong><a name="override">Implementation Note:</a></strong>
 * This class overrides
 * <code>invalidate</code>,
 * <code>validate</code>,
 * <code>revalidate</code>,
 * <code>repaint</code>,
 * and
 * <code>firePropertyChange</code>
 * solely to improve performance.
 * If not overridden, these frequently called methods would execute code paths
 * that are unnecessary for the default tree cell renderer.
 * If you write your own renderer,
 * take care to weigh the benefits and
 * drawbacks of overriding these methods.
 *
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans&trade;
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * <p>
 *  在树中显示条目。 <code> DefaultTreeCellRenderer </code>不是不透明的,除非你的子类绘制你不应该改变这。
 * 有关示例,请参阅<em> Java教程</em>中的<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/tree.html">
 * 如何使用树</a>使用此类定制节点显示。
 *  在树中显示条目。 <code> DefaultTreeCellRenderer </code>不是不透明的,除非你的子类绘制你不应该改变这。
 * <p>
 *  {@code DefaultTreeCellRenderer}使用的一组图标和颜色可以使用各种setter方法进行配置。每个属性的值从defaults表初始化。
 * 当外观和感觉变化(调用{@code updateUI})时,任何属性类型为{@code UIResource}的属性都将从默认表中刷新。
 * 下表列出了{@code DefaultTreeCellRenderer}属性和默认表键之间的映射：。
 * <table border="1" cellpadding="1" cellspacing="0" summary="">
 * <tr valign="top"  align="left">
 *  <th style ="background-color：#CCCCFF"align ="left">属性：<th style ="background-color：#CCCCFF"align ="left">
 * 键：<tr> <td>"leafIcon"< td>"Tree.leafIcon"<tr> <td>"closedIcon"<td>"Tree.closedIcon"<tr> <td>"openIcon
 * "<td>"Tree.openIcon"<tr> <td>"textSelectionColor" <td>"Tree.selectionForeground"<tr> <td>"textNonSele
 * ctionColor"<td>"Tree.textForeground"<tr> <td>"backgroundSelectionColor"<td>"Tree.selectionBackground"
 * <tr> <td>"backgroundNonSelectionColor "<td>"Tree.textBackground"<tr> <td>"borderSelectionColor"<td>"T
 * ree.selectionBorderColor"。
 * </table>
 * <p>
 * <strong> <a name="override"> Implementation Note：</a> </strong>此类覆盖<code> invalidate </code>,<code> v
 * alidate </code>,<code> revalidate </code >,<code> repaint </code>和<code> firePropertyChange </code>,以
 * 提高性能。
 * 如果不被覆盖,这些经常被调用的方法将执行默认树细胞渲染器不必要的代码路径。如果你编写自己的渲染器,注意权衡覆盖这些方法的好处和缺点。
 * 
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author Rob Davis
 * @author Ray Ryan
 * @author Scott Violet
 */
public class DefaultTreeCellRenderer extends JLabel implements TreeCellRenderer
{
    /** Last tree the renderer was painted in. */
    private JTree tree;

    /** Is the value currently selected. */
    protected boolean selected;
    /** True if has focus. */
    protected boolean hasFocus;
    /** True if draws focus border around icon as well. */
    private boolean drawsFocusBorderAroundIcon;
    /** If true, a dashed line is drawn as the focus indicator. */
    private boolean drawDashedFocusIndicator;

    // If drawDashedFocusIndicator is true, the following are used.
    /**
     * Background color of the tree.
     * <p>
     *  树的背景颜色。
     * 
     */
    private Color treeBGColor;
    /**
     * Color to draw the focus indicator in, determined from the background.
     * color.
     * <p>
     *  绘制焦点指示符的颜色,从背景确定。颜色。
     * 
     */
    private Color focusBGColor;

    // Icons
    /** Icon used to show non-leaf nodes that aren't expanded. */
    transient protected Icon closedIcon;

    /** Icon used to show leaf nodes. */
    transient protected Icon leafIcon;

    /** Icon used to show non-leaf nodes that are expanded. */
    transient protected Icon openIcon;

    // Colors
    /** Color to use for the foreground for selected nodes. */
    protected Color textSelectionColor;

    /** Color to use for the foreground for non-selected nodes. */
    protected Color textNonSelectionColor;

    /** Color to use for the background when a node is selected. */
    protected Color backgroundSelectionColor;

    /** Color to use for the background when the node isn't selected. */
    protected Color backgroundNonSelectionColor;

    /** Color to use for the focus indicator when the node has focus. */
    protected Color borderSelectionColor;

    private boolean isDropCell;
    private boolean fillBackground;

    /**
     * Set to true after the constructor has run.
     * <p>
     *  在构造函数运行后设置为true。
     * 
     */
    private boolean inited;

    /**
     * Creates a {@code DefaultTreeCellRenderer}. Icons and text color are
     * determined from the {@code UIManager}.
     * <p>
     *  创建{@code DefaultTreeCellRenderer}。图标和文本颜色由{@code UIManager}确定。
     * 
     */
    public DefaultTreeCellRenderer() {
        inited = true;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.7
     */
    public void updateUI() {
        super.updateUI();
        // To avoid invoking new methods from the constructor, the
        // inited field is first checked. If inited is false, the constructor
        // has not run and there is no point in checking the value. As
        // all look and feels have a non-null value for these properties,
        // a null value means the developer has specifically set it to
        // null. As such, if the value is null, this does not reset the
        // value.
        if (!inited || (getLeafIcon() instanceof UIResource)) {
            setLeafIcon(DefaultLookup.getIcon(this, ui, "Tree.leafIcon"));
        }
        if (!inited || (getClosedIcon() instanceof UIResource)) {
            setClosedIcon(DefaultLookup.getIcon(this, ui, "Tree.closedIcon"));
        }
        if (!inited || (getOpenIcon() instanceof UIManager)) {
            setOpenIcon(DefaultLookup.getIcon(this, ui, "Tree.openIcon"));
        }
        if (!inited || (getTextSelectionColor() instanceof UIResource)) {
            setTextSelectionColor(
                    DefaultLookup.getColor(this, ui, "Tree.selectionForeground"));
        }
        if (!inited || (getTextNonSelectionColor() instanceof UIResource)) {
            setTextNonSelectionColor(
                    DefaultLookup.getColor(this, ui, "Tree.textForeground"));
        }
        if (!inited || (getBackgroundSelectionColor() instanceof UIResource)) {
            setBackgroundSelectionColor(
                    DefaultLookup.getColor(this, ui, "Tree.selectionBackground"));
        }
        if (!inited ||
                (getBackgroundNonSelectionColor() instanceof UIResource)) {
            setBackgroundNonSelectionColor(
                    DefaultLookup.getColor(this, ui, "Tree.textBackground"));
        }
        if (!inited || (getBorderSelectionColor() instanceof UIResource)) {
            setBorderSelectionColor(
                    DefaultLookup.getColor(this, ui, "Tree.selectionBorderColor"));
        }
        drawsFocusBorderAroundIcon = DefaultLookup.getBoolean(
                this, ui, "Tree.drawsFocusBorderAroundIcon", false);
        drawDashedFocusIndicator = DefaultLookup.getBoolean(
                this, ui, "Tree.drawDashedFocusIndicator", false);

        fillBackground = DefaultLookup.getBoolean(this, ui, "Tree.rendererFillBackground", true);
        Insets margins = DefaultLookup.getInsets(this, ui, "Tree.rendererMargins");
        if (margins != null) {
            setBorder(new EmptyBorder(margins.top, margins.left,
                    margins.bottom, margins.right));
        }

        setName("Tree.cellRenderer");
    }


    /**
      * Returns the default icon, for the current laf, that is used to
      * represent non-leaf nodes that are expanded.
      * <p>
      *  返回当前laf的默认图标,用于表示展开的非叶节点。
      * 
      */
    public Icon getDefaultOpenIcon() {
        return DefaultLookup.getIcon(this, ui, "Tree.openIcon");
    }

    /**
      * Returns the default icon, for the current laf, that is used to
      * represent non-leaf nodes that are not expanded.
      * <p>
      *  返回当前laf的默认图标,用于表示未展开的非叶节点。
      * 
      */
    public Icon getDefaultClosedIcon() {
        return DefaultLookup.getIcon(this, ui, "Tree.closedIcon");
    }

    /**
      * Returns the default icon, for the current laf, that is used to
      * represent leaf nodes.
      * <p>
      *  返回用于表示叶节点的当前laf的默认图标。
      * 
      */
    public Icon getDefaultLeafIcon() {
        return DefaultLookup.getIcon(this, ui, "Tree.leafIcon");
    }

    /**
      * Sets the icon used to represent non-leaf nodes that are expanded.
      * <p>
      * 设置用于表示扩展的非叶节点的图标。
      * 
      */
    public void setOpenIcon(Icon newIcon) {
        openIcon = newIcon;
    }

    /**
      * Returns the icon used to represent non-leaf nodes that are expanded.
      * <p>
      *  返回用于表示展开的非叶节点的图标。
      * 
      */
    public Icon getOpenIcon() {
        return openIcon;
    }

    /**
      * Sets the icon used to represent non-leaf nodes that are not expanded.
      * <p>
      *  设置用于表示未扩展的非叶节点的图标。
      * 
      */
    public void setClosedIcon(Icon newIcon) {
        closedIcon = newIcon;
    }

    /**
      * Returns the icon used to represent non-leaf nodes that are not
      * expanded.
      * <p>
      *  返回用于表示未扩展的非叶节点的图标。
      * 
      */
    public Icon getClosedIcon() {
        return closedIcon;
    }

    /**
      * Sets the icon used to represent leaf nodes.
      * <p>
      *  设置用于表示叶节点的图标。
      * 
      */
    public void setLeafIcon(Icon newIcon) {
        leafIcon = newIcon;
    }

    /**
      * Returns the icon used to represent leaf nodes.
      * <p>
      *  返回用于表示叶节点的图标。
      * 
      */
    public Icon getLeafIcon() {
        return leafIcon;
    }

    /**
      * Sets the color the text is drawn with when the node is selected.
      * <p>
      *  设置选择节点时绘制文本的颜色。
      * 
      */
    public void setTextSelectionColor(Color newColor) {
        textSelectionColor = newColor;
    }

    /**
      * Returns the color the text is drawn with when the node is selected.
      * <p>
      *  返回选择节点时绘制文本的颜色。
      * 
      */
    public Color getTextSelectionColor() {
        return textSelectionColor;
    }

    /**
      * Sets the color the text is drawn with when the node isn't selected.
      * <p>
      *  设置未选择节点时绘制文本的颜色。
      * 
      */
    public void setTextNonSelectionColor(Color newColor) {
        textNonSelectionColor = newColor;
    }

    /**
      * Returns the color the text is drawn with when the node isn't selected.
      * <p>
      *  返回未选择节点时绘制文本的颜色。
      * 
      */
    public Color getTextNonSelectionColor() {
        return textNonSelectionColor;
    }

    /**
      * Sets the color to use for the background if node is selected.
      * <p>
      *  设置在选择节点时用于背景的颜色。
      * 
      */
    public void setBackgroundSelectionColor(Color newColor) {
        backgroundSelectionColor = newColor;
    }


    /**
      * Returns the color to use for the background if node is selected.
      * <p>
      *  如果选择了节点,则返回用于背景的颜色。
      * 
      */
    public Color getBackgroundSelectionColor() {
        return backgroundSelectionColor;
    }

    /**
      * Sets the background color to be used for non selected nodes.
      * <p>
      *  设置要用于非选定节点的背景颜色。
      * 
      */
    public void setBackgroundNonSelectionColor(Color newColor) {
        backgroundNonSelectionColor = newColor;
    }

    /**
      * Returns the background color to be used for non selected nodes.
      * <p>
      *  返回用于非选定节点的背景颜色。
      * 
      */
    public Color getBackgroundNonSelectionColor() {
        return backgroundNonSelectionColor;
    }

    /**
      * Sets the color to use for the border.
      * <p>
      *  设置边框使用的颜色。
      * 
      */
    public void setBorderSelectionColor(Color newColor) {
        borderSelectionColor = newColor;
    }

    /**
      * Returns the color the border is drawn.
      * <p>
      *  返回绘制边框的颜色。
      * 
      */
    public Color getBorderSelectionColor() {
        return borderSelectionColor;
    }

    /**
     * Subclassed to map <code>FontUIResource</code>s to null. If
     * <code>font</code> is null, or a <code>FontUIResource</code>, this
     * has the effect of letting the font of the JTree show
     * through. On the other hand, if <code>font</code> is non-null, and not
     * a <code>FontUIResource</code>, the font becomes <code>font</code>.
     * <p>
     *  子类化以将<code> FontUIResource </code>映射为null。
     * 如果<code> font </code>为null或<code> FontUIResource </code>,这会让JTree的字体显示出来。
     * 另一方面,如果<code> font </code>是非空的,而不是<code> FontUIResource </code>,则字体变为<code> font </code>。
     * 
     */
    public void setFont(Font font) {
        if(font instanceof FontUIResource)
            font = null;
        super.setFont(font);
    }

    /**
     * Gets the font of this component.
     * <p>
     *  获取此组件的字体。
     * 
     * 
     * @return this component's font; if a font has not been set
     * for this component, the font of its parent is returned
     */
    public Font getFont() {
        Font font = super.getFont();

        if (font == null && tree != null) {
            // Strive to return a non-null value, otherwise the html support
            // will typically pick up the wrong font in certain situations.
            font = tree.getFont();
        }
        return font;
    }

    /**
     * Subclassed to map <code>ColorUIResource</code>s to null. If
     * <code>color</code> is null, or a <code>ColorUIResource</code>, this
     * has the effect of letting the background color of the JTree show
     * through. On the other hand, if <code>color</code> is non-null, and not
     * a <code>ColorUIResource</code>, the background becomes
     * <code>color</code>.
     * <p>
     * 子类化以将<code> ColorUIResource </code>映射到null。
     * 如果<code> color </code>为null或<code> ColorUIResource </code>,这会产生JTree的背景颜色。
     * 另一方面,如果<code> color </code>是非空的,而不是<code> ColorUIResource </code>,则后台变为<code> color </code>。
     * 
     */
    public void setBackground(Color color) {
        if(color instanceof ColorUIResource)
            color = null;
        super.setBackground(color);
    }

    /**
      * Configures the renderer based on the passed in components.
      * The value is set from messaging the tree with
      * <code>convertValueToText</code>, which ultimately invokes
      * <code>toString</code> on <code>value</code>.
      * The foreground color is set based on the selection and the icon
      * is set based on the <code>leaf</code> and <code>expanded</code>
      * parameters.
      * <p>
      *  根据传入的组件配置渲染器。
      * 该值是从与<code> convertValueToText </code>的消息树设置的,它最终调用<code> value </code>上的<code> toString </code>。
      * 根据选择设置前景颜色,并且基于<code> leaf </code>和<code> expanded </code>参数设置图标。
      * 
      */
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean sel,
                                                  boolean expanded,
                                                  boolean leaf, int row,
                                                  boolean hasFocus) {
        String         stringValue = tree.convertValueToText(value, sel,
                                          expanded, leaf, row, hasFocus);

        this.tree = tree;
        this.hasFocus = hasFocus;
        setText(stringValue);

        Color fg = null;
        isDropCell = false;

        JTree.DropLocation dropLocation = tree.getDropLocation();
        if (dropLocation != null
                && dropLocation.getChildIndex() == -1
                && tree.getRowForPath(dropLocation.getPath()) == row) {

            Color col = DefaultLookup.getColor(this, ui, "Tree.dropCellForeground");
            if (col != null) {
                fg = col;
            } else {
                fg = getTextSelectionColor();
            }

            isDropCell = true;
        } else if (sel) {
            fg = getTextSelectionColor();
        } else {
            fg = getTextNonSelectionColor();
        }

        setForeground(fg);

        Icon icon = null;
        if (leaf) {
            icon = getLeafIcon();
        } else if (expanded) {
            icon = getOpenIcon();
        } else {
            icon = getClosedIcon();
        }

        if (!tree.isEnabled()) {
            setEnabled(false);
            LookAndFeel laf = UIManager.getLookAndFeel();
            Icon disabledIcon = laf.getDisabledIcon(tree, icon);
            if (disabledIcon != null) icon = disabledIcon;
            setDisabledIcon(icon);
        } else {
            setEnabled(true);
            setIcon(icon);
        }
        setComponentOrientation(tree.getComponentOrientation());

        selected = sel;

        return this;
    }

    /**
      * Paints the value.  The background is filled based on selected.
      * <p>
      *  绘制值。背景根据选择填充。
      * 
      */
    public void paint(Graphics g) {
        Color bColor;

        if (isDropCell) {
            bColor = DefaultLookup.getColor(this, ui, "Tree.dropCellBackground");
            if (bColor == null) {
                bColor = getBackgroundSelectionColor();
            }
        } else if (selected) {
            bColor = getBackgroundSelectionColor();
        } else {
            bColor = getBackgroundNonSelectionColor();
            if (bColor == null) {
                bColor = getBackground();
            }
        }

        int imageOffset = -1;
        if (bColor != null && fillBackground) {
            imageOffset = getLabelStart();
            g.setColor(bColor);
            if(getComponentOrientation().isLeftToRight()) {
                g.fillRect(imageOffset, 0, getWidth() - imageOffset,
                           getHeight());
            } else {
                g.fillRect(0, 0, getWidth() - imageOffset,
                           getHeight());
            }
        }

        if (hasFocus) {
            if (drawsFocusBorderAroundIcon) {
                imageOffset = 0;
            }
            else if (imageOffset == -1) {
                imageOffset = getLabelStart();
            }
            if(getComponentOrientation().isLeftToRight()) {
                paintFocus(g, imageOffset, 0, getWidth() - imageOffset,
                           getHeight(), bColor);
            } else {
                paintFocus(g, 0, 0, getWidth() - imageOffset, getHeight(), bColor);
            }
        }
        super.paint(g);
    }

    private void paintFocus(Graphics g, int x, int y, int w, int h, Color notColor) {
        Color       bsColor = getBorderSelectionColor();

        if (bsColor != null && (selected || !drawDashedFocusIndicator)) {
            g.setColor(bsColor);
            g.drawRect(x, y, w - 1, h - 1);
        }
        if (drawDashedFocusIndicator && notColor != null) {
            if (treeBGColor != notColor) {
                treeBGColor = notColor;
                focusBGColor = new Color(~notColor.getRGB());
            }
            g.setColor(focusBGColor);
            BasicGraphicsUtils.drawDashedRect(g, x, y, w, h);
        }
    }

    private int getLabelStart() {
        Icon currentI = getIcon();
        if(currentI != null && getText() != null) {
            return currentI.getIconWidth() + Math.max(0, getIconTextGap() - 1);
        }
        return 0;
    }

    /**
     * Overrides <code>JComponent.getPreferredSize</code> to
     * return slightly wider preferred size value.
     * <p>
     *  覆盖<code> JComponent.getPreferredSize </code>以返回稍宽的首选大小值。
     * 
     */
    public Dimension getPreferredSize() {
        Dimension        retDimension = super.getPreferredSize();

        if(retDimension != null)
            retDimension = new Dimension(retDimension.width + 3,
                                         retDimension.height);
        return retDimension;
    }

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    * <p>
    *  由于性能原因被覆盖。有关详细信息,请参见<a href="#override">实现注意</a>。
    * 
    */
    public void validate() {}

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    *
    * <p>
    *  由于性能原因被覆盖。有关详细信息,请参见<a href="#override">实现注意</a>。
    * 
    * 
    * @since 1.5
    */
    public void invalidate() {}

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    * <p>
    *  由于性能原因被覆盖。有关详细信息,请参见<a href="#override">实现注意</a>。
    * 
    */
    public void revalidate() {}

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    * <p>
    *  由于性能原因被覆盖。有关详细信息,请参见<a href="#override">实现注意</a>。
    * 
    */
    public void repaint(long tm, int x, int y, int width, int height) {}

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    * <p>
    *  由于性能原因被覆盖。有关详细信息,请参见<a href="#override">实现注意</a>。
    * 
    */
    public void repaint(Rectangle r) {}

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    *
    * <p>
    * 由于性能原因被覆盖。有关详细信息,请参见<a href="#override">实现注意</a>。
    * 
    * 
    * @since 1.5
    */
    public void repaint() {}

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    * <p>
    *  由于性能原因被覆盖。有关详细信息,请参见<a href="#override">实现注意</a>。
    * 
    */
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        // Strings get interned...
        if (propertyName == "text"
                || ((propertyName == "font" || propertyName == "foreground")
                    && oldValue != newValue
                    && getClientProperty(javax.swing.plaf.basic.BasicHTML.propertyKey) != null)) {

            super.firePropertyChange(propertyName, oldValue, newValue);
        }
    }

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    * <p>
    *  由于性能原因被覆盖。有关详细信息,请参见<a href="#override">实现注意</a>。
    * 
    */
    public void firePropertyChange(String propertyName, byte oldValue, byte newValue) {}

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    * <p>
    *  由于性能原因被覆盖。有关详细信息,请参见<a href="#override">实现注意</a>。
    * 
    */
    public void firePropertyChange(String propertyName, char oldValue, char newValue) {}

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    * <p>
    *  由于性能原因被覆盖。有关详细信息,请参见<a href="#override">实现注意</a>。
    * 
    */
    public void firePropertyChange(String propertyName, short oldValue, short newValue) {}

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    * <p>
    *  由于性能原因被覆盖。有关详细信息,请参见<a href="#override">实现注意</a>。
    * 
    */
    public void firePropertyChange(String propertyName, int oldValue, int newValue) {}

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    * <p>
    *  由于性能原因被覆盖。有关详细信息,请参见<a href="#override">实现注意</a>。
    * 
    */
    public void firePropertyChange(String propertyName, long oldValue, long newValue) {}

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    * <p>
    *  由于性能原因被覆盖。有关详细信息,请参见<a href="#override">实现注意</a>。
    * 
    */
    public void firePropertyChange(String propertyName, float oldValue, float newValue) {}

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    * <p>
    *  由于性能原因被覆盖。有关详细信息,请参见<a href="#override">实现注意</a>。
    * 
    */
    public void firePropertyChange(String propertyName, double oldValue, double newValue) {}

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    * <p>
    *  由于性能原因被覆盖。有关详细信息,请参见<a href="#override">实现注意</a>。
    */
    public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {}

}
