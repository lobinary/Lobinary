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
 * Class JobPriority is an integer valued printing attribute class that
 * specifies a print job's priority.
 * <P>
 * If a JobPriority attribute is specified for a Print Job, it specifies a
 * priority for scheduling the job. A higher value specifies a higher priority.
 * The value 1 indicates the lowest possible priority. The value 100 indicates
 * the highest possible priority. Among those jobs that are ready to print, a
 * printer must print all jobs with a priority value of <I>n</I> before printing
 * those with a priority value of <I>n</I>-1 for all <I>n.</I>
 * <P>
 * If the client does not specify a JobPriority attribute for a Print Job and
 * the printer does support the JobPriority attribute, the printer must use an
 * implementation-defined default JobPriority value.
 * <P>
 * The client can always specify any job priority value from 1 to 100 for a job.
 * However, a Print Service instance may support fewer than 100 different
 * job priority levels. If this is the case, the Print Service instance
 * automatically maps the client-specified job priority value to one of the
 * supported job priority levels, dividing the 100 job priority values equally
 * among the available job priority levels.
 * <P>
 * <B>IPP Compatibility:</B> The integer value gives the IPP integer value. The
 * category name returned by <CODE>getName()</CODE> gives the IPP attribute
 * name.
 * <P>
 *
 * <p>
 *  JobPriority类是一个整数值打印属性类,用于指定打印作业的优先级。
 * <P>
 *  如果为打印作业指定了JobPriority属性,则它指定调度作业的优先级。较高的值指定较高的优先级。值1表示最低可能的优先级。值100表示​​最高可能的优先级。
 * 在准备打印的那些作业中,打印机必须在打印具有优先级值<I> n </I> -1的所有<I> n </I>之前打印具有优先级值<I> n的所有作业> n。</I>。
 * <P>
 *  如果客户机没有为打印作业指定JobPriority属性,并且打印机支持JobPriority属性,打印机必须使用实现定义的默认JobPriority值。
 * <P>
 *  客户端可以始终为作业指定1到100的任何作业优先级值。但是,打印服务实例可能支持少于100个不同的作业优先级。
 * 如果是这种情况,打印服务实例会自动将客户端指定的作业优先级值映射到其中一个受支持的作业优先级,将100个作业优先级值在可用作业优先级之间平均分配。
 * <P>
 *  <B> IPP兼容性：</B>整数值给出IPP整数值。由<CODE> getName()</CODE>返回的类别名称给出了IPP属性名称。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public final class JobPriority extends IntegerSyntax
    implements PrintRequestAttribute, PrintJobAttribute {

    private static final long serialVersionUID = -4599900369040602769L;

    /**
     * Construct a new job priority attribute with the given integer value.
     *
     * <p>
     * 使用给定的整数值构造新的作业优先级属性。
     * 
     * 
     * @param  value  Integer value.
     *
     * @exception  IllegalArgumentException
     *     (Unchecked exception) Thrown if <CODE>value</CODE> is less than 1
     *     or greater than 100.
     */
    public JobPriority(int value) {
        super (value, 1, 100);
    }

    /**
     * Returns whether this job priority attribute is equivalent to the passed
     * in object. To be equivalent, all of the following conditions must be
     * true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class JobPriority.
     * <LI>
     * This job priority attribute's value and <CODE>object</CODE>'s value
     * are equal.
     * </OL>
     *
     * <p>
     *  返回此作业优先级属性是否等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE>对象</CODE>是JobPriority类的实例。
     * <LI>
     *  此作业优先级属性的值和<CODE>对象</CODE>的值相等。
     * </OL>
     * 
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this job
     *          priority attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return (super.equals (object) && object instanceof JobPriority);
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class JobPriority, the category is class JobPriority itself.
     *
     * <p>
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return JobPriority.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class JobPriority, the category name is <CODE>"job-priority"</CODE>.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于JobPriority类,类别是JobPriority类本身。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "job-priority";
    }

}
