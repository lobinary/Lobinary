/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2011, Oracle and/or its affiliates. All rights reserved.
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

/**
 * An <code>Insets</code> object is a representation of the borders
 * of a container. It specifies the space that a container must leave
 * at each of its edges. The space can be a border, a blank space, or
 * a title.
 *
 * <p>
 *  <code> Insets </code>对象是容器边框的表示。它指定容器必须在其每个边缘处离开的空间。空格可以是边框,空格或标题。
 * 
 * 
 * @author      Arthur van Hoff
 * @author      Sami Shaio
 * @see         java.awt.LayoutManager
 * @see         java.awt.Container
 * @since       JDK1.0
 */
public class Insets implements Cloneable, java.io.Serializable {

    /**
     * The inset from the top.
     * This value is added to the Top of the rectangle
     * to yield a new location for the Top.
     *
     * <p>
     *  从顶部插入。此值将添加到矩形的顶部,为顶部生成新位置。
     * 
     * 
     * @serial
     * @see #clone()
     */
    public int top;

    /**
     * The inset from the left.
     * This value is added to the Left of the rectangle
     * to yield a new location for the Left edge.
     *
     * <p>
     *  从左边的插图。此值将添加到矩形的左边,以产生左边缘的新位置。
     * 
     * 
     * @serial
     * @see #clone()
     */
    public int left;

    /**
     * The inset from the bottom.
     * This value is subtracted from the Bottom of the rectangle
     * to yield a new location for the Bottom.
     *
     * <p>
     *  从底部插入。从矩形底部减去此值,为底部产生一个新位置。
     * 
     * 
     * @serial
     * @see #clone()
     */
    public int bottom;

    /**
     * The inset from the right.
     * This value is subtracted from the Right of the rectangle
     * to yield a new location for the Right edge.
     *
     * <p>
     *  从右边的插图。从矩形的右边减去该值,为右边缘产生一个新位置。
     * 
     * 
     * @serial
     * @see #clone()
     */
    public int right;

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
    private static final long serialVersionUID = -2272572637695466749L;

    static {
        /* ensure that the necessary native libraries are loaded */
        Toolkit.loadLibraries();
        if (!GraphicsEnvironment.isHeadless()) {
            initIDs();
        }
    }

    /**
     * Creates and initializes a new <code>Insets</code> object with the
     * specified top, left, bottom, and right insets.
     * <p>
     *  使用指定的顶部,左侧,底部和右侧插图创建和初始化新的<code> Insets </code>对象。
     * 
     * 
     * @param       top   the inset from the top.
     * @param       left   the inset from the left.
     * @param       bottom   the inset from the bottom.
     * @param       right   the inset from the right.
     */
    public Insets(int top, int left, int bottom, int right) {
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
    }

    /**
     * Set top, left, bottom, and right to the specified values
     *
     * <p>
     *  将顶部,左侧,底部和右侧设置为指定的值
     * 
     * 
     * @param       top   the inset from the top.
     * @param       left   the inset from the left.
     * @param       bottom   the inset from the bottom.
     * @param       right   the inset from the right.
     * @since 1.5
     */
    public void set(int top, int left, int bottom, int right) {
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
    }

    /**
     * Checks whether two insets objects are equal. Two instances
     * of <code>Insets</code> are equal if the four integer values
     * of the fields <code>top</code>, <code>left</code>,
     * <code>bottom</code>, and <code>right</code> are all equal.
     * <p>
     *  检查两个插入对象是否相等。
     * 如果字段<code> top </code>,<code> left </code>,<code> bottom </code>和<code> </code>的四个整数值相等,<code> code> 
     * right </code>都相等。
     *  检查两个插入对象是否相等。
     * 
     * 
     * @return      <code>true</code> if the two insets are equal;
     *                          otherwise <code>false</code>.
     * @since       JDK1.1
     */
    public boolean equals(Object obj) {
        if (obj instanceof Insets) {
            Insets insets = (Insets)obj;
            return ((top == insets.top) && (left == insets.left) &&
                    (bottom == insets.bottom) && (right == insets.right));
        }
        return false;
    }

    /**
     * Returns the hash code for this Insets.
     *
     * <p>
     *  返回此Insets的哈希码。
     * 
     * 
     * @return    a hash code for this Insets.
     */
    public int hashCode() {
        int sum1 = left + bottom;
        int sum2 = right + top;
        int val1 = sum1 * (sum1 + 1)/2 + left;
        int val2 = sum2 * (sum2 + 1)/2 + top;
        int sum3 = val1 + val2;
        return sum3 * (sum3 + 1)/2 + val2;
    }

    /**
     * Returns a string representation of this <code>Insets</code> object.
     * This method is intended to be used only for debugging purposes, and
     * the content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not be
     * <code>null</code>.
     *
     * <p>
     * 返回此<code> Insets </code>对象的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return  a string representation of this <code>Insets</code> object.
     */
    public String toString() {
        return getClass().getName() + "[top="  + top + ",left=" + left + ",bottom=" + bottom + ",right=" + right + "]";
    }

    /**
     * Create a copy of this object.
     * <p>
     *  创建此对象的副本。
     * 
     * 
     * @return     a copy of this <code>Insets</code> object.
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
     * Initialize JNI field and method IDs
     * <p>
     *  初始化JNI字段和方法ID
     */
    private static native void initIDs();

}
