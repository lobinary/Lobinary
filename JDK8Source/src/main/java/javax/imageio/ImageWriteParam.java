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

package javax.imageio;

import java.awt.Dimension;
import java.util.Locale;

/**
 * A class describing how a stream is to be encoded.  Instances of
 * this class or its subclasses are used to supply prescriptive
 * "how-to" information to instances of <code>ImageWriter</code>.
 *
 * <p> A plug-in for a specific image format may define a subclass of
 * this class, and return objects of that class from the
 * <code>getDefaultWriteParam</code> method of its
 * <code>ImageWriter</code> implementation.  For example, the built-in
 * JPEG writer plug-in will return instances of
 * <code>javax.imageio.plugins.jpeg.JPEGImageWriteParam</code>.
 *
 * <p> The region of the image to be written is determined by first
 * intersecting the actual bounds of the image with the rectangle
 * specified by <code>IIOParam.setSourceRegion</code>, if any.  If the
 * resulting rectangle has a width or height of zero, the writer will
 * throw an <code>IIOException</code>. If the intersection is
 * non-empty, writing will commence with the first subsampled pixel
 * and include additional pixels within the intersected bounds
 * according to the horizontal and vertical subsampling factors
 * specified by {@link IIOParam#setSourceSubsampling
 * IIOParam.setSourceSubsampling}.
 *
 * <p> Individual features such as tiling, progressive encoding, and
 * compression may be set in one of four modes.
 * <code>MODE_DISABLED</code> disables the features;
 * <code>MODE_DEFAULT</code> enables the feature with
 * writer-controlled parameter values; <code>MODE_EXPLICIT</code>
 * enables the feature and allows the use of a <code>set</code> method
 * to provide additional parameters; and
 * <code>MODE_COPY_FROM_METADATA</code> copies relevant parameter
 * values from the stream and image metadata objects passed to the
 * writer.  The default for all features is
 * <code>MODE_COPY_FROM_METADATA</code>.  Non-standard features
 * supplied in subclasses are encouraged, but not required to use a
 * similar scheme.
 *
 * <p> Plug-in writers may extend the functionality of
 * <code>ImageWriteParam</code> by providing a subclass that implements
 * additional, plug-in specific interfaces.  It is up to the plug-in
 * to document what interfaces are available and how they are to be
 * used.  Writers will silently ignore any extended features of an
 * <code>ImageWriteParam</code> subclass of which they are not aware.
 * Also, they may ignore any optional features that they normally
 * disable when creating their own <code>ImageWriteParam</code>
 * instances via <code>getDefaultWriteParam</code>.
 *
 * <p> Note that unless a query method exists for a capability, it must
 * be supported by all <code>ImageWriter</code> implementations
 * (<i>e.g.</i> progressive encoding is optional, but subsampling must be
 * supported).
 *
 *
 * <p>
 *  描述流如何被编码的类。此类或其子类的实例用于向<code> ImageWriter </code>的实例提供指定的"how-to"信息。
 * 
 *  <p>特定图片格式的插件可以定义此类的子类,并从其<code> ImageWriter </code>实现的<code> getDefaultWriteParam </code>方法中返回该类的对象。
 * 例如,内置的JPEG编写器插件将返回<code> javax.imageio.plugins.jpeg.JPEGImageWriteParam </code>的实例。
 * 
 *  <p>要写入的图像的区域通过首先将图像的实际边界与由<code> IIOParam.setSourceRegion </code>指定的矩形相交(如果有的话)来确定。
 * 如果生成的矩形的宽度或高度为零,写程序将抛出一个<code> IIOException </code>。
 * 如果交集是非空的,则将以第一子采样像素开始写入,并且根据由{@link IIOParam#setSourceSubsampling IIOParam.setSourceSubsampling}指定的水平
 * 和垂直子采样因子,在相交边界内包括额外的像素。
 * 如果生成的矩形的宽度或高度为零,写程序将抛出一个<code> IIOException </code>。
 * 
 * 可以以四种模式之一设置诸如平铺,渐进编码和压缩的各个特征。
 *  <code> MODE_DISABLED </code>禁用功能; <code> MODE_DEFAULT </code>使用写入器控制的参数值启用该功能; <code> MODE_EXPLICIT 
 * </code>启用此功能,并允许使用<code> set </code>方法提供其他参数;和<code> MODE_COPY_FROM_METADATA </code>从传递给writer的流和图像元数
 * 据对象复制相关参数值。
 * 可以以四种模式之一设置诸如平铺,渐进编码和压缩的各个特征。所有功能的默认值为<code> MODE_COPY_FROM_METADATA </code>。
 * 鼓励在子类中提供的非标准特征,但不需要使用类似的方案。
 * 
 *  <p>插件编写器可以通过提供一个实现附加的插件特定接口的子类来扩展<code> ImageWriteParam </code>的功能。它是由插件来记录什么接口可用以及如何使用它们。
 * 写入器将静默地忽略它们不知道的<code> ImageWriteParam </code>子类的任何扩展功能。
 * 此外,他们可以忽略通过<code> getDefaultWriteParam </code>创建自己的<code> ImageWriteParam </code>实例时通常禁用的任何可选功能。
 * 
 *  <p>请注意,除非存在某种功能的查询方法,否则必须由所有<code> ImageWriter </code>实现(<i>例如</i>渐进编码是可选的,但必须支持子采样)支持。
 * 
 * 
 * @see ImageReadParam
 */
public class ImageWriteParam extends IIOParam {

    /**
     * A constant value that may be passed into methods such as
     * <code>setTilingMode</code>, <code>setProgressiveMode</code>,
     * and <code>setCompressionMode</code> to disable a feature for
     * future writes.  That is, when this mode is set the stream will
     * <b>not</b> be tiled, progressive, or compressed, and the
     * relevant accessor methods will throw an
     * <code>IllegalStateException</code>.
     *
     * <p>
     * 可以传递到诸如<code> setTilingMode </code>,<code> setProgressiveMode </code>和<code> setCompressionMode </code>
     * 等方法的常量值,以禁用将来写入的功能。
     * 也就是说,当设置此模式时,流将<b>不</b>平铺,渐进或压缩,相关的访问器方法将抛出一个<code> IllegalStateException </code>。
     * 
     * 
     * @see #MODE_EXPLICIT
     * @see #MODE_COPY_FROM_METADATA
     * @see #MODE_DEFAULT
     * @see #setProgressiveMode
     * @see #getProgressiveMode
     * @see #setTilingMode
     * @see #getTilingMode
     * @see #setCompressionMode
     * @see #getCompressionMode
     */
    public static final int MODE_DISABLED = 0;

    /**
     * A constant value that may be passed into methods such as
     * <code>setTilingMode</code>,
     * <code>setProgressiveMode</code>, and
     * <code>setCompressionMode</code> to enable that feature for
     * future writes.  That is, when this mode is enabled the stream
     * will be tiled, progressive, or compressed according to a
     * sensible default chosen internally by the writer in a plug-in
     * dependent way, and the relevant accessor methods will
     * throw an <code>IllegalStateException</code>.
     *
     * <p>
     *  可以传递到诸如<code> setTilingMode </code>,<code> setProgressiveMode </code>和<code> setCompressionMode </code>
     * 等方法的常量值,也就是说,当启用此模式时,流将根据由编写者以插件依赖方式在内部选择的明智默认来平铺,渐进或压缩,并且相关的访问器方法将抛出<code> IllegalStateException <代码>
     * 。
     * 
     * 
     * @see #MODE_DISABLED
     * @see #MODE_EXPLICIT
     * @see #MODE_COPY_FROM_METADATA
     * @see #setProgressiveMode
     * @see #getProgressiveMode
     * @see #setTilingMode
     * @see #getTilingMode
     * @see #setCompressionMode
     * @see #getCompressionMode
     */
    public static final int MODE_DEFAULT = 1;

    /**
     * A constant value that may be passed into methods such as
     * <code>setTilingMode</code> or <code>setCompressionMode</code>
     * to enable a feature for future writes. That is, when this mode
     * is set the stream will be tiled or compressed according to
     * additional information supplied to the corresponding
     * <code>set</code> methods in this class and retrievable from the
     * corresponding <code>get</code> methods.  Note that this mode is
     * not supported for progressive output.
     *
     * <p>
     *  可以传递到诸如<code> setTilingMode </code>或<code> setCompressionMode </code>等方法的常量值,以便为将来写入启用功能。
     * 也就是说,当设置该模式时,将根据提供给该类中的相应<code> set </code>方法的附加信息来平铺或压缩流,并且可以从相应的<code> get </code>方法检索。
     * 请注意,渐进式输出不支持此模式。
     * 
     * 
     * @see #MODE_DISABLED
     * @see #MODE_COPY_FROM_METADATA
     * @see #MODE_DEFAULT
     * @see #setProgressiveMode
     * @see #getProgressiveMode
     * @see #setTilingMode
     * @see #getTilingMode
     * @see #setCompressionMode
     * @see #getCompressionMode
     */
    public static final int MODE_EXPLICIT = 2;

    /**
     * A constant value that may be passed into methods such as
     * <code>setTilingMode</code>, <code>setProgressiveMode</code>, or
     * <code>setCompressionMode</code> to enable that feature for
     * future writes.  That is, when this mode is enabled the stream
     * will be tiled, progressive, or compressed based on the contents
     * of stream and/or image metadata passed into the write
     * operation, and any relevant accessor methods will throw an
     * <code>IllegalStateException</code>.
     *
     * <p> This is the default mode for all features, so that a read
     * including metadata followed by a write including metadata will
     * preserve as much information as possible.
     *
     * <p>
     * 可以传递到诸如<code> setTilingMode </code>,<code> setProgressiveMode </code>或<code> setCompressionMode </code>
     * 等方法的常量值,也就是说,当启用此模式时,流将基于传递到写入操作的流和/或图像元数据的内容被平铺,逐行或压缩,并且任何相关的存取器方法将抛出<code> IllegalStateException </code >
     * 。
     * 
     *  <p>这是所有功能的默认模式,因此包括元数据的读取包括元数据的写入将保留尽可能多的信息。
     * 
     * 
     * @see #MODE_DISABLED
     * @see #MODE_EXPLICIT
     * @see #MODE_DEFAULT
     * @see #setProgressiveMode
     * @see #getProgressiveMode
     * @see #setTilingMode
     * @see #getTilingMode
     * @see #setCompressionMode
     * @see #getCompressionMode
     */
    public static final int MODE_COPY_FROM_METADATA = 3;

    // If more modes are added, this should be updated.
    private static final int MAX_MODE = MODE_COPY_FROM_METADATA;

    /**
     * A <code>boolean</code> that is <code>true</code> if this
     * <code>ImageWriteParam</code> allows tile width and tile height
     * parameters to be set.  By default, the value is
     * <code>false</code>.  Subclasses must set the value manually.
     *
     * <p> Subclasses that do not support writing tiles should ensure
     * that this value is set to <code>false</code>.
     * <p>
     *  如果此<code> ImageWriteParam </code>允许设置tile width和tile height参数,则<code> boolean </code>即<code> true </code>
     * 默认情况下,值为<code> false </code>。
     * 子类必须手动设置值。
     * 
     *  <p>不支持写入图块的子类应确保此值设置为<code> false </code>。
     * 
     */
    protected boolean canWriteTiles = false;

    /**
     * The mode controlling tiling settings, which Must be
     * set to one of the four <code>MODE_*</code> values.  The default
     * is <code>MODE_COPY_FROM_METADATA</code>.
     *
     * <p> Subclasses that do not writing tiles may ignore this value.
     *
     * <p>
     *  模式控制平铺设置,其必须设置为四个<code> MODE _ * </code>值之一。默认值为<code> MODE_COPY_FROM_METADATA </code>。
     * 
     *  <p>不编写图块的子类可能会忽略此值。
     * 
     * 
     * @see #MODE_DISABLED
     * @see #MODE_EXPLICIT
     * @see #MODE_COPY_FROM_METADATA
     * @see #MODE_DEFAULT
     * @see #setTilingMode
     * @see #getTilingMode
     */
    protected int tilingMode = MODE_COPY_FROM_METADATA;

    /**
     * An array of preferred tile size range pairs.  The default value
     * is <code>null</code>, which indicates that there are no
     * preferred sizes.  If the value is non-<code>null</code>, it
     * must have an even length of at least two.
     *
     * <p> Subclasses that do not support writing tiles may ignore
     * this value.
     *
     * <p>
     *  首选拼贴大小范围对的数组。默认值为<code> null </code>,表示没有首选大小。如果值为非<code> null </code>,则它的长度必须至少为2。
     * 
     *  <p>不支持写入图块的子类可能会忽略此值。
     * 
     * 
     * @see #getPreferredTileSizes
     */
    protected Dimension[] preferredTileSizes = null;

    /**
     * A <code>boolean</code> that is <code>true</code> if tiling
     * parameters have been specified.
     *
     * <p> Subclasses that do not support writing tiles may ignore
     * this value.
     * <p>
     * 如果指定了平铺参数,则<code> boolean </code>是<code> true </code>。
     * 
     *  <p>不支持写入图块的子类可能会忽略此值。
     * 
     */
    protected boolean tilingSet = false;

    /**
     * The width of each tile if tiling has been set, or 0 otherwise.
     *
     * <p> Subclasses that do not support tiling may ignore this
     * value.
     * <p>
     *  如果已设置平铺,则每个平铺的宽度,否则为0。
     * 
     *  <p>不支持平铺的子类可能会忽略此值。
     * 
     */
    protected int tileWidth = 0;

    /**
     * The height of each tile if tiling has been set, or 0 otherwise.
     * The initial value is <code>0</code>.
     *
     * <p> Subclasses that do not support tiling may ignore this
     * value.
     * <p>
     *  如果平铺已设置,则每个平铺的高度,否则为0。初始值为<code> 0 </code>。
     * 
     *  <p>不支持平铺的子类可能会忽略此值。
     * 
     */
    protected int tileHeight = 0;

    /**
     * A <code>boolean</code> that is <code>true</code> if this
     * <code>ImageWriteParam</code> allows tiling grid offset
     * parameters to be set.  By default, the value is
     * <code>false</code>.  Subclasses must set the value manually.
     *
     * <p> Subclasses that do not support writing tiles, or that
     * support writing but not offsetting tiles must ensure that this
     * value is set to <code>false</code>.
     * <p>
     *  如果此<code> ImageWriteParam </code>允许设置平铺网格偏移参数,则<code> boolean </code>,即<code> true </code>。
     * 默认情况下,值为<code> false </code>。子类必须手动设置值。
     * 
     *  <p>不支持写入磁贴或支持写入但不偏移磁贴的子类必须确保此值设置为<code> false </code>。
     * 
     */
    protected boolean canOffsetTiles = false;

    /**
     * The amount by which the tile grid origin should be offset
     * horizontally from the image origin if tiling has been set,
     * or 0 otherwise.  The initial value is <code>0</code>.
     *
     * <p> Subclasses that do not support offsetting tiles may ignore
     * this value.
     * <p>
     *  如果平铺已设置,拼贴网格原点应从图像原点水平偏移的量,否则为0。初始值为<code> 0 </code>。
     * 
     *  <p>不支持抵消图块的子类可能会忽略此值。
     * 
     */
    protected int tileGridXOffset = 0;

    /**
     * The amount by which the tile grid origin should be offset
     * vertically from the image origin if tiling has been set,
     * or 0 otherwise.  The initial value is <code>0</code>.
     *
     * <p> Subclasses that do not support offsetting tiles may ignore
     * this value.
     * <p>
     *  如果平铺已设置,拼贴网格原点应从图像原点垂直偏移的量,否则为0。初始值为<code> 0 </code>。
     * 
     *  <p>不支持抵消图块的子类可能会忽略此值。
     * 
     */
    protected int tileGridYOffset = 0;

    /**
     * A <code>boolean</code> that is <code>true</code> if this
     * <code>ImageWriteParam</code> allows images to be written as a
     * progressive sequence of increasing quality passes.  By default,
     * the value is <code>false</code>.  Subclasses must set the value
     * manually.
     *
     * <p> Subclasses that do not support progressive encoding must
     * ensure that this value is set to <code>false</code>.
     * <p>
     * 如果此<code> ImageWriteParam </code>允许将图像以增加的质量通过的渐进顺序写入,则<code> boolean </code>是<code> true </code>。
     * 默认情况下,值为<code> false </code>。子类必须手动设置值。
     * 
     *  <p>不支持渐进编码的子类必须确保此值设置为<code> false </code>。
     * 
     */
    protected boolean canWriteProgressive = false;

    /**
     * The mode controlling progressive encoding, which must be set to
     * one of the four <code>MODE_*</code> values, except
     * <code>MODE_EXPLICIT</code>.  The default is
     * <code>MODE_COPY_FROM_METADATA</code>.
     *
     * <p> Subclasses that do not support progressive encoding may
     * ignore this value.
     *
     * <p>
     *  模式控制逐行编码,必须设置为除<code> MODE_EXPLICIT </code>之外的四个<code> MODE _ * </code>值之一。
     * 默认值为<code> MODE_COPY_FROM_METADATA </code>。
     * 
     *  <p>不支持渐进式编码的子类可能会忽略此值。
     * 
     * 
     * @see #MODE_DISABLED
     * @see #MODE_EXPLICIT
     * @see #MODE_COPY_FROM_METADATA
     * @see #MODE_DEFAULT
     * @see #setProgressiveMode
     * @see #getProgressiveMode
     */
    protected int progressiveMode = MODE_COPY_FROM_METADATA;

    /**
     * A <code>boolean</code> that is <code>true</code> if this writer
     * can write images using compression. By default, the value is
     * <code>false</code>.  Subclasses must set the value manually.
     *
     * <p> Subclasses that do not support compression must ensure that
     * this value is set to <code>false</code>.
     * <p>
     *  如果此写入程序可以使用压缩写入图像,则<code> boolean </code>为<code> true </code>。默认情况下,值为<code> false </code>。
     * 子类必须手动设置值。
     * 
     *  <p>不支持压缩的子类必须确保此值设置为<code> false </code>。
     * 
     */
    protected boolean canWriteCompressed = false;

    /**
     * The mode controlling compression settings, which must be set to
     * one of the four <code>MODE_*</code> values.  The default is
     * <code>MODE_COPY_FROM_METADATA</code>.
     *
     * <p> Subclasses that do not support compression may ignore this
     * value.
     *
     * <p>
     *  模式控制压缩设置,必须设置为四个<code> MODE _ * </code>值之一。默认值为<code> MODE_COPY_FROM_METADATA </code>。
     * 
     *  <p>不支持压缩的子类可能会忽略此值。
     * 
     * 
     * @see #MODE_DISABLED
     * @see #MODE_EXPLICIT
     * @see #MODE_COPY_FROM_METADATA
     * @see #MODE_DEFAULT
     * @see #setCompressionMode
     * @see #getCompressionMode
     */
    protected int compressionMode = MODE_COPY_FROM_METADATA;

    /**
     * An array of <code>String</code>s containing the names of the
     * available compression types.  Subclasses must set the value
     * manually.
     *
     * <p> Subclasses that do not support compression may ignore this
     * value.
     * <p>
     *  包含可用压缩类型名称的<code> String </code>数组。子类必须手动设置值。
     * 
     *  <p>不支持压缩的子类可能会忽略此值。
     * 
     */
    protected String[] compressionTypes = null;

    /**
     * A <code>String</code> containing the name of the current
     * compression type, or <code>null</code> if none is set.
     *
     * <p> Subclasses that do not support compression may ignore this
     * value.
     * <p>
     *  包含当前压缩类型名称的<code> String </code>,如果没有设置,则为<code> null </code>。
     * 
     * <p>不支持压缩的子类可能会忽略此值。
     * 
     */
    protected String compressionType = null;

    /**
     * A <code>float</code> containing the current compression quality
     * setting.  The initial value is <code>1.0F</code>.
     *
     * <p> Subclasses that do not support compression may ignore this
     * value.
     * <p>
     *  包含当前压缩质量设置的<code> float </code>。初始值为<code> 1.0F </code>。
     * 
     *  <p>不支持压缩的子类可能会忽略此值。
     * 
     */
    protected float compressionQuality = 1.0F;

    /**
     * A <code>Locale</code> to be used to localize compression type
     * names and quality descriptions, or <code>null</code> to use a
     * default <code>Locale</code>.  Subclasses must set the value
     * manually.
     * <p>
     *  用于本地化压缩类型名称和质量描述的<code> Locale </code>或<code> null </code>以使用默认的<code> Locale </code>。子类必须手动设置值。
     * 
     */
    protected Locale locale = null;

    /**
     * Constructs an empty <code>ImageWriteParam</code>.  It is up to
     * the subclass to set up the instance variables properly.
     * <p>
     *  构造一个空的<code> ImageWriteParam </code>。它是由子类正确设置实例变量。
     * 
     */
    protected ImageWriteParam() {}

    /**
     * Constructs an <code>ImageWriteParam</code> set to use a
     * given <code>Locale</code>.
     *
     * <p>
     *  构造一个<code> ImageWriteParam </code>设置为使用给定的<code> Locale </code>。
     * 
     * 
     * @param locale a <code>Locale</code> to be used to localize
     * compression type names and quality descriptions, or
     * <code>null</code>.
     */
    public ImageWriteParam(Locale locale) {
        this.locale = locale;
    }

    // Return a deep copy of the array
    private static Dimension[] clonePreferredTileSizes(Dimension[] sizes) {
        if (sizes == null) {
            return null;
        }
        Dimension[] temp = new Dimension[sizes.length];
        for (int i = 0; i < sizes.length; i++) {
            temp[i] = new Dimension(sizes[i]);
        }
        return temp;
    }

    /**
     * Returns the currently set <code>Locale</code>, or
     * <code>null</code> if only a default <code>Locale</code> is
     * supported.
     *
     * <p>
     *  如果仅支持默认的<code> Locale </code>,则返回当前设置的<code> Locale </code>或<code> null </code>。
     * 
     * 
     * @return the current <code>Locale</code>, or <code>null</code>.
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Returns <code>true</code> if the writer can perform tiling
     * while writing.  If this method returns <code>false</code>, then
     * <code>setTiling</code> will throw an
     * <code>UnsupportedOperationException</code>.
     *
     * <p>
     *  如果写入器在写入时可以执行平铺,则返回<code> true </code>。
     * 如果此方法返回<code> false </code>,则<code> setTiling </code>会抛出<code> UnsupportedOperationException </code>。
     *  如果写入器在写入时可以执行平铺,则返回<code> true </code>。
     * 
     * 
     * @return <code>true</code> if the writer supports tiling.
     *
     * @see #canOffsetTiles()
     * @see #setTiling(int, int, int, int)
     */
    public boolean canWriteTiles() {
        return canWriteTiles;
    }

    /**
     * Returns <code>true</code> if the writer can perform tiling with
     * non-zero grid offsets while writing.  If this method returns
     * <code>false</code>, then <code>setTiling</code> will throw an
     * <code>UnsupportedOperationException</code> if the grid offset
     * arguments are not both zero.  If <code>canWriteTiles</code>
     * returns <code>false</code>, this method will return
     * <code>false</code> as well.
     *
     * <p>
     *  如果写入者在写入时可以执行具有非零栅格偏移的平铺,则返回<code> true </code>。
     * 如果此方法返回<code> false </code>,则如果网格偏移参数不都为零,则<code> setTiling </code>将抛出<code> UnsupportedOperationExce
     * ption </code>。
     *  如果写入者在写入时可以执行具有非零栅格偏移的平铺,则返回<code> true </code>。
     * 如果<code> canWriteTiles </code>返回<code> false </code>,此方法也将返回<code> false </code>。
     * 
     * 
     * @return <code>true</code> if the writer supports non-zero tile
     * offsets.
     *
     * @see #canWriteTiles()
     * @see #setTiling(int, int, int, int)
     */
    public boolean canOffsetTiles() {
        return canOffsetTiles;
    }

    /**
     * Determines whether the image will be tiled in the output
     * stream and, if it will, how the tiling parameters will be
     * determined.  The modes are interpreted as follows:
     *
     * <ul>
     *
     * <li><code>MODE_DISABLED</code> - The image will not be tiled.
     * <code>setTiling</code> will throw an
     * <code>IllegalStateException</code>.
     *
     * <li><code>MODE_DEFAULT</code> - The image will be tiled using
     * default parameters.  <code>setTiling</code> will throw an
     * <code>IllegalStateException</code>.
     *
     * <li><code>MODE_EXPLICIT</code> - The image will be tiled
     * according to parameters given in the {@link #setTiling setTiling}
     * method.  Any previously set tiling parameters are discarded.
     *
     * <li><code>MODE_COPY_FROM_METADATA</code> - The image will
     * conform to the metadata object passed in to a write.
     * <code>setTiling</code> will throw an
     * <code>IllegalStateException</code>.
     *
     * </ul>
     *
     * <p>
     * 确定图像是否将平铺在输出流中,如果将,将如何确定平铺参数。模式解释如下：
     * 
     * <ul>
     * 
     *  <li> <code> MODE_DISABLED </code>  - 图片不会被平铺。
     *  <code> setTiling </code>会抛出一个<code> IllegalStateException </code>。
     * 
     *  <li> <code> MODE_DEFAULT </code>  - 图片将使用默认参数进行平铺。
     *  <code> setTiling </code>会抛出一个<code> IllegalStateException </code>。
     * 
     *  <li> <code> MODE_EXPLICIT </code>  - 图片将根据{@link #setTiling setTiling}方法中给出的参数进行平铺。
     * 任何先前设置的平铺参数都将被丢弃。
     * 
     *  <li> <code> MODE_COPY_FROM_METADATA </code>  - 图像将符合传递到写入的元数据对象。
     *  <code> setTiling </code>会抛出一个<code> IllegalStateException </code>。
     * 
     * </ul>
     * 
     * 
     * @param mode The mode to use for tiling.
     *
     * @exception UnsupportedOperationException if
     * <code>canWriteTiles</code> returns <code>false</code>.
     * @exception IllegalArgumentException if <code>mode</code> is not
     * one of the modes listed above.
     *
     * @see #setTiling
     * @see #getTilingMode
     */
    public void setTilingMode(int mode) {
        if (canWriteTiles() == false) {
            throw new UnsupportedOperationException("Tiling not supported!");
        }
        if (mode < MODE_DISABLED || mode > MAX_MODE) {
            throw new IllegalArgumentException("Illegal value for mode!");
        }
        this.tilingMode = mode;
        if (mode == MODE_EXPLICIT) {
            unsetTiling();
        }
    }

    /**
     * Returns the current tiling mode, if tiling is supported.
     * Otherwise throws an <code>UnsupportedOperationException</code>.
     *
     * <p>
     *  如果支持平铺,则返回当前平铺模式。否则会抛出<code> UnsupportedOperationException </code>。
     * 
     * 
     * @return the current tiling mode.
     *
     * @exception UnsupportedOperationException if
     * <code>canWriteTiles</code> returns <code>false</code>.
     *
     * @see #setTilingMode
     */
    public int getTilingMode() {
        if (!canWriteTiles()) {
            throw new UnsupportedOperationException("Tiling not supported");
        }
        return tilingMode;
    }

    /**
     * Returns an array of <code>Dimension</code>s indicating the
     * legal size ranges for tiles as they will be encoded in the
     * output file or stream.  The returned array is a copy.
     *
     * <p> The information is returned as a set of pairs; the first
     * element of a pair contains an (inclusive) minimum width and
     * height, and the second element contains an (inclusive) maximum
     * width and height.  Together, each pair defines a valid range of
     * sizes.  To specify a fixed size, use the same width and height
     * for both elements.  To specify an arbitrary range, a value of
     * <code>null</code> is used in place of an actual array of
     * <code>Dimension</code>s.
     *
     * <p> If no array is specified on the constructor, but tiling is
     * allowed, then this method returns <code>null</code>.
     *
     * <p>
     *  返回一个<code> Dimension </code>数组,指示图块的法律大小范围,因为它们将在输出文件或流中编码。返回的数组是一个副本。
     * 
     * <p>信息作为一组对返回;一对中的第一元素包含(包括)最小宽度和高度,第二元素包含(包括)最大宽度和高度。在一起,每对定义一个有效的尺寸范围。要指定固定大小,请为这两个元素使用相同的宽度和高度。
     * 要指定任意范围,使用<code> null </code>的值来代替<code> Dimension </code> s的实际数组。
     * 
     *  <p>如果在构造函数中未指定数组,但允许进行平铺,则此方法返回<code> null </code>。
     * 
     * 
     * @exception UnsupportedOperationException if the plug-in does
     * not support tiling.
     *
     * @return an array of <code>Dimension</code>s with an even length
     * of at least two, or <code>null</code>.
     */
    public Dimension[] getPreferredTileSizes() {
        if (!canWriteTiles()) {
            throw new UnsupportedOperationException("Tiling not supported");
        }
        return clonePreferredTileSizes(preferredTileSizes);
    }

    /**
     * Specifies that the image should be tiled in the output stream.
     * The <code>tileWidth</code> and <code>tileHeight</code>
     * parameters specify the width and height of the tiles in the
     * file.  If the tile width or height is greater than the width or
     * height of the image, the image is not tiled in that dimension.
     *
     * <p> If <code>canOffsetTiles</code> returns <code>false</code>,
     * then the <code>tileGridXOffset</code> and
     * <code>tileGridYOffset</code> parameters must be zero.
     *
     * <p>
     *  指定图像应平铺在输出流中。 <code> tileWidth </code>和<code> tileHeight </code>参数指定文件中图块的宽度和高度。
     * 如果图块宽度或高度大于图片的宽度或高度,则图片不会平铺在该尺寸中。
     * 
     *  <p>如果<code> canOffsetTiles </code>返回<code> false </code>,则<code> tileGridXOffset </code>和<code> tile
     * GridYOffset </code>参数必须为零。
     * 
     * 
     * @param tileWidth the width of each tile.
     * @param tileHeight the height of each tile.
     * @param tileGridXOffset the horizontal offset of the tile grid.
     * @param tileGridYOffset the vertical offset of the tile grid.
     *
     * @exception UnsupportedOperationException if the plug-in does not
     * support tiling.
     * @exception IllegalStateException if the tiling mode is not
     * <code>MODE_EXPLICIT</code>.
     * @exception UnsupportedOperationException if the plug-in does not
     * support grid offsets, and the grid offsets are not both zero.
     * @exception IllegalArgumentException if the tile size is not
     * within one of the allowable ranges returned by
     * <code>getPreferredTileSizes</code>.
     * @exception IllegalArgumentException if <code>tileWidth</code>
     * or <code>tileHeight</code> is less than or equal to 0.
     *
     * @see #canWriteTiles
     * @see #canOffsetTiles
     * @see #getTileWidth()
     * @see #getTileHeight()
     * @see #getTileGridXOffset()
     * @see #getTileGridYOffset()
     */
    public void setTiling(int tileWidth,
                          int tileHeight,
                          int tileGridXOffset,
                          int tileGridYOffset) {
        if (!canWriteTiles()) {
            throw new UnsupportedOperationException("Tiling not supported!");
        }
        if (getTilingMode() != MODE_EXPLICIT) {
            throw new IllegalStateException("Tiling mode not MODE_EXPLICIT!");
        }
        if (tileWidth <= 0 || tileHeight <= 0) {
            throw new IllegalArgumentException
                ("tile dimensions are non-positive!");
        }
        boolean tilesOffset = (tileGridXOffset != 0) || (tileGridYOffset != 0);
        if (!canOffsetTiles() && tilesOffset) {
            throw new UnsupportedOperationException("Can't offset tiles!");
        }
        if (preferredTileSizes != null) {
            boolean ok = true;
            for (int i = 0; i < preferredTileSizes.length; i += 2) {
                Dimension min = preferredTileSizes[i];
                Dimension max = preferredTileSizes[i+1];
                if ((tileWidth < min.width) ||
                    (tileWidth > max.width) ||
                    (tileHeight < min.height) ||
                    (tileHeight > max.height)) {
                    ok = false;
                    break;
                }
            }
            if (!ok) {
                throw new IllegalArgumentException("Illegal tile size!");
            }
        }

        this.tilingSet = true;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.tileGridXOffset = tileGridXOffset;
        this.tileGridYOffset = tileGridYOffset;
    }

    /**
     * Removes any previous tile grid parameters specified by calls to
     * <code>setTiling</code>.
     *
     * <p> The default implementation sets the instance variables
     * <code>tileWidth</code>, <code>tileHeight</code>,
     * <code>tileGridXOffset</code>, and
     * <code>tileGridYOffset</code> to <code>0</code>.
     *
     * <p>
     *  删除通过调用<code> setTiling </code>指定的任何以前的拼贴网格参数。
     * 
     *  <p>默认实现将实例变量<code> tileWidth </code>,<code> tileHeight </code>,<code> tileGridXOffset </code>和<code>
     *  tileGridYOffset </code> 0 </code>。
     * 
     * 
     * @exception UnsupportedOperationException if the plug-in does not
     * support tiling.
     * @exception IllegalStateException if the tiling mode is not
     * <code>MODE_EXPLICIT</code>.
     *
     * @see #setTiling(int, int, int, int)
     */
    public void unsetTiling() {
        if (!canWriteTiles()) {
            throw new UnsupportedOperationException("Tiling not supported!");
        }
        if (getTilingMode() != MODE_EXPLICIT) {
            throw new IllegalStateException("Tiling mode not MODE_EXPLICIT!");
        }
        this.tilingSet = false;
        this.tileWidth = 0;
        this.tileHeight = 0;
        this.tileGridXOffset = 0;
        this.tileGridYOffset = 0;
    }

    /**
     * Returns the width of each tile in an image as it will be
     * written to the output stream.  If tiling parameters have not
     * been set, an <code>IllegalStateException</code> is thrown.
     *
     * <p>
     *  返回图像中每个图块的宽度,因为它将被写入输出流。如果未设置平铺参数,则会抛出<code> IllegalStateException </code>。
     * 
     * 
     * @return the tile width to be used for encoding.
     *
     * @exception UnsupportedOperationException if the plug-in does not
     * support tiling.
     * @exception IllegalStateException if the tiling mode is not
     * <code>MODE_EXPLICIT</code>.
     * @exception IllegalStateException if the tiling parameters have
     * not been set.
     *
     * @see #setTiling(int, int, int, int)
     * @see #getTileHeight()
     */
    public int getTileWidth() {
        if (!canWriteTiles()) {
            throw new UnsupportedOperationException("Tiling not supported!");
        }
        if (getTilingMode() != MODE_EXPLICIT) {
            throw new IllegalStateException("Tiling mode not MODE_EXPLICIT!");
        }
        if (!tilingSet) {
            throw new IllegalStateException("Tiling parameters not set!");
        }
        return tileWidth;
    }

    /**
     * Returns the height of each tile in an image as it will be written to
     * the output stream.  If tiling parameters have not
     * been set, an <code>IllegalStateException</code> is thrown.
     *
     * <p>
     * 返回图像中每个图块的高度,因为它将被写入输出流。如果未设置平铺参数,则会抛出<code> IllegalStateException </code>。
     * 
     * 
     * @return the tile height to be used for encoding.
     *
     * @exception UnsupportedOperationException if the plug-in does not
     * support tiling.
     * @exception IllegalStateException if the tiling mode is not
     * <code>MODE_EXPLICIT</code>.
     * @exception IllegalStateException if the tiling parameters have
     * not been set.
     *
     * @see #setTiling(int, int, int, int)
     * @see #getTileWidth()
     */
    public int getTileHeight() {
        if (!canWriteTiles()) {
            throw new UnsupportedOperationException("Tiling not supported!");
        }
        if (getTilingMode() != MODE_EXPLICIT) {
            throw new IllegalStateException("Tiling mode not MODE_EXPLICIT!");
        }
        if (!tilingSet) {
            throw new IllegalStateException("Tiling parameters not set!");
        }
        return tileHeight;
    }

    /**
     * Returns the horizontal tile grid offset of an image as it will
     * be written to the output stream.  If tiling parameters have not
     * been set, an <code>IllegalStateException</code> is thrown.
     *
     * <p>
     *  返回图像的水平平铺网格偏移,因为它将被写入输出流。如果未设置平铺参数,则会抛出<code> IllegalStateException </code>。
     * 
     * 
     * @return the tile grid X offset to be used for encoding.
     *
     * @exception UnsupportedOperationException if the plug-in does not
     * support tiling.
     * @exception IllegalStateException if the tiling mode is not
     * <code>MODE_EXPLICIT</code>.
     * @exception IllegalStateException if the tiling parameters have
     * not been set.
     *
     * @see #setTiling(int, int, int, int)
     * @see #getTileGridYOffset()
     */
    public int getTileGridXOffset() {
        if (!canWriteTiles()) {
            throw new UnsupportedOperationException("Tiling not supported!");
        }
        if (getTilingMode() != MODE_EXPLICIT) {
            throw new IllegalStateException("Tiling mode not MODE_EXPLICIT!");
        }
        if (!tilingSet) {
            throw new IllegalStateException("Tiling parameters not set!");
        }
        return tileGridXOffset;
    }

    /**
     * Returns the vertical tile grid offset of an image as it will
     * be written to the output stream.  If tiling parameters have not
     * been set, an <code>IllegalStateException</code> is thrown.
     *
     * <p>
     *  返回图像的垂直平铺网格偏移,因为它将被写入输出流。如果未设置平铺参数,则会抛出<code> IllegalStateException </code>。
     * 
     * 
     * @return the tile grid Y offset to be used for encoding.
     *
     * @exception UnsupportedOperationException if the plug-in does not
     * support tiling.
     * @exception IllegalStateException if the tiling mode is not
     * <code>MODE_EXPLICIT</code>.
     * @exception IllegalStateException if the tiling parameters have
     * not been set.
     *
     * @see #setTiling(int, int, int, int)
     * @see #getTileGridXOffset()
     */
    public int getTileGridYOffset() {
        if (!canWriteTiles()) {
            throw new UnsupportedOperationException("Tiling not supported!");
        }
        if (getTilingMode() != MODE_EXPLICIT) {
            throw new IllegalStateException("Tiling mode not MODE_EXPLICIT!");
        }
        if (!tilingSet) {
            throw new IllegalStateException("Tiling parameters not set!");
        }
        return tileGridYOffset;
    }

    /**
     * Returns <code>true</code> if the writer can write out images
     * as a series of passes of progressively increasing quality.
     *
     * <p>
     *  如果写入器可以将图像作为一系列逐渐提高质量的传递写入,则返回<code> true </code>。
     * 
     * 
     * @return <code>true</code> if the writer supports progressive
     * encoding.
     *
     * @see #setProgressiveMode
     * @see #getProgressiveMode
     */
    public boolean canWriteProgressive() {
        return canWriteProgressive;
    }

    /**
     * Specifies that the writer is to write the image out in a
     * progressive mode such that the stream will contain a series of
     * scans of increasing quality.  If progressive encoding is not
     * supported, an <code>UnsupportedOperationException</code> will
     * be thrown.
     *
     * <p>  The mode argument determines how
     * the progression parameters are chosen, and must be either
     * <code>MODE_DISABLED</code>,
     * <code>MODE_COPY_FROM_METADATA</code>, or
     * <code>MODE_DEFAULT</code>.  Otherwise an
     * <code>IllegalArgumentException</code> is thrown.
     *
     * <p> The modes are interpreted as follows:
     *
     * <ul>
     *   <li><code>MODE_DISABLED</code> - No progression.  Use this to
     *   turn off progression.
     *
     *   <li><code>MODE_COPY_FROM_METADATA</code> - The output image
     *   will use whatever progression parameters are found in the
     *   metadata objects passed into the writer.
     *
     *   <li><code>MODE_DEFAULT</code> - The image will be written
     *   progressively, with parameters chosen by the writer.
     * </ul>
     *
     * <p> The default is <code>MODE_COPY_FROM_METADATA</code>.
     *
     * <p>
     *  指定写入器以渐进模式写出图像,使得流将包含质量增加的一系列扫描。如果不支持渐进式编码,将抛出<code> UnsupportedOperationException </code>。
     * 
     *  <p> mode参数决定如何选择渐进参数,并且必须是<code> MODE_DISABLED </code>,<code> MODE_COPY_FROM_METADATA </code>或<code>
     *  MODE_DEFAULT </code>。
     * 否则抛出<code> IllegalArgumentException </code>。
     * 
     *  <p>这些模式解释如下：
     * 
     * <ul>
     *  <li> <code> MODE_DISABLED </code>  - 无进展。使用此选项可关闭渐进。
     * 
     *  <li> <code> MODE_COPY_FROM_METADATA </code>  - 输出图像将使用传递到编写器的元数据对象中找到的任何渐进参数。
     * 
     * <li> <code> MODE_DEFAULT </code>  - 图像将逐步编写,参数由编写者选择。
     * </ul>
     * 
     *  <p>默认为<code> MODE_COPY_FROM_METADATA </code>。
     * 
     * 
     * @param mode The mode for setting progression in the output
     * stream.
     *
     * @exception UnsupportedOperationException if the writer does not
     * support progressive encoding.
     * @exception IllegalArgumentException if <code>mode</code> is not
     * one of the modes listed above.
     *
     * @see #getProgressiveMode
     */
    public void setProgressiveMode(int mode) {
        if (!canWriteProgressive()) {
            throw new UnsupportedOperationException(
                "Progressive output not supported");
        }
        if (mode < MODE_DISABLED || mode > MAX_MODE) {
            throw new IllegalArgumentException("Illegal value for mode!");
        }
        if (mode == MODE_EXPLICIT) {
            throw new IllegalArgumentException(
                "MODE_EXPLICIT not supported for progressive output");
        }
        this.progressiveMode = mode;
    }

    /**
     * Returns the current mode for writing the stream in a
     * progressive manner.
     *
     * <p>
     *  返回以渐进方式写入流的当前模式。
     * 
     * 
     * @return the current mode for progressive encoding.
     *
     * @exception UnsupportedOperationException if the writer does not
     * support progressive encoding.
     *
     * @see #setProgressiveMode
     */
    public int getProgressiveMode() {
        if (!canWriteProgressive()) {
            throw new UnsupportedOperationException
                ("Progressive output not supported");
        }
        return progressiveMode;
    }

    /**
     * Returns <code>true</code> if this writer supports compression.
     *
     * <p>
     *  如果此编写器支持压缩,则返回<code> true </code>。
     * 
     * 
     * @return <code>true</code> if the writer supports compression.
     */
    public boolean canWriteCompressed() {
        return canWriteCompressed;
    }

    /**
     * Specifies whether compression is to be performed, and if so how
     * compression parameters are to be determined.  The <code>mode</code>
     * argument must be one of the four modes, interpreted as follows:
     *
     * <ul>
     *   <li><code>MODE_DISABLED</code> - If the mode is set to
     *   <code>MODE_DISABLED</code>, methods that query or modify the
     *   compression type or parameters will throw an
     *   <code>IllegalStateException</code> (if compression is
     *   normally supported by the plug-in). Some writers, such as JPEG,
     *   do not normally offer uncompressed output. In this case, attempting
     *   to set the mode to <code>MODE_DISABLED</code> will throw an
     *   <code>UnsupportedOperationException</code> and the mode will not be
     *   changed.
     *
     *   <li><code>MODE_EXPLICIT</code> - Compress using the
     *   compression type and quality settings specified in this
     *   <code>ImageWriteParam</code>.  Any previously set compression
     *   parameters are discarded.
     *
     *   <li><code>MODE_COPY_FROM_METADATA</code> - Use whatever
     *   compression parameters are specified in metadata objects
     *   passed in to the writer.
     *
     *   <li><code>MODE_DEFAULT</code> - Use default compression
     *   parameters.
     * </ul>
     *
     * <p> The default is <code>MODE_COPY_FROM_METADATA</code>.
     *
     * <p>
     *  指定是否要执行压缩,如果是,则如何确定压缩参数。 <code> mode </code>参数必须是四种模式之一,解释如下：
     * 
     * <ul>
     *  <li> <code> MODE_DISABLED </code>  - 如果模式设置为<code> MODE_DISABLED </code>,查询或修改压缩类型或参数的方法将抛出<code> Il
     * legalStateException </code>压缩通常由插件支持)。
     * 一些作家,如JPEG,通常不提供未压缩的输出。
     * 在这种情况下,尝试将模式设置为<code> MODE_DISABLED </code>会抛出一个<code> UnsupportedOperationException </code>,并且模式不会改变
     * 。
     * 一些作家,如JPEG,通常不提供未压缩的输出。
     * 
     *  <li> <code> MODE_EXPLICIT </code>  - 使用此<code> ImageWriteParam </code>中指定的压缩类型和质量设置进行压缩。
     * 丢弃任何先前设置的压缩参数。
     * 
     *  <li> <code> MODE_COPY_FROM_METADATA </code>  - 使用在传递到编写器的元数据对象中指定的任何压缩参数。
     * 
     *  <li> <code> MODE_DEFAULT </code>  - 使用默认压缩参数。
     * </ul>
     * 
     *  <p>默认为<code> MODE_COPY_FROM_METADATA </code>。
     * 
     * 
     * @param mode The mode for setting compression in the output
     * stream.
     *
     * @exception UnsupportedOperationException if the writer does not
     * support compression, or does not support the requested mode.
     * @exception IllegalArgumentException if <code>mode</code> is not
     * one of the modes listed above.
     *
     * @see #getCompressionMode
     */
    public void setCompressionMode(int mode) {
        if (!canWriteCompressed()) {
            throw new UnsupportedOperationException(
                "Compression not supported.");
        }
        if (mode < MODE_DISABLED || mode > MAX_MODE) {
            throw new IllegalArgumentException("Illegal value for mode!");
        }
        this.compressionMode = mode;
        if (mode == MODE_EXPLICIT) {
            unsetCompression();
        }
    }

    /**
     * Returns the current compression mode, if compression is
     * supported.
     *
     * <p>
     * 返回当前压缩模式,如果支持压缩。
     * 
     * 
     * @return the current compression mode.
     *
     * @exception UnsupportedOperationException if the writer does not
     * support compression.
     *
     * @see #setCompressionMode
     */
    public int getCompressionMode() {
        if (!canWriteCompressed()) {
            throw new UnsupportedOperationException(
                "Compression not supported.");
        }
        return compressionMode;
    }

    /**
     * Returns a list of available compression types, as an array or
     * <code>String</code>s, or <code>null</code> if a compression
     * type may not be chosen using these interfaces.  The array
     * returned is a copy.
     *
     * <p> If the writer only offers a single, mandatory form of
     * compression, it is not necessary to provide any named
     * compression types.  Named compression types should only be
     * used where the user is able to make a meaningful choice
     * between different schemes.
     *
     * <p> The default implementation checks if compression is
     * supported and throws an
     * <code>UnsupportedOperationException</code> if not.  Otherwise,
     * it returns a clone of the <code>compressionTypes</code>
     * instance variable if it is non-<code>null</code>, or else
     * returns <code>null</code>.
     *
     * <p>
     *  如果不能使用这些接口选择压缩类型,则返回可用压缩类型的列表,如数组或<code> String </code> s或<code> null </code>。返回的数组是一个副本。
     * 
     *  <p>如果编写器只提供单一的强制性压缩形式,则无需提供任何命名的压缩类型。仅当用户能够在不同方案之间进行有意义的选择时,才应使用命名压缩类型。
     * 
     *  <p>默认实现检查是否支持压缩,如果不支持,则会抛出<code> UnsupportedOperationException </code>。
     * 否则,如果它是非<code> null </code>,则返回<code> compressionTypes </code>实例变量的克隆,否则返回<code> null </code>。
     * 
     * 
     * @return an array of <code>String</code>s containing the
     * (non-localized) names of available compression types, or
     * <code>null</code>.
     *
     * @exception UnsupportedOperationException if the writer does not
     * support compression.
     */
    public String[] getCompressionTypes() {
        if (!canWriteCompressed()) {
            throw new UnsupportedOperationException(
                "Compression not supported");
        }
        if (compressionTypes == null) {
            return null;
        }
        return (String[])compressionTypes.clone();
    }

    /**
     * Sets the compression type to one of the values indicated by
     * <code>getCompressionTypes</code>.  If a value of
     * <code>null</code> is passed in, any previous setting is
     * removed.
     *
     * <p> The default implementation checks whether compression is
     * supported and the compression mode is
     * <code>MODE_EXPLICIT</code>.  If so, it calls
     * <code>getCompressionTypes</code> and checks if
     * <code>compressionType</code> is one of the legal values.  If it
     * is, the <code>compressionType</code> instance variable is set.
     * If <code>compressionType</code> is <code>null</code>, the
     * instance variable is set without performing any checking.
     *
     * <p>
     *  将压缩类型设置为<code> getCompressionTypes </code>指示的值之一。如果传递<code> null </code>的值,则会删除任何先前的设置。
     * 
     *  <p>默认实现检查是否支持压缩,压缩模式是<code> MODE_EXPLICIT </code>。
     * 如果是这样,它调用<code> getCompressionTypes </code>并检查<code> compressionType </code>是否是合法值之一。
     * 如果是,则设置<code> compressionType </code>实例变量。
     * 如果<code> compressionType </code>是<code> null </code>,则设置实例变量而不执行任何检查。
     * 
     * 
     * @param compressionType one of the <code>String</code>s returned
     * by <code>getCompressionTypes</code>, or <code>null</code> to
     * remove any previous setting.
     *
     * @exception UnsupportedOperationException if the writer does not
     * support compression.
     * @exception IllegalStateException if the compression mode is not
     * <code>MODE_EXPLICIT</code>.
     * @exception UnsupportedOperationException if there are no
     * settable compression types.
     * @exception IllegalArgumentException if
     * <code>compressionType</code> is non-<code>null</code> but is not
     * one of the values returned by <code>getCompressionTypes</code>.
     *
     * @see #getCompressionTypes
     * @see #getCompressionType
     * @see #unsetCompression
     */
    public void setCompressionType(String compressionType) {
        if (!canWriteCompressed()) {
            throw new UnsupportedOperationException(
                "Compression not supported");
        }
        if (getCompressionMode() != MODE_EXPLICIT) {
            throw new IllegalStateException
                ("Compression mode not MODE_EXPLICIT!");
        }
        String[] legalTypes = getCompressionTypes();
        if (legalTypes == null) {
            throw new UnsupportedOperationException(
                "No settable compression types");
        }
        if (compressionType != null) {
            boolean found = false;
            if (legalTypes != null) {
                for (int i = 0; i < legalTypes.length; i++) {
                    if (compressionType.equals(legalTypes[i])) {
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                throw new IllegalArgumentException("Unknown compression type!");
            }
        }
        this.compressionType = compressionType;
    }

    /**
     * Returns the currently set compression type, or
     * <code>null</code> if none has been set.  The type is returned
     * as a <code>String</code> from among those returned by
     * <code>getCompressionTypes</code>.
     * If no compression type has been set, <code>null</code> is
     * returned.
     *
     * <p> The default implementation checks whether compression is
     * supported and the compression mode is
     * <code>MODE_EXPLICIT</code>.  If so, it returns the value of the
     * <code>compressionType</code> instance variable.
     *
     * <p>
     * 返回当前设置的压缩类型,如果没有设置,则返回<code> null </code>。
     * 类型作为<code> String </code>从<code> getCompressionTypes </code>返回的类型中返回。
     * 如果未设置压缩类型,则返回<code> null </code>。
     * 
     *  <p>默认实现检查是否支持压缩,压缩模式是<code> MODE_EXPLICIT </code>。如果是,则返回<code> compressionType </code>实例变量的值。
     * 
     * 
     * @return the current compression type as a <code>String</code>,
     * or <code>null</code> if no type is set.
     *
     * @exception UnsupportedOperationException if the writer does not
     * support compression.
     * @exception IllegalStateException if the compression mode is not
     * <code>MODE_EXPLICIT</code>.
     *
     * @see #setCompressionType
     */
    public String getCompressionType() {
        if (!canWriteCompressed()) {
            throw new UnsupportedOperationException(
                "Compression not supported.");
        }
        if (getCompressionMode() != MODE_EXPLICIT) {
            throw new IllegalStateException
                ("Compression mode not MODE_EXPLICIT!");
        }
        return compressionType;
    }

    /**
     * Removes any previous compression type and quality settings.
     *
     * <p> The default implementation sets the instance variable
     * <code>compressionType</code> to <code>null</code>, and the
     * instance variable <code>compressionQuality</code> to
     * <code>1.0F</code>.
     *
     * <p>
     *  删除任何先前的压缩类型和质量设置。
     * 
     *  <p>默认实现将实例变量<code> compressionType </code>设置为<code> null </code>,将实例变量<code> compressionQuality </code>
     * 设置为<code> 1.0F </code> 。
     * 
     * 
     * @exception UnsupportedOperationException if the plug-in does not
     * support compression.
     * @exception IllegalStateException if the compression mode is not
     * <code>MODE_EXPLICIT</code>.
     *
     * @see #setCompressionType
     * @see #setCompressionQuality
     */
    public void unsetCompression() {
        if (!canWriteCompressed()) {
            throw new UnsupportedOperationException(
                "Compression not supported");
        }
        if (getCompressionMode() != MODE_EXPLICIT) {
            throw new IllegalStateException
                ("Compression mode not MODE_EXPLICIT!");
        }
        this.compressionType = null;
        this.compressionQuality = 1.0F;
    }

    /**
     * Returns a localized version of the name of the current
     * compression type, using the <code>Locale</code> returned by
     * <code>getLocale</code>.
     *
     * <p> The default implementation checks whether compression is
     * supported and the compression mode is
     * <code>MODE_EXPLICIT</code>.  If so, if
     * <code>compressionType</code> is <code>non-null</code> the value
     * of <code>getCompressionType</code> is returned as a
     * convenience.
     *
     * <p>
     *  使用<code> getLocale </code>返回的<code> Locale </code>返回当前压缩类型的名称的本地化版本。
     * 
     *  <p>默认实现检查是否支持压缩,压缩模式是<code> MODE_EXPLICIT </code>。
     * 如果是,如果<code> compressionType </code>是<code>非空</code>,则返回<code> getCompressionType </code>的值,。
     * 
     * 
     * @return a <code>String</code> containing a localized version of
     * the name of the current compression type.
     *
     * @exception UnsupportedOperationException if the writer does not
     * support compression.
     * @exception IllegalStateException if the compression mode is not
     * <code>MODE_EXPLICIT</code>.
     * @exception IllegalStateException if no compression type is set.
     */
    public String getLocalizedCompressionTypeName() {
        if (!canWriteCompressed()) {
            throw new UnsupportedOperationException(
                "Compression not supported.");
        }
        if (getCompressionMode() != MODE_EXPLICIT) {
            throw new IllegalStateException
                ("Compression mode not MODE_EXPLICIT!");
        }
        if (getCompressionType() == null) {
            throw new IllegalStateException("No compression type set!");
        }
        return getCompressionType();
    }

    /**
     * Returns <code>true</code> if the current compression type
     * provides lossless compression.  If a plug-in provides only
     * one mandatory compression type, then this method may be
     * called without calling <code>setCompressionType</code> first.
     *
     * <p> If there are multiple compression types but none has
     * been set, an <code>IllegalStateException</code> is thrown.
     *
     * <p> The default implementation checks whether compression is
     * supported and the compression mode is
     * <code>MODE_EXPLICIT</code>.  If so, if
     * <code>getCompressionTypes()</code> is <code>null</code> or
     * <code>getCompressionType()</code> is non-<code>null</code>
     * <code>true</code> is returned as a convenience.
     *
     * <p>
     *  如果当前压缩类型提供无损压缩,则返回<code> true </code>。
     * 如果插件只提供一个强制压缩类型,则可以在不调用<code> setCompressionType </code>的情况下调用此方法。
     * 
     *  <p>如果有多个压缩类型,但没有设置,则会抛出<code> IllegalStateException </code>。
     * 
     * <p>默认实现检查是否支持压缩,压缩模式是<code> MODE_EXPLICIT </code>。
     * 如果是,如果<code> getCompressionTypes()</code>是<code> null </code>或<code> getCompressionType()</code>是非<code>
     *  null </code> <code> / code>作为方便返回。
     * <p>默认实现检查是否支持压缩,压缩模式是<code> MODE_EXPLICIT </code>。
     * 
     * 
     * @return <code>true</code> if the current compression type is
     * lossless.
     *
     * @exception UnsupportedOperationException if the writer does not
     * support compression.
     * @exception IllegalStateException if the compression mode is not
     * <code>MODE_EXPLICIT</code>.
     * @exception IllegalStateException if the set of legal
     * compression types is non-<code>null</code> and the current
     * compression type is <code>null</code>.
     */
    public boolean isCompressionLossless() {
        if (!canWriteCompressed()) {
            throw new UnsupportedOperationException(
                "Compression not supported");
        }
        if (getCompressionMode() != MODE_EXPLICIT) {
            throw new IllegalStateException
                ("Compression mode not MODE_EXPLICIT!");
        }
        if ((getCompressionTypes() != null) &&
            (getCompressionType() == null)) {
            throw new IllegalStateException("No compression type set!");
        }
        return true;
    }

    /**
     * Sets the compression quality to a value between <code>0</code>
     * and <code>1</code>.  Only a single compression quality setting
     * is supported by default; writers can provide extended versions
     * of <code>ImageWriteParam</code> that offer more control.  For
     * lossy compression schemes, the compression quality should
     * control the tradeoff between file size and image quality (for
     * example, by choosing quantization tables when writing JPEG
     * images).  For lossless schemes, the compression quality may be
     * used to control the tradeoff between file size and time taken
     * to perform the compression (for example, by optimizing row
     * filters and setting the ZLIB compression level when writing
     * PNG images).
     *
     * <p> A compression quality setting of 0.0 is most generically
     * interpreted as "high compression is important," while a setting of
     * 1.0 is most generically interpreted as "high image quality is
     * important."
     *
     * <p> If there are multiple compression types but none has been
     * set, an <code>IllegalStateException</code> is thrown.
     *
     * <p> The default implementation checks that compression is
     * supported, and that the compression mode is
     * <code>MODE_EXPLICIT</code>.  If so, if
     * <code>getCompressionTypes()</code> returns <code>null</code> or
     * <code>compressionType</code> is non-<code>null</code> it sets
     * the <code>compressionQuality</code> instance variable.
     *
     * <p>
     *  将压缩质量设置为<code> 0 </code>和<code> 1 </code>之间的值。
     * 默认情况下仅支持单个压缩质量设置;写者可以提供提供更多控制的扩展版本的<code> ImageWriteParam </code>。
     * 对于有损压缩方案,压缩质量应该控制文件大小和图像质量之间的折衷(例如,通过在写JPEG图像时选择量化表)。
     * 对于无损方案,压缩质量可以用于控制文件大小和执行压缩所花费的时间之间的折衷(例如,通过优化行过滤器和当写入PNG图像时设置ZLIB压缩级别)。
     * 
     *  <p> 0.0的压缩质量设置通常被解释为"高压缩很重要",而1.0的设置通常被解释为"高图像质量是重要的"。
     * 
     *  <p>如果有多个压缩类型,但没有设置,则会抛出<code> IllegalStateException </code>。
     * 
     * <p>默认实现检查是否支持压缩,压缩模式为<code> MODE_EXPLICIT </code>。
     * 如果是,如果<code> getCompressionTypes()</code>返回<code> null </code>或<code> compressionType </code>是非<code>
     *  null </code> </code>实例变量。
     * <p>默认实现检查是否支持压缩,压缩模式为<code> MODE_EXPLICIT </code>。
     * 
     * 
     * @param quality a <code>float</code> between <code>0</code>and
     * <code>1</code> indicating the desired quality level.
     *
     * @exception UnsupportedOperationException if the writer does not
     * support compression.
     * @exception IllegalStateException if the compression mode is not
     * <code>MODE_EXPLICIT</code>.
     * @exception IllegalStateException if the set of legal
     * compression types is non-<code>null</code> and the current
     * compression type is <code>null</code>.
     * @exception IllegalArgumentException if <code>quality</code> is
     * not between <code>0</code>and <code>1</code>, inclusive.
     *
     * @see #getCompressionQuality
     */
    public void setCompressionQuality(float quality) {
        if (!canWriteCompressed()) {
            throw new UnsupportedOperationException(
                "Compression not supported");
        }
        if (getCompressionMode() != MODE_EXPLICIT) {
            throw new IllegalStateException
                ("Compression mode not MODE_EXPLICIT!");
        }
        if (getCompressionTypes() != null && getCompressionType() == null) {
            throw new IllegalStateException("No compression type set!");
        }
        if (quality < 0.0F || quality > 1.0F) {
            throw new IllegalArgumentException("Quality out-of-bounds!");
        }
        this.compressionQuality = quality;
    }

    /**
     * Returns the current compression quality setting.
     *
     * <p> If there are multiple compression types but none has been
     * set, an <code>IllegalStateException</code> is thrown.
     *
     * <p> The default implementation checks that compression is
     * supported and that the compression mode is
     * <code>MODE_EXPLICIT</code>.  If so, if
     * <code>getCompressionTypes()</code> is <code>null</code> or
     * <code>getCompressionType()</code> is non-<code>null</code>, it
     * returns the value of the <code>compressionQuality</code>
     * instance variable.
     *
     * <p>
     *  返回当前压缩质量设置。
     * 
     *  <p>如果有多个压缩类型,但没有设置,则会抛出<code> IllegalStateException </code>。
     * 
     *  <p>默认实现检查是否支持压缩,压缩模式为<code> MODE_EXPLICIT </code>。
     * 如果是,如果<code> getCompressionTypes()</code>是<code> null </code>或<code> getCompressionType()</code>是非<code>
     *  null </code>的<code> compressionQuality </code>实例变量。
     *  <p>默认实现检查是否支持压缩,压缩模式为<code> MODE_EXPLICIT </code>。
     * 
     * 
     * @return the current compression quality setting.
     *
     * @exception UnsupportedOperationException if the writer does not
     * support compression.
     * @exception IllegalStateException if the compression mode is not
     * <code>MODE_EXPLICIT</code>.
     * @exception IllegalStateException if the set of legal
     * compression types is non-<code>null</code> and the current
     * compression type is <code>null</code>.
     *
     * @see #setCompressionQuality
     */
    public float getCompressionQuality() {
        if (!canWriteCompressed()) {
            throw new UnsupportedOperationException(
                "Compression not supported.");
        }
        if (getCompressionMode() != MODE_EXPLICIT) {
            throw new IllegalStateException
                ("Compression mode not MODE_EXPLICIT!");
        }
        if ((getCompressionTypes() != null) &&
            (getCompressionType() == null)) {
            throw new IllegalStateException("No compression type set!");
        }
        return compressionQuality;
    }


    /**
     * Returns a <code>float</code> indicating an estimate of the
     * number of bits of output data for each bit of input image data
     * at the given quality level.  The value will typically lie
     * between <code>0</code> and <code>1</code>, with smaller values
     * indicating more compression.  A special value of
     * <code>-1.0F</code> is used to indicate that no estimate is
     * available.
     *
     * <p> If there are multiple compression types but none has been set,
     * an <code>IllegalStateException</code> is thrown.
     *
     * <p> The default implementation checks that compression is
     * supported and the compression mode is
     * <code>MODE_EXPLICIT</code>.  If so, if
     * <code>getCompressionTypes()</code> is <code>null</code> or
     * <code>getCompressionType()</code> is non-<code>null</code>, and
     * <code>quality</code> is within bounds, it returns
     * <code>-1.0</code>.
     *
     * <p>
     *  返回指示在给定质量水平的输入图像数据的每个比特的输出数据的比特数的估计的<code> float </code>。
     * 该值通常在<code> 0 </code>和<code> 1 </code>之间,较小的值表示更多的压缩。 <code> -1.0F </code>的特殊值用于指示没有可用的估计。
     * 
     *  <p>如果有多个压缩类型,但没有设置,则会抛出<code> IllegalStateException </code>。
     * 
     * <p>默认实现检查是否支持压缩,压缩模式为<code> MODE_EXPLICIT </code>。
     * 如果是,如果<code> getCompressionTypes()</code>是<code> null </code>或<code> getCompressionType()</code>是非<code>
     *  null </code>质量</code>在范围内,它返回<code> -1.0 </code>。
     * <p>默认实现检查是否支持压缩,压缩模式为<code> MODE_EXPLICIT </code>。
     * 
     * 
     * @param quality the quality setting whose bit rate is to be
     * queried.
     *
     * @return an estimate of the compressed bit rate, or
     * <code>-1.0F</code> if no estimate is available.
     *
     * @exception UnsupportedOperationException if the writer does not
     * support compression.
     * @exception IllegalStateException if the compression mode is not
     * <code>MODE_EXPLICIT</code>.
     * @exception IllegalStateException if the set of legal
     * compression types is non-<code>null</code> and the current
     * compression type is <code>null</code>.
     * @exception IllegalArgumentException if <code>quality</code> is
     * not between <code>0</code>and <code>1</code>, inclusive.
     */
    public float getBitRate(float quality) {
        if (!canWriteCompressed()) {
            throw new UnsupportedOperationException(
                "Compression not supported.");
        }
        if (getCompressionMode() != MODE_EXPLICIT) {
            throw new IllegalStateException
                ("Compression mode not MODE_EXPLICIT!");
        }
        if ((getCompressionTypes() != null) &&
            (getCompressionType() == null)) {
            throw new IllegalStateException("No compression type set!");
        }
        if (quality < 0.0F || quality > 1.0F) {
            throw new IllegalArgumentException("Quality out-of-bounds!");
        }
        return -1.0F;
    }

    /**
     * Returns an array of <code>String</code>s that may be used along
     * with <code>getCompressionQualityValues</code> as part of a user
     * interface for setting or displaying the compression quality
     * level.  The <code>String</code> with index <code>i</code>
     * provides a description of the range of quality levels between
     * <code>getCompressionQualityValues[i]</code> and
     * <code>getCompressionQualityValues[i + 1]</code>.  Note that the
     * length of the array returned from
     * <code>getCompressionQualityValues</code> will always be one
     * greater than that returned from
     * <code>getCompressionQualityDescriptions</code>.
     *
     * <p> As an example, the strings "Good", "Better", and "Best"
     * could be associated with the ranges <code>[0, .33)</code>,
     * <code>[.33, .66)</code>, and <code>[.66, 1.0]</code>.  In this
     * case, <code>getCompressionQualityDescriptions</code> would
     * return <code>{ "Good", "Better", "Best" }</code> and
     * <code>getCompressionQualityValues</code> would return
     * <code>{ 0.0F, .33F, .66F, 1.0F }</code>.
     *
     * <p> If no descriptions are available, <code>null</code> is
     * returned.  If <code>null</code> is returned from
     * <code>getCompressionQualityValues</code>, this method must also
     * return <code>null</code>.
     *
     * <p> The descriptions should be localized for the
     * <code>Locale</code> returned by <code>getLocale</code>, if it
     * is non-<code>null</code>.
     *
     * <p> If there are multiple compression types but none has been set,
     * an <code>IllegalStateException</code> is thrown.
     *
     * <p> The default implementation checks that compression is
     * supported and that the compression mode is
     * <code>MODE_EXPLICIT</code>.  If so, if
     * <code>getCompressionTypes()</code> is <code>null</code> or
     * <code>getCompressionType()</code> is non-<code>null</code>, it
     * returns <code>null</code>.
     *
     * <p>
     *  返回可与<code> getCompressionQualityValues </code>一起使用的<code> String </code>的数组,作为用于设置或显示压缩质量级别的用户界面的一部分
     * 。
     * 具有索引<code> i </code>的<code> String </code>提供<code> getCompressionQualityValues [i] </code>和<code> get
     * CompressionQualityValues [i + 1] </code>。
     * 注意,从<code> getCompressionQualityValues </code>返回的数组的长度总是大于从<code> getCompressionQualityDescriptions </code>
     * 返回的长度。
     * 
     *  
     * 
     * <p>如果没有可用的描述,则返回<code> null </code>。
     * 如果从<code> getCompressionQualityValues </code>返回<code> null </code>,此方法还必须返回<code> null </code>。
     * 
     *  <p>对于<code> getLocale </code>返回的<code> Locale </code>,如果它是非<code> null </code>,则应对本描述进行本地化。
     * 
     *  <p>如果有多个压缩类型,但没有设置,则会抛出<code> IllegalStateException </code>。
     * 
     *  <p>默认实现检查是否支持压缩,压缩模式为<code> MODE_EXPLICIT </code>。
     * 如果是,如果<code> getCompressionTypes()</code>是<code> null </code>或<code> getCompressionType()</code>是非<code>
     *  null </code> > null </code>。
     * 
     * @return an array of <code>String</code>s containing localized
     * descriptions of the compression quality levels.
     *
     * @exception UnsupportedOperationException if the writer does not
     * support compression.
     * @exception IllegalStateException if the compression mode is not
     * <code>MODE_EXPLICIT</code>.
     * @exception IllegalStateException if the set of legal
     * compression types is non-<code>null</code> and the current
     * compression type is <code>null</code>.
     *
     * @see #getCompressionQualityValues
     */
    public String[] getCompressionQualityDescriptions() {
        if (!canWriteCompressed()) {
            throw new UnsupportedOperationException(
                "Compression not supported.");
        }
        if (getCompressionMode() != MODE_EXPLICIT) {
            throw new IllegalStateException
                ("Compression mode not MODE_EXPLICIT!");
        }
        if ((getCompressionTypes() != null) &&
            (getCompressionType() == null)) {
            throw new IllegalStateException("No compression type set!");
        }
        return null;
    }

    /**
     * Returns an array of <code>float</code>s that may be used along
     * with <code>getCompressionQualityDescriptions</code> as part of a user
     * interface for setting or displaying the compression quality
     * level.  See {@link #getCompressionQualityDescriptions
     * getCompressionQualityDescriptions} for more information.
     *
     * <p> If no descriptions are available, <code>null</code> is
     * returned.  If <code>null</code> is returned from
     * <code>getCompressionQualityDescriptions</code>, this method
     * must also return <code>null</code>.
     *
     * <p> If there are multiple compression types but none has been set,
     * an <code>IllegalStateException</code> is thrown.
     *
     * <p> The default implementation checks that compression is
     * supported and that the compression mode is
     * <code>MODE_EXPLICIT</code>.  If so, if
     * <code>getCompressionTypes()</code> is <code>null</code> or
     * <code>getCompressionType()</code> is non-<code>null</code>, it
     * returns <code>null</code>.
     *
     * <p>
     *  <p>默认实现检查是否支持压缩,压缩模式为<code> MODE_EXPLICIT </code>。
     * 
     * 
     * @return an array of <code>float</code>s indicating the
     * boundaries between the compression quality levels as described
     * by the <code>String</code>s from
     * <code>getCompressionQualityDescriptions</code>.
     *
     * @exception UnsupportedOperationException if the writer does not
     * support compression.
     * @exception IllegalStateException if the compression mode is not
     * <code>MODE_EXPLICIT</code>.
     * @exception IllegalStateException if the set of legal
     * compression types is non-<code>null</code> and the current
     * compression type is <code>null</code>.
     *
     * @see #getCompressionQualityDescriptions
     */
    public float[] getCompressionQualityValues() {
        if (!canWriteCompressed()) {
            throw new UnsupportedOperationException(
                "Compression not supported.");
        }
        if (getCompressionMode() != MODE_EXPLICIT) {
            throw new IllegalStateException
                ("Compression mode not MODE_EXPLICIT!");
        }
        if ((getCompressionTypes() != null) &&
            (getCompressionType() == null)) {
            throw new IllegalStateException("No compression type set!");
        }
        return null;
    }
}
