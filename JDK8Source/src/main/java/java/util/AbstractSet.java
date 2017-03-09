/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * This class provides a skeletal implementation of the <tt>Set</tt>
 * interface to minimize the effort required to implement this
 * interface. <p>
 *
 * The process of implementing a set by extending this class is identical
 * to that of implementing a Collection by extending AbstractCollection,
 * except that all of the methods and constructors in subclasses of this
 * class must obey the additional constraints imposed by the <tt>Set</tt>
 * interface (for instance, the add method must not permit addition of
 * multiple instances of an object to a set).<p>
 *
 * Note that this class does not override any of the implementations from
 * the <tt>AbstractCollection</tt> class.  It merely adds implementations
 * for <tt>equals</tt> and <tt>hashCode</tt>.<p>
 *
 * This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * <p>
 *  此类提供了<tt> Set </tt>接口的骨架实现,以最小化实现此接口所需的工作量。 <p>
 * 
 *  通过扩展这个类来实现集合的过程与通过扩展AbstractCollection来实现集合的过程相同,只是该类的子类中的所有方法和构造函数都必须遵守<tt> Set </tt >接口(例如,add方法不允
 * 许将一个对象的多个实例添加到集合中)。
 * <p>。
 * 
 *  请注意,此类不会覆盖<tt> AbstractCollection </tt>类中的任何实现。它只是添加了<tt> equals </tt>和<tt> hashCode </tt>的实现。<p>
 * 
 *  这个类是成员
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 *  Java集合框架</a>。
 * 
 * 
 * @param <E> the type of elements maintained by this set
 *
 * @author  Josh Bloch
 * @author  Neal Gafter
 * @see Collection
 * @see AbstractCollection
 * @see Set
 * @since 1.2
 */

public abstract class AbstractSet<E> extends AbstractCollection<E> implements Set<E> {
    /**
     * Sole constructor.  (For invocation by subclass constructors, typically
     * implicit.)
     * <p>
     *  唯一构造函数。 (对于子类构造函数的调用,通常是隐式的。)
     * 
     */
    protected AbstractSet() {
    }

    // Comparison and hashing

    /**
     * Compares the specified object with this set for equality.  Returns
     * <tt>true</tt> if the given object is also a set, the two sets have
     * the same size, and every member of the given set is contained in
     * this set.  This ensures that the <tt>equals</tt> method works
     * properly across different implementations of the <tt>Set</tt>
     * interface.<p>
     *
     * This implementation first checks if the specified object is this
     * set; if so it returns <tt>true</tt>.  Then, it checks if the
     * specified object is a set whose size is identical to the size of
     * this set; if not, it returns false.  If so, it returns
     * <tt>containsAll((Collection) o)</tt>.
     *
     * <p>
     *  将指定的对象与此设置相比较以确保相等。返回<tt> true </tt>如果给定对象也是一个集合,则两个集合具有相同的大小,并且给定集合的每个成员都包含在此集合中。
     * 这可以确保<tt>等于</tt>方法在<tt> Set </tt>界面的不同实现中正常工作。<p>。
     * 
     * 这个实现首先检查指定的对象是否是这个集合;如果是,则返回<tt> true </tt>。然后,它检查指定的对象是否是一个大小与此集合的大小相同的集合;如果不是,则返回false。
     * 如果是,则返回<tt> containsAll((Collection)o)</tt>。
     * 
     * 
     * @param o object to be compared for equality with this set
     * @return <tt>true</tt> if the specified object is equal to this set
     */
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof Set))
            return false;
        Collection<?> c = (Collection<?>) o;
        if (c.size() != size())
            return false;
        try {
            return containsAll(c);
        } catch (ClassCastException unused)   {
            return false;
        } catch (NullPointerException unused) {
            return false;
        }
    }

    /**
     * Returns the hash code value for this set.  The hash code of a set is
     * defined to be the sum of the hash codes of the elements in the set,
     * where the hash code of a <tt>null</tt> element is defined to be zero.
     * This ensures that <tt>s1.equals(s2)</tt> implies that
     * <tt>s1.hashCode()==s2.hashCode()</tt> for any two sets <tt>s1</tt>
     * and <tt>s2</tt>, as required by the general contract of
     * {@link Object#hashCode}.
     *
     * <p>This implementation iterates over the set, calling the
     * <tt>hashCode</tt> method on each element in the set, and adding up
     * the results.
     *
     * <p>
     *  返回此集合的哈希码值。集合的哈希码被定义为集合中的元素的哈希码的总和,其中<tt> null </tt>元素的哈希码被定义为零。
     * 这可以确保<tt> s1.equals(s2)</tt>意味着任何两个集合<tt> s1 </tt>的<tt> s1.hashCode()== s2.hashCode()</tt> <tt> s2 </tt>
     * ,根据{@link Object#hashCode}的一般合同的要求。
     *  返回此集合的哈希码值。集合的哈希码被定义为集合中的元素的哈希码的总和,其中<tt> null </tt>元素的哈希码被定义为零。
     * 
     *  <p>此实现循环遍历集合,对集合中的每个元素调用<tt> hashCode </tt>方法,并将结果相加。
     * 
     * 
     * @return the hash code value for this set
     * @see Object#equals(Object)
     * @see Set#equals(Object)
     */
    public int hashCode() {
        int h = 0;
        Iterator<E> i = iterator();
        while (i.hasNext()) {
            E obj = i.next();
            if (obj != null)
                h += obj.hashCode();
        }
        return h;
    }

    /**
     * Removes from this set all of its elements that are contained in the
     * specified collection (optional operation).  If the specified
     * collection is also a set, this operation effectively modifies this
     * set so that its value is the <i>asymmetric set difference</i> of
     * the two sets.
     *
     * <p>This implementation determines which is the smaller of this set
     * and the specified collection, by invoking the <tt>size</tt>
     * method on each.  If this set has fewer elements, then the
     * implementation iterates over this set, checking each element
     * returned by the iterator in turn to see if it is contained in
     * the specified collection.  If it is so contained, it is removed
     * from this set with the iterator's <tt>remove</tt> method.  If
     * the specified collection has fewer elements, then the
     * implementation iterates over the specified collection, removing
     * from this set each element returned by the iterator, using this
     * set's <tt>remove</tt> method.
     *
     * <p>Note that this implementation will throw an
     * <tt>UnsupportedOperationException</tt> if the iterator returned by the
     * <tt>iterator</tt> method does not implement the <tt>remove</tt> method.
     *
     * <p>
     *  从此集合中删除包含在指定集合中的所有元素(可选操作)。如果指定的集合也是集合,则此操作有效地修改该集合,使得其值是两个集合的<i>非对称集合差异</i>。
     * 
     * <p>此实现通过在每个方法上调用<tt> size </tt>方法来确定此集合和指定集合中的较小者。
     * 如果这个集合具有较少的元素,那么实现在该集合上迭代,检查迭代器返回的每个元素,以查看它是否包含在指定的集合中。如果它是这样包含的,它将使用迭代器的<tt> remove </tt>方法从此集合中删除。
     * 如果指定的集合具有较少的元素,那么实现将遍历指定的集合,从此集合中除去迭代器返回的每个元素,使用此集合的<tt> remove </tt>方法。
     * 
     * @param  c collection containing elements to be removed from this set
     * @return <tt>true</tt> if this set changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>removeAll</tt> operation
     *         is not supported by this set
     * @throws ClassCastException if the class of an element of this set
     *         is incompatible with the specified collection
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if this set contains a null element and the
     *         specified collection does not permit null elements
     * (<a href="Collection.html#optional-restrictions">optional</a>),
     *         or if the specified collection is null
     * @see #remove(Object)
     * @see #contains(Object)
     */
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);
        boolean modified = false;

        if (size() > c.size()) {
            for (Iterator<?> i = c.iterator(); i.hasNext(); )
                modified |= remove(i.next());
        } else {
            for (Iterator<?> i = iterator(); i.hasNext(); ) {
                if (c.contains(i.next())) {
                    i.remove();
                    modified = true;
                }
            }
        }
        return modified;
    }

}
