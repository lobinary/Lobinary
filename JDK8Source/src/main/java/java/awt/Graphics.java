/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.io.*;
import java.lang.*;
import java.util.*;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

/**
 * The <code>Graphics</code> class is the abstract base class for
 * all graphics contexts that allow an application to draw onto
 * components that are realized on various devices, as well as
 * onto off-screen images.
 * <p>
 * A <code>Graphics</code> object encapsulates state information needed
 * for the basic rendering operations that Java supports.  This
 * state information includes the following properties:
 *
 * <ul>
 * <li>The <code>Component</code> object on which to draw.
 * <li>A translation origin for rendering and clipping coordinates.
 * <li>The current clip.
 * <li>The current color.
 * <li>The current font.
 * <li>The current logical pixel operation function (XOR or Paint).
 * <li>The current XOR alternation color
 *     (see {@link Graphics#setXORMode}).
 * </ul>
 * <p>
 * Coordinates are infinitely thin and lie between the pixels of the
 * output device.
 * Operations that draw the outline of a figure operate by traversing
 * an infinitely thin path between pixels with a pixel-sized pen that hangs
 * down and to the right of the anchor point on the path.
 * Operations that fill a figure operate by filling the interior
 * of that infinitely thin path.
 * Operations that render horizontal text render the ascending
 * portion of character glyphs entirely above the baseline coordinate.
 * <p>
 * The graphics pen hangs down and to the right from the path it traverses.
 * This has the following implications:
 * <ul>
 * <li>If you draw a figure that covers a given rectangle, that
 * figure occupies one extra row of pixels on the right and bottom edges
 * as compared to filling a figure that is bounded by that same rectangle.
 * <li>If you draw a horizontal line along the same <i>y</i> coordinate as
 * the baseline of a line of text, that line is drawn entirely below
 * the text, except for any descenders.
 * </ul><p>
 * All coordinates that appear as arguments to the methods of this
 * <code>Graphics</code> object are considered relative to the
 * translation origin of this <code>Graphics</code> object prior to
 * the invocation of the method.
 * <p>
 * All rendering operations modify only pixels which lie within the
 * area bounded by the current clip, which is specified by a {@link Shape}
 * in user space and is controlled by the program using the
 * <code>Graphics</code> object.  This <i>user clip</i>
 * is transformed into device space and combined with the
 * <i>device clip</i>, which is defined by the visibility of windows and
 * device extents.  The combination of the user clip and device clip
 * defines the <i>composite clip</i>, which determines the final clipping
 * region.  The user clip cannot be modified by the rendering
 * system to reflect the resulting composite clip. The user clip can only
 * be changed through the <code>setClip</code> or <code>clipRect</code>
 * methods.
 * All drawing or writing is done in the current color,
 * using the current paint mode, and in the current font.
 *
 * <p>
 *  <code> Graphics </code>类是所有图形上下文的抽象基类,允许应用程序绘制在各种设备上实现的组件,以及屏幕外图像。
 * <p>
 *  <code> Graphics </code>对象封装Java支持的基本呈现操作所需的状态信息。该状态信息包括以下属性：
 * 
 * <ul>
 *  <li>要绘制的<code>组件</code>对象。 <li>用于渲染和裁剪坐标的翻译原点。 <li>当前剪辑。 <li>当前颜色。 <li>当前字体。
 *  <li>当前逻辑像素操作函数(XOR或Paint)。 <li>当前的XOR交替颜色(参见{@link Graphics#setXORMode})。
 * </ul>
 * <p>
 *  坐标是无穷薄的,位于输出设备的像素之间。绘制图形轮廓的操作通过遍历像素之间的无限细路径进行操作,其中像素大小的笔悬挂下来并且在路径上的锚点的右边。填充图形的操作通过填充无限薄路径的内部来操作。
 * 渲染水平文本的操作使字符字形的上升部分完全高于基线坐标。
 * <p>
 *  图形笔从其穿过的路径向下悬挂并向右悬挂。这具有以下含义：
 * <ul>
 * <li>如果您绘制一个覆盖给定矩形的图形,则与填充由该相同矩形限定的图形相比,该图形在右侧和底部边缘占用一个额外的一行像素。
 *  <li>如果您沿着与文本行基线相同的<i> y </i>坐标绘制水平线,则该线将完全绘制在文本下方,除了任何下降线。
 *  </ul> <p>作为<code> Graphics </code>对象的方法的参数出现的所有坐标都被视为相对于调用之前的<code> Graphics </code>对象的翻译原点方法。
 * <p>
 *  所有渲染操作只修改位于由用户空间中的{@link Shape}指定的当前剪辑边界的区域内的像素,并且由程序使用<code> Graphics </code>对象控制。
 * 该<i>用户剪辑</i>被转换成设备空间,并与由窗口和设备范围的可见性定义的<i>设备剪辑</i>组合。用户剪辑和设备剪辑的组合定义了<i>复合剪辑</i>,其确定最终剪辑区域。
 * 用户剪辑不能由呈现系统修改以反映所得到的复合剪辑。用户剪辑只能通过<code> setClip </code>或<code> clipRect </code>方法进行更改。
 * 所有绘图或写入都是以当前颜色,使用当前绘制模式和当前字体完成的。
 * 
 * 
 * @author      Sami Shaio
 * @author      Arthur van Hoff
 * @see     java.awt.Component
 * @see     java.awt.Graphics#clipRect(int, int, int, int)
 * @see     java.awt.Graphics#setColor(java.awt.Color)
 * @see     java.awt.Graphics#setPaintMode()
 * @see     java.awt.Graphics#setXORMode(java.awt.Color)
 * @see     java.awt.Graphics#setFont(java.awt.Font)
 * @since       JDK1.0
 */
public abstract class Graphics {

    /**
     * Constructs a new <code>Graphics</code> object.
     * This constructor is the default constructor for a graphics
     * context.
     * <p>
     * Since <code>Graphics</code> is an abstract class, applications
     * cannot call this constructor directly. Graphics contexts are
     * obtained from other graphics contexts or are created by calling
     * <code>getGraphics</code> on a component.
     * <p>
     * 构造一个新的<code> Graphics </code>对象。这个构造函数是图形上下文的默认构造函数。
     * <p>
     *  由于<code> Graphics </code>是一个抽象类,应用程序不能直接调用此构造函数。
     * 图形上下文从其他图形上下文中获取,或者通过在组件上调用<code> getGraphics </code>创建。
     * 
     * 
     * @see        java.awt.Graphics#create()
     * @see        java.awt.Component#getGraphics
     */
    protected Graphics() {
    }

    /**
     * Creates a new <code>Graphics</code> object that is
     * a copy of this <code>Graphics</code> object.
     * <p>
     *  创建一个新的<code> Graphics </code>对象,它是这个<code> Graphics </code>对象的副本。
     * 
     * 
     * @return     a new graphics context that is a copy of
     *                       this graphics context.
     */
    public abstract Graphics create();

    /**
     * Creates a new <code>Graphics</code> object based on this
     * <code>Graphics</code> object, but with a new translation and clip area.
     * The new <code>Graphics</code> object has its origin
     * translated to the specified point (<i>x</i>,&nbsp;<i>y</i>).
     * Its clip area is determined by the intersection of the original
     * clip area with the specified rectangle.  The arguments are all
     * interpreted in the coordinate system of the original
     * <code>Graphics</code> object. The new graphics context is
     * identical to the original, except in two respects:
     *
     * <ul>
     * <li>
     * The new graphics context is translated by (<i>x</i>,&nbsp;<i>y</i>).
     * That is to say, the point (<code>0</code>,&nbsp;<code>0</code>) in the
     * new graphics context is the same as (<i>x</i>,&nbsp;<i>y</i>) in
     * the original graphics context.
     * <li>
     * The new graphics context has an additional clipping rectangle, in
     * addition to whatever (translated) clipping rectangle it inherited
     * from the original graphics context. The origin of the new clipping
     * rectangle is at (<code>0</code>,&nbsp;<code>0</code>), and its size
     * is specified by the <code>width</code> and <code>height</code>
     * arguments.
     * </ul>
     * <p>
     * <p>
     *  基于此<code> Graphics </code>对象创建一个新的<code> Graphics </code>对象,但带有一个新的翻译和剪裁区域。
     * 新的<code> Graphics </code>对象的起点已转换为指定点(<i> x </i>,<y> y </i>)。其剪裁区域由原始剪裁区域与指定矩形的交叉点确定。
     * 参数都在原始<code> Graphics </code>对象的坐标系中解释。新的图形上下文与原始图形上下文相同,除了两个方面：。
     * 
     * <ul>
     * <li>
     *  新的图形上下文由(<i> x </i>,<y> y </i>)翻译。
     * 也就是说,新图形上下文中的点(<code> 0 </code>,<code> 0 </code>)与(<i> x </i> i> y </i>)。
     * <li>
     * 除了从原始图形上下文继承的任何(经转换的)剪切矩形之外,新的图形上下文还具有附加的剪切矩形。
     * 新剪切矩形的原点在(<code> 0 </code>,<code> 0 </code>),其大小由<code> width </code>和<code> height </code>参数。
     * </ul>
     * <p>
     * 
     * @param      x   the <i>x</i> coordinate.
     * @param      y   the <i>y</i> coordinate.
     * @param      width   the width of the clipping rectangle.
     * @param      height   the height of the clipping rectangle.
     * @return     a new graphics context.
     * @see        java.awt.Graphics#translate
     * @see        java.awt.Graphics#clipRect
     */
    public Graphics create(int x, int y, int width, int height) {
        Graphics g = create();
        if (g == null) return null;
        g.translate(x, y);
        g.clipRect(0, 0, width, height);
        return g;
    }

    /**
     * Translates the origin of the graphics context to the point
     * (<i>x</i>,&nbsp;<i>y</i>) in the current coordinate system.
     * Modifies this graphics context so that its new origin corresponds
     * to the point (<i>x</i>,&nbsp;<i>y</i>) in this graphics context's
     * original coordinate system.  All coordinates used in subsequent
     * rendering operations on this graphics context will be relative
     * to this new origin.
     * <p>
     *  将图形上下文的原点转换为当前坐标系中的点(<i> x </i>,<y> y </i>)。
     * 修改此图形上下文,以使其新来源对应于此图形上下文的原始坐标系中的点(<i> x </i>,<y> y </i>)。在此图形上下文上的后续渲染操作中使用的所有坐标将相对于该新的原点。
     * 
     * 
     * @param  x   the <i>x</i> coordinate.
     * @param  y   the <i>y</i> coordinate.
     */
    public abstract void translate(int x, int y);

    /**
     * Gets this graphics context's current color.
     * <p>
     *  获取此图形上下文的当前颜色。
     * 
     * 
     * @return    this graphics context's current color.
     * @see       java.awt.Color
     * @see       java.awt.Graphics#setColor(Color)
     */
    public abstract Color getColor();

    /**
     * Sets this graphics context's current color to the specified
     * color. All subsequent graphics operations using this graphics
     * context use this specified color.
     * <p>
     *  将此图形上下文的当前颜色设置为指定的颜色。使用此图形上下文的所有后续图形操作都使用此指定的颜色。
     * 
     * 
     * @param     c   the new rendering color.
     * @see       java.awt.Color
     * @see       java.awt.Graphics#getColor
     */
    public abstract void setColor(Color c);

    /**
     * Sets the paint mode of this graphics context to overwrite the
     * destination with this graphics context's current color.
     * This sets the logical pixel operation function to the paint or
     * overwrite mode.  All subsequent rendering operations will
     * overwrite the destination with the current color.
     * <p>
     *  设置此图形上下文的绘制模式,以使用此图形上下文的当前颜色覆盖目标。这将逻辑像素操作功能设置为绘画或覆盖模式。所有后续的渲染操作都将使用当前颜色覆盖目标。
     * 
     */
    public abstract void setPaintMode();

    /**
     * Sets the paint mode of this graphics context to alternate between
     * this graphics context's current color and the new specified color.
     * This specifies that logical pixel operations are performed in the
     * XOR mode, which alternates pixels between the current color and
     * a specified XOR color.
     * <p>
     * When drawing operations are performed, pixels which are the
     * current color are changed to the specified color, and vice versa.
     * <p>
     * Pixels that are of colors other than those two colors are changed
     * in an unpredictable but reversible manner; if the same figure is
     * drawn twice, then all pixels are restored to their original values.
     * <p>
     * 将此图形上下文的绘制模式设置为在此图形上下文的当前颜色和新的指定颜色之间交替。这指定在XOR模式中执行逻辑像素操作,XOR模式在当前颜色和指定的XOR颜色之间交替像素。
     * <p>
     *  当执行绘制操作时,作为当前颜色的像素被改变为指定颜色,反之亦然。
     * <p>
     *  除了这两种颜色之外的颜色的像素以不可预测但可逆的方式改变;如果相同的图被绘制两次,则所有像素被恢复到它们的原始值。
     * 
     * 
     * @param     c1 the XOR alternation color
     */
    public abstract void setXORMode(Color c1);

    /**
     * Gets the current font.
     * <p>
     *  获取当前字体。
     * 
     * 
     * @return    this graphics context's current font.
     * @see       java.awt.Font
     * @see       java.awt.Graphics#setFont(Font)
     */
    public abstract Font getFont();

    /**
     * Sets this graphics context's font to the specified font.
     * All subsequent text operations using this graphics context
     * use this font. A null argument is silently ignored.
     * <p>
     *  将此图形上下文的字体设置为指定的字体。使用此图形上下文的所有后续文本操作都使用此字体。默认忽略null参数。
     * 
     * 
     * @param  font   the font.
     * @see     java.awt.Graphics#getFont
     * @see     java.awt.Graphics#drawString(java.lang.String, int, int)
     * @see     java.awt.Graphics#drawBytes(byte[], int, int, int, int)
     * @see     java.awt.Graphics#drawChars(char[], int, int, int, int)
    */
    public abstract void setFont(Font font);

    /**
     * Gets the font metrics of the current font.
     * <p>
     *  获取当前字体的字体指标。
     * 
     * 
     * @return    the font metrics of this graphics
     *                    context's current font.
     * @see       java.awt.Graphics#getFont
     * @see       java.awt.FontMetrics
     * @see       java.awt.Graphics#getFontMetrics(Font)
     */
    public FontMetrics getFontMetrics() {
        return getFontMetrics(getFont());
    }

    /**
     * Gets the font metrics for the specified font.
     * <p>
     *  获取指定字体的字体指标。
     * 
     * 
     * @return    the font metrics for the specified font.
     * @param     f the specified font
     * @see       java.awt.Graphics#getFont
     * @see       java.awt.FontMetrics
     * @see       java.awt.Graphics#getFontMetrics()
     */
    public abstract FontMetrics getFontMetrics(Font f);


    /**
     * Returns the bounding rectangle of the current clipping area.
     * This method refers to the user clip, which is independent of the
     * clipping associated with device bounds and window visibility.
     * If no clip has previously been set, or if the clip has been
     * cleared using <code>setClip(null)</code>, this method returns
     * <code>null</code>.
     * The coordinates in the rectangle are relative to the coordinate
     * system origin of this graphics context.
     * <p>
     *  返回当前剪切区域的边界矩形。此方法引用用户剪辑,该剪辑独立于与设备边界和窗口可见性相关联的剪辑。
     * 如果先前没有设置剪辑,或者如果使用<code> setClip(null)</code>清除剪辑,则此方法返回<code> null </code>。矩形中的坐标是相对于该图形上下文的坐标系原点的。
     * 
     * 
     * @return      the bounding rectangle of the current clipping area,
     *              or <code>null</code> if no clip is set.
     * @see         java.awt.Graphics#getClip
     * @see         java.awt.Graphics#clipRect
     * @see         java.awt.Graphics#setClip(int, int, int, int)
     * @see         java.awt.Graphics#setClip(Shape)
     * @since       JDK1.1
     */
    public abstract Rectangle getClipBounds();

    /**
     * Intersects the current clip with the specified rectangle.
     * The resulting clipping area is the intersection of the current
     * clipping area and the specified rectangle.  If there is no
     * current clipping area, either because the clip has never been
     * set, or the clip has been cleared using <code>setClip(null)</code>,
     * the specified rectangle becomes the new clip.
     * This method sets the user clip, which is independent of the
     * clipping associated with device bounds and window visibility.
     * This method can only be used to make the current clip smaller.
     * To set the current clip larger, use any of the setClip methods.
     * Rendering operations have no effect outside of the clipping area.
     * <p>
     * 使用指定的矩形交叉当前剪辑。所得到的剪切区域是当前剪切区域和指定矩形的交集。
     * 如果没有当前剪辑区域,或者因为剪辑从未被设置,或者剪辑已经使用<code> setClip(null)</code>清除,指定的矩形将成为新的剪辑。
     * 此方法设置用户剪辑,该剪辑独立于与设备边界和窗口可见性相关联的剪辑。此方法只能用于使当前剪辑更小。要将当前剪辑设置为更大,请使用任何setClip方法。渲染操作在剪裁区域外没有效果。
     * 
     * 
     * @param x the x coordinate of the rectangle to intersect the clip with
     * @param y the y coordinate of the rectangle to intersect the clip with
     * @param width the width of the rectangle to intersect the clip with
     * @param height the height of the rectangle to intersect the clip with
     * @see #setClip(int, int, int, int)
     * @see #setClip(Shape)
     */
    public abstract void clipRect(int x, int y, int width, int height);

    /**
     * Sets the current clip to the rectangle specified by the given
     * coordinates.  This method sets the user clip, which is
     * independent of the clipping associated with device bounds
     * and window visibility.
     * Rendering operations have no effect outside of the clipping area.
     * <p>
     *  将当前剪辑设置为给定坐标指定的矩形。此方法设置用户剪辑,该剪辑独立于与设备边界和窗口可见性相关联的剪辑。渲染操作在剪裁区域外没有效果。
     * 
     * 
     * @param       x the <i>x</i> coordinate of the new clip rectangle.
     * @param       y the <i>y</i> coordinate of the new clip rectangle.
     * @param       width the width of the new clip rectangle.
     * @param       height the height of the new clip rectangle.
     * @see         java.awt.Graphics#clipRect
     * @see         java.awt.Graphics#setClip(Shape)
     * @see         java.awt.Graphics#getClip
     * @since       JDK1.1
     */
    public abstract void setClip(int x, int y, int width, int height);

    /**
     * Gets the current clipping area.
     * This method returns the user clip, which is independent of the
     * clipping associated with device bounds and window visibility.
     * If no clip has previously been set, or if the clip has been
     * cleared using <code>setClip(null)</code>, this method returns
     * <code>null</code>.
     * <p>
     *  获取当前剪切区域。此方法返回用户剪辑,该剪辑独立于与设备边界和窗口可见性相关联的裁剪。
     * 如果先前没有设置剪辑,或者如果使用<code> setClip(null)</code>清除剪辑,则此方法返回<code> null </code>。
     * 
     * 
     * @return      a <code>Shape</code> object representing the
     *              current clipping area, or <code>null</code> if
     *              no clip is set.
     * @see         java.awt.Graphics#getClipBounds
     * @see         java.awt.Graphics#clipRect
     * @see         java.awt.Graphics#setClip(int, int, int, int)
     * @see         java.awt.Graphics#setClip(Shape)
     * @since       JDK1.1
     */
    public abstract Shape getClip();

    /**
     * Sets the current clipping area to an arbitrary clip shape.
     * Not all objects that implement the <code>Shape</code>
     * interface can be used to set the clip.  The only
     * <code>Shape</code> objects that are guaranteed to be
     * supported are <code>Shape</code> objects that are
     * obtained via the <code>getClip</code> method and via
     * <code>Rectangle</code> objects.  This method sets the
     * user clip, which is independent of the clipping associated
     * with device bounds and window visibility.
     * <p>
     * 将当前剪辑区域设置为任意剪辑形状。并非所有实现<code> Shape </code>接口的对象都可以用来设置剪辑。
     * 保证支持的唯一<code> Shape </code>对象是通过<code> getClip </code>方法和<code> Rectangle </code>获得的<code> Shape </code>
     * 对象>对象。
     * 将当前剪辑区域设置为任意剪辑形状。并非所有实现<code> Shape </code>接口的对象都可以用来设置剪辑。此方法设置用户剪辑,该剪辑独立于与设备边界和窗口可见性相关联的剪辑。
     * 
     * 
     * @param clip the <code>Shape</code> to use to set the clip
     * @see         java.awt.Graphics#getClip()
     * @see         java.awt.Graphics#clipRect
     * @see         java.awt.Graphics#setClip(int, int, int, int)
     * @since       JDK1.1
     */
    public abstract void setClip(Shape clip);

    /**
     * Copies an area of the component by a distance specified by
     * <code>dx</code> and <code>dy</code>. From the point specified
     * by <code>x</code> and <code>y</code>, this method
     * copies downwards and to the right.  To copy an area of the
     * component to the left or upwards, specify a negative value for
     * <code>dx</code> or <code>dy</code>.
     * If a portion of the source rectangle lies outside the bounds
     * of the component, or is obscured by another window or component,
     * <code>copyArea</code> will be unable to copy the associated
     * pixels. The area that is omitted can be refreshed by calling
     * the component's <code>paint</code> method.
     * <p>
     *  将组件的一个区域复制由<code> dx </code>和<code> dy </code>指定的距离。
     * 从<code> x </code>和<code> y </code>指定的点,此方法向下向右复制。
     * 要向左或向上复制组件的区域,请为<code> dx </code>或<code> dy </code>指定负值。
     * 如果源矩形的一部分位于组件的边界之外,或者被另一个窗口或组件遮蔽,则<code> copyArea </code>将无法复制相关联的像素。
     * 省略的区域可以通过调用组件的<code> paint </code>方法进行刷新。
     * 
     * 
     * @param       x the <i>x</i> coordinate of the source rectangle.
     * @param       y the <i>y</i> coordinate of the source rectangle.
     * @param       width the width of the source rectangle.
     * @param       height the height of the source rectangle.
     * @param       dx the horizontal distance to copy the pixels.
     * @param       dy the vertical distance to copy the pixels.
     */
    public abstract void copyArea(int x, int y, int width, int height,
                                  int dx, int dy);

    /**
     * Draws a line, using the current color, between the points
     * <code>(x1,&nbsp;y1)</code> and <code>(x2,&nbsp;y2)</code>
     * in this graphics context's coordinate system.
     * <p>
     *  使用当前颜色在此图形上下文的坐标系中的<code>(x1,&nbsp; y1)</code>和<code>(x2,&nbsp; y2)</code>之间绘制一条线。
     * 
     * 
     * @param   x1  the first point's <i>x</i> coordinate.
     * @param   y1  the first point's <i>y</i> coordinate.
     * @param   x2  the second point's <i>x</i> coordinate.
     * @param   y2  the second point's <i>y</i> coordinate.
     */
    public abstract void drawLine(int x1, int y1, int x2, int y2);

    /**
     * Fills the specified rectangle.
     * The left and right edges of the rectangle are at
     * <code>x</code> and <code>x&nbsp;+&nbsp;width&nbsp;-&nbsp;1</code>.
     * The top and bottom edges are at
     * <code>y</code> and <code>y&nbsp;+&nbsp;height&nbsp;-&nbsp;1</code>.
     * The resulting rectangle covers an area
     * <code>width</code> pixels wide by
     * <code>height</code> pixels tall.
     * The rectangle is filled using the graphics context's current color.
     * <p>
     * 填充指定的矩形。矩形的左右边缘位于<code> x </code>和<code> x&nbsp; +&nbsp; width&nbsp;  - &nbsp; 1 </code>。
     * 顶部和底部边缘位于<code> y </code>和<code> y&nbsp; +&nbsp; height&nbsp;  - &nbsp; 1 </code>。
     * 生成的矩形覆盖<code> width </code>像素宽,<code> height </code>像素高的区域。使用图形上下文的当前颜色填充矩形。
     * 
     * 
     * @param         x   the <i>x</i> coordinate
     *                         of the rectangle to be filled.
     * @param         y   the <i>y</i> coordinate
     *                         of the rectangle to be filled.
     * @param         width   the width of the rectangle to be filled.
     * @param         height   the height of the rectangle to be filled.
     * @see           java.awt.Graphics#clearRect
     * @see           java.awt.Graphics#drawRect
     */
    public abstract void fillRect(int x, int y, int width, int height);

    /**
     * Draws the outline of the specified rectangle.
     * The left and right edges of the rectangle are at
     * <code>x</code> and <code>x&nbsp;+&nbsp;width</code>.
     * The top and bottom edges are at
     * <code>y</code> and <code>y&nbsp;+&nbsp;height</code>.
     * The rectangle is drawn using the graphics context's current color.
     * <p>
     *  绘制指定矩形的轮廓。矩形的左右边缘位于<code> x </code>和<code> x&nbsp; + width </code>。
     * 顶部和底部边缘位于<code> y </code>和<code> y&nbsp; +&nbsp; height </code>。矩形是使用图形上下文的当前颜色绘制的。
     * 
     * 
     * @param         x   the <i>x</i> coordinate
     *                         of the rectangle to be drawn.
     * @param         y   the <i>y</i> coordinate
     *                         of the rectangle to be drawn.
     * @param         width   the width of the rectangle to be drawn.
     * @param         height   the height of the rectangle to be drawn.
     * @see          java.awt.Graphics#fillRect
     * @see          java.awt.Graphics#clearRect
     */
    public void drawRect(int x, int y, int width, int height) {
        if ((width < 0) || (height < 0)) {
            return;
        }

        if (height == 0 || width == 0) {
            drawLine(x, y, x + width, y + height);
        } else {
            drawLine(x, y, x + width - 1, y);
            drawLine(x + width, y, x + width, y + height - 1);
            drawLine(x + width, y + height, x + 1, y + height);
            drawLine(x, y + height, x, y + 1);
        }
    }

    /**
     * Clears the specified rectangle by filling it with the background
     * color of the current drawing surface. This operation does not
     * use the current paint mode.
     * <p>
     * Beginning with Java&nbsp;1.1, the background color
     * of offscreen images may be system dependent. Applications should
     * use <code>setColor</code> followed by <code>fillRect</code> to
     * ensure that an offscreen image is cleared to a specific color.
     * <p>
     *  通过用当前绘图表面的背景颜色填充指定的矩形来清除指定的矩形。此操作不使用当前绘画模式。
     * <p>
     *  从Java 1.1版开始,屏幕外图片的背景颜色可能与系统有关。
     * 应用程序应使用<code> setColor </code>后跟<code> fillRect </code>以确保屏幕外图像被清除为特定颜色。
     * 
     * 
     * @param       x the <i>x</i> coordinate of the rectangle to clear.
     * @param       y the <i>y</i> coordinate of the rectangle to clear.
     * @param       width the width of the rectangle to clear.
     * @param       height the height of the rectangle to clear.
     * @see         java.awt.Graphics#fillRect(int, int, int, int)
     * @see         java.awt.Graphics#drawRect
     * @see         java.awt.Graphics#setColor(java.awt.Color)
     * @see         java.awt.Graphics#setPaintMode
     * @see         java.awt.Graphics#setXORMode(java.awt.Color)
     */
    public abstract void clearRect(int x, int y, int width, int height);

    /**
     * Draws an outlined round-cornered rectangle using this graphics
     * context's current color. The left and right edges of the rectangle
     * are at <code>x</code> and <code>x&nbsp;+&nbsp;width</code>,
     * respectively. The top and bottom edges of the rectangle are at
     * <code>y</code> and <code>y&nbsp;+&nbsp;height</code>.
     * <p>
     *  使用此图形上下文的当前颜色绘制轮廓圆角矩形。矩形的左边缘和右边缘分别位于<code> x </code>和<code> x&nbsp; +&nbsp; width </code>。
     * 矩形的顶部和底部边缘位于<code> y </code>和<code> y&nbsp; +&nbsp; height </code>。
     * 
     * 
     * @param      x the <i>x</i> coordinate of the rectangle to be drawn.
     * @param      y the <i>y</i> coordinate of the rectangle to be drawn.
     * @param      width the width of the rectangle to be drawn.
     * @param      height the height of the rectangle to be drawn.
     * @param      arcWidth the horizontal diameter of the arc
     *                    at the four corners.
     * @param      arcHeight the vertical diameter of the arc
     *                    at the four corners.
     * @see        java.awt.Graphics#fillRoundRect
     */
    public abstract void drawRoundRect(int x, int y, int width, int height,
                                       int arcWidth, int arcHeight);

    /**
     * Fills the specified rounded corner rectangle with the current color.
     * The left and right edges of the rectangle
     * are at <code>x</code> and <code>x&nbsp;+&nbsp;width&nbsp;-&nbsp;1</code>,
     * respectively. The top and bottom edges of the rectangle are at
     * <code>y</code> and <code>y&nbsp;+&nbsp;height&nbsp;-&nbsp;1</code>.
     * <p>
     * 使用当前颜色填充指定的圆角矩形。
     * 矩形的左边缘和右边缘分别位于<code> x </code>和<code> x&nbsp; +&nbsp; width&nbsp;  - &nbsp; 1 </code>。
     * 矩形的顶部和底部边缘位于<code> y </code>和<code> y&nbsp; +&nbsp; height&nbsp;  - &nbsp; 1 </code>。
     * 
     * 
     * @param       x the <i>x</i> coordinate of the rectangle to be filled.
     * @param       y the <i>y</i> coordinate of the rectangle to be filled.
     * @param       width the width of the rectangle to be filled.
     * @param       height the height of the rectangle to be filled.
     * @param       arcWidth the horizontal diameter
     *                     of the arc at the four corners.
     * @param       arcHeight the vertical diameter
     *                     of the arc at the four corners.
     * @see         java.awt.Graphics#drawRoundRect
     */
    public abstract void fillRoundRect(int x, int y, int width, int height,
                                       int arcWidth, int arcHeight);

    /**
     * Draws a 3-D highlighted outline of the specified rectangle.
     * The edges of the rectangle are highlighted so that they
     * appear to be beveled and lit from the upper left corner.
     * <p>
     * The colors used for the highlighting effect are determined
     * based on the current color.
     * The resulting rectangle covers an area that is
     * <code>width&nbsp;+&nbsp;1</code> pixels wide
     * by <code>height&nbsp;+&nbsp;1</code> pixels tall.
     * <p>
     *  绘制指定矩形的3-D突出轮廓。矩形的边缘被突出显示,使得它们看起来是从左上角被斜切和点亮。
     * <p>
     *  基于当前颜色确定用于突出效果的颜色。
     * 生成的矩形覆盖了<code> width&nbsp; +&nbsp; 1&lt; / code&gt;像素宽&lt; code&gt;高度&nbsp; +&nbsp; 1 </code>像素高的区域。
     * 
     * 
     * @param       x the <i>x</i> coordinate of the rectangle to be drawn.
     * @param       y the <i>y</i> coordinate of the rectangle to be drawn.
     * @param       width the width of the rectangle to be drawn.
     * @param       height the height of the rectangle to be drawn.
     * @param       raised a boolean that determines whether the rectangle
     *                      appears to be raised above the surface
     *                      or sunk into the surface.
     * @see         java.awt.Graphics#fill3DRect
     */
    public void draw3DRect(int x, int y, int width, int height,
                           boolean raised) {
        Color c = getColor();
        Color brighter = c.brighter();
        Color darker = c.darker();

        setColor(raised ? brighter : darker);
        drawLine(x, y, x, y + height);
        drawLine(x + 1, y, x + width - 1, y);
        setColor(raised ? darker : brighter);
        drawLine(x + 1, y + height, x + width, y + height);
        drawLine(x + width, y, x + width, y + height - 1);
        setColor(c);
    }

    /**
     * Paints a 3-D highlighted rectangle filled with the current color.
     * The edges of the rectangle will be highlighted so that it appears
     * as if the edges were beveled and lit from the upper left corner.
     * The colors used for the highlighting effect will be determined from
     * the current color.
     * <p>
     *  绘制填充有当前颜色的三维突出显示的矩形。矩形的边缘将被突出显示,使得它看起来好像边缘从左上角被斜切和点亮。用于突出效果的颜色将根据当前颜色确定。
     * 
     * 
     * @param       x the <i>x</i> coordinate of the rectangle to be filled.
     * @param       y the <i>y</i> coordinate of the rectangle to be filled.
     * @param       width the width of the rectangle to be filled.
     * @param       height the height of the rectangle to be filled.
     * @param       raised a boolean value that determines whether the
     *                      rectangle appears to be raised above the surface
     *                      or etched into the surface.
     * @see         java.awt.Graphics#draw3DRect
     */
    public void fill3DRect(int x, int y, int width, int height,
                           boolean raised) {
        Color c = getColor();
        Color brighter = c.brighter();
        Color darker = c.darker();

        if (!raised) {
            setColor(darker);
        }
        fillRect(x+1, y+1, width-2, height-2);
        setColor(raised ? brighter : darker);
        drawLine(x, y, x, y + height - 1);
        drawLine(x + 1, y, x + width - 2, y);
        setColor(raised ? darker : brighter);
        drawLine(x + 1, y + height - 1, x + width - 1, y + height - 1);
        drawLine(x + width - 1, y, x + width - 1, y + height - 2);
        setColor(c);
    }

    /**
     * Draws the outline of an oval.
     * The result is a circle or ellipse that fits within the
     * rectangle specified by the <code>x</code>, <code>y</code>,
     * <code>width</code>, and <code>height</code> arguments.
     * <p>
     * The oval covers an area that is
     * <code>width&nbsp;+&nbsp;1</code> pixels wide
     * and <code>height&nbsp;+&nbsp;1</code> pixels tall.
     * <p>
     *  绘制椭圆的轮廓。
     * 结果是圆形或椭圆形适合由<code> x </code>,<code> y </code>,<code> width </code>和<code> height </code >参数。
     * <p>
     *  椭圆形区域包含<code> width&nbsp; +&lt; 1&lt; / code&gt;像素宽和<code>高度&nbsp; +&nbsp; 1 </code>像素高的区域。
     * 
     * 
     * @param       x the <i>x</i> coordinate of the upper left
     *                     corner of the oval to be drawn.
     * @param       y the <i>y</i> coordinate of the upper left
     *                     corner of the oval to be drawn.
     * @param       width the width of the oval to be drawn.
     * @param       height the height of the oval to be drawn.
     * @see         java.awt.Graphics#fillOval
     */
    public abstract void drawOval(int x, int y, int width, int height);

    /**
     * Fills an oval bounded by the specified rectangle with the
     * current color.
     * <p>
     *  使用当前颜色填充由指定矩形界定的椭圆。
     * 
     * 
     * @param       x the <i>x</i> coordinate of the upper left corner
     *                     of the oval to be filled.
     * @param       y the <i>y</i> coordinate of the upper left corner
     *                     of the oval to be filled.
     * @param       width the width of the oval to be filled.
     * @param       height the height of the oval to be filled.
     * @see         java.awt.Graphics#drawOval
     */
    public abstract void fillOval(int x, int y, int width, int height);

    /**
     * Draws the outline of a circular or elliptical arc
     * covering the specified rectangle.
     * <p>
     * The resulting arc begins at <code>startAngle</code> and extends
     * for <code>arcAngle</code> degrees, using the current color.
     * Angles are interpreted such that 0&nbsp;degrees
     * is at the 3&nbsp;o'clock position.
     * A positive value indicates a counter-clockwise rotation
     * while a negative value indicates a clockwise rotation.
     * <p>
     * The center of the arc is the center of the rectangle whose origin
     * is (<i>x</i>,&nbsp;<i>y</i>) and whose size is specified by the
     * <code>width</code> and <code>height</code> arguments.
     * <p>
     * The resulting arc covers an area
     * <code>width&nbsp;+&nbsp;1</code> pixels wide
     * by <code>height&nbsp;+&nbsp;1</code> pixels tall.
     * <p>
     * The angles are specified relative to the non-square extents of
     * the bounding rectangle such that 45 degrees always falls on the
     * line from the center of the ellipse to the upper right corner of
     * the bounding rectangle. As a result, if the bounding rectangle is
     * noticeably longer in one axis than the other, the angles to the
     * start and end of the arc segment will be skewed farther along the
     * longer axis of the bounds.
     * <p>
     *  绘制覆盖指定矩形的圆形或椭圆弧的轮廓。
     * <p>
     * 结果弧开始于<code> startAngle </code>,并使用当前颜色扩展<code> arcAngle </code>度。角度被解释为0度处于3点钟位置。
     * 正值表示逆时针旋转,而负值表示顺时针旋转。
     * <p>
     *  圆弧的中心是起点为(<i> x </i>,<y> y </i>)的矩形的中心,其大小由<code> width </code>和<code> height </code>参数。
     * <p>
     *  生成的圆弧覆盖了<code> width&nbsp; +&nbsp; 1 </code>像素宽和<code> height&nbsp; +&nbsp; 1 </code>像素高的区域。
     * <p>
     *  相对于边界矩形的非正方形范围指定角度,使得45度总是落在从椭圆的中心到边界矩形的右上角的线上。因此,如果边界矩形在一个轴上比另一个明显更长,则与弧段的开始和结束的角度将沿着边界的较长轴偏移得更远。
     * 
     * 
     * @param        x the <i>x</i> coordinate of the
     *                    upper-left corner of the arc to be drawn.
     * @param        y the <i>y</i>  coordinate of the
     *                    upper-left corner of the arc to be drawn.
     * @param        width the width of the arc to be drawn.
     * @param        height the height of the arc to be drawn.
     * @param        startAngle the beginning angle.
     * @param        arcAngle the angular extent of the arc,
     *                    relative to the start angle.
     * @see         java.awt.Graphics#fillArc
     */
    public abstract void drawArc(int x, int y, int width, int height,
                                 int startAngle, int arcAngle);

    /**
     * Fills a circular or elliptical arc covering the specified rectangle.
     * <p>
     * The resulting arc begins at <code>startAngle</code> and extends
     * for <code>arcAngle</code> degrees.
     * Angles are interpreted such that 0&nbsp;degrees
     * is at the 3&nbsp;o'clock position.
     * A positive value indicates a counter-clockwise rotation
     * while a negative value indicates a clockwise rotation.
     * <p>
     * The center of the arc is the center of the rectangle whose origin
     * is (<i>x</i>,&nbsp;<i>y</i>) and whose size is specified by the
     * <code>width</code> and <code>height</code> arguments.
     * <p>
     * The resulting arc covers an area
     * <code>width&nbsp;+&nbsp;1</code> pixels wide
     * by <code>height&nbsp;+&nbsp;1</code> pixels tall.
     * <p>
     * The angles are specified relative to the non-square extents of
     * the bounding rectangle such that 45 degrees always falls on the
     * line from the center of the ellipse to the upper right corner of
     * the bounding rectangle. As a result, if the bounding rectangle is
     * noticeably longer in one axis than the other, the angles to the
     * start and end of the arc segment will be skewed farther along the
     * longer axis of the bounds.
     * <p>
     *  填充覆盖指定矩形的圆形或椭圆弧。
     * <p>
     *  结果弧开始于<code> startAngle </code>,并延伸<arc> arcAngle </code>度。角度被解释为0度处于3点钟位置。正值表示逆时针旋转,而负值表示顺时针旋转。
     * <p>
     * 圆弧的中心是起点为(<i> x </i>,<y> y </i>)的矩形的中心,其大小由<code> width </code>和<code> height </code>参数。
     * <p>
     *  生成的圆弧覆盖了<code> width&nbsp; +&nbsp; 1 </code>像素宽和<code> height&nbsp; +&nbsp; 1 </code>像素高的区域。
     * <p>
     *  相对于边界矩形的非正方形范围指定角度,使得45度总是落在从椭圆的中心到边界矩形的右上角的线上。因此,如果边界矩形在一个轴上比另一个明显更长,则与弧段的开始和结束的角度将沿着边界的较长轴偏移得更远。
     * 
     * 
     * @param        x the <i>x</i> coordinate of the
     *                    upper-left corner of the arc to be filled.
     * @param        y the <i>y</i>  coordinate of the
     *                    upper-left corner of the arc to be filled.
     * @param        width the width of the arc to be filled.
     * @param        height the height of the arc to be filled.
     * @param        startAngle the beginning angle.
     * @param        arcAngle the angular extent of the arc,
     *                    relative to the start angle.
     * @see         java.awt.Graphics#drawArc
     */
    public abstract void fillArc(int x, int y, int width, int height,
                                 int startAngle, int arcAngle);

    /**
     * Draws a sequence of connected lines defined by
     * arrays of <i>x</i> and <i>y</i> coordinates.
     * Each pair of (<i>x</i>,&nbsp;<i>y</i>) coordinates defines a point.
     * The figure is not closed if the first point
     * differs from the last point.
     * <p>
     *  绘制由<x> x </i>和<y>坐标数组定义的连接线序列。每对(<x> x,<y>)坐标定义一个点。如果第一个点与最后一个点不同,则图形不关闭。
     * 
     * 
     * @param       xPoints an array of <i>x</i> points
     * @param       yPoints an array of <i>y</i> points
     * @param       nPoints the total number of points
     * @see         java.awt.Graphics#drawPolygon(int[], int[], int)
     * @since       JDK1.1
     */
    public abstract void drawPolyline(int xPoints[], int yPoints[],
                                      int nPoints);

    /**
     * Draws a closed polygon defined by
     * arrays of <i>x</i> and <i>y</i> coordinates.
     * Each pair of (<i>x</i>,&nbsp;<i>y</i>) coordinates defines a point.
     * <p>
     * This method draws the polygon defined by <code>nPoint</code> line
     * segments, where the first <code>nPoint&nbsp;-&nbsp;1</code>
     * line segments are line segments from
     * <code>(xPoints[i&nbsp;-&nbsp;1],&nbsp;yPoints[i&nbsp;-&nbsp;1])</code>
     * to <code>(xPoints[i],&nbsp;yPoints[i])</code>, for
     * 1&nbsp;&le;&nbsp;<i>i</i>&nbsp;&le;&nbsp;<code>nPoints</code>.
     * The figure is automatically closed by drawing a line connecting
     * the final point to the first point, if those points are different.
     * <p>
     *  绘制由<i> x </i>和<y> y </i>坐标数组定义的封闭多边形。每对(<x> x,<y>)坐标定义一个点。
     * <p>
     * 此方法绘制由<code> nPoint </code>线段定义的多边形,其中第一个<code> nPoint&nbsp;  -  1 </code>线段是来自<code>(xPoints [i&nbsp
     * ;  - i> i </i>&nbsp;&le;&nbsp; <code> nPoints </code>。
     * 如果这些点不同,则通过绘制将最终点连接到第一点的线自动关闭图形。
     * 
     * 
     * @param        xPoints   a an array of <code>x</code> coordinates.
     * @param        yPoints   a an array of <code>y</code> coordinates.
     * @param        nPoints   a the total number of points.
     * @see          java.awt.Graphics#fillPolygon
     * @see          java.awt.Graphics#drawPolyline
     */
    public abstract void drawPolygon(int xPoints[], int yPoints[],
                                     int nPoints);

    /**
     * Draws the outline of a polygon defined by the specified
     * <code>Polygon</code> object.
     * <p>
     *  绘制由指定的<code>多边形</code>对象定义的多边形的轮廓。
     * 
     * 
     * @param        p the polygon to draw.
     * @see          java.awt.Graphics#fillPolygon
     * @see          java.awt.Graphics#drawPolyline
     */
    public void drawPolygon(Polygon p) {
        drawPolygon(p.xpoints, p.ypoints, p.npoints);
    }

    /**
     * Fills a closed polygon defined by
     * arrays of <i>x</i> and <i>y</i> coordinates.
     * <p>
     * This method draws the polygon defined by <code>nPoint</code> line
     * segments, where the first <code>nPoint&nbsp;-&nbsp;1</code>
     * line segments are line segments from
     * <code>(xPoints[i&nbsp;-&nbsp;1],&nbsp;yPoints[i&nbsp;-&nbsp;1])</code>
     * to <code>(xPoints[i],&nbsp;yPoints[i])</code>, for
     * 1&nbsp;&le;&nbsp;<i>i</i>&nbsp;&le;&nbsp;<code>nPoints</code>.
     * The figure is automatically closed by drawing a line connecting
     * the final point to the first point, if those points are different.
     * <p>
     * The area inside the polygon is defined using an
     * even-odd fill rule, also known as the alternating rule.
     * <p>
     *  填充由<i> x </i>和<y> y </i>坐标数组定义的封闭多边形。
     * <p>
     *  此方法绘制由<code> nPoint </code>线段定义的多边形,其中第一个<code> nPoint&nbsp;  -  1 </code>线段是来自<code>(xPoints [i&nbs
     * p;  - &nbsp; i> i </i>&nbsp;&le;&nbsp; <code> nPoints </code>。
     * 如果这些点不同,则通过绘制将最终点连接到第一点的线自动关闭图形。
     * <p>
     *  多边形内的区域使用偶奇填充规则(也称为交替规则)来定义。
     * 
     * 
     * @param        xPoints   a an array of <code>x</code> coordinates.
     * @param        yPoints   a an array of <code>y</code> coordinates.
     * @param        nPoints   a the total number of points.
     * @see          java.awt.Graphics#drawPolygon(int[], int[], int)
     */
    public abstract void fillPolygon(int xPoints[], int yPoints[],
                                     int nPoints);

    /**
     * Fills the polygon defined by the specified Polygon object with
     * the graphics context's current color.
     * <p>
     * The area inside the polygon is defined using an
     * even-odd fill rule, also known as the alternating rule.
     * <p>
     *  使用图形上下文的当前颜色填充由指定的Polygon对象定义的多边形。
     * <p>
     *  多边形内的区域使用偶奇填充规则(也称为交替规则)来定义。
     * 
     * 
     * @param        p the polygon to fill.
     * @see          java.awt.Graphics#drawPolygon(int[], int[], int)
     */
    public void fillPolygon(Polygon p) {
        fillPolygon(p.xpoints, p.ypoints, p.npoints);
    }

    /**
     * Draws the text given by the specified string, using this
     * graphics context's current font and color. The baseline of the
     * leftmost character is at position (<i>x</i>,&nbsp;<i>y</i>) in this
     * graphics context's coordinate system.
     * <p>
     * 使用此图形上下文的当前字体和颜色绘制由指定字符串给定的文本。最左边字符的基线在该图形上下文的坐标系中的位置(<i> x </i>,<y> y </i>)。
     * 
     * 
     * @param       str      the string to be drawn.
     * @param       x        the <i>x</i> coordinate.
     * @param       y        the <i>y</i> coordinate.
     * @throws NullPointerException if <code>str</code> is <code>null</code>.
     * @see         java.awt.Graphics#drawBytes
     * @see         java.awt.Graphics#drawChars
     */
    public abstract void drawString(String str, int x, int y);

    /**
     * Renders the text of the specified iterator applying its attributes
     * in accordance with the specification of the
     * {@link java.awt.font.TextAttribute TextAttribute} class.
     * <p>
     * The baseline of the leftmost character is at position
     * (<i>x</i>,&nbsp;<i>y</i>) in this graphics context's coordinate system.
     * <p>
     *  根据{@link java.awt.font.TextAttribute TextAttribute}类的规范,呈现指定的迭代器的文本,并应用其属性。
     * <p>
     *  最左边字符的基线在该图形上下文的坐标系中的位置(<i> x </i>,<y> y </i>)。
     * 
     * 
     * @param       iterator the iterator whose text is to be drawn
     * @param       x        the <i>x</i> coordinate.
     * @param       y        the <i>y</i> coordinate.
     * @throws NullPointerException if <code>iterator</code> is
     * <code>null</code>.
     * @see         java.awt.Graphics#drawBytes
     * @see         java.awt.Graphics#drawChars
     */
   public abstract void drawString(AttributedCharacterIterator iterator,
                                    int x, int y);

    /**
     * Draws the text given by the specified character array, using this
     * graphics context's current font and color. The baseline of the
     * first character is at position (<i>x</i>,&nbsp;<i>y</i>) in this
     * graphics context's coordinate system.
     * <p>
     *  使用此图形上下文的当前字体和颜色绘制由指定字符数组给出的文本。第一个字符的基线位于此图形上下文的坐标系中的位置(<i> x </i>,<y> y </i>)。
     * 
     * 
     * @param data the array of characters to be drawn
     * @param offset the start offset in the data
     * @param length the number of characters to be drawn
     * @param x the <i>x</i> coordinate of the baseline of the text
     * @param y the <i>y</i> coordinate of the baseline of the text
     * @throws NullPointerException if <code>data</code> is <code>null</code>.
     * @throws IndexOutOfBoundsException if <code>offset</code> or
     * <code>length</code>is less than zero, or
     * <code>offset+length</code> is greater than the length of the
     * <code>data</code> array.
     * @see         java.awt.Graphics#drawBytes
     * @see         java.awt.Graphics#drawString
     */
    public void drawChars(char data[], int offset, int length, int x, int y) {
        drawString(new String(data, offset, length), x, y);
    }

    /**
     * Draws the text given by the specified byte array, using this
     * graphics context's current font and color. The baseline of the
     * first character is at position (<i>x</i>,&nbsp;<i>y</i>) in this
     * graphics context's coordinate system.
     * <p>
     * Use of this method is not recommended as each byte is interpreted
     * as a Unicode code point in the range 0 to 255, and so can only be
     * used to draw Latin characters in that range.
     * <p>
     *  使用此图形上下文的当前字体和颜色绘制由指定的字节数组给出的文本。第一个字符的基线位于此图形上下文的坐标系中的位置(<i> x </i>,<y> y </i>)。
     * <p>
     *  不推荐使用此方法,因为每个字节都被解释为范围在0到255之间的Unicode代码点,因此只能用于在该范围内绘制拉丁字符。
     * 
     * 
     * @param data the data to be drawn
     * @param offset the start offset in the data
     * @param length the number of bytes that are drawn
     * @param x the <i>x</i> coordinate of the baseline of the text
     * @param y the <i>y</i> coordinate of the baseline of the text
     * @throws NullPointerException if <code>data</code> is <code>null</code>.
     * @throws IndexOutOfBoundsException if <code>offset</code> or
     * <code>length</code>is less than zero, or <code>offset+length</code>
     * is greater than the length of the <code>data</code> array.
     * @see         java.awt.Graphics#drawChars
     * @see         java.awt.Graphics#drawString
     */
    public void drawBytes(byte data[], int offset, int length, int x, int y) {
        drawString(new String(data, 0, offset, length), x, y);
    }

    /**
     * Draws as much of the specified image as is currently available.
     * The image is drawn with its top-left corner at
     * (<i>x</i>,&nbsp;<i>y</i>) in this graphics context's coordinate
     * space. Transparent pixels in the image do not affect whatever
     * pixels are already there.
     * <p>
     * This method returns immediately in all cases, even if the
     * complete image has not yet been loaded, and it has not been dithered
     * and converted for the current output device.
     * <p>
     * If the image has completely loaded and its pixels are
     * no longer being changed, then
     * <code>drawImage</code> returns <code>true</code>.
     * Otherwise, <code>drawImage</code> returns <code>false</code>
     * and as more of
     * the image becomes available
     * or it is time to draw another frame of animation,
     * the process that loads the image notifies
     * the specified image observer.
     * <p>
     *  绘制与当前可用的指定图像一样多的图像。在此图形上下文的坐标空间中,图像的左上角以(<i> x </i>,<y> y </i>)绘制。图像中的透明像素不会影响已经存在的像素。
     * <p>
     * 此方法在所有情况下都会立即返回,即使完整图像尚未加载,并且尚未对当前输出设备进行抖动和转换。
     * <p>
     *  如果图像已经完全加载并且其像素不再被改变,则<code> drawImage </code>返回<code> true </code>。
     * 否则,<code> drawImage </code>返回<code> false </code>,并且随着更多的图像变得可用或是绘制另一帧动画,加载图像的过程通知指定的图像观察者。
     * 
     * 
     * @param    img the specified image to be drawn. This method does
     *               nothing if <code>img</code> is null.
     * @param    x   the <i>x</i> coordinate.
     * @param    y   the <i>y</i> coordinate.
     * @param    observer    object to be notified as more of
     *                          the image is converted.
     * @return   <code>false</code> if the image pixels are still changing;
     *           <code>true</code> otherwise.
     * @see      java.awt.Image
     * @see      java.awt.image.ImageObserver
     * @see      java.awt.image.ImageObserver#imageUpdate(java.awt.Image, int, int, int, int, int)
     */
    public abstract boolean drawImage(Image img, int x, int y,
                                      ImageObserver observer);

    /**
     * Draws as much of the specified image as has already been scaled
     * to fit inside the specified rectangle.
     * <p>
     * The image is drawn inside the specified rectangle of this
     * graphics context's coordinate space, and is scaled if
     * necessary. Transparent pixels do not affect whatever pixels
     * are already there.
     * <p>
     * This method returns immediately in all cases, even if the
     * entire image has not yet been scaled, dithered, and converted
     * for the current output device.
     * If the current output representation is not yet complete, then
     * <code>drawImage</code> returns <code>false</code>. As more of
     * the image becomes available, the process that loads the image notifies
     * the image observer by calling its <code>imageUpdate</code> method.
     * <p>
     * A scaled version of an image will not necessarily be
     * available immediately just because an unscaled version of the
     * image has been constructed for this output device.  Each size of
     * the image may be cached separately and generated from the original
     * data in a separate image production sequence.
     * <p>
     *  绘制与已经缩放以适合指定矩形内部的指定图像一样多。
     * <p>
     *  图像在此图形上下文的坐标空间的指定矩形内绘制,并且如果必要,则被缩放。透明像素不影响已经存在的任何像素。
     * <p>
     *  此方法在所有情况下都会立即返回,即使整个图像尚未缩放,抖动和转换为当前输出设备。
     * 如果当前输出表示尚未完成,则<code> drawImage </code>返回<code> false </code>。
     * 随着更多的图像变得可用,加载图像的过程通过调用其<code> imageUpdate </code>方法通知图像观察者。
     * <p>
     * 图像的缩放版本不一定立即可用,因为已经为该输出设备构建了图像的未缩放版本。图像的每个大小可以单独高速缓存并且在单独的图像产生序列中从原始数据生成。
     * 
     * 
     * @param    img    the specified image to be drawn. This method does
     *                  nothing if <code>img</code> is null.
     * @param    x      the <i>x</i> coordinate.
     * @param    y      the <i>y</i> coordinate.
     * @param    width  the width of the rectangle.
     * @param    height the height of the rectangle.
     * @param    observer    object to be notified as more of
     *                          the image is converted.
     * @return   <code>false</code> if the image pixels are still changing;
     *           <code>true</code> otherwise.
     * @see      java.awt.Image
     * @see      java.awt.image.ImageObserver
     * @see      java.awt.image.ImageObserver#imageUpdate(java.awt.Image, int, int, int, int, int)
     */
    public abstract boolean drawImage(Image img, int x, int y,
                                      int width, int height,
                                      ImageObserver observer);

    /**
     * Draws as much of the specified image as is currently available.
     * The image is drawn with its top-left corner at
     * (<i>x</i>,&nbsp;<i>y</i>) in this graphics context's coordinate
     * space.  Transparent pixels are drawn in the specified
     * background color.
     * <p>
     * This operation is equivalent to filling a rectangle of the
     * width and height of the specified image with the given color and then
     * drawing the image on top of it, but possibly more efficient.
     * <p>
     * This method returns immediately in all cases, even if the
     * complete image has not yet been loaded, and it has not been dithered
     * and converted for the current output device.
     * <p>
     * If the image has completely loaded and its pixels are
     * no longer being changed, then
     * <code>drawImage</code> returns <code>true</code>.
     * Otherwise, <code>drawImage</code> returns <code>false</code>
     * and as more of
     * the image becomes available
     * or it is time to draw another frame of animation,
     * the process that loads the image notifies
     * the specified image observer.
     * <p>
     *  绘制与当前可用的指定图像一样多的图像。在此图形上下文的坐标空间中,图像的左上角以(<i> x </i>,<y> y </i>)绘制。透明像素以指定的背景颜色绘制。
     * <p>
     *  此操作等效于使用给定颜色填充指定图像的宽度和高度的矩形,然后在其上绘制图像,但可能更有效。
     * <p>
     *  此方法在所有情况下都会立即返回,即使完整图像尚未加载,并且尚未对当前输出设备进行抖动和转换。
     * <p>
     *  如果图像已经完全加载并且其像素不再被改变,则<code> drawImage </code>返回<code> true </code>。
     * 否则,<code> drawImage </code>返回<code> false </code>,并且随着更多的图像变得可用或是绘制另一帧动画,加载图像的过程通知指定的图像观察者。
     * 
     * 
     * @param    img the specified image to be drawn. This method does
     *               nothing if <code>img</code> is null.
     * @param    x      the <i>x</i> coordinate.
     * @param    y      the <i>y</i> coordinate.
     * @param    bgcolor the background color to paint under the
     *                         non-opaque portions of the image.
     * @param    observer    object to be notified as more of
     *                          the image is converted.
     * @return   <code>false</code> if the image pixels are still changing;
     *           <code>true</code> otherwise.
     * @see      java.awt.Image
     * @see      java.awt.image.ImageObserver
     * @see      java.awt.image.ImageObserver#imageUpdate(java.awt.Image, int, int, int, int, int)
     */
    public abstract boolean drawImage(Image img, int x, int y,
                                      Color bgcolor,
                                      ImageObserver observer);

    /**
     * Draws as much of the specified image as has already been scaled
     * to fit inside the specified rectangle.
     * <p>
     * The image is drawn inside the specified rectangle of this
     * graphics context's coordinate space, and is scaled if
     * necessary. Transparent pixels are drawn in the specified
     * background color.
     * This operation is equivalent to filling a rectangle of the
     * width and height of the specified image with the given color and then
     * drawing the image on top of it, but possibly more efficient.
     * <p>
     * This method returns immediately in all cases, even if the
     * entire image has not yet been scaled, dithered, and converted
     * for the current output device.
     * If the current output representation is not yet complete then
     * <code>drawImage</code> returns <code>false</code>. As more of
     * the image becomes available, the process that loads the image notifies
     * the specified image observer.
     * <p>
     * A scaled version of an image will not necessarily be
     * available immediately just because an unscaled version of the
     * image has been constructed for this output device.  Each size of
     * the image may be cached separately and generated from the original
     * data in a separate image production sequence.
     * <p>
     *  绘制与已经缩放以适合指定矩形内部的指定图像一样多。
     * <p>
     * 图像在此图形上下文的坐标空间的指定矩形内绘制,并且如果必要,则被缩放。透明像素以指定的背景颜色绘制。此操作等效于使用给定颜色填充指定图像的宽度和高度的矩形,然后在其上绘制图像,但可能更有效。
     * <p>
     *  此方法在所有情况下都会立即返回,即使整个图像尚未缩放,抖动和转换为当前输出设备。
     * 如果当前输出表示还没有完成,那么<​​code> drawImage </code>返回<code> false </code>。随着更多的图像变得可用,加载图像的过程通知指定的图像观察者。
     * <p>
     *  图像的缩放版本不一定立即可用,因为已经为该输出设备构建了图像的未缩放版本。图像的每个大小可以单独高速缓存并且在单独的图像产生序列中从原始数据生成。
     * 
     * 
     * @param    img       the specified image to be drawn. This method does
     *                     nothing if <code>img</code> is null.
     * @param    x         the <i>x</i> coordinate.
     * @param    y         the <i>y</i> coordinate.
     * @param    width     the width of the rectangle.
     * @param    height    the height of the rectangle.
     * @param    bgcolor   the background color to paint under the
     *                         non-opaque portions of the image.
     * @param    observer    object to be notified as more of
     *                          the image is converted.
     * @return   <code>false</code> if the image pixels are still changing;
     *           <code>true</code> otherwise.
     * @see      java.awt.Image
     * @see      java.awt.image.ImageObserver
     * @see      java.awt.image.ImageObserver#imageUpdate(java.awt.Image, int, int, int, int, int)
     */
    public abstract boolean drawImage(Image img, int x, int y,
                                      int width, int height,
                                      Color bgcolor,
                                      ImageObserver observer);

    /**
     * Draws as much of the specified area of the specified image as is
     * currently available, scaling it on the fly to fit inside the
     * specified area of the destination drawable surface. Transparent pixels
     * do not affect whatever pixels are already there.
     * <p>
     * This method returns immediately in all cases, even if the
     * image area to be drawn has not yet been scaled, dithered, and converted
     * for the current output device.
     * If the current output representation is not yet complete then
     * <code>drawImage</code> returns <code>false</code>. As more of
     * the image becomes available, the process that loads the image notifies
     * the specified image observer.
     * <p>
     * This method always uses the unscaled version of the image
     * to render the scaled rectangle and performs the required
     * scaling on the fly. It does not use a cached, scaled version
     * of the image for this operation. Scaling of the image from source
     * to destination is performed such that the first coordinate
     * of the source rectangle is mapped to the first coordinate of
     * the destination rectangle, and the second source coordinate is
     * mapped to the second destination coordinate. The subimage is
     * scaled and flipped as needed to preserve those mappings.
     * <p>
     *  绘制指定图像的指定区域作为当前可用的指定区域,缩放它在飞行中以适合目标可绘制表面的指定区域内。透明像素不影响已经存在的任何像素。
     * <p>
     * 在所有情况下,即使要绘制的图像区域尚未缩放,抖动和转换为当前输出设备,此方法也会立即返回。
     * 如果当前输出表示还没有完成,那么<​​code> drawImage </code>返回<code> false </code>。随着更多的图像变得可用,加载图像的过程通知指定的图像观察者。
     * <p>
     *  此方法始终使用未缩放版本的图像来渲染缩放的矩形并即时执行所需的缩放。它不使用缓存的缩放版本的图像进行此操作。
     * 执行从源到目的地的图像缩放,使得源矩形的第一坐标被映射到目的地矩形的第一坐标,并且第二源坐标被映射到第二目的地坐标。根据需要缩放和翻转子图像以保留这些映射。
     * 
     * 
     * @param       img the specified image to be drawn. This method does
     *                  nothing if <code>img</code> is null.
     * @param       dx1 the <i>x</i> coordinate of the first corner of the
     *                    destination rectangle.
     * @param       dy1 the <i>y</i> coordinate of the first corner of the
     *                    destination rectangle.
     * @param       dx2 the <i>x</i> coordinate of the second corner of the
     *                    destination rectangle.
     * @param       dy2 the <i>y</i> coordinate of the second corner of the
     *                    destination rectangle.
     * @param       sx1 the <i>x</i> coordinate of the first corner of the
     *                    source rectangle.
     * @param       sy1 the <i>y</i> coordinate of the first corner of the
     *                    source rectangle.
     * @param       sx2 the <i>x</i> coordinate of the second corner of the
     *                    source rectangle.
     * @param       sy2 the <i>y</i> coordinate of the second corner of the
     *                    source rectangle.
     * @param       observer object to be notified as more of the image is
     *                    scaled and converted.
     * @return   <code>false</code> if the image pixels are still changing;
     *           <code>true</code> otherwise.
     * @see         java.awt.Image
     * @see         java.awt.image.ImageObserver
     * @see         java.awt.image.ImageObserver#imageUpdate(java.awt.Image, int, int, int, int, int)
     * @since       JDK1.1
     */
    public abstract boolean drawImage(Image img,
                                      int dx1, int dy1, int dx2, int dy2,
                                      int sx1, int sy1, int sx2, int sy2,
                                      ImageObserver observer);

    /**
     * Draws as much of the specified area of the specified image as is
     * currently available, scaling it on the fly to fit inside the
     * specified area of the destination drawable surface.
     * <p>
     * Transparent pixels are drawn in the specified background color.
     * This operation is equivalent to filling a rectangle of the
     * width and height of the specified image with the given color and then
     * drawing the image on top of it, but possibly more efficient.
     * <p>
     * This method returns immediately in all cases, even if the
     * image area to be drawn has not yet been scaled, dithered, and converted
     * for the current output device.
     * If the current output representation is not yet complete then
     * <code>drawImage</code> returns <code>false</code>. As more of
     * the image becomes available, the process that loads the image notifies
     * the specified image observer.
     * <p>
     * This method always uses the unscaled version of the image
     * to render the scaled rectangle and performs the required
     * scaling on the fly. It does not use a cached, scaled version
     * of the image for this operation. Scaling of the image from source
     * to destination is performed such that the first coordinate
     * of the source rectangle is mapped to the first coordinate of
     * the destination rectangle, and the second source coordinate is
     * mapped to the second destination coordinate. The subimage is
     * scaled and flipped as needed to preserve those mappings.
     * <p>
     *  绘制指定图像的指定区域作为当前可用的指定区域,缩放它在飞行中以适合目标可绘制表面的指定区域内。
     * <p>
     *  透明像素以指定的背景颜色绘制。此操作等效于使用给定颜色填充指定图像的宽度和高度的矩形,然后在其上绘制图像,但可能更有效。
     * <p>
     * 在所有情况下,即使要绘制的图像区域尚未缩放,抖动和转换为当前输出设备,此方法也会立即返回。
     * 如果当前输出表示还没有完成,那么<​​code> drawImage </code>返回<code> false </code>。随着更多的图像变得可用,加载图像的过程通知指定的图像观察者。
     * <p>
     *  此方法始终使用未缩放版本的图像来渲染缩放的矩形并即时执行所需的缩放。它不使用缓存的缩放版本的图像进行此操作。
     * 执行从源到目的地的图像缩放,使得源矩形的第一坐标被映射到目的地矩形的第一坐标,并且第二源坐标被映射到第二目的地坐标。根据需要缩放和翻转子图像以保留这些映射。
     * 
     * 
     * @param       img the specified image to be drawn. This method does
     *                  nothing if <code>img</code> is null.
     * @param       dx1 the <i>x</i> coordinate of the first corner of the
     *                    destination rectangle.
     * @param       dy1 the <i>y</i> coordinate of the first corner of the
     *                    destination rectangle.
     * @param       dx2 the <i>x</i> coordinate of the second corner of the
     *                    destination rectangle.
     * @param       dy2 the <i>y</i> coordinate of the second corner of the
     *                    destination rectangle.
     * @param       sx1 the <i>x</i> coordinate of the first corner of the
     *                    source rectangle.
     * @param       sy1 the <i>y</i> coordinate of the first corner of the
     *                    source rectangle.
     * @param       sx2 the <i>x</i> coordinate of the second corner of the
     *                    source rectangle.
     * @param       sy2 the <i>y</i> coordinate of the second corner of the
     *                    source rectangle.
     * @param       bgcolor the background color to paint under the
     *                    non-opaque portions of the image.
     * @param       observer object to be notified as more of the image is
     *                    scaled and converted.
     * @return   <code>false</code> if the image pixels are still changing;
     *           <code>true</code> otherwise.
     * @see         java.awt.Image
     * @see         java.awt.image.ImageObserver
     * @see         java.awt.image.ImageObserver#imageUpdate(java.awt.Image, int, int, int, int, int)
     * @since       JDK1.1
     */
    public abstract boolean drawImage(Image img,
                                      int dx1, int dy1, int dx2, int dy2,
                                      int sx1, int sy1, int sx2, int sy2,
                                      Color bgcolor,
                                      ImageObserver observer);

    /**
     * Disposes of this graphics context and releases
     * any system resources that it is using.
     * A <code>Graphics</code> object cannot be used after
     * <code>dispose</code>has been called.
     * <p>
     * When a Java program runs, a large number of <code>Graphics</code>
     * objects can be created within a short time frame.
     * Although the finalization process of the garbage collector
     * also disposes of the same system resources, it is preferable
     * to manually free the associated resources by calling this
     * method rather than to rely on a finalization process which
     * may not run to completion for a long period of time.
     * <p>
     * Graphics objects which are provided as arguments to the
     * <code>paint</code> and <code>update</code> methods
     * of components are automatically released by the system when
     * those methods return. For efficiency, programmers should
     * call <code>dispose</code> when finished using
     * a <code>Graphics</code> object only if it was created
     * directly from a component or another <code>Graphics</code> object.
     * <p>
     *  处理此图形上下文并释放它正在使用的任何系统资源。在调用<code> dispose </code>之后,不能使用<code> Graphics </code>对象。
     * <p>
     * 当Java程序运行时,可以在短时间内创建大量的<code> Graphics </code>对象。
     * 尽管垃圾收集器的完成过程也丢弃了相同的系统资源,但是优选地通过调用该方法手动释放相关联的资源,而不是依赖可能不会长时间运行完成的完成过程。
     * <p>
     *  作为元素的<code> paint </code>和<code> update </code>方法的参数提供的图形对象在这些方法返回时由系统自动释放。
     * 为了提高效率,只有当直接从组件或另一个<code> Graphics </code>对象创建时,程序员在使用<code> Graphics </code>对象时才应该调用<code> dispose </code>
     * 。
     *  作为元素的<code> paint </code>和<code> update </code>方法的参数提供的图形对象在这些方法返回时由系统自动释放。
     * 
     * 
     * @see         java.awt.Graphics#finalize
     * @see         java.awt.Component#paint
     * @see         java.awt.Component#update
     * @see         java.awt.Component#getGraphics
     * @see         java.awt.Graphics#create
     */
    public abstract void dispose();

    /**
     * Disposes of this graphics context once it is no longer referenced.
     * <p>
     *  一旦不再引用此图形上下文。
     * 
     * 
     * @see #dispose
     */
    public void finalize() {
        dispose();
    }

    /**
     * Returns a <code>String</code> object representing this
     *                        <code>Graphics</code> object's value.
     * <p>
     *  返回表示此<code> Graphics </code>对象值的<code> String </code>对象。
     * 
     * 
     * @return       a string representation of this graphics context.
     */
    public String toString() {
        return getClass().getName() + "[font=" + getFont() + ",color=" + getColor() + "]";
    }

    /**
     * Returns the bounding rectangle of the current clipping area.
     * <p>
     *  返回当前剪切区域的边界矩形。
     * 
     * 
     * @return      the bounding rectangle of the current clipping area
     *              or <code>null</code> if no clip is set.
     * @deprecated As of JDK version 1.1,
     * replaced by <code>getClipBounds()</code>.
     */
    @Deprecated
    public Rectangle getClipRect() {
        return getClipBounds();
    }

    /**
     * Returns true if the specified rectangular area might intersect
     * the current clipping area.
     * The coordinates of the specified rectangular area are in the
     * user coordinate space and are relative to the coordinate
     * system origin of this graphics context.
     * This method may use an algorithm that calculates a result quickly
     * but which sometimes might return true even if the specified
     * rectangular area does not intersect the clipping area.
     * The specific algorithm employed may thus trade off accuracy for
     * speed, but it will never return false unless it can guarantee
     * that the specified rectangular area does not intersect the
     * current clipping area.
     * The clipping area used by this method can represent the
     * intersection of the user clip as specified through the clip
     * methods of this graphics context as well as the clipping
     * associated with the device or image bounds and window visibility.
     *
     * <p>
     * 如果指定的矩形区域可能与当前剪切区域相交,则返回true。指定的矩形区域的坐标在用户坐标空间中,并且相对于该图形上下文的坐标系原点。
     * 该方法可以使用快速计算结果但是即使指定的矩形区域不与剪切区域相交也有时可能返回真的算法。所使用的特定算法因此可以折衷速度的精度,但是它将永远不会返回假,除非它能保证指定的矩形区域不与当前剪切区域相交。
     * 该方法使用的剪辑区域可以表示通过该图形上下文的剪辑方法指定的用户剪辑的交集以及与设备或图像边界和窗口可见性相关联的剪辑。
     * 
     * 
     * @param x the x coordinate of the rectangle to test against the clip
     * @param y the y coordinate of the rectangle to test against the clip
     * @param width the width of the rectangle to test against the clip
     * @param height the height of the rectangle to test against the clip
     * @return <code>true</code> if the specified rectangle intersects
     *         the bounds of the current clip; <code>false</code>
     *         otherwise.
     */
    public boolean hitClip(int x, int y, int width, int height) {
        // Note, this implementation is not very efficient.
        // Subclasses should override this method and calculate
        // the results more directly.
        Rectangle clipRect = getClipBounds();
        if (clipRect == null) {
            return true;
        }
        return clipRect.intersects(x, y, width, height);
    }

    /**
     * Returns the bounding rectangle of the current clipping area.
     * The coordinates in the rectangle are relative to the coordinate
     * system origin of this graphics context.  This method differs
     * from {@link #getClipBounds() getClipBounds} in that an existing
     * rectangle is used instead of allocating a new one.
     * This method refers to the user clip, which is independent of the
     * clipping associated with device bounds and window visibility.
     *  If no clip has previously been set, or if the clip has been
     * cleared using <code>setClip(null)</code>, this method returns the
     * specified <code>Rectangle</code>.
     * <p>
     *  返回当前剪切区域的边界矩形。矩形中的坐标是相对于该图形上下文的坐标系原点的。
     * 此方法不同于{@link #getClipBounds()getClipBounds},因为使用现有的矩形而不是分配新的矩形。此方法引用用户剪辑,该剪辑独立于与设备边界和窗口可见性相关联的剪辑。
     * 
     * @param  r    the rectangle where the current clipping area is
     *              copied to.  Any current values in this rectangle are
     *              overwritten.
     * @return      the bounding rectangle of the current clipping area.
     */
    public Rectangle getClipBounds(Rectangle r) {
        // Note, this implementation is not very efficient.
        // Subclasses should override this method and avoid
        // the allocation overhead of getClipBounds().
        Rectangle clipRect = getClipBounds();
        if (clipRect != null) {
            r.x = clipRect.x;
            r.y = clipRect.y;
            r.width = clipRect.width;
            r.height = clipRect.height;
        } else if (r == null) {
            throw new NullPointerException("null rectangle parameter");
        }
        return r;
    }
}
