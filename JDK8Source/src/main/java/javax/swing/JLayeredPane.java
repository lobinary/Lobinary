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

import java.awt.Component;
import java.util.ArrayList;
import java.util.Hashtable;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import sun.awt.SunToolkit;

import javax.accessibility.*;

/**
 * <code>JLayeredPane</code> adds depth to a JFC/Swing container,
 * allowing components to overlap each other when needed.
 * An <code>Integer</code> object specifies each component's depth in the
 * container, where higher-numbered components sit &quot;on top&quot; of other
 * components.
 * For task-oriented documentation and examples of using layered panes see
 * <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/layeredpane.html">How to Use a Layered Pane</a>,
 * a section in <em>The Java Tutorial</em>.
 *
 * <TABLE STYLE="FLOAT:RIGHT" BORDER="0" SUMMARY="layout">
 * <TR>
 *   <TD ALIGN="CENTER">
 *     <P STYLE="TEXT-ALIGN:CENTER"><IMG SRC="doc-files/JLayeredPane-1.gif"
 *     alt="The following text describes this image."
 *     WIDTH="269" HEIGHT="264" STYLE="FLOAT:BOTTOM; BORDER=0">
 *   </TD>
 * </TR>
 * </TABLE>
 * For convenience, <code>JLayeredPane</code> divides the depth-range
 * into several different layers. Putting a component into one of those
 * layers makes it easy to ensure that components overlap properly,
 * without having to worry about specifying numbers for specific depths:
 * <DL>
 *    <DT><FONT SIZE="2">DEFAULT_LAYER</FONT></DT>
 *         <DD>The standard layer, where most components go. This the bottommost
 *         layer.
 *    <DT><FONT SIZE="2">PALETTE_LAYER</FONT></DT>
 *         <DD>The palette layer sits over the default layer. Useful for floating
 *         toolbars and palettes, so they can be positioned above other components.
 *    <DT><FONT SIZE="2">MODAL_LAYER</FONT></DT>
 *         <DD>The layer used for modal dialogs. They will appear on top of any
 *         toolbars, palettes, or standard components in the container.
 *    <DT><FONT SIZE="2">POPUP_LAYER</FONT></DT>
 *         <DD>The popup layer displays above dialogs. That way, the popup windows
 *         associated with combo boxes, tooltips, and other help text will appear
 *         above the component, palette, or dialog that generated them.
 *    <DT><FONT SIZE="2">DRAG_LAYER</FONT></DT>
 *         <DD>When dragging a component, reassigning it to the drag layer ensures
 *         that it is positioned over every other component in the container. When
 *         finished dragging, it can be reassigned to its normal layer.
 * </DL>
 * The <code>JLayeredPane</code> methods <code>moveToFront(Component)</code>,
 * <code>moveToBack(Component)</code> and <code>setPosition</code> can be used
 * to reposition a component within its layer. The <code>setLayer</code> method
 * can also be used to change the component's current layer.
 *
 * <h2>Details</h2>
 * <code>JLayeredPane</code> manages its list of children like
 * <code>Container</code>, but allows for the definition of a several
 * layers within itself. Children in the same layer are managed exactly
 * like the normal <code>Container</code> object,
 * with the added feature that when children components overlap, children
 * in higher layers display above the children in lower layers.
 * <p>
 * Each layer is a distinct integer number. The layer attribute can be set
 * on a <code>Component</code> by passing an <code>Integer</code>
 * object during the add call.<br> For example:
 * <PRE>
 *     layeredPane.add(child, JLayeredPane.DEFAULT_LAYER);
 * or
 *     layeredPane.add(child, new Integer(10));
 * </PRE>
 * The layer attribute can also be set on a Component by calling<PRE>
 *     layeredPaneParent.setLayer(child, 10)</PRE>
 * on the <code>JLayeredPane</code> that is the parent of component. The layer
 * should be set <i>before</i> adding the child to the parent.
 * <p>
 * Higher number layers display above lower number layers. So, using
 * numbers for the layers and letters for individual components, a
 * representative list order would look like this:<PRE>
 *       5a, 5b, 5c, 2a, 2b, 2c, 1a </PRE>
 * where the leftmost components are closest to the top of the display.
 * <p>
 * A component can be moved to the top or bottom position within its
 * layer by calling <code>moveToFront</code> or <code>moveToBack</code>.
 * <p>
 * The position of a component within a layer can also be specified directly.
 * Valid positions range from 0 up to one less than the number of
 * components in that layer. A value of -1 indicates the bottommost
 * position. A value of 0 indicates the topmost position. Unlike layer
 * numbers, higher position values are <i>lower</i> in the display.
 * <blockquote>
 * <b>Note:</b> This sequence (defined by java.awt.Container) is the reverse
 * of the layer numbering sequence. Usually though, you will use <code>moveToFront</code>,
 * <code>moveToBack</code>, and <code>setLayer</code>.
 * </blockquote>
 * Here are some examples using the method add(Component, layer, position):
 * Calling add(5x, 5, -1) results in:<PRE>
 *       5a, 5b, 5c, 5x, 2a, 2b, 2c, 1a </PRE>
 *
 * Calling add(5z, 5, 2) results in:<PRE>
 *       5a, 5b, 5z, 5c, 5x, 2a, 2b, 2c, 1a </PRE>
 *
 * Calling add(3a, 3, 7) results in:<PRE>
 *       5a, 5b, 5z, 5c, 5x, 3a, 2a, 2b, 2c, 1a </PRE>
 *
 * Using normal paint/event mechanics results in 1a appearing at the bottom
 * and 5a being above all other components.
 * <p>
 * <b>Note:</b> that these layers are simply a logical construct and LayoutManagers
 * will affect all child components of this container without regard for
 * layer settings.
 * <p>
 * <strong>Warning:</strong> Swing is not thread safe. For more
 * information see <a
 * href="package-summary.html#threading">Swing's Threading
 * Policy</a>.
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
 *  <code> JLayeredPane </code>为JFC / Swing容器添加深度,允许组件在需要时相互重叠。
 *  <code> Integer </code>对象指定容器中的每个组件的深度,其中较高编号的组件位于"顶部"的其他组分。
 * 有关面向任务的文档和使用分层窗格的示例,请参见<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/layeredpane.html">
 * 如何使用分层窗格</a> ,在Java教程</em>中的一个部分。
 *  <code> Integer </code>对象指定容器中的每个组件的深度,其中较高编号的组件位于"顶部"的其他组分。
 * 
 * <TABLE STYLE="FLOAT:RIGHT" BORDER="0" SUMMARY="layout">
 * <TR>
 * <TD ALIGN="CENTER">
 *  <P STYLE ="TEXT-ALIGN：CENTER"> <IMG SRC ="doc-files / JLayeredPane-1.gif"alt ="以下文字描述此图片。
 * WIDTH="269" HEIGHT="264" STYLE="FLOAT:BOTTOM; BORDER=0">
 * </TD>
 * </TR>
 * </TABLE>
 *  为了方便起见,<code> JLayeredPane </code>将深度范围划分为几个不同的图层。将组件放入其中一个层可以很容易地确保组件正确重叠,而不必担心为特定深度指定数字：
 * <DL>
 * <DT> <FONT SIZE ="2"> DEFAULT_LAYER </FONT> </DT> <DD>标准层,大多数组件都在那里。这最底层。
 *  <DT> <FONT SIZE ="2"> PALETTE_LAYER </FONT> </DT> <DD>调色板层位于默认图层上方。用于浮动工具栏和调色板,因此它们可以位于其他组件上方。
 *  <DT> <FONT SIZE ="2"> MODAL_LAYER </FONT> </DT> <DD>用于模态对话框的图层。它们将出现在容器中的任何工具栏,调色板或标准组件的顶部。
 *  <DT> <FONT SIZE ="2"> POPUP_LAYER </FONT> </DT> <DD>弹出层显示在对话框上方。
 * 这样,与组合框,工具提示和其他帮助文本相关联的弹出窗口将显示在生成它们的组件,调色板或对话框的上方。
 *  <DT> <FONT SIZE ="2"> DRAG_LAYER </FONT> </DT> <DD>拖动组件时,将其重新分配到拖动层,确保它位于容器中的每个其他组件上。
 * 当完成拖动时,它可以重新分配到其正常层。
 * </DL>
 *  <code> JLayeredPane </code>方法<code> moveToFront(Component)</code>,<code> moveToBack(Component)</code>
 * 和<code> setPosition </code>可用于重新定位组件其层。
 *  <code> setLayer </code>方法也可用于更改组件的当前图层。
 * 
 * <h2>详细</h2> <code> JLayeredPane </code>管理像<code> Container </code>这样的子元素列表,但允许定义其中的几个层。
 * 同一层中的子代理与正常的<code> Container </code>对象一样被管理,增加的特性是当子组件重叠时,较高层中的子级显示在较低层中的子级之上。
 * <p>
 *  每个层是不同的整数。通过在添加调用期间传递<code> Integer </code>对象,可以在<code> Component </code>上设置图层属性。<br>例如：
 * <PRE>
 *  layeredPane.add(child,JLayeredPane.DEFAULT_LAYER);或layeredPane.add(child,new Integer(10));
 * </PRE>
 *  还可以通过在组件的父代码的<code> JLayeredPane </code>上调用<PRE> layeredPaneParent.setLayer(child,10)</PRE>来设置图层属性。
 * 该图层应在</i>将子项添加到父项之前设置为<i>。
 * <p>
 *  较高数字层显示在较低数字层上方。因此,使用用于各个分量的层和字母的数字,代表性列表顺序将如下：<PRE> 5a,5b,5c,2a,2b,2c,1a </PRE>其中最左边的分量最接近顶部的显示。
 * <p>
 *  通过调用<code> moveToFront </code>或<code> moveToBack </code>,可以将组件移动到其层中的顶部或底部位置。
 * <p>
 * 也可以直接指定层中的组件的位置。有效位置的范围从0到小于该层中组件数量的1。值-1表示最底部位置。值0表示最高位置。与层数不同,在显示中较高的位置值<i> </i>。
 * <blockquote>
 *  <b>注意：</b>此序列(由java.awt.Container定义)与图层编号序列相反。
 * 通常,你将使用<code> moveToFront </code>,<code> moveToBack </code>和<code> setLayer </code>。
 * </blockquote>
 *  下面是使用方法add(Component,layer,position)的一些例子：调用add(5x,5,-1)得到：<PRE> 5a,5b,5c,5x,2a,2b,2c,1a </PRE >
 * 
 *  调用add(5z,5,2)得到：5a,5b,5z,5c,5x,2a,2b,2c,1a </PRE>
 * 
 *  调用add(3a,3,7)得到：5a,5b,5z,5c,5x,3a,2a,2b,2c,1a </PRE>
 * 
 *  使用正常的油漆/事件机制导致1a出现在底部,5a超过所有其他组件。
 * <p>
 *  <b>注意：</b>这些图层只是一个逻辑构造,LayoutManagers会影响此容器的所有子组件,而不考虑图层设置。
 * <p>
 *  <strong>警告：</strong> Swing不是线程安全的。有关详情,请参阅<a href="package-summary.html#threading"> Swing的线程策略</a>。
 * <p>
 * <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author David Kloba
 */
@SuppressWarnings("serial")
public class JLayeredPane extends JComponent implements Accessible {
    /// Watch the values in getObjectForLayer()
    /** Convenience object defining the Default layer. Equivalent to new Integer(0).*/
    public final static Integer DEFAULT_LAYER = new Integer(0);
    /** Convenience object defining the Palette layer. Equivalent to new Integer(100).*/
    public final static Integer PALETTE_LAYER = new Integer(100);
    /** Convenience object defining the Modal layer. Equivalent to new Integer(200).*/
    public final static Integer MODAL_LAYER = new Integer(200);
    /** Convenience object defining the Popup layer. Equivalent to new Integer(300).*/
    public final static Integer POPUP_LAYER = new Integer(300);
    /** Convenience object defining the Drag layer. Equivalent to new Integer(400).*/
    public final static Integer DRAG_LAYER = new Integer(400);
    /** Convenience object defining the Frame Content layer.
      * This layer is normally only use to position the contentPane and menuBar
      * components of JFrame.
      * Equivalent to new Integer(-30000).
      * <p>
      *  此图层通常只用于定位JFrame的contentPane和menuBar组件。相当于新的整数(-30000)。
      * 
      * 
      * @see JFrame
      */
    public final static Integer FRAME_CONTENT_LAYER = new Integer(-30000);

    /** Bound property */
    public final static String LAYER_PROPERTY = "layeredContainerLayer";
    // Hashtable to store layer values for non-JComponent components
    private Hashtable<Component,Integer> componentToLayer;
    private boolean optimizedDrawingPossible = true;


//////////////////////////////////////////////////////////////////////////////
//// Container Override methods
//////////////////////////////////////////////////////////////////////////////
    /** Create a new JLayeredPane */
    public JLayeredPane() {
        setLayout(null);
    }

    private void validateOptimizedDrawing() {
        boolean layeredComponentFound = false;
        synchronized(getTreeLock()) {
            Integer layer;

            for (Component c : getComponents()) {
                layer = null;

                if(SunToolkit.isInstanceOf(c, "javax.swing.JInternalFrame") ||
                       (c instanceof JComponent &&
                        (layer = (Integer)((JComponent)c).
                                     getClientProperty(LAYER_PROPERTY)) != null))
                {
                    if(layer != null && layer.equals(FRAME_CONTENT_LAYER))
                        continue;
                    layeredComponentFound = true;
                    break;
                }
            }
        }

        if(layeredComponentFound)
            optimizedDrawingPossible = false;
        else
            optimizedDrawingPossible = true;
    }

    protected void addImpl(Component comp, Object constraints, int index) {
        int layer;
        int pos;

        if(constraints instanceof Integer) {
            layer = ((Integer)constraints).intValue();
            setLayer(comp, layer);
        } else
            layer = getLayer(comp);

        pos = insertIndexForLayer(layer, index);
        super.addImpl(comp, constraints, pos);
        comp.validate();
        comp.repaint();
        validateOptimizedDrawing();
    }

    /**
     * Remove the indexed component from this pane.
     * This is the absolute index, ignoring layers.
     *
     * <p>
     *  从此窗格中删除索引的组件。这是绝对指数,忽略图层。
     * 
     * 
     * @param index  an int specifying the component to remove
     * @see #getIndexOf
     */
    public void remove(int index) {
        Component c = getComponent(index);
        super.remove(index);
        if (c != null && !(c instanceof JComponent)) {
            getComponentToLayer().remove(c);
        }
        validateOptimizedDrawing();
    }

    /**
     * Removes all the components from this container.
     *
     * <p>
     *  从此容器中删除所有组件。
     * 
     * 
     * @since 1.5
     */
    public void removeAll() {
        Component[] children = getComponents();
        Hashtable<Component, Integer> cToL = getComponentToLayer();
        for (int counter = children.length - 1; counter >= 0; counter--) {
            Component c = children[counter];
            if (c != null && !(c instanceof JComponent)) {
                cToL.remove(c);
            }
        }
        super.removeAll();
    }

    /**
     * Returns false if components in the pane can overlap, which makes
     * optimized drawing impossible. Otherwise, returns true.
     *
     * <p>
     *  如果窗格中的组件可能重叠,则返回false,这使得无法进行优化绘制。否则,返回true。
     * 
     * 
     * @return false if components can overlap, else true
     * @see JComponent#isOptimizedDrawingEnabled
     */
    public boolean isOptimizedDrawingEnabled() {
        return optimizedDrawingPossible;
    }


//////////////////////////////////////////////////////////////////////////////
//// New methods for managing layers
//////////////////////////////////////////////////////////////////////////////
    /** Sets the layer property on a JComponent. This method does not cause
      * any side effects like setLayer() (painting, add/remove, etc).
      * Normally you should use the instance method setLayer(), in order to
      * get the desired side-effects (like repainting).
      *
      * <p>
      *  任何副作用像setLayer()(绘画,添加/删除等)。通常你应该使用实例方法setLayer(),以获得所需的副作用(如重绘)。
      * 
      * 
      * @param c      the JComponent to move
      * @param layer  an int specifying the layer to move it to
      * @see #setLayer
      */
    public static void putLayer(JComponent c, int layer) {
        /// MAKE SURE THIS AND setLayer(Component c, int layer, int position)  are SYNCED
        Integer layerObj;

        layerObj = new Integer(layer);
        c.putClientProperty(LAYER_PROPERTY, layerObj);
    }

    /** Gets the layer property for a JComponent, it
      * does not cause any side effects like setLayer(). (painting, add/remove, etc)
      * Normally you should use the instance method getLayer().
      *
      * <p>
      *  不会导致任何副作用像setLayer()。 (绘画,添加/删除等)通常你应该使用实例方法getLayer()。
      * 
      * 
      * @param c  the JComponent to check
      * @return   an int specifying the component's layer
      */
    public static int getLayer(JComponent c) {
        Integer i;
        if((i = (Integer)c.getClientProperty(LAYER_PROPERTY)) != null)
            return i.intValue();
        return DEFAULT_LAYER.intValue();
    }

    /** Convenience method that returns the first JLayeredPane which
      * contains the specified component. Note that all JFrames have a
      * JLayeredPane at their root, so any component in a JFrame will
      * have a JLayeredPane parent.
      *
      * <p>
      *  包含指定的组件。请注意,所有JFrames在其根目录下都有一个JLayeredPane,因此JFrame中的任何组件都将有一个JLayeredPane父级。
      * 
      * 
      * @param c the Component to check
      * @return the JLayeredPane that contains the component, or
      *         null if no JLayeredPane is found in the component
      *         hierarchy
      * @see JFrame
      * @see JRootPane
      */
    public static JLayeredPane getLayeredPaneAbove(Component c) {
        if(c == null) return null;

        Component parent = c.getParent();
        while(parent != null && !(parent instanceof JLayeredPane))
            parent = parent.getParent();
        return (JLayeredPane)parent;
    }

    /** Sets the layer attribute on the specified component,
      * making it the bottommost component in that layer.
      * Should be called before adding to parent.
      *
      * <p>
      *  使其成为该层中最底部的部件。应在添加到父代之前调用。
      * 
      * 
      * @param c     the Component to set the layer for
      * @param layer an int specifying the layer to set, where
      *              lower numbers are closer to the bottom
      */
    public void setLayer(Component c, int layer)  {
        setLayer(c, layer, -1);
    }

    /** Sets the layer attribute for the specified component and
      * also sets its position within that layer.
      *
      * <p>
      *  也设置其在该层中的位置。
      * 
      * 
      * @param c         the Component to set the layer for
      * @param layer     an int specifying the layer to set, where
      *                  lower numbers are closer to the bottom
      * @param position  an int specifying the position within the
      *                  layer, where 0 is the topmost position and -1
      *                  is the bottommost position
      */
    public void setLayer(Component c, int layer, int position)  {
        Integer layerObj;
        layerObj = getObjectForLayer(layer);

        if(layer == getLayer(c) && position == getPosition(c)) {
                repaint(c.getBounds());
            return;
        }

        /// MAKE SURE THIS AND putLayer(JComponent c, int layer) are SYNCED
        if(c instanceof JComponent)
            ((JComponent)c).putClientProperty(LAYER_PROPERTY, layerObj);
        else
            getComponentToLayer().put(c, layerObj);

        if(c.getParent() == null || c.getParent() != this) {
            repaint(c.getBounds());
            return;
        }

        int index = insertIndexForLayer(c, layer, position);

        setComponentZOrder(c, index);
        repaint(c.getBounds());
    }

    /**
     * Returns the layer attribute for the specified Component.
     *
     * <p>
     *  返回指定组件的图层属性。
     * 
     * 
     * @param c  the Component to check
     * @return an int specifying the component's current layer
     */
    public int getLayer(Component c) {
        Integer i;
        if(c instanceof JComponent)
            i = (Integer)((JComponent)c).getClientProperty(LAYER_PROPERTY);
        else
            i = getComponentToLayer().get(c);

        if(i == null)
            return DEFAULT_LAYER.intValue();
        return i.intValue();
    }

    /**
     * Returns the index of the specified Component.
     * This is the absolute index, ignoring layers.
     * Index numbers, like position numbers, have the topmost component
     * at index zero. Larger numbers are closer to the bottom.
     *
     * <p>
     * 返回指定组件的索引。这是绝对指数,忽略图层。索引号(如位置编号)在索引为零时具有最高的分量。较大的数字更接近底部。
     * 
     * 
     * @param c  the Component to check
     * @return an int specifying the component's index
     */
    public int getIndexOf(Component c) {
        int i, count;

        count = getComponentCount();
        for(i = 0; i < count; i++) {
            if(c == getComponent(i))
                return i;
        }
        return -1;
    }
    /**
     * Moves the component to the top of the components in its current layer
     * (position 0).
     *
     * <p>
     *  将组件移动到当前图层(位置0)中组件的顶部。
     * 
     * 
     * @param c the Component to move
     * @see #setPosition(Component, int)
     */
    public void moveToFront(Component c) {
        setPosition(c, 0);
    }

    /**
     * Moves the component to the bottom of the components in its current layer
     * (position -1).
     *
     * <p>
     *  将组件移动到当前图层中的组件底部(位置-1)。
     * 
     * 
     * @param c the Component to move
     * @see #setPosition(Component, int)
     */
    public void moveToBack(Component c) {
        setPosition(c, -1);
    }

    /**
     * Moves the component to <code>position</code> within its current layer,
     * where 0 is the topmost position within the layer and -1 is the bottommost
     * position.
     * <p>
     * <b>Note:</b> Position numbering is defined by java.awt.Container, and
     * is the opposite of layer numbering. Lower position numbers are closer
     * to the top (0 is topmost), and higher position numbers are closer to
     * the bottom.
     *
     * <p>
     *  将组件移动到当前图层中的<code> position </code>,其中0是图层中最顶层的位置,-1是最底层的位置。
     * <p>
     *  <b>注意：</b>位置编号由java.awt.Container定义,与层编号相反。下面的位置数字更靠近顶部(0是最上面的),更高的位置数字更接近底部。
     * 
     * 
     * @param c         the Component to move
     * @param position  an int in the range -1..N-1, where N is the number of
     *                  components in the component's current layer
     */
    public void setPosition(Component c, int position) {
        setLayer(c, getLayer(c), position);
    }

    /**
     * Get the relative position of the component within its layer.
     *
     * <p>
     *  获取组件在其图层中的相对位置。
     * 
     * 
     * @param c  the Component to check
     * @return an int giving the component's position, where 0 is the
     *         topmost position and the highest index value = the count
     *         count of components at that layer, minus 1
     *
     * @see #getComponentCountInLayer
     */
    public int getPosition(Component c) {
        int i, startLayer, curLayer, startLocation, pos = 0;

        getComponentCount();
        startLocation = getIndexOf(c);

        if(startLocation == -1)
            return -1;

        startLayer = getLayer(c);
        for(i = startLocation - 1; i >= 0; i--) {
            curLayer = getLayer(getComponent(i));
            if(curLayer == startLayer)
                pos++;
            else
                return pos;
        }
        return pos;
    }

    /** Returns the highest layer value from all current children.
      * Returns 0 if there are no children.
      *
      * <p>
      *  如果没有子项,则返回0。
      * 
      * 
      * @return an int indicating the layer of the topmost component in the
      *         pane, or zero if there are no children
      */
    public int highestLayer() {
        if(getComponentCount() > 0)
            return getLayer(getComponent(0));
        return 0;
    }

    /** Returns the lowest layer value from all current children.
      * Returns 0 if there are no children.
      *
      * <p>
      *  如果没有子项,则返回0。
      * 
      * 
      * @return an int indicating the layer of the bottommost component in the
      *         pane, or zero if there are no children
      */
    public int lowestLayer() {
        int count = getComponentCount();
        if(count > 0)
            return getLayer(getComponent(count-1));
        return 0;
    }

    /**
     * Returns the number of children currently in the specified layer.
     *
     * <p>
     *  返回当前在指定图层中的子项数。
     * 
     * 
     * @param layer  an int specifying the layer to check
     * @return an int specifying the number of components in that layer
     */
    public int getComponentCountInLayer(int layer) {
        int i, count, curLayer;
        int layerCount = 0;

        count = getComponentCount();
        for(i = 0; i < count; i++) {
            curLayer = getLayer(getComponent(i));
            if(curLayer == layer) {
                layerCount++;
            /// Short circut the counting when we have them all
            } else if(layerCount > 0 || curLayer < layer) {
                break;
            }
        }

        return layerCount;
    }

    /**
     * Returns an array of the components in the specified layer.
     *
     * <p>
     *  返回指定图层中的组件数组。
     * 
     * 
     * @param layer  an int specifying the layer to check
     * @return an array of Components contained in that layer
     */
    public Component[] getComponentsInLayer(int layer) {
        int i, count, curLayer;
        int layerCount = 0;
        Component[] results;

        results = new Component[getComponentCountInLayer(layer)];
        count = getComponentCount();
        for(i = 0; i < count; i++) {
            curLayer = getLayer(getComponent(i));
            if(curLayer == layer) {
                results[layerCount++] = getComponent(i);
            /// Short circut the counting when we have them all
            } else if(layerCount > 0 || curLayer < layer) {
                break;
            }
        }

        return results;
    }

    /**
     * Paints this JLayeredPane within the specified graphics context.
     *
     * <p>
     *  在指定的图形上下文中绘制此JLayeredPane。
     * 
     * 
     * @param g  the Graphics context within which to paint
     */
    public void paint(Graphics g) {
        if(isOpaque()) {
            Rectangle r = g.getClipBounds();
            Color c = getBackground();
            if(c == null)
                c = Color.lightGray;
            g.setColor(c);
            if (r != null) {
                g.fillRect(r.x, r.y, r.width, r.height);
            }
            else {
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        }
        super.paint(g);
    }

//////////////////////////////////////////////////////////////////////////////
//// Implementation Details
//////////////////////////////////////////////////////////////////////////////

    /**
     * Returns the hashtable that maps components to layers.
     *
     * <p>
     *  返回将组件映射到图层的散列表。
     * 
     * 
     * @return the Hashtable used to map components to their layers
     */
    protected Hashtable<Component,Integer> getComponentToLayer() {
        if(componentToLayer == null)
            componentToLayer = new Hashtable<Component,Integer>(4);
        return componentToLayer;
    }

    /**
     * Returns the Integer object associated with a specified layer.
     *
     * <p>
     *  返回与指定图层关联的整数对象。
     * 
     * 
     * @param layer an int specifying the layer
     * @return an Integer object for that layer
     */
    protected Integer getObjectForLayer(int layer) {
        Integer layerObj;
        switch(layer) {
        case 0:
            layerObj = DEFAULT_LAYER;
            break;
        case 100:
            layerObj = PALETTE_LAYER;
            break;
        case 200:
            layerObj = MODAL_LAYER;
            break;
        case 300:
            layerObj = POPUP_LAYER;
            break;
        case 400:
            layerObj = DRAG_LAYER;
            break;
        default:
            layerObj = new Integer(layer);
        }
        return layerObj;
    }

    /**
     * Primitive method that determines the proper location to
     * insert a new child based on layer and position requests.
     *
     * <p>
     *  基于层和位置请求确定插入新子节点的合适位置的基本方法。
     * 
     * 
     * @param layer     an int specifying the layer
     * @param position  an int specifying the position within the layer
     * @return an int giving the (absolute) insertion-index
     *
     * @see #getIndexOf
     */
    protected int insertIndexForLayer(int layer, int position) {
        return insertIndexForLayer(null, layer, position);
    }

    /**
     * This method is an extended version of insertIndexForLayer()
     * to support setLayer which uses Container.setZOrder which does
     * not remove the component from the containment hierarchy though
     * we need to ignore it when calculating the insertion index.
     *
     * <p>
     * 此方法是insertIndexForLayer()的扩展版本以支持setLayer,它使用Container.setZOrder,它不会从包含层次结构中删除组件,但是在计算插入索引时我们需要忽略它。
     * 
     * 
     * @param comp      component to ignore when determining index
     * @param layer     an int specifying the layer
     * @param position  an int specifying the position within the layer
     * @return an int giving the (absolute) insertion-index
     *
     * @see #getIndexOf
     */
    private int insertIndexForLayer(Component comp, int layer, int position) {
        int i, count, curLayer;
        int layerStart = -1;
        int layerEnd = -1;
        int componentCount = getComponentCount();

        ArrayList<Component> compList =
            new ArrayList<Component>(componentCount);
        for (int index = 0; index < componentCount; index++) {
            if (getComponent(index) != comp) {
                compList.add(getComponent(index));
            }
        }

        count = compList.size();
        for (i = 0; i < count; i++) {
            curLayer = getLayer(compList.get(i));
            if (layerStart == -1 && curLayer == layer) {
                layerStart = i;
            }
            if (curLayer < layer) {
                if (i == 0) {
                    // layer is greater than any current layer
                    // [ ASSERT(layer > highestLayer()) ]
                    layerStart = 0;
                    layerEnd = 0;
                } else {
                    layerEnd = i;
                }
                break;
            }
        }

        // layer requested is lower than any current layer
        // [ ASSERT(layer < lowestLayer()) ]
        // put it on the bottom of the stack
        if (layerStart == -1 && layerEnd == -1)
            return count;

        // In the case of a single layer entry handle the degenerative cases
        if (layerStart != -1 && layerEnd == -1)
            layerEnd = count;

        if (layerEnd != -1 && layerStart == -1)
            layerStart = layerEnd;

        // If we are adding to the bottom, return the last element
        if (position == -1)
            return layerEnd;

        // Otherwise make sure the requested position falls in the
        // proper range
        if (position > -1 && layerStart + position <= layerEnd)
            return layerStart + position;

        // Otherwise return the end of the layer
        return layerEnd;
    }

    /**
     * Returns a string representation of this JLayeredPane. This method
     * is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     *
     * <p>
     *  返回此JLayeredPane的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return  a string representation of this JLayeredPane.
     */
    protected String paramString() {
        String optimizedDrawingPossibleString = (optimizedDrawingPossible ?
                                                 "true" : "false");

        return super.paramString() +
        ",optimizedDrawingPossible=" + optimizedDrawingPossibleString;
    }

/////////////////
// Accessibility support
////////////////

    /**
     * Gets the AccessibleContext associated with this JLayeredPane.
     * For layered panes, the AccessibleContext takes the form of an
     * AccessibleJLayeredPane.
     * A new AccessibleJLayeredPane instance is created if necessary.
     *
     * <p>
     *  获取与此JLayeredPane关联的AccessibleContext。对于分层窗格,AccessibleContext采用AccessibleJLayeredPane的形式。
     * 如果需要,将创建一个新的AccessibleJLayeredPane实例。
     * 
     * 
     * @return an AccessibleJLayeredPane that serves as the
     *         AccessibleContext of this JLayeredPane
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJLayeredPane();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>JLayeredPane</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to layered pane user-interface
     * elements.
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
     *  此类实现了对<code> JLayeredPane </code>类的辅助功能支持。它提供了适用于分层窗格用户界面元素的Java辅助功能API的实现。
     * <p>
     *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     */
    @SuppressWarnings("serial")
    protected class AccessibleJLayeredPane extends AccessibleJComponent {

        /**
         * Get the role of this object.
         *
         * <p>
         * 
         * 
         * @return an instance of AccessibleRole describing the role of the
         * object
         * @see AccessibleRole
         */
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.LAYERED_PANE;
        }
    }
}
