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
import javax.print.attribute.EnumSyntax;
import javax.print.attribute.PrintRequestAttribute;
import javax.print.attribute.PrintJobAttribute;

/**
 * Class JobSheets is a printing attribute class, an enumeration, that
 * determines which job start and end sheets, if any, must be printed with a
 * job. Class JobSheets declares keywords for standard job sheets values.
 * Implementation- or site-defined names for a job sheets attribute may also be
 * created by defining a subclass of class JobSheets.
 * <P>
 * The effect of a JobSheets attribute on multidoc print jobs (jobs with
 * multiple documents) may be affected by the {@link MultipleDocumentHandling
 * MultipleDocumentHandling} job attribute, depending on the meaning of the
 * particular JobSheets value.
 * <P>
 * <B>IPP Compatibility:</B>  The category name returned by
 * <CODE>getName()</CODE> is the IPP attribute name.  The
 * enumeration's integer value is the IPP enum value.  The
 * <code>toString()</code> method returns the IPP string representation of
 * the attribute value. For a subclass, the attribute value must be
 * localized to give the IPP name and natural language values.
 * <P>
 *
 * <p>
 *  类JobSheets是一个打印属性类,枚举,用于确定必须使用作业打印哪些作业开始和结束工作表(如果有)。 JobSheets类声明标准作业表值的关键字。
 * 还可以通过定义JobSheets类的子类来创建作业表属性的实现或站点定义的名称。
 * <P>
 *  根据特定JobSheets值的含义,JobSheets属性对多点打印作业(具有多个文档的作业)的影响可能会受到{@link MultipleDocumentHandling MultipleDocumentHandling}
 * 作业属性的影响。
 * <P>
 *  <B> IPP兼容性：</B> <CODE> getName()</CODE>返回的类别名称是IPP属性名称。枚举的整数值是IPP枚举值。
 *  <code> toString()</code>方法返回属性值的IPP字符串表示形式。对于子类,属性值必须本地化以给出IPP名称和自然语言值。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public class JobSheets extends EnumSyntax
        implements PrintRequestAttribute, PrintJobAttribute {

    private static final long serialVersionUID = -4735258056132519759L;

    /**
     * No job sheets are printed.
     * <p>
     *  不打印作业页。
     * 
     */
    public static final JobSheets NONE = new JobSheets(0);

    /**
     * One or more site specific standard job sheets are printed. e.g. a
     * single start sheet is printed, or both start and end sheets are
     * printed.
     * <p>
     *  打印一个或多个特定于站点的标准作业表。例如打印单张开始纸张,或者打印开始和结束纸张。
     * 
     */
    public static final JobSheets STANDARD = new JobSheets(1);

    /**
     * Construct a new job sheets enumeration value with the given integer
     * value.
     *
     * <p>
     *  使用给定的整数值构造新的作业表枚举值。
     * 
     * 
     * @param  value  Integer value.
     */
    protected JobSheets(int value) {
        super (value);
    }

    private static final String[] myStringTable = {
        "none",
        "standard"
    };

    private static final JobSheets[] myEnumValueTable = {
        NONE,
        STANDARD
    };

    /**
     * Returns the string table for class JobSheets.
     * <p>
     *  返回JobSheets类的字符串表。
     * 
     */
    protected String[] getStringTable() {
        return (String[])myStringTable.clone();
    }

    /**
     * Returns the enumeration value table for class JobSheets.
     * <p>
     *  返回类JobSheets的枚举值表。
     * 
     */
    protected EnumSyntax[] getEnumValueTable() {
        return (EnumSyntax[])myEnumValueTable.clone();
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class JobSheets and any vendor-defined subclasses, the category is
     * class JobSheets itself.
     *
     * <p>
     * 获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于JobSheets类和任何供应商定义的子类,类别是JobSheets类本身。
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return JobSheets.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class JobSheets and any vendor-defined subclasses, the category
     * name is <CODE>"job-sheets"</CODE>.
     *
     * <p>
     *  获取此属性值为实例的类别的名称。
     * <P>
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "job-sheets";
    }

}
