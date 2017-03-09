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
package javax.swing;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.beans.PropertyChangeListener;
import java.util.Locale;
import java.util.Vector;

import javax.accessibility.*;

/**
 * This class is inserted in between cell renderers and the components that
 * use them.  It just exists to thwart the repaint() and invalidate() methods
 * which would otherwise propagate up the tree when the renderer was configured.
 * It's used by the implementations of JTable, JTree, and JList.  For example,
 * here's how CellRendererPane is used in the code the paints each row
 * in a JList:
 * <pre>
 *   cellRendererPane = new CellRendererPane();
 *   ...
 *   Component rendererComponent = renderer.getListCellRendererComponent();
 *   renderer.configureListCellRenderer(dataModel.getElementAt(row), row);
 *   cellRendererPane.paintComponent(g, rendererComponent, this, x, y, w, h);
 * </pre>
 * <p>
 * A renderer component must override isShowing() and unconditionally return
 * true to work correctly because the Swing paint does nothing for components
 * with isShowing false.
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
 *  此类插入在单元格渲染器和使用它们的组件之间。它只是存在阻碍repaint()和invalidate()方法,否则会在渲染器配置时在树上传播。它被JTable,JTree和JList的实现使用。
 * 例如,下面是如何使用CellRendererPane在代码中绘制JList中的每一行：。
 * <pre>
 *  cellRendererPane = new CellRendererPane(); ... Component rendererComponent = renderer.getListCellRen
 * dererComponent(); renderer.configureListCellRenderer(dataModel.getElementAt(row),row); cellRendererPa
 * ne.paintComponent(g,rendererComponent,this,x,y,w,h);。
 * </pre>
 * <p>
 *  渲染器组件必须重写isShowing()并无条件地返回true才能正常工作,因为Swing paint对于isShowing为false的组件不起作用。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author Hans Muller
 */
public class CellRendererPane extends Container implements Accessible
{
    /**
     * Construct a CellRendererPane object.
     * <p>
     *  构造CellRendererPane对象。
     * 
     */
    public CellRendererPane() {
        super();
        setLayout(null);
        setVisible(false);
    }

    /**
     * Overridden to avoid propagating a invalidate up the tree when the
     * cell renderer child is configured.
     * <p>
     * 覆盖以避免在配置单元渲染器子代时在树上传播invalidate。
     * 
     */
    public void invalidate() { }


    /**
     * Shouldn't be called.
     * <p>
     *  不应该叫。
     * 
     */
    public void paint(Graphics g) { }


    /**
     * Shouldn't be called.
     * <p>
     *  不应该叫。
     * 
     */
    public void update(Graphics g) { }


    /**
     * If the specified component is already a child of this then we don't
     * bother doing anything - stacking order doesn't matter for cell
     * renderer components (CellRendererPane doesn't paint anyway).
     * <p>
     *  如果指定的组件已经是这个的孩子,那么我们不会做任何事情 - 堆叠顺序对细胞渲染器组件无关紧要(CellRendererPane不会绘制)。
     * 
     */
    protected void addImpl(Component x, Object constraints, int index) {
        if (x.getParent() == this) {
            return;
        }
        else {
            super.addImpl(x, constraints, index);
        }
    }


    /**
     * Paint a cell renderer component c on graphics object g.  Before the component
     * is drawn it's reparented to this (if that's necessary), it's bounds
     * are set to w,h and the graphics object is (effectively) translated to x,y.
     * If it's a JComponent, double buffering is temporarily turned off. After
     * the component is painted it's bounds are reset to -w, -h, 0, 0 so that, if
     * it's the last renderer component painted, it will not start consuming input.
     * The Container p is the component we're actually drawing on, typically it's
     * equal to this.getParent(). If shouldValidate is true the component c will be
     * validated before painted.
     * <p>
     *  在图形对象上绘制单元格渲染器组件c。在组件被绘制之前,它被重新排列为这个(如果这是必要的),它的边界设置为w,h,图形对象(有效地)转换为x,y。如果是JComponent,双缓冲暂时关闭。
     * 组件绘制后,它的边界被重置为-w,-h,0,0,这样,如果它是最后一个渲染器组件,它不会开始消耗输入。 Container p是我们实际绘制的组件,通常它等于this.getParent()。
     * 如果shouldValidate为true,组件c将在绘制前进行验证。
     * 
     */
    public void paintComponent(Graphics g, Component c, Container p, int x, int y, int w, int h, boolean shouldValidate) {
        if (c == null) {
            if (p != null) {
                Color oldColor = g.getColor();
                g.setColor(p.getBackground());
                g.fillRect(x, y, w, h);
                g.setColor(oldColor);
            }
            return;
        }

        if (c.getParent() != this) {
            this.add(c);
        }

        c.setBounds(x, y, w, h);

        if(shouldValidate) {
            c.validate();
        }

        boolean wasDoubleBuffered = false;
        if ((c instanceof JComponent) && ((JComponent)c).isDoubleBuffered()) {
            wasDoubleBuffered = true;
            ((JComponent)c).setDoubleBuffered(false);
        }

        Graphics cg = g.create(x, y, w, h);
        try {
            c.paint(cg);
        }
        finally {
            cg.dispose();
        }

        if (wasDoubleBuffered && (c instanceof JComponent)) {
            ((JComponent)c).setDoubleBuffered(true);
        }

        c.setBounds(-w, -h, 0, 0);
    }


    /**
     * Calls this.paintComponent(g, c, p, x, y, w, h, false).
     * <p>
     *  调用this.paintComponent(g,c,p,x,y,w,h,false)。
     * 
     */
    public void paintComponent(Graphics g, Component c, Container p, int x, int y, int w, int h) {
        paintComponent(g, c, p, x, y, w, h, false);
    }


    /**
     * Calls this.paintComponent() with the rectangles x,y,width,height fields.
     * <p>
     *  使用矩形x,y,width,height字段调用this.paintComponent()。
     * 
     */
    public void paintComponent(Graphics g, Component c, Container p, Rectangle r) {
        paintComponent(g, c, p, r.x, r.y, r.width, r.height);
    }


    private void writeObject(ObjectOutputStream s) throws IOException {
        removeAll();
        s.defaultWriteObject();
    }


/////////////////
// Accessibility support
////////////////

    protected AccessibleContext accessibleContext = null;

    /**
     * Gets the AccessibleContext associated with this CellRendererPane.
     * For CellRendererPanes, the AccessibleContext takes the form of an
     * AccessibleCellRendererPane.
     * A new AccessibleCellRendererPane instance is created if necessary.
     *
     * <p>
     *  获取与此CellRendererPane关联的AccessibleContext。
     * 对于CellRendererPanes,AccessibleContext采用AccessibleCellRendererPane的形式。
     * 如果需要,将创建一个新的AccessibleCellRendererPane实例。
     * 
     * 
     * @return an AccessibleCellRendererPane that serves as the
     *         AccessibleContext of this CellRendererPane
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleCellRendererPane();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>CellRendererPane</code> class.
     * <p>
     *  此类实现<code> CellRendererPane </code>类的辅助功能支持。
     * 
     */
    protected class AccessibleCellRendererPane extends AccessibleAWTContainer {
        // AccessibleContext methods
        //
        /**
         * Get the role of this object.
         *
         * <p>
         *  获取此对象的作用。
         * 
         * @return an instance of AccessibleRole describing the role of the
         * object
         * @see AccessibleRole
         */
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.PANEL;
        }
    } // inner class AccessibleCellRendererPane
}
