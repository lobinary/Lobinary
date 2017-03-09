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

import javax.print.attribute.Attribute;
import javax.print.attribute.DocAttribute;
import javax.print.attribute.PrintJobAttribute;
import javax.print.attribute.PrintRequestAttribute;

/**
 * Class MediaPrintableArea is a printing attribute used to distinguish
 * the printable and non-printable areas of media.
 * <p>
 * The printable area is specified to be a rectangle, within the overall
 * dimensions of a media.
 * <p>
 * Most printers cannot print on the entire surface of the media, due
 * to printer hardware limitations. This class can be used to query
 * the acceptable values for a supposed print job, and to request an area
 * within the constraints of the printable area to be used in a print job.
 * <p>
 * To query for the printable area, a client must supply a suitable context.
 * Without specifying at the very least the size of the media being used
 * no meaningful value for printable area can be obtained.
 * <p>
 * The attribute is not described in terms of the distance from the edge
 * of the paper, in part to emphasise that this attribute is not independent
 * of a particular media, but must be described within the context of a
 * choice of other attributes. Additionally it is usually more convenient
 * for a client to use the printable area.
 * <p>
 * The hardware's minimum margins is not just a property of the printer,
 * but may be a function of the media size, orientation, media type, and
 * any specified finishings.
 * <code>PrintService</code> provides the method to query the supported
 * values of an attribute in a suitable context :
 * See  {@link javax.print.PrintService#getSupportedAttributeValues(Class,DocFlavor, AttributeSet) PrintService.getSupportedAttributeValues()}
 * <p>
 * The rectangular printable area is defined thus:
 * The (x,y) origin is positioned at the top-left of the paper in portrait
 * mode regardless of the orientation specified in the requesting context.
 * For example a printable area for A4 paper in portrait or landscape
 * orientation will have height {@literal >} width.
 * <p>
 * A printable area attribute's values are stored
 * internally as integers in units of micrometers (&#181;m), where 1 micrometer
 * = 10<SUP>-6</SUP> meter = 1/1000 millimeter = 1/25400 inch. This permits
 * dimensions to be represented exactly to a precision of 1/1000 mm (= 1
 * &#181;m) or 1/100 inch (= 254 &#181;m). If fractional inches are expressed in

 * negative powers of two, this permits dimensions to be represented exactly to
 * a precision of 1/8 inch (= 3175 &#181;m) but not 1/16 inch (because 1/16 inch

 * does not equal an integral number of &#181;m).
 * <p>
 * <B>IPP Compatibility:</B> MediaPrintableArea is not an IPP attribute.
 * <p>
 *  MediaPrintableArea类是用于区分可打印和不可打印的介质区域的打印属性。
 * <p>
 *  在介质的整体尺寸内,可打印区域被指定为矩形。
 * <p>
 *  由于打印机硬件限制,大多数打印机无法在介质的整个表面上打印。该类可以用于查询假定的打印作业的可接受的值,并且请求在打印作业中要使用的可打印区域的约束内的区域。
 * <p>
 *  为了查询可打印区域,客户端必须提供合适的上下文。没有至少指定所使用的介质的尺寸,不能获得可打印区域的有意义的值。
 * <p>
 *  该属性不是根据与纸张边缘的距离描述的,部分地强调该属性不是独立于特定介质,而是必须在选择其他属性的上下文中描述。此外,客户端通常更方便地使用可打印区域。
 * <p>
 * 硬件的最小边距不仅是打印机的属性,而且可以是介质尺寸,方向,介质类型和任何指定的完成的函数。
 *  <code> PrintService </code>提供了在合适的上下文中查询属性支持的值的方法：参见{@link javax.print.PrintService#getSupportedAttributeValues(Class,DocFlavor,AttributeSet)PrintService.getSupportedAttributeValues。
 * 硬件的最小边距不仅是打印机的属性,而且可以是介质尺寸,方向,介质类型和任何指定的完成的函数。
 * <p>
 *  矩形可打印区域定义如下：无论在请求上下文中指定的方向如何,(x,y)原点都以纵向模式位于纸张的左上角。例如,纵向或横向的A4纸可打印区域的高度为{@literal>}。
 * <p>
 *  可打印区域属性的值在内部以微米(μm)为单位存储为整数,其中1微米= 10 -6米= 1/1000毫米= 1/25400英寸。
 * 这允许尺寸精确地表示为1/1000mm(=1μm)或1/100英寸(=254μm)的精度。如果以英寸表示。
 * 
 *  这允许尺寸精确地表示为1/8英寸(=3175μm)的精度,而不是1/16英寸(因为1/16英寸
 * 
 *  不等于μm的整数)。
 * <p>
 *  <B> IPP兼容性：</B> MediaPrintableArea不是IPP属性。
 * 
 */

public final class MediaPrintableArea
      implements DocAttribute, PrintRequestAttribute, PrintJobAttribute {

    private int x, y, w, h;
    private int units;

    private static final long serialVersionUID = -1597171464050795793L;

    /**
     * Value to indicate units of inches (in). It is actually the conversion
     * factor by which to multiply inches to yield &#181;m (25400).
     * <p>
     *  用于指示英寸单位的值(in)。它实际上是将英寸乘以产量μm(25400)的转换因子。
     * 
     */
    public static final int INCH = 25400;

    /**
     * Value to indicate units of millimeters (mm). It is actually the
     * conversion factor by which to multiply mm to yield &#181;m (1000).
     * <p>
     * 值表示单位为毫米(mm)。它实际上是将mm乘以mm得到μm(1000)的转换因子。
     * 
     */
    public static final int MM = 1000;

    /**
      * Constructs a MediaPrintableArea object from floating point values.
      * <p>
      *  根据浮点值构造MediaPrintableArea对象。
      * 
      * 
      * @param x      printable x
      * @param y      printable y
      * @param w      printable width
      * @param h      printable height
      * @param units  in which the values are expressed.
      *
      * @exception  IllegalArgumentException
      *     Thrown if {@code x < 0} or {@code y < 0}
      *     or {@code w <= 0} or {@code h <= 0} or
      *     {@code units < 1}.
      */
    public MediaPrintableArea(float x, float y, float w, float h, int units) {
        if ((x < 0.0) || (y < 0.0) || (w <= 0.0) || (h <= 0.0) ||
            (units < 1)) {
            throw new IllegalArgumentException("0 or negative value argument");
        }

        this.x = (int) (x * units + 0.5f);
        this.y = (int) (y * units + 0.5f);
        this.w = (int) (w * units + 0.5f);
        this.h = (int) (h * units + 0.5f);

    }

    /**
      * Constructs a MediaPrintableArea object from integer values.
      * <p>
      *  从整数值构造MediaPrintableArea对象。
      * 
      * 
      * @param x      printable x
      * @param y      printable y
      * @param w      printable width
      * @param h      printable height
      * @param units  in which the values are expressed.
      *
      * @exception  IllegalArgumentException
      *     Thrown if {@code x < 0} or {@code y < 0}
      *     or {@code w <= 0} or {@code h <= 0} or
      *     {@code units < 1}.
      */
    public MediaPrintableArea(int x, int y, int w, int h, int units) {
        if ((x < 0) || (y < 0) || (w <= 0) || (h <= 0) ||
            (units < 1)) {
            throw new IllegalArgumentException("0 or negative value argument");
        }
        this.x = x * units;
        this.y = y * units;
        this.w = w * units;
        this.h = h * units;

    }

    /**
     * Get the printable area as an array of 4 values in the order
     * x, y, w, h. The values returned are in the given units.
     * <p>
     *  将可打印区域按照x,y,w,h的顺序作为4个值的数组。返回的值以给定单位表示。
     * 
     * 
     * @param  units
     *     Unit conversion factor, e.g. {@link #INCH INCH} or
     *     {@link #MM MM}.
     *
     * @return printable area as array of x, y, w, h in the specified units.
     *
     * @exception  IllegalArgumentException
     *     (unchecked exception) Thrown if {@code units < 1}.
     */
    public float[] getPrintableArea(int units) {
        return new float[] { getX(units), getY(units),
                             getWidth(units), getHeight(units) };
    }

    /**
     * Get the x location of the origin of the printable area in the
     * specified units.
     * <p>
     *  以指定的单位获取可打印区域的原点的x位置。
     * 
     * 
     * @param  units
     *     Unit conversion factor, e.g. {@link #INCH INCH} or
     *     {@link #MM MM}.
     *
     * @return  x location of the origin of the printable area in the
     * specified units.
     *
     * @exception  IllegalArgumentException
     *     (unchecked exception) Thrown if {@code units < 1}.
     */
     public float getX(int units) {
        return convertFromMicrometers(x, units);
     }

    /**
     * Get the y location of the origin of the printable area in the
     * specified units.
     * <p>
     *  以指定的单位获取可打印区域的原点的y位置。
     * 
     * 
     * @param  units
     *     Unit conversion factor, e.g. {@link #INCH INCH} or
     *     {@link #MM MM}.
     *
     * @return  y location of the origin of the printable area in the
     * specified units.
     *
     * @exception  IllegalArgumentException
     *     (unchecked exception) Thrown if {@code units < 1}.
     */
     public float getY(int units) {
        return convertFromMicrometers(y, units);
     }

    /**
     * Get the width of the printable area in the specified units.
     * <p>
     *  以指定的单位获取可打印区域的宽度。
     * 
     * 
     * @param  units
     *     Unit conversion factor, e.g. {@link #INCH INCH} or
     *     {@link #MM MM}.
     *
     * @return  width of the printable area in the specified units.
     *
     * @exception  IllegalArgumentException
     *     (unchecked exception) Thrown if {@code units < 1}.
     */
     public float getWidth(int units) {
        return convertFromMicrometers(w, units);
     }

    /**
     * Get the height of the printable area in the specified units.
     * <p>
     *  以指定的单位获取可打印区域的高度。
     * 
     * 
     * @param  units
     *     Unit conversion factor, e.g. {@link #INCH INCH} or
     *     {@link #MM MM}.
     *
     * @return  height of the printable area in the specified units.
     *
     * @exception  IllegalArgumentException
     *     (unchecked exception) Thrown if {@code units < 1}.
     */
     public float getHeight(int units) {
        return convertFromMicrometers(h, units);
     }

    /**
     * Returns whether this media margins attribute is equivalent to the passed
     * in object.
     * To be equivalent, all of the following conditions must be true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class MediaPrintableArea.
     * <LI>
     * The origin and dimensions are the same.
     * </OL>
     *
     * <p>
     *  返回此媒体边距属性是否等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE>对象</CODE>是MediaPrintableArea类的实例。
     * <LI>
     *  原点和尺寸相同。
     * </OL>
     * 
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this media margins
     *          attribute, false otherwise.
     */
    public boolean equals(Object object) {
        boolean ret = false;
        if (object instanceof MediaPrintableArea) {
           MediaPrintableArea mm = (MediaPrintableArea)object;
           if (x == mm.x &&  y == mm.y && w == mm.w && h == mm.h) {
               ret = true;
           }
        }
        return ret;
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class MediaPrintableArea, the category is
     * class MediaPrintableArea itself.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于MediaPrintableArea类,类别是MediaPrintableArea类本身。
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return MediaPrintableArea.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class MediaPrintableArea,
     * the category name is <CODE>"media-printable-area"</CODE>.
     * <p>This is not an IPP V1.1 attribute.
     *
     * <p>
     *  获取此属性值为实例的类别的名称。
     * <P>
     *  对于MediaPrintableArea类,类别名称为<CODE>"media-printable-area"</CODE>。 <p>这不是IPP V1.1属性。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "media-printable-area";
    }

    /**
     * Returns a string version of this rectangular size attribute in the
     * given units.
     *
     * <p>
     *  以给定单位返回此矩形大小属性的字符串版本。
     * 
     * 
     * @param  units
     *     Unit conversion factor, e.g. {@link #INCH INCH} or
     *     {@link #MM MM}.
     * @param  unitsName
     *     Units name string, e.g. <CODE>"in"</CODE> or <CODE>"mm"</CODE>. If
     *     null, no units name is appended to the result.
     *
     * @return  String version of this two-dimensional size attribute.
     *
     * @exception  IllegalArgumentException
     *     (unchecked exception) Thrown if {@code units < 1}.
     */
    public String toString(int units, String unitsName) {
        if (unitsName == null) {
            unitsName = "";
        }
        float []vals = getPrintableArea(units);
        String str = "("+vals[0]+","+vals[1]+")->("+vals[2]+","+vals[3]+")";
        return str + unitsName;
    }

    /**
     * Returns a string version of this rectangular size attribute in mm.
     * <p>
     *  以毫米为单位返回此矩形尺寸属性的字符串版本。
     * 
     */
    public String toString() {
        return(toString(MM, "mm"));
    }

    /**
     * Returns a hash code value for this attribute.
     * <p>
     * 返回此属性的哈希码值。
     */
    public int hashCode() {
        return x + 37*y + 43*w + 47*h;
    }

    private static float convertFromMicrometers(int x, int units) {
        if (units < 1) {
            throw new IllegalArgumentException("units is < 1");
        }
        return ((float)x) / ((float)units);
    }
}
