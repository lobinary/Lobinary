/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2002, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.impl.ior ;


public class ByteBuffer {
    /**
     * The array buffer into which the components of the ByteBuffer are
     * stored. The capacity of the ByteBuffer is the length of this array buffer,
     * and is at least large enough to contain all the ByteBuffer's elements.<p>
     *
     * Any array elements following the last element in the ByteBuffer are 0.
     * <p>
     *  数组缓冲区,ByteBuffer的组件存储在其中。 ByteBuffer的容量是这个数组缓冲区的长度,并且至少足够大以包含所有的ByteBuffer的元素。<p>
     * 
     *  ByteBuffer中最后一个元素后面的数组元素为0。
     * 
     */
    protected byte elementData[];

    /**
     * The number of valid components in this <tt>ByteBuffer</tt> object.
     * Components <tt>elementData[0]</tt> through
     * <tt>elementData[elementCount-1]</tt> are the actual items.
     *
     * <p>
     *  此<tt> ByteBuffer </tt>对象中的有效组件数。
     * 组件<tt> elementData [0] </tt>到<tt> elementData [elementCount-1] </tt>是实际项目。
     * 
     * 
     * @serial
     */
    protected int elementCount;

    /**
     * The amount by which the capacity of the ByteBuffer is automatically
     * incremented when its size becomes greater than its capacity.  If
     * the capacity increment is less than or equal to zero, the capacity
     * of the ByteBuffer is doubled each time it needs to grow.
     *
     * <p>
     *  ByteBuffer的容量在其大小大于其容量时自动递增的量。如果容量增量小于或等于零,则每次需要增长时,ByteBuffer的容量将增加一倍。
     * 
     * 
     * @serial
     */
    protected int capacityIncrement;

    /**
     * Constructs an empty ByteBuffer with the specified initial capacity and
     * capacity increment.
     *
     * <p>
     *  构造具有指定的初始容量和容量增量的空字节缓冲区。
     * 
     * 
     * @param   initialCapacity     the initial capacity of the ByteBuffer.
     * @param   capacityIncrement   the amount by which the capacity is
     *                              increased when the ByteBuffer overflows.
     * @exception IllegalArgumentException if the specified initial capacity
     *               is negative
     */
    public ByteBuffer(int initialCapacity, int capacityIncrement) {
        super();
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal Capacity: "+
                                               initialCapacity);
        this.elementData = new byte[initialCapacity];
        this.capacityIncrement = capacityIncrement;
    }

    /**
     * Constructs an empty ByteBuffer with the specified initial capacity and
     * with its capacity increment equal to zero.
     *
     * <p>
     *  构造具有指定的初始容量和容量增量等于零的空字节缓冲区。
     * 
     * 
     * @param   initialCapacity   the initial capacity of the ByteBuffer.
     * @exception IllegalArgumentException if the specified initial capacity
     *               is negative
     */
    public ByteBuffer(int initialCapacity) {
        this(initialCapacity, 0);
    }

    /**
     * Constructs an empty ByteBuffer so that its internal data array
     * has size <tt>10</tt> and its standard capacity increment is
     * zero.
     * <p>
     *  构造一个空的ByteBuffer,使其内部数据数组的大小为<tt> 10 </tt>,其标准容量增量为零。
     * 
     */
    public ByteBuffer() {
        this(200);
    }

    /**
     * Trims the capacity of this ByteBuffer to be the ByteBuffer's current
     * size. If the capacity of this cector is larger than its current
     * size, then the capacity is changed to equal the size by replacing
     * its internal data array, kept in the field <tt>elementData</tt>,
     * with a smaller one. An application can use this operation to
     * minimize the storage of a ByteBuffer.
     * <p>
     * 将此ByteBuffer的容量修改为ByteBuffer的当前大小。
     * 如果此连接器的容量大于其当前大小,则通过将保留在字段<tt> elementData </tt>中的内部数据数组替换为较小的一个来将容量更改为等于该大小。
     * 应用程序可以使用此操作最小化ByteBuffer的存储。
     * 
     */
    public void trimToSize() {
        int oldCapacity = elementData.length;
        if (elementCount < oldCapacity) {
            byte oldData[] = elementData;
            elementData = new byte[elementCount];
            System.arraycopy(oldData, 0, elementData, 0, elementCount);
        }
    }

    /**
     * This implements the unsynchronized semantics of ensureCapacity.
     * Synchronized methods in this class can internally call this
     * method for ensuring capacity without incurring the cost of an
     * extra synchronization.
     *
     * <p>
     *  这实现了ensureCapacity的不同步语义。此类中的同步方法可以在内部调用此方法以确保容量,而不会导致额外同步的成本。
     * 
     * 
     * @see java.util.ByteBuffer#ensureCapacity(int)
     */
    private void ensureCapacityHelper(int minCapacity) {
        int oldCapacity = elementData.length;
        if (minCapacity > oldCapacity) {
            byte oldData[] = elementData;
            int newCapacity = (capacityIncrement > 0) ?
                (oldCapacity + capacityIncrement) : (oldCapacity * 2);
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            elementData = new byte[newCapacity];
            System.arraycopy(oldData, 0, elementData, 0, elementCount);
        }
    }

    /**
     * Returns the current capacity of this ByteBuffer.
     *
     * <p>
     *  返回此ByteBuffer的当前容量。
     * 
     * 
     * @return  the current capacity (the length of its internal
     *          data arary, kept in the field <tt>elementData</tt>
     *          of this ByteBuffer.
     */
    public int capacity() {
        return elementData.length;
    }

    /**
     * Returns the number of components in this ByteBuffer.
     *
     * <p>
     *  返回此ByteBuffer中的组件数。
     * 
     * 
     * @return  the number of components in this ByteBuffer.
     */
    public int size() {
        return elementCount;
    }

    /**
     * Tests if this ByteBuffer has no components.
     *
     * <p>
     *  测试这个ByteBuffer是否没有组件。
     * 
     * 
     * @return  <code>true</code> if and only if this ByteBuffer has
     *          no components, that is, its size is zero;
     *          <code>false</code> otherwise.
     */
    public boolean isEmpty() {
        return elementCount == 0;
    }

    public void append(byte value)
    {
        ensureCapacityHelper(elementCount + 1);
        elementData[elementCount++] = value;
    }

    public void append( int value )
    {
        ensureCapacityHelper(elementCount + 4);
        doAppend( value ) ;
    }

    private void doAppend( int value )
    {
        int current = value ;
        for (int ctr=0; ctr<4; ctr++) {
            elementData[elementCount+ctr] = (byte)(current & 255) ;
            current = current >> 8 ;
        }
        elementCount += 4 ;
    }

    public void append( String value )
    {
        byte[] data = value.getBytes() ;
        ensureCapacityHelper( elementCount + data.length + 4 ) ;
        doAppend( data.length ) ;
        System.arraycopy( data, 0, elementData, elementCount, data.length ) ;
        elementCount += data.length ;
    }

    /**
     * Returns an array containing all of the elements in this ByteBuffer
     * in the correct order.
     *
     * <p>
     *  以正确的顺序返回包含此ByteBuffer中所有元素的数组。
     * 
     * @since 1.2
     */
    public byte[] toArray() {
        return elementData ;
    }
}
