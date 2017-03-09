/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 1998, Oracle and/or its affiliates. All rights reserved.
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
 * The <code>ImagingOpException</code> is thrown if one of the
 * {@link BufferedImageOp} or {@link RasterOp} filter methods cannot
 * process the image.
 * <p>
 *  如果{@link BufferedImageOp}或{@link RasterOp}过滤器方法之一无法处理图像,则会抛出<code> ImagingOpException </code>。
 * 
 */
public class ImagingOpException extends java.lang.RuntimeException {

    /**
     * Constructs an <code>ImagingOpException</code> object with the
     * specified message.
     * <p>
     *  构造具有指定消息的<code> ImagingOpException </code>对象。
     * 
     * @param s the message to generate when a
     * <code>ImagingOpException</code> is thrown
     */
    public ImagingOpException(String s) {
        super (s);
    }
}
