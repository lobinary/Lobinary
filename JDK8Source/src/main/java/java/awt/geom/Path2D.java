/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2006, 2014, Oracle and/or its affiliates. All rights reserved.
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
import sun.awt.geom.Curve;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.Arrays;

/**
 * The {@code Path2D} class provides a simple, yet flexible
 * shape which represents an arbitrary geometric path.
 * It can fully represent any path which can be iterated by the
 * {@link PathIterator} interface including all of its segment
 * types and winding rules and it implements all of the
 * basic hit testing methods of the {@link Shape} interface.
 * <p>
 * Use {@link Path2D.Float} when dealing with data that can be represented
 * and used with floating point precision.  Use {@link Path2D.Double}
 * for data that requires the accuracy or range of double precision.
 * <p>
 * {@code Path2D} provides exactly those facilities required for
 * basic construction and management of a geometric path and
 * implementation of the above interfaces with little added
 * interpretation.
 * If it is useful to manipulate the interiors of closed
 * geometric shapes beyond simple hit testing then the
 * {@link Area} class provides additional capabilities
 * specifically targeted at closed figures.
 * While both classes nominally implement the {@code Shape}
 * interface, they differ in purpose and together they provide
 * two useful views of a geometric shape where {@code Path2D}
 * deals primarily with a trajectory formed by path segments
 * and {@code Area} deals more with interpretation and manipulation
 * of enclosed regions of 2D geometric space.
 * <p>
 * The {@link PathIterator} interface has more detailed descriptions
 * of the types of segments that make up a path and the winding rules
 * that control how to determine which regions are inside or outside
 * the path.
 *
 * <p>
 *  {@code Path2D}类提供了一个简单而灵活的形状,表示任意几何路径。
 * 它可以完全表示可以由{@link PathIterator}接口迭代的任何路径,包括其所有段类型和绕回规则,并实现{@link Shape}接口的所有基本命中测试方法。
 * <p>
 *  在处理可以使用浮点精度表示和使用的数据时,请使用{@link Path2D.Float}。对于需要精度或双精度范围的数据,请使用{@link Path2D.Double}。
 * <p>
 *  {@code Path2D}提供了几何路径的基本构建和管理所需的设施以及上述接口的实现,并且几乎没有增加解释。
 * 如果对简单命中测试以外的闭合几何形状的内部进行操作是有用的,那么{@link Area}类提供了专门针对闭合数字的附加功能。
 * 虽然这两个类名称上实现{@code Shape}接口,它们的目的不同,它们提供了几何形状的两个有用的视图,其中{@code Path2D}主要处理由路径段和{@code Area}更多具有2D几何空间的
 * 封闭区域的解释和操纵。
 * 如果对简单命中测试以外的闭合几何形状的内部进行操作是有用的,那么{@link Area}类提供了专门针对闭合数字的附加功能。
 * <p>
 * {@link PathIterator}界面更详细地描述了构成路径的段的类型,以及控制如何确定路径内部或外部的哪些区域的绕组规则。
 * 
 * 
 * @author Jim Graham
 * @since 1.6
 */
public abstract class Path2D implements Shape, Cloneable {
    /**
     * An even-odd winding rule for determining the interior of
     * a path.
     *
     * <p>
     *  用于确定路径内部的奇偶卷绕规则。
     * 
     * 
     * @see PathIterator#WIND_EVEN_ODD
     * @since 1.6
     */
    public static final int WIND_EVEN_ODD = PathIterator.WIND_EVEN_ODD;

    /**
     * A non-zero winding rule for determining the interior of a
     * path.
     *
     * <p>
     *  用于确定路径内部的非零绕组规则。
     * 
     * 
     * @see PathIterator#WIND_NON_ZERO
     * @since 1.6
     */
    public static final int WIND_NON_ZERO = PathIterator.WIND_NON_ZERO;

    // For code simplicity, copy these constants to our namespace
    // and cast them to byte constants for easy storage.
    private static final byte SEG_MOVETO  = (byte) PathIterator.SEG_MOVETO;
    private static final byte SEG_LINETO  = (byte) PathIterator.SEG_LINETO;
    private static final byte SEG_QUADTO  = (byte) PathIterator.SEG_QUADTO;
    private static final byte SEG_CUBICTO = (byte) PathIterator.SEG_CUBICTO;
    private static final byte SEG_CLOSE   = (byte) PathIterator.SEG_CLOSE;

    transient byte[] pointTypes;
    transient int numTypes;
    transient int numCoords;
    transient int windingRule;

    static final int INIT_SIZE = 20;
    static final int EXPAND_MAX = 500;

    /**
     * Constructs a new empty {@code Path2D} object.
     * It is assumed that the package sibling subclass that is
     * defaulting to this constructor will fill in all values.
     *
     * <p>
     *  构造一个新的空{@code Path2D}对象。假设默认为此构造函数的包同级子类将填充所有值。
     * 
     * 
     * @since 1.6
     */
    /* private protected */
    Path2D() {
    }

    /**
     * Constructs a new {@code Path2D} object from the given
     * specified initial values.
     * This method is only intended for internal use and should
     * not be made public if the other constructors for this class
     * are ever exposed.
     *
     * <p>
     *  从给定的指定初始值构造一个新的{@code Path2D}对象。此方法仅用于内部使用,如果此类的其他构造函数已公开,则不应公开此方法。
     * 
     * 
     * @param rule the winding rule
     * @param initialTypes the size to make the initial array to
     *                     store the path segment types
     * @since 1.6
     */
    /* private protected */
    Path2D(int rule, int initialTypes) {
        setWindingRule(rule);
        this.pointTypes = new byte[initialTypes];
    }

    abstract float[] cloneCoordsFloat(AffineTransform at);
    abstract double[] cloneCoordsDouble(AffineTransform at);
    abstract void append(float x, float y);
    abstract void append(double x, double y);
    abstract Point2D getPoint(int coordindex);
    abstract void needRoom(boolean needMove, int newCoords);
    abstract int pointCrossings(double px, double py);
    abstract int rectCrossings(double rxmin, double rymin,
                               double rxmax, double rymax);

    /**
     * The {@code Float} class defines a geometric path with
     * coordinates stored in single precision floating point.
     *
     * <p>
     *  {@code Float}类定义具有存储在单精度浮点中的坐标的几何路径。
     * 
     * 
     * @since 1.6
     */
    public static class Float extends Path2D implements Serializable {
        transient float floatCoords[];

        /**
         * Constructs a new empty single precision {@code Path2D} object
         * with a default winding rule of {@link #WIND_NON_ZERO}.
         *
         * <p>
         *  使用默认绕组规则{@link #WIND_NON_ZERO}构造新的空单精度{@code Path2D}对象。
         * 
         * 
         * @since 1.6
         */
        public Float() {
            this(WIND_NON_ZERO, INIT_SIZE);
        }

        /**
         * Constructs a new empty single precision {@code Path2D} object
         * with the specified winding rule to control operations that
         * require the interior of the path to be defined.
         *
         * <p>
         *  使用指定的绕组规则构造新的空单精度{@code Path2D}对象,以控制需要定义路径内部的操作。
         * 
         * 
         * @param rule the winding rule
         * @see #WIND_EVEN_ODD
         * @see #WIND_NON_ZERO
         * @since 1.6
         */
        public Float(int rule) {
            this(rule, INIT_SIZE);
        }

        /**
         * Constructs a new empty single precision {@code Path2D} object
         * with the specified winding rule and the specified initial
         * capacity to store path segments.
         * This number is an initial guess as to how many path segments
         * will be added to the path, but the storage is expanded as
         * needed to store whatever path segments are added.
         *
         * <p>
         *  使用指定的绕组规则和指定的初始容量来构造新的空单精度{@code Path2D}对象,以存储路径段。这个数字是关于将多少路径段添加到路径的初始猜测,但是存储器根据需要扩展以存储添加的任何路径段。
         * 
         * 
         * @param rule the winding rule
         * @param initialCapacity the estimate for the number of path segments
         *                        in the path
         * @see #WIND_EVEN_ODD
         * @see #WIND_NON_ZERO
         * @since 1.6
         */
        public Float(int rule, int initialCapacity) {
            super(rule, initialCapacity);
            floatCoords = new float[initialCapacity * 2];
        }

        /**
         * Constructs a new single precision {@code Path2D} object
         * from an arbitrary {@link Shape} object.
         * All of the initial geometry and the winding rule for this path are
         * taken from the specified {@code Shape} object.
         *
         * <p>
         * 从任意的{@link Shape}对象构造一个新的单精度{@code Path2D}对象。所有这个路径的初始几何和缠绕规则都是从指定的{@code Shape}对象中获取的。
         * 
         * 
         * @param s the specified {@code Shape} object
         * @since 1.6
         */
        public Float(Shape s) {
            this(s, null);
        }

        /**
         * Constructs a new single precision {@code Path2D} object
         * from an arbitrary {@link Shape} object, transformed by an
         * {@link AffineTransform} object.
         * All of the initial geometry and the winding rule for this path are
         * taken from the specified {@code Shape} object and transformed
         * by the specified {@code AffineTransform} object.
         *
         * <p>
         *  从任意{@link Shape}对象构造一个新的单精度{@code Path2D}对象,由{@link AffineTransform}对象进行转换。
         * 所有这个路径的初始几何和绕组规则都取自指定的{@code Shape}对象,并由指定的{@code AffineTransform}对象进行转换。
         * 
         * 
         * @param s the specified {@code Shape} object
         * @param at the specified {@code AffineTransform} object
         * @since 1.6
         */
        public Float(Shape s, AffineTransform at) {
            if (s instanceof Path2D) {
                Path2D p2d = (Path2D) s;
                setWindingRule(p2d.windingRule);
                this.numTypes = p2d.numTypes;
                this.pointTypes = Arrays.copyOf(p2d.pointTypes,
                                                p2d.pointTypes.length);
                this.numCoords = p2d.numCoords;
                this.floatCoords = p2d.cloneCoordsFloat(at);
            } else {
                PathIterator pi = s.getPathIterator(at);
                setWindingRule(pi.getWindingRule());
                this.pointTypes = new byte[INIT_SIZE];
                this.floatCoords = new float[INIT_SIZE * 2];
                append(pi, false);
            }
        }

        float[] cloneCoordsFloat(AffineTransform at) {
            float ret[];
            if (at == null) {
                ret = Arrays.copyOf(this.floatCoords, this.floatCoords.length);
            } else {
                ret = new float[floatCoords.length];
                at.transform(floatCoords, 0, ret, 0, numCoords / 2);
            }
            return ret;
        }

        double[] cloneCoordsDouble(AffineTransform at) {
            double ret[] = new double[floatCoords.length];
            if (at == null) {
                for (int i = 0; i < numCoords; i++) {
                    ret[i] = floatCoords[i];
                }
            } else {
                at.transform(floatCoords, 0, ret, 0, numCoords / 2);
            }
            return ret;
        }

        void append(float x, float y) {
            floatCoords[numCoords++] = x;
            floatCoords[numCoords++] = y;
        }

        void append(double x, double y) {
            floatCoords[numCoords++] = (float) x;
            floatCoords[numCoords++] = (float) y;
        }

        Point2D getPoint(int coordindex) {
            return new Point2D.Float(floatCoords[coordindex],
                                     floatCoords[coordindex+1]);
        }

        void needRoom(boolean needMove, int newCoords) {
            if (needMove && numTypes == 0) {
                throw new IllegalPathStateException("missing initial moveto "+
                                                    "in path definition");
            }
            int size = pointTypes.length;
            if (numTypes >= size) {
                int grow = size;
                if (grow > EXPAND_MAX) {
                    grow = EXPAND_MAX;
                } else if (grow == 0) {
                    grow = 1;
                }
                pointTypes = Arrays.copyOf(pointTypes, size+grow);
            }
            size = floatCoords.length;
            if (numCoords + newCoords > size) {
                int grow = size;
                if (grow > EXPAND_MAX * 2) {
                    grow = EXPAND_MAX * 2;
                }
                if (grow < newCoords) {
                    grow = newCoords;
                }
                floatCoords = Arrays.copyOf(floatCoords, size+grow);
            }
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.6
         */
        public final synchronized void moveTo(double x, double y) {
            if (numTypes > 0 && pointTypes[numTypes - 1] == SEG_MOVETO) {
                floatCoords[numCoords-2] = (float) x;
                floatCoords[numCoords-1] = (float) y;
            } else {
                needRoom(false, 2);
                pointTypes[numTypes++] = SEG_MOVETO;
                floatCoords[numCoords++] = (float) x;
                floatCoords[numCoords++] = (float) y;
            }
        }

        /**
         * Adds a point to the path by moving to the specified
         * coordinates specified in float precision.
         * <p>
         * This method provides a single precision variant of
         * the double precision {@code moveTo()} method on the
         * base {@code Path2D} class.
         *
         * <p>
         *  通过移动到以float precision指定的指定坐标将点添加到路径。
         * <p>
         *  此方法在基础{@code Path2D}类上提供了双精度{@code moveTo()}方法的单精度变体。
         * 
         * 
         * @param x the specified X coordinate
         * @param y the specified Y coordinate
         * @see Path2D#moveTo
         * @since 1.6
         */
        public final synchronized void moveTo(float x, float y) {
            if (numTypes > 0 && pointTypes[numTypes - 1] == SEG_MOVETO) {
                floatCoords[numCoords-2] = x;
                floatCoords[numCoords-1] = y;
            } else {
                needRoom(false, 2);
                pointTypes[numTypes++] = SEG_MOVETO;
                floatCoords[numCoords++] = x;
                floatCoords[numCoords++] = y;
            }
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.6
         */
        public final synchronized void lineTo(double x, double y) {
            needRoom(true, 2);
            pointTypes[numTypes++] = SEG_LINETO;
            floatCoords[numCoords++] = (float) x;
            floatCoords[numCoords++] = (float) y;
        }

        /**
         * Adds a point to the path by drawing a straight line from the
         * current coordinates to the new specified coordinates
         * specified in float precision.
         * <p>
         * This method provides a single precision variant of
         * the double precision {@code lineTo()} method on the
         * base {@code Path2D} class.
         *
         * <p>
         *  通过从当前坐标绘制一条直线到以float precision指定的新指定坐标,将一个点添加到路径。
         * <p>
         *  此方法在基础{@code Path2D}类上提供了双精度{@code lineTo()}方法的单精度变体。
         * 
         * 
         * @param x the specified X coordinate
         * @param y the specified Y coordinate
         * @see Path2D#lineTo
         * @since 1.6
         */
        public final synchronized void lineTo(float x, float y) {
            needRoom(true, 2);
            pointTypes[numTypes++] = SEG_LINETO;
            floatCoords[numCoords++] = x;
            floatCoords[numCoords++] = y;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.6
         */
        public final synchronized void quadTo(double x1, double y1,
                                              double x2, double y2)
        {
            needRoom(true, 4);
            pointTypes[numTypes++] = SEG_QUADTO;
            floatCoords[numCoords++] = (float) x1;
            floatCoords[numCoords++] = (float) y1;
            floatCoords[numCoords++] = (float) x2;
            floatCoords[numCoords++] = (float) y2;
        }

        /**
         * Adds a curved segment, defined by two new points, to the path by
         * drawing a Quadratic curve that intersects both the current
         * coordinates and the specified coordinates {@code (x2,y2)},
         * using the specified point {@code (x1,y1)} as a quadratic
         * parametric control point.
         * All coordinates are specified in float precision.
         * <p>
         * This method provides a single precision variant of
         * the double precision {@code quadTo()} method on the
         * base {@code Path2D} class.
         *
         * <p>
         *  使用指定的点{@code(x1,y1),通过绘制与当前坐标和指定坐标{@code(x2,y2)}相交的二次曲线,将由两个新点定义的曲线段添加到路径)}作为二次参数控制点。所有坐标都以浮点精度指定。
         * <p>
         * 此方法提供了基于{@code Path2D}类的双精度{@code quadTo()}方法的单精度变体。
         * 
         * 
         * @param x1 the X coordinate of the quadratic control point
         * @param y1 the Y coordinate of the quadratic control point
         * @param x2 the X coordinate of the final end point
         * @param y2 the Y coordinate of the final end point
         * @see Path2D#quadTo
         * @since 1.6
         */
        public final synchronized void quadTo(float x1, float y1,
                                              float x2, float y2)
        {
            needRoom(true, 4);
            pointTypes[numTypes++] = SEG_QUADTO;
            floatCoords[numCoords++] = x1;
            floatCoords[numCoords++] = y1;
            floatCoords[numCoords++] = x2;
            floatCoords[numCoords++] = y2;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.6
         */
        public final synchronized void curveTo(double x1, double y1,
                                               double x2, double y2,
                                               double x3, double y3)
        {
            needRoom(true, 6);
            pointTypes[numTypes++] = SEG_CUBICTO;
            floatCoords[numCoords++] = (float) x1;
            floatCoords[numCoords++] = (float) y1;
            floatCoords[numCoords++] = (float) x2;
            floatCoords[numCoords++] = (float) y2;
            floatCoords[numCoords++] = (float) x3;
            floatCoords[numCoords++] = (float) y3;
        }

        /**
         * Adds a curved segment, defined by three new points, to the path by
         * drawing a B&eacute;zier curve that intersects both the current
         * coordinates and the specified coordinates {@code (x3,y3)},
         * using the specified points {@code (x1,y1)} and {@code (x2,y2)} as
         * B&eacute;zier control points.
         * All coordinates are specified in float precision.
         * <p>
         * This method provides a single precision variant of
         * the double precision {@code curveTo()} method on the
         * base {@code Path2D} class.
         *
         * <p>
         *  使用指定的点,通过绘制与当前坐标和指定坐标{@code(x3,y3)}相交的B&eacute; zier曲线,将由三个新点定义的曲线段添加到路径中{@code ,y1)}和{@code(x2,y2)}
         * 作为B&eacute; zier控制点。
         * 所有坐标都以浮点精度指定。
         * <p>
         *  此方法在基础{@code Path2D}类上提供双精度{@code curveTo()}方法的单精度变量。
         * 
         * 
         * @param x1 the X coordinate of the first B&eacute;zier control point
         * @param y1 the Y coordinate of the first B&eacute;zier control point
         * @param x2 the X coordinate of the second B&eacute;zier control point
         * @param y2 the Y coordinate of the second B&eacute;zier control point
         * @param x3 the X coordinate of the final end point
         * @param y3 the Y coordinate of the final end point
         * @see Path2D#curveTo
         * @since 1.6
         */
        public final synchronized void curveTo(float x1, float y1,
                                               float x2, float y2,
                                               float x3, float y3)
        {
            needRoom(true, 6);
            pointTypes[numTypes++] = SEG_CUBICTO;
            floatCoords[numCoords++] = x1;
            floatCoords[numCoords++] = y1;
            floatCoords[numCoords++] = x2;
            floatCoords[numCoords++] = y2;
            floatCoords[numCoords++] = x3;
            floatCoords[numCoords++] = y3;
        }

        int pointCrossings(double px, double py) {
            double movx, movy, curx, cury, endx, endy;
            float coords[] = floatCoords;
            curx = movx = coords[0];
            cury = movy = coords[1];
            int crossings = 0;
            int ci = 2;
            for (int i = 1; i < numTypes; i++) {
                switch (pointTypes[i]) {
                case PathIterator.SEG_MOVETO:
                    if (cury != movy) {
                        crossings +=
                            Curve.pointCrossingsForLine(px, py,
                                                        curx, cury,
                                                        movx, movy);
                    }
                    movx = curx = coords[ci++];
                    movy = cury = coords[ci++];
                    break;
                case PathIterator.SEG_LINETO:
                    crossings +=
                        Curve.pointCrossingsForLine(px, py,
                                                    curx, cury,
                                                    endx = coords[ci++],
                                                    endy = coords[ci++]);
                    curx = endx;
                    cury = endy;
                    break;
                case PathIterator.SEG_QUADTO:
                    crossings +=
                        Curve.pointCrossingsForQuad(px, py,
                                                    curx, cury,
                                                    coords[ci++],
                                                    coords[ci++],
                                                    endx = coords[ci++],
                                                    endy = coords[ci++],
                                                    0);
                    curx = endx;
                    cury = endy;
                    break;
            case PathIterator.SEG_CUBICTO:
                    crossings +=
                        Curve.pointCrossingsForCubic(px, py,
                                                     curx, cury,
                                                     coords[ci++],
                                                     coords[ci++],
                                                     coords[ci++],
                                                     coords[ci++],
                                                     endx = coords[ci++],
                                                     endy = coords[ci++],
                                                     0);
                    curx = endx;
                    cury = endy;
                    break;
                case PathIterator.SEG_CLOSE:
                    if (cury != movy) {
                        crossings +=
                            Curve.pointCrossingsForLine(px, py,
                                                        curx, cury,
                                                        movx, movy);
                    }
                    curx = movx;
                    cury = movy;
                    break;
                }
            }
            if (cury != movy) {
                crossings +=
                    Curve.pointCrossingsForLine(px, py,
                                                curx, cury,
                                                movx, movy);
            }
            return crossings;
        }

        int rectCrossings(double rxmin, double rymin,
                          double rxmax, double rymax)
        {
            float coords[] = floatCoords;
            double curx, cury, movx, movy, endx, endy;
            curx = movx = coords[0];
            cury = movy = coords[1];
            int crossings = 0;
            int ci = 2;
            for (int i = 1;
                 crossings != Curve.RECT_INTERSECTS && i < numTypes;
                 i++)
            {
                switch (pointTypes[i]) {
                case PathIterator.SEG_MOVETO:
                    if (curx != movx || cury != movy) {
                        crossings =
                            Curve.rectCrossingsForLine(crossings,
                                                       rxmin, rymin,
                                                       rxmax, rymax,
                                                       curx, cury,
                                                       movx, movy);
                    }
                    // Count should always be a multiple of 2 here.
                    // assert((crossings & 1) != 0);
                    movx = curx = coords[ci++];
                    movy = cury = coords[ci++];
                    break;
                case PathIterator.SEG_LINETO:
                    crossings =
                        Curve.rectCrossingsForLine(crossings,
                                                   rxmin, rymin,
                                                   rxmax, rymax,
                                                   curx, cury,
                                                   endx = coords[ci++],
                                                   endy = coords[ci++]);
                    curx = endx;
                    cury = endy;
                    break;
                case PathIterator.SEG_QUADTO:
                    crossings =
                        Curve.rectCrossingsForQuad(crossings,
                                                   rxmin, rymin,
                                                   rxmax, rymax,
                                                   curx, cury,
                                                   coords[ci++],
                                                   coords[ci++],
                                                   endx = coords[ci++],
                                                   endy = coords[ci++],
                                                   0);
                    curx = endx;
                    cury = endy;
                    break;
                case PathIterator.SEG_CUBICTO:
                    crossings =
                        Curve.rectCrossingsForCubic(crossings,
                                                    rxmin, rymin,
                                                    rxmax, rymax,
                                                    curx, cury,
                                                    coords[ci++],
                                                    coords[ci++],
                                                    coords[ci++],
                                                    coords[ci++],
                                                    endx = coords[ci++],
                                                    endy = coords[ci++],
                                                    0);
                    curx = endx;
                    cury = endy;
                    break;
                case PathIterator.SEG_CLOSE:
                    if (curx != movx || cury != movy) {
                        crossings =
                            Curve.rectCrossingsForLine(crossings,
                                                       rxmin, rymin,
                                                       rxmax, rymax,
                                                       curx, cury,
                                                       movx, movy);
                    }
                    curx = movx;
                    cury = movy;
                    // Count should always be a multiple of 2 here.
                    // assert((crossings & 1) != 0);
                    break;
                }
            }
            if (crossings != Curve.RECT_INTERSECTS &&
                (curx != movx || cury != movy))
            {
                crossings =
                    Curve.rectCrossingsForLine(crossings,
                                               rxmin, rymin,
                                               rxmax, rymax,
                                               curx, cury,
                                               movx, movy);
            }
            // Count should always be a multiple of 2 here.
            // assert((crossings & 1) != 0);
            return crossings;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.6
         */
        public final void append(PathIterator pi, boolean connect) {
            float coords[] = new float[6];
            while (!pi.isDone()) {
                switch (pi.currentSegment(coords)) {
                case SEG_MOVETO:
                    if (!connect || numTypes < 1 || numCoords < 1) {
                        moveTo(coords[0], coords[1]);
                        break;
                    }
                    if (pointTypes[numTypes - 1] != SEG_CLOSE &&
                        floatCoords[numCoords-2] == coords[0] &&
                        floatCoords[numCoords-1] == coords[1])
                    {
                        // Collapse out initial moveto/lineto
                        break;
                    }
                    lineTo(coords[0], coords[1]);
                    break;
                case SEG_LINETO:
                    lineTo(coords[0], coords[1]);
                    break;
                case SEG_QUADTO:
                    quadTo(coords[0], coords[1],
                           coords[2], coords[3]);
                    break;
                case SEG_CUBICTO:
                    curveTo(coords[0], coords[1],
                            coords[2], coords[3],
                            coords[4], coords[5]);
                    break;
                case SEG_CLOSE:
                    closePath();
                    break;
                }
                pi.next();
                connect = false;
            }
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.6
         */
        public final void transform(AffineTransform at) {
            at.transform(floatCoords, 0, floatCoords, 0, numCoords / 2);
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.6
         */
        public final synchronized Rectangle2D getBounds2D() {
            float x1, y1, x2, y2;
            int i = numCoords;
            if (i > 0) {
                y1 = y2 = floatCoords[--i];
                x1 = x2 = floatCoords[--i];
                while (i > 0) {
                    float y = floatCoords[--i];
                    float x = floatCoords[--i];
                    if (x < x1) x1 = x;
                    if (y < y1) y1 = y;
                    if (x > x2) x2 = x;
                    if (y > y2) y2 = y;
                }
            } else {
                x1 = y1 = x2 = y2 = 0.0f;
            }
            return new Rectangle2D.Float(x1, y1, x2 - x1, y2 - y1);
        }

        /**
         * {@inheritDoc}
         * <p>
         * The iterator for this class is not multi-threaded safe,
         * which means that the {@code Path2D} class does not
         * guarantee that modifications to the geometry of this
         * {@code Path2D} object do not affect any iterations of
         * that geometry that are already in process.
         *
         * <p>
         *  {@inheritDoc}
         * <p>
         *  这个类的迭代器不是多线程安全的,这意味着{@code Path2D}类不保证对这个{@code Path2D}对象的几何形状的修改不会影响已经存在的那个几何的任何迭代处理。
         * 
         * 
         * @since 1.6
         */
        public final PathIterator getPathIterator(AffineTransform at) {
            if (at == null) {
                return new CopyIterator(this);
            } else {
                return new TxIterator(this, at);
            }
        }

        /**
         * Creates a new object of the same class as this object.
         *
         * <p>
         *  创建与此对象相同类的新对象。
         * 
         * 
         * @return     a clone of this instance.
         * @exception  OutOfMemoryError    if there is not enough memory.
         * @see        java.lang.Cloneable
         * @since      1.6
         */
        public final Object clone() {
            // Note: It would be nice to have this return Path2D
            // but one of our subclasses (GeneralPath) needs to
            // offer "public Object clone()" for backwards
            // compatibility so we cannot restrict it further.
            // REMIND: Can we do both somehow?
            if (this instanceof GeneralPath) {
                return new GeneralPath(this);
            } else {
                return new Path2D.Float(this);
            }
        }

        /*
         * JDK 1.6 serialVersionUID
         * <p>
         *  JDK 1.6 serialVersionUID
         * 
         */
        private static final long serialVersionUID = 6990832515060788886L;

        /**
         * Writes the default serializable fields to the
         * {@code ObjectOutputStream} followed by an explicit
         * serialization of the path segments stored in this
         * path.
         *
         * <p>
         *  将缺省可序列化字段写入{@code ObjectOutputStream},然后显式序列化存储在此路径中的路径段。
         * 
         * 
         * @serialData
         * <a name="Path2DSerialData"><!-- --></a>
         * <ol>
         * <li>The default serializable fields.
         * There are no default serializable fields as of 1.6.
         * <li>followed by
         * a byte indicating the storage type of the original object
         * as a hint (SERIAL_STORAGE_FLT_ARRAY)
         * <li>followed by
         * an integer indicating the number of path segments to follow (NP)
         * or -1 to indicate an unknown number of path segments follows
         * <li>followed by
         * an integer indicating the total number of coordinates to follow (NC)
         * or -1 to indicate an unknown number of coordinates follows
         * (NC should always be even since coordinates always appear in pairs
         *  representing an x,y pair)
         * <li>followed by
         * a byte indicating the winding rule
         * ({@link #WIND_EVEN_ODD WIND_EVEN_ODD} or
         *  {@link #WIND_NON_ZERO WIND_NON_ZERO})
         * <li>followed by
         * {@code NP} (or unlimited if {@code NP < 0}) sets of values consisting of
         * a single byte indicating a path segment type
         * followed by one or more pairs of float or double
         * values representing the coordinates of the path segment
         * <li>followed by
         * a byte indicating the end of the path (SERIAL_PATH_END).
         * </ol>
         * <p>
         * The following byte value constants are used in the serialized form
         * of {@code Path2D} objects:
         * <table>
         * <tr>
         * <th>Constant Name</th>
         * <th>Byte Value</th>
         * <th>Followed by</th>
         * <th>Description</th>
         * </tr>
         * <tr>
         * <td>{@code SERIAL_STORAGE_FLT_ARRAY}</td>
         * <td>0x30</td>
         * <td></td>
         * <td>A hint that the original {@code Path2D} object stored
         * the coordinates in a Java array of floats.</td>
         * </tr>
         * <tr>
         * <td>{@code SERIAL_STORAGE_DBL_ARRAY}</td>
         * <td>0x31</td>
         * <td></td>
         * <td>A hint that the original {@code Path2D} object stored
         * the coordinates in a Java array of doubles.</td>
         * </tr>
         * <tr>
         * <td>{@code SERIAL_SEG_FLT_MOVETO}</td>
         * <td>0x40</td>
         * <td>2 floats</td>
         * <td>A {@link #moveTo moveTo} path segment follows.</td>
         * </tr>
         * <tr>
         * <td>{@code SERIAL_SEG_FLT_LINETO}</td>
         * <td>0x41</td>
         * <td>2 floats</td>
         * <td>A {@link #lineTo lineTo} path segment follows.</td>
         * </tr>
         * <tr>
         * <td>{@code SERIAL_SEG_FLT_QUADTO}</td>
         * <td>0x42</td>
         * <td>4 floats</td>
         * <td>A {@link #quadTo quadTo} path segment follows.</td>
         * </tr>
         * <tr>
         * <td>{@code SERIAL_SEG_FLT_CUBICTO}</td>
         * <td>0x43</td>
         * <td>6 floats</td>
         * <td>A {@link #curveTo curveTo} path segment follows.</td>
         * </tr>
         * <tr>
         * <td>{@code SERIAL_SEG_DBL_MOVETO}</td>
         * <td>0x50</td>
         * <td>2 doubles</td>
         * <td>A {@link #moveTo moveTo} path segment follows.</td>
         * </tr>
         * <tr>
         * <td>{@code SERIAL_SEG_DBL_LINETO}</td>
         * <td>0x51</td>
         * <td>2 doubles</td>
         * <td>A {@link #lineTo lineTo} path segment follows.</td>
         * </tr>
         * <tr>
         * <td>{@code SERIAL_SEG_DBL_QUADTO}</td>
         * <td>0x52</td>
         * <td>4 doubles</td>
         * <td>A {@link #curveTo curveTo} path segment follows.</td>
         * </tr>
         * <tr>
         * <td>{@code SERIAL_SEG_DBL_CUBICTO}</td>
         * <td>0x53</td>
         * <td>6 doubles</td>
         * <td>A {@link #curveTo curveTo} path segment follows.</td>
         * </tr>
         * <tr>
         * <td>{@code SERIAL_SEG_CLOSE}</td>
         * <td>0x60</td>
         * <td></td>
         * <td>A {@link #closePath closePath} path segment.</td>
         * </tr>
         * <tr>
         * <td>{@code SERIAL_PATH_END}</td>
         * <td>0x61</td>
         * <td></td>
         * <td>There are no more path segments following.</td>
         * </table>
         *
         * @since 1.6
         */
        private void writeObject(java.io.ObjectOutputStream s)
            throws java.io.IOException
        {
            super.writeObject(s, false);
        }

        /**
         * Reads the default serializable fields from the
         * {@code ObjectInputStream} followed by an explicit
         * serialization of the path segments stored in this
         * path.
         * <p>
         * There are no default serializable fields as of 1.6.
         * <p>
         * The serial data for this object is described in the
         * writeObject method.
         *
         * <p>
         *  从{@code ObjectInputStream}读取默认的可序列化字段,然后显式序列化存储在此路径中的路径段。
         * <p>
         *  没有默认可序列化字段为1.6。
         * <p>
         *  此对象的串行数据在writeObject方法中描述。
         * 
         * 
         * @since 1.6
         */
        private void readObject(java.io.ObjectInputStream s)
            throws java.lang.ClassNotFoundException, java.io.IOException
        {
            super.readObject(s, false);
        }

        static class CopyIterator extends Path2D.Iterator {
            float floatCoords[];

            CopyIterator(Path2D.Float p2df) {
                super(p2df);
                this.floatCoords = p2df.floatCoords;
            }

            public int currentSegment(float[] coords) {
                int type = path.pointTypes[typeIdx];
                int numCoords = curvecoords[type];
                if (numCoords > 0) {
                    System.arraycopy(floatCoords, pointIdx,
                                     coords, 0, numCoords);
                }
                return type;
            }

            public int currentSegment(double[] coords) {
                int type = path.pointTypes[typeIdx];
                int numCoords = curvecoords[type];
                if (numCoords > 0) {
                    for (int i = 0; i < numCoords; i++) {
                        coords[i] = floatCoords[pointIdx + i];
                    }
                }
                return type;
            }
        }

        static class TxIterator extends Path2D.Iterator {
            float floatCoords[];
            AffineTransform affine;

            TxIterator(Path2D.Float p2df, AffineTransform at) {
                super(p2df);
                this.floatCoords = p2df.floatCoords;
                this.affine = at;
            }

            public int currentSegment(float[] coords) {
                int type = path.pointTypes[typeIdx];
                int numCoords = curvecoords[type];
                if (numCoords > 0) {
                    affine.transform(floatCoords, pointIdx,
                                     coords, 0, numCoords / 2);
                }
                return type;
            }

            public int currentSegment(double[] coords) {
                int type = path.pointTypes[typeIdx];
                int numCoords = curvecoords[type];
                if (numCoords > 0) {
                    affine.transform(floatCoords, pointIdx,
                                     coords, 0, numCoords / 2);
                }
                return type;
            }
        }

    }

    /**
     * The {@code Double} class defines a geometric path with
     * coordinates stored in double precision floating point.
     *
     * <p>
     * {@code Double}类定义了一个几何路径,坐标存储在双精度浮点中。
     * 
     * 
     * @since 1.6
     */
    public static class Double extends Path2D implements Serializable {
        transient double doubleCoords[];

        /**
         * Constructs a new empty double precision {@code Path2D} object
         * with a default winding rule of {@link #WIND_NON_ZERO}.
         *
         * <p>
         *  使用默认绕组规则{@link #WIND_NON_ZERO}构造新的空双精度{@code Path2D}对象。
         * 
         * 
         * @since 1.6
         */
        public Double() {
            this(WIND_NON_ZERO, INIT_SIZE);
        }

        /**
         * Constructs a new empty double precision {@code Path2D} object
         * with the specified winding rule to control operations that
         * require the interior of the path to be defined.
         *
         * <p>
         *  使用指定的绕组规则构造新的空双精度{@code Path2D}对象,以控制需要定义路径内部的操作。
         * 
         * 
         * @param rule the winding rule
         * @see #WIND_EVEN_ODD
         * @see #WIND_NON_ZERO
         * @since 1.6
         */
        public Double(int rule) {
            this(rule, INIT_SIZE);
        }

        /**
         * Constructs a new empty double precision {@code Path2D} object
         * with the specified winding rule and the specified initial
         * capacity to store path segments.
         * This number is an initial guess as to how many path segments
         * are in the path, but the storage is expanded as needed to store
         * whatever path segments are added to this path.
         *
         * <p>
         *  使用指定的绕组规则和指定的初始容量来构造新的空双精度{@code Path2D}对象,以存储路径段。此数字是关于路径中有多少路径段的初始猜测,但是根据需要扩展存储器以存储添加到该路径的任何路径段。
         * 
         * 
         * @param rule the winding rule
         * @param initialCapacity the estimate for the number of path segments
         *                        in the path
         * @see #WIND_EVEN_ODD
         * @see #WIND_NON_ZERO
         * @since 1.6
         */
        public Double(int rule, int initialCapacity) {
            super(rule, initialCapacity);
            doubleCoords = new double[initialCapacity * 2];
        }

        /**
         * Constructs a new double precision {@code Path2D} object
         * from an arbitrary {@link Shape} object.
         * All of the initial geometry and the winding rule for this path are
         * taken from the specified {@code Shape} object.
         *
         * <p>
         *  从任意的{@link Shape}对象构造一个新的双精度{@code Path2D}对象。所有这个路径的初始几何和缠绕规则都是从指定的{@code Shape}对象中获取的。
         * 
         * 
         * @param s the specified {@code Shape} object
         * @since 1.6
         */
        public Double(Shape s) {
            this(s, null);
        }

        /**
         * Constructs a new double precision {@code Path2D} object
         * from an arbitrary {@link Shape} object, transformed by an
         * {@link AffineTransform} object.
         * All of the initial geometry and the winding rule for this path are
         * taken from the specified {@code Shape} object and transformed
         * by the specified {@code AffineTransform} object.
         *
         * <p>
         *  从任意{@link Shape}对象构造一个新的双精度{@code Path2D}对象,由{@link AffineTransform}对象转换。
         * 所有这个路径的初始几何和绕组规则都取自指定的{@code Shape}对象,并由指定的{@code AffineTransform}对象进行转换。
         * 
         * 
         * @param s the specified {@code Shape} object
         * @param at the specified {@code AffineTransform} object
         * @since 1.6
         */
        public Double(Shape s, AffineTransform at) {
            if (s instanceof Path2D) {
                Path2D p2d = (Path2D) s;
                setWindingRule(p2d.windingRule);
                this.numTypes = p2d.numTypes;
                this.pointTypes = Arrays.copyOf(p2d.pointTypes,
                                                p2d.pointTypes.length);
                this.numCoords = p2d.numCoords;
                this.doubleCoords = p2d.cloneCoordsDouble(at);
            } else {
                PathIterator pi = s.getPathIterator(at);
                setWindingRule(pi.getWindingRule());
                this.pointTypes = new byte[INIT_SIZE];
                this.doubleCoords = new double[INIT_SIZE * 2];
                append(pi, false);
            }
        }

        float[] cloneCoordsFloat(AffineTransform at) {
            float ret[] = new float[doubleCoords.length];
            if (at == null) {
                for (int i = 0; i < numCoords; i++) {
                    ret[i] = (float) doubleCoords[i];
                }
            } else {
                at.transform(doubleCoords, 0, ret, 0, numCoords / 2);
            }
            return ret;
        }

        double[] cloneCoordsDouble(AffineTransform at) {
            double ret[];
            if (at == null) {
                ret = Arrays.copyOf(this.doubleCoords,
                                    this.doubleCoords.length);
            } else {
                ret = new double[doubleCoords.length];
                at.transform(doubleCoords, 0, ret, 0, numCoords / 2);
            }
            return ret;
        }

        void append(float x, float y) {
            doubleCoords[numCoords++] = x;
            doubleCoords[numCoords++] = y;
        }

        void append(double x, double y) {
            doubleCoords[numCoords++] = x;
            doubleCoords[numCoords++] = y;
        }

        Point2D getPoint(int coordindex) {
            return new Point2D.Double(doubleCoords[coordindex],
                                      doubleCoords[coordindex+1]);
        }

        void needRoom(boolean needMove, int newCoords) {
            if (needMove && numTypes == 0) {
                throw new IllegalPathStateException("missing initial moveto "+
                                                    "in path definition");
            }
            int size = pointTypes.length;
            if (numTypes >= size) {
                int grow = size;
                if (grow > EXPAND_MAX) {
                    grow = EXPAND_MAX;
                } else if (grow == 0) {
                    grow = 1;
                }
                pointTypes = Arrays.copyOf(pointTypes, size+grow);
            }
            size = doubleCoords.length;
            if (numCoords + newCoords > size) {
                int grow = size;
                if (grow > EXPAND_MAX * 2) {
                    grow = EXPAND_MAX * 2;
                }
                if (grow < newCoords) {
                    grow = newCoords;
                }
                doubleCoords = Arrays.copyOf(doubleCoords, size+grow);
            }
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.6
         */
        public final synchronized void moveTo(double x, double y) {
            if (numTypes > 0 && pointTypes[numTypes - 1] == SEG_MOVETO) {
                doubleCoords[numCoords-2] = x;
                doubleCoords[numCoords-1] = y;
            } else {
                needRoom(false, 2);
                pointTypes[numTypes++] = SEG_MOVETO;
                doubleCoords[numCoords++] = x;
                doubleCoords[numCoords++] = y;
            }
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.6
         */
        public final synchronized void lineTo(double x, double y) {
            needRoom(true, 2);
            pointTypes[numTypes++] = SEG_LINETO;
            doubleCoords[numCoords++] = x;
            doubleCoords[numCoords++] = y;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.6
         */
        public final synchronized void quadTo(double x1, double y1,
                                              double x2, double y2)
        {
            needRoom(true, 4);
            pointTypes[numTypes++] = SEG_QUADTO;
            doubleCoords[numCoords++] = x1;
            doubleCoords[numCoords++] = y1;
            doubleCoords[numCoords++] = x2;
            doubleCoords[numCoords++] = y2;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.6
         */
        public final synchronized void curveTo(double x1, double y1,
                                               double x2, double y2,
                                               double x3, double y3)
        {
            needRoom(true, 6);
            pointTypes[numTypes++] = SEG_CUBICTO;
            doubleCoords[numCoords++] = x1;
            doubleCoords[numCoords++] = y1;
            doubleCoords[numCoords++] = x2;
            doubleCoords[numCoords++] = y2;
            doubleCoords[numCoords++] = x3;
            doubleCoords[numCoords++] = y3;
        }

        int pointCrossings(double px, double py) {
            double movx, movy, curx, cury, endx, endy;
            double coords[] = doubleCoords;
            curx = movx = coords[0];
            cury = movy = coords[1];
            int crossings = 0;
            int ci = 2;
            for (int i = 1; i < numTypes; i++) {
                switch (pointTypes[i]) {
                case PathIterator.SEG_MOVETO:
                    if (cury != movy) {
                        crossings +=
                            Curve.pointCrossingsForLine(px, py,
                                                        curx, cury,
                                                        movx, movy);
                    }
                    movx = curx = coords[ci++];
                    movy = cury = coords[ci++];
                    break;
                case PathIterator.SEG_LINETO:
                    crossings +=
                        Curve.pointCrossingsForLine(px, py,
                                                    curx, cury,
                                                    endx = coords[ci++],
                                                    endy = coords[ci++]);
                    curx = endx;
                    cury = endy;
                    break;
                case PathIterator.SEG_QUADTO:
                    crossings +=
                        Curve.pointCrossingsForQuad(px, py,
                                                    curx, cury,
                                                    coords[ci++],
                                                    coords[ci++],
                                                    endx = coords[ci++],
                                                    endy = coords[ci++],
                                                    0);
                    curx = endx;
                    cury = endy;
                    break;
            case PathIterator.SEG_CUBICTO:
                    crossings +=
                        Curve.pointCrossingsForCubic(px, py,
                                                     curx, cury,
                                                     coords[ci++],
                                                     coords[ci++],
                                                     coords[ci++],
                                                     coords[ci++],
                                                     endx = coords[ci++],
                                                     endy = coords[ci++],
                                                     0);
                    curx = endx;
                    cury = endy;
                    break;
                case PathIterator.SEG_CLOSE:
                    if (cury != movy) {
                        crossings +=
                            Curve.pointCrossingsForLine(px, py,
                                                        curx, cury,
                                                        movx, movy);
                    }
                    curx = movx;
                    cury = movy;
                    break;
                }
            }
            if (cury != movy) {
                crossings +=
                    Curve.pointCrossingsForLine(px, py,
                                                curx, cury,
                                                movx, movy);
            }
            return crossings;
        }

        int rectCrossings(double rxmin, double rymin,
                          double rxmax, double rymax)
        {
            double coords[] = doubleCoords;
            double curx, cury, movx, movy, endx, endy;
            curx = movx = coords[0];
            cury = movy = coords[1];
            int crossings = 0;
            int ci = 2;
            for (int i = 1;
                 crossings != Curve.RECT_INTERSECTS && i < numTypes;
                 i++)
            {
                switch (pointTypes[i]) {
                case PathIterator.SEG_MOVETO:
                    if (curx != movx || cury != movy) {
                        crossings =
                            Curve.rectCrossingsForLine(crossings,
                                                       rxmin, rymin,
                                                       rxmax, rymax,
                                                       curx, cury,
                                                       movx, movy);
                    }
                    // Count should always be a multiple of 2 here.
                    // assert((crossings & 1) != 0);
                    movx = curx = coords[ci++];
                    movy = cury = coords[ci++];
                    break;
                case PathIterator.SEG_LINETO:
                    endx = coords[ci++];
                    endy = coords[ci++];
                    crossings =
                        Curve.rectCrossingsForLine(crossings,
                                                   rxmin, rymin,
                                                   rxmax, rymax,
                                                   curx, cury,
                                                   endx, endy);
                    curx = endx;
                    cury = endy;
                    break;
                case PathIterator.SEG_QUADTO:
                    crossings =
                        Curve.rectCrossingsForQuad(crossings,
                                                   rxmin, rymin,
                                                   rxmax, rymax,
                                                   curx, cury,
                                                   coords[ci++],
                                                   coords[ci++],
                                                   endx = coords[ci++],
                                                   endy = coords[ci++],
                                                   0);
                    curx = endx;
                    cury = endy;
                    break;
                case PathIterator.SEG_CUBICTO:
                    crossings =
                        Curve.rectCrossingsForCubic(crossings,
                                                    rxmin, rymin,
                                                    rxmax, rymax,
                                                    curx, cury,
                                                    coords[ci++],
                                                    coords[ci++],
                                                    coords[ci++],
                                                    coords[ci++],
                                                    endx = coords[ci++],
                                                    endy = coords[ci++],
                                                    0);
                    curx = endx;
                    cury = endy;
                    break;
                case PathIterator.SEG_CLOSE:
                    if (curx != movx || cury != movy) {
                        crossings =
                            Curve.rectCrossingsForLine(crossings,
                                                       rxmin, rymin,
                                                       rxmax, rymax,
                                                       curx, cury,
                                                       movx, movy);
                    }
                    curx = movx;
                    cury = movy;
                    // Count should always be a multiple of 2 here.
                    // assert((crossings & 1) != 0);
                    break;
                }
            }
            if (crossings != Curve.RECT_INTERSECTS &&
                (curx != movx || cury != movy))
            {
                crossings =
                    Curve.rectCrossingsForLine(crossings,
                                               rxmin, rymin,
                                               rxmax, rymax,
                                               curx, cury,
                                               movx, movy);
            }
            // Count should always be a multiple of 2 here.
            // assert((crossings & 1) != 0);
            return crossings;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.6
         */
        public final void append(PathIterator pi, boolean connect) {
            double coords[] = new double[6];
            while (!pi.isDone()) {
                switch (pi.currentSegment(coords)) {
                case SEG_MOVETO:
                    if (!connect || numTypes < 1 || numCoords < 1) {
                        moveTo(coords[0], coords[1]);
                        break;
                    }
                    if (pointTypes[numTypes - 1] != SEG_CLOSE &&
                        doubleCoords[numCoords-2] == coords[0] &&
                        doubleCoords[numCoords-1] == coords[1])
                    {
                        // Collapse out initial moveto/lineto
                        break;
                    }
                    lineTo(coords[0], coords[1]);
                    break;
                case SEG_LINETO:
                    lineTo(coords[0], coords[1]);
                    break;
                case SEG_QUADTO:
                    quadTo(coords[0], coords[1],
                           coords[2], coords[3]);
                    break;
                case SEG_CUBICTO:
                    curveTo(coords[0], coords[1],
                            coords[2], coords[3],
                            coords[4], coords[5]);
                    break;
                case SEG_CLOSE:
                    closePath();
                    break;
                }
                pi.next();
                connect = false;
            }
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.6
         */
        public final void transform(AffineTransform at) {
            at.transform(doubleCoords, 0, doubleCoords, 0, numCoords / 2);
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.6
         */
        public final synchronized Rectangle2D getBounds2D() {
            double x1, y1, x2, y2;
            int i = numCoords;
            if (i > 0) {
                y1 = y2 = doubleCoords[--i];
                x1 = x2 = doubleCoords[--i];
                while (i > 0) {
                    double y = doubleCoords[--i];
                    double x = doubleCoords[--i];
                    if (x < x1) x1 = x;
                    if (y < y1) y1 = y;
                    if (x > x2) x2 = x;
                    if (y > y2) y2 = y;
                }
            } else {
                x1 = y1 = x2 = y2 = 0.0;
            }
            return new Rectangle2D.Double(x1, y1, x2 - x1, y2 - y1);
        }

        /**
         * {@inheritDoc}
         * <p>
         * The iterator for this class is not multi-threaded safe,
         * which means that the {@code Path2D} class does not
         * guarantee that modifications to the geometry of this
         * {@code Path2D} object do not affect any iterations of
         * that geometry that are already in process.
         *
         * <p>
         *  {@inheritDoc}
         * <p>
         * 这个类的迭代器不是多线程安全的,这意味着{@code Path2D}类不保证对这个{@code Path2D}对象的几何形状的修改不会影响已经存在的那个几何的任何迭代处理。
         * 
         * 
         * @param at an {@code AffineTransform}
         * @return a new {@code PathIterator} that iterates along the boundary
         *         of this {@code Shape} and provides access to the geometry
         *         of this {@code Shape}'s outline
         * @since 1.6
         */
        public final PathIterator getPathIterator(AffineTransform at) {
            if (at == null) {
                return new CopyIterator(this);
            } else {
                return new TxIterator(this, at);
            }
        }

        /**
         * Creates a new object of the same class as this object.
         *
         * <p>
         *  创建与此对象相同类的新对象。
         * 
         * 
         * @return     a clone of this instance.
         * @exception  OutOfMemoryError    if there is not enough memory.
         * @see        java.lang.Cloneable
         * @since      1.6
         */
        public final Object clone() {
            // Note: It would be nice to have this return Path2D
            // but one of our subclasses (GeneralPath) needs to
            // offer "public Object clone()" for backwards
            // compatibility so we cannot restrict it further.
            // REMIND: Can we do both somehow?
            return new Path2D.Double(this);
        }

        /*
         * JDK 1.6 serialVersionUID
         * <p>
         *  JDK 1.6 serialVersionUID
         * 
         */
        private static final long serialVersionUID = 1826762518450014216L;

        /**
         * Writes the default serializable fields to the
         * {@code ObjectOutputStream} followed by an explicit
         * serialization of the path segments stored in this
         * path.
         *
         * <p>
         *  将缺省可序列化字段写入{@code ObjectOutputStream},然后显式序列化存储在此路径中的路径段。
         * 
         * 
         * @serialData
         * <a name="Path2DSerialData"><!-- --></a>
         * <ol>
         * <li>The default serializable fields.
         * There are no default serializable fields as of 1.6.
         * <li>followed by
         * a byte indicating the storage type of the original object
         * as a hint (SERIAL_STORAGE_DBL_ARRAY)
         * <li>followed by
         * an integer indicating the number of path segments to follow (NP)
         * or -1 to indicate an unknown number of path segments follows
         * <li>followed by
         * an integer indicating the total number of coordinates to follow (NC)
         * or -1 to indicate an unknown number of coordinates follows
         * (NC should always be even since coordinates always appear in pairs
         *  representing an x,y pair)
         * <li>followed by
         * a byte indicating the winding rule
         * ({@link #WIND_EVEN_ODD WIND_EVEN_ODD} or
         *  {@link #WIND_NON_ZERO WIND_NON_ZERO})
         * <li>followed by
         * {@code NP} (or unlimited if {@code NP < 0}) sets of values consisting of
         * a single byte indicating a path segment type
         * followed by one or more pairs of float or double
         * values representing the coordinates of the path segment
         * <li>followed by
         * a byte indicating the end of the path (SERIAL_PATH_END).
         * </ol>
         * <p>
         * The following byte value constants are used in the serialized form
         * of {@code Path2D} objects:
         * <table>
         * <tr>
         * <th>Constant Name</th>
         * <th>Byte Value</th>
         * <th>Followed by</th>
         * <th>Description</th>
         * </tr>
         * <tr>
         * <td>{@code SERIAL_STORAGE_FLT_ARRAY}</td>
         * <td>0x30</td>
         * <td></td>
         * <td>A hint that the original {@code Path2D} object stored
         * the coordinates in a Java array of floats.</td>
         * </tr>
         * <tr>
         * <td>{@code SERIAL_STORAGE_DBL_ARRAY}</td>
         * <td>0x31</td>
         * <td></td>
         * <td>A hint that the original {@code Path2D} object stored
         * the coordinates in a Java array of doubles.</td>
         * </tr>
         * <tr>
         * <td>{@code SERIAL_SEG_FLT_MOVETO}</td>
         * <td>0x40</td>
         * <td>2 floats</td>
         * <td>A {@link #moveTo moveTo} path segment follows.</td>
         * </tr>
         * <tr>
         * <td>{@code SERIAL_SEG_FLT_LINETO}</td>
         * <td>0x41</td>
         * <td>2 floats</td>
         * <td>A {@link #lineTo lineTo} path segment follows.</td>
         * </tr>
         * <tr>
         * <td>{@code SERIAL_SEG_FLT_QUADTO}</td>
         * <td>0x42</td>
         * <td>4 floats</td>
         * <td>A {@link #quadTo quadTo} path segment follows.</td>
         * </tr>
         * <tr>
         * <td>{@code SERIAL_SEG_FLT_CUBICTO}</td>
         * <td>0x43</td>
         * <td>6 floats</td>
         * <td>A {@link #curveTo curveTo} path segment follows.</td>
         * </tr>
         * <tr>
         * <td>{@code SERIAL_SEG_DBL_MOVETO}</td>
         * <td>0x50</td>
         * <td>2 doubles</td>
         * <td>A {@link #moveTo moveTo} path segment follows.</td>
         * </tr>
         * <tr>
         * <td>{@code SERIAL_SEG_DBL_LINETO}</td>
         * <td>0x51</td>
         * <td>2 doubles</td>
         * <td>A {@link #lineTo lineTo} path segment follows.</td>
         * </tr>
         * <tr>
         * <td>{@code SERIAL_SEG_DBL_QUADTO}</td>
         * <td>0x52</td>
         * <td>4 doubles</td>
         * <td>A {@link #curveTo curveTo} path segment follows.</td>
         * </tr>
         * <tr>
         * <td>{@code SERIAL_SEG_DBL_CUBICTO}</td>
         * <td>0x53</td>
         * <td>6 doubles</td>
         * <td>A {@link #curveTo curveTo} path segment follows.</td>
         * </tr>
         * <tr>
         * <td>{@code SERIAL_SEG_CLOSE}</td>
         * <td>0x60</td>
         * <td></td>
         * <td>A {@link #closePath closePath} path segment.</td>
         * </tr>
         * <tr>
         * <td>{@code SERIAL_PATH_END}</td>
         * <td>0x61</td>
         * <td></td>
         * <td>There are no more path segments following.</td>
         * </table>
         *
         * @since 1.6
         */
        private void writeObject(java.io.ObjectOutputStream s)
            throws java.io.IOException
        {
            super.writeObject(s, true);
        }

        /**
         * Reads the default serializable fields from the
         * {@code ObjectInputStream} followed by an explicit
         * serialization of the path segments stored in this
         * path.
         * <p>
         * There are no default serializable fields as of 1.6.
         * <p>
         * The serial data for this object is described in the
         * writeObject method.
         *
         * <p>
         *  从{@code ObjectInputStream}读取默认的可序列化字段,然后显式序列化存储在此路径中的路径段。
         * <p>
         *  没有默认可序列化字段为1.6。
         * <p>
         *  此对象的串行数据在writeObject方法中描述。
         * 
         * 
         * @since 1.6
         */
        private void readObject(java.io.ObjectInputStream s)
            throws java.lang.ClassNotFoundException, java.io.IOException
        {
            super.readObject(s, true);
        }

        static class CopyIterator extends Path2D.Iterator {
            double doubleCoords[];

            CopyIterator(Path2D.Double p2dd) {
                super(p2dd);
                this.doubleCoords = p2dd.doubleCoords;
            }

            public int currentSegment(float[] coords) {
                int type = path.pointTypes[typeIdx];
                int numCoords = curvecoords[type];
                if (numCoords > 0) {
                    for (int i = 0; i < numCoords; i++) {
                        coords[i] = (float) doubleCoords[pointIdx + i];
                    }
                }
                return type;
            }

            public int currentSegment(double[] coords) {
                int type = path.pointTypes[typeIdx];
                int numCoords = curvecoords[type];
                if (numCoords > 0) {
                    System.arraycopy(doubleCoords, pointIdx,
                                     coords, 0, numCoords);
                }
                return type;
            }
        }

        static class TxIterator extends Path2D.Iterator {
            double doubleCoords[];
            AffineTransform affine;

            TxIterator(Path2D.Double p2dd, AffineTransform at) {
                super(p2dd);
                this.doubleCoords = p2dd.doubleCoords;
                this.affine = at;
            }

            public int currentSegment(float[] coords) {
                int type = path.pointTypes[typeIdx];
                int numCoords = curvecoords[type];
                if (numCoords > 0) {
                    affine.transform(doubleCoords, pointIdx,
                                     coords, 0, numCoords / 2);
                }
                return type;
            }

            public int currentSegment(double[] coords) {
                int type = path.pointTypes[typeIdx];
                int numCoords = curvecoords[type];
                if (numCoords > 0) {
                    affine.transform(doubleCoords, pointIdx,
                                     coords, 0, numCoords / 2);
                }
                return type;
            }
        }
    }

    /**
     * Adds a point to the path by moving to the specified
     * coordinates specified in double precision.
     *
     * <p>
     *  通过移动到以双精度指定的指定坐标将点添加到路径。
     * 
     * 
     * @param x the specified X coordinate
     * @param y the specified Y coordinate
     * @since 1.6
     */
    public abstract void moveTo(double x, double y);

    /**
     * Adds a point to the path by drawing a straight line from the
     * current coordinates to the new specified coordinates
     * specified in double precision.
     *
     * <p>
     *  通过从当前坐标绘制一条直线到以双精度指定的新指定坐标,将一个点添加到路径。
     * 
     * 
     * @param x the specified X coordinate
     * @param y the specified Y coordinate
     * @since 1.6
     */
    public abstract void lineTo(double x, double y);

    /**
     * Adds a curved segment, defined by two new points, to the path by
     * drawing a Quadratic curve that intersects both the current
     * coordinates and the specified coordinates {@code (x2,y2)},
     * using the specified point {@code (x1,y1)} as a quadratic
     * parametric control point.
     * All coordinates are specified in double precision.
     *
     * <p>
     *  使用指定的点{@code(x1,y1),通过绘制与当前坐标和指定坐标{@code(x2,y2)}相交的二次曲线,将由两个新点定义的曲线段添加到路径)}作为二次参数控制点。所有坐标以双精度指定。
     * 
     * 
     * @param x1 the X coordinate of the quadratic control point
     * @param y1 the Y coordinate of the quadratic control point
     * @param x2 the X coordinate of the final end point
     * @param y2 the Y coordinate of the final end point
     * @since 1.6
     */
    public abstract void quadTo(double x1, double y1,
                                double x2, double y2);

    /**
     * Adds a curved segment, defined by three new points, to the path by
     * drawing a B&eacute;zier curve that intersects both the current
     * coordinates and the specified coordinates {@code (x3,y3)},
     * using the specified points {@code (x1,y1)} and {@code (x2,y2)} as
     * B&eacute;zier control points.
     * All coordinates are specified in double precision.
     *
     * <p>
     * 使用指定的点,通过绘制与当前坐标和指定坐标{@code(x3,y3)}相交的B&eacute; zier曲线,将由三个新点定义的曲线段添加到路径中{@code ,y1)}和{@code(x2,y2)}作
     * 为B&eacute; zier控制点。
     * 所有坐标以双精度指定。
     * 
     * 
     * @param x1 the X coordinate of the first B&eacute;zier control point
     * @param y1 the Y coordinate of the first B&eacute;zier control point
     * @param x2 the X coordinate of the second B&eacute;zier control point
     * @param y2 the Y coordinate of the second B&eacute;zier control point
     * @param x3 the X coordinate of the final end point
     * @param y3 the Y coordinate of the final end point
     * @since 1.6
     */
    public abstract void curveTo(double x1, double y1,
                                 double x2, double y2,
                                 double x3, double y3);

    /**
     * Closes the current subpath by drawing a straight line back to
     * the coordinates of the last {@code moveTo}.  If the path is already
     * closed then this method has no effect.
     *
     * <p>
     *  通过绘制一条直线返回到最后一个{@code moveTo}的坐标来关闭当前子路径。如果路径已经关闭,则此方法不起作用。
     * 
     * 
     * @since 1.6
     */
    public final synchronized void closePath() {
        if (numTypes == 0 || pointTypes[numTypes - 1] != SEG_CLOSE) {
            needRoom(true, 0);
            pointTypes[numTypes++] = SEG_CLOSE;
        }
    }

    /**
     * Appends the geometry of the specified {@code Shape} object to the
     * path, possibly connecting the new geometry to the existing path
     * segments with a line segment.
     * If the {@code connect} parameter is {@code true} and the
     * path is not empty then any initial {@code moveTo} in the
     * geometry of the appended {@code Shape}
     * is turned into a {@code lineTo} segment.
     * If the destination coordinates of such a connecting {@code lineTo}
     * segment match the ending coordinates of a currently open
     * subpath then the segment is omitted as superfluous.
     * The winding rule of the specified {@code Shape} is ignored
     * and the appended geometry is governed by the winding
     * rule specified for this path.
     *
     * <p>
     *  将指定的{@code Shape}对象的几何体追加到路径,可能将新的几何体连接到具有线段的现有路径段。
     * 如果{@code connect}参数是{@code true},并且路径不为空,那么附加的{@code Shape}的几何中的任何初始{@code moveTo}将变为{@code lineTo}段。
     *  将指定的{@code Shape}对象的几何体追加到路径,可能将新的几何体连接到具有线段的现有路径段。
     * 如果这种连接的{@code lineTo}段的目的地坐标与当前打开的子路径的结束坐标匹配,则该段被省略为多余的。
     * 将忽略指定的{@code Shape}的缠绕规则,并且附加的几何结构由为此路径指定的缠绕规则控制。
     * 
     * 
     * @param s the {@code Shape} whose geometry is appended
     *          to this path
     * @param connect a boolean to control whether or not to turn an initial
     *                {@code moveTo} segment into a {@code lineTo} segment
     *                to connect the new geometry to the existing path
     * @since 1.6
     */
    public final void append(Shape s, boolean connect) {
        append(s.getPathIterator(null), connect);
    }

    /**
     * Appends the geometry of the specified
     * {@link PathIterator} object
     * to the path, possibly connecting the new geometry to the existing
     * path segments with a line segment.
     * If the {@code connect} parameter is {@code true} and the
     * path is not empty then any initial {@code moveTo} in the
     * geometry of the appended {@code Shape} is turned into a
     * {@code lineTo} segment.
     * If the destination coordinates of such a connecting {@code lineTo}
     * segment match the ending coordinates of a currently open
     * subpath then the segment is omitted as superfluous.
     * The winding rule of the specified {@code Shape} is ignored
     * and the appended geometry is governed by the winding
     * rule specified for this path.
     *
     * <p>
     * 将指定的{@link PathIterator}对象的几何体追加到路径,可能将新的几何体连接到具有线段的现有路径段。
     * 如果{@code connect}参数是{@code true},并且路径不为空,那么附加的{@code Shape}的几何中的任何初始{@code moveTo}将变为{@code lineTo}段。
     * 将指定的{@link PathIterator}对象的几何体追加到路径,可能将新的几何体连接到具有线段的现有路径段。
     * 如果这种连接的{@code lineTo}段的目的地坐标与当前打开的子路径的结束坐标匹配,则该段被省略为多余的。
     * 将忽略指定的{@code Shape}的缠绕规则,并且附加的几何结构由为此路径指定的缠绕规则控制。
     * 
     * 
     * @param pi the {@code PathIterator} whose geometry is appended to
     *           this path
     * @param connect a boolean to control whether or not to turn an initial
     *                {@code moveTo} segment into a {@code lineTo} segment
     *                to connect the new geometry to the existing path
     * @since 1.6
     */
    public abstract void append(PathIterator pi, boolean connect);

    /**
     * Returns the fill style winding rule.
     *
     * <p>
     *  返回填充样式绕组规则。
     * 
     * 
     * @return an integer representing the current winding rule.
     * @see #WIND_EVEN_ODD
     * @see #WIND_NON_ZERO
     * @see #setWindingRule
     * @since 1.6
     */
    public final synchronized int getWindingRule() {
        return windingRule;
    }

    /**
     * Sets the winding rule for this path to the specified value.
     *
     * <p>
     *  将此路径的卷绕规则设置为指定值。
     * 
     * 
     * @param rule an integer representing the specified
     *             winding rule
     * @exception IllegalArgumentException if
     *          {@code rule} is not either
     *          {@link #WIND_EVEN_ODD} or
     *          {@link #WIND_NON_ZERO}
     * @see #getWindingRule
     * @since 1.6
     */
    public final void setWindingRule(int rule) {
        if (rule != WIND_EVEN_ODD && rule != WIND_NON_ZERO) {
            throw new IllegalArgumentException("winding rule must be "+
                                               "WIND_EVEN_ODD or "+
                                               "WIND_NON_ZERO");
        }
        windingRule = rule;
    }

    /**
     * Returns the coordinates most recently added to the end of the path
     * as a {@link Point2D} object.
     *
     * <p>
     *  返回最近添加到路径末端的坐标作为{@link Point2D}对象。
     * 
     * 
     * @return a {@code Point2D} object containing the ending coordinates of
     *         the path or {@code null} if there are no points in the path.
     * @since 1.6
     */
    public final synchronized Point2D getCurrentPoint() {
        int index = numCoords;
        if (numTypes < 1 || index < 1) {
            return null;
        }
        if (pointTypes[numTypes - 1] == SEG_CLOSE) {
        loop:
            for (int i = numTypes - 2; i > 0; i--) {
                switch (pointTypes[i]) {
                case SEG_MOVETO:
                    break loop;
                case SEG_LINETO:
                    index -= 2;
                    break;
                case SEG_QUADTO:
                    index -= 4;
                    break;
                case SEG_CUBICTO:
                    index -= 6;
                    break;
                case SEG_CLOSE:
                    break;
                }
            }
        }
        return getPoint(index - 2);
    }

    /**
     * Resets the path to empty.  The append position is set back to the
     * beginning of the path and all coordinates and point types are
     * forgotten.
     *
     * <p>
     *  将路径重置为空。追加位置被设置回路径的开始,并且所有坐标和点类型被忘记。
     * 
     * 
     * @since 1.6
     */
    public final synchronized void reset() {
        numTypes = numCoords = 0;
    }

    /**
     * Transforms the geometry of this path using the specified
     * {@link AffineTransform}.
     * The geometry is transformed in place, which permanently changes the
     * boundary defined by this object.
     *
     * <p>
     *  使用指定的{@link AffineTransform}转换此路径的几何。几何被转换到位,永久地改变由该对象定义的边界。
     * 
     * 
     * @param at the {@code AffineTransform} used to transform the area
     * @since 1.6
     */
    public abstract void transform(AffineTransform at);

    /**
     * Returns a new {@code Shape} representing a transformed version
     * of this {@code Path2D}.
     * Note that the exact type and coordinate precision of the return
     * value is not specified for this method.
     * The method will return a Shape that contains no less precision
     * for the transformed geometry than this {@code Path2D} currently
     * maintains, but it may contain no more precision either.
     * If the tradeoff of precision vs. storage size in the result is
     * important then the convenience constructors in the
     * {@link Path2D.Float#Path2D.Float(Shape, AffineTransform) Path2D.Float}
     * and
     * {@link Path2D.Double#Path2D.Double(Shape, AffineTransform) Path2D.Double}
     * subclasses should be used to make the choice explicit.
     *
     * <p>
     * 返回一个新的{@code Shape},表示此{@code Path2D}的转换版本。请注意,没有为此方法指定返回值的精确类型和坐标精度。
     * 该方法将返回一个Shape,其中包含的转换几何的精度不低于{@code Path2D}当前维护的精度,但它也可能不包含更多的精度。
     * 如果结果中的精度与存储大小的权衡很重要,那么{@link Path2D.Float#Path2D.Float(Shape,AffineTransform)Path2D.Float}和{@link Path2D.Double#Path2D.Double (Shape,AffineTransform)Path2D.Double}
     * 子类应该用来使选择显式。
     * 该方法将返回一个Shape,其中包含的转换几何的精度不低于{@code Path2D}当前维护的精度,但它也可能不包含更多的精度。
     * 
     * 
     * @param at the {@code AffineTransform} used to transform a
     *           new {@code Shape}.
     * @return a new {@code Shape}, transformed with the specified
     *         {@code AffineTransform}.
     * @since 1.6
     */
    public final synchronized Shape createTransformedShape(AffineTransform at) {
        Path2D p2d = (Path2D) clone();
        if (at != null) {
            p2d.transform(at);
        }
        return p2d;
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.6
     */
    public final Rectangle getBounds() {
        return getBounds2D().getBounds();
    }

    /**
     * Tests if the specified coordinates are inside the closed
     * boundary of the specified {@link PathIterator}.
     * <p>
     * This method provides a basic facility for implementors of
     * the {@link Shape} interface to implement support for the
     * {@link Shape#contains(double, double)} method.
     *
     * <p>
     *  测试指定的坐标是否在指定的{@link PathIterator}的闭合边界内。
     * <p>
     *  此方法为{@link Shape}接口的实现者提供了一个基本的功能,以实现对{@link Shape#contains(double,double)}方法的支持。
     * 
     * 
     * @param pi the specified {@code PathIterator}
     * @param x the specified X coordinate
     * @param y the specified Y coordinate
     * @return {@code true} if the specified coordinates are inside the
     *         specified {@code PathIterator}; {@code false} otherwise
     * @since 1.6
     */
    public static boolean contains(PathIterator pi, double x, double y) {
        if (x * 0.0 + y * 0.0 == 0.0) {
            /* N * 0.0 is 0.0 only if N is finite.
             * Here we know that both x and y are finite.
             * <p>
             *  这里我们知道x和y都是有限的。
             * 
             */
            int mask = (pi.getWindingRule() == WIND_NON_ZERO ? -1 : 1);
            int cross = Curve.pointCrossingsForPath(pi, x, y);
            return ((cross & mask) != 0);
        } else {
            /* Either x or y was infinite or NaN.
             * A NaN always produces a negative response to any test
             * and Infinity values cannot be "inside" any path so
             * they should return false as well.
             * <p>
             *  NaN总是对任何测试产生一个负响应,无限值不能在任何路径"内部",所以他们也应该返回假。
             * 
             */
            return false;
        }
    }

    /**
     * Tests if the specified {@link Point2D} is inside the closed
     * boundary of the specified {@link PathIterator}.
     * <p>
     * This method provides a basic facility for implementors of
     * the {@link Shape} interface to implement support for the
     * {@link Shape#contains(Point2D)} method.
     *
     * <p>
     *  测试指定的{@link Point2D}是否在指定的{@link PathIterator}的封闭边界内。
     * <p>
     *  此方法为{@link Shape}接口的实现者提供了一个基本的功能,以实现对{@link Shape#contains(Point2D)}方法的支持。
     * 
     * 
     * @param pi the specified {@code PathIterator}
     * @param p the specified {@code Point2D}
     * @return {@code true} if the specified coordinates are inside the
     *         specified {@code PathIterator}; {@code false} otherwise
     * @since 1.6
     */
    public static boolean contains(PathIterator pi, Point2D p) {
        return contains(pi, p.getX(), p.getY());
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.6
     */
    public final boolean contains(double x, double y) {
        if (x * 0.0 + y * 0.0 == 0.0) {
            /* N * 0.0 is 0.0 only if N is finite.
             * Here we know that both x and y are finite.
             * <p>
             *  这里我们知道x和y都是有限的。
             * 
             */
            if (numTypes < 2) {
                return false;
            }
            int mask = (windingRule == WIND_NON_ZERO ? -1 : 1);
            return ((pointCrossings(x, y) & mask) != 0);
        } else {
            /* Either x or y was infinite or NaN.
             * A NaN always produces a negative response to any test
             * and Infinity values cannot be "inside" any path so
             * they should return false as well.
             * <p>
             * NaN总是对任何测试产生一个负响应,无限值不能在任何路径"内部",所以他们也应该返回假。
             * 
             */
            return false;
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.6
     */
    public final boolean contains(Point2D p) {
        return contains(p.getX(), p.getY());
    }

    /**
     * Tests if the specified rectangular area is entirely inside the
     * closed boundary of the specified {@link PathIterator}.
     * <p>
     * This method provides a basic facility for implementors of
     * the {@link Shape} interface to implement support for the
     * {@link Shape#contains(double, double, double, double)} method.
     * <p>
     * This method object may conservatively return false in
     * cases where the specified rectangular area intersects a
     * segment of the path, but that segment does not represent a
     * boundary between the interior and exterior of the path.
     * Such segments could lie entirely within the interior of the
     * path if they are part of a path with a {@link #WIND_NON_ZERO}
     * winding rule or if the segments are retraced in the reverse
     * direction such that the two sets of segments cancel each
     * other out without any exterior area falling between them.
     * To determine whether segments represent true boundaries of
     * the interior of the path would require extensive calculations
     * involving all of the segments of the path and the winding
     * rule and are thus beyond the scope of this implementation.
     *
     * <p>
     *  测试指定的矩形区域是否完全在指定的{@link PathIterator}的封闭边界内。
     * <p>
     *  此方法为{@link Shape}接口的实现者提供了一个基本的工具,以实现对{@link Shape#contains(double,double,double,double)}方法的支持。
     * <p>
     *  在指定的矩形区域与路径的段相交但该段不表示路径的内部和外部之间的边界的情况下,该方法对象可以保守地返回假。
     * 如果这些段是具有{@link #WIND_NON_ZERO}绕组规则的路径的一部分,或者如果段沿相反方向回退,则这样的段可以完全位于路径的内部,使得两组段彼此抵消任何外部区域落在他们之间。
     * 为了确定段是否表示路径内部的真实边界,将需要涉及路径的所有段和绕组规则的广泛计算,因此超出了该实现的范围。
     * 
     * 
     * @param pi the specified {@code PathIterator}
     * @param x the specified X coordinate
     * @param y the specified Y coordinate
     * @param w the width of the specified rectangular area
     * @param h the height of the specified rectangular area
     * @return {@code true} if the specified {@code PathIterator} contains
     *         the specified rectangular area; {@code false} otherwise.
     * @since 1.6
     */
    public static boolean contains(PathIterator pi,
                                   double x, double y, double w, double h)
    {
        if (java.lang.Double.isNaN(x+w) || java.lang.Double.isNaN(y+h)) {
            /* [xy]+[wh] is NaN if any of those values are NaN,
             * or if adding the two together would produce NaN
             * by virtue of adding opposing Infinte values.
             * Since we need to add them below, their sum must
             * not be NaN.
             * We return false because NaN always produces a
             * negative response to tests
             * <p>
             *  或者如果将这两个加在一起将通过添加相反的Infinte值来产生NaN。因为我们需要在下面添加它们,它们的总和不能是NaN。我们返回false,因为NaN总是对测试产生一个负响应
             * 
             */
            return false;
        }
        if (w <= 0 || h <= 0) {
            return false;
        }
        int mask = (pi.getWindingRule() == WIND_NON_ZERO ? -1 : 2);
        int crossings = Curve.rectCrossingsForPath(pi, x, y, x+w, y+h);
        return (crossings != Curve.RECT_INTERSECTS &&
                (crossings & mask) != 0);
    }

    /**
     * Tests if the specified {@link Rectangle2D} is entirely inside the
     * closed boundary of the specified {@link PathIterator}.
     * <p>
     * This method provides a basic facility for implementors of
     * the {@link Shape} interface to implement support for the
     * {@link Shape#contains(Rectangle2D)} method.
     * <p>
     * This method object may conservatively return false in
     * cases where the specified rectangular area intersects a
     * segment of the path, but that segment does not represent a
     * boundary between the interior and exterior of the path.
     * Such segments could lie entirely within the interior of the
     * path if they are part of a path with a {@link #WIND_NON_ZERO}
     * winding rule or if the segments are retraced in the reverse
     * direction such that the two sets of segments cancel each
     * other out without any exterior area falling between them.
     * To determine whether segments represent true boundaries of
     * the interior of the path would require extensive calculations
     * involving all of the segments of the path and the winding
     * rule and are thus beyond the scope of this implementation.
     *
     * <p>
     * 测试指定的{@link Rectangle2D}是否完全在指定的{@link PathIterator}的封闭边界内。
     * <p>
     *  此方法为{@link Shape}接口的实现者提供了一个基本的功能,以实现对{@link Shape#contains(Rectangle2D)}方法的支持。
     * <p>
     *  在指定的矩形区域与路径的段相交但该段不表示路径的内部和外部之间的边界的情况下,该方法对象可以保守地返回假。
     * 如果这些段是具有{@link #WIND_NON_ZERO}绕组规则的路径的一部分,或者如果段沿相反方向回退,则这样的段可以完全位于路径的内部,使得两组段彼此抵消任何外部区域落在他们之间。
     * 为了确定段是否表示路径内部的真实边界,将需要涉及路径的所有段和绕组规则的广泛计算,因此超出了该实现的范围。
     * 
     * 
     * @param pi the specified {@code PathIterator}
     * @param r a specified {@code Rectangle2D}
     * @return {@code true} if the specified {@code PathIterator} contains
     *         the specified {@code Rectangle2D}; {@code false} otherwise.
     * @since 1.6
     */
    public static boolean contains(PathIterator pi, Rectangle2D r) {
        return contains(pi, r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    /**
     * {@inheritDoc}
     * <p>
     * This method object may conservatively return false in
     * cases where the specified rectangular area intersects a
     * segment of the path, but that segment does not represent a
     * boundary between the interior and exterior of the path.
     * Such segments could lie entirely within the interior of the
     * path if they are part of a path with a {@link #WIND_NON_ZERO}
     * winding rule or if the segments are retraced in the reverse
     * direction such that the two sets of segments cancel each
     * other out without any exterior area falling between them.
     * To determine whether segments represent true boundaries of
     * the interior of the path would require extensive calculations
     * involving all of the segments of the path and the winding
     * rule and are thus beyond the scope of this implementation.
     *
     * <p>
     *  {@inheritDoc}
     * <p>
     * 在指定的矩形区域与路径的段相交但该段不表示路径的内部和外部之间的边界的情况下,该方法对象可以保守地返回假。
     * 如果这些段是具有{@link #WIND_NON_ZERO}绕组规则的路径的一部分,或者如果段沿相反方向回退,则这样的段可以完全位于路径的内部,使得两组段彼此抵消任何外部区域落在他们之间。
     * 为了确定段是否表示路径内部的真实边界,将需要涉及路径的所有段和绕组规则的广泛计算,因此超出了该实现的范围。
     * 
     * 
     * @since 1.6
     */
    public final boolean contains(double x, double y, double w, double h) {
        if (java.lang.Double.isNaN(x+w) || java.lang.Double.isNaN(y+h)) {
            /* [xy]+[wh] is NaN if any of those values are NaN,
             * or if adding the two together would produce NaN
             * by virtue of adding opposing Infinte values.
             * Since we need to add them below, their sum must
             * not be NaN.
             * We return false because NaN always produces a
             * negative response to tests
             * <p>
             *  或者如果将这两个加在一起将通过添加相反的Infinte值来产生NaN。因为我们需要在下面添加它们,它们的总和不能是NaN。我们返回false,因为NaN总是对测试产生一个负响应
             * 
             */
            return false;
        }
        if (w <= 0 || h <= 0) {
            return false;
        }
        int mask = (windingRule == WIND_NON_ZERO ? -1 : 2);
        int crossings = rectCrossings(x, y, x+w, y+h);
        return (crossings != Curve.RECT_INTERSECTS &&
                (crossings & mask) != 0);
    }

    /**
     * {@inheritDoc}
     * <p>
     * This method object may conservatively return false in
     * cases where the specified rectangular area intersects a
     * segment of the path, but that segment does not represent a
     * boundary between the interior and exterior of the path.
     * Such segments could lie entirely within the interior of the
     * path if they are part of a path with a {@link #WIND_NON_ZERO}
     * winding rule or if the segments are retraced in the reverse
     * direction such that the two sets of segments cancel each
     * other out without any exterior area falling between them.
     * To determine whether segments represent true boundaries of
     * the interior of the path would require extensive calculations
     * involving all of the segments of the path and the winding
     * rule and are thus beyond the scope of this implementation.
     *
     * <p>
     *  {@inheritDoc}
     * <p>
     * 在指定的矩形区域与路径的段相交但该段不表示路径的内部和外部之间的边界的情况下,该方法对象可以保守地返回假。
     * 如果这些段是具有{@link #WIND_NON_ZERO}绕组规则的路径的一部分,或者如果段沿相反方向回退,则这样的段可以完全位于路径的内部,使得两组段彼此抵消任何外部区域落在他们之间。
     * 为了确定段是否表示路径内部的真实边界,将需要涉及路径的所有段和绕组规则的广泛计算,因此超出了该实现的范围。
     * 
     * 
     * @since 1.6
     */
    public final boolean contains(Rectangle2D r) {
        return contains(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    /**
     * Tests if the interior of the specified {@link PathIterator}
     * intersects the interior of a specified set of rectangular
     * coordinates.
     * <p>
     * This method provides a basic facility for implementors of
     * the {@link Shape} interface to implement support for the
     * {@link Shape#intersects(double, double, double, double)} method.
     * <p>
     * This method object may conservatively return true in
     * cases where the specified rectangular area intersects a
     * segment of the path, but that segment does not represent a
     * boundary between the interior and exterior of the path.
     * Such a case may occur if some set of segments of the
     * path are retraced in the reverse direction such that the
     * two sets of segments cancel each other out without any
     * interior area between them.
     * To determine whether segments represent true boundaries of
     * the interior of the path would require extensive calculations
     * involving all of the segments of the path and the winding
     * rule and are thus beyond the scope of this implementation.
     *
     * <p>
     *  测试指定的{@link PathIterator}的内部是否与指定的一组直角坐标的内部相交。
     * <p>
     *  此方法为{@link Shape}接口的实现者提供了一种基本的功能,以实现对{@link Shape#intersects(double,double,double,double)}方法的支持。
     * <p>
     * 在指定的矩形区域与路径的段相交但该段不表示路径的内部和外部之间的边界的情况下,该方法对象可以保守地返回真。
     * 如果路径的一些段的集合在相反方向上缩回,使得两组段彼此抵消而在它们之间没有任何内部区域,则可能发生这种情况。
     * 为了确定段是否表示路径内部的真实边界,将需要涉及路径的所有段和绕组规则的广泛计算,因此超出了该实现的范围。
     * 
     * 
     * @param pi the specified {@code PathIterator}
     * @param x the specified X coordinate
     * @param y the specified Y coordinate
     * @param w the width of the specified rectangular coordinates
     * @param h the height of the specified rectangular coordinates
     * @return {@code true} if the specified {@code PathIterator} and
     *         the interior of the specified set of rectangular
     *         coordinates intersect each other; {@code false} otherwise.
     * @since 1.6
     */
    public static boolean intersects(PathIterator pi,
                                     double x, double y, double w, double h)
    {
        if (java.lang.Double.isNaN(x+w) || java.lang.Double.isNaN(y+h)) {
            /* [xy]+[wh] is NaN if any of those values are NaN,
             * or if adding the two together would produce NaN
             * by virtue of adding opposing Infinte values.
             * Since we need to add them below, their sum must
             * not be NaN.
             * We return false because NaN always produces a
             * negative response to tests
             * <p>
             *  或者如果将这两个加在一起将通过添加相反的Infinte值来产生NaN。因为我们需要在下面添加它们,它们的总和不能是NaN。我们返回false,因为NaN总是对测试产生一个负响应
             * 
             */
            return false;
        }
        if (w <= 0 || h <= 0) {
            return false;
        }
        int mask = (pi.getWindingRule() == WIND_NON_ZERO ? -1 : 2);
        int crossings = Curve.rectCrossingsForPath(pi, x, y, x+w, y+h);
        return (crossings == Curve.RECT_INTERSECTS ||
                (crossings & mask) != 0);
    }

    /**
     * Tests if the interior of the specified {@link PathIterator}
     * intersects the interior of a specified {@link Rectangle2D}.
     * <p>
     * This method provides a basic facility for implementors of
     * the {@link Shape} interface to implement support for the
     * {@link Shape#intersects(Rectangle2D)} method.
     * <p>
     * This method object may conservatively return true in
     * cases where the specified rectangular area intersects a
     * segment of the path, but that segment does not represent a
     * boundary between the interior and exterior of the path.
     * Such a case may occur if some set of segments of the
     * path are retraced in the reverse direction such that the
     * two sets of segments cancel each other out without any
     * interior area between them.
     * To determine whether segments represent true boundaries of
     * the interior of the path would require extensive calculations
     * involving all of the segments of the path and the winding
     * rule and are thus beyond the scope of this implementation.
     *
     * <p>
     *  测试指定的{@link PathIterator}的内部是否与指定的{@link Rectangle2D}的内部相交。
     * <p>
     *  此方法为{@link Shape}接口的实现者提供了一个基本的功能,以实现对{@link Shape#intersects(Rectangle2D)}方法的支持。
     * <p>
     * 在指定的矩形区域与路径的段相交但该段不表示路径的内部和外部之间的边界的情况下,该方法对象可以保守地返回真。
     * 如果路径的一些段的集合在相反方向上缩回,使得两组段彼此抵消而在它们之间没有任何内部区域,则可能发生这种情况。
     * 为了确定段是否表示路径内部的真实边界,将需要涉及路径的所有段和绕组规则的广泛计算,因此超出了该实现的范围。
     * 
     * 
     * @param pi the specified {@code PathIterator}
     * @param r the specified {@code Rectangle2D}
     * @return {@code true} if the specified {@code PathIterator} and
     *         the interior of the specified {@code Rectangle2D}
     *         intersect each other; {@code false} otherwise.
     * @since 1.6
     */
    public static boolean intersects(PathIterator pi, Rectangle2D r) {
        return intersects(pi, r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    /**
     * {@inheritDoc}
     * <p>
     * This method object may conservatively return true in
     * cases where the specified rectangular area intersects a
     * segment of the path, but that segment does not represent a
     * boundary between the interior and exterior of the path.
     * Such a case may occur if some set of segments of the
     * path are retraced in the reverse direction such that the
     * two sets of segments cancel each other out without any
     * interior area between them.
     * To determine whether segments represent true boundaries of
     * the interior of the path would require extensive calculations
     * involving all of the segments of the path and the winding
     * rule and are thus beyond the scope of this implementation.
     *
     * <p>
     *  {@inheritDoc}
     * <p>
     *  在指定的矩形区域与路径的段相交但该段不表示路径的内部和外部之间的边界的情况下,该方法对象可以保守地返回真。
     * 如果路径的一些段的集合在相反方向上缩回,使得两组段彼此抵消而在它们之间没有任何内部区域,则可能发生这种情况。
     * 为了确定段是否表示路径内部的真实边界,将需要涉及路径的所有段和绕组规则的广泛计算,因此超出了该实现的范围。
     * 
     * 
     * @since 1.6
     */
    public final boolean intersects(double x, double y, double w, double h) {
        if (java.lang.Double.isNaN(x+w) || java.lang.Double.isNaN(y+h)) {
            /* [xy]+[wh] is NaN if any of those values are NaN,
             * or if adding the two together would produce NaN
             * by virtue of adding opposing Infinte values.
             * Since we need to add them below, their sum must
             * not be NaN.
             * We return false because NaN always produces a
             * negative response to tests
             * <p>
             * 或者如果将这两个加在一起将通过添加相反的Infinte值来产生NaN。因为我们需要在下面添加它们,它们的总和不能是NaN。我们返回false,因为NaN总是对测试产生一个负响应
             * 
             */
            return false;
        }
        if (w <= 0 || h <= 0) {
            return false;
        }
        int mask = (windingRule == WIND_NON_ZERO ? -1 : 2);
        int crossings = rectCrossings(x, y, x+w, y+h);
        return (crossings == Curve.RECT_INTERSECTS ||
                (crossings & mask) != 0);
    }

    /**
     * {@inheritDoc}
     * <p>
     * This method object may conservatively return true in
     * cases where the specified rectangular area intersects a
     * segment of the path, but that segment does not represent a
     * boundary between the interior and exterior of the path.
     * Such a case may occur if some set of segments of the
     * path are retraced in the reverse direction such that the
     * two sets of segments cancel each other out without any
     * interior area between them.
     * To determine whether segments represent true boundaries of
     * the interior of the path would require extensive calculations
     * involving all of the segments of the path and the winding
     * rule and are thus beyond the scope of this implementation.
     *
     * <p>
     *  {@inheritDoc}
     * <p>
     *  在指定的矩形区域与路径的段相交但该段不表示路径的内部和外部之间的边界的情况下,该方法对象可以保守地返回真。
     * 如果路径的一些段的集合在相反方向上缩回,使得两组段彼此抵消而在它们之间没有任何内部区域,则可能发生这种情况。
     * 为了确定段是否表示路径内部的真实边界,将需要涉及路径的所有段和绕组规则的广泛计算,因此超出了该实现的范围。
     * 
     * 
     * @since 1.6
     */
    public final boolean intersects(Rectangle2D r) {
        return intersects(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    /**
     * {@inheritDoc}
     * <p>
     * The iterator for this class is not multi-threaded safe,
     * which means that this {@code Path2D} class does not
     * guarantee that modifications to the geometry of this
     * {@code Path2D} object do not affect any iterations of
     * that geometry that are already in process.
     *
     * <p>
     *  {@inheritDoc}
     * <p>
     *  这个类的迭代器不是多线程安全的,这意味着这个{@code Path2D}类不保证对这个{@code Path2D}对象的几何形状的修改不影响该几何的任何迭代已经在处理。
     * 
     * 
     * @since 1.6
     */
    public final PathIterator getPathIterator(AffineTransform at,
                                              double flatness)
    {
        return new FlatteningPathIterator(getPathIterator(at), flatness);
    }

    /**
     * Creates a new object of the same class as this object.
     *
     * <p>
     *  创建与此对象相同类的新对象。
     * 
     * 
     * @return     a clone of this instance.
     * @exception  OutOfMemoryError            if there is not enough memory.
     * @see        java.lang.Cloneable
     * @since      1.6
     */
    public abstract Object clone();
        // Note: It would be nice to have this return Path2D
        // but one of our subclasses (GeneralPath) needs to
        // offer "public Object clone()" for backwards
        // compatibility so we cannot restrict it further.
        // REMIND: Can we do both somehow?

    /*
     * Support fields and methods for serializing the subclasses.
     * <p>
     *  支持用于序列化子类的字段和方法。
     */
    private static final byte SERIAL_STORAGE_FLT_ARRAY = 0x30;
    private static final byte SERIAL_STORAGE_DBL_ARRAY = 0x31;

    private static final byte SERIAL_SEG_FLT_MOVETO    = 0x40;
    private static final byte SERIAL_SEG_FLT_LINETO    = 0x41;
    private static final byte SERIAL_SEG_FLT_QUADTO    = 0x42;
    private static final byte SERIAL_SEG_FLT_CUBICTO   = 0x43;

    private static final byte SERIAL_SEG_DBL_MOVETO    = 0x50;
    private static final byte SERIAL_SEG_DBL_LINETO    = 0x51;
    private static final byte SERIAL_SEG_DBL_QUADTO    = 0x52;
    private static final byte SERIAL_SEG_DBL_CUBICTO   = 0x53;

    private static final byte SERIAL_SEG_CLOSE         = 0x60;
    private static final byte SERIAL_PATH_END          = 0x61;

    final void writeObject(java.io.ObjectOutputStream s, boolean isdbl)
        throws java.io.IOException
    {
        s.defaultWriteObject();

        float fCoords[];
        double dCoords[];

        if (isdbl) {
            dCoords = ((Path2D.Double) this).doubleCoords;
            fCoords = null;
        } else {
            fCoords = ((Path2D.Float) this).floatCoords;
            dCoords = null;
        }

        int numTypes = this.numTypes;

        s.writeByte(isdbl
                    ? SERIAL_STORAGE_DBL_ARRAY
                    : SERIAL_STORAGE_FLT_ARRAY);
        s.writeInt(numTypes);
        s.writeInt(numCoords);
        s.writeByte((byte) windingRule);

        int cindex = 0;
        for (int i = 0; i < numTypes; i++) {
            int npoints;
            byte serialtype;
            switch (pointTypes[i]) {
            case SEG_MOVETO:
                npoints = 1;
                serialtype = (isdbl
                              ? SERIAL_SEG_DBL_MOVETO
                              : SERIAL_SEG_FLT_MOVETO);
                break;
            case SEG_LINETO:
                npoints = 1;
                serialtype = (isdbl
                              ? SERIAL_SEG_DBL_LINETO
                              : SERIAL_SEG_FLT_LINETO);
                break;
            case SEG_QUADTO:
                npoints = 2;
                serialtype = (isdbl
                              ? SERIAL_SEG_DBL_QUADTO
                              : SERIAL_SEG_FLT_QUADTO);
                break;
            case SEG_CUBICTO:
                npoints = 3;
                serialtype = (isdbl
                              ? SERIAL_SEG_DBL_CUBICTO
                              : SERIAL_SEG_FLT_CUBICTO);
                break;
            case SEG_CLOSE:
                npoints = 0;
                serialtype = SERIAL_SEG_CLOSE;
                break;

            default:
                // Should never happen
                throw new InternalError("unrecognized path type");
            }
            s.writeByte(serialtype);
            while (--npoints >= 0) {
                if (isdbl) {
                    s.writeDouble(dCoords[cindex++]);
                    s.writeDouble(dCoords[cindex++]);
                } else {
                    s.writeFloat(fCoords[cindex++]);
                    s.writeFloat(fCoords[cindex++]);
                }
            }
        }
        s.writeByte(SERIAL_PATH_END);
    }

    final void readObject(java.io.ObjectInputStream s, boolean storedbl)
        throws java.lang.ClassNotFoundException, java.io.IOException
    {
        s.defaultReadObject();

        // The subclass calls this method with the storage type that
        // they want us to use (storedbl) so we ignore the storage
        // method hint from the stream.
        s.readByte();
        int nT = s.readInt();
        int nC = s.readInt();
        try {
            setWindingRule(s.readByte());
        } catch (IllegalArgumentException iae) {
            throw new java.io.InvalidObjectException(iae.getMessage());
        }

        pointTypes = new byte[(nT < 0) ? INIT_SIZE : nT];
        if (nC < 0) {
            nC = INIT_SIZE * 2;
        }
        if (storedbl) {
            ((Path2D.Double) this).doubleCoords = new double[nC];
        } else {
            ((Path2D.Float) this).floatCoords = new float[nC];
        }

    PATHDONE:
        for (int i = 0; nT < 0 || i < nT; i++) {
            boolean isdbl;
            int npoints;
            byte segtype;

            byte serialtype = s.readByte();
            switch (serialtype) {
            case SERIAL_SEG_FLT_MOVETO:
                isdbl = false;
                npoints = 1;
                segtype = SEG_MOVETO;
                break;
            case SERIAL_SEG_FLT_LINETO:
                isdbl = false;
                npoints = 1;
                segtype = SEG_LINETO;
                break;
            case SERIAL_SEG_FLT_QUADTO:
                isdbl = false;
                npoints = 2;
                segtype = SEG_QUADTO;
                break;
            case SERIAL_SEG_FLT_CUBICTO:
                isdbl = false;
                npoints = 3;
                segtype = SEG_CUBICTO;
                break;

            case SERIAL_SEG_DBL_MOVETO:
                isdbl = true;
                npoints = 1;
                segtype = SEG_MOVETO;
                break;
            case SERIAL_SEG_DBL_LINETO:
                isdbl = true;
                npoints = 1;
                segtype = SEG_LINETO;
                break;
            case SERIAL_SEG_DBL_QUADTO:
                isdbl = true;
                npoints = 2;
                segtype = SEG_QUADTO;
                break;
            case SERIAL_SEG_DBL_CUBICTO:
                isdbl = true;
                npoints = 3;
                segtype = SEG_CUBICTO;
                break;

            case SERIAL_SEG_CLOSE:
                isdbl = false;
                npoints = 0;
                segtype = SEG_CLOSE;
                break;

            case SERIAL_PATH_END:
                if (nT < 0) {
                    break PATHDONE;
                }
                throw new StreamCorruptedException("unexpected PATH_END");

            default:
                throw new StreamCorruptedException("unrecognized path type");
            }
            needRoom(segtype != SEG_MOVETO, npoints * 2);
            if (isdbl) {
                while (--npoints >= 0) {
                    append(s.readDouble(), s.readDouble());
                }
            } else {
                while (--npoints >= 0) {
                    append(s.readFloat(), s.readFloat());
                }
            }
            pointTypes[numTypes++] = segtype;
        }
        if (nT >= 0 && s.readByte() != SERIAL_PATH_END) {
            throw new StreamCorruptedException("missing PATH_END");
        }
    }

    static abstract class Iterator implements PathIterator {
        int typeIdx;
        int pointIdx;
        Path2D path;

        static final int curvecoords[] = {2, 2, 4, 6, 0};

        Iterator(Path2D path) {
            this.path = path;
        }

        public int getWindingRule() {
            return path.getWindingRule();
        }

        public boolean isDone() {
            return (typeIdx >= path.numTypes);
        }

        public void next() {
            int type = path.pointTypes[typeIdx++];
            pointIdx += curvecoords[type];
        }
    }
}
