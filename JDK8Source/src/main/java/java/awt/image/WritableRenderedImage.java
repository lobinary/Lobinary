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
import java.awt.Point;

/**
 * WriteableRenderedImage is a common interface for objects which
 * contain or can produce image data in the form of Rasters and
 * which can be modified and/or written over.  The image
 * data may be stored/produced as a single tile or a regular array
 * of tiles.
 * <p>
 * WritableRenderedImage provides notification to other interested
 * objects when a tile is checked out for writing (via the
 * getWritableTile method) and when the last writer of a particular
 * tile relinquishes its access (via a call to releaseWritableTile).
 * Additionally, it allows any caller to determine whether any tiles
 * are currently checked out (via hasTileWriters), and to obtain a
 * list of such tiles (via getWritableTileIndices, in the form of a Vector
 * of Point objects).
 * <p>
 * Objects wishing to be notified of changes in tile writability must
 * implement the TileObserver interface, and are added by a
 * call to addTileObserver.  Multiple calls to
 * addTileObserver for the same object will result in multiple
 * notifications.  An existing observer may reduce its notifications
 * by calling removeTileObserver; if the observer had no
 * notifications the operation is a no-op.
 * <p>
 * It is necessary for a WritableRenderedImage to ensure that
 * notifications occur only when the first writer acquires a tile and
 * the last writer releases it.
 *
 * <p>
 *  WriteableRenderedImage是一个用于包含或可以产生Raster形式的图像数据并且可以被修改和/或写入的对象的通用接口。图像数据可以被存储/产生为单个瓦片或者瓦片的规则阵列。
 * <p>
 *  WritableRenderedImage提供通知给其他感兴趣的对象,当一个瓦片签出写入(通过getWritableTile方法),当一个特定瓦片的最后一个作者放弃了它的访问(通过调用releaseW
 * ritableTile)。
 * 此外,它允许任何调用者确定是否任何瓦片当前被检出(通过hasTileWriters),并获得这样的瓦片的列表(通过getWritableTileIndices,以点对象的向量的形式)。
 * <p>
 * 希望被通知瓦片可写性变化的对象必须实现TileObserver接口,并且通过调用addTileObserver来添加。对同一对象的多次调用addTileObserver将导致多个通知。
 * 现有的观察者可以通过调用removeTileObserver来减少其通知;如果观察者没有通知,操作是无操作。
 * <p>
 *  WritableRenderedImage必须确保只有在第一个写入器获取了一个图块,并且最后一个写入器释放了它时才会发生通知。
 * 
 */

public interface WritableRenderedImage extends RenderedImage
{

  /**
   * Adds an observer.  If the observer is already present,
   * it will receive multiple notifications.
   * <p>
   *  添加一个观察者。如果观察者已经存在,它将接收多个通知。
   * 
   * 
   * @param to the specified <code>TileObserver</code>
   */
  public void addTileObserver(TileObserver to);

  /**
   * Removes an observer.  If the observer was not registered,
   * nothing happens.  If the observer was registered for multiple
   * notifications, it will now be registered for one fewer.
   * <p>
   *  删除观察器。如果观察者没有注册,什么也没有发生。如果观察者注册了多个通知,它现在将注册少于一个。
   * 
   * 
   * @param to the specified <code>TileObserver</code>
   */
  public void removeTileObserver(TileObserver to);

  /**
   * Checks out a tile for writing.
   *
   * The WritableRenderedImage is responsible for notifying all
   * of its TileObservers when a tile goes from having
   * no writers to having one writer.
   *
   * <p>
   *  检查一个瓷砖写。
   * 
   *  WritableRenderedImage负责在瓦片从没有写入者到有一个写入者时通知其所有TileObserver。
   * 
   * 
   * @param tileX the X index of the tile.
   * @param tileY the Y index of the tile.
   * @return a writable tile.
   */
  public WritableRaster getWritableTile(int tileX, int tileY);

  /**
   * Relinquishes the right to write to a tile.  If the caller
   * continues to write to the tile, the results are undefined.
   * Calls to this method should only appear in matching pairs
   * with calls to getWritableTile; any other use will lead
   * to undefined results.
   *
   * The WritableRenderedImage is responsible for notifying all of
   * its TileObservers when a tile goes from having one writer
   * to having no writers.
   *
   * <p>
   *  放弃写入图块的权限。如果调用程序继续写入磁贴,则结果未定义。对此方法的调用应该只出现在与getWritableTile的调用中的匹配对中;任何其他使用将导致未定义的结果。
   * 
   *  WritableRenderedImage负责在瓦片从一个写入器到没有写入器时通知其所有TileObserver。
   * 
   * 
   * @param tileX the X index of the tile.
   * @param tileY the Y index of the tile.
   */
  public void releaseWritableTile(int tileX, int tileY);

  /**
   * Returns whether a tile is currently checked out for writing.
   *
   * <p>
   *  返回图块当前是否已签出写入。
   * 
   * 
   * @param tileX the X index of the tile.
   * @param tileY the Y index of the tile.
   * @return <code>true</code> if specified tile is checked out
   *         for writing; <code>false</code> otherwise.
   */
  public boolean isTileWritable(int tileX, int tileY);

  /**
   * Returns an array of Point objects indicating which tiles
   * are checked out for writing.  Returns null if none are
   * checked out.
   * <p>
   * 返回一个Point对象数组,指示检出哪些图块用于写入。如果没有检出,则返回null。
   * 
   * 
   * @return an array containing the locations of tiles that are
   *         checked out for writing.
   */
  public Point[] getWritableTileIndices();

  /**
   * Returns whether any tile is checked out for writing.
   * Semantically equivalent to (getWritableTileIndices() != null).
   * <p>
   *  返回是否检出任何拼贴以进行书写。语义上等同于(getWritableTileIndices()！= null)。
   * 
   * 
   * @return <code>true</code> if any tiles are checked out for
   *         writing; <code>false</code> otherwise.
   */
  public boolean hasTileWriters();

  /**
   * Sets a rect of the image to the contents of the Raster r, which is
   * assumed to be in the same coordinate space as the WritableRenderedImage.
   * The operation is clipped to the bounds of the WritableRenderedImage.
   * <p>
   *  将图像的矩形设置为栅格r的内容,栅格r假定与WritableRenderedImage处于相同的坐标空间。该操作被裁剪到WritableRenderedImage的边界。
   * 
   * @param r the specified <code>Raster</code>
   */
  public void setData(Raster r);

}
