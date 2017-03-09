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

/**
 * The interface for objects which can produce the image data for Images.
 * Each image contains an ImageProducer which is used to reconstruct
 * the image whenever it is needed, for example, when a new size of the
 * Image is scaled, or when the width or height of the Image is being
 * requested.
 *
 * <p>
 *  可以为图像生成图像数据的对象的界面。每个图像包含ImageProducer,每当需要时,用于重建图像,例如当图像的新尺寸被缩放时,或当正在请求图像的宽度或高度时。
 * 
 * 
 * @see ImageConsumer
 *
 * @author      Jim Graham
 */
public interface ImageProducer {
    /**
     * Registers an <code>ImageConsumer</code> with the
     * <code>ImageProducer</code> for access to the image data
     * during a later reconstruction of the <code>Image</code>.
     * The <code>ImageProducer</code> may, at its discretion,
     * start delivering the image data to the consumer
     * using the <code>ImageConsumer</code> interface immediately,
     * or when the next available image reconstruction is triggered
     * by a call to the <code>startProduction</code> method.
     * <p>
     *  在<code> ImageProducer </code>中注册<code> ImageConsumer </code>,以在稍后重建<code> Image </code>期间访问图像数据。
     *  <code> ImageProducer </code>可以自行决定,立即使用<code> ImageConsumer </code>接口开始将图像数据传递给消费者,或者当下一个可用的图像重建由调用<code>
     *  startProduction </code>方法。
     *  在<code> ImageProducer </code>中注册<code> ImageConsumer </code>,以在稍后重建<code> Image </code>期间访问图像数据。
     * 
     * 
     * @param ic the specified <code>ImageConsumer</code>
     * @see #startProduction
     */
    public void addConsumer(ImageConsumer ic);

    /**
     * Determines if a specified <code>ImageConsumer</code>
     * object is currently registered with this
     * <code>ImageProducer</code> as one of its consumers.
     * <p>
     *  确定指定的<code> ImageConsumer </code>对象当前是否已注册此<code> ImageProducer </code>作为其消费者之一。
     * 
     * 
     * @param ic the specified <code>ImageConsumer</code>
     * @return <code>true</code> if the specified
     *         <code>ImageConsumer</code> is registered with
     *         this <code>ImageProducer</code>;
     *         <code>false</code> otherwise.
     */
    public boolean isConsumer(ImageConsumer ic);

    /**
     * Removes the specified <code>ImageConsumer</code> object
     * from the list of consumers currently registered to
     * receive image data.  It is not considered an error
     * to remove a consumer that is not currently registered.
     * The <code>ImageProducer</code> should stop sending data
     * to this consumer as soon as is feasible.
     * <p>
     *  从当前注册为接收图像数据的消费者列表中删除指定的<code> ImageConsumer </code>对象。删除当前未注册的消费者不被视为错误。
     *  <code> ImageProducer </code>应该尽快停止向此消费者发送数据。
     * 
     * 
     * @param ic the specified <code>ImageConsumer</code>
     */
    public void removeConsumer(ImageConsumer ic);

    /**
     * Registers the specified <code>ImageConsumer</code> object
     * as a consumer and starts an immediate reconstruction of
     * the image data which will then be delivered to this
     * consumer and any other consumer which might have already
     * been registered with the producer.  This method differs
     * from the addConsumer method in that a reproduction of
     * the image data should be triggered as soon as possible.
     * <p>
     * 将指定的<code> ImageConsumer </code>对象注册为消费者,并开始立即重建图像数据,然后将其传送到此消费者和可能已经向生产者注册的任何其他消费者。
     * 该方法与addConsumer方法的不同之处在于,应当尽可能快地触发图像数据的再现。
     * 
     * 
     * @param ic the specified <code>ImageConsumer</code>
     * @see #addConsumer
     */
    public void startProduction(ImageConsumer ic);

    /**
     * Requests, on behalf of the <code>ImageConsumer</code>,
     * that the <code>ImageProducer</code> attempt to resend
     * the image data one more time in TOPDOWNLEFTRIGHT order
     * so that higher quality conversion algorithms which
     * depend on receiving pixels in order can be used to
     * produce a better output version of the image.  The
     * <code>ImageProducer</code> is free to
     * ignore this call if it cannot resend the data in that
     * order.  If the data can be resent, the
     * <code>ImageProducer</code> should respond by executing
     * the following minimum set of <code>ImageConsumer</code>
     * method calls:
     * <pre>{@code
     *  ic.setHints(TOPDOWNLEFTRIGHT | < otherhints >);
     *  ic.setPixels(...);      // As many times as needed
     *  ic.imageComplete();
     * }</pre>
     * <p>
     *  请求代表<code> ImageConsumer </code>,<code> ImageProducer </code>尝试以TOPDOWNLEFTRIGHT顺序再次发送图像数据一次,以便依赖于接收
     * 像素的更高质量的转换算法可以用于产生更好的输出版本的图像。
     * 如果<code> ImageProducer </code>无法按此顺序重新发送数据,则可以忽略此调用。
     * 如果可以重新发送数据,<code> ImageProducer </code>应该通过执行以下最小的<code> ImageConsumer </code>方法调用来响应：<pre> {@ code ic.setHints(TOPDOWNLEFTRIGHT | <otherhints >); ic.setPixels(...); //根据需要多次ic.imageComplete(); }
     * 
     * @param ic the specified <code>ImageConsumer</code>
     * @see ImageConsumer#setHints
     */
    public void requestTopDownLeftRightResend(ImageConsumer ic);
}
