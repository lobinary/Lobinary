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
import javax.print.attribute.SupportedValuesAttribute;

/**
 * Class JobPrioritySupported is an integer valued printing attribute class
 * that specifies whether a Print Service instance supports the {@link
 * JobPriority JobPriority} attribute and the number of different job priority
 * levels supported.
 * <P>
 * The client can always specify any {@link JobPriority JobPriority} value
 * from 1 to 100 for a job. However, the Print Service instance may support
 * fewer than 100 different job priority levels. If this is the case, the
 * Print Service instance automatically maps the client-specified job priority
 * value to one of the supported job priority levels, dividing the 100 job
 * priority values equally among the available job priority levels.
 * <P>
 * <B>IPP Compatibility:</B> The integer value gives the IPP integer value.
 * The category name returned by <CODE>getName()</CODE> gives the IPP
 * attribute name.
 * <P>
 *
 * <p>
 *  JobPrioritySupported类是一个整数值打印属性类,指定打印服务实例是否支持{@link JobPriority JobPriority}属性和支持的不同作业优先级数。
 * <P>
 *  客户端可以为作业始终指定1到100之间的任何{@link JobPriority JobPriority}值。但是,打印服务实例可能支持少于100个不同的作业优先级。
 * 如果是这种情况,打印服务实例会自动将客户端指定的作业优先级值映射到其中一个受支持的作业优先级,将100个作业优先级值在可用作业优先级之间平均分配。
 * <P>
 *  <B> IPP兼容性：</B>整数值给出IPP整数值。由<CODE> getName()</CODE>返回的类别名称给出了IPP属性名称。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public final class JobPrioritySupported extends IntegerSyntax
    implements SupportedValuesAttribute {

    private static final long serialVersionUID = 2564840378013555894L;


    /**
     * Construct a new job priority supported attribute with the given integer
     * value.
     *
     * <p>
     *  使用给定的整数值构造新的作业优先级支持的属性。
     * 
     * 
     * @param  value  Number of different job priority levels supported.
     *
     * @exception  IllegalArgumentException
     *     (Unchecked exception) Thrown if <CODE>value</CODE> is less than 1
     *     or greater than 100.
     */
    public JobPrioritySupported(int value) {
        super (value, 1, 100);
    }

    /**
     * Returns whether this job priority supported attribute is equivalent to
     * the passed in object. To be equivalent, all of the following conditions
     * must be true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class JobPrioritySupported.
     * <LI>
     * This job priority supported attribute's value and
     * <CODE>object</CODE>'s value are equal.
     * </OL>
     *
     * <p>
     *  返回此作业优先级支持属性是否等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE>对象</CODE>是JobPrioritySupported类的实例。
     * <LI>
     *  此作业优先级支持的属性值和<CODE>对象</CODE>的值相等。
     * </OL>
     * 
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this job
     *          priority supported attribute, false otherwise.
     */
    public boolean equals (Object object) {

        return (super.equals(object) &&
               object instanceof JobPrioritySupported);
    }


    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class JobPrioritySupported, the
     * category is class JobPrioritySupported itself.
     *
     * <p>
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return JobPrioritySupported.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class JobPrioritySupported, the
     * category name is <CODE>"job-priority-supported"</CODE>.
     *
     * <p>
     * 获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于JobPrioritySupported类,类别是JobPrioritySupported类本身。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "job-priority-supported";
    }

}
