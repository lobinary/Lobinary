/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2014, Oracle and/or its affiliates. All rights reserved.
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

package java.util;
import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * This class consists exclusively of static methods that operate on or return
 * collections.  It contains polymorphic algorithms that operate on
 * collections, "wrappers", which return a new collection backed by a
 * specified collection, and a few other odds and ends.
 *
 * <p>The methods of this class all throw a <tt>NullPointerException</tt>
 * if the collections or class objects provided to them are null.
 *
 * <p>The documentation for the polymorphic algorithms contained in this class
 * generally includes a brief description of the <i>implementation</i>.  Such
 * descriptions should be regarded as <i>implementation notes</i>, rather than
 * parts of the <i>specification</i>.  Implementors should feel free to
 * substitute other algorithms, so long as the specification itself is adhered
 * to.  (For example, the algorithm used by <tt>sort</tt> does not have to be
 * a mergesort, but it does have to be <i>stable</i>.)
 *
 * <p>The "destructive" algorithms contained in this class, that is, the
 * algorithms that modify the collection on which they operate, are specified
 * to throw <tt>UnsupportedOperationException</tt> if the collection does not
 * support the appropriate mutation primitive(s), such as the <tt>set</tt>
 * method.  These algorithms may, but are not required to, throw this
 * exception if an invocation would have no effect on the collection.  For
 * example, invoking the <tt>sort</tt> method on an unmodifiable list that is
 * already sorted may or may not throw <tt>UnsupportedOperationException</tt>.
 *
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * <p>
 *  这个类完全由对集合进行操作或返回集合的静态方法组成。它包含对集合操作的多态性算法,"wrappers",它返回由指定集合支持的新集合,以及一些其他的奇数和结束。
 * 
 *  <p>如果提供给它们的集合或类对象为null,则此类的方法都会抛出一个<tt> NullPointerException </tt>。
 * 
 *  <p>此类中包含的多态性算法的文档通常包括对<i>实现</i>的简要描述。这样的描述应当被视为<i>实现注释</i>,而不是<i>规范</i>的部分。实现者应该随意替换其他算法,只要遵守规范本身。
 *  (例如,<tt> sort </tt>使用的算法不必是合并排序,但必须<i>稳定</i>。)。
 * 
 * <p>此类中包含的"破坏性"算法,即修改其操作的集合的算法,如果集合不支持适当的突变基元,则指定为抛出<tt> UnsupportedOperationException </tt> s),例如<tt>
 *  set </tt>方法。
 * 如果调用对集合没有影响,这些算法可以(但不是必须)抛出此异常。
 * 例如,对已经排序的不可修改列表调用<tt> sort </tt>方法可能会也可能不会引发<tt> UnsupportedOperationException </tt>。
 * 
 *  <p>此类是的成员
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 *  Java集合框架</a>。
 * 
 * 
 * @author  Josh Bloch
 * @author  Neal Gafter
 * @see     Collection
 * @see     Set
 * @see     List
 * @see     Map
 * @since   1.2
 */

public class Collections {
    // Suppresses default constructor, ensuring non-instantiability.
    private Collections() {
    }

    // Algorithms

    /*
     * Tuning parameters for algorithms - Many of the List algorithms have
     * two implementations, one of which is appropriate for RandomAccess
     * lists, the other for "sequential."  Often, the random access variant
     * yields better performance on small sequential access lists.  The
     * tuning parameters below determine the cutoff point for what constitutes
     * a "small" sequential access list for each algorithm.  The values below
     * were empirically determined to work well for LinkedList. Hopefully
     * they should be reasonable for other sequential access List
     * implementations.  Those doing performance work on this code would
     * do well to validate the values of these parameters from time to time.
     * (The first word of each tuning parameter name is the algorithm to which
     * it applies.)
     * <p>
     *  调整算法的参数 - 许多List算法有两个实现,其中一个适用于RandomAccess列表,另一个适用于"顺序"。通常,随机访问变体在小的顺序访问列表上产生更好的性能。
     * 下面的调谐参数确定什么构成每个算法的"小"顺序访问列表的截止点。下面的值经验性地确定为对LinkedList工作良好。希望他们应该是合理的其他顺序访问列表实现。
     * 那些在这个代码上进行性能工作的人将不时地验证这些参数的值。 (每个调整参数名称的第一个字是它应用的算法。)。
     * 
     */
    private static final int BINARYSEARCH_THRESHOLD   = 5000;
    private static final int REVERSE_THRESHOLD        =   18;
    private static final int SHUFFLE_THRESHOLD        =    5;
    private static final int FILL_THRESHOLD           =   25;
    private static final int ROTATE_THRESHOLD         =  100;
    private static final int COPY_THRESHOLD           =   10;
    private static final int REPLACEALL_THRESHOLD     =   11;
    private static final int INDEXOFSUBLIST_THRESHOLD =   35;

    /**
     * Sorts the specified list into ascending order, according to the
     * {@linkplain Comparable natural ordering} of its elements.
     * All elements in the list must implement the {@link Comparable}
     * interface.  Furthermore, all elements in the list must be
     * <i>mutually comparable</i> (that is, {@code e1.compareTo(e2)}
     * must not throw a {@code ClassCastException} for any elements
     * {@code e1} and {@code e2} in the list).
     *
     * <p>This sort is guaranteed to be <i>stable</i>:  equal elements will
     * not be reordered as a result of the sort.
     *
     * <p>The specified list must be modifiable, but need not be resizable.
     *
     * @implNote
     * This implementation defers to the {@link List#sort(Comparator)}
     * method using the specified list and a {@code null} comparator.
     *
     * <p>
     * 根据其元素的{@linkplain Comparable natural ordering}将升序排序指定的列表。列表中的所有元素都必须实现{@link Comparable}接口。
     * 此外,列表中的所有元素必须<i>相互比较</i>(也就是说,{@code e1.compareTo(e2)}不得为任何元素{@code e1}抛出{@code ClassCastException} {@code e2}
     * )。
     * 根据其元素的{@linkplain Comparable natural ordering}将升序排序指定的列表。列表中的所有元素都必须实现{@link Comparable}接口。
     * 
     *  <p>这种类型保证是<i>稳定</i>：等于元素将不会被重新排序作为排序的结果。
     * 
     *  <p>指定的列表必须可修改,但不必可调整大小。
     * 
     *  @implNote这个实现使用指定的列表和一个{@code null}比较器来推迟{@link List#sort(Comparator)}方法。
     * 
     * 
     * @param  <T> the class of the objects in the list
     * @param  list the list to be sorted.
     * @throws ClassCastException if the list contains elements that are not
     *         <i>mutually comparable</i> (for example, strings and integers).
     * @throws UnsupportedOperationException if the specified list's
     *         list-iterator does not support the {@code set} operation.
     * @throws IllegalArgumentException (optional) if the implementation
     *         detects that the natural ordering of the list elements is
     *         found to violate the {@link Comparable} contract
     * @see List#sort(Comparator)
     */
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<? super T>> void sort(List<T> list) {
        list.sort(null);
    }

    /**
     * Sorts the specified list according to the order induced by the
     * specified comparator.  All elements in the list must be <i>mutually
     * comparable</i> using the specified comparator (that is,
     * {@code c.compare(e1, e2)} must not throw a {@code ClassCastException}
     * for any elements {@code e1} and {@code e2} in the list).
     *
     * <p>This sort is guaranteed to be <i>stable</i>:  equal elements will
     * not be reordered as a result of the sort.
     *
     * <p>The specified list must be modifiable, but need not be resizable.
     *
     * @implNote
     * This implementation defers to the {@link List#sort(Comparator)}
     * method using the specified list and comparator.
     *
     * <p>
     *  根据指定的比较器引起的顺序对指定的列表进行排序。
     * 列表中的所有元素必须使用指定的比较器<i>互相比较</i>(即{@code c.compare(e1,e2)}不能为任何元素{@code ClassCastException}代码e1}和{@code e2}
     * )。
     *  根据指定的比较器引起的顺序对指定的列表进行排序。
     * 
     *  <p>这种类型保证是<i>稳定</i>：等于元素将不会被重新排序作为排序的结果。
     * 
     *  <p>指定的列表必须可修改,但不必可调整大小。
     * 
     *  @implNote此实现使用指定的列表和比较器,推迟到{@link List#sort(Comparator)}方法。
     * 
     * 
     * @param  <T> the class of the objects in the list
     * @param  list the list to be sorted.
     * @param  c the comparator to determine the order of the list.  A
     *        {@code null} value indicates that the elements' <i>natural
     *        ordering</i> should be used.
     * @throws ClassCastException if the list contains elements that are not
     *         <i>mutually comparable</i> using the specified comparator.
     * @throws UnsupportedOperationException if the specified list's
     *         list-iterator does not support the {@code set} operation.
     * @throws IllegalArgumentException (optional) if the comparator is
     *         found to violate the {@link Comparator} contract
     * @see List#sort(Comparator)
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> void sort(List<T> list, Comparator<? super T> c) {
        list.sort(c);
    }


    /**
     * Searches the specified list for the specified object using the binary
     * search algorithm.  The list must be sorted into ascending order
     * according to the {@linkplain Comparable natural ordering} of its
     * elements (as by the {@link #sort(List)} method) prior to making this
     * call.  If it is not sorted, the results are undefined.  If the list
     * contains multiple elements equal to the specified object, there is no
     * guarantee which one will be found.
     *
     * <p>This method runs in log(n) time for a "random access" list (which
     * provides near-constant-time positional access).  If the specified list
     * does not implement the {@link RandomAccess} interface and is large,
     * this method will do an iterator-based binary search that performs
     * O(n) link traversals and O(log n) element comparisons.
     *
     * <p>
     * 使用二分查找算法搜索指定对象的指定列表。
     * 在进行此调用之前,列表必须根据其元素的{@linkplain Comparable natural ordering}(如{@link #sort(List)}方法)按升序排序。
     * 如果没有排序,结果是未定义的。如果列表包含等于指定对象的多个元素,则不能保证将找到哪个元素。
     * 
     *  <p>对于"随机访问"列表(其提供接近恒定时间的位置访问),该方法在log(n)时间中运行。
     * 如果指定的列表不实现{@link RandomAccess}接口并且很大,这个方法将执行基于迭代器的二进制搜索,执行O(n)链接遍历和O(log n)元素比较。
     * 
     * 
     * @param  <T> the class of the objects in the list
     * @param  list the list to be searched.
     * @param  key the key to be searched for.
     * @return the index of the search key, if it is contained in the list;
     *         otherwise, <tt>(-(<i>insertion point</i>) - 1)</tt>.  The
     *         <i>insertion point</i> is defined as the point at which the
     *         key would be inserted into the list: the index of the first
     *         element greater than the key, or <tt>list.size()</tt> if all
     *         elements in the list are less than the specified key.  Note
     *         that this guarantees that the return value will be &gt;= 0 if
     *         and only if the key is found.
     * @throws ClassCastException if the list contains elements that are not
     *         <i>mutually comparable</i> (for example, strings and
     *         integers), or the search key is not mutually comparable
     *         with the elements of the list.
     */
    public static <T>
    int binarySearch(List<? extends Comparable<? super T>> list, T key) {
        if (list instanceof RandomAccess || list.size()<BINARYSEARCH_THRESHOLD)
            return Collections.indexedBinarySearch(list, key);
        else
            return Collections.iteratorBinarySearch(list, key);
    }

    private static <T>
    int indexedBinarySearch(List<? extends Comparable<? super T>> list, T key) {
        int low = 0;
        int high = list.size()-1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            Comparable<? super T> midVal = list.get(mid);
            int cmp = midVal.compareTo(key);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return mid; // key found
        }
        return -(low + 1);  // key not found
    }

    private static <T>
    int iteratorBinarySearch(List<? extends Comparable<? super T>> list, T key)
    {
        int low = 0;
        int high = list.size()-1;
        ListIterator<? extends Comparable<? super T>> i = list.listIterator();

        while (low <= high) {
            int mid = (low + high) >>> 1;
            Comparable<? super T> midVal = get(i, mid);
            int cmp = midVal.compareTo(key);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return mid; // key found
        }
        return -(low + 1);  // key not found
    }

    /**
     * Gets the ith element from the given list by repositioning the specified
     * list listIterator.
     * <p>
     *  通过重新定位指定的列表listIterator从给定列表获取第i个元素。
     * 
     */
    private static <T> T get(ListIterator<? extends T> i, int index) {
        T obj = null;
        int pos = i.nextIndex();
        if (pos <= index) {
            do {
                obj = i.next();
            } while (pos++ < index);
        } else {
            do {
                obj = i.previous();
            } while (--pos > index);
        }
        return obj;
    }

    /**
     * Searches the specified list for the specified object using the binary
     * search algorithm.  The list must be sorted into ascending order
     * according to the specified comparator (as by the
     * {@link #sort(List, Comparator) sort(List, Comparator)}
     * method), prior to making this call.  If it is
     * not sorted, the results are undefined.  If the list contains multiple
     * elements equal to the specified object, there is no guarantee which one
     * will be found.
     *
     * <p>This method runs in log(n) time for a "random access" list (which
     * provides near-constant-time positional access).  If the specified list
     * does not implement the {@link RandomAccess} interface and is large,
     * this method will do an iterator-based binary search that performs
     * O(n) link traversals and O(log n) element comparisons.
     *
     * <p>
     *  使用二分查找算法搜索指定对象的指定列表。
     * 在进行此调用之前,列表必须根据指定的比较器(如{@link #sort(List,Comparator)sort(List,Comparator)}方法)按升序排序。如果没有排序,结果是未定义的。
     * 如果列表包含等于指定对象的多个元素,则不能保证将找到哪个元素。
     * 
     * <p>对于"随机访问"列表(其提供接近恒定时间的位置访问),该方法在log(n)时间中运行。
     * 如果指定的列表不实现{@link RandomAccess}接口并且很大,这个方法将执行基于迭代器的二进制搜索,执行O(n)链接遍历和O(log n)元素比较。
     * 
     * 
     * @param  <T> the class of the objects in the list
     * @param  list the list to be searched.
     * @param  key the key to be searched for.
     * @param  c the comparator by which the list is ordered.
     *         A <tt>null</tt> value indicates that the elements'
     *         {@linkplain Comparable natural ordering} should be used.
     * @return the index of the search key, if it is contained in the list;
     *         otherwise, <tt>(-(<i>insertion point</i>) - 1)</tt>.  The
     *         <i>insertion point</i> is defined as the point at which the
     *         key would be inserted into the list: the index of the first
     *         element greater than the key, or <tt>list.size()</tt> if all
     *         elements in the list are less than the specified key.  Note
     *         that this guarantees that the return value will be &gt;= 0 if
     *         and only if the key is found.
     * @throws ClassCastException if the list contains elements that are not
     *         <i>mutually comparable</i> using the specified comparator,
     *         or the search key is not mutually comparable with the
     *         elements of the list using this comparator.
     */
    @SuppressWarnings("unchecked")
    public static <T> int binarySearch(List<? extends T> list, T key, Comparator<? super T> c) {
        if (c==null)
            return binarySearch((List<? extends Comparable<? super T>>) list, key);

        if (list instanceof RandomAccess || list.size()<BINARYSEARCH_THRESHOLD)
            return Collections.indexedBinarySearch(list, key, c);
        else
            return Collections.iteratorBinarySearch(list, key, c);
    }

    private static <T> int indexedBinarySearch(List<? extends T> l, T key, Comparator<? super T> c) {
        int low = 0;
        int high = l.size()-1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            T midVal = l.get(mid);
            int cmp = c.compare(midVal, key);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return mid; // key found
        }
        return -(low + 1);  // key not found
    }

    private static <T> int iteratorBinarySearch(List<? extends T> l, T key, Comparator<? super T> c) {
        int low = 0;
        int high = l.size()-1;
        ListIterator<? extends T> i = l.listIterator();

        while (low <= high) {
            int mid = (low + high) >>> 1;
            T midVal = get(i, mid);
            int cmp = c.compare(midVal, key);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return mid; // key found
        }
        return -(low + 1);  // key not found
    }

    /**
     * Reverses the order of the elements in the specified list.<p>
     *
     * This method runs in linear time.
     *
     * <p>
     *  颠倒指定列表中元素的顺序。<p>
     * 
     *  此方法在线性时间运行。
     * 
     * 
     * @param  list the list whose elements are to be reversed.
     * @throws UnsupportedOperationException if the specified list or
     *         its list-iterator does not support the <tt>set</tt> operation.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void reverse(List<?> list) {
        int size = list.size();
        if (size < REVERSE_THRESHOLD || list instanceof RandomAccess) {
            for (int i=0, mid=size>>1, j=size-1; i<mid; i++, j--)
                swap(list, i, j);
        } else {
            // instead of using a raw type here, it's possible to capture
            // the wildcard but it will require a call to a supplementary
            // private method
            ListIterator fwd = list.listIterator();
            ListIterator rev = list.listIterator(size);
            for (int i=0, mid=list.size()>>1; i<mid; i++) {
                Object tmp = fwd.next();
                fwd.set(rev.previous());
                rev.set(tmp);
            }
        }
    }

    /**
     * Randomly permutes the specified list using a default source of
     * randomness.  All permutations occur with approximately equal
     * likelihood.
     *
     * <p>The hedge "approximately" is used in the foregoing description because
     * default source of randomness is only approximately an unbiased source
     * of independently chosen bits. If it were a perfect source of randomly
     * chosen bits, then the algorithm would choose permutations with perfect
     * uniformity.
     *
     * <p>This implementation traverses the list backwards, from the last
     * element up to the second, repeatedly swapping a randomly selected element
     * into the "current position".  Elements are randomly selected from the
     * portion of the list that runs from the first element to the current
     * position, inclusive.
     *
     * <p>This method runs in linear time.  If the specified list does not
     * implement the {@link RandomAccess} interface and is large, this
     * implementation dumps the specified list into an array before shuffling
     * it, and dumps the shuffled array back into the list.  This avoids the
     * quadratic behavior that would result from shuffling a "sequential
     * access" list in place.
     *
     * <p>
     *  使用默认的随机性随机排列指定的列表。所有排列以大致相等的可能性发生。
     * 
     *  在上述描述中使用对冲"大约",因为默认的随机性源仅仅是大约独立选择的比特的无偏差源。如果它是随机选择的比特的完美来源,则算法将选择具有完全均匀性的置换。
     * 
     *  <p>这个实现从最后一个元素向后遍历列表,重复地将随机选择的元素交换到"当前位置"。元素是从列表中从第一个元素到当前位置(包括第一个元素)的部分中随机选择的。
     * 
     * <p>此方法在线性时间运行。如果指定的列表不实现{@link RandomAccess}接口并且很大,则此实现在将指定的列表转储到数组之前对其进行混排,并将混洗后的数组转储回列表中。
     * 这避免了将"顺序访问"列表移到适当位置而产生的二次行为。
     * 
     * 
     * @param  list the list to be shuffled.
     * @throws UnsupportedOperationException if the specified list or
     *         its list-iterator does not support the <tt>set</tt> operation.
     */
    public static void shuffle(List<?> list) {
        Random rnd = r;
        if (rnd == null)
            r = rnd = new Random(); // harmless race.
        shuffle(list, rnd);
    }

    private static Random r;

    /**
     * Randomly permute the specified list using the specified source of
     * randomness.  All permutations occur with equal likelihood
     * assuming that the source of randomness is fair.<p>
     *
     * This implementation traverses the list backwards, from the last element
     * up to the second, repeatedly swapping a randomly selected element into
     * the "current position".  Elements are randomly selected from the
     * portion of the list that runs from the first element to the current
     * position, inclusive.<p>
     *
     * This method runs in linear time.  If the specified list does not
     * implement the {@link RandomAccess} interface and is large, this
     * implementation dumps the specified list into an array before shuffling
     * it, and dumps the shuffled array back into the list.  This avoids the
     * quadratic behavior that would result from shuffling a "sequential
     * access" list in place.
     *
     * <p>
     *  使用指定的随机源随机替换指定的列表。假设随机性的来源是公平的,所有排列以相等的可能性发生
     * 
     *  这个实现从最后一个元素向后遍历列表,直到第二个,重复地将随机选择的元素交换到"当前位置"。元素是从列表中从第一个元素到当前位置(包括第一个元素)的部分中随机选择的。<p>
     * 
     *  此方法在线性时间运行。如果指定的列表不实现{@link RandomAccess}接口并且很大,则此实现在将指定的列表转储到数组之前对其进行混排,并将混洗后的数组转储回列表中。
     * 这避免了将"顺序访问"列表移到适当位置而产生的二次行为。
     * 
     * 
     * @param  list the list to be shuffled.
     * @param  rnd the source of randomness to use to shuffle the list.
     * @throws UnsupportedOperationException if the specified list or its
     *         list-iterator does not support the <tt>set</tt> operation.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void shuffle(List<?> list, Random rnd) {
        int size = list.size();
        if (size < SHUFFLE_THRESHOLD || list instanceof RandomAccess) {
            for (int i=size; i>1; i--)
                swap(list, i-1, rnd.nextInt(i));
        } else {
            Object arr[] = list.toArray();

            // Shuffle array
            for (int i=size; i>1; i--)
                swap(arr, i-1, rnd.nextInt(i));

            // Dump array back into list
            // instead of using a raw type here, it's possible to capture
            // the wildcard but it will require a call to a supplementary
            // private method
            ListIterator it = list.listIterator();
            for (int i=0; i<arr.length; i++) {
                it.next();
                it.set(arr[i]);
            }
        }
    }

    /**
     * Swaps the elements at the specified positions in the specified list.
     * (If the specified positions are equal, invoking this method leaves
     * the list unchanged.)
     *
     * <p>
     *  在指定列表中的指定位置交换元素。 (如果指定的位置相等,调用此方法会使列表保持不变。)
     * 
     * 
     * @param list The list in which to swap elements.
     * @param i the index of one element to be swapped.
     * @param j the index of the other element to be swapped.
     * @throws IndexOutOfBoundsException if either <tt>i</tt> or <tt>j</tt>
     *         is out of range (i &lt; 0 || i &gt;= list.size()
     *         || j &lt; 0 || j &gt;= list.size()).
     * @since 1.4
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void swap(List<?> list, int i, int j) {
        // instead of using a raw type here, it's possible to capture
        // the wildcard but it will require a call to a supplementary
        // private method
        final List l = list;
        l.set(i, l.set(j, l.get(i)));
    }

    /**
     * Swaps the two specified elements in the specified array.
     * <p>
     *  交换指定数组中的两个指定元素。
     * 
     */
    private static void swap(Object[] arr, int i, int j) {
        Object tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /**
     * Replaces all of the elements of the specified list with the specified
     * element. <p>
     *
     * This method runs in linear time.
     *
     * <p>
     *  用指定的元素替换指定列表的所有元素。 <p>
     * 
     * 此方法在线性时间运行。
     * 
     * 
     * @param  <T> the class of the objects in the list
     * @param  list the list to be filled with the specified element.
     * @param  obj The element with which to fill the specified list.
     * @throws UnsupportedOperationException if the specified list or its
     *         list-iterator does not support the <tt>set</tt> operation.
     */
    public static <T> void fill(List<? super T> list, T obj) {
        int size = list.size();

        if (size < FILL_THRESHOLD || list instanceof RandomAccess) {
            for (int i=0; i<size; i++)
                list.set(i, obj);
        } else {
            ListIterator<? super T> itr = list.listIterator();
            for (int i=0; i<size; i++) {
                itr.next();
                itr.set(obj);
            }
        }
    }

    /**
     * Copies all of the elements from one list into another.  After the
     * operation, the index of each copied element in the destination list
     * will be identical to its index in the source list.  The destination
     * list must be at least as long as the source list.  If it is longer, the
     * remaining elements in the destination list are unaffected. <p>
     *
     * This method runs in linear time.
     *
     * <p>
     *  将所有元素从一个列表复制到另一个列表。操作之后,目标列表中每个复制元素的索引将与其在源列表中的索引相同。目标列表必须至少与源列表一样长。如果更长,目标列表中的其余元素不受影响。 <p>
     * 
     *  此方法在线性时间运行。
     * 
     * 
     * @param  <T> the class of the objects in the lists
     * @param  dest The destination list.
     * @param  src The source list.
     * @throws IndexOutOfBoundsException if the destination list is too small
     *         to contain the entire source List.
     * @throws UnsupportedOperationException if the destination list's
     *         list-iterator does not support the <tt>set</tt> operation.
     */
    public static <T> void copy(List<? super T> dest, List<? extends T> src) {
        int srcSize = src.size();
        if (srcSize > dest.size())
            throw new IndexOutOfBoundsException("Source does not fit in dest");

        if (srcSize < COPY_THRESHOLD ||
            (src instanceof RandomAccess && dest instanceof RandomAccess)) {
            for (int i=0; i<srcSize; i++)
                dest.set(i, src.get(i));
        } else {
            ListIterator<? super T> di=dest.listIterator();
            ListIterator<? extends T> si=src.listIterator();
            for (int i=0; i<srcSize; i++) {
                di.next();
                di.set(si.next());
            }
        }
    }

    /**
     * Returns the minimum element of the given collection, according to the
     * <i>natural ordering</i> of its elements.  All elements in the
     * collection must implement the <tt>Comparable</tt> interface.
     * Furthermore, all elements in the collection must be <i>mutually
     * comparable</i> (that is, <tt>e1.compareTo(e2)</tt> must not throw a
     * <tt>ClassCastException</tt> for any elements <tt>e1</tt> and
     * <tt>e2</tt> in the collection).<p>
     *
     * This method iterates over the entire collection, hence it requires
     * time proportional to the size of the collection.
     *
     * <p>
     *  根据元素的<i>自然排序</i>,返回给定集合的最小元素。集合中的所有元素都必须实现<tt>可比较</tt>界面。
     * 此外,集合中的所有元素必须<i>相互比较</i>(即,<tt> e1.compareTo(e2)</tt>不得对任何元素抛出<tt> ClassCastException </tt> <tt> e1 </tt>
     * 和<tt> e2 </tt>)。
     *  根据元素的<i>自然排序</i>,返回给定集合的最小元素。集合中的所有元素都必须实现<tt>可比较</tt>界面。<p>。
     * 
     *  此方法遍历整个集合,因此它需要与集合大小成比例的时间。
     * 
     * 
     * @param  <T> the class of the objects in the collection
     * @param  coll the collection whose minimum element is to be determined.
     * @return the minimum element of the given collection, according
     *         to the <i>natural ordering</i> of its elements.
     * @throws ClassCastException if the collection contains elements that are
     *         not <i>mutually comparable</i> (for example, strings and
     *         integers).
     * @throws NoSuchElementException if the collection is empty.
     * @see Comparable
     */
    public static <T extends Object & Comparable<? super T>> T min(Collection<? extends T> coll) {
        Iterator<? extends T> i = coll.iterator();
        T candidate = i.next();

        while (i.hasNext()) {
            T next = i.next();
            if (next.compareTo(candidate) < 0)
                candidate = next;
        }
        return candidate;
    }

    /**
     * Returns the minimum element of the given collection, according to the
     * order induced by the specified comparator.  All elements in the
     * collection must be <i>mutually comparable</i> by the specified
     * comparator (that is, <tt>comp.compare(e1, e2)</tt> must not throw a
     * <tt>ClassCastException</tt> for any elements <tt>e1</tt> and
     * <tt>e2</tt> in the collection).<p>
     *
     * This method iterates over the entire collection, hence it requires
     * time proportional to the size of the collection.
     *
     * <p>
     *  根据指定比较器引起的顺序返回给定集合的最小元素。
     * 集合中的所有元素必须通过指定的比较器<i>互相比较</i>(即<tt> comp.compare(e1,e2)</tt>不得抛出<tt> ClassCastException </tt >对于集合中的任
     * 何元素<tt> e1 </tt>和<tt> e2 </tt>)。
     *  根据指定比较器引起的顺序返回给定集合的最小元素。<p>。
     * 
     *  此方法遍历整个集合,因此它需要与集合大小成比例的时间。
     * 
     * 
     * @param  <T> the class of the objects in the collection
     * @param  coll the collection whose minimum element is to be determined.
     * @param  comp the comparator with which to determine the minimum element.
     *         A <tt>null</tt> value indicates that the elements' <i>natural
     *         ordering</i> should be used.
     * @return the minimum element of the given collection, according
     *         to the specified comparator.
     * @throws ClassCastException if the collection contains elements that are
     *         not <i>mutually comparable</i> using the specified comparator.
     * @throws NoSuchElementException if the collection is empty.
     * @see Comparable
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> T min(Collection<? extends T> coll, Comparator<? super T> comp) {
        if (comp==null)
            return (T)min((Collection) coll);

        Iterator<? extends T> i = coll.iterator();
        T candidate = i.next();

        while (i.hasNext()) {
            T next = i.next();
            if (comp.compare(next, candidate) < 0)
                candidate = next;
        }
        return candidate;
    }

    /**
     * Returns the maximum element of the given collection, according to the
     * <i>natural ordering</i> of its elements.  All elements in the
     * collection must implement the <tt>Comparable</tt> interface.
     * Furthermore, all elements in the collection must be <i>mutually
     * comparable</i> (that is, <tt>e1.compareTo(e2)</tt> must not throw a
     * <tt>ClassCastException</tt> for any elements <tt>e1</tt> and
     * <tt>e2</tt> in the collection).<p>
     *
     * This method iterates over the entire collection, hence it requires
     * time proportional to the size of the collection.
     *
     * <p>
     * 根据元素的<i>自然排序</i>,返回给定集合的最大元素。集合中的所有元素都必须实现<tt>可比较</tt>界面。
     * 此外,集合中的所有元素必须<i>相互比较</i>(即,<tt> e1.compareTo(e2)</tt>不得对任何元素抛出<tt> ClassCastException </tt> <tt> e1 </tt>
     * 和<tt> e2 </tt>)。
     * 根据元素的<i>自然排序</i>,返回给定集合的最大元素。集合中的所有元素都必须实现<tt>可比较</tt>界面。<p>。
     * 
     *  此方法遍历整个集合,因此它需要与集合大小成比例的时间。
     * 
     * 
     * @param  <T> the class of the objects in the collection
     * @param  coll the collection whose maximum element is to be determined.
     * @return the maximum element of the given collection, according
     *         to the <i>natural ordering</i> of its elements.
     * @throws ClassCastException if the collection contains elements that are
     *         not <i>mutually comparable</i> (for example, strings and
     *         integers).
     * @throws NoSuchElementException if the collection is empty.
     * @see Comparable
     */
    public static <T extends Object & Comparable<? super T>> T max(Collection<? extends T> coll) {
        Iterator<? extends T> i = coll.iterator();
        T candidate = i.next();

        while (i.hasNext()) {
            T next = i.next();
            if (next.compareTo(candidate) > 0)
                candidate = next;
        }
        return candidate;
    }

    /**
     * Returns the maximum element of the given collection, according to the
     * order induced by the specified comparator.  All elements in the
     * collection must be <i>mutually comparable</i> by the specified
     * comparator (that is, <tt>comp.compare(e1, e2)</tt> must not throw a
     * <tt>ClassCastException</tt> for any elements <tt>e1</tt> and
     * <tt>e2</tt> in the collection).<p>
     *
     * This method iterates over the entire collection, hence it requires
     * time proportional to the size of the collection.
     *
     * <p>
     *  根据指定比较器引起的顺序返回给定集合的最大元素。
     * 集合中的所有元素必须通过指定的比较器<i>互相比较</i>(即<tt> comp.compare(e1,e2)</tt>不得抛出<tt> ClassCastException </tt >对于集合中的任
     * 何元素<tt> e1 </tt>和<tt> e2 </tt>)。
     *  根据指定比较器引起的顺序返回给定集合的最大元素。<p>。
     * 
     *  此方法遍历整个集合,因此它需要与集合大小成比例的时间。
     * 
     * 
     * @param  <T> the class of the objects in the collection
     * @param  coll the collection whose maximum element is to be determined.
     * @param  comp the comparator with which to determine the maximum element.
     *         A <tt>null</tt> value indicates that the elements' <i>natural
     *        ordering</i> should be used.
     * @return the maximum element of the given collection, according
     *         to the specified comparator.
     * @throws ClassCastException if the collection contains elements that are
     *         not <i>mutually comparable</i> using the specified comparator.
     * @throws NoSuchElementException if the collection is empty.
     * @see Comparable
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> T max(Collection<? extends T> coll, Comparator<? super T> comp) {
        if (comp==null)
            return (T)max((Collection) coll);

        Iterator<? extends T> i = coll.iterator();
        T candidate = i.next();

        while (i.hasNext()) {
            T next = i.next();
            if (comp.compare(next, candidate) > 0)
                candidate = next;
        }
        return candidate;
    }

    /**
     * Rotates the elements in the specified list by the specified distance.
     * After calling this method, the element at index <tt>i</tt> will be
     * the element previously at index <tt>(i - distance)</tt> mod
     * <tt>list.size()</tt>, for all values of <tt>i</tt> between <tt>0</tt>
     * and <tt>list.size()-1</tt>, inclusive.  (This method has no effect on
     * the size of the list.)
     *
     * <p>For example, suppose <tt>list</tt> comprises<tt> [t, a, n, k, s]</tt>.
     * After invoking <tt>Collections.rotate(list, 1)</tt> (or
     * <tt>Collections.rotate(list, -4)</tt>), <tt>list</tt> will comprise
     * <tt>[s, t, a, n, k]</tt>.
     *
     * <p>Note that this method can usefully be applied to sublists to
     * move one or more elements within a list while preserving the
     * order of the remaining elements.  For example, the following idiom
     * moves the element at index <tt>j</tt> forward to position
     * <tt>k</tt> (which must be greater than or equal to <tt>j</tt>):
     * <pre>
     *     Collections.rotate(list.subList(j, k+1), -1);
     * </pre>
     * To make this concrete, suppose <tt>list</tt> comprises
     * <tt>[a, b, c, d, e]</tt>.  To move the element at index <tt>1</tt>
     * (<tt>b</tt>) forward two positions, perform the following invocation:
     * <pre>
     *     Collections.rotate(l.subList(1, 4), -1);
     * </pre>
     * The resulting list is <tt>[a, c, d, b, e]</tt>.
     *
     * <p>To move more than one element forward, increase the absolute value
     * of the rotation distance.  To move elements backward, use a positive
     * shift distance.
     *
     * <p>If the specified list is small or implements the {@link
     * RandomAccess} interface, this implementation exchanges the first
     * element into the location it should go, and then repeatedly exchanges
     * the displaced element into the location it should go until a displaced
     * element is swapped into the first element.  If necessary, the process
     * is repeated on the second and successive elements, until the rotation
     * is complete.  If the specified list is large and doesn't implement the
     * <tt>RandomAccess</tt> interface, this implementation breaks the
     * list into two sublist views around index <tt>-distance mod size</tt>.
     * Then the {@link #reverse(List)} method is invoked on each sublist view,
     * and finally it is invoked on the entire list.  For a more complete
     * description of both algorithms, see Section 2.3 of Jon Bentley's
     * <i>Programming Pearls</i> (Addison-Wesley, 1986).
     *
     * <p>
     *  将指定列表中的元素旋转指定的距离。
     * 在调用此方法之后,索引<tt> i </tt>处的元素将是之前在索引<tt>(i  -  distance)</tt> mod <tt> list.size()</tt>对于<tt> 0 </tt>和<tt>
     *  list.size() -  1 </tt>之间的所有值<tt> i </tt> (此方法对列表的大小没有影响。
     *  将指定列表中的元素旋转指定的距离。)。
     * 
     * <p>例如,假设<tt>列表</tt>包含<tt> [t,a,n,k,s] </tt>。
     * 调用<tt> Collections.rotate(list,1)</tt>(或<tt> Collections.rotate(list,-4)</tt>)后,<tt>列表</tt> [s,t,a,n,
     * k] </tt>。
     * <p>例如,假设<tt>列表</tt>包含<tt> [t,a,n,k,s] </tt>。
     * 
     *  <p>请注意,此方法可以有效地应用于子列表,以移动列表中的一个或多个元素,同时保留其余元素的顺序。
     * 例如,以下成语将位于索引<tt> j </tt>的元素移动到位置<tt> k </tt>(必须大于或等于<tt> j </tt>)：。
     * <pre>
     *  Collections.rotate(list.subList(j,k + 1),-1);
     * </pre>
     *  为了具体化,假设<tt>列表</tt>包括<tt> [a,b,c,d,e] </tt>。要将索引<tt> 1 </tt>(<tt> b </tt>)处的元素向前移动两个位置,请执行以下调用：
     * <pre>
     *  Collections.rotate(l.subList(1,4),-1);
     * </pre>
     *  结果列表是<tt> [a,c,d,b,e] </tt>。
     * 
     *  <p>要向前移动多个元素,请增加旋转距离的绝对值。要向后移动元素,请使用正移位距离。
     * 
     * <p>如果指定的列表很小或者实现了{@link RandomAccess}接口,这个实现将第一个元素交换到它应该去的位置,然后重复地交换被移动的元素到它应该去的位置,直到被移动的元素交换到第一个元素。
     * 如果需要,对第二和后续元件重复该过程,直到旋转完成。
     * 如果指定的列表很大,并且不实现<tt> RandomAccess </tt>接口,则此实现将列表分成两个子列表视图,围绕索引<tt> -distance mod size </tt>。
     * 然后在每个子列表视图上调用{@link #reverse(List)}方法,最后在整个列表上调用它。
     * 有关这两种算法的更完整的描述,参见Jon Bentley的<i> Programming Pearls </i>(Addison-Wesley,1986)第2.3节。
     * 
     * 
     * @param list the list to be rotated.
     * @param distance the distance to rotate the list.  There are no
     *        constraints on this value; it may be zero, negative, or
     *        greater than <tt>list.size()</tt>.
     * @throws UnsupportedOperationException if the specified list or
     *         its list-iterator does not support the <tt>set</tt> operation.
     * @since 1.4
     */
    public static void rotate(List<?> list, int distance) {
        if (list instanceof RandomAccess || list.size() < ROTATE_THRESHOLD)
            rotate1(list, distance);
        else
            rotate2(list, distance);
    }

    private static <T> void rotate1(List<T> list, int distance) {
        int size = list.size();
        if (size == 0)
            return;
        distance = distance % size;
        if (distance < 0)
            distance += size;
        if (distance == 0)
            return;

        for (int cycleStart = 0, nMoved = 0; nMoved != size; cycleStart++) {
            T displaced = list.get(cycleStart);
            int i = cycleStart;
            do {
                i += distance;
                if (i >= size)
                    i -= size;
                displaced = list.set(i, displaced);
                nMoved ++;
            } while (i != cycleStart);
        }
    }

    private static void rotate2(List<?> list, int distance) {
        int size = list.size();
        if (size == 0)
            return;
        int mid =  -distance % size;
        if (mid < 0)
            mid += size;
        if (mid == 0)
            return;

        reverse(list.subList(0, mid));
        reverse(list.subList(mid, size));
        reverse(list);
    }

    /**
     * Replaces all occurrences of one specified value in a list with another.
     * More formally, replaces with <tt>newVal</tt> each element <tt>e</tt>
     * in <tt>list</tt> such that
     * <tt>(oldVal==null ? e==null : oldVal.equals(e))</tt>.
     * (This method has no effect on the size of the list.)
     *
     * <p>
     *  将列表中所有出现的指定值替换为另一个。
     * 更正式地,用<tt> newVal </tt>替换<tt>列表</tt>中的每个元素<tt> e </tt>,使得<tt>(oldVal == null?e == null：oldVal。
     * 等于(e))</tt>。 (此方法对列表的大小没有影响。)。
     * 
     * 
     * @param  <T> the class of the objects in the list
     * @param list the list in which replacement is to occur.
     * @param oldVal the old value to be replaced.
     * @param newVal the new value with which <tt>oldVal</tt> is to be
     *        replaced.
     * @return <tt>true</tt> if <tt>list</tt> contained one or more elements
     *         <tt>e</tt> such that
     *         <tt>(oldVal==null ?  e==null : oldVal.equals(e))</tt>.
     * @throws UnsupportedOperationException if the specified list or
     *         its list-iterator does not support the <tt>set</tt> operation.
     * @since  1.4
     */
    public static <T> boolean replaceAll(List<T> list, T oldVal, T newVal) {
        boolean result = false;
        int size = list.size();
        if (size < REPLACEALL_THRESHOLD || list instanceof RandomAccess) {
            if (oldVal==null) {
                for (int i=0; i<size; i++) {
                    if (list.get(i)==null) {
                        list.set(i, newVal);
                        result = true;
                    }
                }
            } else {
                for (int i=0; i<size; i++) {
                    if (oldVal.equals(list.get(i))) {
                        list.set(i, newVal);
                        result = true;
                    }
                }
            }
        } else {
            ListIterator<T> itr=list.listIterator();
            if (oldVal==null) {
                for (int i=0; i<size; i++) {
                    if (itr.next()==null) {
                        itr.set(newVal);
                        result = true;
                    }
                }
            } else {
                for (int i=0; i<size; i++) {
                    if (oldVal.equals(itr.next())) {
                        itr.set(newVal);
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Returns the starting position of the first occurrence of the specified
     * target list within the specified source list, or -1 if there is no
     * such occurrence.  More formally, returns the lowest index <tt>i</tt>
     * such that {@code source.subList(i, i+target.size()).equals(target)},
     * or -1 if there is no such index.  (Returns -1 if
     * {@code target.size() > source.size()})
     *
     * <p>This implementation uses the "brute force" technique of scanning
     * over the source list, looking for a match with the target at each
     * location in turn.
     *
     * <p>
     * 返回指定源列表中指定目标列表的第一个出现的开始位置,如果没有此类出现,则返回-1。
     * 更正式地,返回最小索引<tt> i </tt>,使得{@code source.subList(i,i + target.size())。equals(target)},如果没有这样的索引。
     *  (如果{@code target.size()> source.size()},则返回-1)。
     * 
     *  <p>此实施方案使用"强力"扫描源列表,依次查找与每个位置处的目标匹配的技术。
     * 
     * 
     * @param source the list in which to search for the first occurrence
     *        of <tt>target</tt>.
     * @param target the list to search for as a subList of <tt>source</tt>.
     * @return the starting position of the first occurrence of the specified
     *         target list within the specified source list, or -1 if there
     *         is no such occurrence.
     * @since  1.4
     */
    public static int indexOfSubList(List<?> source, List<?> target) {
        int sourceSize = source.size();
        int targetSize = target.size();
        int maxCandidate = sourceSize - targetSize;

        if (sourceSize < INDEXOFSUBLIST_THRESHOLD ||
            (source instanceof RandomAccess&&target instanceof RandomAccess)) {
        nextCand:
            for (int candidate = 0; candidate <= maxCandidate; candidate++) {
                for (int i=0, j=candidate; i<targetSize; i++, j++)
                    if (!eq(target.get(i), source.get(j)))
                        continue nextCand;  // Element mismatch, try next cand
                return candidate;  // All elements of candidate matched target
            }
        } else {  // Iterator version of above algorithm
            ListIterator<?> si = source.listIterator();
        nextCand:
            for (int candidate = 0; candidate <= maxCandidate; candidate++) {
                ListIterator<?> ti = target.listIterator();
                for (int i=0; i<targetSize; i++) {
                    if (!eq(ti.next(), si.next())) {
                        // Back up source iterator to next candidate
                        for (int j=0; j<i; j++)
                            si.previous();
                        continue nextCand;
                    }
                }
                return candidate;
            }
        }
        return -1;  // No candidate matched the target
    }

    /**
     * Returns the starting position of the last occurrence of the specified
     * target list within the specified source list, or -1 if there is no such
     * occurrence.  More formally, returns the highest index <tt>i</tt>
     * such that {@code source.subList(i, i+target.size()).equals(target)},
     * or -1 if there is no such index.  (Returns -1 if
     * {@code target.size() > source.size()})
     *
     * <p>This implementation uses the "brute force" technique of iterating
     * over the source list, looking for a match with the target at each
     * location in turn.
     *
     * <p>
     *  返回指定源列表中指定目标列表的最后一次出现的开始位置,如果没有此类出现,则返回-1。
     * 更正式地,返回最高索引<tt> i </tt>,使得{@code source.subList(i,i + target.size())。equals(target)},如果没有这样的索引。
     *  (如果{@code target.size()> source.size()},则返回-1)。
     * 
     *  <p>此实现使用"强力"技术迭代源列表,在每个位置依次查找与目标的匹配。
     * 
     * 
     * @param source the list in which to search for the last occurrence
     *        of <tt>target</tt>.
     * @param target the list to search for as a subList of <tt>source</tt>.
     * @return the starting position of the last occurrence of the specified
     *         target list within the specified source list, or -1 if there
     *         is no such occurrence.
     * @since  1.4
     */
    public static int lastIndexOfSubList(List<?> source, List<?> target) {
        int sourceSize = source.size();
        int targetSize = target.size();
        int maxCandidate = sourceSize - targetSize;

        if (sourceSize < INDEXOFSUBLIST_THRESHOLD ||
            source instanceof RandomAccess) {   // Index access version
        nextCand:
            for (int candidate = maxCandidate; candidate >= 0; candidate--) {
                for (int i=0, j=candidate; i<targetSize; i++, j++)
                    if (!eq(target.get(i), source.get(j)))
                        continue nextCand;  // Element mismatch, try next cand
                return candidate;  // All elements of candidate matched target
            }
        } else {  // Iterator version of above algorithm
            if (maxCandidate < 0)
                return -1;
            ListIterator<?> si = source.listIterator(maxCandidate);
        nextCand:
            for (int candidate = maxCandidate; candidate >= 0; candidate--) {
                ListIterator<?> ti = target.listIterator();
                for (int i=0; i<targetSize; i++) {
                    if (!eq(ti.next(), si.next())) {
                        if (candidate != 0) {
                            // Back up source iterator to next candidate
                            for (int j=0; j<=i+1; j++)
                                si.previous();
                        }
                        continue nextCand;
                    }
                }
                return candidate;
            }
        }
        return -1;  // No candidate matched the target
    }


    // Unmodifiable Wrappers

    /**
     * Returns an unmodifiable view of the specified collection.  This method
     * allows modules to provide users with "read-only" access to internal
     * collections.  Query operations on the returned collection "read through"
     * to the specified collection, and attempts to modify the returned
     * collection, whether direct or via its iterator, result in an
     * <tt>UnsupportedOperationException</tt>.<p>
     *
     * The returned collection does <i>not</i> pass the hashCode and equals
     * operations through to the backing collection, but relies on
     * <tt>Object</tt>'s <tt>equals</tt> and <tt>hashCode</tt> methods.  This
     * is necessary to preserve the contracts of these operations in the case
     * that the backing collection is a set or a list.<p>
     *
     * The returned collection will be serializable if the specified collection
     * is serializable.
     *
     * <p>
     *  返回指定集合的​​不可修改视图。此方法允许模块为用户提供对内部集合的"只读"访问。
     * 对返回的集合"读取"到指定集合的​​查询操作,并尝试修改返回的集合,无论是直接还是通过其迭代器,都会导致<tt> UnsupportedOperationException </tt>。<p>。
     * 
     * 返回的集合</i>不会</i>将hashCode和equals操作传递给后端集合,但依赖于<tt> Object </tt>的<tt> equals </tt>和<tt> hashCode </tt>方
     * 法。
     * 在后台集合为集合或列表的情况下,必须保留这些操作的合同。<p>。
     * 
     *  如果指定的集合是可序列化的,返回的集合将是可序列化的。
     * 
     * 
     * @param  <T> the class of the objects in the collection
     * @param  c the collection for which an unmodifiable view is to be
     *         returned.
     * @return an unmodifiable view of the specified collection.
     */
    public static <T> Collection<T> unmodifiableCollection(Collection<? extends T> c) {
        return new UnmodifiableCollection<>(c);
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    static class UnmodifiableCollection<E> implements Collection<E>, Serializable {
        private static final long serialVersionUID = 1820017752578914078L;

        final Collection<? extends E> c;

        UnmodifiableCollection(Collection<? extends E> c) {
            if (c==null)
                throw new NullPointerException();
            this.c = c;
        }

        public int size()                   {return c.size();}
        public boolean isEmpty()            {return c.isEmpty();}
        public boolean contains(Object o)   {return c.contains(o);}
        public Object[] toArray()           {return c.toArray();}
        public <T> T[] toArray(T[] a)       {return c.toArray(a);}
        public String toString()            {return c.toString();}

        public Iterator<E> iterator() {
            return new Iterator<E>() {
                private final Iterator<? extends E> i = c.iterator();

                public boolean hasNext() {return i.hasNext();}
                public E next()          {return i.next();}
                public void remove() {
                    throw new UnsupportedOperationException();
                }
                @Override
                public void forEachRemaining(Consumer<? super E> action) {
                    // Use backing collection version
                    i.forEachRemaining(action);
                }
            };
        }

        public boolean add(E e) {
            throw new UnsupportedOperationException();
        }
        public boolean remove(Object o) {
            throw new UnsupportedOperationException();
        }

        public boolean containsAll(Collection<?> coll) {
            return c.containsAll(coll);
        }
        public boolean addAll(Collection<? extends E> coll) {
            throw new UnsupportedOperationException();
        }
        public boolean removeAll(Collection<?> coll) {
            throw new UnsupportedOperationException();
        }
        public boolean retainAll(Collection<?> coll) {
            throw new UnsupportedOperationException();
        }
        public void clear() {
            throw new UnsupportedOperationException();
        }

        // Override default methods in Collection
        @Override
        public void forEach(Consumer<? super E> action) {
            c.forEach(action);
        }
        @Override
        public boolean removeIf(Predicate<? super E> filter) {
            throw new UnsupportedOperationException();
        }
        @SuppressWarnings("unchecked")
        @Override
        public Spliterator<E> spliterator() {
            return (Spliterator<E>)c.spliterator();
        }
        @SuppressWarnings("unchecked")
        @Override
        public Stream<E> stream() {
            return (Stream<E>)c.stream();
        }
        @SuppressWarnings("unchecked")
        @Override
        public Stream<E> parallelStream() {
            return (Stream<E>)c.parallelStream();
        }
    }

    /**
     * Returns an unmodifiable view of the specified set.  This method allows
     * modules to provide users with "read-only" access to internal sets.
     * Query operations on the returned set "read through" to the specified
     * set, and attempts to modify the returned set, whether direct or via its
     * iterator, result in an <tt>UnsupportedOperationException</tt>.<p>
     *
     * The returned set will be serializable if the specified set
     * is serializable.
     *
     * <p>
     *  返回指定集合的​​不可修改视图。此方法允许模块为用户提供对内部集的"只读"访问。
     * 对返回的集合"读取"到指定集合的​​查询操作,并尝试修改返回的集合,无论是直接的还是通过其迭代器,导致<tt> UnsupportedOperationException </tt>。<p>。
     * 
     *  如果指定的集合是可序列化的,返回的集合将是可序列化的。
     * 
     * 
     * @param  <T> the class of the objects in the set
     * @param  s the set for which an unmodifiable view is to be returned.
     * @return an unmodifiable view of the specified set.
     */
    public static <T> Set<T> unmodifiableSet(Set<? extends T> s) {
        return new UnmodifiableSet<>(s);
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    static class UnmodifiableSet<E> extends UnmodifiableCollection<E>
                                 implements Set<E>, Serializable {
        private static final long serialVersionUID = -9215047833775013803L;

        UnmodifiableSet(Set<? extends E> s)     {super(s);}
        public boolean equals(Object o) {return o == this || c.equals(o);}
        public int hashCode()           {return c.hashCode();}
    }

    /**
     * Returns an unmodifiable view of the specified sorted set.  This method
     * allows modules to provide users with "read-only" access to internal
     * sorted sets.  Query operations on the returned sorted set "read
     * through" to the specified sorted set.  Attempts to modify the returned
     * sorted set, whether direct, via its iterator, or via its
     * <tt>subSet</tt>, <tt>headSet</tt>, or <tt>tailSet</tt> views, result in
     * an <tt>UnsupportedOperationException</tt>.<p>
     *
     * The returned sorted set will be serializable if the specified sorted set
     * is serializable.
     *
     * <p>
     *  返回指定排序集的不可修改视图。此方法允许模块为用户提供对内部排序集的"只读"访问。对返回的排序集的查询操作"读取"到指定的排序集。
     * 尝试修改返回的排序集(无论是直接的,通过其迭代器还是通过其<tt>子集</tt>,<tt> headSet </tt>或<tt> tailSet </tt>视图) <tt> UnsupportedOpe
     * rationException </tt>。
     *  返回指定排序集的不可修改视图。此方法允许模块为用户提供对内部排序集的"只读"访问。对返回的排序集的查询操作"读取"到指定的排序集。<p>。
     * 
     *  如果指定的排序集是可序列化的,返回的排序集将是可序列化的。
     * 
     * 
     * @param  <T> the class of the objects in the set
     * @param s the sorted set for which an unmodifiable view is to be
     *        returned.
     * @return an unmodifiable view of the specified sorted set.
     */
    public static <T> SortedSet<T> unmodifiableSortedSet(SortedSet<T> s) {
        return new UnmodifiableSortedSet<>(s);
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    static class UnmodifiableSortedSet<E>
                             extends UnmodifiableSet<E>
                             implements SortedSet<E>, Serializable {
        private static final long serialVersionUID = -4929149591599911165L;
        private final SortedSet<E> ss;

        UnmodifiableSortedSet(SortedSet<E> s) {super(s); ss = s;}

        public Comparator<? super E> comparator() {return ss.comparator();}

        public SortedSet<E> subSet(E fromElement, E toElement) {
            return new UnmodifiableSortedSet<>(ss.subSet(fromElement,toElement));
        }
        public SortedSet<E> headSet(E toElement) {
            return new UnmodifiableSortedSet<>(ss.headSet(toElement));
        }
        public SortedSet<E> tailSet(E fromElement) {
            return new UnmodifiableSortedSet<>(ss.tailSet(fromElement));
        }

        public E first()                   {return ss.first();}
        public E last()                    {return ss.last();}
    }

    /**
     * Returns an unmodifiable view of the specified navigable set.  This method
     * allows modules to provide users with "read-only" access to internal
     * navigable sets.  Query operations on the returned navigable set "read
     * through" to the specified navigable set.  Attempts to modify the returned
     * navigable set, whether direct, via its iterator, or via its
     * {@code subSet}, {@code headSet}, or {@code tailSet} views, result in
     * an {@code UnsupportedOperationException}.<p>
     *
     * The returned navigable set will be serializable if the specified
     * navigable set is serializable.
     *
     * <p>
     * 返回指定导航集的不可修改视图。此方法允许模块向用户提供对内部可导航集的"只读"访问。对返回的导航集的查询操作"读取"到指定的可导航集。
     * 尝试修改返回的导航集,无论是直接的,通过其迭代器,还是通过其{@code subSet},{@code headSet}或{@code tailSet}视图,导致{@code UnsupportedOperationException}
     * 。
     * 返回指定导航集的不可修改视图。此方法允许模块向用户提供对内部可导航集的"只读"访问。对返回的导航集的查询操作"读取"到指定的可导航集。<p>。
     * 
     *  如果指定的可导航集是可序列化的,返回的可导航集将是可序列化的。
     * 
     * 
     * @param  <T> the class of the objects in the set
     * @param s the navigable set for which an unmodifiable view is to be
     *        returned
     * @return an unmodifiable view of the specified navigable set
     * @since 1.8
     */
    public static <T> NavigableSet<T> unmodifiableNavigableSet(NavigableSet<T> s) {
        return new UnmodifiableNavigableSet<>(s);
    }

    /**
     * Wraps a navigable set and disables all of the mutative operations.
     *
     * <p>
     *  包装可导航集,并禁用所有的变更操作。
     * 
     * 
     * @param <E> type of elements
     * @serial include
     */
    static class UnmodifiableNavigableSet<E>
                             extends UnmodifiableSortedSet<E>
                             implements NavigableSet<E>, Serializable {

        private static final long serialVersionUID = -6027448201786391929L;

        /**
         * A singleton empty unmodifiable navigable set used for
         * {@link #emptyNavigableSet()}.
         *
         * <p>
         *  用于{@link #emptyNavigableSet()}的单独的空不可修改导航集。
         * 
         * 
         * @param <E> type of elements, if there were any, and bounds
         */
        private static class EmptyNavigableSet<E> extends UnmodifiableNavigableSet<E>
            implements Serializable {
            private static final long serialVersionUID = -6291252904449939134L;

            public EmptyNavigableSet() {
                super(new TreeSet<E>());
            }

            private Object readResolve()        { return EMPTY_NAVIGABLE_SET; }
        }

        @SuppressWarnings("rawtypes")
        private static final NavigableSet<?> EMPTY_NAVIGABLE_SET =
                new EmptyNavigableSet<>();

        /**
         * The instance we are protecting.
         * <p>
         *  我们正在保护的实例。
         * 
         */
        private final NavigableSet<E> ns;

        UnmodifiableNavigableSet(NavigableSet<E> s)         {super(s); ns = s;}

        public E lower(E e)                             { return ns.lower(e); }
        public E floor(E e)                             { return ns.floor(e); }
        public E ceiling(E e)                         { return ns.ceiling(e); }
        public E higher(E e)                           { return ns.higher(e); }
        public E pollFirst()     { throw new UnsupportedOperationException(); }
        public E pollLast()      { throw new UnsupportedOperationException(); }
        public NavigableSet<E> descendingSet()
                 { return new UnmodifiableNavigableSet<>(ns.descendingSet()); }
        public Iterator<E> descendingIterator()
                                         { return descendingSet().iterator(); }

        public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
            return new UnmodifiableNavigableSet<>(
                ns.subSet(fromElement, fromInclusive, toElement, toInclusive));
        }

        public NavigableSet<E> headSet(E toElement, boolean inclusive) {
            return new UnmodifiableNavigableSet<>(
                ns.headSet(toElement, inclusive));
        }

        public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
            return new UnmodifiableNavigableSet<>(
                ns.tailSet(fromElement, inclusive));
        }
    }

    /**
     * Returns an unmodifiable view of the specified list.  This method allows
     * modules to provide users with "read-only" access to internal
     * lists.  Query operations on the returned list "read through" to the
     * specified list, and attempts to modify the returned list, whether
     * direct or via its iterator, result in an
     * <tt>UnsupportedOperationException</tt>.<p>
     *
     * The returned list will be serializable if the specified list
     * is serializable. Similarly, the returned list will implement
     * {@link RandomAccess} if the specified list does.
     *
     * <p>
     *  返回指定列表的不可修改视图。此方法允许模块为用户提供对内部列表的"只读"访问。
     * 对返回的列表的查询操作"读取"到指定的列表,并尝试修改返回的列表,无论是直接还是通过其迭代器,导致<tt> UnsupportedOperationException </tt>。<p>。
     * 
     *  如果指定的列表是可序列化的,返回的列表将是可序列化的。同样,返回的列表将实现{@link RandomAccess},如果指定的列表。
     * 
     * 
     * @param  <T> the class of the objects in the list
     * @param  list the list for which an unmodifiable view is to be returned.
     * @return an unmodifiable view of the specified list.
     */
    public static <T> List<T> unmodifiableList(List<? extends T> list) {
        return (list instanceof RandomAccess ?
                new UnmodifiableRandomAccessList<>(list) :
                new UnmodifiableList<>(list));
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    static class UnmodifiableList<E> extends UnmodifiableCollection<E>
                                  implements List<E> {
        private static final long serialVersionUID = -283967356065247728L;

        final List<? extends E> list;

        UnmodifiableList(List<? extends E> list) {
            super(list);
            this.list = list;
        }

        public boolean equals(Object o) {return o == this || list.equals(o);}
        public int hashCode()           {return list.hashCode();}

        public E get(int index) {return list.get(index);}
        public E set(int index, E element) {
            throw new UnsupportedOperationException();
        }
        public void add(int index, E element) {
            throw new UnsupportedOperationException();
        }
        public E remove(int index) {
            throw new UnsupportedOperationException();
        }
        public int indexOf(Object o)            {return list.indexOf(o);}
        public int lastIndexOf(Object o)        {return list.lastIndexOf(o);}
        public boolean addAll(int index, Collection<? extends E> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void replaceAll(UnaryOperator<E> operator) {
            throw new UnsupportedOperationException();
        }
        @Override
        public void sort(Comparator<? super E> c) {
            throw new UnsupportedOperationException();
        }

        public ListIterator<E> listIterator()   {return listIterator(0);}

        public ListIterator<E> listIterator(final int index) {
            return new ListIterator<E>() {
                private final ListIterator<? extends E> i
                    = list.listIterator(index);

                public boolean hasNext()     {return i.hasNext();}
                public E next()              {return i.next();}
                public boolean hasPrevious() {return i.hasPrevious();}
                public E previous()          {return i.previous();}
                public int nextIndex()       {return i.nextIndex();}
                public int previousIndex()   {return i.previousIndex();}

                public void remove() {
                    throw new UnsupportedOperationException();
                }
                public void set(E e) {
                    throw new UnsupportedOperationException();
                }
                public void add(E e) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void forEachRemaining(Consumer<? super E> action) {
                    i.forEachRemaining(action);
                }
            };
        }

        public List<E> subList(int fromIndex, int toIndex) {
            return new UnmodifiableList<>(list.subList(fromIndex, toIndex));
        }

        /**
         * UnmodifiableRandomAccessList instances are serialized as
         * UnmodifiableList instances to allow them to be deserialized
         * in pre-1.4 JREs (which do not have UnmodifiableRandomAccessList).
         * This method inverts the transformation.  As a beneficial
         * side-effect, it also grafts the RandomAccess marker onto
         * UnmodifiableList instances that were serialized in pre-1.4 JREs.
         *
         * Note: Unfortunately, UnmodifiableRandomAccessList instances
         * serialized in 1.4.1 and deserialized in 1.4 will become
         * UnmodifiableList instances, as this method was missing in 1.4.
         * <p>
         * UnmodifiableRandomAccessList实例被序列化为UnmodifiableList实例,以允许它们在1.4之前的JRE(其没有UnmodifiableRandomAccessList
         * )中被反序列化。
         * 该方法反转变换。作为有益的副作用,它还将RandomAccess标记移植到在1.4之前的JRE中序列化的UnmodifiableList实例上。
         * 
         *  注意：不幸的是,UnmodifiableRandomAccessList实例在1.4.1中序列化并在1.4中反序列化将成为UnmodifiableList实例,因为在1.4中缺少此方法。
         * 
         */
        private Object readResolve() {
            return (list instanceof RandomAccess
                    ? new UnmodifiableRandomAccessList<>(list)
                    : this);
        }
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    static class UnmodifiableRandomAccessList<E> extends UnmodifiableList<E>
                                              implements RandomAccess
    {
        UnmodifiableRandomAccessList(List<? extends E> list) {
            super(list);
        }

        public List<E> subList(int fromIndex, int toIndex) {
            return new UnmodifiableRandomAccessList<>(
                list.subList(fromIndex, toIndex));
        }

        private static final long serialVersionUID = -2542308836966382001L;

        /**
         * Allows instances to be deserialized in pre-1.4 JREs (which do
         * not have UnmodifiableRandomAccessList).  UnmodifiableList has
         * a readResolve method that inverts this transformation upon
         * deserialization.
         * <p>
         *  允许实例在1.4之前的JRE(其没有UnmodifiableRandomAccessList)中被反序列化。
         *  UnmodifiableList有一个readResolve方法,在反序列化后反转这个转换。
         * 
         */
        private Object writeReplace() {
            return new UnmodifiableList<>(list);
        }
    }

    /**
     * Returns an unmodifiable view of the specified map.  This method
     * allows modules to provide users with "read-only" access to internal
     * maps.  Query operations on the returned map "read through"
     * to the specified map, and attempts to modify the returned
     * map, whether direct or via its collection views, result in an
     * <tt>UnsupportedOperationException</tt>.<p>
     *
     * The returned map will be serializable if the specified map
     * is serializable.
     *
     * <p>
     *  返回指定地图的不可修改视图。此方法允许模块为用户提供对内部映射的"只读"访问。
     * 在返回的地图上的查询操作"读取"到指定的地图,并尝试修改返回的地图,无论是直接还是通过其收集视图,导致<tt> UnsupportedOperationException </tt>。<p>。
     * 
     *  如果指定的地图是可序列化的,返回的地图将是可序列化的。
     * 
     * 
     * @param <K> the class of the map keys
     * @param <V> the class of the map values
     * @param  m the map for which an unmodifiable view is to be returned.
     * @return an unmodifiable view of the specified map.
     */
    public static <K,V> Map<K,V> unmodifiableMap(Map<? extends K, ? extends V> m) {
        return new UnmodifiableMap<>(m);
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    private static class UnmodifiableMap<K,V> implements Map<K,V>, Serializable {
        private static final long serialVersionUID = -1034234728574286014L;

        private final Map<? extends K, ? extends V> m;

        UnmodifiableMap(Map<? extends K, ? extends V> m) {
            if (m==null)
                throw new NullPointerException();
            this.m = m;
        }

        public int size()                        {return m.size();}
        public boolean isEmpty()                 {return m.isEmpty();}
        public boolean containsKey(Object key)   {return m.containsKey(key);}
        public boolean containsValue(Object val) {return m.containsValue(val);}
        public V get(Object key)                 {return m.get(key);}

        public V put(K key, V value) {
            throw new UnsupportedOperationException();
        }
        public V remove(Object key) {
            throw new UnsupportedOperationException();
        }
        public void putAll(Map<? extends K, ? extends V> m) {
            throw new UnsupportedOperationException();
        }
        public void clear() {
            throw new UnsupportedOperationException();
        }

        private transient Set<K> keySet;
        private transient Set<Map.Entry<K,V>> entrySet;
        private transient Collection<V> values;

        public Set<K> keySet() {
            if (keySet==null)
                keySet = unmodifiableSet(m.keySet());
            return keySet;
        }

        public Set<Map.Entry<K,V>> entrySet() {
            if (entrySet==null)
                entrySet = new UnmodifiableEntrySet<>(m.entrySet());
            return entrySet;
        }

        public Collection<V> values() {
            if (values==null)
                values = unmodifiableCollection(m.values());
            return values;
        }

        public boolean equals(Object o) {return o == this || m.equals(o);}
        public int hashCode()           {return m.hashCode();}
        public String toString()        {return m.toString();}

        // Override default methods in Map
        @Override
        @SuppressWarnings("unchecked")
        public V getOrDefault(Object k, V defaultValue) {
            // Safe cast as we don't change the value
            return ((Map<K, V>)m).getOrDefault(k, defaultValue);
        }

        @Override
        public void forEach(BiConsumer<? super K, ? super V> action) {
            m.forEach(action);
        }

        @Override
        public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V putIfAbsent(K key, V value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object key, Object value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(K key, V oldValue, V newValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V replace(K key, V value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V computeIfPresent(K key,
                BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V compute(K key,
                BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V merge(K key, V value,
                BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
            throw new UnsupportedOperationException();
        }

        /**
         * We need this class in addition to UnmodifiableSet as
         * Map.Entries themselves permit modification of the backing Map
         * via their setValue operation.  This class is subtle: there are
         * many possible attacks that must be thwarted.
         *
         * <p>
         *  我们需要这个类除了UnmodifiableSet作为Map.Entries自己允许通过他们的setValue操作修改背景Map。这个类是微妙的：有许多可能的攻击必须挫败。
         * 
         * 
         * @serial include
         */
        static class UnmodifiableEntrySet<K,V>
            extends UnmodifiableSet<Map.Entry<K,V>> {
            private static final long serialVersionUID = 7854390611657943733L;

            @SuppressWarnings({"unchecked", "rawtypes"})
            UnmodifiableEntrySet(Set<? extends Map.Entry<? extends K, ? extends V>> s) {
                // Need to cast to raw in order to work around a limitation in the type system
                super((Set)s);
            }

            static <K, V> Consumer<Map.Entry<K, V>> entryConsumer(Consumer<? super Entry<K, V>> action) {
                return e -> action.accept(new UnmodifiableEntry<>(e));
            }

            public void forEach(Consumer<? super Entry<K, V>> action) {
                Objects.requireNonNull(action);
                c.forEach(entryConsumer(action));
            }

            static final class UnmodifiableEntrySetSpliterator<K, V>
                    implements Spliterator<Entry<K,V>> {
                final Spliterator<Map.Entry<K, V>> s;

                UnmodifiableEntrySetSpliterator(Spliterator<Entry<K, V>> s) {
                    this.s = s;
                }

                @Override
                public boolean tryAdvance(Consumer<? super Entry<K, V>> action) {
                    Objects.requireNonNull(action);
                    return s.tryAdvance(entryConsumer(action));
                }

                @Override
                public void forEachRemaining(Consumer<? super Entry<K, V>> action) {
                    Objects.requireNonNull(action);
                    s.forEachRemaining(entryConsumer(action));
                }

                @Override
                public Spliterator<Entry<K, V>> trySplit() {
                    Spliterator<Entry<K, V>> split = s.trySplit();
                    return split == null
                           ? null
                           : new UnmodifiableEntrySetSpliterator<>(split);
                }

                @Override
                public long estimateSize() {
                    return s.estimateSize();
                }

                @Override
                public long getExactSizeIfKnown() {
                    return s.getExactSizeIfKnown();
                }

                @Override
                public int characteristics() {
                    return s.characteristics();
                }

                @Override
                public boolean hasCharacteristics(int characteristics) {
                    return s.hasCharacteristics(characteristics);
                }

                @Override
                public Comparator<? super Entry<K, V>> getComparator() {
                    return s.getComparator();
                }
            }

            @SuppressWarnings("unchecked")
            public Spliterator<Entry<K,V>> spliterator() {
                return new UnmodifiableEntrySetSpliterator<>(
                        (Spliterator<Map.Entry<K, V>>) c.spliterator());
            }

            @Override
            public Stream<Entry<K,V>> stream() {
                return StreamSupport.stream(spliterator(), false);
            }

            @Override
            public Stream<Entry<K,V>> parallelStream() {
                return StreamSupport.stream(spliterator(), true);
            }

            public Iterator<Map.Entry<K,V>> iterator() {
                return new Iterator<Map.Entry<K,V>>() {
                    private final Iterator<? extends Map.Entry<? extends K, ? extends V>> i = c.iterator();

                    public boolean hasNext() {
                        return i.hasNext();
                    }
                    public Map.Entry<K,V> next() {
                        return new UnmodifiableEntry<>(i.next());
                    }
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }

            @SuppressWarnings("unchecked")
            public Object[] toArray() {
                Object[] a = c.toArray();
                for (int i=0; i<a.length; i++)
                    a[i] = new UnmodifiableEntry<>((Map.Entry<? extends K, ? extends V>)a[i]);
                return a;
            }

            @SuppressWarnings("unchecked")
            public <T> T[] toArray(T[] a) {
                // We don't pass a to c.toArray, to avoid window of
                // vulnerability wherein an unscrupulous multithreaded client
                // could get his hands on raw (unwrapped) Entries from c.
                Object[] arr = c.toArray(a.length==0 ? a : Arrays.copyOf(a, 0));

                for (int i=0; i<arr.length; i++)
                    arr[i] = new UnmodifiableEntry<>((Map.Entry<? extends K, ? extends V>)arr[i]);

                if (arr.length > a.length)
                    return (T[])arr;

                System.arraycopy(arr, 0, a, 0, arr.length);
                if (a.length > arr.length)
                    a[arr.length] = null;
                return a;
            }

            /**
             * This method is overridden to protect the backing set against
             * an object with a nefarious equals function that senses
             * that the equality-candidate is Map.Entry and calls its
             * setValue method.
             * <p>
             * 覆盖此方法以保护后端集对具有恶意equals函数的对象,该函数检测等式候选是Map.Entry并调用其setValue方法。
             * 
             */
            public boolean contains(Object o) {
                if (!(o instanceof Map.Entry))
                    return false;
                return c.contains(
                    new UnmodifiableEntry<>((Map.Entry<?,?>) o));
            }

            /**
             * The next two methods are overridden to protect against
             * an unscrupulous List whose contains(Object o) method senses
             * when o is a Map.Entry, and calls o.setValue.
             * <p>
             *  接下来的两个方法被覆盖以防止一个不道德的List,它的contains(Object o)方法在o是Map.Entry时检测,并调用o.setValue。
             * 
             */
            public boolean containsAll(Collection<?> coll) {
                for (Object e : coll) {
                    if (!contains(e)) // Invokes safe contains() above
                        return false;
                }
                return true;
            }
            public boolean equals(Object o) {
                if (o == this)
                    return true;

                if (!(o instanceof Set))
                    return false;
                Set<?> s = (Set<?>) o;
                if (s.size() != c.size())
                    return false;
                return containsAll(s); // Invokes safe containsAll() above
            }

            /**
             * This "wrapper class" serves two purposes: it prevents
             * the client from modifying the backing Map, by short-circuiting
             * the setValue method, and it protects the backing Map against
             * an ill-behaved Map.Entry that attempts to modify another
             * Map Entry when asked to perform an equality check.
             * <p>
             *  这个"包装类"用于两个目的：它通过短路setValue方法防止客户端修改后台Map,并且保护后台Map免受不良行为Map.Entry的影响,当请求时尝试修改另一个Map条目以执行等式检查。
             * 
             */
            private static class UnmodifiableEntry<K,V> implements Map.Entry<K,V> {
                private Map.Entry<? extends K, ? extends V> e;

                UnmodifiableEntry(Map.Entry<? extends K, ? extends V> e)
                        {this.e = Objects.requireNonNull(e);}

                public K getKey()        {return e.getKey();}
                public V getValue()      {return e.getValue();}
                public V setValue(V value) {
                    throw new UnsupportedOperationException();
                }
                public int hashCode()    {return e.hashCode();}
                public boolean equals(Object o) {
                    if (this == o)
                        return true;
                    if (!(o instanceof Map.Entry))
                        return false;
                    Map.Entry<?,?> t = (Map.Entry<?,?>)o;
                    return eq(e.getKey(),   t.getKey()) &&
                           eq(e.getValue(), t.getValue());
                }
                public String toString() {return e.toString();}
            }
        }
    }

    /**
     * Returns an unmodifiable view of the specified sorted map.  This method
     * allows modules to provide users with "read-only" access to internal
     * sorted maps.  Query operations on the returned sorted map "read through"
     * to the specified sorted map.  Attempts to modify the returned
     * sorted map, whether direct, via its collection views, or via its
     * <tt>subMap</tt>, <tt>headMap</tt>, or <tt>tailMap</tt> views, result in
     * an <tt>UnsupportedOperationException</tt>.<p>
     *
     * The returned sorted map will be serializable if the specified sorted map
     * is serializable.
     *
     * <p>
     *  返回指定排序映射的不可修改视图。此方法允许模块为用户提供对内部排序映射的"只读"访问。返回的排序映射的查询操作"读取"到指定的排序映射。
     * 尝试修改返回的排序映射(无论是直接的,通过其集合视图还是通过其<tt> subMap </tt>,<tt> headMap </tt>或<tt> tailMap </tt>视图) a <tt> Unsu
     * pportedOperationException </tt>。
     *  返回指定排序映射的不可修改视图。此方法允许模块为用户提供对内部排序映射的"只读"访问。返回的排序映射的查询操作"读取"到指定的排序映射。<p>。
     * 
     *  如果指定的排序映射是可序列化的,返回的排序映射将是可序列化的。
     * 
     * 
     * @param <K> the class of the map keys
     * @param <V> the class of the map values
     * @param m the sorted map for which an unmodifiable view is to be
     *        returned.
     * @return an unmodifiable view of the specified sorted map.
     */
    public static <K,V> SortedMap<K,V> unmodifiableSortedMap(SortedMap<K, ? extends V> m) {
        return new UnmodifiableSortedMap<>(m);
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    static class UnmodifiableSortedMap<K,V>
          extends UnmodifiableMap<K,V>
          implements SortedMap<K,V>, Serializable {
        private static final long serialVersionUID = -8806743815996713206L;

        private final SortedMap<K, ? extends V> sm;

        UnmodifiableSortedMap(SortedMap<K, ? extends V> m) {super(m); sm = m; }
        public Comparator<? super K> comparator()   { return sm.comparator(); }
        public SortedMap<K,V> subMap(K fromKey, K toKey)
             { return new UnmodifiableSortedMap<>(sm.subMap(fromKey, toKey)); }
        public SortedMap<K,V> headMap(K toKey)
                     { return new UnmodifiableSortedMap<>(sm.headMap(toKey)); }
        public SortedMap<K,V> tailMap(K fromKey)
                   { return new UnmodifiableSortedMap<>(sm.tailMap(fromKey)); }
        public K firstKey()                           { return sm.firstKey(); }
        public K lastKey()                             { return sm.lastKey(); }
    }

    /**
     * Returns an unmodifiable view of the specified navigable map.  This method
     * allows modules to provide users with "read-only" access to internal
     * navigable maps.  Query operations on the returned navigable map "read
     * through" to the specified navigable map.  Attempts to modify the returned
     * navigable map, whether direct, via its collection views, or via its
     * {@code subMap}, {@code headMap}, or {@code tailMap} views, result in
     * an {@code UnsupportedOperationException}.<p>
     *
     * The returned navigable map will be serializable if the specified
     * navigable map is serializable.
     *
     * <p>
     * 返回指定导航地图的不可修改视图。此方法允许模块向用户提供对内部可导航地图的"只读"访问。返回的导航地图上的查询操作"读取"到指定的可导航地图。
     * 尝试修改返回的导航地图,无论是直接的,通过其集合视图还是通过其{@code subMap},{@code headMap}或{@code tailMap}视图,都会导致{@code UnsupportedOperationException}
     * 。
     * 返回指定导航地图的不可修改视图。此方法允许模块向用户提供对内部可导航地图的"只读"访问。返回的导航地图上的查询操作"读取"到指定的可导航地图。<p >。
     * 
     *  如果指定的可导航地图是可序列化的,返回的可导航地图将是可序列化的。
     * 
     * 
     * @param <K> the class of the map keys
     * @param <V> the class of the map values
     * @param m the navigable map for which an unmodifiable view is to be
     *        returned
     * @return an unmodifiable view of the specified navigable map
     * @since 1.8
     */
    public static <K,V> NavigableMap<K,V> unmodifiableNavigableMap(NavigableMap<K, ? extends V> m) {
        return new UnmodifiableNavigableMap<>(m);
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    static class UnmodifiableNavigableMap<K,V>
          extends UnmodifiableSortedMap<K,V>
          implements NavigableMap<K,V>, Serializable {
        private static final long serialVersionUID = -4858195264774772197L;

        /**
         * A class for the {@link EMPTY_NAVIGABLE_MAP} which needs readResolve
         * to preserve singleton property.
         *
         * <p>
         *  {@link EMPTY_NAVIGABLE_MAP}的类,它需要readResolve来保留singleton属性。
         * 
         * 
         * @param <K> type of keys, if there were any, and of bounds
         * @param <V> type of values, if there were any
         */
        private static class EmptyNavigableMap<K,V> extends UnmodifiableNavigableMap<K,V>
            implements Serializable {

            private static final long serialVersionUID = -2239321462712562324L;

            EmptyNavigableMap()                       { super(new TreeMap<K,V>()); }

            @Override
            public NavigableSet<K> navigableKeySet()
                                                { return emptyNavigableSet(); }

            private Object readResolve()        { return EMPTY_NAVIGABLE_MAP; }
        }

        /**
         * Singleton for {@link emptyNavigableMap()} which is also immutable.
         * <p>
         *  Singleton for {@link emptyNavigableMap()}这也是不可变的。
         * 
         */
        private static final EmptyNavigableMap<?,?> EMPTY_NAVIGABLE_MAP =
            new EmptyNavigableMap<>();

        /**
         * The instance we wrap and protect.
         * <p>
         *  我们包装和保护的实例。
         * 
         */
        private final NavigableMap<K, ? extends V> nm;

        UnmodifiableNavigableMap(NavigableMap<K, ? extends V> m)
                                                            {super(m); nm = m;}

        public K lowerKey(K key)                   { return nm.lowerKey(key); }
        public K floorKey(K key)                   { return nm.floorKey(key); }
        public K ceilingKey(K key)               { return nm.ceilingKey(key); }
        public K higherKey(K key)                 { return nm.higherKey(key); }

        @SuppressWarnings("unchecked")
        public Entry<K, V> lowerEntry(K key) {
            Entry<K,V> lower = (Entry<K, V>) nm.lowerEntry(key);
            return (null != lower)
                ? new UnmodifiableEntrySet.UnmodifiableEntry<>(lower)
                : null;
        }

        @SuppressWarnings("unchecked")
        public Entry<K, V> floorEntry(K key) {
            Entry<K,V> floor = (Entry<K, V>) nm.floorEntry(key);
            return (null != floor)
                ? new UnmodifiableEntrySet.UnmodifiableEntry<>(floor)
                : null;
        }

        @SuppressWarnings("unchecked")
        public Entry<K, V> ceilingEntry(K key) {
            Entry<K,V> ceiling = (Entry<K, V>) nm.ceilingEntry(key);
            return (null != ceiling)
                ? new UnmodifiableEntrySet.UnmodifiableEntry<>(ceiling)
                : null;
        }


        @SuppressWarnings("unchecked")
        public Entry<K, V> higherEntry(K key) {
            Entry<K,V> higher = (Entry<K, V>) nm.higherEntry(key);
            return (null != higher)
                ? new UnmodifiableEntrySet.UnmodifiableEntry<>(higher)
                : null;
        }

        @SuppressWarnings("unchecked")
        public Entry<K, V> firstEntry() {
            Entry<K,V> first = (Entry<K, V>) nm.firstEntry();
            return (null != first)
                ? new UnmodifiableEntrySet.UnmodifiableEntry<>(first)
                : null;
        }

        @SuppressWarnings("unchecked")
        public Entry<K, V> lastEntry() {
            Entry<K,V> last = (Entry<K, V>) nm.lastEntry();
            return (null != last)
                ? new UnmodifiableEntrySet.UnmodifiableEntry<>(last)
                : null;
        }

        public Entry<K, V> pollFirstEntry()
                                 { throw new UnsupportedOperationException(); }
        public Entry<K, V> pollLastEntry()
                                 { throw new UnsupportedOperationException(); }
        public NavigableMap<K, V> descendingMap()
                       { return unmodifiableNavigableMap(nm.descendingMap()); }
        public NavigableSet<K> navigableKeySet()
                     { return unmodifiableNavigableSet(nm.navigableKeySet()); }
        public NavigableSet<K> descendingKeySet()
                    { return unmodifiableNavigableSet(nm.descendingKeySet()); }

        public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
            return unmodifiableNavigableMap(
                nm.subMap(fromKey, fromInclusive, toKey, toInclusive));
        }

        public NavigableMap<K, V> headMap(K toKey, boolean inclusive)
             { return unmodifiableNavigableMap(nm.headMap(toKey, inclusive)); }
        public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive)
           { return unmodifiableNavigableMap(nm.tailMap(fromKey, inclusive)); }
    }

    // Synch Wrappers

    /**
     * Returns a synchronized (thread-safe) collection backed by the specified
     * collection.  In order to guarantee serial access, it is critical that
     * <strong>all</strong> access to the backing collection is accomplished
     * through the returned collection.<p>
     *
     * It is imperative that the user manually synchronize on the returned
     * collection when traversing it via {@link Iterator}, {@link Spliterator}
     * or {@link Stream}:
     * <pre>
     *  Collection c = Collections.synchronizedCollection(myCollection);
     *     ...
     *  synchronized (c) {
     *      Iterator i = c.iterator(); // Must be in the synchronized block
     *      while (i.hasNext())
     *         foo(i.next());
     *  }
     * </pre>
     * Failure to follow this advice may result in non-deterministic behavior.
     *
     * <p>The returned collection does <i>not</i> pass the {@code hashCode}
     * and {@code equals} operations through to the backing collection, but
     * relies on {@code Object}'s equals and hashCode methods.  This is
     * necessary to preserve the contracts of these operations in the case
     * that the backing collection is a set or a list.<p>
     *
     * The returned collection will be serializable if the specified collection
     * is serializable.
     *
     * <p>
     *  返回由指定集合支持的同步(线程安全)集合。为了保证串行访问,至关重要的是通过返回的集合实现对所有后端集合的<strong>所有</strong>访问。<p>
     * 
     *  当用户通过{@link迭代器},{@link分割器}或{@link Stream}遍历时,用户必须手动同步返回的集合：
     * <pre>
     *  集合c = Collections.synchronizedCollection(myCollection); ... synchronized(c){迭代器i = c.iterator(); //必须在同步块中while(i.hasNext())foo(i.next()); }
     * }。
     * </pre>
     *  不遵循此建议可能会导致非确定性行为。
     * 
     * <p>返回的集合</i>不会将{@code hashCode}和{@code equals}操作传递给后端集合,而是依赖于{@code Object}的equals和hashCode方法。
     * 在后台集合为集合或列表的情况下,必须保留这些操作的合同。<p>。
     * 
     *  如果指定的集合是可序列化的,返回的集合将是可序列化的。
     * 
     * 
     * @param  <T> the class of the objects in the collection
     * @param  c the collection to be "wrapped" in a synchronized collection.
     * @return a synchronized view of the specified collection.
     */
    public static <T> Collection<T> synchronizedCollection(Collection<T> c) {
        return new SynchronizedCollection<>(c);
    }

    static <T> Collection<T> synchronizedCollection(Collection<T> c, Object mutex) {
        return new SynchronizedCollection<>(c, mutex);
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    static class SynchronizedCollection<E> implements Collection<E>, Serializable {
        private static final long serialVersionUID = 3053995032091335093L;

        final Collection<E> c;  // Backing Collection
        final Object mutex;     // Object on which to synchronize

        SynchronizedCollection(Collection<E> c) {
            this.c = Objects.requireNonNull(c);
            mutex = this;
        }

        SynchronizedCollection(Collection<E> c, Object mutex) {
            this.c = Objects.requireNonNull(c);
            this.mutex = Objects.requireNonNull(mutex);
        }

        public int size() {
            synchronized (mutex) {return c.size();}
        }
        public boolean isEmpty() {
            synchronized (mutex) {return c.isEmpty();}
        }
        public boolean contains(Object o) {
            synchronized (mutex) {return c.contains(o);}
        }
        public Object[] toArray() {
            synchronized (mutex) {return c.toArray();}
        }
        public <T> T[] toArray(T[] a) {
            synchronized (mutex) {return c.toArray(a);}
        }

        public Iterator<E> iterator() {
            return c.iterator(); // Must be manually synched by user!
        }

        public boolean add(E e) {
            synchronized (mutex) {return c.add(e);}
        }
        public boolean remove(Object o) {
            synchronized (mutex) {return c.remove(o);}
        }

        public boolean containsAll(Collection<?> coll) {
            synchronized (mutex) {return c.containsAll(coll);}
        }
        public boolean addAll(Collection<? extends E> coll) {
            synchronized (mutex) {return c.addAll(coll);}
        }
        public boolean removeAll(Collection<?> coll) {
            synchronized (mutex) {return c.removeAll(coll);}
        }
        public boolean retainAll(Collection<?> coll) {
            synchronized (mutex) {return c.retainAll(coll);}
        }
        public void clear() {
            synchronized (mutex) {c.clear();}
        }
        public String toString() {
            synchronized (mutex) {return c.toString();}
        }
        // Override default methods in Collection
        @Override
        public void forEach(Consumer<? super E> consumer) {
            synchronized (mutex) {c.forEach(consumer);}
        }
        @Override
        public boolean removeIf(Predicate<? super E> filter) {
            synchronized (mutex) {return c.removeIf(filter);}
        }
        @Override
        public Spliterator<E> spliterator() {
            return c.spliterator(); // Must be manually synched by user!
        }
        @Override
        public Stream<E> stream() {
            return c.stream(); // Must be manually synched by user!
        }
        @Override
        public Stream<E> parallelStream() {
            return c.parallelStream(); // Must be manually synched by user!
        }
        private void writeObject(ObjectOutputStream s) throws IOException {
            synchronized (mutex) {s.defaultWriteObject();}
        }
    }

    /**
     * Returns a synchronized (thread-safe) set backed by the specified
     * set.  In order to guarantee serial access, it is critical that
     * <strong>all</strong> access to the backing set is accomplished
     * through the returned set.<p>
     *
     * It is imperative that the user manually synchronize on the returned
     * set when iterating over it:
     * <pre>
     *  Set s = Collections.synchronizedSet(new HashSet());
     *      ...
     *  synchronized (s) {
     *      Iterator i = s.iterator(); // Must be in the synchronized block
     *      while (i.hasNext())
     *          foo(i.next());
     *  }
     * </pre>
     * Failure to follow this advice may result in non-deterministic behavior.
     *
     * <p>The returned set will be serializable if the specified set is
     * serializable.
     *
     * <p>
     *  返回由指定集支持的同步(线程安全)集。为了保证串行访问,至关重要的是通过返回的集合完成对后端集的<strong>所有</strong>访问。<p>
     * 
     *  当迭代它时,用户必须在返回的集合上手动同步：
     * <pre>
     *  Set s = Collections.synchronizedSet(new HashSet()); ... synchronized(s){迭代器i = s.iterator(); //必须在同步块中while(i.hasNext())foo(i.next()); }
     * }。
     * </pre>
     *  不遵循此建议可能会导致非确定性行为。
     * 
     *  <p>如果指定的集合是可序列化的,返回的集合将是可序列化的。
     * 
     * 
     * @param  <T> the class of the objects in the set
     * @param  s the set to be "wrapped" in a synchronized set.
     * @return a synchronized view of the specified set.
     */
    public static <T> Set<T> synchronizedSet(Set<T> s) {
        return new SynchronizedSet<>(s);
    }

    static <T> Set<T> synchronizedSet(Set<T> s, Object mutex) {
        return new SynchronizedSet<>(s, mutex);
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    static class SynchronizedSet<E>
          extends SynchronizedCollection<E>
          implements Set<E> {
        private static final long serialVersionUID = 487447009682186044L;

        SynchronizedSet(Set<E> s) {
            super(s);
        }
        SynchronizedSet(Set<E> s, Object mutex) {
            super(s, mutex);
        }

        public boolean equals(Object o) {
            if (this == o)
                return true;
            synchronized (mutex) {return c.equals(o);}
        }
        public int hashCode() {
            synchronized (mutex) {return c.hashCode();}
        }
    }

    /**
     * Returns a synchronized (thread-safe) sorted set backed by the specified
     * sorted set.  In order to guarantee serial access, it is critical that
     * <strong>all</strong> access to the backing sorted set is accomplished
     * through the returned sorted set (or its views).<p>
     *
     * It is imperative that the user manually synchronize on the returned
     * sorted set when iterating over it or any of its <tt>subSet</tt>,
     * <tt>headSet</tt>, or <tt>tailSet</tt> views.
     * <pre>
     *  SortedSet s = Collections.synchronizedSortedSet(new TreeSet());
     *      ...
     *  synchronized (s) {
     *      Iterator i = s.iterator(); // Must be in the synchronized block
     *      while (i.hasNext())
     *          foo(i.next());
     *  }
     * </pre>
     * or:
     * <pre>
     *  SortedSet s = Collections.synchronizedSortedSet(new TreeSet());
     *  SortedSet s2 = s.headSet(foo);
     *      ...
     *  synchronized (s) {  // Note: s, not s2!!!
     *      Iterator i = s2.iterator(); // Must be in the synchronized block
     *      while (i.hasNext())
     *          foo(i.next());
     *  }
     * </pre>
     * Failure to follow this advice may result in non-deterministic behavior.
     *
     * <p>The returned sorted set will be serializable if the specified
     * sorted set is serializable.
     *
     * <p>
     *  返回由指定的排序集支持的同步(线程安全)排序集。为了保证串行访问,至关重要的是通过返回的排序集(或其视图)完成对后退排序集的<strong>所有</strong>访问。<p>
     * 
     * 当迭代它或其任何<tt> subSet </tt>,<tt> headSet </tt>或<tt> tailSet </tt>视图时,用户必须在返回的排序集合上手动同步。
     * <pre>
     *  SortedSet s = Collections.synchronizedSortedSet(new TreeSet()); ... synchronized(s){迭代器i = s.iterator(); //必须在同步块中while(i.hasNext())foo(i.next()); }
     * }。
     * </pre>
     *  要么：
     * <pre>
     *  SortedSet s = Collections.synchronizedSortedSet(new TreeSet()); SortedSet s2 = s.headSet(foo); ... s
     * ynchronized(s){//注意：s,不是s2！迭代器i = s2.iterator(); //必须在同步块中while(i.hasNext())foo(i.next()); }}。
     * </pre>
     *  不遵循此建议可能会导致非确定性行为。
     * 
     *  <p>如果指定的排序集是可序列化的,则返回的排序集将是可序列化的。
     * 
     * 
     * @param  <T> the class of the objects in the set
     * @param  s the sorted set to be "wrapped" in a synchronized sorted set.
     * @return a synchronized view of the specified sorted set.
     */
    public static <T> SortedSet<T> synchronizedSortedSet(SortedSet<T> s) {
        return new SynchronizedSortedSet<>(s);
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    static class SynchronizedSortedSet<E>
        extends SynchronizedSet<E>
        implements SortedSet<E>
    {
        private static final long serialVersionUID = 8695801310862127406L;

        private final SortedSet<E> ss;

        SynchronizedSortedSet(SortedSet<E> s) {
            super(s);
            ss = s;
        }
        SynchronizedSortedSet(SortedSet<E> s, Object mutex) {
            super(s, mutex);
            ss = s;
        }

        public Comparator<? super E> comparator() {
            synchronized (mutex) {return ss.comparator();}
        }

        public SortedSet<E> subSet(E fromElement, E toElement) {
            synchronized (mutex) {
                return new SynchronizedSortedSet<>(
                    ss.subSet(fromElement, toElement), mutex);
            }
        }
        public SortedSet<E> headSet(E toElement) {
            synchronized (mutex) {
                return new SynchronizedSortedSet<>(ss.headSet(toElement), mutex);
            }
        }
        public SortedSet<E> tailSet(E fromElement) {
            synchronized (mutex) {
               return new SynchronizedSortedSet<>(ss.tailSet(fromElement),mutex);
            }
        }

        public E first() {
            synchronized (mutex) {return ss.first();}
        }
        public E last() {
            synchronized (mutex) {return ss.last();}
        }
    }

    /**
     * Returns a synchronized (thread-safe) navigable set backed by the
     * specified navigable set.  In order to guarantee serial access, it is
     * critical that <strong>all</strong> access to the backing navigable set is
     * accomplished through the returned navigable set (or its views).<p>
     *
     * It is imperative that the user manually synchronize on the returned
     * navigable set when iterating over it or any of its {@code subSet},
     * {@code headSet}, or {@code tailSet} views.
     * <pre>
     *  NavigableSet s = Collections.synchronizedNavigableSet(new TreeSet());
     *      ...
     *  synchronized (s) {
     *      Iterator i = s.iterator(); // Must be in the synchronized block
     *      while (i.hasNext())
     *          foo(i.next());
     *  }
     * </pre>
     * or:
     * <pre>
     *  NavigableSet s = Collections.synchronizedNavigableSet(new TreeSet());
     *  NavigableSet s2 = s.headSet(foo, true);
     *      ...
     *  synchronized (s) {  // Note: s, not s2!!!
     *      Iterator i = s2.iterator(); // Must be in the synchronized block
     *      while (i.hasNext())
     *          foo(i.next());
     *  }
     * </pre>
     * Failure to follow this advice may result in non-deterministic behavior.
     *
     * <p>The returned navigable set will be serializable if the specified
     * navigable set is serializable.
     *
     * <p>
     *  返回由指定的可导航集支持的同步(线程安全)可导航集。为了保证串行访问,至关重要的是,通过返回的导航集(或其视图)实现对后端可导航集的<strong>所有</strong>访问。<p>
     * 
     *  当迭代它或其任何{@code subSet},{@code headSet}或{@code tailSet}视图时,用户必须在返回的可导航集上手动同步。
     * <pre>
     *  NavigableSet s = Collections.synchronizedNavigableSet(new TreeSet()); ... synchronized(s){迭代器i = s.iterator(); //必须在同步块中while(i.hasNext())foo(i.next()); }
     * }。
     * </pre>
     *  要么：
     * <pre>
     * NavigableSet s = Collections.synchronizedNavigableSet(new TreeSet()); NavigableSet s2 = s.headSet(foo
     * ,true); ... synchronized(s){//注意：s,不是s2！迭代器i = s2.iterator(); //必须在同步块中while(i.hasNext())foo(i.next()); }
     * }。
     * </pre>
     *  不遵循此建议可能会导致非确定性行为。
     * 
     *  <p>如果指定的可导航集是可序列化的,则返回的可导航集将是可序列化的。
     * 
     * 
     * @param  <T> the class of the objects in the set
     * @param  s the navigable set to be "wrapped" in a synchronized navigable
     * set
     * @return a synchronized view of the specified navigable set
     * @since 1.8
     */
    public static <T> NavigableSet<T> synchronizedNavigableSet(NavigableSet<T> s) {
        return new SynchronizedNavigableSet<>(s);
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    static class SynchronizedNavigableSet<E>
        extends SynchronizedSortedSet<E>
        implements NavigableSet<E>
    {
        private static final long serialVersionUID = -5505529816273629798L;

        private final NavigableSet<E> ns;

        SynchronizedNavigableSet(NavigableSet<E> s) {
            super(s);
            ns = s;
        }

        SynchronizedNavigableSet(NavigableSet<E> s, Object mutex) {
            super(s, mutex);
            ns = s;
        }
        public E lower(E e)      { synchronized (mutex) {return ns.lower(e);} }
        public E floor(E e)      { synchronized (mutex) {return ns.floor(e);} }
        public E ceiling(E e)  { synchronized (mutex) {return ns.ceiling(e);} }
        public E higher(E e)    { synchronized (mutex) {return ns.higher(e);} }
        public E pollFirst()  { synchronized (mutex) {return ns.pollFirst();} }
        public E pollLast()    { synchronized (mutex) {return ns.pollLast();} }

        public NavigableSet<E> descendingSet() {
            synchronized (mutex) {
                return new SynchronizedNavigableSet<>(ns.descendingSet(), mutex);
            }
        }

        public Iterator<E> descendingIterator()
                 { synchronized (mutex) { return descendingSet().iterator(); } }

        public NavigableSet<E> subSet(E fromElement, E toElement) {
            synchronized (mutex) {
                return new SynchronizedNavigableSet<>(ns.subSet(fromElement, true, toElement, false), mutex);
            }
        }
        public NavigableSet<E> headSet(E toElement) {
            synchronized (mutex) {
                return new SynchronizedNavigableSet<>(ns.headSet(toElement, false), mutex);
            }
        }
        public NavigableSet<E> tailSet(E fromElement) {
            synchronized (mutex) {
                return new SynchronizedNavigableSet<>(ns.tailSet(fromElement, true), mutex);
            }
        }

        public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
            synchronized (mutex) {
                return new SynchronizedNavigableSet<>(ns.subSet(fromElement, fromInclusive, toElement, toInclusive), mutex);
            }
        }

        public NavigableSet<E> headSet(E toElement, boolean inclusive) {
            synchronized (mutex) {
                return new SynchronizedNavigableSet<>(ns.headSet(toElement, inclusive), mutex);
            }
        }

        public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
            synchronized (mutex) {
                return new SynchronizedNavigableSet<>(ns.tailSet(fromElement, inclusive), mutex);
            }
        }
    }

    /**
     * Returns a synchronized (thread-safe) list backed by the specified
     * list.  In order to guarantee serial access, it is critical that
     * <strong>all</strong> access to the backing list is accomplished
     * through the returned list.<p>
     *
     * It is imperative that the user manually synchronize on the returned
     * list when iterating over it:
     * <pre>
     *  List list = Collections.synchronizedList(new ArrayList());
     *      ...
     *  synchronized (list) {
     *      Iterator i = list.iterator(); // Must be in synchronized block
     *      while (i.hasNext())
     *          foo(i.next());
     *  }
     * </pre>
     * Failure to follow this advice may result in non-deterministic behavior.
     *
     * <p>The returned list will be serializable if the specified list is
     * serializable.
     *
     * <p>
     *  返回由指定列表支持的同步(线程安全)列表。为了保证串行访问,至关重要的是通过返回的列表完成对后备列表的<strong>所有</strong>访问。<p>
     * 
     *  当迭代它时,用户必须在返回的列表上手动同步：
     * <pre>
     *  List list = Collections.synchronizedList(new ArrayList()); ... synchronized(list){Iterator i = list.iterator(); //必须在同步块中while(i.hasNext())foo(i.next()); }
     * }。
     * </pre>
     *  不遵循此建议可能会导致非确定性行为。
     * 
     *  <p>如果指定的列表是可序列化的,返回的列表将是可序列化的。
     * 
     * 
     * @param  <T> the class of the objects in the list
     * @param  list the list to be "wrapped" in a synchronized list.
     * @return a synchronized view of the specified list.
     */
    public static <T> List<T> synchronizedList(List<T> list) {
        return (list instanceof RandomAccess ?
                new SynchronizedRandomAccessList<>(list) :
                new SynchronizedList<>(list));
    }

    static <T> List<T> synchronizedList(List<T> list, Object mutex) {
        return (list instanceof RandomAccess ?
                new SynchronizedRandomAccessList<>(list, mutex) :
                new SynchronizedList<>(list, mutex));
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    static class SynchronizedList<E>
        extends SynchronizedCollection<E>
        implements List<E> {
        private static final long serialVersionUID = -7754090372962971524L;

        final List<E> list;

        SynchronizedList(List<E> list) {
            super(list);
            this.list = list;
        }
        SynchronizedList(List<E> list, Object mutex) {
            super(list, mutex);
            this.list = list;
        }

        public boolean equals(Object o) {
            if (this == o)
                return true;
            synchronized (mutex) {return list.equals(o);}
        }
        public int hashCode() {
            synchronized (mutex) {return list.hashCode();}
        }

        public E get(int index) {
            synchronized (mutex) {return list.get(index);}
        }
        public E set(int index, E element) {
            synchronized (mutex) {return list.set(index, element);}
        }
        public void add(int index, E element) {
            synchronized (mutex) {list.add(index, element);}
        }
        public E remove(int index) {
            synchronized (mutex) {return list.remove(index);}
        }

        public int indexOf(Object o) {
            synchronized (mutex) {return list.indexOf(o);}
        }
        public int lastIndexOf(Object o) {
            synchronized (mutex) {return list.lastIndexOf(o);}
        }

        public boolean addAll(int index, Collection<? extends E> c) {
            synchronized (mutex) {return list.addAll(index, c);}
        }

        public ListIterator<E> listIterator() {
            return list.listIterator(); // Must be manually synched by user
        }

        public ListIterator<E> listIterator(int index) {
            return list.listIterator(index); // Must be manually synched by user
        }

        public List<E> subList(int fromIndex, int toIndex) {
            synchronized (mutex) {
                return new SynchronizedList<>(list.subList(fromIndex, toIndex),
                                            mutex);
            }
        }

        @Override
        public void replaceAll(UnaryOperator<E> operator) {
            synchronized (mutex) {list.replaceAll(operator);}
        }
        @Override
        public void sort(Comparator<? super E> c) {
            synchronized (mutex) {list.sort(c);}
        }

        /**
         * SynchronizedRandomAccessList instances are serialized as
         * SynchronizedList instances to allow them to be deserialized
         * in pre-1.4 JREs (which do not have SynchronizedRandomAccessList).
         * This method inverts the transformation.  As a beneficial
         * side-effect, it also grafts the RandomAccess marker onto
         * SynchronizedList instances that were serialized in pre-1.4 JREs.
         *
         * Note: Unfortunately, SynchronizedRandomAccessList instances
         * serialized in 1.4.1 and deserialized in 1.4 will become
         * SynchronizedList instances, as this method was missing in 1.4.
         * <p>
         *  SynchronizedRandomAccessList实例被序列化为SynchronizedList实例,以允许它们在1.4之前的JRE(其没有SynchronizedRandomAccessLis
         * t)中被反序列化。
         * 该方法反转变换。作为有益的副作用,它还将RandomAccess标记移植到在1.4之前的JRE中序列化的SynchronizedList实例上。
         * 
         * 注意：不幸的是,在1.4.1中序列化并在1.4中反序列化的SynchronizedRandomAccessList实例将成为SynchronizedList实例,因为在1.4中缺少此方法。
         * 
         */
        private Object readResolve() {
            return (list instanceof RandomAccess
                    ? new SynchronizedRandomAccessList<>(list)
                    : this);
        }
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    static class SynchronizedRandomAccessList<E>
        extends SynchronizedList<E>
        implements RandomAccess {

        SynchronizedRandomAccessList(List<E> list) {
            super(list);
        }

        SynchronizedRandomAccessList(List<E> list, Object mutex) {
            super(list, mutex);
        }

        public List<E> subList(int fromIndex, int toIndex) {
            synchronized (mutex) {
                return new SynchronizedRandomAccessList<>(
                    list.subList(fromIndex, toIndex), mutex);
            }
        }

        private static final long serialVersionUID = 1530674583602358482L;

        /**
         * Allows instances to be deserialized in pre-1.4 JREs (which do
         * not have SynchronizedRandomAccessList).  SynchronizedList has
         * a readResolve method that inverts this transformation upon
         * deserialization.
         * <p>
         *  允许在1.4之前的JRE(其没有SynchronizedRandomAccessList)中反序列化实例。
         *  SynchronizedList有一个readResolve方法,在反序列化后反转这个转换。
         * 
         */
        private Object writeReplace() {
            return new SynchronizedList<>(list);
        }
    }

    /**
     * Returns a synchronized (thread-safe) map backed by the specified
     * map.  In order to guarantee serial access, it is critical that
     * <strong>all</strong> access to the backing map is accomplished
     * through the returned map.<p>
     *
     * It is imperative that the user manually synchronize on the returned
     * map when iterating over any of its collection views:
     * <pre>
     *  Map m = Collections.synchronizedMap(new HashMap());
     *      ...
     *  Set s = m.keySet();  // Needn't be in synchronized block
     *      ...
     *  synchronized (m) {  // Synchronizing on m, not s!
     *      Iterator i = s.iterator(); // Must be in synchronized block
     *      while (i.hasNext())
     *          foo(i.next());
     *  }
     * </pre>
     * Failure to follow this advice may result in non-deterministic behavior.
     *
     * <p>The returned map will be serializable if the specified map is
     * serializable.
     *
     * <p>
     *  返回由指定映射支持的同步(线程安全)映射。为了保证串行访问,至关重要的是通过返回的映射完成对后备映射的<strong>所有</strong>访问。<p>
     * 
     *  当迭代其任何集合视图时,用户必须在返回的地图上手动同步：
     * <pre>
     *  Map m = Collections.synchronizedMap(new HashMap()); ... Set s = m.keySet(); //不需要在同步块中... synchroniz
     * ed(m){//在m上同步,而不是s！迭代器i = s.iterator(); //必须在同步块中while(i.hasNext())foo(i.next()); }}。
     * </pre>
     *  不遵循此建议可能会导致非确定性行为。
     * 
     *  <p>如果指定的地图是可序列化的,返回的地图将是可序列化的。
     * 
     * 
     * @param <K> the class of the map keys
     * @param <V> the class of the map values
     * @param  m the map to be "wrapped" in a synchronized map.
     * @return a synchronized view of the specified map.
     */
    public static <K,V> Map<K,V> synchronizedMap(Map<K,V> m) {
        return new SynchronizedMap<>(m);
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    private static class SynchronizedMap<K,V>
        implements Map<K,V>, Serializable {
        private static final long serialVersionUID = 1978198479659022715L;

        private final Map<K,V> m;     // Backing Map
        final Object      mutex;        // Object on which to synchronize

        SynchronizedMap(Map<K,V> m) {
            this.m = Objects.requireNonNull(m);
            mutex = this;
        }

        SynchronizedMap(Map<K,V> m, Object mutex) {
            this.m = m;
            this.mutex = mutex;
        }

        public int size() {
            synchronized (mutex) {return m.size();}
        }
        public boolean isEmpty() {
            synchronized (mutex) {return m.isEmpty();}
        }
        public boolean containsKey(Object key) {
            synchronized (mutex) {return m.containsKey(key);}
        }
        public boolean containsValue(Object value) {
            synchronized (mutex) {return m.containsValue(value);}
        }
        public V get(Object key) {
            synchronized (mutex) {return m.get(key);}
        }

        public V put(K key, V value) {
            synchronized (mutex) {return m.put(key, value);}
        }
        public V remove(Object key) {
            synchronized (mutex) {return m.remove(key);}
        }
        public void putAll(Map<? extends K, ? extends V> map) {
            synchronized (mutex) {m.putAll(map);}
        }
        public void clear() {
            synchronized (mutex) {m.clear();}
        }

        private transient Set<K> keySet;
        private transient Set<Map.Entry<K,V>> entrySet;
        private transient Collection<V> values;

        public Set<K> keySet() {
            synchronized (mutex) {
                if (keySet==null)
                    keySet = new SynchronizedSet<>(m.keySet(), mutex);
                return keySet;
            }
        }

        public Set<Map.Entry<K,V>> entrySet() {
            synchronized (mutex) {
                if (entrySet==null)
                    entrySet = new SynchronizedSet<>(m.entrySet(), mutex);
                return entrySet;
            }
        }

        public Collection<V> values() {
            synchronized (mutex) {
                if (values==null)
                    values = new SynchronizedCollection<>(m.values(), mutex);
                return values;
            }
        }

        public boolean equals(Object o) {
            if (this == o)
                return true;
            synchronized (mutex) {return m.equals(o);}
        }
        public int hashCode() {
            synchronized (mutex) {return m.hashCode();}
        }
        public String toString() {
            synchronized (mutex) {return m.toString();}
        }

        // Override default methods in Map
        @Override
        public V getOrDefault(Object k, V defaultValue) {
            synchronized (mutex) {return m.getOrDefault(k, defaultValue);}
        }
        @Override
        public void forEach(BiConsumer<? super K, ? super V> action) {
            synchronized (mutex) {m.forEach(action);}
        }
        @Override
        public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
            synchronized (mutex) {m.replaceAll(function);}
        }
        @Override
        public V putIfAbsent(K key, V value) {
            synchronized (mutex) {return m.putIfAbsent(key, value);}
        }
        @Override
        public boolean remove(Object key, Object value) {
            synchronized (mutex) {return m.remove(key, value);}
        }
        @Override
        public boolean replace(K key, V oldValue, V newValue) {
            synchronized (mutex) {return m.replace(key, oldValue, newValue);}
        }
        @Override
        public V replace(K key, V value) {
            synchronized (mutex) {return m.replace(key, value);}
        }
        @Override
        public V computeIfAbsent(K key,
                Function<? super K, ? extends V> mappingFunction) {
            synchronized (mutex) {return m.computeIfAbsent(key, mappingFunction);}
        }
        @Override
        public V computeIfPresent(K key,
                BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
            synchronized (mutex) {return m.computeIfPresent(key, remappingFunction);}
        }
        @Override
        public V compute(K key,
                BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
            synchronized (mutex) {return m.compute(key, remappingFunction);}
        }
        @Override
        public V merge(K key, V value,
                BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
            synchronized (mutex) {return m.merge(key, value, remappingFunction);}
        }

        private void writeObject(ObjectOutputStream s) throws IOException {
            synchronized (mutex) {s.defaultWriteObject();}
        }
    }

    /**
     * Returns a synchronized (thread-safe) sorted map backed by the specified
     * sorted map.  In order to guarantee serial access, it is critical that
     * <strong>all</strong> access to the backing sorted map is accomplished
     * through the returned sorted map (or its views).<p>
     *
     * It is imperative that the user manually synchronize on the returned
     * sorted map when iterating over any of its collection views, or the
     * collections views of any of its <tt>subMap</tt>, <tt>headMap</tt> or
     * <tt>tailMap</tt> views.
     * <pre>
     *  SortedMap m = Collections.synchronizedSortedMap(new TreeMap());
     *      ...
     *  Set s = m.keySet();  // Needn't be in synchronized block
     *      ...
     *  synchronized (m) {  // Synchronizing on m, not s!
     *      Iterator i = s.iterator(); // Must be in synchronized block
     *      while (i.hasNext())
     *          foo(i.next());
     *  }
     * </pre>
     * or:
     * <pre>
     *  SortedMap m = Collections.synchronizedSortedMap(new TreeMap());
     *  SortedMap m2 = m.subMap(foo, bar);
     *      ...
     *  Set s2 = m2.keySet();  // Needn't be in synchronized block
     *      ...
     *  synchronized (m) {  // Synchronizing on m, not m2 or s2!
     *      Iterator i = s.iterator(); // Must be in synchronized block
     *      while (i.hasNext())
     *          foo(i.next());
     *  }
     * </pre>
     * Failure to follow this advice may result in non-deterministic behavior.
     *
     * <p>The returned sorted map will be serializable if the specified
     * sorted map is serializable.
     *
     * <p>
     *  返回由指定的排序映射支持的同步(线程安全)排序映射。为了保证串行访问,至关重要的是通过返回的排序映射(或其视图)实现对返回的排序映射的<strong>所有</strong>访问。<p>
     * 
     * 当迭代任何其集合视图或其任何<tt> subMap </tt>,<tt> headMap </tt>或<tt的任何集合视图时,用户必须在返回的排序映射上手动同步> tailMap </tt>视图。
     * <pre>
     *  SortedMap m = Collections.synchronizedSortedMap(new TreeMap()); ... Set s = m.keySet(); //不需要在同步块中..
     * . synchronized(m){//在m上同步,而不是s！迭代器i = s.iterator(); //必须在同步块中while(i.hasNext())foo(i.next()); }}。
     * </pre>
     *  要么：
     * <pre>
     *  SortedMap m = Collections.synchronizedSortedMap(new TreeMap()); SortedMap m2 = m.subMap(foo,bar); ..
     * . Set s2 = m2.keySet(); //不需要在同步块中... synchronized(m){//在m上同步,而不是m2或s2！迭代器i = s.iterator(); //必须在同步块中while(i.hasNext())foo(i.next()); }
     * }。
     * </pre>
     *  不遵循此建议可能会导致非确定性行为。
     * 
     *  <p>如果指定的排序映射是可序列化的,返回的排序映射将是可序列化的。
     * 
     * 
     * @param <K> the class of the map keys
     * @param <V> the class of the map values
     * @param  m the sorted map to be "wrapped" in a synchronized sorted map.
     * @return a synchronized view of the specified sorted map.
     */
    public static <K,V> SortedMap<K,V> synchronizedSortedMap(SortedMap<K,V> m) {
        return new SynchronizedSortedMap<>(m);
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    static class SynchronizedSortedMap<K,V>
        extends SynchronizedMap<K,V>
        implements SortedMap<K,V>
    {
        private static final long serialVersionUID = -8798146769416483793L;

        private final SortedMap<K,V> sm;

        SynchronizedSortedMap(SortedMap<K,V> m) {
            super(m);
            sm = m;
        }
        SynchronizedSortedMap(SortedMap<K,V> m, Object mutex) {
            super(m, mutex);
            sm = m;
        }

        public Comparator<? super K> comparator() {
            synchronized (mutex) {return sm.comparator();}
        }

        public SortedMap<K,V> subMap(K fromKey, K toKey) {
            synchronized (mutex) {
                return new SynchronizedSortedMap<>(
                    sm.subMap(fromKey, toKey), mutex);
            }
        }
        public SortedMap<K,V> headMap(K toKey) {
            synchronized (mutex) {
                return new SynchronizedSortedMap<>(sm.headMap(toKey), mutex);
            }
        }
        public SortedMap<K,V> tailMap(K fromKey) {
            synchronized (mutex) {
               return new SynchronizedSortedMap<>(sm.tailMap(fromKey),mutex);
            }
        }

        public K firstKey() {
            synchronized (mutex) {return sm.firstKey();}
        }
        public K lastKey() {
            synchronized (mutex) {return sm.lastKey();}
        }
    }

    /**
     * Returns a synchronized (thread-safe) navigable map backed by the
     * specified navigable map.  In order to guarantee serial access, it is
     * critical that <strong>all</strong> access to the backing navigable map is
     * accomplished through the returned navigable map (or its views).<p>
     *
     * It is imperative that the user manually synchronize on the returned
     * navigable map when iterating over any of its collection views, or the
     * collections views of any of its {@code subMap}, {@code headMap} or
     * {@code tailMap} views.
     * <pre>
     *  NavigableMap m = Collections.synchronizedNavigableMap(new TreeMap());
     *      ...
     *  Set s = m.keySet();  // Needn't be in synchronized block
     *      ...
     *  synchronized (m) {  // Synchronizing on m, not s!
     *      Iterator i = s.iterator(); // Must be in synchronized block
     *      while (i.hasNext())
     *          foo(i.next());
     *  }
     * </pre>
     * or:
     * <pre>
     *  NavigableMap m = Collections.synchronizedNavigableMap(new TreeMap());
     *  NavigableMap m2 = m.subMap(foo, true, bar, false);
     *      ...
     *  Set s2 = m2.keySet();  // Needn't be in synchronized block
     *      ...
     *  synchronized (m) {  // Synchronizing on m, not m2 or s2!
     *      Iterator i = s.iterator(); // Must be in synchronized block
     *      while (i.hasNext())
     *          foo(i.next());
     *  }
     * </pre>
     * Failure to follow this advice may result in non-deterministic behavior.
     *
     * <p>The returned navigable map will be serializable if the specified
     * navigable map is serializable.
     *
     * <p>
     *  返回由指定的可导航地图支持的同步(线程安全)可导航地图。为了保证串行访问,至关重要的是,通过返回的导航地图(或其视图)完成对背景可导航地图的<strong>所有</strong>访问。<p>
     * 
     * 当迭代任何其集合视图或其任何{@code subMap},{@code headMap}或{@code tailMap}视图的集合视图时,用户必须在返回的导航地图上手动同步。
     * <pre>
     *  NavigableMap m = Collections.synchronizedNavigableMap(new TreeMap()); ... Set s = m.keySet(); //不需要在
     * 同步块中... synchronized(m){//在m上同步,而不是s！迭代器i = s.iterator(); //必须在同步块中while(i.hasNext())foo(i.next()); }
     * }。
     * </pre>
     *  要么：
     * <pre>
     *  NavigableMap m = Collections.synchronizedNavigableMap(new TreeMap()); NavigableMap m2 = m.subMap(foo
     * ,true,bar,false); ... Set s2 = m2.keySet(); //不需要在同步块中... synchronized(m){//在m上同步,而不是m2或s2！迭代器i = s.iterator(); //必须在同步块中while(i.hasNext())foo(i.next()); }
     * }。
     * </pre>
     *  不遵循此建议可能会导致非确定性行为。
     * 
     *  <p>如果指定的导航地图是可序列化的,则返回的导航地图将是可序列化的。
     * 
     * 
     * @param <K> the class of the map keys
     * @param <V> the class of the map values
     * @param  m the navigable map to be "wrapped" in a synchronized navigable
     *              map
     * @return a synchronized view of the specified navigable map.
     * @since 1.8
     */
    public static <K,V> NavigableMap<K,V> synchronizedNavigableMap(NavigableMap<K,V> m) {
        return new SynchronizedNavigableMap<>(m);
    }

    /**
     * A synchronized NavigableMap.
     *
     * <p>
     *  同步NavigableMap。
     * 
     * 
     * @serial include
     */
    static class SynchronizedNavigableMap<K,V>
        extends SynchronizedSortedMap<K,V>
        implements NavigableMap<K,V>
    {
        private static final long serialVersionUID = 699392247599746807L;

        private final NavigableMap<K,V> nm;

        SynchronizedNavigableMap(NavigableMap<K,V> m) {
            super(m);
            nm = m;
        }
        SynchronizedNavigableMap(NavigableMap<K,V> m, Object mutex) {
            super(m, mutex);
            nm = m;
        }

        public Entry<K, V> lowerEntry(K key)
                        { synchronized (mutex) { return nm.lowerEntry(key); } }
        public K lowerKey(K key)
                          { synchronized (mutex) { return nm.lowerKey(key); } }
        public Entry<K, V> floorEntry(K key)
                        { synchronized (mutex) { return nm.floorEntry(key); } }
        public K floorKey(K key)
                          { synchronized (mutex) { return nm.floorKey(key); } }
        public Entry<K, V> ceilingEntry(K key)
                      { synchronized (mutex) { return nm.ceilingEntry(key); } }
        public K ceilingKey(K key)
                        { synchronized (mutex) { return nm.ceilingKey(key); } }
        public Entry<K, V> higherEntry(K key)
                       { synchronized (mutex) { return nm.higherEntry(key); } }
        public K higherKey(K key)
                         { synchronized (mutex) { return nm.higherKey(key); } }
        public Entry<K, V> firstEntry()
                           { synchronized (mutex) { return nm.firstEntry(); } }
        public Entry<K, V> lastEntry()
                            { synchronized (mutex) { return nm.lastEntry(); } }
        public Entry<K, V> pollFirstEntry()
                       { synchronized (mutex) { return nm.pollFirstEntry(); } }
        public Entry<K, V> pollLastEntry()
                        { synchronized (mutex) { return nm.pollLastEntry(); } }

        public NavigableMap<K, V> descendingMap() {
            synchronized (mutex) {
                return
                    new SynchronizedNavigableMap<>(nm.descendingMap(), mutex);
            }
        }

        public NavigableSet<K> keySet() {
            return navigableKeySet();
        }

        public NavigableSet<K> navigableKeySet() {
            synchronized (mutex) {
                return new SynchronizedNavigableSet<>(nm.navigableKeySet(), mutex);
            }
        }

        public NavigableSet<K> descendingKeySet() {
            synchronized (mutex) {
                return new SynchronizedNavigableSet<>(nm.descendingKeySet(), mutex);
            }
        }


        public SortedMap<K,V> subMap(K fromKey, K toKey) {
            synchronized (mutex) {
                return new SynchronizedNavigableMap<>(
                    nm.subMap(fromKey, true, toKey, false), mutex);
            }
        }
        public SortedMap<K,V> headMap(K toKey) {
            synchronized (mutex) {
                return new SynchronizedNavigableMap<>(nm.headMap(toKey, false), mutex);
            }
        }
        public SortedMap<K,V> tailMap(K fromKey) {
            synchronized (mutex) {
        return new SynchronizedNavigableMap<>(nm.tailMap(fromKey, true),mutex);
            }
        }

        public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
            synchronized (mutex) {
                return new SynchronizedNavigableMap<>(
                    nm.subMap(fromKey, fromInclusive, toKey, toInclusive), mutex);
            }
        }

        public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
            synchronized (mutex) {
                return new SynchronizedNavigableMap<>(
                        nm.headMap(toKey, inclusive), mutex);
            }
        }

        public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
            synchronized (mutex) {
                return new SynchronizedNavigableMap<>(
                    nm.tailMap(fromKey, inclusive), mutex);
            }
        }
    }

    // Dynamically typesafe collection wrappers

    /**
     * Returns a dynamically typesafe view of the specified collection.
     * Any attempt to insert an element of the wrong type will result in an
     * immediate {@link ClassCastException}.  Assuming a collection
     * contains no incorrectly typed elements prior to the time a
     * dynamically typesafe view is generated, and that all subsequent
     * access to the collection takes place through the view, it is
     * <i>guaranteed</i> that the collection cannot contain an incorrectly
     * typed element.
     *
     * <p>The generics mechanism in the language provides compile-time
     * (static) type checking, but it is possible to defeat this mechanism
     * with unchecked casts.  Usually this is not a problem, as the compiler
     * issues warnings on all such unchecked operations.  There are, however,
     * times when static type checking alone is not sufficient.  For example,
     * suppose a collection is passed to a third-party library and it is
     * imperative that the library code not corrupt the collection by
     * inserting an element of the wrong type.
     *
     * <p>Another use of dynamically typesafe views is debugging.  Suppose a
     * program fails with a {@code ClassCastException}, indicating that an
     * incorrectly typed element was put into a parameterized collection.
     * Unfortunately, the exception can occur at any time after the erroneous
     * element is inserted, so it typically provides little or no information
     * as to the real source of the problem.  If the problem is reproducible,
     * one can quickly determine its source by temporarily modifying the
     * program to wrap the collection with a dynamically typesafe view.
     * For example, this declaration:
     *  <pre> {@code
     *     Collection<String> c = new HashSet<>();
     * }</pre>
     * may be replaced temporarily by this one:
     *  <pre> {@code
     *     Collection<String> c = Collections.checkedCollection(
     *         new HashSet<>(), String.class);
     * }</pre>
     * Running the program again will cause it to fail at the point where
     * an incorrectly typed element is inserted into the collection, clearly
     * identifying the source of the problem.  Once the problem is fixed, the
     * modified declaration may be reverted back to the original.
     *
     * <p>The returned collection does <i>not</i> pass the hashCode and equals
     * operations through to the backing collection, but relies on
     * {@code Object}'s {@code equals} and {@code hashCode} methods.  This
     * is necessary to preserve the contracts of these operations in the case
     * that the backing collection is a set or a list.
     *
     * <p>The returned collection will be serializable if the specified
     * collection is serializable.
     *
     * <p>Since {@code null} is considered to be a value of any reference
     * type, the returned collection permits insertion of null elements
     * whenever the backing collection does.
     *
     * <p>
     * 返回指定集合的​​动态类型安全视图。任何尝试插入错误类型的元素将导致立即{@link ClassCastException}。
     * 假设在生成动态类型安全视图之前集合不包含不正确类型的元素,并且所有后续对集合的访问都通过视图进行,则保证集合不能包含不正确的类型元素。
     * 
     *  <p>语言中的泛型机制提供了编译时(静态)类型检查,但是可以使用unchecked类型检查来消除这种机制。通常这不是一个问题,因为编译器对所有这样的未经检查的操作发出警告。
     * 然而,有时候静态类型检查是不够的。例如,假设一个集合被传递给第三方库,并且库代码不会通过插入一个错误类型的元素来破坏集合。
     * 
     * <p>动态类型安全视图的另一个用途是调试。假设程序失败并出现{@code ClassCastException},表示将一个不正确类型的元素放入参数化集合中。
     * 不幸的是,异常可以在插入错误元素之后的任何时间发生,因此它通常提供关于问题的真实源的很少或没有信息。如果问题是可重现的,可以通过临时修改程序来以动态类型安全视图包装集合来快速确定其来源。
     * 例如,这个声明：<pre> {@code Collection <String> c = new HashSet <>(); } </pre>可以被这个替换：<pre> {@code Collection <String> c = Collections.checkedCollection(new HashSet <>(),String.class); }
     *  </pre>再次运行程序将导致它在将错误类型的元素插入到集合中的时候失败,从而清楚地识别问题的根源。
     * 不幸的是,异常可以在插入错误元素之后的任何时间发生,因此它通常提供关于问题的真实源的很少或没有信息。如果问题是可重现的,可以通过临时修改程序来以动态类型安全视图包装集合来快速确定其来源。
     * 一旦问题得到修复,修改的声明可以恢复到原来的状态。
     * 
     *  <p>返回的集合</i> </i>不会将hashCode和equals操作传递给后端集合,而是依赖于{@code Object}的{@code equals}和{@code hashCode}方法。
     * 在后台集合是集合或列表的情况下,这是保留这些操作的合同所必需的。
     * 
     *  <p>如果指定的集合是可序列化的,返回的集合将是可序列化的。
     * 
     * <p>由于{@code null}被认为是任何引用类型的值,所以返回的集合允许在后台收集时插入空元素。
     * 
     * 
     * @param <E> the class of the objects in the collection
     * @param c the collection for which a dynamically typesafe view is to be
     *          returned
     * @param type the type of element that {@code c} is permitted to hold
     * @return a dynamically typesafe view of the specified collection
     * @since 1.5
     */
    public static <E> Collection<E> checkedCollection(Collection<E> c,
                                                      Class<E> type) {
        return new CheckedCollection<>(c, type);
    }

    @SuppressWarnings("unchecked")
    static <T> T[] zeroLengthArray(Class<T> type) {
        return (T[]) Array.newInstance(type, 0);
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    static class CheckedCollection<E> implements Collection<E>, Serializable {
        private static final long serialVersionUID = 1578914078182001775L;

        final Collection<E> c;
        final Class<E> type;

        @SuppressWarnings("unchecked")
        E typeCheck(Object o) {
            if (o != null && !type.isInstance(o))
                throw new ClassCastException(badElementMsg(o));
            return (E) o;
        }

        private String badElementMsg(Object o) {
            return "Attempt to insert " + o.getClass() +
                " element into collection with element type " + type;
        }

        CheckedCollection(Collection<E> c, Class<E> type) {
            this.c = Objects.requireNonNull(c, "c");
            this.type = Objects.requireNonNull(type, "type");
        }

        public int size()                 { return c.size(); }
        public boolean isEmpty()          { return c.isEmpty(); }
        public boolean contains(Object o) { return c.contains(o); }
        public Object[] toArray()         { return c.toArray(); }
        public <T> T[] toArray(T[] a)     { return c.toArray(a); }
        public String toString()          { return c.toString(); }
        public boolean remove(Object o)   { return c.remove(o); }
        public void clear()               {        c.clear(); }

        public boolean containsAll(Collection<?> coll) {
            return c.containsAll(coll);
        }
        public boolean removeAll(Collection<?> coll) {
            return c.removeAll(coll);
        }
        public boolean retainAll(Collection<?> coll) {
            return c.retainAll(coll);
        }

        public Iterator<E> iterator() {
            // JDK-6363904 - unwrapped iterator could be typecast to
            // ListIterator with unsafe set()
            final Iterator<E> it = c.iterator();
            return new Iterator<E>() {
                public boolean hasNext() { return it.hasNext(); }
                public E next()          { return it.next(); }
                public void remove()     {        it.remove(); }};
        }

        public boolean add(E e)          { return c.add(typeCheck(e)); }

        private E[] zeroLengthElementArray; // Lazily initialized

        private E[] zeroLengthElementArray() {
            return zeroLengthElementArray != null ? zeroLengthElementArray :
                (zeroLengthElementArray = zeroLengthArray(type));
        }

        @SuppressWarnings("unchecked")
        Collection<E> checkedCopyOf(Collection<? extends E> coll) {
            Object[] a;
            try {
                E[] z = zeroLengthElementArray();
                a = coll.toArray(z);
                // Defend against coll violating the toArray contract
                if (a.getClass() != z.getClass())
                    a = Arrays.copyOf(a, a.length, z.getClass());
            } catch (ArrayStoreException ignore) {
                // To get better and consistent diagnostics,
                // we call typeCheck explicitly on each element.
                // We call clone() to defend against coll retaining a
                // reference to the returned array and storing a bad
                // element into it after it has been type checked.
                a = coll.toArray().clone();
                for (Object o : a)
                    typeCheck(o);
            }
            // A slight abuse of the type system, but safe here.
            return (Collection<E>) Arrays.asList(a);
        }

        public boolean addAll(Collection<? extends E> coll) {
            // Doing things this way insulates us from concurrent changes
            // in the contents of coll and provides all-or-nothing
            // semantics (which we wouldn't get if we type-checked each
            // element as we added it)
            return c.addAll(checkedCopyOf(coll));
        }

        // Override default methods in Collection
        @Override
        public void forEach(Consumer<? super E> action) {c.forEach(action);}
        @Override
        public boolean removeIf(Predicate<? super E> filter) {
            return c.removeIf(filter);
        }
        @Override
        public Spliterator<E> spliterator() {return c.spliterator();}
        @Override
        public Stream<E> stream()           {return c.stream();}
        @Override
        public Stream<E> parallelStream()   {return c.parallelStream();}
    }

    /**
     * Returns a dynamically typesafe view of the specified queue.
     * Any attempt to insert an element of the wrong type will result in
     * an immediate {@link ClassCastException}.  Assuming a queue contains
     * no incorrectly typed elements prior to the time a dynamically typesafe
     * view is generated, and that all subsequent access to the queue
     * takes place through the view, it is <i>guaranteed</i> that the
     * queue cannot contain an incorrectly typed element.
     *
     * <p>A discussion of the use of dynamically typesafe views may be
     * found in the documentation for the {@link #checkedCollection
     * checkedCollection} method.
     *
     * <p>The returned queue will be serializable if the specified queue
     * is serializable.
     *
     * <p>Since {@code null} is considered to be a value of any reference
     * type, the returned queue permits insertion of {@code null} elements
     * whenever the backing queue does.
     *
     * <p>
     *  返回指定队列的动态类型安全视图。任何尝试插入错误类型的元素将导致立即{@link ClassCastException}。
     * 假定队列在生成动态类型安全视图之前不包含不正确类型的元素,并且所有后续对队列的访问通过视图进行,则保证队列不能包含不正确的类型元素。
     * 
     *  <p>有关使用动态类型安全视图的讨论可在{@link #checkedCollection checkedCollection}方法的文档中找到。
     * 
     *  <p>如果指定的队列是可串行化的,则返回的队列将是可序列化的。
     * 
     *  <p>由于{@code null}被视为任何引用类型的值,所以返回的队列允许在后台队列中插入{@code null}元素。
     * 
     * 
     * @param <E> the class of the objects in the queue
     * @param queue the queue for which a dynamically typesafe view is to be
     *             returned
     * @param type the type of element that {@code queue} is permitted to hold
     * @return a dynamically typesafe view of the specified queue
     * @since 1.8
     */
    public static <E> Queue<E> checkedQueue(Queue<E> queue, Class<E> type) {
        return new CheckedQueue<>(queue, type);
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    static class CheckedQueue<E>
        extends CheckedCollection<E>
        implements Queue<E>, Serializable
    {
        private static final long serialVersionUID = 1433151992604707767L;
        final Queue<E> queue;

        CheckedQueue(Queue<E> queue, Class<E> elementType) {
            super(queue, elementType);
            this.queue = queue;
        }

        public E element()              {return queue.element();}
        public boolean equals(Object o) {return o == this || c.equals(o);}
        public int hashCode()           {return c.hashCode();}
        public E peek()                 {return queue.peek();}
        public E poll()                 {return queue.poll();}
        public E remove()               {return queue.remove();}
        public boolean offer(E e)       {return queue.offer(typeCheck(e));}
    }

    /**
     * Returns a dynamically typesafe view of the specified set.
     * Any attempt to insert an element of the wrong type will result in
     * an immediate {@link ClassCastException}.  Assuming a set contains
     * no incorrectly typed elements prior to the time a dynamically typesafe
     * view is generated, and that all subsequent access to the set
     * takes place through the view, it is <i>guaranteed</i> that the
     * set cannot contain an incorrectly typed element.
     *
     * <p>A discussion of the use of dynamically typesafe views may be
     * found in the documentation for the {@link #checkedCollection
     * checkedCollection} method.
     *
     * <p>The returned set will be serializable if the specified set is
     * serializable.
     *
     * <p>Since {@code null} is considered to be a value of any reference
     * type, the returned set permits insertion of null elements whenever
     * the backing set does.
     *
     * <p>
     *  返回指定集合的​​动态类型安全视图。任何尝试插入错误类型的元素将导致立即{@link ClassCastException}。
     * 假设一个集合在生成动态类型安全视图之前不包含不正确类型的元素,并且所有对该集合的后续访问通过该视图发生,则保证该集合不能包含不正确的类型元素。
     * 
     * <p>有关使用动态类型安全视图的讨论可在{@link #checkedCollection checkedCollection}方法的文档中找到。
     * 
     *  <p>如果指定的集合是可序列化的,返回的集合将是可序列化的。
     * 
     *  <p>由于{@code null}被认为是任何引用类型的值,所以返回的集合允许在后台集合插入空元素。
     * 
     * 
     * @param <E> the class of the objects in the set
     * @param s the set for which a dynamically typesafe view is to be
     *          returned
     * @param type the type of element that {@code s} is permitted to hold
     * @return a dynamically typesafe view of the specified set
     * @since 1.5
     */
    public static <E> Set<E> checkedSet(Set<E> s, Class<E> type) {
        return new CheckedSet<>(s, type);
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    static class CheckedSet<E> extends CheckedCollection<E>
                                 implements Set<E>, Serializable
    {
        private static final long serialVersionUID = 4694047833775013803L;

        CheckedSet(Set<E> s, Class<E> elementType) { super(s, elementType); }

        public boolean equals(Object o) { return o == this || c.equals(o); }
        public int hashCode()           { return c.hashCode(); }
    }

    /**
     * Returns a dynamically typesafe view of the specified sorted set.
     * Any attempt to insert an element of the wrong type will result in an
     * immediate {@link ClassCastException}.  Assuming a sorted set
     * contains no incorrectly typed elements prior to the time a
     * dynamically typesafe view is generated, and that all subsequent
     * access to the sorted set takes place through the view, it is
     * <i>guaranteed</i> that the sorted set cannot contain an incorrectly
     * typed element.
     *
     * <p>A discussion of the use of dynamically typesafe views may be
     * found in the documentation for the {@link #checkedCollection
     * checkedCollection} method.
     *
     * <p>The returned sorted set will be serializable if the specified sorted
     * set is serializable.
     *
     * <p>Since {@code null} is considered to be a value of any reference
     * type, the returned sorted set permits insertion of null elements
     * whenever the backing sorted set does.
     *
     * <p>
     *  返回指定排序集的动态类型安全视图。任何尝试插入错误类型的元素将导致立即{@link ClassCastException}。
     * 假定排序集合在生成动态类型安全视图之前不包含不正确类型的元素,并且所有后续对排序集合的访问通过视图发生,则保证了排序集合不能包含不正确类型的元素。
     * 
     *  <p>有关使用动态类型安全视图的讨论可在{@link #checkedCollection checkedCollection}方法的文档中找到。
     * 
     *  <p>如果指定的排序集是可序列化的,则返回的排序集将是可序列化的。
     * 
     *  <p>由于{@code null}被视为任何引用类型的值,所以返回的排序集允许在后退排序集合插入空元素。
     * 
     * 
     * @param <E> the class of the objects in the set
     * @param s the sorted set for which a dynamically typesafe view is to be
     *          returned
     * @param type the type of element that {@code s} is permitted to hold
     * @return a dynamically typesafe view of the specified sorted set
     * @since 1.5
     */
    public static <E> SortedSet<E> checkedSortedSet(SortedSet<E> s,
                                                    Class<E> type) {
        return new CheckedSortedSet<>(s, type);
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    static class CheckedSortedSet<E> extends CheckedSet<E>
        implements SortedSet<E>, Serializable
    {
        private static final long serialVersionUID = 1599911165492914959L;

        private final SortedSet<E> ss;

        CheckedSortedSet(SortedSet<E> s, Class<E> type) {
            super(s, type);
            ss = s;
        }

        public Comparator<? super E> comparator() { return ss.comparator(); }
        public E first()                   { return ss.first(); }
        public E last()                    { return ss.last(); }

        public SortedSet<E> subSet(E fromElement, E toElement) {
            return checkedSortedSet(ss.subSet(fromElement,toElement), type);
        }
        public SortedSet<E> headSet(E toElement) {
            return checkedSortedSet(ss.headSet(toElement), type);
        }
        public SortedSet<E> tailSet(E fromElement) {
            return checkedSortedSet(ss.tailSet(fromElement), type);
        }
    }

/**
     * Returns a dynamically typesafe view of the specified navigable set.
     * Any attempt to insert an element of the wrong type will result in an
     * immediate {@link ClassCastException}.  Assuming a navigable set
     * contains no incorrectly typed elements prior to the time a
     * dynamically typesafe view is generated, and that all subsequent
     * access to the navigable set takes place through the view, it is
     * <em>guaranteed</em> that the navigable set cannot contain an incorrectly
     * typed element.
     *
     * <p>A discussion of the use of dynamically typesafe views may be
     * found in the documentation for the {@link #checkedCollection
     * checkedCollection} method.
     *
     * <p>The returned navigable set will be serializable if the specified
     * navigable set is serializable.
     *
     * <p>Since {@code null} is considered to be a value of any reference
     * type, the returned navigable set permits insertion of null elements
     * whenever the backing sorted set does.
     *
     * <p>
     * 返回指定导航集的动态类型安全视图。任何尝试插入错误类型的元素将导致立即{@link ClassCastException}。
     * 假设可导航集合在生成动态类型安全视图之前不包含不正确类型的元素,并且所有对可导航集合的所有后续访问都通过视图发生,则保证</em>可导航集合不能包含不正确类型的元素。
     * 
     *  <p>有关使用动态类型安全视图的讨论可在{@link #checkedCollection checkedCollection}方法的文档中找到。
     * 
     *  <p>如果指定的可导航集是可序列化的,则返回的可导航集将是可序列化的。
     * 
     *  <p>由于{@code null}被认为是任何引用类型的值,所以返回的可导航集允许在后退排序集合插入空元素。
     * 
     * 
     * @param <E> the class of the objects in the set
     * @param s the navigable set for which a dynamically typesafe view is to be
     *          returned
     * @param type the type of element that {@code s} is permitted to hold
     * @return a dynamically typesafe view of the specified navigable set
     * @since 1.8
     */
    public static <E> NavigableSet<E> checkedNavigableSet(NavigableSet<E> s,
                                                    Class<E> type) {
        return new CheckedNavigableSet<>(s, type);
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    static class CheckedNavigableSet<E> extends CheckedSortedSet<E>
        implements NavigableSet<E>, Serializable
    {
        private static final long serialVersionUID = -5429120189805438922L;

        private final NavigableSet<E> ns;

        CheckedNavigableSet(NavigableSet<E> s, Class<E> type) {
            super(s, type);
            ns = s;
        }

        public E lower(E e)                             { return ns.lower(e); }
        public E floor(E e)                             { return ns.floor(e); }
        public E ceiling(E e)                         { return ns.ceiling(e); }
        public E higher(E e)                           { return ns.higher(e); }
        public E pollFirst()                         { return ns.pollFirst(); }
        public E pollLast()                            {return ns.pollLast(); }
        public NavigableSet<E> descendingSet()
                      { return checkedNavigableSet(ns.descendingSet(), type); }
        public Iterator<E> descendingIterator()
            {return checkedNavigableSet(ns.descendingSet(), type).iterator(); }

        public NavigableSet<E> subSet(E fromElement, E toElement) {
            return checkedNavigableSet(ns.subSet(fromElement, true, toElement, false), type);
        }
        public NavigableSet<E> headSet(E toElement) {
            return checkedNavigableSet(ns.headSet(toElement, false), type);
        }
        public NavigableSet<E> tailSet(E fromElement) {
            return checkedNavigableSet(ns.tailSet(fromElement, true), type);
        }

        public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
            return checkedNavigableSet(ns.subSet(fromElement, fromInclusive, toElement, toInclusive), type);
        }

        public NavigableSet<E> headSet(E toElement, boolean inclusive) {
            return checkedNavigableSet(ns.headSet(toElement, inclusive), type);
        }

        public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
            return checkedNavigableSet(ns.tailSet(fromElement, inclusive), type);
        }
    }

    /**
     * Returns a dynamically typesafe view of the specified list.
     * Any attempt to insert an element of the wrong type will result in
     * an immediate {@link ClassCastException}.  Assuming a list contains
     * no incorrectly typed elements prior to the time a dynamically typesafe
     * view is generated, and that all subsequent access to the list
     * takes place through the view, it is <i>guaranteed</i> that the
     * list cannot contain an incorrectly typed element.
     *
     * <p>A discussion of the use of dynamically typesafe views may be
     * found in the documentation for the {@link #checkedCollection
     * checkedCollection} method.
     *
     * <p>The returned list will be serializable if the specified list
     * is serializable.
     *
     * <p>Since {@code null} is considered to be a value of any reference
     * type, the returned list permits insertion of null elements whenever
     * the backing list does.
     *
     * <p>
     *  返回指定列表的动态类型安全视图。任何尝试插入错误类型的元素将导致立即{@link ClassCastException}。
     * 假设在生成动态类型安全视图之前列表不包含不正确类型的元素,并且所有对列表的后续访问都通过视图进行,则保证该列表不能包含不正确的类型元素。
     * 
     *  <p>有关使用动态类型安全视图的讨论可在{@link #checkedCollection checkedCollection}方法的文档中找到。
     * 
     * <p>如果指定的列表是可序列化的,返回的列表将是可序列化的。
     * 
     *  <p>由于{@code null}被视为任何引用类型的值,返回的列表允许在后备列表中插入空元素。
     * 
     * 
     * @param <E> the class of the objects in the list
     * @param list the list for which a dynamically typesafe view is to be
     *             returned
     * @param type the type of element that {@code list} is permitted to hold
     * @return a dynamically typesafe view of the specified list
     * @since 1.5
     */
    public static <E> List<E> checkedList(List<E> list, Class<E> type) {
        return (list instanceof RandomAccess ?
                new CheckedRandomAccessList<>(list, type) :
                new CheckedList<>(list, type));
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    static class CheckedList<E>
        extends CheckedCollection<E>
        implements List<E>
    {
        private static final long serialVersionUID = 65247728283967356L;
        final List<E> list;

        CheckedList(List<E> list, Class<E> type) {
            super(list, type);
            this.list = list;
        }

        public boolean equals(Object o)  { return o == this || list.equals(o); }
        public int hashCode()            { return list.hashCode(); }
        public E get(int index)          { return list.get(index); }
        public E remove(int index)       { return list.remove(index); }
        public int indexOf(Object o)     { return list.indexOf(o); }
        public int lastIndexOf(Object o) { return list.lastIndexOf(o); }

        public E set(int index, E element) {
            return list.set(index, typeCheck(element));
        }

        public void add(int index, E element) {
            list.add(index, typeCheck(element));
        }

        public boolean addAll(int index, Collection<? extends E> c) {
            return list.addAll(index, checkedCopyOf(c));
        }
        public ListIterator<E> listIterator()   { return listIterator(0); }

        public ListIterator<E> listIterator(final int index) {
            final ListIterator<E> i = list.listIterator(index);

            return new ListIterator<E>() {
                public boolean hasNext()     { return i.hasNext(); }
                public E next()              { return i.next(); }
                public boolean hasPrevious() { return i.hasPrevious(); }
                public E previous()          { return i.previous(); }
                public int nextIndex()       { return i.nextIndex(); }
                public int previousIndex()   { return i.previousIndex(); }
                public void remove()         {        i.remove(); }

                public void set(E e) {
                    i.set(typeCheck(e));
                }

                public void add(E e) {
                    i.add(typeCheck(e));
                }

                @Override
                public void forEachRemaining(Consumer<? super E> action) {
                    i.forEachRemaining(action);
                }
            };
        }

        public List<E> subList(int fromIndex, int toIndex) {
            return new CheckedList<>(list.subList(fromIndex, toIndex), type);
        }

        /**
         * {@inheritDoc}
         *
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @throws ClassCastException if the class of an element returned by the
         *         operator prevents it from being added to this collection. The
         *         exception may be thrown after some elements of the list have
         *         already been replaced.
         */
        @Override
        public void replaceAll(UnaryOperator<E> operator) {
            Objects.requireNonNull(operator);
            list.replaceAll(e -> typeCheck(operator.apply(e)));
        }

        @Override
        public void sort(Comparator<? super E> c) {
            list.sort(c);
        }
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    static class CheckedRandomAccessList<E> extends CheckedList<E>
                                            implements RandomAccess
    {
        private static final long serialVersionUID = 1638200125423088369L;

        CheckedRandomAccessList(List<E> list, Class<E> type) {
            super(list, type);
        }

        public List<E> subList(int fromIndex, int toIndex) {
            return new CheckedRandomAccessList<>(
                    list.subList(fromIndex, toIndex), type);
        }
    }

    /**
     * Returns a dynamically typesafe view of the specified map.
     * Any attempt to insert a mapping whose key or value have the wrong
     * type will result in an immediate {@link ClassCastException}.
     * Similarly, any attempt to modify the value currently associated with
     * a key will result in an immediate {@link ClassCastException},
     * whether the modification is attempted directly through the map
     * itself, or through a {@link Map.Entry} instance obtained from the
     * map's {@link Map#entrySet() entry set} view.
     *
     * <p>Assuming a map contains no incorrectly typed keys or values
     * prior to the time a dynamically typesafe view is generated, and
     * that all subsequent access to the map takes place through the view
     * (or one of its collection views), it is <i>guaranteed</i> that the
     * map cannot contain an incorrectly typed key or value.
     *
     * <p>A discussion of the use of dynamically typesafe views may be
     * found in the documentation for the {@link #checkedCollection
     * checkedCollection} method.
     *
     * <p>The returned map will be serializable if the specified map is
     * serializable.
     *
     * <p>Since {@code null} is considered to be a value of any reference
     * type, the returned map permits insertion of null keys or values
     * whenever the backing map does.
     *
     * <p>
     *  返回指定地图的动态类型安全视图。任何尝试插入其键或值具有错误类型的映射将导致立即{@link ClassCastException}。
     * 类似地,任何修改当前与键相关联的值的尝试将导致立即{@link ClassCastException},无论是直接通过映射本身尝试修改还是通过从映射获得的{@link Map.Entry}实例{@link Map#entrySet()entry set}
     * 视图。
     *  返回指定地图的动态类型安全视图。任何尝试插入其键或值具有错误类型的映射将导致立即{@link ClassCastException}。
     * 
     *  <p>假设地图在生成动态类型安全视图之前不包含不正确键入的键或值,并且所有后续对地图的访问都通过视图(或其集合视图之一)进行,则<i> >确保</i>地图不能包含错误键入的键或值。
     * 
     *  <p>有关使用动态类型安全视图的讨论可在{@link #checkedCollection checkedCollection}方法的文档中找到。
     * 
     *  <p>如果指定的地图是可序列化的,返回的地图将是可序列化的。
     * 
     *  <p>由于{@code null}被认为是任何引用类型的值,所以返回的映射允许在后台映射时插入空键或值。
     * 
     * 
     * @param <K> the class of the map keys
     * @param <V> the class of the map values
     * @param m the map for which a dynamically typesafe view is to be
     *          returned
     * @param keyType the type of key that {@code m} is permitted to hold
     * @param valueType the type of value that {@code m} is permitted to hold
     * @return a dynamically typesafe view of the specified map
     * @since 1.5
     */
    public static <K, V> Map<K, V> checkedMap(Map<K, V> m,
                                              Class<K> keyType,
                                              Class<V> valueType) {
        return new CheckedMap<>(m, keyType, valueType);
    }


    /**
    /* <p>
    /* 
     * @serial include
     */
    private static class CheckedMap<K,V>
        implements Map<K,V>, Serializable
    {
        private static final long serialVersionUID = 5742860141034234728L;

        private final Map<K, V> m;
        final Class<K> keyType;
        final Class<V> valueType;

        private void typeCheck(Object key, Object value) {
            if (key != null && !keyType.isInstance(key))
                throw new ClassCastException(badKeyMsg(key));

            if (value != null && !valueType.isInstance(value))
                throw new ClassCastException(badValueMsg(value));
        }

        private BiFunction<? super K, ? super V, ? extends V> typeCheck(
                BiFunction<? super K, ? super V, ? extends V> func) {
            Objects.requireNonNull(func);
            return (k, v) -> {
                V newValue = func.apply(k, v);
                typeCheck(k, newValue);
                return newValue;
            };
        }

        private String badKeyMsg(Object key) {
            return "Attempt to insert " + key.getClass() +
                    " key into map with key type " + keyType;
        }

        private String badValueMsg(Object value) {
            return "Attempt to insert " + value.getClass() +
                    " value into map with value type " + valueType;
        }

        CheckedMap(Map<K, V> m, Class<K> keyType, Class<V> valueType) {
            this.m = Objects.requireNonNull(m);
            this.keyType = Objects.requireNonNull(keyType);
            this.valueType = Objects.requireNonNull(valueType);
        }

        public int size()                      { return m.size(); }
        public boolean isEmpty()               { return m.isEmpty(); }
        public boolean containsKey(Object key) { return m.containsKey(key); }
        public boolean containsValue(Object v) { return m.containsValue(v); }
        public V get(Object key)               { return m.get(key); }
        public V remove(Object key)            { return m.remove(key); }
        public void clear()                    { m.clear(); }
        public Set<K> keySet()                 { return m.keySet(); }
        public Collection<V> values()          { return m.values(); }
        public boolean equals(Object o)        { return o == this || m.equals(o); }
        public int hashCode()                  { return m.hashCode(); }
        public String toString()               { return m.toString(); }

        public V put(K key, V value) {
            typeCheck(key, value);
            return m.put(key, value);
        }

        @SuppressWarnings("unchecked")
        public void putAll(Map<? extends K, ? extends V> t) {
            // Satisfy the following goals:
            // - good diagnostics in case of type mismatch
            // - all-or-nothing semantics
            // - protection from malicious t
            // - correct behavior if t is a concurrent map
            Object[] entries = t.entrySet().toArray();
            List<Map.Entry<K,V>> checked = new ArrayList<>(entries.length);
            for (Object o : entries) {
                Map.Entry<?,?> e = (Map.Entry<?,?>) o;
                Object k = e.getKey();
                Object v = e.getValue();
                typeCheck(k, v);
                checked.add(
                        new AbstractMap.SimpleImmutableEntry<>((K)k, (V)v));
            }
            for (Map.Entry<K,V> e : checked)
                m.put(e.getKey(), e.getValue());
        }

        private transient Set<Map.Entry<K,V>> entrySet;

        public Set<Map.Entry<K,V>> entrySet() {
            if (entrySet==null)
                entrySet = new CheckedEntrySet<>(m.entrySet(), valueType);
            return entrySet;
        }

        // Override default methods in Map
        @Override
        public void forEach(BiConsumer<? super K, ? super V> action) {
            m.forEach(action);
        }

        @Override
        public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
            m.replaceAll(typeCheck(function));
        }

        @Override
        public V putIfAbsent(K key, V value) {
            typeCheck(key, value);
            return m.putIfAbsent(key, value);
        }

        @Override
        public boolean remove(Object key, Object value) {
            return m.remove(key, value);
        }

        @Override
        public boolean replace(K key, V oldValue, V newValue) {
            typeCheck(key, newValue);
            return m.replace(key, oldValue, newValue);
        }

        @Override
        public V replace(K key, V value) {
            typeCheck(key, value);
            return m.replace(key, value);
        }

        @Override
        public V computeIfAbsent(K key,
                Function<? super K, ? extends V> mappingFunction) {
            Objects.requireNonNull(mappingFunction);
            return m.computeIfAbsent(key, k -> {
                V value = mappingFunction.apply(k);
                typeCheck(k, value);
                return value;
            });
        }

        @Override
        public V computeIfPresent(K key,
                BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
            return m.computeIfPresent(key, typeCheck(remappingFunction));
        }

        @Override
        public V compute(K key,
                BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
            return m.compute(key, typeCheck(remappingFunction));
        }

        @Override
        public V merge(K key, V value,
                BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
            Objects.requireNonNull(remappingFunction);
            return m.merge(key, value, (v1, v2) -> {
                V newValue = remappingFunction.apply(v1, v2);
                typeCheck(null, newValue);
                return newValue;
            });
        }

        /**
         * We need this class in addition to CheckedSet as Map.Entry permits
         * modification of the backing Map via the setValue operation.  This
         * class is subtle: there are many possible attacks that must be
         * thwarted.
         *
         * <p>
         * 我们需要这个类除了CheckedSet作为Map.Entry允许通过setValue操作修改背景Map。这个类是微妙的：有许多可能的攻击必须挫败。
         * 
         * 
         * @serial exclude
         */
        static class CheckedEntrySet<K,V> implements Set<Map.Entry<K,V>> {
            private final Set<Map.Entry<K,V>> s;
            private final Class<V> valueType;

            CheckedEntrySet(Set<Map.Entry<K, V>> s, Class<V> valueType) {
                this.s = s;
                this.valueType = valueType;
            }

            public int size()        { return s.size(); }
            public boolean isEmpty() { return s.isEmpty(); }
            public String toString() { return s.toString(); }
            public int hashCode()    { return s.hashCode(); }
            public void clear()      {        s.clear(); }

            public boolean add(Map.Entry<K, V> e) {
                throw new UnsupportedOperationException();
            }
            public boolean addAll(Collection<? extends Map.Entry<K, V>> coll) {
                throw new UnsupportedOperationException();
            }

            public Iterator<Map.Entry<K,V>> iterator() {
                final Iterator<Map.Entry<K, V>> i = s.iterator();
                final Class<V> valueType = this.valueType;

                return new Iterator<Map.Entry<K,V>>() {
                    public boolean hasNext() { return i.hasNext(); }
                    public void remove()     { i.remove(); }

                    public Map.Entry<K,V> next() {
                        return checkedEntry(i.next(), valueType);
                    }
                };
            }

            @SuppressWarnings("unchecked")
            public Object[] toArray() {
                Object[] source = s.toArray();

                /*
                 * Ensure that we don't get an ArrayStoreException even if
                 * s.toArray returns an array of something other than Object
                 * <p>
                 *  确保我们不会得到一个ArrayStoreException,即使s.toArray返回一个非Object对象的数组
                 * 
                 */
                Object[] dest = (CheckedEntry.class.isInstance(
                    source.getClass().getComponentType()) ? source :
                                 new Object[source.length]);

                for (int i = 0; i < source.length; i++)
                    dest[i] = checkedEntry((Map.Entry<K,V>)source[i],
                                           valueType);
                return dest;
            }

            @SuppressWarnings("unchecked")
            public <T> T[] toArray(T[] a) {
                // We don't pass a to s.toArray, to avoid window of
                // vulnerability wherein an unscrupulous multithreaded client
                // could get his hands on raw (unwrapped) Entries from s.
                T[] arr = s.toArray(a.length==0 ? a : Arrays.copyOf(a, 0));

                for (int i=0; i<arr.length; i++)
                    arr[i] = (T) checkedEntry((Map.Entry<K,V>)arr[i],
                                              valueType);
                if (arr.length > a.length)
                    return arr;

                System.arraycopy(arr, 0, a, 0, arr.length);
                if (a.length > arr.length)
                    a[arr.length] = null;
                return a;
            }

            /**
             * This method is overridden to protect the backing set against
             * an object with a nefarious equals function that senses
             * that the equality-candidate is Map.Entry and calls its
             * setValue method.
             * <p>
             *  覆盖此方法以保护后端集对具有恶意equals函数的对象,该函数检测等式候选是Map.Entry并调用其setValue方法。
             * 
             */
            public boolean contains(Object o) {
                if (!(o instanceof Map.Entry))
                    return false;
                Map.Entry<?,?> e = (Map.Entry<?,?>) o;
                return s.contains(
                    (e instanceof CheckedEntry) ? e : checkedEntry(e, valueType));
            }

            /**
             * The bulk collection methods are overridden to protect
             * against an unscrupulous collection whose contains(Object o)
             * method senses when o is a Map.Entry, and calls o.setValue.
             * <p>
             *  覆盖批量收集方法以防止一个不道德的集合,其contains(Object o)方法在o是Map.Entry时检测,并调用o.setValue。
             * 
             */
            public boolean containsAll(Collection<?> c) {
                for (Object o : c)
                    if (!contains(o)) // Invokes safe contains() above
                        return false;
                return true;
            }

            public boolean remove(Object o) {
                if (!(o instanceof Map.Entry))
                    return false;
                return s.remove(new AbstractMap.SimpleImmutableEntry
                                <>((Map.Entry<?,?>)o));
            }

            public boolean removeAll(Collection<?> c) {
                return batchRemove(c, false);
            }
            public boolean retainAll(Collection<?> c) {
                return batchRemove(c, true);
            }
            private boolean batchRemove(Collection<?> c, boolean complement) {
                Objects.requireNonNull(c);
                boolean modified = false;
                Iterator<Map.Entry<K,V>> it = iterator();
                while (it.hasNext()) {
                    if (c.contains(it.next()) != complement) {
                        it.remove();
                        modified = true;
                    }
                }
                return modified;
            }

            public boolean equals(Object o) {
                if (o == this)
                    return true;
                if (!(o instanceof Set))
                    return false;
                Set<?> that = (Set<?>) o;
                return that.size() == s.size()
                    && containsAll(that); // Invokes safe containsAll() above
            }

            static <K,V,T> CheckedEntry<K,V,T> checkedEntry(Map.Entry<K,V> e,
                                                            Class<T> valueType) {
                return new CheckedEntry<>(e, valueType);
            }

            /**
             * This "wrapper class" serves two purposes: it prevents
             * the client from modifying the backing Map, by short-circuiting
             * the setValue method, and it protects the backing Map against
             * an ill-behaved Map.Entry that attempts to modify another
             * Map.Entry when asked to perform an equality check.
             * <p>
             *  这个"包装器类"有两个目的：它防止客户端通过短接setValue方法来修改背景映射,并且保护背景映射免受不良行为Map.Entry的影响,该Map.Entry尝试修改另一个Map.Entry要求执行平
             * 等检查。
             * 
             */
            private static class CheckedEntry<K,V,T> implements Map.Entry<K,V> {
                private final Map.Entry<K, V> e;
                private final Class<T> valueType;

                CheckedEntry(Map.Entry<K, V> e, Class<T> valueType) {
                    this.e = Objects.requireNonNull(e);
                    this.valueType = Objects.requireNonNull(valueType);
                }

                public K getKey()        { return e.getKey(); }
                public V getValue()      { return e.getValue(); }
                public int hashCode()    { return e.hashCode(); }
                public String toString() { return e.toString(); }

                public V setValue(V value) {
                    if (value != null && !valueType.isInstance(value))
                        throw new ClassCastException(badValueMsg(value));
                    return e.setValue(value);
                }

                private String badValueMsg(Object value) {
                    return "Attempt to insert " + value.getClass() +
                        " value into map with value type " + valueType;
                }

                public boolean equals(Object o) {
                    if (o == this)
                        return true;
                    if (!(o instanceof Map.Entry))
                        return false;
                    return e.equals(new AbstractMap.SimpleImmutableEntry
                                    <>((Map.Entry<?,?>)o));
                }
            }
        }
    }

    /**
     * Returns a dynamically typesafe view of the specified sorted map.
     * Any attempt to insert a mapping whose key or value have the wrong
     * type will result in an immediate {@link ClassCastException}.
     * Similarly, any attempt to modify the value currently associated with
     * a key will result in an immediate {@link ClassCastException},
     * whether the modification is attempted directly through the map
     * itself, or through a {@link Map.Entry} instance obtained from the
     * map's {@link Map#entrySet() entry set} view.
     *
     * <p>Assuming a map contains no incorrectly typed keys or values
     * prior to the time a dynamically typesafe view is generated, and
     * that all subsequent access to the map takes place through the view
     * (or one of its collection views), it is <i>guaranteed</i> that the
     * map cannot contain an incorrectly typed key or value.
     *
     * <p>A discussion of the use of dynamically typesafe views may be
     * found in the documentation for the {@link #checkedCollection
     * checkedCollection} method.
     *
     * <p>The returned map will be serializable if the specified map is
     * serializable.
     *
     * <p>Since {@code null} is considered to be a value of any reference
     * type, the returned map permits insertion of null keys or values
     * whenever the backing map does.
     *
     * <p>
     *  返回指定排序映射的动态类型安全视图。任何尝试插入其键或值具有错误类型的映射将导致立即{@link ClassCastException}。
     * 类似地,任何修改当前与键相关联的值的尝试将导致立即{@link ClassCastException},无论是直接通过映射本身尝试修改还是通过从映射获得的{@link Map.Entry}实例{@link Map#entrySet()entry set}
     * 视图。
     *  返回指定排序映射的动态类型安全视图。任何尝试插入其键或值具有错误类型的映射将导致立即{@link ClassCastException}。
     * 
     * <p>假设地图在生成动态类型安全视图之前不包含不正确键入的键或值,并且所有后续对地图的访问都通过视图(或其集合视图之一)进行,则<i> >确保</i>地图不能包含错误键入的键或值。
     * 
     *  <p>有关使用动态类型安全视图的讨论可在{@link #checkedCollection checkedCollection}方法的文档中找到。
     * 
     *  <p>如果指定的地图是可序列化的,返回的地图将是可序列化的。
     * 
     *  <p>由于{@code null}被认为是任何引用类型的值,所以返回的映射允许在后台映射时插入空键或值。
     * 
     * 
     * @param <K> the class of the map keys
     * @param <V> the class of the map values
     * @param m the map for which a dynamically typesafe view is to be
     *          returned
     * @param keyType the type of key that {@code m} is permitted to hold
     * @param valueType the type of value that {@code m} is permitted to hold
     * @return a dynamically typesafe view of the specified map
     * @since 1.5
     */
    public static <K,V> SortedMap<K,V> checkedSortedMap(SortedMap<K, V> m,
                                                        Class<K> keyType,
                                                        Class<V> valueType) {
        return new CheckedSortedMap<>(m, keyType, valueType);
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    static class CheckedSortedMap<K,V> extends CheckedMap<K,V>
        implements SortedMap<K,V>, Serializable
    {
        private static final long serialVersionUID = 1599671320688067438L;

        private final SortedMap<K, V> sm;

        CheckedSortedMap(SortedMap<K, V> m,
                         Class<K> keyType, Class<V> valueType) {
            super(m, keyType, valueType);
            sm = m;
        }

        public Comparator<? super K> comparator() { return sm.comparator(); }
        public K firstKey()                       { return sm.firstKey(); }
        public K lastKey()                        { return sm.lastKey(); }

        public SortedMap<K,V> subMap(K fromKey, K toKey) {
            return checkedSortedMap(sm.subMap(fromKey, toKey),
                                    keyType, valueType);
        }
        public SortedMap<K,V> headMap(K toKey) {
            return checkedSortedMap(sm.headMap(toKey), keyType, valueType);
        }
        public SortedMap<K,V> tailMap(K fromKey) {
            return checkedSortedMap(sm.tailMap(fromKey), keyType, valueType);
        }
    }

    /**
     * Returns a dynamically typesafe view of the specified navigable map.
     * Any attempt to insert a mapping whose key or value have the wrong
     * type will result in an immediate {@link ClassCastException}.
     * Similarly, any attempt to modify the value currently associated with
     * a key will result in an immediate {@link ClassCastException},
     * whether the modification is attempted directly through the map
     * itself, or through a {@link Map.Entry} instance obtained from the
     * map's {@link Map#entrySet() entry set} view.
     *
     * <p>Assuming a map contains no incorrectly typed keys or values
     * prior to the time a dynamically typesafe view is generated, and
     * that all subsequent access to the map takes place through the view
     * (or one of its collection views), it is <em>guaranteed</em> that the
     * map cannot contain an incorrectly typed key or value.
     *
     * <p>A discussion of the use of dynamically typesafe views may be
     * found in the documentation for the {@link #checkedCollection
     * checkedCollection} method.
     *
     * <p>The returned map will be serializable if the specified map is
     * serializable.
     *
     * <p>Since {@code null} is considered to be a value of any reference
     * type, the returned map permits insertion of null keys or values
     * whenever the backing map does.
     *
     * <p>
     *  返回指定导航地图的动态类型安全视图。任何尝试插入其键或值具有错误类型的映射将导致立即{@link ClassCastException}。
     * 类似地,任何修改当前与键相关联的值的尝试将导致立即{@link ClassCastException},无论是直接通过映射本身尝试修改还是通过从映射获得的{@link Map.Entry}实例{@link Map#entrySet()entry set}
     * 视图。
     *  返回指定导航地图的动态类型安全视图。任何尝试插入其键或值具有错误类型的映射将导致立即{@link ClassCastException}。
     * 
     * <p>假设地图在生成动态类型安全视图之前不包含不正确键入的键或值,并且所有后续对地图的访问都通过视图(或其集合视图之一)进行,则<em> >保证</em>：地图不能包含键入错误的键或值。
     * 
     *  <p>有关使用动态类型安全视图的讨论可在{@link #checkedCollection checkedCollection}方法的文档中找到。
     * 
     *  <p>如果指定的地图是可序列化的,返回的地图将是可序列化的。
     * 
     *  <p>由于{@code null}被认为是任何引用类型的值,所以返回的映射允许在后台映射时插入空键或值。
     * 
     * 
     * @param <K> type of map keys
     * @param <V> type of map values
     * @param m the map for which a dynamically typesafe view is to be
     *          returned
     * @param keyType the type of key that {@code m} is permitted to hold
     * @param valueType the type of value that {@code m} is permitted to hold
     * @return a dynamically typesafe view of the specified map
     * @since 1.8
     */
    public static <K,V> NavigableMap<K,V> checkedNavigableMap(NavigableMap<K, V> m,
                                                        Class<K> keyType,
                                                        Class<V> valueType) {
        return new CheckedNavigableMap<>(m, keyType, valueType);
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    static class CheckedNavigableMap<K,V> extends CheckedSortedMap<K,V>
        implements NavigableMap<K,V>, Serializable
    {
        private static final long serialVersionUID = -4852462692372534096L;

        private final NavigableMap<K, V> nm;

        CheckedNavigableMap(NavigableMap<K, V> m,
                         Class<K> keyType, Class<V> valueType) {
            super(m, keyType, valueType);
            nm = m;
        }

        public Comparator<? super K> comparator()   { return nm.comparator(); }
        public K firstKey()                           { return nm.firstKey(); }
        public K lastKey()                             { return nm.lastKey(); }

        public Entry<K, V> lowerEntry(K key) {
            Entry<K,V> lower = nm.lowerEntry(key);
            return (null != lower)
                ? new CheckedMap.CheckedEntrySet.CheckedEntry<>(lower, valueType)
                : null;
        }

        public K lowerKey(K key)                   { return nm.lowerKey(key); }

        public Entry<K, V> floorEntry(K key) {
            Entry<K,V> floor = nm.floorEntry(key);
            return (null != floor)
                ? new CheckedMap.CheckedEntrySet.CheckedEntry<>(floor, valueType)
                : null;
        }

        public K floorKey(K key)                   { return nm.floorKey(key); }

        public Entry<K, V> ceilingEntry(K key) {
            Entry<K,V> ceiling = nm.ceilingEntry(key);
            return (null != ceiling)
                ? new CheckedMap.CheckedEntrySet.CheckedEntry<>(ceiling, valueType)
                : null;
        }

        public K ceilingKey(K key)               { return nm.ceilingKey(key); }

        public Entry<K, V> higherEntry(K key) {
            Entry<K,V> higher = nm.higherEntry(key);
            return (null != higher)
                ? new CheckedMap.CheckedEntrySet.CheckedEntry<>(higher, valueType)
                : null;
        }

        public K higherKey(K key)                 { return nm.higherKey(key); }

        public Entry<K, V> firstEntry() {
            Entry<K,V> first = nm.firstEntry();
            return (null != first)
                ? new CheckedMap.CheckedEntrySet.CheckedEntry<>(first, valueType)
                : null;
        }

        public Entry<K, V> lastEntry() {
            Entry<K,V> last = nm.lastEntry();
            return (null != last)
                ? new CheckedMap.CheckedEntrySet.CheckedEntry<>(last, valueType)
                : null;
        }

        public Entry<K, V> pollFirstEntry() {
            Entry<K,V> entry = nm.pollFirstEntry();
            return (null == entry)
                ? null
                : new CheckedMap.CheckedEntrySet.CheckedEntry<>(entry, valueType);
        }

        public Entry<K, V> pollLastEntry() {
            Entry<K,V> entry = nm.pollLastEntry();
            return (null == entry)
                ? null
                : new CheckedMap.CheckedEntrySet.CheckedEntry<>(entry, valueType);
        }

        public NavigableMap<K, V> descendingMap() {
            return checkedNavigableMap(nm.descendingMap(), keyType, valueType);
        }

        public NavigableSet<K> keySet() {
            return navigableKeySet();
        }

        public NavigableSet<K> navigableKeySet() {
            return checkedNavigableSet(nm.navigableKeySet(), keyType);
        }

        public NavigableSet<K> descendingKeySet() {
            return checkedNavigableSet(nm.descendingKeySet(), keyType);
        }

        @Override
        public NavigableMap<K,V> subMap(K fromKey, K toKey) {
            return checkedNavigableMap(nm.subMap(fromKey, true, toKey, false),
                                    keyType, valueType);
        }

        @Override
        public NavigableMap<K,V> headMap(K toKey) {
            return checkedNavigableMap(nm.headMap(toKey, false), keyType, valueType);
        }

        @Override
        public NavigableMap<K,V> tailMap(K fromKey) {
            return checkedNavigableMap(nm.tailMap(fromKey, true), keyType, valueType);
        }

        public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
            return checkedNavigableMap(nm.subMap(fromKey, fromInclusive, toKey, toInclusive), keyType, valueType);
        }

        public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
            return checkedNavigableMap(nm.headMap(toKey, inclusive), keyType, valueType);
        }

        public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
            return checkedNavigableMap(nm.tailMap(fromKey, inclusive), keyType, valueType);
        }
    }

    // Empty collections

    /**
     * Returns an iterator that has no elements.  More precisely,
     *
     * <ul>
     * <li>{@link Iterator#hasNext hasNext} always returns {@code
     * false}.</li>
     * <li>{@link Iterator#next next} always throws {@link
     * NoSuchElementException}.</li>
     * <li>{@link Iterator#remove remove} always throws {@link
     * IllegalStateException}.</li>
     * </ul>
     *
     * <p>Implementations of this method are permitted, but not
     * required, to return the same object from multiple invocations.
     *
     * <p>
     *  返回没有元素的迭代器。更确切地说,
     * 
     * <ul>
     *  <li> {@ link Iterator#hasNext hasNext}始终返回{@code false}。
     * </li> <li> {@ link Iterator#next next}总是引发{@link NoSuchElementException}。
     * </li> <li> @link Iterator#remove remove}总是引发{@link IllegalStateException}。</li>。
     * </ul>
     * 
     *  <p>允许(但不是必须)从多个调用返回相同对象的此方法的实现。
     * 
     * 
     * @param <T> type of elements, if there were any, in the iterator
     * @return an empty iterator
     * @since 1.7
     */
    @SuppressWarnings("unchecked")
    public static <T> Iterator<T> emptyIterator() {
        return (Iterator<T>) EmptyIterator.EMPTY_ITERATOR;
    }

    private static class EmptyIterator<E> implements Iterator<E> {
        static final EmptyIterator<Object> EMPTY_ITERATOR
            = new EmptyIterator<>();

        public boolean hasNext() { return false; }
        public E next() { throw new NoSuchElementException(); }
        public void remove() { throw new IllegalStateException(); }
        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);
        }
    }

    /**
     * Returns a list iterator that has no elements.  More precisely,
     *
     * <ul>
     * <li>{@link Iterator#hasNext hasNext} and {@link
     * ListIterator#hasPrevious hasPrevious} always return {@code
     * false}.</li>
     * <li>{@link Iterator#next next} and {@link ListIterator#previous
     * previous} always throw {@link NoSuchElementException}.</li>
     * <li>{@link Iterator#remove remove} and {@link ListIterator#set
     * set} always throw {@link IllegalStateException}.</li>
     * <li>{@link ListIterator#add add} always throws {@link
     * UnsupportedOperationException}.</li>
     * <li>{@link ListIterator#nextIndex nextIndex} always returns
     * {@code 0}.</li>
     * <li>{@link ListIterator#previousIndex previousIndex} always
     * returns {@code -1}.</li>
     * </ul>
     *
     * <p>Implementations of this method are permitted, but not
     * required, to return the same object from multiple invocations.
     *
     * <p>
     *  返回没有元素的列表迭代器。更确切地说,
     * 
     * <ul>
     * <li> {@ link Iterator#hasNext hasNext}和{@link ListIterator#hasPrevious hasPrevious}总是返回{@code false}。
     * </li> <li> {@ link Iterator#next next}和{@link ListIterator#previous </li> <li> {@ link Iterator#remove remove}
     * 和{@link ListIterator#set set}总是会抛出{@link IllegalStateException}。
     * </li> <li> </li> {@ link ListIterator#nextIndex nextIndex}总是返回{@code 0}。
     * </li> <li> {@ link ListIterator# previousIndex previousIndex}总是返回{@code -1}。</li>。
     * </ul>
     * 
     *  <p>允许(但不是必须)从多个调用返回相同对象的此方法的实现。
     * 
     * 
     * @param <T> type of elements, if there were any, in the iterator
     * @return an empty list iterator
     * @since 1.7
     */
    @SuppressWarnings("unchecked")
    public static <T> ListIterator<T> emptyListIterator() {
        return (ListIterator<T>) EmptyListIterator.EMPTY_ITERATOR;
    }

    private static class EmptyListIterator<E>
        extends EmptyIterator<E>
        implements ListIterator<E>
    {
        static final EmptyListIterator<Object> EMPTY_ITERATOR
            = new EmptyListIterator<>();

        public boolean hasPrevious() { return false; }
        public E previous() { throw new NoSuchElementException(); }
        public int nextIndex()     { return 0; }
        public int previousIndex() { return -1; }
        public void set(E e) { throw new IllegalStateException(); }
        public void add(E e) { throw new UnsupportedOperationException(); }
    }

    /**
     * Returns an enumeration that has no elements.  More precisely,
     *
     * <ul>
     * <li>{@link Enumeration#hasMoreElements hasMoreElements} always
     * returns {@code false}.</li>
     * <li> {@link Enumeration#nextElement nextElement} always throws
     * {@link NoSuchElementException}.</li>
     * </ul>
     *
     * <p>Implementations of this method are permitted, but not
     * required, to return the same object from multiple invocations.
     *
     * <p>
     *  返回没有元素的枚举。更确切地说,
     * 
     * <ul>
     *  <li> {@ link Enumeration#hasMoreElements hasMoreElements}总是返回{@code false}。
     * </li> <li> {@link Enumeration#nextElement nextElement}总是引发{@link NoSuchElementException}。
     * </ul>
     * 
     *  <p>允许(但不是必须)从多个调用返回相同对象的此方法的实现。
     * 
     * 
     * @param  <T> the class of the objects in the enumeration
     * @return an empty enumeration
     * @since 1.7
     */
    @SuppressWarnings("unchecked")
    public static <T> Enumeration<T> emptyEnumeration() {
        return (Enumeration<T>) EmptyEnumeration.EMPTY_ENUMERATION;
    }

    private static class EmptyEnumeration<E> implements Enumeration<E> {
        static final EmptyEnumeration<Object> EMPTY_ENUMERATION
            = new EmptyEnumeration<>();

        public boolean hasMoreElements() { return false; }
        public E nextElement() { throw new NoSuchElementException(); }
    }

    /**
     * The empty set (immutable).  This set is serializable.
     *
     * <p>
     *  空集(immutable)。这个集合是可序列化的。
     * 
     * 
     * @see #emptySet()
     */
    @SuppressWarnings("rawtypes")
    public static final Set EMPTY_SET = new EmptySet<>();

    /**
     * Returns an empty set (immutable).  This set is serializable.
     * Unlike the like-named field, this method is parameterized.
     *
     * <p>This example illustrates the type-safe way to obtain an empty set:
     * <pre>
     *     Set&lt;String&gt; s = Collections.emptySet();
     * </pre>
     * @implNote Implementations of this method need not create a separate
     * {@code Set} object for each call.  Using this method is likely to have
     * comparable cost to using the like-named field.  (Unlike this method, the
     * field does not provide type safety.)
     *
     * <p>
     *  返回一个空集(不可变)。这个集合是可序列化的。与类似命名的字段不同,此方法是参数化的。
     * 
     *  <p>此示例说明了获取空集的类型安全方式：
     * <pre>
     *  设置&lt; String&gt; s = Collections.emptySet();
     * </pre>
     * @implNote此方法的实现不需要为每个调用创建单独的{@code Set}对象。使用这种方法可能具有与使用相同命名字段相当的成本。 (与此方法不同,该字段不提供类型安全性。)
     * 
     * 
     * @param  <T> the class of the objects in the set
     * @return the empty set
     *
     * @see #EMPTY_SET
     * @since 1.5
     */
    @SuppressWarnings("unchecked")
    public static final <T> Set<T> emptySet() {
        return (Set<T>) EMPTY_SET;
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    private static class EmptySet<E>
        extends AbstractSet<E>
        implements Serializable
    {
        private static final long serialVersionUID = 1582296315990362920L;

        public Iterator<E> iterator() { return emptyIterator(); }

        public int size() {return 0;}
        public boolean isEmpty() {return true;}

        public boolean contains(Object obj) {return false;}
        public boolean containsAll(Collection<?> c) { return c.isEmpty(); }

        public Object[] toArray() { return new Object[0]; }

        public <T> T[] toArray(T[] a) {
            if (a.length > 0)
                a[0] = null;
            return a;
        }

        // Override default methods in Collection
        @Override
        public void forEach(Consumer<? super E> action) {
            Objects.requireNonNull(action);
        }
        @Override
        public boolean removeIf(Predicate<? super E> filter) {
            Objects.requireNonNull(filter);
            return false;
        }
        @Override
        public Spliterator<E> spliterator() { return Spliterators.emptySpliterator(); }

        // Preserves singleton property
        private Object readResolve() {
            return EMPTY_SET;
        }
    }

    /**
     * Returns an empty sorted set (immutable).  This set is serializable.
     *
     * <p>This example illustrates the type-safe way to obtain an empty
     * sorted set:
     * <pre> {@code
     *     SortedSet<String> s = Collections.emptySortedSet();
     * }</pre>
     *
     * @implNote Implementations of this method need not create a separate
     * {@code SortedSet} object for each call.
     *
     * <p>
     *  返回一个空的排序集(不可变)。这个集合是可序列化的。
     * 
     *  <p>此示例说明了获取空排序集的类型安全方法：<pre> {@code SortedSet <String> s = Collections.emptySortedSet(); } </pre>
     * 
     *  @implNote此方法的实现不需要为每个调用创建单独的{@code SortedSet}对象。
     * 
     * 
     * @param <E> type of elements, if there were any, in the set
     * @return the empty sorted set
     * @since 1.8
     */
    @SuppressWarnings("unchecked")
    public static <E> SortedSet<E> emptySortedSet() {
        return (SortedSet<E>) UnmodifiableNavigableSet.EMPTY_NAVIGABLE_SET;
    }

    /**
     * Returns an empty navigable set (immutable).  This set is serializable.
     *
     * <p>This example illustrates the type-safe way to obtain an empty
     * navigable set:
     * <pre> {@code
     *     NavigableSet<String> s = Collections.emptyNavigableSet();
     * }</pre>
     *
     * @implNote Implementations of this method need not
     * create a separate {@code NavigableSet} object for each call.
     *
     * <p>
     *  返回一个空的可导航集(不可变)。这个集合是可序列化的。
     * 
     *  <p>此示例说明了获取空导航集的类型安全方法：<pre> {@code NavigableSet <String> s = Collections.emptyNavigableSet(); } </pre>
     * 。
     * 
     *  @implNote此方法的实现不需要为每个调用创建单独的{@code NavigableSet}对象。
     * 
     * 
     * @param <E> type of elements, if there were any, in the set
     * @return the empty navigable set
     * @since 1.8
     */
    @SuppressWarnings("unchecked")
    public static <E> NavigableSet<E> emptyNavigableSet() {
        return (NavigableSet<E>) UnmodifiableNavigableSet.EMPTY_NAVIGABLE_SET;
    }

    /**
     * The empty list (immutable).  This list is serializable.
     *
     * <p>
     *  空列表(不可变)。此列表是可序列化的。
     * 
     * 
     * @see #emptyList()
     */
    @SuppressWarnings("rawtypes")
    public static final List EMPTY_LIST = new EmptyList<>();

    /**
     * Returns an empty list (immutable).  This list is serializable.
     *
     * <p>This example illustrates the type-safe way to obtain an empty list:
     * <pre>
     *     List&lt;String&gt; s = Collections.emptyList();
     * </pre>
     *
     * @implNote
     * Implementations of this method need not create a separate <tt>List</tt>
     * object for each call.   Using this method is likely to have comparable
     * cost to using the like-named field.  (Unlike this method, the field does
     * not provide type safety.)
     *
     * <p>
     *  返回一个空列表(不可变)。此列表是可序列化的。
     * 
     *  <p>此示例说明获取空列表的类型安全方式：
     * <pre>
     *  List&lt; String&gt; s = Collections.emptyList();
     * </pre>
     * 
     *  @implNote此方法的实现不需要为每个调用创建单独的<tt> List </tt>对象。使用这种方法可能具有与使用相同命名字段相当的成本。 (与此方法不同,该字段不提供类型安全性。)
     * 
     * 
     * @param <T> type of elements, if there were any, in the list
     * @return an empty immutable list
     *
     * @see #EMPTY_LIST
     * @since 1.5
     */
    @SuppressWarnings("unchecked")
    public static final <T> List<T> emptyList() {
        return (List<T>) EMPTY_LIST;
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    private static class EmptyList<E>
        extends AbstractList<E>
        implements RandomAccess, Serializable {
        private static final long serialVersionUID = 8842843931221139166L;

        public Iterator<E> iterator() {
            return emptyIterator();
        }
        public ListIterator<E> listIterator() {
            return emptyListIterator();
        }

        public int size() {return 0;}
        public boolean isEmpty() {return true;}

        public boolean contains(Object obj) {return false;}
        public boolean containsAll(Collection<?> c) { return c.isEmpty(); }

        public Object[] toArray() { return new Object[0]; }

        public <T> T[] toArray(T[] a) {
            if (a.length > 0)
                a[0] = null;
            return a;
        }

        public E get(int index) {
            throw new IndexOutOfBoundsException("Index: "+index);
        }

        public boolean equals(Object o) {
            return (o instanceof List) && ((List<?>)o).isEmpty();
        }

        public int hashCode() { return 1; }

        @Override
        public boolean removeIf(Predicate<? super E> filter) {
            Objects.requireNonNull(filter);
            return false;
        }
        @Override
        public void replaceAll(UnaryOperator<E> operator) {
            Objects.requireNonNull(operator);
        }
        @Override
        public void sort(Comparator<? super E> c) {
        }

        // Override default methods in Collection
        @Override
        public void forEach(Consumer<? super E> action) {
            Objects.requireNonNull(action);
        }

        @Override
        public Spliterator<E> spliterator() { return Spliterators.emptySpliterator(); }

        // Preserves singleton property
        private Object readResolve() {
            return EMPTY_LIST;
        }
    }

    /**
     * The empty map (immutable).  This map is serializable.
     *
     * <p>
     *  空地图(不可变)。这个地图是可序列化的。
     * 
     * 
     * @see #emptyMap()
     * @since 1.3
     */
    @SuppressWarnings("rawtypes")
    public static final Map EMPTY_MAP = new EmptyMap<>();

    /**
     * Returns an empty map (immutable).  This map is serializable.
     *
     * <p>This example illustrates the type-safe way to obtain an empty map:
     * <pre>
     *     Map&lt;String, Date&gt; s = Collections.emptyMap();
     * </pre>
     * @implNote Implementations of this method need not create a separate
     * {@code Map} object for each call.  Using this method is likely to have
     * comparable cost to using the like-named field.  (Unlike this method, the
     * field does not provide type safety.)
     *
     * <p>
     * 返回一个空的地图(不可变)。这个地图是可序列化的。
     * 
     *  <p>此示例说明了获取空地图的类型安全方式：
     * <pre>
     *  地图&lt; String,Date&gt; s = Collections.emptyMap();
     * </pre>
     *  @implNote此方法的实现不需要为每个调用创建单独的{@code Map}对象。使用这种方法可能具有与使用相同命名字段相当的成本。 (与此方法不同,该字段不提供类型安全性。)
     * 
     * 
     * @param <K> the class of the map keys
     * @param <V> the class of the map values
     * @return an empty map
     * @see #EMPTY_MAP
     * @since 1.5
     */
    @SuppressWarnings("unchecked")
    public static final <K,V> Map<K,V> emptyMap() {
        return (Map<K,V>) EMPTY_MAP;
    }

    /**
     * Returns an empty sorted map (immutable).  This map is serializable.
     *
     * <p>This example illustrates the type-safe way to obtain an empty map:
     * <pre> {@code
     *     SortedMap<String, Date> s = Collections.emptySortedMap();
     * }</pre>
     *
     * @implNote Implementations of this method need not create a separate
     * {@code SortedMap} object for each call.
     *
     * <p>
     *  返回一个空的排序映射(不可变)。这个地图是可序列化的。
     * 
     *  <p>此示例说明了获取空映射的类型安全方法：<pre> {@code SortedMap <String,Date> s = Collections.emptySortedMap(); } </pre>
     * 。
     * 
     *  @implNote此方法的实现不需要为每个调用创建单独的{@code SortedMap}对象。
     * 
     * 
     * @param <K> the class of the map keys
     * @param <V> the class of the map values
     * @return an empty sorted map
     * @since 1.8
     */
    @SuppressWarnings("unchecked")
    public static final <K,V> SortedMap<K,V> emptySortedMap() {
        return (SortedMap<K,V>) UnmodifiableNavigableMap.EMPTY_NAVIGABLE_MAP;
    }

    /**
     * Returns an empty navigable map (immutable).  This map is serializable.
     *
     * <p>This example illustrates the type-safe way to obtain an empty map:
     * <pre> {@code
     *     NavigableMap<String, Date> s = Collections.emptyNavigableMap();
     * }</pre>
     *
     * @implNote Implementations of this method need not create a separate
     * {@code NavigableMap} object for each call.
     *
     * <p>
     *  返回一个空的可导航地图(不可变)。这个地图是可序列化的。
     * 
     *  <p>此示例说明了获取空映射的类型安全方法：<pre> {@code NavigableMap <String,Date> s = Collections.emptyNavigableMap(); }
     *  </pre>。
     * 
     *  @implNote此方法的实现不需要为每个调用创建单独的{@code NavigableMap}对象。
     * 
     * 
     * @param <K> the class of the map keys
     * @param <V> the class of the map values
     * @return an empty navigable map
     * @since 1.8
     */
    @SuppressWarnings("unchecked")
    public static final <K,V> NavigableMap<K,V> emptyNavigableMap() {
        return (NavigableMap<K,V>) UnmodifiableNavigableMap.EMPTY_NAVIGABLE_MAP;
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    private static class EmptyMap<K,V>
        extends AbstractMap<K,V>
        implements Serializable
    {
        private static final long serialVersionUID = 6428348081105594320L;

        public int size()                          {return 0;}
        public boolean isEmpty()                   {return true;}
        public boolean containsKey(Object key)     {return false;}
        public boolean containsValue(Object value) {return false;}
        public V get(Object key)                   {return null;}
        public Set<K> keySet()                     {return emptySet();}
        public Collection<V> values()              {return emptySet();}
        public Set<Map.Entry<K,V>> entrySet()      {return emptySet();}

        public boolean equals(Object o) {
            return (o instanceof Map) && ((Map<?,?>)o).isEmpty();
        }

        public int hashCode()                      {return 0;}

        // Override default methods in Map
        @Override
        @SuppressWarnings("unchecked")
        public V getOrDefault(Object k, V defaultValue) {
            return defaultValue;
        }

        @Override
        public void forEach(BiConsumer<? super K, ? super V> action) {
            Objects.requireNonNull(action);
        }

        @Override
        public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
            Objects.requireNonNull(function);
        }

        @Override
        public V putIfAbsent(K key, V value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object key, Object value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(K key, V oldValue, V newValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V replace(K key, V value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V computeIfAbsent(K key,
                Function<? super K, ? extends V> mappingFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V computeIfPresent(K key,
                BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V compute(K key,
                BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V merge(K key, V value,
                BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
            throw new UnsupportedOperationException();
        }

        // Preserves singleton property
        private Object readResolve() {
            return EMPTY_MAP;
        }
    }

    // Singleton collections

    /**
     * Returns an immutable set containing only the specified object.
     * The returned set is serializable.
     *
     * <p>
     *  返回仅包含指定对象的不可变集。返回的集合是可序列化的。
     * 
     * 
     * @param  <T> the class of the objects in the set
     * @param o the sole object to be stored in the returned set.
     * @return an immutable set containing only the specified object.
     */
    public static <T> Set<T> singleton(T o) {
        return new SingletonSet<>(o);
    }

    static <E> Iterator<E> singletonIterator(final E e) {
        return new Iterator<E>() {
            private boolean hasNext = true;
            public boolean hasNext() {
                return hasNext;
            }
            public E next() {
                if (hasNext) {
                    hasNext = false;
                    return e;
                }
                throw new NoSuchElementException();
            }
            public void remove() {
                throw new UnsupportedOperationException();
            }
            @Override
            public void forEachRemaining(Consumer<? super E> action) {
                Objects.requireNonNull(action);
                if (hasNext) {
                    action.accept(e);
                    hasNext = false;
                }
            }
        };
    }

    /**
     * Creates a {@code Spliterator} with only the specified element
     *
     * <p>
     *  创建仅包含指定元素的{@code Spliterator}
     * 
     * 
     * @param <T> Type of elements
     * @return A singleton {@code Spliterator}
     */
    static <T> Spliterator<T> singletonSpliterator(final T element) {
        return new Spliterator<T>() {
            long est = 1;

            @Override
            public Spliterator<T> trySplit() {
                return null;
            }

            @Override
            public boolean tryAdvance(Consumer<? super T> consumer) {
                Objects.requireNonNull(consumer);
                if (est > 0) {
                    est--;
                    consumer.accept(element);
                    return true;
                }
                return false;
            }

            @Override
            public void forEachRemaining(Consumer<? super T> consumer) {
                tryAdvance(consumer);
            }

            @Override
            public long estimateSize() {
                return est;
            }

            @Override
            public int characteristics() {
                int value = (element != null) ? Spliterator.NONNULL : 0;

                return value | Spliterator.SIZED | Spliterator.SUBSIZED | Spliterator.IMMUTABLE |
                       Spliterator.DISTINCT | Spliterator.ORDERED;
            }
        };
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    private static class SingletonSet<E>
        extends AbstractSet<E>
        implements Serializable
    {
        private static final long serialVersionUID = 3193687207550431679L;

        private final E element;

        SingletonSet(E e) {element = e;}

        public Iterator<E> iterator() {
            return singletonIterator(element);
        }

        public int size() {return 1;}

        public boolean contains(Object o) {return eq(o, element);}

        // Override default methods for Collection
        @Override
        public void forEach(Consumer<? super E> action) {
            action.accept(element);
        }
        @Override
        public Spliterator<E> spliterator() {
            return singletonSpliterator(element);
        }
        @Override
        public boolean removeIf(Predicate<? super E> filter) {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Returns an immutable list containing only the specified object.
     * The returned list is serializable.
     *
     * <p>
     *  返回仅包含指定对象的不可变列表。返回的列表是可序列化的。
     * 
     * 
     * @param  <T> the class of the objects in the list
     * @param o the sole object to be stored in the returned list.
     * @return an immutable list containing only the specified object.
     * @since 1.3
     */
    public static <T> List<T> singletonList(T o) {
        return new SingletonList<>(o);
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    private static class SingletonList<E>
        extends AbstractList<E>
        implements RandomAccess, Serializable {

        private static final long serialVersionUID = 3093736618740652951L;

        private final E element;

        SingletonList(E obj)                {element = obj;}

        public Iterator<E> iterator() {
            return singletonIterator(element);
        }

        public int size()                   {return 1;}

        public boolean contains(Object obj) {return eq(obj, element);}

        public E get(int index) {
            if (index != 0)
              throw new IndexOutOfBoundsException("Index: "+index+", Size: 1");
            return element;
        }

        // Override default methods for Collection
        @Override
        public void forEach(Consumer<? super E> action) {
            action.accept(element);
        }
        @Override
        public boolean removeIf(Predicate<? super E> filter) {
            throw new UnsupportedOperationException();
        }
        @Override
        public void replaceAll(UnaryOperator<E> operator) {
            throw new UnsupportedOperationException();
        }
        @Override
        public void sort(Comparator<? super E> c) {
        }
        @Override
        public Spliterator<E> spliterator() {
            return singletonSpliterator(element);
        }
    }

    /**
     * Returns an immutable map, mapping only the specified key to the
     * specified value.  The returned map is serializable.
     *
     * <p>
     *  返回不可变的映射,只将指定的键映射到指定的值。返回的地图是可序列化的。
     * 
     * 
     * @param <K> the class of the map keys
     * @param <V> the class of the map values
     * @param key the sole key to be stored in the returned map.
     * @param value the value to which the returned map maps <tt>key</tt>.
     * @return an immutable map containing only the specified key-value
     *         mapping.
     * @since 1.3
     */
    public static <K,V> Map<K,V> singletonMap(K key, V value) {
        return new SingletonMap<>(key, value);
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    private static class SingletonMap<K,V>
          extends AbstractMap<K,V>
          implements Serializable {
        private static final long serialVersionUID = -6979724477215052911L;

        private final K k;
        private final V v;

        SingletonMap(K key, V value) {
            k = key;
            v = value;
        }

        public int size()                                           {return 1;}
        public boolean isEmpty()                                {return false;}
        public boolean containsKey(Object key)             {return eq(key, k);}
        public boolean containsValue(Object value)       {return eq(value, v);}
        public V get(Object key)              {return (eq(key, k) ? v : null);}

        private transient Set<K> keySet;
        private transient Set<Map.Entry<K,V>> entrySet;
        private transient Collection<V> values;

        public Set<K> keySet() {
            if (keySet==null)
                keySet = singleton(k);
            return keySet;
        }

        public Set<Map.Entry<K,V>> entrySet() {
            if (entrySet==null)
                entrySet = Collections.<Map.Entry<K,V>>singleton(
                    new SimpleImmutableEntry<>(k, v));
            return entrySet;
        }

        public Collection<V> values() {
            if (values==null)
                values = singleton(v);
            return values;
        }

        // Override default methods in Map
        @Override
        public V getOrDefault(Object key, V defaultValue) {
            return eq(key, k) ? v : defaultValue;
        }

        @Override
        public void forEach(BiConsumer<? super K, ? super V> action) {
            action.accept(k, v);
        }

        @Override
        public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V putIfAbsent(K key, V value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object key, Object value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(K key, V oldValue, V newValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V replace(K key, V value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V computeIfAbsent(K key,
                Function<? super K, ? extends V> mappingFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V computeIfPresent(K key,
                BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V compute(K key,
                BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V merge(K key, V value,
                BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
            throw new UnsupportedOperationException();
        }
    }

    // Miscellaneous

    /**
     * Returns an immutable list consisting of <tt>n</tt> copies of the
     * specified object.  The newly allocated data object is tiny (it contains
     * a single reference to the data object).  This method is useful in
     * combination with the <tt>List.addAll</tt> method to grow lists.
     * The returned list is serializable.
     *
     * <p>
     * 返回由指定对象的<tt> n </tt>副本组成的不可变列表。新分配的数据对象很小(它包含对数据对象的单个引用)。此方法与<tt> List.addAll </tt>方法结合使用可以增长列表。
     * 返回的列表是可序列化的。
     * 
     * 
     * @param  <T> the class of the object to copy and of the objects
     *         in the returned list.
     * @param  n the number of elements in the returned list.
     * @param  o the element to appear repeatedly in the returned list.
     * @return an immutable list consisting of <tt>n</tt> copies of the
     *         specified object.
     * @throws IllegalArgumentException if {@code n < 0}
     * @see    List#addAll(Collection)
     * @see    List#addAll(int, Collection)
     */
    public static <T> List<T> nCopies(int n, T o) {
        if (n < 0)
            throw new IllegalArgumentException("List length = " + n);
        return new CopiesList<>(n, o);
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    private static class CopiesList<E>
        extends AbstractList<E>
        implements RandomAccess, Serializable
    {
        private static final long serialVersionUID = 2739099268398711800L;

        final int n;
        final E element;

        CopiesList(int n, E e) {
            assert n >= 0;
            this.n = n;
            element = e;
        }

        public int size() {
            return n;
        }

        public boolean contains(Object obj) {
            return n != 0 && eq(obj, element);
        }

        public int indexOf(Object o) {
            return contains(o) ? 0 : -1;
        }

        public int lastIndexOf(Object o) {
            return contains(o) ? n - 1 : -1;
        }

        public E get(int index) {
            if (index < 0 || index >= n)
                throw new IndexOutOfBoundsException("Index: "+index+
                                                    ", Size: "+n);
            return element;
        }

        public Object[] toArray() {
            final Object[] a = new Object[n];
            if (element != null)
                Arrays.fill(a, 0, n, element);
            return a;
        }

        @SuppressWarnings("unchecked")
        public <T> T[] toArray(T[] a) {
            final int n = this.n;
            if (a.length < n) {
                a = (T[])java.lang.reflect.Array
                    .newInstance(a.getClass().getComponentType(), n);
                if (element != null)
                    Arrays.fill(a, 0, n, element);
            } else {
                Arrays.fill(a, 0, n, element);
                if (a.length > n)
                    a[n] = null;
            }
            return a;
        }

        public List<E> subList(int fromIndex, int toIndex) {
            if (fromIndex < 0)
                throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
            if (toIndex > n)
                throw new IndexOutOfBoundsException("toIndex = " + toIndex);
            if (fromIndex > toIndex)
                throw new IllegalArgumentException("fromIndex(" + fromIndex +
                                                   ") > toIndex(" + toIndex + ")");
            return new CopiesList<>(toIndex - fromIndex, element);
        }

        // Override default methods in Collection
        @Override
        public Stream<E> stream() {
            return IntStream.range(0, n).mapToObj(i -> element);
        }

        @Override
        public Stream<E> parallelStream() {
            return IntStream.range(0, n).parallel().mapToObj(i -> element);
        }

        @Override
        public Spliterator<E> spliterator() {
            return stream().spliterator();
        }
    }

    /**
     * Returns a comparator that imposes the reverse of the <em>natural
     * ordering</em> on a collection of objects that implement the
     * {@code Comparable} interface.  (The natural ordering is the ordering
     * imposed by the objects' own {@code compareTo} method.)  This enables a
     * simple idiom for sorting (or maintaining) collections (or arrays) of
     * objects that implement the {@code Comparable} interface in
     * reverse-natural-order.  For example, suppose {@code a} is an array of
     * strings. Then: <pre>
     *          Arrays.sort(a, Collections.reverseOrder());
     * </pre> sorts the array in reverse-lexicographic (alphabetical) order.<p>
     *
     * The returned comparator is serializable.
     *
     * <p>
     *  返回在实现{@code Comparable}接口的对象集合上施加与自然排序</em>相反的比较器。 (自然排序是由对象自己的{@code compareTo}方法强加的排序。
     * )这使得一个简单的成语可以排序(或维护)实现{@code Comparable}接口的对象的集合(或数组) - 自然阶。例如,假设{@code a}是一个字符串数组。
     * 然后：<pre> Arrays.sort(a,Collections.reverseOrder()); </pre>按字母顺序排列数组。<p>。
     * 
     *  返回的比较器是可序列化的。
     * 
     * 
     * @param  <T> the class of the objects compared by the comparator
     * @return A comparator that imposes the reverse of the <i>natural
     *         ordering</i> on a collection of objects that implement
     *         the <tt>Comparable</tt> interface.
     * @see Comparable
     */
    @SuppressWarnings("unchecked")
    public static <T> Comparator<T> reverseOrder() {
        return (Comparator<T>) ReverseComparator.REVERSE_ORDER;
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    private static class ReverseComparator
        implements Comparator<Comparable<Object>>, Serializable {

        private static final long serialVersionUID = 7207038068494060240L;

        static final ReverseComparator REVERSE_ORDER
            = new ReverseComparator();

        public int compare(Comparable<Object> c1, Comparable<Object> c2) {
            return c2.compareTo(c1);
        }

        private Object readResolve() { return Collections.reverseOrder(); }

        @Override
        public Comparator<Comparable<Object>> reversed() {
            return Comparator.naturalOrder();
        }
    }

    /**
     * Returns a comparator that imposes the reverse ordering of the specified
     * comparator.  If the specified comparator is {@code null}, this method is
     * equivalent to {@link #reverseOrder()} (in other words, it returns a
     * comparator that imposes the reverse of the <em>natural ordering</em> on
     * a collection of objects that implement the Comparable interface).
     *
     * <p>The returned comparator is serializable (assuming the specified
     * comparator is also serializable or {@code null}).
     *
     * <p>
     *  返回施加指定比较器的相反顺序的比较器。
     * 如果指定的比较器是{@code null},这个方法等效于{@link #reverseOrder()}(换句话说,它返回一个比较器,它强加了<em>自然排序</em>实现Comparable接口的对象
     * 集合)。
     *  返回施加指定比较器的相反顺序的比较器。
     * 
     *  <p>返回的比较器是可序列化的(假设指定的比较器也是可序列化的或{@code null})。
     * 
     * 
     * @param <T> the class of the objects compared by the comparator
     * @param cmp a comparator who's ordering is to be reversed by the returned
     * comparator or {@code null}
     * @return A comparator that imposes the reverse ordering of the
     *         specified comparator.
     * @since 1.5
     */
    public static <T> Comparator<T> reverseOrder(Comparator<T> cmp) {
        if (cmp == null)
            return reverseOrder();

        if (cmp instanceof ReverseComparator2)
            return ((ReverseComparator2<T>)cmp).cmp;

        return new ReverseComparator2<>(cmp);
    }

    /**
    /* <p>
    /* 
     * @serial include
     */
    private static class ReverseComparator2<T> implements Comparator<T>,
        Serializable
    {
        private static final long serialVersionUID = 4374092139857L;

        /**
         * The comparator specified in the static factory.  This will never
         * be null, as the static factory returns a ReverseComparator
         * instance if its argument is null.
         *
         * <p>
         * 比较器在静态工厂中指定。这将永远不为null,因为静态工厂返回一个ReverseComparator实例,如果其参数为null。
         * 
         * 
         * @serial
         */
        final Comparator<T> cmp;

        ReverseComparator2(Comparator<T> cmp) {
            assert cmp != null;
            this.cmp = cmp;
        }

        public int compare(T t1, T t2) {
            return cmp.compare(t2, t1);
        }

        public boolean equals(Object o) {
            return (o == this) ||
                (o instanceof ReverseComparator2 &&
                 cmp.equals(((ReverseComparator2)o).cmp));
        }

        public int hashCode() {
            return cmp.hashCode() ^ Integer.MIN_VALUE;
        }

        @Override
        public Comparator<T> reversed() {
            return cmp;
        }
    }

    /**
     * Returns an enumeration over the specified collection.  This provides
     * interoperability with legacy APIs that require an enumeration
     * as input.
     *
     * <p>
     *  返回指定集合的​​枚举。这提供了与需要枚举作为输入的传统API的互操作性。
     * 
     * 
     * @param  <T> the class of the objects in the collection
     * @param c the collection for which an enumeration is to be returned.
     * @return an enumeration over the specified collection.
     * @see Enumeration
     */
    public static <T> Enumeration<T> enumeration(final Collection<T> c) {
        return new Enumeration<T>() {
            private final Iterator<T> i = c.iterator();

            public boolean hasMoreElements() {
                return i.hasNext();
            }

            public T nextElement() {
                return i.next();
            }
        };
    }

    /**
     * Returns an array list containing the elements returned by the
     * specified enumeration in the order they are returned by the
     * enumeration.  This method provides interoperability between
     * legacy APIs that return enumerations and new APIs that require
     * collections.
     *
     * <p>
     *  返回包含指定枚举返回的元素的数组列表,按照枚举返回的顺序。此方法提供传回的API之间的互操作性,返回枚举和需要集合的新API。
     * 
     * 
     * @param <T> the class of the objects returned by the enumeration
     * @param e enumeration providing elements for the returned
     *          array list
     * @return an array list containing the elements returned
     *         by the specified enumeration.
     * @since 1.4
     * @see Enumeration
     * @see ArrayList
     */
    public static <T> ArrayList<T> list(Enumeration<T> e) {
        ArrayList<T> l = new ArrayList<>();
        while (e.hasMoreElements())
            l.add(e.nextElement());
        return l;
    }

    /**
     * Returns true if the specified arguments are equal, or both null.
     *
     * NB: Do not replace with Object.equals until JDK-8015417 is resolved.
     * <p>
     *  如果指定的参数相等或返回null,则返回true。
     * 
     *  NB：直到JDK-8015417解决,不要替换Object.equals。
     * 
     */
    static boolean eq(Object o1, Object o2) {
        return o1==null ? o2==null : o1.equals(o2);
    }

    /**
     * Returns the number of elements in the specified collection equal to the
     * specified object.  More formally, returns the number of elements
     * <tt>e</tt> in the collection such that
     * <tt>(o == null ? e == null : o.equals(e))</tt>.
     *
     * <p>
     *  返回指定集合中等于指定对象的元素数。更正式地,返回集合中的元素数量<tt> e </tt>,使得<tt>(o == null?e == null：o.equals(e))</tt>。
     * 
     * 
     * @param c the collection in which to determine the frequency
     *     of <tt>o</tt>
     * @param o the object whose frequency is to be determined
     * @return the number of elements in {@code c} equal to {@code o}
     * @throws NullPointerException if <tt>c</tt> is null
     * @since 1.5
     */
    public static int frequency(Collection<?> c, Object o) {
        int result = 0;
        if (o == null) {
            for (Object e : c)
                if (e == null)
                    result++;
        } else {
            for (Object e : c)
                if (o.equals(e))
                    result++;
        }
        return result;
    }

    /**
     * Returns {@code true} if the two specified collections have no
     * elements in common.
     *
     * <p>Care must be exercised if this method is used on collections that
     * do not comply with the general contract for {@code Collection}.
     * Implementations may elect to iterate over either collection and test
     * for containment in the other collection (or to perform any equivalent
     * computation).  If either collection uses a nonstandard equality test
     * (as does a {@link SortedSet} whose ordering is not <em>compatible with
     * equals</em>, or the key set of an {@link IdentityHashMap}), both
     * collections must use the same nonstandard equality test, or the
     * result of this method is undefined.
     *
     * <p>Care must also be exercised when using collections that have
     * restrictions on the elements that they may contain. Collection
     * implementations are allowed to throw exceptions for any operation
     * involving elements they deem ineligible. For absolute safety the
     * specified collections should contain only elements which are
     * eligible elements for both collections.
     *
     * <p>Note that it is permissible to pass the same collection in both
     * parameters, in which case the method will return {@code true} if and
     * only if the collection is empty.
     *
     * <p>
     *  如果两个指定的集合没有共同的元素,则返回{@code true}。
     * 
     * <p>如果此方法用于不符合{@code Collection}的一般合同的集合,则必须小心。实现可以选择对任一集合进行迭代,并在另一集合中测试用于包含(或执行任何等效计算)。
     * 如果任一集合使用非标准等式测试(如{@link SortedSet},其排序不与equals </em>兼容,或{@link IdentityHashMap}的键集),则两个集合都必须使用相同的非标准等
     * 式测试,或者这个方法的结果是未定义的。
     * <p>如果此方法用于不符合{@code Collection}的一般合同的集合,则必须小心。实现可以选择对任一集合进行迭代,并在另一集合中测试用于包含(或执行任何等效计算)。
     * 
     *  <p>当使用对其可能包含的元素有限制的集合时,也必须小心。允许集合实现为涉及其认为不合格的元素的任何操作抛出异常。对于绝对安全,指定的集合应该只包含两个集合的合格元素。
     * 
     *  <p>请注意,允许在两个参数中传递相同的集合,在这种情况下,当且仅当集合为空时,该方法将返回{@code true}。
     * 
     * 
     * @param c1 a collection
     * @param c2 a collection
     * @return {@code true} if the two specified collections have no
     * elements in common.
     * @throws NullPointerException if either collection is {@code null}.
     * @throws NullPointerException if one collection contains a {@code null}
     * element and {@code null} is not an eligible element for the other collection.
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws ClassCastException if one collection contains an element that is
     * of a type which is ineligible for the other collection.
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @since 1.5
     */
    public static boolean disjoint(Collection<?> c1, Collection<?> c2) {
        // The collection to be used for contains(). Preference is given to
        // the collection who's contains() has lower O() complexity.
        Collection<?> contains = c2;
        // The collection to be iterated. If the collections' contains() impl
        // are of different O() complexity, the collection with slower
        // contains() will be used for iteration. For collections who's
        // contains() are of the same complexity then best performance is
        // achieved by iterating the smaller collection.
        Collection<?> iterate = c1;

        // Performance optimization cases. The heuristics:
        //   1. Generally iterate over c1.
        //   2. If c1 is a Set then iterate over c2.
        //   3. If either collection is empty then result is always true.
        //   4. Iterate over the smaller Collection.
        if (c1 instanceof Set) {
            // Use c1 for contains as a Set's contains() is expected to perform
            // better than O(N/2)
            iterate = c2;
            contains = c1;
        } else if (!(c2 instanceof Set)) {
            // Both are mere Collections. Iterate over smaller collection.
            // Example: If c1 contains 3 elements and c2 contains 50 elements and
            // assuming contains() requires ceiling(N/2) comparisons then
            // checking for all c1 elements in c2 would require 75 comparisons
            // (3 * ceiling(50/2)) vs. checking all c2 elements in c1 requiring
            // 100 comparisons (50 * ceiling(3/2)).
            int c1size = c1.size();
            int c2size = c2.size();
            if (c1size == 0 || c2size == 0) {
                // At least one collection is empty. Nothing will match.
                return true;
            }

            if (c1size > c2size) {
                iterate = c2;
                contains = c1;
            }
        }

        for (Object e : iterate) {
            if (contains.contains(e)) {
               // Found a common element. Collections are not disjoint.
                return false;
            }
        }

        // No common elements were found.
        return true;
    }

    /**
     * Adds all of the specified elements to the specified collection.
     * Elements to be added may be specified individually or as an array.
     * The behavior of this convenience method is identical to that of
     * <tt>c.addAll(Arrays.asList(elements))</tt>, but this method is likely
     * to run significantly faster under most implementations.
     *
     * <p>When elements are specified individually, this method provides a
     * convenient way to add a few elements to an existing collection:
     * <pre>
     *     Collections.addAll(flavors, "Peaches 'n Plutonium", "Rocky Racoon");
     * </pre>
     *
     * <p>
     *  将所有指定的元素添加到指定的集合。要添加的元素可以单独指定或作为数组指定。
     * 此方便方法的行为与<tt> c.addAll(Arrays.asList(elements))</tt>的行为相同,但是这种方法在大多数实现中可能运行得更快。
     * 
     * <p>当单独指定元素时,此方法提供了一种向现有集合添加几个元素的便捷方法：
     * <pre>
     *  Collections.addAll(flavors,"Peaches'n Plutonium","Rocky Racoon");
     * </pre>
     * 
     * 
     * @param  <T> the class of the elements to add and of the collection
     * @param c the collection into which <tt>elements</tt> are to be inserted
     * @param elements the elements to insert into <tt>c</tt>
     * @return <tt>true</tt> if the collection changed as a result of the call
     * @throws UnsupportedOperationException if <tt>c</tt> does not support
     *         the <tt>add</tt> operation
     * @throws NullPointerException if <tt>elements</tt> contains one or more
     *         null values and <tt>c</tt> does not permit null elements, or
     *         if <tt>c</tt> or <tt>elements</tt> are <tt>null</tt>
     * @throws IllegalArgumentException if some property of a value in
     *         <tt>elements</tt> prevents it from being added to <tt>c</tt>
     * @see Collection#addAll(Collection)
     * @since 1.5
     */
    @SafeVarargs
    public static <T> boolean addAll(Collection<? super T> c, T... elements) {
        boolean result = false;
        for (T element : elements)
            result |= c.add(element);
        return result;
    }

    /**
     * Returns a set backed by the specified map.  The resulting set displays
     * the same ordering, concurrency, and performance characteristics as the
     * backing map.  In essence, this factory method provides a {@link Set}
     * implementation corresponding to any {@link Map} implementation.  There
     * is no need to use this method on a {@link Map} implementation that
     * already has a corresponding {@link Set} implementation (such as {@link
     * HashMap} or {@link TreeMap}).
     *
     * <p>Each method invocation on the set returned by this method results in
     * exactly one method invocation on the backing map or its <tt>keySet</tt>
     * view, with one exception.  The <tt>addAll</tt> method is implemented
     * as a sequence of <tt>put</tt> invocations on the backing map.
     *
     * <p>The specified map must be empty at the time this method is invoked,
     * and should not be accessed directly after this method returns.  These
     * conditions are ensured if the map is created empty, passed directly
     * to this method, and no reference to the map is retained, as illustrated
     * in the following code fragment:
     * <pre>
     *    Set&lt;Object&gt; weakHashSet = Collections.newSetFromMap(
     *        new WeakHashMap&lt;Object, Boolean&gt;());
     * </pre>
     *
     * <p>
     *  返回由指定映射支持的集合。结果集显示与背景图相同的排序,并发和性能特性。实质上,这个工厂方法提供了一个{@link Set}实现,对应于任何{@link Map}实现。
     * 在已经具有相应的{@link Set}实现(例如{@link HashMap}或{@link TreeMap})的{@link Map}实现上不需要使用此方法。
     * 
     *  <p>对此方法返回的集合的每个方法调用都会在后台映射或其<tt> keySet </tt>视图上产生一个方法调用,但有一个例外。
     *  <tt> addAll </tt>方法是作为后台映射上的<tt> put </tt>调用序列实现的。
     * 
     *  <p>指定的映射在调用此方法时必须为空,并且在此方法返回后不应直接访问。如果映射创建为空,直接传递到此方法,并且不会保留对映射的引用,则会确保这些条件,如以下代码段所示：
     * <pre>
     *  Set&lt; Object&gt; weakHashSet = Collections.newSetFromMap(new WeakHashMap&lt; Object,Boolean&gt;())
     * ;。
     * </pre>
     * 
     * @param <E> the class of the map keys and of the objects in the
     *        returned set
     * @param map the backing map
     * @return the set backed by the map
     * @throws IllegalArgumentException if <tt>map</tt> is not empty
     * @since 1.6
     */
    public static <E> Set<E> newSetFromMap(Map<E, Boolean> map) {
        return new SetFromMap<>(map);
    }

    /**
    /* <p>
    /* 
    /* 
     * @serial include
     */
    private static class SetFromMap<E> extends AbstractSet<E>
        implements Set<E>, Serializable
    {
        private final Map<E, Boolean> m;  // The backing map
        private transient Set<E> s;       // Its keySet

        SetFromMap(Map<E, Boolean> map) {
            if (!map.isEmpty())
                throw new IllegalArgumentException("Map is non-empty");
            m = map;
            s = map.keySet();
        }

        public void clear()               {        m.clear(); }
        public int size()                 { return m.size(); }
        public boolean isEmpty()          { return m.isEmpty(); }
        public boolean contains(Object o) { return m.containsKey(o); }
        public boolean remove(Object o)   { return m.remove(o) != null; }
        public boolean add(E e) { return m.put(e, Boolean.TRUE) == null; }
        public Iterator<E> iterator()     { return s.iterator(); }
        public Object[] toArray()         { return s.toArray(); }
        public <T> T[] toArray(T[] a)     { return s.toArray(a); }
        public String toString()          { return s.toString(); }
        public int hashCode()             { return s.hashCode(); }
        public boolean equals(Object o)   { return o == this || s.equals(o); }
        public boolean containsAll(Collection<?> c) {return s.containsAll(c);}
        public boolean removeAll(Collection<?> c)   {return s.removeAll(c);}
        public boolean retainAll(Collection<?> c)   {return s.retainAll(c);}
        // addAll is the only inherited implementation

        // Override default methods in Collection
        @Override
        public void forEach(Consumer<? super E> action) {
            s.forEach(action);
        }
        @Override
        public boolean removeIf(Predicate<? super E> filter) {
            return s.removeIf(filter);
        }

        @Override
        public Spliterator<E> spliterator() {return s.spliterator();}
        @Override
        public Stream<E> stream()           {return s.stream();}
        @Override
        public Stream<E> parallelStream()   {return s.parallelStream();}

        private static final long serialVersionUID = 2454657854757543876L;

        private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException
        {
            stream.defaultReadObject();
            s = m.keySet();
        }
    }

    /**
     * Returns a view of a {@link Deque} as a Last-in-first-out (Lifo)
     * {@link Queue}. Method <tt>add</tt> is mapped to <tt>push</tt>,
     * <tt>remove</tt> is mapped to <tt>pop</tt> and so on. This
     * view can be useful when you would like to use a method
     * requiring a <tt>Queue</tt> but you need Lifo ordering.
     *
     * <p>Each method invocation on the queue returned by this method
     * results in exactly one method invocation on the backing deque, with
     * one exception.  The {@link Queue#addAll addAll} method is
     * implemented as a sequence of {@link Deque#addFirst addFirst}
     * invocations on the backing deque.
     *
     * <p>
     * 
     * @param  <T> the class of the objects in the deque
     * @param deque the deque
     * @return the queue
     * @since  1.6
     */
    public static <T> Queue<T> asLifoQueue(Deque<T> deque) {
        return new AsLIFOQueue<>(deque);
    }

    /**
    /* <p>
    /* 将{@link Deque}的视图返回为最后进先出(Lifo){@link Queue}。
    /* 方法<tt>添加</tt>映射到<tt> push </tt>,<tt>删除</tt>映射到<tt> pop </tt>等。
    /* 当您想要使用需要<tt>队列</tt>但需要Lifo排序的方法时,此视图很有用。
    /* 
    /* 
     * @serial include
     */
    static class AsLIFOQueue<E> extends AbstractQueue<E>
        implements Queue<E>, Serializable {
        private static final long serialVersionUID = 1802017725587941708L;
        private final Deque<E> q;
        AsLIFOQueue(Deque<E> q)           { this.q = q; }
        public boolean add(E e)           { q.addFirst(e); return true; }
        public boolean offer(E e)         { return q.offerFirst(e); }
        public E poll()                   { return q.pollFirst(); }
        public E remove()                 { return q.removeFirst(); }
        public E peek()                   { return q.peekFirst(); }
        public E element()                { return q.getFirst(); }
        public void clear()               {        q.clear(); }
        public int size()                 { return q.size(); }
        public boolean isEmpty()          { return q.isEmpty(); }
        public boolean contains(Object o) { return q.contains(o); }
        public boolean remove(Object o)   { return q.remove(o); }
        public Iterator<E> iterator()     { return q.iterator(); }
        public Object[] toArray()         { return q.toArray(); }
        public <T> T[] toArray(T[] a)     { return q.toArray(a); }
        public String toString()          { return q.toString(); }
        public boolean containsAll(Collection<?> c) {return q.containsAll(c);}
        public boolean removeAll(Collection<?> c)   {return q.removeAll(c);}
        public boolean retainAll(Collection<?> c)   {return q.retainAll(c);}
        // We use inherited addAll; forwarding addAll would be wrong

        // Override default methods in Collection
        @Override
        public void forEach(Consumer<? super E> action) {q.forEach(action);}
        @Override
        public boolean removeIf(Predicate<? super E> filter) {
            return q.removeIf(filter);
        }
        @Override
        public Spliterator<E> spliterator() {return q.spliterator();}
        @Override
        public Stream<E> stream()           {return q.stream();}
        @Override
        public Stream<E> parallelStream()   {return q.parallelStream();}
    }
}
