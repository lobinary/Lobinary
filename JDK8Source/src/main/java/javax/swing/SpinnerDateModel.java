/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing;

import java.util.*;
import java.io.Serializable;


/**
 * A <code>SpinnerModel</code> for sequences of <code>Date</code>s.
 * The upper and lower bounds of the sequence are defined by properties called
 * <code>start</code> and <code>end</code> and the size
 * of the increase or decrease computed by the <code>nextValue</code>
 * and <code>previousValue</code> methods is defined by a property
 * called <code>calendarField</code>.  The <code>start</code>
 * and <code>end</code> properties can be <code>null</code> to
 * indicate that the sequence has no lower or upper limit.
 * <p>
 * The value of the <code>calendarField</code> property must be one of the
 * <code>java.util.Calendar</code> constants that specify a field
 * within a <code>Calendar</code>.  The <code>getNextValue</code>
 * and <code>getPreviousValue</code>
 * methods change the date forward or backwards by this amount.
 * For example, if <code>calendarField</code> is <code>Calendar.DAY_OF_WEEK</code>,
 * then <code>nextValue</code> produces a <code>Date</code> that's 24
 * hours after the current <code>value</code>, and <code>previousValue</code>
 * produces a <code>Date</code> that's 24 hours earlier.
 * <p>
 * The legal values for <code>calendarField</code> are:
 * <ul>
 *   <li><code>Calendar.ERA</code>
 *   <li><code>Calendar.YEAR</code>
 *   <li><code>Calendar.MONTH</code>
 *   <li><code>Calendar.WEEK_OF_YEAR</code>
 *   <li><code>Calendar.WEEK_OF_MONTH</code>
 *   <li><code>Calendar.DAY_OF_MONTH</code>
 *   <li><code>Calendar.DAY_OF_YEAR</code>
 *   <li><code>Calendar.DAY_OF_WEEK</code>
 *   <li><code>Calendar.DAY_OF_WEEK_IN_MONTH</code>
 *   <li><code>Calendar.AM_PM</code>
 *   <li><code>Calendar.HOUR</code>
 *   <li><code>Calendar.HOUR_OF_DAY</code>
 *   <li><code>Calendar.MINUTE</code>
 *   <li><code>Calendar.SECOND</code>
 *   <li><code>Calendar.MILLISECOND</code>
 * </ul>
 * However some UIs may set the calendarField before committing the edit
 * to spin the field under the cursor. If you only want one field to
 * spin you can subclass and ignore the setCalendarField calls.
 * <p>
 * This model inherits a <code>ChangeListener</code>.  The
 * <code>ChangeListeners</code> are notified whenever the models
 * <code>value</code>, <code>calendarField</code>,
 * <code>start</code>, or <code>end</code> properties changes.
 *
 * <p>
 *  <code> Date </code>的序列的<code> SpinnerModel </code>。
 * 序列的上限和下限由称为<code> start </code>和<code> end </code>的属性以及由<code> nextValue </code>计算的增加或减少的大小<code> pre
 * viousValue </code>方法由一个名为<code> calendarField </code>的属性定义。
 *  <code> Date </code>的序列的<code> SpinnerModel </code>。
 *  <code> start </code>和<code> end </code>属性可以是<code> null </code>,表示序列没有下限或上限。
 * <p>
 *  <code> calendarField </code>属性的值必须是指定<code> Calendar </code>中的字段的<code> java.util.Calendar </code>常量
 * 之一。
 *  <code> getNextValue </code>和<code> getPreviousValue </code>方法将日期向前或向后更改此金额。
 * 例如,如果<code> calendarField </code>是<code> Calendar.DAY_OF_WEEK </code>,则<code> nextValue </code>会生成一个<code>
 *  Date </code> code> value </code>和<code> previousValue </code>生成24小时前的<code> Date </code>。
 *  <code> getNextValue </code>和<code> getPreviousValue </code>方法将日期向前或向后更改此金额。
 * <p>
 *  <code> calendarField </code>的合法值为：
 * <ul>
 * <li> <code> Calendar.ERA </code> <li> <code> Calendar.WEEK_OF_YEAR </span> </code>代码> <li> <code> Cal
 * endar.WEEK_OF_MONTH </code> <li> <code> Calendar.DAY_OF_MONTH </code> <li> <code> Calendar.DAY_OF_YEA
 * R </code> <li> <code> Calendar.DAY_OF_WEEK </code> <li> <code> Calendar.DAY_OF_WEEK_IN_MONTH </code> 
 * <li> <code> Calendar.AM_PM </code> <li> <code> Calendar.HOUR </code> <li> <code> .HOUR_OF_DAY </code>
 *  <li> <code> Calendar.MINUTE </code> <li> <code> Calendar.SECOND </code> <li> <code> Calendar.MILLISE
 * COND </code>。
 * </ul>
 *  但是一些UI可能会在提交编辑之前设置calendarField,以旋转光标下的字段。如果你只想要一个字段旋转你可以子类和忽略setCalendarField调用。
 * <p>
 *  这个模型继承一个<code> ChangeListener </code>。
 * 当模型<code>值</code>,<code> calendarField </code>,<code>开始</code>或<code>结束</code>时,通知<code> ChangeListen
 * ers </code>属性变化。
 *  这个模型继承一个<code> ChangeListener </code>。
 * 
 * 
 * @see JSpinner
 * @see SpinnerModel
 * @see AbstractSpinnerModel
 * @see SpinnerListModel
 * @see SpinnerNumberModel
 * @see Calendar#add
 *
 * @author Hans Muller
 * @since 1.4
 */
public class SpinnerDateModel extends AbstractSpinnerModel implements Serializable
{
    private Comparable start, end;
    private Calendar value;
    private int calendarField;


    private boolean calendarFieldOK(int calendarField) {
        switch(calendarField) {
        case Calendar.ERA:
        case Calendar.YEAR:
        case Calendar.MONTH:
        case Calendar.WEEK_OF_YEAR:
        case Calendar.WEEK_OF_MONTH:
        case Calendar.DAY_OF_MONTH:
        case Calendar.DAY_OF_YEAR:
        case Calendar.DAY_OF_WEEK:
        case Calendar.DAY_OF_WEEK_IN_MONTH:
        case Calendar.AM_PM:
        case Calendar.HOUR:
        case Calendar.HOUR_OF_DAY:
        case Calendar.MINUTE:
        case Calendar.SECOND:
        case Calendar.MILLISECOND:
            return true;
        default:
            return false;
        }
    }


    /**
     * Creates a <code>SpinnerDateModel</code> that represents a sequence of dates
     * between <code>start</code> and <code>end</code>.  The
     * <code>nextValue</code> and <code>previousValue</code> methods
     * compute elements of the sequence by advancing or reversing
     * the current date <code>value</code> by the
     * <code>calendarField</code> time unit.  For a precise description
     * of what it means to increment or decrement a <code>Calendar</code>
     * <code>field</code>, see the <code>add</code> method in
     * <code>java.util.Calendar</code>.
     * <p>
     * The <code>start</code> and <code>end</code> parameters can be
     * <code>null</code> to indicate that the range doesn't have an
     * upper or lower bound.  If <code>value</code> or
     * <code>calendarField</code> is <code>null</code>, or if both
     * <code>start</code> and <code>end</code> are specified and
     * <code>minimum &gt; maximum</code> then an
     * <code>IllegalArgumentException</code> is thrown.
     * Similarly if <code>(minimum &lt;= value &lt;= maximum)</code> is false,
     * an IllegalArgumentException is thrown.
     *
     * <p>
     *  创建代表<code> start </code>和<code> end </code>之间的一系列日期的<code> SpinnerDateModel </code>。
     *  <code> nextValue </code>和<code> previousValue </code>方法通过<code> calendarField </code>时间提前或逆转当前日期<code>
     * 值</code>来计算序列的元素单元。
     *  创建代表<code> start </code>和<code> end </code>之间的一系列日期的<code> SpinnerDateModel </code>。
     * 有关对<code> Calendar </code> <code>字段</code>进行递增或递减的详细说明,请参阅<code> java.util.Calendar中的<code> add </code>
     *  </code>。
     *  创建代表<code> start </code>和<code> end </code>之间的一系列日期的<code> SpinnerDateModel </code>。
     * <p>
     * <code> start </code>和<code> end </code>参数可以是<code> null </code>,表示范围没有上限或下限。
     * 如果<code> value </code>或<code> dateField </code>是<code> null </code>,或者如果指定了<code> start </code>和<code>
     *  end </code>和<code> minimum&gt;最大</code>,然后抛出<code> IllegalArgumentException </code>。
     * <code> start </code>和<code> end </code>参数可以是<code> null </code>,表示范围没有上限或下限。
     * 类似地,如果<code>(最小值&lt; =值&lt; =最大值)</code>为假,则抛出IllegalArgumentException。
     * 
     * 
     * @param value the current (non <code>null</code>) value of the model
     * @param start the first date in the sequence or <code>null</code>
     * @param end the last date in the sequence or <code>null</code>
     * @param calendarField one of
     *   <ul>
     *    <li><code>Calendar.ERA</code>
     *    <li><code>Calendar.YEAR</code>
     *    <li><code>Calendar.MONTH</code>
     *    <li><code>Calendar.WEEK_OF_YEAR</code>
     *    <li><code>Calendar.WEEK_OF_MONTH</code>
     *    <li><code>Calendar.DAY_OF_MONTH</code>
     *    <li><code>Calendar.DAY_OF_YEAR</code>
     *    <li><code>Calendar.DAY_OF_WEEK</code>
     *    <li><code>Calendar.DAY_OF_WEEK_IN_MONTH</code>
     *    <li><code>Calendar.AM_PM</code>
     *    <li><code>Calendar.HOUR</code>
     *    <li><code>Calendar.HOUR_OF_DAY</code>
     *    <li><code>Calendar.MINUTE</code>
     *    <li><code>Calendar.SECOND</code>
     *    <li><code>Calendar.MILLISECOND</code>
     *   </ul>
     *
     * @throws IllegalArgumentException if <code>value</code> or
     *    <code>calendarField</code> are <code>null</code>,
     *    if <code>calendarField</code> isn't valid,
     *    or if the following expression is
     *    false: <code>(start &lt;= value &lt;= end)</code>.
     *
     * @see Calendar#add
     * @see #setValue
     * @see #setStart
     * @see #setEnd
     * @see #setCalendarField
     */
    public SpinnerDateModel(Date value, Comparable start, Comparable end, int calendarField) {
        if (value == null) {
            throw new IllegalArgumentException("value is null");
        }
        if (!calendarFieldOK(calendarField)) {
            throw new IllegalArgumentException("invalid calendarField");
        }
        if (!(((start == null) || (start.compareTo(value) <= 0)) &&
              ((end == null) || (end.compareTo(value) >= 0)))) {
            throw new IllegalArgumentException("(start <= value <= end) is false");
        }
        this.value = Calendar.getInstance();
        this.start = start;
        this.end = end;
        this.calendarField = calendarField;

        this.value.setTime(value);
    }


    /**
     * Constructs a <code>SpinnerDateModel</code> whose initial
     * <code>value</code> is the current date, <code>calendarField</code>
     * is equal to <code>Calendar.DAY_OF_MONTH</code>, and for which
     * there are no <code>start</code>/<code>end</code> limits.
     * <p>
     *  构造一个<code> SpinnerDateModel </code>,其初始<code>值</code>是当前日期,<code> calendarField </code>等于<code> Cale
     * ndar.DAY_OF_MONTH </code>没有<code> start </code> / <code> end </code>限制。
     * 
     */
    public SpinnerDateModel() {
        this(new Date(), null, null, Calendar.DAY_OF_MONTH);
    }


    /**
     * Changes the lower limit for Dates in this sequence.
     * If <code>start</code> is <code>null</code>,
     * then there is no lower limit.  No bounds checking is done here:
     * the new start value may invalidate the
     * <code>(start &lt;= value &lt;= end)</code>
     * invariant enforced by the constructors.  This is to simplify updating
     * the model.  Naturally one should ensure that the invariant is true
     * before calling the <code>nextValue</code>, <code>previousValue</code>,
     * or <code>setValue</code> methods.
     * <p>
     * Typically this property is a <code>Date</code> however it's possible to use
     * a <code>Comparable</code> with a <code>compareTo</code> method for Dates.
     * For example <code>start</code> might be an instance of a class like this:
     * <pre>
     * MyStartDate implements Comparable {
     *     long t = 12345;
     *     public int compareTo(Date d) {
     *            return (t &lt; d.getTime() ? -1 : (t == d.getTime() ? 0 : 1));
     *     }
     *     public int compareTo(Object o) {
     *            return compareTo((Date)o);
     *     }
     * }
     * </pre>
     * Note that the above example will throw a <code>ClassCastException</code>
     * if the <code>Object</code> passed to <code>compareTo(Object)</code>
     * is not a <code>Date</code>.
     * <p>
     * This method fires a <code>ChangeEvent</code> if the
     * <code>start</code> has changed.
     *
     * <p>
     *  更改此序列中日期的下限。如果<code> start </code>是<code> null </code>,那么没有下限。
     * 这里不进行边界检查：新的起始值可以使由构造函数强制执行的<code>(开始<值<=结束)</>不变量无效。这是为了简化更新模型。
     * 自然地,在调用<code> nextValue </code>,<code> previousValue </code>或<code> setValue </code>方法之前,应确保不变量为真。
     * <p>
     *  通常,此属性是<code> Date </code>,但是可以对Dates使用<code> compareFor </code>方法使用<code> Comparable </code>。
     * 例如<code> start </code>可能是这样的类的一个实例：。
     * <pre>
     * MyStartDate实现了Comparable {long t = 12345; public int compareTo(Date d){return(t <d.getTime()?-1：(t == d.getTime()?0：1) } public int compareTo(Object o){return compareTo((Date)o); }}。
     * </pre>
     *  注意,如果<code> Object </code>传递给<code> compareTo(Object)</code>不是<code> Date </code>,那么上述示例将抛出<code> Cl
     * assCastException </code> 。
     * <p>
     *  如果<code> start </code>已更改,则此方法触发<code> ChangeEvent </code>。
     * 
     * 
     * @param start defines the first date in the sequence
     * @see #getStart
     * @see #setEnd
     * @see #addChangeListener
     */
    public void setStart(Comparable start) {
        if ((start == null) ? (this.start != null) : !start.equals(this.start)) {
            this.start = start;
            fireStateChanged();
        }
    }


    /**
     * Returns the first <code>Date</code> in the sequence.
     *
     * <p>
     *  返回序列中的第一个<code> Date </code>。
     * 
     * 
     * @return the value of the <code>start</code> property
     * @see #setStart
     */
    public Comparable getStart() {
        return start;
    }


    /**
     * Changes the upper limit for <code>Date</code>s in this sequence.
     * If <code>start</code> is <code>null</code>, then there is no upper
     * limit.  No bounds checking is done here: the new
     * start value may invalidate the <code>(start &lt;= value &lt;= end)</code>
     * invariant enforced by the constructors.  This is to simplify updating
     * the model.  Naturally, one should ensure that the invariant is true
     * before calling the <code>nextValue</code>, <code>previousValue</code>,
     * or <code>setValue</code> methods.
     * <p>
     * Typically this property is a <code>Date</code> however it's possible to use
     * <code>Comparable</code> with a <code>compareTo</code> method for
     * <code>Date</code>s.  See <code>setStart</code> for an example.
     * <p>
     * This method fires a <code>ChangeEvent</code> if the <code>end</code>
     * has changed.
     *
     * <p>
     *  更改此序列中<code> Date </code>的上限。如果<code> start </code>是<code> null </code>,那么没有上限。
     * 这里不进行边界检查：新的起始值可以使由构造函数强制执行的<code>(开始<值<=结束)</>不变量无效。这是为了简化更新模型。
     * 自然地,在调用<code> nextValue </code>,<code> previousValue </code>或<code> setValue </code>方法之前,应确保不变量为真。
     * <p>
     *  通常,此属性是<code> Date </code>,但是可以对<code> Date </code>的<code> compareTo </code>方法使用<code> Comparable </code>
     * 有关示例,请参见<code> setStart </code>。
     * <p>
     *  如果<code> end </code>已更改,此方法会触发<code> ChangeEvent </code>。
     * 
     * 
     * @param end defines the last date in the sequence
     * @see #getEnd
     * @see #setStart
     * @see #addChangeListener
     */
    public void setEnd(Comparable end) {
        if ((end == null) ? (this.end != null) : !end.equals(this.end)) {
            this.end = end;
            fireStateChanged();
        }
    }


    /**
     * Returns the last <code>Date</code> in the sequence.
     *
     * <p>
     *  返回序列中的最后一个<code> Date </code>。
     * 
     * 
     * @return the value of the <code>end</code> property
     * @see #setEnd
     */
    public Comparable getEnd() {
        return end;
    }


    /**
     * Changes the size of the date value change computed
     * by the <code>nextValue</code> and <code>previousValue</code> methods.
     * The <code>calendarField</code> parameter must be one of the
     * <code>Calendar</code> field constants like <code>Calendar.MONTH</code>
     * or <code>Calendar.MINUTE</code>.
     * The <code>nextValue</code> and <code>previousValue</code> methods
     * simply move the specified <code>Calendar</code> field forward or backward
     * by one unit with the <code>Calendar.add</code> method.
     * You should use this method with care as some UIs may set the
     * calendarField before committing the edit to spin the field under
     * the cursor. If you only want one field to spin you can subclass
     * and ignore the setCalendarField calls.
     *
     * <p>
     * 更改由<code> nextValue </code>和<code> previousValue </code>方法计算的日期值更改的大小。
     *  <code> calendarField </code>参数必须是<code> Calendar </code>字段常量之一,如<code> Calendar.MONTH </code>或<code>
     *  Calendar.MINUTE </code>。
     * 更改由<code> nextValue </code>和<code> previousValue </code>方法计算的日期值更改的大小。
     *  <code> nextValue </code>和<code> previousValue </code>方法用<code> Calendar.add </code>字段向前或向后移动指定的<code>
     *  Calendar </code>方法。
     * 更改由<code> nextValue </code>和<code> previousValue </code>方法计算的日期值更改的大小。
     * 您应该谨慎使用此方法,因为一些UI可能会在提交编辑之前设置calendarField以旋转光标下的字段。如果你只想要一个字段旋转你可以子类和忽略setCalendarField调用。
     * 
     * 
     * @param calendarField one of
     *  <ul>
     *    <li><code>Calendar.ERA</code>
     *    <li><code>Calendar.YEAR</code>
     *    <li><code>Calendar.MONTH</code>
     *    <li><code>Calendar.WEEK_OF_YEAR</code>
     *    <li><code>Calendar.WEEK_OF_MONTH</code>
     *    <li><code>Calendar.DAY_OF_MONTH</code>
     *    <li><code>Calendar.DAY_OF_YEAR</code>
     *    <li><code>Calendar.DAY_OF_WEEK</code>
     *    <li><code>Calendar.DAY_OF_WEEK_IN_MONTH</code>
     *    <li><code>Calendar.AM_PM</code>
     *    <li><code>Calendar.HOUR</code>
     *    <li><code>Calendar.HOUR_OF_DAY</code>
     *    <li><code>Calendar.MINUTE</code>
     *    <li><code>Calendar.SECOND</code>
     *    <li><code>Calendar.MILLISECOND</code>
     *  </ul>
     * <p>
     * This method fires a <code>ChangeEvent</code> if the
     * <code>calendarField</code> has changed.
     *
     * @see #getCalendarField
     * @see #getNextValue
     * @see #getPreviousValue
     * @see Calendar#add
     * @see #addChangeListener
     */
    public void setCalendarField(int calendarField) {
        if (!calendarFieldOK(calendarField)) {
            throw new IllegalArgumentException("invalid calendarField");
        }
        if (calendarField != this.calendarField) {
            this.calendarField = calendarField;
            fireStateChanged();
        }
    }


    /**
     * Returns the <code>Calendar</code> field that is added to or subtracted from
     * by the <code>nextValue</code> and <code>previousValue</code> methods.
     *
     * <p>
     *  返回<code> nextValue </code>和<code> previousValue </code>方法中添加或减去的<code>日历</code>字段。
     * 
     * 
     * @return the value of the <code>calendarField</code> property
     * @see #setCalendarField
     */
    public int getCalendarField() {
        return calendarField;
    }


    /**
     * Returns the next <code>Date</code> in the sequence, or <code>null</code> if
     * the next date is after <code>end</code>.
     *
     * <p>
     *  返回序列中的下一个<code> Date </code>,如果下一个日期在<code> end </code>之后,则返回<code> null </code>。
     * 
     * 
     * @return the next <code>Date</code> in the sequence, or <code>null</code> if
     *     the next date is after <code>end</code>.
     *
     * @see SpinnerModel#getNextValue
     * @see #getPreviousValue
     * @see #setCalendarField
     */
    public Object getNextValue() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(value.getTime());
        cal.add(calendarField, 1);
        Date next = cal.getTime();
        return ((end == null) || (end.compareTo(next) >= 0)) ? next : null;
    }


    /**
     * Returns the previous <code>Date</code> in the sequence, or <code>null</code>
     * if the previous date is before <code>start</code>.
     *
     * <p>
     *  返回序列中的上一个<code> Date </code>,如果上一个日期在<code> start </code>之前,则返回<code> null </code>。
     * 
     * 
     * @return the previous <code>Date</code> in the sequence, or
     *     <code>null</code> if the previous date
     *     is before <code>start</code>
     *
     * @see SpinnerModel#getPreviousValue
     * @see #getNextValue
     * @see #setCalendarField
     */
    public Object getPreviousValue() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(value.getTime());
        cal.add(calendarField, -1);
        Date prev = cal.getTime();
        return ((start == null) || (start.compareTo(prev) <= 0)) ? prev : null;
    }


    /**
     * Returns the current element in this sequence of <code>Date</code>s.
     * This method is equivalent to <code>(Date)getValue</code>.
     *
     * <p>
     *  返回<code> Date </code>的此序列中的当前元素。此方法等同于<code>(Date)getValue </code>。
     * 
     * 
     * @return the <code>value</code> property
     * @see #setValue
     */
    public Date getDate() {
        return value.getTime();
    }


    /**
     * Returns the current element in this sequence of <code>Date</code>s.
     *
     * <p>
     *  返回<code> Date </code>的此序列中的当前元素。
     * 
     * 
     * @return the <code>value</code> property
     * @see #setValue
     * @see #getDate
     */
    public Object getValue() {
        return value.getTime();
    }


    /**
     * Sets the current <code>Date</code> for this sequence.
     * If <code>value</code> is <code>null</code>,
     * an <code>IllegalArgumentException</code> is thrown.  No bounds
     * checking is done here:
     * the new value may invalidate the <code>(start &lt;= value &lt; end)</code>
     * invariant enforced by the constructors.  Naturally, one should ensure
     * that the <code>(start &lt;= value &lt;= maximum)</code> invariant is true
     * before calling the <code>nextValue</code>, <code>previousValue</code>,
     * or <code>setValue</code> methods.
     * <p>
     * This method fires a <code>ChangeEvent</code> if the
     * <code>value</code> has changed.
     *
     * <p>
     * 设置此序列的当前<code> Date </code>。
     * 如果<code> value </code>是<code> null </code>,则抛出<code> IllegalArgumentException </code>。
     * 这里不进行边界检查：新值可以使由构造函数强制执行的<code>(start&lt; = value&lt; end)</code>不变量无效。
     * 自然地,应该在调用<code> nextValue </code>,<code> previousValue </code>之前确保<code>(start&lt; = value&lt; = maxi
     * mum)</code> <code> setValue </code>方法。
     * 
     * @param value the current (non <code>null</code>)
     *    <code>Date</code> for this sequence
     * @throws IllegalArgumentException if value is <code>null</code>
     *    or not a <code>Date</code>
     * @see #getDate
     * @see #getValue
     * @see #addChangeListener
     */
    public void setValue(Object value) {
        if ((value == null) || !(value instanceof Date)) {
            throw new IllegalArgumentException("illegal value");
        }
        if (!value.equals(this.value.getTime())) {
            this.value.setTime((Date)value);
            fireStateChanged();
        }
    }
}
