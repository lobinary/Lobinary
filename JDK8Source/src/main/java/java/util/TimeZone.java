/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2014, Oracle and/or its affiliates. All rights reserved.
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
 * (C) Copyright Taligent, Inc. 1996 - All Rights Reserved
 * (C) Copyright IBM Corp. 1996 - All Rights Reserved
 *
 *   The original version of this source code and documentation is copyrighted
 * and owned by Taligent, Inc., a wholly-owned subsidiary of IBM. These
 * materials are provided under terms of a License Agreement between Taligent
 * and Sun. This technology is protected by multiple US and International
 * patents. This notice and attribution to Taligent may not be removed.
 *   Taligent is a registered trademark of Taligent, Inc.
 *
 * <p>
 *  (C)版权Taligent,Inc. 1996  - 保留所有权利(C)版权所有IBM Corp. 1996  - 保留所有权利
 * 
 *  此源代码和文档的原始版本由IBM的全资子公司Taligent,Inc.拥有版权和所有权。这些材料是根据Taligent和Sun之间的许可协议的条款提供的。该技术受多项美国和国际专利保护。
 * 此通知和归因于Taligent不得删除。 Taligent是Taligent,Inc.的注册商标。
 * 
 */

package java.util;

import java.io.Serializable;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.time.ZoneId;
import sun.security.action.GetPropertyAction;
import sun.util.calendar.ZoneInfo;
import sun.util.calendar.ZoneInfoFile;
import sun.util.locale.provider.TimeZoneNameUtility;

/**
 * <code>TimeZone</code> represents a time zone offset, and also figures out daylight
 * savings.
 *
 * <p>
 * Typically, you get a <code>TimeZone</code> using <code>getDefault</code>
 * which creates a <code>TimeZone</code> based on the time zone where the program
 * is running. For example, for a program running in Japan, <code>getDefault</code>
 * creates a <code>TimeZone</code> object based on Japanese Standard Time.
 *
 * <p>
 * You can also get a <code>TimeZone</code> using <code>getTimeZone</code>
 * along with a time zone ID. For instance, the time zone ID for the
 * U.S. Pacific Time zone is "America/Los_Angeles". So, you can get a
 * U.S. Pacific Time <code>TimeZone</code> object with:
 * <blockquote><pre>
 * TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
 * </pre></blockquote>
 * You can use the <code>getAvailableIDs</code> method to iterate through
 * all the supported time zone IDs. You can then choose a
 * supported ID to get a <code>TimeZone</code>.
 * If the time zone you want is not represented by one of the
 * supported IDs, then a custom time zone ID can be specified to
 * produce a TimeZone. The syntax of a custom time zone ID is:
 *
 * <blockquote><pre>
 * <a name="CustomID"><i>CustomID:</i></a>
 *         <code>GMT</code> <i>Sign</i> <i>Hours</i> <code>:</code> <i>Minutes</i>
 *         <code>GMT</code> <i>Sign</i> <i>Hours</i> <i>Minutes</i>
 *         <code>GMT</code> <i>Sign</i> <i>Hours</i>
 * <i>Sign:</i> one of
 *         <code>+ -</code>
 * <i>Hours:</i>
 *         <i>Digit</i>
 *         <i>Digit</i> <i>Digit</i>
 * <i>Minutes:</i>
 *         <i>Digit</i> <i>Digit</i>
 * <i>Digit:</i> one of
 *         <code>0 1 2 3 4 5 6 7 8 9</code>
 * </pre></blockquote>
 *
 * <i>Hours</i> must be between 0 to 23 and <i>Minutes</i> must be
 * between 00 to 59.  For example, "GMT+10" and "GMT+0010" mean ten
 * hours and ten minutes ahead of GMT, respectively.
 * <p>
 * The format is locale independent and digits must be taken from the
 * Basic Latin block of the Unicode standard. No daylight saving time
 * transition schedule can be specified with a custom time zone ID. If
 * the specified string doesn't match the syntax, <code>"GMT"</code>
 * is used.
 * <p>
 * When creating a <code>TimeZone</code>, the specified custom time
 * zone ID is normalized in the following syntax:
 * <blockquote><pre>
 * <a name="NormalizedCustomID"><i>NormalizedCustomID:</i></a>
 *         <code>GMT</code> <i>Sign</i> <i>TwoDigitHours</i> <code>:</code> <i>Minutes</i>
 * <i>Sign:</i> one of
 *         <code>+ -</code>
 * <i>TwoDigitHours:</i>
 *         <i>Digit</i> <i>Digit</i>
 * <i>Minutes:</i>
 *         <i>Digit</i> <i>Digit</i>
 * <i>Digit:</i> one of
 *         <code>0 1 2 3 4 5 6 7 8 9</code>
 * </pre></blockquote>
 * For example, TimeZone.getTimeZone("GMT-8").getID() returns "GMT-08:00".
 *
 * <h3>Three-letter time zone IDs</h3>
 *
 * For compatibility with JDK 1.1.x, some other three-letter time zone IDs
 * (such as "PST", "CTT", "AST") are also supported. However, <strong>their
 * use is deprecated</strong> because the same abbreviation is often used
 * for multiple time zones (for example, "CST" could be U.S. "Central Standard
 * Time" and "China Standard Time"), and the Java platform can then only
 * recognize one of them.
 *
 *
 * <p>
 *  <code> TimeZone </code>表示时区偏移,并计算夏令时。
 * 
 * <p>
 *  通常,您将使用<code> getDefault </code>获取<code> TimeZone </code>,它会根据程序运行的时区创建<code> TimeZone </code>。
 * 例如,对于在日本运行的程序,<code> getDefault </code>基于日本标准时间创建<code> TimeZone </code>对象。
 * 
 * <p>
 * 您还可以使用<code> getTimeZone </code>和时区ID获取<code> TimeZone </code>。
 * 例如,美国太平洋时区的时区ID为"America / Los_Angeles"。
 * 因此,您可以通过以下方式获取美国太平洋时间<code> TimeZone </code>对象：<blockquote> <pre> TimeZone tz = TimeZone.getTimeZone(
 * "America / Los_Angeles"); </pre> </blockquote>您可以使用<code> getAvailableIDs </code>方法遍历所有支持的时区ID。
 * 例如,美国太平洋时区的时区ID为"America / Los_Angeles"。然后,您可以选择支持的ID以获取<code> TimeZone </code>。
 * 如果所需的时区不是由支持的ID之一表示,则可以指定自定义时区ID以生成TimeZone。自定义时区ID的语法是：。
 * 
 *  <blockquote> <pre> <a name="CustomID"> <i> CustomID：</i> </a> <code> GMT </code> <i>签署</i> > <code>：
 * </code> <i> </i> </i> </i> </i>代码> GMT </code> <i>签署</i> <i>小时</i> <i>签署</i> i> <i>数字</i> <i> </i> <i>
 *  </i> > <i> </>> </code> </b> </。
 * 
 *  <i>小时</i>必须介于0到23之间,而且<i>分钟</i>必须介于00到59之间。例如,"GMT + 10"和"GMT +提前GMT。
 * <p>
 * 格式与区域设置无关,数字必须取自Unicode标准的基本拉丁语区块。不能使用自定义时区ID指定夏令时转换计划。如果指定的字符串与语法不匹配,则使用<code>"GMT"</code>。
 * <p>
 *  创建<code> TimeZone </code>时,指定的自定义时区ID使用以下语法进行规范化：<blockquote> <pre> <a name="NormalizedCustomID"> <i>
 *  NormalizedCustomID：</a> <code> GMT </code> <i> Sign </i> <i> </i> </i> > <code> +  -  </code> <i> Tw
 * oDigitHours：</i> <i>数字</i> <i>数字</i> <i>分钟：</i> <i> </i> <i> </i> <i> </i> <i> </i> <i> </i> ,TimeZon
 * e.getTimeZone("GMT-8")。
 * getID()返回"GMT-08：00"。
 * 
 *  <h3>三个字母的时区ID </h3>
 * 
 *  为了与JDK 1.1.x兼容,还支持一些其他三个字母的时区ID(例如"PST","CTT","AST")。
 * 但是,<strong>其使用已过时</strong>,因为相同的缩写通常用于多个时区(例如,"CST"可能是美国"中央标准时间"和"中国标准时间"),平台然后只能识别其中之一。
 * 
 * 
 * @see          Calendar
 * @see          GregorianCalendar
 * @see          SimpleTimeZone
 * @author       Mark Davis, David Goldsmith, Chen-Lieh Huang, Alan Liu
 * @since        JDK1.1
 */
abstract public class TimeZone implements Serializable, Cloneable {
    /**
     * Sole constructor.  (For invocation by subclass constructors, typically
     * implicit.)
     * <p>
     *  唯一构造函数。 (对于子类构造函数的调用,通常是隐式的。)
     * 
     */
    public TimeZone() {
    }

    /**
     * A style specifier for <code>getDisplayName()</code> indicating
     * a short name, such as "PST."
     * <p>
     *  <code> getDisplayName()</code>的样式说明符,指示短名称,例如"PST"。
     * 
     * 
     * @see #LONG
     * @since 1.2
     */
    public static final int SHORT = 0;

    /**
     * A style specifier for <code>getDisplayName()</code> indicating
     * a long name, such as "Pacific Standard Time."
     * <p>
     * <code> getDisplayName()</code>的样式说明符,表示长名称,例如"太平洋标准时间"。
     * 
     * 
     * @see #SHORT
     * @since 1.2
     */
    public static final int LONG  = 1;

    // Constants used internally; unit is milliseconds
    private static final int ONE_MINUTE = 60*1000;
    private static final int ONE_HOUR   = 60*ONE_MINUTE;
    private static final int ONE_DAY    = 24*ONE_HOUR;

    // Proclaim serialization compatibility with JDK 1.1
    static final long serialVersionUID = 3581463369166924961L;

    /**
     * Gets the time zone offset, for current date, modified in case of
     * daylight savings. This is the offset to add to UTC to get local time.
     * <p>
     * This method returns a historically correct offset if an
     * underlying <code>TimeZone</code> implementation subclass
     * supports historical Daylight Saving Time schedule and GMT
     * offset changes.
     *
     * <p>
     *  获取当前日期的时区偏移,在夏令时进行修改。这是添加到UTC以获取本地时间的偏移量。
     * <p>
     *  如果底层<code> TimeZone </code>实现子类支持历史夏令时计划和GMT偏移量更改,此方法将返回历史正确的偏移量。
     * 
     * 
     * @param era the era of the given date.
     * @param year the year in the given date.
     * @param month the month in the given date.
     * Month is 0-based. e.g., 0 for January.
     * @param day the day-in-month of the given date.
     * @param dayOfWeek the day-of-week of the given date.
     * @param milliseconds the milliseconds in day in <em>standard</em>
     * local time.
     *
     * @return the offset in milliseconds to add to GMT to get local time.
     *
     * @see Calendar#ZONE_OFFSET
     * @see Calendar#DST_OFFSET
     */
    public abstract int getOffset(int era, int year, int month, int day,
                                  int dayOfWeek, int milliseconds);

    /**
     * Returns the offset of this time zone from UTC at the specified
     * date. If Daylight Saving Time is in effect at the specified
     * date, the offset value is adjusted with the amount of daylight
     * saving.
     * <p>
     * This method returns a historically correct offset value if an
     * underlying TimeZone implementation subclass supports historical
     * Daylight Saving Time schedule and GMT offset changes.
     *
     * <p>
     *  返回此时区在指定日期从UTC的偏移量。如果夏令时在指定日期生效,则会使用夏令时量调整偏移值。
     * <p>
     *  如果底层TimeZone实现子类支持历史夏令时计划和GMT偏移量更改,此方法将返回历史正确的偏移值。
     * 
     * 
     * @param date the date represented in milliseconds since January 1, 1970 00:00:00 GMT
     * @return the amount of time in milliseconds to add to UTC to get local time.
     *
     * @see Calendar#ZONE_OFFSET
     * @see Calendar#DST_OFFSET
     * @since 1.4
     */
    public int getOffset(long date) {
        if (inDaylightTime(new Date(date))) {
            return getRawOffset() + getDSTSavings();
        }
        return getRawOffset();
    }

    /**
     * Gets the raw GMT offset and the amount of daylight saving of this
     * time zone at the given time.
     * <p>
     *  获取给定时间此时区的原始GMT偏移量和夏令时量。
     * 
     * 
     * @param date the milliseconds (since January 1, 1970,
     * 00:00:00.000 GMT) at which the time zone offset and daylight
     * saving amount are found
     * @param offsets an array of int where the raw GMT offset
     * (offset[0]) and daylight saving amount (offset[1]) are stored,
     * or null if those values are not needed. The method assumes that
     * the length of the given array is two or larger.
     * @return the total amount of the raw GMT offset and daylight
     * saving at the specified date.
     *
     * @see Calendar#ZONE_OFFSET
     * @see Calendar#DST_OFFSET
     */
    int getOffsets(long date, int[] offsets) {
        int rawoffset = getRawOffset();
        int dstoffset = 0;
        if (inDaylightTime(new Date(date))) {
            dstoffset = getDSTSavings();
        }
        if (offsets != null) {
            offsets[0] = rawoffset;
            offsets[1] = dstoffset;
        }
        return rawoffset + dstoffset;
    }

    /**
     * Sets the base time zone offset to GMT.
     * This is the offset to add to UTC to get local time.
     * <p>
     * If an underlying <code>TimeZone</code> implementation subclass
     * supports historical GMT offset changes, the specified GMT
     * offset is set as the latest GMT offset and the difference from
     * the known latest GMT offset value is used to adjust all
     * historical GMT offset values.
     *
     * <p>
     *  将基准时区偏移设置为GMT。这是添加到UTC以获取本地时间的偏移量。
     * <p>
     *  如果底层<code> TimeZone </code>实现子类支持历史GMT偏移量更改,则指定的GMT偏移量设置为最新的GMT偏移量,并且与已知的最新GMT偏移量值的差值用于调整所有历史GMT偏移量值
     * 。
     * 
     * 
     * @param offsetMillis the given base time zone offset to GMT.
     */
    abstract public void setRawOffset(int offsetMillis);

    /**
     * Returns the amount of time in milliseconds to add to UTC to get
     * standard time in this time zone. Because this value is not
     * affected by daylight saving time, it is called <I>raw
     * offset</I>.
     * <p>
     * If an underlying <code>TimeZone</code> implementation subclass
     * supports historical GMT offset changes, the method returns the
     * raw offset value of the current date. In Honolulu, for example,
     * its raw offset changed from GMT-10:30 to GMT-10:00 in 1947, and
     * this method always returns -36000000 milliseconds (i.e., -10
     * hours).
     *
     * <p>
     *  返回添加到UTC的时间(以毫秒为单位),以获取此时区的标准时间。因为此值不受夏令时影响,因此称为<I>原始偏移量</I>。
     * <p>
     * 如果底层的<code> TimeZone </code>实现子类支持历史GMT偏移变化,该方法返回当前日期的原始偏移值。
     * 例如,在檀香山,它的原始偏移在1947年从GMT-10：30改变为GMT-10：00,并且该方法总是返回-36000000毫秒(即,-10小时)。
     * 
     * 
     * @return the amount of raw offset time in milliseconds to add to UTC.
     * @see Calendar#ZONE_OFFSET
     */
    public abstract int getRawOffset();

    /**
     * Gets the ID of this time zone.
     * <p>
     *  获取此时区的ID。
     * 
     * 
     * @return the ID of this time zone.
     */
    public String getID()
    {
        return ID;
    }

    /**
     * Sets the time zone ID. This does not change any other data in
     * the time zone object.
     * <p>
     *  设置时区ID。这不会更改时区对象中的任何其他数据。
     * 
     * 
     * @param ID the new time zone ID.
     */
    public void setID(String ID)
    {
        if (ID == null) {
            throw new NullPointerException();
        }
        this.ID = ID;
    }

    /**
     * Returns a long standard time name of this {@code TimeZone} suitable for
     * presentation to the user in the default locale.
     *
     * <p>This method is equivalent to:
     * <blockquote><pre>
     * getDisplayName(false, {@link #LONG},
     *                Locale.getDefault({@link Locale.Category#DISPLAY}))
     * </pre></blockquote>
     *
     * <p>
     *  返回适用于在默认语言环境中向用户显示的{@code TimeZone}的长标准时间名称。
     * 
     *  <p>此方法等效于：<blockquote> <pre> getDisplayName(false,{@link #LONG},Locale.getDefault({@ link Locale.Category#DISPLAY}
     * ))</pre> </blockquote>。
     * 
     * 
     * @return the human-readable name of this time zone in the default locale.
     * @since 1.2
     * @see #getDisplayName(boolean, int, Locale)
     * @see Locale#getDefault(Locale.Category)
     * @see Locale.Category
     */
    public final String getDisplayName() {
        return getDisplayName(false, LONG,
                              Locale.getDefault(Locale.Category.DISPLAY));
    }

    /**
     * Returns a long standard time name of this {@code TimeZone} suitable for
     * presentation to the user in the specified {@code locale}.
     *
     * <p>This method is equivalent to:
     * <blockquote><pre>
     * getDisplayName(false, {@link #LONG}, locale)
     * </pre></blockquote>
     *
     * <p>
     *  返回适用于在指定的{@code locale}中向用户显示的{@code TimeZone}的长标准时间名称。
     * 
     *  <p>此方法等效于：<blockquote> <pre> getDisplayName(false,{@link #LONG},locale)</pre> </blockquote>
     * 
     * 
     * @param locale the locale in which to supply the display name.
     * @return the human-readable name of this time zone in the given locale.
     * @exception NullPointerException if {@code locale} is {@code null}.
     * @since 1.2
     * @see #getDisplayName(boolean, int, Locale)
     */
    public final String getDisplayName(Locale locale) {
        return getDisplayName(false, LONG, locale);
    }

    /**
     * Returns a name in the specified {@code style} of this {@code TimeZone}
     * suitable for presentation to the user in the default locale. If the
     * specified {@code daylight} is {@code true}, a Daylight Saving Time name
     * is returned (even if this {@code TimeZone} doesn't observe Daylight Saving
     * Time). Otherwise, a Standard Time name is returned.
     *
     * <p>This method is equivalent to:
     * <blockquote><pre>
     * getDisplayName(daylight, style,
     *                Locale.getDefault({@link Locale.Category#DISPLAY}))
     * </pre></blockquote>
     *
     * <p>
     *  返回此{@code TimeZone}的指定{@code style}中的一个名称,适合在默认语言环境中向用户显示。
     * 如果指定的{@code daylight}为{@code true},则会返回夏令时名称(即使此{@code TimeZone}未遵守夏令时)。否则,将返回标准时间名称。
     * 
     *  <p>此方法等效于：<blockquote> <pre> getDisplayName(daylight,style,Locale.getDefault({@ link Locale.Category#DISPLAY}
     * ))</pre> </blockquote>。
     * 
     * 
     * @param daylight {@code true} specifying a Daylight Saving Time name, or
     *                 {@code false} specifying a Standard Time name
     * @param style either {@link #LONG} or {@link #SHORT}
     * @return the human-readable name of this time zone in the default locale.
     * @exception IllegalArgumentException if {@code style} is invalid.
     * @since 1.2
     * @see #getDisplayName(boolean, int, Locale)
     * @see Locale#getDefault(Locale.Category)
     * @see Locale.Category
     * @see java.text.DateFormatSymbols#getZoneStrings()
     */
    public final String getDisplayName(boolean daylight, int style) {
        return getDisplayName(daylight, style,
                              Locale.getDefault(Locale.Category.DISPLAY));
    }

    /**
     * Returns a name in the specified {@code style} of this {@code TimeZone}
     * suitable for presentation to the user in the specified {@code
     * locale}. If the specified {@code daylight} is {@code true}, a Daylight
     * Saving Time name is returned (even if this {@code TimeZone} doesn't
     * observe Daylight Saving Time). Otherwise, a Standard Time name is
     * returned.
     *
     * <p>When looking up a time zone name, the {@linkplain
     * ResourceBundle.Control#getCandidateLocales(String,Locale) default
     * <code>Locale</code> search path of <code>ResourceBundle</code>} derived
     * from the specified {@code locale} is used. (No {@linkplain
     * ResourceBundle.Control#getFallbackLocale(String,Locale) fallback
     * <code>Locale</code>} search is performed.) If a time zone name in any
     * {@code Locale} of the search path, including {@link Locale#ROOT}, is
     * found, the name is returned. Otherwise, a string in the
     * <a href="#NormalizedCustomID">normalized custom ID format</a> is returned.
     *
     * <p>
     * 返回此{@code TimeZone}的指定{@code style}中的一个名称,适合在指定的{@code locale}中向用户显示。
     * 如果指定的{@code daylight}为{@code true},则会返回夏令时名称(即使此{@code TimeZone}未遵守夏令时)。否则,将返回标准时间名称。
     * 
     *  <p>查找时区名称时,源自指定的<code> ResourceBundle </code>}的{@linkplain ResourceBundle.Control#getCandidateLocales(String,Locale)默认<code> Locale </code>搜索路径使用{@code locale}
     * 。
     *  (不执行{@linkplain ResourceBundle.Control#getFallbackLocale(String,Locale)fallback <code> Locale </code>}
     * 搜索。
     * )如果搜索路径的任何{@code Locale} link Locale#ROOT},则返回名称。
     * 否则,会返回<a href="#NormalizedCustomID">规范化自定义ID格式</a>的字符串。
     * 
     * 
     * @param daylight {@code true} specifying a Daylight Saving Time name, or
     *                 {@code false} specifying a Standard Time name
     * @param style either {@link #LONG} or {@link #SHORT}
     * @param locale   the locale in which to supply the display name.
     * @return the human-readable name of this time zone in the given locale.
     * @exception IllegalArgumentException if {@code style} is invalid.
     * @exception NullPointerException if {@code locale} is {@code null}.
     * @since 1.2
     * @see java.text.DateFormatSymbols#getZoneStrings()
     */
    public String getDisplayName(boolean daylight, int style, Locale locale) {
        if (style != SHORT && style != LONG) {
            throw new IllegalArgumentException("Illegal style: " + style);
        }
        String id = getID();
        String name = TimeZoneNameUtility.retrieveDisplayName(id, daylight, style, locale);
        if (name != null) {
            return name;
        }

        if (id.startsWith("GMT") && id.length() > 3) {
            char sign = id.charAt(3);
            if (sign == '+' || sign == '-') {
                return id;
            }
        }
        int offset = getRawOffset();
        if (daylight) {
            offset += getDSTSavings();
        }
        return ZoneInfoFile.toCustomID(offset);
    }

    private static String[] getDisplayNames(String id, Locale locale) {
        return TimeZoneNameUtility.retrieveDisplayNames(id, locale);
    }

    /**
     * Returns the amount of time to be added to local standard time
     * to get local wall clock time.
     *
     * <p>The default implementation returns 3600000 milliseconds
     * (i.e., one hour) if a call to {@link #useDaylightTime()}
     * returns {@code true}. Otherwise, 0 (zero) is returned.
     *
     * <p>If an underlying {@code TimeZone} implementation subclass
     * supports historical and future Daylight Saving Time schedule
     * changes, this method returns the amount of saving time of the
     * last known Daylight Saving Time rule that can be a future
     * prediction.
     *
     * <p>If the amount of saving time at any given time stamp is
     * required, construct a {@link Calendar} with this {@code
     * TimeZone} and the time stamp, and call {@link Calendar#get(int)
     * Calendar.get}{@code (}{@link Calendar#DST_OFFSET}{@code )}.
     *
     * <p>
     *  返回要添加到本地标准时间以获取本地挂钟时间的时间量。
     * 
     *  <p>如果调用{@link #useDaylightTime()}返回{@code true},默认实现将返回3600000毫秒(即一小时)。否则返回0(零)。
     * 
     *  <p>如果底层的{@code TimeZone}实现子类支持历史和未来的夏令时计划更改,此方法将返回最后一个已知的夏令时规则的可以作为未来预测的节省时间量。
     * 
     * <p>如果需要任何给定时间戳的节省时间,请使用此{@code TimeZone}和时间戳构建一个{@link日历},然后调用{@link Calendar#get(int)Calendar.get } 
     * {@ code(} {@ link Calendar#DST_OFFSET} {@ code)}。
     * 
     * 
     * @return the amount of saving time in milliseconds
     * @since 1.4
     * @see #inDaylightTime(Date)
     * @see #getOffset(long)
     * @see #getOffset(int,int,int,int,int,int)
     * @see Calendar#ZONE_OFFSET
     */
    public int getDSTSavings() {
        if (useDaylightTime()) {
            return 3600000;
        }
        return 0;
    }

    /**
     * Queries if this {@code TimeZone} uses Daylight Saving Time.
     *
     * <p>If an underlying {@code TimeZone} implementation subclass
     * supports historical and future Daylight Saving Time schedule
     * changes, this method refers to the last known Daylight Saving Time
     * rule that can be a future prediction and may not be the same as
     * the current rule. Consider calling {@link #observesDaylightTime()}
     * if the current rule should also be taken into account.
     *
     * <p>
     *  查询此{@code TimeZone}是否使用夏令时。
     * 
     *  <p>如果底层的{@code TimeZone}实现子类支持历史和将来的夏令时计划更改,则此方法引用最后一个已知的夏令时规则,该规则可以是未来的预测,并且可能与当前规则不同。
     * 如果还要考虑当前规则,请考虑调用{@link #observesDaylightTime()}。
     * 
     * 
     * @return {@code true} if this {@code TimeZone} uses Daylight Saving Time,
     *         {@code false}, otherwise.
     * @see #inDaylightTime(Date)
     * @see Calendar#DST_OFFSET
     */
    public abstract boolean useDaylightTime();

    /**
     * Returns {@code true} if this {@code TimeZone} is currently in
     * Daylight Saving Time, or if a transition from Standard Time to
     * Daylight Saving Time occurs at any future time.
     *
     * <p>The default implementation returns {@code true} if
     * {@code useDaylightTime()} or {@code inDaylightTime(new Date())}
     * returns {@code true}.
     *
     * <p>
     *  如果{@code TimeZone}当前处于夏令时,或者以后出现从标准时间到夏令时的转换,则返回{@code true}。
     * 
     *  <p>如果{@code useDaylightTime()}或{@code inDaylightTime(new Date())}返回{@code true},则默认实现将返回{@code true}
     * 。
     * 
     * 
     * @return {@code true} if this {@code TimeZone} is currently in
     * Daylight Saving Time, or if a transition from Standard Time to
     * Daylight Saving Time occurs at any future time; {@code false}
     * otherwise.
     * @since 1.7
     * @see #useDaylightTime()
     * @see #inDaylightTime(Date)
     * @see Calendar#DST_OFFSET
     */
    public boolean observesDaylightTime() {
        return useDaylightTime() || inDaylightTime(new Date());
    }

    /**
     * Queries if the given {@code date} is in Daylight Saving Time in
     * this time zone.
     *
     * <p>
     *  查询给定的{@code date}是否在此时区的夏令时。
     * 
     * 
     * @param date the given Date.
     * @return {@code true} if the given date is in Daylight Saving Time,
     *         {@code false}, otherwise.
     */
    abstract public boolean inDaylightTime(Date date);

    /**
     * Gets the <code>TimeZone</code> for the given ID.
     *
     * <p>
     *  获取给定ID的<code> TimeZone </code>。
     * 
     * 
     * @param ID the ID for a <code>TimeZone</code>, either an abbreviation
     * such as "PST", a full name such as "America/Los_Angeles", or a custom
     * ID such as "GMT-8:00". Note that the support of abbreviations is
     * for JDK 1.1.x compatibility only and full names should be used.
     *
     * @return the specified <code>TimeZone</code>, or the GMT zone if the given ID
     * cannot be understood.
     */
    public static synchronized TimeZone getTimeZone(String ID) {
        return getTimeZone(ID, true);
    }

    /**
     * Gets the {@code TimeZone} for the given {@code zoneId}.
     *
     * <p>
     *  获取给定{@code zoneId}的{@code TimeZone}。
     * 
     * 
     * @param zoneId a {@link ZoneId} from which the time zone ID is obtained
     * @return the specified {@code TimeZone}, or the GMT zone if the given ID
     *         cannot be understood.
     * @throws NullPointerException if {@code zoneId} is {@code null}
     * @since 1.8
     */
    public static TimeZone getTimeZone(ZoneId zoneId) {
        String tzid = zoneId.getId(); // throws an NPE if null
        char c = tzid.charAt(0);
        if (c == '+' || c == '-') {
            tzid = "GMT" + tzid;
        } else if (c == 'Z' && tzid.length() == 1) {
            tzid = "UTC";
        }
        return getTimeZone(tzid, true);
    }

    /**
     * Converts this {@code TimeZone} object to a {@code ZoneId}.
     *
     * <p>
     *  将此{@code TimeZone}对象转换为{@code ZoneId}。
     * 
     * 
     * @return a {@code ZoneId} representing the same time zone as this
     *         {@code TimeZone}
     * @since 1.8
     */
    public ZoneId toZoneId() {
        String id = getID();
        if (ZoneInfoFile.useOldMapping() && id.length() == 3) {
            if ("EST".equals(id))
                return ZoneId.of("America/New_York");
            if ("MST".equals(id))
                return ZoneId.of("America/Denver");
            if ("HST".equals(id))
                return ZoneId.of("America/Honolulu");
        }
        return ZoneId.of(id, ZoneId.SHORT_IDS);
    }

    private static TimeZone getTimeZone(String ID, boolean fallback) {
        TimeZone tz = ZoneInfo.getTimeZone(ID);
        if (tz == null) {
            tz = parseCustomTimeZone(ID);
            if (tz == null && fallback) {
                tz = new ZoneInfo(GMT_ID, 0);
            }
        }
        return tz;
    }

    /**
     * Gets the available IDs according to the given time zone offset in milliseconds.
     *
     * <p>
     *  根据给定的时区偏移量(以毫秒为单位)获取可用的ID。
     * 
     * 
     * @param rawOffset the given time zone GMT offset in milliseconds.
     * @return an array of IDs, where the time zone for that ID has
     * the specified GMT offset. For example, "America/Phoenix" and "America/Denver"
     * both have GMT-07:00, but differ in daylight saving behavior.
     * @see #getRawOffset()
     */
    public static synchronized String[] getAvailableIDs(int rawOffset) {
        return ZoneInfo.getAvailableIDs(rawOffset);
    }

    /**
     * Gets all the available IDs supported.
     * <p>
     *  获取支持的所有可用ID。
     * 
     * 
     * @return an array of IDs.
     */
    public static synchronized String[] getAvailableIDs() {
        return ZoneInfo.getAvailableIDs();
    }

    /**
     * Gets the platform defined TimeZone ID.
     * <p>
     *  获取平台定义的TimeZone ID。
     * 
     * 
     **/
    private static native String getSystemTimeZoneID(String javaHome);

    /**
     * Gets the custom time zone ID based on the GMT offset of the
     * platform. (e.g., "GMT+08:00")
     * <p>
     *  基于平台的GMT偏移获取自定义时区ID。 (例如,"GMT + 08：00")
     * 
     */
    private static native String getSystemGMTOffsetID();

    /**
     * Gets the default {@code TimeZone} of the Java virtual machine. If the
     * cached default {@code TimeZone} is available, its clone is returned.
     * Otherwise, the method takes the following steps to determine the default
     * time zone.
     *
     * <ul>
     * <li>Use the {@code user.timezone} property value as the default
     * time zone ID if it's available.</li>
     * <li>Detect the platform time zone ID. The source of the
     * platform time zone and ID mapping may vary with implementation.</li>
     * <li>Use {@code GMT} as the last resort if the given or detected
     * time zone ID is unknown.</li>
     * </ul>
     *
     * <p>The default {@code TimeZone} created from the ID is cached,
     * and its clone is returned. The {@code user.timezone} property
     * value is set to the ID upon return.
     *
     * <p>
     * 获取Java虚拟机的默认{@code TimeZone}。如果缓存的默认{@code TimeZone}可用,则返回其克隆。否则,该方法将执行以下步骤来确定默认时区。
     * 
     * <ul>
     *  <li>使用{@code user.timezone}属性值作为默认时区ID(如果可用)。</li> <li>检测平台时区ID。
     *  </li> <li>如果给定或检测到的时区ID未知,请使用{@code GMT}作为最后手段。</li>。
     * </ul>
     * 
     *  <p>从ID创建的默认{@code TimeZone}将被缓存,并返回其克隆。返回时,{@code user.timezone}属性值设置为ID。
     * 
     * 
     * @return the default {@code TimeZone}
     * @see #setDefault(TimeZone)
     */
    public static TimeZone getDefault() {
        return (TimeZone) getDefaultRef().clone();
    }

    /**
     * Returns the reference to the default TimeZone object. This
     * method doesn't create a clone.
     * <p>
     *  返回对默认TimeZone对象的引用。此方法不创建克隆。
     * 
     */
    static TimeZone getDefaultRef() {
        TimeZone defaultZone = defaultTimeZone;
        if (defaultZone == null) {
            // Need to initialize the default time zone.
            defaultZone = setDefaultZone();
            assert defaultZone != null;
        }
        // Don't clone here.
        return defaultZone;
    }

    private static synchronized TimeZone setDefaultZone() {
        TimeZone tz;
        // get the time zone ID from the system properties
        String zoneID = AccessController.doPrivileged(
                new GetPropertyAction("user.timezone"));

        // if the time zone ID is not set (yet), perform the
        // platform to Java time zone ID mapping.
        if (zoneID == null || zoneID.isEmpty()) {
            String javaHome = AccessController.doPrivileged(
                    new GetPropertyAction("java.home"));
            try {
                zoneID = getSystemTimeZoneID(javaHome);
                if (zoneID == null) {
                    zoneID = GMT_ID;
                }
            } catch (NullPointerException e) {
                zoneID = GMT_ID;
            }
        }

        // Get the time zone for zoneID. But not fall back to
        // "GMT" here.
        tz = getTimeZone(zoneID, false);

        if (tz == null) {
            // If the given zone ID is unknown in Java, try to
            // get the GMT-offset-based time zone ID,
            // a.k.a. custom time zone ID (e.g., "GMT-08:00").
            String gmtOffsetID = getSystemGMTOffsetID();
            if (gmtOffsetID != null) {
                zoneID = gmtOffsetID;
            }
            tz = getTimeZone(zoneID, true);
        }
        assert tz != null;

        final String id = zoneID;
        AccessController.doPrivileged(new PrivilegedAction<Void>() {
            @Override
                public Void run() {
                    System.setProperty("user.timezone", id);
                    return null;
                }
            });

        defaultTimeZone = tz;
        return tz;
    }

    /**
     * Sets the {@code TimeZone} that is returned by the {@code getDefault}
     * method. {@code zone} is cached. If {@code zone} is null, the cached
     * default {@code TimeZone} is cleared. This method doesn't change the value
     * of the {@code user.timezone} property.
     *
     * <p>
     *  设置{@code getDefault}方法返回的{@code TimeZone}。 {@code zone}已缓存。
     * 如果{@code zone}为null,则缓存的默认{@code TimeZone}将被清除。此方法不会更改{@code user.timezone}属性的值。
     * 
     * 
     * @param zone the new default {@code TimeZone}, or null
     * @throws SecurityException if the security manager's {@code checkPermission}
     *                           denies {@code PropertyPermission("user.timezone",
     *                           "write")}
     * @see #getDefault
     * @see PropertyPermission
     */
    public static void setDefault(TimeZone zone)
    {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(new PropertyPermission
                               ("user.timezone", "write"));
        }
        defaultTimeZone = zone;
    }

    /**
     * Returns true if this zone has the same rule and offset as another zone.
     * That is, if this zone differs only in ID, if at all.  Returns false
     * if the other zone is null.
     * <p>
     *  如果此区域具有与另一个区域相同的规则和偏移量,则返回true。也就是说,如果这个区域仅在ID上不同,如果有的话。如果其他区域为null,则返回false。
     * 
     * 
     * @param other the <code>TimeZone</code> object to be compared with
     * @return true if the other zone is not null and is the same as this one,
     * with the possible exception of the ID
     * @since 1.2
     */
    public boolean hasSameRules(TimeZone other) {
        return other != null && getRawOffset() == other.getRawOffset() &&
            useDaylightTime() == other.useDaylightTime();
    }

    /**
     * Creates a copy of this <code>TimeZone</code>.
     *
     * <p>
     *  创建此<code> TimeZone </code>的副本。
     * 
     * 
     * @return a clone of this <code>TimeZone</code>
     */
    public Object clone()
    {
        try {
            TimeZone other = (TimeZone) super.clone();
            other.ID = ID;
            return other;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    /**
     * The null constant as a TimeZone.
     * <p>
     *  null常量作为TimeZone。
     * 
     */
    static final TimeZone NO_TIMEZONE = null;

    // =======================privates===============================

    /**
     * The string identifier of this <code>TimeZone</code>.  This is a
     * programmatic identifier used internally to look up <code>TimeZone</code>
     * objects from the system table and also to map them to their localized
     * display names.  <code>ID</code> values are unique in the system
     * table but may not be for dynamically created zones.
     * <p>
     * 此<code> TimeZone </code>的字符串标识符。这是一个内部使用的编程标识符,用于从系统表中查找<code> TimeZone </code>对象,并将它们映射到其本地化的显示名称。
     *  <code> ID </code>值在系统表中是唯一的,但不能用于动态创建的区域。
     * 
     * 
     * @serial
     */
    private String           ID;
    private static volatile TimeZone defaultTimeZone;

    static final String         GMT_ID        = "GMT";
    private static final int    GMT_ID_LENGTH = 3;

    // a static TimeZone we can reference if no AppContext is in place
    private static volatile TimeZone mainAppContextDefault;

    /**
     * Parses a custom time zone identifier and returns a corresponding zone.
     * This method doesn't support the RFC 822 time zone format. (e.g., +hhmm)
     *
     * <p>
     * 
     * @param id a string of the <a href="#CustomID">custom ID form</a>.
     * @return a newly created TimeZone with the given offset and
     * no daylight saving time, or null if the id cannot be parsed.
     */
    private static final TimeZone parseCustomTimeZone(String id) {
        int length;

        // Error if the length of id isn't long enough or id doesn't
        // start with "GMT".
        if ((length = id.length()) < (GMT_ID_LENGTH + 2) ||
            id.indexOf(GMT_ID) != 0) {
            return null;
        }

        ZoneInfo zi;

        // First, we try to find it in the cache with the given
        // id. Even the id is not normalized, the returned ZoneInfo
        // should have its normalized id.
        zi = ZoneInfoFile.getZoneInfo(id);
        if (zi != null) {
            return zi;
        }

        int index = GMT_ID_LENGTH;
        boolean negative = false;
        char c = id.charAt(index++);
        if (c == '-') {
            negative = true;
        } else if (c != '+') {
            return null;
        }

        int hours = 0;
        int num = 0;
        int countDelim = 0;
        int len = 0;
        while (index < length) {
            c = id.charAt(index++);
            if (c == ':') {
                if (countDelim > 0) {
                    return null;
                }
                if (len > 2) {
                    return null;
                }
                hours = num;
                countDelim++;
                num = 0;
                len = 0;
                continue;
            }
            if (c < '0' || c > '9') {
                return null;
            }
            num = num * 10 + (c - '0');
            len++;
        }
        if (index != length) {
            return null;
        }
        if (countDelim == 0) {
            if (len <= 2) {
                hours = num;
                num = 0;
            } else {
                hours = num / 100;
                num %= 100;
            }
        } else {
            if (len != 2) {
                return null;
            }
        }
        if (hours > 23 || num > 59) {
            return null;
        }
        int gmtOffset =  (hours * 60 + num) * 60 * 1000;

        if (gmtOffset == 0) {
            zi = ZoneInfoFile.getZoneInfo(GMT_ID);
            if (negative) {
                zi.setID("GMT-00:00");
            } else {
                zi.setID("GMT+00:00");
            }
        } else {
            zi = ZoneInfoFile.getCustomTimeZone(id, negative ? -gmtOffset : gmtOffset);
        }
        return zi;
    }
}
