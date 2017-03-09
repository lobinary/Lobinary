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

package javax.imageio;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.image.Raster;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.imageio.event.IIOWriteWarningListener;
import javax.imageio.event.IIOWriteProgressListener;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.spi.ImageWriterSpi;

/**
 * An abstract superclass for encoding and writing images.  This class
 * must be subclassed by classes that write out images in the context
 * of the Java Image I/O framework.
 *
 * <p> <code>ImageWriter</code> objects are normally instantiated by
 * the service provider class for the specific format.  Service
 * provider classes are registered with the <code>IIORegistry</code>,
 * which uses them for format recognition and presentation of
 * available format readers and writers.
 *
 * <p>
 *
 * <p>
 *  用于编码和写入图像的抽象超类。这个类必须由在Java Image I / O框架的上下文中写出图像的类进行子类化。
 * 
 *  <p> <code> ImageWriter </code>对象通常由特定格式的服务提供程序类实例化。
 * 服务提供程序类在<code> IIORegistry </code>中注册,它们用于格式识别和可用格式读取器和写入器的呈现。
 * 
 * <p>
 * 
 * 
 * @see ImageReader
 * @see ImageWriteParam
 * @see javax.imageio.spi.IIORegistry
 * @see javax.imageio.spi.ImageWriterSpi
 *
 */
public abstract class ImageWriter implements ImageTranscoder {

    /**
     * The <code>ImageWriterSpi</code> that instantiated this object,
     * or <code>null</code> if its identity is not known or none
     * exists.  By default it is initialized to <code>null</code>.
     * <p>
     *  实例化此对象的<code> ImageWriterSpi </code>,如果其身份不为已知或不存在,则<code> null </code>。
     * 默认情况下,它被初始化为<code> null </code>。
     * 
     */
    protected ImageWriterSpi originatingProvider = null;

    /**
     * The <code>ImageOutputStream</code> or other <code>Object</code>
     * set by <code>setOutput</code> and retrieved by
     * <code>getOutput</code>.  By default it is initialized to
     * <code>null</code>.
     * <p>
     *  由<code> setOutput </code>设置并由<code> getOutput </code>检索的<code> ImageOutputStream </code>或其他<code> Ob
     * ject </code>。
     * 默认情况下,它被初始化为<code> null </code>。
     * 
     */
    protected Object output = null;

    /**
     * An array of <code>Locale</code>s that may be used to localize
     * warning messages and compression setting values, or
     * <code>null</code> if localization is not supported.  By default
     * it is initialized to <code>null</code>.
     * <p>
     *  可以用于本地化警告消息和压缩设置值的<code> Locale </code>数组,如果不支持本地化,则为<code> null </code>。
     * 默认情况下,它被初始化为<code> null </code>。
     * 
     */
    protected Locale[] availableLocales = null;

    /**
     * The current <code>Locale</code> to be used for localization, or
     * <code>null</code> if none has been set.  By default it is
     * initialized to <code>null</code>.
     * <p>
     *  要用于本地化的当前<code> Locale </code>,如果没有设置,则为<code> null </code>。默认情况下,它被初始化为<code> null </code>。
     * 
     */
    protected Locale locale = null;

    /**
     * A <code>List</code> of currently registered
     * <code>IIOWriteWarningListener</code>s, initialized by default to
     * <code>null</code>, which is synonymous with an empty
     * <code>List</code>.
     * <p>
     * 当前注册的<code> IIOWriteWarningListener </code>的<code> List </code>,默认初始化为<code> null </code>,与空的<code> L
     * ist </code>。
     * 
     */
    protected List<IIOWriteWarningListener> warningListeners = null;

    /**
     * A <code>List</code> of <code>Locale</code>s, one for each
     * element of <code>warningListeners</code>, initialized by default
     * <code>null</code>, which is synonymous with an empty
     * <code>List</code>.
     * <p>
     *  <code> Locale </code>的<code> List </code>,对于<code> warningListeners </code>的每个元素都有一个</code>,默认为<code>
     *  null </code>一个空的<code> List </code>。
     * 
     */
    protected List<Locale> warningLocales = null;

    /**
     * A <code>List</code> of currently registered
     * <code>IIOWriteProgressListener</code>s, initialized by default
     * <code>null</code>, which is synonymous with an empty
     * <code>List</code>.
     * <p>
     *  当前注册的<code> IIOWriteProgressListener </code>的<code> List </code>,由默认<code> null </code>初始化,与空的<code>
     *  List </code>。
     * 
     */
    protected List<IIOWriteProgressListener> progressListeners = null;

    /**
     * If <code>true</code>, the current write operation should be
     * aborted.
     * <p>
     *  如果<code> true </code>,则应该中止当前的写操作。
     * 
     */
    private boolean abortFlag = false;

    /**
     * Constructs an <code>ImageWriter</code> and sets its
     * <code>originatingProvider</code> instance variable to the
     * supplied value.
     *
     * <p> Subclasses that make use of extensions should provide a
     * constructor with signature <code>(ImageWriterSpi,
     * Object)</code> in order to retrieve the extension object.  If
     * the extension object is unsuitable, an
     * <code>IllegalArgumentException</code> should be thrown.
     *
     * <p>
     *  构造一个<code> ImageWriter </code>并将其<code> originatingProvider </code>实例变量设置为提供的值。
     * 
     *  <p>使用扩展的子类应该提供带有签名<code>(ImageWriterSpi,Object)</code>的构造函数,以便检索扩展对象。
     * 如果扩展对象不合适,应该抛出一个<code> IllegalArgumentException </code>。
     * 
     * 
     * @param originatingProvider the <code>ImageWriterSpi</code> that
     * is constructing this object, or <code>null</code>.
     */
    protected ImageWriter(ImageWriterSpi originatingProvider) {
        this.originatingProvider = originatingProvider;
    }

    /**
     * Returns the <code>ImageWriterSpi</code> object that created
     * this <code>ImageWriter</code>, or <code>null</code> if this
     * object was not created through the <code>IIORegistry</code>.
     *
     * <p> The default implementation returns the value of the
     * <code>originatingProvider</code> instance variable.
     *
     * <p>
     *  如果此对象不是通过<code> IIORegistry </code>创建的,则返回创建此<code> ImageWriter </code>或<code> null </code>的<code> I
     * mageWriterSpi </code>对象。
     * 
     *  <p>默认实现返回<code> originatingProvider </code>实例变量的值。
     * 
     * 
     * @return an <code>ImageWriterSpi</code>, or <code>null</code>.
     *
     * @see ImageWriterSpi
     */
    public ImageWriterSpi getOriginatingProvider() {
        return originatingProvider;
    }

    /**
     * Sets the destination to the given
     * <code>ImageOutputStream</code> or other <code>Object</code>.
     * The destination is assumed to be ready to accept data, and will
     * not be closed at the end of each write. This allows distributed
     * imaging applications to transmit a series of images over a
     * single network connection.  If <code>output</code> is
     * <code>null</code>, any currently set output will be removed.
     *
     * <p> If <code>output</code> is an
     * <code>ImageOutputStream</code>, calls to the
     * <code>write</code>, <code>writeToSequence</code>, and
     * <code>prepareWriteEmpty</code>/<code>endWriteEmpty</code>
     * methods will preserve the existing contents of the stream.
     * Other write methods, such as <code>writeInsert</code>,
     * <code>replaceStreamMetadata</code>,
     * <code>replaceImageMetadata</code>, <code>replacePixels</code>,
     * <code>prepareInsertEmpty</code>/<code>endInsertEmpty</code>,
     * and <code>endWriteSequence</code>, require the full contents
     * of the stream to be readable and writable, and may alter any
     * portion of the stream.
     *
     * <p> Use of a general <code>Object</code> other than an
     * <code>ImageOutputStream</code> is intended for writers that
     * interact directly with an output device or imaging protocol.
     * The set of legal classes is advertised by the writer's service
     * provider's <code>getOutputTypes</code> method; most writers
     * will return a single-element array containing only
     * <code>ImageOutputStream.class</code> to indicate that they
     * accept only an <code>ImageOutputStream</code>.
     *
     * <p> The default implementation sets the <code>output</code>
     * instance variable to the value of <code>output</code> after
     * checking <code>output</code> against the set of classes
     * advertised by the originating provider, if there is one.
     *
     * <p>
     * 将目标设置为给定的<code> ImageOutputStream </code>或其他<code> Object </code>。目标被假定为准备好接受数据,并且不会在每次写入结束时关闭。
     * 这允许分布式成像应用通过单个网络连接传输一系列图像。如果<code> output </code>是<code> null </code>,任何当前设置的输出将被删除。
     * 
     *  <p>如果<code> output </code>是<code> ImageOutputStream </code>,则调用<code> write </code>,<code> writeToSe
     * quence </code>和<code> prepareWriteEmpty < / code> / <code> endWriteEmpty </code>方法将保留流的现有内容。
     * 其他写方法,如<code> writeInsert </code>,<code> replaceStreamMetadata </code>,<code> replaceImageMetadata </code>
     * ,<code> replacePixels </code>,<code> prepareInsertEmpty </code> / <code> endInsertEmpty </code>和<code>
     *  endWriteSequence </code>,需要流的全部内容是可读和可写的,并且可以改变流的任何部分。
     * 
     *  <p>除了<code> ImageOutputStream </code>之外的一般<code> Object </code>的使用适用于直接与输出设备或成像协议交互的写者。
     * 这组法律类由作者的服务提供者的<code> getOutputTypes </code>方法发布;大多数写入器将返回一个只包含<code> ImageOutputStream.class </code>
     * 的单元素数组,以表示它们只接受一个<code> ImageOutputStream </code>。
     *  <p>除了<code> ImageOutputStream </code>之外的一般<code> Object </code>的使用适用于直接与输出设备或成像协议交互的写者。
     * 
     * <p>默认实现在检查源代码提供商发布的类集合后检查<code> output </code>后将<code> output </code>实例变量设置为<code> output </code> ,如果
     * 有一个。
     * 
     * 
     * @param output the <code>ImageOutputStream</code> or other
     * <code>Object</code> to use for future writing.
     *
     * @exception IllegalArgumentException if <code>output</code> is
     * not an instance of one of the classes returned by the
     * originating service provider's <code>getOutputTypes</code>
     * method.
     *
     * @see #getOutput
     */
    public void setOutput(Object output) {
        if (output != null) {
            ImageWriterSpi provider = getOriginatingProvider();
            if (provider != null) {
                Class[] classes = provider.getOutputTypes();
                boolean found = false;
                for (int i = 0; i < classes.length; i++) {
                    if (classes[i].isInstance(output)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    throw new IllegalArgumentException("Illegal output type!");
                }
            }
        }

        this.output = output;
    }

    /**
     * Returns the <code>ImageOutputStream</code> or other
     * <code>Object</code> set by the most recent call to the
     * <code>setOutput</code> method.  If no destination has been
     * set, <code>null</code> is returned.
     *
     * <p> The default implementation returns the value of the
     * <code>output</code> instance variable.
     *
     * <p>
     *  返回由最近一次调用<code> setOutput </code>方法设置的<code> ImageOutputStream </code>或其他<code> Object </code>如果没有设置
     * 目的地,则返回<code> null </code>。
     * 
     *  <p>默认实现返回<code> output </code>实例变量的值。
     * 
     * 
     * @return the <code>Object</code> that was specified using
     * <code>setOutput</code>, or <code>null</code>.
     *
     * @see #setOutput
     */
    public Object getOutput() {
        return output;
    }

    // Localization

    /**
     * Returns an array of <code>Locale</code>s that may be used to
     * localize warning listeners and compression settings.  A return
     * value of <code>null</code> indicates that localization is not
     * supported.
     *
     * <p> The default implementation returns a clone of the
     * <code>availableLocales</code> instance variable if it is
     * non-<code>null</code>, or else returns <code>null</code>.
     *
     * <p>
     *  返回可用于本地化警告侦听器和压缩设置的<code> Locale </code>数组。 <code> null </code>的返回值表示不支持本地化。
     * 
     *  <p>如果非<code> null </code>,则返回<code> availableLocales </code>实例变量的克隆,否则返回<code> null </code>。
     * 
     * 
     * @return an array of <code>Locale</code>s that may be used as
     * arguments to <code>setLocale</code>, or <code>null</code>.
     */
    public Locale[] getAvailableLocales() {
        return (availableLocales == null) ?
            null : (Locale[])availableLocales.clone();
    }

    /**
     * Sets the current <code>Locale</code> of this
     * <code>ImageWriter</code> to the given value.  A value of
     * <code>null</code> removes any previous setting, and indicates
     * that the writer should localize as it sees fit.
     *
     * <p> The default implementation checks <code>locale</code>
     * against the values returned by
     * <code>getAvailableLocales</code>, and sets the
     * <code>locale</code> instance variable if it is found.  If
     * <code>locale</code> is <code>null</code>, the instance variable
     * is set to <code>null</code> without any checking.
     *
     * <p>
     *  将此<code> ImageWriter </code>的当前<code> Locale </code>设置为给定值。
     *  <code> null </code>的值会删除任何先前的设置,并指示写入程序应按其认为合适的方式进行本地化。
     * 
     *  <p>默认实现针对<code> getAvailableLocales </code>返回的值检查<code> locale </code>,如果找到,设置<code> locale </code>实
     * 例变量。
     * 如果<code> locale </code>是<code> null </code>,则将实例变量设置为<code> null </code>而不进行任何检查。
     * 
     * 
     * @param locale the desired <code>Locale</code>, or
     * <code>null</code>.
     *
     * @exception IllegalArgumentException if <code>locale</code> is
     * non-<code>null</code> but is not one of the values returned by
     * <code>getAvailableLocales</code>.
     *
     * @see #getLocale
     */
    public void setLocale(Locale locale) {
        if (locale != null) {
            Locale[] locales = getAvailableLocales();
            boolean found = false;
            if (locales != null) {
                for (int i = 0; i < locales.length; i++) {
                    if (locale.equals(locales[i])) {
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                throw new IllegalArgumentException("Invalid locale!");
            }
        }
        this.locale = locale;
    }

    /**
     * Returns the currently set <code>Locale</code>, or
     * <code>null</code> if none has been set.
     *
     * <p> The default implementation returns the value of the
     * <code>locale</code> instance variable.
     *
     * <p>
     * 返回当前设置的<code> Locale </code>或<code> null </code>(如果没有设置)。
     * 
     *  <p>默认实现返回<code> locale </code>实例变量的值。
     * 
     * 
     * @return the current <code>Locale</code>, or <code>null</code>.
     *
     * @see #setLocale
     */
    public Locale getLocale() {
        return locale;
    }

    // Write params

    /**
     * Returns a new <code>ImageWriteParam</code> object of the
     * appropriate type for this file format containing default
     * values, that is, those values that would be used
     * if no <code>ImageWriteParam</code> object were specified.  This
     * is useful as a starting point for tweaking just a few parameters
     * and otherwise leaving the default settings alone.
     *
     * <p> The default implementation constructs and returns a new
     * <code>ImageWriteParam</code> object that does not allow tiling,
     * progressive encoding, or compression, and that will be
     * localized for the current <code>Locale</code> (<i>i.e.</i>,
     * what you would get by calling <code>new
     * ImageWriteParam(getLocale())</code>.
     *
     * <p> Individual plug-ins may return an instance of
     * <code>ImageWriteParam</code> with additional optional features
     * enabled, or they may return an instance of a plug-in specific
     * subclass of <code>ImageWriteParam</code>.
     *
     * <p>
     *  返回包含默认值(即,如果未指定<code> ImageWriteParam </code>对象时将使用的那些值)的此文件格式的适当类型的新<对象> ImageWriteParam </code>对象。
     * 这是有用的作为起点只调整几个参数,否则单独保留默认设置。
     * 
     *  <p>默认实现构造并返回一个新的<code> ImageWriteParam </code>对象,该对象不允许平铺,渐进编码或压缩,并且将针对当前<code> Locale </code> i> ie
     *  </i>,你会通过调用<code> new ImageWriteParam(getLocale())</code>得到。
     * 
     *  <p>各个插件可能会返回一个具有启用了额外可选功能的<code> ImageWriteParam </code>实例,或者它们可能返回<code> ImageWriteParam </code>的插件
     * 特定子类的实例。
     * 
     * 
     * @return a new <code>ImageWriteParam</code> object containing
     * default values.
     */
    public ImageWriteParam getDefaultWriteParam() {
        return new ImageWriteParam(getLocale());
    }

    // Metadata

    /**
     * Returns an <code>IIOMetadata</code> object containing default
     * values for encoding a stream of images.  The contents of the
     * object may be manipulated using either the XML tree structure
     * returned by the <code>IIOMetadata.getAsTree</code> method, an
     * <code>IIOMetadataController</code> object, or via plug-in
     * specific interfaces, and the resulting data supplied to one of
     * the <code>write</code> methods that take a stream metadata
     * parameter.
     *
     * <p> An optional <code>ImageWriteParam</code> may be supplied
     * for cases where it may affect the structure of the stream
     * metadata.
     *
     * <p> If the supplied <code>ImageWriteParam</code> contains
     * optional setting values not supported by this writer (<i>e.g.</i>
     * progressive encoding or any format-specific settings), they
     * will be ignored.
     *
     * <p> Writers that do not make use of stream metadata
     * (<i>e.g.</i>, writers for single-image formats) should return
     * <code>null</code>.
     *
     * <p>
     * 返回包含用于编码图像流的默认值的<code> IIOMetadata </code>对象。
     * 可以使用<code> IIOMetadata.getAsTree </code>方法,<code> IIOMetadataController </code>对象或通过插件特定接口返回的XML树结构来操
     * 作对象的内容,结果数据提供给采用流元数据参数的<code> write </code>方法之一。
     * 返回包含用于编码图像流的默认值的<code> IIOMetadata </code>对象。
     * 
     *  <p>可以为可能影响流元数据结构的情况提供可选的<code> ImageWriteParam </code>。
     * 
     *  <p>如果提供的<code> ImageWriteParam </code>包含此作者不支持的可选设置值(<i>例如</i>渐进式编码或任何特定于格式的设置),则它们将被忽略。
     * 
     *  <p>不使用流元数据(<i>例如</i>,用于单图像格式的作者)的作者应返回<code> null </code>。
     * 
     * 
     * @param param an <code>ImageWriteParam</code> that will be used to
     * encode the image, or <code>null</code>.
     *
     * @return an <code>IIOMetadata</code> object.
     */
    public abstract IIOMetadata
        getDefaultStreamMetadata(ImageWriteParam param);

    /**
     * Returns an <code>IIOMetadata</code> object containing default
     * values for encoding an image of the given type.  The contents
     * of the object may be manipulated using either the XML tree
     * structure returned by the <code>IIOMetadata.getAsTree</code>
     * method, an <code>IIOMetadataController</code> object, or via
     * plug-in specific interfaces, and the resulting data supplied to
     * one of the <code>write</code> methods that take a stream
     * metadata parameter.
     *
     * <p> An optional <code>ImageWriteParam</code> may be supplied
     * for cases where it may affect the structure of the image
     * metadata.
     *
     * <p> If the supplied <code>ImageWriteParam</code> contains
     * optional setting values not supported by this writer (<i>e.g.</i>
     * progressive encoding or any format-specific settings), they
     * will be ignored.
     *
     * <p>
     *  返回包含用于编码给定类型的图像的默认值的<code> IIOMetadata </code>对象。
     * 可以使用<code> IIOMetadata.getAsTree </code>方法,<code> IIOMetadataController </code>对象或通过插件特定接口返回的XML树结构来操
     * 作对象的内容,结果数据提供给采用流元数据参数的<code> write </code>方法之一。
     *  返回包含用于编码给定类型的图像的默认值的<code> IIOMetadata </code>对象。
     * 
     *  <p>可能会提供可能影响图像元数据结构的情况下的<code> ImageWriteParam </code>。
     * 
     * <p>如果提供的<code> ImageWriteParam </code>包含此作者不支持的可选设置值(<i>例如</i>渐进式编码或任何特定于格式的设置),则它们将被忽略。
     * 
     * 
     * @param imageType an <code>ImageTypeSpecifier</code> indicating the
     * format of the image to be written later.
     * @param param an <code>ImageWriteParam</code> that will be used to
     * encode the image, or <code>null</code>.
     *
     * @return an <code>IIOMetadata</code> object.
     */
    public abstract IIOMetadata
        getDefaultImageMetadata(ImageTypeSpecifier imageType,
                                ImageWriteParam param);

    // comment inherited
    public abstract IIOMetadata convertStreamMetadata(IIOMetadata inData,
                                                      ImageWriteParam param);

    // comment inherited
    public abstract IIOMetadata
        convertImageMetadata(IIOMetadata inData,
                             ImageTypeSpecifier imageType,
                             ImageWriteParam param);

    // Thumbnails

    /**
     * Returns the number of thumbnails supported by the format being
     * written, given the image type and any additional write
     * parameters and metadata objects that will be used during
     * encoding.  A return value of <code>-1</code> indicates that
     * insufficient information is available.
     *
     * <p> An <code>ImageWriteParam</code> may optionally be supplied
     * for cases where it may affect thumbnail handling.
     *
     * <p> If the supplied <code>ImageWriteParam</code> contains
     * optional setting values not supported by this writer (<i>e.g.</i>
     * progressive encoding or any format-specific settings), they
     * will be ignored.
     *
     * <p> The default implementation returns 0.
     *
     * <p>
     *  根据将在编码期间使用的图像类型和任何其他写入参数和元数据对象,返回正在写入的格式支持的缩略图数。返回值<code> -1 </code>表示信息不足。
     * 
     *  <p>可以选择为影响缩略图处理的情况提供<code> ImageWriteParam </code>。
     * 
     *  <p>如果提供的<code> ImageWriteParam </code>包含此作者不支持的可选设置值(<i>例如</i>渐进式编码或任何特定于格式的设置),则它们将被忽略。
     * 
     *  <p>默认实现返回0。
     * 
     * 
     * @param imageType an <code>ImageTypeSpecifier</code> indicating
     * the type of image to be written, or <code>null</code>.
     * @param param the <code>ImageWriteParam</code> that will be used for
     * writing, or <code>null</code>.
     * @param streamMetadata an <code>IIOMetadata</code> object that will
     * be used for writing, or <code>null</code>.
     * @param imageMetadata an <code>IIOMetadata</code> object that will
     * be used for writing, or <code>null</code>.
     *
     * @return the number of thumbnails that may be written given the
     * supplied parameters, or <code>-1</code> if insufficient
     * information is available.
     */
    public int getNumThumbnailsSupported(ImageTypeSpecifier imageType,
                                         ImageWriteParam param,
                                         IIOMetadata streamMetadata,
                                         IIOMetadata imageMetadata) {
        return 0;
    }

    /**
     * Returns an array of <code>Dimension</code>s indicating the
     * legal size ranges for thumbnail images as they will be encoded
     * in the output file or stream.  This information is merely
     * advisory; the writer will resize any supplied thumbnails as
     * necessary.
     *
     * <p> The information is returned as a set of pairs; the first
     * element of a pair contains an (inclusive) minimum width and
     * height, and the second element contains an (inclusive) maximum
     * width and height.  Together, each pair defines a valid range of
     * sizes.  To specify a fixed size, the same width and height will
     * appear for both elements.  A return value of <code>null</code>
     * indicates that the size is arbitrary or unknown.
     *
     * <p> An <code>ImageWriteParam</code> may optionally be supplied
     * for cases where it may affect thumbnail handling.
     *
     * <p> If the supplied <code>ImageWriteParam</code> contains
     * optional setting values not supported by this writer (<i>e.g.</i>
     * progressive encoding or any format-specific settings), they
     * will be ignored.
     *
     * <p> The default implementation returns <code>null</code>.
     *
     * <p>
     *  返回一个<code> Dimension </code>数组,指示缩略图的合法大小范围,因为它们将在输出文件或流中编码。这些信息仅仅是咨询;作者将根据需要调整所提供的任何缩略图的大小。
     * 
     * <p>信息作为一组对返回;一对中的第一元素包含(包括)最小宽度和高度,第二元素包含(包括)最大宽度和高度。在一起,每对定义一个有效的尺寸范围。要指定固定大小,两个元素将显示相同的宽度和高度。
     *  <code> null </code>的返回值表示大小是任意的或未知的。
     * 
     *  <p>可以选择为影响缩略图处理的情况提供<code> ImageWriteParam </code>。
     * 
     *  <p>如果提供的<code> ImageWriteParam </code>包含此作者不支持的可选设置值(<i>例如</i>渐进式编码或任何特定于格式的设置),则它们将被忽略。
     * 
     *  <p>默认实现返回<code> null </code>。
     * 
     * 
     * @param imageType an <code>ImageTypeSpecifier</code> indicating the
     * type of image to be written, or <code>null</code>.
     * @param param the <code>ImageWriteParam</code> that will be used for
     * writing, or <code>null</code>.
     * @param streamMetadata an <code>IIOMetadata</code> object that will
     * be used for writing, or <code>null</code>.
     * @param imageMetadata an <code>IIOMetadata</code> object that will
     * be used for writing, or <code>null</code>.
     *
     * @return an array of <code>Dimension</code>s with an even length
     * of at least two, or <code>null</code>.
     */
    public Dimension[] getPreferredThumbnailSizes(ImageTypeSpecifier imageType,
                                                  ImageWriteParam param,
                                                  IIOMetadata streamMetadata,
                                                  IIOMetadata imageMetadata) {
        return null;
    }

    /**
     * Returns <code>true</code> if the methods that take an
     * <code>IIOImage</code> parameter are capable of dealing with a
     * <code>Raster</code> (as opposed to <code>RenderedImage</code>)
     * source image.  If this method returns <code>false</code>, then
     * those methods will throw an
     * <code>UnsupportedOperationException</code> if supplied with an
     * <code>IIOImage</code> containing a <code>Raster</code>.
     *
     * <p> The default implementation returns <code>false</code>.
     *
     * <p>
     *  如果采用<code> IIOImage </code>参数的方法能够处理<code> Raster </code>(而不是<code> RenderedImage </code>),则返回<code>
     *  true </code> )源图像。
     * 如果此方法返回<code> false </code>,那么这些方法将抛出<code> UnsupportedOperationException </code>(如果提供了包含<code> Raste
     * r </code>的<code> IIOImage </code>)。
     * 
     *  <p>默认实现返回<code> false </code>。
     * 
     * 
     * @return <code>true</code> if <code>Raster</code> sources are
     * supported.
     */
    public boolean canWriteRasters() {
        return false;
    }

    /**
     * Appends a complete image stream containing a single image and
     * associated stream and image metadata and thumbnails to the
     * output.  Any necessary header information is included.  If the
     * output is an <code>ImageOutputStream</code>, its existing
     * contents prior to the current seek position are not affected,
     * and need not be readable or writable.
     *
     * <p> The output must have been set beforehand using the
     * <code>setOutput</code> method.
     *
     * <p> Stream metadata may optionally be supplied; if it is
     * <code>null</code>, default stream metadata will be used.
     *
     * <p> If <code>canWriteRasters</code> returns <code>true</code>,
     * the <code>IIOImage</code> may contain a <code>Raster</code>
     * source.  Otherwise, it must contain a
     * <code>RenderedImage</code> source.
     *
     * <p> The supplied thumbnails will be resized if needed, and any
     * thumbnails in excess of the supported number will be ignored.
     * If the format requires additional thumbnails that are not
     * provided, the writer should generate them internally.
     *
     * <p>  An <code>ImageWriteParam</code> may
     * optionally be supplied to control the writing process.  If
     * <code>param</code> is <code>null</code>, a default write param
     * will be used.
     *
     * <p> If the supplied <code>ImageWriteParam</code> contains
     * optional setting values not supported by this writer (<i>e.g.</i>
     * progressive encoding or any format-specific settings), they
     * will be ignored.
     *
     * <p>
     * 将包含单个图像和关联的流以及图像元数据和缩略图的完整图像流附加到输出。包括任何必要的头信息。
     * 如果输出是<code> ImageOutputStream </code>,则其在当前搜索位置之前的现有内容不受影响,并且不需要是可读或可写的。
     * 
     *  <p>输出必须使用<code> setOutput </code>方法预先设置。
     * 
     *  <p>可以可选地提供流元数据;如果它是<code> null </code>,则将使用默认流元数据。
     * 
     *  <p>如果<code> canWriteRasters </code>返回<code> true </code>,则<code> IIOImage </code>可能包含<code> Raster </code>
     * 源。
     * 否则,它必须包含一个<code> RenderedImage </code>源。
     * 
     *  <p>如果需要,系统会调整提供的缩略图,并且超过支持的缩略图的任何缩略图将被忽略。如果格式需要未提供的其他缩略图,则编写器应在内部生成它们。
     * 
     *  <p>可以可选地提供<code> ImageWriteParam </code>以控制写入过程。
     * 如果<code> param </code>是<code> null </code>,将使用默认的写参数。
     * 
     *  <p>如果提供的<code> ImageWriteParam </code>包含此作者不支持的可选设置值(<i>例如</i>渐进式编码或任何特定于格式的设置),则它们将被忽略。
     * 
     * 
     * @param streamMetadata an <code>IIOMetadata</code> object representing
     * stream metadata, or <code>null</code> to use default values.
     * @param image an <code>IIOImage</code> object containing an
     * image, thumbnails, and metadata to be written.
     * @param param an <code>ImageWriteParam</code>, or
     * <code>null</code> to use a default
     * <code>ImageWriteParam</code>.
     *
     * @exception IllegalStateException if the output has not
     * been set.
     * @exception UnsupportedOperationException if <code>image</code>
     * contains a <code>Raster</code> and <code>canWriteRasters</code>
     * returns <code>false</code>.
     * @exception IllegalArgumentException if <code>image</code> is
     * <code>null</code>.
     * @exception IOException if an error occurs during writing.
     */
    public abstract void write(IIOMetadata streamMetadata,
                               IIOImage image,
                               ImageWriteParam param) throws IOException;

    /**
     * Appends a complete image stream containing a single image with
     * default metadata and thumbnails to the output.  This method is
     * a shorthand for <code>write(null, image, null)</code>.
     *
     * <p>
     * 将包含具有默认元数据和缩略图的单个图像的完整图像流附加到输出。此方法是<code> write(null,image,null)</code>的缩写。
     * 
     * 
     * @param image an <code>IIOImage</code> object containing an
     * image, thumbnails, and metadata to be written.
     *
     * @exception IllegalStateException if the output has not
     * been set.
     * @exception IllegalArgumentException if <code>image</code> is
     * <code>null</code>.
     * @exception UnsupportedOperationException if <code>image</code>
     * contains a <code>Raster</code> and <code>canWriteRasters</code>
     * returns <code>false</code>.
     * @exception IOException if an error occurs during writing.
     */
    public void write(IIOImage image) throws IOException {
        write(null, image, null);
    }

    /**
     * Appends a complete image stream consisting of a single image
     * with default metadata and thumbnails to the output.  This
     * method is a shorthand for <code>write(null, new IIOImage(image,
     * null, null), null)</code>.
     *
     * <p>
     *  将包含具有默认元数据和缩略图的单个图像的完整图像流附加到输出。
     * 这个方法是<code> write(null,new IIOImage(image,null,null),null)</code>的简写。
     * 
     * 
     * @param image a <code>RenderedImage</code> to be written.
     *
     * @exception IllegalStateException if the output has not
     * been set.
     * @exception IllegalArgumentException if <code>image</code> is
     * <code>null</code>.
     * @exception IOException if an error occurs during writing.
     */
    public void write(RenderedImage image) throws IOException {
        write(null, new IIOImage(image, null, null), null);
    }

    // Check that the output has been set, then throw an
    // UnsupportedOperationException.
    private void unsupported() {
        if (getOutput() == null) {
            throw new IllegalStateException("getOutput() == null!");
        }
        throw new UnsupportedOperationException("Unsupported write variant!");
    }

    // Sequence writes

    /**
     * Returns <code>true</code> if the writer is able to append an
     * image to an image stream that already contains header
     * information and possibly prior images.
     *
     * <p> If <code>canWriteSequence</code> returns <code>false</code>,
     * <code>writeToSequence</code> and <code>endWriteSequence</code>
     * will throw an <code>UnsupportedOperationException</code>.
     *
     * <p> The default implementation returns <code>false</code>.
     *
     * <p>
     *  如果写入程序能够将图像附加到已包含标题信息和可能的之前图像的图像流,则返回<code> true </code>。
     * 
     *  <p>如果<code> canWriteSequence </code>返回<code> false </code>,则<code> writeToSequence </code>和<code> en
     * dWriteSequence </code>会抛出<code> UnsupportedOperationException </code> 。
     * 
     *  <p>默认实现返回<code> false </code>。
     * 
     * 
     * @return <code>true</code> if images may be appended sequentially.
     */
    public boolean canWriteSequence() {
        return false;
    }

    /**
     * Prepares a stream to accept a series of subsequent
     * <code>writeToSequence</code> calls, using the provided stream
     * metadata object.  The metadata will be written to the stream if
     * it should precede the image data.  If the argument is <code>null</code>,
     * default stream metadata is used.
     *
     * <p> If the output is an <code>ImageOutputStream</code>, the existing
     * contents of the output prior to the current seek position are
     * flushed, and need not be readable or writable.  If the format
     * requires that <code>endWriteSequence</code> be able to rewind to
     * patch up the header information, such as for a sequence of images
     * in a single TIFF file, then the metadata written by this method
     * must remain in a writable portion of the stream.  Other formats
     * may flush the stream after this method and after each image.
     *
     * <p> If <code>canWriteSequence</code> returns <code>false</code>,
     * this method will throw an
     * <code>UnsupportedOperationException</code>.
     *
     * <p> The output must have been set beforehand using either
     * the <code>setOutput</code> method.
     *
     * <p> The default implementation throws an
     * <code>IllegalStateException</code> if the output is
     * <code>null</code>, and otherwise throws an
     * <code>UnsupportedOperationException</code>.
     *
     * <p>
     *  使用提供的流元数据对象准备流接受一系列后续的<code> writeToSequence </code>调用。如果元数据应在图像数据之前,则将元数据写入流。
     * 如果参数是<code> null </code>,则使用默认流元数据。
     * 
     * <p>如果输出是<code> ImageOutputStream </code>,则刷新当前搜索位置之前的输出的现有内容,并且不需要可读或可写。
     * 如果格式要求<code> endWriteSequence </code>能够倒回以修补标题信息,例如对于单个TIFF文件中的图像序列,则由该方法写入的元数据必须保持在可写部分的流。
     * 其他格式可以在该方法之后和每个图像之后刷新流。
     * 
     *  <p>如果<code> canWriteSequence </code>返回<code> false </code>,此方法将抛出<code> UnsupportedOperationExceptio
     * n </code>。
     * 
     *  <p>输出必须使用<code> setOutput </code>方法预先设置。
     * 
     *  <p>如果输出为<code> null </code>,则默认实现会抛出<code> IllegalStateException </code>,否则会抛出一个<code> UnsupportedOp
     * erationException </code>。
     * 
     * 
     * @param streamMetadata A stream metadata object, or <code>null</code>.
     *
     * @exception IllegalStateException if the output has not
     * been set.
     * @exception UnsupportedOperationException if
     * <code>canWriteSequence</code> returns <code>false</code>.
     * @exception IOException if an error occurs writing the stream
     * metadata.
     */
    public void prepareWriteSequence(IIOMetadata streamMetadata)
        throws IOException {
        unsupported();
    }

    /**
     * Appends a single image and possibly associated metadata and
     * thumbnails, to the output.  If the output is an
     * <code>ImageOutputStream</code>, the existing contents of the
     * output prior to the current seek position may be flushed, and
     * need not be readable or writable, unless the plug-in needs to
     * be able to patch up the header information when
     * <code>endWriteSequence</code> is called (<i>e.g.</i> TIFF).
     *
     * <p> If <code>canWriteSequence</code> returns <code>false</code>,
     * this method will throw an
     * <code>UnsupportedOperationException</code>.
     *
     * <p> The output must have been set beforehand using
     * the <code>setOutput</code> method.
     *
     * <p> <code>prepareWriteSequence</code> must have been called
     * beforehand, or an <code>IllegalStateException</code> is thrown.
     *
     * <p> If <code>canWriteRasters</code> returns <code>true</code>,
     * the <code>IIOImage</code> may contain a <code>Raster</code>
     * source.  Otherwise, it must contain a
     * <code>RenderedImage</code> source.
     *
     * <p> The supplied thumbnails will be resized if needed, and any
     * thumbnails in excess of the supported number will be ignored.
     * If the format requires additional thumbnails that are not
     * provided, the writer will generate them internally.
     *
     * <p> An <code>ImageWriteParam</code> may optionally be supplied
     * to control the writing process.  If <code>param</code> is
     * <code>null</code>, a default write param will be used.
     *
     * <p> If the supplied <code>ImageWriteParam</code> contains
     * optional setting values not supported by this writer (<i>e.g.</i>
     * progressive encoding or any format-specific settings), they
     * will be ignored.
     *
     * <p> The default implementation throws an
     * <code>IllegalStateException</code> if the output is
     * <code>null</code>, and otherwise throws an
     * <code>UnsupportedOperationException</code>.
     *
     * <p>
     *  将单个图像以及可能关联的元数据和缩略图附加到输出。
     * 如果输出是<code> ImageOutputStream </code>,则当前搜索位置之前的输出的现有内容可以被刷新,并且不需要是可读或可写的,除非插件需要能够补丁调用<code> endWrite
     * Sequence </code>(<i> TIFF)时的头信息。
     *  将单个图像以及可能关联的元数据和缩略图附加到输出。
     * 
     *  <p>如果<code> canWriteSequence </code>返回<code> false </code>,此方法将抛出<code> UnsupportedOperationExceptio
     * n </code>。
     * 
     * <p>输出必须使用<code> setOutput </code>方法预先设置。
     * 
     *  必须事先调用<p> <code> prepareWriteSequence </code>,或者抛出<code> IllegalStateException </code>。
     * 
     *  <p>如果<code> canWriteRasters </code>返回<code> true </code>,则<code> IIOImage </code>可能包含<code> Raster </code>
     * 源。
     * 否则,它必须包含一个<code> RenderedImage </code>源。
     * 
     *  <p>如果需要,系统会调整提供的缩略图,并且超过支持的缩略图的任何缩略图将被忽略。如果格式需要未提供的其他缩略图,则编写器将在内部生成它们。
     * 
     *  <p>可以可选地提供<code> ImageWriteParam </code>以控制写入过程。
     * 如果<code> param </code>是<code> null </code>,将使用默认的写参数。
     * 
     *  <p>如果提供的<code> ImageWriteParam </code>包含此作者不支持的可选设置值(<i>例如</i>渐进式编码或任何特定于格式的设置),则它们将被忽略。
     * 
     *  <p>如果输出为<code> null </code>,则默认实现会抛出<code> IllegalStateException </code>,否则会抛出一个<code> UnsupportedOp
     * erationException </code>。
     * 
     * 
     * @param image an <code>IIOImage</code> object containing an
     * image, thumbnails, and metadata to be written.
     * @param param an <code>ImageWriteParam</code>, or
     * <code>null</code> to use a default
     * <code>ImageWriteParam</code>.
     *
     * @exception IllegalStateException if the output has not
     * been set, or <code>prepareWriteSequence</code> has not been called.
     * @exception UnsupportedOperationException if
     * <code>canWriteSequence</code> returns <code>false</code>.
     * @exception IllegalArgumentException if <code>image</code> is
     * <code>null</code>.
     * @exception UnsupportedOperationException if <code>image</code>
     * contains a <code>Raster</code> and <code>canWriteRasters</code>
     * returns <code>false</code>.
     * @exception IOException if an error occurs during writing.
     */
    public void writeToSequence(IIOImage image, ImageWriteParam param)
        throws IOException {
        unsupported();
    }

    /**
     * Completes the writing of a sequence of images begun with
     * <code>prepareWriteSequence</code>.  Any stream metadata that
     * should come at the end of the sequence of images is written out,
     * and any header information at the beginning of the sequence is
     * patched up if necessary.  If the output is an
     * <code>ImageOutputStream</code>, data through the stream metadata
     * at the end of the sequence are flushed and need not be readable
     * or writable.
     *
     * <p> If <code>canWriteSequence</code> returns <code>false</code>,
     * this method will throw an
     * <code>UnsupportedOperationException</code>.
     *
     * <p> The default implementation throws an
     * <code>IllegalStateException</code> if the output is
     * <code>null</code>, and otherwise throws an
     * <code>UnsupportedOperationException</code>.
     *
     * <p>
     * 完成以<code> prepareWriteSequence </code>开头的一系列图像的写入。任何应该在图像序列的末尾出现的流元数据被写出,并且如果必要的话,在序列的开始处的任何头信息被修补。
     * 如果输出是<code> ImageOutputStream </code>,则通过流元数据在序列末尾的数据被刷新,并且不需要可读或可写。
     * 
     *  <p>如果<code> canWriteSequence </code>返回<code> false </code>,此方法将抛出<code> UnsupportedOperationExceptio
     * n </code>。
     * 
     *  <p>如果输出为<code> null </code>,则默认实现会抛出<code> IllegalStateException </code>,否则会抛出一个<code> UnsupportedOp
     * erationException </code>。
     * 
     * 
     * @exception IllegalStateException if the output has not
     * been set, or <code>prepareWriteSequence</code> has not been called.
     * @exception UnsupportedOperationException if
     * <code>canWriteSequence</code> returns <code>false</code>.
     * @exception IOException if an error occurs during writing.
     */
    public void endWriteSequence() throws IOException {
        unsupported();
    }

    // Metadata replacement

    /**
     * Returns <code>true</code> if it is possible to replace the
     * stream metadata already present in the output.
     *
     * <p> The default implementation throws an
     * <code>IllegalStateException</code> if the output is
     * <code>null</code>, and otherwise returns <code>false</code>.
     *
     * <p>
     *  如果可以替换输出中已存在的流元数据,则返回<code> true </code>。
     * 
     *  <p>如果输出为<code> null </code>,则默认实现会抛出<code> IllegalStateException </code>,否则返回<code> false </code>。
     * 
     * 
     * @return <code>true</code> if replacement of stream metadata is
     * allowed.
     *
     * @exception IllegalStateException if the output has not
     * been set.
     * @exception IOException if an I/O error occurs during the query.
     */
    public boolean canReplaceStreamMetadata() throws IOException {
        if (getOutput() == null) {
            throw new IllegalStateException("getOutput() == null!");
        }
        return false;
    }

    /**
     * Replaces the stream metadata in the output with new
     * information.  If the output is an
     * <code>ImageOutputStream</code>, the prior contents of the
     * stream are examined and possibly edited to make room for the
     * new data.  All of the prior contents of the output must be
     * available for reading and writing.
     *
     * <p> If <code>canReplaceStreamMetadata</code> returns
     * <code>false</code>, an
     * <code>UnsupportedOperationException</code> will be thrown.
     *
     * <p> The default implementation throws an
     * <code>IllegalStateException</code> if the output is
     * <code>null</code>, and otherwise throws an
     * <code>UnsupportedOperationException</code>.
     *
     * <p>
     *  用新信息替换输出中的流元数据。如果输出是<code> ImageOutputStream </code>,则会检查流的先前内容,并可能进行编辑,为新数据腾出空间。
     * 输出的所有先前内容必须可用于读取和写入。
     * 
     *  <p>如果<code> canReplaceStreamMetadata </code>返回<code> false </code>,则会抛出<code> UnsupportedOperationEx
     * ception </code>。
     * 
     * <p>如果输出为<code> null </code>,则默认实现会抛出<code> IllegalStateException </code>,否则会抛出一个<code> UnsupportedOpe
     * rationException </code>。
     * 
     * 
     * @param streamMetadata an <code>IIOMetadata</code> object representing
     * stream metadata, or <code>null</code> to use default values.
     *
     * @exception IllegalStateException if the output has not
     * been set.
     * @exception UnsupportedOperationException if the
     * <code>canReplaceStreamMetadata</code> returns
     * <code>false</code>.  modes do not include
     * @exception IOException if an error occurs during writing.
     */
    public void replaceStreamMetadata(IIOMetadata streamMetadata)
        throws IOException {
        unsupported();
    }

    /**
     * Returns <code>true</code> if it is possible to replace the
     * image metadata associated with an existing image with index
     * <code>imageIndex</code>.  If this method returns
     * <code>false</code>, a call to
     * <code>replaceImageMetadata(imageIndex)</code> will throw an
     * <code>UnsupportedOperationException</code>.
     *
     * <p> A writer that does not support any image metadata
     * replacement may return <code>false</code> without performing
     * bounds checking on the index.
     *
     * <p> The default implementation throws an
     * <code>IllegalStateException</code> if the output is
     * <code>null</code>, and otherwise returns <code>false</code>
     * without checking the value of <code>imageIndex</code>.
     *
     * <p>
     *  如果可以使用索引<code> imageIndex </code>替换与现有图像相关联的图像元数据,则返回<code> true </code>。
     * 如果此方法返回<code> false </code>,则调用<code> replaceImageMetadata(imageIndex)</code>会抛出<code> UnsupportedOpe
     * rationException </code>。
     *  如果可以使用索引<code> imageIndex </code>替换与现有图像相关联的图像元数据,则返回<code> true </code>。
     * 
     *  <p>不支持任何图像元数据替换的写入程序可能会返回<code> false </code>,而不对索引执行边界检查。
     * 
     *  <p>如果输出为<code> null </code>,则默认实现会抛出<code> IllegalStateException </code>,否则返回<code> false </code>,而不
     * 检查<code> imageIndex < / code>。
     * 
     * 
     * @param imageIndex the index of the image whose metadata is to
     * be replaced.
     *
     * @return <code>true</code> if the image metadata of the given
     * image can be replaced.
     *
     * @exception IllegalStateException if the output has not
     * been set.
     * @exception IndexOutOfBoundsException if the writer supports
     * image metadata replacement in general, but
     * <code>imageIndex</code> is less than 0 or greater than the
     * largest available index.
     * @exception IOException if an I/O error occurs during the query.
     */
    public boolean canReplaceImageMetadata(int imageIndex)
        throws IOException {
        if (getOutput() == null) {
            throw new IllegalStateException("getOutput() == null!");
        }
        return false;
    }

    /**
     * Replaces the image metadata associated with an existing image.
     *
     * <p> If <code>canReplaceImageMetadata(imageIndex)</code> returns
     * <code>false</code>, an
     * <code>UnsupportedOperationException</code> will be thrown.
     *
     * <p> The default implementation throws an
     * <code>IllegalStateException</code> if the output is
     * <code>null</code>, and otherwise throws an
     * <code>UnsupportedOperationException</code>.
     *
     * <p>
     *  替换与现有图像相关联的图像元数据。
     * 
     *  <p>如果<code> canReplaceImageMetadata(imageIndex)</code>返回<code> false </code>,将抛出<code> UnsupportedOp
     * erationException </code>。
     * 
     *  <p>如果输出为<code> null </code>,则默认实现会抛出<code> IllegalStateException </code>,否则会抛出一个<code> UnsupportedOp
     * erationException </code>。
     * 
     * 
     * @param imageIndex the index of the image whose metadata is to
     * be replaced.
     * @param imageMetadata an <code>IIOMetadata</code> object
     * representing image metadata, or <code>null</code>.
     *
     * @exception IllegalStateException if the output has not been
     * set.
     * @exception UnsupportedOperationException if
     * <code>canReplaceImageMetadata</code> returns
     * <code>false</code>.
     * @exception IndexOutOfBoundsException if <code>imageIndex</code>
     * is less than 0 or greater than the largest available index.
     * @exception IOException if an error occurs during writing.
     */
    public void replaceImageMetadata(int imageIndex,
                                     IIOMetadata imageMetadata)
        throws IOException {
        unsupported();
    }

    // Image insertion

    /**
     * Returns <code>true</code> if the writer supports the insertion
     * of a new image at the given index.  Existing images with
     * indices greater than or equal to the insertion index will have
     * their indices increased by 1.  A value for
     * <code>imageIndex</code> of <code>-1</code> may be used to
     * signify an index one larger than the current largest index.
     *
     * <p> A writer that does not support any image insertion may
     * return <code>false</code> without performing bounds checking on
     * the index.
     *
     * <p> The default implementation throws an
     * <code>IllegalStateException</code> if the output is
     * <code>null</code>, and otherwise returns <code>false</code>
     * without checking the value of <code>imageIndex</code>.
     *
     * <p>
     * 如果写入程序支持在给定索引处插入新图像,则返回<code> true </code>。
     * 具有大于或等于插入索引的索引的现有图像将使其索引增加1. <code> -1 </code>的<code> imageIndex </code>的值可以用于表示较大的索引比当前最大指数。
     * 
     *  <p>不支持任何图像插入的写入程序可能会返回<code> false </code>,而不对索引执行边界检查。
     * 
     *  <p>如果输出为<code> null </code>,则默认实现会抛出<code> IllegalStateException </code>,否则返回<code> false </code>,而不
     * 检查<code> imageIndex < / code>。
     * 
     * 
     * @param imageIndex the index at which the image is to be
     * inserted.
     *
     * @return <code>true</code> if an image may be inserted at the
     * given index.
     *
     * @exception IllegalStateException if the output has not
     * been set.
     * @exception IndexOutOfBoundsException if the writer supports
     * image insertion in general, but <code>imageIndex</code> is less
     * than -1 or greater than the largest available index.
     * @exception IOException if an I/O error occurs during the query.
     */
    public boolean canInsertImage(int imageIndex) throws IOException {
        if (getOutput() == null) {
            throw new IllegalStateException("getOutput() == null!");
        }
        return false;
    }

    /**
     * Inserts a new image into an existing image stream.  Existing
     * images with an index greater than <code>imageIndex</code> are
     * preserved, and their indices are each increased by 1.  A value
     * for <code>imageIndex</code> of -1 may be used to signify an
     * index one larger than the previous largest index; that is, it
     * will cause the image to be logically appended to the end of the
     * sequence.  If the output is an <code>ImageOutputStream</code>,
     * the entirety of the stream must be both readable and writeable.
     *
     * <p> If <code>canInsertImage(imageIndex)</code> returns
     * <code>false</code>, an
     * <code>UnsupportedOperationException</code> will be thrown.
     *
     * <p> An <code>ImageWriteParam</code> may optionally be supplied
     * to control the writing process.  If <code>param</code> is
     * <code>null</code>, a default write param will be used.
     *
     * <p> If the supplied <code>ImageWriteParam</code> contains
     * optional setting values not supported by this writer (<i>e.g.</i>
     * progressive encoding or any format-specific settings), they
     * will be ignored.
     *
     * <p> The default implementation throws an
     * <code>IllegalStateException</code> if the output is
     * <code>null</code>, and otherwise throws an
     * <code>UnsupportedOperationException</code>.
     *
     * <p>
     *  在现有图像流中插入新图像。
     * 具有大于<code> imageIndex </code>的索引的现有图像被保留,并且它们的索引各自增加1.用于<code> imageIndex </code>为-1的值可以用于表示更大的索引比以前的
     * 最大指数;也就是说,它将导致图像被逻辑地附加到序列的末尾。
     *  在现有图像流中插入新图像。如果输出是<code> ImageOutputStream </code>,则整个流必须是可读和可写的。
     * 
     *  <p>如果<code> canInsertImage(imageIndex)</code>返回<code> false </code>,将抛出<code> UnsupportedOperationEx
     * ception </code>。
     * 
     * <p>可以可选地提供<code> ImageWriteParam </code>以控制写入过程。
     * 如果<code> param </code>是<code> null </code>,将使用默认的写参数。
     * 
     *  <p>如果提供的<code> ImageWriteParam </code>包含此作者不支持的可选设置值(<i>例如</i>渐进式编码或任何特定于格式的设置),则它们将被忽略。
     * 
     *  <p>如果输出为<code> null </code>,则默认实现会抛出<code> IllegalStateException </code>,否则会抛出一个<code> UnsupportedOp
     * erationException </code>。
     * 
     * 
     * @param imageIndex the index at which to write the image.
     * @param image an <code>IIOImage</code> object containing an
     * image, thumbnails, and metadata to be written.
     * @param param an <code>ImageWriteParam</code>, or
     * <code>null</code> to use a default
     * <code>ImageWriteParam</code>.
     *
     * @exception IllegalStateException if the output has not
     * been set.
     * @exception UnsupportedOperationException if
     * <code>canInsertImage(imageIndex)</code> returns <code>false</code>.
     * @exception IllegalArgumentException if <code>image</code> is
     * <code>null</code>.
     * @exception IndexOutOfBoundsException if <code>imageIndex</code>
     * is less than -1 or greater than the largest available index.
     * @exception UnsupportedOperationException if <code>image</code>
     * contains a <code>Raster</code> and <code>canWriteRasters</code>
     * returns <code>false</code>.
     * @exception IOException if an error occurs during writing.
     */
    public void writeInsert(int imageIndex,
                            IIOImage image,
                            ImageWriteParam param) throws IOException {
        unsupported();
    }

    // Image removal

    /**
     * Returns <code>true</code> if the writer supports the removal
     * of an existing image at the given index.  Existing images with
     * indices greater than the insertion index will have
     * their indices decreased by 1.
     *
     * <p> A writer that does not support any image removal may
     * return <code>false</code> without performing bounds checking on
     * the index.
     *
     * <p> The default implementation throws an
     * <code>IllegalStateException</code> if the output is
     * <code>null</code>, and otherwise returns <code>false</code>
     * without checking the value of <code>imageIndex</code>.
     *
     * <p>
     *  如果写入程序支持在给定索引处删除现有图像,则返回<code> true </code>。具有大于插入索引的索引的现有图像将使其索引减小1。
     * 
     *  <p>不支持任何图片删除的写入器可能会返回<code> false </code>,而不对索引执行边界检查。
     * 
     *  <p>如果输出为<code> null </code>,则默认实现会抛出<code> IllegalStateException </code>,否则返回<code> false </code>,而不
     * 检查<code> imageIndex < / code>。
     * 
     * 
     * @param imageIndex the index of the image to be removed.
     *
     * @return <code>true</code> if it is possible to remove the given
     * image.
     *
     * @exception IllegalStateException if the output has not
     * been set.
     * @exception IndexOutOfBoundsException if the writer supports
     * image removal in general, but <code>imageIndex</code> is less
     * than 0 or greater than the largest available index.
     * @exception IOException if an I/O error occurs during the
     * query.
     */
    public boolean canRemoveImage(int imageIndex) throws IOException {
        if (getOutput() == null) {
            throw new IllegalStateException("getOutput() == null!");
        }
        return false;
    }

    /**
     * Removes an image from the stream.
     *
     * <p> If <code>canRemoveImage(imageIndex)</code> returns false,
     * an <code>UnsupportedOperationException</code>will be thrown.
     *
     * <p> The removal may or may not cause a reduction in the actual
     * file size.
     *
     * <p> The default implementation throws an
     * <code>IllegalStateException</code> if the output is
     * <code>null</code>, and otherwise throws an
     * <code>UnsupportedOperationException</code>.
     *
     * <p>
     *  从流中删除图片。
     * 
     *  <p>如果<code> canRemoveImage(imageIndex)</code>返回false,则会抛出<code> UnsupportedOperationException </code>
     * 。
     * 
     *  <p>删除可能会或可能不会导致实际文件大小减少。
     * 
     * <p>如果输出为<code> null </code>,则默认实现会抛出<code> IllegalStateException </code>,否则会抛出一个<code> UnsupportedOpe
     * rationException </code>。
     * 
     * 
     * @param imageIndex the index of the image to be removed.
     *
     * @exception IllegalStateException if the output has not
     * been set.
     * @exception UnsupportedOperationException if
     * <code>canRemoveImage(imageIndex)</code> returns <code>false</code>.
     * @exception IndexOutOfBoundsException if <code>imageIndex</code>
     * is less than 0 or greater than the largest available index.
     * @exception IOException if an I/O error occurs during the
     * removal.
     */
    public void removeImage(int imageIndex) throws IOException {
        unsupported();
    }

    // Empty images

    /**
     * Returns <code>true</code> if the writer supports the writing of
     * a complete image stream consisting of a single image with
     * undefined pixel values and associated metadata and thumbnails
     * to the output.  The pixel values may be defined by future
     * calls to the <code>replacePixels</code> methods.  If the output
     * is an <code>ImageOutputStream</code>, its existing contents
     * prior to the current seek position are not affected, and need
     * not be readable or writable.
     *
     * <p> The default implementation throws an
     * <code>IllegalStateException</code> if the output is
     * <code>null</code>, and otherwise returns <code>false</code>.
     *
     * <p>
     *  如果写入程序支持将包含未定义像素值的单个图像以及关联的元数据和缩略图的完整图像流写入输出,则返回<code> true </code>。
     * 像素值可以通过对<code> replacePixels </code>方法的未来调用来定义。
     * 如果输出是<code> ImageOutputStream </code>,则其在当前搜索位置之前的现有内容不受影响,并且不需要是可读或可写的。
     * 
     *  <p>如果输出为<code> null </code>,则默认实现会抛出<code> IllegalStateException </code>,否则返回<code> false </code>。
     * 
     * 
     * @return <code>true</code> if the writing of complete image
     * stream with contents to be defined later is supported.
     *
     * @exception IllegalStateException if the output has not been
     * set.
     * @exception IOException if an I/O error occurs during the
     * query.
     */
    public boolean canWriteEmpty() throws IOException {
        if (getOutput() == null) {
            throw new IllegalStateException("getOutput() == null!");
        }
        return false;
    }

    /**
     * Begins the writing of a complete image stream, consisting of a
     * single image with undefined pixel values and associated
     * metadata and thumbnails, to the output.  The pixel values will
     * be defined by future calls to the <code>replacePixels</code>
     * methods.  If the output is an <code>ImageOutputStream</code>,
     * its existing contents prior to the current seek position are
     * not affected, and need not be readable or writable.
     *
     * <p> The writing is not complete until a call to
     * <code>endWriteEmpty</code> occurs.  Calls to
     * <code>prepareReplacePixels</code>, <code>replacePixels</code>,
     * and <code>endReplacePixels</code> may occur between calls to
     * <code>prepareWriteEmpty</code> and <code>endWriteEmpty</code>.
     * However, calls to <code>prepareWriteEmpty</code> cannot be
     * nested, and calls to <code>prepareWriteEmpty</code> and
     * <code>prepareInsertEmpty</code> may not be interspersed.
     *
     * <p> If <code>canWriteEmpty</code> returns <code>false</code>,
     * an <code>UnsupportedOperationException</code> will be thrown.
     *
     * <p> An <code>ImageWriteParam</code> may optionally be supplied
     * to control the writing process.  If <code>param</code> is
     * <code>null</code>, a default write param will be used.
     *
     * <p> If the supplied <code>ImageWriteParam</code> contains
     * optional setting values not supported by this writer (<i>e.g.</i>
     * progressive encoding or any format-specific settings), they
     * will be ignored.
     *
     * <p> The default implementation throws an
     * <code>IllegalStateException</code> if the output is
     * <code>null</code>, and otherwise throws an
     * <code>UnsupportedOperationException</code>.
     *
     * <p>
     *  开始将完整的图像流写入输出,该完整的图像流包括具有未定义的像素值的单个图像以及相关联的元数据和缩略图。像素值将通过对<code> replacePixels </code>方法的未来调用来定义。
     * 如果输出是<code> ImageOutputStream </code>,则其在当前搜索位置之前的现有内容不受影响,并且不需要是可读或可写的。
     * 
     * <p>在调用<code> endWriteEmpty </code>之前,写入操作尚未完成。
     * 对<code> prepareWriteEmpty </code>和<code> endWriteEmpty </code的调用之间可能会调用<code> prepareReplacePixels </code>
     * ,<code> replacePixels </code>和<code> endReplacePixels </code> >。
     * <p>在调用<code> endWriteEmpty </code>之前,写入操作尚未完成。
     * 但是,对<code> prepareWriteEmpty </code>的调用不能嵌套,并且调用<code> prepareWriteEmpty </code>和<code> prepareInsert
     * Empty </code>可能不会分散。
     * <p>在调用<code> endWriteEmpty </code>之前,写入操作尚未完成。
     * 
     *  <p>如果<code> canWriteEmpty </code>返回<code> false </code>,则会抛出<code> UnsupportedOperationException </code>
     * 。
     * 
     *  <p>可以可选地提供<code> ImageWriteParam </code>以控制写入过程。
     * 如果<code> param </code>是<code> null </code>,将使用默认的写参数。
     * 
     *  <p>如果提供的<code> ImageWriteParam </code>包含此作者不支持的可选设置值(<i>例如</i>渐进式编码或任何特定于格式的设置),则它们将被忽略。
     * 
     *  <p>如果输出为<code> null </code>,则默认实现会抛出<code> IllegalStateException </code>,否则会抛出一个<code> UnsupportedOp
     * erationException </code>。
     * 
     * 
     * @param streamMetadata an <code>IIOMetadata</code> object representing
     * stream metadata, or <code>null</code> to use default values.
     * @param imageType an <code>ImageTypeSpecifier</code> describing
     * the layout of the image.
     * @param width the width of the image.
     * @param height the height of the image.
     * @param imageMetadata an <code>IIOMetadata</code> object
     * representing image metadata, or <code>null</code>.
     * @param thumbnails a <code>List</code> of
     * <code>BufferedImage</code> thumbnails for this image, or
     * <code>null</code>.
     * @param param an <code>ImageWriteParam</code>, or
     * <code>null</code> to use a default
     * <code>ImageWriteParam</code>.
     *
     * @exception IllegalStateException if the output has not
     * been set.
     * @exception UnsupportedOperationException if
     * <code>canWriteEmpty</code> returns <code>false</code>.
     * @exception IllegalStateException if a previous call to
     * <code>prepareWriteEmpty</code> has been made without a
     * corresponding call to <code>endWriteEmpty</code>.
     * @exception IllegalStateException if a previous call to
     * <code>prepareInsertEmpty</code> has been made without a
     * corresponding call to <code>endInsertEmpty</code>.
     * @exception IllegalArgumentException if <code>imageType</code>
     * is <code>null</code> or <code>thumbnails</code> contains
     * <code>null</code> references or objects other than
     * <code>BufferedImage</code>s.
     * @exception IllegalArgumentException if width or height are less
     * than 1.
     * @exception IOException if an I/O error occurs during writing.
     */
    public void prepareWriteEmpty(IIOMetadata streamMetadata,
                                  ImageTypeSpecifier imageType,
                                  int width, int height,
                                  IIOMetadata imageMetadata,
                                  List<? extends BufferedImage> thumbnails,
                                  ImageWriteParam param) throws IOException {
        unsupported();
    }

    /**
     * Completes the writing of a new image that was begun with a
     * prior call to <code>prepareWriteEmpty</code>.
     *
     * <p> If <code>canWriteEmpty()</code> returns <code>false</code>,
     * an <code>UnsupportedOperationException</code> will be thrown.
     *
     * <p> The default implementation throws an
     * <code>IllegalStateException</code> if the output is
     * <code>null</code>, and otherwise throws an
     * <code>UnsupportedOperationException</code>.
     *
     * <p>
     *  完成对先前调用<code> prepareWriteEmpty </code>开始的新映像的写入。
     * 
     *  <p>如果<code> canWriteEmpty()</code>返回<code> false </code>,则会抛出<code> UnsupportedOperationException </code>
     * 。
     * 
     * <p>如果输出为<code> null </code>,则默认实现会抛出<code> IllegalStateException </code>,否则会抛出一个<code> UnsupportedOpe
     * rationException </code>。
     * 
     * 
     * @exception IllegalStateException if the output has not
     * been set.
     * @exception UnsupportedOperationException if
     * <code>canWriteEmpty(imageIndex)</code> returns
     * <code>false</code>.
     * @exception IllegalStateException if a previous call to
     * <code>prepareWriteEmpty</code> without a corresponding call to
     * <code>endWriteEmpty</code> has not been made.
     * @exception IllegalStateException if a previous call to
     * <code>prepareInsertEmpty</code> without a corresponding call to
     * <code>endInsertEmpty</code> has been made.
     * @exception IllegalStateException if a call to
     * <code>prepareReiplacePixels</code> has been made without a
     * matching call to <code>endReplacePixels</code>.
     * @exception IOException if an I/O error occurs during writing.
     */
    public void endWriteEmpty() throws IOException {
        if (getOutput() == null) {
            throw new IllegalStateException("getOutput() == null!");
        }
        throw new IllegalStateException("No call to prepareWriteEmpty!");
    }

    /**
     * Returns <code>true</code> if the writer supports the insertion
     * of a new, empty image at the given index.  The pixel values of
     * the image are undefined, and may be specified in pieces using
     * the <code>replacePixels</code> methods.  Existing images with
     * indices greater than or equal to the insertion index will have
     * their indices increased by 1.  A value for
     * <code>imageIndex</code> of <code>-1</code> may be used to
     * signify an index one larger than the current largest index.
     *
     * <p> A writer that does not support insertion of empty images
     * may return <code>false</code> without performing bounds
     * checking on the index.
     *
     * <p> The default implementation throws an
     * <code>IllegalStateException</code> if the output is
     * <code>null</code>, and otherwise returns <code>false</code>
     * without checking the value of <code>imageIndex</code>.
     *
     * <p>
     *  如果写入程序支持在给定索引处插入新的空图像,则返回<code> true </code>。图像的像素值未定义,并且可以使用<code> replacePixels </code>方法指定为零件。
     * 具有大于或等于插入索引的索引的现有图像将使其索引增加1. <code> -1 </code>的<code> imageIndex </code>的值可以用于表示较大的索引比当前最大指数。
     * 
     *  <p>不支持插入空图像的写入程序可能会在不对索引执行边界检查的情况下返回<code> false </code>。
     * 
     *  <p>如果输出为<code> null </code>,则默认实现会抛出<code> IllegalStateException </code>,否则返回<code> false </code>,而不
     * 检查<code> imageIndex < / code>。
     * 
     * 
     * @param imageIndex the index at which the image is to be
     * inserted.
     *
     * @return <code>true</code> if an empty image may be inserted at
     * the given index.
     *
     * @exception IllegalStateException if the output has not been
     * set.
     * @exception IndexOutOfBoundsException if the writer supports
     * empty image insertion in general, but <code>imageIndex</code>
     * is less than -1 or greater than the largest available index.
     * @exception IOException if an I/O error occurs during the
     * query.
     */
    public boolean canInsertEmpty(int imageIndex) throws IOException {
        if (getOutput() == null) {
            throw new IllegalStateException("getOutput() == null!");
        }
        return false;
    }

    /**
     * Begins the insertion of a new image with undefined pixel values
     * into an existing image stream.  Existing images with an index
     * greater than <code>imageIndex</code> are preserved, and their
     * indices are each increased by 1.  A value for
     * <code>imageIndex</code> of -1 may be used to signify an index
     * one larger than the previous largest index; that is, it will
     * cause the image to be logically appended to the end of the
     * sequence.  If the output is an <code>ImageOutputStream</code>,
     * the entirety of the stream must be both readable and writeable.
     *
     * <p> The image contents may be
     * supplied later using the <code>replacePixels</code> method.
     * The insertion is not complete until a call to
     * <code>endInsertEmpty</code> occurs.  Calls to
     * <code>prepareReplacePixels</code>, <code>replacePixels</code>,
     * and <code>endReplacePixels</code> may occur between calls to
     * <code>prepareInsertEmpty</code> and
     * <code>endInsertEmpty</code>.  However, calls to
     * <code>prepareInsertEmpty</code> cannot be nested, and calls to
     * <code>prepareWriteEmpty</code> and
     * <code>prepareInsertEmpty</code> may not be interspersed.
     *
     * <p> If <code>canInsertEmpty(imageIndex)</code> returns
     * <code>false</code>, an
     * <code>UnsupportedOperationException</code> will be thrown.
     *
     * <p> An <code>ImageWriteParam</code> may optionally be supplied
     * to control the writing process.  If <code>param</code> is
     * <code>null</code>, a default write param will be used.
     *
     * <p> If the supplied <code>ImageWriteParam</code> contains
     * optional setting values not supported by this writer (<i>e.g.</i>
     * progressive encoding or any format-specific settings), they
     * will be ignored.
     *
     * <p> The default implementation throws an
     * <code>IllegalStateException</code> if the output is
     * <code>null</code>, and otherwise throws an
     * <code>UnsupportedOperationException</code>.
     *
     * <p>
     * 开始将具有未定义像素值的新图像插入到现有图像流中。
     * 具有大于<code> imageIndex </code>的索引的现有图像被保留,并且它们的索引各自增加1.用于<code> imageIndex </code>为-1的值可以用于表示更大的索引比以前的
     * 最大指数;也就是说,它将导致图像被逻辑地附加到序列的末尾。
     * 开始将具有未定义像素值的新图像插入到现有图像流中。如果输出是<code> ImageOutputStream </code>,则整个流必须是可读和可写的。
     * 
     *  <p>稍后可以使用<code> replacePixels </code>方法提供图像内容。在发生对<code> endInsertEmpty </code>的调用之前,插入是不完整的。
     * 对<code> prepareInsertEmpty </code>和<code> endInsertEmpty </code的调用之间可能会调用<code> prepareReplacePixels 
     * </code>,<code> replacePixels </code>和<code> endReplacePixels </code> >。
     *  <p>稍后可以使用<code> replacePixels </code>方法提供图像内容。在发生对<code> endInsertEmpty </code>的调用之前,插入是不完整的。
     * 然而,对<code> prepareInsertEmpty </code>的调用不能嵌套,并且对<code> prepareWriteEmpty </code>和<code> prepareInsert
     * Empty </code>的调用可能不是分散的。
     *  <p>稍后可以使用<code> replacePixels </code>方法提供图像内容。在发生对<code> endInsertEmpty </code>的调用之前,插入是不完整的。
     * 
     *  <p>如果<code> canInsertEmpty(imageIndex)</code>返回<code> false </code>,则会抛出<code> UnsupportedOperationE
     * xception </code>。
     * 
     *  <p>可以可选地提供<code> ImageWriteParam </code>以控制写入过程。
     * 如果<code> param </code>是<code> null </code>,将使用默认的写参数。
     * 
     * <p>如果提供的<code> ImageWriteParam </code>包含此作者不支持的可选设置值(<i>例如</i>渐进式编码或任何特定于格式的设置),则它们将被忽略。
     * 
     *  <p>如果输出为<code> null </code>,则默认实现会抛出<code> IllegalStateException </code>,否则会抛出一个<code> UnsupportedOp
     * erationException </code>。
     * 
     * 
     * @param imageIndex the index at which to write the image.
     * @param imageType an <code>ImageTypeSpecifier</code> describing
     * the layout of the image.
     * @param width the width of the image.
     * @param height the height of the image.
     * @param imageMetadata an <code>IIOMetadata</code> object
     * representing image metadata, or <code>null</code>.
     * @param thumbnails a <code>List</code> of
     * <code>BufferedImage</code> thumbnails for this image, or
     * <code>null</code>.
     * @param param an <code>ImageWriteParam</code>, or
     * <code>null</code> to use a default
     * <code>ImageWriteParam</code>.
     *
     * @exception IllegalStateException if the output has not
     * been set.
     * @exception UnsupportedOperationException if
     * <code>canInsertEmpty(imageIndex)</code> returns
     * <code>false</code>.
     * @exception IndexOutOfBoundsException if <code>imageIndex</code>
     * is less than -1 or greater than the largest available index.
     * @exception IllegalStateException if a previous call to
     * <code>prepareInsertEmpty</code> has been made without a
     * corresponding call to <code>endInsertEmpty</code>.
     * @exception IllegalStateException if a previous call to
     * <code>prepareWriteEmpty</code> has been made without a
     * corresponding call to <code>endWriteEmpty</code>.
     * @exception IllegalArgumentException if <code>imageType</code>
     * is <code>null</code> or <code>thumbnails</code> contains
     * <code>null</code> references or objects other than
     * <code>BufferedImage</code>s.
     * @exception IllegalArgumentException if width or height are less
     * than 1.
     * @exception IOException if an I/O error occurs during writing.
     */
    public void prepareInsertEmpty(int imageIndex,
                                   ImageTypeSpecifier imageType,
                                   int width, int height,
                                   IIOMetadata imageMetadata,
                                   List<? extends BufferedImage> thumbnails,
                                   ImageWriteParam param) throws IOException {
        unsupported();
    }

    /**
     * Completes the insertion of a new image that was begun with a
     * prior call to <code>prepareInsertEmpty</code>.
     *
     * <p> The default implementation throws an
     * <code>IllegalStateException</code> if the output is
     * <code>null</code>, and otherwise throws an
     * <code>UnsupportedOperationException</code>.
     *
     * <p>
     *  完成插入一个新图像,该图像是先前调用<code> prepareInsertEmpty </code>开始的。
     * 
     *  <p>如果输出为<code> null </code>,则默认实现会抛出<code> IllegalStateException </code>,否则会抛出一个<code> UnsupportedOp
     * erationException </code>。
     * 
     * 
     * @exception IllegalStateException if the output has not
     * been set.
     * @exception UnsupportedOperationException if
     * <code>canInsertEmpty(imageIndex)</code> returns
     * <code>false</code>.
     * @exception IllegalStateException if a previous call to
     * <code>prepareInsertEmpty</code> without a corresponding call to
     * <code>endInsertEmpty</code> has not been made.
     * @exception IllegalStateException if a previous call to
     * <code>prepareWriteEmpty</code> without a corresponding call to
     * <code>endWriteEmpty</code> has been made.
     * @exception IllegalStateException if a call to
     * <code>prepareReplacePixels</code> has been made without a
     * matching call to <code>endReplacePixels</code>.
     * @exception IOException if an I/O error occurs during writing.
     */
    public void endInsertEmpty() throws IOException {
        unsupported();
    }

    // Pixel replacement

    /**
     * Returns <code>true</code> if the writer allows pixels of the
     * given image to be replaced using the <code>replacePixels</code>
     * methods.
     *
     * <p> A writer that does not support any pixel replacement may
     * return <code>false</code> without performing bounds checking on
     * the index.
     *
     * <p> The default implementation throws an
     * <code>IllegalStateException</code> if the output is
     * <code>null</code>, and otherwise returns <code>false</code>
     * without checking the value of <code>imageIndex</code>.
     *
     * <p>
     *  如果写入者允许使用<code> replacePixels </code>方法替换给定图像的像素,则返回<code> true </code>。
     * 
     *  <p>不支持任何像素替换的写入程序可能会返回<code> false </code>,而不对索引执行边界检查。
     * 
     *  <p>如果输出为<code> null </code>,则默认实现会抛出<code> IllegalStateException </code>,否则返回<code> false </code>,而不
     * 检查<code> imageIndex < / code>。
     * 
     * 
     * @param imageIndex the index of the image whose pixels are to be
     * replaced.
     *
     * @return <code>true</code> if the pixels of the given
     * image can be replaced.
     *
     * @exception IllegalStateException if the output has not been
     * set.
     * @exception IndexOutOfBoundsException if the writer supports
     * pixel replacement in general, but <code>imageIndex</code> is
     * less than 0 or greater than the largest available index.
     * @exception IOException if an I/O error occurs during the query.
     */
    public boolean canReplacePixels(int imageIndex) throws IOException {
        if (getOutput() == null) {
            throw new IllegalStateException("getOutput() == null!");
        }
        return false;
    }

    /**
     * Prepares the writer to handle a series of calls to the
     * <code>replacePixels</code> methods.  The affected pixel area
     * will be clipped against the supplied
     *
     * <p> If <code>canReplacePixels</code> returns
     * <code>false</code>, and
     * <code>UnsupportedOperationException</code> will be thrown.
     *
     * <p> The default implementation throws an
     * <code>IllegalStateException</code> if the output is
     * <code>null</code>, and otherwise throws an
     * <code>UnsupportedOperationException</code>.
     *
     * <p>
     *  准备写入程序处理对<code> replacePixels </code>方法的一系列调用。受影响的像素区域将根据提供的内容进行裁剪
     * 
     *  <p>如果<code> canReplacePixels </code>返回<code> false </code>,并且将抛出<code> UnsupportedOperationException
     *  </code>。
     * 
     * <p>如果输出为<code> null </code>,则默认实现会抛出<code> IllegalStateException </code>,否则会抛出一个<code> UnsupportedOpe
     * rationException </code>。
     * 
     * 
     * @param imageIndex the index of the image whose pixels are to be
     * replaced.
     * @param region a <code>Rectangle</code> that will be used to clip
     * future pixel regions.
     *
     * @exception IllegalStateException if the output has not
     * been set.
     * @exception UnsupportedOperationException if
     * <code>canReplacePixels(imageIndex)</code> returns
     * <code>false</code>.
     * @exception IndexOutOfBoundsException if <code>imageIndex</code>
     * is less than 0 or greater than the largest available index.
     * @exception IllegalStateException if there is a previous call to
     * <code>prepareReplacePixels</code> without a matching call to
     * <code>endReplacePixels</code> (<i>i.e.</i>, nesting is not
     * allowed).
     * @exception IllegalArgumentException if <code>region</code> is
     * <code>null</code> or has a width or height less than 1.
     * @exception IOException if an I/O error occurs during the
     * preparation.
     */
    public void prepareReplacePixels(int imageIndex,
                                     Rectangle region)  throws IOException {
        unsupported();
    }

    /**
     * Replaces a portion of an image already present in the output
     * with a portion of the given image.  The image data must match,
     * or be convertible to, the image layout of the existing image.
     *
     * <p> The destination region is specified in the
     * <code>param</code> argument, and will be clipped to the image
     * boundaries and the region supplied to
     * <code>prepareReplacePixels</code>.  At least one pixel of the
     * source must not be clipped, or an exception is thrown.
     *
     * <p> An <code>ImageWriteParam</code> may optionally be supplied
     * to control the writing process.  If <code>param</code> is
     * <code>null</code>, a default write param will be used.
     *
     * <p> If the supplied <code>ImageWriteParam</code> contains
     * optional setting values not supported by this writer (<i>e.g.</i>
     * progressive encoding or any format-specific settings), they
     * will be ignored.
     *
     * <p> This method may only be called after a call to
     * <code>prepareReplacePixels</code>, or else an
     * <code>IllegalStateException</code> will be thrown.
     *
     * <p> The default implementation throws an
     * <code>IllegalStateException</code> if the output is
     * <code>null</code>, and otherwise throws an
     * <code>UnsupportedOperationException</code>.
     *
     * <p>
     *  将已经存在于输出中的图像的一部分替换为给定图像的一部分。图像数据必须匹配或可转换为现有图像的图像布局。
     * 
     *  <p>目标区域在<code> param </code>参数中指定,并且将被剪切到图像边界和提供给<code> prepareReplacePixels </code>的区域。
     * 至少不能剪裁源的一个像素,否则抛出异常。
     * 
     *  <p>可以可选地提供<code> ImageWriteParam </code>以控制写入过程。
     * 如果<code> param </code>是<code> null </code>,将使用默认的写参数。
     * 
     *  <p>如果提供的<code> ImageWriteParam </code>包含此作者不支持的可选设置值(<i>例如</i>渐进式编码或任何特定于格式的设置),则它们将被忽略。
     * 
     *  <p>此方法只能在调用<code> prepareReplacePixels </code>后调用,否则将抛出<code> IllegalStateException </code>。
     * 
     *  <p>如果输出为<code> null </code>,则默认实现会抛出<code> IllegalStateException </code>,否则会抛出一个<code> UnsupportedOp
     * erationException </code>。
     * 
     * 
     * @param image a <code>RenderedImage</code> containing source
     * pixels.
     * @param param an <code>ImageWriteParam</code>, or
     * <code>null</code> to use a default
     * <code>ImageWriteParam</code>.
     *
     * @exception IllegalStateException if the output has not
     * been set.
     * @exception UnsupportedOperationException if
     * <code>canReplacePixels(imageIndex)</code> returns
     * <code>false</code>.
     * @exception IllegalStateException if there is no previous call to
     * <code>prepareReplacePixels</code> without a matching call to
     * <code>endReplacePixels</code>.
     * @exception IllegalArgumentException if any of the following are true:
     * <ul>
     * <li> <code>image</code> is <code>null</code>.
     * <li> <code>param</code> is <code>null</code>.
     * <li> the intersected region does not contain at least one pixel.
     * <li> the layout of <code>image</code> does not match, or this
     * writer cannot convert it to, the existing image layout.
     * </ul>
     * @exception IOException if an I/O error occurs during writing.
     */
    public void replacePixels(RenderedImage image, ImageWriteParam param)
        throws IOException {
        unsupported();
    }

    /**
     * Replaces a portion of an image already present in the output
     * with a portion of the given <code>Raster</code>.  The image
     * data must match, or be convertible to, the image layout of the
     * existing image.
     *
     * <p> An <code>ImageWriteParam</code> may optionally be supplied
     * to control the writing process.  If <code>param</code> is
     * <code>null</code>, a default write param will be used.
     *
     * <p> The destination region is specified in the
     * <code>param</code> argument, and will be clipped to the image
     * boundaries and the region supplied to
     * <code>prepareReplacePixels</code>.  At least one pixel of the
     * source must not be clipped, or an exception is thrown.
     *
     * <p> If the supplied <code>ImageWriteParam</code> contains
     * optional setting values not supported by this writer (<i>e.g.</i>
     * progressive encoding or any format-specific settings), they
     * will be ignored.
     *
     * <p> This method may only be called after a call to
     * <code>prepareReplacePixels</code>, or else an
     * <code>IllegalStateException</code> will be thrown.
     *
     * <p> The default implementation throws an
     * <code>IllegalStateException</code> if the output is
     * <code>null</code>, and otherwise throws an
     * <code>UnsupportedOperationException</code>.
     *
     * <p>
     * 将已经存在于输出中的图像的一部分替换为给定的<code> Raster </code>的一部分。图像数据必须匹配或可转换为现有图像的图像布局。
     * 
     *  <p>可以可选地提供<code> ImageWriteParam </code>以控制写入过程。
     * 如果<code> param </code>是<code> null </code>,将使用默认的写参数。
     * 
     *  <p>目标区域在<code> param </code>参数中指定,并且将被剪切到图像边界和提供给<code> prepareReplacePixels </code>的区域。
     * 至少不能剪裁源的一个像素,否则抛出异常。
     * 
     *  <p>如果提供的<code> ImageWriteParam </code>包含此作者不支持的可选设置值(<i>例如</i>渐进式编码或任何特定于格式的设置),则它们将被忽略。
     * 
     *  <p>此方法只能在调用<code> prepareReplacePixels </code>后调用,否则将抛出<code> IllegalStateException </code>。
     * 
     *  <p>如果输出为<code> null </code>,则默认实现会抛出<code> IllegalStateException </code>,否则会抛出一个<code> UnsupportedOp
     * erationException </code>。
     * 
     * 
     * @param raster a <code>Raster</code> containing source
     * pixels.
     * @param param an <code>ImageWriteParam</code>, or
     * <code>null</code> to use a default
     * <code>ImageWriteParam</code>.
     *
     * @exception IllegalStateException if the output has not
     * been set.
     * @exception UnsupportedOperationException if
     * <code>canReplacePixels(imageIndex)</code> returns
     * <code>false</code>.
     * @exception IllegalStateException if there is no previous call to
     * <code>prepareReplacePixels</code> without a matching call to
     * <code>endReplacePixels</code>.
     * @exception UnsupportedOperationException if
     * <code>canWriteRasters</code> returns <code>false</code>.
     * @exception IllegalArgumentException if any of the following are true:
     * <ul>
     * <li> <code>raster</code> is <code>null</code>.
     * <li> <code>param</code> is <code>null</code>.
     * <li> the intersected region does not contain at least one pixel.
     * <li> the layout of <code>raster</code> does not match, or this
     * writer cannot convert it to, the existing image layout.
     * </ul>
     * @exception IOException if an I/O error occurs during writing.
     */
    public void replacePixels(Raster raster, ImageWriteParam param)
        throws IOException {
        unsupported();
    }

    /**
     * Terminates a sequence of calls to <code>replacePixels</code>.
     *
     * <p> If <code>canReplacePixels</code> returns
     * <code>false</code>, and
     * <code>UnsupportedOperationException</code> will be thrown.
     *
     * <p> The default implementation throws an
     * <code>IllegalStateException</code> if the output is
     * <code>null</code>, and otherwise throws an
     * <code>UnsupportedOperationException</code>.
     *
     * <p>
     *  终止对<code> replacePixels </code>的调用序列。
     * 
     *  <p>如果<code> canReplacePixels </code>返回<code> false </code>,并且将抛出<code> UnsupportedOperationException
     *  </code>。
     * 
     * <p>如果输出为<code> null </code>,则默认实现会抛出<code> IllegalStateException </code>,否则会抛出一个<code> UnsupportedOpe
     * rationException </code>。
     * 
     * 
     * @exception IllegalStateException if the output has not
     * been set.
     * @exception UnsupportedOperationException if
     * <code>canReplacePixels(imageIndex)</code> returns
     * <code>false</code>.
     * @exception IllegalStateException if there is no previous call
     * to <code>prepareReplacePixels</code> without a matching call to
     * <code>endReplacePixels</code>.
     * @exception IOException if an I/O error occurs during writing.
     */
    public void endReplacePixels() throws IOException {
        unsupported();
    }

    // Abort

    /**
     * Requests that any current write operation be aborted.  The
     * contents of the output following the abort will be undefined.
     *
     * <p> Writers should call <code>clearAbortRequest</code> at the
     * beginning of each write operation, and poll the value of
     * <code>abortRequested</code> regularly during the write.
     * <p>
     *  请求中止当前的任何写操作。中止后的输出内容将是未定义的。
     * 
     *  <p>写入者应在每次写入操作开始时调用<code> clearAbortRequest </code>,并在写入期间定期轮询<code> abortRequested </code>的值。
     * 
     */
    public synchronized void abort() {
        this.abortFlag = true;
    }

    /**
     * Returns <code>true</code> if a request to abort the current
     * write operation has been made since the writer was instantiated or
     * <code>clearAbortRequest</code> was called.
     *
     * <p>
     *  如果自从写入程序被实例化或调用<code> clearAbortRequest </code>后,已中止当前写入操作的请求,则返回<code> true </code>。
     * 
     * 
     * @return <code>true</code> if the current write operation should
     * be aborted.
     *
     * @see #abort
     * @see #clearAbortRequest
     */
    protected synchronized boolean abortRequested() {
        return this.abortFlag;
    }

    /**
     * Clears any previous abort request.  After this method has been
     * called, <code>abortRequested</code> will return
     * <code>false</code>.
     *
     * <p>
     *  清除任何先前中止请求。调用此方法后,<code> abortRequested </code>将返回<code> false </code>。
     * 
     * 
     * @see #abort
     * @see #abortRequested
     */
    protected synchronized void clearAbortRequest() {
        this.abortFlag = false;
    }

    // Listeners

    /**
     * Adds an <code>IIOWriteWarningListener</code> to the list of
     * registered warning listeners.  If <code>listener</code> is
     * <code>null</code>, no exception will be thrown and no action
     * will be taken.  Messages sent to the given listener will be
     * localized, if possible, to match the current
     * <code>Locale</code>.  If no <code>Locale</code> has been set,
     * warning messages may be localized as the writer sees fit.
     *
     * <p>
     *  向注册的警告侦听器列表中添加<code> IIOWriteWarningListener </code>。
     * 如果<code> listener </code>是<code> null </code>,则不会抛出任何异常,并且不会执行任何操作。
     * 如果可能,发送到给定侦听器的邮件将被本地化,以匹配当前的<code> Locale </code>。如果未设置<code> Locale </code>,警告消息可能会被编辑器认为合适。
     * 
     * 
     * @param listener an <code>IIOWriteWarningListener</code> to be
     * registered.
     *
     * @see #removeIIOWriteWarningListener
     */
    public void addIIOWriteWarningListener(IIOWriteWarningListener listener) {
        if (listener == null) {
            return;
        }
        warningListeners = ImageReader.addToList(warningListeners, listener);
        warningLocales = ImageReader.addToList(warningLocales, getLocale());
    }

    /**
     * Removes an <code>IIOWriteWarningListener</code> from the list
     * of registered warning listeners.  If the listener was not
     * previously registered, or if <code>listener</code> is
     * <code>null</code>, no exception will be thrown and no action
     * will be taken.
     *
     * <p>
     *  从注册的警告侦听器列表中删除<code> IIOWriteWarningListener </code>。
     * 如果侦听器以前没有注册,或者<code> listener </code>是<code> null </code>,则不会抛出任何异常,不会执行任何操作。
     * 
     * 
     * @param listener an <code>IIOWriteWarningListener</code> to be
     * deregistered.
     *
     * @see #addIIOWriteWarningListener
     */
    public
        void removeIIOWriteWarningListener(IIOWriteWarningListener listener) {
        if (listener == null || warningListeners == null) {
            return;
        }
        int index = warningListeners.indexOf(listener);
        if (index != -1) {
            warningListeners.remove(index);
            warningLocales.remove(index);
            if (warningListeners.size() == 0) {
                warningListeners = null;
                warningLocales = null;
            }
        }
    }

    /**
     * Removes all currently registered
     * <code>IIOWriteWarningListener</code> objects.
     *
     * <p> The default implementation sets the
     * <code>warningListeners</code> and <code>warningLocales</code>
     * instance variables to <code>null</code>.
     * <p>
     * 删除所有当前注册的<code> IIOWriteWarningListener </code>对象。
     * 
     *  <p>默认实现将<code> warningListeners </code>和<code> warningLocales </code>实例变量设置为<code> null </code>。
     * 
     */
    public void removeAllIIOWriteWarningListeners() {
        this.warningListeners = null;
        this.warningLocales = null;
    }

    /**
     * Adds an <code>IIOWriteProgressListener</code> to the list of
     * registered progress listeners.  If <code>listener</code> is
     * <code>null</code>, no exception will be thrown and no action
     * will be taken.
     *
     * <p>
     *  向注册进度侦听器列表中添加<code> IIOWriteProgressListener </code>。
     * 如果<code> listener </code>是<code> null </code>,则不会抛出任何异常,并且不会执行任何操作。
     * 
     * 
     * @param listener an <code>IIOWriteProgressListener</code> to be
     * registered.
     *
     * @see #removeIIOWriteProgressListener
     */
    public void
        addIIOWriteProgressListener(IIOWriteProgressListener listener) {
        if (listener == null) {
            return;
        }
        progressListeners = ImageReader.addToList(progressListeners, listener);
    }

    /**
     * Removes an <code>IIOWriteProgressListener</code> from the list
     * of registered progress listeners.  If the listener was not
     * previously registered, or if <code>listener</code> is
     * <code>null</code>, no exception will be thrown and no action
     * will be taken.
     *
     * <p>
     *  从注册的进度侦听器列表中删除<code> IIOWriteProgressListener </code>。
     * 如果侦听器以前没有注册,或者<code> listener </code>是<code> null </code>,则不会抛出任何异常,不会执行任何操作。
     * 
     * 
     * @param listener an <code>IIOWriteProgressListener</code> to be
     * deregistered.
     *
     * @see #addIIOWriteProgressListener
     */
    public void
        removeIIOWriteProgressListener(IIOWriteProgressListener listener) {
        if (listener == null || progressListeners == null) {
            return;
        }
        progressListeners =
            ImageReader.removeFromList(progressListeners, listener);
    }

    /**
     * Removes all currently registered
     * <code>IIOWriteProgressListener</code> objects.
     *
     * <p> The default implementation sets the
     * <code>progressListeners</code> instance variable to
     * <code>null</code>.
     * <p>
     *  删除所有当前注册的<code> IIOWriteProgressListener </code>对象。
     * 
     *  <p>默认实现将<code> progressListeners </code>实例变量设置为<code> null </code>。
     * 
     */
    public void removeAllIIOWriteProgressListeners() {
        this.progressListeners = null;
    }

    /**
     * Broadcasts the start of an image write to all registered
     * <code>IIOWriteProgressListener</code>s by calling their
     * <code>imageStarted</code> method.  Subclasses may use this
     * method as a convenience.
     *
     * <p>
     *  通过调用其<code> imageStarted </code>方法,将图像写入的开始广播到所有注册的<code> IIOWriteProgressListener </code>子类可以使用此方法作
     * 为方便。
     * 
     * 
     * @param imageIndex the index of the image about to be written.
     */
    protected void processImageStarted(int imageIndex) {
        if (progressListeners == null) {
            return;
        }
        int numListeners = progressListeners.size();
        for (int i = 0; i < numListeners; i++) {
            IIOWriteProgressListener listener =
                (IIOWriteProgressListener)progressListeners.get(i);
            listener.imageStarted(this, imageIndex);
        }
    }

    /**
     * Broadcasts the current percentage of image completion to all
     * registered <code>IIOWriteProgressListener</code>s by calling
     * their <code>imageProgress</code> method.  Subclasses may use
     * this method as a convenience.
     *
     * <p>
     *  通过调用其<code> imageProgress </code>方法将当前百分比的图像完成广播到所有注册的<code> IIOWriteProgressListener </code>子类可以使用此
     * 方法作为方便。
     * 
     * 
     * @param percentageDone the current percentage of completion,
     * as a <code>float</code>.
     */
    protected void processImageProgress(float percentageDone) {
        if (progressListeners == null) {
            return;
        }
        int numListeners = progressListeners.size();
        for (int i = 0; i < numListeners; i++) {
            IIOWriteProgressListener listener =
                (IIOWriteProgressListener)progressListeners.get(i);
            listener.imageProgress(this, percentageDone);
        }
    }

    /**
     * Broadcasts the completion of an image write to all registered
     * <code>IIOWriteProgressListener</code>s by calling their
     * <code>imageComplete</code> method.  Subclasses may use this
     * method as a convenience.
     * <p>
     *  通过调用其<code> imageComplete </code>方法,将图像写入完成到所有注册的<code> IIOWriteProgressListener </code>子类可以使用此方法作为方
     * 便。
     * 
     */
    protected void processImageComplete() {
        if (progressListeners == null) {
            return;
        }
        int numListeners = progressListeners.size();
        for (int i = 0; i < numListeners; i++) {
            IIOWriteProgressListener listener =
                (IIOWriteProgressListener)progressListeners.get(i);
            listener.imageComplete(this);
        }
    }

    /**
     * Broadcasts the start of a thumbnail write to all registered
     * <code>IIOWriteProgressListener</code>s by calling their
     * <code>thumbnailStarted</code> method.  Subclasses may use this
     * method as a convenience.
     *
     * <p>
     * 通过调用其<code> thumbnailStarted </code>方法,将缩略图写入的开始广播到所有注册的<code> IIOWriteProgressListener </code>子类可以使用
     * 此方法作为方便。
     * 
     * 
     * @param imageIndex the index of the image associated with the
     * thumbnail.
     * @param thumbnailIndex the index of the thumbnail.
     */
    protected void processThumbnailStarted(int imageIndex,
                                           int thumbnailIndex) {
        if (progressListeners == null) {
            return;
        }
        int numListeners = progressListeners.size();
        for (int i = 0; i < numListeners; i++) {
            IIOWriteProgressListener listener =
                (IIOWriteProgressListener)progressListeners.get(i);
            listener.thumbnailStarted(this, imageIndex, thumbnailIndex);
        }
    }

    /**
     * Broadcasts the current percentage of thumbnail completion to
     * all registered <code>IIOWriteProgressListener</code>s by calling
     * their <code>thumbnailProgress</code> method.  Subclasses may
     * use this method as a convenience.
     *
     * <p>
     *  通过调用其<code> thumbnailProgress </code>方法,将缩略图完成的当前百分比广播到所有注册的<code> IIOWriteProgressListener </code>子
     * 类可以使用此方法作为方便。
     * 
     * 
     * @param percentageDone the current percentage of completion,
     * as a <code>float</code>.
     */
    protected void processThumbnailProgress(float percentageDone) {
        if (progressListeners == null) {
            return;
        }
        int numListeners = progressListeners.size();
        for (int i = 0; i < numListeners; i++) {
            IIOWriteProgressListener listener =
                (IIOWriteProgressListener)progressListeners.get(i);
            listener.thumbnailProgress(this, percentageDone);
        }
    }

    /**
     * Broadcasts the completion of a thumbnail write to all registered
     * <code>IIOWriteProgressListener</code>s by calling their
     * <code>thumbnailComplete</code> method.  Subclasses may use this
     * method as a convenience.
     * <p>
     *  通过调用其<code> thumbnailComplete </code>方法,向所有注册的<code> IIOWriteProgressListener </code>广播缩略图写入的完成。
     * 子类可以使用此方法作为方便。
     * 
     */
    protected void processThumbnailComplete() {
        if (progressListeners == null) {
            return;
        }
        int numListeners = progressListeners.size();
        for (int i = 0; i < numListeners; i++) {
            IIOWriteProgressListener listener =
                (IIOWriteProgressListener)progressListeners.get(i);
            listener.thumbnailComplete(this);
        }
    }

    /**
     * Broadcasts that the write has been aborted to all registered
     * <code>IIOWriteProgressListener</code>s by calling their
     * <code>writeAborted</code> method.  Subclasses may use this
     * method as a convenience.
     * <p>
     *  通过调用其<code> writeAborted </code>方法,写入已中止到所有注册的<code> IIOWriteProgressListener </code>子类可以使用此方法作为方便。
     * 
     */
    protected void processWriteAborted() {
        if (progressListeners == null) {
            return;
        }
        int numListeners = progressListeners.size();
        for (int i = 0; i < numListeners; i++) {
            IIOWriteProgressListener listener =
                (IIOWriteProgressListener)progressListeners.get(i);
            listener.writeAborted(this);
        }
    }

    /**
     * Broadcasts a warning message to all registered
     * <code>IIOWriteWarningListener</code>s by calling their
     * <code>warningOccurred</code> method.  Subclasses may use this
     * method as a convenience.
     *
     * <p>
     *  通过调用其<code> warningOccurred </code>方法向所有注册的<code> IIOWriteWarningListener </code>发出警告消息。
     * 子类可以使用此方法作为方便。
     * 
     * 
     * @param imageIndex the index of the image on which the warning
     * occurred.
     * @param warning the warning message.
     *
     * @exception IllegalArgumentException if <code>warning</code>
     * is <code>null</code>.
     */
    protected void processWarningOccurred(int imageIndex,
                                          String warning) {
        if (warningListeners == null) {
            return;
        }
        if (warning == null) {
            throw new IllegalArgumentException("warning == null!");
        }
        int numListeners = warningListeners.size();
        for (int i = 0; i < numListeners; i++) {
            IIOWriteWarningListener listener =
                (IIOWriteWarningListener)warningListeners.get(i);

            listener.warningOccurred(this, imageIndex, warning);
        }
    }

    /**
     * Broadcasts a localized warning message to all registered
     * <code>IIOWriteWarningListener</code>s by calling their
     * <code>warningOccurred</code> method with a string taken
     * from a <code>ResourceBundle</code>.  Subclasses may use this
     * method as a convenience.
     *
     * <p>
     *  通过使用从<code> ResourceBundle </code>中获取的字符串调用其<code> warningOccurred </code>方法,向所有注册的<code> IIOWriteWa
     * rningListener </code>广播本地化警告消息。
     * 子类可以使用此方法作为方便。
     * 
     * 
     * @param imageIndex the index of the image on which the warning
     * occurred.
     * @param baseName the base name of a set of
     * <code>ResourceBundle</code>s containing localized warning
     * messages.
     * @param keyword the keyword used to index the warning message
     * within the set of <code>ResourceBundle</code>s.
     *
     * @exception IllegalArgumentException if <code>baseName</code>
     * is <code>null</code>.
     * @exception IllegalArgumentException if <code>keyword</code>
     * is <code>null</code>.
     * @exception IllegalArgumentException if no appropriate
     * <code>ResourceBundle</code> may be located.
     * @exception IllegalArgumentException if the named resource is
     * not found in the located <code>ResourceBundle</code>.
     * @exception IllegalArgumentException if the object retrieved
     * from the <code>ResourceBundle</code> is not a
     * <code>String</code>.
     */
    protected void processWarningOccurred(int imageIndex,
                                          String baseName,
                                          String keyword) {
        if (warningListeners == null) {
            return;
        }
        if (baseName == null) {
            throw new IllegalArgumentException("baseName == null!");
        }
        if (keyword == null) {
            throw new IllegalArgumentException("keyword == null!");
        }
        int numListeners = warningListeners.size();
        for (int i = 0; i < numListeners; i++) {
            IIOWriteWarningListener listener =
                (IIOWriteWarningListener)warningListeners.get(i);
            Locale locale = (Locale)warningLocales.get(i);
            if (locale == null) {
                locale = Locale.getDefault();
            }

            /**
             * If an applet supplies an implementation of ImageWriter and
             * resource bundles, then the resource bundle will need to be
             * accessed via the applet class loader. So first try the context
             * class loader to locate the resource bundle.
             * If that throws MissingResourceException, then try the
             * system class loader.
             * <p>
             * 如果applet提供ImageWriter和资源束的实现,那么资源束将需要通过applet类加载器来访问。所以首先尝试上下文类加载器来定位资源束。
             * 如果抛出MissingResourceException,那么尝试系统类加载器。
             * 
             */
            ClassLoader loader = (ClassLoader)
                java.security.AccessController.doPrivileged(
                   new java.security.PrivilegedAction() {
                      public Object run() {
                        return Thread.currentThread().getContextClassLoader();
                      }
                });

            ResourceBundle bundle = null;
            try {
                bundle = ResourceBundle.getBundle(baseName, locale, loader);
            } catch (MissingResourceException mre) {
                try {
                    bundle = ResourceBundle.getBundle(baseName, locale);
                } catch (MissingResourceException mre1) {
                    throw new IllegalArgumentException("Bundle not found!");
                }
            }

            String warning = null;
            try {
                warning = bundle.getString(keyword);
            } catch (ClassCastException cce) {
                throw new IllegalArgumentException("Resource is not a String!");
            } catch (MissingResourceException mre) {
                throw new IllegalArgumentException("Resource is missing!");
            }

            listener.warningOccurred(this, imageIndex, warning);
        }
    }

    // State management

    /**
     * Restores the <code>ImageWriter</code> to its initial state.
     *
     * <p> The default implementation calls
     * <code>setOutput(null)</code>, <code>setLocale(null)</code>,
     * <code>removeAllIIOWriteWarningListeners()</code>,
     * <code>removeAllIIOWriteProgressListeners()</code>, and
     * <code>clearAbortRequest</code>.
     * <p>
     *  将<code> ImageWriter </code>恢复到其初始状态。
     * 
     *  <p>默认实现调用<code> setOutput(null)</code>,<code> setLocale(null)</code>,<code> removeAllIIOWriteWarning
     * Listeners()</code>,<code> removeAllIIOWriteProgressListeners代码>和<code> clearAbortRequest </code>。
     * 
     */
    public void reset() {
        setOutput(null);
        setLocale(null);
        removeAllIIOWriteWarningListeners();
        removeAllIIOWriteProgressListeners();
        clearAbortRequest();
    }

    /**
     * Allows any resources held by this object to be released.  The
     * result of calling any other method (other than
     * <code>finalize</code>) subsequent to a call to this method
     * is undefined.
     *
     * <p>It is important for applications to call this method when they
     * know they will no longer be using this <code>ImageWriter</code>.
     * Otherwise, the writer may continue to hold on to resources
     * indefinitely.
     *
     * <p>The default implementation of this method in the superclass does
     * nothing.  Subclass implementations should ensure that all resources,
     * especially native resources, are released.
     * <p>
     *  允许释放此对象持有的任何资源。未调用在调用此方法后调用任何其他方法(<code> finalize </code>除外)的结果。
     * 
     *  <p>当应用程序知道他们将不再使用此<code> ImageWriter </code>时,调用此方法很重要。否则,作家可能继续无限期地持有资源。
     */
    public void dispose() {
    }
}
