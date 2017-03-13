/***** Lobxxx Translate Finished ******/
/*
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

/*
 * Copyright (c) 2009 by Oracle Corporation. All Rights Reserved.
 * <p>
 *  版权所有(c)2009 by Oracle Corporation保留所有权利
 * 
 */

package com.sun.org.apache.xerces.internal.jaxp.datatype;


import java.math.BigInteger;
import java.math.BigDecimal;
import javax.xml.datatype.DatatypeConstants;

/**
 * <p>Represent a subtype <code>xdt:dayTimeDuration</code> of a <code>Duration</code>
 * as specified in <a href="http://www.w3.org/TR/xpath-datamodel#dayTimeDuration">
 *   XQuery 1.0 and XPath 2.0 Data Model, xdt:dayTimeDuration</a>.</p>
 *
 *
 * <p>The DurationYearMonth object represents a period of Gregorian time,
 * with a lexical representation, "<em>PnDTnHnMnS</em>" that contains only year and month components.
 * </p>
 *
 *
 * <p>
 *  <p>表示<a href=\"http://wwww3org/TR/xpath-datamodel#dayTimeDuration\"> XQuery中指定的<code> Duration </code>
 * 的子类型<code> xdt：dayTimeDuration </code> 10和XPath 20数据模型,xdt：dayTimeDuration </a> </p>。
 * 
 *  <p> DurationYearMonth对象表示格雷戈里时间的一个周期,其中包含仅包含年份和月份组成部分的词汇表示"<em> PnDTnHnMnS </em>"
 * </p>
 * 
 * 
 * @author <a href="mailto:Vikram.Aroskar@Sun.COM">Vikram Aroskar</a>
 * @author <a href="mailto:Huizhe.wang@oracle.com">Joe Wang</a>
 * @version $Revision: 1.2 $, $Date: 2010/05/19 23:20:06 $

 * @see XMLGregorianCalendar#add(Duration)
 */

class DurationDayTimeImpl
        extends DurationImpl {

    public DurationDayTimeImpl(
        boolean isPositive,
        BigInteger days,
        BigInteger hours,
        BigInteger minutes,
        BigDecimal seconds) {

        super(isPositive, null, null, days, hours, minutes, seconds);
        convertToCanonicalDayTime();
    }

    public DurationDayTimeImpl(
        boolean isPositive,
        int days,
        int hours,
        int minutes,
        int seconds) {

        this(
            isPositive,
            wrap(days),
            wrap(hours),
            wrap(minutes),
            (seconds != DatatypeConstants.FIELD_UNDEFINED ? new BigDecimal(String.valueOf(seconds)) : null));
    }

        /**
         * <p>Construct a <code>Duration</code> of type <code>xdt:dayTimeDuration</code> by parsing its <code>String</code> representation,
         * "<em>PnDTnHnMnS</em>", <a href="http://www.w3.org/TR/xpath-datamodel#dayTimeDuration">
         *   XQuery 1.0 and XPath 2.0 Data Model, xdt:dayTimeDuration</a>.</p>
         *
         * <p>The datatype <code>xdt:dayTimeDuration</code> is a subtype of <code>xs:duration</code>
         * whose lexical representation contains only day, hour, minute, and second components.
         * This datatype resides in the namespace <code>http://www.w3.org/2003/11/xpath-datatypes</code>.</p>
         *
     * <p>All four values are set and availabe from the created {@link Duration}</p>
         *
     * <p>The XML Schema specification states that values can be of an arbitrary size.
     * Implementations may chose not to or be incapable of supporting arbitrarily large and/or small values.
     * An {@link UnsupportedOperationException} will be thrown with a message indicating implementation limits
     * if implementation capacities are exceeded.</p>
     *
     * <p>
     * <p>通过解析其<code> String </code>表示形式,"<em> PnDTnHnMnS </em>",</code>构造类型<code> xdt：dayTimeDuration </code>
     * 的< a href ="http：// wwww3org / TR / xpath-datamodel#dayTimeDuration"> XQuery 10和XPath 20数据模型,xdt：dayT
     * imeDuration </a> </p>。
     * 
     *  <p>数据类型<code> xdt：dayTimeDuration </code>是<code> xs：duration </code>的子类型,其词法表示仅包含日,小时,分钟和秒组件此数据类型位于命
     * 名空间<代码> http：// wwww3org / 2003/11 / xpath-datatypes </code> </p>。
     * 
     *  <p>所有四个值都已设置,并可从创建的{@link Duration} </p>中获得
     * 
     * <p> XML模式规范说明值可以是任意大小实现可以选择不支持或不能支持任意大和/或小的值。
     * {@link UnsupportedOperationException}将抛出一个消息,指示实现限制,如果实现超过容量</p>。
     * 
     * 
         * @param lexicalRepresentation Lexical representation of a duration.
         *
         * @throws IllegalArgumentException If <code>lexicalRepresentation</code> is not a valid representation of a <code>Duration</code> expressed only in terms of days and time.
         * @throws UnsupportedOperationException If implementation cannot support requested values.
         * @throws NullPointerException If <code>lexicalRepresentation</code> is <code>null</code>.
         */
    protected DurationDayTimeImpl(String lexicalRepresentation) {
        super(lexicalRepresentation);

        if (getYears() > 0 || getMonths() > 0) {
            throw new IllegalArgumentException(
                    "Trying to create an xdt:dayTimeDuration with an invalid"
                    + " lexical representation of \"" + lexicalRepresentation
                    + "\", data model requires a format PnDTnHnMnS.");
        }

        convertToCanonicalDayTime();
    }
        /**
         * <p>Create a <code>Duration</code> of type <code>xdt:dayTimeDuration</code> using the specified milliseconds as defined in
         * <a href="http://www.w3.org/TR/xpath-datamodel#dayTimeDuration">
         *   XQuery 1.0 and XPath 2.0 Data Model, xdt:dayTimeDuration</a>.</p>
         *
         * <p>The datatype <code>xdt:dayTimeDuration</code> is a subtype of <code>xs:duration</code>
         * whose lexical representation contains only day, hour, minute, and second components.
         * This datatype resides in the namespace <code>http://www.w3.org/2003/11/xpath-datatypes</code>.</p>
         *
     * <p>All four values are set by computing their values from the specified milliseconds
     * and are availabe using the <code>get</code> methods of  the created {@link Duration}.
     * The values conform to and are defined by:</p>
     * <ul>
     *   <li>ISO 8601:2000(E) Section 5.5.3.2 Alternative format</li>
     *   <li><a href="http://www.w3.org/TR/xmlschema-2/#isoformats">
     *     W3C XML Schema 1.0 Part 2, Appendix D, ISO 8601 Date and Time Formats</a>
     *   </li>
     *   <li>{@link XMLGregorianCalendar}  Date/Time Datatype Field Mapping Between XML Schema 1.0 and Java Representation</li>
     * </ul>
         *
         * <p>The default start instance is defined by {@link GregorianCalendar}'s use of the start of the epoch: i.e.,
         * {@link java.util.Calendar#YEAR} = 1970,
         * {@link java.util.Calendar#MONTH} = {@link java.util.Calendar#JANUARY},
         * {@link java.util.Calendar#DATE} = 1, etc.
         * This is important as there are variations in the Gregorian Calendar,
         * e.g. leap years have different days in the month = {@link java.util.Calendar#FEBRUARY}
         * so the result of {@link Duration#getDays()} can be influenced.</p>
         *
     * <p>Any remaining milliseconds after determining the day, hour, minute and second are discarded.</p>
     *
     * <p>
     *  <p>使用指定的毫秒数创建<code> xdt：dayTimeDuration </code>类型的<code> Duration </code>
     * <a href="http://www.w3.org/TR/xpath-datamodel#dayTimeDuration">
     *  XQuery 10和XPath 20数据模型,xdt：dayTimeDuration </a> </p>
     * 
     *  <p>数据类型<code> xdt：dayTimeDuration </code>是<code> xs：duration </code>的子类型,其词法表示仅包含日,小时,分钟和秒组件此数据类型位于命
     * 名空间<代码> http：// wwww3org / 2003/11 / xpath-datatypes </code> </p>。
     * 
     * <p>通过从指定的毫秒计算它们的值来设置所有四个值,并且可以使用所创建的{@link Duration}的<code> get </code>方法获得这些值符合和定义：</p >
     * <ul>
     *  <li> ISO 8601：2000(E)第5532节替代格式</li> <li> <a href=\"http://wwww3org/TR/xmlschema-2/#isoformats\"> W3
     * C XML Schema 10第2部分,附录D,ISO 8601日期和时间格式</a>。
     * </li>
     * 
     * @param durationInMilliseconds Milliseconds of <code>Duration</code> to create.
     *
     * @return New <code>Duration</code> created with the specified <code>durationInMilliseconds</code>.
     *
     * @see <a href="http://www.w3.org/TR/xpath-datamodel#dayTimeDuration">
     *   XQuery 1.0 and XPath 2.0 Data Model, xdt:dayTimeDuration</a>
     */
    protected DurationDayTimeImpl(final long durationInMilliseconds) {
            super(durationInMilliseconds);
            convertToCanonicalDayTime();
            // only day, hour, minute, and second should have values
            years = null;
            months = null;
    }


    /**
     * The value space of xs:dayTimeDuration is the set of fractional second values.
     * <p>
     *  <li> {@ link XMLGregorianCalendar}日期/时间XML模式10和Java表示之间的数据类型字段映射</li>
     * </ul>
     * 
     * <p>默认的开始实例由{@link GregorianCalendar}使用时代的开始来定义：{@link javautilCalendar#YEAR} = 1970,{@link javautilCalendar#MONTH}
     *  = {@link javautilCalendar# JANUARY},{@link javautilCalendar#DATE} = 1,等等这是很重要的,因为在Gregorian日历中有变化,例如
     * 闰年在不同的日子= {@link javautilCalendar#FEBRUARY},所以{@link持续时间#getDays()}可能受影响</p>。
     * 
     * @return fractional second values
     */
    public float getValue() {
        float sec = (seconds==null)?0:seconds.floatValue();
        return (((((getDays() * 24) +
                    getHours()) * 60) +
                    getMinutes())*60) +
                    sec;
    }

    private void convertToCanonicalDayTime() {

        while (getSeconds() >= 60)
        {
            seconds = seconds.subtract(BigDecimal.valueOf(60));
            minutes = BigInteger.valueOf((long) getMinutes()).add(BigInteger.ONE);
        }

        while (getMinutes() >= 60)
        {
            minutes = minutes.subtract(BigInteger.valueOf(60));
            hours = BigInteger.valueOf((long) getHours()).add(BigInteger.ONE);
        }

        while (getHours() >= 24)
        {
            hours = hours.subtract(BigInteger.valueOf(24));
            days = BigInteger.valueOf((long) getDays()).add(BigInteger.ONE);
        }
    }

}
