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

/**
 * This class provides definitions of the notifications sent by timer MBeans.
 * <BR>It defines a timer notification identifier which allows to retrieve a timer notification
 * from the list of notifications of a timer MBean.
 * <P>
 * The timer notifications are created and handled by the timer MBean.
 *
 * <p>
 *  此类提供定时器MBean发送的通知的定义。 <BR>它定义了一个定时器通知标识符,它允许从定时器MBean的通知列表中检索定时器通知。
 * <P>
 *  定时器通知由定时器MBean创建和处理。
 * 
 * 
 * @since 1.5
 */
public class TimerNotification extends javax.management.Notification {


    /* Serial version */
    private static final long serialVersionUID = 1798492029603825750L;

    /*
     * ------------------------------------------
     *  PRIVATE VARIABLES
     * ------------------------------------------
     * <p>
     *  ------------------------------------------私人变数------ ------------------------------------
     * 
     */

    /**
    /* <p>
    /* 
     * @serial Timer notification identifier.
     *         This identifier is used to retrieve a timer notification from the timer list of notifications.
     */
    private Integer notificationID;


    /*
     * ------------------------------------------
     *  CONSTRUCTORS
     * ------------------------------------------
     * <p>
     *  ------------------------------------------建筑师------- -----------------------------------
     * 
     */

    /**
     * Creates a timer notification object.
     *
     * <p>
     *  创建定时器通知对象。
     * 
     * 
     * @param type The notification type.
     * @param source The notification producer.
     * @param sequenceNumber The notification sequence number within the source object.
     * @param timeStamp The notification emission date.
     * @param msg The notification message.
     * @param id The notification identifier.
     *
     */
    public TimerNotification(String type, Object source, long sequenceNumber, long timeStamp, String msg, Integer id) {

        super(type, source, sequenceNumber, timeStamp, msg);
        this.notificationID = id;
    }

    /*
     * ------------------------------------------
     *  PUBLIC METHODS
     * ------------------------------------------
     * <p>
     *  ------------------------------------------公共方法------------------------------------
     * 
     */

    // GETTERS AND SETTERS
    //--------------------

    /**
     * Gets the identifier of this timer notification.
     *
     * <p>
     *  获取此计时器通知的标识符。
     * 
     * 
     * @return The identifier.
     */
    public Integer getNotificationID() {
        return notificationID;
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
     * Creates and returns a copy of this object.
     *
     * <p>
     *  创建并返回此对象的副本。
     */
    Object cloneTimerNotification() {

        TimerNotification clone = new TimerNotification(this.getType(), this.getSource(), this.getSequenceNumber(),
                                                        this.getTimeStamp(), this.getMessage(), notificationID);
        clone.setUserData(this.getUserData());
        return clone;
    }
}
