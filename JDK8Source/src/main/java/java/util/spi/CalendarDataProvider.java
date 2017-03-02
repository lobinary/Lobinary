/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 */

package java.util.spi;

import java.util.Calendar;
import java.util.Locale;

/**
 * An abstract class for service providers that provide locale-dependent {@link
 * Calendar} parameters.
 *
 * 用于提供依赖于区域设置的{@link Calendar}参数的服务提供程序的抽象类。
 * 
 * @author Masayoshi Okutsu
 * @since 1.8
 * @see CalendarNameProvider
 */
public abstract class CalendarDataProvider extends LocaleServiceProvider {

    /**
     * Sole constructor. (For invocation by subclass constructors, typically
     * implicit.)
	 * 唯一构造函数。 （对于子类构造函数的调用，通常是隐式的。）
     */
    protected CalendarDataProvider() {
    }

    /**
     * Returns the first day of a week in the given {@code locale}. This
     * information is required by {@link Calendar} to support operations on the
     * week-related calendar fields.
		<br>
	 * 返回给定{@code locale}中的一周的第一天。 {@link Calendar}需要此信息来支持与星期相关的日历字段的操作。
     * @param locale
     *        the desired locale
     * @return the first day of a week; one of {@link Calendar#SUNDAY} ..
     *         {@link Calendar#SATURDAY},
     *         or 0 if the value isn't available for the {@code locale}
     * @throws NullPointerException
     *         if {@code locale} is {@code null}.
     * @see java.util.Calendar#getFirstDayOfWeek()
     * @see <a href="../Calendar.html#first_week">First Week</a>
     */
    public abstract int getFirstDayOfWeek(Locale locale);

    /**
     * Returns the minimal number of days required in the first week of a
     * year. This information is required by {@link Calendar} to determine the
     * first week of a year. Refer to the description of <a
     * href="../Calendar.html#first_week"> how {@code Calendar} determines
     * the first week</a>.
     *
     *<br>

	 * 返回一年中第一周所需的最少天数。 {@link Calendar}需要此信息来确
	 * 定一年中的第一周。请参阅<a href="../Calendar.html#fi
	 * rst_week"> {@code Calendar}如何确定第一周</a>的说
	 * 明。

     * @param locale
     *        the desired locale
     * @return the minimal number of days of the first week,
     *         or 0 if the value isn't available for the {@code locale}
     * @throws NullPointerException
     *         if {@code locale} is {@code null}.
     * @see java.util.Calendar#getMinimalDaysInFirstWeek()
     */
    public abstract int getMinimalDaysInFirstWeek(Locale locale);
}
