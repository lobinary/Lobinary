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
 * Copyright (c) 2008-2012, Stephen Colebourne & Michael Nascimento Santos
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
 *  版权所有(c)2008-2012,Stephen Colebourne和Michael Nascimento Santos
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
package java.time.format;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.NANO_OF_SECOND;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;
import static java.time.temporal.ChronoField.YEAR;

import java.io.IOException;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.time.DateTimeException;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatterBuilder.CompositePrinterParser;
import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQuery;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Formatter for printing and parsing date-time objects.
 * <p>
 * This class provides the main application entry point for printing and parsing
 * and provides common implementations of {@code DateTimeFormatter}:
 * <ul>
 * <li>Using predefined constants, such as {@link #ISO_LOCAL_DATE}</li>
 * <li>Using pattern letters, such as {@code uuuu-MMM-dd}</li>
 * <li>Using localized styles, such as {@code long} or {@code medium}</li>
 * </ul>
 * <p>
 * More complex formatters are provided by
 * {@link DateTimeFormatterBuilder DateTimeFormatterBuilder}.
 *
 * <p>
 * The main date-time classes provide two methods - one for formatting,
 * {@code format(DateTimeFormatter formatter)}, and one for parsing,
 * {@code parse(CharSequence text, DateTimeFormatter formatter)}.
 * <p>For example:
 * <blockquote><pre>
 *  String text = date.toString(formatter);
 *  LocalDate date = LocalDate.parse(text, formatter);
 * </pre></blockquote>
 * <p>
 * In addition to the format, formatters can be created with desired Locale,
 * Chronology, ZoneId, and DecimalStyle.
 * <p>
 * The {@link #withLocale withLocale} method returns a new formatter that
 * overrides the locale. The locale affects some aspects of formatting and
 * parsing. For example, the {@link #ofLocalizedDate ofLocalizedDate} provides a
 * formatter that uses the locale specific date format.
 * <p>
 * The {@link #withChronology withChronology} method returns a new formatter
 * that overrides the chronology. If overridden, the date-time value is
 * converted to the chronology before formatting. During parsing the date-time
 * value is converted to the chronology before it is returned.
 * <p>
 * The {@link #withZone withZone} method returns a new formatter that overrides
 * the zone. If overridden, the date-time value is converted to a ZonedDateTime
 * with the requested ZoneId before formatting. During parsing the ZoneId is
 * applied before the value is returned.
 * <p>
 * The {@link #withDecimalStyle withDecimalStyle} method returns a new formatter that
 * overrides the {@link DecimalStyle}. The DecimalStyle symbols are used for
 * formatting and parsing.
 * <p>
 * Some applications may need to use the older {@link Format java.text.Format}
 * class for formatting. The {@link #toFormat()} method returns an
 * implementation of {@code java.text.Format}.
 *
 * <h3 id="predefined">Predefined Formatters</h3>
 * <table summary="Predefined Formatters" cellpadding="2" cellspacing="3" border="0" >
 * <thead>
 * <tr class="tableSubHeadingColor">
 * <th class="colFirst" align="left">Formatter</th>
 * <th class="colFirst" align="left">Description</th>
 * <th class="colLast" align="left">Example</th>
 * </tr>
 * </thead>
 * <tbody>
 * <tr class="rowColor">
 * <td>{@link #ofLocalizedDate ofLocalizedDate(dateStyle)} </td>
 * <td> Formatter with date style from the locale </td>
 * <td> '2011-12-03'</td>
 * </tr>
 * <tr class="altColor">
 * <td> {@link #ofLocalizedTime ofLocalizedTime(timeStyle)} </td>
 * <td> Formatter with time style from the locale </td>
 * <td> '10:15:30'</td>
 * </tr>
 * <tr class="rowColor">
 * <td> {@link #ofLocalizedDateTime ofLocalizedDateTime(dateTimeStyle)} </td>
 * <td> Formatter with a style for date and time from the locale</td>
 * <td> '3 Jun 2008 11:05:30'</td>
 * </tr>
 * <tr class="altColor">
 * <td> {@link #ofLocalizedDateTime ofLocalizedDateTime(dateStyle,timeStyle)}
 * </td>
 * <td> Formatter with date and time styles from the locale </td>
 * <td> '3 Jun 2008 11:05'</td>
 * </tr>
 * <tr class="rowColor">
 * <td> {@link #BASIC_ISO_DATE}</td>
 * <td>Basic ISO date </td> <td>'20111203'</td>
 * </tr>
 * <tr class="altColor">
 * <td> {@link #ISO_LOCAL_DATE}</td>
 * <td> ISO Local Date </td>
 * <td>'2011-12-03'</td>
 * </tr>
 * <tr class="rowColor">
 * <td> {@link #ISO_OFFSET_DATE}</td>
 * <td> ISO Date with offset </td>
 * <td>'2011-12-03+01:00'</td>
 * </tr>
 * <tr class="altColor">
 * <td> {@link #ISO_DATE}</td>
 * <td> ISO Date with or without offset </td>
 * <td> '2011-12-03+01:00'; '2011-12-03'</td>
 * </tr>
 * <tr class="rowColor">
 * <td> {@link #ISO_LOCAL_TIME}</td>
 * <td> Time without offset </td>
 * <td>'10:15:30'</td>
 * </tr>
 * <tr class="altColor">
 * <td> {@link #ISO_OFFSET_TIME}</td>
 * <td> Time with offset </td>
 * <td>'10:15:30+01:00'</td>
 * </tr>
 * <tr class="rowColor">
 * <td> {@link #ISO_TIME}</td>
 * <td> Time with or without offset </td>
 * <td>'10:15:30+01:00'; '10:15:30'</td>
 * </tr>
 * <tr class="altColor">
 * <td> {@link #ISO_LOCAL_DATE_TIME}</td>
 * <td> ISO Local Date and Time </td>
 * <td>'2011-12-03T10:15:30'</td>
 * </tr>
 * <tr class="rowColor">
 * <td> {@link #ISO_OFFSET_DATE_TIME}</td>
 * <td> Date Time with Offset
 * </td><td>2011-12-03T10:15:30+01:00'</td>
 * </tr>
 * <tr class="altColor">
 * <td> {@link #ISO_ZONED_DATE_TIME}</td>
 * <td> Zoned Date Time </td>
 * <td>'2011-12-03T10:15:30+01:00[Europe/Paris]'</td>
 * </tr>
 * <tr class="rowColor">
 * <td> {@link #ISO_DATE_TIME}</td>
 * <td> Date and time with ZoneId </td>
 * <td>'2011-12-03T10:15:30+01:00[Europe/Paris]'</td>
 * </tr>
 * <tr class="altColor">
 * <td> {@link #ISO_ORDINAL_DATE}</td>
 * <td> Year and day of year </td>
 * <td>'2012-337'</td>
 * </tr>
 * <tr class="rowColor">
 * <td> {@link #ISO_WEEK_DATE}</td>
 * <td> Year and Week </td>
 * <td>2012-W48-6'</td></tr>
 * <tr class="altColor">
 * <td> {@link #ISO_INSTANT}</td>
 * <td> Date and Time of an Instant </td>
 * <td>'2011-12-03T10:15:30Z' </td>
 * </tr>
 * <tr class="rowColor">
 * <td> {@link #RFC_1123_DATE_TIME}</td>
 * <td> RFC 1123 / RFC 822 </td>
 * <td>'Tue, 3 Jun 2008 11:05:30 GMT'</td>
 * </tr>
 * </tbody>
 * </table>
 *
 * <h3 id="patterns">Patterns for Formatting and Parsing</h3>
 * Patterns are based on a simple sequence of letters and symbols.
 * A pattern is used to create a Formatter using the
 * {@link #ofPattern(String)} and {@link #ofPattern(String, Locale)} methods.
 * For example,
 * {@code "d MMM uuuu"} will format 2011-12-03 as '3&nbsp;Dec&nbsp;2011'.
 * A formatter created from a pattern can be used as many times as necessary,
 * it is immutable and is thread-safe.
 * <p>
 * For example:
 * <blockquote><pre>
 *  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
 *  String text = date.toString(formatter);
 *  LocalDate date = LocalDate.parse(text, formatter);
 * </pre></blockquote>
 * <p>
 * All letters 'A' to 'Z' and 'a' to 'z' are reserved as pattern letters. The
 * following pattern letters are defined:
 * <pre>
 *  Symbol  Meaning                     Presentation      Examples
 *  ------  -------                     ------------      -------
 *   G       era                         text              AD; Anno Domini; A
 *   u       year                        year              2004; 04
 *   y       year-of-era                 year              2004; 04
 *   D       day-of-year                 number            189
 *   M/L     month-of-year               number/text       7; 07; Jul; July; J
 *   d       day-of-month                number            10
 *
 *   Q/q     quarter-of-year             number/text       3; 03; Q3; 3rd quarter
 *   Y       week-based-year             year              1996; 96
 *   w       week-of-week-based-year     number            27
 *   W       week-of-month               number            4
 *   E       day-of-week                 text              Tue; Tuesday; T
 *   e/c     localized day-of-week       number/text       2; 02; Tue; Tuesday; T
 *   F       week-of-month               number            3
 *
 *   a       am-pm-of-day                text              PM
 *   h       clock-hour-of-am-pm (1-12)  number            12
 *   K       hour-of-am-pm (0-11)        number            0
 *   k       clock-hour-of-am-pm (1-24)  number            0
 *
 *   H       hour-of-day (0-23)          number            0
 *   m       minute-of-hour              number            30
 *   s       second-of-minute            number            55
 *   S       fraction-of-second          fraction          978
 *   A       milli-of-day                number            1234
 *   n       nano-of-second              number            987654321
 *   N       nano-of-day                 number            1234000000
 *
 *   V       time-zone ID                zone-id           America/Los_Angeles; Z; -08:30
 *   z       time-zone name              zone-name         Pacific Standard Time; PST
 *   O       localized zone-offset       offset-O          GMT+8; GMT+08:00; UTC-08:00;
 *   X       zone-offset 'Z' for zero    offset-X          Z; -08; -0830; -08:30; -083015; -08:30:15;
 *   x       zone-offset                 offset-x          +0000; -08; -0830; -08:30; -083015; -08:30:15;
 *   Z       zone-offset                 offset-Z          +0000; -0800; -08:00;
 *
 *   p       pad next                    pad modifier      1
 *
 *   '       escape for text             delimiter
 *   ''      single quote                literal           '
 *   [       optional section start
 *   ]       optional section end
 *   #       reserved for future use
 *   {       reserved for future use
 *   }       reserved for future use
 * </pre>
 * <p>
 * The count of pattern letters determines the format.
 * <p>
 * <b>Text</b>: The text style is determined based on the number of pattern
 * letters used. Less than 4 pattern letters will use the
 * {@link TextStyle#SHORT short form}. Exactly 4 pattern letters will use the
 * {@link TextStyle#FULL full form}. Exactly 5 pattern letters will use the
 * {@link TextStyle#NARROW narrow form}.
 * Pattern letters 'L', 'c', and 'q' specify the stand-alone form of the text styles.
 * <p>
 * <b>Number</b>: If the count of letters is one, then the value is output using
 * the minimum number of digits and without padding. Otherwise, the count of digits
 * is used as the width of the output field, with the value zero-padded as necessary.
 * The following pattern letters have constraints on the count of letters.
 * Only one letter of 'c' and 'F' can be specified.
 * Up to two letters of 'd', 'H', 'h', 'K', 'k', 'm', and 's' can be specified.
 * Up to three letters of 'D' can be specified.
 * <p>
 * <b>Number/Text</b>: If the count of pattern letters is 3 or greater, use the
 * Text rules above. Otherwise use the Number rules above.
 * <p>
 * <b>Fraction</b>: Outputs the nano-of-second field as a fraction-of-second.
 * The nano-of-second value has nine digits, thus the count of pattern letters
 * is from 1 to 9. If it is less than 9, then the nano-of-second value is
 * truncated, with only the most significant digits being output.
 * <p>
 * <b>Year</b>: The count of letters determines the minimum field width below
 * which padding is used. If the count of letters is two, then a
 * {@link DateTimeFormatterBuilder#appendValueReduced reduced} two digit form is
 * used. For printing, this outputs the rightmost two digits. For parsing, this
 * will parse using the base value of 2000, resulting in a year within the range
 * 2000 to 2099 inclusive. If the count of letters is less than four (but not
 * two), then the sign is only output for negative years as per
 * {@link SignStyle#NORMAL}. Otherwise, the sign is output if the pad width is
 * exceeded, as per {@link SignStyle#EXCEEDS_PAD}.
 * <p>
 * <b>ZoneId</b>: This outputs the time-zone ID, such as 'Europe/Paris'. If the
 * count of letters is two, then the time-zone ID is output. Any other count of
 * letters throws {@code IllegalArgumentException}.
 * <p>
 * <b>Zone names</b>: This outputs the display name of the time-zone ID. If the
 * count of letters is one, two or three, then the short name is output. If the
 * count of letters is four, then the full name is output. Five or more letters
 * throws {@code IllegalArgumentException}.
 * <p>
 * <b>Offset X and x</b>: This formats the offset based on the number of pattern
 * letters. One letter outputs just the hour, such as '+01', unless the minute
 * is non-zero in which case the minute is also output, such as '+0130'. Two
 * letters outputs the hour and minute, without a colon, such as '+0130'. Three
 * letters outputs the hour and minute, with a colon, such as '+01:30'. Four
 * letters outputs the hour and minute and optional second, without a colon,
 * such as '+013015'. Five letters outputs the hour and minute and optional
 * second, with a colon, such as '+01:30:15'. Six or more letters throws
 * {@code IllegalArgumentException}. Pattern letter 'X' (upper case) will output
 * 'Z' when the offset to be output would be zero, whereas pattern letter 'x'
 * (lower case) will output '+00', '+0000', or '+00:00'.
 * <p>
 * <b>Offset O</b>: This formats the localized offset based on the number of
 * pattern letters. One letter outputs the {@linkplain TextStyle#SHORT short}
 * form of the localized offset, which is localized offset text, such as 'GMT',
 * with hour without leading zero, optional 2-digit minute and second if
 * non-zero, and colon, for example 'GMT+8'. Four letters outputs the
 * {@linkplain TextStyle#FULL full} form, which is localized offset text,
 * such as 'GMT, with 2-digit hour and minute field, optional second field
 * if non-zero, and colon, for example 'GMT+08:00'. Any other count of letters
 * throws {@code IllegalArgumentException}.
 * <p>
 * <b>Offset Z</b>: This formats the offset based on the number of pattern
 * letters. One, two or three letters outputs the hour and minute, without a
 * colon, such as '+0130'. The output will be '+0000' when the offset is zero.
 * Four letters outputs the {@linkplain TextStyle#FULL full} form of localized
 * offset, equivalent to four letters of Offset-O. The output will be the
 * corresponding localized offset text if the offset is zero. Five
 * letters outputs the hour, minute, with optional second if non-zero, with
 * colon. It outputs 'Z' if the offset is zero.
 * Six or more letters throws {@code IllegalArgumentException}.
 * <p>
 * <b>Optional section</b>: The optional section markers work exactly like
 * calling {@link DateTimeFormatterBuilder#optionalStart()} and
 * {@link DateTimeFormatterBuilder#optionalEnd()}.
 * <p>
 * <b>Pad modifier</b>: Modifies the pattern that immediately follows to be
 * padded with spaces. The pad width is determined by the number of pattern
 * letters. This is the same as calling
 * {@link DateTimeFormatterBuilder#padNext(int)}.
 * <p>
 * For example, 'ppH' outputs the hour-of-day padded on the left with spaces to
 * a width of 2.
 * <p>
 * Any unrecognized letter is an error. Any non-letter character, other than
 * '[', ']', '{', '}', '#' and the single quote will be output directly.
 * Despite this, it is recommended to use single quotes around all characters
 * that you want to output directly to ensure that future changes do not break
 * your application.
 *
 * <h3 id="resolving">Resolving</h3>
 * Parsing is implemented as a two-phase operation.
 * First, the text is parsed using the layout defined by the formatter, producing
 * a {@code Map} of field to value, a {@code ZoneId} and a {@code Chronology}.
 * Second, the parsed data is <em>resolved</em>, by validating, combining and
 * simplifying the various fields into more useful ones.
 * <p>
 * Five parsing methods are supplied by this class.
 * Four of these perform both the parse and resolve phases.
 * The fifth method, {@link #parseUnresolved(CharSequence, ParsePosition)},
 * only performs the first phase, leaving the result unresolved.
 * As such, it is essentially a low-level operation.
 * <p>
 * The resolve phase is controlled by two parameters, set on this class.
 * <p>
 * The {@link ResolverStyle} is an enum that offers three different approaches,
 * strict, smart and lenient. The smart option is the default.
 * It can be set using {@link #withResolverStyle(ResolverStyle)}.
 * <p>
 * The {@link #withResolverFields(TemporalField...)} parameter allows the
 * set of fields that will be resolved to be filtered before resolving starts.
 * For example, if the formatter has parsed a year, month, day-of-month
 * and day-of-year, then there are two approaches to resolve a date:
 * (year + month + day-of-month) and (year + day-of-year).
 * The resolver fields allows one of the two approaches to be selected.
 * If no resolver fields are set then both approaches must result in the same date.
 * <p>
 * Resolving separate fields to form a complete date and time is a complex
 * process with behaviour distributed across a number of classes.
 * It follows these steps:
 * <ol>
 * <li>The chronology is determined.
 * The chronology of the result is either the chronology that was parsed,
 * or if no chronology was parsed, it is the chronology set on this class,
 * or if that is null, it is {@code IsoChronology}.
 * <li>The {@code ChronoField} date fields are resolved.
 * This is achieved using {@link Chronology#resolveDate(Map, ResolverStyle)}.
 * Documentation about field resolution is located in the implementation
 * of {@code Chronology}.
 * <li>The {@code ChronoField} time fields are resolved.
 * This is documented on {@link ChronoField} and is the same for all chronologies.
 * <li>Any fields that are not {@code ChronoField} are processed.
 * This is achieved using {@link TemporalField#resolve(Map, TemporalAccessor, ResolverStyle)}.
 * Documentation about field resolution is located in the implementation
 * of {@code TemporalField}.
 * <li>The {@code ChronoField} date and time fields are re-resolved.
 * This allows fields in step four to produce {@code ChronoField} values
 * and have them be processed into dates and times.
 * <li>A {@code LocalTime} is formed if there is at least an hour-of-day available.
 * This involves providing default values for minute, second and fraction of second.
 * <li>Any remaining unresolved fields are cross-checked against any
 * date and/or time that was resolved. Thus, an earlier stage would resolve
 * (year + month + day-of-month) to a date, and this stage would check that
 * day-of-week was valid for the date.
 * <li>If an {@linkplain #parsedExcessDays() excess number of days}
 * was parsed then it is added to the date if a date is available.
 * </ol>
 *
 * @implSpec
 * This class is immutable and thread-safe.
 *
 * <p>
 * 用于打印和解析日期时间对象的格式化程序
 * <p>
 *  此类提供了打印和解析的主要应用程序入口点,并提供了{@code DateTimeFormatter}的常见实现：
 * <ul>
 *  <li>使用预设常数,例如{@link #ISO_LOCAL_DATE} </li> <li>使用图案字母,例如{@code uuuu-MMM-dd} </li> <li> {@code long}或
 * {@code medium} </li>。
 * </ul>
 * <p>
 *  更复杂的格式化程序由{@link DateTimeFormatterBuilder DateTimeFormatterBuilder}
 * 
 * <p>
 * 主日期时间类提供两种方法 - 一种用于格式化,{@code format(DateTimeFormatter formatter)}和一种用于解析,{@code parse(CharSequence text,DateTimeFormatter formatter)}
 *  <p>例如：<blockquote> <pre> String text = datetoString(formatter); LocalDate date = LocalDateparse(text
 * ,formatter); </pre> </blockquote>。
 * <p>
 *  除了格式,格式化器可以创建所需的语言环境,年表,ZoneId和DecimalStyle
 * <p>
 *  {@link #withLocale withLocale}方法返回一个覆盖语言环境的新格式化程序语言环境影响格式化和解析的某些方面例如,{@link #ofLocalizedDate ofLocalizedDate}
 * 提供了一个使用区域设置特定日期格式的格式化程序。
 * <p>
 * {@link #withChronology with Chronology}方法返回一个新的格式化程序,该格式化程序将覆盖年表。如果覆盖,日期 - 时间值将在格式化之前转换为年表。
 * 在解析期间,日期 - 时间值将在返回之前转换为年表。
 * <p>
 *  {@link #withZone withZone}方法返回覆盖区域的新格式化程序如果覆盖,日期时间值将在格式化之前转换为具有请求的ZoneId的ZonedDateTime在解析期间,在返回值之前应用
 * ZoneId。
 * <p>
 *  {@link #withDecimalStyle withDecimalStyle}方法返回一个新的格式化程序,覆盖{@link DecimalStyle} DecimalStyle符号用于格式化和解
 * 析。
 * <p>
 * 一些应用程序可能需要使用较旧的{@link格式javatextFormat}类进行格式化{@link #toFormat()}方法返回一个{@code javatextFormat}
 * 
 *  <h3 id ="predefined">预定义格式器</h3>
 * <table summary="Predefined Formatters" cellpadding="2" cellspacing="3" border="0" >
 * <thead>
 * <tr class="tableSubHeadingColor">
 *  <th class ="colFirst"align ="left">格式化</th> <th class ="colFirst"align ="left">描述</th> <th class ="colLast"align ="left">
 * 示例</th>。
 * </tr>
 * </thead>
 * <tbody>
 * <tr class="rowColor">
 *  <td> {@ link #ofLocalizedLocalizedDate ofLocalizedDate(dateStyle)} </td> <td>具有来自区域设置的日期样式的格式化</td> 
 * <td>'2011-12-03'</td>。
 * </tr>
 * <tr class="altColor">
 *  <td> {@link #ofLocalizedTime ofLocalizedTime(timeStyle)} </td> <td>具有来自区域设置的时间样式的格式化</td> <td> '10：1
 * 5：30'</td>。
 * </tr>
 * <tr class="rowColor">
 * <td> {@link #ofLocalizedDateTime ofLocalizedDateTime(dateTimeStyle)} </td> <td>格式化程序带有来自语言环境的日期和时间样式</td>
 *  <td>'3 Jun 2008 11:05:30' td>。
 * </tr>
 * <tr class="altColor">
 *  <td> {@link #ofLocalizedDateTime ofLocalizedDateTime(dateStyle,timeStyle)}
 * </td>
 *  <td>具有来自区域设置的日期和时间样式的格式器</td> <td>'6 Jun 2008 11:05'</td>
 * </tr>
 * <tr class="rowColor">
 *  <td> {@link #BASIC_ISO_DATE} </td> <td>基本ISO日期</td> <td>'20111203'</td>
 * </tr>
 * <tr class="altColor">
 *  <td> {@link #ISO_LOCAL_DATE} </td> <td> ISO本​​地日期</td> <td>'2011-12-03'</td>
 * </tr>
 * <tr class="rowColor">
 *  <td> {@link #ISO_OFFSET_DATE} </td> <td> ISO日期(偏移量)</td> <td>'2011-12-03 + 01：00'</td>
 * </tr>
 * <tr class="altColor">
 *  <td> {@link #ISO_DATE} </td> <td>含或不含偏移量的ISO日期</td> <td>'2011-12-03 + 01：00'; '2011-12-03'</td>
 * </tr>
 * <tr class="rowColor">
 * <td> {@link #ISO_LOCAL_TIME} </td> <td>无偏移的时间</td> <td> '10：15：30'</td>
 * </tr>
 * <tr class="altColor">
 *  <td> {@link #ISO_OFFSET_TIME} </td> <td>偏移时间</td> <td> '10：15：30 + 01：00'</td>
 * </tr>
 * <tr class="rowColor">
 *  <td> {@link #ISO_TIME} </td> <td>有或没有偏移的时间</td> <td> '10：15：30 + 01：00'; '10：15：30'</td>
 * </tr>
 * <tr class="altColor">
 *  <td> {@link #ISO_LOCAL_DATE_TIME} </td> <td> ISO本​​地日期和时间</td> <td>'2011-12-03T10：15：30'</td>
 * </tr>
 * <tr class="rowColor">
 *  <td> {@link #ISO_OFFSET_DATE_TIME} </td> <td>日期时间偏移</td> <td> 2011-12-03T10：15：30 + 01：00'</td>
 * </tr>
 * <tr class="altColor">
 *  <td> {@link #ISO_ZONED_DATE_TIME} </td> <td>分区日期时间</td> <td>'2011-12-03T10：15：30 + 01：00 [欧洲/巴黎]'</td>
 * 。
 * </tr>
 * <tr class="rowColor">
 *  <td> {@link #ISO_DATE_TIME} </td> <td>使用ZoneId的日期和时间</td> <td>'2011-12-03T10：15：30 + 01：00 [欧洲/巴黎] t
 * d>。
 * </tr>
 * <tr class="altColor">
 * <td> {@link #ISO_ORDINAL_DATE} </td> <td>年和年中的日期</td> <td>'2012-337'</td>
 * </tr>
 * <tr class="rowColor">
 *  <td> {@link #ISO_WEEK_DATE} </td> <td>年和周</td> <td> 2012-W48-6'</td> </tr>
 * <tr class="altColor">
 *  <td> {@link #ISO_INSTANT} </td> <td>即时日期和时间</td> <td>'2011-12-03T10：15：30Z'</td>
 * </tr>
 * <tr class="rowColor">
 *  <td> {@link#RFC_1123_DATE_TIME} </td> <td> RFC 1123 / RFC 822 </td> <td>'Tue,3 Jun 2008 11:05:30 GMT
 * '</td>。
 * </tr>
 * </tbody>
 * </table>
 * 
 * <h3 id ="patterns">格式化和解析的模式</h3>模式基于一个简单的字母和符号序列模式用于使用{@link #ofPattern(String)}和{@ link #ofPattern(String,Locale)}
 * 方法例如,{@code"d MMM uuuu"}将格式2011-12-03设置为'3&nbsp; Dec&nbsp; 2011'从模式创建的格式化程序可以使用多次必要的,它是不可变的,并且是线程安全的。
 * <p>
 *  例如：<blockquote> <pre> DateTimeFormatter formatter = DateTimeFormatterofPattern("yyyy MM dd"); String
 *  text = datetoString(formatter); LocalDate date = LocalDateparse(text,formatter); </pre> </blockquote>
 * 。
 * <p>
 *  所有字母'A'至'Z'和'a'至'z'保留为模式字母定义以下模式字母：
 * <pre>
 * 符号含义表示示例------ ------- ------------ ------- G时代文本AD;公元; 2004年; 2004年04年; 04 D年份数189 M / L年份数字/文本7; 07
 * ; Jul;七月; J d每月第10天。
 * 
 * Q / q四分之一数字/文本3; 03; Q3;第三季度Y每周年度1996年; 96 w基于星期的年份数27 W星期几数字4 E星期几文本星期;星期二; T e / c本地化星期数/文本2; 02;星期
 * 二星期二; T F月份第3周。
 * 
 *  am-pm-of-day文本PM h clock-hour-of-am-pm(1-12)number 12 K hour-of-am-pm(0-11)number 0 k clock-of-am -p
 * m(1-24)数字0。
 * 
 * H小时(0-23)数0分钟小时数30秒秒数55 S第二分数部分978 A毫日数1234 n纳米 - 第二个数字987654321 N纳天数1234000000
 * 
 * V时区ID zone-id America / Los_Angeles; Z; -08：30 z时区名称区域名太平洋标准时间; PST O本地化区域偏移偏移-O GMT + 8; GMT + 08：00
 * ; UTC-08：00; X区偏移"Z"用于零偏移-X Z; -08; -0830; -08：30; -083015; -08：30：15; x zone-offset offset-x +0000; 
 * -08; -0830; -08：30; -083015; -08：30：15; Z zone-offset offset-Z +0000; -0800; -08：00;。
 * 
 *  p垫下一垫修改器1
 * 
 * '文本分隔符的转义''单引号文字'[可选部分开始]可选部分结束#保留供将来使用{保留供未来使用}保留供将来使用
 * </pre>
 * <p>
 *  模式字母的数量决定了格式
 * <p>
 *  <b>文本</b>：文本样式根据使用的模式字母数确定少于4个模式字母将使用{@link TextStyle#SHORT short形式}正好4个模式字母将使用{@link TextStyle #FULL full form}
 * 正确的5个模式字母将使用{@link TextStyle#NARROW窄形式}模式字母'L','c'和'q'指定文本样式的独立形式。
 * <p>
 * <b>数字</b>：如果字母数为1,则使用最小位数而不填充输出值。
 * 否则,数字计数将用作输出字段的宽度,值根据需要进行零填充以下模式字母对字母计数有限制只能指定'c'和'F'的一个字母最多两个字母'd','H','h','K' ,'k','m'和's'可以指定最多可以指
 * 定'D'的三个字母。
 * <b>数字</b>：如果字母数为1,则使用最小位数而不填充输出值。
 * <p>
 *  <b>数字/文本</b>：如果模式字母数为3或更大,请使用上面的文本规则。否则使用上面的数字规则
 * <p>
 * <b>分数</b>：以秒为单位输出纳秒秒字段纳秒秒值具有九位数字,因此,图案字母数从1到9如果它少于比9,则纳秒的值被截断,只有最高有效数字被输出
 * <p>
 * <b>年</b>：字母计数决定使用填充的最小字段宽度如果字母计数为2,则使用{@link DateTimeFormatterBuilder#appendValueReduced reduced}两位数字
 * 形式进行打印,输出最右边的两位数字对于语法分析,将使用基本值2000解析,导致2000到2099范围内的年份。
 * 如果字母数小于四(但不是两个),则只输出符号对于负数年{@link SignStyle#NORMAL}否则,如果超过垫宽度,输出符号{@link SignStyle#EXCEEDS_PAD}。
 * <p>
 * <b> ZoneId </b>：输出时区ID,如"Europe / Paris"如果字母数为2,则输出时区ID任何其他字母数字都会抛出{@code IllegalArgumentException }}
 * 。
 * <p>
 *  <b>区域名称</b>：输出时区ID的显示名称如果字母数为一,二或三,则输出短名称如果字母数为四,则完整name是输出五个或多个字母throws {@code IllegalArgumentException}
 * 。
 * <p>
 * <b>偏移量X和x </b>：根据图案字母数量格式化偏移量一个字母只输出小时,例如"+01",除非分钟为非零,在这种情况下,也输出,如'+0130'两个字母输出小时和分钟,不带冒号,例如'+0130'三
 * 个字母输出小时和分钟,带冒号,例如'+01：30'四个字母输出小时和分钟和可选的第二个,不带冒号,例如'+013015'五个字母输出小时和分钟,可选第二个,带冒号,例如'+01：30：15'六个或更多个
 * 字母{代码IllegalArgumentException}当要输出的偏移量为零时,模式字母'X'(大写)将输出'Z',而模式字母'x'(小写)将输出'+00','+0000' '+00：00'。
 * <p>
 * <b>偏移O </b>：此格式根据模式字母数量格式化本地化偏移量一个字母输出本地化偏移量的{@linkplain TextStyle#SHORT short}形式,即本地化偏移量文本,例如'GMT ',
 * 小时不带前导零,可选2位数分钟和秒(如果非零)和冒号(例如'GMT + 8')四个字母输出{@linkplain TextStyle#FULL full}例如'GMT,带有2位小时和分钟字段,可选的第二
 * 个字段(如果非零)和冒号(例如'GMT + 08：00')任何其他字母数字都会抛出{@code IllegalArgumentException}。
 * <p>
 * <b>偏移Z </b>：根据图案字母数量格式化偏移量一个,两个或三个字母输出小时和分钟,不带冒号,例如"+0130"输出将为"+0000 '当偏移量为零时四个字母输出本地化偏移量的{@linkplain TextStyle#FULL full}
 * 形式,相当于Offset-O的四个字母如果偏移量为零,则输出将是相应的本地化偏移量文本五个字母输出小时,分钟,可选第二(如果非零),冒号如果偏移量为零,则输出'Z'。
 * 六个或更多个字母抛出{@code IllegalArgumentException}。
 * <p>
 *  <b>可选部分</b>：可选部分标记的工作方式与调用{@link DateTimeFormatterBuilder#optionalStart()}和{@link DateTimeFormatterBuilder#optionalEnd()}
 * 完全相同。
 * <p>
 * <b>填充修改符</b>：修改紧接着用空格填充的模式填充宽度由模式字母数确定这与调用{@link DateTimeFormatterBuilder#padNext(int)}相同
 * <p>
 *  例如,'ppH'输出在左侧填充的时间,空格的宽度为2
 * <p>
 *  任何无法识别的字母是错误除了'[',']','{','}','#'和单引号之外的任何非字母字符将被直接输出尽管这样,引用所有要直接输出的字符,以确保未来的更改不会中断您的应用程序
 * 
 * <h3 id ="resolving">解析</h3>解析实现为两阶段操作首先,使用由格式化程序定义的布局解析文本,生成字段值{@code Map} @code ZoneId}和{@code Chronology}
 * 二,通过验证,组合和简化各个字段为更有用的实例,解析的数据被<em>解析</em>。
 * <p>
 *  这个类提供了五个解析方法其中四个同时执行解析和解析阶段第五种方法{@link #parseUnresolved(CharSequence,ParsePosition)}只执行第一阶段,结果未解析。
 * 因此,低级操作。
 * <p>
 *  解析阶段由在此类上设置的两个参数控制
 * <p>
 * {@link ResolverStyle}是一个枚举,提供三种不同的方法,严格,智能和宽松智能选项是默认它可以使用{@link #withResolverStyle(ResolverStyle)}
 * <p>
 *  {@link #withResolverFields(TemporalField)}参数允许在解析开始之前过滤要解析的字段集合例如,如果格式化程序已解析了年,月,日和年,则有两种方法来解析日期：(年+
 * 月+日)和(年+年)解析器字段允许选择两种方法之一如果没有设置解析器字段那么这两种方法必须导致相同的日期。
 * <p>
 * 解析单独的字段以形成完整的日期和时间是一个复杂的过程,其行为分布在多个类中它遵循以下步骤：
 * <ol>
 * <li>确定年表的时间顺序结果的年表是要解析的年表,或者如果没有年表被解析,那么这是在这个类上设置的年表,或者如果它是null,它是{@code IsoChronology} < li> {@code ChronoField}
 * 日期字段已解决这是使用{@link Chronology#resolveDate(Map,ResolverStyle)}实现的关于字段解析的文档位于{@code Chronology}的实现<li> {@code ChronoField}
 * 时间字段被解析这在{@link ChronoField}上记录,并且对所有的时间表都是相同的<li>任何不是{@code ChronoField}的字段被处理这是使用{@link TemporalField#resolve(Map, TemporalAccessor,ResolverStyle)}
 * 关于字段解析的文档位于{@code TemporalField}的实现中<li>重新解析{@code ChronoField}日期和时间字段这允许第四步中的字段产生{@code ChronoField}
 * 值,并使它们成为处理成日期和时间<li>如果至少有一个小时可用,则形成{@code LocalTime}这包括为秒,秒和小数的秒提供默认值<li>任何剩余的未解决字段为cross - 针对已解决的任何日
 * 期和/或时间检查。
 * 因此,较早阶段将解决(年+月+日)为日期,并且该阶段将检查星期对于日期是否有效<li>如果{@linkplain #parsedExcessDays()超过天数}已解析,则将其添加到日期(如果有日期)。
 * </ol>
 * 
 * @implSpec这个类是不可变的和线程安全的
 * 
 * 
 * @since 1.8
 */
public final class DateTimeFormatter {

    /**
     * The printer and/or parser to use, not null.
     * <p>
     *  要使用的打印机和/或解析器,不能为null
     * 
     */
    private final CompositePrinterParser printerParser;
    /**
     * The locale to use for formatting, not null.
     * <p>
     *  用于格式化的语言环境,不是null
     * 
     */
    private final Locale locale;
    /**
     * The symbols to use for formatting, not null.
     * <p>
     *  用于格式化的符号,不为null
     * 
     */
    private final DecimalStyle decimalStyle;
    /**
     * The resolver style to use, not null.
     * <p>
     *  要使用的解析器样式,不是null
     * 
     */
    private final ResolverStyle resolverStyle;
    /**
     * The fields to use in resolving, null for all fields.
     * <p>
     *  要在解析中使用的字段,所有字段为空
     * 
     */
    private final Set<TemporalField> resolverFields;
    /**
     * The chronology to use for formatting, null for no override.
     * <p>
     *  用于格式化的年表,无重写
     * 
     */
    private final Chronology chrono;
    /**
     * The zone to use for formatting, null for no override.
     * <p>
     *  用于格式化的区域,无重写的null
     * 
     */
    private final ZoneId zone;

    //-----------------------------------------------------------------------
    /**
     * Creates a formatter using the specified pattern.
     * <p>
     * This method will create a formatter based on a simple
     * <a href="#patterns">pattern of letters and symbols</a>
     * as described in the class documentation.
     * For example, {@code d MMM uuuu} will format 2011-12-03 as '3 Dec 2011'.
     * <p>
     * The formatter will use the {@link Locale#getDefault(Locale.Category) default FORMAT locale}.
     * This can be changed using {@link DateTimeFormatter#withLocale(Locale)} on the returned formatter
     * Alternatively use the {@link #ofPattern(String, Locale)} variant of this method.
     * <p>
     * The returned formatter has no override chronology or zone.
     * It uses {@link ResolverStyle#SMART SMART} resolver style.
     *
     * <p>
     *  使用指定的模式创建格式化程序
     * <p>
     *  此方法将基于类文档中所述的简单<a href=\"#patterns\">字母和符号</a>模式创建格式化程序。
     * 例如,{@code d MMM uuuu}将格式化2011-12- 03 as'3 Dec 2011'。
     * <p>
     * 格式化程序将使用{@link Locale#getDefault(LocaleCategory)default FORMAT locale}可以使用{@link DateTimeFormatter#withLocale(Locale)}
     * 在返回的格式化程序上更改或者使用{@link #ofPattern(String,Locale) }这个方法的变体。
     * <p>
     *  返回的格式化程序没有重写年表或区域它使用{@link ResolverStyle#SMART SMART}解析器样式
     * 
     * 
     * @param pattern  the pattern to use, not null
     * @return the formatter based on the pattern, not null
     * @throws IllegalArgumentException if the pattern is invalid
     * @see DateTimeFormatterBuilder#appendPattern(String)
     */
    public static DateTimeFormatter ofPattern(String pattern) {
        return new DateTimeFormatterBuilder().appendPattern(pattern).toFormatter();
    }

    /**
     * Creates a formatter using the specified pattern and locale.
     * <p>
     * This method will create a formatter based on a simple
     * <a href="#patterns">pattern of letters and symbols</a>
     * as described in the class documentation.
     * For example, {@code d MMM uuuu} will format 2011-12-03 as '3 Dec 2011'.
     * <p>
     * The formatter will use the specified locale.
     * This can be changed using {@link DateTimeFormatter#withLocale(Locale)} on the returned formatter
     * <p>
     * The returned formatter has no override chronology or zone.
     * It uses {@link ResolverStyle#SMART SMART} resolver style.
     *
     * <p>
     *  使用指定的模式和区域设置创建格式化程序
     * <p>
     *  此方法将基于类文档中所述的简单<a href=\"#patterns\">字母和符号</a>模式创建格式化程序。
     * 例如,{@code d MMM uuuu}将格式化2011-12- 03 as'3 Dec 2011'。
     * <p>
     * 格式化程序将使用指定的区域设置这可以使用{@link DateTimeFormatter#withLocale(Locale)}在返回的格式化程序
     * <p>
     *  返回的格式化程序没有重写年表或区域它使用{@link ResolverStyle#SMART SMART}解析器样式
     * 
     * 
     * @param pattern  the pattern to use, not null
     * @param locale  the locale to use, not null
     * @return the formatter based on the pattern, not null
     * @throws IllegalArgumentException if the pattern is invalid
     * @see DateTimeFormatterBuilder#appendPattern(String)
     */
    public static DateTimeFormatter ofPattern(String pattern, Locale locale) {
        return new DateTimeFormatterBuilder().appendPattern(pattern).toFormatter(locale);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a locale specific date format for the ISO chronology.
     * <p>
     * This returns a formatter that will format or parse a date.
     * The exact format pattern used varies by locale.
     * <p>
     * The locale is determined from the formatter. The formatter returned directly by
     * this method will use the {@link Locale#getDefault(Locale.Category) default FORMAT locale}.
     * The locale can be controlled using {@link DateTimeFormatter#withLocale(Locale) withLocale(Locale)}
     * on the result of this method.
     * <p>
     * Note that the localized pattern is looked up lazily.
     * This {@code DateTimeFormatter} holds the style required and the locale,
     * looking up the pattern required on demand.
     * <p>
     * The returned formatter has a chronology of ISO set to ensure dates in
     * other calendar systems are correctly converted.
     * It has no override zone and uses the {@link ResolverStyle#SMART SMART} resolver style.
     *
     * <p>
     *  返回ISO年表的特定于语言环境的日期格式
     * <p>
     *  这将返回格式化程序,格式化或解析日期使用的确切格式模式因语言环境而异
     * <p>
     *  语言环境由格式化程序确定格式化程序直接返回此方法将使用{@link Locale#getDefault(LocaleCategory)default FORMAT locale}可以使用{@link DateTimeFormatter#withLocale(Locale)withLocale(Locale)}
     * 来控制语言环境}对这种方法的结果。
     * <p>
     * 请注意,本地化模式是懒洋洋地查找此{@code DateTimeFormatter}保存所需的样式和区域设置,查找需要的模式
     * <p>
     *  返回的格式化程序具有ISO设置的年表,以确保其他日历系统中的日期正确转换它没有覆盖区域并使用{@link ResolverStyle#SMART SMART}解析器样式
     * 
     * 
     * @param dateStyle  the formatter style to obtain, not null
     * @return the date formatter, not null
     */
    public static DateTimeFormatter ofLocalizedDate(FormatStyle dateStyle) {
        Objects.requireNonNull(dateStyle, "dateStyle");
        return new DateTimeFormatterBuilder().appendLocalized(dateStyle, null)
                .toFormatter(ResolverStyle.SMART, IsoChronology.INSTANCE);
    }

    /**
     * Returns a locale specific time format for the ISO chronology.
     * <p>
     * This returns a formatter that will format or parse a time.
     * The exact format pattern used varies by locale.
     * <p>
     * The locale is determined from the formatter. The formatter returned directly by
     * this method will use the {@link Locale#getDefault(Locale.Category) default FORMAT locale}.
     * The locale can be controlled using {@link DateTimeFormatter#withLocale(Locale) withLocale(Locale)}
     * on the result of this method.
     * <p>
     * Note that the localized pattern is looked up lazily.
     * This {@code DateTimeFormatter} holds the style required and the locale,
     * looking up the pattern required on demand.
     * <p>
     * The returned formatter has a chronology of ISO set to ensure dates in
     * other calendar systems are correctly converted.
     * It has no override zone and uses the {@link ResolverStyle#SMART SMART} resolver style.
     *
     * <p>
     *  返回ISO年表的特定于语言环境的时间格式
     * <p>
     *  这将返回一个格式化程序,将格式化或解析时间使用的确切的格式模式因语言环境而异
     * <p>
     * 语言环境由格式化程序确定格式化程序直接返回此方法将使用{@link Locale#getDefault(LocaleCategory)default FORMAT locale}可以使用{@link DateTimeFormatter#withLocale(Locale)withLocale(Locale)}
     * 来控制语言环境}对这种方法的结果。
     * <p>
     *  请注意,本地化模式是懒洋洋地查找此{@code DateTimeFormatter}保存所需的样式和区域设置,查找需要的模式
     * <p>
     *  返回的格式化程序具有ISO设置的年表,以确保其他日历系统中的日期正确转换它没有覆盖区域并使用{@link ResolverStyle#SMART SMART}解析器样式
     * 
     * 
     * @param timeStyle  the formatter style to obtain, not null
     * @return the time formatter, not null
     */
    public static DateTimeFormatter ofLocalizedTime(FormatStyle timeStyle) {
        Objects.requireNonNull(timeStyle, "timeStyle");
        return new DateTimeFormatterBuilder().appendLocalized(null, timeStyle)
                .toFormatter(ResolverStyle.SMART, IsoChronology.INSTANCE);
    }

    /**
     * Returns a locale specific date-time formatter for the ISO chronology.
     * <p>
     * This returns a formatter that will format or parse a date-time.
     * The exact format pattern used varies by locale.
     * <p>
     * The locale is determined from the formatter. The formatter returned directly by
     * this method will use the {@link Locale#getDefault(Locale.Category) default FORMAT locale}.
     * The locale can be controlled using {@link DateTimeFormatter#withLocale(Locale) withLocale(Locale)}
     * on the result of this method.
     * <p>
     * Note that the localized pattern is looked up lazily.
     * This {@code DateTimeFormatter} holds the style required and the locale,
     * looking up the pattern required on demand.
     * <p>
     * The returned formatter has a chronology of ISO set to ensure dates in
     * other calendar systems are correctly converted.
     * It has no override zone and uses the {@link ResolverStyle#SMART SMART} resolver style.
     *
     * <p>
     *  返回ISO时间表的特定于语言环境的日期时间格式化程序
     * <p>
     * 这将返回格式化程序,格式化或解析日期时间使用的确切格式模式因语言环境而异
     * <p>
     *  语言环境由格式化程序确定格式化程序直接返回此方法将使用{@link Locale#getDefault(LocaleCategory)default FORMAT locale}可以使用{@link DateTimeFormatter#withLocale(Locale)withLocale(Locale)}
     * 来控制语言环境}对这种方法的结果。
     * <p>
     *  请注意,本地化模式是懒洋洋地查找此{@code DateTimeFormatter}保存所需的样式和区域设置,查找需要的模式
     * <p>
     *  返回的格式化程序具有ISO设置的年表,以确保其他日历系统中的日期正确转换它没有覆盖区域并使用{@link ResolverStyle#SMART SMART}解析器样式
     * 
     * 
     * @param dateTimeStyle  the formatter style to obtain, not null
     * @return the date-time formatter, not null
     */
    public static DateTimeFormatter ofLocalizedDateTime(FormatStyle dateTimeStyle) {
        Objects.requireNonNull(dateTimeStyle, "dateTimeStyle");
        return new DateTimeFormatterBuilder().appendLocalized(dateTimeStyle, dateTimeStyle)
                .toFormatter(ResolverStyle.SMART, IsoChronology.INSTANCE);
    }

    /**
     * Returns a locale specific date and time format for the ISO chronology.
     * <p>
     * This returns a formatter that will format or parse a date-time.
     * The exact format pattern used varies by locale.
     * <p>
     * The locale is determined from the formatter. The formatter returned directly by
     * this method will use the {@link Locale#getDefault() default FORMAT locale}.
     * The locale can be controlled using {@link DateTimeFormatter#withLocale(Locale) withLocale(Locale)}
     * on the result of this method.
     * <p>
     * Note that the localized pattern is looked up lazily.
     * This {@code DateTimeFormatter} holds the style required and the locale,
     * looking up the pattern required on demand.
     * <p>
     * The returned formatter has a chronology of ISO set to ensure dates in
     * other calendar systems are correctly converted.
     * It has no override zone and uses the {@link ResolverStyle#SMART SMART} resolver style.
     *
     * <p>
     * 返回ISO年表的特定于语言环境的日期和时间格式
     * <p>
     *  这将返回格式化程序,格式化或解析日期时间使用的确切格式模式因语言环境而异
     * <p>
     *  语言环境是从格式化程序确定的格式化程序直接返回此方法将使用{@link Locale#getDefault()默认FORMAT语言环境}语言环境可以使用{@link DateTimeFormatter#withLocale(Locale)withLocale(Locale)}
     * 控制这个方法的结果。
     * <p>
     *  请注意,本地化模式是懒洋洋地查找此{@code DateTimeFormatter}保存所需的样式和区域设置,查找需要的模式
     * <p>
     * 返回的格式化程序具有ISO设置的年表,以确保其他日历系统中的日期正确转换它没有覆盖区域并使用{@link ResolverStyle#SMART SMART}解析器样式
     * 
     * 
     * @param dateStyle  the date formatter style to obtain, not null
     * @param timeStyle  the time formatter style to obtain, not null
     * @return the date, time or date-time formatter, not null
     */
    public static DateTimeFormatter ofLocalizedDateTime(FormatStyle dateStyle, FormatStyle timeStyle) {
        Objects.requireNonNull(dateStyle, "dateStyle");
        Objects.requireNonNull(timeStyle, "timeStyle");
        return new DateTimeFormatterBuilder().appendLocalized(dateStyle, timeStyle)
                .toFormatter(ResolverStyle.SMART, IsoChronology.INSTANCE);
    }

    //-----------------------------------------------------------------------
    /**
     * The ISO date formatter that formats or parses a date without an
     * offset, such as '2011-12-03'.
     * <p>
     * This returns an immutable formatter capable of formatting and parsing
     * the ISO-8601 extended local date format.
     * The format consists of:
     * <ul>
     * <li>Four digits or more for the {@link ChronoField#YEAR year}.
     * Years in the range 0000 to 9999 will be pre-padded by zero to ensure four digits.
     * Years outside that range will have a prefixed positive or negative symbol.
     * <li>A dash
     * <li>Two digits for the {@link ChronoField#MONTH_OF_YEAR month-of-year}.
     *  This is pre-padded by zero to ensure two digits.
     * <li>A dash
     * <li>Two digits for the {@link ChronoField#DAY_OF_MONTH day-of-month}.
     *  This is pre-padded by zero to ensure two digits.
     * </ul>
     * <p>
     * The returned formatter has a chronology of ISO set to ensure dates in
     * other calendar systems are correctly converted.
     * It has no override zone and uses the {@link ResolverStyle#STRICT STRICT} resolver style.
     * <p>
     *  ISO日期格式化程序,用于格式化或解析无偏移的日期,例如"2011-12-03"
     * <p>
     *  这返回一个不可变的格式化程序,能够格式化和解析ISO-8601扩展本地日期格式该格式包括：
     * <ul>
     * <li> {@link ChronoField#YEAR年}年的四位数或以上将在0000至9999范围内预填零,以确保四位数超出该范围的年份将具有前缀正负符号<li>短划线<li> {@link ChronoField#MONTH_OF_YEAR month-of-year}
     * 的两位数字用零填充,以确保两个数字<li>短划线<li> {@link ChronoField#DAY_OF_MONTH day-of-month}这是用零填充的,以确保两位数字。
     * </ul>
     * <p>
     *  返回的格式化程序具有ISO设置的年表,以确保正确转换其他日历系统中的日期它没有覆盖区域并使用{@link ResolverStyle#STRICT STRICT}解析器样式
     * 
     */
    public static final DateTimeFormatter ISO_LOCAL_DATE;
    static {
        ISO_LOCAL_DATE = new DateTimeFormatterBuilder()
                .appendValue(YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
                .appendLiteral('-')
                .appendValue(MONTH_OF_YEAR, 2)
                .appendLiteral('-')
                .appendValue(DAY_OF_MONTH, 2)
                .toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
    }

    //-----------------------------------------------------------------------
    /**
     * The ISO date formatter that formats or parses a date with an
     * offset, such as '2011-12-03+01:00'.
     * <p>
     * This returns an immutable formatter capable of formatting and parsing
     * the ISO-8601 extended offset date format.
     * The format consists of:
     * <ul>
     * <li>The {@link #ISO_LOCAL_DATE}
     * <li>The {@link ZoneOffset#getId() offset ID}. If the offset has seconds then
     *  they will be handled even though this is not part of the ISO-8601 standard.
     *  Parsing is case insensitive.
     * </ul>
     * <p>
     * The returned formatter has a chronology of ISO set to ensure dates in
     * other calendar systems are correctly converted.
     * It has no override zone and uses the {@link ResolverStyle#STRICT STRICT} resolver style.
     * <p>
     *  ISO日期格式化程序,用于格式化或解析具有偏移量的日期,例如'2011-12-03 + 01：00'
     * <p>
     * 这返回一个不可变的格式化程序,能够格式化和解析ISO-8601扩展偏移量日期格式该格式包括：
     * <ul>
     *  <li> {@link #ISO_LOCAL_DATE} <li> {@link ZoneOffset#getId()偏移ID}如果偏移具有秒,那么即使它不是ISO-8601标准的一部分,它们也将被处
     * 理。
     * 解析是case不敏感。
     * </ul>
     * <p>
     *  返回的格式化程序具有ISO设置的年表,以确保正确转换其他日历系统中的日期它没有覆盖区域并使用{@link ResolverStyle#STRICT STRICT}解析器样式
     * 
     */
    public static final DateTimeFormatter ISO_OFFSET_DATE;
    static {
        ISO_OFFSET_DATE = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(ISO_LOCAL_DATE)
                .appendOffsetId()
                .toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
    }

    //-----------------------------------------------------------------------
    /**
     * The ISO date formatter that formats or parses a date with the
     * offset if available, such as '2011-12-03' or '2011-12-03+01:00'.
     * <p>
     * This returns an immutable formatter capable of formatting and parsing
     * the ISO-8601 extended date format.
     * The format consists of:
     * <ul>
     * <li>The {@link #ISO_LOCAL_DATE}
     * <li>If the offset is not available then the format is complete.
     * <li>The {@link ZoneOffset#getId() offset ID}. If the offset has seconds then
     *  they will be handled even though this is not part of the ISO-8601 standard.
     *  Parsing is case insensitive.
     * </ul>
     * <p>
     * As this formatter has an optional element, it may be necessary to parse using
     * {@link DateTimeFormatter#parseBest}.
     * <p>
     * The returned formatter has a chronology of ISO set to ensure dates in
     * other calendar systems are correctly converted.
     * It has no override zone and uses the {@link ResolverStyle#STRICT STRICT} resolver style.
     * <p>
     *  ISO日期格式化程序,用于格式化或解析具有偏移量的日期(如果有),例如"2011-12-03"或"2011-12-03 + 01：00"
     * <p>
     * 这返回一个不可变的格式化程序,能够格式化和解析ISO-8601扩展日期格式该格式包括：
     * <ul>
     *  <li> {@link #ISO_LOCAL_DATE} <li>如果偏移量不可用,则格式完成<li> {@link ZoneOffset#getId()偏移ID}如果偏移量有秒,那么它们将被处理尽管
     * 这不是ISO-8601标准的一部分解析是不区分大小写的。
     * </ul>
     * <p>
     *  因为这个格式化器有一个可选的元素,可能需要使用{@link DateTimeFormatter#parseBest}
     * <p>
     *  返回的格式化程序具有ISO设置的年表,以确保正确转换其他日历系统中的日期它没有覆盖区域并使用{@link ResolverStyle#STRICT STRICT}解析器样式
     * 
     */
    public static final DateTimeFormatter ISO_DATE;
    static {
        ISO_DATE = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(ISO_LOCAL_DATE)
                .optionalStart()
                .appendOffsetId()
                .toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
    }

    //-----------------------------------------------------------------------
    /**
     * The ISO time formatter that formats or parses a time without an
     * offset, such as '10:15' or '10:15:30'.
     * <p>
     * This returns an immutable formatter capable of formatting and parsing
     * the ISO-8601 extended local time format.
     * The format consists of:
     * <ul>
     * <li>Two digits for the {@link ChronoField#HOUR_OF_DAY hour-of-day}.
     *  This is pre-padded by zero to ensure two digits.
     * <li>A colon
     * <li>Two digits for the {@link ChronoField#MINUTE_OF_HOUR minute-of-hour}.
     *  This is pre-padded by zero to ensure two digits.
     * <li>If the second-of-minute is not available then the format is complete.
     * <li>A colon
     * <li>Two digits for the {@link ChronoField#SECOND_OF_MINUTE second-of-minute}.
     *  This is pre-padded by zero to ensure two digits.
     * <li>If the nano-of-second is zero or not available then the format is complete.
     * <li>A decimal point
     * <li>One to nine digits for the {@link ChronoField#NANO_OF_SECOND nano-of-second}.
     *  As many digits will be output as required.
     * </ul>
     * <p>
     * The returned formatter has no override chronology or zone.
     * It uses the {@link ResolverStyle#STRICT STRICT} resolver style.
     * <p>
     * 格式化或解析没有偏移量的时间的ISO时间格式器,例如'10：15'或'10：15：30'
     * <p>
     *  这返回一个不可变格式化器,能够格式化和解析ISO-8601扩展本地时间格式该格式包括：
     * <ul>
     * <li> {@link ChronoField#HOUR_OF_DAY hour-of-day}的两位数字由零填充,以确保两个数字<li>冒号<li> {@link ChronoField#MINUTE_OF_HOUR分钟 - <li>如果第二分钟不可用,则格式完成<li>结束<li> {@link ChronoField# SECOND_OF_MINUTE秒钟}
     * 这是用零填充,以确保两个数字<li>如果纳秒为零或不可用,则格式完成<li>小数点<li>一到九{@link ChronoField#NANO_OF_SECOND nano-of-second}的数字将
     * 根据需要输出多个数字。
     * </ul>
     * <p>
     * 返回的格式化程序没有重写年表或区域它使用{@link ResolverStyle#STRICT STRICT}解析器样式
     * 
     */
    public static final DateTimeFormatter ISO_LOCAL_TIME;
    static {
        ISO_LOCAL_TIME = new DateTimeFormatterBuilder()
                .appendValue(HOUR_OF_DAY, 2)
                .appendLiteral(':')
                .appendValue(MINUTE_OF_HOUR, 2)
                .optionalStart()
                .appendLiteral(':')
                .appendValue(SECOND_OF_MINUTE, 2)
                .optionalStart()
                .appendFraction(NANO_OF_SECOND, 0, 9, true)
                .toFormatter(ResolverStyle.STRICT, null);
    }

    //-----------------------------------------------------------------------
    /**
     * The ISO time formatter that formats or parses a time with an
     * offset, such as '10:15+01:00' or '10:15:30+01:00'.
     * <p>
     * This returns an immutable formatter capable of formatting and parsing
     * the ISO-8601 extended offset time format.
     * The format consists of:
     * <ul>
     * <li>The {@link #ISO_LOCAL_TIME}
     * <li>The {@link ZoneOffset#getId() offset ID}. If the offset has seconds then
     *  they will be handled even though this is not part of the ISO-8601 standard.
     *  Parsing is case insensitive.
     * </ul>
     * <p>
     * The returned formatter has no override chronology or zone.
     * It uses the {@link ResolverStyle#STRICT STRICT} resolver style.
     * <p>
     *  ISO时间格式化器格式化或解析具有偏移量的时间,例如'10：15 + 01：00'或'10：15：30 + 01：00'
     * <p>
     *  这返回一个不可变格式化器,能够格式化和解析ISO-8601扩展偏移时间格式该格式包括：
     * <ul>
     *  <li> {@link #ISO_LOCAL_TIME} <li> {@link ZoneOffset#getId()偏移ID}如果偏移具有秒,那么即使它不是ISO-8601标准的一部分,它们也会被处
     * 理。
     * 解析是case不敏感。
     * </ul>
     * <p>
     *  返回的格式化程序没有重写年表或区域它使用{@link ResolverStyle#STRICT STRICT}解析器样式
     * 
     */
    public static final DateTimeFormatter ISO_OFFSET_TIME;
    static {
        ISO_OFFSET_TIME = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(ISO_LOCAL_TIME)
                .appendOffsetId()
                .toFormatter(ResolverStyle.STRICT, null);
    }

    //-----------------------------------------------------------------------
    /**
     * The ISO time formatter that formats or parses a time, with the
     * offset if available, such as '10:15', '10:15:30' or '10:15:30+01:00'.
     * <p>
     * This returns an immutable formatter capable of formatting and parsing
     * the ISO-8601 extended offset time format.
     * The format consists of:
     * <ul>
     * <li>The {@link #ISO_LOCAL_TIME}
     * <li>If the offset is not available then the format is complete.
     * <li>The {@link ZoneOffset#getId() offset ID}. If the offset has seconds then
     *  they will be handled even though this is not part of the ISO-8601 standard.
     *  Parsing is case insensitive.
     * </ul>
     * <p>
     * As this formatter has an optional element, it may be necessary to parse using
     * {@link DateTimeFormatter#parseBest}.
     * <p>
     * The returned formatter has no override chronology or zone.
     * It uses the {@link ResolverStyle#STRICT STRICT} resolver style.
     * <p>
     * 格式化或解析时间的ISO时间格式器,其偏移量(如果可用),例如'10：15','10：15：30'或'10：15：30 + 01：00'
     * <p>
     *  这返回一个不可变格式化器,能够格式化和解析ISO-8601扩展偏移时间格式该格式包括：
     * <ul>
     *  <li> {@link #ISO_LOCAL_TIME} <li>如果偏移量不可用,则格式完成<li> {@link ZoneOffset#getId()偏移ID}如果偏移量有秒,那么它们将被处理尽管
     * 这不是ISO-8601标准的一部分解析是不区分大小写的。
     * </ul>
     * <p>
     *  因为这个格式化器有一个可选的元素,可能需要使用{@link DateTimeFormatter#parseBest}
     * <p>
     *  返回的格式化程序没有重写年表或区域它使用{@link ResolverStyle#STRICT STRICT}解析器样式
     * 
     */
    public static final DateTimeFormatter ISO_TIME;
    static {
        ISO_TIME = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(ISO_LOCAL_TIME)
                .optionalStart()
                .appendOffsetId()
                .toFormatter(ResolverStyle.STRICT, null);
    }

    //-----------------------------------------------------------------------
    /**
     * The ISO date-time formatter that formats or parses a date-time without
     * an offset, such as '2011-12-03T10:15:30'.
     * <p>
     * This returns an immutable formatter capable of formatting and parsing
     * the ISO-8601 extended offset date-time format.
     * The format consists of:
     * <ul>
     * <li>The {@link #ISO_LOCAL_DATE}
     * <li>The letter 'T'. Parsing is case insensitive.
     * <li>The {@link #ISO_LOCAL_TIME}
     * </ul>
     * <p>
     * The returned formatter has a chronology of ISO set to ensure dates in
     * other calendar systems are correctly converted.
     * It has no override zone and uses the {@link ResolverStyle#STRICT STRICT} resolver style.
     * <p>
     * ISO日期时间格式化程序,用于格式化或解析无偏移的日期时间,例如'2011-12-03T10：15：30'
     * <p>
     *  这返回一个不可变格式化器,能够格式化和解析ISO-8601扩展偏移日期时间格式该格式包括：
     * <ul>
     *  <li> {@link #ISO_LOCAL_DATE} <li>字母'T'解析不区分大小写<li> {@link #ISO_LOCAL_TIME}
     * </ul>
     * <p>
     *  返回的格式化程序具有ISO设置的年表,以确保正确转换其他日历系统中的日期它没有覆盖区域并使用{@link ResolverStyle#STRICT STRICT}解析器样式
     * 
     */
    public static final DateTimeFormatter ISO_LOCAL_DATE_TIME;
    static {
        ISO_LOCAL_DATE_TIME = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(ISO_LOCAL_DATE)
                .appendLiteral('T')
                .append(ISO_LOCAL_TIME)
                .toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
    }

    //-----------------------------------------------------------------------
    /**
     * The ISO date-time formatter that formats or parses a date-time with an
     * offset, such as '2011-12-03T10:15:30+01:00'.
     * <p>
     * This returns an immutable formatter capable of formatting and parsing
     * the ISO-8601 extended offset date-time format.
     * The format consists of:
     * <ul>
     * <li>The {@link #ISO_LOCAL_DATE_TIME}
     * <li>The {@link ZoneOffset#getId() offset ID}. If the offset has seconds then
     *  they will be handled even though this is not part of the ISO-8601 standard.
     *  Parsing is case insensitive.
     * </ul>
     * <p>
     * The returned formatter has a chronology of ISO set to ensure dates in
     * other calendar systems are correctly converted.
     * It has no override zone and uses the {@link ResolverStyle#STRICT STRICT} resolver style.
     * <p>
     *  格式化或解析具有偏移量的日期时间的ISO日期时间格式化器,例如'2011-12-03T10：15：30 + 01：00'
     * <p>
     * 这返回一个不可变格式化器,能够格式化和解析ISO-8601扩展偏移日期时间格式该格式包括：
     * <ul>
     *  <li> {@link #ISO_LOCAL_DATE_TIME} <li> {@link ZoneOffset#getId()偏移ID}如果偏移具有秒,那么即使它不是ISO-8601标准的一部分,它
     * 们也将被处理。
     * 解析是case不敏感。
     * </ul>
     * <p>
     *  返回的格式化程序具有ISO设置的年表,以确保正确转换其他日历系统中的日期它没有覆盖区域并使用{@link ResolverStyle#STRICT STRICT}解析器样式
     * 
     */
    public static final DateTimeFormatter ISO_OFFSET_DATE_TIME;
    static {
        ISO_OFFSET_DATE_TIME = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(ISO_LOCAL_DATE_TIME)
                .appendOffsetId()
                .toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
    }

    //-----------------------------------------------------------------------
    /**
     * The ISO-like date-time formatter that formats or parses a date-time with
     * offset and zone, such as '2011-12-03T10:15:30+01:00[Europe/Paris]'.
     * <p>
     * This returns an immutable formatter capable of formatting and parsing
     * a format that extends the ISO-8601 extended offset date-time format
     * to add the time-zone.
     * The section in square brackets is not part of the ISO-8601 standard.
     * The format consists of:
     * <ul>
     * <li>The {@link #ISO_OFFSET_DATE_TIME}
     * <li>If the zone ID is not available or is a {@code ZoneOffset} then the format is complete.
     * <li>An open square bracket '['.
     * <li>The {@link ZoneId#getId() zone ID}. This is not part of the ISO-8601 standard.
     *  Parsing is case sensitive.
     * <li>A close square bracket ']'.
     * </ul>
     * <p>
     * The returned formatter has a chronology of ISO set to ensure dates in
     * other calendar systems are correctly converted.
     * It has no override zone and uses the {@link ResolverStyle#STRICT STRICT} resolver style.
     * <p>
     *  格式化或解析具有偏移和区域的日期时间的ISO类似日期时间格式化程序,例如'2011-12-03T10：15：30 + 01：00 [Europe / Paris]'
     * <p>
     * 这返回一个不可变格式化器,能够格式化和解析扩展ISO-8601扩展偏移日期时间格式的格式以添加时区方括号中的部分不是ISO-8601标准的一部分格式包括：
     * <ul>
     *  <li> {@link #ISO_OFFSET_DATE_TIME} <li>如果区域ID不可用或为{@code ZoneOffset},则表示格式已完成<li>一个空的方括号'['<li> {@link ZoneId#getId()zone ID}
     * 这不是ISO-8601标准的一部分解析是区分大小写的<li>一个接近的方括号']'。
     * </ul>
     * <p>
     *  返回的格式化程序具有ISO设置的年表,以确保正确转换其他日历系统中的日期它没有覆盖区域并使用{@link ResolverStyle#STRICT STRICT}解析器样式
     * 
     */
    public static final DateTimeFormatter ISO_ZONED_DATE_TIME;
    static {
        ISO_ZONED_DATE_TIME = new DateTimeFormatterBuilder()
                .append(ISO_OFFSET_DATE_TIME)
                .optionalStart()
                .appendLiteral('[')
                .parseCaseSensitive()
                .appendZoneRegionId()
                .appendLiteral(']')
                .toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
    }

    //-----------------------------------------------------------------------
    /**
     * The ISO-like date-time formatter that formats or parses a date-time with
     * the offset and zone if available, such as '2011-12-03T10:15:30',
     * '2011-12-03T10:15:30+01:00' or '2011-12-03T10:15:30+01:00[Europe/Paris]'.
     * <p>
     * This returns an immutable formatter capable of formatting and parsing
     * the ISO-8601 extended local or offset date-time format, as well as the
     * extended non-ISO form specifying the time-zone.
     * The format consists of:
     * <ul>
     * <li>The {@link #ISO_LOCAL_DATE_TIME}
     * <li>If the offset is not available to format or parse then the format is complete.
     * <li>The {@link ZoneOffset#getId() offset ID}. If the offset has seconds then
     *  they will be handled even though this is not part of the ISO-8601 standard.
     * <li>If the zone ID is not available or is a {@code ZoneOffset} then the format is complete.
     * <li>An open square bracket '['.
     * <li>The {@link ZoneId#getId() zone ID}. This is not part of the ISO-8601 standard.
     *  Parsing is case sensitive.
     * <li>A close square bracket ']'.
     * </ul>
     * <p>
     * As this formatter has an optional element, it may be necessary to parse using
     * {@link DateTimeFormatter#parseBest}.
     * <p>
     * The returned formatter has a chronology of ISO set to ensure dates in
     * other calendar systems are correctly converted.
     * It has no override zone and uses the {@link ResolverStyle#STRICT STRICT} resolver style.
     * <p>
     * 格式化或解析具有偏移和区域的日期时间(如果可用)的类ISO日期时间格式化器,例如'2011-12-03T10：15：30','2011-12-03T10：15：30 + 01 ：00'or'2011-1
     * 2-03T10：15：30 + 01：00 [欧洲/巴黎]。
     * <p>
     *  这返回一个不变的格式化程序,能够格式化和解析ISO-8601扩展本地或偏移日期时间格式,以及指定时区的扩展非ISO格式。格式包括：
     * <ul>
     * <li> {@link #ISO_LOCAL_DATE_TIME} <li>如果偏移量无法格式化或解析,则格式完成<li> {@link ZoneOffset#getId()偏移ID}如果偏移量有秒,即
     * 使这不是ISO-8601标准的一部分,也会被处理<li>如果区域ID不可用或者是{@code ZoneOffset},则格式完成<li>一个空的方括号'['<li > {@link ZoneId#getId()zone ID}
     * 这不是ISO-8601标准的一部分解析是区分大小写的<li>一个接近的方括号']'。
     * </ul>
     * <p>
     *  因为这个格式化器有一个可选的元素,可能需要使用{@link DateTimeFormatter#parseBest}
     * <p>
     * 返回的格式化程序具有ISO设置的年表,以确保正确转换其他日历系统中的日期它没有覆盖区域并使用{@link ResolverStyle#STRICT STRICT}解析器样式
     * 
     */
    public static final DateTimeFormatter ISO_DATE_TIME;
    static {
        ISO_DATE_TIME = new DateTimeFormatterBuilder()
                .append(ISO_LOCAL_DATE_TIME)
                .optionalStart()
                .appendOffsetId()
                .optionalStart()
                .appendLiteral('[')
                .parseCaseSensitive()
                .appendZoneRegionId()
                .appendLiteral(']')
                .toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
    }

    //-----------------------------------------------------------------------
    /**
     * The ISO date formatter that formats or parses the ordinal date
     * without an offset, such as '2012-337'.
     * <p>
     * This returns an immutable formatter capable of formatting and parsing
     * the ISO-8601 extended ordinal date format.
     * The format consists of:
     * <ul>
     * <li>Four digits or more for the {@link ChronoField#YEAR year}.
     * Years in the range 0000 to 9999 will be pre-padded by zero to ensure four digits.
     * Years outside that range will have a prefixed positive or negative symbol.
     * <li>A dash
     * <li>Three digits for the {@link ChronoField#DAY_OF_YEAR day-of-year}.
     *  This is pre-padded by zero to ensure three digits.
     * <li>If the offset is not available to format or parse then the format is complete.
     * <li>The {@link ZoneOffset#getId() offset ID}. If the offset has seconds then
     *  they will be handled even though this is not part of the ISO-8601 standard.
     *  Parsing is case insensitive.
     * </ul>
     * <p>
     * As this formatter has an optional element, it may be necessary to parse using
     * {@link DateTimeFormatter#parseBest}.
     * <p>
     * The returned formatter has a chronology of ISO set to ensure dates in
     * other calendar systems are correctly converted.
     * It has no override zone and uses the {@link ResolverStyle#STRICT STRICT} resolver style.
     * <p>
     *  ISO日期格式化程序,用于格式化或解析无偏移的序数日期,例如'2012-337'
     * <p>
     *  这返回一个不可变的格式化程序,能够格式化和解析ISO-8601扩展序数日期格式该格式包括：
     * <ul>
     * <li> {@link ChronoField#YEAR年}年的四位数或以上将在0000至9999范围内预填零,以确保四位数超出该范围的年份将具有前缀正负符号<li>短划线<li> {@link ChronoField#DAY_OF_YEAR年的三位数字}
     * 这是用零填充的,以确保三位数字<li>如果偏移量不可用于格式化或解析,则格式完成<li> {@link ZoneOffset#getId()偏移ID}如果偏移具有秒,那么即使它不是ISO-8601标准的
     * 一部分,它们也将被处理。
     * 解析是不区分大小写的。
     * </ul>
     * <p>
     *  因为这个格式化器有一个可选的元素,可能需要使用{@link DateTimeFormatter#parseBest}
     * <p>
     * 返回的格式化程序具有ISO设置的年表,以确保正确转换其他日历系统中的日期它没有覆盖区域并使用{@link ResolverStyle#STRICT STRICT}解析器样式
     * 
     */
    public static final DateTimeFormatter ISO_ORDINAL_DATE;
    static {
        ISO_ORDINAL_DATE = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendValue(YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
                .appendLiteral('-')
                .appendValue(DAY_OF_YEAR, 3)
                .optionalStart()
                .appendOffsetId()
                .toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
    }

    //-----------------------------------------------------------------------
    /**
     * The ISO date formatter that formats or parses the week-based date
     * without an offset, such as '2012-W48-6'.
     * <p>
     * This returns an immutable formatter capable of formatting and parsing
     * the ISO-8601 extended week-based date format.
     * The format consists of:
     * <ul>
     * <li>Four digits or more for the {@link IsoFields#WEEK_BASED_YEAR week-based-year}.
     * Years in the range 0000 to 9999 will be pre-padded by zero to ensure four digits.
     * Years outside that range will have a prefixed positive or negative symbol.
     * <li>A dash
     * <li>The letter 'W'. Parsing is case insensitive.
     * <li>Two digits for the {@link IsoFields#WEEK_OF_WEEK_BASED_YEAR week-of-week-based-year}.
     *  This is pre-padded by zero to ensure three digits.
     * <li>A dash
     * <li>One digit for the {@link ChronoField#DAY_OF_WEEK day-of-week}.
     *  The value run from Monday (1) to Sunday (7).
     * <li>If the offset is not available to format or parse then the format is complete.
     * <li>The {@link ZoneOffset#getId() offset ID}. If the offset has seconds then
     *  they will be handled even though this is not part of the ISO-8601 standard.
     *  Parsing is case insensitive.
     * </ul>
     * <p>
     * As this formatter has an optional element, it may be necessary to parse using
     * {@link DateTimeFormatter#parseBest}.
     * <p>
     * The returned formatter has a chronology of ISO set to ensure dates in
     * other calendar systems are correctly converted.
     * It has no override zone and uses the {@link ResolverStyle#STRICT STRICT} resolver style.
     * <p>
     *  ISO日期格式化程序,用于格式化或解析基于周的日期,而不带偏移量,例如"2012-W48-6"
     * <p>
     *  这返回一个不变的格式化程序,能够格式化和解析ISO-8601基于周的日期格式。格式包括：
     * <ul>
     * <li> {@link IsoFields#WEEK_BASED_YEAR week-based-year}年的0000到9999范围内的四位数或更多数字将被预填零,以确保四位数超出该范围的年份将具有前
     * 缀正数或负数符号<li>短划线<li>字母'W'解析是不区分大小写的<li> {@link IsoFields#WEEK_OF_WEEK_BASED_YEAR周基于一年的两位数字}这是用零填充的,三个数
     * 字<li>短划线<li> {@link ChronoField#DAY_OF_WEEK星期几)的数字从星期一(1)到星期日(7)的值<li>如果偏移量不可用于格式或解析,然后格式完成<li> {@link ZoneOffset#getId()偏移ID}
     * 如果偏移具有秒,则它们将被处理,即使这不是ISO-8601标准的一部分解析不区分大小写。
     * </ul>
     * <p>
     * 因为这个格式化器有一个可选的元素,可能需要使用{@link DateTimeFormatter#parseBest}
     * <p>
     *  返回的格式化程序具有ISO设置的年表,以确保正确转换其他日历系统中的日期它没有覆盖区域并使用{@link ResolverStyle#STRICT STRICT}解析器样式
     * 
     */
    public static final DateTimeFormatter ISO_WEEK_DATE;
    static {
        ISO_WEEK_DATE = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendValue(IsoFields.WEEK_BASED_YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
                .appendLiteral("-W")
                .appendValue(IsoFields.WEEK_OF_WEEK_BASED_YEAR, 2)
                .appendLiteral('-')
                .appendValue(DAY_OF_WEEK, 1)
                .optionalStart()
                .appendOffsetId()
                .toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
    }

    //-----------------------------------------------------------------------
    /**
     * The ISO instant formatter that formats or parses an instant in UTC,
     * such as '2011-12-03T10:15:30Z'.
     * <p>
     * This returns an immutable formatter capable of formatting and parsing
     * the ISO-8601 instant format.
     * When formatting, the second-of-minute is always output.
     * The nano-of-second outputs zero, three, six or nine digits digits as necessary.
     * When parsing, time to at least the seconds field is required.
     * Fractional seconds from zero to nine are parsed.
     * The localized decimal style is not used.
     * <p>
     * This is a special case formatter intended to allow a human readable form
     * of an {@link java.time.Instant}. The {@code Instant} class is designed to
     * only represent a point in time and internally stores a value in nanoseconds
     * from a fixed epoch of 1970-01-01Z. As such, an {@code Instant} cannot be
     * formatted as a date or time without providing some form of time-zone.
     * This formatter allows the {@code Instant} to be formatted, by providing
     * a suitable conversion using {@code ZoneOffset.UTC}.
     * <p>
     * The format consists of:
     * <ul>
     * <li>The {@link #ISO_OFFSET_DATE_TIME} where the instant is converted from
     *  {@link ChronoField#INSTANT_SECONDS} and {@link ChronoField#NANO_OF_SECOND}
     *  using the {@code UTC} offset. Parsing is case insensitive.
     * </ul>
     * <p>
     * The returned formatter has no override chronology or zone.
     * It uses the {@link ResolverStyle#STRICT STRICT} resolver style.
     * <p>
     *  格式化或解析UTC时间的ISO即时格式器,例如'2011-12-03T10：15：30Z'
     * <p>
     * 这返回一个能够格式化和解析ISO-8601即时格式的不可变格式化格式化时,总是输出秒数。
     * 纳秒输出根据需要输出零,三,六或九位数字当解析时,时间至少需要秒字段从零到九的分数秒被解析不使用本地化的十进制样式。
     * <p>
     * 这是一个特殊的格式化程序,旨在允许{@link javatimeInstant}的人类可读形式{@code Instant}类被设计为只表示一个时间点,并且内部存储一个值,以毫微秒为单位, 01-01Z
     * 因此,{@code Instant}不能设置为日期或时间,而不提供某种形式的时区。
     * 此格式化程序允许{@code Instant}格式化,方法是使用{@code ZoneOffsetUTC}。
     * <p>
     *  格式包括：
     * <ul>
     *  <li> {@link #ISO_OFFSET_DATE_TIME},其中使用{@code UTC}偏移从{@link ChronoField#INSTANT_SECONDS}和{@link ChronoField#NANO_OF_SECOND}
     * 转换即时即时解析不区分大小写。
     * </ul>
     * <p>
     * 返回的格式化程序没有重写年表或区域它使用{@link ResolverStyle#STRICT STRICT}解析器样式
     * 
     */
    public static final DateTimeFormatter ISO_INSTANT;
    static {
        ISO_INSTANT = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendInstant()
                .toFormatter(ResolverStyle.STRICT, null);
    }

    //-----------------------------------------------------------------------
    /**
     * The ISO date formatter that formats or parses a date without an
     * offset, such as '20111203'.
     * <p>
     * This returns an immutable formatter capable of formatting and parsing
     * the ISO-8601 basic local date format.
     * The format consists of:
     * <ul>
     * <li>Four digits for the {@link ChronoField#YEAR year}.
     *  Only years in the range 0000 to 9999 are supported.
     * <li>Two digits for the {@link ChronoField#MONTH_OF_YEAR month-of-year}.
     *  This is pre-padded by zero to ensure two digits.
     * <li>Two digits for the {@link ChronoField#DAY_OF_MONTH day-of-month}.
     *  This is pre-padded by zero to ensure two digits.
     * <li>If the offset is not available to format or parse then the format is complete.
     * <li>The {@link ZoneOffset#getId() offset ID} without colons. If the offset has
     *  seconds then they will be handled even though this is not part of the ISO-8601 standard.
     *  Parsing is case insensitive.
     * </ul>
     * <p>
     * As this formatter has an optional element, it may be necessary to parse using
     * {@link DateTimeFormatter#parseBest}.
     * <p>
     * The returned formatter has a chronology of ISO set to ensure dates in
     * other calendar systems are correctly converted.
     * It has no override zone and uses the {@link ResolverStyle#STRICT STRICT} resolver style.
     * <p>
     *  格式化或解析无偏移日期的ISO日期格式器,如"20111203"
     * <p>
     *  这返回一个不可变的格式化程序,能够格式化和解析ISO-8601基本本地日期格式该格式包括：
     * <ul>
     * <li>支持{@link ChronoField#YEAR年}的四位数字<li>支持范围在0000到9999之间的年份<li> {@link ChronoField#MONTH_OF_YEAR month-of-year}
     * 的两位数字这是预填充的零以确保两个数字<li> {@link ChronoField#DAY_OF_MONTH日期的两位数字}这是用零填充的,以确保两个数字<li>如果偏移量不可用于格式化或解析,则格式
     * 完成<li>不带冒号的{@link ZoneOffset#getId()偏移ID}如果偏移具有秒,那么即使它不是ISO-8601标准的一部分,它们也将被处理。
     * 解析是不区分大小写的。
     * </ul>
     * <p>
     *  因为这个格式化器有一个可选的元素,可能需要使用{@link DateTimeFormatter#parseBest}
     * <p>
     * 返回的格式化程序具有ISO设置的年表,以确保正确转换其他日历系统中的日期它没有覆盖区域并使用{@link ResolverStyle#STRICT STRICT}解析器样式
     * 
     */
    public static final DateTimeFormatter BASIC_ISO_DATE;
    static {
        BASIC_ISO_DATE = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendValue(YEAR, 4)
                .appendValue(MONTH_OF_YEAR, 2)
                .appendValue(DAY_OF_MONTH, 2)
                .optionalStart()
                .appendOffset("+HHMMss", "Z")
                .toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
    }

    //-----------------------------------------------------------------------
    /**
     * The RFC-1123 date-time formatter, such as 'Tue, 3 Jun 2008 11:05:30 GMT'.
     * <p>
     * This returns an immutable formatter capable of formatting and parsing
     * most of the RFC-1123 format.
     * RFC-1123 updates RFC-822 changing the year from two digits to four.
     * This implementation requires a four digit year.
     * This implementation also does not handle North American or military zone
     * names, only 'GMT' and offset amounts.
     * <p>
     * The format consists of:
     * <ul>
     * <li>If the day-of-week is not available to format or parse then jump to day-of-month.
     * <li>Three letter {@link ChronoField#DAY_OF_WEEK day-of-week} in English.
     * <li>A comma
     * <li>A space
     * <li>One or two digits for the {@link ChronoField#DAY_OF_MONTH day-of-month}.
     * <li>A space
     * <li>Three letter {@link ChronoField#MONTH_OF_YEAR month-of-year} in English.
     * <li>A space
     * <li>Four digits for the {@link ChronoField#YEAR year}.
     *  Only years in the range 0000 to 9999 are supported.
     * <li>A space
     * <li>Two digits for the {@link ChronoField#HOUR_OF_DAY hour-of-day}.
     *  This is pre-padded by zero to ensure two digits.
     * <li>A colon
     * <li>Two digits for the {@link ChronoField#MINUTE_OF_HOUR minute-of-hour}.
     *  This is pre-padded by zero to ensure two digits.
     * <li>If the second-of-minute is not available then jump to the next space.
     * <li>A colon
     * <li>Two digits for the {@link ChronoField#SECOND_OF_MINUTE second-of-minute}.
     *  This is pre-padded by zero to ensure two digits.
     * <li>A space
     * <li>The {@link ZoneOffset#getId() offset ID} without colons or seconds.
     *  An offset of zero uses "GMT". North American zone names and military zone names are not handled.
     * </ul>
     * <p>
     * Parsing is case insensitive.
     * <p>
     * The returned formatter has a chronology of ISO set to ensure dates in
     * other calendar systems are correctly converted.
     * It has no override zone and uses the {@link ResolverStyle#SMART SMART} resolver style.
     * <p>
     *  RFC-1123日期时间格式化器,例如'Tue,3 Jun 2008 11:05:30 GMT'
     * <p>
     *  这返回一个不变的格式化,能够格式化和解析大多数RFC-1123格式RFC-1123更新RFC-822将年份从两位数更改为四此实现需要四位数年份此实现也不处理北美或军事区域名称,只有'GMT'和偏移量。
     * <p>
     *  格式包括：
     * <ul>
     * <li>如果星期几无法格式化或剖析,请跳至月份<li>英语中的三个字母{@link ChronoField#DAY_OF_WEEK星期几} <li>逗号< li>一个空格<li> {@link ChronoField#DAY_OF_MONTH个日期}
     * 的一位或两位数字<li>一个空格<li>英语中的三个字母{@link ChronoField#MONTH_OF_YEAR month-of-year} li> A空格<li>支持{@link ChronoField#YEAR年}
     * 的四位数字<li>空格<li> {@link ChronoField#HOUR_OF_DAY小时 - 的两位数字-day}这是用零填充的,以确保两个数字<li>冒号<li> {@link ChronoField#MINUTE_OF_HOUR分钟小时}
     * 的两位数字这是用零填充的,以确保两位数字< li>如果第二分钟不可用,请跳转到下一个空格<li>两个数字表示{@link ChronoField#SECOND_OF_MINUTE秒钟}这是由零填充,以确
     * 保两个数字<li>空格<li> {@link ZoneOffset#getId ()偏移ID}无冒号或秒零偏移量使用"GMT"北美区域名称和军事区域名称不处理。
     * </ul>
     * <p>
     * 解析不区分大小写
     * <p>
     *  返回的格式化程序具有ISO设置的年表,以确保其他日历系统中的日期正确转换它没有覆盖区域并使用{@link ResolverStyle#SMART SMART}解析器样式
     * 
     */
    public static final DateTimeFormatter RFC_1123_DATE_TIME;
    static {
        // manually code maps to ensure correct data always used
        // (locale data can be changed by application code)
        Map<Long, String> dow = new HashMap<>();
        dow.put(1L, "Mon");
        dow.put(2L, "Tue");
        dow.put(3L, "Wed");
        dow.put(4L, "Thu");
        dow.put(5L, "Fri");
        dow.put(6L, "Sat");
        dow.put(7L, "Sun");
        Map<Long, String> moy = new HashMap<>();
        moy.put(1L, "Jan");
        moy.put(2L, "Feb");
        moy.put(3L, "Mar");
        moy.put(4L, "Apr");
        moy.put(5L, "May");
        moy.put(6L, "Jun");
        moy.put(7L, "Jul");
        moy.put(8L, "Aug");
        moy.put(9L, "Sep");
        moy.put(10L, "Oct");
        moy.put(11L, "Nov");
        moy.put(12L, "Dec");
        RFC_1123_DATE_TIME = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .parseLenient()
                .optionalStart()
                .appendText(DAY_OF_WEEK, dow)
                .appendLiteral(", ")
                .optionalEnd()
                .appendValue(DAY_OF_MONTH, 1, 2, SignStyle.NOT_NEGATIVE)
                .appendLiteral(' ')
                .appendText(MONTH_OF_YEAR, moy)
                .appendLiteral(' ')
                .appendValue(YEAR, 4)  // 2 digit year not handled
                .appendLiteral(' ')
                .appendValue(HOUR_OF_DAY, 2)
                .appendLiteral(':')
                .appendValue(MINUTE_OF_HOUR, 2)
                .optionalStart()
                .appendLiteral(':')
                .appendValue(SECOND_OF_MINUTE, 2)
                .optionalEnd()
                .appendLiteral(' ')
                .appendOffset("+HHMM", "GMT")  // should handle UT/Z/EST/EDT/CST/CDT/MST/MDT/PST/MDT
                .toFormatter(ResolverStyle.SMART, IsoChronology.INSTANCE);
    }

    //-----------------------------------------------------------------------
    /**
     * A query that provides access to the excess days that were parsed.
     * <p>
     * This returns a singleton {@linkplain TemporalQuery query} that provides
     * access to additional information from the parse. The query always returns
     * a non-null period, with a zero period returned instead of null.
     * <p>
     * There are two situations where this query may return a non-zero period.
     * <ul>
     * <li>If the {@code ResolverStyle} is {@code LENIENT} and a time is parsed
     *  without a date, then the complete result of the parse consists of a
     *  {@code LocalTime} and an excess {@code Period} in days.
     *
     * <li>If the {@code ResolverStyle} is {@code SMART} and a time is parsed
     *  without a date where the time is 24:00:00, then the complete result of
     *  the parse consists of a {@code LocalTime} of 00:00:00 and an excess
     *  {@code Period} of one day.
     * </ul>
     * <p>
     * In both cases, if a complete {@code ChronoLocalDateTime} or {@code Instant}
     * is parsed, then the excess days are added to the date part.
     * As a result, this query will return a zero period.
     * <p>
     * The {@code SMART} behaviour handles the common "end of day" 24:00 value.
     * Processing in {@code LENIENT} mode also produces the same result:
     * <pre>
     *  Text to parse        Parsed object                         Excess days
     *  "2012-12-03T00:00"   LocalDateTime.of(2012, 12, 3, 0, 0)   ZERO
     *  "2012-12-03T24:00"   LocalDateTime.of(2012, 12, 4, 0, 0)   ZERO
     *  "00:00"              LocalTime.of(0, 0)                    ZERO
     *  "24:00"              LocalTime.of(0, 0)                    Period.ofDays(1)
     * </pre>
     * The query can be used as follows:
     * <pre>
     *  TemporalAccessor parsed = formatter.parse(str);
     *  LocalTime time = parsed.query(LocalTime::from);
     *  Period extraDays = parsed.query(DateTimeFormatter.parsedExcessDays());
     * </pre>
     * <p>
     *  提供对已解析的多余天数的访问权限的查询
     * <p>
     *  这将返回一个单例{@linkplain TemporalQuery查询},该查询提供对来自解析的其他信息的访问。查询始终返回非零期间,返回零周期,而不返回null
     * <p>
     *  有两种情况下,此查询可能返回非零周期
     * <ul>
     *  <li>如果{@code ResolverStyle}是{@code LENIENT},并且没有日期的情况下解析时间,则解析的完整结果包括{@code LocalTime}和多余的{@code Period}
     * 天。
     * 
     * <li>如果{@code ResolverStyle}是{@code SMART},并且解析时间的日期不是24:00:00的日期,则解析的完整结果由{@code LocalTime} of 00:00:
     * 00和超过一天的{@code Period}。
     * </ul>
     * <p>
     *  在这两种情况下,如果解析完整的{@code ChronoLocalDateTime}或{@code Instant},则会将超出天数添加到日期部分。因此,此查询将返回零周期
     * <p>
     *  {@code SMART}行为处理常见的"结束日期"24:00值{@code LENIENT}模式中的处理也会产生相同的结果：
     * <pre>
     * 解析解析对象的文本超过天数"2012-12-03T00：00"LocalDateTimeof(2012,12,3,0,0)ZERO"2012-12-03T24：00"LocalDateTimeof(20
     * 12,12,4,0,0) ZERO"00:00"LocalTimeof(0,0)ZERO"24:00"LocalTimeof(0,0)PeriodofDays(1)。
     * </pre>
     *  查询可以使用如下：
     * <pre>
     *  TemporalAccessor parsed = formatterparse(str); LocalTime time = parsedquery(LocalTime :: from); peri
     * od extraDays = parsedquery(DateTimeFormatterparsedExcessDays());。
     * </pre>
     * 
     * @return a query that provides access to the excess days that were parsed
     */
    public static final TemporalQuery<Period> parsedExcessDays() {
        return PARSED_EXCESS_DAYS;
    }
    private static final TemporalQuery<Period> PARSED_EXCESS_DAYS = t -> {
        if (t instanceof Parsed) {
            return ((Parsed) t).excessDays;
        } else {
            return Period.ZERO;
        }
    };

    /**
     * A query that provides access to whether a leap-second was parsed.
     * <p>
     * This returns a singleton {@linkplain TemporalQuery query} that provides
     * access to additional information from the parse. The query always returns
     * a non-null boolean, true if parsing saw a leap-second, false if not.
     * <p>
     * Instant parsing handles the special "leap second" time of '23:59:60'.
     * Leap seconds occur at '23:59:60' in the UTC time-zone, but at other
     * local times in different time-zones. To avoid this potential ambiguity,
     * the handling of leap-seconds is limited to
     * {@link DateTimeFormatterBuilder#appendInstant()}, as that method
     * always parses the instant with the UTC zone offset.
     * <p>
     * If the time '23:59:60' is received, then a simple conversion is applied,
     * replacing the second-of-minute of 60 with 59. This query can be used
     * on the parse result to determine if the leap-second adjustment was made.
     * The query will return one second of excess if it did adjust to remove
     * the leap-second, and zero if not. Note that applying a leap-second
     * smoothing mechanism, such as UTC-SLS, is the responsibility of the
     * application, as follows:
     * <pre>
     *  TemporalAccessor parsed = formatter.parse(str);
     *  Instant instant = parsed.query(Instant::from);
     *  if (parsed.query(DateTimeFormatter.parsedLeapSecond())) {
     *    // validate leap-second is correct and apply correct smoothing
     *  }
     * </pre>
     * <p>
     *  提供对是否解析闰秒的访问的查询
     * <p>
     * 这返回一个单例{@linkplain TemporalQuery查询},提供从解析访问其他信息查询总是返回一个非空布尔值,如果解析看到一个闰秒,则返回false如果不是
     * <p>
     *  即时解析处理"23：59：60"的特殊"闰秒"时间闰秒发生在UTC时区的"23：59：60",但在不同时区的其他本地时间为避免这种潜在模糊度,闰秒的处理限制为{@link DateTimeFormatterBuilder#appendInstant()}
     * ,因为该方法总是解析与UTC区域偏移。
     * <p>
     * 如果接收到时间"23：59：60",则应用简单的转换,用60替换60的秒。
     * 该查询可以用于解析结果以确定是否进行了闰秒调整如果调整去除闰秒,则查询将返回超出的一秒,如果不是则为零注意,应用闰秒平滑机制(例如UTC-SLS)是应用程序的职责,如下所示：。
     * <pre>
     *  TemporalAccessor parsed = formatterparse(str);即时即时= parsedquery(即时::从); if(parsedquery(DateTimeForma
     * tterparsedLeapSecond())){//验证闰秒是正确的,并应用正确的平滑}。
     * </pre>
     * 
     * @return a query that provides access to whether a leap-second was parsed
     */
    public static final TemporalQuery<Boolean> parsedLeapSecond() {
        return PARSED_LEAP_SECOND;
    }
    private static final TemporalQuery<Boolean> PARSED_LEAP_SECOND = t -> {
        if (t instanceof Parsed) {
            return ((Parsed) t).leapSecond;
        } else {
            return Boolean.FALSE;
        }
    };

    //-----------------------------------------------------------------------
    /**
     * Constructor.
     *
     * <p>
     *  构造函数
     * 
     * 
     * @param printerParser  the printer/parser to use, not null
     * @param locale  the locale to use, not null
     * @param decimalStyle  the DecimalStyle to use, not null
     * @param resolverStyle  the resolver style to use, not null
     * @param resolverFields  the fields to use during resolving, null for all fields
     * @param chrono  the chronology to use, null for no override
     * @param zone  the zone to use, null for no override
     */
    DateTimeFormatter(CompositePrinterParser printerParser,
            Locale locale, DecimalStyle decimalStyle,
            ResolverStyle resolverStyle, Set<TemporalField> resolverFields,
            Chronology chrono, ZoneId zone) {
        this.printerParser = Objects.requireNonNull(printerParser, "printerParser");
        this.resolverFields = resolverFields;
        this.locale = Objects.requireNonNull(locale, "locale");
        this.decimalStyle = Objects.requireNonNull(decimalStyle, "decimalStyle");
        this.resolverStyle = Objects.requireNonNull(resolverStyle, "resolverStyle");
        this.chrono = chrono;
        this.zone = zone;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the locale to be used during formatting.
     * <p>
     * This is used to lookup any part of the formatter needing specific
     * localization, such as the text or localized pattern.
     *
     * <p>
     *  获取要在格式化期间使用的区域设置
     * <p>
     * 这用于查找需要特定定位的格式化程序的任何部分,例如文本或本地化模式
     * 
     * 
     * @return the locale of this formatter, not null
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Returns a copy of this formatter with a new locale.
     * <p>
     * This is used to lookup any part of the formatter needing specific
     * localization, such as the text or localized pattern.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回具有新语言环境的此格式化程序的副本
     * <p>
     *  这用于查找需要特定定位的格式化程序的任何部分,例如文本或本地化模式
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param locale  the new locale, not null
     * @return a formatter based on this formatter with the requested locale, not null
     */
    public DateTimeFormatter withLocale(Locale locale) {
        if (this.locale.equals(locale)) {
            return this;
        }
        return new DateTimeFormatter(printerParser, locale, decimalStyle, resolverStyle, resolverFields, chrono, zone);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the DecimalStyle to be used during formatting.
     *
     * <p>
     *  获取要在格式化期间使用的DecimalStyle
     * 
     * 
     * @return the locale of this formatter, not null
     */
    public DecimalStyle getDecimalStyle() {
        return decimalStyle;
    }

    /**
     * Returns a copy of this formatter with a new DecimalStyle.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  使用新的DecimalStyle返回此格式化程序的副本
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param decimalStyle  the new DecimalStyle, not null
     * @return a formatter based on this formatter with the requested DecimalStyle, not null
     */
    public DateTimeFormatter withDecimalStyle(DecimalStyle decimalStyle) {
        if (this.decimalStyle.equals(decimalStyle)) {
            return this;
        }
        return new DateTimeFormatter(printerParser, locale, decimalStyle, resolverStyle, resolverFields, chrono, zone);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the overriding chronology to be used during formatting.
     * <p>
     * This returns the override chronology, used to convert dates.
     * By default, a formatter has no override chronology, returning null.
     * See {@link #withChronology(Chronology)} for more details on overriding.
     *
     * <p>
     *  获取在格式化期间使用的重写年表
     * <p>
     *  这返回覆盖年表,用于转换日期默认情况下,格式化程序没有覆盖年表,返回null有关覆盖的更多详细信息,请参阅{@link #with Chronology(Chronology)}
     * 
     * 
     * @return the override chronology of this formatter, null if no override
     */
    public Chronology getChronology() {
        return chrono;
    }

    /**
     * Returns a copy of this formatter with a new override chronology.
     * <p>
     * This returns a formatter with similar state to this formatter but
     * with the override chronology set.
     * By default, a formatter has no override chronology, returning null.
     * <p>
     * If an override is added, then any date that is formatted or parsed will be affected.
     * <p>
     * When formatting, if the temporal object contains a date, then it will
     * be converted to a date in the override chronology.
     * Whether the temporal contains a date is determined by querying the
     * {@link ChronoField#EPOCH_DAY EPOCH_DAY} field.
     * Any time or zone will be retained unaltered unless overridden.
     * <p>
     * If the temporal object does not contain a date, but does contain one
     * or more {@code ChronoField} date fields, then a {@code DateTimeException}
     * is thrown. In all other cases, the override chronology is added to the temporal,
     * replacing any previous chronology, but without changing the date/time.
     * <p>
     * When parsing, there are two distinct cases to consider.
     * If a chronology has been parsed directly from the text, perhaps because
     * {@link DateTimeFormatterBuilder#appendChronologyId()} was used, then
     * this override chronology has no effect.
     * If no zone has been parsed, then this override chronology will be used
     * to interpret the {@code ChronoField} values into a date according to the
     * date resolving rules of the chronology.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     * 使用新的覆盖时间顺序返回此格式化程序的副本
     * <p>
     *  这将返回一个格式化程序,该格式化程序具有与此格式化程序类似的状态,但是使用覆盖年表集默认情况下,格式化程序没有覆盖时间顺序,返回null
     * <p>
     *  如果添加了覆盖,那么格式化或解析的任何日期都将受到影响
     * <p>
     *  格式化时,如果临时对象包含日期,那么它将在覆盖年表中转换为日期。
     * 通过查询{@link ChronoField#EPOCH_DAY EPOCH_DAY}字段确定临时是否包含日期将保留任何时间或区域除非被覆盖。
     * <p>
     * 如果时间对象不包含日期,但包含一个或多个{@code ChronoField}日期字段,则会抛出{@code DateTimeException}在所有其他情况下,覆盖年表将添加到时间,年代,但不改变日
     * 期/时间。
     * <p>
     *  当解析时,有两种不同的情况要考虑如果一个年表已经直接从文本中解析,也许是因为使用了{@link DateTimeFormatterBuilder#appendChronologyId()},那么这个覆
     * 盖年表没有效果如果没有区域被解析,这个覆盖时间表将用于将{@code ChronoField}值解释为根据年表的日期解析规则的日期。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param chrono  the new chronology, null if no override
     * @return a formatter based on this formatter with the requested override chronology, not null
     */
    public DateTimeFormatter withChronology(Chronology chrono) {
        if (Objects.equals(this.chrono, chrono)) {
            return this;
        }
        return new DateTimeFormatter(printerParser, locale, decimalStyle, resolverStyle, resolverFields, chrono, zone);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the overriding zone to be used during formatting.
     * <p>
     * This returns the override zone, used to convert instants.
     * By default, a formatter has no override zone, returning null.
     * See {@link #withZone(ZoneId)} for more details on overriding.
     *
     * <p>
     * 获取要在格式化期间使用的覆盖区域
     * <p>
     *  这返回覆盖区域,用于转换时刻默认情况下,格式化器没有覆盖区域,返回null有关覆盖的更多详细信息,请参阅{@link #withZone(ZoneId)}
     * 
     * 
     * @return the override zone of this formatter, null if no override
     */
    public ZoneId getZone() {
        return zone;
    }

    /**
     * Returns a copy of this formatter with a new override zone.
     * <p>
     * This returns a formatter with similar state to this formatter but
     * with the override zone set.
     * By default, a formatter has no override zone, returning null.
     * <p>
     * If an override is added, then any instant that is formatted or parsed will be affected.
     * <p>
     * When formatting, if the temporal object contains an instant, then it will
     * be converted to a zoned date-time using the override zone.
     * Whether the temporal is an instant is determined by querying the
     * {@link ChronoField#INSTANT_SECONDS INSTANT_SECONDS} field.
     * If the input has a chronology then it will be retained unless overridden.
     * If the input does not have a chronology, such as {@code Instant}, then
     * the ISO chronology will be used.
     * <p>
     * If the temporal object does not contain an instant, but does contain
     * an offset then an additional check is made. If the normalized override
     * zone is an offset that differs from the offset of the temporal, then
     * a {@code DateTimeException} is thrown. In all other cases, the override
     * zone is added to the temporal, replacing any previous zone, but without
     * changing the date/time.
     * <p>
     * When parsing, there are two distinct cases to consider.
     * If a zone has been parsed directly from the text, perhaps because
     * {@link DateTimeFormatterBuilder#appendZoneId()} was used, then
     * this override zone has no effect.
     * If no zone has been parsed, then this override zone will be included in
     * the result of the parse where it can be used to build instants and date-times.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回带有新覆盖区域的此格式化程序的副本
     * <p>
     *  这会返回一个格式化程序,该格式化程序具有与此格式化程序类似的状态,但是覆盖区域设置默认情况下,格式化程序没有覆盖区域,返回null
     * <p>
     *  如果添加了覆盖,则任何格式化或解析的即时将受到影响
     * <p>
     * 格式化时,如果临时对象包含即时,则使用覆盖区域将其转换为分区日期时间通过查询{@link ChronoField#INSTANT_SECONDS INSTANT_SECONDS}字段确定时间是否为即时值
     * 如果输入具有一个年表,那么它将被保留,除非被覆盖如果输入没有年表,例如{@code Instant},那么将使用ISO年表。
     * <p>
     * 如果时间对象不包含一个瞬时,但是包含一个偏移量,那么将进行一个额外的检查如果标准化覆盖区域是一个不同于时间偏移量的偏移量,那么将抛出一个{@code DateTimeException}在所有其他情况下
     * ,覆盖区域被添加到时间,替换任何先前的区域,但不改变日期/时间。
     * <p>
     *  当解析时,有两种不同的情况要考虑如果区域已经从文本直接解析,也许是因为{@link DateTimeFormatterBuilder#appendZoneId()}被使用,那么覆盖区域没有效果如果没有
     * 区域被解析,这个覆盖区域将被包括在解析的结果中,它可以用于构建时刻和日期时间。
     * <p>
     * 此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param zone  the new override zone, null if no override
     * @return a formatter based on this formatter with the requested override zone, not null
     */
    public DateTimeFormatter withZone(ZoneId zone) {
        if (Objects.equals(this.zone, zone)) {
            return this;
        }
        return new DateTimeFormatter(printerParser, locale, decimalStyle, resolverStyle, resolverFields, chrono, zone);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the resolver style to use during parsing.
     * <p>
     * This returns the resolver style, used during the second phase of parsing
     * when fields are resolved into dates and times.
     * By default, a formatter has the {@link ResolverStyle#SMART SMART} resolver style.
     * See {@link #withResolverStyle(ResolverStyle)} for more details.
     *
     * <p>
     *  获取在解析期间使用的解析器样式
     * <p>
     *  这返回解析器样式,在字段解析为日期和时间的第二阶段解析时使用默认情况下,格式化程序具有{@link ResolverStyle#SMART SMART}解析器样式请参阅{@link #withResolverStyle(ResolverStyle)}
     * 了解更多细节。
     * 
     * 
     * @return the resolver style of this formatter, not null
     */
    public ResolverStyle getResolverStyle() {
        return resolverStyle;
    }

    /**
     * Returns a copy of this formatter with a new resolver style.
     * <p>
     * This returns a formatter with similar state to this formatter but
     * with the resolver style set. By default, a formatter has the
     * {@link ResolverStyle#SMART SMART} resolver style.
     * <p>
     * Changing the resolver style only has an effect during parsing.
     * Parsing a text string occurs in two phases.
     * Phase 1 is a basic text parse according to the fields added to the builder.
     * Phase 2 resolves the parsed field-value pairs into date and/or time objects.
     * The resolver style is used to control how phase 2, resolving, happens.
     * See {@code ResolverStyle} for more information on the options available.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  使用新的解析器样式返回此格式化程序的副本
     * <p>
     *  这会返回一个格式化程序,该格式化程序具有与此格式化程序类似的状态,但是解析器样式已设置默认情况下,格式化程序具有{@link ResolverStyle#SMART SMART}解析器样式
     * <p>
     * 更改解析器样式仅在解析期间有作用解析文本字符串发生在两个阶段阶段1是根据添加到构建器的字段的基本文本解析阶段2将解析的字段/值对解析为日期和/或时间对象解析器样式用于控制阶段2,解决方案的发生情况有关可
     * 用选项的详细信息,请参见{@code ResolverStyle}。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param resolverStyle  the new resolver style, not null
     * @return a formatter based on this formatter with the requested resolver style, not null
     */
    public DateTimeFormatter withResolverStyle(ResolverStyle resolverStyle) {
        Objects.requireNonNull(resolverStyle, "resolverStyle");
        if (Objects.equals(this.resolverStyle, resolverStyle)) {
            return this;
        }
        return new DateTimeFormatter(printerParser, locale, decimalStyle, resolverStyle, resolverFields, chrono, zone);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the resolver fields to use during parsing.
     * <p>
     * This returns the resolver fields, used during the second phase of parsing
     * when fields are resolved into dates and times.
     * By default, a formatter has no resolver fields, and thus returns null.
     * See {@link #withResolverFields(Set)} for more details.
     *
     * <p>
     *  获取在解析期间使用的解析器字段
     * <p>
     *  这返回解析器字段,在字段解析为日期和时间的第二阶段解析时使用默认情况下,格式化程序没有解析器字段,因此返回null有关更多详细信息,请参阅{@link #withResolverFields(Set)}
     * 。
     * 
     * 
     * @return the immutable set of resolver fields of this formatter, null if no fields
     */
    public Set<TemporalField> getResolverFields() {
        return resolverFields;
    }

    /**
     * Returns a copy of this formatter with a new set of resolver fields.
     * <p>
     * This returns a formatter with similar state to this formatter but with
     * the resolver fields set. By default, a formatter has no resolver fields.
     * <p>
     * Changing the resolver fields only has an effect during parsing.
     * Parsing a text string occurs in two phases.
     * Phase 1 is a basic text parse according to the fields added to the builder.
     * Phase 2 resolves the parsed field-value pairs into date and/or time objects.
     * The resolver fields are used to filter the field-value pairs between phase 1 and 2.
     * <p>
     * This can be used to select between two or more ways that a date or time might
     * be resolved. For example, if the formatter consists of year, month, day-of-month
     * and day-of-year, then there are two ways to resolve a date.
     * Calling this method with the arguments {@link ChronoField#YEAR YEAR} and
     * {@link ChronoField#DAY_OF_YEAR DAY_OF_YEAR} will ensure that the date is
     * resolved using the year and day-of-year, effectively meaning that the month
     * and day-of-month are ignored during the resolving phase.
     * <p>
     * In a similar manner, this method can be used to ignore secondary fields that
     * would otherwise be cross-checked. For example, if the formatter consists of year,
     * month, day-of-month and day-of-week, then there is only one way to resolve a
     * date, but the parsed value for day-of-week will be cross-checked against the
     * resolved date. Calling this method with the arguments {@link ChronoField#YEAR YEAR},
     * {@link ChronoField#MONTH_OF_YEAR MONTH_OF_YEAR} and
     * {@link ChronoField#DAY_OF_MONTH DAY_OF_MONTH} will ensure that the date is
     * resolved correctly, but without any cross-check for the day-of-week.
     * <p>
     * In implementation terms, this method behaves as follows. The result of the
     * parsing phase can be considered to be a map of field to value. The behavior
     * of this method is to cause that map to be filtered between phase 1 and 2,
     * removing all fields other than those specified as arguments to this method.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     * 使用一组新的解析器字段返回此格式化程序的副本
     * <p>
     *  这返回一个与此格式化器类似状态的格式化器,但是设置了解析器字段默认情况下,格式化器没有解析器字段
     * <p>
     *  更改解析器字段只在解析期间有影响解析文本字符串发生在两个阶段阶段1是根据添加到构建器的字段的基本文本解析阶段2将解析的字段/值对解析为日期和/或时间对象解析器字段用于过滤阶段1和阶段2之间的字段值对。
     * <p>
     * 这可以用于在可以解决日期或时间的两种或更多种方式之间进行选择。
     * 例如,如果格式化程序包含年,月,月和日,则有两种方法可以解决日期使用参数{@link ChronoField#YEAR YEAR}和{@link ChronoField#DAY_OF_YEAR DAY_OF_YEAR}
     * 调用此方法将确保使用年份和年份来解析日期,实际上意味着月份和日期 - 的月份在解析阶段被忽略。
     * 这可以用于在可以解决日期或时间的两种或更多种方式之间进行选择。
     * <p>
     * 以类似的方式,该方法可以用于忽略否则将被交叉检查的次要字段。例如,如果格式化器包括年,月,月和星期几,则只有一个方法来解析日期,但是星期几的解析值将与解析日期进行交叉检查。
     * 使用参数{@link ChronoField#YEAR YEAR},{@link ChronoField#MONTH_OF_YEAR MONTH_OF_YEAR}和{ @link ChronoField#DAY_OF_MONTH DAY_OF_MONTH}
     * 将确保日期已正确解析,但没有对星期几进行任何交叉检查。
     * 以类似的方式,该方法可以用于忽略否则将被交叉检查的次要字段。例如,如果格式化器包括年,月,月和星期几,则只有一个方法来解析日期,但是星期几的解析值将与解析日期进行交叉检查。
     * <p>
     * 在实现方面,该方法的行为如下：解析阶段的结果可以被认为是字段到值的映射此方法的行为是使得该映射在阶段1和2之间被过滤,去除除了作为此方法的参数指定的那些
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param resolverFields  the new set of resolver fields, null if no fields
     * @return a formatter based on this formatter with the requested resolver style, not null
     */
    public DateTimeFormatter withResolverFields(TemporalField... resolverFields) {
        Set<TemporalField> fields = null;
        if (resolverFields != null) {
            fields = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(resolverFields)));
        }
        if (Objects.equals(this.resolverFields, fields)) {
            return this;
        }
        return new DateTimeFormatter(printerParser, locale, decimalStyle, resolverStyle, fields, chrono, zone);
    }

    /**
     * Returns a copy of this formatter with a new set of resolver fields.
     * <p>
     * This returns a formatter with similar state to this formatter but with
     * the resolver fields set. By default, a formatter has no resolver fields.
     * <p>
     * Changing the resolver fields only has an effect during parsing.
     * Parsing a text string occurs in two phases.
     * Phase 1 is a basic text parse according to the fields added to the builder.
     * Phase 2 resolves the parsed field-value pairs into date and/or time objects.
     * The resolver fields are used to filter the field-value pairs between phase 1 and 2.
     * <p>
     * This can be used to select between two or more ways that a date or time might
     * be resolved. For example, if the formatter consists of year, month, day-of-month
     * and day-of-year, then there are two ways to resolve a date.
     * Calling this method with the arguments {@link ChronoField#YEAR YEAR} and
     * {@link ChronoField#DAY_OF_YEAR DAY_OF_YEAR} will ensure that the date is
     * resolved using the year and day-of-year, effectively meaning that the month
     * and day-of-month are ignored during the resolving phase.
     * <p>
     * In a similar manner, this method can be used to ignore secondary fields that
     * would otherwise be cross-checked. For example, if the formatter consists of year,
     * month, day-of-month and day-of-week, then there is only one way to resolve a
     * date, but the parsed value for day-of-week will be cross-checked against the
     * resolved date. Calling this method with the arguments {@link ChronoField#YEAR YEAR},
     * {@link ChronoField#MONTH_OF_YEAR MONTH_OF_YEAR} and
     * {@link ChronoField#DAY_OF_MONTH DAY_OF_MONTH} will ensure that the date is
     * resolved correctly, but without any cross-check for the day-of-week.
     * <p>
     * In implementation terms, this method behaves as follows. The result of the
     * parsing phase can be considered to be a map of field to value. The behavior
     * of this method is to cause that map to be filtered between phase 1 and 2,
     * removing all fields other than those specified as arguments to this method.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  使用一组新的解析器字段返回此格式化程序的副本
     * <p>
     *  这返回一个与此格式化器类似状态的格式化器,但是设置了解析器字段默认情况下,格式化器没有解析器字段
     * <p>
     * 更改解析器字段只在解析期间有影响解析文本字符串发生在两个阶段阶段1是根据添加到构建器的字段的基本文本解析阶段2将解析的字段/值对解析为日期和/或时间对象解析器字段用于过滤阶段1和阶段2之间的字段值对
     * <p>
     * 这可以用于在可以解决日期或时间的两种或更多种方式之间进行选择。
     * 例如,如果格式化程序包含年,月,月和日,则有两种方法可以解决日期使用参数{@link ChronoField#YEAR YEAR}和{@link ChronoField#DAY_OF_YEAR DAY_OF_YEAR}
     * 调用此方法将确保使用年份和年份来解析日期,实际上意味着月份和日期 - 的月份在解析阶段被忽略。
     * 这可以用于在可以解决日期或时间的两种或更多种方式之间进行选择。
     * <p>
     * 以类似的方式,该方法可以用于忽略否则将被交叉检查的次要字段。例如,如果格式化器包括年,月,月和星期几,则只有一个方法来解析日期,但是星期几的解析值将与解析日期进行交叉检查。
     * 使用参数{@link ChronoField#YEAR YEAR},{@link ChronoField#MONTH_OF_YEAR MONTH_OF_YEAR}和{ @link ChronoField#DAY_OF_MONTH DAY_OF_MONTH}
     * 将确保日期已正确解析,但没有对星期几进行任何交叉检查。
     * 以类似的方式,该方法可以用于忽略否则将被交叉检查的次要字段。例如,如果格式化器包括年,月,月和星期几,则只有一个方法来解析日期,但是星期几的解析值将与解析日期进行交叉检查。
     * <p>
     * 在实现方面,该方法的行为如下：解析阶段的结果可以被认为是字段到值的映射此方法的行为是使得该映射在阶段1和2之间被过滤,去除除了作为此方法的参数指定的那些
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param resolverFields  the new set of resolver fields, null if no fields
     * @return a formatter based on this formatter with the requested resolver style, not null
     */
    public DateTimeFormatter withResolverFields(Set<TemporalField> resolverFields) {
        if (Objects.equals(this.resolverFields, resolverFields)) {
            return this;
        }
        if (resolverFields != null) {
            resolverFields = Collections.unmodifiableSet(new HashSet<>(resolverFields));
        }
        return new DateTimeFormatter(printerParser, locale, decimalStyle, resolverStyle, resolverFields, chrono, zone);
    }

    //-----------------------------------------------------------------------
    /**
     * Formats a date-time object using this formatter.
     * <p>
     * This formats the date-time to a String using the rules of the formatter.
     *
     * <p>
     *  使用此格式化程序格式化日期时间对象
     * <p>
     *  这使用格式化程序的规则将日期时间格式化为字符串
     * 
     * 
     * @param temporal  the temporal object to format, not null
     * @return the formatted string, not null
     * @throws DateTimeException if an error occurs during formatting
     */
    public String format(TemporalAccessor temporal) {
        StringBuilder buf = new StringBuilder(32);
        formatTo(temporal, buf);
        return buf.toString();
    }

    //-----------------------------------------------------------------------
    /**
     * Formats a date-time object to an {@code Appendable} using this formatter.
     * <p>
     * This outputs the formatted date-time to the specified destination.
     * {@link Appendable} is a general purpose interface that is implemented by all
     * key character output classes including {@code StringBuffer}, {@code StringBuilder},
     * {@code PrintStream} and {@code Writer}.
     * <p>
     * Although {@code Appendable} methods throw an {@code IOException}, this method does not.
     * Instead, any {@code IOException} is wrapped in a runtime exception.
     *
     * <p>
     *  使用此格式化程序将日期时间对象格式化为{@code Appendable}
     * <p>
     * 这将输出格式化的日期时间到指定的目标{@link Appendable}是一个通用接口,由所有关键字符输出类实现,包括{@code StringBuffer},{@code StringBuilder}
     * ,{@code PrintStream}和{ @code Writer}。
     * <p>
     *  虽然{@code Appendable}方法抛出一个{@code IOException},但是这个方法不会改为任何{@code IOException}被包装在运行时异常
     * 
     * 
     * @param temporal  the temporal object to format, not null
     * @param appendable  the appendable to format to, not null
     * @throws DateTimeException if an error occurs during formatting
     */
    public void formatTo(TemporalAccessor temporal, Appendable appendable) {
        Objects.requireNonNull(temporal, "temporal");
        Objects.requireNonNull(appendable, "appendable");
        try {
            DateTimePrintContext context = new DateTimePrintContext(temporal, this);
            if (appendable instanceof StringBuilder) {
                printerParser.format(context, (StringBuilder) appendable);
            } else {
                // buffer output to avoid writing to appendable in case of error
                StringBuilder buf = new StringBuilder(32);
                printerParser.format(context, buf);
                appendable.append(buf);
            }
        } catch (IOException ex) {
            throw new DateTimeException(ex.getMessage(), ex);
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Fully parses the text producing a temporal object.
     * <p>
     * This parses the entire text producing a temporal object.
     * It is typically more useful to use {@link #parse(CharSequence, TemporalQuery)}.
     * The result of this method is {@code TemporalAccessor} which has been resolved,
     * applying basic validation checks to help ensure a valid date-time.
     * <p>
     * If the parse completes without reading the entire length of the text,
     * or a problem occurs during parsing or merging, then an exception is thrown.
     *
     * <p>
     *  完全解析生成时间对象的文本
     * <p>
     *  这解析生成一个时间对象的整个文本使用{@link #parse(CharSequence,TemporalQuery)}通常更有用}此方法的结果是{@code TemporalAccessor}已经解
     * 决,应用基本验证检查以帮助确保有效的日期时间。
     * <p>
     * 如果解析完成而不读取文本的整个长度,或者在解析或合并期间发生问题,则抛出异常
     * 
     * 
     * @param text  the text to parse, not null
     * @return the parsed temporal object, not null
     * @throws DateTimeParseException if unable to parse the requested result
     */
    public TemporalAccessor parse(CharSequence text) {
        Objects.requireNonNull(text, "text");
        try {
            return parseResolved0(text, null);
        } catch (DateTimeParseException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            throw createError(text, ex);
        }
    }

    /**
     * Parses the text using this formatter, providing control over the text position.
     * <p>
     * This parses the text without requiring the parse to start from the beginning
     * of the string or finish at the end.
     * The result of this method is {@code TemporalAccessor} which has been resolved,
     * applying basic validation checks to help ensure a valid date-time.
     * <p>
     * The text will be parsed from the specified start {@code ParsePosition}.
     * The entire length of the text does not have to be parsed, the {@code ParsePosition}
     * will be updated with the index at the end of parsing.
     * <p>
     * The operation of this method is slightly different to similar methods using
     * {@code ParsePosition} on {@code java.text.Format}. That class will return
     * errors using the error index on the {@code ParsePosition}. By contrast, this
     * method will throw a {@link DateTimeParseException} if an error occurs, with
     * the exception containing the error index.
     * This change in behavior is necessary due to the increased complexity of
     * parsing and resolving dates/times in this API.
     * <p>
     * If the formatter parses the same field more than once with different values,
     * the result will be an error.
     *
     * <p>
     *  使用此格式化程序解析文本,提供对文本位置的控制
     * <p>
     *  这将解析文本,而不需要解析从字符串的开头开始或结束结束此方法的结果是已解决的{@code TemporalAccessor},应用基本验证检查以帮助确保有效的日期时间
     * <p>
     *  文本将从指定的开始解析{@code ParsePosition}文本的整个长度不必解析,{@code ParsePosition}将使用解析结束时的索引更新
     * <p>
     * 这个方法的操作与在{@code javatextFormat}上使用{@code ParsePosition}的类似方法略有不同。
     * 该类将使用{@code ParsePosition}上的错误索引返回错误。
     * 相反,此方法将抛出一个{@link DateTimeParseException}如果发生错误,包含错误索引的异常此行为更改是必要的,因为在此API中解析和解析日期/时间的复杂性增加。
     * <p>
     *  如果格式化器用不同的值多次解析同一个字段,结果将是错误
     * 
     * 
     * @param text  the text to parse, not null
     * @param position  the position to parse from, updated with length parsed
     *  and the index of any error, not null
     * @return the parsed temporal object, not null
     * @throws DateTimeParseException if unable to parse the requested result
     * @throws IndexOutOfBoundsException if the position is invalid
     */
    public TemporalAccessor parse(CharSequence text, ParsePosition position) {
        Objects.requireNonNull(text, "text");
        Objects.requireNonNull(position, "position");
        try {
            return parseResolved0(text, position);
        } catch (DateTimeParseException | IndexOutOfBoundsException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            throw createError(text, ex);
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Fully parses the text producing an object of the specified type.
     * <p>
     * Most applications should use this method for parsing.
     * It parses the entire text to produce the required date-time.
     * The query is typically a method reference to a {@code from(TemporalAccessor)} method.
     * For example:
     * <pre>
     *  LocalDateTime dt = parser.parse(str, LocalDateTime::from);
     * </pre>
     * If the parse completes without reading the entire length of the text,
     * or a problem occurs during parsing or merging, then an exception is thrown.
     *
     * <p>
     *  完全解析生成指定类型的对象的文本
     * <p>
     * 大多数应用程序应该使用此方法进行解析它解析整个文本以产生所需的日期时间查询通常是{@code from(TemporalAccessor)}方法的方法引用例如：
     * <pre>
     *  LocalDateTime dt = parserparse(str,LocalDateTime :: from);
     * </pre>
     *  如果解析完成而不读取文本的整个长度,或者在解析或合并期间发生问题,则抛出异常
     * 
     * 
     * @param <T> the type of the parsed date-time
     * @param text  the text to parse, not null
     * @param query  the query defining the type to parse to, not null
     * @return the parsed date-time, not null
     * @throws DateTimeParseException if unable to parse the requested result
     */
    public <T> T parse(CharSequence text, TemporalQuery<T> query) {
        Objects.requireNonNull(text, "text");
        Objects.requireNonNull(query, "query");
        try {
            return parseResolved0(text, null).query(query);
        } catch (DateTimeParseException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            throw createError(text, ex);
        }
    }

    /**
     * Fully parses the text producing an object of one of the specified types.
     * <p>
     * This parse method is convenient for use when the parser can handle optional elements.
     * For example, a pattern of 'uuuu-MM-dd HH.mm[ VV]' can be fully parsed to a {@code ZonedDateTime},
     * or partially parsed to a {@code LocalDateTime}.
     * The queries must be specified in order, starting from the best matching full-parse option
     * and ending with the worst matching minimal parse option.
     * The query is typically a method reference to a {@code from(TemporalAccessor)} method.
     * <p>
     * The result is associated with the first type that successfully parses.
     * Normally, applications will use {@code instanceof} to check the result.
     * For example:
     * <pre>
     *  TemporalAccessor dt = parser.parseBest(str, ZonedDateTime::from, LocalDateTime::from);
     *  if (dt instanceof ZonedDateTime) {
     *   ...
     *  } else {
     *   ...
     *  }
     * </pre>
     * If the parse completes without reading the entire length of the text,
     * or a problem occurs during parsing or merging, then an exception is thrown.
     *
     * <p>
     *  完全解析生成指定类型之一的对象的文本
     * <p>
     * 当解析器可以处理可选元素时,此解析方法很方便。
     * 例如,可以将"uuuu-MM-dd HHmm [VV]"模式完全解析为{@code ZonedDateTime}或部分解析为{@代码LocalDateTime}必须按顺序指定查询,从最佳匹配全解析选项开
     * 始,并以最差匹配的最小解析选项结束。
     * 当解析器可以处理可选元素时,此解析方法很方便。查询通常是对{@code from(TemporalAccessor)}方法的引用。
     * <p>
     *  结果与成功解析的第一个类型相关联通常,应用程序将使用{@code instanceof}来检查结果例如：
     * <pre>
     *  TemporalAccessor dt = parserparseBest(str,ZonedDateTime :: from,LocalDateTime :: from); if(dt instan
     * ceof ZonedDateTime){} else {}。
     * </pre>
     * 如果解析完成而不读取文本的整个长度,或者在解析或合并期间发生问题,则抛出异常
     * 
     * 
     * @param text  the text to parse, not null
     * @param queries  the queries defining the types to attempt to parse to,
     *  must implement {@code TemporalAccessor}, not null
     * @return the parsed date-time, not null
     * @throws IllegalArgumentException if less than 2 types are specified
     * @throws DateTimeParseException if unable to parse the requested result
     */
    public TemporalAccessor parseBest(CharSequence text, TemporalQuery<?>... queries) {
        Objects.requireNonNull(text, "text");
        Objects.requireNonNull(queries, "queries");
        if (queries.length < 2) {
            throw new IllegalArgumentException("At least two queries must be specified");
        }
        try {
            TemporalAccessor resolved = parseResolved0(text, null);
            for (TemporalQuery<?> query : queries) {
                try {
                    return (TemporalAccessor) resolved.query(query);
                } catch (RuntimeException ex) {
                    // continue
                }
            }
            throw new DateTimeException("Unable to convert parsed text using any of the specified queries");
        } catch (DateTimeParseException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            throw createError(text, ex);
        }
    }

    private DateTimeParseException createError(CharSequence text, RuntimeException ex) {
        String abbr;
        if (text.length() > 64) {
            abbr = text.subSequence(0, 64).toString() + "...";
        } else {
            abbr = text.toString();
        }
        return new DateTimeParseException("Text '" + abbr + "' could not be parsed: " + ex.getMessage(), text, 0, ex);
    }

    //-----------------------------------------------------------------------
    /**
     * Parses and resolves the specified text.
     * <p>
     * This parses to a {@code TemporalAccessor} ensuring that the text is fully parsed.
     *
     * <p>
     *  解析并解析指定的文本
     * <p>
     *  这解析到{@code TemporalAccessor},确保文本被完全解析
     * 
     * 
     * @param text  the text to parse, not null
     * @param position  the position to parse from, updated with length parsed
     *  and the index of any error, null if parsing whole string
     * @return the resolved result of the parse, not null
     * @throws DateTimeParseException if the parse fails
     * @throws DateTimeException if an error occurs while resolving the date or time
     * @throws IndexOutOfBoundsException if the position is invalid
     */
    private TemporalAccessor parseResolved0(final CharSequence text, final ParsePosition position) {
        ParsePosition pos = (position != null ? position : new ParsePosition(0));
        DateTimeParseContext context = parseUnresolved0(text, pos);
        if (context == null || pos.getErrorIndex() >= 0 || (position == null && pos.getIndex() < text.length())) {
            String abbr;
            if (text.length() > 64) {
                abbr = text.subSequence(0, 64).toString() + "...";
            } else {
                abbr = text.toString();
            }
            if (pos.getErrorIndex() >= 0) {
                throw new DateTimeParseException("Text '" + abbr + "' could not be parsed at index " +
                        pos.getErrorIndex(), text, pos.getErrorIndex());
            } else {
                throw new DateTimeParseException("Text '" + abbr + "' could not be parsed, unparsed text found at index " +
                        pos.getIndex(), text, pos.getIndex());
            }
        }
        return context.toResolved(resolverStyle, resolverFields);
    }

    /**
     * Parses the text using this formatter, without resolving the result, intended
     * for advanced use cases.
     * <p>
     * Parsing is implemented as a two-phase operation.
     * First, the text is parsed using the layout defined by the formatter, producing
     * a {@code Map} of field to value, a {@code ZoneId} and a {@code Chronology}.
     * Second, the parsed data is <em>resolved</em>, by validating, combining and
     * simplifying the various fields into more useful ones.
     * This method performs the parsing stage but not the resolving stage.
     * <p>
     * The result of this method is {@code TemporalAccessor} which represents the
     * data as seen in the input. Values are not validated, thus parsing a date string
     * of '2012-00-65' would result in a temporal with three fields - year of '2012',
     * month of '0' and day-of-month of '65'.
     * <p>
     * The text will be parsed from the specified start {@code ParsePosition}.
     * The entire length of the text does not have to be parsed, the {@code ParsePosition}
     * will be updated with the index at the end of parsing.
     * <p>
     * Errors are returned using the error index field of the {@code ParsePosition}
     * instead of {@code DateTimeParseException}.
     * The returned error index will be set to an index indicative of the error.
     * Callers must check for errors before using the context.
     * <p>
     * If the formatter parses the same field more than once with different values,
     * the result will be an error.
     * <p>
     * This method is intended for advanced use cases that need access to the
     * internal state during parsing. Typical application code should use
     * {@link #parse(CharSequence, TemporalQuery)} or the parse method on the target type.
     *
     * <p>
     *  使用此格式化程序解析文本,无需解析结果,适用于高级用例
     * <p>
     *  解析实现为两阶段操作首先,使用格式化程序定义的布局解析文本,生成一个字段值{@code Map},{@code ZoneId}和{@code Chronology} Second,通过验证,组合和简化
     * 各种字段成更有用的方式,解析的数据被解析</em>。
     * 该方法执行解析阶段,但不执行解析阶段。
     * <p>
     * 此方法的结果是{@code TemporalAccessor},它表示在输入中看到的数据值未经验证,因此解析日期字符串'2012-00-65'将导致具有三个字段的时间：年份' 2012',月份'0'和月
     * 份'65'。
     * <p>
     *  文本将从指定的开始解析{@code ParsePosition}文本的整个长度不必解析,{@code ParsePosition}将使用解析结束时的索引更新
     * <p>
     *  使用{@code ParsePosition}而不是{@code DateTimeParseException}的错误索引字段返回错误。返回的错误索引将设置为指示错误的索引。
     * 调用者必须在使用上下文之前检查错误。
     * <p>
     * 如果格式化器用不同的值多次解析同一个字段,结果将是错误
     * <p>
     *  此方法适用于需要在解析期间访问内部状态的高级用例。典型应用程序代码应使用{@link #parse(CharSequence,TemporalQuery)}或目标类型的解析方法
     * 
     * 
     * @param text  the text to parse, not null
     * @param position  the position to parse from, updated with length parsed
     *  and the index of any error, not null
     * @return the parsed text, null if the parse results in an error
     * @throws DateTimeException if some problem occurs during parsing
     * @throws IndexOutOfBoundsException if the position is invalid
     */
    public TemporalAccessor parseUnresolved(CharSequence text, ParsePosition position) {
        DateTimeParseContext context = parseUnresolved0(text, position);
        if (context == null) {
            return null;
        }
        return context.toUnresolved();
    }

    private DateTimeParseContext parseUnresolved0(CharSequence text, ParsePosition position) {
        Objects.requireNonNull(text, "text");
        Objects.requireNonNull(position, "position");
        DateTimeParseContext context = new DateTimeParseContext(this);
        int pos = position.getIndex();
        pos = printerParser.parse(context, text, pos);
        if (pos < 0) {
            position.setErrorIndex(~pos);  // index not updated from input
            return null;
        }
        position.setIndex(pos);  // errorIndex not updated from input
        return context;
    }

    //-----------------------------------------------------------------------
    /**
     * Returns the formatter as a composite printer parser.
     *
     * <p>
     *  返回格式化器作为复合打印机解析器
     * 
     * 
     * @param optional  whether the printer/parser should be optional
     * @return the printer/parser, not null
     */
    CompositePrinterParser toPrinterParser(boolean optional) {
        return printerParser.withOptional(optional);
    }

    /**
     * Returns this formatter as a {@code java.text.Format} instance.
     * <p>
     * The returned {@link Format} instance will format any {@link TemporalAccessor}
     * and parses to a resolved {@link TemporalAccessor}.
     * <p>
     * Exceptions will follow the definitions of {@code Format}, see those methods
     * for details about {@code IllegalArgumentException} during formatting and
     * {@code ParseException} or null during parsing.
     * The format does not support attributing of the returned format string.
     *
     * <p>
     *  以{@code javatextFormat}实例的形式返回此格式化程序
     * <p>
     *  返回的{@link Format}实例将格式化任何{@link TemporalAccessor}并解析为解析的{@link TemporalAccessor}
     * <p>
     * 异常将遵循{@code Format}的定义,有关格式化期间的{@code IllegalArgumentException}和解析过程中的{@code ParseException}或null的详细信
     * 息,请参阅这些方法格式不支持返回的格式字符串。
     * 
     * 
     * @return this formatter as a classic format instance, not null
     */
    public Format toFormat() {
        return new ClassicFormat(this, null);
    }

    /**
     * Returns this formatter as a {@code java.text.Format} instance that will
     * parse using the specified query.
     * <p>
     * The returned {@link Format} instance will format any {@link TemporalAccessor}
     * and parses to the type specified.
     * The type must be one that is supported by {@link #parse}.
     * <p>
     * Exceptions will follow the definitions of {@code Format}, see those methods
     * for details about {@code IllegalArgumentException} during formatting and
     * {@code ParseException} or null during parsing.
     * The format does not support attributing of the returned format string.
     *
     * <p>
     *  将此格式化程序作为{@code javatextFormat}实例返回,该实例将使用指定的查询进行解析
     * <p>
     *  返回的{@link Format}实例将格式化任何{@link TemporalAccessor}并解析为指定的类型。类型必须是{@link #parse}支持的类型,
     * <p>
     * 异常将遵循{@code Format}的定义,有关格式化期间的{@code IllegalArgumentException}和解析过程中的{@code ParseException}或null的详细信
     * 息,请参阅这些方法格式不支持返回的格式字符串。
     * 
     * 
     * @param parseQuery  the query defining the type to parse to, not null
     * @return this formatter as a classic format instance, not null
     */
    public Format toFormat(TemporalQuery<?> parseQuery) {
        Objects.requireNonNull(parseQuery, "parseQuery");
        return new ClassicFormat(this, parseQuery);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a description of the underlying formatters.
     *
     * <p>
     * 
     * @return a description of this formatter, not null
     */
    @Override
    public String toString() {
        String pattern = printerParser.toString();
        pattern = pattern.startsWith("[") ? pattern : pattern.substring(1, pattern.length() - 1);
        return pattern;
        // TODO: Fix tests to not depend on toString()
//        return "DateTimeFormatter[" + locale +
//                (chrono != null ? "," + chrono : "") +
//                (zone != null ? "," + zone : "") +
//                pattern + "]";
    }

    //-----------------------------------------------------------------------
    /**
     * Implements the classic Java Format API.
     * <p>
     *  返回基础格式化程序的描述
     * 
     * 
     * @serial exclude
     */
    @SuppressWarnings("serial")  // not actually serializable
    static class ClassicFormat extends Format {
        /** The formatter. */
        private final DateTimeFormatter formatter;
        /** The type to be parsed. */
        private final TemporalQuery<?> parseType;
        /** Constructor. */
        public ClassicFormat(DateTimeFormatter formatter, TemporalQuery<?> parseType) {
            this.formatter = formatter;
            this.parseType = parseType;
        }

        @Override
        public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
            Objects.requireNonNull(obj, "obj");
            Objects.requireNonNull(toAppendTo, "toAppendTo");
            Objects.requireNonNull(pos, "pos");
            if (obj instanceof TemporalAccessor == false) {
                throw new IllegalArgumentException("Format target must implement TemporalAccessor");
            }
            pos.setBeginIndex(0);
            pos.setEndIndex(0);
            try {
                formatter.formatTo((TemporalAccessor) obj, toAppendTo);
            } catch (RuntimeException ex) {
                throw new IllegalArgumentException(ex.getMessage(), ex);
            }
            return toAppendTo;
        }
        @Override
        public Object parseObject(String text) throws ParseException {
            Objects.requireNonNull(text, "text");
            try {
                if (parseType == null) {
                    return formatter.parseResolved0(text, null);
                }
                return formatter.parse(text, parseType);
            } catch (DateTimeParseException ex) {
                throw new ParseException(ex.getMessage(), ex.getErrorIndex());
            } catch (RuntimeException ex) {
                throw (ParseException) new ParseException(ex.getMessage(), 0).initCause(ex);
            }
        }
        @Override
        public Object parseObject(String text, ParsePosition pos) {
            Objects.requireNonNull(text, "text");
            DateTimeParseContext context;
            try {
                context = formatter.parseUnresolved0(text, pos);
            } catch (IndexOutOfBoundsException ex) {
                if (pos.getErrorIndex() < 0) {
                    pos.setErrorIndex(0);
                }
                return null;
            }
            if (context == null) {
                if (pos.getErrorIndex() < 0) {
                    pos.setErrorIndex(0);
                }
                return null;
            }
            try {
                TemporalAccessor resolved = context.toResolved(formatter.resolverStyle, formatter.resolverFields);
                if (parseType == null) {
                    return resolved;
                }
                return resolved.query(parseType);
            } catch (RuntimeException ex) {
                pos.setErrorIndex(0);
                return null;
            }
        }
    }

}
