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
 * Class JobImpressions is an integer valued printing attribute class that
 * specifies the total size in number of impressions of the document(s) being
 * submitted. An "impression" is the image (possibly many print-stream pages in
 * different configurations) imposed onto a single media page.
 * <P>
 * The JobImpressions attribute describes the size of the job. This attribute is
 * not intended to be a counter; it is intended to be useful routing and
 * scheduling information if known. The printer may try to compute the
 * JobImpressions attribute's value if it is not supplied in the Print Request.
 * Even if the client does supply a value for the JobImpressions attribute in
 * the Print Request, the printer may choose to change the value if the printer
 * is able to compute a value which is more accurate than the client supplied
 * value. The printer may be able to determine the correct value for the
 * JobImpressions attribute either right at job submission time or at any later
 * point in time.
 * <P>
 * As with {@link JobKOctets JobKOctets}, the JobImpressions value must not
 * include the multiplicative factors contributed by the number of copies
 * specified by the {@link Copies Copies} attribute, independent of whether the
 * device can process multiple copies without making multiple passes over the
 * job or document data and independent of whether the output is collated or
 * not. Thus the value is independent of the implementation and reflects the
 * size of the document(s) measured in impressions independent of the number of
 * copies.
 * <P>
 * As with {@link JobKOctets JobKOctets}, the JobImpressions value must also not
 * include the multiplicative factor due to a copies instruction embedded in the
 * document data. If the document data actually includes replications of the
 * document data, this value will include such replication. In other words, this
 * value is always the number of impressions in the source document data, rather
 * than a measure of the number of impressions to be produced by the job.
 * <P>
 * <B>IPP Compatibility:</B> The integer value gives the IPP integer value. The
 * category name returned by <CODE>getName()</CODE> gives the IPP attribute
 * name.
 * <P>
 *
 * <p>
 *  JobImpressions类是一个整数值打印属性类,用于指定要提交的文档的总展示次数。 "印象"是施加到单个媒体页面上的图像(可能是许多不同配置的打印流页面)。
 * <P>
 *  JobImpressions属性描述作业的大小。此属性不是计数器;如果已知,则意图是有用的路由和调度信息。如果打印请求中未提供JobImpressions属性的值,打印机可能会尝试计算该值。
 * 即使客户端为打印请求中的JobImpressions属性提供了值,如果打印机能够计算比客户端提供的值更精确的值,打印机可以选择更改值。
 * 打印机可以能够在作业提交时或在任何稍后的时间点确定JobImpressions属性的正确值。
 * <P>
 * 与{@link JobKOctets JobKOctets}一样,JobImpressions值不能包括由{@link Copies Copies}属性指定的副本数量所贡献的乘法因子,而与设备是否可以处
 * 理多个副本而不进行多次通过作业或文档数据,并且与是否整理输出无关。
 * 因此,该值与实现无关,并且反映了以印记测量的文档的大小,而与复制数量无关。
 * <P>
 *  与{@link JobKOctets JobKOctets}一样,由于嵌入在文档数据中的副本指令,JobImpressions值也必须不包括乘法因子。
 * 如果文档数据实际上包括文档数据的复制,则该值将包括这样的复制。换句话说,此值始终是源文档数据中的展示次数,而不是作业要产生的展示次数的度量。
 * <P>
 *  <B> IPP兼容性：</B>整数值给出IPP整数值。由<CODE> getName()</CODE>返回的类别名称给出了IPP属性名称。
 * <P>
 * 
 * 
 * @see JobImpressionsSupported
 * @see JobImpressionsCompleted
 * @see JobKOctets
 * @see JobMediaSheets
 *
 * @author  Alan Kaminsky
 */
public final class JobImpressions extends IntegerSyntax
    implements PrintRequestAttribute, PrintJobAttribute {

    private static final long serialVersionUID = 8225537206784322464L;


    /**
     * Construct a new job impressions attribute with the given integer value.
     *
     * <p>
     *  使用给定的整数值构造新的作业展示次数属性。
     * 
     * 
     * @param  value  Integer value.
     *
     * @exception  IllegalArgumentException
     *  (Unchecked exception) Thrown if <CODE>value</CODE> is less than 0.
     */
    public JobImpressions(int value) {
        super(value, 0, Integer.MAX_VALUE);
    }

    /**
     * Returns whether this job impressions attribute is equivalent to the
     * passed in object. To be equivalent, all of the following conditions must
     * be true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class JobImpressions.
     * <LI>
     * This job impressions attribute's value and <CODE>object</CODE>'s value
     * are equal.
     * </OL>
     *
     * <p>
     *  返回此作业展示次数属性是否等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE>对象</CODE>是JobImpressions类的实例。
     * <LI>
     * 此作业展示次数属性的值和<CODE>对象</CODE>的值相等。
     * </OL>
     * 
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this job
     *          impressions attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return super.equals (object) && object instanceof JobImpressions;
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class JobImpressions, the category is class JobImpressions itself.
     *
     * <p>
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return JobImpressions.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class JobImpressions, the category name is
     * <CODE>"job-impressions"</CODE>.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于JobImpressions类,类别是JobImpressions类本身。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "job-impressions";
    }

}
