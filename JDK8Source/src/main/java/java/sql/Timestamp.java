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
import java.time.LocalDateTime;
import java.util.StringTokenizer;

/**
 * <P>A thin wrapper around <code>java.util.Date</code> that allows
 * the JDBC API to identify this as an SQL <code>TIMESTAMP</code> value.
 * It adds the ability
 * to hold the SQL <code>TIMESTAMP</code> fractional seconds value, by allowing
 * the specification of fractional seconds to a precision of nanoseconds.
 * A Timestamp also provides formatting and
 * parsing operations to support the JDBC escape syntax for timestamp values.
 *
 * <p>The precision of a Timestamp object is calculated to be either:
 * <ul>
 * <li><code>19 </code>, which is the number of characters in yyyy-mm-dd hh:mm:ss
 * <li> <code> 20 + s </code>, which is the number
 * of characters in the yyyy-mm-dd hh:mm:ss.[fff...] and <code>s</code> represents  the scale of the given Timestamp,
 * its fractional seconds precision.
 *</ul>
 *
 * <P><B>Note:</B> This type is a composite of a <code>java.util.Date</code> and a
 * separate nanoseconds value. Only integral seconds are stored in the
 * <code>java.util.Date</code> component. The fractional seconds - the nanos - are
 * separate.  The <code>Timestamp.equals(Object)</code> method never returns
 * <code>true</code> when passed an object
 * that isn't an instance of <code>java.sql.Timestamp</code>,
 * because the nanos component of a date is unknown.
 * As a result, the <code>Timestamp.equals(Object)</code>
 * method is not symmetric with respect to the
 * <code>java.util.Date.equals(Object)</code>
 * method.  Also, the <code>hashCode</code> method uses the underlying
 * <code>java.util.Date</code>
 * implementation and therefore does not include nanos in its computation.
 * <P>
 * Due to the differences between the <code>Timestamp</code> class
 * and the <code>java.util.Date</code>
 * class mentioned above, it is recommended that code not view
 * <code>Timestamp</code> values generically as an instance of
 * <code>java.util.Date</code>.  The
 * inheritance relationship between <code>Timestamp</code>
 * and <code>java.util.Date</code> really
 * denotes implementation inheritance, and not type inheritance.
 * <p>
 *  <P> <code> java.util.Date </code>周围的一个薄包装,允许JDBC API将此标识为SQL <code> TIMESTAMP </code>值。
 * 它通过允许小数秒的指定精确到纳秒,增加了保存SQL <code> TIMESTAMP </code>小数秒值的能力。时间戳还提供格式化和解析操作以支持时间戳值的JDBC转义语法。
 * 
 *  <p>时间戳记对象的精度计算为：
 * <ul>
 *  <li> <code> 19 </code>,这是yyyy-mm-dd hh：mm：ss <li> <code> 20 + s </code>中的字符数, yyyy-mm-dd hh：mm：ss。
 * [fff ...]和<code> s </code>表示给定时间戳的比例,即其小数秒精度。
 * /ul>
 * 
 * <P> <B>注意：</B>此类型是一个<code> java.util.Date </code>和一个单独的纳秒值的复合。
 * 只有整数秒存储在<code> java.util.Date </code>组件中。分数秒 - 纳秒 - 是分开的。
 * 当传递不是<code> java.sql.Timestamp </code>的实例的对象时,<code> Timestamp.equals(Object)</code>方法不会返回<code> true
 *  </code>,因为日期的nanos分量是未知的。
 * 只有整数秒存储在<code> java.util.Date </code>组件中。分数秒 - 纳秒 - 是分开的。
 * 因此,<code> Timestamp.equals(Object)</code>方法对于<code> java.util.Date.equals(Object)</code>方法不是对称的。
 * 此外,<code> hashCode </code>方法使用底层的<code> java.util.Date </code>实现,因此在其计算中不包括nanos。
 * <P>
 *  由于上述<code> Timestamp </code>类和<code> java.util.Date </code>类之间的差异,建议代码不能一般查看<code> Timestamp </code>
 * 作为<code> java.util.Date </code>的实例。
 *  <code> Timestamp </code>和<code> java.util.Date </code>之间的继承关系真正表示实现继承,而不是类型继承。
 * 
 */
public class Timestamp extends java.util.Date {

    /**
     * Constructs a <code>Timestamp</code> object initialized
     * with the given values.
     *
     * <p>
     *  构造使用给定值初始化的<code> Timestamp </code>对象。
     * 
     * 
     * @param year the year minus 1900
     * @param month 0 to 11
     * @param date 1 to 31
     * @param hour 0 to 23
     * @param minute 0 to 59
     * @param second 0 to 59
     * @param nano 0 to 999,999,999
     * @deprecated instead use the constructor <code>Timestamp(long millis)</code>
     * @exception IllegalArgumentException if the nano argument is out of bounds
     */
    @Deprecated
    public Timestamp(int year, int month, int date,
                     int hour, int minute, int second, int nano) {
        super(year, month, date, hour, minute, second);
        if (nano > 999999999 || nano < 0) {
            throw new IllegalArgumentException("nanos > 999999999 or < 0");
        }
        nanos = nano;
    }

    /**
     * Constructs a <code>Timestamp</code> object
     * using a milliseconds time value. The
     * integral seconds are stored in the underlying date value; the
     * fractional seconds are stored in the <code>nanos</code> field of
     * the <code>Timestamp</code> object.
     *
     * <p>
     *  使用毫秒时间值构造一个<code> Timestamp </code>对象。
     * 积分秒存储在基础日期值中;小数秒存储在<code> Timestamp </code>对象的<code> nanos </code>字段中。
     * 
     * 
     * @param time milliseconds since January 1, 1970, 00:00:00 GMT.
     *        A negative number is the number of milliseconds before
     *         January 1, 1970, 00:00:00 GMT.
     * @see java.util.Calendar
     */
    public Timestamp(long time) {
        super((time/1000)*1000);
        nanos = (int)((time%1000) * 1000000);
        if (nanos < 0) {
            nanos = 1000000000 + nanos;
            super.setTime(((time/1000)-1)*1000);
        }
    }

    /**
     * Sets this <code>Timestamp</code> object to represent a point in time that is
     * <tt>time</tt> milliseconds after January 1, 1970 00:00:00 GMT.
     *
     * <p>
     * 设置此<code> Timestamp </code>对象以表示1970年1月1日00:00:00 GMT后<tt>时</tt>毫秒的时间点。
     * 
     * 
     * @param time   the number of milliseconds.
     * @see #getTime
     * @see #Timestamp(long time)
     * @see java.util.Calendar
     */
    public void setTime(long time) {
        super.setTime((time/1000)*1000);
        nanos = (int)((time%1000) * 1000000);
        if (nanos < 0) {
            nanos = 1000000000 + nanos;
            super.setTime(((time/1000)-1)*1000);
        }
    }

    /**
     * Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT
     * represented by this <code>Timestamp</code> object.
     *
     * <p>
     *  返回自<code> Timestamp </code>对象表示的1970年1月1日,00:00:00 GMT以来的毫秒数。
     * 
     * 
     * @return  the number of milliseconds since January 1, 1970, 00:00:00 GMT
     *          represented by this date.
     * @see #setTime
     */
    public long getTime() {
        long time = super.getTime();
        return (time + (nanos / 1000000));
    }


    /**
    /* <p>
    /* 
     * @serial
     */
    private int nanos;

    /**
     * Converts a <code>String</code> object in JDBC timestamp escape format to a
     * <code>Timestamp</code> value.
     *
     * <p>
     *  将JDBC时间戳转义格式中的<code> String </code>对象转换为<code> Timestamp </code>值。
     * 
     * 
     * @param s timestamp in format <code>yyyy-[m]m-[d]d hh:mm:ss[.f...]</code>.  The
     * fractional seconds may be omitted. The leading zero for <code>mm</code>
     * and <code>dd</code> may also be omitted.
     *
     * @return corresponding <code>Timestamp</code> value
     * @exception java.lang.IllegalArgumentException if the given argument
     * does not have the format <code>yyyy-[m]m-[d]d hh:mm:ss[.f...]</code>
     */
    public static Timestamp valueOf(String s) {
        final int YEAR_LENGTH = 4;
        final int MONTH_LENGTH = 2;
        final int DAY_LENGTH = 2;
        final int MAX_MONTH = 12;
        final int MAX_DAY = 31;
        String date_s;
        String time_s;
        String nanos_s;
        int year = 0;
        int month = 0;
        int day = 0;
        int hour;
        int minute;
        int second;
        int a_nanos = 0;
        int firstDash;
        int secondDash;
        int dividingSpace;
        int firstColon = 0;
        int secondColon = 0;
        int period = 0;
        String formatError = "Timestamp format must be yyyy-mm-dd hh:mm:ss[.fffffffff]";
        String zeros = "000000000";
        String delimiterDate = "-";
        String delimiterTime = ":";

        if (s == null) throw new java.lang.IllegalArgumentException("null string");

        // Split the string into date and time components
        s = s.trim();
        dividingSpace = s.indexOf(' ');
        if (dividingSpace > 0) {
            date_s = s.substring(0,dividingSpace);
            time_s = s.substring(dividingSpace+1);
        } else {
            throw new java.lang.IllegalArgumentException(formatError);
        }

        // Parse the date
        firstDash = date_s.indexOf('-');
        secondDash = date_s.indexOf('-', firstDash+1);

        // Parse the time
        if (time_s == null)
            throw new java.lang.IllegalArgumentException(formatError);
        firstColon = time_s.indexOf(':');
        secondColon = time_s.indexOf(':', firstColon+1);
        period = time_s.indexOf('.', secondColon+1);

        // Convert the date
        boolean parsedDate = false;
        if ((firstDash > 0) && (secondDash > 0) && (secondDash < date_s.length() - 1)) {
            String yyyy = date_s.substring(0, firstDash);
            String mm = date_s.substring(firstDash + 1, secondDash);
            String dd = date_s.substring(secondDash + 1);
            if (yyyy.length() == YEAR_LENGTH &&
                    (mm.length() >= 1 && mm.length() <= MONTH_LENGTH) &&
                    (dd.length() >= 1 && dd.length() <= DAY_LENGTH)) {
                 year = Integer.parseInt(yyyy);
                 month = Integer.parseInt(mm);
                 day = Integer.parseInt(dd);

                if ((month >= 1 && month <= MAX_MONTH) && (day >= 1 && day <= MAX_DAY)) {
                    parsedDate = true;
                }
            }
        }
        if (! parsedDate) {
            throw new java.lang.IllegalArgumentException(formatError);
        }

        // Convert the time; default missing nanos
        if ((firstColon > 0) & (secondColon > 0) &
            (secondColon < time_s.length()-1)) {
            hour = Integer.parseInt(time_s.substring(0, firstColon));
            minute =
                Integer.parseInt(time_s.substring(firstColon+1, secondColon));
            if ((period > 0) & (period < time_s.length()-1)) {
                second =
                    Integer.parseInt(time_s.substring(secondColon+1, period));
                nanos_s = time_s.substring(period+1);
                if (nanos_s.length() > 9)
                    throw new java.lang.IllegalArgumentException(formatError);
                if (!Character.isDigit(nanos_s.charAt(0)))
                    throw new java.lang.IllegalArgumentException(formatError);
                nanos_s = nanos_s + zeros.substring(0,9-nanos_s.length());
                a_nanos = Integer.parseInt(nanos_s);
            } else if (period > 0) {
                throw new java.lang.IllegalArgumentException(formatError);
            } else {
                second = Integer.parseInt(time_s.substring(secondColon+1));
            }
        } else {
            throw new java.lang.IllegalArgumentException(formatError);
        }

        return new Timestamp(year - 1900, month - 1, day, hour, minute, second, a_nanos);
    }

    /**
     * Formats a timestamp in JDBC timestamp escape format.
     *         <code>yyyy-mm-dd hh:mm:ss.fffffffff</code>,
     * where <code>ffffffffff</code> indicates nanoseconds.
     * <P>
     * <p>
     *  在JDBC时间戳转义格式中格式化时间戳。 <code> yyyy-mm-dd hh：mm：ss.fffffffff </code>,其中<code> ffffffffff </code>表示纳秒。
     * <P>
     * 
     * @return a <code>String</code> object in
     *           <code>yyyy-mm-dd hh:mm:ss.fffffffff</code> format
     */
    @SuppressWarnings("deprecation")
    public String toString () {

        int year = super.getYear() + 1900;
        int month = super.getMonth() + 1;
        int day = super.getDate();
        int hour = super.getHours();
        int minute = super.getMinutes();
        int second = super.getSeconds();
        String yearString;
        String monthString;
        String dayString;
        String hourString;
        String minuteString;
        String secondString;
        String nanosString;
        String zeros = "000000000";
        String yearZeros = "0000";
        StringBuffer timestampBuf;

        if (year < 1000) {
            // Add leading zeros
            yearString = "" + year;
            yearString = yearZeros.substring(0, (4-yearString.length())) +
                yearString;
        } else {
            yearString = "" + year;
        }
        if (month < 10) {
            monthString = "0" + month;
        } else {
            monthString = Integer.toString(month);
        }
        if (day < 10) {
            dayString = "0" + day;
        } else {
            dayString = Integer.toString(day);
        }
        if (hour < 10) {
            hourString = "0" + hour;
        } else {
            hourString = Integer.toString(hour);
        }
        if (minute < 10) {
            minuteString = "0" + minute;
        } else {
            minuteString = Integer.toString(minute);
        }
        if (second < 10) {
            secondString = "0" + second;
        } else {
            secondString = Integer.toString(second);
        }
        if (nanos == 0) {
            nanosString = "0";
        } else {
            nanosString = Integer.toString(nanos);

            // Add leading zeros
            nanosString = zeros.substring(0, (9-nanosString.length())) +
                nanosString;

            // Truncate trailing zeros
            char[] nanosChar = new char[nanosString.length()];
            nanosString.getChars(0, nanosString.length(), nanosChar, 0);
            int truncIndex = 8;
            while (nanosChar[truncIndex] == '0') {
                truncIndex--;
            }

            nanosString = new String(nanosChar, 0, truncIndex + 1);
        }

        // do a string buffer here instead.
        timestampBuf = new StringBuffer(20+nanosString.length());
        timestampBuf.append(yearString);
        timestampBuf.append("-");
        timestampBuf.append(monthString);
        timestampBuf.append("-");
        timestampBuf.append(dayString);
        timestampBuf.append(" ");
        timestampBuf.append(hourString);
        timestampBuf.append(":");
        timestampBuf.append(minuteString);
        timestampBuf.append(":");
        timestampBuf.append(secondString);
        timestampBuf.append(".");
        timestampBuf.append(nanosString);

        return (timestampBuf.toString());
    }

    /**
     * Gets this <code>Timestamp</code> object's <code>nanos</code> value.
     *
     * <p>
     *  获取此<code>时间戳</code>对象的<code> nanos </code>值。
     * 
     * 
     * @return this <code>Timestamp</code> object's fractional seconds component
     * @see #setNanos
     */
    public int getNanos() {
        return nanos;
    }

    /**
     * Sets this <code>Timestamp</code> object's <code>nanos</code> field
     * to the given value.
     *
     * <p>
     *  将此<code> Timestamp </code>对象的<code> nanos </code>字段设置为给定值。
     * 
     * 
     * @param n the new fractional seconds component
     * @exception java.lang.IllegalArgumentException if the given argument
     *            is greater than 999999999 or less than 0
     * @see #getNanos
     */
    public void setNanos(int n) {
        if (n > 999999999 || n < 0) {
            throw new IllegalArgumentException("nanos > 999999999 or < 0");
        }
        nanos = n;
    }

    /**
     * Tests to see if this <code>Timestamp</code> object is
     * equal to the given <code>Timestamp</code> object.
     *
     * <p>
     *  测试此<code> Timestamp </code>对象是否等于给定的<code> Timestamp </code>对象。
     * 
     * 
     * @param ts the <code>Timestamp</code> value to compare with
     * @return <code>true</code> if the given <code>Timestamp</code>
     *         object is equal to this <code>Timestamp</code> object;
     *         <code>false</code> otherwise
     */
    public boolean equals(Timestamp ts) {
        if (super.equals(ts)) {
            if  (nanos == ts.nanos) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Tests to see if this <code>Timestamp</code> object is
     * equal to the given object.
     *
     * This version of the method <code>equals</code> has been added
     * to fix the incorrect
     * signature of <code>Timestamp.equals(Timestamp)</code> and to preserve backward
     * compatibility with existing class files.
     *
     * Note: This method is not symmetric with respect to the
     * <code>equals(Object)</code> method in the base class.
     *
     * <p>
     *  测试这个<code> Timestamp </code>对象是否等于给定的对象。
     * 
     *  已添加此版本的方法<code> equals </code>以修复<code> Timestamp.equals(Timestamp)</code>的不正确的签名,并保留与现有类文件的向后兼容性。
     * 
     *  注意：此方法对于基类中的<code> equals(Object)</code>方法不是对称的。
     * 
     * 
     * @param ts the <code>Object</code> value to compare with
     * @return <code>true</code> if the given <code>Object</code> is an instance
     *         of a <code>Timestamp</code> that
     *         is equal to this <code>Timestamp</code> object;
     *         <code>false</code> otherwise
     */
    public boolean equals(java.lang.Object ts) {
      if (ts instanceof Timestamp) {
        return this.equals((Timestamp)ts);
      } else {
        return false;
      }
    }

    /**
     * Indicates whether this <code>Timestamp</code> object is
     * earlier than the given <code>Timestamp</code> object.
     *
     * <p>
     *  指示此<code> Timestamp </code>对象是否早于给定的<code> Timestamp </code>对象。
     * 
     * 
     * @param ts the <code>Timestamp</code> value to compare with
     * @return <code>true</code> if this <code>Timestamp</code> object is earlier;
     *        <code>false</code> otherwise
     */
    public boolean before(Timestamp ts) {
        return compareTo(ts) < 0;
    }

    /**
     * Indicates whether this <code>Timestamp</code> object is
     * later than the given <code>Timestamp</code> object.
     *
     * <p>
     *  指示此<code> Timestamp </code>对象是否晚于给定的<code> Timestamp </code>对象。
     * 
     * 
     * @param ts the <code>Timestamp</code> value to compare with
     * @return <code>true</code> if this <code>Timestamp</code> object is later;
     *        <code>false</code> otherwise
     */
    public boolean after(Timestamp ts) {
        return compareTo(ts) > 0;
    }

    /**
     * Compares this <code>Timestamp</code> object to the given
     * <code>Timestamp</code> object.
     *
     * <p>
     *  将此<code>时间戳</code>对象与给定的<code> Timestamp </code>对象进行比较。
     * 
     * 
     * @param   ts   the <code>Timestamp</code> object to be compared to
     *                this <code>Timestamp</code> object
     * @return  the value <code>0</code> if the two <code>Timestamp</code>
     *          objects are equal; a value less than <code>0</code> if this
     *          <code>Timestamp</code> object is before the given argument;
     *          and a value greater than <code>0</code> if this
     *          <code>Timestamp</code> object is after the given argument.
     * @since   1.4
     */
    public int compareTo(Timestamp ts) {
        long thisTime = this.getTime();
        long anotherTime = ts.getTime();
        int i = (thisTime<anotherTime ? -1 :(thisTime==anotherTime?0 :1));
        if (i == 0) {
            if (nanos > ts.nanos) {
                    return 1;
            } else if (nanos < ts.nanos) {
                return -1;
            }
        }
        return i;
    }

    /**
     * Compares this <code>Timestamp</code> object to the given
     * <code>Date</code> object.
     *
     * <p>
     * 将此<code> Timestamp </code>对象与给定的<code> Date </code>对象进行比较。
     * 
     * 
     * @param o the <code>Date</code> to be compared to
     *          this <code>Timestamp</code> object
     * @return  the value <code>0</code> if this <code>Timestamp</code> object
     *          and the given object are equal; a value less than <code>0</code>
     *          if this  <code>Timestamp</code> object is before the given argument;
     *          and a value greater than <code>0</code> if this
     *          <code>Timestamp</code> object is after the given argument.
     *
     * @since   1.5
     */
    public int compareTo(java.util.Date o) {
       if(o instanceof Timestamp) {
            // When Timestamp instance compare it with a Timestamp
            // Hence it is basically calling this.compareTo((Timestamp))o);
            // Note typecasting is safe because o is instance of Timestamp
           return compareTo((Timestamp)o);
      } else {
            // When Date doing a o.compareTo(this)
            // will give wrong results.
          Timestamp ts = new Timestamp(o.getTime());
          return this.compareTo(ts);
      }
    }

    /**
     * {@inheritDoc}
     *
     * The {@code hashCode} method uses the underlying {@code java.util.Date}
     * implementation and therefore does not include nanos in its computation.
     *
     * <p>
     *  {@inheritDoc}
     * 
     *  {@code hashCode}方法使用底层的{@code java.util.Date}实现,因此在其计算中不包括nanos。
     * 
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    static final long serialVersionUID = 2745179027874758501L;

    private static final int MILLIS_PER_SECOND = 1000;

    /**
     * Obtains an instance of {@code Timestamp} from a {@code LocalDateTime}
     * object, with the same year, month, day of month, hours, minutes,
     * seconds and nanos date-time value as the provided {@code LocalDateTime}.
     * <p>
     * The provided {@code LocalDateTime} is interpreted as the local
     * date-time in the local time zone.
     *
     * <p>
     *  从{@code LocalDateTime}对象中获取{@code LocalStateTime}对象的{@code LocalDateTime}对象的{@code LocalDateTime}实例,
     * 具有相同的年,月,日,时,分,秒和纳秒日期时间值。
     * <p>
     *  所提供的{@code LocalDateTime}被解释为本地时区中的本地日期时间。
     * 
     * 
     * @param dateTime a {@code LocalDateTime} to convert
     * @return a {@code Timestamp} object
     * @exception NullPointerException if {@code dateTime} is null.
     * @since 1.8
     */
    @SuppressWarnings("deprecation")
    public static Timestamp valueOf(LocalDateTime dateTime) {
        return new Timestamp(dateTime.getYear() - 1900,
                             dateTime.getMonthValue() - 1,
                             dateTime.getDayOfMonth(),
                             dateTime.getHour(),
                             dateTime.getMinute(),
                             dateTime.getSecond(),
                             dateTime.getNano());
    }

    /**
     * Converts this {@code Timestamp} object to a {@code LocalDateTime}.
     * <p>
     * The conversion creates a {@code LocalDateTime} that represents the
     * same year, month, day of month, hours, minutes, seconds and nanos
     * date-time value as this {@code Timestamp} in the local time zone.
     *
     * <p>
     *  将此{@code Timestamp}对象转换为{@code LocalDateTime}。
     * <p>
     *  转换会创建一个{@code LocalDateTime},表示与当地时区的{@code Timestamp}相同的年,月,日,时,分,秒和纳秒日期时间值。
     * 
     * 
     * @return a {@code LocalDateTime} object representing the same date-time value
     * @since 1.8
     */
    @SuppressWarnings("deprecation")
    public LocalDateTime toLocalDateTime() {
        return LocalDateTime.of(getYear() + 1900,
                                getMonth() + 1,
                                getDate(),
                                getHours(),
                                getMinutes(),
                                getSeconds(),
                                getNanos());
    }

    /**
     * Obtains an instance of {@code Timestamp} from an {@link Instant} object.
     * <p>
     * {@code Instant} can store points on the time-line further in the future
     * and further in the past than {@code Date}. In this scenario, this method
     * will throw an exception.
     *
     * <p>
     *  从{@link Instant}对象获取{@code Timestamp}的实例。
     * <p>
     *  {@code Instant}可以在未来进一步在时间轴上存储点,而且比{@code Date}过去的点更多。在这种情况下,此方法将抛出异常。
     * 
     * 
     * @param instant  the instant to convert
     * @return an {@code Timestamp} representing the same point on the time-line as
     *  the provided instant
     * @exception NullPointerException if {@code instant} is null.
     * @exception IllegalArgumentException if the instant is too large to
     *  represent as a {@code Timesamp}
     * @since 1.8
     */
    public static Timestamp from(Instant instant) {
        try {
            Timestamp stamp = new Timestamp(instant.getEpochSecond() * MILLIS_PER_SECOND);
            stamp.nanos = instant.getNano();
            return stamp;
        } catch (ArithmeticException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * Converts this {@code Timestamp} object to an {@code Instant}.
     * <p>
     * The conversion creates an {@code Instant} that represents the same
     * point on the time-line as this {@code Timestamp}.
     *
     * <p>
     *  将此{@code Timestamp}对象转换为{@code Instant}。
     * <p>
     * 
     * @return an instant representing the same point on the time-line
     * @since 1.8
     */
    @Override
    public Instant toInstant() {
        return Instant.ofEpochSecond(super.getTime() / MILLIS_PER_SECOND, nanos);
    }
}
