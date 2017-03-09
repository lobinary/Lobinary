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

import java.awt.geom.Rectangle2D;
import java.beans.Transient;

/**
 * A <code>Rectangle</code> specifies an area in a coordinate space that is
 * enclosed by the <code>Rectangle</code> object's upper-left point
 * {@code (x,y)}
 * in the coordinate space, its width, and its height.
 * <p>
 * A <code>Rectangle</code> object's <code>width</code> and
 * <code>height</code> are <code>public</code> fields. The constructors
 * that create a <code>Rectangle</code>, and the methods that can modify
 * one, do not prevent setting a negative value for width or height.
 * <p>
 * <a name="Empty">
 * A {@code Rectangle} whose width or height is exactly zero has location
 * along those axes with zero dimension, but is otherwise considered empty.
 * The {@link #isEmpty} method will return true for such a {@code Rectangle}.
 * Methods which test if an empty {@code Rectangle} contains or intersects
 * a point or rectangle will always return false if either dimension is zero.
 * Methods which combine such a {@code Rectangle} with a point or rectangle
 * will include the location of the {@code Rectangle} on that axis in the
 * result as if the {@link #add(Point)} method were being called.
 * </a>
 * <p>
 * <a name="NonExistant">
 * A {@code Rectangle} whose width or height is negative has neither
 * location nor dimension along those axes with negative dimensions.
 * Such a {@code Rectangle} is treated as non-existant along those axes.
 * Such a {@code Rectangle} is also empty with respect to containment
 * calculations and methods which test if it contains or intersects a
 * point or rectangle will always return false.
 * Methods which combine such a {@code Rectangle} with a point or rectangle
 * will ignore the {@code Rectangle} entirely in generating the result.
 * If two {@code Rectangle} objects are combined and each has a negative
 * dimension, the result will have at least one negative dimension.
 * </a>
 * <p>
 * Methods which affect only the location of a {@code Rectangle} will
 * operate on its location regardless of whether or not it has a negative
 * or zero dimension along either axis.
 * <p>
 * Note that a {@code Rectangle} constructed with the default no-argument
 * constructor will have dimensions of {@code 0x0} and therefore be empty.
 * That {@code Rectangle} will still have a location of {@code (0,0)} and
 * will contribute that location to the union and add operations.
 * Code attempting to accumulate the bounds of a set of points should
 * therefore initially construct the {@code Rectangle} with a specifically
 * negative width and height or it should use the first point in the set
 * to construct the {@code Rectangle}.
 * For example:
 * <pre>{@code
 *     Rectangle bounds = new Rectangle(0, 0, -1, -1);
 *     for (int i = 0; i < points.length; i++) {
 *         bounds.add(points[i]);
 *     }
 * }</pre>
 * or if we know that the points array contains at least one point:
 * <pre>{@code
 *     Rectangle bounds = new Rectangle(points[0]);
 *     for (int i = 1; i < points.length; i++) {
 *         bounds.add(points[i]);
 *     }
 * }</pre>
 * <p>
 * This class uses 32-bit integers to store its location and dimensions.
 * Frequently operations may produce a result that exceeds the range of
 * a 32-bit integer.
 * The methods will calculate their results in a way that avoids any
 * 32-bit overflow for intermediate results and then choose the best
 * representation to store the final results back into the 32-bit fields
 * which hold the location and dimensions.
 * The location of the result will be stored into the {@link #x} and
 * {@link #y} fields by clipping the true result to the nearest 32-bit value.
 * The values stored into the {@link #width} and {@link #height} dimension
 * fields will be chosen as the 32-bit values that encompass the largest
 * part of the true result as possible.
 * Generally this means that the dimension will be clipped independently
 * to the range of 32-bit integers except that if the location had to be
 * moved to store it into its pair of 32-bit fields then the dimensions
 * will be adjusted relative to the "best representation" of the location.
 * If the true result had a negative dimension and was therefore
 * non-existant along one or both axes, the stored dimensions will be
 * negative numbers in those axes.
 * If the true result had a location that could be represented within
 * the range of 32-bit integers, but zero dimension along one or both
 * axes, then the stored dimensions will be zero in those axes.
 *
 * <p>
 *  <code> Rectangle </code>指定坐标空间中由<code> Rectangle </code>对象的左上点{@code(x,y)}包围的区域, ,及其高度。
 * <p>
 *  <code> Rectangle </code>对象的<code> width </code>和<code> height </code>是<code> public </code>字段。
 * 创建<code> Rectangle </code>的构造函数以及可以修改一个的方法不会阻止为宽度或高度设置负值。
 * <p>
 * <a name="Empty">
 *  宽度或高度正好为零的{@code Rectangle}具有沿着零维度的轴的位置,但在其他情况下视为空。 {@link #isEmpty}方法将对此类{@code Rectangle}返回true。
 * 测试一个空的{@code Rectangle}是否包含或交叉点或矩形的方法将总是返回false,如果任何一个维度为零。
 * 结合这样的{@code Rectangle}与点或矩形的方法将在结果中包括{@code Rectangle}在该轴上的位置,就像调用{@link #add(Point)}方法。
 * </a>
 * <p>
 * <a name="NonExistant">
 * 宽度或高度为负的{@code Rectangle}在沿着具有负尺寸的轴上既不具有位置也不具有尺寸。这样的{@code Rectangle}被视为沿着这些轴不存在。
 * 这样的{@code Rectangle}对于包含计算和方法也是空的,测试它是否包含或相交点或矩形将总是返回false。
 * 将这样的{@code Rectangle}与点或矩形组合的方法将在生成结果时完全忽略{@code Rectangle}。
 * 如果两个{@code Rectangle}对象组合在一起,每个对象具有负维度,则结果将至少有一个负维度。
 * </a>
 * <p>
 *  仅影响{@code Rectangle}的位置的方法将对其位置进行操作,而不管其是否沿任一轴具有负或零维度。
 * <p>
 * 请注意,使用默认无参数构造函数构造的{@code Rectangle}的尺寸为{@code 0x0},因此为空。
 *  {@code Rectangle}的位置仍然为{@code(0,0)},并将该位置贡献给联合并添加操作。
 * 因此,试图累积一组点的边界的代码应该首先构造具有特定负宽度和高度的{@code Rectangle},或者应该使用集合中的第一个点来构造{@code Rectangle}。
 * 例如：<pre> {@ code Rectangle bounds = new Rectangle(0,0,-1,-1); for(int i = 0; i <points.length; i ++){bounds.add(points [i]); }} </pre>
 * 或者如果我们知道points数组包含至少一个点：<pre> {@ code Rectangle bounds = new Rectangle(points [0]); for(int i = 1; i <points.length; i ++){bounds.add(points [i]); }} </pre>
 * 。
 * 因此,试图累积一组点的边界的代码应该首先构造具有特定负宽度和高度的{@code Rectangle},或者应该使用集合中的第一个点来构造{@code Rectangle}。
 * <p>
 * 此类使用32位整数来存储其位置和维度。通常,操作可能会产生超过32位整数范围的结果。方法将以避免中间结果的任何32位溢出的方式计算其结果,然后选择最佳表示以将最终结果存储回保存位置和维度的32位字段。
 * 结果的位置将通过将真实结果剪切为最接近的32位值存储到{@link #x}和{@link #y}字段中。
 * 存储在{@link #width}和{@link #height}维度字段中的值将被选择为尽可能包含真实结果的最大部分的32位值。
 * 通常这意味着维度将被独立地裁剪到32位整数的范围,除了如果位置必须被移动以将其存储到其32位字段对中,则将相对于"最佳表示"来调整维度"的位置。
 * 如果真实结果具有负的尺寸,因此沿着一个或两个轴不存在,则存储的尺寸在这些轴上将是负数。如果真实结果具有可以在32位整数的范围内表示的位置,但是沿着一个或两个轴的零维度,则存储的维度在那些轴中将为零。
 * 
 * 
 * @author      Sami Shaio
 * @since 1.0
 */
public class Rectangle extends Rectangle2D
    implements Shape, java.io.Serializable
{

    /**
     * The X coordinate of the upper-left corner of the <code>Rectangle</code>.
     *
     * <p>
     *  <code> Rectangle </code>左上角的X坐标。
     * 
     * 
     * @serial
     * @see #setLocation(int, int)
     * @see #getLocation()
     * @since 1.0
     */
    public int x;

    /**
     * The Y coordinate of the upper-left corner of the <code>Rectangle</code>.
     *
     * <p>
     * <code> Rectangle </code>的左上角的Y坐标。
     * 
     * 
     * @serial
     * @see #setLocation(int, int)
     * @see #getLocation()
     * @since 1.0
     */
    public int y;

    /**
     * The width of the <code>Rectangle</code>.
     * <p>
     *  <code> Rectangle </code>的宽度。
     * 
     * 
     * @serial
     * @see #setSize(int, int)
     * @see #getSize()
     * @since 1.0
     */
    public int width;

    /**
     * The height of the <code>Rectangle</code>.
     *
     * <p>
     *  <code> Rectangle </code>的高度。
     * 
     * 
     * @serial
     * @see #setSize(int, int)
     * @see #getSize()
     * @since 1.0
     */
    public int height;

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
     private static final long serialVersionUID = -4345857070255674764L;

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
     * Constructs a new <code>Rectangle</code> whose upper-left corner
     * is at (0,&nbsp;0) in the coordinate space, and whose width and
     * height are both zero.
     * <p>
     *  构造一个新的<code> Rectangle </code>,其左上角在坐标空间中为(0,&nbsp; 0),宽度和高度均为零。
     * 
     */
    public Rectangle() {
        this(0, 0, 0, 0);
    }

    /**
     * Constructs a new <code>Rectangle</code>, initialized to match
     * the values of the specified <code>Rectangle</code>.
     * <p>
     *  构造一个新的<code> Rectangle </code>,初始化为与指定的<code> Rectangle </code>的值相匹配。
     * 
     * 
     * @param r  the <code>Rectangle</code> from which to copy initial values
     *           to a newly constructed <code>Rectangle</code>
     * @since 1.1
     */
    public Rectangle(Rectangle r) {
        this(r.x, r.y, r.width, r.height);
    }

    /**
     * Constructs a new <code>Rectangle</code> whose upper-left corner is
     * specified as
     * {@code (x,y)} and whose width and height
     * are specified by the arguments of the same name.
     * <p>
     *  构造一个新的<code> Rectangle </code>,其左上角被指定为{@code(x,y)},其宽度和高度由同名的参数指定。
     * 
     * 
     * @param     x the specified X coordinate
     * @param     y the specified Y coordinate
     * @param     width    the width of the <code>Rectangle</code>
     * @param     height   the height of the <code>Rectangle</code>
     * @since 1.0
     */
    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Constructs a new <code>Rectangle</code> whose upper-left corner
     * is at (0,&nbsp;0) in the coordinate space, and whose width and
     * height are specified by the arguments of the same name.
     * <p>
     *  构造一个新的<code> Rectangle </code>,它的左上角在坐标空间中为(0,&nbsp; 0),其宽度和高度由同名的参数指定。
     * 
     * 
     * @param width the width of the <code>Rectangle</code>
     * @param height the height of the <code>Rectangle</code>
     */
    public Rectangle(int width, int height) {
        this(0, 0, width, height);
    }

    /**
     * Constructs a new <code>Rectangle</code> whose upper-left corner is
     * specified by the {@link Point} argument, and
     * whose width and height are specified by the
     * {@link Dimension} argument.
     * <p>
     *  构造一个新的<code> Rectangle </code>,其左上角由{@link Point}参数指定,其宽度和高度由{@link Dimension}参数指定。
     * 
     * 
     * @param p a <code>Point</code> that is the upper-left corner of
     * the <code>Rectangle</code>
     * @param d a <code>Dimension</code>, representing the
     * width and height of the <code>Rectangle</code>
     */
    public Rectangle(Point p, Dimension d) {
        this(p.x, p.y, d.width, d.height);
    }

    /**
     * Constructs a new <code>Rectangle</code> whose upper-left corner is the
     * specified <code>Point</code>, and whose width and height are both zero.
     * <p>
     *  构造一个新的<code> Rectangle </code>,其左上角是指定的<code> Point </code>,其宽度和高度均为零。
     * 
     * 
     * @param p a <code>Point</code> that is the top left corner
     * of the <code>Rectangle</code>
     */
    public Rectangle(Point p) {
        this(p.x, p.y, 0, 0);
    }

    /**
     * Constructs a new <code>Rectangle</code> whose top left corner is
     * (0,&nbsp;0) and whose width and height are specified
     * by the <code>Dimension</code> argument.
     * <p>
     *  构造一个新的<code> Rectangle </code>,其左上角是(0,&nbsp; 0),其宽度和高度由<code> Dimension </code>参数指定。
     * 
     * 
     * @param d a <code>Dimension</code>, specifying width and height
     */
    public Rectangle(Dimension d) {
        this(0, 0, d.width, d.height);
    }

    /**
     * Returns the X coordinate of the bounding <code>Rectangle</code> in
     * <code>double</code> precision.
     * <p>
     *  返回<code> double </code>精度中边界<code> Rectangle </code>的X坐标。
     * 
     * 
     * @return the X coordinate of the bounding <code>Rectangle</code>.
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the Y coordinate of the bounding <code>Rectangle</code> in
     * <code>double</code> precision.
     * <p>
     * 返回<code> double </code>精度中边界<code> Rectangle </code>的Y坐标。
     * 
     * 
     * @return the Y coordinate of the bounding <code>Rectangle</code>.
     */
    public double getY() {
        return y;
    }

    /**
     * Returns the width of the bounding <code>Rectangle</code> in
     * <code>double</code> precision.
     * <p>
     *  返回<code> double </code>精度中的边界<code> Rectangle </code>的宽度。
     * 
     * 
     * @return the width of the bounding <code>Rectangle</code>.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Returns the height of the bounding <code>Rectangle</code> in
     * <code>double</code> precision.
     * <p>
     *  返回<code> double </code>精度中边界<code> Rectangle </code>的高度。
     * 
     * 
     * @return the height of the bounding <code>Rectangle</code>.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Gets the bounding <code>Rectangle</code> of this <code>Rectangle</code>.
     * <p>
     * This method is included for completeness, to parallel the
     * <code>getBounds</code> method of
     * {@link Component}.
     * <p>
     *  获取此<code> Rectangle </code>的边界<code> Rectangle </code>。
     * <p>
     *  包括这个方法的完整性,以并行{@link Component}的<code> getBounds </code>方法。
     * 
     * 
     * @return    a new <code>Rectangle</code>, equal to the
     * bounding <code>Rectangle</code> for this <code>Rectangle</code>.
     * @see       java.awt.Component#getBounds
     * @see       #setBounds(Rectangle)
     * @see       #setBounds(int, int, int, int)
     * @since     1.1
     */
    @Transient
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.2
     */
    public Rectangle2D getBounds2D() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * Sets the bounding <code>Rectangle</code> of this <code>Rectangle</code>
     * to match the specified <code>Rectangle</code>.
     * <p>
     * This method is included for completeness, to parallel the
     * <code>setBounds</code> method of <code>Component</code>.
     * <p>
     *  设置此<code> Rectangle </code>的边界<code> Rectangle </code>以匹配指定的<code> Rectangle </code>。
     * <p>
     *  此方法包括完整性,以并行<code>组件</code>的<code> setBounds </code>方法。
     * 
     * 
     * @param r the specified <code>Rectangle</code>
     * @see       #getBounds
     * @see       java.awt.Component#setBounds(java.awt.Rectangle)
     * @since     1.1
     */
    public void setBounds(Rectangle r) {
        setBounds(r.x, r.y, r.width, r.height);
    }

    /**
     * Sets the bounding <code>Rectangle</code> of this
     * <code>Rectangle</code> to the specified
     * <code>x</code>, <code>y</code>, <code>width</code>,
     * and <code>height</code>.
     * <p>
     * This method is included for completeness, to parallel the
     * <code>setBounds</code> method of <code>Component</code>.
     * <p>
     *  将<code> Rectangle </code>的边界设置为指定的<code> x </code>,<code> y </code>,<code> width </code> ,<code> hei
     * ght </code>。
     * <p>
     *  此方法包括完整性,以并行<code>组件</code>的<code> setBounds </code>方法。
     * 
     * 
     * @param x the new X coordinate for the upper-left
     *                    corner of this <code>Rectangle</code>
     * @param y the new Y coordinate for the upper-left
     *                    corner of this <code>Rectangle</code>
     * @param width the new width for this <code>Rectangle</code>
     * @param height the new height for this <code>Rectangle</code>
     * @see       #getBounds
     * @see       java.awt.Component#setBounds(int, int, int, int)
     * @since     1.1
     */
    public void setBounds(int x, int y, int width, int height) {
        reshape(x, y, width, height);
    }

    /**
     * Sets the bounds of this {@code Rectangle} to the integer bounds
     * which encompass the specified {@code x}, {@code y}, {@code width},
     * and {@code height}.
     * If the parameters specify a {@code Rectangle} that exceeds the
     * maximum range of integers, the result will be the best
     * representation of the specified {@code Rectangle} intersected
     * with the maximum integer bounds.
     * <p>
     *  将此{@code Rectangle}的边界设置为包含指定的{@code x},{@code y},{@code width}和{@code height}的整数边界。
     * 如果参数指定超过整数的最大范围的{@code Rectangle},结果将是指定的{@code Rectangle}与最大整数边界相交的最佳表示。
     * 
     * 
     * @param x the X coordinate of the upper-left corner of
     *                  the specified rectangle
     * @param y the Y coordinate of the upper-left corner of
     *                  the specified rectangle
     * @param width the width of the specified rectangle
     * @param height the new height of the specified rectangle
     */
    public void setRect(double x, double y, double width, double height) {
        int newx, newy, neww, newh;

        if (x > 2.0 * Integer.MAX_VALUE) {
            // Too far in positive X direction to represent...
            // We cannot even reach the left side of the specified
            // rectangle even with both x & width set to MAX_VALUE.
            // The intersection with the "maximal integer rectangle"
            // is non-existant so we should use a width < 0.
            // REMIND: Should we try to determine a more "meaningful"
            // adjusted value for neww than just "-1"?
            newx = Integer.MAX_VALUE;
            neww = -1;
        } else {
            newx = clip(x, false);
            if (width >= 0) width += x-newx;
            neww = clip(width, width >= 0);
        }

        if (y > 2.0 * Integer.MAX_VALUE) {
            // Too far in positive Y direction to represent...
            newy = Integer.MAX_VALUE;
            newh = -1;
        } else {
            newy = clip(y, false);
            if (height >= 0) height += y-newy;
            newh = clip(height, height >= 0);
        }

        reshape(newx, newy, neww, newh);
    }
    // Return best integer representation for v, clipped to integer
    // range and floor-ed or ceiling-ed, depending on the boolean.
    private static int clip(double v, boolean doceil) {
        if (v <= Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        if (v >= Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return (int) (doceil ? Math.ceil(v) : Math.floor(v));
    }

    /**
     * Sets the bounding <code>Rectangle</code> of this
     * <code>Rectangle</code> to the specified
     * <code>x</code>, <code>y</code>, <code>width</code>,
     * and <code>height</code>.
     * <p>
     * <p>
     * 将<code> Rectangle </code>的边界设置为指定的<code> x </code>,<code> y </code>,<code> width </code> ,<code> heig
     * ht </code>。
     * <p>
     * 
     * @param x the new X coordinate for the upper-left
     *                    corner of this <code>Rectangle</code>
     * @param y the new Y coordinate for the upper-left
     *                    corner of this <code>Rectangle</code>
     * @param width the new width for this <code>Rectangle</code>
     * @param height the new height for this <code>Rectangle</code>
     * @deprecated As of JDK version 1.1,
     * replaced by <code>setBounds(int, int, int, int)</code>.
     */
    @Deprecated
    public void reshape(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Returns the location of this <code>Rectangle</code>.
     * <p>
     * This method is included for completeness, to parallel the
     * <code>getLocation</code> method of <code>Component</code>.
     * <p>
     *  返回此<code> Rectangle </code>的位置。
     * <p>
     *  包括这个方法的完整性,以并行<code> Component </code>的<code> getLocation </code>方法。
     * 
     * 
     * @return the <code>Point</code> that is the upper-left corner of
     *                  this <code>Rectangle</code>.
     * @see       java.awt.Component#getLocation
     * @see       #setLocation(Point)
     * @see       #setLocation(int, int)
     * @since     1.1
     */
    public Point getLocation() {
        return new Point(x, y);
    }

    /**
     * Moves this <code>Rectangle</code> to the specified location.
     * <p>
     * This method is included for completeness, to parallel the
     * <code>setLocation</code> method of <code>Component</code>.
     * <p>
     *  将此<code> Rectangle </code>移动到指定位置。
     * <p>
     *  这个方法包括完整性,以并行<code>组件</code>的<code> setLocation </code>方法。
     * 
     * 
     * @param p the <code>Point</code> specifying the new location
     *                for this <code>Rectangle</code>
     * @see       java.awt.Component#setLocation(java.awt.Point)
     * @see       #getLocation
     * @since     1.1
     */
    public void setLocation(Point p) {
        setLocation(p.x, p.y);
    }

    /**
     * Moves this <code>Rectangle</code> to the specified location.
     * <p>
     * This method is included for completeness, to parallel the
     * <code>setLocation</code> method of <code>Component</code>.
     * <p>
     *  将此<code> Rectangle </code>移动到指定位置。
     * <p>
     *  这个方法包括完整性,以并行<code>组件</code>的<code> setLocation </code>方法。
     * 
     * 
     * @param x the X coordinate of the new location
     * @param y the Y coordinate of the new location
     * @see       #getLocation
     * @see       java.awt.Component#setLocation(int, int)
     * @since     1.1
     */
    public void setLocation(int x, int y) {
        move(x, y);
    }

    /**
     * Moves this <code>Rectangle</code> to the specified location.
     * <p>
     * <p>
     *  将此<code> Rectangle </code>移动到指定位置。
     * <p>
     * 
     * @param x the X coordinate of the new location
     * @param y the Y coordinate of the new location
     * @deprecated As of JDK version 1.1,
     * replaced by <code>setLocation(int, int)</code>.
     */
    @Deprecated
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Translates this <code>Rectangle</code> the indicated distance,
     * to the right along the X coordinate axis, and
     * downward along the Y coordinate axis.
     * <p>
     *  将所指示的距离转换为<code> Rectangle </code>,沿着X坐标轴向右,沿着Y坐标轴向下。
     * 
     * 
     * @param dx the distance to move this <code>Rectangle</code>
     *                 along the X axis
     * @param dy the distance to move this <code>Rectangle</code>
     *                 along the Y axis
     * @see       java.awt.Rectangle#setLocation(int, int)
     * @see       java.awt.Rectangle#setLocation(java.awt.Point)
     */
    public void translate(int dx, int dy) {
        int oldv = this.x;
        int newv = oldv + dx;
        if (dx < 0) {
            // moving leftward
            if (newv > oldv) {
                // negative overflow
                // Only adjust width if it was valid (>= 0).
                if (width >= 0) {
                    // The right edge is now conceptually at
                    // newv+width, but we may move newv to prevent
                    // overflow.  But we want the right edge to
                    // remain at its new location in spite of the
                    // clipping.  Think of the following adjustment
                    // conceptually the same as:
                    // width += newv; newv = MIN_VALUE; width -= newv;
                    width += newv - Integer.MIN_VALUE;
                    // width may go negative if the right edge went past
                    // MIN_VALUE, but it cannot overflow since it cannot
                    // have moved more than MIN_VALUE and any non-negative
                    // number + MIN_VALUE does not overflow.
                }
                newv = Integer.MIN_VALUE;
            }
        } else {
            // moving rightward (or staying still)
            if (newv < oldv) {
                // positive overflow
                if (width >= 0) {
                    // Conceptually the same as:
                    // width += newv; newv = MAX_VALUE; width -= newv;
                    width += newv - Integer.MAX_VALUE;
                    // With large widths and large displacements
                    // we may overflow so we need to check it.
                    if (width < 0) width = Integer.MAX_VALUE;
                }
                newv = Integer.MAX_VALUE;
            }
        }
        this.x = newv;

        oldv = this.y;
        newv = oldv + dy;
        if (dy < 0) {
            // moving upward
            if (newv > oldv) {
                // negative overflow
                if (height >= 0) {
                    height += newv - Integer.MIN_VALUE;
                    // See above comment about no overflow in this case
                }
                newv = Integer.MIN_VALUE;
            }
        } else {
            // moving downward (or staying still)
            if (newv < oldv) {
                // positive overflow
                if (height >= 0) {
                    height += newv - Integer.MAX_VALUE;
                    if (height < 0) height = Integer.MAX_VALUE;
                }
                newv = Integer.MAX_VALUE;
            }
        }
        this.y = newv;
    }

    /**
     * Gets the size of this <code>Rectangle</code>, represented by
     * the returned <code>Dimension</code>.
     * <p>
     * This method is included for completeness, to parallel the
     * <code>getSize</code> method of <code>Component</code>.
     * <p>
     *  获取此<code> Rectangle </code>的大小,由返回的<code> Dimension </code>表示。
     * <p>
     *  包括这个方法的完整性,以并行<code>组件</code>的<code> getSize </code>方法。
     * 
     * 
     * @return a <code>Dimension</code>, representing the size of
     *            this <code>Rectangle</code>.
     * @see       java.awt.Component#getSize
     * @see       #setSize(Dimension)
     * @see       #setSize(int, int)
     * @since     1.1
     */
    public Dimension getSize() {
        return new Dimension(width, height);
    }

    /**
     * Sets the size of this <code>Rectangle</code> to match the
     * specified <code>Dimension</code>.
     * <p>
     * This method is included for completeness, to parallel the
     * <code>setSize</code> method of <code>Component</code>.
     * <p>
     *  设置此<code> Rectangle </code>的大小以匹配指定的<code> Dimension </code>。
     * <p>
     *  此方法包括完整性,以并行<code>组件</code>的<code> setSize </code>方法。
     * 
     * 
     * @param d the new size for the <code>Dimension</code> object
     * @see       java.awt.Component#setSize(java.awt.Dimension)
     * @see       #getSize
     * @since     1.1
     */
    public void setSize(Dimension d) {
        setSize(d.width, d.height);
    }

    /**
     * Sets the size of this <code>Rectangle</code> to the specified
     * width and height.
     * <p>
     * This method is included for completeness, to parallel the
     * <code>setSize</code> method of <code>Component</code>.
     * <p>
     *  将<code> Rectangle </code>的大小设置为指定的宽度和高度。
     * <p>
     * 此方法包括完整性,以并行<code>组件</code>的<code> setSize </code>方法。
     * 
     * 
     * @param width the new width for this <code>Rectangle</code>
     * @param height the new height for this <code>Rectangle</code>
     * @see       java.awt.Component#setSize(int, int)
     * @see       #getSize
     * @since     1.1
     */
    public void setSize(int width, int height) {
        resize(width, height);
    }

    /**
     * Sets the size of this <code>Rectangle</code> to the specified
     * width and height.
     * <p>
     * <p>
     *  将<code> Rectangle </code>的大小设置为指定的宽度和高度。
     * <p>
     * 
     * @param width the new width for this <code>Rectangle</code>
     * @param height the new height for this <code>Rectangle</code>
     * @deprecated As of JDK version 1.1,
     * replaced by <code>setSize(int, int)</code>.
     */
    @Deprecated
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Checks whether or not this <code>Rectangle</code> contains the
     * specified <code>Point</code>.
     * <p>
     *  检查此<code> Rectangle </code>是否包含指定的<code> Point </code>。
     * 
     * 
     * @param p the <code>Point</code> to test
     * @return    <code>true</code> if the specified <code>Point</code>
     *            is inside this <code>Rectangle</code>;
     *            <code>false</code> otherwise.
     * @since     1.1
     */
    public boolean contains(Point p) {
        return contains(p.x, p.y);
    }

    /**
     * Checks whether or not this <code>Rectangle</code> contains the
     * point at the specified location {@code (x,y)}.
     *
     * <p>
     *  检查此<code> Rectangle </code>是否包含指定位置{@code(x,y)}处的点。
     * 
     * 
     * @param  x the specified X coordinate
     * @param  y the specified Y coordinate
     * @return    <code>true</code> if the point
     *            {@code (x,y)} is inside this
     *            <code>Rectangle</code>;
     *            <code>false</code> otherwise.
     * @since     1.1
     */
    public boolean contains(int x, int y) {
        return inside(x, y);
    }

    /**
     * Checks whether or not this <code>Rectangle</code> entirely contains
     * the specified <code>Rectangle</code>.
     *
     * <p>
     *  检查此<code> Rectangle </code>是否完全包含指定的<code> Rectangle </code>。
     * 
     * 
     * @param     r   the specified <code>Rectangle</code>
     * @return    <code>true</code> if the <code>Rectangle</code>
     *            is contained entirely inside this <code>Rectangle</code>;
     *            <code>false</code> otherwise
     * @since     1.2
     */
    public boolean contains(Rectangle r) {
        return contains(r.x, r.y, r.width, r.height);
    }

    /**
     * Checks whether this <code>Rectangle</code> entirely contains
     * the <code>Rectangle</code>
     * at the specified location {@code (X,Y)} with the
     * specified dimensions {@code (W,H)}.
     * <p>
     *  检查此<code> Rectangle </code>是否完全包含指定维度{@code(W,H)}的指定位置{@code(X,Y)}处的<code> Rectangle </code>。
     * 
     * 
     * @param     X the specified X coordinate
     * @param     Y the specified Y coordinate
     * @param     W   the width of the <code>Rectangle</code>
     * @param     H   the height of the <code>Rectangle</code>
     * @return    <code>true</code> if the <code>Rectangle</code> specified by
     *            {@code (X, Y, W, H)}
     *            is entirely enclosed inside this <code>Rectangle</code>;
     *            <code>false</code> otherwise.
     * @since     1.1
     */
    public boolean contains(int X, int Y, int W, int H) {
        int w = this.width;
        int h = this.height;
        if ((w | h | W | H) < 0) {
            // At least one of the dimensions is negative...
            return false;
        }
        // Note: if any dimension is zero, tests below must return false...
        int x = this.x;
        int y = this.y;
        if (X < x || Y < y) {
            return false;
        }
        w += x;
        W += X;
        if (W <= X) {
            // X+W overflowed or W was zero, return false if...
            // either original w or W was zero or
            // x+w did not overflow or
            // the overflowed x+w is smaller than the overflowed X+W
            if (w >= x || W > w) return false;
        } else {
            // X+W did not overflow and W was not zero, return false if...
            // original w was zero or
            // x+w did not overflow and x+w is smaller than X+W
            if (w >= x && W > w) return false;
        }
        h += y;
        H += Y;
        if (H <= Y) {
            if (h >= y || H > h) return false;
        } else {
            if (h >= y && H > h) return false;
        }
        return true;
    }

    /**
     * Checks whether or not this <code>Rectangle</code> contains the
     * point at the specified location {@code (X,Y)}.
     *
     * <p>
     *  检查此<code> Rectangle </code>是否包含指定位置{@code(X,Y)}处的点。
     * 
     * 
     * @param  X the specified X coordinate
     * @param  Y the specified Y coordinate
     * @return    <code>true</code> if the point
     *            {@code (X,Y)} is inside this
     *            <code>Rectangle</code>;
     *            <code>false</code> otherwise.
     * @deprecated As of JDK version 1.1,
     * replaced by <code>contains(int, int)</code>.
     */
    @Deprecated
    public boolean inside(int X, int Y) {
        int w = this.width;
        int h = this.height;
        if ((w | h) < 0) {
            // At least one of the dimensions is negative...
            return false;
        }
        // Note: if either dimension is zero, tests below must return false...
        int x = this.x;
        int y = this.y;
        if (X < x || Y < y) {
            return false;
        }
        w += x;
        h += y;
        //    overflow || intersect
        return ((w < x || w > X) &&
                (h < y || h > Y));
    }

    /**
     * Determines whether or not this <code>Rectangle</code> and the specified
     * <code>Rectangle</code> intersect. Two rectangles intersect if
     * their intersection is nonempty.
     *
     * <p>
     *  确定此<x> Rectangle </code>和指定的<code> Rectangle </code>是否相交。如果两个矩形的交集是非空的,则它们相交。
     * 
     * 
     * @param r the specified <code>Rectangle</code>
     * @return    <code>true</code> if the specified <code>Rectangle</code>
     *            and this <code>Rectangle</code> intersect;
     *            <code>false</code> otherwise.
     */
    public boolean intersects(Rectangle r) {
        int tw = this.width;
        int th = this.height;
        int rw = r.width;
        int rh = r.height;
        if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
            return false;
        }
        int tx = this.x;
        int ty = this.y;
        int rx = r.x;
        int ry = r.y;
        rw += rx;
        rh += ry;
        tw += tx;
        th += ty;
        //      overflow || intersect
        return ((rw < rx || rw > tx) &&
                (rh < ry || rh > ty) &&
                (tw < tx || tw > rx) &&
                (th < ty || th > ry));
    }

    /**
     * Computes the intersection of this <code>Rectangle</code> with the
     * specified <code>Rectangle</code>. Returns a new <code>Rectangle</code>
     * that represents the intersection of the two rectangles.
     * If the two rectangles do not intersect, the result will be
     * an empty rectangle.
     *
     * <p>
     *  计算此<code> Rectangle </code>与指定<code> Rectangle </code>的交集。返回一个新的<code> Rectangle </code>,表示两个矩形的交集。
     * 如果两个矩形不相交,结果将是一个空矩形。
     * 
     * 
     * @param     r   the specified <code>Rectangle</code>
     * @return    the largest <code>Rectangle</code> contained in both the
     *            specified <code>Rectangle</code> and in
     *            this <code>Rectangle</code>; or if the rectangles
     *            do not intersect, an empty rectangle.
     */
    public Rectangle intersection(Rectangle r) {
        int tx1 = this.x;
        int ty1 = this.y;
        int rx1 = r.x;
        int ry1 = r.y;
        long tx2 = tx1; tx2 += this.width;
        long ty2 = ty1; ty2 += this.height;
        long rx2 = rx1; rx2 += r.width;
        long ry2 = ry1; ry2 += r.height;
        if (tx1 < rx1) tx1 = rx1;
        if (ty1 < ry1) ty1 = ry1;
        if (tx2 > rx2) tx2 = rx2;
        if (ty2 > ry2) ty2 = ry2;
        tx2 -= tx1;
        ty2 -= ty1;
        // tx2,ty2 will never overflow (they will never be
        // larger than the smallest of the two source w,h)
        // they might underflow, though...
        if (tx2 < Integer.MIN_VALUE) tx2 = Integer.MIN_VALUE;
        if (ty2 < Integer.MIN_VALUE) ty2 = Integer.MIN_VALUE;
        return new Rectangle(tx1, ty1, (int) tx2, (int) ty2);
    }

    /**
     * Computes the union of this <code>Rectangle</code> with the
     * specified <code>Rectangle</code>. Returns a new
     * <code>Rectangle</code> that
     * represents the union of the two rectangles.
     * <p>
     * If either {@code Rectangle} has any dimension less than zero
     * the rules for <a href=#NonExistant>non-existant</a> rectangles
     * apply.
     * If only one has a dimension less than zero, then the result
     * will be a copy of the other {@code Rectangle}.
     * If both have dimension less than zero, then the result will
     * have at least one dimension less than zero.
     * <p>
     * If the resulting {@code Rectangle} would have a dimension
     * too large to be expressed as an {@code int}, the result
     * will have a dimension of {@code Integer.MAX_VALUE} along
     * that dimension.
     * <p>
     *  使用指定的<code> Rectangle </code>计算此<code> Rectangle </code>的并集。
     * 返回一个新的<code> Rectangle </code>,表示两个矩形的并集。
     * <p>
     * 如果{@code Rectangle}的尺寸小于零,则<a href=#NonExistant>不存在</a>矩形的规则适用。
     * 如果只有一个的维度小于零,那么结果将是另一个{@code Rectangle}的副本。如果两者都具有小于零的维度,则结果将具有小于零的至少一个维度。
     * <p>
     *  如果生成的{@code Rectangle}的维度太大,无法用{@code int}表示,则该维度的维度为{@code Integer.MAX_VALUE}。
     * 
     * 
     * @param r the specified <code>Rectangle</code>
     * @return    the smallest <code>Rectangle</code> containing both
     *            the specified <code>Rectangle</code> and this
     *            <code>Rectangle</code>.
     */
    public Rectangle union(Rectangle r) {
        long tx2 = this.width;
        long ty2 = this.height;
        if ((tx2 | ty2) < 0) {
            // This rectangle has negative dimensions...
            // If r has non-negative dimensions then it is the answer.
            // If r is non-existant (has a negative dimension), then both
            // are non-existant and we can return any non-existant rectangle
            // as an answer.  Thus, returning r meets that criterion.
            // Either way, r is our answer.
            return new Rectangle(r);
        }
        long rx2 = r.width;
        long ry2 = r.height;
        if ((rx2 | ry2) < 0) {
            return new Rectangle(this);
        }
        int tx1 = this.x;
        int ty1 = this.y;
        tx2 += tx1;
        ty2 += ty1;
        int rx1 = r.x;
        int ry1 = r.y;
        rx2 += rx1;
        ry2 += ry1;
        if (tx1 > rx1) tx1 = rx1;
        if (ty1 > ry1) ty1 = ry1;
        if (tx2 < rx2) tx2 = rx2;
        if (ty2 < ry2) ty2 = ry2;
        tx2 -= tx1;
        ty2 -= ty1;
        // tx2,ty2 will never underflow since both original rectangles
        // were already proven to be non-empty
        // they might overflow, though...
        if (tx2 > Integer.MAX_VALUE) tx2 = Integer.MAX_VALUE;
        if (ty2 > Integer.MAX_VALUE) ty2 = Integer.MAX_VALUE;
        return new Rectangle(tx1, ty1, (int) tx2, (int) ty2);
    }

    /**
     * Adds a point, specified by the integer arguments {@code newx,newy}
     * to the bounds of this {@code Rectangle}.
     * <p>
     * If this {@code Rectangle} has any dimension less than zero,
     * the rules for <a href=#NonExistant>non-existant</a>
     * rectangles apply.
     * In that case, the new bounds of this {@code Rectangle} will
     * have a location equal to the specified coordinates and
     * width and height equal to zero.
     * <p>
     * After adding a point, a call to <code>contains</code> with the
     * added point as an argument does not necessarily return
     * <code>true</code>. The <code>contains</code> method does not
     * return <code>true</code> for points on the right or bottom
     * edges of a <code>Rectangle</code>. Therefore, if the added point
     * falls on the right or bottom edge of the enlarged
     * <code>Rectangle</code>, <code>contains</code> returns
     * <code>false</code> for that point.
     * If the specified point must be contained within the new
     * {@code Rectangle}, a 1x1 rectangle should be added instead:
     * <pre>
     *     r.add(newx, newy, 1, 1);
     * </pre>
     * <p>
     *  将整数参数{@code newx,newy}指定的点添加到此{@code Rectangle}的边界。
     * <p>
     *  如果此{@code Rectangle}的尺寸小于零,则<a href=#NonExistant>不存在</a>矩形的规则适用。
     * 在这种情况下,此{@code Rectangle}的新边界的位置将等于指定的坐标,宽度和高度等于零。
     * <p>
     *  添加一个点之后,调用<code>包含</code>与添加的点作为参数不一定返回<code> true </code>。
     *  <code>包含</code>方法不会为<code> Rectangle </code>的右边或底边的点返回<code> true </code>。
     * 因此,如果添加的点落在放大的<code> Rectangle </code>的右边或底部边缘,则<code>包含</code>会返回<code> false </code>。
     * 如果指定点必须包含在新的{@code Rectangle}中,则应添加1x1矩形：。
     * <pre>
     * r.add(newx,newy,1,1);
     * </pre>
     * 
     * @param newx the X coordinate of the new point
     * @param newy the Y coordinate of the new point
     */
    public void add(int newx, int newy) {
        if ((width | height) < 0) {
            this.x = newx;
            this.y = newy;
            this.width = this.height = 0;
            return;
        }
        int x1 = this.x;
        int y1 = this.y;
        long x2 = this.width;
        long y2 = this.height;
        x2 += x1;
        y2 += y1;
        if (x1 > newx) x1 = newx;
        if (y1 > newy) y1 = newy;
        if (x2 < newx) x2 = newx;
        if (y2 < newy) y2 = newy;
        x2 -= x1;
        y2 -= y1;
        if (x2 > Integer.MAX_VALUE) x2 = Integer.MAX_VALUE;
        if (y2 > Integer.MAX_VALUE) y2 = Integer.MAX_VALUE;
        reshape(x1, y1, (int) x2, (int) y2);
    }

    /**
     * Adds the specified {@code Point} to the bounds of this
     * {@code Rectangle}.
     * <p>
     * If this {@code Rectangle} has any dimension less than zero,
     * the rules for <a href=#NonExistant>non-existant</a>
     * rectangles apply.
     * In that case, the new bounds of this {@code Rectangle} will
     * have a location equal to the coordinates of the specified
     * {@code Point} and width and height equal to zero.
     * <p>
     * After adding a <code>Point</code>, a call to <code>contains</code>
     * with the added <code>Point</code> as an argument does not
     * necessarily return <code>true</code>. The <code>contains</code>
     * method does not return <code>true</code> for points on the right
     * or bottom edges of a <code>Rectangle</code>. Therefore if the added
     * <code>Point</code> falls on the right or bottom edge of the
     * enlarged <code>Rectangle</code>, <code>contains</code> returns
     * <code>false</code> for that <code>Point</code>.
     * If the specified point must be contained within the new
     * {@code Rectangle}, a 1x1 rectangle should be added instead:
     * <pre>
     *     r.add(pt.x, pt.y, 1, 1);
     * </pre>
     * <p>
     *  将指定的{@code Point}添加到此{@code Rectangle}的边界。
     * <p>
     *  如果此{@code Rectangle}的尺寸小于零,则<a href=#NonExistant>不存在</a>矩形的规则适用。
     * 在这种情况下,此{@code Rectangle}的新边界的位置将等于指定的{@code Point}的坐标,宽度和高度等于零。
     * <p>
     *  添加<code> Point </code>之后,调用<code>包含</code>,添加<code> Point </code>作为参数不一定返回<code> true </code>。
     *  <code>包含</code>方法不会为<code> Rectangle </code>的右边或底边的点返回<code> true </code>。
     * 因此,如果添加的<code> Point </code>落在放大的<code> Rectangle </code>的右边或底部边缘,则<code>包含</code>返回<code> false </code>
     *  <code> Point </code>。
     *  <code>包含</code>方法不会为<code> Rectangle </code>的右边或底边的点返回<code> true </code>。
     * 如果指定点必须包含在新的{@code Rectangle}中,则应添加1x1矩形：。
     * <pre>
     *  r.add(pt.x,pt.y,1,1);
     * </pre>
     * 
     * @param pt the new <code>Point</code> to add to this
     *           <code>Rectangle</code>
     */
    public void add(Point pt) {
        add(pt.x, pt.y);
    }

    /**
     * Adds a <code>Rectangle</code> to this <code>Rectangle</code>.
     * The resulting <code>Rectangle</code> is the union of the two
     * rectangles.
     * <p>
     * If either {@code Rectangle} has any dimension less than 0, the
     * result will have the dimensions of the other {@code Rectangle}.
     * If both {@code Rectangle}s have at least one dimension less
     * than 0, the result will have at least one dimension less than 0.
     * <p>
     * If either {@code Rectangle} has one or both dimensions equal
     * to 0, the result along those axes with 0 dimensions will be
     * equivalent to the results obtained by adding the corresponding
     * origin coordinate to the result rectangle along that axis,
     * similar to the operation of the {@link #add(Point)} method,
     * but contribute no further dimension beyond that.
     * <p>
     * If the resulting {@code Rectangle} would have a dimension
     * too large to be expressed as an {@code int}, the result
     * will have a dimension of {@code Integer.MAX_VALUE} along
     * that dimension.
     * <p>
     *  向此<code> Rectangle </code>中添加<code> Rectangle </code>。结果<code> Rectangle </code>是两个矩形的联合。
     * <p>
     *  如果{@code Rectangle}的任何维度小于0,则结果将具有另一个{@code Rectangle}的维度。
     * 如果两个{@code Rectangle}的至少一个维度小于0,则结果将至少有一个维度小于0。
     * <p>
     * 如果{@code Rectangle}的一个或两个维度等于0,那么沿着那些具有0维度的轴的结果将等同于通过将相应的原始坐标添加到沿着该轴的结果矩形中获得的结果,类似于{@link #add(Point)}
     * 方法,但不会提供更多的维度。
     * <p>
     *  如果生成的{@code Rectangle}的维度太大,无法用{@code int}表示,则该维度的维度为{@code Integer.MAX_VALUE}。
     * 
     * 
     * @param  r the specified <code>Rectangle</code>
     */
    public void add(Rectangle r) {
        long tx2 = this.width;
        long ty2 = this.height;
        if ((tx2 | ty2) < 0) {
            reshape(r.x, r.y, r.width, r.height);
        }
        long rx2 = r.width;
        long ry2 = r.height;
        if ((rx2 | ry2) < 0) {
            return;
        }
        int tx1 = this.x;
        int ty1 = this.y;
        tx2 += tx1;
        ty2 += ty1;
        int rx1 = r.x;
        int ry1 = r.y;
        rx2 += rx1;
        ry2 += ry1;
        if (tx1 > rx1) tx1 = rx1;
        if (ty1 > ry1) ty1 = ry1;
        if (tx2 < rx2) tx2 = rx2;
        if (ty2 < ry2) ty2 = ry2;
        tx2 -= tx1;
        ty2 -= ty1;
        // tx2,ty2 will never underflow since both original
        // rectangles were non-empty
        // they might overflow, though...
        if (tx2 > Integer.MAX_VALUE) tx2 = Integer.MAX_VALUE;
        if (ty2 > Integer.MAX_VALUE) ty2 = Integer.MAX_VALUE;
        reshape(tx1, ty1, (int) tx2, (int) ty2);
    }

    /**
     * Resizes the <code>Rectangle</code> both horizontally and vertically.
     * <p>
     * This method modifies the <code>Rectangle</code> so that it is
     * <code>h</code> units larger on both the left and right side,
     * and <code>v</code> units larger at both the top and bottom.
     * <p>
     * The new <code>Rectangle</code> has {@code (x - h, y - v)}
     * as its upper-left corner,
     * width of {@code (width + 2h)},
     * and a height of {@code (height + 2v)}.
     * <p>
     * If negative values are supplied for <code>h</code> and
     * <code>v</code>, the size of the <code>Rectangle</code>
     * decreases accordingly.
     * The {@code grow} method will check for integer overflow
     * and underflow, but does not check whether the resulting
     * values of {@code width} and {@code height} grow
     * from negative to non-negative or shrink from non-negative
     * to negative.
     * <p>
     *  水平和垂直调整<code> Rectangle </code>的大小。
     * <p>
     *  此方法修改<code> Rectangle </code>,使得左侧和右侧的<code> h </code>单位更大,顶部和右侧的<code> v </code>底部。
     * <p>
     *  新的<code> Rectangle </code>具有{@code(x  -  h,y  -  v)}作为其左上角,{@code(width + 2h)}的宽度和{@code (高度+ 2v)}。
     * <p>
     *  如果为<code> h </code>和<code> v </code>提供负值,则<code> Rectangle </code>的大小会相应减小。
     *  {@code grow}方法将检查整数溢出和下溢,但不检查{@code width}和{@code height}的结果值是从负值增加到非负值还是从非负值减小到负值。
     * 
     * 
     * @param h the horizontal expansion
     * @param v the vertical expansion
     */
    public void grow(int h, int v) {
        long x0 = this.x;
        long y0 = this.y;
        long x1 = this.width;
        long y1 = this.height;
        x1 += x0;
        y1 += y0;

        x0 -= h;
        y0 -= v;
        x1 += h;
        y1 += v;

        if (x1 < x0) {
            // Non-existant in X direction
            // Final width must remain negative so subtract x0 before
            // it is clipped so that we avoid the risk that the clipping
            // of x0 will reverse the ordering of x0 and x1.
            x1 -= x0;
            if (x1 < Integer.MIN_VALUE) x1 = Integer.MIN_VALUE;
            if (x0 < Integer.MIN_VALUE) x0 = Integer.MIN_VALUE;
            else if (x0 > Integer.MAX_VALUE) x0 = Integer.MAX_VALUE;
        } else { // (x1 >= x0)
            // Clip x0 before we subtract it from x1 in case the clipping
            // affects the representable area of the rectangle.
            if (x0 < Integer.MIN_VALUE) x0 = Integer.MIN_VALUE;
            else if (x0 > Integer.MAX_VALUE) x0 = Integer.MAX_VALUE;
            x1 -= x0;
            // The only way x1 can be negative now is if we clipped
            // x0 against MIN and x1 is less than MIN - in which case
            // we want to leave the width negative since the result
            // did not intersect the representable area.
            if (x1 < Integer.MIN_VALUE) x1 = Integer.MIN_VALUE;
            else if (x1 > Integer.MAX_VALUE) x1 = Integer.MAX_VALUE;
        }

        if (y1 < y0) {
            // Non-existant in Y direction
            y1 -= y0;
            if (y1 < Integer.MIN_VALUE) y1 = Integer.MIN_VALUE;
            if (y0 < Integer.MIN_VALUE) y0 = Integer.MIN_VALUE;
            else if (y0 > Integer.MAX_VALUE) y0 = Integer.MAX_VALUE;
        } else { // (y1 >= y0)
            if (y0 < Integer.MIN_VALUE) y0 = Integer.MIN_VALUE;
            else if (y0 > Integer.MAX_VALUE) y0 = Integer.MAX_VALUE;
            y1 -= y0;
            if (y1 < Integer.MIN_VALUE) y1 = Integer.MIN_VALUE;
            else if (y1 > Integer.MAX_VALUE) y1 = Integer.MAX_VALUE;
        }

        reshape((int) x0, (int) y0, (int) x1, (int) y1);
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.2
     */
    public boolean isEmpty() {
        return (width <= 0) || (height <= 0);
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.2
     */
    public int outcode(double x, double y) {
        /*
         * Note on casts to double below.  If the arithmetic of
         * x+w or y+h is done in int, then we may get integer
         * overflow. By converting to double before the addition
         * we force the addition to be carried out in double to
         * avoid overflow in the comparison.
         *
         * See bug 4320890 for problems that this can cause.
         * <p>
         * 注意转换为双下面。如果x + w或y + h的算术在int中完成,那么我们可能得到整数溢出。通过在加法之前转换为双倍,我们强制加法以双倍进行,以避免在比较中溢出。
         * 
         *  有关可能导致的问题,请参阅错误4320890。
         * 
         */
        int out = 0;
        if (this.width <= 0) {
            out |= OUT_LEFT | OUT_RIGHT;
        } else if (x < this.x) {
            out |= OUT_LEFT;
        } else if (x > this.x + (double) this.width) {
            out |= OUT_RIGHT;
        }
        if (this.height <= 0) {
            out |= OUT_TOP | OUT_BOTTOM;
        } else if (y < this.y) {
            out |= OUT_TOP;
        } else if (y > this.y + (double) this.height) {
            out |= OUT_BOTTOM;
        }
        return out;
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.2
     */
    public Rectangle2D createIntersection(Rectangle2D r) {
        if (r instanceof Rectangle) {
            return intersection((Rectangle) r);
        }
        Rectangle2D dest = new Rectangle2D.Double();
        Rectangle2D.intersect(this, r, dest);
        return dest;
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.2
     */
    public Rectangle2D createUnion(Rectangle2D r) {
        if (r instanceof Rectangle) {
            return union((Rectangle) r);
        }
        Rectangle2D dest = new Rectangle2D.Double();
        Rectangle2D.union(this, r, dest);
        return dest;
    }

    /**
     * Checks whether two rectangles are equal.
     * <p>
     * The result is <code>true</code> if and only if the argument is not
     * <code>null</code> and is a <code>Rectangle</code> object that has the
     * same upper-left corner, width, and height as
     * this <code>Rectangle</code>.
     * <p>
     *  检查两个矩形是否相等。
     * <p>
     *  如果且仅当参数不是<code> null </code>并且是具有相同左上角,宽度的<code> Rectangle </code>对象,结果是<code> true </code>和高度为<code>
     *  Rectangle </code>。
     * 
     * @param obj the <code>Object</code> to compare with
     *                this <code>Rectangle</code>
     * @return    <code>true</code> if the objects are equal;
     *            <code>false</code> otherwise.
     */
    public boolean equals(Object obj) {
        if (obj instanceof Rectangle) {
            Rectangle r = (Rectangle)obj;
            return ((x == r.x) &&
                    (y == r.y) &&
                    (width == r.width) &&
                    (height == r.height));
        }
        return super.equals(obj);
    }

    /**
     * Returns a <code>String</code> representing this
     * <code>Rectangle</code> and its values.
     * <p>
     * 
     * 
     * @return a <code>String</code> representing this
     *               <code>Rectangle</code> object's coordinate and size values.
     */
    public String toString() {
        return getClass().getName() + "[x=" + x + ",y=" + y + ",width=" + width + ",height=" + height + "]";
    }
}
