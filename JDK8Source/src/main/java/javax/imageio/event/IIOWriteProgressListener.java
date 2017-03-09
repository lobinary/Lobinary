/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2001, Oracle and/or its affiliates. All rights reserved.
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

import java.util.EventListener;
import javax.imageio.ImageWriter;

/**
 * An interface used by <code>ImageWriter</code> implementations to notify
 * callers of their image writing methods of progress.
 *
 * <p>
 *  <code> ImageWriter </code>实现使用的接口,通知调用者他们的图像写入方法的进度。
 * 
 * 
 * @see javax.imageio.ImageWriter#write
 *
 */
public interface IIOWriteProgressListener extends EventListener {

    /**
     * Reports that an image write operation is beginning.  All
     * <code>ImageWriter</code> implementations are required to call
     * this method exactly once when beginning an image write
     * operation.
     *
     * <p>
     *  报告图像写入操作开始。当开始图像写入操作时,需要所有<code> ImageWriter </code>实现一次调用此方法。
     * 
     * 
     * @param source the <code>ImageWriter</code> object calling this
     * method.
     * @param imageIndex the index of the image being written within
     * its containing input file or stream.
     */
    void imageStarted(ImageWriter source, int imageIndex);

    /**
     * Reports the approximate degree of completion of the current
     * <code>write</code> call within the associated
     * <code>ImageWriter</code>.
     *
     * <p> The degree of completion is expressed as an index
     * indicating which image is being written, and a percentage
     * varying from <code>0.0F</code> to <code>100.0F</code>
     * indicating how much of the current image has been output.  The
     * percentage should ideally be calculated in terms of the
     * remaining time to completion, but it is usually more practical
     * to use a more well-defined metric such as pixels decoded or
     * portion of input stream consumed.  In any case, a sequence of
     * calls to this method during a given read operation should
     * supply a monotonically increasing sequence of percentage
     * values.  It is not necessary to supply the exact values
     * <code>0</code> and <code>100</code>, as these may be inferred
     * by the callee from other methods.
     *
     * <p> Each particular <code>ImageWriter</code> implementation may
     * call this method at whatever frequency it desires.  A rule of
     * thumb is to call it around each 5 percent mark.
     *
     * <p>
     *  在相关的<code> ImageWriter </code>中报告当前<code> write </code>调用的大致完成程度。
     * 
     *  <p>完成度表示为指示正在写入哪个图像的索引,以及从<code> 0.0F </code>到<code> 100.0F </code>变化的百分比,表示当前图像的多少已输出。
     * 理想地,百分比应该根据剩余完成时间来计算,但是通常更加实际的是使用更精确定义的度量,诸如解码的像素或消耗的输入流的部分。在任何情况下,在给定读操作期间对该方法的调用序列应当提供单调递增的百分比值序列。
     * 没有必要提供<code> 0 </code>和<code> 100 </code>的确切值,因为这些可以由被调用者从其他方法中推断出来。
     * 
     *  <p>每个特定的<code> ImageWriter </code>实现都可以以任何所需的频率调用此方法。一个经验法则是每个5％的标记。
     * 
     * 
     * @param source the <code>ImageWriter</code> object calling this method.
     * @param percentageDone the approximate percentage of decoding that
     * has been completed.
     */
    void imageProgress(ImageWriter source,
                       float percentageDone);

    /**
     * Reports that the image write operation has completed.  All
     * <code>ImageWriter</code> implementations are required to call
     * this method exactly once upon completion of each image write
     * operation.
     *
     * <p>
     * 报告图像写入操作已完成。在每次图像写入操作完成时,需要所有<code> ImageWriter </code>实现一次调用此方法。
     * 
     * 
     * @param source the <code>ImageWriter</code> object calling this method.
     */
    void imageComplete(ImageWriter source);

    /**
     * Reports that a thumbnail write operation is beginning.  All
     * <code>ImageWriter</code> implementations are required to call
     * this method exactly once when beginning a thumbnail write
     * operation.
     *
     * <p>
     *  缩略图写入操作开始的报告。当开始缩略图写操作时,需要所有<code> ImageWriter </code>实现一次调用此方法。
     * 
     * 
     * @param source the <code>ImageWrite</code> object calling this method.
     * @param imageIndex the index of the image being written within its
     * containing input file or stream.
     * @param thumbnailIndex the index of the thumbnail being written.
     */
    void thumbnailStarted(ImageWriter source,
                          int imageIndex, int thumbnailIndex);

    /**
     * Reports the approximate degree of completion of the current
     * thumbnail write within the associated <code>ImageWriter</code>.
     * The semantics are identical to those of
     * <code>imageProgress</code>.
     *
     * <p>
     *  在相关的<code> ImageWriter </code>中报告当前缩略图写入的大致完成度。语义与<code> imageProgress </code>的语义相同。
     * 
     * 
     * @param source the <code>ImageWriter</code> object calling this
     * method.
     * @param percentageDone the approximate percentage of decoding that
     * has been completed.
     */
    void thumbnailProgress(ImageWriter source, float percentageDone);

    /**
     * Reports that a thumbnail write operation has completed.  All
     * <code>ImageWriter</code> implementations are required to call
     * this method exactly once upon completion of each thumbnail
     * write operation.
     *
     * <p>
     *  报告缩略图写入操作已完成。所有<code> ImageWriter </code>实现都需要在每个缩略图写操作完成时调用此方法一次。
     * 
     * 
     * @param source the <code>ImageWriter</code> object calling this
     * method.
     */
    void thumbnailComplete(ImageWriter source);

    /**
     * Reports that a write has been aborted via the writer's
     * <code>abort</code> method.  No further notifications will be
     * given.
     *
     * <p>
     *  报告写入已通过写入程序的<code> abort </code>方法中止。不会提供进一步的通知。
     * 
     * @param source the <code>ImageWriter</code> object calling this
     * method.
     */
    void writeAborted(ImageWriter source);
}
