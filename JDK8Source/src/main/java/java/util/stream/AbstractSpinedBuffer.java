/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
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
package java.util.stream;

/**
 * Base class for a data structure for gathering elements into a buffer and then
 * iterating them. Maintains an array of increasingly sized arrays, so there is
 * no copying cost associated with growing the data structure.
 * <p>
 *  用于将元素收集到缓冲区中,然后迭代它们的数据结构的基类。维护一个数组越来越大的数组,所以没有与增长数据结构相关的复制成本。
 * 
 * 
 * @since 1.8
 */
abstract class AbstractSpinedBuffer {
    /**
     * Minimum power-of-two for the first chunk.
     * <p>
     *  第一个块的最小功率为二。
     * 
     */
    public static final int MIN_CHUNK_POWER = 4;

    /**
     * Minimum size for the first chunk.
     * <p>
     *  第一个块的最小大小。
     * 
     */
    public static final int MIN_CHUNK_SIZE = 1 << MIN_CHUNK_POWER;

    /**
     * Max power-of-two for chunks.
     * <p>
     *  最大功率为二的块。
     * 
     */
    public static final int MAX_CHUNK_POWER = 30;

    /**
     * Minimum array size for array-of-chunks.
     * <p>
     *  数组数组的最小数组大小。
     * 
     */
    public static final int MIN_SPINE_SIZE = 8;


    /**
     * log2 of the size of the first chunk.
     * <p>
     *  log2的第一个块的大小。
     * 
     */
    protected final int initialChunkPower;

    /**
     * Index of the *next* element to write; may point into, or just outside of,
     * the current chunk.
     * <p>
     *  要写入的* next *元素的索引;可以指向当前块或者恰好在当前块之外。
     * 
     */
    protected int elementIndex;

    /**
     * Index of the *current* chunk in the spine array, if the spine array is
     * non-null.
     * <p>
     *  如果脊柱数组是非空的,则在脊柱数组中的* current * chunk的索引。
     * 
     */
    protected int spineIndex;

    /**
     * Count of elements in all prior chunks.
     * <p>
     *  所有先前块中元素的计数。
     * 
     */
    protected long[] priorElementCount;

    /**
     * Construct with an initial capacity of 16.
     * <p>
     *  构造初始容量为16。
     * 
     */
    protected AbstractSpinedBuffer() {
        this.initialChunkPower = MIN_CHUNK_POWER;
    }

    /**
     * Construct with a specified initial capacity.
     *
     * <p>
     *  构造具有指定的初始容量。
     * 
     * 
     * @param initialCapacity The minimum expected number of elements
     */
    protected AbstractSpinedBuffer(int initialCapacity) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal Capacity: "+ initialCapacity);

        this.initialChunkPower = Math.max(MIN_CHUNK_POWER,
                                          Integer.SIZE - Integer.numberOfLeadingZeros(initialCapacity - 1));
    }

    /**
     * Is the buffer currently empty?
     * <p>
     *  缓冲区当前是否为空?
     * 
     */
    public boolean isEmpty() {
        return (spineIndex == 0) && (elementIndex == 0);
    }

    /**
     * How many elements are currently in the buffer?
     * <p>
     *  缓冲区中当前有多少个元素?
     * 
     */
    public long count() {
        return (spineIndex == 0)
               ? elementIndex
               : priorElementCount[spineIndex] + elementIndex;
    }

    /**
     * How big should the nth chunk be?
     * <p>
     *  第n个块应该有多大?
     * 
     */
    protected int chunkSize(int n) {
        int power = (n == 0 || n == 1)
                    ? initialChunkPower
                    : Math.min(initialChunkPower + n - 1, AbstractSpinedBuffer.MAX_CHUNK_POWER);
        return 1 << power;
    }

    /**
     * Remove all data from the buffer
     * <p>
     *  从缓冲区中删除所有数据
     */
    public abstract void clear();
}
