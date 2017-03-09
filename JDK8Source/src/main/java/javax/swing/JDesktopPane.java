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

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.plaf.*;
import javax.accessibility.*;

import java.awt.Component;
import java.awt.Container;
import java.awt.DefaultFocusTraversalPolicy;
import java.awt.FocusTraversalPolicy;
import java.awt.Window;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.beans.PropertyVetoException;
import java.util.Set;
import java.util.TreeSet;
import java.util.LinkedHashSet;
/**
 * A container used to create a multiple-document interface or a virtual desktop.
 * You create <code>JInternalFrame</code> objects and add them to the
 * <code>JDesktopPane</code>. <code>JDesktopPane</code> extends
 * <code>JLayeredPane</code> to manage the potentially overlapping internal
 * frames. It also maintains a reference to an instance of
 * <code>DesktopManager</code> that is set by the UI
 * class for the current look and feel (L&amp;F).  Note that <code>JDesktopPane</code>
 * does not support borders.
 * <p>
 * This class is normally used as the parent of <code>JInternalFrames</code>
 * to provide a pluggable <code>DesktopManager</code> object to the
 * <code>JInternalFrames</code>. The <code>installUI</code> of the
 * L&amp;F specific implementation is responsible for setting the
 * <code>desktopManager</code> variable appropriately.
 * When the parent of a <code>JInternalFrame</code> is a <code>JDesktopPane</code>,
 * it should delegate most of its behavior to the <code>desktopManager</code>
 * (closing, resizing, etc).
 * <p>
 * For further documentation and examples see
 * <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/internalframe.html">How to Use Internal Frames</a>,
 * a section in <em>The Java Tutorial</em>.
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
 *  用于创建多文档界面或虚拟桌面的容器。您创建<code> JInternalFrame </code>对象并将它们添加到<code> JDesktopPane </code>。
 *  <code> JDesktopPane </code>扩展<code> JLayeredPane </code>以管理可能重叠的内部框架。
 * 它还维护对由UI类为当前外观(L&amp; F)设置的<code> DesktopManager </code>实例的引用。请注意,<code> JDesktopPane </code>不支持边框。
 * <p>
 *  此类通常用作<code> JInternalFrames </code>的父代,以向<code> JInternalFrames </code>提供可插入的<code> DesktopManager 
 * </code>对象。
 *  L&amp; F特定实现的<code> installUI </code>负责适当地设置<code> desktopManager </code>变量。
 * 当<code> JInternalFrame </code>的父级是<code> JDesktopPane </code>时,它应将其大部分行为委托给<code> desktopManager </code>
 * (关闭,调整大小等)。
 *  L&amp; F特定实现的<code> installUI </code>负责适当地设置<code> desktopManager </code>变量。
 * <p>
 *  有关其他文档和示例,请参阅<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/internalframe.html">
 * 如何使用内部框架</a>,<em> Java教程</em>。
 * <p>
 *  <strong>警告：</strong> Swing不是线程安全的。有关详情,请参阅<a href="package-summary.html#threading"> Swing的线程策略</a>。
 * <p>
 * <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @see JInternalFrame
 * @see JInternalFrame.JDesktopIcon
 * @see DesktopManager
 *
 * @author David Kloba
 */
public class JDesktopPane extends JLayeredPane implements Accessible
{
    /**
    /* <p>
    /* 
     * @see #getUIClassID
     * @see #readObject
     */
    private static final String uiClassID = "DesktopPaneUI";

    transient DesktopManager desktopManager;

    private transient JInternalFrame selectedFrame = null;

    /**
      * Indicates that the entire contents of the item being dragged
      * should appear inside the desktop pane.
      *
      * <p>
      *  表示正在拖动的项目的整个内容应显示在桌面窗格内。
      * 
      * 
      * @see #OUTLINE_DRAG_MODE
      * @see #setDragMode
      */
    public static final int LIVE_DRAG_MODE = 0;

    /**
      * Indicates that an outline only of the item being dragged
      * should appear inside the desktop pane.
      *
      * <p>
      *  表示仅被拖动项目的轮廓应显示在桌面窗格内。
      * 
      * 
      * @see #LIVE_DRAG_MODE
      * @see #setDragMode
      */
    public static final int OUTLINE_DRAG_MODE = 1;

    private int dragMode = LIVE_DRAG_MODE;
    private boolean dragModeSet = false;
    private transient List<JInternalFrame> framesCache;
    private boolean componentOrderCheckingEnabled = true;
    private boolean componentOrderChanged = false;

    /**
     * Creates a new <code>JDesktopPane</code>.
     * <p>
     *  创建新的<code> JDesktopPane </code>。
     * 
     */
    public JDesktopPane() {
        setUIProperty("opaque", Boolean.TRUE);
        setFocusCycleRoot(true);

        setFocusTraversalPolicy(new LayoutFocusTraversalPolicy() {
            public Component getDefaultComponent(Container c) {
                JInternalFrame jifArray[] = getAllFrames();
                Component comp = null;
                for (JInternalFrame jif : jifArray) {
                    comp = jif.getFocusTraversalPolicy().getDefaultComponent(jif);
                    if (comp != null) {
                        break;
                    }
                }
                return comp;
            }
        });
        updateUI();
    }

    /**
     * Returns the L&amp;F object that renders this component.
     *
     * <p>
     *  返回呈现此组件的L&amp; F对象。
     * 
     * 
     * @return the <code>DesktopPaneUI</code> object that
     *   renders this component
     */
    public DesktopPaneUI getUI() {
        return (DesktopPaneUI)ui;
    }

    /**
     * Sets the L&amp;F object that renders this component.
     *
     * <p>
     *  设置呈现此组件的L&amp; F对象。
     * 
     * 
     * @param ui  the DesktopPaneUI L&amp;F object
     * @see UIDefaults#getUI
     * @beaninfo
     *        bound: true
     *       hidden: true
     *    attribute: visualUpdate true
     *  description: The UI object that implements the Component's LookAndFeel.
     */
    public void setUI(DesktopPaneUI ui) {
        super.setUI(ui);
    }

    /**
     * Sets the "dragging style" used by the desktop pane.
     * You may want to change to one mode or another for
     * performance or aesthetic reasons.
     *
     * <p>
     *  设置桌面窗格使用的"拖动样式"。出于性能或美观的原因,您可能需要更改为一种模式。
     * 
     * 
     * @param dragMode the style of drag to use for items in the Desktop
     *
     * @see #LIVE_DRAG_MODE
     * @see #OUTLINE_DRAG_MODE
     *
     * @beaninfo
     *        bound: true
     *  description: Dragging style for internal frame children.
     *         enum: LIVE_DRAG_MODE JDesktopPane.LIVE_DRAG_MODE
     *               OUTLINE_DRAG_MODE JDesktopPane.OUTLINE_DRAG_MODE
     * @since 1.3
     */
    public void setDragMode(int dragMode) {
        int oldDragMode = this.dragMode;
        this.dragMode = dragMode;
        firePropertyChange("dragMode", oldDragMode, this.dragMode);
        dragModeSet = true;
     }

    /**
     * Gets the current "dragging style" used by the desktop pane.
     * <p>
     *  获取桌面窗格使用的当前"拖动样式"。
     * 
     * 
     * @return either <code>Live_DRAG_MODE</code> or
     *   <code>OUTLINE_DRAG_MODE</code>
     * @see #setDragMode
     * @since 1.3
     */
     public int getDragMode() {
         return dragMode;
     }

    /**
     * Returns the <code>DesktopManger</code> that handles
     * desktop-specific UI actions.
     * <p>
     *  返回处理桌面特定UI操作的<code> DesktopManger </code>。
     * 
     */
    public DesktopManager getDesktopManager() {
        return desktopManager;
    }

    /**
     * Sets the <code>DesktopManger</code> that will handle
     * desktop-specific UI actions. This may be overridden by
     * {@code LookAndFeel}.
     *
     * <p>
     *  设置将处理桌面特定UI操作的<code> DesktopManger </code>。这可能被{@code LookAndFeel}覆盖。
     * 
     * 
     * @param d the <code>DesktopManager</code> to use
     *
     * @beaninfo
     *        bound: true
     *  description: Desktop manager to handle the internal frames in the
     *               desktop pane.
     */
    public void setDesktopManager(DesktopManager d) {
        DesktopManager oldValue = desktopManager;
        desktopManager = d;
        firePropertyChange("desktopManager", oldValue, desktopManager);
    }

    /**
     * Notification from the <code>UIManager</code> that the L&amp;F has changed.
     * Replaces the current UI object with the latest version from the
     * <code>UIManager</code>.
     *
     * <p>
     *  来自<code> UIManager </code>的通知表示L&amp; F已更改。使用<code> UIManager </code>中的最新版本替换当前的UI对象。
     * 
     * 
     * @see JComponent#updateUI
     */
    public void updateUI() {
        setUI((DesktopPaneUI)UIManager.getUI(this));
    }


    /**
     * Returns the name of the L&amp;F class that renders this component.
     *
     * <p>
     *  返回呈现此组件的L&amp; F类的名称。
     * 
     * 
     * @return the string "DesktopPaneUI"
     * @see JComponent#getUIClassID
     * @see UIDefaults#getUI
     */
    public String getUIClassID() {
        return uiClassID;
    }

    /**
     * Returns all <code>JInternalFrames</code> currently displayed in the
     * desktop. Returns iconified frames as well as expanded frames.
     *
     * <p>
     * 返回当前显示在桌面上的所有<code> JInternalFrames </code>。返回图标化框架以及扩展框架。
     * 
     * 
     * @return an array of <code>JInternalFrame</code> objects
     */
    public JInternalFrame[] getAllFrames() {
        return getAllFrames(this).toArray(new JInternalFrame[0]);
    }

    private static Collection<JInternalFrame> getAllFrames(Container parent) {
        int i, count;
        Collection<JInternalFrame> results = new LinkedHashSet<>();
        count = parent.getComponentCount();
        for (i = 0; i < count; i++) {
            Component next = parent.getComponent(i);
            if (next instanceof JInternalFrame) {
                results.add((JInternalFrame) next);
            } else if (next instanceof JInternalFrame.JDesktopIcon) {
                JInternalFrame tmp = ((JInternalFrame.JDesktopIcon) next).getInternalFrame();
                if (tmp != null) {
                    results.add(tmp);
                }
            } else if (next instanceof Container) {
                results.addAll(getAllFrames((Container) next));
            }
        }
        return results;
    }

    /** Returns the currently active <code>JInternalFrame</code>
      * in this <code>JDesktopPane</code>, or <code>null</code>
      * if no <code>JInternalFrame</code> is currently active.
      *
      * <p>
      *  如果没有<code> JInternalFrame </code>当前处于活动状态,则在<code> JDesktopPane </code>或<code> null </code>
      * 
      * 
      * @return the currently active <code>JInternalFrame</code> or
      *   <code>null</code>
      * @since 1.3
      */

    public JInternalFrame getSelectedFrame() {
      return selectedFrame;
    }

    /** Sets the currently active <code>JInternalFrame</code>
     *  in this <code>JDesktopPane</code>. This method is used to bridge
     *  the package gap between JDesktopPane and the platform implementation
     *  code and should not be called directly. To visually select the frame
     *  the client must call JInternalFrame.setSelected(true) to activate
     *  the frame.
     * <p>
     *  在这个<code> JDesktopPane </code>中。此方法用于桥接JDesktopPane和平台实现代码之间的包装差距,不应直接调用。
     * 为了可视地选择框架,客户端必须调用JInternalFrame.setSelected(true)来激活框架。
     * 
     * 
     *  @see JInternalFrame#setSelected(boolean)
     *
     * @param f the internal frame that's currently selected
     * @since 1.3
     */

    public void setSelectedFrame(JInternalFrame f) {
      selectedFrame = f;
    }

    /**
     * Returns all <code>JInternalFrames</code> currently displayed in the
     * specified layer of the desktop. Returns iconified frames as well
     * expanded frames.
     *
     * <p>
     *  返回当前显示在桌面指定图层中的所有<code> JInternalFrames </code>。返回图标化框架以及扩展框架。
     * 
     * 
     * @param layer  an int specifying the desktop layer
     * @return an array of <code>JInternalFrame</code> objects
     * @see JLayeredPane
     */
    public JInternalFrame[] getAllFramesInLayer(int layer) {
        Collection<JInternalFrame> allFrames = getAllFrames(this);
        Iterator<JInternalFrame> iterator = allFrames.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getLayer() != layer) {
                iterator.remove();
            }
        }
        return allFrames.toArray(new JInternalFrame[0]);
    }

    private List<JInternalFrame> getFrames() {
        Component c;
        Set<ComponentPosition> set = new TreeSet<ComponentPosition>();
        for (int i = 0; i < getComponentCount(); i++) {
            c = getComponent(i);
            if (c instanceof JInternalFrame) {
                set.add(new ComponentPosition((JInternalFrame)c, getLayer(c),
                    i));
            }
            else if (c instanceof JInternalFrame.JDesktopIcon)  {
                c = ((JInternalFrame.JDesktopIcon)c).getInternalFrame();
                set.add(new ComponentPosition((JInternalFrame)c, getLayer(c),
                    i));
            }
        }
        List<JInternalFrame> frames = new ArrayList<JInternalFrame>(
                set.size());
        for (ComponentPosition position : set) {
            frames.add(position.component);
        }
        return frames;
   }

    private static class ComponentPosition implements
        Comparable<ComponentPosition> {
        private final JInternalFrame component;
        private final int layer;
        private final int zOrder;

        ComponentPosition(JInternalFrame component, int layer, int zOrder) {
            this.component = component;
            this.layer = layer;
            this.zOrder = zOrder;
        }

        public int compareTo(ComponentPosition o) {
            int delta = o.layer - layer;
            if (delta == 0) {
                return zOrder - o.zOrder;
            }
            return delta;
        }
    }

    private JInternalFrame getNextFrame(JInternalFrame f, boolean forward) {
        verifyFramesCache();
        if (f == null) {
            return getTopInternalFrame();
        }
        int i = framesCache.indexOf(f);
        if (i == -1 || framesCache.size() == 1) {
            /* error */
            return null;
        }
        if (forward) {
            // navigate to the next frame
            if (++i == framesCache.size()) {
                /* wrap */
                i = 0;
            }
        }
        else {
            // navigate to the previous frame
            if (--i == -1) {
                /* wrap */
                i = framesCache.size() - 1;
            }
        }
        return framesCache.get(i);
    }

    JInternalFrame getNextFrame(JInternalFrame f) {
        return getNextFrame(f, true);
    }

    private JInternalFrame getTopInternalFrame() {
        if (framesCache.size() == 0) {
            return null;
        }
        return framesCache.get(0);
    }

    private void updateFramesCache() {
        framesCache = getFrames();
    }

    private void verifyFramesCache() {
        // If framesCache is dirty, then recreate it.
        if (componentOrderChanged) {
            componentOrderChanged = false;
            updateFramesCache();
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    public void remove(Component comp) {
        super.remove(comp);
        updateFramesCache();
    }

    /**
     * Selects the next <code>JInternalFrame</code> in this desktop pane.
     *
     * <p>
     *  在此桌面窗格中选择下一个<code> JInternalFrame </code>。
     * 
     * 
     * @param forward a boolean indicating which direction to select in;
     *        <code>true</code> for forward, <code>false</code> for
     *        backward
     * @return the JInternalFrame that was selected or <code>null</code>
     *         if nothing was selected
     * @since 1.6
     */
    public JInternalFrame selectFrame(boolean forward) {
        JInternalFrame selectedFrame = getSelectedFrame();
        JInternalFrame frameToSelect = getNextFrame(selectedFrame, forward);
        if (frameToSelect == null) {
            return null;
        }
        // Maintain navigation traversal order until an
        // external stack change, such as a click on a frame.
        setComponentOrderCheckingEnabled(false);
        if (forward && selectedFrame != null) {
            selectedFrame.moveToBack();  // For Windows MDI fidelity.
        }
        try { frameToSelect.setSelected(true);
        } catch (PropertyVetoException pve) {}
        setComponentOrderCheckingEnabled(true);
        return frameToSelect;
    }

    /*
     * Sets whether component order checking is enabled.
     * <p>
     *  设置是否启用组件订单检查。
     * 
     * 
     * @param enable a boolean value, where <code>true</code> means
     * a change in component order will cause a change in the keyboard
     * navigation order.
     * @since 1.6
     */
    void setComponentOrderCheckingEnabled(boolean enable) {
        componentOrderCheckingEnabled = enable;
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.6
     */
    protected void addImpl(Component comp, Object constraints, int index) {
        super.addImpl(comp, constraints, index);
        if (componentOrderCheckingEnabled) {
            if (comp instanceof JInternalFrame ||
                comp instanceof JInternalFrame.JDesktopIcon) {
                componentOrderChanged = true;
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.6
     */
    public void remove(int index) {
        if (componentOrderCheckingEnabled) {
            Component comp = getComponent(index);
            if (comp instanceof JInternalFrame ||
                comp instanceof JInternalFrame.JDesktopIcon) {
                componentOrderChanged = true;
            }
        }
        super.remove(index);
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.6
     */
    public void removeAll() {
        if (componentOrderCheckingEnabled) {
            int count = getComponentCount();
            for (int i = 0; i < count; i++) {
                Component comp = getComponent(i);
                if (comp instanceof JInternalFrame ||
                    comp instanceof JInternalFrame.JDesktopIcon) {
                    componentOrderChanged = true;
                    break;
                }
            }
        }
        super.removeAll();
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.6
     */
    public void setComponentZOrder(Component comp, int index) {
        super.setComponentZOrder(comp, index);
        if (componentOrderCheckingEnabled) {
            if (comp instanceof JInternalFrame ||
                comp instanceof JInternalFrame.JDesktopIcon) {
                componentOrderChanged = true;
            }
        }
    }

    /**
     * See readObject() and writeObject() in JComponent for more
     * information about serialization in Swing.
     * <p>
     *  有关Swing中序列化的更多信息,请参阅JComponent中的readObject()和writeObject()。
     * 
     */
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        if (getUIClassID().equals(uiClassID)) {
            byte count = JComponent.getWriteObjCounter(this);
            JComponent.setWriteObjCounter(this, --count);
            if (count == 0 && ui != null) {
                ui.installUI(this);
            }
        }
    }

    void setUIProperty(String propertyName, Object value) {
        if (propertyName == "dragMode") {
            if (!dragModeSet) {
                setDragMode(((Integer)value).intValue());
                dragModeSet = false;
            }
        } else {
            super.setUIProperty(propertyName, value);
        }
    }

    /**
     * Returns a string representation of this <code>JDesktopPane</code>.
     * This method is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     *
     * <p>
     *  返回此<code> JDesktopPane </code>的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return  a string representation of this <code>JDesktopPane</code>
     */
    protected String paramString() {
        String desktopManagerString = (desktopManager != null ?
                                       desktopManager.toString() : "");

        return super.paramString() +
        ",desktopManager=" + desktopManagerString;
    }

/////////////////
// Accessibility support
////////////////

    /**
     * Gets the <code>AccessibleContext</code> associated with this
     * <code>JDesktopPane</code>. For desktop panes, the
     * <code>AccessibleContext</code> takes the form of an
     * <code>AccessibleJDesktopPane</code>.
     * A new <code>AccessibleJDesktopPane</code> instance is created if necessary.
     *
     * <p>
     * 获取与此<code> JDesktopPane </code>关联的<code> AccessibleContext </code>。
     * 对于桌面窗格,<code> AccessibleContext </code>采用<code> AccessibleJDesktopPane </code>的形式。
     * 如果需要,将创建一个新的<code> AccessibleJDesktopPane </code>实例。
     * 
     * 
     * @return an <code>AccessibleJDesktopPane</code> that serves as the
     *         <code>AccessibleContext</code> of this <code>JDesktopPane</code>
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJDesktopPane();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>JDesktopPane</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to desktop pane user-interface
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
     *  此类实现<code> JDesktopPane </code>类的辅助功能支持。它提供了适用于桌面窗格用户界面元素的Java辅助功能API的实现。
     * <p>
     *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     */
    protected class AccessibleJDesktopPane extends AccessibleJComponent {

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
            return AccessibleRole.DESKTOP_PANE;
        }
    }
}
