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

package java.sql;

import java.time.Instant;
import java.time.LocalDate;

/**
 * <P>A thin wrapper around a millisecond value that allows
 * JDBC to identify this as an SQL <code>DATE</code> value.  A
 * milliseconds value represents the number of milliseconds that
 * have passed since January 1, 1970 00:00:00.000 GMT.
 * <p>
 * To conform with the definition of SQL <code>DATE</code>, the
 * millisecond values wrapped by a <code>java.sql.Date</code> instance
 * must be 'normalized' by setting the
 * hours, minutes, seconds, and milliseconds to zero in the particular
 * time zone with which the instance is associated.
 * <p>
 *  <P>一个大约为毫秒值的精简包装,允许JDBC将此标识为SQL <code> DATE </code>值。 A毫秒值表示自1970年1月1日00：00：00.000 GMT以来经过的毫秒数。
 * <p>
 *  为了符合SQL <code> DATE </code>的定义,由<code> java.sql.Date </code>实例包装的毫秒值必须通过设置小时,分钟,秒和在与该实例相关联的特定时区中,该时间
 * 间隔为零。
 * 
 */
public class Date extends java.util.Date {

    /**
     * Constructs a <code>Date</code> object initialized with the given
     * year, month, and day.
     * <P>
     * The result is undefined if a given argument is out of bounds.
     *
     * <p>
     *  构造使用给定的年,月和日初始化的<code> Date </code>对象。
     * <P>
     *  如果给定的参数超出范围,结果是未定义的。
     * 
     * 
     * @param year the year minus 1900; must be 0 to 8099. (Note that
     *        8099 is 9999 minus 1900.)
     * @param month 0 to 11
     * @param day 1 to 31
     * @deprecated instead use the constructor <code>Date(long date)</code>
     */
    @Deprecated
    public Date(int year, int month, int day) {
        super(year, month, day);
    }

    /**
     * Constructs a <code>Date</code> object using the given milliseconds
     * time value.  If the given milliseconds value contains time
     * information, the driver will set the time components to the
     * time in the default time zone (the time zone of the Java virtual
     * machine running the application) that corresponds to zero GMT.
     *
     * <p>
     *  使用给定的毫秒时间值构造一个<code> Date </code>对象。
     * 如果给定的毫秒值包含时间信息,则驱动程序将时间组件设置为对应于零GMT的默认时区(运行应用程序的Java虚拟机的时区)中的时间。
     * 
     * 
     * @param date milliseconds since January 1, 1970, 00:00:00 GMT not
     *        to exceed the milliseconds representation for the year 8099.
     *        A negative number indicates the number of milliseconds
     *        before January 1, 1970, 00:00:00 GMT.
     */
    public Date(long date) {
        // If the millisecond date value contains time info, mask it out.
        super(date);

    }

    /**
     * Sets an existing <code>Date</code> object
     * using the given milliseconds time value.
     * If the given milliseconds value contains time information,
     * the driver will set the time components to the
     * time in the default time zone (the time zone of the Java virtual
     * machine running the application) that corresponds to zero GMT.
     *
     * <p>
     *  使用给定的毫秒时间值设置现有的<code> Date </code>对象。
     * 如果给定的毫秒值包含时间信息,则驱动程序将时间组件设置为对应于零GMT的默认时区(运行应用程序的Java虚拟机的时区)中的时间。
     * 
     * 
     * @param date milliseconds since January 1, 1970, 00:00:00 GMT not
     *        to exceed the milliseconds representation for the year 8099.
     *        A negative number indicates the number of milliseconds
     *        before January 1, 1970, 00:00:00 GMT.
     */
    public void setTime(long date) {
        // If the millisecond date value contains time info, mask it out.
        super.setTime(date);
    }

    /**
     * Converts a string in JDBC date escape format to
     * a <code>Date</code> value.
     *
     * <p>
     * 将JDBC日期转义格式的字符串转换为<code> Date </code>值。
     * 
     * 
     * @param s a <code>String</code> object representing a date in
     *        in the format "yyyy-[m]m-[d]d". The leading zero for <code>mm</code>
     * and <code>dd</code> may also be omitted.
     * @return a <code>java.sql.Date</code> object representing the
     *         given date
     * @throws IllegalArgumentException if the date given is not in the
     *         JDBC date escape format (yyyy-[m]m-[d]d)
     */
    public static Date valueOf(String s) {
        final int YEAR_LENGTH = 4;
        final int MONTH_LENGTH = 2;
        final int DAY_LENGTH = 2;
        final int MAX_MONTH = 12;
        final int MAX_DAY = 31;
        int firstDash;
        int secondDash;
        Date d = null;
        if (s == null) {
            throw new java.lang.IllegalArgumentException();
        }

        firstDash = s.indexOf('-');
        secondDash = s.indexOf('-', firstDash + 1);

        if ((firstDash > 0) && (secondDash > 0) && (secondDash < s.length() - 1)) {
            String yyyy = s.substring(0, firstDash);
            String mm = s.substring(firstDash + 1, secondDash);
            String dd = s.substring(secondDash + 1);
            if (yyyy.length() == YEAR_LENGTH &&
                    (mm.length() >= 1 && mm.length() <= MONTH_LENGTH) &&
                    (dd.length() >= 1 && dd.length() <= DAY_LENGTH)) {
                int year = Integer.parseInt(yyyy);
                int month = Integer.parseInt(mm);
                int day = Integer.parseInt(dd);

                if ((month >= 1 && month <= MAX_MONTH) && (day >= 1 && day <= MAX_DAY)) {
                    d = new Date(year - 1900, month - 1, day);
                }
            }
        }
        if (d == null) {
            throw new java.lang.IllegalArgumentException();
        }

        return d;

    }


    /**
     * Formats a date in the date escape format yyyy-mm-dd.
     * <P>
     * <p>
     *  以日期转义格式yyyy-mm-dd格式化日期。
     * <P>
     * 
     * @return a String in yyyy-mm-dd format
     */
    @SuppressWarnings("deprecation")
    public String toString () {
        int year = super.getYear() + 1900;
        int month = super.getMonth() + 1;
        int day = super.getDate();

        char buf[] = "2000-00-00".toCharArray();
        buf[0] = Character.forDigit(year/1000,10);
        buf[1] = Character.forDigit((year/100)%10,10);
        buf[2] = Character.forDigit((year/10)%10,10);
        buf[3] = Character.forDigit(year%10,10);
        buf[5] = Character.forDigit(month/10,10);
        buf[6] = Character.forDigit(month%10,10);
        buf[8] = Character.forDigit(day/10,10);
        buf[9] = Character.forDigit(day%10,10);

        return new String(buf);
    }

    // Override all the time operations inherited from java.util.Date;

   /**
    * This method is deprecated and should not be used because SQL Date
    * values do not have a time component.
    *
    * <p>
    *  此方法已弃用,不应该使用,因为SQL Date值没有时间组件。
    * 
    * 
    * @deprecated
    * @exception java.lang.IllegalArgumentException if this method is invoked
    * @see #setHours
    */
    @Deprecated
    public int getHours() {
        throw new java.lang.IllegalArgumentException();
    }

   /**
    * This method is deprecated and should not be used because SQL Date
    * values do not have a time component.
    *
    * <p>
    *  此方法已弃用,不应使用,因为SQL Date值没有时间组件。
    * 
    * 
    * @deprecated
    * @exception java.lang.IllegalArgumentException if this method is invoked
    * @see #setMinutes
    */
    @Deprecated
    public int getMinutes() {
        throw new java.lang.IllegalArgumentException();
    }

   /**
    * This method is deprecated and should not be used because SQL Date
    * values do not have a time component.
    *
    * <p>
    *  此方法已弃用,不应使用,因为SQL Date值没有时间组件。
    * 
    * 
    * @deprecated
    * @exception java.lang.IllegalArgumentException if this method is invoked
    * @see #setSeconds
    */
    @Deprecated
    public int getSeconds() {
        throw new java.lang.IllegalArgumentException();
    }

   /**
    * This method is deprecated and should not be used because SQL Date
    * values do not have a time component.
    *
    * <p>
    *  此方法已弃用,不应使用,因为SQL Date值没有时间组件。
    * 
    * 
    * @deprecated
    * @exception java.lang.IllegalArgumentException if this method is invoked
    * @see #getHours
    */
    @Deprecated
    public void setHours(int i) {
        throw new java.lang.IllegalArgumentException();
    }

   /**
    * This method is deprecated and should not be used because SQL Date
    * values do not have a time component.
    *
    * <p>
    *  此方法已弃用,不应使用,因为SQL Date值没有时间组件。
    * 
    * 
    * @deprecated
    * @exception java.lang.IllegalArgumentException if this method is invoked
    * @see #getMinutes
    */
    @Deprecated
    public void setMinutes(int i) {
        throw new java.lang.IllegalArgumentException();
    }

   /**
    * This method is deprecated and should not be used because SQL Date
    * values do not have a time component.
    *
    * <p>
    *  此方法已弃用,不应使用,因为SQL Date值没有时间组件。
    * 
    * 
    * @deprecated
    * @exception java.lang.IllegalArgumentException if this method is invoked
    * @see #getSeconds
    */
    @Deprecated
    public void setSeconds(int i) {
        throw new java.lang.IllegalArgumentException();
    }

   /**
    * Private serial version unique ID to ensure serialization
    * compatibility.
    * <p>
    *  私有串口版本唯一的ID,以确保序列化兼容性。
    * 
    */
    static final long serialVersionUID = 1511598038487230103L;

    /**
     * Obtains an instance of {@code Date} from a {@link LocalDate} object
     * with the same year, month and day of month value as the given
     * {@code LocalDate}.
     * <p>
     * The provided {@code LocalDate} is interpreted as the local date
     * in the local time zone.
     *
     * <p>
     *  从{@link LocalDate}对象中获取{@code Date}的实例,该对象的年,月和日的值与给定的{@code LocalDate}相同。
     * <p>
     *  所提供的{@code LocalDate}会解释为本地时区中的本地日期。
     * 
     * 
     * @param date a {@code LocalDate} to convert
     * @return a {@code Date} object
     * @exception NullPointerException if {@code date} is null
     * @since 1.8
     */
    @SuppressWarnings("deprecation")
    public static Date valueOf(LocalDate date) {
        return new Date(date.getYear() - 1900, date.getMonthValue() -1,
                        date.getDayOfMonth());
    }

    /**
     * Converts this {@code Date} object to a {@code LocalDate}
     * <p>
     * The conversion creates a {@code LocalDate} that represents the same
     * date value as this {@code Date} in local time zone
     *
     * <p>
     *  将此{@code Date}对象转换为{@code LocalDate}
     * <p>
     *  转换会创建{@code LocalDate},表示与本地时区中的{@code Date}相同的日期值
     * 
     * 
     * @return a {@code LocalDate} object representing the same date value
     *
     * @since 1.8
     */
    @SuppressWarnings("deprecation")
    public LocalDate toLocalDate() {
        return LocalDate.of(getYear() + 1900, getMonth() + 1, getDate());
    }

   /**
    * This method always throws an UnsupportedOperationException and should
    * not be used because SQL {@code Date} values do not have a time
    * component.
    *
    * <p>
    * 
    * @exception java.lang.UnsupportedOperationException if this method is invoked
    */
    @Override
    public Instant toInstant() {
        throw new java.lang.UnsupportedOperationException();
    }
}
