/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2006, Oracle and/or its affiliates. All rights reserved.
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
 * Class Fidelity is a printing attribute class, an enumeration,
 * that indicates whether total fidelity to client supplied print request
 * attributes is required.
 * If FIDELITY_TRUE is specified and a service cannot print the job exactly
 * as specified it must reject the job.
 * If FIDELITY_FALSE is specified a reasonable attempt to print the job is
 * acceptable. If not supplied the default is FIDELITY_FALSE.
 *
 * <P>
 * <B>IPP Compatibility:</B> The IPP boolean value is "true" for FIDELITY_TRUE
 * and "false" for FIDELITY_FALSE. The category name returned by
 * <CODE>getName()</CODE> is the IPP attribute name.  The enumeration's
 * integer value is the IPP enum value.  The <code>toString()</code> method
 * returns the IPP string representation of the attribute value.
 * See <a href="http://www.ietf.org/rfc/rfc2911.txt">RFC 2911</a> Section 15.1 for
 * a fuller description of the IPP fidelity attribute.
 * <P>
 *
 * <p>
 *  类Fidelity是打印属性类,枚举,指示是否需要对客户端提供的打印请求属性的总保真度。如果指定了FIDELITY_TRUE并且服务无法完全按照指定打印作业,则它必须拒绝作业。
 * 如果指定FIDELITY_FALSE,则可以接受合理的打印作业尝试。如果未提供,则缺省值为FIDELITY_FALSE。
 * 
 * <P>
 *  <B> IPP兼容性：</B> IPD布尔值对于FIDELITY_TRUE是"true",对于FIDELITY_FALSE是"false"。
 *  <CODE> getName()</CODE>返回的类别名称是IPP属性名称。枚举的整数值是IPP枚举值。 <code> toString()</code>方法返回属性值的IPP字符串表示形式。
 * 有关IPP保真度属性的更完整描述,请参见<a href="http://www.ietf.org/rfc/rfc2911.txt"> RFC 2911 </a>第15.1节。
 * <P>
 * 
 */
public final class Fidelity extends EnumSyntax
        implements PrintJobAttribute, PrintRequestAttribute {

    private static final long serialVersionUID = 6320827847329172308L;

    /**
     * The job must be printed exactly as specified. or else rejected.
     * <p>
     *  作业必须按照指定的完全打印。或否则拒绝。
     * 
     */
    public static final Fidelity
        FIDELITY_TRUE = new Fidelity(0);

    /**
     * The printer should make reasonable attempts to print the job,
     * even if it cannot print it exactly as specified.
     * <p>
     *  打印机应该合理尝试打印作业,即使打印机无法完全按照指定进行打印。
     * 
     */
    public static final Fidelity
        FIDELITY_FALSE = new Fidelity(1);

    /**
     * Construct a new fidelity enumeration value with the
     * given integer value.
     *
     * <p>
     *  使用给定的整数值构造新的保真度枚举值。
     * 
     * 
     * @param  value  Integer value.
     */
    protected Fidelity(int value) {
        super (value);
    }

    private static final String[] myStringTable = {
        "true",
        "false"
    };


    private static final Fidelity[] myEnumValueTable = {
        FIDELITY_TRUE,
        FIDELITY_FALSE
    };

    /**
     * Returns the string table for class Fidelity.
     * <p>
     *  返回Fidelity类的字符串表。
     * 
     */
    protected String[] getStringTable() {
        return myStringTable;
    }

    /**
     * Returns the enumeration value table for class Fidelity.
     * <p>
     *  返回Fidelity类的枚举值表。
     * 
     */
    protected EnumSyntax[] getEnumValueTable() {
        return myEnumValueTable;
    }   /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class Fidelity the category is class Fidelity itself.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     * 对于类Fidelity,类别是Fidelity类本身。
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return Fidelity.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class Fidelity the category name is
     * <CODE>"ipp-attribute-fidelity"</CODE>.
     *
     * <p>
     *  获取此属性值为实例的类别的名称。
     * <P>
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "ipp-attribute-fidelity";
    }

}
