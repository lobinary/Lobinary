/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.jmx.remote.internal;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanServer;
import javax.management.MBeanServerDelegate;
import javax.management.MBeanServerNotification;
import javax.management.Notification;
import javax.management.NotificationBroadcaster;
import javax.management.NotificationFilter;
import javax.management.NotificationFilterSupport;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.QueryEval;
import javax.management.QueryExp;

import javax.management.remote.NotificationResult;
import javax.management.remote.TargetedNotification;

import com.sun.jmx.remote.util.EnvHelp;
import com.sun.jmx.remote.util.ClassLogger;

/** A circular buffer of notifications received from an MBean server. */
/*
  There is one instance of ArrayNotificationBuffer for every
  MBeanServer object that has an attached ConnectorServer.  Then, for
  every ConnectorServer attached to a given MBeanServer, there is an
  instance of the inner class ShareBuffer.  So for example with two
  ConnectorServers it looks like this:

  ConnectorServer1 -> ShareBuffer1 -\
                                     }-> ArrayNotificationBuffer
  ConnectorServer2 -> ShareBuffer2 -/              |
                                                   |
                                                   v
                                              MBeanServer

  The ArrayNotificationBuffer has a circular buffer of
  NamedNotification objects.  Each ConnectorServer defines a
  notification buffer size, and this size is recorded by the
  corresponding ShareBuffer.  The buffer size of the
  ArrayNotificationBuffer is the maximum of all of its ShareBuffers.
  When a ShareBuffer is added or removed, the ArrayNotificationBuffer
  size is adjusted accordingly.

  An ArrayNotificationBuffer also has a BufferListener (which is a
  NotificationListener) registered on every NotificationBroadcaster
  MBean in the MBeanServer to which it is attached.  The cost of this
  potentially large set of listeners is the principal motivation for
  sharing the ArrayNotificationBuffer between ConnectorServers, and
  also the reason that we are careful to discard the
  ArrayNotificationBuffer (and its BufferListeners) when there are no
  longer any ConnectorServers using it.

  The synchronization of this class is inherently complex.  In an attempt
  to limit the complexity, we use just two locks:

  - globalLock controls access to the mapping between an MBeanServer
    and its ArrayNotificationBuffer and to the set of ShareBuffers for
    each ArrayNotificationBuffer.

  - the instance lock of each ArrayNotificationBuffer controls access
    to the array of notifications, including its size, and to the
    dispose flag of the ArrayNotificationBuffer.  The wait/notify
    mechanism is used to indicate changes to the array.

  If both locks are held at the same time, the globalLock must be
  taken first.

  Since adding or removing a BufferListener to an MBean can involve
  calling user code, we are careful not to hold any locks while it is
  done.
/* <p>
/*  对于具有附加的ConnectorServer的每个MBeanServer对象,都有一个ArrayNotificationBuffer实例。
/* 然后,对于连接到给定MBeanServer的每个ConnectorServer,都有一个内部类ShareBuffer的实例。因此,例如有两个ConnectorServers,它看起来像这样：。
/* 
/*  ConnectorServer1  - > ShareBuffer1  -  \}  - > ArrayNotificationBuffer ConnectorServer2  - > ShareBu
/* ffer2  -  / | | v MBeanServer。
/* 
/*  ArrayNotificationBuffer具有NamedNotification对象的循环缓冲区。每个ConnectorServer定义通知缓冲区大小,此大小由相应的ShareBuffer记录。
/*  ArrayNotificationBuffer的缓冲区大小是其所有ShareBuffer的最大值。添加或删除ShareBuffer时,会相应地调整ArrayNotificationBuffer大小。
/* 
/*  ArrayNotificationBuffer也有一个BufferListener(它是一个NotificationListener)在它附加的MBeanServer的每个NotificationBr
/* oadcaster MBean上注册。
/* 这个潜在的大型侦听器集合的成本是在ConnectorServers之间共享ArrayNotificationBuffer的主要动机,以及当不再有任何ConnectorServer使用它时,我们谨慎地丢弃
/* ArrayNotificationBuffer(及其BufferListeners)的原因。
/* 
/* 这个类的同步本质上是复杂的。为了限制复杂性,我们只使用两个锁：
/* 
/*   -  globalLock控制访问MBeanServer及其ArrayNotificationBuffer之间的映射以及每个ArrayNotificationBuffer的ShareBuffers集
/* 合。
/* 
/*   - 每个ArrayNotificationBuffer的实例锁控制对通知数组的访问,包括其大小,以及ArrayNotificationBuffer的dispose标志。
/* 等待/通知机制用于指示对数组的更改。
/* 
/*  如果两个锁同时保持,则必须首先采用globalLock。
/* 
/*  由于向MBean添加或删除BufferListener可能涉及调用用户代码,因此我们在完成时不小心保留任何锁。
/* 
 */
public class ArrayNotificationBuffer implements NotificationBuffer {
    private boolean disposed = false;

    // FACTORY STUFF, INCLUDING SHARING

    private static final Object globalLock = new Object();
    private static final
        HashMap<MBeanServer,ArrayNotificationBuffer> mbsToBuffer =
        new HashMap<MBeanServer,ArrayNotificationBuffer>(1);
    private final Collection<ShareBuffer> sharers = new HashSet<ShareBuffer>(1);

    public static NotificationBuffer getNotificationBuffer(
            MBeanServer mbs, Map<String, ?> env) {

        if (env == null)
            env = Collections.emptyMap();

        //Find out queue size
        int queueSize = EnvHelp.getNotifBufferSize(env);

        ArrayNotificationBuffer buf;
        boolean create;
        NotificationBuffer sharer;
        synchronized (globalLock) {
            buf = mbsToBuffer.get(mbs);
            create = (buf == null);
            if (create) {
                buf = new ArrayNotificationBuffer(mbs, queueSize);
                mbsToBuffer.put(mbs, buf);
            }
            sharer = buf.new ShareBuffer(queueSize);
        }
        /* We avoid holding any locks while calling createListeners.
         * This prevents possible deadlocks involving user code, but
         * does mean that a second ConnectorServer created and started
         * in this window will return before all the listeners are ready,
         * which could lead to surprising behaviour.  The alternative
         * would be to block the second ConnectorServer until the first
         * one has finished adding all the listeners, but that would then
         * be subject to deadlock.
         * <p>
         *  这可以防止涉及用户代码的可能死锁,但这意味着在所有监听器准备就绪之前,在此窗口中创建和启动的第二个ConnectorServer将返回,这可能会导致令人惊讶的行为。
         * 另一种方法是阻塞第二个ConnectorServer,直到第一个添加完所有的侦听器,然后会遇到死锁。
         * 
         */
        if (create)
            buf.createListeners();
        return sharer;
    }

    /* Ensure that this buffer is no longer the one that will be returned by
     * getNotificationBuffer.  This method is idempotent - calling it more
     * than once has no effect beyond that of calling it once.
     * <p>
     *  getNotificationBuffer。这个方法是幂等的 - 调用它不止一次没有效果超过调用它一次。
     * 
     */
    static void removeNotificationBuffer(MBeanServer mbs) {
        synchronized (globalLock) {
            mbsToBuffer.remove(mbs);
        }
    }

    void addSharer(ShareBuffer sharer) {
        synchronized (globalLock) {
            synchronized (this) {
                if (sharer.getSize() > queueSize)
                    resize(sharer.getSize());
            }
            sharers.add(sharer);
        }
    }

    private void removeSharer(ShareBuffer sharer) {
        boolean empty;
        synchronized (globalLock) {
            sharers.remove(sharer);
            empty = sharers.isEmpty();
            if (empty)
                removeNotificationBuffer(mBeanServer);
            else {
                int max = 0;
                for (ShareBuffer buf : sharers) {
                    int bufsize = buf.getSize();
                    if (bufsize > max)
                        max = bufsize;
                }
                if (max < queueSize)
                    resize(max);
            }
        }
        if (empty) {
            synchronized (this) {
                disposed = true;
                // Notify potential waiting fetchNotification call
                notifyAll();
            }
            destroyListeners();
        }
    }

    private synchronized void resize(int newSize) {
        if (newSize == queueSize)
            return;
        while (queue.size() > newSize)
            dropNotification();
        queue.resize(newSize);
        queueSize = newSize;
    }

    private class ShareBuffer implements NotificationBuffer {
        ShareBuffer(int size) {
            this.size = size;
            addSharer(this);
        }

        public NotificationResult
            fetchNotifications(NotificationBufferFilter filter,
                               long startSequenceNumber,
                               long timeout,
                               int maxNotifications)
                throws InterruptedException {
            NotificationBuffer buf = ArrayNotificationBuffer.this;
            return buf.fetchNotifications(filter, startSequenceNumber,
                                          timeout, maxNotifications);
        }

        public void dispose() {
            ArrayNotificationBuffer.this.removeSharer(this);
        }

        int getSize() {
            return size;
        }

        private final int size;
    }


    // ARRAYNOTIFICATIONBUFFER IMPLEMENTATION

    private ArrayNotificationBuffer(MBeanServer mbs, int queueSize) {
        if (logger.traceOn())
            logger.trace("Constructor", "queueSize=" + queueSize);

        if (mbs == null || queueSize < 1)
            throw new IllegalArgumentException("Bad args");

        this.mBeanServer = mbs;
        this.queueSize = queueSize;
        this.queue = new ArrayQueue<NamedNotification>(queueSize);
        this.earliestSequenceNumber = System.currentTimeMillis();
        this.nextSequenceNumber = this.earliestSequenceNumber;

        logger.trace("Constructor", "ends");
    }

    private synchronized boolean isDisposed() {
        return disposed;
    }

    // We no longer support calling this method from outside.
    // The JDK doesn't contain any such calls and users are not
    // supposed to be accessing this class.
    public void dispose() {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>Fetch notifications that match the given listeners.</p>
     *
     * <p>The operation only considers notifications with a sequence
     * number at least <code>startSequenceNumber</code>.  It will take
     * no longer than <code>timeout</code>, and will return no more
     * than <code>maxNotifications</code> different notifications.</p>
     *
     * <p>If there are no notifications matching the criteria, the
     * operation will block until one arrives, subject to the
     * timeout.</p>
     *
     * <p>
     *  <p>提取符合指定倾听者的通知。</p>
     * 
     * <p>此操作只考虑序号至少为<code> startSequenceNumber </code>的通知。
     * 它不会超过<code> timeout </code>,并且只会返回不超过<code> maxNotifications </code>的通知。</p>。
     * 
     *  <p>如果没有符合条件的通知,则操作将阻止,直到到达为止,但会超时。</p>
     * 
     * 
     * @param filter an object that will add notifications to a
     * {@code List<TargetedNotification>} if they match the current
     * listeners with their filters.
     * @param startSequenceNumber the first sequence number to
     * consider.
     * @param timeout the maximum time to wait.  May be 0 to indicate
     * not to wait if there are no notifications.
     * @param maxNotifications the maximum number of notifications to
     * return.  May be 0 to indicate a wait for eligible notifications
     * that will return a usable <code>nextSequenceNumber</code>.  The
     * {@link TargetedNotification} array in the returned {@link
     * NotificationResult} may contain more than this number of
     * elements but will not contain more than this number of
     * different notifications.
     */
    public NotificationResult
        fetchNotifications(NotificationBufferFilter filter,
                           long startSequenceNumber,
                           long timeout,
                           int maxNotifications)
            throws InterruptedException {

        logger.trace("fetchNotifications", "starts");

        if (startSequenceNumber < 0 || isDisposed()) {
            synchronized(this) {
                return new NotificationResult(earliestSequenceNumber(),
                                              nextSequenceNumber(),
                                              new TargetedNotification[0]);
            }
        }

        // Check arg validity
        if (filter == null
            || startSequenceNumber < 0 || timeout < 0
            || maxNotifications < 0) {
            logger.trace("fetchNotifications", "Bad args");
            throw new IllegalArgumentException("Bad args to fetch");
        }

        if (logger.debugOn()) {
            logger.trace("fetchNotifications",
                  "filter=" + filter + "; startSeq=" +
                  startSequenceNumber + "; timeout=" + timeout +
                  "; max=" + maxNotifications);
        }

        if (startSequenceNumber > nextSequenceNumber()) {
            final String msg = "Start sequence number too big: " +
                startSequenceNumber + " > " + nextSequenceNumber();
            logger.trace("fetchNotifications", msg);
            throw new IllegalArgumentException(msg);
        }

        /* Determine the end time corresponding to the timeout value.
           Caller may legitimately supply Long.MAX_VALUE to indicate no
           timeout.  In that case the addition will overflow and produce
           a negative end time.  Set end time to Long.MAX_VALUE in that
        /* <p>
        /*  呼叫者可能合法地提供Long.MAX_VALUE以指示没有超时。在这种情况下,加法将溢出并产生负结束时间。将结束时间设置为Long.MAX_VALUE
        /* 
        /* 
           case.  We assume System.currentTimeMillis() is positive.  */
        long endTime = System.currentTimeMillis() + timeout;
        if (endTime < 0) // overflow
            endTime = Long.MAX_VALUE;

        if (logger.debugOn())
            logger.debug("fetchNotifications", "endTime=" + endTime);

        /* We set earliestSeq the first time through the loop.  If we
           set it here, notifications could be dropped before we
           started examining them, so earliestSeq might not correspond
        /* <p>
        /*  设置它,通知可以在我们开始检查它们之前,因此earliestSeq可能不对应
        /* 
        /* 
           to the earliest notification we examined.  */
        long earliestSeq = -1;
        long nextSeq = startSequenceNumber;
        List<TargetedNotification> notifs =
            new ArrayList<TargetedNotification>();

        /* On exit from this loop, notifs, earliestSeq, and nextSeq must
        /* <p>
        /* 
           all be correct values for the returned NotificationResult.  */
        while (true) {
            logger.debug("fetchNotifications", "main loop starts");

            NamedNotification candidate;

            /* Get the next available notification regardless of filters,
            /* <p>
            /* 
               or wait for one to arrive if there is none.  */
            synchronized (this) {

                /* First time through.  The current earliestSequenceNumber
                /* <p>
                /* 
                   is the first one we could have examined.  */
                if (earliestSeq < 0) {
                    earliestSeq = earliestSequenceNumber();
                    if (logger.debugOn()) {
                        logger.debug("fetchNotifications",
                              "earliestSeq=" + earliestSeq);
                    }
                    if (nextSeq < earliestSeq) {
                        nextSeq = earliestSeq;
                        logger.debug("fetchNotifications",
                                     "nextSeq=earliestSeq");
                    }
                } else
                    earliestSeq = earliestSequenceNumber();

                /* If many notifications have been dropped since the
                   last time through, nextSeq could now be earlier
                   than the current earliest.  If so, notifications
                   may have been lost and we return now so the caller
                /* <p>
                /*  上次通过,nextSeq现在可以早于当前最早。如果是,通知可能已丢失,我们现在返回,因此呼叫者
                /* 
                /* 
                   can see this next time it calls.  */
                if (nextSeq < earliestSeq) {
                    logger.trace("fetchNotifications",
                          "nextSeq=" + nextSeq + " < " + "earliestSeq=" +
                          earliestSeq + " so may have lost notifs");
                    break;
                }

                if (nextSeq < nextSequenceNumber()) {
                    candidate = notificationAt(nextSeq);
                    // Skip security check if NotificationBufferFilter is not overloaded
                    if (!(filter instanceof ServerNotifForwarder.NotifForwarderBufferFilter)) {
                        try {
                            ServerNotifForwarder.checkMBeanPermission(this.mBeanServer,
                                                      candidate.getObjectName(),"addNotificationListener");
                        } catch (InstanceNotFoundException | SecurityException e) {
                            if (logger.debugOn()) {
                                logger.debug("fetchNotifications", "candidate: " + candidate + " skipped. exception " + e);
                            }
                            ++nextSeq;
                            continue;
                        }
                    }

                    if (logger.debugOn()) {
                        logger.debug("fetchNotifications", "candidate: " +
                                     candidate);
                        logger.debug("fetchNotifications", "nextSeq now " +
                                     nextSeq);
                    }
                } else {
                    /* nextSeq is the largest sequence number.  If we
                       already got notifications, return them now.
                       Otherwise wait for some to arrive, with
                    /* <p>
                    /*  已经收到通知,现在返回。否则等待一些到达,与
                    /* 
                    /* 
                       timeout.  */
                    if (notifs.size() > 0) {
                        logger.debug("fetchNotifications",
                              "no more notifs but have some so don't wait");
                        break;
                    }
                    long toWait = endTime - System.currentTimeMillis();
                    if (toWait <= 0) {
                        logger.debug("fetchNotifications", "timeout");
                        break;
                    }

                    /* dispose called */
                    if (isDisposed()) {
                        if (logger.debugOn())
                            logger.debug("fetchNotifications",
                                         "dispose callled, no wait");
                        return new NotificationResult(earliestSequenceNumber(),
                                                  nextSequenceNumber(),
                                                  new TargetedNotification[0]);
                    }

                    if (logger.debugOn())
                        logger.debug("fetchNotifications",
                                     "wait(" + toWait + ")");
                    wait(toWait);

                    continue;
                }
            }

            /* We have a candidate notification.  See if it matches
               our filters.  We do this outside the synchronized block
               so we don't hold up everyone accessing the buffer
               (including notification senders) while we evaluate
            /* <p>
            /*  我们的过滤器。我们在同步块之外做这个,所以我们不阻止每个人访问缓冲区(包括通知发送者),而我们评估
            /* 
            /* 
               potentially slow filters.  */
            ObjectName name = candidate.getObjectName();
            Notification notif = candidate.getNotification();
            List<TargetedNotification> matchedNotifs =
                new ArrayList<TargetedNotification>();
            logger.debug("fetchNotifications",
                         "applying filter to candidate");
            filter.apply(matchedNotifs, name, notif);

            if (matchedNotifs.size() > 0) {
                /* We only check the max size now, so that our
                   returned nextSeq is as large as possible.  This
                   prevents the caller from thinking it missed
                   interesting notifications when in fact we knew they
                /* <p>
                /*  返回的nextSeq尽可能大。这防止呼叫者认为它错过了有趣的通知,事实上我们知道他们
                /* 
                /* 
                   weren't.  */
                if (maxNotifications <= 0) {
                    logger.debug("fetchNotifications",
                                 "reached maxNotifications");
                    break;
                }
                --maxNotifications;
                if (logger.debugOn())
                    logger.debug("fetchNotifications", "add: " +
                                 matchedNotifs);
                notifs.addAll(matchedNotifs);
            }

            ++nextSeq;
        } // end while

        /* Construct and return the result.  */
        int nnotifs = notifs.size();
        TargetedNotification[] resultNotifs =
            new TargetedNotification[nnotifs];
        notifs.toArray(resultNotifs);
        NotificationResult nr =
            new NotificationResult(earliestSeq, nextSeq, resultNotifs);
        if (logger.debugOn())
            logger.debug("fetchNotifications", nr.toString());
        logger.trace("fetchNotifications", "ends");

        return nr;
    }

    synchronized long earliestSequenceNumber() {
        return earliestSequenceNumber;
    }

    synchronized long nextSequenceNumber() {
        return nextSequenceNumber;
    }

    synchronized void addNotification(NamedNotification notif) {
        if (logger.traceOn())
            logger.trace("addNotification", notif.toString());

        while (queue.size() >= queueSize) {
            dropNotification();
            if (logger.debugOn()) {
                logger.debug("addNotification",
                      "dropped oldest notif, earliestSeq=" +
                      earliestSequenceNumber);
            }
        }
        queue.add(notif);
        nextSequenceNumber++;
        if (logger.debugOn())
            logger.debug("addNotification", "nextSeq=" + nextSequenceNumber);
        notifyAll();
    }

    private void dropNotification() {
        queue.remove(0);
        earliestSequenceNumber++;
    }

    synchronized NamedNotification notificationAt(long seqNo) {
        long index = seqNo - earliestSequenceNumber;
        if (index < 0 || index > Integer.MAX_VALUE) {
            final String msg = "Bad sequence number: " + seqNo + " (earliest "
                + earliestSequenceNumber + ")";
            logger.trace("notificationAt", msg);
            throw new IllegalArgumentException(msg);
        }
        return queue.get((int) index);
    }

    private static class NamedNotification {
        NamedNotification(ObjectName sender, Notification notif) {
            this.sender = sender;
            this.notification = notif;
        }

        ObjectName getObjectName() {
            return sender;
        }

        Notification getNotification() {
            return notification;
        }

        public String toString() {
            return "NamedNotification(" + sender + ", " + notification + ")";
        }

        private final ObjectName sender;
        private final Notification notification;
    }

    /*
     * Add our listener to every NotificationBroadcaster MBean
     * currently in the MBean server and to every
     * NotificationBroadcaster later created.
     *
     * It would be really nice if we could just do
     * mbs.addNotificationListener(new ObjectName("*:*"), ...);
     * Definitely something for the next version of JMX.
     *
     * There is a nasty race condition that we must handle.  We
     * first register for MBean-creation notifications so we can add
     * listeners to new MBeans, then we query the existing MBeans to
     * add listeners to them.  The problem is that a new MBean could
     * arrive after we register for creations but before the query has
     * completed.  Then we could see the MBean both in the query and
     * in an MBean-creation notification, and we would end up
     * registering our listener twice.
     *
     * To solve this problem, we arrange for new MBeans that arrive
     * while the query is being done to be added to the Set createdDuringQuery
     * and we do not add a listener immediately.  When the query is done,
     * we atomically turn off the addition of new names to createdDuringQuery
     * and add all the names that were there to the result of the query.
     * Since we are dealing with Sets, the result is the same whether or not
     * the newly-created MBean was included in the query result.
     *
     * It is important not to hold any locks during the operation of adding
     * listeners to MBeans.  An MBean's addNotificationListener can be
     * arbitrary user code, and this could deadlock with any locks we hold
     * (see bug 6239400).  The corollary is that we must not do any operations
     * in this method or the methods it calls that require locks.
     * <p>
     *  将我们的监听器添加到当前在MBean服务器中的每个NotificationBroadcaster MBean以及以后创建的每个NotificationBroadcaster。
     * 
     * 这将是真的很好,如果我们可以做mbs.addNotificationListener(new ObjectName("*：*"),...);绝对是下一个版本的JMX的东西。
     * 
     *  有一个讨厌的种族条件,我们必须处理。我们首先注册MBean创建通知,所以我们可以添加侦听器到新的MBean,然后我们查询现有的MBean以添加侦听器。
     * 问题是,一个新的MBean可以在我们注册创建之后但在查询完成之前到达。然后我们可以在查询和MBean创建通知中看到MBean,我们最终会注册我们的监听器两次。
     * 
     *  为了解决这个问题,我们安排在查询完成时到达的新MBean被添加到Set createdDuringQuery,我们不立即添加监听器。
     * 当查询完成时,我们原子性地关闭添加新名称到createdDuringQuery并将所有的名称添加到查询的结果。因为我们处理集合,无论新创建的MBean是否包括在查询结果中,结果都是相同的。
     * 
     *  在向MBean添加侦听器的操作期间不要保持任何锁定是重要的。 MBean的addNotificationListener可以是任意的用户代码,这可能与我们持有的任何锁死锁(参见错误6239400)。
     * 推论是,我们不能在这个方法或者它所调用的需要锁的方法中做任何操作。
     * 
     */
    private void createListeners() {
        logger.debug("createListeners", "starts");

        synchronized (this) {
            createdDuringQuery = new HashSet<ObjectName>();
        }

        try {
            addNotificationListener(MBeanServerDelegate.DELEGATE_NAME,
                                    creationListener, creationFilter, null);
            logger.debug("createListeners", "added creationListener");
        } catch (Exception e) {
            final String msg = "Can't add listener to MBean server delegate: ";
            RuntimeException re = new IllegalArgumentException(msg + e);
            EnvHelp.initCause(re, e);
            logger.fine("createListeners", msg + e);
            logger.debug("createListeners", e);
            throw re;
        }

        /* Spec doesn't say whether Set returned by QueryNames can be modified
        /* <p>
        /* 
           so we clone it. */
        Set<ObjectName> names = queryNames(null, broadcasterQuery);
        names = new HashSet<ObjectName>(names);

        synchronized (this) {
            names.addAll(createdDuringQuery);
            createdDuringQuery = null;
        }

        for (ObjectName name : names)
            addBufferListener(name);
        logger.debug("createListeners", "ends");
    }

    private void addBufferListener(ObjectName name) {
        checkNoLocks();
        if (logger.debugOn())
            logger.debug("addBufferListener", name.toString());
        try {
            addNotificationListener(name, bufferListener, null, name);
        } catch (Exception e) {
            logger.trace("addBufferListener", e);
            /* This can happen if the MBean was unregistered just
               after the query.  Or user NotificationBroadcaster might
            /* <p>
            /* 
               throw unexpected exception.  */
        }
    }

    private void removeBufferListener(ObjectName name) {
        checkNoLocks();
        if (logger.debugOn())
            logger.debug("removeBufferListener", name.toString());
        try {
            removeNotificationListener(name, bufferListener);
        } catch (Exception e) {
            logger.trace("removeBufferListener", e);
        }
    }

    private void addNotificationListener(final ObjectName name,
                                         final NotificationListener listener,
                                         final NotificationFilter filter,
                                         final Object handback)
            throws Exception {
        try {
            AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() {
                public Void run() throws InstanceNotFoundException {
                    mBeanServer.addNotificationListener(name,
                                                        listener,
                                                        filter,
                                                        handback);
                    return null;
                }
            });
        } catch (Exception e) {
            throw extractException(e);
        }
    }

    private void removeNotificationListener(final ObjectName name,
                                            final NotificationListener listener)
            throws Exception {
        try {
            AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() {
                public Void run() throws Exception {
                    mBeanServer.removeNotificationListener(name, listener);
                    return null;
                }
            });
        } catch (Exception e) {
            throw extractException(e);
        }
    }

    private Set<ObjectName> queryNames(final ObjectName name,
                                       final QueryExp query) {
        PrivilegedAction<Set<ObjectName>> act =
            new PrivilegedAction<Set<ObjectName>>() {
                public Set<ObjectName> run() {
                    return mBeanServer.queryNames(name, query);
                }
            };
        try {
            return AccessController.doPrivileged(act);
        } catch (RuntimeException e) {
            logger.fine("queryNames", "Failed to query names: " + e);
            logger.debug("queryNames", e);
            throw e;
        }
    }

    private static boolean isInstanceOf(final MBeanServer mbs,
                                        final ObjectName name,
                                        final String className) {
        PrivilegedExceptionAction<Boolean> act =
            new PrivilegedExceptionAction<Boolean>() {
                public Boolean run() throws InstanceNotFoundException {
                    return mbs.isInstanceOf(name, className);
                }
            };
        try {
            return AccessController.doPrivileged(act);
        } catch (Exception e) {
            logger.fine("isInstanceOf", "failed: " + e);
            logger.debug("isInstanceOf", e);
            return false;
        }
    }

    /* This method must not be synchronized.  See the comment on the
     * createListeners method.
     *
     * The notification could arrive after our buffer has been destroyed
     * or even during its destruction.  So we always add our listener
     * (without synchronization), then we check if the buffer has been
     * destroyed and if so remove the listener we just added.
     * <p>
     *  后查询。或者用户NotificationBroadcaster可能
     * 
     */
    private void createdNotification(MBeanServerNotification n) {
        final String shouldEqual =
            MBeanServerNotification.REGISTRATION_NOTIFICATION;
        if (!n.getType().equals(shouldEqual)) {
            logger.warning("createNotification", "bad type: " + n.getType());
            return;
        }

        ObjectName name = n.getMBeanName();
        if (logger.debugOn())
            logger.debug("createdNotification", "for: " + name);

        synchronized (this) {
            if (createdDuringQuery != null) {
                createdDuringQuery.add(name);
                return;
            }
        }

        if (isInstanceOf(mBeanServer, name, broadcasterClass)) {
            addBufferListener(name);
            if (isDisposed())
                removeBufferListener(name);
        }
    }

    private class BufferListener implements NotificationListener {
        public void handleNotification(Notification notif, Object handback) {
            if (logger.debugOn()) {
                logger.debug("BufferListener.handleNotification",
                      "notif=" + notif + "; handback=" + handback);
            }
            ObjectName name = (ObjectName) handback;
            addNotification(new NamedNotification(name, notif));
        }
    }

    private final NotificationListener bufferListener = new BufferListener();

    private static class BroadcasterQuery
            extends QueryEval implements QueryExp {
        private static final long serialVersionUID = 7378487660587592048L;

        public boolean apply(final ObjectName name) {
            final MBeanServer mbs = QueryEval.getMBeanServer();
            return isInstanceOf(mbs, name, broadcasterClass);
        }
    }
    private static final QueryExp broadcasterQuery = new BroadcasterQuery();

    private static final NotificationFilter creationFilter;
    static {
        NotificationFilterSupport nfs = new NotificationFilterSupport();
        nfs.enableType(MBeanServerNotification.REGISTRATION_NOTIFICATION);
        creationFilter = nfs;
    }

    private final NotificationListener creationListener =
        new NotificationListener() {
            public void handleNotification(Notification notif,
                                           Object handback) {
                logger.debug("creationListener", "handleNotification called");
                createdNotification((MBeanServerNotification) notif);
            }
        };

    private void destroyListeners() {
        checkNoLocks();
        logger.debug("destroyListeners", "starts");
        try {
            removeNotificationListener(MBeanServerDelegate.DELEGATE_NAME,
                                       creationListener);
        } catch (Exception e) {
            logger.warning("remove listener from MBeanServer delegate", e);
        }
        Set<ObjectName> names = queryNames(null, broadcasterQuery);
        for (final ObjectName name : names) {
            if (logger.debugOn())
                logger.debug("destroyListeners",
                             "remove listener from " + name);
            removeBufferListener(name);
        }
        logger.debug("destroyListeners", "ends");
    }

    private void checkNoLocks() {
        if (Thread.holdsLock(this) || Thread.holdsLock(globalLock))
            logger.warning("checkNoLocks", "lock protocol violation");
    }

    /**
     * Iterate until we extract the real exception
     * from a stack of PrivilegedActionExceptions.
     * <p>
     *  createListeners方法。
     * 
     * 通知可以在我们的缓冲区已经被销毁或甚至在其销毁之后到达。所以我们总是添加我们的监听器(没有同步),然后我们检查缓冲区是否已被销毁,如果是这样,删除我们刚刚添加的监听器。
     * 
     */
    private static Exception extractException(Exception e) {
        while (e instanceof PrivilegedActionException) {
            e = ((PrivilegedActionException)e).getException();
        }
        return e;
    }

    private static final ClassLogger logger =
        new ClassLogger("javax.management.remote.misc",
                        "ArrayNotificationBuffer");

    private final MBeanServer mBeanServer;
    private final ArrayQueue<NamedNotification> queue;
    private int queueSize;
    private long earliestSequenceNumber;
    private long nextSequenceNumber;
    private Set<ObjectName> createdDuringQuery;

    static final String broadcasterClass =
        NotificationBroadcaster.class.getName();
}
