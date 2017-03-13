/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1994, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.util;

import java.text.DateFormat;
import java.time.LocalDate;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.lang.ref.SoftReference;
import java.time.Instant;
import sun.util.calendar.BaseCalendar;
import sun.util.calendar.CalendarDate;
import sun.util.calendar.CalendarSystem;
import sun.util.calendar.CalendarUtils;
import sun.util.calendar.Era;
import sun.util.calendar.Gregorian;
import sun.util.calendar.ZoneInfo;

/**
 * The class <code>Date</code> represents a specific instant
 * in time, with millisecond precision.
 * <p>
 * Prior to JDK&nbsp;1.1, the class <code>Date</code> had two additional
 * functions.  It allowed the interpretation of dates as year, month, day, hour,
 * minute, and second values.  It also allowed the formatting and parsing
 * of date strings.  Unfortunately, the API for these functions was not
 * amenable to internationalization.  As of JDK&nbsp;1.1, the
 * <code>Calendar</code> class should be used to convert between dates and time
 * fields and the <code>DateFormat</code> class should be used to format and
 * parse date strings.
 * The corresponding methods in <code>Date</code> are deprecated.
 * <p>
 * Although the <code>Date</code> class is intended to reflect
 * coordinated universal time (UTC), it may not do so exactly,
 * depending on the host environment of the Java Virtual Machine.
 * Nearly all modern operating systems assume that 1&nbsp;day&nbsp;=
 * 24&nbsp;&times;&nbsp;60&nbsp;&times;&nbsp;60&nbsp;= 86400 seconds
 * in all cases. In UTC, however, about once every year or two there
 * is an extra second, called a "leap second." The leap
 * second is always added as the last second of the day, and always
 * on December 31 or June 30. For example, the last minute of the
 * year 1995 was 61 seconds long, thanks to an added leap second.
 * Most computer clocks are not accurate enough to be able to reflect
 * the leap-second distinction.
 * <p>
 * Some computer standards are defined in terms of Greenwich mean
 * time (GMT), which is equivalent to universal time (UT).  GMT is
 * the "civil" name for the standard; UT is the
 * "scientific" name for the same standard. The
 * distinction between UTC and UT is that UTC is based on an atomic
 * clock and UT is based on astronomical observations, which for all
 * practical purposes is an invisibly fine hair to split. Because the
 * earth's rotation is not uniform (it slows down and speeds up
 * in complicated ways), UT does not always flow uniformly. Leap
 * seconds are introduced as needed into UTC so as to keep UTC within
 * 0.9 seconds of UT1, which is a version of UT with certain
 * corrections applied. There are other time and date systems as
 * well; for example, the time scale used by the satellite-based
 * global positioning system (GPS) is synchronized to UTC but is
 * <i>not</i> adjusted for leap seconds. An interesting source of
 * further information is the U.S. Naval Observatory, particularly
 * the Directorate of Time at:
 * <blockquote><pre>
 *     <a href=http://tycho.usno.navy.mil>http://tycho.usno.navy.mil</a>
 * </pre></blockquote>
 * <p>
 * and their definitions of "Systems of Time" at:
 * <blockquote><pre>
 *     <a href=http://tycho.usno.navy.mil/systime.html>http://tycho.usno.navy.mil/systime.html</a>
 * </pre></blockquote>
 * <p>
 * In all methods of class <code>Date</code> that accept or return
 * year, month, date, hours, minutes, and seconds values, the
 * following representations are used:
 * <ul>
 * <li>A year <i>y</i> is represented by the integer
 *     <i>y</i>&nbsp;<code>-&nbsp;1900</code>.
 * <li>A month is represented by an integer from 0 to 11; 0 is January,
 *     1 is February, and so forth; thus 11 is December.
 * <li>A date (day of month) is represented by an integer from 1 to 31
 *     in the usual manner.
 * <li>An hour is represented by an integer from 0 to 23. Thus, the hour
 *     from midnight to 1 a.m. is hour 0, and the hour from noon to 1
 *     p.m. is hour 12.
 * <li>A minute is represented by an integer from 0 to 59 in the usual manner.
 * <li>A second is represented by an integer from 0 to 61; the values 60 and
 *     61 occur only for leap seconds and even then only in Java
 *     implementations that actually track leap seconds correctly. Because
 *     of the manner in which leap seconds are currently introduced, it is
 *     extremely unlikely that two leap seconds will occur in the same
 *     minute, but this specification follows the date and time conventions
 *     for ISO C.
 * </ul>
 * <p>
 * In all cases, arguments given to methods for these purposes need
 * not fall within the indicated ranges; for example, a date may be
 * specified as January 32 and is interpreted as meaning February 1.
 *
 * <p>
 *  类<code> Date </code>表示时间上的特定时刻,具有毫秒精度
 * <p>
 * 在JDK 11之前,类<code> Date </code>有两个附加函数它允许将日期解释为年,月,日,小时,分钟和秒值它还允许格式化和解析日期字符串不幸的是,这些函数的API不适合国际化从JDK 11
 * 开始,<code> Calendar </code>类应该用于在日期和时间字段之间转换,而<code> DateFormat </code>用于格式化和解析日期字符串<code> Date </code>
 * 中的相应方法已弃用。
 * <p>
 * 虽然<code> Date </code>类旨在反映协调的世界时间(UTC),但它可能不会完全这样做,这取决于Java虚拟机的主机环境。
 * 几乎所有现代操作系统都假设1& = 24&nbsp;&times;&nbsp; 60&nbsp;&times;&nbsp; 60&nbsp; = 86400秒在所有情况下在UTC中,然而,大约每年或每两
 * 年有一个额外的秒,称为"闰秒"作为一天的最后一秒,总是在12月31日或6月30日例如,1995年的最后一分钟是61秒长,由于增加闰秒大多数电脑时钟不够准确,能够反映闰秒区分。
 * 虽然<code> Date </code>类旨在反映协调的世界时间(UTC),但它可能不会完全这样做,这取决于Java虚拟机的主机环境。
 * <p>
 * 一些计算机标准是根据格林威治平均时间(GMT)定义的,相当于通用时间(UT)GMT是标准的"民事"名称; UT是同一标准的"科学"名称UTC和UT之间的区别是UTC是基于原子钟,UT是基于天文观测,这对
 * 于所有实际目的是一个不可见的细毛发分裂因为地球的旋转是不均匀的(它减慢并且以复杂的方式加速),UT不总是均匀地流动。
 * 闰秒根据需要被引入到UTC中,以便将UTC保持在UT1的09秒内,UT1是具有某些校正的UT的版本还有其他时间和日期系统;例如,基于卫星的全球定位系统(GPS)使用的时间标度被同步到UTC,但是不针对闰
 * 秒进行调整。
 * 进一步信息的有趣来源是美国海军天文台,特别是时间董事会：<blockquote> <pre> <a href=http://tychousnonavymil> http：// tychousnonavy
 * mil </a> </pre> </blockquote>。
 * <p>
 * 及其对"时间系统"的定义：<blockquote> <pre> <a href=http://tychousnonavymil/systimehtml> http：// tychousnonavymil
 *  / systimehtml </a> </pre> </blockquote>。
 * <p>
 *  在接受或返回年,月,日,小时,分钟和秒值的<code> Date </code>类的所有方法中,使用以下表示法：
 * <ul>
 * <li>年<i> y </i>以整数表示<i> y </i>&nbsp; <code>  - &nbsp; 1900 </code> <li> 0〜11; 0是1月,1是2月,等等;因此11是十二月<li>
 * 日期(月中)以通常的方式由1到31的整数表示<li>小时由从0到23的整数表示。
 * 因此,从午夜到上午1点的小时是小时0,并且从中午到下午1点的小时是小时12 <li>以通常的方式用从0到59的整数表示分钟<li>第二个由从0到61的整数表示;值60和61仅出现闰秒,甚至只有在实际正确
 * 跟踪闰秒的Java实现中才会出现由于当前引入闰秒的方式,极不可能在同一分钟内出现两个闰秒,但本规范遵循ISO C的日期和时间惯例。
 * </ul>
 * <p>
 * 在所有情况下,给予用于这些目的的方法的论证不需要落在所指示的范围内;例如,日期可以被指定为1月32日,并且被解释为意味着2月1日
 * 
 * 
 * @author  James Gosling
 * @author  Arthur van Hoff
 * @author  Alan Liu
 * @see     java.text.DateFormat
 * @see     java.util.Calendar
 * @see     java.util.TimeZone
 * @since   JDK1.0
 */
public class Date
    implements java.io.Serializable, Cloneable, Comparable<Date>
{
    private static final BaseCalendar gcal =
                                CalendarSystem.getGregorianCalendar();
    private static BaseCalendar jcal;

    private transient long fastTime;

    /*
     * If cdate is null, then fastTime indicates the time in millis.
     * If cdate.isNormalized() is true, then fastTime and cdate are in
     * synch. Otherwise, fastTime is ignored, and cdate indicates the
     * time.
     * <p>
     *  如果cdate为null,则fastTime指示以毫为单位的时间。如果cdateisNormalized()为true,则fastTime和cdate处于同步状态。
     * 否则,fastTime被忽略,cdate指示时间。
     * 
     */
    private transient BaseCalendar.Date cdate;

    // Initialized just before the value is used. See parse().
    private static int defaultCenturyStart;

    /* use serialVersionUID from modified java.util.Date for
     * interoperability with JDK1.1. The Date was modified to write
     * and read only the UTC time.
     * <p>
     *  与JDK11的互操作性修改日期以写入和读取UTC时间
     * 
     */
    private static final long serialVersionUID = 7523967970034938905L;

    /**
     * Allocates a <code>Date</code> object and initializes it so that
     * it represents the time at which it was allocated, measured to the
     * nearest millisecond.
     *
     * <p>
     *  分配<code> Date </code>对象并对其进行初始化,以便表示分配时间,以最近的毫秒为单位
     * 
     * 
     * @see     java.lang.System#currentTimeMillis()
     */
    public Date() {
        this(System.currentTimeMillis());
    }

    /**
     * Allocates a <code>Date</code> object and initializes it to
     * represent the specified number of milliseconds since the
     * standard base time known as "the epoch", namely January 1,
     * 1970, 00:00:00 GMT.
     *
     * <p>
     * 分配<code> Date </code>对象并将其初始化以表示自称为"时代"的标准基准时间以来的指定毫秒数,即1970年1月1日,00:00:00 GMT
     * 
     * 
     * @param   date   the milliseconds since January 1, 1970, 00:00:00 GMT.
     * @see     java.lang.System#currentTimeMillis()
     */
    public Date(long date) {
        fastTime = date;
    }

    /**
     * Allocates a <code>Date</code> object and initializes it so that
     * it represents midnight, local time, at the beginning of the day
     * specified by the <code>year</code>, <code>month</code>, and
     * <code>date</code> arguments.
     *
     * <p>
     *  分配<code> Date </code>对象并将其初始化,以便它表示由<code> year </code>,<code> month </code>指定的日期开始的午夜,和<code> date 
     * </code>参数。
     * 
     * 
     * @param   year    the year minus 1900.
     * @param   month   the month between 0-11.
     * @param   date    the day of the month between 1-31.
     * @see     java.util.Calendar
     * @deprecated As of JDK version 1.1,
     * replaced by <code>Calendar.set(year + 1900, month, date)</code>
     * or <code>GregorianCalendar(year + 1900, month, date)</code>.
     */
    @Deprecated
    public Date(int year, int month, int date) {
        this(year, month, date, 0, 0, 0);
    }

    /**
     * Allocates a <code>Date</code> object and initializes it so that
     * it represents the instant at the start of the minute specified by
     * the <code>year</code>, <code>month</code>, <code>date</code>,
     * <code>hrs</code>, and <code>min</code> arguments, in the local
     * time zone.
     *
     * <p>
     *  分配<code> Date </code>对象并初始化它,以便它表示<code> year </code>,<code> month </code>,<code>日期</code>,<code> hr
     * s </code>和<code> min </code>。
     * 
     * 
     * @param   year    the year minus 1900.
     * @param   month   the month between 0-11.
     * @param   date    the day of the month between 1-31.
     * @param   hrs     the hours between 0-23.
     * @param   min     the minutes between 0-59.
     * @see     java.util.Calendar
     * @deprecated As of JDK version 1.1,
     * replaced by <code>Calendar.set(year + 1900, month, date,
     * hrs, min)</code> or <code>GregorianCalendar(year + 1900,
     * month, date, hrs, min)</code>.
     */
    @Deprecated
    public Date(int year, int month, int date, int hrs, int min) {
        this(year, month, date, hrs, min, 0);
    }

    /**
     * Allocates a <code>Date</code> object and initializes it so that
     * it represents the instant at the start of the second specified
     * by the <code>year</code>, <code>month</code>, <code>date</code>,
     * <code>hrs</code>, <code>min</code>, and <code>sec</code> arguments,
     * in the local time zone.
     *
     * <p>
     * 分配<code> Date </code>对象并初始化它,以便它表示<code> year </code>,<code> month </code>,<code>日期</code>,<code> hrs
     *  </code>,<code> min </code>和<code> sec <。
     * 
     * 
     * @param   year    the year minus 1900.
     * @param   month   the month between 0-11.
     * @param   date    the day of the month between 1-31.
     * @param   hrs     the hours between 0-23.
     * @param   min     the minutes between 0-59.
     * @param   sec     the seconds between 0-59.
     * @see     java.util.Calendar
     * @deprecated As of JDK version 1.1,
     * replaced by <code>Calendar.set(year + 1900, month, date,
     * hrs, min, sec)</code> or <code>GregorianCalendar(year + 1900,
     * month, date, hrs, min, sec)</code>.
     */
    @Deprecated
    public Date(int year, int month, int date, int hrs, int min, int sec) {
        int y = year + 1900;
        // month is 0-based. So we have to normalize month to support Long.MAX_VALUE.
        if (month >= 12) {
            y += month / 12;
            month %= 12;
        } else if (month < 0) {
            y += CalendarUtils.floorDivide(month, 12);
            month = CalendarUtils.mod(month, 12);
        }
        BaseCalendar cal = getCalendarSystem(y);
        cdate = (BaseCalendar.Date) cal.newCalendarDate(TimeZone.getDefaultRef());
        cdate.setNormalizedDate(y, month + 1, date).setTimeOfDay(hrs, min, sec, 0);
        getTimeImpl();
        cdate = null;
    }

    /**
     * Allocates a <code>Date</code> object and initializes it so that
     * it represents the date and time indicated by the string
     * <code>s</code>, which is interpreted as if by the
     * {@link Date#parse} method.
     *
     * <p>
     *  分配<code> Date </code>对象并对其进行初始化,以便表示由字符串<code> s </code>指示的日期和时间,这被解释为{@link Date#parse}方法
     * 
     * 
     * @param   s   a string representation of the date.
     * @see     java.text.DateFormat
     * @see     java.util.Date#parse(java.lang.String)
     * @deprecated As of JDK version 1.1,
     * replaced by <code>DateFormat.parse(String s)</code>.
     */
    @Deprecated
    public Date(String s) {
        this(parse(s));
    }

    /**
     * Return a copy of this object.
     * <p>
     *  返回此对象的副本
     * 
     */
    public Object clone() {
        Date d = null;
        try {
            d = (Date)super.clone();
            if (cdate != null) {
                d.cdate = (BaseCalendar.Date) cdate.clone();
            }
        } catch (CloneNotSupportedException e) {} // Won't happen
        return d;
    }

    /**
     * Determines the date and time based on the arguments. The
     * arguments are interpreted as a year, month, day of the month,
     * hour of the day, minute within the hour, and second within the
     * minute, exactly as for the <tt>Date</tt> constructor with six
     * arguments, except that the arguments are interpreted relative
     * to UTC rather than to the local time zone. The time indicated is
     * returned represented as the distance, measured in milliseconds,
     * of that time from the epoch (00:00:00 GMT on January 1, 1970).
     *
     * <p>
     * 根据参数确定日期和时间参数解释为年,月,日,时,分,分,秒,与<tt>日期</tt>构造函数有六个参数,除了参数被解释为相对于UTC而不是本地时区所指示的时间被表示为从时代开始的那段时间的距离(以毫秒计
     * )(00:00:00 GMT 1970年1月1日)。
     * 
     * 
     * @param   year    the year minus 1900.
     * @param   month   the month between 0-11.
     * @param   date    the day of the month between 1-31.
     * @param   hrs     the hours between 0-23.
     * @param   min     the minutes between 0-59.
     * @param   sec     the seconds between 0-59.
     * @return  the number of milliseconds since January 1, 1970, 00:00:00 GMT for
     *          the date and time specified by the arguments.
     * @see     java.util.Calendar
     * @deprecated As of JDK version 1.1,
     * replaced by <code>Calendar.set(year + 1900, month, date,
     * hrs, min, sec)</code> or <code>GregorianCalendar(year + 1900,
     * month, date, hrs, min, sec)</code>, using a UTC
     * <code>TimeZone</code>, followed by <code>Calendar.getTime().getTime()</code>.
     */
    @Deprecated
    public static long UTC(int year, int month, int date,
                           int hrs, int min, int sec) {
        int y = year + 1900;
        // month is 0-based. So we have to normalize month to support Long.MAX_VALUE.
        if (month >= 12) {
            y += month / 12;
            month %= 12;
        } else if (month < 0) {
            y += CalendarUtils.floorDivide(month, 12);
            month = CalendarUtils.mod(month, 12);
        }
        int m = month + 1;
        BaseCalendar cal = getCalendarSystem(y);
        BaseCalendar.Date udate = (BaseCalendar.Date) cal.newCalendarDate(null);
        udate.setNormalizedDate(y, m, date).setTimeOfDay(hrs, min, sec, 0);

        // Use a Date instance to perform normalization. Its fastTime
        // is the UTC value after the normalization.
        Date d = new Date(0);
        d.normalize(udate);
        return d.fastTime;
    }

    /**
     * Attempts to interpret the string <tt>s</tt> as a representation
     * of a date and time. If the attempt is successful, the time
     * indicated is returned represented as the distance, measured in
     * milliseconds, of that time from the epoch (00:00:00 GMT on
     * January 1, 1970). If the attempt fails, an
     * <tt>IllegalArgumentException</tt> is thrown.
     * <p>
     * It accepts many syntaxes; in particular, it recognizes the IETF
     * standard date syntax: "Sat, 12 Aug 1995 13:30:00 GMT". It also
     * understands the continental U.S. time-zone abbreviations, but for
     * general use, a time-zone offset should be used: "Sat, 12 Aug 1995
     * 13:30:00 GMT+0430" (4 hours, 30 minutes west of the Greenwich
     * meridian). If no time zone is specified, the local time zone is
     * assumed. GMT and UTC are considered equivalent.
     * <p>
     * The string <tt>s</tt> is processed from left to right, looking for
     * data of interest. Any material in <tt>s</tt> that is within the
     * ASCII parenthesis characters <tt>(</tt> and <tt>)</tt> is ignored.
     * Parentheses may be nested. Otherwise, the only characters permitted
     * within <tt>s</tt> are these ASCII characters:
     * <blockquote><pre>
     * abcdefghijklmnopqrstuvwxyz
     * ABCDEFGHIJKLMNOPQRSTUVWXYZ
     * 0123456789,+-:/</pre></blockquote>
     * and whitespace characters.<p>
     * A consecutive sequence of decimal digits is treated as a decimal
     * number:<ul>
     * <li>If a number is preceded by <tt>+</tt> or <tt>-</tt> and a year
     *     has already been recognized, then the number is a time-zone
     *     offset. If the number is less than 24, it is an offset measured
     *     in hours. Otherwise, it is regarded as an offset in minutes,
     *     expressed in 24-hour time format without punctuation. A
     *     preceding <tt>-</tt> means a westward offset. Time zone offsets
     *     are always relative to UTC (Greenwich). Thus, for example,
     *     <tt>-5</tt> occurring in the string would mean "five hours west
     *     of Greenwich" and <tt>+0430</tt> would mean "four hours and
     *     thirty minutes east of Greenwich." It is permitted for the
     *     string to specify <tt>GMT</tt>, <tt>UT</tt>, or <tt>UTC</tt>
     *     redundantly-for example, <tt>GMT-5</tt> or <tt>utc+0430</tt>.
     * <li>The number is regarded as a year number if one of the
     *     following conditions is true:
     * <ul>
     *     <li>The number is equal to or greater than 70 and followed by a
     *         space, comma, slash, or end of string
     *     <li>The number is less than 70, and both a month and a day of
     *         the month have already been recognized</li>
     * </ul>
     *     If the recognized year number is less than 100, it is
     *     interpreted as an abbreviated year relative to a century of
     *     which dates are within 80 years before and 19 years after
     *     the time when the Date class is initialized.
     *     After adjusting the year number, 1900 is subtracted from
     *     it. For example, if the current year is 1999 then years in
     *     the range 19 to 99 are assumed to mean 1919 to 1999, while
     *     years from 0 to 18 are assumed to mean 2000 to 2018.  Note
     *     that this is slightly different from the interpretation of
     *     years less than 100 that is used in {@link java.text.SimpleDateFormat}.
     * <li>If the number is followed by a colon, it is regarded as an hour,
     *     unless an hour has already been recognized, in which case it is
     *     regarded as a minute.
     * <li>If the number is followed by a slash, it is regarded as a month
     *     (it is decreased by 1 to produce a number in the range <tt>0</tt>
     *     to <tt>11</tt>), unless a month has already been recognized, in
     *     which case it is regarded as a day of the month.
     * <li>If the number is followed by whitespace, a comma, a hyphen, or
     *     end of string, then if an hour has been recognized but not a
     *     minute, it is regarded as a minute; otherwise, if a minute has
     *     been recognized but not a second, it is regarded as a second;
     *     otherwise, it is regarded as a day of the month. </ul><p>
     * A consecutive sequence of letters is regarded as a word and treated
     * as follows:<ul>
     * <li>A word that matches <tt>AM</tt>, ignoring case, is ignored (but
     *     the parse fails if an hour has not been recognized or is less
     *     than <tt>1</tt> or greater than <tt>12</tt>).
     * <li>A word that matches <tt>PM</tt>, ignoring case, adds <tt>12</tt>
     *     to the hour (but the parse fails if an hour has not been
     *     recognized or is less than <tt>1</tt> or greater than <tt>12</tt>).
     * <li>Any word that matches any prefix of <tt>SUNDAY, MONDAY, TUESDAY,
     *     WEDNESDAY, THURSDAY, FRIDAY</tt>, or <tt>SATURDAY</tt>, ignoring
     *     case, is ignored. For example, <tt>sat, Friday, TUE</tt>, and
     *     <tt>Thurs</tt> are ignored.
     * <li>Otherwise, any word that matches any prefix of <tt>JANUARY,
     *     FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER,
     *     OCTOBER, NOVEMBER</tt>, or <tt>DECEMBER</tt>, ignoring case, and
     *     considering them in the order given here, is recognized as
     *     specifying a month and is converted to a number (<tt>0</tt> to
     *     <tt>11</tt>). For example, <tt>aug, Sept, april</tt>, and
     *     <tt>NOV</tt> are recognized as months. So is <tt>Ma</tt>, which
     *     is recognized as <tt>MARCH</tt>, not <tt>MAY</tt>.
     * <li>Any word that matches <tt>GMT, UT</tt>, or <tt>UTC</tt>, ignoring
     *     case, is treated as referring to UTC.
     * <li>Any word that matches <tt>EST, CST, MST</tt>, or <tt>PST</tt>,
     *     ignoring case, is recognized as referring to the time zone in
     *     North America that is five, six, seven, or eight hours west of
     *     Greenwich, respectively. Any word that matches <tt>EDT, CDT,
     *     MDT</tt>, or <tt>PDT</tt>, ignoring case, is recognized as
     *     referring to the same time zone, respectively, during daylight
     *     saving time.</ul><p>
     * Once the entire string s has been scanned, it is converted to a time
     * result in one of two ways. If a time zone or time-zone offset has been
     * recognized, then the year, month, day of month, hour, minute, and
     * second are interpreted in UTC and then the time-zone offset is
     * applied. Otherwise, the year, month, day of month, hour, minute, and
     * second are interpreted in the local time zone.
     *
     * <p>
     * 尝试将<tt> s </tt>解释为日期和时间的表示如果尝试成功,则返回的时间表示为从时间(00：00：00：00： 00:00 GMT on January 1,1970)如果尝试失败,则会抛出<tt>
     *  IllegalArgumentException </tt>。
     * <p>
     *  它接受许多语法;特别是它承认IETF标准日期语法："Sat,12 Aug 1995 13:30:00 GMT"它还理解美国大陆的时区缩写,但是对于一般使用,应该使用时区偏移： Sat,12 Aug 1
     * 995 13:30:00 GMT + 0430"(4小时,格林威治子午线以西30分钟)如果没有指定时区,则假定本地时区为GMT和UTC,。
     * <p>
     * 从左到右处理字符串<tt> s </tt>,寻找感兴趣的数据<tt> s </tt>中的任何材料,它在ASCII括号字符<tt>(</tt> tt>)</tt>允许的字符包括以下ASCII字符：<blockquote>
     *  <pre> abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ 0123456789,+  - ：/ </pre > </blockquote>
     * 和空格字符<p>连续的十进制数字序列被视为十进制数字：<ul> <li>如果数字前面有<tt> + </tt>或<tt>  -  < / tt>,并且一年已经被识别,则该数字是时区偏移量如果该数量小于2
     * 4,则它是以小时为单位测量的偏移量否则,它被认为是以分钟为单位的偏移,以没有标点的24小时时间格式表示。
     * 在<tt>  -  </tt>之前的表示向西偏移时区偏移总是相对于UTC(格林威治)在字符串中出现的<tt> -5 </tt>意味着"格林威治以西五小时",而<tt> +0430 </tt>将意味着"格
     * 林威治以东四小时三十分钟"以冗余方式指定<tt> GMT </tt>,<tt> UT </tt>或<tt> UTC </tt>(例如,<tt> GMT-5 </tt>或<tt> utc + 0430 </tt>
     *  <li>如果满足以下条件之一,则该数字被视为年份数：。
     * <ul>
     * <li>数字等于或大于70,后跟空格,逗号,斜杠或字符串结尾<li>数字小于70,且月份和月份都已被识别</li>
     * </ul>
     * 如果识别的年份数小于100,则将其解释为相对于一个世纪的缩写年份,其中日期在Date类初始化之前的80年之前和19年之后在调整年份之后,减去1900例如,如果当年是1999年,那么19到99年的年份假定
     * 为1919年到1999年,而0到18年的年份假定为2000年到2018年。
     * 注意,这与解释在{@link javatextSimpleDateFormat}中使用的小于100的年数<li>如果数字后面跟有冒号,则将其视为一小时,除非已经识别了一小时,在这种情况下,它被视为一分钟
     * <li>如果数字后面跟着斜杠,则视为一个月(将其减小1以产生<tt> 0 </tt>至<tt> 11 </tt>的数字)除非已经识别了一个月,在这种情况下,它被视为一个月中的一天<li>如果数字后面跟有
     * 空格,逗号,连字符或字符串结尾,则如果一小时已被识别,不是一分钟,它被认为是一分钟;否则,如果已经识别出分钟但不是秒,则将其视为秒;否则,它被认为是一个月的一天</ul> <p>连续的字母序列被视为一个
     * 字,并按以下方式处理：<ul> <li>忽略与忽略大小写的<tt> AM </tt>匹配的字词如果一小时未被识别或小于<tt> 1 </tt>或大于<tt> 12 </tt>则失败)<li>与<tt> 
     * PM </tt>匹配的单词,则向小时添加<tt> 12 </tt>(但如果一小时未被识别或小于<tt> 1 </tt>或大于<tt> 12 </tt> li>忽略与<tt>任何前缀<SUNDAY,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY </tt>
     * 或<tt> SATURDAY </tt>(忽略大小写)相符的任何字词例如,<tt> sat ,Friday,TUE </tt>和<tt> Thursday </tt>将被忽略<li>否则,任何与<tt>
     * 任何前缀</tt>或<tt>十二月</tt>匹配的字词</tt> ,忽略大小写,并且按照此处给出的顺序考虑它们被识别为指定一个月份并转换为一个数字(<tt> 0 </tt>至<tt> 11 </tt>)
     * 例如,<tt> aug,9月,april </tt>和<tt> NOV </tt>被识别为月份,因此是<tt> Ma </tt>,被识别为<tt> MARCH </tt>,而不是<tt > MAY </tt>
     *  <li>任何符合<tt> GMT,UT </tt>或<tt> UTC </tt>且忽略大小写的字词,<li>任何与<tt> EST,CST,MST </tt>或<tt> PST </tt>(忽略大小写
     * )相符的字词,都会被视为指北美时区为五,六,分别在格林威治以西七,八或八小时内任何符合<tt> EDT,CDT,MDT </tt>或<tt> PDT </tt>且忽略大小写的字词被视为指同一时区,分别在
     * 夏令时期间</ul> <p>一旦整个字符串已被扫描,它将被转换为两种方式之一的时间结果如果已识别时区或时区偏移,则年,月,日,时,分和秒以UTC解释,然后应用时区偏移量。
     * 否则,将按当地时区解释年,月,日,时,分和秒。
     * 
     * 
     * @param   s   a string to be parsed as a date.
     * @return  the number of milliseconds since January 1, 1970, 00:00:00 GMT
     *          represented by the string argument.
     * @see     java.text.DateFormat
     * @deprecated As of JDK version 1.1,
     * replaced by <code>DateFormat.parse(String s)</code>.
     */
    @Deprecated
    public static long parse(String s) {
        int year = Integer.MIN_VALUE;
        int mon = -1;
        int mday = -1;
        int hour = -1;
        int min = -1;
        int sec = -1;
        int millis = -1;
        int c = -1;
        int i = 0;
        int n = -1;
        int wst = -1;
        int tzoffset = -1;
        int prevc = 0;
    syntax:
        {
            if (s == null)
                break syntax;
            int limit = s.length();
            while (i < limit) {
                c = s.charAt(i);
                i++;
                if (c <= ' ' || c == ',')
                    continue;
                if (c == '(') { // skip comments
                    int depth = 1;
                    while (i < limit) {
                        c = s.charAt(i);
                        i++;
                        if (c == '(') depth++;
                        else if (c == ')')
                            if (--depth <= 0)
                                break;
                    }
                    continue;
                }
                if ('0' <= c && c <= '9') {
                    n = c - '0';
                    while (i < limit && '0' <= (c = s.charAt(i)) && c <= '9') {
                        n = n * 10 + c - '0';
                        i++;
                    }
                    if (prevc == '+' || prevc == '-' && year != Integer.MIN_VALUE) {
                        // timezone offset
                        if (n < 24)
                            n = n * 60; // EG. "GMT-3"
                        else
                            n = n % 100 + n / 100 * 60; // eg "GMT-0430"
                        if (prevc == '+')   // plus means east of GMT
                            n = -n;
                        if (tzoffset != 0 && tzoffset != -1)
                            break syntax;
                        tzoffset = n;
                    } else if (n >= 70)
                        if (year != Integer.MIN_VALUE)
                            break syntax;
                        else if (c <= ' ' || c == ',' || c == '/' || i >= limit)
                            // year = n < 1900 ? n : n - 1900;
                            year = n;
                        else
                            break syntax;
                    else if (c == ':')
                        if (hour < 0)
                            hour = (byte) n;
                        else if (min < 0)
                            min = (byte) n;
                        else
                            break syntax;
                    else if (c == '/')
                        if (mon < 0)
                            mon = (byte) (n - 1);
                        else if (mday < 0)
                            mday = (byte) n;
                        else
                            break syntax;
                    else if (i < limit && c != ',' && c > ' ' && c != '-')
                        break syntax;
                    else if (hour >= 0 && min < 0)
                        min = (byte) n;
                    else if (min >= 0 && sec < 0)
                        sec = (byte) n;
                    else if (mday < 0)
                        mday = (byte) n;
                    // Handle two-digit years < 70 (70-99 handled above).
                    else if (year == Integer.MIN_VALUE && mon >= 0 && mday >= 0)
                        year = n;
                    else
                        break syntax;
                    prevc = 0;
                } else if (c == '/' || c == ':' || c == '+' || c == '-')
                    prevc = c;
                else {
                    int st = i - 1;
                    while (i < limit) {
                        c = s.charAt(i);
                        if (!('A' <= c && c <= 'Z' || 'a' <= c && c <= 'z'))
                            break;
                        i++;
                    }
                    if (i <= st + 1)
                        break syntax;
                    int k;
                    for (k = wtb.length; --k >= 0;)
                        if (wtb[k].regionMatches(true, 0, s, st, i - st)) {
                            int action = ttb[k];
                            if (action != 0) {
                                if (action == 1) {  // pm
                                    if (hour > 12 || hour < 1)
                                        break syntax;
                                    else if (hour < 12)
                                        hour += 12;
                                } else if (action == 14) {  // am
                                    if (hour > 12 || hour < 1)
                                        break syntax;
                                    else if (hour == 12)
                                        hour = 0;
                                } else if (action <= 13) {  // month!
                                    if (mon < 0)
                                        mon = (byte) (action - 2);
                                    else
                                        break syntax;
                                } else {
                                    tzoffset = action - 10000;
                                }
                            }
                            break;
                        }
                    if (k < 0)
                        break syntax;
                    prevc = 0;
                }
            }
            if (year == Integer.MIN_VALUE || mon < 0 || mday < 0)
                break syntax;
            // Parse 2-digit years within the correct default century.
            if (year < 100) {
                synchronized (Date.class) {
                    if (defaultCenturyStart == 0) {
                        defaultCenturyStart = gcal.getCalendarDate().getYear() - 80;
                    }
                }
                year += (defaultCenturyStart / 100) * 100;
                if (year < defaultCenturyStart) year += 100;
            }
            if (sec < 0)
                sec = 0;
            if (min < 0)
                min = 0;
            if (hour < 0)
                hour = 0;
            BaseCalendar cal = getCalendarSystem(year);
            if (tzoffset == -1)  { // no time zone specified, have to use local
                BaseCalendar.Date ldate = (BaseCalendar.Date) cal.newCalendarDate(TimeZone.getDefaultRef());
                ldate.setDate(year, mon + 1, mday);
                ldate.setTimeOfDay(hour, min, sec, 0);
                return cal.getTime(ldate);
            }
            BaseCalendar.Date udate = (BaseCalendar.Date) cal.newCalendarDate(null); // no time zone
            udate.setDate(year, mon + 1, mday);
            udate.setTimeOfDay(hour, min, sec, 0);
            return cal.getTime(udate) + tzoffset * (60 * 1000);
        }
        // syntax error
        throw new IllegalArgumentException();
    }
    private final static String wtb[] = {
        "am", "pm",
        "monday", "tuesday", "wednesday", "thursday", "friday",
        "saturday", "sunday",
        "january", "february", "march", "april", "may", "june",
        "july", "august", "september", "october", "november", "december",
        "gmt", "ut", "utc", "est", "edt", "cst", "cdt",
        "mst", "mdt", "pst", "pdt"
    };
    private final static int ttb[] = {
        14, 1, 0, 0, 0, 0, 0, 0, 0,
        2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
        10000 + 0, 10000 + 0, 10000 + 0,    // GMT/UT/UTC
        10000 + 5 * 60, 10000 + 4 * 60,     // EST/EDT
        10000 + 6 * 60, 10000 + 5 * 60,     // CST/CDT
        10000 + 7 * 60, 10000 + 6 * 60,     // MST/MDT
        10000 + 8 * 60, 10000 + 7 * 60      // PST/PDT
    };

    /**
     * Returns a value that is the result of subtracting 1900 from the
     * year that contains or begins with the instant in time represented
     * by this <code>Date</code> object, as interpreted in the local
     * time zone.
     *
     * <p>
     * 返回一个值,该值是从包含或开始于由此<code> Date </code>对象表示的时间的年份减去1900的结果,如在本地时区中解释的
     * 
     * 
     * @return  the year represented by this date, minus 1900.
     * @see     java.util.Calendar
     * @deprecated As of JDK version 1.1,
     * replaced by <code>Calendar.get(Calendar.YEAR) - 1900</code>.
     */
    @Deprecated
    public int getYear() {
        return normalize().getYear() - 1900;
    }

    /**
     * Sets the year of this <tt>Date</tt> object to be the specified
     * value plus 1900. This <code>Date</code> object is modified so
     * that it represents a point in time within the specified year,
     * with the month, date, hour, minute, and second the same as
     * before, as interpreted in the local time zone. (Of course, if
     * the date was February 29, for example, and the year is set to a
     * non-leap year, then the new date will be treated as if it were
     * on March 1.)
     *
     * <p>
     *  将<tt> Date </tt>对象的年份设置为指定值加1900此修改<code> Date </code>对象,以便它表示指定年份内的时间点,日期,小时,分钟和秒,如同当地时区中所解释的(当然,如果
     * 日期是2月29日,例如,年份设置为非闰年,则新日期将被视为是在3月1日)。
     * 
     * 
     * @param   year    the year value.
     * @see     java.util.Calendar
     * @deprecated As of JDK version 1.1,
     * replaced by <code>Calendar.set(Calendar.YEAR, year + 1900)</code>.
     */
    @Deprecated
    public void setYear(int year) {
        getCalendarDate().setNormalizedYear(year + 1900);
    }

    /**
     * Returns a number representing the month that contains or begins
     * with the instant in time represented by this <tt>Date</tt> object.
     * The value returned is between <code>0</code> and <code>11</code>,
     * with the value <code>0</code> representing January.
     *
     * <p>
     * 返回一个数字,表示包含或以此<tt> Date </tt>对象表示的时间开始的月份。
     * 返回的值在<code> 0 </code>和<code> 11 </code>其中<code> 0 </code>表示一月。
     * 
     * 
     * @return  the month represented by this date.
     * @see     java.util.Calendar
     * @deprecated As of JDK version 1.1,
     * replaced by <code>Calendar.get(Calendar.MONTH)</code>.
     */
    @Deprecated
    public int getMonth() {
        return normalize().getMonth() - 1; // adjust 1-based to 0-based
    }

    /**
     * Sets the month of this date to the specified value. This
     * <tt>Date</tt> object is modified so that it represents a point
     * in time within the specified month, with the year, date, hour,
     * minute, and second the same as before, as interpreted in the
     * local time zone. If the date was October 31, for example, and
     * the month is set to June, then the new date will be treated as
     * if it were on July 1, because June has only 30 days.
     *
     * <p>
     *  将此日期的月份设置为指定值。
     * 修改此<tt>日期</tt>对象,使其表示指定月份内的时间点,年,日,小时,分钟和秒与之前,如当地时区解释如果日期是例如10月31日,并且月份被设置为6月,则新的日期将被视为是在7月1日,因为6月只有3
     * 0天。
     *  将此日期的月份设置为指定值。
     * 
     * 
     * @param   month   the month value between 0-11.
     * @see     java.util.Calendar
     * @deprecated As of JDK version 1.1,
     * replaced by <code>Calendar.set(Calendar.MONTH, int month)</code>.
     */
    @Deprecated
    public void setMonth(int month) {
        int y = 0;
        if (month >= 12) {
            y = month / 12;
            month %= 12;
        } else if (month < 0) {
            y = CalendarUtils.floorDivide(month, 12);
            month = CalendarUtils.mod(month, 12);
        }
        BaseCalendar.Date d = getCalendarDate();
        if (y != 0) {
            d.setNormalizedYear(d.getNormalizedYear() + y);
        }
        d.setMonth(month + 1); // adjust 0-based to 1-based month numbering
    }

    /**
     * Returns the day of the month represented by this <tt>Date</tt> object.
     * The value returned is between <code>1</code> and <code>31</code>
     * representing the day of the month that contains or begins with the
     * instant in time represented by this <tt>Date</tt> object, as
     * interpreted in the local time zone.
     *
     * <p>
     * 返回此<tt>日期</tt>对象所表示的月份中的日期返回的值介于代表包含或开始的月份日期的<code> 1 </code>和<code> 31 </code>之间其中该时间由此<tt>日期</tt>对象
     * 表示,如在本地时区中解释的。
     * 
     * 
     * @return  the day of the month represented by this date.
     * @see     java.util.Calendar
     * @deprecated As of JDK version 1.1,
     * replaced by <code>Calendar.get(Calendar.DAY_OF_MONTH)</code>.
     * @deprecated
     */
    @Deprecated
    public int getDate() {
        return normalize().getDayOfMonth();
    }

    /**
     * Sets the day of the month of this <tt>Date</tt> object to the
     * specified value. This <tt>Date</tt> object is modified so that
     * it represents a point in time within the specified day of the
     * month, with the year, month, hour, minute, and second the same
     * as before, as interpreted in the local time zone. If the date
     * was April 30, for example, and the date is set to 31, then it
     * will be treated as if it were on May 1, because April has only
     * 30 days.
     *
     * <p>
     *  将此<tt>日期</tt>对象的月份日期设置为指定的值。
     * 修改此<tt>日期</tt>对象,以表示该月的指定日期内的时间点,与本地时区中解释的年,月,小时,分钟和秒相同如果日期例如是4月30日,并且日期设置为31,则它将被视为是在5月1日,因为四月只有30天。
     *  将此<tt>日期</tt>对象的月份日期设置为指定的值。
     * 
     * 
     * @param   date   the day of the month value between 1-31.
     * @see     java.util.Calendar
     * @deprecated As of JDK version 1.1,
     * replaced by <code>Calendar.set(Calendar.DAY_OF_MONTH, int date)</code>.
     */
    @Deprecated
    public void setDate(int date) {
        getCalendarDate().setDayOfMonth(date);
    }

    /**
     * Returns the day of the week represented by this date. The
     * returned value (<tt>0</tt> = Sunday, <tt>1</tt> = Monday,
     * <tt>2</tt> = Tuesday, <tt>3</tt> = Wednesday, <tt>4</tt> =
     * Thursday, <tt>5</tt> = Friday, <tt>6</tt> = Saturday)
     * represents the day of the week that contains or begins with
     * the instant in time represented by this <tt>Date</tt> object,
     * as interpreted in the local time zone.
     *
     * <p>
     * 返回此日期表示的星期返回的值(<tt> 0 </tt> = Sunday,<tt> 1 </tt> = Monday,<tt> 2 </tt> = Tuesday,<tt> 3 </tt> =星期三,<tt>
     *  4 </tt> =星期四,<tt> 5 </tt> =星期五,<tt> 6 </tt> =星期六)以由此<tt>日期</tt>对象表示的时间开始,如在本地时区中解释的。
     * 
     * 
     * @return  the day of the week represented by this date.
     * @see     java.util.Calendar
     * @deprecated As of JDK version 1.1,
     * replaced by <code>Calendar.get(Calendar.DAY_OF_WEEK)</code>.
     */
    @Deprecated
    public int getDay() {
        return normalize().getDayOfWeek() - BaseCalendar.SUNDAY;
    }

    /**
     * Returns the hour represented by this <tt>Date</tt> object. The
     * returned value is a number (<tt>0</tt> through <tt>23</tt>)
     * representing the hour within the day that contains or begins
     * with the instant in time represented by this <tt>Date</tt>
     * object, as interpreted in the local time zone.
     *
     * <p>
     *  返回由此<tt>日期</tt>对象表示的小时返回的值是表示包含或开始的一天内的小时的数字(<tt> 0 </tt>到<tt> 23 </tt>)其中该时间由此<tt>日期</tt>对象表示,如在本地时
     * 区中解释的。
     * 
     * 
     * @return  the hour represented by this date.
     * @see     java.util.Calendar
     * @deprecated As of JDK version 1.1,
     * replaced by <code>Calendar.get(Calendar.HOUR_OF_DAY)</code>.
     */
    @Deprecated
    public int getHours() {
        return normalize().getHours();
    }

    /**
     * Sets the hour of this <tt>Date</tt> object to the specified value.
     * This <tt>Date</tt> object is modified so that it represents a point
     * in time within the specified hour of the day, with the year, month,
     * date, minute, and second the same as before, as interpreted in the
     * local time zone.
     *
     * <p>
     * 将<tt> Date </tt>对象的小时设置为指定的值。修改此<tt>日期</tt>对象,以便它表示一天中指定小时内的时间点,月,日,分和秒与之前相同,如在本地时区中解释的
     * 
     * 
     * @param   hours   the hour value.
     * @see     java.util.Calendar
     * @deprecated As of JDK version 1.1,
     * replaced by <code>Calendar.set(Calendar.HOUR_OF_DAY, int hours)</code>.
     */
    @Deprecated
    public void setHours(int hours) {
        getCalendarDate().setHours(hours);
    }

    /**
     * Returns the number of minutes past the hour represented by this date,
     * as interpreted in the local time zone.
     * The value returned is between <code>0</code> and <code>59</code>.
     *
     * <p>
     *  返回此日期表示的小时之前的分钟数,以本地时区解释返回的值在<code> 0 </code>和<code> 59 </code>
     * 
     * 
     * @return  the number of minutes past the hour represented by this date.
     * @see     java.util.Calendar
     * @deprecated As of JDK version 1.1,
     * replaced by <code>Calendar.get(Calendar.MINUTE)</code>.
     */
    @Deprecated
    public int getMinutes() {
        return normalize().getMinutes();
    }

    /**
     * Sets the minutes of this <tt>Date</tt> object to the specified value.
     * This <tt>Date</tt> object is modified so that it represents a point
     * in time within the specified minute of the hour, with the year, month,
     * date, hour, and second the same as before, as interpreted in the
     * local time zone.
     *
     * <p>
     *  将此<tt>日期</tt>对象的分钟设置为指定值。修改此<tt>日期</tt>对象,以便它表示小时的指定分钟内的时间点,月,日,小时和秒相同,如在本地时区中解释的
     * 
     * 
     * @param   minutes   the value of the minutes.
     * @see     java.util.Calendar
     * @deprecated As of JDK version 1.1,
     * replaced by <code>Calendar.set(Calendar.MINUTE, int minutes)</code>.
     */
    @Deprecated
    public void setMinutes(int minutes) {
        getCalendarDate().setMinutes(minutes);
    }

    /**
     * Returns the number of seconds past the minute represented by this date.
     * The value returned is between <code>0</code> and <code>61</code>. The
     * values <code>60</code> and <code>61</code> can only occur on those
     * Java Virtual Machines that take leap seconds into account.
     *
     * <p>
     * 返回此日期表示的分钟数秒返回的值在<code> 0 </code>和<code> 61之间</code>值<code> 60 </code>和<code> / code>只能出现在考虑闰秒的Java虚拟
     * 机上。
     * 
     * 
     * @return  the number of seconds past the minute represented by this date.
     * @see     java.util.Calendar
     * @deprecated As of JDK version 1.1,
     * replaced by <code>Calendar.get(Calendar.SECOND)</code>.
     */
    @Deprecated
    public int getSeconds() {
        return normalize().getSeconds();
    }

    /**
     * Sets the seconds of this <tt>Date</tt> to the specified value.
     * This <tt>Date</tt> object is modified so that it represents a
     * point in time within the specified second of the minute, with
     * the year, month, date, hour, and minute the same as before, as
     * interpreted in the local time zone.
     *
     * <p>
     *  将此<tt>日期</tt>的秒设置为指定的值。此<tt>日期</tt>对象被修改,以表示在指定的分钟秒内的时间点, ,日期,小时和分钟,如在本地时区中解释的
     * 
     * 
     * @param   seconds   the seconds value.
     * @see     java.util.Calendar
     * @deprecated As of JDK version 1.1,
     * replaced by <code>Calendar.set(Calendar.SECOND, int seconds)</code>.
     */
    @Deprecated
    public void setSeconds(int seconds) {
        getCalendarDate().setSeconds(seconds);
    }

    /**
     * Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT
     * represented by this <tt>Date</tt> object.
     *
     * <p>
     *  返回自此<tt>日期</tt>对象所表示的1970年1月1日,00:00:00 GMT以来的毫秒数
     * 
     * 
     * @return  the number of milliseconds since January 1, 1970, 00:00:00 GMT
     *          represented by this date.
     */
    public long getTime() {
        return getTimeImpl();
    }

    private final long getTimeImpl() {
        if (cdate != null && !cdate.isNormalized()) {
            normalize();
        }
        return fastTime;
    }

    /**
     * Sets this <code>Date</code> object to represent a point in time that is
     * <code>time</code> milliseconds after January 1, 1970 00:00:00 GMT.
     *
     * <p>
     * 设置此<code> Date </code>对象以表示1970年1月1日之后<code>时间</code>毫秒的时间点00:00:00 GMT
     * 
     * 
     * @param   time   the number of milliseconds.
     */
    public void setTime(long time) {
        fastTime = time;
        cdate = null;
    }

    /**
     * Tests if this date is before the specified date.
     *
     * <p>
     *  测试此日期是否在指定日期之前
     * 
     * 
     * @param   when   a date.
     * @return  <code>true</code> if and only if the instant of time
     *            represented by this <tt>Date</tt> object is strictly
     *            earlier than the instant represented by <tt>when</tt>;
     *          <code>false</code> otherwise.
     * @exception NullPointerException if <code>when</code> is null.
     */
    public boolean before(Date when) {
        return getMillisOf(this) < getMillisOf(when);
    }

    /**
     * Tests if this date is after the specified date.
     *
     * <p>
     *  测试此日期是否在指定日期之后
     * 
     * 
     * @param   when   a date.
     * @return  <code>true</code> if and only if the instant represented
     *          by this <tt>Date</tt> object is strictly later than the
     *          instant represented by <tt>when</tt>;
     *          <code>false</code> otherwise.
     * @exception NullPointerException if <code>when</code> is null.
     */
    public boolean after(Date when) {
        return getMillisOf(this) > getMillisOf(when);
    }

    /**
     * Compares two dates for equality.
     * The result is <code>true</code> if and only if the argument is
     * not <code>null</code> and is a <code>Date</code> object that
     * represents the same point in time, to the millisecond, as this object.
     * <p>
     * Thus, two <code>Date</code> objects are equal if and only if the
     * <code>getTime</code> method returns the same <code>long</code>
     * value for both.
     *
     * <p>
     *  比较两个日期的相等性结果是<code> true </code>当且仅当参数不是<code> null </code>且是代表同一点的<code> Date </code>时间,到毫秒,作为这个对象。
     * <p>
     *  因此,当且仅当<code> getTime </code>方法对于两个<code> getTime </code>方法返回相同的<code> long </code>值时,两个<code> Date 
     * </code>。
     * 
     * 
     * @param   obj   the object to compare with.
     * @return  <code>true</code> if the objects are the same;
     *          <code>false</code> otherwise.
     * @see     java.util.Date#getTime()
     */
    public boolean equals(Object obj) {
        return obj instanceof Date && getTime() == ((Date) obj).getTime();
    }

    /**
     * Returns the millisecond value of this <code>Date</code> object
     * without affecting its internal state.
     * <p>
     *  返回此<code> Date </code>对象的毫秒值,而不影响其内部状态
     * 
     */
    static final long getMillisOf(Date date) {
        if (date.cdate == null || date.cdate.isNormalized()) {
            return date.fastTime;
        }
        BaseCalendar.Date d = (BaseCalendar.Date) date.cdate.clone();
        return gcal.getTime(d);
    }

    /**
     * Compares two Dates for ordering.
     *
     * <p>
     *  比较两个日期进行订购
     * 
     * 
     * @param   anotherDate   the <code>Date</code> to be compared.
     * @return  the value <code>0</code> if the argument Date is equal to
     *          this Date; a value less than <code>0</code> if this Date
     *          is before the Date argument; and a value greater than
     *      <code>0</code> if this Date is after the Date argument.
     * @since   1.2
     * @exception NullPointerException if <code>anotherDate</code> is null.
     */
    public int compareTo(Date anotherDate) {
        long thisTime = getMillisOf(this);
        long anotherTime = getMillisOf(anotherDate);
        return (thisTime<anotherTime ? -1 : (thisTime==anotherTime ? 0 : 1));
    }

    /**
     * Returns a hash code value for this object. The result is the
     * exclusive OR of the two halves of the primitive <tt>long</tt>
     * value returned by the {@link Date#getTime}
     * method. That is, the hash code is the value of the expression:
     * <blockquote><pre>{@code
     * (int)(this.getTime()^(this.getTime() >>> 32))
     * }</pre></blockquote>
     *
     * <p>
     * 返回此对象的哈希码值结果是由{@link Date#getTime}方法返回的原始<tt> long </tt>值的两半的异或。
     * 即,哈希码是值的表达式：<blockquote> <pre> {@ code(int)(thisgetTime()^(thisgetTime()>>> 32))} </pre> </blockquote>
     * 。
     * 返回此对象的哈希码值结果是由{@link Date#getTime}方法返回的原始<tt> long </tt>值的两半的异或。
     * 
     * 
     * @return  a hash code value for this object.
     */
    public int hashCode() {
        long ht = this.getTime();
        return (int) ht ^ (int) (ht >> 32);
    }

    /**
     * Converts this <code>Date</code> object to a <code>String</code>
     * of the form:
     * <blockquote><pre>
     * dow mon dd hh:mm:ss zzz yyyy</pre></blockquote>
     * where:<ul>
     * <li><tt>dow</tt> is the day of the week (<tt>Sun, Mon, Tue, Wed,
     *     Thu, Fri, Sat</tt>).
     * <li><tt>mon</tt> is the month (<tt>Jan, Feb, Mar, Apr, May, Jun,
     *     Jul, Aug, Sep, Oct, Nov, Dec</tt>).
     * <li><tt>dd</tt> is the day of the month (<tt>01</tt> through
     *     <tt>31</tt>), as two decimal digits.
     * <li><tt>hh</tt> is the hour of the day (<tt>00</tt> through
     *     <tt>23</tt>), as two decimal digits.
     * <li><tt>mm</tt> is the minute within the hour (<tt>00</tt> through
     *     <tt>59</tt>), as two decimal digits.
     * <li><tt>ss</tt> is the second within the minute (<tt>00</tt> through
     *     <tt>61</tt>, as two decimal digits.
     * <li><tt>zzz</tt> is the time zone (and may reflect daylight saving
     *     time). Standard time zone abbreviations include those
     *     recognized by the method <tt>parse</tt>. If time zone
     *     information is not available, then <tt>zzz</tt> is empty -
     *     that is, it consists of no characters at all.
     * <li><tt>yyyy</tt> is the year, as four decimal digits.
     * </ul>
     *
     * <p>
     * 将此<code> Date </code>对象转换为以下形式的<code> String </code>：<blockquote> <pre> dow mon dd hh：mm：ss zzz yyyy 
     * </pre> </blockquote> ：<ul> <li> <tt> dow </tt>是星期几(<tt> Sun,Mon,Tue,Wed,Thu,Fri,Sat </tt>)<li> <tt> m
     * on </tt>是月份(<tt> Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sep,Oct,Nov,Dec </tt>)<li> <tt> dd </tt>是一个月中的某一天(<tt>
     *  01 </tt>到<tt> 31 </tt>),因为两个小数位<li> <tt> hh </tt> > <tt> 00 </tt>到<tt>的两个小数位<li> <tt> mm </tt> > 59 
     * </tt>),因为两个十进制数字<li> <tt> ss </tt>是分钟内的第二个(<tt> 00 </tt>到<tt> 61 </tt>十进制数字<li> <tt> zzz </tt>是时区(可能反
     * 映夏令时)标准时区缩写包括通过方法识别的时区<tt> parse </tt>如果时区信息不可用, <tt> zzz </tt>是空的 - 也就是说,它根本没有字符<li> <tt> yyyy </tt>
     * 是年份,。
     * </ul>
     * 
     * 
     * @return  a string representation of this date.
     * @see     java.util.Date#toLocaleString()
     * @see     java.util.Date#toGMTString()
     */
    public String toString() {
        // "EEE MMM dd HH:mm:ss zzz yyyy";
        BaseCalendar.Date date = normalize();
        StringBuilder sb = new StringBuilder(28);
        int index = date.getDayOfWeek();
        if (index == BaseCalendar.SUNDAY) {
            index = 8;
        }
        convertToAbbr(sb, wtb[index]).append(' ');                        // EEE
        convertToAbbr(sb, wtb[date.getMonth() - 1 + 2 + 7]).append(' ');  // MMM
        CalendarUtils.sprintf0d(sb, date.getDayOfMonth(), 2).append(' '); // dd

        CalendarUtils.sprintf0d(sb, date.getHours(), 2).append(':');   // HH
        CalendarUtils.sprintf0d(sb, date.getMinutes(), 2).append(':'); // mm
        CalendarUtils.sprintf0d(sb, date.getSeconds(), 2).append(' '); // ss
        TimeZone zi = date.getZone();
        if (zi != null) {
            sb.append(zi.getDisplayName(date.isDaylightTime(), TimeZone.SHORT, Locale.US)); // zzz
        } else {
            sb.append("GMT");
        }
        sb.append(' ').append(date.getYear());  // yyyy
        return sb.toString();
    }

    /**
     * Converts the given name to its 3-letter abbreviation (e.g.,
     * "monday" -> "Mon") and stored the abbreviation in the given
     * <code>StringBuilder</code>.
     * <p>
     * 将给定名称转换为其3个字母的缩写(例如,"monday" - >"Mon"),并将缩写存储在给定的<code> StringBuilder </code>
     * 
     */
    private static final StringBuilder convertToAbbr(StringBuilder sb, String name) {
        sb.append(Character.toUpperCase(name.charAt(0)));
        sb.append(name.charAt(1)).append(name.charAt(2));
        return sb;
    }

    /**
     * Creates a string representation of this <tt>Date</tt> object in an
     * implementation-dependent form. The intent is that the form should
     * be familiar to the user of the Java application, wherever it may
     * happen to be running. The intent is comparable to that of the
     * "<code>%c</code>" format supported by the <code>strftime()</code>
     * function of ISO&nbsp;C.
     *
     * <p>
     *  以与实现相关的形式创建此<tt> Date </tt>对象的字符串表示形式意图是,表单应该是Java应用程序的用户熟悉的,无论它可能发生在哪里运行该意图是可比的ISO&nbsp; C的<code> s
     * trftime()</code>函数支持的"<code>％c </code>。
     * 
     * 
     * @return  a string representation of this date, using the locale
     *          conventions.
     * @see     java.text.DateFormat
     * @see     java.util.Date#toString()
     * @see     java.util.Date#toGMTString()
     * @deprecated As of JDK version 1.1,
     * replaced by <code>DateFormat.format(Date date)</code>.
     */
    @Deprecated
    public String toLocaleString() {
        DateFormat formatter = DateFormat.getDateTimeInstance();
        return formatter.format(this);
    }

    /**
     * Creates a string representation of this <tt>Date</tt> object of
     * the form:
     * <blockquote><pre>
     * d mon yyyy hh:mm:ss GMT</pre></blockquote>
     * where:<ul>
     * <li><i>d</i> is the day of the month (<tt>1</tt> through <tt>31</tt>),
     *     as one or two decimal digits.
     * <li><i>mon</i> is the month (<tt>Jan, Feb, Mar, Apr, May, Jun, Jul,
     *     Aug, Sep, Oct, Nov, Dec</tt>).
     * <li><i>yyyy</i> is the year, as four decimal digits.
     * <li><i>hh</i> is the hour of the day (<tt>00</tt> through <tt>23</tt>),
     *     as two decimal digits.
     * <li><i>mm</i> is the minute within the hour (<tt>00</tt> through
     *     <tt>59</tt>), as two decimal digits.
     * <li><i>ss</i> is the second within the minute (<tt>00</tt> through
     *     <tt>61</tt>), as two decimal digits.
     * <li><i>GMT</i> is exactly the ASCII letters "<tt>GMT</tt>" to indicate
     *     Greenwich Mean Time.
     * </ul><p>
     * The result does not depend on the local time zone.
     *
     * <p>
     * 创建此<tt>日期</tt>对象的字符串表示形式：<blockquote> <pre> d mon yyyy hh：mm：ss GMT </pre> </blockquote>其中：<ul> <li> 
     * <i> d </i>是一个月中的某一天(<tt> 1 </tt>到<tt> 31 </tt>),为一个或两个十进制数字<li> <i> mon </i >是月份(<tt> Jan,Feb,Mar,Apr
     * ,May,Jun,Jul,Aug,Sep,Oct,Nov,Dec </tt>)<li> <i> yyyy </i> ,因为四个十进制数字<li> <i> hh </i>是一天中的小时(<tt> 00 </tt>
     * 到<tt> 23 </tt>), i> mm </i>是小时内的分钟(<tt> 00 </tt>到<tt> 59 </tt>),因为两个小数位<li> <i> ss </i>分钟内<second> <i>
     *  GMT </i>的两个十进制数字正好是ASCII字母"<tt> GMT </tt> / tt>"表示格林威治标准时间</ul> <p>结果不依赖于本地时区。
     * 
     * 
     * @return  a string representation of this date, using the Internet GMT
     *          conventions.
     * @see     java.text.DateFormat
     * @see     java.util.Date#toString()
     * @see     java.util.Date#toLocaleString()
     * @deprecated As of JDK version 1.1,
     * replaced by <code>DateFormat.format(Date date)</code>, using a
     * GMT <code>TimeZone</code>.
     */
    @Deprecated
    public String toGMTString() {
        // d MMM yyyy HH:mm:ss 'GMT'
        long t = getTime();
        BaseCalendar cal = getCalendarSystem(t);
        BaseCalendar.Date date =
            (BaseCalendar.Date) cal.getCalendarDate(getTime(), (TimeZone)null);
        StringBuilder sb = new StringBuilder(32);
        CalendarUtils.sprintf0d(sb, date.getDayOfMonth(), 1).append(' '); // d
        convertToAbbr(sb, wtb[date.getMonth() - 1 + 2 + 7]).append(' ');  // MMM
        sb.append(date.getYear()).append(' ');                            // yyyy
        CalendarUtils.sprintf0d(sb, date.getHours(), 2).append(':');      // HH
        CalendarUtils.sprintf0d(sb, date.getMinutes(), 2).append(':');    // mm
        CalendarUtils.sprintf0d(sb, date.getSeconds(), 2);                // ss
        sb.append(" GMT");                                                // ' GMT'
        return sb.toString();
    }

    /**
     * Returns the offset, measured in minutes, for the local time zone
     * relative to UTC that is appropriate for the time represented by
     * this <code>Date</code> object.
     * <p>
     * For example, in Massachusetts, five time zones west of Greenwich:
     * <blockquote><pre>
     * new Date(96, 1, 14).getTimezoneOffset() returns 300</pre></blockquote>
     * because on February 14, 1996, standard time (Eastern Standard Time)
     * is in use, which is offset five hours from UTC; but:
     * <blockquote><pre>
     * new Date(96, 5, 1).getTimezoneOffset() returns 240</pre></blockquote>
     * because on June 1, 1996, daylight saving time (Eastern Daylight Time)
     * is in use, which is offset only four hours from UTC.<p>
     * This method produces the same result as if it computed:
     * <blockquote><pre>
     * (this.getTime() - UTC(this.getYear(),
     *                       this.getMonth(),
     *                       this.getDate(),
     *                       this.getHours(),
     *                       this.getMinutes(),
     *                       this.getSeconds())) / (60 * 1000)
     * </pre></blockquote>
     *
     * <p>
     * 返回本地时区相对于UTC的偏移量(以分钟为单位),该偏移量适用于此<code> Date </code>对象所表示的时间
     * <p>
     * 例如,在马萨诸塞州,格林威治以西的五个时区：<blockquote> <pre> new Date(96,1,14)getTimezoneOffset()返回300 </pre> </blockquote>
     * ,因为1996年2月14日, (东部标准时间)正在使用,这偏离UTC五个小时;但是：<blockquote> <pre> new Date(96,5,1)getTimezoneOffset()返回240
     *  </pre> </blockquote>,因为1996年6月1日,夏令时(Eastern Daylight Time)是从UTC UTC偏移只有四个小时<p>这个方法产生相同的结果,如果它计算：<blockquote>
     *  <pre>(thisgetTime() -  UTC(thisgetYear(),thisgetMonth(),thisgetDate(),thisgetHours thisgetMinutes(),
     * thisgetSeconds()))/(60 * 1000)</pre> </blockquote>。
     * 
     * 
     * @return  the time-zone offset, in minutes, for the current time zone.
     * @see     java.util.Calendar#ZONE_OFFSET
     * @see     java.util.Calendar#DST_OFFSET
     * @see     java.util.TimeZone#getDefault
     * @deprecated As of JDK version 1.1,
     * replaced by <code>-(Calendar.get(Calendar.ZONE_OFFSET) +
     * Calendar.get(Calendar.DST_OFFSET)) / (60 * 1000)</code>.
     */
    @Deprecated
    public int getTimezoneOffset() {
        int zoneOffset;
        if (cdate == null) {
            TimeZone tz = TimeZone.getDefaultRef();
            if (tz instanceof ZoneInfo) {
                zoneOffset = ((ZoneInfo)tz).getOffsets(fastTime, null);
            } else {
                zoneOffset = tz.getOffset(fastTime);
            }
        } else {
            normalize();
            zoneOffset = cdate.getZoneOffset();
        }
        return -zoneOffset/60000;  // convert to minutes
    }

    private final BaseCalendar.Date getCalendarDate() {
        if (cdate == null) {
            BaseCalendar cal = getCalendarSystem(fastTime);
            cdate = (BaseCalendar.Date) cal.getCalendarDate(fastTime,
                                                            TimeZone.getDefaultRef());
        }
        return cdate;
    }

    private final BaseCalendar.Date normalize() {
        if (cdate == null) {
            BaseCalendar cal = getCalendarSystem(fastTime);
            cdate = (BaseCalendar.Date) cal.getCalendarDate(fastTime,
                                                            TimeZone.getDefaultRef());
            return cdate;
        }

        // Normalize cdate with the TimeZone in cdate first. This is
        // required for the compatible behavior.
        if (!cdate.isNormalized()) {
            cdate = normalize(cdate);
        }

        // If the default TimeZone has changed, then recalculate the
        // fields with the new TimeZone.
        TimeZone tz = TimeZone.getDefaultRef();
        if (tz != cdate.getZone()) {
            cdate.setZone(tz);
            CalendarSystem cal = getCalendarSystem(cdate);
            cal.getCalendarDate(fastTime, cdate);
        }
        return cdate;
    }

    // fastTime and the returned data are in sync upon return.
    private final BaseCalendar.Date normalize(BaseCalendar.Date date) {
        int y = date.getNormalizedYear();
        int m = date.getMonth();
        int d = date.getDayOfMonth();
        int hh = date.getHours();
        int mm = date.getMinutes();
        int ss = date.getSeconds();
        int ms = date.getMillis();
        TimeZone tz = date.getZone();

        // If the specified year can't be handled using a long value
        // in milliseconds, GregorianCalendar is used for full
        // compatibility with underflow and overflow. This is required
        // by some JCK tests. The limits are based max year values -
        // years that can be represented by max values of d, hh, mm,
        // ss and ms. Also, let GregorianCalendar handle the default
        // cutover year so that we don't need to worry about the
        // transition here.
        if (y == 1582 || y > 280000000 || y < -280000000) {
            if (tz == null) {
                tz = TimeZone.getTimeZone("GMT");
            }
            GregorianCalendar gc = new GregorianCalendar(tz);
            gc.clear();
            gc.set(GregorianCalendar.MILLISECOND, ms);
            gc.set(y, m-1, d, hh, mm, ss);
            fastTime = gc.getTimeInMillis();
            BaseCalendar cal = getCalendarSystem(fastTime);
            date = (BaseCalendar.Date) cal.getCalendarDate(fastTime, tz);
            return date;
        }

        BaseCalendar cal = getCalendarSystem(y);
        if (cal != getCalendarSystem(date)) {
            date = (BaseCalendar.Date) cal.newCalendarDate(tz);
            date.setNormalizedDate(y, m, d).setTimeOfDay(hh, mm, ss, ms);
        }
        // Perform the GregorianCalendar-style normalization.
        fastTime = cal.getTime(date);

        // In case the normalized date requires the other calendar
        // system, we need to recalculate it using the other one.
        BaseCalendar ncal = getCalendarSystem(fastTime);
        if (ncal != cal) {
            date = (BaseCalendar.Date) ncal.newCalendarDate(tz);
            date.setNormalizedDate(y, m, d).setTimeOfDay(hh, mm, ss, ms);
            fastTime = ncal.getTime(date);
        }
        return date;
    }

    /**
     * Returns the Gregorian or Julian calendar system to use with the
     * given date. Use Gregorian from October 15, 1582.
     *
     * <p>
     * 返回要在给定日期使用的公历或儒略历日历系统。使用从1582年10月15日起的格里高利
     * 
     * 
     * @param year normalized calendar year (not -1900)
     * @return the CalendarSystem to use for the specified date
     */
    private static final BaseCalendar getCalendarSystem(int year) {
        if (year >= 1582) {
            return gcal;
        }
        return getJulianCalendar();
    }

    private static final BaseCalendar getCalendarSystem(long utc) {
        // Quickly check if the time stamp given by `utc' is the Epoch
        // or later. If it's before 1970, we convert the cutover to
        // local time to compare.
        if (utc >= 0
            || utc >= GregorianCalendar.DEFAULT_GREGORIAN_CUTOVER
                        - TimeZone.getDefaultRef().getOffset(utc)) {
            return gcal;
        }
        return getJulianCalendar();
    }

    private static final BaseCalendar getCalendarSystem(BaseCalendar.Date cdate) {
        if (jcal == null) {
            return gcal;
        }
        if (cdate.getEra() != null) {
            return jcal;
        }
        return gcal;
    }

    synchronized private static final BaseCalendar getJulianCalendar() {
        if (jcal == null) {
            jcal = (BaseCalendar) CalendarSystem.forName("julian");
        }
        return jcal;
    }

    /**
     * Save the state of this object to a stream (i.e., serialize it).
     *
     * <p>
     *  将此对象的状态保存到流(即,序列化它)
     * 
     * 
     * @serialData The value returned by <code>getTime()</code>
     *             is emitted (long).  This represents the offset from
     *             January 1, 1970, 00:00:00 GMT in milliseconds.
     */
    private void writeObject(ObjectOutputStream s)
         throws IOException
    {
        s.writeLong(getTimeImpl());
    }

    /**
     * Reconstitute this object from a stream (i.e., deserialize it).
     * <p>
     *  从流重构此对象(即,反序列化它)
     * 
     */
    private void readObject(ObjectInputStream s)
         throws IOException, ClassNotFoundException
    {
        fastTime = s.readLong();
    }

    /**
     * Obtains an instance of {@code Date} from an {@code Instant} object.
     * <p>
     * {@code Instant} uses a precision of nanoseconds, whereas {@code Date}
     * uses a precision of milliseconds.  The conversion will trancate any
     * excess precision information as though the amount in nanoseconds was
     * subject to integer division by one million.
     * <p>
     * {@code Instant} can store points on the time-line further in the future
     * and further in the past than {@code Date}. In this scenario, this method
     * will throw an exception.
     *
     * <p>
     *  从{@code Instant}对象获取{@code Date}的实例
     * <p>
     *  {@code Instant}使用纳秒的精度,而{@code Date}使用毫秒的精度。转换将传递任何超额精度信息,就好像以纳秒为单位的量受到整数除以一百万
     * <p>
     *  {@code Instant}可以在未来进一步在时间线上存储点,并且在过去比{@code Date}更长。在这种情况下,此方法将抛出异常
     * 
     * 
     * @param instant  the instant to convert
     * @return a {@code Date} representing the same point on the time-line as
     *  the provided instant
     * @exception NullPointerException if {@code instant} is null.
     * @exception IllegalArgumentException if the instant is too large to
     *  represent as a {@code Date}
     * @since 1.8
     */
    public static Date from(Instant instant) {
        try {
            return new Date(instant.toEpochMilli());
        } catch (ArithmeticException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * Converts this {@code Date} object to an {@code Instant}.
     * <p>
     * The conversion creates an {@code Instant} that represents the same
     * point on the time-line as this {@code Date}.
     *
     * <p>
     * 
     * @return an instant representing the same point on the time-line as
     *  this {@code Date} object
     * @since 1.8
     */
    public Instant toInstant() {
        return Instant.ofEpochMilli(getTime());
    }
}
