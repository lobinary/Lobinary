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
 * Class ColorSupported is a printing attribute class, an enumeration, that
 * identifies whether the device is capable of any type of color printing at
 * all, including highlight color as well as full process color. All document
 * instructions having to do with color are embedded within the print data (none
 * are attributes attached to the job outside the print data).
 * <P>
 * Note: End users are able to determine the nature and details of the color
 * support by querying the {@link PrinterMoreInfoManufacturer
 * PrinterMoreInfoManufacturer} attribute.
 * <P>
 * Don't confuse the ColorSupported attribute with the {@link Chromaticity
 * Chromaticity} attribute. {@link Chromaticity Chromaticity} is an attribute
 * the client can specify for a job to tell the printer whether to print a
 * document in monochrome or color, possibly causing the printer to print a
 * color document in monochrome. ColorSupported is a printer description
 * attribute that tells whether the printer can print in color regardless of how
 * the client specifies to print any particular document.
 * <P>
 * <B>IPP Compatibility:</B> The IPP boolean value is "true" for SUPPORTED and
 * "false" for NOT_SUPPORTED. The category name returned by
 * <CODE>getName()</CODE> is the IPP attribute name.  The enumeration's
 * integer value is the IPP enum value.  The <code>toString()</code> method
 * returns the IPP string representation of the attribute value.
 * <P>
 *
 * <p>
 *  ColorSupported类是一个打印属性类,枚举,用于标识设备是否能够进行任何类型的彩色打印,包括突出显示颜色以及完整的过程颜色。
 * 与打印数据有关的所有文档指令都被嵌入在打印数据中(没有是附加到打印数据外部的作业的属性)。
 * <P>
 *  注意：最终用户可以通过查询{@link PrinterMoreInfoManufacturer PrinterMoreInfoManufacturer}属性来确定颜色支持的性质和详细信息。
 * <P>
 *  不要将ColorSupported属性与{@link Chromaticity Chromaticity}属性混淆。
 *  {@link Chromaticity Chromaticity}是客户端可以为作业指定的属性,告诉打印机是以单色还是彩色打印文档,可能导致打印机以单色打印彩色文档。
 *  ColorSupported是一个打印机描述属性,它说明打印机是否可以打印,而不管客户端指定打印任何特定文档。
 * <P>
 * <B> IPP兼容性：</B> IPP布尔值对于SUPPORTED为"true",对于NOT_SUPPORTED为"false"。
 *  <CODE> getName()</CODE>返回的类别名称是IPP属性名称。枚举的整数值是IPP枚举值。 <code> toString()</code>方法返回属性值的IPP字符串表示形式。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public final class ColorSupported extends EnumSyntax
    implements PrintServiceAttribute {

    private static final long serialVersionUID = -2700555589688535545L;

    /**
     * The printer is not capable of any type of color printing.
     * <p>
     *  打印机不能进行任何类型的彩色打印。
     * 
     */
    public static final ColorSupported NOT_SUPPORTED = new ColorSupported(0);

    /**
     * The printer is capable of some type of color printing, such as
     * highlight color or full process color.
     * <p>
     *  打印机能够进行某种类型的彩色打印,例如高亮颜色或全过程颜色。
     * 
     */
    public static final ColorSupported SUPPORTED = new ColorSupported(1);

    /**
     * Construct a new color supported enumeration value with the given
     * integer value.
     *
     * <p>
     *  使用给定的整数值构造新颜色支持的枚举值。
     * 
     * 
     * @param  value  Integer value.
     */
    protected ColorSupported(int value) {
        super (value);
    }

    private static final String[] myStringTable = {"not-supported",
                                                   "supported"};

    private static final ColorSupported[] myEnumValueTable = {NOT_SUPPORTED,
                                                              SUPPORTED};

    /**
     * Returns the string table for class ColorSupported.
     * <p>
     *  返回类ColorSupported的字符串表。
     * 
     */
    protected String[] getStringTable() {
        return myStringTable;
    }

    /**
     * Returns the enumeration value table for class ColorSupported.
     * <p>
     *  返回ColorSupported类的枚举值表。
     * 
     */
    protected EnumSyntax[] getEnumValueTable() {
        return myEnumValueTable;
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class ColorSupported, the category is class ColorSupported itself.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于ColorSupported类,类别是ColorSupported类本身。
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return ColorSupported.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class ColorSupported, the category name is <CODE>"color-supported"</CODE>.
     *
     * <p>
     *  获取此属性值为实例的类别的名称。
     * <P>
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "color-supported";
    }

}
