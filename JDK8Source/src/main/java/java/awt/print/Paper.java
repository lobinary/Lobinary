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

package java.awt.print;

import java.awt.geom.Rectangle2D;

/**
 * The <code>Paper</code> class describes the physical characteristics of
 * a piece of paper.
 * <p>
 * When creating a <code>Paper</code> object, it is the application's
 * responsibility to ensure that the paper size and the imageable area
 * are compatible.  For example, if the paper size is changed from
 * 11 x 17 to 8.5 x 11, the application might need to reduce the
 * imageable area so that whatever is printed fits on the page.
 * <p>
 * <p>
 *  <code> Paper </code>类描述了一张纸的物理特性。
 * <p>
 *  创建<code> Paper </code>对象时,应用程序有责任确保纸张大小和可成像区域兼容。
 * 例如,如果纸张尺寸从11 x 17更改为8.5 x 11,则应用程序可能需要缩小可成像区域,以使打印出的任何内容适合页面。
 * <p>
 * 
 * @see #setSize(double, double)
 * @see #setImageableArea(double, double, double, double)
 */
public class Paper implements Cloneable {

 /* Private Class Variables */

    private static final int INCH = 72;
    private static final double LETTER_WIDTH = 8.5 * INCH;
    private static final double LETTER_HEIGHT = 11 * INCH;

 /* Instance Variables */

    /**
     * The height of the physical page in 1/72nds
     * of an inch. The number is stored as a floating
     * point value rather than as an integer
     * to facilitate the conversion from metric
     * units to 1/72nds of an inch and then back.
     * (This may or may not be a good enough reason
     * for a float).
     * <p>
     *  物理页面的高度在1/72英寸。该数字存储为浮点值而不是整数,以便于将公制单位转换为1 / 72nds,然后返回。 (这可能是也可能不是一个足够的理由浮动)。
     * 
     */
    private double mHeight;

    /**
     * The width of the physical page in 1/72nds
     * of an inch.
     * <p>
     *  物理页面的宽度在1/72英寸。
     * 
     */
    private double mWidth;

    /**
     * The area of the page on which drawing will
     * be visable. The area outside of this
     * rectangle but on the Page generally
     * reflects the printer's hardware margins.
     * The origin of the physical page is
     * at (0, 0) with this rectangle provided
     * in that coordinate system.
     * <p>
     *  图纸将可见的页面区域。该矩形之外但在页面上的区域通常反映了打印机的硬件边距。物理页的原点在(0,0),在该坐标系中提供该矩形。
     * 
     */
    private Rectangle2D mImageableArea;

 /* Constructors */

    /**
     * Creates a letter sized piece of paper
     * with one inch margins.
     * <p>
     *  创建一个尺寸为一英寸的纸张。
     * 
     */
    public Paper() {
        mHeight = LETTER_HEIGHT;
        mWidth = LETTER_WIDTH;
        mImageableArea = new Rectangle2D.Double(INCH, INCH,
                                                mWidth - 2 * INCH,
                                                mHeight - 2 * INCH);
    }

 /* Instance Methods */

    /**
     * Creates a copy of this <code>Paper</code> with the same contents
     * as this <code>Paper</code>.
     * <p>
     *  创建与此<code> Paper </code>相同内容的<code> Paper </code>副本。
     * 
     * 
     * @return a copy of this <code>Paper</code>.
     */
    public Object clone() {

        Paper newPaper;

        try {
            /* It's okay to copy the reference to the imageable
             * area into the clone since we always return a copy
             * of the imageable area when asked for it.
             * <p>
             *  区域进入克隆,因为我们总是在请求它时返回可成像区域的副本。
             * 
             */
            newPaper = (Paper) super.clone();

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            newPaper = null;    // should never happen.
        }

        return newPaper;
    }

    /**
     * Returns the height of the page in 1/72nds of an inch.
     * <p>
     *  以1 / 72nds为单位返回页面的高度。
     * 
     * 
     * @return the height of the page described by this
     *          <code>Paper</code>.
     */
    public double getHeight() {
        return mHeight;
    }

    /**
     * Sets the width and height of this <code>Paper</code>
     * object, which represents the properties of the page onto
     * which printing occurs.
     * The dimensions are supplied in 1/72nds of
     * an inch.
     * <p>
     * 设置<code> Paper </code>对象的宽度和高度,它代表打印所在的页面的属性。尺寸以1/72英寸提供。
     * 
     * 
     * @param width the value to which to set this <code>Paper</code>
     * object's width
     * @param height the value to which to set this <code>Paper</code>
     * object's height
     */
    public void setSize(double width, double height) {
        mWidth = width;
        mHeight = height;
    }

    /**
     * Returns the width of the page in 1/72nds
     * of an inch.
     * <p>
     *  以1 / 72nds为单位返回页面的宽度。
     * 
     * 
     * @return the width of the page described by this
     * <code>Paper</code>.
     */
    public double getWidth() {
        return mWidth;
    }

    /**
     * Sets the imageable area of this <code>Paper</code>.  The
     * imageable area is the area on the page in which printing
     * occurs.
     * <p>
     *  设置此<code>纸张</code>的可成像区域。可成像区域是页面上进行打印的区域。
     * 
     * 
     * @param x the X coordinate to which to set the
     * upper-left corner of the imageable area of this <code>Paper</code>
     * @param y the Y coordinate to which to set the
     * upper-left corner of the imageable area of this <code>Paper</code>
     * @param width the value to which to set the width of the
     * imageable area of this <code>Paper</code>
     * @param height the value to which to set the height of the
     * imageable area of this <code>Paper</code>
     */
    public void setImageableArea(double x, double y,
                                 double width, double height) {
        mImageableArea = new Rectangle2D.Double(x, y, width,height);
    }

    /**
     * Returns the x coordinate of the upper-left corner of this
     * <code>Paper</code> object's imageable area.
     * <p>
     *  返回此<code> Paper </code>对象的可成像区域左上角的x坐标。
     * 
     * 
     * @return the x coordinate of the imageable area.
     */
    public double getImageableX() {
        return mImageableArea.getX();
    }

    /**
     * Returns the y coordinate of the upper-left corner of this
     * <code>Paper</code> object's imageable area.
     * <p>
     *  返回此<code> Paper </code>对象的可成像区域左上角的y坐标。
     * 
     * 
     * @return the y coordinate of the imageable area.
     */
    public double getImageableY() {
        return mImageableArea.getY();
    }

    /**
     * Returns the width of this <code>Paper</code> object's imageable
     * area.
     * <p>
     *  返回此<>代码</code>对象的可成像区域的宽度。
     * 
     * 
     * @return the width of the imageable area.
     */
    public double getImageableWidth() {
        return mImageableArea.getWidth();
    }

    /**
     * Returns the height of this <code>Paper</code> object's imageable
     * area.
     * <p>
     *  返回<code> Paper </code>对象的可成像区域的高度。
     * 
     * @return the height of the imageable area.
     */
    public double getImageableHeight() {
        return mImageableArea.getHeight();
    }
}
