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
import javax.print.attribute.PrintRequestAttribute;
import javax.print.attribute.PrintJobAttribute;

/**
 * Class JobMediaSheets is an integer valued printing attribute class that
 * specifies the total number of media sheets to be produced for this job.
 * <P>
 * The JobMediaSheets attribute describes the size of the job. This attribute is
 * not intended to be a counter; it is intended to be useful routing and
 * scheduling information if known. The printer may try to compute the
 * JobMediaSheets attribute's value if it is not supplied in the Print Request.
 * Even if the client does supply a value for the JobMediaSheets attribute in
 * the Print Request, the printer may choose to change the value if the printer
 * is able to compute a value which is more accurate than the client supplied
 * value. The printer may be able to determine the correct value for the
 * JobMediaSheets attribute either right at job submission time or at any later
 * point in time.
 * <P>
 * Unlike the {@link JobKOctets JobKOctets} and {@link JobImpressions
 * JobImpressions} attributes, the JobMediaSheets value must include the
 * multiplicative factors contributed by the number of copies specified by the
 * {@link Copies Copies} attribute and a "number of copies" instruction embedded
 * in the document data, if any. This difference allows the system administrator
 * to control the lower and upper bounds of both (1) the size of the document(s)
 * with {@link JobKOctetsSupported JobKOctetsSupported} and {@link
 * JobImpressionsSupported JobImpressionsSupported} and (2) the size of the job
 * with {@link JobMediaSheetsSupported JobMediaSheetsSupported}.
 * <P>
 * <B>IPP Compatibility:</B> The integer value gives the IPP integer value. The
 * category name returned by <CODE>getName()</CODE> gives the IPP attribute
 * name.
 * <P>
 *
 * <p>
 *  JobMediaSheets类是一个整数值打印属性类,它指定要为此作业生成的介质页数总数。
 * <P>
 *  JobMediaSheets属性描述作业的大小。此属性不是计数器;如果已知,则意图是有用的路由和调度信息。如果打印请求中没有提供JobMediaSheets属性的值,打印机可能会尝试计算。
 * 即使客户端为打印请求中的JobMediaSheets属性提供了值,如果打印机能够计算比客户端提供的值更精确的值,打印机可以选择更改值。
 * 打印机可以能够在作业提交时或在任何稍后的时间点确定JobMediaSheets属性的正确值。
 * <P>
 * 与{@link JobKOctets JobKOctets}和{@link JobImpressions JobImpressions}属性不同,JobMediaSheets值必须包含由{@link Copies Copies}
 * 属性指定的副本数和嵌入的"副本数"指令所贡献的乘法因子在文档数据中,如果有的话。
 * 此差异允许系统管理员控制(1)使用{@link JobKOctetsSupported JobKOctetsSupported}和{@link JobImpressionsSupported JobImpressionsSupported}
 * 的文档大小的下限和上限,以及(2)作业的大小{@link JobMediaSheetsSupported JobMediaSheetsSupported}。
 * <P>
 *  <B> IPP兼容性：</B>整数值给出IPP整数值。由<CODE> getName()</CODE>返回的类别名称给出了IPP属性名称。
 * <P>
 * 
 * 
 * @see JobMediaSheetsSupported
 * @see JobMediaSheetsCompleted
 * @see JobKOctets
 * @see JobImpressions
 *
 * @author  Alan Kaminsky
 */
public class JobMediaSheets extends IntegerSyntax
        implements PrintRequestAttribute, PrintJobAttribute {


    private static final long serialVersionUID = 408871131531979741L;

    /**
     * Construct a new job media sheets attribute with the given integer
     * value.
     *
     * <p>
     *  使用给定的整数值构造新的作业介质工作表属性。
     * 
     * 
     * @param  value  Integer value.
     *
     * @exception  IllegalArgumentException
     *   (Unchecked exception) Thrown if <CODE>value</CODE> is less than 0.
     */
    public JobMediaSheets(int value) {
        super (value, 0, Integer.MAX_VALUE);
    }

    /**
     * Returns whether this job media sheets attribute is equivalent to the
     * passed in object. To be equivalent, all of the following conditions must
     * be true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class JobMediaSheets.
     * <LI>
     * This job media sheets attribute's value and <CODE>object</CODE>'s
     * value are equal.
     * </OL>
     *
     * <p>
     *  返回此作业媒体工作表属性是否等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE>对象</CODE>是JobMediaSheets类的实例。
     * <LI>
     *  此作业介质工作表属性的值和<CODE>对象</CODE>的值相等。
     * </OL>
     * 
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this job media
     *          sheets attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return super.equals(object) && object instanceof JobMediaSheets;
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class JobMediaSheets and any vendor-defined subclasses, the category
     * is class JobMediaSheets itself.
     *
     * <p>
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return JobMediaSheets.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class JobMediaSheets and any vendor-defined subclasses, the
     * category name is <CODE>"job-media-sheets"</CODE>.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于JobMediaSheets类和任何供应商定义的子类,类别是JobMediaSheets类本身。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "job-media-sheets";
    }

}
