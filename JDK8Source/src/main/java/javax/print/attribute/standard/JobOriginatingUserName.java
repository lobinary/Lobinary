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
import javax.print.attribute.PrintJobAttribute;

/**
 * Class JobOriginatingUserName is a printing attribute class, a text
 * attribute, that contains the name of the end user that submitted the
 * print job. If possible, the printer sets this attribute to the most
 * authenticated printable user name that it can obtain from the
 * authentication service that authenticated the submitted Print Request.
 * If such is not available, the printer uses the value of the
 * {@link RequestingUserName RequestingUserName}
 * attribute supplied by the client in the Print Request's attribute set.
 * If no authentication service is available, and the client did not supply
 * a {@link RequestingUserName RequestingUserName} attribute,
 * the printer sets the JobOriginatingUserName attribute to an empty
 * (zero-length) string.
 * <P>
 * <B>IPP Compatibility:</B> The string value gives the IPP name value. The
 * locale gives the IPP natural language. The category name returned by
 * <CODE>getName()</CODE> gives the IPP attribute name.
 * <P>
 *
 * <p>
 *  JobOriginatingUserName类是一个打印属性类,一个文本属性,包含提交打印作业的最终用户的名称。
 * 如果可能,打印机将此属性设置为可从认证所提交的打印请求的认证服务获得的最可认证的可打印用户名。
 * 如果不可用,则打印机使用客户端在打印请求属性集中提供的{@link RequestingUserName RequestingUserName}属性的值。
 * 如果没有可用的身份验证服务,并且客户端未提供{@link RequestingUserName RequestingUserName}属性,则打印机将JobOriginatingUserName属性设置
 * 为空(零长度)字符串。
 * 如果不可用,则打印机使用客户端在打印请求属性集中提供的{@link RequestingUserName RequestingUserName}属性的值。
 * <P>
 *  <B> IPP兼容性：</B>字符串值给出IPP名称值。语言环境提供IPP自然语言。由<CODE> getName()</CODE>返回的类别名称给出了IPP属性名称。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public final class JobOriginatingUserName extends TextSyntax
        implements PrintJobAttribute {

    private static final long serialVersionUID = -8052537926362933477L;

    /**
     * Constructs a new job originating user name attribute with the given
     * user name and locale.
     *
     * <p>
     *  使用给定的用户名和区域设置构造新的作业来源用户名属性。
     * 
     * 
     * @param  userName  User name.
     * @param  locale    Natural language of the text string. null
     * is interpreted to mean the default locale as returned
     * by <code>Locale.getDefault()</code>
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <CODE>userName</CODE> is null.
     */
    public JobOriginatingUserName(String userName, Locale locale) {
        super (userName, locale);
    }

    /**
     * Returns whether this job originating user name attribute is equivalent to
     * the passed in object. To be equivalent, all of the following conditions
     * must be true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class JobOriginatingUserName.
     * <LI>
     * This job originating user name attribute's underlying string and
     * <CODE>object</CODE>'s underlying string are equal.
     * <LI>
     * This job originating user name attribute's locale and
     * <CODE>object</CODE>'s locale are equal.
     * </OL>
     *
     * <p>
     *  返回此作业来源用户名属性是否等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE>对象</CODE>是JobOriginatingUserName类的实例。
     * <LI>
     * 此作业来源用户名属性的底层字符串和<CODE>对象</CODE>的底层字符串是相等的。
     * <LI>
     *  此作业来源用户名属性的区域设置和<CODE>对象</CODE>的区域设置相等。
     * </OL>
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this job
     *          originating user name attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return (super.equals (object) &&
                object instanceof JobOriginatingUserName);
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class JobOriginatingUserName, the
     * category is class JobOriginatingUserName itself.
     *
     * <p>
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return JobOriginatingUserName.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class JobOriginatingUserName, the
     * category name is <CODE>"job-originating-user-name"</CODE>.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于JobOriginatingUserName类,类别为JobOriginatingUserName类本身。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "job-originating-user-name";
    }

}
