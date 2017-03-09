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
 * Copyright (c) 2012, Stephen Colebourne & Michael Nascimento Santos
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
 *  版权所有(c)2012,Stephen Colebourne和Michael Nascimento Santos
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
package java.time.chrono;

import static java.time.temporal.ChronoField.EPOCH_DAY;
import static java.time.temporal.ChronoField.ERA;
import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoUnit.DAYS;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Comparator;
import java.util.Objects;

/**
 * A date without time-of-day or time-zone in an arbitrary chronology, intended
 * for advanced globalization use cases.
 * <p>
 * <b>Most applications should declare method signatures, fields and variables
 * as {@link LocalDate}, not this interface.</b>
 * <p>
 * A {@code ChronoLocalDate} is the abstract representation of a date where the
 * {@code Chronology chronology}, or calendar system, is pluggable.
 * The date is defined in terms of fields expressed by {@link TemporalField},
 * where most common implementations are defined in {@link ChronoField}.
 * The chronology defines how the calendar system operates and the meaning of
 * the standard fields.
 *
 * <h3>When to use this interface</h3>
 * The design of the API encourages the use of {@code LocalDate} rather than this
 * interface, even in the case where the application needs to deal with multiple
 * calendar systems.
 * <p>
 * This concept can seem surprising at first, as the natural way to globalize an
 * application might initially appear to be to abstract the calendar system.
 * However, as explored below, abstracting the calendar system is usually the wrong
 * approach, resulting in logic errors and hard to find bugs.
 * As such, it should be considered an application-wide architectural decision to choose
 * to use this interface as opposed to {@code LocalDate}.
 *
 * <h3>Architectural issues to consider</h3>
 * These are some of the points that must be considered before using this interface
 * throughout an application.
 * <p>
 * 1) Applications using this interface, as opposed to using just {@code LocalDate},
 * face a significantly higher probability of bugs. This is because the calendar system
 * in use is not known at development time. A key cause of bugs is where the developer
 * applies assumptions from their day-to-day knowledge of the ISO calendar system
 * to code that is intended to deal with any arbitrary calendar system.
 * The section below outlines how those assumptions can cause problems
 * The primary mechanism for reducing this increased risk of bugs is a strong code review process.
 * This should also be considered a extra cost in maintenance for the lifetime of the code.
 * <p>
 * 2) This interface does not enforce immutability of implementations.
 * While the implementation notes indicate that all implementations must be immutable
 * there is nothing in the code or type system to enforce this. Any method declared
 * to accept a {@code ChronoLocalDate} could therefore be passed a poorly or
 * maliciously written mutable implementation.
 * <p>
 * 3) Applications using this interface  must consider the impact of eras.
 * {@code LocalDate} shields users from the concept of eras, by ensuring that {@code getYear()}
 * returns the proleptic year. That decision ensures that developers can think of
 * {@code LocalDate} instances as consisting of three fields - year, month-of-year and day-of-month.
 * By contrast, users of this interface must think of dates as consisting of four fields -
 * era, year-of-era, month-of-year and day-of-month. The extra era field is frequently
 * forgotten, yet it is of vital importance to dates in an arbitrary calendar system.
 * For example, in the Japanese calendar system, the era represents the reign of an Emperor.
 * Whenever one reign ends and another starts, the year-of-era is reset to one.
 * <p>
 * 4) The only agreed international standard for passing a date between two systems
 * is the ISO-8601 standard which requires the ISO calendar system. Using this interface
 * throughout the application will inevitably lead to the requirement to pass the date
 * across a network or component boundary, requiring an application specific protocol or format.
 * <p>
 * 5) Long term persistence, such as a database, will almost always only accept dates in the
 * ISO-8601 calendar system (or the related Julian-Gregorian). Passing around dates in other
 * calendar systems increases the complications of interacting with persistence.
 * <p>
 * 6) Most of the time, passing a {@code ChronoLocalDate} throughout an application
 * is unnecessary, as discussed in the last section below.
 *
 * <h3>False assumptions causing bugs in multi-calendar system code</h3>
 * As indicated above, there are many issues to consider when try to use and manipulate a
 * date in an arbitrary calendar system. These are some of the key issues.
 * <p>
 * Code that queries the day-of-month and assumes that the value will never be more than
 * 31 is invalid. Some calendar systems have more than 31 days in some months.
 * <p>
 * Code that adds 12 months to a date and assumes that a year has been added is invalid.
 * Some calendar systems have a different number of months, such as 13 in the Coptic or Ethiopic.
 * <p>
 * Code that adds one month to a date and assumes that the month-of-year value will increase
 * by one or wrap to the next year is invalid. Some calendar systems have a variable number
 * of months in a year, such as the Hebrew.
 * <p>
 * Code that adds one month, then adds a second one month and assumes that the day-of-month
 * will remain close to its original value is invalid. Some calendar systems have a large difference
 * between the length of the longest month and the length of the shortest month.
 * For example, the Coptic or Ethiopic have 12 months of 30 days and 1 month of 5 days.
 * <p>
 * Code that adds seven days and assumes that a week has been added is invalid.
 * Some calendar systems have weeks of other than seven days, such as the French Revolutionary.
 * <p>
 * Code that assumes that because the year of {@code date1} is greater than the year of {@code date2}
 * then {@code date1} is after {@code date2} is invalid. This is invalid for all calendar systems
 * when referring to the year-of-era, and especially untrue of the Japanese calendar system
 * where the year-of-era restarts with the reign of every new Emperor.
 * <p>
 * Code that treats month-of-year one and day-of-month one as the start of the year is invalid.
 * Not all calendar systems start the year when the month value is one.
 * <p>
 * In general, manipulating a date, and even querying a date, is wide open to bugs when the
 * calendar system is unknown at development time. This is why it is essential that code using
 * this interface is subjected to additional code reviews. It is also why an architectural
 * decision to avoid this interface type is usually the correct one.
 *
 * <h3>Using LocalDate instead</h3>
 * The primary alternative to using this interface throughout your application is as follows.
 * <ul>
 * <li>Declare all method signatures referring to dates in terms of {@code LocalDate}.
 * <li>Either store the chronology (calendar system) in the user profile or lookup
 *  the chronology from the user locale
 * <li>Convert the ISO {@code LocalDate} to and from the user's preferred calendar system during
 *  printing and parsing
 * </ul>
 * This approach treats the problem of globalized calendar systems as a localization issue
 * and confines it to the UI layer. This approach is in keeping with other localization
 * issues in the java platform.
 * <p>
 * As discussed above, performing calculations on a date where the rules of the calendar system
 * are pluggable requires skill and is not recommended.
 * Fortunately, the need to perform calculations on a date in an arbitrary calendar system
 * is extremely rare. For example, it is highly unlikely that the business rules of a library
 * book rental scheme will allow rentals to be for one month, where meaning of the month
 * is dependent on the user's preferred calendar system.
 * <p>
 * A key use case for calculations on a date in an arbitrary calendar system is producing
 * a month-by-month calendar for display and user interaction. Again, this is a UI issue,
 * and use of this interface solely within a few methods of the UI layer may be justified.
 * <p>
 * In any other part of the system, where a date must be manipulated in a calendar system
 * other than ISO, the use case will generally specify the calendar system to use.
 * For example, an application may need to calculate the next Islamic or Hebrew holiday
 * which may require manipulating the date.
 * This kind of use case can be handled as follows:
 * <ul>
 * <li>start from the ISO {@code LocalDate} being passed to the method
 * <li>convert the date to the alternate calendar system, which for this use case is known
 *  rather than arbitrary
 * <li>perform the calculation
 * <li>convert back to {@code LocalDate}
 * </ul>
 * Developers writing low-level frameworks or libraries should also avoid this interface.
 * Instead, one of the two general purpose access interfaces should be used.
 * Use {@link TemporalAccessor} if read-only access is required, or use {@link Temporal}
 * if read-write access is required.
 *
 * @implSpec
 * This interface must be implemented with care to ensure other classes operate correctly.
 * All implementations that can be instantiated must be final, immutable and thread-safe.
 * Subclasses should be Serializable wherever possible.
 * <p>
 * Additional calendar systems may be added to the system.
 * See {@link Chronology} for more details.
 *
 * <p>
 *  在任意年表中没有时间或时区的日期,用于高级全球化用例。
 * <p>
 *  <b>大多数应用程序应该将方法签名,字段和变量声明为{@link LocalDate},而不是此接口。</b>
 * <p>
 *  {@code ChronoLocalDate}是{@code Chronology chronology}或日历系统可插拔日期的抽象表示形式。
 * 日期以由{@link TemporalField}表示的字段来定义,其中最常见的实现在{@link ChronoField}中定义。年表定义日历系统的操作方式和标准字段的含义。
 * 
 * <h3>何时使用此接口</h3> API的设计鼓励使用{@code LocalDate}而不是此接口,即使在应用程序需要处理多个日历系统的情况下。
 * <p>
 *  这个概念首先看起来很奇怪,因为全球化应用程序的自然方式最初似乎是抽象日历系统。然而,如下所述,抽象日历系统通常是错误的方法,导致逻辑错误,很难找到错误。
 * 因此,它应该被视为一个应用程序范围的架构决定,选择使用这个接口,而不是{@code LocalDate}。
 * 
 *  <h3>要考虑的架构问题</h3>这些是在整个应用程序中使用此接口之前必须考虑的一些要点。
 * <p>
 *  1)使用这个接口的应用程序,而不是仅仅使用{@code LocalDate},面临的bug的概率明显更高。这是因为正在使用的日历系统在开发时是未知的。
 * 错误的一个关键原因是开发人员将其日常知识的ISO日历系统的假设应用于旨在处理任何任意日历系统的代码。下面的部分概述了这些假设如何导致问题减少这种增加错误风险的主要机制是强大的代码审查过程。
 * 这也应该被认为是在代码的生命周期的维护中的额外成本。
 * <p>
 * 2)此接口不强制实现的不变性。虽然实现注释表明所有实现必须是不可变的,但是在代码或类型系统中没有任何东西来强制这样做。
 * 任何声明接受{@code ChronoLocalDate}的方法都可能被传递一个糟糕的或恶意写的可变实现。
 * <p>
 *  3)使用该接口的应用必须考虑时间的影响。 {@code LocalDate}通过确保{@code getYear()}返回延迟年度,使用户免受时间的概念。
 * 该决定确保开发人员可以将{@code LocalDate}实例视为由三个字段组成 - 年,月和日。相比之下,该界面的用户必须将日期视为由四个字段组成：时代,年份,年份和月份。
 * 额外的时代领域经常被遗忘,但它对任意日历系统中的日期至关重要。例如,在日本日历系统中,时代代表皇帝的统治。每当一个统治结束和另一个统治开始时,年龄被重置为一。
 * <p>
 *  4)唯一商定的在两个系统之间传递日期的国际标准是ISO-8601标准,其要求ISO日历系统。
 * 在整个应用程序中使用此接口将不可避免地导致需要在网络或组件边界上传递日期,从而需要特定于应用程序的协议或格式。
 * <p>
 * 5)长期持久性,例如数据库,几乎总是只接受ISO-8601日历系统(或相关的朱利安 - 格里高利)中的日期。在其他日历系统中传递日期会增加与持久性交互的复杂性。
 * <p>
 *  6)大多数时候,在整个应用程序中传递{@code ChronoLocalDate}是不必要的,如下面最后一节所讨论的。
 * 
 *  <h3>在多日历系统代码中造成错误的假设假设</h3>如上所述,尝试在任意日历系统中使用和操作日期时,需要考虑很多问题。这些是一些关键问题。
 * <p>
 *  查询月份日期并假定值永远不会大于31的代码无效。某些日历系统在几个月内有超过31天的时间。
 * <p>
 *  将日期添加12个月并假定已添加年份的代码无效。一些日历系统有不同的月数,如在科普特或埃塞俄比亚的13。
 * <p>
 *  将日期添加一个月并假定年份值增加1或换行到下一年的代码无效。一些日历系统在一年中具有可变的月数,例如希伯来语。
 * <p>
 * 添加一个月的代码,然后再增加一个月,并假定月份将保持接近其原始值无效。一些日历系统在最长月份的长度和最短月份的长度之间具有大的差异。例如,科普特或埃塞俄比亚人有12个月的30天和1个月的5天。
 * <p>
 *  添加七天并假设添加一周的代码无效。一些日历系统有七天以外的周,例如法国革命。
 * <p>
 *  假设由于{@code date1}的年份大于{@code date2}的年份,因此{@code date2}无效,因此{@code date1}之后的代码。
 * 这对于所有的日历系统在涉及到年代的时候是无效的,特别是日本日历系统的不真实,那里的年代与每个新皇帝的统治重新开始。
 * <p>
 *  将年月日1和日期月1作为年度开始的代码无效。并非所有日历系统都在月值为1的年份开始。
 * <p>
 *  一般来说,操作日期,甚至查询日期,当日历系统在开发时是未知的时候,对于错误是开放的。这就是为什么使用此接口的代码必须经过额外的代码审查。这也是为什么避免这种接口类型的架构决定通常是正确的。
 * 
 *  <h3>代替使用LocalDate </h3>在整个应用程序中使用此接口的主要替代方法如下。
 * <ul>
 * <li>以{@code LocalDate}的形式声明指向日期的所有方法签名。
 *  <li>将年表(日历系统)存储在用户个人资料中或从用户区域设置查找年表<li>在打印和解析期间将ISO {@code LocalDate}转换为用户首选的日历系统。
 * </ul>
 *  这种方法将全球化日历系统的问题视为本地化问题,并将其限制在UI层。这种方法与Java平台中的其他本地化问题保持一致。
 * <p>
 *  如上所述,在日历系统的规则是可插入的日期执行计算需要技能,并且不推荐。幸运的是,在任意日历系统中对日期执行计算的需要是极其罕见的。
 * 例如,图书馆图书租赁计划的商业规则将允许租赁一个月,其中月份的意义取决于用户的偏好日程表系统是不太可能的。
 * <p>
 *  在任意日历系统中的日期上的计算的关键用例是产生用于显示和用户交互的逐月日历。再次,这是UI问题,并且仅在UI层的几个方法内使用该接口可以是合理的。
 * <p>
 * 在系统的任何其他部分,其中日期必须在除ISO之外的日历系统中操作,该用例通常将指定要使用的日历系统。例如,应用程序可能需要计算可能需要操作日期的下一个伊斯兰或希伯来节日。这种用例可以处理如下：
 * <ul>
 *  <li>从传递给方法的ISO {@code LocalDate} <li>将日期转换为备用日历系统,对于此用例是已知的,而不是任意的<li>执行计算<li>转换回来到{@code LocalDate}
 * 。
 * </ul>
 *  编写低级框架或库的开发人员也应该避免这种接口。相反,应该使用两个通用访问接口之一。
 * 如果需要只读访问,请使用{@link TemporalAccessor},如果需要读写访问,请使用{@link Temporal}。
 * 
 *  @implSpec必须小心地实现此接口,以确保其他类正常运行。所有可以实例化的实现必须是final,immutable和线程安全的。子类应尽可能序列化。
 * <p>
 *  可以向系统添加附加日历系统。有关详细信息,请参阅{@link Chronology}。
 * 
 * 
 * @since 1.8
 */
public interface ChronoLocalDate
        extends Temporal, TemporalAdjuster, Comparable<ChronoLocalDate> {

    /**
     * Gets a comparator that compares {@code ChronoLocalDate} in
     * time-line order ignoring the chronology.
     * <p>
     * This comparator differs from the comparison in {@link #compareTo} in that it
     * only compares the underlying date and not the chronology.
     * This allows dates in different calendar systems to be compared based
     * on the position of the date on the local time-line.
     * The underlying comparison is equivalent to comparing the epoch-day.
     * <p>
     *  获取一个比较器,按时间线顺序比较{@code ChronoLocalDate}忽略年表。
     * <p>
     * 此比较器与{@link #compareTo}中的比较不同,它仅比较基础日期而不是年表。这允许基于本地时间线上的日期的位置来比较不同日历系统中的日期。潜在的比较等同于比较时代。
     * 
     * 
     * @return a comparator that compares in time-line order ignoring the chronology
     *
     * @see #isAfter
     * @see #isBefore
     * @see #isEqual
     */
    static Comparator<ChronoLocalDate> timeLineOrder() {
        return AbstractChronology.DATE_ORDER;
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code ChronoLocalDate} from a temporal object.
     * <p>
     * This obtains a local date based on the specified temporal.
     * A {@code TemporalAccessor} represents an arbitrary set of date and time information,
     * which this factory converts to an instance of {@code ChronoLocalDate}.
     * <p>
     * The conversion extracts and combines the chronology and the date
     * from the temporal object. The behavior is equivalent to using
     * {@link Chronology#date(TemporalAccessor)} with the extracted chronology.
     * Implementations are permitted to perform optimizations such as accessing
     * those fields that are equivalent to the relevant objects.
     * <p>
     * This method matches the signature of the functional interface {@link TemporalQuery}
     * allowing it to be used as a query via method reference, {@code ChronoLocalDate::from}.
     *
     * <p>
     *  从临时对象获取{@code ChronoLocalDate}的实例。
     * <p>
     *  这基于指定的时间获得本地日期。 {@code TemporalAccessor}表示一组任意的日期和时间信息,此工厂将其转换为{@code ChronoLocalDate}的实例。
     * <p>
     *  转换提取并组合来自时间对象的年表和日期。该行为等同于使用{@link Chronology#date(TemporalAccessor)}和提取的年表。
     * 实现允许执行优化,例如访问等价于相关对象的那些字段。
     * <p>
     *  此方法匹配功能接口{@link TemporalQuery}的签名,允许它通过方法引用{@code ChronoLocalDate :: from}用作查询。
     * 
     * 
     * @param temporal  the temporal object to convert, not null
     * @return the date, not null
     * @throws DateTimeException if unable to convert to a {@code ChronoLocalDate}
     * @see Chronology#date(TemporalAccessor)
     */
    static ChronoLocalDate from(TemporalAccessor temporal) {
        if (temporal instanceof ChronoLocalDate) {
            return (ChronoLocalDate) temporal;
        }
        Objects.requireNonNull(temporal, "temporal");
        Chronology chrono = temporal.query(TemporalQueries.chronology());
        if (chrono == null) {
            throw new DateTimeException("Unable to obtain ChronoLocalDate from TemporalAccessor: " + temporal.getClass());
        }
        return chrono.date(temporal);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the chronology of this date.
     * <p>
     * The {@code Chronology} represents the calendar system in use.
     * The era and other fields in {@link ChronoField} are defined by the chronology.
     *
     * <p>
     *  获取此日期的年表。
     * <p>
     *  {@code Chronology}表示正在使用的日历系统。 {@link ChronoField}中的时代和其他字段由年表定义。
     * 
     * 
     * @return the chronology, not null
     */
    Chronology getChronology();

    /**
     * Gets the era, as defined by the chronology.
     * <p>
     * The era is, conceptually, the largest division of the time-line.
     * Most calendar systems have a single epoch dividing the time-line into two eras.
     * However, some have multiple eras, such as one for the reign of each leader.
     * The exact meaning is determined by the {@code Chronology}.
     * <p>
     * All correctly implemented {@code Era} classes are singletons, thus it
     * is valid code to write {@code date.getEra() == SomeChrono.ERA_NAME)}.
     * <p>
     * This default implementation uses {@link Chronology#eraOf(int)}.
     *
     * <p>
     *  获得时代,如年表所定义的。
     * <p>
     * 时代在概念上是时间线的最大分割。大多数日历系统具有将时间线分为两个时间的单个时期。然而,一些人有多个时代,例如每个领导人统治一个时代。确切的含义由{@code Chronology}确定。
     * <p>
     *  所有正确实现的{@code Era}类都是单例,因此它是有效的代码{@code date.getEra()== SomeChrono.ERA_NAME)}。
     * <p>
     *  此默认实现使用{@link Chronology#eraOf(int)}。
     * 
     * 
     * @return the chronology specific era constant applicable at this date, not null
     */
    default Era getEra() {
        return getChronology().eraOf(get(ERA));
    }

    /**
     * Checks if the year is a leap year, as defined by the calendar system.
     * <p>
     * A leap-year is a year of a longer length than normal.
     * The exact meaning is determined by the chronology with the constraint that
     * a leap-year must imply a year-length longer than a non leap-year.
     * <p>
     * This default implementation uses {@link Chronology#isLeapYear(long)}.
     *
     * <p>
     *  检查年份是否为日历系统定义的闰年。
     * <p>
     *  闰年是比正常长的一年。确切的含义是由年表确定的,闰年必须暗示比非闰年更长的年长。
     * <p>
     *  此默认实现使用{@link Chronology#isLeapYear(long)}。
     * 
     * 
     * @return true if this date is in a leap year, false otherwise
     */
    default boolean isLeapYear() {
        return getChronology().isLeapYear(getLong(YEAR));
    }

    /**
     * Returns the length of the month represented by this date, as defined by the calendar system.
     * <p>
     * This returns the length of the month in days.
     *
     * <p>
     *  返回由此日期表示的月份的长度,由日历系统定义。
     * <p>
     *  这将返回月份的长度(以天为单位)。
     * 
     * 
     * @return the length of the month in days
     */
    int lengthOfMonth();

    /**
     * Returns the length of the year represented by this date, as defined by the calendar system.
     * <p>
     * This returns the length of the year in days.
     * <p>
     * The default implementation uses {@link #isLeapYear()} and returns 365 or 366.
     *
     * <p>
     *  返回由此日期表示的年份的长度,由日历系统定义。
     * <p>
     *  这将返回年的长度(以天为单位)。
     * <p>
     *  默认实现使用{@link #isLeapYear()}并返回365或366。
     * 
     * 
     * @return the length of the year in days
     */
    default int lengthOfYear() {
        return (isLeapYear() ? 366 : 365);
    }

    /**
     * Checks if the specified field is supported.
     * <p>
     * This checks if the specified field can be queried on this date.
     * If false, then calling the {@link #range(TemporalField) range},
     * {@link #get(TemporalField) get} and {@link #with(TemporalField, long)}
     * methods will throw an exception.
     * <p>
     * The set of supported fields is defined by the chronology and normally includes
     * all {@code ChronoField} date fields.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.isSupportedBy(TemporalAccessor)}
     * passing {@code this} as the argument.
     * Whether the field is supported is determined by the field.
     *
     * <p>
     *  检查是否支持指定的字段。
     * <p>
     *  这将检查在此日期是否可以查询指定的字段。
     * 如果为false,则调用{@link #range(TemporalField)范围},{@link #get(TemporalField)get}和{@link #with(TemporalField,long)}
     * 方法将抛出异常。
     *  这将检查在此日期是否可以查询指定的字段。
     * <p>
     * 支持字段集由年表定义,通常包括所有{@code ChronoField}日期字段。
     * <p>
     *  如果字段不是{@code ChronoField},那么通过调用{@code TemporalField.isSupportedBy(TemporalAccessor)}传递{@code this}作
     * 为参数来获得此方法的结果。
     * 字段是否受支持由字段确定。
     * 
     * 
     * @param field  the field to check, null returns false
     * @return true if the field can be queried, false if not
     */
    @Override
    default boolean isSupported(TemporalField field) {
        if (field instanceof ChronoField) {
            return field.isDateBased();
        }
        return field != null && field.isSupportedBy(this);
    }

    /**
     * Checks if the specified unit is supported.
     * <p>
     * This checks if the specified unit can be added to or subtracted from this date.
     * If false, then calling the {@link #plus(long, TemporalUnit)} and
     * {@link #minus(long, TemporalUnit) minus} methods will throw an exception.
     * <p>
     * The set of supported units is defined by the chronology and normally includes
     * all {@code ChronoUnit} date units except {@code FOREVER}.
     * <p>
     * If the unit is not a {@code ChronoUnit}, then the result of this method
     * is obtained by invoking {@code TemporalUnit.isSupportedBy(Temporal)}
     * passing {@code this} as the argument.
     * Whether the unit is supported is determined by the unit.
     *
     * <p>
     *  检查是否支持指定的单元。
     * <p>
     *  这将检查指定的单位是否可以从此日期添加或减去。
     * 如果为false,则调用{@link #plus(long,TemporalUnit)}和{@link #minus(long,TemporalUnit)minus}方法将抛出异常。
     * <p>
     *  受支持单位的集合由年表定义,通常包括除{@code FOREVER}之外的所有{@code ChronoUnit}日期单位。
     * <p>
     *  如果单元不是{@code ChronoUnit},那么通过调用{@code TemporalUnit.isSupportedBy(Temporal)}传递{@code this}作为参数来获得此方法的
     * 结果。
     * 单元是否受支持由单元确定。
     * 
     * 
     * @param unit  the unit to check, null returns false
     * @return true if the unit can be added/subtracted, false if not
     */
    @Override
    default boolean isSupported(TemporalUnit unit) {
        if (unit instanceof ChronoUnit) {
            return unit.isDateBased();
        }
        return unit != null && unit.isSupportedBy(this);
    }

    //-----------------------------------------------------------------------
    // override for covariant return type
    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @throws DateTimeException {@inheritDoc}
     * @throws ArithmeticException {@inheritDoc}
     */
    @Override
    default ChronoLocalDate with(TemporalAdjuster adjuster) {
        return ChronoLocalDateImpl.ensureValid(getChronology(), Temporal.super.with(adjuster));
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @throws DateTimeException {@inheritDoc}
     * @throws UnsupportedTemporalTypeException {@inheritDoc}
     * @throws ArithmeticException {@inheritDoc}
     */
    @Override
    default ChronoLocalDate with(TemporalField field, long newValue) {
        if (field instanceof ChronoField) {
            throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
        }
        return ChronoLocalDateImpl.ensureValid(getChronology(), field.adjustInto(this, newValue));
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @throws DateTimeException {@inheritDoc}
     * @throws ArithmeticException {@inheritDoc}
     */
    @Override
    default ChronoLocalDate plus(TemporalAmount amount) {
        return ChronoLocalDateImpl.ensureValid(getChronology(), Temporal.super.plus(amount));
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @throws DateTimeException {@inheritDoc}
     * @throws ArithmeticException {@inheritDoc}
     */
    @Override
    default ChronoLocalDate plus(long amountToAdd, TemporalUnit unit) {
        if (unit instanceof ChronoUnit) {
            throw new UnsupportedTemporalTypeException("Unsupported unit: " + unit);
        }
        return ChronoLocalDateImpl.ensureValid(getChronology(), unit.addTo(this, amountToAdd));
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @throws DateTimeException {@inheritDoc}
     * @throws ArithmeticException {@inheritDoc}
     */
    @Override
    default ChronoLocalDate minus(TemporalAmount amount) {
        return ChronoLocalDateImpl.ensureValid(getChronology(), Temporal.super.minus(amount));
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @throws DateTimeException {@inheritDoc}
     * @throws UnsupportedTemporalTypeException {@inheritDoc}
     * @throws ArithmeticException {@inheritDoc}
     */
    @Override
    default ChronoLocalDate minus(long amountToSubtract, TemporalUnit unit) {
        return ChronoLocalDateImpl.ensureValid(getChronology(), Temporal.super.minus(amountToSubtract, unit));
    }

    //-----------------------------------------------------------------------
    /**
     * Queries this date using the specified query.
     * <p>
     * This queries this date using the specified query strategy object.
     * The {@code TemporalQuery} object defines the logic to be used to
     * obtain the result. Read the documentation of the query to understand
     * what the result of this method will be.
     * <p>
     * The result of this method is obtained by invoking the
     * {@link TemporalQuery#queryFrom(TemporalAccessor)} method on the
     * specified query passing {@code this} as the argument.
     *
     * <p>
     *  使用指定的查询查询此日期。
     * <p>
     *  这将使用指定的查询策略对象查询此日期。 {@code TemporalQuery}对象定义用于获取结果的逻辑。阅读查询的文档以了解此方法的结果。
     * <p>
     * 此方法的结果是通过对指定的查询调用{@link TemporalQuery#queryFrom(TemporalAccessor)}方法传递{@code this}作为参数来获得的。
     * 
     * 
     * @param <R> the type of the result
     * @param query  the query to invoke, not null
     * @return the query result, null may be returned (defined by the query)
     * @throws DateTimeException if unable to query (defined by the query)
     * @throws ArithmeticException if numeric overflow occurs (defined by the query)
     */
    @SuppressWarnings("unchecked")
    @Override
    default <R> R query(TemporalQuery<R> query) {
        if (query == TemporalQueries.zoneId() || query == TemporalQueries.zone() || query == TemporalQueries.offset()) {
            return null;
        } else if (query == TemporalQueries.localTime()) {
            return null;
        } else if (query == TemporalQueries.chronology()) {
            return (R) getChronology();
        } else if (query == TemporalQueries.precision()) {
            return (R) DAYS;
        }
        // inline TemporalAccessor.super.query(query) as an optimization
        // non-JDK classes are not permitted to make this optimization
        return query.queryFrom(this);
    }

    /**
     * Adjusts the specified temporal object to have the same date as this object.
     * <p>
     * This returns a temporal object of the same observable type as the input
     * with the date changed to be the same as this.
     * <p>
     * The adjustment is equivalent to using {@link Temporal#with(TemporalField, long)}
     * passing {@link ChronoField#EPOCH_DAY} as the field.
     * <p>
     * In most cases, it is clearer to reverse the calling pattern by using
     * {@link Temporal#with(TemporalAdjuster)}:
     * <pre>
     *   // these two lines are equivalent, but the second approach is recommended
     *   temporal = thisLocalDate.adjustInto(temporal);
     *   temporal = temporal.with(thisLocalDate);
     * </pre>
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  将指定的时间对象调整为与此对象具有相同的日期。
     * <p>
     *  这返回一个与输入相同的observable类型的时间对象,日期更改为与此相同。
     * <p>
     *  该调整等同于使用{@link Temporal#with(TemporalField,long)}传递{@link ChronoField#EPOCH_DAY}作为字段。
     * <p>
     *  在大多数情况下,通过使用{@link Temporal#with(TemporalAdjuster)}来反转呼叫模式是更清楚的：
     * <pre>
     *  //这两行是等价的,但第二种方法是建议temporal = thisLocalDate.adjustInto(temporal); temporal = temporal.with(thisLocal
     * Date);。
     * </pre>
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param temporal  the target object to be adjusted, not null
     * @return the adjusted object, not null
     * @throws DateTimeException if unable to make the adjustment
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    default Temporal adjustInto(Temporal temporal) {
        return temporal.with(EPOCH_DAY, toEpochDay());
    }

    /**
     * Calculates the amount of time until another date in terms of the specified unit.
     * <p>
     * This calculates the amount of time between two {@code ChronoLocalDate}
     * objects in terms of a single {@code TemporalUnit}.
     * The start and end points are {@code this} and the specified date.
     * The result will be negative if the end is before the start.
     * The {@code Temporal} passed to this method is converted to a
     * {@code ChronoLocalDate} using {@link Chronology#date(TemporalAccessor)}.
     * The calculation returns a whole number, representing the number of
     * complete units between the two dates.
     * For example, the amount in days between two dates can be calculated
     * using {@code startDate.until(endDate, DAYS)}.
     * <p>
     * There are two equivalent ways of using this method.
     * The first is to invoke this method.
     * The second is to use {@link TemporalUnit#between(Temporal, Temporal)}:
     * <pre>
     *   // these two lines are equivalent
     *   amount = start.until(end, MONTHS);
     *   amount = MONTHS.between(start, end);
     * </pre>
     * The choice should be made based on which makes the code more readable.
     * <p>
     * The calculation is implemented in this method for {@link ChronoUnit}.
     * The units {@code DAYS}, {@code WEEKS}, {@code MONTHS}, {@code YEARS},
     * {@code DECADES}, {@code CENTURIES}, {@code MILLENNIA} and {@code ERAS}
     * should be supported by all implementations.
     * Other {@code ChronoUnit} values will throw an exception.
     * <p>
     * If the unit is not a {@code ChronoUnit}, then the result of this method
     * is obtained by invoking {@code TemporalUnit.between(Temporal, Temporal)}
     * passing {@code this} as the first argument and the converted input temporal as
     * the second argument.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  根据指定的单位计算直到另一个日期的时间量。
     * <p>
     * 这将根据单个{@code TemporalUnit}计算两个{@code ChronoLocalDate}对象之间的时间量。起点和终点是{@code this}和指定的日期。
     * 如果结束在开始之前,结果将为负。
     * 使用{@link Chronology#date(TemporalAccessor)}将传递给此方法的{@code Temporal}转换为{@code ChronoLocalDate}。
     * 计算返回一个整数,表示两个日期之间的完整单位数。例如,两个日期之间的天数可以使用{@code startDate.until(endDate,DAYS)}计算。
     * <p>
     *  有两种等效的方法使用这种方法。第一个是调用这个方法。第二个是使用{@link TemporalUnit#between(Temporal,Temporal)}：
     * <pre>
     *  //这两行是等价的amount = start.until(end,MONTHS); amount = MONTHS.between(start,end);
     * </pre>
     *  应该基于哪个使得代码更可读的选择。
     * <p>
     *  该计算在{@link ChronoUnit}的此方法中实现。
     *  {@code DAYS},{@code WEEKS},{@code MONTHS},{@code YEARS},{@code DECADES},{@code CENTURIES},{@code MILLENNIA}
     * 和{@code ERAS}应该得到所有实现的支持。
     *  该计算在{@link ChronoUnit}的此方法中实现。其他{@code ChronoUnit}值会抛出异常。
     * <p>
     *  如果单元不是{@code ChronoUnit},那么通过调用{@code TemporalUnit.between(Temporal,Temporal)}传递{@code this}作为第一个参数和
     * 转换的输入时间为第二个参数。
     * <p>
     * 此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param endExclusive  the end date, exclusive, which is converted to a
     *  {@code ChronoLocalDate} in the same chronology, not null
     * @param unit  the unit to measure the amount in, not null
     * @return the amount of time between this date and the end date
     * @throws DateTimeException if the amount cannot be calculated, or the end
     *  temporal cannot be converted to a {@code ChronoLocalDate}
     * @throws UnsupportedTemporalTypeException if the unit is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override  // override for Javadoc
    long until(Temporal endExclusive, TemporalUnit unit);

    /**
     * Calculates the period between this date and another date as a {@code ChronoPeriod}.
     * <p>
     * This calculates the period between two dates. All supplied chronologies
     * calculate the period using years, months and days, however the
     * {@code ChronoPeriod} API allows the period to be represented using other units.
     * <p>
     * The start and end points are {@code this} and the specified date.
     * The result will be negative if the end is before the start.
     * The negative sign will be the same in each of year, month and day.
     * <p>
     * The calculation is performed using the chronology of this date.
     * If necessary, the input date will be converted to match.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  计算此日期与另一个日期之间的期间为{@code ChronoPeriod}。
     * <p>
     *  这将计算两个日期之间的期间。所有提供的年表都使用年,月和日计算期间,但{@code ChronoPeriod} API允许使用其他单位表示期间。
     * <p>
     *  起点和终点是{@code this}和指定的日期。如果结束在开始之前,结果将为负。负号在年,月和日的每一个中都是相同的。
     * <p>
     *  使用该日期的年表进行计算。如果需要,输入日期将被转换为匹配。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param endDateExclusive  the end date, exclusive, which may be in any chronology, not null
     * @return the period between this date and the end date, not null
     * @throws DateTimeException if the period cannot be calculated
     * @throws ArithmeticException if numeric overflow occurs
     */
    ChronoPeriod until(ChronoLocalDate endDateExclusive);

    /**
     * Formats this date using the specified formatter.
     * <p>
     * This date will be passed to the formatter to produce a string.
     * <p>
     * The default implementation must behave as follows:
     * <pre>
     *  return formatter.format(this);
     * </pre>
     *
     * <p>
     *  使用指定的格式化程序格式化此日期。
     * <p>
     *  此日期将传递到格式化程序以生成字符串。
     * <p>
     *  默认实现必须如下所示：
     * <pre>
     *  return formatter.format(this);
     * </pre>
     * 
     * 
     * @param formatter  the formatter to use, not null
     * @return the formatted date string, not null
     * @throws DateTimeException if an error occurs during printing
     */
    default String format(DateTimeFormatter formatter) {
        Objects.requireNonNull(formatter, "formatter");
        return formatter.format(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Combines this date with a time to create a {@code ChronoLocalDateTime}.
     * <p>
     * This returns a {@code ChronoLocalDateTime} formed from this date at the specified time.
     * All possible combinations of date and time are valid.
     *
     * <p>
     *  将此日期与时间组合,以创建{@code ChronoLocalDateTime}。
     * <p>
     *  这将返回在指定时间从此日期形成的{@code ChronoLocalDateTime}。所有可能的日期和时间组合都有效。
     * 
     * 
     * @param localTime  the local time to use, not null
     * @return the local date-time formed from this date and the specified time, not null
     */
    @SuppressWarnings("unchecked")
    default ChronoLocalDateTime<?> atTime(LocalTime localTime) {
        return ChronoLocalDateTimeImpl.of(this, localTime);
    }

    //-----------------------------------------------------------------------
    /**
     * Converts this date to the Epoch Day.
     * <p>
     * The {@link ChronoField#EPOCH_DAY Epoch Day count} is a simple
     * incrementing count of days where day 0 is 1970-01-01 (ISO).
     * This definition is the same for all chronologies, enabling conversion.
     * <p>
     * This default implementation queries the {@code EPOCH_DAY} field.
     *
     * <p>
     *  将此日期转换为纪元日。
     * <p>
     *  {@link ChronoField#EPOCH_DAY纪元日计数}是简单的递增天数,第0天为1970-01-01(ISO)。这个定义对于所有的年表都是一样的,可以进行转换。
     * <p>
     *  此默认实现查询{@code EPOCH_DAY}字段。
     * 
     * 
     * @return the Epoch Day equivalent to this date
     */
    default long toEpochDay() {
        return getLong(EPOCH_DAY);
    }

    //-----------------------------------------------------------------------
    /**
     * Compares this date to another date, including the chronology.
     * <p>
     * The comparison is based first on the underlying time-line date, then
     * on the chronology.
     * It is "consistent with equals", as defined by {@link Comparable}.
     * <p>
     * For example, the following is the comparator order:
     * <ol>
     * <li>{@code 2012-12-03 (ISO)}</li>
     * <li>{@code 2012-12-04 (ISO)}</li>
     * <li>{@code 2555-12-04 (ThaiBuddhist)}</li>
     * <li>{@code 2012-12-05 (ISO)}</li>
     * </ol>
     * Values #2 and #3 represent the same date on the time-line.
     * When two values represent the same date, the chronology ID is compared to distinguish them.
     * This step is needed to make the ordering "consistent with equals".
     * <p>
     * If all the date objects being compared are in the same chronology, then the
     * additional chronology stage is not required and only the local date is used.
     * To compare the dates of two {@code TemporalAccessor} instances, including dates
     * in two different chronologies, use {@link ChronoField#EPOCH_DAY} as a comparator.
     * <p>
     * This default implementation performs the comparison defined above.
     *
     * <p>
     * 将此日期与另一个日期(包括年表)进行比较。
     * <p>
     *  比较首先基于潜在的时间线日期,然后基于年表。它是"与等号一致",由{@link Comparable}定义。
     * <p>
     *  例如,以下是比较器顺序：
     * <ol>
     *  <li> {@ code 2012-12-03(ISO)} </li> <li> {@ code 2012-12-04(ISO)} </li> <li> {@ code 2555-12-04 ThaiBuddhist)}
     *  </li> <li> {@ code 2012-12-05(ISO)} </li>。
     * </ol>
     *  值#2和#3表示时间线上的相同日期。当两个值表示相同的日期时,比较年表ID以区分它们。需要此步骤使排序"与等号一致"。
     * <p>
     *  如果所比较的所有日期对象都在相同的年表中,则不需要附加的年表,而仅使用本地日期。
     * 要比较两个{@code TemporalAccessor}实例的日期,包括两个不同年代的日期,请使用{@link ChronoField#EPOCH_DAY}作为比较。
     * <p>
     *  此默认实现执行上面定义的比较。
     * 
     * 
     * @param other  the other date to compare to, not null
     * @return the comparator value, negative if less, positive if greater
     */
    @Override
    default int compareTo(ChronoLocalDate other) {
        int cmp = Long.compare(toEpochDay(), other.toEpochDay());
        if (cmp == 0) {
            cmp = getChronology().compareTo(other.getChronology());
        }
        return cmp;
    }

    /**
     * Checks if this date is after the specified date ignoring the chronology.
     * <p>
     * This method differs from the comparison in {@link #compareTo} in that it
     * only compares the underlying date and not the chronology.
     * This allows dates in different calendar systems to be compared based
     * on the time-line position.
     * This is equivalent to using {@code date1.toEpochDay() &gt; date2.toEpochDay()}.
     * <p>
     * This default implementation performs the comparison based on the epoch-day.
     *
     * <p>
     *  检查此日期是否晚于指定的日期,而忽略年表。
     * <p>
     *  此方法与{@link #compareTo}中的比较不同,它仅比较基础日期而不是年表。这允许基于时间线位置来比较不同日历系统中的日期。
     * 这相当于使用{@code date1.toEpochDay()&gt; date2.toEpochDay()}。
     * <p>
     *  此默认实现基于时代日执行比较。
     * 
     * 
     * @param other  the other date to compare to, not null
     * @return true if this is after the specified date
     */
    default boolean isAfter(ChronoLocalDate other) {
        return this.toEpochDay() > other.toEpochDay();
    }

    /**
     * Checks if this date is before the specified date ignoring the chronology.
     * <p>
     * This method differs from the comparison in {@link #compareTo} in that it
     * only compares the underlying date and not the chronology.
     * This allows dates in different calendar systems to be compared based
     * on the time-line position.
     * This is equivalent to using {@code date1.toEpochDay() &lt; date2.toEpochDay()}.
     * <p>
     * This default implementation performs the comparison based on the epoch-day.
     *
     * <p>
     * 检查此日期是否早于指定的日期,而忽略年表。
     * <p>
     *  此方法与{@link #compareTo}中的比较不同,它仅比较基础日期而不是年表。这允许基于时间线位置来比较不同日历系统中的日期。
     * 这相当于使用{@code date1.toEpochDay()&lt; date2.toEpochDay()}。
     * <p>
     *  此默认实现基于时代日执行比较。
     * 
     * 
     * @param other  the other date to compare to, not null
     * @return true if this is before the specified date
     */
    default boolean isBefore(ChronoLocalDate other) {
        return this.toEpochDay() < other.toEpochDay();
    }

    /**
     * Checks if this date is equal to the specified date ignoring the chronology.
     * <p>
     * This method differs from the comparison in {@link #compareTo} in that it
     * only compares the underlying date and not the chronology.
     * This allows dates in different calendar systems to be compared based
     * on the time-line position.
     * This is equivalent to using {@code date1.toEpochDay() == date2.toEpochDay()}.
     * <p>
     * This default implementation performs the comparison based on the epoch-day.
     *
     * <p>
     *  检查此日期是否等于忽略年表的指定日期。
     * <p>
     *  此方法与{@link #compareTo}中的比较不同,它仅比较基础日期而不是年表。这允许基于时间线位置来比较不同日历系统中的日期。
     * 这相当于使用{@code date1.toEpochDay()== date2.toEpochDay()}。
     * <p>
     *  此默认实现基于时代日执行比较。
     * 
     * 
     * @param other  the other date to compare to, not null
     * @return true if the underlying date is equal to the specified date
     */
    default boolean isEqual(ChronoLocalDate other) {
        return this.toEpochDay() == other.toEpochDay();
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if this date is equal to another date, including the chronology.
     * <p>
     * Compares this date with another ensuring that the date and chronology are the same.
     * <p>
     * To compare the dates of two {@code TemporalAccessor} instances, including dates
     * in two different chronologies, use {@link ChronoField#EPOCH_DAY} as a comparator.
     *
     * <p>
     *  检查此日期是否等于另一个日期,包括年表。
     * <p>
     *  将此日期与另一个日期进行比较,确保日期和年表相同。
     * <p>
     *  要比较两个{@code TemporalAccessor}实例的日期,包括两个不同年代的日期,请使用{@link ChronoField#EPOCH_DAY}作为比较。
     * 
     * 
     * @param obj  the object to check, null returns false
     * @return true if this is equal to the other date
     */
    @Override
    boolean equals(Object obj);

    /**
     * A hash code for this date.
     *
     * <p>
     *  此日期的哈希码。
     * 
     * 
     * @return a suitable hash code
     */
    @Override
    int hashCode();

    //-----------------------------------------------------------------------
    /**
     * Outputs this date as a {@code String}.
     * <p>
     * The output will include the full local date.
     *
     * <p>
     *  将此日期作为{@code String}输出。
     * <p>
     * 
     * @return the formatted date, not null
     */
    @Override
    String toString();

}
