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

/**
 * The <code>Dimension2D</code> class is to encapsulate a width
 * and a height dimension.
 * <p>
 * This class is only the abstract superclass for all objects that
 * store a 2D dimension.
 * The actual storage representation of the sizes is left to
 * the subclass.
 *
 * <p>
 *  <code> Dimension2D </code>类是封装宽度和高度维度。
 * <p>
 *  这个类只是存储2D维度的所有对象的抽象超类。尺寸的实际存储表示是留给子类的。
 * 
 * 
 * @author      Jim Graham
 * @since 1.2
 */
public abstract class Dimension2D implements Cloneable {

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
     * @see java.awt.Dimension
     * @since 1.2
     */
    protected Dimension2D() {
    }

    /**
     * Returns the width of this <code>Dimension</code> in double
     * precision.
     * <p>
     *  以双精度返回此<code> Dimension </code>的宽度。
     * 
     * 
     * @return the width of this <code>Dimension</code>.
     * @since 1.2
     */
    public abstract double getWidth();

    /**
     * Returns the height of this <code>Dimension</code> in double
     * precision.
     * <p>
     *  以双精度返回此<code> Dimension </code>的高度。
     * 
     * 
     * @return the height of this <code>Dimension</code>.
     * @since 1.2
     */
    public abstract double getHeight();

    /**
     * Sets the size of this <code>Dimension</code> object to the
     * specified width and height.
     * This method is included for completeness, to parallel the
     * {@link java.awt.Component#getSize getSize} method of
     * {@link java.awt.Component}.
     * <p>
     *  将此<code> Dimension </code>对象的大小设置为指定的宽度和高度。
     * 为了完整性,包含这个方法,以并行{@link java.awt.Component}的{@link java.awt.Component#getSize getSize}方法。
     * 
     * 
     * @param width  the new width for the <code>Dimension</code>
     * object
     * @param height  the new height for the <code>Dimension</code>
     * object
     * @since 1.2
     */
    public abstract void setSize(double width, double height);

    /**
     * Sets the size of this <code>Dimension2D</code> object to
     * match the specified size.
     * This method is included for completeness, to parallel the
     * <code>getSize</code> method of <code>Component</code>.
     * <p>
     *  设置此<code> Dimension2D </code>对象的大小以匹配指定的大小。包括这个方法的完整性,以并行<code>组件</code>的<code> getSize </code>方法。
     * 
     * 
     * @param d  the new size for the <code>Dimension2D</code>
     * object
     * @since 1.2
     */
    public void setSize(Dimension2D d) {
        setSize(d.getWidth(), d.getHeight());
    }

    /**
     * Creates a new object of the same class as this object.
     *
     * <p>
     *  创建与此对象相同类的新对象。
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
