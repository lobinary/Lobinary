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
 * Class PrinterState is a printing attribute class, an enumeration, that
 * identifies the current state of a printer. Class PrinterState defines
 * standard printer state values. A Print Service implementation only needs
 * to report those printer states which are appropriate for the particular
 * implementation; it does not have to report every defined printer state. The
 * {@link PrinterStateReasons PrinterStateReasons} attribute augments the
 * PrinterState attribute to give more detailed information about the printer
 * in  given printer state.
 * <P>
 * <B>IPP Compatibility:</B> The category name returned by
 * <CODE>getName()</CODE> is the IPP attribute name.  The enumeration's
 * integer value is the IPP enum value.  The <code>toString()</code> method
 * returns the IPP string representation of the attribute value.
 * <P>
 *
 * <p>
 *  类PrinterState是一个打印属性类,枚举,用于标识打印机的当前状态。 PrinterState类定义标准打印机状态值。
 * 打印服务实现仅需要报告适合于特定实现的那些打印机状态;它不必报告每个定义的打印机状态。
 *  {@link PrinterStateReasons PrinterStateReasons}属性可增强PrinterState属性,以在给定打印机状态下提供有关打印机的更多详细信息。
 * <P>
 *  <B> IPP兼容性：</B> <CODE> getName()</CODE>返回的类别名称是IPP属性名称。枚举的整数值是IPP枚举值。
 *  <code> toString()</code>方法返回属性值的IPP字符串表示形式。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public final class PrinterState extends EnumSyntax
implements PrintServiceAttribute {

    private static final long serialVersionUID = -649578618346507718L;

    /**
     * The printer state is unknown.
     * <p>
     *  打印机状态未知。
     * 
     */
    public static final PrinterState UNKNOWN = new PrinterState(0);

    /**
     * Indicates that new jobs can start processing without waiting.
     * <p>
     *  表示新作业可以开始处理,无需等待。
     * 
     */
    public static final PrinterState IDLE = new PrinterState(3);

    /**
     * Indicates that jobs are processing;
     * new jobs will wait before processing.
     * <p>
     *  表示作业正在处理;新作业将在处理前等待。
     * 
     */
    public static final PrinterState PROCESSING = new PrinterState(4);

    /**
     * Indicates that no jobs can be processed and intervention is required.
     * <p>
     *  表示无法处理作业,需要进行干预。
     * 
     */
    public static final PrinterState STOPPED = new PrinterState(5);

    /**
     * Construct a new printer state enumeration value with the given integer
     * value.
     *
     * <p>
     *  使用给定的整数值构造新的打印机状态枚举值。
     * 
     * 
     * @param  value  Integer value.
     */
    protected PrinterState(int value) {
        super (value);
    }

    private static final String[] myStringTable = {
        "unknown",
        null,
        null,
        "idle",
        "processing",
        "stopped"
    };

    private static final PrinterState[] myEnumValueTable = {
        UNKNOWN,
        null,
        null,
        IDLE,
        PROCESSING,
        STOPPED
    };

    /**
     * Returns the string table for class PrinterState.
     * <p>
     *  返回PrinterState类的字符串表。
     * 
     */
    protected String[] getStringTable() {
        return myStringTable;
    }

    /**
     * Returns the enumeration value table for class PrinterState.
     * <p>
     *  返回类PrinterState的枚举值表。
     * 
     */
    protected EnumSyntax[] getEnumValueTable() {
        return myEnumValueTable;
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class PrinterState, the category is class PrinterState itself.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     * 对于PrinterState类,类别是PrinterState类本身。
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return PrinterState.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class PrinterState, the category name is <CODE>"printer-state"</CODE>.
     *
     * <p>
     *  获取此属性值为实例的类别的名称。
     * <P>
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "printer-state";
    }

}
