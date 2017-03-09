/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2012, Oracle and/or its affiliates. All rights reserved.
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
 * Private implementation class for EnumSet, for "jumbo" enum types
 * (i.e., those with more than 64 elements).
 *
 * <p>
 *  EnumSet的私有实现类,用于"jumbo"枚举类型(即,具有超过64个元素的类)。
 * 
 * 
 * @author Josh Bloch
 * @since 1.5
 * @serial exclude
 */
class JumboEnumSet<E extends Enum<E>> extends EnumSet<E> {
    private static final long serialVersionUID = 334349849919042784L;

    /**
     * Bit vector representation of this set.  The ith bit of the jth
     * element of this array represents the  presence of universe[64*j +i]
     * in this set.
     * <p>
     *  此集合的位向量表示。该数组的第j个元素的第i位表示该集合中存在宇宙[64 * j + i]。
     * 
     */
    private long elements[];

    // Redundant - maintained for performance
    private int size = 0;

    JumboEnumSet(Class<E>elementType, Enum<?>[] universe) {
        super(elementType, universe);
        elements = new long[(universe.length + 63) >>> 6];
    }

    void addRange(E from, E to) {
        int fromIndex = from.ordinal() >>> 6;
        int toIndex = to.ordinal() >>> 6;

        if (fromIndex == toIndex) {
            elements[fromIndex] = (-1L >>>  (from.ordinal() - to.ordinal() - 1))
                            << from.ordinal();
        } else {
            elements[fromIndex] = (-1L << from.ordinal());
            for (int i = fromIndex + 1; i < toIndex; i++)
                elements[i] = -1;
            elements[toIndex] = -1L >>> (63 - to.ordinal());
        }
        size = to.ordinal() - from.ordinal() + 1;
    }

    void addAll() {
        for (int i = 0; i < elements.length; i++)
            elements[i] = -1;
        elements[elements.length - 1] >>>= -universe.length;
        size = universe.length;
    }

    void complement() {
        for (int i = 0; i < elements.length; i++)
            elements[i] = ~elements[i];
        elements[elements.length - 1] &= (-1L >>> -universe.length);
        size = universe.length - size;
    }

    /**
     * Returns an iterator over the elements contained in this set.  The
     * iterator traverses the elements in their <i>natural order</i> (which is
     * the order in which the enum constants are declared). The returned
     * Iterator is a "weakly consistent" iterator that will never throw {@link
     * ConcurrentModificationException}.
     *
     * <p>
     *  在此集合中包含的元素上返回一个迭代器。迭代器以它们的<i>自然顺序</i>(这是枚举常量的声明顺序)遍历元素。
     * 返回的Iterator是一个"弱一致"的迭代器,永远不会抛出{@link ConcurrentModificationException}。
     * 
     * 
     * @return an iterator over the elements contained in this set
     */
    public Iterator<E> iterator() {
        return new EnumSetIterator<>();
    }

    private class EnumSetIterator<E extends Enum<E>> implements Iterator<E> {
        /**
         * A bit vector representing the elements in the current "word"
         * of the set not yet returned by this iterator.
         * <p>
         *  表示当前"字"中的元素尚未由此迭代器返回的位向量。
         * 
         */
        long unseen;

        /**
         * The index corresponding to unseen in the elements array.
         * <p>
         *  索引对应于elements数组中的notseen。
         * 
         */
        int unseenIndex = 0;

        /**
         * The bit representing the last element returned by this iterator
         * but not removed, or zero if no such element exists.
         * <p>
         *  表示此迭代器返回但未删除的最后一个元素的位,如果不存在此元素,则为零。
         * 
         */
        long lastReturned = 0;

        /**
         * The index corresponding to lastReturned in the elements array.
         * <p>
         *  在元素数组中对应于lastReturned的索引。
         * 
         */
        int lastReturnedIndex = 0;

        EnumSetIterator() {
            unseen = elements[0];
        }

        @Override
        public boolean hasNext() {
            while (unseen == 0 && unseenIndex < elements.length - 1)
                unseen = elements[++unseenIndex];
            return unseen != 0;
        }

        @Override
        @SuppressWarnings("unchecked")
        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            lastReturned = unseen & -unseen;
            lastReturnedIndex = unseenIndex;
            unseen -= lastReturned;
            return (E) universe[(lastReturnedIndex << 6)
                                + Long.numberOfTrailingZeros(lastReturned)];
        }

        @Override
        public void remove() {
            if (lastReturned == 0)
                throw new IllegalStateException();
            final long oldElements = elements[lastReturnedIndex];
            elements[lastReturnedIndex] &= ~lastReturned;
            if (oldElements != elements[lastReturnedIndex]) {
                size--;
            }
            lastReturned = 0;
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
        return size;
    }

    /**
     * Returns <tt>true</tt> if this set contains no elements.
     *
     * <p>
     *  如果此集合不包含元素,则返回<tt> true </tt>。
     * 
     * 
     * @return <tt>true</tt> if this set contains no elements
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns <tt>true</tt> if this set contains the specified element.
     *
     * <p>
     *  如果此集合包含指定的元素,则返回<tt> true </tt>。
     * 
     * 
     * @param e element to be checked for containment in this collection
     * @return <tt>true</tt> if this set contains the specified element
     */
    public boolean contains(Object e) {
        if (e == null)
            return false;
        Class<?> eClass = e.getClass();
        if (eClass != elementType && eClass.getSuperclass() != elementType)
            return false;

        int eOrdinal = ((Enum<?>)e).ordinal();
        return (elements[eOrdinal >>> 6] & (1L << eOrdinal)) != 0;
    }

    // Modification Operations

    /**
     * Adds the specified element to this set if it is not already present.
     *
     * <p>
     *  如果指定的元素不存在,则将其添加到此集合。
     * 
     * 
     * @param e element to be added to this set
     * @return <tt>true</tt> if the set changed as a result of the call
     *
     * @throws NullPointerException if <tt>e</tt> is null
     */
    public boolean add(E e) {
        typeCheck(e);

        int eOrdinal = e.ordinal();
        int eWordNum = eOrdinal >>> 6;

        long oldElements = elements[eWordNum];
        elements[eWordNum] |= (1L << eOrdinal);
        boolean result = (elements[eWordNum] != oldElements);
        if (result)
            size++;
        return result;
    }

    /**
     * Removes the specified element from this set if it is present.
     *
     * <p>
     *  从此集合中删除指定的元素(如果存在)。
     * 
     * 
     * @param e element to be removed from this set, if present
     * @return <tt>true</tt> if the set contained the specified element
     */
    public boolean remove(Object e) {
        if (e == null)
            return false;
        Class<?> eClass = e.getClass();
        if (eClass != elementType && eClass.getSuperclass() != elementType)
            return false;
        int eOrdinal = ((Enum<?>)e).ordinal();
        int eWordNum = eOrdinal >>> 6;

        long oldElements = elements[eWordNum];
        elements[eWordNum] &= ~(1L << eOrdinal);
        boolean result = (elements[eWordNum] != oldElements);
        if (result)
            size--;
        return result;
    }

    // Bulk Operations

    /**
     * Returns <tt>true</tt> if this set contains all of the elements
     * in the specified collection.
     *
     * <p>
     *  如果此集合包含指定集合中的所有元素,则返回<tt> true </tt>。
     * 
     * 
     * @param c collection to be checked for containment in this set
     * @return <tt>true</tt> if this set contains all of the elements
     *        in the specified collection
     * @throws NullPointerException if the specified collection is null
     */
    public boolean containsAll(Collection<?> c) {
        if (!(c instanceof JumboEnumSet))
            return super.containsAll(c);

        JumboEnumSet<?> es = (JumboEnumSet<?>)c;
        if (es.elementType != elementType)
            return es.isEmpty();

        for (int i = 0; i < elements.length; i++)
            if ((es.elements[i] & ~elements[i]) != 0)
                return false;
        return true;
    }

    /**
     * Adds all of the elements in the specified collection to this set.
     *
     * <p>
     * 将指定集合中的所有元素添加到此集合。
     * 
     * 
     * @param c collection whose elements are to be added to this set
     * @return <tt>true</tt> if this set changed as a result of the call
     * @throws NullPointerException if the specified collection or any of
     *     its elements are null
     */
    public boolean addAll(Collection<? extends E> c) {
        if (!(c instanceof JumboEnumSet))
            return super.addAll(c);

        JumboEnumSet<?> es = (JumboEnumSet<?>)c;
        if (es.elementType != elementType) {
            if (es.isEmpty())
                return false;
            else
                throw new ClassCastException(
                    es.elementType + " != " + elementType);
        }

        for (int i = 0; i < elements.length; i++)
            elements[i] |= es.elements[i];
        return recalculateSize();
    }

    /**
     * Removes from this set all of its elements that are contained in
     * the specified collection.
     *
     * <p>
     *  从此集合中删除包含在指定集合中的所有元素。
     * 
     * 
     * @param c elements to be removed from this set
     * @return <tt>true</tt> if this set changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     */
    public boolean removeAll(Collection<?> c) {
        if (!(c instanceof JumboEnumSet))
            return super.removeAll(c);

        JumboEnumSet<?> es = (JumboEnumSet<?>)c;
        if (es.elementType != elementType)
            return false;

        for (int i = 0; i < elements.length; i++)
            elements[i] &= ~es.elements[i];
        return recalculateSize();
    }

    /**
     * Retains only the elements in this set that are contained in the
     * specified collection.
     *
     * <p>
     *  仅保留此集合中包含在指定集合中的元素。
     * 
     * 
     * @param c elements to be retained in this set
     * @return <tt>true</tt> if this set changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     */
    public boolean retainAll(Collection<?> c) {
        if (!(c instanceof JumboEnumSet))
            return super.retainAll(c);

        JumboEnumSet<?> es = (JumboEnumSet<?>)c;
        if (es.elementType != elementType) {
            boolean changed = (size != 0);
            clear();
            return changed;
        }

        for (int i = 0; i < elements.length; i++)
            elements[i] &= es.elements[i];
        return recalculateSize();
    }

    /**
     * Removes all of the elements from this set.
     * <p>
     *  删除此集合中的所有元素。
     * 
     */
    public void clear() {
        Arrays.fill(elements, 0);
        size = 0;
    }

    /**
     * Compares the specified object with this set for equality.  Returns
     * <tt>true</tt> if the given object is also a set, the two sets have
     * the same size, and every member of the given set is contained in
     * this set.
     *
     * <p>
     *  将指定的对象与此设置相比较以确保相等。返回<tt> true </tt>如果给定对象也是一个集合,则两个集合具有相同的大小,并且给定集合的每个成员都包含在此集合中。
     * 
     * 
     * @param o object to be compared for equality with this set
     * @return <tt>true</tt> if the specified object is equal to this set
     */
    public boolean equals(Object o) {
        if (!(o instanceof JumboEnumSet))
            return super.equals(o);

        JumboEnumSet<?> es = (JumboEnumSet<?>)o;
        if (es.elementType != elementType)
            return size == 0 && es.size == 0;

        return Arrays.equals(es.elements, elements);
    }

    /**
     * Recalculates the size of the set.  Returns true if it's changed.
     * <p>
     *  重新计算集合的大小。如果更改,则返回true。
     */
    private boolean recalculateSize() {
        int oldSize = size;
        size = 0;
        for (long elt : elements)
            size += Long.bitCount(elt);

        return size != oldSize;
    }

    public EnumSet<E> clone() {
        JumboEnumSet<E> result = (JumboEnumSet<E>) super.clone();
        result.elements = result.elements.clone();
        return result;
    }
}
