/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
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

/**
 * This class provides a skeletal implementation of the {@link List}
 * interface to minimize the effort required to implement this interface
 * backed by a "random access" data store (such as an array).  For sequential
 * access data (such as a linked list), {@link AbstractSequentialList} should
 * be used in preference to this class.
 *
 * <p>To implement an unmodifiable list, the programmer needs only to extend
 * this class and provide implementations for the {@link #get(int)} and
 * {@link List#size() size()} methods.
 *
 * <p>To implement a modifiable list, the programmer must additionally
 * override the {@link #set(int, Object) set(int, E)} method (which otherwise
 * throws an {@code UnsupportedOperationException}).  If the list is
 * variable-size the programmer must additionally override the
 * {@link #add(int, Object) add(int, E)} and {@link #remove(int)} methods.
 *
 * <p>The programmer should generally provide a void (no argument) and collection
 * constructor, as per the recommendation in the {@link Collection} interface
 * specification.
 *
 * <p>Unlike the other abstract collection implementations, the programmer does
 * <i>not</i> have to provide an iterator implementation; the iterator and
 * list iterator are implemented by this class, on top of the "random access"
 * methods:
 * {@link #get(int)},
 * {@link #set(int, Object) set(int, E)},
 * {@link #add(int, Object) add(int, E)} and
 * {@link #remove(int)}.
 *
 * <p>The documentation for each non-abstract method in this class describes its
 * implementation in detail.  Each of these methods may be overridden if the
 * collection being implemented admits a more efficient implementation.
 *
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * <p>
 *  此类提供了{@link List}接口的骨架实现,以最小化实现由"随机访问"数据存储(如阵列)支持的此接口所需的工作量。
 * 对于顺序存取数据(例如链接列表),应优先使用{@link AbstractSequentialList}此类。
 * 
 *  <p>要实现一个不可修改的列表,程序员只需要扩展这个类并为{@link #get(int)}和{@link List#size()size()}方法提供实现。
 * 
 *  <p>要实现可修改的列表,程序员必须另外覆盖{@link #set(int,Object)set(int,E)}方法(否则会抛出{@code UnsupportedOperationException}
 * )。
 * 如果列表是可变大小的,程序员必须另外覆盖{@link #add(int,Object)add(int,E)}和{@link #remove(int)}方法。
 * 
 *  <p>程序员通常应该按照{@link Collection}接口规范中的建议提供一个void(无参数)和集合构造函数。
 * 
 * <p>与其他抽象集合实现不同,程序员</i>不必提供迭代器实现;迭代器和列表迭代器由这个类实现,在"随机访问"方法之上：{@link #get(int)},{@link #set(int,Object)set(int,E)}
 * ,{ @link #add(int,Object)add(int,E)}和{@link #remove(int)}。
 * 
 *  <p>此类中每个非抽象方法的文档详细描述了它的实现。如果实现的集合允许更有效的实现,则这些方法中的每一个可以被覆盖。
 * 
 *  <p>此类是的成员
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 *  Java集合框架</a>。
 * 
 * 
 * @author  Josh Bloch
 * @author  Neal Gafter
 * @since 1.2
 */

public abstract class AbstractList<E> extends AbstractCollection<E> implements List<E> {
    /**
     * Sole constructor.  (For invocation by subclass constructors, typically
     * implicit.)
     * <p>
     *  唯一构造函数。 (对于子类构造函数的调用,通常是隐式的。)
     * 
     */
    protected AbstractList() {
    }

    /**
     * Appends the specified element to the end of this list (optional
     * operation).
     *
     * <p>Lists that support this operation may place limitations on what
     * elements may be added to this list.  In particular, some
     * lists will refuse to add null elements, and others will impose
     * restrictions on the type of elements that may be added.  List
     * classes should clearly specify in their documentation any restrictions
     * on what elements may be added.
     *
     * <p>This implementation calls {@code add(size(), e)}.
     *
     * <p>Note that this implementation throws an
     * {@code UnsupportedOperationException} unless
     * {@link #add(int, Object) add(int, E)} is overridden.
     *
     * <p>
     *  将指定的元素追加到此列表的末尾(可选操作)。
     * 
     *  <p>支持此操作的列表可能会对可能添加到此列表的元素设置限制。特别地,一些列表将拒绝添加空元素,并且其他列表将对可以添加的元素的类型施加限制。
     * 列表类应该在其文档中清楚地指定对可以添加什么元素的任何限制。
     * 
     *  <p>此实现调用{@code add(size(),e)}。
     * 
     *  <p>请注意,除非{@link #add(int,Object)add(int,E)}被覆盖,否则此实现会抛出{@code UnsupportedOperationException}。
     * 
     * 
     * @param e element to be appended to this list
     * @return {@code true} (as specified by {@link Collection#add})
     * @throws UnsupportedOperationException if the {@code add} operation
     *         is not supported by this list
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this list
     * @throws NullPointerException if the specified element is null and this
     *         list does not permit null elements
     * @throws IllegalArgumentException if some property of this element
     *         prevents it from being added to this list
     */
    public boolean add(E e) {
        add(size(), e);
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    abstract public E get(int index);

    /**
     * {@inheritDoc}
     *
     * <p>This implementation always throws an
     * {@code UnsupportedOperationException}.
     *
     * <p>
     *  {@inheritDoc}
     * 
     *  <p>此实现总是抛出{@code UnsupportedOperationException}。
     * 
     * 
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * @throws IllegalArgumentException      {@inheritDoc}
     * @throws IndexOutOfBoundsException     {@inheritDoc}
     */
    public E set(int index, E element) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation always throws an
     * {@code UnsupportedOperationException}.
     *
     * <p>
     *  {@inheritDoc}
     * 
     * <p>此实现总是抛出{@code UnsupportedOperationException}。
     * 
     * 
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * @throws IllegalArgumentException      {@inheritDoc}
     * @throws IndexOutOfBoundsException     {@inheritDoc}
     */
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation always throws an
     * {@code UnsupportedOperationException}.
     *
     * <p>
     *  {@inheritDoc}
     * 
     *  <p>此实现总是抛出{@code UnsupportedOperationException}。
     * 
     * 
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws IndexOutOfBoundsException     {@inheritDoc}
     */
    public E remove(int index) {
        throw new UnsupportedOperationException();
    }


    // Search Operations

    /**
     * {@inheritDoc}
     *
     * <p>This implementation first gets a list iterator (with
     * {@code listIterator()}).  Then, it iterates over the list until the
     * specified element is found or the end of the list is reached.
     *
     * <p>
     *  {@inheritDoc}
     * 
     *  <p>此实现首先获取列表迭代器(使用{@code listIterator()})。然后,它遍历列表,直到找到指定的元素或到达列表的末尾。
     * 
     * 
     * @throws ClassCastException   {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    public int indexOf(Object o) {
        ListIterator<E> it = listIterator();
        if (o==null) {
            while (it.hasNext())
                if (it.next()==null)
                    return it.previousIndex();
        } else {
            while (it.hasNext())
                if (o.equals(it.next()))
                    return it.previousIndex();
        }
        return -1;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation first gets a list iterator that points to the end
     * of the list (with {@code listIterator(size())}).  Then, it iterates
     * backwards over the list until the specified element is found, or the
     * beginning of the list is reached.
     *
     * <p>
     *  {@inheritDoc}
     * 
     *  <p>这个实现首先得到一个指向列表结尾的列表迭代器(使用{@code listIterator(size())})。然后,它在列表中向后迭代,直到找到指定的元素,或者到达列表的开头。
     * 
     * 
     * @throws ClassCastException   {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    public int lastIndexOf(Object o) {
        ListIterator<E> it = listIterator(size());
        if (o==null) {
            while (it.hasPrevious())
                if (it.previous()==null)
                    return it.nextIndex();
        } else {
            while (it.hasPrevious())
                if (o.equals(it.previous()))
                    return it.nextIndex();
        }
        return -1;
    }


    // Bulk Operations

    /**
     * Removes all of the elements from this list (optional operation).
     * The list will be empty after this call returns.
     *
     * <p>This implementation calls {@code removeRange(0, size())}.
     *
     * <p>Note that this implementation throws an
     * {@code UnsupportedOperationException} unless {@code remove(int
     * index)} or {@code removeRange(int fromIndex, int toIndex)} is
     * overridden.
     *
     * <p>
     *  从此列表中删除所有元素(可选操作)。此调用返回后,列表将为空。
     * 
     *  <p>此实现调用{@code removeRange(0,size())}。
     * 
     *  <p>请注意,除非{@code remove(int index)}或{@code removeRange(int fromIndex,int toIndex)}被覆盖,否则此实现会抛出{@code UnsupportedOperationException}
     * 。
     * 
     * 
     * @throws UnsupportedOperationException if the {@code clear} operation
     *         is not supported by this list
     */
    public void clear() {
        removeRange(0, size());
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation gets an iterator over the specified collection
     * and iterates over it, inserting the elements obtained from the
     * iterator into this list at the appropriate position, one at a time,
     * using {@code add(int, E)}.
     * Many implementations will override this method for efficiency.
     *
     * <p>Note that this implementation throws an
     * {@code UnsupportedOperationException} unless
     * {@link #add(int, Object) add(int, E)} is overridden.
     *
     * <p>
     *  {@inheritDoc}
     * 
     *  <p>这个实现在指定的集合上获取一个迭代器,并迭代它,使用{@code add(int,E)}将从迭代器获得的元素插入到合适位置的列表中,一次一个。许多实现将覆盖此方法以提高效率。
     * 
     *  <p>请注意,除非{@link #add(int,Object)add(int,E)}被覆盖,否则此实现会抛出{@code UnsupportedOperationException}。
     * 
     * 
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * @throws IllegalArgumentException      {@inheritDoc}
     * @throws IndexOutOfBoundsException     {@inheritDoc}
     */
    public boolean addAll(int index, Collection<? extends E> c) {
        rangeCheckForAdd(index);
        boolean modified = false;
        for (E e : c) {
            add(index++, e);
            modified = true;
        }
        return modified;
    }


    // Iterators

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     *
     * <p>This implementation returns a straightforward implementation of the
     * iterator interface, relying on the backing list's {@code size()},
     * {@code get(int)}, and {@code remove(int)} methods.
     *
     * <p>Note that the iterator returned by this method will throw an
     * {@link UnsupportedOperationException} in response to its
     * {@code remove} method unless the list's {@code remove(int)} method is
     * overridden.
     *
     * <p>This implementation can be made to throw runtime exceptions in the
     * face of concurrent modification, as described in the specification
     * for the (protected) {@link #modCount} field.
     *
     * <p>
     * 以正确的顺序返回此列表中的元素的迭代器。
     * 
     *  <p>此实现返回迭代器接口的直接实现,依赖于支持列表的{@code size()},{@code get(int)}和{@code remove(int)}方法。
     * 
     *  <p>请注意,除非列表的{@code remove(int)}方法被覆盖,否则此方法返回的迭代器将会响应其{@code remove}方法而抛出一个{@link UnsupportedOperationException}
     * 。
     * 
     *  <p>这种实现可以在面对并发修改时抛出运行时异常,如(protected){@link #modCount}字段的规范中所述。
     * 
     * 
     * @return an iterator over the elements in this list in proper sequence
     */
    public Iterator<E> iterator() {
        return new Itr();
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation returns {@code listIterator(0)}.
     *
     * <p>
     *  {@inheritDoc}
     * 
     *  <p>此实现返回{@code listIterator(0)}。
     * 
     * 
     * @see #listIterator(int)
     */
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation returns a straightforward implementation of the
     * {@code ListIterator} interface that extends the implementation of the
     * {@code Iterator} interface returned by the {@code iterator()} method.
     * The {@code ListIterator} implementation relies on the backing list's
     * {@code get(int)}, {@code set(int, E)}, {@code add(int, E)}
     * and {@code remove(int)} methods.
     *
     * <p>Note that the list iterator returned by this implementation will
     * throw an {@link UnsupportedOperationException} in response to its
     * {@code remove}, {@code set} and {@code add} methods unless the
     * list's {@code remove(int)}, {@code set(int, E)}, and
     * {@code add(int, E)} methods are overridden.
     *
     * <p>This implementation can be made to throw runtime exceptions in the
     * face of concurrent modification, as described in the specification for
     * the (protected) {@link #modCount} field.
     *
     * <p>
     *  {@inheritDoc}
     * 
     *  <p>此实现返回{@code ListIterator}接口的直接实现,该接口扩展了{@code iterator()}方法返回的{@code Iterator}接口的实现。
     *  {@code ListIterator}实现依赖于支持列表的{@code get(int)},{@code set(int,E)},{@code add(int,E)}和{@code remove }
     *  方法。
     *  <p>此实现返回{@code ListIterator}接口的直接实现,该接口扩展了{@code iterator()}方法返回的{@code Iterator}接口的实现。
     * 
     *  <p>请注意,此实现返回的列表迭代器将响应其{@code remove},{@code set}和{@code add}方法而抛出{@link UnsupportedOperationException}
     * ,除非列表的{@code remove int)},{@code set(int,E)}和{@code add(int,E)}方法。
     * 
     * <p>这种实现可以在面对并发修改时抛出运行时异常,如(protected){@link #modCount}字段的规范中所述。
     * 
     * 
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public ListIterator<E> listIterator(final int index) {
        rangeCheckForAdd(index);

        return new ListItr(index);
    }

    private class Itr implements Iterator<E> {
        /**
         * Index of element to be returned by subsequent call to next.
         * <p>
         *  将通过后续调用返回到下一个的元素的索引。
         * 
         */
        int cursor = 0;

        /**
         * Index of element returned by most recent call to next or
         * previous.  Reset to -1 if this element is deleted by a call
         * to remove.
         * <p>
         *  由最近一次调用next或previous返回的元素的索引。如果要删除的调用删除此元素,请重置为-1。
         * 
         */
        int lastRet = -1;

        /**
         * The modCount value that the iterator believes that the backing
         * List should have.  If this expectation is violated, the iterator
         * has detected concurrent modification.
         * <p>
         *  迭代器认为支持List应该具有的modCount值。如果违反此期望,迭代器已检测到并发修改。
         * 
         */
        int expectedModCount = modCount;

        public boolean hasNext() {
            return cursor != size();
        }

        public E next() {
            checkForComodification();
            try {
                int i = cursor;
                E next = get(i);
                lastRet = i;
                cursor = i + 1;
                return next;
            } catch (IndexOutOfBoundsException e) {
                checkForComodification();
                throw new NoSuchElementException();
            }
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                AbstractList.this.remove(lastRet);
                if (lastRet < cursor)
                    cursor--;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException e) {
                throw new ConcurrentModificationException();
            }
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    private class ListItr extends Itr implements ListIterator<E> {
        ListItr(int index) {
            cursor = index;
        }

        public boolean hasPrevious() {
            return cursor != 0;
        }

        public E previous() {
            checkForComodification();
            try {
                int i = cursor - 1;
                E previous = get(i);
                lastRet = cursor = i;
                return previous;
            } catch (IndexOutOfBoundsException e) {
                checkForComodification();
                throw new NoSuchElementException();
            }
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor-1;
        }

        public void set(E e) {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                AbstractList.this.set(lastRet, e);
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public void add(E e) {
            checkForComodification();

            try {
                int i = cursor;
                AbstractList.this.add(i, e);
                lastRet = -1;
                cursor = i + 1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation returns a list that subclasses
     * {@code AbstractList}.  The subclass stores, in private fields, the
     * offset of the subList within the backing list, the size of the subList
     * (which can change over its lifetime), and the expected
     * {@code modCount} value of the backing list.  There are two variants
     * of the subclass, one of which implements {@code RandomAccess}.
     * If this list implements {@code RandomAccess} the returned list will
     * be an instance of the subclass that implements {@code RandomAccess}.
     *
     * <p>The subclass's {@code set(int, E)}, {@code get(int)},
     * {@code add(int, E)}, {@code remove(int)}, {@code addAll(int,
     * Collection)} and {@code removeRange(int, int)} methods all
     * delegate to the corresponding methods on the backing abstract list,
     * after bounds-checking the index and adjusting for the offset.  The
     * {@code addAll(Collection c)} method merely returns {@code addAll(size,
     * c)}.
     *
     * <p>The {@code listIterator(int)} method returns a "wrapper object"
     * over a list iterator on the backing list, which is created with the
     * corresponding method on the backing list.  The {@code iterator} method
     * merely returns {@code listIterator()}, and the {@code size} method
     * merely returns the subclass's {@code size} field.
     *
     * <p>All methods first check to see if the actual {@code modCount} of
     * the backing list is equal to its expected value, and throw a
     * {@code ConcurrentModificationException} if it is not.
     *
     * <p>
     *  {@inheritDoc}
     * 
     *  <p>此实现返回一个子类{@code AbstractList}的列表。
     * 子类在私有字段中存储后备列表中的子列表的偏移量,子列表的大小(其可以在其生存期内改变)以及后备列表的预期的{@code modCount}值。
     * 有两个子类的变体,其中之一实现{@code RandomAccess}。如果此列表实现{@code RandomAccess},返回的列表将是实现{@code RandomAccess}的子类的实例。
     * 
     *  <p>子类的{@code set(int,E)},{@code get(int)},{@code add(int,E)},{@code remove(int)},{@code addAll int,Collection)}
     * 和{@code removeRange(int,int)}方法都委托给相应的方法在支持抽象列表上,在边界检查索引和调整偏移量。
     *  {@code addAll(Collection c)}方法只返回{@code addAll(size,c)}。
     * 
     * <p> {@code listIterator(int)}方法在支持列表上的列表迭代器上返回一个"包装器对象",它是使用相应的方法在支持列表上创建的。
     *  {@code iterator}方法只返回{@code listIterator()},而{@code size}方法只返回子类的{@code size}字段。
     * 
     *  <p>所有方法首先检查支持列表的实际{@code modCount}是否等于其预期值,如果没有,则抛出{@code ConcurrentModificationException}。
     * 
     * 
     * @throws IndexOutOfBoundsException if an endpoint index value is out of range
     *         {@code (fromIndex < 0 || toIndex > size)}
     * @throws IllegalArgumentException if the endpoint indices are out of order
     *         {@code (fromIndex > toIndex)}
     */
    public List<E> subList(int fromIndex, int toIndex) {
        return (this instanceof RandomAccess ?
                new RandomAccessSubList<>(this, fromIndex, toIndex) :
                new SubList<>(this, fromIndex, toIndex));
    }

    // Comparison and hashing

    /**
     * Compares the specified object with this list for equality.  Returns
     * {@code true} if and only if the specified object is also a list, both
     * lists have the same size, and all corresponding pairs of elements in
     * the two lists are <i>equal</i>.  (Two elements {@code e1} and
     * {@code e2} are <i>equal</i> if {@code (e1==null ? e2==null :
     * e1.equals(e2))}.)  In other words, two lists are defined to be
     * equal if they contain the same elements in the same order.<p>
     *
     * This implementation first checks if the specified object is this
     * list. If so, it returns {@code true}; if not, it checks if the
     * specified object is a list. If not, it returns {@code false}; if so,
     * it iterates over both lists, comparing corresponding pairs of elements.
     * If any comparison returns {@code false}, this method returns
     * {@code false}.  If either iterator runs out of elements before the
     * other it returns {@code false} (as the lists are of unequal length);
     * otherwise it returns {@code true} when the iterations complete.
     *
     * <p>
     *  将指定的对象与此列表进行比较以确保相等。当且仅当指定的对象也是一个列表,两个列表都具有相同的大小,并且两个列表中的所有相应的元素对都是<i>等于</i>时,返回{@code true}。
     *  (如果{@code(e1 == null?e2 == null：e1.equals(e2))},则{@code e1}和{@code e2}两个元素<i>如果两个列表包含相同顺序的相同元素,则两个列表
     * 被定义为相等。
     *  将指定的对象与此列表进行比较以确保相等。当且仅当指定的对象也是一个列表,两个列表都具有相同的大小,并且两个列表中的所有相应的元素对都是<i>等于</i>时,返回{@code true}。<p>。
     * 
     *  此实现首先检查指定的对象是否为此列表。如果是这样,它返回{@code true};如果不是,它检查指定的对象是否是列表。
     * 如果没有,它返回{@code false};如果是,它迭代两个列表,比较相应的元素对。如果任何比较返回{@code false},此方法返回{@code false}。
     * 如果任一迭代器在另一个迭代器之前运行元素,它返回{@code false}(因为列表的长度不等);否则当迭代完成时返回{@code true}。
     * 
     * 
     * @param o the object to be compared for equality with this list
     * @return {@code true} if the specified object is equal to this list
     */
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof List))
            return false;

        ListIterator<E> e1 = listIterator();
        ListIterator<?> e2 = ((List<?>) o).listIterator();
        while (e1.hasNext() && e2.hasNext()) {
            E o1 = e1.next();
            Object o2 = e2.next();
            if (!(o1==null ? o2==null : o1.equals(o2)))
                return false;
        }
        return !(e1.hasNext() || e2.hasNext());
    }

    /**
     * Returns the hash code value for this list.
     *
     * <p>This implementation uses exactly the code that is used to define the
     * list hash function in the documentation for the {@link List#hashCode}
     * method.
     *
     * <p>
     * 返回此列表的哈希码值。
     * 
     *  <p>此实现完全使用用于在{@link List#hashCode}方法的文档中定义列表哈希函数的代码。
     * 
     * 
     * @return the hash code value for this list
     */
    public int hashCode() {
        int hashCode = 1;
        for (E e : this)
            hashCode = 31*hashCode + (e==null ? 0 : e.hashCode());
        return hashCode;
    }

    /**
     * Removes from this list all of the elements whose index is between
     * {@code fromIndex}, inclusive, and {@code toIndex}, exclusive.
     * Shifts any succeeding elements to the left (reduces their index).
     * This call shortens the list by {@code (toIndex - fromIndex)} elements.
     * (If {@code toIndex==fromIndex}, this operation has no effect.)
     *
     * <p>This method is called by the {@code clear} operation on this list
     * and its subLists.  Overriding this method to take advantage of
     * the internals of the list implementation can <i>substantially</i>
     * improve the performance of the {@code clear} operation on this list
     * and its subLists.
     *
     * <p>This implementation gets a list iterator positioned before
     * {@code fromIndex}, and repeatedly calls {@code ListIterator.next}
     * followed by {@code ListIterator.remove} until the entire range has
     * been removed.  <b>Note: if {@code ListIterator.remove} requires linear
     * time, this implementation requires quadratic time.</b>
     *
     * <p>
     *  从此列表中删除其索引在{@code fromIndex}(包括)和{@code toIndex}(排除)之间的所有元素。将任何后续元素向左移(减少其索引)。
     * 此调用通过{@code(toIndex  -  fromIndex)}元素缩短列表。 (如果{@code toIndex == fromIndex},此操作没有效果。)。
     * 
     *  <p>此方法由对此列表及其子列表的{@code clear}操作调用。覆盖此方法以利用列表实现的内部功能可以基本上提高对此列表及其子列表的{@code clear}操作的性能。
     * 
     *  <p>此实现获得位于{@code fromIndex}之前的列表迭代器,并重复调用{@code ListIterator.next},然后再调用{@code ListIterator.remove},
     * 直到整个范围被删除。
     *  <b>注意：如果{@code ListIterator.remove}需要线性时间,此实现需要二次时间。</b>。
     * 
     * 
     * @param fromIndex index of first element to be removed
     * @param toIndex index after last element to be removed
     */
    protected void removeRange(int fromIndex, int toIndex) {
        ListIterator<E> it = listIterator(fromIndex);
        for (int i=0, n=toIndex-fromIndex; i<n; i++) {
            it.next();
            it.remove();
        }
    }

    /**
     * The number of times this list has been <i>structurally modified</i>.
     * Structural modifications are those that change the size of the
     * list, or otherwise perturb it in such a fashion that iterations in
     * progress may yield incorrect results.
     *
     * <p>This field is used by the iterator and list iterator implementation
     * returned by the {@code iterator} and {@code listIterator} methods.
     * If the value of this field changes unexpectedly, the iterator (or list
     * iterator) will throw a {@code ConcurrentModificationException} in
     * response to the {@code next}, {@code remove}, {@code previous},
     * {@code set} or {@code add} operations.  This provides
     * <i>fail-fast</i> behavior, rather than non-deterministic behavior in
     * the face of concurrent modification during iteration.
     *
     * <p><b>Use of this field by subclasses is optional.</b> If a subclass
     * wishes to provide fail-fast iterators (and list iterators), then it
     * merely has to increment this field in its {@code add(int, E)} and
     * {@code remove(int)} methods (and any other methods that it overrides
     * that result in structural modifications to the list).  A single call to
     * {@code add(int, E)} or {@code remove(int)} must add no more than
     * one to this field, or the iterators (and list iterators) will throw
     * bogus {@code ConcurrentModificationExceptions}.  If an implementation
     * does not wish to provide fail-fast iterators, this field may be
     * ignored.
     * <p>
     *  此列表已在<i>结构上修改的次数</i>。结构修改是那些改变列表大小或以其他方式扰乱它的方式,使得正在进行的迭代可能产生不正确的结果。
     * 
     * <p>此字段由{@code iterator}和{@code listIterator}方法返回的迭代器和列表迭代器实现使用。
     * 如果此字段的值意外更改,迭代器(或列表迭代器)将响应{@code next},{@code remove},{@code previous},{@code set }或{@code add}操作。
     * 这提供了<i> fail-fast </i>行为,而不是在迭代期间面临并发修改的非确定性行为。
     * 
     *  <p> <b>子类使用此字段是可选的。
     */
    protected transient int modCount = 0;

    private void rangeCheckForAdd(int index) {
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size();
    }
}

class SubList<E> extends AbstractList<E> {
    private final AbstractList<E> l;
    private final int offset;
    private int size;

    SubList(AbstractList<E> list, int fromIndex, int toIndex) {
        if (fromIndex < 0)
            throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
        if (toIndex > list.size())
            throw new IndexOutOfBoundsException("toIndex = " + toIndex);
        if (fromIndex > toIndex)
            throw new IllegalArgumentException("fromIndex(" + fromIndex +
                                               ") > toIndex(" + toIndex + ")");
        l = list;
        offset = fromIndex;
        size = toIndex - fromIndex;
        this.modCount = l.modCount;
    }

    public E set(int index, E element) {
        rangeCheck(index);
        checkForComodification();
        return l.set(index+offset, element);
    }

    public E get(int index) {
        rangeCheck(index);
        checkForComodification();
        return l.get(index+offset);
    }

    public int size() {
        checkForComodification();
        return size;
    }

    public void add(int index, E element) {
        rangeCheckForAdd(index);
        checkForComodification();
        l.add(index+offset, element);
        this.modCount = l.modCount;
        size++;
    }

    public E remove(int index) {
        rangeCheck(index);
        checkForComodification();
        E result = l.remove(index+offset);
        this.modCount = l.modCount;
        size--;
        return result;
    }

    protected void removeRange(int fromIndex, int toIndex) {
        checkForComodification();
        l.removeRange(fromIndex+offset, toIndex+offset);
        this.modCount = l.modCount;
        size -= (toIndex-fromIndex);
    }

    public boolean addAll(Collection<? extends E> c) {
        return addAll(size, c);
    }

    public boolean addAll(int index, Collection<? extends E> c) {
        rangeCheckForAdd(index);
        int cSize = c.size();
        if (cSize==0)
            return false;

        checkForComodification();
        l.addAll(offset+index, c);
        this.modCount = l.modCount;
        size += cSize;
        return true;
    }

    public Iterator<E> iterator() {
        return listIterator();
    }

    public ListIterator<E> listIterator(final int index) {
        checkForComodification();
        rangeCheckForAdd(index);

        return new ListIterator<E>() {
            private final ListIterator<E> i = l.listIterator(index+offset);

            public boolean hasNext() {
                return nextIndex() < size;
            }

            public E next() {
                if (hasNext())
                    return i.next();
                else
                    throw new NoSuchElementException();
            }

            public boolean hasPrevious() {
                return previousIndex() >= 0;
            }

            public E previous() {
                if (hasPrevious())
                    return i.previous();
                else
                    throw new NoSuchElementException();
            }

            public int nextIndex() {
                return i.nextIndex() - offset;
            }

            public int previousIndex() {
                return i.previousIndex() - offset;
            }

            public void remove() {
                i.remove();
                SubList.this.modCount = l.modCount;
                size--;
            }

            public void set(E e) {
                i.set(e);
            }

            public void add(E e) {
                i.add(e);
                SubList.this.modCount = l.modCount;
                size++;
            }
        };
    }

    public List<E> subList(int fromIndex, int toIndex) {
        return new SubList<>(this, fromIndex, toIndex);
    }

    private void rangeCheck(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private void rangeCheckForAdd(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }

    private void checkForComodification() {
        if (this.modCount != l.modCount)
            throw new ConcurrentModificationException();
    }
}

class RandomAccessSubList<E> extends SubList<E> implements RandomAccess {
    RandomAccessSubList(AbstractList<E> list, int fromIndex, int toIndex) {
        super(list, fromIndex, toIndex);
    }

    public List<E> subList(int fromIndex, int toIndex) {
        return new RandomAccessSubList<>(this, fromIndex, toIndex);
    }
}
