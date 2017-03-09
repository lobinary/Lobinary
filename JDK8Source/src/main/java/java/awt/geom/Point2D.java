/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
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

import java.io.Serializable;

/**
 * The <code>Point2D</code> class defines a point representing a location
 * in {@code (x,y)} coordinate space.
 * <p>
 * This class is only the abstract superclass for all objects that
 * store a 2D coordinate.
 * The actual storage representation of the coordinates is left to
 * the subclass.
 *
 * <p>
 *  <code> Point2D </code>类定义了表示{@code(x,y)}坐标空间中的位置的点。
 * <p>
 *  这个类只是存储2D坐标的所有对象的抽象超类。坐标的实际存储表示是留给子类的。
 * 
 * 
 * @author      Jim Graham
 * @since 1.2
 */
public abstract class Point2D implements Cloneable {

    /**
     * The <code>Float</code> class defines a point specified in float
     * precision.
     * <p>
     *  <code> Float </code>类定义了以float精度指定的点。
     * 
     * 
     * @since 1.2
     */
    public static class Float extends Point2D implements Serializable {
        /**
         * The X coordinate of this <code>Point2D</code>.
         * <p>
         *  此<code> Point2D </code>的X坐标。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public float x;

        /**
         * The Y coordinate of this <code>Point2D</code>.
         * <p>
         *  此<code> Point2D </code>的Y坐标。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public float y;

        /**
         * Constructs and initializes a <code>Point2D</code> with
         * coordinates (0,&nbsp;0).
         * <p>
         *  用坐标(0,&nbsp; 0)构造并初始化一个<code> Point2D </code>。
         * 
         * 
         * @since 1.2
         */
        public Float() {
        }

        /**
         * Constructs and initializes a <code>Point2D</code> with
         * the specified coordinates.
         *
         * <p>
         *  用指定的坐标构造和初始化一个<code> Point2D </code>。
         * 
         * 
         * @param x the X coordinate of the newly
         *          constructed <code>Point2D</code>
         * @param y the Y coordinate of the newly
         *          constructed <code>Point2D</code>
         * @since 1.2
         */
        public Float(float x, float y) {
            this.x = x;
            this.y = y;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public double getX() {
            return (double) x;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public double getY() {
            return (double) y;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public void setLocation(double x, double y) {
            this.x = (float) x;
            this.y = (float) y;
        }

        /**
         * Sets the location of this <code>Point2D</code> to the
         * specified <code>float</code> coordinates.
         *
         * <p>
         *  将<code> Point2D </code>的位置设置为指定的<code> float </code>坐标。
         * 
         * 
         * @param x the new X coordinate of this {@code Point2D}
         * @param y the new Y coordinate of this {@code Point2D}
         * @since 1.2
         */
        public void setLocation(float x, float y) {
            this.x = x;
            this.y = y;
        }

        /**
         * Returns a <code>String</code> that represents the value
         * of this <code>Point2D</code>.
         * <p>
         *  返回表示此<code> Point2D </code>的值的<code> String </code>。
         * 
         * 
         * @return a string representation of this <code>Point2D</code>.
         * @since 1.2
         */
        public String toString() {
            return "Point2D.Float["+x+", "+y+"]";
        }

        /*
         * JDK 1.6 serialVersionUID
         * <p>
         *  JDK 1.6 serialVersionUID
         * 
         */
        private static final long serialVersionUID = -2870572449815403710L;
    }

    /**
     * The <code>Double</code> class defines a point specified in
     * <code>double</code> precision.
     * <p>
     *  <code> Double </code>类定义了<code> double </code>精度中指定的点。
     * 
     * 
     * @since 1.2
     */
    public static class Double extends Point2D implements Serializable {
        /**
         * The X coordinate of this <code>Point2D</code>.
         * <p>
         *  此<code> Point2D </code>的X坐标。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public double x;

        /**
         * The Y coordinate of this <code>Point2D</code>.
         * <p>
         *  此<code> Point2D </code>的Y坐标。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public double y;

        /**
         * Constructs and initializes a <code>Point2D</code> with
         * coordinates (0,&nbsp;0).
         * <p>
         *  用坐标(0,&nbsp; 0)构造并初始化一个<code> Point2D </code>。
         * 
         * 
         * @since 1.2
         */
        public Double() {
        }

        /**
         * Constructs and initializes a <code>Point2D</code> with the
         * specified coordinates.
         *
         * <p>
         *  用指定的坐标构造和初始化一个<code> Point2D </code>。
         * 
         * 
         * @param x the X coordinate of the newly
         *          constructed <code>Point2D</code>
         * @param y the Y coordinate of the newly
         *          constructed <code>Point2D</code>
         * @since 1.2
         */
        public Double(double x, double y) {
            this.x = x;
            this.y = y;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public double getX() {
            return x;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public double getY() {
            return y;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public void setLocation(double x, double y) {
            this.x = x;
            this.y = y;
        }

        /**
         * Returns a <code>String</code> that represents the value
         * of this <code>Point2D</code>.
         * <p>
         *  返回表示此<code> Point2D </code>的值的<code> String </code>。
         * 
         * 
         * @return a string representation of this <code>Point2D</code>.
         * @since 1.2
         */
        public String toString() {
            return "Point2D.Double["+x+", "+y+"]";
        }

        /*
         * JDK 1.6 serialVersionUID
         * <p>
         * JDK 1.6 serialVersionUID
         * 
         */
        private static final long serialVersionUID = 6150783262733311327L;
    }

    /**
     * This is an abstract class that cannot be instantiated directly.
     * Type-specific implementation subclasses are available for
     * instantiation and provide a number of formats for storing
     * the information necessary to satisfy the various accessor
     * methods below.
     *
     * <p>
     *  这是一个不能直接实例化的抽象类。类型特定的实现子类可用于实例化并且提供用于存储满足下面的各种存取器方法所必需的信息的多种格式。
     * 
     * 
     * @see java.awt.geom.Point2D.Float
     * @see java.awt.geom.Point2D.Double
     * @see java.awt.Point
     * @since 1.2
     */
    protected Point2D() {
    }

    /**
     * Returns the X coordinate of this <code>Point2D</code> in
     * <code>double</code> precision.
     * <p>
     *  返回<code> double </code>精度中此<code> Point2D </code>的X坐标。
     * 
     * 
     * @return the X coordinate of this <code>Point2D</code>.
     * @since 1.2
     */
    public abstract double getX();

    /**
     * Returns the Y coordinate of this <code>Point2D</code> in
     * <code>double</code> precision.
     * <p>
     *  返回<code> double </code>精度中<code> Point2D </code>的Y坐标。
     * 
     * 
     * @return the Y coordinate of this <code>Point2D</code>.
     * @since 1.2
     */
    public abstract double getY();

    /**
     * Sets the location of this <code>Point2D</code> to the
     * specified <code>double</code> coordinates.
     *
     * <p>
     *  将<code> Point2D </code>的位置设置为指定的<code> double </code>坐标。
     * 
     * 
     * @param x the new X coordinate of this {@code Point2D}
     * @param y the new Y coordinate of this {@code Point2D}
     * @since 1.2
     */
    public abstract void setLocation(double x, double y);

    /**
     * Sets the location of this <code>Point2D</code> to the same
     * coordinates as the specified <code>Point2D</code> object.
     * <p>
     *  将<code> Point2D </code>的位置设置为与指定的<code> Point2D </code>对象相同的坐标。
     * 
     * 
     * @param p the specified <code>Point2D</code> to which to set
     * this <code>Point2D</code>
     * @since 1.2
     */
    public void setLocation(Point2D p) {
        setLocation(p.getX(), p.getY());
    }

    /**
     * Returns the square of the distance between two points.
     *
     * <p>
     *  返回两点之间的距离的平方。
     * 
     * 
     * @param x1 the X coordinate of the first specified point
     * @param y1 the Y coordinate of the first specified point
     * @param x2 the X coordinate of the second specified point
     * @param y2 the Y coordinate of the second specified point
     * @return the square of the distance between the two
     * sets of specified coordinates.
     * @since 1.2
     */
    public static double distanceSq(double x1, double y1,
                                    double x2, double y2)
    {
        x1 -= x2;
        y1 -= y2;
        return (x1 * x1 + y1 * y1);
    }

    /**
     * Returns the distance between two points.
     *
     * <p>
     *  返回两点之间的距离。
     * 
     * 
     * @param x1 the X coordinate of the first specified point
     * @param y1 the Y coordinate of the first specified point
     * @param x2 the X coordinate of the second specified point
     * @param y2 the Y coordinate of the second specified point
     * @return the distance between the two sets of specified
     * coordinates.
     * @since 1.2
     */
    public static double distance(double x1, double y1,
                                  double x2, double y2)
    {
        x1 -= x2;
        y1 -= y2;
        return Math.sqrt(x1 * x1 + y1 * y1);
    }

    /**
     * Returns the square of the distance from this
     * <code>Point2D</code> to a specified point.
     *
     * <p>
     *  返回从此<code> Point2D </code>到指定点的距离的平方。
     * 
     * 
     * @param px the X coordinate of the specified point to be measured
     *           against this <code>Point2D</code>
     * @param py the Y coordinate of the specified point to be measured
     *           against this <code>Point2D</code>
     * @return the square of the distance between this
     * <code>Point2D</code> and the specified point.
     * @since 1.2
     */
    public double distanceSq(double px, double py) {
        px -= getX();
        py -= getY();
        return (px * px + py * py);
    }

    /**
     * Returns the square of the distance from this
     * <code>Point2D</code> to a specified <code>Point2D</code>.
     *
     * <p>
     *  返回从此<code> Point2D </code>到指定的<code> Point2D </code>的距离的平方。
     * 
     * 
     * @param pt the specified point to be measured
     *           against this <code>Point2D</code>
     * @return the square of the distance between this
     * <code>Point2D</code> to a specified <code>Point2D</code>.
     * @since 1.2
     */
    public double distanceSq(Point2D pt) {
        double px = pt.getX() - this.getX();
        double py = pt.getY() - this.getY();
        return (px * px + py * py);
    }

    /**
     * Returns the distance from this <code>Point2D</code> to
     * a specified point.
     *
     * <p>
     *  返回从此<code> Point2D </code>到指定点的距离。
     * 
     * 
     * @param px the X coordinate of the specified point to be measured
     *           against this <code>Point2D</code>
     * @param py the Y coordinate of the specified point to be measured
     *           against this <code>Point2D</code>
     * @return the distance between this <code>Point2D</code>
     * and a specified point.
     * @since 1.2
     */
    public double distance(double px, double py) {
        px -= getX();
        py -= getY();
        return Math.sqrt(px * px + py * py);
    }

    /**
     * Returns the distance from this <code>Point2D</code> to a
     * specified <code>Point2D</code>.
     *
     * <p>
     *  返回从此<code> Point2D </code>到指定的<code> Point2D </code>的距离。
     * 
     * 
     * @param pt the specified point to be measured
     *           against this <code>Point2D</code>
     * @return the distance between this <code>Point2D</code> and
     * the specified <code>Point2D</code>.
     * @since 1.2
     */
    public double distance(Point2D pt) {
        double px = pt.getX() - this.getX();
        double py = pt.getY() - this.getY();
        return Math.sqrt(px * px + py * py);
    }

    /**
     * Creates a new object of the same class and with the
     * same contents as this object.
     * <p>
     *  创建与此对象具有相同类和相同内容的新对象。
     * 
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

    /**
     * Returns the hashcode for this <code>Point2D</code>.
     * <p>
     *  返回此<code> Point2D </code>的哈希码。
     * 
     * 
     * @return      a hash code for this <code>Point2D</code>.
     */
    public int hashCode() {
        long bits = java.lang.Double.doubleToLongBits(getX());
        bits ^= java.lang.Double.doubleToLongBits(getY()) * 31;
        return (((int) bits) ^ ((int) (bits >> 32)));
    }

    /**
     * Determines whether or not two points are equal. Two instances of
     * <code>Point2D</code> are equal if the values of their
     * <code>x</code> and <code>y</code> member fields, representing
     * their position in the coordinate space, are the same.
     * <p>
     * 确定两个点是否相等。如果<code> x </code>和<code> y </code>成员字段的值代表它们在坐标空间中的位置相同,则<code> Point2D </code>的两个实例是相等的。
     * 
     * @param obj an object to be compared with this <code>Point2D</code>
     * @return <code>true</code> if the object to be compared is
     *         an instance of <code>Point2D</code> and has
     *         the same values; <code>false</code> otherwise.
     * @since 1.2
     */
    public boolean equals(Object obj) {
        if (obj instanceof Point2D) {
            Point2D p2d = (Point2D) obj;
            return (getX() == p2d.getX()) && (getY() == p2d.getY());
        }
        return super.equals(obj);
    }
}
