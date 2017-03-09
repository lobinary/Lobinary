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
import javax.print.attribute.PrintServiceAttribute;

/**
 * Class PrinterLocation is a printing attribute class, a text attribute, that
 * identifies the location of the device. This could include things like:
 * <CODE>"in Room 123A, second floor of building XYZ"</CODE>.
 * <P>
 * <B>IPP Compatibility:</B> The string value gives the IPP name value. The
 * locale gives the IPP natural language. The category name returned by
 * <CODE>getName()</CODE> gives the IPP attribute name.
 * <P>
 *
 * <p>
 *  类PrinterLocation是一个打印属性类,一个文本属性,用于标识设备的位置。这可能包括以下内容：<CODE>"在建筑物XYZ"二楼的123A室"</CODE>。
 * <P>
 *  <B> IPP兼容性：</B>字符串值给出IPP名称值。语言环境提供IPP自然语言。由<CODE> getName()</CODE>返回的类别名称给出了IPP属性名称。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public final class PrinterLocation extends TextSyntax
    implements PrintServiceAttribute {

    private static final long serialVersionUID = -1598610039865566337L;

    /**
     * Constructs a new printer location attribute with the given location and
     * locale.
     *
     * <p>
     *  构造具有给定位置和区域设置的新打印机位置属性。
     * 
     * 
     * @param  location  Printer location.
     * @param  locale    Natural language of the text string. null
     * is interpreted to mean the default locale as returned
     * by <code>Locale.getDefault()</code>
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <CODE>location</CODE> is null.
     */
    public PrinterLocation(String location, Locale locale) {
        super (location, locale);
    }

    /**
     * Returns whether this printer location attribute is equivalent to the
     * passed in object. To be equivalent, all of the following conditions
     * must be true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class PrinterLocation.
     * <LI>
     * This printer location attribute's underlying string and
     * <CODE>object</CODE>'s underlying string are equal.
     * <LI>
     * This printer location attribute's locale and <CODE>object</CODE>'s
     * locale are equal.
     * </OL>
     *
     * <p>
     *  返回此打印机位置属性是否等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE>对象</CODE>是PrinterLocation类的实例。
     * <LI>
     *  此打印机位置属性的底层字符串和<CODE>对象</CODE>的底层字符串是相等的。
     * <LI>
     *  此打印机位置属性的区域设置和<CODE>对象</CODE>的区域设置相等。
     * </OL>
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this printer
     *          location attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return (super.equals(object) && object instanceof PrinterLocation);
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class PrinterLocation, the
     * category is class PrinterLocation itself.
     *
     * <p>
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return PrinterLocation.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class PrinterLocation, the
     * category name is <CODE>"printer-location"</CODE>.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于PrinterLocation类,类别是PrinterLocation类本身。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "printer-location";
    }

}
