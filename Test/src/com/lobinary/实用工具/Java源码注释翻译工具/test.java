/***** Lobxxx Translate Finished ******/
/***** Lobxxx Translate Finished ******/
package com.lobinary.实用工具.Java源码注释翻译工具;
/***** Lobxxx Translate Finished ******/
import java.util.Calendar;
import java.util.Locale;
import java.util.spi.CalendarDataProvider;
import java.util.spi.LocaleServiceProvider;
import java.util.spi.TimeZoneNameProvider;

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

 * 用于提供{@code Calendar}字段值的本地化字符串表示（显示名称）的服务提供程序的抽象类。 <p> <a name="calendartypes"> <b>日历类型</ b> </a> <p>日历类型用于指定{@link #getDisplayName（String，int，int， int，Locale）getDisplayName}和{@link #getDisplayNames（String，int，int，Locale）getDisplayNames}方法提供日历字段值名称。有关详情，请参阅{@link Calendar＃getCalendarType（）}。 <p> <b>日历字段</ b> <p>日历字段是使用{@link Calendar}中定义的常量指定的。以下是日历常用字段及其为每个日历系统支持的值。<table style =“border-bottom：1px 
 * solid”border =“1”cellpadding =“3”cellspacing =“0”summary =“Field values”> <tr> <th> Field </ th> <th> Value < / th> <th>说明</ t> </ tr> <tr> <td valign =“top”> {@ link Calendar＃MONTH} </ td> <td valign =“top”> {@ link Calendar＃ JANUARY}到{@link Calendar＃UNDECIMBER} </ td> <td>月份编号为0（例如，0  -  1月，...，11  -  12月）。一些日历系统有13个月。如果受支持的语言环境需要，则需要在格式化和独立表单中支持月份名称。 </ td> </ tr> <tr> <td valign =“top”> {@ link Calendar＃DAY_OF_WEEK} </ td </ td>如果两种形式没有区别，则应以两种形式返回相同的名称。 > <td valign =“top”> {@ link日历＃SUNDAY}到{@link日历＃SATURDAY} </ td> <td>星期几编号从1开始， ，...，7  - 星期六）。</ td> </ tr> <tr> <td valign =“top”> {@ link Calendar＃AM_PM} </ td> <td valign =“top”> {@ link Calendar＃AM} to {@link Calendar #PM} </ td> <td> 0  -  AM，1  -  PM </ td> </ tr> </ table> <p style =“margin-top：20px”>以下是日历特定字段要支持的值。 <table style =“border-bottom：1px solid”border =“1”cellpadding =“3”cellspacing =“0”summary =“日历类型和字段值”> <tr> <th>日历类型</ th> th>字段</ th> <th>值</ th> <th>描述</ th> </ tr> <tr> <td rowspan =“2”valign =“top”> {@ code“gregory”} </ td> <td> <td> <td> <td> {@ link java.util.GregorianCalendar＃BC} </ td> <td rowspan =“2”valign =“top”> {@ link calendar＃ （BCE）</ td> </ tr> <tr> <td> 1 </ td> <td> {@ link java.util.GregorianCalendar＃AD} <td rowspan =“2”valign =“top”> {@ code“buddhist”} </ td> <td rowspan =“2”valign =“top”> {@ link Calendar＃ERA} </ td> <td > 0 </ td> <td> BC（BCE）</ td> </ tr> <tr> <td> 1 </ td> <td>（Buddhist Era）</ td> </ tr> <tr> <td rowspan 

 * @author Masayoshi Okutsu
 * @since 1.8
 * @see CalendarDataProvider
 * @see Locale#getUnicodeLocaleType(String)
 */
public abstract class test extends LocaleServiceProvider {
    /**
     * Sole constructor. (For invocation by subclass constructors, typically
     * implicit.)
	 
	 * 
	 


     */
    protected test() {
    }
