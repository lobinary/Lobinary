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

import javax.print.attribute.EnumSyntax;
import javax.print.attribute.Attribute;

/**
 * Class Severity is a printing attribute class, an enumeration, that denotes
 * the severity of a {@link PrinterStateReason PrinterStateReason} attribute.
 * <P>
 * Instances of Severity do not appear in a Print Service's attribute set
 * directly. Rather, a {@link PrinterStateReasons PrinterStateReasons}
 * attribute appears in the Print Service's attribute set.
 *  The {@link PrinterStateReasons
 * PrinterStateReasons} attribute contains zero, one, or more than one {@link
 * PrinterStateReason PrinterStateReason} objects which pertain to the Print
 * Service's status, and each {@link PrinterStateReason PrinterStateReason}
 * object is associated with a Severity level of REPORT (least severe),
 * WARNING, or ERROR (most severe).
 * The printer adds a {@link PrinterStateReason
 * PrinterStateReason} object to the Print Service's
 * {@link PrinterStateReasons PrinterStateReasons} attribute when the
 * corresponding condition becomes true
 * of the printer, and the printer removes the {@link PrinterStateReason
 * PrinterStateReason} object again when the corresponding condition becomes
 * false, regardless of whether the Print Service's overall
 * {@link PrinterState PrinterState} also changed.
 * <P>
 * <B>IPP Compatibility:</B>
 * <code>Severity.toString()</code> returns either "error", "warning", or
 * "report".  The string values returned by
 * each individual {@link PrinterStateReason} and
 * associated {@link Severity} object's <CODE>toString()</CODE>
 * methods, concatenated together with a hyphen (<CODE>"-"</CODE>) in
 * between, gives the IPP keyword value for a {@link PrinterStateReasons}.
 * The category name returned by <CODE>getName()</CODE> gives the IPP
 * attribute name.
 * <P>
 *
 * <p>
 *  类严重性是一个打印属性类,枚举,表示{@link PrinterStateReason PrinterStateReason}属性的严重性。
 * <P>
 *  严重性实例不会直接显示在打印服务的属性集中。相反,{@link PrinterStateReasons PrinterStateReasons}属性显示在打印服务的属性集中。
 *  {@link PrinterStateReasons PrinterStateReasons}属性包含与打印服务状态相关的零个,一个或多个{@link PrinterStateReason PrinterStateReason}
 * 对象,每个{@link PrinterStateReason PrinterStateReason}对象与严重性级别为REPORT(最不严重),WARNING或ERROR(最严重)。
 *  严重性实例不会直接显示在打印服务的属性集中。相反,{@link PrinterStateReasons PrinterStateReasons}属性显示在打印服务的属性集中。
 * 当相应条件成为打印机的相应条件时,打印机向打印服务的{@link PrinterStateReasons PrinterStateReasons}属性添加{@link PrinterStateReason PrinterStateReason}
 * 对象,并且当相应条件成为时,打印机再次删除{@link PrinterStateReason PrinterStateReason}对象false,而不管打印服务的整体{@link PrinterState PrinterState}
 * 是否也更改。
 *  严重性实例不会直接显示在打印服务的属性集中。相反,{@link PrinterStateReasons PrinterStateReasons}属性显示在打印服务的属性集中。
 * <P>
 * <B> IPP兼容性：</B> <code> Severity.toString()</code>返回"error","warning"或"report"。
 * 由每个{@link PrinterStateReason}和相关联的{@link Severity}对象的<CODE> toString()</CODE>方法返回的字符串值与连字符(<CODE>" - 
 * "</CODE> between,给出{@link PrinterStateReasons}的IPP关键字值。
 * <B> IPP兼容性：</B> <code> Severity.toString()</code>返回"error","warning"或"report"。
 * 由<CODE> getName()</CODE>返回的类别名称给出了IPP属性名称。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public final class Severity extends EnumSyntax implements Attribute {

    private static final long serialVersionUID = 8781881462717925380L;

    /**
     * Indicates that the {@link PrinterStateReason PrinterStateReason} is a
     * "report" (least severe). An implementation may choose to omit some or
     * all reports.
     * Some reports specify finer granularity about the printer state;
     * others serve as a precursor to a warning. A report must contain nothing
     * that could affect the printed output.
     * <p>
     *  表示{@link PrinterStateReason PrinterStateReason}是一个"报告"(最不严重)。实现可以选择省略一些或所有报告。
     * 一些报告指定了关于打印机状态的更精细的粒度;其他人则作为警告的先兆。报告不能包含任何可能影响打印输出的内容。
     * 
     */
    public static final Severity REPORT = new Severity (0);

    /**
     * Indicates that the {@link PrinterStateReason PrinterStateReason} is a
     * "warning." An implementation may choose to omit some or all warnings.
     * Warnings serve as a precursor to an error. A warning must contain
     * nothing  that prevents a job from completing, though in some cases the
     * output may be of lower quality.
     * <p>
     *  表示{@link PrinterStateReason PrinterStateReason}是一个"警告"。实现可以选择省略一些或所有警告。警告作为错误的前兆。
     * 警告必须不包含任何阻止作业完成的内容,但在某些情况下,输出可能质量较低。
     * 
     */
    public static final Severity WARNING = new Severity (1);

    /**
     * Indicates that the {@link PrinterStateReason PrinterStateReason} is an
     * "error" (most severe). An implementation must include all errors.
     * If this attribute contains one or more errors, the printer's
     * {@link PrinterState PrinterState} must be STOPPED.
     * <p>
     *  表示{@link PrinterStateReason PrinterStateReason}是一个"错误"(最严重)。实施必须包括所有错误。
     * 如果此属性包含一个或多个错误,则打印机的{@link PrinterState PrinterState}必须为STOPPED。
     * 
     */
    public static final Severity ERROR = new Severity (2);

    /**
     * Construct a new severity enumeration value with the given integer
     * value.
     *
     * <p>
     *  使用给定的整数值构造新的严重性枚举值。
     * 
     * 
     * @param  value  Integer value.
     */
    protected Severity(int value) {
        super (value);
    }

    private static final String[] myStringTable = {
        "report",
        "warning",
        "error"
    };

    private static final Severity[] myEnumValueTable = {
        REPORT,
        WARNING,
        ERROR
    };

    /**
     * Returns the string table for class Severity.
     * <p>
     *  返回类Severity的字符串表。
     * 
     */
    protected String[] getStringTable() {
        return myStringTable;
    }

    /**
     * Returns the enumeration value table for class Severity.
     * <p>
     * 返回类Severity的枚举值表。
     * 
     */
    protected EnumSyntax[] getEnumValueTable() {
        return myEnumValueTable;
    }


    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class Severity, the category is class Severity itself.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于类Severity,类别是Severity类本身。
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return Severity.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class Severit, the category name is <CODE>"severity"</CODE>.
     *
     * <p>
     *  获取此属性值为实例的类别的名称。
     * <P>
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "severity";
    }

}
