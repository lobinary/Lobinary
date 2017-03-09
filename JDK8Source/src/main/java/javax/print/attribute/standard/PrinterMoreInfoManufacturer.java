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

import java.net.URI;

import javax.print.attribute.Attribute;
import javax.print.attribute.URISyntax;
import javax.print.attribute.PrintServiceAttribute;

/**
 * Class PrinterMoreInfoManufacturer is a printing attribute class, a URI,
 * that is used to obtain more information about this type of device.
 * The information obtained from this URI is intended for end user
 * consumption. Features outside the scope of the Print Service API
 * can be accessed from this URI (e.g.,
 * latest firmware, upgrades, service proxies, optional features available,
 * details on color support). The information is intended to be germane to
 * this kind of printer without regard to site specific modifications or
 * services.
 * <P>
 * In contrast, the {@link PrinterMoreInfo PrinterMoreInfo} attribute is used
 * to find out more information about this specific printer rather than this
 * general kind of printer.
 * <P>
 * <P>
 * <B>IPP Compatibility:</B> The string form returned by
 * <CODE>toString()</CODE> gives the IPP uri value.
 * The category name returned by <CODE>getName()</CODE>
 * gives the IPP attribute name.
 * <P>
 *
 * <p>
 *  类PrinterMoreInfoManufacturer是一个打印属性类,一个URI,用于获取有关此类型设备的更多信息。从此URI获取的信息用于最终用户消费。
 * 可以从此URI访问打印服务API范围之外的功能(例如,最新固件,升级,服务代理,可用的可选功能,颜色支持的详细信息)。该信息旨在与此类打印机密切相关,而不考虑站点特定的修改或服务。
 * <P>
 *  相比之下,{@link PrinterMoreInfo PrinterMoreInfo}属性用于查找有关此特定打印机的更多信息,而不是这种通用类型的打印机。
 * <P>
 * <P>
 *  <B> IPP兼容性：</B> <CODE> toString()</CODE>返回的字符串形式给出了IPP uri值。
 * 由<CODE> getName()</CODE>返回的类别名称给出了IPP属性名称。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public final class PrinterMoreInfoManufacturer extends URISyntax
        implements PrintServiceAttribute {

    private static final long serialVersionUID = 3323271346485076608L;

    /**
     * Constructs a new printer more info manufacturer attribute with the
     * specified URI.
     *
     * <p>
     *  使用指定的URI构造新的打印机更多信息制造商属性。
     * 
     * 
     * @param  uri  URI.
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <CODE>uri</CODE> is null.
     */
    public PrinterMoreInfoManufacturer(URI uri) {
        super (uri);
    }

    /**
     * Returns whether this printer more info manufacturer attribute is
     * equivalent to the passed in object. To be equivalent, all of the
     * following conditions must be true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class
     * PrinterMoreInfoManufacturer.
     * <LI>
     * This printer more info manufacturer attribute's URI and
     * <CODE>object</CODE>'s URI are equal.
     * </OL>
     *
     * <p>
     *  返回此打印机的更多信息制造商属性是否等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE>对象</CODE>是PrinterMoreInfoManufacturer类的实例。
     * <LI>
     *  此打印机更多信息制造商属性的URI和<CODE>对象</CODE>的URI是相等的。
     * </OL>
     * 
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this printer
     *          more info manufacturer attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return (super.equals(object) &&
                object instanceof PrinterMoreInfoManufacturer);
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class PrinterMoreInfoManufacturer, the category is
     * class PrinterMoreInfoManufacturer itself.
     *
     * <p>
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return PrinterMoreInfoManufacturer.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class PrinterMoreInfoManufacturer, the category name is
     * <CODE>"printer-more-info-manufacturer"</CODE>.
     *
     * <p>
     * 获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于PrinterMoreInfoManufacturer类,类别是PrinterMoreInfoManufacturer类本身。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "printer-more-info-manufacturer";
    }

}
