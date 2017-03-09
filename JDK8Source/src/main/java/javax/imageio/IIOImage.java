/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2004, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.util.List;
import javax.imageio.metadata.IIOMetadata;

/**
 * A simple container class to aggregate an image, a set of
 * thumbnail (preview) images, and an object representing metadata
 * associated with the image.
 *
 * <p> The image data may take the form of either a
 * <code>RenderedImage</code>, or a <code>Raster</code>.  Reader
 * methods that return an <code>IIOImage</code> will always return a
 * <code>BufferedImage</code> using the <code>RenderedImage</code>
 * reference.  Writer methods that accept an <code>IIOImage</code>
 * will always accept a <code>RenderedImage</code>, and may optionally
 * accept a <code>Raster</code>.
 *
 * <p> Exactly one of <code>getRenderedImage</code> and
 * <code>getRaster</code> will return a non-<code>null</code> value.
 * Subclasses are responsible for ensuring this behavior.
 *
 * <p>
 *  用于聚合图像的简单容器类,一组缩略图(预览)图像和表示与图像相关联的元数据的对象。
 * 
 *  <p>图像数据可以采用<code> RenderedImage </code>或<code> Raster </code>的形式。
 * 返回<code> IIOImage </code>的读取器方法将始终使用<code> RenderedImage </code>引用返回<code> BufferedImage </code>。
 * 接受<code> IIOImage </code>的写方法将始终接受<code> RenderedImage </code>,并且可以选择接受<code> Raster </code>。
 * 
 *  <p> <code> getRenderedImage </code>和<code> getRaster </code>中的一个将返回非<code> null </code>值。子类负责确保此行为。
 * 
 * 
 * @see ImageReader#readAll(int, ImageReadParam)
 * @see ImageReader#readAll(java.util.Iterator)
 * @see ImageWriter#write(javax.imageio.metadata.IIOMetadata,
 *                        IIOImage, ImageWriteParam)
 * @see ImageWriter#write(IIOImage)
 * @see ImageWriter#writeToSequence(IIOImage, ImageWriteParam)
 * @see ImageWriter#writeInsert(int, IIOImage, ImageWriteParam)
 *
 */
public class IIOImage {

    /**
     * The <code>RenderedImage</code> being referenced.
     * <p>
     *  被引用的<code> RenderedImage </code>。
     * 
     */
    protected RenderedImage image;

    /**
     * The <code>Raster</code> being referenced.
     * <p>
     *  被引用的<code> Raster </code>。
     * 
     */
    protected Raster raster;

    /**
     * A <code>List</code> of <code>BufferedImage</code> thumbnails,
     * or <code>null</code>.  Non-<code>BufferedImage</code> objects
     * must not be stored in this <code>List</code>.
     * <p>
     *  <code> BufferedImage </code>缩略图的<code>列表</code>或<code> null </code>。
     * 非<code> BufferedImage </code>对象不能存储在此<code> List </code>中。
     * 
     */
    protected List<? extends BufferedImage> thumbnails = null;

    /**
     * An <code>IIOMetadata</code> object containing metadata
     * associated with the image.
     * <p>
     *  包含与图像相关联的元数据的<code> IIOMetadata </code>对象。
     * 
     */
    protected IIOMetadata metadata;

    /**
     * Constructs an <code>IIOImage</code> containing a
     * <code>RenderedImage</code>, and thumbnails and metadata
     * associated with it.
     *
     * <p> All parameters are stored by reference.
     *
     * <p> The <code>thumbnails</code> argument must either be
     * <code>null</code> or contain only <code>BufferedImage</code>
     * objects.
     *
     * <p>
     *  构造包含<code> RenderedImage </code>的<code> IIOImage </code>,以及与其关联的缩略图和元数据。
     * 
     *  <p>所有参数都通过引用存储。
     * 
     * <p> <code>缩略图</code>参数必须是<code> null </code>或只包含<code> BufferedImage </code>对象。
     * 
     * 
     * @param image a <code>RenderedImage</code>.
     * @param thumbnails a <code>List</code> of <code>BufferedImage</code>s,
     * or <code>null</code>.
     * @param metadata an <code>IIOMetadata</code> object, or
     * <code>null</code>.
     *
     * @exception IllegalArgumentException if <code>image</code> is
     * <code>null</code>.
     */
    public IIOImage(RenderedImage image,
                    List<? extends BufferedImage> thumbnails,
                    IIOMetadata metadata) {
        if (image == null) {
            throw new IllegalArgumentException("image == null!");
        }
        this.image = image;
        this.raster = null;
        this.thumbnails = thumbnails;
        this.metadata = metadata;
    }

    /**
     * Constructs an <code>IIOImage</code> containing a
     * <code>Raster</code>, and thumbnails and metadata
     * associated with it.
     *
     * <p> All parameters are stored by reference.
     *
     * <p>
     *  构造包含<code> Raster </code>的<code> IIOImage </code>,以及与其关联的缩略图和元数据。
     * 
     *  <p>所有参数都通过引用存储。
     * 
     * 
     * @param raster a <code>Raster</code>.
     * @param thumbnails a <code>List</code> of <code>BufferedImage</code>s,
     * or <code>null</code>.
     * @param metadata an <code>IIOMetadata</code> object, or
     * <code>null</code>.
     *
     * @exception IllegalArgumentException if <code>raster</code> is
     * <code>null</code>.
     */
    public IIOImage(Raster raster,
                    List<? extends BufferedImage> thumbnails,
                    IIOMetadata metadata) {
        if (raster == null) {
            throw new IllegalArgumentException("raster == null!");
        }
        this.raster = raster;
        this.image = null;
        this.thumbnails = thumbnails;
        this.metadata = metadata;
    }

    /**
     * Returns the currently set <code>RenderedImage</code>, or
     * <code>null</code> if only a <code>Raster</code> is available.
     *
     * <p>
     *  如果只有<code> Raster </code>可用,则返回当前设置的<code> RenderedImage </code>或<code> null </code>。
     * 
     * 
     * @return a <code>RenderedImage</code>, or <code>null</code>.
     *
     * @see #setRenderedImage
     */
    public RenderedImage getRenderedImage() {
        synchronized(this) {
            return image;
        }
    }

    /**
     * Sets the current <code>RenderedImage</code>.  The value is
     * stored by reference.  Any existing <code>Raster</code> is
     * discarded.
     *
     * <p>
     *  设置当前<code> RenderedImage </code>。该值通过引用存储。任何现有的<code> Raster </code>被丢弃。
     * 
     * 
     * @param image a <code>RenderedImage</code>.
     *
     * @exception IllegalArgumentException if <code>image</code> is
     * <code>null</code>.
     *
     * @see #getRenderedImage
     */
    public void setRenderedImage(RenderedImage image) {
        synchronized(this) {
            if (image == null) {
                throw new IllegalArgumentException("image == null!");
            }
            this.image = image;
            this.raster = null;
        }
    }

    /**
     * Returns <code>true</code> if this <code>IIOImage</code> stores
     * a <code>Raster</code> rather than a <code>RenderedImage</code>.
     *
     * <p>
     *  如果此<code> IIOImage </code>存储<code> Raster </code>而不是<code> RenderedImage </code>,则返回<code> true </code>
     * 。
     * 
     * 
     * @return <code>true</code> if a <code>Raster</code> is
     * available.
     */
    public boolean hasRaster() {
        synchronized(this) {
            return (raster != null);
        }
    }

    /**
     * Returns the currently set <code>Raster</code>, or
     * <code>null</code> if only a <code>RenderedImage</code> is
     * available.
     *
     * <p>
     *  如果只有<code> RenderedImage </code>可用,则返回当前设置的<code> Raster </code>或<code> null </code>。
     * 
     * 
     * @return a <code>Raster</code>, or <code>null</code>.
     *
     * @see #setRaster
     */
    public Raster getRaster() {
        synchronized(this) {
            return raster;
        }
    }

    /**
     * Sets the current <code>Raster</code>.  The value is
     * stored by reference.  Any existing <code>RenderedImage</code> is
     * discarded.
     *
     * <p>
     *  设置当前<code> Raster </code>。该值通过引用存储。任何现有的<code> RenderedImage </code>被丢弃。
     * 
     * 
     * @param raster a <code>Raster</code>.
     *
     * @exception IllegalArgumentException if <code>raster</code> is
     * <code>null</code>.
     *
     * @see #getRaster
     */
    public void setRaster(Raster raster) {
        synchronized(this) {
            if (raster == null) {
                throw new IllegalArgumentException("raster == null!");
            }
            this.raster = raster;
            this.image = null;
        }
    }

    /**
     * Returns the number of thumbnails stored in this
     * <code>IIOImage</code>.
     *
     * <p>
     *  返回存储在此<code> IIOImage </code>中的缩略图的数量。
     * 
     * 
     * @return the number of thumbnails, as an <code>int</code>.
     */
    public int getNumThumbnails() {
        return thumbnails == null ? 0 : thumbnails.size();
    }

    /**
     * Returns a thumbnail associated with the main image.
     *
     * <p>
     *  返回与主图像关联的缩略图。
     * 
     * 
     * @param index the index of the desired thumbnail image.
     *
     * @return a thumbnail image, as a <code>BufferedImage</code>.
     *
     * @exception IndexOutOfBoundsException if the supplied index is
     * negative or larger than the largest valid index.
     * @exception ClassCastException if a
     * non-<code>BufferedImage</code> object is encountered in the
     * list of thumbnails at the given index.
     *
     * @see #getThumbnails
     * @see #setThumbnails
     */
    public BufferedImage getThumbnail(int index) {
        if (thumbnails == null) {
            throw new IndexOutOfBoundsException("No thumbnails available!");
        }
        return (BufferedImage)thumbnails.get(index);
    }

    /**
     * Returns the current <code>List</code> of thumbnail
     * <code>BufferedImage</code>s, or <code>null</code> if none is
     * set.  A live reference is returned.
     *
     * <p>
     *  返回缩略图<code> BufferedImage </code> s或<code> null </code>(如果未设置)的当前<code> List </code>返回实时引用。
     * 
     * 
     * @return the current <code>List</code> of
     * <code>BufferedImage</code> thumbnails, or <code>null</code>.
     *
     * @see #getThumbnail(int)
     * @see #setThumbnails
     */
    public List<? extends BufferedImage> getThumbnails() {
        return thumbnails;
    }

    /**
     * Sets the list of thumbnails to a new <code>List</code> of
     * <code>BufferedImage</code>s, or to <code>null</code>.  The
     * reference to the previous <code>List</code> is discarded.
     *
     * <p> The <code>thumbnails</code> argument must either be
     * <code>null</code> or contain only <code>BufferedImage</code>
     * objects.
     *
     * <p>
     *  将缩略图列表设置为<code> BufferedImage </code>的新<code> List </code>或<code> null </code>。
     * 将删除对上一个<code> List </code>的引用。
     * 
     *  <p> <code>缩略图</code>参数必须是<code> null </code>或只包含<code> BufferedImage </code>对象。
     * 
     * 
     * @param thumbnails a <code>List</code> of
     * <code>BufferedImage</code> thumbnails, or <code>null</code>.
     *
     * @see #getThumbnail(int)
     * @see #getThumbnails
     */
    public void setThumbnails(List<? extends BufferedImage> thumbnails) {
        this.thumbnails = thumbnails;
    }

    /**
     * Returns a reference to the current <code>IIOMetadata</code>
     * object, or <code>null</code> is none is set.
     *
     * <p>
     * 返回对当前<code> IIOMetadata </code>对象的引用,或<code> null </code>为none。
     * 
     * 
     * @return an <code>IIOMetadata</code> object, or <code>null</code>.
     *
     * @see #setMetadata
     */
    public IIOMetadata getMetadata() {
        return metadata;
    }

    /**
     * Sets the <code>IIOMetadata</code> to a new object, or
     * <code>null</code>.
     *
     * <p>
     *  将<code> IIOMetadata </code>设置为新对象,或<code> null </code>。
     * 
     * @param metadata an <code>IIOMetadata</code> object, or
     * <code>null</code>.
     *
     * @see #getMetadata
     */
    public void setMetadata(IIOMetadata metadata) {
        this.metadata = metadata;
    }
}
