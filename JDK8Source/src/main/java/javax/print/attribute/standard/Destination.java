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
import javax.print.attribute.PrintRequestAttribute;
import javax.print.attribute.PrintJobAttribute;

/**
 * Class Destination is a printing attribute class, a URI, that is used to
 * indicate an alternate destination for the spooled printer formatted
 * data. Many PrintServices will not support the notion of a destination
 * other than the printer device, and so will not support this attribute.
 * <p>
 * A common use for this attribute will be applications which want
 * to redirect output to a local disk file : eg."file:out.prn".
 * Note that proper construction of "file:" scheme URI instances should
 * be performed using the <code>toURI()</code> method of class
 * {@link java.io.File File}.
 * See the documentation on that class for more information.
 * <p>
 * If a destination URI is specified in a PrintRequest and it is not
 * accessible for output by the PrintService, a PrintException will be thrown.
 * The PrintException may implement URIException to provide a more specific
 * cause.
 * <P>
 * <B>IPP Compatibility:</B> Destination is not an IPP attribute.
 * <P>
 *
 * <p>
 *  类目标是打印属性类,URI,用于指示假脱机打印机格式化数据的备用目标。许多PrintServices将不支持除打印机设备之外的目标的概念,因此不支持此属性。
 * <p>
 *  此属性的常见用途是将输出重定向到本地磁盘文件的应用程序：例如"file：out.prn"。
 * 注意,应该使用类{@link java.io.File File}的<code> toURI()</code>方法来正确构造"file："方案URI实例。有关更多信息,请参阅该类的文档。
 * <p>
 *  如果在PrintRequest中指定了目标URI,并且PrintService无法访问该目标URI,则会抛出PrintException。
 *  PrintException可能实现URIException以提供更具体的原因。
 * <P>
 *  <B> IPP兼容性：</B>目标不是IPP属性。
 * <P>
 * 
 * 
 * @author  Phil Race.
 */
public final class Destination extends URISyntax
        implements PrintJobAttribute, PrintRequestAttribute {

    private static final long serialVersionUID = 6776739171700415321L;

    /**
     * Constructs a new destination attribute with the specified URI.
     *
     * <p>
     *  使用指定的URI构造新的目标属性。
     * 
     * 
     * @param  uri  URI.
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <CODE>uri</CODE> is null.
     */
    public Destination(URI uri) {
        super (uri);
    }

    /**
     * Returns whether this destination attribute is equivalent to the
     * passed in object. To be equivalent, all of the following conditions
     * must be true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class Destination.
     * <LI>
     * This destination attribute's URI and <CODE>object</CODE>'s URI
     * are equal.
     * </OL>
     *
     * <p>
     *  返回此目标属性是否等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE>对象</CODE>是类Destination的一个实例。
     * <LI>
     *  此目标属性的URI和<CODE>对象</CODE>的URI相等。
     * </OL>
     * 
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this destination
     *         attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return (super.equals(object) &&
                object instanceof Destination);
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class Destination, the category is class Destination itself.
     *
     * <p>
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return Destination.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class Destination, the category name is <CODE>"spool-data-destination"</CODE>.
     *
     * <p>
     * 获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于类Destination,类别是类Destination本身。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "spool-data-destination";
    }

}
