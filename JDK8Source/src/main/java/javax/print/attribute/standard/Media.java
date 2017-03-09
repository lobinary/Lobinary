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

import javax.print.attribute.Attribute;
import javax.print.attribute.DocAttribute;
import javax.print.attribute.EnumSyntax;
import javax.print.attribute.PrintRequestAttribute;
import javax.print.attribute.PrintJobAttribute;

/**
 * Class Media is a printing attribute class that specifies the
 * medium on which to print.
 * <p>
 * Media may be specified in different ways.
 * <ul>
 * <li> it may be specified by paper source - eg paper tray
 * <li> it may be specified by a standard size - eg "A4"
 * <li> it may be specified by a name - eg "letterhead"
 * </ul>
 * Each of these corresponds to the IPP "media" attribute.
 * The current API does not support describing media by characteristics
 * (eg colour, opacity).
 * This may be supported in a later revision of the specification.
 * <p>
 * A Media object is constructed with a value which represents
 * one of the ways in which the Media attribute can be specified.
 * <p>
 * <B>IPP Compatibility:</B>  The category name returned by
 * <CODE>getName()</CODE> is the IPP attribute name.  The enumeration's
 * integer value is the IPP enum value.  The <code>toString()</code> method
 * returns the IPP string representation of the attribute value.
 * <P>
 *
 * <p>
 *  类Media是指定要打印的介质的打印属性类。
 * <p>
 *  可以以不同的方式指定媒体。
 * <ul>
 *  <li>它可以由纸张来源指定 - 例如纸盒<li>,它可以由标准尺寸指定 - 例如"A4"<li>可以通过名称指定 - 例如"letterhead"
 * </ul>
 *  其中每个对应于IPP"媒体"属性。当前API不支持按特征描述媒体(例如颜色,不透明度)。这可以在本说明书的稍后修订中得到支持。
 * <p>
 *  媒体对象使用表示媒体属性可以被指定的方式之一的值来构造。
 * <p>
 *  <B> IPP兼容性：</B> <CODE> getName()</CODE>返回的类别名称是IPP属性名称。枚举的整数值是IPP枚举值。
 *  <code> toString()</code>方法返回属性值的IPP字符串表示形式。
 * <P>
 * 
 * 
 * @author Phil Race
 */
public abstract class Media extends EnumSyntax
    implements DocAttribute, PrintRequestAttribute, PrintJobAttribute {

    private static final long serialVersionUID = -2823970704630722439L;

    /**
     * Constructs a new media attribute specified by name.
     *
     * <p>
     *  构造由名称指定的新媒体属性。
     * 
     * 
     * @param value         a value
     */
    protected Media(int value) {
           super (value);
    }

    /**
     * Returns whether this media attribute is equivalent to the passed in
     * object. To be equivalent, all of the following conditions must be true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is of the same subclass of Media as this object.
     * <LI>
     * The values are equal.
     * </OL>
     *
     * <p>
     *  返回此媒体属性是否等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE>对象</CODE>与此对象属于Media的同一子类。
     * <LI>
     *  值相等。
     * </OL>
     * 
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this media
     *          attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return(object != null && object instanceof Media &&
               object.getClass() == this.getClass() &&
               ((Media)object).getValue() == this.getValue());
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class Media and any vendor-defined subclasses, the category is
     * class Media itself.
     *
     * <p>
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return Media.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class Media and any vendor-defined subclasses, the category name is
     * <CODE>"media"</CODE>.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     * 对于类Media和任何供应商定义的子类,类别是类Media本身。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "media";
    }

}
