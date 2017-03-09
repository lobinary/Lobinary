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
 * Copyright (c) 2007-2012, Stephen Colebourne & Michael Nascimento Santos
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
 *  版权所有(c)2007-2012,Stephen Colebourne和Michael Nascimento Santos
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
package java.time;

import static java.time.LocalTime.NANOS_PER_SECOND;
import static java.time.LocalTime.SECONDS_PER_DAY;
import static java.time.LocalTime.SECONDS_PER_HOUR;
import static java.time.LocalTime.SECONDS_PER_MINUTE;
import static java.time.temporal.ChronoField.INSTANT_SECONDS;
import static java.time.temporal.ChronoField.MICRO_OF_SECOND;
import static java.time.temporal.ChronoField.MILLI_OF_SECOND;
import static java.time.temporal.ChronoField.NANO_OF_SECOND;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.NANOS;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
import java.time.temporal.ValueRange;
import java.util.Objects;

/**
 * An instantaneous point on the time-line.
 * <p>
 * This class models a single instantaneous point on the time-line.
 * This might be used to record event time-stamps in the application.
 * <p>
 * The range of an instant requires the storage of a number larger than a {@code long}.
 * To achieve this, the class stores a {@code long} representing epoch-seconds and an
 * {@code int} representing nanosecond-of-second, which will always be between 0 and 999,999,999.
 * The epoch-seconds are measured from the standard Java epoch of {@code 1970-01-01T00:00:00Z}
 * where instants after the epoch have positive values, and earlier instants have negative values.
 * For both the epoch-second and nanosecond parts, a larger value is always later on the time-line
 * than a smaller value.
 *
 * <h3>Time-scale</h3>
 * <p>
 * The length of the solar day is the standard way that humans measure time.
 * This has traditionally been subdivided into 24 hours of 60 minutes of 60 seconds,
 * forming a 86400 second day.
 * <p>
 * Modern timekeeping is based on atomic clocks which precisely define an SI second
 * relative to the transitions of a Caesium atom. The length of an SI second was defined
 * to be very close to the 86400th fraction of a day.
 * <p>
 * Unfortunately, as the Earth rotates the length of the day varies.
 * In addition, over time the average length of the day is getting longer as the Earth slows.
 * As a result, the length of a solar day in 2012 is slightly longer than 86400 SI seconds.
 * The actual length of any given day and the amount by which the Earth is slowing
 * are not predictable and can only be determined by measurement.
 * The UT1 time-scale captures the accurate length of day, but is only available some
 * time after the day has completed.
 * <p>
 * The UTC time-scale is a standard approach to bundle up all the additional fractions
 * of a second from UT1 into whole seconds, known as <i>leap-seconds</i>.
 * A leap-second may be added or removed depending on the Earth's rotational changes.
 * As such, UTC permits a day to have 86399 SI seconds or 86401 SI seconds where
 * necessary in order to keep the day aligned with the Sun.
 * <p>
 * The modern UTC time-scale was introduced in 1972, introducing the concept of whole leap-seconds.
 * Between 1958 and 1972, the definition of UTC was complex, with minor sub-second leaps and
 * alterations to the length of the notional second. As of 2012, discussions are underway
 * to change the definition of UTC again, with the potential to remove leap seconds or
 * introduce other changes.
 * <p>
 * Given the complexity of accurate timekeeping described above, this Java API defines
 * its own time-scale, the <i>Java Time-Scale</i>.
 * <p>
 * The Java Time-Scale divides each calendar day into exactly 86400
 * subdivisions, known as seconds.  These seconds may differ from the
 * SI second.  It closely matches the de facto international civil time
 * scale, the definition of which changes from time to time.
 * <p>
 * The Java Time-Scale has slightly different definitions for different
 * segments of the time-line, each based on the consensus international
 * time scale that is used as the basis for civil time. Whenever the
 * internationally-agreed time scale is modified or replaced, a new
 * segment of the Java Time-Scale must be defined for it.  Each segment
 * must meet these requirements:
 * <ul>
 * <li>the Java Time-Scale shall closely match the underlying international
 *  civil time scale;</li>
 * <li>the Java Time-Scale shall exactly match the international civil
 *  time scale at noon each day;</li>
 * <li>the Java Time-Scale shall have a precisely-defined relationship to
 *  the international civil time scale.</li>
 * </ul>
 * There are currently, as of 2013, two segments in the Java time-scale.
 * <p>
 * For the segment from 1972-11-03 (exact boundary discussed below) until
 * further notice, the consensus international time scale is UTC (with
 * leap seconds).  In this segment, the Java Time-Scale is identical to
 * <a href="http://www.cl.cam.ac.uk/~mgk25/time/utc-sls/">UTC-SLS</a>.
 * This is identical to UTC on days that do not have a leap second.
 * On days that do have a leap second, the leap second is spread equally
 * over the last 1000 seconds of the day, maintaining the appearance of
 * exactly 86400 seconds per day.
 * <p>
 * For the segment prior to 1972-11-03, extending back arbitrarily far,
 * the consensus international time scale is defined to be UT1, applied
 * proleptically, which is equivalent to the (mean) solar time on the
 * prime meridian (Greenwich). In this segment, the Java Time-Scale is
 * identical to the consensus international time scale. The exact
 * boundary between the two segments is the instant where UT1 = UTC
 * between 1972-11-03T00:00 and 1972-11-04T12:00.
 * <p>
 * Implementations of the Java time-scale using the JSR-310 API are not
 * required to provide any clock that is sub-second accurate, or that
 * progresses monotonically or smoothly. Implementations are therefore
 * not required to actually perform the UTC-SLS slew or to otherwise be
 * aware of leap seconds. JSR-310 does, however, require that
 * implementations must document the approach they use when defining a
 * clock representing the current instant.
 * See {@link Clock} for details on the available clocks.
 * <p>
 * The Java time-scale is used for all date-time classes.
 * This includes {@code Instant}, {@code LocalDate}, {@code LocalTime}, {@code OffsetDateTime},
 * {@code ZonedDateTime} and {@code Duration}.
 *
 * <p>
 * This is a <a href="{@docRoot}/java/lang/doc-files/ValueBased.html">value-based</a>
 * class; use of identity-sensitive operations (including reference equality
 * ({@code ==}), identity hash code, or synchronization) on instances of
 * {@code Instant} may have unpredictable results and should be avoided.
 * The {@code equals} method should be used for comparisons.
 *
 * @implSpec
 * This class is immutable and thread-safe.
 *
 * <p>
 *  时间线上的瞬时点。
 * <p>
 *  该类对时间线上的单个瞬时点进行建模。这可能用于在应用程序中记录事件时间戳。
 * <p>
 *  即时的范围需要存储大于{@code long}的数字。为了实现这一点,类存储代表时代秒的{@code long}和表示纳秒秒数的{@code int},它将始终在0和999,999,999之间。
 * 纪元秒是从{@code 1970-01-01T00：00：00Z}的标准Java时代测量的,其中时代之后的时刻具有正值,并且较早的时刻具有负值。对于时代秒和纳秒部分,在时间线上总是比较小的值更大的值。
 * 
 * <h3>时间标度</h3>
 * <p>
 *  太阳日的长度是人类测量时间的标准方式。这传统上被细分为24小时60分钟的60秒,形成86400第二天。
 * <p>
 *  现代计时是基于原子钟,其精确地定义相对于铯原子的跃迁的SI秒。 SI秒的长度定义为非常接近一天的86400分数。
 * <p>
 *  不幸的是,随着地球旋转,一天的长度变化。此外,随着时间的推移,一天的平均长度随着地球的变慢而变长。因此,2012年太阳日的长度略长于86400 SI秒。
 * 任何给定日子的实际长度和地球减速的量是不可预测的,只能通过测量来确定。 UT1时标捕获准确的一天的时间,但只在一天完成后一段时间可用。
 * <p>
 *  UTC时标是一种标准方法,将从UT1到秒的所有附加分数合并为一个整秒,称为<i>闰秒</i>。根据地球的旋转变化,可以添加或移除闰秒。
 * 因此,UTC允许一天在必要时具有86399SI秒或86401SI秒,以保持天与太阳对齐。
 * <p>
 * 现代的UTC时标是在1972年引入的,引入了整个闰秒的概念。在1958年和1972年之间,UTC的定义是复杂的,有次要的次秒跳跃和改变的名义秒的长度。
 * 截至2012年,正在进行讨论以改变UTC的​​定义,有可能删除闰秒或引入其他更改。
 * <p>
 *  鉴于上述精确计时的复杂性,该Java API定义了它自己的时间尺度,即<i> Java Time-Scale </i>。
 * <p>
 *  Java Time-Scale将每个日历日划分为精确的86400个细分,称为秒。这些秒数可能不同于SI秒。它与事实上的国际民用时间尺度紧密匹配,其定义随时间而变化。
 * <p>
 *  Java时间尺度对于时间线的不同段具有稍微不同的定义,每个段基于用作民事时间的基础的共识国际时间尺度。每当修改或替换国际商定的时间尺度时,必须为其定义Java时间标度的新段。
 * 每个细分都必须满足以下要求：。
 * <ul>
 *  <li> Java时间尺度应与基本的国际民用时间尺度紧密匹配; </li> <li> Java时间尺度应每天中午与国际民用时间尺度完全匹配; </li> Java时间尺度应与国际民用时间尺度具有精确定
 * 义的关系。
 * </li>。
 * </ul>
 * 目前,截至2013年,Java时间尺度中有两个部分。
 * <p>
 *  对于1972-11-03(下面讨论的精确边界)的分段,直到另行通知,共识国际时间尺度是UTC(闰秒)。
 * 在此细分中,Java时间标度与<a href="http://www.cl.cam.ac.uk/~mgk25/time/utc-sls/"> UTC-SLS </a>完全相同。
 * 这与UTC没有闰秒的日期相同。在有闰秒的日子,闰秒在一天的最后1000秒平均分配,保持每天正好86400秒的外观。
 * <p>
 *  对于1972年11月03日之前的段,任意延伸到远处,共识国际时间尺度被定义为UT1,其在视觉上应用,其等于初始子午线(格林威治)的(平均)太阳时间。
 * 在这个段中,Java时间标度与共识国际时间标度相同。两个段之间的确切边界是UT1 = 1972-11-03T00：00和1972-11-04T12：00之间的UTC的时刻。
 * <p>
 * 使用JSR-310 API的Java时间尺度的实现不需要提供任何秒精确的时间,或者单调或平滑地进行的任何时钟。因此,实现不需要实际执行UTC-SLS回转或以其他方式知道闰秒。
 * 然而,JSR-310要求实现必须记录在定义表示当前时刻的时钟时使用的方法。有关可用时钟的详细信息,请参阅{@link Clock}。
 * <p>
 *  Java时间标度用于所有日期时间类。
 * 这包括{@code Instant},{@code LocalDate},{@code LocalTime},{@code OffsetDateTime},{@code ZonedDateTime}和{@code Duration}
 * 。
 *  Java时间标度用于所有日期时间类。
 * 
 * <p>
 *  这是<a href="{@docRoot}/java/lang/doc-files/ValueBased.html">以价值为基础的</a>类;在{@code Instant}实例上使用身份敏感操作(
 * 包括引用相等({@code ==}),身份哈希码或同步)可能会产生不可预测的结果,应该避免使用。
 * 应该使用{@code equals}方法进行比较。
 * 
 *  @implSpec这个类是不可变的和线程安全的。
 * 
 * 
 * @since 1.8
 */
public final class Instant
        implements Temporal, TemporalAdjuster, Comparable<Instant>, Serializable {

    /**
     * Constant for the 1970-01-01T00:00:00Z epoch instant.
     * <p>
     *  常数为1970-01-01T00：00：00Z时期。
     * 
     */
    public static final Instant EPOCH = new Instant(0, 0);
    /**
     * The minimum supported epoch second.
     * <p>
     *  最小支持的时期秒。
     * 
     */
    private static final long MIN_SECOND = -31557014167219200L;
    /**
     * The maximum supported epoch second.
     * <p>
     *  最大支持的时期秒。
     * 
     */
    private static final long MAX_SECOND = 31556889864403199L;
    /**
     * The minimum supported {@code Instant}, '-1000000000-01-01T00:00Z'.
     * This could be used by an application as a "far past" instant.
     * <p>
     * This is one year earlier than the minimum {@code LocalDateTime}.
     * This provides sufficient values to handle the range of {@code ZoneOffset}
     * which affect the instant in addition to the local date-time.
     * The value is also chosen such that the value of the year fits in
     * an {@code int}.
     * <p>
     *  最低支持{@code Instant},'-1000000000-01-01T00：00Z'。这可以由应用程序用作"远过去"时刻。
     * <p>
     * 这比最小{@code LocalDateTime}早一年。这提供了足够的值来处理影响除了本地日期时间之外的时刻的{@code ZoneOffset}的范围。
     * 该值也被选择为使得年的值适合{@code int}。
     * 
     */
    public static final Instant MIN = Instant.ofEpochSecond(MIN_SECOND, 0);
    /**
     * The maximum supported {@code Instant}, '1000000000-12-31T23:59:59.999999999Z'.
     * This could be used by an application as a "far future" instant.
     * <p>
     * This is one year later than the maximum {@code LocalDateTime}.
     * This provides sufficient values to handle the range of {@code ZoneOffset}
     * which affect the instant in addition to the local date-time.
     * The value is also chosen such that the value of the year fits in
     * an {@code int}.
     * <p>
     *  支持的最大{@code Instant},'1000000000-12-31T23：59：59.999999999Z'。这可以被应用程序用作"远期"时刻。
     * <p>
     *  这比最大{@code LocalDateTime}晚一年。这提供了足够的值来处理影响除了本地日期时间之外的时刻的{@code ZoneOffset}的范围。
     * 该值也被选择为使得年的值适合{@code int}。
     * 
     */
    public static final Instant MAX = Instant.ofEpochSecond(MAX_SECOND, 999_999_999);

    /**
     * Serialization version.
     * <p>
     *  序列化版本。
     * 
     */
    private static final long serialVersionUID = -665713676816604388L;

    /**
     * The number of seconds from the epoch of 1970-01-01T00:00:00Z.
     * <p>
     *  从1970-01-01T00：00：00Z的时期的秒数。
     * 
     */
    private final long seconds;
    /**
     * The number of nanoseconds, later along the time-line, from the seconds field.
     * This is always positive, and never exceeds 999,999,999.
     * <p>
     *  纳秒数,后面的时间线,从秒字段。这总是积极的,从不超过999,999,999。
     * 
     */
    private final int nanos;

    //-----------------------------------------------------------------------
    /**
     * Obtains the current instant from the system clock.
     * <p>
     * This will query the {@link Clock#systemUTC() system UTC clock} to
     * obtain the current instant.
     * <p>
     * Using this method will prevent the ability to use an alternate time-source for
     * testing because the clock is effectively hard-coded.
     *
     * <p>
     *  从系统时钟获取当前时刻。
     * <p>
     *  这将查询{@link Clock#systemUTC()系统UTC时钟}以获取当前时刻。
     * <p>
     *  使用此方法将会阻止使用备用时间源进行测试,因为时钟是有效的硬编码。
     * 
     * 
     * @return the current instant using the system clock, not null
     */
    public static Instant now() {
        return Clock.systemUTC().instant();
    }

    /**
     * Obtains the current instant from the specified clock.
     * <p>
     * This will query the specified clock to obtain the current time.
     * <p>
     * Using this method allows the use of an alternate clock for testing.
     * The alternate clock may be introduced using {@link Clock dependency injection}.
     *
     * <p>
     *  从指定的时钟获取当前时刻。
     * <p>
     *  这将查询指定的时钟以获取当前时间。
     * <p>
     *  使用此方法允许使用替代时钟进行测试。可以使用{@link时钟依赖注入}来引入替代时钟。
     * 
     * 
     * @param clock  the clock to use, not null
     * @return the current instant, not null
     */
    public static Instant now(Clock clock) {
        Objects.requireNonNull(clock, "clock");
        return clock.instant();
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code Instant} using seconds from the
     * epoch of 1970-01-01T00:00:00Z.
     * <p>
     * The nanosecond field is set to zero.
     *
     * <p>
     * 从1970-01-01T00：00：00Z的时代使用秒来获取{@code Instant}的实例。
     * <p>
     *  纳秒字段设置为零。
     * 
     * 
     * @param epochSecond  the number of seconds from 1970-01-01T00:00:00Z
     * @return an instant, not null
     * @throws DateTimeException if the instant exceeds the maximum or minimum instant
     */
    public static Instant ofEpochSecond(long epochSecond) {
        return create(epochSecond, 0);
    }

    /**
     * Obtains an instance of {@code Instant} using seconds from the
     * epoch of 1970-01-01T00:00:00Z and nanosecond fraction of second.
     * <p>
     * This method allows an arbitrary number of nanoseconds to be passed in.
     * The factory will alter the values of the second and nanosecond in order
     * to ensure that the stored nanosecond is in the range 0 to 999,999,999.
     * For example, the following will result in the exactly the same instant:
     * <pre>
     *  Instant.ofEpochSecond(3, 1);
     *  Instant.ofEpochSecond(4, -999_999_999);
     *  Instant.ofEpochSecond(2, 1000_000_001);
     * </pre>
     *
     * <p>
     *  从1970-01-01T00：00：00Z的时代和秒的纳秒分数获取{@code Instant}的实例。
     * <p>
     *  此方法允许传入任意数量的纳秒。工厂将改变第二和纳秒的值,以确保存储的纳秒在0至999,999,999的范围内。例如,以下将导致完全相同的时刻：
     * <pre>
     *  Instant.ofEpochSecond(3,1); Instant.ofEpochSecond(4,-999_999_999); Instant.ofEpochSecond(2,1000_000_
     * 001);。
     * </pre>
     * 
     * 
     * @param epochSecond  the number of seconds from 1970-01-01T00:00:00Z
     * @param nanoAdjustment  the nanosecond adjustment to the number of seconds, positive or negative
     * @return an instant, not null
     * @throws DateTimeException if the instant exceeds the maximum or minimum instant
     * @throws ArithmeticException if numeric overflow occurs
     */
    public static Instant ofEpochSecond(long epochSecond, long nanoAdjustment) {
        long secs = Math.addExact(epochSecond, Math.floorDiv(nanoAdjustment, NANOS_PER_SECOND));
        int nos = (int)Math.floorMod(nanoAdjustment, NANOS_PER_SECOND);
        return create(secs, nos);
    }

    /**
     * Obtains an instance of {@code Instant} using milliseconds from the
     * epoch of 1970-01-01T00:00:00Z.
     * <p>
     * The seconds and nanoseconds are extracted from the specified milliseconds.
     *
     * <p>
     *  从1970-01-01T00：00：00Z的时代使用毫秒获取{@code Instant}的实例。
     * <p>
     *  秒和纳秒是从指定的毫秒提取的。
     * 
     * 
     * @param epochMilli  the number of milliseconds from 1970-01-01T00:00:00Z
     * @return an instant, not null
     * @throws DateTimeException if the instant exceeds the maximum or minimum instant
     */
    public static Instant ofEpochMilli(long epochMilli) {
        long secs = Math.floorDiv(epochMilli, 1000);
        int mos = (int)Math.floorMod(epochMilli, 1000);
        return create(secs, mos * 1000_000);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code Instant} from a temporal object.
     * <p>
     * This obtains an instant based on the specified temporal.
     * A {@code TemporalAccessor} represents an arbitrary set of date and time information,
     * which this factory converts to an instance of {@code Instant}.
     * <p>
     * The conversion extracts the {@link ChronoField#INSTANT_SECONDS INSTANT_SECONDS}
     * and {@link ChronoField#NANO_OF_SECOND NANO_OF_SECOND} fields.
     * <p>
     * This method matches the signature of the functional interface {@link TemporalQuery}
     * allowing it to be used as a query via method reference, {@code Instant::from}.
     *
     * <p>
     *  从临时对象获取{@code Instant}的实例。
     * <p>
     *  这基于指定的时间获得时刻。 {@code TemporalAccessor}表示一组任意的日期和时间信息,此工厂会将其转换为{@code Instant}的实例。
     * <p>
     *  转换将提取{@link ChronoField#INSTANT_SECONDS INSTANT_SECONDS}和{@link ChronoField#NANO_OF_SECOND NANO_OF_SECOND}
     * 字段。
     * <p>
     *  此方法匹配功能接口{@link TemporalQuery}的签名,允许它通过方法引用{@code Instant :: from}用作查询。
     * 
     * 
     * @param temporal  the temporal object to convert, not null
     * @return the instant, not null
     * @throws DateTimeException if unable to convert to an {@code Instant}
     */
    public static Instant from(TemporalAccessor temporal) {
        if (temporal instanceof Instant) {
            return (Instant) temporal;
        }
        Objects.requireNonNull(temporal, "temporal");
        try {
            long instantSecs = temporal.getLong(INSTANT_SECONDS);
            int nanoOfSecond = temporal.get(NANO_OF_SECOND);
            return Instant.ofEpochSecond(instantSecs, nanoOfSecond);
        } catch (DateTimeException ex) {
            throw new DateTimeException("Unable to obtain Instant from TemporalAccessor: " +
                    temporal + " of type " + temporal.getClass().getName(), ex);
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code Instant} from a text string such as
     * {@code 2007-12-03T10:15:30.00Z}.
     * <p>
     * The string must represent a valid instant in UTC and is parsed using
     * {@link DateTimeFormatter#ISO_INSTANT}.
     *
     * <p>
     * 从文本字符串(例如{@code 2007-12-03T10：15：30.00Z})获取{@code Instant}的实例。
     * <p>
     *  该字符串必须表示一个有效的UTC时间,并使用{@link DateTimeFormatter#ISO_INSTANT}进行解析。
     * 
     * 
     * @param text  the text to parse, not null
     * @return the parsed instant, not null
     * @throws DateTimeParseException if the text cannot be parsed
     */
    public static Instant parse(final CharSequence text) {
        return DateTimeFormatter.ISO_INSTANT.parse(text, Instant::from);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code Instant} using seconds and nanoseconds.
     *
     * <p>
     *  使用秒和纳秒获取{@code Instant}的实例。
     * 
     * 
     * @param seconds  the length of the duration in seconds
     * @param nanoOfSecond  the nano-of-second, from 0 to 999,999,999
     * @throws DateTimeException if the instant exceeds the maximum or minimum instant
     */
    private static Instant create(long seconds, int nanoOfSecond) {
        if ((seconds | nanoOfSecond) == 0) {
            return EPOCH;
        }
        if (seconds < MIN_SECOND || seconds > MAX_SECOND) {
            throw new DateTimeException("Instant exceeds minimum or maximum instant");
        }
        return new Instant(seconds, nanoOfSecond);
    }

    /**
     * Constructs an instance of {@code Instant} using seconds from the epoch of
     * 1970-01-01T00:00:00Z and nanosecond fraction of second.
     *
     * <p>
     *  使用1970-01-01T00：00：00Z和第二个纳秒级的秒构造{@code Instant}的实例。
     * 
     * 
     * @param epochSecond  the number of seconds from 1970-01-01T00:00:00Z
     * @param nanos  the nanoseconds within the second, must be positive
     */
    private Instant(long epochSecond, int nanos) {
        super();
        this.seconds = epochSecond;
        this.nanos = nanos;
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if the specified field is supported.
     * <p>
     * This checks if this instant can be queried for the specified field.
     * If false, then calling the {@link #range(TemporalField) range},
     * {@link #get(TemporalField) get} and {@link #with(TemporalField, long)}
     * methods will throw an exception.
     * <p>
     * If the field is a {@link ChronoField} then the query is implemented here.
     * The supported fields are:
     * <ul>
     * <li>{@code NANO_OF_SECOND}
     * <li>{@code MICRO_OF_SECOND}
     * <li>{@code MILLI_OF_SECOND}
     * <li>{@code INSTANT_SECONDS}
     * </ul>
     * All other {@code ChronoField} instances will return false.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.isSupportedBy(TemporalAccessor)}
     * passing {@code this} as the argument.
     * Whether the field is supported is determined by the field.
     *
     * <p>
     *  检查是否支持指定的字段。
     * <p>
     *  这将检查是否可以查询指定字段的此即时。
     * 如果为false,则调用{@link #range(TemporalField)范围},{@link #get(TemporalField)get}和{@link #with(TemporalField,long)}
     * 方法将抛出异常。
     *  这将检查是否可以查询指定字段的此即时。
     * <p>
     *  如果字段是{@link ChronoField},则在此执行查询。支持的字段包括：
     * <ul>
     *  <li> {@ code NANO_OF_SECOND} <li> {@ code MICRO_OF_SECOND} <li> {@ code MILLI_OF_SECOND} <li> {@ code INSTANT_SECONDS}
     * 。
     * </ul>
     *  所有其他{@code ChronoField}实例将返回false。
     * <p>
     *  如果字段不是{@code ChronoField},那么通过调用{@code TemporalField.isSupportedBy(TemporalAccessor)}传递{@code this}作
     * 为参数来获得此方法的结果。
     * 字段是否受支持由字段确定。
     * 
     * 
     * @param field  the field to check, null returns false
     * @return true if the field is supported on this instant, false if not
     */
    @Override
    public boolean isSupported(TemporalField field) {
        if (field instanceof ChronoField) {
            return field == INSTANT_SECONDS || field == NANO_OF_SECOND || field == MICRO_OF_SECOND || field == MILLI_OF_SECOND;
        }
        return field != null && field.isSupportedBy(this);
    }

    /**
     * Checks if the specified unit is supported.
     * <p>
     * This checks if the specified unit can be added to, or subtracted from, this date-time.
     * If false, then calling the {@link #plus(long, TemporalUnit)} and
     * {@link #minus(long, TemporalUnit) minus} methods will throw an exception.
     * <p>
     * If the unit is a {@link ChronoUnit} then the query is implemented here.
     * The supported units are:
     * <ul>
     * <li>{@code NANOS}
     * <li>{@code MICROS}
     * <li>{@code MILLIS}
     * <li>{@code SECONDS}
     * <li>{@code MINUTES}
     * <li>{@code HOURS}
     * <li>{@code HALF_DAYS}
     * <li>{@code DAYS}
     * </ul>
     * All other {@code ChronoUnit} instances will return false.
     * <p>
     * If the unit is not a {@code ChronoUnit}, then the result of this method
     * is obtained by invoking {@code TemporalUnit.isSupportedBy(Temporal)}
     * passing {@code this} as the argument.
     * Whether the unit is supported is determined by the unit.
     *
     * <p>
     *  检查是否支持指定的单元。
     * <p>
     *  这将检查指定的单位是否可以添加到此日期时间或从此日期时间中减去。
     * 如果为false,则调用{@link #plus(long,TemporalUnit)}和{@link #minus(long,TemporalUnit)minus}方法将抛出异常。
     * <p>
     * 如果单位是{@link ChronoUnit},则在此执行查询。支持的单位有：
     * <ul>
     *  <li> {@ code MINUTES} <li> {@ code MICROS} <li> {@ code MILLIS} <li> {@ code SECONDS} <li> {@ code MINUTES}
     *  > {@ code HALF_DAYS} <li> {@ code DAYS}。
     * </ul>
     *  所有其他{@code ChronoUnit}实例将返回false。
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
    public boolean isSupported(TemporalUnit unit) {
        if (unit instanceof ChronoUnit) {
            return unit.isTimeBased() || unit == DAYS;
        }
        return unit != null && unit.isSupportedBy(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the range of valid values for the specified field.
     * <p>
     * The range object expresses the minimum and maximum valid values for a field.
     * This instant is used to enhance the accuracy of the returned range.
     * If it is not possible to return the range, because the field is not supported
     * or for some other reason, an exception is thrown.
     * <p>
     * If the field is a {@link ChronoField} then the query is implemented here.
     * The {@link #isSupported(TemporalField) supported fields} will return
     * appropriate range instances.
     * All other {@code ChronoField} instances will throw an {@code UnsupportedTemporalTypeException}.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.rangeRefinedBy(TemporalAccessor)}
     * passing {@code this} as the argument.
     * Whether the range can be obtained is determined by the field.
     *
     * <p>
     *  获取指定字段的有效值范围。
     * <p>
     *  范围对象表示字段的最小和最大有效值。此时刻用于提高返回范围的精度。如果不可能返回范围,因为该字段不受支持或由于某种其他原因,将抛出异常。
     * <p>
     *  如果字段是{@link ChronoField},则在此执行查询。 {@link #isSupported(TemporalField)supported fields}会传回适当的范围执行个体。
     * 所有其他{@code ChronoField}实例将抛出{@code UnsupportedTemporalTypeException}。
     * <p>
     *  如果字段不是{@code ChronoField},那么通过调用{@code TemporalField.rangeRefinedBy(TemporalAccessor)}传递{@code this}
     * 作为参数来获得此方法的结果。
     * 是否可以获得范围由字段确定。
     * 
     * 
     * @param field  the field to query the range for, not null
     * @return the range of valid values for the field, not null
     * @throws DateTimeException if the range for the field cannot be obtained
     * @throws UnsupportedTemporalTypeException if the field is not supported
     */
    @Override  // override for Javadoc
    public ValueRange range(TemporalField field) {
        return Temporal.super.range(field);
    }

    /**
     * Gets the value of the specified field from this instant as an {@code int}.
     * <p>
     * This queries this instant for the value for the specified field.
     * The returned value will always be within the valid range of values for the field.
     * If it is not possible to return the value, because the field is not supported
     * or for some other reason, an exception is thrown.
     * <p>
     * If the field is a {@link ChronoField} then the query is implemented here.
     * The {@link #isSupported(TemporalField) supported fields} will return valid
     * values based on this date-time, except {@code INSTANT_SECONDS} which is too
     * large to fit in an {@code int} and throws a {@code DateTimeException}.
     * All other {@code ChronoField} instances will throw an {@code UnsupportedTemporalTypeException}.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.getFrom(TemporalAccessor)}
     * passing {@code this} as the argument. Whether the value can be obtained,
     * and what the value represents, is determined by the field.
     *
     * <p>
     *  从此时刻获取指定字段的值为{@code int}。
     * <p>
     * 这将立即查询指定字段的值。返回的值将始终在字段的有效值范围内。如果不可能返回值,因为该字段不受支持或由于某种其他原因,将抛出异常。
     * <p>
     *  如果字段是{@link ChronoField},则在此执行查询。
     * 根据此日期时间,{@link #isSupported(TemporalField)支持的字段}将返回有效的值,但{@code INSTANT_SECONDS}太大,无法适应{@code int}并引发
     * {@code DateTimeException} 。
     *  如果字段是{@link ChronoField},则在此执行查询。
     * 所有其他{@code ChronoField}实例将抛出{@code UnsupportedTemporalTypeException}。
     * <p>
     *  如果字段不是{@code ChronoField},那么通过调用{@code TemporalField.getFrom(TemporalAccessor)}传递{@code this}作为参数来获得
     * 此方法的结果。
     * 是否可以获取该值以及该值表示什么,由字段确定。
     * 
     * 
     * @param field  the field to get, not null
     * @return the value for the field
     * @throws DateTimeException if a value for the field cannot be obtained or
     *         the value is outside the range of valid values for the field
     * @throws UnsupportedTemporalTypeException if the field is not supported or
     *         the range of values exceeds an {@code int}
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override  // override for Javadoc and performance
    public int get(TemporalField field) {
        if (field instanceof ChronoField) {
            switch ((ChronoField) field) {
                case NANO_OF_SECOND: return nanos;
                case MICRO_OF_SECOND: return nanos / 1000;
                case MILLI_OF_SECOND: return nanos / 1000_000;
                case INSTANT_SECONDS: INSTANT_SECONDS.checkValidIntValue(seconds);
            }
            throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
        }
        return range(field).checkValidIntValue(field.getFrom(this), field);
    }

    /**
     * Gets the value of the specified field from this instant as a {@code long}.
     * <p>
     * This queries this instant for the value for the specified field.
     * If it is not possible to return the value, because the field is not supported
     * or for some other reason, an exception is thrown.
     * <p>
     * If the field is a {@link ChronoField} then the query is implemented here.
     * The {@link #isSupported(TemporalField) supported fields} will return valid
     * values based on this date-time.
     * All other {@code ChronoField} instances will throw an {@code UnsupportedTemporalTypeException}.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.getFrom(TemporalAccessor)}
     * passing {@code this} as the argument. Whether the value can be obtained,
     * and what the value represents, is determined by the field.
     *
     * <p>
     *  从此时钟获取指定字段的值为{@code long}。
     * <p>
     *  这将立即查询指定字段的值。如果不可能返回值,因为该字段不受支持或由于某种其他原因,将抛出异常。
     * <p>
     *  如果字段是{@link ChronoField},则在此执行查询。 {@link #isSupported(TemporalField)supported fields}将根据此日期时间返回有效值。
     * 所有其他{@code ChronoField}实例将抛出{@code UnsupportedTemporalTypeException}。
     * <p>
     * 如果字段不是{@code ChronoField},那么通过调用{@code TemporalField.getFrom(TemporalAccessor)}传递{@code this}作为参数来获得此
     * 方法的结果。
     * 是否可以获取该值以及该值表示什么,由字段确定。
     * 
     * 
     * @param field  the field to get, not null
     * @return the value for the field
     * @throws DateTimeException if a value for the field cannot be obtained
     * @throws UnsupportedTemporalTypeException if the field is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public long getLong(TemporalField field) {
        if (field instanceof ChronoField) {
            switch ((ChronoField) field) {
                case NANO_OF_SECOND: return nanos;
                case MICRO_OF_SECOND: return nanos / 1000;
                case MILLI_OF_SECOND: return nanos / 1000_000;
                case INSTANT_SECONDS: return seconds;
            }
            throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
        }
        return field.getFrom(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the number of seconds from the Java epoch of 1970-01-01T00:00:00Z.
     * <p>
     * The epoch second count is a simple incrementing count of seconds where
     * second 0 is 1970-01-01T00:00:00Z.
     * The nanosecond part of the day is returned by {@code getNanosOfSecond}.
     *
     * <p>
     *  获取从1970-01-01T00：00：00Z的Java纪元开始的秒数。
     * <p>
     *  时代秒计数是秒的简单递增计数,其中第二个0是1970-01-01T00：00：00Z。当天的纳秒部分由{@code getNanosOfSecond}返回。
     * 
     * 
     * @return the seconds from the epoch of 1970-01-01T00:00:00Z
     */
    public long getEpochSecond() {
        return seconds;
    }

    /**
     * Gets the number of nanoseconds, later along the time-line, from the start
     * of the second.
     * <p>
     * The nanosecond-of-second value measures the total number of nanoseconds from
     * the second returned by {@code getEpochSecond}.
     *
     * <p>
     *  获取纳秒的数量,后面沿着时间线,从第二个开始。
     * <p>
     *  纳秒秒测量从{@code getEpochSecond}返回的第二个纳秒的总数。
     * 
     * 
     * @return the nanoseconds within the second, always positive, never exceeds 999,999,999
     */
    public int getNano() {
        return nanos;
    }

    //-------------------------------------------------------------------------
    /**
     * Returns an adjusted copy of this instant.
     * <p>
     * This returns an {@code Instant}, based on this one, with the instant adjusted.
     * The adjustment takes place using the specified adjuster strategy object.
     * Read the documentation of the adjuster to understand what adjustment will be made.
     * <p>
     * The result of this method is obtained by invoking the
     * {@link TemporalAdjuster#adjustInto(Temporal)} method on the
     * specified adjuster passing {@code this} as the argument.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此即时的调整副本。
     * <p>
     *  这会返回一个{@code Instant},基于此,即时调整。使用指定的调整器策略对象进行调整。阅读调整器的文档以了解将要进行的调整。
     * <p>
     *  此方法的结果是通过调用指定调整器的{@link TemporalAdjuster#adjustInto(Temporal)}方法传递{@code this}作为参数来获得的。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param adjuster the adjuster to use, not null
     * @return an {@code Instant} based on {@code this} with the adjustment made, not null
     * @throws DateTimeException if the adjustment cannot be made
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public Instant with(TemporalAdjuster adjuster) {
        return (Instant) adjuster.adjustInto(this);
    }

    /**
     * Returns a copy of this instant with the specified field set to a new value.
     * <p>
     * This returns an {@code Instant}, based on this one, with the value
     * for the specified field changed.
     * If it is not possible to set the value, because the field is not supported or for
     * some other reason, an exception is thrown.
     * <p>
     * If the field is a {@link ChronoField} then the adjustment is implemented here.
     * The supported fields behave as follows:
     * <ul>
     * <li>{@code NANO_OF_SECOND} -
     *  Returns an {@code Instant} with the specified nano-of-second.
     *  The epoch-second will be unchanged.
     * <li>{@code MICRO_OF_SECOND} -
     *  Returns an {@code Instant} with the nano-of-second replaced by the specified
     *  micro-of-second multiplied by 1,000. The epoch-second will be unchanged.
     * <li>{@code MILLI_OF_SECOND} -
     *  Returns an {@code Instant} with the nano-of-second replaced by the specified
     *  milli-of-second multiplied by 1,000,000. The epoch-second will be unchanged.
     * <li>{@code INSTANT_SECONDS} -
     *  Returns an {@code Instant} with the specified epoch-second.
     *  The nano-of-second will be unchanged.
     * </ul>
     * <p>
     * In all cases, if the new value is outside the valid range of values for the field
     * then a {@code DateTimeException} will be thrown.
     * <p>
     * All other {@code ChronoField} instances will throw an {@code UnsupportedTemporalTypeException}.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.adjustInto(Temporal, long)}
     * passing {@code this} as the argument. In this case, the field determines
     * whether and how to adjust the instant.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此即时的副本,指定的字段设置为新值。
     * <p>
     * 这会返回一个{@code Instant},基于此,已更改指定字段的值。如果无法设置该值,因为该字段不受支持或由于某种其他原因,将抛出异常。
     * <p>
     *  如果字段是{@link ChronoField},那么在此处执行调整。支持的字段的行为如下：
     * <ul>
     *  <li> {@ code NANO_OF_SECOND}  - 返回指定纳秒的{@code Instant}。时代秒将不变。
     *  <li> {@ code MICRO_OF_SECOND}  - 返回一个{@code Instant},由指定的微秒乘以1000替换为纳秒。时代秒将不变。
     *  <li> {@ code MILLI_OF_SECOND}  - 返回一个{@code Instant},由指定的毫秒乘以1,000,000代替纳秒。时代秒将不变。
     *  <li> {@ code INSTANT_SECONDS}  - 返回具有指定纪元秒的{@code Instant}。纳秒是不变的。
     * </ul>
     * <p>
     *  在所有情况下,如果新值在字段的有效值范围之外,那么将抛出{@code DateTimeException}。
     * <p>
     *  所有其他{@code ChronoField}实例将抛出{@code UnsupportedTemporalTypeException}。
     * <p>
     *  如果字段不是{@code ChronoField},那么通过调用{@code TemporalField.adjustInto(Temporal,long)}传递{@code this}作为参数来获得
     * 此方法的结果。
     * 在这种情况下,字段确定是否以及如何调整时刻。
     * <p>
     * 此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param field  the field to set in the result, not null
     * @param newValue  the new value of the field in the result
     * @return an {@code Instant} based on {@code this} with the specified field set, not null
     * @throws DateTimeException if the field cannot be set
     * @throws UnsupportedTemporalTypeException if the field is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public Instant with(TemporalField field, long newValue) {
        if (field instanceof ChronoField) {
            ChronoField f = (ChronoField) field;
            f.checkValidValue(newValue);
            switch (f) {
                case MILLI_OF_SECOND: {
                    int nval = (int) newValue * 1000_000;
                    return (nval != nanos ? create(seconds, nval) : this);
                }
                case MICRO_OF_SECOND: {
                    int nval = (int) newValue * 1000;
                    return (nval != nanos ? create(seconds, nval) : this);
                }
                case NANO_OF_SECOND: return (newValue != nanos ? create(seconds, (int) newValue) : this);
                case INSTANT_SECONDS: return (newValue != seconds ? create(newValue, nanos) : this);
            }
            throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
        }
        return field.adjustInto(this, newValue);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this {@code Instant} truncated to the specified unit.
     * <p>
     * Truncating the instant returns a copy of the original with fields
     * smaller than the specified unit set to zero.
     * The fields are calculated on the basis of using a UTC offset as seen
     * in {@code toString}.
     * For example, truncating with the {@link ChronoUnit#MINUTES MINUTES} unit will
     * round down to the nearest minute, setting the seconds and nanoseconds to zero.
     * <p>
     * The unit must have a {@linkplain TemporalUnit#getDuration() duration}
     * that divides into the length of a standard day without remainder.
     * This includes all supplied time units on {@link ChronoUnit} and
     * {@link ChronoUnit#DAYS DAYS}. Other units throw an exception.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code Instant}的副本,截断到指定的单位。
     * <p>
     *  截断瞬时将返回字段小于指定单位设置为零的原始副本。这些字段是基于使用UTC偏移计算的,如{@code toString}中所示。
     * 例如,使用{@link ChronoUnit#MINUTES MINUTES}单位截断将向下舍入到最近的分钟,将秒和纳秒设置为零。
     * <p>
     *  单位必须有一个{@linkplain TemporalUnit#getDuration()duration},它分成标准日的长度,没有余数。
     * 这包括{@link ChronoUnit}和{@link ChronoUnit#DAYS DAYS}上提供的所有时间单位。其他单位抛出异常。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param unit  the unit to truncate to, not null
     * @return an {@code Instant} based on this instant with the time truncated, not null
     * @throws DateTimeException if the unit is invalid for truncation
     * @throws UnsupportedTemporalTypeException if the unit is not supported
     */
    public Instant truncatedTo(TemporalUnit unit) {
        if (unit == ChronoUnit.NANOS) {
            return this;
        }
        Duration unitDur = unit.getDuration();
        if (unitDur.getSeconds() > LocalTime.SECONDS_PER_DAY) {
            throw new UnsupportedTemporalTypeException("Unit is too large to be used for truncation");
        }
        long dur = unitDur.toNanos();
        if ((LocalTime.NANOS_PER_DAY % dur) != 0) {
            throw new UnsupportedTemporalTypeException("Unit must divide into a standard day without remainder");
        }
        long nod = (seconds % LocalTime.SECONDS_PER_DAY) * LocalTime.NANOS_PER_SECOND + nanos;
        long result = (nod / dur) * dur;
        return plusNanos(result - nod);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this instant with the specified amount added.
     * <p>
     * This returns an {@code Instant}, based on this one, with the specified amount added.
     * The amount is typically {@link Duration} but may be any other type implementing
     * the {@link TemporalAmount} interface.
     * <p>
     * The calculation is delegated to the amount object by calling
     * {@link TemporalAmount#addTo(Temporal)}. The amount implementation is free
     * to implement the addition in any way it wishes, however it typically
     * calls back to {@link #plus(long, TemporalUnit)}. Consult the documentation
     * of the amount implementation to determine if it can be successfully added.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此即时的副本,并添加指定的金额。
     * <p>
     *  这会根据这个值返回一个{@code Instant},并添加指定的金额。金额通常为{@link Duration},但可以是实现{@link TemporalAmount}界面的任何其他类型。
     * <p>
     *  通过调用{@link TemporalAmount#addTo(Temporal)}将计算委托给金额对象。
     * 金额实现可以以任何希望的方式实现添加,但是它通常回调{@link #plus(long,TemporalUnit)}。请参阅金额实施的文档以确定是否可以成功添加。
     * <p>
     * 此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param amountToAdd  the amount to add, not null
     * @return an {@code Instant} based on this instant with the addition made, not null
     * @throws DateTimeException if the addition cannot be made
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public Instant plus(TemporalAmount amountToAdd) {
        return (Instant) amountToAdd.addTo(this);
    }

    /**
     * Returns a copy of this instant with the specified amount added.
     * <p>
     * This returns an {@code Instant}, based on this one, with the amount
     * in terms of the unit added. If it is not possible to add the amount, because the
     * unit is not supported or for some other reason, an exception is thrown.
     * <p>
     * If the field is a {@link ChronoUnit} then the addition is implemented here.
     * The supported fields behave as follows:
     * <ul>
     * <li>{@code NANOS} -
     *  Returns a {@code Instant} with the specified number of nanoseconds added.
     *  This is equivalent to {@link #plusNanos(long)}.
     * <li>{@code MICROS} -
     *  Returns a {@code Instant} with the specified number of microseconds added.
     *  This is equivalent to {@link #plusNanos(long)} with the amount
     *  multiplied by 1,000.
     * <li>{@code MILLIS} -
     *  Returns a {@code Instant} with the specified number of milliseconds added.
     *  This is equivalent to {@link #plusNanos(long)} with the amount
     *  multiplied by 1,000,000.
     * <li>{@code SECONDS} -
     *  Returns a {@code Instant} with the specified number of seconds added.
     *  This is equivalent to {@link #plusSeconds(long)}.
     * <li>{@code MINUTES} -
     *  Returns a {@code Instant} with the specified number of minutes added.
     *  This is equivalent to {@link #plusSeconds(long)} with the amount
     *  multiplied by 60.
     * <li>{@code HOURS} -
     *  Returns a {@code Instant} with the specified number of hours added.
     *  This is equivalent to {@link #plusSeconds(long)} with the amount
     *  multiplied by 3,600.
     * <li>{@code HALF_DAYS} -
     *  Returns a {@code Instant} with the specified number of half-days added.
     *  This is equivalent to {@link #plusSeconds(long)} with the amount
     *  multiplied by 43,200 (12 hours).
     * <li>{@code DAYS} -
     *  Returns a {@code Instant} with the specified number of days added.
     *  This is equivalent to {@link #plusSeconds(long)} with the amount
     *  multiplied by 86,400 (24 hours).
     * </ul>
     * <p>
     * All other {@code ChronoUnit} instances will throw an {@code UnsupportedTemporalTypeException}.
     * <p>
     * If the field is not a {@code ChronoUnit}, then the result of this method
     * is obtained by invoking {@code TemporalUnit.addTo(Temporal, long)}
     * passing {@code this} as the argument. In this case, the unit determines
     * whether and how to perform the addition.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此即时的副本,并添加指定的金额。
     * <p>
     *  这会根据这个值返回一个{@code Instant},其中的金额按照添加的单位而定。如果无法添加该金额,因为该单元不受支持或出于其他原因,将抛出异常。
     * <p>
     *  如果字段是{@link ChronoUnit},则在此处实现添加。支持的字段的行为如下：
     * <ul>
     * <li> {@ code NANOS}  - 返回添加了指定的纳秒数的{@code Instant}。这相当于{@link #plusNanos(long)}。
     *  <li> {@ code MICROS}  - 返回添加了指定的微秒数的{@code Instant}。这相当于{@link #plusNanos(long)},金额乘以1,000。
     *  <li> {@ code MILLIS}  - 返回添加了指定的毫秒数的{@code Instant}。这相当于{@link #plusNanos(long)},金额乘以1,000,000。
     *  <li> {@ code SECONDS}  - 返回添加了指定秒数的{@code Instant}。这相当于{@link #plusSeconds(long)}。
     *  <li> {@ code MINUTES}  - 返回添加了指定分钟数的{@code Instant}。
     * 这等于{@link #plusSeconds(long)},金额乘以60. <li> {@ code HOURS}  - 返回添加了指定小时数的{@code Instant}。
     * 这相当于{@link #plusSeconds(long)},金额乘以3,600。 <li> {@ code HALF_DAYS}  - 返回添加了指定天数的{@code Instant}。
     * 这相当于{@link #plusSeconds(long)},金额乘以43.00(12小时)。 <li> {@ code DAYS}  - 返回添加了指定天数的{@code Instant}。
     * 这相当于{@link #plusSeconds(long)},金额乘以86,400(24小时)。
     * </ul>
     * <p>
     *  所有其他{@code ChronoUnit}实例将抛出{@code UnsupportedTemporalTypeException}。
     * <p>
     * 如果字段不是{@code ChronoUnit},那么通过调用{@code TemporalUnit.addTo(Temporal,long)}传递{@code this}作为参数来获得此方法的结果。
     * 在这种情况下,单元确定是否以及如何执行添加。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param amountToAdd  the amount of the unit to add to the result, may be negative
     * @param unit  the unit of the amount to add, not null
     * @return an {@code Instant} based on this instant with the specified amount added, not null
     * @throws DateTimeException if the addition cannot be made
     * @throws UnsupportedTemporalTypeException if the unit is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public Instant plus(long amountToAdd, TemporalUnit unit) {
        if (unit instanceof ChronoUnit) {
            switch ((ChronoUnit) unit) {
                case NANOS: return plusNanos(amountToAdd);
                case MICROS: return plus(amountToAdd / 1000_000, (amountToAdd % 1000_000) * 1000);
                case MILLIS: return plusMillis(amountToAdd);
                case SECONDS: return plusSeconds(amountToAdd);
                case MINUTES: return plusSeconds(Math.multiplyExact(amountToAdd, SECONDS_PER_MINUTE));
                case HOURS: return plusSeconds(Math.multiplyExact(amountToAdd, SECONDS_PER_HOUR));
                case HALF_DAYS: return plusSeconds(Math.multiplyExact(amountToAdd, SECONDS_PER_DAY / 2));
                case DAYS: return plusSeconds(Math.multiplyExact(amountToAdd, SECONDS_PER_DAY));
            }
            throw new UnsupportedTemporalTypeException("Unsupported unit: " + unit);
        }
        return unit.addTo(this, amountToAdd);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this instant with the specified duration in seconds added.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此时间的副本,指定的持续时间(以秒为单位)。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param secondsToAdd  the seconds to add, positive or negative
     * @return an {@code Instant} based on this instant with the specified seconds added, not null
     * @throws DateTimeException if the result exceeds the maximum or minimum instant
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Instant plusSeconds(long secondsToAdd) {
        return plus(secondsToAdd, 0);
    }

    /**
     * Returns a copy of this instant with the specified duration in milliseconds added.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此时间的副本,指定的持续时间(以毫秒为单位)。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param millisToAdd  the milliseconds to add, positive or negative
     * @return an {@code Instant} based on this instant with the specified milliseconds added, not null
     * @throws DateTimeException if the result exceeds the maximum or minimum instant
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Instant plusMillis(long millisToAdd) {
        return plus(millisToAdd / 1000, (millisToAdd % 1000) * 1000_000);
    }

    /**
     * Returns a copy of this instant with the specified duration in nanoseconds added.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此时间的副本,指定的持续时间(以纳秒为单位)。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param nanosToAdd  the nanoseconds to add, positive or negative
     * @return an {@code Instant} based on this instant with the specified nanoseconds added, not null
     * @throws DateTimeException if the result exceeds the maximum or minimum instant
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Instant plusNanos(long nanosToAdd) {
        return plus(0, nanosToAdd);
    }

    /**
     * Returns a copy of this instant with the specified duration added.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此时间的副本,并添加指定的持续时间。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param secondsToAdd  the seconds to add, positive or negative
     * @param nanosToAdd  the nanos to add, positive or negative
     * @return an {@code Instant} based on this instant with the specified seconds added, not null
     * @throws DateTimeException if the result exceeds the maximum or minimum instant
     * @throws ArithmeticException if numeric overflow occurs
     */
    private Instant plus(long secondsToAdd, long nanosToAdd) {
        if ((secondsToAdd | nanosToAdd) == 0) {
            return this;
        }
        long epochSec = Math.addExact(seconds, secondsToAdd);
        epochSec = Math.addExact(epochSec, nanosToAdd / NANOS_PER_SECOND);
        nanosToAdd = nanosToAdd % NANOS_PER_SECOND;
        long nanoAdjustment = nanos + nanosToAdd;  // safe int+NANOS_PER_SECOND
        return ofEpochSecond(epochSec, nanoAdjustment);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this instant with the specified amount subtracted.
     * <p>
     * This returns an {@code Instant}, based on this one, with the specified amount subtracted.
     * The amount is typically {@link Duration} but may be any other type implementing
     * the {@link TemporalAmount} interface.
     * <p>
     * The calculation is delegated to the amount object by calling
     * {@link TemporalAmount#subtractFrom(Temporal)}. The amount implementation is free
     * to implement the subtraction in any way it wishes, however it typically
     * calls back to {@link #minus(long, TemporalUnit)}. Consult the documentation
     * of the amount implementation to determine if it can be successfully subtracted.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此指定减去的时间的副本。
     * <p>
     *  系统会根据这个值返回{@code Instant},并减去指定的金额。金额通常为{@link Duration},但可以是实现{@link TemporalAmount}界面的任何其他类型。
     * <p>
     * 通过调用{@link TemporalAmount#subtractFrom(Temporal)}将计算委托给金额对象。
     * 金额实现可以以任何方式自由实现减法,但它通常回调{@link #minus(long,TemporalUnit)}。请参阅金额实施的文档,以确定是否可以成功扣除。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param amountToSubtract  the amount to subtract, not null
     * @return an {@code Instant} based on this instant with the subtraction made, not null
     * @throws DateTimeException if the subtraction cannot be made
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public Instant minus(TemporalAmount amountToSubtract) {
        return (Instant) amountToSubtract.subtractFrom(this);
    }

    /**
     * Returns a copy of this instant with the specified amount subtracted.
     * <p>
     * This returns a {@code Instant}, based on this one, with the amount
     * in terms of the unit subtracted. If it is not possible to subtract the amount,
     * because the unit is not supported or for some other reason, an exception is thrown.
     * <p>
     * This method is equivalent to {@link #plus(long, TemporalUnit)} with the amount negated.
     * See that method for a full description of how addition, and thus subtraction, works.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此指定减去的时间的副本。
     * <p>
     *  系统会根据这个值返回{@code Instant},并以减去的单位为单位的金额。如果不可能减去金额,因为该单元不受支持或由于某种其他原因,将抛出异常。
     * <p>
     *  此方法等效于{@link #plus(long,TemporalUnit)},其值为negated。请参阅该方法,了解如何添加和减少的工作原理的完整描述。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param amountToSubtract  the amount of the unit to subtract from the result, may be negative
     * @param unit  the unit of the amount to subtract, not null
     * @return an {@code Instant} based on this instant with the specified amount subtracted, not null
     * @throws DateTimeException if the subtraction cannot be made
     * @throws UnsupportedTemporalTypeException if the unit is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public Instant minus(long amountToSubtract, TemporalUnit unit) {
        return (amountToSubtract == Long.MIN_VALUE ? plus(Long.MAX_VALUE, unit).plus(1, unit) : plus(-amountToSubtract, unit));
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this instant with the specified duration in seconds subtracted.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此时间的副本,指定的持续时间(以秒为单位)。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param secondsToSubtract  the seconds to subtract, positive or negative
     * @return an {@code Instant} based on this instant with the specified seconds subtracted, not null
     * @throws DateTimeException if the result exceeds the maximum or minimum instant
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Instant minusSeconds(long secondsToSubtract) {
        if (secondsToSubtract == Long.MIN_VALUE) {
            return plusSeconds(Long.MAX_VALUE).plusSeconds(1);
        }
        return plusSeconds(-secondsToSubtract);
    }

    /**
     * Returns a copy of this instant with the specified duration in milliseconds subtracted.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  以指定的持续时间(以毫秒减去)返回此即时的副本。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param millisToSubtract  the milliseconds to subtract, positive or negative
     * @return an {@code Instant} based on this instant with the specified milliseconds subtracted, not null
     * @throws DateTimeException if the result exceeds the maximum or minimum instant
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Instant minusMillis(long millisToSubtract) {
        if (millisToSubtract == Long.MIN_VALUE) {
            return plusMillis(Long.MAX_VALUE).plusMillis(1);
        }
        return plusMillis(-millisToSubtract);
    }

    /**
     * Returns a copy of this instant with the specified duration in nanoseconds subtracted.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此时间的副本,指定的持续时间(以纳秒减去)。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param nanosToSubtract  the nanoseconds to subtract, positive or negative
     * @return an {@code Instant} based on this instant with the specified nanoseconds subtracted, not null
     * @throws DateTimeException if the result exceeds the maximum or minimum instant
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Instant minusNanos(long nanosToSubtract) {
        if (nanosToSubtract == Long.MIN_VALUE) {
            return plusNanos(Long.MAX_VALUE).plusNanos(1);
        }
        return plusNanos(-nanosToSubtract);
    }

    //-------------------------------------------------------------------------
    /**
     * Queries this instant using the specified query.
     * <p>
     * This queries this instant using the specified query strategy object.
     * The {@code TemporalQuery} object defines the logic to be used to
     * obtain the result. Read the documentation of the query to understand
     * what the result of this method will be.
     * <p>
     * The result of this method is obtained by invoking the
     * {@link TemporalQuery#queryFrom(TemporalAccessor)} method on the
     * specified query passing {@code this} as the argument.
     *
     * <p>
     *  使用指定的查询查询此即时。
     * <p>
     * 这将使用指定的查询策略对象查询此即时。 {@code TemporalQuery}对象定义用于获取结果的逻辑。阅读查询的文档以了解此方法的结果。
     * <p>
     *  此方法的结果是通过对指定的查询调用{@link TemporalQuery#queryFrom(TemporalAccessor)}方法传递{@code this}作为参数来获得的。
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
    public <R> R query(TemporalQuery<R> query) {
        if (query == TemporalQueries.precision()) {
            return (R) NANOS;
        }
        // inline TemporalAccessor.super.query(query) as an optimization
        if (query == TemporalQueries.chronology() || query == TemporalQueries.zoneId() ||
                query == TemporalQueries.zone() || query == TemporalQueries.offset() ||
                query == TemporalQueries.localDate() || query == TemporalQueries.localTime()) {
            return null;
        }
        return query.queryFrom(this);
    }

    /**
     * Adjusts the specified temporal object to have this instant.
     * <p>
     * This returns a temporal object of the same observable type as the input
     * with the instant changed to be the same as this.
     * <p>
     * The adjustment is equivalent to using {@link Temporal#with(TemporalField, long)}
     * twice, passing {@link ChronoField#INSTANT_SECONDS} and
     * {@link ChronoField#NANO_OF_SECOND} as the fields.
     * <p>
     * In most cases, it is clearer to reverse the calling pattern by using
     * {@link Temporal#with(TemporalAdjuster)}:
     * <pre>
     *   // these two lines are equivalent, but the second approach is recommended
     *   temporal = thisInstant.adjustInto(temporal);
     *   temporal = temporal.with(thisInstant);
     * </pre>
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  调整指定的时间对象以拥有此即时。
     * <p>
     *  这返回一个与输入相同的observable类型的时间对象,即时改变为与此相同。
     * <p>
     *  该调整相当于使用{@link Temporal#with(TemporalField,long)}两次,传递{@link ChronoField#INSTANT_SECONDS}和{@link ChronoField#NANO_OF_SECOND}
     * 作为字段。
     * <p>
     *  在大多数情况下,通过使用{@link Temporal#with(TemporalAdjuster)}来反转呼叫模式是更清楚的：
     * <pre>
     *  //这两行是等价的,但第二种方法是推荐temporal = thisInstant.adjustInto(temporal); temporal = temporal.with(thisInstant
     * );。
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
    public Temporal adjustInto(Temporal temporal) {
        return temporal.with(INSTANT_SECONDS, seconds).with(NANO_OF_SECOND, nanos);
    }

    /**
     * Calculates the amount of time until another instant in terms of the specified unit.
     * <p>
     * This calculates the amount of time between two {@code Instant}
     * objects in terms of a single {@code TemporalUnit}.
     * The start and end points are {@code this} and the specified instant.
     * The result will be negative if the end is before the start.
     * The calculation returns a whole number, representing the number of
     * complete units between the two instants.
     * The {@code Temporal} passed to this method is converted to a
     * {@code Instant} using {@link #from(TemporalAccessor)}.
     * For example, the amount in days between two dates can be calculated
     * using {@code startInstant.until(endInstant, SECONDS)}.
     * <p>
     * There are two equivalent ways of using this method.
     * The first is to invoke this method.
     * The second is to use {@link TemporalUnit#between(Temporal, Temporal)}:
     * <pre>
     *   // these two lines are equivalent
     *   amount = start.until(end, SECONDS);
     *   amount = SECONDS.between(start, end);
     * </pre>
     * The choice should be made based on which makes the code more readable.
     * <p>
     * The calculation is implemented in this method for {@link ChronoUnit}.
     * The units {@code NANOS}, {@code MICROS}, {@code MILLIS}, {@code SECONDS},
     * {@code MINUTES}, {@code HOURS}, {@code HALF_DAYS} and {@code DAYS}
     * are supported. Other {@code ChronoUnit} values will throw an exception.
     * <p>
     * If the unit is not a {@code ChronoUnit}, then the result of this method
     * is obtained by invoking {@code TemporalUnit.between(Temporal, Temporal)}
     * passing {@code this} as the first argument and the converted input temporal
     * as the second argument.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  根据指定的单位计算直到另一个时刻的时间量。
     * <p>
     * 这会根据单个{@code TemporalUnit}计算两个{@code Instant}对象之间的时间量。开始和结束点是{@code this}和指定的时刻。如果结束在开始之前,结果将为负。
     * 计算返回一个整数,表示两个时刻之间的完整单位数。传递给此方法的{@code Temporal}将使用{@link #from(TemporalAccessor)}转换为{@code Instant}。
     * 例如,两个日期之间的天数可以使用{@code startInstant.until(endInstant,SECONDS)}计算。
     * <p>
     *  有两种等效的方法使用这种方法。第一个是调用这个方法。第二个是使用{@link TemporalUnit#between(Temporal,Temporal)}：
     * <pre>
     *  //这两行是等价的amount = start.until(end,SECONDS); amount = SECONDS.between(start,end);
     * </pre>
     *  应该基于哪个使得代码更可读的选择。
     * <p>
     *  该计算在{@link ChronoUnit}的此方法中实现。
     *  {@code NANOS},{@code MICROS},{@code MILLIS},{@code SECONDS},{@code MINUTES},{@code HOURS},{@code HALF_DAYS}
     * 和{@code DAYS}支持的。
     *  该计算在{@link ChronoUnit}的此方法中实现。其他{@code ChronoUnit}值会抛出异常。
     * <p>
     *  如果单元不是{@code ChronoUnit},那么通过调用{@code TemporalUnit.between(Temporal,Temporal)}传递{@code this}作为第一个参数和
     * 转换的输入时间为第二个参数。
     * <p>
     * 此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param endExclusive  the end date, exclusive, which is converted to an {@code Instant}, not null
     * @param unit  the unit to measure the amount in, not null
     * @return the amount of time between this instant and the end instant
     * @throws DateTimeException if the amount cannot be calculated, or the end
     *  temporal cannot be converted to an {@code Instant}
     * @throws UnsupportedTemporalTypeException if the unit is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public long until(Temporal endExclusive, TemporalUnit unit) {
        Instant end = Instant.from(endExclusive);
        if (unit instanceof ChronoUnit) {
            ChronoUnit f = (ChronoUnit) unit;
            switch (f) {
                case NANOS: return nanosUntil(end);
                case MICROS: return nanosUntil(end) / 1000;
                case MILLIS: return Math.subtractExact(end.toEpochMilli(), toEpochMilli());
                case SECONDS: return secondsUntil(end);
                case MINUTES: return secondsUntil(end) / SECONDS_PER_MINUTE;
                case HOURS: return secondsUntil(end) / SECONDS_PER_HOUR;
                case HALF_DAYS: return secondsUntil(end) / (12 * SECONDS_PER_HOUR);
                case DAYS: return secondsUntil(end) / (SECONDS_PER_DAY);
            }
            throw new UnsupportedTemporalTypeException("Unsupported unit: " + unit);
        }
        return unit.between(this, end);
    }

    private long nanosUntil(Instant end) {
        long secsDiff = Math.subtractExact(end.seconds, seconds);
        long totalNanos = Math.multiplyExact(secsDiff, NANOS_PER_SECOND);
        return Math.addExact(totalNanos, end.nanos - nanos);
    }

    private long secondsUntil(Instant end) {
        long secsDiff = Math.subtractExact(end.seconds, seconds);
        long nanosDiff = end.nanos - nanos;
        if (secsDiff > 0 && nanosDiff < 0) {
            secsDiff--;
        } else if (secsDiff < 0 && nanosDiff > 0) {
            secsDiff++;
        }
        return secsDiff;
    }

    //-----------------------------------------------------------------------
    /**
     * Combines this instant with an offset to create an {@code OffsetDateTime}.
     * <p>
     * This returns an {@code OffsetDateTime} formed from this instant at the
     * specified offset from UTC/Greenwich. An exception will be thrown if the
     * instant is too large to fit into an offset date-time.
     * <p>
     * This method is equivalent to
     * {@link OffsetDateTime#ofInstant(Instant, ZoneId) OffsetDateTime.ofInstant(this, offset)}.
     *
     * <p>
     *  将此时间与偏移组合,以创建{@code OffsetDateTime}。
     * <p>
     *  这将返回一个{@code OffsetDateTime}从UTC /格林威治指定的偏移量形成的这个时刻。如果瞬时太大而无法适应偏移日期时间,则会抛出异常。
     * <p>
     *  此方法等效于{@link OffsetDateTime#ofInstant(Instant,ZoneId)OffsetDateTime.ofInstant(this,offset)}。
     * 
     * 
     * @param offset  the offset to combine with, not null
     * @return the offset date-time formed from this instant and the specified offset, not null
     * @throws DateTimeException if the result exceeds the supported range
     */
    public OffsetDateTime atOffset(ZoneOffset offset) {
        return OffsetDateTime.ofInstant(this, offset);
    }

    /**
     * Combines this instant with a time-zone to create a {@code ZonedDateTime}.
     * <p>
     * This returns an {@code ZonedDateTime} formed from this instant at the
     * specified time-zone. An exception will be thrown if the instant is too
     * large to fit into a zoned date-time.
     * <p>
     * This method is equivalent to
     * {@link ZonedDateTime#ofInstant(Instant, ZoneId) ZonedDateTime.ofInstant(this, zone)}.
     *
     * <p>
     *  将此时间与时区相结合,创建{@code ZonedDateTime}。
     * <p>
     *  这将返回在指定时区从此时刻形成的{@code ZonedDateTime}。如果时间太长,无法适应分区日期时间,则会抛出异常。
     * <p>
     *  此方法等效于{@link ZonedDateTime#ofInstant(Instant,ZoneId)ZonedDateTime.ofInstant(this,zone)}。
     * 
     * 
     * @param zone  the zone to combine with, not null
     * @return the zoned date-time formed from this instant and the specified zone, not null
     * @throws DateTimeException if the result exceeds the supported range
     */
    public ZonedDateTime atZone(ZoneId zone) {
        return ZonedDateTime.ofInstant(this, zone);
    }

    //-----------------------------------------------------------------------
    /**
     * Converts this instant to the number of milliseconds from the epoch
     * of 1970-01-01T00:00:00Z.
     * <p>
     * If this instant represents a point on the time-line too far in the future
     * or past to fit in a {@code long} milliseconds, then an exception is thrown.
     * <p>
     * If this instant has greater than millisecond precision, then the conversion
     * will drop any excess precision information as though the amount in nanoseconds
     * was subject to integer division by one million.
     *
     * <p>
     *  将此时间转换为从1970-01-01T00：00：00Z的时期的毫秒数。
     * <p>
     *  如果此时刻表示时间线上太远或远远超过{@code long}毫秒的点,则抛出异常。
     * <p>
     *  如果此瞬时具有大于毫秒的精度,则转换将丢弃任何超额精度信息,如同以纳秒为单位的量经受整数除以一百万。
     * 
     * 
     * @return the number of milliseconds since the epoch of 1970-01-01T00:00:00Z
     * @throws ArithmeticException if numeric overflow occurs
     */
    public long toEpochMilli() {
        long millis = Math.multiplyExact(seconds, 1000);
        return millis + nanos / 1000_000;
    }

    //-----------------------------------------------------------------------
    /**
     * Compares this instant to the specified instant.
     * <p>
     * The comparison is based on the time-line position of the instants.
     * It is "consistent with equals", as defined by {@link Comparable}.
     *
     * <p>
     *  将此时间与指定的时间进行比较。
     * <p>
     *  比较基于时刻的时间线位置。它是"与等号一致",由{@link Comparable}定义。
     * 
     * 
     * @param otherInstant  the other instant to compare to, not null
     * @return the comparator value, negative if less, positive if greater
     * @throws NullPointerException if otherInstant is null
     */
    @Override
    public int compareTo(Instant otherInstant) {
        int cmp = Long.compare(seconds, otherInstant.seconds);
        if (cmp != 0) {
            return cmp;
        }
        return nanos - otherInstant.nanos;
    }

    /**
     * Checks if this instant is after the specified instant.
     * <p>
     * The comparison is based on the time-line position of the instants.
     *
     * <p>
     * 检查此时间是否在指定的时间之后。
     * <p>
     *  比较基于时刻的时间线位置。
     * 
     * 
     * @param otherInstant  the other instant to compare to, not null
     * @return true if this instant is after the specified instant
     * @throws NullPointerException if otherInstant is null
     */
    public boolean isAfter(Instant otherInstant) {
        return compareTo(otherInstant) > 0;
    }

    /**
     * Checks if this instant is before the specified instant.
     * <p>
     * The comparison is based on the time-line position of the instants.
     *
     * <p>
     *  检查此时间是否在指定的时间之前。
     * <p>
     *  比较基于时刻的时间线位置。
     * 
     * 
     * @param otherInstant  the other instant to compare to, not null
     * @return true if this instant is before the specified instant
     * @throws NullPointerException if otherInstant is null
     */
    public boolean isBefore(Instant otherInstant) {
        return compareTo(otherInstant) < 0;
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if this instant is equal to the specified instant.
     * <p>
     * The comparison is based on the time-line position of the instants.
     *
     * <p>
     *  检查此时间是否等于指定的时间。
     * <p>
     *  比较基于时刻的时间线位置。
     * 
     * 
     * @param otherInstant  the other instant, null returns false
     * @return true if the other instant is equal to this one
     */
    @Override
    public boolean equals(Object otherInstant) {
        if (this == otherInstant) {
            return true;
        }
        if (otherInstant instanceof Instant) {
            Instant other = (Instant) otherInstant;
            return this.seconds == other.seconds &&
                   this.nanos == other.nanos;
        }
        return false;
    }

    /**
     * Returns a hash code for this instant.
     *
     * <p>
     *  返回此即时的哈希码。
     * 
     * 
     * @return a suitable hash code
     */
    @Override
    public int hashCode() {
        return ((int) (seconds ^ (seconds >>> 32))) + 51 * nanos;
    }

    //-----------------------------------------------------------------------
    /**
     * A string representation of this instant using ISO-8601 representation.
     * <p>
     * The format used is the same as {@link DateTimeFormatter#ISO_INSTANT}.
     *
     * <p>
     *  此即时的字符串表示使用ISO-8601表示。
     * <p>
     *  使用的格式与{@link DateTimeFormatter#ISO_INSTANT}相同。
     * 
     * 
     * @return an ISO-8601 representation of this instant, not null
     */
    @Override
    public String toString() {
        return DateTimeFormatter.ISO_INSTANT.format(this);
    }

    // -----------------------------------------------------------------------
    /**
     * Writes the object using a
     * <a href="../../serialized-form.html#java.time.Ser">dedicated serialized form</a>.
     * <p>
     *  使用<a href="../../serialized-form.html#java.time.Ser">专用序列化表单</a>写入对象。
     * 
     * 
     * @serialData
     * <pre>
     *  out.writeByte(2);  // identifies an Instant
     *  out.writeLong(seconds);
     *  out.writeInt(nanos);
     * </pre>
     *
     * @return the instance of {@code Ser}, not null
     */
    private Object writeReplace() {
        return new Ser(Ser.INSTANT_TYPE, this);
    }

    /**
     * Defend against malicious streams.
     *
     * <p>
     *  防御恶意流。
     * 
     * @param s the stream to read
     * @throws InvalidObjectException always
     */
    private void readObject(ObjectInputStream s) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    void writeExternal(DataOutput out) throws IOException {
        out.writeLong(seconds);
        out.writeInt(nanos);
    }

    static Instant readExternal(DataInput in) throws IOException {
        long seconds = in.readLong();
        int nanos = in.readInt();
        return Instant.ofEpochSecond(seconds, nanos);
    }

}
