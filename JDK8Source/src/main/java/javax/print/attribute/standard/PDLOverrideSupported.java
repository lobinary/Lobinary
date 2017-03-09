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
import javax.print.attribute.EnumSyntax;
import javax.print.attribute.PrintServiceAttribute;

/**
 * Class PDLOverrideSupported is a printing attribute class, an enumeration,
 * that expresses the printer's ability to attempt to override processing
 * instructions embedded in documents' print data with processing instructions
 * specified as attributes outside the print data.
 * <P>
 * <B>IPP Compatibility:</B> The category name returned by
 * <CODE>getName()</CODE> is the IPP attribute name.  The enumeration's
 * integer value is the IPP enum value.  The <code>toString()</code> method
 * returns the IPP string representation of the attribute value.
 * <P>
 *
 * <p>
 *  类PDLOverrideSupported是打印属性类,枚举,表示打印机试图覆盖嵌入在文档打印数据中的处理指令的能力,其中处理指令被指定为打印数据之外的属性。
 * <P>
 *  <B> IPP兼容性：</B> <CODE> getName()</CODE>返回的类别名称是IPP属性名称。枚举的整数值是IPP枚举值。
 *  <code> toString()</code>方法返回属性值的IPP字符串表示形式。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public class PDLOverrideSupported extends EnumSyntax
    implements PrintServiceAttribute {

    private static final long serialVersionUID = -4393264467928463934L;

    /**
     * The printer makes no attempt to make the external job attribute values
     * take precedence over embedded instructions in the documents' print
     * data.
     * <p>
     *  打印机不会尝试使外部作业属性值优先于文档打印数据中的嵌入指令。
     * 
     */
    public static final PDLOverrideSupported
        NOT_ATTEMPTED = new PDLOverrideSupported(0);

    /**
     * The printer attempts to make the external job attribute values take
     * precedence over embedded instructions in the documents' print data,
     * however there is no guarantee.
     * <p>
     *  打印机尝试使外部作业属性值优先于文档打印数据中的嵌入指令,但不能保证。
     * 
     */
    public static final PDLOverrideSupported
        ATTEMPTED = new PDLOverrideSupported(1);


    /**
     * Construct a new PDL override supported enumeration value with the given
     * integer value.
     *
     * <p>
     *  使用给定的整数值构造新的PDL覆盖支持的枚举值。
     * 
     * 
     * @param  value  Integer value.
     */
    protected PDLOverrideSupported(int value) {
        super (value);
    }

    private static final String[] myStringTable = {
        "not-attempted",
        "attempted"
    };

    private static final PDLOverrideSupported[] myEnumValueTable = {
        NOT_ATTEMPTED,
        ATTEMPTED
    };

    /**
     * Returns the string table for class PDLOverrideSupported.
     * <p>
     *  返回类PDLOverrideSupported的字符串表。
     * 
     */
    protected String[] getStringTable() {
        return (String[])myStringTable.clone();
    }

    /**
     * Returns the enumeration value table for class PDLOverrideSupported.
     * <p>
     *  返回类PDLOverrideSupported的枚举值表。
     * 
     */
    protected EnumSyntax[] getEnumValueTable() {
        return (EnumSyntax[])myEnumValueTable.clone();
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class PDLOverrideSupported and any vendor-defined subclasses, the
     * category is class PDLOverrideSupported itself.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于类PDLOverrideSupported和任何供应商定义的子类,类别是类PDLOverrideSupported本身。
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return PDLOverrideSupported.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class PDLOverrideSupported and any vendor-defined subclasses, the
     * category name is <CODE>"pdl-override-supported"</CODE>.
     *
     * <p>
     * 获取此属性值为实例的类别的名称。
     * <P>
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "pdl-override-supported";
    }

}
