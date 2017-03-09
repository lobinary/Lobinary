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

import static java.time.LocalTime.NANOS_PER_MINUTE;
import static java.time.LocalTime.NANOS_PER_SECOND;

import java.io.Serializable;
import java.util.Objects;
import java.util.TimeZone;

/**
 * A clock providing access to the current instant, date and time using a time-zone.
 * <p>
 * Instances of this class are used to find the current instant, which can be
 * interpreted using the stored time-zone to find the current date and time.
 * As such, a clock can be used instead of {@link System#currentTimeMillis()}
 * and {@link TimeZone#getDefault()}.
 * <p>
 * Use of a {@code Clock} is optional. All key date-time classes also have a
 * {@code now()} factory method that uses the system clock in the default time zone.
 * The primary purpose of this abstraction is to allow alternate clocks to be
 * plugged in as and when required. Applications use an object to obtain the
 * current time rather than a static method. This can simplify testing.
 * <p>
 * Best practice for applications is to pass a {@code Clock} into any method
 * that requires the current instant. A dependency injection framework is one
 * way to achieve this:
 * <pre>
 *  public class MyBean {
 *    private Clock clock;  // dependency inject
 *    ...
 *    public void process(LocalDate eventDate) {
 *      if (eventDate.isBefore(LocalDate.now(clock)) {
 *        ...
 *      }
 *    }
 *  }
 * </pre>
 * This approach allows an alternate clock, such as {@link #fixed(Instant, ZoneId) fixed}
 * or {@link #offset(Clock, Duration) offset} to be used during testing.
 * <p>
 * The {@code system} factory methods provide clocks based on the best available
 * system clock This may use {@link System#currentTimeMillis()}, or a higher
 * resolution clock if one is available.
 *
 * @implSpec
 * This abstract class must be implemented with care to ensure other classes operate correctly.
 * All implementations that can be instantiated must be final, immutable and thread-safe.
 * <p>
 * The principal methods are defined to allow the throwing of an exception.
 * In normal use, no exceptions will be thrown, however one possible implementation would be to
 * obtain the time from a central time server across the network. Obviously, in this case the
 * lookup could fail, and so the method is permitted to throw an exception.
 * <p>
 * The returned instants from {@code Clock} work on a time-scale that ignores leap seconds,
 * as described in {@link Instant}. If the implementation wraps a source that provides leap
 * second information, then a mechanism should be used to "smooth" the leap second.
 * The Java Time-Scale mandates the use of UTC-SLS, however clock implementations may choose
 * how accurate they are with the time-scale so long as they document how they work.
 * Implementations are therefore not required to actually perform the UTC-SLS slew or to
 * otherwise be aware of leap seconds.
 * <p>
 * Implementations should implement {@code Serializable} wherever possible and must
 * document whether or not they do support serialization.
 *
 * @implNote
 * The clock implementation provided here is based on {@link System#currentTimeMillis()}.
 * That method provides little to no guarantee about the accuracy of the clock.
 * Applications requiring a more accurate clock must implement this abstract class
 * themselves using a different external clock, such as an NTP server.
 *
 * <p>
 *  提供使用时区访问当前时间,日期和时间的时钟。
 * <p>
 *  该类的实例用于查找当前时刻,可以使用存储的时区来解释当前时刻,以查找当前日期和时间。
 * 因此,可以使用时钟代替{@link System#currentTimeMillis()}和{@link TimeZone#getDefault()}。
 * <p>
 *  使用{@code Clock}是可选的。所有关键日期时间类也有一个{@code now()}工厂方法,该方法使用默认时区中的系统时钟。此抽象的主要目的是允许在需要时插入备用时钟。
 * 应用程序使用对象来获取当前时间,而不是静态方法。这可以简化测试。
 * <p>
 * 应用程序的最佳做法是将{@code Clock}传递到需要当前时刻的任何方法。依赖注入框架是实现这一点的一种方式：
 * <pre>
 *  public class MyBean {private Clock clock; //依赖注入... public void process(LocalDate eventDate){if(eventDate.isBefore(LocalDate.now(clock)){...}
 * }}。
 * </pre>
 *  此方法允许在测试期间使用替代时钟,例如{@link #fixed(Instant,ZoneId)fixed}或{@link #offset(Clock,Duration)offset}。
 * <p>
 *  {@code system}工厂方法基于最佳可用系统时钟提供时钟。这可以使用{@link System#currentTimeMillis()}或更高分辨率的时钟(如果有的话)。
 * 
 *  @implSpec这个抽象类必须小心地实现,以确保其他类操作正确。所有可以实例化的实现必须是final,immutable和线程安全的。
 * <p>
 *  主要方法被定义为允许抛出异常。在正常使用中,不会抛出异常,但是一种可能的实现方式是从网络上的中央时间服务器获取时间。显然,在这种情况下,查找可能失败,因此允许该方法抛出异常。
 * <p>
 * 从{@code Clock}返回的时间在忽略闰秒的时间范围内工作,如{@link Instant}中所述。如果实现包装提供闰秒信息的源,则应该使用机制来"平滑"闰秒。
 *  Java Time-Scale强制使用UTC-SLS,但是时钟实现可以选择它们与时间尺度的精确程度,只要它们记录它们如何工作。因此,实现不需要实际执行UTC-SLS回转或以其他方式知道闰秒。
 * <p>
 *  实现应尽可能实现{@code Serializable},并且必须记录他们是否支持序列化。
 * 
 *  @implNote这里提供的时钟实现基于{@link System#currentTimeMillis()}。该方法几乎不能保证时钟的精度。
 * 需要更精确时钟的应用程序必须使用不同的外部时钟(如NTP服务器)自己实现此抽象类。
 * 
 * 
 * @since 1.8
 */
public abstract class Clock {

    /**
     * Obtains a clock that returns the current instant using the best available
     * system clock, converting to date and time using the UTC time-zone.
     * <p>
     * This clock, rather than {@link #systemDefaultZone()}, should be used when
     * you need the current instant without the date or time.
     * <p>
     * This clock is based on the best available system clock.
     * This may use {@link System#currentTimeMillis()}, or a higher resolution
     * clock if one is available.
     * <p>
     * Conversion from instant to date or time uses the {@linkplain ZoneOffset#UTC UTC time-zone}.
     * <p>
     * The returned implementation is immutable, thread-safe and {@code Serializable}.
     * It is equivalent to {@code system(ZoneOffset.UTC)}.
     *
     * <p>
     *  获取使用最佳可用系统时钟返回当前时钟的时钟,使用UTC时区转换为日期和时间。
     * <p>
     *  当你需要没有日期或时间的当前时刻时,应该使用此时钟,而不是{@link #systemDefaultZone()}。
     * <p>
     *  此时钟基于最佳可用系统时钟。这可以使用{@link System#currentTimeMillis()}或更高分辨率的时钟(如果有的话)。
     * <p>
     * 从即时到日期或时间的转换使用{@linkplain ZoneOffset#UTC UTC时区}。
     * <p>
     *  返回的实现是不可变的,线程安全的和{@code Serializable}。它等同于{@code system(ZoneOffset.UTC)}。
     * 
     * 
     * @return a clock that uses the best available system clock in the UTC zone, not null
     */
    public static Clock systemUTC() {
        return new SystemClock(ZoneOffset.UTC);
    }

    /**
     * Obtains a clock that returns the current instant using the best available
     * system clock, converting to date and time using the default time-zone.
     * <p>
     * This clock is based on the best available system clock.
     * This may use {@link System#currentTimeMillis()}, or a higher resolution
     * clock if one is available.
     * <p>
     * Using this method hard codes a dependency to the default time-zone into your application.
     * It is recommended to avoid this and use a specific time-zone whenever possible.
     * The {@link #systemUTC() UTC clock} should be used when you need the current instant
     * without the date or time.
     * <p>
     * The returned implementation is immutable, thread-safe and {@code Serializable}.
     * It is equivalent to {@code system(ZoneId.systemDefault())}.
     *
     * <p>
     *  获取使用最佳可用系统时钟返回当前时钟的时钟,使用默认时区转换为日期和时间。
     * <p>
     *  此时钟基于最佳可用系统时钟。这可以使用{@link System#currentTimeMillis()}或更高分辨率的时钟(如果有的话)。
     * <p>
     *  使用此方法将对应用程序的默认时区的依赖项硬编码。建议避免这种情况,并尽可能使用特定的时区。当需要当前时刻而不使用日期或时间时,应使用{@link #systemUTC()UTC时钟}。
     * <p>
     *  返回的实现是不可变的,线程安全的和{@code Serializable}。它等同于{@code system(ZoneId.systemDefault())}。
     * 
     * 
     * @return a clock that uses the best available system clock in the default zone, not null
     * @see ZoneId#systemDefault()
     */
    public static Clock systemDefaultZone() {
        return new SystemClock(ZoneId.systemDefault());
    }

    /**
     * Obtains a clock that returns the current instant using best available
     * system clock.
     * <p>
     * This clock is based on the best available system clock.
     * This may use {@link System#currentTimeMillis()}, or a higher resolution
     * clock if one is available.
     * <p>
     * Conversion from instant to date or time uses the specified time-zone.
     * <p>
     * The returned implementation is immutable, thread-safe and {@code Serializable}.
     *
     * <p>
     *  获取使用最佳可用系统时钟返回当前时钟的时钟。
     * <p>
     *  此时钟基于最佳可用系统时钟。这可以使用{@link System#currentTimeMillis()}或更高分辨率的时钟(如果有的话)。
     * <p>
     *  从即时到日期或时间的转换使用指定的时区。
     * <p>
     *  返回的实现是不可变的,线程安全的和{@code Serializable}。
     * 
     * 
     * @param zone  the time-zone to use to convert the instant to date-time, not null
     * @return a clock that uses the best available system clock in the specified zone, not null
     */
    public static Clock system(ZoneId zone) {
        Objects.requireNonNull(zone, "zone");
        return new SystemClock(zone);
    }

    //-------------------------------------------------------------------------
    /**
     * Obtains a clock that returns the current instant ticking in whole seconds
     * using best available system clock.
     * <p>
     * This clock will always have the nano-of-second field set to zero.
     * This ensures that the visible time ticks in whole seconds.
     * The underlying clock is the best available system clock, equivalent to
     * using {@link #system(ZoneId)}.
     * <p>
     * Implementations may use a caching strategy for performance reasons.
     * As such, it is possible that the start of the second observed via this
     * clock will be later than that observed directly via the underlying clock.
     * <p>
     * The returned implementation is immutable, thread-safe and {@code Serializable}.
     * It is equivalent to {@code tick(system(zone), Duration.ofSeconds(1))}.
     *
     * <p>
     *  获取一个时钟,使用最佳可用系统时钟以整秒钟返回当前时刻。
     * <p>
     * 该时钟将总是具有设置为零的纳秒二阶场。这确保可见时间以整秒为单位。底层时钟是最好的可用系统时钟,相当于使用{@link #system(ZoneId)}。
     * <p>
     *  出于性能原因,实现可以使用高速缓存策略。因此,可能经由该时钟观察到的第二个的开始将晚于直接经由基础时钟观察到的开始。
     * <p>
     *  返回的实现是不可变的,线程安全的和{@code Serializable}。它等价于{@code tick(system(zone),Duration.ofSeconds(1))}。
     * 
     * 
     * @param zone  the time-zone to use to convert the instant to date-time, not null
     * @return a clock that ticks in whole seconds using the specified zone, not null
     */
    public static Clock tickSeconds(ZoneId zone) {
        return new TickClock(system(zone), NANOS_PER_SECOND);
    }

    /**
     * Obtains a clock that returns the current instant ticking in whole minutes
     * using best available system clock.
     * <p>
     * This clock will always have the nano-of-second and second-of-minute fields set to zero.
     * This ensures that the visible time ticks in whole minutes.
     * The underlying clock is the best available system clock, equivalent to
     * using {@link #system(ZoneId)}.
     * <p>
     * Implementations may use a caching strategy for performance reasons.
     * As such, it is possible that the start of the minute observed via this
     * clock will be later than that observed directly via the underlying clock.
     * <p>
     * The returned implementation is immutable, thread-safe and {@code Serializable}.
     * It is equivalent to {@code tick(system(zone), Duration.ofMinutes(1))}.
     *
     * <p>
     *  获取一个时钟,使用最佳可用系统时钟返回整个分钟的当前时刻。
     * <p>
     *  该时钟将总是具有设置为零的毫微秒和秒的字段。这确保可见时间在整分钟内滴答。底层时钟是最好的可用系统时钟,相当于使用{@link #system(ZoneId)}。
     * <p>
     *  出于性能原因,实现可以使用高速缓存策略。因此,可能通过该时钟观察到的分钟的开始晚于直接通过基础时钟观察到的分钟的开始。
     * <p>
     *  返回的实现是不可变的,线程安全的和{@code Serializable}。它等价于{@code tick(system(zone),Duration.ofMinutes(1))}。
     * 
     * 
     * @param zone  the time-zone to use to convert the instant to date-time, not null
     * @return a clock that ticks in whole minutes using the specified zone, not null
     */
    public static Clock tickMinutes(ZoneId zone) {
        return new TickClock(system(zone), NANOS_PER_MINUTE);
    }

    /**
     * Obtains a clock that returns instants from the specified clock truncated
     * to the nearest occurrence of the specified duration.
     * <p>
     * This clock will only tick as per the specified duration. Thus, if the duration
     * is half a second, the clock will return instants truncated to the half second.
     * <p>
     * The tick duration must be positive. If it has a part smaller than a whole
     * millisecond, then the whole duration must divide into one second without
     * leaving a remainder. All normal tick durations will match these criteria,
     * including any multiple of hours, minutes, seconds and milliseconds, and
     * sensible nanosecond durations, such as 20ns, 250,000ns and 500,000ns.
     * <p>
     * A duration of zero or one nanosecond would have no truncation effect.
     * Passing one of these will return the underlying clock.
     * <p>
     * Implementations may use a caching strategy for performance reasons.
     * As such, it is possible that the start of the requested duration observed
     * via this clock will be later than that observed directly via the underlying clock.
     * <p>
     * The returned implementation is immutable, thread-safe and {@code Serializable}
     * providing that the base clock is.
     *
     * <p>
     *  获取一个时钟,它返回从指定时钟截断到指定持续时间的最近事件的时刻。
     * <p>
     * 此时钟只会根据指定的持续时间进行计时。因此,如果持续时间为半秒,则时钟将返回截断到半秒的瞬间。
     * <p>
     *  滴答时间必须为正。如果它的一部分小于整个毫秒,则整个持续时间必须分成一秒而不留下余数。
     * 所有正常滴答持续时间将匹配这些标准,包括小时,分钟,秒和毫秒的任何倍数,以及明显的纳秒持续时间,例如20ns,250,000ns和500,000ns。
     * <p>
     *  持续时间为零或一纳秒将不具有截断效应。通过其中一个将返回基础时钟。
     * <p>
     *  出于性能原因,实现可以使用高速缓存策略。因此,有可能通过该时钟观察到的所请求持续时间的开始晚于直接经由基础时钟观察到的开始。
     * <p>
     *  返回的实现是不可变的,线程安全的和{@code Serializable}提供基本时钟。
     * 
     * 
     * @param baseClock  the base clock to base the ticking clock on, not null
     * @param tickDuration  the duration of each visible tick, not negative, not null
     * @return a clock that ticks in whole units of the duration, not null
     * @throws IllegalArgumentException if the duration is negative, or has a
     *  part smaller than a whole millisecond such that the whole duration is not
     *  divisible into one second
     * @throws ArithmeticException if the duration is too large to be represented as nanos
     */
    public static Clock tick(Clock baseClock, Duration tickDuration) {
        Objects.requireNonNull(baseClock, "baseClock");
        Objects.requireNonNull(tickDuration, "tickDuration");
        if (tickDuration.isNegative()) {
            throw new IllegalArgumentException("Tick duration must not be negative");
        }
        long tickNanos = tickDuration.toNanos();
        if (tickNanos % 1000_000 == 0) {
            // ok, no fraction of millisecond
        } else if (1000_000_000 % tickNanos == 0) {
            // ok, divides into one second without remainder
        } else {
            throw new IllegalArgumentException("Invalid tick duration");
        }
        if (tickNanos <= 1) {
            return baseClock;
        }
        return new TickClock(baseClock, tickNanos);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains a clock that always returns the same instant.
     * <p>
     * This clock simply returns the specified instant.
     * As such, it is not a clock in the conventional sense.
     * The main use case for this is in testing, where the fixed clock ensures
     * tests are not dependent on the current clock.
     * <p>
     * The returned implementation is immutable, thread-safe and {@code Serializable}.
     *
     * <p>
     *  获取总是返回相同时刻的时钟。
     * <p>
     *  此时钟只返回指定的时刻。因此,它不是常规意义上的时钟。这种情况的主要用途是在测试中,其中固定时钟确保测试不依赖于当前时钟。
     * <p>
     *  返回的实现是不可变的,线程安全的和{@code Serializable}。
     * 
     * 
     * @param fixedInstant  the instant to use as the clock, not null
     * @param zone  the time-zone to use to convert the instant to date-time, not null
     * @return a clock that always returns the same instant, not null
     */
    public static Clock fixed(Instant fixedInstant, ZoneId zone) {
        Objects.requireNonNull(fixedInstant, "fixedInstant");
        Objects.requireNonNull(zone, "zone");
        return new FixedClock(fixedInstant, zone);
    }

    //-------------------------------------------------------------------------
    /**
     * Obtains a clock that returns instants from the specified clock with the
     * specified duration added
     * <p>
     * This clock wraps another clock, returning instants that are later by the
     * specified duration. If the duration is negative, the instants will be
     * earlier than the current date and time.
     * The main use case for this is to simulate running in the future or in the past.
     * <p>
     * A duration of zero would have no offsetting effect.
     * Passing zero will return the underlying clock.
     * <p>
     * The returned implementation is immutable, thread-safe and {@code Serializable}
     * providing that the base clock is.
     *
     * <p>
     *  获取从指定时钟返回具有指定持续时间的时刻的时钟
     * <p>
     * 该时钟包装另一个时钟,返回指定持续时间的时刻。如果持续时间为负,则时刻将早于当前日期和时间。这个的主要用例是模拟未来或过去的运行。
     * <p>
     *  持续时间为零将没有抵消效应。传递0将返回基础时钟。
     * <p>
     *  返回的实现是不可变的,线程安全的和{@code Serializable}提供基本时钟。
     * 
     * 
     * @param baseClock  the base clock to add the duration to, not null
     * @param offsetDuration  the duration to add, not null
     * @return a clock based on the base clock with the duration added, not null
     */
    public static Clock offset(Clock baseClock, Duration offsetDuration) {
        Objects.requireNonNull(baseClock, "baseClock");
        Objects.requireNonNull(offsetDuration, "offsetDuration");
        if (offsetDuration.equals(Duration.ZERO)) {
            return baseClock;
        }
        return new OffsetClock(baseClock, offsetDuration);
    }

    //-----------------------------------------------------------------------
    /**
     * Constructor accessible by subclasses.
     * <p>
     *  构造函数可以通过子类访问。
     * 
     */
    protected Clock() {
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the time-zone being used to create dates and times.
     * <p>
     * A clock will typically obtain the current instant and then convert that
     * to a date or time using a time-zone. This method returns the time-zone used.
     *
     * <p>
     *  获取用于创建日期和时间的时区。
     * <p>
     *  时钟通常将获得当前时刻,然后使用时区将其转换为日期或时间。此方法返回使用的时区。
     * 
     * 
     * @return the time-zone being used to interpret instants, not null
     */
    public abstract ZoneId getZone();

    /**
     * Returns a copy of this clock with a different time-zone.
     * <p>
     * A clock will typically obtain the current instant and then convert that
     * to a date or time using a time-zone. This method returns a clock with
     * similar properties but using a different time-zone.
     *
     * <p>
     *  返回具有不同时区的此时钟的副本。
     * <p>
     *  时钟通常将获得当前时刻,然后使用时区将其转换为日期或时间。此方法返回具有类似属性但使用不同时区的时钟。
     * 
     * 
     * @param zone  the time-zone to change to, not null
     * @return a clock based on this clock with the specified time-zone, not null
     */
    public abstract Clock withZone(ZoneId zone);

    //-------------------------------------------------------------------------
    /**
     * Gets the current millisecond instant of the clock.
     * <p>
     * This returns the millisecond-based instant, measured from 1970-01-01T00:00Z (UTC).
     * This is equivalent to the definition of {@link System#currentTimeMillis()}.
     * <p>
     * Most applications should avoid this method and use {@link Instant} to represent
     * an instant on the time-line rather than a raw millisecond value.
     * This method is provided to allow the use of the clock in high performance use cases
     * where the creation of an object would be unacceptable.
     * <p>
     * The default implementation currently calls {@link #instant}.
     *
     * <p>
     *  获取当前毫秒时钟的时钟。
     * <p>
     *  这返回基于毫秒的时刻,从1970-01-01T00：00Z(UTC)开始测量。这相当于{@link System#currentTimeMillis()}的定义。
     * <p>
     *  大多数应用程序应避免使用此方法,并使用{@link Instant}在时间线上表示即时值,而不是原始毫秒值。提供此方法以允许在高性能使用情况下使用时钟,其中对象的创建将是不可接受的。
     * <p>
     * 默认实现当前调用{@link #instant}。
     * 
     * 
     * @return the current millisecond instant from this clock, measured from
     *  the Java epoch of 1970-01-01T00:00Z (UTC), not null
     * @throws DateTimeException if the instant cannot be obtained, not thrown by most implementations
     */
    public long millis() {
        return instant().toEpochMilli();
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the current instant of the clock.
     * <p>
     * This returns an instant representing the current instant as defined by the clock.
     *
     * <p>
     *  获取时钟的当前时刻。
     * <p>
     *  这将返回表示由时钟定义的当前时刻的时刻。
     * 
     * 
     * @return the current instant from this clock, not null
     * @throws DateTimeException if the instant cannot be obtained, not thrown by most implementations
     */
    public abstract Instant instant();

    //-----------------------------------------------------------------------
    /**
     * Checks if this clock is equal to another clock.
     * <p>
     * Clocks should override this method to compare equals based on
     * their state and to meet the contract of {@link Object#equals}.
     * If not overridden, the behavior is defined by {@link Object#equals}
     *
     * <p>
     *  检查此时钟是否等于另一个时钟。
     * <p>
     *  时钟应该重写这个方法来比较等于基于他们的状态和满足{@link对象#等于}的合同。如果没有重写,行为由{@link Object#equals}
     * 
     * 
     * @param obj  the object to check, null returns false
     * @return true if this is equal to the other clock
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /**
     * A hash code for this clock.
     * <p>
     * Clocks should override this method based on
     * their state and to meet the contract of {@link Object#hashCode}.
     * If not overridden, the behavior is defined by {@link Object#hashCode}
     *
     * <p>
     *  此时钟的哈希码。
     * <p>
     *  时钟应该基于它们的状态覆盖这个方法,并且满足{@link Object#hashCode}的合同。如果没有重写,行为由{@link Object#hashCode}
     * 
     * 
     * @return a suitable hash code
     */
    @Override
    public  int hashCode() {
        return super.hashCode();
    }

    //-----------------------------------------------------------------------
    /**
     * Implementation of a clock that always returns the latest time from
     * {@link System#currentTimeMillis()}.
     * <p>
     *  实现一个总是从{@link System#currentTimeMillis()}返回最新时间的时钟。
     * 
     */
    static final class SystemClock extends Clock implements Serializable {
        private static final long serialVersionUID = 6740630888130243051L;
        private final ZoneId zone;

        SystemClock(ZoneId zone) {
            this.zone = zone;
        }
        @Override
        public ZoneId getZone() {
            return zone;
        }
        @Override
        public Clock withZone(ZoneId zone) {
            if (zone.equals(this.zone)) {  // intentional NPE
                return this;
            }
            return new SystemClock(zone);
        }
        @Override
        public long millis() {
            return System.currentTimeMillis();
        }
        @Override
        public Instant instant() {
            return Instant.ofEpochMilli(millis());
        }
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof SystemClock) {
                return zone.equals(((SystemClock) obj).zone);
            }
            return false;
        }
        @Override
        public int hashCode() {
            return zone.hashCode() + 1;
        }
        @Override
        public String toString() {
            return "SystemClock[" + zone + "]";
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Implementation of a clock that always returns the same instant.
     * This is typically used for testing.
     * <p>
     *  实现总是返回相同时刻的时钟。这通常用于测试。
     * 
     */
    static final class FixedClock extends Clock implements Serializable {
       private static final long serialVersionUID = 7430389292664866958L;
        private final Instant instant;
        private final ZoneId zone;

        FixedClock(Instant fixedInstant, ZoneId zone) {
            this.instant = fixedInstant;
            this.zone = zone;
        }
        @Override
        public ZoneId getZone() {
            return zone;
        }
        @Override
        public Clock withZone(ZoneId zone) {
            if (zone.equals(this.zone)) {  // intentional NPE
                return this;
            }
            return new FixedClock(instant, zone);
        }
        @Override
        public long millis() {
            return instant.toEpochMilli();
        }
        @Override
        public Instant instant() {
            return instant;
        }
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof FixedClock) {
                FixedClock other = (FixedClock) obj;
                return instant.equals(other.instant) && zone.equals(other.zone);
            }
            return false;
        }
        @Override
        public int hashCode() {
            return instant.hashCode() ^ zone.hashCode();
        }
        @Override
        public String toString() {
            return "FixedClock[" + instant + "," + zone + "]";
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Implementation of a clock that adds an offset to an underlying clock.
     * <p>
     *  实现向底层时钟添加偏移的时钟。
     * 
     */
    static final class OffsetClock extends Clock implements Serializable {
       private static final long serialVersionUID = 2007484719125426256L;
        private final Clock baseClock;
        private final Duration offset;

        OffsetClock(Clock baseClock, Duration offset) {
            this.baseClock = baseClock;
            this.offset = offset;
        }
        @Override
        public ZoneId getZone() {
            return baseClock.getZone();
        }
        @Override
        public Clock withZone(ZoneId zone) {
            if (zone.equals(baseClock.getZone())) {  // intentional NPE
                return this;
            }
            return new OffsetClock(baseClock.withZone(zone), offset);
        }
        @Override
        public long millis() {
            return Math.addExact(baseClock.millis(), offset.toMillis());
        }
        @Override
        public Instant instant() {
            return baseClock.instant().plus(offset);
        }
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof OffsetClock) {
                OffsetClock other = (OffsetClock) obj;
                return baseClock.equals(other.baseClock) && offset.equals(other.offset);
            }
            return false;
        }
        @Override
        public int hashCode() {
            return baseClock.hashCode() ^ offset.hashCode();
        }
        @Override
        public String toString() {
            return "OffsetClock[" + baseClock + "," + offset + "]";
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Implementation of a clock that adds an offset to an underlying clock.
     * <p>
     *  实现向底层时钟添加偏移的时钟。
     */
    static final class TickClock extends Clock implements Serializable {
        private static final long serialVersionUID = 6504659149906368850L;
        private final Clock baseClock;
        private final long tickNanos;

        TickClock(Clock baseClock, long tickNanos) {
            this.baseClock = baseClock;
            this.tickNanos = tickNanos;
        }
        @Override
        public ZoneId getZone() {
            return baseClock.getZone();
        }
        @Override
        public Clock withZone(ZoneId zone) {
            if (zone.equals(baseClock.getZone())) {  // intentional NPE
                return this;
            }
            return new TickClock(baseClock.withZone(zone), tickNanos);
        }
        @Override
        public long millis() {
            long millis = baseClock.millis();
            return millis - Math.floorMod(millis, tickNanos / 1000_000L);
        }
        @Override
        public Instant instant() {
            if ((tickNanos % 1000_000) == 0) {
                long millis = baseClock.millis();
                return Instant.ofEpochMilli(millis - Math.floorMod(millis, tickNanos / 1000_000L));
            }
            Instant instant = baseClock.instant();
            long nanos = instant.getNano();
            long adjust = Math.floorMod(nanos, tickNanos);
            return instant.minusNanos(adjust);
        }
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof TickClock) {
                TickClock other = (TickClock) obj;
                return baseClock.equals(other.baseClock) && tickNanos == other.tickNanos;
            }
            return false;
        }
        @Override
        public int hashCode() {
            return baseClock.hashCode() ^ ((int) (tickNanos ^ (tickNanos >>> 32)));
        }
        @Override
        public String toString() {
            return "TickClock[" + baseClock + "," + Duration.ofNanos(tickNanos) + "]";
        }
    }

}
