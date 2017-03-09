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

package java.awt.geom;

import java.util.*;

/**
 * A utility class to iterate over the path segments of an ellipse
 * through the PathIterator interface.
 *
 * <p>
 *  一个实用程序类,通过PathIterator接口对椭圆的路径段进行迭代。
 * 
 * 
 * @author      Jim Graham
 */
class EllipseIterator implements PathIterator {
    double x, y, w, h;
    AffineTransform affine;
    int index;

    EllipseIterator(Ellipse2D e, AffineTransform at) {
        this.x = e.getX();
        this.y = e.getY();
        this.w = e.getWidth();
        this.h = e.getHeight();
        this.affine = at;
        if (w < 0 || h < 0) {
            index = 6;
        }
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
        return index > 5;
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

    // ArcIterator.btan(Math.PI/2)
    public static final double CtrlVal = 0.5522847498307933;

    /*
     * ctrlpts contains the control points for a set of 4 cubic
     * bezier curves that approximate a circle of radius 0.5
     * centered at 0.5, 0.5
     * <p>
     *  ctrlpts包含一组4个三次贝塞尔曲线的控制点,近似以0.5,0.5为中心的半径0.5的圆
     * 
     */
    private static final double pcv = 0.5 + CtrlVal * 0.5;
    private static final double ncv = 0.5 - CtrlVal * 0.5;
    private static double ctrlpts[][] = {
        {  1.0,  pcv,  pcv,  1.0,  0.5,  1.0 },
        {  ncv,  1.0,  0.0,  pcv,  0.0,  0.5 },
        {  0.0,  ncv,  ncv,  0.0,  0.5,  0.0 },
        {  pcv,  0.0,  1.0,  ncv,  1.0,  0.5 }
    };

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
            throw new NoSuchElementException("ellipse iterator out of bounds");
        }
        if (index == 5) {
            return SEG_CLOSE;
        }
        if (index == 0) {
            double ctrls[] = ctrlpts[3];
            coords[0] = (float) (x + ctrls[4] * w);
            coords[1] = (float) (y + ctrls[5] * h);
            if (affine != null) {
                affine.transform(coords, 0, coords, 0, 1);
            }
            return SEG_MOVETO;
        }
        double ctrls[] = ctrlpts[index - 1];
        coords[0] = (float) (x + ctrls[0] * w);
        coords[1] = (float) (y + ctrls[1] * h);
        coords[2] = (float) (x + ctrls[2] * w);
        coords[3] = (float) (y + ctrls[3] * h);
        coords[4] = (float) (x + ctrls[4] * w);
        coords[5] = (float) (y + ctrls[5] * h);
        if (affine != null) {
            affine.transform(coords, 0, coords, 0, 3);
        }
        return SEG_CUBICTO;
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
            throw new NoSuchElementException("ellipse iterator out of bounds");
        }
        if (index == 5) {
            return SEG_CLOSE;
        }
        if (index == 0) {
            double ctrls[] = ctrlpts[3];
            coords[0] = x + ctrls[4] * w;
            coords[1] = y + ctrls[5] * h;
            if (affine != null) {
                affine.transform(coords, 0, coords, 0, 1);
            }
            return SEG_MOVETO;
        }
        double ctrls[] = ctrlpts[index - 1];
        coords[0] = x + ctrls[0] * w;
        coords[1] = y + ctrls[1] * h;
        coords[2] = x + ctrls[2] * w;
        coords[3] = y + ctrls[3] * h;
        coords[4] = x + ctrls[4] * w;
        coords[5] = y + ctrls[5] * h;
        if (affine != null) {
            affine.transform(coords, 0, coords, 0, 3);
        }
        return SEG_CUBICTO;
    }
}
