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

import java.time.Clock;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * A calendar system, used to organize and identify dates.
 * <p>
 * The main date and time API is built on the ISO calendar system.
 * The chronology operates behind the scenes to represent the general concept of a calendar system.
 * For example, the Japanese, Minguo, Thai Buddhist and others.
 * <p>
 * Most other calendar systems also operate on the shared concepts of year, month and day,
 * linked to the cycles of the Earth around the Sun, and the Moon around the Earth.
 * These shared concepts are defined by {@link ChronoField} and are available
 * for use by any {@code Chronology} implementation:
 * <pre>
 *   LocalDate isoDate = ...
 *   ThaiBuddhistDate thaiDate = ...
 *   int isoYear = isoDate.get(ChronoField.YEAR);
 *   int thaiYear = thaiDate.get(ChronoField.YEAR);
 * </pre>
 * As shown, although the date objects are in different calendar systems, represented by different
 * {@code Chronology} instances, both can be queried using the same constant on {@code ChronoField}.
 * For a full discussion of the implications of this, see {@link ChronoLocalDate}.
 * In general, the advice is to use the known ISO-based {@code LocalDate}, rather than
 * {@code ChronoLocalDate}.
 * <p>
 * While a {@code Chronology} object typically uses {@code ChronoField} and is based on
 * an era, year-of-era, month-of-year, day-of-month model of a date, this is not required.
 * A {@code Chronology} instance may represent a totally different kind of calendar system,
 * such as the Mayan.
 * <p>
 * In practical terms, the {@code Chronology} instance also acts as a factory.
 * The {@link #of(String)} method allows an instance to be looked up by identifier,
 * while the {@link #ofLocale(Locale)} method allows lookup by locale.
 * <p>
 * The {@code Chronology} instance provides a set of methods to create {@code ChronoLocalDate} instances.
 * The date classes are used to manipulate specific dates.
 * <ul>
 * <li> {@link #dateNow() dateNow()}
 * <li> {@link #dateNow(Clock) dateNow(clock)}
 * <li> {@link #dateNow(ZoneId) dateNow(zone)}
 * <li> {@link #date(int, int, int) date(yearProleptic, month, day)}
 * <li> {@link #date(Era, int, int, int) date(era, yearOfEra, month, day)}
 * <li> {@link #dateYearDay(int, int) dateYearDay(yearProleptic, dayOfYear)}
 * <li> {@link #dateYearDay(Era, int, int) dateYearDay(era, yearOfEra, dayOfYear)}
 * <li> {@link #date(TemporalAccessor) date(TemporalAccessor)}
 * </ul>
 *
 * <h3 id="addcalendars">Adding New Calendars</h3>
 * The set of available chronologies can be extended by applications.
 * Adding a new calendar system requires the writing of an implementation of
 * {@code Chronology}, {@code ChronoLocalDate} and {@code Era}.
 * The majority of the logic specific to the calendar system will be in
 * {@code ChronoLocalDate}. The {@code Chronology} subclass acts as a factory.
 * <p>
 * To permit the discovery of additional chronologies, the {@link java.util.ServiceLoader ServiceLoader}
 * is used. A file must be added to the {@code META-INF/services} directory with the
 * name 'java.time.chrono.Chronology' listing the implementation classes.
 * See the ServiceLoader for more details on service loading.
 * For lookup by id or calendarType, the system provided calendars are found
 * first followed by application provided calendars.
 * <p>
 * Each chronology must define a chronology ID that is unique within the system.
 * If the chronology represents a calendar system defined by the
 * CLDR specification then the calendar type is the concatenation of the
 * CLDR type and, if applicable, the CLDR variant,
 *
 * @implSpec
 * This interface must be implemented with care to ensure other classes operate correctly.
 * All implementations that can be instantiated must be final, immutable and thread-safe.
 * Subclasses should be Serializable wherever possible.
 *
 * <p>
 *  日历系统,用于组织和识别日期。
 * <p>
 *  主日期和时间API构建在ISO日历系统上。年表在幕后操作以代表日历系统的一般概念。例如,日本,民族,泰国佛教和其他。
 * <p>
 *  大多数其他日历系统也运行在年,月和日的共享概念,与周围的地球周围的太阳和地球周围的月球的周期。
 * 这些共享概念由{@link ChronoField}定义,可供任何{@code Chronology}实现使用：。
 * <pre>
 *  LocalDate isoDate = ... ThaiBuddhistDate thaiDate = ... int isoYear = isoDate.get(ChronoField.YEAR);
 *  int thaiYear = thaiDate.get(ChronoField.YEAR);。
 * </pre>
 * 如图所示,虽然日期对象在不同的​​日历系统中,由不同的{@code Chronology}实例表示,但都可以使用{@code ChronoField}上的相同常数来查询。
 * 有关这一问题的完整讨论,请参阅{@link ChronoLocalDate}。
 * 一般来说,建议是使用已知的基于ISO的{@code LocalDate},而不是{@code ChronoLocalDate}。
 * <p>
 *  虽然{@code Chronology}对象通常使用{@code ChronoField},并且基于日期的年代,年份,年份,月份日期模型,但这不是必需的。
 *  {@code Chronology}实例可能代表完全不同类型的日历系统,例如玛雅。
 * <p>
 *  实际上,{@code Chronology}实例也充当工厂。
 *  {@link #of(String)}方法允许通过标识符查找实例,而{@link #ofLocale(Locale)}方法允许通过语言环境查找。
 * <p>
 *  {@code Chronology}实例提供了一组创建{@code ChronoLocalDate}实例的方法。日期类用于操作特定日期。
 * <ul>
 * <li> {@link #dateNow()dateNow()} <li> {@link #dateNow(Clock)dateNow(clock)} <li> {@link #dateNow(ZoneId)dateNow(zone)}
 *  <li> { @link #date(int,int,int)date(yearProleptic,month,day)} <li> {@link #date(Era,int,int,int)date(era,yearOfEra,month,day)}
 *  <li > {@link #dateYearDay(int,int)dateYearDay(yearProleptic,dayOfYear)} <li> {@link #dateYearDay(Era,int,int)dateYearDay(era,yearOfEra,dayOfYear)}
 *  <li> {@link #date (TemporalAccessor)date(TemporalAccessor)}。
 * </ul>
 * 
 *  <h3 id ="addcalendars">添加新日历</h3>可用应用程序可扩展可用的日历集。
 * 添加新的日历系统需要编写{@code Chronology},{@code ChronoLocalDate}和{@code Era}的实现。
 * 特定于日历系统的大多数逻辑将在{@code ChronoLocalDate}中。 {@code Chronology}子类充当工厂。
 * <p>
 *  为了允许发现额外的年表,使用{@link java.util.ServiceLoader ServiceLoader}。
 * 必须将文件添加到{@code META-INF / services}目录,名称为"java.time.chrono.Chronology",其中列出了实现类。
 * 有关服务加载的更多详细信息,请参阅ServiceLoader。对于通过id或calendarType查找,系统提供的日历首先被找到,然后是应用程序提供的日历。
 * <p>
 * 每个年表必须定义系统内唯一的年表ID。如果年表代表由CLDR规范定义的日历系统,则日历类型是CLDR类型和CLDR变体(如果适用)的串联,
 * 
 *  @implSpec必须小心地实现此接口,以确保其他类正常运行。所有可以实例化的实现必须是final,immutable和线程安全的。子类应尽可能序列化。
 * 
 * 
 * @since 1.8
 */
public interface Chronology extends Comparable<Chronology> {

    /**
     * Obtains an instance of {@code Chronology} from a temporal object.
     * <p>
     * This obtains a chronology based on the specified temporal.
     * A {@code TemporalAccessor} represents an arbitrary set of date and time information,
     * which this factory converts to an instance of {@code Chronology}.
     * <p>
     * The conversion will obtain the chronology using {@link TemporalQueries#chronology()}.
     * If the specified temporal object does not have a chronology, {@link IsoChronology} is returned.
     * <p>
     * This method matches the signature of the functional interface {@link TemporalQuery}
     * allowing it to be used in queries via method reference, {@code Chronology::from}.
     *
     * <p>
     *  从临时对象获取{@code Chronology}的实例。
     * <p>
     *  这获得基于指定时间的年表。 {@code TemporalAccessor}表示一组任意的日期和时间信息,此工厂将其转换为{@code Chronology}的实例。
     * <p>
     *  转换将使用{@link TemporalQueries#chronology()}获取年表。如果指定的时间对象没有时间顺序,则返回{@link IsoChronology}。
     * <p>
     *  此方法匹配功能接口{@link TemporalQuery}的签名,允许通过方法引用{@code Chronology :: from}在查询中使用它。
     * 
     * 
     * @param temporal  the temporal to convert, not null
     * @return the chronology, not null
     * @throws DateTimeException if unable to convert to an {@code Chronology}
     */
    static Chronology from(TemporalAccessor temporal) {
        Objects.requireNonNull(temporal, "temporal");
        Chronology obj = temporal.query(TemporalQueries.chronology());
        return (obj != null ? obj : IsoChronology.INSTANCE);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code Chronology} from a locale.
     * <p>
     * This returns a {@code Chronology} based on the specified locale,
     * typically returning {@code IsoChronology}. Other calendar systems
     * are only returned if they are explicitly selected within the locale.
     * <p>
     * The {@link Locale} class provide access to a range of information useful
     * for localizing an application. This includes the language and region,
     * such as "en-GB" for English as used in Great Britain.
     * <p>
     * The {@code Locale} class also supports an extension mechanism that
     * can be used to identify a calendar system. The mechanism is a form
     * of key-value pairs, where the calendar system has the key "ca".
     * For example, the locale "en-JP-u-ca-japanese" represents the English
     * language as used in Japan with the Japanese calendar system.
     * <p>
     * This method finds the desired calendar system by in a manner equivalent
     * to passing "ca" to {@link Locale#getUnicodeLocaleType(String)}.
     * If the "ca" key is not present, then {@code IsoChronology} is returned.
     * <p>
     * Note that the behavior of this method differs from the older
     * {@link java.util.Calendar#getInstance(Locale)} method.
     * If that method receives a locale of "th_TH" it will return {@code BuddhistCalendar}.
     * By contrast, this method will return {@code IsoChronology}.
     * Passing the locale "th-TH-u-ca-buddhist" into either method will
     * result in the Thai Buddhist calendar system and is therefore the
     * recommended approach going forward for Thai calendar system localization.
     * <p>
     * A similar, but simpler, situation occurs for the Japanese calendar system.
     * The locale "jp_JP_JP" has previously been used to access the calendar.
     * However, unlike the Thai locale, "ja_JP_JP" is automatically converted by
     * {@code Locale} to the modern and recommended form of "ja-JP-u-ca-japanese".
     * Thus, there is no difference in behavior between this method and
     * {@code Calendar#getInstance(Locale)}.
     *
     * <p>
     *  从语言环境获取{@code Chronology}的实例。
     * <p>
     *  这将返回基于指定区域设置的{@code Chronology},通常返回{@code IsoChronology}。如果在语言环境中显式选择其他日历系统,则仅返回。
     * <p>
     * {@link Locale}类提供对用于本地化应用程序有用的一系列信息的访问。这包括语言和地区,例如在英国使用的英语的"en-GB"。
     * <p>
     *  {@code Locale}类还支持可用于标识日历系统的扩展机制。该机制是一种形式的键值对,其中日历系统具有键"ca"。
     * 例如,区域设置"en-JP-u-ca-japanese"表示日本在日本日历系统中使用的英语语言。
     * <p>
     *  此方法以等同于将"ca"传递到{@link Locale#getUnicodeLocaleType(String)}的方式找到所需的日历系统。
     * 如果"ca"键不存在,则返回{@code IsoChronology}。
     * <p>
     *  请注意,此方法的行为与旧的{@link java.util.Calendar#getInstance(Locale)}方法不同。
     * 如果该方法接收到"th_TH"的语言环境,它将返回{@code BuddhistCalendar}。相比之下,这个方法将返回{@code IsoChronology}。
     * 将地区"th-TH-u-ca-buddhist"传递到任一方法将导致泰国佛教日历系统,并且因此是推进的用于泰国日历系统本地化的方法。
     * <p>
     * 类似的,但更简单的情况发生在日本日历系统。区域设置"jp_JP_JP"以前已用于访问日历。
     * 但是,与泰语语言环境不同,"ja_JP_JP"由{@code Locale}自动转换为现代和推荐的"ja-JP-u-ca-japanese"形式。
     * 因此,此方法与{@code Calendar#getInstance(Locale)}之间的行为没有差异。
     * 
     * 
     * @param locale  the locale to use to obtain the calendar system, not null
     * @return the calendar system associated with the locale, not null
     * @throws DateTimeException if the locale-specified calendar cannot be found
     */
    static Chronology ofLocale(Locale locale) {
        return AbstractChronology.ofLocale(locale);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code Chronology} from a chronology ID or
     * calendar system type.
     * <p>
     * This returns a chronology based on either the ID or the type.
     * The {@link #getId() chronology ID} uniquely identifies the chronology.
     * The {@link #getCalendarType() calendar system type} is defined by the
     * CLDR specification.
     * <p>
     * The chronology may be a system chronology or a chronology
     * provided by the application via ServiceLoader configuration.
     * <p>
     * Since some calendars can be customized, the ID or type typically refers
     * to the default customization. For example, the Gregorian calendar can have multiple
     * cutover dates from the Julian, but the lookup only provides the default cutover date.
     *
     * <p>
     *  从年表ID或日历系统类型获取{@code Chronology}的实例。
     * <p>
     *  这将返回基于ID或类型的年表。 {@link #getId()年表ID}唯一标识年表。 {@link #getCalendarType()日历系统类型}由CLDR规范定义。
     * <p>
     *  年表可以是由应用通过ServiceLoader配置提供的系统年表或年表。
     * <p>
     *  由于某些日历可以自定义,所以ID或类型通常指默认自定义。例如,公历日历可以具有多个从朱利安开始的割接日期,但是查找仅提供默认割接日期。
     * 
     * 
     * @param id  the chronology ID or calendar system type, not null
     * @return the chronology with the identifier requested, not null
     * @throws DateTimeException if the chronology cannot be found
     */
    static Chronology of(String id) {
        return AbstractChronology.of(id);
    }

    /**
     * Returns the available chronologies.
     * <p>
     * Each returned {@code Chronology} is available for use in the system.
     * The set of chronologies includes the system chronologies and
     * any chronologies provided by the application via ServiceLoader
     * configuration.
     *
     * <p>
     *  返回可用的年表。
     * <p>
     *  每个返回的{@code Chronology}都可以在系统中使用。时间表集包括系统时间表和由应用程序通过ServiceLoader配置提供的任何时间表。
     * 
     * 
     * @return the independent, modifiable set of the available chronology IDs, not null
     */
    static Set<Chronology> getAvailableChronologies() {
        return AbstractChronology.getAvailableChronologies();
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the ID of the chronology.
     * <p>
     * The ID uniquely identifies the {@code Chronology}.
     * It can be used to lookup the {@code Chronology} using {@link #of(String)}.
     *
     * <p>
     *  获取年表的ID。
     * <p>
     *  ID唯一标识{@code Chronology}。它可以用于使用{@link #of(String)}查找{@code Chronology}。
     * 
     * 
     * @return the chronology ID, not null
     * @see #getCalendarType()
     */
    String getId();

    /**
     * Gets the calendar type of the calendar system.
     * <p>
     * The calendar type is an identifier defined by the CLDR and
     * <em>Unicode Locale Data Markup Language (LDML)</em> specifications
     * to uniquely identification a calendar.
     * The {@code getCalendarType} is the concatenation of the CLDR calendar type
     * and the variant, if applicable, is appended separated by "-".
     * The calendar type is used to lookup the {@code Chronology} using {@link #of(String)}.
     *
     * <p>
     * 获取日历系统的日历类型。
     * <p>
     *  日历类型是由CLDR和<em> Unicode区域设置数据标记语言(LDML)</em>规范定义的唯一标识日历的标识符。
     *  {@code getCalendarType}是CLDR日历类型的拼接,如果适用,则用" - "分隔附加的变体。
     * 日历类型用于使用{@link #of(String)}查找{@code Chronology}。
     * 
     * 
     * @return the calendar system type, null if the calendar is not defined by CLDR/LDML
     * @see #getId()
     */
    String getCalendarType();

    //-----------------------------------------------------------------------
    /**
     * Obtains a local date in this chronology from the era, year-of-era,
     * month-of-year and day-of-month fields.
     *
     * @implSpec
     * The default implementation combines the era and year-of-era into a proleptic
     * year before calling {@link #date(int, int, int)}.
     *
     * <p>
     *  从这个年代,从时代,年份,年份和月份字段获得地方日期。
     * 
     *  @implSpec在调用{@link #date(int,int,int)}之前,默认实现将时代和年代结合成一个前兆年。
     * 
     * 
     * @param era  the era of the correct type for the chronology, not null
     * @param yearOfEra  the chronology year-of-era
     * @param month  the chronology month-of-year
     * @param dayOfMonth  the chronology day-of-month
     * @return the local date in this chronology, not null
     * @throws DateTimeException if unable to create the date
     * @throws ClassCastException if the {@code era} is not of the correct type for the chronology
     */
    default ChronoLocalDate date(Era era, int yearOfEra, int month, int dayOfMonth) {
        return date(prolepticYear(era, yearOfEra), month, dayOfMonth);
    }

    /**
     * Obtains a local date in this chronology from the proleptic-year,
     * month-of-year and day-of-month fields.
     *
     * <p>
     *  从这个年表中获得一个本地日期,从年龄,月份和月份字段。
     * 
     * 
     * @param prolepticYear  the chronology proleptic-year
     * @param month  the chronology month-of-year
     * @param dayOfMonth  the chronology day-of-month
     * @return the local date in this chronology, not null
     * @throws DateTimeException if unable to create the date
     */
    ChronoLocalDate date(int prolepticYear, int month, int dayOfMonth);

    /**
     * Obtains a local date in this chronology from the era, year-of-era and
     * day-of-year fields.
     *
     * @implSpec
     * The default implementation combines the era and year-of-era into a proleptic
     * year before calling {@link #dateYearDay(int, int)}.
     *
     * <p>
     *  从这个年代,从时代,年代和年年字段获得地方日期。
     * 
     *  @implSpec在调用{@link #dateYearDay(int,int)}之前,默认实现将时代和年代结合成一个前兆年。
     * 
     * 
     * @param era  the era of the correct type for the chronology, not null
     * @param yearOfEra  the chronology year-of-era
     * @param dayOfYear  the chronology day-of-year
     * @return the local date in this chronology, not null
     * @throws DateTimeException if unable to create the date
     * @throws ClassCastException if the {@code era} is not of the correct type for the chronology
     */
    default ChronoLocalDate dateYearDay(Era era, int yearOfEra, int dayOfYear) {
        return dateYearDay(prolepticYear(era, yearOfEra), dayOfYear);
    }

    /**
     * Obtains a local date in this chronology from the proleptic-year and
     * day-of-year fields.
     *
     * <p>
     *  从推测年和年年字段中获得这个年表中的当地日期。
     * 
     * 
     * @param prolepticYear  the chronology proleptic-year
     * @param dayOfYear  the chronology day-of-year
     * @return the local date in this chronology, not null
     * @throws DateTimeException if unable to create the date
     */
    ChronoLocalDate dateYearDay(int prolepticYear, int dayOfYear);

    /**
     * Obtains a local date in this chronology from the epoch-day.
     * <p>
     * The definition of {@link ChronoField#EPOCH_DAY EPOCH_DAY} is the same
     * for all calendar systems, thus it can be used for conversion.
     *
     * <p>
     *  从这个时代获得这个年表中的本地日期。
     * <p>
     *  {@link ChronoField#EPOCH_DAY EPOCH_DAY}的定义对所有日历系统都相同,因此可用于转换。
     * 
     * 
     * @param epochDay  the epoch day
     * @return the local date in this chronology, not null
     * @throws DateTimeException if unable to create the date
     */
    ChronoLocalDate dateEpochDay(long epochDay);

    //-----------------------------------------------------------------------
    /**
     * Obtains the current local date in this chronology from the system clock in the default time-zone.
     * <p>
     * This will query the {@link Clock#systemDefaultZone() system clock} in the default
     * time-zone to obtain the current date.
     * <p>
     * Using this method will prevent the ability to use an alternate clock for testing
     * because the clock is hard-coded.
     *
     * @implSpec
     * The default implementation invokes {@link #dateNow(Clock)}.
     *
     * <p>
     *  从默认时区中的系统时钟获取此年表中的当前本地日期。
     * <p>
     * 这将在默认时区中查询{@link Clock#systemDefaultZone()系统时钟}以获取当前日期。
     * <p>
     *  使用此方法将会阻止使用备用时钟进行测试,因为时钟是硬编码的。
     * 
     *  @implSpec默认实现调用{@link #dateNow(Clock)}。
     * 
     * 
     * @return the current local date using the system clock and default time-zone, not null
     * @throws DateTimeException if unable to create the date
     */
    default ChronoLocalDate dateNow() {
        return dateNow(Clock.systemDefaultZone());
    }

    /**
     * Obtains the current local date in this chronology from the system clock in the specified time-zone.
     * <p>
     * This will query the {@link Clock#system(ZoneId) system clock} to obtain the current date.
     * Specifying the time-zone avoids dependence on the default time-zone.
     * <p>
     * Using this method will prevent the ability to use an alternate clock for testing
     * because the clock is hard-coded.
     *
     * @implSpec
     * The default implementation invokes {@link #dateNow(Clock)}.
     *
     * <p>
     *  从指定时区中的系统时钟获取此年表中的当前本地日期。
     * <p>
     *  这将查询{@link Clock#system(ZoneId)系统时钟}以获取当前日期。指定时区避免了对默认时区的依赖。
     * <p>
     *  使用此方法将会阻止使用备用时钟进行测试,因为时钟是硬编码的。
     * 
     *  @implSpec默认实现调用{@link #dateNow(Clock)}。
     * 
     * 
     * @param zone  the zone ID to use, not null
     * @return the current local date using the system clock, not null
     * @throws DateTimeException if unable to create the date
     */
    default ChronoLocalDate dateNow(ZoneId zone) {
        return dateNow(Clock.system(zone));
    }

    /**
     * Obtains the current local date in this chronology from the specified clock.
     * <p>
     * This will query the specified clock to obtain the current date - today.
     * Using this method allows the use of an alternate clock for testing.
     * The alternate clock may be introduced using {@link Clock dependency injection}.
     *
     * @implSpec
     * The default implementation invokes {@link #date(TemporalAccessor)} )}.
     *
     * <p>
     *  从指定的时钟获取此年表中的当前本地日期。
     * <p>
     *  这将查询指定的时钟以获取当前日期 - 今天。使用此方法允许使用替代时钟进行测试。可以使用{@link时钟依赖注入}来引入替代时钟。
     * 
     *  @implSpec默认实现调用{@link #date(TemporalAccessor)})}。
     * 
     * 
     * @param clock  the clock to use, not null
     * @return the current local date, not null
     * @throws DateTimeException if unable to create the date
     */
    default ChronoLocalDate dateNow(Clock clock) {
        Objects.requireNonNull(clock, "clock");
        return date(LocalDate.now(clock));
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains a local date in this chronology from another temporal object.
     * <p>
     * This obtains a date in this chronology based on the specified temporal.
     * A {@code TemporalAccessor} represents an arbitrary set of date and time information,
     * which this factory converts to an instance of {@code ChronoLocalDate}.
     * <p>
     * The conversion typically uses the {@link ChronoField#EPOCH_DAY EPOCH_DAY}
     * field, which is standardized across calendar systems.
     * <p>
     * This method matches the signature of the functional interface {@link TemporalQuery}
     * allowing it to be used as a query via method reference, {@code aChronology::date}.
     *
     * <p>
     *  在这个年表中从另一个时间对象获得本地日期。
     * <p>
     *  这基于指定的时间获得该年表中的日期。 {@code TemporalAccessor}表示一组任意的日期和时间信息,此工厂将其转换为{@code ChronoLocalDate}的实例。
     * <p>
     * 转化通常使用{@link ChronoField#EPOCH_DAY EPOCH_DAY}字段,该字段在日历系统中标准化。
     * <p>
     *  此方法匹配功能接口{@link TemporalQuery}的签名,允许它通过方法引用{@code aChronology :: date}用作查询。
     * 
     * 
     * @param temporal  the temporal object to convert, not null
     * @return the local date in this chronology, not null
     * @throws DateTimeException if unable to create the date
     * @see ChronoLocalDate#from(TemporalAccessor)
     */
    ChronoLocalDate date(TemporalAccessor temporal);

    /**
     * Obtains a local date-time in this chronology from another temporal object.
     * <p>
     * This obtains a date-time in this chronology based on the specified temporal.
     * A {@code TemporalAccessor} represents an arbitrary set of date and time information,
     * which this factory converts to an instance of {@code ChronoLocalDateTime}.
     * <p>
     * The conversion extracts and combines the {@code ChronoLocalDate} and the
     * {@code LocalTime} from the temporal object.
     * Implementations are permitted to perform optimizations such as accessing
     * those fields that are equivalent to the relevant objects.
     * The result uses this chronology.
     * <p>
     * This method matches the signature of the functional interface {@link TemporalQuery}
     * allowing it to be used as a query via method reference, {@code aChronology::localDateTime}.
     *
     * <p>
     *  在这个年表中从另一个时间对象获得本地日期时间。
     * <p>
     *  这基于指定的时间来获得该年表中的日期时间。 {@code TemporalAccessor}表示一组任意的日期和时间信息,该工厂转换为{@code ChronoLocalDateTime}的实例。
     * <p>
     *  转换从时间对象提取和组合{@code ChronoLocalDate}和{@code LocalTime}。实现允许执行优化,例如访问等价于相关对象的那些字段。结果使用这个年表。
     * <p>
     *  此方法匹配功能接口{@link TemporalQuery}的签名,允许它通过方法引用{@code aChronology :: localDateTime}用作查询。
     * 
     * 
     * @param temporal  the temporal object to convert, not null
     * @return the local date-time in this chronology, not null
     * @throws DateTimeException if unable to create the date-time
     * @see ChronoLocalDateTime#from(TemporalAccessor)
     */
    default ChronoLocalDateTime<? extends ChronoLocalDate> localDateTime(TemporalAccessor temporal) {
        try {
            return date(temporal).atTime(LocalTime.from(temporal));
        } catch (DateTimeException ex) {
            throw new DateTimeException("Unable to obtain ChronoLocalDateTime from TemporalAccessor: " + temporal.getClass(), ex);
        }
    }

    /**
     * Obtains a {@code ChronoZonedDateTime} in this chronology from another temporal object.
     * <p>
     * This obtains a zoned date-time in this chronology based on the specified temporal.
     * A {@code TemporalAccessor} represents an arbitrary set of date and time information,
     * which this factory converts to an instance of {@code ChronoZonedDateTime}.
     * <p>
     * The conversion will first obtain a {@code ZoneId} from the temporal object,
     * falling back to a {@code ZoneOffset} if necessary. It will then try to obtain
     * an {@code Instant}, falling back to a {@code ChronoLocalDateTime} if necessary.
     * The result will be either the combination of {@code ZoneId} or {@code ZoneOffset}
     * with {@code Instant} or {@code ChronoLocalDateTime}.
     * Implementations are permitted to perform optimizations such as accessing
     * those fields that are equivalent to the relevant objects.
     * The result uses this chronology.
     * <p>
     * This method matches the signature of the functional interface {@link TemporalQuery}
     * allowing it to be used as a query via method reference, {@code aChronology::zonedDateTime}.
     *
     * <p>
     *  从另一个时间对象获取此年表中的{@code ChronoZonedDateTime}。
     * <p>
     *  这在该年表中基于指定的时间获得分区日期时间。
     *  {@code TemporalAccessor}表示一组任意的日期和时间信息,此工厂将其转换为{@code ChronoZonedDateTime}的实例。
     * <p>
     * 转换将首先从临时对象获取{@code ZoneId},如果必要,则返回到{@code ZoneOffset}。
     * 然后,系统会尝试获取{@code Instant},如有必要,会返回到{@code ChronoLocalDateTime}。
     * 结果将是{@code ZoneId}或{@code ZoneOffset}与{@code Instant}或{@code ChronoLocalDateTime}的组合。
     * 实现允许执行优化,例如访问等价于相关对象的那些字段。结果使用这个年表。
     * <p>
     *  此方法匹配功能接口{@link TemporalQuery}的签名,允许它通过方法引用{@code aChronology :: zonedDateTime}用作查询。
     * 
     * 
     * @param temporal  the temporal object to convert, not null
     * @return the zoned date-time in this chronology, not null
     * @throws DateTimeException if unable to create the date-time
     * @see ChronoZonedDateTime#from(TemporalAccessor)
     */
    default ChronoZonedDateTime<? extends ChronoLocalDate> zonedDateTime(TemporalAccessor temporal) {
        try {
            ZoneId zone = ZoneId.from(temporal);
            try {
                Instant instant = Instant.from(temporal);
                return zonedDateTime(instant, zone);

            } catch (DateTimeException ex1) {
                ChronoLocalDateTimeImpl<?> cldt = ChronoLocalDateTimeImpl.ensureValid(this, localDateTime(temporal));
                return ChronoZonedDateTimeImpl.ofBest(cldt, zone, null);
            }
        } catch (DateTimeException ex) {
            throw new DateTimeException("Unable to obtain ChronoZonedDateTime from TemporalAccessor: " + temporal.getClass(), ex);
        }
    }

    /**
     * Obtains a {@code ChronoZonedDateTime} in this chronology from an {@code Instant}.
     * <p>
     * This obtains a zoned date-time with the same instant as that specified.
     *
     * <p>
     *  从{@code Instant}中获取此年表中的{@code ChronoZonedDateTime}。
     * <p>
     *  这将获得具有与指定的相同时刻的分区日期时间。
     * 
     * 
     * @param instant  the instant to create the date-time from, not null
     * @param zone  the time-zone, not null
     * @return the zoned date-time, not null
     * @throws DateTimeException if the result exceeds the supported range
     */
    default ChronoZonedDateTime<? extends ChronoLocalDate> zonedDateTime(Instant instant, ZoneId zone) {
        return ChronoZonedDateTimeImpl.ofInstant(this, instant, zone);
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if the specified year is a leap year.
     * <p>
     * A leap-year is a year of a longer length than normal.
     * The exact meaning is determined by the chronology according to the following constraints.
     * <ul>
     * <li>a leap-year must imply a year-length longer than a non leap-year.
     * <li>a chronology that does not support the concept of a year must return false.
     * </ul>
     *
     * <p>
     *  检查指定的年份是否为闰年。
     * <p>
     *  闰年是比正常长的一年。确切的含义由根据以下约束的年表确定。
     * <ul>
     *  <li>闰年必须暗示比非闰年长一年的长度。 <li>不支持一年的概念的年表必须返回false。
     * </ul>
     * 
     * 
     * @param prolepticYear  the proleptic-year to check, not validated for range
     * @return true if the year is a leap year
     */
    boolean isLeapYear(long prolepticYear);

    /**
     * Calculates the proleptic-year given the era and year-of-era.
     * <p>
     * This combines the era and year-of-era into the single proleptic-year field.
     * <p>
     * If the chronology makes active use of eras, such as {@code JapaneseChronology}
     * then the year-of-era will be validated against the era.
     * For other chronologies, validation is optional.
     *
     * <p>
     *  计算给定的时代和年代的推测年。
     * <p>
     *  这将时代和年代结合到单一的年龄领域。
     * <p>
     * 如果年表积极使用时代,例如{@code Japanese Chronology},那么年代将被验证对这个时代。对于其他年代,验证是可选的。
     * 
     * 
     * @param era  the era of the correct type for the chronology, not null
     * @param yearOfEra  the chronology year-of-era
     * @return the proleptic-year
     * @throws DateTimeException if unable to convert to a proleptic-year,
     *  such as if the year is invalid for the era
     * @throws ClassCastException if the {@code era} is not of the correct type for the chronology
     */
    int prolepticYear(Era era, int yearOfEra);

    /**
     * Creates the chronology era object from the numeric value.
     * <p>
     * The era is, conceptually, the largest division of the time-line.
     * Most calendar systems have a single epoch dividing the time-line into two eras.
     * However, some have multiple eras, such as one for the reign of each leader.
     * The exact meaning is determined by the chronology according to the following constraints.
     * <p>
     * The era in use at 1970-01-01 must have the value 1.
     * Later eras must have sequentially higher values.
     * Earlier eras must have sequentially lower values.
     * Each chronology must refer to an enum or similar singleton to provide the era values.
     * <p>
     * This method returns the singleton era of the correct type for the specified era value.
     *
     * <p>
     *  从数值创建年表时代对象。
     * <p>
     *  时代在概念上是时间线的最大分割。大多数日历系统具有将时间线分为两个时间的单个时期。然而,一些人有多个时代,例如每个领导人统治一个时代。确切的含义由根据以下约束的年表确定。
     * <p>
     *  在1970-01-01使用的时代必须具有值1.后来的时间必须有顺序更高的值。早期时间必须具有顺序较低的值。每个年表必须引用枚举或类似的单例来提供时代值。
     * <p>
     *  此方法返回指定时代值的正确类型的单例时代。
     * 
     * 
     * @param eraValue  the era value
     * @return the calendar system era, not null
     * @throws DateTimeException if unable to create the era
     */
    Era eraOf(int eraValue);

    /**
     * Gets the list of eras for the chronology.
     * <p>
     * Most calendar systems have an era, within which the year has meaning.
     * If the calendar system does not support the concept of eras, an empty
     * list must be returned.
     *
     * <p>
     *  获取年表的时代列表。
     * <p>
     *  大多数日历系统有一个时代,在这个时代里,一年有意义。如果日历系统不支持时间的概念,则必须返回空列表。
     * 
     * 
     * @return the list of eras for the chronology, may be immutable, not null
     */
    List<Era> eras();

    //-----------------------------------------------------------------------
    /**
     * Gets the range of valid values for the specified field.
     * <p>
     * All fields can be expressed as a {@code long} integer.
     * This method returns an object that describes the valid range for that value.
     * <p>
     * Note that the result only describes the minimum and maximum valid values
     * and it is important not to read too much into them. For example, there
     * could be values within the range that are invalid for the field.
     * <p>
     * This method will return a result whether or not the chronology supports the field.
     *
     * <p>
     *  获取指定字段的有效值范围。
     * <p>
     *  所有字段都可以表示为{@code long}整数。此方法返回描述该值的有效范围的对象。
     * <p>
     *  注意,结果只描述最小和最大有效值,重要的是不要过多地读入它们。例如,可能存在对于字段无效的范围内的值。
     * <p>
     * 无论时间顺序是否支持字段,此方法都将返回结果。
     * 
     * 
     * @param field  the field to get the range for, not null
     * @return the range of valid values for the field, not null
     * @throws DateTimeException if the range for the field cannot be obtained
     */
    ValueRange range(ChronoField field);

    //-----------------------------------------------------------------------
    /**
     * Gets the textual representation of this chronology.
     * <p>
     * This returns the textual name used to identify the chronology,
     * suitable for presentation to the user.
     * The parameters control the style of the returned text and the locale.
     *
     * @implSpec
     * The default implementation behaves as the the formatter was used to
     * format the chronology textual name.
     *
     * <p>
     *  获取此年表的文本表示。
     * <p>
     *  这返回用于识别年表的文本名称,适合于向用户呈现。参数控制返回的文本和语言环境的样式。
     * 
     *  @implSpec默认实现的行为与格式化程序用于格式化年表文本名称。
     * 
     * 
     * @param style  the style of the text required, not null
     * @param locale  the locale to use, not null
     * @return the text value of the chronology, not null
     */
    default String getDisplayName(TextStyle style, Locale locale) {
        TemporalAccessor temporal = new TemporalAccessor() {
            @Override
            public boolean isSupported(TemporalField field) {
                return false;
            }
            @Override
            public long getLong(TemporalField field) {
                throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
            }
            @SuppressWarnings("unchecked")
            @Override
            public <R> R query(TemporalQuery<R> query) {
                if (query == TemporalQueries.chronology()) {
                    return (R) Chronology.this;
                }
                return TemporalAccessor.super.query(query);
            }
        };
        return new DateTimeFormatterBuilder().appendChronologyText(style).toFormatter(locale).format(temporal);
    }

    //-----------------------------------------------------------------------
    /**
     * Resolves parsed {@code ChronoField} values into a date during parsing.
     * <p>
     * Most {@code TemporalField} implementations are resolved using the
     * resolve method on the field. By contrast, the {@code ChronoField} class
     * defines fields that only have meaning relative to the chronology.
     * As such, {@code ChronoField} date fields are resolved here in the
     * context of a specific chronology.
     * <p>
     * The default implementation, which explains typical resolve behaviour,
     * is provided in {@link AbstractChronology}.
     *
     * <p>
     *  在解析期间将解析的{@code ChronoField}值解析为日期。
     * <p>
     *  大多数{@code TemporalField}实现都使用字段上的resolve方法解析。相比之下,{@code ChronoField}类定义的字段只有相对于年表的含义。
     * 因此,{@code ChronoField}日期字段在此处在特定年表的上下文中解析。
     * <p>
     *  {@link AbstractChronology}中提供了默认实现(解释了典型的解析行为)。
     * 
     * 
     * @param fieldValues  the map of fields to values, which can be updated, not null
     * @param resolverStyle  the requested type of resolve, not null
     * @return the resolved date, null if insufficient information to create a date
     * @throws DateTimeException if the date cannot be resolved, typically
     *  because of a conflict in the input data
     */
    ChronoLocalDate resolveDate(Map<TemporalField, Long> fieldValues, ResolverStyle resolverStyle);

    //-----------------------------------------------------------------------
    /**
     * Obtains a period for this chronology based on years, months and days.
     * <p>
     * This returns a period tied to this chronology using the specified
     * years, months and days.  All supplied chronologies use periods
     * based on years, months and days, however the {@code ChronoPeriod} API
     * allows the period to be represented using other units.
     *
     * @implSpec
     * The default implementation returns an implementation class suitable
     * for most calendar systems. It is based solely on the three units.
     * Normalization, addition and subtraction derive the number of months
     * in a year from the {@link #range(ChronoField)}. If the number of
     * months within a year is fixed, then the calculation approach for
     * addition, subtraction and normalization is slightly different.
     * <p>
     * If implementing an unusual calendar system that is not based on
     * years, months and days, or where you want direct control, then
     * the {@code ChronoPeriod} interface must be directly implemented.
     * <p>
     * The returned period is immutable and thread-safe.
     *
     * <p>
     *  基于年,月和日获取这个年表的期间。
     * <p>
     *  这将使用指定的年份,月份和天数返回与此年表相关的期间。所有提供的年表都使用基于年,月和日的期间,但{@code ChronoPeriod} API允许使用其他单位表示期间。
     * 
     * @implSpec默认实现返回适用于大多数日历系统的实现类。它完全基于三个单位。归一化,加法和减法从{@link #range(ChronoField)}得到一年中的月数。
     * 如果一年内的月数是固定的,则加法,减法和归一化的计算方法略有不同。
     * <p>
     *  如果实施不是基于年,月和日的特殊日历系统,或者您希望直接控制,则必须直接实施{@code ChronoPeriod}界面。
     * <p>
     *  返回的周期是不可变的和线程安全的。
     * 
     * 
     * @param years  the number of years, may be negative
     * @param months  the number of years, may be negative
     * @param days  the number of years, may be negative
     * @return the period in terms of this chronology, not null
     */
    default ChronoPeriod period(int years, int months, int days) {
        return new ChronoPeriodImpl(this, years, months, days);
    }

    //-----------------------------------------------------------------------
    /**
     * Compares this chronology to another chronology.
     * <p>
     * The comparison order first by the chronology ID string, then by any
     * additional information specific to the subclass.
     * It is "consistent with equals", as defined by {@link Comparable}.
     *
     * <p>
     *  将这个年表与另一个年表比较。
     * <p>
     *  比较顺序首先由年表ID字符串,然后由任何特定于子类的附加信息。它是"与等号一致",由{@link Comparable}定义。
     * 
     * 
     * @param other  the other chronology to compare to, not null
     * @return the comparator value, negative if less, positive if greater
     */
    @Override
    int compareTo(Chronology other);

    /**
     * Checks if this chronology is equal to another chronology.
     * <p>
     * The comparison is based on the entire state of the object.
     *
     * <p>
     *  检查这个年表是否等于另一个年表。
     * <p>
     *  比较基于对象的整个状态。
     * 
     * 
     * @param obj  the object to check, null returns false
     * @return true if this is equal to the other chronology
     */
    @Override
    boolean equals(Object obj);

    /**
     * A hash code for this chronology.
     * <p>
     * The hash code should be based on the entire state of the object.
     *
     * <p>
     *  这个年表的哈希码。
     * <p>
     *  哈希码应该基于对象的整个状态。
     * 
     * 
     * @return a suitable hash code
     */
    @Override
    int hashCode();

    //-----------------------------------------------------------------------
    /**
     * Outputs this chronology as a {@code String}.
     * <p>
     * The format should include the entire state of the object.
     *
     * <p>
     *  将此年表输出为{@code String}。
     * <p>
     * 
     * @return a string representation of this chronology, not null
     */
    @Override
    String toString();

}
