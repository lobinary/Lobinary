/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2006, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.datatype;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;

/**
 * <p>Utility class to contain basic Datatype values as constants.</p>
 *
 * <p>
 *  <p>实用程序类包含基本数据类型值作为常量。</p>
 * 
 * 
 * @author <a href="mailto:Jeff.Suttor@Sun.com">Jeff Suttor</a>
 * @since 1.5
 */

public final class DatatypeConstants {

    /**
     * <p>Private constructor to prevent instantiation.</p>
     * <p>
     *  <p>私人构造函数阻止实例化。</p>
     * 
     */
        private DatatypeConstants() {
        }

        /**
         * Value for first month of year.
         * <p>
         *  一年中第一个月的价值。
         * 
         */
        public static final int JANUARY  = 1;

        /**
         * Value for second month of year.
         * <p>
         *  一年的第二个月的价值。
         * 
         */
        public static final int FEBRUARY = 2;

        /**
         * Value for third month of year.
         * <p>
         *  一年中第三个月的价值。
         * 
         */
        public static final int MARCH    = 3;

        /**
         * Value for fourth month of year.
         * <p>
         *  一年的第四个月的价值。
         * 
         */
        public static final int APRIL    = 4;

        /**
         * Value for fifth month of year.
         * <p>
         *  一年中第五个月的价值。
         * 
         */
        public static final int MAY      = 5;

        /**
         * Value for sixth month of year.
         * <p>
         *  一年的第六个月的价值。
         * 
         */
        public static final int JUNE     = 6;

        /**
         * Value for seventh month of year.
         * <p>
         *  一年的第七个月的价值。
         * 
         */
        public static final int JULY     = 7;

        /**
         * Value for eighth month of year.
         * <p>
         *  一年中第八个月的价值。
         * 
         */
        public static final int AUGUST   = 8;

        /**
         * Value for ninth month of year.
         * <p>
         *  一年的第九个月的价值。
         * 
         */
        public static final int SEPTEMBER = 9;

        /**
         * Value for tenth month of year.
         * <p>
         *  值为一年的第十个月。
         * 
         */
        public static final int OCTOBER = 10;

        /**
         * Value for eleven month of year.
         * <p>
         *  一年的十一个月的价值。
         * 
         */
        public static final int NOVEMBER = 11;

        /**
         * Value for twelve month of year.
         * <p>
         *  一年十二个月的价值。
         * 
         */
        public static final int DECEMBER = 12;

        /**
         * <p>Comparison result.</p>
         * <p>
         *  <p>比较结果。</p>
         * 
         */
        public static final int LESSER = -1;

        /**
         * <p>Comparison result.</p>
         * <p>
         *  <p>比较结果。</p>
         * 
         */
        public static final int EQUAL =  0;

        /**
         * <p>Comparison result.</p>
         * <p>
         *  <p>比较结果。</p>
         * 
         */
        public static final int GREATER =  1;

        /**
         * <p>Comparison result.</p>
         * <p>
         *  <p>比较结果。</p>
         * 
         */
        public static final int INDETERMINATE =  2;

        /**
         * Designation that an "int" field is not set.
         * <p>
         *  指定未设置"int"字段。
         * 
         */
        public static final int FIELD_UNDEFINED = Integer.MIN_VALUE;

        /**
         * <p>A constant that represents the years field.</p>
         * <p>
         *  <p>代表年份字段的常数。</p>
         * 
         */
        public static final Field YEARS = new Field("YEARS", 0);

        /**
         * <p>A constant that represents the months field.</p>
         * <p>
         *  <p>代表月份字段的常数。</p>
         * 
         */
        public static final Field MONTHS = new Field("MONTHS", 1);

        /**
         * <p>A constant that represents the days field.</p>
         * <p>
         *  <p>代表天字段的常数。</p>
         * 
         */
        public static final Field DAYS = new Field("DAYS", 2);

        /**
         * <p>A constant that represents the hours field.</p>
         * <p>
         *  <p>代表小时字段的常数。</p>
         * 
         */
        public static final Field HOURS = new Field("HOURS", 3);

        /**
         * <p>A constant that represents the minutes field.</p>
         * <p>
         *  <p>代表分钟栏位的常数。</p>
         * 
         */
        public static final Field MINUTES = new Field("MINUTES", 4);

        /**
         * <p>A constant that represents the seconds field.</p>
         * <p>
         *  <p>代表秒字段的常数。</p>
         * 
         */
        public static final Field SECONDS = new Field("SECONDS", 5);

        /**
         * Type-safe enum class that represents six fields
         * of the {@link Duration} class.
         * <p>
         *  类型安全的枚举类,表示{@link Duration}类的六个字段。
         * 
         * 
         * @since 1.5
         */
        public static final class Field {

                /**
                 * <p><code>String</code> representation of <code>Field</code>.</p>
                 * <p>
                 *  <p> <code>字段</code>的<code>字符串</code>表示。</p>
                 * 
                 */
                private final String str;
                /**
                 * <p>Unique id of the field.</p>
                 *
                 * <p>This value allows the {@link Duration} class to use switch
                 * statements to process fields.</p>
                 * <p>
                 *  <p>字段的唯一ID。</p>
                 * 
                 *  <p>此值允许{@link Duration}类使用switch语句处理字段。</p>
                 * 
                 */
                private final int id;

                /**
                 * <p>Construct a <code>Field</code> with specified values.</p>
                 * <p>
                 *  <p>使用指定的值构建<code>字段</code>。</p>
                 * 
                 * 
                 * @param str <code>String</code> representation of <code>Field</code>
                 * @param id  <code>int</code> representation of <code>Field</code>
                 */
                private Field(final String str, final int id) {
                        this.str = str;
                        this.id = id;
                }
                /**
                 * Returns a field name in English. This method
                 * is intended to be used for debugging/diagnosis
                 * and not for display to end-users.
                 *
                 * <p>
                 * 返回英语字段名称。此方法旨在用于调试/诊断,而不是用于向最终用户显示。
                 * 
                 * 
                 * @return
                 *      a non-null valid String constant.
                 */
                public String toString() { return str; }

                /**
                 * <p>Get id of this Field.</p>
                 *
                 * <p>
                 *  <p>获取此字段的ID。</p>
                 * 
                 * 
                 * @return Id of field.
                 */
                public int getId() {
                        return id;
                }
        }

        /**
         * <p>Fully qualified name for W3C XML Schema 1.0 datatype <code>dateTime</code>.</p>
         * <p>
         *  <p> W3C XML Schema 1.0数据类型<code> dateTime </code>的完全限定名称。</p>
         * 
         */
        public static final QName DATETIME = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "dateTime");

        /**
         * <p>Fully qualified name for W3C XML Schema 1.0 datatype <code>time</code>.</p>
         * <p>
         *  <p> W3C XML Schema 1.0数据类型<code> time </code>的完全限定名称。</p>
         * 
         */
        public static final QName TIME = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "time");

        /**
         * <p>Fully qualified name for W3C XML Schema 1.0 datatype <code>date</code>.</p>
         * <p>
         *  <p> W3C XML Schema 1.0数据类型<code> date </code>的完全限定名称。</p>
         * 
         */
        public static final QName DATE = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "date");

        /**
         * <p>Fully qualified name for W3C XML Schema 1.0 datatype <code>gYearMonth</code>.</p>
         * <p>
         *  <p> W3C XML Schema 1.0数据类型<code> gYearMonth </code>的完全限定名。</p>
         * 
         */
        public static final QName GYEARMONTH = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "gYearMonth");

        /**
         * <p>Fully qualified name for W3C XML Schema 1.0 datatype <code>gMonthDay</code>.</p>
         * <p>
         *  <p> W3C XML Schema 1.0数据类型<code> gMonthDay </code>的完全限定名称。</p>
         * 
         */
        public static final QName GMONTHDAY = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "gMonthDay");

        /**
         * <p>Fully qualified name for W3C XML Schema 1.0 datatype <code>gYear</code>.</p>
         * <p>
         *  <p> W3C XML Schema 1.0数据类型<code> gYear </code>的完全限定名称。</p>
         * 
         */
        public static final QName GYEAR = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "gYear");

        /**
         * <p>Fully qualified name for W3C XML Schema 1.0 datatype <code>gMonth</code>.</p>
         * <p>
         *  <p> W3C XML Schema 1.0数据类型<code> gMonth </code>的完全限定名称。</p>
         * 
         */
        public static final QName GMONTH = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "gMonth");

        /**
         * <p>Fully qualified name for W3C XML Schema 1.0 datatype <code>gDay</code>.</p>
         * <p>
         *  <p> W3C XML Schema 1.0数据类型<code> gDay </code>的完全限定名称。</p>
         * 
         */
        public static final QName GDAY = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "gDay");

        /**
         * <p>Fully qualified name for W3C XML Schema datatype <code>duration</code>.</p>
         * <p>
         *  <p> W3C XML模式数据类型<code> duration </code>的完全限定名称。</p>
         * 
         */
        public static final QName DURATION = new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "duration");

        /**
         * <p>Fully qualified name for XQuery 1.0 and XPath 2.0 datatype <code>dayTimeDuration</code>.</p>
         * <p>
         *  <p> XQuery 1.0和XPath 2.0数据类型<code> dayTimeDuration </code>的完全限定名称。</p>
         * 
         */
        public static final QName DURATION_DAYTIME = new QName(XMLConstants.W3C_XPATH_DATATYPE_NS_URI, "dayTimeDuration");

        /**
         * <p>Fully qualified name for XQuery 1.0 and XPath 2.0 datatype <code>yearMonthDuration</code>.</p>
         * <p>
         *  <p> XQuery 1.0和XPath 2.0数据类型<code> yearMonthDuration </code>的完全限定名称。</p>
         * 
         */
        public static final QName DURATION_YEARMONTH = new QName(XMLConstants.W3C_XPATH_DATATYPE_NS_URI, "yearMonthDuration");

        /**
         * W3C XML Schema max timezone offset is -14:00. Zone offset is in minutes.
         * <p>
         *  W3C XML模式最大时区偏移量为-14：00。区域偏移量以分钟为单位。
         * 
         */
        public static final int MAX_TIMEZONE_OFFSET = -14 * 60;

        /**
         * W3C XML Schema min timezone offset is +14:00. Zone offset is in minutes.
         * <p>
         *  W3C XML模式最小时区偏移是+14：00。区域偏移量以分钟为单位。
         */
        public static final int MIN_TIMEZONE_OFFSET = 14 * 60;

}
