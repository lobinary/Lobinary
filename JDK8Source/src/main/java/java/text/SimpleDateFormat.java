/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * (C) Copyright IBM Corp. 1996-1998 - All Rights Reserved
 *
 *   The original version of this source code and documentation is copyrighted
 * and owned by Taligent, Inc., a wholly-owned subsidiary of IBM. These
 * materials are provided under terms of a License Agreement between Taligent
 * and Sun. This technology is protected by multiple US and International
 * patents. This notice and attribution to Taligent may not be removed.
 *   Taligent is a registered trademark of Taligent, Inc.
 *
 * <p>
 *  (C)版权所有Taligent,Inc 1996  - 保留所有权利(C)版权所有IBM Corp 1996-1998  - 保留所有权利
 * 
 *  此源代码和文档的原始版本由IBM的全资子公司Taligent,Inc拥有版权和所有权。
 * 这些资料根据Taligent和Sun之间的许可协议的条款提供此技术受多个美国和国际专利保护Taligent是Taligent的注册商标。Taligent是Taligent的注册商标。
 * 
 */

package java.text;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import static java.text.DateFormatSymbols.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import sun.util.calendar.CalendarUtils;
import sun.util.calendar.ZoneInfoFile;
import sun.util.locale.provider.LocaleProviderAdapter;

/**
 * <code>SimpleDateFormat</code> is a concrete class for formatting and
 * parsing dates in a locale-sensitive manner. It allows for formatting
 * (date &rarr; text), parsing (text &rarr; date), and normalization.
 *
 * <p>
 * <code>SimpleDateFormat</code> allows you to start by choosing
 * any user-defined patterns for date-time formatting. However, you
 * are encouraged to create a date-time formatter with either
 * <code>getTimeInstance</code>, <code>getDateInstance</code>, or
 * <code>getDateTimeInstance</code> in <code>DateFormat</code>. Each
 * of these class methods can return a date/time formatter initialized
 * with a default format pattern. You may modify the format pattern
 * using the <code>applyPattern</code> methods as desired.
 * For more information on using these methods, see
 * {@link DateFormat}.
 *
 * <h3>Date and Time Patterns</h3>
 * <p>
 * Date and time formats are specified by <em>date and time pattern</em>
 * strings.
 * Within date and time pattern strings, unquoted letters from
 * <code>'A'</code> to <code>'Z'</code> and from <code>'a'</code> to
 * <code>'z'</code> are interpreted as pattern letters representing the
 * components of a date or time string.
 * Text can be quoted using single quotes (<code>'</code>) to avoid
 * interpretation.
 * <code>"''"</code> represents a single quote.
 * All other characters are not interpreted; they're simply copied into the
 * output string during formatting or matched against the input string
 * during parsing.
 * <p>
 * The following pattern letters are defined (all other characters from
 * <code>'A'</code> to <code>'Z'</code> and from <code>'a'</code> to
 * <code>'z'</code> are reserved):
 * <blockquote>
 * <table border=0 cellspacing=3 cellpadding=0 summary="Chart shows pattern letters, date/time component, presentation, and examples.">
 *     <tr style="background-color: rgb(204, 204, 255);">
 *         <th align=left>Letter
 *         <th align=left>Date or Time Component
 *         <th align=left>Presentation
 *         <th align=left>Examples
 *     <tr>
 *         <td><code>G</code>
 *         <td>Era designator
 *         <td><a href="#text">Text</a>
 *         <td><code>AD</code>
 *     <tr style="background-color: rgb(238, 238, 255);">
 *         <td><code>y</code>
 *         <td>Year
 *         <td><a href="#year">Year</a>
 *         <td><code>1996</code>; <code>96</code>
 *     <tr>
 *         <td><code>Y</code>
 *         <td>Week year
 *         <td><a href="#year">Year</a>
 *         <td><code>2009</code>; <code>09</code>
 *     <tr style="background-color: rgb(238, 238, 255);">
 *         <td><code>M</code>
 *         <td>Month in year (context sensitive)
 *         <td><a href="#month">Month</a>
 *         <td><code>July</code>; <code>Jul</code>; <code>07</code>
 *     <tr>
 *         <td><code>L</code>
 *         <td>Month in year (standalone form)
 *         <td><a href="#month">Month</a>
 *         <td><code>July</code>; <code>Jul</code>; <code>07</code>
 *     <tr style="background-color: rgb(238, 238, 255);">
 *         <td><code>w</code>
 *         <td>Week in year
 *         <td><a href="#number">Number</a>
 *         <td><code>27</code>
 *     <tr>
 *         <td><code>W</code>
 *         <td>Week in month
 *         <td><a href="#number">Number</a>
 *         <td><code>2</code>
 *     <tr style="background-color: rgb(238, 238, 255);">
 *         <td><code>D</code>
 *         <td>Day in year
 *         <td><a href="#number">Number</a>
 *         <td><code>189</code>
 *     <tr>
 *         <td><code>d</code>
 *         <td>Day in month
 *         <td><a href="#number">Number</a>
 *         <td><code>10</code>
 *     <tr style="background-color: rgb(238, 238, 255);">
 *         <td><code>F</code>
 *         <td>Day of week in month
 *         <td><a href="#number">Number</a>
 *         <td><code>2</code>
 *     <tr>
 *         <td><code>E</code>
 *         <td>Day name in week
 *         <td><a href="#text">Text</a>
 *         <td><code>Tuesday</code>; <code>Tue</code>
 *     <tr style="background-color: rgb(238, 238, 255);">
 *         <td><code>u</code>
 *         <td>Day number of week (1 = Monday, ..., 7 = Sunday)
 *         <td><a href="#number">Number</a>
 *         <td><code>1</code>
 *     <tr>
 *         <td><code>a</code>
 *         <td>Am/pm marker
 *         <td><a href="#text">Text</a>
 *         <td><code>PM</code>
 *     <tr style="background-color: rgb(238, 238, 255);">
 *         <td><code>H</code>
 *         <td>Hour in day (0-23)
 *         <td><a href="#number">Number</a>
 *         <td><code>0</code>
 *     <tr>
 *         <td><code>k</code>
 *         <td>Hour in day (1-24)
 *         <td><a href="#number">Number</a>
 *         <td><code>24</code>
 *     <tr style="background-color: rgb(238, 238, 255);">
 *         <td><code>K</code>
 *         <td>Hour in am/pm (0-11)
 *         <td><a href="#number">Number</a>
 *         <td><code>0</code>
 *     <tr>
 *         <td><code>h</code>
 *         <td>Hour in am/pm (1-12)
 *         <td><a href="#number">Number</a>
 *         <td><code>12</code>
 *     <tr style="background-color: rgb(238, 238, 255);">
 *         <td><code>m</code>
 *         <td>Minute in hour
 *         <td><a href="#number">Number</a>
 *         <td><code>30</code>
 *     <tr>
 *         <td><code>s</code>
 *         <td>Second in minute
 *         <td><a href="#number">Number</a>
 *         <td><code>55</code>
 *     <tr style="background-color: rgb(238, 238, 255);">
 *         <td><code>S</code>
 *         <td>Millisecond
 *         <td><a href="#number">Number</a>
 *         <td><code>978</code>
 *     <tr>
 *         <td><code>z</code>
 *         <td>Time zone
 *         <td><a href="#timezone">General time zone</a>
 *         <td><code>Pacific Standard Time</code>; <code>PST</code>; <code>GMT-08:00</code>
 *     <tr style="background-color: rgb(238, 238, 255);">
 *         <td><code>Z</code>
 *         <td>Time zone
 *         <td><a href="#rfc822timezone">RFC 822 time zone</a>
 *         <td><code>-0800</code>
 *     <tr>
 *         <td><code>X</code>
 *         <td>Time zone
 *         <td><a href="#iso8601timezone">ISO 8601 time zone</a>
 *         <td><code>-08</code>; <code>-0800</code>;  <code>-08:00</code>
 * </table>
 * </blockquote>
 * Pattern letters are usually repeated, as their number determines the
 * exact presentation:
 * <ul>
 * <li><strong><a name="text">Text:</a></strong>
 *     For formatting, if the number of pattern letters is 4 or more,
 *     the full form is used; otherwise a short or abbreviated form
 *     is used if available.
 *     For parsing, both forms are accepted, independent of the number
 *     of pattern letters.<br><br></li>
 * <li><strong><a name="number">Number:</a></strong>
 *     For formatting, the number of pattern letters is the minimum
 *     number of digits, and shorter numbers are zero-padded to this amount.
 *     For parsing, the number of pattern letters is ignored unless
 *     it's needed to separate two adjacent fields.<br><br></li>
 * <li><strong><a name="year">Year:</a></strong>
 *     If the formatter's {@link #getCalendar() Calendar} is the Gregorian
 *     calendar, the following rules are applied.<br>
 *     <ul>
 *     <li>For formatting, if the number of pattern letters is 2, the year
 *         is truncated to 2 digits; otherwise it is interpreted as a
 *         <a href="#number">number</a>.
 *     <li>For parsing, if the number of pattern letters is more than 2,
 *         the year is interpreted literally, regardless of the number of
 *         digits. So using the pattern "MM/dd/yyyy", "01/11/12" parses to
 *         Jan 11, 12 A.D.
 *     <li>For parsing with the abbreviated year pattern ("y" or "yy"),
 *         <code>SimpleDateFormat</code> must interpret the abbreviated year
 *         relative to some century.  It does this by adjusting dates to be
 *         within 80 years before and 20 years after the time the <code>SimpleDateFormat</code>
 *         instance is created. For example, using a pattern of "MM/dd/yy" and a
 *         <code>SimpleDateFormat</code> instance created on Jan 1, 1997,  the string
 *         "01/11/12" would be interpreted as Jan 11, 2012 while the string "05/04/64"
 *         would be interpreted as May 4, 1964.
 *         During parsing, only strings consisting of exactly two digits, as defined by
 *         {@link Character#isDigit(char)}, will be parsed into the default century.
 *         Any other numeric string, such as a one digit string, a three or more digit
 *         string, or a two digit string that isn't all digits (for example, "-1"), is
 *         interpreted literally.  So "01/02/3" or "01/02/003" are parsed, using the
 *         same pattern, as Jan 2, 3 AD.  Likewise, "01/02/-3" is parsed as Jan 2, 4 BC.
 *     </ul>
 *     Otherwise, calendar system specific forms are applied.
 *     For both formatting and parsing, if the number of pattern
 *     letters is 4 or more, a calendar specific {@linkplain
 *     Calendar#LONG long form} is used. Otherwise, a calendar
 *     specific {@linkplain Calendar#SHORT short or abbreviated form}
 *     is used.<br>
 *     <br>
 *     If week year {@code 'Y'} is specified and the {@linkplain
 *     #getCalendar() calendar} doesn't support any <a
 *     href="../util/GregorianCalendar.html#week_year"> week
 *     years</a>, the calendar year ({@code 'y'}) is used instead. The
 *     support of week years can be tested with a call to {@link
 *     DateFormat#getCalendar() getCalendar()}.{@link
 *     java.util.Calendar#isWeekDateSupported()
 *     isWeekDateSupported()}.<br><br></li>
 * <li><strong><a name="month">Month:</a></strong>
 *     If the number of pattern letters is 3 or more, the month is
 *     interpreted as <a href="#text">text</a>; otherwise,
 *     it is interpreted as a <a href="#number">number</a>.<br>
 *     <ul>
 *     <li>Letter <em>M</em> produces context-sensitive month names, such as the
 *         embedded form of names. If a {@code DateFormatSymbols} has been set
 *         explicitly with constructor {@link #SimpleDateFormat(String,
 *         DateFormatSymbols)} or method {@link
 *         #setDateFormatSymbols(DateFormatSymbols)}, the month names given by
 *         the {@code DateFormatSymbols} are used.</li>
 *     <li>Letter <em>L</em> produces the standalone form of month names.</li>
 *     </ul>
 *     <br></li>
 * <li><strong><a name="timezone">General time zone:</a></strong>
 *     Time zones are interpreted as <a href="#text">text</a> if they have
 *     names. For time zones representing a GMT offset value, the
 *     following syntax is used:
 *     <pre>
 *     <a name="GMTOffsetTimeZone"><i>GMTOffsetTimeZone:</i></a>
 *             <code>GMT</code> <i>Sign</i> <i>Hours</i> <code>:</code> <i>Minutes</i>
 *     <i>Sign:</i> one of
 *             <code>+ -</code>
 *     <i>Hours:</i>
 *             <i>Digit</i>
 *             <i>Digit</i> <i>Digit</i>
 *     <i>Minutes:</i>
 *             <i>Digit</i> <i>Digit</i>
 *     <i>Digit:</i> one of
 *             <code>0 1 2 3 4 5 6 7 8 9</code></pre>
 *     <i>Hours</i> must be between 0 and 23, and <i>Minutes</i> must be between
 *     00 and 59. The format is locale independent and digits must be taken
 *     from the Basic Latin block of the Unicode standard.
 *     <p>For parsing, <a href="#rfc822timezone">RFC 822 time zones</a> are also
 *     accepted.<br><br></li>
 * <li><strong><a name="rfc822timezone">RFC 822 time zone:</a></strong>
 *     For formatting, the RFC 822 4-digit time zone format is used:
 *
 *     <pre>
 *     <i>RFC822TimeZone:</i>
 *             <i>Sign</i> <i>TwoDigitHours</i> <i>Minutes</i>
 *     <i>TwoDigitHours:</i>
 *             <i>Digit Digit</i></pre>
 *     <i>TwoDigitHours</i> must be between 00 and 23. Other definitions
 *     are as for <a href="#timezone">general time zones</a>.
 *
 *     <p>For parsing, <a href="#timezone">general time zones</a> are also
 *     accepted.
 * <li><strong><a name="iso8601timezone">ISO 8601 Time zone:</a></strong>
 *     The number of pattern letters designates the format for both formatting
 *     and parsing as follows:
 *     <pre>
 *     <i>ISO8601TimeZone:</i>
 *             <i>OneLetterISO8601TimeZone</i>
 *             <i>TwoLetterISO8601TimeZone</i>
 *             <i>ThreeLetterISO8601TimeZone</i>
 *     <i>OneLetterISO8601TimeZone:</i>
 *             <i>Sign</i> <i>TwoDigitHours</i>
 *             {@code Z}
 *     <i>TwoLetterISO8601TimeZone:</i>
 *             <i>Sign</i> <i>TwoDigitHours</i> <i>Minutes</i>
 *             {@code Z}
 *     <i>ThreeLetterISO8601TimeZone:</i>
 *             <i>Sign</i> <i>TwoDigitHours</i> {@code :} <i>Minutes</i>
 *             {@code Z}</pre>
 *     Other definitions are as for <a href="#timezone">general time zones</a> or
 *     <a href="#rfc822timezone">RFC 822 time zones</a>.
 *
 *     <p>For formatting, if the offset value from GMT is 0, {@code "Z"} is
 *     produced. If the number of pattern letters is 1, any fraction of an hour
 *     is ignored. For example, if the pattern is {@code "X"} and the time zone is
 *     {@code "GMT+05:30"}, {@code "+05"} is produced.
 *
 *     <p>For parsing, {@code "Z"} is parsed as the UTC time zone designator.
 *     <a href="#timezone">General time zones</a> are <em>not</em> accepted.
 *
 *     <p>If the number of pattern letters is 4 or more, {@link
 *     IllegalArgumentException} is thrown when constructing a {@code
 *     SimpleDateFormat} or {@linkplain #applyPattern(String) applying a
 *     pattern}.
 * </ul>
 * <code>SimpleDateFormat</code> also supports <em>localized date and time
 * pattern</em> strings. In these strings, the pattern letters described above
 * may be replaced with other, locale dependent, pattern letters.
 * <code>SimpleDateFormat</code> does not deal with the localization of text
 * other than the pattern letters; that's up to the client of the class.
 *
 * <h4>Examples</h4>
 *
 * The following examples show how date and time patterns are interpreted in
 * the U.S. locale. The given date and time are 2001-07-04 12:08:56 local time
 * in the U.S. Pacific Time time zone.
 * <blockquote>
 * <table border=0 cellspacing=3 cellpadding=0 summary="Examples of date and time patterns interpreted in the U.S. locale">
 *     <tr style="background-color: rgb(204, 204, 255);">
 *         <th align=left>Date and Time Pattern
 *         <th align=left>Result
 *     <tr>
 *         <td><code>"yyyy.MM.dd G 'at' HH:mm:ss z"</code>
 *         <td><code>2001.07.04 AD at 12:08:56 PDT</code>
 *     <tr style="background-color: rgb(238, 238, 255);">
 *         <td><code>"EEE, MMM d, ''yy"</code>
 *         <td><code>Wed, Jul 4, '01</code>
 *     <tr>
 *         <td><code>"h:mm a"</code>
 *         <td><code>12:08 PM</code>
 *     <tr style="background-color: rgb(238, 238, 255);">
 *         <td><code>"hh 'o''clock' a, zzzz"</code>
 *         <td><code>12 o'clock PM, Pacific Daylight Time</code>
 *     <tr>
 *         <td><code>"K:mm a, z"</code>
 *         <td><code>0:08 PM, PDT</code>
 *     <tr style="background-color: rgb(238, 238, 255);">
 *         <td><code>"yyyyy.MMMMM.dd GGG hh:mm aaa"</code>
 *         <td><code>02001.July.04 AD 12:08 PM</code>
 *     <tr>
 *         <td><code>"EEE, d MMM yyyy HH:mm:ss Z"</code>
 *         <td><code>Wed, 4 Jul 2001 12:08:56 -0700</code>
 *     <tr style="background-color: rgb(238, 238, 255);">
 *         <td><code>"yyMMddHHmmssZ"</code>
 *         <td><code>010704120856-0700</code>
 *     <tr>
 *         <td><code>"yyyy-MM-dd'T'HH:mm:ss.SSSZ"</code>
 *         <td><code>2001-07-04T12:08:56.235-0700</code>
 *     <tr style="background-color: rgb(238, 238, 255);">
 *         <td><code>"yyyy-MM-dd'T'HH:mm:ss.SSSXXX"</code>
 *         <td><code>2001-07-04T12:08:56.235-07:00</code>
 *     <tr>
 *         <td><code>"YYYY-'W'ww-u"</code>
 *         <td><code>2001-W27-3</code>
 * </table>
 * </blockquote>
 *
 * <h4><a name="synchronization">Synchronization</a></h4>
 *
 * <p>
 * Date formats are not synchronized.
 * It is recommended to create separate format instances for each thread.
 * If multiple threads access a format concurrently, it must be synchronized
 * externally.
 *
 * <p>
 * <code> SimpleDateFormat </code>是一个以区域设置敏感的方式格式化和解析日期的具体类。它允许格式化(日期和文本),解析(文本&rarr;日期)
 * 
 * <p>
 *  <code> SimpleDateFormat </code>允许您从选择任何用户定义的日期时间格式化模式开始。
 * 但是,建议您使用<code> getTimeInstance </code>,<code>这些类方法中的每一个都可以返回使用默认格式模式初始化的日期/时间格式化程序。
 * 您可以使用默认格式模式修改格式模式。 <code> applyPattern </code>方法有关使用这些方法的更多信息,请参阅{@link DateFormat}。
 * 
 * <h3>日期和时间模式</h3>
 * <p>
 *  日期和时间格式由<em>日期和时间模式</em>字符串指定日期和时间模式字符串中,从<code>'A'</code>到<code>'Z'</从<code>'a'</code>到<code>'z'</code>
 * 被解释为表示日期或时间字符串组成部分的模式字母文本可以使用单引号引起来(<code>'< / code>)以避免解释<code>"''"</code>表示单引号所有其他字符不解释;它们只是在格式化期间复
 * 制到输出字符串或在解析期间与输入字符串匹配。
 * <p>
 *  定义了以下模式字母(从<code>'A'</code>到<code>'Z'</code>和从<code>'a'</code>到<code>'z '</code>保留)：
 * <blockquote>
 * <table border=0 cellspacing=3 cellpadding=0 summary="Chart shows pattern letters, date/time component, presentation, and examples.">
 * <tr style="background-color: rgb(204, 204, 255);">
 * <th align = left>字母<th align = left>日期或时间组件<th align = left>演示<th align = left>示例
 * <tr>
 *  <td> <code> G </code> <td>时代指示符<td> <a href=\"#text\">文字</a> <td> <code> AD </code>
 * <tr style="background-color: rgb(238, 238, 255);">
 *  <td> <code> y </code> <td>年<td> <a href=\"#year\">年</a> <td> <code> 1996 </code>; <code> 96 </code>。
 * <tr>
 *  <td> <code> Y </code> <td>周年<td> <a href=\"#year\">年</a> <td> <code> 2009 </code> <code> 09 </code>。
 * <tr style="background-color: rgb(238, 238, 255);">
 *  <td> <code> M </code> <td>年份中的月份(与上下文相关)<td> <a href=\"#month\">月</a> <td> <code> July </code>; <code>
 *  Jul </code>; <code> 07 </code>。
 * <tr>
 *  <td> <code> L </code> <td>每年的月份(独立表格)<td> <a href=\"#month\">月</a> <td> <code> July </code>; <code> 
 * Jul </code>; <code> 07 </code>。
 * <tr style="background-color: rgb(238, 238, 255);">
 *  <td> <code> w </code> <td>年份周<td> <a href=\"#number\">数字</a> <td> <code> 27 </code>
 * <tr>
 * <td> <code> W </code> <td>每月一周<td> <a href=\"#number\">数字</a> <td> <code> 2 </code>
 * <tr style="background-color: rgb(238, 238, 255);">
 *  <td> <code> D </code> <td>一年中的某一天<td> <a href=\"#number\"> Number </a> <td> <code> 189 </code>
 * <tr>
 *  <td> <code> d </code> <td>每月的某一天<td> <a href=\"#number\">数字</a> <td> <code> 10 </code>
 * <tr style="background-color: rgb(238, 238, 255);">
 *  <td> <code> F </code> <td>每月的星期几<td> <a href=\"#number\">数字</a> <td> <code> 2 </code>
 * <tr>
 *  <td> <code> E </code> <td>星期名称<td> <a href=\"#text\">短讯</a> <td> <code>星期二</code>; <code> Tue </code>
 * 。
 * <tr style="background-color: rgb(238, 238, 255);">
 *  <td> <code> u </code> <td>星期几(1 =星期一,7 =星期日)<td> <a href=\"#number\">号码</a> <td> <code > 1 </code>
 * <tr>
 *  <td> <code> a </code> <td> Am / pm marker <td> <a href=\"#text\"> Text </a> <td> <code> PM </code>
 * <tr style="background-color: rgb(238, 238, 255);">
 *  <td> <code> H </code> <td>一天中的时间(0-23)<td> <a href=\"#number\">号码</a> <td> <code> 0 </code>
 * <tr>
 * <td> <code> k </code> <td>一天中的时间(1-24)<td> <a href=\"#number\">号码</a> <td> <code> 24 </code>
 * <tr style="background-color: rgb(238, 238, 255);">
 *  <td> <code> K </code> <td>小时am / pm(0-11)<td> <a href=\"#number\"> Number </a> <td> <code> 0 <代码>
 * <tr>
 *  <td> <code> h </code> <td>小时at am / pm(1-12)<td> <a href=\"#number\"> Number </a> <td> <code>代码>
 * <tr style="background-color: rgb(238, 238, 255);">
 *  <td> <code> m </code> <td>每小时分钟<td> <a href=\"#number\">数字</a> <td> <code> 30 </code>
 * <tr>
 *  <td> <code> </code> <td>秒钟<td> <a href=\"#number\">数字</a> <td> <code> 55 </code>
 * <tr style="background-color: rgb(238, 238, 255);">
 *  <td> <code> S </code> <td>毫秒<td> <a href=\"#number\">数字</a> <td> <code> 978 </code>
 * <tr>
 *  <td> <code> z </code> <td>时区<td> <a href=\"#timezone\">一般时区</a> <td> <code>太平洋标准时间</code>; <code> PS
 * T </code>; <code> GMT-08：00 </code>。
 * <tr style="background-color: rgb(238, 238, 255);">
 * <td> <code> Z </code> <td>时区<td> <a href=\"#rfc822timezone\"> RFC 822时区</a> <td> <code> -0800 </code>
 * 。
 * <tr>
 *  <td> <code> X </code> <td>时区<td> <a href=\"#iso8601timezone\"> ISO 8601时区</a> <td> <code> -08 </code>
 * ; <code> -0800 </code>; <code> -08：00 </code>。
 * </table>
 * </blockquote>
 *  模式字母通常重复,因为它们的数字决定了精确的演示：
 * <ul>
 * <li> <strong> <a name=\"text\">文字：</a> </strong>对于格式化,如果图案字母数量为4或更多,则使用完整表单;如果可用,则使用缩写形式。
 * 对于解析,两种形式都被接受,与模式字母数无关<br> <br> </li> <li> <strong> <a name=\"number\"> Number ：</a> </strong>对于格式化,花
 * 样字母数是最小的数字数,而较短的数字是零填充到这个数量。
 * <li> <strong> <a name=\"text\">文字：</a> </strong>对于格式化,如果图案字母数量为4或更多,则使用完整表单;如果可用,则使用缩写形式。
 * 对于解析,花样字母的数目被忽略,除非需要分开两个<br> <br> </li> <li> <strong> <a name=\"year\">年份：</a> </strong>如果格式化程序的{@link #getCalendar()Calendar}
 * 格里历日历,应用以下规则<br>。
 * <li> <strong> <a name=\"text\">文字：</a> </strong>对于格式化,如果图案字母数量为4或更多,则使用完整表单;如果可用,则使用缩写形式。
 * <ul>
 * 例如,使用在1997年1月1日创建的"MM / dd / yy"和<code> SimpleDateFormat </code>实例的模式,字符串"01/11/12"将被解释为2012年1月11日字符串
 * "05/04/64"将被解释为1964年5月4日在解析期间,只有由{@link Character#isDigit(char)}定义的仅由两位数字组成的字符串将被解析为默认世纪Any其他数字字符串,例如
 * 一个数字字符串,三个或更多个数字字符串或不是所有数字(例如,"-1")的两个数字字符串被解释为"01/02/3"或"01/02/003"被解析,使用与Jan 2,3 AD相同的模式。
 * 同样,"01/02 / -3"被解析为Jan 2,4 BC。
 * </ul>
 * 否则,将应用日历系统特定的表单对于格式化和解析,如果模式字母数为4或更多,则使用特定于日历的{@linkplain Calendar#LONG长形式}否则,特定日历{@linkplain Calendar#SHORT短或缩写形式}
 * 被使用<br>。
 * <br>
 * 如果指定了周年{@code'Y'}且{@linkplain #getCalendar()日历}不支持任何<a href=\"/util/GregorianCalendarhtml#week_year\">
 * 周年</a>,则日历使用{@link DateFormat#getCalendar()getCalendar()} {@ link javautilCalendar#isWeekDateSupported()isWeekDateSupported()}
 *  </br> <br> </li> <li> <strong> <a name=\"month\">月份：</a> </strong>如果图案字母数量为3个或更多,则将月份解释为< a href ="#text">
 *  text </a>;否则,系统会将其解释为<a href=\"#number\">号码</a> <br>。
 * <ul>
 * <li>信件<em> M </em>会产生上下文相关的月份名称,例如名称的嵌入形式如果{@code DateFormatSymbols}已使用建构函式{@link #SimpleDateFormat(String,DateFormatSymbols)或方法{@link #setDateFormatSymbols(DateFormatSymbols)}
 * 时,使用{@code DateFormatSymbols}提供的月份名称</li> <li>信件</em>产生月份名称的独立形式< li>。
 * </ul>
 *  <br> </li> <li> <strong> <a name=\"timezone\">一般时区：</a> </strong>时区被解释为<a href=\"#text\"> text </strong>
 *  a>如果它们具有名称对于表示GMT偏移值的时区,使用以下语法：。
 * <pre>
 * <a name=\"GMTOffsetTimeZone\"> <i> GMTOffsetTimeZone：</i> </a> <code> GMT </code> <i>签署</i> <i>小时</i>
 *  <code>：< / code> <i>分钟</i> <i>签名：</i> <code> +  -  </code> <i>小时之一：</i> <i> >数字</i> <i> </i> <i> </i>
 *  <i> </i> <code> 0 1 2 3 4 5 6 7 8 9 </code> </p> <i>小时</i>必须介于0和23之间,而<i>分钟</i> 59格式与区域设置无关,数字必须取自Un
 * icode标准的基本拉丁语区块<p>对于解析,还接受<a href=\"#rfc822timezone\"> RFC 822时区</a> <br> < br> </li> <li> <strong> <a name=\"rfc822timezone\">
 *  RFC 822时区：</a> </strong>对于格式化,使用RFC 822 4位时区格式：。
 * 
 * <pre>
 * <i> RFC822TimeZone：</i> <i> </i> <i> TwoDigitHours </i> <i>分钟</i> <i> TwoDigitHours：</i> <i> > </pre>
 *  <i> TwoDigitHours </i>必须介于00和23之间其他定义与<a href=\"#timezone\">一般时区</a>。
 * 
 *  <p>为了解析,还接受<a href=\"#timezone\">一般时区</a> <li> <strong> <a name=\"iso8601timezone\"> ISO 8601时区：</a>
 *  < strong>模式字母数指定格式化和解析的格式如下：。
 * <pre>
 * <i> ISO8601TimeZone：</i> <i> OneLetterISO8601TimeZone </i> <i> TwoLetterISO8601TimeZone </i> <i> Thre
 * eLetterISO8601TimeZone </i> <i> OneLetterISO8601TimeZone：</i> <i> <i> TwoDigitHours </i> {@code Z} <i>
 *  TwoLetterISO8601TimeZone：</i> <i>签署</i> <i> TwoDigitHours </i> <i> } <i> ThreeLetterISO8601TimeZone：
 * </i> <i>签名</i> <i> TwoDigitHours </i> {@code：} <i>分钟</i> {@code Z} </与<a href=\"#timezone\">一般时区</a>或
 * <a href=\"#rfc822timezone\"> RFC 822时区</a>相同。
 * 
 *  <p>对于格式化,如果GMT的偏移值为0,则生成{@code"Z"}如果模式字母数为1,则忽略小时的任何小数部分例如,如果模式为{@code "X"},时区为{@code"GMT + 05：30"},
 * 则产生{@code"+05"}。
 * 
 * <p>对于解析,{@code"Z"}被解析为UTC时区指示符<a href=\"#timezone\">一般时区</a>未接受</em>
 * 
 *  <p>如果模式字母数量为4或更多,则在构造{@code SimpleDateFormat}或{@linkplain #applyPattern(String)应用模式}时抛出{@link IllegalArgumentException}
 * 。
 * </ul>
 *  <code> SimpleDateFormat </code>也支持<em>本地化日期和时间模式</em>字符串在这些字符串中,上面描述的模式字母可以用其他的,依赖于locale的模式字母替换<code>
 *  SimpleDateFormat </code >不处理除了模式字母以外的文本的本地化;这取决于类的客户端。
 * 
 *  <h4>示例</h4>
 * 
 * 以下示例显示在美国地区如何解释日期和时间模式给定的日期和时间为2001-07-04 12:08:56美国太平洋时间当地时间时区
 * <blockquote>
 * <table border=0 cellspacing=3 cellpadding=0 summary="Examples of date and time patterns interpreted in the U.S. locale">
 * <tr style="background-color: rgb(204, 204, 255);">
 *  <th align = left>日期和时间模式<th align = left>结果
 * <tr>
 *  <td> <code>"yyyyMMdd G'at'HH：mm：ss z"</code> <td> <code> 20010704 AD at 12:08:56 PDT </code>
 * <tr style="background-color: rgb(238, 238, 255);">
 *  <td> <code>"EEE,MMM d,''yy"</code> <td> <code> Wed,Jul 4,
 * <tr>
 *  <td> <code>"h：mm a"</code> <td> <code> 12:08 PM </code>
 * <tr style="background-color: rgb(238, 238, 255);">
 *  <td> <code>"hh'o''clock'a,zzzz"</code> <td> <code>太平洋夏令时间下午12点</code>
 * <tr>
 *  <td> <code>"K：mm a,z"</code> <td> <code> 0:08 PM,PDT </code>
 * <tr style="background-color: rgb(238, 238, 255);">
 *  <td> <code>"yyyyyMMMMMdd GGG hh：mm aaa"</code> <td> <code> 02001July04 AD 12:08 PM </code>
 * <tr>
 * <td> <code>"EEE,d MMM yyyy HH：mm：ss Z"</code> <td> <code> Wed,4 Jul 2001 12:08:56 -0700 </code>
 * <tr style="background-color: rgb(238, 238, 255);">
 *  <td> <code>"yyMMddHHmmssZ"</code> <td> <code> 010704120856-0700 </code>
 * <tr>
 *  <td> <code>"yyyy-MM-dd'T'HH：mm：ssSSSZ"</code> <td> <code> 2001-07-04T12：08：56235-0700 </code>
 * <tr style="background-color: rgb(238, 238, 255);">
 *  <td> <code>"yyyy-MM-dd'T'HH：mm：ssSSSXXX"</code> <td> <code> 2001-07-04T12：08：56235-07：00 </code>
 * <tr>
 *  <td> <code>"YYYY-'W'ww-u"</code> <td> <code> 2001-W27-3 </code>
 * </table>
 * </blockquote>
 * 
 *  <h4> <a name=\"synchronization\">同步</a> </h4>
 * 
 * <p>
 *  日期格式不同步建议为每个线程创建单独的格式实例如果多个线程并发访问格式,则必须在外部同步
 * 
 * 
 * @see          <a href="https://docs.oracle.com/javase/tutorial/i18n/format/simpleDateFormat.html">Java Tutorial</a>
 * @see          java.util.Calendar
 * @see          java.util.TimeZone
 * @see          DateFormat
 * @see          DateFormatSymbols
 * @author       Mark Davis, Chen-Lieh Huang, Alan Liu
 */
public class SimpleDateFormat extends DateFormat {

    // the official serial version ID which says cryptically
    // which version we're compatible with
    static final long serialVersionUID = 4774881970558875024L;

    // the internal serial version which says which version was written
    // - 0 (default) for version up to JDK 1.1.3
    // - 1 for version from JDK 1.1.4, which includes a new field
    static final int currentSerialVersion = 1;

    /**
     * The version of the serialized data on the stream.  Possible values:
     * <ul>
     * <li><b>0</b> or not present on stream: JDK 1.1.3.  This version
     * has no <code>defaultCenturyStart</code> on stream.
     * <li><b>1</b> JDK 1.1.4 or later.  This version adds
     * <code>defaultCenturyStart</code>.
     * </ul>
     * When streaming out this class, the most recent format
     * and the highest allowable <code>serialVersionOnStream</code>
     * is written.
     * <p>
     *  流上的序列化数据的版本可能的值：
     * <ul>
     * <li> <b> 0 </b>或不在流中：JDK 113此版本在流上没有<code> defaultCenturyStart </code> <li> <b> 1 </b> JDK 114或更高版本此版
     * 本添加<code> defaultCenturyStart </code>。
     * </ul>
     *  当流出这个类时,写入最近的格式和最高允许的<code> serialVersionOnStream </code>
     * 
     * 
     * @serial
     * @since JDK1.1.4
     */
    private int serialVersionOnStream = currentSerialVersion;

    /**
     * The pattern string of this formatter.  This is always a non-localized
     * pattern.  May not be null.  See class documentation for details.
     * <p>
     *  此格式化程序的模式字符串这始终是非本地化模式可能不是null有关详细信息,请参阅类文档
     * 
     * 
     * @serial
     */
    private String pattern;

    /**
     * Saved numberFormat and pattern.
     * <p>
     *  保存的numberFormat和pattern
     * 
     * 
     * @see SimpleDateFormat#checkNegativeNumberExpression
     */
    transient private NumberFormat originalNumberFormat;
    transient private String originalNumberPattern;

    /**
     * The minus sign to be used with format and parse.
     * <p>
     *  用于格式和解析的减号
     * 
     */
    transient private char minusSign = '-';

    /**
     * True when a negative sign follows a number.
     * (True as default in Arabic.)
     * <p>
     *  当数字后面带负号时为真(阿拉伯语为默认值为True)
     * 
     */
    transient private boolean hasFollowingMinusSign = false;

    /**
     * True if standalone form needs to be used.
     * <p>
     *  如果需要使用独立表单,则为true
     * 
     */
    transient private boolean forceStandaloneForm = false;

    /**
     * The compiled pattern.
     * <p>
     *  编译模式
     * 
     */
    transient private char[] compiledPattern;

    /**
     * Tags for the compiled pattern.
     * <p>
     *  编译模式的标签
     * 
     */
    private final static int TAG_QUOTE_ASCII_CHAR       = 100;
    private final static int TAG_QUOTE_CHARS            = 101;

    /**
     * Locale dependent digit zero.
     * <p>
     *  区域相关数字零
     * 
     * 
     * @see #zeroPaddingNumber
     * @see java.text.DecimalFormatSymbols#getZeroDigit
     */
    transient private char zeroDigit;

    /**
     * The symbols used by this formatter for week names, month names,
     * etc.  May not be null.
     * <p>
     * 此格式化程序用于星期名称,月份名称等的符号可能不为空
     * 
     * 
     * @serial
     * @see java.text.DateFormatSymbols
     */
    private DateFormatSymbols formatData;

    /**
     * We map dates with two-digit years into the century starting at
     * <code>defaultCenturyStart</code>, which may be any date.  May
     * not be null.
     * <p>
     *  我们将两位数字的日期映射到从<code> defaultCenturyStart </code>开始的世纪,这可能是任何日期可能不是null
     * 
     * 
     * @serial
     * @since JDK1.1.4
     */
    private Date defaultCenturyStart;

    transient private int defaultCenturyStartYear;

    private static final int MILLIS_PER_MINUTE = 60 * 1000;

    // For time zones that have no names, use strings GMT+minutes and
    // GMT-minutes. For instance, in France the time zone is GMT+60.
    private static final String GMT = "GMT";

    /**
     * Cache NumberFormat instances with Locale key.
     * <p>
     *  使用Locale键缓存NumberFormat实例
     * 
     */
    private static final ConcurrentMap<Locale, NumberFormat> cachedNumberFormatData
        = new ConcurrentHashMap<>(3);

    /**
     * The Locale used to instantiate this
     * <code>SimpleDateFormat</code>. The value may be null if this object
     * has been created by an older <code>SimpleDateFormat</code> and
     * deserialized.
     *
     * <p>
     *  Locale用于实例化此<code> SimpleDateFormat </code>如果此对象由较旧的<code> SimpleDateFormat </code>创建并且反序列化
     * 
     * 
     * @serial
     * @since 1.6
     */
    private Locale locale;

    /**
     * Indicates whether this <code>SimpleDateFormat</code> should use
     * the DateFormatSymbols. If true, the format and parse methods
     * use the DateFormatSymbols values. If false, the format and
     * parse methods call Calendar.getDisplayName or
     * Calendar.getDisplayNames.
     * <p>
     *  指示此<代码> SimpleDateFormat </code>是否应使用DateFormatSymbols如果为true,格式和解析方法使用DateFormatSymbols值如果为false,格式
     * 和解析方法将调用CalendargetDisplayName或CalendargetDisplayNames。
     * 
     */
    transient boolean useDateFormatSymbols;

    /**
     * Constructs a <code>SimpleDateFormat</code> using the default pattern and
     * date format symbols for the default
     * {@link java.util.Locale.Category#FORMAT FORMAT} locale.
     * <b>Note:</b> This constructor may not support all locales.
     * For full coverage, use the factory methods in the {@link DateFormat}
     * class.
     * <p>
     * 使用默认{@link javautilLocaleCategory#FORMAT FORMAT}语言环境的默认模式和日期格式符号构造<code> SimpleDateFormat </>> <b>注意：
     * </b>此构造函数可能不支持所有语言环境对于完全覆盖,请在{@link DateFormat}类中使用工厂方法。
     * 
     */
    public SimpleDateFormat() {
        this("", Locale.getDefault(Locale.Category.FORMAT));
        applyPatternImpl(LocaleProviderAdapter.getResourceBundleBased().getLocaleResources(locale)
                         .getDateTimePattern(SHORT, SHORT, calendar));
    }

    /**
     * Constructs a <code>SimpleDateFormat</code> using the given pattern and
     * the default date format symbols for the default
     * {@link java.util.Locale.Category#FORMAT FORMAT} locale.
     * <b>Note:</b> This constructor may not support all locales.
     * For full coverage, use the factory methods in the {@link DateFormat}
     * class.
     * <p>This is equivalent to calling
     * {@link #SimpleDateFormat(String, Locale)
     *     SimpleDateFormat(pattern, Locale.getDefault(Locale.Category.FORMAT))}.
     *
     * <p>
     *  使用默认{@link javautilLocaleCategory#FORMAT FORMAT}语言环境的给定模式和默认日期格式符号构造<code> SimpleDateFormat </>> <b>
     * 注意：</b>此构造函数可能不支持所有语言环境覆盖,在{@link DateFormat}类中使用工厂方法<p>这等效于调用{@link #SimpleDateFormat(String,Locale)SimpleDateFormat(pattern,LocalegetDefault(LocaleCategoryFORMAT))}
     * 。
     * 
     * 
     * @see java.util.Locale#getDefault(java.util.Locale.Category)
     * @see java.util.Locale.Category#FORMAT
     * @param pattern the pattern describing the date and time format
     * @exception NullPointerException if the given pattern is null
     * @exception IllegalArgumentException if the given pattern is invalid
     */
    public SimpleDateFormat(String pattern)
    {
        this(pattern, Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Constructs a <code>SimpleDateFormat</code> using the given pattern and
     * the default date format symbols for the given locale.
     * <b>Note:</b> This constructor may not support all locales.
     * For full coverage, use the factory methods in the {@link DateFormat}
     * class.
     *
     * <p>
     * 使用给定模式和给定语言环境的默认日期格式符号构造<code> SimpleDateFormat </b> <b>注意：</b>此构造函数可能不支持所有语言环境对于完全覆盖,请使用{@link DateFormat}
     * 类。
     * 
     * 
     * @param pattern the pattern describing the date and time format
     * @param locale the locale whose date format symbols should be used
     * @exception NullPointerException if the given pattern or locale is null
     * @exception IllegalArgumentException if the given pattern is invalid
     */
    public SimpleDateFormat(String pattern, Locale locale)
    {
        if (pattern == null || locale == null) {
            throw new NullPointerException();
        }

        initializeCalendar(locale);
        this.pattern = pattern;
        this.formatData = DateFormatSymbols.getInstanceRef(locale);
        this.locale = locale;
        initialize(locale);
    }

    /**
     * Constructs a <code>SimpleDateFormat</code> using the given pattern and
     * date format symbols.
     *
     * <p>
     *  使用给定的模式和日期格式符号构造一个<code> SimpleDateFormat </code>
     * 
     * 
     * @param pattern the pattern describing the date and time format
     * @param formatSymbols the date format symbols to be used for formatting
     * @exception NullPointerException if the given pattern or formatSymbols is null
     * @exception IllegalArgumentException if the given pattern is invalid
     */
    public SimpleDateFormat(String pattern, DateFormatSymbols formatSymbols)
    {
        if (pattern == null || formatSymbols == null) {
            throw new NullPointerException();
        }

        this.pattern = pattern;
        this.formatData = (DateFormatSymbols) formatSymbols.clone();
        this.locale = Locale.getDefault(Locale.Category.FORMAT);
        initializeCalendar(this.locale);
        initialize(this.locale);
        useDateFormatSymbols = true;
    }

    /* Initialize compiledPattern and numberFormat fields */
    private void initialize(Locale loc) {
        // Verify and compile the given pattern.
        compiledPattern = compile(pattern);

        /* try the cache first */
        numberFormat = cachedNumberFormatData.get(loc);
        if (numberFormat == null) { /* cache miss */
            numberFormat = NumberFormat.getIntegerInstance(loc);
            numberFormat.setGroupingUsed(false);

            /* update cache */
            cachedNumberFormatData.putIfAbsent(loc, numberFormat);
        }
        numberFormat = (NumberFormat) numberFormat.clone();

        initializeDefaultCentury();
    }

    private void initializeCalendar(Locale loc) {
        if (calendar == null) {
            assert loc != null;
            // The format object must be constructed using the symbols for this zone.
            // However, the calendar should use the current default TimeZone.
            // If this is not contained in the locale zone strings, then the zone
            // will be formatted using generic GMT+/-H:MM nomenclature.
            calendar = Calendar.getInstance(TimeZone.getDefault(), loc);
        }
    }

    /**
     * Returns the compiled form of the given pattern. The syntax of
     * the compiled pattern is:
     * <blockquote>
     * CompiledPattern:
     *     EntryList
     * EntryList:
     *     Entry
     *     EntryList Entry
     * Entry:
     *     TagField
     *     TagField data
     * TagField:
     *     Tag Length
     *     TaggedData
     * Tag:
     *     pattern_char_index
     *     TAG_QUOTE_CHARS
     * Length:
     *     short_length
     *     long_length
     * TaggedData:
     *     TAG_QUOTE_ASCII_CHAR ascii_char
     *
     * </blockquote>
     *
     * where `short_length' is an 8-bit unsigned integer between 0 and
     * 254.  `long_length' is a sequence of an 8-bit integer 255 and a
     * 32-bit signed integer value which is split into upper and lower
     * 16-bit fields in two char's. `pattern_char_index' is an 8-bit
     * integer between 0 and 18. `ascii_char' is an 7-bit ASCII
     * character value. `data' depends on its Tag value.
     * <p>
     * If Length is short_length, Tag and short_length are packed in a
     * single char, as illustrated below.
     * <blockquote>
     *     char[0] = (Tag << 8) | short_length;
     * </blockquote>
     *
     * If Length is long_length, Tag and 255 are packed in the first
     * char and a 32-bit integer, as illustrated below.
     * <blockquote>
     *     char[0] = (Tag << 8) | 255;
     *     char[1] = (char) (long_length >>> 16);
     *     char[2] = (char) (long_length & 0xffff);
     * </blockquote>
     * <p>
     * If Tag is a pattern_char_index, its Length is the number of
     * pattern characters. For example, if the given pattern is
     * "yyyy", Tag is 1 and Length is 4, followed by no data.
     * <p>
     * If Tag is TAG_QUOTE_CHARS, its Length is the number of char's
     * following the TagField. For example, if the given pattern is
     * "'o''clock'", Length is 7 followed by a char sequence of
     * <code>o&nbs;'&nbs;c&nbs;l&nbs;o&nbs;c&nbs;k</code>.
     * <p>
     * TAG_QUOTE_ASCII_CHAR is a special tag and has an ASCII
     * character in place of Length. For example, if the given pattern
     * is "'o'", the TaggedData entry is
     * <code>((TAG_QUOTE_ASCII_CHAR&nbs;<<&nbs;8)&nbs;|&nbs;'o')</code>.
     *
     * <p>
     *  返回给定模式的编译形式编译模式的语法是：
     * <blockquote>
     *  CompiledPattern：EntryList EntryList：Entry EntryList Entry Entry：TagField TagField data TagField：Tag 
     * Length TaggedData Tag：pattern_char_index TAG_QUOTE_CHARS Length：short_length long_length TaggedData：T
     * AG_QUOTE_ASCII_CHAR ascii_char。
     * 
     * </blockquote>
     * 
     * 其中`short_length'是0和254之间的8位无符号整数`long_length'是8位整数255和32位有符号整数值的序列,它被分成两个char的高16位和低16位字段`pattern_cha
     * r_index'是0和18之间的8位整数`ascii_char'是一个7位ASCII字符值`data'取决于它的标签值。
     * <p>
     *  如果Length是short_length,则Tag和short_length被打包在单个char中,如下所示
     * <blockquote>
     *  char [0] =(Tag << 8)| short_length;
     * </blockquote>
     * 
     *  如果Length是long_length,则标签和255被打包在第一个char和32位整数中,如下所示
     * <blockquote>
     *  char [0] =(Tag << 8)| 255; char [1] =(char)(long_length >>> 16); char [2] =(char)(long_length&0xffff
     * );。
     * </blockquote>
     * <p>
     * 如果Tag是pattern_char_index,则其长度是模式字符的数量。例如,如果给定模式是"yyyy",则Tag为1,Length为4,后面没有数据
     * <p>
     *  如果Tag是TAG_QUOTE_CHARS,它的长度是跟在TagField之后的char的数量。
     * 例如,如果给定模式是"'o''clock'",Length为7,后面跟着一个char序列<code> o&nbs;'&nbs; c& ; l&nbs; o&nbs; c&nbs; k </code>。
     * <p>
     *  TAG_QUOTE_ASCII_CHAR是一个特殊的标记,并且具有ASCII字符代替长度。
     * 例如,如果给定模式是"o",TaggedData条目是<code>((TAG_QUOTE_ASCII_CHAR&nbs; <<&nbs; 8)&nbs; |& 'o')</code>。
     * 
     * 
     * @exception NullPointerException if the given pattern is null
     * @exception IllegalArgumentException if the given pattern is invalid
     */
    private char[] compile(String pattern) {
        int length = pattern.length();
        boolean inQuote = false;
        StringBuilder compiledCode = new StringBuilder(length * 2);
        StringBuilder tmpBuffer = null;
        int count = 0, tagcount = 0;
        int lastTag = -1, prevTag = -1;

        for (int i = 0; i < length; i++) {
            char c = pattern.charAt(i);

            if (c == '\'') {
                // '' is treated as a single quote regardless of being
                // in a quoted section.
                if ((i + 1) < length) {
                    c = pattern.charAt(i + 1);
                    if (c == '\'') {
                        i++;
                        if (count != 0) {
                            encode(lastTag, count, compiledCode);
                            tagcount++;
                            prevTag = lastTag;
                            lastTag = -1;
                            count = 0;
                        }
                        if (inQuote) {
                            tmpBuffer.append(c);
                        } else {
                            compiledCode.append((char)(TAG_QUOTE_ASCII_CHAR << 8 | c));
                        }
                        continue;
                    }
                }
                if (!inQuote) {
                    if (count != 0) {
                        encode(lastTag, count, compiledCode);
                        tagcount++;
                        prevTag = lastTag;
                        lastTag = -1;
                        count = 0;
                    }
                    if (tmpBuffer == null) {
                        tmpBuffer = new StringBuilder(length);
                    } else {
                        tmpBuffer.setLength(0);
                    }
                    inQuote = true;
                } else {
                    int len = tmpBuffer.length();
                    if (len == 1) {
                        char ch = tmpBuffer.charAt(0);
                        if (ch < 128) {
                            compiledCode.append((char)(TAG_QUOTE_ASCII_CHAR << 8 | ch));
                        } else {
                            compiledCode.append((char)(TAG_QUOTE_CHARS << 8 | 1));
                            compiledCode.append(ch);
                        }
                    } else {
                        encode(TAG_QUOTE_CHARS, len, compiledCode);
                        compiledCode.append(tmpBuffer);
                    }
                    inQuote = false;
                }
                continue;
            }
            if (inQuote) {
                tmpBuffer.append(c);
                continue;
            }
            if (!(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z')) {
                if (count != 0) {
                    encode(lastTag, count, compiledCode);
                    tagcount++;
                    prevTag = lastTag;
                    lastTag = -1;
                    count = 0;
                }
                if (c < 128) {
                    // In most cases, c would be a delimiter, such as ':'.
                    compiledCode.append((char)(TAG_QUOTE_ASCII_CHAR << 8 | c));
                } else {
                    // Take any contiguous non-ASCII alphabet characters and
                    // put them in a single TAG_QUOTE_CHARS.
                    int j;
                    for (j = i + 1; j < length; j++) {
                        char d = pattern.charAt(j);
                        if (d == '\'' || (d >= 'a' && d <= 'z' || d >= 'A' && d <= 'Z')) {
                            break;
                        }
                    }
                    compiledCode.append((char)(TAG_QUOTE_CHARS << 8 | (j - i)));
                    for (; i < j; i++) {
                        compiledCode.append(pattern.charAt(i));
                    }
                    i--;
                }
                continue;
            }

            int tag;
            if ((tag = DateFormatSymbols.patternChars.indexOf(c)) == -1) {
                throw new IllegalArgumentException("Illegal pattern character " +
                                                   "'" + c + "'");
            }
            if (lastTag == -1 || lastTag == tag) {
                lastTag = tag;
                count++;
                continue;
            }
            encode(lastTag, count, compiledCode);
            tagcount++;
            prevTag = lastTag;
            lastTag = tag;
            count = 1;
        }

        if (inQuote) {
            throw new IllegalArgumentException("Unterminated quote");
        }

        if (count != 0) {
            encode(lastTag, count, compiledCode);
            tagcount++;
            prevTag = lastTag;
        }

        forceStandaloneForm = (tagcount == 1 && prevTag == PATTERN_MONTH);

        // Copy the compiled pattern to a char array
        int len = compiledCode.length();
        char[] r = new char[len];
        compiledCode.getChars(0, len, r, 0);
        return r;
    }

    /**
     * Encodes the given tag and length and puts encoded char(s) into buffer.
     * <p>
     *  对给定的标签和长度进行编码,并将编码的字符放入缓冲区
     * 
     */
    private static void encode(int tag, int length, StringBuilder buffer) {
        if (tag == PATTERN_ISO_ZONE && length >= 4) {
            throw new IllegalArgumentException("invalid ISO 8601 format: length=" + length);
        }
        if (length < 255) {
            buffer.append((char)(tag << 8 | length));
        } else {
            buffer.append((char)((tag << 8) | 0xff));
            buffer.append((char)(length >>> 16));
            buffer.append((char)(length & 0xffff));
        }
    }

    /* Initialize the fields we use to disambiguate ambiguous years. Separate
     * so we can call it from readObject().
     * <p>
     *  所以我们可以从readObject()
     * 
     */
    private void initializeDefaultCentury() {
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add( Calendar.YEAR, -80 );
        parseAmbiguousDatesAsAfter(calendar.getTime());
    }

    /* Define one-century window into which to disambiguate dates using
     * two-digit years.
     * <p>
     *  两位数年
     * 
     */
    private void parseAmbiguousDatesAsAfter(Date startDate) {
        defaultCenturyStart = startDate;
        calendar.setTime(startDate);
        defaultCenturyStartYear = calendar.get(Calendar.YEAR);
    }

    /**
     * Sets the 100-year period 2-digit years will be interpreted as being in
     * to begin on the date the user specifies.
     *
     * <p>
     * 设置100年期间2位数年份将被解释为从用户指定的日期开始
     * 
     * 
     * @param startDate During parsing, two digit years will be placed in the range
     * <code>startDate</code> to <code>startDate + 100 years</code>.
     * @see #get2DigitYearStart
     * @since 1.2
     */
    public void set2DigitYearStart(Date startDate) {
        parseAmbiguousDatesAsAfter(new Date(startDate.getTime()));
    }

    /**
     * Returns the beginning date of the 100-year period 2-digit years are interpreted
     * as being within.
     *
     * <p>
     *  返回100年期间的开始日期,2位数年份被解释为在内
     * 
     * 
     * @return the start of the 100-year period into which two digit years are
     * parsed
     * @see #set2DigitYearStart
     * @since 1.2
     */
    public Date get2DigitYearStart() {
        return (Date) defaultCenturyStart.clone();
    }

    /**
     * Formats the given <code>Date</code> into a date/time string and appends
     * the result to the given <code>StringBuffer</code>.
     *
     * <p>
     *  将给定的<code> Date </code>格式化为日期/时间字符串,并将结果追加到给定的<code> StringBuffer </code>
     * 
     * 
     * @param date the date-time value to be formatted into a date-time string.
     * @param toAppendTo where the new date-time text is to be appended.
     * @param pos the formatting position. On input: an alignment field,
     * if desired. On output: the offsets of the alignment field.
     * @return the formatted date-time string.
     * @exception NullPointerException if the given {@code date} is {@code null}.
     */
    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo,
                               FieldPosition pos)
    {
        pos.beginIndex = pos.endIndex = 0;
        return format(date, toAppendTo, pos.getFieldDelegate());
    }

    // Called from Format after creating a FieldDelegate
    private StringBuffer format(Date date, StringBuffer toAppendTo,
                                FieldDelegate delegate) {
        // Convert input date to time field list
        calendar.setTime(date);

        boolean useDateFormatSymbols = useDateFormatSymbols();

        for (int i = 0; i < compiledPattern.length; ) {
            int tag = compiledPattern[i] >>> 8;
            int count = compiledPattern[i++] & 0xff;
            if (count == 255) {
                count = compiledPattern[i++] << 16;
                count |= compiledPattern[i++];
            }

            switch (tag) {
            case TAG_QUOTE_ASCII_CHAR:
                toAppendTo.append((char)count);
                break;

            case TAG_QUOTE_CHARS:
                toAppendTo.append(compiledPattern, i, count);
                i += count;
                break;

            default:
                subFormat(tag, count, delegate, toAppendTo, useDateFormatSymbols);
                break;
            }
        }
        return toAppendTo;
    }

    /**
     * Formats an Object producing an <code>AttributedCharacterIterator</code>.
     * You can use the returned <code>AttributedCharacterIterator</code>
     * to build the resulting String, as well as to determine information
     * about the resulting String.
     * <p>
     * Each attribute key of the AttributedCharacterIterator will be of type
     * <code>DateFormat.Field</code>, with the corresponding attribute value
     * being the same as the attribute key.
     *
     * <p>
     *  格式化产生<code> AttributedCharacterIterator </code>的对象您可以使用返回的<code> AttributedCharacterIterator </code>
     * 构建生成的String,以及确定有关生成的String的信息。
     * <p>
     *  AttributedCharacterIterator的每个属性键的类型为<code> DateFormatField </code>,对应的属性值与属性键相同
     * 
     * 
     * @exception NullPointerException if obj is null.
     * @exception IllegalArgumentException if the Format cannot format the
     *            given object, or if the Format's pattern string is invalid.
     * @param obj The object to format
     * @return AttributedCharacterIterator describing the formatted value.
     * @since 1.4
     */
    @Override
    public AttributedCharacterIterator formatToCharacterIterator(Object obj) {
        StringBuffer sb = new StringBuffer();
        CharacterIteratorFieldDelegate delegate = new
                         CharacterIteratorFieldDelegate();

        if (obj instanceof Date) {
            format((Date)obj, sb, delegate);
        }
        else if (obj instanceof Number) {
            format(new Date(((Number)obj).longValue()), sb, delegate);
        }
        else if (obj == null) {
            throw new NullPointerException(
                   "formatToCharacterIterator must be passed non-null object");
        }
        else {
            throw new IllegalArgumentException(
                             "Cannot format given Object as a Date");
        }
        return delegate.getIterator(sb.toString());
    }

    // Map index into pattern character string to Calendar field number
    private static final int[] PATTERN_INDEX_TO_CALENDAR_FIELD = {
        Calendar.ERA,
        Calendar.YEAR,
        Calendar.MONTH,
        Calendar.DATE,
        Calendar.HOUR_OF_DAY,
        Calendar.HOUR_OF_DAY,
        Calendar.MINUTE,
        Calendar.SECOND,
        Calendar.MILLISECOND,
        Calendar.DAY_OF_WEEK,
        Calendar.DAY_OF_YEAR,
        Calendar.DAY_OF_WEEK_IN_MONTH,
        Calendar.WEEK_OF_YEAR,
        Calendar.WEEK_OF_MONTH,
        Calendar.AM_PM,
        Calendar.HOUR,
        Calendar.HOUR,
        Calendar.ZONE_OFFSET,
        Calendar.ZONE_OFFSET,
        CalendarBuilder.WEEK_YEAR,         // Pseudo Calendar field
        CalendarBuilder.ISO_DAY_OF_WEEK,   // Pseudo Calendar field
        Calendar.ZONE_OFFSET,
        Calendar.MONTH
    };

    // Map index into pattern character string to DateFormat field number
    private static final int[] PATTERN_INDEX_TO_DATE_FORMAT_FIELD = {
        DateFormat.ERA_FIELD,
        DateFormat.YEAR_FIELD,
        DateFormat.MONTH_FIELD,
        DateFormat.DATE_FIELD,
        DateFormat.HOUR_OF_DAY1_FIELD,
        DateFormat.HOUR_OF_DAY0_FIELD,
        DateFormat.MINUTE_FIELD,
        DateFormat.SECOND_FIELD,
        DateFormat.MILLISECOND_FIELD,
        DateFormat.DAY_OF_WEEK_FIELD,
        DateFormat.DAY_OF_YEAR_FIELD,
        DateFormat.DAY_OF_WEEK_IN_MONTH_FIELD,
        DateFormat.WEEK_OF_YEAR_FIELD,
        DateFormat.WEEK_OF_MONTH_FIELD,
        DateFormat.AM_PM_FIELD,
        DateFormat.HOUR1_FIELD,
        DateFormat.HOUR0_FIELD,
        DateFormat.TIMEZONE_FIELD,
        DateFormat.TIMEZONE_FIELD,
        DateFormat.YEAR_FIELD,
        DateFormat.DAY_OF_WEEK_FIELD,
        DateFormat.TIMEZONE_FIELD,
        DateFormat.MONTH_FIELD
    };

    // Maps from DecimalFormatSymbols index to Field constant
    private static final Field[] PATTERN_INDEX_TO_DATE_FORMAT_FIELD_ID = {
        Field.ERA,
        Field.YEAR,
        Field.MONTH,
        Field.DAY_OF_MONTH,
        Field.HOUR_OF_DAY1,
        Field.HOUR_OF_DAY0,
        Field.MINUTE,
        Field.SECOND,
        Field.MILLISECOND,
        Field.DAY_OF_WEEK,
        Field.DAY_OF_YEAR,
        Field.DAY_OF_WEEK_IN_MONTH,
        Field.WEEK_OF_YEAR,
        Field.WEEK_OF_MONTH,
        Field.AM_PM,
        Field.HOUR1,
        Field.HOUR0,
        Field.TIME_ZONE,
        Field.TIME_ZONE,
        Field.YEAR,
        Field.DAY_OF_WEEK,
        Field.TIME_ZONE,
        Field.MONTH
    };

    /**
     * Private member function that does the real date/time formatting.
     * <p>
     * 执行真正的日期/时间格式化的私人成员函数
     * 
     */
    private void subFormat(int patternCharIndex, int count,
                           FieldDelegate delegate, StringBuffer buffer,
                           boolean useDateFormatSymbols)
    {
        int     maxIntCount = Integer.MAX_VALUE;
        String  current = null;
        int     beginOffset = buffer.length();

        int field = PATTERN_INDEX_TO_CALENDAR_FIELD[patternCharIndex];
        int value;
        if (field == CalendarBuilder.WEEK_YEAR) {
            if (calendar.isWeekDateSupported()) {
                value = calendar.getWeekYear();
            } else {
                // use calendar year 'y' instead
                patternCharIndex = PATTERN_YEAR;
                field = PATTERN_INDEX_TO_CALENDAR_FIELD[patternCharIndex];
                value = calendar.get(field);
            }
        } else if (field == CalendarBuilder.ISO_DAY_OF_WEEK) {
            value = CalendarBuilder.toISODayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));
        } else {
            value = calendar.get(field);
        }

        int style = (count >= 4) ? Calendar.LONG : Calendar.SHORT;
        if (!useDateFormatSymbols && field < Calendar.ZONE_OFFSET
            && patternCharIndex != PATTERN_MONTH_STANDALONE) {
            current = calendar.getDisplayName(field, style, locale);
        }

        // Note: zeroPaddingNumber() assumes that maxDigits is either
        // 2 or maxIntCount. If we make any changes to this,
        // zeroPaddingNumber() must be fixed.

        switch (patternCharIndex) {
        case PATTERN_ERA: // 'G'
            if (useDateFormatSymbols) {
                String[] eras = formatData.getEras();
                if (value < eras.length) {
                    current = eras[value];
                }
            }
            if (current == null) {
                current = "";
            }
            break;

        case PATTERN_WEEK_YEAR: // 'Y'
        case PATTERN_YEAR:      // 'y'
            if (calendar instanceof GregorianCalendar) {
                if (count != 2) {
                    zeroPaddingNumber(value, count, maxIntCount, buffer);
                } else {
                    zeroPaddingNumber(value, 2, 2, buffer);
                } // clip 1996 to 96
            } else {
                if (current == null) {
                    zeroPaddingNumber(value, style == Calendar.LONG ? 1 : count,
                                      maxIntCount, buffer);
                }
            }
            break;

        case PATTERN_MONTH:            // 'M' (context seinsive)
            if (useDateFormatSymbols) {
                String[] months;
                if (count >= 4) {
                    months = formatData.getMonths();
                    current = months[value];
                } else if (count == 3) {
                    months = formatData.getShortMonths();
                    current = months[value];
                }
            } else {
                if (count < 3) {
                    current = null;
                } else if (forceStandaloneForm) {
                    current = calendar.getDisplayName(field, style | 0x8000, locale);
                    if (current == null) {
                        current = calendar.getDisplayName(field, style, locale);
                    }
                }
            }
            if (current == null) {
                zeroPaddingNumber(value+1, count, maxIntCount, buffer);
            }
            break;

        case PATTERN_MONTH_STANDALONE: // 'L'
            assert current == null;
            if (locale == null) {
                String[] months;
                if (count >= 4) {
                    months = formatData.getMonths();
                    current = months[value];
                } else if (count == 3) {
                    months = formatData.getShortMonths();
                    current = months[value];
                }
            } else {
                if (count >= 3) {
                    current = calendar.getDisplayName(field, style | 0x8000, locale);
                }
            }
            if (current == null) {
                zeroPaddingNumber(value+1, count, maxIntCount, buffer);
            }
            break;

        case PATTERN_HOUR_OF_DAY1: // 'k' 1-based.  eg, 23:59 + 1 hour =>> 24:59
            if (current == null) {
                if (value == 0) {
                    zeroPaddingNumber(calendar.getMaximum(Calendar.HOUR_OF_DAY) + 1,
                                      count, maxIntCount, buffer);
                } else {
                    zeroPaddingNumber(value, count, maxIntCount, buffer);
                }
            }
            break;

        case PATTERN_DAY_OF_WEEK: // 'E'
            if (useDateFormatSymbols) {
                String[] weekdays;
                if (count >= 4) {
                    weekdays = formatData.getWeekdays();
                    current = weekdays[value];
                } else { // count < 4, use abbreviated form if exists
                    weekdays = formatData.getShortWeekdays();
                    current = weekdays[value];
                }
            }
            break;

        case PATTERN_AM_PM:    // 'a'
            if (useDateFormatSymbols) {
                String[] ampm = formatData.getAmPmStrings();
                current = ampm[value];
            }
            break;

        case PATTERN_HOUR1:    // 'h' 1-based.  eg, 11PM + 1 hour =>> 12 AM
            if (current == null) {
                if (value == 0) {
                    zeroPaddingNumber(calendar.getLeastMaximum(Calendar.HOUR) + 1,
                                      count, maxIntCount, buffer);
                } else {
                    zeroPaddingNumber(value, count, maxIntCount, buffer);
                }
            }
            break;

        case PATTERN_ZONE_NAME: // 'z'
            if (current == null) {
                if (formatData.locale == null || formatData.isZoneStringsSet) {
                    int zoneIndex =
                        formatData.getZoneIndex(calendar.getTimeZone().getID());
                    if (zoneIndex == -1) {
                        value = calendar.get(Calendar.ZONE_OFFSET) +
                            calendar.get(Calendar.DST_OFFSET);
                        buffer.append(ZoneInfoFile.toCustomID(value));
                    } else {
                        int index = (calendar.get(Calendar.DST_OFFSET) == 0) ? 1: 3;
                        if (count < 4) {
                            // Use the short name
                            index++;
                        }
                        String[][] zoneStrings = formatData.getZoneStringsWrapper();
                        buffer.append(zoneStrings[zoneIndex][index]);
                    }
                } else {
                    TimeZone tz = calendar.getTimeZone();
                    boolean daylight = (calendar.get(Calendar.DST_OFFSET) != 0);
                    int tzstyle = (count < 4 ? TimeZone.SHORT : TimeZone.LONG);
                    buffer.append(tz.getDisplayName(daylight, tzstyle, formatData.locale));
                }
            }
            break;

        case PATTERN_ZONE_VALUE: // 'Z' ("-/+hhmm" form)
            value = (calendar.get(Calendar.ZONE_OFFSET) +
                     calendar.get(Calendar.DST_OFFSET)) / 60000;

            int width = 4;
            if (value >= 0) {
                buffer.append('+');
            } else {
                width++;
            }

            int num = (value / 60) * 100 + (value % 60);
            CalendarUtils.sprintf0d(buffer, num, width);
            break;

        case PATTERN_ISO_ZONE:   // 'X'
            value = calendar.get(Calendar.ZONE_OFFSET)
                    + calendar.get(Calendar.DST_OFFSET);

            if (value == 0) {
                buffer.append('Z');
                break;
            }

            value /=  60000;
            if (value >= 0) {
                buffer.append('+');
            } else {
                buffer.append('-');
                value = -value;
            }

            CalendarUtils.sprintf0d(buffer, value / 60, 2);
            if (count == 1) {
                break;
            }

            if (count == 3) {
                buffer.append(':');
            }
            CalendarUtils.sprintf0d(buffer, value % 60, 2);
            break;

        default:
     // case PATTERN_DAY_OF_MONTH:         // 'd'
     // case PATTERN_HOUR_OF_DAY0:         // 'H' 0-based.  eg, 23:59 + 1 hour =>> 00:59
     // case PATTERN_MINUTE:               // 'm'
     // case PATTERN_SECOND:               // 's'
     // case PATTERN_MILLISECOND:          // 'S'
     // case PATTERN_DAY_OF_YEAR:          // 'D'
     // case PATTERN_DAY_OF_WEEK_IN_MONTH: // 'F'
     // case PATTERN_WEEK_OF_YEAR:         // 'w'
     // case PATTERN_WEEK_OF_MONTH:        // 'W'
     // case PATTERN_HOUR0:                // 'K' eg, 11PM + 1 hour =>> 0 AM
     // case PATTERN_ISO_DAY_OF_WEEK:      // 'u' pseudo field, Monday = 1, ..., Sunday = 7
            if (current == null) {
                zeroPaddingNumber(value, count, maxIntCount, buffer);
            }
            break;
        } // switch (patternCharIndex)

        if (current != null) {
            buffer.append(current);
        }

        int fieldID = PATTERN_INDEX_TO_DATE_FORMAT_FIELD[patternCharIndex];
        Field f = PATTERN_INDEX_TO_DATE_FORMAT_FIELD_ID[patternCharIndex];

        delegate.formatted(fieldID, f, f, beginOffset, buffer.length(), buffer);
    }

    /**
     * Formats a number with the specified minimum and maximum number of digits.
     * <p>
     *  格式化具有指定的最小和最大位数的数字
     * 
     */
    private void zeroPaddingNumber(int value, int minDigits, int maxDigits, StringBuffer buffer)
    {
        // Optimization for 1, 2 and 4 digit numbers. This should
        // cover most cases of formatting date/time related items.
        // Note: This optimization code assumes that maxDigits is
        // either 2 or Integer.MAX_VALUE (maxIntCount in format()).
        try {
            if (zeroDigit == 0) {
                zeroDigit = ((DecimalFormat)numberFormat).getDecimalFormatSymbols().getZeroDigit();
            }
            if (value >= 0) {
                if (value < 100 && minDigits >= 1 && minDigits <= 2) {
                    if (value < 10) {
                        if (minDigits == 2) {
                            buffer.append(zeroDigit);
                        }
                        buffer.append((char)(zeroDigit + value));
                    } else {
                        buffer.append((char)(zeroDigit + value / 10));
                        buffer.append((char)(zeroDigit + value % 10));
                    }
                    return;
                } else if (value >= 1000 && value < 10000) {
                    if (minDigits == 4) {
                        buffer.append((char)(zeroDigit + value / 1000));
                        value %= 1000;
                        buffer.append((char)(zeroDigit + value / 100));
                        value %= 100;
                        buffer.append((char)(zeroDigit + value / 10));
                        buffer.append((char)(zeroDigit + value % 10));
                        return;
                    }
                    if (minDigits == 2 && maxDigits == 2) {
                        zeroPaddingNumber(value % 100, 2, 2, buffer);
                        return;
                    }
                }
            }
        } catch (Exception e) {
        }

        numberFormat.setMinimumIntegerDigits(minDigits);
        numberFormat.setMaximumIntegerDigits(maxDigits);
        numberFormat.format((long)value, buffer, DontCareFieldPosition.INSTANCE);
    }


    /**
     * Parses text from a string to produce a <code>Date</code>.
     * <p>
     * The method attempts to parse text starting at the index given by
     * <code>pos</code>.
     * If parsing succeeds, then the index of <code>pos</code> is updated
     * to the index after the last character used (parsing does not necessarily
     * use all characters up to the end of the string), and the parsed
     * date is returned. The updated <code>pos</code> can be used to
     * indicate the starting point for the next call to this method.
     * If an error occurs, then the index of <code>pos</code> is not
     * changed, the error index of <code>pos</code> is set to the index of
     * the character where the error occurred, and null is returned.
     *
     * <p>This parsing operation uses the {@link DateFormat#calendar
     * calendar} to produce a {@code Date}. All of the {@code
     * calendar}'s date-time fields are {@linkplain Calendar#clear()
     * cleared} before parsing, and the {@code calendar}'s default
     * values of the date-time fields are used for any missing
     * date-time information. For example, the year value of the
     * parsed {@code Date} is 1970 with {@link GregorianCalendar} if
     * no year value is given from the parsing operation.  The {@code
     * TimeZone} value may be overwritten, depending on the given
     * pattern and the time zone value in {@code text}. Any {@code
     * TimeZone} value that has previously been set by a call to
     * {@link #setTimeZone(java.util.TimeZone) setTimeZone} may need
     * to be restored for further operations.
     *
     * <p>
     *  从字符串解析文本以生成<code> Date </code>
     * <p>
     * 该方法尝试解析从<code> pos </code>给出的索引开始的文本如果解析成功,则将<code> pos </code>的索引更新为使用最后一个字符后的索引(解析不会必须使用所有字符直到字符串的结
     * 尾),并返回分析的日期更新的<code> pos </code>可用于指示下一次调用此方法的起点如果发生错误,不改变<code> pos </code>的索引,将<code> pos </code>的错
     * 误索引设置为发生错误的字符的索引,并返回null。
     * 
     * <p>此解析操作使用{@link DateFormat#calendar calendar}生成{@code Date}所有{@code calendar}的日期时间字段均为{@linkplain Calendar#clear()cleared}
     * 解析,并且日期时间字段的{@code calendar}的默认值用于任何缺少的日期时间信息例如,解析的{@code Date}的年份值是1970年{@link GregorianCalendar}如果解
     * 析操作没有给出年份值{@code TimeZone}值可能会被覆盖,具体取决于给定的模式和{@code text}中的时区值任何{@code TimeZone}值以前设置的可能需要恢复对{@link #setTimeZone(javautilTimeZone)setTimeZone}
     * 的调用以进行进一步操作。
     * 
     * 
     * @param text  A <code>String</code>, part of which should be parsed.
     * @param pos   A <code>ParsePosition</code> object with index and error
     *              index information as described above.
     * @return A <code>Date</code> parsed from the string. In case of
     *         error, returns null.
     * @exception NullPointerException if <code>text</code> or <code>pos</code> is null.
     */
    @Override
    public Date parse(String text, ParsePosition pos)
    {
        checkNegativeNumberExpression();

        int start = pos.index;
        int oldStart = start;
        int textLength = text.length();

        boolean[] ambiguousYear = {false};

        CalendarBuilder calb = new CalendarBuilder();

        for (int i = 0; i < compiledPattern.length; ) {
            int tag = compiledPattern[i] >>> 8;
            int count = compiledPattern[i++] & 0xff;
            if (count == 255) {
                count = compiledPattern[i++] << 16;
                count |= compiledPattern[i++];
            }

            switch (tag) {
            case TAG_QUOTE_ASCII_CHAR:
                if (start >= textLength || text.charAt(start) != (char)count) {
                    pos.index = oldStart;
                    pos.errorIndex = start;
                    return null;
                }
                start++;
                break;

            case TAG_QUOTE_CHARS:
                while (count-- > 0) {
                    if (start >= textLength || text.charAt(start) != compiledPattern[i++]) {
                        pos.index = oldStart;
                        pos.errorIndex = start;
                        return null;
                    }
                    start++;
                }
                break;

            default:
                // Peek the next pattern to determine if we need to
                // obey the number of pattern letters for
                // parsing. It's required when parsing contiguous
                // digit text (e.g., "20010704") with a pattern which
                // has no delimiters between fields, like "yyyyMMdd".
                boolean obeyCount = false;

                // In Arabic, a minus sign for a negative number is put after
                // the number. Even in another locale, a minus sign can be
                // put after a number using DateFormat.setNumberFormat().
                // If both the minus sign and the field-delimiter are '-',
                // subParse() needs to determine whether a '-' after a number
                // in the given text is a delimiter or is a minus sign for the
                // preceding number. We give subParse() a clue based on the
                // information in compiledPattern.
                boolean useFollowingMinusSignAsDelimiter = false;

                if (i < compiledPattern.length) {
                    int nextTag = compiledPattern[i] >>> 8;
                    if (!(nextTag == TAG_QUOTE_ASCII_CHAR ||
                          nextTag == TAG_QUOTE_CHARS)) {
                        obeyCount = true;
                    }

                    if (hasFollowingMinusSign &&
                        (nextTag == TAG_QUOTE_ASCII_CHAR ||
                         nextTag == TAG_QUOTE_CHARS)) {
                        int c;
                        if (nextTag == TAG_QUOTE_ASCII_CHAR) {
                            c = compiledPattern[i] & 0xff;
                        } else {
                            c = compiledPattern[i+1];
                        }

                        if (c == minusSign) {
                            useFollowingMinusSignAsDelimiter = true;
                        }
                    }
                }
                start = subParse(text, start, tag, count, obeyCount,
                                 ambiguousYear, pos,
                                 useFollowingMinusSignAsDelimiter, calb);
                if (start < 0) {
                    pos.index = oldStart;
                    return null;
                }
            }
        }

        // At this point the fields of Calendar have been set.  Calendar
        // will fill in default values for missing fields when the time
        // is computed.

        pos.index = start;

        Date parsedDate;
        try {
            parsedDate = calb.establish(calendar).getTime();
            // If the year value is ambiguous,
            // then the two-digit year == the default start year
            if (ambiguousYear[0]) {
                if (parsedDate.before(defaultCenturyStart)) {
                    parsedDate = calb.addYear(100).establish(calendar).getTime();
                }
            }
        }
        // An IllegalArgumentException will be thrown by Calendar.getTime()
        // if any fields are out of range, e.g., MONTH == 17.
        catch (IllegalArgumentException e) {
            pos.errorIndex = start;
            pos.index = oldStart;
            return null;
        }

        return parsedDate;
    }

    /**
     * Private code-size reduction function used by subParse.
     * <p>
     * subParse使用的私有代码大小缩减函数
     * 
     * 
     * @param text the time text being parsed.
     * @param start where to start parsing.
     * @param field the date field being parsed.
     * @param data the string array to parsed.
     * @return the new start position if matching succeeded; a negative number
     * indicating matching failure, otherwise.
     */
    private int matchString(String text, int start, int field, String[] data, CalendarBuilder calb)
    {
        int i = 0;
        int count = data.length;

        if (field == Calendar.DAY_OF_WEEK) {
            i = 1;
        }

        // There may be multiple strings in the data[] array which begin with
        // the same prefix (e.g., Cerven and Cervenec (June and July) in Czech).
        // We keep track of the longest match, and return that.  Note that this
        // unfortunately requires us to test all array elements.
        int bestMatchLength = 0, bestMatch = -1;
        for (; i<count; ++i)
        {
            int length = data[i].length();
            // Always compare if we have no match yet; otherwise only compare
            // against potentially better matches (longer strings).
            if (length > bestMatchLength &&
                text.regionMatches(true, start, data[i], 0, length))
            {
                bestMatch = i;
                bestMatchLength = length;
            }
        }
        if (bestMatch >= 0)
        {
            calb.set(field, bestMatch);
            return start + bestMatchLength;
        }
        return -start;
    }

    /**
     * Performs the same thing as matchString(String, int, int,
     * String[]). This method takes a Map<String, Integer> instead of
     * String[].
     * <p>
     *  执行与matchString相同的事情(String,int,int,String [])此方法使用Map <String,Integer>而不是String []
     * 
     */
    private int matchString(String text, int start, int field,
                            Map<String,Integer> data, CalendarBuilder calb) {
        if (data != null) {
            // TODO: make this default when it's in the spec.
            if (data instanceof SortedMap) {
                for (String name : data.keySet()) {
                    if (text.regionMatches(true, start, name, 0, name.length())) {
                        calb.set(field, data.get(name));
                        return start + name.length();
                    }
                }
                return -start;
            }

            String bestMatch = null;

            for (String name : data.keySet()) {
                int length = name.length();
                if (bestMatch == null || length > bestMatch.length()) {
                    if (text.regionMatches(true, start, name, 0, length)) {
                        bestMatch = name;
                    }
                }
            }

            if (bestMatch != null) {
                calb.set(field, data.get(bestMatch));
                return start + bestMatch.length();
            }
        }
        return -start;
    }

    private int matchZoneString(String text, int start, String[] zoneNames) {
        for (int i = 1; i <= 4; ++i) {
            // Checking long and short zones [1 & 2],
            // and long and short daylight [3 & 4].
            String zoneName = zoneNames[i];
            if (text.regionMatches(true, start,
                                   zoneName, 0, zoneName.length())) {
                return i;
            }
        }
        return -1;
    }

    private boolean matchDSTString(String text, int start, int zoneIndex, int standardIndex,
                                   String[][] zoneStrings) {
        int index = standardIndex + 2;
        String zoneName  = zoneStrings[zoneIndex][index];
        if (text.regionMatches(true, start,
                               zoneName, 0, zoneName.length())) {
            return true;
        }
        return false;
    }

    /**
     * find time zone 'text' matched zoneStrings and set to internal
     * calendar.
     * <p>
     *  查找时区"文本"匹配的zoneStrings并设置为内部日历
     * 
     */
    private int subParseZoneString(String text, int start, CalendarBuilder calb) {
        boolean useSameName = false; // true if standard and daylight time use the same abbreviation.
        TimeZone currentTimeZone = getTimeZone();

        // At this point, check for named time zones by looking through
        // the locale data from the TimeZoneNames strings.
        // Want to be able to parse both short and long forms.
        int zoneIndex = formatData.getZoneIndex(currentTimeZone.getID());
        TimeZone tz = null;
        String[][] zoneStrings = formatData.getZoneStringsWrapper();
        String[] zoneNames = null;
        int nameIndex = 0;
        if (zoneIndex != -1) {
            zoneNames = zoneStrings[zoneIndex];
            if ((nameIndex = matchZoneString(text, start, zoneNames)) > 0) {
                if (nameIndex <= 2) {
                    // Check if the standard name (abbr) and the daylight name are the same.
                    useSameName = zoneNames[nameIndex].equalsIgnoreCase(zoneNames[nameIndex + 2]);
                }
                tz = TimeZone.getTimeZone(zoneNames[0]);
            }
        }
        if (tz == null) {
            zoneIndex = formatData.getZoneIndex(TimeZone.getDefault().getID());
            if (zoneIndex != -1) {
                zoneNames = zoneStrings[zoneIndex];
                if ((nameIndex = matchZoneString(text, start, zoneNames)) > 0) {
                    if (nameIndex <= 2) {
                        useSameName = zoneNames[nameIndex].equalsIgnoreCase(zoneNames[nameIndex + 2]);
                    }
                    tz = TimeZone.getTimeZone(zoneNames[0]);
                }
            }
        }

        if (tz == null) {
            int len = zoneStrings.length;
            for (int i = 0; i < len; i++) {
                zoneNames = zoneStrings[i];
                if ((nameIndex = matchZoneString(text, start, zoneNames)) > 0) {
                    if (nameIndex <= 2) {
                        useSameName = zoneNames[nameIndex].equalsIgnoreCase(zoneNames[nameIndex + 2]);
                    }
                    tz = TimeZone.getTimeZone(zoneNames[0]);
                    break;
                }
            }
        }
        if (tz != null) { // Matched any ?
            if (!tz.equals(currentTimeZone)) {
                setTimeZone(tz);
            }
            // If the time zone matched uses the same name
            // (abbreviation) for both standard and daylight time,
            // let the time zone in the Calendar decide which one.
            //
            // Also if tz.getDSTSaving() returns 0 for DST, use tz to
            // determine the local time. (6645292)
            int dstAmount = (nameIndex >= 3) ? tz.getDSTSavings() : 0;
            if (!(useSameName || (nameIndex >= 3 && dstAmount == 0))) {
                calb.clear(Calendar.ZONE_OFFSET).set(Calendar.DST_OFFSET, dstAmount);
            }
            return (start + zoneNames[nameIndex].length());
        }
        return 0;
    }

    /**
     * Parses numeric forms of time zone offset, such as "hh:mm", and
     * sets calb to the parsed value.
     *
     * <p>
     *  解析时区偏移的数值形式,例如"hh：mm",并将calb设置为解析的值
     * 
     * 
     * @param text  the text to be parsed
     * @param start the character position to start parsing
     * @param sign  1: positive; -1: negative
     * @param count 0: 'Z' or "GMT+hh:mm" parsing; 1 - 3: the number of 'X's
     * @param colon true - colon required between hh and mm; false - no colon required
     * @param calb  a CalendarBuilder in which the parsed value is stored
     * @return updated parsed position, or its negative value to indicate a parsing error
     */
    private int subParseNumericZone(String text, int start, int sign, int count,
                                    boolean colon, CalendarBuilder calb) {
        int index = start;

      parse:
        try {
            char c = text.charAt(index++);
            // Parse hh
            int hours;
            if (!isDigit(c)) {
                break parse;
            }
            hours = c - '0';
            c = text.charAt(index++);
            if (isDigit(c)) {
                hours = hours * 10 + (c - '0');
            } else {
                // If no colon in RFC 822 or 'X' (ISO), two digits are
                // required.
                if (count > 0 || !colon) {
                    break parse;
                }
                --index;
            }
            if (hours > 23) {
                break parse;
            }
            int minutes = 0;
            if (count != 1) {
                // Proceed with parsing mm
                c = text.charAt(index++);
                if (colon) {
                    if (c != ':') {
                        break parse;
                    }
                    c = text.charAt(index++);
                }
                if (!isDigit(c)) {
                    break parse;
                }
                minutes = c - '0';
                c = text.charAt(index++);
                if (!isDigit(c)) {
                    break parse;
                }
                minutes = minutes * 10 + (c - '0');
                if (minutes > 59) {
                    break parse;
                }
            }
            minutes += hours * 60;
            calb.set(Calendar.ZONE_OFFSET, minutes * MILLIS_PER_MINUTE * sign)
                .set(Calendar.DST_OFFSET, 0);
            return index;
        } catch (IndexOutOfBoundsException e) {
        }
        return  1 - index; // -(index - 1)
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    /**
     * Private member function that converts the parsed date strings into
     * timeFields. Returns -start (for ParsePosition) if failed.
     * <p>
     *  将解析的日期字符串转换为timeFields的私有成员函数如果失败,返回-start(对于ParsePosition)
     * 
     * 
     * @param text the time text to be parsed.
     * @param start where to start parsing.
     * @param patternCharIndex the index of the pattern character.
     * @param count the count of a pattern character.
     * @param obeyCount if true, then the next field directly abuts this one,
     * and we should use the count to know when to stop parsing.
     * @param ambiguousYear return parameter; upon return, if ambiguousYear[0]
     * is true, then a two-digit year was parsed and may need to be readjusted.
     * @param origPos origPos.errorIndex is used to return an error index
     * at which a parse error occurred, if matching failure occurs.
     * @return the new start position if matching succeeded; -1 indicating
     * matching failure, otherwise. In case matching failure occurred,
     * an error index is set to origPos.errorIndex.
     */
    private int subParse(String text, int start, int patternCharIndex, int count,
                         boolean obeyCount, boolean[] ambiguousYear,
                         ParsePosition origPos,
                         boolean useFollowingMinusSignAsDelimiter, CalendarBuilder calb) {
        Number number;
        int value = 0;
        ParsePosition pos = new ParsePosition(0);
        pos.index = start;
        if (patternCharIndex == PATTERN_WEEK_YEAR && !calendar.isWeekDateSupported()) {
            // use calendar year 'y' instead
            patternCharIndex = PATTERN_YEAR;
        }
        int field = PATTERN_INDEX_TO_CALENDAR_FIELD[patternCharIndex];

        // If there are any spaces here, skip over them.  If we hit the end
        // of the string, then fail.
        for (;;) {
            if (pos.index >= text.length()) {
                origPos.errorIndex = start;
                return -1;
            }
            char c = text.charAt(pos.index);
            if (c != ' ' && c != '\t') {
                break;
            }
            ++pos.index;
        }
        // Remember the actual start index
        int actualStart = pos.index;

      parsing:
        {
            // We handle a few special cases here where we need to parse
            // a number value.  We handle further, more generic cases below.  We need
            // to handle some of them here because some fields require extra processing on
            // the parsed value.
            if (patternCharIndex == PATTERN_HOUR_OF_DAY1 ||
                patternCharIndex == PATTERN_HOUR1 ||
                (patternCharIndex == PATTERN_MONTH && count <= 2) ||
                patternCharIndex == PATTERN_YEAR ||
                patternCharIndex == PATTERN_WEEK_YEAR) {
                // It would be good to unify this with the obeyCount logic below,
                // but that's going to be difficult.
                if (obeyCount) {
                    if ((start+count) > text.length()) {
                        break parsing;
                    }
                    number = numberFormat.parse(text.substring(0, start+count), pos);
                } else {
                    number = numberFormat.parse(text, pos);
                }
                if (number == null) {
                    if (patternCharIndex != PATTERN_YEAR || calendar instanceof GregorianCalendar) {
                        break parsing;
                    }
                } else {
                    value = number.intValue();

                    if (useFollowingMinusSignAsDelimiter && (value < 0) &&
                        (((pos.index < text.length()) &&
                         (text.charAt(pos.index) != minusSign)) ||
                         ((pos.index == text.length()) &&
                          (text.charAt(pos.index-1) == minusSign)))) {
                        value = -value;
                        pos.index--;
                    }
                }
            }

            boolean useDateFormatSymbols = useDateFormatSymbols();

            int index;
            switch (patternCharIndex) {
            case PATTERN_ERA: // 'G'
                if (useDateFormatSymbols) {
                    if ((index = matchString(text, start, Calendar.ERA, formatData.getEras(), calb)) > 0) {
                        return index;
                    }
                } else {
                    Map<String, Integer> map = getDisplayNamesMap(field, locale);
                    if ((index = matchString(text, start, field, map, calb)) > 0) {
                        return index;
                    }
                }
                break parsing;

            case PATTERN_WEEK_YEAR: // 'Y'
            case PATTERN_YEAR:      // 'y'
                if (!(calendar instanceof GregorianCalendar)) {
                    // calendar might have text representations for year values,
                    // such as "\u5143" in JapaneseImperialCalendar.
                    int style = (count >= 4) ? Calendar.LONG : Calendar.SHORT;
                    Map<String, Integer> map = calendar.getDisplayNames(field, style, locale);
                    if (map != null) {
                        if ((index = matchString(text, start, field, map, calb)) > 0) {
                            return index;
                        }
                    }
                    calb.set(field, value);
                    return pos.index;
                }

                // If there are 3 or more YEAR pattern characters, this indicates
                // that the year value is to be treated literally, without any
                // two-digit year adjustments (e.g., from "01" to 2001).  Otherwise
                // we made adjustments to place the 2-digit year in the proper
                // century, for parsed strings from "00" to "99".  Any other string
                // is treated literally:  "2250", "-1", "1", "002".
                if (count <= 2 && (pos.index - actualStart) == 2
                    && Character.isDigit(text.charAt(actualStart))
                    && Character.isDigit(text.charAt(actualStart + 1))) {
                    // Assume for example that the defaultCenturyStart is 6/18/1903.
                    // This means that two-digit years will be forced into the range
                    // 6/18/1903 to 6/17/2003.  As a result, years 00, 01, and 02
                    // correspond to 2000, 2001, and 2002.  Years 04, 05, etc. correspond
                    // to 1904, 1905, etc.  If the year is 03, then it is 2003 if the
                    // other fields specify a date before 6/18, or 1903 if they specify a
                    // date afterwards.  As a result, 03 is an ambiguous year.  All other
                    // two-digit years are unambiguous.
                    int ambiguousTwoDigitYear = defaultCenturyStartYear % 100;
                    ambiguousYear[0] = value == ambiguousTwoDigitYear;
                    value += (defaultCenturyStartYear/100)*100 +
                        (value < ambiguousTwoDigitYear ? 100 : 0);
                }
                calb.set(field, value);
                return pos.index;

            case PATTERN_MONTH: // 'M'
                if (count <= 2) // i.e., M or MM.
                {
                    // Don't want to parse the month if it is a string
                    // while pattern uses numeric style: M or MM.
                    // [We computed 'value' above.]
                    calb.set(Calendar.MONTH, value - 1);
                    return pos.index;
                }

                if (useDateFormatSymbols) {
                    // count >= 3 // i.e., MMM or MMMM
                    // Want to be able to parse both short and long forms.
                    // Try count == 4 first:
                    int newStart;
                    if ((newStart = matchString(text, start, Calendar.MONTH,
                                                formatData.getMonths(), calb)) > 0) {
                        return newStart;
                    }
                    // count == 4 failed, now try count == 3
                    if ((index = matchString(text, start, Calendar.MONTH,
                                             formatData.getShortMonths(), calb)) > 0) {
                        return index;
                    }
                } else {
                    Map<String, Integer> map = getDisplayNamesMap(field, locale);
                    if ((index = matchString(text, start, field, map, calb)) > 0) {
                        return index;
                    }
                }
                break parsing;

            case PATTERN_HOUR_OF_DAY1: // 'k' 1-based.  eg, 23:59 + 1 hour =>> 24:59
                if (!isLenient()) {
                    // Validate the hour value in non-lenient
                    if (value < 1 || value > 24) {
                        break parsing;
                    }
                }
                // [We computed 'value' above.]
                if (value == calendar.getMaximum(Calendar.HOUR_OF_DAY) + 1) {
                    value = 0;
                }
                calb.set(Calendar.HOUR_OF_DAY, value);
                return pos.index;

            case PATTERN_DAY_OF_WEEK:  // 'E'
                {
                    if (useDateFormatSymbols) {
                        // Want to be able to parse both short and long forms.
                        // Try count == 4 (DDDD) first:
                        int newStart;
                        if ((newStart=matchString(text, start, Calendar.DAY_OF_WEEK,
                                                  formatData.getWeekdays(), calb)) > 0) {
                            return newStart;
                        }
                        // DDDD failed, now try DDD
                        if ((index = matchString(text, start, Calendar.DAY_OF_WEEK,
                                                 formatData.getShortWeekdays(), calb)) > 0) {
                            return index;
                        }
                    } else {
                        int[] styles = { Calendar.LONG, Calendar.SHORT };
                        for (int style : styles) {
                            Map<String,Integer> map = calendar.getDisplayNames(field, style, locale);
                            if ((index = matchString(text, start, field, map, calb)) > 0) {
                                return index;
                            }
                        }
                    }
                }
                break parsing;

            case PATTERN_AM_PM:    // 'a'
                if (useDateFormatSymbols) {
                    if ((index = matchString(text, start, Calendar.AM_PM,
                                             formatData.getAmPmStrings(), calb)) > 0) {
                        return index;
                    }
                } else {
                    Map<String,Integer> map = getDisplayNamesMap(field, locale);
                    if ((index = matchString(text, start, field, map, calb)) > 0) {
                        return index;
                    }
                }
                break parsing;

            case PATTERN_HOUR1: // 'h' 1-based.  eg, 11PM + 1 hour =>> 12 AM
                if (!isLenient()) {
                    // Validate the hour value in non-lenient
                    if (value < 1 || value > 12) {
                        break parsing;
                    }
                }
                // [We computed 'value' above.]
                if (value == calendar.getLeastMaximum(Calendar.HOUR) + 1) {
                    value = 0;
                }
                calb.set(Calendar.HOUR, value);
                return pos.index;

            case PATTERN_ZONE_NAME:  // 'z'
            case PATTERN_ZONE_VALUE: // 'Z'
                {
                    int sign = 0;
                    try {
                        char c = text.charAt(pos.index);
                        if (c == '+') {
                            sign = 1;
                        } else if (c == '-') {
                            sign = -1;
                        }
                        if (sign == 0) {
                            // Try parsing a custom time zone "GMT+hh:mm" or "GMT".
                            if ((c == 'G' || c == 'g')
                                && (text.length() - start) >= GMT.length()
                                && text.regionMatches(true, start, GMT, 0, GMT.length())) {
                                pos.index = start + GMT.length();

                                if ((text.length() - pos.index) > 0) {
                                    c = text.charAt(pos.index);
                                    if (c == '+') {
                                        sign = 1;
                                    } else if (c == '-') {
                                        sign = -1;
                                    }
                                }

                                if (sign == 0) {    /* "GMT" without offset */
                                    calb.set(Calendar.ZONE_OFFSET, 0)
                                        .set(Calendar.DST_OFFSET, 0);
                                    return pos.index;
                                }

                                // Parse the rest as "hh:mm"
                                int i = subParseNumericZone(text, ++pos.index,
                                                            sign, 0, true, calb);
                                if (i > 0) {
                                    return i;
                                }
                                pos.index = -i;
                            } else {
                                // Try parsing the text as a time zone
                                // name or abbreviation.
                                int i = subParseZoneString(text, pos.index, calb);
                                if (i > 0) {
                                    return i;
                                }
                                pos.index = -i;
                            }
                        } else {
                            // Parse the rest as "hhmm" (RFC 822)
                            int i = subParseNumericZone(text, ++pos.index,
                                                        sign, 0, false, calb);
                            if (i > 0) {
                                return i;
                            }
                            pos.index = -i;
                        }
                    } catch (IndexOutOfBoundsException e) {
                    }
                }
                break parsing;

            case PATTERN_ISO_ZONE:   // 'X'
                {
                    if ((text.length() - pos.index) <= 0) {
                        break parsing;
                    }

                    int sign;
                    char c = text.charAt(pos.index);
                    if (c == 'Z') {
                        calb.set(Calendar.ZONE_OFFSET, 0).set(Calendar.DST_OFFSET, 0);
                        return ++pos.index;
                    }

                    // parse text as "+/-hh[[:]mm]" based on count
                    if (c == '+') {
                        sign = 1;
                    } else if (c == '-') {
                        sign = -1;
                    } else {
                        ++pos.index;
                        break parsing;
                    }
                    int i = subParseNumericZone(text, ++pos.index, sign, count,
                                                count == 3, calb);
                    if (i > 0) {
                        return i;
                    }
                    pos.index = -i;
                }
                break parsing;

            default:
         // case PATTERN_DAY_OF_MONTH:         // 'd'
         // case PATTERN_HOUR_OF_DAY0:         // 'H' 0-based.  eg, 23:59 + 1 hour =>> 00:59
         // case PATTERN_MINUTE:               // 'm'
         // case PATTERN_SECOND:               // 's'
         // case PATTERN_MILLISECOND:          // 'S'
         // case PATTERN_DAY_OF_YEAR:          // 'D'
         // case PATTERN_DAY_OF_WEEK_IN_MONTH: // 'F'
         // case PATTERN_WEEK_OF_YEAR:         // 'w'
         // case PATTERN_WEEK_OF_MONTH:        // 'W'
         // case PATTERN_HOUR0:                // 'K' 0-based.  eg, 11PM + 1 hour =>> 0 AM
         // case PATTERN_ISO_DAY_OF_WEEK:      // 'u' (pseudo field);

                // Handle "generic" fields
                if (obeyCount) {
                    if ((start+count) > text.length()) {
                        break parsing;
                    }
                    number = numberFormat.parse(text.substring(0, start+count), pos);
                } else {
                    number = numberFormat.parse(text, pos);
                }
                if (number != null) {
                    value = number.intValue();

                    if (useFollowingMinusSignAsDelimiter && (value < 0) &&
                        (((pos.index < text.length()) &&
                         (text.charAt(pos.index) != minusSign)) ||
                         ((pos.index == text.length()) &&
                          (text.charAt(pos.index-1) == minusSign)))) {
                        value = -value;
                        pos.index--;
                    }

                    calb.set(field, value);
                    return pos.index;
                }
                break parsing;
            }
        }

        // Parsing failed.
        origPos.errorIndex = pos.index;
        return -1;
    }

    /**
     * Returns true if the DateFormatSymbols has been set explicitly or locale
     * is null.
     * <p>
     *  如果DateFormatSymbols已显式设置或locale为null,则返回true
     * 
     */
    private boolean useDateFormatSymbols() {
        return useDateFormatSymbols || locale == null;
    }

    /**
     * Translates a pattern, mapping each character in the from string to the
     * corresponding character in the to string.
     *
     * <p>
     *  翻译模式,将from字符串中的每个字符映射到to字符串中的相应字符
     * 
     * 
     * @exception IllegalArgumentException if the given pattern is invalid
     */
    private String translatePattern(String pattern, String from, String to) {
        StringBuilder result = new StringBuilder();
        boolean inQuote = false;
        for (int i = 0; i < pattern.length(); ++i) {
            char c = pattern.charAt(i);
            if (inQuote) {
                if (c == '\'') {
                    inQuote = false;
                }
            }
            else {
                if (c == '\'') {
                    inQuote = true;
                } else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                    int ci = from.indexOf(c);
                    if (ci >= 0) {
                        // patternChars is longer than localPatternChars due
                        // to serialization compatibility. The pattern letters
                        // unsupported by localPatternChars pass through.
                        if (ci < to.length()) {
                            c = to.charAt(ci);
                        }
                    } else {
                        throw new IllegalArgumentException("Illegal pattern " +
                                                           " character '" +
                                                           c + "'");
                    }
                }
            }
            result.append(c);
        }
        if (inQuote) {
            throw new IllegalArgumentException("Unfinished quote in pattern");
        }
        return result.toString();
    }

    /**
     * Returns a pattern string describing this date format.
     *
     * <p>
     *  返回描述此日期格式的模式字符串
     * 
     * 
     * @return a pattern string describing this date format.
     */
    public String toPattern() {
        return pattern;
    }

    /**
     * Returns a localized pattern string describing this date format.
     *
     * <p>
     *  返回描述此日期格式的本地化模式字符串
     * 
     * 
     * @return a localized pattern string describing this date format.
     */
    public String toLocalizedPattern() {
        return translatePattern(pattern,
                                DateFormatSymbols.patternChars,
                                formatData.getLocalPatternChars());
    }

    /**
     * Applies the given pattern string to this date format.
     *
     * <p>
     * 将给定的模式字符串应用于此日期格式
     * 
     * 
     * @param pattern the new date and time pattern for this date format
     * @exception NullPointerException if the given pattern is null
     * @exception IllegalArgumentException if the given pattern is invalid
     */
    public void applyPattern(String pattern)
    {
        applyPatternImpl(pattern);
    }

    private void applyPatternImpl(String pattern) {
        compiledPattern = compile(pattern);
        this.pattern = pattern;
    }

    /**
     * Applies the given localized pattern string to this date format.
     *
     * <p>
     *  将给定的本地化模式字符串应用于此日期格式
     * 
     * 
     * @param pattern a String to be mapped to the new date and time format
     *        pattern for this format
     * @exception NullPointerException if the given pattern is null
     * @exception IllegalArgumentException if the given pattern is invalid
     */
    public void applyLocalizedPattern(String pattern) {
         String p = translatePattern(pattern,
                                     formatData.getLocalPatternChars(),
                                     DateFormatSymbols.patternChars);
         compiledPattern = compile(p);
         this.pattern = p;
    }

    /**
     * Gets a copy of the date and time format symbols of this date format.
     *
     * <p>
     *  获取此日期格式的日期和时间格式符号的副本
     * 
     * 
     * @return the date and time format symbols of this date format
     * @see #setDateFormatSymbols
     */
    public DateFormatSymbols getDateFormatSymbols()
    {
        return (DateFormatSymbols)formatData.clone();
    }

    /**
     * Sets the date and time format symbols of this date format.
     *
     * <p>
     *  设置此日期格式的日期和时间格式符号
     * 
     * 
     * @param newFormatSymbols the new date and time format symbols
     * @exception NullPointerException if the given newFormatSymbols is null
     * @see #getDateFormatSymbols
     */
    public void setDateFormatSymbols(DateFormatSymbols newFormatSymbols)
    {
        this.formatData = (DateFormatSymbols)newFormatSymbols.clone();
        useDateFormatSymbols = true;
    }

    /**
     * Creates a copy of this <code>SimpleDateFormat</code>. This also
     * clones the format's date format symbols.
     *
     * <p>
     *  创建此<code> SimpleDateFormat </code>的副本这也克隆格式的日期格式符号
     * 
     * 
     * @return a clone of this <code>SimpleDateFormat</code>
     */
    @Override
    public Object clone() {
        SimpleDateFormat other = (SimpleDateFormat) super.clone();
        other.formatData = (DateFormatSymbols) formatData.clone();
        return other;
    }

    /**
     * Returns the hash code value for this <code>SimpleDateFormat</code> object.
     *
     * <p>
     *  返回此<> SimpleDateFormat </code>对象的哈希码值
     * 
     * 
     * @return the hash code value for this <code>SimpleDateFormat</code> object.
     */
    @Override
    public int hashCode()
    {
        return pattern.hashCode();
        // just enough fields for a reasonable distribution
    }

    /**
     * Compares the given object with this <code>SimpleDateFormat</code> for
     * equality.
     *
     * <p>
     *  将给定对象与此<code> SimpleDateFormat </code>进行比较以实现相等
     * 
     * 
     * @return true if the given object is equal to this
     * <code>SimpleDateFormat</code>
     */
    @Override
    public boolean equals(Object obj)
    {
        if (!super.equals(obj)) {
            return false; // super does class check
        }
        SimpleDateFormat that = (SimpleDateFormat) obj;
        return (pattern.equals(that.pattern)
                && formatData.equals(that.formatData));
    }

    private static final int[] REST_OF_STYLES = {
        Calendar.SHORT_STANDALONE, Calendar.LONG_FORMAT, Calendar.LONG_STANDALONE,
    };
    private Map<String, Integer> getDisplayNamesMap(int field, Locale locale) {
        Map<String, Integer> map = calendar.getDisplayNames(field, Calendar.SHORT_FORMAT, locale);
        // Get all SHORT and LONG styles (avoid NARROW styles).
        for (int style : REST_OF_STYLES) {
            Map<String, Integer> m = calendar.getDisplayNames(field, style, locale);
            if (m != null) {
                map.putAll(m);
            }
        }
        return map;
    }

    /**
     * After reading an object from the input stream, the format
     * pattern in the object is verified.
     * <p>
     * <p>
     *  在从输入流读取对象之后,验证对象中的格式模式
     * <p>
     * 
     * @exception InvalidObjectException if the pattern is invalid
     */
    private void readObject(ObjectInputStream stream)
                         throws IOException, ClassNotFoundException {
        stream.defaultReadObject();

        try {
            compiledPattern = compile(pattern);
        } catch (Exception e) {
            throw new InvalidObjectException("invalid pattern");
        }

        if (serialVersionOnStream < 1) {
            // didn't have defaultCenturyStart field
            initializeDefaultCentury();
        }
        else {
            // fill in dependent transient field
            parseAmbiguousDatesAsAfter(defaultCenturyStart);
        }
        serialVersionOnStream = currentSerialVersion;

        // If the deserialized object has a SimpleTimeZone, try
        // to replace it with a ZoneInfo equivalent in order to
        // be compatible with the SimpleTimeZone-based
        // implementation as much as possible.
        TimeZone tz = getTimeZone();
        if (tz instanceof SimpleTimeZone) {
            String id = tz.getID();
            TimeZone zi = TimeZone.getTimeZone(id);
            if (zi != null && zi.hasSameRules(tz) && zi.getID().equals(id)) {
                setTimeZone(zi);
            }
        }
    }

    /**
     * Analyze the negative subpattern of DecimalFormat and set/update values
     * as necessary.
     * <p>
     *  分析DecimalFormat的负子模式,并根据需要设置/更新值
     */
    private void checkNegativeNumberExpression() {
        if ((numberFormat instanceof DecimalFormat) &&
            !numberFormat.equals(originalNumberFormat)) {
            String numberPattern = ((DecimalFormat)numberFormat).toPattern();
            if (!numberPattern.equals(originalNumberPattern)) {
                hasFollowingMinusSign = false;

                int separatorIndex = numberPattern.indexOf(';');
                // If the negative subpattern is not absent, we have to analayze
                // it in order to check if it has a following minus sign.
                if (separatorIndex > -1) {
                    int minusIndex = numberPattern.indexOf('-', separatorIndex);
                    if ((minusIndex > numberPattern.lastIndexOf('0')) &&
                        (minusIndex > numberPattern.lastIndexOf('#'))) {
                        hasFollowingMinusSign = true;
                        minusSign = ((DecimalFormat)numberFormat).getDecimalFormatSymbols().getMinusSign();
                    }
                }
                originalNumberPattern = numberPattern;
            }
            originalNumberFormat = numberFormat;
        }
    }

}
