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

package javax.imageio.spi;

import java.awt.image.RenderedImage;
import java.io.IOException;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

/**
 * The service provider interface (SPI) for <code>ImageWriter</code>s.
 * For more information on service provider classes, see the class comment
 * for the <code>IIORegistry</code> class.
 *
 * <p> Each <code>ImageWriterSpi</code> provides several types of information
 * about the <code>ImageWriter</code> class with which it is associated.
 *
 * <p> The name of the vendor who defined the SPI class and a
 * brief description of the class are available via the
 * <code>getVendorName</code>, <code>getDescription</code>,
 * and <code>getVersion</code> methods.
 * These methods may be internationalized to provide locale-specific
 * output.  These methods are intended mainly to provide short,
 * human-writable information that might be used to organize a pop-up
 * menu or other list.
 *
 * <p> Lists of format names, file suffixes, and MIME types associated
 * with the service may be obtained by means of the
 * <code>getFormatNames</code>, <code>getFileSuffixes</code>, and
 * <code>getMIMEType</code> methods.  These methods may be used to
 * identify candidate <code>ImageWriter</code>s for writing a
 * particular file or stream based on manual format selection, file
 * naming, or MIME associations.
 *
 * <p> A more reliable way to determine which <code>ImageWriter</code>s
 * are likely to be able to parse a particular data stream is provided
 * by the <code>canEncodeImage</code> method.  This methods allows the
 * service provider to inspect the actual image contents.
 *
 * <p> Finally, an instance of the <code>ImageWriter</code> class
 * associated with this service provider may be obtained by calling
 * the <code>createWriterInstance</code> method.  Any heavyweight
 * initialization, such as the loading of native libraries or creation
 * of large tables, should be deferred at least until the first
 * invocation of this method.
 *
 * <p>
 *  用于<code> ImageWriter </code>的服务提供程序接口(SPI)。有关服务提供程序类的更多信息,请参阅<code> IIORegistry </code>类的类注释。
 * 
 *  <p>每个<code> ImageWriterSpi </code>提供了与它相关的<code> ImageWriter </code>类的几种类型的信息。
 * 
 *  <p>通过<code> getVendorName </code>,<code> getDescription </code>和<code> getVersion </code可以获得定义SPI类的供应商的名称和类的简要描述>
 * 方法。
 * 这些方法可以被国际化以提供特定于场所的输出。这些方法主要旨在提供可用于组织弹出菜单或其他列表的短的,人类可写的信息。
 * 
 *  <p>与服务相关联的格式名称,文件后缀和MIME类型的列表可以通过<code> getFormatNames </code>,<code> getFileSuffixes </code>和<code>
 *  getMIMEType </code>代码>方法。
 * 这些方法可以用于基于手动格式选择,文件命名或MIME关联来识别用于写入特定文件或流的候选<code> ImageWriter </code>。
 * 
 * <p>通过<code> canEncodeImage </code>方法提供了一种更可靠的方法来确定哪些<code> ImageWriter </code>可能能够解析特定数据流。
 * 这种方法允许服务提供商检查实际图像内容。
 * 
 *  <p>最后,可以通过调用<code> createWriterInstance </code>方法获得与此服务提供程序关联的<code> ImageWriter </code>类的实例。
 * 任何重量级初始化,例如加载本机库或创建大表,都应该推迟,直到第一次调用此方法。
 * 
 * 
 * @see IIORegistry
 * @see javax.imageio.ImageTypeSpecifier
 * @see javax.imageio.ImageWriter
 *
 */
public abstract class ImageWriterSpi extends ImageReaderWriterSpi {

    /**
     * A single-element array, initially containing
     * <code>ImageOutputStream.class</code>, to be returned from
     * <code>getOutputTypes</code>.
     * <p>
     *  一个单元素数组,最初包含<code> ImageOutputStream.class </code>,从<code> getOutputTypes </code>返回。
     * 
     * 
     * @deprecated Instead of using this field, directly create
     * the equivalent array <code>{ ImageOutputStream.class }</code>.
     */
    @Deprecated
    public static final Class[] STANDARD_OUTPUT_TYPE =
        { ImageOutputStream.class };

    /**
     * An array of <code>Class</code> objects to be returned from
     * <code>getOutputTypes</code>, initially <code>null</code>.
     * <p>
     *  从<code> getOutputTypes </code>返回的<code> Class </code>对象数组,最初为<code> null </code>。
     * 
     */
    protected Class[] outputTypes = null;

    /**
     * An array of strings to be returned from
     * <code>getImageReaderSpiNames</code>, initially
     * <code>null</code>.
     * <p>
     *  要从<code> getImageReaderSpiNames </code>返回的字符串数组,最初为<code> null </code>。
     * 
     */
    protected String[] readerSpiNames = null;

    /**
     * The <code>Class</code> of the writer, initially
     * <code>null</code>.
     * <p>
     *  作者的<code> Class </code>,最初为<code> null </code>。
     * 
     */
    private Class writerClass = null;

    /**
     * Constructs a blank <code>ImageWriterSpi</code>.  It is up to
     * the subclass to initialize instance variables and/or override
     * method implementations in order to provide working versions of
     * all methods.
     * <p>
     *  构造一个空白<code> ImageWriterSpi </code>。它是由子类初始化实例变量和/或覆盖方法实现为了提供所有方法的工作版本。
     * 
     */
    protected ImageWriterSpi() {
    }

    /**
     * Constructs an <code>ImageWriterSpi</code> with a given
     * set of values.
     *
     * <p>
     *  用给定的一组值构造一个<code> ImageWriterSpi </code>。
     * 
     * 
     * @param vendorName the vendor name, as a non-<code>null</code>
     * <code>String</code>.
     * @param version a version identifier, as a non-<code>null</code>
     * <code>String</code>.
     * @param names a non-<code>null</code> array of
     * <code>String</code>s indicating the format names.  At least one
     * entry must be present.
     * @param suffixes an array of <code>String</code>s indicating the
     * common file suffixes.  If no suffixes are defined,
     * <code>null</code> should be supplied.  An array of length 0
     * will be normalized to <code>null</code>.
     * @param MIMETypes an array of <code>String</code>s indicating
     * the format's MIME types.  If no suffixes are defined,
     * <code>null</code> should be supplied.  An array of length 0
     * will be normalized to <code>null</code>.
     * @param writerClassName the fully-qualified name of the
     * associated <code>ImageWriterSpi</code> class, as a
     * non-<code>null</code> <code>String</code>.
     * @param outputTypes an array of <code>Class</code> objects of
     * length at least 1 indicating the legal output types.
     * @param readerSpiNames an array <code>String</code>s of length
     * at least 1 naming the classes of all associated
     * <code>ImageReader</code>s, or <code>null</code>.  An array of
     * length 0 is normalized to <code>null</code>.
     * @param supportsStandardStreamMetadataFormat a
     * <code>boolean</code> that indicates whether a stream metadata
     * object can use trees described by the standard metadata format.
     * @param nativeStreamMetadataFormatName a
     * <code>String</code>, or <code>null</code>, to be returned from
     * <code>getNativeStreamMetadataFormatName</code>.
     * @param nativeStreamMetadataFormatClassName a
     * <code>String</code>, or <code>null</code>, to be used to instantiate
     * a metadata format object to be returned from
     * <code>getNativeStreamMetadataFormat</code>.
     * @param extraStreamMetadataFormatNames an array of
     * <code>String</code>s, or <code>null</code>, to be returned from
     * <code>getExtraStreamMetadataFormatNames</code>.  An array of length
     * 0 is normalized to <code>null</code>.
     * @param extraStreamMetadataFormatClassNames an array of
     * <code>String</code>s, or <code>null</code>, to be used to instantiate
     * a metadata format object to be returned from
     * <code>getStreamMetadataFormat</code>.  An array of length
     * 0 is normalized to <code>null</code>.
     * @param supportsStandardImageMetadataFormat a
     * <code>boolean</code> that indicates whether an image metadata
     * object can use trees described by the standard metadata format.
     * @param nativeImageMetadataFormatName a
     * <code>String</code>, or <code>null</code>, to be returned from
     * <code>getNativeImageMetadataFormatName</code>.
     * @param nativeImageMetadataFormatClassName a
     * <code>String</code>, or <code>null</code>, to be used to instantiate
     * a metadata format object to be returned from
     * <code>getNativeImageMetadataFormat</code>.
     * @param extraImageMetadataFormatNames an array of
     * <code>String</code>s to be returned from
     * <code>getExtraImageMetadataFormatNames</code>.  An array of length 0
     * is normalized to <code>null</code>.
     * @param extraImageMetadataFormatClassNames an array of
     * <code>String</code>s, or <code>null</code>, to be used to instantiate
     * a metadata format object to be returned from
     * <code>getImageMetadataFormat</code>.  An array of length
     * 0 is normalized to <code>null</code>.
     *
     * @exception IllegalArgumentException if <code>vendorName</code>
     * is <code>null</code>.
     * @exception IllegalArgumentException if <code>version</code>
     * is <code>null</code>.
     * @exception IllegalArgumentException if <code>names</code>
     * is <code>null</code> or has length 0.
     * @exception IllegalArgumentException if <code>writerClassName</code>
     * is <code>null</code>.
     * @exception IllegalArgumentException if <code>outputTypes</code>
     * is <code>null</code> or has length 0.
     */
    public ImageWriterSpi(String vendorName,
                          String version,
                          String[] names,
                          String[] suffixes,
                          String[] MIMETypes,
                          String writerClassName,
                          Class[] outputTypes,
                          String[] readerSpiNames,
                          boolean supportsStandardStreamMetadataFormat,
                          String nativeStreamMetadataFormatName,
                          String nativeStreamMetadataFormatClassName,
                          String[] extraStreamMetadataFormatNames,
                          String[] extraStreamMetadataFormatClassNames,
                          boolean supportsStandardImageMetadataFormat,
                          String nativeImageMetadataFormatName,
                          String nativeImageMetadataFormatClassName,
                          String[] extraImageMetadataFormatNames,
                          String[] extraImageMetadataFormatClassNames) {
        super(vendorName, version,
              names, suffixes, MIMETypes, writerClassName,
              supportsStandardStreamMetadataFormat,
              nativeStreamMetadataFormatName,
              nativeStreamMetadataFormatClassName,
              extraStreamMetadataFormatNames,
              extraStreamMetadataFormatClassNames,
              supportsStandardImageMetadataFormat,
              nativeImageMetadataFormatName,
              nativeImageMetadataFormatClassName,
              extraImageMetadataFormatNames,
              extraImageMetadataFormatClassNames);

        if (outputTypes == null) {
            throw new IllegalArgumentException
                ("outputTypes == null!");
        }
        if (outputTypes.length == 0) {
            throw new IllegalArgumentException
                ("outputTypes.length == 0!");
        }

        this.outputTypes = (outputTypes == STANDARD_OUTPUT_TYPE) ?
            new Class<?>[] { ImageOutputStream.class } :
            outputTypes.clone();

        // If length == 0, leave it null
        if (readerSpiNames != null && readerSpiNames.length > 0) {
            this.readerSpiNames = (String[])readerSpiNames.clone();
        }
    }

    /**
     * Returns <code>true</code> if the format that this writer
     * outputs preserves pixel data bit-accurately.  The default
     * implementation returns <code>true</code>.
     *
     * <p>
     *  如果该写入程序输出的格式准确地保留像素数据,则返回<code> true </code>。默认实现返回<code> true </code>。
     * 
     * 
     * @return <code>true</code> if the format preserves full pixel
     * accuracy.
     */
    public boolean isFormatLossless() {
        return true;
    }

    /**
     * Returns an array of <code>Class</code> objects indicating what
     * types of objects may be used as arguments to the writer's
     * <code>setOutput</code> method.
     *
     * <p> For most writers, which only output to an
     * <code>ImageOutputStream</code>, a single-element array
     * containing <code>ImageOutputStream.class</code> should be
     * returned.
     *
     * <p>
     * 返回一个<code> Class </code>对象的数组,表示可以用作写入器的<code> setOutput </code>方法的参数的对象类型。
     * 
     *  <p>对于大多数只输出到<code> ImageOutputStream </code>的写入程序,应该返回包含<code> ImageOutputStream.class </code>的单元素数组
     * 。
     * 
     * 
     * @return a non-<code>null</code> array of
     * <code>Class</code>objects of length at least 1.
     */
    public Class[] getOutputTypes() {
        return (Class[])outputTypes.clone();
    }

    /**
     * Returns <code>true</code> if the <code>ImageWriter</code>
     * implementation associated with this service provider is able to
     * encode an image with the given layout.  The layout
     * (<i>i.e.</i>, the image's <code>SampleModel</code> and
     * <code>ColorModel</code>) is described by an
     * <code>ImageTypeSpecifier</code> object.
     *
     * <p> A return value of <code>true</code> is not an absolute
     * guarantee of successful encoding; the encoding process may still
     * produce errors due to factors such as I/O errors, inconsistent
     * or malformed data structures, etc.  The intent is that a
     * reasonable inspection of the basic structure of the image be
     * performed in order to determine if it is within the scope of
     * the encoding format.  For example, a service provider for a
     * format that can only encode greyscale would return
     * <code>false</code> if handed an RGB <code>BufferedImage</code>.
     * Similarly, a service provider for a format that can encode
     * 8-bit RGB imagery might refuse to encode an image with an
     * associated alpha channel.
     *
     * <p> Different <code>ImageWriter</code>s, and thus service
     * providers, may choose to be more or less strict.  For example,
     * they might accept an image with premultiplied alpha even though
     * it will have to be divided out of each pixel, at some loss of
     * precision, in order to be stored.
     *
     * <p>
     *  如果与此服务提供程序相关联的<code> ImageWriter </code>实现能够编码具有给定布局的图像,则返回<code> true </code>。
     * 通过<code> ImageTypeSpecifier </code>对象描述布局(<i> ie </i>,图像的<code> SampleModel </code>和<code> ColorModel
     *  </code>)。
     *  如果与此服务提供程序相关联的<code> ImageWriter </code>实现能够编码具有给定布局的图像,则返回<code> true </code>。
     * 
     *  <p> <code> true </code>的返回值不是成功编码的绝对保证;编码过程仍然可能由于诸如I / O错误,不一致或畸形的数据结构等因素而产生错误。
     * 意图是执行对图像的基本结构的合理检查,以便确定它是否在编码格式的范围。
     * 例如,对于只能编码灰度的格式的服务提供商,如果交给RGB <code> BufferedImage </code>,则返回<code> false </code>。
     * 类似地,用于可以编码8位RGB图像的格式的服务提供商可以拒绝使用相关联的alpha通道对图像进行编码。
     * 
     * <p>不同的<code> ImageWriter </code>,因此服务提供商可能选择或多或少严格。例如,它们可以接受具有预乘α的图像,即使它将必须在某些精度损失时被从每个像素中划分出来以便存储。
     * 
     * 
     * @param type an <code>ImageTypeSpecifier</code> specifying the
     * layout of the image to be written.
     *
     * @return <code>true</code> if this writer is likely to be able
     * to encode images with the given layout.
     *
     * @exception IllegalArgumentException if <code>type</code>
     * is <code>null</code>.
     */
    public abstract boolean canEncodeImage(ImageTypeSpecifier type);

    /**
     * Returns <code>true</code> if the <code>ImageWriter</code>
     * implementation associated with this service provider is able to
     * encode the given <code>RenderedImage</code> instance.  Note
     * that this includes instances of
     * <code>java.awt.image.BufferedImage</code>.
     *
     * <p> See the discussion for
     * <code>canEncodeImage(ImageTypeSpecifier)</code> for information
     * on the semantics of this method.
     *
     * <p>
     *  如果与此服务提供程序相关联的<code> ImageWriter </code>实现能够对给定的<code> RenderedImage </code>实例进行编码,则返回<code> true </code>
     * 请注意,这包括<code> java.awt.image.BufferedImage </code>的实例。
     * 
     *  <p>有关此方法的语义的信息,请参阅<code> canEncodeImage(ImageTypeSpecifier)</code>的讨论。
     * 
     * 
     * @param im an instance of <code>RenderedImage</code> to be encoded.
     *
     * @return <code>true</code> if this writer is likely to be able
     * to encode this image.
     *
     * @exception IllegalArgumentException if <code>im</code>
     * is <code>null</code>.
     */
    public boolean canEncodeImage(RenderedImage im) {
        return canEncodeImage(ImageTypeSpecifier.createFromRenderedImage(im));
    }

    /**
     * Returns an instance of the <code>ImageWriter</code>
     * implementation associated with this service provider.
     * The returned object will initially be in an initial state as if
     * its <code>reset</code> method had been called.
     *
     * <p> The default implementation simply returns
     * <code>createWriterInstance(null)</code>.
     *
     * <p>
     *  返回与此服务提供商相关联的<code> ImageWriter </code>实现的实例。返回的对象最初将处于初始状态,就像它的<code> reset </code>方法被调用。
     * 
     *  <p>默认实现只返回<code> createWriterInstance(null)</code>。
     * 
     * 
     * @return an <code>ImageWriter</code> instance.
     *
     * @exception IOException if an error occurs during loading,
     * or initialization of the writer class, or during instantiation
     * or initialization of the writer object.
     */
    public ImageWriter createWriterInstance() throws IOException {
        return createWriterInstance(null);
    }

    /**
     * Returns an instance of the <code>ImageWriter</code>
     * implementation associated with this service provider.
     * The returned object will initially be in an initial state
     * as if its <code>reset</code> method had been called.
     *
     * <p> An <code>Object</code> may be supplied to the plug-in at
     * construction time.  The nature of the object is entirely
     * plug-in specific.
     *
     * <p> Typically, a plug-in will implement this method using code
     * such as <code>return new MyImageWriter(this)</code>.
     *
     * <p>
     *  返回与此服务提供商相关联的<code> ImageWriter </code>实现的实例。返回的对象最初将处于初始状态,就像它的<code> reset </code>方法被调用。
     * 
     *  <p>在构建时可以向插件提供<code> Object </code>。对象的本质是完全插件特定的。
     * 
     *  <p>通常,插件将使用<code> return new MyImageWriter(this)</code>等代码实现此方法。
     * 
     * 
     * @param extension a plug-in specific extension object, which may
     * be <code>null</code>.
     *
     * @return an <code>ImageWriter</code> instance.
     *
     * @exception IOException if the attempt to instantiate
     * the writer fails.
     * @exception IllegalArgumentException if the
     * <code>ImageWriter</code>'s constructor throws an
     * <code>IllegalArgumentException</code> to indicate that the
     * extension object is unsuitable.
     */
    public abstract ImageWriter createWriterInstance(Object extension)
        throws IOException;

    /**
     * Returns <code>true</code> if the <code>ImageWriter</code> object
     * passed in is an instance of the <code>ImageWriter</code>
     * associated with this service provider.
     *
     * <p>
     * 如果传入的<code> ImageWriter </code>对象是与此服务提供程序关联的<code> ImageWriter </code>的实例,则返回<code> true </code>
     * 
     * 
     * @param writer an <code>ImageWriter</code> instance.
     *
     * @return <code>true</code> if <code>writer</code> is recognized
     *
     * @exception IllegalArgumentException if <code>writer</code> is
     * <code>null</code>.
     */
    public boolean isOwnWriter(ImageWriter writer) {
        if (writer == null) {
            throw new IllegalArgumentException("writer == null!");
        }
        String name = writer.getClass().getName();
        return name.equals(pluginClassName);
    }

    /**
     * Returns an array of <code>String</code>s containing all the
     * fully qualified names of all the <code>ImageReaderSpi</code>
     * classes that can understand the internal metadata
     * representation used by the <code>ImageWriter</code> associated
     * with this service provider, or <code>null</code> if there are
     * no such <code>ImageReaders</code> specified.  If a
     * non-<code>null</code> value is returned, it must have non-zero
     * length.
     *
     * <p> The first item in the array must be the name of the service
     * provider for the "preferred" reader, as it will be used to
     * instantiate the <code>ImageReader</code> returned by
     * <code>ImageIO.getImageReader(ImageWriter)</code>.
     *
     * <p> This mechanism may be used to obtain
     * <code>ImageReaders</code> that will generated non-pixel
     * meta-data (see <code>IIOExtraDataInfo</code>) in a structure
     * understood by an <code>ImageWriter</code>.  By reading the
     * image and obtaining this data from one of the
     * <code>ImageReaders</code> obtained with this method and passing
     * it on to the <code>ImageWriter</code>, a client program can
     * read an image, modify it in some way, and write it back out
     * preserving all meta-data, without having to understand anything
     * about the internal structure of the meta-data, or even about
     * the image format.
     *
     * <p>
     *  返回一个包含所有<code> ImageReaderSpi </code>类的所有完全限定名称的<code> String </code>数组,可以理解<code> ImageWriter </code>
     * 关联与此服务提供程序,或<code> null </code>如果没有指定此类<code> ImageReaders </code>。
     * 如果返回非<code> null </code>值,则它必须具有非零长度。
     * 
     *  <p>数组中的第一个项目必须是"首选"读取器的服务提供程序的名称,因为它将用于实例化由<code> ImageIO.getImageReader(ImageWriter)返回的<code> Image
     * Reader </code> )</code>。
     * 
     * @return an array of <code>String</code>s of length at least 1
     * containing names of <code>ImageReaderSpi</code>s, or
     * <code>null</code>.
     *
     * @see javax.imageio.ImageIO#getImageReader(ImageWriter)
     * @see ImageReaderSpi#getImageWriterSpiNames()
     */
    public String[] getImageReaderSpiNames() {
        return readerSpiNames == null ?
            null : (String[])readerSpiNames.clone();
    }
}
