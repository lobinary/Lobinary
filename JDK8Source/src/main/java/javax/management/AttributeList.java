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

package javax.management;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Represents a list of values for attributes of an MBean.  See the
 * {@link MBeanServerConnection#getAttributes getAttributes} and
 * {@link MBeanServerConnection#setAttributes setAttributes} methods of
 * {@link MBeanServer} and {@link MBeanServerConnection}.</p>
 *
 * <p id="type-safe">For compatibility reasons, it is possible, though
 * highly discouraged, to add objects to an {@code AttributeList} that are
 * not instances of {@code Attribute}.  However, an {@code AttributeList}
 * can be made <em>type-safe</em>, which means that an attempt to add
 * an object that is not an {@code Attribute} will produce an {@code
 * IllegalArgumentException}.  An {@code AttributeList} becomes type-safe
 * when the method {@link #asList()} is called on it.</p>
 *
 * <p>
 *  <p>表示MBean的属性值列表请参阅{@link MBeanServer}和{@link MBeanServerConnection} </p>的{@link MBeanServerConnection#getAttributes getAttributes}
 * 和{@link MBeanServerConnection#setAttributes setAttributes}。
 * 
 * <p id ="type-safe">出于兼容性原因,尽管非常不鼓励,可以向{@code AttributeList}添加不是{@code Attribute}实例的对象。
 * 但是,{@code AttributeList}可以设为<em>类型安全</em>,这意味着尝试添加不是{@code Attribute}的对象将产生{@code IllegalArgumentException}
 * 。
 * <p id ="type-safe">出于兼容性原因,尽管非常不鼓励,可以向{@code AttributeList}添加不是{@code Attribute}实例的对象。
 * {@code AttributeList}变为类型安全当方法{@link #asList()}被调用时</p>。
 * 
 * 
 * @since 1.5
 */
/* We cannot extend ArrayList<Attribute> because our legacy
   add(Attribute) method would then override add(E) in ArrayList<E>,
   and our return value is void whereas ArrayList.add(E)'s is boolean.
   Likewise for set(int,Attribute).  Grrr.  We cannot use covariance
   to override the most important methods and have them return
   Attribute, either, because that would break subclasses that
   override those methods in turn (using the original return type
   of Object).  Finally, we cannot implement Iterable<Attribute>
   so you could write
       for (Attribute a : attributeList)
   because ArrayList<> implements Iterable<> and the same class cannot
   implement two versions of a generic interface.  Instead we provide
   the asList() method so you can write
       for (Attribute a : attributeList.asList())
/* <p>
/* add(Attribute)方法将覆盖ArrayList <E>中的add(E),我们的返回值为void,而ArrayListadd(E)的值为boolean同样对于set(int,Attribute)
/* Grrr我们不能使用协方差来覆盖最多重要的方法,并让它们返回Attribute,因为这将打破依次覆盖这些方法的子类(使用Object的原始返回类型)最后,我们不能实现Iterable <Attribute>
/* ,所以你可以写(Attribute a：attributeList)因为ArrayList <>实现Iterable <>,并且同一个类不能实现通用接口的两个版本。
/* 我们提供asList()方法,所以你可以写(Attribute a：attributeListasList())。
/* 
*/
public class AttributeList extends ArrayList<Object> {

    private transient volatile boolean typeSafe;
    private transient volatile boolean tainted;

    /* Serial version */
    private static final long serialVersionUID = -4077085769279709076L;

    /**
     * Constructs an empty <CODE>AttributeList</CODE>.
     * <p>
     *  构造一个空的<CODE> AttributeList </CODE>
     * 
     */
    public AttributeList() {
        super();
    }

    /**
     * Constructs an empty <CODE>AttributeList</CODE> with
     * the initial capacity specified.
     *
     * <p>
     * 构造具有指定初始容量的空的<CODE> AttributeList </CODE>
     * 
     * 
     * @param initialCapacity the initial capacity of the
     * <code>AttributeList</code>, as specified by {@link
     * ArrayList#ArrayList(int)}.
     */
    public AttributeList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructs an <CODE>AttributeList</CODE> containing the
     * elements of the <CODE>AttributeList</CODE> specified, in the
     * order in which they are returned by the
     * <CODE>AttributeList</CODE>'s iterator.  The
     * <CODE>AttributeList</CODE> instance has an initial capacity of
     * 110% of the size of the <CODE>AttributeList</CODE> specified.
     *
     * <p>
     *  构造包含<CODE> AttributeList </CODE>的元素的<CODE> AttributeList </CODE>,它们按<CODE> AttributeList </CODE>的迭代器
     * 返回的顺序<CODE> AttributeList </CODE>实例具有指定的<CODE> AttributeList </CODE>大小的110％的初始容量。
     * 
     * 
     * @param list the <code>AttributeList</code> that defines the initial
     * contents of the new <code>AttributeList</code>.
     *
     * @see ArrayList#ArrayList(java.util.Collection)
     */
    public AttributeList(AttributeList list) {
        super(list);
    }

    /**
     * Constructs an {@code AttributeList} containing the elements of the
     * {@code List} specified, in the order in which they are returned by
     * the {@code List}'s iterator.
     *
     * <p>
     *  构造包含{@code List}的元素的{@code AttributeList},它们按照{@code List}的迭代器返回的顺序
     * 
     * 
     * @param list the {@code List} that defines the initial contents of
     * the new {@code AttributeList}.
     *
     * @exception IllegalArgumentException if the {@code list} parameter
     * is {@code null} or if the {@code list} parameter contains any
     * non-Attribute objects.
     *
     * @see ArrayList#ArrayList(java.util.Collection)
     *
     * @since 1.6
     */
    public AttributeList(List<Attribute> list) {
        // Check for null parameter
        //
        if (list == null)
            throw new IllegalArgumentException("Null parameter");

        // Check for non-Attribute objects
        //
        adding(list);

        // Build the List<Attribute>
        //
        super.addAll(list);
    }

    /**
     * Return a view of this list as a {@code List<Attribute>}.
     * Changes to the returned value are reflected by changes
     * to the original {@code AttributeList} and vice versa.
     *
     * <p>
     *  以{@code List <Attribute>}形式返回此列表的视图对原始{@code AttributeList}的更改反映了对返回值的更改,反之亦然
     * 
     * 
     * @return a {@code List<Attribute>} whose contents
     * reflect the contents of this {@code AttributeList}.
     *
     * <p>If this method has ever been called on a given
     * {@code AttributeList} instance, a subsequent attempt to add
     * an object to that instance which is not an {@code Attribute}
     * will fail with a {@code IllegalArgumentException}. For compatibility
     * reasons, an {@code AttributeList} on which this method has never
     * been called does allow objects other than {@code Attribute}s to
     * be added.</p>
     *
     * @throws IllegalArgumentException if this {@code AttributeList} contains
     * an element that is not an {@code Attribute}.
     *
     * @since 1.6
     */
    @SuppressWarnings("unchecked")
    public List<Attribute> asList() {
        typeSafe = true;
        if (tainted)
            adding((Collection<?>) this);  // will throw IllegalArgumentException
        return (List<Attribute>) (List<?>) this;
    }

    /**
     * Adds the {@code Attribute} specified as the last element of the list.
     *
     * <p>
     * 添加指定为列表的最后一个元素的{@code Attribute}
     * 
     * 
     * @param object  The attribute to be added.
     */
    public void add(Attribute object)  {
        super.add(object);
    }

    /**
     * Inserts the attribute specified as an element at the position specified.
     * Elements with an index greater than or equal to the current position are
     * shifted up. If the index is out of range {@literal (index < 0 || index >
     * size())} a RuntimeOperationsException should be raised, wrapping the
     * java.lang.IndexOutOfBoundsException thrown.
     *
     * <p>
     *  插入在指定位置指定为元素的属性向上移动索引大于或等于当前位置的元素如果索引超出范围{@literal(index <0 || index> size())} a RuntimeOperationsEx
     * ception应该引发,包装javalangIndexOutOfBoundsException抛出。
     * 
     * 
     * @param object  The <CODE>Attribute</CODE> object to be inserted.
     * @param index The position in the list where the new {@code Attribute}
     * object is to be inserted.
     */
    public void add(int index, Attribute object)  {
        try {
            super.add(index, object);
        }
        catch (IndexOutOfBoundsException e) {
            throw new RuntimeOperationsException(e,
                "The specified index is out of range");
        }
    }

    /**
     * Sets the element at the position specified to be the attribute specified.
     * The previous element at that position is discarded. If the index is
     * out of range {@literal (index < 0 || index > size())} a RuntimeOperationsException
     * should be raised, wrapping the java.lang.IndexOutOfBoundsException thrown.
     *
     * <p>
     *  设置指定位置处的元素为指定的属性该位置上的前一个元素被丢弃如果索引超出范围{@literal(index <0 || index> size())},应引发RuntimeOperationsExcep
     * tion,抛出javalangIndexOutOfBoundsException。
     * 
     * 
     * @param object  The value to which the attribute element should be set.
     * @param index  The position specified.
     */
    public void set(int index, Attribute object)  {
        try {
            super.set(index, object);
        }
        catch (IndexOutOfBoundsException e) {
            throw new RuntimeOperationsException(e,
                "The specified index is out of range");
        }
    }

    /**
     * Appends all the elements in the <CODE>AttributeList</CODE> specified to
     * the end of the list, in the order in which they are returned by the
     * Iterator of the <CODE>AttributeList</CODE> specified.
     *
     * <p>
     * 将指定的<CODE> AttributeList </CODE>中的所有元素按照它们由<CODE> AttributeList </CODE>指定的迭代器返回的顺序追加到列表的末尾
     * 
     * 
     * @param list  Elements to be inserted into the list.
     *
     * @return true if this list changed as a result of the call.
     *
     * @see ArrayList#addAll(java.util.Collection)
     */
    public boolean addAll(AttributeList list)  {
        return (super.addAll(list));
    }

    /**
     * Inserts all of the elements in the <CODE>AttributeList</CODE> specified
     * into this list, starting at the specified position, in the order in which
     * they are returned by the Iterator of the {@code AttributeList} specified.
     * If the index is out of range {@literal (index < 0 || index > size())} a
     * RuntimeOperationsException should be raised, wrapping the
     * java.lang.IndexOutOfBoundsException thrown.
     *
     * <p>
     *  将指定的<CODE> AttributeList </CODE>中的所有元素插入到此列表中,从指定的位置开始,按照它们由{@code AttributeList}指定的迭代器返回的顺序如果索引退出的范
     * 围{@literal(index <0 || index> size())}应该引发一个RuntimeOperationsException,包装javalangIndexOutOfBoundsExce
     * ption抛出。
     * 
     * 
     * @param list  Elements to be inserted into the list.
     * @param index  Position at which to insert the first element from the
     * <CODE>AttributeList</CODE> specified.
     *
     * @return true if this list changed as a result of the call.
     *
     * @see ArrayList#addAll(int, java.util.Collection)
     */
    public boolean addAll(int index, AttributeList list)  {
        try {
            return super.addAll(index, list);
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeOperationsException(e,
                "The specified index is out of range");
        }
    }

    /*
     * Override all of the methods from ArrayList<Object> that might add
     * a non-Attribute to the List, and disallow that if asList has ever
     * been called on this instance.
     * <p>
     *  覆盖ArrayList <Object>中可能向列表中添加非属性的所有方法,并禁止在此实例上调用asList
     * 
     */

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @throws IllegalArgumentException if this {@code AttributeList} is
     * <a href="#type-safe">type-safe</a> and {@code element} is not an
     * {@code Attribute}.
     */
    @Override
    public boolean add(Object element) {
        adding(element);
        return super.add(element);
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @throws IllegalArgumentException if this {@code AttributeList} is
     * <a href="#type-safe">type-safe</a> and {@code element} is not an
     * {@code Attribute}.
     */
    @Override
    public void add(int index, Object element) {
        adding(element);
        super.add(index, element);
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @throws IllegalArgumentException if this {@code AttributeList} is
     * <a href="#type-safe">type-safe</a> and {@code c} contains an
     * element that is not an {@code Attribute}.
     */
    @Override
    public boolean addAll(Collection<?> c) {
        adding(c);
        return super.addAll(c);
    }

    /**
     * {@inheritDoc}
     * <p>
     * {@inheritDoc}
     * 
     * 
     * @throws IllegalArgumentException if this {@code AttributeList} is
     * <a href="#type-safe">type-safe</a> and {@code c} contains an
     * element that is not an {@code Attribute}.
     */
    @Override
    public boolean addAll(int index, Collection<?> c) {
        adding(c);
        return super.addAll(index, c);
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * @throws IllegalArgumentException if this {@code AttributeList} is
     * <a href="#type-safe">type-safe</a> and {@code element} is not an
     * {@code Attribute}.
     */
    @Override
    public Object set(int index, Object element) {
        adding(element);
        return super.set(index, element);
    }

    private void adding(Object x) {
        if (x == null || x instanceof Attribute)
            return;
        if (typeSafe)
            throw new IllegalArgumentException("Not an Attribute: " + x);
        else
            tainted = true;
    }

    private void adding(Collection<?> c) {
        for (Object x : c)
            adding(x);
    }
}
