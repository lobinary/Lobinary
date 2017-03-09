/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, Oracle and/or its affiliates. All rights reserved.
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

package javax.imageio.spi;

import javax.imageio.ImageTranscoder;

/**
 * The service provider interface (SPI) for <code>ImageTranscoder</code>s.
 * For more information on service provider classes, see the class comment
 * for the <code>IIORegistry</code> class.
 *
 * <p>
 *  用于<code> ImageTranscoder </code>的服务提供程序接口(SPI)。有关服务提供程序类的更多信息,请参阅<code> IIORegistry </code>类的类注释。
 * 
 * 
 * @see IIORegistry
 * @see javax.imageio.ImageTranscoder
 *
 */
public abstract class ImageTranscoderSpi extends IIOServiceProvider {

    /**
     * Constructs a blank <code>ImageTranscoderSpi</code>.  It is up
     * to the subclass to initialize instance variables and/or
     * override method implementations in order to provide working
     * versions of all methods.
     * <p>
     *  构造一个空白的<code> ImageTranscoderSpi </code>。它是由子类初始化实例变量和/或覆盖方法实现为了提供所有方法的工作版本。
     * 
     */
    protected ImageTranscoderSpi() {
    }

    /**
     * Constructs an <code>ImageTranscoderSpi</code> with a given set
     * of values.
     *
     * <p>
     *  用给定的一组值构造一个<code> ImageTranscoderSpi </code>。
     * 
     * 
     * @param vendorName the vendor name.
     * @param version a version identifier.
     */
    public ImageTranscoderSpi(String vendorName,
                              String version) {
        super(vendorName, version);
    }

    /**
     * Returns the fully qualified class name of an
     * <code>ImageReaderSpi</code> class that generates
     * <code>IIOMetadata</code> objects that may be used as input to
     * this transcoder.
     *
     * <p>
     *  返回可生成可用作此代码转换器输入的<code> IIOMetadata </code>对象的<code> ImageReaderSpi </code>类的完全限定类名。
     * 
     * 
     * @return a <code>String</code> containing the fully-qualified
     * class name of the <code>ImageReaderSpi</code> implementation class.
     *
     * @see ImageReaderSpi
     */
    public abstract String getReaderServiceProviderName();

    /**
     * Returns the fully qualified class name of an
     * <code>ImageWriterSpi</code> class that generates
     * <code>IIOMetadata</code> objects that may be used as input to
     * this transcoder.
     *
     * <p>
     *  返回可生成可用作此代码转换器输入的<code> IIOMetadata </code>对象的<code> ImageWriterSpi </code>类的完全限定类名。
     * 
     * 
     * @return a <code>String</code> containing the fully-qualified
     * class name of the <code>ImageWriterSpi</code> implementation class.
     *
     * @see ImageWriterSpi
     */
    public abstract String getWriterServiceProviderName();

    /**
     * Returns an instance of the <code>ImageTranscoder</code>
     * implementation associated with this service provider.
     *
     * <p>
     *  返回与此服务提供商相关联的<code> ImageTranscoder </code>实现的实例。
     * 
     * @return an <code>ImageTranscoder</code> instance.
     */
    public abstract ImageTranscoder createTranscoderInstance();
}
