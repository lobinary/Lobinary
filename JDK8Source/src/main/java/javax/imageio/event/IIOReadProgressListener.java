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

import java.util.EventListener;
import javax.imageio.ImageReader;

/**
 * An interface used by <code>ImageReader</code> implementations to
 * notify callers of their image and thumbnail reading methods of
 * progress.
 *
 * <p> This interface receives general indications of decoding
 * progress (via the <code>imageProgress</code> and
 * <code>thumbnailProgress</code> methods), and events indicating when
 * an entire image has been updated (via the
 * <code>imageStarted</code>, <code>imageComplete</code>,
 * <code>thumbnailStarted</code> and <code>thumbnailComplete</code>
 * methods).  Applications that wish to be informed of pixel updates
 * as they happen (for example, during progressive decoding), should
 * provide an <code>IIOReadUpdateListener</code>.
 *
 * <p>
 *  <code> ImageReader </code>实现使用的接口,通知调用者其图像和缩略图读取进度方法。
 * 
 *  <p>此接口接收解码进度的一般指示(通过<code> imageProgress </code>和<code> thumbnailProgress </code>方法),以及指示整个图像何时更新的事件
 * (通过<code> imageStarted </code>,<code> imageComplete </code>,<code> thumbnailStarted </code>和<code> th
 * umbnailComplete </code>方法)。
 * 希望在像素发生时(例如,在渐进解码期间)被通知像素更新的应用应当提供<code> IIOReadUpdateListener </code>。
 * 
 * 
 * @see IIOReadUpdateListener
 * @see javax.imageio.ImageReader#addIIOReadProgressListener
 * @see javax.imageio.ImageReader#removeIIOReadProgressListener
 *
 */
public interface IIOReadProgressListener extends EventListener {

    /**
     * Reports that a sequence of read operations is beginning.
     * <code>ImageReader</code> implementations are required to call
     * this method exactly once from their
     * <code>readAll(Iterator)</code> method.
     *
     * <p>
     *  报告读操作序列正在开始。需要从<code> readAll(Iterator)</code>方法调用此方法一次,才需要执行<code> ImageReader </code>实现。
     * 
     * 
     * @param source the <code>ImageReader</code> object calling this method.
     * @param minIndex the index of the first image to be read.
     */
    void sequenceStarted(ImageReader source, int minIndex);

    /**
     * Reports that a sequence of read operations has completed.
     * <code>ImageReader</code> implementations are required to call
     * this method exactly once from their
     * <code>readAll(Iterator)</code> method.
     *
     * <p>
     *  报告读取操作序列已完成。需要从<code> readAll(Iterator)</code>方法调用此方法一次,才需要执行<code> ImageReader </code>实现。
     * 
     * 
     * @param source the <code>ImageReader</code> object calling this method.
     */
    void sequenceComplete(ImageReader source);

    /**
     * Reports that an image read operation is beginning.  All
     * <code>ImageReader</code> implementations are required to call
     * this method exactly once when beginning an image read
     * operation.
     *
     * <p>
     *  报告图像读取操作正在开始。当开始图像读取操作时,需要所有<code> ImageReader </code>实现一次调用此方法。
     * 
     * 
     * @param source the <code>ImageReader</code> object calling this method.
     * @param imageIndex the index of the image being read within its
     * containing input file or stream.
     */
    void imageStarted(ImageReader source, int imageIndex);

    /**
     * Reports the approximate degree of completion of the current
     * <code>read</code> call of the associated
     * <code>ImageReader</code>.
     *
     * <p> The degree of completion is expressed as a percentage
     * varying from <code>0.0F</code> to <code>100.0F</code>.  The
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
     * <p> Each particular <code>ImageReader</code> implementation may
     * call this method at whatever frequency it desires.  A rule of
     * thumb is to call it around each 5 percent mark.
     *
     * <p>
     * 报告关联的<code> ImageReader </code>的当前<code>读取</code>调用的大致完成程度。
     * 
     *  <p>完成度表示为从<code> 0.0F </code>变为<code> 100.0F </code>的百分比。
     * 理想地,百分比应该根据剩余完成时间来计算,但是通常更加实际的是使用更精确定义的度量,诸如解码的像素或消耗的输入流的部分。在任何情况下,在给定读操作期间对该方法的调用序列应当提供单调递增的百分比值序列。
     * 没有必要提供<code> 0 </code>和<code> 100 </code>的确切值,因为这些可以由被调用者从其他方法中推断出来。
     * 
     *  <p>每个特定的<code> ImageReader </code>实现都可以按照它想要的任何频率调用这个方法。一个经验法则是每个5％的标记。
     * 
     * 
     * @param source the <code>ImageReader</code> object calling this method.
     * @param percentageDone the approximate percentage of decoding that
     * has been completed.
     */
    void imageProgress(ImageReader source, float percentageDone);

    /**
     * Reports that the current image read operation has completed.
     * All <code>ImageReader</code> implementations are required to
     * call this method exactly once upon completion of each image
     * read operation.
     *
     * <p>
     *  报告当前图像读取操作已完成。在每次图像读取操作完成时,需要所有<code> ImageReader </code>实现一次调用该方法。
     * 
     * 
     * @param source the <code>ImageReader</code> object calling this
     * method.
     */
    void imageComplete(ImageReader source);

    /**
     * Reports that a thumbnail read operation is beginning.  All
     * <code>ImageReader</code> implementations are required to call
     * this method exactly once when beginning a thumbnail read
     * operation.
     *
     * <p>
     *  报告缩略图读取操作正在开始。当开始缩略图读取操作时,需要所有<code> ImageReader </code>实现一次调用此方法。
     * 
     * 
     * @param source the <code>ImageReader</code> object calling this method.
     * @param imageIndex the index of the image being read within its
     * containing input file or stream.
     * @param thumbnailIndex the index of the thumbnail being read.
     */
    void thumbnailStarted(ImageReader source,
                          int imageIndex, int thumbnailIndex);

    /**
     * Reports the approximate degree of completion of the current
     * <code>getThumbnail</code> call within the associated
     * <code>ImageReader</code>.  The semantics are identical to those
     * of <code>imageProgress</code>.
     *
     * <p>
     * 在关联的<code> ImageReader </code>中报告当前<code> getThumbnail </code>调用的大致完成程度。
     * 语义与<code> imageProgress </code>的语义相同。
     * 
     * 
     * @param source the <code>ImageReader</code> object calling this method.
     * @param percentageDone the approximate percentage of decoding that
     * has been completed.
     */
    void thumbnailProgress(ImageReader source, float percentageDone);

    /**
     * Reports that a thumbnail read operation has completed.  All
     * <code>ImageReader</code> implementations are required to call
     * this method exactly once upon completion of each thumbnail read
     * operation.
     *
     * <p>
     *  报告缩略图读取操作已完成。在完成每个缩略图读取操作后,需要所有<code> ImageReader </code>实现一次调用此方法。
     * 
     * 
     * @param source the <code>ImageReader</code> object calling this
     * method.
     */
    void thumbnailComplete(ImageReader source);

    /**
     * Reports that a read has been aborted via the reader's
     * <code>abort</code> method.  No further notifications will be
     * given.
     *
     * <p>
     *  报告读取已通过读取器的<code> abort </code>方法中止。不会提供进一步的通知。
     * 
     * @param source the <code>ImageReader</code> object calling this
     * method.
     */
    void readAborted(ImageReader source);
}
