/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * Copyright (c) 2011-2012, Stephen Colebourne & Michael Nascimento Santos
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-310 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * <p>
 *  版权所有(c)2011-2012,Stephen Colebourne和Michael Nascimento Santos
 * 
 *  版权所有。
 * 
 *  如果满足以下条件,则允许重新分发和使用源代码和二进制形式(带或不带修改)：
 * 
 *  *源代码的再分发必须保留上述版权声明,此条件列表和以下免责声明。
 * 
 *  *二进制形式的再分发必须在随发行提供的文档和/或其他材料中复制上述版权声明,此条件列表和以下免责声明。
 * 
 *  *未经特定事先书面许可,JSR-310的名称及其贡献者的名称不得用于支持或推广衍生自此软件的产品。
 * 
 * 本软件由版权所有者和贡献者"按原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 * 在任何情况下,版权所有者或贡献者对任何直接,间接,偶发,特殊,惩戒性或后果性损害(包括但不限于替代商品或服务的采购,使用,数据或利润损失,或业务中断),无论是由于任何责任推定,无论是在合同,严格责任,或
 * 侵权(包括疏忽或其他)任何方式使用本软件,即使已被告知此类损害的可能性。
 * 本软件由版权所有者和贡献者"按原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 * 
 */
package java.time.temporal;

import static java.time.DayOfWeek.THURSDAY;
import static java.time.DayOfWeek.WEDNESDAY;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.EPOCH_DAY;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.FOREVER;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.WEEKS;
import static java.time.temporal.ChronoUnit.YEARS;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
import java.time.format.ResolverStyle;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import sun.util.locale.provider.LocaleProviderAdapter;
import sun.util.locale.provider.LocaleResources;

/**
 * Fields and units specific to the ISO-8601 calendar system,
 * including quarter-of-year and week-based-year.
 * <p>
 * This class defines fields and units that are specific to the ISO calendar system.
 *
 * <h3>Quarter of year</h3>
 * The ISO-8601 standard is based on the standard civic 12 month year.
 * This is commonly divided into four quarters, often abbreviated as Q1, Q2, Q3 and Q4.
 * <p>
 * January, February and March are in Q1.
 * April, May and June are in Q2.
 * July, August and September are in Q3.
 * October, November and December are in Q4.
 * <p>
 * The complete date is expressed using three fields:
 * <ul>
 * <li>{@link #DAY_OF_QUARTER DAY_OF_QUARTER} - the day within the quarter, from 1 to 90, 91 or 92
 * <li>{@link #QUARTER_OF_YEAR QUARTER_OF_YEAR} - the week within the week-based-year
 * <li>{@link ChronoField#YEAR YEAR} - the standard ISO year
 * </ul>
 *
 * <h3>Week based years</h3>
 * The ISO-8601 standard was originally intended as a data interchange format,
 * defining a string format for dates and times. However, it also defines an
 * alternate way of expressing the date, based on the concept of week-based-year.
 * <p>
 * The date is expressed using three fields:
 * <ul>
 * <li>{@link ChronoField#DAY_OF_WEEK DAY_OF_WEEK} - the standard field defining the
 *  day-of-week from Monday (1) to Sunday (7)
 * <li>{@link #WEEK_OF_WEEK_BASED_YEAR} - the week within the week-based-year
 * <li>{@link #WEEK_BASED_YEAR WEEK_BASED_YEAR} - the week-based-year
 * </ul>
 * The week-based-year itself is defined relative to the standard ISO proleptic year.
 * It differs from the standard year in that it always starts on a Monday.
 * <p>
 * The first week of a week-based-year is the first Monday-based week of the standard
 * ISO year that has at least 4 days in the new year.
 * <ul>
 * <li>If January 1st is Monday then week 1 starts on January 1st
 * <li>If January 1st is Tuesday then week 1 starts on December 31st of the previous standard year
 * <li>If January 1st is Wednesday then week 1 starts on December 30th of the previous standard year
 * <li>If January 1st is Thursday then week 1 starts on December 29th of the previous standard year
 * <li>If January 1st is Friday then week 1 starts on January 4th
 * <li>If January 1st is Saturday then week 1 starts on January 3rd
 * <li>If January 1st is Sunday then week 1 starts on January 2nd
 * </ul>
 * There are 52 weeks in most week-based years, however on occasion there are 53 weeks.
 * <p>
 * For example:
 *
 * <table cellpadding="0" cellspacing="3" border="0" style="text-align: left; width: 50%;">
 * <caption>Examples of Week based Years</caption>
 * <tr><th>Date</th><th>Day-of-week</th><th>Field values</th></tr>
 * <tr><th>2008-12-28</th><td>Sunday</td><td>Week 52 of week-based-year 2008</td></tr>
 * <tr><th>2008-12-29</th><td>Monday</td><td>Week 1 of week-based-year 2009</td></tr>
 * <tr><th>2008-12-31</th><td>Wednesday</td><td>Week 1 of week-based-year 2009</td></tr>
 * <tr><th>2009-01-01</th><td>Thursday</td><td>Week 1 of week-based-year 2009</td></tr>
 * <tr><th>2009-01-04</th><td>Sunday</td><td>Week 1 of week-based-year 2009</td></tr>
 * <tr><th>2009-01-05</th><td>Monday</td><td>Week 2 of week-based-year 2009</td></tr>
 * </table>
 *
 * @implSpec
 * <p>
 * This class is immutable and thread-safe.
 *
 * <p>
 *  ISO-8601日历系统特有的字段和单位,包括年度和周为基础的年度。
 * <p>
 *  此类定义特定于ISO日历系统的字段和单位。
 * 
 *  <h3>年度季度</h3> ISO-8601标准基于标准公民12个月。这通常分为四个季度,通常缩写为Q1,Q2,Q3和Q4。
 * <p>
 *  1月,2月和3月在Q1。 4月,5月和6月是第2季度。 7月,8月和9月在Q3。 10月,11月和12月在Q4。
 * <p>
 *  完整日期使用三个字段表示：
 * <ul>
 * <li> {@ link #DAY_OF_QUARTER DAY_OF_QUARTER}  -  1至90,91或92天<li> {@ link #QUARTER_OF_YEAR QUARTER_OF_YEAR}
 *   - 每周一周<li> {@ link ChronoField#YEAR YEAR}  - 标准ISO年。
 * </ul>
 * 
 *  <h3>以周为基础的年度</h3> ISO-8601标准最初用作数据交换格式,定义日期和时间的字符串格式。然而,它还定义了基于基于周的年的概念的表示日期的替代方式。
 * <p>
 *  日期使用三个字段表示：
 * <ul>
 *  <li> {@ link ChronoField#DAY_OF_WEEK DAY_OF_WEEK}  - 定义星期一(1)到星期日(7)的星期几的标准字段<li> {@ link #WEEK_OF_WEEK_BASED_YEAR}
 *   - 一周内的一周,年<li> {@ link #WEEK_BASED_YEAR WEEK_BASED_YEAR}  - 以周为基础的年。
 * </ul>
 *  基于星期的年份本身是相对于标准ISO预测年来定义的。它与标准年不同,它总是从星期一开始。
 * <p>
 *  每周基于周的年的第一周是标准ISO年的第一个基于星期一的星期,在新的一年中至少有4天。
 * <ul>
 * <li>如果1月1日是星期一,则第1周从1月1日开始<li>如果1月1日是星期二,则第1周从上一年的12月31日开始<li>如果1月1日是星期三,则第1周从12月30日开始<li>如果1月1日是星期四,
 * 则第1周从上一个标准年12月29日开始<li>如果1月1日是星期五,则1月1日从1月4日开始<li>如果1月1日是星期六, 1将于1月3日开始<li>如果1月1日是星期日,则第1周从1月2日开始。
 * </ul>
 *  在大多数周的年份有52周,但有时有53周。
 * <p>
 *  例如：
 * 
 * <table cellpadding="0" cellspacing="3" border="0" style="text-align: left; width: 50%;">
 *  <caption>以周为基础的年份示例</caption> <tr> <th>日期</th> <th>星期</th> <th>字段值</th> </tr> <tr > <th> 2008年12月28日
 * </t> <td>星期日</td> <td> 2008年第52周</td> </tr> <tr> <th> -29 </t> <td>星期一</td> <td> 2009年第一周的第一周</td> </tr>
 *  <tr> <th> 2008-12-31 </th> < td>星期三</td> <td> 2009年第1周</td> </tr> <tr> <th> 2009-01-01 </th> <td>星期四
 * </td> <td> 2009年第1周第/ 1周</td> </tr> <tr> <th> 2009-01-04 </th> <td>周日</td> <td> 2009年</td> </tr> <tr>
 *  <th> 2009-01-05 </th> <td>星期一</td> <td> td> </tr>。
 * </table>
 * 
 *  @implSpec
 * <p>
 *  这个类是不可变的和线程安全的。
 * 
 * 
 * @since 1.8
 */
public final class IsoFields {

    /**
     * The field that represents the day-of-quarter.
     * <p>
     * This field allows the day-of-quarter value to be queried and set.
     * The day-of-quarter has values from 1 to 90 in Q1 of a standard year, from 1 to 91
     * in Q1 of a leap year, from 1 to 91 in Q2 and from 1 to 92 in Q3 and Q4.
     * <p>
     * The day-of-quarter can only be calculated if the day-of-year, month-of-year and year
     * are available.
     * <p>
     * When setting this field, the value is allowed to be partially lenient, taking any
     * value from 1 to 92. If the quarter has less than 92 days, then day 92, and
     * potentially day 91, is in the following quarter.
     * <p>
     * In the resolving phase of parsing, a date can be created from a year,
     * quarter-of-year and day-of-quarter.
     * <p>
     * In {@linkplain ResolverStyle#STRICT strict mode}, all three fields are
     * validated against their range of valid values. The day-of-quarter field
     * is validated from 1 to 90, 91 or 92 depending on the year and quarter.
     * <p>
     * In {@linkplain ResolverStyle#SMART smart mode}, all three fields are
     * validated against their range of valid values. The day-of-quarter field is
     * validated between 1 and 92, ignoring the actual range based on the year and quarter.
     * If the day-of-quarter exceeds the actual range by one day, then the resulting date
     * is one day later. If the day-of-quarter exceeds the actual range by two days,
     * then the resulting date is two days later.
     * <p>
     * In {@linkplain ResolverStyle#LENIENT lenient mode}, only the year is validated
     * against the range of valid values. The resulting date is calculated equivalent to
     * the following three stage approach. First, create a date on the first of January
     * in the requested year. Then take the quarter-of-year, subtract one, and add the
     * amount in quarters to the date. Finally, take the day-of-quarter, subtract one,
     * and add the amount in days to the date.
     * <p>
     * This unit is an immutable and thread-safe singleton.
     * <p>
     *  表示季度的字段。
     * <p>
     * 此字段允许查询和设置季度值。季度的季度值在标准年的第一季度从1到90,从闰年的第一季度的1到91,第二季度的从1到91,以及第三季度和第四季度的从1到92。
     * <p>
     *  每日季度只能在年,月和年可用时计算。
     * <p>
     *  设置此字段时,允许该值部分放宽,取1到92之间的任何值。如果季度少于92天,则第92天(可能为第91天)在下一季度。
     * <p>
     *  在解析的解析阶段,可以从一年,四分之一和四分之一天创建日期。
     * <p>
     *  在{@linkplain ResolverStyle#STRICT strict mode}中,所有三个字段都根据其有效值范围进行验证。季度字段的有效期为1至90,91或92,具体取决于年份和季度。
     * <p>
     *  在{@linkplain ResolverStyle#SMART智能模式}中,所有三个字段均根据其有效值范围进行验证。季度字段在1到92之间有效,忽略基于年份和季度的实际范围。
     * 如果季度超过实际范围一天,则生成的日期是一天后。如果季度超过实际范围两天,则生成的日期为两天后。
     * <p>
     * 在{@linkplain ResolverStyle#LENIENT宽松模式}中,只对有效值范围验证年份。计算结果日期等同于以下三阶段方法。首先,在请求的年份中,在1月1日创建日期。
     * 然后取四分之一,减去1,并将该季度的金额加到日期。最后,取季度,减去1,然后将天数加上日期。
     * <p>
     *  这个单元是一个不可变和线程安全的单例。
     * 
     */
    public static final TemporalField DAY_OF_QUARTER = Field.DAY_OF_QUARTER;
    /**
     * The field that represents the quarter-of-year.
     * <p>
     * This field allows the quarter-of-year value to be queried and set.
     * The quarter-of-year has values from 1 to 4.
     * <p>
     * The quarter-of-year can only be calculated if the month-of-year is available.
     * <p>
     * In the resolving phase of parsing, a date can be created from a year,
     * quarter-of-year and day-of-quarter.
     * See {@link #DAY_OF_QUARTER} for details.
     * <p>
     * This unit is an immutable and thread-safe singleton.
     * <p>
     *  表示四分之一的字段。
     * <p>
     *  此字段允许查询和设置四分之一年的值。四分之一的值为1到4。
     * <p>
     *  只有当年份可用时,才能计算年度季度。
     * <p>
     *  在解析的解析阶段,可以从一年,四分之一和四分之一天创建日期。有关详情,请参阅{@link #DAY_OF_QUARTER}。
     * <p>
     *  这个单元是一个不可变和线程安全的单例。
     * 
     */
    public static final TemporalField QUARTER_OF_YEAR = Field.QUARTER_OF_YEAR;
    /**
     * The field that represents the week-of-week-based-year.
     * <p>
     * This field allows the week of the week-based-year value to be queried and set.
     * The week-of-week-based-year has values from 1 to 52, or 53 if the
     * week-based-year has 53 weeks.
     * <p>
     * In the resolving phase of parsing, a date can be created from a
     * week-based-year, week-of-week-based-year and day-of-week.
     * <p>
     * In {@linkplain ResolverStyle#STRICT strict mode}, all three fields are
     * validated against their range of valid values. The week-of-week-based-year
     * field is validated from 1 to 52 or 53 depending on the week-based-year.
     * <p>
     * In {@linkplain ResolverStyle#SMART smart mode}, all three fields are
     * validated against their range of valid values. The week-of-week-based-year
     * field is validated between 1 and 53, ignoring the week-based-year.
     * If the week-of-week-based-year is 53, but the week-based-year only has
     * 52 weeks, then the resulting date is in week 1 of the following week-based-year.
     * <p>
     * In {@linkplain ResolverStyle#LENIENT lenient mode}, only the week-based-year
     * is validated against the range of valid values. If the day-of-week is outside
     * the range 1 to 7, then the resulting date is adjusted by a suitable number of
     * weeks to reduce the day-of-week to the range 1 to 7. If the week-of-week-based-year
     * value is outside the range 1 to 52, then any excess weeks are added or subtracted
     * from the resulting date.
     * <p>
     * This unit is an immutable and thread-safe singleton.
     * <p>
     *  表示基于星期的年份的字段。
     * <p>
     *  此字段允许查询和设置基于周的年份值的周。基于星期的年份的值为1到52,如果基于星期的年份有53周,则为53。
     * <p>
     *  在解析的解析阶段中,可以从基于周的年,基于周的周的年和周的周中创建日期。
     * <p>
     * 在{@linkplain ResolverStyle#STRICT strict mode}中,所有三个字段都根据其有效值范围进行验证。基于星期的年份字段根据基于星期的年份从1到52或53来验证。
     * <p>
     *  在{@linkplain ResolverStyle#SMART智能模式}中,所有三个字段均根据其有效值范围进行验证。基于星期的年份字段在1和53之间有效,忽略基于周的年。
     * 如果基于星期的年份为53,但基于星期的年份仅具有52周,则所得日期在下一周基于年的年份的第1周。
     * <p>
     *  在{@linkplain ResolverStyle#LENIENT宽松模式}中,仅对基于周的年份对有效值范围进行验证。
     * 如果星期在1到7的范围之外,则将所得到的日期调整合适的周数,以将星期减少到1至7的范围。如果星期的周 - 基于年的值在1到52的范围之外,则从结果日期中添加或减去任何多余的周。
     * <p>
     *  这个单元是一个不可变和线程安全的单例。
     * 
     */
    public static final TemporalField WEEK_OF_WEEK_BASED_YEAR = Field.WEEK_OF_WEEK_BASED_YEAR;
    /**
     * The field that represents the week-based-year.
     * <p>
     * This field allows the week-based-year value to be queried and set.
     * <p>
     * The field has a range that matches {@link LocalDate#MAX} and {@link LocalDate#MIN}.
     * <p>
     * In the resolving phase of parsing, a date can be created from a
     * week-based-year, week-of-week-based-year and day-of-week.
     * See {@link #WEEK_OF_WEEK_BASED_YEAR} for details.
     * <p>
     * This unit is an immutable and thread-safe singleton.
     * <p>
     *  表示基于周的年的字段。
     * <p>
     *  此字段允许查询和设置基于周的年份值。
     * <p>
     *  该字段的范围与{@link LocalDate#MAX}和{@link LocalDate#MIN}匹配。
     * <p>
     *  在解析的解析阶段中,可以从基于周的年,基于周的周的年和周的周中创建日期。有关详情,请参阅{@link #WEEK_OF_WEEK_BASED_YEAR}。
     * <p>
     *  这个单元是一个不可变和线程安全的单例。
     * 
     */
    public static final TemporalField WEEK_BASED_YEAR = Field.WEEK_BASED_YEAR;
    /**
     * The unit that represents week-based-years for the purpose of addition and subtraction.
     * <p>
     * This allows a number of week-based-years to be added to, or subtracted from, a date.
     * The unit is equal to either 52 or 53 weeks.
     * The estimated duration of a week-based-year is the same as that of a standard ISO
     * year at {@code 365.2425 Days}.
     * <p>
     * The rules for addition add the number of week-based-years to the existing value
     * for the week-based-year field. If the resulting week-based-year only has 52 weeks,
     * then the date will be in week 1 of the following week-based-year.
     * <p>
     * This unit is an immutable and thread-safe singleton.
     * <p>
     * 表示加法和减法目的的基于周的年的单位。
     * <p>
     *  这允许将多个基于周的年份添加到日期中或从日期中减去。单位等于52或53周。每周基准年的估计持续时间与标准ISO年{@code 365.2425 Days}的持续时间相同。
     * <p>
     *  添加规则将基于周的年份数添加到基于周的年份字段的现有值。如果生成的基于周的年只有52周,则日期将是下一周基于年的周的第1周。
     * <p>
     *  这个单元是一个不可变和线程安全的单例。
     * 
     */
    public static final TemporalUnit WEEK_BASED_YEARS = Unit.WEEK_BASED_YEARS;
    /**
     * Unit that represents the concept of a quarter-year.
     * For the ISO calendar system, it is equal to 3 months.
     * The estimated duration of a quarter-year is one quarter of {@code 365.2425 Days}.
     * <p>
     * This unit is an immutable and thread-safe singleton.
     * <p>
     *  代表四分之一年概念的单位。对于ISO日历系统,它等于3个月。四分之一年的估计持续时间为{@code 365.2425 Days}的四分之一。
     * <p>
     *  这个单元是一个不可变和线程安全的单例。
     * 
     */
    public static final TemporalUnit QUARTER_YEARS = Unit.QUARTER_YEARS;

    /**
     * Restricted constructor.
     * <p>
     *  受限制的构造函数。
     * 
     */
    private IsoFields() {
        throw new AssertionError("Not instantiable");
    }

    //-----------------------------------------------------------------------
    /**
     * Implementation of the field.
     * <p>
     *  实施该领域。
     * 
     */
    private static enum Field implements TemporalField {
        DAY_OF_QUARTER {
            @Override
            public TemporalUnit getBaseUnit() {
                return DAYS;
            }
            @Override
            public TemporalUnit getRangeUnit() {
                return QUARTER_YEARS;
            }
            @Override
            public ValueRange range() {
                return ValueRange.of(1, 90, 92);
            }
            @Override
            public boolean isSupportedBy(TemporalAccessor temporal) {
                return temporal.isSupported(DAY_OF_YEAR) && temporal.isSupported(MONTH_OF_YEAR) &&
                        temporal.isSupported(YEAR) && isIso(temporal);
            }
            @Override
            public ValueRange rangeRefinedBy(TemporalAccessor temporal) {
                if (isSupportedBy(temporal) == false) {
                    throw new UnsupportedTemporalTypeException("Unsupported field: DayOfQuarter");
                }
                long qoy = temporal.getLong(QUARTER_OF_YEAR);
                if (qoy == 1) {
                    long year = temporal.getLong(YEAR);
                    return (IsoChronology.INSTANCE.isLeapYear(year) ? ValueRange.of(1, 91) : ValueRange.of(1, 90));
                } else if (qoy == 2) {
                    return ValueRange.of(1, 91);
                } else if (qoy == 3 || qoy == 4) {
                    return ValueRange.of(1, 92);
                } // else value not from 1 to 4, so drop through
                return range();
            }
            @Override
            public long getFrom(TemporalAccessor temporal) {
                if (isSupportedBy(temporal) == false) {
                    throw new UnsupportedTemporalTypeException("Unsupported field: DayOfQuarter");
                }
                int doy = temporal.get(DAY_OF_YEAR);
                int moy = temporal.get(MONTH_OF_YEAR);
                long year = temporal.getLong(YEAR);
                return doy - QUARTER_DAYS[((moy - 1) / 3) + (IsoChronology.INSTANCE.isLeapYear(year) ? 4 : 0)];
            }
            @SuppressWarnings("unchecked")
            @Override
            public <R extends Temporal> R adjustInto(R temporal, long newValue) {
                // calls getFrom() to check if supported
                long curValue = getFrom(temporal);
                range().checkValidValue(newValue, this);  // leniently check from 1 to 92 TODO: check
                return (R) temporal.with(DAY_OF_YEAR, temporal.getLong(DAY_OF_YEAR) + (newValue - curValue));
            }
            @Override
            public ChronoLocalDate resolve(
                    Map<TemporalField, Long> fieldValues, TemporalAccessor partialTemporal, ResolverStyle resolverStyle) {
                Long yearLong = fieldValues.get(YEAR);
                Long qoyLong = fieldValues.get(QUARTER_OF_YEAR);
                if (yearLong == null || qoyLong == null) {
                    return null;
                }
                int y = YEAR.checkValidIntValue(yearLong);  // always validate
                long doq = fieldValues.get(DAY_OF_QUARTER);
                ensureIso(partialTemporal);
                LocalDate date;
                if (resolverStyle == ResolverStyle.LENIENT) {
                    date = LocalDate.of(y, 1, 1).plusMonths(Math.multiplyExact(Math.subtractExact(qoyLong, 1), 3));
                    doq = Math.subtractExact(doq, 1);
                } else {
                    int qoy = QUARTER_OF_YEAR.range().checkValidIntValue(qoyLong, QUARTER_OF_YEAR);  // validated
                    date = LocalDate.of(y, ((qoy - 1) * 3) + 1, 1);
                    if (doq < 1 || doq > 90) {
                        if (resolverStyle == ResolverStyle.STRICT) {
                            rangeRefinedBy(date).checkValidValue(doq, this);  // only allow exact range
                        } else {  // SMART
                            range().checkValidValue(doq, this);  // allow 1-92 rolling into next quarter
                        }
                    }
                    doq--;
                }
                fieldValues.remove(this);
                fieldValues.remove(YEAR);
                fieldValues.remove(QUARTER_OF_YEAR);
                return date.plusDays(doq);
            }
            @Override
            public String toString() {
                return "DayOfQuarter";
            }
        },
        QUARTER_OF_YEAR {
            @Override
            public TemporalUnit getBaseUnit() {
                return QUARTER_YEARS;
            }
            @Override
            public TemporalUnit getRangeUnit() {
                return YEARS;
            }
            @Override
            public ValueRange range() {
                return ValueRange.of(1, 4);
            }
            @Override
            public boolean isSupportedBy(TemporalAccessor temporal) {
                return temporal.isSupported(MONTH_OF_YEAR) && isIso(temporal);
            }
            @Override
            public long getFrom(TemporalAccessor temporal) {
                if (isSupportedBy(temporal) == false) {
                    throw new UnsupportedTemporalTypeException("Unsupported field: QuarterOfYear");
                }
                long moy = temporal.getLong(MONTH_OF_YEAR);
                return ((moy + 2) / 3);
            }
            @SuppressWarnings("unchecked")
            @Override
            public <R extends Temporal> R adjustInto(R temporal, long newValue) {
                // calls getFrom() to check if supported
                long curValue = getFrom(temporal);
                range().checkValidValue(newValue, this);  // strictly check from 1 to 4
                return (R) temporal.with(MONTH_OF_YEAR, temporal.getLong(MONTH_OF_YEAR) + (newValue - curValue) * 3);
            }
            @Override
            public String toString() {
                return "QuarterOfYear";
            }
        },
        WEEK_OF_WEEK_BASED_YEAR {
            @Override
            public String getDisplayName(Locale locale) {
                Objects.requireNonNull(locale, "locale");
                LocaleResources lr = LocaleProviderAdapter.getResourceBundleBased()
                                            .getLocaleResources(locale);
                ResourceBundle rb = lr.getJavaTimeFormatData();
                return rb.containsKey("field.week") ? rb.getString("field.week") : toString();
            }

            @Override
            public TemporalUnit getBaseUnit() {
                return WEEKS;
            }
            @Override
            public TemporalUnit getRangeUnit() {
                return WEEK_BASED_YEARS;
            }
            @Override
            public ValueRange range() {
                return ValueRange.of(1, 52, 53);
            }
            @Override
            public boolean isSupportedBy(TemporalAccessor temporal) {
                return temporal.isSupported(EPOCH_DAY) && isIso(temporal);
            }
            @Override
            public ValueRange rangeRefinedBy(TemporalAccessor temporal) {
                if (isSupportedBy(temporal) == false) {
                    throw new UnsupportedTemporalTypeException("Unsupported field: WeekOfWeekBasedYear");
                }
                return getWeekRange(LocalDate.from(temporal));
            }
            @Override
            public long getFrom(TemporalAccessor temporal) {
                if (isSupportedBy(temporal) == false) {
                    throw new UnsupportedTemporalTypeException("Unsupported field: WeekOfWeekBasedYear");
                }
                return getWeek(LocalDate.from(temporal));
            }
            @SuppressWarnings("unchecked")
            @Override
            public <R extends Temporal> R adjustInto(R temporal, long newValue) {
                // calls getFrom() to check if supported
                range().checkValidValue(newValue, this);  // lenient range
                return (R) temporal.plus(Math.subtractExact(newValue, getFrom(temporal)), WEEKS);
            }
            @Override
            public ChronoLocalDate resolve(
                    Map<TemporalField, Long> fieldValues, TemporalAccessor partialTemporal, ResolverStyle resolverStyle) {
                Long wbyLong = fieldValues.get(WEEK_BASED_YEAR);
                Long dowLong = fieldValues.get(DAY_OF_WEEK);
                if (wbyLong == null || dowLong == null) {
                    return null;
                }
                int wby = WEEK_BASED_YEAR.range().checkValidIntValue(wbyLong, WEEK_BASED_YEAR);  // always validate
                long wowby = fieldValues.get(WEEK_OF_WEEK_BASED_YEAR);
                ensureIso(partialTemporal);
                LocalDate date = LocalDate.of(wby, 1, 4);
                if (resolverStyle == ResolverStyle.LENIENT) {
                    long dow = dowLong;  // unvalidated
                    if (dow > 7) {
                        date = date.plusWeeks((dow - 1) / 7);
                        dow = ((dow - 1) % 7) + 1;
                    } else if (dow < 1) {
                        date = date.plusWeeks(Math.subtractExact(dow,  7) / 7);
                        dow = ((dow + 6) % 7) + 1;
                    }
                    date = date.plusWeeks(Math.subtractExact(wowby, 1)).with(DAY_OF_WEEK, dow);
                } else {
                    int dow = DAY_OF_WEEK.checkValidIntValue(dowLong);  // validated
                    if (wowby < 1 || wowby > 52) {
                        if (resolverStyle == ResolverStyle.STRICT) {
                            getWeekRange(date).checkValidValue(wowby, this);  // only allow exact range
                        } else {  // SMART
                            range().checkValidValue(wowby, this);  // allow 1-53 rolling into next year
                        }
                    }
                    date = date.plusWeeks(wowby - 1).with(DAY_OF_WEEK, dow);
                }
                fieldValues.remove(this);
                fieldValues.remove(WEEK_BASED_YEAR);
                fieldValues.remove(DAY_OF_WEEK);
                return date;
            }
            @Override
            public String toString() {
                return "WeekOfWeekBasedYear";
            }
        },
        WEEK_BASED_YEAR {
            @Override
            public TemporalUnit getBaseUnit() {
                return WEEK_BASED_YEARS;
            }
            @Override
            public TemporalUnit getRangeUnit() {
                return FOREVER;
            }
            @Override
            public ValueRange range() {
                return YEAR.range();
            }
            @Override
            public boolean isSupportedBy(TemporalAccessor temporal) {
                return temporal.isSupported(EPOCH_DAY) && isIso(temporal);
            }
            @Override
            public long getFrom(TemporalAccessor temporal) {
                if (isSupportedBy(temporal) == false) {
                    throw new UnsupportedTemporalTypeException("Unsupported field: WeekBasedYear");
                }
                return getWeekBasedYear(LocalDate.from(temporal));
            }
            @SuppressWarnings("unchecked")
            @Override
            public <R extends Temporal> R adjustInto(R temporal, long newValue) {
                if (isSupportedBy(temporal) == false) {
                    throw new UnsupportedTemporalTypeException("Unsupported field: WeekBasedYear");
                }
                int newWby = range().checkValidIntValue(newValue, WEEK_BASED_YEAR);  // strict check
                LocalDate date = LocalDate.from(temporal);
                int dow = date.get(DAY_OF_WEEK);
                int week = getWeek(date);
                if (week == 53 && getWeekRange(newWby) == 52) {
                    week = 52;
                }
                LocalDate resolved = LocalDate.of(newWby, 1, 4);  // 4th is guaranteed to be in week one
                int days = (dow - resolved.get(DAY_OF_WEEK)) + ((week - 1) * 7);
                resolved = resolved.plusDays(days);
                return (R) temporal.with(resolved);
            }
            @Override
            public String toString() {
                return "WeekBasedYear";
            }
        };

        @Override
        public boolean isDateBased() {
            return true;
        }

        @Override
        public boolean isTimeBased() {
            return false;
        }

        @Override
        public ValueRange rangeRefinedBy(TemporalAccessor temporal) {
            return range();
        }

        //-------------------------------------------------------------------------
        private static final int[] QUARTER_DAYS = {0, 90, 181, 273, 0, 91, 182, 274};

        private static boolean isIso(TemporalAccessor temporal) {
            return Chronology.from(temporal).equals(IsoChronology.INSTANCE);
        }

        private static void ensureIso(TemporalAccessor temporal) {
            if (isIso(temporal) == false) {
                throw new DateTimeException("Resolve requires IsoChronology");
            }
        }

        private static ValueRange getWeekRange(LocalDate date) {
            int wby = getWeekBasedYear(date);
            return ValueRange.of(1, getWeekRange(wby));
        }

        private static int getWeekRange(int wby) {
            LocalDate date = LocalDate.of(wby, 1, 1);
            // 53 weeks if standard year starts on Thursday, or Wed in a leap year
            if (date.getDayOfWeek() == THURSDAY || (date.getDayOfWeek() == WEDNESDAY && date.isLeapYear())) {
                return 53;
            }
            return 52;
        }

        private static int getWeek(LocalDate date) {
            int dow0 = date.getDayOfWeek().ordinal();
            int doy0 = date.getDayOfYear() - 1;
            int doyThu0 = doy0 + (3 - dow0);  // adjust to mid-week Thursday (which is 3 indexed from zero)
            int alignedWeek = doyThu0 / 7;
            int firstThuDoy0 = doyThu0 - (alignedWeek * 7);
            int firstMonDoy0 = firstThuDoy0 - 3;
            if (firstMonDoy0 < -3) {
                firstMonDoy0 += 7;
            }
            if (doy0 < firstMonDoy0) {
                return (int) getWeekRange(date.withDayOfYear(180).minusYears(1)).getMaximum();
            }
            int week = ((doy0 - firstMonDoy0) / 7) + 1;
            if (week == 53) {
                if ((firstMonDoy0 == -3 || (firstMonDoy0 == -2 && date.isLeapYear())) == false) {
                    week = 1;
                }
            }
            return week;
        }

        private static int getWeekBasedYear(LocalDate date) {
            int year = date.getYear();
            int doy = date.getDayOfYear();
            if (doy <= 3) {
                int dow = date.getDayOfWeek().ordinal();
                if (doy - dow < -2) {
                    year--;
                }
            } else if (doy >= 363) {
                int dow = date.getDayOfWeek().ordinal();
                doy = doy - 363 - (date.isLeapYear() ? 1 : 0);
                if (doy - dow >= 0) {
                    year++;
                }
            }
            return year;
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Implementation of the period unit.
     * <p>
     *  执行期间单位。
     * 
     */
    private static enum Unit implements TemporalUnit {

        /**
         * Unit that represents the concept of a week-based-year.
         * <p>
         *  表示每周基于年的概念的单位。
         * 
         */
        WEEK_BASED_YEARS("WeekBasedYears", Duration.ofSeconds(31556952L)),
        /**
         * Unit that represents the concept of a quarter-year.
         * <p>
         *  代表四分之一年概念的单位。
         */
        QUARTER_YEARS("QuarterYears", Duration.ofSeconds(31556952L / 4));

        private final String name;
        private final Duration duration;

        private Unit(String name, Duration estimatedDuration) {
            this.name = name;
            this.duration = estimatedDuration;
        }

        @Override
        public Duration getDuration() {
            return duration;
        }

        @Override
        public boolean isDurationEstimated() {
            return true;
        }

        @Override
        public boolean isDateBased() {
            return true;
        }

        @Override
        public boolean isTimeBased() {
            return false;
        }

        @Override
        public boolean isSupportedBy(Temporal temporal) {
            return temporal.isSupported(EPOCH_DAY);
        }

        @SuppressWarnings("unchecked")
        @Override
        public <R extends Temporal> R addTo(R temporal, long amount) {
            switch (this) {
                case WEEK_BASED_YEARS:
                    return (R) temporal.with(WEEK_BASED_YEAR,
                            Math.addExact(temporal.get(WEEK_BASED_YEAR), amount));
                case QUARTER_YEARS:
                    // no overflow (256 is multiple of 4)
                    return (R) temporal.plus(amount / 256, YEARS)
                            .plus((amount % 256) * 3, MONTHS);
                default:
                    throw new IllegalStateException("Unreachable");
            }
        }

        @Override
        public long between(Temporal temporal1Inclusive, Temporal temporal2Exclusive) {
            if (temporal1Inclusive.getClass() != temporal2Exclusive.getClass()) {
                return temporal1Inclusive.until(temporal2Exclusive, this);
            }
            switch(this) {
                case WEEK_BASED_YEARS:
                    return Math.subtractExact(temporal2Exclusive.getLong(WEEK_BASED_YEAR),
                            temporal1Inclusive.getLong(WEEK_BASED_YEAR));
                case QUARTER_YEARS:
                    return temporal1Inclusive.until(temporal2Exclusive, MONTHS) / 3;
                default:
                    throw new IllegalStateException("Unreachable");
            }
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
