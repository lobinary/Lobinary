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

import java.util.Locale;

import javax.print.attribute.Attribute;
import javax.print.attribute.TextSyntax;
import javax.print.attribute.PrintJobAttribute;

/**
 * Class OutputDeviceAssigned is a printing attribute class, a text attribute,
 * that identifies the output device to which the service has assigned this
 * job. If an output device implements an embedded Print Service instance, the
 * printer need not set this attribute. If a print server implements a
 * Print Service instance, the value may be empty (zero- length string) or not
 * returned until the service assigns an output device to the job. This
 * attribute is particularly useful when a single service supports multiple
 * devices (so called "fan-out").
 * <P>
 * <B>IPP Compatibility:</B> The string value gives the IPP name value. The
 * locale gives the IPP natural language. The category name returned by
 * <CODE>getName()</CODE> gives the IPP attribute name.
 * <P>
 *
 * <p>
 *  类OutputDeviceAssigned是打印属性类,文本属性,用于标识服务已为其分配此作业的输出设备。如果输出设备实现嵌入式打印服务实例,则打印机不需要设置此属性。
 * 如果打印服务器实现打印服务实例,则该值可能为空(零长度字符串),或者直到服务将输出设备分配给作业时才返回。当单个服务支持多个设备(所谓的"扇出")时,此属性特别有用。
 * <P>
 *  <B> IPP兼容性：</B>字符串值给出IPP名称值。语言环境提供IPP自然语言。由<CODE> getName()</CODE>返回的类别名称给出了IPP属性名称。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public final class OutputDeviceAssigned extends TextSyntax
    implements PrintJobAttribute {

    private static final long serialVersionUID = 5486733778854271081L;

    /**
     * Constructs a new output device assigned attribute with the given device
     * name and locale.
     *
     * <p>
     *  使用给定的设备名称和区域设置构造新的输出设备分配属性。
     * 
     * 
     * @param  deviceName  Device name.
     * @param  locale      Natural language of the text string. null
     * is interpreted to mean the default locale as returned
     * by <code>Locale.getDefault()</code>
     *
     * @exception  NullPointerException
     *   (unchecked exception) Thrown if <CODE>deviceName</CODE> is null.
     */
    public OutputDeviceAssigned(String deviceName, Locale locale) {

        super (deviceName, locale);
    }

    // Exported operations inherited and overridden from class Object.

    /**
     * Returns whether this output device assigned attribute is equivalent to
     * the passed in object. To be equivalent, all of the following conditions
     * must be true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class OutputDeviceAssigned.
     * <LI>
     * This output device assigned attribute's underlying string and
     * <CODE>object</CODE>'s underlying string are equal.
     * <LI>
     * This output device assigned attribute's locale and
     * <CODE>object</CODE>'s locale are equal.
     * </OL>
     *
     * <p>
     *  返回此输出设备分配的属性是否等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE>对象</CODE>是OutputDeviceAssigned类的实例。
     * <LI>
     *  此输出设备分配的属性的底层字符串和<CODE>对象</CODE>的底层字符串是相等的。
     * <LI>
     *  此输出设备分配的属性的区域设置和<CODE>对象</CODE>的区域设置相等。
     * </OL>
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this output
     *          device assigned attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return (super.equals (object) &&
                object instanceof OutputDeviceAssigned);
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class OutputDeviceAssigned, the
     * category is class OutputDeviceAssigned itself.
     *
     * <p>
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return OutputDeviceAssigned.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class OutputDeviceAssigned, the
     * category name is <CODE>"output-device-assigned"</CODE>.
     *
     * <p>
     * 获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于OutputDeviceAssigned类,类别是OutputDeviceAssigned类本身。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "output-device-assigned";
    }

}
