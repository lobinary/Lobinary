/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * warnings (non-fatal errors).  Fatal errors cause the relevant
 * read method to throw an <code>IIOException</code>.
 *
 * <p> Localization is handled by associating a <code>Locale</code>
 * with each <code>IIOReadWarningListener</code> as it is registered
 * with an <code>ImageReader</code>.  It is up to the
 * <code>ImageReader</code> to provide localized messages.
 *
 * <p>
 *  <code> ImageReader </code>实现使用的接口,用于通知调用者其图像和警告的缩略图读取方法(非致命错误)。
 * 致命错误导致相关的读取方法抛出一个<code> IIOException </code>。
 * 
 *  <p>通过将<code> Locale </code>与每个<code> IIOReadWarningListener </code>关联,将其注册到<code> ImageReader </code>
 * 中,可以处理本地化。
 * 
 * @see javax.imageio.ImageReader#addIIOReadWarningListener
 * @see javax.imageio.ImageReader#removeIIOReadWarningListener
 *
 */
public interface IIOReadWarningListener extends EventListener {

    /**
     * Reports the occurrence of a non-fatal error in decoding.  Decoding
     * will continue following the call to this method.  The application
     * may choose to display a dialog, print the warning to the console,
     * ignore the warning, or take any other action it chooses.
     *
     * <p>
     * 它由<code> ImageReader </code>提供本地化的消息。
     * 
     * 
     * @param source the <code>ImageReader</code> object calling this method.
     * @param warning a <code>String</code> containing the warning.
     */
    void warningOccurred(ImageReader source, String warning);
}
