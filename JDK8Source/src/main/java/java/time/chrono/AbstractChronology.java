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
 *  版权所有
 * 
 *  如果满足以下条件,则允许重新分发和使用源代码和二进制形式(带或不带修改)：
 * 
 *  *源代码的再分发必须保留上述版权声明,此条件列表和以下免责声明
 * 
 *  *二进制形式的再分发必须在随分发版提供的文档和/或其他材料中复制上述版权声明,此条件列表和以下免责声明
 * 
 * *未经特定事先书面许可,JSR-310的名称及其贡献者的名称不得用于支持或宣传衍生自此软件的产品
 * 
 * 本软件由版权所有者和贡献者"按原样"提供,任何明示或暗示的担保,包括但不限于适销性和针对特定用途的适用性的默示担保,在任何情况下均不得免版权所有者或贡献者对任何直接,间接,偶发,特殊,惩罚性或后果性损害
 * (包括但不限于替代商品或服务的采购,使用,数据或利润损失;或业务中断)有责任的理论,无论是在合同,严格责任或侵权(包括疏忽或其他方式)以任何方式使用本软件,即使已被告知此类损害的可能性。
 * 
 */
package java.time.chrono;

import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.EPOCH_DAY;
import static java.time.temporal.ChronoField.ERA;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.PROLEPTIC_MONTH;
import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.WEEKS;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import sun.util.logging.PlatformLogger;

/**
 * An abstract implementation of a calendar system, used to organize and identify dates.
 * <p>
 * The main date and time API is built on the ISO calendar system.
 * The chronology operates behind the scenes to represent the general concept of a calendar system.
 * <p>
 * See {@link Chronology} for more details.
 *
 * @implSpec
 * This class is separated from the {@code Chronology} interface so that the static methods
 * are not inherited. While {@code Chronology} can be implemented directly, it is strongly
 * recommended to extend this abstract class instead.
 * <p>
 * This class must be implemented with care to ensure other classes operate correctly.
 * All implementations that can be instantiated must be final, immutable and thread-safe.
 * Subclasses should be Serializable wherever possible.
 *
 * <p>
 * 日历系统的抽象实现,用于组织和识别日期
 * <p>
 *  主日期和时间API建立在ISO日历系统上时间顺序在幕后操作,代表日历系统的一般概念
 * <p>
 *  有关详细信息,请参阅{@link Chronology}
 * 
 *  @implSpec这个类与{@code Chronology}接口分离,因此静态方法不会被继承当{@code Chronology}可以直接实现时,强烈建议扩展这个抽象类
 * <p>
 *  这个类必须小心地实现,以确保其他类正常运行所有可以实例化的实现必须是final,immutable和线程安全的子类应该是可序列化的
 * 
 * 
 * @since 1.8
 */
public abstract class AbstractChronology implements Chronology {

    /**
     * ChronoLocalDate order constant.
     * <p>
     *  ChronoLocalDate订单常量
     * 
     */
    static final Comparator<ChronoLocalDate> DATE_ORDER =
        (Comparator<ChronoLocalDate> & Serializable) (date1, date2) -> {
            return Long.compare(date1.toEpochDay(), date2.toEpochDay());
        };
    /**
     * ChronoLocalDateTime order constant.
     * <p>
     * ChronoLocalDateTime顺序常量
     * 
     */
    static final Comparator<ChronoLocalDateTime<? extends ChronoLocalDate>> DATE_TIME_ORDER =
        (Comparator<ChronoLocalDateTime<? extends ChronoLocalDate>> & Serializable) (dateTime1, dateTime2) -> {
            int cmp = Long.compare(dateTime1.toLocalDate().toEpochDay(), dateTime2.toLocalDate().toEpochDay());
            if (cmp == 0) {
                cmp = Long.compare(dateTime1.toLocalTime().toNanoOfDay(), dateTime2.toLocalTime().toNanoOfDay());
            }
            return cmp;
        };
    /**
     * ChronoZonedDateTime order constant.
     * <p>
     *  ChronoZonedDateTime顺序常量
     * 
     */
    static final Comparator<ChronoZonedDateTime<?>> INSTANT_ORDER =
            (Comparator<ChronoZonedDateTime<?>> & Serializable) (dateTime1, dateTime2) -> {
                int cmp = Long.compare(dateTime1.toEpochSecond(), dateTime2.toEpochSecond());
                if (cmp == 0) {
                    cmp = Long.compare(dateTime1.toLocalTime().getNano(), dateTime2.toLocalTime().getNano());
                }
                return cmp;
            };

    /**
     * Map of available calendars by ID.
     * <p>
     *  可用日历的地图(按ID)
     * 
     */
    private static final ConcurrentHashMap<String, Chronology> CHRONOS_BY_ID = new ConcurrentHashMap<>();
    /**
     * Map of available calendars by calendar type.
     * <p>
     *  按日历类型显示可用日历的地图
     * 
     */
    private static final ConcurrentHashMap<String, Chronology> CHRONOS_BY_TYPE = new ConcurrentHashMap<>();

    /**
     * Register a Chronology by its ID and type for lookup by {@link #of(String)}.
     * Chronologies must not be registered until they are completely constructed.
     * Specifically, not in the constructor of Chronology.
     *
     * <p>
     *  通过{@link #of(String)}查找的ID和类型注册年表Chronology必须在完全构建之前注册。具体而言,不是在年表的构造函数中
     * 
     * 
     * @param chrono the chronology to register; not null
     * @return the already registered Chronology if any, may be null
     */
    static Chronology registerChrono(Chronology chrono) {
        return registerChrono(chrono, chrono.getId());
    }

    /**
     * Register a Chronology by ID and type for lookup by {@link #of(String)}.
     * Chronos must not be registered until they are completely constructed.
     * Specifically, not in the constructor of Chronology.
     *
     * <p>
     *  通过{@link #of(String)}查找的ID和类型注册年表。Chronos必须注册,直到它们完全构造特别是,不是在年表的构造函数
     * 
     * 
     * @param chrono the chronology to register; not null
     * @param id the ID to register the chronology; not null
     * @return the already registered Chronology if any, may be null
     */
    static Chronology registerChrono(Chronology chrono, String id) {
        Chronology prev = CHRONOS_BY_ID.putIfAbsent(id, chrono);
        if (prev == null) {
            String type = chrono.getCalendarType();
            if (type != null) {
                CHRONOS_BY_TYPE.putIfAbsent(type, chrono);
            }
        }
        return prev;
    }

    /**
     * Initialization of the maps from id and type to Chronology.
     * The ServiceLoader is used to find and register any implementations
     * of {@link java.time.chrono.AbstractChronology} found in the bootclass loader.
     * The built-in chronologies are registered explicitly.
     * Calendars configured via the Thread's context classloader are local
     * to that thread and are ignored.
     * <p>
     * The initialization is done only once using the registration
     * of the IsoChronology as the test and the final step.
     * Multiple threads may perform the initialization concurrently.
     * Only the first registration of each Chronology is retained by the
     * ConcurrentHashMap.
     * <p>
     * 将映射从id和类型初始化为Chronology ServiceLoader用于查找和注册在bootclass加载器中找到的任何实现{@link javatimechronoAbstractChronology}
     * 内置的年表是明确注册的通过Thread的上下文类加载器配置的日历是本地的线程并被忽略。
     * <p>
     *  初始化仅使用IsoChronology的注册作为测试和最后一步进行一次多线程可以同时执行初始化只有每个Chronology的第一个注册被ConcurrentHashMap保留
     * 
     * 
     * @return true if the cache was initialized
     */
    private static boolean initCache() {
        if (CHRONOS_BY_ID.get("ISO") == null) {
            // Initialization is incomplete

            // Register built-in Chronologies
            registerChrono(HijrahChronology.INSTANCE);
            registerChrono(JapaneseChronology.INSTANCE);
            registerChrono(MinguoChronology.INSTANCE);
            registerChrono(ThaiBuddhistChronology.INSTANCE);

            // Register Chronologies from the ServiceLoader
            @SuppressWarnings("rawtypes")
            ServiceLoader<AbstractChronology> loader =  ServiceLoader.load(AbstractChronology.class, null);
            for (AbstractChronology chrono : loader) {
                String id = chrono.getId();
                if (id.equals("ISO") || registerChrono(chrono) != null) {
                    // Log the attempt to replace an existing Chronology
                    PlatformLogger logger = PlatformLogger.getLogger("java.time.chrono");
                    logger.warning("Ignoring duplicate Chronology, from ServiceLoader configuration "  + id);
                }
            }

            // finally, register IsoChronology to mark initialization is complete
            registerChrono(IsoChronology.INSTANCE);
            return true;
        }
        return false;
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code Chronology} from a locale.
     * <p>
     * See {@link Chronology#ofLocale(Locale)}.
     *
     * <p>
     *  从语言环境获取{@code Chronology}的实例
     * <p>
     *  请参阅{@link Chronology#ofLocale(Locale)}
     * 
     * 
     * @param locale  the locale to use to obtain the calendar system, not null
     * @return the calendar system associated with the locale, not null
     * @throws java.time.DateTimeException if the locale-specified calendar cannot be found
     */
    static Chronology ofLocale(Locale locale) {
        Objects.requireNonNull(locale, "locale");
        String type = locale.getUnicodeLocaleType("ca");
        if (type == null || "iso".equals(type) || "iso8601".equals(type)) {
            return IsoChronology.INSTANCE;
        }
        // Not pre-defined; lookup by the type
        do {
            Chronology chrono = CHRONOS_BY_TYPE.get(type);
            if (chrono != null) {
                return chrono;
            }
            // If not found, do the initialization (once) and repeat the lookup
        } while (initCache());

        // Look for a Chronology using ServiceLoader of the Thread's ContextClassLoader
        // Application provided Chronologies must not be cached
        @SuppressWarnings("rawtypes")
        ServiceLoader<Chronology> loader = ServiceLoader.load(Chronology.class);
        for (Chronology chrono : loader) {
            if (type.equals(chrono.getCalendarType())) {
                return chrono;
            }
        }
        throw new DateTimeException("Unknown calendar system: " + type);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code Chronology} from a chronology ID or
     * calendar system type.
     * <p>
     * See {@link Chronology#of(String)}.
     *
     * <p>
     * 从年表ID或日历系统类型获取{@code Chronology}的实例
     * <p>
     *  请参阅{@link Chronology#of(String)}
     * 
     * 
     * @param id  the chronology ID or calendar system type, not null
     * @return the chronology with the identifier requested, not null
     * @throws java.time.DateTimeException if the chronology cannot be found
     */
    static Chronology of(String id) {
        Objects.requireNonNull(id, "id");
        do {
            Chronology chrono = of0(id);
            if (chrono != null) {
                return chrono;
            }
            // If not found, do the initialization (once) and repeat the lookup
        } while (initCache());

        // Look for a Chronology using ServiceLoader of the Thread's ContextClassLoader
        // Application provided Chronologies must not be cached
        @SuppressWarnings("rawtypes")
        ServiceLoader<Chronology> loader = ServiceLoader.load(Chronology.class);
        for (Chronology chrono : loader) {
            if (id.equals(chrono.getId()) || id.equals(chrono.getCalendarType())) {
                return chrono;
            }
        }
        throw new DateTimeException("Unknown chronology: " + id);
    }

    /**
     * Obtains an instance of {@code Chronology} from a chronology ID or
     * calendar system type.
     *
     * <p>
     *  从年表ID或日历系统类型获取{@code Chronology}的实例
     * 
     * 
     * @param id  the chronology ID or calendar system type, not null
     * @return the chronology with the identifier requested, or {@code null} if not found
     */
    private static Chronology of0(String id) {
        Chronology chrono = CHRONOS_BY_ID.get(id);
        if (chrono == null) {
            chrono = CHRONOS_BY_TYPE.get(id);
        }
        return chrono;
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
     *  返回可用的年表
     * <p>
     *  每个返回的{@code Chronology}可供系统使用这些年表包括系统年表和应用程序通过ServiceLoader配置提供的任何年表
     * 
     * 
     * @return the independent, modifiable set of the available chronology IDs, not null
     */
    static Set<Chronology> getAvailableChronologies() {
        initCache();       // force initialization
        HashSet<Chronology> chronos = new HashSet<>(CHRONOS_BY_ID.values());

        /// Add in Chronologies from the ServiceLoader configuration
        @SuppressWarnings("rawtypes")
        ServiceLoader<Chronology> loader = ServiceLoader.load(Chronology.class);
        for (Chronology chrono : loader) {
            chronos.add(chrono);
        }
        return chronos;
    }

    //-----------------------------------------------------------------------
    /**
     * Creates an instance.
     * <p>
     *  创建实例
     * 
     */
    protected AbstractChronology() {
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
     * {@code ChronoField} instances are resolved by this method, which may
     * be overridden in subclasses.
     * <ul>
     * <li>{@code EPOCH_DAY} - If present, this is converted to a date and
     *  all other date fields are then cross-checked against the date.
     * <li>{@code PROLEPTIC_MONTH} - If present, then it is split into the
     *  {@code YEAR} and {@code MONTH_OF_YEAR}. If the mode is strict or smart
     *  then the field is validated.
     * <li>{@code YEAR_OF_ERA} and {@code ERA} - If both are present, then they
     *  are combined to form a {@code YEAR}. In lenient mode, the {@code YEAR_OF_ERA}
     *  range is not validated, in smart and strict mode it is. The {@code ERA} is
     *  validated for range in all three modes. If only the {@code YEAR_OF_ERA} is
     *  present, and the mode is smart or lenient, then the last available era
     *  is assumed. In strict mode, no era is assumed and the {@code YEAR_OF_ERA} is
     *  left untouched. If only the {@code ERA} is present, then it is left untouched.
     * <li>{@code YEAR}, {@code MONTH_OF_YEAR} and {@code DAY_OF_MONTH} -
     *  If all three are present, then they are combined to form a date.
     *  In all three modes, the {@code YEAR} is validated.
     *  If the mode is smart or strict, then the month and day are validated.
     *  If the mode is lenient, then the date is combined in a manner equivalent to
     *  creating a date on the first day of the first month in the requested year,
     *  then adding the difference in months, then the difference in days.
     *  If the mode is smart, and the day-of-month is greater than the maximum for
     *  the year-month, then the day-of-month is adjusted to the last day-of-month.
     *  If the mode is strict, then the three fields must form a valid date.
     * <li>{@code YEAR} and {@code DAY_OF_YEAR} -
     *  If both are present, then they are combined to form a date.
     *  In all three modes, the {@code YEAR} is validated.
     *  If the mode is lenient, then the date is combined in a manner equivalent to
     *  creating a date on the first day of the requested year, then adding
     *  the difference in days.
     *  If the mode is smart or strict, then the two fields must form a valid date.
     * <li>{@code YEAR}, {@code MONTH_OF_YEAR}, {@code ALIGNED_WEEK_OF_MONTH} and
     *  {@code ALIGNED_DAY_OF_WEEK_IN_MONTH} -
     *  If all four are present, then they are combined to form a date.
     *  In all three modes, the {@code YEAR} is validated.
     *  If the mode is lenient, then the date is combined in a manner equivalent to
     *  creating a date on the first day of the first month in the requested year, then adding
     *  the difference in months, then the difference in weeks, then in days.
     *  If the mode is smart or strict, then the all four fields are validated to
     *  their outer ranges. The date is then combined in a manner equivalent to
     *  creating a date on the first day of the requested year and month, then adding
     *  the amount in weeks and days to reach their values. If the mode is strict,
     *  the date is additionally validated to check that the day and week adjustment
     *  did not change the month.
     * <li>{@code YEAR}, {@code MONTH_OF_YEAR}, {@code ALIGNED_WEEK_OF_MONTH} and
     *  {@code DAY_OF_WEEK} - If all four are present, then they are combined to
     *  form a date. The approach is the same as described above for
     *  years, months and weeks in {@code ALIGNED_DAY_OF_WEEK_IN_MONTH}.
     *  The day-of-week is adjusted as the next or same matching day-of-week once
     *  the years, months and weeks have been handled.
     * <li>{@code YEAR}, {@code ALIGNED_WEEK_OF_YEAR} and {@code ALIGNED_DAY_OF_WEEK_IN_YEAR} -
     *  If all three are present, then they are combined to form a date.
     *  In all three modes, the {@code YEAR} is validated.
     *  If the mode is lenient, then the date is combined in a manner equivalent to
     *  creating a date on the first day of the requested year, then adding
     *  the difference in weeks, then in days.
     *  If the mode is smart or strict, then the all three fields are validated to
     *  their outer ranges. The date is then combined in a manner equivalent to
     *  creating a date on the first day of the requested year, then adding
     *  the amount in weeks and days to reach their values. If the mode is strict,
     *  the date is additionally validated to check that the day and week adjustment
     *  did not change the year.
     * <li>{@code YEAR}, {@code ALIGNED_WEEK_OF_YEAR} and {@code DAY_OF_WEEK} -
     *  If all three are present, then they are combined to form a date.
     *  The approach is the same as described above for years and weeks in
     *  {@code ALIGNED_DAY_OF_WEEK_IN_YEAR}. The day-of-week is adjusted as the
     *  next or same matching day-of-week once the years and weeks have been handled.
     * </ul>
     * <p>
     * The default implementation is suitable for most calendar systems.
     * If {@link java.time.temporal.ChronoField#YEAR_OF_ERA} is found without an {@link java.time.temporal.ChronoField#ERA}
     * then the last era in {@link #eras()} is used.
     * The implementation assumes a 7 day week, that the first day-of-month
     * has the value 1, that first day-of-year has the value 1, and that the
     * first of the month and year always exists.
     *
     * <p>
     *  在解析期间将解析的{@code ChronoField}值解析为日期
     * <p>
     * 大多数{@code TemporalField}实现使用字段上的resolve方法解析相比之下,{@code ChronoField}类定义了仅对于年表有意义的字段。
     * 因此,{@code ChronoField}日期字段在此解析特定年表的上下文。
     * <p>
     *  {@code ChronoField}实例由此方法解析,可能会在子类中覆盖
     * <ul>
     * <li> {@ code EPOCH_DAY}  - 如果存在,则将其转换为日期,然后根据日期交叉检查所有其他日期字段。
     * <li> {@ code PROLEPTIC_MONTH}  - 如果存在,则将其拆分为{ @code YEAR}和{@code MONTH_OF_YEAR}如果模式是strict或smart,则会验证
     * 此字段<li> {@ code YEAR_OF_ERA}和{@code ERA}  - 如果两者都存在, @code YEAR}在宽松模式下,{@code YEAR_OF_ERA}范围未验证,在智能和严
     * 格模式下,{@code ERA}在所有三种模式中的范围内验证如果只有{@code YEAR_OF_ERA} ,并且模式是智能的或宽松的,则假定最后一个可用的时代在严格模式中,不假定时代,并且{@code YEAR_OF_ERA}
     * 保持不变如果只有{@code ERA}存在,那么它将保持不变。
     * <li> {@ code EPOCH_DAY}  - 如果存在,则将其转换为日期,然后根据日期交叉检查所有其他日期字段。
     * <li> {@ code YEAR},{@code MONTH_OF_YEAR}和{@code DAY_OF_MONTH}  - 如果三个都存在,日期在所有三种模式下,{@code YEAR}被验证如果
     * 模式是智能或严格,则验证月份和日期如果模式是宽松的,则日期以等同于在在所请求的年份中的第一个月的第一天,然后加上在月中的差额,然后在天中的差异如果模式是聪明的,并且日期大于年 - 月的最大值,那么天将月
     * 份调整为最后一个日期如果模式为严格,则三个字段必须形成有效日期<li> {@ code YEAR}和{@code DAY_OF_YEAR}  - 如果两者都存在,则它们被组合以形成日期在所有三种模式下,
     * {@code YEAR}被验证如果模式是宽松的,以等同于在所请求年份的第一天创建日期的方式组合,然后添加天数差异如果模式是智能或严格,则这两个字段必须形成有效日期<li> {@ code YEAR} ,
     * {@code MONTH_OF_YEAR},{@code ALIGNED_WEEK_OF_MONTH}和{@code ALIGNED_DAY_OF_WEEK_IN_MONTH}  - 如果所有四个都存在
     * ,则将它们组合形成日期在所有三种模式下,{@code YEAR}如果模式是宽松的,则日期以等同于在所请求的年份中的第一个月的第一天创建日期的方式组合,然后添加差异以月为单位,然后以星期为单位,然后以天为
     * 单位如果模式是智能的或严格的,则所有四个字段被验证到它们的外部范围。
     * <li> {@ code EPOCH_DAY}  - 如果存在,则将其转换为日期,然后根据日期交叉检查所有其他日期字段。
     * 然后,日期以等同于在所请求的年份和月份的第一天创建日期的方式组合,然后将以星期和天为单位的量相加达到其值如果模式为strict,则日期会另外验证,以检查日期和周的调整是否未更改月份<li> {@ code YEAR}
     * ,{@code MONTH_OF_YEAR},{@code ALIGNED_WEEK_OF_MONTH}和{@代码DAY_OF_WEEK}  - 如果所有四个都存在,则将它们组合以形成日期该方法与上述{@code ALIGNED_DAY_OF_WEEK_IN_MONTH}
     * 中的年,月和周相同。
     * <li> {@ code EPOCH_DAY}  - 如果存在,则将其转换为日期,然后根据日期交叉检查所有其他日期字段。
     * 一旦处理了年,月和周,星期几被调整为下一个或相同的匹配星期< li> {@ code YEAR},{@code ALIGNED_WEEK_OF_YEAR}和{@code ALIGNED_DAY_OF_WEEK_IN_YEAR}
     *   - 如果所有三个都存在,则将它们组合形成日期在所有三种模式下,{@code YEAR}是宽松的,则日期以等同于在所请求的年份的第一天创建日期的方式组合,然后以星期,然后以天为单位来添加差异。
     * <li> {@ code EPOCH_DAY}  - 如果存在,则将其转换为日期,然后根据日期交叉检查所有其他日期字段。
     * 如果模式是智能的或严格的,则所有三个字段被验证到它们的外部范围日期然后以等同于在所请求年份的第一天创建日期的方式组合,然后添加以周和天为单位的量以达到其值。
     * 如果模式是严格的,则日期被另外验证以检查日期和周调整没有改变年份<li> {@ code YEAR},{@code ALIGNED_WEEK_OF_YEAR}和{@code DAY_OF_WEEK}  
     * - 如果所有三个都存在,那么它们被组合以形成日期该方法与所描述的相同在{@code ALIGNED_DAY_OF_WEEK_IN_YEAR}中的多年和几周内,一旦处理了几年和几周,就会将星期几调整为下一
     * 个或相同的匹配星期。
     * 如果模式是智能的或严格的,则所有三个字段被验证到它们的外部范围日期然后以等同于在所请求年份的第一天创建日期的方式组合,然后添加以周和天为单位的量以达到其值。
     * </ul>
     * <p>
     * 默认实现适用于大多数日历系统如果在没有{@link javatimetemporalChronoField#ERA}的情况下找到{@link javatimetemporalChronoField#YEAR_OF_ERA}
     * ,则使用{@link #eras()}中的最后一个时代。
     * 实现假设为7天星期,第一个日期具有值1,第一个日期具有值1,并且月和年的第一个总是存在。
     * 
     * 
     * @param fieldValues  the map of fields to values, which can be updated, not null
     * @param resolverStyle  the requested type of resolve, not null
     * @return the resolved date, null if insufficient information to create a date
     * @throws java.time.DateTimeException if the date cannot be resolved, typically
     *  because of a conflict in the input data
     */
    @Override
    public ChronoLocalDate resolveDate(Map<TemporalField, Long> fieldValues, ResolverStyle resolverStyle) {
        // check epoch-day before inventing era
        if (fieldValues.containsKey(EPOCH_DAY)) {
            return dateEpochDay(fieldValues.remove(EPOCH_DAY));
        }

        // fix proleptic month before inventing era
        resolveProlepticMonth(fieldValues, resolverStyle);

        // invent era if necessary to resolve year-of-era
        ChronoLocalDate resolved = resolveYearOfEra(fieldValues, resolverStyle);
        if (resolved != null) {
            return resolved;
        }

        // build date
        if (fieldValues.containsKey(YEAR)) {
            if (fieldValues.containsKey(MONTH_OF_YEAR)) {
                if (fieldValues.containsKey(DAY_OF_MONTH)) {
                    return resolveYMD(fieldValues, resolverStyle);
                }
                if (fieldValues.containsKey(ALIGNED_WEEK_OF_MONTH)) {
                    if (fieldValues.containsKey(ALIGNED_DAY_OF_WEEK_IN_MONTH)) {
                        return resolveYMAA(fieldValues, resolverStyle);
                    }
                    if (fieldValues.containsKey(DAY_OF_WEEK)) {
                        return resolveYMAD(fieldValues, resolverStyle);
                    }
                }
            }
            if (fieldValues.containsKey(DAY_OF_YEAR)) {
                return resolveYD(fieldValues, resolverStyle);
            }
            if (fieldValues.containsKey(ALIGNED_WEEK_OF_YEAR)) {
                if (fieldValues.containsKey(ALIGNED_DAY_OF_WEEK_IN_YEAR)) {
                    return resolveYAA(fieldValues, resolverStyle);
                }
                if (fieldValues.containsKey(DAY_OF_WEEK)) {
                    return resolveYAD(fieldValues, resolverStyle);
                }
            }
        }
        return null;
    }

    void resolveProlepticMonth(Map<TemporalField, Long> fieldValues, ResolverStyle resolverStyle) {
        Long pMonth = fieldValues.remove(PROLEPTIC_MONTH);
        if (pMonth != null) {
            if (resolverStyle != ResolverStyle.LENIENT) {
                PROLEPTIC_MONTH.checkValidValue(pMonth);
            }
            // first day-of-month is likely to be safest for setting proleptic-month
            // cannot add to year zero, as not all chronologies have a year zero
            ChronoLocalDate chronoDate = dateNow()
                    .with(DAY_OF_MONTH, 1).with(PROLEPTIC_MONTH, pMonth);
            addFieldValue(fieldValues, MONTH_OF_YEAR, chronoDate.get(MONTH_OF_YEAR));
            addFieldValue(fieldValues, YEAR, chronoDate.get(YEAR));
        }
    }

    ChronoLocalDate resolveYearOfEra(Map<TemporalField, Long> fieldValues, ResolverStyle resolverStyle) {
        Long yoeLong = fieldValues.remove(YEAR_OF_ERA);
        if (yoeLong != null) {
            Long eraLong = fieldValues.remove(ERA);
            int yoe;
            if (resolverStyle != ResolverStyle.LENIENT) {
                yoe = range(YEAR_OF_ERA).checkValidIntValue(yoeLong, YEAR_OF_ERA);
            } else {
                yoe = Math.toIntExact(yoeLong);
            }
            if (eraLong != null) {
                Era eraObj = eraOf(range(ERA).checkValidIntValue(eraLong, ERA));
                addFieldValue(fieldValues, YEAR, prolepticYear(eraObj, yoe));
            } else {
                if (fieldValues.containsKey(YEAR)) {
                    int year = range(YEAR).checkValidIntValue(fieldValues.get(YEAR), YEAR);
                    ChronoLocalDate chronoDate = dateYearDay(year, 1);
                    addFieldValue(fieldValues, YEAR, prolepticYear(chronoDate.getEra(), yoe));
                } else if (resolverStyle == ResolverStyle.STRICT) {
                    // do not invent era if strict
                    // reinstate the field removed earlier, no cross-check issues
                    fieldValues.put(YEAR_OF_ERA, yoeLong);
                } else {
                    List<Era> eras = eras();
                    if (eras.isEmpty()) {
                        addFieldValue(fieldValues, YEAR, yoe);
                    } else {
                        Era eraObj = eras.get(eras.size() - 1);
                        addFieldValue(fieldValues, YEAR, prolepticYear(eraObj, yoe));
                    }
                }
            }
        } else if (fieldValues.containsKey(ERA)) {
            range(ERA).checkValidValue(fieldValues.get(ERA), ERA);  // always validated
        }
        return null;
    }

    ChronoLocalDate resolveYMD(Map<TemporalField, Long> fieldValues, ResolverStyle resolverStyle) {
        int y = range(YEAR).checkValidIntValue(fieldValues.remove(YEAR), YEAR);
        if (resolverStyle == ResolverStyle.LENIENT) {
            long months = Math.subtractExact(fieldValues.remove(MONTH_OF_YEAR), 1);
            long days = Math.subtractExact(fieldValues.remove(DAY_OF_MONTH), 1);
            return date(y, 1, 1).plus(months, MONTHS).plus(days, DAYS);
        }
        int moy = range(MONTH_OF_YEAR).checkValidIntValue(fieldValues.remove(MONTH_OF_YEAR), MONTH_OF_YEAR);
        ValueRange domRange = range(DAY_OF_MONTH);
        int dom = domRange.checkValidIntValue(fieldValues.remove(DAY_OF_MONTH), DAY_OF_MONTH);
        if (resolverStyle == ResolverStyle.SMART) {  // previous valid
            try {
                return date(y, moy, dom);
            } catch (DateTimeException ex) {
                return date(y, moy, 1).with(TemporalAdjusters.lastDayOfMonth());
            }
        }
        return date(y, moy, dom);
    }

    ChronoLocalDate resolveYD(Map<TemporalField, Long> fieldValues, ResolverStyle resolverStyle) {
        int y = range(YEAR).checkValidIntValue(fieldValues.remove(YEAR), YEAR);
        if (resolverStyle == ResolverStyle.LENIENT) {
            long days = Math.subtractExact(fieldValues.remove(DAY_OF_YEAR), 1);
            return dateYearDay(y, 1).plus(days, DAYS);
        }
        int doy = range(DAY_OF_YEAR).checkValidIntValue(fieldValues.remove(DAY_OF_YEAR), DAY_OF_YEAR);
        return dateYearDay(y, doy);  // smart is same as strict
    }

    ChronoLocalDate resolveYMAA(Map<TemporalField, Long> fieldValues, ResolverStyle resolverStyle) {
        int y = range(YEAR).checkValidIntValue(fieldValues.remove(YEAR), YEAR);
        if (resolverStyle == ResolverStyle.LENIENT) {
            long months = Math.subtractExact(fieldValues.remove(MONTH_OF_YEAR), 1);
            long weeks = Math.subtractExact(fieldValues.remove(ALIGNED_WEEK_OF_MONTH), 1);
            long days = Math.subtractExact(fieldValues.remove(ALIGNED_DAY_OF_WEEK_IN_MONTH), 1);
            return date(y, 1, 1).plus(months, MONTHS).plus(weeks, WEEKS).plus(days, DAYS);
        }
        int moy = range(MONTH_OF_YEAR).checkValidIntValue(fieldValues.remove(MONTH_OF_YEAR), MONTH_OF_YEAR);
        int aw = range(ALIGNED_WEEK_OF_MONTH).checkValidIntValue(fieldValues.remove(ALIGNED_WEEK_OF_MONTH), ALIGNED_WEEK_OF_MONTH);
        int ad = range(ALIGNED_DAY_OF_WEEK_IN_MONTH).checkValidIntValue(fieldValues.remove(ALIGNED_DAY_OF_WEEK_IN_MONTH), ALIGNED_DAY_OF_WEEK_IN_MONTH);
        ChronoLocalDate date = date(y, moy, 1).plus((aw - 1) * 7 + (ad - 1), DAYS);
        if (resolverStyle == ResolverStyle.STRICT && date.get(MONTH_OF_YEAR) != moy) {
            throw new DateTimeException("Strict mode rejected resolved date as it is in a different month");
        }
        return date;
    }

    ChronoLocalDate resolveYMAD(Map<TemporalField, Long> fieldValues, ResolverStyle resolverStyle) {
        int y = range(YEAR).checkValidIntValue(fieldValues.remove(YEAR), YEAR);
        if (resolverStyle == ResolverStyle.LENIENT) {
            long months = Math.subtractExact(fieldValues.remove(MONTH_OF_YEAR), 1);
            long weeks = Math.subtractExact(fieldValues.remove(ALIGNED_WEEK_OF_MONTH), 1);
            long dow = Math.subtractExact(fieldValues.remove(DAY_OF_WEEK), 1);
            return resolveAligned(date(y, 1, 1), months, weeks, dow);
        }
        int moy = range(MONTH_OF_YEAR).checkValidIntValue(fieldValues.remove(MONTH_OF_YEAR), MONTH_OF_YEAR);
        int aw = range(ALIGNED_WEEK_OF_MONTH).checkValidIntValue(fieldValues.remove(ALIGNED_WEEK_OF_MONTH), ALIGNED_WEEK_OF_MONTH);
        int dow = range(DAY_OF_WEEK).checkValidIntValue(fieldValues.remove(DAY_OF_WEEK), DAY_OF_WEEK);
        ChronoLocalDate date = date(y, moy, 1).plus((aw - 1) * 7, DAYS).with(nextOrSame(DayOfWeek.of(dow)));
        if (resolverStyle == ResolverStyle.STRICT && date.get(MONTH_OF_YEAR) != moy) {
            throw new DateTimeException("Strict mode rejected resolved date as it is in a different month");
        }
        return date;
    }

    ChronoLocalDate resolveYAA(Map<TemporalField, Long> fieldValues, ResolverStyle resolverStyle) {
        int y = range(YEAR).checkValidIntValue(fieldValues.remove(YEAR), YEAR);
        if (resolverStyle == ResolverStyle.LENIENT) {
            long weeks = Math.subtractExact(fieldValues.remove(ALIGNED_WEEK_OF_YEAR), 1);
            long days = Math.subtractExact(fieldValues.remove(ALIGNED_DAY_OF_WEEK_IN_YEAR), 1);
            return dateYearDay(y, 1).plus(weeks, WEEKS).plus(days, DAYS);
        }
        int aw = range(ALIGNED_WEEK_OF_YEAR).checkValidIntValue(fieldValues.remove(ALIGNED_WEEK_OF_YEAR), ALIGNED_WEEK_OF_YEAR);
        int ad = range(ALIGNED_DAY_OF_WEEK_IN_YEAR).checkValidIntValue(fieldValues.remove(ALIGNED_DAY_OF_WEEK_IN_YEAR), ALIGNED_DAY_OF_WEEK_IN_YEAR);
        ChronoLocalDate date = dateYearDay(y, 1).plus((aw - 1) * 7 + (ad - 1), DAYS);
        if (resolverStyle == ResolverStyle.STRICT && date.get(YEAR) != y) {
            throw new DateTimeException("Strict mode rejected resolved date as it is in a different year");
        }
        return date;
    }

    ChronoLocalDate resolveYAD(Map<TemporalField, Long> fieldValues, ResolverStyle resolverStyle) {
        int y = range(YEAR).checkValidIntValue(fieldValues.remove(YEAR), YEAR);
        if (resolverStyle == ResolverStyle.LENIENT) {
            long weeks = Math.subtractExact(fieldValues.remove(ALIGNED_WEEK_OF_YEAR), 1);
            long dow = Math.subtractExact(fieldValues.remove(DAY_OF_WEEK), 1);
            return resolveAligned(dateYearDay(y, 1), 0, weeks, dow);
        }
        int aw = range(ALIGNED_WEEK_OF_YEAR).checkValidIntValue(fieldValues.remove(ALIGNED_WEEK_OF_YEAR), ALIGNED_WEEK_OF_YEAR);
        int dow = range(DAY_OF_WEEK).checkValidIntValue(fieldValues.remove(DAY_OF_WEEK), DAY_OF_WEEK);
        ChronoLocalDate date = dateYearDay(y, 1).plus((aw - 1) * 7, DAYS).with(nextOrSame(DayOfWeek.of(dow)));
        if (resolverStyle == ResolverStyle.STRICT && date.get(YEAR) != y) {
            throw new DateTimeException("Strict mode rejected resolved date as it is in a different year");
        }
        return date;
    }

    ChronoLocalDate resolveAligned(ChronoLocalDate base, long months, long weeks, long dow) {
        ChronoLocalDate date = base.plus(months, MONTHS).plus(weeks, WEEKS);
        if (dow > 7) {
            date = date.plus((dow - 1) / 7, WEEKS);
            dow = ((dow - 1) % 7) + 1;
        } else if (dow < 1) {
            date = date.plus(Math.subtractExact(dow,  7) / 7, WEEKS);
            dow = ((dow + 6) % 7) + 1;
        }
        return date.with(nextOrSame(DayOfWeek.of((int) dow)));
    }

    /**
     * Adds a field-value pair to the map, checking for conflicts.
     * <p>
     * If the field is not already present, then the field-value pair is added to the map.
     * If the field is already present and it has the same value as that specified, no action occurs.
     * If the field is already present and it has a different value to that specified, then
     * an exception is thrown.
     *
     * <p>
     * 
     * @param field  the field to add, not null
     * @param value  the value to add, not null
     * @throws java.time.DateTimeException if the field is already present with a different value
     */
    void addFieldValue(Map<TemporalField, Long> fieldValues, ChronoField field, long value) {
        Long old = fieldValues.get(field);  // check first for better error message
        if (old != null && old.longValue() != value) {
            throw new DateTimeException("Conflict found: " + field + " " + old + " differs from " + field + " " + value);
        }
        fieldValues.put(field, value);
    }

    //-----------------------------------------------------------------------
    /**
     * Compares this chronology to another chronology.
     * <p>
     * The comparison order first by the chronology ID string, then by any
     * additional information specific to the subclass.
     * It is "consistent with equals", as defined by {@link Comparable}.
     *
     * @implSpec
     * This implementation compares the chronology ID.
     * Subclasses must compare any additional state that they store.
     *
     * <p>
     *  向映射中添加字段值对,检查冲突
     * <p>
     *  如果字段不存在,则将字段值对添加到映射中如果字段已存在,并且它具有与指定的值相同的值,则不会执行操作,如果字段已存在且具有不同的值到指定的,然后抛出异常
     * 
     * 
     * @param other  the other chronology to compare to, not null
     * @return the comparator value, negative if less, positive if greater
     */
    @Override
    public int compareTo(Chronology other) {
        return getId().compareTo(other.getId());
    }

    /**
     * Checks if this chronology is equal to another chronology.
     * <p>
     * The comparison is based on the entire state of the object.
     *
     * @implSpec
     * This implementation checks the type and calls
     * {@link #compareTo(java.time.chrono.Chronology)}.
     *
     * <p>
     * 将这个年表与另一个年表比较
     * <p>
     *  比较顺序首先由年表ID字符串,然后由任何特定于子类的附加信息它是"一致等于",由{@link Comparable}定义,
     * 
     *  @implSpec此实现比较年表ID子类必须比较它们存储的任何其他状态
     * 
     * 
     * @param obj  the object to check, null returns false
     * @return true if this is equal to the other chronology
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
           return true;
        }
        if (obj instanceof AbstractChronology) {
            return compareTo((AbstractChronology) obj) == 0;
        }
        return false;
    }

    /**
     * A hash code for this chronology.
     * <p>
     * The hash code should be based on the entire state of the object.
     *
     * @implSpec
     * This implementation is based on the chronology ID and class.
     * Subclasses should add any additional state that they store.
     *
     * <p>
     *  检查这个年表是否等于另一个年表
     * <p>
     *  比较基于对象的整个状态
     * 
     *  @implSpec这个实现检查类型和调用{@link #compareTo(javatimechronoChronology)}
     * 
     * 
     * @return a suitable hash code
     */
    @Override
    public int hashCode() {
        return getClass().hashCode() ^ getId().hashCode();
    }

    //-----------------------------------------------------------------------
    /**
     * Outputs this chronology as a {@code String}, using the chronology ID.
     *
     * <p>
     *  此年表的哈希代码
     * <p>
     *  哈希码应该基于对象的整个状态
     * 
     * @implSpec这个实现是基于年表ID和类子类应该添加他们存储的任何附加状态
     * 
     * 
     * @return a string representation of this chronology, not null
     */
    @Override
    public String toString() {
        return getId();
    }

    //-----------------------------------------------------------------------
    /**
     * Writes the Chronology using a
     * <a href="../../../serialized-form.html#java.time.chrono.Ser">dedicated serialized form</a>.
     * <pre>
     *  out.writeByte(1);  // identifies this as a Chronology
     *  out.writeUTF(getId());
     * </pre>
     *
     * <p>
     *  使用年表ID将此年表输出为{@code String}
     * 
     * 
     * @return the instance of {@code Ser}, not null
     */
    Object writeReplace() {
        return new Ser(Ser.CHRONO_TYPE, this);
    }

    /**
     * Defend against malicious streams.
     *
     * <p>
     *  使用<a href=\"///serialized-formhtml#javatimechronoSer\">专用序列化表单</a>撰写年表
     * <pre>
     *  outwriteByte(1); //将此标识为年表outwriteUTF(getId());
     * </pre>
     * 
     * @param s the stream to read
     * @throws java.io.InvalidObjectException always
     */
    private void readObject(ObjectInputStream s) throws ObjectStreamException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    void writeExternal(DataOutput out) throws IOException {
        out.writeUTF(getId());
    }

    static Chronology readExternal(DataInput in) throws IOException {
        String id = in.readUTF();
        return Chronology.of(id);
    }

}
