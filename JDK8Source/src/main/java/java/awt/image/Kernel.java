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

package java.awt.image;


/**
 * The <code>Kernel</code> class defines a matrix that describes how a
 * specified pixel and its surrounding pixels affect the value
 * computed for the pixel's position in the output image of a filtering
 * operation.  The X origin and Y origin indicate the kernel matrix element
 * that corresponds to the pixel position for which an output value is
 * being computed.
 *
 * <p>
 *  <code> Kernel </code>类定义了一个矩阵,描述了指定像素及其周围像素如何影响为滤波操作的输出图像中的像素位置计算的值。 X原点和Y原点指示与计算输出值的像素位置相对应的核矩阵元素。
 * 
 * 
 * @see ConvolveOp
 */
public class Kernel implements Cloneable {
    private int  width;
    private int  height;
    private int  xOrigin;
    private int  yOrigin;
    private float data[];

    private static native void initIDs();
    static {
        ColorModel.loadLibraries();
        initIDs();
    }

    /**
     * Constructs a <code>Kernel</code> object from an array of floats.
     * The first <code>width</code>*<code>height</code> elements of
     * the <code>data</code> array are copied.
     * If the length of the <code>data</code> array is less
     * than width*height, an <code>IllegalArgumentException</code> is thrown.
     * The X origin is (width-1)/2 and the Y origin is (height-1)/2.
     * <p>
     *  从浮点数组构造一个<code> Kernel </code>对象。
     * 复制<code> data </code>数组的第一个<code> width </code> * <code> height </code>如果<code> data </code>数组的长度小于wi
     * dth * height,则抛出一个<code> IllegalArgumentException </code>。
     *  从浮点数组构造一个<code> Kernel </code>对象。 X原点是(width-1)/ 2,Y原点是(height-1)/ 2。
     * 
     * 
     * @param width         width of the kernel
     * @param height        height of the kernel
     * @param data          kernel data in row major order
     * @throws IllegalArgumentException if the length of <code>data</code>
     *         is less than the product of <code>width</code> and
     *         <code>height</code>
     */
    public Kernel(int width, int height, float data[]) {
        this.width  = width;
        this.height = height;
        this.xOrigin  = (width-1)>>1;
        this.yOrigin  = (height-1)>>1;
        int len = width*height;
        if (data.length < len) {
            throw new IllegalArgumentException("Data array too small "+
                                               "(is "+data.length+
                                               " and should be "+len);
        }
        this.data = new float[len];
        System.arraycopy(data, 0, this.data, 0, len);

    }

    /**
     * Returns the X origin of this <code>Kernel</code>.
     * <p>
     *  返回此<code> Kernel </code>的X原点。
     * 
     * 
     * @return the X origin.
     */
    final public int getXOrigin(){
        return xOrigin;
    }

    /**
     * Returns the Y origin of this <code>Kernel</code>.
     * <p>
     *  返回此<code> Kernel </code>的Y原点。
     * 
     * 
     * @return the Y origin.
     */
    final public int getYOrigin() {
        return yOrigin;
    }

    /**
     * Returns the width of this <code>Kernel</code>.
     * <p>
     *  返回此<code> Kernel </code>的宽度。
     * 
     * 
     * @return the width of this <code>Kernel</code>.
     */
    final public int getWidth() {
        return width;
    }

    /**
     * Returns the height of this <code>Kernel</code>.
     * <p>
     *  返回此<code> Kernel </code>的高度。
     * 
     * 
     * @return the height of this <code>Kernel</code>.
     */
    final public int getHeight() {
        return height;
    }

    /**
     * Returns the kernel data in row major order.
     * The <code>data</code> array is returned.  If <code>data</code>
     * is <code>null</code>, a new array is allocated.
     * <p>
     *  以行主要顺序返回内核数据。返回<code> data </code>数组。如果<code> data </code>是<code> null </code>,则会分配一个新数组。
     * 
     * 
     * @param data  if non-null, contains the returned kernel data
     * @return the <code>data</code> array containing the kernel data
     *         in row major order or, if <code>data</code> is
     *         <code>null</code>, a newly allocated array containing
     *         the kernel data in row major order
     * @throws IllegalArgumentException if <code>data</code> is less
     *         than the size of this <code>Kernel</code>
     */
    final public float[] getKernelData(float[] data) {
        if (data == null) {
            data = new float[this.data.length];
        }
        else if (data.length < this.data.length) {
            throw new IllegalArgumentException("Data array too small "+
                                               "(should be "+this.data.length+
                                               " but is "+
                                               data.length+" )");
        }
        System.arraycopy(this.data, 0, data, 0, this.data.length);

        return data;
    }

    /**
     * Clones this object.
     * <p>
     *  克隆此对象。
     * 
     * @return a clone of this object.
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
