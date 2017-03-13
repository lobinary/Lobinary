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

package java.util.spi;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

/**
 * An abstract class for service providers that provide localized string
 * representations (display names) of {@code Calendar} field values.
 *
 * <p><a name="calendartypes"><b>Calendar Types</b></a>
 *
 * <p>Calendar types are used to specify calendar systems for which the {@link
 * #getDisplayName(String, int, int, int, Locale) getDisplayName} and {@link
 * #getDisplayNames(String, int, int, Locale) getDisplayNames} methods provide
 * calendar field value names. See {@link Calendar#getCalendarType()} for details.
 *
 * <p><b>Calendar Fields</b>
 *
 * <p>Calendar fields are specified with the constants defined in {@link
 * Calendar}. The following are calendar-common fields and their values to be
 * supported for each calendar system.
 *
 * <table style="border-bottom:1px solid" border="1" cellpadding="3" cellspacing="0" summary="Field values">
 *   <tr>
 *     <th>Field</th>
 *     <th>Value</th>
 *     <th>Description</th>
 *   </tr>
 *   <tr>
 *     <td valign="top">{@link Calendar#MONTH}</td>
 *     <td valign="top">{@link Calendar#JANUARY} to {@link Calendar#UNDECIMBER}</td>
 *     <td>Month numbering is 0-based (e.g., 0 - January, ..., 11 -
 *         December). Some calendar systems have 13 months. Month
 *         names need to be supported in both the formatting and
 *         stand-alone forms if required by the supported locales. If there's
 *         no distinction in the two forms, the same names should be returned
 *         in both of the forms.</td>
 *   </tr>
 *   <tr>
 *     <td valign="top">{@link Calendar#DAY_OF_WEEK}</td>
 *     <td valign="top">{@link Calendar#SUNDAY} to {@link Calendar#SATURDAY}</td>
 *     <td>Day-of-week numbering is 1-based starting from Sunday (i.e., 1 - Sunday,
 *         ..., 7 - Saturday).</td>
 *   </tr>
 *   <tr>
 *     <td valign="top">{@link Calendar#AM_PM}</td>
 *     <td valign="top">{@link Calendar#AM} to {@link Calendar#PM}</td>
 *     <td>0 - AM, 1 - PM</td>
 *   </tr>
 * </table>
 *
 * <p style="margin-top:20px">The following are calendar-specific fields and their values to be supported.
 *
 * <table style="border-bottom:1px solid" border="1" cellpadding="3" cellspacing="0" summary="Calendar type and field values">
 *   <tr>
 *     <th>Calendar Type</th>
 *     <th>Field</th>
 *     <th>Value</th>
 *     <th>Description</th>
 *   </tr>
 *   <tr>
 *     <td rowspan="2" valign="top">{@code "gregory"}</td>
 *     <td rowspan="2" valign="top">{@link Calendar#ERA}</td>
 *     <td>0</td>
 *     <td>{@link java.util.GregorianCalendar#BC} (BCE)</td>
 *   </tr>
 *   <tr>
 *     <td>1</td>
 *     <td>{@link java.util.GregorianCalendar#AD} (CE)</td>
 *   </tr>
 *   <tr>
 *     <td rowspan="2" valign="top">{@code "buddhist"}</td>
 *     <td rowspan="2" valign="top">{@link Calendar#ERA}</td>
 *     <td>0</td>
 *     <td>BC (BCE)</td>
 *   </tr>
 *   <tr>
 *     <td>1</td>
 *     <td>B.E. (Buddhist Era)</td>
 *   </tr>
 *   <tr>
 *     <td rowspan="6" valign="top">{@code "japanese"}</td>
 *     <td rowspan="5" valign="top">{@link Calendar#ERA}</td>
 *     <td>0</td>
 *     <td>Seireki (Before Meiji)</td>
 *   </tr>
 *   <tr>
 *     <td>1</td>
 *     <td>Meiji</td>
 *   </tr>
 *   <tr>
 *     <td>2</td>
 *     <td>Taisho</td>
 *   </tr>
 *   <tr>
 *     <td>3</td>
 *     <td>Showa</td>
 *   </tr>
 *   <tr>
 *     <td>4</td>
 *     <td >Heisei</td>
 *   </tr>
 *   <tr>
 *     <td>{@link Calendar#YEAR}</td>
 *     <td>1</td>
 *     <td>the first year in each era. It should be returned when a long
 *     style ({@link Calendar#LONG_FORMAT} or {@link Calendar#LONG_STANDALONE}) is
 *     specified. See also the <a href="../../text/SimpleDateFormat.html#year">
 *     Year representation in {@code SimpleDateFormat}</a>.</td>
 *   </tr>
 *   <tr>
 *     <td rowspan="2" valign="top">{@code "roc"}</td>
 *     <td rowspan="2" valign="top">{@link Calendar#ERA}</td>
 *     <td>0</td>
 *     <td>Before R.O.C.</td>
 *   </tr>
 *   <tr>
 *     <td>1</td>
 *     <td>R.O.C.</td>
 *   </tr>
 *   <tr>
 *     <td rowspan="2" valign="top">{@code "islamic"}</td>
 *     <td rowspan="2" valign="top">{@link Calendar#ERA}</td>
 *     <td>0</td>
 *     <td>Before AH</td>
 *   </tr>
 *   <tr>
 *     <td>1</td>
 *     <td>Anno Hijrah (AH)</td>
 *   </tr>
 * </table>
 *
 * <p>Calendar field value names for {@code "gregory"} must be consistent with
 * the date-time symbols provided by {@link java.text.spi.DateFormatSymbolsProvider}.
 *
 * <p>Time zone names are supported by {@link TimeZoneNameProvider}.
 *
 * <p>
 *  用于提供{@code Calendar}字段值的本地化字符串表示(显示名称)的服务提供程序的抽象类
 * 
 *  <p> <a name=\"calendartypes\"> <b>日历类型</b> </a>
 * 
 *  <p>日历类型用于指定{@link #getDisplayName(String,int,int,int,Locale)getDisplayName}和{@link #getDisplayNames(String,int,int,Locale)getDisplayNames}
 * 方法的日历系统提供日历字段值名称有关详细信息,请参阅{@link Calendar#getCalendarType()}。
 * 
 *  <p> <b>日历字段</b>
 * 
 * <p>日历字段使用{@link Calendar}中定义的常量指定。以下是日历常用字段及其每个日历系统支持的值
 * 
 * <table style="border-bottom:1px solid" border="1" cellpadding="3" cellspacing="0" summary="Field values">
 * <tr>
 *  <th>字段</th> <th>值</th> <th>描述</th>
 * </tr>
 * <tr>
 *  <td valign ="top"> {@ link Calendar#MONTH} </td> <td valign ="top"> {@ link Calendar#JANUARY} to {@link Calendar#UNDECIMBER}
 *  </td> <td> Month编号为0(例如,0  -  1月,11  -  12月)某些日历系统有13个月如果支持的区域设置需要,格式化和独立窗体中需要支持月份名称如果没有区分两个形式,应以两种形
 * 式</td>返回相同的名称。
 * </tr>
 * <tr>
 * <td valign ="top"> {@ link Calendar#DAY_OF_WEEK} </td> <td valign ="top"> {@ link Calendar#SUNDAY} to
 *  {@link Calendar#SATURDAY} </td> <td> Day星期日编号从1开始(即1  - 星期日,7  - 星期六)。
 * </td>。
 * </tr>
 * <tr>
 *  <td valign ="top"> {@ link Calendar#AM_PM} </td> <td valign ="top"> {@ link Calendar#AM} to {@link Calendar#PM}
 *  </td> <td> 0 -  AM,1  -  PM </td>。
 * </tr>
 * </table>
 * 
 *  <p style ="margin-top：20px">以下是特定于日历的字段及其支持的值
 * 
 * <table style="border-bottom:1px solid" border="1" cellpadding="3" cellspacing="0" summary="Calendar type and field values">
 * <tr>
 *  <th>日历类型</th> <th>字段</th> <th>值</th> <th>描述</th>
 * </tr>
 * <tr>
 *  <td rowspan ="2"valign ="top"> {@ code"gregory"} </td> <td rowspan ="2"valign ="top"> {@ link Calendar#ERA}
 *  </td> <td > 0 </td> <td> {@ link javautilGregorianCalendar#BC}(BCE)</td>。
 * </tr>
 * <tr>
 *  <td> 1 </td> <td> {@ link javautilGregorianCalender#AD}(CE)</td>
 * </tr>
 * <tr>
 * <td rowspan ="2"valign ="top"> {@ code"buddhist"} </td> <td rowspan ="2"valign ="top"> {@ link Calendar#ERA}
 *  </td> <td > 0 </td> <td> BC(BCE)</td>。
 * </tr>
 * <tr>
 *  <td> 1 </td> <td> BE(佛教时代)</td>
 * </tr>
 * <tr>
 *  <td rowspan ="6"valign ="top"> {@ code"japanese"} </td> <td rowspan ="5"valign ="top"> {@ link Calendar#ERA}
 *  </td> <td > 0 </td> <td> Seireki(Meiji之前)</td>。
 * </tr>
 * <tr>
 *  <td> 1 </td> <td> Meiji </td>
 * </tr>
 * <tr>
 *  <td> 2 </td> <td> Taisho </td>
 * </tr>
 * <tr>
 *  <td> 3 </td> <td> Showa </td>
 * </tr>
 * <tr>
 *  <td> 4 </td> <td> Heisei </td>
 * </tr>
 * 
 * @author Masayoshi Okutsu
 * @since 1.8
 * @see CalendarDataProvider
 * @see Locale#getUnicodeLocaleType(String)
 */
public abstract class CalendarNameProvider extends LocaleServiceProvider {
    /**
     * Sole constructor. (For invocation by subclass constructors, typically
     * implicit.)
     * <p>
     * <tr>
     *  <td> {@ link Calendar#YEAR} </td> <td> 1 </td> <td>每个时代的第一年当长式样式({@link Calendar#LONG_FORMAT}链接日历#LO
     * NG_STANDALONE})另请参见<a href=\"//text/SimpleDateFormathtml#year\"> Year表示在{@code SimpleDateFormat} </a>
     *  </td>。
     * </tr>
     * <tr>
     * <td rowspan ="2"valign ="top"> {@ code"roc"} </td> <td rowspan ="2"valign ="top"> {@ link Calendar#ERA}
     *  </td> <td > 0 </td> <td> ROC之前</td>。
     * </tr>
     * <tr>
     *  <td> 1 </td> <td> ROC </td>
     * </tr>
     * <tr>
     *  <td rowspan ="2"valign ="top"> {@ code"islamic"} </td> <td rowspan ="2"valign ="top"> {@ link Calendar#ERA}
     *  </td> <td > 0 </td> <td> Before AH </td>之前。
     * </tr>
     * <tr>
     *  <td> 1 </td> <td> Anno Hijrah(AH)</td>
     * </tr>
     * </table>
     * 
     *  <p> {@code"gregory"}的日历字段值名称必须与{@link javatextspiDateFormatSymbolsProvider}提供的日期时间符号一致
     * 
     *  <p>时区名称由{@link TimeZoneNameProvider}支持
     * 
     */
    protected CalendarNameProvider() {
    }

    /**
     * Returns the string representation (display name) of the calendar
     * <code>field value</code> in the given <code>style</code> and
     * <code>locale</code>.  If no string representation is
     * applicable, <code>null</code> is returned.
     *
     * <p>{@code field} is a {@code Calendar} field index, such as {@link
     * Calendar#MONTH}. The time zone fields, {@link Calendar#ZONE_OFFSET} and
     * {@link Calendar#DST_OFFSET}, are <em>not</em> supported by this
     * method. {@code null} must be returned if any time zone fields are
     * specified.
     *
     * <p>{@code value} is the numeric representation of the {@code field} value.
     * For example, if {@code field} is {@link Calendar#DAY_OF_WEEK}, the valid
     * values are {@link Calendar#SUNDAY} to {@link Calendar#SATURDAY}
     * (inclusive).
     *
     * <p>{@code style} gives the style of the string representation. It is one
     * of {@link Calendar#SHORT_FORMAT} ({@link Calendar#SHORT SHORT}),
     * {@link Calendar#SHORT_STANDALONE}, {@link Calendar#LONG_FORMAT}
     * ({@link Calendar#LONG LONG}), {@link Calendar#LONG_STANDALONE},
     * {@link Calendar#NARROW_FORMAT}, or {@link Calendar#NARROW_STANDALONE}.
     *
     * <p>For example, the following call will return {@code "Sunday"}.
     * <pre>
     * getDisplayName("gregory", Calendar.DAY_OF_WEEK, Calendar.SUNDAY,
     *                Calendar.LONG_STANDALONE, Locale.ENGLISH);
     * </pre>
     *
     * <p>
     *  唯一构造函数(对于通过子类构造函数调用,通常是隐式的)
     * 
     * 
     * @param calendarType
     *              the calendar type. (Any calendar type given by {@code locale}
     *              is ignored.)
     * @param field
     *              the {@code Calendar} field index,
     *              such as {@link Calendar#DAY_OF_WEEK}
     * @param value
     *              the value of the {@code Calendar field},
     *              such as {@link Calendar#MONDAY}
     * @param style
     *              the string representation style: one of {@link
     *              Calendar#SHORT_FORMAT} ({@link Calendar#SHORT SHORT}),
     *              {@link Calendar#SHORT_STANDALONE}, {@link
     *              Calendar#LONG_FORMAT} ({@link Calendar#LONG LONG}),
     *              {@link Calendar#LONG_STANDALONE},
     *              {@link Calendar#NARROW_FORMAT},
     *              or {@link Calendar#NARROW_STANDALONE}
     * @param locale
     *              the desired locale
     * @return the string representation of the {@code field value}, or {@code
     *         null} if the string representation is not applicable or
     *         the given calendar type is unknown
     * @throws IllegalArgumentException
     *         if {@code field} or {@code style} is invalid
     * @throws NullPointerException if {@code locale} is {@code null}
     * @see TimeZoneNameProvider
     * @see java.util.Calendar#get(int)
     * @see java.util.Calendar#getDisplayName(int, int, Locale)
     */
    public abstract String getDisplayName(String calendarType,
                                          int field, int value,
                                          int style, Locale locale);

    /**
     * Returns a {@code Map} containing all string representations (display
     * names) of the {@code Calendar} {@code field} in the given {@code style}
     * and {@code locale} and their corresponding field values.
     *
     * <p>{@code field} is a {@code Calendar} field index, such as {@link
     * Calendar#MONTH}. The time zone fields, {@link Calendar#ZONE_OFFSET} and
     * {@link Calendar#DST_OFFSET}, are <em>not</em> supported by this
     * method. {@code null} must be returned if any time zone fields are specified.
     *
     * <p>{@code style} gives the style of the string representation. It must be
     * one of {@link Calendar#ALL_STYLES}, {@link Calendar#SHORT_FORMAT} ({@link
     * Calendar#SHORT SHORT}), {@link Calendar#SHORT_STANDALONE}, {@link
     * Calendar#LONG_FORMAT} ({@link Calendar#LONG LONG}), {@link
     * Calendar#LONG_STANDALONE}, {@link Calendar#NARROW_FORMAT}, or
     * {@link Calendar#NARROW_STANDALONE}. Note that narrow names may
     * not be unique due to use of single characters, such as "S" for Sunday
     * and Saturday, and that no narrow names are included in that case.
     *
     * <p>For example, the following call will return a {@code Map} containing
     * {@code "January"} to {@link Calendar#JANUARY}, {@code "Jan"} to {@link
     * Calendar#JANUARY}, {@code "February"} to {@link Calendar#FEBRUARY},
     * {@code "Feb"} to {@link Calendar#FEBRUARY}, and so on.
     * <pre>
     * getDisplayNames("gregory", Calendar.MONTH, Calendar.ALL_STYLES, Locale.ENGLISH);
     * </pre>
     *
     * <p>
     * 返回给定<code>样式</code>和<code>语言环境</code>中日历的字符串表示(显示名称)<code>字段值</code>如果不适用字符串表示形式, null </code>
     * 
     *  <p> {@ code field}是一个{@code Calendar}字段索引,例如{@link Calendar#MONTH}时区字段{@link Calendar#ZONE_OFFSET}和{@link Calendar#DST_OFFSET}
     * 如果指定了任何时区字段,则必须返回此方法支持的<em> </em> {@code null}。
     * 
     *  <p> {@ code value}是{@code field}值的数字表示形式例如,如果{@code field}为{@link Calendar#DAY_OF_WEEK},则有效值为{@link Calendar#SUNDAY}
     *  {@link日历#SATURDAY}(含)。
     * 
     * <p> {@ code style}给出了字符串表示的样式。
     * 它是{@link日历#SHORT_FORMAT}({@link日历#SHORT SHORT}),{@link日历#SHORT_STANDALONE},{@link日历# LONG_FORMAT}({@link Calendar#LONG LONG}
     * ),{@link Calendar#LONG_STANDALONE},{@link Calendar#NARROW_FORMAT}或{@link Calendar#NARROW_STANDALONE}。
     * <p> {@ code style}给出了字符串表示的样式。
     * 
     *  <p>例如,以下调用将返回{@code"Sunday"}
     * <pre>
     *  getDisplayName("gregory",CalendarDAY_OF_WEEK,CalendarSUNDAY,CalendarLONG_STANDALONE,LocaleENGLISH);。
     * </pre>
     * 
     * 
     * @param calendarType
     *              the calendar type. (Any calendar type given by {@code locale}
     *              is ignored.)
     * @param field
     *              the calendar field for which the display names are returned
     * @param style
     *              the style applied to the display names; one of
     *              {@link Calendar#ALL_STYLES}, {@link Calendar#SHORT_FORMAT}
     *              ({@link Calendar#SHORT SHORT}), {@link
     *              Calendar#SHORT_STANDALONE}, {@link Calendar#LONG_FORMAT}
     *              ({@link Calendar#LONG LONG}), {@link Calendar#LONG_STANDALONE},
     *              {@link Calendar#NARROW_FORMAT},
     *              or {@link Calendar#NARROW_STANDALONE}
     * @param locale
     *              the desired locale
     * @return a {@code Map} containing all display names of {@code field} in
     *         {@code style} and {@code locale} and their {@code field} values,
     *         or {@code null} if no display names are defined for {@code field}
     * @throws NullPointerException
     *         if {@code locale} is {@code null}
     * @see Calendar#getDisplayNames(int, int, Locale)
     */
    public abstract Map<String, Integer> getDisplayNames(String calendarType,
                                                         int field, int style,
                                                         Locale locale);
}
