/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2004, Oracle and/or its affiliates. All rights reserved.
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
import java.util.Locale;

import javax.print.attribute.Attribute;
import javax.print.attribute.URISyntax;
import javax.print.attribute.PrintServiceAttribute;

/**
 * Class PrinterURI is a printing attribute class, a URI, that specifies the
 * globally unique name of a printer.  If it has such a name, an administrator
 * determines a printer's URI and sets this attribute to that name.
 * <P>
 * <B>IPP Compatibility:</B>  This implements the
 * IPP printer-uri attribute. The string form returned by
 * <CODE>toString()</CODE>  gives the IPP printer-uri value.
 * The category name returned by <CODE>getName()</CODE>
 * gives the IPP attribute name.
 * <P>
 *
 * <p>
 *  类PrinterURI是一个打印属性类,一个URI,它指定打印机的全局唯一名称。如果它有这样的名称,管理员确定打印机的URI并将此属性设置为该名称。
 * <P>
 *  <B> IPP兼容性：</B>这实现IPP printer-uri属性。 <CODE> toString()</CODE>返回的字符串形式给出了IPP printer-uri值。
 * 由<CODE> getName()</CODE>返回的类别名称给出了IPP属性名称。
 * <P>
 * 
 * 
 * @author  Robert Herriot
 */

public final class PrinterURI extends URISyntax
        implements PrintServiceAttribute {

    private static final long serialVersionUID = 7923912792485606497L;

    /**
     * Constructs a new PrinterURI attribute with the specified URI.
     *
     * <p>
     *  构造具有指定URI的新PrinterURI属性。
     * 
     * 
     * @param  uri  URI of the printer
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <CODE>uri</CODE> is null.
     */
    public PrinterURI(URI uri) {
        super (uri);
    }

    /**
     * Returns whether this printer name attribute is equivalent to the passed
     * in object. To be equivalent, all of the following conditions must be
     * true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class PrinterURI.
     * <LI>
     * This PrinterURI attribute's underlying URI and
     * <CODE>object</CODE>'s underlying URI are equal.
     * </OL>
     *
     * <p>
     *  返回此打印机名称属性是否等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE>对象</CODE>是PrinterURI类的实例。
     * <LI>
     *  这个PrinterURI属性的底层URI和<CODE>对象</CODE>的底层URI是相等的。
     * </OL>
     * 
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this PrinterURI
     *          attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return (super.equals(object) && object instanceof PrinterURI);
    }

   /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class PrinterURI and any vendor-defined subclasses, the category is
     * class PrinterURI itself.
     *
     * <p>
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return PrinterURI.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class PrinterURI and any vendor-defined subclasses, the category
     * name is <CODE>"printer-uri"</CODE>.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于PrinterURI类和任何供应商定义的子类,类别是PrinterURI类本身。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "printer-uri";
    }

}
