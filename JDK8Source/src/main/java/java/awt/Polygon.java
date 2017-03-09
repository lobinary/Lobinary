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

import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import sun.awt.geom.Crossings;
import java.util.Arrays;

/**
 * The <code>Polygon</code> class encapsulates a description of a
 * closed, two-dimensional region within a coordinate space. This
 * region is bounded by an arbitrary number of line segments, each of
 * which is one side of the polygon. Internally, a polygon
 * comprises of a list of {@code (x,y)}
 * coordinate pairs, where each pair defines a <i>vertex</i> of the
 * polygon, and two successive pairs are the endpoints of a
 * line that is a side of the polygon. The first and final
 * pairs of {@code (x,y)} points are joined by a line segment
 * that closes the polygon.  This <code>Polygon</code> is defined with
 * an even-odd winding rule.  See
 * {@link java.awt.geom.PathIterator#WIND_EVEN_ODD WIND_EVEN_ODD}
 * for a definition of the even-odd winding rule.
 * This class's hit-testing methods, which include the
 * <code>contains</code>, <code>intersects</code> and <code>inside</code>
 * methods, use the <i>insideness</i> definition described in the
 * {@link Shape} class comments.
 *
 * <p>
 *  <code> Polygon </code>类封装了在坐标空间内的封闭二维区域的描述。该区域由任意数量的线段限定,每个线段是多边形的一侧。
 * 在内部,多边形包括{@code(x,y)}坐标对的列表,其中每对定义多边形的<i>顶点<i> <i>,并且两个连续的对是线的端点,多边形的一边。
 * 第一个和最后一对{@code(x,y)}点由闭合多边形的线段连接。该<code>多边形</code>用偶数奇数绕组规则定义。
 * 有关偶数奇数绕组规则的定义,请参见{@link java.awt.geom.PathIterator#WIND_EVEN_ODD WIND_EVEN_ODD}。
 * 此类的命中测试方法包括<code>包含</code>,<code>与</code>内部的</code>和<code>相交,使用<i> insideness </i>在{@link Shape}类注释中。
 * 有关偶数奇数绕组规则的定义,请参见{@link java.awt.geom.PathIterator#WIND_EVEN_ODD WIND_EVEN_ODD}。
 * 
 * 
 * @author      Sami Shaio
 * @see Shape
 * @author      Herb Jellinek
 * @since       1.0
 */
public class Polygon implements Shape, java.io.Serializable {

    /**
     * The total number of points.  The value of <code>npoints</code>
     * represents the number of valid points in this <code>Polygon</code>
     * and might be less than the number of elements in
     * {@link #xpoints xpoints} or {@link #ypoints ypoints}.
     * This value can be NULL.
     *
     * <p>
     *  总点数。
     *  <code> npoints </code>的值表示此<code>多边形</code>中的有效点数,可能小于{@link #xpoints xpoints}或{@link #ypoints ypoints}
     * 。
     *  总点数。此值可以为NULL。
     * 
     * 
     * @serial
     * @see #addPoint(int, int)
     * @since 1.0
     */
    public int npoints;

    /**
     * The array of X coordinates.  The number of elements in
     * this array might be more than the number of X coordinates
     * in this <code>Polygon</code>.  The extra elements allow new points
     * to be added to this <code>Polygon</code> without re-creating this
     * array.  The value of {@link #npoints npoints} is equal to the
     * number of valid points in this <code>Polygon</code>.
     *
     * <p>
     * X坐标数组。此数组中的元素数量可能多于此<code>多边形</code>中的X坐标数量。额外的元素允许新的点添加到这个<code>多边形</code>,而不需要重新创建这个数组。
     *  {@link #npoints npoints}的值等于此<code>多边形</code>中的有效点数。
     * 
     * 
     * @serial
     * @see #addPoint(int, int)
     * @since 1.0
     */
    public int xpoints[];

    /**
     * The array of Y coordinates.  The number of elements in
     * this array might be more than the number of Y coordinates
     * in this <code>Polygon</code>.  The extra elements allow new points
     * to be added to this <code>Polygon</code> without re-creating this
     * array.  The value of <code>npoints</code> is equal to the
     * number of valid points in this <code>Polygon</code>.
     *
     * <p>
     *  Y坐标数组。此数组中的元素数量可能多于此<code>多边形</code>中的Y坐标数量。额外的元素允许新的点添加到这个<code>多边形</code>,而不需要重新创建这个数组。
     *  <code> npoints </code>的值等于此<code>多边形</code>中的有效点数。
     * 
     * 
     * @serial
     * @see #addPoint(int, int)
     * @since 1.0
     */
    public int ypoints[];

    /**
     * The bounds of this {@code Polygon}.
     * This value can be null.
     *
     * <p>
     *  此{@code多边形}的边界。此值可以为null。
     * 
     * 
     * @serial
     * @see #getBoundingBox()
     * @see #getBounds()
     * @since 1.0
     */
    protected Rectangle bounds;

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
    private static final long serialVersionUID = -6460061437900069969L;

    /*
     * Default length for xpoints and ypoints.
     * <p>
     *  xpoint和ypoints的默认长​​度。
     * 
     */
    private static final int MIN_LENGTH = 4;

    /**
     * Creates an empty polygon.
     * <p>
     *  创建空多边形。
     * 
     * 
     * @since 1.0
     */
    public Polygon() {
        xpoints = new int[MIN_LENGTH];
        ypoints = new int[MIN_LENGTH];
    }

    /**
     * Constructs and initializes a <code>Polygon</code> from the specified
     * parameters.
     * <p>
     *  从指定的参数构造并初始化<code>多边形</code>。
     * 
     * 
     * @param xpoints an array of X coordinates
     * @param ypoints an array of Y coordinates
     * @param npoints the total number of points in the
     *                          <code>Polygon</code>
     * @exception  NegativeArraySizeException if the value of
     *                       <code>npoints</code> is negative.
     * @exception  IndexOutOfBoundsException if <code>npoints</code> is
     *             greater than the length of <code>xpoints</code>
     *             or the length of <code>ypoints</code>.
     * @exception  NullPointerException if <code>xpoints</code> or
     *             <code>ypoints</code> is <code>null</code>.
     * @since 1.0
     */
    public Polygon(int xpoints[], int ypoints[], int npoints) {
        // Fix 4489009: should throw IndexOutofBoundsException instead
        // of OutofMemoryException if npoints is huge and > {x,y}points.length
        if (npoints > xpoints.length || npoints > ypoints.length) {
            throw new IndexOutOfBoundsException("npoints > xpoints.length || "+
                                                "npoints > ypoints.length");
        }
        // Fix 6191114: should throw NegativeArraySizeException with
        // negative npoints
        if (npoints < 0) {
            throw new NegativeArraySizeException("npoints < 0");
        }
        // Fix 6343431: Applet compatibility problems if arrays are not
        // exactly npoints in length
        this.npoints = npoints;
        this.xpoints = Arrays.copyOf(xpoints, npoints);
        this.ypoints = Arrays.copyOf(ypoints, npoints);
    }

    /**
     * Resets this <code>Polygon</code> object to an empty polygon.
     * The coordinate arrays and the data in them are left untouched
     * but the number of points is reset to zero to mark the old
     * vertex data as invalid and to start accumulating new vertex
     * data at the beginning.
     * All internally-cached data relating to the old vertices
     * are discarded.
     * Note that since the coordinate arrays from before the reset
     * are reused, creating a new empty <code>Polygon</code> might
     * be more memory efficient than resetting the current one if
     * the number of vertices in the new polygon data is significantly
     * smaller than the number of vertices in the data from before the
     * reset.
     * <p>
     * 将此<code>多边形</code>对象重置为空多边形。坐标数组和其中的数据保持不变,但是点的数量被重置为零,以将旧的顶点数据标记为无效,并开始在开始累积新的顶点数据。
     * 与旧顶点相关的所有内部缓存的数据被丢弃。
     * 注意,由于重置前的坐标数组被重新使用,所以如果新的多边形数据中的顶点的数目显着小于重新设置的多边形数据的顶点的数目,则创建新的空<代码>多边形</code>可能比重置当前的<复位前数据中的顶点数。
     * 
     * 
     * @see         java.awt.Polygon#invalidate
     * @since 1.4
     */
    public void reset() {
        npoints = 0;
        bounds = null;
    }

    /**
     * Invalidates or flushes any internally-cached data that depends
     * on the vertex coordinates of this <code>Polygon</code>.
     * This method should be called after any direct manipulation
     * of the coordinates in the <code>xpoints</code> or
     * <code>ypoints</code> arrays to avoid inconsistent results
     * from methods such as <code>getBounds</code> or <code>contains</code>
     * that might cache data from earlier computations relating to
     * the vertex coordinates.
     * <p>
     *  无效或刷新依赖于此<code>多边形</code>的顶点坐标的任何内部缓存数据。
     * 在任何直接操作<code> xpoints </code>或<code> ypoints </code>数组中的坐标之后,应该调用此方法,以避免诸如<code> getBounds </code>代码>
     * 包含</code>可能缓存来自与顶点坐标有关的早期计算的数据。
     *  无效或刷新依赖于此<code>多边形</code>的顶点坐标的任何内部缓存数据。
     * 
     * 
     * @see         java.awt.Polygon#getBounds
     * @since 1.4
     */
    public void invalidate() {
        bounds = null;
    }

    /**
     * Translates the vertices of the <code>Polygon</code> by
     * <code>deltaX</code> along the x axis and by
     * <code>deltaY</code> along the y axis.
     * <p>
     *  沿x轴转换<code>多边形</code>的顶点,通过<code> deltaX </code>沿y轴转换<code> deltaY </code>
     * 
     * 
     * @param deltaX the amount to translate along the X axis
     * @param deltaY the amount to translate along the Y axis
     * @since 1.1
     */
    public void translate(int deltaX, int deltaY) {
        for (int i = 0; i < npoints; i++) {
            xpoints[i] += deltaX;
            ypoints[i] += deltaY;
        }
        if (bounds != null) {
            bounds.translate(deltaX, deltaY);
        }
    }

    /*
     * Calculates the bounding box of the points passed to the constructor.
     * Sets <code>bounds</code> to the result.
     * <p>
     *  计算传递给构造函数的点的边界框。将<code> bounds </code>设置为结果。
     * 
     * 
     * @param xpoints[] array of <i>x</i> coordinates
     * @param ypoints[] array of <i>y</i> coordinates
     * @param npoints the total number of points
     */
    void calculateBounds(int xpoints[], int ypoints[], int npoints) {
        int boundsMinX = Integer.MAX_VALUE;
        int boundsMinY = Integer.MAX_VALUE;
        int boundsMaxX = Integer.MIN_VALUE;
        int boundsMaxY = Integer.MIN_VALUE;

        for (int i = 0; i < npoints; i++) {
            int x = xpoints[i];
            boundsMinX = Math.min(boundsMinX, x);
            boundsMaxX = Math.max(boundsMaxX, x);
            int y = ypoints[i];
            boundsMinY = Math.min(boundsMinY, y);
            boundsMaxY = Math.max(boundsMaxY, y);
        }
        bounds = new Rectangle(boundsMinX, boundsMinY,
                               boundsMaxX - boundsMinX,
                               boundsMaxY - boundsMinY);
    }

    /*
     * Resizes the bounding box to accommodate the specified coordinates.
     * <p>
     *  调整边界框的大小以适应指定的坐标。
     * 
     * 
     * @param x,&nbsp;y the specified coordinates
     */
    void updateBounds(int x, int y) {
        if (x < bounds.x) {
            bounds.width = bounds.width + (bounds.x - x);
            bounds.x = x;
        }
        else {
            bounds.width = Math.max(bounds.width, x - bounds.x);
            // bounds.x = bounds.x;
        }

        if (y < bounds.y) {
            bounds.height = bounds.height + (bounds.y - y);
            bounds.y = y;
        }
        else {
            bounds.height = Math.max(bounds.height, y - bounds.y);
            // bounds.y = bounds.y;
        }
    }

    /**
     * Appends the specified coordinates to this <code>Polygon</code>.
     * <p>
     * If an operation that calculates the bounding box of this
     * <code>Polygon</code> has already been performed, such as
     * <code>getBounds</code> or <code>contains</code>, then this
     * method updates the bounding box.
     * <p>
     *  将指定的坐标追加到此<code>多边形</code>。
     * <p>
     * 如果已经执行了计算该<code>多边形</code>的边界框的操作,例如<code> getBounds </code>或<code>包含</code>,则该方法更新边界框。
     * 
     * 
     * @param       x the specified X coordinate
     * @param       y the specified Y coordinate
     * @see         java.awt.Polygon#getBounds
     * @see         java.awt.Polygon#contains
     * @since 1.0
     */
    public void addPoint(int x, int y) {
        if (npoints >= xpoints.length || npoints >= ypoints.length) {
            int newLength = npoints * 2;
            // Make sure that newLength will be greater than MIN_LENGTH and
            // aligned to the power of 2
            if (newLength < MIN_LENGTH) {
                newLength = MIN_LENGTH;
            } else if ((newLength & (newLength - 1)) != 0) {
                newLength = Integer.highestOneBit(newLength);
            }

            xpoints = Arrays.copyOf(xpoints, newLength);
            ypoints = Arrays.copyOf(ypoints, newLength);
        }
        xpoints[npoints] = x;
        ypoints[npoints] = y;
        npoints++;
        if (bounds != null) {
            updateBounds(x, y);
        }
    }

    /**
     * Gets the bounding box of this <code>Polygon</code>.
     * The bounding box is the smallest {@link Rectangle} whose
     * sides are parallel to the x and y axes of the
     * coordinate space, and can completely contain the <code>Polygon</code>.
     * <p>
     *  获取此<code>多边形</code>的边框。边界框是最小的{@link Rectangle},其边与坐标空间的x和y轴平行,并且可以完全包含<code>多边形</code>。
     * 
     * 
     * @return a <code>Rectangle</code> that defines the bounds of this
     * <code>Polygon</code>.
     * @since 1.1
     */
    public Rectangle getBounds() {
        return getBoundingBox();
    }

    /**
     * Returns the bounds of this <code>Polygon</code>.
     * <p>
     *  返回此<code>多边形</code>的边界。
     * 
     * 
     * @return the bounds of this <code>Polygon</code>.
     * @deprecated As of JDK version 1.1,
     * replaced by <code>getBounds()</code>.
     * @since 1.0
     */
    @Deprecated
    public Rectangle getBoundingBox() {
        if (npoints == 0) {
            return new Rectangle();
        }
        if (bounds == null) {
            calculateBounds(xpoints, ypoints, npoints);
        }
        return bounds.getBounds();
    }

    /**
     * Determines whether the specified {@link Point} is inside this
     * <code>Polygon</code>.
     * <p>
     *  确定指定的{@link Point}是否在此<代码>多边形</code>中。
     * 
     * 
     * @param p the specified <code>Point</code> to be tested
     * @return <code>true</code> if the <code>Polygon</code> contains the
     *                  <code>Point</code>; <code>false</code> otherwise.
     * @see #contains(double, double)
     * @since 1.0
     */
    public boolean contains(Point p) {
        return contains(p.x, p.y);
    }

    /**
     * Determines whether the specified coordinates are inside this
     * <code>Polygon</code>.
     * <p>
     * <p>
     *  确定指定的坐标是否在此<代码>多边形</code>中。
     * <p>
     * 
     * @param x the specified X coordinate to be tested
     * @param y the specified Y coordinate to be tested
     * @return {@code true} if this {@code Polygon} contains
     *         the specified coordinates {@code (x,y)};
     *         {@code false} otherwise.
     * @see #contains(double, double)
     * @since 1.1
     */
    public boolean contains(int x, int y) {
        return contains((double) x, (double) y);
    }

    /**
     * Determines whether the specified coordinates are contained in this
     * <code>Polygon</code>.
     * <p>
     *  确定指定的坐标是否包含在此<code>多边形</code>中。
     * 
     * 
     * @param x the specified X coordinate to be tested
     * @param y the specified Y coordinate to be tested
     * @return {@code true} if this {@code Polygon} contains
     *         the specified coordinates {@code (x,y)};
     *         {@code false} otherwise.
     * @see #contains(double, double)
     * @deprecated As of JDK version 1.1,
     * replaced by <code>contains(int, int)</code>.
     * @since 1.0
     */
    @Deprecated
    public boolean inside(int x, int y) {
        return contains((double) x, (double) y);
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
        return getBounds();
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.2
     */
    public boolean contains(double x, double y) {
        if (npoints <= 2 || !getBoundingBox().contains(x, y)) {
            return false;
        }
        int hits = 0;

        int lastx = xpoints[npoints - 1];
        int lasty = ypoints[npoints - 1];
        int curx, cury;

        // Walk the edges of the polygon
        for (int i = 0; i < npoints; lastx = curx, lasty = cury, i++) {
            curx = xpoints[i];
            cury = ypoints[i];

            if (cury == lasty) {
                continue;
            }

            int leftx;
            if (curx < lastx) {
                if (x >= lastx) {
                    continue;
                }
                leftx = curx;
            } else {
                if (x >= curx) {
                    continue;
                }
                leftx = lastx;
            }

            double test1, test2;
            if (cury < lasty) {
                if (y < cury || y >= lasty) {
                    continue;
                }
                if (x < leftx) {
                    hits++;
                    continue;
                }
                test1 = x - curx;
                test2 = y - cury;
            } else {
                if (y < lasty || y >= cury) {
                    continue;
                }
                if (x < leftx) {
                    hits++;
                    continue;
                }
                test1 = x - lastx;
                test2 = y - lasty;
            }

            if (test1 < (test2 / (lasty - cury) * (lastx - curx))) {
                hits++;
            }
        }

        return ((hits & 1) != 0);
    }

    private Crossings getCrossings(double xlo, double ylo,
                                   double xhi, double yhi)
    {
        Crossings cross = new Crossings.EvenOdd(xlo, ylo, xhi, yhi);
        int lastx = xpoints[npoints - 1];
        int lasty = ypoints[npoints - 1];
        int curx, cury;

        // Walk the edges of the polygon
        for (int i = 0; i < npoints; i++) {
            curx = xpoints[i];
            cury = ypoints[i];
            if (cross.accumulateLine(lastx, lasty, curx, cury)) {
                return null;
            }
            lastx = curx;
            lasty = cury;
        }

        return cross;
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.2
     */
    public boolean contains(Point2D p) {
        return contains(p.getX(), p.getY());
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.2
     */
    public boolean intersects(double x, double y, double w, double h) {
        if (npoints <= 0 || !getBoundingBox().intersects(x, y, w, h)) {
            return false;
        }

        Crossings cross = getCrossings(x, y, x+w, y+h);
        return (cross == null || !cross.isEmpty());
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.2
     */
    public boolean intersects(Rectangle2D r) {
        return intersects(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.2
     */
    public boolean contains(double x, double y, double w, double h) {
        if (npoints <= 0 || !getBoundingBox().intersects(x, y, w, h)) {
            return false;
        }

        Crossings cross = getCrossings(x, y, x+w, y+h);
        return (cross != null && cross.covers(y, y+h));
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.2
     */
    public boolean contains(Rectangle2D r) {
        return contains(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    /**
     * Returns an iterator object that iterates along the boundary of this
     * <code>Polygon</code> and provides access to the geometry
     * of the outline of this <code>Polygon</code>.  An optional
     * {@link AffineTransform} can be specified so that the coordinates
     * returned in the iteration are transformed accordingly.
     * <p>
     *  返回一个迭代器对象,该对象沿着<code> Polygon </code>的边界进行迭代,并提供对此<code>多边形</code>的轮廓几何体的访问。
     * 可以指定一个可选的{@link AffineTransform},以便在迭代中返回的坐标被相应地转换。
     * 
     * 
     * @param at an optional <code>AffineTransform</code> to be applied to the
     *          coordinates as they are returned in the iteration, or
     *          <code>null</code> if untransformed coordinates are desired
     * @return a {@link PathIterator} object that provides access to the
     *          geometry of this <code>Polygon</code>.
     * @since 1.2
     */
    public PathIterator getPathIterator(AffineTransform at) {
        return new PolygonPathIterator(this, at);
    }

    /**
     * Returns an iterator object that iterates along the boundary of
     * the <code>Shape</code> and provides access to the geometry of the
     * outline of the <code>Shape</code>.  Only SEG_MOVETO, SEG_LINETO, and
     * SEG_CLOSE point types are returned by the iterator.
     * Since polygons are already flat, the <code>flatness</code> parameter
     * is ignored.  An optional <code>AffineTransform</code> can be specified
     * in which case the coordinates returned in the iteration are transformed
     * accordingly.
     * <p>
     * 返回一个迭代器对象,它沿着<code> Shape </code>的边界进行迭代,并提供对<code> Shape </code>的轮廓几何体的访问。
     * 迭代器只返回SEG_MOVETO,SEG_LINETO和SEG_CLOSE点类型。由于多边形已经是平的,因此将忽略<code> flatness </code>参数。
     * 可以指定一个可选的<code> AffineTransform </code>,在这种情况下,迭代中返回的坐标将相应地进行转换。
     * 
     * 
     * @param at an optional <code>AffineTransform</code> to be applied to the
     *          coordinates as they are returned in the iteration, or
     *          <code>null</code> if untransformed coordinates are desired
     * @param flatness the maximum amount that the control points
     *          for a given curve can vary from colinear before a subdivided
     *          curve is replaced by a straight line connecting the
     *          endpoints.  Since polygons are already flat the
     *          <code>flatness</code> parameter is ignored.
     * @return a <code>PathIterator</code> object that provides access to the
     *          <code>Shape</code> object's geometry.
     * @since 1.2
     */
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return getPathIterator(at);
    }

    class PolygonPathIterator implements PathIterator {
        Polygon poly;
        AffineTransform transform;
        int index;

        public PolygonPathIterator(Polygon pg, AffineTransform at) {
            poly = pg;
            transform = at;
            if (pg.npoints == 0) {
                // Prevent a spurious SEG_CLOSE segment
                index = 1;
            }
        }

        /**
         * Returns the winding rule for determining the interior of the
         * path.
         * <p>
         *  返回确定路径内部的绕组规则。
         * 
         * 
         * @return an integer representing the current winding rule.
         * @see PathIterator#WIND_NON_ZERO
         */
        public int getWindingRule() {
            return WIND_EVEN_ODD;
        }

        /**
         * Tests if there are more points to read.
         * <p>
         *  测试是否有更多的要读取的点。
         * 
         * 
         * @return <code>true</code> if there are more points to read;
         *          <code>false</code> otherwise.
         */
        public boolean isDone() {
            return index > poly.npoints;
        }

        /**
         * Moves the iterator forwards, along the primary direction of
         * traversal, to the next segment of the path when there are
         * more points in that direction.
         * <p>
         *  当该方向上有更多的点时,将迭代器沿着遍历的主要方向向前移动到路径的下一段。
         * 
         */
        public void next() {
            index++;
        }

        /**
         * Returns the coordinates and type of the current path segment in
         * the iteration.
         * The return value is the path segment type:
         * SEG_MOVETO, SEG_LINETO, or SEG_CLOSE.
         * A <code>float</code> array of length 2 must be passed in and
         * can be used to store the coordinates of the point(s).
         * Each point is stored as a pair of <code>float</code> x,&nbsp;y
         * coordinates.  SEG_MOVETO and SEG_LINETO types return one
         * point, and SEG_CLOSE does not return any points.
         * <p>
         *  返回迭代中当前路径段的坐标和类型。返回值是路径段类型：SEG_MOVETO,SEG_LINETO或SEG_CLOSE。
         * 必须传递长度为2的<code> float </code>数组,并且可以用于存储点的坐标。每个点都存储为一对<code> float </code> x,y坐标。
         *  SEG_MOVETO和SEG_LINETO类型返回一个点,SEG_CLOSE不返回任何点。
         * 
         * 
         * @param coords a <code>float</code> array that specifies the
         * coordinates of the point(s)
         * @return an integer representing the type and coordinates of the
         *              current path segment.
         * @see PathIterator#SEG_MOVETO
         * @see PathIterator#SEG_LINETO
         * @see PathIterator#SEG_CLOSE
         */
        public int currentSegment(float[] coords) {
            if (index >= poly.npoints) {
                return SEG_CLOSE;
            }
            coords[0] = poly.xpoints[index];
            coords[1] = poly.ypoints[index];
            if (transform != null) {
                transform.transform(coords, 0, coords, 0, 1);
            }
            return (index == 0 ? SEG_MOVETO : SEG_LINETO);
        }

        /**
         * Returns the coordinates and type of the current path segment in
         * the iteration.
         * The return value is the path segment type:
         * SEG_MOVETO, SEG_LINETO, or SEG_CLOSE.
         * A <code>double</code> array of length 2 must be passed in and
         * can be used to store the coordinates of the point(s).
         * Each point is stored as a pair of <code>double</code> x,&nbsp;y
         * coordinates.
         * SEG_MOVETO and SEG_LINETO types return one point,
         * and SEG_CLOSE does not return any points.
         * <p>
         * 返回迭代中当前路径段的坐标和类型。返回值是路径段类型：SEG_MOVETO,SEG_LINETO或SEG_CLOSE。
         * 必须传递长度为2的<code> double </code>数组,并且可以用于存储点的坐标。每个点都存储为一对<code> double </code> x,y坐标。
         * 
         * @param coords a <code>double</code> array that specifies the
         * coordinates of the point(s)
         * @return an integer representing the type and coordinates of the
         *              current path segment.
         * @see PathIterator#SEG_MOVETO
         * @see PathIterator#SEG_LINETO
         * @see PathIterator#SEG_CLOSE
         */
        public int currentSegment(double[] coords) {
            if (index >= poly.npoints) {
                return SEG_CLOSE;
            }
            coords[0] = poly.xpoints[index];
            coords[1] = poly.ypoints[index];
            if (transform != null) {
                transform.transform(coords, 0, coords, 0, 1);
            }
            return (index == 0 ? SEG_MOVETO : SEG_LINETO);
        }
    }
}
