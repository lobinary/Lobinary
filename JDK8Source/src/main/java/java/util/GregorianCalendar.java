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
 * (C) Copyright Taligent, Inc. 1996-1998 - All Rights Reserved
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
 *  (C)版权Taligent,Inc 1996-1998  - 保留所有权利(C)版权所有IBM Corp 1996-1998  - 保留所有权利
 * 
 *  此源代码和文档的原始版本由IBM的全资子公司Taligent,Inc拥有版权和所有权。
 * 这些资料根据Taligent和Sun之间的许可协议的条款提供此技术受多个美国和国际专利保护Taligent是Taligent的注册商标。Taligent是Taligent的注册商标。
 * 
 */

package java.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import sun.util.calendar.BaseCalendar;
import sun.util.calendar.CalendarDate;
import sun.util.calendar.CalendarSystem;
import sun.util.calendar.CalendarUtils;
import sun.util.calendar.Era;
import sun.util.calendar.Gregorian;
import sun.util.calendar.JulianCalendar;
import sun.util.calendar.ZoneInfo;

/**
 * <code>GregorianCalendar</code> is a concrete subclass of
 * <code>Calendar</code> and provides the standard calendar system
 * used by most of the world.
 *
 * <p> <code>GregorianCalendar</code> is a hybrid calendar that
 * supports both the Julian and Gregorian calendar systems with the
 * support of a single discontinuity, which corresponds by default to
 * the Gregorian date when the Gregorian calendar was instituted
 * (October 15, 1582 in some countries, later in others).  The cutover
 * date may be changed by the caller by calling {@link
 * #setGregorianChange(Date) setGregorianChange()}.
 *
 * <p>
 * Historically, in those countries which adopted the Gregorian calendar first,
 * October 4, 1582 (Julian) was thus followed by October 15, 1582 (Gregorian). This calendar models
 * this correctly.  Before the Gregorian cutover, <code>GregorianCalendar</code>
 * implements the Julian calendar.  The only difference between the Gregorian
 * and the Julian calendar is the leap year rule. The Julian calendar specifies
 * leap years every four years, whereas the Gregorian calendar omits century
 * years which are not divisible by 400.
 *
 * <p>
 * <code>GregorianCalendar</code> implements <em>proleptic</em> Gregorian and
 * Julian calendars. That is, dates are computed by extrapolating the current
 * rules indefinitely far backward and forward in time. As a result,
 * <code>GregorianCalendar</code> may be used for all years to generate
 * meaningful and consistent results. However, dates obtained using
 * <code>GregorianCalendar</code> are historically accurate only from March 1, 4
 * AD onward, when modern Julian calendar rules were adopted.  Before this date,
 * leap year rules were applied irregularly, and before 45 BC the Julian
 * calendar did not even exist.
 *
 * <p>
 * Prior to the institution of the Gregorian calendar, New Year's Day was
 * March 25. To avoid confusion, this calendar always uses January 1. A manual
 * adjustment may be made if desired for dates that are prior to the Gregorian
 * changeover and which fall between January 1 and March 24.
 *
 * <h3><a name="week_and_year">Week Of Year and Week Year</a></h3>
 *
 * <p>Values calculated for the {@link Calendar#WEEK_OF_YEAR
 * WEEK_OF_YEAR} field range from 1 to 53. The first week of a
 * calendar year is the earliest seven day period starting on {@link
 * Calendar#getFirstDayOfWeek() getFirstDayOfWeek()} that contains at
 * least {@link Calendar#getMinimalDaysInFirstWeek()
 * getMinimalDaysInFirstWeek()} days from that year. It thus depends
 * on the values of {@code getMinimalDaysInFirstWeek()}, {@code
 * getFirstDayOfWeek()}, and the day of the week of January 1. Weeks
 * between week 1 of one year and week 1 of the following year
 * (exclusive) are numbered sequentially from 2 to 52 or 53 (except
 * for year(s) involved in the Julian-Gregorian transition).
 *
 * <p>The {@code getFirstDayOfWeek()} and {@code
 * getMinimalDaysInFirstWeek()} values are initialized using
 * locale-dependent resources when constructing a {@code
 * GregorianCalendar}. <a name="iso8601_compatible_setting">The week
 * determination is compatible</a> with the ISO 8601 standard when {@code
 * getFirstDayOfWeek()} is {@code MONDAY} and {@code
 * getMinimalDaysInFirstWeek()} is 4, which values are used in locales
 * where the standard is preferred. These values can explicitly be set by
 * calling {@link Calendar#setFirstDayOfWeek(int) setFirstDayOfWeek()} and
 * {@link Calendar#setMinimalDaysInFirstWeek(int)
 * setMinimalDaysInFirstWeek()}.
 *
 * <p>A <a name="week_year"><em>week year</em></a> is in sync with a
 * {@code WEEK_OF_YEAR} cycle. All weeks between the first and last
 * weeks (inclusive) have the same <em>week year</em> value.
 * Therefore, the first and last days of a week year may have
 * different calendar year values.
 *
 * <p>For example, January 1, 1998 is a Thursday. If {@code
 * getFirstDayOfWeek()} is {@code MONDAY} and {@code
 * getMinimalDaysInFirstWeek()} is 4 (ISO 8601 standard compatible
 * setting), then week 1 of 1998 starts on December 29, 1997, and ends
 * on January 4, 1998. The week year is 1998 for the last three days
 * of calendar year 1997. If, however, {@code getFirstDayOfWeek()} is
 * {@code SUNDAY}, then week 1 of 1998 starts on January 4, 1998, and
 * ends on January 10, 1998; the first three days of 1998 then are
 * part of week 53 of 1997 and their week year is 1997.
 *
 * <h4>Week Of Month</h4>
 *
 * <p>Values calculated for the <code>WEEK_OF_MONTH</code> field range from 0
 * to 6.  Week 1 of a month (the days with <code>WEEK_OF_MONTH =
 * 1</code>) is the earliest set of at least
 * <code>getMinimalDaysInFirstWeek()</code> contiguous days in that month,
 * ending on the day before <code>getFirstDayOfWeek()</code>.  Unlike
 * week 1 of a year, week 1 of a month may be shorter than 7 days, need
 * not start on <code>getFirstDayOfWeek()</code>, and will not include days of
 * the previous month.  Days of a month before week 1 have a
 * <code>WEEK_OF_MONTH</code> of 0.
 *
 * <p>For example, if <code>getFirstDayOfWeek()</code> is <code>SUNDAY</code>
 * and <code>getMinimalDaysInFirstWeek()</code> is 4, then the first week of
 * January 1998 is Sunday, January 4 through Saturday, January 10.  These days
 * have a <code>WEEK_OF_MONTH</code> of 1.  Thursday, January 1 through
 * Saturday, January 3 have a <code>WEEK_OF_MONTH</code> of 0.  If
 * <code>getMinimalDaysInFirstWeek()</code> is changed to 3, then January 1
 * through January 3 have a <code>WEEK_OF_MONTH</code> of 1.
 *
 * <h4>Default Fields Values</h4>
 *
 * <p>The <code>clear</code> method sets calendar field(s)
 * undefined. <code>GregorianCalendar</code> uses the following
 * default value for each calendar field if its value is undefined.
 *
 * <table cellpadding="0" cellspacing="3" border="0"
 *        summary="GregorianCalendar default field values"
 *        style="text-align: left; width: 66%;">
 *   <tbody>
 *     <tr>
 *       <th style="vertical-align: top; background-color: rgb(204, 204, 255);
 *           text-align: center;">Field<br>
 *       </th>
 *       <th style="vertical-align: top; background-color: rgb(204, 204, 255);
 *           text-align: center;">Default Value<br>
 *       </th>
 *     </tr>
 *     <tr>
 *       <td style="vertical-align: middle;">
 *              <code>ERA<br></code>
 *       </td>
 *       <td style="vertical-align: middle;">
 *              <code>AD<br></code>
 *       </td>
 *     </tr>
 *     <tr>
 *       <td style="vertical-align: middle; background-color: rgb(238, 238, 255);">
 *              <code>YEAR<br></code>
 *       </td>
 *       <td style="vertical-align: middle; background-color: rgb(238, 238, 255);">
 *              <code>1970<br></code>
 *       </td>
 *     </tr>
 *     <tr>
 *       <td style="vertical-align: middle;">
 *              <code>MONTH<br></code>
 *       </td>
 *       <td style="vertical-align: middle;">
 *              <code>JANUARY<br></code>
 *       </td>
 *     </tr>
 *     <tr>
 *       <td style="vertical-align: top; background-color: rgb(238, 238, 255);">
 *              <code>DAY_OF_MONTH<br></code>
 *       </td>
 *       <td style="vertical-align: top; background-color: rgb(238, 238, 255);">
 *              <code>1<br></code>
 *       </td>
 *     </tr>
 *     <tr>
 *       <td style="vertical-align: middle;">
 *              <code>DAY_OF_WEEK<br></code>
 *       </td>
 *       <td style="vertical-align: middle;">
 *              <code>the first day of week<br></code>
 *       </td>
 *     </tr>
 *     <tr>
 *       <td style="vertical-align: top; background-color: rgb(238, 238, 255);">
 *              <code>WEEK_OF_MONTH<br></code>
 *       </td>
 *       <td style="vertical-align: top; background-color: rgb(238, 238, 255);">
 *              <code>0<br></code>
 *       </td>
 *     </tr>
 *     <tr>
 *       <td style="vertical-align: top;">
 *              <code>DAY_OF_WEEK_IN_MONTH<br></code>
 *       </td>
 *       <td style="vertical-align: top;">
 *              <code>1<br></code>
 *       </td>
 *     </tr>
 *     <tr>
 *       <td style="vertical-align: middle; background-color: rgb(238, 238, 255);">
 *              <code>AM_PM<br></code>
 *       </td>
 *       <td style="vertical-align: middle; background-color: rgb(238, 238, 255);">
 *              <code>AM<br></code>
 *       </td>
 *     </tr>
 *     <tr>
 *       <td style="vertical-align: middle;">
 *              <code>HOUR, HOUR_OF_DAY, MINUTE, SECOND, MILLISECOND<br></code>
 *       </td>
 *       <td style="vertical-align: middle;">
 *              <code>0<br></code>
 *       </td>
 *     </tr>
 *   </tbody>
 * </table>
 * <br>Default values are not applicable for the fields not listed above.
 *
 * <p>
 * <strong>Example:</strong>
 * <blockquote>
 * <pre>
 * // get the supported ids for GMT-08:00 (Pacific Standard Time)
 * String[] ids = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000);
 * // if no ids were returned, something is wrong. get out.
 * if (ids.length == 0)
 *     System.exit(0);
 *
 *  // begin output
 * System.out.println("Current Time");
 *
 * // create a Pacific Standard Time time zone
 * SimpleTimeZone pdt = new SimpleTimeZone(-8 * 60 * 60 * 1000, ids[0]);
 *
 * // set up rules for Daylight Saving Time
 * pdt.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
 * pdt.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
 *
 * // create a GregorianCalendar with the Pacific Daylight time zone
 * // and the current date and time
 * Calendar calendar = new GregorianCalendar(pdt);
 * Date trialTime = new Date();
 * calendar.setTime(trialTime);
 *
 * // print out a bunch of interesting things
 * System.out.println("ERA: " + calendar.get(Calendar.ERA));
 * System.out.println("YEAR: " + calendar.get(Calendar.YEAR));
 * System.out.println("MONTH: " + calendar.get(Calendar.MONTH));
 * System.out.println("WEEK_OF_YEAR: " + calendar.get(Calendar.WEEK_OF_YEAR));
 * System.out.println("WEEK_OF_MONTH: " + calendar.get(Calendar.WEEK_OF_MONTH));
 * System.out.println("DATE: " + calendar.get(Calendar.DATE));
 * System.out.println("DAY_OF_MONTH: " + calendar.get(Calendar.DAY_OF_MONTH));
 * System.out.println("DAY_OF_YEAR: " + calendar.get(Calendar.DAY_OF_YEAR));
 * System.out.println("DAY_OF_WEEK: " + calendar.get(Calendar.DAY_OF_WEEK));
 * System.out.println("DAY_OF_WEEK_IN_MONTH: "
 *                    + calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));
 * System.out.println("AM_PM: " + calendar.get(Calendar.AM_PM));
 * System.out.println("HOUR: " + calendar.get(Calendar.HOUR));
 * System.out.println("HOUR_OF_DAY: " + calendar.get(Calendar.HOUR_OF_DAY));
 * System.out.println("MINUTE: " + calendar.get(Calendar.MINUTE));
 * System.out.println("SECOND: " + calendar.get(Calendar.SECOND));
 * System.out.println("MILLISECOND: " + calendar.get(Calendar.MILLISECOND));
 * System.out.println("ZONE_OFFSET: "
 *                    + (calendar.get(Calendar.ZONE_OFFSET)/(60*60*1000)));
 * System.out.println("DST_OFFSET: "
 *                    + (calendar.get(Calendar.DST_OFFSET)/(60*60*1000)));

 * System.out.println("Current Time, with hour reset to 3");
 * calendar.clear(Calendar.HOUR_OF_DAY); // so doesn't override
 * calendar.set(Calendar.HOUR, 3);
 * System.out.println("ERA: " + calendar.get(Calendar.ERA));
 * System.out.println("YEAR: " + calendar.get(Calendar.YEAR));
 * System.out.println("MONTH: " + calendar.get(Calendar.MONTH));
 * System.out.println("WEEK_OF_YEAR: " + calendar.get(Calendar.WEEK_OF_YEAR));
 * System.out.println("WEEK_OF_MONTH: " + calendar.get(Calendar.WEEK_OF_MONTH));
 * System.out.println("DATE: " + calendar.get(Calendar.DATE));
 * System.out.println("DAY_OF_MONTH: " + calendar.get(Calendar.DAY_OF_MONTH));
 * System.out.println("DAY_OF_YEAR: " + calendar.get(Calendar.DAY_OF_YEAR));
 * System.out.println("DAY_OF_WEEK: " + calendar.get(Calendar.DAY_OF_WEEK));
 * System.out.println("DAY_OF_WEEK_IN_MONTH: "
 *                    + calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));
 * System.out.println("AM_PM: " + calendar.get(Calendar.AM_PM));
 * System.out.println("HOUR: " + calendar.get(Calendar.HOUR));
 * System.out.println("HOUR_OF_DAY: " + calendar.get(Calendar.HOUR_OF_DAY));
 * System.out.println("MINUTE: " + calendar.get(Calendar.MINUTE));
 * System.out.println("SECOND: " + calendar.get(Calendar.SECOND));
 * System.out.println("MILLISECOND: " + calendar.get(Calendar.MILLISECOND));
 * System.out.println("ZONE_OFFSET: "
 *        + (calendar.get(Calendar.ZONE_OFFSET)/(60*60*1000))); // in hours
 * System.out.println("DST_OFFSET: "
 *        + (calendar.get(Calendar.DST_OFFSET)/(60*60*1000))); // in hours
 * </pre>
 * </blockquote>
 *
 * <p>
 * <code> GregorianCalendar </code>是<code> Calendar </code>的一个具体子类,提供了大多数世界使用的标准日历系统
 * 
 *  <p> <code> GregorianCalendar </code>是一个混合日历,支持Julian和Gregorian日历系统,并支持单个不连续,默认情况下对应于公历日历的公历日期(2010年1
 * 0月15日) 1582在一些国家,后来在其他)割接日期可能会改变由调用者通过调用{@link #setGregorianChange(Date)setGregorianChange()}。
 * 
 * <p>
 * 历史上,在那些采用公历日历的国家中,1582年10月4日(朱利安)之后是1582年10月15日(Gregorian)。
 * 这个日历模型正确在Gregorian切换之前,<code> GregorianCalendar </code>朱利安日历格里高利历和儒略历的唯一区别是闰年规则朱利安历法规定闰年每四年,而公历日历省略不能
 * 被400整除的世纪年代。
 * 历史上,在那些采用公历日历的国家中,1582年10月4日(朱利安)之后是1582年10月15日(Gregorian)。
 * 
 * <p>
 * <code> GregorianCalendar </code>实现<em> proleptian </em> Gregorian和Julian日历也就是说,通过无限远地向后和向前推断当前规则来计算日期
 * 结果,<code> GregorianCalendar </code >可以用于所有年份以产生有意义的和一致的结果然而,使用<code> GregorianCalendar </code>获得的日期历史
 * 上仅从公元4年3月1日起,当采用现代儒略历日历规则时准确在此日期之前,年规则被不规则地应用,在公元前45年,儒略历甚至不存在。
 * 
 * <p>
 * 在制定公历之前,元旦是3月25日为了避免混淆,这个日历总是使用1月1日。如果需要,可以在公历切换之前的日期,并且在1月1日和3月之间的日期进行手动调整24
 * 
 *  <h3> <a name=\"week_and_year\">年周年周</a> </h3>
 * 
 * <p>为{@link日历#WEEK_OF_YEAR WEEK_OF_YEAR}字段计算的值范围从1到53一个日历年的第一周是从{@link Calendar#getFirstDayOfWeek()getFirstDayOfWeek()}
 * 开始的最早的七天,其中包含至少{@link Calendar#getMinimalDaysInFirstWeek()getMinimalDaysInFirstWeek()}从那一年起它因此取决于{@code getMinimalDaysInFirstWeek()}
 * ,{@code getFirstDayOfWeek()}的值和1月1日的星期几一年的第1周和下一年的第1周(不包括)之间的周数从2到52或53(从朱利安 - 格里高利转变中涉及的年份除外)。
 * 
 * <p>构建{@code GregorianCalendar} <a name=\"iso8601_compatible_setting\">星期确定是兼容的时,使用与区域设置相关的资源来初始化{@code getFirstDayOfWeek()}
 * 和{@code getMinimalDaysInFirstWeek当{@code getFirstDayOfWeek()}为{@code MONDAY}且{@code getMinimalDaysInFirstWeek()}
 * 为4时,使用ISO 8601标准,其中的值在标准为首选的区域设置中使用这些值可以显式地通过调用{@link Calendar#setFirstDayOfWeek(int)setFirstDayOfWeek()}
 * 和{@link Calendar#setMinimalDaysInFirstWeek(int)setMinimalDaysInFirstWeek()}。
 * 
 * <p> <a name=\"week_year\"> <em>周年</em> </a>与{@code WEEK_OF_YEAR}周期同步第一周和最后一周之间的所有周都包含相同的<em>周年</em>值因
 * 此,一周年的第一天和最后几天可能有不同的日历年值。
 * 
 * <p>例如,1998年1月1日是星期四如果{@code getFirstDayOfWeek()}为{@code MONDAY},{@code getMinimalDaysInFirstWeek()}为4
 * (ISO 8601标准兼容设置),则1998年第1周从1997年12月29日开始,到1998年1月4日结束1997年的最后三天的周年是1998年然而,{@code getFirstDayOfWeek()}
 * 是{@code SUNDAY},那么第1周1998年1月4日开始,1998年1月10日结束; 1998年头三天是1997年第53周的一部分,其周年是1997年。
 * 
 *  <h4>每周几周</h4>
 * 
 * <p>为<code> WEEK_OF_MONTH </code>字段计算的值范围为0到6个月的第1周(具有<code> WEEK_OF_MONTH = 1 </code>的天)是最早的一组,至少<code >
 *  getMinimalDaysInFirstWeek()</code>该月中的连续日期,结束于<code> getFirstDayOfWeek()</code>与一年中的第1周不同,一个月的第1周可能会
 * 少于7天,从<code> getFirstDayOfWeek()</code>开始,并且不包括上一个月的天数。
 * 第1周之前的某个月的天数有<code> WEEK_OF_MONTH </code>为0。
 * 
 * <p>例如,如果<code> getFirstDayOfWeek()</code>是<code> SUNDAY </code>和<code> getMinimalDaysInFirstWeek()</code>
 * 为4,那么1998年1月的第一周是星期天, 1月4日至1月10日星期六这些日子有<code> WEEK_OF_MONTH </code> 1月1日星期四至1月3日星期六的<code> WEEK_OF_M
 * ONTH </code>为0如果<code> getMinimalDaysInFirstWeek / code>更改为3,则1月1日到1月3日的<code> WEEK_OF_MONTH </code>为
 * 1。
 * 
 *  <h4>默认字段值</h4>
 * 
 *  <p> <code> clear </code>方法设置日历字段未定义<code> GregorianCalendar </code>如果每个日历字段的值未定义,则会对其使用以下默认值
 * 
 * <table cellpadding ="0"cellspacing ="3"border ="0"summary ="GregorianCalendar default field values"
 * style="text-align: left; width: 66%;">
 * <tbody>
 * <tr>
 *  <th style ="vertical-align：top; background-color：rgb(204,204,255); text-align：center;">字段<br>
 * </th>
 *  <th style ="vertical-align：top; background-color：rgb(204,204,255); text-align：center;">默认值<br>
 * </th>
 * </tr>
 * <tr>
 * <td style="vertical-align: middle;">
 *  <code> ERA <br> </code>
 * </td>
 * <td style="vertical-align: middle;">
 *  <code> AD <br> </code>
 * </td>
 * </tr>
 * <tr>
 * <td style="vertical-align: middle; background-color: rgb(238, 238, 255);">
 *  <code> YEAR <br> </code>
 * </td>
 * <td style="vertical-align: middle; background-color: rgb(238, 238, 255);">
 *  <code> 1970 <br> </code>
 * </td>
 * </tr>
 * <tr>
 * <td style="vertical-align: middle;">
 *  <code> MONTH <br> </code>
 * </td>
 * <td style="vertical-align: middle;">
 *  <code> JANUARY <br> </code>
 * </td>
 * </tr>
 * <tr>
 * <td style="vertical-align: top; background-color: rgb(238, 238, 255);">
 *  <code> DAY_OF_MONTH <br> </code>
 * </td>
 * <td style="vertical-align: top; background-color: rgb(238, 238, 255);">
 *  <code> 1 <br> </code>
 * </td>
 * </tr>
 * <tr>
 * <td style="vertical-align: middle;">
 *  <code> DAY_OF_WEEK <br> </code>
 * </td>
 * <td style="vertical-align: middle;">
 *  <code>周的第一天<br> </code>
 * </td>
 * </tr>
 * <tr>
 * <td style="vertical-align: top; background-color: rgb(238, 238, 255);">
 *  <code> WEEK_OF_MONTH <br> </code>
 * </td>
 * <td style="vertical-align: top; background-color: rgb(238, 238, 255);">
 *  <code> 0 <br> </code>
 * </td>
 * </tr>
 * <tr>
 * <td style="vertical-align: top;">
 *  <code> DAY_OF_WEEK_IN_MONTH <br> </code>
 * </td>
 * <td style="vertical-align: top;">
 *  <code> 1 <br> </code>
 * </td>
 * </tr>
 * <tr>
 * <td style="vertical-align: middle; background-color: rgb(238, 238, 255);">
 *  <code> AM_PM <br> </code>
 * </td>
 * <td style="vertical-align: middle; background-color: rgb(238, 238, 255);">
 *  <code> AM <br> </code>
 * </td>
 * </tr>
 * <tr>
 * <td style="vertical-align: middle;">
 *  <code> HOUR,HOUR_OF_DAY,MINUTE,SECOND,MILLISECOND <br> </code>
 * </td>
 * <td style="vertical-align: middle;">
 * <code> 0 <br> </code>
 * </td>
 * </tr>
 * </tbody>
 * </table>
 *  <br>默认值不适用于上面未列出的字段
 * 
 * <p>
 *  <strong>示例：</strong>
 * <blockquote>
 * <pre>
 *  //获取GMT-08：00(太平洋标准时间)的支持的ID。
 * String [] ids = TimeZonegetAvailableIDs(-8 * 60 * 60 * 1000); //如果没有返回ids,则会出错if(idslength == 0)Syste
 * mexit(0);。
 *  //获取GMT-08：00(太平洋标准时间)的支持的ID。
 * 
 *  // begin output Systemoutprintln("Current Time");
 * 
 *  //创建太平洋标准时间时区SimpleTimeZone pdt = new SimpleTimeZone(-8 * 60 * 60 * 1000,ids [0]);
 * 
 *  //为夏令时设置规则pdtsetStartRule(CalendarAPRIL,1,CalendarSUNDAY,2 * 60 * 60 * 1000); pdtsetEndRule(Calendar
 * OCTOBER,-1,CalendarSUNDAY,2 * 60 * 60 * 1000);。
 * 
 * //创建一个带太平洋夏令时区域的GregorianCalendar //和当前日期和时间Calendar calendar = new GregorianCalendar(pdt); Date tria
 * lTime = new Date(); calendarsetTime(trialTime);。
 * 
 * println("HOUR："+ calendarget(CalendarHOUR)); Systemoutprintln("HOUR_OF_DAY："+ calendarget(CalendarHOU
 * R_OF_DAY)); Systemoutprintln("MINUTE："+ calendarget(CalendarMINUTE)); Systemoutprintln("SECOND："+ cal
 * endarget(CalendarSECOND)); Systemoutprintln("MILLISECOND："+ calendarget(CalendarMILLISECOND)); System
 * outprintln("ZONE_OFFSET："+(calendarget(CalendarZONE_OFFSET)/(60 * 60 * 1000))); Systemoutprintln("DST
 * _OFFSET："+(calendarget(CalendarDST_OFFSET)/(60 * 60 * 1000)));。
 * 
 * Systemoutprintln("当前时间,小时重置为3"); calendarclear(CalendarHOUR_OF_DAY); //所以不重写日历(CalendarHOUR,3); Syste
 * moutprintln("ERA："+ calendarget(CalendarERA)); Systemoutprintln("YEAR："+ calendarget(CalendarYEAR)); 
 * Systemoutprintln("MONTH："+ calendarget(CalendarMONTH)); Systemoutprintln("WEEK_OF_YEAR："+ calendarget
 * (CalendarWEEK_OF_YEAR)); Systemoutprintln("WEEK_OF_MONTH："+ calendarget(CalendarWEEK_OF_MONTH)); Syst
 * emoutprintln("DATE："+ calendarget(CalendarDATE)); Systemoutprintln("DAY_OF_MONTH："+ calendarget(Calen
 * darDAY_OF_MONTH)); Systemoutprintln("DAY_OF_YEAR："+ calendarget(CalendarDAY_OF_YEAR)); Systemoutprint
 * ln("DAY_OF_WEEK："+ calendarget(CalendarDAY_OF_WEEK)); Systemoutprintln("DAY_OF_WEEK_IN_MONTH："+ calen
 * darget(CalendarDAY_OF_WEEK_IN_MONTH)); Systemoutprintln("AM_PM："+ calendarget(CalendarAM_PM)); System
 * outprintln("HOUR："+ calendarget(CalendarHOUR)); Systemoutprintln("HOUR_OF_DAY："+ calendarget(Calendar
 * HOUR_OF_DAY)); Systemoutprintln("MINUTE："+ calendarget(CalendarMINUTE)); Systemoutprintln("SECOND："+ 
 * calendarget(CalendarSECOND)); Systemoutprintln("MILLISECOND："+ calendarget(CalendarMILLISECOND)); Sys
 * temoutprintln("ZONE_OFFSET："+(calendarget(CalendarZONE_OFFSET)/(60 * 60 * 1000))); //以小时为单位Systemoutp
 * rintln("DST_OFFSET："+(calendarget(CalendarDST_OFFSET)/(60 * 60 * 1000))); //以小时为单位。
 * </pre>
 * </blockquote>
 * 
 * 
 * @see          TimeZone
 * @author David Goldsmith, Mark Davis, Chen-Lieh Huang, Alan Liu
 * @since JDK1.1
 */
public class GregorianCalendar extends Calendar {
    /*
     * Implementation Notes
     *
     * The epoch is the number of days or milliseconds from some defined
     * starting point. The epoch for java.util.Date is used here; that is,
     * milliseconds from January 1, 1970 (Gregorian), midnight UTC.  Other
     * epochs which are used are January 1, year 1 (Gregorian), which is day 1
     * of the Gregorian calendar, and December 30, year 0 (Gregorian), which is
     * day 1 of the Julian calendar.
     *
     * We implement the proleptic Julian and Gregorian calendars.  This means we
     * implement the modern definition of the calendar even though the
     * historical usage differs.  For example, if the Gregorian change is set
     * to new Date(Long.MIN_VALUE), we have a pure Gregorian calendar which
     * labels dates preceding the invention of the Gregorian calendar in 1582 as
     * if the calendar existed then.
     *
     * Likewise, with the Julian calendar, we assume a consistent
     * 4-year leap year rule, even though the historical pattern of
     * leap years is irregular, being every 3 years from 45 BCE
     * through 9 BCE, then every 4 years from 8 CE onwards, with no
     * leap years in-between.  Thus date computations and functions
     * such as isLeapYear() are not intended to be historically
     * accurate.
     * <p>
     * 实现注释
     * 
     *  时代是从某个定义的起始点开始的天数或毫秒数此处使用javautilDate的时期;即从1970年1月1日(格雷戈里安)到午夜UTC的毫秒数。
     * 使用的其他时期是1月1日,第1年(格里高利亚),这是公历日历的第1天和12月30日,第0年(格里高利亚)是儒略历的第1天。
     * 
     *  我们实现proleptian朱利安和格里高利历日历这意味着我们实现日历的现代定义,即使历史使用不同例如,如果格雷戈里亚改变设置为新的日期(LongMIN_VALUE),我们有一个纯粹的公历日历标记日期
     * 之前在1582年的公历日历的发明,好像日历存在了。
     * 
     * 同样,对于朱利安日历,我们假设一个连续的4年闰年规则,即使闰年的历史模式是不规则的,每3年从公元前45年至公元前9年,然后每4年从公元前8年,没有闰年之间因此日期计算和函数,如isLeapYear()不
     * 打算历史上准确。
     * 
     */

//////////////////
// Class Variables
//////////////////

    /**
     * Value of the <code>ERA</code> field indicating
     * the period before the common era (before Christ), also known as BCE.
     * The sequence of years at the transition from <code>BC</code> to <code>AD</code> is
     * ..., 2 BC, 1 BC, 1 AD, 2 AD,...
     *
     * <p>
     *  <code> ERA </code>字段的值指示在公共时代之前(在基督之前)的时期,也称为BCE从<code> BC </code>到<code> AD < / code>是,2 BC,1 BC,1 
     * AD,2 AD,。
     * 
     * 
     * @see #ERA
     */
    public static final int BC = 0;

    /**
     * Value of the {@link #ERA} field indicating
     * the period before the common era, the same value as {@link #BC}.
     *
     * <p>
     *  指示通用时代前的时间段的{@link #ERA}字段的值,与{@link #BC}
     * 
     * 
     * @see #CE
     */
    static final int BCE = 0;

    /**
     * Value of the <code>ERA</code> field indicating
     * the common era (Anno Domini), also known as CE.
     * The sequence of years at the transition from <code>BC</code> to <code>AD</code> is
     * ..., 2 BC, 1 BC, 1 AD, 2 AD,...
     *
     * <p>
     * 表示公共时代(Anno Domini)的<code> ERA </code>字段的值,也称为CE在从<code> BC </code>到<code> AD </code>是,2 BC,1 BC,1 AD
     * ,2 AD,。
     * 
     * 
     * @see #ERA
     */
    public static final int AD = 1;

    /**
     * Value of the {@link #ERA} field indicating
     * the common era, the same value as {@link #AD}.
     *
     * <p>
     *  指示公共年龄的{@link #ERA}字段的值,与{@link #AD}的值相同
     * 
     * 
     * @see #BCE
     */
    static final int CE = 1;

    private static final int EPOCH_OFFSET   = 719163; // Fixed date of January 1, 1970 (Gregorian)
    private static final int EPOCH_YEAR     = 1970;

    static final int MONTH_LENGTH[]
        = {31,28,31,30,31,30,31,31,30,31,30,31}; // 0-based
    static final int LEAP_MONTH_LENGTH[]
        = {31,29,31,30,31,30,31,31,30,31,30,31}; // 0-based

    // Useful millisecond constants.  Although ONE_DAY and ONE_WEEK can fit
    // into ints, they must be longs in order to prevent arithmetic overflow
    // when performing (bug 4173516).
    private static final int  ONE_SECOND = 1000;
    private static final int  ONE_MINUTE = 60*ONE_SECOND;
    private static final int  ONE_HOUR   = 60*ONE_MINUTE;
    private static final long ONE_DAY    = 24*ONE_HOUR;
    private static final long ONE_WEEK   = 7*ONE_DAY;

    /*
     * <pre>
     *                            Greatest       Least
     * Field name        Minimum   Minimum     Maximum     Maximum
     * ----------        -------   -------     -------     -------
     * ERA                     0         0           1           1
     * YEAR                    1         1   292269054   292278994
     * MONTH                   0         0          11          11
     * WEEK_OF_YEAR            1         1          52*         53
     * WEEK_OF_MONTH           0         0           4*          6
     * DAY_OF_MONTH            1         1          28*         31
     * DAY_OF_YEAR             1         1         365*        366
     * DAY_OF_WEEK             1         1           7           7
     * DAY_OF_WEEK_IN_MONTH   -1        -1           4*          6
     * AM_PM                   0         0           1           1
     * HOUR                    0         0          11          11
     * HOUR_OF_DAY             0         0          23          23
     * MINUTE                  0         0          59          59
     * SECOND                  0         0          59          59
     * MILLISECOND             0         0         999         999
     * ZONE_OFFSET        -13:00    -13:00       14:00       14:00
     * DST_OFFSET           0:00      0:00        0:20        2:00
     * </pre>
     * *: depends on the Gregorian change date
     * <p>
     * <pre>
     * 最大最小字段名称最小最小最大最大---------- ------- ------- ------- ------- ERA 0 0 1 1年1 1 292269054 292278994 MONTH 
     * 0 0 11 11 WEEK_OF_YEAR 1 1 52 * 53 WEEK_OF_MONTH 0 0 4 * 6 DAY_OF_MONTH 1 1 28 * 31 DAY_OF_YEAR 1 1 3
     * 65 * 366 DAY_OF_WEEK 1 1 7 7 DAY_OF_WEEK_IN_MONTH -1 -1 4 * 6 AM_PM 0 0 1 1 HOUR 0 0 11 11 HOUR_OF_DA
     * Y 0 0 23 23 MINUTE 0 0 59 59 SECOND 0 0 59 59 MILLISECOND 0 0 999 999 ZONE_OFFSET -13：00 -13：00 14:00
     *  14:00 DST_OFFSET 0:00 0:00 0:20 2:00。
     * </pre>
     * *：取决于公历变更日期
     * 
     */
    static final int MIN_VALUES[] = {
        BCE,            // ERA
        1,              // YEAR
        JANUARY,        // MONTH
        1,              // WEEK_OF_YEAR
        0,              // WEEK_OF_MONTH
        1,              // DAY_OF_MONTH
        1,              // DAY_OF_YEAR
        SUNDAY,         // DAY_OF_WEEK
        1,              // DAY_OF_WEEK_IN_MONTH
        AM,             // AM_PM
        0,              // HOUR
        0,              // HOUR_OF_DAY
        0,              // MINUTE
        0,              // SECOND
        0,              // MILLISECOND
        -13*ONE_HOUR,   // ZONE_OFFSET (UNIX compatibility)
        0               // DST_OFFSET
    };
    static final int LEAST_MAX_VALUES[] = {
        CE,             // ERA
        292269054,      // YEAR
        DECEMBER,       // MONTH
        52,             // WEEK_OF_YEAR
        4,              // WEEK_OF_MONTH
        28,             // DAY_OF_MONTH
        365,            // DAY_OF_YEAR
        SATURDAY,       // DAY_OF_WEEK
        4,              // DAY_OF_WEEK_IN
        PM,             // AM_PM
        11,             // HOUR
        23,             // HOUR_OF_DAY
        59,             // MINUTE
        59,             // SECOND
        999,            // MILLISECOND
        14*ONE_HOUR,    // ZONE_OFFSET
        20*ONE_MINUTE   // DST_OFFSET (historical least maximum)
    };
    static final int MAX_VALUES[] = {
        CE,             // ERA
        292278994,      // YEAR
        DECEMBER,       // MONTH
        53,             // WEEK_OF_YEAR
        6,              // WEEK_OF_MONTH
        31,             // DAY_OF_MONTH
        366,            // DAY_OF_YEAR
        SATURDAY,       // DAY_OF_WEEK
        6,              // DAY_OF_WEEK_IN
        PM,             // AM_PM
        11,             // HOUR
        23,             // HOUR_OF_DAY
        59,             // MINUTE
        59,             // SECOND
        999,            // MILLISECOND
        14*ONE_HOUR,    // ZONE_OFFSET
        2*ONE_HOUR      // DST_OFFSET (double summer time)
    };

    // Proclaim serialization compatibility with JDK 1.1
    @SuppressWarnings("FieldNameHidesFieldInSuperclass")
    static final long serialVersionUID = -8125100834729963327L;

    // Reference to the sun.util.calendar.Gregorian instance (singleton).
    private static final Gregorian gcal =
                                CalendarSystem.getGregorianCalendar();

    // Reference to the JulianCalendar instance (singleton), set as needed. See
    // getJulianCalendarSystem().
    private static JulianCalendar jcal;

    // JulianCalendar eras. See getJulianCalendarSystem().
    private static Era[] jeras;

    // The default value of gregorianCutover.
    static final long DEFAULT_GREGORIAN_CUTOVER = -12219292800000L;

/////////////////////
// Instance Variables
/////////////////////

    /**
     * The point at which the Gregorian calendar rules are used, measured in
     * milliseconds from the standard epoch.  Default is October 15, 1582
     * (Gregorian) 00:00:00 UTC or -12219292800000L.  For this value, October 4,
     * 1582 (Julian) is followed by October 15, 1582 (Gregorian).  This
     * corresponds to Julian day number 2299161.
     * <p>
     *  使用公历日历规则的点,以毫秒为单位从标准时期测量默认为1582年10月15日(格里高利亚)00:00:00 UTC或-12219292800000L对于此值,1582年10月4日(朱利安) 1582年
     * 10月15日(格里高利亚)这对应于儒略日数2299161。
     * 
     * 
     * @serial
     */
    private long gregorianCutover = DEFAULT_GREGORIAN_CUTOVER;

    /**
     * The fixed date of the gregorianCutover.
     * <p>
     *  gregorianCutover的固定日期
     * 
     */
    private transient long gregorianCutoverDate =
        (((DEFAULT_GREGORIAN_CUTOVER + 1)/ONE_DAY) - 1) + EPOCH_OFFSET; // == 577736

    /**
     * The normalized year of the gregorianCutover in Gregorian, with
     * 0 representing 1 BCE, -1 representing 2 BCE, etc.
     * <p>
     *  格雷戈里亚的gregorianCutover的归一化年份,0代表1 BCE,-1代表2 BCE等
     * 
     */
    private transient int gregorianCutoverYear = 1582;

    /**
     * The normalized year of the gregorianCutover in Julian, with 0
     * representing 1 BCE, -1 representing 2 BCE, etc.
     * <p>
     *  Julian中gregorianCutover的归一化年份,0代表1 BCE,-1代表2 BCE等
     * 
     */
    private transient int gregorianCutoverYearJulian = 1582;

    /**
     * gdate always has a sun.util.calendar.Gregorian.Date instance to
     * avoid overhead of creating it. The assumption is that most
     * applications will need only Gregorian calendar calculations.
     * <p>
     * gdate总是有一个sunutilcalendarGregorianDate实例,以避免创建它的开销假设是大多数应用程序只需要Gregorian日历计算
     * 
     */
    private transient BaseCalendar.Date gdate;

    /**
     * Reference to either gdate or a JulianCalendar.Date
     * instance. After calling complete(), this value is guaranteed to
     * be set.
     * <p>
     *  引用gdate或JulianCalendarDate实例调用complete()后,将保证设置此值
     * 
     */
    private transient BaseCalendar.Date cdate;

    /**
     * The CalendarSystem used to calculate the date in cdate. After
     * calling complete(), this value is guaranteed to be set and
     * consistent with the cdate value.
     * <p>
     *  用于在cdate中计算日期的CalendarSystem在调用complete()之后,此值将保证设置并与cdate值一致
     * 
     */
    private transient BaseCalendar calsys;

    /**
     * Temporary int[2] to get time zone offsets. zoneOffsets[0] gets
     * the GMT offset value and zoneOffsets[1] gets the DST saving
     * value.
     * <p>
     *  临时int [2]获取时区偏移zoneOffsets [0]获取GMT偏移值,zoneOffsets [1]获取DST保存值
     * 
     */
    private transient int[] zoneOffsets;

    /**
     * Temporary storage for saving original fields[] values in
     * non-lenient mode.
     * <p>
     *  用于在非宽松模式下保存原始字段[]值的临时存储
     * 
     */
    private transient int[] originalFields;

///////////////
// Constructors
///////////////

    /**
     * Constructs a default <code>GregorianCalendar</code> using the current time
     * in the default time zone with the default
     * {@link Locale.Category#FORMAT FORMAT} locale.
     * <p>
     * 使用默认时区中的当前时间使用默认的{@link LocaleCategory#FORMAT FORMAT}语言环境构造默认<code> GregorianCalendar </code>
     * 
     */
    public GregorianCalendar() {
        this(TimeZone.getDefaultRef(), Locale.getDefault(Locale.Category.FORMAT));
        setZoneShared(true);
    }

    /**
     * Constructs a <code>GregorianCalendar</code> based on the current time
     * in the given time zone with the default
     * {@link Locale.Category#FORMAT FORMAT} locale.
     *
     * <p>
     *  根据指定时区中的当前时间使用默认{@link LocaleCategory#FORMAT FORMAT}语言环境构造<code> GregorianCalendar </code>
     * 
     * 
     * @param zone the given time zone.
     */
    public GregorianCalendar(TimeZone zone) {
        this(zone, Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Constructs a <code>GregorianCalendar</code> based on the current time
     * in the default time zone with the given locale.
     *
     * <p>
     *  根据具有给定语言环境的默认时区中的当前时间构造<code> GregorianCalendar </code>
     * 
     * 
     * @param aLocale the given locale.
     */
    public GregorianCalendar(Locale aLocale) {
        this(TimeZone.getDefaultRef(), aLocale);
        setZoneShared(true);
    }

    /**
     * Constructs a <code>GregorianCalendar</code> based on the current time
     * in the given time zone with the given locale.
     *
     * <p>
     *  根据给定时区中具有给定语言环境的当前时间构造一个<code> GregorianCalendar </code>
     * 
     * 
     * @param zone the given time zone.
     * @param aLocale the given locale.
     */
    public GregorianCalendar(TimeZone zone, Locale aLocale) {
        super(zone, aLocale);
        gdate = (BaseCalendar.Date) gcal.newCalendarDate(zone);
        setTimeInMillis(System.currentTimeMillis());
    }

    /**
     * Constructs a <code>GregorianCalendar</code> with the given date set
     * in the default time zone with the default locale.
     *
     * <p>
     *  构造一个<code> GregorianCalendar </code>,其中默认时区中使用默认语言环境设置给定日期
     * 
     * 
     * @param year the value used to set the <code>YEAR</code> calendar field in the calendar.
     * @param month the value used to set the <code>MONTH</code> calendar field in the calendar.
     * Month value is 0-based. e.g., 0 for January.
     * @param dayOfMonth the value used to set the <code>DAY_OF_MONTH</code> calendar field in the calendar.
     */
    public GregorianCalendar(int year, int month, int dayOfMonth) {
        this(year, month, dayOfMonth, 0, 0, 0, 0);
    }

    /**
     * Constructs a <code>GregorianCalendar</code> with the given date
     * and time set for the default time zone with the default locale.
     *
     * <p>
     * 构造具有为默认时区设置的给定日期和时间的<code> GregorianCalendar </code>,默认时区使用默认语言环境
     * 
     * 
     * @param year the value used to set the <code>YEAR</code> calendar field in the calendar.
     * @param month the value used to set the <code>MONTH</code> calendar field in the calendar.
     * Month value is 0-based. e.g., 0 for January.
     * @param dayOfMonth the value used to set the <code>DAY_OF_MONTH</code> calendar field in the calendar.
     * @param hourOfDay the value used to set the <code>HOUR_OF_DAY</code> calendar field
     * in the calendar.
     * @param minute the value used to set the <code>MINUTE</code> calendar field
     * in the calendar.
     */
    public GregorianCalendar(int year, int month, int dayOfMonth, int hourOfDay,
                             int minute) {
        this(year, month, dayOfMonth, hourOfDay, minute, 0, 0);
    }

    /**
     * Constructs a GregorianCalendar with the given date
     * and time set for the default time zone with the default locale.
     *
     * <p>
     *  构造GregorianCalendar,使用默认语言环境为默认时区设置的给定日期和时间
     * 
     * 
     * @param year the value used to set the <code>YEAR</code> calendar field in the calendar.
     * @param month the value used to set the <code>MONTH</code> calendar field in the calendar.
     * Month value is 0-based. e.g., 0 for January.
     * @param dayOfMonth the value used to set the <code>DAY_OF_MONTH</code> calendar field in the calendar.
     * @param hourOfDay the value used to set the <code>HOUR_OF_DAY</code> calendar field
     * in the calendar.
     * @param minute the value used to set the <code>MINUTE</code> calendar field
     * in the calendar.
     * @param second the value used to set the <code>SECOND</code> calendar field
     * in the calendar.
     */
    public GregorianCalendar(int year, int month, int dayOfMonth, int hourOfDay,
                             int minute, int second) {
        this(year, month, dayOfMonth, hourOfDay, minute, second, 0);
    }

    /**
     * Constructs a <code>GregorianCalendar</code> with the given date
     * and time set for the default time zone with the default locale.
     *
     * <p>
     *  构造具有为默认时区设置的给定日期和时间的<code> GregorianCalendar </code>,默认时区使用默认语言环境
     * 
     * 
     * @param year the value used to set the <code>YEAR</code> calendar field in the calendar.
     * @param month the value used to set the <code>MONTH</code> calendar field in the calendar.
     * Month value is 0-based. e.g., 0 for January.
     * @param dayOfMonth the value used to set the <code>DAY_OF_MONTH</code> calendar field in the calendar.
     * @param hourOfDay the value used to set the <code>HOUR_OF_DAY</code> calendar field
     * in the calendar.
     * @param minute the value used to set the <code>MINUTE</code> calendar field
     * in the calendar.
     * @param second the value used to set the <code>SECOND</code> calendar field
     * in the calendar.
     * @param millis the value used to set the <code>MILLISECOND</code> calendar field
     */
    GregorianCalendar(int year, int month, int dayOfMonth,
                      int hourOfDay, int minute, int second, int millis) {
        super();
        gdate = (BaseCalendar.Date) gcal.newCalendarDate(getZone());
        this.set(YEAR, year);
        this.set(MONTH, month);
        this.set(DAY_OF_MONTH, dayOfMonth);

        // Set AM_PM and HOUR here to set their stamp values before
        // setting HOUR_OF_DAY (6178071).
        if (hourOfDay >= 12 && hourOfDay <= 23) {
            // If hourOfDay is a valid PM hour, set the correct PM values
            // so that it won't throw an exception in case it's set to
            // non-lenient later.
            this.internalSet(AM_PM, PM);
            this.internalSet(HOUR, hourOfDay - 12);
        } else {
            // The default value for AM_PM is AM.
            // We don't care any out of range value here for leniency.
            this.internalSet(HOUR, hourOfDay);
        }
        // The stamp values of AM_PM and HOUR must be COMPUTED. (6440854)
        setFieldsComputed(HOUR_MASK|AM_PM_MASK);

        this.set(HOUR_OF_DAY, hourOfDay);
        this.set(MINUTE, minute);
        this.set(SECOND, second);
        // should be changed to set() when this constructor is made
        // public.
        this.internalSet(MILLISECOND, millis);
    }

    /**
     * Constructs an empty GregorianCalendar.
     *
     * <p>
     *  构造一个空的GregorianCalendar
     * 
     * 
     * @param zone    the given time zone
     * @param aLocale the given locale
     * @param flag    the flag requesting an empty instance
     */
    GregorianCalendar(TimeZone zone, Locale locale, boolean flag) {
        super(zone, locale);
        gdate = (BaseCalendar.Date) gcal.newCalendarDate(getZone());
    }

/////////////////
// Public methods
/////////////////

    /**
     * Sets the <code>GregorianCalendar</code> change date. This is the point when the switch
     * from Julian dates to Gregorian dates occurred. Default is October 15,
     * 1582 (Gregorian). Previous to this, dates will be in the Julian calendar.
     * <p>
     * To obtain a pure Julian calendar, set the change date to
     * <code>Date(Long.MAX_VALUE)</code>.  To obtain a pure Gregorian calendar,
     * set the change date to <code>Date(Long.MIN_VALUE)</code>.
     *
     * <p>
     *  设置<code> GregorianCalendar </code>更改日期这是从Julian日期切换到Gregorian日期的时间点默认值是1582年10月15日(Gregorian)在此之前,日期
     * 将在儒略历。
     * <p>
     * 要获取纯朱利安日历,请将更改日期设置为<code> Date(LongMAX_VALUE)</code>要获取纯Gregorian日历,请将更改日期设置为<code> Date(LongMIN_VALU
     * E)</code>。
     * 
     * 
     * @param date the given Gregorian cutover date.
     */
    public void setGregorianChange(Date date) {
        long cutoverTime = date.getTime();
        if (cutoverTime == gregorianCutover) {
            return;
        }
        // Before changing the cutover date, make sure to have the
        // time of this calendar.
        complete();
        setGregorianChange(cutoverTime);
    }

    private void setGregorianChange(long cutoverTime) {
        gregorianCutover = cutoverTime;
        gregorianCutoverDate = CalendarUtils.floorDivide(cutoverTime, ONE_DAY)
                                + EPOCH_OFFSET;

        // To provide the "pure" Julian calendar as advertised.
        // Strictly speaking, the last millisecond should be a
        // Gregorian date. However, the API doc specifies that setting
        // the cutover date to Long.MAX_VALUE will make this calendar
        // a pure Julian calendar. (See 4167995)
        if (cutoverTime == Long.MAX_VALUE) {
            gregorianCutoverDate++;
        }

        BaseCalendar.Date d = getGregorianCutoverDate();

        // Set the cutover year (in the Gregorian year numbering)
        gregorianCutoverYear = d.getYear();

        BaseCalendar julianCal = getJulianCalendarSystem();
        d = (BaseCalendar.Date) julianCal.newCalendarDate(TimeZone.NO_TIMEZONE);
        julianCal.getCalendarDateFromFixedDate(d, gregorianCutoverDate - 1);
        gregorianCutoverYearJulian = d.getNormalizedYear();

        if (time < gregorianCutover) {
            // The field values are no longer valid under the new
            // cutover date.
            setUnnormalized();
        }
    }

    /**
     * Gets the Gregorian Calendar change date.  This is the point when the
     * switch from Julian dates to Gregorian dates occurred. Default is
     * October 15, 1582 (Gregorian). Previous to this, dates will be in the Julian
     * calendar.
     *
     * <p>
     *  获取Gregorian日历更改日期这是从Julian日期切换到Gregorian日期的时间点缺省为1582年10月15日(Gregorian)在此之前,日期将在儒略历
     * 
     * 
     * @return the Gregorian cutover date for this <code>GregorianCalendar</code> object.
     */
    public final Date getGregorianChange() {
        return new Date(gregorianCutover);
    }

    /**
     * Determines if the given year is a leap year. Returns <code>true</code> if
     * the given year is a leap year. To specify BC year numbers,
     * <code>1 - year number</code> must be given. For example, year BC 4 is
     * specified as -3.
     *
     * <p>
     *  确定给定年份是否为闰年如果给定年份是闰年,则返回<code> true </code>若要指定BC年份数字,必须给定<code> 1年数字</code>例如,年BC 4被指定为-3
     * 
     * 
     * @param year the given year.
     * @return <code>true</code> if the given year is a leap year; <code>false</code> otherwise.
     */
    public boolean isLeapYear(int year) {
        if ((year & 3) != 0) {
            return false;
        }

        if (year > gregorianCutoverYear) {
            return (year%100 != 0) || (year%400 == 0); // Gregorian
        }
        if (year < gregorianCutoverYearJulian) {
            return true; // Julian
        }
        boolean gregorian;
        // If the given year is the Gregorian cutover year, we need to
        // determine which calendar system to be applied to February in the year.
        if (gregorianCutoverYear == gregorianCutoverYearJulian) {
            BaseCalendar.Date d = getCalendarDate(gregorianCutoverDate); // Gregorian
            gregorian = d.getMonth() < BaseCalendar.MARCH;
        } else {
            gregorian = year == gregorianCutoverYear;
        }
        return gregorian ? (year%100 != 0) || (year%400 == 0) : true;
    }

    /**
     * Returns {@code "gregory"} as the calendar type.
     *
     * <p>
     *  返回{@code"gregory"}作为日历类型
     * 
     * 
     * @return {@code "gregory"}
     * @since 1.8
     */
    @Override
    public String getCalendarType() {
        return "gregory";
    }

    /**
     * Compares this <code>GregorianCalendar</code> to the specified
     * <code>Object</code>. The result is <code>true</code> if and
     * only if the argument is a <code>GregorianCalendar</code> object
     * that represents the same time value (millisecond offset from
     * the <a href="Calendar.html#Epoch">Epoch</a>) under the same
     * <code>Calendar</code> parameters and Gregorian change date as
     * this object.
     *
     * <p>
     * 将<code> GregorianCalendar </code>与指定的<code> Object </code>进行比较。
     * 如果且仅当参数是<code> GregorianCalendar </code>对象时,结果是<code> true </code>表示与此对象相同的<code>日历</code>参数和公历变更日期下的
     * 相同时间值(与<a href=\"Calendarhtml#Epoch\">时代</a>的毫秒偏移量)。
     * 将<code> GregorianCalendar </code>与指定的<code> Object </code>进行比较。
     * 
     * 
     * @param obj the object to compare with.
     * @return <code>true</code> if this object is equal to <code>obj</code>;
     * <code>false</code> otherwise.
     * @see Calendar#compareTo(Calendar)
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof GregorianCalendar &&
            super.equals(obj) &&
            gregorianCutover == ((GregorianCalendar)obj).gregorianCutover;
    }

    /**
     * Generates the hash code for this <code>GregorianCalendar</code> object.
     * <p>
     *  为此<code> GregorianCalendar </code>对象生成哈希码
     * 
     */
    @Override
    public int hashCode() {
        return super.hashCode() ^ (int)gregorianCutoverDate;
    }

    /**
     * Adds the specified (signed) amount of time to the given calendar field,
     * based on the calendar's rules.
     *
     * <p><em>Add rule 1</em>. The value of <code>field</code>
     * after the call minus the value of <code>field</code> before the
     * call is <code>amount</code>, modulo any overflow that has occurred in
     * <code>field</code>. Overflow occurs when a field value exceeds its
     * range and, as a result, the next larger field is incremented or
     * decremented and the field value is adjusted back into its range.</p>
     *
     * <p><em>Add rule 2</em>. If a smaller field is expected to be
     * invariant, but it is impossible for it to be equal to its
     * prior value because of changes in its minimum or maximum after
     * <code>field</code> is changed, then its value is adjusted to be as close
     * as possible to its expected value. A smaller field represents a
     * smaller unit of time. <code>HOUR</code> is a smaller field than
     * <code>DAY_OF_MONTH</code>. No adjustment is made to smaller fields
     * that are not expected to be invariant. The calendar system
     * determines what fields are expected to be invariant.</p>
     *
     * <p>
     *  根据日历规则,将指定的(已签名)时间量添加到给定日历字段
     * 
     * <p> <em>添加规则1 </em>调用之后<code>字段</code>的值减去调用之前<code>字段</code>的值<code> amount </code >,对在<code>字段中发生的任
     * 何溢出进行取模</code>当字段值超过其范围时发生溢出,结果是下一个较大字段递增或递减,并且将字段值调回其范围</p>。
     * 
     * <p> <em>添加规则2 </em>如果一个较小的字段预期是不变的,但是它不可能等于其之前的值,因为它的最小值或最大值之后的变化< / code>被改变,则其值被调整为尽可能接近其期望值较小的字段表示
     * 较小的时间单位<code> HOUR </code>是比<code> DAY_OF_MONTH </code >不对预​​期不变的较小字段进行调整日历系统确定哪些字段预期是不变的</p>。
     * 
     * 
     * @param field the calendar field.
     * @param amount the amount of date or time to be added to the field.
     * @exception IllegalArgumentException if <code>field</code> is
     * <code>ZONE_OFFSET</code>, <code>DST_OFFSET</code>, or unknown,
     * or if any calendar fields have out-of-range values in
     * non-lenient mode.
     */
    @Override
    public void add(int field, int amount) {
        // If amount == 0, do nothing even the given field is out of
        // range. This is tested by JCK.
        if (amount == 0) {
            return;   // Do nothing!
        }

        if (field < 0 || field >= ZONE_OFFSET) {
            throw new IllegalArgumentException();
        }

        // Sync the time and calendar fields.
        complete();

        if (field == YEAR) {
            int year = internalGet(YEAR);
            if (internalGetEra() == CE) {
                year += amount;
                if (year > 0) {
                    set(YEAR, year);
                } else { // year <= 0
                    set(YEAR, 1 - year);
                    // if year == 0, you get 1 BCE.
                    set(ERA, BCE);
                }
            }
            else { // era == BCE
                year -= amount;
                if (year > 0) {
                    set(YEAR, year);
                } else { // year <= 0
                    set(YEAR, 1 - year);
                    // if year == 0, you get 1 CE
                    set(ERA, CE);
                }
            }
            pinDayOfMonth();
        } else if (field == MONTH) {
            int month = internalGet(MONTH) + amount;
            int year = internalGet(YEAR);
            int y_amount;

            if (month >= 0) {
                y_amount = month/12;
            } else {
                y_amount = (month+1)/12 - 1;
            }
            if (y_amount != 0) {
                if (internalGetEra() == CE) {
                    year += y_amount;
                    if (year > 0) {
                        set(YEAR, year);
                    } else { // year <= 0
                        set(YEAR, 1 - year);
                        // if year == 0, you get 1 BCE
                        set(ERA, BCE);
                    }
                }
                else { // era == BCE
                    year -= y_amount;
                    if (year > 0) {
                        set(YEAR, year);
                    } else { // year <= 0
                        set(YEAR, 1 - year);
                        // if year == 0, you get 1 CE
                        set(ERA, CE);
                    }
                }
            }

            if (month >= 0) {
                set(MONTH,  month % 12);
            } else {
                // month < 0
                month %= 12;
                if (month < 0) {
                    month += 12;
                }
                set(MONTH, JANUARY + month);
            }
            pinDayOfMonth();
        } else if (field == ERA) {
            int era = internalGet(ERA) + amount;
            if (era < 0) {
                era = 0;
            }
            if (era > 1) {
                era = 1;
            }
            set(ERA, era);
        } else {
            long delta = amount;
            long timeOfDay = 0;
            switch (field) {
            // Handle the time fields here. Convert the given
            // amount to milliseconds and call setTimeInMillis.
            case HOUR:
            case HOUR_OF_DAY:
                delta *= 60 * 60 * 1000;        // hours to minutes
                break;

            case MINUTE:
                delta *= 60 * 1000;             // minutes to seconds
                break;

            case SECOND:
                delta *= 1000;                  // seconds to milliseconds
                break;

            case MILLISECOND:
                break;

            // Handle week, day and AM_PM fields which involves
            // time zone offset change adjustment. Convert the
            // given amount to the number of days.
            case WEEK_OF_YEAR:
            case WEEK_OF_MONTH:
            case DAY_OF_WEEK_IN_MONTH:
                delta *= 7;
                break;

            case DAY_OF_MONTH: // synonym of DATE
            case DAY_OF_YEAR:
            case DAY_OF_WEEK:
                break;

            case AM_PM:
                // Convert the amount to the number of days (delta)
                // and +12 or -12 hours (timeOfDay).
                delta = amount / 2;
                timeOfDay = 12 * (amount % 2);
                break;
            }

            // The time fields don't require time zone offset change
            // adjustment.
            if (field >= HOUR) {
                setTimeInMillis(time + delta);
                return;
            }

            // The rest of the fields (week, day or AM_PM fields)
            // require time zone offset (both GMT and DST) change
            // adjustment.

            // Translate the current time to the fixed date and time
            // of the day.
            long fd = getCurrentFixedDate();
            timeOfDay += internalGet(HOUR_OF_DAY);
            timeOfDay *= 60;
            timeOfDay += internalGet(MINUTE);
            timeOfDay *= 60;
            timeOfDay += internalGet(SECOND);
            timeOfDay *= 1000;
            timeOfDay += internalGet(MILLISECOND);
            if (timeOfDay >= ONE_DAY) {
                fd++;
                timeOfDay -= ONE_DAY;
            } else if (timeOfDay < 0) {
                fd--;
                timeOfDay += ONE_DAY;
            }

            fd += delta; // fd is the expected fixed date after the calculation
            int zoneOffset = internalGet(ZONE_OFFSET) + internalGet(DST_OFFSET);
            setTimeInMillis((fd - EPOCH_OFFSET) * ONE_DAY + timeOfDay - zoneOffset);
            zoneOffset -= internalGet(ZONE_OFFSET) + internalGet(DST_OFFSET);
            // If the time zone offset has changed, then adjust the difference.
            if (zoneOffset != 0) {
                setTimeInMillis(time + zoneOffset);
                long fd2 = getCurrentFixedDate();
                // If the adjustment has changed the date, then take
                // the previous one.
                if (fd2 != fd) {
                    setTimeInMillis(time - zoneOffset);
                }
            }
        }
    }

    /**
     * Adds or subtracts (up/down) a single unit of time on the given time
     * field without changing larger fields.
     * <p>
     * <em>Example</em>: Consider a <code>GregorianCalendar</code>
     * originally set to December 31, 1999. Calling {@link #roll(int,boolean) roll(Calendar.MONTH, true)}
     * sets the calendar to January 31, 1999.  The <code>YEAR</code> field is unchanged
     * because it is a larger field than <code>MONTH</code>.</p>
     *
     * <p>
     *  在给定时间字段上添加或减少(上/下)单个时间单位,而不更改较大字段
     * <p>
     * <em>示例</em>：考虑最初设置为1999年12月31日的<code> GregorianCalendar </code>。
     * 调用{@link #roll(int,boolean)roll(CalendarMONTH,true)}将日历设置为1月31日,1999 <code> YEAR </code>字段未更改,因为它是一个比
     * <code> MONTH </code> </p>。
     * <em>示例</em>：考虑最初设置为1999年12月31日的<code> GregorianCalendar </code>。
     * 
     * 
     * @param up indicates if the value of the specified calendar field is to be
     * rolled up or rolled down. Use <code>true</code> if rolling up, <code>false</code> otherwise.
     * @exception IllegalArgumentException if <code>field</code> is
     * <code>ZONE_OFFSET</code>, <code>DST_OFFSET</code>, or unknown,
     * or if any calendar fields have out-of-range values in
     * non-lenient mode.
     * @see #add(int,int)
     * @see #set(int,int)
     */
    @Override
    public void roll(int field, boolean up) {
        roll(field, up ? +1 : -1);
    }

    /**
     * Adds a signed amount to the specified calendar field without changing larger fields.
     * A negative roll amount means to subtract from field without changing
     * larger fields. If the specified amount is 0, this method performs nothing.
     *
     * <p>This method calls {@link #complete()} before adding the
     * amount so that all the calendar fields are normalized. If there
     * is any calendar field having an out-of-range value in non-lenient mode, then an
     * <code>IllegalArgumentException</code> is thrown.
     *
     * <p>
     * <em>Example</em>: Consider a <code>GregorianCalendar</code>
     * originally set to August 31, 1999. Calling <code>roll(Calendar.MONTH,
     * 8)</code> sets the calendar to April 30, <strong>1999</strong>. Using a
     * <code>GregorianCalendar</code>, the <code>DAY_OF_MONTH</code> field cannot
     * be 31 in the month April. <code>DAY_OF_MONTH</code> is set to the closest possible
     * value, 30. The <code>YEAR</code> field maintains the value of 1999 because it
     * is a larger field than <code>MONTH</code>.
     * <p>
     * <em>Example</em>: Consider a <code>GregorianCalendar</code>
     * originally set to Sunday June 6, 1999. Calling
     * <code>roll(Calendar.WEEK_OF_MONTH, -1)</code> sets the calendar to
     * Tuesday June 1, 1999, whereas calling
     * <code>add(Calendar.WEEK_OF_MONTH, -1)</code> sets the calendar to
     * Sunday May 30, 1999. This is because the roll rule imposes an
     * additional constraint: The <code>MONTH</code> must not change when the
     * <code>WEEK_OF_MONTH</code> is rolled. Taken together with add rule 1,
     * the resultant date must be between Tuesday June 1 and Saturday June
     * 5. According to add rule 2, the <code>DAY_OF_WEEK</code>, an invariant
     * when changing the <code>WEEK_OF_MONTH</code>, is set to Tuesday, the
     * closest possible value to Sunday (where Sunday is the first day of the
     * week).</p>
     *
     * <p>
     *  将带符号的金额添加到指定的日历字段而不更改较大的字段负的滚动量意味着从字段中减去而不更改较大的字段如果指定的金额为0,则此方法不执行任何操作
     * 
     *  <p>此方法在添加金额之前调用{@link #complete()},以便所有日历字段均正常化如果在非宽松模式下有任何日历字段具有超出范围值,则<code > IllegalArgumentExcep
     * tion </code>。
     * 
     * <p>
     * <em>示例</em>：考虑一个最初设置为1999年8月31日的<code> GregorianCalendar </code>。
     * <call> <code> roll(CalendarMONTH,8)</code> 1999 </strong>使用<code> GregorianCalendar </code>,<code> DA
     * Y_OF_MONTH </code>字段在四月中不能为31日<code> DAY_OF_MONTH </code>设置为尽可能接近的值30 <code> YEAR </code>字段保持1999的值,因
     * 为它是一个比<code> MONTH </code>。
     * <em>示例</em>：考虑一个最初设置为1999年8月31日的<code> GregorianCalendar </code>。
     * <p>
     * <em> </em>：考虑原来设置为1999年6月6日星期日的<code> GregorianCalendar </code>。
     * 调用<code> roll(CalendarWEEK_OF_MONTH,-1)</code> 1999,而调用<code> add(CalendarWEEK_OF_MONTH,-1)</code>将日历
     * 设置为1999年5月30日星期五这是因为滚动规则强加了一个附加约束：<code> MONTH </code>当<code> WEEK_OF_MONTH </code>滚动时与添加规则1一起,结果日期必须
     * 在6月1日星期六至6月5日星期六之间。
     * <em> </em>：考虑原来设置为1999年6月6日星期日的<code> GregorianCalendar </code>。
     * 根据添加规则2,<code> DAY_OF_WEEK </code>当更改<code> WEEK_OF_MONTH </code>时,设置为星期二,最接近星期日的可能值(星期日是星期日的第一天)</p>
     * 。
     * <em> </em>：考虑原来设置为1999年6月6日星期日的<code> GregorianCalendar </code>。
     * 
     * 
     * @param field the calendar field.
     * @param amount the signed amount to add to <code>field</code>.
     * @exception IllegalArgumentException if <code>field</code> is
     * <code>ZONE_OFFSET</code>, <code>DST_OFFSET</code>, or unknown,
     * or if any calendar fields have out-of-range values in
     * non-lenient mode.
     * @see #roll(int,boolean)
     * @see #add(int,int)
     * @see #set(int,int)
     * @since 1.2
     */
    @Override
    public void roll(int field, int amount) {
        // If amount == 0, do nothing even the given field is out of
        // range. This is tested by JCK.
        if (amount == 0) {
            return;
        }

        if (field < 0 || field >= ZONE_OFFSET) {
            throw new IllegalArgumentException();
        }

        // Sync the time and calendar fields.
        complete();

        int min = getMinimum(field);
        int max = getMaximum(field);

        switch (field) {
        case AM_PM:
        case ERA:
        case YEAR:
        case MINUTE:
        case SECOND:
        case MILLISECOND:
            // These fields are handled simply, since they have fixed minima
            // and maxima.  The field DAY_OF_MONTH is almost as simple.  Other
            // fields are complicated, since the range within they must roll
            // varies depending on the date.
            break;

        case HOUR:
        case HOUR_OF_DAY:
            {
                int unit = max + 1; // 12 or 24 hours
                int h = internalGet(field);
                int nh = (h + amount) % unit;
                if (nh < 0) {
                    nh += unit;
                }
                time += ONE_HOUR * (nh - h);

                // The day might have changed, which could happen if
                // the daylight saving time transition brings it to
                // the next day, although it's very unlikely. But we
                // have to make sure not to change the larger fields.
                CalendarDate d = calsys.getCalendarDate(time, getZone());
                if (internalGet(DAY_OF_MONTH) != d.getDayOfMonth()) {
                    d.setDate(internalGet(YEAR),
                              internalGet(MONTH) + 1,
                              internalGet(DAY_OF_MONTH));
                    if (field == HOUR) {
                        assert (internalGet(AM_PM) == PM);
                        d.addHours(+12); // restore PM
                    }
                    time = calsys.getTime(d);
                }
                int hourOfDay = d.getHours();
                internalSet(field, hourOfDay % unit);
                if (field == HOUR) {
                    internalSet(HOUR_OF_DAY, hourOfDay);
                } else {
                    internalSet(AM_PM, hourOfDay / 12);
                    internalSet(HOUR, hourOfDay % 12);
                }

                // Time zone offset and/or daylight saving might have changed.
                int zoneOffset = d.getZoneOffset();
                int saving = d.getDaylightSaving();
                internalSet(ZONE_OFFSET, zoneOffset - saving);
                internalSet(DST_OFFSET, saving);
                return;
            }

        case MONTH:
            // Rolling the month involves both pinning the final value to [0, 11]
            // and adjusting the DAY_OF_MONTH if necessary.  We only adjust the
            // DAY_OF_MONTH if, after updating the MONTH field, it is illegal.
            // E.g., <jan31>.roll(MONTH, 1) -> <feb28> or <feb29>.
            {
                if (!isCutoverYear(cdate.getNormalizedYear())) {
                    int mon = (internalGet(MONTH) + amount) % 12;
                    if (mon < 0) {
                        mon += 12;
                    }
                    set(MONTH, mon);

                    // Keep the day of month in the range.  We don't want to spill over
                    // into the next month; e.g., we don't want jan31 + 1 mo -> feb31 ->
                    // mar3.
                    int monthLen = monthLength(mon);
                    if (internalGet(DAY_OF_MONTH) > monthLen) {
                        set(DAY_OF_MONTH, monthLen);
                    }
                } else {
                    // We need to take care of different lengths in
                    // year and month due to the cutover.
                    int yearLength = getActualMaximum(MONTH) + 1;
                    int mon = (internalGet(MONTH) + amount) % yearLength;
                    if (mon < 0) {
                        mon += yearLength;
                    }
                    set(MONTH, mon);
                    int monthLen = getActualMaximum(DAY_OF_MONTH);
                    if (internalGet(DAY_OF_MONTH) > monthLen) {
                        set(DAY_OF_MONTH, monthLen);
                    }
                }
                return;
            }

        case WEEK_OF_YEAR:
            {
                int y = cdate.getNormalizedYear();
                max = getActualMaximum(WEEK_OF_YEAR);
                set(DAY_OF_WEEK, internalGet(DAY_OF_WEEK));
                int woy = internalGet(WEEK_OF_YEAR);
                int value = woy + amount;
                if (!isCutoverYear(y)) {
                    int weekYear = getWeekYear();
                    if (weekYear == y) {
                        // If the new value is in between min and max
                        // (exclusive), then we can use the value.
                        if (value > min && value < max) {
                            set(WEEK_OF_YEAR, value);
                            return;
                        }
                        long fd = getCurrentFixedDate();
                        // Make sure that the min week has the current DAY_OF_WEEK
                        // in the calendar year
                        long day1 = fd - (7 * (woy - min));
                        if (calsys.getYearFromFixedDate(day1) != y) {
                            min++;
                        }

                        // Make sure the same thing for the max week
                        fd += 7 * (max - internalGet(WEEK_OF_YEAR));
                        if (calsys.getYearFromFixedDate(fd) != y) {
                            max--;
                        }
                    } else {
                        // When WEEK_OF_YEAR and YEAR are out of sync,
                        // adjust woy and amount to stay in the calendar year.
                        if (weekYear > y) {
                            if (amount < 0) {
                                amount++;
                            }
                            woy = max;
                        } else {
                            if (amount > 0) {
                                amount -= woy - max;
                            }
                            woy = min;
                        }
                    }
                    set(field, getRolledValue(woy, amount, min, max));
                    return;
                }

                // Handle cutover here.
                long fd = getCurrentFixedDate();
                BaseCalendar cal;
                if (gregorianCutoverYear == gregorianCutoverYearJulian) {
                    cal = getCutoverCalendarSystem();
                } else if (y == gregorianCutoverYear) {
                    cal = gcal;
                } else {
                    cal = getJulianCalendarSystem();
                }
                long day1 = fd - (7 * (woy - min));
                // Make sure that the min week has the current DAY_OF_WEEK
                if (cal.getYearFromFixedDate(day1) != y) {
                    min++;
                }

                // Make sure the same thing for the max week
                fd += 7 * (max - woy);
                cal = (fd >= gregorianCutoverDate) ? gcal : getJulianCalendarSystem();
                if (cal.getYearFromFixedDate(fd) != y) {
                    max--;
                }
                // value: the new WEEK_OF_YEAR which must be converted
                // to month and day of month.
                value = getRolledValue(woy, amount, min, max) - 1;
                BaseCalendar.Date d = getCalendarDate(day1 + value * 7);
                set(MONTH, d.getMonth() - 1);
                set(DAY_OF_MONTH, d.getDayOfMonth());
                return;
            }

        case WEEK_OF_MONTH:
            {
                boolean isCutoverYear = isCutoverYear(cdate.getNormalizedYear());
                // dow: relative day of week from first day of week
                int dow = internalGet(DAY_OF_WEEK) - getFirstDayOfWeek();
                if (dow < 0) {
                    dow += 7;
                }

                long fd = getCurrentFixedDate();
                long month1;     // fixed date of the first day (usually 1) of the month
                int monthLength; // actual month length
                if (isCutoverYear) {
                    month1 = getFixedDateMonth1(cdate, fd);
                    monthLength = actualMonthLength();
                } else {
                    month1 = fd - internalGet(DAY_OF_MONTH) + 1;
                    monthLength = calsys.getMonthLength(cdate);
                }

                // the first day of week of the month.
                long monthDay1st = BaseCalendar.getDayOfWeekDateOnOrBefore(month1 + 6,
                                                                           getFirstDayOfWeek());
                // if the week has enough days to form a week, the
                // week starts from the previous month.
                if ((int)(monthDay1st - month1) >= getMinimalDaysInFirstWeek()) {
                    monthDay1st -= 7;
                }
                max = getActualMaximum(field);

                // value: the new WEEK_OF_MONTH value
                int value = getRolledValue(internalGet(field), amount, 1, max) - 1;

                // nfd: fixed date of the rolled date
                long nfd = monthDay1st + value * 7 + dow;

                // Unlike WEEK_OF_YEAR, we need to change day of week if the
                // nfd is out of the month.
                if (nfd < month1) {
                    nfd = month1;
                } else if (nfd >= (month1 + monthLength)) {
                    nfd = month1 + monthLength - 1;
                }
                int dayOfMonth;
                if (isCutoverYear) {
                    // If we are in the cutover year, convert nfd to
                    // its calendar date and use dayOfMonth.
                    BaseCalendar.Date d = getCalendarDate(nfd);
                    dayOfMonth = d.getDayOfMonth();
                } else {
                    dayOfMonth = (int)(nfd - month1) + 1;
                }
                set(DAY_OF_MONTH, dayOfMonth);
                return;
            }

        case DAY_OF_MONTH:
            {
                if (!isCutoverYear(cdate.getNormalizedYear())) {
                    max = calsys.getMonthLength(cdate);
                    break;
                }

                // Cutover year handling
                long fd = getCurrentFixedDate();
                long month1 = getFixedDateMonth1(cdate, fd);
                // It may not be a regular month. Convert the date and range to
                // the relative values, perform the roll, and
                // convert the result back to the rolled date.
                int value = getRolledValue((int)(fd - month1), amount, 0, actualMonthLength() - 1);
                BaseCalendar.Date d = getCalendarDate(month1 + value);
                assert d.getMonth()-1 == internalGet(MONTH);
                set(DAY_OF_MONTH, d.getDayOfMonth());
                return;
            }

        case DAY_OF_YEAR:
            {
                max = getActualMaximum(field);
                if (!isCutoverYear(cdate.getNormalizedYear())) {
                    break;
                }

                // Handle cutover here.
                long fd = getCurrentFixedDate();
                long jan1 = fd - internalGet(DAY_OF_YEAR) + 1;
                int value = getRolledValue((int)(fd - jan1) + 1, amount, min, max);
                BaseCalendar.Date d = getCalendarDate(jan1 + value - 1);
                set(MONTH, d.getMonth() - 1);
                set(DAY_OF_MONTH, d.getDayOfMonth());
                return;
            }

        case DAY_OF_WEEK:
            {
                if (!isCutoverYear(cdate.getNormalizedYear())) {
                    // If the week of year is in the same year, we can
                    // just change DAY_OF_WEEK.
                    int weekOfYear = internalGet(WEEK_OF_YEAR);
                    if (weekOfYear > 1 && weekOfYear < 52) {
                        set(WEEK_OF_YEAR, weekOfYear); // update stamp[WEEK_OF_YEAR]
                        max = SATURDAY;
                        break;
                    }
                }

                // We need to handle it in a different way around year
                // boundaries and in the cutover year. Note that
                // changing era and year values violates the roll
                // rule: not changing larger calendar fields...
                amount %= 7;
                if (amount == 0) {
                    return;
                }
                long fd = getCurrentFixedDate();
                long dowFirst = BaseCalendar.getDayOfWeekDateOnOrBefore(fd, getFirstDayOfWeek());
                fd += amount;
                if (fd < dowFirst) {
                    fd += 7;
                } else if (fd >= dowFirst + 7) {
                    fd -= 7;
                }
                BaseCalendar.Date d = getCalendarDate(fd);
                set(ERA, (d.getNormalizedYear() <= 0 ? BCE : CE));
                set(d.getYear(), d.getMonth() - 1, d.getDayOfMonth());
                return;
            }

        case DAY_OF_WEEK_IN_MONTH:
            {
                min = 1; // after normalized, min should be 1.
                if (!isCutoverYear(cdate.getNormalizedYear())) {
                    int dom = internalGet(DAY_OF_MONTH);
                    int monthLength = calsys.getMonthLength(cdate);
                    int lastDays = monthLength % 7;
                    max = monthLength / 7;
                    int x = (dom - 1) % 7;
                    if (x < lastDays) {
                        max++;
                    }
                    set(DAY_OF_WEEK, internalGet(DAY_OF_WEEK));
                    break;
                }

                // Cutover year handling
                long fd = getCurrentFixedDate();
                long month1 = getFixedDateMonth1(cdate, fd);
                int monthLength = actualMonthLength();
                int lastDays = monthLength % 7;
                max = monthLength / 7;
                int x = (int)(fd - month1) % 7;
                if (x < lastDays) {
                    max++;
                }
                int value = getRolledValue(internalGet(field), amount, min, max) - 1;
                fd = month1 + value * 7 + x;
                BaseCalendar cal = (fd >= gregorianCutoverDate) ? gcal : getJulianCalendarSystem();
                BaseCalendar.Date d = (BaseCalendar.Date) cal.newCalendarDate(TimeZone.NO_TIMEZONE);
                cal.getCalendarDateFromFixedDate(d, fd);
                set(DAY_OF_MONTH, d.getDayOfMonth());
                return;
            }
        }

        set(field, getRolledValue(internalGet(field), amount, min, max));
    }

    /**
     * Returns the minimum value for the given calendar field of this
     * <code>GregorianCalendar</code> instance. The minimum value is
     * defined as the smallest value returned by the {@link
     * Calendar#get(int) get} method for any possible time value,
     * taking into consideration the current values of the
     * {@link Calendar#getFirstDayOfWeek() getFirstDayOfWeek},
     * {@link Calendar#getMinimalDaysInFirstWeek() getMinimalDaysInFirstWeek},
     * {@link #getGregorianChange() getGregorianChange} and
     * {@link Calendar#getTimeZone() getTimeZone} methods.
     *
     * <p>
     * 返回此<code> GregorianCalendar </code>实例的给定日历字段的最小值最小值被定义为{@link Calendar#get(int)get}方法为任何可能的时间值返回的最小值,
     * 考虑{@link Calendar#getFirstDayOfWeek()getFirstDayOfWeek},{@link Calendar#getMinimalDaysInFirstWeek()getMinimalDaysInFirstWeek}
     * ,{@link #getGregorianChange()getGregorianChange}和{@link Calendar#getTimeZone()getTimeZone}方法。
     * 
     * 
     * @param field the calendar field.
     * @return the minimum value for the given calendar field.
     * @see #getMaximum(int)
     * @see #getGreatestMinimum(int)
     * @see #getLeastMaximum(int)
     * @see #getActualMinimum(int)
     * @see #getActualMaximum(int)
     */
    @Override
    public int getMinimum(int field) {
        return MIN_VALUES[field];
    }

    /**
     * Returns the maximum value for the given calendar field of this
     * <code>GregorianCalendar</code> instance. The maximum value is
     * defined as the largest value returned by the {@link
     * Calendar#get(int) get} method for any possible time value,
     * taking into consideration the current values of the
     * {@link Calendar#getFirstDayOfWeek() getFirstDayOfWeek},
     * {@link Calendar#getMinimalDaysInFirstWeek() getMinimalDaysInFirstWeek},
     * {@link #getGregorianChange() getGregorianChange} and
     * {@link Calendar#getTimeZone() getTimeZone} methods.
     *
     * <p>
     * 返回此<code> GregorianCalendar </code>实例的给定日历字段的最大值最大值定义为对于任何可能的时间值,由{@link Calendar#get(int)get}方法返回的最大
     * 值,考虑到{@link Calendar#getFirstDayOfWeek()getFirstDayOfWeek},{@link Calendar#getMinimalDaysInFirstWeek()getMinimalDaysInFirstWeek}
     * ,{@link #getGregorianChange()getGregorianChange}和{@link Calendar#getTimeZone()getTimeZone}方法。
     * 
     * 
     * @param field the calendar field.
     * @return the maximum value for the given calendar field.
     * @see #getMinimum(int)
     * @see #getGreatestMinimum(int)
     * @see #getLeastMaximum(int)
     * @see #getActualMinimum(int)
     * @see #getActualMaximum(int)
     */
    @Override
    public int getMaximum(int field) {
        switch (field) {
        case MONTH:
        case DAY_OF_MONTH:
        case DAY_OF_YEAR:
        case WEEK_OF_YEAR:
        case WEEK_OF_MONTH:
        case DAY_OF_WEEK_IN_MONTH:
        case YEAR:
            {
                // On or after Gregorian 200-3-1, Julian and Gregorian
                // calendar dates are the same or Gregorian dates are
                // larger (i.e., there is a "gap") after 300-3-1.
                if (gregorianCutoverYear > 200) {
                    break;
                }
                // There might be "overlapping" dates.
                GregorianCalendar gc = (GregorianCalendar) clone();
                gc.setLenient(true);
                gc.setTimeInMillis(gregorianCutover);
                int v1 = gc.getActualMaximum(field);
                gc.setTimeInMillis(gregorianCutover-1);
                int v2 = gc.getActualMaximum(field);
                return Math.max(MAX_VALUES[field], Math.max(v1, v2));
            }
        }
        return MAX_VALUES[field];
    }

    /**
     * Returns the highest minimum value for the given calendar field
     * of this <code>GregorianCalendar</code> instance. The highest
     * minimum value is defined as the largest value returned by
     * {@link #getActualMinimum(int)} for any possible time value,
     * taking into consideration the current values of the
     * {@link Calendar#getFirstDayOfWeek() getFirstDayOfWeek},
     * {@link Calendar#getMinimalDaysInFirstWeek() getMinimalDaysInFirstWeek},
     * {@link #getGregorianChange() getGregorianChange} and
     * {@link Calendar#getTimeZone() getTimeZone} methods.
     *
     * <p>
     * 返回此<code> GregorianCalendar </code>实例的给定日历字段的最大最小值。
     * 最大最小值被定义为对于任何可能的时间值,由{@link #getActualMinimum(int)}返回的最大值,考虑{@link Calendar#getFirstDayOfWeek()getFirstDayOfWeek}
     * ,{@link Calendar#getMinimalDaysInFirstWeek()getMinimalDaysInFirstWeek},{@link #getGregorianChange()getGregorianChange}
     * 和{@link Calendar#getTimeZone()getTimeZone}方法的当前值。
     * 返回此<code> GregorianCalendar </code>实例的给定日历字段的最大最小值。
     * 
     * 
     * @param field the calendar field.
     * @return the highest minimum value for the given calendar field.
     * @see #getMinimum(int)
     * @see #getMaximum(int)
     * @see #getLeastMaximum(int)
     * @see #getActualMinimum(int)
     * @see #getActualMaximum(int)
     */
    @Override
    public int getGreatestMinimum(int field) {
        if (field == DAY_OF_MONTH) {
            BaseCalendar.Date d = getGregorianCutoverDate();
            long mon1 = getFixedDateMonth1(d, gregorianCutoverDate);
            d = getCalendarDate(mon1);
            return Math.max(MIN_VALUES[field], d.getDayOfMonth());
        }
        return MIN_VALUES[field];
    }

    /**
     * Returns the lowest maximum value for the given calendar field
     * of this <code>GregorianCalendar</code> instance. The lowest
     * maximum value is defined as the smallest value returned by
     * {@link #getActualMaximum(int)} for any possible time value,
     * taking into consideration the current values of the
     * {@link Calendar#getFirstDayOfWeek() getFirstDayOfWeek},
     * {@link Calendar#getMinimalDaysInFirstWeek() getMinimalDaysInFirstWeek},
     * {@link #getGregorianChange() getGregorianChange} and
     * {@link Calendar#getTimeZone() getTimeZone} methods.
     *
     * <p>
     * 返回此<code> GregorianCalendar </code>实例的给定日历字段的最小最大值最小的最大值定义为对于任何可能的时间值,由{@link #getActualMaximum(int)}
     * 返回的最小值,考虑{@link Calendar#getFirstDayOfWeek()getFirstDayOfWeek},{@link Calendar#getMinimalDaysInFirstWeek()getMinimalDaysInFirstWeek}
     * ,{@link #getGregorianChange()getGregorianChange}和{@link Calendar#getTimeZone()getTimeZone}方法的当前值。
     * 
     * 
     * @param field the calendar field
     * @return the lowest maximum value for the given calendar field.
     * @see #getMinimum(int)
     * @see #getMaximum(int)
     * @see #getGreatestMinimum(int)
     * @see #getActualMinimum(int)
     * @see #getActualMaximum(int)
     */
    @Override
    public int getLeastMaximum(int field) {
        switch (field) {
        case MONTH:
        case DAY_OF_MONTH:
        case DAY_OF_YEAR:
        case WEEK_OF_YEAR:
        case WEEK_OF_MONTH:
        case DAY_OF_WEEK_IN_MONTH:
        case YEAR:
            {
                GregorianCalendar gc = (GregorianCalendar) clone();
                gc.setLenient(true);
                gc.setTimeInMillis(gregorianCutover);
                int v1 = gc.getActualMaximum(field);
                gc.setTimeInMillis(gregorianCutover-1);
                int v2 = gc.getActualMaximum(field);
                return Math.min(LEAST_MAX_VALUES[field], Math.min(v1, v2));
            }
        }
        return LEAST_MAX_VALUES[field];
    }

    /**
     * Returns the minimum value that this calendar field could have,
     * taking into consideration the given time value and the current
     * values of the
     * {@link Calendar#getFirstDayOfWeek() getFirstDayOfWeek},
     * {@link Calendar#getMinimalDaysInFirstWeek() getMinimalDaysInFirstWeek},
     * {@link #getGregorianChange() getGregorianChange} and
     * {@link Calendar#getTimeZone() getTimeZone} methods.
     *
     * <p>For example, if the Gregorian change date is January 10,
     * 1970 and the date of this <code>GregorianCalendar</code> is
     * January 20, 1970, the actual minimum value of the
     * <code>DAY_OF_MONTH</code> field is 10 because the previous date
     * of January 10, 1970 is December 27, 1996 (in the Julian
     * calendar). Therefore, December 28, 1969 to January 9, 1970
     * don't exist.
     *
     * <p>
     * 考虑到给定时间值和{@link Calendar#getFirstDayOfWeek()getFirstDayOfWeek}的给定时间值和当前值,返回此日历字段可能具有的最小值,{@link Calendar#getMinimalDaysInFirstWeek()getMinimalDaysInFirstWeek}
     * ,{@link# getGregorianChange()getGregorianChange}和{@link Calendar#getTimeZone()getTimeZone}方法。
     * 
     *  <p>例如,如果格雷戈里改变日期是1970年1月10日,并且<code> GregorianCalendar </code>的日期是1970年1月20日,则<code> DAY_OF_MONTH </code>
     * 字段的实际最小值是10,因为1970年1月10日的前日是1996年12月27日(儒略历)因此,1969年12月28日到1970年1月9日不存在。
     * 
     * 
     * @param field the calendar field
     * @return the minimum of the given field for the time value of
     * this <code>GregorianCalendar</code>
     * @see #getMinimum(int)
     * @see #getMaximum(int)
     * @see #getGreatestMinimum(int)
     * @see #getLeastMaximum(int)
     * @see #getActualMaximum(int)
     * @since 1.2
     */
    @Override
    public int getActualMinimum(int field) {
        if (field == DAY_OF_MONTH) {
            GregorianCalendar gc = getNormalizedCalendar();
            int year = gc.cdate.getNormalizedYear();
            if (year == gregorianCutoverYear || year == gregorianCutoverYearJulian) {
                long month1 = getFixedDateMonth1(gc.cdate, gc.calsys.getFixedDate(gc.cdate));
                BaseCalendar.Date d = getCalendarDate(month1);
                return d.getDayOfMonth();
            }
        }
        return getMinimum(field);
    }

    /**
     * Returns the maximum value that this calendar field could have,
     * taking into consideration the given time value and the current
     * values of the
     * {@link Calendar#getFirstDayOfWeek() getFirstDayOfWeek},
     * {@link Calendar#getMinimalDaysInFirstWeek() getMinimalDaysInFirstWeek},
     * {@link #getGregorianChange() getGregorianChange} and
     * {@link Calendar#getTimeZone() getTimeZone} methods.
     * For example, if the date of this instance is February 1, 2004,
     * the actual maximum value of the <code>DAY_OF_MONTH</code> field
     * is 29 because 2004 is a leap year, and if the date of this
     * instance is February 1, 2005, it's 28.
     *
     * <p>This method calculates the maximum value of {@link
     * Calendar#WEEK_OF_YEAR WEEK_OF_YEAR} based on the {@link
     * Calendar#YEAR YEAR} (calendar year) value, not the <a
     * href="#week_year">week year</a>. Call {@link
     * #getWeeksInWeekYear()} to get the maximum value of {@code
     * WEEK_OF_YEAR} in the week year of this {@code GregorianCalendar}.
     *
     * <p>
     * 
     * <p>此方法根据{@link Calendar#YEAR YEAR}(日历年)值而不是<a href=\"#week_year\">周年</b>计算{@link日历#WEEK_OF_YEAR WEEK_OF_YEAR}
     * 的最大值, a>调用{@link #getWeeksInWeekYear()}可获得此{@code GregorianCalendar}周年的{@code WEEK_OF_YEAR}最大值。
     * 
     * 
     * @param field the calendar field
     * @return the maximum of the given field for the time value of
     * this <code>GregorianCalendar</code>
     * @see #getMinimum(int)
     * @see #getMaximum(int)
     * @see #getGreatestMinimum(int)
     * @see #getLeastMaximum(int)
     * @see #getActualMinimum(int)
     * @since 1.2
     */
    @Override
    public int getActualMaximum(int field) {
        final int fieldsForFixedMax = ERA_MASK|DAY_OF_WEEK_MASK|HOUR_MASK|AM_PM_MASK|
            HOUR_OF_DAY_MASK|MINUTE_MASK|SECOND_MASK|MILLISECOND_MASK|
            ZONE_OFFSET_MASK|DST_OFFSET_MASK;
        if ((fieldsForFixedMax & (1<<field)) != 0) {
            return getMaximum(field);
        }

        GregorianCalendar gc = getNormalizedCalendar();
        BaseCalendar.Date date = gc.cdate;
        BaseCalendar cal = gc.calsys;
        int normalizedYear = date.getNormalizedYear();

        int value = -1;
        switch (field) {
        case MONTH:
            {
                if (!gc.isCutoverYear(normalizedYear)) {
                    value = DECEMBER;
                    break;
                }

                // January 1 of the next year may or may not exist.
                long nextJan1;
                do {
                    nextJan1 = gcal.getFixedDate(++normalizedYear, BaseCalendar.JANUARY, 1, null);
                } while (nextJan1 < gregorianCutoverDate);
                BaseCalendar.Date d = (BaseCalendar.Date) date.clone();
                cal.getCalendarDateFromFixedDate(d, nextJan1 - 1);
                value = d.getMonth() - 1;
            }
            break;

        case DAY_OF_MONTH:
            {
                value = cal.getMonthLength(date);
                if (!gc.isCutoverYear(normalizedYear) || date.getDayOfMonth() == value) {
                    break;
                }

                // Handle cutover year.
                long fd = gc.getCurrentFixedDate();
                if (fd >= gregorianCutoverDate) {
                    break;
                }
                int monthLength = gc.actualMonthLength();
                long monthEnd = gc.getFixedDateMonth1(gc.cdate, fd) + monthLength - 1;
                // Convert the fixed date to its calendar date.
                BaseCalendar.Date d = gc.getCalendarDate(monthEnd);
                value = d.getDayOfMonth();
            }
            break;

        case DAY_OF_YEAR:
            {
                if (!gc.isCutoverYear(normalizedYear)) {
                    value = cal.getYearLength(date);
                    break;
                }

                // Handle cutover year.
                long jan1;
                if (gregorianCutoverYear == gregorianCutoverYearJulian) {
                    BaseCalendar cocal = gc.getCutoverCalendarSystem();
                    jan1 = cocal.getFixedDate(normalizedYear, 1, 1, null);
                } else if (normalizedYear == gregorianCutoverYearJulian) {
                    jan1 = cal.getFixedDate(normalizedYear, 1, 1, null);
                } else {
                    jan1 = gregorianCutoverDate;
                }
                // January 1 of the next year may or may not exist.
                long nextJan1 = gcal.getFixedDate(++normalizedYear, 1, 1, null);
                if (nextJan1 < gregorianCutoverDate) {
                    nextJan1 = gregorianCutoverDate;
                }
                assert jan1 <= cal.getFixedDate(date.getNormalizedYear(), date.getMonth(),
                                                date.getDayOfMonth(), date);
                assert nextJan1 >= cal.getFixedDate(date.getNormalizedYear(), date.getMonth(),
                                                date.getDayOfMonth(), date);
                value = (int)(nextJan1 - jan1);
            }
            break;

        case WEEK_OF_YEAR:
            {
                if (!gc.isCutoverYear(normalizedYear)) {
                    // Get the day of week of January 1 of the year
                    CalendarDate d = cal.newCalendarDate(TimeZone.NO_TIMEZONE);
                    d.setDate(date.getYear(), BaseCalendar.JANUARY, 1);
                    int dayOfWeek = cal.getDayOfWeek(d);
                    // Normalize the day of week with the firstDayOfWeek value
                    dayOfWeek -= getFirstDayOfWeek();
                    if (dayOfWeek < 0) {
                        dayOfWeek += 7;
                    }
                    value = 52;
                    int magic = dayOfWeek + getMinimalDaysInFirstWeek() - 1;
                    if ((magic == 6) ||
                        (date.isLeapYear() && (magic == 5 || magic == 12))) {
                        value++;
                    }
                    break;
                }

                if (gc == this) {
                    gc = (GregorianCalendar) gc.clone();
                }
                int maxDayOfYear = getActualMaximum(DAY_OF_YEAR);
                gc.set(DAY_OF_YEAR, maxDayOfYear);
                value = gc.get(WEEK_OF_YEAR);
                if (internalGet(YEAR) != gc.getWeekYear()) {
                    gc.set(DAY_OF_YEAR, maxDayOfYear - 7);
                    value = gc.get(WEEK_OF_YEAR);
                }
            }
            break;

        case WEEK_OF_MONTH:
            {
                if (!gc.isCutoverYear(normalizedYear)) {
                    CalendarDate d = cal.newCalendarDate(null);
                    d.setDate(date.getYear(), date.getMonth(), 1);
                    int dayOfWeek = cal.getDayOfWeek(d);
                    int monthLength = cal.getMonthLength(d);
                    dayOfWeek -= getFirstDayOfWeek();
                    if (dayOfWeek < 0) {
                        dayOfWeek += 7;
                    }
                    int nDaysFirstWeek = 7 - dayOfWeek; // # of days in the first week
                    value = 3;
                    if (nDaysFirstWeek >= getMinimalDaysInFirstWeek()) {
                        value++;
                    }
                    monthLength -= nDaysFirstWeek + 7 * 3;
                    if (monthLength > 0) {
                        value++;
                        if (monthLength > 7) {
                            value++;
                        }
                    }
                    break;
                }

                // Cutover year handling
                if (gc == this) {
                    gc = (GregorianCalendar) gc.clone();
                }
                int y = gc.internalGet(YEAR);
                int m = gc.internalGet(MONTH);
                do {
                    value = gc.get(WEEK_OF_MONTH);
                    gc.add(WEEK_OF_MONTH, +1);
                } while (gc.get(YEAR) == y && gc.get(MONTH) == m);
            }
            break;

        case DAY_OF_WEEK_IN_MONTH:
            {
                // may be in the Gregorian cutover month
                int ndays, dow1;
                int dow = date.getDayOfWeek();
                if (!gc.isCutoverYear(normalizedYear)) {
                    BaseCalendar.Date d = (BaseCalendar.Date) date.clone();
                    ndays = cal.getMonthLength(d);
                    d.setDayOfMonth(1);
                    cal.normalize(d);
                    dow1 = d.getDayOfWeek();
                } else {
                    // Let a cloned GregorianCalendar take care of the cutover cases.
                    if (gc == this) {
                        gc = (GregorianCalendar) clone();
                    }
                    ndays = gc.actualMonthLength();
                    gc.set(DAY_OF_MONTH, gc.getActualMinimum(DAY_OF_MONTH));
                    dow1 = gc.get(DAY_OF_WEEK);
                }
                int x = dow - dow1;
                if (x < 0) {
                    x += 7;
                }
                ndays -= x;
                value = (ndays + 6) / 7;
            }
            break;

        case YEAR:
            /* The year computation is no different, in principle, from the
             * others, however, the range of possible maxima is large.  In
             * addition, the way we know we've exceeded the range is different.
             * For these reasons, we use the special case code below to handle
             * this field.
             *
             * The actual maxima for YEAR depend on the type of calendar:
             *
             *     Gregorian = May 17, 292275056 BCE - Aug 17, 292278994 CE
             *     Julian    = Dec  2, 292269055 BCE - Jan  3, 292272993 CE
             *     Hybrid    = Dec  2, 292269055 BCE - Aug 17, 292278994 CE
             *
             * We know we've exceeded the maximum when either the month, date,
             * time, or era changes in response to setting the year.  We don't
             * check for month, date, and time here because the year and era are
             * sufficient to detect an invalid year setting.  NOTE: If code is
             * added to check the month and date in the future for some reason,
             * Feb 29 must be allowed to shift to Mar 1 when setting the year.
             * <p>
             *  其他,然而,可能的最大值的范围是大此外,我们知道我们已经超过范围的方式是不同的由于这些原因,我们使用下面的特殊情况代码来处理这个字段
             * 
             *  YEAR的实际最大值取决于日历类型：
             * 
             *  Gregorian = May 17,292275056 BCE  -  Aug 17,292278994 CE Julian = Dec 2,292269055 BCE  -  Jan 3,2922
             * 72993 CE Hybrid = Dec 2,292269055 BCE  -  Aug 17,292278994 CE。
             * 
             * 我们知道当月,日期,时间或时代因为设置年份而发生变化时,我们已经超出了最大值。
             * 我们在这里不检查月份,日期和时间,因为年份和时代足以检测到无效年设置注意：如果由于某种原因添加代码以检查未来的月份和日期,则在设置年份时,必须允许2月29日更改为3月1日。
             * 
             */
            {
                if (gc == this) {
                    gc = (GregorianCalendar) clone();
                }

                // Calculate the millisecond offset from the beginning
                // of the year of this calendar and adjust the max
                // year value if we are beyond the limit in the max
                // year.
                long current = gc.getYearOffsetInMillis();

                if (gc.internalGetEra() == CE) {
                    gc.setTimeInMillis(Long.MAX_VALUE);
                    value = gc.get(YEAR);
                    long maxEnd = gc.getYearOffsetInMillis();
                    if (current > maxEnd) {
                        value--;
                    }
                } else {
                    CalendarSystem mincal = gc.getTimeInMillis() >= gregorianCutover ?
                        gcal : getJulianCalendarSystem();
                    CalendarDate d = mincal.getCalendarDate(Long.MIN_VALUE, getZone());
                    long maxEnd = (cal.getDayOfYear(d) - 1) * 24 + d.getHours();
                    maxEnd *= 60;
                    maxEnd += d.getMinutes();
                    maxEnd *= 60;
                    maxEnd += d.getSeconds();
                    maxEnd *= 1000;
                    maxEnd += d.getMillis();
                    value = d.getYear();
                    if (value <= 0) {
                        assert mincal == gcal;
                        value = 1 - value;
                    }
                    if (current < maxEnd) {
                        value--;
                    }
                }
            }
            break;

        default:
            throw new ArrayIndexOutOfBoundsException(field);
        }
        return value;
    }

    /**
     * Returns the millisecond offset from the beginning of this
     * year. This Calendar object must have been normalized.
     * <p>
     *  返回从今年开始的毫秒偏移量此Calendar对象必须已经过规范化
     * 
     */
    private long getYearOffsetInMillis() {
        long t = (internalGet(DAY_OF_YEAR) - 1) * 24;
        t += internalGet(HOUR_OF_DAY);
        t *= 60;
        t += internalGet(MINUTE);
        t *= 60;
        t += internalGet(SECOND);
        t *= 1000;
        return t + internalGet(MILLISECOND) -
            (internalGet(ZONE_OFFSET) + internalGet(DST_OFFSET));
    }

    @Override
    public Object clone()
    {
        GregorianCalendar other = (GregorianCalendar) super.clone();

        other.gdate = (BaseCalendar.Date) gdate.clone();
        if (cdate != null) {
            if (cdate != gdate) {
                other.cdate = (BaseCalendar.Date) cdate.clone();
            } else {
                other.cdate = other.gdate;
            }
        }
        other.originalFields = null;
        other.zoneOffsets = null;
        return other;
    }

    @Override
    public TimeZone getTimeZone() {
        TimeZone zone = super.getTimeZone();
        // To share the zone by CalendarDates
        gdate.setZone(zone);
        if (cdate != null && cdate != gdate) {
            cdate.setZone(zone);
        }
        return zone;
    }

    @Override
    public void setTimeZone(TimeZone zone) {
        super.setTimeZone(zone);
        // To share the zone by CalendarDates
        gdate.setZone(zone);
        if (cdate != null && cdate != gdate) {
            cdate.setZone(zone);
        }
    }

    /**
     * Returns {@code true} indicating this {@code GregorianCalendar}
     * supports week dates.
     *
     * <p>
     *  返回{@code true}表示此{@code GregorianCalendar}支持星期日期
     * 
     * 
     * @return {@code true} (always)
     * @see #getWeekYear()
     * @see #setWeekDate(int,int,int)
     * @see #getWeeksInWeekYear()
     * @since 1.7
     */
    @Override
    public final boolean isWeekDateSupported() {
        return true;
    }

    /**
     * Returns the <a href="#week_year">week year</a> represented by this
     * {@code GregorianCalendar}. The dates in the weeks between 1 and the
     * maximum week number of the week year have the same week year value
     * that may be one year before or after the {@link Calendar#YEAR YEAR}
     * (calendar year) value.
     *
     * <p>This method calls {@link Calendar#complete()} before
     * calculating the week year.
     *
     * <p>
     * 返回由此{@code GregorianCalendar}表示的<a href=\"#week_year\">周年</a> 1周与周年的最大周数之间的周数具有相同的周年值,可能是在{@link日历#YEAR YEAR}
     * (日历年)值之前或之后的一年。
     * 
     *  <p>此方法在计算周年之前调用{@link Calendar#complete()}
     * 
     * 
     * @return the week year represented by this {@code GregorianCalendar}.
     *         If the {@link Calendar#ERA ERA} value is {@link #BC}, the year is
     *         represented by 0 or a negative number: BC 1 is 0, BC 2
     *         is -1, BC 3 is -2, and so on.
     * @throws IllegalArgumentException
     *         if any of the calendar fields is invalid in non-lenient mode.
     * @see #isWeekDateSupported()
     * @see #getWeeksInWeekYear()
     * @see Calendar#getFirstDayOfWeek()
     * @see Calendar#getMinimalDaysInFirstWeek()
     * @since 1.7
     */
    @Override
    public int getWeekYear() {
        int year = get(YEAR); // implicitly calls complete()
        if (internalGetEra() == BCE) {
            year = 1 - year;
        }

        // Fast path for the Gregorian calendar years that are never
        // affected by the Julian-Gregorian transition
        if (year > gregorianCutoverYear + 1) {
            int weekOfYear = internalGet(WEEK_OF_YEAR);
            if (internalGet(MONTH) == JANUARY) {
                if (weekOfYear >= 52) {
                    --year;
                }
            } else {
                if (weekOfYear == 1) {
                    ++year;
                }
            }
            return year;
        }

        // General (slow) path
        int dayOfYear = internalGet(DAY_OF_YEAR);
        int maxDayOfYear = getActualMaximum(DAY_OF_YEAR);
        int minimalDays = getMinimalDaysInFirstWeek();

        // Quickly check the possibility of year adjustments before
        // cloning this GregorianCalendar.
        if (dayOfYear > minimalDays && dayOfYear < (maxDayOfYear - 6)) {
            return year;
        }

        // Create a clone to work on the calculation
        GregorianCalendar cal = (GregorianCalendar) clone();
        cal.setLenient(true);
        // Use GMT so that intermediate date calculations won't
        // affect the time of day fields.
        cal.setTimeZone(TimeZone.getTimeZone("GMT"));
        // Go to the first day of the year, which is usually January 1.
        cal.set(DAY_OF_YEAR, 1);
        cal.complete();

        // Get the first day of the first day-of-week in the year.
        int delta = getFirstDayOfWeek() - cal.get(DAY_OF_WEEK);
        if (delta != 0) {
            if (delta < 0) {
                delta += 7;
            }
            cal.add(DAY_OF_YEAR, delta);
        }
        int minDayOfYear = cal.get(DAY_OF_YEAR);
        if (dayOfYear < minDayOfYear) {
            if (minDayOfYear <= minimalDays) {
                --year;
            }
        } else {
            cal.set(YEAR, year + 1);
            cal.set(DAY_OF_YEAR, 1);
            cal.complete();
            int del = getFirstDayOfWeek() - cal.get(DAY_OF_WEEK);
            if (del != 0) {
                if (del < 0) {
                    del += 7;
                }
                cal.add(DAY_OF_YEAR, del);
            }
            minDayOfYear = cal.get(DAY_OF_YEAR) - 1;
            if (minDayOfYear == 0) {
                minDayOfYear = 7;
            }
            if (minDayOfYear >= minimalDays) {
                int days = maxDayOfYear - dayOfYear + 1;
                if (days <= (7 - minDayOfYear)) {
                    ++year;
                }
            }
        }
        return year;
    }

    /**
     * Sets this {@code GregorianCalendar} to the date given by the
     * date specifiers - <a href="#week_year">{@code weekYear}</a>,
     * {@code weekOfYear}, and {@code dayOfWeek}. {@code weekOfYear}
     * follows the <a href="#week_and_year">{@code WEEK_OF_YEAR}
     * numbering</a>.  The {@code dayOfWeek} value must be one of the
     * {@link Calendar#DAY_OF_WEEK DAY_OF_WEEK} values: {@link
     * Calendar#SUNDAY SUNDAY} to {@link Calendar#SATURDAY SATURDAY}.
     *
     * <p>Note that the numeric day-of-week representation differs from
     * the ISO 8601 standard, and that the {@code weekOfYear}
     * numbering is compatible with the standard when {@code
     * getFirstDayOfWeek()} is {@code MONDAY} and {@code
     * getMinimalDaysInFirstWeek()} is 4.
     *
     * <p>Unlike the {@code set} method, all of the calendar fields
     * and the instant of time value are calculated upon return.
     *
     * <p>If {@code weekOfYear} is out of the valid week-of-year
     * range in {@code weekYear}, the {@code weekYear}
     * and {@code weekOfYear} values are adjusted in lenient
     * mode, or an {@code IllegalArgumentException} is thrown in
     * non-lenient mode.
     *
     * <p>
     * 将此{@code GregorianCalendar}设置为日期说明符所指定的日期 -  <a href=\"#week_year\"> {@code weekYear} </a>,{@code weekOfYear}
     * 和{@code dayOfWeek} {@code weekOfYear}遵循<a href=\"#week_and_year\"> {@code WEEK_OF_YEAR}编号</a> {@code dayOfWeek}
     * 值必须是{@link日历#DAY_OF_WEEK DAY_OF_WEEK}值之一：{@link Calendar# SUNDAY SUNDAY}到{@link日历#SATURDAY SATURDAY}。
     * 
     *  <p>请注意,星期几的表示与ISO 8601标准不同,{@code getFirstDayOfWeek()}为{@code MONDAY}时,{@code weekOfYear}编号与标准兼容, @c
     * ode getMinimalDaysInFirstWeek()}是4。
     * 
     * <p>与{@code set}方法不同,所有日历字段和时间值都会在返回时计算
     * 
     *  <p>如果{@code weekOfYear}在{@code weekYear}的有效星期范围之外,{@code weekYear}和{@code weekOfYear}值会在宽松模式下调整,或者{@code weekYear}
     * 代码IllegalArgumentException}在非宽松模式中抛出。
     * 
     * 
     * @param weekYear    the week year
     * @param weekOfYear  the week number based on {@code weekYear}
     * @param dayOfWeek   the day of week value: one of the constants
     *                    for the {@link #DAY_OF_WEEK DAY_OF_WEEK} field:
     *                    {@link Calendar#SUNDAY SUNDAY}, ...,
     *                    {@link Calendar#SATURDAY SATURDAY}.
     * @exception IllegalArgumentException
     *            if any of the given date specifiers is invalid,
     *            or if any of the calendar fields are inconsistent
     *            with the given date specifiers in non-lenient mode
     * @see GregorianCalendar#isWeekDateSupported()
     * @see Calendar#getFirstDayOfWeek()
     * @see Calendar#getMinimalDaysInFirstWeek()
     * @since 1.7
     */
    @Override
    public void setWeekDate(int weekYear, int weekOfYear, int dayOfWeek) {
        if (dayOfWeek < SUNDAY || dayOfWeek > SATURDAY) {
            throw new IllegalArgumentException("invalid dayOfWeek: " + dayOfWeek);
        }

        // To avoid changing the time of day fields by date
        // calculations, use a clone with the GMT time zone.
        GregorianCalendar gc = (GregorianCalendar) clone();
        gc.setLenient(true);
        int era = gc.get(ERA);
        gc.clear();
        gc.setTimeZone(TimeZone.getTimeZone("GMT"));
        gc.set(ERA, era);
        gc.set(YEAR, weekYear);
        gc.set(WEEK_OF_YEAR, 1);
        gc.set(DAY_OF_WEEK, getFirstDayOfWeek());
        int days = dayOfWeek - getFirstDayOfWeek();
        if (days < 0) {
            days += 7;
        }
        days += 7 * (weekOfYear - 1);
        if (days != 0) {
            gc.add(DAY_OF_YEAR, days);
        } else {
            gc.complete();
        }

        if (!isLenient() &&
            (gc.getWeekYear() != weekYear
             || gc.internalGet(WEEK_OF_YEAR) != weekOfYear
             || gc.internalGet(DAY_OF_WEEK) != dayOfWeek)) {
            throw new IllegalArgumentException();
        }

        set(ERA, gc.internalGet(ERA));
        set(YEAR, gc.internalGet(YEAR));
        set(MONTH, gc.internalGet(MONTH));
        set(DAY_OF_MONTH, gc.internalGet(DAY_OF_MONTH));

        // to avoid throwing an IllegalArgumentException in
        // non-lenient, set WEEK_OF_YEAR internally
        internalSet(WEEK_OF_YEAR, weekOfYear);
        complete();
    }

    /**
     * Returns the number of weeks in the <a href="#week_year">week year</a>
     * represented by this {@code GregorianCalendar}.
     *
     * <p>For example, if this {@code GregorianCalendar}'s date is
     * December 31, 2008 with <a href="#iso8601_compatible_setting">the ISO
     * 8601 compatible setting</a>, this method will return 53 for the
     * period: December 29, 2008 to January 3, 2010 while {@link
     * #getActualMaximum(int) getActualMaximum(WEEK_OF_YEAR)} will return
     * 52 for the period: December 31, 2007 to December 28, 2008.
     *
     * <p>
     *  返回由此{@code GregorianCalendar}表示的<a href=\"#week_year\">周年</a>中的周数
     * 
     * <p>例如,如果{@code GregorianCalendar}的日期为2008年12月31日并且使用<a href=\"#iso8601_compatible_setting\"> ISO 8601
     * 兼容设置</a>,则此方法将在以下期间返回53： 2008年12月29日至2010年1月3日,而{@ link #getActualMaximum(int)getActualMaximum(WEEK_OF_YEAR)}
     * 将在此期间返回52：2007年12月31日至2008年12月28日。
     * 
     * 
     * @return the number of weeks in the week year.
     * @see Calendar#WEEK_OF_YEAR
     * @see #getWeekYear()
     * @see #getActualMaximum(int)
     * @since 1.7
     */
    @Override
    public int getWeeksInWeekYear() {
        GregorianCalendar gc = getNormalizedCalendar();
        int weekYear = gc.getWeekYear();
        if (weekYear == gc.internalGet(YEAR)) {
            return gc.getActualMaximum(WEEK_OF_YEAR);
        }

        // Use the 2nd week for calculating the max of WEEK_OF_YEAR
        if (gc == this) {
            gc = (GregorianCalendar) gc.clone();
        }
        gc.setWeekDate(weekYear, 2, internalGet(DAY_OF_WEEK));
        return gc.getActualMaximum(WEEK_OF_YEAR);
    }

/////////////////////////////
// Time => Fields computation
/////////////////////////////

    /**
     * The fixed date corresponding to gdate. If the value is
     * Long.MIN_VALUE, the fixed date value is unknown. Currently,
     * Julian calendar dates are not cached.
     * <p>
     *  与gdate对应的固定日期如果值为LongMIN_VALUE,则固定日期值为unknown目前,不对Julian日历日期进行缓存
     * 
     */
    transient private long cachedFixedDate = Long.MIN_VALUE;

    /**
     * Converts the time value (millisecond offset from the <a
     * href="Calendar.html#Epoch">Epoch</a>) to calendar field values.
     * The time is <em>not</em>
     * recomputed first; to recompute the time, then the fields, call the
     * <code>complete</code> method.
     *
     * <p>
     *  将时间值(与<a href=\"Calendarhtml#Epoch\">时代</a>的毫秒偏移量)转换为日历字段值首先重新计算时间<em> </em>;重新计算时间,然后是字段,调用<code> c
     * omplete </code>方法。
     * 
     * 
     * @see Calendar#complete
     */
    @Override
    protected void computeFields() {
        int mask;
        if (isPartiallyNormalized()) {
            // Determine which calendar fields need to be computed.
            mask = getSetStateFields();
            int fieldMask = ~mask & ALL_FIELDS;
            // We have to call computTime in case calsys == null in
            // order to set calsys and cdate. (6263644)
            if (fieldMask != 0 || calsys == null) {
                mask |= computeFields(fieldMask,
                                      mask & (ZONE_OFFSET_MASK|DST_OFFSET_MASK));
                assert mask == ALL_FIELDS;
            }
        } else {
            mask = ALL_FIELDS;
            computeFields(mask, 0);
        }
        // After computing all the fields, set the field state to `COMPUTED'.
        setFieldsComputed(mask);
    }

    /**
     * This computeFields implements the conversion from UTC
     * (millisecond offset from the Epoch) to calendar
     * field values. fieldMask specifies which fields to change the
     * setting state to COMPUTED, although all fields are set to
     * the correct values. This is required to fix 4685354.
     *
     * <p>
     * 此computeFields实现从UTC(从Epoch的毫秒偏移)到日历字段值的转换fieldMask指定哪些字段将设置状态更改为COMPUTED,虽然所有字段都设置为正确的值这是需要修复的468535
     * 4。
     * 
     * 
     * @param fieldMask a bit mask to specify which fields to change
     * the setting state.
     * @param tzMask a bit mask to specify which time zone offset
     * fields to be used for time calculations
     * @return a new field mask that indicates what field values have
     * actually been set.
     */
    private int computeFields(int fieldMask, int tzMask) {
        int zoneOffset = 0;
        TimeZone tz = getZone();
        if (zoneOffsets == null) {
            zoneOffsets = new int[2];
        }
        if (tzMask != (ZONE_OFFSET_MASK|DST_OFFSET_MASK)) {
            if (tz instanceof ZoneInfo) {
                zoneOffset = ((ZoneInfo)tz).getOffsets(time, zoneOffsets);
            } else {
                zoneOffset = tz.getOffset(time);
                zoneOffsets[0] = tz.getRawOffset();
                zoneOffsets[1] = zoneOffset - zoneOffsets[0];
            }
        }
        if (tzMask != 0) {
            if (isFieldSet(tzMask, ZONE_OFFSET)) {
                zoneOffsets[0] = internalGet(ZONE_OFFSET);
            }
            if (isFieldSet(tzMask, DST_OFFSET)) {
                zoneOffsets[1] = internalGet(DST_OFFSET);
            }
            zoneOffset = zoneOffsets[0] + zoneOffsets[1];
        }

        // By computing time and zoneOffset separately, we can take
        // the wider range of time+zoneOffset than the previous
        // implementation.
        long fixedDate = zoneOffset / ONE_DAY;
        int timeOfDay = zoneOffset % (int)ONE_DAY;
        fixedDate += time / ONE_DAY;
        timeOfDay += (int) (time % ONE_DAY);
        if (timeOfDay >= ONE_DAY) {
            timeOfDay -= ONE_DAY;
            ++fixedDate;
        } else {
            while (timeOfDay < 0) {
                timeOfDay += ONE_DAY;
                --fixedDate;
            }
        }
        fixedDate += EPOCH_OFFSET;

        int era = CE;
        int year;
        if (fixedDate >= gregorianCutoverDate) {
            // Handle Gregorian dates.
            assert cachedFixedDate == Long.MIN_VALUE || gdate.isNormalized()
                        : "cache control: not normalized";
            assert cachedFixedDate == Long.MIN_VALUE ||
                   gcal.getFixedDate(gdate.getNormalizedYear(),
                                          gdate.getMonth(),
                                          gdate.getDayOfMonth(), gdate)
                                == cachedFixedDate
                        : "cache control: inconsictency" +
                          ", cachedFixedDate=" + cachedFixedDate +
                          ", computed=" +
                          gcal.getFixedDate(gdate.getNormalizedYear(),
                                                 gdate.getMonth(),
                                                 gdate.getDayOfMonth(),
                                                 gdate) +
                          ", date=" + gdate;

            // See if we can use gdate to avoid date calculation.
            if (fixedDate != cachedFixedDate) {
                gcal.getCalendarDateFromFixedDate(gdate, fixedDate);
                cachedFixedDate = fixedDate;
            }

            year = gdate.getYear();
            if (year <= 0) {
                year = 1 - year;
                era = BCE;
            }
            calsys = gcal;
            cdate = gdate;
            assert cdate.getDayOfWeek() > 0 : "dow="+cdate.getDayOfWeek()+", date="+cdate;
        } else {
            // Handle Julian calendar dates.
            calsys = getJulianCalendarSystem();
            cdate = (BaseCalendar.Date) jcal.newCalendarDate(getZone());
            jcal.getCalendarDateFromFixedDate(cdate, fixedDate);
            Era e = cdate.getEra();
            if (e == jeras[0]) {
                era = BCE;
            }
            year = cdate.getYear();
        }

        // Always set the ERA and YEAR values.
        internalSet(ERA, era);
        internalSet(YEAR, year);
        int mask = fieldMask | (ERA_MASK|YEAR_MASK);

        int month =  cdate.getMonth() - 1; // 0-based
        int dayOfMonth = cdate.getDayOfMonth();

        // Set the basic date fields.
        if ((fieldMask & (MONTH_MASK|DAY_OF_MONTH_MASK|DAY_OF_WEEK_MASK))
            != 0) {
            internalSet(MONTH, month);
            internalSet(DAY_OF_MONTH, dayOfMonth);
            internalSet(DAY_OF_WEEK, cdate.getDayOfWeek());
            mask |= MONTH_MASK|DAY_OF_MONTH_MASK|DAY_OF_WEEK_MASK;
        }

        if ((fieldMask & (HOUR_OF_DAY_MASK|AM_PM_MASK|HOUR_MASK
                          |MINUTE_MASK|SECOND_MASK|MILLISECOND_MASK)) != 0) {
            if (timeOfDay != 0) {
                int hours = timeOfDay / ONE_HOUR;
                internalSet(HOUR_OF_DAY, hours);
                internalSet(AM_PM, hours / 12); // Assume AM == 0
                internalSet(HOUR, hours % 12);
                int r = timeOfDay % ONE_HOUR;
                internalSet(MINUTE, r / ONE_MINUTE);
                r %= ONE_MINUTE;
                internalSet(SECOND, r / ONE_SECOND);
                internalSet(MILLISECOND, r % ONE_SECOND);
            } else {
                internalSet(HOUR_OF_DAY, 0);
                internalSet(AM_PM, AM);
                internalSet(HOUR, 0);
                internalSet(MINUTE, 0);
                internalSet(SECOND, 0);
                internalSet(MILLISECOND, 0);
            }
            mask |= (HOUR_OF_DAY_MASK|AM_PM_MASK|HOUR_MASK
                     |MINUTE_MASK|SECOND_MASK|MILLISECOND_MASK);
        }

        if ((fieldMask & (ZONE_OFFSET_MASK|DST_OFFSET_MASK)) != 0) {
            internalSet(ZONE_OFFSET, zoneOffsets[0]);
            internalSet(DST_OFFSET, zoneOffsets[1]);
            mask |= (ZONE_OFFSET_MASK|DST_OFFSET_MASK);
        }

        if ((fieldMask & (DAY_OF_YEAR_MASK|WEEK_OF_YEAR_MASK|WEEK_OF_MONTH_MASK|DAY_OF_WEEK_IN_MONTH_MASK)) != 0) {
            int normalizedYear = cdate.getNormalizedYear();
            long fixedDateJan1 = calsys.getFixedDate(normalizedYear, 1, 1, cdate);
            int dayOfYear = (int)(fixedDate - fixedDateJan1) + 1;
            long fixedDateMonth1 = fixedDate - dayOfMonth + 1;
            int cutoverGap = 0;
            int cutoverYear = (calsys == gcal) ? gregorianCutoverYear : gregorianCutoverYearJulian;
            int relativeDayOfMonth = dayOfMonth - 1;

            // If we are in the cutover year, we need some special handling.
            if (normalizedYear == cutoverYear) {
                // Need to take care of the "missing" days.
                if (gregorianCutoverYearJulian <= gregorianCutoverYear) {
                    // We need to find out where we are. The cutover
                    // gap could even be more than one year.  (One
                    // year difference in ~48667 years.)
                    fixedDateJan1 = getFixedDateJan1(cdate, fixedDate);
                    if (fixedDate >= gregorianCutoverDate) {
                        fixedDateMonth1 = getFixedDateMonth1(cdate, fixedDate);
                    }
                }
                int realDayOfYear = (int)(fixedDate - fixedDateJan1) + 1;
                cutoverGap = dayOfYear - realDayOfYear;
                dayOfYear = realDayOfYear;
                relativeDayOfMonth = (int)(fixedDate - fixedDateMonth1);
            }
            internalSet(DAY_OF_YEAR, dayOfYear);
            internalSet(DAY_OF_WEEK_IN_MONTH, relativeDayOfMonth / 7 + 1);

            int weekOfYear = getWeekNumber(fixedDateJan1, fixedDate);

            // The spec is to calculate WEEK_OF_YEAR in the
            // ISO8601-style. This creates problems, though.
            if (weekOfYear == 0) {
                // If the date belongs to the last week of the
                // previous year, use the week number of "12/31" of
                // the "previous" year. Again, if the previous year is
                // the Gregorian cutover year, we need to take care of
                // it.  Usually the previous day of January 1 is
                // December 31, which is not always true in
                // GregorianCalendar.
                long fixedDec31 = fixedDateJan1 - 1;
                long prevJan1  = fixedDateJan1 - 365;
                if (normalizedYear > (cutoverYear + 1)) {
                    if (CalendarUtils.isGregorianLeapYear(normalizedYear - 1)) {
                        --prevJan1;
                    }
                } else if (normalizedYear <= gregorianCutoverYearJulian) {
                    if (CalendarUtils.isJulianLeapYear(normalizedYear - 1)) {
                        --prevJan1;
                    }
                } else {
                    BaseCalendar calForJan1 = calsys;
                    //int prevYear = normalizedYear - 1;
                    int prevYear = getCalendarDate(fixedDec31).getNormalizedYear();
                    if (prevYear == gregorianCutoverYear) {
                        calForJan1 = getCutoverCalendarSystem();
                        if (calForJan1 == jcal) {
                            prevJan1 = calForJan1.getFixedDate(prevYear,
                                                               BaseCalendar.JANUARY,
                                                               1,
                                                               null);
                        } else {
                            prevJan1 = gregorianCutoverDate;
                            calForJan1 = gcal;
                        }
                    } else if (prevYear <= gregorianCutoverYearJulian) {
                        calForJan1 = getJulianCalendarSystem();
                        prevJan1 = calForJan1.getFixedDate(prevYear,
                                                           BaseCalendar.JANUARY,
                                                           1,
                                                           null);
                    }
                }
                weekOfYear = getWeekNumber(prevJan1, fixedDec31);
            } else {
                if (normalizedYear > gregorianCutoverYear ||
                    normalizedYear < (gregorianCutoverYearJulian - 1)) {
                    // Regular years
                    if (weekOfYear >= 52) {
                        long nextJan1 = fixedDateJan1 + 365;
                        if (cdate.isLeapYear()) {
                            nextJan1++;
                        }
                        long nextJan1st = BaseCalendar.getDayOfWeekDateOnOrBefore(nextJan1 + 6,
                                                                                  getFirstDayOfWeek());
                        int ndays = (int)(nextJan1st - nextJan1);
                        if (ndays >= getMinimalDaysInFirstWeek() && fixedDate >= (nextJan1st - 7)) {
                            // The first days forms a week in which the date is included.
                            weekOfYear = 1;
                        }
                    }
                } else {
                    BaseCalendar calForJan1 = calsys;
                    int nextYear = normalizedYear + 1;
                    if (nextYear == (gregorianCutoverYearJulian + 1) &&
                        nextYear < gregorianCutoverYear) {
                        // In case the gap is more than one year.
                        nextYear = gregorianCutoverYear;
                    }
                    if (nextYear == gregorianCutoverYear) {
                        calForJan1 = getCutoverCalendarSystem();
                    }

                    long nextJan1;
                    if (nextYear > gregorianCutoverYear
                        || gregorianCutoverYearJulian == gregorianCutoverYear
                        || nextYear == gregorianCutoverYearJulian) {
                        nextJan1 = calForJan1.getFixedDate(nextYear,
                                                           BaseCalendar.JANUARY,
                                                           1,
                                                           null);
                    } else {
                        nextJan1 = gregorianCutoverDate;
                        calForJan1 = gcal;
                    }

                    long nextJan1st = BaseCalendar.getDayOfWeekDateOnOrBefore(nextJan1 + 6,
                                                                              getFirstDayOfWeek());
                    int ndays = (int)(nextJan1st - nextJan1);
                    if (ndays >= getMinimalDaysInFirstWeek() && fixedDate >= (nextJan1st - 7)) {
                        // The first days forms a week in which the date is included.
                        weekOfYear = 1;
                    }
                }
            }
            internalSet(WEEK_OF_YEAR, weekOfYear);
            internalSet(WEEK_OF_MONTH, getWeekNumber(fixedDateMonth1, fixedDate));
            mask |= (DAY_OF_YEAR_MASK|WEEK_OF_YEAR_MASK|WEEK_OF_MONTH_MASK|DAY_OF_WEEK_IN_MONTH_MASK);
        }
        return mask;
    }

    /**
     * Returns the number of weeks in a period between fixedDay1 and
     * fixedDate. The getFirstDayOfWeek-getMinimalDaysInFirstWeek rule
     * is applied to calculate the number of weeks.
     *
     * <p>
     *  返回在fixedDay1和fixedDate之间的周期内的周数getFirstDayOfWeek-getMinimalDaysInFirstWeek规则用于计算周数
     * 
     * 
     * @param fixedDay1 the fixed date of the first day of the period
     * @param fixedDate the fixed date of the last day of the period
     * @return the number of weeks of the given period
     */
    private int getWeekNumber(long fixedDay1, long fixedDate) {
        // We can always use `gcal' since Julian and Gregorian are the
        // same thing for this calculation.
        long fixedDay1st = Gregorian.getDayOfWeekDateOnOrBefore(fixedDay1 + 6,
                                                                getFirstDayOfWeek());
        int ndays = (int)(fixedDay1st - fixedDay1);
        assert ndays <= 7;
        if (ndays >= getMinimalDaysInFirstWeek()) {
            fixedDay1st -= 7;
        }
        int normalizedDayOfPeriod = (int)(fixedDate - fixedDay1st);
        if (normalizedDayOfPeriod >= 0) {
            return normalizedDayOfPeriod / 7 + 1;
        }
        return CalendarUtils.floorDivide(normalizedDayOfPeriod, 7) + 1;
    }

    /**
     * Converts calendar field values to the time value (millisecond
     * offset from the <a href="Calendar.html#Epoch">Epoch</a>).
     *
     * <p>
     *  将日历字段值转换为时间值(距离<a href=\"Calendarhtml#Epoch\">时代的毫秒偏移量</a>)
     * 
     * 
     * @exception IllegalArgumentException if any calendar fields are invalid.
     */
    @Override
    protected void computeTime() {
        // In non-lenient mode, perform brief checking of calendar
        // fields which have been set externally. Through this
        // checking, the field values are stored in originalFields[]
        // to see if any of them are normalized later.
        if (!isLenient()) {
            if (originalFields == null) {
                originalFields = new int[FIELD_COUNT];
            }
            for (int field = 0; field < FIELD_COUNT; field++) {
                int value = internalGet(field);
                if (isExternallySet(field)) {
                    // Quick validation for any out of range values
                    if (value < getMinimum(field) || value > getMaximum(field)) {
                        throw new IllegalArgumentException(getFieldName(field));
                    }
                }
                originalFields[field] = value;
            }
        }

        // Let the super class determine which calendar fields to be
        // used to calculate the time.
        int fieldMask = selectFields();

        // The year defaults to the epoch start. We don't check
        // fieldMask for YEAR because YEAR is a mandatory field to
        // determine the date.
        int year = isSet(YEAR) ? internalGet(YEAR) : EPOCH_YEAR;

        int era = internalGetEra();
        if (era == BCE) {
            year = 1 - year;
        } else if (era != CE) {
            // Even in lenient mode we disallow ERA values other than CE & BCE.
            // (The same normalization rule as add()/roll() could be
            // applied here in lenient mode. But this checking is kept
            // unchanged for compatibility as of 1.5.)
            throw new IllegalArgumentException("Invalid era");
        }

        // If year is 0 or negative, we need to set the ERA value later.
        if (year <= 0 && !isSet(ERA)) {
            fieldMask |= ERA_MASK;
            setFieldsComputed(ERA_MASK);
        }

        // Calculate the time of day. We rely on the convention that
        // an UNSET field has 0.
        long timeOfDay = 0;
        if (isFieldSet(fieldMask, HOUR_OF_DAY)) {
            timeOfDay += (long) internalGet(HOUR_OF_DAY);
        } else {
            timeOfDay += internalGet(HOUR);
            // The default value of AM_PM is 0 which designates AM.
            if (isFieldSet(fieldMask, AM_PM)) {
                timeOfDay += 12 * internalGet(AM_PM);
            }
        }
        timeOfDay *= 60;
        timeOfDay += internalGet(MINUTE);
        timeOfDay *= 60;
        timeOfDay += internalGet(SECOND);
        timeOfDay *= 1000;
        timeOfDay += internalGet(MILLISECOND);

        // Convert the time of day to the number of days and the
        // millisecond offset from midnight.
        long fixedDate = timeOfDay / ONE_DAY;
        timeOfDay %= ONE_DAY;
        while (timeOfDay < 0) {
            timeOfDay += ONE_DAY;
            --fixedDate;
        }

        // Calculate the fixed date since January 1, 1 (Gregorian).
        calculateFixedDate: {
            long gfd, jfd;
            if (year > gregorianCutoverYear && year > gregorianCutoverYearJulian) {
                gfd = fixedDate + getFixedDate(gcal, year, fieldMask);
                if (gfd >= gregorianCutoverDate) {
                    fixedDate = gfd;
                    break calculateFixedDate;
                }
                jfd = fixedDate + getFixedDate(getJulianCalendarSystem(), year, fieldMask);
            } else if (year < gregorianCutoverYear && year < gregorianCutoverYearJulian) {
                jfd = fixedDate + getFixedDate(getJulianCalendarSystem(), year, fieldMask);
                if (jfd < gregorianCutoverDate) {
                    fixedDate = jfd;
                    break calculateFixedDate;
                }
                gfd = jfd;
            } else {
                jfd = fixedDate + getFixedDate(getJulianCalendarSystem(), year, fieldMask);
                gfd = fixedDate + getFixedDate(gcal, year, fieldMask);
            }

            // Now we have to determine which calendar date it is.

            // If the date is relative from the beginning of the year
            // in the Julian calendar, then use jfd;
            if (isFieldSet(fieldMask, DAY_OF_YEAR) || isFieldSet(fieldMask, WEEK_OF_YEAR)) {
                if (gregorianCutoverYear == gregorianCutoverYearJulian) {
                    fixedDate = jfd;
                    break calculateFixedDate;
                } else if (year == gregorianCutoverYear) {
                    fixedDate = gfd;
                    break calculateFixedDate;
                }
            }

            if (gfd >= gregorianCutoverDate) {
                if (jfd >= gregorianCutoverDate) {
                    fixedDate = gfd;
                } else {
                    // The date is in an "overlapping" period. No way
                    // to disambiguate it. Determine it using the
                    // previous date calculation.
                    if (calsys == gcal || calsys == null) {
                        fixedDate = gfd;
                    } else {
                        fixedDate = jfd;
                    }
                }
            } else {
                if (jfd < gregorianCutoverDate) {
                    fixedDate = jfd;
                } else {
                    // The date is in a "missing" period.
                    if (!isLenient()) {
                        throw new IllegalArgumentException("the specified date doesn't exist");
                    }
                    // Take the Julian date for compatibility, which
                    // will produce a Gregorian date.
                    fixedDate = jfd;
                }
            }
        }

        // millis represents local wall-clock time in milliseconds.
        long millis = (fixedDate - EPOCH_OFFSET) * ONE_DAY + timeOfDay;

        // Compute the time zone offset and DST offset.  There are two potential
        // ambiguities here.  We'll assume a 2:00 am (wall time) switchover time
        // for discussion purposes here.
        // 1. The transition into DST.  Here, a designated time of 2:00 am - 2:59 am
        //    can be in standard or in DST depending.  However, 2:00 am is an invalid
        //    representation (the representation jumps from 1:59:59 am Std to 3:00:00 am DST).
        //    We assume standard time.
        // 2. The transition out of DST.  Here, a designated time of 1:00 am - 1:59 am
        //    can be in standard or DST.  Both are valid representations (the rep
        //    jumps from 1:59:59 DST to 1:00:00 Std).
        //    Again, we assume standard time.
        // We use the TimeZone object, unless the user has explicitly set the ZONE_OFFSET
        // or DST_OFFSET fields; then we use those fields.
        TimeZone zone = getZone();
        if (zoneOffsets == null) {
            zoneOffsets = new int[2];
        }
        int tzMask = fieldMask & (ZONE_OFFSET_MASK|DST_OFFSET_MASK);
        if (tzMask != (ZONE_OFFSET_MASK|DST_OFFSET_MASK)) {
            if (zone instanceof ZoneInfo) {
                ((ZoneInfo)zone).getOffsetsByWall(millis, zoneOffsets);
            } else {
                int gmtOffset = isFieldSet(fieldMask, ZONE_OFFSET) ?
                                    internalGet(ZONE_OFFSET) : zone.getRawOffset();
                zone.getOffsets(millis - gmtOffset, zoneOffsets);
            }
        }
        if (tzMask != 0) {
            if (isFieldSet(tzMask, ZONE_OFFSET)) {
                zoneOffsets[0] = internalGet(ZONE_OFFSET);
            }
            if (isFieldSet(tzMask, DST_OFFSET)) {
                zoneOffsets[1] = internalGet(DST_OFFSET);
            }
        }

        // Adjust the time zone offset values to get the UTC time.
        millis -= zoneOffsets[0] + zoneOffsets[1];

        // Set this calendar's time in milliseconds
        time = millis;

        int mask = computeFields(fieldMask | getSetStateFields(), tzMask);

        if (!isLenient()) {
            for (int field = 0; field < FIELD_COUNT; field++) {
                if (!isExternallySet(field)) {
                    continue;
                }
                if (originalFields[field] != internalGet(field)) {
                    String s = originalFields[field] + " -> " + internalGet(field);
                    // Restore the original field values
                    System.arraycopy(originalFields, 0, fields, 0, fields.length);
                    throw new IllegalArgumentException(getFieldName(field) + ": " + s);
                }
            }
        }
        setFieldsNormalized(mask);
    }

    /**
     * Computes the fixed date under either the Gregorian or the
     * Julian calendar, using the given year and the specified calendar fields.
     *
     * <p>
     *  使用给定年份和指定的日历字段计算格里高利历史或儒略历日历下的固定日期
     * 
     * 
     * @param cal the CalendarSystem to be used for the date calculation
     * @param year the normalized year number, with 0 indicating the
     * year 1 BCE, -1 indicating 2 BCE, etc.
     * @param fieldMask the calendar fields to be used for the date calculation
     * @return the fixed date
     * @see Calendar#selectFields
     */
    private long getFixedDate(BaseCalendar cal, int year, int fieldMask) {
        int month = JANUARY;
        if (isFieldSet(fieldMask, MONTH)) {
            // No need to check if MONTH has been set (no isSet(MONTH)
            // call) since its unset value happens to be JANUARY (0).
            month = internalGet(MONTH);

            // If the month is out of range, adjust it into range
            if (month > DECEMBER) {
                year += month / 12;
                month %= 12;
            } else if (month < JANUARY) {
                int[] rem = new int[1];
                year += CalendarUtils.floorDivide(month, 12, rem);
                month = rem[0];
            }
        }

        // Get the fixed date since Jan 1, 1 (Gregorian). We are on
        // the first day of either `month' or January in 'year'.
        long fixedDate = cal.getFixedDate(year, month + 1, 1,
                                          cal == gcal ? gdate : null);
        if (isFieldSet(fieldMask, MONTH)) {
            // Month-based calculations
            if (isFieldSet(fieldMask, DAY_OF_MONTH)) {
                // We are on the first day of the month. Just add the
                // offset if DAY_OF_MONTH is set. If the isSet call
                // returns false, that means DAY_OF_MONTH has been
                // selected just because of the selected
                // combination. We don't need to add any since the
                // default value is the 1st.
                if (isSet(DAY_OF_MONTH)) {
                    // To avoid underflow with DAY_OF_MONTH-1, add
                    // DAY_OF_MONTH, then subtract 1.
                    fixedDate += internalGet(DAY_OF_MONTH);
                    fixedDate--;
                }
            } else {
                if (isFieldSet(fieldMask, WEEK_OF_MONTH)) {
                    long firstDayOfWeek = BaseCalendar.getDayOfWeekDateOnOrBefore(fixedDate + 6,
                                                                                  getFirstDayOfWeek());
                    // If we have enough days in the first week, then
                    // move to the previous week.
                    if ((firstDayOfWeek - fixedDate) >= getMinimalDaysInFirstWeek()) {
                        firstDayOfWeek -= 7;
                    }
                    if (isFieldSet(fieldMask, DAY_OF_WEEK)) {
                        firstDayOfWeek = BaseCalendar.getDayOfWeekDateOnOrBefore(firstDayOfWeek + 6,
                                                                                 internalGet(DAY_OF_WEEK));
                    }
                    // In lenient mode, we treat days of the previous
                    // months as a part of the specified
                    // WEEK_OF_MONTH. See 4633646.
                    fixedDate = firstDayOfWeek + 7 * (internalGet(WEEK_OF_MONTH) - 1);
                } else {
                    int dayOfWeek;
                    if (isFieldSet(fieldMask, DAY_OF_WEEK)) {
                        dayOfWeek = internalGet(DAY_OF_WEEK);
                    } else {
                        dayOfWeek = getFirstDayOfWeek();
                    }
                    // We are basing this on the day-of-week-in-month.  The only
                    // trickiness occurs if the day-of-week-in-month is
                    // negative.
                    int dowim;
                    if (isFieldSet(fieldMask, DAY_OF_WEEK_IN_MONTH)) {
                        dowim = internalGet(DAY_OF_WEEK_IN_MONTH);
                    } else {
                        dowim = 1;
                    }
                    if (dowim >= 0) {
                        fixedDate = BaseCalendar.getDayOfWeekDateOnOrBefore(fixedDate + (7 * dowim) - 1,
                                                                            dayOfWeek);
                    } else {
                        // Go to the first day of the next week of
                        // the specified week boundary.
                        int lastDate = monthLength(month, year) + (7 * (dowim + 1));
                        // Then, get the day of week date on or before the last date.
                        fixedDate = BaseCalendar.getDayOfWeekDateOnOrBefore(fixedDate + lastDate - 1,
                                                                            dayOfWeek);
                    }
                }
            }
        } else {
            if (year == gregorianCutoverYear && cal == gcal
                && fixedDate < gregorianCutoverDate
                && gregorianCutoverYear != gregorianCutoverYearJulian) {
                // January 1 of the year doesn't exist.  Use
                // gregorianCutoverDate as the first day of the
                // year.
                fixedDate = gregorianCutoverDate;
            }
            // We are on the first day of the year.
            if (isFieldSet(fieldMask, DAY_OF_YEAR)) {
                // Add the offset, then subtract 1. (Make sure to avoid underflow.)
                fixedDate += internalGet(DAY_OF_YEAR);
                fixedDate--;
            } else {
                long firstDayOfWeek = BaseCalendar.getDayOfWeekDateOnOrBefore(fixedDate + 6,
                                                                              getFirstDayOfWeek());
                // If we have enough days in the first week, then move
                // to the previous week.
                if ((firstDayOfWeek - fixedDate) >= getMinimalDaysInFirstWeek()) {
                    firstDayOfWeek -= 7;
                }
                if (isFieldSet(fieldMask, DAY_OF_WEEK)) {
                    int dayOfWeek = internalGet(DAY_OF_WEEK);
                    if (dayOfWeek != getFirstDayOfWeek()) {
                        firstDayOfWeek = BaseCalendar.getDayOfWeekDateOnOrBefore(firstDayOfWeek + 6,
                                                                                 dayOfWeek);
                    }
                }
                fixedDate = firstDayOfWeek + 7 * ((long)internalGet(WEEK_OF_YEAR) - 1);
            }
        }

        return fixedDate;
    }

    /**
     * Returns this object if it's normalized (all fields and time are
     * in sync). Otherwise, a cloned object is returned after calling
     * complete() in lenient mode.
     * <p>
     * 如果它被规范化(所有字段和时间都是同步的),则返回此对象。否则,在宽松模式下调用complete()后将返回一个克隆对象
     * 
     */
    private GregorianCalendar getNormalizedCalendar() {
        GregorianCalendar gc;
        if (isFullyNormalized()) {
            gc = this;
        } else {
            // Create a clone and normalize the calendar fields
            gc = (GregorianCalendar) this.clone();
            gc.setLenient(true);
            gc.complete();
        }
        return gc;
    }

    /**
     * Returns the Julian calendar system instance (singleton). 'jcal'
     * and 'jeras' are set upon the return.
     * <p>
     *  返回Julian日历系统实例(单例)'jcal'和'jeras'在返回时设置
     * 
     */
    private static synchronized BaseCalendar getJulianCalendarSystem() {
        if (jcal == null) {
            jcal = (JulianCalendar) CalendarSystem.forName("julian");
            jeras = jcal.getEras();
        }
        return jcal;
    }

    /**
     * Returns the calendar system for dates before the cutover date
     * in the cutover year. If the cutover date is January 1, the
     * method returns Gregorian. Otherwise, Julian.
     * <p>
     *  返回割接年份中割接日期之前的日历系统如果割接日期为1月1日,则该方法返回Gregorian否则,Julian
     * 
     */
    private BaseCalendar getCutoverCalendarSystem() {
        if (gregorianCutoverYearJulian < gregorianCutoverYear) {
            return gcal;
        }
        return getJulianCalendarSystem();
    }

    /**
     * Determines if the specified year (normalized) is the Gregorian
     * cutover year. This object must have been normalized.
     * <p>
     *  确定指定年份(标准化)是否为格雷戈里切割年份此对象必须已经过规范化
     * 
     */
    private boolean isCutoverYear(int normalizedYear) {
        int cutoverYear = (calsys == gcal) ? gregorianCutoverYear : gregorianCutoverYearJulian;
        return normalizedYear == cutoverYear;
    }

    /**
     * Returns the fixed date of the first day of the year (usually
     * January 1) before the specified date.
     *
     * <p>
     *  返回指定日期之前一年中第一天(通常为1月1日)的固定日期
     * 
     * 
     * @param date the date for which the first day of the year is
     * calculated. The date has to be in the cut-over year (Gregorian
     * or Julian).
     * @param fixedDate the fixed date representation of the date
     */
    private long getFixedDateJan1(BaseCalendar.Date date, long fixedDate) {
        assert date.getNormalizedYear() == gregorianCutoverYear ||
            date.getNormalizedYear() == gregorianCutoverYearJulian;
        if (gregorianCutoverYear != gregorianCutoverYearJulian) {
            if (fixedDate >= gregorianCutoverDate) {
                // Dates before the cutover date don't exist
                // in the same (Gregorian) year. So, no
                // January 1 exists in the year. Use the
                // cutover date as the first day of the year.
                return gregorianCutoverDate;
            }
        }
        // January 1 of the normalized year should exist.
        BaseCalendar juliancal = getJulianCalendarSystem();
        return juliancal.getFixedDate(date.getNormalizedYear(), BaseCalendar.JANUARY, 1, null);
    }

    /**
     * Returns the fixed date of the first date of the month (usually
     * the 1st of the month) before the specified date.
     *
     * <p>
     *  返回指定日期之前的月份的第一个日期(通常为月份的第一天)的固定日期
     * 
     * 
     * @param date the date for which the first day of the month is
     * calculated. The date has to be in the cut-over year (Gregorian
     * or Julian).
     * @param fixedDate the fixed date representation of the date
     */
    private long getFixedDateMonth1(BaseCalendar.Date date, long fixedDate) {
        assert date.getNormalizedYear() == gregorianCutoverYear ||
            date.getNormalizedYear() == gregorianCutoverYearJulian;
        BaseCalendar.Date gCutover = getGregorianCutoverDate();
        if (gCutover.getMonth() == BaseCalendar.JANUARY
            && gCutover.getDayOfMonth() == 1) {
            // The cutover happened on January 1.
            return fixedDate - date.getDayOfMonth() + 1;
        }

        long fixedDateMonth1;
        // The cutover happened sometime during the year.
        if (date.getMonth() == gCutover.getMonth()) {
            // The cutover happened in the month.
            BaseCalendar.Date jLastDate = getLastJulianDate();
            if (gregorianCutoverYear == gregorianCutoverYearJulian
                && gCutover.getMonth() == jLastDate.getMonth()) {
                // The "gap" fits in the same month.
                fixedDateMonth1 = jcal.getFixedDate(date.getNormalizedYear(),
                                                    date.getMonth(),
                                                    1,
                                                    null);
            } else {
                // Use the cutover date as the first day of the month.
                fixedDateMonth1 = gregorianCutoverDate;
            }
        } else {
            // The cutover happened before the month.
            fixedDateMonth1 = fixedDate - date.getDayOfMonth() + 1;
        }

        return fixedDateMonth1;
    }

    /**
     * Returns a CalendarDate produced from the specified fixed date.
     *
     * <p>
     * 返回从指定的固定日期生成的CalendarDate
     * 
     * 
     * @param fd the fixed date
     */
    private BaseCalendar.Date getCalendarDate(long fd) {
        BaseCalendar cal = (fd >= gregorianCutoverDate) ? gcal : getJulianCalendarSystem();
        BaseCalendar.Date d = (BaseCalendar.Date) cal.newCalendarDate(TimeZone.NO_TIMEZONE);
        cal.getCalendarDateFromFixedDate(d, fd);
        return d;
    }

    /**
     * Returns the Gregorian cutover date as a BaseCalendar.Date. The
     * date is a Gregorian date.
     * <p>
     *  将Gregorian割接日期作为BaseCalendarDate返回日期是公历日期
     * 
     */
    private BaseCalendar.Date getGregorianCutoverDate() {
        return getCalendarDate(gregorianCutoverDate);
    }

    /**
     * Returns the day before the Gregorian cutover date as a
     * BaseCalendar.Date. The date is a Julian date.
     * <p>
     *  将Gregorian割接日期前一天作为BaseCalendarDate返回日期是Julian日期
     * 
     */
    private BaseCalendar.Date getLastJulianDate() {
        return getCalendarDate(gregorianCutoverDate - 1);
    }

    /**
     * Returns the length of the specified month in the specified
     * year. The year number must be normalized.
     *
     * <p>
     *  返回指定年份中指定月份的长度必须对年份数进行归一化
     * 
     * 
     * @see #isLeapYear(int)
     */
    private int monthLength(int month, int year) {
        return isLeapYear(year) ? LEAP_MONTH_LENGTH[month] : MONTH_LENGTH[month];
    }

    /**
     * Returns the length of the specified month in the year provided
     * by internalGet(YEAR).
     *
     * <p>
     *  返回由internalGet(YEAR)提供的年份中指定月份的长度
     * 
     * 
     * @see #isLeapYear(int)
     */
    private int monthLength(int month) {
        int year = internalGet(YEAR);
        if (internalGetEra() == BCE) {
            year = 1 - year;
        }
        return monthLength(month, year);
    }

    private int actualMonthLength() {
        int year = cdate.getNormalizedYear();
        if (year != gregorianCutoverYear && year != gregorianCutoverYearJulian) {
            return calsys.getMonthLength(cdate);
        }
        BaseCalendar.Date date = (BaseCalendar.Date) cdate.clone();
        long fd = calsys.getFixedDate(date);
        long month1 = getFixedDateMonth1(date, fd);
        long next1 = month1 + calsys.getMonthLength(date);
        if (next1 < gregorianCutoverDate) {
            return (int)(next1 - month1);
        }
        if (cdate != gdate) {
            date = (BaseCalendar.Date) gcal.newCalendarDate(TimeZone.NO_TIMEZONE);
        }
        gcal.getCalendarDateFromFixedDate(date, next1);
        next1 = getFixedDateMonth1(date, next1);
        return (int)(next1 - month1);
    }

    /**
     * Returns the length (in days) of the specified year. The year
     * must be normalized.
     * <p>
     *  返回指定年份的长度(以天为单位)年份必须标准化
     * 
     */
    private int yearLength(int year) {
        return isLeapYear(year) ? 366 : 365;
    }

    /**
     * Returns the length (in days) of the year provided by
     * internalGet(YEAR).
     * <p>
     *  返回由internalGet(YEAR)提供的年份(以天为单位)
     * 
     */
    private int yearLength() {
        int year = internalGet(YEAR);
        if (internalGetEra() == BCE) {
            year = 1 - year;
        }
        return yearLength(year);
    }

    /**
     * After adjustments such as add(MONTH), add(YEAR), we don't want the
     * month to jump around.  E.g., we don't want Jan 31 + 1 month to go to Mar
     * 3, we want it to go to Feb 28.  Adjustments which might run into this
     * problem call this method to retain the proper month.
     * <p>
     * 调整后,如添加(MONTH),添加(YEAR),我们不想让月份跳到Eg,我们不想要1月31 + 1个月去3月3日,我们想要去2月28日可能会遇到此问题的调整将调用此方法以保留正确的月份
     * 
     */
    private void pinDayOfMonth() {
        int year = internalGet(YEAR);
        int monthLen;
        if (year > gregorianCutoverYear || year < gregorianCutoverYearJulian) {
            monthLen = monthLength(internalGet(MONTH));
        } else {
            GregorianCalendar gc = getNormalizedCalendar();
            monthLen = gc.getActualMaximum(DAY_OF_MONTH);
        }
        int dom = internalGet(DAY_OF_MONTH);
        if (dom > monthLen) {
            set(DAY_OF_MONTH, monthLen);
        }
    }

    /**
     * Returns the fixed date value of this object. The time value and
     * calendar fields must be in synch.
     * <p>
     *  返回此对象的固定日期值时间值和日历字段必须同步
     * 
     */
    private long getCurrentFixedDate() {
        return (calsys == gcal) ? cachedFixedDate : calsys.getFixedDate(cdate);
    }

    /**
     * Returns the new value after 'roll'ing the specified value and amount.
     * <p>
     *  在滚动指定的值和数量后返回新值
     * 
     */
    private static int getRolledValue(int value, int amount, int min, int max) {
        assert value >= min && value <= max;
        int range = max - min + 1;
        amount %= range;
        int n = value + amount;
        if (n > max) {
            n -= range;
        } else if (n < min) {
            n += range;
        }
        assert n >= min && n <= max;
        return n;
    }

    /**
     * Returns the ERA.  We need a special method for this because the
     * default ERA is CE, but a zero (unset) ERA is BCE.
     * <p>
     *  返回ERA我们需要一个特殊的方法,因为默认ERA是CE,但零(未设置)ERA是BCE
     * 
     */
    private int internalGetEra() {
        return isSet(ERA) ? internalGet(ERA) : CE;
    }

    /**
     * Updates internal state.
     * <p>
     *  更新内部状态
     * 
     */
    private void readObject(ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        if (gdate == null) {
            gdate = (BaseCalendar.Date) gcal.newCalendarDate(getZone());
            cachedFixedDate = Long.MIN_VALUE;
        }
        setGregorianChange(gregorianCutover);
    }

    /**
     * Converts this object to a {@code ZonedDateTime} that represents
     * the same point on the time-line as this {@code GregorianCalendar}.
     * <p>
     * Since this object supports a Julian-Gregorian cutover date and
     * {@code ZonedDateTime} does not, it is possible that the resulting year,
     * month and day will have different values.  The result will represent the
     * correct date in the ISO calendar system, which will also be the same value
     * for Modified Julian Days.
     *
     * <p>
     *  将此对象转换为{@code ZonedDateTime},表示与此{@code GregorianCalendar}时间轴上的相同点
     * <p>
     * 由于此对象支持Julian-Gregorian割接日期,{@code ZonedDateTime}不支持,因此生成的年,月和日可能具有不同的值。
     * 结果将表示ISO日历系统中的正确日期,这也将修改儒略日的值相同。
     * 
     * 
     * @return a zoned date-time representing the same point on the time-line
     *  as this gregorian calendar
     * @since 1.8
     */
    public ZonedDateTime toZonedDateTime() {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(getTimeInMillis()),
                                       getTimeZone().toZoneId());
    }

    /**
     * Obtains an instance of {@code GregorianCalendar} with the default locale
     * from a {@code ZonedDateTime} object.
     * <p>
     * Since {@code ZonedDateTime} does not support a Julian-Gregorian cutover
     * date and uses ISO calendar system, the return GregorianCalendar is a pure
     * Gregorian calendar and uses ISO 8601 standard for week definitions,
     * which has {@code MONDAY} as the {@link Calendar#getFirstDayOfWeek()
     * FirstDayOfWeek} and {@code 4} as the value of the
     * {@link Calendar#getMinimalDaysInFirstWeek() MinimalDaysInFirstWeek}.
     * <p>
     * {@code ZoneDateTime} can store points on the time-line further in the
     * future and further in the past than {@code GregorianCalendar}. In this
     * scenario, this method will throw an {@code IllegalArgumentException}
     * exception.
     *
     * <p>
     *  使用{@code ZonedDateTime}对象的默认语言区域获取{@code GregorianCalendar}的实例
     * <p>
     * 由于{@code ZonedDateTime}不支持Julian-Gregorian切换日期并使用ISO日历系统,因此返回GregorianCalendar是一个纯粹的公历,对于星期定义使用ISO 86
     * 01标准,{@code MONDAY}作为{@link日历#getFirstDayOfWeek()FirstDayOfWeek}和{@code 4}作为{@link Calendar#getMinimalDaysInFirstWeek()MinimalDaysInFirstWeek}
     * 的值。
     * 
     * @param zdt  the zoned date-time object to convert
     * @return  the gregorian calendar representing the same point on the
     *  time-line as the zoned date-time provided
     * @exception NullPointerException if {@code zdt} is null
     * @exception IllegalArgumentException if the zoned date-time is too
     * large to represent as a {@code GregorianCalendar}
     * @since 1.8
     */
    public static GregorianCalendar from(ZonedDateTime zdt) {
        GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone(zdt.getZone()));
        cal.setGregorianChange(new Date(Long.MIN_VALUE));
        cal.setFirstDayOfWeek(MONDAY);
        cal.setMinimalDaysInFirstWeek(4);
        try {
            cal.setTimeInMillis(Math.addExact(Math.multiplyExact(zdt.toEpochSecond(), 1000),
                                              zdt.get(ChronoField.MILLI_OF_SECOND)));
        } catch (ArithmeticException ex) {
            throw new IllegalArgumentException(ex);
        }
        return cal;
    }
}
