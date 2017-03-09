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

import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * The <code>Shape</code> interface provides definitions for objects
 * that represent some form of geometric shape.  The <code>Shape</code>
 * is described by a {@link PathIterator} object, which can express the
 * outline of the <code>Shape</code> as well as a rule for determining
 * how the outline divides the 2D plane into interior and exterior
 * points.  Each <code>Shape</code> object provides callbacks to get the
 * bounding box of the geometry, determine whether points or
 * rectangles lie partly or entirely within the interior
 * of the <code>Shape</code>, and retrieve a <code>PathIterator</code>
 * object that describes the trajectory path of the <code>Shape</code>
 * outline.
 * <p>
 * <a name="def_insideness"><b>Definition of insideness:</b></a>
 * A point is considered to lie inside a
 * <code>Shape</code> if and only if:
 * <ul>
 * <li> it lies completely
 * inside the<code>Shape</code> boundary <i>or</i>
 * <li>
 * it lies exactly on the <code>Shape</code> boundary <i>and</i> the
 * space immediately adjacent to the
 * point in the increasing <code>X</code> direction is
 * entirely inside the boundary <i>or</i>
 * <li>
 * it lies exactly on a horizontal boundary segment <b>and</b> the
 * space immediately adjacent to the point in the
 * increasing <code>Y</code> direction is inside the boundary.
 * </ul>
 * <p>The <code>contains</code> and <code>intersects</code> methods
 * consider the interior of a <code>Shape</code> to be the area it
 * encloses as if it were filled.  This means that these methods
 * consider
 * unclosed shapes to be implicitly closed for the purpose of
 * determining if a shape contains or intersects a rectangle or if a
 * shape contains a point.
 *
 * <p>
 *  <code> Shape </code>接口为表示某种形状的几何形状的对象提供了定义。
 *  <code> Shape </code>由{@link PathIterator}对象描述,它可以表示<code> Shape </code>的轮廓,以及用于确定轮廓如何将2D平面划分为内部和外部点。
 *  <code> Shape </code>接口为表示某种形状的几何形状的对象提供了定义。
 * 每个<code> Shape </code>对象提供回调以获取几何的边界框,确定点或矩形是否部分或全部位于<code> Shape </code>内部,并检索<code> PathIterator </code>
 * 对象,描述<code> Shape </code>大纲的轨迹路径。
 *  <code> Shape </code>接口为表示某种形状的几何形状的对象提供了定义。
 * <p>
 *  <a name="def_insideness"> <b>隐性定义：</b> </a>当且仅当以下情况时,点被认为位于<code> Shape </code>中：
 * <ul>
 *  <li>它完全位于<code> Shape </code>边界<i>或</i>内
 * <li>
 *  它正好位于<code> Shape </code>边界<i>和</i>上,与增加的<code> X </code>方向上的点直接相邻的空间完全在边界<i> </i>
 * <li>
 *  它正好位于水平边界段<b>上,并且<y> </b>在增加<code> Y </code>方向上与该点直接相邻的空间在边界内。
 * </ul>
 * <p> <code>包含</code>和<code> intersects </code>方法认为<code> Shape </code>的内部是它所包含的区域,就好像它被填充一样。
 * 这意味着这些方法认为未闭合的形状被隐含地闭合,用于确定形状是否包含或相交于矩形或者如果形状包含点。
 * 
 * 
 * @see java.awt.geom.PathIterator
 * @see java.awt.geom.AffineTransform
 * @see java.awt.geom.FlatteningPathIterator
 * @see java.awt.geom.GeneralPath
 *
 * @author Jim Graham
 * @since 1.2
 */
public interface Shape {
    /**
     * Returns an integer {@link Rectangle} that completely encloses the
     * <code>Shape</code>.  Note that there is no guarantee that the
     * returned <code>Rectangle</code> is the smallest bounding box that
     * encloses the <code>Shape</code>, only that the <code>Shape</code>
     * lies entirely within the indicated  <code>Rectangle</code>.  The
     * returned <code>Rectangle</code> might also fail to completely
     * enclose the <code>Shape</code> if the <code>Shape</code> overflows
     * the limited range of the integer data type.  The
     * <code>getBounds2D</code> method generally returns a
     * tighter bounding box due to its greater flexibility in
     * representation.
     *
     * <p>
     * Note that the <a href="{@docRoot}/java/awt/Shape.html#def_insideness">
     * definition of insideness</a> can lead to situations where points
     * on the defining outline of the {@code shape} may not be considered
     * contained in the returned {@code bounds} object, but only in cases
     * where those points are also not considered contained in the original
     * {@code shape}.
     * </p>
     * <p>
     * If a {@code point} is inside the {@code shape} according to the
     * {@link #contains(double x, double y) contains(point)} method, then
     * it must be inside the returned {@code Rectangle} bounds object
     * according to the {@link #contains(double x, double y) contains(point)}
     * method of the {@code bounds}. Specifically:
     * </p>
     * <p>
     *  {@code shape.contains(x,y)} requires {@code bounds.contains(x,y)}
     * </p>
     * <p>
     * If a {@code point} is not inside the {@code shape}, then it might
     * still be contained in the {@code bounds} object:
     * </p>
     * <p>
     *  {@code bounds.contains(x,y)} does not imply {@code shape.contains(x,y)}
     * </p>
     * <p>
     *  返回一个完整包含<code> Shape </code>的整数{@link Rectangle}。
     * 注意,不能保证返回的<code> Rectangle </code>是包围<code> Shape </code>的最小边界框,只是<code> Shape </code> <code> Rectang
     * le </code>。
     *  返回一个完整包含<code> Shape </code>的整数{@link Rectangle}。
     * 如果<code> Shape </code>溢出整数数据类型的有限范围,则返回的<code> Rectangle </code>也可能无法完全包含<code> Shape </code>。
     * 由于代码的更大的灵活性,<code> getBounds2D </code>方法通常返回更紧的边界框。
     * 
     * <p>
     *  请注意,<a href="{@docRoot}/java/awt/Shape.html#def_insideness">隐藏性定义</a>可能会导致{@code shape}的定义轮廓上的点不能被认为
     * 包含在返回的{@code bounds}对象中,但仅在这些点也不被认为包含在原始{@code shape}中的情况下。
     * </p>
     * <p>
     * 如果根据{@link #contains(double x,double y)contains(point)}方法,{@code point}在{@code shape}中,那么它必须在返回的{@code Rectangle}
     * 对象根据{@code #contains(double x,double y)contains(point)}方法的{@code bounds}。
     * 特别：。
     * </p>
     * <p>
     *  {@code shape.contains(x,y)}需要{@code bounds.contains(x,y)}
     * </p>
     * <p>
     *  如果{@code point}不在{@code shape}内,则它可能仍包含在{@code bounds}对象中：
     * </p>
     * <p>
     *  {@code bounds.contains(x,y)}并不意味着{@code shape.contains(x,y)}
     * </p>
     * 
     * @return an integer <code>Rectangle</code> that completely encloses
     *                 the <code>Shape</code>.
     * @see #getBounds2D
     * @since 1.2
     */
    public Rectangle getBounds();

    /**
     * Returns a high precision and more accurate bounding box of
     * the <code>Shape</code> than the <code>getBounds</code> method.
     * Note that there is no guarantee that the returned
     * {@link Rectangle2D} is the smallest bounding box that encloses
     * the <code>Shape</code>, only that the <code>Shape</code> lies
     * entirely within the indicated <code>Rectangle2D</code>.  The
     * bounding box returned by this method is usually tighter than that
     * returned by the <code>getBounds</code> method and never fails due
     * to overflow problems since the return value can be an instance of
     * the <code>Rectangle2D</code> that uses double precision values to
     * store the dimensions.
     *
     * <p>
     * Note that the <a href="{@docRoot}/java/awt/Shape.html#def_insideness">
     * definition of insideness</a> can lead to situations where points
     * on the defining outline of the {@code shape} may not be considered
     * contained in the returned {@code bounds} object, but only in cases
     * where those points are also not considered contained in the original
     * {@code shape}.
     * </p>
     * <p>
     * If a {@code point} is inside the {@code shape} according to the
     * {@link #contains(Point2D p) contains(point)} method, then it must
     * be inside the returned {@code Rectangle2D} bounds object according
     * to the {@link #contains(Point2D p) contains(point)} method of the
     * {@code bounds}. Specifically:
     * </p>
     * <p>
     *  {@code shape.contains(p)} requires {@code bounds.contains(p)}
     * </p>
     * <p>
     * If a {@code point} is not inside the {@code shape}, then it might
     * still be contained in the {@code bounds} object:
     * </p>
     * <p>
     *  {@code bounds.contains(p)} does not imply {@code shape.contains(p)}
     * </p>
     * <p>
     *  返回<code> Shape </code>的高精度和更精确的边界框比<code> getBounds </code>方法。
     * 注意,不能保证返回的{@link Rectangle2D}是包围<code> Shape </code>的最小边界框,只是<code> Shape </code>完全在指定的<code> Rectang
     * le2D </code>。
     *  返回<code> Shape </code>的高精度和更精确的边界框比<code> getBounds </code>方法。
     * 这个方法返回的边界框通常比<code> getBounds </code>方法返回的边框更紧,而且不会由于溢出问题而失败,因为返回值可以是<code> Rectangle2D </code>的一个实例使
     * 用双精度值存储维度。
     *  返回<code> Shape </code>的高精度和更精确的边界框比<code> getBounds </code>方法。
     * 
     * <p>
     * 请注意,<a href="{@docRoot}/java/awt/Shape.html#def_insideness">隐藏性定义</a>可能会导致{@code shape}的定义轮廓上的点不能被认为包
     * 含在返回的{@code bounds}对象中,但仅在这些点也不被认为包含在原始{@code shape}中的情况下。
     * </p>
     * <p>
     *  如果根据{@link #contains(Point2D p)contains(point)}方法,{@code point}在{@code shape}中,那么它必须在返回的{@code Rectangle2D}
     *  {@code bounds}的{@link #contains(Point2D p)contains(point)}方法。
     * 特别：。
     * </p>
     * <p>
     *  {@code shape.contains(p)}需要{@code bounds.contains(p)}
     * </p>
     * <p>
     *  如果{@code point}不在{@code shape}内,则它可能仍包含在{@code bounds}对象中：
     * </p>
     * <p>
     *  {@code bounds.contains(p)}并不意味着{@code shape.contains(p)}
     * </p>
     * 
     * @return an instance of <code>Rectangle2D</code> that is a
     *                 high-precision bounding box of the <code>Shape</code>.
     * @see #getBounds
     * @since 1.2
     */
    public Rectangle2D getBounds2D();

    /**
     * Tests if the specified coordinates are inside the boundary of the
     * <code>Shape</code>, as described by the
     * <a href="{@docRoot}/java/awt/Shape.html#def_insideness">
     * definition of insideness</a>.
     * <p>
     *  测试指定的坐标是否在<code> Shape </code>的边界内,如
     * <a href="{@docRoot}/java/awt/Shape.html#def_insideness">
     *  隐性的定义</a>。
     * 
     * 
     * @param x the specified X coordinate to be tested
     * @param y the specified Y coordinate to be tested
     * @return <code>true</code> if the specified coordinates are inside
     *         the <code>Shape</code> boundary; <code>false</code>
     *         otherwise.
     * @since 1.2
     */
    public boolean contains(double x, double y);

    /**
     * Tests if a specified {@link Point2D} is inside the boundary
     * of the <code>Shape</code>, as described by the
     * <a href="{@docRoot}/java/awt/Shape.html#def_insideness">
     * definition of insideness</a>.
     * <p>
     *  测试指定的{@link Point2D}是否在<code> Shape </code>的边界内,如
     * <a href="{@docRoot}/java/awt/Shape.html#def_insideness">
     *  隐性的定义</a>。
     * 
     * 
     * @param p the specified <code>Point2D</code> to be tested
     * @return <code>true</code> if the specified <code>Point2D</code> is
     *          inside the boundary of the <code>Shape</code>;
     *          <code>false</code> otherwise.
     * @since 1.2
     */
    public boolean contains(Point2D p);

    /**
     * Tests if the interior of the <code>Shape</code> intersects the
     * interior of a specified rectangular area.
     * The rectangular area is considered to intersect the <code>Shape</code>
     * if any point is contained in both the interior of the
     * <code>Shape</code> and the specified rectangular area.
     * <p>
     * The {@code Shape.intersects()} method allows a {@code Shape}
     * implementation to conservatively return {@code true} when:
     * <ul>
     * <li>
     * there is a high probability that the rectangular area and the
     * <code>Shape</code> intersect, but
     * <li>
     * the calculations to accurately determine this intersection
     * are prohibitively expensive.
     * </ul>
     * This means that for some {@code Shapes} this method might
     * return {@code true} even though the rectangular area does not
     * intersect the {@code Shape}.
     * The {@link java.awt.geom.Area Area} class performs
     * more accurate computations of geometric intersection than most
     * {@code Shape} objects and therefore can be used if a more precise
     * answer is required.
     *
     * <p>
     *  测试<code> Shape </code>内部是否与指定矩形区域的内部相交。
     * 如果任何点包含在<code> Shape </code>和指定的矩形区域的内部,则认为矩形区域与<code> Shape </code>相交。
     * <p>
     * {@code Shape.intersects()}方法允许{@code Shape}实现在以下情况下保守地返回{@code true}：
     * <ul>
     * <li>
     *  矩形区域和<code> Shape </code>相交的概率很高,但是
     * <li>
     *  准确地确定这个交叉的计算是非常昂贵的。
     * </ul>
     *  这意味着对于某些{@code Shapes},即使矩形区域与{@code Shape}不相交,此方法也可能返回{@code true}。
     *  {@link java.awt.geom.Area Area}类比大多数{@code Shape}对象执行几何交集的更精确的计算,因此可以在需要更精确的答案时使用。
     * 
     * 
     * @param x the X coordinate of the upper-left corner
     *          of the specified rectangular area
     * @param y the Y coordinate of the upper-left corner
     *          of the specified rectangular area
     * @param w the width of the specified rectangular area
     * @param h the height of the specified rectangular area
     * @return <code>true</code> if the interior of the <code>Shape</code> and
     *          the interior of the rectangular area intersect, or are
     *          both highly likely to intersect and intersection calculations
     *          would be too expensive to perform; <code>false</code> otherwise.
     * @see java.awt.geom.Area
     * @since 1.2
     */
    public boolean intersects(double x, double y, double w, double h);

    /**
     * Tests if the interior of the <code>Shape</code> intersects the
     * interior of a specified <code>Rectangle2D</code>.
     * The {@code Shape.intersects()} method allows a {@code Shape}
     * implementation to conservatively return {@code true} when:
     * <ul>
     * <li>
     * there is a high probability that the <code>Rectangle2D</code> and the
     * <code>Shape</code> intersect, but
     * <li>
     * the calculations to accurately determine this intersection
     * are prohibitively expensive.
     * </ul>
     * This means that for some {@code Shapes} this method might
     * return {@code true} even though the {@code Rectangle2D} does not
     * intersect the {@code Shape}.
     * The {@link java.awt.geom.Area Area} class performs
     * more accurate computations of geometric intersection than most
     * {@code Shape} objects and therefore can be used if a more precise
     * answer is required.
     *
     * <p>
     *  测试<code> Shape </code>内部是否与指定的<code> Rectangle2D </code>内部相交。
     *  {@code Shape.intersects()}方法允许{@code Shape}实现在以下情况下保守地返回{@code true}：。
     * <ul>
     * <li>
     *  很可能<code> Rectangle2D </code>和<code> Shape </code>相交,但是
     * <li>
     *  准确地确定这个交叉的计算是非常昂贵的。
     * </ul>
     *  这意味着对于某些{@code Shapes},即使{@code Rectangle2D}与{@code Shape}不相交,此方法也可能返回{@code true}。
     *  {@link java.awt.geom.Area Area}类比大多数{@code Shape}对象执行几何交集的更精确的计算,因此可以在需要更精确的答案时使用。
     * 
     * 
     * @param r the specified <code>Rectangle2D</code>
     * @return <code>true</code> if the interior of the <code>Shape</code> and
     *          the interior of the specified <code>Rectangle2D</code>
     *          intersect, or are both highly likely to intersect and intersection
     *          calculations would be too expensive to perform; <code>false</code>
     *          otherwise.
     * @see #intersects(double, double, double, double)
     * @since 1.2
     */
    public boolean intersects(Rectangle2D r);

    /**
     * Tests if the interior of the <code>Shape</code> entirely contains
     * the specified rectangular area.  All coordinates that lie inside
     * the rectangular area must lie within the <code>Shape</code> for the
     * entire rectangular area to be considered contained within the
     * <code>Shape</code>.
     * <p>
     * The {@code Shape.contains()} method allows a {@code Shape}
     * implementation to conservatively return {@code false} when:
     * <ul>
     * <li>
     * the <code>intersect</code> method returns <code>true</code> and
     * <li>
     * the calculations to determine whether or not the
     * <code>Shape</code> entirely contains the rectangular area are
     * prohibitively expensive.
     * </ul>
     * This means that for some {@code Shapes} this method might
     * return {@code false} even though the {@code Shape} contains
     * the rectangular area.
     * The {@link java.awt.geom.Area Area} class performs
     * more accurate geometric computations than most
     * {@code Shape} objects and therefore can be used if a more precise
     * answer is required.
     *
     * <p>
     * 测试<code> Shape </code>的内部是否完全包含指定的矩形区域。
     * 位于矩形区域内的所有坐标必须位于<code> Shape </code>内,整个矩形区域被视为包含在<code> Shape </code>中。
     * <p>
     *  {@code Shape.contains()}方法允许{@code Shape}实现在以下情况下保守地返回{@code false}：
     * <ul>
     * <li>
     *  <code> intersect </code>方法返回<code> true </code>和
     * <li>
     *  确定<code> Shape </code>是否完全包含矩形区域的计算是非常昂贵的。
     * </ul>
     *  这意味着对于某些{@code Shapes},即使{@code Shape}包含矩形区域,此方法也可能返回{@code false}。
     *  {@link java.awt.geom.Area Area}类比大多数{@code Shape}对象执行更准确的几何计算,因此如果需要更精确的答案,可以使用它。
     * 
     * 
     * @param x the X coordinate of the upper-left corner
     *          of the specified rectangular area
     * @param y the Y coordinate of the upper-left corner
     *          of the specified rectangular area
     * @param w the width of the specified rectangular area
     * @param h the height of the specified rectangular area
     * @return <code>true</code> if the interior of the <code>Shape</code>
     *          entirely contains the specified rectangular area;
     *          <code>false</code> otherwise or, if the <code>Shape</code>
     *          contains the rectangular area and the
     *          <code>intersects</code> method returns <code>true</code>
     *          and the containment calculations would be too expensive to
     *          perform.
     * @see java.awt.geom.Area
     * @see #intersects
     * @since 1.2
     */
    public boolean contains(double x, double y, double w, double h);

    /**
     * Tests if the interior of the <code>Shape</code> entirely contains the
     * specified <code>Rectangle2D</code>.
     * The {@code Shape.contains()} method allows a {@code Shape}
     * implementation to conservatively return {@code false} when:
     * <ul>
     * <li>
     * the <code>intersect</code> method returns <code>true</code> and
     * <li>
     * the calculations to determine whether or not the
     * <code>Shape</code> entirely contains the <code>Rectangle2D</code>
     * are prohibitively expensive.
     * </ul>
     * This means that for some {@code Shapes} this method might
     * return {@code false} even though the {@code Shape} contains
     * the {@code Rectangle2D}.
     * The {@link java.awt.geom.Area Area} class performs
     * more accurate geometric computations than most
     * {@code Shape} objects and therefore can be used if a more precise
     * answer is required.
     *
     * <p>
     *  测试<code> Shape </code>内部是否包含指定的<code> Rectangle2D </code>。
     *  {@code Shape.contains()}方法允许{@code Shape}实现在以下情况下保守地返回{@code false}：。
     * <ul>
     * <li>
     *  <code> intersect </code>方法返回<code> true </code>和
     * <li>
     *  确定<code> Shape </code>是否完全包含<code> Rectangle2D </code>的计算过于昂贵。
     * </ul>
     * 这意味着对于某些{@code Shapes},即使{@code Shape}包含{@code Rectangle2D},此方法也可能返回{@code false}。
     *  {@link java.awt.geom.Area Area}类比大多数{@code Shape}对象执行更准确的几何计算,因此如果需要更精确的答案,可以使用它。
     * 
     * 
     * @param r The specified <code>Rectangle2D</code>
     * @return <code>true</code> if the interior of the <code>Shape</code>
     *          entirely contains the <code>Rectangle2D</code>;
     *          <code>false</code> otherwise or, if the <code>Shape</code>
     *          contains the <code>Rectangle2D</code> and the
     *          <code>intersects</code> method returns <code>true</code>
     *          and the containment calculations would be too expensive to
     *          perform.
     * @see #contains(double, double, double, double)
     * @since 1.2
     */
    public boolean contains(Rectangle2D r);

    /**
     * Returns an iterator object that iterates along the
     * <code>Shape</code> boundary and provides access to the geometry of the
     * <code>Shape</code> outline.  If an optional {@link AffineTransform}
     * is specified, the coordinates returned in the iteration are
     * transformed accordingly.
     * <p>
     * Each call to this method returns a fresh <code>PathIterator</code>
     * object that traverses the geometry of the <code>Shape</code> object
     * independently from any other <code>PathIterator</code> objects in use
     * at the same time.
     * <p>
     * It is recommended, but not guaranteed, that objects
     * implementing the <code>Shape</code> interface isolate iterations
     * that are in process from any changes that might occur to the original
     * object's geometry during such iterations.
     *
     * <p>
     *  返回沿着<code> Shape </code>边界迭代的迭代器对象,并提供对<code> Shape </code>大纲的几何体的访问。
     * 如果指定了可选的{@link AffineTransform},则迭代中返回的坐标将相应地进行转换。
     * <p>
     *  每次调用此方法时,都会返回一个新的<code> PathIterator </code>对象,该对象独立于任何其他<code> PathIterator </code>对象时间。
     * <p>
     *  建议但不是保证,实现<code> Shape </code>接口的对象隔离在迭代期间可能发生到原始对象的几何的任何改变的过程中的迭代。
     * 
     * 
     * @param at an optional <code>AffineTransform</code> to be applied to the
     *          coordinates as they are returned in the iteration, or
     *          <code>null</code> if untransformed coordinates are desired
     * @return a new <code>PathIterator</code> object, which independently
     *          traverses the geometry of the <code>Shape</code>.
     * @since 1.2
     */
    public PathIterator getPathIterator(AffineTransform at);

    /**
     * Returns an iterator object that iterates along the <code>Shape</code>
     * boundary and provides access to a flattened view of the
     * <code>Shape</code> outline geometry.
     * <p>
     * Only SEG_MOVETO, SEG_LINETO, and SEG_CLOSE point types are
     * returned by the iterator.
     * <p>
     * If an optional <code>AffineTransform</code> is specified,
     * the coordinates returned in the iteration are transformed
     * accordingly.
     * <p>
     * The amount of subdivision of the curved segments is controlled
     * by the <code>flatness</code> parameter, which specifies the
     * maximum distance that any point on the unflattened transformed
     * curve can deviate from the returned flattened path segments.
     * Note that a limit on the accuracy of the flattened path might be
     * silently imposed, causing very small flattening parameters to be
     * treated as larger values.  This limit, if there is one, is
     * defined by the particular implementation that is used.
     * <p>
     * Each call to this method returns a fresh <code>PathIterator</code>
     * object that traverses the <code>Shape</code> object geometry
     * independently from any other <code>PathIterator</code> objects in use at
     * the same time.
     * <p>
     * It is recommended, but not guaranteed, that objects
     * implementing the <code>Shape</code> interface isolate iterations
     * that are in process from any changes that might occur to the original
     * object's geometry during such iterations.
     *
     * <p>
     *  返回沿着<code> Shape </code>边界迭代的迭代器对象,并提供对<code> Shape </code>轮廓几何图形的平面视图的访问。
     * <p>
     *  迭代器只返回SEG_MOVETO,SEG_LINETO和SEG_CLOSE点类型。
     * <p>
     *  如果指定了可选的<code> AffineTransform </code>,则迭代中返回的坐标将相应地进行转换。
     * <p>
     * 曲线段的细分量由<code> flatness </code>参数控制,该参数指定未平坦变换曲线上的任何点可以偏离返回的平坦路径段的最大距离。
     * 
     * @param at an optional <code>AffineTransform</code> to be applied to the
     *          coordinates as they are returned in the iteration, or
     *          <code>null</code> if untransformed coordinates are desired
     * @param flatness the maximum distance that the line segments used to
     *          approximate the curved segments are allowed to deviate
     *          from any point on the original curve
     * @return a new <code>PathIterator</code> that independently traverses
     *         a flattened view of the geometry of the  <code>Shape</code>.
     * @since 1.2
     */
    public PathIterator getPathIterator(AffineTransform at, double flatness);
}
