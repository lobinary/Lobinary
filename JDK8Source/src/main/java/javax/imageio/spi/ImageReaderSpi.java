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

import java.io.IOException;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * The service provider interface (SPI) for <code>ImageReader</code>s.
 * For more information on service provider classes, see the class comment
 * for the <code>IIORegistry</code> class.
 *
 * <p> Each <code>ImageReaderSpi</code> provides several types of information
 * about the <code>ImageReader</code> class with which it is associated.
 *
 * <p> The name of the vendor who defined the SPI class and a
 * brief description of the class are available via the
 * <code>getVendorName</code>, <code>getDescription</code>,
 * and <code>getVersion</code> methods.
 * These methods may be internationalized to provide locale-specific
 * output.  These methods are intended mainly to provide short,
 * human-readable information that might be used to organize a pop-up
 * menu or other list.
 *
 * <p> Lists of format names, file suffixes, and MIME types associated
 * with the service may be obtained by means of the
 * <code>getFormatNames</code>, <code>getFileSuffixes</code>, and
 * <code>getMIMETypes</code> methods.  These methods may be used to
 * identify candidate <code>ImageReader</code>s for decoding a
 * particular file or stream based on manual format selection, file
 * naming, or MIME associations (for example, when accessing a file
 * over HTTP or as an email attachment).
 *
 * <p> A more reliable way to determine which <code>ImageReader</code>s
 * are likely to be able to parse a particular data stream is provided
 * by the <code>canDecodeInput</code> method.  This methods allows the
 * service provider to inspect the actual stream contents.
 *
 * <p> Finally, an instance of the <code>ImageReader</code> class
 * associated with this service provider may be obtained by calling
 * the <code>createReaderInstance</code> method.  Any heavyweight
 * initialization, such as the loading of native libraries or creation
 * of large tables, should be deferred at least until the first
 * invocation of this method.
 *
 * <p>
 *  用于<code> ImageReader </code>的服务提供程序接口(SPI)。有关服务提供程序类的更多信息,请参阅<code> IIORegistry </code>类的类注释。
 * 
 *  <p>每个<code> ImageReaderSpi </code>提供了与它相关的<code> ImageReader </code>类的几种类型的信息。
 * 
 *  <p>通过<code> getVendorName </code>,<code> getDescription </code>和<code> getVersion </code可以获得定义SPI类的供应商的名称和类的简要描述>
 * 方法。
 * 这些方法可以被国际化以提供特定于场所的输出。这些方法主要旨在提供可用于组织弹出菜单或其它列表的简短的,人类可读的信息。
 * 
 *  <p>与服务相关联的格式名称,文件后缀和MIME类型的列表可以通过<code> getFormatNames </code>,<code> getFileSuffixes </code>和<code>
 *  getMIMETypes </代码>方法。
 * 这些方法可以用于基于手动格式选择,文件命名或MIME关联来识别用于解码特定文件或流的候选<code> ImageReader </code>(例如,当通过HTTP访问文件或作为电子邮件附件)。
 * 
 * <p>通过<code> canDecodeInput </code>方法提供更可靠的方法来确定哪些<code> ImageReader </code>可能能够解析特定数据流。
 * 这种方法允许服务提供商检查实际流内容。
 * 
 *  <p>最后,可以通过调用<code> createReaderInstance </code>方法获得与此服务提供程序关联的<code> ImageReader </code>类的实例。
 * 任何重量级初始化,例如加载本机库或创建大表,都应该推迟,直到第一次调用此方法。
 * 
 * 
 * @see IIORegistry
 * @see javax.imageio.ImageReader
 *
 */
public abstract class ImageReaderSpi extends ImageReaderWriterSpi {

    /**
     * A single-element array, initially containing
     * <code>ImageInputStream.class</code>, to be returned from
     * <code>getInputTypes</code>.
     * <p>
     *  一个单元素数组,最初包含<code> ImageInputStream.class </code>,从<code> getInputTypes </code>返回。
     * 
     * 
     * @deprecated Instead of using this field, directly create
     * the equivalent array <code>{ ImageInputStream.class }</code>.
     */
    @Deprecated
    public static final Class[] STANDARD_INPUT_TYPE =
        { ImageInputStream.class };

    /**
     * An array of <code>Class</code> objects to be returned from
     * <code>getInputTypes</code>, initially <code>null</code>.
     * <p>
     *  从<code> getInputTypes </code>返回的<code> Class </code>对象数组,最初为<code> null </code>。
     * 
     */
    protected Class[] inputTypes = null;

    /**
     * An array of strings to be returned from
     * <code>getImageWriterSpiNames</code>, initially
     * <code>null</code>.
     * <p>
     *  要从<code> getImageWriterSpiNames </code>返回的字符串数组,最初为<code> null </code>。
     * 
     */
    protected String[] writerSpiNames = null;

    /**
     * The <code>Class</code> of the reader, initially
     * <code>null</code>.
     * <p>
     *  读者的<code> Class </code>,最初<code> null </code>。
     * 
     */
    private Class readerClass = null;

    /**
     * Constructs a blank <code>ImageReaderSpi</code>.  It is up to
     * the subclass to initialize instance variables and/or override
     * method implementations in order to provide working versions of
     * all methods.
     * <p>
     *  构造一个空白的<code> ImageReaderSpi </code>。它是由子类初始化实例变量和/或覆盖方法实现为了提供所有方法的工作版本。
     * 
     */
    protected ImageReaderSpi() {
    }

    /**
     * Constructs an <code>ImageReaderSpi</code> with a given
     * set of values.
     *
     * <p>
     *  用给定的一组值构造一个<code> ImageReaderSpi </code>。
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
     * the format's MIME types.  If no MIME types are defined,
     * <code>null</code> should be supplied.  An array of length 0
     * will be normalized to <code>null</code>.
     * @param readerClassName the fully-qualified name of the
     * associated <code>ImageReader</code> class, as a
     * non-<code>null</code> <code>String</code>.
     * @param inputTypes a non-<code>null</code> array of
     * <code>Class</code> objects of length at least 1 indicating the
     * legal input types.
     * @param writerSpiNames an array <code>String</code>s naming the
     * classes of all associated <code>ImageWriter</code>s, or
     * <code>null</code>.  An array of length 0 is normalized to
     * <code>null</code>.
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
     * @exception IllegalArgumentException if <code>readerClassName</code>
     * is <code>null</code>.
     * @exception IllegalArgumentException if <code>inputTypes</code>
     * is <code>null</code> or has length 0.
     */
    public ImageReaderSpi(String vendorName,
                          String version,
                          String[] names,
                          String[] suffixes,
                          String[] MIMETypes,
                          String readerClassName,
                          Class[] inputTypes,
                          String[] writerSpiNames,
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
              names, suffixes, MIMETypes, readerClassName,
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

        if (inputTypes == null) {
            throw new IllegalArgumentException
                ("inputTypes == null!");
        }
        if (inputTypes.length == 0) {
            throw new IllegalArgumentException
                ("inputTypes.length == 0!");
        }

        this.inputTypes = (inputTypes == STANDARD_INPUT_TYPE) ?
            new Class<?>[] { ImageInputStream.class } :
            inputTypes.clone();

        // If length == 0, leave it null
        if (writerSpiNames != null && writerSpiNames.length > 0) {
            this.writerSpiNames = (String[])writerSpiNames.clone();
        }
    }

    /**
     * Returns an array of <code>Class</code> objects indicating what
     * types of objects may be used as arguments to the reader's
     * <code>setInput</code> method.
     *
     * <p> For most readers, which only accept input from an
     * <code>ImageInputStream</code>, a single-element array
     * containing <code>ImageInputStream.class</code> should be
     * returned.
     *
     * <p>
     *  返回一个<code> Class </code>对象的数组,指示可以将哪些类型的对象用作读取器的<code> setInput </code>方法的参数。
     * 
     * <p>对于大多数只接受来自<code> ImageInputStream </code>的输入的读者,应该返回包含<code> ImageInputStream.class </code>的单元素数组。
     * 
     * 
     * @return a non-<code>null</code> array of
     * <code>Class</code>objects of length at least 1.
     */
    public Class[] getInputTypes() {
        return (Class[])inputTypes.clone();
    }

    /**
     * Returns <code>true</code> if the supplied source object appears
     * to be of the format supported by this reader.  Returning
     * <code>true</code> from this method does not guarantee that
     * reading will succeed, only that there appears to be a
     * reasonable chance of success based on a brief inspection of the
     * stream contents.  If the source is an
     * <code>ImageInputStream</code>, implementations will commonly
     * check the first several bytes of the stream for a "magic
     * number" associated with the format.  Once actual reading has
     * commenced, the reader may still indicate failure at any time
     * prior to the completion of decoding.
     *
     * <p> It is important that the state of the object not be
     * disturbed in order that other <code>ImageReaderSpi</code>s can
     * properly determine whether they are able to decode the object.
     * In particular, if the source is an
     * <code>ImageInputStream</code>, a
     * <code>mark</code>/<code>reset</code> pair should be used to
     * preserve the stream position.
     *
     * <p> Formats such as "raw," which can potentially attempt
     * to read nearly any stream, should return <code>false</code>
     * in order to avoid being invoked in preference to a closer
     * match.
     *
     * <p> If <code>source</code> is not an instance of one of the
     * classes returned by <code>getInputTypes</code>, the method
     * should simply return <code>false</code>.
     *
     * <p>
     *  如果提供的源对象显示为此读取器支持的格式,则返回<code> true </code>。
     * 从此方法返回<code> true </code>不能保证读取将成功,只是基于对流内容的简短检查似乎有合理的成功机会。
     * 如果源是<code> ImageInputStream </code>,实现将通常检查流的前几个字节与该格式相关联的"幻数"。一旦实际读取已经开始,读取器仍然可以在解码完成之前的任何时间指示失败。
     * 
     *  <p>重要的是,对象的状态不被干扰,以便其他<code> ImageReaderSpi </code>可以正确地确定它们是否能够解码对象。
     * 特别地,如果源是<code> ImageInputStream </code>,则应当使用<code>标记</code> / <code>重置</code>对来保留流位置。
     * 
     *  <p>格式,例如"raw",可能会尝试读取几乎任何流,应返回<code> false </code>,以避免被优先于更接近的匹配调用。
     * 
     *  <p>如果<code> source </code>不是<code> getInputTypes </code>返回的类的实例,则该方法应该返回<code> false </code>。
     * 
     * 
     * @param source the object (typically an
     * <code>ImageInputStream</code>) to be decoded.
     *
     * @return <code>true</code> if it is likely that this stream can
     * be decoded.
     *
     * @exception IllegalArgumentException if <code>source</code> is
     * <code>null</code>.
     * @exception IOException if an I/O error occurs while reading the
     * stream.
     */
    public abstract boolean canDecodeInput(Object source) throws IOException;

    /**
     * Returns an instance of the <code>ImageReader</code>
     * implementation associated with this service provider.
     * The returned object will initially be in an initial state
     * as if its <code>reset</code> method had been called.
     *
     * <p> The default implementation simply returns
     * <code>createReaderInstance(null)</code>.
     *
     * <p>
     * 返回与此服务提供商相关联的<code> ImageReader </code>实现的实例。返回的对象最初将处于初始状态,就像它的<code> reset </code>方法被调用。
     * 
     *  <p>默认实现只返回<code> createReaderInstance(null)</code>。
     * 
     * 
     * @return an <code>ImageReader</code> instance.
     *
     * @exception IOException if an error occurs during loading,
     * or initialization of the reader class, or during instantiation
     * or initialization of the reader object.
     */
    public ImageReader createReaderInstance() throws IOException {
        return createReaderInstance(null);
    }

    /**
     * Returns an instance of the <code>ImageReader</code>
     * implementation associated with this service provider.
     * The returned object will initially be in an initial state
     * as if its <code>reset</code> method had been called.
     *
     * <p> An <code>Object</code> may be supplied to the plug-in at
     * construction time.  The nature of the object is entirely
     * plug-in specific.
     *
     * <p> Typically, a plug-in will implement this method using code
     * such as <code>return new MyImageReader(this)</code>.
     *
     * <p>
     *  返回与此服务提供商相关联的<code> ImageReader </code>实现的实例。返回的对象最初将处于初始状态,就像它的<code> reset </code>方法被调用。
     * 
     *  <p>在构建时可以向插件提供<code> Object </code>。对象的本质是完全插件特定的。
     * 
     *  <p>通常,插件将使用<code> return new MyImageReader(this)</code>等代码来实现此方法。
     * 
     * 
     * @param extension a plug-in specific extension object, which may
     * be <code>null</code>.
     *
     * @return an <code>ImageReader</code> instance.
     *
     * @exception IOException if the attempt to instantiate
     * the reader fails.
     * @exception IllegalArgumentException if the
     * <code>ImageReader</code>'s constructor throws an
     * <code>IllegalArgumentException</code> to indicate that the
     * extension object is unsuitable.
     */
    public abstract ImageReader createReaderInstance(Object extension)
        throws IOException;

    /**
     * Returns <code>true</code> if the <code>ImageReader</code> object
     * passed in is an instance of the <code>ImageReader</code>
     * associated with this service provider.
     *
     * <p> The default implementation compares the fully-qualified
     * class name of the <code>reader</code> argument with the class
     * name passed into the constructor.  This method may be overridden
     * if more sophisticated checking is required.
     *
     * <p>
     *  如果传入的<code> ImageReader </code>对象是与此服务提供程序关联的<code> ImageReader </code>的实例,则返回<code> true </code>
     * 
     *  <p>默认实现将<code> reader </code>参数的完全限定类名与传递到构造函数中的类名相比较。如果需要更复杂的检查,则可以覆盖此方法。
     * 
     * 
     * @param reader an <code>ImageReader</code> instance.
     *
     * @return <code>true</code> if <code>reader</code> is recognized.
     *
     * @exception IllegalArgumentException if <code>reader</code> is
     * <code>null</code>.
     */
    public boolean isOwnReader(ImageReader reader) {
        if (reader == null) {
            throw new IllegalArgumentException("reader == null!");
        }
        String name = reader.getClass().getName();
        return name.equals(pluginClassName);
    }

    /**
     * Returns an array of <code>String</code>s containing the fully
     * qualified names of all the <code>ImageWriterSpi</code> classes
     * that can understand the internal metadata representation used
     * by the <code>ImageReader</code> associated with this service
     * provider, or <code>null</code> if there are no such
     * <code>ImageWriter</code>s specified.  If a
     * non-<code>null</code> value is returned, it must have non-zero
     * length.
     *
     * <p> The first item in the array must be the name of the service
     * provider for the "preferred" writer, as it will be used to
     * instantiate the <code>ImageWriter</code> returned by
     * <code>ImageIO.getImageWriter(ImageReader)</code>.
     *
     * <p> This mechanism may be used to obtain
     * <code>ImageWriters</code> that will understand the internal
     * structure of non-pixel meta-data (see
     * <code>IIOTreeInfo</code>) generated by an
     * <code>ImageReader</code>.  By obtaining this data from the
     * <code>ImageReader</code> and passing it on to one of the
     * <code>ImageWriters</code> obtained with this method, a client
     * program can read an image, modify it in some way, and write it
     * back out while preserving all meta-data, without having to
     * understand anything about the internal structure of the
     * meta-data, or even about the image format.
     *
     * <p>
     * 返回一个<code> String </code>数组,其中包含所有<code> ImageWriterSpi </code>类的完全限定名称,可以理解与<code> ImageReader </code>
     * 如果没有指定此类<code> ImageWriter </code>,则此服务提供程序或<code> null </code>。
     * 如果返回非<code> null </code>值,则它必须具有非零长度。
     * 
     *  <p>数组中的第一个项目必须是"首选"编写器的服务提供程序的名称,因为它将用于实例化由<code> ImageIO.getImageWriter(ImageReader)返回的<code> Image
     * Writer </code> )</code>。
     * 
     * @return an array of <code>String</code>s of length at least 1
     * containing names of <code>ImageWriterSpi</code>, or
     * <code>null</code>.
     *
     * @see javax.imageio.ImageIO#getImageWriter(ImageReader)
     */
    public String[] getImageWriterSpiNames() {
        return writerSpiNames == null ?
            null : (String[])writerSpiNames.clone();
    }
}
