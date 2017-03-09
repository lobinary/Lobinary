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
 * Class PrinterName is a printing attribute class, a text attribute, that
 * specifies the name of a printer. It is a name that is more end-user friendly
 * than a URI. An administrator determines a printer's name and sets this
 * attribute to that name. This name may be the last part of the printer's URI
 * or it may be unrelated. In non-US-English locales, a name may contain
 * characters that are not allowed in a URI.
 * <P>
 * <B>IPP Compatibility:</B> The string value gives the IPP name value. The
 * locale gives the IPP natural language. The category name returned by
 * <CODE>getName()</CODE> gives the IPP attribute name.
 * <P>
 *
 * <p>
 *  PrinterName类是一个打印属性类,一个文本属性,用于指定打印机的名称。它是一个比URI更加最终用户友好的名称。管理员确定打印机的名称,并将此属性设置为该名称。
 * 此名称可能是打印机URI的最后一部分,也可能不相关。在非美国英语区域设置中,名称可能包含URI中不允许使用的字符。
 * <P>
 *  <B> IPP兼容性：</B>字符串值给出IPP名称值。语言环境提供IPP自然语言。由<CODE> getName()</CODE>返回的类别名称给出了IPP属性名称。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public final class PrinterName extends TextSyntax
        implements PrintServiceAttribute {

    private static final long serialVersionUID = 299740639137803127L;

    /**
     * Constructs a new printer name attribute with the given name and locale.
     *
     * <p>
     *  构造具有给定名称和区域设置的新打印机名称属性。
     * 
     * 
     * @param  printerName  Printer name.
     * @param  locale       Natural language of the text string. null
     * is interpreted to mean the default locale as returned
     * by <code>Locale.getDefault()</code>
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <CODE>printerName</CODE> is null.
     */
    public PrinterName(String printerName, Locale locale) {
        super (printerName, locale);
    }

    /**
     * Returns whether this printer name attribute is equivalent to the passed
     * in object. To be equivalent, all of the following conditions must be
     * true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class PrinterName.
     * <LI>
     * This printer name attribute's underlying string and
     * <CODE>object</CODE>'s underlying string are equal.
     * <LI>
     * This printer name attribute's locale and <CODE>object</CODE>'s locale
     * are equal.
     * </OL>
     *
     * <p>
     *  返回此打印机名称属性是否等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE>对象</CODE>是PrinterName类的实例。
     * <LI>
     *  此打印机名称属性的底层字符串和<CODE>对象</CODE>的底层字符串是相等的。
     * <LI>
     *  此打印机名称属性的区域设置和<CODE>对象</CODE>的区域设置相等。
     * </OL>
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this printer
     *          name attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return (super.equals(object) && object instanceof PrinterName);
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class PrinterName, the category is
     * class PrinterName itself.
     *
     * <p>
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return PrinterName.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class PrinterName, the category
     * name is <CODE>"printer-name"</CODE>.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于PrinterName类,类别是PrinterName类本身。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "printer-name";
    }

}
