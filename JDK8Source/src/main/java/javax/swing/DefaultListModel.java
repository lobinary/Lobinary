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

package javax.swing;

import java.util.Vector;
import java.util.Enumeration;

import javax.swing.event.*;


/**
 * This class loosely implements the <code>java.util.Vector</code>
 * API, in that it implements the 1.1.x version of
 * <code>java.util.Vector</code>, has no collection class support,
 * and notifies the <code>ListDataListener</code>s when changes occur.
 * Presently it delegates to a <code>Vector</code>,
 * in a future release it will be a real Collection implementation.
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans&trade;
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * <p>
 *  这个类宽松地实现了<code> java.util.Vector </code> API,因为它实现了<code> java.util.Vector </code>的1.1.x版本,没有集合类支持,当
 * 发生更改时,使用<code> ListDataListener </code>。
 * 目前,它委托一个<code> Vector </code>,在未来的版本中,它将是一个真正的Collection实现。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @param <E> the type of the elements of this model
 *
 * @author Hans Muller
 */
public class DefaultListModel<E> extends AbstractListModel<E>
{
    private Vector<E> delegate = new Vector<E>();

    /**
     * Returns the number of components in this list.
     * <p>
     * This method is identical to <code>size</code>, which implements the
     * <code>List</code> interface defined in the 1.2 Collections framework.
     * This method exists in conjunction with <code>setSize</code> so that
     * <code>size</code> is identifiable as a JavaBean property.
     *
     * <p>
     *  返回此列表中的组件数。
     * <p>
     *  此方法与<code> size </code>相同,它实现了1.2集合框架中定义的<code> List </code>接口。
     * 此方法与<code> setSize </code>结合存在,以便<code> size </code>可标识为JavaBean属性。
     * 
     * 
     * @return  the number of components in this list
     * @see #size()
     */
    public int getSize() {
        return delegate.size();
    }

    /**
     * Returns the component at the specified index.
     * <blockquote>
     * <b>Note:</b> Although this method is not deprecated, the preferred
     *    method to use is <code>get(int)</code>, which implements the
     *    <code>List</code> interface defined in the 1.2 Collections framework.
     * </blockquote>
     * <p>
     *  返回指定索引处的组件。
     * <blockquote>
     *  <b>注意：</b>虽然这个方法并没有被弃用,但是使用的首选方法是<code> get(int)</code>,它实现了1.2中定义的<code> List </code>集合框架。
     * </blockquote>
     * 
     * @param      index   an index into this list
     * @return     the component at the specified index
     * @exception  ArrayIndexOutOfBoundsException  if the <code>index</code>
     *             is negative or greater than the current size of this
     *             list
     * @see #get(int)
     */
    public E getElementAt(int index) {
        return delegate.elementAt(index);
    }

    /**
     * Copies the components of this list into the specified array.
     * The array must be big enough to hold all the objects in this list,
     * else an <code>IndexOutOfBoundsException</code> is thrown.
     *
     * <p>
     * 将此列表的组件复制到指定的数组中。数组必须足够大以容纳此列表中的所有对象,否则抛出一个<code> IndexOutOfBoundsException </code>。
     * 
     * 
     * @param   anArray   the array into which the components get copied
     * @see Vector#copyInto(Object[])
     */
    public void copyInto(Object anArray[]) {
        delegate.copyInto(anArray);
    }

    /**
     * Trims the capacity of this list to be the list's current size.
     *
     * <p>
     *  将此列表的容量修改为列表的当前大小。
     * 
     * 
     * @see Vector#trimToSize()
     */
    public void trimToSize() {
        delegate.trimToSize();
    }

    /**
     * Increases the capacity of this list, if necessary, to ensure
     * that it can hold at least the number of components specified by
     * the minimum capacity argument.
     *
     * <p>
     *  如果需要,增加此列表的容量,以确保它至少可以容纳由最小容量参数指定的组件数。
     * 
     * 
     * @param   minCapacity   the desired minimum capacity
     * @see Vector#ensureCapacity(int)
     */
    public void ensureCapacity(int minCapacity) {
        delegate.ensureCapacity(minCapacity);
    }

    /**
     * Sets the size of this list.
     *
     * <p>
     *  设置此列表的大小。
     * 
     * 
     * @param   newSize   the new size of this list
     * @see Vector#setSize(int)
     */
    public void setSize(int newSize) {
        int oldSize = delegate.size();
        delegate.setSize(newSize);
        if (oldSize > newSize) {
            fireIntervalRemoved(this, newSize, oldSize-1);
        }
        else if (oldSize < newSize) {
            fireIntervalAdded(this, oldSize, newSize-1);
        }
    }

    /**
     * Returns the current capacity of this list.
     *
     * <p>
     *  返回此列表的当前容量。
     * 
     * 
     * @return  the current capacity
     * @see Vector#capacity()
     */
    public int capacity() {
        return delegate.capacity();
    }

    /**
     * Returns the number of components in this list.
     *
     * <p>
     *  返回此列表中的组件数。
     * 
     * 
     * @return  the number of components in this list
     * @see Vector#size()
     */
    public int size() {
        return delegate.size();
    }

    /**
     * Tests whether this list has any components.
     *
     * <p>
     *  测试此列表是否具有任何组件。
     * 
     * 
     * @return  <code>true</code> if and only if this list has
     *          no components, that is, its size is zero;
     *          <code>false</code> otherwise
     * @see Vector#isEmpty()
     */
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    /**
     * Returns an enumeration of the components of this list.
     *
     * <p>
     *  返回此列表的组件的枚举。
     * 
     * 
     * @return  an enumeration of the components of this list
     * @see Vector#elements()
     */
    public Enumeration<E> elements() {
        return delegate.elements();
    }

    /**
     * Tests whether the specified object is a component in this list.
     *
     * <p>
     *  测试指定的对象是否为此列表中的组件。
     * 
     * 
     * @param   elem   an object
     * @return  <code>true</code> if the specified object
     *          is the same as a component in this list
     * @see Vector#contains(Object)
     */
    public boolean contains(Object elem) {
        return delegate.contains(elem);
    }

    /**
     * Searches for the first occurrence of <code>elem</code>.
     *
     * <p>
     *  搜索第一次出现的<code> elem </code>。
     * 
     * 
     * @param   elem   an object
     * @return  the index of the first occurrence of the argument in this
     *          list; returns <code>-1</code> if the object is not found
     * @see Vector#indexOf(Object)
     */
    public int indexOf(Object elem) {
        return delegate.indexOf(elem);
    }

    /**
     * Searches for the first occurrence of <code>elem</code>, beginning
     * the search at <code>index</code>.
     *
     * <p>
     *  搜索第一次出现的<code> elem </code>,开始在<code> index </code>处搜索。
     * 
     * 
     * @param   elem    an desired component
     * @param   index   the index from which to begin searching
     * @return  the index where the first occurrence of <code>elem</code>
     *          is found after <code>index</code>; returns <code>-1</code>
     *          if the <code>elem</code> is not found in the list
     * @see Vector#indexOf(Object,int)
     */
     public int indexOf(Object elem, int index) {
        return delegate.indexOf(elem, index);
    }

    /**
     * Returns the index of the last occurrence of <code>elem</code>.
     *
     * <p>
     *  返回<code> elem </code>的最后一个出现的索引。
     * 
     * 
     * @param   elem   the desired component
     * @return  the index of the last occurrence of <code>elem</code>
     *          in the list; returns <code>-1</code> if the object is not found
     * @see Vector#lastIndexOf(Object)
     */
    public int lastIndexOf(Object elem) {
        return delegate.lastIndexOf(elem);
    }

    /**
     * Searches backwards for <code>elem</code>, starting from the
     * specified index, and returns an index to it.
     *
     * <p>
     *  从指定的索引开始向后搜索<code> elem </code>,并向其返回索引。
     * 
     * 
     * @param  elem    the desired component
     * @param  index   the index to start searching from
     * @return the index of the last occurrence of the <code>elem</code>
     *          in this list at position less than <code>index</code>;
     *          returns <code>-1</code> if the object is not found
     * @see Vector#lastIndexOf(Object,int)
     */
    public int lastIndexOf(Object elem, int index) {
        return delegate.lastIndexOf(elem, index);
    }

    /**
     * Returns the component at the specified index.
     * Throws an <code>ArrayIndexOutOfBoundsException</code> if the index
     * is negative or not less than the size of the list.
     * <blockquote>
     * <b>Note:</b> Although this method is not deprecated, the preferred
     *    method to use is <code>get(int)</code>, which implements the
     *    <code>List</code> interface defined in the 1.2 Collections framework.
     * </blockquote>
     *
     * <p>
     *  返回指定索引处的组件。如果索引为负数或不小于列表大小,则抛出<code> ArrayIndexOutOfBoundsException </code>。
     * <blockquote>
     *  <b>注意：</b>虽然这个方法并没有被弃用,但是使用的首选方法是<code> get(int)</code>,它实现了1.2中定义的<code> List </code>集合框架。
     * </blockquote>
     * 
     * 
     * @param      index   an index into this list
     * @return     the component at the specified index
     * @see #get(int)
     * @see Vector#elementAt(int)
     */
    public E elementAt(int index) {
        return delegate.elementAt(index);
    }

    /**
     * Returns the first component of this list.
     * Throws a <code>NoSuchElementException</code> if this
     * vector has no components.
     * <p>
     * 返回此列表的第一个组件。如果此向量没有组件,则抛出<code> NoSuchElementException </code>。
     * 
     * 
     * @return     the first component of this list
     * @see Vector#firstElement()
     */
    public E firstElement() {
        return delegate.firstElement();
    }

    /**
     * Returns the last component of the list.
     * Throws a <code>NoSuchElementException</code> if this vector
     * has no components.
     *
     * <p>
     *  返回列表的最后一个组件。如果此向量没有组件,则抛出<code> NoSuchElementException </code>。
     * 
     * 
     * @return  the last component of the list
     * @see Vector#lastElement()
     */
    public E lastElement() {
        return delegate.lastElement();
    }

    /**
     * Sets the component at the specified <code>index</code> of this
     * list to be the specified element. The previous component at that
     * position is discarded.
     * <p>
     * Throws an <code>ArrayIndexOutOfBoundsException</code> if the index
     * is invalid.
     * <blockquote>
     * <b>Note:</b> Although this method is not deprecated, the preferred
     *    method to use is <code>set(int,Object)</code>, which implements the
     *    <code>List</code> interface defined in the 1.2 Collections framework.
     * </blockquote>
     *
     * <p>
     *  将该列表的指定<code> index </code>处的组件设置为指定的元素。在该位置的前一个组件被丢弃。
     * <p>
     *  如果索引无效,则抛出<code> ArrayIndexOutOfBoundsException </code>。
     * <blockquote>
     *  <b>注意：</b>虽然这个方法不被弃用,但使用的首选方法是<code> set(int,Object)</code>,它实现<code> List </code> 1.2集合框架。
     * </blockquote>
     * 
     * 
     * @param      element what the component is to be set to
     * @param      index   the specified index
     * @see #set(int,Object)
     * @see Vector#setElementAt(Object,int)
     */
    public void setElementAt(E element, int index) {
        delegate.setElementAt(element, index);
        fireContentsChanged(this, index, index);
    }

    /**
     * Deletes the component at the specified index.
     * <p>
     * Throws an <code>ArrayIndexOutOfBoundsException</code> if the index
     * is invalid.
     * <blockquote>
     * <b>Note:</b> Although this method is not deprecated, the preferred
     *    method to use is <code>remove(int)</code>, which implements the
     *    <code>List</code> interface defined in the 1.2 Collections framework.
     * </blockquote>
     *
     * <p>
     *  删除指定索引处的组件。
     * <p>
     *  如果索引无效,则抛出<code> ArrayIndexOutOfBoundsException </code>。
     * <blockquote>
     *  <b>注意：</b>虽然这个方法不被弃用,但是使用的首选方法是<code> remove(int)</code>,它实现了1.2中定义的<code> List </code>集合框架。
     * </blockquote>
     * 
     * 
     * @param      index   the index of the object to remove
     * @see #remove(int)
     * @see Vector#removeElementAt(int)
     */
    public void removeElementAt(int index) {
        delegate.removeElementAt(index);
        fireIntervalRemoved(this, index, index);
    }

    /**
     * Inserts the specified element as a component in this list at the
     * specified <code>index</code>.
     * <p>
     * Throws an <code>ArrayIndexOutOfBoundsException</code> if the index
     * is invalid.
     * <blockquote>
     * <b>Note:</b> Although this method is not deprecated, the preferred
     *    method to use is <code>add(int,Object)</code>, which implements the
     *    <code>List</code> interface defined in the 1.2 Collections framework.
     * </blockquote>
     *
     * <p>
     *  将指定的元素作为组件插入到此列表中指定的<code> index </code>。
     * <p>
     *  如果索引无效,则抛出<code> ArrayIndexOutOfBoundsException </code>。
     * <blockquote>
     *  <b>注意：</b>虽然这种方法不被弃用,但使用的首选方法是<code> add(int,Object)</code>,它实现<code> List </code> 1.2集合框架。
     * </blockquote>
     * 
     * 
     * @param      element the component to insert
     * @param      index   where to insert the new component
     * @exception  ArrayIndexOutOfBoundsException  if the index was invalid
     * @see #add(int,Object)
     * @see Vector#insertElementAt(Object,int)
     */
    public void insertElementAt(E element, int index) {
        delegate.insertElementAt(element, index);
        fireIntervalAdded(this, index, index);
    }

    /**
     * Adds the specified component to the end of this list.
     *
     * <p>
     *  将指定的组件添加到此列表的末尾。
     * 
     * 
     * @param   element   the component to be added
     * @see Vector#addElement(Object)
     */
    public void addElement(E element) {
        int index = delegate.size();
        delegate.addElement(element);
        fireIntervalAdded(this, index, index);
    }

    /**
     * Removes the first (lowest-indexed) occurrence of the argument
     * from this list.
     *
     * <p>
     * 从此列表中删除参数的第一个(索引最低的)。
     * 
     * 
     * @param   obj   the component to be removed
     * @return  <code>true</code> if the argument was a component of this
     *          list; <code>false</code> otherwise
     * @see Vector#removeElement(Object)
     */
    public boolean removeElement(Object obj) {
        int index = indexOf(obj);
        boolean rv = delegate.removeElement(obj);
        if (index >= 0) {
            fireIntervalRemoved(this, index, index);
        }
        return rv;
    }


    /**
     * Removes all components from this list and sets its size to zero.
     * <blockquote>
     * <b>Note:</b> Although this method is not deprecated, the preferred
     *    method to use is <code>clear</code>, which implements the
     *    <code>List</code> interface defined in the 1.2 Collections framework.
     * </blockquote>
     *
     * <p>
     *  从此列表中删除所有组件,并将其大小设置为零。
     * <blockquote>
     *  <b>注意：</b>虽然这个方法不被弃用,但使用的首选方法是<code> clear </code>,它实现了1.2集合框架中定义的<code> List </code>接口。
     * </blockquote>
     * 
     * 
     * @see #clear()
     * @see Vector#removeAllElements()
     */
    public void removeAllElements() {
        int index1 = delegate.size()-1;
        delegate.removeAllElements();
        if (index1 >= 0) {
            fireIntervalRemoved(this, 0, index1);
        }
    }


    /**
     * Returns a string that displays and identifies this
     * object's properties.
     *
     * <p>
     *  返回显示和标识此对象属性的字符串。
     * 
     * 
     * @return a String representation of this object
     */
   public String toString() {
        return delegate.toString();
    }


    /* The remaining methods are included for compatibility with the
     * Java 2 platform Vector class.
     * <p>
     *  Java 2平台矢量类。
     * 
     */

    /**
     * Returns an array containing all of the elements in this list in the
     * correct order.
     *
     * <p>
     *  以正确的顺序返回包含此列表中所有元素的数组。
     * 
     * 
     * @return an array containing the elements of the list
     * @see Vector#toArray()
     */
    public Object[] toArray() {
        Object[] rv = new Object[delegate.size()];
        delegate.copyInto(rv);
        return rv;
    }

    /**
     * Returns the element at the specified position in this list.
     * <p>
     * Throws an <code>ArrayIndexOutOfBoundsException</code>
     * if the index is out of range
     * (<code>index &lt; 0 || index &gt;= size()</code>).
     *
     * <p>
     *  返回此列表中指定位置的元素。
     * <p>
     *  如果索引超出范围(<code> index&lt; 0 || index&gt; = size()</code>),则抛出<code> ArrayIndexOutOfBoundsException </code>
     * 。
     * 
     * 
     * @param index index of element to return
     */
    public E get(int index) {
        return delegate.elementAt(index);
    }

    /**
     * Replaces the element at the specified position in this list with the
     * specified element.
     * <p>
     * Throws an <code>ArrayIndexOutOfBoundsException</code>
     * if the index is out of range
     * (<code>index &lt; 0 || index &gt;= size()</code>).
     *
     * <p>
     *  用指定的元素替换此列表中指定位置处的元素。
     * <p>
     *  如果索引超出范围(<code> index&lt; 0 || index&gt; = size()</code>),则会抛出<code> ArrayIndexOutOfBoundsException 
     * </code>。
     * 
     * 
     * @param index index of element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     */
    public E set(int index, E element) {
        E rv = delegate.elementAt(index);
        delegate.setElementAt(element, index);
        fireContentsChanged(this, index, index);
        return rv;
    }

    /**
     * Inserts the specified element at the specified position in this list.
     * <p>
     * Throws an <code>ArrayIndexOutOfBoundsException</code> if the
     * index is out of range
     * (<code>index &lt; 0 || index &gt; size()</code>).
     *
     * <p>
     *  在此列表中指定的位置插入指定的元素。
     * <p>
     *  如果索引超出范围(<code> index&lt; 0 || index&gt; size()</code>),则抛出<code> ArrayIndexOutOfBoundsException </code>
     * 。
     * 
     * 
     * @param index index at which the specified element is to be inserted
     * @param element element to be inserted
     */
    public void add(int index, E element) {
        delegate.insertElementAt(element, index);
        fireIntervalAdded(this, index, index);
    }

    /**
     * Removes the element at the specified position in this list.
     * Returns the element that was removed from the list.
     * <p>
     * Throws an <code>ArrayIndexOutOfBoundsException</code>
     * if the index is out of range
     * (<code>index &lt; 0 || index &gt;= size()</code>).
     *
     * <p>
     *  删除此列表中指定位置的元素。返回从列表中删除的元素。
     * <p>
     *  如果索引超出范围(<code> index&lt; 0 || index&gt; = size()</code>),则抛出<code> ArrayIndexOutOfBoundsException </code>
     * 。
     * 
     * 
     * @param index the index of the element to removed
     * @return the element previously at the specified position
     */
    public E remove(int index) {
        E rv = delegate.elementAt(index);
        delegate.removeElementAt(index);
        fireIntervalRemoved(this, index, index);
        return rv;
    }

    /**
     * Removes all of the elements from this list.  The list will
     * be empty after this call returns (unless it throws an exception).
     * <p>
     * 从此列表中删除所有元素。此调用返回后,列表将为空(除非它抛出异常)。
     * 
     */
    public void clear() {
        int index1 = delegate.size()-1;
        delegate.removeAllElements();
        if (index1 >= 0) {
            fireIntervalRemoved(this, 0, index1);
        }
    }

    /**
     * Deletes the components at the specified range of indexes.
     * The removal is inclusive, so specifying a range of (1,5)
     * removes the component at index 1 and the component at index 5,
     * as well as all components in between.
     * <p>
     * Throws an <code>ArrayIndexOutOfBoundsException</code>
     * if the index was invalid.
     * Throws an <code>IllegalArgumentException</code> if
     * <code>fromIndex &gt; toIndex</code>.
     *
     * <p>
     *  删除指定的索引范围内的组件。删除是包含性的,因此指定范围(1,5)将删除索引1处的组件和索引5处的组件,以及它们之间的所有组件。
     * <p>
     *  如果索引无效,则抛出<code> ArrayIndexOutOfBoundsException </code>。
     * 抛出<code> IllegalArgumentException </code>如果<code> fromIndex&gt; toIndex </code>。
     * 
     * 
     * @param      fromIndex the index of the lower end of the range
     * @param      toIndex   the index of the upper end of the range
     * @see        #remove(int)
     */
    public void removeRange(int fromIndex, int toIndex) {
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException("fromIndex must be <= toIndex");
        }
        for(int i = toIndex; i >= fromIndex; i--) {
            delegate.removeElementAt(i);
        }
        fireIntervalRemoved(this, fromIndex, toIndex);
    }

    /*
    public void addAll(Collection c) {
    }

    public void addAll(int index, Collection c) {
    }
    /* <p>
    */
}
