/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing;

/**
 * A <code>SizeSequence</code> object
 * efficiently maintains an ordered list
 * of sizes and corresponding positions.
 * One situation for which <code>SizeSequence</code>
 * might be appropriate is in a component
 * that displays multiple rows of unequal size.
 * In this case, a single <code>SizeSequence</code>
 * object could be used to track the heights
 * and Y positions of all rows.
 * <p>
 * Another example would be a multi-column component,
 * such as a <code>JTable</code>,
 * in which the column sizes are not all equal.
 * The <code>JTable</code> might use a single
 * <code>SizeSequence</code> object
 * to store the widths and X positions of all the columns.
 * The <code>JTable</code> could then use the
 * <code>SizeSequence</code> object
 * to find the column corresponding to a certain position.
 * The <code>JTable</code> could update the
 * <code>SizeSequence</code> object
 * whenever one or more column sizes changed.
 *
 * <p>
 * The following figure shows the relationship between size and position data
 * for a multi-column component.
 *
 * <center>
 * <img src="doc-files/SizeSequence-1.gif" width=384 height = 100
 * alt="The first item begins at position 0, the second at the position equal
 to the size of the previous item, and so on.">
 * </center>
 * <p>
 * In the figure, the first index (0) corresponds to the first column,
 * the second index (1) to the second column, and so on.
 * The first column's position starts at 0,
 * and the column occupies <em>size<sub>0</sub></em> pixels,
 * where <em>size<sub>0</sub></em> is the value returned by
 * <code>getSize(0)</code>.
 * Thus, the first column ends at <em>size<sub>0</sub></em> - 1.
 * The second column then begins at
 * the position <em>size<sub>0</sub></em>
 * and occupies <em>size<sub>1</sub></em> (<code>getSize(1)</code>) pixels.
 * <p>
 * Note that a <code>SizeSequence</code> object simply represents intervals
 * along an axis.
 * In our examples, the intervals represent height or width in pixels.
 * However, any other unit of measure (for example, time in days)
 * could be just as valid.
 *
 *
 * <h3>Implementation Notes</h3>
 *
 * Normally when storing the size and position of entries,
 * one would choose between
 * storing the sizes or storing their positions
 * instead. The two common operations that are needed during
 * rendering are: <code>getIndex(position)</code>
 * and <code>setSize(index, size)</code>.
 * Whichever choice of internal format is made one of these
 * operations is costly when the number of entries becomes large.
 * If sizes are stored, finding the index of the entry
 * that encloses a particular position is linear in the
 * number of entries. If positions are stored instead, setting
 * the size of an entry at a particular index requires updating
 * the positions of the affected entries, which is also a linear
 * calculation.
 * <p>
 * Like the above techniques this class holds an array of N integers
 * internally but uses a hybrid encoding, which is halfway
 * between the size-based and positional-based approaches.
 * The result is a data structure that takes the same space to store
 * the information but can perform most operations in Log(N) time
 * instead of O(N), where N is the number of entries in the list.
 * <p>
 * Two operations that remain O(N) in the number of entries are
 * the <code>insertEntries</code>
 * and <code>removeEntries</code> methods, both
 * of which are implemented by converting the internal array to
 * a set of integer sizes, copying it into the new array, and then
 * reforming the hybrid representation in place.
 *
 * <p>
 *  <code> SizeSequence </code>对象有效地维护大小和对应位置的有序列表。
 * 对于<code> SizeSequence </code>可能适合的一种情况是在显示多行不等大小的组件中。
 * 在这种情况下,可以使用单个<code> SizeSequence </code>对象来跟踪所有行的高度和Y位置。
 * <p>
 *  另一个示例是多列组件,例如<code> JTable </code>,其中列大小不是全部相等。
 *  <code> JTable </code>可能使用单个<code> SizeSequence </code>对象来存储所有列的宽度和X位置。
 * 然后,<code> JTable </code>可以使用<code> SizeSequence </code>对象来查找与某个位置对应的列。
 * 每当一个或多个列大小更改时,<code> JTable </code>可以更新<code> SizeSequence </code>对象。
 * 
 * <p>
 *  下图显示了多列组件的大小和位置数据之间的关系。
 * 
 * <center>
 *  <img src ="doc-files / SizeSequence-1.gif"width = 384 height = 100 alt ="第一个项目从位置0开始,第二个在位置相等
 * to the size of the previous item, and so on.">
 * </center>
 * <p>
 * 在该图中,第一索引(0)对应于第一列,第二索引(1)对应于第二列,等等。
 * 第一列的位置从0开始,该列占据<em> size <sub> 0 </sub> </em>像素,其中<em> size <sub> 0 </sub> </em>由<code> getSize(0)</code>
 * 返回。
 * 在该图中,第一索引(0)对应于第一列,第二索引(1)对应于第二列,等等。因此,第一列以<em> size <sub> 0 </sub> </em>  -  1结束。
 * 然后第二列从<em> size <sub> 0 </sub>并占用<em> size <sub> 1 </sub> </em>(<code> getSize(1)</code>)像素。
 * <p>
 *  注意,一个<code> SizeSequence </code>对象只是表示沿着一个轴的间隔。在我们的示例中,间隔表示高度或宽度(以像素为单位)。
 * 但是,任何其他计量单位(例如,以天为单位的时间)可能同样有效。
 * 
 *  <h3>实施注意事项</h3>
 * 
 *  通常在存储条目的大小和位置时,可以在存储大小或存储其位置之间进行选择。
 * 渲染期间需要的两个常见操作是：<code> getIndex(position)</code>和<code> setSize(index,size)</code>。
 * 无论选择哪种内部格式,当条目数变大时,这些操作中的一个操作是昂贵的。如果存储了大小,则找到包围特定位置的条目的索引在条目的数量中是线性的。
 * 如果替换地存储位置,则在特定索引处设置条目的大小需要更新受影响条目的位置,这也是线性计算。
 * <p>
 * 像上面的技术一样,这个类在内部拥有一个N个整数的数组,但是使用一个混合编码,这是基于大小和基于位置的方法之间的一半。
 * 结果是采用相同的空间来存储信息,但是可以在Log(N)时间而不是O(N)中执行大多数操作的数据结构,其中N是列表中的条目的数量。
 * <p>
 *  在条目数中保留O(N)的两个操作是<code> insertEntries </code>和<code> removeEntries </code>方法,两者都通过将内部数组转换为一组整数大小,将其复
 * 制到新数组中,然后重新构造混合表示。
 * 
 * 
 * @author Philip Milne
 * @since 1.3
 */

/*
 *   Each method is implemented by taking the minimum and
 *   maximum of the range of integers that need to be operated
 *   upon. All the algorithms work by dividing this range
 *   into two smaller ranges and recursing. The recursion
 *   is terminated when the upper and lower bounds are equal.
 * <p>
 *  每个方法通过取需要操作的整数的范围的最小值和最大值来实现。所有算法通过将该范围划分为两个较小的范围和递归来工作。当上限和下限相等时,递归终止。
 * 
 */

public class SizeSequence {

    private static int[] emptyArray = new int[0];
    private int a[];

    /**
     * Creates a new <code>SizeSequence</code> object
     * that contains no entries.  To add entries, you
     * can use <code>insertEntries</code> or <code>setSizes</code>.
     *
     * <p>
     *  创建一个不包含任何条目的新<对象> SizeSequence </code>对象。
     * 要添加条目,可以使用<code> insertEntries </code>或<code> setSizes </code>。
     * 
     * 
     * @see #insertEntries
     * @see #setSizes(int[])
     */
    public SizeSequence() {
        a = emptyArray;
    }

    /**
     * Creates a new <code>SizeSequence</code> object
     * that contains the specified number of entries,
     * all initialized to have size 0.
     *
     * <p>
     *  创建一个新的<code> SizeSequence </code>对象,该对象包含指定数量的条目,所有条目都初始化为大小为0。
     * 
     * 
     * @param numEntries  the number of sizes to track
     * @exception NegativeArraySizeException if
     *    <code>numEntries &lt; 0</code>
     */
    public SizeSequence(int numEntries) {
        this(numEntries, 0);
    }

    /**
     * Creates a new <code>SizeSequence</code> object
     * that contains the specified number of entries,
     * all initialized to have size <code>value</code>.
     *
     * <p>
     *  创建一个新的<code> SizeSequence </code>对象,该对象包含指定数量的条目,所有条目都初始化为大小<code> value </code>。
     * 
     * 
     * @param numEntries  the number of sizes to track
     * @param value       the initial value of each size
     */
    public SizeSequence(int numEntries, int value) {
        this();
        insertEntries(0, numEntries, value);
    }

    /**
     * Creates a new <code>SizeSequence</code> object
     * that contains the specified sizes.
     *
     * <p>
     *  创建一个包含指定大小的新<对象> SizeSequence </code>对象。
     * 
     * 
     * @param sizes  the array of sizes to be contained in
     *               the <code>SizeSequence</code>
     */
    public SizeSequence(int[] sizes) {
        this();
        setSizes(sizes);
    }

    /**
     * Resets the size sequence to contain <code>length</code> items
     * all with a size of <code>size</code>.
     * <p>
     * 重置大小序列以包含所有大小为<code> size </code>的<code> length </code>项。
     * 
     */
    void setSizes(int length, int size) {
        if (a.length != length) {
            a = new int[length];
        }
        setSizes(0, length, size);
    }

    private int setSizes(int from, int to, int size) {
        if (to <= from) {
            return 0;
        }
        int m = (from + to)/2;
        a[m] = size + setSizes(from, m, size);
        return a[m] + setSizes(m + 1, to, size);
    }

    /**
     * Resets this <code>SizeSequence</code> object,
     * using the data in the <code>sizes</code> argument.
     * This method reinitializes this object so that it
     * contains as many entries as the <code>sizes</code> array.
     * Each entry's size is initialized to the value of the
     * corresponding item in <code>sizes</code>.
     *
     * <p>
     *  使用<code> sizes </code>参数中的数据重置此<code> SizeSequence </code>对象。
     * 此方法重新初始化此对象,使其包含与<code> sizes </code>数组一样多的条目。每个条目的大小初始化为<code> sizes </code>中对应项的值。
     * 
     * 
     * @param sizes  the array of sizes to be contained in
     *               this <code>SizeSequence</code>
     */
    public void setSizes(int[] sizes) {
        if (a.length != sizes.length) {
            a = new int[sizes.length];
        }
        setSizes(0, a.length, sizes);
    }

    private int setSizes(int from, int to, int[] sizes) {
        if (to <= from) {
            return 0;
        }
        int m = (from + to)/2;
        a[m] = sizes[m] + setSizes(from, m, sizes);
        return a[m] + setSizes(m + 1, to, sizes);
    }

    /**
     * Returns the size of all entries.
     *
     * <p>
     *  返回所有条目的大小。
     * 
     * 
     * @return  a new array containing the sizes in this object
     */
    public int[] getSizes() {
        int n = a.length;
        int[] sizes = new int[n];
        getSizes(0, n, sizes);
        return sizes;
    }

    private int getSizes(int from, int to, int[] sizes) {
        if (to <= from) {
            return 0;
        }
        int m = (from + to)/2;
        sizes[m] = a[m] - getSizes(from, m, sizes);
        return a[m] + getSizes(m + 1, to, sizes);
    }

    /**
     * Returns the start position for the specified entry.
     * For example, <code>getPosition(0)</code> returns 0,
     * <code>getPosition(1)</code> is equal to
     *   <code>getSize(0)</code>,
     * <code>getPosition(2)</code> is equal to
     *   <code>getSize(0)</code> + <code>getSize(1)</code>,
     * and so on.
     * <p>Note that if <code>index</code> is greater than
     * <code>length</code> the value returned may
     * be meaningless.
     *
     * <p>
     *  返回指定条目的开始位置。
     * 例如,<code> getPosition(0)</code>返回0,<code> getPosition(1)</code>等于<code> getSize(0)</code>,<code> getP
     * osition </code>等于<code> getSize(0)</code> + <code> getSize(1)</code>,等等。
     *  返回指定条目的开始位置。 <p>请注意,如果<code> index </code>大于<code> length </code>,则返回的值可能无意义。
     * 
     * 
     * @param index  the index of the entry whose position is desired
     * @return       the starting position of the specified entry
     */
    public int getPosition(int index) {
        return getPosition(0, a.length, index);
    }

    private int getPosition(int from, int to, int index) {
        if (to <= from) {
            return 0;
        }
        int m = (from + to)/2;
        if (index <= m) {
            return getPosition(from, m, index);
        }
        else {
            return a[m] + getPosition(m + 1, to, index);
        }
    }

    /**
     * Returns the index of the entry
     * that corresponds to the specified position.
     * For example, <code>getIndex(0)</code> is 0,
     * since the first entry always starts at position 0.
     *
     * <p>
     *  返回与指定位置对应的条目的索引。例如,<code> getIndex(0)</code>为0,因为第一个条目总是从位置0开始。
     * 
     * 
     * @param position  the position of the entry
     * @return  the index of the entry that occupies the specified position
     */
    public int getIndex(int position) {
        return getIndex(0, a.length, position);
    }

    private int getIndex(int from, int to, int position) {
        if (to <= from) {
            return from;
        }
        int m = (from + to)/2;
        int pivot = a[m];
        if (position < pivot) {
           return getIndex(from, m, position);
        }
        else {
            return getIndex(m + 1, to, position - pivot);
        }
    }

    /**
     * Returns the size of the specified entry.
     * If <code>index</code> is out of the range
     * <code>(0 &lt;= index &lt; getSizes().length)</code>
     * the behavior is unspecified.
     *
     * <p>
     *  返回指定条目的大小。如果<code> index </code>超出范围<code>(0&lt; = index&lt; getSizes()。length)</code>,则未指定行为。
     * 
     * 
     * @param index  the index corresponding to the entry
     * @return  the size of the entry
     */
    public int getSize(int index) {
        return getPosition(index + 1) - getPosition(index);
    }

    /**
     * Sets the size of the specified entry.
     * Note that if the value of <code>index</code>
     * does not fall in the range:
     * <code>(0 &lt;= index &lt; getSizes().length)</code>
     * the behavior is unspecified.
     *
     * <p>
     *  设置指定条目的大小。注意,如果<code> index </code>的值不在范围内：<code>(0 <= index <getSizes()。length)</code>,行为未指定。
     * 
     * 
     * @param index  the index corresponding to the entry
     * @param size   the size of the entry
     */
    public void setSize(int index, int size) {
        changeSize(0, a.length, index, size - getSize(index));
    }

    private void changeSize(int from, int to, int index, int delta) {
        if (to <= from) {
            return;
        }
        int m = (from + to)/2;
        if (index <= m) {
            a[m] += delta;
            changeSize(from, m, index, delta);
        }
        else {
            changeSize(m + 1, to, index, delta);
        }
    }

    /**
     * Adds a contiguous group of entries to this <code>SizeSequence</code>.
     * Note that the values of <code>start</code> and
     * <code>length</code> must satisfy the following
     * conditions:  <code>(0 &lt;= start &lt; getSizes().length)
     * AND (length &gt;= 0)</code>.  If these conditions are
     * not met, the behavior is unspecified and an exception
     * may be thrown.
     *
     * <p>
     * 向此<code> SizeSequence </code>中添加一组连续的条目。
     * 注意,<code> start </code>和<code> length </code>的值必须满足以下条件：<code>(0 <= start&lt; getSizes()。
     * length)AND(length& = 0)</code>。如果不满足这些条件,那么行为是未指定的,并且可能抛出异常。
     * 
     * 
     * @param start   the index to be assigned to the first entry
     *                in the group
     * @param length  the number of entries in the group
     * @param value   the size to be assigned to each new entry
     * @exception ArrayIndexOutOfBoundsException if the parameters
     *   are outside of the range:
     *   (<code>0 &lt;= start &lt; (getSizes().length)) AND (length &gt;= 0)</code>
     */
    public void insertEntries(int start, int length, int value) {
        int sizes[] = getSizes();
        int end = start + length;
        int n = a.length + length;
        a = new int[n];
        for (int i = 0; i < start; i++) {
            a[i] = sizes[i] ;
        }
        for (int i = start; i < end; i++) {
            a[i] = value ;
        }
        for (int i = end; i < n; i++) {
            a[i] = sizes[i-length] ;
        }
        setSizes(a);
    }

    /**
     * Removes a contiguous group of entries
     * from this <code>SizeSequence</code>.
     * Note that the values of <code>start</code> and
     * <code>length</code> must satisfy the following
     * conditions:  <code>(0 &lt;= start &lt; getSizes().length)
     * AND (length &gt;= 0)</code>.  If these conditions are
     * not met, the behavior is unspecified and an exception
     * may be thrown.
     *
     * <p>
     *  从此<code> SizeSequence </code>中删除一组连续的条目。
     * 注意,<code> start </code>和<code> length </code>的值必须满足以下条件：<code>(0 <= start&lt; getSizes()。
     * 
     * @param start   the index of the first entry to be removed
     * @param length  the number of entries to be removed
     */
    public void removeEntries(int start, int length) {
        int sizes[] = getSizes();
        int end = start + length;
        int n = a.length - length;
        a = new int[n];
        for (int i = 0; i < start; i++) {
            a[i] = sizes[i] ;
        }
        for (int i = start; i < n; i++) {
            a[i] = sizes[i+length] ;
        }
        setSizes(a);
    }
}
