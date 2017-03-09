/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2007, Oracle and/or its affiliates. All rights reserved.
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
package java.awt;

import javax.accessibility.*;

/**
 * <code>Panel</code> is the simplest container class. A panel
 * provides space in which an application can attach any other
 * component, including other panels.
 * <p>
 * The default layout manager for a panel is the
 * <code>FlowLayout</code> layout manager.
 *
 * <p>
 *  <code> Panel </code>是最简单的容器类。面板提供了应用程序可以附加任何其他组件(包括其他面板)的空间。
 * <p>
 *  面板的默认布局管理器是<code> FlowLayout </code>布局管理器。
 * 
 * 
 * @author      Sami Shaio
 * @see     java.awt.FlowLayout
 * @since   JDK1.0
 */
public class Panel extends Container implements Accessible {
    private static final String base = "panel";
    private static int nameCounter = 0;

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
     private static final long serialVersionUID = -2728009084054400034L;

    /**
     * Creates a new panel using the default layout manager.
     * The default layout manager for all panels is the
     * <code>FlowLayout</code> class.
     * <p>
     *  使用默认布局管理器创建一个新面板。所有面板的默认布局管理器是<code> FlowLayout </code>类。
     * 
     */
    public Panel() {
        this(new FlowLayout());
    }

    /**
     * Creates a new panel with the specified layout manager.
     * <p>
     *  使用指定的布局管理器创建一个新面板。
     * 
     * 
     * @param layout the layout manager for this panel.
     * @since JDK1.1
     */
    public Panel(LayoutManager layout) {
        setLayout(layout);
    }

    /**
     * Construct a name for this component.  Called by getName() when the
     * name is null.
     * <p>
     *  构造此组件的名称。当名称为null时由getName()调用。
     * 
     */
    String constructComponentName() {
        synchronized (Panel.class) {
            return base + nameCounter++;
        }
    }

    /**
     * Creates the Panel's peer.  The peer allows you to modify the
     * appearance of the panel without changing its functionality.
     * <p>
     *  创建面板的对等体。对等体允许您修改面板的外观,而不更改其功能。
     * 
     */

    public void addNotify() {
        synchronized (getTreeLock()) {
            if (peer == null)
                peer = getToolkit().createPanel(this);
            super.addNotify();
        }
    }

/////////////////
// Accessibility support
////////////////

    /**
     * Gets the AccessibleContext associated with this Panel.
     * For panels, the AccessibleContext takes the form of an
     * AccessibleAWTPanel.
     * A new AccessibleAWTPanel instance is created if necessary.
     *
     * <p>
     *  获取与此面板关联的AccessibleContext。对于面板,AccessibleContext采用AccessibleAWTPanel的形式。
     * 如有必要,将创建一个新的AccessibleAWTPanel实例。
     * 
     * 
     * @return an AccessibleAWTPanel that serves as the
     *         AccessibleContext of this Panel
     * @since 1.3
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleAWTPanel();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>Panel</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to panel user-interface elements.
     * <p>
     *  此类实现<code> Panel </code>类的辅助功能支持。它提供了适用于面板用户界面元素的Java辅助功能API的实现。
     * 
     * 
     * @since 1.3
     */
    protected class AccessibleAWTPanel extends AccessibleAWTContainer {

        private static final long serialVersionUID = -6409552226660031050L;

        /**
         * Get the role of this object.
         *
         * <p>
         *  获取此对象的作用。
         * 
         * @return an instance of AccessibleRole describing the role of the
         * object
         */
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.PANEL;
        }
    }

}
