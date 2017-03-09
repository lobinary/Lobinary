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
 * <CODE>Arc2D</CODE> is the abstract superclass for all objects that
 * store a 2D arc defined by a framing rectangle,
 * start angle, angular extent (length of the arc), and a closure type
 * (<CODE>OPEN</CODE>, <CODE>CHORD</CODE>, or <CODE>PIE</CODE>).
 * <p>
 * <a name="inscribes">
 * The arc is a partial section of a full ellipse which
 * inscribes the framing rectangle of its parent {@link RectangularShape}.
 * </a>
 * <a name="angles">
 * The angles are specified relative to the non-square
 * framing rectangle such that 45 degrees always falls on the line from
 * the center of the ellipse to the upper right corner of the framing
 * rectangle.
 * As a result, if the framing rectangle is noticeably longer along one
 * axis than the other, the angles to the start and end of the arc segment
 * will be skewed farther along the longer axis of the frame.
 * </a>
 * <p>
 * The actual storage representation of the coordinates is left to
 * the subclass.
 *
 * <p>
 *  <CODE> Arc2D </CODE>是存储由框架矩形,起始角,角度范围(弧的长度)和闭包类型(<CODE> OPEN </CODE)定义的2D弧的所有对象的抽象超类>,<CODE> CHORD </CODE>
 * 或<CODE> PIE </CODE>)。
 * <p>
 * <a name="inscribes">
 *  弧是整个椭圆的部分截面,其内接于其父{@link RectangularShape}的框架矩形。
 * </a>
 * <a name="angles">
 *  角度相对于非正方形框架矩形指定,使得45度总是落在从椭圆的中心到框架矩形的右上角的线上。因此,如果框架矩形沿着一个轴比另一个明显更长,则到弧段的开始和结束的角度将沿着框架的较长轴偏移得更远。
 * </a>
 * <p>
 *  坐标的实际存储表示是留给子类的。
 * 
 * 
 * @author      Jim Graham
 * @since 1.2
 */
public abstract class Arc2D extends RectangularShape {

    /**
     * The closure type for an open arc with no path segments
     * connecting the two ends of the arc segment.
     * <p>
     *  对于没有连接弧段的两端的路径段的开放弧的闭合类型。
     * 
     * 
     * @since 1.2
     */
    public final static int OPEN = 0;

    /**
     * The closure type for an arc closed by drawing a straight
     * line segment from the start of the arc segment to the end of the
     * arc segment.
     * <p>
     *  通过从弧段的起点到弧段的终点绘制直线段来关闭弧的闭合类型。
     * 
     * 
     * @since 1.2
     */
    public final static int CHORD = 1;

    /**
     * The closure type for an arc closed by drawing straight line
     * segments from the start of the arc segment to the center
     * of the full ellipse and from that point to the end of the arc segment.
     * <p>
     *  通过从圆弧段的起点到整个椭圆的中心并从该点到圆弧段的终点绘制直线段闭合的圆弧的闭合类型。
     * 
     * 
     * @since 1.2
     */
    public final static int PIE = 2;

    /**
     * This class defines an arc specified in {@code float} precision.
     * <p>
     *  这个类定义了{@code float} precision中指定的弧。
     * 
     * 
     * @since 1.2
     */
    public static class Float extends Arc2D implements Serializable {
        /**
         * The X coordinate of the upper-left corner of the framing
         * rectangle of the arc.
         * <p>
         * 弧的框架矩形的左上角的X坐标。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public float x;

        /**
         * The Y coordinate of the upper-left corner of the framing
         * rectangle of the arc.
         * <p>
         *  圆弧框架矩形的左上角的Y坐标。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public float y;

        /**
         * The overall width of the full ellipse of which this arc is
         * a partial section (not considering the
         * angular extents).
         * <p>
         *  整个椭圆的总宽度,其中该弧是部分截面(不考虑角度范围)。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public float width;

        /**
         * The overall height of the full ellipse of which this arc is
         * a partial section (not considering the
         * angular extents).
         * <p>
         *  整个椭圆的总高度,其中该弧是部分截面(不考虑角度范围)。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public float height;

        /**
         * The starting angle of the arc in degrees.
         * <p>
         *  圆弧的起始角度。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public float start;

        /**
         * The angular extent of the arc in degrees.
         * <p>
         *  弧度的角度范围。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public float extent;

        /**
         * Constructs a new OPEN arc, initialized to location (0, 0),
         * size (0, 0), angular extents (start = 0, extent = 0).
         * <p>
         *  构造一个新的OPEN弧,初始化为位置(0,0),大小(0,0),角度范围(start = 0,extent = 0)。
         * 
         * 
         * @since 1.2
         */
        public Float() {
            super(OPEN);
        }

        /**
         * Constructs a new arc, initialized to location (0, 0),
         * size (0, 0), angular extents (start = 0, extent = 0), and
         * the specified closure type.
         *
         * <p>
         *  构造一个新弧,初始化为位置(0,0),大小(0,0),角度范围(start = 0,extent = 0)和指定的闭包类型。
         * 
         * 
         * @param type The closure type for the arc:
         * {@link #OPEN}, {@link #CHORD}, or {@link #PIE}.
         * @since 1.2
         */
        public Float(int type) {
            super(type);
        }

        /**
         * Constructs a new arc, initialized to the specified location,
         * size, angular extents, and closure type.
         *
         * <p>
         *  构造一个新的弧,初始化为指定的位置,大小,角度范围和闭合类型。
         * 
         * 
         * @param x The X coordinate of the upper-left corner of
         *          the arc's framing rectangle.
         * @param y The Y coordinate of the upper-left corner of
         *          the arc's framing rectangle.
         * @param w The overall width of the full ellipse of which
         *          this arc is a partial section.
         * @param h The overall height of the full ellipse of which this
         *          arc is a partial section.
         * @param start The starting angle of the arc in degrees.
         * @param extent The angular extent of the arc in degrees.
         * @param type The closure type for the arc:
         * {@link #OPEN}, {@link #CHORD}, or {@link #PIE}.
         * @since 1.2
         */
        public Float(float x, float y, float w, float h,
                     float start, float extent, int type) {
            super(type);
            this.x = x;
            this.y = y;
            this.width = w;
            this.height = h;
            this.start = start;
            this.extent = extent;
        }

        /**
         * Constructs a new arc, initialized to the specified location,
         * size, angular extents, and closure type.
         *
         * <p>
         *  构造一个新的弧,初始化为指定的位置,大小,角度范围和闭合类型。
         * 
         * 
         * @param ellipseBounds The framing rectangle that defines the
         * outer boundary of the full ellipse of which this arc is a
         * partial section.
         * @param start The starting angle of the arc in degrees.
         * @param extent The angular extent of the arc in degrees.
         * @param type The closure type for the arc:
         * {@link #OPEN}, {@link #CHORD}, or {@link #PIE}.
         * @since 1.2
         */
        public Float(Rectangle2D ellipseBounds,
                     float start, float extent, int type) {
            super(type);
            this.x = (float) ellipseBounds.getX();
            this.y = (float) ellipseBounds.getY();
            this.width = (float) ellipseBounds.getWidth();
            this.height = (float) ellipseBounds.getHeight();
            this.start = start;
            this.extent = extent;
        }

        /**
         * {@inheritDoc}
         * Note that the arc
         * <a href="Arc2D.html#inscribes">partially inscribes</a>
         * the framing rectangle of this {@code RectangularShape}.
         *
         * <p>
         *  {@inheritDoc}请注意,弧{<a href="Arc2D.html#inscribes">部分内接</a>此{@code RectangularShape}的框架矩形。
         * 
         * 
         * @since 1.2
         */
        public double getX() {
            return (double) x;
        }

        /**
         * {@inheritDoc}
         * Note that the arc
         * <a href="Arc2D.html#inscribes">partially inscribes</a>
         * the framing rectangle of this {@code RectangularShape}.
         *
         * <p>
         *  {@inheritDoc}请注意,弧{<a href="Arc2D.html#inscribes">部分内接</a>此{@code RectangularShape}的框架矩形。
         * 
         * 
         * @since 1.2
         */
        public double getY() {
            return (double) y;
        }

        /**
         * {@inheritDoc}
         * Note that the arc
         * <a href="Arc2D.html#inscribes">partially inscribes</a>
         * the framing rectangle of this {@code RectangularShape}.
         *
         * <p>
         *  {@inheritDoc}请注意,弧{<a href="Arc2D.html#inscribes">部分内接</a>此{@code RectangularShape}的框架矩形。
         * 
         * 
         * @since 1.2
         */
        public double getWidth() {
            return (double) width;
        }

        /**
         * {@inheritDoc}
         * Note that the arc
         * <a href="Arc2D.html#inscribes">partially inscribes</a>
         * the framing rectangle of this {@code RectangularShape}.
         *
         * <p>
         * {@inheritDoc}请注意,弧{<a href="Arc2D.html#inscribes">部分内接</a>此{@code RectangularShape}的框架矩形。
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
        public double getAngleStart() {
            return (double) start;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public double getAngleExtent() {
            return (double) extent;
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
            return (width <= 0.0 || height <= 0.0);
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public void setArc(double x, double y, double w, double h,
                           double angSt, double angExt, int closure) {
            this.setArcType(closure);
            this.x = (float) x;
            this.y = (float) y;
            this.width = (float) w;
            this.height = (float) h;
            this.start = (float) angSt;
            this.extent = (float) angExt;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public void setAngleStart(double angSt) {
            this.start = (float) angSt;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public void setAngleExtent(double angExt) {
            this.extent = (float) angExt;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        protected Rectangle2D makeBounds(double x, double y,
                                         double w, double h) {
            return new Rectangle2D.Float((float) x, (float) y,
                                         (float) w, (float) h);
        }

        /*
         * JDK 1.6 serialVersionUID
         * <p>
         *  JDK 1.6 serialVersionUID
         * 
         */
        private static final long serialVersionUID = 9130893014586380278L;

        /**
         * Writes the default serializable fields to the
         * <code>ObjectOutputStream</code> followed by a byte
         * indicating the arc type of this <code>Arc2D</code>
         * instance.
         *
         * <p>
         *  将默认可序列化字段写入<code> ObjectOutputStream </code>后跟一个字节,指示此<code> Arc2D </code>实例的弧类型。
         * 
         * 
         * @serialData
         * <ol>
         * <li>The default serializable fields.
         * <li>
         * followed by a <code>byte</code> indicating the arc type
         * {@link #OPEN}, {@link #CHORD}, or {@link #PIE}.
         * </ol>
         */
        private void writeObject(java.io.ObjectOutputStream s)
            throws java.io.IOException
        {
            s.defaultWriteObject();

            s.writeByte(getArcType());
        }

        /**
         * Reads the default serializable fields from the
         * <code>ObjectInputStream</code> followed by a byte
         * indicating the arc type of this <code>Arc2D</code>
         * instance.
         *
         * <p>
         *  从<code> ObjectInputStream </code>中读取默认可序列化字段,后跟一个指示此<code> Arc2D </code>实例的弧类型的字节。
         * 
         * 
         * @serialData
         * <ol>
         * <li>The default serializable fields.
         * <li>
         * followed by a <code>byte</code> indicating the arc type
         * {@link #OPEN}, {@link #CHORD}, or {@link #PIE}.
         * </ol>
         */
        private void readObject(java.io.ObjectInputStream s)
            throws java.lang.ClassNotFoundException, java.io.IOException
        {
            s.defaultReadObject();

            try {
                setArcType(s.readByte());
            } catch (IllegalArgumentException iae) {
                throw new java.io.InvalidObjectException(iae.getMessage());
            }
        }
    }

    /**
     * This class defines an arc specified in {@code double} precision.
     * <p>
     *  此类定义在{@code double} precision中指定的弧。
     * 
     * 
     * @since 1.2
     */
    public static class Double extends Arc2D implements Serializable {
        /**
         * The X coordinate of the upper-left corner of the framing
         * rectangle of the arc.
         * <p>
         *  弧的框架矩形的左上角的X坐标。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public double x;

        /**
         * The Y coordinate of the upper-left corner of the framing
         * rectangle of the arc.
         * <p>
         *  圆弧框架矩形的左上角的Y坐标。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public double y;

        /**
         * The overall width of the full ellipse of which this arc is
         * a partial section (not considering the angular extents).
         * <p>
         *  整个椭圆的总宽度,其中该弧是部分截面(不考虑角度范围)。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public double width;

        /**
         * The overall height of the full ellipse of which this arc is
         * a partial section (not considering the angular extents).
         * <p>
         *  整个椭圆的总高度,其中该弧是部分截面(不考虑角度范围)。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public double height;

        /**
         * The starting angle of the arc in degrees.
         * <p>
         *  圆弧的起始角度。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public double start;

        /**
         * The angular extent of the arc in degrees.
         * <p>
         *  弧度的角度范围。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public double extent;

        /**
         * Constructs a new OPEN arc, initialized to location (0, 0),
         * size (0, 0), angular extents (start = 0, extent = 0).
         * <p>
         *  构造一个新的OPEN弧,初始化为位置(0,0),大小(0,0),角度范围(start = 0,extent = 0)。
         * 
         * 
         * @since 1.2
         */
        public Double() {
            super(OPEN);
        }

        /**
         * Constructs a new arc, initialized to location (0, 0),
         * size (0, 0), angular extents (start = 0, extent = 0), and
         * the specified closure type.
         *
         * <p>
         *  构造一个新弧,初始化为位置(0,0),大小(0,0),角度范围(start = 0,extent = 0)和指定的闭包类型。
         * 
         * 
         * @param type The closure type for the arc:
         * {@link #OPEN}, {@link #CHORD}, or {@link #PIE}.
         * @since 1.2
         */
        public Double(int type) {
            super(type);
        }

        /**
         * Constructs a new arc, initialized to the specified location,
         * size, angular extents, and closure type.
         *
         * <p>
         *  构造一个新的弧,初始化为指定的位置,大小,角度范围和闭合类型。
         * 
         * 
         * @param x The X coordinate of the upper-left corner
         *          of the arc's framing rectangle.
         * @param y The Y coordinate of the upper-left corner
         *          of the arc's framing rectangle.
         * @param w The overall width of the full ellipse of which this
         *          arc is a partial section.
         * @param h The overall height of the full ellipse of which this
         *          arc is a partial section.
         * @param start The starting angle of the arc in degrees.
         * @param extent The angular extent of the arc in degrees.
         * @param type The closure type for the arc:
         * {@link #OPEN}, {@link #CHORD}, or {@link #PIE}.
         * @since 1.2
         */
        public Double(double x, double y, double w, double h,
                      double start, double extent, int type) {
            super(type);
            this.x = x;
            this.y = y;
            this.width = w;
            this.height = h;
            this.start = start;
            this.extent = extent;
        }

        /**
         * Constructs a new arc, initialized to the specified location,
         * size, angular extents, and closure type.
         *
         * <p>
         * 构造一个新的弧,初始化为指定的位置,大小,角度范围和闭合类型。
         * 
         * 
         * @param ellipseBounds The framing rectangle that defines the
         * outer boundary of the full ellipse of which this arc is a
         * partial section.
         * @param start The starting angle of the arc in degrees.
         * @param extent The angular extent of the arc in degrees.
         * @param type The closure type for the arc:
         * {@link #OPEN}, {@link #CHORD}, or {@link #PIE}.
         * @since 1.2
         */
        public Double(Rectangle2D ellipseBounds,
                      double start, double extent, int type) {
            super(type);
            this.x = ellipseBounds.getX();
            this.y = ellipseBounds.getY();
            this.width = ellipseBounds.getWidth();
            this.height = ellipseBounds.getHeight();
            this.start = start;
            this.extent = extent;
        }

        /**
         * {@inheritDoc}
         * Note that the arc
         * <a href="Arc2D.html#inscribes">partially inscribes</a>
         * the framing rectangle of this {@code RectangularShape}.
         *
         * <p>
         *  {@inheritDoc}请注意,弧{<a href="Arc2D.html#inscribes">部分内接</a>此{@code RectangularShape}的框架矩形。
         * 
         * 
         * @since 1.2
         */
        public double getX() {
            return x;
        }

        /**
         * {@inheritDoc}
         * Note that the arc
         * <a href="Arc2D.html#inscribes">partially inscribes</a>
         * the framing rectangle of this {@code RectangularShape}.
         *
         * <p>
         *  {@inheritDoc}请注意,弧{<a href="Arc2D.html#inscribes">部分内接</a>此{@code RectangularShape}的框架矩形。
         * 
         * 
         * @since 1.2
         */
        public double getY() {
            return y;
        }

        /**
         * {@inheritDoc}
         * Note that the arc
         * <a href="Arc2D.html#inscribes">partially inscribes</a>
         * the framing rectangle of this {@code RectangularShape}.
         *
         * <p>
         *  {@inheritDoc}请注意,弧{<a href="Arc2D.html#inscribes">部分内接</a>此{@code RectangularShape}的框架矩形。
         * 
         * 
         * @since 1.2
         */
        public double getWidth() {
            return width;
        }

        /**
         * {@inheritDoc}
         * Note that the arc
         * <a href="Arc2D.html#inscribes">partially inscribes</a>
         * the framing rectangle of this {@code RectangularShape}.
         *
         * <p>
         *  {@inheritDoc}请注意,弧{<a href="Arc2D.html#inscribes">部分内接</a>此{@code RectangularShape}的框架矩形。
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
        public double getAngleStart() {
            return start;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public double getAngleExtent() {
            return extent;
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
            return (width <= 0.0 || height <= 0.0);
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public void setArc(double x, double y, double w, double h,
                           double angSt, double angExt, int closure) {
            this.setArcType(closure);
            this.x = x;
            this.y = y;
            this.width = w;
            this.height = h;
            this.start = angSt;
            this.extent = angExt;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public void setAngleStart(double angSt) {
            this.start = angSt;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public void setAngleExtent(double angExt) {
            this.extent = angExt;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        protected Rectangle2D makeBounds(double x, double y,
                                         double w, double h) {
            return new Rectangle2D.Double(x, y, w, h);
        }

        /*
         * JDK 1.6 serialVersionUID
         * <p>
         *  JDK 1.6 serialVersionUID
         * 
         */
        private static final long serialVersionUID = 728264085846882001L;

        /**
         * Writes the default serializable fields to the
         * <code>ObjectOutputStream</code> followed by a byte
         * indicating the arc type of this <code>Arc2D</code>
         * instance.
         *
         * <p>
         *  将默认可序列化字段写入<code> ObjectOutputStream </code>后跟一个字节,指示此<code> Arc2D </code>实例的弧类型。
         * 
         * 
         * @serialData
         * <ol>
         * <li>The default serializable fields.
         * <li>
         * followed by a <code>byte</code> indicating the arc type
         * {@link #OPEN}, {@link #CHORD}, or {@link #PIE}.
         * </ol>
         */
        private void writeObject(java.io.ObjectOutputStream s)
            throws java.io.IOException
        {
            s.defaultWriteObject();

            s.writeByte(getArcType());
        }

        /**
         * Reads the default serializable fields from the
         * <code>ObjectInputStream</code> followed by a byte
         * indicating the arc type of this <code>Arc2D</code>
         * instance.
         *
         * <p>
         *  从<code> ObjectInputStream </code>中读取默认可序列化字段,后跟一个指示此<code> Arc2D </code>实例的弧类型的字节。
         * 
         * 
         * @serialData
         * <ol>
         * <li>The default serializable fields.
         * <li>
         * followed by a <code>byte</code> indicating the arc type
         * {@link #OPEN}, {@link #CHORD}, or {@link #PIE}.
         * </ol>
         */
        private void readObject(java.io.ObjectInputStream s)
            throws java.lang.ClassNotFoundException, java.io.IOException
        {
            s.defaultReadObject();

            try {
                setArcType(s.readByte());
            } catch (IllegalArgumentException iae) {
                throw new java.io.InvalidObjectException(iae.getMessage());
            }
        }
    }

    private int type;

    /**
     * This is an abstract class that cannot be instantiated directly.
     * Type-specific implementation subclasses are available for
     * instantiation and provide a number of formats for storing
     * the information necessary to satisfy the various accessor
     * methods below.
     * <p>
     * This constructor creates an object with a default closure
     * type of {@link #OPEN}.  It is provided only to enable
     * serialization of subclasses.
     *
     * <p>
     *  这是一个不能直接实例化的抽象类。类型特定的实现子类可用于实例化并且提供用于存储满足下面的各种存取器方法所必需的信息的多种格式。
     * <p>
     * 此构造函数创建一个默认闭包类型为{@link #OPEN}的对象。它仅用于启用子类的序列化。
     * 
     * 
     * @see java.awt.geom.Arc2D.Float
     * @see java.awt.geom.Arc2D.Double
     */
    protected Arc2D() {
        this(OPEN);
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
     * @param type The closure type of this arc:
     * {@link #OPEN}, {@link #CHORD}, or {@link #PIE}.
     * @see java.awt.geom.Arc2D.Float
     * @see java.awt.geom.Arc2D.Double
     * @since 1.2
     */
    protected Arc2D(int type) {
        setArcType(type);
    }

    /**
     * Returns the starting angle of the arc.
     *
     * <p>
     *  返回弧的起始角度。
     * 
     * 
     * @return A double value that represents the starting angle
     * of the arc in degrees.
     * @see #setAngleStart
     * @since 1.2
     */
    public abstract double getAngleStart();

    /**
     * Returns the angular extent of the arc.
     *
     * <p>
     *  返回弧的角度范围。
     * 
     * 
     * @return A double value that represents the angular extent
     * of the arc in degrees.
     * @see #setAngleExtent
     * @since 1.2
     */
    public abstract double getAngleExtent();

    /**
     * Returns the arc closure type of the arc: {@link #OPEN},
     * {@link #CHORD}, or {@link #PIE}.
     * <p>
     *  返回圆弧的闭环类型：{@link #OPEN},{@link #CHORD}或{@link #PIE}。
     * 
     * 
     * @return One of the integer constant closure types defined
     * in this class.
     * @see #setArcType
     * @since 1.2
     */
    public int getArcType() {
        return type;
    }

    /**
     * Returns the starting point of the arc.  This point is the
     * intersection of the ray from the center defined by the
     * starting angle and the elliptical boundary of the arc.
     *
     * <p>
     *  返回弧的起点。这个点是从起始角度定义的中心的光线与弧的椭圆边界的交点。
     * 
     * 
     * @return A <CODE>Point2D</CODE> object representing the
     * x,y coordinates of the starting point of the arc.
     * @since 1.2
     */
    public Point2D getStartPoint() {
        double angle = Math.toRadians(-getAngleStart());
        double x = getX() + (Math.cos(angle) * 0.5 + 0.5) * getWidth();
        double y = getY() + (Math.sin(angle) * 0.5 + 0.5) * getHeight();
        return new Point2D.Double(x, y);
    }

    /**
     * Returns the ending point of the arc.  This point is the
     * intersection of the ray from the center defined by the
     * starting angle plus the angular extent of the arc and the
     * elliptical boundary of the arc.
     *
     * <p>
     *  返回圆弧的终点。这个点是由起始角度加上弧的角度范围和弧的椭圆边界限定的中心的光线的交点。
     * 
     * 
     * @return A <CODE>Point2D</CODE> object representing the
     * x,y coordinates  of the ending point of the arc.
     * @since 1.2
     */
    public Point2D getEndPoint() {
        double angle = Math.toRadians(-getAngleStart() - getAngleExtent());
        double x = getX() + (Math.cos(angle) * 0.5 + 0.5) * getWidth();
        double y = getY() + (Math.sin(angle) * 0.5 + 0.5) * getHeight();
        return new Point2D.Double(x, y);
    }

    /**
     * Sets the location, size, angular extents, and closure type of
     * this arc to the specified double values.
     *
     * <p>
     *  将此弧的位置,大小,角度范围和闭合类型设置为指定的double值。
     * 
     * 
     * @param x The X coordinate of the upper-left corner of the arc.
     * @param y The Y coordinate of the upper-left corner of the arc.
     * @param w The overall width of the full ellipse of which
     *          this arc is a partial section.
     * @param h The overall height of the full ellipse of which
     *          this arc is a partial section.
     * @param angSt The starting angle of the arc in degrees.
     * @param angExt The angular extent of the arc in degrees.
     * @param closure The closure type for the arc:
     * {@link #OPEN}, {@link #CHORD}, or {@link #PIE}.
     * @since 1.2
     */
    public abstract void setArc(double x, double y, double w, double h,
                                double angSt, double angExt, int closure);

    /**
     * Sets the location, size, angular extents, and closure type of
     * this arc to the specified values.
     *
     * <p>
     *  将此弧的位置,大小,角度范围和闭合类型设置为指定的值。
     * 
     * 
     * @param loc The <CODE>Point2D</CODE> representing the coordinates of
     * the upper-left corner of the arc.
     * @param size The <CODE>Dimension2D</CODE> representing the width
     * and height of the full ellipse of which this arc is
     * a partial section.
     * @param angSt The starting angle of the arc in degrees.
     * @param angExt The angular extent of the arc in degrees.
     * @param closure The closure type for the arc:
     * {@link #OPEN}, {@link #CHORD}, or {@link #PIE}.
     * @since 1.2
     */
    public void setArc(Point2D loc, Dimension2D size,
                       double angSt, double angExt, int closure) {
        setArc(loc.getX(), loc.getY(), size.getWidth(), size.getHeight(),
               angSt, angExt, closure);
    }

    /**
     * Sets the location, size, angular extents, and closure type of
     * this arc to the specified values.
     *
     * <p>
     *  将此弧的位置,大小,角度范围和闭合类型设置为指定的值。
     * 
     * 
     * @param rect The framing rectangle that defines the
     * outer boundary of the full ellipse of which this arc is a
     * partial section.
     * @param angSt The starting angle of the arc in degrees.
     * @param angExt The angular extent of the arc in degrees.
     * @param closure The closure type for the arc:
     * {@link #OPEN}, {@link #CHORD}, or {@link #PIE}.
     * @since 1.2
     */
    public void setArc(Rectangle2D rect, double angSt, double angExt,
                       int closure) {
        setArc(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(),
               angSt, angExt, closure);
    }

    /**
     * Sets this arc to be the same as the specified arc.
     *
     * <p>
     *  将此圆弧设置为与指定圆弧相同。
     * 
     * 
     * @param a The <CODE>Arc2D</CODE> to use to set the arc's values.
     * @since 1.2
     */
    public void setArc(Arc2D a) {
        setArc(a.getX(), a.getY(), a.getWidth(), a.getHeight(),
               a.getAngleStart(), a.getAngleExtent(), a.type);
    }

    /**
     * Sets the position, bounds, angular extents, and closure type of
     * this arc to the specified values. The arc is defined by a center
     * point and a radius rather than a framing rectangle for the full ellipse.
     *
     * <p>
     *  将此弧的位置,边界,角度范围和闭合类型设置为指定的值。弧由中心点和半径定义,而不是整个椭圆的框架矩形。
     * 
     * 
     * @param x The X coordinate of the center of the arc.
     * @param y The Y coordinate of the center of the arc.
     * @param radius The radius of the arc.
     * @param angSt The starting angle of the arc in degrees.
     * @param angExt The angular extent of the arc in degrees.
     * @param closure The closure type for the arc:
     * {@link #OPEN}, {@link #CHORD}, or {@link #PIE}.
     * @since 1.2
     */
    public void setArcByCenter(double x, double y, double radius,
                               double angSt, double angExt, int closure) {
        setArc(x - radius, y - radius, radius * 2.0, radius * 2.0,
               angSt, angExt, closure);
    }

    /**
     * Sets the position, bounds, and angular extents of this arc to the
     * specified value. The starting angle of the arc is tangent to the
     * line specified by points (p1, p2), the ending angle is tangent to
     * the line specified by points (p2, p3), and the arc has the
     * specified radius.
     *
     * <p>
     * 将此弧的位置,边界和角度范围设置为指定的值。圆弧的起始角度与点(p1,p2)指定的直线相切,结束角与由点(p2,p3)指定的直线相切,圆弧具有指定的半径。
     * 
     * 
     * @param p1 The first point that defines the arc. The starting
     * angle of the arc is tangent to the line specified by points (p1, p2).
     * @param p2 The second point that defines the arc. The starting
     * angle of the arc is tangent to the line specified by points (p1, p2).
     * The ending angle of the arc is tangent to the line specified by
     * points (p2, p3).
     * @param p3 The third point that defines the arc. The ending angle
     * of the arc is tangent to the line specified by points (p2, p3).
     * @param radius The radius of the arc.
     * @since 1.2
     */
    public void setArcByTangent(Point2D p1, Point2D p2, Point2D p3,
                                double radius) {
        double ang1 = Math.atan2(p1.getY() - p2.getY(),
                                 p1.getX() - p2.getX());
        double ang2 = Math.atan2(p3.getY() - p2.getY(),
                                 p3.getX() - p2.getX());
        double diff = ang2 - ang1;
        if (diff > Math.PI) {
            ang2 -= Math.PI * 2.0;
        } else if (diff < -Math.PI) {
            ang2 += Math.PI * 2.0;
        }
        double bisect = (ang1 + ang2) / 2.0;
        double theta = Math.abs(ang2 - bisect);
        double dist = radius / Math.sin(theta);
        double x = p2.getX() + dist * Math.cos(bisect);
        double y = p2.getY() + dist * Math.sin(bisect);
        // REMIND: This needs some work...
        if (ang1 < ang2) {
            ang1 -= Math.PI / 2.0;
            ang2 += Math.PI / 2.0;
        } else {
            ang1 += Math.PI / 2.0;
            ang2 -= Math.PI / 2.0;
        }
        ang1 = Math.toDegrees(-ang1);
        ang2 = Math.toDegrees(-ang2);
        diff = ang2 - ang1;
        if (diff < 0) {
            diff += 360;
        } else {
            diff -= 360;
        }
        setArcByCenter(x, y, radius, ang1, diff, type);
    }

    /**
     * Sets the starting angle of this arc to the specified double
     * value.
     *
     * <p>
     *  将此弧的起始角度设置为指定的double值。
     * 
     * 
     * @param angSt The starting angle of the arc in degrees.
     * @see #getAngleStart
     * @since 1.2
     */
    public abstract void setAngleStart(double angSt);

    /**
     * Sets the angular extent of this arc to the specified double
     * value.
     *
     * <p>
     *  将此弧的角度范围设置为指定的double值。
     * 
     * 
     * @param angExt The angular extent of the arc in degrees.
     * @see #getAngleExtent
     * @since 1.2
     */
    public abstract void setAngleExtent(double angExt);

    /**
     * Sets the starting angle of this arc to the angle that the
     * specified point defines relative to the center of this arc.
     * The angular extent of the arc will remain the same.
     *
     * <p>
     *  将此弧的起始角度设置为指定点相对于此弧的中心定义的角度。弧的角度范围将保持相同。
     * 
     * 
     * @param p The <CODE>Point2D</CODE> that defines the starting angle.
     * @see #getAngleStart
     * @since 1.2
     */
    public void setAngleStart(Point2D p) {
        // Bias the dx and dy by the height and width of the oval.
        double dx = getHeight() * (p.getX() - getCenterX());
        double dy = getWidth() * (p.getY() - getCenterY());
        setAngleStart(-Math.toDegrees(Math.atan2(dy, dx)));
    }

    /**
     * Sets the starting angle and angular extent of this arc using two
     * sets of coordinates. The first set of coordinates is used to
     * determine the angle of the starting point relative to the arc's
     * center. The second set of coordinates is used to determine the
     * angle of the end point relative to the arc's center.
     * The arc will always be non-empty and extend counterclockwise
     * from the first point around to the second point.
     *
     * <p>
     *  使用两组坐标设置此弧的起始角度和角度范围。第一组坐标用于确定起点相对于弧的中心的角度。第二组坐标用于确定端点相对于弧的中心的角度。圆弧将永远是非空的,并从第一点逆时针延伸到第二点。
     * 
     * 
     * @param x1 The X coordinate of the arc's starting point.
     * @param y1 The Y coordinate of the arc's starting point.
     * @param x2 The X coordinate of the arc's ending point.
     * @param y2 The Y coordinate of the arc's ending point.
     * @since 1.2
     */
    public void setAngles(double x1, double y1, double x2, double y2) {
        double x = getCenterX();
        double y = getCenterY();
        double w = getWidth();
        double h = getHeight();
        // Note: reversing the Y equations negates the angle to adjust
        // for the upside down coordinate system.
        // Also we should bias atans by the height and width of the oval.
        double ang1 = Math.atan2(w * (y - y1), h * (x1 - x));
        double ang2 = Math.atan2(w * (y - y2), h * (x2 - x));
        ang2 -= ang1;
        if (ang2 <= 0.0) {
            ang2 += Math.PI * 2.0;
        }
        setAngleStart(Math.toDegrees(ang1));
        setAngleExtent(Math.toDegrees(ang2));
    }

    /**
     * Sets the starting angle and angular extent of this arc using
     * two points. The first point is used to determine the angle of
     * the starting point relative to the arc's center.
     * The second point is used to determine the angle of the end point
     * relative to the arc's center.
     * The arc will always be non-empty and extend counterclockwise
     * from the first point around to the second point.
     *
     * <p>
     *  使用两点设置此弧的起始角度和角度范围。第一点用于确定起点相对于圆弧中心的角度。第二点用于确定端点相对于圆弧中心的角度。圆弧将永远是非空的,并从第一点逆时针延伸到第二点。
     * 
     * 
     * @param p1 The <CODE>Point2D</CODE> that defines the arc's
     * starting point.
     * @param p2 The <CODE>Point2D</CODE> that defines the arc's
     * ending point.
     * @since 1.2
     */
    public void setAngles(Point2D p1, Point2D p2) {
        setAngles(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    /**
     * Sets the closure type of this arc to the specified value:
     * <CODE>OPEN</CODE>, <CODE>CHORD</CODE>, or <CODE>PIE</CODE>.
     *
     * <p>
     *  将此弧的闭合类型设置为指定的值：<CODE> OPEN </CODE>,<CODE> CHORD </CODE>或<CODE> PIE </CODE>。
     * 
     * 
     * @param type The integer constant that represents the closure
     * type of this arc: {@link #OPEN}, {@link #CHORD}, or
     * {@link #PIE}.
     *
     * @throws IllegalArgumentException if <code>type</code> is not
     * 0, 1, or 2.+
     * @see #getArcType
     * @since 1.2
     */
    public void setArcType(int type) {
        if (type < OPEN || type > PIE) {
            throw new IllegalArgumentException("invalid type for Arc: "+type);
        }
        this.type = type;
    }

    /**
     * {@inheritDoc}
     * Note that the arc
     * <a href="Arc2D.html#inscribes">partially inscribes</a>
     * the framing rectangle of this {@code RectangularShape}.
     *
     * <p>
     * {@inheritDoc}请注意,弧{<a href="Arc2D.html#inscribes">部分内接</a>此{@code RectangularShape}的框架矩形。
     * 
     * 
     * @since 1.2
     */
    public void setFrame(double x, double y, double w, double h) {
        setArc(x, y, w, h, getAngleStart(), getAngleExtent(), type);
    }

    /**
     * Returns the high-precision framing rectangle of the arc.  The framing
     * rectangle contains only the part of this <code>Arc2D</code> that is
     * in between the starting and ending angles and contains the pie
     * wedge, if this <code>Arc2D</code> has a <code>PIE</code> closure type.
     * <p>
     * This method differs from the
     * {@link RectangularShape#getBounds() getBounds} in that the
     * <code>getBounds</code> method only returns the bounds of the
     * enclosing ellipse of this <code>Arc2D</code> without considering
     * the starting and ending angles of this <code>Arc2D</code>.
     *
     * <p>
     *  返回圆弧的高精度框架矩形。
     * 框架矩形只包含<code> Arc2D </code>中位于起始角和结束角之间的部分,并且包含饼形图,如果此<code> Arc2D </code>有一个<code> PIE <代码>闭包类型。
     * <p>
     *  此方法与{@link RectangularShape#getBounds()getBounds}不同,因为<code> getBounds </code>方法仅返回此<code> Arc2D </code>
     * 的封闭椭圆的边界,这个<code> Arc2D </code>的结束角度。
     * 
     * 
     * @return the <CODE>Rectangle2D</CODE> that represents the arc's
     * framing rectangle.
     * @since 1.2
     */
    public Rectangle2D getBounds2D() {
        if (isEmpty()) {
            return makeBounds(getX(), getY(), getWidth(), getHeight());
        }
        double x1, y1, x2, y2;
        if (getArcType() == PIE) {
            x1 = y1 = x2 = y2 = 0.0;
        } else {
            x1 = y1 = 1.0;
            x2 = y2 = -1.0;
        }
        double angle = 0.0;
        for (int i = 0; i < 6; i++) {
            if (i < 4) {
                // 0-3 are the four quadrants
                angle += 90.0;
                if (!containsAngle(angle)) {
                    continue;
                }
            } else if (i == 4) {
                // 4 is start angle
                angle = getAngleStart();
            } else {
                // 5 is end angle
                angle += getAngleExtent();
            }
            double rads = Math.toRadians(-angle);
            double xe = Math.cos(rads);
            double ye = Math.sin(rads);
            x1 = Math.min(x1, xe);
            y1 = Math.min(y1, ye);
            x2 = Math.max(x2, xe);
            y2 = Math.max(y2, ye);
        }
        double w = getWidth();
        double h = getHeight();
        x2 = (x2 - x1) * 0.5 * w;
        y2 = (y2 - y1) * 0.5 * h;
        x1 = getX() + (x1 * 0.5 + 0.5) * w;
        y1 = getY() + (y1 * 0.5 + 0.5) * h;
        return makeBounds(x1, y1, x2, y2);
    }

    /**
     * Constructs a <code>Rectangle2D</code> of the appropriate precision
     * to hold the parameters calculated to be the framing rectangle
     * of this arc.
     *
     * <p>
     *  构造适当精度的<code> Rectangle2D </code>,以保存计算为此弧的框架矩形的参数。
     * 
     * 
     * @param x The X coordinate of the upper-left corner of the
     * framing rectangle.
     * @param y The Y coordinate of the upper-left corner of the
     * framing rectangle.
     * @param w The width of the framing rectangle.
     * @param h The height of the framing rectangle.
     * @return a <code>Rectangle2D</code> that is the framing rectangle
     *     of this arc.
     * @since 1.2
     */
    protected abstract Rectangle2D makeBounds(double x, double y,
                                              double w, double h);

    /*
     * Normalizes the specified angle into the range -180 to 180.
     * <p>
     *  将指定的角度标准化到-180到180的范围内。
     * 
     */
    static double normalizeDegrees(double angle) {
        if (angle > 180.0) {
            if (angle <= (180.0 + 360.0)) {
                angle = angle - 360.0;
            } else {
                angle = Math.IEEEremainder(angle, 360.0);
                // IEEEremainder can return -180 here for some input values...
                if (angle == -180.0) {
                    angle = 180.0;
                }
            }
        } else if (angle <= -180.0) {
            if (angle > (-180.0 - 360.0)) {
                angle = angle + 360.0;
            } else {
                angle = Math.IEEEremainder(angle, 360.0);
                // IEEEremainder can return -180 here for some input values...
                if (angle == -180.0) {
                    angle = 180.0;
                }
            }
        }
        return angle;
    }

    /**
     * Determines whether or not the specified angle is within the
     * angular extents of the arc.
     *
     * <p>
     *  确定指定的角度是否在弧的角度范围内。
     * 
     * 
     * @param angle The angle to test.
     *
     * @return <CODE>true</CODE> if the arc contains the angle,
     * <CODE>false</CODE> if the arc doesn't contain the angle.
     * @since 1.2
     */
    public boolean containsAngle(double angle) {
        double angExt = getAngleExtent();
        boolean backwards = (angExt < 0.0);
        if (backwards) {
            angExt = -angExt;
        }
        if (angExt >= 360.0) {
            return true;
        }
        angle = normalizeDegrees(angle) - normalizeDegrees(getAngleStart());
        if (backwards) {
            angle = -angle;
        }
        if (angle < 0.0) {
            angle += 360.0;
        }


        return (angle >= 0.0) && (angle < angExt);
    }

    /**
     * Determines whether or not the specified point is inside the boundary
     * of the arc.
     *
     * <p>
     *  确定指定点是否在圆弧的边界内。
     * 
     * 
     * @param x The X coordinate of the point to test.
     * @param y The Y coordinate of the point to test.
     *
     * @return <CODE>true</CODE> if the point lies within the bound of
     * the arc, <CODE>false</CODE> if the point lies outside of the
     * arc's bounds.
     * @since 1.2
     */
    public boolean contains(double x, double y) {
        // Normalize the coordinates compared to the ellipse
        // having a center at 0,0 and a radius of 0.5.
        double ellw = getWidth();
        if (ellw <= 0.0) {
            return false;
        }
        double normx = (x - getX()) / ellw - 0.5;
        double ellh = getHeight();
        if (ellh <= 0.0) {
            return false;
        }
        double normy = (y - getY()) / ellh - 0.5;
        double distSq = (normx * normx + normy * normy);
        if (distSq >= 0.25) {
            return false;
        }
        double angExt = Math.abs(getAngleExtent());
        if (angExt >= 360.0) {
            return true;
        }
        boolean inarc = containsAngle(-Math.toDegrees(Math.atan2(normy,
                                                                 normx)));
        if (type == PIE) {
            return inarc;
        }
        // CHORD and OPEN behave the same way
        if (inarc) {
            if (angExt >= 180.0) {
                return true;
            }
            // point must be outside the "pie triangle"
        } else {
            if (angExt <= 180.0) {
                return false;
            }
            // point must be inside the "pie triangle"
        }
        // The point is inside the pie triangle iff it is on the same
        // side of the line connecting the ends of the arc as the center.
        double angle = Math.toRadians(-getAngleStart());
        double x1 = Math.cos(angle);
        double y1 = Math.sin(angle);
        angle += Math.toRadians(-getAngleExtent());
        double x2 = Math.cos(angle);
        double y2 = Math.sin(angle);
        boolean inside = (Line2D.relativeCCW(x1, y1, x2, y2, 2*normx, 2*normy) *
                          Line2D.relativeCCW(x1, y1, x2, y2, 0, 0) >= 0);
        return inarc ? !inside : inside;
    }

    /**
     * Determines whether or not the interior of the arc intersects
     * the interior of the specified rectangle.
     *
     * <p>
     *  确定圆弧内部是否与指定矩形内部相交。
     * 
     * 
     * @param x The X coordinate of the rectangle's upper-left corner.
     * @param y The Y coordinate of the rectangle's upper-left corner.
     * @param w The width of the rectangle.
     * @param h The height of the rectangle.
     *
     * @return <CODE>true</CODE> if the arc intersects the rectangle,
     * <CODE>false</CODE> if the arc doesn't intersect the rectangle.
     * @since 1.2
     */
    public boolean intersects(double x, double y, double w, double h) {

        double aw = getWidth();
        double ah = getHeight();

        if ( w <= 0 || h <= 0 || aw <= 0 || ah <= 0 ) {
            return false;
        }
        double ext = getAngleExtent();
        if (ext == 0) {
            return false;
        }

        double ax  = getX();
        double ay  = getY();
        double axw = ax + aw;
        double ayh = ay + ah;
        double xw  = x + w;
        double yh  = y + h;

        // check bbox
        if (x >= axw || y >= ayh || xw <= ax || yh <= ay) {
            return false;
        }

        // extract necessary data
        double axc = getCenterX();
        double ayc = getCenterY();
        Point2D sp = getStartPoint();
        Point2D ep = getEndPoint();
        double sx = sp.getX();
        double sy = sp.getY();
        double ex = ep.getX();
        double ey = ep.getY();

        /*
         * Try to catch rectangles that intersect arc in areas
         * outside of rectagle with left top corner coordinates
         * (min(center x, start point x, end point x),
         *  min(center y, start point y, end point y))
         * and rigth bottom corner coordinates
         * (max(center x, start point x, end point x),
         *  max(center y, start point y, end point y)).
         * So we'll check axis segments outside of rectangle above.
         * <p>
         * 尝试抓住在具有左顶角坐标(min(中心x,起点x,终点x),min(中心y,起点y,终点y))和直角底角之外的区域中与弧相交的矩形坐标(max(中心x,起点x,终点x),max(中心y,起点y,终点y)
         * )。
         * 因此,我们将检查上面矩形以外的轴段。
         * 
         */
        if (ayc >= y && ayc <= yh) { // 0 and 180
            if ((sx < xw && ex < xw && axc < xw &&
                 axw > x && containsAngle(0)) ||
                (sx > x && ex > x && axc > x &&
                 ax < xw && containsAngle(180))) {
                return true;
            }
        }
        if (axc >= x && axc <= xw) { // 90 and 270
            if ((sy > y && ey > y && ayc > y &&
                 ay < yh && containsAngle(90)) ||
                (sy < yh && ey < yh && ayc < yh &&
                 ayh > y && containsAngle(270))) {
                return true;
            }
        }

        /*
         * For PIE we should check intersection with pie slices;
         * also we should do the same for arcs with extent is greater
         * than 180, because we should cover case of rectangle, which
         * situated between center of arc and chord, but does not
         * intersect the chord.
         * <p>
         *  对于PIE,我们应该用饼图检查交叉;对于弧度大于180的弧也应该这样做,因为我们应该覆盖位于弧和中心之间但不与弦交叉的矩形的情况。
         * 
         */
        Rectangle2D rect = new Rectangle2D.Double(x, y, w, h);
        if (type == PIE || Math.abs(ext) > 180) {
            // for PIE: try to find intersections with pie slices
            if (rect.intersectsLine(axc, ayc, sx, sy) ||
                rect.intersectsLine(axc, ayc, ex, ey)) {
                return true;
            }
        } else {
            // for CHORD and OPEN: try to find intersections with chord
            if (rect.intersectsLine(sx, sy, ex, ey)) {
                return true;
            }
        }

        // finally check the rectangle corners inside the arc
        if (contains(x, y) || contains(x + w, y) ||
            contains(x, y + h) || contains(x + w, y + h)) {
            return true;
        }

        return false;
    }

    /**
     * Determines whether or not the interior of the arc entirely contains
     * the specified rectangle.
     *
     * <p>
     *  确定圆弧内部是否完全包含指定的矩形。
     * 
     * 
     * @param x The X coordinate of the rectangle's upper-left corner.
     * @param y The Y coordinate of the rectangle's upper-left corner.
     * @param w The width of the rectangle.
     * @param h The height of the rectangle.
     *
     * @return <CODE>true</CODE> if the arc contains the rectangle,
     * <CODE>false</CODE> if the arc doesn't contain the rectangle.
     * @since 1.2
     */
    public boolean contains(double x, double y, double w, double h) {
        return contains(x, y, w, h, null);
    }

    /**
     * Determines whether or not the interior of the arc entirely contains
     * the specified rectangle.
     *
     * <p>
     *  确定圆弧内部是否完全包含指定的矩形。
     * 
     * 
     * @param r The <CODE>Rectangle2D</CODE> to test.
     *
     * @return <CODE>true</CODE> if the arc contains the rectangle,
     * <CODE>false</CODE> if the arc doesn't contain the rectangle.
     * @since 1.2
     */
    public boolean contains(Rectangle2D r) {
        return contains(r.getX(), r.getY(), r.getWidth(), r.getHeight(), r);
    }

    private boolean contains(double x, double y, double w, double h,
                             Rectangle2D origrect) {
        if (!(contains(x, y) &&
              contains(x + w, y) &&
              contains(x, y + h) &&
              contains(x + w, y + h))) {
            return false;
        }
        // If the shape is convex then we have done all the testing
        // we need.  Only PIE arcs can be concave and then only if
        // the angular extents are greater than 180 degrees.
        if (type != PIE || Math.abs(getAngleExtent()) <= 180.0) {
            return true;
        }
        // For a PIE shape we have an additional test for the case where
        // the angular extents are greater than 180 degrees and all four
        // rectangular corners are inside the shape but one of the
        // rectangle edges spans across the "missing wedge" of the arc.
        // We can test for this case by checking if the rectangle intersects
        // either of the pie angle segments.
        if (origrect == null) {
            origrect = new Rectangle2D.Double(x, y, w, h);
        }
        double halfW = getWidth() / 2.0;
        double halfH = getHeight() / 2.0;
        double xc = getX() + halfW;
        double yc = getY() + halfH;
        double angle = Math.toRadians(-getAngleStart());
        double xe = xc + halfW * Math.cos(angle);
        double ye = yc + halfH * Math.sin(angle);
        if (origrect.intersectsLine(xc, yc, xe, ye)) {
            return false;
        }
        angle += Math.toRadians(-getAngleExtent());
        xe = xc + halfW * Math.cos(angle);
        ye = yc + halfH * Math.sin(angle);
        return !origrect.intersectsLine(xc, yc, xe, ye);
    }

    /**
     * Returns an iteration object that defines the boundary of the
     * arc.
     * This iterator is multithread safe.
     * <code>Arc2D</code> guarantees that
     * modifications to the geometry of the arc
     * do not affect any iterations of that geometry that
     * are already in process.
     *
     * <p>
     *  返回定义弧的边界的迭代对象。这个迭代器是多线程安全的。 <code> Arc2D </code>保证对弧几何的修改不会影响已经在处理中的几何的任何迭代。
     * 
     * 
     * @param at an optional <CODE>AffineTransform</CODE> to be applied
     * to the coordinates as they are returned in the iteration, or null
     * if the untransformed coordinates are desired.
     *
     * @return A <CODE>PathIterator</CODE> that defines the arc's boundary.
     * @since 1.2
     */
    public PathIterator getPathIterator(AffineTransform at) {
        return new ArcIterator(this, at);
    }

    /**
     * Returns the hashcode for this <code>Arc2D</code>.
     * <p>
     *  返回此<code> Arc2D </code>的哈希码。
     * 
     * 
     * @return the hashcode for this <code>Arc2D</code>.
     * @since 1.6
     */
    public int hashCode() {
        long bits = java.lang.Double.doubleToLongBits(getX());
        bits += java.lang.Double.doubleToLongBits(getY()) * 37;
        bits += java.lang.Double.doubleToLongBits(getWidth()) * 43;
        bits += java.lang.Double.doubleToLongBits(getHeight()) * 47;
        bits += java.lang.Double.doubleToLongBits(getAngleStart()) * 53;
        bits += java.lang.Double.doubleToLongBits(getAngleExtent()) * 59;
        bits += getArcType() * 61;
        return (((int) bits) ^ ((int) (bits >> 32)));
    }

    /**
     * Determines whether or not the specified <code>Object</code> is
     * equal to this <code>Arc2D</code>.  The specified
     * <code>Object</code> is equal to this <code>Arc2D</code>
     * if it is an instance of <code>Arc2D</code> and if its
     * location, size, arc extents and type are the same as this
     * <code>Arc2D</code>.
     * <p>
     *  确定指定的<code> Object </code>是否等于此<code> Arc2D </code>。
     * 如果它是<code> Arc2D </code>的一个实例,并且它的位置,大小,弧度范围和类型相同,则指定的<code> Object </code>等于此<code> Arc2D </code>作为<code>
     *  Arc2D </code>。
     * 
     * @param obj  an <code>Object</code> to be compared with this
     *             <code>Arc2D</code>.
     * @return  <code>true</code> if <code>obj</code> is an instance
     *          of <code>Arc2D</code> and has the same values;
     *          <code>false</code> otherwise.
     * @since 1.6
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Arc2D) {
            Arc2D a2d = (Arc2D) obj;
            return ((getX() == a2d.getX()) &&
                    (getY() == a2d.getY()) &&
                    (getWidth() == a2d.getWidth()) &&
                    (getHeight() == a2d.getHeight()) &&
                    (getAngleStart() == a2d.getAngleStart()) &&
                    (getAngleExtent() == a2d.getAngleExtent()) &&
                    (getArcType() == a2d.getArcType()));
        }
        return false;
    }
}
