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
import javax.print.attribute.ResolutionSyntax;
import javax.print.attribute.DocAttribute;
import javax.print.attribute.PrintRequestAttribute;
import javax.print.attribute.PrintJobAttribute;

/**
 * Class PrinterResolution is a printing attribute class that specifies an
 * exact resolution supported by a printer or to be used for a print job.
 * This attribute assumes that printers have a small set of device resolutions
 * at which they can operate rather than a continuum.
 * <p>
 * PrinterResolution is used in multiple ways:
 * <OL TYPE=1>
 * <LI>
 * When a client searches looking for a printer that supports the client's
 * desired resolution exactly (no more, no less), the client specifies
 * an instance of class PrinterResolution indicating the exact resolution the
 * client wants. Only printers supporting that exact resolution will match the
 * search.
 * <P>
 * <LI>
 * When a client needs to print a job using the client's desired resolution
 * exactly (no more, no less), the client specifies an instance of class
 * PrinterResolution as an attribute of the Print Job. This will fail if the
 * Print Job doesn't support that exact resolution, and Fidelity is set to
 * true.
 * </OL>
 * If a client wants to locate a printer supporting a resolution
 * greater than some required minimum, then it may be necessary to exclude
 * this attribute from a lookup request and to directly query the set of
 * supported resolutions, and specify the one that most closely meets
 * the client's requirements.
 * In some cases this may be more simply achieved by specifying a
 * PrintQuality attribute which often controls resolution.
 * <P>
 * <P>
 * <B>IPP Compatibility:</B> The information needed to construct an IPP
 * <CODE>"printer-resolution"</CODE> attribute can be obtained by calling
 * methods on the PrinterResolution object. The category name returned by
 * <CODE>getName()</CODE> gives the IPP attribute name.
 * <P>
 *
 * <p>
 *  类PrinterResolution是一个打印属性类,指定打印机支持的或用于打印作业的精确分辨率。此属性假定打印机具有一组小的设备分辨率,以便它们可以操作,而不是连续。
 * <p>
 *  PrinterResolution有多种使用方式：
 * <OL TYPE=1>
 * <LI>
 *  当客户端搜索寻找支持客户端期望的分辨率(不再更多,不少于)的打印机时,客户端指定类PrinterResolution的实例,指示客户端想要的确切分辨率。只有支持该精确分辨率的打印机将匹配搜索。
 * <P>
 * <LI>
 *  当客户端需要使用客户端期望的分辨率(不再更多,不少于)打印作业时,客户端将打印分辨率类的实例指定为打印作业的属性。如果打印作业不支持该精确分辨率,并且Fidelity设置为true,这将失败。
 * </OL>
 *  如果客户端想要定位支持大于某一所需最小值的分辨率的打印机,则可能有必要从查找请求中排除该属性,并直接查询支持的分辨率集合,并指定最接近客户端的分辨率要求。
 * 在某些情况下,这可以通过指定通常控制分辨率的PrintQuality属性来更简单地实现。
 * <P>
 * <P>
 * <B> IPP兼容性：</B>构造IPP <CODE>"打印机分辨率"</CODE>属性所需的信息可以通过调用PrinterResolution对象上的方法获得。
 * 由<CODE> getName()</CODE>返回的类别名称给出了IPP属性名称。
 * <P>
 * 
 * 
 * @author  David Mendenhall
 * @author  Alan Kaminsky
 */
public final class PrinterResolution    extends ResolutionSyntax
        implements DocAttribute, PrintRequestAttribute, PrintJobAttribute {

    private static final long serialVersionUID = 13090306561090558L;

    /**
     * Construct a new printer resolution attribute from the given items.
     *
     * <p>
     *  从给定项目构造新的打印机分辨率属性。
     * 
     * 
     * @param  crossFeedResolution
     *     Cross feed direction resolution.
     * @param  feedResolution
     *     Feed direction resolution.
     * @param  units
     *    Unit conversion factor, e.g. <code>ResolutionSyntax.DPI</CODE>
     * or <code>ResolutionSyntax.DPCM</CODE>.
     *
     * @exception  IllegalArgumentException
     *     (unchecked exception) Thrown if {@code crossFeedResolution < 1} or
     *     {@code feedResolution < 1} or {@code units < 1}.
     */
    public PrinterResolution(int crossFeedResolution, int feedResolution,
                             int units) {
        super (crossFeedResolution, feedResolution, units);
    }

    /**
     * Returns whether this printer resolution attribute is equivalent to the
     * passed in object. To be equivalent, all of the following conditions
     * must be true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class PrinterResolution.
     * <LI>
     * This attribute's cross feed direction resolution is equal to
     * <CODE>object</CODE>'s cross feed direction resolution.
     * <LI>
     * This attribute's feed direction resolution is equal to
     * <CODE>object</CODE>'s feed direction resolution.
     * </OL>
     *
     * <p>
     *  返回此打印机分辨率属性是否等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE>对象</CODE>是PrinterResolution类的实例。
     * <LI>
     *  此属性的横向进纸方向分辨率等于<CODE>对象</CODE>的横向进纸方向分辨率。
     * <LI>
     *  此属性的进给方向分辨率等于<CODE>对象</CODE>的进给方向分辨率。
     * </OL>
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this printer
     *          resolution attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return (super.equals (object) &&
                object instanceof PrinterResolution);
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class PrinterResolution, the category is class PrinterResolution itself.
     *
     * <p>
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return PrinterResolution.class;
                }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class PrinterResolution, the
     * category name is <CODE>"printer-resolution"</CODE>.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于PrinterResolution类,类别是PrinterResolution类本身。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "printer-resolution";
    }

}
