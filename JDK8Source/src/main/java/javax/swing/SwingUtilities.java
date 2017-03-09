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

import sun.reflect.misc.ReflectUtil;
import sun.swing.SwingUtilities2;
import sun.swing.UIAction;

import java.applet.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.dnd.DropTarget;

import java.lang.reflect.*;

import javax.accessibility.*;
import javax.swing.event.MenuDragMouseEvent;
import javax.swing.plaf.UIResource;
import javax.swing.text.View;
import java.security.AccessController;
import sun.security.action.GetPropertyAction;

import sun.awt.AppContext;

/**
 * A collection of utility methods for Swing.
 *
 * <p>
 *  Swing的实用程序方法的集合。
 * 
 * 
 * @author unknown
 */
public class SwingUtilities implements SwingConstants
{
    // These states are system-wide, rather than AppContext wide.
    private static boolean canAccessEventQueue = false;
    private static boolean eventQueueTested = false;

    /**
     * Indicates if we should change the drop target when a
     * {@code TransferHandler} is set.
     * <p>
     *  指示在设置{@code TransferHandler}时是否应更改放置目标。
     * 
     */
    private static boolean suppressDropSupport;

    /**
     * Indiciates if we've checked the system property for suppressing
     * drop support.
     * <p>
     *  指示是否已检查系统属性以抑制拖放支持。
     * 
     */
    private static boolean checkedSuppressDropSupport;


    /**
     * Returns true if <code>setTransferHandler</code> should change the
     * <code>DropTarget</code>.
     * <p>
     *  如果<code> setTransferHandler </code>应该更改<code> DropTarget </code>,则返回true。
     * 
     */
    private static boolean getSuppressDropTarget() {
        if (!checkedSuppressDropSupport) {
            suppressDropSupport = Boolean.valueOf(
                AccessController.doPrivileged(
                    new GetPropertyAction("suppressSwingDropSupport")));
            checkedSuppressDropSupport = true;
        }
        return suppressDropSupport;
    }

    /**
     * Installs a {@code DropTarget} on the component as necessary for a
     * {@code TransferHandler} change.
     * <p>
     *  根据需要在组件上安装{@code DropTarget}以进行{@code TransferHandler}更改。
     * 
     */
    static void installSwingDropTargetAsNecessary(Component c,
                                                         TransferHandler t) {

        if (!getSuppressDropTarget()) {
            DropTarget dropHandler = c.getDropTarget();
            if ((dropHandler == null) || (dropHandler instanceof UIResource)) {
                if (t == null) {
                    c.setDropTarget(null);
                } else if (!GraphicsEnvironment.isHeadless()) {
                    c.setDropTarget(new TransferHandler.SwingDropTarget(c));
                }
            }
        }
    }

    /**
     * Return true if <code>a</code> contains <code>b</code>
     * <p>
     *  如果<code> a </code>包含<code> b </code>,则返回true
     * 
     */
    public static final boolean isRectangleContainingRectangle(Rectangle a,Rectangle b) {
        return b.x >= a.x && (b.x + b.width) <= (a.x + a.width) &&
                b.y >= a.y && (b.y + b.height) <= (a.y + a.height);
    }

    /**
     * Return the rectangle (0,0,bounds.width,bounds.height) for the component <code>aComponent</code>
     * <p>
     *  返回组件的矩形(0,0,bounds.width,bounds.height)<code> aComponent </code>
     * 
     */
    public static Rectangle getLocalBounds(Component aComponent) {
        Rectangle b = new Rectangle(aComponent.getBounds());
        b.x = b.y = 0;
        return b;
    }


    /**
     * Returns the first <code>Window </code> ancestor of <code>c</code>, or
     * {@code null} if <code>c</code> is not contained inside a <code>Window</code>.
     *
     * <p>
     *  如果<code> Window </code>中不包含<code> c </code>,则返回<code> c </code>的第一个<code> Window </code>祖先, 。
     * 
     * 
     * @param c <code>Component</code> to get <code>Window</code> ancestor
     *        of.
     * @return the first <code>Window </code> ancestor of <code>c</code>, or
     *         {@code null} if <code>c</code> is not contained inside a
     *         <code>Window</code>.
     * @since 1.3
     */
    public static Window getWindowAncestor(Component c) {
        for(Container p = c.getParent(); p != null; p = p.getParent()) {
            if (p instanceof Window) {
                return (Window)p;
            }
        }
        return null;
    }

    /**
     * Converts the location <code>x</code> <code>y</code> to the
     * parents coordinate system, returning the location.
     * <p>
     *  将位置<code> x </code> <code> y </code>转换为父级坐标系,返回位置。
     * 
     */
    static Point convertScreenLocationToParent(Container parent,int x, int y) {
        for (Container p = parent; p != null; p = p.getParent()) {
            if (p instanceof Window) {
                Point point = new Point(x, y);

                SwingUtilities.convertPointFromScreen(point, parent);
                return point;
            }
        }
        throw new Error("convertScreenLocationToParent: no window ancestor");
    }

    /**
     * Convert a <code>aPoint</code> in <code>source</code> coordinate system to
     * <code>destination</code> coordinate system.
     * If <code>source</code> is {@code null}, <code>aPoint</code> is assumed to be in <code>destination</code>'s
     * root component coordinate system.
     * If <code>destination</code> is {@code null}, <code>aPoint</code> will be converted to <code>source</code>'s
     * root component coordinate system.
     * If both <code>source</code> and <code>destination</code> are {@code null}, return <code>aPoint</code>
     * without any conversion.
     * <p>
     *  将<code>源</code>坐标系中的<code> aPoint </code>转换为<code>目标</code>坐标系。
     * 如果<code> source </code>是{@code null},则假定<code> aPoint </code>在<code>目标</code>的根组件坐标系中。
     * 如果<code> destination </code>是{@code null},则<code> aPoint </code>将转换为<code> source </code>的根组件坐标系。
     * 如果<code> source </code>和<code> destination </code>都是{@code null},则返回<code> aPoint </code>。
     * 
     */
    public static Point convertPoint(Component source,Point aPoint,Component destination) {
        Point p;

        if(source == null && destination == null)
            return aPoint;
        if(source == null) {
            source = getWindowAncestor(destination);
            if(source == null)
                throw new Error("Source component not connected to component tree hierarchy");
        }
        p = new Point(aPoint);
        convertPointToScreen(p,source);
        if(destination == null) {
            destination = getWindowAncestor(source);
            if(destination == null)
                throw new Error("Destination component not connected to component tree hierarchy");
        }
        convertPointFromScreen(p,destination);
        return p;
    }

    /**
     * Convert the point <code>(x,y)</code> in <code>source</code> coordinate system to
     * <code>destination</code> coordinate system.
     * If <code>source</code> is {@code null}, <code>(x,y)</code> is assumed to be in <code>destination</code>'s
     * root component coordinate system.
     * If <code>destination</code> is {@code null}, <code>(x,y)</code> will be converted to <code>source</code>'s
     * root component coordinate system.
     * If both <code>source</code> and <code>destination</code> are {@code null}, return <code>(x,y)</code>
     * without any conversion.
     * <p>
     * 将<code>源</code>坐标系中的<code>(x,y)</code>转换为<code>目标</code>坐标系。
     * 如果<code> source </code>是{@code null},则假定<code>(x,y)</code>在<code>目标</code>的根组件坐标系中。
     * 如果<code> destination </code>为{@code null},则<code>(x,y)</code>将转换为<code> source </code>的根组件坐标系。
     * 如果<code> source </code>和<code> destination </code>都是{@code null},则返回<code>(x,y)</code>。
     * 
     */
    public static Point convertPoint(Component source,int x, int y,Component destination) {
        Point point = new Point(x,y);
        return convertPoint(source,point,destination);
    }

    /**
     * Convert the rectangle <code>aRectangle</code> in <code>source</code> coordinate system to
     * <code>destination</code> coordinate system.
     * If <code>source</code> is {@code null}, <code>aRectangle</code> is assumed to be in <code>destination</code>'s
     * root component coordinate system.
     * If <code>destination</code> is {@code null}, <code>aRectangle</code> will be converted to <code>source</code>'s
     * root component coordinate system.
     * If both <code>source</code> and <code>destination</code> are {@code null}, return <code>aRectangle</code>
     * without any conversion.
     * <p>
     *  将<code> source </code>坐标系中的矩形<code> aRectangle </code>转换为<code>目标</code>坐标系。
     * 如果<code> source </code>是{@code null},则假设<code> aRectangle </code>在<code>目标</code>的根组件坐标系中。
     * 如果<code> destination </code>是{@code null},则<code> aRectangle </code>将转换为<code> source </code>的根组件坐标系。
     * 如果<code> source </code>是{@code null},则假设<code> aRectangle </code>在<code>目标</code>的根组件坐标系中。
     * 如果<code> source </code>和<code> destination </code>都是{@code null},则返回<code> aRectangle </code>。
     * 
     */
    public static Rectangle convertRectangle(Component source,Rectangle aRectangle,Component destination) {
        Point point = new Point(aRectangle.x,aRectangle.y);
        point =  convertPoint(source,point,destination);
        return new Rectangle(point.x,point.y,aRectangle.width,aRectangle.height);
    }

    /**
     * Convenience method for searching above <code>comp</code> in the
     * component hierarchy and returns the first object of class <code>c</code> it
     * finds. Can return {@code null}, if a class <code>c</code> cannot be found.
     * <p>
     *  用于在组件层次结构中搜索<code> comp </code>上方的方便方法,并返回它找到的<code> c </code>类的第一个对象。
     * 如果无法找到<code> c </code>类,则可以返回{@code null}。
     * 
     */
    public static Container getAncestorOfClass(Class<?> c, Component comp)
    {
        if(comp == null || c == null)
            return null;

        Container parent = comp.getParent();
        while(parent != null && !(c.isInstance(parent)))
            parent = parent.getParent();
        return parent;
    }

    /**
     * Convenience method for searching above <code>comp</code> in the
     * component hierarchy and returns the first object of <code>name</code> it
     * finds. Can return {@code null}, if <code>name</code> cannot be found.
     * <p>
     * 用于在组件层次结构中搜索<code> comp </code>上方的方便方法,并返回其找到的<code> name </code>的第一个对象。
     * 如果找不到<code> name </code>,可以返回{@code null}。
     * 
     */
    public static Container getAncestorNamed(String name, Component comp) {
        if(comp == null || name == null)
            return null;

        Container parent = comp.getParent();
        while(parent != null && !(name.equals(parent.getName())))
            parent = parent.getParent();
        return parent;
    }

    /**
     * Returns the deepest visible descendent Component of <code>parent</code>
     * that contains the location <code>x</code>, <code>y</code>.
     * If <code>parent</code> does not contain the specified location,
     * then <code>null</code> is returned.  If <code>parent</code> is not a
     * container, or none of <code>parent</code>'s visible descendents
     * contain the specified location, <code>parent</code> is returned.
     *
     * <p>
     *  返回包含位置<code> x </code>,<code> y </code>的<code> parent </code>的最深可见的后代组件。
     * 如果<code> parent </code>不包含指定的位置,则返回<code> null </code>。
     * 如果<code> parent </code>不是容器,或者<code> parent </code>的可见后代都不包含指定的位置,则返回<code> parent </code>。
     * 
     * 
     * @param parent the root component to begin the search
     * @param x the x target location
     * @param y the y target location
     */
    public static Component getDeepestComponentAt(Component parent, int x, int y) {
        if (!parent.contains(x, y)) {
            return null;
        }
        if (parent instanceof Container) {
            Component components[] = ((Container)parent).getComponents();
            for (Component comp : components) {
                if (comp != null && comp.isVisible()) {
                    Point loc = comp.getLocation();
                    if (comp instanceof Container) {
                        comp = getDeepestComponentAt(comp, x - loc.x, y - loc.y);
                    } else {
                        comp = comp.getComponentAt(x - loc.x, y - loc.y);
                    }
                    if (comp != null && comp.isVisible()) {
                        return comp;
                    }
                }
            }
        }
        return parent;
    }


    /**
     * Returns a MouseEvent similar to <code>sourceEvent</code> except that its x
     * and y members have been converted to <code>destination</code>'s coordinate
     * system.  If <code>source</code> is {@code null}, <code>sourceEvent</code> x and y members
     * are assumed to be into <code>destination</code>'s root component coordinate system.
     * If <code>destination</code> is <code>null</code>, the
     * returned MouseEvent will be in <code>source</code>'s coordinate system.
     * <code>sourceEvent</code> will not be changed. A new event is returned.
     * the <code>source</code> field of the returned event will be set
     * to <code>destination</code> if destination is non-{@code null}
     * use the translateMouseEvent() method to translate a mouse event from
     * one component to another without changing the source.
     * <p>
     *  返回类似于<code> sourceEvent </code>的MouseEvent,除了它的x和y成员已经转换为<code> destination </code>的坐标系。
     * 如果<code> source </code>是{@code null},则假设<code> sourceEvent </code> x和y成员进入<code>目标</code>的根组件坐标系。
     * 如果<code> destination </code>是<code> null </code>,则返回的MouseEvent将在<code> source </code>的坐标系中。
     *  <code> sourceEvent </code>不会被改变。返回一个新事件。
     * 如果目标不是 -  {@ code null},则使用translateMouseEvent()方法从一个组件翻译鼠标事件,则返回事件的<code> source </code>字段将设置为<code>
     *  destination </code>到另一个没有改变源。
     *  <code> sourceEvent </code>不会被改变。返回一个新事件。
     * 
     */
    public static MouseEvent convertMouseEvent(Component source,
                                               MouseEvent sourceEvent,
                                               Component destination) {
        Point p = convertPoint(source,new Point(sourceEvent.getX(),
                                                sourceEvent.getY()),
                               destination);
        Component newSource;

        if(destination != null)
            newSource = destination;
        else
            newSource = source;

        MouseEvent newEvent;
        if (sourceEvent instanceof MouseWheelEvent) {
            MouseWheelEvent sourceWheelEvent = (MouseWheelEvent)sourceEvent;
            newEvent = new MouseWheelEvent(newSource,
                                           sourceWheelEvent.getID(),
                                           sourceWheelEvent.getWhen(),
                                           sourceWheelEvent.getModifiers()
                                                   | sourceWheelEvent.getModifiersEx(),
                                           p.x,p.y,
                                           sourceWheelEvent.getXOnScreen(),
                                           sourceWheelEvent.getYOnScreen(),
                                           sourceWheelEvent.getClickCount(),
                                           sourceWheelEvent.isPopupTrigger(),
                                           sourceWheelEvent.getScrollType(),
                                           sourceWheelEvent.getScrollAmount(),
                                           sourceWheelEvent.getWheelRotation());
        }
        else if (sourceEvent instanceof MenuDragMouseEvent) {
            MenuDragMouseEvent sourceMenuDragEvent = (MenuDragMouseEvent)sourceEvent;
            newEvent = new MenuDragMouseEvent(newSource,
                                              sourceMenuDragEvent.getID(),
                                              sourceMenuDragEvent.getWhen(),
                                              sourceMenuDragEvent.getModifiers()
                                                      | sourceMenuDragEvent.getModifiersEx(),
                                              p.x,p.y,
                                              sourceMenuDragEvent.getXOnScreen(),
                                              sourceMenuDragEvent.getYOnScreen(),
                                              sourceMenuDragEvent.getClickCount(),
                                              sourceMenuDragEvent.isPopupTrigger(),
                                              sourceMenuDragEvent.getPath(),
                                              sourceMenuDragEvent.getMenuSelectionManager());
        }
        else {
            newEvent = new MouseEvent(newSource,
                                      sourceEvent.getID(),
                                      sourceEvent.getWhen(),
                                      sourceEvent.getModifiers()
                                              | sourceEvent.getModifiersEx(),
                                      p.x,p.y,
                                      sourceEvent.getXOnScreen(),
                                      sourceEvent.getYOnScreen(),
                                      sourceEvent.getClickCount(),
                                      sourceEvent.isPopupTrigger(),
                                      sourceEvent.getButton());
        }
        return newEvent;
    }


    /**
     * Convert a point from a component's coordinate system to
     * screen coordinates.
     *
     * <p>
     *  将点从组件的坐标系转换为屏幕坐标。
     * 
     * 
     * @param p  a Point object (converted to the new coordinate system)
     * @param c  a Component object
     */
    public static void convertPointToScreen(Point p,Component c) {
            Rectangle b;
            int x,y;

            do {
                if(c instanceof JComponent) {
                    x = c.getX();
                    y = c.getY();
                } else if(c instanceof java.applet.Applet ||
                          c instanceof java.awt.Window) {
                    try {
                        Point pp = c.getLocationOnScreen();
                        x = pp.x;
                        y = pp.y;
                    } catch (IllegalComponentStateException icse) {
                        x = c.getX();
                        y = c.getY();
                    }
                } else {
                    x = c.getX();
                    y = c.getY();
                }

                p.x += x;
                p.y += y;

                if(c instanceof java.awt.Window || c instanceof java.applet.Applet)
                    break;
                c = c.getParent();
            } while(c != null);
        }

    /**
     * Convert a point from a screen coordinates to a component's
     * coordinate system
     *
     * <p>
     * 将点从屏幕坐标转换为组件的坐标系
     * 
     * 
     * @param p  a Point object (converted to the new coordinate system)
     * @param c  a Component object
     */
    public static void convertPointFromScreen(Point p,Component c) {
        Rectangle b;
        int x,y;

        do {
            if(c instanceof JComponent) {
                x = c.getX();
                y = c.getY();
            }  else if(c instanceof java.applet.Applet ||
                       c instanceof java.awt.Window) {
                try {
                    Point pp = c.getLocationOnScreen();
                    x = pp.x;
                    y = pp.y;
                } catch (IllegalComponentStateException icse) {
                    x = c.getX();
                    y = c.getY();
                }
            } else {
                x = c.getX();
                y = c.getY();
            }

            p.x -= x;
            p.y -= y;

            if(c instanceof java.awt.Window || c instanceof java.applet.Applet)
                break;
            c = c.getParent();
        } while(c != null);
    }

    /**
     * Returns the first <code>Window </code> ancestor of <code>c</code>, or
     * {@code null} if <code>c</code> is not contained inside a <code>Window</code>.
     * <p>
     * Note: This method provides the same functionality as
     * <code>getWindowAncestor</code>.
     *
     * <p>
     *  如果<code> Window </code>中不包含<code> c </code>,则返回<code> c </code>的第一个<code> Window </code>祖先, 。
     * <p>
     *  注意：此方法提供与<code> getWindowAncestor </code>相同的功能。
     * 
     * 
     * @param c <code>Component</code> to get <code>Window</code> ancestor
     *        of.
     * @return the first <code>Window </code> ancestor of <code>c</code>, or
     *         {@code null} if <code>c</code> is not contained inside a
     *         <code>Window</code>.
     */
    public static Window windowForComponent(Component c) {
        return getWindowAncestor(c);
    }

    /**
     * Return <code>true</code> if a component <code>a</code> descends from a component <code>b</code>
     * <p>
     *  返回<code> true </code>如果组件<code> a </code>从组件下降<code> b </code>
     * 
     */
    public static boolean isDescendingFrom(Component a,Component b) {
        if(a == b)
            return true;
        for(Container p = a.getParent();p!=null;p=p.getParent())
            if(p == b)
                return true;
        return false;
    }


    /**
     * Convenience to calculate the intersection of two rectangles
     * without allocating a new rectangle.
     * If the two rectangles don't intersect,
     * then the returned rectangle begins at (0,0)
     * and has zero width and height.
     *
     * <p>
     *  方便计算两个矩形的交叉,而不分配一个新的矩形。如果两个矩形不相交,则返回的矩形从(0,0)开始并且具有零宽度和高度。
     * 
     * 
     * @param x       the X coordinate of the first rectangle's top-left point
     * @param y       the Y coordinate of the first rectangle's top-left point
     * @param width   the width of the first rectangle
     * @param height  the height of the first rectangle
     * @param dest    the second rectangle
     *
     * @return <code>dest</code>, modified to specify the intersection
     */
    public static Rectangle computeIntersection(int x,int y,int width,int height,Rectangle dest) {
        int x1 = (x > dest.x) ? x : dest.x;
        int x2 = ((x+width) < (dest.x + dest.width)) ? (x+width) : (dest.x + dest.width);
        int y1 = (y > dest.y) ? y : dest.y;
        int y2 = ((y + height) < (dest.y + dest.height) ? (y+height) : (dest.y + dest.height));

        dest.x = x1;
        dest.y = y1;
        dest.width = x2 - x1;
        dest.height = y2 - y1;

        // If rectangles don't intersect, return zero'd intersection.
        if (dest.width < 0 || dest.height < 0) {
            dest.x = dest.y = dest.width = dest.height = 0;
        }

        return dest;
    }

    /**
     * Convenience method that calculates the union of two rectangles
     * without allocating a new rectangle.
     *
     * <p>
     *  便利方法计算两个矩形的并集,而不分配新的矩形。
     * 
     * 
     * @param x the x-coordinate of the first rectangle
     * @param y the y-coordinate of the first rectangle
     * @param width the width of the first rectangle
     * @param height the height of the first rectangle
     * @param dest  the coordinates of the second rectangle; the union
     *    of the two rectangles is returned in this rectangle
     * @return the <code>dest</code> <code>Rectangle</code>
     */
    public static Rectangle computeUnion(int x,int y,int width,int height,Rectangle dest) {
        int x1 = (x < dest.x) ? x : dest.x;
        int x2 = ((x+width) > (dest.x + dest.width)) ? (x+width) : (dest.x + dest.width);
        int y1 = (y < dest.y) ? y : dest.y;
        int y2 = ((y+height) > (dest.y + dest.height)) ? (y+height) : (dest.y + dest.height);

        dest.x = x1;
        dest.y = y1;
        dest.width = (x2 - x1);
        dest.height= (y2 - y1);
        return dest;
    }

    /**
     * Convenience returning an array of rect representing the regions within
     * <code>rectA</code> that do not overlap with <code>rectB</code>. If the
     * two Rects do not overlap, returns an empty array
     * <p>
     *  方便地返回一个rect数组,表示<code> rectA </code>中不与<code> rectB </code>重叠的区域。如果两个Rect不重叠,则返回一个空数组
     * 
     */
    public static Rectangle[] computeDifference(Rectangle rectA,Rectangle rectB) {
        if (rectB == null || !rectA.intersects(rectB) || isRectangleContainingRectangle(rectB,rectA)) {
            return new Rectangle[0];
        }

        Rectangle t = new Rectangle();
        Rectangle a=null,b=null,c=null,d=null;
        Rectangle result[];
        int rectCount = 0;

        /* rectA contains rectB */
        if (isRectangleContainingRectangle(rectA,rectB)) {
            t.x = rectA.x; t.y = rectA.y; t.width = rectB.x - rectA.x; t.height = rectA.height;
            if(t.width > 0 && t.height > 0) {
                a = new Rectangle(t);
                rectCount++;
            }

            t.x = rectB.x; t.y = rectA.y; t.width = rectB.width; t.height = rectB.y - rectA.y;
            if(t.width > 0 && t.height > 0) {
                b = new Rectangle(t);
                rectCount++;
            }

            t.x = rectB.x; t.y = rectB.y + rectB.height; t.width = rectB.width;
            t.height = rectA.y + rectA.height - (rectB.y + rectB.height);
            if(t.width > 0 && t.height > 0) {
                c = new Rectangle(t);
                rectCount++;
            }

            t.x = rectB.x + rectB.width; t.y = rectA.y; t.width = rectA.x + rectA.width - (rectB.x + rectB.width);
            t.height = rectA.height;
            if(t.width > 0 && t.height > 0) {
                d = new Rectangle(t);
                rectCount++;
            }
        } else {
            /* 1 */
            if (rectB.x <= rectA.x && rectB.y <= rectA.y) {
                if ((rectB.x + rectB.width) > (rectA.x + rectA.width)) {

                    t.x = rectA.x; t.y = rectB.y + rectB.height;
                    t.width = rectA.width; t.height = rectA.y + rectA.height - (rectB.y + rectB.height);
                    if(t.width > 0 && t.height > 0) {
                        a = t;
                        rectCount++;
                    }
                } else if ((rectB.y + rectB.height) > (rectA.y + rectA.height)) {
                    t.setBounds((rectB.x + rectB.width), rectA.y,
                                (rectA.x + rectA.width) - (rectB.x + rectB.width), rectA.height);
                    if(t.width > 0 && t.height > 0) {
                        a = t;
                        rectCount++;
                    }
                } else {
                    t.setBounds((rectB.x + rectB.width), rectA.y,
                                (rectA.x + rectA.width) - (rectB.x + rectB.width),
                                (rectB.y + rectB.height) - rectA.y);
                    if(t.width > 0 && t.height > 0) {
                        a = new Rectangle(t);
                        rectCount++;
                    }

                    t.setBounds(rectA.x, (rectB.y + rectB.height), rectA.width,
                                (rectA.y + rectA.height) - (rectB.y + rectB.height));
                    if(t.width > 0 && t.height > 0) {
                        b = new Rectangle(t);
                        rectCount++;
                    }
                }
            } else if (rectB.x <= rectA.x && (rectB.y + rectB.height) >= (rectA.y + rectA.height)) {
                if ((rectB.x + rectB.width) > (rectA.x + rectA.width)) {
                    t.setBounds(rectA.x, rectA.y, rectA.width, rectB.y - rectA.y);
                    if(t.width > 0 && t.height > 0) {
                        a = t;
                        rectCount++;
                    }
                } else {
                    t.setBounds(rectA.x, rectA.y, rectA.width, rectB.y - rectA.y);
                    if(t.width > 0 && t.height > 0) {
                        a = new Rectangle(t);
                        rectCount++;
                    }
                    t.setBounds((rectB.x + rectB.width), rectB.y,
                                (rectA.x + rectA.width) - (rectB.x + rectB.width),
                                (rectA.y + rectA.height) - rectB.y);
                    if(t.width > 0 && t.height > 0) {
                        b = new Rectangle(t);
                        rectCount++;
                    }
                }
            } else if (rectB.x <= rectA.x) {
                if ((rectB.x + rectB.width) >= (rectA.x + rectA.width)) {
                    t.setBounds(rectA.x, rectA.y, rectA.width, rectB.y - rectA.y);
                    if(t.width>0 && t.height > 0) {
                        a = new Rectangle(t);
                        rectCount++;
                    }

                    t.setBounds(rectA.x, (rectB.y + rectB.height), rectA.width,
                                (rectA.y + rectA.height) - (rectB.y + rectB.height));
                    if(t.width > 0 && t.height > 0) {
                        b = new Rectangle(t);
                        rectCount++;
                    }
                } else {
                    t.setBounds(rectA.x, rectA.y, rectA.width, rectB.y - rectA.y);
                    if(t.width > 0 && t.height > 0) {
                        a = new Rectangle(t);
                        rectCount++;
                    }

                    t.setBounds((rectB.x + rectB.width), rectB.y,
                                (rectA.x + rectA.width) - (rectB.x + rectB.width),
                                rectB.height);
                    if(t.width > 0 && t.height > 0) {
                        b = new Rectangle(t);
                        rectCount++;
                    }

                    t.setBounds(rectA.x, (rectB.y + rectB.height), rectA.width,
                                (rectA.y + rectA.height) - (rectB.y + rectB.height));
                    if(t.width > 0 && t.height > 0) {
                        c = new Rectangle(t);
                        rectCount++;
                    }
                }
            } else if (rectB.x <= (rectA.x + rectA.width) && (rectB.x + rectB.width) > (rectA.x + rectA.width)) {
                if (rectB.y <= rectA.y && (rectB.y + rectB.height) > (rectA.y + rectA.height)) {
                    t.setBounds(rectA.x, rectA.y, rectB.x - rectA.x, rectA.height);
                    if(t.width > 0 && t.height > 0) {
                        a = t;
                        rectCount++;
                    }
                } else if (rectB.y <= rectA.y) {
                    t.setBounds(rectA.x, rectA.y, rectB.x - rectA.x,
                                (rectB.y + rectB.height) - rectA.y);
                    if(t.width > 0 && t.height > 0) {
                        a = new Rectangle(t);
                        rectCount++;
                    }

                    t.setBounds(rectA.x, (rectB.y + rectB.height), rectA.width,
                                (rectA.y + rectA.height) - (rectB.y + rectB.height));
                    if(t.width > 0 && t.height > 0) {
                        b = new Rectangle(t);
                        rectCount++;
                    }
                } else if ((rectB.y + rectB.height) > (rectA.y + rectA.height)) {
                    t.setBounds(rectA.x, rectA.y, rectA.width, rectB.y - rectA.y);
                    if(t.width > 0 && t.height > 0) {
                        a = new Rectangle(t);
                        rectCount++;
                    }

                    t.setBounds(rectA.x, rectB.y, rectB.x - rectA.x,
                                (rectA.y + rectA.height) - rectB.y);
                    if(t.width > 0 && t.height > 0) {
                        b = new Rectangle(t);
                        rectCount++;
                    }
                } else {
                    t.setBounds(rectA.x, rectA.y, rectA.width, rectB.y - rectA.y);
                    if(t.width > 0 && t.height > 0) {
                        a = new Rectangle(t);
                        rectCount++;
                    }

                    t.setBounds(rectA.x, rectB.y, rectB.x - rectA.x,
                                rectB.height);
                    if(t.width > 0 && t.height > 0) {
                        b = new Rectangle(t);
                        rectCount++;
                    }

                    t.setBounds(rectA.x, (rectB.y + rectB.height), rectA.width,
                                (rectA.y + rectA.height) - (rectB.y + rectB.height));
                    if(t.width > 0 && t.height > 0) {
                        c = new Rectangle(t);
                        rectCount++;
                    }
                }
            } else if (rectB.x >= rectA.x && (rectB.x + rectB.width) <= (rectA.x + rectA.width)) {
                if (rectB.y <= rectA.y && (rectB.y + rectB.height) > (rectA.y + rectA.height)) {
                    t.setBounds(rectA.x, rectA.y, rectB.x - rectA.x, rectA.height);
                    if(t.width > 0 && t.height > 0) {
                        a = new Rectangle(t);
                        rectCount++;
                    }
                    t.setBounds((rectB.x + rectB.width), rectA.y,
                                (rectA.x + rectA.width) - (rectB.x + rectB.width), rectA.height);
                    if(t.width > 0 && t.height > 0) {
                        b = new Rectangle(t);
                        rectCount++;
                    }
                } else if (rectB.y <= rectA.y) {
                    t.setBounds(rectA.x, rectA.y, rectB.x - rectA.x, rectA.height);
                    if(t.width > 0 && t.height > 0) {
                        a = new Rectangle(t);
                        rectCount++;
                    }

                    t.setBounds(rectB.x, (rectB.y + rectB.height),
                                rectB.width,
                                (rectA.y + rectA.height) - (rectB.y + rectB.height));
                    if(t.width > 0 && t.height > 0) {
                        b = new Rectangle(t);
                        rectCount++;
                    }

                    t.setBounds((rectB.x + rectB.width), rectA.y,
                                (rectA.x + rectA.width) - (rectB.x + rectB.width), rectA.height);
                    if(t.width > 0 && t.height > 0) {
                        c = new Rectangle(t);
                        rectCount++;
                    }
                } else {
                    t.setBounds(rectA.x, rectA.y, rectB.x - rectA.x, rectA.height);
                    if(t.width > 0 && t.height > 0) {
                        a = new Rectangle(t);
                        rectCount++;
                    }

                    t.setBounds(rectB.x, rectA.y, rectB.width,
                                rectB.y - rectA.y);
                    if(t.width > 0 && t.height > 0) {
                        b = new Rectangle(t);
                        rectCount++;
                    }

                    t.setBounds((rectB.x + rectB.width), rectA.y,
                                (rectA.x + rectA.width) - (rectB.x + rectB.width), rectA.height);
                    if(t.width > 0 && t.height > 0) {
                        c = new Rectangle(t);
                        rectCount++;
                    }
                }
            }
        }

        result = new Rectangle[rectCount];
        rectCount = 0;
        if(a != null)
            result[rectCount++] = a;
        if(b != null)
            result[rectCount++] = b;
        if(c != null)
            result[rectCount++] = c;
        if(d != null)
            result[rectCount++] = d;
        return result;
    }

    /**
     * Returns true if the mouse event specifies the left mouse button.
     *
     * <p>
     *  如果鼠标事件指定了鼠标左键,则返回true。
     * 
     * 
     * @param anEvent  a MouseEvent object
     * @return true if the left mouse button was active
     */
    public static boolean isLeftMouseButton(MouseEvent anEvent) {
         return ((anEvent.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0 ||
                 anEvent.getButton() == MouseEvent.BUTTON1);
    }

    /**
     * Returns true if the mouse event specifies the middle mouse button.
     *
     * <p>
     *  如果鼠标事件指定中间鼠标按钮,则返回true。
     * 
     * 
     * @param anEvent  a MouseEvent object
     * @return true if the middle mouse button was active
     */
    public static boolean isMiddleMouseButton(MouseEvent anEvent) {
        return ((anEvent.getModifiersEx() & InputEvent.BUTTON2_DOWN_MASK) != 0 ||
                anEvent.getButton() == MouseEvent.BUTTON2);
    }

    /**
     * Returns true if the mouse event specifies the right mouse button.
     *
     * <p>
     *  如果鼠标事件指定了鼠标右键,则返回true。
     * 
     * 
     * @param anEvent  a MouseEvent object
     * @return true if the right mouse button was active
     */
    public static boolean isRightMouseButton(MouseEvent anEvent) {
        return ((anEvent.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK) != 0 ||
                anEvent.getButton() == MouseEvent.BUTTON3);
    }

    /**
     * Compute the width of the string using a font with the specified
     * "metrics" (sizes).
     *
     * <p>
     *  使用具有指定"指标"(大小)的字体计算字符串的宽度。
     * 
     * 
     * @param fm   a FontMetrics object to compute with
     * @param str  the String to compute
     * @return an int containing the string width
     */
    public static int computeStringWidth(FontMetrics fm,String str) {
        // You can't assume that a string's width is the sum of its
        // characters' widths in Java2D -- it may be smaller due to
        // kerning, etc.
        return SwingUtilities2.stringWidth(null, fm, str);
    }

    /**
     * Compute and return the location of the icons origin, the
     * location of origin of the text baseline, and a possibly clipped
     * version of the compound labels string.  Locations are computed
     * relative to the viewR rectangle.
     * The JComponents orientation (LEADING/TRAILING) will also be taken
     * into account and translated into LEFT/RIGHT values accordingly.
     * <p>
     * 计算并返回图标原点的位置,文本基线的原点位置,以及复合标签字符串的可能剪切版本。相对于viewR矩形计算位置。
     *  JComponents方向(LEADING / TRAILING)也将被考虑,并相应地转换为LEFT / RIGHT值。
     * 
     */
    public static String layoutCompoundLabel(JComponent c,
                                             FontMetrics fm,
                                             String text,
                                             Icon icon,
                                             int verticalAlignment,
                                             int horizontalAlignment,
                                             int verticalTextPosition,
                                             int horizontalTextPosition,
                                             Rectangle viewR,
                                             Rectangle iconR,
                                             Rectangle textR,
                                             int textIconGap)
    {
        boolean orientationIsLeftToRight = true;
        int     hAlign = horizontalAlignment;
        int     hTextPos = horizontalTextPosition;

        if (c != null) {
            if (!(c.getComponentOrientation().isLeftToRight())) {
                orientationIsLeftToRight = false;
            }
        }

        // Translate LEADING/TRAILING values in horizontalAlignment
        // to LEFT/RIGHT values depending on the components orientation
        switch (horizontalAlignment) {
        case LEADING:
            hAlign = (orientationIsLeftToRight) ? LEFT : RIGHT;
            break;
        case TRAILING:
            hAlign = (orientationIsLeftToRight) ? RIGHT : LEFT;
            break;
        }

        // Translate LEADING/TRAILING values in horizontalTextPosition
        // to LEFT/RIGHT values depending on the components orientation
        switch (horizontalTextPosition) {
        case LEADING:
            hTextPos = (orientationIsLeftToRight) ? LEFT : RIGHT;
            break;
        case TRAILING:
            hTextPos = (orientationIsLeftToRight) ? RIGHT : LEFT;
            break;
        }

        return layoutCompoundLabelImpl(c,
                                       fm,
                                       text,
                                       icon,
                                       verticalAlignment,
                                       hAlign,
                                       verticalTextPosition,
                                       hTextPos,
                                       viewR,
                                       iconR,
                                       textR,
                                       textIconGap);
    }

    /**
     * Compute and return the location of the icons origin, the
     * location of origin of the text baseline, and a possibly clipped
     * version of the compound labels string.  Locations are computed
     * relative to the viewR rectangle.
     * This layoutCompoundLabel() does not know how to handle LEADING/TRAILING
     * values in horizontalTextPosition (they will default to RIGHT) and in
     * horizontalAlignment (they will default to CENTER).
     * Use the other version of layoutCompoundLabel() instead.
     * <p>
     *  计算并返回图标原点的位置,文本基线的原点位置,以及复合标签字符串的可能剪切版本。相对于viewR矩形计算位置。
     * 这个layoutCompoundLabel()不知道如何处理horizo​​ntalTextPosition中的LEADING / TRAILING值(它们将默认为RIGHT)和horizo​​ntal
     * Alignment(它们将默认为CENTER)。
     *  计算并返回图标原点的位置,文本基线的原点位置,以及复合标签字符串的可能剪切版本。相对于viewR矩形计算位置。请改用其他版本的layoutCompoundLabel()。
     * 
     */
    public static String layoutCompoundLabel(
        FontMetrics fm,
        String text,
        Icon icon,
        int verticalAlignment,
        int horizontalAlignment,
        int verticalTextPosition,
        int horizontalTextPosition,
        Rectangle viewR,
        Rectangle iconR,
        Rectangle textR,
        int textIconGap)
    {
        return layoutCompoundLabelImpl(null, fm, text, icon,
                                       verticalAlignment,
                                       horizontalAlignment,
                                       verticalTextPosition,
                                       horizontalTextPosition,
                                       viewR, iconR, textR, textIconGap);
    }

    /**
     * Compute and return the location of the icons origin, the
     * location of origin of the text baseline, and a possibly clipped
     * version of the compound labels string.  Locations are computed
     * relative to the viewR rectangle.
     * This layoutCompoundLabel() does not know how to handle LEADING/TRAILING
     * values in horizontalTextPosition (they will default to RIGHT) and in
     * horizontalAlignment (they will default to CENTER).
     * Use the other version of layoutCompoundLabel() instead.
     * <p>
     *  计算并返回图标原点的位置,文本基线的原点位置,以及复合标签字符串的可能剪切版本。相对于viewR矩形计算位置。
     * 这个layoutCompoundLabel()不知道如何处理horizo​​ntalTextPosition中的LEADING / TRAILING值(它们将默认为RIGHT)和horizo​​ntal
     * Alignment(它们将默认为CENTER)。
     *  计算并返回图标原点的位置,文本基线的原点位置,以及复合标签字符串的可能剪切版本。相对于viewR矩形计算位置。请改用其他版本的layoutCompoundLabel()。
     * 
     */
    private static String layoutCompoundLabelImpl(
        JComponent c,
        FontMetrics fm,
        String text,
        Icon icon,
        int verticalAlignment,
        int horizontalAlignment,
        int verticalTextPosition,
        int horizontalTextPosition,
        Rectangle viewR,
        Rectangle iconR,
        Rectangle textR,
        int textIconGap)
    {
        /* Initialize the icon bounds rectangle iconR.
        /* <p>
         */

        if (icon != null) {
            iconR.width = icon.getIconWidth();
            iconR.height = icon.getIconHeight();
        }
        else {
            iconR.width = iconR.height = 0;
        }

        /* Initialize the text bounds rectangle textR.  If a null
         * or and empty String was specified we substitute "" here
         * and use 0,0,0,0 for textR.
         * <p>
         *  或者指定空字符串,我们用""替换,并为textR使用0,0,0,0。
         * 
         */

        boolean textIsEmpty = (text == null) || text.equals("");
        int lsb = 0;
        int rsb = 0;
        /* Unless both text and icon are non-null, we effectively ignore
         * the value of textIconGap.
         * <p>
         *  textIconGap的值。
         * 
         */
        int gap;

        View v;
        if (textIsEmpty) {
            textR.width = textR.height = 0;
            text = "";
            gap = 0;
        }
        else {
            int availTextWidth;
            gap = (icon == null) ? 0 : textIconGap;

            if (horizontalTextPosition == CENTER) {
                availTextWidth = viewR.width;
            }
            else {
                availTextWidth = viewR.width - (iconR.width + gap);
            }
            v = (c != null) ? (View) c.getClientProperty("html") : null;
            if (v != null) {
                textR.width = Math.min(availTextWidth,
                                       (int) v.getPreferredSpan(View.X_AXIS));
                textR.height = (int) v.getPreferredSpan(View.Y_AXIS);
            } else {
                textR.width = SwingUtilities2.stringWidth(c, fm, text);
                lsb = SwingUtilities2.getLeftSideBearing(c, fm, text);
                if (lsb < 0) {
                    // If lsb is negative, add it to the width and later
                    // adjust the x location. This gives more space than is
                    // actually needed.
                    // This is done like this for two reasons:
                    // 1. If we set the width to the actual bounds all
                    //    callers would have to account for negative lsb
                    //    (pref size calculations ONLY look at width of
                    //    textR)
                    // 2. You can do a drawString at the returned location
                    //    and the text won't be clipped.
                    textR.width -= lsb;
                }
                if (textR.width > availTextWidth) {
                    text = SwingUtilities2.clipString(c, fm, text,
                                                      availTextWidth);
                    textR.width = SwingUtilities2.stringWidth(c, fm, text);
                }
                textR.height = fm.getHeight();
            }
        }


        /* Compute textR.x,y given the verticalTextPosition and
         * horizontalTextPosition properties
         * <p>
         *  horizo​​ntalTextPosition属性
         * 
         */

        if (verticalTextPosition == TOP) {
            if (horizontalTextPosition != CENTER) {
                textR.y = 0;
            }
            else {
                textR.y = -(textR.height + gap);
            }
        }
        else if (verticalTextPosition == CENTER) {
            textR.y = (iconR.height / 2) - (textR.height / 2);
        }
        else { // (verticalTextPosition == BOTTOM)
            if (horizontalTextPosition != CENTER) {
                textR.y = iconR.height - textR.height;
            }
            else {
                textR.y = (iconR.height + gap);
            }
        }

        if (horizontalTextPosition == LEFT) {
            textR.x = -(textR.width + gap);
        }
        else if (horizontalTextPosition == CENTER) {
            textR.x = (iconR.width / 2) - (textR.width / 2);
        }
        else { // (horizontalTextPosition == RIGHT)
            textR.x = (iconR.width + gap);
        }

        // WARNING: DefaultTreeCellEditor uses a shortened version of
        // this algorithm to position it's Icon. If you change how this
        // is calculated, be sure and update DefaultTreeCellEditor too.

        /* labelR is the rectangle that contains iconR and textR.
         * Move it to its proper position given the labelAlignment
         * properties.
         *
         * To avoid actually allocating a Rectangle, Rectangle.union
         * has been inlined below.
         * <p>
         *  给定labelAlignment属性,将其移动到正确的位置。
         * 
         * 为了避免实际分配一个Rectangle,Rectangle.union已经在下面内联。
         * 
         */
        int labelR_x = Math.min(iconR.x, textR.x);
        int labelR_width = Math.max(iconR.x + iconR.width,
                                    textR.x + textR.width) - labelR_x;
        int labelR_y = Math.min(iconR.y, textR.y);
        int labelR_height = Math.max(iconR.y + iconR.height,
                                     textR.y + textR.height) - labelR_y;

        int dx, dy;

        if (verticalAlignment == TOP) {
            dy = viewR.y - labelR_y;
        }
        else if (verticalAlignment == CENTER) {
            dy = (viewR.y + (viewR.height / 2)) - (labelR_y + (labelR_height / 2));
        }
        else { // (verticalAlignment == BOTTOM)
            dy = (viewR.y + viewR.height) - (labelR_y + labelR_height);
        }

        if (horizontalAlignment == LEFT) {
            dx = viewR.x - labelR_x;
        }
        else if (horizontalAlignment == RIGHT) {
            dx = (viewR.x + viewR.width) - (labelR_x + labelR_width);
        }
        else { // (horizontalAlignment == CENTER)
            dx = (viewR.x + (viewR.width / 2)) -
                 (labelR_x + (labelR_width / 2));
        }

        /* Translate textR and glypyR by dx,dy.
        /* <p>
         */

        textR.x += dx;
        textR.y += dy;

        iconR.x += dx;
        iconR.y += dy;

        if (lsb < 0) {
            // lsb is negative. Shift the x location so that the text is
            // visually drawn at the right location.
            textR.x -= lsb;

            textR.width += lsb;
        }
        if (rsb > 0) {
            textR.width -= rsb;
        }

        return text;
    }


    /**
     * Paints a component to the specified <code>Graphics</code>.
     * This method is primarily useful to render
     * <code>Component</code>s that don't exist as part of the visible
     * containment hierarchy, but are used for rendering.  For
     * example, if you are doing your own rendering and want to render
     * some text (or even HTML), you could make use of
     * <code>JLabel</code>'s text rendering support and have it paint
     * directly by way of this method, without adding the label to the
     * visible containment hierarchy.
     * <p>
     * This method makes use of <code>CellRendererPane</code> to handle
     * the actual painting, and is only recommended if you use one
     * component for rendering.  If you make use of multiple components
     * to handle the rendering, as <code>JTable</code> does, use
     * <code>CellRendererPane</code> directly.  Otherwise, as described
     * below, you could end up with a <code>CellRendererPane</code>
     * per <code>Component</code>.
     * <p>
     * If <code>c</code>'s parent is not a <code>CellRendererPane</code>,
     * a new <code>CellRendererPane</code> is created, <code>c</code> is
     * added to it, and the <code>CellRendererPane</code> is added to
     * <code>p</code>.  If <code>c</code>'s parent is a
     * <code>CellRendererPane</code> and the <code>CellRendererPane</code>s
     * parent is not <code>p</code>, it is added to <code>p</code>.
     * <p>
     * The component should either descend from <code>JComponent</code>
     * or be another kind of lightweight component.
     * A lightweight component is one whose "lightweight" property
     * (returned by the <code>Component</code>
     * <code>isLightweight</code> method)
     * is true. If the Component is not lightweight, bad things map happen:
     * crashes, exceptions, painting problems...
     *
     * <p>
     *  将组件绘制到指定的<code> Graphics </code>。此方法主要用于呈现不存在作为可见的包含层次结构的一部分但用于呈现的<code> Component </code>。
     * 例如,如果你正在做自己的渲染,并想渲染一些文本(甚至HTML),你可以使用<code> JLabel </code>的文本渲染支持,并直接通过这种方法,而不将标签添加到可见的包含层次结构中。
     * <p>
     *  此方法使用<code> CellRendererPane </code>来处理实际绘制,并且仅在使用一个组件进行渲染时才推荐。
     * 如果你使用多个组件来处理渲染,就像<code> JTable </code>那样,直接使用<code> CellRendererPane </code>。
     * 否则,如下所述,你最终可以用<code> CellRendererPane </code>每个<code> Component </code>。
     * <p>
     *  如果<code> c </code>的父代不是<code> CellRendererPane </code>,则创建新的<code> CellRendererPane </code>,<code> c
     *  </code>并将<code> CellRendererPane </code>添加到<code> p </code>。
     * 如果<code> c </code>的父代码是<code> CellRendererPane </code>,而<code> CellRendererPane </code>的父代码不是<code> p
     *  </code> code> p </code>。
     * <p>
     * 该组件应该从<code> JComponent </code>下降,或者是另一种轻量级组件。
     * 轻量级组件是其"轻量级"属性(由<code> Component </code> <code> isLightweight </code>方法返回)为true的组件。
     * 如果组件不是轻量级的,坏东西映射发生：崩溃,异常,绘画问题...。
     * 
     * 
     * @param g  the <code>Graphics</code> object to draw on
     * @param c  the <code>Component</code> to draw
     * @param p  the intermediate <code>Container</code>
     * @param x  an int specifying the left side of the area draw in, in pixels,
     *           measured from the left edge of the graphics context
     * @param y  an int specifying the top of the area to draw in, in pixels
     *           measured down from the top edge of the graphics context
     * @param w  an int specifying the width of the area draw in, in pixels
     * @param h  an int specifying the height of the area draw in, in pixels
     *
     * @see CellRendererPane
     * @see java.awt.Component#isLightweight
     */
    public static void paintComponent(Graphics g, Component c, Container p, int x, int y, int w, int h) {
        getCellRendererPane(c, p).paintComponent(g, c, p, x, y, w, h,false);
    }

    /**
     * Paints a component to the specified <code>Graphics</code>.  This
     * is a cover method for
     * {@link #paintComponent(Graphics,Component,Container,int,int,int,int)}.
     * Refer to it for more information.
     *
     * <p>
     *  将组件绘制到指定的<code> Graphics </code>。
     * 这是{@link #paintComponent(Graphics,Component,Container,int,int,int,int)}的覆盖方法。有关详细信息,请参阅。
     * 
     * 
     * @param g  the <code>Graphics</code> object to draw on
     * @param c  the <code>Component</code> to draw
     * @param p  the intermediate <code>Container</code>
     * @param r  the <code>Rectangle</code> to draw in
     *
     * @see #paintComponent(Graphics,Component,Container,int,int,int,int)
     * @see CellRendererPane
     */
    public static void paintComponent(Graphics g, Component c, Container p, Rectangle r) {
        paintComponent(g, c, p, r.x, r.y, r.width, r.height);
    }


    /*
     * Ensures that cell renderer <code>c</code> has a
     * <code>ComponentShell</code> parent and that
     * the shell's parent is p.
     * <p>
     *  确保单元格渲染器<code> c </code>有一个<code> ComponentShell </code>父类,shell的父类为p。
     * 
     */
    private static CellRendererPane getCellRendererPane(Component c, Container p) {
        Container shell = c.getParent();
        if (shell instanceof CellRendererPane) {
            if (shell.getParent() != p) {
                p.add(shell);
            }
        } else {
            shell = new CellRendererPane();
            shell.add(c);
            p.add(shell);
        }
        return (CellRendererPane)shell;
    }

    /**
     * A simple minded look and feel change: ask each node in the tree
     * to <code>updateUI()</code> -- that is, to initialize its UI property
     * with the current look and feel.
     * <p>
     *  一个简单的头脑外观和感觉的变化：问树中的每个节点<code> updateUI()</code>  - 也就是,用当前的外观和感觉来初始化其UI属性。
     * 
     */
    public static void updateComponentTreeUI(Component c) {
        updateComponentTreeUI0(c);
        c.invalidate();
        c.validate();
        c.repaint();
    }

    private static void updateComponentTreeUI0(Component c) {
        if (c instanceof JComponent) {
            JComponent jc = (JComponent) c;
            jc.updateUI();
            JPopupMenu jpm =jc.getComponentPopupMenu();
            if(jpm != null) {
                updateComponentTreeUI(jpm);
            }
        }
        Component[] children = null;
        if (c instanceof JMenu) {
            children = ((JMenu)c).getMenuComponents();
        }
        else if (c instanceof Container) {
            children = ((Container)c).getComponents();
        }
        if (children != null) {
            for (Component child : children) {
                updateComponentTreeUI0(child);
            }
        }
    }


    /**
     * Causes <i>doRun.run()</i> to be executed asynchronously on the
     * AWT event dispatching thread.  This will happen after all
     * pending AWT events have been processed.  This method should
     * be used when an application thread needs to update the GUI.
     * In the following example the <code>invokeLater</code> call queues
     * the <code>Runnable</code> object <code>doHelloWorld</code>
     * on the event dispatching thread and
     * then prints a message.
     * <pre>
     * Runnable doHelloWorld = new Runnable() {
     *     public void run() {
     *         System.out.println("Hello World on " + Thread.currentThread());
     *     }
     * };
     *
     * SwingUtilities.invokeLater(doHelloWorld);
     * System.out.println("This might well be displayed before the other message.");
     * </pre>
     * If invokeLater is called from the event dispatching thread --
     * for example, from a JButton's ActionListener -- the <i>doRun.run()</i> will
     * still be deferred until all pending events have been processed.
     * Note that if the <i>doRun.run()</i> throws an uncaught exception
     * the event dispatching thread will unwind (not the current thread).
     * <p>
     * Additional documentation and examples for this method can be
     * found in
     * <A HREF="https://docs.oracle.com/javase/tutorial/uiswing/concurrency/index.html">Concurrency in Swing</a>.
     * <p>
     * As of 1.3 this method is just a cover for <code>java.awt.EventQueue.invokeLater()</code>.
     * <p>
     * Unlike the rest of Swing, this method can be invoked from any thread.
     *
     * <p>
     *  导致在AWT事件分派线程上异步执行<i> doRun.run()</i>。这将在所有待处理的AWT事件被处理后发生。当应用程序线程需要更新GUI时,应使用此方法。
     * 在以下示例中,<code> invokeLater </code>调用将事件分派线程上的<code> Runnable </code>对象<code> doHelloWorld </code>排队,然后
     * 打印一条消息。
     *  导致在AWT事件分派线程上异步执行<i> doRun.run()</i>。这将在所有待处理的AWT事件被处理后发生。当应用程序线程需要更新GUI时,应使用此方法。
     * <pre>
     *  Runnable doHelloWorld = new Runnable(){public void run(){System.out.println("Hello World on"+ Thread.currentThread()); }
     * };。
     * 
     * SwingUtilities.invokeLater(doHelloWorld); System.out.println("这可能会显示在其他消息之前。
     * </pre>
     *  如果从事件分派线程调用​​invokeLater(例如,从JButton的ActionListener调用),则仍将推迟<i> doRun.run()</i>,直到所有挂起的事件都被处理。
     * 注意,如果<i> doRun.run()</i>抛出未捕获的异常,事件分派线程将会解开(而不是当前线程)。
     * <p>
     *  有关此方法的其他文档和示例,请参见<A HREF="https://docs.oracle.com/javase/tutorial/uiswing/concurrency/index.html"> S
     * wing中的并发</a>。
     * <p>
     *  从1.3开始,这个方法只是一个覆盖<code> java.awt.EventQueue.invokeLater()</code>。
     * <p>
     *  与Swing的其余部分不同,此方法可以从任何线程调用。
     * 
     * 
     * @see #invokeAndWait
     */
    public static void invokeLater(Runnable doRun) {
        EventQueue.invokeLater(doRun);
    }


    /**
     * Causes <code>doRun.run()</code> to be executed synchronously on the
     * AWT event dispatching thread.  This call blocks until
     * all pending AWT events have been processed and (then)
     * <code>doRun.run()</code> returns. This method should
     * be used when an application thread needs to update the GUI.
     * It shouldn't be called from the event dispatching thread.
     * Here's an example that creates a new application thread
     * that uses <code>invokeAndWait</code> to print a string from the event
     * dispatching thread and then, when that's finished, print
     * a string from the application thread.
     * <pre>
     * final Runnable doHelloWorld = new Runnable() {
     *     public void run() {
     *         System.out.println("Hello World on " + Thread.currentThread());
     *     }
     * };
     *
     * Thread appThread = new Thread() {
     *     public void run() {
     *         try {
     *             SwingUtilities.invokeAndWait(doHelloWorld);
     *         }
     *         catch (Exception e) {
     *             e.printStackTrace();
     *         }
     *         System.out.println("Finished on " + Thread.currentThread());
     *     }
     * };
     * appThread.start();
     * </pre>
     * Note that if the <code>Runnable.run</code> method throws an
     * uncaught exception
     * (on the event dispatching thread) it's caught and rethrown, as
     * an <code>InvocationTargetException</code>, on the caller's thread.
     * <p>
     * Additional documentation and examples for this method can be
     * found in
     * <A HREF="https://docs.oracle.com/javase/tutorial/uiswing/concurrency/index.html">Concurrency in Swing</a>.
     * <p>
     * As of 1.3 this method is just a cover for
     * <code>java.awt.EventQueue.invokeAndWait()</code>.
     *
     * <p>
     *  导致<code> doRun.run()</code>在AWT事件分派线程上同步执行。此调用阻塞,直到所有待处理的AWT事件被处理,然后<code> doRun.run()</code>返回。
     * 当应用程序线程需要更新GUI时,应使用此方法。它不应该从事件调度线程调用。
     * 这里有一个例子,创建一个新的应用程序线程,使用<code> invokeAndWait </code>从事件分发线程打印一个字符串,然后,当它完成后,从应用程序线程打印一个字符串。
     * <pre>
     * 最后Runnable doHelloWorld = new Runnable(){public void run(){System.out.println("Hello World on"+ Thread.currentThread()); }
     * };。
     * 
     *  线程appThread = new Thread(){public void run(){try {SwingUtilities.invokeAndWait(doHelloWorld); } catc
     * h(Exception e){e.printStackTrace(); } System.out.println("Finished on"+ Thread.currentThread()); }}; 
     * appThread.start();。
     * </pre>
     *  注意,如果<code> Runnable.run </code>方法抛出一个未捕获的异常(在事件分派线程上),它会作为<code> InvocationTargetException </code>在
     * 调用者的线程上被捕获和重新抛出。
     * <p>
     *  有关此方法的其他文档和示例,请参见<A HREF="https://docs.oracle.com/javase/tutorial/uiswing/concurrency/index.html"> S
     * wing中的并发</a>。
     * <p>
     *  从1.3开始,这个方法只是一个覆盖<code> java.awt.EventQueue.invokeAndWait()</code>。
     * 
     * 
     * @exception  InterruptedException if we're interrupted while waiting for
     *             the event dispatching thread to finish executing
     *             <code>doRun.run()</code>
     * @exception  InvocationTargetException  if an exception is thrown
     *             while running <code>doRun</code>
     *
     * @see #invokeLater
     */
    public static void invokeAndWait(final Runnable doRun)
        throws InterruptedException, InvocationTargetException
    {
        EventQueue.invokeAndWait(doRun);
    }

    /**
     * Returns true if the current thread is an AWT event dispatching thread.
     * <p>
     * As of 1.3 this method is just a cover for
     * <code>java.awt.EventQueue.isDispatchThread()</code>.
     *
     * <p>
     *  如果当前线程是AWT事件分派线程,则返回true。
     * <p>
     *  从1.3开始,这个方法只是一个覆盖<code> java.awt.EventQueue.isDispatchThread()</code>。
     * 
     * 
     * @return true if the current thread is an AWT event dispatching thread
     */
    public static boolean isEventDispatchThread()
    {
        return EventQueue.isDispatchThread();
    }


    /*
     * --- Accessibility Support ---
     *
     * <p>
     *  ---辅助功能
     * 
     */

    /**
     * Get the index of this object in its accessible parent.<p>
     *
     * Note: as of the Java 2 platform v1.3, it is recommended that developers call
     * Component.AccessibleAWTComponent.getAccessibleIndexInParent() instead
     * of using this method.
     *
     * <p>
     *  在其可访问的父代中获取此对象的索引。<p>
     * 
     *  注意：从Java 2平台v1.3开始,建议开发人员调用Component.AccessibleAWTComponent.getAccessibleIndexInParent(),而不是使用此方法。
     * 
     * 
     * @return -1 of this object does not have an accessible parent.
     * Otherwise, the index of the child in its accessible parent.
     */
    public static int getAccessibleIndexInParent(Component c) {
        return c.getAccessibleContext().getAccessibleIndexInParent();
    }

    /**
     * Returns the <code>Accessible</code> child contained at the
     * local coordinate <code>Point</code>, if one exists.
     * Otherwise returns <code>null</code>.
     *
     * <p>
     *  返回包含在本地坐标<code> Point </code>(如果存在)中的<code> Accessible </code>子代。否则返回<code> null </code>。
     * 
     * 
     * @return the <code>Accessible</code> at the specified location,
     *    if it exists; otherwise <code>null</code>
     */
    public static Accessible getAccessibleAt(Component c, Point p) {
        if (c instanceof Container) {
            return c.getAccessibleContext().getAccessibleComponent().getAccessibleAt(p);
        } else if (c instanceof Accessible) {
            Accessible a = (Accessible) c;
            if (a != null) {
                AccessibleContext ac = a.getAccessibleContext();
                if (ac != null) {
                    AccessibleComponent acmp;
                    Point location;
                    int nchildren = ac.getAccessibleChildrenCount();
                    for (int i=0; i < nchildren; i++) {
                        a = ac.getAccessibleChild(i);
                        if ((a != null)) {
                            ac = a.getAccessibleContext();
                            if (ac != null) {
                                acmp = ac.getAccessibleComponent();
                                if ((acmp != null) && (acmp.isShowing())) {
                                    location = acmp.getLocation();
                                    Point np = new Point(p.x-location.x,
                                                         p.y-location.y);
                                    if (acmp.contains(np)){
                                        return a;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return (Accessible) c;
        }
        return null;
    }

    /**
     * Get the state of this object. <p>
     *
     * Note: as of the Java 2 platform v1.3, it is recommended that developers call
     * Component.AccessibleAWTComponent.getAccessibleIndexInParent() instead
     * of using this method.
     *
     * <p>
     *  获取此对象的状态。 <p>
     * 
     * 注意：从Java 2平台v1.3开始,建议开发人员调用Component.AccessibleAWTComponent.getAccessibleIndexInParent(),而不是使用此方法。
     * 
     * 
     * @return an instance of AccessibleStateSet containing the current state
     * set of the object
     * @see AccessibleState
     */
    public static AccessibleStateSet getAccessibleStateSet(Component c) {
        return c.getAccessibleContext().getAccessibleStateSet();
    }

    /**
     * Returns the number of accessible children in the object.  If all
     * of the children of this object implement Accessible, than this
     * method should return the number of children of this object. <p>
     *
     * Note: as of the Java 2 platform v1.3, it is recommended that developers call
     * Component.AccessibleAWTComponent.getAccessibleIndexInParent() instead
     * of using this method.
     *
     * <p>
     *  返回对象中可访问的子项数。如果这个对象的所有子对象实现Accessible,那么这个方法应该返回这个对象的子对象数。 <p>
     * 
     *  注意：从Java 2平台v1.3开始,建议开发人员调用Component.AccessibleAWTComponent.getAccessibleIndexInParent(),而不是使用此方法。
     * 
     * 
     * @return the number of accessible children in the object.
     */
    public static int getAccessibleChildrenCount(Component c) {
        return c.getAccessibleContext().getAccessibleChildrenCount();
    }

    /**
     * Return the nth Accessible child of the object. <p>
     *
     * Note: as of the Java 2 platform v1.3, it is recommended that developers call
     * Component.AccessibleAWTComponent.getAccessibleIndexInParent() instead
     * of using this method.
     *
     * <p>
     *  返回对象的第n个Accessible子项。 <p>
     * 
     *  注意：从Java 2平台v1.3开始,建议开发人员调用Component.AccessibleAWTComponent.getAccessibleIndexInParent(),而不是使用此方法。
     * 
     * 
     * @param i zero-based index of child
     * @return the nth Accessible child of the object
     */
    public static Accessible getAccessibleChild(Component c, int i) {
        return c.getAccessibleContext().getAccessibleChild(i);
    }

    /**
     * Return the child <code>Component</code> of the specified
     * <code>Component</code> that is the focus owner, if any.
     *
     * <p>
     *  返回指定的<code> Component </code>(即焦点所有者)(如果有的话)的<code> Component </code>
     * 
     * 
     * @param c the root of the <code>Component</code> hierarchy to
     *        search for the focus owner
     * @return the focus owner, or <code>null</code> if there is no focus
     *         owner, or if the focus owner is not <code>comp</code>, or a
     *         descendant of <code>comp</code>
     *
     * @see java.awt.KeyboardFocusManager#getFocusOwner
     * @deprecated As of 1.4, replaced by
     *   <code>KeyboardFocusManager.getFocusOwner()</code>.
     */
    @Deprecated
    public static Component findFocusOwner(Component c) {
        Component focusOwner = KeyboardFocusManager.
            getCurrentKeyboardFocusManager().getFocusOwner();

        // verify focusOwner is a descendant of c
        for (Component temp = focusOwner; temp != null;
             temp = (temp instanceof Window) ? null : temp.getParent())
        {
            if (temp == c) {
                return focusOwner;
            }
        }

        return null;
    }

    /**
     * If c is a JRootPane descendant return its JRootPane ancestor.
     * If c is a RootPaneContainer then return its JRootPane.
     * <p>
     *  如果c是JRootPane后代,则返回其JRootPane祖先。如果c是RootPaneContainer,则返回其JRootPane。
     * 
     * 
     * @return the JRootPane for Component c or {@code null}.
     */
    public static JRootPane getRootPane(Component c) {
        if (c instanceof RootPaneContainer) {
            return ((RootPaneContainer)c).getRootPane();
        }
        for( ; c != null; c = c.getParent()) {
            if (c instanceof JRootPane) {
                return (JRootPane)c;
            }
        }
        return null;
    }


    /**
     * Returns the root component for the current component tree.
     * <p>
     *  返回当前组件树的根组件。
     * 
     * 
     * @return the first ancestor of c that's a Window or the last Applet ancestor
     */
    public static Component getRoot(Component c) {
        Component applet = null;
        for(Component p = c; p != null; p = p.getParent()) {
            if (p instanceof Window) {
                return p;
            }
            if (p instanceof Applet) {
                applet = p;
            }
        }
        return applet;
    }

    static JComponent getPaintingOrigin(JComponent c) {
        Container p = c;
        while ((p = p.getParent()) instanceof JComponent) {
            JComponent jp = (JComponent) p;
            if (jp.isPaintingOrigin()) {
                return jp;
            }
        }
        return null;
    }

    /**
     * Process the key bindings for the <code>Component</code> associated with
     * <code>event</code>. This method is only useful if
     * <code>event.getComponent()</code> does not descend from
     * <code>JComponent</code>, or your are not invoking
     * <code>super.processKeyEvent</code> from within your
     * <code>JComponent</code> subclass. <code>JComponent</code>
     * automatically processes bindings from within its
     * <code>processKeyEvent</code> method, hence you rarely need
     * to directly invoke this method.
     *
     * <p>
     * 处理与<code> event </code>关联的<code> Component </code>的键绑定。
     * 此方法仅在<code> event.getComponent()</code>不从<code> JComponent </code>下降时才有用,或者您没有从</code>中调用<code> super
     * .processKeyEvent </code>代码> JComponent </code>子类。
     * 处理与<code> event </code>关联的<code> Component </code>的键绑定。
     *  <code> JComponent </code>自动处理其<code> processKeyEvent </code>方法中的绑定,因此很少需要直接调用此方法。
     * 
     * 
     * @param event KeyEvent used to identify which bindings to process, as
     *              well as which Component has focus.
     * @return true if a binding has found and processed
     * @since 1.4
     */
    public static boolean processKeyBindings(KeyEvent event) {
        if (event != null) {
            if (event.isConsumed()) {
                return false;
            }

            Component component = event.getComponent();
            boolean pressed = (event.getID() == KeyEvent.KEY_PRESSED);

            if (!isValidKeyEventForKeyBindings(event)) {
                return false;
            }
            // Find the first JComponent in the ancestor hierarchy, and
            // invoke processKeyBindings on it
            while (component != null) {
                if (component instanceof JComponent) {
                    return ((JComponent)component).processKeyBindings(
                                                   event, pressed);
                }
                if ((component instanceof Applet) ||
                    (component instanceof Window)) {
                    // No JComponents, if Window or Applet parent, process
                    // WHEN_IN_FOCUSED_WINDOW bindings.
                    return JComponent.processKeyBindingsForAllComponents(
                                  event, (Container)component, pressed);
                }
                component = component.getParent();
            }
        }
        return false;
    }

    /**
     * Returns true if the <code>e</code> is a valid KeyEvent to use in
     * processing the key bindings associated with JComponents.
     * <p>
     *  如果<code> e </code>是用于处理与JComponent相关联的键绑定的有效KeyEvent,则返回true。
     * 
     */
    static boolean isValidKeyEventForKeyBindings(KeyEvent e) {
        return true;
    }

    /**
     * Invokes <code>actionPerformed</code> on <code>action</code> if
     * <code>action</code> is enabled (and non-{@code null}). The command for the
     * ActionEvent is determined by:
     * <ol>
     *   <li>If the action was registered via
     *       <code>registerKeyboardAction</code>, then the command string
     *       passed in ({@code null} will be used if {@code null} was passed in).
     *   <li>Action value with name Action.ACTION_COMMAND_KEY, unless {@code null}.
     *   <li>String value of the KeyEvent, unless <code>getKeyChar</code>
     *       returns KeyEvent.CHAR_UNDEFINED..
     * </ol>
     * This will return true if <code>action</code> is non-{@code null} and
     * actionPerformed is invoked on it.
     *
     * <p>
     *  如果启用了<code> action </code>(以及非 -  {@ code null}),则在<code> action </code>上调用<code> actionPerformed </code>
     * 。
     *  ActionEvent的命令由下式确定：。
     * <ol>
     *  <li>如果操作是通过<code> registerKeyboardAction </code>注册的,那么如果传递{@code null},将使用传递的命令字符串({@code null})。
     *  <li>操作值为Action.ACTION_COMMAND_KEY,除非{@code null}。
     *  <li> KeyEvent的字符串值,除非<code> getKeyChar </code>返回KeyEvent.CHAR_UNDEFINED ..。
     * </ol>
     *  如果<code> action </code>为非 -  {@ code null}并且调用了actionPerformed,则此操作将返回true。
     * 
     * 
     * @since 1.3
     */
    public static boolean notifyAction(Action action, KeyStroke ks,
                                       KeyEvent event, Object sender,
                                       int modifiers) {
        if (action == null) {
            return false;
        }
        if (action instanceof UIAction) {
            if (!((UIAction)action).isEnabled(sender)) {
                return false;
            }
        }
        else if (!action.isEnabled()) {
            return false;
        }
        Object commandO;
        boolean stayNull;

        // Get the command object.
        commandO = action.getValue(Action.ACTION_COMMAND_KEY);
        if (commandO == null && (action instanceof JComponent.ActionStandin)) {
            // ActionStandin is used for historical reasons to support
            // registerKeyboardAction with a null value.
            stayNull = true;
        }
        else {
            stayNull = false;
        }

        // Convert it to a string.
        String command;

        if (commandO != null) {
            command = commandO.toString();
        }
        else if (!stayNull && event.getKeyChar() != KeyEvent.CHAR_UNDEFINED) {
            command = String.valueOf(event.getKeyChar());
        }
        else {
            // Do null for undefined chars, or if registerKeyboardAction
            // was called with a null.
            command = null;
        }
        action.actionPerformed(new ActionEvent(sender,
                        ActionEvent.ACTION_PERFORMED, command, event.getWhen(),
                        modifiers));
        return true;
    }


    /**
     * Convenience method to change the UI InputMap for <code>component</code>
     * to <code>uiInputMap</code>. If <code>uiInputMap</code> is {@code null},
     * this removes any previously installed UI InputMap.
     *
     * <p>
     *  方便方法将<code> component </code>的InputMap更改为<code> uiInputMap </code>。
     * 如果<code> uiInputMap </code>是{@code null},则会删除任何以前安装的UI InputMap。
     * 
     * 
     * @since 1.3
     */
    public static void replaceUIInputMap(JComponent component, int type,
                                         InputMap uiInputMap) {
        InputMap map = component.getInputMap(type, (uiInputMap != null));

        while (map != null) {
            InputMap parent = map.getParent();
            if (parent == null || (parent instanceof UIResource)) {
                map.setParent(uiInputMap);
                return;
            }
            map = parent;
        }
    }


    /**
     * Convenience method to change the UI ActionMap for <code>component</code>
     * to <code>uiActionMap</code>. If <code>uiActionMap</code> is {@code null},
     * this removes any previously installed UI ActionMap.
     *
     * <p>
     * 方便方法将<code> component </code>的ActionMap更改为<code> uiActionMap </code>。
     * 如果<code> uiActionMap </code>是{@code null},则会删除任何先前安装的UI ActionMap。
     * 
     * 
     * @since 1.3
     */
    public static void replaceUIActionMap(JComponent component,
                                          ActionMap uiActionMap) {
        ActionMap map = component.getActionMap((uiActionMap != null));

        while (map != null) {
            ActionMap parent = map.getParent();
            if (parent == null || (parent instanceof UIResource)) {
                map.setParent(uiActionMap);
                return;
            }
            map = parent;
        }
    }


    /**
     * Returns the InputMap provided by the UI for condition
     * <code>condition</code> in component <code>component</code>.
     * <p>This will return {@code null} if the UI has not installed a InputMap
     * of the specified type.
     *
     * <p>
     *  返回由组件<code>组件</code>中条件<code> condition </code>的UI提供的InputMap。
     *  <p>如果UI尚未安装指定类型的InputMap,此操作将返回{@code null}。
     * 
     * 
     * @since 1.3
     */
    public static InputMap getUIInputMap(JComponent component, int condition) {
        InputMap map = component.getInputMap(condition, false);
        while (map != null) {
            InputMap parent = map.getParent();
            if (parent instanceof UIResource) {
                return parent;
            }
            map = parent;
        }
        return null;
    }

    /**
     * Returns the ActionMap provided by the UI
     * in component <code>component</code>.
     * <p>This will return {@code null} if the UI has not installed an ActionMap.
     *
     * <p>
     *  返回UI在组件<code> component </code>中提供的ActionMap。 <p>如果UI尚未安装ActionMap,此操作将返回{@code null}。
     * 
     * 
     * @since 1.3
     */
    public static ActionMap getUIActionMap(JComponent component) {
        ActionMap map = component.getActionMap(false);
        while (map != null) {
            ActionMap parent = map.getParent();
            if (parent instanceof UIResource) {
                return parent;
            }
            map = parent;
        }
        return null;
    }


    // Don't use String, as it's not guaranteed to be unique in a Hashtable.
    private static final Object sharedOwnerFrameKey =
       new StringBuffer("SwingUtilities.sharedOwnerFrame");

    static class SharedOwnerFrame extends Frame implements WindowListener {
        public void addNotify() {
            super.addNotify();
            installListeners();
        }

        /**
         * Install window listeners on owned windows to watch for displayability changes
         * <p>
         *  在拥有的窗口上安装窗口侦听器以监视可显示性更改
         * 
         */
        void installListeners() {
            Window[] windows = getOwnedWindows();
            for (Window window : windows) {
                if (window != null) {
                    window.removeWindowListener(this);
                    window.addWindowListener(this);
                }
            }
        }

        /**
         * Watches for displayability changes and disposes shared instance if there are no
         * displayable children left.
         * <p>
         *  如果没有可显示的儿童离开,可显示性的手表会更改并处理共享实例。
         * 
         */
        public void windowClosed(WindowEvent e) {
            synchronized(getTreeLock()) {
                Window[] windows = getOwnedWindows();
                for (Window window : windows) {
                    if (window != null) {
                        if (window.isDisplayable()) {
                            return;
                        }
                        window.removeWindowListener(this);
                    }
                }
                dispose();
            }
        }
        public void windowOpened(WindowEvent e) {
        }
        public void windowClosing(WindowEvent e) {
        }
        public void windowIconified(WindowEvent e) {
        }
        public void windowDeiconified(WindowEvent e) {
        }
        public void windowActivated(WindowEvent e) {
        }
        public void windowDeactivated(WindowEvent e) {
        }

        public void show() {
            // This frame can never be shown
        }
        public void dispose() {
            try {
                getToolkit().getSystemEventQueue();
                super.dispose();
            } catch (Exception e) {
                // untrusted code not allowed to dispose
            }
        }
    }

    /**
     * Returns a toolkit-private, shared, invisible Frame
     * to be the owner for JDialogs and JWindows created with
     * {@code null} owners.
     * <p>
     *  返回一个工具箱 - 私有,共享,不可见框架是使用{@code null}所有者创建的JDialogs和JWindows的所有者。
     * 
     * 
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    static Frame getSharedOwnerFrame() throws HeadlessException {
        Frame sharedOwnerFrame =
            (Frame)SwingUtilities.appContextGet(sharedOwnerFrameKey);
        if (sharedOwnerFrame == null) {
            sharedOwnerFrame = new SharedOwnerFrame();
            SwingUtilities.appContextPut(sharedOwnerFrameKey,
                                         sharedOwnerFrame);
        }
        return sharedOwnerFrame;
    }

    /**
     * Returns a SharedOwnerFrame's shutdown listener to dispose the SharedOwnerFrame
     * if it has no more displayable children.
     * <p>
     *  返回一个SharedOwnerFrame的关闭监听器来处理SharedOwnerFrame,如果它没有更多可显示的孩子。
     * 
     * 
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    static WindowListener getSharedOwnerFrameShutdownListener() throws HeadlessException {
        Frame sharedOwnerFrame = getSharedOwnerFrame();
        return (WindowListener)sharedOwnerFrame;
    }

    /* Don't make these AppContext accessors public or protected --
     * since AppContext is in sun.awt in 1.2, we shouldn't expose it
     * even indirectly with a public API.
     * <p>
     *  因为AppContext在1.2中的sun.awt中,我们不应该使用公共API间接暴露它。
     * 
     */
    // REMIND(aim): phase out use of 4 methods below since they
    // are just private covers for AWT methods (?)

    static Object appContextGet(Object key) {
        return AppContext.getAppContext().get(key);
    }

    static void appContextPut(Object key, Object value) {
        AppContext.getAppContext().put(key, value);
    }

    static void appContextRemove(Object key) {
        AppContext.getAppContext().remove(key);
    }


    static Class<?> loadSystemClass(String className) throws ClassNotFoundException {
        ReflectUtil.checkPackageAccess(className);
        return Class.forName(className, true, Thread.currentThread().
                             getContextClassLoader());
    }


   /*
     * Convenience function for determining ComponentOrientation.  Helps us
     * avoid having Munge directives throughout the code.
     * <p>
     *  用于确定ComponentOrientation的便捷功能。帮助我们避免在整个代码中有Munge指令。
     * 
     */
    static boolean isLeftToRight( Component c ) {
        return c.getComponentOrientation().isLeftToRight();
    }
    private SwingUtilities() {
        throw new Error("SwingUtilities is just a container for static methods");
    }

    /**
     * Returns true if the Icon <code>icon</code> is an instance of
     * ImageIcon, and the image it contains is the same as <code>image</code>.
     * <p>
     *  如果Icon <code>图标</code>是ImageIcon的实例,则返回true,并且其包含的图像与<code> image </code>相同。
     * 
     */
    static boolean doesIconReferenceImage(Icon icon, Image image) {
        Image iconImage = (icon != null && (icon instanceof ImageIcon)) ?
                           ((ImageIcon)icon).getImage() : null;
        return (iconImage == image);
    }

    /**
     * Returns index of the first occurrence of <code>mnemonic</code>
     * within string <code>text</code>. Matching algorithm is not
     * case-sensitive.
     *
     * <p>
     *  返回字符串<code> text </code>中<code>助记符</code>第一次出现的索引。匹配算法不区分大小写。
     * 
     * 
     * @param text The text to search through, may be {@code null}
     * @param mnemonic The mnemonic to find the character for.
     * @return index into the string if exists, otherwise -1
     */
    static int findDisplayedMnemonicIndex(String text, int mnemonic) {
        if (text == null || mnemonic == '\0') {
            return -1;
        }

        char uc = Character.toUpperCase((char)mnemonic);
        char lc = Character.toLowerCase((char)mnemonic);

        int uci = text.indexOf(uc);
        int lci = text.indexOf(lc);

        if (uci == -1) {
            return lci;
        } else if(lci == -1) {
            return uci;
        } else {
            return (lci < uci) ? lci : uci;
        }
    }

    /**
     * Stores the position and size of
     * the inner painting area of the specified component
     * in <code>r</code> and returns <code>r</code>.
     * The position and size specify the bounds of the component,
     * adjusted so as not to include the border area (the insets).
     * This method is useful for classes
     * that implement painting code.
     *
     * <p>
     * 在<code> r </code>中存储指定组件的内部绘画区域的位置和大小,并返回<code> r </code>。位置和大小指定组件的边界,调整为不包括边框区域(插入)。
     * 此方法对于实现绘制代码的类非常有用。
     * 
     * 
     * @param c  the JComponent in question; if {@code null}, this method returns {@code null}
     * @param r  the Rectangle instance to be modified;
     *           may be {@code null}
     * @return {@code null} if the Component is {@code null};
     *         otherwise, returns the passed-in rectangle (if non-{@code null})
     *         or a new rectangle specifying position and size information
     *
     * @since 1.4
     */
    public static Rectangle calculateInnerArea(JComponent c, Rectangle r) {
        if (c == null) {
            return null;
        }
        Rectangle rect = r;
        Insets insets = c.getInsets();

        if (rect == null) {
            rect = new Rectangle();
        }

        rect.x = insets.left;
        rect.y = insets.top;
        rect.width = c.getWidth() - insets.left - insets.right;
        rect.height = c.getHeight() - insets.top - insets.bottom;

        return rect;
    }

    static void updateRendererOrEditorUI(Object rendererOrEditor) {
        if (rendererOrEditor == null) {
            return;
        }

        Component component = null;

        if (rendererOrEditor instanceof Component) {
            component = (Component)rendererOrEditor;
        }
        if (rendererOrEditor instanceof DefaultCellEditor) {
            component = ((DefaultCellEditor)rendererOrEditor).getComponent();
        }

        if (component != null) {
            SwingUtilities.updateComponentTreeUI(component);
        }
    }

    /**
     * Returns the first ancestor of the {@code component}
     * which is not an instance of {@link JLayer}.
     *
     * <p>
     *  返回{@code component}的第一个祖先,它不是{@link JLayer}的实例。
     * 
     * 
     * @param component {@code Component} to get
     * the first ancestor of, which is not a {@link JLayer} instance.
     *
     * @return the first ancestor of the {@code component}
     * which is not an instance of {@link JLayer}.
     * If such an ancestor can not be found, {@code null} is returned.
     *
     * @throws NullPointerException if {@code component} is {@code null}
     * @see JLayer
     *
     * @since 1.7
     */
    public static Container getUnwrappedParent(Component component) {
        Container parent = component.getParent();
        while(parent instanceof JLayer) {
            parent = parent.getParent();
        }
        return parent;
    }

    /**
     * Returns the first {@code JViewport}'s descendant
     * which is not an instance of {@code JLayer}.
     * If such a descendant can not be found, {@code null} is returned.
     *
     * If the {@code viewport}'s view component is not a {@code JLayer},
     * this method is equivalent to {@link JViewport#getView()}
     * otherwise {@link JLayer#getView()} will be recursively
     * called on all descending {@code JLayer}s.
     *
     * <p>
     *  返回不是{@code JLayer}的实例的第一个{@code JViewport}的后代。如果找不到这样的后代,则返回{@code null}。
     * 
     *  如果{@code viewport}的视图组件不是{@code JLayer},此方法相当于{@link JViewport#getView()},否则{@link JLayer#getView()}
     * 将被递归调用所有降序{@code JLayer}。
     * 
     * 
     * @param viewport {@code JViewport} to get the first descendant of,
     * which in not a {@code JLayer} instance.
     *
     * @return the first {@code JViewport}'s descendant
     * which is not an instance of {@code JLayer}.
     * If such a descendant can not be found, {@code null} is returned.
     *
     * @throws NullPointerException if {@code viewport} is {@code null}
     * @see JViewport#getView()
     * @see JLayer
     *
     * @since 1.7
     */
    public static Component getUnwrappedView(JViewport viewport) {
        Component view = viewport.getView();
        while (view instanceof JLayer) {
            view = ((JLayer)view).getView();
        }
        return view;
    }

   /**
     * Retrieves the validate root of a given container.
     *
     * If the container is contained within a {@code CellRendererPane}, this
     * method returns {@code null} due to the synthetic nature of the {@code
     * CellRendererPane}.
     * <p>
     * The component hierarchy must be displayable up to the toplevel component
     * (either a {@code Frame} or an {@code Applet} object.) Otherwise this
     * method returns {@code null}.
     * <p>
     * If the {@code visibleOnly} argument is {@code true}, the found validate
     * root and all its parents up to the toplevel component must also be
     * visible. Otherwise this method returns {@code null}.
     *
     * <p>
     *  检索给定容器的有效根。
     * 
     *  如果容器包含在{@code CellRendererPane}中,由于{@code CellRendererPane}的综合性质,此方法返回{@code null}。
     * <p>
     * 
     * @return the validate root of the given container or null
     * @see java.awt.Component#isDisplayable()
     * @see java.awt.Component#isVisible()
     * @since 1.7
     */
    static Container getValidateRoot(Container c, boolean visibleOnly) {
        Container root = null;

        for (; c != null; c = c.getParent())
        {
            if (!c.isDisplayable() || c instanceof CellRendererPane) {
                return null;
            }
            if (c.isValidateRoot()) {
                root = c;
                break;
            }
        }

        if (root == null) {
            return null;
        }

        for (; c != null; c = c.getParent()) {
            if (!c.isDisplayable() || (visibleOnly && !c.isVisible())) {
                return null;
            }
            if (c instanceof Window || c instanceof Applet) {
                return root;
            }
        }

        return null;
    }
}
