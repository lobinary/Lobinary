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
import javax.print.attribute.IntegerSyntax;
import javax.print.attribute.PrintJobAttribute;

/**
 * Class NumberOfDocuments is an integer valued printing attribute that
 * indicates the number of individual docs the printer has accepted for this
 * job, regardless of whether the docs' print data has reached the printer or
 * not.
 * <P>
 * <B>IPP Compatibility:</B> The integer value gives the IPP integer value. The
 * category name returned by <CODE>getName()</CODE> gives the IPP attribute
 * name.
 * <P>
 *
 * <p>
 *  类NumberOfDocuments是一个整数值打印属性,表示打印机为此作业接受的单个文档的数量,无论文档的打印数据是否已到达打印机。
 * <P>
 *  <B> IPP兼容性：</B>整数值给出IPP整数值。由<CODE> getName()</CODE>返回的类别名称给出了IPP属性名称。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public final class NumberOfDocuments extends IntegerSyntax
    implements PrintJobAttribute {

    private static final long serialVersionUID = 7891881310684461097L;


    /**
     * Construct a new number of documents attribute with the given integer
     * value.
     *
     * <p>
     *  使用给定的整数值构造新的文档数量属性。
     * 
     * 
     * @param  value  Integer value.
     *
     * @exception  IllegalArgumentException
     *   (Unchecked exception) Thrown if <CODE>value</CODE> is less than 0.
     */
    public NumberOfDocuments(int value) {
        super (value, 0, Integer.MAX_VALUE);
    }

    /**
     * Returns whether this number of documents attribute is equivalent to the
     * passed in object. To be equivalent, all of the following conditions
     * must be true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class NumberOfDocuments.
     * <LI>
     * This number of documents attribute's value and <CODE>object</CODE>'s
     * value are equal.
     * </OL>
     *
     * <p>
     *  返回这个文档数量的属性是否等于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE> object </CODE>是NumberOfDocuments类的实例。
     * <LI>
     *  这个文档属性的值和<CODE>对象</CODE>的值相等。
     * </OL>
     * 
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this number of
     *          documents attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return (super.equals (object) &&
                object instanceof NumberOfDocuments);
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class NumberOfDocuments, the
     * category is class NumberOfDocuments itself.
     *
     * <p>
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return NumberOfDocuments.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class NumberOfDocuments, the
     * category name is <CODE>"number-of-documents"</CODE>.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于NumberOfDocuments类,类别是NumberOfDocuments类本身。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "number-of-documents";
    }

}
