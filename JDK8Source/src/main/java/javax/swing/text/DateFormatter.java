/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.text;

import java.awt.event.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.text.*;

/**
 * DateFormatter is an <code>InternationalFormatter</code> that does its
 * formatting by way of an instance of <code>java.text.DateFormat</code>.
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans&trade;
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * <p>
 *  DateFormatter是一个通过<code> java.text.DateFormat </code>的实例进行格式化的<code> InternationalFormatter </code>。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @see java.text.DateFormat
 *
 * @since 1.4
 */
public class DateFormatter extends InternationalFormatter {
    /**
     * This is shorthand for
     * <code>new DateFormatter(DateFormat.getDateInstance())</code>.
     * <p>
     *  这是<code> new DateFormatter(DateFormat.getDateInstance())</code>的简写。
     * 
     */
    public DateFormatter() {
        this(DateFormat.getDateInstance());
    }

    /**
     * Returns a DateFormatter configured with the specified
     * <code>Format</code> instance.
     *
     * <p>
     *  返回使用指定的<code> Format </code>实例配置的DateFormatter。
     * 
     * 
     * @param format Format used to dictate legal values
     */
    public DateFormatter(DateFormat format) {
        super(format);
        setFormat(format);
    }

    /**
     * Sets the format that dictates the legal values that can be edited
     * and displayed.
     * <p>
     * If you have used the nullary constructor the value of this property
     * will be determined for the current locale by way of the
     * <code>Dateformat.getDateInstance()</code> method.
     *
     * <p>
     *  设置指定可编辑和显示的合法值的格式。
     * <p>
     *  如果已经使用nullary构造函数,则将通过<code> Dateformat.getDateInstance()</code>方法为当前语言环境确定此属性的值。
     * 
     * 
     * @param format DateFormat instance used for converting from/to Strings
     */
    public void setFormat(DateFormat format) {
        super.setFormat(format);
    }

    /**
     * Returns the Calendar that <code>DateFormat</code> is associated with,
     * or if the <code>Format</code> is not a <code>DateFormat</code>
     * <code>Calendar.getInstance</code> is returned.
     * <p>
     *  返回与<code> DateFormat </code>相关联的日历,或者如果<code>格式</code>不是<code> DateFormat </code> <code> Calendar.ge
     * tInstance </code> 。
     * 
     */
    private Calendar getCalendar() {
        Format f = getFormat();

        if (f instanceof DateFormat) {
            return ((DateFormat)f).getCalendar();
        }
        return Calendar.getInstance();
    }


    /**
     * Returns true, as DateFormatterFilter will support
     * incrementing/decrementing of the value.
     * <p>
     *  返回true,因为DateFormatterFilter将支持该值的递增/递减。
     * 
     */
    boolean getSupportsIncrement() {
        return true;
    }

    /**
     * Returns the field that will be adjusted by adjustValue.
     * <p>
     *  返回将由adjustValue调整的字段。
     * 
     */
    Object getAdjustField(int start, Map attributes) {
        Iterator attrs = attributes.keySet().iterator();

        while (attrs.hasNext()) {
            Object key = attrs.next();

            if ((key instanceof DateFormat.Field) &&
                (key == DateFormat.Field.HOUR1 ||
                 ((DateFormat.Field)key).getCalendarField() != -1)) {
                return key;
            }
        }
        return null;
    }

    /**
     * Adjusts the Date if FieldPosition identifies a known calendar
     * field.
     * <p>
     * 如果FieldPosition标识已知的日历字段,则调整日期。
     */
    Object adjustValue(Object value, Map attributes, Object key,
                           int direction) throws
                      BadLocationException, ParseException {
        if (key != null) {
            int field;

            // HOUR1 has no corresponding calendar field, thus, map
            // it to HOUR0 which will give the correct behavior.
            if (key == DateFormat.Field.HOUR1) {
                key = DateFormat.Field.HOUR0;
            }
            field = ((DateFormat.Field)key).getCalendarField();

            Calendar calendar = getCalendar();

            if (calendar != null) {
                calendar.setTime((Date)value);

                int fieldValue = calendar.get(field);

                try {
                    calendar.add(field, direction);
                    value = calendar.getTime();
                } catch (Throwable th) {
                    value = null;
                }
                return value;
            }
        }
        return null;
    }
}
