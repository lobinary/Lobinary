/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.Image;


/**
 * An asynchronous update interface for receiving notifications about
 * Image information as the Image is constructed.
 *
 * <p>
 *  异步更新接口,用于在构建映像时接收关于映像信息的通知
 * 
 * 
 * @author      Jim Graham
 */
public interface ImageObserver {
    /**
     * This method is called when information about an image which was
     * previously requested using an asynchronous interface becomes
     * available.  Asynchronous interfaces are method calls such as
     * getWidth(ImageObserver) and drawImage(img, x, y, ImageObserver)
     * which take an ImageObserver object as an argument.  Those methods
     * register the caller as interested either in information about
     * the overall image itself (in the case of getWidth(ImageObserver))
     * or about an output version of an image (in the case of the
     * drawImage(img, x, y, [w, h,] ImageObserver) call).
     *
     * <p>This method
     * should return true if further updates are needed or false if the
     * required information has been acquired.  The image which was being
     * tracked is passed in using the img argument.  Various constants
     * are combined to form the infoflags argument which indicates what
     * information about the image is now available.  The interpretation
     * of the x, y, width, and height arguments depends on the contents
     * of the infoflags argument.
     * <p>
     * The <code>infoflags</code> argument should be the bitwise inclusive
     * <b>OR</b> of the following flags: <code>WIDTH</code>,
     * <code>HEIGHT</code>, <code>PROPERTIES</code>, <code>SOMEBITS</code>,
     * <code>FRAMEBITS</code>, <code>ALLBITS</code>, <code>ERROR</code>,
     * <code>ABORT</code>.
     *
     * <p>
     * 当以前使用异步接口请求的映像的信息可用时,调用此方法异步接口是诸如getWidth(ImageObserver)和drawImage(img,x,y,ImageObserver)之类的方法调用,它们使用
     * ImageObserver对象作为参数。
     * 方法将调用者注册为感兴趣的关于整个图像本身的信息(在getWidth(ImageObserver)的情况下)或关于图像的输出版本(在drawImage的情况下(在img,x,y,[w,h ,] Imag
     * eObserver)call)。
     * 
     * <p>如果需要进一步更新,此方法应返回true;如果已获取所需信息,则返回false使用img参数传递正在跟踪的图像各种常量组合形成infoflags参数, image现在可用x,y,width和hei
     * ght参数的解释取决于infoflags参数的内容。
     * <p>
     *  <code> infoflags </code>参数应为以下标志的按位包含<b> OR </b>：<code> WIDTH </code>,<code> HEIGHT </code> </code>,
     * <code> SOMEBITS </code>,<code> FRAMEBITS </code>,<code> ALLBITS </code>,<code> ERROR </code>。
     * 
     * 
     * @param     img   the image being observed.
     * @param     infoflags   the bitwise inclusive OR of the following
     *               flags:  <code>WIDTH</code>, <code>HEIGHT</code>,
     *               <code>PROPERTIES</code>, <code>SOMEBITS</code>,
     *               <code>FRAMEBITS</code>, <code>ALLBITS</code>,
     *               <code>ERROR</code>, <code>ABORT</code>.
     * @param     x   the <i>x</i> coordinate.
     * @param     y   the <i>y</i> coordinate.
     * @param     width    the width.
     * @param     height   the height.
     * @return    <code>false</code> if the infoflags indicate that the
     *            image is completely loaded; <code>true</code> otherwise.
     *
     * @see #WIDTH
     * @see #HEIGHT
     * @see #PROPERTIES
     * @see #SOMEBITS
     * @see #FRAMEBITS
     * @see #ALLBITS
     * @see #ERROR
     * @see #ABORT
     * @see Image#getWidth
     * @see Image#getHeight
     * @see java.awt.Graphics#drawImage
     */
    public boolean imageUpdate(Image img, int infoflags,
                               int x, int y, int width, int height);

    /**
     * This flag in the infoflags argument to imageUpdate indicates that
     * the width of the base image is now available and can be taken
     * from the width argument to the imageUpdate callback method.
     * <p>
     * imageUpdate的infoflags参数中的此标志表示基础映像的宽度现在可用,并且可以从imageUpdate回调方法的width参数获取
     * 
     * 
     * @see Image#getWidth
     * @see #imageUpdate
     */
    public static final int WIDTH = 1;

    /**
     * This flag in the infoflags argument to imageUpdate indicates that
     * the height of the base image is now available and can be taken
     * from the height argument to the imageUpdate callback method.
     * <p>
     *  imageUpdate的infoflags参数中的此标志表示基础映像的高度现在可用,并且可以从imageUpdate回调方法的height参数获取
     * 
     * 
     * @see Image#getHeight
     * @see #imageUpdate
     */
    public static final int HEIGHT = 2;

    /**
     * This flag in the infoflags argument to imageUpdate indicates that
     * the properties of the image are now available.
     * <p>
     *  imageUpdate的infoflags参数中的此标志表示图像的属性现在可用
     * 
     * 
     * @see Image#getProperty
     * @see #imageUpdate
     */
    public static final int PROPERTIES = 4;

    /**
     * This flag in the infoflags argument to imageUpdate indicates that
     * more pixels needed for drawing a scaled variation of the image
     * are available.  The bounding box of the new pixels can be taken
     * from the x, y, width, and height arguments to the imageUpdate
     * callback method.
     * <p>
     *  imageUpdate的infoflags参数中的此标志表示绘制图像的缩放变体所需的更多像素可用新像素的边界框可以从x,y,width和height参数获取到imageUpdate回调方法
     * 
     * 
     * @see java.awt.Graphics#drawImage
     * @see #imageUpdate
     */
    public static final int SOMEBITS = 8;

    /**
     * This flag in the infoflags argument to imageUpdate indicates that
     * another complete frame of a multi-frame image which was previously
     * drawn is now available to be drawn again.  The x, y, width, and height
     * arguments to the imageUpdate callback method should be ignored.
     * <p>
     * imageUpdate的infoflags参数中的此标志指示先前绘制的多帧图像的另一个完整帧现在可用于再次绘制。应该忽略imageUpdate回调方法的x,y,width和height参数
     * 
     * 
     * @see java.awt.Graphics#drawImage
     * @see #imageUpdate
     */
    public static final int FRAMEBITS = 16;

    /**
     * This flag in the infoflags argument to imageUpdate indicates that
     * a static image which was previously drawn is now complete and can
     * be drawn again in its final form.  The x, y, width, and height
     * arguments to the imageUpdate callback method should be ignored.
     * <p>
     *  imageUpdate的infoflags参数中的此标志表示先前绘制的静态图像现在已完成,并且可以以其最终形式再次绘制。应该忽略imageUpdate回调方法的x,y,width和height参数
     * 
     * 
     * @see java.awt.Graphics#drawImage
     * @see #imageUpdate
     */
    public static final int ALLBITS = 32;

    /**
     * This flag in the infoflags argument to imageUpdate indicates that
     * an image which was being tracked asynchronously has encountered
     * an error.  No further information will become available and
     * drawing the image will fail.
     * As a convenience, the ABORT flag will be indicated at the same
     * time to indicate that the image production was aborted.
     * <p>
     * imageUpdate的infoflags参数中的此标志表示正在被异步跟踪的图像遇到错误。没有其他信息可用,并且绘制图像将失败为方便起见,将同时指示ABORT标志,以指示图像生成中止
     * 
     * 
     * @see #imageUpdate
     */
    public static final int ERROR = 64;

    /**
     * This flag in the infoflags argument to imageUpdate indicates that
     * an image which was being tracked asynchronously was aborted before
     * production was complete.  No more information will become available
     * without further action to trigger another image production sequence.
     * If the ERROR flag was not also set in this image update, then
     * accessing any of the data in the image will restart the production
     * again, probably from the beginning.
     * <p>
     *  imageUpdate的infoflags参数中的此标志表示正在异步跟踪的映像在生产完成之前已中止没有更多信息将无法进一步操作触发另一个映像生成序列如果在此映像更新中未设置ERROR标志,则访问映像中
     * 的任何数据将重新开始生产,可能从头开始。
     * 
     * @see #imageUpdate
     */
    public static final int ABORT = 128;
}
