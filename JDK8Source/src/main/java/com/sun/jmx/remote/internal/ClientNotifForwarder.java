/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2012, Oracle and/or its affiliates. All rights reserved.
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

import java.io.IOException;
import java.io.NotSerializableException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.security.auth.Subject;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.NotificationFilter;
import javax.management.ObjectName;
import javax.management.MBeanServerNotification;
import javax.management.InstanceNotFoundException;
import javax.management.ListenerNotFoundException;

import javax.management.remote.NotificationResult;
import javax.management.remote.TargetedNotification;

import com.sun.jmx.remote.util.ClassLogger;
import com.sun.jmx.remote.util.EnvHelp;
import java.rmi.UnmarshalException;


public abstract class ClientNotifForwarder {

    private final AccessControlContext acc;

    public ClientNotifForwarder(Map env) {
        this(null, env);
    }

    private static int threadId;

    /* An Executor that allows at most one executing and one pending
       Runnable.  It uses at most one thread -- as soon as there is
       no pending Runnable the thread can exit.  Another thread is
       created as soon as there is a new pending Runnable.  This
       Executor is adapted for use in a situation where each Runnable
       usually schedules up another Runnable.  On return from the
       first one, the second one is immediately executed.  So this
       just becomes a complicated way to write a while loop, but with
       the advantage that you can replace it with another Executor,
       for instance one that you are using to execute a bunch of other
       unrelated work.

       You might expect that a java.util.concurrent.ThreadPoolExecutor
       with corePoolSize=0 and maximumPoolSize=1 would have the same
       behavior, but it does not.  A ThreadPoolExecutor only creates
       a new thread when a new task is submitted and the number of
       existing threads is < corePoolSize.  This can never happen when
       corePoolSize=0, so new threads are never created.  Surprising,
       but there you are.
    /* <p>
    /*  Runnable。它最多使用一个线程 - 只要没有挂起的Runnable线程可以退出。只要有一个新的待处理的Runnable,就创建另一个线程。
    /* 该执行器适用于每个Runnable通常调度另一个Runnable的情况。从第一个返回时,第二个立即执行。
    /* 所以这只是一个复杂的写一个while循环的方法,但是有一个优点,你可以用另一个Executor替换它,例如你用来执行一堆其他不相关的工作。
    /* 
    /*  您可能希望具有corePoolSize = 0和maximumPoolSize = 1的java.util.concurrent.ThreadPoolExecutor具有相同的行为,但它不会。
    /* 当提交一个新任务并且现有线程的数量为<corePoolSize时,ThreadPoolExecutor仅创建一个新线程。
    /* 当corePoolSize = 0时永远不会发生这种情况,因此永远不会创建新线程。令人惊讶,但你有。
    /* 
    */
    private static class LinearExecutor implements Executor {
        public synchronized void execute(Runnable command) {
            if (this.command != null)
                throw new IllegalArgumentException("More than one command");
            this.command = command;
            if (thread == null) {
                thread = new Thread() {

                    @Override
                    public void run() {
                        while (true) {
                            Runnable r;
                            synchronized (LinearExecutor.this) {
                                if (LinearExecutor.this.command == null) {
                                    thread = null;
                                    return;
                                } else {
                                    r = LinearExecutor.this.command;
                                    LinearExecutor.this.command = null;
                                }
                            }
                            r.run();
                        }
                    }
                };
                thread.setDaemon(true);
                thread.setName("ClientNotifForwarder-" + ++threadId);
                thread.start();
            }
        }

        private Runnable command;
        private Thread thread;
    }

    public ClientNotifForwarder(ClassLoader defaultClassLoader, Map<String, ?> env) {
        maxNotifications = EnvHelp.getMaxFetchNotifNumber(env);
        timeout = EnvHelp.getFetchTimeout(env);

        /* You can supply an Executor in which the remote call to
           fetchNotifications will be made.  The Executor's execute
           method reschedules another task, so you must not use
        /* <p>
        /*  fetch通知。执行器的execute方法重新调度另一个任务,因此您不能使用
        /* 
        /* 
           an Executor that executes tasks in the caller's thread.  */
        Executor ex = (Executor)
            env.get("jmx.remote.x.fetch.notifications.executor");
        if (ex == null)
            ex = new LinearExecutor();
        else if (logger.traceOn())
            logger.trace("ClientNotifForwarder", "executor is " + ex);

        this.defaultClassLoader = defaultClassLoader;
        this.executor = ex;
        this.acc = AccessController.getContext();
    }

    /**
     * Called to to fetch notifications from a server.
     * <p>
     *  调用从服务器获取通知。
     * 
     */
    abstract protected NotificationResult fetchNotifs(long clientSequenceNumber,
                                                      int maxNotifications,
                                                      long timeout)
            throws IOException, ClassNotFoundException;

    abstract protected Integer addListenerForMBeanRemovedNotif()
        throws IOException, InstanceNotFoundException;

    abstract protected void removeListenerForMBeanRemovedNotif(Integer id)
        throws IOException, InstanceNotFoundException,
               ListenerNotFoundException;

    /**
     * Used to send out a notification about lost notifs
     * <p>
     *  用于发送有关丢失的notifs的通​​知
     * 
     */
    abstract protected void lostNotifs(String message, long number);


    public synchronized void addNotificationListener(Integer listenerID,
                                        ObjectName name,
                                        NotificationListener listener,
                                        NotificationFilter filter,
                                        Object handback,
                                        Subject delegationSubject)
            throws IOException, InstanceNotFoundException {

        if (logger.traceOn()) {
            logger.trace("addNotificationListener",
                         "Add the listener "+listener+" at "+name);
        }

        infoList.put(listenerID,
                     new ClientListenerInfo(listenerID,
                                            name,
                                            listener,
                                            filter,
                                            handback,
                                            delegationSubject));


        init(false);
    }

    public synchronized Integer[]
        removeNotificationListener(ObjectName name,
                                   NotificationListener listener)
        throws ListenerNotFoundException, IOException {

        beforeRemove();

        if (logger.traceOn()) {
            logger.trace("removeNotificationListener",
                         "Remove the listener "+listener+" from "+name);
        }

        List<Integer> ids = new ArrayList<Integer>();
        List<ClientListenerInfo> values =
                new ArrayList<ClientListenerInfo>(infoList.values());
        for (int i=values.size()-1; i>=0; i--) {
            ClientListenerInfo li = values.get(i);

            if (li.sameAs(name, listener)) {
                ids.add(li.getListenerID());

                infoList.remove(li.getListenerID());
            }
        }

        if (ids.isEmpty())
            throw new ListenerNotFoundException("Listener not found");

        return ids.toArray(new Integer[0]);
    }

    public synchronized Integer
        removeNotificationListener(ObjectName name,
                                   NotificationListener listener,
                                   NotificationFilter filter,
                                   Object handback)
            throws ListenerNotFoundException, IOException {

        if (logger.traceOn()) {
            logger.trace("removeNotificationListener",
                         "Remove the listener "+listener+" from "+name);
        }

        beforeRemove();

        Integer id = null;

        List<ClientListenerInfo> values =
                new ArrayList<ClientListenerInfo>(infoList.values());
        for (int i=values.size()-1; i>=0; i--) {
            ClientListenerInfo li = values.get(i);
            if (li.sameAs(name, listener, filter, handback)) {
                id=li.getListenerID();

                infoList.remove(id);

                break;
            }
        }

        if (id == null)
            throw new ListenerNotFoundException("Listener not found");

        return id;
    }

    public synchronized Integer[] removeNotificationListener(ObjectName name) {
        if (logger.traceOn()) {
            logger.trace("removeNotificationListener",
                         "Remove all listeners registered at "+name);
        }

        List<Integer> ids = new ArrayList<Integer>();

        List<ClientListenerInfo> values =
                new ArrayList<ClientListenerInfo>(infoList.values());
        for (int i=values.size()-1; i>=0; i--) {
            ClientListenerInfo li = values.get(i);
            if (li.sameAs(name)) {
                ids.add(li.getListenerID());

                infoList.remove(li.getListenerID());
            }
        }

        return ids.toArray(new Integer[0]);
    }

    /*
     * Called when a connector is doing reconnection. Like <code>postReconnection</code>,
     * this method is intended to be called only by a client connector:
     * <code>RMIConnector</code> and <code>ClientIntermediary</code>.
     * Call this method will set the flag beingReconnection to <code>true</code>,
     * and the thread used to fetch notifis will be stopped, a new thread can be
     * created only after the method <code>postReconnection</code> is called.
     *
     * It is caller's responsiblity to not re-call this method before calling
     * <code>postReconnection</code>.
     * <p>
     * 当连接器正在重新连接时调用。
     * 与<code> postReconnection </code>类似,此方法仅由客户机连接器调用：<code> RMIConnector </code>和<code> ClientIntermediar
     * y </code>。
     * 当连接器正在重新连接时调用。
     * 调用此方法将标记areReconnection设置为<code> true </code>,并且用于获取notifis的线程将被停止,只有在调用<code> postReconnection </code>
     * 方法后才能创建一个新线程。
     * 当连接器正在重新连接时调用。
     * 
     *  这是调用者在调用<code> postReconnection </code>之前不重新调用此方法的职责。
     * 
     */
    public synchronized ClientListenerInfo[] preReconnection() throws IOException {
        if (state == TERMINATED || beingReconnected) { // should never
            throw new IOException("Illegal state.");
        }

        final ClientListenerInfo[] tmp =
            infoList.values().toArray(new ClientListenerInfo[0]);


        beingReconnected = true;

        infoList.clear();

        return tmp;
    }

    /**
     * Called after reconnection is finished.
     * This method is intended to be called only by a client connector:
     * <code>RMIConnector</code> and <code>ClientIntermediary</code>.
     * <p>
     *  重新连接完成后调用。此方法仅由客户端连接器调用：<code> RMIConnector </code>和<code> ClientIntermediary </code>。
     * 
     */
    public synchronized void postReconnection(ClientListenerInfo[] listenerInfos)
        throws IOException {

        if (state == TERMINATED) {
            return;
        }

        while (state == STOPPING) {
            try {
                wait();
            } catch (InterruptedException ire) {
                IOException ioe = new IOException(ire.toString());
                EnvHelp.initCause(ioe, ire);
                throw ioe;
            }
        }

        final boolean trace = logger.traceOn();
        final int len   = listenerInfos.length;

        for (int i=0; i<len; i++) {
            if (trace) {
                logger.trace("addNotificationListeners",
                             "Add a listener at "+
                             listenerInfos[i].getListenerID());
            }

            infoList.put(listenerInfos[i].getListenerID(), listenerInfos[i]);
        }

        beingReconnected = false;
        notifyAll();

        if (currentFetchThread == Thread.currentThread() ||
              state == STARTING || state == STARTED) { // doing or waiting reconnection
              // only update mbeanRemovedNotifID
            try {
                mbeanRemovedNotifID = addListenerForMBeanRemovedNotif();
            } catch (Exception e) {
                final String msg =
                    "Failed to register a listener to the mbean " +
                    "server: the client will not do clean when an MBean " +
                    "is unregistered";
                if (logger.traceOn()) {
                    logger.trace("init", msg, e);
                }
            }
        } else {
              while (state == STOPPING) {
                  try {
                      wait();
                  } catch (InterruptedException ire) {
                      IOException ioe = new IOException(ire.toString());
                      EnvHelp.initCause(ioe, ire);
                      throw ioe;
                  }
              }

              if (listenerInfos.length > 0) { // old listeners are re-added
                  init(true); // not update clientSequenceNumber
              } else if (infoList.size() > 0) { // only new listeners added during reconnection
                  init(false); // need update clientSequenceNumber
              }
          }
    }

    public synchronized void terminate() {
        if (state == TERMINATED) {
            return;
        }

        if (logger.traceOn()) {
            logger.trace("terminate", "Terminating...");
        }

        if (state == STARTED) {
           infoList.clear();
        }

        setState(TERMINATED);
    }


    // -------------------------------------------------
    // private classes
    // -------------------------------------------------
    //

    private class NotifFetcher implements Runnable {

        private volatile boolean alreadyLogged = false;

        private void logOnce(String msg, SecurityException x) {
            if (alreadyLogged) return;
            // Log only once.
            logger.config("setContextClassLoader",msg);
            if (x != null) logger.fine("setContextClassLoader", x);
            alreadyLogged = true;
        }

        // Set new context class loader, returns previous one.
        private final ClassLoader setContextClassLoader(final ClassLoader loader) {
            final AccessControlContext ctxt = ClientNotifForwarder.this.acc;
            // if ctxt is null, log a config message and throw a
            // SecurityException.
            if (ctxt == null) {
                logOnce("AccessControlContext must not be null.",null);
                throw new SecurityException("AccessControlContext must not be null");
            }
            return AccessController.doPrivileged(
                new PrivilegedAction<ClassLoader>() {
                    public ClassLoader run() {
                        try {
                            // get context class loader - may throw
                            // SecurityException - though unlikely.
                            final ClassLoader previous =
                                Thread.currentThread().getContextClassLoader();

                            // if nothing needs to be done, break here...
                            if (loader == previous) return previous;

                            // reset context class loader - may throw
                            // SecurityException
                            Thread.currentThread().setContextClassLoader(loader);
                            return previous;
                        } catch (SecurityException x) {
                            logOnce("Permission to set ContextClassLoader missing. " +
                                    "Notifications will not be dispatched. " +
                                    "Please check your Java policy configuration: " +
                                    x, x);
                            throw x;
                        }
                    }
                }, ctxt);
        }

        public void run() {
            final ClassLoader previous;
            if (defaultClassLoader != null) {
                previous = setContextClassLoader(defaultClassLoader);
            } else {
                previous = null;
            }
            try {
                doRun();
            } finally {
                if (defaultClassLoader != null) {
                    setContextClassLoader(previous);
                }
            }
        }

        private void doRun() {
            synchronized (ClientNotifForwarder.this) {
                currentFetchThread = Thread.currentThread();

                if (state == STARTING) {
                    setState(STARTED);
                }
            }


            NotificationResult nr = null;
            if (!shouldStop() && (nr = fetchNotifs()) != null) {
                // nr == null means got exception

                final TargetedNotification[] notifs =
                    nr.getTargetedNotifications();
                final int len = notifs.length;
                final Map<Integer, ClientListenerInfo> listeners;
                final Integer myListenerID;

                long missed = 0;

                synchronized(ClientNotifForwarder.this) {
                    // check sequence number.
                    //
                    if (clientSequenceNumber >= 0) {
                        missed = nr.getEarliestSequenceNumber() -
                            clientSequenceNumber;
                    }

                    clientSequenceNumber = nr.getNextSequenceNumber();

                    listeners = new HashMap<Integer, ClientListenerInfo>();

                    for (int i = 0 ; i < len ; i++) {
                        final TargetedNotification tn = notifs[i];
                        final Integer listenerID = tn.getListenerID();

                        // check if an mbean unregistration notif
                        if (!listenerID.equals(mbeanRemovedNotifID)) {
                            final ClientListenerInfo li = infoList.get(listenerID);
                            if (li != null) {
                                listeners.put(listenerID, li);
                            }
                            continue;
                        }
                        final Notification notif = tn.getNotification();
                        final String unreg =
                            MBeanServerNotification.UNREGISTRATION_NOTIFICATION;
                        if (notif instanceof MBeanServerNotification &&
                            notif.getType().equals(unreg)) {

                            MBeanServerNotification mbsn =
                                (MBeanServerNotification) notif;
                            ObjectName name = mbsn.getMBeanName();

                            removeNotificationListener(name);
                        }
                    }
                    myListenerID = mbeanRemovedNotifID;
                }

                if (missed > 0) {
                    final String msg =
                        "May have lost up to " + missed +
                        " notification" + (missed == 1 ? "" : "s");
                    lostNotifs(msg, missed);
                    logger.trace("NotifFetcher.run", msg);
                }

                // forward
                for (int i = 0 ; i < len ; i++) {
                    final TargetedNotification tn = notifs[i];
                    dispatchNotification(tn,myListenerID,listeners);
                }
            }

            synchronized (ClientNotifForwarder.this) {
                currentFetchThread = null;
            }

            if (nr == null || shouldStop()) {
                // tell that the thread is REALLY stopped
                setState(STOPPED);

                try {
                      removeListenerForMBeanRemovedNotif(mbeanRemovedNotifID);
                } catch (Exception e) {
                    if (logger.traceOn()) {
                        logger.trace("NotifFetcher-run",
                                "removeListenerForMBeanRemovedNotif", e);
                    }
                }
            } else {
                executor.execute(this);
            }
        }

        void dispatchNotification(TargetedNotification tn,
                                  Integer myListenerID,
                                  Map<Integer, ClientListenerInfo> listeners) {
            final Notification notif = tn.getNotification();
            final Integer listenerID = tn.getListenerID();

            if (listenerID.equals(myListenerID)) return;
            final ClientListenerInfo li = listeners.get(listenerID);

            if (li == null) {
                logger.trace("NotifFetcher.dispatch",
                             "Listener ID not in map");
                return;
            }

            NotificationListener l = li.getListener();
            Object h = li.getHandback();
            try {
                l.handleNotification(notif, h);
            } catch (RuntimeException e) {
                final String msg =
                    "Failed to forward a notification " +
                    "to a listener";
                logger.trace("NotifFetcher-run", msg, e);
            }

        }

        private NotificationResult fetchNotifs() {
            try {
                NotificationResult nr = ClientNotifForwarder.this.
                    fetchNotifs(clientSequenceNumber,maxNotifications,
                                timeout);

                if (logger.traceOn()) {
                    logger.trace("NotifFetcher-run",
                                 "Got notifications from the server: "+nr);
                }

                return nr;
            } catch (ClassNotFoundException | NotSerializableException | UnmarshalException e) {
                logger.trace("NotifFetcher.fetchNotifs", e);
                return fetchOneNotif();
            } catch (IOException ioe) {
                if (!shouldStop()) {
                    logger.error("NotifFetcher-run",
                                 "Failed to fetch notification, " +
                                 "stopping thread. Error is: " + ioe, ioe);
                    logger.debug("NotifFetcher-run",ioe);
                }

                // no more fetching
                return null;
            }
        }

        /* Fetch one notification when we suspect that it might be a
           notification that we can't deserialize (because of a
           missing class).  First we ask for 0 notifications with 0
           timeout.  This allows us to skip sequence numbers for
           notifications that don't match our filters.  Then we ask
           for one notification.  If that produces a
           ClassNotFoundException, NotSerializableException or
           UnmarshalException, we increase our sequence number and ask again.
           Eventually we will either get a successful notification, or a
           return with 0 notifications.  In either case we can return a
           NotificationResult.  This algorithm works (albeit less
           well) even if the server implementation doesn't optimize a
           request for 0 notifications to skip sequence numbers for
           notifications that don't match our filters.

           If we had at least one
           ClassNotFoundException/NotSerializableException/UnmarshalException,
           then we must emit a JMXConnectionNotification.LOST_NOTIFS.
        /* <p>
        /*  通知我们不能反序列化(因为缺少类)。首先,我们要求0通知0超时。这样,我们可以跳过与我们的过滤器不匹配的通知的序列号。然后我们要求一个通知。
        /* 如果产生ClassNotFoundException,NotSerializableException或UnmarshalException,我们增加我们的序列号并再次询问。
        /* 最终我们将获得一个成功的通知,或返回0通知。在任一种情况下,我们都可以返回一个NotificationResult。
        /* 该算法工作(虽然不太好),即使服务器实现没有优化对0通知的请求,以跳过与我们的过滤器不匹配的通知的序列号。
        /* 
        /* 如果我们至少有一个ClassNotFoundException / NotSerializableException / UnmarshalException,那么我们必须发出一个JMXConnecti
        /* onNotification.LOST_NOTIFS。
        /* 
        */
        private NotificationResult fetchOneNotif() {
            ClientNotifForwarder cnf = ClientNotifForwarder.this;

            long startSequenceNumber = clientSequenceNumber;

            int notFoundCount = 0;

            NotificationResult result = null;
            long firstEarliest = -1;

            while (result == null && !shouldStop()) {
                NotificationResult nr;

                try {
                    // 0 notifs to update startSequenceNumber
                    nr = cnf.fetchNotifs(startSequenceNumber, 0, 0L);
                } catch (ClassNotFoundException e) {
                    logger.warning("NotifFetcher.fetchOneNotif",
                                   "Impossible exception: " + e);
                    logger.debug("NotifFetcher.fetchOneNotif",e);
                    return null;
                } catch (IOException e) {
                    if (!shouldStop())
                        logger.trace("NotifFetcher.fetchOneNotif", e);
                    return null;
                }

                if (shouldStop())
                    return null;

                startSequenceNumber = nr.getNextSequenceNumber();
                if (firstEarliest < 0)
                    firstEarliest = nr.getEarliestSequenceNumber();

                try {
                    // 1 notif to skip possible missing class
                    result = cnf.fetchNotifs(startSequenceNumber, 1, 0L);
                } catch (ClassNotFoundException | NotSerializableException | UnmarshalException e) {
                    logger.warning("NotifFetcher.fetchOneNotif",
                                   "Failed to deserialize a notification: "+e.toString());
                    if (logger.traceOn()) {
                        logger.trace("NotifFetcher.fetchOneNotif",
                                     "Failed to deserialize a notification.", e);
                    }

                    notFoundCount++;
                    startSequenceNumber++;
                } catch (Exception e) {
                    if (!shouldStop())
                        logger.trace("NotifFetcher.fetchOneNotif", e);
                    return null;
                }
            }

            if (notFoundCount > 0) {
                final String msg =
                    "Dropped " + notFoundCount + " notification" +
                    (notFoundCount == 1 ? "" : "s") +
                    " because classes were missing locally or incompatible";
                lostNotifs(msg, notFoundCount);
                // Even if result.getEarliestSequenceNumber() is now greater than
                // it was initially, meaning some notifs have been dropped
                // from the buffer, we don't want the caller to see that
                // because it is then likely to renotify about the lost notifs.
                // So we put back the first value of earliestSequenceNumber
                // that we saw.
                if (result != null) {
                    result = new NotificationResult(
                            firstEarliest, result.getNextSequenceNumber(),
                            result.getTargetedNotifications());
                }
            }

            return result;
        }

        private boolean shouldStop() {
            synchronized (ClientNotifForwarder.this) {
                if (state != STARTED) {
                    return true;
                } else if (infoList.size() == 0) {
                    // no more listener, stop fetching
                    setState(STOPPING);

                    return true;
                }

                return false;
            }
        }
    }


// -------------------------------------------------
// private methods
// -------------------------------------------------
    private synchronized void setState(int newState) {
        if (state == TERMINATED) {
            return;
        }

        state = newState;
        this.notifyAll();
    }

    /*
     * Called to decide whether need to start a thread for fetching notifs.
     * <P>The parameter reconnected will decide whether to initilize the clientSequenceNumber,
     * initilaizing the clientSequenceNumber means to ignore all notifications arrived before.
     * If it is reconnected, we will not initialize in order to get all notifications arrived
     * during the reconnection. It may cause the newly registered listeners to receive some
     * notifications arrived before its registray.
     * <p>
     *  调用来决定是否需要启动一个线程来获取notifs。
     *  <P>重新连接的参数将决定是否初始化clientSequenceNumber,初始化clientSequenceNumber意味着忽略之前到达的所有通知。
     * 如果重新连接,我们将不会初始化,以便在重新连接期间获得所有通知到达。它可能导致新注册的听众接收在注册之前到达的一些通知。
     * 
     */
    private synchronized void init(boolean reconnected) throws IOException {
        switch (state) {
        case STARTED:
            return;
        case STARTING:
            return;
        case TERMINATED:
            throw new IOException("The ClientNotifForwarder has been terminated.");
        case STOPPING:
            if (beingReconnected == true) {
                // wait for another thread to do, which is doing reconnection
                return;
            }

            while (state == STOPPING) { // make sure only one fetching thread.
                try {
                    wait();
                } catch (InterruptedException ire) {
                    IOException ioe = new IOException(ire.toString());
                    EnvHelp.initCause(ioe, ire);

                    throw ioe;
                }
            }

            // re-call this method to check the state again,
            // the state can be other value like TERMINATED.
            init(reconnected);

            return;
        case STOPPED:
            if (beingReconnected == true) {
                // wait for another thread to do, which is doing reconnection
                return;
            }

            if (logger.traceOn()) {
                logger.trace("init", "Initializing...");
            }

            // init the clientSequenceNumber if not reconnected.
            if (!reconnected) {
                try {
                    NotificationResult nr = fetchNotifs(-1, 0, 0);

                    if (state != STOPPED) { // JDK-8038940
                                            // reconnection must happen during
                                            // fetchNotifs(-1, 0, 0), and a new
                                            // thread takes over the fetching job
                        return;
                    }

                    clientSequenceNumber = nr.getNextSequenceNumber();
                } catch (ClassNotFoundException e) {
                    // can't happen
                    logger.warning("init", "Impossible exception: "+ e);
                    logger.debug("init",e);
                }
            }

            // for cleaning
            try {
                mbeanRemovedNotifID = addListenerForMBeanRemovedNotif();
            } catch (Exception e) {
                final String msg =
                    "Failed to register a listener to the mbean " +
                    "server: the client will not do clean when an MBean " +
                    "is unregistered";
                if (logger.traceOn()) {
                    logger.trace("init", msg, e);
                }
            }

            setState(STARTING);

            // start fetching
            executor.execute(new NotifFetcher());

            return;
        default:
            // should not
            throw new IOException("Unknown state.");
        }
    }

    /**
     * Import: should not remove a listener during reconnection, the reconnection
     * needs to change the listener list and that will possibly make removal fail.
     * <p>
     *  导入：不应在重新连接期间删除侦听器,重新连接需要更改侦听器列表,这可能会使删除失败。
     * 
     */
    private synchronized void beforeRemove() throws IOException {
        while (beingReconnected) {
            if (state == TERMINATED) {
                throw new IOException("Terminated.");
            }

            try {
                wait();
            } catch (InterruptedException ire) {
                IOException ioe = new IOException(ire.toString());
                EnvHelp.initCause(ioe, ire);

                throw ioe;
            }
        }

        if (state == TERMINATED) {
            throw new IOException("Terminated.");
        }
    }

// -------------------------------------------------
// private variables
// -------------------------------------------------

    private final ClassLoader defaultClassLoader;
    private final Executor executor;

    private final Map<Integer, ClientListenerInfo> infoList =
            new HashMap<Integer, ClientListenerInfo>();

    // notif stuff
    private long clientSequenceNumber = -1;
    private final int maxNotifications;
    private final long timeout;
    private Integer mbeanRemovedNotifID = null;
    private Thread currentFetchThread;

    // state
    /**
     * This state means that a thread is being created for fetching and forwarding notifications.
     * <p>
     *  此状态表示正在创建用于提取和转发通知的线程。
     * 
     */
    private static final int STARTING = 0;

    /**
     * This state tells that a thread has been started for fetching and forwarding notifications.
     * <p>
     *  此状态表明已启动线程以提取和转发通知。
     * 
     */
    private static final int STARTED = 1;

    /**
     * This state means that the fetching thread is informed to stop.
     * <p>
     *  这个状态意味着获取线程被通知停止。
     * 
     */
    private static final int STOPPING = 2;

    /**
     * This state means that the fetching thread is already stopped.
     * <p>
     *  此状态意味着提取线程已停止。
     * 
     */
    private static final int STOPPED = 3;

    /**
     * This state means that this object is terminated and no more thread will be created
     * for fetching notifications.
     * <p>
     *  此状态表示此对象已终止,并且将不会创建用于提取通知的更多线程。
     * 
     */
    private static final int TERMINATED = 4;

    private int state = STOPPED;

    /**
     * This variable is used to tell whether a connector (RMIConnector or ClientIntermediary)
     * is doing reconnection.
     * This variable will be set to true by the method <code>preReconnection</code>, and set
     * to false by <code>postReconnection</code>.
     * When beingReconnected == true, no thread will be created for fetching notifications.
     * <p>
     * 此变量用于判断连接器(RMIConnector或ClientIntermediary)是否正在重新连接。
     * 此变量将通过方法<code> preReconnection </code>设置为true,并通过<code> postReconnection </code>设置为false。
     */
    private boolean beingReconnected = false;

    private static final ClassLogger logger =
        new ClassLogger("javax.management.remote.misc",
                        "ClientNotifForwarder");
}
