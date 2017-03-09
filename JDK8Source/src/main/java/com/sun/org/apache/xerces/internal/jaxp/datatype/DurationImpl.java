/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有2005 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.jaxp.datatype;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import com.sun.org.apache.xerces.internal.util.DatatypeMessageFormatter;

/**
 * <p>Immutable representation of a time span as defined in
 * the W3C XML Schema 1.0 specification.</p>
 *
 * <p>A Duration object represents a period of Gregorian time,
 * which consists of six fields (years, months, days, hours,
 * minutes, and seconds) plus a sign (+/-) field.</p>
 *
 * <p>The first five fields have non-negative (>=0) integers or null
 * (which represents that the field is not set),
 * and the seconds field has a non-negative decimal or null.
 * A negative sign indicates a negative duration.</p>
 *
 * <p>This class provides a number of methods that make it easy
 * to use for the duration datatype of XML Schema 1.0 with
 * the errata.</p>
 *
 * <h2>Order relationship</h2>
 * <p>Duration objects only have partial order, where two values A and B
 * maybe either:</p>
 * <ol>
 *  <li>A&lt;B (A is shorter than B)
 *  <li>A&gt;B (A is longer than B)
 *  <li>A==B   (A and B are of the same duration)
 *  <li>A&lt;>B (Comparison between A and B is indeterminate)
 * </ol>
 * <p>For example, 30 days cannot be meaningfully compared to one month.
 * The {@link #compare(Duration)} method implements this
 * relationship.</p>
 *
 * <p>See the {@link #isLongerThan(Duration)} method for details about
 * the order relationship among {@link Duration} objects.</p>
 *
 *
 *
 * <h2>Operations over Duration</h2>
 * <p>This class provides a set of basic arithmetic operations, such
 * as addition, subtraction and multiplication.
 * Because durations don't have total order, an operation could
 * fail for some combinations of operations. For example, you cannot
 * subtract 15 days from 1 month. See the javadoc of those methods
 * for detailed conditions where this could happen.</p>
 *
 * <p>Also, division of a duration by a number is not provided because
 * the {@link Duration} class can only deal with finite precision
 * decimal numbers. For example, one cannot represent 1 sec divided by 3.</p>
 *
 * <p>However, you could substitute a division by 3 with multiplying
 * by numbers such as 0.3 or 0.333.</p>
 *
 *
 *
 * <h2>Range of allowed values</h2>
 * <p>
 * Because some operations of {@link Duration} rely on {@link Calendar}
 * even though {@link Duration} can hold very large or very small values,
 * some of the methods may not work correctly on such {@link Duration}s.
 * The impacted methods document their dependency on {@link Calendar}.
 *
 *
 * <p>
 *  <p>在W3C XML Schema 1.0规范中定义的时间跨度的不变表示。</p>
 * 
 *  <p>持续时间对象表示格里历时间周期,其中包含六个字段(年,月,日,小时,分钟和秒)加上符号(+/-)字段。</p>
 * 
 *  <p>前五个字段具有非负(> = 0)整数或null(表示字段未设置),秒字段具有非负十进制或null。负号表示持续时间为负。</p>
 * 
 *  <p>此类提供了许多方法,使其易于在XML Schema 1.0的持续时间数据类型中使用勘误。</p>
 * 
 *  <h2>顺序关系</h2> <p>持续时间对象仅具有部分顺序,其中两个值A和B可以是：</p>
 * <ol>
 * <li> A <B(A小于B)<li> A> B(A比B长)<li> A == B(A和B具有相同的持续时间)<li> A < (A和B之间的比较是不确定的)
 * </ol>
 *  <p>例如,与一个月相比,30天不能有意义。 {@link #compare(Duration)}方法实现此关系。</p>
 * 
 *  <p>有关{@link Duration}对象之间的顺序关系的详情,请参阅{@link #isLongerThan(Duration)}方法。</p>
 * 
 *  <h2>持续时间的操作</h2> <p>此类提供了一组基本的算术运算,例如加法,减法和乘法。因为持续时间没有总顺序,所以操作可能对于操作的一些组合而失败。例如,您不能从1个月中减去15天。
 * 有关可能发生这种情况的详细情况,请参阅这些方法的javadoc。</p>。
 * 
 *  <p>此外,由于{@link Duration}类只能处理有限精度的十进制数,因此不提供按数字划分的持续时间。例如,不能表示1秒除以3。</p>
 * 
 *  <p>但是,您可以用3乘以数字(如0.3或0.333)代替除法。</p>
 * 
 *  <h2>允许值范围</h2>
 * <p>
 *  由于{@link Duration}的某些操作依赖于{@link Calendar},即使{@link Duration}可以保存非常大或非常小的值,某些方法可能无法在此类{@link Duration}
 * 上正常工作。
 * 受影响的方法记录了他们对{@link Calendar}的依赖。
 * 
 * 
 * @author <a href="mailto:Kohsuke.Kawaguchi@Sun.com">Kohsuke Kawaguchi</a>
 * @author <a href="mailto:Joseph.Fialli@Sun.com">Joseph Fialli</a>
 * @version $Revision: 1.8 $, $Date: 2010/05/19 23:20:06 $

 * @see XMLGregorianCalendar#add(Duration)
 */
class DurationImpl
        extends Duration
        implements Serializable {

    /**
     * <p>Number of Fields.</p>
     * <p>
     *  <p>字段数。</p>
     * 
     */
    private static final int FIELD_NUM = 6;

    /**
     * <p>Internal array of value Fields.</p>
     * <p>
     * <p>内部值数组字段。</p>
     * 
     */
        private static final DatatypeConstants.Field[] FIELDS = new DatatypeConstants.Field[]{
                        DatatypeConstants.YEARS,
                        DatatypeConstants.MONTHS,
                        DatatypeConstants.DAYS,
                        DatatypeConstants.HOURS,
                        DatatypeConstants.MINUTES,
                        DatatypeConstants.SECONDS
                };

                /**
                 * <p>Internal array of value Field ids.</p>
                 * <p>
                 *  <p>内部数组值字段ids。</p>
                 * 
                 */
                private static final int[] FIELD_IDS = {
                                DatatypeConstants.YEARS.getId(),
                                DatatypeConstants.MONTHS.getId(),
                                DatatypeConstants.DAYS.getId(),
                                DatatypeConstants.HOURS.getId(),
                                DatatypeConstants.MINUTES.getId(),
                                DatatypeConstants.SECONDS.getId()
                        };

    /**
     * TimeZone for GMT.
     * <p>
     *  GMT的时区。
     * 
     */
    private static final TimeZone GMT = TimeZone.getTimeZone("GMT");

        /**
         * <p>BigDecimal value of 0.</p>
         * <p>
         *  <p> BigDecimal的值为0。</p>
         * 
         */
        private static final BigDecimal ZERO = BigDecimal.valueOf((long) 0);

    /**
     * <p>Indicates the sign. -1, 0 or 1 if the duration is negative,
     * zero, or positive.</p>
     * <p>
     *  <p>表示标志。 -1,0或1,如果持续时间为负,零或正。</p>
     * 
     */
    protected int signum;

    /**
     * <p>Years of this <code>Duration</code>.</p>
     * <p>
     *  <p>这段<code> Duration </code>的年龄。</p>
     * 
     */
    /**
     * These were final since Duration is immutable. But new subclasses need
     * to be able to set after conversion. It won't break the immutable nature
     * of them since there's no other way to set new values to them
     * <p>
     *  这些是最终的,因为Duration是不可变的。但是新的子类需要能够在转换后设置。它不会打破他们的不可改变的性质,因为没有其他方法为他们设定新的价值
     * 
     */
    protected BigInteger years;

    /**
     * <p>Months of this <code>Duration</code>.</p>
     * <p>
     *  <p>此<code>持续时间</code>的月份。</p>
     * 
     */
    protected BigInteger months;

    /**
     * <p>Days of this <code>Duration</code>.</p>
     * <p>
     *  <p>此<code>持续时间</code>的天数。</p>
     * 
     */
    protected BigInteger days;

    /**
     * <p>Hours of this <code>Duration</code>.</p>
     * <p>
     *  <p>此<code>持续时间</code>的小时</p>
     * 
     */
    protected BigInteger hours;

    /**
     * <p>Minutes of this <code>Duration</code>.</p>
     * <p>
     *  <p>此<code>持续时间</code>的分钟。</p>
     * 
     */
    protected BigInteger minutes;

    /**
     * <p>Seconds of this <code>Duration</code>.</p>
     * <p>
     *  <p> <code> Duration </code>的秒数。</p>
     * 
     */
    protected BigDecimal seconds;

        /**
         * Returns the sign of this duration in -1,0, or 1.
         *
         * <p>
         *  以-1,0或1返回此持续时间的符号。
         * 
         * 
         * @return
         *      -1 if this duration is negative, 0 if the duration is zero,
         *      and 1 if the duration is postive.
         */
        public int getSign() {

                return signum;
        }

        /**
         * TODO: Javadoc
         * <p>
         *  TODO：Javadoc
         * 
         * 
         * @param isPositive Sign.
         *
         * @return 1 if positive, else -1.
         */
    protected int calcSignum(boolean isPositive) {
        if ((years == null || years.signum() == 0)
            && (months == null || months.signum() == 0)
            && (days == null || days.signum() == 0)
            && (hours == null || hours.signum() == 0)
            && (minutes == null || minutes.signum() == 0)
            && (seconds == null || seconds.signum() == 0)) {
            return 0;
            }

            if (isPositive) {
                return 1;
            } else {
                return -1;
            }

    }

    /**
     * <p>Constructs a new Duration object by specifying each field individually.</p>
     *
     * <p>All the parameters are optional as long as at least one field is present.
     * If specified, parameters have to be zero or positive.</p>
     *
     * <p>
     *  <p>通过单独指定每个字段构造一个新的Duration对象。</p>
     * 
     *  <p>只要至少有一个字段存在,所有参数都是可选的。如果指定,参数必须为零或正数。</p>
     * 
     * 
     * @param isPositive Set to <code>false</code> to create a negative duration. When the length
     *   of the duration is zero, this parameter will be ignored.
     * @param years of this <code>Duration</code>
     * @param months of this <code>Duration</code>
     * @param days of this <code>Duration</code>
     * @param hours of this <code>Duration</code>
     * @param minutes of this <code>Duration</code>
     * @param seconds of this <code>Duration</code>
     *
     * @throws IllegalArgumentException
     *    If years, months, days, hours, minutes and
     *    seconds parameters are all <code>null</code>. Or if any
     *    of those parameters are negative.
     */
    protected DurationImpl(
        boolean isPositive,
        BigInteger years,
        BigInteger months,
        BigInteger days,
        BigInteger hours,
        BigInteger minutes,
        BigDecimal seconds) {

        this.years = years;
        this.months = months;
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;

        this.signum = calcSignum(isPositive);

        // sanity check
        if (years == null
            && months == null
            && days == null
            && hours == null
            && minutes == null
            && seconds == null) {
            throw new IllegalArgumentException(
            //"all the fields are null"
            DatatypeMessageFormatter.formatMessage(null, "AllFieldsNull", null)
            );
        }
        testNonNegative(years, DatatypeConstants.YEARS);
        testNonNegative(months, DatatypeConstants.MONTHS);
        testNonNegative(days, DatatypeConstants.DAYS);
        testNonNegative(hours, DatatypeConstants.HOURS);
        testNonNegative(minutes, DatatypeConstants.MINUTES);
        testNonNegative(seconds, DatatypeConstants.SECONDS);
    }

    /**
     * <p>Makes sure that the given number is non-negative. If it is not,
     * throw {@link IllegalArgumentException}.</p>
     *
     * <p>
     *  <p>确保给定的数字是非负数。如果不是,则抛出{@link IllegalArgumentException}。</p>
     * 
     * 
     * @param n Number to test.
     * @param f Field to test.
     */
    protected static void testNonNegative(BigInteger n, DatatypeConstants.Field f) {
        if (n != null && n.signum() < 0) {
            throw new IllegalArgumentException(
                DatatypeMessageFormatter.formatMessage(null, "NegativeField", new Object[]{f.toString()})
            );
        }
    }

    /**
     * <p>Makes sure that the given number is non-negative. If it is not,
     * throw {@link IllegalArgumentException}.</p>
     *
     * <p>
     *  <p>确保给定的数字是非负数。如果不是,则抛出{@link IllegalArgumentException}。</p>
     * 
     * 
     * @param n Number to test.
     * @param f Field to test.
     */
    protected static void testNonNegative(BigDecimal n, DatatypeConstants.Field f) {
        if (n != null && n.signum() < 0) {

            throw new IllegalArgumentException(
                DatatypeMessageFormatter.formatMessage(null, "NegativeField", new Object[]{f.toString()})
            );
        }
    }

    /**
     * <p>Constructs a new Duration object by specifying each field
     * individually.</p>
     *
     * <p>This method is functionally equivalent to
     * invoking another constructor by wrapping
     * all non-zero parameters into {@link BigInteger} and {@link BigDecimal}.
     * Zero value of int parameter is equivalent of null value of
     * the corresponding field.</p>
     *
     * <p>
     *  <p>通过单独指定每个字段构造一个新的Duration对象。</p>
     * 
     * <p>此方法在功能上等同于通过将所有非零参数包装到{@link BigInteger}和{@link BigDecimal}中来调用另一个构造函数。 int参数的零值等于相应字段的空值。</p>
     * 
     * 
     * @see #DurationImpl(boolean, BigInteger, BigInteger, BigInteger, BigInteger,
     *   BigInteger, BigDecimal)
     */
    protected DurationImpl(
        final boolean isPositive,
        final int years,
        final int months,
        final int days,
        final int hours,
        final int minutes,
        final int seconds) {
        this(
            isPositive,
            wrap(years),
            wrap(months),
            wrap(days),
            wrap(hours),
            wrap(minutes),
            seconds != DatatypeConstants.FIELD_UNDEFINED ? new BigDecimal(String.valueOf(seconds)) : null);
    }

        /**
         * TODO: Javadoc
         *
         * <p>
         *  TODO：Javadoc
         * 
         * 
         * @param i int to convert to BigInteger.
         *
         * @return BigInteger representation of int.
         */
    protected static BigInteger wrap(final int i) {

        // field may not be set
        if (i == DatatypeConstants.FIELD_UNDEFINED) {
                return null;
        }

        // int -> BigInteger
        return new BigInteger(String.valueOf(i));
    }

    /**
     * <p>Constructs a new Duration object by specifying the duration
     * in milliseconds.</p>
     *
     * <p>
     *  <p>通过指定持续时间(以毫秒为单位)构造一个新的Duration对象。</p>
     * 
     * 
     * @param durationInMilliSeconds
     *      The length of the duration in milliseconds.
     */
    protected DurationImpl(final long durationInMilliSeconds) {

        long l = durationInMilliSeconds;

        if (l > 0) {
            signum = 1;
        } else if (l < 0) {
            signum = -1;
            if (l == 0x8000000000000000L) {
                // negating 0x8000000000000000L causes an overflow
                l++;
            }
            l *= -1;
        } else {
            signum = 0;
        }

        // let GregorianCalendar do the heavy lifting
        GregorianCalendar gregorianCalendar = new GregorianCalendar(GMT);

        // duration is the offset from the Epoch
        gregorianCalendar.setTimeInMillis(l);

        // now find out how much each field has changed
        long int2long = 0L;

        // years
        int2long = gregorianCalendar.get(Calendar.YEAR) - 1970;
        this.years = BigInteger.valueOf(int2long);

        // months
        int2long = gregorianCalendar.get(Calendar.MONTH);
        this.months = BigInteger.valueOf(int2long);

        // days
        int2long = gregorianCalendar.get(Calendar.DAY_OF_MONTH) - 1;
        this.days = BigInteger.valueOf(int2long);

        // hours
        int2long = gregorianCalendar.get(Calendar.HOUR_OF_DAY);
        this.hours = BigInteger.valueOf(int2long);

        // minutes
        int2long = gregorianCalendar.get(Calendar.MINUTE);
        this.minutes = BigInteger.valueOf(int2long);

        // seconds & milliseconds
        int2long = (gregorianCalendar.get(Calendar.SECOND) * 1000)
                    + gregorianCalendar.get(Calendar.MILLISECOND);
        this.seconds = BigDecimal.valueOf(int2long, 3);
    }

    /**
     * Constructs a new Duration object by
     * parsing its string representation
     * "PnYnMnDTnHnMnS" as defined in XML Schema 1.0 section 3.2.6.1.
     *
     * <p>
     * The string representation may not have any leading
     * and trailing whitespaces.
     *
     * <p>
     * For example, this method parses strings like
     * "P1D" (1 day), "-PT100S" (-100 sec.), "P1DT12H" (1 days and 12 hours).
     *
     * <p>
     * The parsing is done field by field so that
     * the following holds for any lexically correct string x:
     * <pre>
     * new Duration(x).toString().equals(x)
     * </pre>
     *
     * Returns a non-null valid duration object that holds the value
     * indicated by the lexicalRepresentation parameter.
     *
     * <p>
     *  通过解析XML模式1.0第3.2.6.1节中定义的字符串表示"PnYnMnDTnHnMnS"构造一个新的Duration对象。
     * 
     * <p>
     *  字符串表示可以不具有任何前导和尾随空格。
     * 
     * <p>
     *  例如,此方法解析类似"P1D"(1天),"-PT100S"(约100秒),"P1DT12H"(1天和12小时)的字符串。
     * 
     * <p>
     *  解析是逐字段完成的,因此对于任何词法正确的字符串x,以下条件成立：
     * <pre>
     *  new Duration(x).toString()。equals(x)
     * </pre>
     * 
     *  返回一个非空的有效持续时间对象,该对象保存由lexicalRepresentation参数指示的值。
     * 
     * 
     * @param lexicalRepresentation
     *      Lexical representation of a duration.
     * @throws IllegalArgumentException
     *      If the given string does not conform to the aforementioned
     *      specification.
     * @throws NullPointerException
     *      If the given string is null.
     */
    protected DurationImpl(String lexicalRepresentation)
        throws IllegalArgumentException {
        // only if I could use the JDK1.4 regular expression ....

        final String s = lexicalRepresentation;
        boolean positive;
        int[] idx = new int[1];
        int length = s.length();
        boolean timeRequired = false;

        if (lexicalRepresentation == null) {
            throw new NullPointerException();
        }

        idx[0] = 0;
        if (length != idx[0] && s.charAt(idx[0]) == '-') {
            idx[0]++;
            positive = false;
        } else {
            positive = true;
        }

        if (length != idx[0] && s.charAt(idx[0]++) != 'P') {
            throw new IllegalArgumentException(s); //,idx[0]-1);
        }


        // phase 1: chop the string into chunks
        // (where a chunk is '<number><a symbol>'
        //--------------------------------------
        int dateLen = 0;
        String[] dateParts = new String[3];
        int[] datePartsIndex = new int[3];
        while (length != idx[0]
            && isDigit(s.charAt(idx[0]))
            && dateLen < 3) {
            datePartsIndex[dateLen] = idx[0];
            dateParts[dateLen++] = parsePiece(s, idx);
        }

        if (length != idx[0]) {
            if (s.charAt(idx[0]++) == 'T') {
                timeRequired = true;
            } else {
                throw new IllegalArgumentException(s); // ,idx[0]-1);
            }
        }

        int timeLen = 0;
        String[] timeParts = new String[3];
        int[] timePartsIndex = new int[3];
        while (length != idx[0]
            && isDigitOrPeriod(s.charAt(idx[0]))
            && timeLen < 3) {
            timePartsIndex[timeLen] = idx[0];
            timeParts[timeLen++] = parsePiece(s, idx);
        }

        if (timeRequired && timeLen == 0) {
            throw new IllegalArgumentException(s); // ,idx[0]);
        }

        if (length != idx[0]) {
            throw new IllegalArgumentException(s); // ,idx[0]);
        }
        if (dateLen == 0 && timeLen == 0) {
            throw new IllegalArgumentException(s); // ,idx[0]);
        }

        // phase 2: check the ordering of chunks
        //--------------------------------------
        organizeParts(s, dateParts, datePartsIndex, dateLen, "YMD");
        organizeParts(s, timeParts, timePartsIndex, timeLen, "HMS");

        // parse into numbers
        years = parseBigInteger(s, dateParts[0], datePartsIndex[0]);
        months = parseBigInteger(s, dateParts[1], datePartsIndex[1]);
        days = parseBigInteger(s, dateParts[2], datePartsIndex[2]);
        hours = parseBigInteger(s, timeParts[0], timePartsIndex[0]);
        minutes = parseBigInteger(s, timeParts[1], timePartsIndex[1]);
        seconds = parseBigDecimal(s, timeParts[2], timePartsIndex[2]);
        signum = calcSignum(positive);
    }


    /**
     * TODO: Javadoc
     *
     * <p>
     *  TODO：Javadoc
     * 
     * 
     * @param ch char to test.
     *
     * @return true if ch is a digit, else false.
     */
    private static boolean isDigit(char ch) {
        return '0' <= ch && ch <= '9';
    }

    /**
     * TODO: Javadoc
     *
     * <p>
     *  TODO：Javadoc
     * 
     * 
     * @param ch to test.
     *
     * @return true if ch is a digit or a period, else false.
     */
    private static boolean isDigitOrPeriod(char ch) {
        return isDigit(ch) || ch == '.';
    }

    /**
     * TODO: Javadoc
     *
     * <p>
     *  TODO：Javadoc
     * 
     * 
     * @param whole String to parse.
     * @param idx TODO: ???
     *
     * @return Result of parsing.
     *
     * @throws IllegalArgumentException If whole cannot be parsed.
     */
    private static String parsePiece(String whole, int[] idx)
        throws IllegalArgumentException {
        int start = idx[0];
        while (idx[0] < whole.length()
            && isDigitOrPeriod(whole.charAt(idx[0]))) {
            idx[0]++;
            }
        if (idx[0] == whole.length()) {
            throw new IllegalArgumentException(whole); // ,idx[0]);
        }

        idx[0]++;

        return whole.substring(start, idx[0]);
    }

    /**
     * TODO: Javadoc.
     *
     * <p>
     *  TODO：Javadoc。
     * 
     * 
     * @param whole TODO: ???
     * @param parts TODO: ???
     * @param partsIndex TODO: ???
     * @param len TODO: ???
     * @param tokens TODO: ???
     *
     * @throws IllegalArgumentException TODO: ???
     */
    private static void organizeParts(
        String whole,
        String[] parts,
        int[] partsIndex,
        int len,
        String tokens)
        throws IllegalArgumentException {

        int idx = tokens.length();
        for (int i = len - 1; i >= 0; i--) {
            int nidx =
                tokens.lastIndexOf(
                    parts[i].charAt(parts[i].length() - 1),
                    idx - 1);
            if (nidx == -1) {
                throw new IllegalArgumentException(whole);
                // ,partsIndex[i]+parts[i].length()-1);
            }

            for (int j = nidx + 1; j < idx; j++) {
                parts[j] = null;
            }
            idx = nidx;
            parts[idx] = parts[i];
            partsIndex[idx] = partsIndex[i];
        }
        for (idx--; idx >= 0; idx--) {
            parts[idx] = null;
        }
    }

    /**
     * TODO: Javadoc
     *
     * <p>
     *  TODO：Javadoc
     * 
     * 
     * @param whole TODO: ???.
     * @param part TODO: ???.
     * @param index TODO: ???.
     *
     * @return TODO: ???.
     *
     * @throws IllegalArgumentException TODO: ???.
     */
    private static BigInteger parseBigInteger(
        String whole,
        String part,
        int index)
        throws IllegalArgumentException {
        if (part == null) {
            return null;
        }
        part = part.substring(0, part.length() - 1);
        //        try {
        return new BigInteger(part);
        //        } catch( NumberFormatException e ) {
        //            throw new ParseException( whole, index );
        //        }
    }

    /**
     * TODO: Javadoc.
     *
     * <p>
     *  托多：Javadoc。
     * 
     * 
     * @param whole TODO: ???.
     * @param part TODO: ???.
     * @param index TODO: ???.
     *
     * @return TODO: ???.
     *
     * @throws IllegalArgumentException TODO: ???.
     */
    private static BigDecimal parseBigDecimal(
        String whole,
        String part,
        int index)
        throws IllegalArgumentException {
        if (part == null) {
            return null;
        }
        part = part.substring(0, part.length() - 1);
        // NumberFormatException is IllegalArgumentException
        //        try {
        return new BigDecimal(part);
        //        } catch( NumberFormatException e ) {
        //            throw new ParseException( whole, index );
        //        }
    }

    /**
     * <p>Four constants defined for the comparison of durations.</p>
     * <p>
     *  <p>为比较持续时间定义的四个常数。</p>
     * 
     */
    private static final XMLGregorianCalendar[] TEST_POINTS = new XMLGregorianCalendar[] {
        XMLGregorianCalendarImpl.parse("1696-09-01T00:00:00Z"),
        XMLGregorianCalendarImpl.parse("1697-02-01T00:00:00Z"),
        XMLGregorianCalendarImpl.parse("1903-03-01T00:00:00Z"),
        XMLGregorianCalendarImpl.parse("1903-07-01T00:00:00Z")
    };

        /**
         * <p>Partial order relation comparison with this <code>Duration</code> instance.</p>
         *
         * <p>Comparison result must be in accordance with
         * <a href="http://www.w3.org/TR/xmlschema-2/#duration-order">W3C XML Schema 1.0 Part 2, Section 3.2.7.6.2,
         * <i>Order relation on duration</i></a>.</p>
         *
         * <p>Return:</p>
         * <ul>
         *   <li>{@link DatatypeConstants#LESSER} if this <code>Duration</code> is shorter than <code>duration</code> parameter</li>
         *   <li>{@link DatatypeConstants#EQUAL} if this <code>Duration</code> is equal to <code>duration</code> parameter</li>
         *   <li>{@link DatatypeConstants#GREATER} if this <code>Duration</code> is longer than <code>duration</code> parameter</li>
         *   <li>{@link DatatypeConstants#INDETERMINATE} if a conclusive partial order relation cannot be determined</li>
         * </ul>
         *
         * <p>
         *  <p>与此<code> Duration </code>实例的部分顺序关系比较。</p>
         * 
         *  <p>比较结果必须符合<a href="http://www.w3.org/TR/xmlschema-2/#duration-order"> W3C XML Schema 1.0第2部分,第3.2.7
         * .6.2节,<i>订单关系持续时间</i> </a>。
         * </p>。
         * 
         *  <p>返回：</p>
         * <ul>
         * <li> {@ link DatatypeConstants#EQUAL}如果此<code> Duration </code>小于<code> duration </code>参数</li>如果此<code>
         * 持续时间</code>长于<code>持续时间</code>,则持续时间</code>等于<code> duration </code>参数</li> / code>参数</li> <li> {@ link DatatypeConstants#INDETERMINATE}
         * 如果无法确定结论性部分订单关系</li>。
         * </ul>
         * 
         * 
         * @param duration to compare
         *
         * @return the relationship between <code>this</code> <code>Duration</code>and <code>duration</code> parameter as
         *   {@link DatatypeConstants#LESSER}, {@link DatatypeConstants#EQUAL}, {@link DatatypeConstants#GREATER}
         *   or {@link DatatypeConstants#INDETERMINATE}.
         *
         * @throws UnsupportedOperationException If the underlying implementation
         *   cannot reasonably process the request, e.g. W3C XML Schema allows for
         *   arbitrarily large/small/precise values, the request may be beyond the
         *   implementations capability.
         * @throws NullPointerException if <code>duration</code> is <code>null</code>.
         *
         * @see #isShorterThan(Duration)
         * @see #isLongerThan(Duration)
         */
    public int compare(Duration rhs) {

        BigInteger maxintAsBigInteger = BigInteger.valueOf((long) Integer.MAX_VALUE);
        BigInteger minintAsBigInteger = BigInteger.valueOf((long) Integer.MIN_VALUE);

        // check for fields that are too large in this Duration
        if (years != null && years.compareTo(maxintAsBigInteger) == 1) {
            throw new UnsupportedOperationException(
                        DatatypeMessageFormatter.formatMessage(null, "TooLarge",
                            new Object[]{this.getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.YEARS.toString(), years.toString()})
                                        //this.getClass().getName() + "#compare(Duration duration)"
                                                //+ " years too large to be supported by this implementation "
                                                //+ years.toString()
                                        );
        }
        if (months != null && months.compareTo(maxintAsBigInteger) == 1) {
                throw new UnsupportedOperationException(
                        DatatypeMessageFormatter.formatMessage(null, "TooLarge",
                            new Object[]{this.getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.MONTHS.toString(), months.toString()})

                                        //this.getClass().getName() + "#compare(Duration duration)"
                                                //+ " months too large to be supported by this implementation "
                                                //+ months.toString()
                                        );
        }
        if (days != null && days.compareTo(maxintAsBigInteger) == 1) {
                throw new UnsupportedOperationException(
                        DatatypeMessageFormatter.formatMessage(null, "TooLarge",
                            new Object[]{this.getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.DAYS.toString(), days.toString()})

                                        //this.getClass().getName() + "#compare(Duration duration)"
                                                //+ " days too large to be supported by this implementation "
                                                //+ days.toString()
                                        );
        }
        if (hours != null && hours.compareTo(maxintAsBigInteger) == 1) {
                throw new UnsupportedOperationException(
                        DatatypeMessageFormatter.formatMessage(null, "TooLarge",
                            new Object[]{this.getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.HOURS.toString(), hours.toString()})

                                        //this.getClass().getName() + "#compare(Duration duration)"
                                                //+ " hours too large to be supported by this implementation "
                                                //+ hours.toString()
                                        );
        }
        if (minutes != null && minutes.compareTo(maxintAsBigInteger) == 1) {
                throw new UnsupportedOperationException(
                        DatatypeMessageFormatter.formatMessage(null, "TooLarge",
                            new Object[]{this.getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.MINUTES.toString(), minutes.toString()})

                                        //this.getClass().getName() + "#compare(Duration duration)"
                                                //+ " minutes too large to be supported by this implementation "
                                                //+ minutes.toString()
                                        );
        }
        if (seconds != null && seconds.toBigInteger().compareTo(maxintAsBigInteger) == 1) {
                throw new UnsupportedOperationException(
                        DatatypeMessageFormatter.formatMessage(null, "TooLarge",
                            new Object[]{this.getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.SECONDS.toString(), seconds.toString()})

                                        //this.getClass().getName() + "#compare(Duration duration)"
                                                //+ " seconds too large to be supported by this implementation "
                                                //+ seconds.toString()
                                        );
        }

        // check for fields that are too large in rhs Duration
        BigInteger rhsYears = (BigInteger) rhs.getField(DatatypeConstants.YEARS);
        if (rhsYears != null && rhsYears.compareTo(maxintAsBigInteger) == 1) {
                throw new UnsupportedOperationException(
                        DatatypeMessageFormatter.formatMessage(null, "TooLarge",
                            new Object[]{this.getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.YEARS.toString(), rhsYears.toString()})

                                        //this.getClass().getName() + "#compare(Duration duration)"
                                                //+ " years too large to be supported by this implementation "
                                                //+ rhsYears.toString()
                                        );
        }
        BigInteger rhsMonths = (BigInteger) rhs.getField(DatatypeConstants.MONTHS);
        if (rhsMonths != null && rhsMonths.compareTo(maxintAsBigInteger) == 1) {
                throw new UnsupportedOperationException(
                        DatatypeMessageFormatter.formatMessage(null, "TooLarge",
                            new Object[]{this.getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.MONTHS.toString(), rhsMonths.toString()})

                                        //this.getClass().getName() + "#compare(Duration duration)"
                                                //+ " months too large to be supported by this implementation "
                                                //+ rhsMonths.toString()
                                        );
        }
        BigInteger rhsDays = (BigInteger) rhs.getField(DatatypeConstants.DAYS);
        if (rhsDays != null && rhsDays.compareTo(maxintAsBigInteger) == 1) {
                throw new UnsupportedOperationException(
                        DatatypeMessageFormatter.formatMessage(null, "TooLarge",
                            new Object[]{this.getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.DAYS.toString(), rhsDays.toString()})

                                        //this.getClass().getName() + "#compare(Duration duration)"
                                                //+ " days too large to be supported by this implementation "
                                                //+ rhsDays.toString()
                                        );
        }
        BigInteger rhsHours = (BigInteger) rhs.getField(DatatypeConstants.HOURS);
        if (rhsHours != null && rhsHours.compareTo(maxintAsBigInteger) == 1) {
                throw new UnsupportedOperationException(
                        DatatypeMessageFormatter.formatMessage(null, "TooLarge",
                            new Object[]{this.getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.HOURS.toString(), rhsHours.toString()})

                                        //this.getClass().getName() + "#compare(Duration duration)"
                                                //+ " hours too large to be supported by this implementation "
                                                //+ rhsHours.toString()
                                        );
        }
        BigInteger rhsMinutes = (BigInteger) rhs.getField(DatatypeConstants.MINUTES);
        if (rhsMinutes != null && rhsMinutes.compareTo(maxintAsBigInteger) == 1) {
                throw new UnsupportedOperationException(
                        DatatypeMessageFormatter.formatMessage(null, "TooLarge",
                            new Object[]{this.getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.MINUTES.toString(), rhsMinutes.toString()})

                                        //this.getClass().getName() + "#compare(Duration duration)"
                                                //+ " minutes too large to be supported by this implementation "
                                                //+ rhsMinutes.toString()
                                        );
        }
        BigDecimal rhsSecondsAsBigDecimal = (BigDecimal) rhs.getField(DatatypeConstants.SECONDS);
        BigInteger rhsSeconds = null;
        if ( rhsSecondsAsBigDecimal != null ) {
                rhsSeconds =  rhsSecondsAsBigDecimal.toBigInteger();
        }
        if (rhsSeconds != null && rhsSeconds.compareTo(maxintAsBigInteger) == 1) {
                throw new UnsupportedOperationException(
                        DatatypeMessageFormatter.formatMessage(null, "TooLarge",
                            new Object[]{this.getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.SECONDS.toString(), rhsSeconds.toString()})

                                        //this.getClass().getName() + "#compare(Duration duration)"
                                                //+ " seconds too large to be supported by this implementation "
                                                //+ rhsSeconds.toString()
                                        );
        }

        // turn this Duration into a GregorianCalendar
        GregorianCalendar lhsCalendar = new GregorianCalendar(
                        1970,
                                1,
                                1,
                                0,
                                0,
                                0);
                lhsCalendar.add(GregorianCalendar.YEAR, getYears() * getSign());
                lhsCalendar.add(GregorianCalendar.MONTH, getMonths() * getSign());
                lhsCalendar.add(GregorianCalendar.DAY_OF_YEAR, getDays() * getSign());
                lhsCalendar.add(GregorianCalendar.HOUR_OF_DAY, getHours() * getSign());
                lhsCalendar.add(GregorianCalendar.MINUTE, getMinutes() * getSign());
                lhsCalendar.add(GregorianCalendar.SECOND, getSeconds() * getSign());

                // turn compare Duration into a GregorianCalendar
        GregorianCalendar rhsCalendar = new GregorianCalendar(
                                1970,
                                1,
                                1,
                                0,
                                0,
                                0);
                rhsCalendar.add(GregorianCalendar.YEAR, rhs.getYears() * rhs.getSign());
                rhsCalendar.add(GregorianCalendar.MONTH, rhs.getMonths() * rhs.getSign());
                rhsCalendar.add(GregorianCalendar.DAY_OF_YEAR, rhs.getDays() * rhs.getSign());
                rhsCalendar.add(GregorianCalendar.HOUR_OF_DAY, rhs.getHours() * rhs.getSign());
                rhsCalendar.add(GregorianCalendar.MINUTE, rhs.getMinutes() * rhs.getSign());
                rhsCalendar.add(GregorianCalendar.SECOND, rhs.getSeconds() * rhs.getSign());


                if (lhsCalendar.equals(rhsCalendar)) {
                        return DatatypeConstants.EQUAL;
                }

                return compareDates(this, rhs);
    }

    /**
     * Compares 2 given durations. (refer to W3C Schema Datatypes "3.2.6 duration")
     *
     * <p>
     *  比较2个给定的持续时间。 (请参阅W3C模式数据类型"3.2.6持续时间")
     * 
     * 
     * @param duration1  Unnormalized duration
     * @param duration2  Unnormalized duration
     * @return INDETERMINATE if the order relationship between date1 and date2 is indeterminate.
     * EQUAL if the order relation between date1 and date2 is EQUAL.
     * If the strict parameter is true, return LESS_THAN if date1 is less than date2 and
     * return GREATER_THAN if date1 is greater than date2.
     * If the strict parameter is false, return LESS_THAN if date1 is less than OR equal to date2 and
     * return GREATER_THAN if date1 is greater than OR equal to date2
     */
    private int compareDates(Duration duration1, Duration duration2) {

        int resultA = DatatypeConstants.INDETERMINATE;
        int resultB = DatatypeConstants.INDETERMINATE;

        XMLGregorianCalendar tempA = (XMLGregorianCalendar)TEST_POINTS[0].clone();
        XMLGregorianCalendar tempB = (XMLGregorianCalendar)TEST_POINTS[0].clone();

        //long comparison algorithm is required
        tempA.add(duration1);
        tempB.add(duration2);
        resultA =  tempA.compare(tempB);
        if ( resultA == DatatypeConstants.INDETERMINATE ) {
            return DatatypeConstants.INDETERMINATE;
        }

        tempA = (XMLGregorianCalendar)TEST_POINTS[1].clone();
        tempB = (XMLGregorianCalendar)TEST_POINTS[1].clone();

        tempA.add(duration1);
        tempB.add(duration2);
        resultB = tempA.compare(tempB);
        resultA = compareResults(resultA, resultB);
        if (resultA == DatatypeConstants.INDETERMINATE) {
            return DatatypeConstants.INDETERMINATE;
        }

        tempA = (XMLGregorianCalendar)TEST_POINTS[2].clone();
        tempB = (XMLGregorianCalendar)TEST_POINTS[2].clone();

        tempA.add(duration1);
        tempB.add(duration2);
        resultB = tempA.compare(tempB);
        resultA = compareResults(resultA, resultB);
        if (resultA == DatatypeConstants.INDETERMINATE) {
            return DatatypeConstants.INDETERMINATE;
        }

        tempA = (XMLGregorianCalendar)TEST_POINTS[3].clone();
        tempB = (XMLGregorianCalendar)TEST_POINTS[3].clone();

        tempA.add(duration1);
        tempB.add(duration2);
        resultB = tempA.compare(tempB);
        resultA = compareResults(resultA, resultB);

        return resultA;
    }

    private int compareResults(int resultA, int resultB){

      if ( resultB == DatatypeConstants.INDETERMINATE ) {
            return DatatypeConstants.INDETERMINATE;
        }
        else if ( resultA!=resultB) {
            return DatatypeConstants.INDETERMINATE;
        }
        return resultA;
    }

    /**
     * Returns a hash code consistent with the definition of the equals method.
     *
     * <p>
     *  返回与equals方法的定义一致的哈希代码。
     * 
     * 
     * @see Object#hashCode()
     */
    public int hashCode() {
        // component wise hash is not correct because 1day = 24hours
        Calendar cal = TEST_POINTS[0].toGregorianCalendar();
        this.addTo(cal);
        return (int) getCalendarTimeInMillis(cal);
    }

    /**
     * Returns a string representation of this duration object.
     *
     * <p>
     * The result is formatter according to the XML Schema 1.0
     * spec and can be always parsed back later into the
     * equivalent duration object by
     * the {@link #DurationImpl(String)} constructor.
     *
     * <p>
     * Formally, the following holds for any {@link Duration}
     * object x.
     * <pre>
     * new Duration(x.toString()).equals(x)
     * </pre>
     *
     * <p>
     *  返回此持续时间对象的字符串表示形式。
     * 
     * <p>
     *  结果是根据XML Schema 1.0规范的格式化程序,并且可以随后通过{@link #DurationImpl(String)}构造函数解析为等效持续时间对象。
     * 
     * <p>
     *  形式上,以下适用于任何{@link Duration}对象x。
     * <pre>
     *  new Duration(x.toString())。equals(x)
     * </pre>
     * 
     * 
     * @return
     *      Always return a non-null valid String object.
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        if (signum < 0) {
            buf.append('-');
        }
        buf.append('P');

        if (years != null) {
            buf.append(years + "Y");
        }
        if (months != null) {
            buf.append(months + "M");
        }
        if (days != null) {
            buf.append(days + "D");
        }

        if (hours != null || minutes != null || seconds != null) {
            buf.append('T');
            if (hours != null) {
                buf.append(hours + "H");
            }
            if (minutes != null) {
                buf.append(minutes + "M");
            }
            if (seconds != null) {
                buf.append(toString(seconds) + "S");
            }
        }

        return buf.toString();
    }

    /**
     * <p>Turns {@link BigDecimal} to a string representation.</p>
     *
     * <p>Due to a behavior change in the {@link BigDecimal#toString()}
     * method in JDK1.5, this had to be implemented here.</p>
     *
     * <p>
     *  <p>将{@link BigDecimal}改为字符串表示。</p>
     * 
     *  <p>由于JDK1.5中的{@link BigDecimal#toString()}方法中的行为更改,必须在此处实现。</p>
     * 
     * 
     * @param bd <code>BigDecimal</code> to format as a <code>String</code>
     *
     * @return  <code>String</code> representation of <code>BigDecimal</code>
     */
    private String toString(BigDecimal bd) {
        String intString = bd.unscaledValue().toString();
        int scale = bd.scale();

        if (scale == 0) {
            return intString;
        }

        /* Insert decimal point */
        StringBuffer buf;
        int insertionPoint = intString.length() - scale;
        if (insertionPoint == 0) { /* Point goes right before intVal */
            return "0." + intString;
        } else if (insertionPoint > 0) { /* Point goes inside intVal */
            buf = new StringBuffer(intString);
            buf.insert(insertionPoint, '.');
        } else { /* We must insert zeros between point and intVal */
            buf = new StringBuffer(3 - insertionPoint + intString.length());
            buf.append("0.");
            for (int i = 0; i < -insertionPoint; i++) {
                buf.append('0');
            }
            buf.append(intString);
        }
        return buf.toString();
    }

    /**
     * Checks if a field is set.
     *
     * A field of a duration object may or may not be present.
     * This method can be used to test if a field is present.
     *
     * <p>
     *  检查是否设置了字段。
     * 
     *  持续时间对象的字段可以存在或可以不存在。此方法可用于测试是否存在字段。
     * 
     * 
     * @param field
     *      one of the six Field constants (YEARS,MONTHS,DAYS,HOURS,
     *      MINUTES, or SECONDS.)
     * @return
     *      true if the field is present. false if not.
     *
     * @throws NullPointerException
     *      If the field parameter is null.
     */
    public boolean isSet(DatatypeConstants.Field field) {

        if (field == null) {
            String methodName = "javax.xml.datatype.Duration" + "#isSet(DatatypeConstants.Field field)" ;
                throw new NullPointerException(
                //"cannot be called with field == null"
                DatatypeMessageFormatter.formatMessage(null, "FieldCannotBeNull", new Object[]{methodName})
                );
        }

        if (field == DatatypeConstants.YEARS) {
                        return years != null;
        }

                if (field == DatatypeConstants.MONTHS) {
                        return months != null;
                }

                if (field == DatatypeConstants.DAYS) {
                        return days != null;
                }

                if (field == DatatypeConstants.HOURS) {
                        return hours != null;
                }

                if (field == DatatypeConstants.MINUTES) {
                        return minutes != null;
                }

                if (field == DatatypeConstants.SECONDS) {
                        return seconds != null;
                }
        String methodName = "javax.xml.datatype.Duration" + "#isSet(DatatypeConstants.Field field)";

        throw new IllegalArgumentException(
            DatatypeMessageFormatter.formatMessage(null,"UnknownField", new Object[]{methodName, field.toString()})
                );

    }

    /**
     * Gets the value of a field.
     *
     * Fields of a duration object may contain arbitrary large value.
     * Therefore this method is designed to return a {@link Number} object.
     *
     * In case of YEARS, MONTHS, DAYS, HOURS, and MINUTES, the returned
     * number will be a non-negative integer. In case of seconds,
     * the returned number may be a non-negative decimal value.
     *
     * <p>
     *  获取字段的值。
     * 
     *  持续时间对象的字段可能包含任意大的值。因此,此方法旨在返回一个{@link Number}对象。
     * 
     * 在YEARS,MONTHS,DAYS,HOURS和MINUTES的情况下,返回的数字将是一个非负整数。在秒的情况下,返回的数字可以是非负十进制值。
     * 
     * 
     * @param field
     *      one of the six Field constants (YEARS,MONTHS,DAYS,HOURS,
     *      MINUTES, or SECONDS.)
     * @return
     *      If the specified field is present, this method returns
     *      a non-null non-negative {@link Number} object that
     *      represents its value. If it is not present, return null.
     *      For YEARS, MONTHS, DAYS, HOURS, and MINUTES, this method
     *      returns a {@link BigInteger} object. For SECONDS, this
     *      method returns a {@link BigDecimal}.
     *
     * @throws NullPointerException
     *      If the field parameter is null.
     */
    public Number getField(DatatypeConstants.Field field) {

                if (field == null) {
            String methodName = "javax.xml.datatype.Duration" + "#isSet(DatatypeConstants.Field field) " ;

                        throw new NullPointerException(
                DatatypeMessageFormatter.formatMessage(null,"FieldCannotBeNull", new Object[]{methodName})
                );
                }

                if (field == DatatypeConstants.YEARS) {
                        return years;
                }

                if (field == DatatypeConstants.MONTHS) {
                        return months;
                }

                if (field == DatatypeConstants.DAYS) {
                        return days;
                }

                if (field == DatatypeConstants.HOURS) {
                        return hours;
                }

                if (field == DatatypeConstants.MINUTES) {
                        return minutes;
                }

                if (field == DatatypeConstants.SECONDS) {
                        return seconds;
                }
                /**
                throw new IllegalArgumentException(
                        "javax.xml.datatype.Duration"
                        + "#(getSet(DatatypeConstants.Field field) called with an unknown field: "
                        + field.toString()
                );
                /* <p>
                /*  抛出新的IllegalArgumentException("javax.xml.datatype.Duration"+"#(getSet(DatatypeConstants.Field字段)调用一个未
                /* 知字段："+ field.toString());。
                /* 
        */
        String methodName = "javax.xml.datatype.Duration" + "#(getSet(DatatypeConstants.Field field)";

        throw new IllegalArgumentException(
            DatatypeMessageFormatter.formatMessage(null,"UnknownField", new Object[]{methodName, field.toString()})
                );

    }

    /**
     * Obtains the value of the YEARS field as an integer value,
     * or 0 if not present.
     *
     * <p>
     * This method is a convenience method around the
     * {@link #getField(DatatypeConstants.Field)} method.
     *
     * <p>
     * Note that since this method returns <tt>int</tt>, this
     * method will return an incorrect value for {@link Duration}s
     * with the year field that goes beyond the range of <tt>int</tt>.
     * Use <code>getField(YEARS)</code> to avoid possible loss of precision.</p>
     *
     * <p>
     *  以整数值获取YEARS字段的值,如果不存在,则取值为0。
     * 
     * <p>
     *  此方法是围绕{@link #getField(DatatypeConstants.Field)}方法的一种便利方法。
     * 
     * <p>
     *  请注意,由于此方法返回<tt> int </tt>,所以此方法将返回{@link Duration}的不正确的值,其年份字段超出了<tt> int </tt>的范围。
     * 使用<code> getField(YEARS)</code>可避免可能的精度损失。</p>。
     * 
     * 
     * @return
     *      If the YEARS field is present, return
     *      its value as an integer by using the {@link Number#intValue()}
     *      method. If the YEARS field is not present, return 0.
     */
    public int getYears() {
        return getInt(DatatypeConstants.YEARS);
    }

    /**
     * Obtains the value of the MONTHS field as an integer value,
     * or 0 if not present.
     *
     * This method works just like {@link #getYears()} except
     * that this method works on the MONTHS field.
     *
     * <p>
     *  获取MONTHS字段的值作为整数值,如果不存在,则为0。
     * 
     *  此方法的工作方式类似于{@link #getYears()},但此方法适用于MONTHS字段。
     * 
     * 
     * @return Months of this <code>Duration</code>.
     */
    public int getMonths() {
        return getInt(DatatypeConstants.MONTHS);
    }

    /**
     * Obtains the value of the DAYS field as an integer value,
     * or 0 if not present.
     *
     * This method works just like {@link #getYears()} except
     * that this method works on the DAYS field.
     *
     * <p>
     *  获取DAYS字段的值作为整数值,如果不存在,则为0。
     * 
     *  此方法的工作方式类似于{@link #getYears()},但此方法适用于DAYS字段。
     * 
     * 
     * @return Days of this <code>Duration</code>.
     */
    public int getDays() {
        return getInt(DatatypeConstants.DAYS);
    }

    /**
     * Obtains the value of the HOURS field as an integer value,
     * or 0 if not present.
     *
     * This method works just like {@link #getYears()} except
     * that this method works on the HOURS field.
     *
     * <p>
     *  获取HOURS字段的值作为整数值,如果不存在,则取值为0。
     * 
     *  此方法的工作方式类似于{@link #getYears()},但此方法适用于HOURS字段。
     * 
     * 
     * @return Hours of this <code>Duration</code>.
     *
     */
    public int getHours() {
        return getInt(DatatypeConstants.HOURS);
    }

    /**
     * Obtains the value of the MINUTES field as an integer value,
     * or 0 if not present.
     *
     * This method works just like {@link #getYears()} except
     * that this method works on the MINUTES field.
     *
     * <p>
     *  以整数值获取MINUTES字段的值,如果不存在,则取值为0。
     * 
     *  此方法的工作方式与{@link #getYears()}相似,只不过此方法适用于MINUTES字段。
     * 
     * 
     * @return Minutes of this <code>Duration</code>.
     *
     */
    public int getMinutes() {
        return getInt(DatatypeConstants.MINUTES);
    }

    /**
     * Obtains the value of the SECONDS field as an integer value,
     * or 0 if not present.
     *
     * This method works just like {@link #getYears()} except
     * that this method works on the SECONDS field.
     *
     * <p>
     * 获取SECONDS字段的值作为整数值,如果不存在,则为0。
     * 
     *  此方法的工作方式与{@link #getYears()}相似,但此方法适用于SECONDS字段。
     * 
     * 
     * @return seconds in the integer value. The fraction of seconds
     *   will be discarded (for example, if the actual value is 2.5,
     *   this method returns 2)
     */
    public int getSeconds() {
        return getInt(DatatypeConstants.SECONDS);
    }

    /**
     * <p>Return the requested field value as an int.</p>
     *
     * <p>If field is not set, i.e. == null, 0 is returned.</p>
     *
     * <p>
     *  <p>将请求的字段值作为int返回。</p>
     * 
     *  <p>如果未设置字段,即== null,则返回0。</p>
     * 
     * 
     * @param field To get value for.
     *
     * @return int value of field or 0 if field is not set.
     */
    private int getInt(DatatypeConstants.Field field) {
        Number n = getField(field);
        if (n == null) {
            return 0;
        } else {
            return n.intValue();
        }
    }

    /**
     * <p>Returns the length of the duration in milli-seconds.</p>
     *
     * <p>If the seconds field carries more digits than milli-second order,
     * those will be simply discarded (or in other words, rounded to zero.)
     * For example, for any Calendar value <code>x<code>,</p>
     * <pre>
     * <code>new Duration("PT10.00099S").getTimeInMills(x) == 10000</code>.
     * <code>new Duration("-PT10.00099S").getTimeInMills(x) == -10000</code>.
     * </pre>
     *
     * <p>
     * Note that this method uses the {@link #addTo(Calendar)} method,
     * which may work incorectly with {@link Duration} objects with
     * very large values in its fields. See the {@link #addTo(Calendar)}
     * method for details.
     *
     * <p>
     *  <p>返回持续时间的长度(以毫秒为单位)。</p>
     * 
     *  <p>如果秒字段携带的位数超过毫秒级,那么它们将被丢弃(或者换句话说舍入为零)。例如,对于任何日历值<code> x <code> >
     * <pre>
     *  <code> new Duration("PT10.00099S")。getTimeInMills(x)== 10000 </code>。
     *  <code> new Duration(" -  PT10.00099S")。getTimeInMills(x)== -10000 </code>。
     * </pre>
     * 
     * <p>
     *  请注意,此方法使用{@link #addTo(Calendar)}方法,该方法可能与其字段中具有非常大值的{@link Duration}对象无关。
     * 有关详细信息,请参阅{@link #addTo(Calendar)}方法。
     * 
     * 
     * @param startInstant
     *      The length of a month/year varies. The <code>startInstant</code> is
     *      used to disambiguate this variance. Specifically, this method
     *      returns the difference between <code>startInstant</code> and
     *      <code>startInstant+duration</code>
     *
     * @return milliseconds between <code>startInstant</code> and
     *   <code>startInstant</code> plus this <code>Duration</code>
     *
     * @throws NullPointerException if <code>startInstant</code> parameter
     * is null.
     *
     */
    public long getTimeInMillis(final Calendar startInstant) {
        Calendar cal = (Calendar) startInstant.clone();
        addTo(cal);
        return getCalendarTimeInMillis(cal)
                    - getCalendarTimeInMillis(startInstant);
    }

    /**
     * <p>Returns the length of the duration in milli-seconds.</p>
     *
     * <p>If the seconds field carries more digits than milli-second order,
     * those will be simply discarded (or in other words, rounded to zero.)
     * For example, for any <code>Date</code> value <code>x<code>,</p>
     * <pre>
     * <code>new Duration("PT10.00099S").getTimeInMills(x) == 10000</code>.
     * <code>new Duration("-PT10.00099S").getTimeInMills(x) == -10000</code>.
     * </pre>
     *
     * <p>
     * Note that this method uses the {@link #addTo(Date)} method,
     * which may work incorectly with {@link Duration} objects with
     * very large values in its fields. See the {@link #addTo(Date)}
     * method for details.
     *
     * <p>
     *  <p>返回持续时间的长度(以毫秒为单位)。</p>
     * 
     *  <p>如果秒字段携带的位数超过毫秒级,那么它们将被丢弃(或者换句话说,舍入为零)。例如,对于任何<code> Date </code> <code>,</p>
     * <pre>
     *  <code> new Duration("PT10.00099S")。getTimeInMills(x)== 10000 </code>。
     *  <code> new Duration(" -  PT10.00099S")。getTimeInMills(x)== -10000 </code>。
     * </pre>
     * 
     * <p>
     * 请注意,此方法使用{@link #addTo(Date)}方法,该方法可能与其字段中具有非常大的值的{@link Duration}对象无关。
     * 有关详情,请参阅{@link #addTo(Date)}方法。
     * 
     * 
     * @param startInstant
     *      The length of a month/year varies. The <code>startInstant</code> is
     *      used to disambiguate this variance. Specifically, this method
     *      returns the difference between <code>startInstant</code> and
     *      <code>startInstant+duration</code>.
     *
     * @throws NullPointerException
     *      If the startInstant parameter is null.
     *
     * @return milliseconds between <code>startInstant</code> and
     *   <code>startInstant</code> plus this <code>Duration</code>
     *
     * @see #getTimeInMillis(Calendar)
     */
    public long getTimeInMillis(final Date startInstant) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(startInstant);
        this.addTo(cal);
        return getCalendarTimeInMillis(cal) - startInstant.getTime();
    }

//    /**
//     * Returns an equivalent but "normalized" duration value.
//     *
//     * Intuitively, the normalization moves YEARS into
//     * MONTHS (by x12) and moves DAYS, HOURS, and MINUTES fields
//     * into SECONDS (by x86400, x3600, and x60 respectively.)
//     *
//     *
//     * Formally, this method satisfies the following conditions:
//     * <ul>
//     *  <li>x.normalize().equals(x)
//     *  <li>!x.normalize().isSet(Duration.YEARS)
//     *  <li>!x.normalize().isSet(Duration.DAYS)
//     *  <li>!x.normalize().isSet(Duration.HOURS)
//     *  <li>!x.normalize().isSet(Duration.MINUTES)
//     * </ul>
//     *
//     * <p>
//     *  // *返回一个等效但"规范化"的持续时间值。
//     *  // * // *直观地,标准化将YEARS移动到// * MONTHS(x12),并将DAYS,HOURS和MINUTES字段// *移动到SECONDS(分别为x86400,x3600和x60) 
//     * / * //正式地,此方法满足以下条件：// * <ul> // * <li> x.normalize()。
//     *  // *返回一个等效但"规范化"的持续时间值。equals(x)// * <li>！x.normalize (Duration.YEARS)// * <li>！x.normalize()。
//     * isSet(Duration.DAYS)// * <li>！x.normalize()。isSet(Duration.HOURS)// * <li> x.normalize()。
//     * isSet(Duration.MINUTES)// * </ul> // *。
//     * 
//     * 
//     * @return
//     *      always return a non-null valid value.
//     */
//    public Duration normalize() {
//        return null;
//    }

    /**
     * <p>Converts the years and months fields into the days field
     * by using a specific time instant as the reference point.</p>
     *
     * <p>For example, duration of one month normalizes to 31 days
     * given the start time instance "July 8th 2003, 17:40:32".</p>
     *
     * <p>Formally, the computation is done as follows:</p>
     * <ol>
     *  <li>The given Calendar object is cloned.
     *  <li>The years, months and days fields will be added to
     *      the {@link Calendar} object
     *      by using the {@link Calendar#add(int,int)} method.
     *  <li>The difference between two Calendars are computed in terms of days.
     *  <li>The computed days, along with the hours, minutes and seconds
     *      fields of this duration object is used to construct a new
     *      Duration object.
     * </ol>
     *
     * <p>Note that since the Calendar class uses <code>int</code> to
     * hold the value of year and month, this method may produce
     * an unexpected result if this duration object holds
     * a very large value in the years or months fields.</p>
     *
     * <p>
     *  <p>使用特定时间点作为参考点,将年份和月份字段转换为天字段。</p>
     * 
     *  <p>例如,假设开始时间实例为"2003年7月8日,17:40:32",一个月的持续时间标准化为31天。</p>
     * 
     *  <p>正式地,计算如下：</p>
     * <ol>
     * <li>克隆了给定的Calendar对象。 <li>年,月和日字段将通过使用{@link Calendar#add(int,int)}方法添加到{@link Calendar}对象。
     *  <li>两个日历之间的差异以天数计算。 <li>计算的天数,以及此持续时间对象的小时,分​​钟和秒字段用于构建一个新的Duration对象。
     * </ol>
     * 
     *  <p>请注意,由于Calendar类使用<code> int </code>来保存年和月的值,如果此持续时间对象在年或月字段中保持非常大的值,则此方法可能会产生意外的结果。 </p>
     * 
     * 
     * @param startTimeInstant <code>Calendar</code> reference point.
     *
     * @return <code>Duration</code> of years and months of this <code>Duration</code> as days.
     *
     * @throws NullPointerException If the startTimeInstant parameter is null.
     */
    public Duration normalizeWith(Calendar startTimeInstant) {

        Calendar c = (Calendar) startTimeInstant.clone();

        // using int may cause overflow, but
        // Calendar internally treats value as int anyways.
        c.add(Calendar.YEAR, getYears() * signum);
        c.add(Calendar.MONTH, getMonths() * signum);
        c.add(Calendar.DAY_OF_MONTH, getDays() * signum);

        // obtain the difference in terms of days
        long diff = getCalendarTimeInMillis(c) - getCalendarTimeInMillis(startTimeInstant);
        int days = (int) (diff / (1000L * 60L * 60L * 24L));

        return new DurationImpl(
            days >= 0,
            null,
            null,
            wrap(Math.abs(days)),
            (BigInteger) getField(DatatypeConstants.HOURS),
            (BigInteger) getField(DatatypeConstants.MINUTES),
            (BigDecimal) getField(DatatypeConstants.SECONDS));
    }

    /**
     * <p>Computes a new duration whose value is <code>factor</code> times
     * longer than the value of this duration.</p>
     *
     * <p>This method is provided for the convenience.
     * It is functionally equivalent to the following code:</p>
     * <pre>
     * multiply(new BigDecimal(String.valueOf(factor)))
     * </pre>
     *
     * <p>
     *  <p>计算值为比此持续时间值长</p> </d>倍的新持续时间。</p>
     * 
     *  <p>提供此方法是为了方便。它在功能上等同于以下代码：</p>
     * <pre>
     *  multiply(new BigDecimal(String.valueOf(factor)))
     * </pre>
     * 
     * 
     * @param factor Factor times longer of new <code>Duration</code> to create.
     *
     * @return New <code>Duration</code> that is <code>factor</code>times longer than this <code>Duration</code>.
     *
     * @see #multiply(BigDecimal)
     */
    public Duration multiply(int factor) {
        return multiply(BigDecimal.valueOf(factor));
    }

    /**
     * Computes a new duration whose value is <code>factor</code> times
     * longer than the value of this duration.
     *
     * <p>
     * For example,
     * <pre>
     * "P1M" (1 month) * "12" = "P12M" (12 months)
     * "PT1M" (1 min) * "0.3" = "PT18S" (18 seconds)
     * "P1M" (1 month) * "1.5" = IllegalStateException
     * </pre>
     *
     * <p>
     * Since the {@link Duration} class is immutable, this method
     * doesn't change the value of this object. It simply computes
     * a new Duration object and returns it.
     *
     * <p>
     * The operation will be performed field by field with the precision
     * of {@link BigDecimal}. Since all the fields except seconds are
     * restricted to hold integers,
     * any fraction produced by the computation will be
     * carried down toward the next lower unit. For example,
     * if you multiply "P1D" (1 day) with "0.5", then it will be 0.5 day,
     * which will be carried down to "PT12H" (12 hours).
     * When fractions of month cannot be meaningfully carried down
     * to days, or year to months, this will cause an
     * {@link IllegalStateException} to be thrown.
     * For example if you multiple one month by 0.5.</p>
     *
     * <p>
     * To avoid {@link IllegalStateException}, use
     * the {@link #normalizeWith(Calendar)} method to remove the years
     * and months fields.
     *
     * <p>
     *  计算值为比此持续时间的值更长的<code> factor </code>时间的新持续时间。
     * 
     * <p>
     *  例如,
     * <pre>
     *  "P1M"(1个月)*"12"="P12M"(12个月)"PT1M"(1分钟)*"0.3"="PT18S" IllegalStateException
     * </pre>
     * 
     * <p>
     *  由于{@link Duration}类是不可变的,所以此方法不会更改此对象的值。它只是计算一个新的Duration对象并返回它。
     * 
     * <p>
     * 该操作将以字段为单位以{@link BigDecimal}的精度执行。由于除了秒之外的所有字段都被限制为保持整数,所以由计算产生的任何分数将被向下传送到下一个较低单位。
     * 例如,如果将"P1D"(1天)乘以"0.5",那么它将是0.5天,这将被延续到"PT12H"(12小时)。
     * 当月份的分数不能有效地传递到天,或年到月,这将导致{@link IllegalStateException}被抛出。例如,如果您将多个月份乘以0.5。</p>。
     * 
     * <p>
     *  要避免{@link IllegalStateException},请使用{@link #normalizeWith(Calendar)}方法删除年份和月份字段。
     * 
     * 
     * @param factor to multiply by
     *
     * @return
     *      returns a non-null valid {@link Duration} object
     *
     * @throws IllegalStateException if operation produces fraction in
     * the months field.
     *
     * @throws NullPointerException if the <code>factor</code> parameter is
     * <code>null</code>.
     *
     */
    public Duration multiply(BigDecimal factor) {
        BigDecimal carry = ZERO;
        int factorSign = factor.signum();
        factor = factor.abs();

        BigDecimal[] buf = new BigDecimal[6];

        for (int i = 0; i < 5; i++) {
            BigDecimal bd = getFieldAsBigDecimal(FIELDS[i]);
            bd = bd.multiply(factor).add(carry);

            buf[i] = bd.setScale(0, BigDecimal.ROUND_DOWN);

            bd = bd.subtract(buf[i]);
            if (i == 1) {
                if (bd.signum() != 0) {
                    throw new IllegalStateException(); // illegal carry-down
                } else {
                    carry = ZERO;
                }
            } else {
                carry = bd.multiply(FACTORS[i]);
            }
        }

        if (seconds != null) {
            buf[5] = seconds.multiply(factor).add(carry);
        } else {
            buf[5] = carry;
        }

        return new DurationImpl(
            this.signum * factorSign >= 0,
            toBigInteger(buf[0], null == years),
            toBigInteger(buf[1], null == months),
            toBigInteger(buf[2], null == days),
            toBigInteger(buf[3], null == hours),
            toBigInteger(buf[4], null == minutes),
            (buf[5].signum() == 0 && seconds == null) ? null : buf[5]);
    }

    /**
     * <p>Gets the value of the field as a {@link BigDecimal}.</p>
     *
     * <p>If the field is unset, return 0.</p>
     *
     * <p>
     *  <p>将字段的值作为{@link BigDecimal}。</p>
     * 
     *  <p>如果字段未设置,则返回0。</p>
     * 
     * 
     * @param f Field to get value for.
     *
     * @return  non-null valid {@link BigDecimal}.
     */
    private BigDecimal getFieldAsBigDecimal(DatatypeConstants.Field f) {
        if (f == DatatypeConstants.SECONDS) {
            if (seconds != null) {
                return seconds;
            } else {
                return ZERO;
            }
        } else {
            BigInteger bi = (BigInteger) getField(f);
            if (bi == null) {
                return ZERO;
            } else {
                return new BigDecimal(bi);
            }
        }
    }

    /**
     * <p>BigInteger value of BigDecimal value.</p>
     *
     * <p>
     *  <p> BigDecimal值的BigInteger值。</p>
     * 
     * 
     * @param value Value to convert.
     * @param canBeNull Can returned value be null?
     *
     * @return BigInteger value of BigDecimal, possibly null.
     */
    private static BigInteger toBigInteger(
        BigDecimal value,
        boolean canBeNull) {
        if (canBeNull && value.signum() == 0) {
            return null;
        } else {
            return value.unscaledValue();
        }
    }

    /**
     * 1 unit of FIELDS[i] is equivalent to <code>FACTORS[i]</code> unit of
     * FIELDS[i+1].
     * <p>
     *  1单位的FIELDS [i]等于FIELDS [i + 1]的<code> FACTORS [i] </code>单位。
     * 
     */
    private static final BigDecimal[] FACTORS = new BigDecimal[]{
        BigDecimal.valueOf(12),
        null/*undefined*/,
        BigDecimal.valueOf(24),
        BigDecimal.valueOf(60),
        BigDecimal.valueOf(60)
    };

    /**
     * <p>Computes a new duration whose value is <code>this+rhs</code>.</p>
     *
     * <p>For example,</p>
     * <pre>
     * "1 day" + "-3 days" = "-2 days"
     * "1 year" + "1 day" = "1 year and 1 day"
     * "-(1 hour,50 minutes)" + "-20 minutes" = "-(1 hours,70 minutes)"
     * "15 hours" + "-3 days" = "-(2 days,9 hours)"
     * "1 year" + "-1 day" = IllegalStateException
     * </pre>
     *
     * <p>Since there's no way to meaningfully subtract 1 day from 1 month,
     * there are cases where the operation fails in
     * {@link IllegalStateException}.</p>
     *
     * <p>
     * Formally, the computation is defined as follows.</p>
     * <p>
     * Firstly, we can assume that two {@link Duration}s to be added
     * are both positive without losing generality (i.e.,
     * <code>(-X)+Y=Y-X</code>, <code>X+(-Y)=X-Y</code>,
     * <code>(-X)+(-Y)=-(X+Y)</code>)
     *
     * <p>
     * Addition of two positive {@link Duration}s are simply defined as
     * field by field addition where missing fields are treated as 0.
     * <p>
     * A field of the resulting {@link Duration} will be unset if and
     * only if respective fields of two input {@link Duration}s are unset.
     * <p>
     * Note that <code>lhs.add(rhs)</code> will be always successful if
     * <code>lhs.signum()*rhs.signum()!=-1</code> or both of them are
     * normalized.</p>
     *
     * <p>
     *  BigDecimal.valueOf(24),BigDecimal.valueOf(60),BigDecimal.valueOf(60)};
     * 
     *  / ** <p>计算值为<code> this + rhs </code>的新持续时间。</p>
     * 
     *  <p>例如,</p>
     * <pre>
     *  "1天"+"-3天"="-2天""1年"+"1天"="1年1天"" - (1小时50分钟)"+"-20分" " - (1小时70分钟)""15小时"+"-3天"=" - (2天,9小时)""1年"+"
     * -1天"= IllegalStateException。
     * </pre>
     * 
     *  <p>由于无法从1个月中减去1天,因此有时{@link IllegalStateException}操作失败。</p>
     * 
     * <p>
     * 形式上,计算定义如下。</p>
     * <p>
     *  首先,我们可以假设要添加的两个{@link Duration}都是正的而不失去通用性(即,<code>(-X)+ Y = YX </code>,<code> X +( -  Y) XY </code>
     * ,<code>( -  X)+( -  Y)=  - (X + Y)</code>。
     * 
     * <p>
     *  添加两个正{@link Duration}简单地定义为字段添加,其中缺少的字段被视为0。
     * <p>
     *  当且仅当未设置两个输入{@link Duration}的相应字段时,将取消生成的{@link Duration}的字段。
     * <p>
     *  注意,如果<code> lhs.signum()* rhs.signum()！=  -  1 </code>或两者都被规范化,<code> lhs.add(rhs)</code> / p>
     * 
     * 
     * @param rhs <code>Duration</code> to add to this <code>Duration</code>
     *
     * @return
     *      non-null valid Duration object.
     *
     * @throws NullPointerException
     *      If the rhs parameter is null.
     * @throws IllegalStateException
     *      If two durations cannot be meaningfully added. For
     *      example, adding negative one day to one month causes
     *      this exception.
     *
     *
     * @see #subtract(Duration)
     */
    public Duration add(final Duration rhs) {
        Duration lhs = this;
        BigDecimal[] buf = new BigDecimal[6];

        buf[0] = sanitize((BigInteger) lhs.getField(DatatypeConstants.YEARS),
                lhs.getSign()).add(sanitize((BigInteger) rhs.getField(DatatypeConstants.YEARS),  rhs.getSign()));
        buf[1] = sanitize((BigInteger) lhs.getField(DatatypeConstants.MONTHS),
                lhs.getSign()).add(sanitize((BigInteger) rhs.getField(DatatypeConstants.MONTHS), rhs.getSign()));
        buf[2] = sanitize((BigInteger) lhs.getField(DatatypeConstants.DAYS),
                lhs.getSign()).add(sanitize((BigInteger) rhs.getField(DatatypeConstants.DAYS),   rhs.getSign()));
        buf[3] = sanitize((BigInteger) lhs.getField(DatatypeConstants.HOURS),
                lhs.getSign()).add(sanitize((BigInteger) rhs.getField(DatatypeConstants.HOURS),  rhs.getSign()));
        buf[4] = sanitize((BigInteger) lhs.getField(DatatypeConstants.MINUTES),
                lhs.getSign()).add(sanitize((BigInteger) rhs.getField(DatatypeConstants.MINUTES), rhs.getSign()));
        buf[5] = sanitize((BigDecimal) lhs.getField(DatatypeConstants.SECONDS),
                lhs.getSign()).add(sanitize((BigDecimal) rhs.getField(DatatypeConstants.SECONDS), rhs.getSign()));

        // align sign
        alignSigns(buf, 0, 2); // Y,M
        alignSigns(buf, 2, 6); // D,h,m,s

        // make sure that the sign bit is consistent across all 6 fields.
        int s = 0;
        for (int i = 0; i < 6; i++) {
            if (s * buf[i].signum() < 0) {
                throw new IllegalStateException();
            }
            if (s == 0) {
                s = buf[i].signum();
            }
        }

        return new DurationImpl(
            s >= 0,
            toBigInteger(sanitize(buf[0], s),
                lhs.getField(DatatypeConstants.YEARS) == null && rhs.getField(DatatypeConstants.YEARS) == null),
            toBigInteger(sanitize(buf[1], s),
                lhs.getField(DatatypeConstants.MONTHS) == null && rhs.getField(DatatypeConstants.MONTHS) == null),
            toBigInteger(sanitize(buf[2], s),
                lhs.getField(DatatypeConstants.DAYS) == null && rhs.getField(DatatypeConstants.DAYS) == null),
            toBigInteger(sanitize(buf[3], s),
                lhs.getField(DatatypeConstants.HOURS) == null && rhs.getField(DatatypeConstants.HOURS) == null),
            toBigInteger(sanitize(buf[4], s),
                lhs.getField(DatatypeConstants.MINUTES) == null && rhs.getField(DatatypeConstants.MINUTES) == null),
             (buf[5].signum() == 0
             && lhs.getField(DatatypeConstants.SECONDS) == null
             && rhs.getField(DatatypeConstants.SECONDS) == null) ? null : sanitize(buf[5], s));
    }

    private static void alignSigns(BigDecimal[] buf, int start, int end) {
        // align sign
        boolean touched;

        do { // repeat until all the sign bits become consistent
            touched = false;
            int s = 0; // sign of the left fields

            for (int i = start; i < end; i++) {
                if (s * buf[i].signum() < 0) {
                    // this field has different sign than its left field.
                    touched = true;

                    // compute the number of unit that needs to be borrowed.
                    BigDecimal borrow =
                        buf[i].abs().divide(
                            FACTORS[i - 1],
                            BigDecimal.ROUND_UP);
                    if (buf[i].signum() > 0) {
                        borrow = borrow.negate();
                    }

                    // update values
                    buf[i - 1] = buf[i - 1].subtract(borrow);
                    buf[i] = buf[i].add(borrow.multiply(FACTORS[i - 1]));
                }
                if (buf[i].signum() != 0) {
                    s = buf[i].signum();
                }
            }
        } while (touched);
    }

    /**
     * Compute <code>value*signum</code> where value==null is treated as
     * value==0.
     * <p>
     *  计算<code> value * signum </code>其中value == null被视为value == 0。
     * 
     * 
     * @param value Value to sanitize.
     * @param signum 0 to sanitize to 0, > 0 to sanitize to <code>value</code>, < 0 to sanitize to negative <code>value</code>.
     *
     * @return non-null {@link BigDecimal}.
     */
    private static BigDecimal sanitize(BigInteger value, int signum) {
        if (signum == 0 || value == null) {
            return ZERO;
        }
        if (signum > 0) {
            return new BigDecimal(value);
        }
        return new BigDecimal(value.negate());
    }

    /**
     * <p>Compute <code>value*signum</code> where <code>value==null</code> is treated as <code>value==0</code></p>.
     *
     * <p>
     *  <p>计算<code> value * signum </code>其中<code> value == null </code>被视为<code> value == 0 </code> </p>。
     * 
     * 
     * @param value Value to sanitize.
     * @param signum 0 to sanitize to 0, > 0 to sanitize to <code>value</code>, < 0 to sanitize to negative <code>value</code>.
     *
     * @return non-null {@link BigDecimal}.
     */
    static BigDecimal sanitize(BigDecimal value, int signum) {
        if (signum == 0 || value == null) {
            return ZERO;
        }
        if (signum > 0) {
            return value;
        }
        return value.negate();
    }

    /**
     * <p>Computes a new duration whose value is <code>this-rhs</code>.</p>
     *
     * <p>For example:</p>
     * <pre>
     * "1 day" - "-3 days" = "4 days"
     * "1 year" - "1 day" = IllegalStateException
     * "-(1 hour,50 minutes)" - "-20 minutes" = "-(1hours,30 minutes)"
     * "15 hours" - "-3 days" = "3 days and 15 hours"
     * "1 year" - "-1 day" = "1 year and 1 day"
     * </pre>
     *
     * <p>Since there's no way to meaningfully subtract 1 day from 1 month,
     * there are cases where the operation fails in {@link IllegalStateException}.</p>
     *
     * <p>Formally the computation is defined as follows.
     * First, we can assume that two {@link Duration}s are both positive
     * without losing generality.  (i.e.,
     * <code>(-X)-Y=-(X+Y)</code>, <code>X-(-Y)=X+Y</code>,
     * <code>(-X)-(-Y)=-(X-Y)</code>)</p>
     *
     * <p>Then two durations are subtracted field by field.
     * If the sign of any non-zero field <tt>F</tt> is different from
     * the sign of the most significant field,
     * 1 (if <tt>F</tt> is negative) or -1 (otherwise)
     * will be borrowed from the next bigger unit of <tt>F</tt>.</p>
     *
     * <p>This process is repeated until all the non-zero fields have
     * the same sign.</p>
     *
     * <p>If a borrow occurs in the days field (in other words, if
     * the computation needs to borrow 1 or -1 month to compensate
     * days), then the computation fails by throwing an
     * {@link IllegalStateException}.</p>
     *
     * <p>
     *  <p>计算值为<code> this-rhs </code>的新持续时间。</p>
     * 
     *  <p>例如：</p>
     * <pre>
     *  "1天" - "-3天"="4天""1年" - "1天"= IllegalStateException" - (1小时50分钟)" - "-20分钟"=" )""15小时" - "-3天"="3天15
     * 小时""1年" - "-1天"="1年1天"。
     * </pre>
     * 
     *  <p>由于无法从1个月中减去1天,因此有时{@link IllegalStateException}操作失败。</p>
     * 
     * <p>形式上,计算定义如下。首先,我们可以假设两个{@link Duration}都是正的,不会失去一般性。
     *  (即,<code>(-X)-Y =  - (X + Y)</code>,<code> X  - ( -  Y)= X + Y </code> (-Y)=  - (XY)</code>)</p>。
     * 
     *  <p>然后逐个字段减去两个持续时间。
     * 如果任何非零字段<tt> F </tt>的符号不同于最高有效字段的符号,则1(如果<tt> F </tt>为负)或-1(否则)从<tt> F </tt>的下一个更大单位借来。</p>。
     * 
     *  <p>重复此过程,直到所有非零字段具有相同的符号。</p>
     * 
     *  <p>如果在days字段中发生借用(换句话说,如果计算需要借用1或-1个月来补偿天数),那么计算失败通过引发{@link IllegalStateException}。</p>
     * 
     * 
     * @param rhs <code>Duration</code> to substract from this <code>Duration</code>.
     *
     * @return New <code>Duration</code> created from subtracting <code>rhs</code> from this <code>Duration</code>.
     *
     * @throws IllegalStateException
     *      If two durations cannot be meaningfully subtracted. For
     *      example, subtracting one day from one month causes
     *      this exception.
     *
     * @throws NullPointerException
     *      If the rhs parameter is null.
     *
     * @see #add(Duration)
     */
    public Duration subtract(final Duration rhs) {
        return add(rhs.negate());
    }

    /**
     * Returns a new {@link Duration} object whose
     * value is <code>-this</code>.
     *
     * <p>
     * Since the {@link Duration} class is immutable, this method
     * doesn't change the value of this object. It simply computes
     * a new Duration object and returns it.
     *
     * <p>
     *  返回值为<code> -this </code>的新{@link Duration}对象。
     * 
     * <p>
     *  由于{@link Duration}类是不可变的,所以此方法不会更改此对象的值。它只是计算一个新的Duration对象并返回它。
     * 
     * 
     * @return
     *      always return a non-null valid {@link Duration} object.
     */
    public Duration negate() {
        return new DurationImpl(
            signum <= 0,
            years,
            months,
            days,
            hours,
            minutes,
            seconds);
    }

    /**
     * Returns the sign of this duration in -1,0, or 1.
     *
     * <p>
     *  以-1,0或1返回此持续时间的符号。
     * 
     * 
     * @return
     *      -1 if this duration is negative, 0 if the duration is zero,
     *      and 1 if the duration is postive.
     */
    public int signum() {
        return signum;
    }


    /**
     * Adds this duration to a {@link Calendar} object.
     *
     * <p>
     * Calls {@link java.util.Calendar#add(int,int)} in the
     * order of YEARS, MONTHS, DAYS, HOURS, MINUTES, SECONDS, and MILLISECONDS
     * if those fields are present. Because the {@link Calendar} class
     * uses int to hold values, there are cases where this method
     * won't work correctly (for example if values of fields
     * exceed the range of int.)
     * </p>
     *
     * <p>
     * Also, since this duration class is a Gregorian duration, this
     * method will not work correctly if the given {@link Calendar}
     * object is based on some other calendar systems.
     * </p>
     *
     * <p>
     * Any fractional parts of this {@link Duration} object
     * beyond milliseconds will be simply ignored. For example, if
     * this duration is "P1.23456S", then 1 is added to SECONDS,
     * 234 is added to MILLISECONDS, and the rest will be unused.
     * </p>
     *
     * <p>
     * Note that because {@link Calendar#add(int, int)} is using
     * <tt>int</tt>, {@link Duration} with values beyond the
     * range of <tt>int</tt> in its fields
     * will cause overflow/underflow to the given {@link Calendar}.
     * {@link XMLGregorianCalendar#add(Duration)} provides the same
     * basic operation as this method while avoiding
     * the overflow/underflow issues.
     *
     * <p>
     *  将此持续时间添加到{@link Calendar}对象。
     * 
     * <p>
     *  如果这些字段存在,则按YEARS,MONTHS,DAYS,HOURS,MINUTES,SECONDS和MILLISECONDS的顺序调用{@link java.util.Calendar#add(int,int)}
     * 。
     * 因为{@link Calendar}类使用int来保存值,所以有时候这种方法将无法正常工作(例如,如果字段的值超过int的范围)。
     * </p>
     * 
     * <p>
     * 此外,由于此持续时间类是一个格雷戈里时间,如果给定的{@link Calendar}对象基于某些其他日历系统,此方法将无法正常工作。
     * </p>
     * 
     * <p>
     *  超过毫秒的{@link Duration}对象的任何小数部分都将被忽略。
     * 例如,如果此持续时间为"P1.23456S",则将1添加到SECONDS,将234添加到MILLISECONDS,其余将不使用。
     * </p>
     * 
     * <p>
     *  请注意,由于{@link Calendar#add(int,int)}使用<tt> int </tt>,因此其字段中的值超出<tt> int </tt>的值的{@link Duration}溢出/下溢
     * 到给定的{@link日历}。
     *  {@link XMLGregorianCalendar#add(Duration)}提供与此方法相同的基本操作,同时避免上溢/下溢问题。
     * 
     * 
     * @param calendar
     *      A calendar object whose value will be modified.
     * @throws NullPointerException
     *      if the calendar parameter is null.
     */
    public void addTo(Calendar calendar) {
        calendar.add(Calendar.YEAR, getYears() * signum);
        calendar.add(Calendar.MONTH, getMonths() * signum);
        calendar.add(Calendar.DAY_OF_MONTH, getDays() * signum);
        calendar.add(Calendar.HOUR, getHours() * signum);
        calendar.add(Calendar.MINUTE, getMinutes() * signum);
        calendar.add(Calendar.SECOND, getSeconds() * signum);

        if (seconds != null) {
            BigDecimal fraction =
                seconds.subtract(seconds.setScale(0, BigDecimal.ROUND_DOWN));
            int millisec = fraction.movePointRight(3).intValue();
            calendar.add(Calendar.MILLISECOND, millisec * signum);
        }
    }

    /**
     * Adds this duration to a {@link Date} object.
     *
     * <p>
     * The given date is first converted into
     * a {@link java.util.GregorianCalendar}, then the duration
     * is added exactly like the {@link #addTo(Calendar)} method.
     *
     * <p>
     * The updated time instant is then converted back into a
     * {@link Date} object and used to update the given {@link Date} object.
     *
     * <p>
     * This somewhat redundant computation is necessary to unambiguously
     * determine the duration of months and years.
     *
     * <p>
     *  将此持续时间添加到{@link Date}对象。
     * 
     * <p>
     *  给定日期首先转换为{@link java.util.GregorianCalendar},然后持续时间与{@link #addTo(Calendar)}方法完全相同。
     * 
     * <p>
     *  更新的时间点然后被转换回{@link Date}对象,并用于更新给定的{@link Date}对象。
     * 
     * <p>
     *  这种有些冗余的计算对于明确地确定月和年的持续时间是必要的。
     * 
     * 
     * @param date
     *      A date object whose value will be modified.
     * @throws NullPointerException
     *      if the date parameter is null.
     */
    public void addTo(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date); // this will throw NPE if date==null
        this.addTo(cal);
        date.setTime(getCalendarTimeInMillis(cal));
    }

    /**
     * <p>Stream Unique Identifier.</p>
     *
     * <p>TODO: Serialization should use the XML string representation as
     * the serialization format to ensure future compatibility.</p>
     * <p>
     *  <p>流唯一标识符</p>
     * 
     *  <p> TODO：序列化应使用XML字符串表示作为序列化格式,以确保未来的兼容性。</p>
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Writes {@link Duration} as a lexical representation
     * for maximum future compatibility.
     *
     * <p>
     *  将{@link Duration}用作词汇表示,以实现最大的未来兼容性。
     * 
     * 
     * @return
     *      An object that encapsulates the string
     *      returned by <code>this.toString()</code>.
     */
    private Object writeReplace() throws IOException {
        return new DurationStream(this.toString());
    }

    /**
     * Representation of {@link Duration} in the object stream.
     *
     * <p>
     *  在对象流中表示{@link Duration}。
     * 
     * 
     * @author Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
     */
    private static class DurationStream implements Serializable {
        private final String lexical;

        private DurationStream(String _lexical) {
            this.lexical = _lexical;
        }

        private Object readResolve() throws ObjectStreamException {
            //            try {
            return new DurationImpl(lexical);
            //            } catch( ParseException e ) {
            //                throw new StreamCorruptedException("unable to parse "+lexical+" as duration");
            //            }
        }

        private static final long serialVersionUID = 1L;
    }

    /**
     * Calls the {@link Calendar#getTimeInMillis} method.
     * Prior to JDK1.4, this method was protected and therefore
     * cannot be invoked directly.
     *
     * In future, this should be replaced by
     * <code>cal.getTimeInMillis()</code>
     * <p>
     * 调用{@link Calendar#getTimeInMillis}方法。在JDK1.4之前,此方法受到保护,因此无法直接调用。
     * 
     */
    private static long getCalendarTimeInMillis(Calendar cal) {
        return cal.getTime().getTime();
    }
}
