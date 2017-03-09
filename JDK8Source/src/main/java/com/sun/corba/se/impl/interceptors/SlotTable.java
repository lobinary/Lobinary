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

import com.sun.corba.se.impl.corba.AnyImpl;
import com.sun.corba.se.spi.orb.ORB;
import org.omg.PortableInterceptor.Current;
import org.omg.PortableInterceptor.InvalidSlot;
import org.omg.CORBA.Any;

/**
 * SlotTable is used internally by PICurrent to store the slot information.
 * <p>
 *  SlotTable在PICurrent内部用于存储插槽信息。
 * 
 */
public class SlotTable {
    // The vector where all the slot data for the current thread is stored
    private Any[] theSlotData;

    // Required for instantiating Any object.
    private ORB orb;

    // The flag to check whether there are any updates in the current SlotTable.
    // The slots will be reset to null, only if this flag is set.
    private boolean dirtyFlag;

    /**
     * The constructor instantiates an Array of Any[] of size given by slotSize
     * parameter.
     * <p>
     *  构造函数实例化由slotSize参数指定的大小的任何[]的数组。
     * 
     */
    SlotTable( ORB orb, int slotSize ) {
        dirtyFlag = false;
        this.orb = orb;
        theSlotData = new Any[slotSize];
    }

    /**
     * This method sets the slot data at the given slot id (index).
     * <p>
     *  此方法在给定的槽id(索引)设置槽数据。
     * 
     */
    public void set_slot( int id, Any data ) throws InvalidSlot
    {
        // First check whether the slot is allocated
        // If not, raise the invalid slot exception
        if( id >= theSlotData.length ) {
            throw new InvalidSlot();
        }
        dirtyFlag = true;
        theSlotData[id] = data;
    }

    /**
     * This method get the slot data for the given slot id (index).
     * <p>
     *  此方法获取给定槽id(索引)的槽数据。
     * 
     */
    public Any get_slot( int id ) throws InvalidSlot
    {
        // First check whether the slot is allocated
        // If not, raise the invalid slot exception
        if( id >= theSlotData.length ) {
            throw new InvalidSlot();
        }
        if( theSlotData[id] == null ) {
            theSlotData [id] = new AnyImpl(orb);
        }
        return theSlotData[ id ];
    }


    /**
     * This method resets all the slot data to null if dirtyFlag is set.
     * <p>
     *  如果dirtyFlag设置,此方法将所有插槽数据重置为null。
     * 
     */
    void resetSlots( ) {
        if( dirtyFlag == true ) {
            for( int i = 0; i < theSlotData.length; i++ ) {
                theSlotData[i] = null;
            }
        }
    }

    /**
     * This method returns the size of the allocated slots.
     * <p>
     *  此方法返回分配的插槽的大小。
     */
    int getSize( ) {
        return theSlotData.length;
    }

}
