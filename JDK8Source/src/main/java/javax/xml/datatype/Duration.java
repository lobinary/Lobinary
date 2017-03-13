/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2006, Oracle and/or its affiliates. All rights reserved.
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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.namespace.QName;

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
 *
 * <p>For example, 30 days cannot be meaningfully compared to one month.
 * The {@link #compare(Duration duration)} method implements this
 * relationship.</p>
 *
 * <p>See the {@link #isLongerThan(Duration)} method for details about
 * the order relationship among <code>Duration</code> objects.</p>
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
 * the <code>Duration</code> class can only deal with finite precision
 * decimal numbers. For example, one cannot represent 1 sec divided by 3.</p>
 *
 * <p>However, you could substitute a division by 3 with multiplying
 * by numbers such as 0.3 or 0.333.</p>
 *
 * <h2>Range of allowed values</h2>
 * <p>
 * Because some operations of <code>Duration</code> rely on {@link Calendar}
 * even though {@link Duration} can hold very large or very small values,
 * some of the methods may not work correctly on such <code>Duration</code>s.
 * The impacted methods document their dependency on {@link Calendar}.
 *
 * <p>
 *  <p>在W3C XML Schema 10规范</p>中定义的时间跨度的不变表示
 * 
 *  <p>持续时间对象表示格里历时间,其中包含六个字段(年,月,日,小时,分钟和秒)加上符号(+/-)字段</p>
 * 
 *  <p>前五个字段具有非负(> = 0)整数或null(表示字段未设置),秒字段具有非负十进制或null负号表示负持续时间< / p>
 * 
 *  <p>此类提供了许多方法,使其易于在XML Schema 10的持续时间数据类型中使用,其中勘误</p>
 * 
 * <h2>顺序关系</h2> <p>持续时间对象仅具有部分顺序,其中两个值A和B可以是：</p>
 * <ol>
 *  <li> A <B(A小于B)<li> A> B(A比B长)<li> A == B(A和B具有相同的持续时间)<li> A < (A和B之间的比较是不确定的)
 * </ol>
 * 
 *  <p>例如,与一个月相比,30天无法有意义{@link #compare(Duration duration)}方法实现此关系</p>
 * 
 *  <p>有关<code> Duration </code>对象之间的顺序关系的详情,请参阅{@link #isLongerThan(Duration)}方法</p>
 * 
 * <h2>持续时间的操作</h2> <p>此类提供一组基本的算术运算,例如加法,减法和乘法因为持续时间没有总阶数,操作可能会失败一些操作组合例如,你不能从1个月减去15天查看这些方法的javadoc可能发
 * 生这种情况的详细条件</p>。
 * 
 *  <p>此外,由于<code> Duration </code>类只能处理有限精度的十进制数,因此不提供由数字划分的持续时间。例如,不能表示1秒除以3 </p>
 * 
 *  <p>但是,您可以用3乘以数字(如03或0333 </p>)来代替除法
 * 
 *  <h2>允许值范围</h2>
 * <p>
 * 由于<code> Duration </code>的某些操作依赖于{@link Calendar},即使{@link Duration}可以保存非常大或非常小的值,某些方法可能无法在此类<code> D
 * uration < / code> s受影响的方法记录了它们对{@link Calendar}的依赖性,。
 * 
 * 
 * @author <a href="mailto:Joseph.Fialli@Sun.COM">Joseph Fialli</a>
 * @author <a href="mailto:Kohsuke.Kawaguchi@Sun.com">Kohsuke Kawaguchi</a>
 * @author <a href="mailto:Jeff.Suttor@Sun.com">Jeff Suttor</a>
 * @author <a href="mailto:Sunitha.Reddy@Sun.com">Sunitha Reddy</a>
 * @see XMLGregorianCalendar#add(Duration)
 * @since 1.5
 */
public abstract class Duration {

    /**
     * <p>Debugging <code>true</code> or <code>false</code>.</p>
     * <p>
     *  <p>调试<code> true </code>或<code> false </code> </p>
     * 
     */
    private static final boolean DEBUG = true;

    /**
     * Default no-arg constructor.
     *
     * <p>Note: Always use the {@link DatatypeFactory} to
     * construct an instance of <code>Duration</code>.
     * The constructor on this class cannot be guaranteed to
     * produce an object with a consistent state and may be
     * removed in the future.</p>
     * <p>
     *  默认无参数构造函数
     * 
     *  <p>注意：始终使用{@link DatatypeFactory}构造<code> Duration </code>的实例。
     * 此类的构造函数不能保证产生具有一致状态的对象,并且可能会在将来删除< / p>。
     * 
     */
    public Duration() {
    }

    /**
     * <p>Return the name of the XML Schema date/time type that this instance
     * maps to. Type is computed based on fields that are set,
     * i.e. {@link #isSet(DatatypeConstants.Field field)} == <code>true</code>.</p>
     *
     * <table border="2" rules="all" cellpadding="2">
     *   <thead>
     *     <tr>
     *       <th align="center" colspan="7">
     *         Required fields for XML Schema 1.0 Date/Time Datatypes.<br/>
     *         <i>(timezone is optional for all date/time datatypes)</i>
     *       </th>
     *     </tr>
     *   </thead>
     *   <tbody>
     *     <tr>
     *       <td>Datatype</td>
     *       <td>year</td>
     *       <td>month</td>
     *       <td>day</td>
     *       <td>hour</td>
     *       <td>minute</td>
     *       <td>second</td>
     *     </tr>
     *     <tr>
     *       <td>{@link DatatypeConstants#DURATION}</td>
     *       <td>X</td>
     *       <td>X</td>
     *       <td>X</td>
     *       <td>X</td>
     *       <td>X</td>
     *       <td>X</td>
     *     </tr>
     *     <tr>
     *       <td>{@link DatatypeConstants#DURATION_DAYTIME}</td>
     *       <td></td>
     *       <td></td>
     *       <td>X</td>
     *       <td>X</td>
     *       <td>X</td>
     *       <td>X</td>
     *     </tr>
     *     <tr>
     *       <td>{@link DatatypeConstants#DURATION_YEARMONTH}</td>
     *       <td>X</td>
     *       <td>X</td>
     *       <td></td>
     *       <td></td>
     *       <td></td>
     *       <td></td>
     *     </tr>
     *   </tbody>
     * </table>
     *
     * <p>
     * <p>返回此实例映射到类型的XML模式日期/时间类型的名称是基于设置的字段计算的,即{@link #isSet(DatatypeConstantsField field)} == <code> true
     *  </code> </p>。
     * 
     * <table border="2" rules="all" cellpadding="2">
     * <thead>
     * <tr>
     * <th align="center" colspan="7">
     *  XML模式的必填字段10日期/时间数据类型<br/> <i>(所有日期/时间数据类型的时区是可选的)</i>
     * </th>
     * </tr>
     * </thead>
     * <tbody>
     * <tr>
     *  <td>数据类型</td> <td>年份</td> <td>月份</td> <td>日期</td> <td>小时</td> <td> >第二</td>
     * </tr>
     * <tr>
     *  <td> {@ link DatatypeConstants#DURATION} </td> <td> X </td> <td> X </td> <td> X </td> <td> X </td> <td>
     *  </td> <td> X </td>。
     * </tr>
     * <tr>
     *  <td> {@ link DatatypeConstants#DURATION_DAYTIME} </td> <td> </td> <td> </td> <td> X </td> <td> X </td>
     *  td> <td> X </td>。
     * </tr>
     * <tr>
     *  <td> {@ link DatatypeConstants#DURATION_YEARMONTH} </td> <td> X </td> <td> X </td> <td> </td> <td> </td>
     *  <td> </td > <td> </td>。
     * </tr>
     * </tbody>
     * </table>
     * 
     * 
     * @return one of the following constants:
     *   {@link DatatypeConstants#DURATION},
     *   {@link DatatypeConstants#DURATION_DAYTIME} or
     *   {@link DatatypeConstants#DURATION_YEARMONTH}.
     *
     * @throws IllegalStateException If the combination of set fields does not match one of the XML Schema date/time datatypes.
     */
    public QName getXMLSchemaType() {

        boolean yearSet = isSet(DatatypeConstants.YEARS);
        boolean monthSet = isSet(DatatypeConstants.MONTHS);
        boolean daySet = isSet(DatatypeConstants.DAYS);
        boolean hourSet = isSet(DatatypeConstants.HOURS);
        boolean minuteSet = isSet(DatatypeConstants.MINUTES);
        boolean secondSet = isSet(DatatypeConstants.SECONDS);

        // DURATION
        if (yearSet
            && monthSet
            && daySet
            && hourSet
            && minuteSet
            && secondSet) {
            return DatatypeConstants.DURATION;
        }

        // DURATION_DAYTIME
        if (!yearSet
            && !monthSet
            && daySet
            && hourSet
            && minuteSet
            && secondSet) {
            return DatatypeConstants.DURATION_DAYTIME;
        }

        // DURATION_YEARMONTH
        if (yearSet
            && monthSet
            && !daySet
            && !hourSet
            && !minuteSet
            && !secondSet) {
            return DatatypeConstants.DURATION_YEARMONTH;
        }

        // nothing matches
        throw new IllegalStateException(
                "javax.xml.datatype.Duration#getXMLSchemaType():"
                + " this Duration does not match one of the XML Schema date/time datatypes:"
                + " year set = " + yearSet
                + " month set = " + monthSet
                + " day set = " + daySet
                + " hour set = " + hourSet
                + " minute set = " + minuteSet
                + " second set = " + secondSet
        );
    }

    /**
     * Returns the sign of this duration in -1,0, or 1.
     *
     * <p>
     * 以-1,0或1返回此持续时间的符号
     * 
     * 
     * @return
     *      -1 if this duration is negative, 0 if the duration is zero,
     *      and 1 if the duration is positive.
     */
    public abstract int getSign();

    /**
     * <p>Get the years value of this <code>Duration</code> as an <code>int</code> or <code>0</code> if not present.</p>
     *
     * <p><code>getYears()</code> is a convenience method for
     * {@link #getField(DatatypeConstants.Field field) getField(DatatypeConstants.YEARS)}.</p>
     *
     * <p>As the return value is an <code>int</code>, an incorrect value will be returned for <code>Duration</code>s
     * with years that go beyond the range of an <code>int</code>.
     * Use {@link #getField(DatatypeConstants.Field field) getField(DatatypeConstants.YEARS)} to avoid possible loss of precision.</p>
     *
     * <p>
     *  <p>如果不存在,将<code> Duration </code>的年值设置为<code> int </code>或<code> 0 </code>
     * 
     *  <p> <code> getYears()</code>是{@link #getField(DatatypeConstantsField field)getField(DatatypeConstantsYEARS)}
     * 的便利方法</p>。
     * 
     *  <p>由于返回值是<code> int </code>,因此<code> Duration </code>将返回不正确的值,超过了<code> int </code >使用{@link #getField(DatatypeConstantsField field)getField(DatatypeConstantsYEARS)}
     * 避免可能的精度损失</p>。
     * 
     * 
     * @return If the years field is present, return its value as an <code>int</code>, else return <code>0</code>.
     */
    public int getYears() {
        return getField(DatatypeConstants.YEARS).intValue();
    }

    /**
     * Obtains the value of the MONTHS field as an integer value,
     * or 0 if not present.
     *
     * This method works just like {@link #getYears()} except
     * that this method works on the MONTHS field.
     *
     * <p>
     *  获取MONTHS字段的值作为整数值,如果不存在,则为0
     * 
     *  此方法的工作方式类似于{@link #getYears()},但此方法适用于MONTHS字段
     * 
     * 
     * @return Months of this <code>Duration</code>.
     */
    public int getMonths() {
        return getField(DatatypeConstants.MONTHS).intValue();
    }

    /**
     * Obtains the value of the DAYS field as an integer value,
     * or 0 if not present.
     *
     * This method works just like {@link #getYears()} except
     * that this method works on the DAYS field.
     *
     * <p>
     * 获取DAYS字段的值作为整数值,如果不存在,则为0
     * 
     *  此方法的工作方式类似于{@link #getYears()},但此方法适用于DAYS字段
     * 
     * 
     * @return Days of this <code>Duration</code>.
     */
    public int getDays() {
        return getField(DatatypeConstants.DAYS).intValue();
    }

    /**
     * Obtains the value of the HOURS field as an integer value,
     * or 0 if not present.
     *
     * This method works just like {@link #getYears()} except
     * that this method works on the HOURS field.
     *
     * <p>
     *  获取HOURS字段的值作为整数值,如果不存在,则取值为0
     * 
     *  此方法的工作方式与{@link #getYears()}相似,但此方法适用于HOURS字段
     * 
     * 
     * @return Hours of this <code>Duration</code>.
     *
     */
    public int getHours() {
        return getField(DatatypeConstants.HOURS).intValue();
    }

    /**
     * Obtains the value of the MINUTES field as an integer value,
     * or 0 if not present.
     *
     * This method works just like {@link #getYears()} except
     * that this method works on the MINUTES field.
     *
     * <p>
     *  以整数值获取MINUTES字段的值,如果不存在,则取值为0
     * 
     *  此方法的工作方式与{@link #getYears()}相似,只不过此方法适用于MINUTES字段
     * 
     * 
     * @return Minutes of this <code>Duration</code>.
     *
     */
    public int getMinutes() {
        return getField(DatatypeConstants.MINUTES).intValue();
    }

    /**
     * Obtains the value of the SECONDS field as an integer value,
     * or 0 if not present.
     *
     * This method works just like {@link #getYears()} except
     * that this method works on the SECONDS field.
     *
     * <p>
     *  获取SECONDS字段的值作为整数值,如果不存在,则为0
     * 
     *  此方法的工作方式与{@link #getYears()}相似,但此方法适用于SECONDS字段
     * 
     * 
     * @return seconds in the integer value. The fraction of seconds
     *   will be discarded (for example, if the actual value is 2.5,
     *   this method returns 2)
     */
    public int getSeconds() {
        return getField(DatatypeConstants.SECONDS).intValue();
    }

    /**
     * <p>Returns the length of the duration in milli-seconds.</p>
     *
     * <p>If the seconds field carries more digits than milli-second order,
     * those will be simply discarded (or in other words, rounded to zero.)
     * For example, for any Calendar value <code>x</code>,</p>
     * <pre>
     * <code>new Duration("PT10.00099S").getTimeInMills(x) == 10000</code>.
     * <code>new Duration("-PT10.00099S").getTimeInMills(x) == -10000</code>.
     * </pre>
     *
     * <p>
     * Note that this method uses the {@link #addTo(Calendar)} method,
     * which may work incorrectly with <code>Duration</code> objects with
     * very large values in its fields. See the {@link #addTo(Calendar)}
     * method for details.
     *
     * <p>
     *  <p>返回持续时间的长度(以毫秒为单位)</p>
     * 
     * <p>如果秒字段携带的位数超过毫秒级,那么它们将被丢弃(或者换句话说,舍入为零)。例如,对于任何日历值<code> x </code> >
     * <pre>
     *  <code> new Duration(" -  PT1000099S")getTimeInMills(x)== -10000 </code> getTimeInMills(x)== 10000 </code>
     * 。
     * </pre>
     * 
     * <p>
     *  请注意,此方法使用{@link #addTo(Calendar)}方法,该方法在其字段中具有非常大的值的<code> Duration </code>对象中可能无法正常工作。
     * 参见{@link #addTo详情。
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
     * For example, for any <code>Date</code> value <code>x</code>,</p>
     * <pre>
     * <code>new Duration("PT10.00099S").getTimeInMills(x) == 10000</code>.
     * <code>new Duration("-PT10.00099S").getTimeInMills(x) == -10000</code>.
     * </pre>
     *
     * <p>
     * Note that this method uses the {@link #addTo(Date)} method,
     * which may work incorrectly with <code>Duration</code> objects with
     * very large values in its fields. See the {@link #addTo(Date)}
     * method for details.
     *
     * <p>
     *  <p>返回持续时间的长度(以毫秒为单位)</p>
     * 
     * <p>如果秒字段携带的位数超过毫秒级,那么它们将被丢弃(或换句话说,舍入为零)。例如,对于任何<code> Date </code> / code>,</p>
     * <pre>
     *  <code> new Duration(" -  PT1000099S")getTimeInMills(x)== -10000 </code> getTimeInMills(x)== 10000 </code>
     * 。
     * </pre>
     * 
     * <p>
     *  请注意,此方法使用{@link #addTo(Date)}方法,该方法在其字段中具有非常大的值的<code> Duration </code>对象中可能无法正常工作参见{@link #addTo详情。
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
     *  获取字段的值
     * 
     *  持续时间对象的字段可能包含任意大的值因此,此方法旨在返回{@link Number}对象
     * 
     * 在YEARS,MONTHS,DAYS,HOURS和MINUTES的情况下,返回的数字将是非负整数在秒的情况下,返回的数字可以是非负十进制值
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
     *      returns a {@link java.math.BigInteger} object. For SECONDS, this
     *      method returns a {@link java.math.BigDecimal}.
     *
     * @throws NullPointerException If the <code>field</code> is <code>null</code>.
     */
    public abstract Number getField(final DatatypeConstants.Field field);

    /**
     * Checks if a field is set.
     *
     * A field of a duration object may or may not be present.
     * This method can be used to test if a field is present.
     *
     * <p>
     *  检查是否设置了字段
     * 
     *  持续时间对象的字段可以存在也可以不存在此方法可用于测试字段是否存在
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
    public abstract boolean isSet(final DatatypeConstants.Field field);

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
     * Firstly, we can assume that two <code>Duration</code>s to be added
     * are both positive without losing generality (i.e.,
     * <code>(-X)+Y=Y-X</code>, <code>X+(-Y)=X-Y</code>,
     * <code>(-X)+(-Y)=-(X+Y)</code>)
     *
     * <p>
     * Addition of two positive <code>Duration</code>s are simply defined as
     * field by field addition where missing fields are treated as 0.
     * <p>
     * A field of the resulting <code>Duration</code> will be unset if and
     * only if respective fields of two input <code>Duration</code>s are unset.
     * <p>
     * Note that <code>lhs.add(rhs)</code> will be always successful if
     * <code>lhs.signum()*rhs.signum()!=-1</code> or both of them are
     * normalized.</p>
     *
     * <p>
     *  <p>计算值为<code> this + rhs </code> </p>的新持续时间
     * 
     *  <p>例如,</p>
     * <pre>
     *  "1天"+"-3天"="-2天""1年"+"1天"="1年1天"" - (1小时50分钟)"+"-20分" " - (1小时70分钟)""15小时"+"-3天"=" - (2天,9小时)""1年"+"
     * -1天"= IllegalStateException。
     * </pre>
     * 
     *  <p>由于没有办法从1个月有效地减去1天,有些情况下操作失败{@link IllegalStateException} </p>
     * 
     * <p>
     * 形式上,计算定义如下</p>
     * <p>
     *  首先,我们可以假设待添加的两个<code> Duration </code>都是正的,而不会失去通用性(即,<code>(-X)+ Y = YX </code>,<code> X + Y)= XY </code>
     * ,<code>(-X)+(-Y)=  - (X + Y)。
     * 
     * <p>
     *  加上两个正的<code> Duration </code>被简单地定义为字段通过字段添加,其中丢失字段被视为0
     * <p>
     *  当且仅当两个输入<code> Duration </code>的各个字段未设置时,所得的<code> Duration </code>
     * <p>
     *  请注意,如果<code> lhssignum()* rhssignum()！=  -  1 </code>或两者都已规范化,<code> lhsadd(rhs)</code>
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
    public abstract Duration add(final Duration rhs);

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
     * Any fractional parts of this <code>Duration</code> object
     * beyond milliseconds will be simply ignored. For example, if
     * this duration is "P1.23456S", then 1 is added to SECONDS,
     * 234 is added to MILLISECONDS, and the rest will be unused.
     * </p>
     *
     * <p>
     * Note that because {@link Calendar#add(int, int)} is using
     * <code>int</code>, <code>Duration</code> with values beyond the
     * range of <code>int</code> in its fields
     * will cause overflow/underflow to the given {@link Calendar}.
     * {@link XMLGregorianCalendar#add(Duration)} provides the same
     * basic operation as this method while avoiding
     * the overflow/underflow issues.
     *
     * <p>
     *  将此持续时间添加到{@link Calendar}对象
     * 
     * <p>
     * 如果这些字段存在,则按照YEARS,MONTHS,DAYS,HOURS,MINUTES,SECONDS和MILLISECONDS的顺序调用{@link javautilCalendar#add(int,int)}
     * 因为{@link Calendar}类使用int来保存值,有些情况下,此方法将无法正常工作(例如,如果字段的值超过int的范围)。
     * </p>
     * 
     * <p>
     *  此外,由于此持续时间类是一个格雷戈里时间,如果给定的{@link Calendar}对象基于某些其他日历系统,此方法将无法正常工作
     * </p>
     * 
     * <p>
     *  超过毫秒的<code> Duration </code>对象的任何小数部分将被忽略。
     * 例如,如果此持续时间是"P123456S",则将1添加到SECONDS中,将234添加到MILLISECONDS,。
     * </p>
     * 
     * <p>
     * 请注意,由于{@link Calendar#add(int,int)}正在使用<code> int </code>,<code> Duration </code>字段将导致给定{@link日历} {@link XMLGregorianCalendar#add(Duration)}
     * 的溢出/下溢提供与此方法相同的基本操作,同时避免上溢/下溢问题。
     * 
     * 
     * @param calendar
     *      A calendar object whose value will be modified.
     * @throws NullPointerException
     *      if the calendar parameter is null.
     */
    public abstract void addTo(Calendar calendar);

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
     *  将此持续时间添加到{@link Date}对象
     * 
     * <p>
     *  给定日期首先转换为{@link javautilGregorianCalendar},然后持续时间与{@link #addTo(Calendar)}方法完全相同
     * 
     * <p>
     *  更新的时间点然后被转换回{@link Date}对象,并用于更新给定的{@link Date}对象
     * 
     * <p>
     *  这种有些冗余的计算对于明确地确定月和年的持续时间是必要的
     * 
     * 
     * @param date
     *      A date object whose value will be modified.
     * @throws NullPointerException
     *      if the date parameter is null.
     */
    public void addTo(Date date) {

        // check data parameter
        if (date == null) {
            throw new NullPointerException(
                "Cannot call "
                + this.getClass().getName()
                + "#addTo(Date date) with date == null."
            );
        }

        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        this.addTo(cal);
        date.setTime(getCalendarTimeInMillis(cal));
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
     * First, we can assume that two <code>Duration</code>s are both positive
     * without losing generality.  (i.e.,
     * <code>(-X)-Y=-(X+Y)</code>, <code>X-(-Y)=X+Y</code>,
     * <code>(-X)-(-Y)=-(X-Y)</code>)</p>
     *
     * <p>Then two durations are subtracted field by field.
     * If the sign of any non-zero field <code>F</code> is different from
     * the sign of the most significant field,
     * 1 (if <code>F</code> is negative) or -1 (otherwise)
     * will be borrowed from the next bigger unit of <code>F</code>.</p>
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
     * <p>计算值为<code> this-rhs </code> </p>的新持续时间
     * 
     *  <p>例如：</p>
     * <pre>
     *  "1天" - "-3天"="4天""1年" - "1天"= IllegalStateException" - (1小时50分钟)" - "-20分钟"=" )""15小时" - "-3天"="3天15
     * 小时""1年" - "-1天"="1年1天"。
     * </pre>
     * 
     *  <p>由于没有办法从1个月有效地减去1天,有些情况下操作失败{@link IllegalStateException} </p>
     * 
     *  正式地,计算被定义如下：首先,我们可以假设两个<code> Duration </code>都是正的,而不失去通用性(即,<code>(-X)-Y =  - (X + Y )</code>,<code>
     *  X  - ( -  Y)= X + Y </code>,<code>( -  X) - ( -  Y)=  - 。
     * 
     * <p>然后,逐个字段地减去两个持续时间。
     * 如果任何非零字段<code> F </code>的符号不同于最高有效字段的符号1(如果<code> F </code>是负的)或-1(否则)将从<code> F </code> </p>的下一个更大的单
     * 位借用。
     * <p>然后,逐个字段地减去两个持续时间。
     * 
     *  <p>重复此过程,直到所有非零字段具有相同的符号</p>
     * 
     *  <p>如果借位发生在days字段中(换句话说,如果计算需要借用1或-1个月来补偿天数),那么计算失败会抛出一个{@link IllegalStateException} </p>
     * 
     * 
     * @param rhs <code>Duration</code> to subtract from this <code>Duration</code>.
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
     *  <p>计算新值,其值为<code> factor </code>的时间长于此持续时间的值</p>
     * 
     *  <p>提供此方法是为了方便起见,它在功能上等同于以下代码：</p>
     * <pre>
     * multiply(new BigDecimal(StringvalueOf(factor)))
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
        return multiply(new BigDecimal(String.valueOf(factor)));
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
     * Since the <code>Duration</code> class is immutable, this method
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
     *  计算值为比此持续时间的值更长的<code> factor </code>时间的新持续时间
     * 
     * <p>
     *  例如,
     * <pre>
     *  "P1M"(1个月)*"12"="P12M"(12个月)"PT1M"(1分钟)*"03"="PT18S" IllegalStateException
     * </pre>
     * 
     * <p>
     *  因为<code> Duration </code>类是不可变的,所以这个方法不会改变这个对象的值它只是计算一个新的Duration对象并返回它
     * 
     * <p>
     * 操作将以字段为单位按照{@link BigDecimal}的精度执行。由于除秒之外的所有字段都被限制为保持整数,所以计算产生的任何分数都将被传递到下一个较低单位。
     * 例如,如果乘以"P1D"(1天)和"05",那么它将是05天,这将被转到"PT12H"(12小时)。
     * 当月份不能有效地进行到天,将导致{@link IllegalStateException}被抛出例如,如果你多个一个月由05 </p>。
     * 
     * <p>
     *  要避免{@link IllegalStateException},请使用{@link #normalizeWith(Calendar)}方法删除年份和月份字段
     * 
     * 
     * @param factor to multiply by
     *
     * @return
     *      returns a non-null valid <code>Duration</code> object
     *
     * @throws IllegalStateException if operation produces fraction in
     * the months field.
     *
     * @throws NullPointerException if the <code>factor</code> parameter is
     * <code>null</code>.
     *
     */
    public abstract Duration multiply(final BigDecimal factor);

    /**
     * Returns a new <code>Duration</code> object whose
     * value is <code>-this</code>.
     *
     * <p>
     * Since the <code>Duration</code> class is immutable, this method
     * doesn't change the value of this object. It simply computes
     * a new Duration object and returns it.
     *
     * <p>
     *  返回值为<code> -this </code>的新<object> Duration </code>对象
     * 
     * <p>
     * 因为<code> Duration </code>类是不可变的,所以这个方法不会改变这个对象的值它只是计算一个新的Duration对象并返回它
     * 
     * 
     * @return
     *      always return a non-null valid <code>Duration</code> object.
     */
    public abstract Duration negate();

    /**
     * <p>Converts the years and months fields into the days field
     * by using a specific time instant as the reference point.</p>
     *
     * <p>For example, duration of one month normalizes to 31 days
     * given the start time instance "July 8th 2003, 17:40:32".</p>
     *
     * <p>Formally, the computation is done as follows:</p>
     * <ol>
     *  <li>the given Calendar object is cloned</li>
     *  <li>the years, months and days fields will be added to the {@link Calendar} object
     *      by using the {@link Calendar#add(int,int)} method</li>
     *  <li>the difference between the two Calendars in computed in milliseconds and converted to days,
     *     if a remainder occurs due to Daylight Savings Time, it is discarded</li>
     *  <li>the computed days, along with the hours, minutes and seconds
     *      fields of this duration object is used to construct a new
     *      Duration object.</li>
     * </ol>
     *
     * <p>Note that since the Calendar class uses <code>int</code> to
     * hold the value of year and month, this method may produce
     * an unexpected result if this duration object holds
     * a very large value in the years or months fields.</p>
     *
     * <p>
     *  <p>使用特定时间点作为参考点</p>,将年份和月份字段转换为天字段
     * 
     *  <p>例如,假设开始时间实例为"2003年7月8日17：40：32",一个月的持续时间标准化为31天。</p>
     * 
     *  <p>正式地,计算如下：</p>
     * <ol>
     * <li>克隆了给定的Google日历对象</li> <li>使用{@link Calendar#add(int,int)}方法将年,月和日字段添加到{@link Calendar} </li> <li>
     * 以毫秒计算并转换为天的两个日历之间的差异,如果由于夏令时产生余数,则会被丢弃</li> <li>计算的天数以及小时,分钟和秒字段用于构造一个新的Duration对象</li>。
     * </ol>
     * 
     *  <p>请注意,由于Calendar类使用<code> int </code>来保存年和月的值,如果此持续时间对象在年或月字段中保持非常大的值,则此方法可能会产生意外的结果< / p>
     * 
     * 
     * @param startTimeInstant <code>Calendar</code> reference point.
     *
     * @return <code>Duration</code> of years and months of this <code>Duration</code> as days.
     *
     * @throws NullPointerException If the startTimeInstant parameter is null.
     */
    public abstract Duration normalizeWith(final Calendar startTimeInstant);

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
     * <p>与此<code> Duration </code>实例</p>的部分顺序关系比较
     * 
     *  <p>比较结果必须符合<a href=\"http://wwww3org/TR/xmlschema-2/#duration-order\"> W3C XML模式10第2部分,第32762节,<i>订单
     * 关系持续时间</i> </a> </p>。
     * 
     *  <p>返回：</p>
     * <ul>
     *  <li> {@ link DatatypeConstants#EQUAL}如果此<code> Duration </code>小于<code> duration </code>参数</li>如果此<code>
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
    public abstract int compare(final Duration duration);

    /**
     * <p>Checks if this duration object is strictly longer than
     * another <code>Duration</code> object.</p>
     *
     * <p>Duration X is "longer" than Y if and only if X>Y
     * as defined in the section 3.2.6.2 of the XML Schema 1.0
     * specification.</p>
     *
     * <p>For example, "P1D" (one day) > "PT12H" (12 hours) and
     * "P2Y" (two years) > "P23M" (23 months).</p>
     *
     * <p>
     * <p>检查此持续时间对象是否严格长于另一个<code> Duration </code>对象</p>
     * 
     *  <p>当且仅当XML Schema 10规范</p>的第3262节中定义的X> Y时,X的"比X更长"
     * 
     *  <p>例如,"P1D"(一天)>"PT12H"(12小时)和"P2Y"(两年)>"P23M"(23个月)</p>
     * 
     * 
     * @param duration <code>Duration</code> to test this <code>Duration</code> against.
     *
     * @throws UnsupportedOperationException If the underlying implementation
     *   cannot reasonably process the request, e.g. W3C XML Schema allows for
     *   arbitrarily large/small/precise values, the request may be beyond the
     *   implementations capability.
     * @throws NullPointerException If <code>duration</code> is null.
     *
     * @return
     *      true if the duration represented by this object
     *      is longer than the given duration. false otherwise.
     *
     * @see #isShorterThan(Duration)
     * @see #compare(Duration duration)
     */
    public boolean isLongerThan(final Duration duration) {
        return compare(duration) == DatatypeConstants.GREATER;
    }

    /**
     * <p>Checks if this duration object is strictly shorter than
     * another <code>Duration</code> object.</p>
     *
     * <p>
     *  <p>检查此持续时间对象是否严格地短于另一个<code> Duration </code>对象</p>
     * 
     * 
     * @param duration <code>Duration</code> to test this <code>Duration</code> against.
     *
     * @return <code>true</code> if <code>duration</code> parameter is shorter than this <code>Duration</code>,
     *   else <code>false</code>.
     *
     * @throws UnsupportedOperationException If the underlying implementation
     *   cannot reasonably process the request, e.g. W3C XML Schema allows for
     *   arbitrarily large/small/precise values, the request may be beyond the
     *   implementations capability.
     * @throws NullPointerException if <code>duration</code> is null.
     *
     * @see #isLongerThan(Duration duration)
     * @see #compare(Duration duration)
     */
    public boolean isShorterThan(final Duration duration) {
        return compare(duration) == DatatypeConstants.LESSER;
    }

    /**
     * <p>Checks if this duration object has the same duration
     * as another <code>Duration</code> object.</p>
     *
     * <p>For example, "P1D" (1 day) is equal to "PT24H" (24 hours).</p>
     *
     * <p>Duration X is equal to Y if and only if time instant
     * t+X and t+Y are the same for all the test time instants
     * specified in the section 3.2.6.2 of the XML Schema 1.0
     * specification.</p>
     *
     * <p>Note that there are cases where two <code>Duration</code>s are
     * "incomparable" to each other, like one month and 30 days.
     * For example,</p>
     * <pre>
     * !new Duration("P1M").isShorterThan(new Duration("P30D"))
     * !new Duration("P1M").isLongerThan(new Duration("P30D"))
     * !new Duration("P1M").equals(new Duration("P30D"))
     * </pre>
     *
     * <p>
     *  <p>检查此持续时间对象是否与另一个<code> Duration </code>对象持续时间相同</p>
     * 
     *  <p>例如,"P1D"(1天)等于"PT24H"(24小时)</p>
     * 
     *  <p>当且仅当时间t + X和t + Y对于XML模式10规范</p>的第3262节中指定的所有测试时刻是相同的时,X等于Y
     * 
     * <p>请注意,有些情况下,两个<code> Duration </code>是彼此"无法比拟的",例如一个月和30天。例如,</p>
     * <pre>
     *  ！new Duration("P1M")isShorterThan(new Duration("P30D"))！new Duration("P1M")isLongerThan(new Duration
     * ("P30D")) P30D"))。
     * </pre>
     * 
     * 
     * @param duration
     *      The object to compare this <code>Duration</code> against.
     *
     * @return
     *      <code>true</code> if this duration is the same length as
     *         <code>duration</code>.
     *      <code>false</code> if <code>duration</code> is <code>null</code>,
     *         is not a
     *         <code>Duration</code> object,
     *         or its length is different from this duration.
     *
     * @throws UnsupportedOperationException If the underlying implementation
     *   cannot reasonably process the request, e.g. W3C XML Schema allows for
     *   arbitrarily large/small/precise values, the request may be beyond the
     *   implementations capability.
     *
     * @see #compare(Duration duration)
     */
    public boolean equals(final Object duration) {

        if (duration == null || !(duration instanceof Duration)) {
            return false;
        }

        return compare((Duration) duration) == DatatypeConstants.EQUAL;
    }

    /**
     * Returns a hash code consistent with the definition of the equals method.
     *
     * <p>
     *  返回与equals方法的定义一致的哈希代码
     * 
     * 
     * @see Object#hashCode()
     */
    public abstract int hashCode();

    /**
     * <p>Returns a <code>String</code> representation of this <code>Duration</code> <code>Object</code>.</p>
     *
     * <p>The result is formatted according to the XML Schema 1.0 spec and can be always parsed back later into the
     * equivalent <code>Duration</code> <code>Object</code> by {@link DatatypeFactory#newDuration(String  lexicalRepresentation)}.</p>
     *
     * <p>Formally, the following holds for any <code>Duration</code>
     * <code>Object</code> x:</p>
     * <pre>
     * new Duration(x.toString()).equals(x)
     * </pre>
     *
     * <p>
     *  <p>返回此<code>持续时间</code> <code>对象</code> </p>的<code> String </code>
     * 
     *  <p>结果根据XML Schema 10规范格式化,可以随后通过{@link DatatypeFactory#newDuration(String lexicalRepresentation)总是解析为等效的<code> Duration </code> <code> Object </code> )}
     *  </p>。
     * 
     * <p>正式地,以下适用于任何<code> Duration </code> <code> Object </code> x：</p>
     * <pre>
     *  new Duration(xtoString())equals(x)
     * </pre>
     * 
     * 
     * @return A non-<code>null</code> valid <code>String</code> representation of this <code>Duration</code>.
     */
    public String toString() {

        StringBuffer buf = new StringBuffer();

        if (getSign() < 0) {
            buf.append('-');
        }
        buf.append('P');

        BigInteger years = (BigInteger) getField(DatatypeConstants.YEARS);
        if (years != null) {
            buf.append(years + "Y");
        }

        BigInteger months = (BigInteger) getField(DatatypeConstants.MONTHS);
        if (months != null) {
            buf.append(months + "M");
        }

        BigInteger days = (BigInteger) getField(DatatypeConstants.DAYS);
        if (days != null) {
            buf.append(days + "D");
        }

        BigInteger hours = (BigInteger) getField(DatatypeConstants.HOURS);
        BigInteger minutes = (BigInteger) getField(DatatypeConstants.MINUTES);
        BigDecimal seconds = (BigDecimal) getField(DatatypeConstants.SECONDS);
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
     * <p>Calls the {@link Calendar#getTimeInMillis} method.
     * Prior to JDK1.4, this method was protected and therefore
     * cannot be invoked directly.</p>
     *
     * <p>TODO: In future, this should be replaced by <code>cal.getTimeInMillis()</code>.</p>
     *
     * <p>
     *  <p>将{@link BigDecimal}改为字符串表示</p>
     * 
     *  <p>由于JDK15中的{@link BigDecimal#toString()}方法中的行为更改,因此必须在此处实施</p>
     * 
     * 
     * @param cal <code>Calendar</code> to get time in milliseconds.
     *
     * @return Milliseconds of <code>cal</code>.
     */
    private static long getCalendarTimeInMillis(final Calendar cal) {
        return cal.getTime().getTime();
    }
}
