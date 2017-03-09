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
package javax.print.attribute.standard;

import java.net.URI;

import javax.print.attribute.Attribute;
import javax.print.attribute.URISyntax;
import javax.print.attribute.PrintServiceAttribute;

/**
 * Class PrinterMoreInfo is a printing attribute class, a URI, that is used to
 * obtain more information about this specific printer. For example, this
 * could be an HTTP type URI referencing an HTML page accessible to a web
 * browser. The information obtained from this URI is intended for end user
 * consumption. Features outside the scope of the Print Service API can be
 * accessed from this URI.
 * The information is intended to be specific to this printer instance and
 * site specific services (e.g. job pricing, services offered, end user
 * assistance).
 * <P>
 * In contrast, the {@link PrinterMoreInfoManufacturer
 * PrinterMoreInfoManufacturer} attribute is used to find out more information
 * about this general kind of printer rather than this specific printer.
 * <P>
 * <B>IPP Compatibility:</B> The string form returned by
 * <CODE>toString()</CODE>  gives the IPP uri value.
 * The category name returned by <CODE>getName()</CODE>
 * gives the IPP attribute name.
 * <P>
 *
 * <p>
 *  类PrinterMoreInfo是一个打印属性类,一个URI,用于获取有关此特定打印机的更多信息。例如,这可以是引用web浏览器可访问的HTML页面的HTTP类型URI。
 * 从此URI获取的信息用于最终用户消费。可以从此URI访问打印服务API范围之外的功能。该信息旨在特定于此打印机实例和特定于站点的服务(例如作业定价,提供的服务,最终用户帮助)。
 * <P>
 *  相比之下,{@link PrinterMoreInfoManufacturer PrinterMoreInfoManufacturer}属性用于查找有关此常规类型的打印机而不是此特定打印机的更多信息。
 * <P>
 *  <B> IPP兼容性：</B> <CODE> toString()</CODE>返回的字符串形式给出了IPP uri值。
 * 由<CODE> getName()</CODE>返回的类别名称给出了IPP属性名称。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public final class PrinterMoreInfo extends URISyntax
        implements PrintServiceAttribute {

    private static final long serialVersionUID = 4555850007675338574L;

    /**
     * Constructs a new printer more info attribute with the specified URI.
     *
     * <p>
     *  使用指定的URI构造新的打印机更多信息属性。
     * 
     * 
     * @param  uri  URI.
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <CODE>uri</CODE> is null.
     */
    public PrinterMoreInfo(URI uri) {
        super (uri);
    }

    /**
     * Returns whether this printer more info attribute is equivalent to the
     * passed in object. To be equivalent, all of the following conditions
     * must be true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class PrinterMoreInfo.
     * <LI>
     * This printer more info attribute's URI and <CODE>object</CODE>'s URI
     * are equal.
     * </OL>
     *
     * <p>
     *  返回此打印机的more info属性是否等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE>对象</CODE>是PrinterMoreInfo类的实例。
     * <LI>
     *  此打印机的info属性的URI和<CODE>对象</CODE>的URI是相等的。
     * </OL>
     * 
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this printer
     *          more info attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return (super.equals(object) &&
                object instanceof PrinterMoreInfo);
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class PrinterMoreInfo, the category is class PrinterMoreInfo itself.
     *
     * <p>
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return PrinterMoreInfo.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class PrinterMoreInfo, the
     * category name is <CODE>"printer-more-info"</CODE>.
     *
     * <p>
     * 获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于PrinterMoreInfo类,类别是PrinterMoreInfo类本身。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "printer-more-info";
    }

}
