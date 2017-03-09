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
import javax.print.attribute.TextSyntax;
import javax.print.attribute.PrintRequestAttribute;
import javax.print.attribute.PrintJobAttribute;

/**
 * Class JobName is a printing attribute class, a text attribute, that specifies
 * the name of a print job. A job's name is an arbitrary string defined by the
 * client. It does not need to be unique between different jobs. A Print Job's
 * JobName attribute is set to the value supplied by the client in the Print
 * Request's attribute set. If, however, the client does not supply a JobName
 * attribute in the Print Request, the printer, when it creates the Print Job,
 * must generate a JobName. The printer should generate the value of the Print
 * Job's JobName attribute from the first of the following sources that produces
 * a value: (1) the {@link DocumentName DocumentName} attribute of the first (or
 * only) doc in the job, (2) the URL of the first (or only) doc in the job, if
 * the doc's print data representation object is a URL, or (3) any other piece
 * of Print Job specific and/or document content information.
 * <P>
 * <B>IPP Compatibility:</B> The string value gives the IPP name value. The
 * locale gives the IPP natural language. The category name returned by
 * <CODE>getName()</CODE> gives the IPP attribute name.
 * <P>
 *
 * <p>
 *  JobName类是一个打印属性类,一个文本属性,用于指定打印作业的名称。作业名称是由客户端定义的任意字符串。它不需要在不同的工作之间是唯一的。
 * 打印作业的JobName属性设置为客户端在打印请求属性集中提供的值。但是,如果客户端在打印请求中没有提供JobName属性,则打印机在创建打印作业时必须生成JobName。
 * 打印机应从生成值的第一个源生成打印作业的JobName属性的值：(1)作业中第一个(或唯一)doc的{@link DocumentName DocumentName}属性,(2)如果文档的打印数据表示对
 * 象是URL,则在作业中的第一(或仅有)文档的URL,或(3)任何其他打印作业特定和/或文档内容信息。
 * 打印作业的JobName属性设置为客户端在打印请求属性集中提供的值。但是,如果客户端在打印请求中没有提供JobName属性,则打印机在创建打印作业时必须生成JobName。
 * <P>
 *  <B> IPP兼容性：</B>字符串值给出IPP名称值。语言环境提供IPP自然语言。由<CODE> getName()</CODE>返回的类别名称给出了IPP属性名称。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public final class JobName extends TextSyntax
        implements PrintRequestAttribute, PrintJobAttribute {

    private static final long serialVersionUID = 4660359192078689545L;

    /**
     * Constructs a new job name attribute with the given job name and locale.
     *
     * <p>
     *  使用给定的作业名称和区域设置构造新的作业名称属性。
     * 
     * 
     * @param  jobName  Job name.
     * @param  locale   Natural language of the text string. null
     * is interpreted to mean the default locale as returned
     * by <code>Locale.getDefault()</code>
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <CODE>jobName</CODE> is null.
     */
    public JobName(String jobName, Locale locale) {
        super (jobName, locale);
    }

    /**
     * Returns whether this job name attribute is equivalent to the passed in
     * object. To be equivalent, all of the following conditions must be true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class JobName.
     * <LI>
     * This job name attribute's underlying string and <CODE>object</CODE>'s
     * underlying string are equal.
     * <LI>
     * This job name attribute's locale and <CODE>object</CODE>'s locale are
     * equal.
     * </OL>
     *
     * <p>
     *  返回此作业名称属性是否等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     * <CODE>对象</CODE>是JobName类的实例。
     * <LI>
     *  此作业名称属性的底层字符串和<CODE>对象</CODE>的底层字符串是相等的。
     * <LI>
     *  此作业名称属性的区域设置和<CODE>对象</CODE>的区域设置相等。
     * </OL>
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this job name
     *          attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return (super.equals(object) && object instanceof JobName);
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class JobName, the category is class JobName itself.
     *
     * <p>
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return JobName.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class JobName, the category name is <CODE>"job-name"</CODE>.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于JobName类,类别是JobName类本身。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "job-name";
    }

}
