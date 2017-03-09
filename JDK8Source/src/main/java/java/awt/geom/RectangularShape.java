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

import java.awt.Shape;
import java.awt.Rectangle;
import java.beans.Transient;

/**
 * <code>RectangularShape</code> is the base class for a number of
 * {@link Shape} objects whose geometry is defined by a rectangular frame.
 * This class does not directly specify any specific geometry by
 * itself, but merely provides manipulation methods inherited by
 * a whole category of <code>Shape</code> objects.
 * The manipulation methods provided by this class can be used to
 * query and modify the rectangular frame, which provides a reference
 * for the subclasses to define their geometry.
 *
 * <p>
 *  <code> RectangularShape </code>是几何由矩形框定义的{@link Shape}对象的基类。
 * 这个类本身并不直接指定任何特定的几何,而只是提供由整个类别的<code> Shape </code>对象继承的操作方法。此类提供的操作方法可用于查询和修改矩形框架,为子类提供参考以定义其几何。
 * 
 * 
 * @author      Jim Graham
 * @since 1.2
 */
public abstract class RectangularShape implements Shape, Cloneable {

    /**
     * This is an abstract class that cannot be instantiated directly.
     *
     * <p>
     *  这是一个不能直接实例化的抽象类。
     * 
     * 
     * @see Arc2D
     * @see Ellipse2D
     * @see Rectangle2D
     * @see RoundRectangle2D
     * @since 1.2
     */
    protected RectangularShape() {
    }

    /**
     * Returns the X coordinate of the upper-left corner of
     * the framing rectangle in <code>double</code> precision.
     * <p>
     *  返回<code> double </code>精度中框架矩形左上角的X坐标。
     * 
     * 
     * @return the X coordinate of the upper-left corner of
     * the framing rectangle.
     * @since 1.2
     */
    public abstract double getX();

    /**
     * Returns the Y coordinate of the upper-left corner of
     * the framing rectangle in <code>double</code> precision.
     * <p>
     *  返回<code> double </code>精度中框线矩形左上角的Y坐标。
     * 
     * 
     * @return the Y coordinate of the upper-left corner of
     * the framing rectangle.
     * @since 1.2
     */
    public abstract double getY();

    /**
     * Returns the width of the framing rectangle in
     * <code>double</code> precision.
     * <p>
     *  以<code> double </code>精度返回框架矩形的宽度。
     * 
     * 
     * @return the width of the framing rectangle.
     * @since 1.2
     */
    public abstract double getWidth();

    /**
     * Returns the height of the framing rectangle
     * in <code>double</code> precision.
     * <p>
     *  以<code> double </code>精度返回框架矩形的高度。
     * 
     * 
     * @return the height of the framing rectangle.
     * @since 1.2
     */
    public abstract double getHeight();

    /**
     * Returns the smallest X coordinate of the framing
     * rectangle of the <code>Shape</code> in <code>double</code>
     * precision.
     * <p>
     *  返回<code> double </code>精度中<code> Shape </code>的框架矩形的最小X坐标。
     * 
     * 
     * @return the smallest X coordinate of the framing
     *          rectangle of the <code>Shape</code>.
     * @since 1.2
     */
    public double getMinX() {
        return getX();
    }

    /**
     * Returns the smallest Y coordinate of the framing
     * rectangle of the <code>Shape</code> in <code>double</code>
     * precision.
     * <p>
     *  返回<code> double </code>精度中<code> Shape </code>的框架矩形的最小Y坐标。
     * 
     * 
     * @return the smallest Y coordinate of the framing
     *          rectangle of the <code>Shape</code>.
     * @since 1.2
     */
    public double getMinY() {
        return getY();
    }

    /**
     * Returns the largest X coordinate of the framing
     * rectangle of the <code>Shape</code> in <code>double</code>
     * precision.
     * <p>
     *  返回<code> double </code>精度中<code> Shape </code>的框架矩形的最大X坐标。
     * 
     * 
     * @return the largest X coordinate of the framing
     *          rectangle of the <code>Shape</code>.
     * @since 1.2
     */
    public double getMaxX() {
        return getX() + getWidth();
    }

    /**
     * Returns the largest Y coordinate of the framing
     * rectangle of the <code>Shape</code> in <code>double</code>
     * precision.
     * <p>
     * 返回<code> double </code>精度中<code> Shape </code>的框架矩形的最大Y坐标。
     * 
     * 
     * @return the largest Y coordinate of the framing
     *          rectangle of the <code>Shape</code>.
     * @since 1.2
     */
    public double getMaxY() {
        return getY() + getHeight();
    }

    /**
     * Returns the X coordinate of the center of the framing
     * rectangle of the <code>Shape</code> in <code>double</code>
     * precision.
     * <p>
     *  返回<code> double </code>精度中<code> Shape </code>的框架矩形中心的X坐标。
     * 
     * 
     * @return the X coordinate of the center of the framing rectangle
     *          of the <code>Shape</code>.
     * @since 1.2
     */
    public double getCenterX() {
        return getX() + getWidth() / 2.0;
    }

    /**
     * Returns the Y coordinate of the center of the framing
     * rectangle of the <code>Shape</code> in <code>double</code>
     * precision.
     * <p>
     *  返回<code> double </code>精度中<code> Shape </code>的框架矩形中心的Y坐标。
     * 
     * 
     * @return the Y coordinate of the center of the framing rectangle
     *          of the <code>Shape</code>.
     * @since 1.2
     */
    public double getCenterY() {
        return getY() + getHeight() / 2.0;
    }

    /**
     * Returns the framing {@link Rectangle2D}
     * that defines the overall shape of this object.
     * <p>
     *  返回定义此对象的整体形状的框架{@link Rectangle2D}。
     * 
     * 
     * @return a <code>Rectangle2D</code>, specified in
     * <code>double</code> coordinates.
     * @see #setFrame(double, double, double, double)
     * @see #setFrame(Point2D, Dimension2D)
     * @see #setFrame(Rectangle2D)
     * @since 1.2
     */
    @Transient
    public Rectangle2D getFrame() {
        return new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight());
    }

    /**
     * Determines whether the <code>RectangularShape</code> is empty.
     * When the <code>RectangularShape</code> is empty, it encloses no
     * area.
     * <p>
     *  确定<code> RectangularShape </code>是否为空。当<code> RectangularShape </code>为空时,它不包含任何区域。
     * 
     * 
     * @return <code>true</code> if the <code>RectangularShape</code> is empty;
     *          <code>false</code> otherwise.
     * @since 1.2
     */
    public abstract boolean isEmpty();

    /**
     * Sets the location and size of the framing rectangle of this
     * <code>Shape</code> to the specified rectangular values.
     *
     * <p>
     *  将<code> Shape </code>的框架矩形的位置和大小设置为指定的矩形值。
     * 
     * 
     * @param x the X coordinate of the upper-left corner of the
     *          specified rectangular shape
     * @param y the Y coordinate of the upper-left corner of the
     *          specified rectangular shape
     * @param w the width of the specified rectangular shape
     * @param h the height of the specified rectangular shape
     * @see #getFrame
     * @since 1.2
     */
    public abstract void setFrame(double x, double y, double w, double h);

    /**
     * Sets the location and size of the framing rectangle of this
     * <code>Shape</code> to the specified {@link Point2D} and
     * {@link Dimension2D}, respectively.  The framing rectangle is used
     * by the subclasses of <code>RectangularShape</code> to define
     * their geometry.
     * <p>
     *  将<code> Shape </code>的框架矩形的位置和大小分别设置为指定的{@link Point2D}和{@link Dimension2D}。
     * 框架矩形由<code> RectangularShape </code>的子类使用以定义其几何。
     * 
     * 
     * @param loc the specified <code>Point2D</code>
     * @param size the specified <code>Dimension2D</code>
     * @see #getFrame
     * @since 1.2
     */
    public void setFrame(Point2D loc, Dimension2D size) {
        setFrame(loc.getX(), loc.getY(), size.getWidth(), size.getHeight());
    }

    /**
     * Sets the framing rectangle of this <code>Shape</code> to
     * be the specified <code>Rectangle2D</code>.  The framing rectangle is
     * used by the subclasses of <code>RectangularShape</code> to define
     * their geometry.
     * <p>
     *  将<code> Shape </code>的框架矩形设置为指定的<code> Rectangle2D </code>。
     * 框架矩形由<code> RectangularShape </code>的子类使用以定义其几何。
     * 
     * 
     * @param r the specified <code>Rectangle2D</code>
     * @see #getFrame
     * @since 1.2
     */
    public void setFrame(Rectangle2D r) {
        setFrame(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    /**
     * Sets the diagonal of the framing rectangle of this <code>Shape</code>
     * based on the two specified coordinates.  The framing rectangle is
     * used by the subclasses of <code>RectangularShape</code> to define
     * their geometry.
     *
     * <p>
     *  基于两个指定的坐标设置此<code> Shape </code>框架矩形的对角线。框架矩形由<code> RectangularShape </code>的子类使用以定义其几何。
     * 
     * 
     * @param x1 the X coordinate of the start point of the specified diagonal
     * @param y1 the Y coordinate of the start point of the specified diagonal
     * @param x2 the X coordinate of the end point of the specified diagonal
     * @param y2 the Y coordinate of the end point of the specified diagonal
     * @since 1.2
     */
    public void setFrameFromDiagonal(double x1, double y1,
                                     double x2, double y2) {
        if (x2 < x1) {
            double t = x1;
            x1 = x2;
            x2 = t;
        }
        if (y2 < y1) {
            double t = y1;
            y1 = y2;
            y2 = t;
        }
        setFrame(x1, y1, x2 - x1, y2 - y1);
    }

    /**
     * Sets the diagonal of the framing rectangle of this <code>Shape</code>
     * based on two specified <code>Point2D</code> objects.  The framing
     * rectangle is used by the subclasses of <code>RectangularShape</code>
     * to define their geometry.
     *
     * <p>
     * 基于两个指定的<code> Point2D </code>对象,设置此<code> Shape </code>框架矩形的对角线。
     * 框架矩形由<code> RectangularShape </code>的子类使用以定义其几何。
     * 
     * 
     * @param p1 the start <code>Point2D</code> of the specified diagonal
     * @param p2 the end <code>Point2D</code> of the specified diagonal
     * @since 1.2
     */
    public void setFrameFromDiagonal(Point2D p1, Point2D p2) {
        setFrameFromDiagonal(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    /**
     * Sets the framing rectangle of this <code>Shape</code>
     * based on the specified center point coordinates and corner point
     * coordinates.  The framing rectangle is used by the subclasses of
     * <code>RectangularShape</code> to define their geometry.
     *
     * <p>
     *  基于指定的中心点坐标和角点坐标设置此<code> Shape </code>的框架矩形。框架矩形由<code> RectangularShape </code>的子类使用以定义其几何。
     * 
     * 
     * @param centerX the X coordinate of the specified center point
     * @param centerY the Y coordinate of the specified center point
     * @param cornerX the X coordinate of the specified corner point
     * @param cornerY the Y coordinate of the specified corner point
     * @since 1.2
     */
    public void setFrameFromCenter(double centerX, double centerY,
                                   double cornerX, double cornerY) {
        double halfW = Math.abs(cornerX - centerX);
        double halfH = Math.abs(cornerY - centerY);
        setFrame(centerX - halfW, centerY - halfH, halfW * 2.0, halfH * 2.0);
    }

    /**
     * Sets the framing rectangle of this <code>Shape</code> based on a
     * specified center <code>Point2D</code> and corner
     * <code>Point2D</code>.  The framing rectangle is used by the subclasses
     * of <code>RectangularShape</code> to define their geometry.
     * <p>
     *  基于指定的中心<code> Point2D </code>和角<code> Point2D </code>设置此<code> Shape </code>的框架矩形。
     * 框架矩形由<code> RectangularShape </code>的子类使用以定义其几何。
     * 
     * 
     * @param center the specified center <code>Point2D</code>
     * @param corner the specified corner <code>Point2D</code>
     * @since 1.2
     */
    public void setFrameFromCenter(Point2D center, Point2D corner) {
        setFrameFromCenter(center.getX(), center.getY(),
                           corner.getX(), corner.getY());
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.2
     */
    public boolean contains(Point2D p) {
        return contains(p.getX(), p.getY());
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
        return intersects(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.2
     */
    public boolean contains(Rectangle2D r) {
        return contains(r.getX(), r.getY(), r.getWidth(), r.getHeight());
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
        double width = getWidth();
        double height = getHeight();
        if (width < 0 || height < 0) {
            return new Rectangle();
        }
        double x = getX();
        double y = getY();
        double x1 = Math.floor(x);
        double y1 = Math.floor(y);
        double x2 = Math.ceil(x + width);
        double y2 = Math.ceil(y + height);
        return new Rectangle((int) x1, (int) y1,
                                      (int) (x2 - x1), (int) (y2 - y1));
    }

    /**
     * Returns an iterator object that iterates along the
     * <code>Shape</code> object's boundary and provides access to a
     * flattened view of the outline of the <code>Shape</code>
     * object's geometry.
     * <p>
     * Only SEG_MOVETO, SEG_LINETO, and SEG_CLOSE point types will
     * be returned by the iterator.
     * <p>
     * The amount of subdivision of the curved segments is controlled
     * by the <code>flatness</code> parameter, which specifies the
     * maximum distance that any point on the unflattened transformed
     * curve can deviate from the returned flattened path segments.
     * An optional {@link AffineTransform} can
     * be specified so that the coordinates returned in the iteration are
     * transformed accordingly.
     * <p>
     *  返回一个迭代器对象,该对象沿着<code> Shape </code>对象的边界进行迭代,并提供对<code> Shape </code>对象几何体轮廓的展开视图的访问。
     * <p>
     *  只有SEG_MOVETO,SEG_LINETO和SEG_CLOSE点类型将由迭代器返回。
     * <p>
     *  曲线段的细分量由<code> flatness </code>参数控制,该参数指定未平坦变换曲线上的任何点可以偏离返回的平坦路径段的最大距离。
     * 
     * @param at an optional <code>AffineTransform</code> to be applied to the
     *          coordinates as they are returned in the iteration,
     *          or <code>null</code> if untransformed coordinates are desired.
     * @param flatness the maximum distance that the line segments used to
     *          approximate the curved segments are allowed to deviate
     *          from any point on the original curve
     * @return a <code>PathIterator</code> object that provides access to
     *          the <code>Shape</code> object's flattened geometry.
     * @since 1.2
     */
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return new FlatteningPathIterator(getPathIterator(at), flatness);
    }

    /**
     * Creates a new object of the same class and with the same
     * contents as this object.
     * <p>
     * 可以指定一个可选的{@link AffineTransform},以便在迭代中返回的坐标被相应地转换。
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
}
