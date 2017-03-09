/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2003, Oracle and/or its affiliates. All rights reserved.
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

package javax.accessibility;

import java.awt.*;
import java.awt.event.*;

/**
 * The AccessibleComponent interface should be supported by any object
 * that is rendered on the screen.  This interface provides the standard
 * mechanism for an assistive technology to determine and set the
 * graphical representation of an object.  Applications can determine
 * if an object supports the AccessibleComponent interface by first
 * obtaining its AccessibleContext
 * and then calling the
 * {@link AccessibleContext#getAccessibleComponent} method.
 * If the return value is not null, the object supports this interface.
 *
 * <p>
 *  AccessibleComponent接口应该由在屏幕上呈现的任何对象支持。该接口为辅助技术提供标准机制,以确定和设置对象的图形表示。
 * 应用程序可以通过首先获取其AccessibleContext然后调用{@link AccessibleContext#getAccessibleComponent}方法来确定对象是否支持Accessib
 * leComponent接口。
 *  AccessibleComponent接口应该由在屏幕上呈现的任何对象支持。该接口为辅助技术提供标准机制,以确定和设置对象的图形表示。如果返回值不为null,则对象支持此接口。
 * 
 * 
 * @see Accessible
 * @see Accessible#getAccessibleContext
 * @see AccessibleContext
 * @see AccessibleContext#getAccessibleComponent
 *
 * @author      Peter Korn
 * @author      Hans Muller
 * @author      Willie Walker
 */
public interface AccessibleComponent {

    /**
     * Gets the background color of this object.
     *
     * <p>
     *  获取此对象的背景颜色。
     * 
     * 
     * @return the background color, if supported, of the object;
     * otherwise, null
     * @see #setBackground
     */
    public Color getBackground();

    /**
     * Sets the background color of this object.
     *
     * <p>
     *  设置此对象的背景颜色。
     * 
     * 
     * @param c the new Color for the background
     * @see #setBackground
     */
    public void setBackground(Color c);

    /**
     * Gets the foreground color of this object.
     *
     * <p>
     *  获取此对象的前景颜色。
     * 
     * 
     * @return the foreground color, if supported, of the object;
     * otherwise, null
     * @see #setForeground
     */
    public Color getForeground();

    /**
     * Sets the foreground color of this object.
     *
     * <p>
     *  设置此对象的前景颜色。
     * 
     * 
     * @param c the new Color for the foreground
     * @see #getForeground
     */
    public void setForeground(Color c);

    /**
     * Gets the Cursor of this object.
     *
     * <p>
     *  获取此对象的Cursor。
     * 
     * 
     * @return the Cursor, if supported, of the object; otherwise, null
     * @see #setCursor
     */
    public Cursor getCursor();

    /**
     * Sets the Cursor of this object.
     *
     * <p>
     *  设置此对象的Cursor。
     * 
     * 
     * @param cursor  the new Cursor for the object
     * @see #getCursor
     */
    public void setCursor(Cursor cursor);

    /**
     * Gets the Font of this object.
     *
     * <p>
     *  获取此对象的字体。
     * 
     * 
     * @return the Font,if supported, for the object; otherwise, null
     * @see #setFont
     */
    public Font getFont();

    /**
     * Sets the Font of this object.
     *
     * <p>
     *  设置此对象的字体。
     * 
     * 
     * @param f the new Font for the object
     * @see #getFont
     */
    public void setFont(Font f);

    /**
     * Gets the FontMetrics of this object.
     *
     * <p>
     *  获取此对象的FontMetrics。
     * 
     * 
     * @param f the Font
     * @return the FontMetrics, if supported, the object; otherwise, null
     * @see #getFont
     */
    public FontMetrics getFontMetrics(Font f);

    /**
     * Determines if the object is enabled.  Objects that are enabled
     * will also have the AccessibleState.ENABLED state set in their
     * AccessibleStateSets.
     *
     * <p>
     *  确定对象是否已启用。已启用的对象也将在其AccessibleStateSets中设置AccessibleState.ENABLED状态。
     * 
     * 
     * @return true if object is enabled; otherwise, false
     * @see #setEnabled
     * @see AccessibleContext#getAccessibleStateSet
     * @see AccessibleState#ENABLED
     * @see AccessibleStateSet
     */
    public boolean isEnabled();

    /**
     * Sets the enabled state of the object.
     *
     * <p>
     *  设置对象的启用状态。
     * 
     * 
     * @param b if true, enables this object; otherwise, disables it
     * @see #isEnabled
     */
    public void setEnabled(boolean b);

    /**
     * Determines if the object is visible.  Note: this means that the
     * object intends to be visible; however, it may not be
     * showing on the screen because one of the objects that this object
     * is contained by is currently not visible.  To determine if an object is
     * showing on the screen, use isShowing().
     * <p>Objects that are visible will also have the
     * AccessibleState.VISIBLE state set in their AccessibleStateSets.
     *
     * <p>
     * 确定对象是否可见。注意：这意味着对象是可见的;但是,它可能不会显示在屏幕上,因为该对象包含的对象之一当前不可见。要确定对象是否显示在屏幕上,请使用isShowing()。
     *  <p>可见的对象也将在其AccessibleStateSets中设置AccessibleState.VISIBLE状态。
     * 
     * 
     * @return true if object is visible; otherwise, false
     * @see #setVisible
     * @see AccessibleContext#getAccessibleStateSet
     * @see AccessibleState#VISIBLE
     * @see AccessibleStateSet
     */
    public boolean isVisible();

    /**
     * Sets the visible state of the object.
     *
     * <p>
     *  设置对象的可见状态。
     * 
     * 
     * @param b if true, shows this object; otherwise, hides it
     * @see #isVisible
     */
    public void setVisible(boolean b);

    /**
     * Determines if the object is showing.  This is determined by checking
     * the visibility of the object and its ancestors.
     * Note: this
     * will return true even if the object is obscured by another (for example,
     * it is underneath a menu that was pulled down).
     *
     * <p>
     *  确定对象是否正在显示。这是通过检查对象及其祖先的可见性来确定的。注意：即使对象被另一个对象遮盖,这将返回true(例如,它位于下拉的菜单下)。
     * 
     * 
     * @return true if object is showing; otherwise, false
     */
    public boolean isShowing();

    /**
     * Checks whether the specified point is within this object's bounds,
     * where the point's x and y coordinates are defined to be relative to the
     * coordinate system of the object.
     *
     * <p>
     *  检查指定点是否在此对象的边界内,其中点的x和y坐标被定义为相对于对象的坐标系。
     * 
     * 
     * @param p the Point relative to the coordinate system of the object
     * @return true if object contains Point; otherwise false
     * @see #getBounds
     */
    public boolean contains(Point p);

    /**
     * Returns the location of the object on the screen.
     *
     * <p>
     *  返回对象在屏幕上的位置。
     * 
     * 
     * @return the location of the object on screen; null if this object
     * is not on the screen
     * @see #getBounds
     * @see #getLocation
     */
    public Point getLocationOnScreen();

    /**
     * Gets the location of the object relative to the parent in the form
     * of a point specifying the object's top-left corner in the screen's
     * coordinate space.
     *
     * <p>
     *  以指定对象在屏幕坐标空间中左上角的点的形式获取对象相对于父对象的位置。
     * 
     * 
     * @return An instance of Point representing the top-left corner of the
     * object's bounds in the coordinate space of the screen; null if
     * this object or its parent are not on the screen
     * @see #getBounds
     * @see #getLocationOnScreen
     */
    public Point getLocation();

    /**
     * Sets the location of the object relative to the parent.
     * <p>
     *  设置对象相对于父对象的位置。
     * 
     * 
     * @param p the new position for the top-left corner
     * @see #getLocation
     */
    public void setLocation(Point p);

    /**
     * Gets the bounds of this object in the form of a Rectangle object.
     * The bounds specify this object's width, height, and location
     * relative to its parent.
     *
     * <p>
     *  以Rectangle对象的形式获取此对象的边界。 bounds指定此对象的宽度,高度和相对于其父级的位置。
     * 
     * 
     * @return A rectangle indicating this component's bounds; null if
     * this object is not on the screen.
     * @see #contains
     */
    public Rectangle getBounds();

    /**
     * Sets the bounds of this object in the form of a Rectangle object.
     * The bounds specify this object's width, height, and location
     * relative to its parent.
     *
     * <p>
     *  以Rectangle对象的形式设置此对象的边界。 bounds指定此对象的宽度,高度和相对于其父级的位置。
     * 
     * 
     * @param r rectangle indicating this component's bounds
     * @see #getBounds
     */
    public void setBounds(Rectangle r);

    /**
     * Returns the size of this object in the form of a Dimension object.
     * The height field of the Dimension object contains this object's
     * height, and the width field of the Dimension object contains this
     * object's width.
     *
     * <p>
     * 以Dimension对象的形式返回此对象的大小。 Dimension对象的height字段包含此对象的高度,Dimension对象的width字段包含此对象的宽度。
     * 
     * 
     * @return A Dimension object that indicates the size of this component;
     * null if this object is not on the screen
     * @see #setSize
     */
    public Dimension getSize();

    /**
     * Resizes this object so that it has width and height.
     *
     * <p>
     *  调整此对象的大小,使其具有宽度和高度。
     * 
     * 
     * @param d The dimension specifying the new size of the object.
     * @see #getSize
     */
    public void setSize(Dimension d);

    /**
     * Returns the Accessible child, if one exists, contained at the local
     * coordinate Point.
     *
     * <p>
     *  返回Accessible child(如果存在)包含在本地坐标Point。
     * 
     * 
     * @param p The point relative to the coordinate system of this object.
     * @return the Accessible, if it exists, at the specified location;
     * otherwise null
     */
    public Accessible getAccessibleAt(Point p);

    /**
     * Returns whether this object can accept focus or not.   Objects that
     * can accept focus will also have the AccessibleState.FOCUSABLE state
     * set in their AccessibleStateSets.
     *
     * <p>
     *  返回此对象是否可以接受焦点。可以接受焦点的对象也将在其AccessibleStateSets中设置AccessibleState.FOCUSABLE状态。
     * 
     * 
     * @return true if object can accept focus; otherwise false
     * @see AccessibleContext#getAccessibleStateSet
     * @see AccessibleState#FOCUSABLE
     * @see AccessibleState#FOCUSED
     * @see AccessibleStateSet
     */
    public boolean isFocusTraversable();

    /**
     * Requests focus for this object.  If this object cannot accept focus,
     * nothing will happen.  Otherwise, the object will attempt to take
     * focus.
     * <p>
     *  此对象的请求焦点。如果这个对象不能接受焦点,什么也不会发生。否则,对象将尝试获取焦点。
     * 
     * 
     * @see #isFocusTraversable
     */
    public void requestFocus();

    /**
     * Adds the specified focus listener to receive focus events from this
     * component.
     *
     * <p>
     *  添加指定的焦点侦听器以从此组件接收焦点事件。
     * 
     * 
     * @param l the focus listener
     * @see #removeFocusListener
     */
    public void addFocusListener(FocusListener l);

    /**
     * Removes the specified focus listener so it no longer receives focus
     * events from this component.
     *
     * <p>
     *  删除指定的焦点侦听器,使其不再从此组件接收焦点事件。
     * 
     * @param l the focus listener
     * @see #addFocusListener
     */
    public void removeFocusListener(FocusListener l);
}
