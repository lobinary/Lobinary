/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2007, Oracle and/or its affiliates. All rights reserved.
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



// java imports
//
import java.util.Date;
import java.util.Vector;
// NPCTE fix for bugId 4464388, esc 0,  MR , to be added after modification of jmx spec
//import java.io.Serializable;
// end of NPCTE fix for bugId 4464388

// jmx imports
//
import javax.management.InstanceNotFoundException;

/**
 * Exposes the management interface of the timer MBean.
 *
 * <p>
 *  公开定时器MBean的管理接口。
 * 
 * 
 * @since 1.5
 */
public interface TimerMBean {

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
    public void start();

    /**
     * Stops the timer.
     * <p>
     *  停止定时器。
     * 
     */
    public void stop();

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
     *  请注意,一旦计时器通知已添加到通知列表中,则无法更新其关联的日期,时间段和发生次数。
     * <P>
     * 在定期通知的情况下,参数<i> fixedRate </i>的值用于指定执行方案,如{@link java.util.Timer}中所指定。
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

    public Integer addNotification(String type, String message, Object userData,
                                   Date date, long period, long nbOccurences, boolean fixedRate)
        throws java.lang.IllegalArgumentException;

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

    public Integer addNotification(String type, String message, Object userData,
                                   Date date, long period, long nbOccurences)
        throws java.lang.IllegalArgumentException;

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
     *  使用指定的<CODE>类型</CODE>,<CODE>消息</CODE>和<CODE> userData </CODE>创建新的计时器通知,并将其插入具有给定日期和时间的通知列表, null出现次数。
     * <P>
     * 定时器通知将使用固定延迟执行方案使用定时器周期连续地重复,如{@link java.util.Timer}中所规定。
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

    public Integer addNotification(String type, String message, Object userData,
                                   Date date, long period)
        throws java.lang.IllegalArgumentException;

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

    public Integer addNotification(String type, String message, Object userData, Date date)
        throws java.lang.IllegalArgumentException;

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
    public void removeNotification(Integer id) throws InstanceNotFoundException;

    /**
     * Removes all the timer notifications corresponding to the specified type from the list of notifications.
     *
     * <p>
     *  从通知列表中删除与指定类型对应的所有定时器通知。
     * 
     * 
     * @param type The timer notification type.
     *
     * @exception InstanceNotFoundException The specified type does not correspond to any timer notification
     * in the list of notifications of this timer MBean.
     */
    public void removeNotifications(String type) throws InstanceNotFoundException;

    /**
     * Removes all the timer notifications from the list of notifications
     * and resets the counter used to update the timer notification identifiers.
     * <p>
     *  从通知列表中删除所有计时器通知,并重置用于更新计时器通知标识符的计数器。
     * 
     */
    public void removeAllNotifications();

    // GETTERS AND SETTERS
    //--------------------

    /**
     * Gets the number of timer notifications registered into the list of notifications.
     *
     * <p>
     * 获取注册到通知列表中的计时器通知数。
     * 
     * 
     * @return The number of timer notifications.
     */
    public int getNbNotifications();

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
    public Vector<Integer> getAllNotificationIDs();

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
    public Vector<Integer> getNotificationIDs(String type);

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
    public String getNotificationType(Integer id);

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
    public String getNotificationMessage(Integer id);

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
    // NPCTE fix for bugId 4464388, esc 0 , MR , 03 sept 2001 , to be added after modification of jmx spec
    //public Serializable getNotificationUserData(Integer id);
    // end of NPCTE fix for bugId 4464388
    public Object getNotificationUserData(Integer id);
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
    public Date getDate(Integer id);

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
    public Long getPeriod(Integer id);

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
    public Long getNbOccurences(Integer id);

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
    public Boolean getFixedRate(Integer id);

    /**
     * Gets the flag indicating whether or not the timer sends past notifications.
     *
     * <p>
     *  获取指示定时器是否发送过去通知的标志。
     * 
     * 
     * @return The past notifications sending on/off flag value.
     *
     * @see #setSendPastNotifications
     */
    public boolean getSendPastNotifications();

    /**
     * Sets the flag indicating whether the timer sends past notifications or not.
     *
     * <p>
     *  设置指示定时器是否发送过去通知的标志。
     * 
     * 
     * @param value The past notifications sending on/off flag value.
     *
     * @see #getSendPastNotifications
     */
    public void setSendPastNotifications(boolean value);

    /**
     * Tests whether the timer MBean is active.
     * A timer MBean is marked active when the {@link #start start} method is called.
     * It becomes inactive when the {@link #stop stop} method is called.
     *
     * <p>
     *  测试定时器MBean是否活动。当调用{@link #start start}方法时,定时器MBean被标记为活动。当调用{@link #stop stop}方法时,它将变为无效。
     * 
     * 
     * @return <CODE>true</CODE> if the timer MBean is active, <CODE>false</CODE> otherwise.
     */
    public boolean isActive();

    /**
     * Tests whether the list of timer notifications is empty.
     *
     * <p>
     *  测试定时器通知的列表是否为空。
     * 
     * @return <CODE>true</CODE> if the list of timer notifications is empty, <CODE>false</CODE> otherwise.
     */
    public boolean isEmpty();
}
