/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2003, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.impl.interceptors;

import com.sun.corba.se.spi.orb.ORB;
import org.omg.PortableInterceptor.Current;
import org.omg.PortableInterceptor.InvalidSlot;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_INV_ORDER;
import org.omg.CORBA.CompletionStatus;

import com.sun.corba.se.spi.logging.CORBALogDomains ;
import com.sun.corba.se.impl.logging.OMGSystemException ;

/**
 * PICurrent is the implementation of Current as specified in the Portable
 * Interceptors Spec orbos/99-12-02.
 * IMPORTANT: PICurrent is implemented with the assumption that get_slot()
 * or set_slot() will not be called in ORBInitializer.pre_init() and
 * post_init().
 * <p>
 *  PICurrent是在便携式拦截器Spec orbos / 99-12-02中指定的电流的实现。
 * 重要信息：PICurrent的实现假设get_slot()或set_slot()不会在ORBInitializer.pre_init()和post_init()中调用。
 * 
 */
public class PICurrent extends org.omg.CORBA.LocalObject
    implements Current
{
    // slotCounter is used to keep track of ORBInitInfo.allocate_slot_id()
    private int slotCounter;

    // The ORB associated with this PICurrent object.
    private ORB myORB;

    private OMGSystemException wrapper ;

    // True if the orb is still initialzing and get_slot and set_slot are not
    // to be called.
    private boolean orbInitializing;

    // ThreadLocal contains a stack of SlotTable which are used
    // for resolve_initial_references( "PICurrent" );
    private ThreadLocal threadLocalSlotTable
        = new ThreadLocal( ) {
            protected Object initialValue( ) {
                SlotTable table = new SlotTable( myORB, slotCounter );
                return new SlotTableStack( myORB, table );
            }
        };

    /**
     * PICurrent constructor which will be called for every ORB
     * initialization.
     * <p>
     *  PICurrent构造函数,将为每个ORB初始化调用。
     * 
     */
    PICurrent( ORB myORB ) {
        this.myORB = myORB;
        wrapper = OMGSystemException.get( myORB,
            CORBALogDomains.RPC_PROTOCOL ) ;
        this.orbInitializing = true;
        slotCounter = 0;
    }


    /**
     * This method will be called from ORBInitInfo.allocate_slot_id( ).
     * simply returns a slot id by incrementing slotCounter.
     * <p>
     *  此方法将从ORBInitInfo.allocate_slot_id()调用。通过增加slotCounter简单地返回一个slot id。
     * 
     */
    int allocateSlotId( ) {
        int slotId = slotCounter;
        slotCounter = slotCounter + 1;
        return slotId;
    }


    /**
     * This method gets the SlotTable which is on the top of the
     * ThreadLocalStack.
     * <p>
     *  此方法获取位于ThreadLocalStack顶部的SlotTable。
     * 
     */
    SlotTable getSlotTable( ) {
        SlotTable table = (SlotTable)
                ((SlotTableStack)threadLocalSlotTable.get()).peekSlotTable();
        return table;
    }

    /**
     * This method pushes a SlotTable on the SlotTableStack. When there is
     * a resolve_initial_references("PICurrent") after this call. The new
     * PICurrent will be returned.
     * <p>
     *  此方法在SlotTableStack上推送SlotTable。此调用后,如果有resolve_initial_references("PICurrent")。将返回新的PICurrent。
     * 
     */
    void pushSlotTable( ) {
        SlotTableStack st = (SlotTableStack)threadLocalSlotTable.get();
        st.pushSlotTable( );
    }


    /**
     * This method pops a SlotTable on the SlotTableStack.
     * <p>
     *  这个方法在SlotTableStack上弹出一个SlotTable。
     * 
     */
    void popSlotTable( ) {
        SlotTableStack st = (SlotTableStack)threadLocalSlotTable.get();
        st.popSlotTable( );
    }

    /**
     * This method sets the slot data at the given slot id (index) in the
     * Slot Table which is on the top of the SlotTableStack.
     * <p>
     *  此方法在SlotTableStack顶部的插槽表中的给定插槽id(索引)处设置插槽数据。
     * 
     */
    public void set_slot( int id, Any data ) throws InvalidSlot
    {
        if( orbInitializing ) {
            // As per ptc/00-08-06 if the ORB is still initializing, disallow
            // calls to get_slot and set_slot.  If an attempt is made to call,
            // throw a BAD_INV_ORDER.
            throw wrapper.invalidPiCall3() ;
        }

        getSlotTable().set_slot( id, data );
    }

    /**
     * This method gets the slot data at the given slot id (index) from the
     * Slot Table which is on the top of the SlotTableStack.
     * <p>
     *  此方法从位于SlotTableStack顶部的槽表获取给定槽id(索引)处的槽数据。
     * 
     */
    public Any get_slot( int id ) throws InvalidSlot
    {
        if( orbInitializing ) {
            // As per ptc/00-08-06 if the ORB is still initializing, disallow
            // calls to get_slot and set_slot.  If an attempt is made to call,
            // throw a BAD_INV_ORDER.
            throw wrapper.invalidPiCall4() ;
        }

        return getSlotTable().get_slot( id );
    }

    /**
     * This method resets all the slot data to null in the
     * Slot Table which is on the top of SlotTableStack.
     * <p>
     *  此方法将所有插槽数据在SlotTableStack顶部的插槽表中重置为null。
     * 
     */
    void resetSlotTable( ) {
        getSlotTable().resetSlots();
    }

    /**
     * Called from ORB when the ORBInitializers are about to start
     * initializing.
     * <p>
     *  当ORBInitializers即将开始初始化时从ORB调用。
     */
    void setORBInitializing( boolean init ) {
        this.orbInitializing = init;
    }
}
