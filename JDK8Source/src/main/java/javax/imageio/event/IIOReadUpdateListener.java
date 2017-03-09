/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.imageio.event;

import java.awt.image.BufferedImage;
import java.util.EventListener;
import javax.imageio.ImageReader;

/**
 * An interface used by <code>ImageReader</code> implementations to
 * notify callers of their image and thumbnail reading methods of
 * pixel updates.
 *
 * <p>
 *  <code> ImageReader </code>实现使用的接口,通知调用者他们的图像和像素更新的缩略图读取方法。
 * 
 * 
 * @see javax.imageio.ImageReader#addIIOReadUpdateListener
 * @see javax.imageio.ImageReader#removeIIOReadUpdateListener
 *
 */
public interface IIOReadUpdateListener extends EventListener {

    /**
     * Reports that the current read operation is about to begin a
     * progressive pass.  Readers of formats that support progressive
     * encoding should use this to notify clients when each pass is
     * completed when reading a progressively encoded image.
     *
     * <p> An estimate of the area that will be updated by the pass is
     * indicated by the <code>minX</code>, <code>minY</code>,
     * <code>width</code>, and <code>height</code> parameters.  If the
     * pass is interlaced, that is, it only updates selected rows or
     * columns, the <code>periodX</code> and <code>periodY</code>
     * parameters will indicate the degree of subsampling.  The set of
     * bands that may be affected is indicated by the value of
     * <code>bands</code>.
     *
     * <p>
     *  报告当前读取操作将要开始渐进式传递。支持渐进式编码的格式的读者应该使用此选项在读取渐进编码图像时,在每次通过完成时通知客户端。
     * 
     *  <p>将通过通行证更新的区域的估计值由<code> minX </code>,<code> minY </code>,<code> width </code>和<code> height </code>
     * 参数。
     * 如果传递是隔行扫描的,即它只更新所选的行或列,则<code> periodX </code>和<code> periodY </code>参数将指示子采样的程度。
     * 可能受影响的频带集由<code> bands </code>的值指示。
     * 
     * 
     * @param source the <code>ImageReader</code> object calling this
     * method.
     * @param theImage the <code>BufferedImage</code> being updated.
     * @param pass the number of the pass that is about to begin,
     * starting with 0.
     * @param minPass the index of the first pass that will be decoded.
     * @param maxPass the index of the last pass that will be decoded.
     * @param minX the X coordinate of the leftmost updated column
     * of pixels.
     * @param minY the Y coordinate of the uppermost updated row
     * of pixels.
     * @param periodX the horizontal spacing between updated pixels;
     * a value of 1 means no gaps.
     * @param periodY the vertical spacing between updated pixels;
     * a value of 1 means no gaps.
     * @param bands an array of <code>int</code>s indicating the the
     * set bands that may be updated.
     */
    void passStarted(ImageReader source,
                     BufferedImage theImage,
                     int pass,
                     int minPass, int maxPass,
                     int minX, int minY,
                     int periodX, int periodY,
                     int[] bands);

    /**
     * Reports that a given region of the image has been updated.
     * The application might choose to redisplay the specified area,
     * for example, in order to provide a progressive display effect,
     * or perform other incremental processing.
     *
     * <p> Note that different image format readers may produce
     * decoded pixels in a variety of different orders.  Many readers
     * will produce pixels in a simple top-to-bottom,
     * left-to-right-order, but others may use multiple passes of
     * interlacing, tiling, etc.  The sequence of updates may even
     * differ from call to call depending on network speeds, for
     * example.  A call to this method does not guarantee that all the
     * specified pixels have actually been updated, only that some
     * activity has taken place within some subregion of the one
     * specified.
     *
     * <p> The particular <code>ImageReader</code> implementation may
     * choose how often to provide updates.  Each update specifies
     * that a given region of the image has been updated since the
     * last update.  A region is described by its spatial bounding box
     * (<code>minX</code>, <code>minY</code>, <code>width</code>, and
     * <code>height</code>); X and Y subsampling factors
     * (<code>periodX</code> and <code>periodY</code>); and a set of
     * updated bands (<code>bands</code>).  For example, the update:
     *
     * <pre>
     * minX = 10
     * minY = 20
     * width = 3
     * height = 4
     * periodX = 2
     * periodY = 3
     * bands = { 1, 3 }
     * </pre>
     *
     * would indicate that bands 1 and 3 of the following pixels were
     * updated:
     *
     * <pre>
     * (10, 20) (12, 20) (14, 20)
     * (10, 23) (12, 23) (14, 23)
     * (10, 26) (12, 26) (14, 26)
     * (10, 29) (12, 29) (14, 29)
     * </pre>
     *
     * <p>
     *  报告图像的给定区域已更新。应用可以选择重新显示指定区域,例如,以便提供渐进显示效果,或者执行其他增量处理。
     * 
     * 注意,不同的图像格式读取器可以以各种不同的顺序产生解码的像素。许多读者将以简单的从上到下,从左到右的顺序产生像素,但是其他读者可以使用多次交错,平铺等。
     * 更新的顺序甚至可以根据网络速度而不同于呼叫到呼叫, 例如。调用此方法并不保证所有指定的像素实际上都已更新,只是在指定的某个子区域内发生了某些活动。
     * 
     *  <p>特定的<code> ImageReader </code>实现可以选择提供更新的频率。每个更新指定图像的给定区域自从上次更新以来已被更新。
     * 区域由其空间边界框(<code> minX </code>,<code> minY </code>,<code> width </code>和<code> height </code>)描述; X和Y子
     * 采样因子(<code> periodX </code>和<code> periodY </code>);和一组更新的频带(<code> bands </code>)。
     *  <p>特定的<code> ImageReader </code>实现可以选择提供更新的频率。每个更新指定图像的给定区域自从上次更新以来已被更新。例如,更新：。
     * 
     * <pre>
     *  minX = 10minY = 20 width = 3 height = 4 periodX = 2 periodY = 3 bands = {1,3}
     * </pre>
     * 
     *  将指示以下像素的频带1和3被更新：
     * 
     * 
     * @param source the <code>ImageReader</code> object calling this method.
     * @param theImage the <code>BufferedImage</code> being updated.
     * @param minX the X coordinate of the leftmost updated column
     * of pixels.
     * @param minY the Y coordinate of the uppermost updated row
     * of pixels.
     * @param width the number of updated pixels horizontally.
     * @param height the number of updated pixels vertically.
     * @param periodX the horizontal spacing between updated pixels;
     * a value of 1 means no gaps.
     * @param periodY the vertical spacing between updated pixels;
     * a value of 1 means no gaps.
     * @param bands an array of <code>int</code>s indicating which
     * bands are being updated.
     */
    void imageUpdate(ImageReader source,
                     BufferedImage theImage,
                     int minX, int minY,
                     int width, int height,
                     int periodX, int periodY,
                     int[] bands);

    /**
     * Reports that the current read operation has completed a
     * progressive pass.  Readers of formats that support
     * progressive encoding should use this to notify clients when
     * each pass is completed when reading a progressively
     * encoded image.
     *
     * <p>
     * <pre>
     *  (10,21)(12,20)(12,20)(14,20)(10,23)(12,23)(14,23)(10,26)(12,26)(14,26)(10,29) (12,29)(14,29)
     * </pre>
     * 
     * 
     * @param source the <code>ImageReader</code> object calling this
     * method.
     * @param theImage the <code>BufferedImage</code> being updated.
     *
     * @see javax.imageio.ImageReadParam#setSourceProgressivePasses(int, int)
     */
    void passComplete(ImageReader source, BufferedImage theImage);

    /**
     * Reports that the current thumbnail read operation is about to
     * begin a progressive pass.  Readers of formats that support
     * progressive encoding should use this to notify clients when
     * each pass is completed when reading a progressively encoded
     * thumbnail image.
     *
     * <p>
     * 报告当前读取操作已完成渐进式通过。支持渐进式编码的格式的读者应该使用此选项,以便在读取逐行编码的图片时,在每次通过完成时通知客户端。
     * 
     * 
     * @param source the <code>ImageReader</code> object calling this
     * method.
     * @param theThumbnail the <code>BufferedImage</code> thumbnail
     * being updated.
     * @param pass the number of the pass that is about to begin,
     * starting with 0.
     * @param minPass the index of the first pass that will be decoded.
     * @param maxPass the index of the last pass that will be decoded.
     * @param minX the X coordinate of the leftmost updated column
     * of pixels.
     * @param minY the Y coordinate of the uppermost updated row
     * of pixels.
     * @param periodX the horizontal spacing between updated pixels;
     * a value of 1 means no gaps.
     * @param periodY the vertical spacing between updated pixels;
     * a value of 1 means no gaps.
     * @param bands an array of <code>int</code>s indicating the the
     * set bands that may be updated.
     *
     * @see #passStarted
     */
    void thumbnailPassStarted(ImageReader source,
                              BufferedImage theThumbnail,
                              int pass,
                              int minPass, int maxPass,
                              int minX, int minY,
                              int periodX, int periodY,
                              int[] bands);

    /**
     * Reports that a given region of a thumbnail image has been updated.
     * The application might choose to redisplay the specified area,
     * for example, in order to provide a progressive display effect,
     * or perform other incremental processing.
     *
     * <p>
     *  报告当前缩略图读取操作即将开始逐行扫描。支持渐进式编码的格式的读者应该使用此选项来在读取渐进编码的缩略图时完成每次通过时通知客户端。
     * 
     * 
     * @param source the <code>ImageReader</code> object calling this method.
     * @param theThumbnail the <code>BufferedImage</code> thumbnail
     * being updated.
     * @param minX the X coordinate of the leftmost updated column
     * of pixels.
     * @param minY the Y coordinate of the uppermost updated row
     * of pixels.
     * @param width the number of updated pixels horizontally.
     * @param height the number of updated pixels vertically.
     * @param periodX the horizontal spacing between updated pixels;
     * a value of 1 means no gaps.
     * @param periodY the vertical spacing between updated pixels;
     * a value of 1 means no gaps.
     * @param bands an array of <code>int</code>s indicating which
     * bands are being updated.
     *
     * @see #imageUpdate
     */
    void thumbnailUpdate(ImageReader source,
                         BufferedImage theThumbnail,
                         int minX, int minY,
                         int width, int height,
                         int periodX, int periodY,
                         int[] bands);

    /**
     * Reports that the current thumbnail read operation has completed
     * a progressive pass.  Readers of formats that support
     * progressive encoding should use this to notify clients when
     * each pass is completed when reading a progressively encoded
     * thumbnail image.
     *
     * <p>
     *  报告缩略图的给定区域已更新。应用可以选择重新显示指定区域,例如,以便提供渐进显示效果,或者执行其他增量处理。
     * 
     * 
     * @param source the <code>ImageReader</code> object calling this
     * method.
     * @param theThumbnail the <code>BufferedImage</code> thumbnail
     * being updated.
     *
     * @see #passComplete
     */
    void thumbnailPassComplete(ImageReader source, BufferedImage theThumbnail);
}
