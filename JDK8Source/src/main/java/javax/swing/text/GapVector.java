/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.text;

import java.util.Vector;
import java.io.Serializable;
import javax.swing.undo.UndoableEdit;

/**
 * An implementation of a gapped buffer similar to that used by
 * emacs.  The underlying storage is a java array of some type,
 * which is known only by the subclass of this class.  The array
 * has a gap somewhere.  The gap is moved to the location of changes
 * to take advantage of common behavior where most changes occur
 * in the same location.  Changes that occur at a gap boundary are
 * generally cheap and moving the gap is generally cheaper than
 * moving the array contents directly to accommodate the change.
 *
 * <p>
 *  类似于emacs使用的有缺口缓冲区的实现。底层存储是某种类型的java数组,只有该类的子类才知道。数组在某处有一个缺口。间隙移动到更改的位置,以利用大多数更改发生在同一位置的常见行为。
 * 在间隙边界处发生的变化通常是便宜的,并且移动间隙通常比直接移动阵列内容以便适应变化更便宜。
 * 
 * 
 * @author  Timothy Prinzing
 * @see GapContent
 */
abstract class GapVector implements Serializable {


    /**
     * Creates a new GapVector object.  Initial size defaults to 10.
     * <p>
     *  创建一个新的GapVector对象。初始大小默认为10。
     * 
     */
    public GapVector() {
        this(10);
    }

    /**
     * Creates a new GapVector object, with the initial
     * size specified.
     *
     * <p>
     *  创建一个新的GapVector对象,并指定初始大小。
     * 
     * 
     * @param initialLength the initial size
     */
    public GapVector(int initialLength) {
        array = allocateArray(initialLength);
        g0 = 0;
        g1 = initialLength;
    }

    /**
     * Allocate an array to store items of the type
     * appropriate (which is determined by the subclass).
     * <p>
     *  分配数组以存储适当类型的项(由子类确定)。
     * 
     */
    protected abstract Object allocateArray(int len);

    /**
     * Get the length of the allocated array
     * <p>
     *  获取分配的数组的长度
     * 
     */
    protected abstract int getArrayLength();

    /**
     * Access to the array.  The actual type
     * of the array is known only by the subclass.
     * <p>
     *  访问数组。数组的实际类型只有子类才知道。
     * 
     */
    protected final Object getArray() {
        return array;
    }

    /**
     * Access to the start of the gap.
     * <p>
     *  访问间隙的开始。
     * 
     */
    protected final int getGapStart() {
        return g0;
    }

    /**
     * Access to the end of the gap.
     * <p>
     *  访问到间隙的尽头。
     * 
     */
    protected final int getGapEnd() {
        return g1;
    }

    // ---- variables -----------------------------------

    /**
     * The array of items.  The type is determined by the subclass.
     * <p>
     *  项目数组。类型由子类确定。
     * 
     */
    private Object array;

    /**
     * start of gap in the array
     * <p>
     *  数组中间隙的开始
     * 
     */
    private int g0;

    /**
     * end of gap in the array
     * <p>
     *  阵列中的间隙末端
     * 
     */
    private int g1;


    // --- gap management -------------------------------

    /**
     * Replace the given logical position in the storage with
     * the given new items.  This will move the gap to the area
     * being changed if the gap is not currently located at the
     * change location.
     *
     * <p>
     *  用给定的新项替换存储中的给定逻辑位置。如果间隙当前不位于更改位置,则这将将间隙移动到正在更改的区域。
     * 
     * 
     * @param position the location to make the replacement.  This
     *  is not the location in the underlying storage array, but
     *  the location in the contiguous space being modeled.
     * @param rmSize the number of items to remove
     * @param addItems the new items to place in storage.
     */
    protected void replace(int position, int rmSize, Object addItems, int addSize) {
        int addOffset = 0;
        if (addSize == 0) {
            close(position, rmSize);
            return;
        } else if (rmSize > addSize) {
            /* Shrink the end. */
            close(position+addSize, rmSize-addSize);
        } else {
            /* Grow the end, do two chunks. */
            int endSize = addSize - rmSize;
            int end = open(position + rmSize, endSize);
            System.arraycopy(addItems, rmSize, array, end, endSize);
            addSize = rmSize;
        }
        System.arraycopy(addItems, addOffset, array, position, addSize);
    }

    /**
     * Delete nItems at position.  Squeezes any marks
     * within the deleted area to position.  This moves
     * the gap to the best place by minimizing it's
     * overall movement.  The gap must intersect the
     * target block.
     * <p>
     * 删除位置的nItems。将删除区域中的任何标记压缩到位置。这通过最小化它的整体运动将差距移动到最佳位置。间隙必须与目标块相交。
     * 
     */
    void close(int position, int nItems) {
        if (nItems == 0)  return;

        int end = position + nItems;
        int new_gs = (g1 - g0) + nItems;
        if (end <= g0) {
            // Move gap to end of block.
            if (g0 != end) {
                shiftGap(end);
            }
            // Adjust g0.
            shiftGapStartDown(g0 - nItems);
        } else if (position >= g0) {
            // Move gap to beginning of block.
            if (g0 != position) {
                shiftGap(position);
            }
            // Adjust g1.
            shiftGapEndUp(g0 + new_gs);
        } else {
            // The gap is properly inside the target block.
            // No data movement necessary, simply move both gap pointers.
            shiftGapStartDown(position);
            shiftGapEndUp(g0 + new_gs);
        }
    }

    /**
     * Make space for the given number of items at the given
     * location.
     *
     * <p>
     *  在给定位置为给定数量的项目留出空间。
     * 
     * 
     * @return the location that the caller should fill in
     */
    int open(int position, int nItems) {
        int gapSize = g1 - g0;
        if (nItems == 0) {
            if (position > g0)
                position += gapSize;
            return position;
        }

        // Expand the array if the gap is too small.
        shiftGap(position);
        if (nItems >= gapSize) {
            // Pre-shift the gap, to reduce total movement.
            shiftEnd(getArrayLength() - gapSize + nItems);
            gapSize = g1 - g0;
        }

        g0 = g0 + nItems;
        return position;
    }

    /**
     * resize the underlying storage array to the
     * given new size
     * <p>
     *  将底层存储阵列的大小调整为给定的新大小
     * 
     */
    void resize(int nsize) {
        Object narray = allocateArray(nsize);
        System.arraycopy(array, 0, narray, 0, Math.min(nsize, getArrayLength()));
        array = narray;
    }

    /**
     * Make the gap bigger, moving any necessary data and updating
     * the appropriate marks
     * <p>
     *  使间隙更大,移动任何必要的数据和更新适当的标记
     * 
     */
    protected void shiftEnd(int newSize) {
        int oldSize = getArrayLength();
        int oldGapEnd = g1;
        int upperSize = oldSize - oldGapEnd;
        int arrayLength = getNewArraySize(newSize);
        int newGapEnd = arrayLength - upperSize;
        resize(arrayLength);
        g1 = newGapEnd;

        if (upperSize != 0) {
            // Copy array items to new end of array.
            System.arraycopy(array, oldGapEnd, array, newGapEnd, upperSize);
        }
    }

    /**
     * Calculates a new size of the storage array depending on required
     * capacity.
     * <p>
     *  根据所需容量计算存储阵列的新大小。
     * 
     * 
     * @param reqSize the size which is necessary for new content
     * @return the new size of the storage array
     */
    int getNewArraySize(int reqSize) {
        return (reqSize + 1) * 2;
    }

    /**
     * Move the start of the gap to a new location,
     * without changing the size of the gap.  This
     * moves the data in the array and updates the
     * marks accordingly.
     * <p>
     *  将间隙的开始移动到新位置,而不更改间隙的大小。这将移动数组中的数据并相应地更新标记。
     * 
     */
    protected void shiftGap(int newGapStart) {
        if (newGapStart == g0) {
            return;
        }
        int oldGapStart = g0;
        int dg = newGapStart - oldGapStart;
        int oldGapEnd = g1;
        int newGapEnd = oldGapEnd + dg;
        int gapSize = oldGapEnd - oldGapStart;

        g0 = newGapStart;
        g1 = newGapEnd;
        if (dg > 0) {
            // Move gap up, move data down.
            System.arraycopy(array, oldGapEnd, array, oldGapStart, dg);
        } else if (dg < 0) {
            // Move gap down, move data up.
            System.arraycopy(array, newGapStart, array, newGapEnd, -dg);
        }
    }

    /**
     * Adjust the gap end downward.  This doesn't move
     * any data, but it does update any marks affected
     * by the boundary change.  All marks from the old
     * gap start down to the new gap start are squeezed
     * to the end of the gap (their location has been
     * removed).
     * <p>
     *  向下调整间隙端。这不会移动任何数据,但它会更新受边界变化影响的任何标记。从旧的差距开始到新的差距开始的所有标记被挤压到差距的末端(他们的位置已被删除)。
     * 
     */
    protected void shiftGapStartDown(int newGapStart) {
        g0 = newGapStart;
    }

    /**
     * Adjust the gap end upward.  This doesn't move
     * any data, but it does update any marks affected
     * by the boundary change. All marks from the old
     * gap end up to the new gap end are squeezed
     * to the end of the gap (their location has been
     * removed).
     * <p>
     *  向上调整间隙端。这不会移动任何数据,但它会更新受边界变化影响的任何标记。从旧间隙到新间隙端的所有标记被挤压到间隙的末端(它们的位置已被移除)。
     */
    protected void shiftGapEndUp(int newGapEnd) {
        g1 = newGapEnd;
    }

}
