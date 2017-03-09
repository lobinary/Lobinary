/***** Lobxxx Translate Finished ******/

/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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
import javax.print.attribute.DocAttribute;
import javax.print.attribute.PrintRequestAttribute;
import javax.print.attribute.PrintJobAttribute;

/**
 * Class Chromaticity is a printing attribute class, an enumeration, that
 * specifies monochrome or color printing. This is used by a print client
 * to specify how the print data should be generated or processed. It is not
 * descriptive of the color capabilities of the device. Query the service's
 * {@link ColorSupported ColorSupported} attribute to determine if the device
 * can be verified to support color printing.
 * <P>
 * The table below shows the effects of specifying a Chromaticity attribute of
 * {@link #MONOCHROME MONOCHROME} or {@link #COLOR COLOR}
 * for a monochrome or color document.
 * <P>
 * <TABLE BORDER=1 CELLPADDING=2 CELLSPACING=1 SUMMARY="Shows effects of specifying MONOCHROME or COLOR Chromaticity attributes">
 * <TR>
 * <TH>
 * Chromaticity<BR>Attribute
 * </TH>
 * <TH>
 * Effect on<BR>Monochrome Document
 * </TH>
 * <TH>
 * Effect on<BR>Color Document
 * </TH>
 * </TR>
 * <TR>
 * <TD>
 * {@link #MONOCHROME MONOCHROME}
 * </TD>
 * <TD>
 * Printed as is, in monochrome
 * </TD>
 * <TD>
 * Printed in monochrome, with colors converted to shades of gray
 * </TD>
 * </TR>
 * <TR>
 * <TD>
 * {@link #COLOR COLOR}
 * </TD>
 * <TD>
 * Printed as is, in monochrome
 * </TD>
 * <TD>
 * Printed as is, in color
 * </TD>
 * </TR>
 * </TABLE>
 * <P>
 * <P>
 * <B>IPP Compatibility:</B> Chromaticity is not an IPP attribute at present.
 * <P>
 *
 * <p>
 *  类别色度是指定单色或彩色打印的打印属性类,枚举。这由打印客户端用于指定如何生成或处理打印数据。它不描述设备的颜色能力。
 * 查询服务的{@link ColorSupported ColorSupported}属性,以确定是否可以验证设备是否支持彩色打印。
 * <P>
 *  下表显示了为单色或彩色文档指定{@link #MONOCHROME MONOCHROME}或{@link #COLOR COLOR}的色度属性的效果。
 * <P>
 * <TABLE BORDER=1 CELLPADDING=2 CELLSPACING=1 SUMMARY="Shows effects of specifying MONOCHROME or COLOR Chromaticity attributes">
 * <TR>
 * <TH>
 *  色度<BR>属性
 * </TH>
 * <TH>
 *  对<BR>单色文档的影响
 * </TH>
 * <TH>
 *  对<BR>颜色文档的影响
 * </TH>
 * </TR>
 * <TR>
 * <TD>
 *  {@link #MONOCHROME MONOCHROME}
 * </TD>
 * <TD>
 *  以单色打印
 * </TD>
 * <TD>
 *  以单色打印,用颜色转换为灰色阴影
 * </TD>
 * </TR>
 * <TR>
 * <TD>
 *  {@link #COLOR COLOR}
 * </TD>
 * <TD>
 *  以单色打印
 * </TD>
 * 
 * @author  Alan Kaminsky
 */
public final class Chromaticity extends EnumSyntax
    implements DocAttribute, PrintRequestAttribute, PrintJobAttribute {

    private static final long serialVersionUID = 4660543931355214012L;

    /**
     * Monochrome printing.
     * <p>
     * <TD>
     *  按原样打印,颜色
     * </TD>
     * </TR>
     * </TABLE>
     * <P>
     * <P>
     *  <B> IPP兼容性：</B>当前,色度不是IPP属性。
     * <P>
     * 
     */
    public static final Chromaticity MONOCHROME = new Chromaticity(0);

    /**
     * Color printing.
     * <p>
     *  单色打印。
     * 
     */
    public static final Chromaticity COLOR = new Chromaticity(1);


    /**
     * Construct a new chromaticity enumeration value with the given integer
     * value.
     *
     * <p>
     *  彩色印刷。
     * 
     * 
     * @param  value  Integer value.
     */
    protected Chromaticity(int value) {
        super(value);
    }

    private static final String[] myStringTable = {"monochrome",
                                                   "color"};

    private static final Chromaticity[] myEnumValueTable = {MONOCHROME,
                                                            COLOR};

    /**
     * Returns the string table for class Chromaticity.
     * <p>
     *  用给定的整数值构造新的色度枚举值。
     * 
     */
    protected String[] getStringTable() {
        return myStringTable;
    }

    /**
     * Returns the enumeration value table for class Chromaticity.
     * <p>
     *  返回类Chromaticity的字符串表。
     * 
     */
    protected EnumSyntax[] getEnumValueTable() {
        return myEnumValueTable;
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class Chromaticity, the category is the class Chromaticity itself.
     *
     * <p>
     *  返回类Chromaticity的枚举值表。
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return Chromaticity.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class Chromaticity, the category name is <CODE>"chromaticity"</CODE>.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于类别色度,类别是类别色度本身。
     * 
     * 
     * @return  Attribute category name.
     */
        public final String getName() {
            return "chromaticity";
        }

        }
