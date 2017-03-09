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


package javax.print.attribute;

import java.io.Serializable;

/**
 * Class Size2DSyntax is an abstract base class providing the common
 * implementation of all attributes denoting a size in two dimensions.
 * <P>
 * A two-dimensional size attribute's value consists of two items, the X
 * dimension and the Y dimension. A two-dimensional size attribute may be
 * constructed by supplying the two values and indicating the units in which the
 * values are measured. Methods are provided to return a two-dimensional size
 * attribute's values, indicating the units in which the values are to be
 * returned. The two most common size units are inches (in) and millimeters
 * (mm), and exported constants {@link #INCH INCH} and {@link #MM
 * MM} are provided for indicating those units.
 * <P>
 * Once constructed, a two-dimensional size attribute's value is immutable.
 * <P>
 * <B>Design</B>
 * <P>
 * A two-dimensional size attribute's X and Y dimension values are stored
 * internally as integers in units of micrometers (&#181;m), where 1 micrometer
 * = 10<SUP>-6</SUP> meter = 1/1000 millimeter = 1/25400 inch. This permits
 * dimensions to be represented exactly to a precision of 1/1000 mm (= 1
 * &#181;m) or 1/100 inch (= 254 &#181;m). If fractional inches are expressed in
 * negative powers of two, this permits dimensions to be represented exactly to
 * a precision of 1/8 inch (= 3175 &#181;m) but not 1/16 inch (because 1/16 inch
 * does not equal an integral number of &#181;m).
 * <P>
 * Storing the dimensions internally in common units of &#181;m lets two size
 * attributes be compared without regard to the units in which they were
 * created; for example, 8.5 in will compare equal to 215.9 mm, as they both are
 * stored as 215900 &#181;m. For example, a lookup service can
 * match resolution attributes based on equality of their serialized
 * representations regardless of the units in which they were created. Using
 * integers for internal storage allows precise equality comparisons to be done,
 * which would not be guaranteed if an internal floating point representation
 * were used. Note that if you're looking for U.S. letter sized media in metric
 * units, you have to search for a media size of 215.9 x 279.4 mm; rounding off
 * to an integral 216 x 279 mm will not match.
 * <P>
 * The exported constant {@link #INCH INCH} is actually the
 * conversion factor by which to multiply a value in inches to get the value in
 * &#181;m. Likewise, the exported constant {@link #MM MM} is the
 * conversion factor by which to multiply a value in mm to get the value in
 * &#181;m. A client can specify a resolution value in units other than inches
 * or mm by supplying its own conversion factor. However, since the internal
 * units of &#181;m was chosen with supporting only the external units of inch
 * and mm in mind, there is no guarantee that the conversion factor for the
 * client's units will be an exact integer. If the conversion factor isn't an
 * exact integer, resolution values in the client's units won't be stored
 * precisely.
 * <P>
 *
 * <p>
 *  Class Size2DSyntax是一个抽象基类,提供表示二维大小的所有属性的通用实现。
 * <P>
 *  二维大小属性的值由两个项目组成,即X维度和Y维度。可以通过提供两个值并指示测量值的单位来构建二维大小属性。提供了返回二维大小属性值的方法,指示要返回值的单位。
 * 两个最常见的尺寸单位是英寸(in)和毫米(mm),并且提供导出的常数{@link #INCH INCH}和{@link #MM MM}以指示这些单位。
 * <P>
 *  一旦构造,二维大小属性的值是不可变的。
 * <P>
 *  <B>设计</B>
 * <P>
 *  二维尺寸属性的X和Y尺寸值在内部以微米(μm)为单位存储为整数,其中1微米= 10 -6米= 1/1000毫米= 1/25400英寸。
 * 这允许尺寸精确地表示为1/1000mm(=1μm)或1/100英寸(=254μm)的精度。
 * 如果分数英寸用2的负倍数表示,这允许尺寸精确地表示为1/8英寸(=3175μm)的精度,而不是1/16英寸(因为1/16英寸不等于整数倍μm)。
 * <P>
 * 以μm的公共单位内部存储尺寸允许比较两个尺寸属性而不考虑它们被创建的单位;例如,8.5in将等于215.9mm,因为它们都被存储为215900μm。
 * 例如,查找服务可以基于它们的序列化表示的等同性来匹配分辨率属性,而不管它们在其中被创建的单位。使用整数用于内部存储允许进行精确的平等比较,如果使用内部浮点表示,则不能保证。
 * 请注意,如果您要以公制单位查找美国信件大小的媒体,则必须搜索215.9 x 279.4 mm的媒体大小;四舍五入为整数216 x 279毫米将不匹配。
 * <P>
 * 导出的常数{@link #INCH INCH}实际上是乘以一个以英寸为单位的值的转换因子,以得到以μm为单位的值。
 * 同样,导出的常数{@link #MM MM}是乘以一个以mm为单位的值的转换因子,以获得以μm为单位的值。客户端可以通过提供其自己的转换因子来指定除英寸或mm以外的单位的分辨率值。
 * 然而,由于选择了μm的内部单位,仅支持英寸和mm的外部单位,因此不能保证客户端单位的转换因子将是一个确切的整数。如果转换因子不是确切的整数,客户端单位中的分辨率值将不会精确存储。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public abstract class Size2DSyntax implements Serializable, Cloneable {

    private static final long serialVersionUID = 5584439964938660530L;

    /**
     * X dimension in units of micrometers (&#181;m).
     * <p>
     *  X尺寸以微米(μm)为单位。
     * 
     * 
     * @serial
     */
    private int x;

    /**
     * Y dimension in units of micrometers (&#181;m).
     * <p>
     *  Y尺寸以微米(μm)为单位。
     * 
     * 
     * @serial
     */
    private int y;

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
     *  值表示单位为毫米(mm)。它实际上是将mm乘以mm得到μm(1000)的转换因子。
     * 
     */
    public static final int MM = 1000;


    /**
     * Construct a new two-dimensional size attribute from the given
     * floating-point values.
     *
     * <p>
     *  从给定的浮点值构造新的二维大小属性。
     * 
     * 
     * @param  x  X dimension.
     * @param  y  Y dimension.
     * @param  units
     *     Unit conversion factor, e.g. {@link #INCH INCH} or
     *     {@link #MM MM}.
     *
     * @exception  IllegalArgumentException
     *     (Unchecked exception) Thrown if {@code x < 0} or {@code y < 0} or
     *     {@code units < 1}.
     */
    protected Size2DSyntax(float x, float y, int units) {
        if (x < 0.0f) {
            throw new IllegalArgumentException("x < 0");
        }
        if (y < 0.0f) {
            throw new IllegalArgumentException("y < 0");
        }
        if (units < 1) {
            throw new IllegalArgumentException("units < 1");
        }
        this.x = (int) (x * units + 0.5f);
        this.y = (int) (y * units + 0.5f);
    }

    /**
     * Construct a new two-dimensional size attribute from the given integer
     * values.
     *
     * <p>
     *  从给定的整数值构造新的二维大小属性。
     * 
     * 
     * @param  x  X dimension.
     * @param  y  Y dimension.
     * @param  units
     *     Unit conversion factor, e.g. {@link #INCH INCH} or
     *     {@link #MM MM}.
     *
     * @exception  IllegalArgumentException
     *   (Unchecked exception) Thrown if {@code x < 0} or {@code y < 0}
     *    or {@code units < 1}.
     */
    protected Size2DSyntax(int x, int y, int units) {
        if (x < 0) {
            throw new IllegalArgumentException("x < 0");
        }
        if (y < 0) {
            throw new IllegalArgumentException("y < 0");
        }
        if (units < 1) {
            throw new IllegalArgumentException("units < 1");
        }
        this.x = x * units;
        this.y = y * units;
    }

    /**
     * Convert a value from micrometers to some other units. The result is
     * returned as a floating-point number.
     *
     * <p>
     *  将值从微米转换为其他单位。结果作为浮点数返回。
     * 
     * 
     * @param  x
     *     Value (micrometers) to convert.
     * @param  units
     *     Unit conversion factor, e.g. {@link #INCH <CODE>INCH</CODE>} or
     *     {@link #MM <CODE>MM</CODE>}.
     *
     * @return  The value of <CODE>x</CODE> converted to the desired units.
     *
     * @exception  IllegalArgumentException
     *     (unchecked exception) Thrown if <CODE>units</CODE> < 1.
     */
    private static float convertFromMicrometers(int x, int units) {
        if (units < 1) {
            throw new IllegalArgumentException("units is < 1");
        }
        return ((float)x) / ((float)units);
    }

    /**
     * Get this two-dimensional size attribute's dimensions in the given units
     * as floating-point values.
     *
     * <p>
     *  以给定单位将此二维大小属性的维度作为浮点值。
     * 
     * 
     * @param  units
     *     Unit conversion factor, e.g. {@link #INCH INCH} or {@link #MM MM}.
     *
     * @return  A two-element array with the X dimension at index 0 and the Y
     *          dimension at index 1.
     *
     * @exception  IllegalArgumentException
     *     (unchecked exception) Thrown if {@code units < 1}.
     */
    public float[] getSize(int units) {
        return new float[] {getX(units), getY(units)};
    }

    /**
     * Returns this two-dimensional size attribute's X dimension in the given
     * units as a floating-point value.
     *
     * <p>
     * 以给定单位将此二维大小属性的X维度作为浮点值返回。
     * 
     * 
     * @param  units
     *     Unit conversion factor, e.g. {@link #INCH INCH} or {@link #MM MM}.
     *
     * @return  X dimension.
     *
     * @exception  IllegalArgumentException
     *     (unchecked exception) Thrown if {@code units < 1}.
     */
    public float getX(int units) {
        return convertFromMicrometers(x, units);
    }

    /**
     * Returns this two-dimensional size attribute's Y dimension in the given
     * units as a floating-point value.
     *
     * <p>
     *  将给定单位中的二维大小属性的Y维度作为浮点值返回。
     * 
     * 
     * @param  units
     *     Unit conversion factor, e.g. {@link #INCH INCH} or {@link #MM MM}.
     *
     * @return  Y dimension.
     *
     * @exception  IllegalArgumentException
     *     (unchecked exception) Thrown if {@code units < 1}.
     */
    public float getY(int units) {
        return convertFromMicrometers(y, units);
    }

    /**
     * Returns a string version of this two-dimensional size attribute in the
     * given units. The string takes the form <CODE>"<I>X</I>x<I>Y</I>
     * <I>U</I>"</CODE>, where <I>X</I> is the X dimension, <I>Y</I> is the Y
     * dimension, and <I>U</I> is the units name. The values are displayed in
     * floating point.
     *
     * <p>
     *  以给定单位返回此二维尺寸属性的字符串版本。
     * 字符串采用<CODE>"<I> X </I> x <I> Y </I> <I> U </I>"</CODE>的形式,其中<I> X </I> X维度,Y I是Y维度,并且U I是单位名称。
     * 值以浮点形式显示。
     * 
     * 
     * @param  units
     *     Unit conversion factor, e.g. {@link #INCH INCH} or {@link #MM MM}.
     *
     * @param  unitsName
     *     Units name string, e.g. {@code in} or {@code mm}. If
     *     null, no units name is appended to the result.
     *
     * @return  String version of this two-dimensional size attribute.
     *
     * @exception  IllegalArgumentException
     *     (unchecked exception) Thrown if {@code units < 1}.
     */
    public String toString(int units, String unitsName) {
        StringBuffer result = new StringBuffer();
        result.append(getX (units));
        result.append('x');
        result.append(getY (units));
        if (unitsName != null) {
            result.append(' ');
            result.append(unitsName);
        }
        return result.toString();
    }

    /**
     * Returns whether this two-dimensional size attribute is equivalent to the
     * passed in object. To be equivalent, all of the following conditions must
     * be true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class Size2DSyntax.
     * <LI>
     * This attribute's X dimension is equal to <CODE>object</CODE>'s X
     * dimension.
     * <LI>
     * This attribute's Y dimension is equal to <CODE>object</CODE>'s Y
     * dimension.
     * </OL>
     *
     * <p>
     *  返回此二维大小属性是否等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE> object </CODE>是Size2DSyntax类的实例。
     * <LI>
     *  此属性的X维度等于<CODE>对象</CODE>的X维度。
     * <LI>
     *  此属性的Y维度等于<CODE>对象</CODE>的Y维度。
     * </OL>
     * 
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this
     *          two-dimensional size attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return(object != null &&
               object instanceof Size2DSyntax &&
               this.x == ((Size2DSyntax) object).x &&
               this.y == ((Size2DSyntax) object).y);
    }

    /**
     * Returns a hash code value for this two-dimensional size attribute.
     * <p>
     *  返回此二维尺寸属性的哈希码值。
     * 
     */
    public int hashCode() {
        return (((x & 0x0000FFFF)      ) |
                ((y & 0x0000FFFF) << 16));
    }

    /**
     * Returns a string version of this two-dimensional size attribute. The
     * string takes the form <CODE>"<I>X</I>x<I>Y</I> um"</CODE>, where
     * <I>X</I> is the X dimension and <I>Y</I> is the Y dimension.
     * The values are reported in the internal units of micrometers.
     * <p>
     *  返回此二维尺寸属性的字符串版本。字符串采取形式<CODE>"<I> X </I> x <I> Y </I> um"</CODE>,其中<I> X </I>是X维度, Y </i>是Y维度。
     * 值以微米的内部单位报告。
     * 
     */
    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append(x);
        result.append('x');
        result.append(y);
        result.append(" um");
        return result.toString();
    }

    /**
     * Returns this two-dimensional size attribute's X dimension in units of
     * micrometers (&#181;m). (For use in a subclass.)
     *
     * <p>
     *  以微米(μm)为单位返回此二维尺寸属性的X尺寸。 (用于子类。)
     * 
     * 
     * @return  X dimension (&#181;m).
     */
    protected int getXMicrometers(){
        return x;
    }

    /**
     * Returns this two-dimensional size attribute's Y dimension in units of
     * micrometers (&#181;m). (For use in a subclass.)
     *
     * <p>
     *  以微米(μm)为单位返回此二维尺寸属性的Y尺寸。 (用于子类。)
     * 
     * @return  Y dimension (&#181;m).
     */
    protected int getYMicrometers() {
        return y;
    }

}
