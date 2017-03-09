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
 *
 *
 *
 *
 *
 * Copyright (c) 2012-2013, Stephen Colebourne & Michael Nascimento Santos
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
 *  版权所有(c)2012-2013,Stephen Colebourne和Michael Nascimento Santos
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

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.YEARS;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;
import java.util.function.UnaryOperator;

/**
 * Common and useful TemporalAdjusters.
 * <p>
 * Adjusters are a key tool for modifying temporal objects.
 * They exist to externalize the process of adjustment, permitting different
 * approaches, as per the strategy design pattern.
 * Examples might be an adjuster that sets the date avoiding weekends, or one that
 * sets the date to the last day of the month.
 * <p>
 * There are two equivalent ways of using a {@code TemporalAdjuster}.
 * The first is to invoke the method on the interface directly.
 * The second is to use {@link Temporal#with(TemporalAdjuster)}:
 * <pre>
 *   // these two lines are equivalent, but the second approach is recommended
 *   temporal = thisAdjuster.adjustInto(temporal);
 *   temporal = temporal.with(thisAdjuster);
 * </pre>
 * It is recommended to use the second approach, {@code with(TemporalAdjuster)},
 * as it is a lot clearer to read in code.
 * <p>
 * This class contains a standard set of adjusters, available as static methods.
 * These include:
 * <ul>
 * <li>finding the first or last day of the month
 * <li>finding the first day of next month
 * <li>finding the first or last day of the year
 * <li>finding the first day of next year
 * <li>finding the first or last day-of-week within a month, such as "first Wednesday in June"
 * <li>finding the next or previous day-of-week, such as "next Thursday"
 * </ul>
 *
 * @implSpec
 * All the implementations supplied by the static methods are immutable.
 *
 * <p>
 *  常用和有用的TemporalAdjusters。
 * <p>
 *  调整器是修改时态对象的关键工具。它们存在于外部化调整的过程,允许不同的方法,根据战略设计模式。示例可以是设置避免周末的日期的调整器,或者将日期设置为该月的最后一天的日期。
 * <p>
 *  有两种等效的使用{@code TemporalAdjuster}的方法。第一种是直接在接口上调用方法。第二个是使用{@link Temporal#with(TemporalAdjuster)}：
 * <pre>
 *  //这两行是等效的,但第二种方法是建议temporal = thisAdjuster.adjustInto(temporal); temporal = temporal.with(thisAdjust
 * er);。
 * </pre>
 * 建议使用第二种方法,{@code with(TemporalAdjuster)},因为它在代码中很容易阅读。
 * <p>
 *  这个类包含一组标准的调整器,可用作静态方法。这些包括：
 * <ul>
 *  <li>查找每月的第一天或最后一天<li>查找下个月的第一天<li>查找一年中的第一天或最后一天<li>找到明年的第一天<li>找到第一个或在一个月内的最后一天,例如"六月的第一个星期三"<li>找到
 * 下一个或前一个星期,例如"下星期四"。
 * </ul>
 * 
 *  @implSpec静态方法提供的所有实现都是不可变的。
 * 
 * 
 * @see TemporalAdjuster
 * @since 1.8
 */
public final class TemporalAdjusters {

    /**
     * Private constructor since this is a utility class.
     * <p>
     *  私人构造函数,因为这是一个实用程序类。
     * 
     */
    private TemporalAdjusters() {
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains a {@code TemporalAdjuster} that wraps a date adjuster.
     * <p>
     * The {@code TemporalAdjuster} is based on the low level {@code Temporal} interface.
     * This method allows an adjustment from {@code LocalDate} to {@code LocalDate}
     * to be wrapped to match the temporal-based interface.
     * This is provided for convenience to make user-written adjusters simpler.
     * <p>
     * In general, user-written adjusters should be static constants:
     * <pre>{@code
     *  static TemporalAdjuster TWO_DAYS_LATER =
     *       TemporalAdjusters.ofDateAdjuster(date -> date.plusDays(2));
     * }</pre>
     *
     * <p>
     *  获取一个包含日期调整器的{@code TemporalAdjuster}。
     * <p>
     *  {@code TemporalAdjuster}基于低级{@code Temporal}接口。
     * 此方法允许从{@code LocalDate}到{@code LocalDate}的调整被包装以匹配基于时间的接口。这是为了方便使用户编写的调节器更简单。
     * <p>
     *  一般来说,用户编写的调整器应该是静态常量：<pre> {@ code static TemporalAdjuster TWO_DAYS_LATER = TemporalAdjusters.ofDateAdjuster(date  - > date.plusDays(2)); }
     *  </pre>。
     * 
     * 
     * @param dateBasedAdjuster  the date-based adjuster, not null
     * @return the temporal adjuster wrapping on the date adjuster, not null
     */
    public static TemporalAdjuster ofDateAdjuster(UnaryOperator<LocalDate> dateBasedAdjuster) {
        Objects.requireNonNull(dateBasedAdjuster, "dateBasedAdjuster");
        return (temporal) -> {
            LocalDate input = LocalDate.from(temporal);
            LocalDate output = dateBasedAdjuster.apply(input);
            return temporal.with(output);
        };
    }

    //-----------------------------------------------------------------------
    /**
     * Returns the "first day of month" adjuster, which returns a new date set to
     * the first day of the current month.
     * <p>
     * The ISO calendar system behaves as follows:<br>
     * The input 2011-01-15 will return 2011-01-01.<br>
     * The input 2011-02-15 will return 2011-02-01.
     * <p>
     * The behavior is suitable for use with most calendar systems.
     * It is equivalent to:
     * <pre>
     *  temporal.with(DAY_OF_MONTH, 1);
     * </pre>
     *
     * <p>
     *  返回"第一天的日期"调整器,它返回一个新日期设置为当前月份的第一天。
     * <p>
     *  ISO日历系统的行为如下：<br>输入2011-01-15将返回2011-01-01。<br>输入2011-02-15将返回2011-02-01。
     * <p>
     * 该行为适用于大多数日历系统。它等效于：
     * <pre>
     *  temporal.with(DAY_OF_MONTH,1);
     * </pre>
     * 
     * 
     * @return the first day-of-month adjuster, not null
     */
    public static TemporalAdjuster firstDayOfMonth() {
        return (temporal) -> temporal.with(DAY_OF_MONTH, 1);
    }

    /**
     * Returns the "last day of month" adjuster, which returns a new date set to
     * the last day of the current month.
     * <p>
     * The ISO calendar system behaves as follows:<br>
     * The input 2011-01-15 will return 2011-01-31.<br>
     * The input 2011-02-15 will return 2011-02-28.<br>
     * The input 2012-02-15 will return 2012-02-29 (leap year).<br>
     * The input 2011-04-15 will return 2011-04-30.
     * <p>
     * The behavior is suitable for use with most calendar systems.
     * It is equivalent to:
     * <pre>
     *  long lastDay = temporal.range(DAY_OF_MONTH).getMaximum();
     *  temporal.with(DAY_OF_MONTH, lastDay);
     * </pre>
     *
     * <p>
     *  返回"月份的最后一天"调整器,它返回设置为当前月份最后一天的新日期。
     * <p>
     *  ISO日历系统的行为如下：<br>输入2011-01-15将返回2011-01-31。<br>输入2011-02-15将返回2011-02-28。
     * <br>输入2012 -02-15将返回2012-02-29(闰年)。<br>输入2011-04-15将返回2011-04-30。
     * <p>
     *  该行为适用于大多数日历系统。它等效于：
     * <pre>
     *  long lastDay = temporal.range(DAY_OF_MONTH).getMaximum(); temporal.with(DAY_OF_MONTH,lastDay);
     * </pre>
     * 
     * 
     * @return the last day-of-month adjuster, not null
     */
    public static TemporalAdjuster lastDayOfMonth() {
        return (temporal) -> temporal.with(DAY_OF_MONTH, temporal.range(DAY_OF_MONTH).getMaximum());
    }

    /**
     * Returns the "first day of next month" adjuster, which returns a new date set to
     * the first day of the next month.
     * <p>
     * The ISO calendar system behaves as follows:<br>
     * The input 2011-01-15 will return 2011-02-01.<br>
     * The input 2011-02-15 will return 2011-03-01.
     * <p>
     * The behavior is suitable for use with most calendar systems.
     * It is equivalent to:
     * <pre>
     *  temporal.with(DAY_OF_MONTH, 1).plus(1, MONTHS);
     * </pre>
     *
     * <p>
     *  返回"下个月的第一天"调整器,它返回一个新日期设置为下个月的第一天。
     * <p>
     *  ISO日历系统的行为如下：<br>输入2011-01-15将返回2011-02-01。<br>输入2011-02-15将返回2011-03-01。
     * <p>
     *  该行为适用于大多数日历系统。它等效于：
     * <pre>
     *  temporal.with(DAY_OF_MONTH,1).plus(1,MONTHS);
     * </pre>
     * 
     * 
     * @return the first day of next month adjuster, not null
     */
    public static TemporalAdjuster firstDayOfNextMonth() {
        return (temporal) -> temporal.with(DAY_OF_MONTH, 1).plus(1, MONTHS);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns the "first day of year" adjuster, which returns a new date set to
     * the first day of the current year.
     * <p>
     * The ISO calendar system behaves as follows:<br>
     * The input 2011-01-15 will return 2011-01-01.<br>
     * The input 2011-02-15 will return 2011-01-01.<br>
     * <p>
     * The behavior is suitable for use with most calendar systems.
     * It is equivalent to:
     * <pre>
     *  temporal.with(DAY_OF_YEAR, 1);
     * </pre>
     *
     * <p>
     *  返回"年度第一天"调整器,它返回设置为当前年份第一天的新日期。
     * <p>
     *  ISO日历系统的行为如下：<br>输入2011-01-15将返回2011-01-01。<br>输入2011-02-15将返回2011-01-01。<br>
     * <p>
     *  该行为适用于大多数日历系统。它等效于：
     * <pre>
     *  temporal.with(DAY_OF_YEAR,1);
     * </pre>
     * 
     * 
     * @return the first day-of-year adjuster, not null
     */
    public static TemporalAdjuster firstDayOfYear() {
        return (temporal) -> temporal.with(DAY_OF_YEAR, 1);
    }

    /**
     * Returns the "last day of year" adjuster, which returns a new date set to
     * the last day of the current year.
     * <p>
     * The ISO calendar system behaves as follows:<br>
     * The input 2011-01-15 will return 2011-12-31.<br>
     * The input 2011-02-15 will return 2011-12-31.<br>
     * <p>
     * The behavior is suitable for use with most calendar systems.
     * It is equivalent to:
     * <pre>
     *  long lastDay = temporal.range(DAY_OF_YEAR).getMaximum();
     *  temporal.with(DAY_OF_YEAR, lastDay);
     * </pre>
     *
     * <p>
     * 返回"最后一天"调整器,它返回一个新日期设置为当前年份的最后一天。
     * <p>
     *  ISO日历系统的行为如下：<br>输入2011-01-15将返回2011-12-31。<br>输入2011-02-15将返回2011-12-31。<br>
     * <p>
     *  该行为适用于大多数日历系统。它等效于：
     * <pre>
     *  long lastDay = temporal.range(DAY_OF_YEAR).getMaximum(); temporal.with(DAY_OF_YEAR,lastDay);
     * </pre>
     * 
     * 
     * @return the last day-of-year adjuster, not null
     */
    public static TemporalAdjuster lastDayOfYear() {
        return (temporal) -> temporal.with(DAY_OF_YEAR, temporal.range(DAY_OF_YEAR).getMaximum());
    }

    /**
     * Returns the "first day of next year" adjuster, which returns a new date set to
     * the first day of the next year.
     * <p>
     * The ISO calendar system behaves as follows:<br>
     * The input 2011-01-15 will return 2012-01-01.
     * <p>
     * The behavior is suitable for use with most calendar systems.
     * It is equivalent to:
     * <pre>
     *  temporal.with(DAY_OF_YEAR, 1).plus(1, YEARS);
     * </pre>
     *
     * <p>
     *  返回"明年第一天"调整器,它返回一个新日期设置为下一年的第一天。
     * <p>
     *  ISO日历系统的行为如下：<br>输入2011-01-15将返回2012-01-01。
     * <p>
     *  该行为适用于大多数日历系统。它等效于：
     * <pre>
     *  temporal.with(DAY_OF_YEAR,1).plus(1,YEARS);
     * </pre>
     * 
     * 
     * @return the first day of next month adjuster, not null
     */
    public static TemporalAdjuster firstDayOfNextYear() {
        return (temporal) -> temporal.with(DAY_OF_YEAR, 1).plus(1, YEARS);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns the first in month adjuster, which returns a new date
     * in the same month with the first matching day-of-week.
     * This is used for expressions like 'first Tuesday in March'.
     * <p>
     * The ISO calendar system behaves as follows:<br>
     * The input 2011-12-15 for (MONDAY) will return 2011-12-05.<br>
     * The input 2011-12-15 for (FRIDAY) will return 2011-12-02.<br>
     * <p>
     * The behavior is suitable for use with most calendar systems.
     * It uses the {@code DAY_OF_WEEK} and {@code DAY_OF_MONTH} fields
     * and the {@code DAYS} unit, and assumes a seven day week.
     *
     * <p>
     *  返回月份中的第一个调整器,它返回同一月份中与第一个匹配星期几相同的新日期。这用于表示"三月第一个星期二"。
     * <p>
     *  ISO日历系统的行为如下：<br> 2011年12月15日(MONDAY)的输入将返回2011-12-05。
     * <br> 2011-12-15(FRIDAY)的输入将返回2011-12- 02. <br>。
     * <p>
     *  该行为适用于大多数日历系统。它使用{@code DAY_OF_WEEK}和{@code DAY_OF_MONTH}字段和{@code DAYS}单位,假设为每周七天。
     * 
     * 
     * @param dayOfWeek  the day-of-week, not null
     * @return the first in month adjuster, not null
     */
    public static TemporalAdjuster firstInMonth(DayOfWeek dayOfWeek) {
        return TemporalAdjusters.dayOfWeekInMonth(1, dayOfWeek);
    }

    /**
     * Returns the last in month adjuster, which returns a new date
     * in the same month with the last matching day-of-week.
     * This is used for expressions like 'last Tuesday in March'.
     * <p>
     * The ISO calendar system behaves as follows:<br>
     * The input 2011-12-15 for (MONDAY) will return 2011-12-26.<br>
     * The input 2011-12-15 for (FRIDAY) will return 2011-12-30.<br>
     * <p>
     * The behavior is suitable for use with most calendar systems.
     * It uses the {@code DAY_OF_WEEK} and {@code DAY_OF_MONTH} fields
     * and the {@code DAYS} unit, and assumes a seven day week.
     *
     * <p>
     *  返回月份调整器中的最后一个月,它返回与上一个匹配星期几相同的月份中的新日期。这用于表示"三月最后一个星期二"。
     * <p>
     * ISO日历系统的行为如下：<br> 2011-12-15(MONDAY)的输入将返回2011-12-26。<br> 2011-12-15(FRIDAY)的输入将返回2011-12- 30. <br>
     * <p>
     *  该行为适用于大多数日历系统。它使用{@code DAY_OF_WEEK}和{@code DAY_OF_MONTH}字段和{@code DAYS}单位,假设为每周七天。
     * 
     * 
     * @param dayOfWeek  the day-of-week, not null
     * @return the first in month adjuster, not null
     */
    public static TemporalAdjuster lastInMonth(DayOfWeek dayOfWeek) {
        return TemporalAdjusters.dayOfWeekInMonth(-1, dayOfWeek);
    }

    /**
     * Returns the day-of-week in month adjuster, which returns a new date
     * in the same month with the ordinal day-of-week.
     * This is used for expressions like the 'second Tuesday in March'.
     * <p>
     * The ISO calendar system behaves as follows:<br>
     * The input 2011-12-15 for (1,TUESDAY) will return 2011-12-06.<br>
     * The input 2011-12-15 for (2,TUESDAY) will return 2011-12-13.<br>
     * The input 2011-12-15 for (3,TUESDAY) will return 2011-12-20.<br>
     * The input 2011-12-15 for (4,TUESDAY) will return 2011-12-27.<br>
     * The input 2011-12-15 for (5,TUESDAY) will return 2012-01-03.<br>
     * The input 2011-12-15 for (-1,TUESDAY) will return 2011-12-27 (last in month).<br>
     * The input 2011-12-15 for (-4,TUESDAY) will return 2011-12-06 (3 weeks before last in month).<br>
     * The input 2011-12-15 for (-5,TUESDAY) will return 2011-11-29 (4 weeks before last in month).<br>
     * The input 2011-12-15 for (0,TUESDAY) will return 2011-11-29 (last in previous month).<br>
     * <p>
     * For a positive or zero ordinal, the algorithm is equivalent to finding the first
     * day-of-week that matches within the month and then adding a number of weeks to it.
     * For a negative ordinal, the algorithm is equivalent to finding the last
     * day-of-week that matches within the month and then subtracting a number of weeks to it.
     * The ordinal number of weeks is not validated and is interpreted leniently
     * according to this algorithm. This definition means that an ordinal of zero finds
     * the last matching day-of-week in the previous month.
     * <p>
     * The behavior is suitable for use with most calendar systems.
     * It uses the {@code DAY_OF_WEEK} and {@code DAY_OF_MONTH} fields
     * and the {@code DAYS} unit, and assumes a seven day week.
     *
     * <p>
     *  返回月调整程序中的星期几,它返回同一月份的新日期和序数星期。这用于表达式,如"3月的第二个星期二"。
     * <p>
     *  ISO日历系统的行为如下：<br> 2011年12月15日(1,TUESDAY)的输入2011-12-15将返回2011-12-06。
     * <br> 2011年12月15日(2,TUESDAY)的输入将返回2011-12-13。<br> 2011年12月15日(3,TUESDAY)的输入将返回2011-12-20。
     * <br> 2011年12月15日(4,TUESDAY)的输入将返回2011- 12-27。<br>输入2011-12-15(5,TUESDAY)将返回2012-01-03。
     * <br>输入2011-12-15(-1,TUESDAY)将返回2011-12 -27(月内)。<br>输入2011-12-15(-4,TUESDAY)将返回2011-12-06(月份最后一周前3周)。
     * <br>输入2011-12- 15  - (-5,TUESDAY)将返回2011-11-29(月份最后一个月前的4周)。
     * <br> 2011年12月15日(0,TUESDAY)的输入将返回2011-11-29上个月)。<br>。
     * <p>
     * 对于正数或零序数,算法等效于找到在该月内匹配的第一个星期几,然后将星期数加上。对于负序数,该算法等效于找到在该月内匹配的最后一个星期,然后减去星期数。序数周不被验证,并且根据该算法宽松地解释。
     * 此定义意味着零的序数找到上一个月中最后一个匹配的星期几。
     * <p>
     *  该行为适用于大多数日历系统。它使用{@code DAY_OF_WEEK}和{@code DAY_OF_MONTH}字段和{@code DAYS}单位,假设为每周七天。
     * 
     * 
     * @param ordinal  the week within the month, unbounded but typically from -5 to 5
     * @param dayOfWeek  the day-of-week, not null
     * @return the day-of-week in month adjuster, not null
     */
    public static TemporalAdjuster dayOfWeekInMonth(int ordinal, DayOfWeek dayOfWeek) {
        Objects.requireNonNull(dayOfWeek, "dayOfWeek");
        int dowValue = dayOfWeek.getValue();
        if (ordinal >= 0) {
            return (temporal) -> {
                Temporal temp = temporal.with(DAY_OF_MONTH, 1);
                int curDow = temp.get(DAY_OF_WEEK);
                int dowDiff = (dowValue - curDow + 7) % 7;
                dowDiff += (ordinal - 1L) * 7L;  // safe from overflow
                return temp.plus(dowDiff, DAYS);
            };
        } else {
            return (temporal) -> {
                Temporal temp = temporal.with(DAY_OF_MONTH, temporal.range(DAY_OF_MONTH).getMaximum());
                int curDow = temp.get(DAY_OF_WEEK);
                int daysDiff = dowValue - curDow;
                daysDiff = (daysDiff == 0 ? 0 : (daysDiff > 0 ? daysDiff - 7 : daysDiff));
                daysDiff -= (-ordinal - 1L) * 7L;  // safe from overflow
                return temp.plus(daysDiff, DAYS);
            };
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Returns the next day-of-week adjuster, which adjusts the date to the
     * first occurrence of the specified day-of-week after the date being adjusted.
     * <p>
     * The ISO calendar system behaves as follows:<br>
     * The input 2011-01-15 (a Saturday) for parameter (MONDAY) will return 2011-01-17 (two days later).<br>
     * The input 2011-01-15 (a Saturday) for parameter (WEDNESDAY) will return 2011-01-19 (four days later).<br>
     * The input 2011-01-15 (a Saturday) for parameter (SATURDAY) will return 2011-01-22 (seven days later).
     * <p>
     * The behavior is suitable for use with most calendar systems.
     * It uses the {@code DAY_OF_WEEK} field and the {@code DAYS} unit,
     * and assumes a seven day week.
     *
     * <p>
     *  返回下一个星期调整器,它将日期调整为在调整日期后指定的星期几的第一个日期。
     * <p>
     *  ISO日历系统的行为如下：<br>参数(MONDAY)的输入2011-01-15(星期六)将返回2011-01-17(两天后)。
     * <br>输入2011-01-15 (星期六)参数(WEDNESDAY)将返回2011-01-19(四天后)。
     * <br>参数(SATURDAY)的输入2011-01-15(星期六)将返回2011-01-22天后)。
     * <p>
     *  该行为适用于大多数日历系统。它使用{@code DAY_OF_WEEK}字段和{@code DAYS}单位,假设每周七天。
     * 
     * 
     * @param dayOfWeek  the day-of-week to move the date to, not null
     * @return the next day-of-week adjuster, not null
     */
    public static TemporalAdjuster next(DayOfWeek dayOfWeek) {
        int dowValue = dayOfWeek.getValue();
        return (temporal) -> {
            int calDow = temporal.get(DAY_OF_WEEK);
            int daysDiff = calDow - dowValue;
            return temporal.plus(daysDiff >= 0 ? 7 - daysDiff : -daysDiff, DAYS);
        };
    }

    /**
     * Returns the next-or-same day-of-week adjuster, which adjusts the date to the
     * first occurrence of the specified day-of-week after the date being adjusted
     * unless it is already on that day in which case the same object is returned.
     * <p>
     * The ISO calendar system behaves as follows:<br>
     * The input 2011-01-15 (a Saturday) for parameter (MONDAY) will return 2011-01-17 (two days later).<br>
     * The input 2011-01-15 (a Saturday) for parameter (WEDNESDAY) will return 2011-01-19 (four days later).<br>
     * The input 2011-01-15 (a Saturday) for parameter (SATURDAY) will return 2011-01-15 (same as input).
     * <p>
     * The behavior is suitable for use with most calendar systems.
     * It uses the {@code DAY_OF_WEEK} field and the {@code DAYS} unit,
     * and assumes a seven day week.
     *
     * <p>
     * 返回下一个或相同的星期调整器,它将日期调整为调整日期后指定的星期几的第一个日期,除非它已经在该日期,在这种情况下返回相同的对象。
     * <p>
     *  ISO日历系统的行为如下：<br>参数(MONDAY)的输入2011-01-15(星期六)将返回2011-01-17(两天后)。
     * <br>输入2011-01-15 (星期六)的参数(WEDNESDAY)将返回2011-01-19(四天后)。
     * <br>参数(SATURDAY)的输入2011-01-15(星期六)将返回2011-01-15作为输入)。
     * <p>
     *  该行为适用于大多数日历系统。它使用{@code DAY_OF_WEEK}字段和{@code DAYS}单位,假设每周七天。
     * 
     * 
     * @param dayOfWeek  the day-of-week to check for or move the date to, not null
     * @return the next-or-same day-of-week adjuster, not null
     */
    public static TemporalAdjuster nextOrSame(DayOfWeek dayOfWeek) {
        int dowValue = dayOfWeek.getValue();
        return (temporal) -> {
            int calDow = temporal.get(DAY_OF_WEEK);
            if (calDow == dowValue) {
                return temporal;
            }
            int daysDiff = calDow - dowValue;
            return temporal.plus(daysDiff >= 0 ? 7 - daysDiff : -daysDiff, DAYS);
        };
    }

    /**
     * Returns the previous day-of-week adjuster, which adjusts the date to the
     * first occurrence of the specified day-of-week before the date being adjusted.
     * <p>
     * The ISO calendar system behaves as follows:<br>
     * The input 2011-01-15 (a Saturday) for parameter (MONDAY) will return 2011-01-10 (five days earlier).<br>
     * The input 2011-01-15 (a Saturday) for parameter (WEDNESDAY) will return 2011-01-12 (three days earlier).<br>
     * The input 2011-01-15 (a Saturday) for parameter (SATURDAY) will return 2011-01-08 (seven days earlier).
     * <p>
     * The behavior is suitable for use with most calendar systems.
     * It uses the {@code DAY_OF_WEEK} field and the {@code DAYS} unit,
     * and assumes a seven day week.
     *
     * <p>
     *  返回上一周的日期调整器,它将日期调整为在调整日期之前指定的星期几的第一个日期。
     * <p>
     *  ISO日历系统的行为如下：<br>参数(MONDAY)的输入2011-01-15(星期六)将返回2011-01-10(提前五天)。
     * <br>输入2011-01-15 (星期六)参数(WEDNESDAY)将返回2011-01-12(三天前)。
     * <br>参数(SATURDAY)的输入2011-01-15(星期六)将返回2011-01-08天)。
     * <p>
     *  该行为适用于大多数日历系统。它使用{@code DAY_OF_WEEK}字段和{@code DAYS}单位,假设每周七天。
     * 
     * 
     * @param dayOfWeek  the day-of-week to move the date to, not null
     * @return the previous day-of-week adjuster, not null
     */
    public static TemporalAdjuster previous(DayOfWeek dayOfWeek) {
        int dowValue = dayOfWeek.getValue();
        return (temporal) -> {
            int calDow = temporal.get(DAY_OF_WEEK);
            int daysDiff = dowValue - calDow;
            return temporal.minus(daysDiff >= 0 ? 7 - daysDiff : -daysDiff, DAYS);
        };
    }

    /**
     * Returns the previous-or-same day-of-week adjuster, which adjusts the date to the
     * first occurrence of the specified day-of-week before the date being adjusted
     * unless it is already on that day in which case the same object is returned.
     * <p>
     * The ISO calendar system behaves as follows:<br>
     * The input 2011-01-15 (a Saturday) for parameter (MONDAY) will return 2011-01-10 (five days earlier).<br>
     * The input 2011-01-15 (a Saturday) for parameter (WEDNESDAY) will return 2011-01-12 (three days earlier).<br>
     * The input 2011-01-15 (a Saturday) for parameter (SATURDAY) will return 2011-01-15 (same as input).
     * <p>
     * The behavior is suitable for use with most calendar systems.
     * It uses the {@code DAY_OF_WEEK} field and the {@code DAYS} unit,
     * and assumes a seven day week.
     *
     * <p>
     * 返回前一个或相同的星期调整器,它将日期调整为在调整日期之前指定的星期几的第一个日期,除非它已经在该日期,在这种情况下返回相同的对象。
     * <p>
     *  ISO日历系统的行为如下：<br>参数(MONDAY)的输入2011-01-15(星期六)将返回2011-01-10(提前五天)。
     * <br>输入2011-01-15 (星期六)参数(WEDNESDAY)将返回2011-01-12(三天前)。
     * 
     * @param dayOfWeek  the day-of-week to check for or move the date to, not null
     * @return the previous-or-same day-of-week adjuster, not null
     */
    public static TemporalAdjuster previousOrSame(DayOfWeek dayOfWeek) {
        int dowValue = dayOfWeek.getValue();
        return (temporal) -> {
            int calDow = temporal.get(DAY_OF_WEEK);
            if (calDow == dowValue) {
                return temporal;
            }
            int daysDiff = dowValue - calDow;
            return temporal.minus(daysDiff >= 0 ? 7 - daysDiff : -daysDiff, DAYS);
        };
    }

}
