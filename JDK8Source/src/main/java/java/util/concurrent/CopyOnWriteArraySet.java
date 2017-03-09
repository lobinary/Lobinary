/***** Lobxxx Translate Finished ******/
/*
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

/*
 *
 *
 *
 *
 *
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 * <p>
 *  由Doug Lea在JCP JSR-166专家组成员的帮助下撰写,并发布到公共领域,如http://creativecommons.org/publicdomain/zero/1.0/
 * 
 */

package java.util.concurrent;
import java.util.Collection;
import java.util.Set;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Predicate;
import java.util.function.Consumer;

/**
 * A {@link java.util.Set} that uses an internal {@link CopyOnWriteArrayList}
 * for all of its operations.  Thus, it shares the same basic properties:
 * <ul>
 *  <li>It is best suited for applications in which set sizes generally
 *       stay small, read-only operations
 *       vastly outnumber mutative operations, and you need
 *       to prevent interference among threads during traversal.
 *  <li>It is thread-safe.
 *  <li>Mutative operations ({@code add}, {@code set}, {@code remove}, etc.)
 *      are expensive since they usually entail copying the entire underlying
 *      array.
 *  <li>Iterators do not support the mutative {@code remove} operation.
 *  <li>Traversal via iterators is fast and cannot encounter
 *      interference from other threads. Iterators rely on
 *      unchanging snapshots of the array at the time the iterators were
 *      constructed.
 * </ul>
 *
 * <p><b>Sample Usage.</b> The following code sketch uses a
 * copy-on-write set to maintain a set of Handler objects that
 * perform some action upon state updates.
 *
 *  <pre> {@code
 * class Handler { void handle(); ... }
 *
 * class X {
 *   private final CopyOnWriteArraySet<Handler> handlers
 *     = new CopyOnWriteArraySet<Handler>();
 *   public void addHandler(Handler h) { handlers.add(h); }
 *
 *   private long internalState;
 *   private synchronized void changeState() { internalState = ...; }
 *
 *   public void update() {
 *     changeState();
 *     for (Handler handler : handlers)
 *       handler.handle();
 *   }
 * }}</pre>
 *
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * <p>
 *  一个{@link java.util.Set},它对其所有操作使用内部{@link CopyOnWriteArrayList}。因此,它具有相同的基本性质：
 * <ul>
 *  <li>它最适合于集合大小通常保持较小的应用程序,只读操作大大超过突变操作,并且您需要在遍历期间防止线程之间的干扰。 <li>它是线程安全的。
 *  <li>互斥操作({@code add},{@code set},{@code remove}等)很贵,因为它们通常需要复制整个底层数组。 <li>迭代器不支持突变{@code remove}操作。
 *  <li>遍历迭代器速度快,不会遇到其他线程的干扰。迭代器依赖于迭代器构造时阵列的不变快照。
 * </ul>
 * 
 *  <p> <b>示例用法</b>。以下代码草图使用写时复制集来维护一组处理程序对象,这些对象在状态更新时执行某些操作。
 * 
 *  <pre> {@code class Handler {void handle(); ...}
 * 
 *  class X {private final CopyOnWriteArraySet <Handler> handlers = new CopyOnWriteArraySet <Handler>(); public void addHandler(Handler h){handlers.add(h); }
 * }。
 * 
 *  private long internalState; private synchronized void changeState(){internalState = ...; }}
 * 
 * public void update(){changeState(); for(Handler handler：handlers)handler.handle(); }}} </pre>
 * 
 *  <p>此类是的成员
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 *  Java集合框架</a>。
 * 
 * 
 * @see CopyOnWriteArrayList
 * @since 1.5
 * @author Doug Lea
 * @param <E> the type of elements held in this collection
 */
public class CopyOnWriteArraySet<E> extends AbstractSet<E>
        implements java.io.Serializable {
    private static final long serialVersionUID = 5457747651344034263L;

    private final CopyOnWriteArrayList<E> al;

    /**
     * Creates an empty set.
     * <p>
     *  创建空集。
     * 
     */
    public CopyOnWriteArraySet() {
        al = new CopyOnWriteArrayList<E>();
    }

    /**
     * Creates a set containing all of the elements of the specified
     * collection.
     *
     * <p>
     *  创建包含指定集合的​​所有元素的集合。
     * 
     * 
     * @param c the collection of elements to initially contain
     * @throws NullPointerException if the specified collection is null
     */
    public CopyOnWriteArraySet(Collection<? extends E> c) {
        if (c.getClass() == CopyOnWriteArraySet.class) {
            @SuppressWarnings("unchecked") CopyOnWriteArraySet<E> cc =
                (CopyOnWriteArraySet<E>)c;
            al = new CopyOnWriteArrayList<E>(cc.al);
        }
        else {
            al = new CopyOnWriteArrayList<E>();
            al.addAllAbsent(c);
        }
    }

    /**
     * Returns the number of elements in this set.
     *
     * <p>
     *  返回此集合中的元素数。
     * 
     * 
     * @return the number of elements in this set
     */
    public int size() {
        return al.size();
    }

    /**
     * Returns {@code true} if this set contains no elements.
     *
     * <p>
     *  如果此集合不包含元素,则返回{@code true}。
     * 
     * 
     * @return {@code true} if this set contains no elements
     */
    public boolean isEmpty() {
        return al.isEmpty();
    }

    /**
     * Returns {@code true} if this set contains the specified element.
     * More formally, returns {@code true} if and only if this set
     * contains an element {@code e} such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
     *
     * <p>
     *  如果此集合包含指定的元素,则返回{@code true}。
     * 更正式地说,当且仅当这个集合包含一个元素{@code e},使得<tt>(o == null&nbsp;?&nbsp; e == null&nbsp;：&nbsp; o.equals ))</tt>。
     * 
     * 
     * @param o element whose presence in this set is to be tested
     * @return {@code true} if this set contains the specified element
     */
    public boolean contains(Object o) {
        return al.contains(o);
    }

    /**
     * Returns an array containing all of the elements in this set.
     * If this set makes any guarantees as to what order its elements
     * are returned by its iterator, this method must return the
     * elements in the same order.
     *
     * <p>The returned array will be "safe" in that no references to it
     * are maintained by this set.  (In other words, this method must
     * allocate a new array even if this set is backed by an array).
     * The caller is thus free to modify the returned array.
     *
     * <p>This method acts as bridge between array-based and collection-based
     * APIs.
     *
     * <p>
     *  返回一个包含此集合中所有元素的数组。如果这个集合对它的元素被它的迭代器返回的顺序作出任何保证,那么这个方法必须以相同的顺序返回这些元素。
     * 
     *  <p>返回的数组将是"安全的",因为没有对它的引用由此集合维护。 (换句话说,这个方法必须分配一个新数组,即使这个数组支持数组)。因此调用者可以自由地修改返回的数组。
     * 
     *  <p>此方法充当基于阵列和基于集合的API之间的桥梁。
     * 
     * 
     * @return an array containing all the elements in this set
     */
    public Object[] toArray() {
        return al.toArray();
    }

    /**
     * Returns an array containing all of the elements in this set; the
     * runtime type of the returned array is that of the specified array.
     * If the set fits in the specified array, it is returned therein.
     * Otherwise, a new array is allocated with the runtime type of the
     * specified array and the size of this set.
     *
     * <p>If this set fits in the specified array with room to spare
     * (i.e., the array has more elements than this set), the element in
     * the array immediately following the end of the set is set to
     * {@code null}.  (This is useful in determining the length of this
     * set <i>only</i> if the caller knows that this set does not contain
     * any null elements.)
     *
     * <p>If this set makes any guarantees as to what order its elements
     * are returned by its iterator, this method must return the elements
     * in the same order.
     *
     * <p>Like the {@link #toArray()} method, this method acts as bridge between
     * array-based and collection-based APIs.  Further, this method allows
     * precise control over the runtime type of the output array, and may,
     * under certain circumstances, be used to save allocation costs.
     *
     * <p>Suppose {@code x} is a set known to contain only strings.
     * The following code can be used to dump the set into a newly allocated
     * array of {@code String}:
     *
     *  <pre> {@code String[] y = x.toArray(new String[0]);}</pre>
     *
     * Note that {@code toArray(new Object[0])} is identical in function to
     * {@code toArray()}.
     *
     * <p>
     *  返回一个包含此集合中所有元素的数组;返回的数组的运行时类型是指定数组的运行时类型。如果集合适合指定的数组,则返回其中。否则,将使用指定数组的运行时类型和此集合的大小分配新数组。
     * 
     * <p>如果此集合适合具有剩余空间的指定阵列(即,数组具有比此集合更多的元素),则紧接集合结尾的数组中的元素将设置为{@code null}。
     *  (如果调用者知道此集合不包含任何空元素,则这在确定此集合的长度</i> </i>时非常有用。)。
     * 
     *  <p>如果此集合对其元素由其迭代器返回的顺序作出任何保证,则此方法必须以相同的顺序返回元素。
     * 
     *  <p>与{@link #toArray()}方法类似,此方法充当基于数组和基于集合的API之间的桥梁。此外,该方法允许对输出阵列的运行时类型的精确控制,并且在某些情况下可以用于节省分配成本。
     * 
     *  <p>假设{@code x}是一个已知只包含字符串的集合。以下代码可用于将集合转储到新分配的{@code String}数组中：
     * 
     *  <pre> {@code String [] y = x.toArray(new String [0]);} </pre>
     * 
     *  注意,{@code toArray(new Object [0])}在功能上与{@code toArray()}是相同的。
     * 
     * 
     * @param a the array into which the elements of this set are to be
     *        stored, if it is big enough; otherwise, a new array of the same
     *        runtime type is allocated for this purpose.
     * @return an array containing all the elements in this set
     * @throws ArrayStoreException if the runtime type of the specified array
     *         is not a supertype of the runtime type of every element in this
     *         set
     * @throws NullPointerException if the specified array is null
     */
    public <T> T[] toArray(T[] a) {
        return al.toArray(a);
    }

    /**
     * Removes all of the elements from this set.
     * The set will be empty after this call returns.
     * <p>
     *  删除此集合中的所有元素。此调用返回后,集合将为空。
     * 
     */
    public void clear() {
        al.clear();
    }

    /**
     * Removes the specified element from this set if it is present.
     * More formally, removes an element {@code e} such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>,
     * if this set contains such an element.  Returns {@code true} if
     * this set contained the element (or equivalently, if this set
     * changed as a result of the call).  (This set will not contain the
     * element once the call returns.)
     *
     * <p>
     * 从此集合中删除指定的元素(如果存在)。
     * 更正式地,删除元素{@code e},使得<tt>(o == null&nbsp;?&nbsp; e == null&nbsp;：&nbsp; o.equals(e))</tt>元素。
     * 如果此集合包含元素(或等效地,如果此集合作为调用的结果而更改),则返回{@code true}。 (这个集合在调用返回后不会包含元素。)。
     * 
     * 
     * @param o object to be removed from this set, if present
     * @return {@code true} if this set contained the specified element
     */
    public boolean remove(Object o) {
        return al.remove(o);
    }

    /**
     * Adds the specified element to this set if it is not already present.
     * More formally, adds the specified element {@code e} to this set if
     * the set contains no element {@code e2} such that
     * <tt>(e==null&nbsp;?&nbsp;e2==null&nbsp;:&nbsp;e.equals(e2))</tt>.
     * If this set already contains the element, the call leaves the set
     * unchanged and returns {@code false}.
     *
     * <p>
     *  如果指定的元素不存在,则将其添加到此集合。
     * 更正式地,如果集合不包含元素{@code e2},使得<tt>(e == null&nbsp;?&nbsp; e2 == null&nbsp;：&nbsp;等于(e2))</tt>。
     * 如果此集合已经包含元素,则调用保持集合不变,并返回{@code false}。
     * 
     * 
     * @param e element to be added to this set
     * @return {@code true} if this set did not already contain the specified
     *         element
     */
    public boolean add(E e) {
        return al.addIfAbsent(e);
    }

    /**
     * Returns {@code true} if this set contains all of the elements of the
     * specified collection.  If the specified collection is also a set, this
     * method returns {@code true} if it is a <i>subset</i> of this set.
     *
     * <p>
     *  如果此集合包含指定集合的​​所有元素,则返回{@code true}。如果指定的集合也是集合,则此方法会返回{@code true}(如果它是此集合的<i>子集</i>)。
     * 
     * 
     * @param  c collection to be checked for containment in this set
     * @return {@code true} if this set contains all of the elements of the
     *         specified collection
     * @throws NullPointerException if the specified collection is null
     * @see #contains(Object)
     */
    public boolean containsAll(Collection<?> c) {
        return al.containsAll(c);
    }

    /**
     * Adds all of the elements in the specified collection to this set if
     * they're not already present.  If the specified collection is also a
     * set, the {@code addAll} operation effectively modifies this set so
     * that its value is the <i>union</i> of the two sets.  The behavior of
     * this operation is undefined if the specified collection is modified
     * while the operation is in progress.
     *
     * <p>
     *  将指定集合中的所有元素添加到此集合(如果它们尚未存在)。如果指定的集合也是一个集合,{@code addAll}操作有效地修改此集合,以使其值为两个集合的<i> union </i>。
     * 如果在操作正在进行时修改指定的集合,则此操作的行为是未定义的。
     * 
     * 
     * @param  c collection containing elements to be added to this set
     * @return {@code true} if this set changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     * @see #add(Object)
     */
    public boolean addAll(Collection<? extends E> c) {
        return al.addAllAbsent(c) > 0;
    }

    /**
     * Removes from this set all of its elements that are contained in the
     * specified collection.  If the specified collection is also a set,
     * this operation effectively modifies this set so that its value is the
     * <i>asymmetric set difference</i> of the two sets.
     *
     * <p>
     * 从此集合中删除包含在指定集合中的所有元素。如果指定的集合也是集合,则此操作有效地修改该集合,使得其值是两个集合的<i>非对称集合差异</i>。
     * 
     * 
     * @param  c collection containing elements to be removed from this set
     * @return {@code true} if this set changed as a result of the call
     * @throws ClassCastException if the class of an element of this set
     *         is incompatible with the specified collection (optional)
     * @throws NullPointerException if this set contains a null element and the
     *         specified collection does not permit null elements (optional),
     *         or if the specified collection is null
     * @see #remove(Object)
     */
    public boolean removeAll(Collection<?> c) {
        return al.removeAll(c);
    }

    /**
     * Retains only the elements in this set that are contained in the
     * specified collection.  In other words, removes from this set all of
     * its elements that are not contained in the specified collection.  If
     * the specified collection is also a set, this operation effectively
     * modifies this set so that its value is the <i>intersection</i> of the
     * two sets.
     *
     * <p>
     *  仅保留此集合中包含在指定集合中的元素。换句话说,从此集合中删除未包含在指定集合中的所有元素。如果指定的集合也是一个集合,则此操作有效地修改此集合,以使其值为两个集合的<i>交集</i>。
     * 
     * 
     * @param  c collection containing elements to be retained in this set
     * @return {@code true} if this set changed as a result of the call
     * @throws ClassCastException if the class of an element of this set
     *         is incompatible with the specified collection (optional)
     * @throws NullPointerException if this set contains a null element and the
     *         specified collection does not permit null elements (optional),
     *         or if the specified collection is null
     * @see #remove(Object)
     */
    public boolean retainAll(Collection<?> c) {
        return al.retainAll(c);
    }

    /**
     * Returns an iterator over the elements contained in this set
     * in the order in which these elements were added.
     *
     * <p>The returned iterator provides a snapshot of the state of the set
     * when the iterator was constructed. No synchronization is needed while
     * traversing the iterator. The iterator does <em>NOT</em> support the
     * {@code remove} method.
     *
     * <p>
     *  按照添加这些元素的顺序,返回此集合中包含的元素的迭代器。
     * 
     *  <p>返回的迭代器提供了迭代器构造时集合的状态的快照。遍历迭代器时不需要同步。迭代器会<em>不</em>支持{@code remove}方法。
     * 
     * 
     * @return an iterator over the elements in this set
     */
    public Iterator<E> iterator() {
        return al.iterator();
    }

    /**
     * Compares the specified object with this set for equality.
     * Returns {@code true} if the specified object is the same object
     * as this object, or if it is also a {@link Set} and the elements
     * returned by an {@linkplain Set#iterator() iterator} over the
     * specified set are the same as the elements returned by an
     * iterator over this set.  More formally, the two iterators are
     * considered to return the same elements if they return the same
     * number of elements and for every element {@code e1} returned by
     * the iterator over the specified set, there is an element
     * {@code e2} returned by the iterator over this set such that
     * {@code (e1==null ? e2==null : e1.equals(e2))}.
     *
     * <p>
     * 将指定的对象与此设置相比较以确保相等。
     * 如果指定的对象与此对象是相同的对象,或者如果它也是{@link Set},并且由{@linkplain Set#iterator()iterator}返回的元素超过指定的集合,则返回{@code true}
     * 与迭代器在此集合上返回的元素相同。
     * 将指定的对象与此设置相比较以确保相等。
     * 更正式地说,如果两个迭代器返回相同数量的元素,并且对于由迭代器在指定集合上返回的每个元素{@code e1},都会返回相同的元素,有一个元素{@code e2}该集合上的迭代器使得{@code(e1 == null?e2 == null：e1.equals(e2))}
     * 。
     * 将指定的对象与此设置相比较以确保相等。
     * 
     * 
     * @param o object to be compared for equality with this set
     * @return {@code true} if the specified object is equal to this set
     */
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Set))
            return false;
        Set<?> set = (Set<?>)(o);
        Iterator<?> it = set.iterator();

        // Uses O(n^2) algorithm that is only appropriate
        // for small sets, which CopyOnWriteArraySets should be.

        //  Use a single snapshot of underlying array
        Object[] elements = al.getArray();
        int len = elements.length;
        // Mark matched elements to avoid re-checking
        boolean[] matched = new boolean[len];
        int k = 0;
        outer: while (it.hasNext()) {
            if (++k > len)
                return false;
            Object x = it.next();
            for (int i = 0; i < len; ++i) {
                if (!matched[i] && eq(x, elements[i])) {
                    matched[i] = true;
                    continue outer;
                }
            }
            return false;
        }
        return k == len;
    }

    public boolean removeIf(Predicate<? super E> filter) {
        return al.removeIf(filter);
    }

    public void forEach(Consumer<? super E> action) {
        al.forEach(action);
    }

    /**
     * Returns a {@link Spliterator} over the elements in this set in the order
     * in which these elements were added.
     *
     * <p>The {@code Spliterator} reports {@link Spliterator#IMMUTABLE},
     * {@link Spliterator#DISTINCT}, {@link Spliterator#SIZED}, and
     * {@link Spliterator#SUBSIZED}.
     *
     * <p>The spliterator provides a snapshot of the state of the set
     * when the spliterator was constructed. No synchronization is needed while
     * operating on the spliterator.
     *
     * <p>
     *  按照添加这些元素的顺序,返回此组中元素的{@link Spliterator}。
     * 
     *  <p> {@code Spliterator}报告{@link Spliterator#IMMUTABLE},{@link Spliterator#DISTINCT},{@link Spliterator#SIZED}
     * 和{@link Spliterator#SUBSIZED}。
     * 
     * 
     * @return a {@code Spliterator} over the elements in this set
     * @since 1.8
     */
    public Spliterator<E> spliterator() {
        return Spliterators.spliterator
            (al.getArray(), Spliterator.IMMUTABLE | Spliterator.DISTINCT);
    }

    /**
     * Tests for equality, coping with nulls.
     * <p>
     *  <p>拆分器提供构建拆分器时集合状态的快照。在拼接器上操作时不需要同步。
     * 
     */
    private static boolean eq(Object o1, Object o2) {
        return (o1 == null) ? o2 == null : o1.equals(o2);
    }
}
