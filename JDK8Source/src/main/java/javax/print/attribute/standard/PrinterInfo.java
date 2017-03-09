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
 * Class PrinterInfo is a printing attribute class, a text attribute, that
 * provides descriptive information about a printer. This could include things
 * like: <CODE>"This printer can be used for printing color transparencies for
 * HR presentations"</CODE>, or <CODE>"Out of courtesy for others, please
 * print only small (1-5 page) jobs at this printer"</CODE>, or even \
 * <CODE>"This printer is going away on July 1, 1997, please find a new
 * printer"</CODE>.
 * <P>
 * <B>IPP Compatibility:</B> The string value gives the IPP name value. The
 * locale gives the IPP natural language. The category name returned by
 * <CODE>getName()</CODE> gives the IPP attribute name.
 * <P>
 *
 * <p>
 *  类PrinterInfo是一个打印属性类,一个文本属性,提供关于打印机的描述性信息。
 * 这可能包括以下内容：<CODE>"此打印机可用于打印彩色透明胶片用于HR演示"</CODE>或<CODE>"出于对其他人的礼貌,请仅打印小在此打印机"</CODE>,或甚至\ <CODE>"此打印机将于
 * 1997年7月1日消失,请找到一台新打印机"</CODE>。
 *  类PrinterInfo是一个打印属性类,一个文本属性,提供关于打印机的描述性信息。
 * <P>
 *  <B> IPP兼容性：</B>字符串值给出IPP名称值。语言环境提供IPP自然语言。由<CODE> getName()</CODE>返回的类别名称给出了IPP属性名称。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public final class PrinterInfo extends TextSyntax
        implements PrintServiceAttribute {

    private static final long serialVersionUID = 7765280618777599727L;

    /**
     * Constructs a new printer info attribute with the given information
     * string and locale.
     *
     * <p>
     *  构造具有给定信息字符串和区域设置的新打印机信息属性。
     * 
     * 
     * @param  info    Printer information string.
     * @param  locale  Natural language of the text string. null
     * is interpreted to mean the default locale as returned
     * by <code>Locale.getDefault()</code>
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <CODE>info</CODE> is null.
     */
    public PrinterInfo(String info, Locale locale) {
        super (info, locale);
    }

    /**
     * Returns whether this printer info attribute is equivalent to the passed
     * in object. To be equivalent, all of the following conditions must be
     * true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class PrinterInfo.
     * <LI>
     * This printer info attribute's underlying string and
     * <CODE>object</CODE>'s underlying string are equal.
     * <LI>
     * This printer info attribute's locale and <CODE>object</CODE>'s
     * locale are equal.
     * </OL>
     *
     * <p>
     *  返回此打印机info属性是否等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE>对象</CODE>是PrinterInfo类的实例。
     * <LI>
     *  此打印机info属性的底层字符串和<CODE>对象</CODE>的底层字符串是相等的。
     * <LI>
     *  此打印机信息属性的区域设置和<CODE>对象</CODE>的区域设置相等。
     * </OL>
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this printer
     *          info attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return (super.equals(object) && object instanceof PrinterInfo);
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class PrinterInfo, the category is class PrinterInfo itself.
     *
     * <p>
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return PrinterInfo.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class PrinterInfo, the category name is <CODE>"printer-info"</CODE>.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于PrinterInfo类,类别为PrinterInfo类本身。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "printer-info";
    }

}
