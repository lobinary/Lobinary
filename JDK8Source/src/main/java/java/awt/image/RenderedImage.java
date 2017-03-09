/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2008, Oracle and/or its affiliates. All rights reserved.
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
import java.util.Dictionary;
import java.util.Vector;

/**
 * RenderedImage is a common interface for objects which contain
 * or can produce image data in the form of Rasters.  The image
 * data may be stored/produced as a single tile or a regular array
 * of tiles.
 * <p>
 *  RenderedImage是包含或可以以Raster形式生成图像数据的对象的通用接口。图像数据可以被存储/产生为单个瓦片或者瓦片的规则阵列。
 * 
 */

public interface RenderedImage {

    /**
     * Returns a vector of RenderedImages that are the immediate sources of
     * image data for this RenderedImage.  This method returns null if
     * the RenderedImage object has no information about its immediate
     * sources.  It returns an empty Vector if the RenderedImage object has
     * no immediate sources.
     * <p>
     *  返回一个RenderedImages的向量,它是这个RenderedImage的图像数据的直接来源。如果RenderedImage对象没有有关其直接源的信息,此方法返回null。
     * 如果RenderedImage对象没有立即源,它返回一个空向量。
     * 
     * 
     * @return a Vector of <code>RenderedImage</code> objects.
     */
    Vector<RenderedImage> getSources();

    /**
     * Gets a property from the property set of this image.  The set of
     * properties and whether it is immutable is determined by the
     * implementing class.  This method returns
     * java.awt.Image.UndefinedProperty if the specified property is
     * not defined for this RenderedImage.
     * <p>
     *  从此图像的属性集获取属性。属性集和它是否不可变由实现类确定。如果未为此RenderedImage定义指定的属性,此方法将返回java.awt.Image.UndefinedProperty。
     * 
     * 
     * @param name the name of the property
     * @return the property indicated by the specified name.
     * @see java.awt.Image#UndefinedProperty
     */
    Object getProperty(String name);

    /**
      * Returns an array of names recognized by
      * {@link #getProperty(String) getProperty(String)}
      * or <code>null</code>, if no property names are recognized.
      * <p>
      *  如果未识别属性名称,则返回由{@link #getProperty(String)getProperty(String)}或<code> null </code>识别的名称数组。
      * 
      * 
      * @return a <code>String</code> array containing all of the
      * property names that <code>getProperty(String)</code> recognizes;
      * or <code>null</code> if no property names are recognized.
      */
    String[] getPropertyNames();

    /**
     * Returns the ColorModel associated with this image.  All Rasters
     * returned from this image will have this as their ColorModel.  This
     * can return null.
     * <p>
     * 返回与此图像关联的ColorModel。从此图像返回的所有栅格都将具有此ColorModel。这可以返回null。
     * 
     * 
     * @return the <code>ColorModel</code> of this image.
     */
    ColorModel getColorModel();

    /**
     * Returns the SampleModel associated with this image.  All Rasters
     * returned from this image will have this as their SampleModel.
     * <p>
     *  返回与此图像相关联的SampleModel。从此图片返回的所有栅格都将具有此示例模型。
     * 
     * 
     * @return the <code>SampleModel</code> of this image.
     */
    SampleModel getSampleModel();

    /**
     * Returns the width of the RenderedImage.
     * <p>
     *  返回RenderedImage的宽度。
     * 
     * 
     * @return the width of this <code>RenderedImage</code>.
     */
    int getWidth();

    /**
     * Returns the height of the RenderedImage.
     * <p>
     *  返回RenderedImage的高度。
     * 
     * 
     * @return the height of this <code>RenderedImage</code>.
     */
    int getHeight();

    /**
     * Returns the minimum X coordinate (inclusive) of the RenderedImage.
     * <p>
     *  返回RenderedImage的最小X坐标(包括)。
     * 
     * 
     * @return the X coordinate of this <code>RenderedImage</code>.
     */
    int getMinX();

    /**
     * Returns the minimum Y coordinate (inclusive) of the RenderedImage.
     * <p>
     *  返回RenderedImage的最小Y坐标(包括)。
     * 
     * 
     * @return the Y coordinate of this <code>RenderedImage</code>.
     */
    int getMinY();

    /**
     * Returns the number of tiles in the X direction.
     * <p>
     *  返回X方向上的图块数。
     * 
     * 
     * @return the number of tiles in the X direction.
     */
    int getNumXTiles();

    /**
     * Returns the number of tiles in the Y direction.
     * <p>
     *  返回Y方向上的图块数。
     * 
     * 
     * @return the number of tiles in the Y direction.
     */
    int getNumYTiles();

    /**
     *  Returns the minimum tile index in the X direction.
     * <p>
     *  返回X方向上的最小图块索引。
     * 
     * 
     *  @return the minimum tile index in the X direction.
     */
    int getMinTileX();

    /**
     *  Returns the minimum tile index in the Y direction.
     * <p>
     *  返回Y方向上的最小图块索引。
     * 
     * 
     *  @return the minimum tile index in the X direction.
     */
    int getMinTileY();

    /**
     *  Returns the tile width in pixels.  All tiles must have the same
     *  width.
     * <p>
     *  返回图块宽度(以像素为单位)。所有图块都必须具有相同的宽度。
     * 
     * 
     *  @return the tile width in pixels.
     */
    int getTileWidth();

    /**
     *  Returns the tile height in pixels.  All tiles must have the same
     *  height.
     * <p>
     *  返回图块高度(以像素为单位)。所有图块必须具有相同的高度。
     * 
     * 
     *  @return the tile height in pixels.
     */
    int getTileHeight();

    /**
     * Returns the X offset of the tile grid relative to the origin,
     * i.e., the X coordinate of the upper-left pixel of tile (0, 0).
     * (Note that tile (0, 0) may not actually exist.)
     * <p>
     *  返回瓦片网格相对于原点的X偏移量,即瓦片(0,0)的左上像素的X坐标。 (请注意,tile(0,0)可能不实际存在)。
     * 
     * 
     * @return the X offset of the tile grid relative to the origin.
     */
    int getTileGridXOffset();

    /**
     * Returns the Y offset of the tile grid relative to the origin,
     * i.e., the Y coordinate of the upper-left pixel of tile (0, 0).
     * (Note that tile (0, 0) may not actually exist.)
     * <p>
     *  返回瓦片网格相对于原点的Y偏移量,即瓦片(0,0)的左上像素的Y坐标。 (请注意,tile(0,0)可能不实际存在)。
     * 
     * 
     * @return the Y offset of the tile grid relative to the origin.
     */
    int getTileGridYOffset();

    /**
     * Returns tile (tileX, tileY).  Note that tileX and tileY are indices
     * into the tile array, not pixel locations.  The Raster that is returned
     * is live and will be updated if the image is changed.
     * <p>
     *  返回tile(tileX,tileY)。请注意,tileX和tileY是tile数组中的索引,而不是像素位置。返回的栅格是实时的,如果图像更改,则会更新。
     * 
     * 
     * @param tileX the X index of the requested tile in the tile array
     * @param tileY the Y index of the requested tile in the tile array
     * @return the tile given the specified indices.
     */
   Raster getTile(int tileX, int tileY);

    /**
     * Returns the image as one large tile (for tile based
     * images this will require fetching the whole image
     * and copying the image data over).  The Raster returned is
     * a copy of the image data and will not be updated if the image
     * is changed.
     * <p>
     * 将图像返回为一个大图块(对于基于图块的图像,这将需要获取整个图像并复制图像数据)。返回的栅格是图像数据的副本,如果图像更改,则不会更新。
     * 
     * 
     * @return the image as one large tile.
     */
    Raster getData();

    /**
     * Computes and returns an arbitrary region of the RenderedImage.
     * The Raster returned is a copy of the image data and will not
     * be updated if the image is changed.
     * <p>
     *  计算并返回RenderedImage的任意区域。返回的栅格是图像数据的副本,如果图像更改,则不会更新。
     * 
     * 
     * @param rect the region of the RenderedImage to be returned.
     * @return the region of the <code>RenderedImage</code>
     * indicated by the specified <code>Rectangle</code>.
     */
    Raster getData(Rectangle rect);

    /**
     * Computes an arbitrary rectangular region of the RenderedImage
     * and copies it into a caller-supplied WritableRaster.  The region
     * to be computed is determined from the bounds of the supplied
     * WritableRaster.  The supplied WritableRaster must have a
     * SampleModel that is compatible with this image.  If raster is null,
     * an appropriate WritableRaster is created.
     * <p>
     *  计算RenderedImage的任意矩形区域,并将其复制到调用者提供的WritableRaster中。要计算的区域从提供的WritableRaster的边界确定。
     * 提供的WritableRaster必须具有与此映像兼容的SampleModel。如果raster为null,则会创建一个合适的WritableRaster。
     * 
     * @param raster a WritableRaster to hold the returned portion of the
     *               image, or null.
     * @return a reference to the supplied or created WritableRaster.
     */
    WritableRaster copyData(WritableRaster raster);
}
