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

import java.util.Date;
import java.util.logging.Level;
import static com.sun.jmx.defaults.JmxProperties.TIMER_LOGGER;

/**
 * This class provides a simple implementation of an alarm clock MBean.
 * The aim of this MBean is to set up an alarm which wakes up the timer every timeout (fixed-delay)
 * or at the specified date (fixed-rate).
 * <p>
 *  此类提供了闹钟MBean的简单实现。此MBean的目的是设置一个闹钟,每当超时(固定延迟)或在指定日期(固定速率)唤醒定时器。
 * 
 */

class TimerAlarmClock extends java.util.TimerTask {

    Timer listener = null;
    long timeout = 10000;
    Date next = null;

    /*
     * ------------------------------------------
     *  CONSTRUCTORS
     * ------------------------------------------
     * <p>
     *  ------------------------------------------建筑师------- -----------------------------------
     * 
     */

    public TimerAlarmClock(Timer listener, long timeout) {
        this.listener = listener;
        this.timeout = Math.max(0L, timeout);
    }

    public TimerAlarmClock(Timer listener, Date next) {
        this.listener = listener;
        this.next = next;
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
     * This method is called by the timer when it is started.
     * <p>
     *  该方法在启动时由定时器调用。
     */
    public void run() {

        try {
            //this.sleep(timeout);
            TimerAlarmClockNotification notif = new TimerAlarmClockNotification(this);
            listener.notifyAlarmClock(notif);
        } catch (Exception e) {
            TIMER_LOGGER.logp(Level.FINEST, Timer.class.getName(), "run",
                    "Got unexpected exception when sending a notification", e);
        }
    }
}
