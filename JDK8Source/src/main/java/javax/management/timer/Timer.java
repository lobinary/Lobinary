/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2012, Oracle and/or its affiliates. All rights reserved.
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

package javax.management.timer;

import static com.sun.jmx.defaults.JmxProperties.TIMER_LOGGER;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.logging.Level;

// jmx imports
//
import javax.management.InstanceNotFoundException;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanRegistration;
import javax.management.MBeanServer;
import javax.management.NotificationBroadcasterSupport;
import javax.management.ObjectName;

/**
 *
 * Provides the implementation of the timer MBean.
 * The timer MBean sends out an alarm at a specified time
 * that wakes up all the listeners registered to receive timer notifications.
 * <P>
 *
 * This class manages a list of dated timer notifications.
 * A method allows users to add/remove as many notifications as required.
 * When a timer notification is emitted by the timer and becomes obsolete,
 * it is automatically removed from the list of timer notifications.
 * <BR>Additional timer notifications can be added into regularly repeating notifications.
 * <P>
 *
 * Note:
 * <OL>
 * <LI>When sending timer notifications, the timer updates the notification sequence number
 * irrespective of the notification type.
 * <LI>The timer service relies on the system date of the host where the <CODE>Timer</CODE> class is loaded.
 * Listeners may receive untimely notifications
 * if their host has a different system date.
 * To avoid such problems, synchronize the system date of all host machines where timing is needed.
 * <LI>The default behavior for periodic notifications is <i>fixed-delay execution</i>, as
 *     specified in {@link java.util.Timer}. In order to use <i>fixed-rate execution</i>, use the
 *     overloaded {@link #addNotification(String, String, Object, Date, long, long, boolean)} method.
 * <LI>Notification listeners are potentially all executed in the same
 * thread.  Therefore, they should execute rapidly to avoid holding up
 * other listeners or perturbing the regularity of fixed-delay
 * executions.  See {@link NotificationBroadcasterSupport}.
 * </OL>
 *
 * <p>
 *  提供定时器MBean的实现。定时器MBean在指定时间发出警报,唤醒所有已注册的接收器以接收定时器通知。
 * <P>
 * 
 *  此类管理日期计时器通知的列表。方法允许用户根据需要添加/删除尽可能多的通知。当定时器发出定时器通知并变为过时,它会自动从定时器通知列表中删除。 <BR>额外的计时器通知可以添加到定期重复的通知中。
 * <P>
 * 
 *  注意：
 * <OL>
 * <LI>当发送定时器通知时,定时器更新通知序列号,而不考虑通知类型。 <LI>定时器服务依赖于加载<CODE> Timer </CODE>类的主机的系统日期。
 * 如果主持人具有不同的系统日期,则监听器可能会收到不及时的通知。为避免此类问题,请同步所有需要计时的主机计算机的系统日期。
 *  <LI>定期通知的默认行为是<i>固定延迟执行</i>,如{@link java.util.Timer}中所指定。
 * 为了使用<i>固定速率执行</i>,请使用重载的{@link #addNotification(String,String,Object,Date,long,long,boolean)}方法。
 *  <LI>通知侦听器可能都在同一个线程中执行。因此,他们应该快速执行,以避免阻止其他监听器或扰乱固定延迟执行的规律性。
 * 请参阅{@link NotificationBroadcasterSupport}。
 * </OL>
 * 
 * 
 * @since 1.5
 */
public class Timer extends NotificationBroadcasterSupport
        implements TimerMBean, MBeanRegistration {


    /*
     * ------------------------------------------
     *  PUBLIC VARIABLES
     * ------------------------------------------
     * <p>
     *  ------------------------------------------公共变量------ ------------------------------------
     * 
     */

    /**
     * Number of milliseconds in one second.
     * Useful constant for the <CODE>addNotification</CODE> method.
     * <p>
     *  以秒为单位的毫秒数。 <CODE> addNotification </CODE>方法的有用常数。
     * 
     */
    public static final long ONE_SECOND = 1000;

    /**
     * Number of milliseconds in one minute.
     * Useful constant for the <CODE>addNotification</CODE> method.
     * <p>
     *  一分钟内的毫秒数。 <CODE> addNotification </CODE>方法的有用常数。
     * 
     */
    public static final long ONE_MINUTE = 60*ONE_SECOND;

    /**
     * Number of milliseconds in one hour.
     * Useful constant for the <CODE>addNotification</CODE> method.
     * <p>
     *  以一小时为单位的毫秒数。 <CODE> addNotification </CODE>方法的有用常数。
     * 
     */
    public static final long ONE_HOUR   = 60*ONE_MINUTE;

    /**
     * Number of milliseconds in one day.
     * Useful constant for the <CODE>addNotification</CODE> method.
     * <p>
     *  一天中的毫秒数。 <CODE> addNotification </CODE>方法的有用常数。
     * 
     */
    public static final long ONE_DAY    = 24*ONE_HOUR;

    /**
     * Number of milliseconds in one week.
     * Useful constant for the <CODE>addNotification</CODE> method.
     * <p>
     * 一周的毫秒数。 <CODE> addNotification </CODE>方法的有用常数。
     * 
     */
    public static final long ONE_WEEK   = 7*ONE_DAY;

    /*
     * ------------------------------------------
     *  PRIVATE VARIABLES
     * ------------------------------------------
     * <p>
     *  ------------------------------------------私人变数------ ------------------------------------
     * 
     */

    /**
     * Table containing all the timer notifications of this timer,
     * with the associated date, period and number of occurrences.
     * <p>
     *  包含此计时器的所有计时器通知的表,以及相关的日期,周期和发生次数。
     * 
     */
    final private Map<Integer,Object[]> timerTable =
        new HashMap<>();

    /**
     * Past notifications sending on/off flag value.
     * This attribute is used to specify if the timer has to send past notifications after start.
     * <BR>The default value is set to <CODE>false</CODE>.
     * <p>
     *  过去通知发送开/关标志值。此属性用于指定计时器是否必须在启动后发送过去的通知。 <BR>默认值设置为<CODE> false </CODE>。
     * 
     */
    private boolean sendPastNotifications = false;

    /**
     * Timer state.
     * The default value is set to <CODE>false</CODE>.
     * <p>
     *  定时器状态。默认值设置为<CODE> false </CODE>。
     * 
     */
    private transient boolean isActive = false;

    /**
     * Timer sequence number.
     * The default value is set to 0.
     * <p>
     *  定时器序列号。默认值设置为0。
     * 
     */
    private transient long sequenceNumber = 0;

    // Flags needed to keep the indexes of the objects in the array.
    //
    private static final int TIMER_NOTIF_INDEX     = 0;
    private static final int TIMER_DATE_INDEX      = 1;
    private static final int TIMER_PERIOD_INDEX    = 2;
    private static final int TIMER_NB_OCCUR_INDEX  = 3;
    private static final int ALARM_CLOCK_INDEX     = 4;
    private static final int FIXED_RATE_INDEX      = 5;

    /**
     * The notification counter ID.
     * Used to keep the max key value inserted into the timer table.
     * <p>
     *  通知计数器ID。用于保持最大键值插入定时器表。
     * 
     */
    volatile private int counterID = 0;

    private java.util.Timer timer;

    /*
     * ------------------------------------------
     *  CONSTRUCTORS
     * ------------------------------------------
     * <p>
     *  ------------------------------------------建筑师------- -----------------------------------
     * 
     */

    /**
     * Default constructor.
     * <p>
     *  默认构造函数。
     * 
     */
    public Timer() {
    }

    /*
     * ------------------------------------------
     *  PUBLIC METHODS
     * ------------------------------------------
     * <p>
     *  ------------------------------------------公共方法------------------------------------
     * 
     */

    /**
     * Allows the timer MBean to perform any operations it needs before being registered
     * in the MBean server.
     * <P>
     * Not used in this context.
     *
     * <p>
     *  允许定时器MBean在注册到MBean服务器之前执行其所需的任何操作。
     * <P>
     *  在此上下文中未使用。
     * 
     * 
     * @param server The MBean server in which the timer MBean will be registered.
     * @param name The object name of the timer MBean.
     *
     * @return The name of the timer MBean registered.
     *
     * @exception java.lang.Exception
     */
    public ObjectName preRegister(MBeanServer server, ObjectName name)
        throws java.lang.Exception {
        return name;
    }

    /**
     * Allows the timer MBean to perform any operations needed after having been
     * registered in the MBean server or after the registration has failed.
     * <P>
     * Not used in this context.
     * <p>
     *  允许定时器MBean在已在MBean服务器中注册或注册失败后执行所需的任何操作。
     * <P>
     *  在此上下文中未使用。
     * 
     */
    public void postRegister (Boolean registrationDone) {
    }

    /**
     * Allows the timer MBean to perform any operations it needs before being unregistered
     * by the MBean server.
     * <P>
     * Stops the timer.
     *
     * <p>
     *  允许定时器MBean在MBean服务器取消注册之前执行其所需的任何操作。
     * <P>
     *  停止定时器。
     * 
     * 
     * @exception java.lang.Exception
     */
    public void preDeregister() throws java.lang.Exception {

        TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(),
                "preDeregister", "stop the timer");

        // Stop the timer.
        //
        stop();
    }

    /**
     * Allows the timer MBean to perform any operations needed after having been
     * unregistered by the MBean server.
     * <P>
     * Not used in this context.
     * <p>
     *  允许定时器MBean在MBean服务器取消注册后执行所需的任何操作。
     * <P>
     * 在此上下文中未使用。
     * 
     */
    public void postDeregister() {
    }

    /*
     * This overrides the method in NotificationBroadcasterSupport.
     * Return the MBeanNotificationInfo[] array for this MBean.
     * The returned array has one element to indicate that the MBean
     * can emit TimerNotification.  The array of type strings
     * associated with this entry is a snapshot of the current types
     * that were given to addNotification.
     * <p>
     *  这将覆盖NotificationBroadcasterSupport中的方法。返回此MBean的MBeanNotificationInfo []数组。
     * 返回的数组有一个元素,表示MBean可以发出TimerNotification。与此条目关联的类型字符串数组是给予addNotification的当前类型的快照。
     * 
     */
    public synchronized MBeanNotificationInfo[] getNotificationInfo() {
        Set<String> notifTypes = new TreeSet<String>();
        for (Object[] entry : timerTable.values()) {
            TimerNotification notif = (TimerNotification)
                entry[TIMER_NOTIF_INDEX];
            notifTypes.add(notif.getType());
        }
        String[] notifTypesArray =
            notifTypes.toArray(new String[0]);
        return new MBeanNotificationInfo[] {
            new MBeanNotificationInfo(notifTypesArray,
                                      TimerNotification.class.getName(),
                                      "Notification sent by Timer MBean")
        };
    }

    /**
     * Starts the timer.
     * <P>
     * If there is one or more timer notifications before the time in the list of notifications, the notification
     * is sent according to the <CODE>sendPastNotifications</CODE> flag and then, updated
     * according to its period and remaining number of occurrences.
     * If the timer notification date remains earlier than the current date, this notification is just removed
     * from the list of notifications.
     * <p>
     *  启动定时器。
     * <P>
     *  如果在通知列表中的时间之前存在一个或多个定时器通知,则根据<CODE> sendPastNotifications </CODE>标志发送通知,然后根据其周期和剩余出现次数来更新通知。
     * 如果计时器通知日期早于当前日期,则此通知将从通知列表中删除。
     * 
     */
    public synchronized void start() {

        TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(),
                "start", "starting the timer");

        // Start the TimerAlarmClock.
        //
        if (isActive == false) {

            timer = new java.util.Timer();

            TimerAlarmClock alarmClock;
            Date date;

            Date currentDate = new Date();

            // Send or not past notifications depending on the flag.
            // Update the date and the number of occurrences of past notifications
            // to make them later than the current date.
            //
            sendPastNotifications(currentDate, sendPastNotifications);

            // Update and start all the TimerAlarmClocks.
            // Here, all the notifications in the timer table are later than the current date.
            //
            for (Object[] obj : timerTable.values()) {

                // Retrieve the date notification and the TimerAlarmClock.
                //
                date = (Date)obj[TIMER_DATE_INDEX];

                // Update all the TimerAlarmClock timeouts and start them.
                //
                boolean fixedRate = ((Boolean)obj[FIXED_RATE_INDEX]).booleanValue();
                if (fixedRate)
                {
                  alarmClock = new TimerAlarmClock(this, date);
                  obj[ALARM_CLOCK_INDEX] = (Object)alarmClock;
                  timer.schedule(alarmClock, alarmClock.next);
                }
                else
                {
                  alarmClock = new TimerAlarmClock(this, (date.getTime() - currentDate.getTime()));
                  obj[ALARM_CLOCK_INDEX] = (Object)alarmClock;
                  timer.schedule(alarmClock, alarmClock.timeout);
                }
            }

            // Set the state to ON.
            //
            isActive = true;

            TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(),
                    "start", "timer started");
        } else {
            TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(),
                    "start", "the timer is already activated");
        }
    }

    /**
     * Stops the timer.
     * <p>
     *  停止定时器。
     * 
     */
    public synchronized void stop() {

        TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(),
                "stop", "stopping the timer");

        // Stop the TimerAlarmClock.
        //
        if (isActive == true) {

            for (Object[] obj : timerTable.values()) {

                // Stop all the TimerAlarmClock.
                //
                TimerAlarmClock alarmClock = (TimerAlarmClock)obj[ALARM_CLOCK_INDEX];
                if (alarmClock != null) {
//                     alarmClock.interrupt();
//                     try {
//                         // Wait until the thread die.
//                         //
//                         alarmClock.join();
//                     } catch (InterruptedException ex) {
//                         // Ignore...
//                     }
//                     // Remove the reference on the TimerAlarmClock.
//                     //

                    alarmClock.cancel();
                }
            }

            timer.cancel();

            // Set the state to OFF.
            //
            isActive = false;

            TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(),
                    "stop", "timer stopped");
        } else {
            TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(),
                    "stop", "the timer is already deactivated");
        }
    }

    /**
     * Creates a new timer notification with the specified <CODE>type</CODE>, <CODE>message</CODE>
     * and <CODE>userData</CODE> and inserts it into the list of notifications with a given date,
     * period and number of occurrences.
     * <P>
     * If the timer notification to be inserted has a date that is before the current date,
     * the method behaves as if the specified date were the current date. <BR>
     * For once-off notifications, the notification is delivered immediately. <BR>
     * For periodic notifications, the first notification is delivered immediately and the
     * subsequent ones are spaced as specified by the period parameter.
     * <P>
     * Note that once the timer notification has been added into the list of notifications,
     * its associated date, period and number of occurrences cannot be updated.
     * <P>
     * In the case of a periodic notification, the value of parameter <i>fixedRate</i> is used to
     * specify the execution scheme, as specified in {@link java.util.Timer}.
     *
     * <p>
     *  使用指定的<CODE>类型</CODE>,<CODE>消息</CODE>和<CODE> userData </CODE>创建新的计时器通知,并将其插入具有给定日期,的出现。
     * <P>
     *  如果要插入的计时器通知的日期早于当前日期,则该方法的行为就像指定的日期是当前日期。 <BR>对于一次性通知,通知会立即传送。
     *  <BR>对于定期通知,第一个通知立即传递,后续的通知按照period参数指定的间隔。
     * <P>
     * 请注意,一旦计时器通知已添加到通知列表中,则无法更新其关联的日期,时间段和发生次数。
     * <P>
     *  在定期通知的情况下,参数<i> fixedRate </i>的值用于指定执行方案,如{@link java.util.Timer}中所指定。
     * 
     * 
     * @param type The timer notification type.
     * @param message The timer notification detailed message.
     * @param userData The timer notification user data object.
     * @param date The date when the notification occurs.
     * @param period The period of the timer notification (in milliseconds).
     * @param nbOccurences The total number the timer notification will be emitted.
     * @param fixedRate If <code>true</code> and if the notification is periodic, the notification
     *                  is scheduled with a <i>fixed-rate</i> execution scheme. If
     *                  <code>false</code> and if the notification is periodic, the notification
     *                  is scheduled with a <i>fixed-delay</i> execution scheme. Ignored if the
     *                  notification is not periodic.
     *
     * @return The identifier of the new created timer notification.
     *
     * @exception java.lang.IllegalArgumentException The date is {@code null} or
     * the period or the number of occurrences is negative.
     *
     * @see #addNotification(String, String, Object, Date, long, long)
     */
// NPCTE fix for bugId 4464388, esc 0,  MR, to be added after modification of jmx spec
//  public synchronized Integer addNotification(String type, String message, Serializable userData,
//                                                Date date, long period, long nbOccurences)
// end of NPCTE fix for bugId 4464388

    public synchronized Integer addNotification(String type, String message, Object userData,
                                                Date date, long period, long nbOccurences, boolean fixedRate)
        throws java.lang.IllegalArgumentException {

        if (date == null) {
            throw new java.lang.IllegalArgumentException("Timer notification date cannot be null.");
        }

        // Check that all the timer notification attributes are valid.
        //

        // Invalid timer period value exception:
        // Check that the period and the nbOccurences are POSITIVE VALUES.
        //
        if ((period < 0) || (nbOccurences < 0)) {
            throw new java.lang.IllegalArgumentException("Negative values for the periodicity");
        }

        Date currentDate = new Date();

        // Update the date if it is before the current date.
        //
        if (currentDate.after(date)) {

            date.setTime(currentDate.getTime());
            if (TIMER_LOGGER.isLoggable(Level.FINER)) {
                TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(),
                        "addNotification",
                        "update timer notification to add with:" +
                        "\n\tNotification date = " + date);
            }
        }

        // Create and add the timer notification into the timer table.
        //
        Integer notifID = Integer.valueOf(++counterID);

        // The sequenceNumber and the timeStamp attributes are updated
        // when the notification is emitted by the timer.
        //
        TimerNotification notif = new TimerNotification(type, this, 0, 0, message, notifID);
        notif.setUserData(userData);

        Object[] obj = new Object[6];

        TimerAlarmClock alarmClock;
        if (fixedRate)
        {
          alarmClock = new TimerAlarmClock(this, date);
        }
        else
        {
          alarmClock = new TimerAlarmClock(this, (date.getTime() - currentDate.getTime()));
        }

        // Fix bug 00417.B
        // The date registered into the timer is a clone from the date parameter.
        //
        Date d = new Date(date.getTime());

        obj[TIMER_NOTIF_INDEX] = (Object)notif;
        obj[TIMER_DATE_INDEX] = (Object)d;
        obj[TIMER_PERIOD_INDEX] = (Object) period;
        obj[TIMER_NB_OCCUR_INDEX] = (Object) nbOccurences;
        obj[ALARM_CLOCK_INDEX] = (Object)alarmClock;
        obj[FIXED_RATE_INDEX] = Boolean.valueOf(fixedRate);

        if (TIMER_LOGGER.isLoggable(Level.FINER)) {
            StringBuilder strb = new StringBuilder()
            .append("adding timer notification:\n\t")
            .append("Notification source = ")
            .append(notif.getSource())
            .append("\n\tNotification type = ")
            .append(notif.getType())
            .append("\n\tNotification ID = ")
            .append(notifID)
            .append("\n\tNotification date = ")
            .append(d)
            .append("\n\tNotification period = ")
            .append(period)
            .append("\n\tNotification nb of occurrences = ")
            .append(nbOccurences)
            .append("\n\tNotification executes at fixed rate = ")
            .append(fixedRate);
            TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(),
                    "addNotification", strb.toString());
        }

        timerTable.put(notifID, obj);

        // Update and start the TimerAlarmClock.
        //
        if (isActive == true) {
          if (fixedRate)
          {
            timer.schedule(alarmClock, alarmClock.next);
          }
          else
          {
            timer.schedule(alarmClock, alarmClock.timeout);
          }
        }

        TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(),
                "addNotification", "timer notification added");
        return notifID;
    }

    /**
     * Creates a new timer notification with the specified <CODE>type</CODE>, <CODE>message</CODE>
     * and <CODE>userData</CODE> and inserts it into the list of notifications with a given date,
     * period and number of occurrences.
     * <P>
     * If the timer notification to be inserted has a date that is before the current date,
     * the method behaves as if the specified date were the current date. <BR>
     * For once-off notifications, the notification is delivered immediately. <BR>
     * For periodic notifications, the first notification is delivered immediately and the
     * subsequent ones are spaced as specified by the period parameter.
     * <P>
     * Note that once the timer notification has been added into the list of notifications,
     * its associated date, period and number of occurrences cannot be updated.
     * <P>
     * In the case of a periodic notification, uses a <i>fixed-delay</i> execution scheme, as specified in
     * {@link java.util.Timer}. In order to use a <i>fixed-rate</i> execution scheme, use
     * {@link #addNotification(String, String, Object, Date, long, long, boolean)} instead.
     *
     * <p>
     *  使用指定的<CODE>类型</CODE>,<CODE>消息</CODE>和<CODE> userData </CODE>创建新的计时器通知,并将其插入具有给定日期,的出现。
     * <P>
     *  如果要插入的计时器通知的日期早于当前日期,则该方法的行为就像指定的日期是当前日期。 <BR>对于一次性通知,通知会立即传送。
     *  <BR>对于定期通知,第一个通知立即传递,后续的通知按照period参数指定的间隔。
     * <P>
     *  请注意,一旦计时器通知已添加到通知列表中,则无法更新其关联的日期,时间段和发生次数。
     * <P>
     *  在定期通知的情况下,使用<i> fixed-delay </i>执行方案,如{@link java.util.Timer}中所指定。
     * 为了使用<i>固定速率</i>执行方案,请改用{@link #addNotification(String,String,Object,Date,long,long,boolean)}。
     * 
     * 
     * @param type The timer notification type.
     * @param message The timer notification detailed message.
     * @param userData The timer notification user data object.
     * @param date The date when the notification occurs.
     * @param period The period of the timer notification (in milliseconds).
     * @param nbOccurences The total number the timer notification will be emitted.
     *
     * @return The identifier of the new created timer notification.
     *
     * @exception java.lang.IllegalArgumentException The date is {@code null} or
     * the period or the number of occurrences is negative.
     *
     * @see #addNotification(String, String, Object, Date, long, long, boolean)
     */
// NPCTE fix for bugId 4464388, esc 0,  MR , to be added after modification of jmx spec
//  public synchronized Integer addNotification(String type, String message, Serializable userData,
//                                              Date date, long period)
// end of NPCTE fix for bugId 4464388 */

    public synchronized Integer addNotification(String type, String message, Object userData,
                                                Date date, long period, long nbOccurences)
        throws java.lang.IllegalArgumentException {

      return addNotification(type, message, userData, date, period, nbOccurences, false);
    }

    /**
     * Creates a new timer notification with the specified <CODE>type</CODE>, <CODE>message</CODE>
     * and <CODE>userData</CODE> and inserts it into the list of notifications with a given date
     * and period and a null number of occurrences.
     * <P>
     * The timer notification will repeat continuously using the timer period using a <i>fixed-delay</i>
     * execution scheme, as specified in {@link java.util.Timer}. In order to use a <i>fixed-rate</i>
     * execution scheme, use {@link #addNotification(String, String, Object, Date, long, long,
     * boolean)} instead.
     * <P>
     * If the timer notification to be inserted has a date that is before the current date,
     * the method behaves as if the specified date were the current date. The
     * first notification is delivered immediately and the subsequent ones are
     * spaced as specified by the period parameter.
     *
     * <p>
     * 使用指定的<CODE>类型</CODE>,<CODE>消息</CODE>和<CODE> userData </CODE>创建新的计时器通知,并将其插入具有给定日期和时间的通知列表, null出现次数。
     * <P>
     *  定时器通知将使用固定延迟执行方案使用定时器周期连续地重复,如{@link java.util.Timer}中所规定。
     * 为了使用<i>固定速率</i>执行方案,请改用{@link #addNotification(String,String,Object,Date,long,long,boolean)}。
     * <P>
     *  如果要插入的计时器通知的日期早于当前日期,则该方法的行为就像指定的日期是当前日期。第一个通知立即传递,后续的通知按照周期参数指定的间隔。
     * 
     * 
     * @param type The timer notification type.
     * @param message The timer notification detailed message.
     * @param userData The timer notification user data object.
     * @param date The date when the notification occurs.
     * @param period The period of the timer notification (in milliseconds).
     *
     * @return The identifier of the new created timer notification.
     *
     * @exception java.lang.IllegalArgumentException The date is {@code null} or
     * the period is negative.
     */
// NPCTE fix for bugId 4464388, esc 0,  MR , to be added after modification of jmx spec
//  public synchronized Integer addNotification(String type, String message, Serializable userData,
//                                              Date date, long period)
// end of NPCTE fix for bugId 4464388 */

    public synchronized Integer addNotification(String type, String message, Object userData,
                                                Date date, long period)
        throws java.lang.IllegalArgumentException {

        return (addNotification(type, message, userData, date, period, 0));
    }

    /**
     * Creates a new timer notification with the specified <CODE>type</CODE>, <CODE>message</CODE>
     * and <CODE>userData</CODE> and inserts it into the list of notifications with a given date
     * and a null period and number of occurrences.
     * <P>
     * The timer notification will be handled once at the specified date.
     * <P>
     * If the timer notification to be inserted has a date that is before the current date,
     * the method behaves as if the specified date were the current date and the
     * notification is delivered immediately.
     *
     * <p>
     *  使用指定的<CODE>类型</CODE>,<CODE>消息</CODE>和<CODE> userData </CODE>创建新的计时器通知,并将其插入具有给定日期和空期的通知列表和出现次数。
     * <P>
     *  定时器通知将在指定日期处理一次。
     * <P>
     *  如果要插入的定时器通知的日期在当前日期之前,则该方法的行为就像指定的日期是当前日期,并且立即发送通知。
     * 
     * 
     * @param type The timer notification type.
     * @param message The timer notification detailed message.
     * @param userData The timer notification user data object.
     * @param date The date when the notification occurs.
     *
     * @return The identifier of the new created timer notification.
     *
     * @exception java.lang.IllegalArgumentException The date is {@code null}.
     */
// NPCTE fix for bugId 4464388, esc 0,  MR, to be added after modification of jmx spec
//  public synchronized Integer addNotification(String type, String message, Serializable userData, Date date)
//      throws java.lang.IllegalArgumentException {
// end of NPCTE fix for bugId 4464388

    public synchronized Integer addNotification(String type, String message, Object userData, Date date)
        throws java.lang.IllegalArgumentException {


        return (addNotification(type, message, userData, date, 0, 0));
    }

    /**
     * Removes the timer notification corresponding to the specified identifier from the list of notifications.
     *
     * <p>
     *  从通知列表中删除与指定标识符相对应的计时器通知。
     * 
     * 
     * @param id The timer notification identifier.
     *
     * @exception InstanceNotFoundException The specified identifier does not correspond to any timer notification
     * in the list of notifications of this timer MBean.
     */
    public synchronized void removeNotification(Integer id) throws InstanceNotFoundException {

        // Check that the notification to remove is effectively in the timer table.
        //
        if (timerTable.containsKey(id) == false) {
            throw new InstanceNotFoundException("Timer notification to remove not in the list of notifications");
        }

        // Stop the TimerAlarmClock.
        //
        Object[] obj = timerTable.get(id);
        TimerAlarmClock alarmClock = (TimerAlarmClock)obj[ALARM_CLOCK_INDEX];
        if (alarmClock != null) {
//             alarmClock.interrupt();
//             try {
//                 // Wait until the thread die.
//                 //
//                 alarmClock.join();
//             } catch (InterruptedException e) {
//                 // Ignore...
//             }
//             // Remove the reference on the TimerAlarmClock.
//             //
            alarmClock.cancel();
        }

        // Remove the timer notification from the timer table.
        //
        if (TIMER_LOGGER.isLoggable(Level.FINER)) {
            StringBuilder strb = new StringBuilder()
            .append("removing timer notification:")
            .append("\n\tNotification source = ")
            .append(((TimerNotification)obj[TIMER_NOTIF_INDEX]).getSource())
            .append("\n\tNotification type = ")
            .append(((TimerNotification)obj[TIMER_NOTIF_INDEX]).getType())
            .append("\n\tNotification ID = ")
            .append(((TimerNotification)obj[TIMER_NOTIF_INDEX]).getNotificationID())
            .append("\n\tNotification date = ")
            .append(obj[TIMER_DATE_INDEX])
            .append("\n\tNotification period = ")
            .append(obj[TIMER_PERIOD_INDEX])
            .append("\n\tNotification nb of occurrences = ")
            .append(obj[TIMER_NB_OCCUR_INDEX])
            .append("\n\tNotification executes at fixed rate = ")
            .append(obj[FIXED_RATE_INDEX]);
            TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(),
                    "removeNotification", strb.toString());
        }

        timerTable.remove(id);

        TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(),
                "removeNotification", "timer notification removed");
    }

    /**
     * Removes all the timer notifications corresponding to the specified type from the list of notifications.
     *
     * <p>
     * 从通知列表中删除与指定类型对应的所有定时器通知。
     * 
     * 
     * @param type The timer notification type.
     *
     * @exception InstanceNotFoundException The specified type does not correspond to any timer notification
     * in the list of notifications of this timer MBean.
     */
    public synchronized void removeNotifications(String type) throws InstanceNotFoundException {

        Vector<Integer> v = getNotificationIDs(type);

        if (v.isEmpty())
            throw new InstanceNotFoundException("Timer notifications to remove not in the list of notifications");

        for (Integer i : v)
            removeNotification(i);
    }

    /**
     * Removes all the timer notifications from the list of notifications
     * and resets the counter used to update the timer notification identifiers.
     * <p>
     *  从通知列表中删除所有计时器通知,并重置用于更新计时器通知标识符的计数器。
     * 
     */
    public synchronized void removeAllNotifications() {

        TimerAlarmClock alarmClock;

        for (Object[] obj : timerTable.values()) {

            // Stop the TimerAlarmClock.
            //
            alarmClock = (TimerAlarmClock)obj[ALARM_CLOCK_INDEX];
//             if (alarmClock != null) {
//                 alarmClock.interrupt();
//                 try {
//                     // Wait until the thread die.
//                     //
//                     alarmClock.join();
//                 } catch (InterruptedException ex) {
//                     // Ignore...
//                 }
                  // Remove the reference on the TimerAlarmClock.
                  //
//             }
            alarmClock.cancel();
        }

        // Remove all the timer notifications from the timer table.
        TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(),
                "removeAllNotifications", "removing all timer notifications");

        timerTable.clear();

        TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(),
                "removeAllNotifications", "all timer notifications removed");
        // Reset the counterID.
        //
        counterID = 0;

        TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(),
                "removeAllNotifications", "timer notification counter ID reset");
    }

    // GETTERS AND SETTERS
    //--------------------

    /**
     * Gets the number of timer notifications registered into the list of notifications.
     *
     * <p>
     *  获取注册到通知列表中的计时器通知数。
     * 
     * 
     * @return The number of timer notifications.
     */
    public synchronized int getNbNotifications() {
        return timerTable.size();
    }

    /**
     * Gets all timer notification identifiers registered into the list of notifications.
     *
     * <p>
     *  获取注册到通知列表中的所有计时器通知标识符。
     * 
     * 
     * @return A vector of <CODE>Integer</CODE> objects containing all the timer notification identifiers.
     * <BR>The vector is empty if there is no timer notification registered for this timer MBean.
     */
    public synchronized Vector<Integer> getAllNotificationIDs() {
        return new Vector<Integer>(timerTable.keySet());
    }

    /**
     * Gets all the identifiers of timer notifications corresponding to the specified type.
     *
     * <p>
     *  获取与指定类型对应的定时器通知的所有标识符。
     * 
     * 
     * @param type The timer notification type.
     *
     * @return A vector of <CODE>Integer</CODE> objects containing all the identifiers of
     * timer notifications with the specified <CODE>type</CODE>.
     * <BR>The vector is empty if there is no timer notifications registered for this timer MBean
     * with the specified <CODE>type</CODE>.
     */
    public synchronized Vector<Integer> getNotificationIDs(String type) {

        String s;

        Vector<Integer> v = new Vector<Integer>();

        for (Map.Entry<Integer,Object[]> entry : timerTable.entrySet()) {
            Object[] obj = entry.getValue();
            s = ((TimerNotification)obj[TIMER_NOTIF_INDEX]).getType();
            if ((type == null) ? s == null : type.equals(s))
                v.addElement(entry.getKey());
        }
        return v;
    }
    // 5089997: return is Vector<Integer> not Vector<TimerNotification>

    /**
     * Gets the timer notification type corresponding to the specified identifier.
     *
     * <p>
     *  获取与指定标识符相对应的定时器通知类型。
     * 
     * 
     * @param id The timer notification identifier.
     *
     * @return The timer notification type or null if the identifier is not mapped to any
     * timer notification registered for this timer MBean.
     */
    public synchronized String getNotificationType(Integer id) {

        Object[] obj = timerTable.get(id);
        if (obj != null) {
            return ( ((TimerNotification)obj[TIMER_NOTIF_INDEX]).getType() );
        }
        return null;
    }

    /**
     * Gets the timer notification detailed message corresponding to the specified identifier.
     *
     * <p>
     *  获取与指定标识符相对应的定时器通知详细消息。
     * 
     * 
     * @param id The timer notification identifier.
     *
     * @return The timer notification detailed message or null if the identifier is not mapped to any
     * timer notification registered for this timer MBean.
     */
    public synchronized String getNotificationMessage(Integer id) {

        Object[] obj = timerTable.get(id);
        if (obj != null) {
            return ( ((TimerNotification)obj[TIMER_NOTIF_INDEX]).getMessage() );
        }
        return null;
    }

    /**
     * Gets the timer notification user data object corresponding to the specified identifier.
     *
     * <p>
     *  获取与指定标识符相对应的定时器通知用户数据对象。
     * 
     * 
     * @param id The timer notification identifier.
     *
     * @return The timer notification user data object or null if the identifier is not mapped to any
     * timer notification registered for this timer MBean.
     */
    // NPCTE fix for bugId 4464388, esc 0, MR, 03 sept 2001, to be added after modification of jmx spec
    //public Serializable getNotificationUserData(Integer id) {
    // end of NPCTE fix for bugId 4464388

    public synchronized Object getNotificationUserData(Integer id) {
        Object[] obj = timerTable.get(id);
        if (obj != null) {
            return ( ((TimerNotification)obj[TIMER_NOTIF_INDEX]).getUserData() );
        }
        return null;
    }

    /**
     * Gets a copy of the date associated to a timer notification.
     *
     * <p>
     *  获取与计时器通知相关联的日期的副本。
     * 
     * 
     * @param id The timer notification identifier.
     *
     * @return A copy of the date or null if the identifier is not mapped to any
     * timer notification registered for this timer MBean.
     */
    public synchronized Date getDate(Integer id) {

        Object[] obj = timerTable.get(id);
        if (obj != null) {
            Date date = (Date)obj[TIMER_DATE_INDEX];
            return (new Date(date.getTime()));
        }
        return null;
    }

    /**
     * Gets a copy of the period (in milliseconds) associated to a timer notification.
     *
     * <p>
     *  获取与计时器通知相关联的时间段(以毫秒为单位)的副本。
     * 
     * 
     * @param id The timer notification identifier.
     *
     * @return A copy of the period or null if the identifier is not mapped to any
     * timer notification registered for this timer MBean.
     */
    public synchronized Long getPeriod(Integer id) {

        Object[] obj = timerTable.get(id);
        if (obj != null) {
            return (Long)obj[TIMER_PERIOD_INDEX];
        }
        return null;
    }

    /**
     * Gets a copy of the remaining number of occurrences associated to a timer notification.
     *
     * <p>
     *  获取与计时器通知相关联的剩余发生次数的副本。
     * 
     * 
     * @param id The timer notification identifier.
     *
     * @return A copy of the remaining number of occurrences or null if the identifier is not mapped to any
     * timer notification registered for this timer MBean.
     */
    public synchronized Long getNbOccurences(Integer id) {

        Object[] obj = timerTable.get(id);
        if (obj != null) {
            return (Long)obj[TIMER_NB_OCCUR_INDEX];
        }
        return null;
    }

    /**
     * Gets a copy of the flag indicating whether a periodic notification is
     * executed at <i>fixed-delay</i> or at <i>fixed-rate</i>.
     *
     * <p>
     *  获得指示是以固定延迟还是以固定速率</i>执行周期性通知的标志的副本。
     * 
     * 
     * @param id The timer notification identifier.
     *
     * @return A copy of the flag indicating whether a periodic notification is
     *         executed at <i>fixed-delay</i> or at <i>fixed-rate</i>.
     */
    public synchronized Boolean getFixedRate(Integer id) {

      Object[] obj = timerTable.get(id);
      if (obj != null) {
        Boolean fixedRate = (Boolean)obj[FIXED_RATE_INDEX];
        return (Boolean.valueOf(fixedRate.booleanValue()));
      }
      return null;
    }

    /**
     * Gets the flag indicating whether or not the timer sends past notifications.
     * <BR>The default value of the past notifications sending on/off flag is <CODE>false</CODE>.
     *
     * <p>
     *  获取指示定时器是否发送过去通知的标志。 <BR>过去通知发送开/关标志的默认值为<CODE> false </CODE>。
     * 
     * 
     * @return The past notifications sending on/off flag value.
     *
     * @see #setSendPastNotifications
     */
    public boolean getSendPastNotifications() {
        return sendPastNotifications;
    }

    /**
     * Sets the flag indicating whether the timer sends past notifications or not.
     * <BR>The default value of the past notifications sending on/off flag is <CODE>false</CODE>.
     *
     * <p>
     *  设置指示定时器是否发送过去通知的标志。 <BR>过去通知发送开/关标志的默认值为<CODE> false </CODE>。
     * 
     * 
     * @param value The past notifications sending on/off flag value.
     *
     * @see #getSendPastNotifications
     */
    public void setSendPastNotifications(boolean value) {
        sendPastNotifications = value;
    }

    /**
     * Tests whether the timer MBean is active.
     * A timer MBean is marked active when the {@link #start start} method is called.
     * It becomes inactive when the {@link #stop stop} method is called.
     * <BR>The default value of the active on/off flag is <CODE>false</CODE>.
     *
     * <p>
     * 测试定时器MBean是否活动。当调用{@link #start start}方法时,定时器MBean被标记为活动。当调用{@link #stop stop}方法时,它将变为无效。
     *  <BR>活动开/关标志的默认值为<CODE> false </CODE>。
     * 
     * 
     * @return <CODE>true</CODE> if the timer MBean is active, <CODE>false</CODE> otherwise.
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Tests whether the list of timer notifications is empty.
     *
     * <p>
     *  测试定时器通知的列表是否为空。
     * 
     * 
     * @return <CODE>true</CODE> if the list of timer notifications is empty, <CODE>false</CODE> otherwise.
     */
    public synchronized boolean isEmpty() {
        return (timerTable.isEmpty());
    }

    /*
     * ------------------------------------------
     *  PRIVATE METHODS
     * ------------------------------------------
     * <p>
     *  ------------------------------------------私有方法------ ------------------------------------
     * 
     */

    /**
     * Sends or not past notifications depending on the specified flag.
     *
     * <p>
     *  根据指定的标志发送或不发送通知。
     * 
     * 
     * @param currentDate The current date.
     * @param currentFlag The flag indicating if past notifications must be sent or not.
     */
    private synchronized void sendPastNotifications(Date currentDate, boolean currentFlag) {

        TimerNotification notif;
        Integer notifID;
        Date date;

        ArrayList<Object[]> values =
            new ArrayList<Object[]>(timerTable.values());

        for (Object[] obj : values) {

            // Retrieve the timer notification and the date notification.
            //
            notif = (TimerNotification)obj[TIMER_NOTIF_INDEX];
            notifID = notif.getNotificationID();
            date = (Date)obj[TIMER_DATE_INDEX];

            // Update the timer notification while:
            //  - the timer notification date is earlier than the current date
            //  - the timer notification has not been removed from the timer table.
            //
            while ( (currentDate.after(date)) && (timerTable.containsKey(notifID)) ) {

                if (currentFlag == true) {
                    if (TIMER_LOGGER.isLoggable(Level.FINER)) {
                        StringBuilder strb = new StringBuilder()
                        .append("sending past timer notification:")
                        .append("\n\tNotification source = ")
                        .append(notif.getSource())
                        .append("\n\tNotification type = ")
                        .append(notif.getType())
                        .append("\n\tNotification ID = ")
                        .append(notif.getNotificationID())
                        .append("\n\tNotification date = ")
                        .append(date)
                        .append("\n\tNotification period = ")
                        .append(obj[TIMER_PERIOD_INDEX])
                        .append("\n\tNotification nb of occurrences = ")
                        .append(obj[TIMER_NB_OCCUR_INDEX])
                        .append("\n\tNotification executes at fixed rate = ")
                        .append(obj[FIXED_RATE_INDEX]);
                        TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(),
                                "sendPastNotifications", strb.toString());
                    }
                    sendNotification(date, notif);

                    TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(),
                            "sendPastNotifications", "past timer notification sent");
                }

                // Update the date and the number of occurrences of the timer notification.
                //
                updateTimerTable(notif.getNotificationID());
            }
        }
    }

    /**
     * If the timer notification is not periodic, it is removed from the list of notifications.
     * <P>
     * If the timer period of the timer notification has a non null periodicity,
     * the date of the timer notification is updated by adding the periodicity.
     * The associated TimerAlarmClock is updated by setting its timeout to the period value.
     * <P>
     * If the timer period has a defined number of occurrences, the timer
     * notification is updated if the number of occurrences has not yet been reached.
     * Otherwise it is removed from the list of notifications.
     *
     * <p>
     *  如果定时器通知不是周期性的,则从通知列表中移除。
     * <P>
     *  如果定时器通知的定时器周期具有非零周期,则通过添加周期性来更新定时器通知的日期。相关的TimerAlarmClock通过将其超时设置为周期值来更新。
     * <P>
     *  如果定时器周期具有定义的出现次数,则如果尚未达到出现次数,则更新定时器通知。否则,它将从通知列表中删除。
     * 
     * 
     * @param notifID The timer notification identifier to update.
     */
    private synchronized void updateTimerTable(Integer notifID) {

        // Retrieve the timer notification and the TimerAlarmClock.
        //
        Object[] obj = timerTable.get(notifID);
        Date date = (Date)obj[TIMER_DATE_INDEX];
        Long period = (Long)obj[TIMER_PERIOD_INDEX];
        Long nbOccurences = (Long)obj[TIMER_NB_OCCUR_INDEX];
        Boolean fixedRate = (Boolean)obj[FIXED_RATE_INDEX];
        TimerAlarmClock alarmClock = (TimerAlarmClock)obj[ALARM_CLOCK_INDEX];

        if (period.longValue() != 0) {

            // Update the date and the number of occurrences of the timer notification
            // and the TimerAlarmClock time out.
            // NOTES :
            //   nbOccurences = 0 notifies an infinite periodicity.
            //   nbOccurences = 1 notifies a finite periodicity that has reached its end.
            //   nbOccurences > 1 notifies a finite periodicity that has not yet reached its end.
            //
            if ((nbOccurences.longValue() == 0) || (nbOccurences.longValue() > 1)) {

                date.setTime(date.getTime() + period.longValue());
                obj[TIMER_NB_OCCUR_INDEX] = Long.valueOf(java.lang.Math.max(0L, (nbOccurences.longValue() - 1)));
                nbOccurences = (Long)obj[TIMER_NB_OCCUR_INDEX];

                if (isActive == true) {
                  if (fixedRate.booleanValue())
                  {
                    alarmClock = new TimerAlarmClock(this, date);
                    obj[ALARM_CLOCK_INDEX] = (Object)alarmClock;
                    timer.schedule(alarmClock, alarmClock.next);
                  }
                  else
                  {
                    alarmClock = new TimerAlarmClock(this, period.longValue());
                    obj[ALARM_CLOCK_INDEX] = (Object)alarmClock;
                    timer.schedule(alarmClock, alarmClock.timeout);
                  }
                }
                if (TIMER_LOGGER.isLoggable(Level.FINER)) {
                    TimerNotification notif = (TimerNotification)obj[TIMER_NOTIF_INDEX];
                    StringBuilder strb = new StringBuilder()
                    .append("update timer notification with:")
                    .append("\n\tNotification source = ")
                    .append(notif.getSource())
                    .append("\n\tNotification type = ")
                    .append(notif.getType())
                    .append("\n\tNotification ID = ")
                    .append(notifID)
                    .append("\n\tNotification date = ")
                    .append(date)
                    .append("\n\tNotification period = ")
                    .append(period)
                    .append("\n\tNotification nb of occurrences = ")
                    .append(nbOccurences)
                    .append("\n\tNotification executes at fixed rate = ")
                    .append(fixedRate);
                    TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(),
                            "updateTimerTable", strb.toString());
                }
            }
            else {
                if (alarmClock != null) {
//                     alarmClock.interrupt();
//                     try {
//                         // Wait until the thread die.
//                         //
//                         alarmClock.join();
//                     } catch (InterruptedException e) {
//                         // Ignore...
//                     }
                    alarmClock.cancel();
                }
                timerTable.remove(notifID);
            }
        }
        else {
            if (alarmClock != null) {
//                 alarmClock.interrupt();
//                 try {
//                     // Wait until the thread die.
//                     //
//                     alarmClock.join();
//                 } catch (InterruptedException e) {
//                     // Ignore...
//                 }

                   alarmClock.cancel();
            }
            timerTable.remove(notifID);
        }
    }

    /*
     * ------------------------------------------
     *  PACKAGE METHODS
     * ------------------------------------------
     * <p>
     *  ------------------------------------------包装方法------ ------------------------------------
     * 
     */

    /**
     * This method is called by the timer each time
     * the TimerAlarmClock has exceeded its timeout.
     *
     * <p>
     *  每当TimerAlarmClock超过其超时时,定时器将调用此方法。
     * 
     * 
     * @param notification The TimerAlarmClock notification.
     */
    @SuppressWarnings("deprecation")
    void notifyAlarmClock(TimerAlarmClockNotification notification) {

        TimerNotification timerNotification = null;
        Date timerDate = null;

        // Retrieve the timer notification associated to the alarm-clock.
        //
        TimerAlarmClock alarmClock = (TimerAlarmClock)notification.getSource();

        synchronized(Timer.this) {
            for (Object[] obj : timerTable.values()) {
                if (obj[ALARM_CLOCK_INDEX] == alarmClock) {
                    timerNotification = (TimerNotification)obj[TIMER_NOTIF_INDEX];
                    timerDate = (Date)obj[TIMER_DATE_INDEX];
                    break;
                }
            }
        }

        // Notify the timer.
        //
        sendNotification(timerDate, timerNotification);

        // Update the notification and the TimerAlarmClock timeout.
        //
        updateTimerTable(timerNotification.getNotificationID());
    }

    /**
     * This method is used by the timer MBean to update and send a timer
     * notification to all the listeners registered for this kind of notification.
     *
     * <p>
     *  定时器MBean使用此方法来更新定时器通知并向为此类通知注册的所有侦听器发送定时器通知。
     * 
     * @param timeStamp The notification emission date.
     * @param notification The timer notification to send.
     */
    void sendNotification(Date timeStamp, TimerNotification notification) {

        if (TIMER_LOGGER.isLoggable(Level.FINER)) {
            StringBuilder strb = new StringBuilder()
            .append("sending timer notification:")
            .append("\n\tNotification source = ")
            .append(notification.getSource())
            .append("\n\tNotification type = ")
            .append(notification.getType())
            .append("\n\tNotification ID = ")
            .append(notification.getNotificationID())
            .append("\n\tNotification date = ")
            .append(timeStamp);
            TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(),
                    "sendNotification", strb.toString());
        }
        long curSeqNumber;
        synchronized(this) {
            sequenceNumber = sequenceNumber + 1;
            curSeqNumber = sequenceNumber;
        }
        synchronized (notification) {
            notification.setTimeStamp(timeStamp.getTime());
            notification.setSequenceNumber(curSeqNumber);
            this.sendNotification((TimerNotification)notification.cloneTimerNotification());
        }

        TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(),
                "sendNotification", "timer notification sent");
    }
}
