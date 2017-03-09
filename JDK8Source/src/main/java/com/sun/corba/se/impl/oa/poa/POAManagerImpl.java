/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2004, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.impl.oa.poa;

import java.util.Iterator;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

import org.omg.CORBA.LocalObject;
import org.omg.CORBA.CompletionStatus ;

import org.omg.PortableServer.POAManager;
import org.omg.PortableServer.POAManagerPackage.State;
import org.omg.PortableServer.POA;

import org.omg.PortableInterceptor.DISCARDING ;
import org.omg.PortableInterceptor.ACTIVE ;
import org.omg.PortableInterceptor.HOLDING ;
import org.omg.PortableInterceptor.INACTIVE ;
import org.omg.PortableInterceptor.NON_EXISTENT ;

import com.sun.corba.se.spi.protocol.PIHandler ;

import com.sun.corba.se.impl.logging.POASystemException ;

import com.sun.corba.se.impl.orbutil.ORBUtility ;

/** POAManagerImpl is the implementation of the POAManager interface.
 *  Its public methods are activate(), hold_requests(), discard_requests()
 *  and deactivate().
 * <p>
 *  它的公共方法是activate(),hold_requests(),discard_requests()和deactivate()。
 * 
 */

public class POAManagerImpl extends org.omg.CORBA.LocalObject implements
    POAManager
{
    private final POAFactory factory ;  // factory which contains global state
                                        // for all POAManagers
    private PIHandler pihandler ;       // for adapterManagerStateChanged
    private State state;                // current state of this POAManager
    private Set poas = new HashSet(4) ; // all poas controlled by this POAManager
    private int nInvocations=0;         // Number of invocations in progress
    private int nWaiters=0;             // Number of threads waiting for
                                        // invocations to complete
    private int myId = 0 ;              // This POAManager's ID
    private boolean debug ;
    private boolean explicitStateChange ; // initially false, set true as soon as
                                        // one of activate, hold_request,
                                        // discard_request, or deactivate is called.

    private String stateToString( State state )
    {
        switch (state.value()) {
            case State._HOLDING : return "State[HOLDING]" ;
            case State._ACTIVE : return "State[ACTIVE]" ;
            case State._DISCARDING : return "State[DISCARDING]" ;
            case State._INACTIVE : return "State[INACTIVE]" ;
        }

        return "State[UNKNOWN]" ;
    }

    public String toString()
    {
        return "POAManagerImpl[myId=" + myId +
            " state=" + stateToString(state) +
            " nInvocations=" + nInvocations +
            " nWaiters=" + nWaiters + "]" ;
    }

    POAFactory getFactory()
    {
        return factory ;
    }

    PIHandler getPIHandler()
    {
        return pihandler ;
    }

    private void countedWait()
    {
        try {
            if (debug) {
                ORBUtility.dprint( this, "Calling countedWait on POAManager " +
                    this + " nWaiters=" + nWaiters ) ;
            }

            nWaiters++ ;
            wait();
        } catch ( java.lang.InterruptedException ex ) {
            // NOP
        } finally {
            nWaiters-- ;

            if (debug) {
                ORBUtility.dprint( this, "Exiting countedWait on POAManager " +
                    this + " nWaiters=" + nWaiters ) ;
            }
        }
    }

    private void notifyWaiters()
    {
        if (debug) {
            ORBUtility.dprint( this, "Calling notifyWaiters on POAManager " +
                this + " nWaiters=" + nWaiters ) ;
        }

        if (nWaiters >0)
            notifyAll() ;
    }

    public int getManagerId()
    {
        return myId ;
    }

    POAManagerImpl( POAFactory factory, PIHandler pihandler )
    {
        this.factory = factory ;
        factory.addPoaManager(this);
        this.pihandler = pihandler ;
        myId = factory.newPOAManagerId() ;
        state = State.HOLDING;
        debug = factory.getORB().poaDebugFlag ;
        explicitStateChange = false ;

        if (debug) {
            ORBUtility.dprint( this, "Creating POAManagerImpl " + this ) ;
        }
    }

    synchronized void addPOA(POA poa)
    {
        // XXX This is probably not the correct error
        if (state.value() == State._INACTIVE) {
            POASystemException wrapper = factory.getWrapper();
            throw wrapper.addPoaInactive( CompletionStatus.COMPLETED_NO ) ;
        }

        poas.add(poa);
    }

    synchronized void removePOA(POA poa)
    {
        poas.remove(poa);
        if ( poas.isEmpty() ) {
            factory.removePoaManager(this);
        }
    }

    public short getORTState()
    {
        switch (state.value()) {
            case State._HOLDING    : return HOLDING.value ;
            case State._ACTIVE     : return ACTIVE.value ;
            case State._INACTIVE   : return INACTIVE.value ;
            case State._DISCARDING : return DISCARDING.value ;
            default                : return NON_EXISTENT.value ;
        }
    }

/****************************************************************************
 * The following four public methods are used to change the POAManager's state.
 *
 * A note on the design of synchronization code:
 * There are 4 places where a thread would need to wait for a condition:
 *      - in hold_requests, discard_requests, deactivate, enter
 * There are 5 places where a thread notifies a condition:
 *      - in activate, hold_requests, discard_requests, deactivate, exit
 *
 * Since each notify needs to awaken waiters in several of the 4 places,
 * and since wait() in Java has the nice property of releasing the lock
 * on its monitor before sleeping, it seemed simplest to have just one
 * monitor object: "this". Thus all notifies will awaken all waiters.
 * On waking up, each waiter verifies that the condition it was waiting
 * for is satisfied, otherwise it goes back into a wait().
 *
 * <p>
 *  以下四个公共方法用于更改POAManager的状态。
 * 
 *  关于同步代码设计的注意事项：有4个地方需要等待一个条件： - 在hold_requests,discard_requests,deactivate,enter有一个线程通知一个条件的5个地方： - 在
 * activate,hold_requests, discard_requests,deactivate,exit。
 * 
 *  由于每个通知需要在4个地方中的几个地方唤醒服务者,并且由于Java中的wait()在休眠之前释放了它的监视器上的锁定,这很简单,只有一个监视对象："this"。因此,所有通知将唤醒所有服务员。
 * 在唤醒时,每个服务器验证它等待的条件是否满足,否则返回到wait()。
 * 
 * 
 ****************************************************************************/

    /**
     * <code>activate</code>
     * <b>Spec: pages 3-14 thru 3-18</b>
     * <p>
     *  <code> activate </code> <b> Spec：pages 3-14 thru 3-18 </b>
     * 
     */
    public synchronized void activate()
        throws org.omg.PortableServer.POAManagerPackage.AdapterInactive
    {
        explicitStateChange = true ;

        if (debug) {
            ORBUtility.dprint( this,
                "Calling activate on POAManager " + this ) ;
        }

        try {
            if ( state.value() == State._INACTIVE )
                throw new org.omg.PortableServer.POAManagerPackage.AdapterInactive();

            // set the state to ACTIVE
            state = State.ACTIVE;

            pihandler.adapterManagerStateChanged( myId, getORTState() ) ;

            // Notify any invocations that were waiting because the previous
            // state was HOLDING, as well as notify any threads that were waiting
            // inside hold_requests() or discard_requests().
            notifyWaiters();
        } finally {
            if (debug) {
                ORBUtility.dprint( this,
                    "Exiting activate on POAManager " + this ) ;
            }
        }
    }

    /**
     * <code>hold_requests</code>
     * <b>Spec: pages 3-14 thru 3-18</b>
     * <p>
     *  <code> hold_requests </code> <b>规格：第3-14到3-18页</b>
     * 
     */
    public synchronized void hold_requests(boolean wait_for_completion)
        throws org.omg.PortableServer.POAManagerPackage.AdapterInactive
    {
        explicitStateChange = true ;

        if (debug) {
            ORBUtility.dprint( this,
                "Calling hold_requests on POAManager " + this ) ;
        }

        try {
            if ( state.value() == State._INACTIVE )
                throw new org.omg.PortableServer.POAManagerPackage.AdapterInactive();
            // set the state to HOLDING
            state  = State.HOLDING;

            pihandler.adapterManagerStateChanged( myId, getORTState() ) ;

            // Notify any threads that were waiting in the wait() inside
            // discard_requests. This will cause discard_requests to return
            // (which is in conformance with the spec).
            notifyWaiters();

            if ( wait_for_completion ) {
                while ( state.value() == State._HOLDING && nInvocations > 0 ) {
                    countedWait() ;
                }
            }
        } finally {
            if (debug) {
                ORBUtility.dprint( this,
                    "Exiting hold_requests on POAManager " + this ) ;
            }
        }
    }

    /**
     * <code>discard_requests</code>
     * <b>Spec: pages 3-14 thru 3-18</b>
     * <p>
     *  <code> discard_requests </code> <b>规格：第3-14到3-18页</b>
     * 
     */
    public synchronized void discard_requests(boolean wait_for_completion)
        throws org.omg.PortableServer.POAManagerPackage.AdapterInactive
    {
        explicitStateChange = true ;

        if (debug) {
            ORBUtility.dprint( this,
                "Calling hold_requests on POAManager " + this ) ;
        }

        try {
            if ( state.value() == State._INACTIVE )
                throw new org.omg.PortableServer.POAManagerPackage.AdapterInactive();

            // set the state to DISCARDING
            state = State.DISCARDING;

            pihandler.adapterManagerStateChanged( myId, getORTState() ) ;

            // Notify any invocations that were waiting because the previous
            // state was HOLDING. Those invocations will henceforth be rejected with
            // a TRANSIENT exception. Also notify any threads that were waiting
            // inside hold_requests().
            notifyWaiters();

            if ( wait_for_completion ) {
                while ( state.value() == State._DISCARDING && nInvocations > 0 ) {
                    countedWait() ;
                }
            }
        } finally {
            if (debug) {
                ORBUtility.dprint( this,
                    "Exiting hold_requests on POAManager " + this ) ;
            }
        }
    }

    /**
     * <code>deactivate</code>
     * <b>Spec: pages 3-14 thru 3-18</b>
     * Note: INACTIVE is a permanent state.
     * <p>
     *  <code>停用</code> <b>规格：第3-14页至第3-18页</b>注意：INACTIVE是永久状态。
     * 
     */

    public void deactivate(boolean etherealize_objects, boolean wait_for_completion)
        throws org.omg.PortableServer.POAManagerPackage.AdapterInactive
    {
        explicitStateChange = true ;

        try {
            synchronized( this ) {
                if (debug) {
                    ORBUtility.dprint( this,
                        "Calling deactivate on POAManager " + this ) ;
                }

                if ( state.value() == State._INACTIVE )
                    throw new org.omg.PortableServer.POAManagerPackage.AdapterInactive();

                state = State.INACTIVE;

                pihandler.adapterManagerStateChanged( myId, getORTState() ) ;

                // Notify any invocations that were waiting because the previous
                // state was HOLDING. Those invocations will then be rejected with
                // an OBJ_ADAPTER exception. Also notify any threads that were waiting
                // inside hold_requests() or discard_requests().
                notifyWaiters();
            }

            POAManagerDeactivator deactivator = new POAManagerDeactivator( this,
                etherealize_objects, debug ) ;

            if (wait_for_completion)
                deactivator.run() ;
            else {
                Thread thr = new Thread(deactivator) ;
                thr.start() ;
            }
        } finally {
            synchronized(this) {
                if (debug) {
                    ORBUtility.dprint( this,
                        "Exiting deactivate on POAManager " + this ) ;
                }
            }
        }
    }

    private class POAManagerDeactivator implements Runnable
    {
        private boolean etherealize_objects ;
        private POAManagerImpl pmi ;
        private boolean debug ;

        POAManagerDeactivator( POAManagerImpl pmi, boolean etherealize_objects,
            boolean debug )
        {
            this.etherealize_objects = etherealize_objects ;
            this.pmi = pmi ;
            this.debug = debug ;
        }

        public void run()
        {
            try {
                synchronized (pmi) {
                    if (debug) {
                        ORBUtility.dprint( this,
                            "Calling run with etherealize_objects=" +
                            etherealize_objects + " pmi=" + pmi ) ;
                    }

                    while ( pmi.nInvocations > 0 ) {
                        countedWait() ;
                    }
                }

                if (etherealize_objects) {
                    Iterator iterator = null ;

                    // Make sure that poas cannot change while we copy it!
                    synchronized (pmi) {
                        if (debug) {
                            ORBUtility.dprint( this,
                                "run: Preparing to etherealize with pmi=" +
                                pmi ) ;
                        }

                        iterator = (new HashSet(pmi.poas)).iterator();
                    }

                    while (iterator.hasNext()) {
                        // Each RETAIN+USE_SERVANT_MGR poa
                        // must call etherealize for all its objects
                        ((POAImpl)iterator.next()).etherealizeAll();
                    }

                    synchronized (pmi) {
                        if (debug) {
                            ORBUtility.dprint( this,
                                "run: removing POAManager and clearing poas " +
                                "with pmi=" + pmi ) ;
                        }

                        factory.removePoaManager(pmi);
                        poas.clear();
                    }
                }
            } finally {
                if (debug) {
                    synchronized (pmi) {
                        ORBUtility.dprint( this, "Exiting run" ) ;
                    }
                }
            }
        }
    }

    /**
     * Added according to the spec CORBA V2.3; this returns the
     * state of the POAManager
     * <p>
     *  根据规范CORBA V2.3添加;这返回POAManager的状态
     * 
     */

    public org.omg.PortableServer.POAManagerPackage.State get_state () {
        return state;
    }

/****************************************************************************
 * The following methods are used on the invocation path.
 * <p>
 *  在调用路径上使用以下方法。
 * 
 * 
 ****************************************************************************/

    // called from POA.find_POA before calling
    // AdapterActivator.unknown_adapter.
    synchronized void checkIfActive()
    {
        try {
            if (debug) {
                ORBUtility.dprint( this,
                    "Calling checkIfActive for POAManagerImpl " + this ) ;
            }

            checkState();
        } finally {
            if (debug) {
                ORBUtility.dprint( this,
                    "Exiting checkIfActive for POAManagerImpl " + this ) ;
            }
        }
    }

    private void checkState()
    {
        while ( state.value() != State._ACTIVE ) {
            switch ( state.value() ) {
                case State._HOLDING:
                    while ( state.value() == State._HOLDING ) {
                        countedWait() ;
                    }
                    break;

                case State._DISCARDING:
                    throw factory.getWrapper().poaDiscarding() ;

                case State._INACTIVE:
                    throw factory.getWrapper().poaInactive() ;
            }
        }
    }

    synchronized void enter()
    {
        try {
            if (debug) {
                ORBUtility.dprint( this,
                    "Calling enter for POAManagerImpl " + this ) ;
            }

            checkState();
            nInvocations++;
        } finally {
            if (debug) {
                ORBUtility.dprint( this,
                    "Exiting enter for POAManagerImpl " + this ) ;
            }
        }
    }

    synchronized void exit()
    {
        try {
            if (debug) {
                ORBUtility.dprint( this,
                    "Calling exit for POAManagerImpl " + this ) ;
            }

            nInvocations--;

            if ( nInvocations == 0 ) {
                // This notifies any threads that were in the
                // wait_for_completion loop in hold/discard/deactivate().
                notifyWaiters();
            }
        } finally {
            if (debug) {
                ORBUtility.dprint( this,
                    "Exiting exit for POAManagerImpl " + this ) ;
            }
        }
    }

    /** Activate the POAManager if no explicit state change has ever been
     * previously invoked.
     * <p>
     *  以前调用。
     */
    public synchronized void implicitActivation()
    {
        if (!explicitStateChange)
            try {
                activate() ;
            } catch (org.omg.PortableServer.POAManagerPackage.AdapterInactive ai) {
                // ignore the exception.
            }
    }
}
