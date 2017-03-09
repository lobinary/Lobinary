/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
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

package java.awt;

import java.awt.geom.Dimension2D;
import java.beans.Transient;

/**
 * The <code>Dimension</code> class encapsulates the width and
 * height of a component (in integer precision) in a single object.
 * The class is
 * associated with certain properties of components. Several methods
 * defined by the <code>Component</code> class and the
 * <code>LayoutManager</code> interface return a
 * <code>Dimension</code> object.
 * <p>
 * Normally the values of <code>width</code>
 * and <code>height</code> are non-negative integers.
 * The constructors that allow you to create a dimension do
 * not prevent you from setting a negative value for these properties.
 * If the value of <code>width</code> or <code>height</code> is
 * negative, the behavior of some methods defined by other objects is
 * undefined.
 *
 * <p>
 *  <code> Dimension </code>类在单个对象中封装了组件的宽度和高度(整数精度)。类与组件的某些属性相关联。
 * 由<code> Component </code>类和<code> LayoutManager </code>接口定义的几种方法返回一个<code> Dimension </code>对象。
 * <p>
 *  通常,<code> width </code>和<code> height </code>的值是非负整数。允许创建维度的构造函数不会阻止您为这些属性设置负值。
 * 如果<code> width </code>或<code> height </code>的值为负,则其他对象定义的一些方法的行为是未定义的。
 * 
 * 
 * @author      Sami Shaio
 * @author      Arthur van Hoff
 * @see         java.awt.Component
 * @see         java.awt.LayoutManager
 * @since       1.0
 */
public class Dimension extends Dimension2D implements java.io.Serializable {

    /**
     * The width dimension; negative values can be used.
     *
     * <p>
     *  宽度尺寸;可以使用负值。
     * 
     * 
     * @serial
     * @see #getSize
     * @see #setSize
     * @since 1.0
     */
    public int width;

    /**
     * The height dimension; negative values can be used.
     *
     * <p>
     *  高度尺寸;可以使用负值。
     * 
     * 
     * @serial
     * @see #getSize
     * @see #setSize
     * @since 1.0
     */
    public int height;

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
     private static final long serialVersionUID = 4723952579491349524L;

    /**
     * Initialize JNI field and method IDs
     * <p>
     *  初始化JNI字段和方法ID
     * 
     */
    private static native void initIDs();

    static {
        /* ensure that the necessary native libraries are loaded */
        Toolkit.loadLibraries();
        if (!GraphicsEnvironment.isHeadless()) {
            initIDs();
        }
    }

    /**
     * Creates an instance of <code>Dimension</code> with a width
     * of zero and a height of zero.
     * <p>
     *  创建宽度为零,高度为零的<code> Dimension </code>实例。
     * 
     */
    public Dimension() {
        this(0, 0);
    }

    /**
     * Creates an instance of <code>Dimension</code> whose width
     * and height are the same as for the specified dimension.
     *
     * <p>
     *  创建<code> Dimension </code>的实例,其宽度和高度与指定维度相同。
     * 
     * 
     * @param    d   the specified dimension for the
     *               <code>width</code> and
     *               <code>height</code> values
     */
    public Dimension(Dimension d) {
        this(d.width, d.height);
    }

    /**
     * Constructs a <code>Dimension</code> and initializes
     * it to the specified width and specified height.
     *
     * <p>
     *  构造<code>维</code>并将其初始化为指定的宽度和指定的高度。
     * 
     * 
     * @param width the specified width
     * @param height the specified height
     */
    public Dimension(int width, int height) {
        this.width = width;
        this.height = height;
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
     * Sets the size of this <code>Dimension</code> object to
     * the specified width and height in double precision.
     * Note that if <code>width</code> or <code>height</code>
     * are larger than <code>Integer.MAX_VALUE</code>, they will
     * be reset to <code>Integer.MAX_VALUE</code>.
     *
     * <p>
     * 将此<code> Dimension </code>对象的大小设置为指定的双精度宽度和高度。
     * 请注意,如果<code> width </code>或<code> height </code>大于<code> Integer.MAX_VALUE </code>,则会将其重置为<code> Inte
     * ger.MAX_VALUE </code>。
     * 将此<code> Dimension </code>对象的大小设置为指定的双精度宽度和高度。
     * 
     * 
     * @param width  the new width for the <code>Dimension</code> object
     * @param height the new height for the <code>Dimension</code> object
     * @since 1.2
     */
    public void setSize(double width, double height) {
        this.width = (int) Math.ceil(width);
        this.height = (int) Math.ceil(height);
    }

    /**
     * Gets the size of this <code>Dimension</code> object.
     * This method is included for completeness, to parallel the
     * <code>getSize</code> method defined by <code>Component</code>.
     *
     * <p>
     *  获取此<<span> </code>对象的大小。包括这个方法的完整性,以并行<code>组件</code>定义的<code> getSize </code>方法。
     * 
     * 
     * @return   the size of this dimension, a new instance of
     *           <code>Dimension</code> with the same width and height
     * @see      java.awt.Dimension#setSize
     * @see      java.awt.Component#getSize
     * @since    1.1
     */
    @Transient
    public Dimension getSize() {
        return new Dimension(width, height);
    }

    /**
     * Sets the size of this <code>Dimension</code> object to the specified size.
     * This method is included for completeness, to parallel the
     * <code>setSize</code> method defined by <code>Component</code>.
     * <p>
     *  将此<code> Dimension </code>对象的大小设置为指定的大小。
     * 此方法是为了完整性而包括的,以并行<code>组件</code>定义的<code> setSize </code>方法。
     * 
     * 
     * @param    d  the new size for this <code>Dimension</code> object
     * @see      java.awt.Dimension#getSize
     * @see      java.awt.Component#setSize
     * @since    1.1
     */
    public void setSize(Dimension d) {
        setSize(d.width, d.height);
    }

    /**
     * Sets the size of this <code>Dimension</code> object
     * to the specified width and height.
     * This method is included for completeness, to parallel the
     * <code>setSize</code> method defined by <code>Component</code>.
     *
     * <p>
     *  将此<code> Dimension </code>对象的大小设置为指定的宽度和高度。
     * 此方法是为了完整性而包括的,以并行<code>组件</code>定义的<code> setSize </code>方法。
     * 
     * 
     * @param    width   the new width for this <code>Dimension</code> object
     * @param    height  the new height for this <code>Dimension</code> object
     * @see      java.awt.Dimension#getSize
     * @see      java.awt.Component#setSize
     * @since    1.1
     */
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Checks whether two dimension objects have equal values.
     * <p>
     *  检查两个维对象是否具有相等的值。
     * 
     */
    public boolean equals(Object obj) {
        if (obj instanceof Dimension) {
            Dimension d = (Dimension)obj;
            return (width == d.width) && (height == d.height);
        }
        return false;
    }

    /**
     * Returns the hash code for this <code>Dimension</code>.
     *
     * <p>
     *  返回此<code> Dimension </code>的哈希码。
     * 
     * 
     * @return    a hash code for this <code>Dimension</code>
     */
    public int hashCode() {
        int sum = width + height;
        return sum * (sum + 1)/2 + width;
    }

    /**
     * Returns a string representation of the values of this
     * <code>Dimension</code> object's <code>height</code> and
     * <code>width</code> fields. This method is intended to be used only
     * for debugging purposes, and the content and format of the returned
     * string may vary between implementations. The returned string may be
     * empty but may not be <code>null</code>.
     *
     * <p>
     *  返回此<code> Dimension </code>对象的<code> height </code>和<code> width </code>字段的值的字符串表示形式。
     * 此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * @return  a string representation of this <code>Dimension</code>
     *          object
     */
    public String toString() {
        return getClass().getName() + "[width=" + width + ",height=" + height + "]";
    }
}
