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
package javax.print.attribute.standard;

import java.util.HashMap;
import java.util.Vector;

import javax.print.attribute.Size2DSyntax;
import javax.print.attribute.Attribute;

/**
 * Class MediaSize is a two-dimensional size valued printing attribute class
 * that indicates the dimensions of the medium in a portrait orientation, with
 * the X dimension running along the bottom edge and the Y dimension running
 * along the left edge. Thus, the Y dimension must be greater than or equal to
 * the X dimension. Class MediaSize declares many standard media size
 * values, organized into nested classes for ISO, JIS, North American,
 * engineering, and other media.
 * <P>
 * MediaSize is not yet used to specify media. Its current role is
 * as a mapping for named media (see {@link MediaSizeName MediaSizeName}).
 * Clients can use the mapping method
 * <code>MediaSize.getMediaSizeForName(MediaSizeName)</code>
 * to find the physical dimensions of the MediaSizeName instances
 * enumerated in this API. This is useful for clients which need this
 * information to format {@literal &} paginate printing.
 * <P>
 *
 * <p>
 *  类MediaSize是二维尺寸值打印属性类,其以纵向方向指示介质的尺寸,其中X尺寸沿着底部边缘延伸,Y尺寸沿着左边缘延伸。因此,Y维度必须大于或等于X维度。
 *  MediaSize类声明了许多标准介质大小值,组织为ISO,JIS,北美,工程和其他介质的嵌套类。
 * <P>
 *  MediaSize尚未用于指定介质。它当前的作用是作为命名媒体的映射(参见{@link MediaSizeName MediaSizeName})。
 * 客户端可以使用映射方法<code> MediaSize.getMediaSizeForName(MediaSizeName)</code>来查找此API中枚举的MediaSizeName实例的物理尺寸。
 *  MediaSize尚未用于指定介质。它当前的作用是作为命名媒体的映射(参见{@link MediaSizeName MediaSizeName})。
 * 这对于需要此信息来格式化{@literal&}分页打印的客户端很有用。
 * <P>
 * 
 * 
 * @author  Phil Race, Alan Kaminsky
 */
public class MediaSize extends Size2DSyntax implements Attribute {

    private static final long serialVersionUID = -1967958664615414771L;

    private MediaSizeName mediaName;

    private static HashMap mediaMap = new HashMap(100, 10);

    private static Vector sizeVector = new Vector(100, 10);

    /**
     * Construct a new media size attribute from the given floating-point
     * values.
     *
     * <p>
     *  从给定的浮点值构造新的介质尺寸属性。
     * 
     * 
     * @param  x  X dimension.
     * @param  y  Y dimension.
     * @param  units
     *     Unit conversion factor, e.g. <CODE>Size2DSyntax.INCH</CODE> or
     *     <CODE>Size2DSyntax.MM</CODE>.
     *
     * @exception  IllegalArgumentException
     *   (Unchecked exception) Thrown if {@code x < 0} or {@code y < 0} or
     *   {@code units < 1} or {@code x > y}.
     */
    public MediaSize(float x, float y,int units) {
        super (x, y, units);
        if (x > y) {
            throw new IllegalArgumentException("X dimension > Y dimension");
        }
        sizeVector.add(this);
    }

    /**
     * Construct a new media size attribute from the given integer values.
     *
     * <p>
     *  从给定的整数值构造新的介质大小属性。
     * 
     * 
     * @param  x  X dimension.
     * @param  y  Y dimension.
     * @param  units
     *     Unit conversion factor, e.g. <CODE>Size2DSyntax.INCH</CODE> or
     *     <CODE>Size2DSyntax.MM</CODE>.
     *
     * @exception  IllegalArgumentException
     *   (Unchecked exception) Thrown if {@code x < 0} or {@code y < 0} or
     *   {@code units < 1} or {@code x > y}.
     */
    public MediaSize(int x, int y,int units) {
        super (x, y, units);
        if (x > y) {
            throw new IllegalArgumentException("X dimension > Y dimension");
        }
        sizeVector.add(this);
    }

   /**
     * Construct a new media size attribute from the given floating-point
     * values.
     *
     * <p>
     *  从给定的浮点值构造新的介质尺寸属性。
     * 
     * 
     * @param  x  X dimension.
     * @param  y  Y dimension.
     * @param  units
     *     Unit conversion factor, e.g. <CODE>Size2DSyntax.INCH</CODE> or
     *     <CODE>Size2DSyntax.MM</CODE>.
     * @param media a media name to associate with this MediaSize
     *
     * @exception  IllegalArgumentException
     *   (Unchecked exception) Thrown if {@code x < 0} or {@code y < 0} or
     *   {@code units < 1} or {@code x > y}.
     */
    public MediaSize(float x, float y,int units, MediaSizeName media) {
        super (x, y, units);
        if (x > y) {
            throw new IllegalArgumentException("X dimension > Y dimension");
        }
        if (media != null && mediaMap.get(media) == null) {
            mediaName = media;
            mediaMap.put(mediaName, this);
        }
        sizeVector.add(this);
    }

    /**
     * Construct a new media size attribute from the given integer values.
     *
     * <p>
     *  从给定的整数值构造新的介质大小属性。
     * 
     * 
     * @param  x  X dimension.
     * @param  y  Y dimension.
     * @param  units
     *     Unit conversion factor, e.g. <CODE>Size2DSyntax.INCH</CODE> or
     *     <CODE>Size2DSyntax.MM</CODE>.
     * @param media a media name to associate with this MediaSize
     *
     * @exception  IllegalArgumentException
     *   (Unchecked exception) Thrown if {@code x < 0} or {@code y < 0} or
     *   {@code units < 1} or {@code x > y}.
     */
    public MediaSize(int x, int y,int units, MediaSizeName media) {
        super (x, y, units);
        if (x > y) {
            throw new IllegalArgumentException("X dimension > Y dimension");
        }
        if (media != null && mediaMap.get(media) == null) {
            mediaName = media;
            mediaMap.put(mediaName, this);
        }
        sizeVector.add(this);
    }

    /**
     * Get the media name, if any, for this size.
     *
     * <p>
     *  获取此大小的媒体名称(如果有)。
     * 
     * 
     * @return the name for this media size, or null if no name was
     * associated with this size (an anonymous size).
     */
    public MediaSizeName getMediaSizeName() {
        return mediaName;
    }

    /**
     * Get the MediaSize for the specified named media.
     *
     * <p>
     *  获取指定命名媒体的MediaSize。
     * 
     * 
     * @param media - the name of the media for which the size is sought
     * @return size of the media, or null if this media is not associated
     * with any size.
     */
    public static MediaSize getMediaSizeForName(MediaSizeName media) {
        return (MediaSize)mediaMap.get(media);
    }

    /**
     * The specified dimensions are used to locate a matching MediaSize
     * instance from amongst all the standard MediaSize instances.
     * If there is no exact match, the closest match is used.
     * <p>
     * The MediaSize is in turn used to locate the MediaSizeName object.
     * This method may return null if the closest matching MediaSize
     * has no corresponding Media instance.
     * <p>
     * This method is useful for clients which have only dimensions and
     * want to find a Media which corresponds to the dimensions.
     * <p>
     * 指定的维度用于从所有标准MediaSize实例中定位匹配的MediaSize实例。如果没有完全匹配,则使用最接近的匹配。
     * <p>
     *  MediaSize又用于定位MediaSizeName对象。如果最接近的匹配MediaSize没有对应的媒体实例,则此方法可以返回null。
     * <p>
     *  此方法对于仅具有维度并且希望找到对应于维度的媒体的客户端是有用的。
     * 
     * 
     * @param x - X dimension
     * @param y - Y dimension.
     * @param  units
     *     Unit conversion factor, e.g. <CODE>Size2DSyntax.INCH</CODE> or
     *     <CODE>Size2DSyntax.MM</CODE>
     * @return MediaSizeName matching these dimensions, or null.
     * @exception IllegalArgumentException if {@code x <= 0},
     *      {@code y <= 0}, or {@code units < 1}.
     *
     */
    public static MediaSizeName findMedia(float x, float y, int units) {

        MediaSize match = MediaSize.ISO.A4;

        if (x <= 0.0f || y <= 0.0f || units < 1) {
            throw new IllegalArgumentException("args must be +ve values");
        }

        double ls = x * x + y * y;
        double tmp_ls;
        float []dim;
        float diffx = x;
        float diffy = y;

        for (int i=0; i < sizeVector.size() ; i++) {
            MediaSize mediaSize = (MediaSize)sizeVector.elementAt(i);
            dim = mediaSize.getSize(units);
            if (x == dim[0] && y == dim[1]) {
                match = mediaSize;
                break;
            } else {
                diffx = x - dim[0];
                diffy = y - dim[1];
                tmp_ls = diffx * diffx + diffy * diffy;
                if (tmp_ls < ls) {
                    ls = tmp_ls;
                    match = mediaSize;
                }
            }
        }

        return match.getMediaSizeName();
    }

    /**
     * Returns whether this media size attribute is equivalent to the passed
     * in object.
     * To be equivalent, all of the following conditions must be true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class MediaSize.
     * <LI>
     * This media size attribute's X dimension is equal to
     * <CODE>object</CODE>'s X dimension.
     * <LI>
     * This media size attribute's Y dimension is equal to
     * <CODE>object</CODE>'s Y dimension.
     * </OL>
     *
     * <p>
     *  返回此媒体大小属性是否等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE>对象</CODE>是类MediaSize的一个实例。
     * <LI>
     *  此媒体大小属性的X维度等于<CODE>对象</CODE>的X维度。
     * <LI>
     *  此媒体大小属性的Y维度等于<CODE>对象</CODE>的Y维度。
     * </OL>
     * 
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this media size
     *          attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return (super.equals(object) && object instanceof MediaSize);
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class MediaSize and any vendor-defined subclasses, the category is
     * class MediaSize itself.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于MediaSize类和任何供应商定义的子类,类别是MediaSize类本身。
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return MediaSize.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class MediaSize and any vendor-defined subclasses, the category
     * name is <CODE>"media-size"</CODE>.
     *
     * <p>
     *  获取此属性值为实例的类别的名称。
     * <P>
     *  对于MediaSize类和任何供应商定义的子类,类别名称为<CODE>"media-size"</CODE>。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "media-size";
    }

    /**
     * Class MediaSize.ISO includes {@link MediaSize MediaSize} values for ISO
     * media.
     * <P>
     * <p>
     *  MediaSize.ISO类包括ISO媒体的{@link MediaSize MediaSize}值。
     * <P>
     */
    public final static class ISO {
        /**
         * Specifies the ISO A0 size, 841 mm by 1189 mm.
         * <p>
         *  指定ISO A0大小,841 mm x 1189 mm。
         * 
         */
        public static final MediaSize
            A0 = new MediaSize(841, 1189, Size2DSyntax.MM, MediaSizeName.ISO_A0);
        /**
         * Specifies the ISO A1 size, 594 mm by 841 mm.
         * <p>
         *  指定ISO A1尺寸,594 mm x 841 mm。
         * 
         */
        public static final MediaSize
            A1 = new MediaSize(594, 841, Size2DSyntax.MM, MediaSizeName.ISO_A1);
        /**
         * Specifies the ISO A2 size, 420 mm by 594 mm.
         * <p>
         *  指定ISO A2尺寸,420 mm x 594 mm。
         * 
         */
        public static final MediaSize
            A2 = new MediaSize(420, 594, Size2DSyntax.MM, MediaSizeName.ISO_A2);
        /**
         * Specifies the ISO A3 size, 297 mm by 420 mm.
         * <p>
         * 指定ISO A3尺寸,297 mm x 420 mm。
         * 
         */
        public static final MediaSize
            A3 = new MediaSize(297, 420, Size2DSyntax.MM, MediaSizeName.ISO_A3);
        /**
         * Specifies the ISO A4 size, 210 mm by 297 mm.
         * <p>
         *  指定ISO A4尺寸,210 mm x 297 mm。
         * 
         */
        public static final MediaSize
            A4 = new MediaSize(210, 297, Size2DSyntax.MM, MediaSizeName.ISO_A4);
        /**
         * Specifies the ISO A5 size, 148 mm by 210 mm.
         * <p>
         *  指定ISO A5尺寸,148 mm x 210 mm。
         * 
         */
        public static final MediaSize
            A5 = new MediaSize(148, 210, Size2DSyntax.MM, MediaSizeName.ISO_A5);
        /**
         * Specifies the ISO A6 size, 105 mm by 148 mm.
         * <p>
         *  指定ISO A6尺寸,105 mm x 148 mm。
         * 
         */
        public static final MediaSize
            A6 = new MediaSize(105, 148, Size2DSyntax.MM, MediaSizeName.ISO_A6);
        /**
         * Specifies the ISO A7 size, 74 mm by 105 mm.
         * <p>
         *  指定ISO A7尺寸,74 mm x 105 mm。
         * 
         */
        public static final MediaSize
            A7 = new MediaSize(74, 105, Size2DSyntax.MM, MediaSizeName.ISO_A7);
        /**
         * Specifies the ISO A8 size, 52 mm by 74 mm.
         * <p>
         *  指定ISO A8尺寸,52 mm x 74 mm。
         * 
         */
        public static final MediaSize
            A8 = new MediaSize(52, 74, Size2DSyntax.MM, MediaSizeName.ISO_A8);
        /**
         * Specifies the ISO A9 size, 37 mm by 52 mm.
         * <p>
         *  指定ISO A9尺寸,37 mm x 52 mm。
         * 
         */
        public static final MediaSize
            A9 = new MediaSize(37, 52, Size2DSyntax.MM, MediaSizeName.ISO_A9);
        /**
         * Specifies the ISO A10 size, 26 mm by 37 mm.
         * <p>
         *  指定ISO A10尺寸,26 mm×37 mm。
         * 
         */
        public static final MediaSize
            A10 = new MediaSize(26, 37, Size2DSyntax.MM, MediaSizeName.ISO_A10);
        /**
         * Specifies the ISO B0 size, 1000 mm by 1414 mm.
         * <p>
         *  指定ISO B0大小,1000 mm x 1414 mm。
         * 
         */
        public static final MediaSize
            B0 = new MediaSize(1000, 1414, Size2DSyntax.MM, MediaSizeName.ISO_B0);
        /**
         * Specifies the ISO B1 size, 707 mm by 1000 mm.
         * <p>
         *  指定ISO B1尺寸,707 mm x 1000 mm。
         * 
         */
        public static final MediaSize
            B1 = new MediaSize(707, 1000, Size2DSyntax.MM, MediaSizeName.ISO_B1);
        /**
         * Specifies the ISO B2 size, 500 mm by 707 mm.
         * <p>
         *  指定ISO B2尺寸,500 mm x 707 mm。
         * 
         */
        public static final MediaSize
            B2 = new MediaSize(500, 707, Size2DSyntax.MM, MediaSizeName.ISO_B2);
        /**
         * Specifies the ISO B3 size, 353 mm by 500 mm.
         * <p>
         *  指定ISO B3尺寸,353 mm x 500 mm。
         * 
         */
        public static final MediaSize
            B3 = new MediaSize(353, 500, Size2DSyntax.MM, MediaSizeName.ISO_B3);
        /**
         * Specifies the ISO B4 size, 250 mm by 353 mm.
         * <p>
         *  指定ISO B4尺寸,250 mm x 353 mm。
         * 
         */
        public static final MediaSize
            B4 = new MediaSize(250, 353, Size2DSyntax.MM, MediaSizeName.ISO_B4);
        /**
         * Specifies the ISO B5 size, 176 mm by 250 mm.
         * <p>
         *  指定ISO B5大小,176 mm x 250 mm。
         * 
         */
        public static final MediaSize
            B5 = new MediaSize(176, 250, Size2DSyntax.MM, MediaSizeName.ISO_B5);
        /**
         * Specifies the ISO B6 size, 125 mm by 176 mm.
         * <p>
         *  指定ISO B6尺寸,125 mm x 176 mm。
         * 
         */
        public static final MediaSize
            B6 = new MediaSize(125, 176, Size2DSyntax.MM, MediaSizeName.ISO_B6);
        /**
         * Specifies the ISO B7 size, 88 mm by 125 mm.
         * <p>
         *  指定ISO B7大小,88 mm x 125 mm。
         * 
         */
        public static final MediaSize
            B7 = new MediaSize(88, 125, Size2DSyntax.MM, MediaSizeName.ISO_B7);
        /**
         * Specifies the ISO B8 size, 62 mm by 88 mm.
         * <p>
         *  指定ISO B8大小,62 mm x 88 mm。
         * 
         */
        public static final MediaSize
            B8 = new MediaSize(62, 88, Size2DSyntax.MM, MediaSizeName.ISO_B8);
        /**
         * Specifies the ISO B9 size, 44 mm by 62 mm.
         * <p>
         *  指定ISO B9尺寸,44 mm x 62 mm。
         * 
         */
        public static final MediaSize
            B9 = new MediaSize(44, 62, Size2DSyntax.MM, MediaSizeName.ISO_B9);
        /**
         * Specifies the ISO B10 size, 31 mm by 44 mm.
         * <p>
         *  指定ISO B10尺寸,31 mm x 44 mm。
         * 
         */
        public static final MediaSize
            B10 = new MediaSize(31, 44, Size2DSyntax.MM, MediaSizeName.ISO_B10);
        /**
         * Specifies the ISO C3 size, 324 mm by 458 mm.
         * <p>
         *  指定ISO C3尺寸,324 mm x 458 mm。
         * 
         */
        public static final MediaSize
            C3 = new MediaSize(324, 458, Size2DSyntax.MM, MediaSizeName.ISO_C3);
        /**
         * Specifies the ISO C4 size, 229 mm by 324 mm.
         * <p>
         *  指定ISO C4尺寸,229 mm x 324 mm。
         * 
         */
        public static final MediaSize
            C4 = new MediaSize(229, 324, Size2DSyntax.MM, MediaSizeName.ISO_C4);
        /**
         * Specifies the ISO C5 size, 162 mm by 229 mm.
         * <p>
         *  指定ISO C5尺寸,162 mm x 229 mm。
         * 
         */
        public static final MediaSize
            C5 = new MediaSize(162, 229, Size2DSyntax.MM, MediaSizeName.ISO_C5);
        /**
         * Specifies the ISO C6 size, 114 mm by 162 mm.
         * <p>
         *  指定ISO C6尺寸,114 mm x 162 mm。
         * 
         */
        public static final MediaSize
            C6 = new MediaSize(114, 162, Size2DSyntax.MM, MediaSizeName.ISO_C6);
        /**
         * Specifies the ISO Designated Long size, 110 mm by 220 mm.
         * <p>
         *  指定ISO指定长尺寸,110 mm x 220 mm。
         * 
         */
        public static final MediaSize
            DESIGNATED_LONG = new MediaSize(110, 220, Size2DSyntax.MM,
                                            MediaSizeName.ISO_DESIGNATED_LONG);

        /**
         * Hide all constructors.
         * <p>
         *  隐藏所有构造函数。
         * 
         */
        private ISO() {
        }
    }

    /**
     * Class MediaSize.JIS includes {@link MediaSize MediaSize} values for JIS
     * (Japanese) media.      *
     * <p>
     *  MediaSize.JIS类包括JIS(日语)媒体的{@link MediaSize MediaSize}值。 *：
     * 
     */
    public final static class JIS {

        /**
         * Specifies the JIS B0 size, 1030 mm by 1456 mm.
         * <p>
         *  指定JIS B0尺寸,1030 mm x 1456 mm。
         * 
         */
        public static final MediaSize
            B0 = new MediaSize(1030, 1456, Size2DSyntax.MM, MediaSizeName.JIS_B0);
        /**
         * Specifies the JIS B1 size, 728 mm by 1030 mm.
         * <p>
         *  指定JIS B1尺寸,728 mm x 1030 mm。
         * 
         */
        public static final MediaSize
            B1 = new MediaSize(728, 1030, Size2DSyntax.MM, MediaSizeName.JIS_B1);
        /**
         * Specifies the JIS B2 size, 515 mm by 728 mm.
         * <p>
         *  指定JIS B2尺寸,515 mm×728 mm。
         * 
         */
        public static final MediaSize
            B2 = new MediaSize(515, 728, Size2DSyntax.MM, MediaSizeName.JIS_B2);
        /**
         * Specifies the JIS B3 size, 364 mm by 515 mm.
         * <p>
         *  指定JIS B3尺寸,364 mm×515 mm。
         * 
         */
        public static final MediaSize
            B3 = new MediaSize(364, 515, Size2DSyntax.MM, MediaSizeName.JIS_B3);
        /**
         * Specifies the JIS B4 size, 257 mm by 364 mm.
         * <p>
         *  指定JIS B4尺寸,257 mm x 364 mm。
         * 
         */
        public static final MediaSize
            B4 = new MediaSize(257, 364, Size2DSyntax.MM, MediaSizeName.JIS_B4);
        /**
         * Specifies the JIS B5 size, 182 mm by 257 mm.
         * <p>
         * 指定JIS B5尺寸,182 mm x 257 mm。
         * 
         */
        public static final MediaSize
            B5 = new MediaSize(182, 257, Size2DSyntax.MM, MediaSizeName.JIS_B5);
        /**
         * Specifies the JIS B6 size, 128 mm by 182 mm.
         * <p>
         *  指定JIS B6尺寸,128 mm×182 mm。
         * 
         */
        public static final MediaSize
            B6 = new MediaSize(128, 182, Size2DSyntax.MM, MediaSizeName.JIS_B6);
        /**
         * Specifies the JIS B7 size, 91 mm by 128 mm.
         * <p>
         *  指定JIS B7尺寸,91 mm x 128 mm。
         * 
         */
        public static final MediaSize
            B7 = new MediaSize(91, 128, Size2DSyntax.MM, MediaSizeName.JIS_B7);
        /**
         * Specifies the JIS B8 size, 64 mm by 91 mm.
         * <p>
         *  指定JIS B8尺寸,64 mm x 91 mm。
         * 
         */
        public static final MediaSize
            B8 = new MediaSize(64, 91, Size2DSyntax.MM, MediaSizeName.JIS_B8);
        /**
         * Specifies the JIS B9 size, 45 mm by 64 mm.
         * <p>
         *  指定JIS B9尺寸,45 mm x 64 mm。
         * 
         */
        public static final MediaSize
            B9 = new MediaSize(45, 64, Size2DSyntax.MM, MediaSizeName.JIS_B9);
        /**
         * Specifies the JIS B10 size, 32 mm by 45 mm.
         * <p>
         *  指定JIS B10尺寸,32 mm x 45 mm。
         * 
         */
        public static final MediaSize
            B10 = new MediaSize(32, 45, Size2DSyntax.MM, MediaSizeName.JIS_B10);
        /**
         * Specifies the JIS Chou ("long") #1 envelope size, 142 mm by 332 mm.
         * <p>
         *  指定JIS Chou("长")#1信封大小,142 mm x 332 mm。
         * 
         */
        public static final MediaSize CHOU_1 = new MediaSize(142, 332, Size2DSyntax.MM);
        /**
         * Specifies the JIS Chou ("long") #2 envelope size, 119 mm by 277 mm.
         * <p>
         *  指定JIS Chou("long")#2信封尺寸,119 mm x 277 mm。
         * 
         */
        public static final MediaSize CHOU_2 = new MediaSize(119, 277, Size2DSyntax.MM);
        /**
         * Specifies the JIS Chou ("long") #3 envelope size, 120 mm by 235 mm.
         * <p>
         *  指定JIS Chou("长")#3信封大小,120 mm x 235 mm。
         * 
         */
        public static final MediaSize CHOU_3 = new MediaSize(120, 235, Size2DSyntax.MM);
        /**
         * Specifies the JIS Chou ("long") #4 envelope size, 90 mm by 205 mm.
         * <p>
         *  指定JIS Chou("long")#4信封尺寸,90 mm x 205 mm。
         * 
         */
        public static final MediaSize CHOU_4 = new MediaSize(90, 205, Size2DSyntax.MM);
        /**
         * Specifies the JIS Chou ("long") #30 envelope size, 92 mm by 235 mm.
         * <p>
         *  指定JIS Chou("长")#30信封大小,92 mm x 235 mm。
         * 
         */
        public static final MediaSize CHOU_30 = new MediaSize(92, 235, Size2DSyntax.MM);
        /**
         * Specifies the JIS Chou ("long") #40 envelope size, 90 mm by 225 mm.
         * <p>
         *  指定JIS Chou("long")#40信封尺寸,90 mm x 225 mm。
         * 
         */
        public static final MediaSize CHOU_40 = new MediaSize(90, 225, Size2DSyntax.MM);
        /**
         * Specifies the JIS Kaku ("square") #0 envelope size, 287 mm by 382 mm.
         * <p>
         *  指定JIS Kaku("正方形")#0信封大小,287 mm x 382 mm。
         * 
         */
        public static final MediaSize KAKU_0 = new MediaSize(287, 382, Size2DSyntax.MM);
        /**
         * Specifies the JIS Kaku ("square") #1 envelope size, 270 mm by 382 mm.
         * <p>
         *  指定JIS Kaku("正方形")#1信封大小,270 mm×382 mm。
         * 
         */
        public static final MediaSize KAKU_1 = new MediaSize(270, 382, Size2DSyntax.MM);
        /**
         * Specifies the JIS Kaku ("square") #2 envelope size, 240 mm by 332 mm.
         * <p>
         *  指定JIS Kaku("正方形")#2信封尺寸,240毫米×332毫米。
         * 
         */
        public static final MediaSize KAKU_2 = new MediaSize(240, 332, Size2DSyntax.MM);
        /**
         * Specifies the JIS Kaku ("square") #3 envelope size, 216 mm by 277 mm.
         * <p>
         *  指定JIS Kaku("正方形")#3信封大小,216 mm×277 mm。
         * 
         */
        public static final MediaSize KAKU_3 = new MediaSize(216, 277, Size2DSyntax.MM);
        /**
         * Specifies the JIS Kaku ("square") #4 envelope size, 197 mm by 267 mm.
         * <p>
         *  指定JIS Kaku("正方形")#4信封尺寸,197 mm x 267 mm。
         * 
         */
        public static final MediaSize KAKU_4 = new MediaSize(197, 267, Size2DSyntax.MM);
        /**
         * Specifies the JIS Kaku ("square") #5 envelope size, 190 mm by 240 mm.
         * <p>
         *  指定JIS Kaku("正方形")#5信封尺寸,190mm×240mm。
         * 
         */
        public static final MediaSize KAKU_5 = new MediaSize(190, 240, Size2DSyntax.MM);
        /**
         * Specifies the JIS Kaku ("square") #6 envelope size, 162 mm by 229 mm.
         * <p>
         *  指定JIS Kaku("正方形")#6信封大小,162 mm x 229 mm。
         * 
         */
        public static final MediaSize KAKU_6 = new MediaSize(162, 229, Size2DSyntax.MM);
        /**
         * Specifies the JIS Kaku ("square") #7 envelope size, 142 mm by 205 mm.
         * <p>
         *  指定JIS Kaku("正方形")#7信封尺寸,142 mm×205 mm。
         * 
         */
        public static final MediaSize KAKU_7 = new MediaSize(142, 205, Size2DSyntax.MM);
        /**
         * Specifies the JIS Kaku ("square") #8 envelope size, 119 mm by 197 mm.
         * <p>
         *  指定JIS Kaku("正方形")#8信封大小,119 mm乘197 mm。
         * 
         */
        public static final MediaSize KAKU_8 = new MediaSize(119, 197, Size2DSyntax.MM);
        /**
         * Specifies the JIS Kaku ("square") #20 envelope size, 229 mm by 324 mm.
         * <p>
         *  指定JIS Kaku("正方形")#20信封大小,229 mm x 324 mm。
         * 
         */
        public static final MediaSize KAKU_20 = new MediaSize(229, 324, Size2DSyntax.MM);
        /**
         * Specifies the JIS Kaku ("square") A4 envelope size, 228 mm by 312 mm.
         * <p>
         *  指定JIS Kaku("正方形")A4信封尺寸,228 mm x 312 mm。
         * 
         */
        public static final MediaSize KAKU_A4 = new MediaSize(228, 312, Size2DSyntax.MM);
        /**
         * Specifies the JIS You ("Western") #1 envelope size, 120 mm by 176 mm.
         * <p>
         * 指定JIS You("Western")#1信封大小,120 mm x 176 mm。
         * 
         */
        public static final MediaSize YOU_1 = new MediaSize(120, 176, Size2DSyntax.MM);
        /**
         * Specifies the JIS You ("Western") #2 envelope size, 114 mm by 162 mm.
         * <p>
         *  指定JIS You("Western")#2信封尺寸,114 mm x 162 mm。
         * 
         */
        public static final MediaSize YOU_2 = new MediaSize(114, 162, Size2DSyntax.MM);
        /**
         * Specifies the JIS You ("Western") #3 envelope size, 98 mm by 148 mm.
         * <p>
         *  指定JIS You("Western")#3信封大小,98 mm x 148 mm。
         * 
         */
        public static final MediaSize YOU_3 = new MediaSize(98, 148, Size2DSyntax.MM);
        /**
         * Specifies the JIS You ("Western") #4 envelope size, 105 mm by 235 mm.
         * <p>
         *  指定JIS You("Western")#4信封大小,105 mm x 235 mm。
         * 
         */
        public static final MediaSize YOU_4 = new MediaSize(105, 235, Size2DSyntax.MM);
        /**
         * Specifies the JIS You ("Western") #5 envelope size, 95 mm by 217 mm.
         * <p>
         *  指定JIS You("Western")#5信封大小,95 mm x 217 mm。
         * 
         */
        public static final MediaSize YOU_5 = new MediaSize(95, 217, Size2DSyntax.MM);
        /**
         * Specifies the JIS You ("Western") #6 envelope size, 98 mm by 190 mm.
         * <p>
         *  指定JIS You("Western")#6信封尺寸,98 mm×190 mm。
         * 
         */
        public static final MediaSize YOU_6 = new MediaSize(98, 190, Size2DSyntax.MM);
        /**
         * Specifies the JIS You ("Western") #7 envelope size, 92 mm by 165 mm.
         * <p>
         *  指定JIS You("Western")#7信封尺寸,92 mm x 165 mm。
         * 
         */
        public static final MediaSize YOU_7 = new MediaSize(92, 165, Size2DSyntax.MM);
        /**
         * Hide all constructors.
         * <p>
         *  隐藏所有构造函数。
         * 
         */
        private JIS() {
        }
    }

    /**
     * Class MediaSize.NA includes {@link MediaSize MediaSize} values for North
     * American media.
     * <p>
     *  MediaSize.NA类包括北美媒体的{@link MediaSize MediaSize}值。
     * 
     */
    public final static class NA {

        /**
         * Specifies the North American letter size, 8.5 inches by 11 inches.
         * <p>
         *  指定北美信件大小,8.5英寸x 11英寸。
         * 
         */
        public static final MediaSize
            LETTER = new MediaSize(8.5f, 11.0f, Size2DSyntax.INCH,
                                                MediaSizeName.NA_LETTER);
        /**
         * Specifies the North American legal size, 8.5 inches by 14 inches.
         * <p>
         *  指定北美法定大小,8.5英寸x 14英寸。
         * 
         */
        public static final MediaSize
            LEGAL = new MediaSize(8.5f, 14.0f, Size2DSyntax.INCH,
                                               MediaSizeName.NA_LEGAL);
        /**
         * Specifies the North American 5 inch by 7 inch paper.
         * <p>
         *  指定北美5英寸×7英寸的纸张。
         * 
         */
        public static final MediaSize
            NA_5X7 = new MediaSize(5, 7, Size2DSyntax.INCH,
                                   MediaSizeName.NA_5X7);
        /**
         * Specifies the North American 8 inch by 10 inch paper.
         * <p>
         *  指定北美8英寸×10英寸纸张。
         * 
         */
        public static final MediaSize
            NA_8X10 = new MediaSize(8, 10, Size2DSyntax.INCH,
                                   MediaSizeName.NA_8X10);
        /**
         * Specifies the North American Number 9 business envelope size,
         * 3.875 inches by 8.875 inches.
         * <p>
         *  指定北美9号商业信封大小,3.875英寸乘8.875英寸。
         * 
         */
        public static final MediaSize
            NA_NUMBER_9_ENVELOPE =
            new MediaSize(3.875f, 8.875f, Size2DSyntax.INCH,
                          MediaSizeName.NA_NUMBER_9_ENVELOPE);
        /**
         * Specifies the North American Number 10 business envelope size,
         * 4.125 inches by 9.5 inches.
         * <p>
         *  指定北美10号商业信封大小,4.125英寸乘9.5英寸。
         * 
         */
        public static final MediaSize
            NA_NUMBER_10_ENVELOPE =
            new MediaSize(4.125f, 9.5f, Size2DSyntax.INCH,
                          MediaSizeName.NA_NUMBER_10_ENVELOPE);
        /**
         * Specifies the North American Number 11 business envelope size,
         * 4.5 inches by 10.375 inches.
         * <p>
         *  指定北美11号商业信封大小,4.5英寸x 10.375英寸。
         * 
         */
        public static final MediaSize
            NA_NUMBER_11_ENVELOPE =
            new MediaSize(4.5f, 10.375f, Size2DSyntax.INCH,
                          MediaSizeName.NA_NUMBER_11_ENVELOPE);
        /**
         * Specifies the North American Number 12 business envelope size,
         * 4.75 inches by 11 inches.
         * <p>
         *  指定北美12号商业信封大小,4.75英寸x 11英寸。
         * 
         */
        public static final MediaSize
            NA_NUMBER_12_ENVELOPE =
            new MediaSize(4.75f, 11.0f, Size2DSyntax.INCH,
                          MediaSizeName.NA_NUMBER_12_ENVELOPE);
        /**
         * Specifies the North American Number 14 business envelope size,
         * 5 inches by 11.5 inches.
         * <p>
         *  指定北美14号商业信封尺寸,5英寸x 11.5英寸。
         * 
         */
        public static final MediaSize
            NA_NUMBER_14_ENVELOPE =
            new MediaSize(5.0f, 11.5f, Size2DSyntax.INCH,
                          MediaSizeName.NA_NUMBER_14_ENVELOPE);

        /**
         * Specifies the North American 6 inch by 9 inch envelope size.
         * <p>
         *  指定北美6英寸x 9英寸信封尺寸。
         * 
         */
        public static final MediaSize
            NA_6X9_ENVELOPE = new MediaSize(6.0f, 9.0f, Size2DSyntax.INCH,
                                            MediaSizeName.NA_6X9_ENVELOPE);
        /**
         * Specifies the North American 7 inch by 9 inch envelope size.
         * <p>
         *  指定北美7英寸x 9英寸的信封尺寸。
         * 
         */
        public static final MediaSize
            NA_7X9_ENVELOPE = new MediaSize(7.0f, 9.0f, Size2DSyntax.INCH,
                                            MediaSizeName.NA_7X9_ENVELOPE);
        /**
         * Specifies the North American 9 inch by 11 inch envelope size.
         * <p>
         *  指定北美9英寸x 11英寸的信封尺寸。
         * 
         */
        public static final MediaSize
            NA_9x11_ENVELOPE = new MediaSize(9.0f, 11.0f, Size2DSyntax.INCH,
                                             MediaSizeName.NA_9X11_ENVELOPE);
        /**
         * Specifies the North American 9 inch by 12 inch envelope size.
         * <p>
         * 指定北美9英寸x 12英寸的信封尺寸。
         * 
         */
        public static final MediaSize
            NA_9x12_ENVELOPE = new MediaSize(9.0f, 12.0f, Size2DSyntax.INCH,
                                             MediaSizeName.NA_9X12_ENVELOPE);
        /**
         * Specifies the North American 10 inch by 13 inch envelope size.
         * <p>
         *  指定北美10英寸x 13英寸的信封尺寸。
         * 
         */
        public static final MediaSize
            NA_10x13_ENVELOPE = new MediaSize(10.0f, 13.0f, Size2DSyntax.INCH,
                                              MediaSizeName.NA_10X13_ENVELOPE);
        /**
         * Specifies the North American 10 inch by 14 inch envelope size.
         * <p>
         *  指定北美10英寸x 14英寸的信封尺寸。
         * 
         */
        public static final MediaSize
            NA_10x14_ENVELOPE = new MediaSize(10.0f, 14.0f, Size2DSyntax.INCH,
                                              MediaSizeName.NA_10X14_ENVELOPE);
        /**
         * Specifies the North American 10 inch by 15 inch envelope size.
         * <p>
         *  指定北美10英寸x 15英寸的信封尺寸。
         * 
         */
        public static final MediaSize
            NA_10X15_ENVELOPE = new MediaSize(10.0f, 15.0f, Size2DSyntax.INCH,
                                              MediaSizeName.NA_10X15_ENVELOPE);
        /**
         * Hide all constructors.
         * <p>
         *  隐藏所有构造函数。
         * 
         */
        private NA() {
        }
    }

    /**
     * Class MediaSize.Engineering includes {@link MediaSize MediaSize} values
     * for engineering media.
     * <p>
     *  MediaSize.Engineering类包括工程介质的{@link MediaSize MediaSize}值。
     * 
     */
    public final static class Engineering {

        /**
         * Specifies the engineering A size, 8.5 inch by 11 inch.
         * <p>
         *  指定工程A尺寸,8.5英寸×11英寸。
         * 
         */
        public static final MediaSize
            A = new MediaSize(8.5f, 11.0f, Size2DSyntax.INCH,
                              MediaSizeName.A);
        /**
         * Specifies the engineering B size, 11 inch by 17 inch.
         * <p>
         *  指定工程B尺寸,11英寸x 17英寸。
         * 
         */
        public static final MediaSize
            B = new MediaSize(11.0f, 17.0f, Size2DSyntax.INCH,
                              MediaSizeName.B);
        /**
         * Specifies the engineering C size, 17 inch by 22 inch.
         * <p>
         *  指定工程C尺寸,17英寸x 22英寸。
         * 
         */
        public static final MediaSize
            C = new MediaSize(17.0f, 22.0f, Size2DSyntax.INCH,
                              MediaSizeName.C);
        /**
         * Specifies the engineering D size, 22 inch by 34 inch.
         * <p>
         *  指定工程D尺寸,22 x 34英寸。
         * 
         */
        public static final MediaSize
            D = new MediaSize(22.0f, 34.0f, Size2DSyntax.INCH,
                              MediaSizeName.D);
        /**
         * Specifies the engineering E size, 34 inch by 44 inch.
         * <p>
         *  指定工程E尺寸,34 x 44英寸。
         * 
         */
        public static final MediaSize
            E = new MediaSize(34.0f, 44.0f, Size2DSyntax.INCH,
                              MediaSizeName.E);
        /**
         * Hide all constructors.
         * <p>
         *  隐藏所有构造函数。
         * 
         */
        private Engineering() {
        }
    }

    /**
     * Class MediaSize.Other includes {@link MediaSize MediaSize} values for
     * miscellaneous media.
     * <p>
     *  MediaSize类。其他包括用于其他媒体的{@link MediaSize MediaSize}值。
     * 
     */
    public final static class Other {
        /**
         * Specifies the executive size, 7.25 inches by 10.5 inches.
         * <p>
         *  指定执行尺寸,7.25 x 10.5英寸。
         * 
         */
        public static final MediaSize
            EXECUTIVE = new MediaSize(7.25f, 10.5f, Size2DSyntax.INCH,
                                      MediaSizeName.EXECUTIVE);
        /**
         * Specifies the ledger size, 11 inches by 17 inches.
         * <p>
         *  指定分类帐大小,11英寸x 17英寸。
         * 
         */
        public static final MediaSize
            LEDGER = new MediaSize(11.0f, 17.0f, Size2DSyntax.INCH,
                                   MediaSizeName.LEDGER);

        /**
         * Specifies the tabloid size, 11 inches by 17 inches.
         * <p>
         *  指定小报大小,11英寸乘17英寸。
         * 
         * 
         * @since 1.5
         */
        public static final MediaSize
            TABLOID = new MediaSize(11.0f, 17.0f, Size2DSyntax.INCH,
                                   MediaSizeName.TABLOID);

        /**
         * Specifies the invoice size, 5.5 inches by 8.5 inches.
         * <p>
         *  指定发票大小,5.5英寸x 8.5英寸。
         * 
         */
        public static final MediaSize
            INVOICE = new MediaSize(5.5f, 8.5f, Size2DSyntax.INCH,
                              MediaSizeName.INVOICE);
        /**
         * Specifies the folio size, 8.5 inches by 13 inches.
         * <p>
         *  指定作品集大小,8.5英寸x 13英寸。
         * 
         */
        public static final MediaSize
            FOLIO = new MediaSize(8.5f, 13.0f, Size2DSyntax.INCH,
                                  MediaSizeName.FOLIO);
        /**
         * Specifies the quarto size, 8.5 inches by 10.83 inches.
         * <p>
         *  指定四分尺寸,8.5英寸乘10.83英寸。
         * 
         */
        public static final MediaSize
            QUARTO = new MediaSize(8.5f, 10.83f, Size2DSyntax.INCH,
                                   MediaSizeName.QUARTO);
        /**
         * Specifies the Italy envelope size, 110 mm by 230 mm.
         * <p>
         *  指定意大利信封尺寸,110 mm x 230 mm。
         * 
         */
        public static final MediaSize
        ITALY_ENVELOPE = new MediaSize(110, 230, Size2DSyntax.MM,
                                       MediaSizeName.ITALY_ENVELOPE);
        /**
         * Specifies the Monarch envelope size, 3.87 inch by 7.5 inch.
         * <p>
         *  指定Monarch信封大小,3.87英寸乘7.5英寸。
         * 
         */
        public static final MediaSize
        MONARCH_ENVELOPE = new MediaSize(3.87f, 7.5f, Size2DSyntax.INCH,
                                         MediaSizeName.MONARCH_ENVELOPE);
        /**
         * Specifies the Personal envelope size, 3.625 inch by 6.5 inch.
         * <p>
         *  指定个人信封大小,3.625英寸x 6.5英寸。
         * 
         */
        public static final MediaSize
        PERSONAL_ENVELOPE = new MediaSize(3.625f, 6.5f, Size2DSyntax.INCH,
                                         MediaSizeName.PERSONAL_ENVELOPE);
        /**
         * Specifies the Japanese postcard size, 100 mm by 148 mm.
         * <p>
         *  指定日本明信片尺寸,100 mm x 148 mm。
         * 
         */
        public static final MediaSize
            JAPANESE_POSTCARD = new MediaSize(100, 148, Size2DSyntax.MM,
                                              MediaSizeName.JAPANESE_POSTCARD);
        /**
         * Specifies the Japanese Double postcard size, 148 mm by 200 mm.
         * <p>
         *  指定日本双明信片尺寸,148 mm x 200 mm。
         * 
         */
        public static final MediaSize
            JAPANESE_DOUBLE_POSTCARD = new MediaSize(148, 200, Size2DSyntax.MM,
                                     MediaSizeName.JAPANESE_DOUBLE_POSTCARD);
        /**
         * Hide all constructors.
         * <p>
         *  隐藏所有构造函数。
         * 
         */
        private Other() {
        }
    }

    /* force loading of all the subclasses so that the instances
     * are created and inserted into the hashmap.
     * <p>
     *  被创建并插入到hashmap中。
     */
    static {
        MediaSize ISOA4 = ISO.A4;
        MediaSize JISB5 = JIS.B5;
        MediaSize NALETTER = NA.LETTER;
        MediaSize EngineeringC = Engineering.C;
        MediaSize OtherEXECUTIVE = Other.EXECUTIVE;
    }
}
