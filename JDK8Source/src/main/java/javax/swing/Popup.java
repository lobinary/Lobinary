/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2011, Oracle and/or its affiliates. All rights reserved.
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

import sun.awt.ModalExclude;
import sun.awt.SunToolkit;

/**
 * Popups are used to display a <code>Component</code> to the user, typically
 * on top of all the other <code>Component</code>s in a particular containment
 * hierarchy. <code>Popup</code>s have a very small life cycle. Once you
 * have obtained a <code>Popup</code>, and hidden it (invoked the
 * <code>hide</code> method), you should no longer
 * invoke any methods on it. This allows the <code>PopupFactory</code> to cache
 * <code>Popup</code>s for later use.
 * <p>
 * The general contract is that if you need to change the size of the
 * <code>Component</code>, or location of the <code>Popup</code>, you should
 * obtain a new <code>Popup</code>.
 * <p>
 * <code>Popup</code> does not descend from <code>Component</code>, rather
 * implementations of <code>Popup</code> are responsible for creating
 * and maintaining their own <code>Component</code>s to render the
 * requested <code>Component</code> to the user.
 * <p>
 * You typically do not explicitly create an instance of <code>Popup</code>,
 * instead obtain one from a <code>PopupFactory</code>.
 *
 * <p>
 *  弹出窗口用于向用户显示<code> Component </code>,通常位于特定包含层次结构中的所有其他<code> Component </code>之上。
 *  <code> Popup </code>具有非常小的生命周期。一旦你获得了一个<code> Popup </code>,并隐藏它(调用<code>隐藏</code>方法),你不应再调用任何方法。
 * 这允许<code> PopupFactory </code>缓存<code> Popup </code>以供以后使用。
 * <p>
 *  一般的合同是,如果你需要改变<code> Component </code>的大小或<code> Popup </code>的位置,你应该获得一个新的<code> Popup </code>。
 * <p>
 *  <code> Popup </code>不会从<code> Component </code>下降,而是<code> Popup </code>的实现负责创建和维护自己的<code> Componen
 * t </code>向用户呈现所请求的<code> Component </code>。
 * <p>
 *  您通常不会显式创建<code> Popup </code>的实例,而是从<code> PopupFactory </code>中获取一个实例。
 * 
 * 
 * @see PopupFactory
 *
 * @since 1.4
 */
public class Popup {
    /**
     * The Component representing the Popup.
     * <p>
     *  代表弹出窗口的组件。
     * 
     */
    private Component component;

    /**
     * Creates a <code>Popup</code> for the Component <code>owner</code>
     * containing the Component <code>contents</code>. <code>owner</code>
     * is used to determine which <code>Window</code> the new
     * <code>Popup</code> will parent the <code>Component</code> the
     * <code>Popup</code> creates to.
     * A null <code>owner</code> implies there is no valid parent.
     * <code>x</code> and
     * <code>y</code> specify the preferred initial location to place
     * the <code>Popup</code> at. Based on screen size, or other paramaters,
     * the <code>Popup</code> may not display at <code>x</code> and
     * <code>y</code>.
     *
     * <p>
     * 为包含组件<code> contents </code>的组件<code>所有者</code>创建<code> Popup </code>。
     *  <code> owner </code>用于确定<code> Window </code>新的<code> Popup </code>是<code> Component </code> >创建到。
     * 空的<code>所有者</code>意味着没有有效的父。 <code> x </code>和<code> y </code>指定放置<code> Popup </code>的首选初始位置。
     * 基于屏幕大小或其他参数,<code> Popup </code>可能不会显示在<code> x </code>和<code> y </code>。
     * 
     * 
     * @param owner    Component mouse coordinates are relative to, may be null
     * @param contents Contents of the Popup
     * @param x        Initial x screen coordinate
     * @param y        Initial y screen coordinate
     * @exception IllegalArgumentException if contents is null
     */
    protected Popup(Component owner, Component contents, int x, int y) {
        this();
        if (contents == null) {
            throw new IllegalArgumentException("Contents must be non-null");
        }
        reset(owner, contents, x, y);
    }

    /**
     * Creates a <code>Popup</code>. This is provided for subclasses.
     * <p>
     *  创建<code>弹出窗口</code>。这是为子类提供的。
     * 
     */
    protected Popup() {
    }

    /**
     * Makes the <code>Popup</code> visible. If the <code>Popup</code> is
     * currently visible, this has no effect.
     * <p>
     *  使<code> Popup </code>可见。如果<code> Popup </code>当前可见,这没有任何效果。
     * 
     */

    @SuppressWarnings("deprecation")
    public void show() {
        Component component = getComponent();

        if (component != null) {
            component.show();
        }
    }

    /**
     * Hides and disposes of the <code>Popup</code>. Once a <code>Popup</code>
     * has been disposed you should no longer invoke methods on it. A
     * <code>dispose</code>d <code>Popup</code> may be reclaimed and later used
     * based on the <code>PopupFactory</code>. As such, if you invoke methods
     * on a <code>disposed</code> <code>Popup</code>, indeterminate
     * behavior will result.
     * <p>
     *  隐藏和处置<code> Popup </code>。一旦一个<code> Popup </code>被处理,你就不能再调用它的方法。
     * 基于<code> PopupFactory </code>,可以回收并稍后使用<code> dispose </code> d <code> Popup </code>。
     * 因此,如果调用<code>处置</code> <code> Popup </code>上的方法,将导致不确定的行为。
     * 
     */

    @SuppressWarnings("deprecation")
    public void hide() {
        Component component = getComponent();

        if (component instanceof JWindow) {
            component.hide();
            ((JWindow)component).getContentPane().removeAll();
        }
        dispose();
    }

    /**
     * Frees any resources the <code>Popup</code> may be holding onto.
     * <p>
     *  释放<code> Popup </code>可能持有的任何资源。
     * 
     */
    void dispose() {
        Component component = getComponent();
        Window window = SwingUtilities.getWindowAncestor(component);

        if (component instanceof JWindow) {
            ((Window)component).dispose();
            component = null;
        }
        // If our parent is a DefaultFrame, we need to dispose it, too.
        if (window instanceof DefaultFrame) {
            window.dispose();
        }
    }

    /**
     * Resets the <code>Popup</code> to an initial state.
     * <p>
     *  将<code> Popup </code>重置为初始状态。
     * 
     */
    void reset(Component owner, Component contents, int ownerX, int ownerY) {
        if (getComponent() == null) {
            component = createComponent(owner);
        }

        Component c = getComponent();

        if (c instanceof JWindow) {
            JWindow component = (JWindow)getComponent();

            component.setLocation(ownerX, ownerY);
            component.getContentPane().add(contents, BorderLayout.CENTER);
            component.invalidate();
            component.validate();
            if(component.isVisible()) {
                // Do not call pack() if window is not visible to
                // avoid early native peer creation
                pack();
            }
        }
    }


    /**
     * Causes the <code>Popup</code> to be sized to fit the preferred size
     * of the <code>Component</code> it contains.
     * <p>
     *  导致<code> Popup </code>的大小适合其包含的<code> Component </code>的首选大小。
     * 
     */
    void pack() {
        Component component = getComponent();

        if (component instanceof Window) {
            ((Window)component).pack();
        }
    }

    /**
     * Returns the <code>Window</code> to use as the parent of the
     * <code>Window</code> created for the <code>Popup</code>. This creates
     * a new <code>DefaultFrame</code>, if necessary.
     * <p>
     * 返回<code> Window </code>以用作为<code> Popup </code>创建的<code> Window </code>的父级。
     * 如果需要,这将创建一个新的<code> DefaultFrame </code>。
     * 
     */
    private Window getParentWindow(Component owner) {
        Window window = null;

        if (owner instanceof Window) {
            window = (Window)owner;
        }
        else if (owner != null) {
            window = SwingUtilities.getWindowAncestor(owner);
        }
        if (window == null) {
            window = new DefaultFrame();
        }
        return window;
    }

    /**
     * Creates the Component to use as the parent of the <code>Popup</code>.
     * The default implementation creates a <code>Window</code>, subclasses
     * should override.
     * <p>
     *  创建用作<code> Popup </code>的父级的组件。默认实现创建一个<code> Window </code>,子类应该覆盖。
     * 
     */
    Component createComponent(Component owner) {
        if (GraphicsEnvironment.isHeadless()) {
            // Generally not useful, bail.
            return null;
        }
        return new HeavyWeightWindow(getParentWindow(owner));
    }

    /**
     * Returns the <code>Component</code> returned from
     * <code>createComponent</code> that will hold the <code>Popup</code>.
     * <p>
     *  返回从<code> createComponent </code>返回的<code> Component </code>,它将保存<code> Popup </code>。
     * 
     */
    Component getComponent() {
        return component;
    }


    /**
     * Component used to house window.
     * <p>
     *  用于容纳窗口的组件。
     * 
     */
    static class HeavyWeightWindow extends JWindow implements ModalExclude {
        HeavyWeightWindow(Window parent) {
            super(parent);
            setFocusableWindowState(false);
            setType(Window.Type.POPUP);

            // Popups are typically transient and most likely won't benefit
            // from true double buffering.  Turn it off here.
            getRootPane().setUseTrueDoubleBuffering(false);
            // Try to set "always-on-top" for the popup window.
            // Applets usually don't have sufficient permissions to do it.
            // In this case simply ignore the exception.
            try {
                setAlwaysOnTop(true);
            } catch (SecurityException se) {
                // setAlwaysOnTop is restricted,
                // the exception is ignored
            }
        }

        public void update(Graphics g) {
            paint(g);
        }

        public void show() {
            this.pack();
            if (getWidth() > 0 && getHeight() > 0) {
                super.show();
            }
        }
    }


    /**
     * Used if no valid Window ancestor of the supplied owner is found.
     * <p>
     * PopupFactory uses this as a way to know when the Popup shouldn't
     * be cached based on the Window.
     * <p>
     *  如果未找到提供的所有者的有效的窗口祖先,则使用。
     * <p>
     */
    static class DefaultFrame extends Frame {
    }
}
