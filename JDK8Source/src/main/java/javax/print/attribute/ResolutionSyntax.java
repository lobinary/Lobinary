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
 * Class ResolutionSyntax is an abstract base class providing the common
 * implementation of all attributes denoting a printer resolution.
 * <P>
 * A resolution attribute's value consists of two items, the cross feed
 * direction resolution and the feed direction resolution. A resolution
 * attribute may be constructed by supplying the two values and indicating the
 * units in which the values are measured. Methods are provided to return a
 * resolution attribute's values, indicating the units in which the values are
 * to be returned. The two most common resolution units are dots per inch (dpi)
 * and dots per centimeter (dpcm), and exported constants {@link #DPI
 * DPI} and {@link #DPCM DPCM} are provided for
 * indicating those units.
 * <P>
 * Once constructed, a resolution attribute's value is immutable.
 * <P>
 * <B>Design</B>
 * <P>
 * A resolution attribute's cross feed direction resolution and feed direction
 * resolution values are stored internally using units of dots per 100 inches
 * (dphi). Storing the values in dphi rather than, say, metric units allows
 * precise integer arithmetic conversions between dpi and dphi and between dpcm
 * and dphi: 1 dpi = 100 dphi, 1 dpcm = 254 dphi. Thus, the values can be stored
 * into and retrieved back from a resolution attribute in either units with no
 * loss of precision. This would not be guaranteed if a floating point
 * representation were used. However, roundoff error will in general occur if a
 * resolution attribute's values are created in one units and retrieved in
 * different units; for example, 600 dpi will be rounded to 236 dpcm, whereas
 * the true value (to five figures) is 236.22 dpcm.
 * <P>
 * Storing the values internally in common units of dphi lets two resolution
 * attributes be compared without regard to the units in which they were
 * created; for example, 300 dpcm will compare equal to 762 dpi, as they both
 * are stored as 76200 dphi. In particular, a lookup service can
 * match resolution attributes based on equality of their serialized
 * representations regardless of the units in which they were created. Again,
 * using integers for internal storage allows precise equality comparisons to be
 * done, which would not be guaranteed if a floating point representation were
 * used.
 * <P>
 * The exported constant {@link #DPI DPI} is actually the
 * conversion factor by which to multiply a value in dpi to get the value in
 * dphi. Likewise, the exported constant {@link #DPCM DPCM} is the
 * conversion factor by which to multiply a value in dpcm to get the value in
 * dphi. A client can specify a resolution value in units other than dpi or dpcm
 * by supplying its own conversion factor. However, since the internal units of
 * dphi was chosen with supporting only the external units of dpi and dpcm in
 * mind, there is no guarantee that the conversion factor for the client's units
 * will be an exact integer. If the conversion factor isn't an exact integer,
 * resolution values in the client's units won't be stored precisely.
 * <P>
 *
 * <p>
 *  类ResolutionSyntax是一个抽象基类,提供表示打印机分辨率的所有属性的通用实现。
 * <P>
 *  分辨率属性的值由两个项目组成,即横向进纸方向分辨率和进纸方向分辨率。可以通过提供两个值并指示测量值的单位来构建分辨率属性。提供了返回分辨率属性值的方法,指示要返回值的单位。
 * 两个最常见的分辨率单位是每英寸点数(dpi)和每厘米点数(dpcm),并且提供导出的常数{@link #DPI DPI}和{@link #DPCM DPCM}以指示这些单位。
 * <P>
 *  一旦构造,解析属性的值是不可变的。
 * <P>
 *  <B>设计</B>
 * <P>
 * 分辨率属性的横向进给方向分辨率和进给方向分辨率值以每100英寸(dphi)的点为单位存储在内部。
 * 将值存储在dphi而不是公制单位允许dpi和dphi之间以及dpcm和dphi之间的精确整数算术转换：1dpi = 100dphi,1dpcm = 254dphi。
 * 因此,可以以没有精度损失的任何单位将值存储到分辨率属性中并从其中检索。如果使用浮点表示,则不能保证。
 * 然而,如果分辨率属性的值在一个单位中创建并且以不同单位检索,则一般会发生舍入误差;例如,600dpi将被舍入到236dpcm,而真实值(到五个数字)是236.22dpcm。
 * <P>
 *  将内部值存储在dphi的公共单元中,可以比较两个解析属性,而不考虑创建它们的单位;例如,300dpcm将比较等于762dpi,因为它们都存储为76200dphi。
 * 特别地,查找服务可以基于它们的序列化表示的等同性来匹配分辨率属性,而不管它们在其中被创建的单位。再次,使用整数用于内部存储允许进行精确的等式比较,如果使用浮点表示,这将不能得到保证。
 * <P>
 * 导出的常数{@link #DPI DPI}实际上是乘以dpi中的值以获得dphi中的值的转换因子。
 * 同样,导出的常数{@link #DPCM DPCM}是用于乘以dpcm中的值以获得dphi中的值的转换因子。客户端可以通过提供其自身的转换因子来指定除dpi或dpcm之外的单位的分辨率值。
 * 然而,由于dphi的内部单元被选择为仅支持dpi和dpcm的外部单元,所以不能保证客户端单元的转换因子将是确切的整数。如果转换因子不是确切的整数,客户端单位中的分辨率值将不会精确存储。
 * <P>
 * 
 * 
 * @author  David Mendenhall
 * @author  Alan Kaminsky
 */
public abstract class ResolutionSyntax implements Serializable, Cloneable {

    private static final long serialVersionUID = 2706743076526672017L;

    /**
     * Cross feed direction resolution in units of dots per 100 inches (dphi).
     * <p>
     *  以每100英寸点数(dphi)为单位的交叉进给方向分辨率。
     * 
     * 
     * @serial
     */
    private int crossFeedResolution;

    /**
     * Feed direction resolution in units of dots per 100 inches (dphi).
     * <p>
     *  进给方向分辨率,单位为每100英寸的点数(dphi)。
     * 
     * 
     * @serial
     */
    private int feedResolution;

    /**
     * Value to indicate units of dots per inch (dpi). It is actually the
     * conversion factor by which to multiply dpi to yield dphi (100).
     * <p>
     *  指示每英寸点数的单位(dpi)的值。它实际上是乘以dpi以产生dphi(100)的转换因子。
     * 
     */
    public static final int DPI = 100;

    /**
     * Value to indicate units of dots per centimeter (dpcm). It is actually
     * the conversion factor by which to multiply dpcm to yield dphi (254).
     * <p>
     *  用于指示每厘米点数单位(dpcm)的值。它实际上是乘以dpcm以产生dphi(254)的转换因子。
     * 
     */
    public static final int DPCM = 254;


    /**
     * Construct a new resolution attribute from the given items.
     *
     * <p>
     *  从给定项目构造新的分辨率属性。
     * 
     * 
     * @param  crossFeedResolution
     *     Cross feed direction resolution.
     * @param  feedResolution
     *     Feed direction resolution.
     * @param units
     *     Unit conversion factor, e.g. {@link #DPI DPI} or
     * {@link    #DPCM DPCM}.
     *
     * @exception  IllegalArgumentException
     *     (unchecked exception) Thrown if {@code crossFeedResolution < 1}
     *     or {@code feedResolution < 1} or {@code units < 1}.
     */
    public ResolutionSyntax(int crossFeedResolution, int feedResolution,
                            int units) {

        if (crossFeedResolution < 1) {
            throw new IllegalArgumentException("crossFeedResolution is < 1");
        }
        if (feedResolution < 1) {
                throw new IllegalArgumentException("feedResolution is < 1");
        }
        if (units < 1) {
                throw new IllegalArgumentException("units is < 1");
        }

        this.crossFeedResolution = crossFeedResolution * units;
        this.feedResolution = feedResolution * units;
    }

    /**
     * Convert a value from dphi to some other units. The result is rounded to
     * the nearest integer.
     *
     * <p>
     *  将值从dphi转换为其他单位。结果四舍五入为最接近的整数。
     * 
     * 
     * @param  dphi
     *     Value (dphi) to convert.
     * @param  units
     *     Unit conversion factor, e.g. {@link #DPI <CODE>DPI</CODE>} or
     * {@link     #DPCM <CODE>DPCM</CODE>}.
     *
     * @return  The value of <CODE>dphi</CODE> converted to the desired units.
     *
     * @exception  IllegalArgumentException
     *     (unchecked exception) Thrown if <CODE>units</CODE> < 1.
     */
    private static int convertFromDphi(int dphi, int units) {
        if (units < 1) {
            throw new IllegalArgumentException(": units is < 1");
        }
        int round = units / 2;
        return (dphi + round) / units;
    }

    /**
     * Get this resolution attribute's resolution values in the given units.
     * The values are rounded to the nearest integer.
     *
     * <p>
     *  以给定单位获取此分辨率属性的分辨率值。值将四舍五入为最接近的整数。
     * 
     * 
     * @param  units
     *     Unit conversion factor, e.g. {@link #DPI DPI} or
     * {@link   #DPCM DPCM}.
     *
     * @return  A two-element array with the cross feed direction resolution
     *          at index 0 and the feed direction resolution at index 1.
     *
     * @exception  IllegalArgumentException
     *     (unchecked exception) Thrown if {@code units < 1}.
     */
    public int[] getResolution(int units) {
        return new int[] { getCrossFeedResolution(units),
                               getFeedResolution(units)
                               };
    }

    /**
     * Returns this resolution attribute's cross feed direction resolution in
     * the given units. The value is rounded to the nearest integer.
     *
     * <p>
     * 以给定单位返回此分辨率属性的横向进纸方向分辨率。值将四舍五入为最接近的整数。
     * 
     * 
     * @param  units
     *     Unit conversion factor, e.g. {@link #DPI DPI} or
     * {@link  #DPCM DPCM}.
     *
     * @return  Cross feed direction resolution.
     *
     * @exception  IllegalArgumentException
     *     (unchecked exception) Thrown if {@code units < 1}.
     */
    public int getCrossFeedResolution(int units) {
        return convertFromDphi (crossFeedResolution, units);
    }

    /**
     * Returns this resolution attribute's feed direction resolution in the
     * given units. The value is rounded to the nearest integer.
     *
     * <p>
     *  以给定单位返回此分辨率属性的Feed方向分辨率。值将四舍五入为最接近的整数。
     * 
     * 
     * @param  units
     *     Unit conversion factor, e.g. {@link #DPI DPI} or {@link
     *     #DPCM DPCM}.
     *
     * @return  Feed direction resolution.
     *
     * @exception  IllegalArgumentException
     *     (unchecked exception) Thrown if {@code units < 1}.
     */
    public int getFeedResolution(int units) {
        return convertFromDphi (feedResolution, units);
    }

    /**
     * Returns a string version of this resolution attribute in the given units.
     * The string takes the form <CODE>"<I>C</I>x<I>F</I> <I>U</I>"</CODE>,
     * where <I>C</I> is the cross feed direction resolution, <I>F</I> is the
     * feed direction resolution, and <I>U</I> is the units name. The values are
     * rounded to the nearest integer.
     *
     * <p>
     *  以给定单位返回此分辨率属性的字符串版本。
     * 字符串采用<CODE>"<I> C </I> x <I> F </I> <I> U </I>"</CODE>的格式,其中<I> C </I>横向进给方向分辨率,I / F是进给方向分辨率,并且<I> U
     *  </I>是单元名称。
     *  以给定单位返回此分辨率属性的字符串版本。值将四舍五入为最接近的整数。
     * 
     * 
     * @param  units
     *     Unit conversion factor, e.g. {@link #DPI CODE>DPI} or {@link
     *     #DPCM DPCM}.
     * @param  unitsName
     *     Units name string, e.g. <CODE>"dpi"</CODE> or <CODE>"dpcm"</CODE>. If
     *     null, no units name is appended to the result.
     *
     * @return  String version of this resolution attribute.
     *
     * @exception  IllegalArgumentException
     *     (unchecked exception) Thrown if {@code units < 1}.
     */
    public String toString(int units, String unitsName) {
        StringBuffer result = new StringBuffer();
        result.append(getCrossFeedResolution (units));
        result.append('x');
        result.append(getFeedResolution (units));
        if (unitsName != null) {
            result.append (' ');
            result.append (unitsName);
        }
        return result.toString();
    }


    /**
     * Determine whether this resolution attribute's value is less than or
     * equal to the given resolution attribute's value. This is true if all
     * of the following conditions are true:
     * <UL>
     * <LI>
     * This attribute's cross feed direction resolution is less than or equal to
     * the <CODE>other</CODE> attribute's cross feed direction resolution.
     * <LI>
     * This attribute's feed direction resolution is less than or equal to the
     * <CODE>other</CODE> attribute's feed direction resolution.
     * </UL>
     *
     * <p>
     *  确定此分辨率属性的值是否小于或等于给定分辨率属性的值。如果满足以下所有条件,则为true：
     * <UL>
     * <LI>
     *  此属性的横向进纸方向分辨率小于或等于<CODE>其他</CODE>属性的横向进纸方向分辨率。
     * <LI>
     *  此属性的Feed方向分辨率小于或等于<CODE>其他</CODE>属性的Feed方向分辨率。
     * </UL>
     * 
     * 
     * @param  other  Resolution attribute to compare with.
     *
     * @return  True if this resolution attribute is less than or equal to the
     *          <CODE>other</CODE> resolution attribute, false otherwise.
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <CODE>other</CODE> is null.
     */
    public boolean lessThanOrEquals(ResolutionSyntax other) {
        return (this.crossFeedResolution <= other.crossFeedResolution &&
                this.feedResolution <= other.feedResolution);
    }


    /**
     * Returns whether this resolution attribute is equivalent to the passed in
     * object. To be equivalent, all of the following conditions must be true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class ResolutionSyntax.
     * <LI>
     * This attribute's cross feed direction resolution is equal to
     * <CODE>object</CODE>'s cross feed direction resolution.
     * <LI>
     * This attribute's feed direction resolution is equal to
     * <CODE>object</CODE>'s feed direction resolution.
     * </OL>
     *
     * <p>
     *  返回此resolution属性是否等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE>对象</CODE>是类ResolutionSyntax的实例。
     * <LI>
     *  此属性的横向进纸方向分辨率等于<CODE>对象</CODE>的横向进纸方向分辨率。
     * <LI>
     *  此属性的进给方向分辨率等于<CODE>对象</CODE>的进给方向分辨率。
     * </OL>
     * 
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this resolution
     *          attribute, false otherwise.
     */
    public boolean equals(Object object) {

        return(object != null &&
               object instanceof ResolutionSyntax &&
               this.crossFeedResolution ==
               ((ResolutionSyntax) object).crossFeedResolution &&
               this.feedResolution ==
               ((ResolutionSyntax) object).feedResolution);
    }

    /**
     * Returns a hash code value for this resolution attribute.
     * <p>
     * 返回此解析度属性的哈希码值。
     * 
     */
    public int hashCode() {
        return(((crossFeedResolution & 0x0000FFFF)) |
               ((feedResolution      & 0x0000FFFF) << 16));
    }

    /**
     * Returns a string version of this resolution attribute. The string takes
     * the form <CODE>"<I>C</I>x<I>F</I> dphi"</CODE>, where <I>C</I> is the
     * cross feed direction resolution and <I>F</I> is the feed direction
     * resolution. The values are reported in the internal units of dphi.
     * <p>
     *  返回此解析度属性的字符串版本。
     * 该字符串采用<CODE>"<I> C </I> x <I> F </I> dphi"</CODE>的形式,其中<I> C </I>是交叉进给方向分辨率, I> F </I>是进给方向分辨率。
     * 值以dphi的内部单位报告。
     * 
     */
    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append(crossFeedResolution);
        result.append('x');
        result.append(feedResolution);
        result.append(" dphi");
        return result.toString();
    }


    /**
     * Returns this resolution attribute's cross feed direction resolution in
     * units of dphi. (For use in a subclass.)
     *
     * <p>
     *  以dphi为单位返回此分辨率属性的交叉进给方向分辨率。 (用于子类。)
     * 
     * 
     * @return  Cross feed direction resolution.
     */
    protected int getCrossFeedResolutionDphi() {
        return crossFeedResolution;
    }

    /**
     * Returns this resolution attribute's feed direction resolution in units
     * of dphi. (For use in a subclass.)
     *
     * <p>
     *  以dphi为单位返回此分辨率属性的进给方向分辨率。 (用于子类。)
     * 
     * @return  Feed direction resolution.
     */
    protected int getFeedResolutionDphi() {
        return feedResolution;
    }

}
