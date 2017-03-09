/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.peer.LightweightPeer;
import java.awt.peer.ScrollPanePeer;
import java.awt.event.*;
import javax.accessibility.*;
import sun.awt.ScrollPaneWheelScroller;
import sun.awt.SunToolkit;

import java.beans.ConstructorProperties;
import java.beans.Transient;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

/**
 * A container class which implements automatic horizontal and/or
 * vertical scrolling for a single child component.  The display
 * policy for the scrollbars can be set to:
 * <OL>
 * <LI>as needed: scrollbars created and shown only when needed by scrollpane
 * <LI>always: scrollbars created and always shown by the scrollpane
 * <LI>never: scrollbars never created or shown by the scrollpane
 * </OL>
 * <P>
 * The state of the horizontal and vertical scrollbars is represented
 * by two <code>ScrollPaneAdjustable</code> objects (one for each
 * dimension) which implement the <code>Adjustable</code> interface.
 * The API provides methods to access those objects such that the
 * attributes on the Adjustable object (such as unitIncrement, value,
 * etc.) can be manipulated.
 * <P>
 * Certain adjustable properties (minimum, maximum, blockIncrement,
 * and visibleAmount) are set internally by the scrollpane in accordance
 * with the geometry of the scrollpane and its child and these should
 * not be set by programs using the scrollpane.
 * <P>
 * If the scrollbar display policy is defined as "never", then the
 * scrollpane can still be programmatically scrolled using the
 * setScrollPosition() method and the scrollpane will move and clip
 * the child's contents appropriately.  This policy is useful if the
 * program needs to create and manage its own adjustable controls.
 * <P>
 * The placement of the scrollbars is controlled by platform-specific
 * properties set by the user outside of the program.
 * <P>
 * The initial size of this container is set to 100x100, but can
 * be reset using setSize().
 * <P>
 * Scrolling with the wheel on a wheel-equipped mouse is enabled by default.
 * This can be disabled using <code>setWheelScrollingEnabled</code>.
 * Wheel scrolling can be customized by setting the block and
 * unit increment of the horizontal and vertical Adjustables.
 * For information on how mouse wheel events are dispatched, see
 * the class description for {@link MouseWheelEvent}.
 * <P>
 * Insets are used to define any space used by scrollbars and any
 * borders created by the scroll pane. getInsets() can be used
 * to get the current value for the insets.  If the value of
 * scrollbarsAlwaysVisible is false, then the value of the insets
 * will change dynamically depending on whether the scrollbars are
 * currently visible or not.
 *
 * <p>
 *  对单个子组件实现自动水平和/或垂直滚动​​的容器类。滚动条的显示策略可以设置为：
 * <OL>
 *  <LI>根据需要：滚动条仅在需要时通过滚动条创建和显示<LI> always：滚动条创建并始终由滚动条显示<LI>从不：滚动条从未创建或显示的滚动条
 * </OL>
 * <P>
 *  水平和垂直滚动条的状态由两个实现<code> Adjustable </code>接口的<code> ScrollPaneAdjustable </code>对象(每个维度一个)表示。
 *  API提供了访问这些对象的方法,以便可以处理Adjustable对象上的属性(例如unitIncrement,value等)。
 * <P>
 *  某些可调属性(minimum,maximum,blockIncrement和visibleAmount)由滚动条根据滚动条及其子项的几何形状在内部设置,这些不应该由使用滚动条的程序设置。
 * <P>
 *  如果滚动条显示策略被定义为"从不",则滚动条仍然可以使用setScrollPosition()方法以编程方式滚动,滚动条将适当地移动和剪切孩子的内容。
 * 如果程序需要创建和管理自己的可调节控件,则此策略很有用。
 * <P>
 * 滚动条的放置由用户在程序外部设置的平台特定属性控制。
 * <P>
 *  此容器的初始大小设置为100x100,但可以使用setSize()重置。
 * <P>
 *  默认情况下,启用带滚轮的鼠标滚轮。这可以使用<code> setWheelScrollingEnabled </code>禁用。可以通过设置水平和垂直可调节的块和单位增量来定制滚轮滚动。
 * 有关如何分派鼠标滚轮事件的信息,请参阅{@link MouseWheelEvent}的类描述。
 * <P>
 *  插图用于定义滚动条和滚动窗格创建的任何边框所使用的任何空间。 getInsets()可以用于获取插入的当前值。
 * 如果scrollbarsAlwaysVisible的值为false,那么insets的值将根据滚动条当前是否可见而动态地改变。
 * 
 * 
 * @author      Tom Ball
 * @author      Amy Fowler
 * @author      Tim Prinzing
 */
public class ScrollPane extends Container implements Accessible {


    /**
     * Initialize JNI field and method IDs
     * <p>
     *  初始化JNI字段和方法ID
     * 
     */
    private static native void initIDs();

    static {
        /* ensure that the necessary native libraries are loaded */
        Toolkit.loadLibraries();
        if (!GraphicsEnvironment.isHeadless()) {
            initIDs();
        }
    }

    /**
     * Specifies that horizontal/vertical scrollbar should be shown
     * only when the size of the child exceeds the size of the scrollpane
     * in the horizontal/vertical dimension.
     * <p>
     *  指定只有当子项的大小超过水平/垂直维度中滚动窗格的大小时才显示水平/垂直滚动条。
     * 
     */
    public static final int SCROLLBARS_AS_NEEDED = 0;

    /**
     * Specifies that horizontal/vertical scrollbars should always be
     * shown regardless of the respective sizes of the scrollpane and child.
     * <p>
     *  指定水平/垂直滚动条应始终显示,而不考虑滚动条和子项的相应大小。
     * 
     */
    public static final int SCROLLBARS_ALWAYS = 1;

    /**
     * Specifies that horizontal/vertical scrollbars should never be shown
     * regardless of the respective sizes of the scrollpane and child.
     * <p>
     *  指定不管滚动条和子项的相应大小,都不应显示水平/垂直滚动条。
     * 
     */
    public static final int SCROLLBARS_NEVER = 2;

    /**
     * There are 3 ways in which a scroll bar can be displayed.
     * This integer will represent one of these 3 displays -
     * (SCROLLBARS_ALWAYS, SCROLLBARS_AS_NEEDED, SCROLLBARS_NEVER)
     *
     * <p>
     * 有3种方式可以显示滚动条。此整数将表示这3个显示中的一个 - (SCROLLBARS_ALWAYS,SCROLLBARS_AS_NEEDED,SCROLLBARS_NEVER)
     * 
     * 
     * @serial
     * @see #getScrollbarDisplayPolicy
     */
    private int scrollbarDisplayPolicy;

    /**
     * An adjustable vertical scrollbar.
     * It is important to note that you must <em>NOT</em> call 3
     * <code>Adjustable</code> methods, namely:
     * <code>setMinimum()</code>, <code>setMaximum()</code>,
     * <code>setVisibleAmount()</code>.
     *
     * <p>
     *  可调整的垂直滚动条。
     * 请务必注意,您必须<em>不</em>调用3 <code>可调整</code>方法,即：<code> setMinimum()</code>,<code> setMaximum >,<code> set
     * VisibleAmount()</code>。
     *  可调整的垂直滚动条。
     * 
     * 
     * @serial
     * @see #getVAdjustable
     */
    private ScrollPaneAdjustable vAdjustable;

    /**
     * An adjustable horizontal scrollbar.
     * It is important to note that you must <em>NOT</em> call 3
     * <code>Adjustable</code> methods, namely:
     * <code>setMinimum()</code>, <code>setMaximum()</code>,
     * <code>setVisibleAmount()</code>.
     *
     * <p>
     *  可调水平滚动条。
     * 请务必注意,您必须<em>不</em>调用3 <code>可调整</code>方法,即：<code> setMinimum()</code>,<code> setMaximum >,<code> set
     * VisibleAmount()</code>。
     *  可调水平滚动条。
     * 
     * 
     * @serial
     * @see #getHAdjustable
     */
    private ScrollPaneAdjustable hAdjustable;

    private static final String base = "scrollpane";
    private static int nameCounter = 0;

    private static final boolean defaultWheelScroll = true;

    /**
     * Indicates whether or not scrolling should take place when a
     * MouseWheelEvent is received.
     *
     * <p>
     *  指示是否在接收到MouseWheelEvent时进行滚动。
     * 
     * 
     * @serial
     * @since 1.4
     */
    private boolean wheelScrollingEnabled = defaultWheelScroll;

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
    private static final long serialVersionUID = 7956609840827222915L;

    /**
     * Create a new scrollpane container with a scrollbar display
     * policy of "as needed".
     * <p>
     *  创建一个新的滚动条容器,滚动条显示策略为"根据需要"。
     * 
     * 
     * @throws HeadlessException if GraphicsEnvironment.isHeadless()
     *     returns true
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public ScrollPane() throws HeadlessException {
        this(SCROLLBARS_AS_NEEDED);
    }

    /**
     * Create a new scrollpane container.
     * <p>
     *  创建一个新的scrollpane容器。
     * 
     * 
     * @param scrollbarDisplayPolicy policy for when scrollbars should be shown
     * @throws IllegalArgumentException if the specified scrollbar
     *     display policy is invalid
     * @throws HeadlessException if GraphicsEnvironment.isHeadless()
     *     returns true
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    @ConstructorProperties({"scrollbarDisplayPolicy"})
    public ScrollPane(int scrollbarDisplayPolicy) throws HeadlessException {
        GraphicsEnvironment.checkHeadless();
        this.layoutMgr = null;
        this.width = 100;
        this.height = 100;
        switch (scrollbarDisplayPolicy) {
            case SCROLLBARS_NEVER:
            case SCROLLBARS_AS_NEEDED:
            case SCROLLBARS_ALWAYS:
                this.scrollbarDisplayPolicy = scrollbarDisplayPolicy;
                break;
            default:
                throw new IllegalArgumentException("illegal scrollbar display policy");
        }

        vAdjustable = new ScrollPaneAdjustable(this, new PeerFixer(this),
                                               Adjustable.VERTICAL);
        hAdjustable = new ScrollPaneAdjustable(this, new PeerFixer(this),
                                               Adjustable.HORIZONTAL);
        setWheelScrollingEnabled(defaultWheelScroll);
    }

    /**
     * Construct a name for this component.  Called by getName() when the
     * name is null.
     * <p>
     *  构造此组件的名称。当名称为null时由getName()调用。
     * 
     */
    String constructComponentName() {
        synchronized (ScrollPane.class) {
            return base + nameCounter++;
        }
    }

    // The scrollpane won't work with a windowless child... it assumes
    // it is moving a child window around so the windowless child is
    // wrapped with a window.
    private void addToPanel(Component comp, Object constraints, int index) {
        Panel child = new Panel();
        child.setLayout(new BorderLayout());
        child.add(comp);
        super.addImpl(child, constraints, index);
        validate();
    }

    /**
     * Adds the specified component to this scroll pane container.
     * If the scroll pane has an existing child component, that
     * component is removed and the new one is added.
     * <p>
     *  将指定的组件添加到此滚动窗格容器。如果滚动窗格具有现有子组件,则删除该组件并添加新组件。
     * 
     * 
     * @param comp the component to be added
     * @param constraints  not applicable
     * @param index position of child component (must be &lt;= 0)
     */
    protected final void addImpl(Component comp, Object constraints, int index) {
        synchronized (getTreeLock()) {
            if (getComponentCount() > 0) {
                remove(0);
            }
            if (index > 0) {
                throw new IllegalArgumentException("position greater than 0");
            }

            if (!SunToolkit.isLightweightOrUnknown(comp)) {
                super.addImpl(comp, constraints, index);
            } else {
                addToPanel(comp, constraints, index);
            }
        }
    }

    /**
     * Returns the display policy for the scrollbars.
     * <p>
     *  返回滚动条的显示策略。
     * 
     * 
     * @return the display policy for the scrollbars
     */
    public int getScrollbarDisplayPolicy() {
        return scrollbarDisplayPolicy;
    }

    /**
     * Returns the current size of the scroll pane's view port.
     * <p>
     *  返回滚动窗格的视图端口的当前大小。
     * 
     * 
     * @return the size of the view port in pixels
     */
    public Dimension getViewportSize() {
        Insets i = getInsets();
        return new Dimension(width - i.right - i.left,
                             height - i.top - i.bottom);
    }

    /**
     * Returns the height that would be occupied by a horizontal
     * scrollbar, which is independent of whether it is currently
     * displayed by the scroll pane or not.
     * <p>
     *  返回将由水平滚动条占用的高度,这与其是否当前由滚动窗格显示无关。
     * 
     * 
     * @return the height of a horizontal scrollbar in pixels
     */
    public int getHScrollbarHeight() {
        int h = 0;
        if (scrollbarDisplayPolicy != SCROLLBARS_NEVER) {
            ScrollPanePeer peer = (ScrollPanePeer)this.peer;
            if (peer != null) {
                h = peer.getHScrollbarHeight();
            }
        }
        return h;
    }

    /**
     * Returns the width that would be occupied by a vertical
     * scrollbar, which is independent of whether it is currently
     * displayed by the scroll pane or not.
     * <p>
     * 返回垂直滚动条所占用的宽度,这与当前是否由滚动窗口显示无关。
     * 
     * 
     * @return the width of a vertical scrollbar in pixels
     */
    public int getVScrollbarWidth() {
        int w = 0;
        if (scrollbarDisplayPolicy != SCROLLBARS_NEVER) {
            ScrollPanePeer peer = (ScrollPanePeer)this.peer;
            if (peer != null) {
                w = peer.getVScrollbarWidth();
            }
        }
        return w;
    }

    /**
     * Returns the <code>ScrollPaneAdjustable</code> object which
     * represents the state of the vertical scrollbar.
     * The declared return type of this method is
     * <code>Adjustable</code> to maintain backward compatibility.
     * <p>
     *  返回代表垂直滚动条状态的<code> ScrollPaneAdjustable </code>对象。此方法的声明返回类型为<code> Adjustable </code>以保持向后兼容性。
     * 
     * 
     * @see java.awt.ScrollPaneAdjustable
     */
    public Adjustable getVAdjustable() {
        return vAdjustable;
    }

    /**
     * Returns the <code>ScrollPaneAdjustable</code> object which
     * represents the state of the horizontal scrollbar.
     * The declared return type of this method is
     * <code>Adjustable</code> to maintain backward compatibility.
     * <p>
     *  返回代表水平滚动条状态的<code> ScrollPaneAdjustable </code>对象。此方法的声明返回类型为<code> Adjustable </code>以保持向后兼容性。
     * 
     * 
     * @see java.awt.ScrollPaneAdjustable
     */
    public Adjustable getHAdjustable() {
        return hAdjustable;
    }

    /**
     * Scrolls to the specified position within the child component.
     * A call to this method is only valid if the scroll pane contains
     * a child.  Specifying a position outside of the legal scrolling bounds
     * of the child will scroll to the closest legal position.
     * Legal bounds are defined to be the rectangle:
     * x = 0, y = 0, width = (child width - view port width),
     * height = (child height - view port height).
     * This is a convenience method which interfaces with the Adjustable
     * objects which represent the state of the scrollbars.
     * <p>
     *  滚动到子组件中的指定位置。只有滚动窗格包含子对象时,对此方法的调用才有效。指定儿童的法律滚动边界之外的位置将滚动到最接近的法律位置。
     * 法律边界定义为矩形：x = 0,y = 0,width =(子宽度 - 视图端口宽度),height =(子高度 - 视图端口高度)。这是一个方便的方法,它与代表滚动条状态的可调对象接口。
     * 
     * 
     * @param x the x position to scroll to
     * @param y the y position to scroll to
     * @throws NullPointerException if the scrollpane does not contain
     *     a child
     */
    public void setScrollPosition(int x, int y) {
        synchronized (getTreeLock()) {
            if (getComponentCount()==0) {
                throw new NullPointerException("child is null");
            }
            hAdjustable.setValue(x);
            vAdjustable.setValue(y);
        }
    }

    /**
     * Scrolls to the specified position within the child component.
     * A call to this method is only valid if the scroll pane contains
     * a child and the specified position is within legal scrolling bounds
     * of the child.  Specifying a position outside of the legal scrolling
     * bounds of the child will scroll to the closest legal position.
     * Legal bounds are defined to be the rectangle:
     * x = 0, y = 0, width = (child width - view port width),
     * height = (child height - view port height).
     * This is a convenience method which interfaces with the Adjustable
     * objects which represent the state of the scrollbars.
     * <p>
     * 滚动到子组件中的指定位置。对此方法的调用仅在滚动窗格包含子项且指定的位置在子项的合法滚动边界内时有效。指定儿童的法律滚动边界之外的位置将滚动到最接近的法律位置。
     * 法律边界定义为矩形：x = 0,y = 0,width =(子宽度 - 视图端口宽度),height =(子高度 - 视图端口高度)。这是一个方便的方法,它与代表滚动条状态的可调对象接口。
     * 
     * 
     * @param p the Point representing the position to scroll to
     * @throws NullPointerException if {@code p} is {@code null}
     */
    public void setScrollPosition(Point p) {
        setScrollPosition(p.x, p.y);
    }

    /**
     * Returns the current x,y position within the child which is displayed
     * at the 0,0 location of the scrolled panel's view port.
     * This is a convenience method which interfaces with the adjustable
     * objects which represent the state of the scrollbars.
     * <p>
     *  返回显示在滚动面板视图端口的0,0位置的子对象中当前的x,y位置。这是一个方便的方法,它与代表滚动条状态的可调节对象接口。
     * 
     * 
     * @return the coordinate position for the current scroll position
     * @throws NullPointerException if the scrollpane does not contain
     *     a child
     */
    @Transient
    public Point getScrollPosition() {
        synchronized (getTreeLock()) {
            if (getComponentCount()==0) {
                throw new NullPointerException("child is null");
            }
            return new Point(hAdjustable.getValue(), vAdjustable.getValue());
        }
    }

    /**
     * Sets the layout manager for this container.  This method is
     * overridden to prevent the layout mgr from being set.
     * <p>
     *  设置此容器的布局管理器。将覆盖此方法以防止设置布局mgr。
     * 
     * 
     * @param mgr the specified layout manager
     */
    public final void setLayout(LayoutManager mgr) {
        throw new AWTError("ScrollPane controls layout");
    }

    /**
     * Lays out this container by resizing its child to its preferred size.
     * If the new preferred size of the child causes the current scroll
     * position to be invalid, the scroll position is set to the closest
     * valid position.
     *
     * <p>
     *  通过将其子项大小调整为其首选大小来放出此容器。如果孩子的新的优选大小导致当前滚动位置无效,则滚动位置被设置为最近的有效位置。
     * 
     * 
     * @see Component#validate
     */
    public void doLayout() {
        layout();
    }

    /**
     * Determine the size to allocate the child component.
     * If the viewport area is bigger than the preferred size
     * of the child then the child is allocated enough
     * to fill the viewport, otherwise the child is given
     * it's preferred size.
     * <p>
     *  确定分配子组件的大小。如果视口区域大于子元素的首选大小,则子元素被分配足以填充视口,否则子元素被赋予其优选大小。
     * 
     */
    Dimension calculateChildSize() {
        //
        // calculate the view size, accounting for border but not scrollbars
        // - don't use right/bottom insets since they vary depending
        //   on whether or not scrollbars were displayed on last resize
        //
        Dimension       size = getSize();
        Insets          insets = getInsets();
        int             viewWidth = size.width - insets.left*2;
        int             viewHeight = size.height - insets.top*2;

        //
        // determine whether or not horz or vert scrollbars will be displayed
        //
        boolean vbarOn;
        boolean hbarOn;
        Component child = getComponent(0);
        Dimension childSize = new Dimension(child.getPreferredSize());

        if (scrollbarDisplayPolicy == SCROLLBARS_AS_NEEDED) {
            vbarOn = childSize.height > viewHeight;
            hbarOn = childSize.width  > viewWidth;
        } else if (scrollbarDisplayPolicy == SCROLLBARS_ALWAYS) {
            vbarOn = hbarOn = true;
        } else { // SCROLLBARS_NEVER
            vbarOn = hbarOn = false;
        }

        //
        // adjust predicted view size to account for scrollbars
        //
        int vbarWidth = getVScrollbarWidth();
        int hbarHeight = getHScrollbarHeight();
        if (vbarOn) {
            viewWidth -= vbarWidth;
        }
        if(hbarOn) {
            viewHeight -= hbarHeight;
        }

        //
        // if child is smaller than view, size it up
        //
        if (childSize.width < viewWidth) {
            childSize.width = viewWidth;
        }
        if (childSize.height < viewHeight) {
            childSize.height = viewHeight;
        }

        return childSize;
    }

    /**
    /* <p>
    /* 
     * @deprecated As of JDK version 1.1,
     * replaced by <code>doLayout()</code>.
     */
    @Deprecated
    public void layout() {
        if (getComponentCount()==0) {
            return;
        }
        Component c = getComponent(0);
        Point p = getScrollPosition();
        Dimension cs = calculateChildSize();
        Dimension vs = getViewportSize();
        Insets i = getInsets();

        c.reshape(i.left - p.x, i.top - p.y, cs.width, cs.height);
        ScrollPanePeer peer = (ScrollPanePeer)this.peer;
        if (peer != null) {
            peer.childResized(cs.width, cs.height);
        }

        // update adjustables... the viewport size may have changed
        // with the scrollbars coming or going so the viewport size
        // is updated before the adjustables.
        vs = getViewportSize();
        hAdjustable.setSpan(0, cs.width, vs.width);
        vAdjustable.setSpan(0, cs.height, vs.height);
    }

    /**
     * Prints the component in this scroll pane.
     * <p>
     *  在此滚动窗格中打印组件。
     * 
     * 
     * @param g the specified Graphics window
     * @see Component#print
     * @see Component#printAll
     */
    public void printComponents(Graphics g) {
        if (getComponentCount()==0) {
            return;
        }
        Component c = getComponent(0);
        Point p = c.getLocation();
        Dimension vs = getViewportSize();
        Insets i = getInsets();

        Graphics cg = g.create();
        try {
            cg.clipRect(i.left, i.top, vs.width, vs.height);
            cg.translate(p.x, p.y);
            c.printAll(cg);
        } finally {
            cg.dispose();
        }
    }

    /**
     * Creates the scroll pane's peer.
     * <p>
     *  创建滚动窗格的对等体。
     * 
     */
    public void addNotify() {
        synchronized (getTreeLock()) {

            int vAdjustableValue = 0;
            int hAdjustableValue = 0;

            // Bug 4124460. Save the current adjustable values,
            // so they can be restored after addnotify. Set the
            // adjustables to 0, to prevent crashes for possible
            // negative values.
            if (getComponentCount() > 0) {
                vAdjustableValue = vAdjustable.getValue();
                hAdjustableValue = hAdjustable.getValue();
                vAdjustable.setValue(0);
                hAdjustable.setValue(0);
            }

            if (peer == null)
                peer = getToolkit().createScrollPane(this);
            super.addNotify();

            // Bug 4124460. Restore the adjustable values.
            if (getComponentCount() > 0) {
                vAdjustable.setValue(vAdjustableValue);
                hAdjustable.setValue(hAdjustableValue);
            }
        }
    }

    /**
     * Returns a string representing the state of this
     * <code>ScrollPane</code>. This
     * method is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not be
     * <code>null</code>.
     *
     * <p>
     * 返回表示此<code> ScrollPane </code>的状态的字符串。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return the parameter string of this scroll pane
     */
    public String paramString() {
        String sdpStr;
        switch (scrollbarDisplayPolicy) {
            case SCROLLBARS_AS_NEEDED:
                sdpStr = "as-needed";
                break;
            case SCROLLBARS_ALWAYS:
                sdpStr = "always";
                break;
            case SCROLLBARS_NEVER:
                sdpStr = "never";
                break;
            default:
                sdpStr = "invalid display policy";
        }
        Point p = (getComponentCount()>0)? getScrollPosition() : new Point(0,0);
        Insets i = getInsets();
        return super.paramString()+",ScrollPosition=("+p.x+","+p.y+")"+
            ",Insets=("+i.top+","+i.left+","+i.bottom+","+i.right+")"+
            ",ScrollbarDisplayPolicy="+sdpStr+
        ",wheelScrollingEnabled="+isWheelScrollingEnabled();
    }

    void autoProcessMouseWheel(MouseWheelEvent e) {
        processMouseWheelEvent(e);
    }

    /**
     * Process mouse wheel events that are delivered to this
     * <code>ScrollPane</code> by scrolling an appropriate amount.
     * <p>Note that if the event parameter is <code>null</code>
     * the behavior is unspecified and may result in an
     * exception.
     *
     * <p>
     *  处理通过滚动适当的量传递到此<code> ScrollPane </code>的鼠标滚轮事件。 <p>请注意,如果事件参数为<code> null </code>,则此行为未指定,并可能导致异常。
     * 
     * 
     * @param e  the mouse wheel event
     * @since 1.4
     */
    protected void processMouseWheelEvent(MouseWheelEvent e) {
        if (isWheelScrollingEnabled()) {
            ScrollPaneWheelScroller.handleWheelScrolling(this, e);
            e.consume();
        }
        super.processMouseWheelEvent(e);
    }

    /**
     * If wheel scrolling is enabled, we return true for MouseWheelEvents
     * <p>
     *  如果启用滚轮滚动,则对MouseWheelEvents返回true
     * 
     * 
     * @since 1.4
     */
    protected boolean eventTypeEnabled(int type) {
        if (type == MouseEvent.MOUSE_WHEEL && isWheelScrollingEnabled()) {
            return true;
        }
        else {
            return super.eventTypeEnabled(type);
        }
    }

    /**
     * Enables/disables scrolling in response to movement of the mouse wheel.
     * Wheel scrolling is enabled by default.
     *
     * <p>
     *  启用/禁用响应鼠标滚轮的移动进行滚动。默认情况下启用滚轮滚动。
     * 
     * 
     * @param handleWheel   <code>true</code> if scrolling should be done
     *                      automatically for a MouseWheelEvent,
     *                      <code>false</code> otherwise.
     * @see #isWheelScrollingEnabled
     * @see java.awt.event.MouseWheelEvent
     * @see java.awt.event.MouseWheelListener
     * @since 1.4
     */
    public void setWheelScrollingEnabled(boolean handleWheel) {
        wheelScrollingEnabled = handleWheel;
    }

    /**
     * Indicates whether or not scrolling will take place in response to
     * the mouse wheel.  Wheel scrolling is enabled by default.
     *
     * <p>
     *  指示是否响应鼠标滚轮进行滚动。默认情况下启用滚轮滚动。
     * 
     * 
     * @see #setWheelScrollingEnabled(boolean)
     * @since 1.4
     */
    public boolean isWheelScrollingEnabled() {
        return wheelScrollingEnabled;
    }


    /**
     * Writes default serializable fields to stream.
     * <p>
     *  将缺省可序列化字段写入流。
     * 
     */
    private void writeObject(ObjectOutputStream s) throws IOException {
        // 4352819: We only need this degenerate writeObject to make
        // it safe for future versions of this class to write optional
        // data to the stream.
        s.defaultWriteObject();
    }

    /**
     * Reads default serializable fields to stream.
     * <p>
     *  读取默认可序列化字段流。
     * 
     * 
     * @exception HeadlessException if
     * <code>GraphicsEnvironment.isHeadless()</code> returns
     * <code>true</code>
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    private void readObject(ObjectInputStream s)
        throws ClassNotFoundException, IOException, HeadlessException
    {
        GraphicsEnvironment.checkHeadless();
        // 4352819: Gotcha!  Cannot use s.defaultReadObject here and
        // then continue with reading optional data.  Use GetField instead.
        ObjectInputStream.GetField f = s.readFields();

        // Old fields
        scrollbarDisplayPolicy = f.get("scrollbarDisplayPolicy",
                                       SCROLLBARS_AS_NEEDED);
        hAdjustable = (ScrollPaneAdjustable)f.get("hAdjustable", null);
        vAdjustable = (ScrollPaneAdjustable)f.get("vAdjustable", null);

        // Since 1.4
        wheelScrollingEnabled = f.get("wheelScrollingEnabled",
                                      defaultWheelScroll);

//      // Note to future maintainers
//      if (f.defaulted("wheelScrollingEnabled")) {
//          // We are reading pre-1.4 stream that doesn't have
//          // optional data, not even the TC_ENDBLOCKDATA marker.
//          // Reading anything after this point is unsafe as we will
//          // read unrelated objects further down the stream (4352819).
//      }
//      else {
//          // Reading data from 1.4 or later, it's ok to try to read
//          // optional data as OptionalDataException with eof == true
//          // will be correctly reported
//      }
    }

    class PeerFixer implements AdjustmentListener, java.io.Serializable
    {
        private static final long serialVersionUID = 1043664721353696630L;

        PeerFixer(ScrollPane scroller) {
            this.scroller = scroller;
        }

        /**
         * Invoked when the value of the adjustable has changed.
         * <p>
         *  在可调整值已更改时调用。
         * 
         */
        public void adjustmentValueChanged(AdjustmentEvent e) {
            Adjustable adj = e.getAdjustable();
            int value = e.getValue();
            ScrollPanePeer peer = (ScrollPanePeer) scroller.peer;
            if (peer != null) {
                peer.setValue(adj, value);
            }

            Component c = scroller.getComponent(0);
            switch(adj.getOrientation()) {
            case Adjustable.VERTICAL:
                c.move(c.getLocation().x, -(value));
                break;
            case Adjustable.HORIZONTAL:
                c.move(-(value), c.getLocation().y);
                break;
            default:
                throw new IllegalArgumentException("Illegal adjustable orientation");
            }
        }

        private ScrollPane scroller;
    }


/////////////////
// Accessibility support
////////////////

    /**
     * Gets the AccessibleContext associated with this ScrollPane.
     * For scroll panes, the AccessibleContext takes the form of an
     * AccessibleAWTScrollPane.
     * A new AccessibleAWTScrollPane instance is created if necessary.
     *
     * <p>
     *  获取与此ScrollPane关联的AccessibleContext。对于滚动窗格,AccessibleContext采用AccessibleAWTScrollPane的形式。
     * 如果需要,将创建一个新的AccessibleAWTScrollPane实例。
     * 
     * 
     * @return an AccessibleAWTScrollPane that serves as the
     *         AccessibleContext of this ScrollPane
     * @since 1.3
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleAWTScrollPane();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>ScrollPane</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to scroll pane user-interface
     * elements.
     * <p>
     *  此类实现<code> ScrollPane </code>类的辅助功能支持。它提供了适用于滚动窗格用户界面元素的Java辅助功能API的实现。
     * 
     * 
     * @since 1.3
     */
    protected class AccessibleAWTScrollPane extends AccessibleAWTContainer
    {
        /*
         * JDK 1.3 serialVersionUID
         * <p>
         *  JDK 1.3 serialVersionUID
         * 
         */
        private static final long serialVersionUID = 6100703663886637L;

        /**
         * Get the role of this object.
         *
         * <p>
         *  获取此对象的作用。
         * 
         * 
         * @return an instance of AccessibleRole describing the role of the
         * object
         * @see AccessibleRole
         */
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.SCROLL_PANE;
        }

    } // class AccessibleAWTScrollPane

}

/*
 * In JDK 1.1.1, the pkg private class java.awt.PeerFixer was moved to
 * become an inner class of ScrollPane, which broke serialization
 * for ScrollPane objects using JDK 1.1.
 * Instead of moving it back out here, which would break all JDK 1.1.x
 * releases, we keep PeerFixer in both places. Because of the scoping rules,
 * the PeerFixer that is used in ScrollPane will be the one that is the
 * inner class. This pkg private PeerFixer class below will only be used
 * if the Java 2 platform is used to deserialize ScrollPane objects that were serialized
 * using JDK1.1
 * <p>
 * 在JDK 1.1.1中,pkg私有类java.awt.PeerFixer被移动成为ScrollPane的内部类,它使用JDK 1.1打破了ScrollPane对象的序列化。
 * 不是将其移回这里,这将打破所有JDK 1.1.x版本,我们在两个地方保持PeerFixer。由于范围规则,在ScrollPane中使用的PeerFixer将是内部类。
 * 下面的pkg私有PeerFixer类只会在Java 2平台用于反序列化使用JDK1.1序列化的ScrollPane对象时使用。
 * 
 */
class PeerFixer implements AdjustmentListener, java.io.Serializable {
    /*
     * serialVersionUID
     * <p>
     *  serialVersionUID
     * 
     */
    private static final long serialVersionUID = 7051237413532574756L;

    PeerFixer(ScrollPane scroller) {
        this.scroller = scroller;
    }

    /**
     * Invoked when the value of the adjustable has changed.
     * <p>
     *  在可调整值已更改时调用。
     */
    public void adjustmentValueChanged(AdjustmentEvent e) {
        Adjustable adj = e.getAdjustable();
        int value = e.getValue();
        ScrollPanePeer peer = (ScrollPanePeer) scroller.peer;
        if (peer != null) {
            peer.setValue(adj, value);
        }

        Component c = scroller.getComponent(0);
        switch(adj.getOrientation()) {
        case Adjustable.VERTICAL:
            c.move(c.getLocation().x, -(value));
            break;
        case Adjustable.HORIZONTAL:
            c.move(-(value), c.getLocation().y);
            break;
        default:
            throw new IllegalArgumentException("Illegal adjustable orientation");
        }
    }

    private ScrollPane scroller;
}
