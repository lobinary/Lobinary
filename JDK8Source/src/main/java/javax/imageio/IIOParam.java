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

import java.awt.Point;
import java.awt.Rectangle;

/**
 * A superclass of all classes describing how streams should be
 * decoded or encoded.  This class contains all the variables and
 * methods that are shared by <code>ImageReadParam</code> and
 * <code>ImageWriteParam</code>.
 *
 * <p> This class provides mechanisms to specify a source region and a
 * destination region.  When reading, the source is the stream and
 * the in-memory image is the destination.  When writing, these are
 * reversed.  In the case of writing, destination regions may be used
 * only with a writer that supports pixel replacement.
 * <p>
 * Decimation subsampling may be specified for both readers
 * and writers, using a movable subsampling grid.
 * <p>
 * Subsets of the source and destination bands may be selected.
 *
 * <p>
 *  描述流应如何解码或编码的所有类的超类。此类包含由<code> ImageReadParam </code>和<code> ImageWriteParam </code>共享的所有变量和方法。
 * 
 *  <p>此类提供了指定源区域和目标区域的机制。当读取时,源是流,而内存中的图像是目标。写入时,它们是相反的。在写入的情况下,目的地区域可以仅与支持像素替换的写入器一起使用。
 * <p>
 *  可以使用可移动子采样网格为读取器和写入器指定抽取子采样。
 * <p>
 *  可以选择源和目的频带的子集。
 * 
 */
public abstract class IIOParam {

    /**
     * The source region, on <code>null</code> if none is set.
     * <p>
     *  源区域,在<code> null </code>(如果没有设置)。
     * 
     */
    protected Rectangle sourceRegion = null;

    /**
     * The decimation subsampling to be applied in the horizontal
     * direction.  By default, the value is <code>1</code>.
     * The value must not be negative or 0.
     * <p>
     *  在水平方向上应用抽取子采样。默认情况下,值为<code> 1 </code>。该值不能为负或0。
     * 
     */
    protected int sourceXSubsampling = 1;

    /**
     * The decimation subsampling to be applied in the vertical
     * direction.  By default, the value is <code>1</code>.
     * The value must not be negative or 0.
     * <p>
     *  抽取子采样在垂直方向上应用。默认情况下,值为<code> 1 </code>。该值不能为负或0。
     * 
     */
    protected int sourceYSubsampling = 1;

    /**
     * A horizontal offset to be applied to the subsampling grid before
     * subsampling.  The first pixel to be used will be offset this
     * amount from the origin of the region, or of the image if no
     * region is specified.
     * <p>
     *  在子采样之前应用于子采样网格的水平偏移。如果没有指定区域,要使用的第一个像素将从该区域的原点或图像的原点偏移该量。
     * 
     */
    protected int subsamplingXOffset = 0;

    /**
     * A vertical offset to be applied to the subsampling grid before
     * subsampling.  The first pixel to be used will be offset this
     * amount from the origin of the region, or of the image if no
     * region is specified.
     * <p>
     * 子采样之前应用于子采样网格的垂直偏移。如果没有指定区域,要使用的第一个像素将从该区域的原点或图像的原点偏移该量。
     * 
     */
    protected int subsamplingYOffset = 0;

    /**
     * An array of <code>int</code>s indicating which source bands
     * will be used, or <code>null</code>.  If <code>null</code>, the
     * set of source bands to be used is as described in the comment
     * for the <code>setSourceBands</code> method.  No value should
     * be allowed to be negative.
     * <p>
     *  指示将使用哪些源频带的<code> int </code>数组,或<code> null </code>。
     * 如果<code> null </code>,要使用的源带集合如<code> setSourceBands </code>方法的注释中所述。任何值都不能为负数。
     * 
     */
    protected int[] sourceBands = null;

    /**
     * An <code>ImageTypeSpecifier</code> to be used to generate a
     * destination image when reading, or to set the output color type
     * when writing.  If non has been set the value will be
     * <code>null</code>.  By default, the value is <code>null</code>.
     * <p>
     *  用于在读取时生成目标图像或在写入时设置输出颜色类型的<code> ImageTypeSpecifier </code>。如果未设置,则值将为<code> null </code>。
     * 默认情况下,值为<code> null </code>。
     * 
     */
    protected ImageTypeSpecifier destinationType = null;

    /**
     * The offset in the destination where the upper-left decoded
     * pixel should be placed.  By default, the value is (0, 0).
     * <p>
     *  在应该放置左上解码像素的目的地中的偏移。默认情况下,值为(0,0)。
     * 
     */
    protected Point destinationOffset = new Point(0, 0);

    /**
     * The default <code>IIOParamController</code> that will be
     * used to provide settings for this <code>IIOParam</code>
     * object when the <code>activateController</code> method
     * is called.  This default should be set by subclasses
     * that choose to provide their own default controller,
     * usually a GUI, for setting parameters.
     *
     * <p>
     *  当调用<code> activateController </code>方法时,将使用默认<code> IIOParamController </code>来为<code> IIOParam </code>
     * 对象提供设置。
     * 这个默认值应该由子类设置,这些子类选择提供自己的默认控制器(通常是GUI)来设置参数。
     * 
     * 
     * @see IIOParamController
     * @see #getDefaultController
     * @see #activateController
     */
    protected IIOParamController defaultController = null;

    /**
     * The <code>IIOParamController</code> that will be
     * used to provide settings for this <code>IIOParam</code>
     * object when the <code>activateController</code> method
     * is called.  This value overrides any default controller,
     * even when null.
     *
     * <p>
     *  当调用<code> activateController </code>方法时,<code> IIOParamController </code>将用于为<code> IIOParam </code>
     * 对象提供设置。
     * 此值将覆盖所有默认控制器,即使为null。
     * 
     * 
     * @see IIOParamController
     * @see #setController(IIOParamController)
     * @see #hasController()
     * @see #activateController()
     */
    protected IIOParamController controller = null;

    /**
     * Protected constructor may be called only by subclasses.
     * <p>
     *  受保护的构造函数只能由子类调用。
     * 
     */
    protected IIOParam() {
        controller = defaultController;
    }

    /**
     * Sets the source region of interest.  The region of interest is
     * described as a rectangle, with the upper-left corner of the
     * source image as pixel (0, 0) and increasing values down and to
     * the right.  The actual number of pixels used will depend on
     * the subsampling factors set by <code>setSourceSubsampling</code>.
     * If subsampling has been set such that this number is zero,
     * an <code>IllegalStateException</code> will be thrown.
     *
     * <p> The source region of interest specified by this method will
     * be clipped as needed to fit within the source bounds, as well
     * as the destination offsets, width, and height at the time of
     * actual I/O.
     *
     * <p> A value of <code>null</code> for <code>sourceRegion</code>
     * will remove any region specification, causing the entire image
     * to be used.
     *
     * <p>
     * 设置感兴趣的源区域。感兴趣区域被描述为矩形,源图像的左上角为像素(0,0),向下和向右增加值。
     * 使用的实际像素数将取决于<code> setSourceSubsampling </code>设置的子采样因子。
     * 如果已设置子采样,以使此数为零,将抛出<code> IllegalStateException </code>。
     * 
     *  <p>由此方法指定的感兴趣源区域将根据需要进行裁剪,以适应源边界,以及实际I / O时的目标偏移量,宽度和高度。
     * 
     *  <p> <code> sourceRegion </code>的<code> null </code>值将删除任何区域规范,导致使用整个图像。
     * 
     * 
     * @param sourceRegion a <code>Rectangle</code> specifying the
     * source region of interest, or <code>null</code>.
     *
     * @exception IllegalArgumentException if
     * <code>sourceRegion</code> is non-<code>null</code> and either
     * <code>sourceRegion.x</code> or <code>sourceRegion.y</code> is
     * negative.
     * @exception IllegalArgumentException if
     * <code>sourceRegion</code> is non-<code>null</code> and either
     * <code>sourceRegion.width</code> or
     * <code>sourceRegion.height</code> is negative or 0.
     * @exception IllegalStateException if subsampling is such that
     * this region will have a subsampled width or height of zero.
     *
     * @see #getSourceRegion
     * @see #setSourceSubsampling
     * @see ImageReadParam#setDestinationOffset
     * @see ImageReadParam#getDestinationOffset
     */
    public void setSourceRegion(Rectangle sourceRegion) {
        if (sourceRegion == null) {
            this.sourceRegion = null;
            return;
        }

        if (sourceRegion.x < 0) {
            throw new IllegalArgumentException("sourceRegion.x < 0!");
        }
        if (sourceRegion.y < 0){
            throw new IllegalArgumentException("sourceRegion.y < 0!");
        }
        if (sourceRegion.width <= 0) {
            throw new IllegalArgumentException("sourceRegion.width <= 0!");
        }
        if (sourceRegion.height <= 0) {
            throw new IllegalArgumentException("sourceRegion.height <= 0!");
        }

        // Throw an IllegalStateException if region falls between subsamples
        if (sourceRegion.width <= subsamplingXOffset) {
            throw new IllegalStateException
                ("sourceRegion.width <= subsamplingXOffset!");
        }
        if (sourceRegion.height <= subsamplingYOffset) {
            throw new IllegalStateException
                ("sourceRegion.height <= subsamplingYOffset!");
        }

        this.sourceRegion = (Rectangle)sourceRegion.clone();
    }

    /**
     * Returns the source region to be used.  The returned value is
     * that set by the most recent call to
     * <code>setSourceRegion</code>, and will be <code>null</code> if
     * there is no region set.
     *
     * <p>
     *  返回要使用的源区域。返回的值是由最近一次调用<code> setSourceRegion </code>设置的值,如果没有设置区域,则返回值为<code> null </code>。
     * 
     * 
     * @return the source region of interest as a
     * <code>Rectangle</code>, or <code>null</code>.
     *
     * @see #setSourceRegion
     */
    public Rectangle getSourceRegion() {
        if (sourceRegion == null) {
            return null;
        }
        return (Rectangle)sourceRegion.clone();
    }

    /**
     * Specifies a decimation subsampling to apply on I/O.  The
     * <code>sourceXSubsampling</code> and
     * <code>sourceYSubsampling</code> parameters specify the
     * subsampling period (<i>i.e.</i>, the number of rows and columns
     * to advance after every source pixel).  Specifically, a period of
     * 1 will use every row or column; a period of 2 will use every
     * other row or column.  The <code>subsamplingXOffset</code> and
     * <code>subsamplingYOffset</code> parameters specify an offset
     * from the region (or image) origin for the first subsampled pixel.
     * Adjusting the origin of the subsample grid is useful for avoiding
     * seams when subsampling a very large source image into destination
     * regions that will be assembled into a complete subsampled image.
     * Most users will want to simply leave these parameters at 0.
     *
     * <p> The number of pixels and scanlines to be used are calculated
     * as follows.
     * <p>
     * The number of subsampled pixels in a scanline is given by
     * <p>
     * <code>truncate[(width - subsamplingXOffset + sourceXSubsampling - 1)
     * / sourceXSubsampling]</code>.
     * <p>
     * If the region is such that this width is zero, an
     * <code>IllegalStateException</code> is thrown.
     * <p>
     * The number of scanlines to be used can be computed similarly.
     *
     * <p>The ability to set the subsampling grid to start somewhere
     * other than the source region origin is useful if the
     * region is being used to create subsampled tiles of a large image,
     * where the tile width and height are not multiples of the
     * subsampling periods.  If the subsampling grid does not remain
     * consistent from tile to tile, there will be artifacts at the tile
     * boundaries.  By adjusting the subsampling grid offset for each
     * tile to compensate, these artifacts can be avoided.  The tradeoff
     * is that in order to avoid these artifacts, the tiles are not all
     * the same size.  The grid offset to use in this case is given by:
     * <br>
     * grid offset = [period - (region offset modulo period)] modulo period)
     *
     * <p> If either <code>sourceXSubsampling</code> or
     * <code>sourceYSubsampling</code> is 0 or negative, an
     * <code>IllegalArgumentException</code> will be thrown.
     *
     * <p> If either <code>subsamplingXOffset</code> or
     * <code>subsamplingYOffset</code> is negative or greater than or
     * equal to the corresponding period, an
     * <code>IllegalArgumentException</code> will be thrown.
     *
     * <p> There is no <code>unsetSourceSubsampling</code> method;
     * simply call <code>setSourceSubsampling(1, 1, 0, 0)</code> to
     * restore default values.
     *
     * <p>
     * 指定要应用于I / O的抽取子采样。
     *  <code> sourceXSubsampling </code>和<code> sourceYSubsampling </code>参数指定子采样周期(即</i>,在每个源像素之后前进的行和列的数量
     * )。
     * 指定要应用于I / O的抽取子采样。具体来说,1的周期将使用每行或每列;周期为2将使用每隔一行或每列。
     *  <code> subsamplingXOffset </code>和<code> subsamplingYOffset </code>参数指定来自第一子采样像素的区域(或图像)原点的偏移。
     * 调整子样本网格的原点对于避免在将非常大的源图像子采样到将被组装成完整子采样图像的目的地区域中时的接缝是有用的。大多数用户都希望将这些参数保留为0。
     * 
     *  <p>要使用的像素和扫描线的数量如下计算。
     * <p>
     *  扫描线中子采样像素的数量由下式给出
     * <p>
     *  <code> truncate [(width  -  subsamplingXOffset + sourceXSubsampling  -  1)/ sourceXSubsampling] </code>
     * 。
     * <p>
     *  如果该区域的宽度为零,则抛出<code> IllegalStateException </code>。
     * <p>
     *  可以类似地计算要使用的扫描线的数量。
     * 
     * 如果该区域用于创建大图像的二次抽样图块,其中图块宽度和高度不是子采样周期的倍数,则将子采样网格设置为从源区域原点之外开始的能力是有用的。如果子采样网格从图块到图块不保持一致,则在图块边界处将存在伪影。
     * 通过调整每个图块的子采样网格偏移以进行补偿,可以避免这些伪影。折衷是为了避免这些伪像,图块不是全部相同的大小。在这种情况下使用的网格偏移由下式给出：。
     * <br>
     *  网格偏移= [周期 - (区域偏移模数周期)]模数周期)
     * 
     *  <p>如果<code> sourceXSubsampling </code>或<code> sourceYSubsampling </code>为0或否定,则会抛出<code> IllegalArgu
     * mentException </code>。
     * 
     *  <p>如果<code> subsamplingXOffset </code>或<code> subsamplingYOffset </code>为负值或大于或等于相应的期间,则会抛出<code> Il
     * legalArgumentException </code>。
     * 
     *  <p>没有<code> unsetSourceSubsampling </code>方法;只需调用<code> setSourceSubsampling(1,1,0,0)</code>即可恢复默认值。
     * 
     * 
     * @param sourceXSubsampling the number of columns to advance
     * between pixels.
     * @param sourceYSubsampling the number of rows to advance between
     * pixels.
     * @param subsamplingXOffset the horizontal offset of the first subsample
     * within the region, or within the image if no region is set.
     * @param subsamplingYOffset the horizontal offset of the first subsample
     * within the region, or within the image if no region is set.
     * @exception IllegalArgumentException if either period is
     * negative or 0, or if either grid offset is negative or greater than
     * the corresponding period.
     * @exception IllegalStateException if the source region is such that
     * the subsampled output would contain no pixels.
     */
    public void setSourceSubsampling(int sourceXSubsampling,
                                     int sourceYSubsampling,
                                     int subsamplingXOffset,
                                     int subsamplingYOffset) {
        if (sourceXSubsampling <= 0) {
            throw new IllegalArgumentException("sourceXSubsampling <= 0!");
        }
        if (sourceYSubsampling <= 0) {
            throw new IllegalArgumentException("sourceYSubsampling <= 0!");
        }
        if (subsamplingXOffset < 0 ||
            subsamplingXOffset >= sourceXSubsampling) {
            throw new IllegalArgumentException
                ("subsamplingXOffset out of range!");
        }
        if (subsamplingYOffset < 0 ||
            subsamplingYOffset >= sourceYSubsampling) {
            throw new IllegalArgumentException
                ("subsamplingYOffset out of range!");
        }

        // Throw an IllegalStateException if region falls between subsamples
        if (sourceRegion != null) {
            if (subsamplingXOffset >= sourceRegion.width ||
                subsamplingYOffset >= sourceRegion.height) {
                throw new IllegalStateException("region contains no pixels!");
            }
        }

        this.sourceXSubsampling = sourceXSubsampling;
        this.sourceYSubsampling = sourceYSubsampling;
        this.subsamplingXOffset = subsamplingXOffset;
        this.subsamplingYOffset = subsamplingYOffset;
    }

    /**
     * Returns the number of source columns to advance for each pixel.
     *
     * <p>If <code>setSourceSubsampling</code> has not been called, 1
     * is returned (which is the correct value).
     *
     * <p>
     *  返回每个像素前进的源列数。
     * 
     *  <p>如果未调用<code> setSourceSubsampling </code>,则返回1(这是正确的值)。
     * 
     * 
     * @return the source subsampling X period.
     *
     * @see #setSourceSubsampling
     * @see #getSourceYSubsampling
     */
    public int getSourceXSubsampling() {
        return sourceXSubsampling;
    }

    /**
     * Returns the number of rows to advance for each pixel.
     *
     * <p>If <code>setSourceSubsampling</code> has not been called, 1
     * is returned (which is the correct value).
     *
     * <p>
     *  返回每个像素的行数。
     * 
     * <p>如果未调用<code> setSourceSubsampling </code>,则返回1(这是正确的值)。
     * 
     * 
     * @return the source subsampling Y period.
     *
     * @see #setSourceSubsampling
     * @see #getSourceXSubsampling
     */
    public int getSourceYSubsampling() {
        return sourceYSubsampling;
    }

    /**
     * Returns the horizontal offset of the subsampling grid.
     *
     * <p>If <code>setSourceSubsampling</code> has not been called, 0
     * is returned (which is the correct value).
     *
     * <p>
     *  返回子采样网格的水平偏移量。
     * 
     *  <p>如果未调用<code> setSourceSubsampling </code>,则返回0(这是正确的值)。
     * 
     * 
     * @return the source subsampling grid X offset.
     *
     * @see #setSourceSubsampling
     * @see #getSubsamplingYOffset
     */
    public int getSubsamplingXOffset() {
        return subsamplingXOffset;
    }

    /**
     * Returns the vertical offset of the subsampling grid.
     *
     * <p>If <code>setSourceSubsampling</code> has not been called, 0
     * is returned (which is the correct value).
     *
     * <p>
     *  返回子采样网格的垂直偏移量。
     * 
     *  <p>如果未调用<code> setSourceSubsampling </code>,则返回0(这是正确的值)。
     * 
     * 
     * @return the source subsampling grid Y offset.
     *
     * @see #setSourceSubsampling
     * @see #getSubsamplingXOffset
     */
    public int getSubsamplingYOffset() {
        return subsamplingYOffset;
    }

    /**
     * Sets the indices of the source bands to be used.  Duplicate
     * indices are not allowed.
     *
     * <p> A <code>null</code> value indicates that all source bands
     * will be used.
     *
     * <p> At the time of reading, an
     * <code>IllegalArgumentException</code> will be thrown by the
     * reader or writer if a value larger than the largest available
     * source band index has been specified or if the number of source
     * bands and destination bands to be used differ.  The
     * <code>ImageReader.checkReadParamBandSettings</code> method may
     * be used to automate this test.
     *
     * <p> Semantically, a copy is made of the array; changes to the
     * array contents subsequent to this call have no effect on
     * this <code>IIOParam</code>.
     *
     * <p>
     *  设置要使用的源波段的索引。不允许重复索引。
     * 
     *  <p> <code> null </code>值表示将使用所有源波段。
     * 
     *  <p>在读取时,如果已经指定大于最大可用源频带索引的值,或者如果源频带和目的频带的数量被指定,则读取器或写入器将抛出<code> IllegalArgumentException </code>使用不
     * 同。
     *  <code> ImageReader.checkReadParamBandSettings </code>方法可用于自动执行此测试。
     * 
     *  <p>语义上,复制由数组;在此调用之后对数组内容的更改对此<code> IIOParam </code>没有影响。
     * 
     * 
     * @param sourceBands an array of integer band indices to be
     * used.
     *
     * @exception IllegalArgumentException if <code>sourceBands</code>
     * contains a negative or duplicate value.
     *
     * @see #getSourceBands
     * @see ImageReadParam#setDestinationBands
     * @see ImageReader#checkReadParamBandSettings
     */
    public void setSourceBands(int[] sourceBands) {
        if (sourceBands == null) {
            this.sourceBands = null;
        } else {
            int numBands = sourceBands.length;
            for (int i = 0; i < numBands; i++) {
                int band = sourceBands[i];
                if (band < 0) {
                    throw new IllegalArgumentException("Band value < 0!");
                }
                for (int j = i + 1; j < numBands; j++) {
                    if (band == sourceBands[j]) {
                        throw new IllegalArgumentException("Duplicate band value!");
                    }
                }

            }
            this.sourceBands = (int[])(sourceBands.clone());
        }
    }

    /**
     * Returns the set of of source bands to be used. The returned
     * value is that set by the most recent call to
     * <code>setSourceBands</code>, or <code>null</code> if there have
     * been no calls to <code>setSourceBands</code>.
     *
     * <p> Semantically, the array returned is a copy; changes to
     * array contents subsequent to this call have no effect on this
     * <code>IIOParam</code>.
     *
     * <p>
     *  返回要使用的源波段组。
     * 返回值是由最近一次调用<code> setSourceBands </code>设置的值,如果没有调用<code> setSourceBands </code>,则返回<code> null </code>
     * 。
     *  返回要使用的源波段组。
     * 
     *  <p>语义上,返回的数组是一个副本;在此调用之后对数组内容的更改对此<code> IIOParam </code>没有影响。
     * 
     * 
     * @return the set of source bands to be used, or
     * <code>null</code>.
     *
     * @see #setSourceBands
     */
    public int[] getSourceBands() {
        if (sourceBands == null) {
            return null;
        }
        return (int[])(sourceBands.clone());
    }

    /**
     * Sets the desired image type for the destination image, using an
     * <code>ImageTypeSpecifier</code>.
     *
     * <p> When reading, if the layout of the destination has been set
     * using this method, each call to an <code>ImageReader</code>
     * <code>read</code> method will return a new
     * <code>BufferedImage</code> using the format specified by the
     * supplied type specifier.  As a side effect, any destination
     * <code>BufferedImage</code> set by
     * <code>ImageReadParam.setDestination(BufferedImage)</code> will
     * no longer be set as the destination.  In other words, this
     * method may be thought of as calling
     * <code>setDestination((BufferedImage)null)</code>.
     *
     * <p> When writing, the destination type maybe used to determine
     * the color type of the image.  The <code>SampleModel</code>
     * information will be ignored, and may be <code>null</code>.  For
     * example, a 4-banded image could represent either CMYK or RGBA
     * data.  If a destination type is set, its
     * <code>ColorModel</code> will override any
     * <code>ColorModel</code> on the image itself.  This is crucial
     * when <code>setSourceBands</code> is used since the image's
     * <code>ColorModel</code> will refer to the entire image rather
     * than to the subset of bands being written.
     *
     * <p>
     * 使用<code> ImageTypeSpecifier </code>设置目标图像的所需图像类型。
     * 
     *  <p>当读取时,如果使用此方法设置了目的地布局,则每次调用<code> ImageReader </code> <code> read </code>方法都会返回一个新的<code> Buffered
     * Image <代码>使用由提供的类型说明符指定的格式。
     * 作为副作用,由<code> ImageReadParam.setDestination(BufferedImage)</code>设置的任何目标<code> BufferedImage </code>将
     * 不再设置为目标。
     * 换句话说,该方法可以被认为是调用<code> setDestination((BufferedImage)null)</code>。
     * 
     *  <p>写入时,目标类型可用于确定图像的颜色类型。 <code> SampleModel </code>信息将被忽略,并且可以是<code> null </code>。
     * 例如,4带图像可以表示CMYK或RGBA数据。如果设置了目标类型,其<code> ColorModel </code>将覆盖图像本身上的任何<code> ColorModel </code>。
     * 当使用<code> setSourceBands </code>时,这是至关重要的,因为图像的<code> ColorModel </code>将引用整个图像,而不是正在写入的波段子集。
     * 
     * 
     * @param destinationType the <code>ImageTypeSpecifier</code> to
     * be used to determine the destination layout and color type.
     *
     * @see #getDestinationType
     */
    public void setDestinationType(ImageTypeSpecifier destinationType) {
        this.destinationType = destinationType;
    }

    /**
     * Returns the type of image to be returned by the read, if one
     * was set by a call to
     * <code>setDestination(ImageTypeSpecifier)</code>, as an
     * <code>ImageTypeSpecifier</code>.  If none was set,
     * <code>null</code> is returned.
     *
     * <p>
     *  如果通过调用<code> setDestination(ImageTypeSpecifier)</code>设置为<code> ImageTypeSpecifier </code>,则返回读取返回的图
     * 像类型。
     * 如果没有设置,则返回<code> null </code>。
     * 
     * 
     * @return an <code>ImageTypeSpecifier</code> describing the
     * destination type, or <code>null</code>.
     *
     * @see #setDestinationType
     */
    public ImageTypeSpecifier getDestinationType() {
        return destinationType;
    }

    /**
     * Specifies the offset in the destination image at which future
     * decoded pixels are to be placed, when reading, or where a
     * region will be written, when writing.
     *
     * <p> When reading, the region to be written within the
     * destination <code>BufferedImage</code> will start at this
     * offset and have a width and height determined by the source
     * region of interest, the subsampling parameters, and the
     * destination bounds.
     *
     * <p> Normal writes are not affected by this method, only writes
     * performed using <code>ImageWriter.replacePixels</code>.  For
     * such writes, the offset specified is within the output stream
     * image whose pixels are being modified.
     *
     * <p> There is no <code>unsetDestinationOffset</code> method;
     * simply call <code>setDestinationOffset(new Point(0, 0))</code> to
     * restore default values.
     *
     * <p>
     * 指定目标图像中将在放置未来解码像素时,读取时或写入时写入区域的偏移量。
     * 
     *  <p>当读取时,要在目标<code> BufferedImage </code>内写入的区域将在此偏移处开始,并具有由感兴趣的源区域,子采样参数和目标边界确定的宽度和高度。
     * 
     *  <p>正常写入不受此方法影响,只使用<code> ImageWriter.replacePixels </code>执行写入。对于这样的写入,指定的偏移在其像素被修改的输出流图像内。
     * 
     *  <p>没有<code> unsetDestinationOffset </code>方法;只需调用<code> setDestinationOffset(new Point(0,0))</code>恢
     * 复默认值。
     * 
     * 
     * @param destinationOffset the offset in the destination, as a
     * <code>Point</code>.
     *
     * @exception IllegalArgumentException if
     * <code>destinationOffset</code> is <code>null</code>.
     *
     * @see #getDestinationOffset
     * @see ImageWriter#replacePixels
     */
    public void setDestinationOffset(Point destinationOffset) {
        if (destinationOffset == null) {
            throw new IllegalArgumentException("destinationOffset == null!");
        }
        this.destinationOffset = (Point)destinationOffset.clone();
    }

    /**
     * Returns the offset in the destination image at which pixels are
     * to be placed.
     *
     * <p> If <code>setDestinationOffsets</code> has not been called,
     * a <code>Point</code> with zero X and Y values is returned
     * (which is the correct value).
     *
     * <p>
     *  返回目标图像中要放置像素的偏移量。
     * 
     *  <p>如果未调用<code> setDestinationOffsets </code>,则会返回一个具有零X和Y值的<code> Point </code>(这是正确的值)。
     * 
     * 
     * @return the destination offset as a <code>Point</code>.
     *
     * @see #setDestinationOffset
     */
    public Point getDestinationOffset() {
        return (Point)destinationOffset.clone();
    }

    /**
     * Sets the <code>IIOParamController</code> to be used
     * to provide settings for this <code>IIOParam</code>
     * object when the <code>activateController</code> method
     * is called, overriding any default controller.  If the
     * argument is <code>null</code>, no controller will be
     * used, including any default.  To restore the default, use
     * <code>setController(getDefaultController())</code>.
     *
     * <p>
     *  当调用<code> activateController </code>方法时,设置<code> IIOParamController </code>用于为<code> IIOParam </code>
     * 对象提供设置,覆盖任何默认控制器。
     * 如果参数是<code> null </code>,则不使用控制器,包括任何默认值。
     * 要恢复默认值,使用<code> setController(getDefaultController())</code>。
     * 
     * 
     * @param controller An appropriate
     * <code>IIOParamController</code>, or <code>null</code>.
     *
     * @see IIOParamController
     * @see #getController
     * @see #getDefaultController
     * @see #hasController
     * @see #activateController()
     */
    public void setController(IIOParamController controller) {
        this.controller = controller;
    }

    /**
     * Returns whatever <code>IIOParamController</code> is currently
     * installed.  This could be the default if there is one,
     * <code>null</code>, or the argument of the most recent call
     * to <code>setController</code>.
     *
     * <p>
     * 返回当前安装的<code> IIOParamController </code>。
     * 如果有一个<code> null </code>,或者最近一次调用<code> setController </code>的参数,这可能是默认值。
     * 
     * 
     * @return the currently installed
     * <code>IIOParamController</code>, or <code>null</code>.
     *
     * @see IIOParamController
     * @see #setController
     * @see #getDefaultController
     * @see #hasController
     * @see #activateController()
     */
    public IIOParamController getController() {
        return controller;
    }

    /**
     * Returns the default <code>IIOParamController</code>, if there
     * is one, regardless of the currently installed controller.  If
     * there is no default controller, returns <code>null</code>.
     *
     * <p>
     *  返回默认<code> IIOParamController </code>(如果有),而不管当前安装的控制器。如果没有默认控制器,则返回<code> null </code>。
     * 
     * 
     * @return the default <code>IIOParamController</code>, or
     * <code>null</code>.
     *
     * @see IIOParamController
     * @see #setController(IIOParamController)
     * @see #getController
     * @see #hasController
     * @see #activateController()
     */
    public IIOParamController getDefaultController() {
        return defaultController;
    }

    /**
     * Returns <code>true</code> if there is a controller installed
     * for this <code>IIOParam</code> object.  This will return
     * <code>true</code> if <code>getController</code> would not
     * return <code>null</code>.
     *
     * <p>
     *  如果为此<code> IIOParam </code>对象安装了控制器,则返回<code> true </code>。
     * 如果<code> getController </code>不会返回<code> null </code>,则会返回<code> true </code>。
     * 
     * 
     * @return <code>true</code> if a controller is installed.
     *
     * @see IIOParamController
     * @see #setController(IIOParamController)
     * @see #getController
     * @see #getDefaultController
     * @see #activateController()
     */
    public boolean hasController() {
        return (controller != null);
    }

    /**
     * Activates the installed <code>IIOParamController</code> for
     * this <code>IIOParam</code> object and returns the resulting
     * value.  When this method returns <code>true</code>, all values
     * for this <code>IIOParam</code> object will be ready for the
     * next read or write operation.  If <code>false</code> is
     * returned, no settings in this object will have been disturbed
     * (<i>i.e.</i>, the user canceled the operation).
     *
     * <p> Ordinarily, the controller will be a GUI providing a user
     * interface for a subclass of <code>IIOParam</code> for a
     * particular plug-in.  Controllers need not be GUIs, however.
     *
     * <p>
     *  激活为此<code> IIOParam </code>对象安装的<code> IIOParamController </code>,并返回结果值。
     * 当此方法返回<code> true </code>时,此<code> IIOParam </code>对象的所有值都将准备好进行下一个读取或写入操作。
     * 如果返回<code> false </code>,则此对象中的任何设置都不会受到干扰(即</i>,用户取消操作)。
     * 
     * @return <code>true</code> if the controller completed normally.
     *
     * @exception IllegalStateException if there is no controller
     * currently installed.
     *
     * @see IIOParamController
     * @see #setController(IIOParamController)
     * @see #getController
     * @see #getDefaultController
     * @see #hasController
     */
    public boolean activateController() {
        if (!hasController()) {
            throw new IllegalStateException("hasController() == false!");
        }
        return getController().activate(this);
    }
}
