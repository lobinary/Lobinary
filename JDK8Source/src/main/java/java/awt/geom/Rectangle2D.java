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
 * The <code>Rectangle2D</code> class describes a rectangle
 * defined by a location {@code (x,y)} and dimension
 * {@code (w x h)}.
 * <p>
 * This class is only the abstract superclass for all objects that
 * store a 2D rectangle.
 * The actual storage representation of the coordinates is left to
 * the subclass.
 *
 * <p>
 *  <code> Rectangle2D </code>类描述了由位置{@code(x,y)}和维度{@code(w x h)}定义的矩形。
 * <p>
 *  这个类只是存储2D矩形的所有对象的抽象超类。坐标的实际存储表示是留给子类的。
 * 
 * 
 * @author      Jim Graham
 * @since 1.2
 */
public abstract class Rectangle2D extends RectangularShape {
    /**
     * The bitmask that indicates that a point lies to the left of
     * this <code>Rectangle2D</code>.
     * <p>
     *  指示一个点位于此<code> Rectangle2D </code>左侧的位掩码。
     * 
     * 
     * @since 1.2
     */
    public static final int OUT_LEFT = 1;

    /**
     * The bitmask that indicates that a point lies above
     * this <code>Rectangle2D</code>.
     * <p>
     *  指示点位于此<code> Rectangle2D </code>上方的位掩码。
     * 
     * 
     * @since 1.2
     */
    public static final int OUT_TOP = 2;

    /**
     * The bitmask that indicates that a point lies to the right of
     * this <code>Rectangle2D</code>.
     * <p>
     *  指示点位于此<code> Rectangle2D </code>右侧的位掩码。
     * 
     * 
     * @since 1.2
     */
    public static final int OUT_RIGHT = 4;

    /**
     * The bitmask that indicates that a point lies below
     * this <code>Rectangle2D</code>.
     * <p>
     *  指示点位于此<code> Rectangle2D </code>下方的位掩码。
     * 
     * 
     * @since 1.2
     */
    public static final int OUT_BOTTOM = 8;

    /**
     * The <code>Float</code> class defines a rectangle specified in float
     * coordinates.
     * <p>
     *  <code> Float </code>类定义了在float坐标中指定的矩形。
     * 
     * 
     * @since 1.2
     */
    public static class Float extends Rectangle2D implements Serializable {
        /**
         * The X coordinate of this <code>Rectangle2D</code>.
         * <p>
         *  此<code> Rectangle2D </code>的X坐标。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public float x;

        /**
         * The Y coordinate of this <code>Rectangle2D</code>.
         * <p>
         *  此<code> Rectangle2D </code>的Y坐标。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public float y;

        /**
         * The width of this <code>Rectangle2D</code>.
         * <p>
         *  此<code> Rectangle2D </code>的宽度。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public float width;

        /**
         * The height of this <code>Rectangle2D</code>.
         * <p>
         *  这个<code> Rectangle2D </code>的高度。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public float height;

        /**
         * Constructs a new <code>Rectangle2D</code>, initialized to
         * location (0.0,&nbsp;0.0) and size (0.0,&nbsp;0.0).
         * <p>
         *  构造一个新的<code> Rectangle2D </code>,初始化为位置(0.0,&nbsp; 0.0)和大小(0.0,&nbsp; 0.0)。
         * 
         * 
         * @since 1.2
         */
        public Float() {
        }

        /**
         * Constructs and initializes a <code>Rectangle2D</code>
         * from the specified <code>float</code> coordinates.
         *
         * <p>
         *  从指定的<code> float </code>坐标构造并初始化<code> Rectangle2D </code>。
         * 
         * 
         * @param x the X coordinate of the upper-left corner
         *          of the newly constructed <code>Rectangle2D</code>
         * @param y the Y coordinate of the upper-left corner
         *          of the newly constructed <code>Rectangle2D</code>
         * @param w the width of the newly constructed
         *          <code>Rectangle2D</code>
         * @param h the height of the newly constructed
         *          <code>Rectangle2D</code>
         * @since 1.2
        */
        public Float(float x, float y, float w, float h) {
            setRect(x, y, w, h);
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
        public boolean isEmpty() {
            return (width <= 0.0f) || (height <= 0.0f);
        }

        /**
         * Sets the location and size of this <code>Rectangle2D</code>
         * to the specified <code>float</code> values.
         *
         * <p>
         *  将<code> Rectangle2D </code>的位置和大小设置为指定的<code> float </code>值。
         * 
         * 
         * @param x the X coordinate of the upper-left corner
         *          of this <code>Rectangle2D</code>
         * @param y the Y coordinate of the upper-left corner
         *          of this <code>Rectangle2D</code>
         * @param w the width of this <code>Rectangle2D</code>
         * @param h the height of this <code>Rectangle2D</code>
         * @since 1.2
         */
        public void setRect(float x, float y, float w, float h) {
            this.x = x;
            this.y = y;
            this.width = w;
            this.height = h;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public void setRect(double x, double y, double w, double h) {
            this.x = (float) x;
            this.y = (float) y;
            this.width = (float) w;
            this.height = (float) h;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public void setRect(Rectangle2D r) {
            this.x = (float) r.getX();
            this.y = (float) r.getY();
            this.width = (float) r.getWidth();
            this.height = (float) r.getHeight();
        }

        /**
         * {@inheritDoc}
         * <p>
         * {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public int outcode(double x, double y) {
            /*
             * Note on casts to double below.  If the arithmetic of
             * x+w or y+h is done in float, then some bits may be
             * lost if the binary exponents of x/y and w/h are not
             * similar.  By converting to double before the addition
             * we force the addition to be carried out in double to
             * avoid rounding error in the comparison.
             *
             * See bug 4320890 for problems that this inaccuracy causes.
             * <p>
             *  注意转换为双下面。如果x + w或y + h的算术在float中进行,则如果x / y和w / h的二进制指数不相似,则一些比特可能丢失。
             * 通过在加法之前转换为双倍,我们强制加法以双倍进行,以避免在比较中的舍入误差。
             * 
             *  请参阅错误4320890,了解此错误导致的问题。
             * 
             */
            int out = 0;
            if (this.width <= 0) {
                out |= OUT_LEFT | OUT_RIGHT;
            } else if (x < this.x) {
                out |= OUT_LEFT;
            } else if (x > this.x + (double) this.width) {
                out |= OUT_RIGHT;
            }
            if (this.height <= 0) {
                out |= OUT_TOP | OUT_BOTTOM;
            } else if (y < this.y) {
                out |= OUT_TOP;
            } else if (y > this.y + (double) this.height) {
                out |= OUT_BOTTOM;
            }
            return out;
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
            return new Float(x, y, width, height);
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public Rectangle2D createIntersection(Rectangle2D r) {
            Rectangle2D dest;
            if (r instanceof Float) {
                dest = new Rectangle2D.Float();
            } else {
                dest = new Rectangle2D.Double();
            }
            Rectangle2D.intersect(this, r, dest);
            return dest;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public Rectangle2D createUnion(Rectangle2D r) {
            Rectangle2D dest;
            if (r instanceof Float) {
                dest = new Rectangle2D.Float();
            } else {
                dest = new Rectangle2D.Double();
            }
            Rectangle2D.union(this, r, dest);
            return dest;
        }

        /**
         * Returns the <code>String</code> representation of this
         * <code>Rectangle2D</code>.
         * <p>
         *  返回此代码<code> Rectangle2D </code>的<code> String </code>表示形式。
         * 
         * 
         * @return a <code>String</code> representing this
         * <code>Rectangle2D</code>.
         * @since 1.2
         */
        public String toString() {
            return getClass().getName()
                + "[x=" + x +
                ",y=" + y +
                ",w=" + width +
                ",h=" + height + "]";
        }

        /*
         * JDK 1.6 serialVersionUID
         * <p>
         *  JDK 1.6 serialVersionUID
         * 
         */
        private static final long serialVersionUID = 3798716824173675777L;
    }

    /**
     * The <code>Double</code> class defines a rectangle specified in
     * double coordinates.
     * <p>
     *  <code> Double </code>类定义在双坐标中指定的矩形。
     * 
     * 
     * @since 1.2
     */
    public static class Double extends Rectangle2D implements Serializable {
        /**
         * The X coordinate of this <code>Rectangle2D</code>.
         * <p>
         *  此<code> Rectangle2D </code>的X坐标。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public double x;

        /**
         * The Y coordinate of this <code>Rectangle2D</code>.
         * <p>
         *  此<code> Rectangle2D </code>的Y坐标。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public double y;

        /**
         * The width of this <code>Rectangle2D</code>.
         * <p>
         *  此<code> Rectangle2D </code>的宽度。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public double width;

        /**
         * The height of this <code>Rectangle2D</code>.
         * <p>
         *  这个<code> Rectangle2D </code>的高度。
         * 
         * 
         * @since 1.2
         * @serial
         */
        public double height;

        /**
         * Constructs a new <code>Rectangle2D</code>, initialized to
         * location (0,&nbsp;0) and size (0,&nbsp;0).
         * <p>
         *  构造一个新的<code> Rectangle2D </code>,初始化为位置(0,&nbsp; 0)和大小(0,&nbsp; 0)。
         * 
         * 
         * @since 1.2
         */
        public Double() {
        }

        /**
         * Constructs and initializes a <code>Rectangle2D</code>
         * from the specified <code>double</code> coordinates.
         *
         * <p>
         *  从指定的<code> double </code>坐标构造并初始化<code> Rectangle2D </code>。
         * 
         * 
         * @param x the X coordinate of the upper-left corner
         *          of the newly constructed <code>Rectangle2D</code>
         * @param y the Y coordinate of the upper-left corner
         *          of the newly constructed <code>Rectangle2D</code>
         * @param w the width of the newly constructed
         *          <code>Rectangle2D</code>
         * @param h the height of the newly constructed
         *          <code>Rectangle2D</code>
         * @since 1.2
         */
        public Double(double x, double y, double w, double h) {
            setRect(x, y, w, h);
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
        public boolean isEmpty() {
            return (width <= 0.0) || (height <= 0.0);
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public void setRect(double x, double y, double w, double h) {
            this.x = x;
            this.y = y;
            this.width = w;
            this.height = h;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public void setRect(Rectangle2D r) {
            this.x = r.getX();
            this.y = r.getY();
            this.width = r.getWidth();
            this.height = r.getHeight();
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public int outcode(double x, double y) {
            int out = 0;
            if (this.width <= 0) {
                out |= OUT_LEFT | OUT_RIGHT;
            } else if (x < this.x) {
                out |= OUT_LEFT;
            } else if (x > this.x + this.width) {
                out |= OUT_RIGHT;
            }
            if (this.height <= 0) {
                out |= OUT_TOP | OUT_BOTTOM;
            } else if (y < this.y) {
                out |= OUT_TOP;
            } else if (y > this.y + this.height) {
                out |= OUT_BOTTOM;
            }
            return out;
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
            return new Double(x, y, width, height);
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public Rectangle2D createIntersection(Rectangle2D r) {
            Rectangle2D dest = new Rectangle2D.Double();
            Rectangle2D.intersect(this, r, dest);
            return dest;
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @since 1.2
         */
        public Rectangle2D createUnion(Rectangle2D r) {
            Rectangle2D dest = new Rectangle2D.Double();
            Rectangle2D.union(this, r, dest);
            return dest;
        }

        /**
         * Returns the <code>String</code> representation of this
         * <code>Rectangle2D</code>.
         * <p>
         *  返回此<code> Rectangle2D </code>的<code> String </code>表示形式。
         * 
         * 
         * @return a <code>String</code> representing this
         * <code>Rectangle2D</code>.
         * @since 1.2
         */
        public String toString() {
            return getClass().getName()
                + "[x=" + x +
                ",y=" + y +
                ",w=" + width +
                ",h=" + height + "]";
        }

        /*
         * JDK 1.6 serialVersionUID
         * <p>
         *  JDK 1.6 serialVersionUID
         * 
         */
        private static final long serialVersionUID = 7771313791441850493L;
    }

    /**
     * This is an abstract class that cannot be instantiated directly.
     * Type-specific implementation subclasses are available for
     * instantiation and provide a number of formats for storing
     * the information necessary to satisfy the various accessor
     * methods below.
     *
     * <p>
     * 这是一个不能直接实例化的抽象类。类型特定的实现子类可用于实例化并且提供用于存储满足下面的各种存取器方法所必需的信息的多种格式。
     * 
     * 
     * @see java.awt.geom.Rectangle2D.Float
     * @see java.awt.geom.Rectangle2D.Double
     * @see java.awt.Rectangle
     * @since 1.2
     */
    protected Rectangle2D() {
    }

    /**
     * Sets the location and size of this <code>Rectangle2D</code>
     * to the specified <code>double</code> values.
     *
     * <p>
     *  将<code> Rectangle2D </code>的位置和大小设置为指定的<code> double </code>值。
     * 
     * 
     * @param x the X coordinate of the upper-left corner
     *          of this <code>Rectangle2D</code>
     * @param y the Y coordinate of the upper-left corner
     *          of this <code>Rectangle2D</code>
     * @param w the width of this <code>Rectangle2D</code>
     * @param h the height of this <code>Rectangle2D</code>
     * @since 1.2
     */
    public abstract void setRect(double x, double y, double w, double h);

    /**
     * Sets this <code>Rectangle2D</code> to be the same as the specified
     * <code>Rectangle2D</code>.
     * <p>
     *  将此<code> Rectangle2D </code>设置为与指定的<code> Rectangle2D </code>相同。
     * 
     * 
     * @param r the specified <code>Rectangle2D</code>
     * @since 1.2
     */
    public void setRect(Rectangle2D r) {
        setRect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    /**
     * Tests if the specified line segment intersects the interior of this
     * <code>Rectangle2D</code>.
     *
     * <p>
     *  测试指定的线段是否与此<code> Rectangle2D </code>的内部相交。
     * 
     * 
     * @param x1 the X coordinate of the start point of the specified
     *           line segment
     * @param y1 the Y coordinate of the start point of the specified
     *           line segment
     * @param x2 the X coordinate of the end point of the specified
     *           line segment
     * @param y2 the Y coordinate of the end point of the specified
     *           line segment
     * @return <code>true</code> if the specified line segment intersects
     * the interior of this <code>Rectangle2D</code>; <code>false</code>
     * otherwise.
     * @since 1.2
     */
    public boolean intersectsLine(double x1, double y1, double x2, double y2) {
        int out1, out2;
        if ((out2 = outcode(x2, y2)) == 0) {
            return true;
        }
        while ((out1 = outcode(x1, y1)) != 0) {
            if ((out1 & out2) != 0) {
                return false;
            }
            if ((out1 & (OUT_LEFT | OUT_RIGHT)) != 0) {
                double x = getX();
                if ((out1 & OUT_RIGHT) != 0) {
                    x += getWidth();
                }
                y1 = y1 + (x - x1) * (y2 - y1) / (x2 - x1);
                x1 = x;
            } else {
                double y = getY();
                if ((out1 & OUT_BOTTOM) != 0) {
                    y += getHeight();
                }
                x1 = x1 + (y - y1) * (x2 - x1) / (y2 - y1);
                y1 = y;
            }
        }
        return true;
    }

    /**
     * Tests if the specified line segment intersects the interior of this
     * <code>Rectangle2D</code>.
     * <p>
     *  测试指定的线段是否与此<code> Rectangle2D </code>的内部相交。
     * 
     * 
     * @param l the specified {@link Line2D} to test for intersection
     * with the interior of this <code>Rectangle2D</code>
     * @return <code>true</code> if the specified <code>Line2D</code>
     * intersects the interior of this <code>Rectangle2D</code>;
     * <code>false</code> otherwise.
     * @since 1.2
     */
    public boolean intersectsLine(Line2D l) {
        return intersectsLine(l.getX1(), l.getY1(), l.getX2(), l.getY2());
    }

    /**
     * Determines where the specified coordinates lie with respect
     * to this <code>Rectangle2D</code>.
     * This method computes a binary OR of the appropriate mask values
     * indicating, for each side of this <code>Rectangle2D</code>,
     * whether or not the specified coordinates are on the same side
     * of the edge as the rest of this <code>Rectangle2D</code>.
     * <p>
     *  确定指定的坐标相对于<code> Rectangle2D </code>的位置。
     * 该方法计算适当的掩码值的二进制OR,指示对于<code> Rectangle2D </code>的每一侧,指定的坐标是否与该<code> Rectangle2D的其余部分在边缘的同一侧</code>。
     * 
     * 
     * @param x the specified X coordinate
     * @param y the specified Y coordinate
     * @return the logical OR of all appropriate out codes.
     * @see #OUT_LEFT
     * @see #OUT_TOP
     * @see #OUT_RIGHT
     * @see #OUT_BOTTOM
     * @since 1.2
     */
    public abstract int outcode(double x, double y);

    /**
     * Determines where the specified {@link Point2D} lies with
     * respect to this <code>Rectangle2D</code>.
     * This method computes a binary OR of the appropriate mask values
     * indicating, for each side of this <code>Rectangle2D</code>,
     * whether or not the specified <code>Point2D</code> is on the same
     * side of the edge as the rest of this <code>Rectangle2D</code>.
     * <p>
     *  确定指定的{@link Point2D}相对于此<code> Rectangle2D </code>的位置。
     * 该方法计算适当掩码值的二进制OR,指示对于<code> Rectangle2D </code>的每一侧,指定的<code> Point2D </code>是否位于边的同一侧这个<code> Rectan
     * gle2D </code>的其余部分。
     *  确定指定的{@link Point2D}相对于此<code> Rectangle2D </code>的位置。
     * 
     * 
     * @param p the specified <code>Point2D</code>
     * @return the logical OR of all appropriate out codes.
     * @see #OUT_LEFT
     * @see #OUT_TOP
     * @see #OUT_RIGHT
     * @see #OUT_BOTTOM
     * @since 1.2
     */
    public int outcode(Point2D p) {
        return outcode(p.getX(), p.getY());
    }

    /**
     * Sets the location and size of the outer bounds of this
     * <code>Rectangle2D</code> to the specified rectangular values.
     *
     * <p>
     *  将<code> Rectangle2D </code>的外边界的位置和大小设置为指定的矩形值。
     * 
     * 
     * @param x the X coordinate of the upper-left corner
     *          of this <code>Rectangle2D</code>
     * @param y the Y coordinate of the upper-left corner
     *          of this <code>Rectangle2D</code>
     * @param w the width of this <code>Rectangle2D</code>
     * @param h the height of this <code>Rectangle2D</code>
     * @since 1.2
     */
    public void setFrame(double x, double y, double w, double h) {
        setRect(x, y, w, h);
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
        return (Rectangle2D) clone();
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
        double x0 = getX();
        double y0 = getY();
        return (x >= x0 &&
                y >= y0 &&
                x < x0 + getWidth() &&
                y < y0 + getHeight());
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
        double x0 = getX();
        double y0 = getY();
        return (x + w > x0 &&
                y + h > y0 &&
                x < x0 + getWidth() &&
                y < y0 + getHeight());
    }

    /**
     * {@inheritDoc}
     * <p>
     * {@inheritDoc}
     * 
     * 
     * @since 1.2
     */
    public boolean contains(double x, double y, double w, double h) {
        if (isEmpty() || w <= 0 || h <= 0) {
            return false;
        }
        double x0 = getX();
        double y0 = getY();
        return (x >= x0 &&
                y >= y0 &&
                (x + w) <= x0 + getWidth() &&
                (y + h) <= y0 + getHeight());
    }

    /**
     * Returns a new <code>Rectangle2D</code> object representing the
     * intersection of this <code>Rectangle2D</code> with the specified
     * <code>Rectangle2D</code>.
     * <p>
     *  返回一个新的<code> Rectangle2D </code>对象,表示此<code> Rectangle2D </code>与指定的<code> Rectangle2D </code>的交集。
     * 
     * 
     * @param r the <code>Rectangle2D</code> to be intersected with
     * this <code>Rectangle2D</code>
     * @return the largest <code>Rectangle2D</code> contained in both
     *          the specified <code>Rectangle2D</code> and in this
     *          <code>Rectangle2D</code>.
     * @since 1.2
     */
    public abstract Rectangle2D createIntersection(Rectangle2D r);

    /**
     * Intersects the pair of specified source <code>Rectangle2D</code>
     * objects and puts the result into the specified destination
     * <code>Rectangle2D</code> object.  One of the source rectangles
     * can also be the destination to avoid creating a third Rectangle2D
     * object, but in this case the original points of this source
     * rectangle will be overwritten by this method.
     * <p>
     *  交叉一对指定的源<code> Rectangle2D </code>对象,并将结果放入指定的目标<code> Rectangle2D </code>对象。
     * 源矩形之一也可以是目标,以避免创建第三个Rectangle2D对象,但在这种情况下,此源矩形的原始点将被此方法覆盖。
     * 
     * 
     * @param src1 the first of a pair of <code>Rectangle2D</code>
     * objects to be intersected with each other
     * @param src2 the second of a pair of <code>Rectangle2D</code>
     * objects to be intersected with each other
     * @param dest the <code>Rectangle2D</code> that holds the
     * results of the intersection of <code>src1</code> and
     * <code>src2</code>
     * @since 1.2
     */
    public static void intersect(Rectangle2D src1,
                                 Rectangle2D src2,
                                 Rectangle2D dest) {
        double x1 = Math.max(src1.getMinX(), src2.getMinX());
        double y1 = Math.max(src1.getMinY(), src2.getMinY());
        double x2 = Math.min(src1.getMaxX(), src2.getMaxX());
        double y2 = Math.min(src1.getMaxY(), src2.getMaxY());
        dest.setFrame(x1, y1, x2-x1, y2-y1);
    }

    /**
     * Returns a new <code>Rectangle2D</code> object representing the
     * union of this <code>Rectangle2D</code> with the specified
     * <code>Rectangle2D</code>.
     * <p>
     *  返回一个代表此<code> Rectangle2D </code>与指定<code> Rectangle2D </code>的并集的新<代码> Rectangle2D </code>对象。
     * 
     * 
     * @param r the <code>Rectangle2D</code> to be combined with
     * this <code>Rectangle2D</code>
     * @return the smallest <code>Rectangle2D</code> containing both
     * the specified <code>Rectangle2D</code> and this
     * <code>Rectangle2D</code>.
     * @since 1.2
     */
    public abstract Rectangle2D createUnion(Rectangle2D r);

    /**
     * Unions the pair of source <code>Rectangle2D</code> objects
     * and puts the result into the specified destination
     * <code>Rectangle2D</code> object.  One of the source rectangles
     * can also be the destination to avoid creating a third Rectangle2D
     * object, but in this case the original points of this source
     * rectangle will be overwritten by this method.
     * <p>
     *  结合源对<code> Rectangle2D </code>对,并将结果放入指定的目标<code> Rectangle2D </code>对象中。
     * 源矩形之一也可以是目标,以避免创建第三个Rectangle2D对象,但在这种情况下,此源矩形的原始点将被此方法覆盖。
     * 
     * 
     * @param src1 the first of a pair of <code>Rectangle2D</code>
     * objects to be combined with each other
     * @param src2 the second of a pair of <code>Rectangle2D</code>
     * objects to be combined with each other
     * @param dest the <code>Rectangle2D</code> that holds the
     * results of the union of <code>src1</code> and
     * <code>src2</code>
     * @since 1.2
     */
    public static void union(Rectangle2D src1,
                             Rectangle2D src2,
                             Rectangle2D dest) {
        double x1 = Math.min(src1.getMinX(), src2.getMinX());
        double y1 = Math.min(src1.getMinY(), src2.getMinY());
        double x2 = Math.max(src1.getMaxX(), src2.getMaxX());
        double y2 = Math.max(src1.getMaxY(), src2.getMaxY());
        dest.setFrameFromDiagonal(x1, y1, x2, y2);
    }

    /**
     * Adds a point, specified by the double precision arguments
     * <code>newx</code> and <code>newy</code>, to this
     * <code>Rectangle2D</code>.  The resulting <code>Rectangle2D</code>
     * is the smallest <code>Rectangle2D</code> that
     * contains both the original <code>Rectangle2D</code> and the
     * specified point.
     * <p>
     * After adding a point, a call to <code>contains</code> with the
     * added point as an argument does not necessarily return
     * <code>true</code>. The <code>contains</code> method does not
     * return <code>true</code> for points on the right or bottom
     * edges of a rectangle. Therefore, if the added point falls on
     * the left or bottom edge of the enlarged rectangle,
     * <code>contains</code> returns <code>false</code> for that point.
     * <p>
     *  向<code> Rectangle2D </code>中添加由双精度参数<code> newx </code>和<code> newy </code>指定的点。
     * 生成的<code> Rectangle2D </code>是包含原始<code> Rectangle2D </code>和指定点的最小<code> Rectangle2D </code>。
     * <p>
     * 添加一个点之后,调用<code>包含</code>与添加的点作为参数不一定返回<code> true </code>。
     *  <code>包含</code>方法不会为矩形的右边或底边的点返回<code> true </code>。
     * 因此,如果添加的点落在放大的矩形的左边或下边缘,<code>包含</code>返回<code> false </code>。
     * 
     * 
     * @param newx the X coordinate of the new point
     * @param newy the Y coordinate of the new point
     * @since 1.2
     */
    public void add(double newx, double newy) {
        double x1 = Math.min(getMinX(), newx);
        double x2 = Math.max(getMaxX(), newx);
        double y1 = Math.min(getMinY(), newy);
        double y2 = Math.max(getMaxY(), newy);
        setRect(x1, y1, x2 - x1, y2 - y1);
    }

    /**
     * Adds the <code>Point2D</code> object <code>pt</code> to this
     * <code>Rectangle2D</code>.
     * The resulting <code>Rectangle2D</code> is the smallest
     * <code>Rectangle2D</code> that contains both the original
     * <code>Rectangle2D</code> and the specified <code>Point2D</code>.
     * <p>
     * After adding a point, a call to <code>contains</code> with the
     * added point as an argument does not necessarily return
     * <code>true</code>. The <code>contains</code>
     * method does not return <code>true</code> for points on the right
     * or bottom edges of a rectangle. Therefore, if the added point falls
     * on the left or bottom edge of the enlarged rectangle,
     * <code>contains</code> returns <code>false</code> for that point.
     * <p>
     *  将<code> Point2D </code>对象<code> pt </code>添加到此<code> Rectangle2D </code>。
     * 结果<code> Rectangle2D </code>是包含原始<code> Rectangle2D </code>和指定的<code> Point2D </code>的最小<code> Rectan
     * gle2D </code>。
     *  将<code> Point2D </code>对象<code> pt </code>添加到此<code> Rectangle2D </code>。
     * <p>
     *  添加一个点之后,调用<code>包含</code>与添加的点作为参数不一定返回<code> true </code>。
     *  <code>包含</code>方法不会为矩形的右边或底边的点返回<code> true </code>。
     * 因此,如果添加的点落在放大的矩形的左边或下边缘,<code>包含</code>返回<code> false </code>。
     * 
     * 
     * @param     pt the new <code>Point2D</code> to add to this
     * <code>Rectangle2D</code>.
     * @since 1.2
     */
    public void add(Point2D pt) {
        add(pt.getX(), pt.getY());
    }

    /**
     * Adds a <code>Rectangle2D</code> object to this
     * <code>Rectangle2D</code>.  The resulting <code>Rectangle2D</code>
     * is the union of the two <code>Rectangle2D</code> objects.
     * <p>
     *  向此<code> Rectangle2D </code>中添加<code> Rectangle2D </code>对象。
     * 结果<code> Rectangle2D </code>是两个<code> Rectangle2D </code>对象的并集。
     * 
     * 
     * @param r the <code>Rectangle2D</code> to add to this
     * <code>Rectangle2D</code>.
     * @since 1.2
     */
    public void add(Rectangle2D r) {
        double x1 = Math.min(getMinX(), r.getMinX());
        double x2 = Math.max(getMaxX(), r.getMaxX());
        double y1 = Math.min(getMinY(), r.getMinY());
        double y2 = Math.max(getMaxY(), r.getMaxY());
        setRect(x1, y1, x2 - x1, y2 - y1);
    }

    /**
     * Returns an iteration object that defines the boundary of this
     * <code>Rectangle2D</code>.
     * The iterator for this class is multi-threaded safe, which means
     * that this <code>Rectangle2D</code> class guarantees that
     * modifications to the geometry of this <code>Rectangle2D</code>
     * object do not affect any iterations of that geometry that
     * are already in process.
     * <p>
     * 返回定义此<code> Rectangle2D </code>的边界的迭代对象。
     * 这个类的迭代器是多线程安全的,这意味着这个<code> Rectangle2D </code>类保证对这个<code> Rectangle2D </code>对象的几何形状的修改不影响该几何的任何迭代已
     * 经在进行中。
     * 返回定义此<code> Rectangle2D </code>的边界的迭代对象。
     * 
     * 
     * @param at an optional <code>AffineTransform</code> to be applied to
     * the coordinates as they are returned in the iteration, or
     * <code>null</code> if untransformed coordinates are desired
     * @return    the <code>PathIterator</code> object that returns the
     *          geometry of the outline of this
     *          <code>Rectangle2D</code>, one segment at a time.
     * @since 1.2
     */
    public PathIterator getPathIterator(AffineTransform at) {
        return new RectIterator(this, at);
    }

    /**
     * Returns an iteration object that defines the boundary of the
     * flattened <code>Rectangle2D</code>.  Since rectangles are already
     * flat, the <code>flatness</code> parameter is ignored.
     * The iterator for this class is multi-threaded safe, which means
     * that this <code>Rectangle2D</code> class guarantees that
     * modifications to the geometry of this <code>Rectangle2D</code>
     * object do not affect any iterations of that geometry that
     * are already in process.
     * <p>
     *  返回定义平铺的<code> Rectangle2D </code>边界的迭代对象。由于矩形已经平坦,因此将忽略<code> flatness </code>参数。
     * 这个类的迭代器是多线程安全的,这意味着这个<code> Rectangle2D </code>类保证对这个<code> Rectangle2D </code>对象的几何形状的修改不影响该几何的任何迭代已
     * 经在进行中。
     *  返回定义平铺的<code> Rectangle2D </code>边界的迭代对象。由于矩形已经平坦,因此将忽略<code> flatness </code>参数。
     * 
     * 
     * @param at an optional <code>AffineTransform</code> to be applied to
     * the coordinates as they are returned in the iteration, or
     * <code>null</code> if untransformed coordinates are desired
     * @param flatness the maximum distance that the line segments used to
     * approximate the curved segments are allowed to deviate from any
     * point on the original curve.  Since rectangles are already flat,
     * the <code>flatness</code> parameter is ignored.
     * @return    the <code>PathIterator</code> object that returns the
     *          geometry of the outline of this
     *          <code>Rectangle2D</code>, one segment at a time.
     * @since 1.2
     */
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return new RectIterator(this, at);
    }

    /**
     * Returns the hashcode for this <code>Rectangle2D</code>.
     * <p>
     *  返回此<code> Rectangle2D </code>的哈希码。
     * 
     * 
     * @return the hashcode for this <code>Rectangle2D</code>.
     * @since 1.2
     */
    public int hashCode() {
        long bits = java.lang.Double.doubleToLongBits(getX());
        bits += java.lang.Double.doubleToLongBits(getY()) * 37;
        bits += java.lang.Double.doubleToLongBits(getWidth()) * 43;
        bits += java.lang.Double.doubleToLongBits(getHeight()) * 47;
        return (((int) bits) ^ ((int) (bits >> 32)));
    }

    /**
     * Determines whether or not the specified <code>Object</code> is
     * equal to this <code>Rectangle2D</code>.  The specified
     * <code>Object</code> is equal to this <code>Rectangle2D</code>
     * if it is an instance of <code>Rectangle2D</code> and if its
     * location and size are the same as this <code>Rectangle2D</code>.
     * <p>
     *  确定指定的<code> Object </code>是否等于此<code> Rectangle2D </code>。
     * 如果它是<code> Rectangle2D </code>的一个实例,并且它的位置和大小与这个<code>相同,则指定的<code> Object </code>等于此<code> Rectangle
     * 2D </code> Rectangle2D </code>。
     * 
     * @param obj an <code>Object</code> to be compared with this
     * <code>Rectangle2D</code>.
     * @return     <code>true</code> if <code>obj</code> is an instance
     *                     of <code>Rectangle2D</code> and has
     *                     the same values; <code>false</code> otherwise.
     * @since 1.2
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Rectangle2D) {
            Rectangle2D r2d = (Rectangle2D) obj;
            return ((getX() == r2d.getX()) &&
                    (getY() == r2d.getY()) &&
                    (getWidth() == r2d.getWidth()) &&
                    (getHeight() == r2d.getHeight()));
        }
        return false;
    }
}
