/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2006, Oracle and/or its affiliates. All rights reserved.
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
 * The <code>RoundRectangle2D</code> class defines a rectangle with
 * rounded corners defined by a location {@code (x,y)}, a
 * dimension {@code (w x h)}, and the width and height of an arc
 * with which to round the corners.
 * <p>
 * This class is the abstract superclass for all objects that
 * store a 2D rounded rectangle.
 * The actual storage representation of the coordinates is left to
 * the subclass.
 *
 * <p>
 *  <code> RoundRectangle2D </code>类定义了一个由位置{@code(x,y)},维度{@code(wxh)}定义的圆角的矩形,以及圆弧的宽度和高度以圆角。
 * <p>
 *  这个类是存储2D圆角矩形的所有对象的抽象超类。坐标的实际存储表示是留给子类的。
 * 
 * 
 * @author      Jim Graham
 * @since 1.2
 */
public abstract class RoundRectangle2D extends RectangularShape {

    /**
     * The <code>Float</code> class defines a rectangle with rounded
     * corners all specified in <code>float</code> coordinates.
     * <p>
     *  <code> Float </code>类定义了一个在<code> float </code>坐标中指定的具有圆角的矩形。
     * 
     * 
     * @since 1.2
     */
    public static class Float extends RoundRectangle2D
        implements Serializable
    {
        /**
         * The X coordinate of this <code>RoundRectangle2D</code>.
         * <p>
         *  此<code> RoundRectangle2D </code>的X坐标。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public float x;

        /**
         * The Y coordinate of this <code>RoundRectangle2D</code>.
         * <p>
         *  此<code> RoundRectangle2D </code>的Y坐标。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public float y;

        /**
         * The width of this <code>RoundRectangle2D</code>.
         * <p>
         *  这个<code> RoundRectangle2D </code>的宽度。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public float width;

        /**
         * The height of this <code>RoundRectangle2D</code>.
         * <p>
         *  这个<code> RoundRectangle2D </code>的高度。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public float height;

        /**
         * The width of the arc that rounds off the corners.
         * <p>
         *  圆角的圆弧的宽度。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public float arcwidth;

        /**
         * The height of the arc that rounds off the corners.
         * <p>
         *  圆角的圆弧的高度。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public float archeight;

        /**
         * Constructs a new <code>RoundRectangle2D</code>, initialized to
         * location (0.0,&nbsp;0.0), size (0.0,&nbsp;0.0), and corner arcs
         * of radius 0.0.
         * <p>
         *  构造一个新的<code> RoundRectangle2D </code>,初始化为位置(0.0,&nbsp; 0.0),大小(0.0,0.0)和半径为0.0的拐角圆弧。
         * 
         * 
         * @since 1.2
         */
        public Float() {
        }

        /**
         * Constructs and initializes a <code>RoundRectangle2D</code>
         * from the specified <code>float</code> coordinates.
         *
         * <p>
         *  从指定的<code> float </code>坐标构造并初始化<code> RoundRectangle2D </code>。
         * 
         * 
         * @param x the X coordinate of the newly
         *          constructed <code>RoundRectangle2D</code>
         * @param y the Y coordinate of the newly
         *          constructed <code>RoundRectangle2D</code>
         * @param w the width to which to set the newly
         *          constructed <code>RoundRectangle2D</code>
         * @param h the height to which to set the newly
         *          constructed <code>RoundRectangle2D</code>
         * @param arcw the width of the arc to use to round off the
         *             corners of the newly constructed
         *             <code>RoundRectangle2D</code>
         * @param arch the height of the arc to use to round off the
         *             corners of the newly constructed
         *             <code>RoundRectangle2D</code>
         * @since 1.2
         */
        public Float(float x, float y, float w, float h,
                     float arcw, float arch)
        {
            setRoundRect(x, y, w, h, arcw, arch);
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
        public double getWidth() {
            return (double) width;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public double getHeight() {
            return (double) height;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public double getArcWidth() {
            return (double) arcwidth;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public double getArcHeight() {
            return (double) archeight;
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
            return (width <= 0.0f) || (height <= 0.0f);
        }

        /**
         * Sets the location, size, and corner radii of this
         * <code>RoundRectangle2D</code> to the specified
         * <code>float</code> values.
         *
         * <p>
         *  将<code> RoundRectangle2D </code>的位置,大小和角半径设置为指定的<code> float </code>值。
         * 
         * 
         * @param x the X coordinate to which to set the
         *          location of this <code>RoundRectangle2D</code>
         * @param y the Y coordinate to which to set the
         *          location of this <code>RoundRectangle2D</code>
         * @param w the width to which to set this
         *          <code>RoundRectangle2D</code>
         * @param h the height to which to set this
         *          <code>RoundRectangle2D</code>
         * @param arcw the width to which to set the arc of this
         *             <code>RoundRectangle2D</code>
         * @param arch the height to which to set the arc of this
         *             <code>RoundRectangle2D</code>
         * @since 1.2
         */
        public void setRoundRect(float x, float y, float w, float h,
                                 float arcw, float arch)
        {
            this.x = x;
            this.y = y;
            this.width = w;
            this.height = h;
            this.arcwidth = arcw;
            this.archeight = arch;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public void setRoundRect(double x, double y, double w, double h,
                                 double arcw, double arch)
        {
            this.x = (float) x;
            this.y = (float) y;
            this.width = (float) w;
            this.height = (float) h;
            this.arcwidth = (float) arcw;
            this.archeight = (float) arch;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public void setRoundRect(RoundRectangle2D rr) {
            this.x = (float) rr.getX();
            this.y = (float) rr.getY();
            this.width = (float) rr.getWidth();
            this.height = (float) rr.getHeight();
            this.arcwidth = (float) rr.getArcWidth();
            this.archeight = (float) rr.getArcHeight();
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
            return new Rectangle2D.Float(x, y, width, height);
        }

        /*
         * JDK 1.6 serialVersionUID
         * <p>
         * JDK 1.6 serialVersionUID
         * 
         */
        private static final long serialVersionUID = -3423150618393866922L;
    }

    /**
     * The <code>Double</code> class defines a rectangle with rounded
     * corners all specified in <code>double</code> coordinates.
     * <p>
     *  <code> Double </code>类定义了在<code> double </code>坐标中指定的具有圆角的矩形。
     * 
     * 
     * @since 1.2
     */
    public static class Double extends RoundRectangle2D
        implements Serializable
    {
        /**
         * The X coordinate of this <code>RoundRectangle2D</code>.
         * <p>
         *  此<code> RoundRectangle2D </code>的X坐标。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public double x;

        /**
         * The Y coordinate of this <code>RoundRectangle2D</code>.
         * <p>
         *  此<code> RoundRectangle2D </code>的Y坐标。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public double y;

        /**
         * The width of this <code>RoundRectangle2D</code>.
         * <p>
         *  这个<code> RoundRectangle2D </code>的宽度。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public double width;

        /**
         * The height of this <code>RoundRectangle2D</code>.
         * <p>
         *  这个<code> RoundRectangle2D </code>的高度。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public double height;

        /**
         * The width of the arc that rounds off the corners.
         * <p>
         *  圆角的圆弧的宽度。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public double arcwidth;

        /**
         * The height of the arc that rounds off the corners.
         * <p>
         *  圆角的圆弧的高度。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public double archeight;

        /**
         * Constructs a new <code>RoundRectangle2D</code>, initialized to
         * location (0.0,&nbsp;0.0), size (0.0,&nbsp;0.0), and corner arcs
         * of radius 0.0.
         * <p>
         *  构造一个新的<code> RoundRectangle2D </code>,初始化为位置(0.0,&nbsp; 0.0),大小(0.0,0.0)和半径为0.0的拐角圆弧。
         * 
         * 
         * @since 1.2
         */
        public Double() {
        }

        /**
         * Constructs and initializes a <code>RoundRectangle2D</code>
         * from the specified <code>double</code> coordinates.
         *
         * <p>
         *  从指定的<code> double </code>坐标构造并初始化<code> RoundRectangle2D </code>。
         * 
         * 
         * @param x the X coordinate of the newly
         *          constructed <code>RoundRectangle2D</code>
         * @param y the Y coordinate of the newly
         *          constructed <code>RoundRectangle2D</code>
         * @param w the width to which to set the newly
         *          constructed <code>RoundRectangle2D</code>
         * @param h the height to which to set the newly
         *          constructed <code>RoundRectangle2D</code>
         * @param arcw the width of the arc to use to round off the
         *             corners of the newly constructed
         *             <code>RoundRectangle2D</code>
         * @param arch the height of the arc to use to round off the
         *             corners of the newly constructed
         *             <code>RoundRectangle2D</code>
         * @since 1.2
         */
        public Double(double x, double y, double w, double h,
                      double arcw, double arch)
        {
            setRoundRect(x, y, w, h, arcw, arch);
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
        public double getWidth() {
            return width;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public double getHeight() {
            return height;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public double getArcWidth() {
            return arcwidth;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public double getArcHeight() {
            return archeight;
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
            return (width <= 0.0f) || (height <= 0.0f);
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public void setRoundRect(double x, double y, double w, double h,
                                 double arcw, double arch)
        {
            this.x = x;
            this.y = y;
            this.width = w;
            this.height = h;
            this.arcwidth = arcw;
            this.archeight = arch;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public void setRoundRect(RoundRectangle2D rr) {
            this.x = rr.getX();
            this.y = rr.getY();
            this.width = rr.getWidth();
            this.height = rr.getHeight();
            this.arcwidth = rr.getArcWidth();
            this.archeight = rr.getArcHeight();
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
            return new Rectangle2D.Double(x, y, width, height);
        }

        /*
         * JDK 1.6 serialVersionUID
         * <p>
         *  JDK 1.6 serialVersionUID
         * 
         */
        private static final long serialVersionUID = 1048939333485206117L;
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
     * @see java.awt.geom.RoundRectangle2D.Float
     * @see java.awt.geom.RoundRectangle2D.Double
     * @since 1.2
     */
    protected RoundRectangle2D() {
    }

    /**
     * Gets the width of the arc that rounds off the corners.
     * <p>
     *  获取圆角四舍五入的弧的宽度。
     * 
     * 
     * @return the width of the arc that rounds off the corners
     * of this <code>RoundRectangle2D</code>.
     * @since 1.2
     */
    public abstract double getArcWidth();

    /**
     * Gets the height of the arc that rounds off the corners.
     * <p>
     *  获取圆角四舍五入的弧的高度。
     * 
     * 
     * @return the height of the arc that rounds off the corners
     * of this <code>RoundRectangle2D</code>.
     * @since 1.2
     */
    public abstract double getArcHeight();

    /**
     * Sets the location, size, and corner radii of this
     * <code>RoundRectangle2D</code> to the specified
     * <code>double</code> values.
     *
     * <p>
     *  将<code> RoundRectangle2D </code>的位置,大小和角半径设置为指定的<code> double </code>值。
     * 
     * 
     * @param x the X coordinate to which to set the
     *          location of this <code>RoundRectangle2D</code>
     * @param y the Y coordinate to which to set the
     *          location of this <code>RoundRectangle2D</code>
     * @param w the width to which to set this
     *          <code>RoundRectangle2D</code>
     * @param h the height to which to set this
     *          <code>RoundRectangle2D</code>
     * @param arcWidth the width to which to set the arc of this
     *                 <code>RoundRectangle2D</code>
     * @param arcHeight the height to which to set the arc of this
     *                  <code>RoundRectangle2D</code>
     * @since 1.2
     */
    public abstract void setRoundRect(double x, double y, double w, double h,
                                      double arcWidth, double arcHeight);

    /**
     * Sets this <code>RoundRectangle2D</code> to be the same as the
     * specified <code>RoundRectangle2D</code>.
     * <p>
     *  将<code> RoundRectangle2D </code>设置为与指定的<code> RoundRectangle2D </code>相同。
     * 
     * 
     * @param rr the specified <code>RoundRectangle2D</code>
     * @since 1.2
     */
    public void setRoundRect(RoundRectangle2D rr) {
        setRoundRect(rr.getX(), rr.getY(), rr.getWidth(), rr.getHeight(),
                     rr.getArcWidth(), rr.getArcHeight());
    }

    /**
     * {@inheritDoc}
     * <p>
     * {@inheritDoc}
     * 
     * 
     * @since 1.2
     */
    public void setFrame(double x, double y, double w, double h) {
        setRoundRect(x, y, w, h, getArcWidth(), getArcHeight());
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
        if (isEmpty()) {
            return false;
        }
        double rrx0 = getX();
        double rry0 = getY();
        double rrx1 = rrx0 + getWidth();
        double rry1 = rry0 + getHeight();
        // Check for trivial rejection - point is outside bounding rectangle
        if (x < rrx0 || y < rry0 || x >= rrx1 || y >= rry1) {
            return false;
        }
        double aw = Math.min(getWidth(), Math.abs(getArcWidth())) / 2.0;
        double ah = Math.min(getHeight(), Math.abs(getArcHeight())) / 2.0;
        // Check which corner point is in and do circular containment
        // test - otherwise simple acceptance
        if (x >= (rrx0 += aw) && x < (rrx0 = rrx1 - aw)) {
            return true;
        }
        if (y >= (rry0 += ah) && y < (rry0 = rry1 - ah)) {
            return true;
        }
        x = (x - rrx0) / aw;
        y = (y - rry0) / ah;
        return (x * x + y * y <= 1.0);
    }

    private int classify(double coord, double left, double right,
                         double arcsize)
    {
        if (coord < left) {
            return 0;
        } else if (coord < left + arcsize) {
            return 1;
        } else if (coord < right - arcsize) {
            return 2;
        } else if (coord < right) {
            return 3;
        } else {
            return 4;
        }
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
        if (isEmpty() || w <= 0 || h <= 0) {
            return false;
        }
        double rrx0 = getX();
        double rry0 = getY();
        double rrx1 = rrx0 + getWidth();
        double rry1 = rry0 + getHeight();
        // Check for trivial rejection - bounding rectangles do not intersect
        if (x + w <= rrx0 || x >= rrx1 || y + h <= rry0 || y >= rry1) {
            return false;
        }
        double aw = Math.min(getWidth(), Math.abs(getArcWidth())) / 2.0;
        double ah = Math.min(getHeight(), Math.abs(getArcHeight())) / 2.0;
        int x0class = classify(x, rrx0, rrx1, aw);
        int x1class = classify(x + w, rrx0, rrx1, aw);
        int y0class = classify(y, rry0, rry1, ah);
        int y1class = classify(y + h, rry0, rry1, ah);
        // Trivially accept if any point is inside inner rectangle
        if (x0class == 2 || x1class == 2 || y0class == 2 || y1class == 2) {
            return true;
        }
        // Trivially accept if either edge spans inner rectangle
        if ((x0class < 2 && x1class > 2) || (y0class < 2 && y1class > 2)) {
            return true;
        }
        // Since neither edge spans the center, then one of the corners
        // must be in one of the rounded edges.  We detect this case if
        // a [xy]0class is 3 or a [xy]1class is 1.  One of those two cases
        // must be true for each direction.
        // We now find a "nearest point" to test for being inside a rounded
        // corner.
        x = (x1class == 1) ? (x = x + w - (rrx0 + aw)) : (x = x - (rrx1 - aw));
        y = (y1class == 1) ? (y = y + h - (rry0 + ah)) : (y = y - (rry1 - ah));
        x = x / aw;
        y = y / ah;
        return (x * x + y * y <= 1.0);
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
        if (isEmpty() || w <= 0 || h <= 0) {
            return false;
        }
        return (contains(x, y) &&
                contains(x + w, y) &&
                contains(x, y + h) &&
                contains(x + w, y + h));
    }

    /**
     * Returns an iteration object that defines the boundary of this
     * <code>RoundRectangle2D</code>.
     * The iterator for this class is multi-threaded safe, which means
     * that this <code>RoundRectangle2D</code> class guarantees that
     * modifications to the geometry of this <code>RoundRectangle2D</code>
     * object do not affect any iterations of that geometry that
     * are already in process.
     * <p>
     *  返回一个迭代对象,它定义了这个<code> RoundRectangle2D </code>的边界。
     * 这个类的迭代器是多线程安全的,这意味着这个<code> RoundRectangle2D </code>类保证对这个<code> RoundRectangle2D </code>对象的几何形状的修改不会
     * 影响该几何的任何迭代已经在进行中。
     *  返回一个迭代对象,它定义了这个<code> RoundRectangle2D </code>的边界。
     * 
     * 
     * @param at an optional <code>AffineTransform</code> to be applied to
     * the coordinates as they are returned in the iteration, or
     * <code>null</code> if untransformed coordinates are desired
     * @return    the <code>PathIterator</code> object that returns the
     *          geometry of the outline of this
     *          <code>RoundRectangle2D</code>, one segment at a time.
     * @since 1.2
     */
    public PathIterator getPathIterator(AffineTransform at) {
        return new RoundRectIterator(this, at);
    }

    /**
     * Returns the hashcode for this <code>RoundRectangle2D</code>.
     * <p>
     *  返回此<c> RoundRectangle2D </code>的哈希码。
     * 
     * 
     * @return the hashcode for this <code>RoundRectangle2D</code>.
     * @since 1.6
     */
    public int hashCode() {
        long bits = java.lang.Double.doubleToLongBits(getX());
        bits += java.lang.Double.doubleToLongBits(getY()) * 37;
        bits += java.lang.Double.doubleToLongBits(getWidth()) * 43;
        bits += java.lang.Double.doubleToLongBits(getHeight()) * 47;
        bits += java.lang.Double.doubleToLongBits(getArcWidth()) * 53;
        bits += java.lang.Double.doubleToLongBits(getArcHeight()) * 59;
        return (((int) bits) ^ ((int) (bits >> 32)));
    }

    /**
     * Determines whether or not the specified <code>Object</code> is
     * equal to this <code>RoundRectangle2D</code>.  The specified
     * <code>Object</code> is equal to this <code>RoundRectangle2D</code>
     * if it is an instance of <code>RoundRectangle2D</code> and if its
     * location, size, and corner arc dimensions are the same as this
     * <code>RoundRectangle2D</code>.
     * <p>
     *  确定指定的<code> Object </code>是否等于此<code> RoundRectangle2D </code>。
     * 如果它是<code> RoundRectangle2D </code>的一个实例,并且它的位置,大小和角弧尺寸是相同的,则指定的<code> Object </code>等于这个<code> Round
     * Rectangle2D </code>作为<code> RoundRectangle2D </code>。
     * 
     * @param obj  an <code>Object</code> to be compared with this
     *             <code>RoundRectangle2D</code>.
     * @return  <code>true</code> if <code>obj</code> is an instance
     *          of <code>RoundRectangle2D</code> and has the same values;
     *          <code>false</code> otherwise.
     * @since 1.6
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof RoundRectangle2D) {
            RoundRectangle2D rr2d = (RoundRectangle2D) obj;
            return ((getX() == rr2d.getX()) &&
                    (getY() == rr2d.getY()) &&
                    (getWidth() == rr2d.getWidth()) &&
                    (getHeight() == rr2d.getHeight()) &&
                    (getArcWidth() == rr2d.getArcWidth()) &&
                    (getArcHeight() == rr2d.getArcHeight()));
        }
        return false;
    }
}
