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

/**
 * Class RequestingUserName is a printing attribute class, a text attribute,
 * that specifies the name of the end user that submitted the print job. A
 * requesting user name is an arbitrary string defined by the client. The
 * printer does not put the client-specified RequestingUserName attribute into
 * the Print Job's attribute set; rather, the printer puts in a {@link
 * JobOriginatingUserName JobOriginatingUserName} attribute.
 * This means that services which support specifying a username with this
 * attribute should also report a JobOriginatingUserName in the job's
 * attribute set. Note that many print services may have a way to independently
 * authenticate the user name, and so may state support for a
 * requesting user name, but in practice will then report the user name
 * authenticated by the service rather than that specified via this
 * attribute.
 * <P>
 * <B>IPP Compatibility:</B> The string value gives the IPP name value. The
 * locale gives the IPP natural language. The category name returned by
 * <CODE>getName()</CODE> gives the IPP attribute name.
 * <P>
 *
 * <p>
 *  类RequestingUserName是打印属性类,文本属性,用于指定提交打印作业的最终用户的名称。请求用户名是由客户端定义的任意字符串。
 * 打印机不会将客户机指定的RequestingUserName属性放入打印作业的属性集中;而是,打印机放入{@link JobOriginatingUserName JobOriginatingUserName}
 * 属性。
 *  类RequestingUserName是打印属性类,文本属性,用于指定提交打印作业的最终用户的名称。请求用户名是由客户端定义的任意字符串。
 * 这意味着支持使用此属性指定用户名的服务也应该报告作业属性集中的JobOriginatingUserName。
 * 注意,许多打印服务可能具有独立地认证用户名的方式,因此可以声明对请求用户名的支持,但是实际上将报告由服务认证的用户名,而不是通过该属性指定的用户名。
 * <P>
 *  <B> IPP兼容性：</B>字符串值给出IPP名称值。语言环境提供IPP自然语言。由<CODE> getName()</CODE>返回的类别名称给出了IPP属性名称。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public final class RequestingUserName   extends TextSyntax
    implements PrintRequestAttribute {

    private static final long serialVersionUID = -2683049894310331454L;

    /**
     * Constructs a new requesting user name attribute with the given user
     * name and locale.
     *
     * <p>
     *  使用给定的用户名和区域设置构造新的请求用户名属性。
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
    public RequestingUserName(String userName, Locale locale) {
        super (userName, locale);
    }

    /**
     * Returns whether this requesting user name attribute is equivalent to
     * the passed in object. To be equivalent, all of the following
     * conditions must be true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class RequestingUserName.
     * <LI>
     * This requesting user name attribute's underlying string and
     * <CODE>object</CODE>'s underlying string are equal.
     * <LI>
     * This requesting user name attribute's locale and
     * <CODE>object</CODE>'s locale are equal.
     * </OL>
     *
     * <p>
     *  返回此请求用户名属性是否等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     * <CODE> object </CODE>是RequestingUserName类的实例。
     * <LI>
     *  这个请求用户名属性的底层字符串和<CODE>对象</CODE>的底层字符串是相等的。
     * <LI>
     *  此请求用户名属性的区域设置和<CODE>对象</CODE>的区域设置是相等的。
     * </OL>
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this requesting
     *          user name attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return (super.equals(object) &&
                object instanceof RequestingUserName);
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class RequestingUserName, the
     * category is class RequestingUserName itself.
     *
     * <p>
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return RequestingUserName.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class RequestingUserName, the
     * category name is <CODE>"requesting-user-name"</CODE>.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于RequestingUserName类,类别是RequestingUserName类本身。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "requesting-user-name";
    }

}
