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
 *  (C)版权Taligent,Inc. 1996-1998  - 保留所有权利(C)版权所有IBM Corp. 1996-1998  - 保留所有权利
 * 
 *  此源代码和文档的原始版本由IBM的全资子公司Taligent,Inc.拥有版权和所有权。这些材料是根据Taligent和Sun之间的许可协议的条款提供的。该技术受多项美国和国际专利保护。
 * 此通知和归因于Taligent不得删除。 Taligent是Taligent,Inc.的注册商标。
 * 
 */

package java.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PermissionCollection;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import sun.util.BuddhistCalendar;
import sun.util.calendar.ZoneInfo;
import sun.util.locale.provider.CalendarDataUtility;
import sun.util.locale.provider.LocaleProviderAdapter;
import sun.util.spi.CalendarProvider;

/**
 * The <code>Calendar</code> class is an abstract class that provides methods
 * for converting between a specific instant in time and a set of {@link
 * #fields calendar fields} such as <code>YEAR</code>, <code>MONTH</code>,
 * <code>DAY_OF_MONTH</code>, <code>HOUR</code>, and so on, and for
 * manipulating the calendar fields, such as getting the date of the next
 * week. An instant in time can be represented by a millisecond value that is
 * an offset from the <a name="Epoch"><em>Epoch</em></a>, January 1, 1970
 * 00:00:00.000 GMT (Gregorian).
 *
 * <p>The class also provides additional fields and methods for
 * implementing a concrete calendar system outside the package. Those
 * fields and methods are defined as <code>protected</code>.
 *
 * <p>
 * Like other locale-sensitive classes, <code>Calendar</code> provides a
 * class method, <code>getInstance</code>, for getting a generally useful
 * object of this type. <code>Calendar</code>'s <code>getInstance</code> method
 * returns a <code>Calendar</code> object whose
 * calendar fields have been initialized with the current date and time:
 * <blockquote>
 * <pre>
 *     Calendar rightNow = Calendar.getInstance();
 * </pre>
 * </blockquote>
 *
 * <p>A <code>Calendar</code> object can produce all the calendar field values
 * needed to implement the date-time formatting for a particular language and
 * calendar style (for example, Japanese-Gregorian, Japanese-Traditional).
 * <code>Calendar</code> defines the range of values returned by
 * certain calendar fields, as well as their meaning.  For example,
 * the first month of the calendar system has value <code>MONTH ==
 * JANUARY</code> for all calendars.  Other values are defined by the
 * concrete subclass, such as <code>ERA</code>.  See individual field
 * documentation and subclass documentation for details.
 *
 * <h3>Getting and Setting Calendar Field Values</h3>
 *
 * <p>The calendar field values can be set by calling the <code>set</code>
 * methods. Any field values set in a <code>Calendar</code> will not be
 * interpreted until it needs to calculate its time value (milliseconds from
 * the Epoch) or values of the calendar fields. Calling the
 * <code>get</code>, <code>getTimeInMillis</code>, <code>getTime</code>,
 * <code>add</code> and <code>roll</code> involves such calculation.
 *
 * <h4>Leniency</h4>
 *
 * <p><code>Calendar</code> has two modes for interpreting the calendar
 * fields, <em>lenient</em> and <em>non-lenient</em>.  When a
 * <code>Calendar</code> is in lenient mode, it accepts a wider range of
 * calendar field values than it produces.  When a <code>Calendar</code>
 * recomputes calendar field values for return by <code>get()</code>, all of
 * the calendar fields are normalized. For example, a lenient
 * <code>GregorianCalendar</code> interprets <code>MONTH == JANUARY</code>,
 * <code>DAY_OF_MONTH == 32</code> as February 1.

 * <p>When a <code>Calendar</code> is in non-lenient mode, it throws an
 * exception if there is any inconsistency in its calendar fields. For
 * example, a <code>GregorianCalendar</code> always produces
 * <code>DAY_OF_MONTH</code> values between 1 and the length of the month. A
 * non-lenient <code>GregorianCalendar</code> throws an exception upon
 * calculating its time or calendar field values if any out-of-range field
 * value has been set.
 *
 * <h4><a name="first_week">First Week</a></h4>
 *
 * <code>Calendar</code> defines a locale-specific seven day week using two
 * parameters: the first day of the week and the minimal days in first week
 * (from 1 to 7).  These numbers are taken from the locale resource data when a
 * <code>Calendar</code> is constructed.  They may also be specified explicitly
 * through the methods for setting their values.
 *
 * <p>When setting or getting the <code>WEEK_OF_MONTH</code> or
 * <code>WEEK_OF_YEAR</code> fields, <code>Calendar</code> must determine the
 * first week of the month or year as a reference point.  The first week of a
 * month or year is defined as the earliest seven day period beginning on
 * <code>getFirstDayOfWeek()</code> and containing at least
 * <code>getMinimalDaysInFirstWeek()</code> days of that month or year.  Weeks
 * numbered ..., -1, 0 precede the first week; weeks numbered 2, 3,... follow
 * it.  Note that the normalized numbering returned by <code>get()</code> may be
 * different.  For example, a specific <code>Calendar</code> subclass may
 * designate the week before week 1 of a year as week <code><i>n</i></code> of
 * the previous year.
 *
 * <h4>Calendar Fields Resolution</h4>
 *
 * When computing a date and time from the calendar fields, there
 * may be insufficient information for the computation (such as only
 * year and month with no day of month), or there may be inconsistent
 * information (such as Tuesday, July 15, 1996 (Gregorian) -- July 15,
 * 1996 is actually a Monday). <code>Calendar</code> will resolve
 * calendar field values to determine the date and time in the
 * following way.
 *
 * <p><a name="resolution">If there is any conflict in calendar field values,
 * <code>Calendar</code> gives priorities to calendar fields that have been set
 * more recently.</a> The following are the default combinations of the
 * calendar fields. The most recent combination, as determined by the
 * most recently set single field, will be used.
 *
 * <p><a name="date_resolution">For the date fields</a>:
 * <blockquote>
 * <pre>
 * YEAR + MONTH + DAY_OF_MONTH
 * YEAR + MONTH + WEEK_OF_MONTH + DAY_OF_WEEK
 * YEAR + MONTH + DAY_OF_WEEK_IN_MONTH + DAY_OF_WEEK
 * YEAR + DAY_OF_YEAR
 * YEAR + DAY_OF_WEEK + WEEK_OF_YEAR
 * </pre></blockquote>
 *
 * <a name="time_resolution">For the time of day fields</a>:
 * <blockquote>
 * <pre>
 * HOUR_OF_DAY
 * AM_PM + HOUR
 * </pre></blockquote>
 *
 * <p>If there are any calendar fields whose values haven't been set in the selected
 * field combination, <code>Calendar</code> uses their default values. The default
 * value of each field may vary by concrete calendar systems. For example, in
 * <code>GregorianCalendar</code>, the default of a field is the same as that
 * of the start of the Epoch: i.e., <code>YEAR = 1970</code>, <code>MONTH =
 * JANUARY</code>, <code>DAY_OF_MONTH = 1</code>, etc.
 *
 * <p>
 * <strong>Note:</strong> There are certain possible ambiguities in
 * interpretation of certain singular times, which are resolved in the
 * following ways:
 * <ol>
 *     <li> 23:59 is the last minute of the day and 00:00 is the first
 *          minute of the next day. Thus, 23:59 on Dec 31, 1999 &lt; 00:00 on
 *          Jan 1, 2000 &lt; 00:01 on Jan 1, 2000.
 *
 *     <li> Although historically not precise, midnight also belongs to "am",
 *          and noon belongs to "pm", so on the same day,
 *          12:00 am (midnight) &lt; 12:01 am, and 12:00 pm (noon) &lt; 12:01 pm
 * </ol>
 *
 * <p>
 * The date or time format strings are not part of the definition of a
 * calendar, as those must be modifiable or overridable by the user at
 * runtime. Use {@link DateFormat}
 * to format dates.
 *
 * <h4>Field Manipulation</h4>
 *
 * The calendar fields can be changed using three methods:
 * <code>set()</code>, <code>add()</code>, and <code>roll()</code>.
 *
 * <p><strong><code>set(f, value)</code></strong> changes calendar field
 * <code>f</code> to <code>value</code>.  In addition, it sets an
 * internal member variable to indicate that calendar field <code>f</code> has
 * been changed. Although calendar field <code>f</code> is changed immediately,
 * the calendar's time value in milliseconds is not recomputed until the next call to
 * <code>get()</code>, <code>getTime()</code>, <code>getTimeInMillis()</code>,
 * <code>add()</code>, or <code>roll()</code> is made. Thus, multiple calls to
 * <code>set()</code> do not trigger multiple, unnecessary
 * computations. As a result of changing a calendar field using
 * <code>set()</code>, other calendar fields may also change, depending on the
 * calendar field, the calendar field value, and the calendar system. In addition,
 * <code>get(f)</code> will not necessarily return <code>value</code> set by
 * the call to the <code>set</code> method
 * after the calendar fields have been recomputed. The specifics are determined by
 * the concrete calendar class.</p>
 *
 * <p><em>Example</em>: Consider a <code>GregorianCalendar</code>
 * originally set to August 31, 1999. Calling <code>set(Calendar.MONTH,
 * Calendar.SEPTEMBER)</code> sets the date to September 31,
 * 1999. This is a temporary internal representation that resolves to
 * October 1, 1999 if <code>getTime()</code>is then called. However, a
 * call to <code>set(Calendar.DAY_OF_MONTH, 30)</code> before the call to
 * <code>getTime()</code> sets the date to September 30, 1999, since
 * no recomputation occurs after <code>set()</code> itself.</p>
 *
 * <p><strong><code>add(f, delta)</code></strong> adds <code>delta</code>
 * to field <code>f</code>.  This is equivalent to calling <code>set(f,
 * get(f) + delta)</code> with two adjustments:</p>
 *
 * <blockquote>
 *   <p><strong>Add rule 1</strong>. The value of field <code>f</code>
 *   after the call minus the value of field <code>f</code> before the
 *   call is <code>delta</code>, modulo any overflow that has occurred in
 *   field <code>f</code>. Overflow occurs when a field value exceeds its
 *   range and, as a result, the next larger field is incremented or
 *   decremented and the field value is adjusted back into its range.</p>
 *
 *   <p><strong>Add rule 2</strong>. If a smaller field is expected to be
 *   invariant, but it is impossible for it to be equal to its
 *   prior value because of changes in its minimum or maximum after field
 *   <code>f</code> is changed or other constraints, such as time zone
 *   offset changes, then its value is adjusted to be as close
 *   as possible to its expected value. A smaller field represents a
 *   smaller unit of time. <code>HOUR</code> is a smaller field than
 *   <code>DAY_OF_MONTH</code>. No adjustment is made to smaller fields
 *   that are not expected to be invariant. The calendar system
 *   determines what fields are expected to be invariant.</p>
 * </blockquote>
 *
 * <p>In addition, unlike <code>set()</code>, <code>add()</code> forces
 * an immediate recomputation of the calendar's milliseconds and all
 * fields.</p>
 *
 * <p><em>Example</em>: Consider a <code>GregorianCalendar</code>
 * originally set to August 31, 1999. Calling <code>add(Calendar.MONTH,
 * 13)</code> sets the calendar to September 30, 2000. <strong>Add rule
 * 1</strong> sets the <code>MONTH</code> field to September, since
 * adding 13 months to August gives September of the next year. Since
 * <code>DAY_OF_MONTH</code> cannot be 31 in September in a
 * <code>GregorianCalendar</code>, <strong>add rule 2</strong> sets the
 * <code>DAY_OF_MONTH</code> to 30, the closest possible value. Although
 * it is a smaller field, <code>DAY_OF_WEEK</code> is not adjusted by
 * rule 2, since it is expected to change when the month changes in a
 * <code>GregorianCalendar</code>.</p>
 *
 * <p><strong><code>roll(f, delta)</code></strong> adds
 * <code>delta</code> to field <code>f</code> without changing larger
 * fields. This is equivalent to calling <code>add(f, delta)</code> with
 * the following adjustment:</p>
 *
 * <blockquote>
 *   <p><strong>Roll rule</strong>. Larger fields are unchanged after the
 *   call. A larger field represents a larger unit of
 *   time. <code>DAY_OF_MONTH</code> is a larger field than
 *   <code>HOUR</code>.</p>
 * </blockquote>
 *
 * <p><em>Example</em>: See {@link java.util.GregorianCalendar#roll(int, int)}.
 *
 * <p><strong>Usage model</strong>. To motivate the behavior of
 * <code>add()</code> and <code>roll()</code>, consider a user interface
 * component with increment and decrement buttons for the month, day, and
 * year, and an underlying <code>GregorianCalendar</code>. If the
 * interface reads January 31, 1999 and the user presses the month
 * increment button, what should it read? If the underlying
 * implementation uses <code>set()</code>, it might read March 3, 1999. A
 * better result would be February 28, 1999. Furthermore, if the user
 * presses the month increment button again, it should read March 31,
 * 1999, not March 28, 1999. By saving the original date and using either
 * <code>add()</code> or <code>roll()</code>, depending on whether larger
 * fields should be affected, the user interface can behave as most users
 * will intuitively expect.</p>
 *
 * <p>
 *  <code> Calendar </code>类是一个抽象类,它提供了在特定时刻和一组{@link #fields日历字段}之间进行转换的方法,例如<code> YEAR </code> > MONT
 * H </code>,<code> DAY_OF_MONTH </code>,<code> HOUR </code>等,以及操作日历字段,例如获取下周的日期。
 * 时间瞬间可以用毫秒值表示,该值是与<a name="Epoch"> <em>时代</em> </a>的1970年1月1日00：00：00.000 GMT(格雷戈里亚)。
 * 
 *  <p>该类还提供了用于在包外部实现具体日历系统的附加字段和方法。这些字段和方法定义为<code> protected </code>。
 * 
 * <p>
 * 像其他区域敏感类一样,<code> Calendar </code>提供了一个类方法<code> getInstance </code>,用于获取这种类型的一个有用的对象。
 *  <code>日历</code>的<code> getInstance </code>方法返回日历字段已使用当前日期和时间初始化的<code>日历</code>对象：。
 * <blockquote>
 * <pre>
 *  calendar rightNow = Calendar.getInstance();
 * </pre>
 * </blockquote>
 * 
 *  <p> <code>日历</code>对象可以生成为特定语言和日历样式(例如,日语 - 公历,日语 - 传统)实现日期时间格式化所需的所有日历字段值。
 *  <code> Calendar </code>定义某些日历字段返回的值的范围及其含义。例如,日历系统的第一个月对所有日历都具有值<code> MONTH == JANUARY </code>。
 * 其他值由具体子类定义,例如<code> ERA </code>。有关详细信息,请参阅单个字段文档和子类文档。
 * 
 *  <h3>获取和设置日历字段值</h3>
 * 
 *  <p>日历栏位值可以透过呼叫<code> set </code>方法设定。
 * 在<code> Calendar </code>中设置的任何字段值都不会被解释,直到需要计算其时间值(从历元开始的毫秒)或日历字段的值。
 * 调用<code> get </code>,<code> getTimeInMillis </code>,<code> getTime </code>,<code> add </code>和<code> 
 * roll </code>。
 * 在<code> Calendar </code>中设置的任何字段值都不会被解释,直到需要计算其时间值(从历元开始的毫秒)或日历字段的值。
 * 
 *  <h4>宽限期</h4>
 * 
 * <p> <code>日历</code>有两种模式,用于解释日历字段,<em>宽恕</em>和<em>非宽恕</em>。
 * 当<code> Calendar </code>处于宽松模式时,它接受的日历字段值范围比它生成的范围更宽。
 * 当<code> Calendar </code>重新计算由<code> get()</code>返回的日历字段值时,所有日历字段均被标准化。
 * 例如,宽松的<code> GregorianCalendar </code>将解释为<code> MONTH == JANUARY </code>,<code> DAY_OF_MONTH == 32 </code>
 * 为2月1日。
 * 当<code> Calendar </code>重新计算由<code> get()</code>返回的日历字段值时,所有日历字段均被标准化。
 * 
 *  <p>当<code> Calendar </code>处于非宽松模式时,如果日历字段中存在任何不一致,则会抛出异常。
 * 例如,<code> GregorianCalendar </code>始终产生<code> DAY_OF_MONTH </code>值,介于1和月份之间。
 * 如果已设置任何超出范围字段值,则非宽松的<code> GregorianCalendar </code>在计算其时间或日历字段值时抛出异常。
 * 
 *  <h4> <a name="first_week">第一周</a> </h4>
 * 
 *  <code>日历</code>使用两个参数定义了特定于区域设置的七天周：一周的第一天和第一周的最小天数(从1到7)。
 * 这些数字取自构建<code> Calendar </code>时的区域设置资源数据。它们也可以通过用于设置它们的值的方法明确地指定。
 * 
 * <p>设置或获取<code> WEEK_OF_MONTH </code>或<code> WEEK_OF_YEAR </code>字段时,<code>日历</code>必须将月份或年份的第一周作为参考点。
 * 一个月或一年的第一周被定义为从<code> getFirstDayOfWeek()</code>开始并且包含该月或年的至少<code> getMinimalDaysInFirstWeek()</code>
 * 天的最早七天时段。
 * 周编号为...,-1,0在第一周之前;周编号2,3,...跟着它。注意,由<code> get()</code>返回的规范化编号可能不同。
 * 例如,特定的<code> Calendar </code>子类可以将一年中第1周之前的周指定为上一年的<code> <i> </i> </code>。
 * 
 *  <h4>日历字段分辨率</h4>
 * 
 *  当从日历字段计算日期和时间时,可能没有足够的用于计算的信息(例如只有没有月日的年份和月份),或者可能存在不一致的信息(例如1996年7月15日星期二(Gregorian ) -  1996年7月15日
 * 实际上是星期一)。
 *  <code>日历</code>将解析日历字段值,以通过以下方式确定日期和时间。
 * 
 * <p> <a name="resolution">如果日历字段值存在任何冲突,则<code>日历</code>会为最近设置的日历字段设置优先级。</a>以下是默认值日历字段的组合。
 * 将使用由最近设置的单个字段确定的最近组合。
 * 
 *  <p> <a name="date_resolution">日期栏位</a>：
 * <blockquote>
 * <pre>
 *  YEAR + MONTH + DAY_OF_MONTH YEAR + MONTH + WEEK_OF_MONTH + DAY_OF_WEEK YEAR + MONTH + DAY_OF_WEEK_IN
 * _MONTH + DAY_OF_WEEK YEAR + DAY_OF_YEAR YEAR + DAY_OF_WEEK + WEEK_OF_YEAR </pre> </blockquote>。
 * 
 *  <a name="time_resolution">时间字段</a>：
 * <blockquote>
 * <pre>
 *  HOUR_OF_DAY AM_PM + HOUR </pre> </blockquote>
 * 
 *  <p>如果有任何日历字段的值尚未在所选字段组合中设置,<code> Calendar </code>会使用其默认值。每个字段的默认值可能因具体的日历系统而异。
 * 例如,在<code> GregorianCalendar </code>中,字段的默认值与Epoch的开头相同：例如,<code> YEAR = 1970 </code>,<code> MONTH = 
 * JANUARY < / code>,<code> DAY_OF_MONTH = 1 </code>等。
 *  <p>如果有任何日历字段的值尚未在所选字段组合中设置,<code> Calendar </code>会使用其默认值。每个字段的默认值可能因具体的日历系统而异。
 * 
 * <p>
 *  <strong>注意</strong>：对某些奇异时间的解释有一定可能的歧义,解决方法如下：
 * <ol>
 *  <li> 23:59是一天的最后一分钟,00:00是第二天的第一分钟。因此,1999年12月31日23:59&lt; 00:00,2000年1月1日&lt; 00:01 2000年1月1日。
 * 
 * <li>虽然历史上不精确,但午夜也属于"am",中午属于"pm",所以在同一天,凌晨12:00(午夜) 12:01 am,和12:00 pm(中午) 12:01 pm
 * </ol>
 * 
 * <p>
 *  日期或时间格式字符串不是日历定义的一部分,因为这些字符串必须在运行时由用户修改或覆盖。使用{@link DateFormat}格式化日期。
 * 
 *  <h4>字段操作</h4>
 * 
 *  可以使用三种方法更改日历字段：<code> set()</code>,<code> add()</code>和<code> roll()</code>。
 * 
 * <p> <strong> <code> set(f,value)</code> </strong>将日历字段<code> f </code>更改为<code> value </code>。
 * 此外,它设置一个内部成员变量以指示日历字段<code> f </code>已更改。
 * 虽然日历字段<code> f </code>立即更改,但直到下一次调用<code> get()</code>,<code> getTime()</code>才会重新计算日历的时间值,<code> get
 * TimeInMillis()</code>,<code> add()</code>或<code> roll()</code>因此,对<code> set()</code>的多个调用不会触发多个不必要的计
 * 算。
 * 此外,它设置一个内部成员变量以指示日历字段<code> f </code>已更改。
 * 作为使用<code> set()</code>更改日历字段的结果,其他日历字段也可能更改,具体取决于日历字段,日历字段值和日历系统。
 * 此外,在重新计算日历字段之后,<code> get(f)</code>不一定返回通过调用<code> set </code>方法设置的<code> value </code>。
 * 具体细节由具体的日历类确定。</p>。
 * 
 * <p> <em>示例</em>：考虑最初设置为1999年8月31日的<code> GregorianCalendar </code>。
 * 调用<code> set(Calendar.MONTH,Calendar.SEPTEMBER)</code>日期到1999年9月31日。
 * 这是临时内部表示,解析为1999年10月1日,如果<code> getTime()</code>然后被调用。
 * 然而,在调用<code> getTime()</code>之前对<code> set(Calendar.DAY_OF_MONTH,30)</code>的调用将日期设置为1999年9月30日,因为<code>
 *  > set()</code>本身。
 * 这是临时内部表示,解析为1999年10月1日,如果<code> getTime()</code>然后被调用。</p>。
 * 
 *  <p> <strong> <code> add(f,delta)</code> </strong>向字段<code> f </code>添加了<code> delta </code>。
 * 这相当于调用<code> set(f,get(f)+ delta)</code>,并进行两个调整：</p>。
 * 
 * <blockquote>
 *  <p> <strong>添加规则1 </strong>。
 * 调用之前的字段<code> f </code>减去调用前的<code> f </code>字段的值<code> delta </code>,对在字段<代码> f </code>。
 * 当字段值超出其范围,并且结果是下一个较大的字段增加或减少并且字段值被调整回到其范围时,溢出发生。</p>。
 * 
 * <p> <strong>添加规则2 </strong>。
 * 如果较小的字段预期是不变的,但是由于在字段<code> f </code>改变之后其最小值或最大值的变化或其他约束,例如时间,其不可能等于其先前值区域偏移变化,则其值被调整为尽可能接近其期望值。
 * 较小的字段表示较小的时间单位。 <code> HOUR </code>是一个比<code> DAY_OF_MONTH </code>更小的字段。不对不期望是不变的较小字段进行调整。
 * 日历系统决定了哪些字段应该是不变的。</p>。
 * </blockquote>
 * 
 *  <p>此外,与<code> set()</code>不同,<code> add()</code>强制立即重新计算日历的毫秒数和所有字段。</p>
 * 
 *  <p> <em>示例</em>：考虑原来设置为1999年8月31日的<code> GregorianCalendar </code>。
 * 调用<code> add(Calendar.MONTH,13)</code> 2000年9月30日。
 * <strong>添加规则1 </strong>将<code> MONTH </code>字段设置为9月,因为在8月份增加了13个月,即为下一年9月。
 * 由于<code> DAY_OF_MONTH </code>在9月份不能在<code> GregorianCalendar </code>中31,<strong>添加规则2 </strong>会将<code>
 *  DAY_OF_MONTH </code>设置为30,可能的值。
 * <strong>添加规则1 </strong>将<code> MONTH </code>字段设置为9月,因为在8月份增加了13个月,即为下一年9月。
 * 虽然它是一个较小的字段,但是<code> DAY_OF_WEEK </code>不会被规则2调整,因为当<code> GregorianCalendar </code>中的月份更改时,它会改变。
 * 
 * <p> <strong> <code> roll(f,delta)</code> </strong>在不更改大字段的情况下将<code> delta </code>添加到字段<code> f </code>
 * 。
 * 这相当于调用<code> add(f,delta)</code>,并进行以下调整：</p>。
 * 
 * <blockquote>
 *  <p> <strong>订单规则</strong>。呼叫后较大的字段不变。较大的字段表示较大的时间单位。
 *  <code> DAY_OF_MONTH </code>是比<code> HOUR </code>更大的字段。</p>。
 * </blockquote>
 * 
 *  <p> <em>示例</em>：请参阅{@link java.util.GregorianCalendar#roll(int,int)}。
 * 
 *  <p> <strong>使用模式</strong>。
 * 为了激发<code> add()</code>和<code> roll()</code>的行为,考虑一个用户界面组件,包括月,日和年的递增和递减按钮,代码> GregorianCalendar </code>
 * 。
 *  <p> <strong>使用模式</strong>。
 * 如果界面读取1999年1月31日,并且用户按下月增量按钮,应该读什么?如果底层实现使用<code> set()</code>,它可能会读取1999年3月3日。更好的结果是1999年2月28日。
 * 此外,如果用户再次按下月增量按钮, 31,1999,而不是1999年3月28日。
 * 通过保存原始日期并使用<code> add()</code>或<code> roll()</code>,根据是否应该影响更大的字段,用户界面可以像大多数用户一样直观地期待。</p>。
 * 
 * 
 * @see          java.lang.System#currentTimeMillis()
 * @see          Date
 * @see          GregorianCalendar
 * @see          TimeZone
 * @see          java.text.DateFormat
 * @author Mark Davis, David Goldsmith, Chen-Lieh Huang, Alan Liu
 * @since JDK1.1
 */
public abstract class Calendar implements Serializable, Cloneable, Comparable<Calendar> {

    // Data flow in Calendar
    // ---------------------

    // The current time is represented in two ways by Calendar: as UTC
    // milliseconds from the epoch (1 January 1970 0:00 UTC), and as local
    // fields such as MONTH, HOUR, AM_PM, etc.  It is possible to compute the
    // millis from the fields, and vice versa.  The data needed to do this
    // conversion is encapsulated by a TimeZone object owned by the Calendar.
    // The data provided by the TimeZone object may also be overridden if the
    // user sets the ZONE_OFFSET and/or DST_OFFSET fields directly. The class
    // keeps track of what information was most recently set by the caller, and
    // uses that to compute any other information as needed.

    // If the user sets the fields using set(), the data flow is as follows.
    // This is implemented by the Calendar subclass's computeTime() method.
    // During this process, certain fields may be ignored.  The disambiguation
    // algorithm for resolving which fields to pay attention to is described
    // in the class documentation.

    //   local fields (YEAR, MONTH, DATE, HOUR, MINUTE, etc.)
    //           |
    //           | Using Calendar-specific algorithm
    //           V
    //   local standard millis
    //           |
    //           | Using TimeZone or user-set ZONE_OFFSET / DST_OFFSET
    //           V
    //   UTC millis (in time data member)

    // If the user sets the UTC millis using setTime() or setTimeInMillis(),
    // the data flow is as follows.  This is implemented by the Calendar
    // subclass's computeFields() method.

    //   UTC millis (in time data member)
    //           |
    //           | Using TimeZone getOffset()
    //           V
    //   local standard millis
    //           |
    //           | Using Calendar-specific algorithm
    //           V
    //   local fields (YEAR, MONTH, DATE, HOUR, MINUTE, etc.)

    // In general, a round trip from fields, through local and UTC millis, and
    // back out to fields is made when necessary.  This is implemented by the
    // complete() method.  Resolving a partial set of fields into a UTC millis
    // value allows all remaining fields to be generated from that value.  If
    // the Calendar is lenient, the fields are also renormalized to standard
    // ranges when they are regenerated.

    /**
     * Field number for <code>get</code> and <code>set</code> indicating the
     * era, e.g., AD or BC in the Julian calendar. This is a calendar-specific
     * value; see subclass documentation.
     *
     * <p>
     * <code> get </code>的字段编号和表示时代的<code> set </code>,例如儒略历中的AD或BC。这是特定于日历的值;请参阅子类文档。
     * 
     * 
     * @see GregorianCalendar#AD
     * @see GregorianCalendar#BC
     */
    public final static int ERA = 0;

    /**
     * Field number for <code>get</code> and <code>set</code> indicating the
     * year. This is a calendar-specific value; see subclass documentation.
     * <p>
     *  <code> get </code>的字段编号和指示年份的<code> set </code>。这是特定于日历的值;请参阅子类文档。
     * 
     */
    public final static int YEAR = 1;

    /**
     * Field number for <code>get</code> and <code>set</code> indicating the
     * month. This is a calendar-specific value. The first month of
     * the year in the Gregorian and Julian calendars is
     * <code>JANUARY</code> which is 0; the last depends on the number
     * of months in a year.
     *
     * <p>
     *  <code> get </code>的字段编号和指示月份的<code> set </code>。这是特定于日历的值。
     * 一年中的第一个月在格雷戈里亚和朱利安日历是<code> JANUARY </code>这是0;最后一个取决于一年中的月数。
     * 
     * 
     * @see #JANUARY
     * @see #FEBRUARY
     * @see #MARCH
     * @see #APRIL
     * @see #MAY
     * @see #JUNE
     * @see #JULY
     * @see #AUGUST
     * @see #SEPTEMBER
     * @see #OCTOBER
     * @see #NOVEMBER
     * @see #DECEMBER
     * @see #UNDECIMBER
     */
    public final static int MONTH = 2;

    /**
     * Field number for <code>get</code> and <code>set</code> indicating the
     * week number within the current year.  The first week of the year, as
     * defined by <code>getFirstDayOfWeek()</code> and
     * <code>getMinimalDaysInFirstWeek()</code>, has value 1.  Subclasses define
     * the value of <code>WEEK_OF_YEAR</code> for days before the first week of
     * the year.
     *
     * <p>
     *  <code> get </code>的字段编号和<code>设置</code>表示当前年份内的周数。
     * 由<code> getFirstDayOfWeek()</code>和<code> getMinimalDaysInFirstWeek()</code>定义的一年中的第一周具有值1.子类定义<code>
     *  WEEK_OF_YEAR </code>的值日前一周的第一周。
     *  <code> get </code>的字段编号和<code>设置</code>表示当前年份内的周数。
     * 
     * 
     * @see #getFirstDayOfWeek
     * @see #getMinimalDaysInFirstWeek
     */
    public final static int WEEK_OF_YEAR = 3;

    /**
     * Field number for <code>get</code> and <code>set</code> indicating the
     * week number within the current month.  The first week of the month, as
     * defined by <code>getFirstDayOfWeek()</code> and
     * <code>getMinimalDaysInFirstWeek()</code>, has value 1.  Subclasses define
     * the value of <code>WEEK_OF_MONTH</code> for days before the first week of
     * the month.
     *
     * <p>
     *  <code> get </code>的字段编号和<code>设置</code>表示当前月份的周数。
     * 由<code> getFirstDayOfWeek()</code>和<code> getMinimalDaysInFirstWeek()</code>定义的月份的第一周具有值1.子类定义<code> 
     * WEEK_OF_MONTH </code>的值在该月的第一周之前的天。
     *  <code> get </code>的字段编号和<code>设置</code>表示当前月份的周数。
     * 
     * 
     * @see #getFirstDayOfWeek
     * @see #getMinimalDaysInFirstWeek
     */
    public final static int WEEK_OF_MONTH = 4;

    /**
     * Field number for <code>get</code> and <code>set</code> indicating the
     * day of the month. This is a synonym for <code>DAY_OF_MONTH</code>.
     * The first day of the month has value 1.
     *
     * <p>
     *  <code> get </code>的字段编号和<code>设置</code>表示一个月的日期。这是<code> DAY_OF_MONTH </code>的同义词。每月的第一天的值为1。
     * 
     * 
     * @see #DAY_OF_MONTH
     */
    public final static int DATE = 5;

    /**
     * Field number for <code>get</code> and <code>set</code> indicating the
     * day of the month. This is a synonym for <code>DATE</code>.
     * The first day of the month has value 1.
     *
     * <p>
     * <code> get </code>的字段编号和<code>设置</code>表示一个月的日期。这是<code> DATE </code>的同义词。每月的第一天的值为1。
     * 
     * 
     * @see #DATE
     */
    public final static int DAY_OF_MONTH = 5;

    /**
     * Field number for <code>get</code> and <code>set</code> indicating the day
     * number within the current year.  The first day of the year has value 1.
     * <p>
     *  <code> get </code>的字段编号和指示当前年份中的天数的<code> set </code>。一年的第一天有价值1。
     * 
     */
    public final static int DAY_OF_YEAR = 6;

    /**
     * Field number for <code>get</code> and <code>set</code> indicating the day
     * of the week.  This field takes values <code>SUNDAY</code>,
     * <code>MONDAY</code>, <code>TUESDAY</code>, <code>WEDNESDAY</code>,
     * <code>THURSDAY</code>, <code>FRIDAY</code>, and <code>SATURDAY</code>.
     *
     * <p>
     *  <code> get </code>的字段编号和<code>设置</code>表示星期几。
     * 此字段取值<code> SUNDAY </code>,<code> MONDAY </code>,<code> TUESDAY </code>,<code> WEDNESDAY </code>,<code>
     *  THURSDAY </code>代码> FRIDAY </code>和<code> SATURDAY </code>。
     *  <code> get </code>的字段编号和<code>设置</code>表示星期几。
     * 
     * 
     * @see #SUNDAY
     * @see #MONDAY
     * @see #TUESDAY
     * @see #WEDNESDAY
     * @see #THURSDAY
     * @see #FRIDAY
     * @see #SATURDAY
     */
    public final static int DAY_OF_WEEK = 7;

    /**
     * Field number for <code>get</code> and <code>set</code> indicating the
     * ordinal number of the day of the week within the current month. Together
     * with the <code>DAY_OF_WEEK</code> field, this uniquely specifies a day
     * within a month.  Unlike <code>WEEK_OF_MONTH</code> and
     * <code>WEEK_OF_YEAR</code>, this field's value does <em>not</em> depend on
     * <code>getFirstDayOfWeek()</code> or
     * <code>getMinimalDaysInFirstWeek()</code>.  <code>DAY_OF_MONTH 1</code>
     * through <code>7</code> always correspond to <code>DAY_OF_WEEK_IN_MONTH
     * 1</code>; <code>8</code> through <code>14</code> correspond to
     * <code>DAY_OF_WEEK_IN_MONTH 2</code>, and so on.
     * <code>DAY_OF_WEEK_IN_MONTH 0</code> indicates the week before
     * <code>DAY_OF_WEEK_IN_MONTH 1</code>.  Negative values count back from the
     * end of the month, so the last Sunday of a month is specified as
     * <code>DAY_OF_WEEK = SUNDAY, DAY_OF_WEEK_IN_MONTH = -1</code>.  Because
     * negative values count backward they will usually be aligned differently
     * within the month than positive values.  For example, if a month has 31
     * days, <code>DAY_OF_WEEK_IN_MONTH -1</code> will overlap
     * <code>DAY_OF_WEEK_IN_MONTH 5</code> and the end of <code>4</code>.
     *
     * <p>
     * <code> get </code>的字段编号和<code> set </code>表示当前月份中星期几的序号。
     * 与<code> DAY_OF_WEEK </code>字段一起,它唯一地指定一个月内的某一天。
     * 与<code> WEEK_OF_MONTH </code>和<code> WEEK_OF_YEAR </code>不同,此字段的值不会</em>取决于<code> getFirstDayOfWeek()
     * </code>或<code> getMinimalDaysInFirstWeek </code>。
     * 与<code> DAY_OF_WEEK </code>字段一起,它唯一地指定一个月内的某一天。
     *  <code> DAY_OF_MONTH 1 </code>到<code> 7 </code>总是对应于<code> DAY_OF_WEEK_IN_MONTH 1 </code>; <code> 8 </code>
     * 到<code> 14 </code>对应于<code> DAY_OF_WEEK_IN_MONTH 2 </code>,等等。
     * 与<code> DAY_OF_WEEK </code>字段一起,它唯一地指定一个月内的某一天。
     *  <code> DAY_OF_WEEK_IN_MONTH 0 </code>表示<code> DAY_OF_WEEK_IN_MONTH 1 </code>前一周。
     * 负值会从月底开始计算,因此一个月的最后一个星期日被指定为<code> DAY_OF_WEEK = SUNDAY,DAY_OF_WEEK_IN_MONTH = -1 </code>。
     * 因为负值向后计数,它们通常在月内与正值不同。
     * 例如,如果一个月份有31天,<code> DAY_OF_WEEK_IN_MONTH -1 </code>将与<code> DAY_OF_WEEK_IN_MONTH 5 </code>和<code> 4 
     * </code>结束重叠。
     * 因为负值向后计数,它们通常在月内与正值不同。
     * 
     * 
     * @see #DAY_OF_WEEK
     * @see #WEEK_OF_MONTH
     */
    public final static int DAY_OF_WEEK_IN_MONTH = 8;

    /**
     * Field number for <code>get</code> and <code>set</code> indicating
     * whether the <code>HOUR</code> is before or after noon.
     * E.g., at 10:04:15.250 PM the <code>AM_PM</code> is <code>PM</code>.
     *
     * <p>
     *  <code> get </code>的字段编号和<code> set </code>表示<code> HOUR </code>是否在中午之前或之后。
     * 例如,在10：04：15.250 PM,<code> AM_PM </code>是<code> PM </code>。
     * 
     * 
     * @see #AM
     * @see #PM
     * @see #HOUR
     */
    public final static int AM_PM = 9;

    /**
     * Field number for <code>get</code> and <code>set</code> indicating the
     * hour of the morning or afternoon. <code>HOUR</code> is used for the
     * 12-hour clock (0 - 11). Noon and midnight are represented by 0, not by 12.
     * E.g., at 10:04:15.250 PM the <code>HOUR</code> is 10.
     *
     * <p>
     * <code> get </code>的字段编号和指示上午或下午的小时的<code>设置</code>。 <code> HOUR </code>用于12小时制时钟(0  -  11)。
     * 中午和午夜用0而不是12表示。例如,在10：04：15.250 PM,<code> HOUR </code>为10。
     * 
     * 
     * @see #AM_PM
     * @see #HOUR_OF_DAY
     */
    public final static int HOUR = 10;

    /**
     * Field number for <code>get</code> and <code>set</code> indicating the
     * hour of the day. <code>HOUR_OF_DAY</code> is used for the 24-hour clock.
     * E.g., at 10:04:15.250 PM the <code>HOUR_OF_DAY</code> is 22.
     *
     * <p>
     *  <code> get </code>的字段编号和指示一天中的小时的<code> set </code>。 <code> HOUR_OF_DAY </code>用于24小时制。
     * 例如,在10：04：15.250 PM,<code> HOUR_OF_DAY </code>为22。
     * 
     * 
     * @see #HOUR
     */
    public final static int HOUR_OF_DAY = 11;

    /**
     * Field number for <code>get</code> and <code>set</code> indicating the
     * minute within the hour.
     * E.g., at 10:04:15.250 PM the <code>MINUTE</code> is 4.
     * <p>
     *  <code> get </code>的字段编号和<code>设置</code>表示小时内的分钟。例如,在10：04：15.250 PM,<code> MINUTE </code>为4。
     * 
     */
    public final static int MINUTE = 12;

    /**
     * Field number for <code>get</code> and <code>set</code> indicating the
     * second within the minute.
     * E.g., at 10:04:15.250 PM the <code>SECOND</code> is 15.
     * <p>
     *  <code> get </code>的字段编号和<code>设置</code>表示分钟内的秒。例如,在10：04：15.250 PM,<code> SECOND </code>为15。
     * 
     */
    public final static int SECOND = 13;

    /**
     * Field number for <code>get</code> and <code>set</code> indicating the
     * millisecond within the second.
     * E.g., at 10:04:15.250 PM the <code>MILLISECOND</code> is 250.
     * <p>
     *  <code> get </code>的字段编号和<code> set </code>表示秒内的毫秒。
     * 例如,在10：04：15.250 PM,<code> MILLISECOND </code>为250。
     * 
     */
    public final static int MILLISECOND = 14;

    /**
     * Field number for <code>get</code> and <code>set</code>
     * indicating the raw offset from GMT in milliseconds.
     * <p>
     * This field reflects the correct GMT offset value of the time
     * zone of this <code>Calendar</code> if the
     * <code>TimeZone</code> implementation subclass supports
     * historical GMT offset changes.
     * <p>
     *  <code> get </code>的字段编号和<code> set </code>表示与GMT的原始偏移量(以毫秒为单位)。
     * <p>
     *  如果<code> TimeZone </code>实现子类支持历史GMT偏移量更改,则此字段反映此<code> Calendar </code>的时区的正确GMT偏移值。
     * 
     */
    public final static int ZONE_OFFSET = 15;

    /**
     * Field number for <code>get</code> and <code>set</code> indicating the
     * daylight saving offset in milliseconds.
     * <p>
     * This field reflects the correct daylight saving offset value of
     * the time zone of this <code>Calendar</code> if the
     * <code>TimeZone</code> implementation subclass supports
     * historical Daylight Saving Time schedule changes.
     * <p>
     *  <code> get </code>的字段编号和<code>设置</code>表示以毫秒为单位的夏令时偏移量。
     * <p>
     * 如果<code> TimeZone </code>实现子类支持历史夏令时计划更改,此字段反映此<code> Calendar </code>的时区的正确夏令时偏移值。
     * 
     */
    public final static int DST_OFFSET = 16;

    /**
     * The number of distinct fields recognized by <code>get</code> and <code>set</code>.
     * Field numbers range from <code>0..FIELD_COUNT-1</code>.
     * <p>
     *  由<code> get </code>和<code>设置</code>识别的不同字段的数量。字段编号范围从<code> 0..FIELD_COUNT-1 </code>。
     * 
     */
    public final static int FIELD_COUNT = 17;

    /**
     * Value of the {@link #DAY_OF_WEEK} field indicating
     * Sunday.
     * <p>
     *  {@link #DAY_OF_WEEK}字段的值,表示星期日。
     * 
     */
    public final static int SUNDAY = 1;

    /**
     * Value of the {@link #DAY_OF_WEEK} field indicating
     * Monday.
     * <p>
     *  {@link #DAY_OF_WEEK}字段的值,表示星期一。
     * 
     */
    public final static int MONDAY = 2;

    /**
     * Value of the {@link #DAY_OF_WEEK} field indicating
     * Tuesday.
     * <p>
     *  {@link #DAY_OF_WEEK}字段的值,表示星期二。
     * 
     */
    public final static int TUESDAY = 3;

    /**
     * Value of the {@link #DAY_OF_WEEK} field indicating
     * Wednesday.
     * <p>
     *  {@link #DAY_OF_WEEK}字段的值,表示星期三。
     * 
     */
    public final static int WEDNESDAY = 4;

    /**
     * Value of the {@link #DAY_OF_WEEK} field indicating
     * Thursday.
     * <p>
     *  {@link #DAY_OF_WEEK}字段的值,表示星期四。
     * 
     */
    public final static int THURSDAY = 5;

    /**
     * Value of the {@link #DAY_OF_WEEK} field indicating
     * Friday.
     * <p>
     *  {@link #DAY_OF_WEEK}字段的值,表示星期五。
     * 
     */
    public final static int FRIDAY = 6;

    /**
     * Value of the {@link #DAY_OF_WEEK} field indicating
     * Saturday.
     * <p>
     *  {@link #DAY_OF_WEEK}字段的值,表示星期六。
     * 
     */
    public final static int SATURDAY = 7;

    /**
     * Value of the {@link #MONTH} field indicating the
     * first month of the year in the Gregorian and Julian calendars.
     * <p>
     *  {@link #MONTH}字段的值,表示公历和儒略历日历中年份的第一个月。
     * 
     */
    public final static int JANUARY = 0;

    /**
     * Value of the {@link #MONTH} field indicating the
     * second month of the year in the Gregorian and Julian calendars.
     * <p>
     *  {@link #MONTH}字段的值,表示一年中第二个月的格里高利历和朱利安日历。
     * 
     */
    public final static int FEBRUARY = 1;

    /**
     * Value of the {@link #MONTH} field indicating the
     * third month of the year in the Gregorian and Julian calendars.
     * <p>
     *  {@link #MONTH}字段的值,表示公历和儒略历日历中年份的第三个月。
     * 
     */
    public final static int MARCH = 2;

    /**
     * Value of the {@link #MONTH} field indicating the
     * fourth month of the year in the Gregorian and Julian calendars.
     * <p>
     *  {@link #MONTH}字段的值,表示一年中第四个月的公历和朱利安日历。
     * 
     */
    public final static int APRIL = 3;

    /**
     * Value of the {@link #MONTH} field indicating the
     * fifth month of the year in the Gregorian and Julian calendars.
     * <p>
     *  {@link #MONTH}字段的值,表示一年中的第五个月的格里高利历和日历。
     * 
     */
    public final static int MAY = 4;

    /**
     * Value of the {@link #MONTH} field indicating the
     * sixth month of the year in the Gregorian and Julian calendars.
     * <p>
     *  {@link #MONTH}字段的值,表示一年中的第六个月在公历和朱利安日历中的值。
     * 
     */
    public final static int JUNE = 5;

    /**
     * Value of the {@link #MONTH} field indicating the
     * seventh month of the year in the Gregorian and Julian calendars.
     * <p>
     * {@link #MONTH}字段的值,表示一年中第七个月的公历和朱利安日历。
     * 
     */
    public final static int JULY = 6;

    /**
     * Value of the {@link #MONTH} field indicating the
     * eighth month of the year in the Gregorian and Julian calendars.
     * <p>
     *  {@link #MONTH}字段的值,表示公历和儒略历日历中年份的第八个月。
     * 
     */
    public final static int AUGUST = 7;

    /**
     * Value of the {@link #MONTH} field indicating the
     * ninth month of the year in the Gregorian and Julian calendars.
     * <p>
     *  {@link #MONTH}字段的值,表示Gregorian和Julian日历中年份的第九个月。
     * 
     */
    public final static int SEPTEMBER = 8;

    /**
     * Value of the {@link #MONTH} field indicating the
     * tenth month of the year in the Gregorian and Julian calendars.
     * <p>
     *  {@ link #MONTH}字段的值,表示公历和儒略历日历中年份的第十个月。
     * 
     */
    public final static int OCTOBER = 9;

    /**
     * Value of the {@link #MONTH} field indicating the
     * eleventh month of the year in the Gregorian and Julian calendars.
     * <p>
     *  {@link #MONTH}字段的值,表示一年中的第十一个月在公历和朱利安日历中的值。
     * 
     */
    public final static int NOVEMBER = 10;

    /**
     * Value of the {@link #MONTH} field indicating the
     * twelfth month of the year in the Gregorian and Julian calendars.
     * <p>
     *  {@link #MONTH}字段的值,表示一年中第十二个月的格里高利历和朱利安日历。
     * 
     */
    public final static int DECEMBER = 11;

    /**
     * Value of the {@link #MONTH} field indicating the
     * thirteenth month of the year. Although <code>GregorianCalendar</code>
     * does not use this value, lunar calendars do.
     * <p>
     *  {@link #MONTH}字段的值,表示一年中的第十三个月。虽然<code> GregorianCalendar </code>不使用这个值,但月球日历。
     * 
     */
    public final static int UNDECIMBER = 12;

    /**
     * Value of the {@link #AM_PM} field indicating the
     * period of the day from midnight to just before noon.
     * <p>
     *  {@link #AM_PM}字段的值,表示从午夜到中午之前的一天中的某个时段。
     * 
     */
    public final static int AM = 0;

    /**
     * Value of the {@link #AM_PM} field indicating the
     * period of the day from noon to just before midnight.
     * <p>
     *  {@link #AM_PM}字段的值,表示当天的中午到午夜之间的时段。
     * 
     */
    public final static int PM = 1;

    /**
     * A style specifier for {@link #getDisplayNames(int, int, Locale)
     * getDisplayNames} indicating names in all styles, such as
     * "January" and "Jan".
     *
     * <p>
     *  {@link #getDisplayNames(int,int,Locale)getDisplayNames}的样式说明符,指示所有样式中的名称,例如"January"和"Jan"。
     * 
     * 
     * @see #SHORT_FORMAT
     * @see #LONG_FORMAT
     * @see #SHORT_STANDALONE
     * @see #LONG_STANDALONE
     * @see #SHORT
     * @see #LONG
     * @since 1.6
     */
    public static final int ALL_STYLES = 0;

    static final int STANDALONE_MASK = 0x8000;

    /**
     * A style specifier for {@link #getDisplayName(int, int, Locale)
     * getDisplayName} and {@link #getDisplayNames(int, int, Locale)
     * getDisplayNames} equivalent to {@link #SHORT_FORMAT}.
     *
     * <p>
     *  等同于{@link #SHORT_FORMAT}的{@link #getDisplayName(int,int,Locale)getDisplayName}和{@link #getDisplayNames(int,int,Locale)getDisplayNames}
     * 的样式说明符。
     * 
     * 
     * @see #SHORT_STANDALONE
     * @see #LONG
     * @since 1.6
     */
    public static final int SHORT = 1;

    /**
     * A style specifier for {@link #getDisplayName(int, int, Locale)
     * getDisplayName} and {@link #getDisplayNames(int, int, Locale)
     * getDisplayNames} equivalent to {@link #LONG_FORMAT}.
     *
     * <p>
     * {@link #getDisplayName(int,int,Locale)getDisplayName}和{@link #getDisplayNames(int,int,Locale)getDisplayNames}
     * 的格式说明符,等效于{@link #LONG_FORMAT}。
     * 
     * 
     * @see #LONG_STANDALONE
     * @see #SHORT
     * @since 1.6
     */
    public static final int LONG = 2;

    /**
     * A style specifier for {@link #getDisplayName(int, int, Locale)
     * getDisplayName} and {@link #getDisplayNames(int, int, Locale)
     * getDisplayNames} indicating a narrow name used for format. Narrow names
     * are typically single character strings, such as "M" for Monday.
     *
     * <p>
     *  {@link #getDisplayName(int,int,Locale)getDisplayName}和{@link #getDisplayNames(int,int,Locale)getDisplayNames}
     * 的样式说明符,表示用于格式的窄名称。
     * 窄名称通常是单个字符串,例如星期一的"M"。
     * 
     * 
     * @see #NARROW_STANDALONE
     * @see #SHORT_FORMAT
     * @see #LONG_FORMAT
     * @since 1.8
     */
    public static final int NARROW_FORMAT = 4;

    /**
     * A style specifier for {@link #getDisplayName(int, int, Locale)
     * getDisplayName} and {@link #getDisplayNames(int, int, Locale)
     * getDisplayNames} indicating a narrow name independently. Narrow names
     * are typically single character strings, such as "M" for Monday.
     *
     * <p>
     *  {@link #getDisplayName(int,int,Locale)getDisplayName}和{@link #getDisplayNames(int,int,Locale)getDisplayNames}
     * 的样式说明符,表示单独的窄名称。
     * 窄名称通常是单个字符串,例如星期一的"M"。
     * 
     * 
     * @see #NARROW_FORMAT
     * @see #SHORT_STANDALONE
     * @see #LONG_STANDALONE
     * @since 1.8
     */
    public static final int NARROW_STANDALONE = NARROW_FORMAT | STANDALONE_MASK;

    /**
     * A style specifier for {@link #getDisplayName(int, int, Locale)
     * getDisplayName} and {@link #getDisplayNames(int, int, Locale)
     * getDisplayNames} indicating a short name used for format.
     *
     * <p>
     *  {@link #getDisplayName(int,int,Locale)getDisplayName}和{@link #getDisplayNames(int,int,Locale)getDisplayNames}
     * 的样式说明符,表示用于格式的简短名称。
     * 
     * 
     * @see #SHORT_STANDALONE
     * @see #LONG_FORMAT
     * @see #LONG_STANDALONE
     * @since 1.8
     */
    public static final int SHORT_FORMAT = 1;

    /**
     * A style specifier for {@link #getDisplayName(int, int, Locale)
     * getDisplayName} and {@link #getDisplayNames(int, int, Locale)
     * getDisplayNames} indicating a long name used for format.
     *
     * <p>
     *  {@link #getDisplayName(int,int,Locale)getDisplayName}和{@link #getDisplayNames(int,int,Locale)getDisplayNames}
     * 的样式说明符,表示用于格式的长名称。
     * 
     * 
     * @see #LONG_STANDALONE
     * @see #SHORT_FORMAT
     * @see #SHORT_STANDALONE
     * @since 1.8
     */
    public static final int LONG_FORMAT = 2;

    /**
     * A style specifier for {@link #getDisplayName(int, int, Locale)
     * getDisplayName} and {@link #getDisplayNames(int, int, Locale)
     * getDisplayNames} indicating a short name used independently,
     * such as a month abbreviation as calendar headers.
     *
     * <p>
     *  {@ link #getDisplayName(int,int,Locale)getDisplayName}和{@link #getDisplayNames(int,int,Locale)getDisplayNames}
     * 的样式说明符,表示单独使用的短名称,例如月份缩写为日历标题。
     * 
     * 
     * @see #SHORT_FORMAT
     * @see #LONG_FORMAT
     * @see #LONG_STANDALONE
     * @since 1.8
     */
    public static final int SHORT_STANDALONE = SHORT | STANDALONE_MASK;

    /**
     * A style specifier for {@link #getDisplayName(int, int, Locale)
     * getDisplayName} and {@link #getDisplayNames(int, int, Locale)
     * getDisplayNames} indicating a long name used independently,
     * such as a month name as calendar headers.
     *
     * <p>
     * {@link #getDisplayName(int,int,Locale)getDisplayName}和{@link #getDisplayNames(int,int,Locale)getDisplayNames}
     * 的样式说明符,表示单独使用的长名称,例如月份名称作为日历标题。
     * 
     * 
     * @see #LONG_FORMAT
     * @see #SHORT_FORMAT
     * @see #SHORT_STANDALONE
     * @since 1.8
     */
    public static final int LONG_STANDALONE = LONG | STANDALONE_MASK;

    // Internal notes:
    // Calendar contains two kinds of time representations: current "time" in
    // milliseconds, and a set of calendar "fields" representing the current time.
    // The two representations are usually in sync, but can get out of sync
    // as follows.
    // 1. Initially, no fields are set, and the time is invalid.
    // 2. If the time is set, all fields are computed and in sync.
    // 3. If a single field is set, the time is invalid.
    // Recomputation of the time and fields happens when the object needs
    // to return a result to the user, or use a result for a computation.

    /**
     * The calendar field values for the currently set time for this calendar.
     * This is an array of <code>FIELD_COUNT</code> integers, with index values
     * <code>ERA</code> through <code>DST_OFFSET</code>.
     * <p>
     *  此日历当前设置时间的日历字段值。这是一个<code> FIELD_COUNT </code>整数数组,索引值<code> ERA </code>到<code> DST_OFFSET </code>。
     * 
     * 
     * @serial
     */
    @SuppressWarnings("ProtectedField")
    protected int           fields[];

    /**
     * The flags which tell if a specified calendar field for the calendar is set.
     * A new object has no fields set.  After the first call to a method
     * which generates the fields, they all remain set after that.
     * This is an array of <code>FIELD_COUNT</code> booleans, with index values
     * <code>ERA</code> through <code>DST_OFFSET</code>.
     * <p>
     *  指示是否设置日历的指定日历字段的标志。新对象没有设置字段。在第一次调用生成字段的方法之后,它们都在此之后保持置位。
     * 这是一个<code> FIELD_COUNT </code>布尔值数组,索引值<code> ERA </code>到<code> DST_OFFSET </code>。
     * 
     * 
     * @serial
     */
    @SuppressWarnings("ProtectedField")
    protected boolean       isSet[];

    /**
     * Pseudo-time-stamps which specify when each field was set. There
     * are two special values, UNSET and COMPUTED. Values from
     * MINIMUM_USER_SET to Integer.MAX_VALUE are legal user set values.
     * <p>
     *  指定每个字段何时设置的伪时间戳。有两个特殊值,UNSET和COMPUTED。 MINIMUM_USER_SET到Integer.MAX_VALUE的值是合法的用户设置值。
     * 
     */
    transient private int   stamp[];

    /**
     * The currently set time for this calendar, expressed in milliseconds after
     * January 1, 1970, 0:00:00 GMT.
     * <p>
     *  此日历的当前设置时间,以1970年1月1日0:00:00 GMT后的毫秒数表示。
     * 
     * 
     * @see #isTimeSet
     * @serial
     */
    @SuppressWarnings("ProtectedField")
    protected long          time;

    /**
     * True if then the value of <code>time</code> is valid.
     * The time is made invalid by a change to an item of <code>field[]</code>.
     * <p>
     *  如果<code> time </code>的值有效,则为true。通过更改<code>字段[] </code>的项目,时间无效。
     * 
     * 
     * @see #time
     * @serial
     */
    @SuppressWarnings("ProtectedField")
    protected boolean       isTimeSet;

    /**
     * True if <code>fields[]</code> are in sync with the currently set time.
     * If false, then the next attempt to get the value of a field will
     * force a recomputation of all fields from the current value of
     * <code>time</code>.
     * <p>
     *  如果<code> fields [] </code>与当前设置的时间同步,则为true。如果为假,则下一次尝试获取字段的值将强制重新计算来自<code> time </code>的当前值的所有字段。
     * 
     * 
     * @serial
     */
    @SuppressWarnings("ProtectedField")
    protected boolean       areFieldsSet;

    /**
     * True if all fields have been set.
     * <p>
     *  如果所有字段都已设置,则为True。
     * 
     * 
     * @serial
     */
    transient boolean       areAllFieldsSet;

    /**
     * <code>True</code> if this calendar allows out-of-range field values during computation
     * of <code>time</code> from <code>fields[]</code>.
     * <p>
     * <code> True </code>如果此日历在<code>时间</code>从<code> fields [] </code>计算期间允许超出范围字段值。
     * 
     * 
     * @see #setLenient
     * @see #isLenient
     * @serial
     */
    private boolean         lenient = true;

    /**
     * The <code>TimeZone</code> used by this calendar. <code>Calendar</code>
     * uses the time zone data to translate between locale and GMT time.
     * <p>
     *  此日历使用的<code> TimeZone </code>。 <code>日历</code>使用时区数据在区域设置和GMT时间之间进行转换。
     * 
     * 
     * @serial
     */
    private TimeZone        zone;

    /**
     * <code>True</code> if zone references to a shared TimeZone object.
     * <p>
     *  <code> True </code>如果区域引用共享的TimeZone对象。
     * 
     */
    transient private boolean sharedZone = false;

    /**
     * The first day of the week, with possible values <code>SUNDAY</code>,
     * <code>MONDAY</code>, etc.  This is a locale-dependent value.
     * <p>
     *  一周的第一天,可能的值为<code> SUNDAY </code>,<code> MONDAY </code>等。这是一个区域设置相关的值。
     * 
     * 
     * @serial
     */
    private int             firstDayOfWeek;

    /**
     * The number of days required for the first week in a month or year,
     * with possible values from 1 to 7.  This is a locale-dependent value.
     * <p>
     *  一个月或一年中第一周所需的天数,可能的值为1到7.这是一个与区域设置相关的值。
     * 
     * 
     * @serial
     */
    private int             minimalDaysInFirstWeek;

    /**
     * Cache to hold the firstDayOfWeek and minimalDaysInFirstWeek
     * of a Locale.
     * <p>
     *  缓存保存区域设置的firstDayOfWeek和minimalDaysInFirstWeek。
     * 
     */
    private static final ConcurrentMap<Locale, int[]> cachedLocaleData
        = new ConcurrentHashMap<>(3);

    // Special values of stamp[]
    /**
     * The corresponding fields[] has no value.
     * <p>
     *  相应的字段[]没有值。
     * 
     */
    private static final int        UNSET = 0;

    /**
     * The value of the corresponding fields[] has been calculated internally.
     * <p>
     *  相应的字段[]的值已在内部计算。
     * 
     */
    private static final int        COMPUTED = 1;

    /**
     * The value of the corresponding fields[] has been set externally. Stamp
     * values which are greater than 1 represents the (pseudo) time when the
     * corresponding fields[] value was set.
     * <p>
     *  相应的字段[]的值已在外部设置。大于1的印记值表示设置相应字段[]值时的(伪)时间。
     * 
     */
    private static final int        MINIMUM_USER_STAMP = 2;

    /**
     * The mask value that represents all of the fields.
     * <p>
     *  表示所有字段的掩码值。
     * 
     */
    static final int ALL_FIELDS = (1 << FIELD_COUNT) - 1;

    /**
     * The next available value for <code>stamp[]</code>, an internal array.
     * This actually should not be written out to the stream, and will probably
     * be removed from the stream in the near future.  In the meantime,
     * a value of <code>MINIMUM_USER_STAMP</code> should be used.
     * <p>
     *  <code> stamp [] </code>的下一个可用值,一个内部数组。这实际上不应该写入流,并且可能会在不久的将来从流中删除。
     * 在此期间,应使用<code> MINIMUM_USER_STAMP </code>的值。
     * 
     * 
     * @serial
     */
    private int             nextStamp = MINIMUM_USER_STAMP;

    // the internal serial version which says which version was written
    // - 0 (default) for version up to JDK 1.1.5
    // - 1 for version from JDK 1.1.6, which writes a correct 'time' value
    //     as well as compatible values for other fields.  This is a
    //     transitional format.
    // - 2 (not implemented yet) a future version, in which fields[],
    //     areFieldsSet, and isTimeSet become transient, and isSet[] is
    //     removed. In JDK 1.1.6 we write a format compatible with version 2.
    static final int        currentSerialVersion = 1;

    /**
     * The version of the serialized data on the stream.  Possible values:
     * <dl>
     * <dt><b>0</b> or not present on stream</dt>
     * <dd>
     * JDK 1.1.5 or earlier.
     * </dd>
     * <dt><b>1</b></dt>
     * <dd>
     * JDK 1.1.6 or later.  Writes a correct 'time' value
     * as well as compatible values for other fields.  This is a
     * transitional format.
     * </dd>
     * </dl>
     * When streaming out this class, the most recent format
     * and the highest allowable <code>serialVersionOnStream</code>
     * is written.
     * <p>
     *  流上的序列化数据的版本。可能的值：
     * <dl>
     *  <dt> <b> 0 </b>或不在线</dt>
     * <dd>
     *  JDK 1.1.5或更早版本。
     * </dd>
     *  <dt> <b> 1 </b> </dt>
     * <dd>
     * JDK 1.1.6或更高版本。写入正确的"时间"值以及其他字段的兼容值。这是一个过渡格式。
     * </dd>
     * </dl>
     *  当流出这个类时,将写入最新的格式和最高允许的<code> serialVersionOnStream </code>。
     * 
     * 
     * @serial
     * @since JDK1.1.6
     */
    private int             serialVersionOnStream = currentSerialVersion;

    // Proclaim serialization compatibility with JDK 1.1
    static final long       serialVersionUID = -1807547505821590642L;

    // Mask values for calendar fields
    @SuppressWarnings("PointlessBitwiseExpression")
    final static int ERA_MASK           = (1 << ERA);
    final static int YEAR_MASK          = (1 << YEAR);
    final static int MONTH_MASK         = (1 << MONTH);
    final static int WEEK_OF_YEAR_MASK  = (1 << WEEK_OF_YEAR);
    final static int WEEK_OF_MONTH_MASK = (1 << WEEK_OF_MONTH);
    final static int DAY_OF_MONTH_MASK  = (1 << DAY_OF_MONTH);
    final static int DATE_MASK          = DAY_OF_MONTH_MASK;
    final static int DAY_OF_YEAR_MASK   = (1 << DAY_OF_YEAR);
    final static int DAY_OF_WEEK_MASK   = (1 << DAY_OF_WEEK);
    final static int DAY_OF_WEEK_IN_MONTH_MASK  = (1 << DAY_OF_WEEK_IN_MONTH);
    final static int AM_PM_MASK         = (1 << AM_PM);
    final static int HOUR_MASK          = (1 << HOUR);
    final static int HOUR_OF_DAY_MASK   = (1 << HOUR_OF_DAY);
    final static int MINUTE_MASK        = (1 << MINUTE);
    final static int SECOND_MASK        = (1 << SECOND);
    final static int MILLISECOND_MASK   = (1 << MILLISECOND);
    final static int ZONE_OFFSET_MASK   = (1 << ZONE_OFFSET);
    final static int DST_OFFSET_MASK    = (1 << DST_OFFSET);

    /**
     * {@code Calendar.Builder} is used for creating a {@code Calendar} from
     * various date-time parameters.
     *
     * <p>There are two ways to set a {@code Calendar} to a date-time value. One
     * is to set the instant parameter to a millisecond offset from the <a
     * href="Calendar.html#Epoch">Epoch</a>. The other is to set individual
     * field parameters, such as {@link Calendar#YEAR YEAR}, to their desired
     * values. These two ways can't be mixed. Trying to set both the instant and
     * individual fields will cause an {@link IllegalStateException} to be
     * thrown. However, it is permitted to override previous values of the
     * instant or field parameters.
     *
     * <p>If no enough field parameters are given for determining date and/or
     * time, calendar specific default values are used when building a
     * {@code Calendar}. For example, if the {@link Calendar#YEAR YEAR} value
     * isn't given for the Gregorian calendar, 1970 will be used. If there are
     * any conflicts among field parameters, the <a
     * href="Calendar.html#resolution"> resolution rules</a> are applied.
     * Therefore, the order of field setting matters.
     *
     * <p>In addition to the date-time parameters,
     * the {@linkplain #setLocale(Locale) locale},
     * {@linkplain #setTimeZone(TimeZone) time zone},
     * {@linkplain #setWeekDefinition(int, int) week definition}, and
     * {@linkplain #setLenient(boolean) leniency mode} parameters can be set.
     *
     * <p><b>Examples</b>
     * <p>The following are sample usages. Sample code assumes that the
     * {@code Calendar} constants are statically imported.
     *
     * <p>The following code produces a {@code Calendar} with date 2012-12-31
     * (Gregorian) because Monday is the first day of a week with the <a
     * href="GregorianCalendar.html#iso8601_compatible_setting"> ISO 8601
     * compatible week parameters</a>.
     * <pre>
     *   Calendar cal = new Calendar.Builder().setCalendarType("iso8601")
     *                        .setWeekDate(2013, 1, MONDAY).build();</pre>
     * <p>The following code produces a Japanese {@code Calendar} with date
     * 1989-01-08 (Gregorian), assuming that the default {@link Calendar#ERA ERA}
     * is <em>Heisei</em> that started on that day.
     * <pre>
     *   Calendar cal = new Calendar.Builder().setCalendarType("japanese")
     *                        .setFields(YEAR, 1, DAY_OF_YEAR, 1).build();</pre>
     *
     * <p>
     *  {@code Calendar.Builder}用于根据各种日期/时间参数创建{@code Calendar}。
     * 
     *  <p>有两种方法可以将{@code Calendar}设置为日期时间值。一个是将即时参数设置为与<a href="Calendar.html#Epoch">时代</a>的毫秒偏移。
     * 另一种是将各个字段参数(例如{@link Calendar#YEAR YEAR})设置为所需的值。这两种方式不能混合。
     * 尝试设置即时和单个字段将导致{@link IllegalStateException}被抛出。但是,允许覆盖即时或字段参数的先前值。
     * 
     *  <p>如果没有足够的字段参数用于确定日期和/或时间,则在构建{@code Calendar}时会使用特定于日历的默认值。
     * 例如,如果没有为公历提供{@link Calendar#YEAR YEAR}值,则将使用1970年。
     * 如果字段参数之间存在冲突,则会应用<a href="Calendar.html#resolution">解析规则</a>。因此,现场设置的顺序很重要。
     * 
     * <p>除了日期时间参数,{@linkplain #setLocale(Locale)locale},{@linkplain #setTimeZone(TimeZone)time zone},{@linkplain #setWeekDefinition(int,int)week definition}
     * 和{@linkplain #setLenient(boolean)leniency mode}参数。
     * 
     *  <p> <b>示例</b> <p>以下是示例用法。示例代码假定{@code Calendar}常量是静态导入的。
     * 
     *  <p>以下代码会生成一个日期为2012-12-31(Gregorian)的{@code Calendar},因为星期一是一周的第一天,其中包含<a href="GregorianCalendar.html#iso8601_compatible_setting">
     *  ISO 8601兼容周参数</a>。
     * <pre>
     *  Calendar cal = new Calendar.Builder()。
     * setCalendarType("iso8601").setWeekDate(2013,1,MONDAY).build(); </pre> <p>以下代码生成日期{@code Calendar} 198
     * 9-01-08(Gregorian),假设默认的{@link Calendar#ERA ERA}是从那天开始的平成</em>。
     *  Calendar cal = new Calendar.Builder()。
     * <pre>
     *  Calendar cal = new Calendar.Builder()。
     * setCalendarType("japanese").setFields(YEAR,1,DAY_OF_YEAR,1).build(); </pre>。
     * 
     * 
     * @since 1.8
     * @see Calendar#getInstance(TimeZone, Locale)
     * @see Calendar#fields
     */
    public static class Builder {
        private static final int NFIELDS = FIELD_COUNT + 1; // +1 for WEEK_YEAR
        private static final int WEEK_YEAR = FIELD_COUNT;

        private long instant;
        // Calendar.stamp[] (lower half) and Calendar.fields[] (upper half) combined
        private int[] fields;
        // Pseudo timestamp starting from MINIMUM_USER_STAMP.
        // (COMPUTED is used to indicate that the instant has been set.)
        private int nextStamp;
        // maxFieldIndex keeps the max index of fields which have been set.
        // (WEEK_YEAR is never included.)
        private int maxFieldIndex;
        private String type;
        private TimeZone zone;
        private boolean lenient = true;
        private Locale locale;
        private int firstDayOfWeek, minimalDaysInFirstWeek;

        /**
         * Constructs a {@code Calendar.Builder}.
         * <p>
         *  构造一个{@code Calendar.Builder}。
         * 
         */
        public Builder() {
        }

        /**
         * Sets the instant parameter to the given {@code instant} value that is
         * a millisecond offset from <a href="Calendar.html#Epoch">the
         * Epoch</a>.
         *
         * <p>
         *  将即时参数设置为给定的{@code instant}值,即与<a href="Calendar.html#Epoch">时代</a>的毫秒偏移量。
         * 
         * 
         * @param instant a millisecond offset from the Epoch
         * @return this {@code Calendar.Builder}
         * @throws IllegalStateException if any of the field parameters have
         *                               already been set
         * @see Calendar#setTime(Date)
         * @see Calendar#setTimeInMillis(long)
         * @see Calendar#time
         */
        public Builder setInstant(long instant) {
            if (fields != null) {
                throw new IllegalStateException();
            }
            this.instant = instant;
            nextStamp = COMPUTED;
            return this;
        }

        /**
         * Sets the instant parameter to the {@code instant} value given by a
         * {@link Date}. This method is equivalent to a call to
         * {@link #setInstant(long) setInstant(instant.getTime())}.
         *
         * <p>
         *  将即时参数设置为{@link Date}给出的{@code instant}值。
         * 此方法等效于调用{@link #setInstant(long)setInstant(instant.getTime())}。
         * 
         * 
         * @param instant a {@code Date} representing a millisecond offset from
         *                the Epoch
         * @return this {@code Calendar.Builder}
         * @throws NullPointerException  if {@code instant} is {@code null}
         * @throws IllegalStateException if any of the field parameters have
         *                               already been set
         * @see Calendar#setTime(Date)
         * @see Calendar#setTimeInMillis(long)
         * @see Calendar#time
         */
        public Builder setInstant(Date instant) {
            return setInstant(instant.getTime()); // NPE if instant == null
        }

        /**
         * Sets the {@code field} parameter to the given {@code value}.
         * {@code field} is an index to the {@link Calendar#fields}, such as
         * {@link Calendar#DAY_OF_MONTH DAY_OF_MONTH}. Field value validation is
         * not performed in this method. Any out of range values are either
         * normalized in lenient mode or detected as an invalid value in
         * non-lenient mode when building a {@code Calendar}.
         *
         * <p>
         * 将{@code field}参数设置为给定的{@code value}。
         *  {@code field}是{@link Calendar#fields}的索引,例如{@link日历#DAY_OF_MONTH DAY_OF_MONTH}。在此方法中不执行字段值验证。
         * 任何超出范围值在宽松模式下标准化或在构建{@code Calendar}时在非宽松模式中检测为无效值。
         * 
         * 
         * @param field an index to the {@code Calendar} fields
         * @param value the field value
         * @return this {@code Calendar.Builder}
         * @throws IllegalArgumentException if {@code field} is invalid
         * @throws IllegalStateException if the instant value has already been set,
         *                      or if fields have been set too many
         *                      (approximately {@link Integer#MAX_VALUE}) times.
         * @see Calendar#set(int, int)
         */
        public Builder set(int field, int value) {
            // Note: WEEK_YEAR can't be set with this method.
            if (field < 0 || field >= FIELD_COUNT) {
                throw new IllegalArgumentException("field is invalid");
            }
            if (isInstantSet()) {
                throw new IllegalStateException("instant has been set");
            }
            allocateFields();
            internalSet(field, value);
            return this;
        }

        /**
         * Sets field parameters to their values given by
         * {@code fieldValuePairs} that are pairs of a field and its value.
         * For example,
         * <pre>
         *   setFeilds(Calendar.YEAR, 2013,
         *             Calendar.MONTH, Calendar.DECEMBER,
         *             Calendar.DAY_OF_MONTH, 23);</pre>
         * is equivalent to the sequence of the following
         * {@link #set(int, int) set} calls:
         * <pre>
         *   set(Calendar.YEAR, 2013)
         *   .set(Calendar.MONTH, Calendar.DECEMBER)
         *   .set(Calendar.DAY_OF_MONTH, 23);</pre>
         *
         * <p>
         *  将字段参数设置为由{@code fieldValuePairs}给出的值,它们是字段及其值的对。例如,
         * <pre>
         *  setFeilds(Calendar.YEAR,2013,Calendar.MONTH,Calendar.DECEMBER,Calendar.DAY_OF_MONTH,23); </pre>等价于以下
         * {@link #set(int,int)set}调用的顺序：。
         * <pre>
         *  set(Calendar.YEAR,2013).set(Calendar.MONTH,Calendar.DECEMBER).set(Calendar.DAY_OF_MONTH,23); </pre>。
         * 
         * 
         * @param fieldValuePairs field-value pairs
         * @return this {@code Calendar.Builder}
         * @throws NullPointerException if {@code fieldValuePairs} is {@code null}
         * @throws IllegalArgumentException if any of fields are invalid,
         *             or if {@code fieldValuePairs.length} is an odd number.
         * @throws IllegalStateException    if the instant value has been set,
         *             or if fields have been set too many (approximately
         *             {@link Integer#MAX_VALUE}) times.
         */
        public Builder setFields(int... fieldValuePairs) {
            int len = fieldValuePairs.length;
            if ((len % 2) != 0) {
                throw new IllegalArgumentException();
            }
            if (isInstantSet()) {
                throw new IllegalStateException("instant has been set");
            }
            if ((nextStamp + len / 2) < 0) {
                throw new IllegalStateException("stamp counter overflow");
            }
            allocateFields();
            for (int i = 0; i < len; ) {
                int field = fieldValuePairs[i++];
                // Note: WEEK_YEAR can't be set with this method.
                if (field < 0 || field >= FIELD_COUNT) {
                    throw new IllegalArgumentException("field is invalid");
                }
                internalSet(field, fieldValuePairs[i++]);
            }
            return this;
        }

        /**
         * Sets the date field parameters to the values given by {@code year},
         * {@code month}, and {@code dayOfMonth}. This method is equivalent to
         * a call to:
         * <pre>
         *   setFields(Calendar.YEAR, year,
         *             Calendar.MONTH, month,
         *             Calendar.DAY_OF_MONTH, dayOfMonth);</pre>
         *
         * <p>
         *  将日期字段参数设置为{@code year},{@code month}和{@code dayOfMonth}给出的值。此方法等效于调用：
         * <pre>
         *  setFields(Calendar.YEAR,year,Calendar.MONTH,month,Calendar.DAY_OF_MONTH,dayOfMonth); </pre>
         * 
         * 
         * @param year       the {@link Calendar#YEAR YEAR} value
         * @param month      the {@link Calendar#MONTH MONTH} value
         *                   (the month numbering is <em>0-based</em>).
         * @param dayOfMonth the {@link Calendar#DAY_OF_MONTH DAY_OF_MONTH} value
         * @return this {@code Calendar.Builder}
         */
        public Builder setDate(int year, int month, int dayOfMonth) {
            return setFields(YEAR, year, MONTH, month, DAY_OF_MONTH, dayOfMonth);
        }

        /**
         * Sets the time of day field parameters to the values given by
         * {@code hourOfDay}, {@code minute}, and {@code second}. This method is
         * equivalent to a call to:
         * <pre>
         *   setTimeOfDay(hourOfDay, minute, second, 0);</pre>
         *
         * <p>
         *  将时段字段参数设置为{@code hourOfDay},{@code minute}和{@code second}给出的值。此方法等效于调用：
         * <pre>
         *  setTimeOfDay(hourOfDay,minute,second,0); </pre>
         * 
         * 
         * @param hourOfDay the {@link Calendar#HOUR_OF_DAY HOUR_OF_DAY} value
         *                  (24-hour clock)
         * @param minute    the {@link Calendar#MINUTE MINUTE} value
         * @param second    the {@link Calendar#SECOND SECOND} value
         * @return this {@code Calendar.Builder}
         */
        public Builder setTimeOfDay(int hourOfDay, int minute, int second) {
            return setTimeOfDay(hourOfDay, minute, second, 0);
        }

        /**
         * Sets the time of day field parameters to the values given by
         * {@code hourOfDay}, {@code minute}, {@code second}, and
         * {@code millis}. This method is equivalent to a call to:
         * <pre>
         *   setFields(Calendar.HOUR_OF_DAY, hourOfDay,
         *             Calendar.MINUTE, minute,
         *             Calendar.SECOND, second,
         *             Calendar.MILLISECOND, millis);</pre>
         *
         * <p>
         *  将时段字段参数设置为{@code hourOfDay},{@code minute},{@code second}和{@code millis}给出的值。此方法等效于调用：
         * <pre>
         * setFields(Calendar.HOUR_OF_DAY,hourOfDay,Calendar.MINUTE,minute,Calendar.SECOND,second,Calendar.MILLI
         * SECOND,millis); </pre>。
         * 
         * 
         * @param hourOfDay the {@link Calendar#HOUR_OF_DAY HOUR_OF_DAY} value
         *                  (24-hour clock)
         * @param minute    the {@link Calendar#MINUTE MINUTE} value
         * @param second    the {@link Calendar#SECOND SECOND} value
         * @param millis    the {@link Calendar#MILLISECOND MILLISECOND} value
         * @return this {@code Calendar.Builder}
         */
        public Builder setTimeOfDay(int hourOfDay, int minute, int second, int millis) {
            return setFields(HOUR_OF_DAY, hourOfDay, MINUTE, minute,
                             SECOND, second, MILLISECOND, millis);
        }

        /**
         * Sets the week-based date parameters to the values with the given
         * date specifiers - week year, week of year, and day of week.
         *
         * <p>If the specified calendar doesn't support week dates, the
         * {@link #build() build} method will throw an {@link IllegalArgumentException}.
         *
         * <p>
         *  将基于周的日期参数设置为具有给定日期说明符的值 - 周年,年周和星期几。
         * 
         *  <p>如果指定的日历不支持周日期,{@link #build()build}方法将抛出{@link IllegalArgumentException}。
         * 
         * 
         * @param weekYear   the week year
         * @param weekOfYear the week number based on {@code weekYear}
         * @param dayOfWeek  the day of week value: one of the constants
         *     for the {@link Calendar#DAY_OF_WEEK DAY_OF_WEEK} field:
         *     {@link Calendar#SUNDAY SUNDAY}, ..., {@link Calendar#SATURDAY SATURDAY}.
         * @return this {@code Calendar.Builder}
         * @see Calendar#setWeekDate(int, int, int)
         * @see Calendar#isWeekDateSupported()
         */
        public Builder setWeekDate(int weekYear, int weekOfYear, int dayOfWeek) {
            allocateFields();
            internalSet(WEEK_YEAR, weekYear);
            internalSet(WEEK_OF_YEAR, weekOfYear);
            internalSet(DAY_OF_WEEK, dayOfWeek);
            return this;
        }

        /**
         * Sets the time zone parameter to the given {@code zone}. If no time
         * zone parameter is given to this {@code Caledar.Builder}, the
         * {@linkplain TimeZone#getDefault() default
         * <code>TimeZone</code>} will be used in the {@link #build() build}
         * method.
         *
         * <p>
         *  将时区参数设置为给定的{@code zone}。
         * 如果没有给这个{@code Caledar.Builder}时区参数,{@linkplain TimeZone#getDefault()default <code> TimeZone </code>}将
         * 在{@link #build方法。
         *  将时区参数设置为给定的{@code zone}。
         * 
         * 
         * @param zone the {@link TimeZone}
         * @return this {@code Calendar.Builder}
         * @throws NullPointerException if {@code zone} is {@code null}
         * @see Calendar#setTimeZone(TimeZone)
         */
        public Builder setTimeZone(TimeZone zone) {
            if (zone == null) {
                throw new NullPointerException();
            }
            this.zone = zone;
            return this;
        }

        /**
         * Sets the lenient mode parameter to the value given by {@code lenient}.
         * If no lenient parameter is given to this {@code Calendar.Builder},
         * lenient mode will be used in the {@link #build() build} method.
         *
         * <p>
         *  将宽松模式参数设置为{@code lenient}给定的值。
         * 如果没有lenient参数给这个{@code Calendar.Builder},宽松模式将在{@link #build()build}方法中使用。
         * 
         * 
         * @param lenient {@code true} for lenient mode;
         *                {@code false} for non-lenient mode
         * @return this {@code Calendar.Builder}
         * @see Calendar#setLenient(boolean)
         */
        public Builder setLenient(boolean lenient) {
            this.lenient = lenient;
            return this;
        }

        /**
         * Sets the calendar type parameter to the given {@code type}. The
         * calendar type given by this method has precedence over any explicit
         * or implicit calendar type given by the
         * {@linkplain #setLocale(Locale) locale}.
         *
         * <p>In addition to the available calendar types returned by the
         * {@link Calendar#getAvailableCalendarTypes() Calendar.getAvailableCalendarTypes}
         * method, {@code "gregorian"} and {@code "iso8601"} as aliases of
         * {@code "gregory"} can be used with this method.
         *
         * <p>
         *  将日历类型参数设置为给定的{@code type}。此方法给出的日历类型优先于由{@linkplain #setLocale(Locale)locale}给出的任何显式或隐式日历类型。
         * 
         *  <p>除了{@link Calendar#getAvailableCalendarTypes()Calendar.getAvailableCalendarTypes}方法,{@code"gregorian"}
         * 和{@code"iso8601"}返回的可用日历类型为{@code"gregory "}可以与此方法一起使用。
         * 
         * 
         * @param type the calendar type
         * @return this {@code Calendar.Builder}
         * @throws NullPointerException if {@code type} is {@code null}
         * @throws IllegalArgumentException if {@code type} is unknown
         * @throws IllegalStateException if another calendar type has already been set
         * @see Calendar#getCalendarType()
         * @see Calendar#getAvailableCalendarTypes()
         */
        public Builder setCalendarType(String type) {
            if (type.equals("gregorian")) { // NPE if type == null
                type = "gregory";
            }
            if (!Calendar.getAvailableCalendarTypes().contains(type)
                    && !type.equals("iso8601")) {
                throw new IllegalArgumentException("unknown calendar type: " + type);
            }
            if (this.type == null) {
                this.type = type;
            } else {
                if (!this.type.equals(type)) {
                    throw new IllegalStateException("calendar type override");
                }
            }
            return this;
        }

        /**
         * Sets the locale parameter to the given {@code locale}. If no locale
         * is given to this {@code Calendar.Builder}, the {@linkplain
         * Locale#getDefault(Locale.Category) default <code>Locale</code>}
         * for {@link Locale.Category#FORMAT} will be used.
         *
         * <p>If no calendar type is explicitly given by a call to the
         * {@link #setCalendarType(String) setCalendarType} method,
         * the {@code Locale} value is used to determine what type of
         * {@code Calendar} to be built.
         *
         * <p>If no week definition parameters are explicitly given by a call to
         * the {@link #setWeekDefinition(int,int) setWeekDefinition} method, the
         * {@code Locale}'s default values are used.
         *
         * <p>
         * 将locale参数设置为给定的{@code locale}。
         * 如果没有为此{@code Calendar.Builder}指定区域设置,则将使用{@link Locale.Category#FORMAT}的{@linkplain Locale#getDefault(Locale.Category)默认<code> Locale </code>}
         *  。
         * 将locale参数设置为给定的{@code locale}。
         * 
         *  <p>如果没有通过调用{@link #setCalendarType(String)setCalendarType}方法显式给出日历类型,则使用{@code Locale}值来确定要构建的是什么类型的
         * {@code Calendar}。
         * 
         *  <p>如果没有通过调用{@link #setWeekDefinition(int,int)setWeekDefinition}方法显式给出星期定义参数,则会使用{@code Locale}的默认值。
         * 
         * 
         * @param locale the {@link Locale}
         * @throws NullPointerException if {@code locale} is {@code null}
         * @return this {@code Calendar.Builder}
         * @see Calendar#getInstance(Locale)
         */
        public Builder setLocale(Locale locale) {
            if (locale == null) {
                throw new NullPointerException();
            }
            this.locale = locale;
            return this;
        }

        /**
         * Sets the week definition parameters to the values given by
         * {@code firstDayOfWeek} and {@code minimalDaysInFirstWeek} that are
         * used to determine the <a href="Calendar.html#First_Week">first
         * week</a> of a year. The parameters given by this method have
         * precedence over the default values given by the
         * {@linkplain #setLocale(Locale) locale}.
         *
         * <p>
         *  将星期定义参数设置为{@code firstDayOfWeek}和{@code minimalDaysInFirstWeek}给定的值,这些值用于确定一年中的<a href="Calendar.html#First_Week">
         * 第一周</a>。
         * 此方法给出的参数优先于由{@linkplain #setLocale(Locale)locale}给出的默认值。
         * 
         * 
         * @param firstDayOfWeek the first day of a week; one of
         *                       {@link Calendar#SUNDAY} to {@link Calendar#SATURDAY}
         * @param minimalDaysInFirstWeek the minimal number of days in the first
         *                               week (1..7)
         * @return this {@code Calendar.Builder}
         * @throws IllegalArgumentException if {@code firstDayOfWeek} or
         *                                  {@code minimalDaysInFirstWeek} is invalid
         * @see Calendar#getFirstDayOfWeek()
         * @see Calendar#getMinimalDaysInFirstWeek()
         */
        public Builder setWeekDefinition(int firstDayOfWeek, int minimalDaysInFirstWeek) {
            if (!isValidWeekParameter(firstDayOfWeek)
                    || !isValidWeekParameter(minimalDaysInFirstWeek)) {
                throw new IllegalArgumentException();
            }
            this.firstDayOfWeek = firstDayOfWeek;
            this.minimalDaysInFirstWeek = minimalDaysInFirstWeek;
            return this;
        }

        /**
         * Returns a {@code Calendar} built from the parameters set by the
         * setter methods. The calendar type given by the {@link #setCalendarType(String)
         * setCalendarType} method or the {@linkplain #setLocale(Locale) locale} is
         * used to determine what {@code Calendar} to be created. If no explicit
         * calendar type is given, the locale's default calendar is created.
         *
         * <p>If the calendar type is {@code "iso8601"}, the
         * {@linkplain GregorianCalendar#setGregorianChange(Date) Gregorian change date}
         * of a {@link GregorianCalendar} is set to {@code Date(Long.MIN_VALUE)}
         * to be the <em>proleptic</em> Gregorian calendar. Its week definition
         * parameters are also set to be <a
         * href="GregorianCalendar.html#iso8601_compatible_setting">compatible
         * with the ISO 8601 standard</a>. Note that the
         * {@link GregorianCalendar#getCalendarType() getCalendarType} method of
         * a {@code GregorianCalendar} created with {@code "iso8601"} returns
         * {@code "gregory"}.
         *
         * <p>The default values are used for locale and time zone if these
         * parameters haven't been given explicitly.
         *
         * <p>Any out of range field values are either normalized in lenient
         * mode or detected as an invalid value in non-lenient mode.
         *
         * <p>
         *  返回根据setter方法设置的参数构建的{@code Calendar}。
         * 由{@link #setCalendarType(String)setCalendarType}方法或{@linkplain #setLocale(Locale)locale}指定的日历类型用于确定要创
         * 建的{@code Calendar}。
         *  返回根据setter方法设置的参数构建的{@code Calendar}。如果未指定显式日历类型,则会创建语言环境的默认日历。
         * 
         * <p>如果日历类型为{@code"iso8601"},{@link GregorianCalendar}的{@linkplain GregorianCalendar#setGregorianChange(Date)Gregorian change date}
         * 设置为{@code Date(Long.MIN_VALUE)}成为<em> proleptian </em>公历。
         * 其周定义参数也设置为<a href="GregorianCalendar.html#iso8601_compatible_setting">与ISO 8601标准兼容</a>。
         * 请注意,使用{@code"iso8601"}创建的{@code GregorianCalendar}的{@link GregorianCalendar#getCalendarType()getCalendarType}
         * 方法会返回{@code"gregory"}。
         * 其周定义参数也设置为<a href="GregorianCalendar.html#iso8601_compatible_setting">与ISO 8601标准兼容</a>。
         * 
         *  <p>如果没有明确给出这些参数,则默认值用于区域设置和时区。
         * 
         *  <p>任何超出范围的字段值都以宽松模式标准化,或在非宽松模式下检测为无效值。
         * 
         * 
         * @return a {@code Calendar} built with parameters of this {@code
         *         Calendar.Builder}
         * @throws IllegalArgumentException if the calendar type is unknown, or
         *             if any invalid field values are given in non-lenient mode, or
         *             if a week date is given for the calendar type that doesn't
         *             support week dates.
         * @see Calendar#getInstance(TimeZone, Locale)
         * @see Locale#getDefault(Locale.Category)
         * @see TimeZone#getDefault()
         */
        public Calendar build() {
            if (locale == null) {
                locale = Locale.getDefault();
            }
            if (zone == null) {
                zone = TimeZone.getDefault();
            }
            Calendar cal;
            if (type == null) {
                type = locale.getUnicodeLocaleType("ca");
            }
            if (type == null) {
                if (locale.getCountry() == "TH"
                    && locale.getLanguage() == "th") {
                    type = "buddhist";
                } else {
                    type = "gregory";
                }
            }
            switch (type) {
            case "gregory":
                cal = new GregorianCalendar(zone, locale, true);
                break;
            case "iso8601":
                GregorianCalendar gcal = new GregorianCalendar(zone, locale, true);
                // make gcal a proleptic Gregorian
                gcal.setGregorianChange(new Date(Long.MIN_VALUE));
                // and week definition to be compatible with ISO 8601
                setWeekDefinition(MONDAY, 4);
                cal = gcal;
                break;
            case "buddhist":
                cal = new BuddhistCalendar(zone, locale);
                cal.clear();
                break;
            case "japanese":
                cal = new JapaneseImperialCalendar(zone, locale, true);
                break;
            default:
                throw new IllegalArgumentException("unknown calendar type: " + type);
            }
            cal.setLenient(lenient);
            if (firstDayOfWeek != 0) {
                cal.setFirstDayOfWeek(firstDayOfWeek);
                cal.setMinimalDaysInFirstWeek(minimalDaysInFirstWeek);
            }
            if (isInstantSet()) {
                cal.setTimeInMillis(instant);
                cal.complete();
                return cal;
            }

            if (fields != null) {
                boolean weekDate = isSet(WEEK_YEAR)
                                       && fields[WEEK_YEAR] > fields[YEAR];
                if (weekDate && !cal.isWeekDateSupported()) {
                    throw new IllegalArgumentException("week date is unsupported by " + type);
                }

                // Set the fields from the min stamp to the max stamp so that
                // the fields resolution works in the Calendar.
                for (int stamp = MINIMUM_USER_STAMP; stamp < nextStamp; stamp++) {
                    for (int index = 0; index <= maxFieldIndex; index++) {
                        if (fields[index] == stamp) {
                            cal.set(index, fields[NFIELDS + index]);
                            break;
                        }
                    }
                }

                if (weekDate) {
                    int weekOfYear = isSet(WEEK_OF_YEAR) ? fields[NFIELDS + WEEK_OF_YEAR] : 1;
                    int dayOfWeek = isSet(DAY_OF_WEEK)
                                    ? fields[NFIELDS + DAY_OF_WEEK] : cal.getFirstDayOfWeek();
                    cal.setWeekDate(fields[NFIELDS + WEEK_YEAR], weekOfYear, dayOfWeek);
                }
                cal.complete();
            }

            return cal;
        }

        private void allocateFields() {
            if (fields == null) {
                fields = new int[NFIELDS * 2];
                nextStamp = MINIMUM_USER_STAMP;
                maxFieldIndex = -1;
            }
        }

        private void internalSet(int field, int value) {
            fields[field] = nextStamp++;
            if (nextStamp < 0) {
                throw new IllegalStateException("stamp counter overflow");
            }
            fields[NFIELDS + field] = value;
            if (field > maxFieldIndex && field < WEEK_YEAR) {
                maxFieldIndex = field;
            }
        }

        private boolean isInstantSet() {
            return nextStamp == COMPUTED;
        }

        private boolean isSet(int index) {
            return fields != null && fields[index] > UNSET;
        }

        private boolean isValidWeekParameter(int value) {
            return value > 0 && value <= 7;
        }
    }

    /**
     * Constructs a Calendar with the default time zone
     * and the default {@link java.util.Locale.Category#FORMAT FORMAT}
     * locale.
     * <p>
     *  使用默认时区和默认{@link java.util.Locale.Category#FORMAT FORMAT}区域设置构造日历。
     * 
     * 
     * @see     TimeZone#getDefault
     */
    protected Calendar()
    {
        this(TimeZone.getDefaultRef(), Locale.getDefault(Locale.Category.FORMAT));
        sharedZone = true;
    }

    /**
     * Constructs a calendar with the specified time zone and locale.
     *
     * <p>
     *  构造具有指定时区和区域设置的日历。
     * 
     * 
     * @param zone the time zone to use
     * @param aLocale the locale for the week data
     */
    protected Calendar(TimeZone zone, Locale aLocale)
    {
        fields = new int[FIELD_COUNT];
        isSet = new boolean[FIELD_COUNT];
        stamp = new int[FIELD_COUNT];

        this.zone = zone;
        setWeekCountData(aLocale);
    }

    /**
     * Gets a calendar using the default time zone and locale. The
     * <code>Calendar</code> returned is based on the current time
     * in the default time zone with the default
     * {@link Locale.Category#FORMAT FORMAT} locale.
     *
     * <p>
     *  使用默认时区和区域设置获取日历。
     * 返回的<code> Calendar </code>基于默认时区的当前时间,默认使用{@link Locale.Category#FORMAT FORMAT}区域设置。
     * 
     * 
     * @return a Calendar.
     */
    public static Calendar getInstance()
    {
        return createCalendar(TimeZone.getDefault(), Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Gets a calendar using the specified time zone and default locale.
     * The <code>Calendar</code> returned is based on the current time
     * in the given time zone with the default
     * {@link Locale.Category#FORMAT FORMAT} locale.
     *
     * <p>
     *  使用指定的时区和默认语言环境获取日历。返回的<code>日历</code>基于指定时区中的当前时间,默认使用{@link Locale.Category#FORMAT FORMAT}区域设置。
     * 
     * 
     * @param zone the time zone to use
     * @return a Calendar.
     */
    public static Calendar getInstance(TimeZone zone)
    {
        return createCalendar(zone, Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Gets a calendar using the default time zone and specified locale.
     * The <code>Calendar</code> returned is based on the current time
     * in the default time zone with the given locale.
     *
     * <p>
     * 使用默认时区和指定的区域设置获取日历。返回的<code> Calendar </code>基于具有给定语言环境的默认时区中的当前时间。
     * 
     * 
     * @param aLocale the locale for the week data
     * @return a Calendar.
     */
    public static Calendar getInstance(Locale aLocale)
    {
        return createCalendar(TimeZone.getDefault(), aLocale);
    }

    /**
     * Gets a calendar with the specified time zone and locale.
     * The <code>Calendar</code> returned is based on the current time
     * in the given time zone with the given locale.
     *
     * <p>
     *  获取具有指定时区和区域设置的日历。返回的<code> Calendar </code>基于给定时区中具有给定语言环境的当前时间。
     * 
     * 
     * @param zone the time zone to use
     * @param aLocale the locale for the week data
     * @return a Calendar.
     */
    public static Calendar getInstance(TimeZone zone,
                                       Locale aLocale)
    {
        return createCalendar(zone, aLocale);
    }

    private static Calendar createCalendar(TimeZone zone,
                                           Locale aLocale)
    {
        CalendarProvider provider =
            LocaleProviderAdapter.getAdapter(CalendarProvider.class, aLocale)
                                 .getCalendarProvider();
        if (provider != null) {
            try {
                return provider.getInstance(zone, aLocale);
            } catch (IllegalArgumentException iae) {
                // fall back to the default instantiation
            }
        }

        Calendar cal = null;

        if (aLocale.hasExtensions()) {
            String caltype = aLocale.getUnicodeLocaleType("ca");
            if (caltype != null) {
                switch (caltype) {
                case "buddhist":
                cal = new BuddhistCalendar(zone, aLocale);
                    break;
                case "japanese":
                    cal = new JapaneseImperialCalendar(zone, aLocale);
                    break;
                case "gregory":
                    cal = new GregorianCalendar(zone, aLocale);
                    break;
                }
            }
        }
        if (cal == null) {
            // If no known calendar type is explicitly specified,
            // perform the traditional way to create a Calendar:
            // create a BuddhistCalendar for th_TH locale,
            // a JapaneseImperialCalendar for ja_JP_JP locale, or
            // a GregorianCalendar for any other locales.
            // NOTE: The language, country and variant strings are interned.
            if (aLocale.getLanguage() == "th" && aLocale.getCountry() == "TH") {
                cal = new BuddhistCalendar(zone, aLocale);
            } else if (aLocale.getVariant() == "JP" && aLocale.getLanguage() == "ja"
                       && aLocale.getCountry() == "JP") {
                cal = new JapaneseImperialCalendar(zone, aLocale);
            } else {
                cal = new GregorianCalendar(zone, aLocale);
            }
        }
        return cal;
    }

    /**
     * Returns an array of all locales for which the <code>getInstance</code>
     * methods of this class can return localized instances.
     * The array returned must contain at least a <code>Locale</code>
     * instance equal to {@link java.util.Locale#US Locale.US}.
     *
     * <p>
     *  返回所有语言环境的数组,其中此类的<code> getInstance </code>方法可以返回本地化实例。
     * 返回的数组必须至少包含等于{@link java.util.Locale#US Locale.US}的<code> Locale </code>实例。
     * 
     * 
     * @return An array of locales for which localized
     *         <code>Calendar</code> instances are available.
     */
    public static synchronized Locale[] getAvailableLocales()
    {
        return DateFormat.getAvailableLocales();
    }

    /**
     * Converts the current calendar field values in {@link #fields fields[]}
     * to the millisecond time value
     * {@link #time}.
     *
     * <p>
     *  将{@link #fields fields []}中的当前日历字段值转换为毫秒时间值{@link #time}。
     * 
     * 
     * @see #complete()
     * @see #computeFields()
     */
    protected abstract void computeTime();

    /**
     * Converts the current millisecond time value {@link #time}
     * to calendar field values in {@link #fields fields[]}.
     * This allows you to sync up the calendar field values with
     * a new time that is set for the calendar.  The time is <em>not</em>
     * recomputed first; to recompute the time, then the fields, call the
     * {@link #complete()} method.
     *
     * <p>
     *  将当前毫秒时间值{@link #time}转换为{@link #fields fields []}中的日历字段值。这允许您将日历字段值与为日历设置的新时间同步。
     * 时间不是</em>首先重新计算;重新计算时间,然后是字段,调用{@link #complete()}方法。
     * 
     * 
     * @see #computeTime()
     */
    protected abstract void computeFields();

    /**
     * Returns a <code>Date</code> object representing this
     * <code>Calendar</code>'s time value (millisecond offset from the <a
     * href="#Epoch">Epoch</a>").
     *
     * <p>
     *  返回代表此<code> Calendar </code>的时间值(距离<a href="#Epoch"> Epoch </a>的毫秒偏移)的<code> Date </code>对象。
     * 
     * 
     * @return a <code>Date</code> representing the time value.
     * @see #setTime(Date)
     * @see #getTimeInMillis()
     */
    public final Date getTime() {
        return new Date(getTimeInMillis());
    }

    /**
     * Sets this Calendar's time with the given <code>Date</code>.
     * <p>
     * Note: Calling <code>setTime()</code> with
     * <code>Date(Long.MAX_VALUE)</code> or <code>Date(Long.MIN_VALUE)</code>
     * may yield incorrect field values from <code>get()</code>.
     *
     * <p>
     *  使用给定的<code> Date </code>设置此日历的时间。
     * <p>
     *  注意：使用<code> Date(Long.MAX_VALUE)</code>或<code> Date(Long.MIN_VALUE)</code>调用<code> setTime()</code>可
     * 能会导致<code> get()</code>。
     * 
     * 
     * @param date the given Date.
     * @see #getTime()
     * @see #setTimeInMillis(long)
     */
    public final void setTime(Date date) {
        setTimeInMillis(date.getTime());
    }

    /**
     * Returns this Calendar's time value in milliseconds.
     *
     * <p>
     *  返回此日历的时间值(以毫秒为单位)。
     * 
     * 
     * @return the current time as UTC milliseconds from the epoch.
     * @see #getTime()
     * @see #setTimeInMillis(long)
     */
    public long getTimeInMillis() {
        if (!isTimeSet) {
            updateTime();
        }
        return time;
    }

    /**
     * Sets this Calendar's current time from the given long value.
     *
     * <p>
     * 根据给定的长整数值设置此日历的当前时间。
     * 
     * 
     * @param millis the new time in UTC milliseconds from the epoch.
     * @see #setTime(Date)
     * @see #getTimeInMillis()
     */
    public void setTimeInMillis(long millis) {
        // If we don't need to recalculate the calendar field values,
        // do nothing.
        if (time == millis && isTimeSet && areFieldsSet && areAllFieldsSet
            && (zone instanceof ZoneInfo) && !((ZoneInfo)zone).isDirty()) {
            return;
        }
        time = millis;
        isTimeSet = true;
        areFieldsSet = false;
        computeFields();
        areAllFieldsSet = areFieldsSet = true;
    }

    /**
     * Returns the value of the given calendar field. In lenient mode,
     * all calendar fields are normalized. In non-lenient mode, all
     * calendar fields are validated and this method throws an
     * exception if any calendar fields have out-of-range values. The
     * normalization and validation are handled by the
     * {@link #complete()} method, which process is calendar
     * system dependent.
     *
     * <p>
     *  返回给定日历字段的值。在宽松模式下,所有日历字段均被标准化。在非宽松模式下,将验证所有日历字段,如果任何日历字段超出范围值,此方法将抛出异常。
     * 规范化和验证由{@link #complete()}方法处理,该过程是与日历系统相关的。
     * 
     * 
     * @param field the given calendar field.
     * @return the value for the given calendar field.
     * @throws ArrayIndexOutOfBoundsException if the specified field is out of range
     *             (<code>field &lt; 0 || field &gt;= FIELD_COUNT</code>).
     * @see #set(int,int)
     * @see #complete()
     */
    public int get(int field)
    {
        complete();
        return internalGet(field);
    }

    /**
     * Returns the value of the given calendar field. This method does
     * not involve normalization or validation of the field value.
     *
     * <p>
     *  返回给定日历字段的值。此方法不涉及字段值的归一化或验证。
     * 
     * 
     * @param field the given calendar field.
     * @return the value for the given calendar field.
     * @see #get(int)
     */
    protected final int internalGet(int field)
    {
        return fields[field];
    }

    /**
     * Sets the value of the given calendar field. This method does
     * not affect any setting state of the field in this
     * <code>Calendar</code> instance.
     *
     * <p>
     *  设置给定日历字段的值。此方法不会影响此<code> Calendar </code>实例中字段的任何设置状态。
     * 
     * 
     * @throws IndexOutOfBoundsException if the specified field is out of range
     *             (<code>field &lt; 0 || field &gt;= FIELD_COUNT</code>).
     * @see #areFieldsSet
     * @see #isTimeSet
     * @see #areAllFieldsSet
     * @see #set(int,int)
     */
    final void internalSet(int field, int value)
    {
        fields[field] = value;
    }

    /**
     * Sets the given calendar field to the given value. The value is not
     * interpreted by this method regardless of the leniency mode.
     *
     * <p>
     *  将给定的日历字段设置为给定值。该方法不解释该值,无论宽松模式如何。
     * 
     * 
     * @param field the given calendar field.
     * @param value the value to be set for the given calendar field.
     * @throws ArrayIndexOutOfBoundsException if the specified field is out of range
     *             (<code>field &lt; 0 || field &gt;= FIELD_COUNT</code>).
     * in non-lenient mode.
     * @see #set(int,int,int)
     * @see #set(int,int,int,int,int)
     * @see #set(int,int,int,int,int,int)
     * @see #get(int)
     */
    public void set(int field, int value)
    {
        // If the fields are partially normalized, calculate all the
        // fields before changing any fields.
        if (areFieldsSet && !areAllFieldsSet) {
            computeFields();
        }
        internalSet(field, value);
        isTimeSet = false;
        areFieldsSet = false;
        isSet[field] = true;
        stamp[field] = nextStamp++;
        if (nextStamp == Integer.MAX_VALUE) {
            adjustStamp();
        }
    }

    /**
     * Sets the values for the calendar fields <code>YEAR</code>,
     * <code>MONTH</code>, and <code>DAY_OF_MONTH</code>.
     * Previous values of other calendar fields are retained.  If this is not desired,
     * call {@link #clear()} first.
     *
     * <p>
     *  设置日历字段<code> YEAR </code>,<code> MONTH </code>和<code> DAY_OF_MONTH </code>的值。保留其他日历字段的先前值。
     * 如果不需要,请首先调用{@link #clear()}。
     * 
     * 
     * @param year the value used to set the <code>YEAR</code> calendar field.
     * @param month the value used to set the <code>MONTH</code> calendar field.
     * Month value is 0-based. e.g., 0 for January.
     * @param date the value used to set the <code>DAY_OF_MONTH</code> calendar field.
     * @see #set(int,int)
     * @see #set(int,int,int,int,int)
     * @see #set(int,int,int,int,int,int)
     */
    public final void set(int year, int month, int date)
    {
        set(YEAR, year);
        set(MONTH, month);
        set(DATE, date);
    }

    /**
     * Sets the values for the calendar fields <code>YEAR</code>,
     * <code>MONTH</code>, <code>DAY_OF_MONTH</code>,
     * <code>HOUR_OF_DAY</code>, and <code>MINUTE</code>.
     * Previous values of other fields are retained.  If this is not desired,
     * call {@link #clear()} first.
     *
     * <p>
     *  设置日历字段的值<code> YEAR </code>,<code> MONTH </code>,<code> DAY_OF_MONTH </code>,<code> HOUR_OF_DAY </code>
     * 和<code> MINUTE <代码>。
     * 保留其他字段的先前值。如果不需要,请首先调用{@link #clear()}。
     * 
     * 
     * @param year the value used to set the <code>YEAR</code> calendar field.
     * @param month the value used to set the <code>MONTH</code> calendar field.
     * Month value is 0-based. e.g., 0 for January.
     * @param date the value used to set the <code>DAY_OF_MONTH</code> calendar field.
     * @param hourOfDay the value used to set the <code>HOUR_OF_DAY</code> calendar field.
     * @param minute the value used to set the <code>MINUTE</code> calendar field.
     * @see #set(int,int)
     * @see #set(int,int,int)
     * @see #set(int,int,int,int,int,int)
     */
    public final void set(int year, int month, int date, int hourOfDay, int minute)
    {
        set(YEAR, year);
        set(MONTH, month);
        set(DATE, date);
        set(HOUR_OF_DAY, hourOfDay);
        set(MINUTE, minute);
    }

    /**
     * Sets the values for the fields <code>YEAR</code>, <code>MONTH</code>,
     * <code>DAY_OF_MONTH</code>, <code>HOUR_OF_DAY</code>, <code>MINUTE</code>, and
     * <code>SECOND</code>.
     * Previous values of other fields are retained.  If this is not desired,
     * call {@link #clear()} first.
     *
     * <p>
     * 设置字段<code> YEAR </code>,<code> MONTH </code>,<code> DAY_OF_MONTH </code>,<code> HOUR_OF_DAY </code>,<code>
     *  MINUTE </code> ,<code> SECOND </code>。
     * 保留其他字段的先前值。如果不需要,请首先调用{@link #clear()}。
     * 
     * 
     * @param year the value used to set the <code>YEAR</code> calendar field.
     * @param month the value used to set the <code>MONTH</code> calendar field.
     * Month value is 0-based. e.g., 0 for January.
     * @param date the value used to set the <code>DAY_OF_MONTH</code> calendar field.
     * @param hourOfDay the value used to set the <code>HOUR_OF_DAY</code> calendar field.
     * @param minute the value used to set the <code>MINUTE</code> calendar field.
     * @param second the value used to set the <code>SECOND</code> calendar field.
     * @see #set(int,int)
     * @see #set(int,int,int)
     * @see #set(int,int,int,int,int)
     */
    public final void set(int year, int month, int date, int hourOfDay, int minute,
                          int second)
    {
        set(YEAR, year);
        set(MONTH, month);
        set(DATE, date);
        set(HOUR_OF_DAY, hourOfDay);
        set(MINUTE, minute);
        set(SECOND, second);
    }

    /**
     * Sets all the calendar field values and the time value
     * (millisecond offset from the <a href="#Epoch">Epoch</a>) of
     * this <code>Calendar</code> undefined. This means that {@link
     * #isSet(int) isSet()} will return <code>false</code> for all the
     * calendar fields, and the date and time calculations will treat
     * the fields as if they had never been set. A
     * <code>Calendar</code> implementation class may use its specific
     * default field values for date/time calculations. For example,
     * <code>GregorianCalendar</code> uses 1970 if the
     * <code>YEAR</code> field value is undefined.
     *
     * <p>
     *  设置此<code>日历</code>未定义的所有日历字段值和时间值(与<a href="#Epoch">时代</a>的毫秒偏移量)。
     * 这意味着{@link #isSet(int)isSet()}将为所有日历字段返回<code> false </code>,日期和时间计算会将字段视为从未设置过。
     *  <code> Calendar </code>实现类可以使用其特定的默认字段值进行日期/时间计算。
     * 例如,如果<code> YEAR </code>字段值未定义,<code> GregorianCalendar </code>使用1970。
     * 
     * 
     * @see #clear(int)
     */
    public final void clear()
    {
        for (int i = 0; i < fields.length; ) {
            stamp[i] = fields[i] = 0; // UNSET == 0
            isSet[i++] = false;
        }
        areAllFieldsSet = areFieldsSet = false;
        isTimeSet = false;
    }

    /**
     * Sets the given calendar field value and the time value
     * (millisecond offset from the <a href="#Epoch">Epoch</a>) of
     * this <code>Calendar</code> undefined. This means that {@link
     * #isSet(int) isSet(field)} will return <code>false</code>, and
     * the date and time calculations will treat the field as if it
     * had never been set. A <code>Calendar</code> implementation
     * class may use the field's specific default value for date and
     * time calculations.
     *
     * <p>The {@link #HOUR_OF_DAY}, {@link #HOUR} and {@link #AM_PM}
     * fields are handled independently and the <a
     * href="#time_resolution">the resolution rule for the time of
     * day</a> is applied. Clearing one of the fields doesn't reset
     * the hour of day value of this <code>Calendar</code>. Use {@link
     * #set(int,int) set(Calendar.HOUR_OF_DAY, 0)} to reset the hour
     * value.
     *
     * <p>
     *  设置此<code> Calendar </code>未定义的给定日历字段值和时间值(与<a href="#Epoch">时代</a>的毫秒偏移量)。
     * 这意味着{@link #isSet(int)isSet(field)}将返回<code> false </code>,日期和时间计算将该字段视为从未设置过。
     *  <code> Calendar </code>实现类可以使用字段的特定默认值进行日期和时间计算。
     * 
     * <p> {@link #HOUR_OF_DAY},{@link #HOUR}和{@link #AM_PM}字段是单独处理的,<a href="#time_resolution">时间的解析规则</a >
     * 。
     * 清除其中一个字段不会重置此<code>日历</code>的日时值。使用{@link #set(int,int)set(Calendar.HOUR_OF_DAY,0)}重置小时值。
     * 
     * 
     * @param field the calendar field to be cleared.
     * @see #clear()
     */
    public final void clear(int field)
    {
        fields[field] = 0;
        stamp[field] = UNSET;
        isSet[field] = false;

        areAllFieldsSet = areFieldsSet = false;
        isTimeSet = false;
    }

    /**
     * Determines if the given calendar field has a value set,
     * including cases that the value has been set by internal fields
     * calculations triggered by a <code>get</code> method call.
     *
     * <p>
     *  确定给定日历字段是否设置了值,包括通过<code> get </code>方法调用触发的内部字段计算设置值的情况。
     * 
     * 
     * @param field the calendar field to test
     * @return <code>true</code> if the given calendar field has a value set;
     * <code>false</code> otherwise.
     */
    public final boolean isSet(int field)
    {
        return stamp[field] != UNSET;
    }

    /**
     * Returns the string representation of the calendar
     * <code>field</code> value in the given <code>style</code> and
     * <code>locale</code>.  If no string representation is
     * applicable, <code>null</code> is returned. This method calls
     * {@link Calendar#get(int) get(field)} to get the calendar
     * <code>field</code> value if the string representation is
     * applicable to the given calendar <code>field</code>.
     *
     * <p>For example, if this <code>Calendar</code> is a
     * <code>GregorianCalendar</code> and its date is 2005-01-01, then
     * the string representation of the {@link #MONTH} field would be
     * "January" in the long style in an English locale or "Jan" in
     * the short style. However, no string representation would be
     * available for the {@link #DAY_OF_MONTH} field, and this method
     * would return <code>null</code>.
     *
     * <p>The default implementation supports the calendar fields for
     * which a {@link DateFormatSymbols} has names in the given
     * <code>locale</code>.
     *
     * <p>
     *  返回给定<code> style </code>和<code> locale </code>中的日历<code>字段</code>的字符串表示形式。
     * 如果没有适用的字符串表示,则返回<code> null </code>。
     * 如果字符串表示适用于给定的日历<code>字段</code>,则此方法调用{@link Calendar#get(int)get(field)}以获取日历<code>字段</code>。
     * 
     *  <p>例如,如果此<code>日历</code>是<code> GregorianCalendar </code>,日期为2005-01-01,则{@link #MONTH}字段的字符串表示在英语地区
     * 的长风格中为"一月"或在短风格中为"Jan"。
     * 但是,对于{@link #DAY_OF_MONTH}字段,字符串表示不可用,并且此方法将返回<code> null </code>。
     * 
     *  <p>默认实现支持{@link DateFormatSymbols}在给定的<code> locale </code>中具有名称的日历字段。
     * 
     * 
     * @param field
     *        the calendar field for which the string representation
     *        is returned
     * @param style
     *        the style applied to the string representation; one of {@link
     *        #SHORT_FORMAT} ({@link #SHORT}), {@link #SHORT_STANDALONE},
     *        {@link #LONG_FORMAT} ({@link #LONG}), {@link #LONG_STANDALONE},
     *        {@link #NARROW_FORMAT}, or {@link #NARROW_STANDALONE}.
     * @param locale
     *        the locale for the string representation
     *        (any calendar types specified by {@code locale} are ignored)
     * @return the string representation of the given
     *        {@code field} in the given {@code style}, or
     *        {@code null} if no string representation is
     *        applicable.
     * @exception IllegalArgumentException
     *        if {@code field} or {@code style} is invalid,
     *        or if this {@code Calendar} is non-lenient and any
     *        of the calendar fields have invalid values
     * @exception NullPointerException
     *        if {@code locale} is null
     * @since 1.6
     */
    public String getDisplayName(int field, int style, Locale locale) {
        if (!checkDisplayNameParams(field, style, SHORT, NARROW_FORMAT, locale,
                            ERA_MASK|MONTH_MASK|DAY_OF_WEEK_MASK|AM_PM_MASK)) {
            return null;
        }

        // the standalone and narrow styles are supported only through CalendarDataProviders.
        if (isStandaloneStyle(style) || isNarrowStyle(style)) {
            return CalendarDataUtility.retrieveFieldValueName(getCalendarType(),
                                                              field, get(field),
                                                              style, locale);
        }

        DateFormatSymbols symbols = DateFormatSymbols.getInstance(locale);
        String[] strings = getFieldStrings(field, style, symbols);
        if (strings != null) {
            int fieldValue = get(field);
            if (fieldValue < strings.length) {
                return strings[fieldValue];
            }
        }
        return null;
    }

    /**
     * Returns a {@code Map} containing all names of the calendar
     * {@code field} in the given {@code style} and
     * {@code locale} and their corresponding field values. For
     * example, if this {@code Calendar} is a {@link
     * GregorianCalendar}, the returned map would contain "Jan" to
     * {@link #JANUARY}, "Feb" to {@link #FEBRUARY}, and so on, in the
     * {@linkplain #SHORT short} style in an English locale.
     *
     * <p>Narrow names may not be unique due to use of single characters,
     * such as "S" for Sunday and Saturday. In that case narrow names are not
     * included in the returned {@code Map}.
     *
     * <p>The values of other calendar fields may be taken into
     * account to determine a set of display names. For example, if
     * this {@code Calendar} is a lunisolar calendar system and
     * the year value given by the {@link #YEAR} field has a leap
     * month, this method would return month names containing the leap
     * month name, and month names are mapped to their values specific
     * for the year.
     *
     * <p>The default implementation supports display names contained in
     * a {@link DateFormatSymbols}. For example, if {@code field}
     * is {@link #MONTH} and {@code style} is {@link
     * #ALL_STYLES}, this method returns a {@code Map} containing
     * all strings returned by {@link DateFormatSymbols#getShortMonths()}
     * and {@link DateFormatSymbols#getMonths()}.
     *
     * <p>
     * 返回包含给定{@code style}和{@code locale}中的日历{@code field}的所有名称及其相应字段值的{@code Map}。
     * 例如,如果{@code Calendar}是{@link GregorianCalendar},则返回的地图将包含"Jan"到{@link #JANUARY},"Feb"到{@link #FEBRUARY}
     * 英语区域设置中的{@linkplain #SHORT short}样式。
     * 返回包含给定{@code style}和{@code locale}中的日历{@code field}的所有名称及其相应字段值的{@code Map}。
     * 
     *  <p>由于使用单个字符(例如星期日和星期六的"S"),缩小的名称可能不是唯一的。在这种情况下,狭窄的名称不包括在返回的{@code Map}中。
     * 
     *  <p>可以考虑其他日历字段的值以确定一组显示名称。
     * 例如,如果{@code Calendar}是一个月日历系统,并且{@link #YEAR}字段给出的年值具有闰月,则此方法将返回包含闰月名称的月份名称,月份名称映射到其年度的特定值。
     * 
     *  <p>默认实现支持{@link DateFormatSymbols}中包含的显示名称。
     * 例如,如果{@code field}为{@link #MONTH}且{@code style}为{@link #ALL_STYLES},此方法将返回一个{@code Map},其中包含{@link DateFormatSymbols#getShortMonths ()}
     * 和{@link DateFormatSymbols#getMonths()}。
     *  <p>默认实现支持{@link DateFormatSymbols}中包含的显示名称。
     * 
     * 
     * @param field
     *        the calendar field for which the display names are returned
     * @param style
     *        the style applied to the string representation; one of {@link
     *        #SHORT_FORMAT} ({@link #SHORT}), {@link #SHORT_STANDALONE},
     *        {@link #LONG_FORMAT} ({@link #LONG}), {@link #LONG_STANDALONE},
     *        {@link #NARROW_FORMAT}, or {@link #NARROW_STANDALONE}
     * @param locale
     *        the locale for the display names
     * @return a {@code Map} containing all display names in
     *        {@code style} and {@code locale} and their
     *        field values, or {@code null} if no display names
     *        are defined for {@code field}
     * @exception IllegalArgumentException
     *        if {@code field} or {@code style} is invalid,
     *        or if this {@code Calendar} is non-lenient and any
     *        of the calendar fields have invalid values
     * @exception NullPointerException
     *        if {@code locale} is null
     * @since 1.6
     */
    public Map<String, Integer> getDisplayNames(int field, int style, Locale locale) {
        if (!checkDisplayNameParams(field, style, ALL_STYLES, NARROW_FORMAT, locale,
                                    ERA_MASK|MONTH_MASK|DAY_OF_WEEK_MASK|AM_PM_MASK)) {
            return null;
        }
        if (style == ALL_STYLES || isStandaloneStyle(style)) {
            return CalendarDataUtility.retrieveFieldValueNames(getCalendarType(), field, style, locale);
        }
        // SHORT, LONG, or NARROW
        return getDisplayNamesImpl(field, style, locale);
    }

    private Map<String,Integer> getDisplayNamesImpl(int field, int style, Locale locale) {
        DateFormatSymbols symbols = DateFormatSymbols.getInstance(locale);
        String[] strings = getFieldStrings(field, style, symbols);
        if (strings != null) {
            Map<String,Integer> names = new HashMap<>();
            for (int i = 0; i < strings.length; i++) {
                if (strings[i].length() == 0) {
                    continue;
                }
                names.put(strings[i], i);
            }
            return names;
        }
        return null;
    }

    boolean checkDisplayNameParams(int field, int style, int minStyle, int maxStyle,
                                   Locale locale, int fieldMask) {
        int baseStyle = getBaseStyle(style); // Ignore the standalone mask
        if (field < 0 || field >= fields.length ||
            baseStyle < minStyle || baseStyle > maxStyle) {
            throw new IllegalArgumentException();
        }
        if (locale == null) {
            throw new NullPointerException();
        }
        return isFieldSet(fieldMask, field);
    }

    private String[] getFieldStrings(int field, int style, DateFormatSymbols symbols) {
        int baseStyle = getBaseStyle(style); // ignore the standalone mask

        // DateFormatSymbols doesn't support any narrow names.
        if (baseStyle == NARROW_FORMAT) {
            return null;
        }

        String[] strings = null;
        switch (field) {
        case ERA:
            strings = symbols.getEras();
            break;

        case MONTH:
            strings = (baseStyle == LONG) ? symbols.getMonths() : symbols.getShortMonths();
            break;

        case DAY_OF_WEEK:
            strings = (baseStyle == LONG) ? symbols.getWeekdays() : symbols.getShortWeekdays();
            break;

        case AM_PM:
            strings = symbols.getAmPmStrings();
            break;
        }
        return strings;
    }

    /**
     * Fills in any unset fields in the calendar fields. First, the {@link
     * #computeTime()} method is called if the time value (millisecond offset
     * from the <a href="#Epoch">Epoch</a>) has not been calculated from
     * calendar field values. Then, the {@link #computeFields()} method is
     * called to calculate all calendar field values.
     * <p>
     * 填写日历字段中的任何未设置字段。首先,如果未从日历字段值计算时间值(距<a href="#Epoch">时代的毫秒偏移量),则调用{@link #computeTime()}方法。
     * 然后,调用{@link #computeFields()}方法计算所有日历字段值。
     * 
     */
    protected void complete()
    {
        if (!isTimeSet) {
            updateTime();
        }
        if (!areFieldsSet || !areAllFieldsSet) {
            computeFields(); // fills in unset fields
            areAllFieldsSet = areFieldsSet = true;
        }
    }

    /**
     * Returns whether the value of the specified calendar field has been set
     * externally by calling one of the setter methods rather than by the
     * internal time calculation.
     *
     * <p>
     *  通过调用其中一个setter方法而不是内部时间计算,返回指定的日历字段的值是否已在外部设置。
     * 
     * 
     * @return <code>true</code> if the field has been set externally,
     * <code>false</code> otherwise.
     * @exception IndexOutOfBoundsException if the specified
     *                <code>field</code> is out of range
     *               (<code>field &lt; 0 || field &gt;= FIELD_COUNT</code>).
     * @see #selectFields()
     * @see #setFieldsComputed(int)
     */
    final boolean isExternallySet(int field) {
        return stamp[field] >= MINIMUM_USER_STAMP;
    }

    /**
     * Returns a field mask (bit mask) indicating all calendar fields that
     * have the state of externally or internally set.
     *
     * <p>
     *  返回指示具有外部或内部状态的所有日历字段的字段掩码(位掩码)。
     * 
     * 
     * @return a bit mask indicating set state fields
     */
    final int getSetStateFields() {
        int mask = 0;
        for (int i = 0; i < fields.length; i++) {
            if (stamp[i] != UNSET) {
                mask |= 1 << i;
            }
        }
        return mask;
    }

    /**
     * Sets the state of the specified calendar fields to
     * <em>computed</em>. This state means that the specified calendar fields
     * have valid values that have been set by internal time calculation
     * rather than by calling one of the setter methods.
     *
     * <p>
     *  将指定的日历字段的状态设置为<em>已计算</em>。此状态表示指定的日历字段具有由内部时间计算设置的有效值,而不是通过调用某个setter方法。
     * 
     * 
     * @param fieldMask the field to be marked as computed.
     * @exception IndexOutOfBoundsException if the specified
     *                <code>field</code> is out of range
     *               (<code>field &lt; 0 || field &gt;= FIELD_COUNT</code>).
     * @see #isExternallySet(int)
     * @see #selectFields()
     */
    final void setFieldsComputed(int fieldMask) {
        if (fieldMask == ALL_FIELDS) {
            for (int i = 0; i < fields.length; i++) {
                stamp[i] = COMPUTED;
                isSet[i] = true;
            }
            areFieldsSet = areAllFieldsSet = true;
        } else {
            for (int i = 0; i < fields.length; i++) {
                if ((fieldMask & 1) == 1) {
                    stamp[i] = COMPUTED;
                    isSet[i] = true;
                } else {
                    if (areAllFieldsSet && !isSet[i]) {
                        areAllFieldsSet = false;
                    }
                }
                fieldMask >>>= 1;
            }
        }
    }

    /**
     * Sets the state of the calendar fields that are <em>not</em> specified
     * by <code>fieldMask</code> to <em>unset</em>. If <code>fieldMask</code>
     * specifies all the calendar fields, then the state of this
     * <code>Calendar</code> becomes that all the calendar fields are in sync
     * with the time value (millisecond offset from the Epoch).
     *
     * <p>
     *  将<code> fieldMask </code>指定的<n>不是</em>的日历字段的状态设置为<em>取消设置</em>。
     * 如果<code> fieldMask </code>指定所有日历字段,则此<code> Calendar </code>的状态变为所有日历字段与时间值(从Epoch偏移的毫秒)同步。
     * 
     * 
     * @param fieldMask the field mask indicating which calendar fields are in
     * sync with the time value.
     * @exception IndexOutOfBoundsException if the specified
     *                <code>field</code> is out of range
     *               (<code>field &lt; 0 || field &gt;= FIELD_COUNT</code>).
     * @see #isExternallySet(int)
     * @see #selectFields()
     */
    final void setFieldsNormalized(int fieldMask) {
        if (fieldMask != ALL_FIELDS) {
            for (int i = 0; i < fields.length; i++) {
                if ((fieldMask & 1) == 0) {
                    stamp[i] = fields[i] = 0; // UNSET == 0
                    isSet[i] = false;
                }
                fieldMask >>= 1;
            }
        }

        // Some or all of the fields are in sync with the
        // milliseconds, but the stamp values are not normalized yet.
        areFieldsSet = true;
        areAllFieldsSet = false;
    }

    /**
     * Returns whether the calendar fields are partially in sync with the time
     * value or fully in sync but not stamp values are not normalized yet.
     * <p>
     *  返回日历字段是否与时间值部分同步或完全同步,但不会标记邮票值。
     * 
     */
    final boolean isPartiallyNormalized() {
        return areFieldsSet && !areAllFieldsSet;
    }

    /**
     * Returns whether the calendar fields are fully in sync with the time
     * value.
     * <p>
     *  返回日历字段是否与时间值完全同步。
     * 
     */
    final boolean isFullyNormalized() {
        return areFieldsSet && areAllFieldsSet;
    }

    /**
     * Marks this Calendar as not sync'd.
     * <p>
     *  将此日历标记为未同步。
     * 
     */
    final void setUnnormalized() {
        areFieldsSet = areAllFieldsSet = false;
    }

    /**
     * Returns whether the specified <code>field</code> is on in the
     * <code>fieldMask</code>.
     * <p>
     * 返回<code> fieldMask </code>中指定的<code>字段</code>是否为on。
     * 
     */
    static boolean isFieldSet(int fieldMask, int field) {
        return (fieldMask & (1 << field)) != 0;
    }

    /**
     * Returns a field mask indicating which calendar field values
     * to be used to calculate the time value. The calendar fields are
     * returned as a bit mask, each bit of which corresponds to a field, i.e.,
     * the mask value of <code>field</code> is <code>(1 &lt;&lt;
     * field)</code>. For example, 0x26 represents the <code>YEAR</code>,
     * <code>MONTH</code>, and <code>DAY_OF_MONTH</code> fields (i.e., 0x26 is
     * equal to
     * <code>(1&lt;&lt;YEAR)|(1&lt;&lt;MONTH)|(1&lt;&lt;DAY_OF_MONTH))</code>.
     *
     * <p>This method supports the calendar fields resolution as described in
     * the class description. If the bit mask for a given field is on and its
     * field has not been set (i.e., <code>isSet(field)</code> is
     * <code>false</code>), then the default value of the field has to be
     * used, which case means that the field has been selected because the
     * selected combination involves the field.
     *
     * <p>
     *  返回一个字段掩码,指示要用于计算时间值的日历字段值。日历字段作为位掩码返回,其中的每个位对应于一个字段,即<code>字段</code>的掩码值是<code>(1 <<field)</code>。
     * 例如,0x26表示<code> YEAR </code>,<code> MONTH </code>和<code> DAY_OF_MONTH </code>字段(即0x26等于<code>(1 < )|(1 <&lt; MONTH)|(1 <DAY_OF_MONTH))</code>
     * 。
     *  返回一个字段掩码,指示要用于计算时间值的日历字段值。日历字段作为位掩码返回,其中的每个位对应于一个字段,即<code>字段</code>的掩码值是<code>(1 <<field)</code>。
     * 
     *  <p>此方法支持类描述中描述的日历字段分辨率。
     * 如果给定字段的位掩码是打开的并且其字段尚未设置(即,<code> isSet(field)</code>是<code> false </code>),则字段的默认值,这种情况意味着已经选择了字段,因为所
     * 选择的组合涉及字段。
     *  <p>此方法支持类描述中描述的日历字段分辨率。
     * 
     * 
     * @return a bit mask of selected fields
     * @see #isExternallySet(int)
     */
    final int selectFields() {
        // This implementation has been taken from the GregorianCalendar class.

        // The YEAR field must always be used regardless of its SET
        // state because YEAR is a mandatory field to determine the date
        // and the default value (EPOCH_YEAR) may change through the
        // normalization process.
        int fieldMask = YEAR_MASK;

        if (stamp[ERA] != UNSET) {
            fieldMask |= ERA_MASK;
        }
        // Find the most recent group of fields specifying the day within
        // the year.  These may be any of the following combinations:
        //   MONTH + DAY_OF_MONTH
        //   MONTH + WEEK_OF_MONTH + DAY_OF_WEEK
        //   MONTH + DAY_OF_WEEK_IN_MONTH + DAY_OF_WEEK
        //   DAY_OF_YEAR
        //   WEEK_OF_YEAR + DAY_OF_WEEK
        // We look for the most recent of the fields in each group to determine
        // the age of the group.  For groups involving a week-related field such
        // as WEEK_OF_MONTH, DAY_OF_WEEK_IN_MONTH, or WEEK_OF_YEAR, both the
        // week-related field and the DAY_OF_WEEK must be set for the group as a
        // whole to be considered.  (See bug 4153860 - liu 7/24/98.)
        int dowStamp = stamp[DAY_OF_WEEK];
        int monthStamp = stamp[MONTH];
        int domStamp = stamp[DAY_OF_MONTH];
        int womStamp = aggregateStamp(stamp[WEEK_OF_MONTH], dowStamp);
        int dowimStamp = aggregateStamp(stamp[DAY_OF_WEEK_IN_MONTH], dowStamp);
        int doyStamp = stamp[DAY_OF_YEAR];
        int woyStamp = aggregateStamp(stamp[WEEK_OF_YEAR], dowStamp);

        int bestStamp = domStamp;
        if (womStamp > bestStamp) {
            bestStamp = womStamp;
        }
        if (dowimStamp > bestStamp) {
            bestStamp = dowimStamp;
        }
        if (doyStamp > bestStamp) {
            bestStamp = doyStamp;
        }
        if (woyStamp > bestStamp) {
            bestStamp = woyStamp;
        }

        /* No complete combination exists.  Look for WEEK_OF_MONTH,
         * DAY_OF_WEEK_IN_MONTH, or WEEK_OF_YEAR alone.  Treat DAY_OF_WEEK alone
         * as DAY_OF_WEEK_IN_MONTH.
         * <p>
         *  DAY_OF_WEEK_IN_MONTH,或WEEK_OF_YEAR。将DAY_OF_WEEK单独视为DAY_OF_WEEK_IN_MONTH。
         * 
         */
        if (bestStamp == UNSET) {
            womStamp = stamp[WEEK_OF_MONTH];
            dowimStamp = Math.max(stamp[DAY_OF_WEEK_IN_MONTH], dowStamp);
            woyStamp = stamp[WEEK_OF_YEAR];
            bestStamp = Math.max(Math.max(womStamp, dowimStamp), woyStamp);

            /* Treat MONTH alone or no fields at all as DAY_OF_MONTH.  This may
             * result in bestStamp = domStamp = UNSET if no fields are set,
             * which indicates DAY_OF_MONTH.
             * <p>
             *  如果没有设置字段,则结果为bestStamp = domStamp = UNSET,表示DAY_OF_MONTH。
             * 
             */
            if (bestStamp == UNSET) {
                bestStamp = domStamp = monthStamp;
            }
        }

        if (bestStamp == domStamp ||
           (bestStamp == womStamp && stamp[WEEK_OF_MONTH] >= stamp[WEEK_OF_YEAR]) ||
           (bestStamp == dowimStamp && stamp[DAY_OF_WEEK_IN_MONTH] >= stamp[WEEK_OF_YEAR])) {
            fieldMask |= MONTH_MASK;
            if (bestStamp == domStamp) {
                fieldMask |= DAY_OF_MONTH_MASK;
            } else {
                assert (bestStamp == womStamp || bestStamp == dowimStamp);
                if (dowStamp != UNSET) {
                    fieldMask |= DAY_OF_WEEK_MASK;
                }
                if (womStamp == dowimStamp) {
                    // When they are equal, give the priority to
                    // WEEK_OF_MONTH for compatibility.
                    if (stamp[WEEK_OF_MONTH] >= stamp[DAY_OF_WEEK_IN_MONTH]) {
                        fieldMask |= WEEK_OF_MONTH_MASK;
                    } else {
                        fieldMask |= DAY_OF_WEEK_IN_MONTH_MASK;
                    }
                } else {
                    if (bestStamp == womStamp) {
                        fieldMask |= WEEK_OF_MONTH_MASK;
                    } else {
                        assert (bestStamp == dowimStamp);
                        if (stamp[DAY_OF_WEEK_IN_MONTH] != UNSET) {
                            fieldMask |= DAY_OF_WEEK_IN_MONTH_MASK;
                        }
                    }
                }
            }
        } else {
            assert (bestStamp == doyStamp || bestStamp == woyStamp ||
                    bestStamp == UNSET);
            if (bestStamp == doyStamp) {
                fieldMask |= DAY_OF_YEAR_MASK;
            } else {
                assert (bestStamp == woyStamp);
                if (dowStamp != UNSET) {
                    fieldMask |= DAY_OF_WEEK_MASK;
                }
                fieldMask |= WEEK_OF_YEAR_MASK;
            }
        }

        // Find the best set of fields specifying the time of day.  There
        // are only two possibilities here; the HOUR_OF_DAY or the
        // AM_PM and the HOUR.
        int hourOfDayStamp = stamp[HOUR_OF_DAY];
        int hourStamp = aggregateStamp(stamp[HOUR], stamp[AM_PM]);
        bestStamp = (hourStamp > hourOfDayStamp) ? hourStamp : hourOfDayStamp;

        // if bestStamp is still UNSET, then take HOUR or AM_PM. (See 4846659)
        if (bestStamp == UNSET) {
            bestStamp = Math.max(stamp[HOUR], stamp[AM_PM]);
        }

        // Hours
        if (bestStamp != UNSET) {
            if (bestStamp == hourOfDayStamp) {
                fieldMask |= HOUR_OF_DAY_MASK;
            } else {
                fieldMask |= HOUR_MASK;
                if (stamp[AM_PM] != UNSET) {
                    fieldMask |= AM_PM_MASK;
                }
            }
        }
        if (stamp[MINUTE] != UNSET) {
            fieldMask |= MINUTE_MASK;
        }
        if (stamp[SECOND] != UNSET) {
            fieldMask |= SECOND_MASK;
        }
        if (stamp[MILLISECOND] != UNSET) {
            fieldMask |= MILLISECOND_MASK;
        }
        if (stamp[ZONE_OFFSET] >= MINIMUM_USER_STAMP) {
                fieldMask |= ZONE_OFFSET_MASK;
        }
        if (stamp[DST_OFFSET] >= MINIMUM_USER_STAMP) {
            fieldMask |= DST_OFFSET_MASK;
        }

        return fieldMask;
    }

    int getBaseStyle(int style) {
        return style & ~STANDALONE_MASK;
    }

    boolean isStandaloneStyle(int style) {
        return (style & STANDALONE_MASK) != 0;
    }

    boolean isNarrowStyle(int style) {
        return style == NARROW_FORMAT || style == NARROW_STANDALONE;
    }

    /**
     * Returns the pseudo-time-stamp for two fields, given their
     * individual pseudo-time-stamps.  If either of the fields
     * is unset, then the aggregate is unset.  Otherwise, the
     * aggregate is the later of the two stamps.
     * <p>
     *  给定它们各自的伪时间戳,返回两个字段的伪时间戳。如果任一字段未设置,则将取消设置聚合。否则,聚合是两个邮票中的较晚者。
     * 
     */
    private static int aggregateStamp(int stamp_a, int stamp_b) {
        if (stamp_a == UNSET || stamp_b == UNSET) {
            return UNSET;
        }
        return (stamp_a > stamp_b) ? stamp_a : stamp_b;
    }

    /**
     * Returns an unmodifiable {@code Set} containing all calendar types
     * supported by {@code Calendar} in the runtime environment. The available
     * calendar types can be used for the <a
     * href="Locale.html#def_locale_extension">Unicode locale extensions</a>.
     * The {@code Set} returned contains at least {@code "gregory"}. The
     * calendar types don't include aliases, such as {@code "gregorian"} for
     * {@code "gregory"}.
     *
     * <p>
     * 在运行时环境中返回不可修改的{@code Set},其中包含{@code Calendar}支持的所有日历类型。
     * 可用的日历类型可用于<a href="Locale.html#def_locale_extension"> Unicode语言区域扩展程序</a>。
     * 返回的{@code Set}至少包含{@code"gregory"}。日历类型不包含别名,例如{@code"gregory"}的{@code"gregorian"}。
     * 
     * 
     * @return an unmodifiable {@code Set} containing all available calendar types
     * @since 1.8
     * @see #getCalendarType()
     * @see Calendar.Builder#setCalendarType(String)
     * @see Locale#getUnicodeLocaleType(String)
     */
    public static Set<String> getAvailableCalendarTypes() {
        return AvailableCalendarTypes.SET;
    }

    private static class AvailableCalendarTypes {
        private static final Set<String> SET;
        static {
            Set<String> set = new HashSet<>(3);
            set.add("gregory");
            set.add("buddhist");
            set.add("japanese");
            SET = Collections.unmodifiableSet(set);
        }
        private AvailableCalendarTypes() {
        }
    }

    /**
     * Returns the calendar type of this {@code Calendar}. Calendar types are
     * defined by the <em>Unicode Locale Data Markup Language (LDML)</em>
     * specification.
     *
     * <p>The default implementation of this method returns the class name of
     * this {@code Calendar} instance. Any subclasses that implement
     * LDML-defined calendar systems should override this method to return
     * appropriate calendar types.
     *
     * <p>
     *  返回此{@code Calendar}的日历类型。日历类型由<em> Unicode区域设置数据标记语言(LDML)</em>规范定义。
     * 
     *  <p>此方法的默认实现返回此{@code Calendar}实例的类名。实现LDML定义的日历系统的任何子类都应该覆盖此方法以返回相应的日历类型。
     * 
     * 
     * @return the LDML-defined calendar type or the class name of this
     *         {@code Calendar} instance
     * @since 1.8
     * @see <a href="Locale.html#def_extensions">Locale extensions</a>
     * @see Locale.Builder#setLocale(Locale)
     * @see Locale.Builder#setUnicodeLocaleKeyword(String, String)
     */
    public String getCalendarType() {
        return this.getClass().getName();
    }

    /**
     * Compares this <code>Calendar</code> to the specified
     * <code>Object</code>.  The result is <code>true</code> if and only if
     * the argument is a <code>Calendar</code> object of the same calendar
     * system that represents the same time value (millisecond offset from the
     * <a href="#Epoch">Epoch</a>) under the same
     * <code>Calendar</code> parameters as this object.
     *
     * <p>The <code>Calendar</code> parameters are the values represented
     * by the <code>isLenient</code>, <code>getFirstDayOfWeek</code>,
     * <code>getMinimalDaysInFirstWeek</code> and <code>getTimeZone</code>
     * methods. If there is any difference in those parameters
     * between the two <code>Calendar</code>s, this method returns
     * <code>false</code>.
     *
     * <p>Use the {@link #compareTo(Calendar) compareTo} method to
     * compare only the time values.
     *
     * <p>
     *  将此<code>日历</code>与指定的<code>对象</code>进行比较。
     * 如果且仅当参数是相同日历系统的<code> Calendar </code>对象时,结果是<code> true </code>,代表相同的时间值(毫秒,偏离<a href ="# Epoch"> Ep
     * och </a>)在与此对象相同的<code> Calendar </code>参数下。
     *  将此<code>日历</code>与指定的<code>对象</code>进行比较。
     * 
     *  <p> <code> </code>参数是由<code> isLenient </code>,<code> getFirstDayOfWeek </code>,<code> getMinimalDay
     * sInFirstWeek </code>和<code> getTimeZone </code>方法。
     * 如果两个<code> Calendar </code>之间的参数有任何差异,则此方法返回<code> false </code>。
     * 
     * <p>使用{@link #compareTo(Calendar)compareTo}方法仅比较时间值。
     * 
     * 
     * @param obj the object to compare with.
     * @return <code>true</code> if this object is equal to <code>obj</code>;
     * <code>false</code> otherwise.
     */
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        try {
            Calendar that = (Calendar)obj;
            return compareTo(getMillisOf(that)) == 0 &&
                lenient == that.lenient &&
                firstDayOfWeek == that.firstDayOfWeek &&
                minimalDaysInFirstWeek == that.minimalDaysInFirstWeek &&
                zone.equals(that.zone);
        } catch (Exception e) {
            // Note: GregorianCalendar.computeTime throws
            // IllegalArgumentException if the ERA value is invalid
            // even it's in lenient mode.
        }
        return false;
    }

    /**
     * Returns a hash code for this calendar.
     *
     * <p>
     *  返回此日历的哈希码。
     * 
     * 
     * @return a hash code value for this object.
     * @since 1.2
     */
    @Override
    public int hashCode() {
        // 'otheritems' represents the hash code for the previous versions.
        int otheritems = (lenient ? 1 : 0)
            | (firstDayOfWeek << 1)
            | (minimalDaysInFirstWeek << 4)
            | (zone.hashCode() << 7);
        long t = getMillisOf(this);
        return (int) t ^ (int)(t >> 32) ^ otheritems;
    }

    /**
     * Returns whether this <code>Calendar</code> represents a time
     * before the time represented by the specified
     * <code>Object</code>. This method is equivalent to:
     * <pre>{@code
     *         compareTo(when) < 0
     * }</pre>
     * if and only if <code>when</code> is a <code>Calendar</code>
     * instance. Otherwise, the method returns <code>false</code>.
     *
     * <p>
     *  返回此<code> Calendar </code>是否表示指定<code> Object </code>表示的时间之前的时间。
     * 此方法等效于：<pre> {@ code compareTo(when)<0} </pre>当且仅当<code>当</code>是<code> Calendar </code>实例时。
     * 否则,该方法返回<code> false </code>。
     * 
     * 
     * @param when the <code>Object</code> to be compared
     * @return <code>true</code> if the time of this
     * <code>Calendar</code> is before the time represented by
     * <code>when</code>; <code>false</code> otherwise.
     * @see     #compareTo(Calendar)
     */
    public boolean before(Object when) {
        return when instanceof Calendar
            && compareTo((Calendar)when) < 0;
    }

    /**
     * Returns whether this <code>Calendar</code> represents a time
     * after the time represented by the specified
     * <code>Object</code>. This method is equivalent to:
     * <pre>{@code
     *         compareTo(when) > 0
     * }</pre>
     * if and only if <code>when</code> is a <code>Calendar</code>
     * instance. Otherwise, the method returns <code>false</code>.
     *
     * <p>
     *  返回此<代码>日历</code>是否表示指定<code> Object </code>表示的时间后的时间。
     * 此方法等效于：<pre> {@ code compareTo(when)> 0} </pre>当且仅当<code>当</code>是<code> Calendar </code>实例时。
     * 否则,该方法返回<code> false </code>。
     * 
     * 
     * @param when the <code>Object</code> to be compared
     * @return <code>true</code> if the time of this <code>Calendar</code> is
     * after the time represented by <code>when</code>; <code>false</code>
     * otherwise.
     * @see     #compareTo(Calendar)
     */
    public boolean after(Object when) {
        return when instanceof Calendar
            && compareTo((Calendar)when) > 0;
    }

    /**
     * Compares the time values (millisecond offsets from the <a
     * href="#Epoch">Epoch</a>) represented by two
     * <code>Calendar</code> objects.
     *
     * <p>
     *  比较由两个<code> Calendar </code>对象表示的时间值(与<a href="#Epoch">时代</a>的毫秒偏移量)。
     * 
     * 
     * @param anotherCalendar the <code>Calendar</code> to be compared.
     * @return the value <code>0</code> if the time represented by the argument
     * is equal to the time represented by this <code>Calendar</code>; a value
     * less than <code>0</code> if the time of this <code>Calendar</code> is
     * before the time represented by the argument; and a value greater than
     * <code>0</code> if the time of this <code>Calendar</code> is after the
     * time represented by the argument.
     * @exception NullPointerException if the specified <code>Calendar</code> is
     *            <code>null</code>.
     * @exception IllegalArgumentException if the time value of the
     * specified <code>Calendar</code> object can't be obtained due to
     * any invalid calendar values.
     * @since   1.5
     */
    @Override
    public int compareTo(Calendar anotherCalendar) {
        return compareTo(getMillisOf(anotherCalendar));
    }

    /**
     * Adds or subtracts the specified amount of time to the given calendar field,
     * based on the calendar's rules. For example, to subtract 5 days from
     * the current time of the calendar, you can achieve it by calling:
     * <p><code>add(Calendar.DAY_OF_MONTH, -5)</code>.
     *
     * <p>
     *  基于日历的规则,将指定的时间量添加或减少到给定的日历字段。
     * 例如,要从日历的当前时间减去5天,您可以通过调用<p> <code> add(Calendar.DAY_OF_MONTH,-5)</code>来实现。
     * 
     * 
     * @param field the calendar field.
     * @param amount the amount of date or time to be added to the field.
     * @see #roll(int,int)
     * @see #set(int,int)
     */
    abstract public void add(int field, int amount);

    /**
     * Adds or subtracts (up/down) a single unit of time on the given time
     * field without changing larger fields. For example, to roll the current
     * date up by one day, you can achieve it by calling:
     * <p>roll(Calendar.DATE, true).
     * When rolling on the year or Calendar.YEAR field, it will roll the year
     * value in the range between 1 and the value returned by calling
     * <code>getMaximum(Calendar.YEAR)</code>.
     * When rolling on the month or Calendar.MONTH field, other fields like
     * date might conflict and, need to be changed. For instance,
     * rolling the month on the date 01/31/96 will result in 02/29/96.
     * When rolling on the hour-in-day or Calendar.HOUR_OF_DAY field, it will
     * roll the hour value in the range between 0 and 23, which is zero-based.
     *
     * <p>
     * 在给定时间字段上添加或减少(上/下)单个时间单位,而不更改较大字段。例如,要将当前日期向前滚动一天,您可以通过调用以下方式实现：<p> roll(Calendar.DATE,true)。
     * 当在年或Calendar.YEAR字段上滚动时,它将滚动年值,范围在1和调用<code> getMaximum(Calendar.YEAR)</code>返回的值之间。
     * 当在月或Calendar.MONTH字段上滚动时,其他字段(如日期)可能会冲突,需要更改。例如,滚动日期01/31/96的月份将导致02/29/96。
     * 在日历或Calendar.HOUR_OF_DAY字段中滚动时,它将滚动0到23之间的范围内的小时值,这是零为基础的。
     * 
     * 
     * @param field the time field.
     * @param up indicates if the value of the specified time field is to be
     * rolled up or rolled down. Use true if rolling up, false otherwise.
     * @see Calendar#add(int,int)
     * @see Calendar#set(int,int)
     */
    abstract public void roll(int field, boolean up);

    /**
     * Adds the specified (signed) amount to the specified calendar field
     * without changing larger fields.  A negative amount means to roll
     * down.
     *
     * <p>NOTE:  This default implementation on <code>Calendar</code> just repeatedly calls the
     * version of {@link #roll(int,boolean) roll()} that rolls by one unit.  This may not
     * always do the right thing.  For example, if the <code>DAY_OF_MONTH</code> field is 31,
     * rolling through February will leave it set to 28.  The <code>GregorianCalendar</code>
     * version of this function takes care of this problem.  Other subclasses
     * should also provide overrides of this function that do the right thing.
     *
     * <p>
     *  将指定的(带符号)金额添加到指定的日历字段,而不更改较大的字段。负数意味着下滚。
     * 
     *  <p>注意：<code> Calendar </code>上的此默认实现仅重复调用滚动一个单位的{@link #roll(int,boolean)roll()}版本。这可能不总是正确的事情。
     * 例如,如果<code> DAY_OF_MONTH </code>字段为31,滚动到2月将使其设置为28.此函数的<code> GregorianCalendar </code>版本处理此问题。
     * 其他子类也应该提供这个函数的覆盖,做正确的事情。
     * 
     * 
     * @param field the calendar field.
     * @param amount the signed amount to add to the calendar <code>field</code>.
     * @since 1.2
     * @see #roll(int,boolean)
     * @see #add(int,int)
     * @see #set(int,int)
     */
    public void roll(int field, int amount)
    {
        while (amount > 0) {
            roll(field, true);
            amount--;
        }
        while (amount < 0) {
            roll(field, false);
            amount++;
        }
    }

    /**
     * Sets the time zone with the given time zone value.
     *
     * <p>
     *  设置具有给定时区值的时区。
     * 
     * 
     * @param value the given time zone.
     */
    public void setTimeZone(TimeZone value)
    {
        zone = value;
        sharedZone = false;
        /* Recompute the fields from the time using the new zone.  This also
         * works if isTimeSet is false (after a call to set()).  In that case
         * the time will be computed from the fields using the new zone, then
         * the fields will get recomputed from that.  Consider the sequence of
         * calls: cal.setTimeZone(EST); cal.set(HOUR, 1); cal.setTimeZone(PST).
         * Is cal set to 1 o'clock EST or 1 o'clock PST?  Answer: PST.  More
         * generally, a call to setTimeZone() affects calls to set() BEFORE AND
         * AFTER it up to the next call to complete().
         * <p>
         * 如果isTimeSet为false(调用set()后)。在这种情况下,将使用新区域的字段计算时间,然后将从那里重新计算字段。
         * 考虑调用的顺序：cal.setTimeZone(EST); cal.set(HOUR,1); cal.setTimeZone(PST)。是cal设置为1点EST还是1点PST?答：PST。
         * 更一般地,调用setTimeZone()会影响set()之前和之后的调用,直到下一次调用complete()。
         * 
         */
        areAllFieldsSet = areFieldsSet = false;
    }

    /**
     * Gets the time zone.
     *
     * <p>
     *  获取时区。
     * 
     * 
     * @return the time zone object associated with this calendar.
     */
    public TimeZone getTimeZone()
    {
        // If the TimeZone object is shared by other Calendar instances, then
        // create a clone.
        if (sharedZone) {
            zone = (TimeZone) zone.clone();
            sharedZone = false;
        }
        return zone;
    }

    /**
     * Returns the time zone (without cloning).
     * <p>
     *  返回时区(无克隆)。
     * 
     */
    TimeZone getZone() {
        return zone;
    }

    /**
     * Sets the sharedZone flag to <code>shared</code>.
     * <p>
     *  将sharedZone标志设置为<code> shared </code>。
     * 
     */
    void setZoneShared(boolean shared) {
        sharedZone = shared;
    }

    /**
     * Specifies whether or not date/time interpretation is to be lenient.  With
     * lenient interpretation, a date such as "February 942, 1996" will be
     * treated as being equivalent to the 941st day after February 1, 1996.
     * With strict (non-lenient) interpretation, such dates will cause an exception to be
     * thrown. The default is lenient.
     *
     * <p>
     *  指定日期/时间解释是否宽松。以宽泛的解释,诸如"1996年2月942年"这样的日期将被视为相当于1996年2月1日后的第941天。以严格(非宽容)解释,这种日期将导致抛出异常。
     * 默认值为lenient。
     * 
     * 
     * @param lenient <code>true</code> if the lenient mode is to be turned
     * on; <code>false</code> if it is to be turned off.
     * @see #isLenient()
     * @see java.text.DateFormat#setLenient
     */
    public void setLenient(boolean lenient)
    {
        this.lenient = lenient;
    }

    /**
     * Tells whether date/time interpretation is to be lenient.
     *
     * <p>
     *  说明日期/时间解释是否宽松。
     * 
     * 
     * @return <code>true</code> if the interpretation mode of this calendar is lenient;
     * <code>false</code> otherwise.
     * @see #setLenient(boolean)
     */
    public boolean isLenient()
    {
        return lenient;
    }

    /**
     * Sets what the first day of the week is; e.g., <code>SUNDAY</code> in the U.S.,
     * <code>MONDAY</code> in France.
     *
     * <p>
     *  设置一周的第一天是什么;例如,在美国的<code> SUNDAY </code>,在法国的<code> MONDAY </code>。
     * 
     * 
     * @param value the given first day of the week.
     * @see #getFirstDayOfWeek()
     * @see #getMinimalDaysInFirstWeek()
     */
    public void setFirstDayOfWeek(int value)
    {
        if (firstDayOfWeek == value) {
            return;
        }
        firstDayOfWeek = value;
        invalidateWeekFields();
    }

    /**
     * Gets what the first day of the week is; e.g., <code>SUNDAY</code> in the U.S.,
     * <code>MONDAY</code> in France.
     *
     * <p>
     *  获取一周的第一天是什么;例如,在美国的<code> SUNDAY </code>,在法国的<code> MONDAY </code>。
     * 
     * 
     * @return the first day of the week.
     * @see #setFirstDayOfWeek(int)
     * @see #getMinimalDaysInFirstWeek()
     */
    public int getFirstDayOfWeek()
    {
        return firstDayOfWeek;
    }

    /**
     * Sets what the minimal days required in the first week of the year are;
     * For example, if the first week is defined as one that contains the first
     * day of the first month of a year, call this method with value 1. If it
     * must be a full week, use value 7.
     *
     * <p>
     *  设置一年中第一周所需的最少天数;例如,如果第一周定义为包含一年中第一个月的第一天的一周,则调用此值为1的方法。如果第一周必须是整周,请使用值7。
     * 
     * 
     * @param value the given minimal days required in the first week
     * of the year.
     * @see #getMinimalDaysInFirstWeek()
     */
    public void setMinimalDaysInFirstWeek(int value)
    {
        if (minimalDaysInFirstWeek == value) {
            return;
        }
        minimalDaysInFirstWeek = value;
        invalidateWeekFields();
    }

    /**
     * Gets what the minimal days required in the first week of the year are;
     * e.g., if the first week is defined as one that contains the first day
     * of the first month of a year, this method returns 1. If
     * the minimal days required must be a full week, this method
     * returns 7.
     *
     * <p>
     * 获得一年中第一周所需的最少天数;例如,如果第一周被定义为包含一年的第一个月的第一天的一周,则该方法返回1.如果所需的最小天数必须是整周,则该方法返回7。
     * 
     * 
     * @return the minimal days required in the first week of the year.
     * @see #setMinimalDaysInFirstWeek(int)
     */
    public int getMinimalDaysInFirstWeek()
    {
        return minimalDaysInFirstWeek;
    }

    /**
     * Returns whether this {@code Calendar} supports week dates.
     *
     * <p>The default implementation of this method returns {@code false}.
     *
     * <p>
     *  返回此{@code Calendar}是否支持周日期。
     * 
     *  <p>此方法的默认实现返回{@code false}。
     * 
     * 
     * @return {@code true} if this {@code Calendar} supports week dates;
     *         {@code false} otherwise.
     * @see #getWeekYear()
     * @see #setWeekDate(int,int,int)
     * @see #getWeeksInWeekYear()
     * @since 1.7
     */
    public boolean isWeekDateSupported() {
        return false;
    }

    /**
     * Returns the week year represented by this {@code Calendar}. The
     * week year is in sync with the week cycle. The {@linkplain
     * #getFirstDayOfWeek() first day of the first week} is the first
     * day of the week year.
     *
     * <p>The default implementation of this method throws an
     * {@link UnsupportedOperationException}.
     *
     * <p>
     *  返回此{@code Calendar}所代表的周年。周年与周周期同步。 {@linkplain #getFirstDayOfWeek()第一周的第一天}是星期年份的第一天。
     * 
     *  <p>此方法的默认实现会抛出{@link UnsupportedOperationException}。
     * 
     * 
     * @return the week year of this {@code Calendar}
     * @exception UnsupportedOperationException
     *            if any week year numbering isn't supported
     *            in this {@code Calendar}.
     * @see #isWeekDateSupported()
     * @see #getFirstDayOfWeek()
     * @see #getMinimalDaysInFirstWeek()
     * @since 1.7
     */
    public int getWeekYear() {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the date of this {@code Calendar} with the the given date
     * specifiers - week year, week of year, and day of week.
     *
     * <p>Unlike the {@code set} method, all of the calendar fields
     * and {@code time} values are calculated upon return.
     *
     * <p>If {@code weekOfYear} is out of the valid week-of-year range
     * in {@code weekYear}, the {@code weekYear} and {@code
     * weekOfYear} values are adjusted in lenient mode, or an {@code
     * IllegalArgumentException} is thrown in non-lenient mode.
     *
     * <p>The default implementation of this method throws an
     * {@code UnsupportedOperationException}.
     *
     * <p>
     *  使用给定的日期说明符(周年,周年和星期几)设置此{@code日历}的日期。
     * 
     *  <p>与{@code set}方法不同,所有日历字段和{@code time}值都会在返回时计算。
     * 
     *  <p>如果{@code weekOfYear}在{@code weekYear}的有效星期范围之外,{@code weekYear}和{@code weekOfYear}的值会以宽松模式调整,或者是{@code weekYear}
     * 代码IllegalArgumentException}在非宽松模式中抛出。
     * 
     *  <p>此方法的默认实现会抛出{@code UnsupportedOperationException}。
     * 
     * 
     * @param weekYear   the week year
     * @param weekOfYear the week number based on {@code weekYear}
     * @param dayOfWeek  the day of week value: one of the constants
     *                   for the {@link #DAY_OF_WEEK} field: {@link
     *                   #SUNDAY}, ..., {@link #SATURDAY}.
     * @exception IllegalArgumentException
     *            if any of the given date specifiers is invalid
     *            or any of the calendar fields are inconsistent
     *            with the given date specifiers in non-lenient mode
     * @exception UnsupportedOperationException
     *            if any week year numbering isn't supported in this
     *            {@code Calendar}.
     * @see #isWeekDateSupported()
     * @see #getFirstDayOfWeek()
     * @see #getMinimalDaysInFirstWeek()
     * @since 1.7
     */
    public void setWeekDate(int weekYear, int weekOfYear, int dayOfWeek) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the number of weeks in the week year represented by this
     * {@code Calendar}.
     *
     * <p>The default implementation of this method throws an
     * {@code UnsupportedOperationException}.
     *
     * <p>
     *  返回此{@code Calendar}所代表的星期年份的周数。
     * 
     *  <p>此方法的默认实现会抛出{@code UnsupportedOperationException}。
     * 
     * 
     * @return the number of weeks in the week year.
     * @exception UnsupportedOperationException
     *            if any week year numbering isn't supported in this
     *            {@code Calendar}.
     * @see #WEEK_OF_YEAR
     * @see #isWeekDateSupported()
     * @see #getWeekYear()
     * @see #getActualMaximum(int)
     * @since 1.7
     */
    public int getWeeksInWeekYear() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the minimum value for the given calendar field of this
     * <code>Calendar</code> instance. The minimum value is defined as
     * the smallest value returned by the {@link #get(int) get} method
     * for any possible time value.  The minimum value depends on
     * calendar system specific parameters of the instance.
     *
     * <p>
     * 返回此<code> Calendar </code>实例的给定日历字段的最小值。最小值被定义为{@link #get(int)get}方法为任何可能的时间值返回的最小值。
     * 最小值取决于实例的日历系统特定参数。
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
    abstract public int getMinimum(int field);

    /**
     * Returns the maximum value for the given calendar field of this
     * <code>Calendar</code> instance. The maximum value is defined as
     * the largest value returned by the {@link #get(int) get} method
     * for any possible time value. The maximum value depends on
     * calendar system specific parameters of the instance.
     *
     * <p>
     *  返回此<code> Calendar </code>实例的给定日历字段的最大值。最大值定义为对于任何可能的时间值,由{@link #get(int)get}方法返回的最大值。
     * 最大值取决于实例的日历系统特定参数。
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
    abstract public int getMaximum(int field);

    /**
     * Returns the highest minimum value for the given calendar field
     * of this <code>Calendar</code> instance. The highest minimum
     * value is defined as the largest value returned by {@link
     * #getActualMinimum(int)} for any possible time value. The
     * greatest minimum value depends on calendar system specific
     * parameters of the instance.
     *
     * <p>
     *  返回此<code> Calendar </code>实例的给定日历字段的最大最小值。
     * 对于任何可能的时间值,最小的最小值被定义为{@link #getActualMinimum(int)}返回的最大值。最大最小值取决于实例的日历系统特定参数。
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
    abstract public int getGreatestMinimum(int field);

    /**
     * Returns the lowest maximum value for the given calendar field
     * of this <code>Calendar</code> instance. The lowest maximum
     * value is defined as the smallest value returned by {@link
     * #getActualMaximum(int)} for any possible time value. The least
     * maximum value depends on calendar system specific parameters of
     * the instance. For example, a <code>Calendar</code> for the
     * Gregorian calendar system returns 28 for the
     * <code>DAY_OF_MONTH</code> field, because the 28th is the last
     * day of the shortest month of this calendar, February in a
     * common year.
     *
     * <p>
     *  返回此<code> Calendar </code>实例的给定日历字段的最低最大值。
     * 最小的最大值定义为对于任何可能的时间值,由{@link #getActualMaximum(int)}返回的最小值。最小的最小值取决于实例的日历系统特定参数。
     * 例如,格雷戈里亚日历系统的<code>日历</code>会为<code> DAY_OF_MONTH </code>字段返回28,因为第28个日历是此日历最短月份的最后一天,年。
     * 
     * 
     * @param field the calendar field.
     * @return the lowest maximum value for the given calendar field.
     * @see #getMinimum(int)
     * @see #getMaximum(int)
     * @see #getGreatestMinimum(int)
     * @see #getActualMinimum(int)
     * @see #getActualMaximum(int)
     */
    abstract public int getLeastMaximum(int field);

    /**
     * Returns the minimum value that the specified calendar field
     * could have, given the time value of this <code>Calendar</code>.
     *
     * <p>The default implementation of this method uses an iterative
     * algorithm to determine the actual minimum value for the
     * calendar field. Subclasses should, if possible, override this
     * with a more efficient implementation - in many cases, they can
     * simply return <code>getMinimum()</code>.
     *
     * <p>
     * 返回指定日历字段可能具有的最小值,给定此<code> Calendar </code>的时间值。
     * 
     *  <p>此方法的默认实现使用迭代算法确定日历字段的实际最小值。如果可能的话,子类应该用更有效的实现来覆盖它 - 在许多情况下,它们可以简单地返回<code> getMinimum()</code>。
     * 
     * 
     * @param field the calendar field
     * @return the minimum of the given calendar field for the time
     * value of this <code>Calendar</code>
     * @see #getMinimum(int)
     * @see #getMaximum(int)
     * @see #getGreatestMinimum(int)
     * @see #getLeastMaximum(int)
     * @see #getActualMaximum(int)
     * @since 1.2
     */
    public int getActualMinimum(int field) {
        int fieldValue = getGreatestMinimum(field);
        int endValue = getMinimum(field);

        // if we know that the minimum value is always the same, just return it
        if (fieldValue == endValue) {
            return fieldValue;
        }

        // clone the calendar so we don't mess with the real one, and set it to
        // accept anything for the field values
        Calendar work = (Calendar)this.clone();
        work.setLenient(true);

        // now try each value from getLeastMaximum() to getMaximum() one by one until
        // we get a value that normalizes to another value.  The last value that
        // normalizes to itself is the actual minimum for the current date
        int result = fieldValue;

        do {
            work.set(field, fieldValue);
            if (work.get(field) != fieldValue) {
                break;
            } else {
                result = fieldValue;
                fieldValue--;
            }
        } while (fieldValue >= endValue);

        return result;
    }

    /**
     * Returns the maximum value that the specified calendar field
     * could have, given the time value of this
     * <code>Calendar</code>. For example, the actual maximum value of
     * the <code>MONTH</code> field is 12 in some years, and 13 in
     * other years in the Hebrew calendar system.
     *
     * <p>The default implementation of this method uses an iterative
     * algorithm to determine the actual maximum value for the
     * calendar field. Subclasses should, if possible, override this
     * with a more efficient implementation.
     *
     * <p>
     *  根据此<code> Calendar </code>的时间值,返回指定日历字段可能具有的最大值。
     * 例如,<code> MONTH </code>字段的实际最大值在某些年份为12,在其他年份在希伯来历日历系统中为13。
     * 
     *  <p>此方法的默认实现使用迭代算法确定日历字段的实际最大值。如果可能,子类应该用更有效的实现来覆盖它。
     * 
     * 
     * @param field the calendar field
     * @return the maximum of the given calendar field for the time
     * value of this <code>Calendar</code>
     * @see #getMinimum(int)
     * @see #getMaximum(int)
     * @see #getGreatestMinimum(int)
     * @see #getLeastMaximum(int)
     * @see #getActualMinimum(int)
     * @since 1.2
     */
    public int getActualMaximum(int field) {
        int fieldValue = getLeastMaximum(field);
        int endValue = getMaximum(field);

        // if we know that the maximum value is always the same, just return it.
        if (fieldValue == endValue) {
            return fieldValue;
        }

        // clone the calendar so we don't mess with the real one, and set it to
        // accept anything for the field values.
        Calendar work = (Calendar)this.clone();
        work.setLenient(true);

        // if we're counting weeks, set the day of the week to Sunday.  We know the
        // last week of a month or year will contain the first day of the week.
        if (field == WEEK_OF_YEAR || field == WEEK_OF_MONTH) {
            work.set(DAY_OF_WEEK, firstDayOfWeek);
        }

        // now try each value from getLeastMaximum() to getMaximum() one by one until
        // we get a value that normalizes to another value.  The last value that
        // normalizes to itself is the actual maximum for the current date
        int result = fieldValue;

        do {
            work.set(field, fieldValue);
            if (work.get(field) != fieldValue) {
                break;
            } else {
                result = fieldValue;
                fieldValue++;
            }
        } while (fieldValue <= endValue);

        return result;
    }

    /**
     * Creates and returns a copy of this object.
     *
     * <p>
     *  创建并返回此对象的副本。
     * 
     * 
     * @return a copy of this object.
     */
    @Override
    public Object clone()
    {
        try {
            Calendar other = (Calendar) super.clone();

            other.fields = new int[FIELD_COUNT];
            other.isSet = new boolean[FIELD_COUNT];
            other.stamp = new int[FIELD_COUNT];
            for (int i = 0; i < FIELD_COUNT; i++) {
                other.fields[i] = fields[i];
                other.stamp[i] = stamp[i];
                other.isSet[i] = isSet[i];
            }
            other.zone = (TimeZone) zone.clone();
            return other;
        }
        catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
    }

    private static final String[] FIELD_NAME = {
        "ERA", "YEAR", "MONTH", "WEEK_OF_YEAR", "WEEK_OF_MONTH", "DAY_OF_MONTH",
        "DAY_OF_YEAR", "DAY_OF_WEEK", "DAY_OF_WEEK_IN_MONTH", "AM_PM", "HOUR",
        "HOUR_OF_DAY", "MINUTE", "SECOND", "MILLISECOND", "ZONE_OFFSET",
        "DST_OFFSET"
    };

    /**
     * Returns the name of the specified calendar field.
     *
     * <p>
     *  返回指定日历字段的名称。
     * 
     * 
     * @param field the calendar field
     * @return the calendar field name
     * @exception IndexOutOfBoundsException if <code>field</code> is negative,
     * equal to or greater then <code>FIELD_COUNT</code>.
     */
    static String getFieldName(int field) {
        return FIELD_NAME[field];
    }

    /**
     * Return a string representation of this calendar. This method
     * is intended to be used only for debugging purposes, and the
     * format of the returned string may vary between implementations.
     * The returned string may be empty but may not be <code>null</code>.
     *
     * <p>
     *  返回此日历的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的格式可能因实现而异。返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return  a string representation of this calendar.
     */
    @Override
    public String toString() {
        // NOTE: BuddhistCalendar.toString() interprets the string
        // produced by this method so that the Gregorian year number
        // is substituted by its B.E. year value. It relies on
        // "...,YEAR=<year>,..." or "...,YEAR=?,...".
        StringBuilder buffer = new StringBuilder(800);
        buffer.append(getClass().getName()).append('[');
        appendValue(buffer, "time", isTimeSet, time);
        buffer.append(",areFieldsSet=").append(areFieldsSet);
        buffer.append(",areAllFieldsSet=").append(areAllFieldsSet);
        buffer.append(",lenient=").append(lenient);
        buffer.append(",zone=").append(zone);
        appendValue(buffer, ",firstDayOfWeek", true, (long) firstDayOfWeek);
        appendValue(buffer, ",minimalDaysInFirstWeek", true, (long) minimalDaysInFirstWeek);
        for (int i = 0; i < FIELD_COUNT; ++i) {
            buffer.append(',');
            appendValue(buffer, FIELD_NAME[i], isSet(i), (long) fields[i]);
        }
        buffer.append(']');
        return buffer.toString();
    }

    // =======================privates===============================

    private static void appendValue(StringBuilder sb, String item, boolean valid, long value) {
        sb.append(item).append('=');
        if (valid) {
            sb.append(value);
        } else {
            sb.append('?');
        }
    }

    /**
     * Both firstDayOfWeek and minimalDaysInFirstWeek are locale-dependent.
     * They are used to figure out the week count for a specific date for
     * a given locale. These must be set when a Calendar is constructed.
     * <p>
     *  firstDayOfWeek和minimalDaysInFirstWeek都是区域设置相关的。它们用于计算给定区域设置的特定日期的周计数。这些必须在构建日历时设置。
     * 
     * 
     * @param desiredLocale the given locale.
     */
    private void setWeekCountData(Locale desiredLocale)
    {
        /* try to get the Locale data from the cache */
        int[] data = cachedLocaleData.get(desiredLocale);
        if (data == null) {  /* cache miss */
            data = new int[2];
            data[0] = CalendarDataUtility.retrieveFirstDayOfWeek(desiredLocale);
            data[1] = CalendarDataUtility.retrieveMinimalDaysInFirstWeek(desiredLocale);
            cachedLocaleData.putIfAbsent(desiredLocale, data);
        }
        firstDayOfWeek = data[0];
        minimalDaysInFirstWeek = data[1];
    }

    /**
     * Recomputes the time and updates the status fields isTimeSet
     * and areFieldsSet.  Callers should check isTimeSet and only
     * call this method if isTimeSet is false.
     * <p>
     * 重新计算时间并更新状态字段isTimeSet和areFieldsSet。调用者应该检查isTimeSet,并且只有当isTimeSet为false时才调用此方法。
     * 
     */
    private void updateTime() {
        computeTime();
        // The areFieldsSet and areAllFieldsSet values are no longer
        // controlled here (as of 1.5).
        isTimeSet = true;
    }

    private int compareTo(long t) {
        long thisTime = getMillisOf(this);
        return (thisTime > t) ? 1 : (thisTime == t) ? 0 : -1;
    }

    private static long getMillisOf(Calendar calendar) {
        if (calendar.isTimeSet) {
            return calendar.time;
        }
        Calendar cal = (Calendar) calendar.clone();
        cal.setLenient(true);
        return cal.getTimeInMillis();
    }

    /**
     * Adjusts the stamp[] values before nextStamp overflow. nextStamp
     * is set to the next stamp value upon the return.
     * <p>
     *  在nextStamp溢出之前调整stamp []值。返回时,nextStamp设置为下一个戳记值。
     * 
     */
    private void adjustStamp() {
        int max = MINIMUM_USER_STAMP;
        int newStamp = MINIMUM_USER_STAMP;

        for (;;) {
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < stamp.length; i++) {
                int v = stamp[i];
                if (v >= newStamp && min > v) {
                    min = v;
                }
                if (max < v) {
                    max = v;
                }
            }
            if (max != min && min == Integer.MAX_VALUE) {
                break;
            }
            for (int i = 0; i < stamp.length; i++) {
                if (stamp[i] == min) {
                    stamp[i] = newStamp;
                }
            }
            newStamp++;
            if (min == max) {
                break;
            }
        }
        nextStamp = newStamp;
    }

    /**
     * Sets the WEEK_OF_MONTH and WEEK_OF_YEAR fields to new values with the
     * new parameter value if they have been calculated internally.
     * <p>
     *  如果已在内部计算,则将WEEK_OF_MONTH和WEEK_OF_YEAR字段设置为具有新参数值的新值。
     * 
     */
    private void invalidateWeekFields()
    {
        if (stamp[WEEK_OF_MONTH] != COMPUTED &&
            stamp[WEEK_OF_YEAR] != COMPUTED) {
            return;
        }

        // We have to check the new values of these fields after changing
        // firstDayOfWeek and/or minimalDaysInFirstWeek. If the field values
        // have been changed, then set the new values. (4822110)
        Calendar cal = (Calendar) clone();
        cal.setLenient(true);
        cal.clear(WEEK_OF_MONTH);
        cal.clear(WEEK_OF_YEAR);

        if (stamp[WEEK_OF_MONTH] == COMPUTED) {
            int weekOfMonth = cal.get(WEEK_OF_MONTH);
            if (fields[WEEK_OF_MONTH] != weekOfMonth) {
                fields[WEEK_OF_MONTH] = weekOfMonth;
            }
        }

        if (stamp[WEEK_OF_YEAR] == COMPUTED) {
            int weekOfYear = cal.get(WEEK_OF_YEAR);
            if (fields[WEEK_OF_YEAR] != weekOfYear) {
                fields[WEEK_OF_YEAR] = weekOfYear;
            }
        }
    }

    /**
     * Save the state of this object to a stream (i.e., serialize it).
     *
     * Ideally, <code>Calendar</code> would only write out its state data and
     * the current time, and not write any field data out, such as
     * <code>fields[]</code>, <code>isTimeSet</code>, <code>areFieldsSet</code>,
     * and <code>isSet[]</code>.  <code>nextStamp</code> also should not be part
     * of the persistent state. Unfortunately, this didn't happen before JDK 1.1
     * shipped. To be compatible with JDK 1.1, we will always have to write out
     * the field values and state flags.  However, <code>nextStamp</code> can be
     * removed from the serialization stream; this will probably happen in the
     * near future.
     * <p>
     *  将此对象的状态保存到流(即,序列化它)。
     * 
     *  理想情况下,<code> Calendar </code>只会写出其状态数据和当前时间,而不写任何字段数据,例如<code> fields [] </code>,<code> isTimeSet </code >
     * ,<code> areFieldsSet </code>和<code> isSet [] </code>。
     *  <code> nextStamp </code>也不应该是持久状态的一部分。不幸的是,这没有发生在JDK 1.1发布之前。为了与JDK 1.1兼容,我们将总是写出字段值和状态标志。
     * 但是,<code> nextStamp </code>可以从序列化流中删除;这可能会在不久的将来发生。
     * 
     */
    private synchronized void writeObject(ObjectOutputStream stream)
         throws IOException
    {
        // Try to compute the time correctly, for the future (stream
        // version 2) in which we don't write out fields[] or isSet[].
        if (!isTimeSet) {
            try {
                updateTime();
            }
            catch (IllegalArgumentException e) {}
        }

        // If this Calendar has a ZoneInfo, save it and set a
        // SimpleTimeZone equivalent (as a single DST schedule) for
        // backward compatibility.
        TimeZone savedZone = null;
        if (zone instanceof ZoneInfo) {
            SimpleTimeZone stz = ((ZoneInfo)zone).getLastRuleInstance();
            if (stz == null) {
                stz = new SimpleTimeZone(zone.getRawOffset(), zone.getID());
            }
            savedZone = zone;
            zone = stz;
        }

        // Write out the 1.1 FCS object.
        stream.defaultWriteObject();

        // Write out the ZoneInfo object
        // 4802409: we write out even if it is null, a temporary workaround
        // the real fix for bug 4844924 in corba-iiop
        stream.writeObject(savedZone);
        if (savedZone != null) {
            zone = savedZone;
        }
    }

    private static class CalendarAccessControlContext {
        private static final AccessControlContext INSTANCE;
        static {
            RuntimePermission perm = new RuntimePermission("accessClassInPackage.sun.util.calendar");
            PermissionCollection perms = perm.newPermissionCollection();
            perms.add(perm);
            INSTANCE = new AccessControlContext(new ProtectionDomain[] {
                                                    new ProtectionDomain(null, perms)
                                                });
        }
        private CalendarAccessControlContext() {
        }
    }

    /**
     * Reconstitutes this object from a stream (i.e., deserialize it).
     * <p>
     *  从流重构此对象(即,反序列化它)。
     * 
     */
    private void readObject(ObjectInputStream stream)
         throws IOException, ClassNotFoundException
    {
        final ObjectInputStream input = stream;
        input.defaultReadObject();

        stamp = new int[FIELD_COUNT];

        // Starting with version 2 (not implemented yet), we expect that
        // fields[], isSet[], isTimeSet, and areFieldsSet may not be
        // streamed out anymore.  We expect 'time' to be correct.
        if (serialVersionOnStream >= 2)
        {
            isTimeSet = true;
            if (fields == null) {
                fields = new int[FIELD_COUNT];
            }
            if (isSet == null) {
                isSet = new boolean[FIELD_COUNT];
            }
        }
        else if (serialVersionOnStream >= 0)
        {
            for (int i=0; i<FIELD_COUNT; ++i) {
                stamp[i] = isSet[i] ? COMPUTED : UNSET;
            }
        }

        serialVersionOnStream = currentSerialVersion;

        // If there's a ZoneInfo object, use it for zone.
        ZoneInfo zi = null;
        try {
            zi = AccessController.doPrivileged(
                    new PrivilegedExceptionAction<ZoneInfo>() {
                        @Override
                        public ZoneInfo run() throws Exception {
                            return (ZoneInfo) input.readObject();
                        }
                    },
                    CalendarAccessControlContext.INSTANCE);
        } catch (PrivilegedActionException pae) {
            Exception e = pae.getException();
            if (!(e instanceof OptionalDataException)) {
                if (e instanceof RuntimeException) {
                    throw (RuntimeException) e;
                } else if (e instanceof IOException) {
                    throw (IOException) e;
                } else if (e instanceof ClassNotFoundException) {
                    throw (ClassNotFoundException) e;
                }
                throw new RuntimeException(e);
            }
        }
        if (zi != null) {
            zone = zi;
        }

        // If the deserialized object has a SimpleTimeZone, try to
        // replace it with a ZoneInfo equivalent (as of 1.4) in order
        // to be compatible with the SimpleTimeZone-based
        // implementation as much as possible.
        if (zone instanceof SimpleTimeZone) {
            String id = zone.getID();
            TimeZone tz = TimeZone.getTimeZone(id);
            if (tz != null && tz.hasSameRules(zone) && tz.getID().equals(id)) {
                zone = tz;
            }
        }
    }

    /**
     * Converts this object to an {@link Instant}.
     * <p>
     * The conversion creates an {@code Instant} that represents the
     * same point on the time-line as this {@code Calendar}.
     *
     * <p>
     *  将此对象转换为{@link Instant}。
     * <p>
     * 
     * @return the instant representing the same point on the time-line
     * @since 1.8
     */
    public final Instant toInstant() {
        return Instant.ofEpochMilli(getTimeInMillis());
    }
}
