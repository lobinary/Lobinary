/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, Oracle and/or its affiliates. All rights reserved.
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

import java.util.*;

/**
 * A utility class to iterate over the path segments of a quadratic curve
 * segment through the PathIterator interface.
 *
 * <p>
 *  一个实用程序类,通过PathIterator接口对二次曲线段的路径段进行迭代。
 * 
 * 
 * @author      Jim Graham
 */
class QuadIterator implements PathIterator {
    QuadCurve2D quad;
    AffineTransform affine;
    int index;

    QuadIterator(QuadCurve2D q, AffineTransform at) {
        this.quad = q;
        this.affine = at;
    }

    /**
     * Return the winding rule for determining the insideness of the
     * path.
     * <p>
     *  返回绕组规则以确定路径的隐藏性。
     * 
     * 
     * @see #WIND_EVEN_ODD
     * @see #WIND_NON_ZERO
     */
    public int getWindingRule() {
        return WIND_NON_ZERO;
    }

    /**
     * Tests if there are more points to read.
     * <p>
     *  测试是否有更多的要读取的点。
     * 
     * 
     * @return true if there are more points to read
     */
    public boolean isDone() {
        return (index > 1);
    }

    /**
     * Moves the iterator to the next segment of the path forwards
     * along the primary direction of traversal as long as there are
     * more points in that direction.
     * <p>
     *  只要在该方向上有更多的点,将迭代器沿着遍历的主要方向向前移动到路径的下一段。
     * 
     */
    public void next() {
        index++;
    }

    /**
     * Returns the coordinates and type of the current path segment in
     * the iteration.
     * The return value is the path segment type:
     * SEG_MOVETO, SEG_LINETO, SEG_QUADTO, SEG_CUBICTO, or SEG_CLOSE.
     * A float array of length 6 must be passed in and may be used to
     * store the coordinates of the point(s).
     * Each point is stored as a pair of float x,y coordinates.
     * SEG_MOVETO and SEG_LINETO types will return one point,
     * SEG_QUADTO will return two points,
     * SEG_CUBICTO will return 3 points
     * and SEG_CLOSE will not return any points.
     * <p>
     *  返回迭代中当前路径段的坐标和类型。返回值是路径段类型：SEG_MOVETO,SEG_LINETO,SEG_QUADTO,SEG_CUBICTO或SEG_CLOSE。
     * 必须传入长度为6的float数组,并可用于存储点的坐标。每个点都存储为一对float x,y坐标。
     *  SEG_MOVETO和SEG_LINETO类型将返回一个点,SEG_QUADTO将返回两个点,SEG_CUBICTO将返回3个点,SEG_CLOSE将不返回任何点。
     * 
     * 
     * @see #SEG_MOVETO
     * @see #SEG_LINETO
     * @see #SEG_QUADTO
     * @see #SEG_CUBICTO
     * @see #SEG_CLOSE
     */
    public int currentSegment(float[] coords) {
        if (isDone()) {
            throw new NoSuchElementException("quad iterator iterator out of bounds");
        }
        int type;
        if (index == 0) {
            coords[0] = (float) quad.getX1();
            coords[1] = (float) quad.getY1();
            type = SEG_MOVETO;
        } else {
            coords[0] = (float) quad.getCtrlX();
            coords[1] = (float) quad.getCtrlY();
            coords[2] = (float) quad.getX2();
            coords[3] = (float) quad.getY2();
            type = SEG_QUADTO;
        }
        if (affine != null) {
            affine.transform(coords, 0, coords, 0, index == 0 ? 1 : 2);
        }
        return type;
    }

    /**
     * Returns the coordinates and type of the current path segment in
     * the iteration.
     * The return value is the path segment type:
     * SEG_MOVETO, SEG_LINETO, SEG_QUADTO, SEG_CUBICTO, or SEG_CLOSE.
     * A double array of length 6 must be passed in and may be used to
     * store the coordinates of the point(s).
     * Each point is stored as a pair of double x,y coordinates.
     * SEG_MOVETO and SEG_LINETO types will return one point,
     * SEG_QUADTO will return two points,
     * SEG_CUBICTO will return 3 points
     * and SEG_CLOSE will not return any points.
     * <p>
     * 返回迭代中当前路径段的坐标和类型。返回值是路径段类型：SEG_MOVETO,SEG_LINETO,SEG_QUADTO,SEG_CUBICTO或SEG_CLOSE。
     * 必须传入长度为6的双数组,并可用于存储点的坐标。每个点被存储为一对双x,y坐标。
     * 
     * @see #SEG_MOVETO
     * @see #SEG_LINETO
     * @see #SEG_QUADTO
     * @see #SEG_CUBICTO
     * @see #SEG_CLOSE
     */
    public int currentSegment(double[] coords) {
        if (isDone()) {
            throw new NoSuchElementException("quad iterator iterator out of bounds");
        }
        int type;
        if (index == 0) {
            coords[0] = quad.getX1();
            coords[1] = quad.getY1();
            type = SEG_MOVETO;
        } else {
            coords[0] = quad.getCtrlX();
            coords[1] = quad.getCtrlY();
            coords[2] = quad.getX2();
            coords[3] = quad.getY2();
            type = SEG_QUADTO;
        }
        if (affine != null) {
            affine.transform(coords, 0, coords, 0, index == 0 ? 1 : 2);
        }
        return type;
    }
}
