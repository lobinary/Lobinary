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

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.INTERNAL;
import org.omg.PortableInterceptor.Current;
import org.omg.PortableInterceptor.InvalidSlot;

import com.sun.corba.se.impl.corba.AnyImpl;

import com.sun.corba.se.impl.logging.InterceptorsSystemException;
import com.sun.corba.se.spi.logging.CORBALogDomains;

import com.sun.corba.se.spi.orb.ORB;

/**
 * SlotTableStack is the container of SlotTable instances for each thread
 * <p>
 *  SlotTableStack是每个线程的SlotTable实例的容器
 * 
 */
public class SlotTableStack
{
    // SlotTablePool is the container for reusable SlotTables'
    private class SlotTablePool {

        // Contains a list of reusable SlotTable
        private SlotTable[] pool;

        // High water mark for the pool
        // If the pool size reaches this limit then putSlotTable will
        // not put SlotTable to the pool.
        private final int  HIGH_WATER_MARK = 5;

        // currentIndex points to the last SlotTable in the list
        private int currentIndex;

        SlotTablePool( ) {
            pool = new SlotTable[HIGH_WATER_MARK];
            currentIndex = 0;
        }

        /**
         * Puts SlotTable to the re-usable pool.
         * <p>
         *  将SlotTable置于可重用池中。
         * 
         */
        void putSlotTable( SlotTable table ) {
            // If there are enough SlotTables in the pool, then don't add
            // this table to the pool.
            if( currentIndex >= HIGH_WATER_MARK ) {
                // Let the garbage collector collect it.
                return;
            }
            pool[currentIndex] = table;
            currentIndex++;
        }

        /**
         * Gets SlotTable from the re-usable pool.
         * <p>
         *  从可重用池中获取SlotTable。
         * 
         */
        SlotTable getSlotTable( ) {
            // If there are no entries in the pool then return null
            if( currentIndex == 0 ) {
                return null;
            }
            // Works like a stack, Gets the last one added first
            currentIndex--;
            return pool[currentIndex];
        }
    }

    // Contains all the active SlotTables for each thread.
    // The List is made to behave like a stack.
    private java.util.List tableContainer;

    // Keeps track of number of PICurrents in the stack.
    private int currentIndex;

    // For Every Thread there will be a pool of re-usable SlotTables'
    // stored in SlotTablePool
    private SlotTablePool tablePool;

    // The ORB associated with this slot table stack
    private ORB orb;

    private InterceptorsSystemException wrapper ;

    /**
     * Constructs the stack and and SlotTablePool
     * <p>
     *  构造堆栈和SlotTablePool
     * 
     */
    SlotTableStack( ORB orb, SlotTable table ) {
       this.orb = orb;
       wrapper = InterceptorsSystemException.get( orb, CORBALogDomains.RPC_PROTOCOL ) ;

       currentIndex = 0;
       tableContainer = new java.util.ArrayList( );
       tablePool = new SlotTablePool( );
       // SlotTableStack will be created with one SlotTable on the stack.
       // This table is used as the reference to query for number of
       // allocated slots to create other slottables.
       tableContainer.add( currentIndex, table );
       currentIndex++;
    }


    /**
     * pushSlotTable  pushes a fresh Slot Table on to the stack by doing the
     * following,
     * 1: Checks to see if there is any SlotTable in SlotTablePool
     *    If present then use that instance to push into the SlotTableStack
     *
     * 2: If there is no SlotTable in the pool, then creates a new one and
     *    pushes that into the SlotTableStack
     * <p>
     *  pushSlotTable通过执行以下操作将新的Slot Table推送到堆栈：1：检查SlotTablePool中是否有任何SlotTable如果存在,然后使用该实例推入SlotTableStack
     * 。
     * 
     *  2：如果池中没有SlotTable,则创建一个新的SlotTable并将其推入SlotTableStack
     * 
     */
    void pushSlotTable( ) {
        SlotTable table = tablePool.getSlotTable( );
        if( table == null ) {
            // get an existing PICurrent to get the slotSize
            SlotTable tableTemp = peekSlotTable();
            table = new SlotTable( orb, tableTemp.getSize( ));
        }
        // NOTE: Very important not to always "add" - otherwise a memory leak.
        if (currentIndex == tableContainer.size()) {
            // Add will cause the table to grow.
            tableContainer.add( currentIndex, table );
        } else if (currentIndex > tableContainer.size()) {
            throw wrapper.slotTableInvariant( new Integer( currentIndex ),
                new Integer( tableContainer.size() ) ) ;
        } else {
            // Set will override unused slots.
            tableContainer.set( currentIndex, table );
        }
        currentIndex++;
    }

    /**
     * popSlotTable does the following
     * 1: pops the top SlotTable in the SlotTableStack
     *
     * 2: resets the slots in the SlotTable which resets the slotvalues to
     *    null if there are any previous sets.
     *
     * 3: puts the reset SlotTable into the SlotTablePool to reuse
     * <p>
     *  popSlotTable执行以下1：弹出SlotTableStack中的顶部SlotTable
     * 
     *  2：重置SlotTable中的槽,如果存在任何先前的集,则将槽值重置为空。
     * 
     *  3：将复位SlotTable放入SlotTablePool以重用
     */
    void  popSlotTable( ) {
        if( currentIndex <= 1 ) {
            // Do not pop the SlotTable, If there is only one.
            // This should not happen, But an extra check for safety.
            throw wrapper.cantPopOnlyPicurrent() ;
        }
        currentIndex--;
        SlotTable table = (SlotTable)tableContainer.get( currentIndex );
        tableContainer.set( currentIndex, null ); // Do not leak memory.
        table.resetSlots( );
        tablePool.putSlotTable( table );
    }

    /**
     * peekSlotTable gets the top SlotTable from the SlotTableStack without
     * popping.
     * <p>
     * 
     */
    SlotTable peekSlotTable( ) {
       return (SlotTable) tableContainer.get( currentIndex - 1);
    }

}

// End of file.
