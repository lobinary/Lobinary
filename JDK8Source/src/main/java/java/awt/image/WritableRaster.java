/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

/* ****************************************************************
 ******************************************************************
 ******************************************************************
 *** COPYRIGHT (c) Eastman Kodak Company, 1997
 *** As  an unpublished  work pursuant to Title 17 of the United
 *** States Code.  All rights reserved.
 ******************************************************************
 ******************************************************************
 * <p>
 *  **************************************************** ************** ********************************
 * **** **************************** * COPYRIGHT(c)Eastman Kodak Company,1997 *根据United Nations Title 17
 * 的未发表作品*国家代码。
 * 版权所有。
 *  **************************************************** ************** ********************************
 * **** ****************************。
 * 版权所有。
 * 
 * 
 ******************************************************************/

package java.awt.image;
import java.awt.Rectangle;
import java.awt.Point;

/**
 * This class extends Raster to provide pixel writing capabilities.
 * Refer to the class comment for Raster for descriptions of how
 * a Raster stores pixels.
 *
 * <p> The constructors of this class are protected.  To instantiate
 * a WritableRaster, use one of the createWritableRaster factory methods
 * in the Raster class.
 * <p>
 *  这个类扩展了Raster以提供像素写入功能。有关栅格存储像素的说明,请参阅Raster的类注释。
 * 
 *  <p>此类的构造函数受保护。要实例化WritableRaster,请使用Raster类中的一个createWritableRaster工厂方法。
 * 
 */
public class WritableRaster extends Raster {

    /**
     *  Constructs a WritableRaster with the given SampleModel.  The
     *  WritableRaster's upper left corner is origin and it is the
     *  same size as the  SampleModel.  A DataBuffer large enough to
     *  describe the WritableRaster is automatically created.
     * <p>
     *  使用给定的SampleModel构造WritableRaster。 WritableRaster的左上角是原点,它与SampleModel的大小相同。
     * 自动创建大到足以描述WritableRaster的DataBuffer。
     * 
     * 
     *  @param sampleModel     The SampleModel that specifies the layout.
     *  @param origin          The Point that specifies the origin.
     *  @throws RasterFormatException if computing either
     *          <code>origin.x + sampleModel.getWidth()</code> or
     *          <code>origin.y + sampleModel.getHeight()</code> results
     *          in integer overflow
     */
    protected WritableRaster(SampleModel sampleModel,
                             Point origin) {
        this(sampleModel,
             sampleModel.createDataBuffer(),
             new Rectangle(origin.x,
                           origin.y,
                           sampleModel.getWidth(),
                           sampleModel.getHeight()),
             origin,
             null);
    }

    /**
     *  Constructs a WritableRaster with the given SampleModel and DataBuffer.
     *  The WritableRaster's upper left corner is origin and it is the same
     *  size as the SampleModel.  The DataBuffer is not initialized and must
     *  be compatible with SampleModel.
     * <p>
     *  使用给定的SampleModel和DataBuffer构造WritableRaster。 WritableRaster的左上角是原点,它与SampleModel的大小相同。
     *  DataBuffer未初始化,必须与SampleModel兼容。
     * 
     * 
     *  @param sampleModel     The SampleModel that specifies the layout.
     *  @param dataBuffer      The DataBuffer that contains the image data.
     *  @param origin          The Point that specifies the origin.
     *  @throws RasterFormatException if computing either
     *          <code>origin.x + sampleModel.getWidth()</code> or
     *          <code>origin.y + sampleModel.getHeight()</code> results
     *          in integer overflow
     */
    protected WritableRaster(SampleModel sampleModel,
                             DataBuffer dataBuffer,
                             Point origin) {
        this(sampleModel,
             dataBuffer,
             new Rectangle(origin.x,
                           origin.y,
                           sampleModel.getWidth(),
                           sampleModel.getHeight()),
             origin,
             null);
    }

    /**
     * Constructs a WritableRaster with the given SampleModel, DataBuffer,
     * and parent.  aRegion specifies the bounding rectangle of the new
     * Raster.  When translated into the base Raster's coordinate
     * system, aRegion must be contained by the base Raster.
     * (The base Raster is the Raster's ancestor which has no parent.)
     * sampleModelTranslate specifies the sampleModelTranslateX and
     * sampleModelTranslateY values of the new Raster.
     *
     * Note that this constructor should generally be called by other
     * constructors or create methods, it should not be used directly.
     * <p>
     * 构造具有给定SampleModel,DataBuffer和父的WritableRaster。 aRegion指定新栅格的边界矩形。当转换为基本光栅的坐标系时,基本光栅必须包含一个区域。
     *  (基本栅格是栅格的祖先,没有父元素。)sampleModelTranslate指定新栅格的sampleModelTranslateX和sampleModelTranslateY值。
     * 
     *  注意,这个构造函数通常应该由其他构造函数或create方法调用,不应该直接使用。
     * 
     * 
     * @param sampleModel     The SampleModel that specifies the layout.
     * @param dataBuffer      The DataBuffer that contains the image data.
     * @param aRegion         The Rectangle that specifies the image area.
     * @param sampleModelTranslate  The Point that specifies the translation
     *                        from SampleModel to Raster coordinates.
     * @param parent          The parent (if any) of this raster.
     * @throws RasterFormatException if <code>aRegion</code> has width
     *         or height less than or equal to zero, or computing either
     *         <code>aRegion.x + aRegion.width</code> or
     *         <code>aRegion.y + aRegion.height</code> results in integer
     *         overflow
     */
    protected WritableRaster(SampleModel sampleModel,
                             DataBuffer dataBuffer,
                             Rectangle aRegion,
                             Point sampleModelTranslate,
                             WritableRaster parent){
        super(sampleModel,dataBuffer,aRegion,sampleModelTranslate,parent);
    }

    /** Returns the parent WritableRaster (if any) of this WritableRaster,
     *  or else null.
     * <p>
     *  否则为null。
     * 
     * 
     *  @return the parent of this <code>WritableRaster</code>, or
     *          <code>null</code>.
     */
    public WritableRaster getWritableParent() {
        return (WritableRaster)parent;
    }

    /**
     * Create a WritableRaster with the same size, SampleModel and DataBuffer
     * as this one, but with a different location.  The new WritableRaster
     * will possess a reference to the current WritableRaster, accessible
     * through its getParent() and getWritableParent() methods.
     *
     * <p>
     *  创建一个与此大小相同的WritableRaster,SampleModel和DataBuffer,但使用不同的位置。
     * 新的WritableRaster将拥有对当前WritableRaster的引用,可通过其getParent()和getWritableParent()方法访问。
     * 
     * 
     * @param childMinX X coord of the upper left corner of the new Raster.
     * @param childMinY Y coord of the upper left corner of the new Raster.
     * @return a <code>WritableRaster</code> the same as this one except
     *         for the specified location.
     * @throws RasterFormatException if  computing either
     *         <code>childMinX + this.getWidth()</code> or
     *         <code>childMinY + this.getHeight()</code> results in integer
     *         overflow
     */
    public WritableRaster createWritableTranslatedChild(int childMinX,
                                                        int childMinY) {
        return createWritableChild(minX,minY,width,height,
                                   childMinX,childMinY,null);
    }

    /**
     * Returns a new WritableRaster which shares all or part of this
     * WritableRaster's DataBuffer.  The new WritableRaster will
     * possess a reference to the current WritableRaster, accessible
     * through its getParent() and getWritableParent() methods.
     *
     * <p> The parentX, parentY, width and height parameters form a
     * Rectangle in this WritableRaster's coordinate space, indicating
     * the area of pixels to be shared.  An error will be thrown if
     * this Rectangle is not contained with the bounds of the current
     * WritableRaster.
     *
     * <p> The new WritableRaster may additionally be translated to a
     * different coordinate system for the plane than that used by the current
     * WritableRaster.  The childMinX and childMinY parameters give
     * the new (x, y) coordinate of the upper-left pixel of the
     * returned WritableRaster; the coordinate (childMinX, childMinY)
     * in the new WritableRaster will map to the same pixel as the
     * coordinate (parentX, parentY) in the current WritableRaster.
     *
     * <p> The new WritableRaster may be defined to contain only a
     * subset of the bands of the current WritableRaster, possibly
     * reordered, by means of the bandList parameter.  If bandList is
     * null, it is taken to include all of the bands of the current
     * WritableRaster in their current order.
     *
     * <p> To create a new WritableRaster that contains a subregion of
     * the current WritableRaster, but shares its coordinate system
     * and bands, this method should be called with childMinX equal to
     * parentX, childMinY equal to parentY, and bandList equal to
     * null.
     *
     * <p>
     *  返回一个新的WritableRaster,它共享此WritableRaster的DataBuffer的全部或部分。
     * 新的WritableRaster将拥有对当前WritableRaster的引用,可通过其getParent()和getWritableParent()方法访问。
     * 
     *  <p> parentX,parentY,width和height参数在WritableRaster的坐标空间中形成一个Rectangle,表示要共享的像素区域。
     * 如果此Rectangle不包含在当前WritableRaster的边界中,则会抛出错误。
     * 
     * <p>新的WritableRaster可以另外转换为与当前WritableRaster所使用的平面不同的坐标系。
     *  childMinX和childMinY参数给出了返回的WritableRaster的左上角像素的新(x,y)坐标;新WritableRaster中的坐标(childMinX,childMinY)将映射
     * 到与当前WritableRaster中的坐标(parentX,parentY)相同的像素。
     * <p>新的WritableRaster可以另外转换为与当前WritableRaster所使用的平面不同的坐标系。
     * 
     *  新的WritableRaster可以被定义为仅包含当前WritableRaster的频带的子集,可能通过bandList参数重新排序。
     * 如果bandList为null,则它包括当前WritableRaster的当前顺序中的所有频带。
     * 
     *  <p>要创建一个包含当前WritableRaster的子区域但是共享其坐标系和带的新WritableRaster,应该使用childMinX等于parentX,childMinY等于parentY,并
     * 且bandList等于null来调用此方法。
     * 
     * 
     * @param parentX    X coordinate of the upper left corner in this
     *                   WritableRaster's coordinates.
     * @param parentY    Y coordinate of the upper left corner in this
     *                   WritableRaster's coordinates.
     * @param w          Width of the region starting at (parentX, parentY).
     * @param h          Height of the region starting at (parentX, parentY).
     * @param childMinX  X coordinate of the upper left corner of
     *                   the returned WritableRaster.
     * @param childMinY  Y coordinate of the upper left corner of
     *                   the returned WritableRaster.
     * @param bandList   Array of band indices, or null to use all bands.
     * @return a <code>WritableRaster</code> sharing all or part of the
     *         <code>DataBuffer</code> of this <code>WritableRaster</code>.
     * @exception RasterFormatException if the subregion is outside of the
     *                               raster bounds.
     * @throws RasterFormatException if <code>w</code> or
     *         <code>h</code>
     *         is less than or equal to zero, or computing any of
     *         <code>parentX + w</code>, <code>parentY + h</code>,
     *         <code>childMinX + w</code>, or
     *         <code>childMinY + h</code> results in integer
     *         overflow
     */
    public WritableRaster createWritableChild(int parentX, int parentY,
                                              int w, int h,
                                              int childMinX, int childMinY,
                                              int bandList[]) {
        if (parentX < this.minX) {
            throw new RasterFormatException("parentX lies outside raster");
        }
        if (parentY < this.minY) {
            throw new RasterFormatException("parentY lies outside raster");
        }
        if ((parentX+w < parentX) || (parentX+w > this.width + this.minX)) {
            throw new RasterFormatException("(parentX + width) is outside raster");
        }
        if ((parentY+h < parentY) || (parentY+h > this.height + this.minY)) {
            throw new RasterFormatException("(parentY + height) is outside raster");
        }

        SampleModel sm;
        // Note: the SampleModel for the child Raster should have the same
        // width and height as that for the parent, since it represents
        // the physical layout of the pixel data.  The child Raster's width
        // and height represent a "virtual" view of the pixel data, so
        // they may be different than those of the SampleModel.
        if (bandList != null) {
            sm = sampleModel.createSubsetSampleModel(bandList);
        }
        else {
            sm = sampleModel;
        }

        int deltaX = childMinX - parentX;
        int deltaY = childMinY - parentY;

        return new WritableRaster(sm,
                                  getDataBuffer(),
                                  new Rectangle(childMinX,childMinY,
                                                w, h),
                                  new Point(sampleModelTranslateX+deltaX,
                                            sampleModelTranslateY+deltaY),
                                  this);
    }

    /**
     * Sets the data for a single pixel from a
     * primitive array of type TransferType.  For image data supported by
     * the Java 2D(tm) API, this will be one of DataBuffer.TYPE_BYTE,
     * DataBuffer.TYPE_USHORT, DataBuffer.TYPE_INT, DataBuffer.TYPE_SHORT,
     * DataBuffer.TYPE_FLOAT, or DataBuffer.TYPE_DOUBLE.  Data in the array
     * may be in a packed format, thus increasing efficiency for data
     * transfers.
     * An ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds, or if inData is not large enough to hold the pixel data.
     * However, explicit bounds checking is not guaranteed.
     * A ClassCastException will be thrown if the input object is not null
     * and references anything other than an array of TransferType.
     * <p>
     * 从TransferType类型的基本数组设置单个像素的数据。
     * 对于Java 2D(tm)API支持的图像数据,这将是DataBuffer.TYPE_BYTE,DataBuffer.TYPE_USHORT,DataBuffer.TYPE_INT,DataBuffer
     * .TYPE_SHORT,DataBuffer.TYPE_FLOAT或DataBuffer.TYPE_DOUBLE之一。
     * 从TransferType类型的基本数组设置单个像素的数据。阵列中的数据可以是打包格式,因此提高了数据传输的效率。
     * 如果坐标不在边界中,或者如果inData不足以容纳像素数据,则可能会抛出ArrayIndexOutOfBoundsException。但是,不能保证显式边界检查。
     * 如果输入对象不为null并且引用TransferType数组以外的任何对象,则将抛出ClassCastException。
     * 
     * 
     * @see java.awt.image.SampleModel#setDataElements(int, int, Object, DataBuffer)
     * @param x        The X coordinate of the pixel location.
     * @param y        The Y coordinate of the pixel location.
     * @param inData   An object reference to an array of type defined by
     *                 getTransferType() and length getNumDataElements()
     *                 containing the pixel data to place at x,y.
     *
     * @throws ArrayIndexOutOfBoundsException if the coordinates are not
     * in bounds, or if inData is too small to hold the input.
     */
    public void setDataElements(int x, int y, Object inData) {
        sampleModel.setDataElements(x-sampleModelTranslateX,
                                    y-sampleModelTranslateY,
                                    inData, dataBuffer);
    }

    /**
     * Sets the data for a rectangle of pixels from an input Raster.
     * The input Raster must be compatible with this WritableRaster
     * in that they must have the same number of bands, corresponding bands
     * must have the same number of bits per sample, the TransferTypes
     * and NumDataElements must be the same, and the packing used by
     * the getDataElements/setDataElements must be identical.
     * An ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * However, explicit bounds checking is not guaranteed.
     * <p>
     *  设置来自输入栅格的像素矩形的数据。
     * 输入栅格必须与此WritableRaster兼容,因为它们必须具有相同数量的波段,相应的波段必须具有每个样本相同的位数,TransferTypes和NumDataElements必须相同,并且getDa
     * taElements / setDataElements使用的打包必须相同。
     *  设置来自输入栅格的像素矩形的数据。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。但是,不能保证显式边界检查。
     * 
     * 
     * @param x        The X coordinate of the pixel location.
     * @param y        The Y coordinate of the pixel location.
     * @param inRaster Raster containing data to place at x,y.
     *
     * @throws NullPointerException if inRaster is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates are not
     * in bounds.
     */
    public void setDataElements(int x, int y, Raster inRaster) {
        int dstOffX = x+inRaster.getMinX();
        int dstOffY = y+inRaster.getMinY();
        int width  = inRaster.getWidth();
        int height = inRaster.getHeight();
        if ((dstOffX < this.minX) || (dstOffY < this.minY) ||
            (dstOffX + width > this.minX + this.width) ||
            (dstOffY + height > this.minY + this.height)) {
            throw new ArrayIndexOutOfBoundsException
                ("Coordinate out of bounds!");
        }

        int srcOffX = inRaster.getMinX();
        int srcOffY = inRaster.getMinY();
        Object tdata = null;

        for (int startY=0; startY < height; startY++) {
            tdata = inRaster.getDataElements(srcOffX, srcOffY+startY,
                                             width, 1, tdata);
            setDataElements(dstOffX, dstOffY+startY,
                            width, 1, tdata);
        }
    }

    /**
     * Sets the data for a rectangle of pixels from a
     * primitive array of type TransferType.  For image data supported by
     * the Java 2D API, this will be one of DataBuffer.TYPE_BYTE,
     * DataBuffer.TYPE_USHORT, DataBuffer.TYPE_INT, DataBuffer.TYPE_SHORT,
     * DataBuffer.TYPE_FLOAT, or DataBuffer.TYPE_DOUBLE.  Data in the array
     * may be in a packed format, thus increasing efficiency for data
     * transfers.
     * An ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds, or if inData is not large enough to hold the pixel data.
     * However, explicit bounds checking is not guaranteed.
     * A ClassCastException will be thrown if the input object is not null
     * and references anything other than an array of TransferType.
     * <p>
     * 从TransferType类型的基本数组设置像素矩形的数据。
     * 对于Java 2D API支持的图像数据,这将是DataBuffer.TYPE_BYTE,DataBuffer.TYPE_USHORT,DataBuffer.TYPE_INT,DataBuffer.TY
     * PE_SHORT,DataBuffer.TYPE_FLOAT或DataBuffer.TYPE_DOUBLE之一。
     * 从TransferType类型的基本数组设置像素矩形的数据。阵列中的数据可以是打包格式,因此提高了数据传输的效率。
     * 如果坐标不在边界中,或者如果inData不足以容纳像素数据,则可能会抛出ArrayIndexOutOfBoundsException。但是,不能保证显式边界检查。
     * 如果输入对象不为null并且引用TransferType数组以外的任何对象,则将抛出ClassCastException。
     * 
     * 
     * @see java.awt.image.SampleModel#setDataElements(int, int, int, int, Object, DataBuffer)
     * @param x        The X coordinate of the upper left pixel location.
     * @param y        The Y coordinate of the upper left pixel location.
     * @param w        Width of the pixel rectangle.
     * @param h        Height of the pixel rectangle.
     * @param inData   An object reference to an array of type defined by
     *                 getTransferType() and length w*h*getNumDataElements()
     *                 containing the pixel data to place between x,y and
     *                 x+w-1, y+h-1.
     *
     * @throws NullPointerException if inData is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates are not
     * in bounds, or if inData is too small to hold the input.
     */
    public void setDataElements(int x, int y, int w, int h, Object inData) {
        sampleModel.setDataElements(x-sampleModelTranslateX,
                                    y-sampleModelTranslateY,
                                    w,h,inData,dataBuffer);
    }

    /**
     * Copies pixels from Raster srcRaster to this WritableRaster.  Each pixel
     * in srcRaster is copied to the same x,y address in this raster, unless
     * the address falls outside the bounds of this raster.  srcRaster
     * must have the same number of bands as this WritableRaster.  The
     * copy is a simple copy of source samples to the corresponding destination
     * samples.
     * <p>
     * If all samples of both source and destination Rasters are of
     * integral type and less than or equal to 32 bits in size, then calling
     * this method is equivalent to executing the following code for all
     * <code>x,y</code> addresses valid in both Rasters.
     * <pre>{@code
     *       Raster srcRaster;
     *       WritableRaster dstRaster;
     *       for (int b = 0; b < srcRaster.getNumBands(); b++) {
     *           dstRaster.setSample(x, y, b, srcRaster.getSample(x, y, b));
     *       }
     * }</pre>
     * Thus, when copying an integral type source to an integral type
     * destination, if the source sample size is greater than the destination
     * sample size for a particular band, the high order bits of the source
     * sample are truncated.  If the source sample size is less than the
     * destination size for a particular band, the high order bits of the
     * destination are zero-extended or sign-extended depending on whether
     * srcRaster's SampleModel treats the sample as a signed or unsigned
     * quantity.
     * <p>
     * When copying a float or double source to an integral type destination,
     * each source sample is cast to the destination type.  When copying an
     * integral type source to a float or double destination, the source
     * is first converted to a 32-bit int (if necessary), using the above
     * rules for integral types, and then the int is cast to float or
     * double.
     * <p>
     * <p>
     *  将像素从Raster srcRaster复制到此WritableRaster。 srcRaster中的每个像素都复制到此栅格中的同一x,y地址,除非地址超出此栅格的边界。
     *  srcRaster必须具有与此WritableRaster相同的波段数。副本是源样本到相应目的地样本的简单副本。
     * <p>
     * 如果源和目标光栅的所有样本都是整数类型且小于或等于32位大小,则调用此方法等同于对所有<code> x,y </code>地址有效的所有<code> x,y </code>两个栅格。
     *  <pre> {@ code Raster srcRaster; WritableRaster dstRaster; for(int b = 0; b <srcRaster.getNumBands(); b ++){dstRaster.setSample(x,y,b,srcRaster.getSample(x,y,b)); }} </pre>
     * 因此,当将整数类型源复制到整数类型目的地时,如果源样本大小大于特定频带的目标样本大小,则源样本的高阶位将被截断。
     * 如果源和目标光栅的所有样本都是整数类型且小于或等于32位大小,则调用此方法等同于对所有<code> x,y </code>地址有效的所有<code> x,y </code>两个栅格。
     * 如果源样本大小小于特定频带的目标大小,则根据srcRaster的SampleModel将样本视为有符号数还是无符号数,目标的高阶位为零扩展或符号扩展。
     * <p>
     *  将浮点或双源复制到整数类型目标时,每个源样本都将转换为目标类型。
     * 当将整数类型源复制到浮点或双目的地时,首先使用上述整数类型规则将源转换为32位int(如果需要),然后将int转换为float或double。
     * <p>
     * 
     * @param srcRaster  The  Raster from which to copy pixels.
     *
     * @throws NullPointerException if srcRaster is null.
     */
    public void setRect(Raster srcRaster) {
        setRect(0,0,srcRaster);
    }

    /**
     * Copies pixels from Raster srcRaster to this WritableRaster.
     * For each (x, y) address in srcRaster, the corresponding pixel
     * is copied to address (x+dx, y+dy) in this WritableRaster,
     * unless (x+dx, y+dy) falls outside the bounds of this raster.
     * srcRaster must have the same number of bands as this WritableRaster.
     * The copy is a simple copy of source samples to the corresponding
     * destination samples.  For details, see
     * {@link WritableRaster#setRect(Raster)}.
     *
     * <p>
     * 将像素从Raster srcRaster复制到此WritableRaster。
     * 对于srcRaster中的每个(x,y)地址,相应的像素被复制到此WritableRaster中的地址(x + dx,y + dy),除非(x + dx,y + dy)落在该栅格的边界之外。
     *  srcRaster必须具有与此WritableRaster相同的波段数。副本是源样本到相应目的地样本的简单副本。
     * 有关详细信息,请参阅{@link WritableRaster#setRect(Raster)}。
     * 
     * 
     * @param dx        The X translation factor from src space to dst space
     *                  of the copy.
     * @param dy        The Y translation factor from src space to dst space
     *                  of the copy.
     * @param srcRaster The Raster from which to copy pixels.
     *
     * @throws NullPointerException if srcRaster is null.
     */
    public void setRect(int dx, int dy, Raster srcRaster) {
        int width  = srcRaster.getWidth();
        int height = srcRaster.getHeight();
        int srcOffX = srcRaster.getMinX();
        int srcOffY = srcRaster.getMinY();
        int dstOffX = dx+srcOffX;
        int dstOffY = dy+srcOffY;

        // Clip to this raster
        if (dstOffX < this.minX) {
            int skipX = this.minX - dstOffX;
            width -= skipX;
            srcOffX += skipX;
            dstOffX = this.minX;
        }
        if (dstOffY < this.minY) {
            int skipY = this.minY - dstOffY;
            height -= skipY;
            srcOffY += skipY;
            dstOffY = this.minY;
        }
        if (dstOffX+width > this.minX+this.width) {
            width = this.minX + this.width - dstOffX;
        }
        if (dstOffY+height > this.minY+this.height) {
            height = this.minY + this.height - dstOffY;
        }

        if (width <= 0 || height <= 0) {
            return;
        }

        switch (srcRaster.getSampleModel().getDataType()) {
        case DataBuffer.TYPE_BYTE:
        case DataBuffer.TYPE_SHORT:
        case DataBuffer.TYPE_USHORT:
        case DataBuffer.TYPE_INT:
            int[] iData = null;
            for (int startY=0; startY < height; startY++) {
                // Grab one scanline at a time
                iData =
                    srcRaster.getPixels(srcOffX, srcOffY+startY, width, 1,
                                        iData);
                setPixels(dstOffX, dstOffY+startY, width, 1, iData);
            }
            break;

        case DataBuffer.TYPE_FLOAT:
            float[] fData = null;
            for (int startY=0; startY < height; startY++) {
                fData =
                    srcRaster.getPixels(srcOffX, srcOffY+startY, width, 1,
                                        fData);
                setPixels(dstOffX, dstOffY+startY, width, 1, fData);
            }
            break;

        case DataBuffer.TYPE_DOUBLE:
            double[] dData = null;
            for (int startY=0; startY < height; startY++) {
                // Grab one scanline at a time
                dData =
                    srcRaster.getPixels(srcOffX, srcOffY+startY, width, 1,
                                        dData);
                setPixels(dstOffX, dstOffY+startY, width, 1, dData);
            }
            break;
        }
    }

    /**
     * Sets a pixel in the DataBuffer using an int array of samples for input.
     * An ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * However, explicit bounds checking is not guaranteed.
     * <p>
     *  设置DataBuffer中的一个像素,使用int数组样本进行输入。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。但是,不能保证显式边界检查。
     * 
     * 
     * @param x      The X coordinate of the pixel location.
     * @param y      The Y coordinate of the pixel location.
     * @param iArray The input samples in a int array.
     *
     * @throws NullPointerException if iArray is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates are not
     * in bounds, or if iArray is too small to hold the input.
     */
    public void setPixel(int x, int y, int iArray[]) {
        sampleModel.setPixel(x-sampleModelTranslateX,y-sampleModelTranslateY,
                             iArray,dataBuffer);
    }

    /**
     * Sets a pixel in the DataBuffer using a float array of samples for input.
     * An ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * However, explicit bounds checking is not guaranteed.
     * <p>
     *  在DataBuffer中使用浮点数组来设置输入的像素。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。但是,不能保证显式边界检查。
     * 
     * 
     * @param x      The X coordinate of the pixel location.
     * @param y      The Y coordinate of the pixel location.
     * @param fArray The input samples in a float array.
     *
     * @throws NullPointerException if fArray is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates are not
     * in bounds, or if fArray is too small to hold the input.
     */
    public void setPixel(int x, int y, float fArray[]) {
        sampleModel.setPixel(x-sampleModelTranslateX,y-sampleModelTranslateY,
                             fArray,dataBuffer);
    }

    /**
     * Sets a pixel in the DataBuffer using a double array of samples for input.
     * An ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * However, explicit bounds checking is not guaranteed.
     * <p>
     *  使用双数组样本在DataBuffer中设置像素以进行输入。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。但是,不能保证显式边界检查。
     * 
     * 
     * @param x      The X coordinate of the pixel location.
     * @param y      The Y coordinate of the pixel location.
     * @param dArray The input samples in a double array.
     *
     * @throws NullPointerException if dArray is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates are not
     * in bounds, or if dArray is too small to hold the input.
     */
    public void setPixel(int x, int y, double dArray[]) {
        sampleModel.setPixel(x-sampleModelTranslateX,y-sampleModelTranslateY,
                             dArray,dataBuffer);
    }

    /**
     * Sets all samples for a rectangle of pixels from an int array containing
     * one sample per array element.
     * An ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * However, explicit bounds checking is not guaranteed.
     * <p>
     *  为包含每个数组元素一个样本的int数组设置像素矩形的所有样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。但是,不能保证显式边界检查。
     * 
     * 
     * @param x        The X coordinate of the upper left pixel location.
     * @param y        The Y coordinate of the upper left pixel location.
     * @param w        Width of the pixel rectangle.
     * @param h        Height of the pixel rectangle.
     * @param iArray   The input int pixel array.
     *
     * @throws NullPointerException if iArray is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates are not
     * in bounds, or if iArray is too small to hold the input.
     */
    public void setPixels(int x, int y, int w, int h, int iArray[]) {
        sampleModel.setPixels(x-sampleModelTranslateX,y-sampleModelTranslateY,
                              w,h,iArray,dataBuffer);
    }

    /**
     * Sets all samples for a rectangle of pixels from a float array containing
     * one sample per array element.
     * An ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * However, explicit bounds checking is not guaranteed.
     * <p>
     * 为包含每个数组元素一个样本的float数组设置像素矩形的所有样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。但是,不能保证显式边界检查。
     * 
     * 
     * @param x        The X coordinate of the upper left pixel location.
     * @param y        The Y coordinate of the upper left pixel location.
     * @param w        Width of the pixel rectangle.
     * @param h        Height of the pixel rectangle.
     * @param fArray   The input float pixel array.
     *
     * @throws NullPointerException if fArray is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates are not
     * in bounds, or if fArray is too small to hold the input.
     */
    public void setPixels(int x, int y, int w, int h, float fArray[]) {
        sampleModel.setPixels(x-sampleModelTranslateX,y-sampleModelTranslateY,
                              w,h,fArray,dataBuffer);
    }

    /**
     * Sets all samples for a rectangle of pixels from a double array containing
     * one sample per array element.
     * An ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * However, explicit bounds checking is not guaranteed.
     * <p>
     *  为包含每个数组元素一个样本的双阵列设置像素矩形的所有样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。但是,不能保证显式边界检查。
     * 
     * 
     * @param x        The X coordinate of the upper left pixel location.
     * @param y        The Y coordinate of the upper left pixel location.
     * @param w        Width of the pixel rectangle.
     * @param h        Height of the pixel rectangle.
     * @param dArray   The input double pixel array.
     *
     * @throws NullPointerException if dArray is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates are not
     * in bounds, or if dArray is too small to hold the input.
     */
    public void setPixels(int x, int y, int w, int h, double dArray[]) {
        sampleModel.setPixels(x-sampleModelTranslateX,y-sampleModelTranslateY,
                              w,h,dArray,dataBuffer);
    }

    /**
     * Sets a sample in the specified band for the pixel located at (x,y)
     * in the DataBuffer using an int for input.
     * An ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * However, explicit bounds checking is not guaranteed.
     * <p>
     *  使用int作为输入,为DataBuffer中位于(x,y)的像素设置指定频带中的样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 但是,不能保证显式边界检查。
     * 
     * 
     * @param x        The X coordinate of the pixel location.
     * @param y        The Y coordinate of the pixel location.
     * @param b        The band to set.
     * @param s        The input sample.
     *
     * @throws ArrayIndexOutOfBoundsException if the coordinates or
     * the band index are not in bounds.
     */
    public void setSample(int x, int y, int b, int s) {
        sampleModel.setSample(x-sampleModelTranslateX,
                              y-sampleModelTranslateY, b, s,
                              dataBuffer);
    }

    /**
     * Sets a sample in the specified band for the pixel located at (x,y)
     * in the DataBuffer using a float for input.
     * An ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * However, explicit bounds checking is not guaranteed.
     * <p>
     *  使用float作为输入,为DataBuffer中位于(x,y)的像素设置指定频带中的样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 但是,不能保证显式边界检查。
     * 
     * 
     * @param x        The X coordinate of the pixel location.
     * @param y        The Y coordinate of the pixel location.
     * @param b        The band to set.
     * @param s        The input sample as a float.
     *
     * @throws ArrayIndexOutOfBoundsException if the coordinates or
     * the band index are not in bounds.
     */
    public void setSample(int x, int y, int b, float s){
        sampleModel.setSample(x-sampleModelTranslateX,y-sampleModelTranslateY,
                              b,s,dataBuffer);
    }

    /**
     * Sets a sample in the specified band for the pixel located at (x,y)
     * in the DataBuffer using a double for input.
     * An ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * However, explicit bounds checking is not guaranteed.
     * <p>
     *  在指定的频带中为位于(x,y)的像素在DataBuffer中使用双精度输入设置样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 但是,不能保证显式边界检查。
     * 
     * 
     * @param x        The X coordinate of the pixel location.
     * @param y        The Y coordinate of the pixel location.
     * @param b        The band to set.
     * @param s        The input sample as a double.
     *
     * @throws ArrayIndexOutOfBoundsException if the coordinates or
     * the band index are not in bounds.
     */
    public void setSample(int x, int y, int b, double s){
        sampleModel.setSample(x-sampleModelTranslateX,y-sampleModelTranslateY,
                                    b,s,dataBuffer);
    }

    /**
     * Sets the samples in the specified band for the specified rectangle
     * of pixels from an int array containing one sample per array element.
     * An ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * However, explicit bounds checking is not guaranteed.
     * <p>
     * 对于指定的像素矩形,从包含每个数组元素一个样本的int数组中设置指定带中的样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。但是,不能保证显式边界检查。
     * 
     * 
     * @param x        The X coordinate of the upper left pixel location.
     * @param y        The Y coordinate of the upper left pixel location.
     * @param w        Width of the pixel rectangle.
     * @param h        Height of the pixel rectangle.
     * @param b        The band to set.
     * @param iArray   The input int sample array.
     *
     * @throws NullPointerException if iArray is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates or
     * the band index are not in bounds, or if iArray is too small to
     * hold the input.
     */
    public void setSamples(int x, int y, int w, int h, int b,
                           int iArray[]) {
        sampleModel.setSamples(x-sampleModelTranslateX,y-sampleModelTranslateY,
                               w,h,b,iArray,dataBuffer);
    }

    /**
     * Sets the samples in the specified band for the specified rectangle
     * of pixels from a float array containing one sample per array element.
     * An ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * However, explicit bounds checking is not guaranteed.
     * <p>
     *  在包含每个数组元素一个样本的float数组中,为指定的像素矩形设置指定带中的样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 但是,不能保证显式边界检查。
     * 
     * 
     * @param x        The X coordinate of the upper left pixel location.
     * @param y        The Y coordinate of the upper left pixel location.
     * @param w        Width of the pixel rectangle.
     * @param h        Height of the pixel rectangle.
     * @param b        The band to set.
     * @param fArray   The input float sample array.
     *
     * @throws NullPointerException if fArray is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates or
     * the band index are not in bounds, or if fArray is too small to
     * hold the input.
     */
    public void setSamples(int x, int y, int w, int h, int b,
                           float fArray[]) {
        sampleModel.setSamples(x-sampleModelTranslateX,y-sampleModelTranslateY,
                               w,h,b,fArray,dataBuffer);
    }

    /**
     * Sets the samples in the specified band for the specified rectangle
     * of pixels from a double array containing one sample per array element.
     * An ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * However, explicit bounds checking is not guaranteed.
     * <p>
     * 
     * @param x        The X coordinate of the upper left pixel location.
     * @param y        The Y coordinate of the upper left pixel location.
     * @param w        Width of the pixel rectangle.
     * @param h        Height of the pixel rectangle.
     * @param b        The band to set.
     * @param dArray   The input double sample array.
     *
     * @throws NullPointerException if dArray is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates or
     * the band index are not in bounds, or if dArray is too small to
     * hold the input.
     */
    public void setSamples(int x, int y, int w, int h, int b,
                           double dArray[]) {
        sampleModel.setSamples(x-sampleModelTranslateX,y-sampleModelTranslateY,
                              w,h,b,dArray,dataBuffer);
    }

}
