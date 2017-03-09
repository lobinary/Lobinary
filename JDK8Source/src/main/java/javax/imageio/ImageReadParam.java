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
import java.awt.image.BufferedImage;

/**
 * A class describing how a stream is to be decoded.  Instances of
 * this class or its subclasses are used to supply prescriptive
 * "how-to" information to instances of <code>ImageReader</code>.
 *
 * <p> An image encoded as part of a file or stream may be thought of
 * extending out in multiple dimensions: the spatial dimensions of
 * width and height, a number of bands, and a number of progressive
 * decoding passes.  This class allows a contiguous (hyper)rectangular
 * subarea of the image in all of these dimensions to be selected for
 * decoding.  Additionally, the spatial dimensions may be subsampled
 * discontinuously.  Finally, color and format conversions may be
 * specified by controlling the <code>ColorModel</code> and
 * <code>SampleModel</code> of the destination image, either by
 * providing a <code>BufferedImage</code> or by using an
 * <code>ImageTypeSpecifier</code>.
 *
 * <p> An <code>ImageReadParam</code> object is used to specify how an
 * image, or a set of images, will be converted on input from
 * a stream in the context of the Java Image I/O framework.  A plug-in for a
 * specific image format will return instances of
 * <code>ImageReadParam</code> from the
 * <code>getDefaultReadParam</code> method of its
 * <code>ImageReader</code> implementation.
 *
 * <p> The state maintained by an instance of
 * <code>ImageReadParam</code> is independent of any particular image
 * being decoded.  When actual decoding takes place, the values set in
 * the read param are combined with the actual properties of the image
 * being decoded from the stream and the destination
 * <code>BufferedImage</code> that will receive the decoded pixel
 * data.  For example, the source region set using
 * <code>setSourceRegion</code> will first be intersected with the
 * actual valid source area.  The result will be translated by the
 * value returned by <code>getDestinationOffset</code>, and the
 * resulting rectangle intersected with the actual valid destination
 * area to yield the destination area that will be written.
 *
 * <p> The parameters specified by an <code>ImageReadParam</code> are
 * applied to an image as follows.  First, if a rendering size has
 * been set by <code>setSourceRenderSize</code>, the entire decoded
 * image is rendered at the size given by
 * <code>getSourceRenderSize</code>.  Otherwise, the image has its
 * natural size given by <code>ImageReader.getWidth</code> and
 * <code>ImageReader.getHeight</code>.
 *
 * <p> Next, the image is clipped against the source region
 * specified by <code>getSourceXOffset</code>, <code>getSourceYOffset</code>,
 * <code>getSourceWidth</code>, and <code>getSourceHeight</code>.
 *
 * <p> The resulting region is then subsampled according to the
 * factors given in {@link IIOParam#setSourceSubsampling
 * IIOParam.setSourceSubsampling}.  The first pixel,
 * the number of pixels per row, and the number of rows all depend
 * on the subsampling settings.
 * Call the minimum X and Y coordinates of the resulting rectangle
 * (<code>minX</code>, <code>minY</code>), its width <code>w</code>
 * and its height <code>h</code>.
 *
 * <p> This rectangle is offset by
 * (<code>getDestinationOffset().x</code>,
 * <code>getDestinationOffset().y</code>) and clipped against the
 * destination bounds.  If no destination image has been set, the
 * destination is defined to have a width of
 * <code>getDestinationOffset().x</code> + <code>w</code>, and a
 * height of <code>getDestinationOffset().y</code> + <code>h</code> so
 * that all pixels of the source region may be written to the
 * destination.
 *
 * <p> Pixels that land, after subsampling, within the destination
 * image, and that are written in one of the progressive passes
 * specified by <code>getSourceMinProgressivePass</code> and
 * <code>getSourceNumProgressivePasses</code> are passed along to the
 * next step.
 *
 * <p> Finally, the source samples of each pixel are mapped into
 * destination bands according to the algorithm described in the
 * comment for <code>setDestinationBands</code>.
 *
 * <p> Plug-in writers may extend the functionality of
 * <code>ImageReadParam</code> by providing a subclass that implements
 * additional, plug-in specific interfaces.  It is up to the plug-in
 * to document what interfaces are available and how they are to be
 * used.  Readers will silently ignore any extended features of an
 * <code>ImageReadParam</code> subclass of which they are not aware.
 * Also, they may ignore any optional features that they normally
 * disable when creating their own <code>ImageReadParam</code>
 * instances via <code>getDefaultReadParam</code>.
 *
 * <p> Note that unless a query method exists for a capability, it must
 * be supported by all <code>ImageReader</code> implementations
 * (<i>e.g.</i> source render size is optional, but subsampling must be
 * supported).
 *
 *
 * <p>
 *  描述流如何被解码的类。该类或其子类的实例用于向<code> ImageReader </code>的实例提供指定的"how-to"信息。
 * 
 *  编码为文件或流的一部分的图像可以被认为在多个维度中延伸：宽度和高度的空间维度,带的数量以及渐进解码通道的数量。该类允许选择所有这些维度中的图像的连续(超)矩形子区域用于解码。
 * 另外,空间维度可以不连续地子采样。
 * 最后,可以通过控制目标图像的<code> ColorModel </code>和<code> SampleModel </code>来指定颜色和格式转换,方法是提供一个<code> BufferedIm
 * age </code> <code> ImageTypeSpecifier </code>。
 * 另外,空间维度可以不连续地子采样。
 * 
 *  <p> <code> ImageReadParam </code>对象用于指定如何在Java Image I / O框架的上下文中对来自流的输入进行图像或一组图像的转换。
 * 用于特定图像格式的插件将从其<code> ImageReader </code>实现的<code> getDefaultReadParam </code>方法中返回<code> ImageReadPar
 * am </code>的实例。
 *  <p> <code> ImageReadParam </code>对象用于指定如何在Java Image I / O框架的上下文中对来自流的输入进行图像或一组图像的转换。
 * 
 * <p>由<code> ImageReadParam </code>实例维护的状态与正在解码的任何特定图像无关。
 * 当发生实际解码时,将读取参数中设置的值与将从流接收解码的图像数据的目的地<code> BufferedImage </code>的正被解码的图像的实际属性组合。
 * 例如,使用<code> setSourceRegion </code>设置的源区域将首先与实际有效的源区域相交。
 * 结果将通过<code> getDestinationOffset </code>返回的值进行转换,并将生成的矩形与实际有效的目标区域相交,以生成将要写入的目标区域。
 * 
 *  <p>由<code> ImageReadParam </code>指定的参数将应用于图像,如下所示。
 * 首先,如果通过<code> setSourceRenderSize </code>设置了渲染大小,则整个解码图像以由<code> getSourceRenderSize </code>给出的大小渲染。
 * 否则,图像具有由<code> ImageReader.getWidth </code>和<code> ImageReader.getHeight </code>给出的自然大小。
 * 
 *  <p>接下来,图像将根据<code> getSourceXOffset </code>,<code> getSourceYOffset </code>,<code> getSourceWidth </code>
 * 和<code> getSourceHeight </code >。
 * 
 * <p>然后根据{@link IIOParam#setSourceSubsampling IIOParam.setSourceSubsampling}中给出的因子对生成的区域进行子采样。
 * 第一个像素,每行像素数和行数均取决于子采样设置。
 * 调用结果矩形的最小X和Y坐标(<code> minX </code>,<code> minY </code>),其宽度<code> w </code>代码>。
 * 
 *  <p>此矩形由(<code> getDestinationOffset()。x </code>,<code> getDestinationOffset()。
 * y </code>)偏移并针对目标边界进行裁剪。如果没有设置目的地图像,则目的地被定义为具有<code> getDestinationOffset()。
 * x </code> + <code> w </code>的宽度,以及<code> getDestinationOffset y </code> + <code> h </code>,以便源区域的所有像素
 * 可以写入目的地。
 * y </code>)偏移并针对目标边界进行裁剪。如果没有设置目的地图像,则目的地被定义为具有<code> getDestinationOffset()。
 * 
 *  <p>在子采样之后,在目标图像内,并且由<code> getSourceMinProgressivePass </code>和<code> getSourceNumProgressivePasses 
 * </code>指定的渐进遍中写入的像素被传递到下一个步。
 * 
 *  最后,根据<code> setDestinationBands </code>的注释中描述的算法将每个像素的源样本映射到目的地频带。
 * 
 * <p>插件编写器可以通过提供一个实现附加的插件特定接口的子类来扩展<code> ImageReadParam </code>的功能。它是由插件来记录什么接口可用以及如何使用它们。
 * 读者将默认忽略他们不知道的<code> ImageReadParam </code>子类的任何扩展功能。
 * 此外,他们可以忽略通过<code> getDefaultReadParam </code>创建自己的<code> ImageReadParam </code>实例时通常禁用的任何可选功能。
 * 
 *  <p>请注意,除非存在某种功能的查询方法,否则必须由所有<code> ImageReader </code>实现(</i>源渲染大小是可选的,但必须支持子采样) 。
 * 
 * 
 * @see ImageReader
 * @see ImageWriter
 * @see ImageWriteParam
 */
public class ImageReadParam extends IIOParam {

    /**
     * <code>true</code> if this <code>ImageReadParam</code> allows
     * the source rendering dimensions to be set.  By default, the
     * value is <code>false</code>.  Subclasses must set this value
     * manually.
     *
     * <p> <code>ImageReader</code>s that do not support setting of
     * the source render size should set this value to
     * <code>false</code>.
     * <p>
     *  <code> true </code>如果此<code> ImageReadParam </code>允许设置源渲染尺寸。默认情况下,值为<code> false </code>。
     * 子类必须手动设置此值。
     * 
     *  不支持设置源渲染大小的<p> <code> ImageReader </code>应将此值设置为<code> false </code>。
     * 
     */
    protected boolean canSetSourceRenderSize = false;

    /**
     * The desired rendering width and height of the source, if
     * <code>canSetSourceRenderSize</code> is <code>true</code>, or
     * <code>null</code>.
     *
     * <p> <code>ImageReader</code>s that do not support setting of
     * the source render size may ignore this value.
     * <p>
     *  如果<code> canSetSourceRenderSize </code>是<code> true </code>或<code> null </code>,则源的期望呈现宽度和高度。
     * 
     *  不支持设置源渲染大小的<p> <code> ImageReader </code>可能会忽略此值。
     * 
     */
    protected Dimension sourceRenderSize = null;

    /**
     * The current destination <code>BufferedImage</code>, or
     * <code>null</code> if none has been set.  By default, the value
     * is <code>null</code>.
     * <p>
     *  当前目标<code> BufferedImage </code>或<code> null </code>(如果没有设置)。默认情况下,值为<code> null </code>。
     * 
     */
    protected BufferedImage destination = null;

    /**
     * The set of destination bands to be used, as an array of
     * <code>int</code>s.  By default, the value is <code>null</code>,
     * indicating all destination bands should be written in order.
     * <p>
     * 要使用的目的频带集,作为<code> int </code>的数组。默认情况下,值为<code> null </code>,表示所有目标频带应按顺序写入。
     * 
     */
    protected int[] destinationBands = null;

    /**
     * The minimum index of a progressive pass to read from the
     * source.  By default, the value is set to 0, which indicates
     * that passes starting with the first available pass should be
     * decoded.
     *
     * <p> Subclasses should ensure that this value is
     * non-negative.
     * <p>
     *  从源读取的渐进式最小索引。默认情况下,该值设置为0,表示应该解码以第一个可用通道开头的通过。
     * 
     *  <p>子类应确保此值为非负数。
     * 
     */
    protected int minProgressivePass = 0;

    /**
     * The maximum number of progressive passes to read from the
     * source.  By default, the value is set to
     * <code>Integer.MAX_VALUE</code>, which indicates that passes up
     * to and including the last available pass should be decoded.
     *
     * <p> Subclasses should ensure that this value is positive.
     * Additionally, if the value is not
     * <code>Integer.MAX_VALUE</code>, then <code>minProgressivePass +
     * numProgressivePasses - 1</code> should not exceed
     * <code>Integer.MAX_VALUE</code>.
     * <p>
     *  从源读取的逐行扫描的最大数量。默认情况下,该值设置为<code> Integer.MAX_VALUE </code>,表示应该解码直到并包括最后一个可用通行的通过。
     * 
     *  <p>子类应确保此值为正。
     * 此外,如果值不是<code> Integer.MAX_VALUE </code>,则<code> minProgressivePass + numProgressivePasses  -  1 </code>
     * 不应超过<code> Integer.MAX_VALUE </code>。
     *  <p>子类应确保此值为正。
     * 
     */
    protected int numProgressivePasses = Integer.MAX_VALUE;

    /**
     * Constructs an <code>ImageReadParam</code>.
     * <p>
     *  构造一个<code> ImageReadParam </code>。
     * 
     */
    public ImageReadParam() {}

    // Comment inherited
    public void setDestinationType(ImageTypeSpecifier destinationType) {
        super.setDestinationType(destinationType);
        setDestination(null);
    }

    /**
     * Supplies a <code>BufferedImage</code> to be used as the
     * destination for decoded pixel data.  The currently set image
     * will be written to by the <code>read</code>,
     * <code>readAll</code>, and <code>readRaster</code> methods, and
     * a reference to it will be returned by those methods.
     *
     * <p> Pixel data from the aforementioned methods will be written
     * starting at the offset specified by
     * <code>getDestinationOffset</code>.
     *
     * <p> If <code>destination</code> is <code>null</code>, a
     * newly-created <code>BufferedImage</code> will be returned by
     * those methods.
     *
     * <p> At the time of reading, the image is checked to verify that
     * its <code>ColorModel</code> and <code>SampleModel</code>
     * correspond to one of the <code>ImageTypeSpecifier</code>s
     * returned from the <code>ImageReader</code>'s
     * <code>getImageTypes</code> method.  If it does not, the reader
     * will throw an <code>IIOException</code>.
     *
     * <p>
     *  提供<code> BufferedImage </code>作为解码像素数据的目标位置。
     * 当前设置的图像将被<code> read </code>,<code> readAll </code>和<code> readRaster </code>方法写入,并且这些方法将返回对它的引用。
     * 
     *  <p>上述方法中的像素数据将从由<code> getDestinationOffset </code>指定的偏移量开始写入。
     * 
     *  <p>如果<code> destination </code>是<code> null </code>,那些方法将返回新创建的<code> BufferedImage </code>。
     * 
     * <p>在读取时,检查图像以验证其<code> ColorModel </code>和<code> SampleModel </code>对应于从<code> ImageTypeSpecifier </code>
     * 返回的<code> <code> ImageReader </code>的<code> getImageTypes </code>方法。
     * 如果没有,读者会抛出一个<code> IIOException </code>。
     * 
     * 
     * @param destination the BufferedImage to be written to, or
     * <code>null</code>.
     *
     * @see #getDestination
     */
    public void setDestination(BufferedImage destination) {
        this.destination = destination;
    }

    /**
     * Returns the <code>BufferedImage</code> currently set by the
     * <code>setDestination</code> method, or <code>null</code>
     * if none is set.
     *
     * <p>
     *  返回由<code> setDestination </code>方法当前设置的<code> BufferedImage </code>,如果没有设置,则返回<code> null </code>。
     * 
     * 
     * @return the BufferedImage to be written to.
     *
     * @see #setDestination
     */
    public BufferedImage getDestination() {
        return destination;
    }

    /**
     * Sets the indices of the destination bands where data
     * will be placed.  Duplicate indices are not allowed.
     *
     * <p> A <code>null</code> value indicates that all destination
     * bands will be used.
     *
     * <p> Choosing a destination band subset will not affect the
     * number of bands in the output image of a read if no destination
     * image is specified; the created destination image will still
     * have the same number of bands as if this method had never been
     * called.  If a different number of bands in the destination
     * image is desired, an image must be supplied using the
     * <code>ImageReadParam.setDestination</code> method.
     *
     * <p> At the time of reading or writing, an
     * <code>IllegalArgumentException</code> will be thrown by the
     * reader or writer if a value larger than the largest destination
     * band index has been specified, or if the number of source bands
     * and destination bands to be used differ.  The
     * <code>ImageReader.checkReadParamBandSettings</code> method may
     * be used to automate this test.
     *
     * <p>
     *  设置要放置数据的目标波段的索引。不允许重复索引。
     * 
     *  <p> <code> null </code>值表示将使用所有目标波段。
     * 
     *  <p>如果未指定目标图像,选择目标带子集将不会影响读取的输出图像中的带数;所创建的目标图像仍然具有与从未调用该方法时相同的频带数。
     * 如果目的图像中需要不同数量的带,则必须使用<code> ImageReadParam.setDestination </code>方法提供图像。
     * 
     *  <p>在读取或写入时,如果已经指定大于最大目标频带索引的值,或者如果源频带的数量和源频带的数量已经被指定,则读取器或写入器将抛出<code> IllegalArgumentException </code>
     * 要使用的目的频带不同。
     *  <code> ImageReader.checkReadParamBandSettings </code>方法可用于自动执行此测试。
     * 
     * 
     * @param destinationBands an array of integer band indices to be
     * used.
     *
     * @exception IllegalArgumentException if <code>destinationBands</code>
     * contains a negative or duplicate value.
     *
     * @see #getDestinationBands
     * @see #getSourceBands
     * @see ImageReader#checkReadParamBandSettings
     */
    public void setDestinationBands(int[] destinationBands) {
        if (destinationBands == null) {
            this.destinationBands = null;
        } else {
            int numBands = destinationBands.length;
            for (int i = 0; i < numBands; i++) {
                int band = destinationBands[i];
                if (band < 0) {
                    throw new IllegalArgumentException("Band value < 0!");
                }
                for (int j = i + 1; j < numBands; j++) {
                    if (band == destinationBands[j]) {
                        throw new IllegalArgumentException("Duplicate band value!");
                    }
                }
            }
            this.destinationBands = (int[])destinationBands.clone();
        }
    }

    /**
     * Returns the set of band indices where data will be placed.
     * If no value has been set, <code>null</code> is returned to
     * indicate that all destination bands will be used.
     *
     * <p>
     * 返回要放置数据的波段索引集。如果没有设置值,则返回<code> null </code>以指示将使用所有目的地频带。
     * 
     * 
     * @return the indices of the destination bands to be used,
     * or <code>null</code>.
     *
     * @see #setDestinationBands
     */
    public int[] getDestinationBands() {
        if (destinationBands == null) {
            return null;
        } else {
            return (int[])(destinationBands.clone());
        }
    }

    /**
     * Returns <code>true</code> if this reader allows the source
     * image to be rendered at an arbitrary size as part of the
     * decoding process, by means of the
     * <code>setSourceRenderSize</code> method.  If this method
     * returns <code>false</code>, calls to
     * <code>setSourceRenderSize</code> will throw an
     * <code>UnsupportedOperationException</code>.
     *
     * <p>
     *  如果此读取器允许源图像通过<code> setSourceRenderSize </code>方法在解码过程中作为任意大小进行渲染,则返回<code> true </code>。
     * 如果此方法返回<code> false </code>,对<code> setSourceRenderSize </code>的调用将抛出<code> UnsupportedOperationExcep
     * tion </code>。
     *  如果此读取器允许源图像通过<code> setSourceRenderSize </code>方法在解码过程中作为任意大小进行渲染,则返回<code> true </code>。
     * 
     * 
     * @return <code>true</code> if setting source rendering size is
     * supported.
     *
     * @see #setSourceRenderSize
     */
    public boolean canSetSourceRenderSize() {
        return canSetSourceRenderSize;
    }

    /**
     * If the image is able to be rendered at an arbitrary size, sets
     * the source width and height to the supplied values.  Note that
     * the values returned from the <code>getWidth</code> and
     * <code>getHeight</code> methods on <code>ImageReader</code> are
     * not affected by this method; they will continue to return the
     * default size for the image.  Similarly, if the image is also
     * tiled the tile width and height are given in terms of the default
     * size.
     *
     * <p> Typically, the width and height should be chosen such that
     * the ratio of width to height closely approximates the aspect
     * ratio of the image, as returned from
     * <code>ImageReader.getAspectRatio</code>.
     *
     * <p> If this plug-in does not allow the rendering size to be
     * set, an <code>UnsupportedOperationException</code> will be
     * thrown.
     *
     * <p> To remove the render size setting, pass in a value of
     * <code>null</code> for <code>size</code>.
     *
     * <p>
     *  如果图像能够以任意大小渲染,请将源宽度和高度设置为提供的值。
     * 注意,从<code> getWidth </code>和<code> getHeight </code>方法返回的<code> ImageReader </code>值不受此方法的影响;它们将继续返回图
     * 像的默认大小。
     *  如果图像能够以任意大小渲染,请将源宽度和高度设置为提供的值。类似地,如果图像也被平铺,则瓦片宽度和高度以默认尺寸给出。
     * 
     *  <p>通常,应该选择宽度和高度,以便从<code> ImageReader.getAspectRatio </code>返回的宽度与高度的比率接近图像的宽高比。
     * 
     *  <p>如果此插件不允许设置渲染大小,则会抛出<code> UnsupportedOperationException </code>。
     * 
     *  <p>要移除渲染大小设置,请为<code> size </code>输入<code> null </code>的值。
     * 
     * 
     * @param size a <code>Dimension</code> indicating the desired
     * width and height.
     *
     * @exception IllegalArgumentException if either the width or the
     * height is negative or 0.
     * @exception UnsupportedOperationException if image resizing
     * is not supported by this plug-in.
     *
     * @see #getSourceRenderSize
     * @see ImageReader#getWidth
     * @see ImageReader#getHeight
     * @see ImageReader#getAspectRatio
     */
    public void setSourceRenderSize(Dimension size)
        throws UnsupportedOperationException {
        if (!canSetSourceRenderSize()) {
            throw new UnsupportedOperationException
                ("Can't set source render size!");
        }

        if (size == null) {
            this.sourceRenderSize = null;
        } else {
            if (size.width <= 0 || size.height <= 0) {
                throw new IllegalArgumentException("width or height <= 0!");
            }
            this.sourceRenderSize = (Dimension)size.clone();
        }
    }

    /**
     * Returns the width and height of the source image as it
     * will be rendered during decoding, if they have been set via the
     * <code>setSourceRenderSize</code> method.  A
     * <code>null</code>value indicates that no setting has been made.
     *
     * <p>
     * 返回源图像在解码期间渲染的宽度和高度,如果它们已通过<code> setSourceRenderSize </code>方法设置。 <code> null </code>值表示未进行任何设置。
     * 
     * 
     * @return the rendered width and height of the source image
     * as a <code>Dimension</code>.
     *
     * @see #setSourceRenderSize
     */
    public Dimension getSourceRenderSize() {
        return (sourceRenderSize == null) ?
            null : (Dimension)sourceRenderSize.clone();
    }

    /**
     * Sets the range of progressive passes that will be decoded.
     * Passes outside of this range will be ignored.
     *
     * <p> A progressive pass is a re-encoding of the entire image,
     * generally at progressively higher effective resolutions, but
     * requiring greater transmission bandwidth.  The most common use
     * of progressive encoding is found in the JPEG format, where
     * successive passes include more detailed representations of the
     * high-frequency image content.
     *
     * <p> The actual number of passes to be decoded is determined
     * during decoding, based on the number of actual passes available
     * in the stream.  Thus if <code>minPass + numPasses - 1</code> is
     * larger than the index of the last available passes, decoding
     * will end with that pass.
     *
     * <p> A value of <code>numPasses</code> of
     * <code>Integer.MAX_VALUE</code> indicates that all passes from
     * <code>minPass</code> forward should be read.  Otherwise, the
     * index of the last pass (<i>i.e.</i>, <code>minPass + numPasses
     * - 1</code>) must not exceed <code>Integer.MAX_VALUE</code>.
     *
     * <p> There is no <code>unsetSourceProgressivePasses</code>
     * method; the same effect may be obtained by calling
     * <code>setSourceProgressivePasses(0, Integer.MAX_VALUE)</code>.
     *
     * <p>
     *  设置将要解码的渐进遍数的范围。超出此范围的通过将被忽略。
     * 
     *  逐行扫描是整个图像的重新编码,通常是逐渐更高的有效分辨率,但是需要更大的传输带宽。逐行编码的最常见的用法是以JPEG格式找到的,其中连续的遍次包括高频图像内容的更详细的表示。
     * 
     *  <p>在解码期间,基于流中可用的实际遍数来确定要解码的实际遍数。因此,如果<code> minPass + numPasses-1 </code>大于最后可用遍的索引,则解码将以该遍结束。
     * 
     *  <p> <code> Integer.MAX_VALUE </code>的<code> numPasses </code>值表示应读取从<code> minPass </code>向前的所有传递。
     * 否则,最后一次通过的索引(<i> ie。
     * </i>,<code> minPass + numPasses  -  1 </code>)不能超过<code> Integer.MAX_VALUE </code>。
     * 
     *  <p>没有<code> unsetSourceProgressivePasses </code>方法;通过调用<code> setSourceProgressivePasses(0,Integer.M
     * AX_VALUE)</code>可以获得相同的效果。
     * 
     * @param minPass the index of the first pass to be decoded.
     * @param numPasses the maximum number of passes to be decoded.
     *
     * @exception IllegalArgumentException if <code>minPass</code> is
     * negative, <code>numPasses</code> is negative or 0, or
     * <code>numPasses</code> is smaller than
     * <code>Integer.MAX_VALUE</code> but <code>minPass +
     * numPasses - 1</code> is greater than
     * <code>INTEGER.MAX_VALUE</code>.
     *
     * @see #getSourceMinProgressivePass
     * @see #getSourceMaxProgressivePass
     */
    public void setSourceProgressivePasses(int minPass, int numPasses) {
        if (minPass < 0) {
            throw new IllegalArgumentException("minPass < 0!");
        }
        if (numPasses <= 0) {
            throw new IllegalArgumentException("numPasses <= 0!");
        }
        if ((numPasses != Integer.MAX_VALUE) &&
            (((minPass + numPasses - 1) & 0x80000000) != 0)) {
            throw new IllegalArgumentException
                ("minPass + numPasses - 1 > INTEGER.MAX_VALUE!");
        }

        this.minProgressivePass = minPass;
        this.numProgressivePasses = numPasses;
    }

    /**
     * Returns the index of the first progressive pass that will be
     * decoded. If no value has been set, 0 will be returned (which is
     * the correct value).
     *
     * <p>
     * 
     * 
     * @return the index of the first pass that will be decoded.
     *
     * @see #setSourceProgressivePasses
     * @see #getSourceNumProgressivePasses
     */
    public int getSourceMinProgressivePass() {
        return minProgressivePass;
    }

    /**
     * If <code>getSourceNumProgressivePasses</code> is equal to
     * <code>Integer.MAX_VALUE</code>, returns
     * <code>Integer.MAX_VALUE</code>.  Otherwise, returns
     * <code>getSourceMinProgressivePass() +
     * getSourceNumProgressivePasses() - 1</code>.
     *
     * <p>
     * 返回将要解码的第一个渐进式遍的索引。如果没有设置值,将返回0(这是正确的值)。
     * 
     * 
     * @return the index of the last pass to be read, or
     * <code>Integer.MAX_VALUE</code>.
     */
    public int getSourceMaxProgressivePass() {
        if (numProgressivePasses == Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        } else {
            return minProgressivePass + numProgressivePasses - 1;
        }
    }

    /**
     * Returns the number of the progressive passes that will be
     * decoded. If no value has been set,
     * <code>Integer.MAX_VALUE</code> will be returned (which is the
     * correct value).
     *
     * <p>
     *  如果<code> getSourceNumProgressivePasses </code>等于<code> Integer.MAX_VALUE </code>,则返回<code> Integer.M
     * AX_VALUE </code>。
     * 否则,返回<code> getSourceMinProgressivePass()+ getSourceNumProgressivePasses() -  1 </code>。
     * 
     * 
     * @return the number of the passes that will be decoded.
     *
     * @see #setSourceProgressivePasses
     * @see #getSourceMinProgressivePass
     */
    public int getSourceNumProgressivePasses() {
        return numProgressivePasses;
    }
}
