/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2013, Oracle and/or its affiliates. All rights reserved.
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
import javax.print.attribute.PrintJobAttribute;
import javax.print.attribute.PrintRequestAttribute;

/**
 * Class PresentationDirection is a printing attribute class, an enumeration,
 * that is used in conjunction with the {@link  NumberUp NumberUp} attribute to
 * indicate the layout of multiple print-stream pages to impose upon a
 * single side of an instance of a selected medium.
 * This is useful to mirror the text layout conventions of different scripts.
 * For example, English is "toright-tobottom", Hebrew is "toleft-tobottom"
 *  and Japanese is usually "tobottom-toleft".
 * <P>
 * <B>IPP Compatibility:</B>  This attribute is not an IPP 1.1
 * attribute; it is an attribute in the Production Printing Extension
 * (<a href="ftp://ftp.pwg.org/pub/pwg/standards/pwg5100.3.pdf">PDF</a>)
 * of IPP 1.1.  The category name returned by
 * <CODE>getName()</CODE> is the IPP attribute name.  The enumeration's
 * integer value is the IPP enum value.  The <code>toString()</code> method
 * returns the IPP string representation of the attribute value.
 * <P>
 *
 * <p>
 *  类PresentationDirection是打印属性类,枚举,其与{@link NumberUp NumberUp}属性结合使用以指示要对所选介质的实例的单侧施加的多个打印流页面的布局。
 * 这对于镜像不同脚本的文本布局约定很有用。例如,英语是"toright-tobottom",希伯来语是"toleft-tobottom",日语通常是"tobottom-toftft"。
 * <P>
 *  <B> IPP兼容性：</B>此属性不是IPP 1.1属性;它是IPP 1.1的生产打印扩展程序(<a href="ftp://ftp.pwg.org/pub/pwg/standards/pwg5100.3.pdf">
 *  PDF </a>)中的属性。
 *  <CODE> getName()</CODE>返回的类别名称是IPP属性名称。枚举的整数值是IPP枚举值。 <code> toString()</code>方法返回属性值的IPP字符串表示形式。
 * <P>
 * 
 * 
 * @author  Phil Race.
 */
public final class PresentationDirection extends EnumSyntax
       implements PrintJobAttribute, PrintRequestAttribute  {

    private static final long serialVersionUID = 8294728067230931780L;

    /**
     * Pages are laid out in columns starting at the top left,
     * proceeding towards the bottom {@literal &} right.
     * <p>
     *  页面排列在从左上角开始的列中,向右移动{@literal&}。
     * 
     */
    public static final PresentationDirection TOBOTTOM_TORIGHT =
        new PresentationDirection(0);

    /**
     * Pages are laid out in columns starting at the top right,
     * proceeding towards the bottom {@literal &} left.
     * <p>
     *  页面排列在从右上角开始的列中,向左移动{@literal&}。
     * 
     */
    public static final PresentationDirection TOBOTTOM_TOLEFT =
        new PresentationDirection(1);

    /**
     * Pages are laid out in columns starting at the bottom left,
     * proceeding towards the top {@literal &} right.
     * <p>
     *  页面排列在从左下方开始的列中,向右移动到顶部{@literal&}。
     * 
     */
    public static final PresentationDirection TOTOP_TORIGHT =
        new PresentationDirection(2);

    /**
     * Pages are laid out in columns starting at the bottom right,
     * proceeding towards the top {@literal &} left.
     * <p>
     *  页面排列在从右下角开始的列中,向上{@literal&}向左。
     * 
     */
    public static final PresentationDirection TOTOP_TOLEFT =
        new PresentationDirection(3);

    /**
     * Pages are laid out in rows starting at the top left,
     * proceeding towards the right {@literal &} bottom.
     * <p>
     * 页面从左上方开始按行排列,向右移动{@literal&} bottom。
     * 
     */
    public static final PresentationDirection TORIGHT_TOBOTTOM =
        new PresentationDirection(4);

    /**
     * Pages are laid out in rows starting at the bottom left,
     * proceeding towards the right {@literal &} top.
     * <p>
     *  页面从左下方开始按行排列,向右移动{@literal&} top。
     * 
     */
    public static final PresentationDirection TORIGHT_TOTOP =
        new PresentationDirection(5);

    /**
     * Pages are laid out in rows starting at the top right,
     * proceeding towards the left {@literal &} bottom.
     * <p>
     *  页面排列在从右上角开始的行中,向左移动{@literal&} bottom。
     * 
     */
    public static final PresentationDirection TOLEFT_TOBOTTOM =
        new PresentationDirection(6);

    /**
     * Pages are laid out in rows starting at the bottom right,
     * proceeding towards the left {@literal &} top.
     * <p>
     *  页面排列在从右下角开始的行中,向左移动{@literal&} top。
     * 
     */
    public static final PresentationDirection TOLEFT_TOTOP =
        new PresentationDirection(7);

    /**
     * Construct a new presentation direction enumeration value with the given
     * integer value.
     *
     * <p>
     *  使用给定的整数值构造新的显示方向枚举值。
     * 
     * 
     * @param  value  Integer value.
     */
    private PresentationDirection(int value) {
        super (value);
    }

    private static final String[] myStringTable = {
        "tobottom-toright",
        "tobottom-toleft",
        "totop-toright",
        "totop-toleft",
        "toright-tobottom",
        "toright-totop",
        "toleft-tobottom",
        "toleft-totop",
    };

    private static final PresentationDirection[] myEnumValueTable = {
        TOBOTTOM_TORIGHT,
        TOBOTTOM_TOLEFT,
        TOTOP_TORIGHT,
        TOTOP_TOLEFT,
        TORIGHT_TOBOTTOM,
        TORIGHT_TOTOP,
        TOLEFT_TOBOTTOM,
        TOLEFT_TOTOP,
    };

    /**
     * Returns the string table for class PresentationDirection.
     * <p>
     *  返回类PresentationDirection的字符串表。
     * 
     */
    protected String[] getStringTable() {
        return myStringTable;
    }

    /**
     * Returns the enumeration value table for class PresentationDirection.
     * <p>
     *  返回类PresentationDirection的枚举值表。
     * 
     */
    protected EnumSyntax[] getEnumValueTable() {
        return myEnumValueTable;
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class PresentationDirection
     * the category is class PresentationDirection itself.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于类PresentationDirection,类别是类PresentationDirection本身。
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return PresentationDirection.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class PresentationDirection
     * the category name is <CODE>"presentation-direction"</CODE>.
     *
     * <p>
     *  获取此属性值为实例的类别的名称。
     * <P>
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "presentation-direction";
    }

}
