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

package java.awt.geom;

import java.lang.annotation.Native;

/**
 * The <code>PathIterator</code> interface provides the mechanism
 * for objects that implement the {@link java.awt.Shape Shape}
 * interface to return the geometry of their boundary by allowing
 * a caller to retrieve the path of that boundary a segment at a
 * time.  This interface allows these objects to retrieve the path of
 * their boundary a segment at a time by using 1st through 3rd order
 * B&eacute;zier curves, which are lines and quadratic or cubic
 * B&eacute;zier splines.
 * <p>
 * Multiple subpaths can be expressed by using a "MOVETO" segment to
 * create a discontinuity in the geometry to move from the end of
 * one subpath to the beginning of the next.
 * <p>
 * Each subpath can be closed manually by ending the last segment in
 * the subpath on the same coordinate as the beginning "MOVETO" segment
 * for that subpath or by using a "CLOSE" segment to append a line
 * segment from the last point back to the first.
 * Be aware that manually closing an outline as opposed to using a
 * "CLOSE" segment to close the path might result in different line
 * style decorations being used at the end points of the subpath.
 * For example, the {@link java.awt.BasicStroke BasicStroke} object
 * uses a line "JOIN" decoration to connect the first and last points
 * if a "CLOSE" segment is encountered, whereas simply ending the path
 * on the same coordinate as the beginning coordinate results in line
 * "CAP" decorations being used at the ends.
 *
 * <p>
 *  <code> PathIterator </code>接口为实现{@link java.awt.Shape Shape}接口的对象提供了返回其边界几何的机制,允许调用者检索该边界的路径一个时间。
 * 这个接口允许这些对象通过使用第一到第三阶B&eacute; zier曲线(它们是线和二次或三次B&eacute; zier样条)来一次检索其边界的路径。
 * <p>
 *  多个子路径可以通过使用"MOVETO"段来表示,以在几何形状中创建不连续以从一个子路径的末端移动到下一个子路径的开始。
 * <p>
 * 每个子路径可以通过在与子路径的开始"MOVETO"段相同的坐标上结束子路径中的最后一个段来手动关闭,或者通过使用"关闭"段来将最后一个点的线段附加回到第一个。
 * 请注意,手动关闭轮廓而不是使用"关闭"段关闭路径可能会导致在子路径的端点使用不同的线样式装饰。
 * 例如,如果遇到"CLOSE"段,{@link java.awt.BasicStroke BasicStroke}对象使用线"JOIN"修饰来连接第一个和最后一个点,而只是结束与开始时相同坐标上的路径坐标
 * 导致在末端使用线"CAP"装饰。
 * 请注意,手动关闭轮廓而不是使用"关闭"段关闭路径可能会导致在子路径的端点使用不同的线样式装饰。
 * 
 * 
 * @see java.awt.Shape
 * @see java.awt.BasicStroke
 *
 * @author Jim Graham
 */
public interface PathIterator {
    /**
     * The winding rule constant for specifying an even-odd rule
     * for determining the interior of a path.
     * The even-odd rule specifies that a point lies inside the
     * path if a ray drawn in any direction from that point to
     * infinity is crossed by path segments an odd number of times.
     * <p>
     *  用于指定用于确定路径内部的偶奇规则的缠绕规则常数。偶奇规则指定如果从该点到无穷远的任何方向绘制的光线被奇数次的路径段穿过,则点位于路径内部。
     * 
     */
    @Native public static final int WIND_EVEN_ODD       = 0;

    /**
     * The winding rule constant for specifying a non-zero rule
     * for determining the interior of a path.
     * The non-zero rule specifies that a point lies inside the
     * path if a ray drawn in any direction from that point to
     * infinity is crossed by path segments a different number
     * of times in the counter-clockwise direction than the
     * clockwise direction.
     * <p>
     *  卷绕规则常数,用于指定用于确定路径内部的非零规则。非零规则指定如果沿着从顺时针方向的逆时针方向以不同次数跨越从该点到无穷大的任何方向绘制的光线,则点位于路径内部。
     * 
     */
    @Native public static final int WIND_NON_ZERO       = 1;

    /**
     * The segment type constant for a point that specifies the
     * starting location for a new subpath.
     * <p>
     *  指定新子路径的起始位置的点的段类型常量。
     * 
     */
    @Native public static final int SEG_MOVETO          = 0;

    /**
     * The segment type constant for a point that specifies the
     * end point of a line to be drawn from the most recently
     * specified point.
     * <p>
     * 指定要从最近指定的点绘制的线的终点的点的线段类型常数。
     * 
     */
    @Native public static final int SEG_LINETO          = 1;

    /**
     * The segment type constant for the pair of points that specify
     * a quadratic parametric curve to be drawn from the most recently
     * specified point.
     * The curve is interpolated by solving the parametric control
     * equation in the range <code>(t=[0..1])</code> using
     * the most recently specified (current) point (CP),
     * the first control point (P1),
     * and the final interpolated control point (P2).
     * The parametric control equation for this curve is:
     * <pre>
     *          P(t) = B(2,0)*CP + B(2,1)*P1 + B(2,2)*P2
     *          0 &lt;= t &lt;= 1
     *
     *        B(n,m) = mth coefficient of nth degree Bernstein polynomial
     *               = C(n,m) * t^(m) * (1 - t)^(n-m)
     *        C(n,m) = Combinations of n things, taken m at a time
     *               = n! / (m! * (n-m)!)
     * </pre>
     * <p>
     *  指定从最近指定的点绘制的二次参数曲线的点对的线段类型常数。
     * 通过使用最近指定的(当前)点(CP),第一控制点(P1)和第二控制点(P1)求解在<code>(t = [0..1])</code>范围内的参数控制方程, ,和最终内插控制点(P2)。
     * 该曲线的参数控制方程为：。
     * <pre>
     *  P(t)= B(2,0)* CP + B(2,1)* P1 + B(2,2)* P2 0 <= t <= 1
     * 
     *  B(n,m)=第n次伯恩斯坦多项式的第m个系数= C(n,m)* t ^(m)*(1-t) m在一次= n！ /(m！*(n-m)！)
     * </pre>
     */
    @Native public static final int SEG_QUADTO          = 2;

    /**
     * The segment type constant for the set of 3 points that specify
     * a cubic parametric curve to be drawn from the most recently
     * specified point.
     * The curve is interpolated by solving the parametric control
     * equation in the range <code>(t=[0..1])</code> using
     * the most recently specified (current) point (CP),
     * the first control point (P1),
     * the second control point (P2),
     * and the final interpolated control point (P3).
     * The parametric control equation for this curve is:
     * <pre>
     *          P(t) = B(3,0)*CP + B(3,1)*P1 + B(3,2)*P2 + B(3,3)*P3
     *          0 &lt;= t &lt;= 1
     *
     *        B(n,m) = mth coefficient of nth degree Bernstein polynomial
     *               = C(n,m) * t^(m) * (1 - t)^(n-m)
     *        C(n,m) = Combinations of n things, taken m at a time
     *               = n! / (m! * (n-m)!)
     * </pre>
     * This form of curve is commonly known as a B&eacute;zier curve.
     * <p>
     *  用于指定从最近指定点绘制的三次参数曲线的3点集合的线段类型常数。
     * 通过使用最近指定的(当前)点(CP),第一控制点(P1)和第二控制点(P1)求解在<code>(t = [0..1])</code>范围内的参数控制方程, ,第二控制点(P2)和最终内插控制点(P3)。
     *  用于指定从最近指定点绘制的三次参数曲线的3点集合的线段类型常数。该曲线的参数控制方程为：。
     * <pre>
     *  P(t)= B(3,0)* CP + B(3,1)* P1 + B(3,2)* P2 + B(3,3)* P30≤t≤1
     * 
     *  B(n,m)=第n次伯恩斯坦多项式的第m个系数= C(n,m)* t ^(m)*(1-t) m在一次= n！ /(m！*(n-m)！)
     * </pre>
     * 这种形式的曲线通常称为B&eacute; zier曲线。
     * 
     */
    @Native public static final int SEG_CUBICTO         = 3;

    /**
     * The segment type constant that specifies that
     * the preceding subpath should be closed by appending a line segment
     * back to the point corresponding to the most recent SEG_MOVETO.
     * <p>
     *  段类型常量,指定前一个子路径应通过将线段追加到对应于最近的SEG_MOVETO的点来关闭。
     * 
     */
    @Native public static final int SEG_CLOSE           = 4;

    /**
     * Returns the winding rule for determining the interior of the
     * path.
     * <p>
     *  返回确定路径内部的绕组规则。
     * 
     * 
     * @return the winding rule.
     * @see #WIND_EVEN_ODD
     * @see #WIND_NON_ZERO
     */
    public int getWindingRule();

    /**
     * Tests if the iteration is complete.
     * <p>
     *  测试迭代是否完成。
     * 
     * 
     * @return <code>true</code> if all the segments have
     * been read; <code>false</code> otherwise.
     */
    public boolean isDone();

    /**
     * Moves the iterator to the next segment of the path forwards
     * along the primary direction of traversal as long as there are
     * more points in that direction.
     * <p>
     *  只要在该方向上有更多的点,将迭代器沿着遍历的主要方向向前移动到路径的下一段。
     * 
     */
    public void next();

    /**
     * Returns the coordinates and type of the current path segment in
     * the iteration.
     * The return value is the path-segment type:
     * SEG_MOVETO, SEG_LINETO, SEG_QUADTO, SEG_CUBICTO, or SEG_CLOSE.
     * A float array of length 6 must be passed in and can be used to
     * store the coordinates of the point(s).
     * Each point is stored as a pair of float x,y coordinates.
     * SEG_MOVETO and SEG_LINETO types returns one point,
     * SEG_QUADTO returns two points,
     * SEG_CUBICTO returns 3 points
     * and SEG_CLOSE does not return any points.
     * <p>
     *  返回迭代中当前路径段的坐标和类型。返回值是路径段类型：SEG_MOVETO,SEG_LINETO,SEG_QUADTO,SEG_CUBICTO或SEG_CLOSE。
     * 必须传入长度为6的float数组,并可用于存储点的坐标。每个点都存储为一对float x,y坐标。
     *  SEG_MOVETO和SEG_LINETO类型返回一个点,SEG_QUADTO返回两个点,SEG_CUBICTO返回3个点,SEG_CLOSE不返回任何点。
     * 
     * 
     * @param coords an array that holds the data returned from
     * this method
     * @return the path-segment type of the current path segment.
     * @see #SEG_MOVETO
     * @see #SEG_LINETO
     * @see #SEG_QUADTO
     * @see #SEG_CUBICTO
     * @see #SEG_CLOSE
     */
    public int currentSegment(float[] coords);

    /**
     * Returns the coordinates and type of the current path segment in
     * the iteration.
     * The return value is the path-segment type:
     * SEG_MOVETO, SEG_LINETO, SEG_QUADTO, SEG_CUBICTO, or SEG_CLOSE.
     * A double array of length 6 must be passed in and can be used to
     * store the coordinates of the point(s).
     * Each point is stored as a pair of double x,y coordinates.
     * SEG_MOVETO and SEG_LINETO types returns one point,
     * SEG_QUADTO returns two points,
     * SEG_CUBICTO returns 3 points
     * and SEG_CLOSE does not return any points.
     * <p>
     *  返回迭代中当前路径段的坐标和类型。返回值是路径段类型：SEG_MOVETO,SEG_LINETO,SEG_QUADTO,SEG_CUBICTO或SEG_CLOSE。
     * 必须传入长度为6的双数组,并可用于存储点的坐标。每个点被存储为一对双x,y坐标。
     * 
     * @param coords an array that holds the data returned from
     * this method
     * @return the path-segment type of the current path segment.
     * @see #SEG_MOVETO
     * @see #SEG_LINETO
     * @see #SEG_QUADTO
     * @see #SEG_CUBICTO
     * @see #SEG_CLOSE
     */
    public int currentSegment(double[] coords);
}
