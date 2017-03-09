/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import java.lang.annotation.Native;

/**
 * The <code>PageFormat</code> class describes the size and
 * orientation of a page to be printed.
 * <p>
 *  <code> PageFormat </code>类描述了要打印的页面的大小和方向。
 * 
 */
public class PageFormat implements Cloneable
{

 /* Class Constants */

    /**
     *  The origin is at the bottom left of the paper with
     *  x running bottom to top and y running left to right.
     *  Note that this is not the Macintosh landscape but
     *  is the Window's and PostScript landscape.
     * <p>
     *  原点在纸张的左下角,x从底部到顶部,y从左到右。注意,这不是Macintosh的景观,而是Window和PostScript的景观。
     * 
     */
    @Native public static final int LANDSCAPE = 0;

    /**
     *  The origin is at the top left of the paper with
     *  x running to the right and y running down the
     *  paper.
     * <p>
     *  原点在纸张的左上角,x向右运行,y从纸张向下运行。
     * 
     */
    @Native public static final int PORTRAIT = 1;

    /**
     *  The origin is at the top right of the paper with x
     *  running top to bottom and y running right to left.
     *  Note that this is the Macintosh landscape.
     * <p>
     *  原点在纸张的右上角,x从上到下,y从右到左。请注意,这是Macintosh环境。
     * 
     */
    @Native public static final int REVERSE_LANDSCAPE = 2;

 /* Instance Variables */

    /**
     * A description of the physical piece of paper.
     * <p>
     *  物理纸张的描述。
     * 
     */
    private Paper mPaper;

    /**
     * The orientation of the current page. This will be
     * one of the constants: PORTRIAT, LANDSCAPE, or
     * REVERSE_LANDSCAPE,
     * <p>
     *  当前页面的方向。这将是其中一个常量：PORTRIAT,LANDSCAPE或REVERSE_LANDSCAPE,
     * 
     */
    private int mOrientation = PORTRAIT;

 /* Constructors */

    /**
     * Creates a default, portrait-oriented
     * <code>PageFormat</code>.
     * <p>
     *  创建默认的面向肖像的<code> PageFormat </code>。
     * 
     */
    public PageFormat()
    {
        mPaper = new Paper();
    }

 /* Instance Methods */

    /**
     * Makes a copy of this <code>PageFormat</code> with the same
     * contents as this <code>PageFormat</code>.
     * <p>
     *  使用与此<code> PageFormat </code>相同的内容复制此<code> PageFormat </code>。
     * 
     * 
     * @return a copy of this <code>PageFormat</code>.
     */
    public Object clone() {
        PageFormat newPage;

        try {
            newPage = (PageFormat) super.clone();
            newPage.mPaper = (Paper)mPaper.clone();

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            newPage = null;     // should never happen.
        }

        return newPage;
    }


    /**
     * Returns the width, in 1/72nds of an inch, of the page.
     * This method takes into account the orientation of the
     * page when determining the width.
     * <p>
     *  返回页面的1 / 72nds中的宽度。此方法在确定页面宽度时考虑页面的方向。
     * 
     * 
     * @return the width of the page.
     */
    public double getWidth() {
        double width;
        int orientation = getOrientation();

        if (orientation == PORTRAIT) {
            width = mPaper.getWidth();
        } else {
            width = mPaper.getHeight();
        }

        return width;
    }

    /**
     * Returns the height, in 1/72nds of an inch, of the page.
     * This method takes into account the orientation of the
     * page when determining the height.
     * <p>
     *  返回页面的高度,以1 / 72nds为单位。该方法在确定高度时考虑页面的取向。
     * 
     * 
     * @return the height of the page.
     */
    public double getHeight() {
        double height;
        int orientation = getOrientation();

        if (orientation == PORTRAIT) {
            height = mPaper.getHeight();
        } else {
            height = mPaper.getWidth();
        }

        return height;
    }

    /**
     * Returns the x coordinate of the upper left point of the
     * imageable area of the <code>Paper</code> object
     * associated with this <code>PageFormat</code>.
     * This method takes into account the
     * orientation of the page.
     * <p>
     * 返回与此<code> PageFormat </code>关联的<code> Paper </code>对象的可成像区域的左上点的x坐标。此方法考虑了页面的方向。
     * 
     * 
     * @return the x coordinate of the upper left point of the
     * imageable area of the <code>Paper</code> object
     * associated with this <code>PageFormat</code>.
     */
    public double getImageableX() {
        double x;

        switch (getOrientation()) {

        case LANDSCAPE:
            x = mPaper.getHeight()
                - (mPaper.getImageableY() + mPaper.getImageableHeight());
            break;

        case PORTRAIT:
            x = mPaper.getImageableX();
            break;

        case REVERSE_LANDSCAPE:
            x = mPaper.getImageableY();
            break;

        default:
            /* This should never happen since it signifies that the
             * PageFormat is in an invalid orientation.
             * <p>
             *  PageFormat无效的方向。
             * 
             */
            throw new InternalError("unrecognized orientation");

        }

        return x;
    }

    /**
     * Returns the y coordinate of the upper left point of the
     * imageable area of the <code>Paper</code> object
     * associated with this <code>PageFormat</code>.
     * This method takes into account the
     * orientation of the page.
     * <p>
     *  返回与此<code> PageFormat </code>关联的<code> Paper </code>对象的可成像区域的左上点的y坐标。此方法考虑了页面的方向。
     * 
     * 
     * @return the y coordinate of the upper left point of the
     * imageable area of the <code>Paper</code> object
     * associated with this <code>PageFormat</code>.
     */
    public double getImageableY() {
        double y;

        switch (getOrientation()) {

        case LANDSCAPE:
            y = mPaper.getImageableX();
            break;

        case PORTRAIT:
            y = mPaper.getImageableY();
            break;

        case REVERSE_LANDSCAPE:
            y = mPaper.getWidth()
                - (mPaper.getImageableX() + mPaper.getImageableWidth());
            break;

        default:
            /* This should never happen since it signifies that the
             * PageFormat is in an invalid orientation.
             * <p>
             *  PageFormat无效的方向。
             * 
             */
            throw new InternalError("unrecognized orientation");

        }

        return y;
    }

    /**
     * Returns the width, in 1/72nds of an inch, of the imageable
     * area of the page. This method takes into account the orientation
     * of the page.
     * <p>
     *  返回页面的可成像区域的宽度,以1 / 72nds为单位。此方法考虑了页面的方向。
     * 
     * 
     * @return the width of the page.
     */
    public double getImageableWidth() {
        double width;

        if (getOrientation() == PORTRAIT) {
            width = mPaper.getImageableWidth();
        } else {
            width = mPaper.getImageableHeight();
        }

        return width;
    }

    /**
     * Return the height, in 1/72nds of an inch, of the imageable
     * area of the page. This method takes into account the orientation
     * of the page.
     * <p>
     *  返回页面的可成像区域的高度,以1 / 72nds为单位。此方法考虑了页面的方向。
     * 
     * 
     * @return the height of the page.
     */
    public double getImageableHeight() {
        double height;

        if (getOrientation() == PORTRAIT) {
            height = mPaper.getImageableHeight();
        } else {
            height = mPaper.getImageableWidth();
        }

        return height;
    }


    /**
     * Returns a copy of the {@link Paper} object associated
     * with this <code>PageFormat</code>.  Changes made to the
     * <code>Paper</code> object returned from this method do not
     * affect the <code>Paper</code> object of this
     * <code>PageFormat</code>.  To update the <code>Paper</code>
     * object of this <code>PageFormat</code>, create a new
     * <code>Paper</code> object and set it into this
     * <code>PageFormat</code> by using the {@link #setPaper(Paper)}
     * method.
     * <p>
     *  返回与此<code> PageFormat </code>关联的{@link Paper}对象的副本。
     * 对从此方法返回的<code> Paper </code>对象所做的更改不会影响此<code> PageFormat </code>的<code> Paper </code>对象。
     * 要更新此<code> PageFormat </code>的<code> Paper </code>对象,请创建一个新的<code> Paper </code>对象,并将其设置为<code> PageF
     * ormat </code> {@link #setPaper(Paper)}方法。
     * 对从此方法返回的<code> Paper </code>对象所做的更改不会影响此<code> PageFormat </code>的<code> Paper </code>对象。
     * 
     * 
     * @return a copy of the <code>Paper</code> object associated
     *          with this <code>PageFormat</code>.
     * @see #setPaper
     */
    public Paper getPaper() {
        return (Paper)mPaper.clone();
    }

    /**
     * Sets the <code>Paper</code> object for this
     * <code>PageFormat</code>.
     * <p>
     *  为此<code> PageFormat </code>设置<code> Paper </code>对象。
     * 
     * 
     * @param paper the <code>Paper</code> object to which to set
     * the <code>Paper</code> object for this <code>PageFormat</code>.
     * @exception NullPointerException
     *              a null paper instance was passed as a parameter.
     * @see #getPaper
     */
     public void setPaper(Paper paper) {
         mPaper = (Paper)paper.clone();
     }

    /**
     * Sets the page orientation. <code>orientation</code> must be
     * one of the constants: PORTRAIT, LANDSCAPE,
     * or REVERSE_LANDSCAPE.
     * <p>
     *  设置页面方向。 <code> orientation </code>必须是以下常量之一：PORTRAIT,LANDSCAPE或REVERSE_LANDSCAPE。
     * 
     * 
     * @param orientation the new orientation for the page
     * @throws IllegalArgumentException if
     *          an unknown orientation was requested
     * @see #getOrientation
     */
    public void setOrientation(int orientation) throws IllegalArgumentException
    {
        if (0 <= orientation && orientation <= REVERSE_LANDSCAPE) {
            mOrientation = orientation;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns the orientation of this <code>PageFormat</code>.
     * <p>
     *  返回此<code> PageFormat </code>的方向。
     * 
     * 
     * @return this <code>PageFormat</code> object's orientation.
     * @see #setOrientation
     */
    public int getOrientation() {
        return mOrientation;
    }

    /**
     * Returns a transformation matrix that translates user
     * space rendering to the requested orientation
     * of the page.  The values are placed into the
     * array as
     * {&nbsp;m00,&nbsp;m10,&nbsp;m01,&nbsp;m11,&nbsp;m02,&nbsp;m12} in
     * the form required by the {@link AffineTransform}
     * constructor.
     * <p>
     * 返回一个转换矩阵,将用户空间渲染转换为请求的页面方向。
     * 这些值以{&nbsp; m00,&nbsp; m10,&nbsp; m01,&nbsp; m11,&nbsp; m02,&nbsp; m12}的形式放置在数组中,格式为{@link AffineTransform}
     * 构造函数所需的形式。
     * 
     * @return the matrix used to translate user space rendering
     * to the orientation of the page.
     * @see java.awt.geom.AffineTransform
     */
    public double[] getMatrix() {
        double[] matrix = new double[6];

        switch (mOrientation) {

        case LANDSCAPE:
            matrix[0] =  0;     matrix[1] = -1;
            matrix[2] =  1;     matrix[3] =  0;
            matrix[4] =  0;     matrix[5] =  mPaper.getHeight();
            break;

        case PORTRAIT:
            matrix[0] =  1;     matrix[1] =  0;
            matrix[2] =  0;     matrix[3] =  1;
            matrix[4] =  0;     matrix[5] =  0;
            break;

        case REVERSE_LANDSCAPE:
            matrix[0] =  0;                     matrix[1] =  1;
            matrix[2] = -1;                     matrix[3] =  0;
            matrix[4] =  mPaper.getWidth();     matrix[5] =  0;
            break;

        default:
            throw new IllegalArgumentException();
        }

        return matrix;
    }
}
