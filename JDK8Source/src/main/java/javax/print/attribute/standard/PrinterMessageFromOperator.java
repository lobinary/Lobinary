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
import javax.print.attribute.PrintServiceAttribute;

/**
 * Class PrinterMessageFromOperator is a printing attribute class, a text
 * attribute, that provides a message from an operator, system administrator,
 * or "intelligent" process to indicate to the end user information about or
 * status of the printer, such as why it is unavailable or when it is
 * expected to be available.
 * <P>
 * A Print Service's attribute set includes zero instances or one instance of
 * a
 * PrinterMessageFromOperator attribute, not more than one instance. A new
 * PrinterMessageFromOperator attribute replaces an existing
 * PrinterMessageFromOperator attribute, if any. In other words,
 * PrinterMessageFromOperator is not intended to be a history log.
 * If it wishes, the client can detect changes to a Print Service's
 * PrinterMessageFromOperator
 * attribute and maintain the client's own history log of the
 * PrinterMessageFromOperator attribute values.
 * <P>
 * <B>IPP Compatibility:</B> The string value gives the IPP name value. The
 * locale gives the IPP natural language. The category name returned by
 * <CODE>getName()</CODE> gives the IPP attribute name.
 * <P>
 *
 * <p>
 *  类PrinterMessageFromOperator是打印属性类,文本属性,其提供来自操作者,系统管理员或"智能"过程的消息以向终端用户指示关于打印机的信息或状态,诸如为什么它不可用或何时它预计可用
 * 。
 * <P>
 *  打印服务的属性集包括零个实例或PrinterMessageFromOperator属性的一个实例,不超过一个实例。
 * 新的PrinterMessageFromOperator属性替换现有的PrinterMessageFromOperator属性(如果有)。
 * 换句话说,PrinterMessageFromOperator不打算作为历史日志。
 * 如果希望,客户端可以检测对打印服务的PrinterMessageFromOperator属性的更改,并维护客户端自己的PrinterMessageFromOperator属性值的历史日志。
 * <P>
 *  <B> IPP兼容性：</B>字符串值给出IPP名称值。语言环境提供IPP自然语言。由<CODE> getName()</CODE>返回的类别名称给出了IPP属性名称。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public final class PrinterMessageFromOperator   extends TextSyntax
    implements PrintServiceAttribute {

    static final long serialVersionUID = -4486871203218629318L;

    /**
     * Constructs a new printer message from operator attribute with the
     * given message and locale.
     *
     * <p>
     *  根据给定消息和区域设置构造来自操作员属性的新打印机消息。
     * 
     * 
     * @param  message  Message.
     * @param  locale   Natural language of the text string. null
     * is interpreted to mean the default locale as returned
     * by <code>Locale.getDefault()</code>
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <CODE>message</CODE> is null.
     */
    public PrinterMessageFromOperator(String message, Locale locale) {
        super (message, locale);
    }

    /**
     * Returns whether this printer message from operator attribute is
     * equivalent to the passed in object. To be equivalent, all of the
     * following conditions must be true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class
     * PrinterMessageFromOperator.
     * <LI>
     * This printer message from operator attribute's underlying string and
     * <CODE>object</CODE>'s underlying string are equal.
     * <LI>
     * This printer message from operator attribute's locale and
     * <CODE>object</CODE>'s locale are equal.
     * </OL>
     *
     * <p>
     *  返回此打印机消息是否来自operator属性等同于传入的in对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     * <CODE>对象</CODE>是PrinterMessageFromOperator类的实例。
     * <LI>
     *  来自操作符属性的底层字符串和<CODE>对象</CODE>的底层字符串的打印机消息是相等的。
     * <LI>
     *  来自操作员属性的区域设置和<CODE>对象</CODE>的打印机消息是相等的。
     * </OL>
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this printer
     *          message from operator attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return (super.equals(object) &&
                object instanceof PrinterMessageFromOperator);
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class PrinterMessageFromOperator,
     * the category is class PrinterMessageFromOperator itself.
     *
     * <p>
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return PrinterMessageFromOperator.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class PrinterMessageFromOperator,
     * the category name is <CODE>"printer-message-from-operator"</CODE>.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于PrinterMessageFromOperator类,类别是PrinterMessageFromOperator类本身。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "printer-message-from-operator";
    }

}
