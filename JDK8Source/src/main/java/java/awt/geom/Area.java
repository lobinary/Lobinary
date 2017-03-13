/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2006, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.geom;

import java.awt.Shape;
import java.awt.Rectangle;
import java.util.Vector;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import sun.awt.geom.Curve;
import sun.awt.geom.Crossings;
import sun.awt.geom.AreaOp;

/**
 * An <code>Area</code> object stores and manipulates a
 * resolution-independent description of an enclosed area of
 * 2-dimensional space.
 * <code>Area</code> objects can be transformed and can perform
 * various Constructive Area Geometry (CAG) operations when combined
 * with other <code>Area</code> objects.
 * The CAG operations include area
 * {@link #add addition}, {@link #subtract subtraction},
 * {@link #intersect intersection}, and {@link #exclusiveOr exclusive or}.
 * See the linked method documentation for examples of the various
 * operations.
 * <p>
 * The <code>Area</code> class implements the <code>Shape</code>
 * interface and provides full support for all of its hit-testing
 * and path iteration facilities, but an <code>Area</code> is more
 * specific than a generalized path in a number of ways:
 * <ul>
 * <li>Only closed paths and sub-paths are stored.
 *     <code>Area</code> objects constructed from unclosed paths
 *     are implicitly closed during construction as if those paths
 *     had been filled by the <code>Graphics2D.fill</code> method.
 * <li>The interiors of the individual stored sub-paths are all
 *     non-empty and non-overlapping.  Paths are decomposed during
 *     construction into separate component non-overlapping parts,
 *     empty pieces of the path are discarded, and then these
 *     non-empty and non-overlapping properties are maintained
 *     through all subsequent CAG operations.  Outlines of different
 *     component sub-paths may touch each other, as long as they
 *     do not cross so that their enclosed areas overlap.
 * <li>The geometry of the path describing the outline of the
 *     <code>Area</code> resembles the path from which it was
 *     constructed only in that it describes the same enclosed
 *     2-dimensional area, but may use entirely different types
 *     and ordering of the path segments to do so.
 * </ul>
 * Interesting issues which are not always obvious when using
 * the <code>Area</code> include:
 * <ul>
 * <li>Creating an <code>Area</code> from an unclosed (open)
 *     <code>Shape</code> results in a closed outline in the
 *     <code>Area</code> object.
 * <li>Creating an <code>Area</code> from a <code>Shape</code>
 *     which encloses no area (even when "closed") produces an
 *     empty <code>Area</code>.  A common example of this issue
 *     is that producing an <code>Area</code> from a line will
 *     be empty since the line encloses no area.  An empty
 *     <code>Area</code> will iterate no geometry in its
 *     <code>PathIterator</code> objects.
 * <li>A self-intersecting <code>Shape</code> may be split into
 *     two (or more) sub-paths each enclosing one of the
 *     non-intersecting portions of the original path.
 * <li>An <code>Area</code> may take more path segments to
 *     describe the same geometry even when the original
 *     outline is simple and obvious.  The analysis that the
 *     <code>Area</code> class must perform on the path may
 *     not reflect the same concepts of "simple and obvious"
 *     as a human being perceives.
 * </ul>
 *
 * <p>
 *  <code> Area </code>对象存储和操作对二维空间的封闭区域的独立于分辨率的描述<code> Area </code>对象可以被转换并且可以执行各种建设性区域几何当与其他<code> Ar
 * ea </code>对象组合时CAG操作包括区域{@link #add addition},{@link #subtract subtraction},{@link #intersect intersection}
 * 和{@link #exclusiveOr exclusive }有关各种操作的示例,请参阅链接的方法文档。
 * <p>
 * <code> Area </code>类实现了<code> Shape </code>接口,并为其所有的命中测试和路径迭代设施提供全面支持,但<code> Area </code>比一般的路径在多种方式
 * ：。
 * <ul>
 * <li>仅存储封闭路径和子路径</span> </>>由未封闭路径构造的<code> Area </code>对象在构建期间隐式关闭,如同这些路径已由<code> Graphics2Dfill </code>
 * 方法填充<li >各个存储的子路径的内部都是非空的,并且在构造期间不重叠的路径被分解成单独的组件非重叠部分,该路径的空段被丢弃,然后这些非空和非重叠通过所有后续CAG操作维持属性。
 * 不同组件子路径的轮廓可以彼此接触,只要它们不交叉使得它们的封闭区域重叠<li>描述<code> Area </code>大纲的路径的几何形状类似于构建它的路径,因为它描述了相同的封闭二维区域,但可以使用
 * 完全不同的类型和顺序的路径段。
 * </ul>
 * 使用<code> Area </code>时并不总是显而易见的有趣问题包括：
 * <ul>
 * <li>从未关闭(打开)<code>形状</code>创建<code>区域</code>会在<code>区域</code>对象中生成封闭的大纲<li>创建<代码>区域</code>从不包含区域的<code>
 *  Shape </code>(即使"closed")产生空的<code> Area </code>。
 * 此问题的常见示例是生成<code > Area </code>将不会在其<code> PathIterator </code>对象中迭代几何<li>自相交<code> Shape </code>可以分成
 * 两个(或多个)子路径,每个子路径包含原始路径<li>的一个非相交部分。
 * <code> Area </code>以描述相同的几何,即使原始轮廓是简单和明显的<code> Area </code>类必须在路径上执行的分析可能不会反映与人类感知的"简单和明显"相同的概念。
 * </ul>
 * 
 * 
 * @since 1.2
 */
public class Area implements Shape, Cloneable {
    private static Vector EmptyCurves = new Vector();

    private Vector curves;

    /**
     * Default constructor which creates an empty area.
     * <p>
     * 默认构造函数创建一个空白区域
     * 
     * 
     * @since 1.2
     */
    public Area() {
        curves = EmptyCurves;
    }

    /**
     * The <code>Area</code> class creates an area geometry from the
     * specified {@link Shape} object.  The geometry is explicitly
     * closed, if the <code>Shape</code> is not already closed.  The
     * fill rule (even-odd or winding) specified by the geometry of the
     * <code>Shape</code> is used to determine the resulting enclosed area.
     * <p>
     *  <code> Area </code>类从指定的{@link Shape}对象创建一个区域几何。
     * 如果<code> Shape </code>还没有关闭,几何将被明确关闭填充规则或绕组)由<code> Shape </code>的几何形状指定,用于确定所得到的封闭区域。
     * 
     * 
     * @param s  the <code>Shape</code> from which the area is constructed
     * @throws NullPointerException if <code>s</code> is null
     * @since 1.2
     */
    public Area(Shape s) {
        if (s instanceof Area) {
            curves = ((Area) s).curves;
        } else {
            curves = pathToCurves(s.getPathIterator(null));
        }
    }

    private static Vector pathToCurves(PathIterator pi) {
        Vector curves = new Vector();
        int windingRule = pi.getWindingRule();
        // coords array is big enough for holding:
        //     coordinates returned from currentSegment (6)
        //     OR
        //         two subdivided quadratic curves (2+4+4=10)
        //         AND
        //             0-1 horizontal splitting parameters
        //             OR
        //             2 parametric equation derivative coefficients
        //     OR
        //         three subdivided cubic curves (2+6+6+6=20)
        //         AND
        //             0-2 horizontal splitting parameters
        //             OR
        //             3 parametric equation derivative coefficients
        double coords[] = new double[23];
        double movx = 0, movy = 0;
        double curx = 0, cury = 0;
        double newx, newy;
        while (!pi.isDone()) {
            switch (pi.currentSegment(coords)) {
            case PathIterator.SEG_MOVETO:
                Curve.insertLine(curves, curx, cury, movx, movy);
                curx = movx = coords[0];
                cury = movy = coords[1];
                Curve.insertMove(curves, movx, movy);
                break;
            case PathIterator.SEG_LINETO:
                newx = coords[0];
                newy = coords[1];
                Curve.insertLine(curves, curx, cury, newx, newy);
                curx = newx;
                cury = newy;
                break;
            case PathIterator.SEG_QUADTO:
                newx = coords[2];
                newy = coords[3];
                Curve.insertQuad(curves, curx, cury, coords);
                curx = newx;
                cury = newy;
                break;
            case PathIterator.SEG_CUBICTO:
                newx = coords[4];
                newy = coords[5];
                Curve.insertCubic(curves, curx, cury, coords);
                curx = newx;
                cury = newy;
                break;
            case PathIterator.SEG_CLOSE:
                Curve.insertLine(curves, curx, cury, movx, movy);
                curx = movx;
                cury = movy;
                break;
            }
            pi.next();
        }
        Curve.insertLine(curves, curx, cury, movx, movy);
        AreaOp operator;
        if (windingRule == PathIterator.WIND_EVEN_ODD) {
            operator = new AreaOp.EOWindOp();
        } else {
            operator = new AreaOp.NZWindOp();
        }
        return operator.calculate(curves, EmptyCurves);
    }

    /**
     * Adds the shape of the specified <code>Area</code> to the
     * shape of this <code>Area</code>.
     * The resulting shape of this <code>Area</code> will include
     * the union of both shapes, or all areas that were contained
     * in either this or the specified <code>Area</code>.
     * <pre>
     *     // Example:
     *     Area a1 = new Area([triangle 0,0 =&gt; 8,0 =&gt; 0,8]);
     *     Area a2 = new Area([triangle 0,0 =&gt; 8,0 =&gt; 8,8]);
     *     a1.add(a2);
     *
     *        a1(before)     +         a2         =     a1(after)
     *
     *     ################     ################     ################
     *     ##############         ##############     ################
     *     ############             ############     ################
     *     ##########                 ##########     ################
     *     ########                     ########     ################
     *     ######                         ######     ######    ######
     *     ####                             ####     ####        ####
     *     ##                                 ##     ##            ##
     * </pre>
     * <p>
     *  将<code> Area </code>的形状添加到此<code> Area </code>的形状。
     * <code> Area </code>的结果形状将包括两个形状的并集,包含在此或指定的<code> Area </code>中的区域。
     * <pre>
     *  //示例：Area a1 = new Area([triangle 0,0 =&gt; 8,0 =&gt; 0,8]);面积a2 =新面积([三角形0,0 => 8,0 => 8,8]); a1add
     * (a2);。
     * 
     * a1(before)+ a2 = a1(after)
     * 
     *  ################ ################ ################ ## ############ ############## ################ #
     * ####### #### ############ ################ ########## ######## ################# ######## ######## ##
     * ############## ###### ###### ###### ###### #### #### #### #### ## ## ## ##。
     * </pre>
     * 
     * @param   rhs  the <code>Area</code> to be added to the
     *          current shape
     * @throws NullPointerException if <code>rhs</code> is null
     * @since 1.2
     */
    public void add(Area rhs) {
        curves = new AreaOp.AddOp().calculate(this.curves, rhs.curves);
        invalidateBounds();
    }

    /**
     * Subtracts the shape of the specified <code>Area</code> from the
     * shape of this <code>Area</code>.
     * The resulting shape of this <code>Area</code> will include
     * areas that were contained only in this <code>Area</code>
     * and not in the specified <code>Area</code>.
     * <pre>
     *     // Example:
     *     Area a1 = new Area([triangle 0,0 =&gt; 8,0 =&gt; 0,8]);
     *     Area a2 = new Area([triangle 0,0 =&gt; 8,0 =&gt; 8,8]);
     *     a1.subtract(a2);
     *
     *        a1(before)     -         a2         =     a1(after)
     *
     *     ################     ################
     *     ##############         ##############     ##
     *     ############             ############     ####
     *     ##########                 ##########     ######
     *     ########                     ########     ########
     *     ######                         ######     ######
     *     ####                             ####     ####
     *     ##                                 ##     ##
     * </pre>
     * <p>
     *  从<code> Area </code>的形状中减去指定<code> Area </code>的形状。
     * <code> Area </code>的结果形状将包括仅包含在<代码>区域</code>,而不是在指定的<code> Area </code>中。
     * <pre>
     * //示例：Area a1 = new Area([triangle 0,0 =&gt; 8,0 =&gt; 0,8]);面积a2 =新面积([三角形0,0 => 8,0 => 8,8]); a1subt
     * ract(a2);。
     * 
     *  a1(before)-a2 = a1(after)
     * 
     *  ################ ################ ############## #### ########## ## ############ ############ #### #
     * ######### ########## ###### ######## ######## ######## ###### #### ####### #### #### #### ### ##。
     * </pre>
     * 
     * @param   rhs  the <code>Area</code> to be subtracted from the
     *          current shape
     * @throws NullPointerException if <code>rhs</code> is null
     * @since 1.2
     */
    public void subtract(Area rhs) {
        curves = new AreaOp.SubOp().calculate(this.curves, rhs.curves);
        invalidateBounds();
    }

    /**
     * Sets the shape of this <code>Area</code> to the intersection of
     * its current shape and the shape of the specified <code>Area</code>.
     * The resulting shape of this <code>Area</code> will include
     * only areas that were contained in both this <code>Area</code>
     * and also in the specified <code>Area</code>.
     * <pre>
     *     // Example:
     *     Area a1 = new Area([triangle 0,0 =&gt; 8,0 =&gt; 0,8]);
     *     Area a2 = new Area([triangle 0,0 =&gt; 8,0 =&gt; 8,8]);
     *     a1.intersect(a2);
     *
     *      a1(before)   intersect     a2         =     a1(after)
     *
     *     ################     ################     ################
     *     ##############         ##############       ############
     *     ############             ############         ########
     *     ##########                 ##########           ####
     *     ########                     ########
     *     ######                         ######
     *     ####                             ####
     *     ##                                 ##
     * </pre>
     * <p>
     * 将<code> Area </code>的形状设置为其当前形状与指定<code> Area </code>的形状的交集。
     * <code> Area </code>的结果形状将仅包括这些<code> Area </code>和指定的<code> Area </code>中包含的区域。
     * <pre>
     *  //示例：Area a1 = new Area([triangle 0,0 =&gt; 8,0 =&gt; 0,8]);面积a2 =新面积([三角形0,0 => 8,0 => 8,8]); a1int
     * ersect(a2);。
     * 
     *  a1(before)相交a2 = a1(after)
     * 
     * ################     ################     ################ ##############         ##############     
     *   ############ ############             ############         ######## ##########                 ####
     * ######           #### ########                     ######## ######                         ###### ###
     * #                             #### ##                                 ##。
     * </pre>
     * 
     * @param   rhs  the <code>Area</code> to be intersected with this
     *          <code>Area</code>
     * @throws NullPointerException if <code>rhs</code> is null
     * @since 1.2
     */
    public void intersect(Area rhs) {
        curves = new AreaOp.IntOp().calculate(this.curves, rhs.curves);
        invalidateBounds();
    }

    /**
     * Sets the shape of this <code>Area</code> to be the combined area
     * of its current shape and the shape of the specified <code>Area</code>,
     * minus their intersection.
     * The resulting shape of this <code>Area</code> will include
     * only areas that were contained in either this <code>Area</code>
     * or in the specified <code>Area</code>, but not in both.
     * <pre>
     *     // Example:
     *     Area a1 = new Area([triangle 0,0 =&gt; 8,0 =&gt; 0,8]);
     *     Area a2 = new Area([triangle 0,0 =&gt; 8,0 =&gt; 8,8]);
     *     a1.exclusiveOr(a2);
     *
     *        a1(before)    xor        a2         =     a1(after)
     *
     *     ################     ################
     *     ##############         ##############     ##            ##
     *     ############             ############     ####        ####
     *     ##########                 ##########     ######    ######
     *     ########                     ########     ################
     *     ######                         ######     ######    ######
     *     ####                             ####     ####        ####
     *     ##                                 ##     ##            ##
     * </pre>
     * <p>
     *  Sets the shape of this <code>Area</code> to be the combined area of its current shape and the shape 
     * of the specified <code>Area</code>, minus their intersection The resulting shape of this <code>Area</code>
     *  will include only areas that were contained in either this <code>Area</code> or in the specified <code>
     * Area</code>, but not in both。
     * <pre>
     * //示例：Area a1 = new Area([triangle 0,0 =&gt; 8,0 =&gt; 0,8]);面积a2 =新面积([三角形0,0 => 8,0 => 8,8]); a1excl
     * usiveOr(a2);。
     * 
     *  a1(before)xor a2 = a1(after)
     * 
     *  ################ ################ ############## #### ########## ### ############ ############ #### 
     * #### #### ###### ########## ###### ###### ######## ######## ###### ########## ###### ###### ###### ##
     * #### #### #### #### #### ## ## ## ##。
     * </pre>
     * 
     * @param   rhs  the <code>Area</code> to be exclusive ORed with this
     *          <code>Area</code>.
     * @throws NullPointerException if <code>rhs</code> is null
     * @since 1.2
     */
    public void exclusiveOr(Area rhs) {
        curves = new AreaOp.XorOp().calculate(this.curves, rhs.curves);
        invalidateBounds();
    }

    /**
     * Removes all of the geometry from this <code>Area</code> and
     * restores it to an empty area.
     * <p>
     *  从<code> Area </code>中删除所有几何图形,并将其恢复到空白区域
     * 
     * 
     * @since 1.2
     */
    public void reset() {
        curves = new Vector();
        invalidateBounds();
    }

    /**
     * Tests whether this <code>Area</code> object encloses any area.
     * <p>
     * 测试这个<code> Area </code>对象是否包含任何区域
     * 
     * 
     * @return    <code>true</code> if this <code>Area</code> object
     * represents an empty area; <code>false</code> otherwise.
     * @since 1.2
     */
    public boolean isEmpty() {
        return (curves.size() == 0);
    }

    /**
     * Tests whether this <code>Area</code> consists entirely of
     * straight edged polygonal geometry.
     * <p>
     *  测试此<code> Area </code>是否完全由直边多边形几何构成
     * 
     * 
     * @return    <code>true</code> if the geometry of this
     * <code>Area</code> consists entirely of line segments;
     * <code>false</code> otherwise.
     * @since 1.2
     */
    public boolean isPolygonal() {
        Enumeration enum_ = curves.elements();
        while (enum_.hasMoreElements()) {
            if (((Curve) enum_.nextElement()).getOrder() > 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests whether this <code>Area</code> is rectangular in shape.
     * <p>
     *  测试此<code> Area </code>是否为矩形
     * 
     * 
     * @return    <code>true</code> if the geometry of this
     * <code>Area</code> is rectangular in shape; <code>false</code>
     * otherwise.
     * @since 1.2
     */
    public boolean isRectangular() {
        int size = curves.size();
        if (size == 0) {
            return true;
        }
        if (size > 3) {
            return false;
        }
        Curve c1 = (Curve) curves.get(1);
        Curve c2 = (Curve) curves.get(2);
        if (c1.getOrder() != 1 || c2.getOrder() != 1) {
            return false;
        }
        if (c1.getXTop() != c1.getXBot() || c2.getXTop() != c2.getXBot()) {
            return false;
        }
        if (c1.getYTop() != c2.getYTop() || c1.getYBot() != c2.getYBot()) {
            // One might be able to prove that this is impossible...
            return false;
        }
        return true;
    }

    /**
     * Tests whether this <code>Area</code> is comprised of a single
     * closed subpath.  This method returns <code>true</code> if the
     * path contains 0 or 1 subpaths, or <code>false</code> if the path
     * contains more than 1 subpath.  The subpaths are counted by the
     * number of {@link PathIterator#SEG_MOVETO SEG_MOVETO}  segments
     * that appear in the path.
     * <p>
     *  测试此<code> Area </code>是否由单个闭合子路径组成如果路径包含0或1个子路径,此方法返回<code> true </code>,如果路径包含0或1个子路径,则返回<code> fal
     * se </code>包含多个子路径子路径由路径中显示的{@link PathIterator#SEG_MOVETO SEG_MOVETO}段的数量计数。
     * 
     * 
     * @return    <code>true</code> if the <code>Area</code> is comprised
     * of a single basic geometry; <code>false</code> otherwise.
     * @since 1.2
     */
    public boolean isSingular() {
        if (curves.size() < 3) {
            return true;
        }
        Enumeration enum_ = curves.elements();
        enum_.nextElement(); // First Order0 "moveto"
        while (enum_.hasMoreElements()) {
            if (((Curve) enum_.nextElement()).getOrder() == 0) {
                return false;
            }
        }
        return true;
    }

    private Rectangle2D cachedBounds;
    private void invalidateBounds() {
        cachedBounds = null;
    }
    private Rectangle2D getCachedBounds() {
        if (cachedBounds != null) {
            return cachedBounds;
        }
        Rectangle2D r = new Rectangle2D.Double();
        if (curves.size() > 0) {
            Curve c = (Curve) curves.get(0);
            // First point is always an order 0 curve (moveto)
            r.setRect(c.getX0(), c.getY0(), 0, 0);
            for (int i = 1; i < curves.size(); i++) {
                ((Curve) curves.get(i)).enlarge(r);
            }
        }
        return (cachedBounds = r);
    }

    /**
     * Returns a high precision bounding {@link Rectangle2D} that
     * completely encloses this <code>Area</code>.
     * <p>
     * The Area class will attempt to return the tightest bounding
     * box possible for the Shape.  The bounding box will not be
     * padded to include the control points of curves in the outline
     * of the Shape, but should tightly fit the actual geometry of
     * the outline itself.
     * <p>
     *  返回完全包含此<code> Area </code>的高精度边界{@link Rectangle2D}
     * <p>
     * Area类将尝试返回可能的最小边界框的形状边界框不会被填充以包括在Shape的轮廓中的曲线的控制点,但应该紧密地适合轮廓本身的实际几何
     * 
     * 
     * @return    the bounding <code>Rectangle2D</code> for the
     * <code>Area</code>.
     * @since 1.2
     */
    public Rectangle2D getBounds2D() {
        return getCachedBounds().getBounds2D();
    }

    /**
     * Returns a bounding {@link Rectangle} that completely encloses
     * this <code>Area</code>.
     * <p>
     * The Area class will attempt to return the tightest bounding
     * box possible for the Shape.  The bounding box will not be
     * padded to include the control points of curves in the outline
     * of the Shape, but should tightly fit the actual geometry of
     * the outline itself.  Since the returned object represents
     * the bounding box with integers, the bounding box can only be
     * as tight as the nearest integer coordinates that encompass
     * the geometry of the Shape.
     * <p>
     *  返回完全包含此<code> Area </code>的边界{@link Rectangle}
     * <p>
     *  Area类将尝试返回可能的最小边界框的形状边界框不会被填充以包括在Shape的轮廓中的曲线的控制点,但是应该紧密适合轮廓本身的实际几何形状由于返回对象表示具有整数的边界框,边界框只能与包含Shape的
     * 几何形状的最近的整数坐标一样紧密。
     * 
     * 
     * @return    the bounding <code>Rectangle</code> for the
     * <code>Area</code>.
     * @since 1.2
     */
    public Rectangle getBounds() {
        return getCachedBounds().getBounds();
    }

    /**
     * Returns an exact copy of this <code>Area</code> object.
     * <p>
     * 返回此<code> Area </code>对象的精确副本
     * 
     * 
     * @return    Created clone object
     * @since 1.2
     */
    public Object clone() {
        return new Area(this);
    }

    /**
     * Tests whether the geometries of the two <code>Area</code> objects
     * are equal.
     * This method will return false if the argument is null.
     * <p>
     *  测试两个<code> Area </code>对象的几何是否相等如果参数为null,此方法将返回false
     * 
     * 
     * @param   other  the <code>Area</code> to be compared to this
     *          <code>Area</code>
     * @return  <code>true</code> if the two geometries are equal;
     *          <code>false</code> otherwise.
     * @since 1.2
     */
    public boolean equals(Area other) {
        // REMIND: A *much* simpler operation should be possible...
        // Should be able to do a curve-wise comparison since all Areas
        // should evaluate their curves in the same top-down order.
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        Vector c = new AreaOp.XorOp().calculate(this.curves, other.curves);
        return c.isEmpty();
    }

    /**
     * Transforms the geometry of this <code>Area</code> using the specified
     * {@link AffineTransform}.  The geometry is transformed in place, which
     * permanently changes the enclosed area defined by this object.
     * <p>
     *  使用指定的{@link AffineTransform}转换此<code> Area </code>的几何。几何将被转换到位,永久更改此对象定义的封闭区域
     * 
     * 
     * @param t  the transformation used to transform the area
     * @throws NullPointerException if <code>t</code> is null
     * @since 1.2
     */
    public void transform(AffineTransform t) {
        if (t == null) {
            throw new NullPointerException("transform must not be null");
        }
        // REMIND: A simpler operation can be performed for some types
        // of transform.
        curves = pathToCurves(getPathIterator(t));
        invalidateBounds();
    }

    /**
     * Creates a new <code>Area</code> object that contains the same
     * geometry as this <code>Area</code> transformed by the specified
     * <code>AffineTransform</code>.  This <code>Area</code> object
     * is unchanged.
     * <p>
     *  创建一个新的<code> Area </code>对象,该对象包含与通过指定的<code> AffineTransform </code>转换的<code> Area </code>相同的几何体</code>
     * 不变。
     * 
     * 
     * @param t  the specified <code>AffineTransform</code> used to transform
     *           the new <code>Area</code>
     * @throws NullPointerException if <code>t</code> is null
     * @return   a new <code>Area</code> object representing the transformed
     *           geometry.
     * @since 1.2
     */
    public Area createTransformedArea(AffineTransform t) {
        Area a = new Area(this);
        a.transform(t);
        return a;
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
        if (!getCachedBounds().contains(x, y)) {
            return false;
        }
        Enumeration enum_ = curves.elements();
        int crossings = 0;
        while (enum_.hasMoreElements()) {
            Curve c = (Curve) enum_.nextElement();
            crossings += c.crossingsFor(x, y);
        }
        return ((crossings & 1) == 1);
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
    public boolean contains(double x, double y, double w, double h) {
        if (w < 0 || h < 0) {
            return false;
        }
        if (!getCachedBounds().contains(x, y, w, h)) {
            return false;
        }
        Crossings c = Crossings.findCrossings(curves, x, y, x+w, y+h);
        return (c != null && c.covers(y, y+h));
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
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.2
     */
    public boolean intersects(double x, double y, double w, double h) {
        if (w < 0 || h < 0) {
            return false;
        }
        if (!getCachedBounds().intersects(x, y, w, h)) {
            return false;
        }
        Crossings c = Crossings.findCrossings(curves, x, y, x+w, y+h);
        return (c == null || !c.isEmpty());
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
     * Creates a {@link PathIterator} for the outline of this
     * <code>Area</code> object.  This <code>Area</code> object is unchanged.
     * <p>
     * 为此<code> Area </code>对象的大纲创建一个{@link PathIterator}这个<code> Area </code>对象不变
     * 
     * 
     * @param at an optional <code>AffineTransform</code> to be applied to
     * the coordinates as they are returned in the iteration, or
     * <code>null</code> if untransformed coordinates are desired
     * @return    the <code>PathIterator</code> object that returns the
     *          geometry of the outline of this <code>Area</code>, one
     *          segment at a time.
     * @since 1.2
     */
    public PathIterator getPathIterator(AffineTransform at) {
        return new AreaIterator(curves, at);
    }

    /**
     * Creates a <code>PathIterator</code> for the flattened outline of
     * this <code>Area</code> object.  Only uncurved path segments
     * represented by the SEG_MOVETO, SEG_LINETO, and SEG_CLOSE point
     * types are returned by the iterator.  This <code>Area</code>
     * object is unchanged.
     * <p>
     *  为此<code> Area </code>对象的扁平轮廓创建<code> PathIterator </code>只有由SEG_MOVETO,SEG_LINETO和SEG_CLOSE表示的未弯曲路径段
     * 由迭代器返回此<code> Area </code>对象不变。
     * 
     * @param at an optional <code>AffineTransform</code> to be
     * applied to the coordinates as they are returned in the
     * iteration, or <code>null</code> if untransformed coordinates
     * are desired
     * @param flatness the maximum amount that the control points
     * for a given curve can vary from colinear before a subdivided
     * curve is replaced by a straight line connecting the end points
     * @return    the <code>PathIterator</code> object that returns the
     * geometry of the outline of this <code>Area</code>, one segment
     * at a time.
     * @since 1.2
     */
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return new FlatteningPathIterator(getPathIterator(at), flatness);
    }
}

class AreaIterator implements PathIterator {
    private AffineTransform transform;
    private Vector curves;
    private int index;
    private Curve prevcurve;
    private Curve thiscurve;

    public AreaIterator(Vector curves, AffineTransform at) {
        this.curves = curves;
        this.transform = at;
        if (curves.size() >= 1) {
            thiscurve = (Curve) curves.get(0);
        }
    }

    public int getWindingRule() {
        // REMIND: Which is better, EVEN_ODD or NON_ZERO?
        //         The paths calculated could be classified either way.
        //return WIND_EVEN_ODD;
        return WIND_NON_ZERO;
    }

    public boolean isDone() {
        return (prevcurve == null && thiscurve == null);
    }

    public void next() {
        if (prevcurve != null) {
            prevcurve = null;
        } else {
            prevcurve = thiscurve;
            index++;
            if (index < curves.size()) {
                thiscurve = (Curve) curves.get(index);
                if (thiscurve.getOrder() != 0 &&
                    prevcurve.getX1() == thiscurve.getX0() &&
                    prevcurve.getY1() == thiscurve.getY0())
                {
                    prevcurve = null;
                }
            } else {
                thiscurve = null;
            }
        }
    }

    public int currentSegment(float coords[]) {
        double dcoords[] = new double[6];
        int segtype = currentSegment(dcoords);
        int numpoints = (segtype == SEG_CLOSE ? 0
                         : (segtype == SEG_QUADTO ? 2
                            : (segtype == SEG_CUBICTO ? 3
                               : 1)));
        for (int i = 0; i < numpoints * 2; i++) {
            coords[i] = (float) dcoords[i];
        }
        return segtype;
    }

    public int currentSegment(double coords[]) {
        int segtype;
        int numpoints;
        if (prevcurve != null) {
            // Need to finish off junction between curves
            if (thiscurve == null || thiscurve.getOrder() == 0) {
                return SEG_CLOSE;
            }
            coords[0] = thiscurve.getX0();
            coords[1] = thiscurve.getY0();
            segtype = SEG_LINETO;
            numpoints = 1;
        } else if (thiscurve == null) {
            throw new NoSuchElementException("area iterator out of bounds");
        } else {
            segtype = thiscurve.getSegment(coords);
            numpoints = thiscurve.getOrder();
            if (numpoints == 0) {
                numpoints = 1;
            }
        }
        if (transform != null) {
            transform.transform(coords, 0, coords, 0, numpoints);
        }
        return segtype;
    }
}
