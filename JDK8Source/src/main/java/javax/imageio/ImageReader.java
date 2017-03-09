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

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.event.IIOReadWarningListener;
import javax.imageio.event.IIOReadProgressListener;
import javax.imageio.event.IIOReadUpdateListener;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataFormatImpl;
import javax.imageio.stream.ImageInputStream;

/**
 * An abstract superclass for parsing and decoding of images.  This
 * class must be subclassed by classes that read in images in the
 * context of the Java Image I/O framework.
 *
 * <p> <code>ImageReader</code> objects are normally instantiated by
 * the service provider interface (SPI) class for the specific format.
 * Service provider classes (e.g., instances of
 * <code>ImageReaderSpi</code>) are registered with the
 * <code>IIORegistry</code>, which uses them for format recognition
 * and presentation of available format readers and writers.
 *
 * <p> When an input source is set (using the <code>setInput</code>
 * method), it may be marked as "seek forward only".  This setting
 * means that images contained within the input source will only be
 * read in order, possibly allowing the reader to avoid caching
 * portions of the input containing data associated with images that
 * have been read previously.
 *
 * <p>
 *  用于解析和解码图像的抽象超类。这个类必须由在Java Image I / O框架的上下文中读入图像的类进行子类化。
 * 
 *  <p> <code> ImageReader </code>对象通常由特定格式的服务提供程序接口(SPI)类实例化。
 * 服务提供者类(例如,<code> ImageReaderSpi </code>的实例)向<code> IIORegistry </code>注册,其使用它们用于格式识别和可用格式读取器和写入器的呈现。
 * 
 *  <p>当设置输入源时(使用<code> setInput </code>方法),它可能会被标记为"仅限向前搜索"。
 * 该设置意味着包含在输入源内的图像将仅按顺序读取,可能允许读取器避免缓存包含与之前已经读取的图像相关联的数据的输入的部分。
 * 
 * 
 * @see ImageWriter
 * @see javax.imageio.spi.IIORegistry
 * @see javax.imageio.spi.ImageReaderSpi
 *
 */
public abstract class ImageReader {

    /**
     * The <code>ImageReaderSpi</code> that instantiated this object,
     * or <code>null</code> if its identity is not known or none
     * exists.  By default it is initialized to <code>null</code>.
     * <p>
     *  实例化此对象的<code> ImageReaderSpi </code>,如果其身份未知或不存在,则<code> null </code>。
     * 默认情况下,它被初始化为<code> null </code>。
     * 
     */
    protected ImageReaderSpi originatingProvider;

    /**
     * The <code>ImageInputStream</code> or other
     * <code>Object</code> by <code>setInput</code> and retrieved
     * by <code>getInput</code>.  By default it is initialized to
     * <code>null</code>.
     * <p>
     *  通过<code> setInput </code>并由<code> getInput </code>检索的<code> ImageInputStream </code>或其他<code> Object
     *  </code>。
     * 默认情况下,它被初始化为<code> null </code>。
     * 
     */
    protected Object input = null;

    /**
     * <code>true</code> if the current input source has been marked
     * as allowing only forward seeking by <code>setInput</code>.  By
     * default, the value is <code>false</code>.
     *
     * <p>
     * <code> true </code>如果当前输入源已被标记为仅允许通过<code> setInput </code>进行向前搜索。默认情况下,值为<code> false </code>。
     * 
     * 
     * @see #minIndex
     * @see #setInput
     */
    protected boolean seekForwardOnly = false;

    /**
     * <code>true</code> if the current input source has been marked
     * as allowing metadata to be ignored by <code>setInput</code>.
     * By default, the value is <code>false</code>.
     *
     * <p>
     *  <code> true </code>如果当前输入源已被标记为允许元数据被<code> setInput </code>忽略。默认情况下,值为<code> false </code>。
     * 
     * 
     * @see #setInput
     */
    protected boolean ignoreMetadata = false;

    /**
     * The smallest valid index for reading, initially 0.  When
     * <code>seekForwardOnly</code> is <code>true</code>, various methods
     * may throw an <code>IndexOutOfBoundsException</code> on an
     * attempt to access data associate with an image having a lower
     * index.
     *
     * <p>
     *  当<code> seekForwardOnly </code>是<code> true </code>时,各种方法可能会在尝试访问数据时引发<code> IndexOutOfBoundsExcepti
     * on </code>具有较低索引的图像。
     * 
     * 
     * @see #seekForwardOnly
     * @see #setInput
     */
    protected int minIndex = 0;

    /**
     * An array of <code>Locale</code>s which may be used to localize
     * warning messages, or <code>null</code> if localization is not
     * supported.
     * <p>
     *  可以用于本地化警告消息的<code> Locale </code>数组,如果不支持本地化,则为<code> null </code>。
     * 
     */
    protected Locale[] availableLocales = null;

    /**
     * The current <code>Locale</code> to be used for localization, or
     * <code>null</code> if none has been set.
     * <p>
     *  要用于本地化的当前<code> Locale </code>,如果没有设置,则为<code> null </code>。
     * 
     */
    protected Locale locale = null;

    /**
     * A <code>List</code> of currently registered
     * <code>IIOReadWarningListener</code>s, initialized by default to
     * <code>null</code>, which is synonymous with an empty
     * <code>List</code>.
     * <p>
     *  当前注册的<code> IIOReadWarningListener </code>的<code> List </code>,默认初始化为<code> null </code>,与空的<code> L
     * ist </code>。
     * 
     */
    protected List<IIOReadWarningListener> warningListeners = null;

    /**
     * A <code>List</code> of the <code>Locale</code>s associated with
     * each currently registered <code>IIOReadWarningListener</code>,
     * initialized by default to <code>null</code>, which is
     * synonymous with an empty <code>List</code>.
     * <p>
     *  与每个当前注册的<code> IIOReadWarningListener </code>相关联的<code> Locale </code>的<code> List </code>,默认初始化为<code>
     *  null </code>带有一个空的<code> List </code>。
     * 
     */
    protected List<Locale> warningLocales = null;

    /**
     * A <code>List</code> of currently registered
     * <code>IIOReadProgressListener</code>s, initialized by default
     * to <code>null</code>, which is synonymous with an empty
     * <code>List</code>.
     * <p>
     *  当前注册的<code> IIOReadProgressListener </code>的<code> List </code>,默认初始化为<code> null </code>,与空的<code> 
     * List </code>。
     * 
     */
    protected List<IIOReadProgressListener> progressListeners = null;

    /**
     * A <code>List</code> of currently registered
     * <code>IIOReadUpdateListener</code>s, initialized by default to
     * <code>null</code>, which is synonymous with an empty
     * <code>List</code>.
     * <p>
     * 当前注册的<code> IIOReadUpdateListener </code>的<code> List </code>,默认初始化为<code> null </code>,与空的<code> Lis
     * t </code>。
     * 
     */
    protected List<IIOReadUpdateListener> updateListeners = null;

    /**
     * If <code>true</code>, the current read operation should be
     * aborted.
     * <p>
     *  如果<code> true </code>,则应该中止当前的读操作。
     * 
     */
    private boolean abortFlag = false;

    /**
     * Constructs an <code>ImageReader</code> and sets its
     * <code>originatingProvider</code> field to the supplied value.
     *
     * <p> Subclasses that make use of extensions should provide a
     * constructor with signature <code>(ImageReaderSpi,
     * Object)</code> in order to retrieve the extension object.  If
     * the extension object is unsuitable, an
     * <code>IllegalArgumentException</code> should be thrown.
     *
     * <p>
     *  构造一个<code> ImageReader </code>并将其<code> originatingProvider </code>字段设置为提供的值。
     * 
     *  <p>使用扩展的子类应该提供一个带有签名<code>(ImageReaderSpi,Object)</code>的构造函数,以便检索扩展对象。
     * 如果扩展对象不合适,应该抛出一个<code> IllegalArgumentException </code>。
     * 
     * 
     * @param originatingProvider the <code>ImageReaderSpi</code> that is
     * invoking this constructor, or <code>null</code>.
     */
    protected ImageReader(ImageReaderSpi originatingProvider) {
        this.originatingProvider = originatingProvider;
    }

    /**
     * Returns a <code>String</code> identifying the format of the
     * input source.
     *
     * <p> The default implementation returns
     * <code>originatingProvider.getFormatNames()[0]</code>.
     * Implementations that may not have an originating service
     * provider, or which desire a different naming policy should
     * override this method.
     *
     * <p>
     *  返回一个标识输入源格式的<code> String </code>。
     * 
     *  <p>默认实现返回<code> originatingProvider.getFormatNames()[0] </code>。可能没有始发服务提供程序或希望使用不同命名策略的实现应覆盖此方法。
     * 
     * 
     * @exception IOException if an error occurs reading the
     * information from the input source.
     *
     * @return the format name, as a <code>String</code>.
     */
    public String getFormatName() throws IOException {
        return originatingProvider.getFormatNames()[0];
    }

    /**
     * Returns the <code>ImageReaderSpi</code> that was passed in on
     * the constructor.  Note that this value may be <code>null</code>.
     *
     * <p>
     *  返回在构造函数中传递的<code> ImageReaderSpi </code>。注意,该值可以是<code> null </code>。
     * 
     * 
     * @return an <code>ImageReaderSpi</code>, or <code>null</code>.
     *
     * @see ImageReaderSpi
     */
    public ImageReaderSpi getOriginatingProvider() {
        return originatingProvider;
    }

    /**
     * Sets the input source to use to the given
     * <code>ImageInputStream</code> or other <code>Object</code>.
     * The input source must be set before any of the query or read
     * methods are used.  If <code>input</code> is <code>null</code>,
     * any currently set input source will be removed.  In any case,
     * the value of <code>minIndex</code> will be initialized to 0.
     *
     * <p> The <code>seekForwardOnly</code> parameter controls whether
     * the value returned by <code>getMinIndex</code> will be
     * increased as each image (or thumbnail, or image metadata) is
     * read.  If <code>seekForwardOnly</code> is true, then a call to
     * <code>read(index)</code> will throw an
     * <code>IndexOutOfBoundsException</code> if {@code index < this.minIndex};
     * otherwise, the value of
     * <code>minIndex</code> will be set to <code>index</code>.  If
     * <code>seekForwardOnly</code> is <code>false</code>, the value of
     * <code>minIndex</code> will remain 0 regardless of any read
     * operations.
     *
     * <p> The <code>ignoreMetadata</code> parameter, if set to
     * <code>true</code>, allows the reader to disregard any metadata
     * encountered during the read.  Subsequent calls to the
     * <code>getStreamMetadata</code> and
     * <code>getImageMetadata</code> methods may return
     * <code>null</code>, and an <code>IIOImage</code> returned from
     * <code>readAll</code> may return <code>null</code> from their
     * <code>getMetadata</code> method.  Setting this parameter may
     * allow the reader to work more efficiently.  The reader may
     * choose to disregard this setting and return metadata normally.
     *
     * <p> Subclasses should take care to remove any cached
     * information based on the previous stream, such as header
     * information or partially decoded image data.
     *
     * <p> Use of a general <code>Object</code> other than an
     * <code>ImageInputStream</code> is intended for readers that
     * interact directly with a capture device or imaging protocol.
     * The set of legal classes is advertised by the reader's service
     * provider's <code>getInputTypes</code> method; most readers
     * will return a single-element array containing only
     * <code>ImageInputStream.class</code> to indicate that they
     * accept only an <code>ImageInputStream</code>.
     *
     * <p> The default implementation checks the <code>input</code>
     * argument against the list returned by
     * <code>originatingProvider.getInputTypes()</code> and fails
     * if the argument is not an instance of one of the classes
     * in the list.  If the originating provider is set to
     * <code>null</code>, the input is accepted only if it is an
     * <code>ImageInputStream</code>.
     *
     * <p>
     *  将要使用的输入源设置为给定的<code> ImageInputStream </code>或其他<code> Object </code>。在使用任何查询或读取方法之前,必须设置输入源。
     * 如果<code> input </code>是<code> null </code>,任何当前设置的输入源将被删除。在任何情况下,<code> minIndex </code>的值将初始化为0。
     * 
     * <p> <code> seekForwardOnly </code>参数控制<code> getMinIndex </code>返回的值是否随着每个图像(或缩略图或图像元数据)的读取而增加。
     * 如果<code> seekForwardOnly </code>为true,则调用<code> read(index)</code>会抛出<code> IndexOutOfBoundsException
     *  </code>,如果{@code index <this.minIndex}否则,<code> minIndex </code>的值将设置为<code> index </code>。
     * <p> <code> seekForwardOnly </code>参数控制<code> getMinIndex </code>返回的值是否随着每个图像(或缩略图或图像元数据)的读取而增加。
     * 如果<code> seekForwardOnly </code>是<code> false </code>,则<code> minIndex </code>的值将保持为0,而不管任何读取操作。
     * 
     *  <p> <code> ignoreMetadata </code>参数(如果设置为<code> true </code>)允许读者忽略读取过程中遇到的任何元数据。
     * 对<code> getStreamMetadata </code>和<code> getImageMetadata </code>方法的后续调用可以返回<code> null </code>和从<code>
     *  readAll < / code>可以从<code> getMetadata </code>方法中返回<code> null </code>。
     *  <p> <code> ignoreMetadata </code>参数(如果设置为<code> true </code>)允许读者忽略读取过程中遇到的任何元数据。
     * 设置此参数可以允许读取器更有效地工作。读者可以选择忽略此设置并正常返回元数据。
     * 
     *  <p>子类别应小心删除基于上一个流的任何缓存信息,例如标题信息或部分解码的图像数据。
     * 
     * <p>使用除<code> ImageInputStream </code>之外的一般<code>对象</code>,是为与读取器直接交互的捕获设备或成像协议。
     * 该组法律类由读者的服务提供商的<code> getInputTypes </code>方法广告;大多数读者将返回一个只包含<code> ImageInputStream.class </code>的单元
     * 素数组,表示它们只接受一个<code> ImageInputStream </code>。
     * <p>使用除<code> ImageInputStream </code>之外的一般<code>对象</code>,是为与读取器直接交互的捕获设备或成像协议。
     * 
     *  <p>默认实现会根据<code> originatingProvider.getInputTypes()</code>返回的列表检查<code> input </code>参数,如果参数不是列表。
     * 如果始发提供程序设置为<code> null </code>,则只有当它是<code> ImageInputStream </code>时,才接受输入。
     * 
     * 
     * @param input the <code>ImageInputStream</code> or other
     * <code>Object</code> to use for future decoding.
     * @param seekForwardOnly if <code>true</code>, images and metadata
     * may only be read in ascending order from this input source.
     * @param ignoreMetadata if <code>true</code>, metadata
     * may be ignored during reads.
     *
     * @exception IllegalArgumentException if <code>input</code> is
     * not an instance of one of the classes returned by the
     * originating service provider's <code>getInputTypes</code>
     * method, or is not an <code>ImageInputStream</code>.
     *
     * @see ImageInputStream
     * @see #getInput
     * @see javax.imageio.spi.ImageReaderSpi#getInputTypes
     */
    public void setInput(Object input,
                         boolean seekForwardOnly,
                         boolean ignoreMetadata) {
        if (input != null) {
            boolean found = false;
            if (originatingProvider != null) {
                Class[] classes = originatingProvider.getInputTypes();
                for (int i = 0; i < classes.length; i++) {
                    if (classes[i].isInstance(input)) {
                        found = true;
                        break;
                    }
                }
            } else {
                if (input instanceof ImageInputStream) {
                    found = true;
                }
            }
            if (!found) {
                throw new IllegalArgumentException("Incorrect input type!");
            }

            this.seekForwardOnly = seekForwardOnly;
            this.ignoreMetadata = ignoreMetadata;
            this.minIndex = 0;
        }

        this.input = input;
    }

    /**
     * Sets the input source to use to the given
     * <code>ImageInputStream</code> or other <code>Object</code>.
     * The input source must be set before any of the query or read
     * methods are used.  If <code>input</code> is <code>null</code>,
     * any currently set input source will be removed.  In any case,
     * the value of <code>minIndex</code> will be initialized to 0.
     *
     * <p> The <code>seekForwardOnly</code> parameter controls whether
     * the value returned by <code>getMinIndex</code> will be
     * increased as each image (or thumbnail, or image metadata) is
     * read.  If <code>seekForwardOnly</code> is true, then a call to
     * <code>read(index)</code> will throw an
     * <code>IndexOutOfBoundsException</code> if {@code index < this.minIndex};
     * otherwise, the value of
     * <code>minIndex</code> will be set to <code>index</code>.  If
     * <code>seekForwardOnly</code> is <code>false</code>, the value of
     * <code>minIndex</code> will remain 0 regardless of any read
     * operations.
     *
     * <p> This method is equivalent to <code>setInput(input,
     * seekForwardOnly, false)</code>.
     *
     * <p>
     *  将要使用的输入源设置为给定的<code> ImageInputStream </code>或其他<code> Object </code>。在使用任何查询或读取方法之前,必须设置输入源。
     * 如果<code> input </code>是<code> null </code>,任何当前设置的输入源将被删除。在任何情况下,<code> minIndex </code>的值将初始化为0。
     * 
     * <p> <code> seekForwardOnly </code>参数控制<code> getMinIndex </code>返回的值是否随着每个图像(或缩略图或图像元数据)的读取而增加。
     * 如果<code> seekForwardOnly </code>为true,则调用<code> read(index)</code>会抛出<code> IndexOutOfBoundsException
     *  </code>,如果{@code index <this.minIndex}否则,<code> minIndex </code>的值将设置为<code> index </code>。
     * <p> <code> seekForwardOnly </code>参数控制<code> getMinIndex </code>返回的值是否随着每个图像(或缩略图或图像元数据)的读取而增加。
     * 如果<code> seekForwardOnly </code>是<code> false </code>,则<code> minIndex </code>的值将保持为0,而不管任何读取操作。
     * 
     *  <p>此方法等效于<code> setInput(input,seekForwardOnly,false)</code>。
     * 
     * 
     * @param input the <code>ImageInputStream</code> or other
     * <code>Object</code> to use for future decoding.
     * @param seekForwardOnly if <code>true</code>, images and metadata
     * may only be read in ascending order from this input source.
     *
     * @exception IllegalArgumentException if <code>input</code> is
     * not an instance of one of the classes returned by the
     * originating service provider's <code>getInputTypes</code>
     * method, or is not an <code>ImageInputStream</code>.
     *
     * @see #getInput
     */
    public void setInput(Object input,
                         boolean seekForwardOnly) {
        setInput(input, seekForwardOnly, false);
    }

    /**
     * Sets the input source to use to the given
     * <code>ImageInputStream</code> or other <code>Object</code>.
     * The input source must be set before any of the query or read
     * methods are used.  If <code>input</code> is <code>null</code>,
     * any currently set input source will be removed.  In any case,
     * the value of <code>minIndex</code> will be initialized to 0.
     *
     * <p> This method is equivalent to <code>setInput(input, false,
     * false)</code>.
     *
     * <p>
     *  将要使用的输入源设置为给定的<code> ImageInputStream </code>或其他<code> Object </code>。在使用任何查询或读取方法之前,必须设置输入源。
     * 如果<code> input </code>是<code> null </code>,任何当前设置的输入源将被删除。在任何情况下,<code> minIndex </code>的值将初始化为0。
     * 
     *  <p>此方法等效于<code> setInput(input,false,false)</code>。
     * 
     * 
     * @param input the <code>ImageInputStream</code> or other
     * <code>Object</code> to use for future decoding.
     *
     * @exception IllegalArgumentException if <code>input</code> is
     * not an instance of one of the classes returned by the
     * originating service provider's <code>getInputTypes</code>
     * method, or is not an <code>ImageInputStream</code>.
     *
     * @see #getInput
     */
    public void setInput(Object input) {
        setInput(input, false, false);
    }

    /**
     * Returns the <code>ImageInputStream</code> or other
     * <code>Object</code> previously set as the input source.  If the
     * input source has not been set, <code>null</code> is returned.
     *
     * <p>
     *  返回之前设置为输入源的<code> ImageInputStream </code>或其他<code> Object </code>。如果未设置输入源,则返回<code> null </code>。
     * 
     * 
     * @return the <code>Object</code> that will be used for future
     * decoding, or <code>null</code>.
     *
     * @see ImageInputStream
     * @see #setInput
     */
    public Object getInput() {
        return input;
    }

    /**
     * Returns <code>true</code> if the current input source has been
     * marked as seek forward only by passing <code>true</code> as the
     * <code>seekForwardOnly</code> argument to the
     * <code>setInput</code> method.
     *
     * <p>
     *  如果当前输入源已通过将<code> true </code>作为<code> seekForwardOnly </code>参数传递给<code> setInput </code>标记为seek fo
     * rward,则返回<code> true </code>代码>方法。
     * 
     * 
     * @return <code>true</code> if the input source is seek forward
     * only.
     *
     * @see #setInput
     */
    public boolean isSeekForwardOnly() {
        return seekForwardOnly;
    }

    /**
     * Returns <code>true</code> if the current input source has been
     * marked as allowing metadata to be ignored by passing
     * <code>true</code> as the <code>ignoreMetadata</code> argument
     * to the <code>setInput</code> method.
     *
     * <p>
     * 如果当前输入源已标记为允许通过将<code> true </code>作为<code> ignoreMetadata </code>参数传递给<code> setInput而忽略元数据,则返回<code>
     *  true </code> </code>方法。
     * 
     * 
     * @return <code>true</code> if the metadata may be ignored.
     *
     * @see #setInput
     */
    public boolean isIgnoringMetadata() {
        return ignoreMetadata;
    }

    /**
     * Returns the lowest valid index for reading an image, thumbnail,
     * or image metadata.  If <code>seekForwardOnly()</code> is
     * <code>false</code>, this value will typically remain 0,
     * indicating that random access is possible.  Otherwise, it will
     * contain the value of the most recently accessed index, and
     * increase in a monotonic fashion.
     *
     * <p>
     *  返回读取图像,缩略图或图像元数据的最低有效索引。
     * 如果<code> seekForwardOnly()</code>是<code> false </code>,则该值通常将保持为0,指示可以进行随机访问。
     * 否则,它将包含最近访问的索引的值,并以单调方式增加。
     * 
     * 
     * @return the minimum legal index for reading.
     */
    public int getMinIndex() {
        return minIndex;
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
        if (availableLocales == null) {
            return null;
        } else {
            return (Locale[])availableLocales.clone();
        }
    }

    /**
     * Sets the current <code>Locale</code> of this
     * <code>ImageReader</code> to the given value.  A value of
     * <code>null</code> removes any previous setting, and indicates
     * that the reader should localize as it sees fit.
     *
     * <p>
     *  将此<code> ImageReader </code>的当前<code> Locale </code>设置为给定值。
     *  <code> null </code>的值会删除任何先前的设置,并指示读者应该按其认为合适的方式进行本地化。
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
     * <p>
     *  返回当前设置的<code> Locale </code>或<code> null </code>(如果没有设置)。
     * 
     * 
     * @return the current <code>Locale</code>, or <code>null</code>.
     *
     * @see #setLocale
     */
    public Locale getLocale() {
        return locale;
    }

    // Image queries

    /**
     * Returns the number of images, not including thumbnails, available
     * from the current input source.
     *
     * <p> Note that some image formats (such as animated GIF) do not
     * specify how many images are present in the stream.  Thus
     * determining the number of images will require the entire stream
     * to be scanned and may require memory for buffering.  If images
     * are to be processed in order, it may be more efficient to
     * simply call <code>read</code> with increasing indices until an
     * <code>IndexOutOfBoundsException</code> is thrown to indicate
     * that no more images are available.  The
     * <code>allowSearch</code> parameter may be set to
     * <code>false</code> to indicate that an exhaustive search is not
     * desired; the return value will be <code>-1</code> to indicate
     * that a search is necessary.  If the input has been specified
     * with <code>seekForwardOnly</code> set to <code>true</code>,
     * this method throws an <code>IllegalStateException</code> if
     * <code>allowSearch</code> is set to <code>true</code>.
     *
     * <p>
     *  返回当前输入源可用的图像数量,不包括缩略图。
     * 
     * <p>请注意,某些图片格式(例如动画GIF)不会指定流中存在多少图片。因此,确定图像的数量将需要扫描整个流,并且可能需要存储器用于缓冲。
     * 如果要按顺序处理图像,则可以更有效地简单地用递增的索引调用<code>读取</code>,直到抛出<code> IndexOutOfBoundsException </code>以指示没有更多图像可用为
     * 止。
     * <p>请注意,某些图片格式(例如动画GIF)不会指定流中存在多少图片。因此,确定图像的数量将需要扫描整个流,并且可能需要存储器用于缓冲。
     *  <code> allowSearch </code>参数可以设置为<code> false </code>以指示不需要穷举搜索;返回值将是<code> -1 </code>以指示需要搜索。
     * 如果已经使用<code> seekForwardOnly </code>设置为<code> true </code>指定输入,则此方法会将<code> IllegalStateException </code>
     * 引发为<code> allowSearch </code> <code> true </code>。
     *  <code> allowSearch </code>参数可以设置为<code> false </code>以指示不需要穷举搜索;返回值将是<code> -1 </code>以指示需要搜索。
     * 
     * 
     * @param allowSearch if <code>true</code>, the true number of
     * images will be returned even if a search is required.  If
     * <code>false</code>, the reader may return <code>-1</code>
     * without performing the search.
     *
     * @return the number of images, as an <code>int</code>, or
     * <code>-1</code> if <code>allowSearch</code> is
     * <code>false</code> and a search would be required.
     *
     * @exception IllegalStateException if the input source has not been set,
     * or if the input has been specified with <code>seekForwardOnly</code>
     * set to <code>true</code>.
     * @exception IOException if an error occurs reading the
     * information from the input source.
     *
     * @see #setInput
     */
    public abstract int getNumImages(boolean allowSearch) throws IOException;

    /**
     * Returns the width in pixels of the given image within the input
     * source.
     *
     * <p> If the image can be rendered to a user-specified size, then
     * this method returns the default width.
     *
     * <p>
     *  返回输入源中给定图像的宽度(以像素为单位)。
     * 
     *  <p>如果图像可以呈现为用户指定的大小,则此方法返回默认宽度。
     * 
     * 
     * @param imageIndex the index of the image to be queried.
     *
     * @return the width of the image, as an <code>int</code>.
     *
     * @exception IllegalStateException if the input source has not been set.
     * @exception IndexOutOfBoundsException if the supplied index is
     * out of bounds.
     * @exception IOException if an error occurs reading the width
     * information from the input source.
     */
    public abstract int getWidth(int imageIndex) throws IOException;

    /**
     * Returns the height in pixels of the given image within the
     * input source.
     *
     * <p> If the image can be rendered to a user-specified size, then
     * this method returns the default height.
     *
     * <p>
     *  返回输入源中给定图像的高度(以像素为单位)。
     * 
     *  <p>如果图像可以呈现为用户指定的大小,则此方法将返回默认高度。
     * 
     * 
     * @param imageIndex the index of the image to be queried.
     *
     * @return the height of the image, as an <code>int</code>.
     *
     * @exception IllegalStateException if the input source has not been set.
     * @exception IndexOutOfBoundsException if the supplied index is
     * out of bounds.
     * @exception IOException if an error occurs reading the height
     * information from the input source.
     */
    public abstract int getHeight(int imageIndex) throws IOException;

    /**
     * Returns <code>true</code> if the storage format of the given
     * image places no inherent impediment on random access to pixels.
     * For most compressed formats, such as JPEG, this method should
     * return <code>false</code>, as a large section of the image in
     * addition to the region of interest may need to be decoded.
     *
     * <p> This is merely a hint for programs that wish to be
     * efficient; all readers must be able to read arbitrary regions
     * as specified in an <code>ImageReadParam</code>.
     *
     * <p> Note that formats that return <code>false</code> from
     * this method may nonetheless allow tiling (<i>e.g.</i> Restart
     * Markers in JPEG), and random access will likely be reasonably
     * efficient on tiles.  See {@link #isImageTiled isImageTiled}.
     *
     * <p> A reader for which all images are guaranteed to support
     * easy random access, or are guaranteed not to support easy
     * random access, may return <code>true</code> or
     * <code>false</code> respectively without accessing any image
     * data.  In such cases, it is not necessary to throw an exception
     * even if no input source has been set or the image index is out
     * of bounds.
     *
     * <p> The default implementation returns <code>false</code>.
     *
     * <p>
     * 如果给定图像的存储格式没有对随机访问像素的固有障碍,则返回<code> true </code>。
     * 对于大多数压缩格式(例如JPEG),此方法应返回<code> false </code>,因为除了感兴趣区域之外,图像的大部分可能需要解码。
     * 
     *  <p>这只是希望高效的节目的提示;所有读取器必须能够读取<code> ImageReadParam </code>中指定的任意区域。
     * 
     *  <p>请注意,从此方法返回<code> false </code>的格式可能允许平铺(<i>例如</i>在JPEG中重新启动标记),并且随机访问在图块上可能相当高效。
     * 请参阅{@link #isImageTiled isImageTiled}。
     * 
     *  <p>所有图片均可确保支持轻松随机存取,或保证不支持轻松随机存取的读取器可分别返回<code> true </code>或<code> false </code>图像数据。
     * 在这种情况下,即使没有设置输入源或图像索引超出边界,也不必抛出异常。
     * 
     *  <p>默认实现返回<code> false </code>。
     * 
     * 
     * @param imageIndex the index of the image to be queried.
     *
     * @return <code>true</code> if reading a region of interest of
     * the given image is likely to be efficient.
     *
     * @exception IllegalStateException if an input source is required
     * to determine the return value, but none has been set.
     * @exception IndexOutOfBoundsException if an image must be
     * accessed to determine the return value, but the supplied index
     * is out of bounds.
     * @exception IOException if an error occurs during reading.
     */
    public boolean isRandomAccessEasy(int imageIndex) throws IOException {
        return false;
    }

    /**
     * Returns the aspect ratio of the given image (that is, its width
     * divided by its height) as a <code>float</code>.  For images
     * that are inherently resizable, this method provides a way to
     * determine the appropriate width given a desired height, or vice
     * versa.  For non-resizable images, the true width and height
     * are used.
     *
     * <p> The default implementation simply returns
     * <code>(float)getWidth(imageIndex)/getHeight(imageIndex)</code>.
     *
     * <p>
     *  将给定图像的宽高比(即,其宽度除以其高度)返回为<code> float </code>。对于固有地可调整大小的图像,该方法提供了一种在给定期望高度的情况下确定适当宽度的方式,反之亦然。
     * 对于不可调整大小的图像,使用真实的宽度和高度。
     * 
     * <p>默认实现只返回<code>(float)getWidth(imageIndex)/ getHeight(imageIndex)</code>。
     * 
     * 
     * @param imageIndex the index of the image to be queried.
     *
     * @return a <code>float</code> indicating the aspect ratio of the
     * given image.
     *
     * @exception IllegalStateException if the input source has not been set.
     * @exception IndexOutOfBoundsException if the supplied index is
     * out of bounds.
     * @exception IOException if an error occurs during reading.
     */
    public float getAspectRatio(int imageIndex) throws IOException {
        return (float)getWidth(imageIndex)/getHeight(imageIndex);
    }

    /**
     * Returns an <code>ImageTypeSpecifier</code> indicating the
     * <code>SampleModel</code> and <code>ColorModel</code> which most
     * closely represents the "raw" internal format of the image.  For
     * example, for a JPEG image the raw type might have a YCbCr color
     * space even though the image would conventionally be transformed
     * into an RGB color space prior to display.  The returned value
     * should also be included in the list of values returned by
     * <code>getImageTypes</code>.
     *
     * <p> The default implementation simply returns the first entry
     * from the list provided by <code>getImageType</code>.
     *
     * <p>
     *  返回指示最接近地表示图像的"原始"内部格式的<code> SampleModel </code>和<code> ColorModel </code>的<code> ImageTypeSpecifier
     *  </code>。
     * 例如,对于JPEG图像,原始类型可能具有YCbCr色彩空间,即使图像在显示之前将常规地变换成RGB色彩空间。返回的值也应包含在<code> getImageTypes </code>返回的值列表中。
     * 
     *  <p>默认实现只返回<code> getImageType </code>提供的列表中的第一个条目。
     * 
     * 
     * @param imageIndex the index of the image to be queried.
     *
     * @return an <code>ImageTypeSpecifier</code>.
     *
     * @exception IllegalStateException if the input source has not been set.
     * @exception IndexOutOfBoundsException if the supplied index is
     * out of bounds.
     * @exception IOException if an error occurs reading the format
     * information from the input source.
     */
    public ImageTypeSpecifier getRawImageType(int imageIndex)
        throws IOException {
        return (ImageTypeSpecifier)getImageTypes(imageIndex).next();
    }

    /**
     * Returns an <code>Iterator</code> containing possible image
     * types to which the given image may be decoded, in the form of
     * <code>ImageTypeSpecifiers</code>s.  At least one legal image
     * type will be returned.
     *
     * <p> The first element of the iterator should be the most
     * "natural" type for decoding the image with as little loss as
     * possible.  For example, for a JPEG image the first entry should
     * be an RGB image, even though the image data is stored
     * internally in a YCbCr color space.
     *
     * <p>
     *  返回<code> Iterator </code>,其中包含可以解码给定图像的可能的图像类型,格式为<code> ImageTypeSpecifiers </code>。将返回至少一个法定图片类型。
     * 
     *  <p>迭代器的第一个元素应该是用于以尽可能少的损失对图像进行解码的最自然的类型。例如,对于JPEG图像,第一条目应该是RGB图像,即使图像数据内部存储在YCbCr颜色空间中。
     * 
     * 
     * @param imageIndex the index of the image to be
     * <code>retrieved</code>.
     *
     * @return an <code>Iterator</code> containing at least one
     * <code>ImageTypeSpecifier</code> representing suggested image
     * types for decoding the current given image.
     *
     * @exception IllegalStateException if the input source has not been set.
     * @exception IndexOutOfBoundsException if the supplied index is
     * out of bounds.
     * @exception IOException if an error occurs reading the format
     * information from the input source.
     *
     * @see ImageReadParam#setDestination(BufferedImage)
     * @see ImageReadParam#setDestinationType(ImageTypeSpecifier)
     */
    public abstract Iterator<ImageTypeSpecifier>
        getImageTypes(int imageIndex) throws IOException;

    /**
     * Returns a default <code>ImageReadParam</code> object
     * appropriate for this format.  All subclasses should define a
     * set of default values for all parameters and return them with
     * this call.  This method may be called before the input source
     * is set.
     *
     * <p> The default implementation constructs and returns a new
     * <code>ImageReadParam</code> object that does not allow source
     * scaling (<i>i.e.</i>, it returns <code>new
     * ImageReadParam()</code>.
     *
     * <p>
     *  返回与此格式相对应的默认<code> ImageReadParam </code>对象。所有子类都应为所有参数定义一组默认值,并通过此调用返回它们。可以在设置输入源之前调用此方法。
     * 
     * <p>默认实现构造并返回一个不允许源缩放的新的<code> ImageReadParam </code>对象(<i> ie </i>,它返回<code> new ImageReadParam()</code>
     * 。
     * 
     * 
     * @return an <code>ImageReadParam</code> object which may be used
     * to control the decoding process using a set of default settings.
     */
    public ImageReadParam getDefaultReadParam() {
        return new ImageReadParam();
    }

    /**
     * Returns an <code>IIOMetadata</code> object representing the
     * metadata associated with the input source as a whole (i.e., not
     * associated with any particular image), or <code>null</code> if
     * the reader does not support reading metadata, is set to ignore
     * metadata, or if no metadata is available.
     *
     * <p>
     *  返回代表与整个输入源相关联的元数据(即,不与任何特定图像相关联)的<code> IIOMetadata </code>对象,或者如果读取器不支持读取元数据,设置为忽略元数据,或者如果没有元数据可用。
     * 
     * 
     * @return an <code>IIOMetadata</code> object, or <code>null</code>.
     *
     * @exception IOException if an error occurs during reading.
     */
    public abstract IIOMetadata getStreamMetadata() throws IOException;

    /**
     * Returns an <code>IIOMetadata</code> object representing the
     * metadata associated with the input source as a whole (i.e.,
     * not associated with any particular image).  If no such data
     * exists, <code>null</code> is returned.
     *
     * <p> The resulting metadata object is only responsible for
     * returning documents in the format named by
     * <code>formatName</code>.  Within any documents that are
     * returned, only nodes whose names are members of
     * <code>nodeNames</code> are required to be returned.  In this
     * way, the amount of metadata processing done by the reader may
     * be kept to a minimum, based on what information is actually
     * needed.
     *
     * <p> If <code>formatName</code> is not the name of a supported
     * metadata format, <code>null</code> is returned.
     *
     * <p> In all cases, it is legal to return a more capable metadata
     * object than strictly necessary.  The format name and node names
     * are merely hints that may be used to reduce the reader's
     * workload.
     *
     * <p> The default implementation simply returns the result of
     * calling <code>getStreamMetadata()</code>, after checking that
     * the format name is supported.  If it is not,
     * <code>null</code> is returned.
     *
     * <p>
     *  返回表示与输入源整体相关联的元数据(即,不与任何特定图像相关联)的<code> IIOMetadata </code>对象。如果不存在这样的数据,则返回<code> null </code>。
     * 
     *  <p>生成的元数据对象只负责返回以<code> formatName </code>命名的格式的文档。在返回的任何文档中,只需要返回名称为<code> nodeNames </code>成员的节点。
     * 以这种方式,基于实际需要什么信息,读取器完成的元数据处理的量可以保持最小。
     * 
     *  <p>如果<code> formatName </code>不是受支持的元数据格式的名称,则会返回<code> null </code>。
     * 
     *  <p>在所有情况下,返回一个比严格必要的更有能力的元数据对象是合法的。格式名称和节点名称仅仅是可以用于减少读者工作量的提示。
     * 
     * <p>在检查格式名是否被支持后,默认实现只返回调用<code> getStreamMetadata()</code>的结果。如果不是,返回<code> null </code>。
     * 
     * 
     * @param formatName a metadata format name that may be used to retrieve
     * a document from the returned <code>IIOMetadata</code> object.
     * @param nodeNames a <code>Set</code> containing the names of
     * nodes that may be contained in a retrieved document.
     *
     * @return an <code>IIOMetadata</code> object, or <code>null</code>.
     *
     * @exception IllegalArgumentException if <code>formatName</code>
     * is <code>null</code>.
     * @exception IllegalArgumentException if <code>nodeNames</code>
     * is <code>null</code>.
     * @exception IOException if an error occurs during reading.
     */
    public IIOMetadata getStreamMetadata(String formatName,
                                         Set<String> nodeNames)
        throws IOException
    {
        return getMetadata(formatName, nodeNames, true, 0);
    }

    private IIOMetadata getMetadata(String formatName,
                                    Set nodeNames,
                                    boolean wantStream,
                                    int imageIndex) throws IOException {
        if (formatName == null) {
            throw new IllegalArgumentException("formatName == null!");
        }
        if (nodeNames == null) {
            throw new IllegalArgumentException("nodeNames == null!");
        }
        IIOMetadata metadata =
            wantStream
            ? getStreamMetadata()
            : getImageMetadata(imageIndex);
        if (metadata != null) {
            if (metadata.isStandardMetadataFormatSupported() &&
                formatName.equals
                (IIOMetadataFormatImpl.standardMetadataFormatName)) {
                return metadata;
            }
            String nativeName = metadata.getNativeMetadataFormatName();
            if (nativeName != null && formatName.equals(nativeName)) {
                return metadata;
            }
            String[] extraNames = metadata.getExtraMetadataFormatNames();
            if (extraNames != null) {
                for (int i = 0; i < extraNames.length; i++) {
                    if (formatName.equals(extraNames[i])) {
                        return metadata;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns an <code>IIOMetadata</code> object containing metadata
     * associated with the given image, or <code>null</code> if the
     * reader does not support reading metadata, is set to ignore
     * metadata, or if no metadata is available.
     *
     * <p>
     *  返回包含与给定图像相关联的元数据的<code> IIOMetadata </code>对象,如果读取器不支持读取元数据,设置为忽略元数据,或者没有元数据可用,则返回<code> null </code>
     * 。
     * 
     * 
     * @param imageIndex the index of the image whose metadata is to
     * be retrieved.
     *
     * @return an <code>IIOMetadata</code> object, or
     * <code>null</code>.
     *
     * @exception IllegalStateException if the input source has not been
     * set.
     * @exception IndexOutOfBoundsException if the supplied index is
     * out of bounds.
     * @exception IOException if an error occurs during reading.
     */
    public abstract IIOMetadata getImageMetadata(int imageIndex)
        throws IOException;

    /**
     * Returns an <code>IIOMetadata</code> object representing the
     * metadata associated with the given image, or <code>null</code>
     * if the reader does not support reading metadata or none
     * is available.
     *
     * <p> The resulting metadata object is only responsible for
     * returning documents in the format named by
     * <code>formatName</code>.  Within any documents that are
     * returned, only nodes whose names are members of
     * <code>nodeNames</code> are required to be returned.  In this
     * way, the amount of metadata processing done by the reader may
     * be kept to a minimum, based on what information is actually
     * needed.
     *
     * <p> If <code>formatName</code> is not the name of a supported
     * metadata format, <code>null</code> may be returned.
     *
     * <p> In all cases, it is legal to return a more capable metadata
     * object than strictly necessary.  The format name and node names
     * are merely hints that may be used to reduce the reader's
     * workload.
     *
     * <p> The default implementation simply returns the result of
     * calling <code>getImageMetadata(imageIndex)</code>, after
     * checking that the format name is supported.  If it is not,
     * <code>null</code> is returned.
     *
     * <p>
     *  如果读取器不支持读取元数据或没有可用的元素,则返回表示与给定图像相关联的元数据的<code> IIOMetadata </code>对象,或<code> null </code>。
     * 
     *  <p>生成的元数据对象只负责返回以<code> formatName </code>命名的格式的文档。在返回的任何文档中,只需要返回名称为<code> nodeNames </code>成员的节点。
     * 以这种方式,基于实际需要什么信息,读取器完成的元数据处理的量可以保持最小。
     * 
     *  <p>如果<code> formatName </code>不是受支持的元数据格式的名称,则可能会返回<code> null </code>。
     * 
     *  <p>在所有情况下,返回一个比严格必要的更有能力的元数据对象是合法的。格式名称和节点名称仅仅是可以用于减少读者工作量的提示。
     * 
     * <p>默认实现在检查格式名是否支持后,简单地返回调用<code> getImageMetadata(imageIndex)</code>的结果。如果不是,返回<code> null </code>。
     * 
     * 
     * @param imageIndex the index of the image whose metadata is to
     * be retrieved.
     * @param formatName a metadata format name that may be used to retrieve
     * a document from the returned <code>IIOMetadata</code> object.
     * @param nodeNames a <code>Set</code> containing the names of
     * nodes that may be contained in a retrieved document.
     *
     * @return an <code>IIOMetadata</code> object, or <code>null</code>.
     *
     * @exception IllegalStateException if the input source has not been
     * set.
     * @exception IndexOutOfBoundsException if the supplied index is
     * out of bounds.
     * @exception IllegalArgumentException if <code>formatName</code>
     * is <code>null</code>.
     * @exception IllegalArgumentException if <code>nodeNames</code>
     * is <code>null</code>.
     * @exception IOException if an error occurs during reading.
     */
    public IIOMetadata getImageMetadata(int imageIndex,
                                        String formatName,
                                        Set<String> nodeNames)
        throws IOException {
        return getMetadata(formatName, nodeNames, false, imageIndex);
    }

    /**
     * Reads the image indexed by <code>imageIndex</code> and returns
     * it as a complete <code>BufferedImage</code>, using a default
     * <code>ImageReadParam</code>.  This is a convenience method
     * that calls <code>read(imageIndex, null)</code>.
     *
     * <p> The image returned will be formatted according to the first
     * <code>ImageTypeSpecifier</code> returned from
     * <code>getImageTypes</code>.
     *
     * <p> Any registered <code>IIOReadProgressListener</code> objects
     * will be notified by calling their <code>imageStarted</code>
     * method, followed by calls to their <code>imageProgress</code>
     * method as the read progresses.  Finally their
     * <code>imageComplete</code> method will be called.
     * <code>IIOReadUpdateListener</code> objects may be updated at
     * other times during the read as pixels are decoded.  Finally,
     * <code>IIOReadWarningListener</code> objects will receive
     * notification of any non-fatal warnings that occur during
     * decoding.
     *
     * <p>
     *  读取由<code> imageIndex </code>索引的图像,并使用默认的<code> ImageReadParam </code>将其作为完整的<code> BufferedImage </code>
     * 这是一个方便的方法,调用<code> read(imageIndex,null)</code>。
     * 
     *  <p>返回的图片将根据<code> getImageTypes </code>返回的第一个<code> ImageTypeSpecifier </code>进行格式化。
     * 
     *  <p>任何注册的<code> IIOReadProgressListener </code>对象将通过调用他们的<code> imageStarted </code>方法通知,随后随着读取的进行调用其
     * <code> imageProgress </code>方法。
     * 最后,他们的<code> imageComplete </code>方法将被调用。
     * 可以在读取期间的其它时间在像素被解码时更新<code> IIOReadUpdateListener </code>对象。
     * 最后,<code> IIOReadWarningListener </code>对象将接收到在解码期间发生的任何非致命警告的通知。
     * 
     * 
     * @param imageIndex the index of the image to be retrieved.
     *
     * @return the desired portion of the image as a
     * <code>BufferedImage</code>.
     *
     * @exception IllegalStateException if the input source has not been
     * set.
     * @exception IndexOutOfBoundsException if the supplied index is
     * out of bounds.
     * @exception IOException if an error occurs during reading.
     */
    public BufferedImage read(int imageIndex) throws IOException {
        return read(imageIndex, null);
    }

    /**
     * Reads the image indexed by <code>imageIndex</code> and returns
     * it as a complete <code>BufferedImage</code>, using a supplied
     * <code>ImageReadParam</code>.
     *
     * <p> The actual <code>BufferedImage</code> returned will be
     * chosen using the algorithm defined by the
     * <code>getDestination</code> method.
     *
     * <p> Any registered <code>IIOReadProgressListener</code> objects
     * will be notified by calling their <code>imageStarted</code>
     * method, followed by calls to their <code>imageProgress</code>
     * method as the read progresses.  Finally their
     * <code>imageComplete</code> method will be called.
     * <code>IIOReadUpdateListener</code> objects may be updated at
     * other times during the read as pixels are decoded.  Finally,
     * <code>IIOReadWarningListener</code> objects will receive
     * notification of any non-fatal warnings that occur during
     * decoding.
     *
     * <p> The set of source bands to be read and destination bands to
     * be written is determined by calling <code>getSourceBands</code>
     * and <code>getDestinationBands</code> on the supplied
     * <code>ImageReadParam</code>.  If the lengths of the arrays
     * returned by these methods differ, the set of source bands
     * contains an index larger that the largest available source
     * index, or the set of destination bands contains an index larger
     * than the largest legal destination index, an
     * <code>IllegalArgumentException</code> is thrown.
     *
     * <p> If the supplied <code>ImageReadParam</code> contains
     * optional setting values not supported by this reader (<i>e.g.</i>
     * source render size or any format-specific settings), they will
     * be ignored.
     *
     * <p>
     *  读取由<code> imageIndex </code>索引的图片,并使用提供的<code> ImageReadParam </code>将其作为完整的<code> BufferedImage </code>
     * 。
     * 
     *  <p>返回的实际<code> BufferedImage </code>将使用<code> getDestination </code>方法定义的算法进行选择。
     * 
     * <p>任何注册的<code> IIOReadProgressListener </code>对象将通过调用他们的<code> imageStarted </code>方法通知,随后随着读取的进行调用其<code>
     *  imageProgress </code>方法。
     * 最后,他们的<code> imageComplete </code>方法将被调用。
     * 可以在读取期间的其它时间在像素被解码时更新<code> IIOReadUpdateListener </code>对象。
     * 最后,<code> IIOReadWarningListener </code>对象将接收到在解码期间发生的任何非致命警告的通知。
     * 
     *  <p>通过在提供的<code> ImageReadParam </code>上调用<code> getSourceBands </code>和<code> getDestinationBands </code>
     * ,确定要读取的源带集和要写入的目标带集。
     * 如果由这些方法返回的数组的长度不同,则源频带集合包含大于最大可用源索引的索引,或者目的频带集合包含大于最大合法目的地索引的索引,<code> IllegalArgumentException </code>
     * 。
     * 
     *  <p>如果提供的<code> ImageReadParam </code>包含此阅读器不支持的可选设置值(<i> </i>源渲染大小或任何特定于格式的设置),则会被忽略。
     * 
     * 
     * @param imageIndex the index of the image to be retrieved.
     * @param param an <code>ImageReadParam</code> used to control
     * the reading process, or <code>null</code>.
     *
     * @return the desired portion of the image as a
     * <code>BufferedImage</code>.
     *
     * @exception IllegalStateException if the input source has not been
     * set.
     * @exception IndexOutOfBoundsException if the supplied index is
     * out of bounds.
     * @exception IllegalArgumentException if the set of source and
     * destination bands specified by
     * <code>param.getSourceBands</code> and
     * <code>param.getDestinationBands</code> differ in length or
     * include indices that are out of bounds.
     * @exception IllegalArgumentException if the resulting image would
     * have a width or height less than 1.
     * @exception IOException if an error occurs during reading.
     */
    public abstract BufferedImage read(int imageIndex, ImageReadParam param)
        throws IOException;

    /**
     * Reads the image indexed by <code>imageIndex</code> and returns
     * an <code>IIOImage</code> containing the image, thumbnails, and
     * associated image metadata, using a supplied
     * <code>ImageReadParam</code>.
     *
     * <p> The actual <code>BufferedImage</code> referenced by the
     * returned <code>IIOImage</code> will be chosen using the
     * algorithm defined by the <code>getDestination</code> method.
     *
     * <p> Any registered <code>IIOReadProgressListener</code> objects
     * will be notified by calling their <code>imageStarted</code>
     * method, followed by calls to their <code>imageProgress</code>
     * method as the read progresses.  Finally their
     * <code>imageComplete</code> method will be called.
     * <code>IIOReadUpdateListener</code> objects may be updated at
     * other times during the read as pixels are decoded.  Finally,
     * <code>IIOReadWarningListener</code> objects will receive
     * notification of any non-fatal warnings that occur during
     * decoding.
     *
     * <p> The set of source bands to be read and destination bands to
     * be written is determined by calling <code>getSourceBands</code>
     * and <code>getDestinationBands</code> on the supplied
     * <code>ImageReadParam</code>.  If the lengths of the arrays
     * returned by these methods differ, the set of source bands
     * contains an index larger that the largest available source
     * index, or the set of destination bands contains an index larger
     * than the largest legal destination index, an
     * <code>IllegalArgumentException</code> is thrown.
     *
     * <p> Thumbnails will be returned in their entirety regardless of
     * the region settings.
     *
     * <p> If the supplied <code>ImageReadParam</code> contains
     * optional setting values not supported by this reader (<i>e.g.</i>
     * source render size or any format-specific settings), those
     * values will be ignored.
     *
     * <p>
     *  读取由<code> imageIndex </code>索引的图像,并使用提供的<code> ImageReadParam </code>返回包含图像,缩略图和关联图像元数据的<code> IIOIm
     * age </code>。
     * 
     * <p>返回的<code> IIOImage </code>引用的实际<code> BufferedImage </code>将使用<code> getDestination </code>方法定义的算法
     * 进行选择。
     * 
     *  <p>任何注册的<code> IIOReadProgressListener </code>对象将通过调用他们的<code> imageStarted </code>方法通知,随后随着读取的进行调用其
     * <code> imageProgress </code>方法。
     * 最后,他们的<code> imageComplete </code>方法将被调用。
     * 可以在读取期间的其它时间在像素被解码时更新<code> IIOReadUpdateListener </code>对象。
     * 最后,<code> IIOReadWarningListener </code>对象将接收到在解码期间发生的任何非致命警告的通知。
     * 
     *  <p>通过在提供的<code> ImageReadParam </code>上调用<code> getSourceBands </code>和<code> getDestinationBands </code>
     * ,确定要读取的源带集和要写入的目标带集。
     * 如果由这些方法返回的数组的长度不同,则源频带集合包含大于最大可用源索引的索引,或者目的频带集合包含大于最大合法目的地索引的索引,<code> IllegalArgumentException </code>
     * 。
     * 
     *  <p>无论区域设置如何,缩略图都将全部返回。
     * 
     * <p>如果提供的<code> ImageReadParam </code>包含此阅读器不支持的可选设置值(例如</i>源渲染大小或任何特定于格式的设置),那些值将被忽略。
     * 
     * 
     * @param imageIndex the index of the image to be retrieved.
     * @param param an <code>ImageReadParam</code> used to control
     * the reading process, or <code>null</code>.
     *
     * @return an <code>IIOImage</code> containing the desired portion
     * of the image, a set of thumbnails, and associated image
     * metadata.
     *
     * @exception IllegalStateException if the input source has not been
     * set.
     * @exception IndexOutOfBoundsException if the supplied index is
     * out of bounds.
     * @exception IllegalArgumentException if the set of source and
     * destination bands specified by
     * <code>param.getSourceBands</code> and
     * <code>param.getDestinationBands</code> differ in length or
     * include indices that are out of bounds.
     * @exception IllegalArgumentException if the resulting image
     * would have a width or height less than 1.
     * @exception IOException if an error occurs during reading.
     */
    public IIOImage readAll(int imageIndex, ImageReadParam param)
        throws IOException {
        if (imageIndex < getMinIndex()) {
            throw new IndexOutOfBoundsException("imageIndex < getMinIndex()!");
        }

        BufferedImage im = read(imageIndex, param);

        ArrayList thumbnails = null;
        int numThumbnails = getNumThumbnails(imageIndex);
        if (numThumbnails > 0) {
            thumbnails = new ArrayList();
            for (int j = 0; j < numThumbnails; j++) {
                thumbnails.add(readThumbnail(imageIndex, j));
            }
        }

        IIOMetadata metadata = getImageMetadata(imageIndex);
        return new IIOImage(im, thumbnails, metadata);
    }

    /**
     * Returns an <code>Iterator</code> containing all the images,
     * thumbnails, and metadata, starting at the index given by
     * <code>getMinIndex</code>, from the input source in the form of
     * <code>IIOImage</code> objects.  An <code>Iterator</code>
     * containing <code>ImageReadParam</code> objects is supplied; one
     * element is consumed for each image read from the input source
     * until no more images are available.  If the read param
     * <code>Iterator</code> runs out of elements, but there are still
     * more images available from the input source, default read
     * params are used for the remaining images.
     *
     * <p> If <code>params</code> is <code>null</code>, a default read
     * param will be used for all images.
     *
     * <p> The actual <code>BufferedImage</code> referenced by the
     * returned <code>IIOImage</code> will be chosen using the
     * algorithm defined by the <code>getDestination</code> method.
     *
     * <p> Any registered <code>IIOReadProgressListener</code> objects
     * will be notified by calling their <code>sequenceStarted</code>
     * method once.  Then, for each image decoded, there will be a
     * call to <code>imageStarted</code>, followed by calls to
     * <code>imageProgress</code> as the read progresses, and finally
     * to <code>imageComplete</code>.  The
     * <code>sequenceComplete</code> method will be called after the
     * last image has been decoded.
     * <code>IIOReadUpdateListener</code> objects may be updated at
     * other times during the read as pixels are decoded.  Finally,
     * <code>IIOReadWarningListener</code> objects will receive
     * notification of any non-fatal warnings that occur during
     * decoding.
     *
     * <p> The set of source bands to be read and destination bands to
     * be written is determined by calling <code>getSourceBands</code>
     * and <code>getDestinationBands</code> on the supplied
     * <code>ImageReadParam</code>.  If the lengths of the arrays
     * returned by these methods differ, the set of source bands
     * contains an index larger that the largest available source
     * index, or the set of destination bands contains an index larger
     * than the largest legal destination index, an
     * <code>IllegalArgumentException</code> is thrown.
     *
     * <p> Thumbnails will be returned in their entirety regardless of the
     * region settings.
     *
     * <p> If any of the supplied <code>ImageReadParam</code>s contain
     * optional setting values not supported by this reader (<i>e.g.</i>
     * source render size or any format-specific settings), they will
     * be ignored.
     *
     * <p>
     *  从<code> IIOImage </code>形式的输入源返回包含从<code> getMinIndex </code>给出的索引处开始的所有图像,缩略图和元数据的<code>迭代器</code> 
     * >对象。
     * 提供了包含<code> ImageReadParam </code>对象的<code>迭代器</code>;对于从输入源读取的每个图像消耗一个元素,直到没有更多图像可用。
     * 如果读取参数<code> Iterator </code>用尽元素,但输入源仍有更多图像可用,则对剩余图像使用默认读取参数。
     * 
     *  <p>如果<code> params </code>是<code> null </code>,则所有图片都将使用默认读取参数。
     * 
     *  <p>返回的<code> IIOImage </code>引用的实际<code> BufferedImage </code>将使用<code> getDestination </code>方法定义的算
     * 法进行选择。
     * 
     * <p>任何注册的<code> IIOReadProgressListener </code>对象将通过调用其<code> sequenceStarted </code>方法一次通知。
     * 然后,对于解码的每个图像,将调用<code> imageStarted </code>,随后调用<code> imageProgress </code>作为读取进度,最后到<code> imageCom
     * plete </code> 。
     * <p>任何注册的<code> IIOReadProgressListener </code>对象将通过调用其<code> sequenceStarted </code>方法一次通知。
     * 在最后一个图像被解码后,将调用<code> sequenceComplete </code>方法。
     * 可以在读取期间的其它时间在像素被解码时更新<code> IIOReadUpdateListener </code>对象。
     * 最后,<code> IIOReadWarningListener </code>对象将接收到在解码期间发生的任何非致命警告的通知。
     * 
     *  <p>通过在提供的<code> ImageReadParam </code>上调用<code> getSourceBands </code>和<code> getDestinationBands </code>
     * ,确定要读取的源带集和要写入的目标带集。
     * 如果由这些方法返回的数组的长度不同,则源频带集合包含大于最大可用源索引的索引,或者目的频带集合包含大于最大合法目的地索引的索引,<code> IllegalArgumentException </code>
     * 。
     * 
     *  <p>无论区域设置如何,缩略图都将全部返回。
     * 
     *  <p>如果任何提供的<code> ImageReadParam </code>包含此阅读器不支持的可选设置值(<i>例如</i>源渲染大小或任何特定于格式的设置),它们将被忽略。
     * 
     * 
     * @param params an <code>Iterator</code> containing
     * <code>ImageReadParam</code> objects.
     *
     * @return an <code>Iterator</code> representing the
     * contents of the input source as <code>IIOImage</code>s.
     *
     * @exception IllegalStateException if the input source has not been
     * set.
     * @exception IllegalArgumentException if any
     * non-<code>null</code> element of <code>params</code> is not an
     * <code>ImageReadParam</code>.
     * @exception IllegalArgumentException if the set of source and
     * destination bands specified by
     * <code>param.getSourceBands</code> and
     * <code>param.getDestinationBands</code> differ in length or
     * include indices that are out of bounds.
     * @exception IllegalArgumentException if a resulting image would
     * have a width or height less than 1.
     * @exception IOException if an error occurs during reading.
     *
     * @see ImageReadParam
     * @see IIOImage
     */
    public Iterator<IIOImage>
        readAll(Iterator<? extends ImageReadParam> params)
        throws IOException
    {
        List output = new ArrayList();

        int imageIndex = getMinIndex();

        // Inform IIOReadProgressListeners we're starting a sequence
        processSequenceStarted(imageIndex);

        while (true) {
            // Inform IIOReadProgressListeners and IIOReadUpdateListeners
            // that we're starting a new image

            ImageReadParam param = null;
            if (params != null && params.hasNext()) {
                Object o = params.next();
                if (o != null) {
                    if (o instanceof ImageReadParam) {
                        param = (ImageReadParam)o;
                    } else {
                        throw new IllegalArgumentException
                            ("Non-ImageReadParam supplied as part of params!");
                    }
                }
            }

            BufferedImage bi = null;
            try {
                bi = read(imageIndex, param);
            } catch (IndexOutOfBoundsException e) {
                break;
            }

            ArrayList thumbnails = null;
            int numThumbnails = getNumThumbnails(imageIndex);
            if (numThumbnails > 0) {
                thumbnails = new ArrayList();
                for (int j = 0; j < numThumbnails; j++) {
                    thumbnails.add(readThumbnail(imageIndex, j));
                }
            }

            IIOMetadata metadata = getImageMetadata(imageIndex);
            IIOImage im = new IIOImage(bi, thumbnails, metadata);
            output.add(im);

            ++imageIndex;
        }

        // Inform IIOReadProgressListeners we're ending a sequence
        processSequenceComplete();

        return output.iterator();
    }

    /**
     * Returns <code>true</code> if this plug-in supports reading
     * just a {@link java.awt.image.Raster Raster} of pixel data.
     * If this method returns <code>false</code>, calls to
     * {@link #readRaster readRaster} or {@link #readTileRaster readTileRaster}
     * will throw an <code>UnsupportedOperationException</code>.
     *
     * <p> The default implementation returns <code>false</code>.
     *
     * <p>
     * 如果此插件支持只读取{@link java.awt.image.Raster Raster}的像素数据,则返回<code> true </code>。
     * 如果此方法返回<code> false </code>,对{@link #readRaster readRaster}或{@link #readTileRaster readTileRaster}的调用
     * 将抛出一个<code> UnsupportedOperationException </code>。
     * 如果此插件支持只读取{@link java.awt.image.Raster Raster}的像素数据,则返回<code> true </code>。
     * 
     *  <p>默认实现返回<code> false </code>。
     * 
     * 
     * @return <code>true</code> if this plug-in supports reading raw
     * <code>Raster</code>s.
     *
     * @see #readRaster
     * @see #readTileRaster
     */
    public boolean canReadRaster() {
        return false;
    }

    /**
     * Returns a new <code>Raster</code> object containing the raw pixel data
     * from the image stream, without any color conversion applied.  The
     * application must determine how to interpret the pixel data by other
     * means.  Any destination or image-type parameters in the supplied
     * <code>ImageReadParam</code> object are ignored, but all other
     * parameters are used exactly as in the {@link #read read}
     * method, except that any destination offset is used as a logical rather
     * than a physical offset.  The size of the returned <code>Raster</code>
     * will always be that of the source region clipped to the actual image.
     * Logical offsets in the stream itself are ignored.
     *
     * <p> This method allows formats that normally apply a color
     * conversion, such as JPEG, and formats that do not normally have an
     * associated colorspace, such as remote sensing or medical imaging data,
     * to provide access to raw pixel data.
     *
     * <p> Any registered <code>readUpdateListener</code>s are ignored, as
     * there is no <code>BufferedImage</code>, but all other listeners are
     * called exactly as they are for the {@link #read read} method.
     *
     * <p> If {@link #canReadRaster canReadRaster()} returns
     * <code>false</code>, this method throws an
     * <code>UnsupportedOperationException</code>.
     *
     * <p> If the supplied <code>ImageReadParam</code> contains
     * optional setting values not supported by this reader (<i>e.g.</i>
     * source render size or any format-specific settings), they will
     * be ignored.
     *
     * <p> The default implementation throws an
     * <code>UnsupportedOperationException</code>.
     *
     * <p>
     *  返回一个包含图像流中原始像素数据的新<code> Raster </code>对象,不应用任何颜色转换。应用程序必须确定如何通过其他方式解释像素数据。
     * 将忽略所提供的<code> ImageReadParam </code>对象中的任何目标或图像类型参数,但所有其他参数的使用与{@link #read read}方法完全相同,除了任何目标偏移量用作逻辑
     * 而不是物理偏移。
     *  返回一个包含图像流中原始像素数据的新<code> Raster </code>对象,不应用任何颜色转换。应用程序必须确定如何通过其他方式解释像素数据。
     * 返回的<code> Raster </code>的大小将始终是剪切到实际图像的源区域的大小。流中的逻辑偏移本身被忽略。
     * 
     *  <p>该方法允许通常应用颜色转换的格式(例如JPEG)和通常不具有相关联的颜色空间的格式(例如遥感或医学成像数据),以提供对原始像素数据的访问。
     * 
     *  <p>任何注册的<code> readUpdateListener </code>都会被忽略,因为没有<code> BufferedImage </code>,但所有其他侦听器的调用方式与{@link #read read}
     *  。
     * 
     * <p>如果{@link #canReadRaster canReadRaster()}返回<code> false </code>,此方法会抛出一个<code> UnsupportedOperation
     * Exception </code>。
     * 
     *  <p>如果提供的<code> ImageReadParam </code>包含此阅读器不支持的可选设置值(<i> </i>源渲染大小或任何特定于格式的设置),则会被忽略。
     * 
     *  <p>默认实现会抛出<code> UnsupportedOperationException </code>。
     * 
     * 
     * @param imageIndex the index of the image to be read.
     * @param param an <code>ImageReadParam</code> used to control
     * the reading process, or <code>null</code>.
     *
     * @return the desired portion of the image as a
     * <code>Raster</code>.
     *
     * @exception UnsupportedOperationException if this plug-in does not
     * support reading raw <code>Raster</code>s.
     * @exception IllegalStateException if the input source has not been
     * set.
     * @exception IndexOutOfBoundsException if the supplied index is
     * out of bounds.
     * @exception IOException if an error occurs during reading.
     *
     * @see #canReadRaster
     * @see #read
     * @see java.awt.image.Raster
     */
    public Raster readRaster(int imageIndex, ImageReadParam param)
        throws IOException {
        throw new UnsupportedOperationException("readRaster not supported!");
    }

    /**
     * Returns <code>true</code> if the image is organized into
     * <i>tiles</i>, that is, equal-sized non-overlapping rectangles.
     *
     * <p> A reader plug-in may choose whether or not to expose tiling
     * that is present in the image as it is stored.  It may even
     * choose to advertise tiling when none is explicitly present.  In
     * general, tiling should only be advertised if there is some
     * advantage (in speed or space) to accessing individual tiles.
     * Regardless of whether the reader advertises tiling, it must be
     * capable of reading an arbitrary rectangular region specified in
     * an <code>ImageReadParam</code>.
     *
     * <p> A reader for which all images are guaranteed to be tiled,
     * or are guaranteed not to be tiled, may return <code>true</code>
     * or <code>false</code> respectively without accessing any image
     * data.  In such cases, it is not necessary to throw an exception
     * even if no input source has been set or the image index is out
     * of bounds.
     *
     * <p> The default implementation just returns <code>false</code>.
     *
     * <p>
     *  如果图片组织为<i>图块</i>,即等于大小的非重叠矩形,则返回<code> true </code>。
     * 
     *  <p>读取器插件可以选择是否在存储图像时呈现其存在的平铺。当没有明确存在时,它甚至可以选择广告平铺。一般来说,只有在访问单个磁贴有一些优势(速度或空间)时,才应该广告平铺。
     * 无论读者是否广告平铺,它都必须能够读取在<code> ImageReadParam </code>中指定的任意矩形区域。
     * 
     *  <p>对于所有图像都保证为平铺或保证不平铺的读取器,可以分别返回<code> true </code>或<code> false </code>,而不访问任何图像数据。
     * 在这种情况下,即使没有设置输入源或图像索引超出边界,也不必抛出异常。
     * 
     *  <p>默认实现只返回<code> false </code>。
     * 
     * 
     * @param imageIndex the index of the image to be queried.
     *
     * @return <code>true</code> if the image is tiled.
     *
     * @exception IllegalStateException if an input source is required
     * to determine the return value, but none has been set.
     * @exception IndexOutOfBoundsException if an image must be
     * accessed to determine the return value, but the supplied index
     * is out of bounds.
     * @exception IOException if an error occurs during reading.
     */
    public boolean isImageTiled(int imageIndex) throws IOException {
        return false;
    }

    /**
     * Returns the width of a tile in the given image.
     *
     * <p> The default implementation simply returns
     * <code>getWidth(imageIndex)</code>, which is correct for
     * non-tiled images.  Readers that support tiling should override
     * this method.
     *
     * <p>
     *  返回给定图片中图块的宽度。
     * 
     * <p>默认实现只返回<code> getWidth(imageIndex)</code>,这对于非平铺图像是正确的。支持平铺的读者应该覆盖此方法。
     * 
     * 
     * @return the width of a tile.
     *
     * @param imageIndex the index of the image to be queried.
     *
     * @exception IllegalStateException if the input source has not been set.
     * @exception IndexOutOfBoundsException if the supplied index is
     * out of bounds.
     * @exception IOException if an error occurs during reading.
     */
    public int getTileWidth(int imageIndex) throws IOException {
        return getWidth(imageIndex);
    }

    /**
     * Returns the height of a tile in the given image.
     *
     * <p> The default implementation simply returns
     * <code>getHeight(imageIndex)</code>, which is correct for
     * non-tiled images.  Readers that support tiling should override
     * this method.
     *
     * <p>
     *  返回指定图片中图块的高度。
     * 
     *  <p>默认实现只返回<code> getHeight(imageIndex)</code>,这对于非平铺图像是正确的。支持平铺的读者应该覆盖此方法。
     * 
     * 
     * @return the height of a tile.
     *
     * @param imageIndex the index of the image to be queried.
     *
     * @exception IllegalStateException if the input source has not been set.
     * @exception IndexOutOfBoundsException if the supplied index is
     * out of bounds.
     * @exception IOException if an error occurs during reading.
     */
    public int getTileHeight(int imageIndex) throws IOException {
        return getHeight(imageIndex);
    }

    /**
     * Returns the X coordinate of the upper-left corner of tile (0,
     * 0) in the given image.
     *
     * <p> A reader for which the tile grid X offset always has the
     * same value (usually 0), may return the value without accessing
     * any image data.  In such cases, it is not necessary to throw an
     * exception even if no input source has been set or the image
     * index is out of bounds.
     *
     * <p> The default implementation simply returns 0, which is
     * correct for non-tiled images and tiled images in most formats.
     * Readers that support tiling with non-(0, 0) offsets should
     * override this method.
     *
     * <p>
     *  返回给定图像中图块(0,0)左上角的X坐标。
     * 
     *  <p>一个读取器,其瓦片网格X偏移总是具有相同的值(通常为0),可以返回该值,而不访问任何图像数据。在这种情况下,即使没有设置输入源或图像索引超出边界,也不必抛出异常。
     * 
     *  <p>默认实现只返回0,这对于非平铺图像和大多数格式的平铺图像都是正确的。支持使用非(0,0)偏移进行平铺的读取器应覆盖此方法。
     * 
     * 
     * @return the X offset of the tile grid.
     *
     * @param imageIndex the index of the image to be queried.
     *
     * @exception IllegalStateException if an input source is required
     * to determine the return value, but none has been set.
     * @exception IndexOutOfBoundsException if an image must be
     * accessed to determine the return value, but the supplied index
     * is out of bounds.
     * @exception IOException if an error occurs during reading.
     */
    public int getTileGridXOffset(int imageIndex) throws IOException {
        return 0;
    }

    /**
     * Returns the Y coordinate of the upper-left corner of tile (0,
     * 0) in the given image.
     *
     * <p> A reader for which the tile grid Y offset always has the
     * same value (usually 0), may return the value without accessing
     * any image data.  In such cases, it is not necessary to throw an
     * exception even if no input source has been set or the image
     * index is out of bounds.
     *
     * <p> The default implementation simply returns 0, which is
     * correct for non-tiled images and tiled images in most formats.
     * Readers that support tiling with non-(0, 0) offsets should
     * override this method.
     *
     * <p>
     *  返回给定图像中图块(0,0)左上角的Y坐标。
     * 
     *  <p>读取器的瓦片网格Y偏移总是具有相同的值(通常为0),可以返回该值,而不访问任何图像数据。在这种情况下,即使没有设置输入源或图像索引超出边界,也不必抛出异常。
     * 
     * <p>默认实现只返回0,这对于非平铺图像和大多数格式的平铺图像都是正确的。支持使用非(0,0)偏移进行平铺的读取器应覆盖此方法。
     * 
     * 
     * @return the Y offset of the tile grid.
     *
     * @param imageIndex the index of the image to be queried.
     *
     * @exception IllegalStateException if an input source is required
     * to determine the return value, but none has been set.
     * @exception IndexOutOfBoundsException if an image must be
     * accessed to determine the return value, but the supplied index
     * is out of bounds.
     * @exception IOException if an error occurs during reading.
     */
    public int getTileGridYOffset(int imageIndex) throws IOException {
        return 0;
    }

    /**
     * Reads the tile indicated by the <code>tileX</code> and
     * <code>tileY</code> arguments, returning it as a
     * <code>BufferedImage</code>.  If the arguments are out of range,
     * an <code>IllegalArgumentException</code> is thrown.  If the
     * image is not tiled, the values 0, 0 will return the entire
     * image; any other values will cause an
     * <code>IllegalArgumentException</code> to be thrown.
     *
     * <p> This method is merely a convenience equivalent to calling
     * <code>read(int, ImageReadParam)</code> with a read param
     * specifying a source region having offsets of
     * <code>tileX*getTileWidth(imageIndex)</code>,
     * <code>tileY*getTileHeight(imageIndex)</code> and width and
     * height of <code>getTileWidth(imageIndex)</code>,
     * <code>getTileHeight(imageIndex)</code>; and subsampling
     * factors of 1 and offsets of 0.  To subsample a tile, call
     * <code>read</code> with a read param specifying this region
     * and different subsampling parameters.
     *
     * <p> The default implementation returns the entire image if
     * <code>tileX</code> and <code>tileY</code> are 0, or throws
     * an <code>IllegalArgumentException</code> otherwise.
     *
     * <p>
     *  读取由<code> tileX </code>和<code> tileY </code>参数指示的图块,并将其作为<code> BufferedImage </code>返回。
     * 如果参数超出范围,则抛出<code> IllegalArgumentException </code>。
     * 如果图像不平铺,值0,0将返回整个图像;任何其他值将导致<code> IllegalArgumentException </code>被抛出。
     * 
     *  <p>此方法仅仅是等效于使用读取参数调用<code> read(int,ImageReadParam)</code>的指定具有偏移量<code> tileX * getTileWidth(imageI
     * ndex)</code> <code> tileY * getTileHeight(imageIndex)</code>以及<code> getTileWidth(imageIndex)</code>,
     * <code> getTileHeight(imageIndex)</code>的宽度和高度;并且子采样因子为1和偏移量为0.要对tile进行子采样,请使用指定此区域和不同子采样参数的读取参数调用<code>
     *  read </code>。
     * 
     *  <p>如果<code> tileX </code>和<code> tileY </code>为0,则默认实现返回整个图像,否则抛出<code> IllegalArgumentException </code>
     * 。
     * 
     * 
     * @param imageIndex the index of the image to be retrieved.
     * @param tileX the column index (starting with 0) of the tile
     * to be retrieved.
     * @param tileY the row index (starting with 0) of the tile
     * to be retrieved.
     *
     * @return the tile as a <code>BufferedImage</code>.
     *
     * @exception IllegalStateException if the input source has not been
     * set.
     * @exception IndexOutOfBoundsException if <code>imageIndex</code>
     * is out of bounds.
     * @exception IllegalArgumentException if the tile indices are
     * out of bounds.
     * @exception IOException if an error occurs during reading.
     */
    public BufferedImage readTile(int imageIndex,
                                  int tileX, int tileY) throws IOException {
        if ((tileX != 0) || (tileY != 0)) {
            throw new IllegalArgumentException("Invalid tile indices");
        }
        return read(imageIndex);
    }

    /**
     * Returns a new <code>Raster</code> object containing the raw
     * pixel data from the tile, without any color conversion applied.
     * The application must determine how to interpret the pixel data by other
     * means.
     *
     * <p> If {@link #canReadRaster canReadRaster()} returns
     * <code>false</code>, this method throws an
     * <code>UnsupportedOperationException</code>.
     *
     * <p> The default implementation checks if reading
     * <code>Raster</code>s is supported, and if so calls {@link
     * #readRaster readRaster(imageIndex, null)} if
     * <code>tileX</code> and <code>tileY</code> are 0, or throws an
     * <code>IllegalArgumentException</code> otherwise.
     *
     * <p>
     *  返回一个新的<code> Raster </code>对象,其中包含来自图块的原始像素数据,未应用任何颜色转换。应用程序必须确定如何通过其他方式解释像素数据。
     * 
     * <p>如果{@link #canReadRaster canReadRaster()}返回<code> false </code>,此方法会抛出一个<code> UnsupportedOperation
     * Exception </code>。
     * 
     *  <p>默认实现检查是否支持读取<code> Raster </code>,如果支持,则调用{@link #readRaster readRaster(imageIndex,null)} if <code>
     *  tileX </code> tileY </code>为0,否则抛出<code> IllegalArgumentException </code>。
     * 
     * 
     * @param imageIndex the index of the image to be retrieved.
     * @param tileX the column index (starting with 0) of the tile
     * to be retrieved.
     * @param tileY the row index (starting with 0) of the tile
     * to be retrieved.
     *
     * @return the tile as a <code>Raster</code>.
     *
     * @exception UnsupportedOperationException if this plug-in does not
     * support reading raw <code>Raster</code>s.
     * @exception IllegalArgumentException if the tile indices are
     * out of bounds.
     * @exception IllegalStateException if the input source has not been
     * set.
     * @exception IndexOutOfBoundsException if <code>imageIndex</code>
     * is out of bounds.
     * @exception IOException if an error occurs during reading.
     *
     * @see #readTile
     * @see #readRaster
     * @see java.awt.image.Raster
     */
    public Raster readTileRaster(int imageIndex,
                                 int tileX, int tileY) throws IOException {
        if (!canReadRaster()) {
            throw new UnsupportedOperationException
                ("readTileRaster not supported!");
        }
        if ((tileX != 0) || (tileY != 0)) {
            throw new IllegalArgumentException("Invalid tile indices");
        }
        return readRaster(imageIndex, null);
    }

    // RenderedImages

    /**
     * Returns a <code>RenderedImage</code> object that contains the
     * contents of the image indexed by <code>imageIndex</code>.  By
     * default, the returned image is simply the
     * <code>BufferedImage</code> returned by <code>read(imageIndex,
     * param)</code>.
     *
     * <p> The semantics of this method may differ from those of the
     * other <code>read</code> methods in several ways.  First, any
     * destination image and/or image type set in the
     * <code>ImageReadParam</code> may be ignored.  Second, the usual
     * listener calls are not guaranteed to be made, or to be
     * meaningful if they are.  This is because the returned image may
     * not be fully populated with pixel data at the time it is
     * returned, or indeed at any time.
     *
     * <p> If the supplied <code>ImageReadParam</code> contains
     * optional setting values not supported by this reader (<i>e.g.</i>
     * source render size or any format-specific settings), they will
     * be ignored.
     *
     * <p> The default implementation just calls
     * {@link #read read(imageIndex, param)}.
     *
     * <p>
     *  返回一个<code> RenderedImage </code>对象,其中包含由<code> imageIndex </code>索引的图片内容。
     * 默认情况下,返回的图像只是由<code> read(imageIndex,param)</code>返回的<code> BufferedImage </code>。
     * 
     *  <p>此方法的语义可能与其他<code> read </code>方法的语义在几个方面不同。
     * 首先,可以忽略在<code> ImageReadParam </code>中设置的任何目标图像和/或图像类型。第二,通常的监听器调用不能保证做出,或者如果它们是有意义的。
     * 这是因为返回的图像在返回时或者实际上在任何时候可能未被像素数据完全填充。
     * 
     *  <p>如果提供的<code> ImageReadParam </code>包含此阅读器不支持的可选设置值(<i> </i>源渲染大小或任何特定于格式的设置),则会被忽略。
     * 
     *  <p>默认实现仅调用{@link #read read(imageIndex,param)}。
     * 
     * 
     * @param imageIndex the index of the image to be retrieved.
     * @param param an <code>ImageReadParam</code> used to control
     * the reading process, or <code>null</code>.
     *
     * @return a <code>RenderedImage</code> object providing a view of
     * the image.
     *
     * @exception IllegalStateException if the input source has not been
     * set.
     * @exception IndexOutOfBoundsException if the supplied index is
     * out of bounds.
     * @exception IllegalArgumentException if the set of source and
     * destination bands specified by
     * <code>param.getSourceBands</code> and
     * <code>param.getDestinationBands</code> differ in length or
     * include indices that are out of bounds.
     * @exception IllegalArgumentException if the resulting image
     * would have a width or height less than 1.
     * @exception IOException if an error occurs during reading.
     */
    public RenderedImage readAsRenderedImage(int imageIndex,
                                             ImageReadParam param)
        throws IOException {
        return read(imageIndex, param);
    }

    // Thumbnails

    /**
     * Returns <code>true</code> if the image format understood by
     * this reader supports thumbnail preview images associated with
     * it.  The default implementation returns <code>false</code>.
     *
     * <p> If this method returns <code>false</code>,
     * <code>hasThumbnails</code> and <code>getNumThumbnails</code>
     * will return <code>false</code> and <code>0</code>,
     * respectively, and <code>readThumbnail</code> will throw an
     * <code>UnsupportedOperationException</code>, regardless of their
     * arguments.
     *
     * <p> A reader that does not support thumbnails need not
     * implement any of the thumbnail-related methods.
     *
     * <p>
     * 如果此读者理解的图像格式支持与其关联的缩略图预览图像,则返回<code> true </code>。默认实现返回<code> false </code>。
     * 
     *  <p>如果此方法返回<code> false </code>,则<code> hasThumbnails </code>和<code> getNumThumbnails </code>将返回<code>
     *  false </code> code>,并且<code> readThumbnail </code>会抛出一个<code> UnsupportedOperationException </code>,
     * 无论它们的参数如何。
     * 
     *  <p>不支持缩略图的阅读器不需要实现任何缩略图相关方法。
     * 
     * 
     * @return <code>true</code> if thumbnails are supported.
     */
    public boolean readerSupportsThumbnails() {
        return false;
    }

    /**
     * Returns <code>true</code> if the given image has thumbnail
     * preview images associated with it.  If the format does not
     * support thumbnails (<code>readerSupportsThumbnails</code>
     * returns <code>false</code>), <code>false</code> will be
     * returned regardless of whether an input source has been set or
     * whether <code>imageIndex</code> is in bounds.
     *
     * <p> The default implementation returns <code>true</code> if
     * <code>getNumThumbnails</code> returns a value greater than 0.
     *
     * <p>
     *  如果给定图像具有与其关联的缩略图预览图像,则返回<code> true </code>。
     * 如果格式不支持缩略图(<code> readerSupportsThumbnails </code>返回<code> false </code>),则将返回<code> false </code>,无论
     * 是否设置了输入源,代码> imageIndex </code>在边界内。
     *  如果给定图像具有与其关联的缩略图预览图像,则返回<code> true </code>。
     * 
     *  <p>如果<code> getNumThumbnails </code>返回大于0的值,则默认实现将返回<code> true </code>。
     * 
     * 
     * @param imageIndex the index of the image being queried.
     *
     * @return <code>true</code> if the given image has thumbnails.
     *
     * @exception IllegalStateException if the reader supports
     * thumbnails but the input source has not been set.
     * @exception IndexOutOfBoundsException if the reader supports
     * thumbnails but <code>imageIndex</code> is out of bounds.
     * @exception IOException if an error occurs during reading.
     */
    public boolean hasThumbnails(int imageIndex) throws IOException {
        return getNumThumbnails(imageIndex) > 0;
    }

    /**
     * Returns the number of thumbnail preview images associated with
     * the given image.  If the format does not support thumbnails,
     * (<code>readerSupportsThumbnails</code> returns
     * <code>false</code>), <code>0</code> will be returned regardless
     * of whether an input source has been set or whether
     * <code>imageIndex</code> is in bounds.
     *
     * <p> The default implementation returns 0 without checking its
     * argument.
     *
     * <p>
     *  返回与给定图像相关联的缩略图预览图像的数量。
     * 如果格式不支持缩略图(<code> readerSupportsThumbnails </code>返回<code> false </code>),则将返回<code> 0 </code>,无论是否设置
     * 了输入源, <code> imageIndex </code>在边界内。
     *  返回与给定图像相关联的缩略图预览图像的数量。
     * 
     *  <p>默认实现返回0,而不检查其参数。
     * 
     * 
     * @param imageIndex the index of the image being queried.
     *
     * @return the number of thumbnails associated with the given
     * image.
     *
     * @exception IllegalStateException if the reader supports
     * thumbnails but the input source has not been set.
     * @exception IndexOutOfBoundsException if the reader supports
     * thumbnails but <code>imageIndex</code> is out of bounds.
     * @exception IOException if an error occurs during reading.
     */
    public int getNumThumbnails(int imageIndex)
        throws IOException {
        return 0;
    }

    /**
     * Returns the width of the thumbnail preview image indexed by
     * <code>thumbnailIndex</code>, associated with the image indexed
     * by <code>ImageIndex</code>.
     *
     * <p> If the reader does not support thumbnails,
     * (<code>readerSupportsThumbnails</code> returns
     * <code>false</code>), an <code>UnsupportedOperationException</code>
     * will be thrown.
     *
     * <p> The default implementation simply returns
     * <code>readThumbnail(imageindex,
     * thumbnailIndex).getWidth()</code>.  Subclasses should therefore
     * override this method if possible in order to avoid forcing the
     * thumbnail to be read.
     *
     * <p>
     * 返回由<code> thumbnailIndex </code>编索引的缩略图预览图像的宽度,与由<code> ImageIndex </code>索引的图片相关联。
     * 
     *  <p>如果读者不支持缩略图(<code> readerSupportsThumbnails </code>返回<code> false </code>),则会抛出<code> UnsupportedO
     * perationException </code>。
     * 
     *  <p>默认实现只返回<code> readThumbnail(imageindex,thumbnailIndex).getWidth()</code>。
     * 因此,如果可能,子类应该覆盖此方法,以避免强制缩略图被读取。
     * 
     * 
     * @param imageIndex the index of the image to be retrieved.
     * @param thumbnailIndex the index of the thumbnail to be retrieved.
     *
     * @return the width of the desired thumbnail as an <code>int</code>.
     *
     * @exception UnsupportedOperationException if thumbnails are not
     * supported.
     * @exception IllegalStateException if the input source has not been set.
     * @exception IndexOutOfBoundsException if either of the supplied
     * indices are out of bounds.
     * @exception IOException if an error occurs during reading.
     */
    public int getThumbnailWidth(int imageIndex, int thumbnailIndex)
        throws IOException {
        return readThumbnail(imageIndex, thumbnailIndex).getWidth();
    }

    /**
     * Returns the height of the thumbnail preview image indexed by
     * <code>thumbnailIndex</code>, associated with the image indexed
     * by <code>ImageIndex</code>.
     *
     * <p> If the reader does not support thumbnails,
     * (<code>readerSupportsThumbnails</code> returns
     * <code>false</code>), an <code>UnsupportedOperationException</code>
     * will be thrown.
     *
     * <p> The default implementation simply returns
     * <code>readThumbnail(imageindex,
     * thumbnailIndex).getHeight()</code>.  Subclasses should
     * therefore override this method if possible in order to avoid
     * forcing the thumbnail to be read.
     *
     * <p>
     *  返回由<code> thumbnailIndex </code>编索引的缩略图预览图像的高度,该图像与由<code> ImageIndex </code>索引的图片相关联。
     * 
     *  <p>如果读者不支持缩略图(<code> readerSupportsThumbnails </code>返回<code> false </code>),则会抛出<code> UnsupportedO
     * perationException </code>。
     * 
     *  <p>默认实现只返回<code> readThumbnail(imageindex,thumbnailIndex).getHeight()</code>。
     * 因此,如果可能,子类应该覆盖此方法,以避免强制缩略图被读取。
     * 
     * 
     * @param imageIndex the index of the image to be retrieved.
     * @param thumbnailIndex the index of the thumbnail to be retrieved.
     *
     * @return the height of the desired thumbnail as an <code>int</code>.
     *
     * @exception UnsupportedOperationException if thumbnails are not
     * supported.
     * @exception IllegalStateException if the input source has not been set.
     * @exception IndexOutOfBoundsException if either of the supplied
     * indices are out of bounds.
     * @exception IOException if an error occurs during reading.
     */
    public int getThumbnailHeight(int imageIndex, int thumbnailIndex)
        throws IOException {
        return readThumbnail(imageIndex, thumbnailIndex).getHeight();
    }

    /**
     * Returns the thumbnail preview image indexed by
     * <code>thumbnailIndex</code>, associated with the image indexed
     * by <code>ImageIndex</code> as a <code>BufferedImage</code>.
     *
     * <p> Any registered <code>IIOReadProgressListener</code> objects
     * will be notified by calling their
     * <code>thumbnailStarted</code>, <code>thumbnailProgress</code>,
     * and <code>thumbnailComplete</code> methods.
     *
     * <p> If the reader does not support thumbnails,
     * (<code>readerSupportsThumbnails</code> returns
     * <code>false</code>), an <code>UnsupportedOperationException</code>
     * will be thrown regardless of whether an input source has been
     * set or whether the indices are in bounds.
     *
     * <p> The default implementation throws an
     * <code>UnsupportedOperationException</code>.
     *
     * <p>
     *  返回由<code> thumbnailIndex </code>索引的缩略图预览图像,该图像与由<code> ImageIndex </code>作为<code> BufferedImage </code>
     * 索引的图像相关联。
     * 
     *  <p>通过调用<code> thumbnailStarted </code>,<code> thumbnailProgress </code>和<code> thumbnailComplete </code>
     * 方法,通知任何注册的<code> IIOReadProgressListener </code>对象。
     * 
     * <p>如果读者不支持缩略图(<code> readerSupportsThumbnails </code>返回<code> false </code>),则会抛出<code> UnsupportedOp
     * erationException </code>,无论输入源是否已设置或索引是否在边界中。
     * 
     *  <p>默认实现会抛出<code> UnsupportedOperationException </code>。
     * 
     * 
     * @param imageIndex the index of the image to be retrieved.
     * @param thumbnailIndex the index of the thumbnail to be retrieved.
     *
     * @return the desired thumbnail as a <code>BufferedImage</code>.
     *
     * @exception UnsupportedOperationException if thumbnails are not
     * supported.
     * @exception IllegalStateException if the input source has not been set.
     * @exception IndexOutOfBoundsException if either of the supplied
     * indices are out of bounds.
     * @exception IOException if an error occurs during reading.
     */
    public BufferedImage readThumbnail(int imageIndex,
                                       int thumbnailIndex)
        throws IOException {
        throw new UnsupportedOperationException("Thumbnails not supported!");
    }

    // Abort

    /**
     * Requests that any current read operation be aborted.  The
     * contents of the image following the abort will be undefined.
     *
     * <p> Readers should call <code>clearAbortRequest</code> at the
     * beginning of each read operation, and poll the value of
     * <code>abortRequested</code> regularly during the read.
     * <p>
     *  请求中止任何当前读操作。中止后的映像内容将未定义。
     * 
     *  <p>读取器应在每次读取操作开始时调用<code> clearAbortRequest </code>,并在读取期间定期轮询<code> abortRequested </code>的值。
     * 
     */
    public synchronized void abort() {
        this.abortFlag = true;
    }

    /**
     * Returns <code>true</code> if a request to abort the current
     * read operation has been made since the reader was instantiated or
     * <code>clearAbortRequest</code> was called.
     *
     * <p>
     *  如果自从读取器被实例化或调用了<code> clearAbortRequest </code>后,如果已中止当前读取操作的请求,则返回<code> true </code>。
     * 
     * 
     * @return <code>true</code> if the current read operation should
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

    // Add an element to a list, creating a new list if the
    // existing list is null, and return the list.
    static List addToList(List l, Object elt) {
        if (l == null) {
            l = new ArrayList();
        }
        l.add(elt);
        return l;
    }


    // Remove an element from a list, discarding the list if the
    // resulting list is empty, and return the list or null.
    static List removeFromList(List l, Object elt) {
        if (l == null) {
            return l;
        }
        l.remove(elt);
        if (l.size() == 0) {
            l = null;
        }
        return l;
    }

    /**
     * Adds an <code>IIOReadWarningListener</code> to the list of
     * registered warning listeners.  If <code>listener</code> is
     * <code>null</code>, no exception will be thrown and no action
     * will be taken.  Messages sent to the given listener will be
     * localized, if possible, to match the current
     * <code>Locale</code>.  If no <code>Locale</code> has been set,
     * warning messages may be localized as the reader sees fit.
     *
     * <p>
     *  向注册的警告侦听器列表中添加<code> IIOReadWarningListener </code>。
     * 如果<code> listener </code>是<code> null </code>,则不会抛出任何异常,并且不会执行任何操作。
     * 如果可能,发送到给定侦听器的邮件将被本地化,以匹配当前的<code> Locale </code>。如果没有设置<code> Locale </code>,警告消息可能被本地化为读者认为合适。
     * 
     * 
     * @param listener an <code>IIOReadWarningListener</code> to be registered.
     *
     * @see #removeIIOReadWarningListener
     */
    public void addIIOReadWarningListener(IIOReadWarningListener listener) {
        if (listener == null) {
            return;
        }
        warningListeners = addToList(warningListeners, listener);
        warningLocales = addToList(warningLocales, getLocale());
    }

    /**
     * Removes an <code>IIOReadWarningListener</code> from the list of
     * registered error listeners.  If the listener was not previously
     * registered, or if <code>listener</code> is <code>null</code>,
     * no exception will be thrown and no action will be taken.
     *
     * <p>
     * 从注册的错误侦听器列表中删除<code> IIOReadWarningListener </code>。
     * 如果侦听器以前没有注册,或者<code> listener </code>是<code> null </code>,则不会抛出任何异常,不会执行任何操作。
     * 
     * 
     * @param listener an IIOReadWarningListener to be unregistered.
     *
     * @see #addIIOReadWarningListener
     */
    public void removeIIOReadWarningListener(IIOReadWarningListener listener) {
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
     * <code>IIOReadWarningListener</code> objects.
     *
     * <p> The default implementation sets the
     * <code>warningListeners</code> and <code>warningLocales</code>
     * instance variables to <code>null</code>.
     * <p>
     *  删除所有当前注册的<code> IIOReadWarningListener </code>对象。
     * 
     *  <p>默认实现将<code> warningListeners </code>和<code> warningLocales </code>实例变量设置为<code> null </code>。
     * 
     */
    public void removeAllIIOReadWarningListeners() {
        warningListeners = null;
        warningLocales = null;
    }

    /**
     * Adds an <code>IIOReadProgressListener</code> to the list of
     * registered progress listeners.  If <code>listener</code> is
     * <code>null</code>, no exception will be thrown and no action
     * will be taken.
     *
     * <p>
     *  向注册的进度侦听器列表中添加<code> IIOReadProgressListener </code>。
     * 如果<code> listener </code>是<code> null </code>,则不会抛出任何异常,并且不会执行任何操作。
     * 
     * 
     * @param listener an IIOReadProgressListener to be registered.
     *
     * @see #removeIIOReadProgressListener
     */
    public void addIIOReadProgressListener(IIOReadProgressListener listener) {
        if (listener == null) {
            return;
        }
        progressListeners = addToList(progressListeners, listener);
    }

    /**
     * Removes an <code>IIOReadProgressListener</code> from the list
     * of registered progress listeners.  If the listener was not
     * previously registered, or if <code>listener</code> is
     * <code>null</code>, no exception will be thrown and no action
     * will be taken.
     *
     * <p>
     *  从注册的进度侦听器列表中删除<code> IIOReadProgressListener </code>。
     * 如果侦听器以前没有注册,或者<code> listener </code>是<code> null </code>,则不会抛出任何异常,不会执行任何操作。
     * 
     * 
     * @param listener an IIOReadProgressListener to be unregistered.
     *
     * @see #addIIOReadProgressListener
     */
    public void
        removeIIOReadProgressListener (IIOReadProgressListener listener) {
        if (listener == null || progressListeners == null) {
            return;
        }
        progressListeners = removeFromList(progressListeners, listener);
    }

    /**
     * Removes all currently registered
     * <code>IIOReadProgressListener</code> objects.
     *
     * <p> The default implementation sets the
     * <code>progressListeners</code> instance variable to
     * <code>null</code>.
     * <p>
     *  删除所有当前注册的<code> IIOReadProgressListener </code>对象。
     * 
     *  <p>默认实现将<code> progressListeners </code>实例变量设置为<code> null </code>。
     * 
     */
    public void removeAllIIOReadProgressListeners() {
        progressListeners = null;
    }

    /**
     * Adds an <code>IIOReadUpdateListener</code> to the list of
     * registered update listeners.  If <code>listener</code> is
     * <code>null</code>, no exception will be thrown and no action
     * will be taken.  The listener will receive notification of pixel
     * updates as images and thumbnails are decoded, including the
     * starts and ends of progressive passes.
     *
     * <p> If no update listeners are present, the reader may choose
     * to perform fewer updates to the pixels of the destination
     * images and/or thumbnails, which may result in more efficient
     * decoding.
     *
     * <p> For example, in progressive JPEG decoding each pass
     * contains updates to a set of coefficients, which would have to
     * be transformed into pixel values and converted to an RGB color
     * space for each pass if listeners are present.  If no listeners
     * are present, the coefficients may simply be accumulated and the
     * final results transformed and color converted one time only.
     *
     * <p> The final results of decoding will be the same whether or
     * not intermediate updates are performed.  Thus if only the final
     * image is desired it may be preferable not to register any
     * <code>IIOReadUpdateListener</code>s.  In general, progressive
     * updating is most effective when fetching images over a network
     * connection that is very slow compared to local CPU processing;
     * over a fast connection, progressive updates may actually slow
     * down the presentation of the image.
     *
     * <p>
     *  向注册的更新侦听器列表中添加<code> IIOReadUpdateListener </code>。
     * 如果<code> listener </code>是<code> null </code>,则不会抛出任何异常,并且不会执行任何操作。
     * 当图像和缩略图被解码时,监听器将接收像素更新的通知,包括渐进通过的开始和结束。
     * 
     * <p>如果不存在更新侦听器,则读取器可以选择对目的图像和/或缩略图的像素执行更少的更新,这可以导致更有效的解码。
     * 
     *  例如,在逐行JPEG解码中,每一遍包含对一组系数的更新,如果存在听众,则这些更新将必须被变换成像素值并且被转换为用于每次通过的RGB色彩空间。
     * 如果不存在收听者,则可以简单地累积系数,并且仅对一次转换和颜色转换最终结果。
     * 
     *  <p>无论是否执行中间更新,解码的最终结果将是相同的。因此,如果只需要最终图像,则优选不注册任何<code> IIOReadUpdateListener </code>。
     * 一般来说,当通过网络连接获取图像时,与本地CPU处理相比,逐步更新是最有效的;通过快速连接,渐进式更新实际上可能减慢图像的呈现。
     * 
     * 
     * @param listener an IIOReadUpdateListener to be registered.
     *
     * @see #removeIIOReadUpdateListener
     */
    public void
        addIIOReadUpdateListener(IIOReadUpdateListener listener) {
        if (listener == null) {
            return;
        }
        updateListeners = addToList(updateListeners, listener);
    }

    /**
     * Removes an <code>IIOReadUpdateListener</code> from the list of
     * registered update listeners.  If the listener was not
     * previously registered, or if <code>listener</code> is
     * <code>null</code>, no exception will be thrown and no action
     * will be taken.
     *
     * <p>
     *  从注册的更新侦听器列表中删除<code> IIOReadUpdateListener </code>。
     * 如果侦听器以前没有注册,或者<code> listener </code>是<code> null </code>,则不会抛出任何异常,不会执行任何操作。
     * 
     * 
     * @param listener an IIOReadUpdateListener to be unregistered.
     *
     * @see #addIIOReadUpdateListener
     */
    public void removeIIOReadUpdateListener(IIOReadUpdateListener listener) {
        if (listener == null || updateListeners == null) {
            return;
        }
        updateListeners = removeFromList(updateListeners, listener);
    }

    /**
     * Removes all currently registered
     * <code>IIOReadUpdateListener</code> objects.
     *
     * <p> The default implementation sets the
     * <code>updateListeners</code> instance variable to
     * <code>null</code>.
     * <p>
     *  删除所有当前注册的<code> IIOReadUpdateListener </code>对象。
     * 
     *  <p>默认实现将<code> updateListeners </code>实例变量设置为<code> null </code>。
     * 
     */
    public void removeAllIIOReadUpdateListeners() {
        updateListeners = null;
    }

    /**
     * Broadcasts the start of an sequence of image reads to all
     * registered <code>IIOReadProgressListener</code>s by calling
     * their <code>sequenceStarted</code> method.  Subclasses may use
     * this method as a convenience.
     *
     * <p>
     * 通过调用其<code> sequenceStarted </code>方法,将图像读取序列的开始广播到所有注册的<code> IIOReadProgressListener </code>子类可以使用此
     * 方法作为方便。
     * 
     * 
     * @param minIndex the lowest index being read.
     */
    protected void processSequenceStarted(int minIndex) {
        if (progressListeners == null) {
            return;
        }
        int numListeners = progressListeners.size();
        for (int i = 0; i < numListeners; i++) {
            IIOReadProgressListener listener =
                (IIOReadProgressListener)progressListeners.get(i);
            listener.sequenceStarted(this, minIndex);
        }
    }

    /**
     * Broadcasts the completion of an sequence of image reads to all
     * registered <code>IIOReadProgressListener</code>s by calling
     * their <code>sequenceComplete</code> method.  Subclasses may use
     * this method as a convenience.
     * <p>
     *  通过调用其<code> sequenceComplete </code>方法,将图像读取序列的完成广播到所有注册的<code> IIOReadProgressListener </code>子类可以使
     * 用此方法作为方便。
     * 
     */
    protected void processSequenceComplete() {
        if (progressListeners == null) {
            return;
        }
        int numListeners = progressListeners.size();
        for (int i = 0; i < numListeners; i++) {
            IIOReadProgressListener listener =
                (IIOReadProgressListener)progressListeners.get(i);
            listener.sequenceComplete(this);
        }
    }

    /**
     * Broadcasts the start of an image read to all registered
     * <code>IIOReadProgressListener</code>s by calling their
     * <code>imageStarted</code> method.  Subclasses may use this
     * method as a convenience.
     *
     * <p>
     *  通过调用其<code> imageStarted </code>方法,将读取的图像的开始广播到所有注册的<code> IIOReadProgressListener </code>子类可以使用此方法作
     * 为方便。
     * 
     * 
     * @param imageIndex the index of the image about to be read.
     */
    protected void processImageStarted(int imageIndex) {
        if (progressListeners == null) {
            return;
        }
        int numListeners = progressListeners.size();
        for (int i = 0; i < numListeners; i++) {
            IIOReadProgressListener listener =
                (IIOReadProgressListener)progressListeners.get(i);
            listener.imageStarted(this, imageIndex);
        }
    }

    /**
     * Broadcasts the current percentage of image completion to all
     * registered <code>IIOReadProgressListener</code>s by calling
     * their <code>imageProgress</code> method.  Subclasses may use
     * this method as a convenience.
     *
     * <p>
     *  通过调用其<code> imageProgress </code>方法将当前百分比的映像完成广播到所有注册的<code> IIOReadProgressListener </code>子类可以使用此方
     * 法作为方便。
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
            IIOReadProgressListener listener =
                (IIOReadProgressListener)progressListeners.get(i);
            listener.imageProgress(this, percentageDone);
        }
    }

    /**
     * Broadcasts the completion of an image read to all registered
     * <code>IIOReadProgressListener</code>s by calling their
     * <code>imageComplete</code> method.  Subclasses may use this
     * method as a convenience.
     * <p>
     *  通过调用其<code> imageComplete </code>方法,将读取的图像完成广播到所有注册的<code> IIOReadProgressListener </code>子类可以使用此方法作
     * 为方便。
     * 
     */
    protected void processImageComplete() {
        if (progressListeners == null) {
            return;
        }
        int numListeners = progressListeners.size();
        for (int i = 0; i < numListeners; i++) {
            IIOReadProgressListener listener =
                (IIOReadProgressListener)progressListeners.get(i);
            listener.imageComplete(this);
        }
    }

    /**
     * Broadcasts the start of a thumbnail read to all registered
     * <code>IIOReadProgressListener</code>s by calling their
     * <code>thumbnailStarted</code> method.  Subclasses may use this
     * method as a convenience.
     *
     * <p>
     *  通过调用其<code> thumbnailStarted </code>方法将缩略图读取的开始广播到所有注册的<code> IIOReadProgressListener </code>子类可以使用此
     * 方法作为方便。
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
            IIOReadProgressListener listener =
                (IIOReadProgressListener)progressListeners.get(i);
            listener.thumbnailStarted(this, imageIndex, thumbnailIndex);
        }
    }

    /**
     * Broadcasts the current percentage of thumbnail completion to
     * all registered <code>IIOReadProgressListener</code>s by calling
     * their <code>thumbnailProgress</code> method.  Subclasses may
     * use this method as a convenience.
     *
     * <p>
     *  通过调用其<code> thumbnailProgress </code>方法将缩略图完成的当前百分比广播到所有注册的<code> IIOReadProgressListener </code>子类可
     * 以使用此方法作为方便。
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
            IIOReadProgressListener listener =
                (IIOReadProgressListener)progressListeners.get(i);
            listener.thumbnailProgress(this, percentageDone);
        }
    }

    /**
     * Broadcasts the completion of a thumbnail read to all registered
     * <code>IIOReadProgressListener</code>s by calling their
     * <code>thumbnailComplete</code> method.  Subclasses may use this
     * method as a convenience.
     * <p>
     * 通过调用其<code> thumbnailComplete </code>方法将缩略图读取完成广播到所有注册的<code> IIOReadProgressListener </code>子类可以使用此方
     * 法作为方便。
     * 
     */
    protected void processThumbnailComplete() {
        if (progressListeners == null) {
            return;
        }
        int numListeners = progressListeners.size();
        for (int i = 0; i < numListeners; i++) {
            IIOReadProgressListener listener =
                (IIOReadProgressListener)progressListeners.get(i);
            listener.thumbnailComplete(this);
        }
    }

    /**
     * Broadcasts that the read has been aborted to all registered
     * <code>IIOReadProgressListener</code>s by calling their
     * <code>readAborted</code> method.  Subclasses may use this
     * method as a convenience.
     * <p>
     *  通过调用其<code> readAborted </code>方法,读取已中止到所有注册的<code> IIOReadProgressListener </code>的广播。
     * 子类可以使用此方法作为方便。
     * 
     */
    protected void processReadAborted() {
        if (progressListeners == null) {
            return;
        }
        int numListeners = progressListeners.size();
        for (int i = 0; i < numListeners; i++) {
            IIOReadProgressListener listener =
                (IIOReadProgressListener)progressListeners.get(i);
            listener.readAborted(this);
        }
    }

    /**
     * Broadcasts the beginning of a progressive pass to all
     * registered <code>IIOReadUpdateListener</code>s by calling their
     * <code>passStarted</code> method.  Subclasses may use this
     * method as a convenience.
     *
     * <p>
     *  通过调用其<code> passStarted </code>方法,将渐进传递的开始广播到所有注册的<code> IIOReadUpdateListener </code>子类可以使用此方法作为方便。
     * 
     * 
     * @param theImage the <code>BufferedImage</code> being updated.
     * @param pass the index of the current pass, starting with 0.
     * @param minPass the index of the first pass that will be decoded.
     * @param maxPass the index of the last pass that will be decoded.
     * @param minX the X coordinate of the upper-left pixel included
     * in the pass.
     * @param minY the X coordinate of the upper-left pixel included
     * in the pass.
     * @param periodX the horizontal separation between pixels.
     * @param periodY the vertical separation between pixels.
     * @param bands an array of <code>int</code>s indicating the
     * set of affected bands of the destination.
     */
    protected void processPassStarted(BufferedImage theImage,
                                      int pass,
                                      int minPass, int maxPass,
                                      int minX, int minY,
                                      int periodX, int periodY,
                                      int[] bands) {
        if (updateListeners == null) {
            return;
        }
        int numListeners = updateListeners.size();
        for (int i = 0; i < numListeners; i++) {
            IIOReadUpdateListener listener =
                (IIOReadUpdateListener)updateListeners.get(i);
            listener.passStarted(this, theImage, pass,
                                 minPass,
                                 maxPass,
                                 minX, minY,
                                 periodX, periodY,
                                 bands);
        }
    }

    /**
     * Broadcasts the update of a set of samples to all registered
     * <code>IIOReadUpdateListener</code>s by calling their
     * <code>imageUpdate</code> method.  Subclasses may use this
     * method as a convenience.
     *
     * <p>
     *  通过调用其<code> imageUpdate </code>方法将一组样本的更新广播到所有注册的<code> IIOReadUpdateListener </code>子类可以使用此方法作为方便。
     * 
     * 
     * @param theImage the <code>BufferedImage</code> being updated.
     * @param minX the X coordinate of the upper-left pixel included
     * in the pass.
     * @param minY the X coordinate of the upper-left pixel included
     * in the pass.
     * @param width the total width of the area being updated, including
     * pixels being skipped if <code>periodX &gt; 1</code>.
     * @param height the total height of the area being updated,
     * including pixels being skipped if <code>periodY &gt; 1</code>.
     * @param periodX the horizontal separation between pixels.
     * @param periodY the vertical separation between pixels.
     * @param bands an array of <code>int</code>s indicating the
     * set of affected bands of the destination.
     */
    protected void processImageUpdate(BufferedImage theImage,
                                      int minX, int minY,
                                      int width, int height,
                                      int periodX, int periodY,
                                      int[] bands) {
        if (updateListeners == null) {
            return;
        }
        int numListeners = updateListeners.size();
        for (int i = 0; i < numListeners; i++) {
            IIOReadUpdateListener listener =
                (IIOReadUpdateListener)updateListeners.get(i);
            listener.imageUpdate(this,
                                 theImage,
                                 minX, minY,
                                 width, height,
                                 periodX, periodY,
                                 bands);
        }
    }

    /**
     * Broadcasts the end of a progressive pass to all
     * registered <code>IIOReadUpdateListener</code>s by calling their
     * <code>passComplete</code> method.  Subclasses may use this
     * method as a convenience.
     *
     * <p>
     *  通过调用它们的<code> passComplete </code>方法,向所有注册的<code> IIOReadUpdateListener </code>广播渐进式传递的结束。
     * 子类可以使用此方法作为方便。
     * 
     * 
     * @param theImage the <code>BufferedImage</code> being updated.
     */
    protected void processPassComplete(BufferedImage theImage) {
        if (updateListeners == null) {
            return;
        }
        int numListeners = updateListeners.size();
        for (int i = 0; i < numListeners; i++) {
            IIOReadUpdateListener listener =
                (IIOReadUpdateListener)updateListeners.get(i);
            listener.passComplete(this, theImage);
        }
    }

    /**
     * Broadcasts the beginning of a thumbnail progressive pass to all
     * registered <code>IIOReadUpdateListener</code>s by calling their
     * <code>thumbnailPassStarted</code> method.  Subclasses may use this
     * method as a convenience.
     *
     * <p>
     *  通过调用其<code> thumbnailPassStarted </code>方法,将缩略图渐进传递的开始广播到所有注册的<code> IIOReadUpdateListener </code>子类
     * 可以使用此方法作为方便。
     * 
     * 
     * @param theThumbnail the <code>BufferedImage</code> thumbnail
     * being updated.
     * @param pass the index of the current pass, starting with 0.
     * @param minPass the index of the first pass that will be decoded.
     * @param maxPass the index of the last pass that will be decoded.
     * @param minX the X coordinate of the upper-left pixel included
     * in the pass.
     * @param minY the X coordinate of the upper-left pixel included
     * in the pass.
     * @param periodX the horizontal separation between pixels.
     * @param periodY the vertical separation between pixels.
     * @param bands an array of <code>int</code>s indicating the
     * set of affected bands of the destination.
     */
    protected void processThumbnailPassStarted(BufferedImage theThumbnail,
                                               int pass,
                                               int minPass, int maxPass,
                                               int minX, int minY,
                                               int periodX, int periodY,
                                               int[] bands) {
        if (updateListeners == null) {
            return;
        }
        int numListeners = updateListeners.size();
        for (int i = 0; i < numListeners; i++) {
            IIOReadUpdateListener listener =
                (IIOReadUpdateListener)updateListeners.get(i);
            listener.thumbnailPassStarted(this, theThumbnail, pass,
                                          minPass,
                                          maxPass,
                                          minX, minY,
                                          periodX, periodY,
                                          bands);
        }
    }

    /**
     * Broadcasts the update of a set of samples in a thumbnail image
     * to all registered <code>IIOReadUpdateListener</code>s by
     * calling their <code>thumbnailUpdate</code> method.  Subclasses may
     * use this method as a convenience.
     *
     * <p>
     *  通过调用其<code> thumbnailUpdate </code>方法将缩略图中一组样本的更新广播到所有注册的<code> IIOReadUpdateListener </code>子类可以使用此
     * 方法作为方便。
     * 
     * 
     * @param theThumbnail the <code>BufferedImage</code> thumbnail
     * being updated.
     * @param minX the X coordinate of the upper-left pixel included
     * in the pass.
     * @param minY the X coordinate of the upper-left pixel included
     * in the pass.
     * @param width the total width of the area being updated, including
     * pixels being skipped if <code>periodX &gt; 1</code>.
     * @param height the total height of the area being updated,
     * including pixels being skipped if <code>periodY &gt; 1</code>.
     * @param periodX the horizontal separation between pixels.
     * @param periodY the vertical separation between pixels.
     * @param bands an array of <code>int</code>s indicating the
     * set of affected bands of the destination.
     */
    protected void processThumbnailUpdate(BufferedImage theThumbnail,
                                          int minX, int minY,
                                          int width, int height,
                                          int periodX, int periodY,
                                          int[] bands) {
        if (updateListeners == null) {
            return;
        }
        int numListeners = updateListeners.size();
        for (int i = 0; i < numListeners; i++) {
            IIOReadUpdateListener listener =
                (IIOReadUpdateListener)updateListeners.get(i);
            listener.thumbnailUpdate(this,
                                     theThumbnail,
                                     minX, minY,
                                     width, height,
                                     periodX, periodY,
                                     bands);
        }
    }

    /**
     * Broadcasts the end of a thumbnail progressive pass to all
     * registered <code>IIOReadUpdateListener</code>s by calling their
     * <code>thumbnailPassComplete</code> method.  Subclasses may use this
     * method as a convenience.
     *
     * <p>
     * 通过调用其<code> thumbnailPassComplete </code>方法,将缩略图逐行传递的结束广播到所有注册的<code> IIOReadUpdateListener </code>子类
     * 可以使用此方法作为方便。
     * 
     * 
     * @param theThumbnail the <code>BufferedImage</code> thumbnail
     * being updated.
     */
    protected void processThumbnailPassComplete(BufferedImage theThumbnail) {
        if (updateListeners == null) {
            return;
        }
        int numListeners = updateListeners.size();
        for (int i = 0; i < numListeners; i++) {
            IIOReadUpdateListener listener =
                (IIOReadUpdateListener)updateListeners.get(i);
            listener.thumbnailPassComplete(this, theThumbnail);
        }
    }

    /**
     * Broadcasts a warning message to all registered
     * <code>IIOReadWarningListener</code>s by calling their
     * <code>warningOccurred</code> method.  Subclasses may use this
     * method as a convenience.
     *
     * <p>
     *  通过调用其<code> warningOccurred </code>方法向所有注册的<code> IIOReadWarningListener </code>发出警告消息。
     * 子类可以使用此方法作为方便。
     * 
     * 
     * @param warning the warning message to send.
     *
     * @exception IllegalArgumentException if <code>warning</code>
     * is <code>null</code>.
     */
    protected void processWarningOccurred(String warning) {
        if (warningListeners == null) {
            return;
        }
        if (warning == null) {
            throw new IllegalArgumentException("warning == null!");
        }
        int numListeners = warningListeners.size();
        for (int i = 0; i < numListeners; i++) {
            IIOReadWarningListener listener =
                (IIOReadWarningListener)warningListeners.get(i);

            listener.warningOccurred(this, warning);
        }
    }

    /**
     * Broadcasts a localized warning message to all registered
     * <code>IIOReadWarningListener</code>s by calling their
     * <code>warningOccurred</code> method with a string taken
     * from a <code>ResourceBundle</code>.  Subclasses may use this
     * method as a convenience.
     *
     * <p>
     *  通过使用从<code> ResourceBundle </code>中获取的字符串调用其<code> warningOccurred </code>方法,向所有注册的<code> IIOReadWar
     * ningListener </code>广播本地化警告消息。
     * 子类可以使用此方法作为方便。
     * 
     * 
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
    protected void processWarningOccurred(String baseName,
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
            IIOReadWarningListener listener =
                (IIOReadWarningListener)warningListeners.get(i);
            Locale locale = (Locale)warningLocales.get(i);
            if (locale == null) {
                locale = Locale.getDefault();
            }

            /**
             * If an applet supplies an implementation of ImageReader and
             * resource bundles, then the resource bundle will need to be
             * accessed via the applet class loader. So first try the context
             * class loader to locate the resource bundle.
             * If that throws MissingResourceException, then try the
             * system class loader.
             * <p>
             *  如果一个applet提供了ImageReader和资源束的实现,那么需要通过applet类加载器来访问资源束。所以首先尝试上下文类加载器来定位资源束。
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

            listener.warningOccurred(this, warning);
        }
    }

    // State management

    /**
     * Restores the <code>ImageReader</code> to its initial state.
     *
     * <p> The default implementation calls <code>setInput(null,
     * false)</code>, <code>setLocale(null)</code>,
     * <code>removeAllIIOReadUpdateListeners()</code>,
     * <code>removeAllIIOReadWarningListeners()</code>,
     * <code>removeAllIIOReadProgressListeners()</code>, and
     * <code>clearAbortRequest</code>.
     * <p>
     *  将<code> ImageReader </code>恢复到其初始状态。
     * 
     *  <p>默认实现调用<code> setInput(null,false)</code>,<code> setLocale(null)</code>,<code> removeAllIIOReadUpd
     * ateListeners()</code>,<code> removeAllIIOReadWarningListeners </code>,<code> removeAllIIOReadProgress
     * Listeners()</code>和<code> clearAbortRequest </code>。
     * 
     */
    public void reset() {
        setInput(null, false, false);
        setLocale(null);
        removeAllIIOReadUpdateListeners();
        removeAllIIOReadProgressListeners();
        removeAllIIOReadWarningListeners();
        clearAbortRequest();
    }

    /**
     * Allows any resources held by this object to be released.  The
     * result of calling any other method (other than
     * <code>finalize</code>) subsequent to a call to this method
     * is undefined.
     *
     * <p>It is important for applications to call this method when they
     * know they will no longer be using this <code>ImageReader</code>.
     * Otherwise, the reader may continue to hold on to resources
     * indefinitely.
     *
     * <p>The default implementation of this method in the superclass does
     * nothing.  Subclass implementations should ensure that all resources,
     * especially native resources, are released.
     * <p>
     *  允许释放此对象持有的任何资源。未调用在调用此方法后调用任何其他方法(<code> finalize </code>除外)的结果。
     * 
     * <p>当应用程序知道他们不再使用此<code> ImageReader </code>时,调用此方法很重要。否则,读者可以继续无限期地保持资源。
     * 
     *  <p>超类中此方法的默认实现不起作用。子类实现应该确保所有资源,特别是本地资源,被释放。
     * 
     */
    public void dispose() {
    }

    // Utility methods

    /**
     * A utility method that may be used by readers to compute the
     * region of the source image that should be read, taking into
     * account any source region and subsampling offset settings in
     * the supplied <code>ImageReadParam</code>.  The actual
     * subsampling factors, destination size, and destination offset
     * are <em>not</em> taken into consideration, thus further
     * clipping must take place.  The {@link #computeRegions computeRegions}
     * method performs all necessary clipping.
     *
     * <p>
     *  读取器可以使用实用方法来计算应该读取的源图像的区域,同时考虑所提供的<code> ImageReadParam </code>中的任何源区域和子采样偏移设置。
     * 不考虑实际的子采样因子,目的地大小和目的地偏移,因此必须进行进一步限幅。 {@link #computeRegions computeRegions}方法执行所有必要的裁剪。
     * 
     * 
     * @param param the <code>ImageReadParam</code> being used, or
     * <code>null</code>.
     * @param srcWidth the width of the source image.
     * @param srcHeight the height of the source image.
     *
     * @return the source region as a <code>Rectangle</code>.
     */
    protected static Rectangle getSourceRegion(ImageReadParam param,
                                               int srcWidth,
                                               int srcHeight) {
        Rectangle sourceRegion = new Rectangle(0, 0, srcWidth, srcHeight);
        if (param != null) {
            Rectangle region = param.getSourceRegion();
            if (region != null) {
                sourceRegion = sourceRegion.intersection(region);
            }

            int subsampleXOffset = param.getSubsamplingXOffset();
            int subsampleYOffset = param.getSubsamplingYOffset();
            sourceRegion.x += subsampleXOffset;
            sourceRegion.y += subsampleYOffset;
            sourceRegion.width -= subsampleXOffset;
            sourceRegion.height -= subsampleYOffset;
        }

        return sourceRegion;
    }

    /**
     * Computes the source region of interest and the destination
     * region of interest, taking the width and height of the source
     * image, an optional destination image, and an optional
     * <code>ImageReadParam</code> into account.  The source region
     * begins with the entire source image.  Then that is clipped to
     * the source region specified in the <code>ImageReadParam</code>,
     * if one is specified.
     *
     * <p> If either of the destination offsets are negative, the
     * source region is clipped so that its top left will coincide
     * with the top left of the destination image, taking subsampling
     * into account.  Then the result is clipped to the destination
     * image on the right and bottom, if one is specified, taking
     * subsampling and destination offsets into account.
     *
     * <p> Similarly, the destination region begins with the source
     * image, is translated to the destination offset given in the
     * <code>ImageReadParam</code> if there is one, and finally is
     * clipped to the destination image, if there is one.
     *
     * <p> If either the source or destination regions end up having a
     * width or height of 0, an <code>IllegalArgumentException</code>
     * is thrown.
     *
     * <p> The {@link #getSourceRegion getSourceRegion>}
     * method may be used if only source clipping is desired.
     *
     * <p>
     *  计算感兴趣的源区域和感兴趣的目的区域,考虑源图像的宽度和高度,可选的目的图像和可选的<code> ImageReadParam </code>。源区域以整个源图像开始。
     * 然后将其剪切到<code> ImageReadParam </code>中指定的源区域,如果指定了它。
     * 
     * <p>如果目标偏移量中的任一个为负,则源区域被剪切,使得其左上角将与目标图像的左上角重合,从而考虑子采样。然后将结果剪切到右侧和底部的目标图像(如果指定了一个),将子采样和目标偏移考虑在内。
     * 
     *  <p>类似地,目的地区域以源图像开始,如果有一个,则被转换到在<code> ImageReadParam </code>中给出的目的地偏移,并且最终被剪切到目的图像,如果有的话。
     * 
     *  <p>如果源区域或目标区域的宽度或高度为0,则会抛出<code> IllegalArgumentException </code>。
     * 
     *  <p>如果仅需要源剪辑,则可以使用{@link #getSourceRegion getSourceRegion>}方法。
     * 
     * 
     * @param param an <code>ImageReadParam</code>, or <code>null</code>.
     * @param srcWidth the width of the source image.
     * @param srcHeight the height of the source image.
     * @param image a <code>BufferedImage</code> that will be the
     * destination image, or <code>null</code>.
     * @param srcRegion a <code>Rectangle</code> that will be filled with
     * the source region of interest.
     * @param destRegion a <code>Rectangle</code> that will be filled with
     * the destination region of interest.
     * @exception IllegalArgumentException if <code>srcRegion</code>
     * is <code>null</code>.
     * @exception IllegalArgumentException if <code>dstRegion</code>
     * is <code>null</code>.
     * @exception IllegalArgumentException if the resulting source or
     * destination region is empty.
     */
    protected static void computeRegions(ImageReadParam param,
                                         int srcWidth,
                                         int srcHeight,
                                         BufferedImage image,
                                         Rectangle srcRegion,
                                         Rectangle destRegion) {
        if (srcRegion == null) {
            throw new IllegalArgumentException("srcRegion == null!");
        }
        if (destRegion == null) {
            throw new IllegalArgumentException("destRegion == null!");
        }

        // Start with the entire source image
        srcRegion.setBounds(0, 0, srcWidth, srcHeight);

        // Destination also starts with source image, as that is the
        // maximum extent if there is no subsampling
        destRegion.setBounds(0, 0, srcWidth, srcHeight);

        // Clip that to the param region, if there is one
        int periodX = 1;
        int periodY = 1;
        int gridX = 0;
        int gridY = 0;
        if (param != null) {
            Rectangle paramSrcRegion = param.getSourceRegion();
            if (paramSrcRegion != null) {
                srcRegion.setBounds(srcRegion.intersection(paramSrcRegion));
            }
            periodX = param.getSourceXSubsampling();
            periodY = param.getSourceYSubsampling();
            gridX = param.getSubsamplingXOffset();
            gridY = param.getSubsamplingYOffset();
            srcRegion.translate(gridX, gridY);
            srcRegion.width -= gridX;
            srcRegion.height -= gridY;
            destRegion.setLocation(param.getDestinationOffset());
        }

        // Now clip any negative destination offsets, i.e. clip
        // to the top and left of the destination image
        if (destRegion.x < 0) {
            int delta = -destRegion.x*periodX;
            srcRegion.x += delta;
            srcRegion.width -= delta;
            destRegion.x = 0;
        }
        if (destRegion.y < 0) {
            int delta = -destRegion.y*periodY;
            srcRegion.y += delta;
            srcRegion.height -= delta;
            destRegion.y = 0;
        }

        // Now clip the destination Region to the subsampled width and height
        int subsampledWidth = (srcRegion.width + periodX - 1)/periodX;
        int subsampledHeight = (srcRegion.height + periodY - 1)/periodY;
        destRegion.width = subsampledWidth;
        destRegion.height = subsampledHeight;

        // Now clip that to right and bottom of the destination image,
        // if there is one, taking subsampling into account
        if (image != null) {
            Rectangle destImageRect = new Rectangle(0, 0,
                                                    image.getWidth(),
                                                    image.getHeight());
            destRegion.setBounds(destRegion.intersection(destImageRect));
            if (destRegion.isEmpty()) {
                throw new IllegalArgumentException
                    ("Empty destination region!");
            }

            int deltaX = destRegion.x + subsampledWidth - image.getWidth();
            if (deltaX > 0) {
                srcRegion.width -= deltaX*periodX;
            }
            int deltaY =  destRegion.y + subsampledHeight - image.getHeight();
            if (deltaY > 0) {
                srcRegion.height -= deltaY*periodY;
            }
        }
        if (srcRegion.isEmpty() || destRegion.isEmpty()) {
            throw new IllegalArgumentException("Empty region!");
        }
    }

    /**
     * A utility method that may be used by readers to test the
     * validity of the source and destination band settings of an
     * <code>ImageReadParam</code>.  This method may be called as soon
     * as the reader knows both the number of bands of the source
     * image as it exists in the input stream, and the number of bands
     * of the destination image that being written.
     *
     * <p> The method retrieves the source and destination band
     * setting arrays from param using the <code>getSourceBands</code>
     * and <code>getDestinationBands</code>methods (or considers them
     * to be <code>null</code> if <code>param</code> is
     * <code>null</code>).  If the source band setting array is
     * <code>null</code>, it is considered to be equal to the array
     * <code>{ 0, 1, ..., numSrcBands - 1 }</code>, and similarly for
     * the destination band setting array.
     *
     * <p> The method then tests that both arrays are equal in length,
     * and that neither array contains a value larger than the largest
     * available band index.
     *
     * <p> Any failure results in an
     * <code>IllegalArgumentException</code> being thrown; success
     * results in the method returning silently.
     *
     * <p>
     *  一种实用方法,可由读者使用来测试<code> ImageReadParam </code>的源和目标带设置的有效性。
     * 一旦读取器知道源图像在输入流中存在的带的数量以及被写入的目的图像的带的数量,则可以调用该方法。
     * 
     * <p>该方法使用<code> getSourceBands </code>和<code> getDestinationBands </code>方法从参数中检索源和目标带设置数组(或者认为它们是<code>
     *  null </code> if <code> param </code> is <code> null </code>)。
     * 如果源带设置数组是<code> null </code>,则认为等于数组<code> {0,1,...,numSrcBands -1} </code>目的频带设置数组。
     * 
     *  <p>该方法然后测试两个数组的长度相等,并且两个数组都不包含大于最大可用频带索引的值。
     * 
     *  <p>任何失败都会导致<code> IllegalArgumentException </code>被抛出;成功导致方法静默返回。
     * 
     * 
     * @param param the <code>ImageReadParam</code> being used to read
     * the image.
     * @param numSrcBands the number of bands of the image as it exists
     * int the input source.
     * @param numDstBands the number of bands in the destination image
     * being written.
     *
     * @exception IllegalArgumentException if <code>param</code>
     * contains an invalid specification of a source and/or
     * destination band subset.
     */
    protected static void checkReadParamBandSettings(ImageReadParam param,
                                                     int numSrcBands,
                                                     int numDstBands) {
        // A null param is equivalent to srcBands == dstBands == null.
        int[] srcBands = null;
        int[] dstBands = null;
        if (param != null) {
            srcBands = param.getSourceBands();
            dstBands = param.getDestinationBands();
        }

        int paramSrcBandLength =
            (srcBands == null) ? numSrcBands : srcBands.length;
        int paramDstBandLength =
            (dstBands == null) ? numDstBands : dstBands.length;

        if (paramSrcBandLength != paramDstBandLength) {
            throw new IllegalArgumentException("ImageReadParam num source & dest bands differ!");
        }

        if (srcBands != null) {
            for (int i = 0; i < srcBands.length; i++) {
                if (srcBands[i] >= numSrcBands) {
                    throw new IllegalArgumentException("ImageReadParam source bands contains a value >= the number of source bands!");
                }
            }
        }

        if (dstBands != null) {
            for (int i = 0; i < dstBands.length; i++) {
                if (dstBands[i] >= numDstBands) {
                    throw new IllegalArgumentException("ImageReadParam dest bands contains a value >= the number of dest bands!");
                }
            }
        }
    }

    /**
     * Returns the <code>BufferedImage</code> to which decoded pixel
     * data should be written.  The image is determined by inspecting
     * the supplied <code>ImageReadParam</code> if it is
     * non-<code>null</code>; if its <code>getDestination</code>
     * method returns a non-<code>null</code> value, that image is
     * simply returned.  Otherwise,
     * <code>param.getDestinationType</code> method is called to
     * determine if a particular image type has been specified.  If
     * so, the returned <code>ImageTypeSpecifier</code> is used after
     * checking that it is equal to one of those included in
     * <code>imageTypes</code>.
     *
     * <p> If <code>param</code> is <code>null</code> or the above
     * steps have not yielded an image or an
     * <code>ImageTypeSpecifier</code>, the first value obtained from
     * the <code>imageTypes</code> parameter is used.  Typically, the
     * caller will set <code>imageTypes</code> to the value of
     * <code>getImageTypes(imageIndex)</code>.
     *
     * <p> Next, the dimensions of the image are determined by a call
     * to <code>computeRegions</code>.  The actual width and height of
     * the image being decoded are passed in as the <code>width</code>
     * and <code>height</code> parameters.
     *
     * <p>
     *  返回应写入解码像素数据的<code> BufferedImage </code>。
     * 如果它是非<code> null </code>,则通过检查提供的<code> ImageReadParam </code>如果它的<code> getDestination </code>方法返回一个
     * 非<code> null </code>值,那个图像被简单地返回。
     *  返回应写入解码像素数据的<code> BufferedImage </code>。
     * 否则,将调用<code> param.getDestinationType </code>方法来确定是否已指定了特定的图像类型。
     * 如果是,则在检查它等于<code> imageTypes </code>中包括的那个之一之后,使用返回的<code> ImageTypeSpecifier </code>。
     * 
     * 
     * @param param an <code>ImageReadParam</code> to be used to get
     * the destination image or image type, or <code>null</code>.
     * @param imageTypes an <code>Iterator</code> of
     * <code>ImageTypeSpecifier</code>s indicating the legal image
     * types, with the default first.
     * @param width the true width of the image or tile begin decoded.
     * @param height the true width of the image or tile being decoded.
     *
     * @return the <code>BufferedImage</code> to which decoded pixel
     * data should be written.
     *
     * @exception IIOException if the <code>ImageTypeSpecifier</code>
     * specified by <code>param</code> does not match any of the legal
     * ones from <code>imageTypes</code>.
     * @exception IllegalArgumentException if <code>imageTypes</code>
     * is <code>null</code> or empty, or if an object not of type
     * <code>ImageTypeSpecifier</code> is retrieved from it.
     * @exception IllegalArgumentException if the resulting image would
     * have a width or height less than 1.
     * @exception IllegalArgumentException if the product of
     * <code>width</code> and <code>height</code> is greater than
     * <code>Integer.MAX_VALUE</code>.
     */
    protected static BufferedImage
        getDestination(ImageReadParam param,
                       Iterator<ImageTypeSpecifier> imageTypes,
                       int width, int height)
        throws IIOException {
        if (imageTypes == null || !imageTypes.hasNext()) {
            throw new IllegalArgumentException("imageTypes null or empty!");
        }
        if ((long)width*height > Integer.MAX_VALUE) {
            throw new IllegalArgumentException
                ("width*height > Integer.MAX_VALUE!");
        }

        BufferedImage dest = null;
        ImageTypeSpecifier imageType = null;

        // If param is non-null, use it
        if (param != null) {
            // Try to get the image itself
            dest = param.getDestination();
            if (dest != null) {
                return dest;
            }

            // No image, get the image type
            imageType = param.getDestinationType();
        }

        // No info from param, use fallback image type
        if (imageType == null) {
            Object o = imageTypes.next();
            if (!(o instanceof ImageTypeSpecifier)) {
                throw new IllegalArgumentException
                    ("Non-ImageTypeSpecifier retrieved from imageTypes!");
            }
            imageType = (ImageTypeSpecifier)o;
        } else {
            boolean foundIt = false;
            while (imageTypes.hasNext()) {
                ImageTypeSpecifier type =
                    (ImageTypeSpecifier)imageTypes.next();
                if (type.equals(imageType)) {
                    foundIt = true;
                    break;
                }
            }

            if (!foundIt) {
                throw new IIOException
                    ("Destination type from ImageReadParam does not match!");
            }
        }

        Rectangle srcRegion = new Rectangle(0,0,0,0);
        Rectangle destRegion = new Rectangle(0,0,0,0);
        computeRegions(param,
                       width,
                       height,
                       null,
                       srcRegion,
                       destRegion);

        int destWidth = destRegion.x + destRegion.width;
        int destHeight = destRegion.y + destRegion.height;
        // Create a new image based on the type specifier
        return imageType.createBufferedImage(destWidth, destHeight);
    }
}
