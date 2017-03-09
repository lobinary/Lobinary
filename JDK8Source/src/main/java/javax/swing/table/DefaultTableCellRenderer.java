/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing.table;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.Component;
import java.awt.Color;
import java.awt.Rectangle;

import java.io.Serializable;
import sun.swing.DefaultLookup;


/**
 * The standard class for rendering (displaying) individual cells
 * in a <code>JTable</code>.
 * <p>
 *
 * <strong><a name="override">Implementation Note:</a></strong>
 * This class inherits from <code>JLabel</code>, a standard component class.
 * However <code>JTable</code> employs a unique mechanism for rendering
 * its cells and therefore requires some slightly modified behavior
 * from its cell renderer.
 * The table class defines a single cell renderer and uses it as a
 * as a rubber-stamp for rendering all cells in the table;
 * it renders the first cell,
 * changes the contents of that cell renderer,
 * shifts the origin to the new location, re-draws it, and so on.
 * The standard <code>JLabel</code> component was not
 * designed to be used this way and we want to avoid
 * triggering a <code>revalidate</code> each time the
 * cell is drawn. This would greatly decrease performance because the
 * <code>revalidate</code> message would be
 * passed up the hierarchy of the container to determine whether any other
 * components would be affected.
 * As the renderer is only parented for the lifetime of a painting operation
 * we similarly want to avoid the overhead associated with walking the
 * hierarchy for painting operations.
 * So this class
 * overrides the <code>validate</code>, <code>invalidate</code>,
 * <code>revalidate</code>, <code>repaint</code>, and
 * <code>firePropertyChange</code> methods to be
 * no-ops and override the <code>isOpaque</code> method solely to improve
 * performance.  If you write your own renderer,
 * please keep this performance consideration in mind.
 * <p>
 *
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
 *  用于在<code> JTable </code>中呈现(显示)单个单元格的标准类。
 * <p>
 * 
 * <strong> <a name="override">实施注意事项：</a> </strong>此类继承自标准组件类<code> JLabel </code>。
 * 然而,<code> JTable </code>使用一个独特的机制来渲染它的单元格,因此需要从它的单元格渲染器稍微修改一些行为。
 * 表类定义单个单元格渲染器,并将其用作橡皮图章,用于渲染表中的所有单元格;它渲染第一个单元格,更改该单元格渲染器的内容,将原点移动到新位置,重新绘制它,等等。
 * 标准的<code> JLabel </code>组件不是设计为使用这种方式,我们希望避免每次绘制单元格时触发<code> revalidate </code>。
 * 这将大大降低性能,因为<code> revalidate </code>消息将沿着容器的层次结构向上传递,以确定是否会影响任何其他组件。
 * 由于渲染器仅在绘画操作的生命周期内为父节点,所以我们同样希望避免与用于绘画操作的层次结构相关联的开销。
 * 所以这个类重写<code> validate </code>,<code> invalidate </code>,<code> revalidate </code>,<code> repaint </code>
 * 和<code> firePropertyChange </code>方法作为非操作,并覆盖<code> isOpaque </code>方法,以提高性能。
 * 由于渲染器仅在绘画操作的生命周期内为父节点,所以我们同样希望避免与用于绘画操作的层次结构相关联的开销。如果你写自己的渲染器,请记住这个性能考虑。
 * <p>
 * 
 * <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author Philip Milne
 * @see JTable
 */
public class DefaultTableCellRenderer extends JLabel
    implements TableCellRenderer, Serializable
{

   /**
    * An empty <code>Border</code>. This field might not be used. To change the
    * <code>Border</code> used by this renderer override the
    * <code>getTableCellRendererComponent</code> method and set the border
    * of the returned component directly.
    * <p>
    *  一个空的<code> Border </code>。可能不使用此字段。
    * 要更改此渲染器使用的<code> Border </code>,请覆盖<code> getTableCellRendererComponent </code>方法,并直接设置返回组件的边框。
    * 
    */
    private static final Border SAFE_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
    private static final Border DEFAULT_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
    protected static Border noFocusBorder = DEFAULT_NO_FOCUS_BORDER;

    // We need a place to store the color the JLabel should be returned
    // to after its foreground and background colors have been set
    // to the selection background color.
    // These ivars will be made protected when their names are finalized.
    private Color unselectedForeground;
    private Color unselectedBackground;

    /**
     * Creates a default table cell renderer.
     * <p>
     *  创建默认表单元格渲染器。
     * 
     */
    public DefaultTableCellRenderer() {
        super();
        setOpaque(true);
        setBorder(getNoFocusBorder());
        setName("Table.cellRenderer");
    }

    private Border getNoFocusBorder() {
        Border border = DefaultLookup.getBorder(this, ui, "Table.cellNoFocusBorder");
        if (System.getSecurityManager() != null) {
            if (border != null) return border;
            return SAFE_NO_FOCUS_BORDER;
        } else if (border != null) {
            if (noFocusBorder == null || noFocusBorder == DEFAULT_NO_FOCUS_BORDER) {
                return border;
            }
        }
        return noFocusBorder;
    }

    /**
     * Overrides <code>JComponent.setForeground</code> to assign
     * the unselected-foreground color to the specified color.
     *
     * <p>
     *  覆盖<code> JComponent.setForeground </code>以将未选择的前景颜色分配给指定的颜色。
     * 
     * 
     * @param c set the foreground color to this value
     */
    public void setForeground(Color c) {
        super.setForeground(c);
        unselectedForeground = c;
    }

    /**
     * Overrides <code>JComponent.setBackground</code> to assign
     * the unselected-background color to the specified color.
     *
     * <p>
     *  覆盖<code> JComponent.setBackground </code>以将未选定的背景颜色分配给指定的颜色。
     * 
     * 
     * @param c set the background color to this value
     */
    public void setBackground(Color c) {
        super.setBackground(c);
        unselectedBackground = c;
    }

    /**
     * Notification from the <code>UIManager</code> that the look and feel
     * [L&amp;F] has changed.
     * Replaces the current UI object with the latest version from the
     * <code>UIManager</code>.
     *
     * <p>
     *  来自<code> UIManager </code>的通知,外观和感觉[L&amp; F]已更改。使用<code> UIManager </code>中的最新版本替换当前的UI对象。
     * 
     * 
     * @see JComponent#updateUI
     */
    public void updateUI() {
        super.updateUI();
        setForeground(null);
        setBackground(null);
    }

    // implements javax.swing.table.TableCellRenderer
    /**
     *
     * Returns the default table cell renderer.
     * <p>
     * During a printing operation, this method will be called with
     * <code>isSelected</code> and <code>hasFocus</code> values of
     * <code>false</code> to prevent selection and focus from appearing
     * in the printed output. To do other customization based on whether
     * or not the table is being printed, check the return value from
     * {@link javax.swing.JComponent#isPaintingForPrint()}.
     *
     * <p>
     *  返回默认表格单元格渲染器。
     * <p>
     * 在打印操作期间,将使用<code> isSelected </code>和<code> hasFocus </code>值<code> false </code>调用此方法,以防止选择和焦点出现在打印输
     * 出中。
     * 要根据表是否正在打印进行其他自定义,请检查{@link javax.swing.JComponent#isPaintingForPrint()}的返回值。
     * 
     * 
     * @param table  the <code>JTable</code>
     * @param value  the value to assign to the cell at
     *                  <code>[row, column]</code>
     * @param isSelected true if cell is selected
     * @param hasFocus true if cell has focus
     * @param row  the row of the cell to render
     * @param column the column of the cell to render
     * @return the default table cell renderer
     * @see javax.swing.JComponent#isPaintingForPrint()
     */
    public Component getTableCellRendererComponent(JTable table, Object value,
                          boolean isSelected, boolean hasFocus, int row, int column) {
        if (table == null) {
            return this;
        }

        Color fg = null;
        Color bg = null;

        JTable.DropLocation dropLocation = table.getDropLocation();
        if (dropLocation != null
                && !dropLocation.isInsertRow()
                && !dropLocation.isInsertColumn()
                && dropLocation.getRow() == row
                && dropLocation.getColumn() == column) {

            fg = DefaultLookup.getColor(this, ui, "Table.dropCellForeground");
            bg = DefaultLookup.getColor(this, ui, "Table.dropCellBackground");

            isSelected = true;
        }

        if (isSelected) {
            super.setForeground(fg == null ? table.getSelectionForeground()
                                           : fg);
            super.setBackground(bg == null ? table.getSelectionBackground()
                                           : bg);
        } else {
            Color background = unselectedBackground != null
                                    ? unselectedBackground
                                    : table.getBackground();
            if (background == null || background instanceof javax.swing.plaf.UIResource) {
                Color alternateColor = DefaultLookup.getColor(this, ui, "Table.alternateRowColor");
                if (alternateColor != null && row % 2 != 0) {
                    background = alternateColor;
                }
            }
            super.setForeground(unselectedForeground != null
                                    ? unselectedForeground
                                    : table.getForeground());
            super.setBackground(background);
        }

        setFont(table.getFont());

        if (hasFocus) {
            Border border = null;
            if (isSelected) {
                border = DefaultLookup.getBorder(this, ui, "Table.focusSelectedCellHighlightBorder");
            }
            if (border == null) {
                border = DefaultLookup.getBorder(this, ui, "Table.focusCellHighlightBorder");
            }
            setBorder(border);

            if (!isSelected && table.isCellEditable(row, column)) {
                Color col;
                col = DefaultLookup.getColor(this, ui, "Table.focusCellForeground");
                if (col != null) {
                    super.setForeground(col);
                }
                col = DefaultLookup.getColor(this, ui, "Table.focusCellBackground");
                if (col != null) {
                    super.setBackground(col);
                }
            }
        } else {
            setBorder(getNoFocusBorder());
        }

        setValue(value);

        return this;
    }

    /*
     * The following methods are overridden as a performance measure to
     * to prune code-paths are often called in the case of renders
     * but which we know are unnecessary.  Great care should be taken
     * when writing your own renderer to weigh the benefits and
     * drawbacks of overriding methods like these.
     * <p>
     *  以下方法被覆盖作为删除代码的性能度量路径通常在渲染的情况下调用,但我们知道是不必要的。在编写自己的渲染器时应该格外小心,以便权衡这些重写方法的优点和缺点。
     * 
     */

    /**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a>
     * for more information.
     * <p>
     *  由于性能原因被覆盖。有关详细信息,请参见<a href="#override">实现注意</a>。
     * 
     */
    public boolean isOpaque() {
        Color back = getBackground();
        Component p = getParent();
        if (p != null) {
            p = p.getParent();
        }

        // p should now be the JTable.
        boolean colorMatch = (back != null) && (p != null) &&
            back.equals(p.getBackground()) &&
                        p.isOpaque();
        return !colorMatch && super.isOpaque();
    }

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
    public void validate() {}

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
    public void repaint(Rectangle r) { }

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
    public void repaint() {
    }

    /**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a>
     * for more information.
     * <p>
     * 由于性能原因被覆盖。有关详细信息,请参见<a href="#override">实现注意</a>。
     * 
     */
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        // Strings get interned...
        if (propertyName=="text"
                || propertyName == "labelFor"
                || propertyName == "displayedMnemonic"
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
    public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) { }


    /**
     * Sets the <code>String</code> object for the cell being rendered to
     * <code>value</code>.
     *
     * <p>
     *  将要呈现的单元格的<code> String </code>对象设置为<code> value </code>。
     * 
     * 
     * @param value  the string value for this cell; if value is
     *          <code>null</code> it sets the text value to an empty string
     * @see JLabel#setText
     *
     */
    protected void setValue(Object value) {
        setText((value == null) ? "" : value.toString());
    }


    /**
     * A subclass of <code>DefaultTableCellRenderer</code> that
     * implements <code>UIResource</code>.
     * <code>DefaultTableCellRenderer</code> doesn't implement
     * <code>UIResource</code>
     * directly so that applications can safely override the
     * <code>cellRenderer</code> property with
     * <code>DefaultTableCellRenderer</code> subclasses.
     * <p>
     * <strong>Warning:</strong>
     * Serialized objects of this class will not be compatible with
     * future Swing releases. The current serialization support is
     * appropriate for short term storage or RMI between applications running
     * the same version of Swing.  As of 1.4, support for long term storage
     * of all JavaBeans&trade;
     * has been added to the <code>java.beans</code> package.
     * Please see {@link java.beans.XMLEncoder}.
     * <p>
     *  实现<code> UIResource </code>的<code> DefaultTableCellRenderer </code>的子类。
     *  <code> DefaultTableCellRenderer </code>不直接实现<code> UIResource </code>,以便应用程序可以安全地覆盖<code> cellRender
     * er </code>属性和<code> DefaultTableCellRenderer </code>子类。
     *  实现<code> UIResource </code>的<code> DefaultTableCellRenderer </code>的子类。
     */
    public static class UIResource extends DefaultTableCellRenderer
        implements javax.swing.plaf.UIResource
    {
    }

}
