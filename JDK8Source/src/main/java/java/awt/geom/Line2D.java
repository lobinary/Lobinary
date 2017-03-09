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

import java.awt.Shape;
import java.awt.Rectangle;
import java.io.Serializable;

/**
 * This <code>Line2D</code> represents a line segment in {@code (x,y)}
 * coordinate space.  This class, like all of the Java 2D API, uses a
 * default coordinate system called <i>user space</i> in which the y-axis
 * values increase downward and x-axis values increase to the right.  For
 * more information on the user space coordinate system, see the
 * <a href="https://docs.oracle.com/javase/1.3/docs/guide/2d/spec/j2d-intro.fm2.html#61857">
 * Coordinate Systems</a> section of the Java 2D Programmer's Guide.
 * <p>
 * This class is only the abstract superclass for all objects that
 * store a 2D line segment.
 * The actual storage representation of the coordinates is left to
 * the subclass.
 *
 * <p>
 *  此<code> Line2D </code>表示{@code(x,y)}坐标空间中的线段。
 * 该类与所有Java 2D API一样,使用默认坐标系统,称为<i>用户空间</i>,其中y轴值向下增加,x轴值向右增加。有关用户空间坐标系的更多信息,请参阅。
 * <a href="https://docs.oracle.com/javase/1.3/docs/guide/2d/spec/j2d-intro.fm2.html#61857">
 *  Java 2D程序员指南中的"坐标系统</a>"部分。
 * <p>
 *  这个类只是存储2D线段的所有对象的抽象超类。坐标的实际存储表示是留给子类的。
 * 
 * 
 * @author      Jim Graham
 * @since 1.2
 */
public abstract class Line2D implements Shape, Cloneable {

    /**
     * A line segment specified with float coordinates.
     * <p>
     *  用浮点坐标指定的线段。
     * 
     * 
     * @since 1.2
     */
    public static class Float extends Line2D implements Serializable {
        /**
         * The X coordinate of the start point of the line segment.
         * <p>
         *  线段起点的X坐标。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public float x1;

        /**
         * The Y coordinate of the start point of the line segment.
         * <p>
         *  线段起点的Y坐标。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public float y1;

        /**
         * The X coordinate of the end point of the line segment.
         * <p>
         *  线段终点的X坐标。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public float x2;

        /**
         * The Y coordinate of the end point of the line segment.
         * <p>
         *  线段的终点的Y坐标。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public float y2;

        /**
         * Constructs and initializes a Line with coordinates (0, 0) &rarr; (0, 0).
         * <p>
         *  构造和初始化坐标为(0,0)&rarr的线; (0,0)。
         * 
         * 
         * @since 1.2
         */
        public Float() {
        }

        /**
         * Constructs and initializes a Line from the specified coordinates.
         * <p>
         *  根据指定的坐标构造和初始化线。
         * 
         * 
         * @param x1 the X coordinate of the start point
         * @param y1 the Y coordinate of the start point
         * @param x2 the X coordinate of the end point
         * @param y2 the Y coordinate of the end point
         * @since 1.2
         */
        public Float(float x1, float y1, float x2, float y2) {
            setLine(x1, y1, x2, y2);
        }

        /**
         * Constructs and initializes a <code>Line2D</code> from the
         * specified <code>Point2D</code> objects.
         * <p>
         *  从指定的<code> Point2D </code>对象构造并初始化<code> Line2D </code>。
         * 
         * 
         * @param p1 the start <code>Point2D</code> of this line segment
         * @param p2 the end <code>Point2D</code> of this line segment
         * @since 1.2
         */
        public Float(Point2D p1, Point2D p2) {
            setLine(p1, p2);
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public double getX1() {
            return (double) x1;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public double getY1() {
            return (double) y1;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public Point2D getP1() {
            return new Point2D.Float(x1, y1);
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public double getX2() {
            return (double) x2;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public double getY2() {
            return (double) y2;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public Point2D getP2() {
            return new Point2D.Float(x2, y2);
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public void setLine(double x1, double y1, double x2, double y2) {
            this.x1 = (float) x1;
            this.y1 = (float) y1;
            this.x2 = (float) x2;
            this.y2 = (float) y2;
        }

        /**
         * Sets the location of the end points of this <code>Line2D</code>
         * to the specified float coordinates.
         * <p>
         *  将<code> Line2D </code>的结束点的位置设置为指定的浮点坐标。
         * 
         * 
         * @param x1 the X coordinate of the start point
         * @param y1 the Y coordinate of the start point
         * @param x2 the X coordinate of the end point
         * @param y2 the Y coordinate of the end point
         * @since 1.2
         */
        public void setLine(float x1, float y1, float x2, float y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
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
            float x, y, w, h;
            if (x1 < x2) {
                x = x1;
                w = x2 - x1;
            } else {
                x = x2;
                w = x1 - x2;
            }
            if (y1 < y2) {
                y = y1;
                h = y2 - y1;
            } else {
                y = y2;
                h = y1 - y2;
            }
            return new Rectangle2D.Float(x, y, w, h);
        }

        /*
         * JDK 1.6 serialVersionUID
         * <p>
         *  JDK 1.6 serialVersionUID
         * 
         */
        private static final long serialVersionUID = 6161772511649436349L;
    }

    /**
     * A line segment specified with double coordinates.
     * <p>
     * 用双坐标指定的线段。
     * 
     * 
     * @since 1.2
     */
    public static class Double extends Line2D implements Serializable {
        /**
         * The X coordinate of the start point of the line segment.
         * <p>
         *  线段起点的X坐标。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public double x1;

        /**
         * The Y coordinate of the start point of the line segment.
         * <p>
         *  线段起点的Y坐标。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public double y1;

        /**
         * The X coordinate of the end point of the line segment.
         * <p>
         *  线段终点的X坐标。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public double x2;

        /**
         * The Y coordinate of the end point of the line segment.
         * <p>
         *  线段的终点的Y坐标。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public double y2;

        /**
         * Constructs and initializes a Line with coordinates (0, 0) &rarr; (0, 0).
         * <p>
         *  构造和初始化坐标为(0,0)&rarr的线; (0,0)。
         * 
         * 
         * @since 1.2
         */
        public Double() {
        }

        /**
         * Constructs and initializes a <code>Line2D</code> from the
         * specified coordinates.
         * <p>
         *  从指定的坐标构造和初始化<code> Line2D </code>。
         * 
         * 
         * @param x1 the X coordinate of the start point
         * @param y1 the Y coordinate of the start point
         * @param x2 the X coordinate of the end point
         * @param y2 the Y coordinate of the end point
         * @since 1.2
         */
        public Double(double x1, double y1, double x2, double y2) {
            setLine(x1, y1, x2, y2);
        }

        /**
         * Constructs and initializes a <code>Line2D</code> from the
         * specified <code>Point2D</code> objects.
         * <p>
         *  从指定的<code> Point2D </code>对象构造并初始化<code> Line2D </code>。
         * 
         * 
         * @param p1 the start <code>Point2D</code> of this line segment
         * @param p2 the end <code>Point2D</code> of this line segment
         * @since 1.2
         */
        public Double(Point2D p1, Point2D p2) {
            setLine(p1, p2);
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public double getX1() {
            return x1;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public double getY1() {
            return y1;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public Point2D getP1() {
            return new Point2D.Double(x1, y1);
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public double getX2() {
            return x2;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public double getY2() {
            return y2;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public Point2D getP2() {
            return new Point2D.Double(x2, y2);
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public void setLine(double x1, double y1, double x2, double y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
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
            double x, y, w, h;
            if (x1 < x2) {
                x = x1;
                w = x2 - x1;
            } else {
                x = x2;
                w = x1 - x2;
            }
            if (y1 < y2) {
                y = y1;
                h = y2 - y1;
            } else {
                y = y2;
                h = y1 - y2;
            }
            return new Rectangle2D.Double(x, y, w, h);
        }

        /*
         * JDK 1.6 serialVersionUID
         * <p>
         *  JDK 1.6 serialVersionUID
         * 
         */
        private static final long serialVersionUID = 7979627399746467499L;
    }

    /**
     * This is an abstract class that cannot be instantiated directly.
     * Type-specific implementation subclasses are available for
     * instantiation and provide a number of formats for storing
     * the information necessary to satisfy the various accessory
     * methods below.
     *
     * <p>
     *  这是一个不能直接实例化的抽象类。类型特定的实现子类可用于实例化,并且提供用于存储满足下面的各种附件方法所必需的信息的多种格式。
     * 
     * 
     * @see java.awt.geom.Line2D.Float
     * @see java.awt.geom.Line2D.Double
     * @since 1.2
     */
    protected Line2D() {
    }

    /**
     * Returns the X coordinate of the start point in double precision.
     * <p>
     *  以双精度返回起点的X坐标。
     * 
     * 
     * @return the X coordinate of the start point of this
     *         {@code Line2D} object.
     * @since 1.2
     */
    public abstract double getX1();

    /**
     * Returns the Y coordinate of the start point in double precision.
     * <p>
     *  以双精度返回起点的Y坐标。
     * 
     * 
     * @return the Y coordinate of the start point of this
     *         {@code Line2D} object.
     * @since 1.2
     */
    public abstract double getY1();

    /**
     * Returns the start <code>Point2D</code> of this <code>Line2D</code>.
     * <p>
     *  返回此<code> Line2D </code>的开始<code> Point2D </code>。
     * 
     * 
     * @return the start <code>Point2D</code> of this <code>Line2D</code>.
     * @since 1.2
     */
    public abstract Point2D getP1();

    /**
     * Returns the X coordinate of the end point in double precision.
     * <p>
     *  以双精度返回终点的X坐标。
     * 
     * 
     * @return the X coordinate of the end point of this
     *         {@code Line2D} object.
     * @since 1.2
     */
    public abstract double getX2();

    /**
     * Returns the Y coordinate of the end point in double precision.
     * <p>
     *  以双精度返回终点的Y坐标。
     * 
     * 
     * @return the Y coordinate of the end point of this
     *         {@code Line2D} object.
     * @since 1.2
     */
    public abstract double getY2();

    /**
     * Returns the end <code>Point2D</code> of this <code>Line2D</code>.
     * <p>
     *  返回此<code> Line2D </code>的结束<code> Point2D </code>。
     * 
     * 
     * @return the end <code>Point2D</code> of this <code>Line2D</code>.
     * @since 1.2
     */
    public abstract Point2D getP2();

    /**
     * Sets the location of the end points of this <code>Line2D</code> to
     * the specified double coordinates.
     * <p>
     *  将<code> Line2D </code>的结束点的位置设置为指定的双坐标。
     * 
     * 
     * @param x1 the X coordinate of the start point
     * @param y1 the Y coordinate of the start point
     * @param x2 the X coordinate of the end point
     * @param y2 the Y coordinate of the end point
     * @since 1.2
     */
    public abstract void setLine(double x1, double y1, double x2, double y2);

    /**
     * Sets the location of the end points of this <code>Line2D</code> to
     * the specified <code>Point2D</code> coordinates.
     * <p>
     * 将<code> Line2D </code>的终点位置设置为指定的<code> Point2D </code>坐标。
     * 
     * 
     * @param p1 the start <code>Point2D</code> of the line segment
     * @param p2 the end <code>Point2D</code> of the line segment
     * @since 1.2
     */
    public void setLine(Point2D p1, Point2D p2) {
        setLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    /**
     * Sets the location of the end points of this <code>Line2D</code> to
     * the same as those end points of the specified <code>Line2D</code>.
     * <p>
     *  将<code> Line2D </code>的结束点的位置设置为与指定的<code> Line2D </code>的结束点相同。
     * 
     * 
     * @param l the specified <code>Line2D</code>
     * @since 1.2
     */
    public void setLine(Line2D l) {
        setLine(l.getX1(), l.getY1(), l.getX2(), l.getY2());
    }

    /**
     * Returns an indicator of where the specified point
     * {@code (px,py)} lies with respect to the line segment from
     * {@code (x1,y1)} to {@code (x2,y2)}.
     * The return value can be either 1, -1, or 0 and indicates
     * in which direction the specified line must pivot around its
     * first end point, {@code (x1,y1)}, in order to point at the
     * specified point {@code (px,py)}.
     * <p>A return value of 1 indicates that the line segment must
     * turn in the direction that takes the positive X axis towards
     * the negative Y axis.  In the default coordinate system used by
     * Java 2D, this direction is counterclockwise.
     * <p>A return value of -1 indicates that the line segment must
     * turn in the direction that takes the positive X axis towards
     * the positive Y axis.  In the default coordinate system, this
     * direction is clockwise.
     * <p>A return value of 0 indicates that the point lies
     * exactly on the line segment.  Note that an indicator value
     * of 0 is rare and not useful for determining collinearity
     * because of floating point rounding issues.
     * <p>If the point is colinear with the line segment, but
     * not between the end points, then the value will be -1 if the point
     * lies "beyond {@code (x1,y1)}" or 1 if the point lies
     * "beyond {@code (x2,y2)}".
     *
     * <p>
     *  返回指定点{@code(px,py)}相对于从{@code(x1,y1)}到{@code(x2,y2)}的线段的位置的指示符。
     * 返回值可以是1,-1或0,并且指示指定的行必须围绕其第一个结束点{@code(x1,y1)}枢转,以便指向指定的点{@code (px,py)}。
     *  <p>返回值为1表示线段必须沿着正X轴朝负Y轴的方向旋转。在Java 2D使用的默认坐标系中,此方向为逆时针。 <p>返回值为-1表示线段必须沿着正X轴朝向正Y轴的方向旋转。
     * 在默认坐标系中,该方向为顺时针方向。 <p>返回值为0表示点正好位于线段上。注意,指示符值0是罕见的,并且由于浮点舍入问题而不用于确定共线性。
     *  <p>如果点与线段共线,但不在端点之间,则如果点位于"{@code(x1,y1)}"之上,则值为-1,如果点位于"超出{@code(x2,y2)}"。
     * 
     * 
     * @param x1 the X coordinate of the start point of the
     *           specified line segment
     * @param y1 the Y coordinate of the start point of the
     *           specified line segment
     * @param x2 the X coordinate of the end point of the
     *           specified line segment
     * @param y2 the Y coordinate of the end point of the
     *           specified line segment
     * @param px the X coordinate of the specified point to be
     *           compared with the specified line segment
     * @param py the Y coordinate of the specified point to be
     *           compared with the specified line segment
     * @return an integer that indicates the position of the third specified
     *                  coordinates with respect to the line segment formed
     *                  by the first two specified coordinates.
     * @since 1.2
     */
    public static int relativeCCW(double x1, double y1,
                                  double x2, double y2,
                                  double px, double py)
    {
        x2 -= x1;
        y2 -= y1;
        px -= x1;
        py -= y1;
        double ccw = px * y2 - py * x2;
        if (ccw == 0.0) {
            // The point is colinear, classify based on which side of
            // the segment the point falls on.  We can calculate a
            // relative value using the projection of px,py onto the
            // segment - a negative value indicates the point projects
            // outside of the segment in the direction of the particular
            // endpoint used as the origin for the projection.
            ccw = px * x2 + py * y2;
            if (ccw > 0.0) {
                // Reverse the projection to be relative to the original x2,y2
                // x2 and y2 are simply negated.
                // px and py need to have (x2 - x1) or (y2 - y1) subtracted
                //    from them (based on the original values)
                // Since we really want to get a positive answer when the
                //    point is "beyond (x2,y2)", then we want to calculate
                //    the inverse anyway - thus we leave x2 & y2 negated.
                px -= x2;
                py -= y2;
                ccw = px * x2 + py * y2;
                if (ccw < 0.0) {
                    ccw = 0.0;
                }
            }
        }
        return (ccw < 0.0) ? -1 : ((ccw > 0.0) ? 1 : 0);
    }

    /**
     * Returns an indicator of where the specified point
     * {@code (px,py)} lies with respect to this line segment.
     * See the method comments of
     * {@link #relativeCCW(double, double, double, double, double, double)}
     * to interpret the return value.
     * <p>
     * 返回指定点{@code(px,py)}相对于此线段的位置的指示符。
     * 请参阅{@link #relativeCCW(double,double,double,double,double,double)}的方法注释来解释返回值。
     * 
     * 
     * @param px the X coordinate of the specified point
     *           to be compared with this <code>Line2D</code>
     * @param py the Y coordinate of the specified point
     *           to be compared with this <code>Line2D</code>
     * @return an integer that indicates the position of the specified
     *         coordinates with respect to this <code>Line2D</code>
     * @see #relativeCCW(double, double, double, double, double, double)
     * @since 1.2
     */
    public int relativeCCW(double px, double py) {
        return relativeCCW(getX1(), getY1(), getX2(), getY2(), px, py);
    }

    /**
     * Returns an indicator of where the specified <code>Point2D</code>
     * lies with respect to this line segment.
     * See the method comments of
     * {@link #relativeCCW(double, double, double, double, double, double)}
     * to interpret the return value.
     * <p>
     *  返回指定的<code> Point2D </code>相对于此线段的位置的指示符。
     * 请参阅{@link #relativeCCW(double,double,double,double,double,double)}的方法注释来解释返回值。
     * 
     * 
     * @param p the specified <code>Point2D</code> to be compared
     *          with this <code>Line2D</code>
     * @return an integer that indicates the position of the specified
     *         <code>Point2D</code> with respect to this <code>Line2D</code>
     * @see #relativeCCW(double, double, double, double, double, double)
     * @since 1.2
     */
    public int relativeCCW(Point2D p) {
        return relativeCCW(getX1(), getY1(), getX2(), getY2(),
                           p.getX(), p.getY());
    }

    /**
     * Tests if the line segment from {@code (x1,y1)} to
     * {@code (x2,y2)} intersects the line segment from {@code (x3,y3)}
     * to {@code (x4,y4)}.
     *
     * <p>
     *  测试{@code(x1,y1)}到{@code(x2,y2)}的线段是否与{@code(x3,y3)}到{@code(x4,y4)}的线段相交。
     * 
     * 
     * @param x1 the X coordinate of the start point of the first
     *           specified line segment
     * @param y1 the Y coordinate of the start point of the first
     *           specified line segment
     * @param x2 the X coordinate of the end point of the first
     *           specified line segment
     * @param y2 the Y coordinate of the end point of the first
     *           specified line segment
     * @param x3 the X coordinate of the start point of the second
     *           specified line segment
     * @param y3 the Y coordinate of the start point of the second
     *           specified line segment
     * @param x4 the X coordinate of the end point of the second
     *           specified line segment
     * @param y4 the Y coordinate of the end point of the second
     *           specified line segment
     * @return <code>true</code> if the first specified line segment
     *                  and the second specified line segment intersect
     *                  each other; <code>false</code> otherwise.
     * @since 1.2
     */
    public static boolean linesIntersect(double x1, double y1,
                                         double x2, double y2,
                                         double x3, double y3,
                                         double x4, double y4)
    {
        return ((relativeCCW(x1, y1, x2, y2, x3, y3) *
                 relativeCCW(x1, y1, x2, y2, x4, y4) <= 0)
                && (relativeCCW(x3, y3, x4, y4, x1, y1) *
                    relativeCCW(x3, y3, x4, y4, x2, y2) <= 0));
    }

    /**
     * Tests if the line segment from {@code (x1,y1)} to
     * {@code (x2,y2)} intersects this line segment.
     *
     * <p>
     *  测试{@code(x1,y1)}到{@code(x2,y2)}的线段是否与此线段相交。
     * 
     * 
     * @param x1 the X coordinate of the start point of the
     *           specified line segment
     * @param y1 the Y coordinate of the start point of the
     *           specified line segment
     * @param x2 the X coordinate of the end point of the
     *           specified line segment
     * @param y2 the Y coordinate of the end point of the
     *           specified line segment
     * @return {@code <true>} if this line segment and the specified line segment
     *                  intersect each other; <code>false</code> otherwise.
     * @since 1.2
     */
    public boolean intersectsLine(double x1, double y1, double x2, double y2) {
        return linesIntersect(x1, y1, x2, y2,
                              getX1(), getY1(), getX2(), getY2());
    }

    /**
     * Tests if the specified line segment intersects this line segment.
     * <p>
     *  测试指定的线段是否与此线段相交。
     * 
     * 
     * @param l the specified <code>Line2D</code>
     * @return <code>true</code> if this line segment and the specified line
     *                  segment intersect each other;
     *                  <code>false</code> otherwise.
     * @since 1.2
     */
    public boolean intersectsLine(Line2D l) {
        return linesIntersect(l.getX1(), l.getY1(), l.getX2(), l.getY2(),
                              getX1(), getY1(), getX2(), getY2());
    }

    /**
     * Returns the square of the distance from a point to a line segment.
     * The distance measured is the distance between the specified
     * point and the closest point between the specified end points.
     * If the specified point intersects the line segment in between the
     * end points, this method returns 0.0.
     *
     * <p>
     *  返回从点到线段的距离的平方。测量的距离是指定点和指定终点之间的最近点之间的距离。如果指定点与终点之间的线段相交,则此方法返回0.0。
     * 
     * 
     * @param x1 the X coordinate of the start point of the
     *           specified line segment
     * @param y1 the Y coordinate of the start point of the
     *           specified line segment
     * @param x2 the X coordinate of the end point of the
     *           specified line segment
     * @param y2 the Y coordinate of the end point of the
     *           specified line segment
     * @param px the X coordinate of the specified point being
     *           measured against the specified line segment
     * @param py the Y coordinate of the specified point being
     *           measured against the specified line segment
     * @return a double value that is the square of the distance from the
     *                  specified point to the specified line segment.
     * @see #ptLineDistSq(double, double, double, double, double, double)
     * @since 1.2
     */
    public static double ptSegDistSq(double x1, double y1,
                                     double x2, double y2,
                                     double px, double py)
    {
        // Adjust vectors relative to x1,y1
        // x2,y2 becomes relative vector from x1,y1 to end of segment
        x2 -= x1;
        y2 -= y1;
        // px,py becomes relative vector from x1,y1 to test point
        px -= x1;
        py -= y1;
        double dotprod = px * x2 + py * y2;
        double projlenSq;
        if (dotprod <= 0.0) {
            // px,py is on the side of x1,y1 away from x2,y2
            // distance to segment is length of px,py vector
            // "length of its (clipped) projection" is now 0.0
            projlenSq = 0.0;
        } else {
            // switch to backwards vectors relative to x2,y2
            // x2,y2 are already the negative of x1,y1=>x2,y2
            // to get px,py to be the negative of px,py=>x2,y2
            // the dot product of two negated vectors is the same
            // as the dot product of the two normal vectors
            px = x2 - px;
            py = y2 - py;
            dotprod = px * x2 + py * y2;
            if (dotprod <= 0.0) {
                // px,py is on the side of x2,y2 away from x1,y1
                // distance to segment is length of (backwards) px,py vector
                // "length of its (clipped) projection" is now 0.0
                projlenSq = 0.0;
            } else {
                // px,py is between x1,y1 and x2,y2
                // dotprod is the length of the px,py vector
                // projected on the x2,y2=>x1,y1 vector times the
                // length of the x2,y2=>x1,y1 vector
                projlenSq = dotprod * dotprod / (x2 * x2 + y2 * y2);
            }
        }
        // Distance to line is now the length of the relative point
        // vector minus the length of its projection onto the line
        // (which is zero if the projection falls outside the range
        //  of the line segment).
        double lenSq = px * px + py * py - projlenSq;
        if (lenSq < 0) {
            lenSq = 0;
        }
        return lenSq;
    }

    /**
     * Returns the distance from a point to a line segment.
     * The distance measured is the distance between the specified
     * point and the closest point between the specified end points.
     * If the specified point intersects the line segment in between the
     * end points, this method returns 0.0.
     *
     * <p>
     *  返回从点到线段的距离。测量的距离是指定点和指定终点之间的最近点之间的距离。如果指定点与终点之间的线段相交,则此方法返回0.0。
     * 
     * 
     * @param x1 the X coordinate of the start point of the
     *           specified line segment
     * @param y1 the Y coordinate of the start point of the
     *           specified line segment
     * @param x2 the X coordinate of the end point of the
     *           specified line segment
     * @param y2 the Y coordinate of the end point of the
     *           specified line segment
     * @param px the X coordinate of the specified point being
     *           measured against the specified line segment
     * @param py the Y coordinate of the specified point being
     *           measured against the specified line segment
     * @return a double value that is the distance from the specified point
     *                          to the specified line segment.
     * @see #ptLineDist(double, double, double, double, double, double)
     * @since 1.2
     */
    public static double ptSegDist(double x1, double y1,
                                   double x2, double y2,
                                   double px, double py)
    {
        return Math.sqrt(ptSegDistSq(x1, y1, x2, y2, px, py));
    }

    /**
     * Returns the square of the distance from a point to this line segment.
     * The distance measured is the distance between the specified
     * point and the closest point between the current line's end points.
     * If the specified point intersects the line segment in between the
     * end points, this method returns 0.0.
     *
     * <p>
     * 返回从点到此线段的距离的平方。测量的距离是指定点与当前线的端点之间的最近点之间的距离。如果指定点与终点之间的线段相交,则此方法返回0.0。
     * 
     * 
     * @param px the X coordinate of the specified point being
     *           measured against this line segment
     * @param py the Y coordinate of the specified point being
     *           measured against this line segment
     * @return a double value that is the square of the distance from the
     *                  specified point to the current line segment.
     * @see #ptLineDistSq(double, double)
     * @since 1.2
     */
    public double ptSegDistSq(double px, double py) {
        return ptSegDistSq(getX1(), getY1(), getX2(), getY2(), px, py);
    }

    /**
     * Returns the square of the distance from a <code>Point2D</code> to
     * this line segment.
     * The distance measured is the distance between the specified
     * point and the closest point between the current line's end points.
     * If the specified point intersects the line segment in between the
     * end points, this method returns 0.0.
     * <p>
     *  返回从<code> Point2D </code>到此线段的距离的平方。测量的距离是指定点与当前线的端点之间的最近点之间的距离。如果指定点与终点之间的线段相交,则此方法返回0.0。
     * 
     * 
     * @param pt the specified <code>Point2D</code> being measured against
     *           this line segment.
     * @return a double value that is the square of the distance from the
     *                  specified <code>Point2D</code> to the current
     *                  line segment.
     * @see #ptLineDistSq(Point2D)
     * @since 1.2
     */
    public double ptSegDistSq(Point2D pt) {
        return ptSegDistSq(getX1(), getY1(), getX2(), getY2(),
                           pt.getX(), pt.getY());
    }

    /**
     * Returns the distance from a point to this line segment.
     * The distance measured is the distance between the specified
     * point and the closest point between the current line's end points.
     * If the specified point intersects the line segment in between the
     * end points, this method returns 0.0.
     *
     * <p>
     *  返回从点到此线段的距离。测量的距离是指定点与当前线的端点之间的最近点之间的距离。如果指定点与终点之间的线段相交,则此方法返回0.0。
     * 
     * 
     * @param px the X coordinate of the specified point being
     *           measured against this line segment
     * @param py the Y coordinate of the specified point being
     *           measured against this line segment
     * @return a double value that is the distance from the specified
     *                  point to the current line segment.
     * @see #ptLineDist(double, double)
     * @since 1.2
     */
    public double ptSegDist(double px, double py) {
        return ptSegDist(getX1(), getY1(), getX2(), getY2(), px, py);
    }

    /**
     * Returns the distance from a <code>Point2D</code> to this line
     * segment.
     * The distance measured is the distance between the specified
     * point and the closest point between the current line's end points.
     * If the specified point intersects the line segment in between the
     * end points, this method returns 0.0.
     * <p>
     *  返回从<code> Point2D </code>到此线段的距离。测量的距离是指定点与当前线的端点之间的最近点之间的距离。如果指定点与终点之间的线段相交,则此方法返回0.0。
     * 
     * 
     * @param pt the specified <code>Point2D</code> being measured
     *          against this line segment
     * @return a double value that is the distance from the specified
     *                          <code>Point2D</code> to the current line
     *                          segment.
     * @see #ptLineDist(Point2D)
     * @since 1.2
     */
    public double ptSegDist(Point2D pt) {
        return ptSegDist(getX1(), getY1(), getX2(), getY2(),
                         pt.getX(), pt.getY());
    }

    /**
     * Returns the square of the distance from a point to a line.
     * The distance measured is the distance between the specified
     * point and the closest point on the infinitely-extended line
     * defined by the specified coordinates.  If the specified point
     * intersects the line, this method returns 0.0.
     *
     * <p>
     *  返回从点到线的距离的平方。测量的距离是指定点与由指定坐标定义的无限延伸线上的最近点之间的距离。如果指定的点与线相交,则此方法返回0.0。
     * 
     * 
     * @param x1 the X coordinate of the start point of the specified line
     * @param y1 the Y coordinate of the start point of the specified line
     * @param x2 the X coordinate of the end point of the specified line
     * @param y2 the Y coordinate of the end point of the specified line
     * @param px the X coordinate of the specified point being
     *           measured against the specified line
     * @param py the Y coordinate of the specified point being
     *           measured against the specified line
     * @return a double value that is the square of the distance from the
     *                  specified point to the specified line.
     * @see #ptSegDistSq(double, double, double, double, double, double)
     * @since 1.2
     */
    public static double ptLineDistSq(double x1, double y1,
                                      double x2, double y2,
                                      double px, double py)
    {
        // Adjust vectors relative to x1,y1
        // x2,y2 becomes relative vector from x1,y1 to end of segment
        x2 -= x1;
        y2 -= y1;
        // px,py becomes relative vector from x1,y1 to test point
        px -= x1;
        py -= y1;
        double dotprod = px * x2 + py * y2;
        // dotprod is the length of the px,py vector
        // projected on the x1,y1=>x2,y2 vector times the
        // length of the x1,y1=>x2,y2 vector
        double projlenSq = dotprod * dotprod / (x2 * x2 + y2 * y2);
        // Distance to line is now the length of the relative point
        // vector minus the length of its projection onto the line
        double lenSq = px * px + py * py - projlenSq;
        if (lenSq < 0) {
            lenSq = 0;
        }
        return lenSq;
    }

    /**
     * Returns the distance from a point to a line.
     * The distance measured is the distance between the specified
     * point and the closest point on the infinitely-extended line
     * defined by the specified coordinates.  If the specified point
     * intersects the line, this method returns 0.0.
     *
     * <p>
     * 返回从点到线的距离。测量的距离是指定点与由指定坐标定义的无限延伸线上的最近点之间的距离。如果指定的点与线相交,则此方法返回0.0。
     * 
     * 
     * @param x1 the X coordinate of the start point of the specified line
     * @param y1 the Y coordinate of the start point of the specified line
     * @param x2 the X coordinate of the end point of the specified line
     * @param y2 the Y coordinate of the end point of the specified line
     * @param px the X coordinate of the specified point being
     *           measured against the specified line
     * @param py the Y coordinate of the specified point being
     *           measured against the specified line
     * @return a double value that is the distance from the specified
     *                   point to the specified line.
     * @see #ptSegDist(double, double, double, double, double, double)
     * @since 1.2
     */
    public static double ptLineDist(double x1, double y1,
                                    double x2, double y2,
                                    double px, double py)
    {
        return Math.sqrt(ptLineDistSq(x1, y1, x2, y2, px, py));
    }

    /**
     * Returns the square of the distance from a point to this line.
     * The distance measured is the distance between the specified
     * point and the closest point on the infinitely-extended line
     * defined by this <code>Line2D</code>.  If the specified point
     * intersects the line, this method returns 0.0.
     *
     * <p>
     *  返回从点到此线的距离的平方。测量的距离是指定点与由此<code> Line2D </code>定义的无限扩展线上的最近点之间的距离。如果指定的点与线相交,则此方法返回0.0。
     * 
     * 
     * @param px the X coordinate of the specified point being
     *           measured against this line
     * @param py the Y coordinate of the specified point being
     *           measured against this line
     * @return a double value that is the square of the distance from a
     *                  specified point to the current line.
     * @see #ptSegDistSq(double, double)
     * @since 1.2
     */
    public double ptLineDistSq(double px, double py) {
        return ptLineDistSq(getX1(), getY1(), getX2(), getY2(), px, py);
    }

    /**
     * Returns the square of the distance from a specified
     * <code>Point2D</code> to this line.
     * The distance measured is the distance between the specified
     * point and the closest point on the infinitely-extended line
     * defined by this <code>Line2D</code>.  If the specified point
     * intersects the line, this method returns 0.0.
     * <p>
     *  返回从指定<code> Point2D </code>到此行的距离的平方。测量的距离是指定点与由此<code> Line2D </code>定义的无限扩展线上的最近点之间的距离。
     * 如果指定的点与线相交,则此方法返回0.0。
     * 
     * 
     * @param pt the specified <code>Point2D</code> being measured
     *           against this line
     * @return a double value that is the square of the distance from a
     *                  specified <code>Point2D</code> to the current
     *                  line.
     * @see #ptSegDistSq(Point2D)
     * @since 1.2
     */
    public double ptLineDistSq(Point2D pt) {
        return ptLineDistSq(getX1(), getY1(), getX2(), getY2(),
                            pt.getX(), pt.getY());
    }

    /**
     * Returns the distance from a point to this line.
     * The distance measured is the distance between the specified
     * point and the closest point on the infinitely-extended line
     * defined by this <code>Line2D</code>.  If the specified point
     * intersects the line, this method returns 0.0.
     *
     * <p>
     *  返回从点到此线的距离。测量的距离是指定点与由此<code> Line2D </code>定义的无限扩展线上的最近点之间的距离。如果指定的点与线相交,则此方法返回0.0。
     * 
     * 
     * @param px the X coordinate of the specified point being
     *           measured against this line
     * @param py the Y coordinate of the specified point being
     *           measured against this line
     * @return a double value that is the distance from a specified point
     *                  to the current line.
     * @see #ptSegDist(double, double)
     * @since 1.2
     */
    public double ptLineDist(double px, double py) {
        return ptLineDist(getX1(), getY1(), getX2(), getY2(), px, py);
    }

    /**
     * Returns the distance from a <code>Point2D</code> to this line.
     * The distance measured is the distance between the specified
     * point and the closest point on the infinitely-extended line
     * defined by this <code>Line2D</code>.  If the specified point
     * intersects the line, this method returns 0.0.
     * <p>
     *  返回从<code> Point2D </code>到此行的距离。测量的距离是指定点与由此<code> Line2D </code>定义的无限扩展线上的最近点之间的距离。
     * 如果指定的点与线相交,则此方法返回0.0。
     * 
     * 
     * @param pt the specified <code>Point2D</code> being measured
     * @return a double value that is the distance from a specified
     *                  <code>Point2D</code> to the current line.
     * @see #ptSegDist(Point2D)
     * @since 1.2
     */
    public double ptLineDist(Point2D pt) {
        return ptLineDist(getX1(), getY1(), getX2(), getY2(),
                         pt.getX(), pt.getY());
    }

    /**
     * Tests if a specified coordinate is inside the boundary of this
     * <code>Line2D</code>.  This method is required to implement the
     * {@link Shape} interface, but in the case of <code>Line2D</code>
     * objects it always returns <code>false</code> since a line contains
     * no area.
     * <p>
     * 测试指定的坐标是否在此<code> Line2D </code>的边界内。
     * 此方法是实现{@link Shape}接口所必需的,但在<code> Line2D </code>对象的情况下,它总是返回<code> false </code>,因为一行不包含区域。
     * 
     * 
     * @param x the X coordinate of the specified point to be tested
     * @param y the Y coordinate of the specified point to be tested
     * @return <code>false</code> because a <code>Line2D</code> contains
     * no area.
     * @since 1.2
     */
    public boolean contains(double x, double y) {
        return false;
    }

    /**
     * Tests if a given <code>Point2D</code> is inside the boundary of
     * this <code>Line2D</code>.
     * This method is required to implement the {@link Shape} interface,
     * but in the case of <code>Line2D</code> objects it always returns
     * <code>false</code> since a line contains no area.
     * <p>
     *  测试给定的<code> Point2D </code>是否在此<code> Line2D </code>的边界内。
     * 此方法是实现{@link Shape}接口所必需的,但在<code> Line2D </code>对象的情况下,它总是返回<code> false </code>,因为一行不包含区域。
     * 
     * 
     * @param p the specified <code>Point2D</code> to be tested
     * @return <code>false</code> because a <code>Line2D</code> contains
     * no area.
     * @since 1.2
     */
    public boolean contains(Point2D p) {
        return false;
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
        return intersects(new Rectangle2D.Double(x, y, w, h));
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
        return r.intersectsLine(getX1(), getY1(), getX2(), getY2());
    }

    /**
     * Tests if the interior of this <code>Line2D</code> entirely contains
     * the specified set of rectangular coordinates.
     * This method is required to implement the <code>Shape</code> interface,
     * but in the case of <code>Line2D</code> objects it always returns
     * false since a line contains no area.
     * <p>
     *  测试这个<code> Line2D </code>的内部是否完全包含指定的一组直角坐标。
     * 此方法是实现<code> Shape </code>接口所必需的,但在<code> Line2D </code>对象的情况下,它总是返回false,因为一行不包含区域。
     * 
     * 
     * @param x the X coordinate of the upper-left corner of the
     *          specified rectangular area
     * @param y the Y coordinate of the upper-left corner of the
     *          specified rectangular area
     * @param w the width of the specified rectangular area
     * @param h the height of the specified rectangular area
     * @return <code>false</code> because a <code>Line2D</code> contains
     * no area.
     * @since 1.2
     */
    public boolean contains(double x, double y, double w, double h) {
        return false;
    }

    /**
     * Tests if the interior of this <code>Line2D</code> entirely contains
     * the specified <code>Rectangle2D</code>.
     * This method is required to implement the <code>Shape</code> interface,
     * but in the case of <code>Line2D</code> objects it always returns
     * <code>false</code> since a line contains no area.
     * <p>
     *  测试此<code> Line2D </code>的内部是否完全包含指定的<code> Rectangle2D </code>。
     * 此方法是实现<code> Shape </code>接口所必需的,但在<code> Line2D </code>对象的情况下,它总是返回<code> false </code>,因为一行不包含区域。
     * 
     * 
     * @param r the specified <code>Rectangle2D</code> to be tested
     * @return <code>false</code> because a <code>Line2D</code> contains
     * no area.
     * @since 1.2
     */
    public boolean contains(Rectangle2D r) {
        return false;
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.2
     */
    public Rectangle getBounds() {
        return getBounds2D().getBounds();
    }

    /**
     * Returns an iteration object that defines the boundary of this
     * <code>Line2D</code>.
     * The iterator for this class is not multi-threaded safe,
     * which means that this <code>Line2D</code> class does not
     * guarantee that modifications to the geometry of this
     * <code>Line2D</code> object do not affect any iterations of that
     * geometry that are already in process.
     * <p>
     * 返回定义此<code> Line2D </code>边界的迭代对象。
     * 这个类的迭代器不是多线程安全的,这意味着这个<code> Line2D </code>类不保证对这个<code> Line2D </code>对象的几何形状的修改不影响任何迭代该几何已经在处理中。
     * 
     * 
     * @param at the specified {@link AffineTransform}
     * @return a {@link PathIterator} that defines the boundary of this
     *          <code>Line2D</code>.
     * @since 1.2
     */
    public PathIterator getPathIterator(AffineTransform at) {
        return new LineIterator(this, at);
    }

    /**
     * Returns an iteration object that defines the boundary of this
     * flattened <code>Line2D</code>.
     * The iterator for this class is not multi-threaded safe,
     * which means that this <code>Line2D</code> class does not
     * guarantee that modifications to the geometry of this
     * <code>Line2D</code> object do not affect any iterations of that
     * geometry that are already in process.
     * <p>
     *  返回定义此扁平<code> Line2D </code>的边界的迭代对象。
     * 这个类的迭代器不是多线程安全的,这意味着这个<code> Line2D </code>类不保证对这个<code> Line2D </code>对象的几何形状的修改不影响任何迭代该几何已经在处理中。
     * 
     * 
     * @param at the specified <code>AffineTransform</code>
     * @param flatness the maximum amount that the control points for a
     *          given curve can vary from colinear before a subdivided
     *          curve is replaced by a straight line connecting the
     *          end points.  Since a <code>Line2D</code> object is
     *          always flat, this parameter is ignored.
     * @return a <code>PathIterator</code> that defines the boundary of the
     *                  flattened <code>Line2D</code>
     * @since 1.2
     */
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return new LineIterator(this, at);
    }

    /**
     * Creates a new object of the same class as this object.
     *
     * <p>
     * 
     * @return     a clone of this instance.
     * @exception  OutOfMemoryError            if there is not enough memory.
     * @see        java.lang.Cloneable
     * @since      1.2
     */
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
    }
}
