/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2004, Oracle and/or its affiliates. All rights reserved.
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


package javax.print.attribute;

import java.io.Serializable;

/**
 * Class AttributeSetUtilities provides static methods for manipulating
 * AttributeSets.
 * <ul>
 * <li>Methods for creating unmodifiable and synchronized views of attribute
 * sets.
 * <li>operations useful for building
 * implementations of interface {@link AttributeSet AttributeSet}
 * </ul>
 * <P>
 * An <B>unmodifiable view</B> <I>U</I> of an AttributeSet <I>S</I> provides a
 * client with "read-only" access to <I>S</I>. Query operations on <I>U</I>
 * "read through" to <I>S</I>; thus, changes in <I>S</I> are reflected in
 * <I>U</I>. However, any attempt to modify <I>U</I>,
 *  results in an UnmodifiableSetException.
 * The unmodifiable view object <I>U</I> will be serializable if the
 * attribute set object <I>S</I> is serializable.
 * <P>
 * A <B>synchronized view</B> <I>V</I> of an attribute set <I>S</I> provides a
 * client with synchronized (multiple thread safe) access to <I>S</I>. Each
 * operation of <I>V</I> is synchronized using <I>V</I> itself as the lock
 * object and then merely invokes the corresponding operation of <I>S</I>. In
 * order to guarantee mutually exclusive access, it is critical that all
 * access to <I>S</I> is accomplished through <I>V</I>. The synchronized view
 * object <I>V</I> will be serializable if the attribute set object <I>S</I>
 * is serializable.
 * <P>
 * As mentioned in the package description of javax.print, a null reference
 * parameter to methods is
 * incorrect unless explicitly documented on the method as having a meaningful
 * interpretation.  Usage to the contrary is incorrect coding and may result in
 * a run time exception either immediately
 * or at some later time. IllegalArgumentException and NullPointerException
 * are examples of typical and acceptable run time exceptions for such cases.
 *
 * <p>
 *  Class AttributeSetUtilities类提供了操作AttributeSet的静态方法。
 * <ul>
 *  <li>用于创建属性集的不可修改和同步视图的方法。 <li>用于构建接口{@link AttributeSet AttributeSet}的实现的操作
 * </ul>
 * <P>
 *  AttributeSet <I> S </I>的<b>不可修改视图</>>向客户端提供对<I> S </I>的"只读"访问。
 * 在<I> U </I>上的查询操作"读取"到<I> S </I>;因此,<I> S </I>的变化反映在<I> U </I>中。
 * 然而,任何修改<I> U </I>的尝试都会导致UnmodifiableSetException。如果属性集对象<I> S </I>是可串行化的,则不可修改的视图对象</U>将是可串行化的。
 * <P>
 *  属性集<I> S </I>的<B>同步视图</>> </I>向客户端提供对<I> S </I>的同步(多线程安全) 。
 * 使用<I> V </I>本身作为锁定对象来同步<I> V </I>的每个操作,然后仅仅调用<I> S </I>的相应操作。
 * 为了保证互斥访问,关键是通过<I> V </I>完成对<I> S </I>的所有访问。如果属性集对象</u>是可串行化的,则同步视图对象</i>将是可串行化的。
 * <P>
 * 如javax.print的包描述中所述,除非在方法上明确记录为具有有意义的解释,否则方法的空引用参数是不正确的。相反的用法是不正确的编码,并且可能立即或在稍后的时间导致运行时异常。
 *  IllegalArgumentException和NullPointerException是这种情况下典型和可接受的运行时异常的例子。
 * 
 * 
 * @author  Alan Kaminsky
 */
public final class AttributeSetUtilities {

    /* Suppress default constructor, ensuring non-instantiability.
    /* <p>
     */
    private AttributeSetUtilities() {
    }

    /**
    /* <p>
    /* 
      * @serial include
      */
    private static class UnmodifiableAttributeSet
        implements AttributeSet, Serializable {

        private AttributeSet attrset;

        /* Unmodifiable view of the underlying attribute set.
        /* <p>
         */
        public UnmodifiableAttributeSet(AttributeSet attributeSet) {

            attrset = attributeSet;
        }

        public Attribute get(Class<?> key) {
            return attrset.get(key);
        }

        public boolean add(Attribute attribute) {
            throw new UnmodifiableSetException();
        }

        public synchronized boolean remove(Class<?> category) {
            throw new UnmodifiableSetException();
        }

        public boolean remove(Attribute attribute) {
            throw new UnmodifiableSetException();
        }

        public boolean containsKey(Class<?> category) {
            return attrset.containsKey(category);
        }

        public boolean containsValue(Attribute attribute) {
            return attrset.containsValue(attribute);
        }

        public boolean addAll(AttributeSet attributes) {
            throw new UnmodifiableSetException();
        }

        public int size() {
            return attrset.size();
        }

        public Attribute[] toArray() {
            return attrset.toArray();
        }

        public void clear() {
            throw new UnmodifiableSetException();
        }

        public boolean isEmpty() {
            return attrset.isEmpty();
        }

        public boolean equals(Object o) {
            return attrset.equals (o);
        }

        public int hashCode() {
            return attrset.hashCode();
        }

    }

    /**
    /* <p>
    /* 
      * @serial include
      */
    private static class UnmodifiableDocAttributeSet
        extends UnmodifiableAttributeSet
        implements DocAttributeSet, Serializable {

        public UnmodifiableDocAttributeSet(DocAttributeSet attributeSet) {

            super (attributeSet);
        }
    }

    /**
    /* <p>
    /* 
      * @serial include
      */
    private static class UnmodifiablePrintRequestAttributeSet
        extends UnmodifiableAttributeSet
        implements PrintRequestAttributeSet, Serializable
    {
        public UnmodifiablePrintRequestAttributeSet
            (PrintRequestAttributeSet attributeSet) {

            super (attributeSet);
        }
    }

    /**
    /* <p>
    /* 
      * @serial include
      */
    private static class UnmodifiablePrintJobAttributeSet
        extends UnmodifiableAttributeSet
        implements PrintJobAttributeSet, Serializable
    {
        public UnmodifiablePrintJobAttributeSet
            (PrintJobAttributeSet attributeSet) {

            super (attributeSet);
        }
    }

    /**
    /* <p>
    /* 
      * @serial include
      */
    private static class UnmodifiablePrintServiceAttributeSet
        extends UnmodifiableAttributeSet
        implements PrintServiceAttributeSet, Serializable
    {
        public UnmodifiablePrintServiceAttributeSet
            (PrintServiceAttributeSet attributeSet) {

            super (attributeSet);
        }
    }

    /**
     * Creates an unmodifiable view of the given attribute set.
     *
     * <p>
     *  创建给定属性集的不可更改视图。
     * 
     * 
     * @param  attributeSet  Underlying attribute set.
     *
     * @return  Unmodifiable view of <CODE>attributeSet</CODE>.
     *
     * @exception  NullPointerException
     *     Thrown if <CODE>attributeSet</CODE> is null. Null is never a
     */
    public static AttributeSet unmodifiableView(AttributeSet attributeSet) {
        if (attributeSet == null) {
            throw new NullPointerException();
        }

        return new UnmodifiableAttributeSet(attributeSet);
    }

    /**
     * Creates an unmodifiable view of the given doc attribute set.
     *
     * <p>
     *  创建给定doc属性集的不可修改视图。
     * 
     * 
     * @param  attributeSet  Underlying doc attribute set.
     *
     * @return  Unmodifiable view of <CODE>attributeSet</CODE>.
     *
     * @exception  NullPointerException
     *     Thrown if <CODE>attributeSet</CODE> is null.
     */
    public static DocAttributeSet unmodifiableView
        (DocAttributeSet attributeSet) {
        if (attributeSet == null) {
            throw new NullPointerException();
        }
        return new UnmodifiableDocAttributeSet(attributeSet);
    }

    /**
     * Creates an unmodifiable view of the given print request attribute set.
     *
     * <p>
     *  创建给定打印请求属性集的不可修改视图。
     * 
     * 
     * @param  attributeSet  Underlying print request attribute set.
     *
     * @return  Unmodifiable view of <CODE>attributeSet</CODE>.
     *
     * @exception  NullPointerException
     *     Thrown if <CODE>attributeSet</CODE> is null.
     */
    public static PrintRequestAttributeSet
        unmodifiableView(PrintRequestAttributeSet attributeSet) {
        if (attributeSet == null) {
            throw new NullPointerException();
        }
        return new UnmodifiablePrintRequestAttributeSet(attributeSet);
    }

    /**
     * Creates an unmodifiable view of the given print job attribute set.
     *
     * <p>
     *  创建给定打印作业属性集的不可修改视图。
     * 
     * 
     * @param  attributeSet  Underlying print job attribute set.
     *
     * @return  Unmodifiable view of <CODE>attributeSet</CODE>.
     *
     * @exception  NullPointerException
     *     Thrown if <CODE>attributeSet</CODE> is null.
     */
    public static PrintJobAttributeSet
        unmodifiableView(PrintJobAttributeSet attributeSet) {
        if (attributeSet == null) {
            throw new NullPointerException();
        }
        return new UnmodifiablePrintJobAttributeSet(attributeSet);
    }

    /**
     * Creates an unmodifiable view of the given print service attribute set.
     *
     * <p>
     *  创建给定打印服务属性集的不可修改视图。
     * 
     * 
     * @param  attributeSet  Underlying print service attribute set.
     *
     * @return  Unmodifiable view of <CODE>attributeSet</CODE>.
     *
     * @exception  NullPointerException
     *     Thrown if <CODE>attributeSet</CODE> is null.
     */
    public static PrintServiceAttributeSet
        unmodifiableView(PrintServiceAttributeSet attributeSet) {
        if (attributeSet == null) {
            throw new NullPointerException();
        }
        return new UnmodifiablePrintServiceAttributeSet (attributeSet);
    }

    /**
    /* <p>
    /* 
      * @serial include
      */
    private static class SynchronizedAttributeSet
                        implements AttributeSet, Serializable {

        private AttributeSet attrset;

        public SynchronizedAttributeSet(AttributeSet attributeSet) {
            attrset = attributeSet;
        }

        public synchronized Attribute get(Class<?> category) {
            return attrset.get(category);
        }

        public synchronized boolean add(Attribute attribute) {
            return attrset.add(attribute);
        }

        public synchronized boolean remove(Class<?> category) {
            return attrset.remove(category);
        }

        public synchronized boolean remove(Attribute attribute) {
            return attrset.remove(attribute);
        }

        public synchronized boolean containsKey(Class<?> category) {
            return attrset.containsKey(category);
        }

        public synchronized boolean containsValue(Attribute attribute) {
            return attrset.containsValue(attribute);
        }

        public synchronized boolean addAll(AttributeSet attributes) {
            return attrset.addAll(attributes);
        }

        public synchronized int size() {
            return attrset.size();
        }

        public synchronized Attribute[] toArray() {
            return attrset.toArray();
        }

        public synchronized void clear() {
            attrset.clear();
        }

        public synchronized boolean isEmpty() {
            return attrset.isEmpty();
        }

        public synchronized boolean equals(Object o) {
            return attrset.equals (o);
        }

        public synchronized int hashCode() {
            return attrset.hashCode();
        }
    }

    /**
    /* <p>
    /* 
      * @serial include
      */
    private static class SynchronizedDocAttributeSet
        extends SynchronizedAttributeSet
        implements DocAttributeSet, Serializable {

        public SynchronizedDocAttributeSet(DocAttributeSet attributeSet) {
            super(attributeSet);
        }
    }

    /**
    /* <p>
    /* 
      * @serial include
      */
    private static class SynchronizedPrintRequestAttributeSet
        extends SynchronizedAttributeSet
        implements PrintRequestAttributeSet, Serializable {

        public SynchronizedPrintRequestAttributeSet
            (PrintRequestAttributeSet attributeSet) {
            super(attributeSet);
        }
    }

    /**
    /* <p>
    /* 
      * @serial include
      */
    private static class SynchronizedPrintJobAttributeSet
        extends SynchronizedAttributeSet
        implements PrintJobAttributeSet, Serializable {

        public SynchronizedPrintJobAttributeSet
            (PrintJobAttributeSet attributeSet) {
            super(attributeSet);
        }
    }

    /**
    /* <p>
    /* 
      * @serial include
      */
    private static class SynchronizedPrintServiceAttributeSet
        extends SynchronizedAttributeSet
        implements PrintServiceAttributeSet, Serializable {
        public SynchronizedPrintServiceAttributeSet
            (PrintServiceAttributeSet attributeSet) {
            super(attributeSet);
        }
    }

    /**
     * Creates a synchronized view of the given attribute set.
     *
     * <p>
     *  创建给定属性集的同步视图。
     * 
     * 
     * @param  attributeSet  Underlying attribute set.
     *
     * @return  Synchronized view of <CODE>attributeSet</CODE>.
     *
     * @exception  NullPointerException
     *     Thrown if <CODE>attributeSet</CODE> is null.
     */
    public static AttributeSet synchronizedView
        (AttributeSet attributeSet) {
        if (attributeSet == null) {
            throw new NullPointerException();
        }
        return new SynchronizedAttributeSet(attributeSet);
    }

    /**
     * Creates a synchronized view of the given doc attribute set.
     *
     * <p>
     *  创建给定doc属性集的同步视图。
     * 
     * 
     * @param  attributeSet  Underlying doc attribute set.
     *
     * @return  Synchronized view of <CODE>attributeSet</CODE>.
     *
     * @exception  NullPointerException
     *     Thrown if <CODE>attributeSet</CODE> is null.
     */
    public static DocAttributeSet
        synchronizedView(DocAttributeSet attributeSet) {
        if (attributeSet == null) {
            throw new NullPointerException();
        }
        return new SynchronizedDocAttributeSet(attributeSet);
    }

    /**
     * Creates a synchronized view of the given print request attribute set.
     *
     * <p>
     *  创建给定打印请求属性集的同步视图。
     * 
     * 
     * @param  attributeSet  Underlying print request attribute set.
     *
     * @return  Synchronized view of <CODE>attributeSet</CODE>.
     *
     * @exception  NullPointerException
     *     Thrown if <CODE>attributeSet</CODE> is null.
     */
    public static PrintRequestAttributeSet
        synchronizedView(PrintRequestAttributeSet attributeSet) {
        if (attributeSet == null) {
            throw new NullPointerException();
        }
        return new SynchronizedPrintRequestAttributeSet(attributeSet);
    }

    /**
     * Creates a synchronized view of the given print job attribute set.
     *
     * <p>
     *  创建给定打印作业属性集的同步视图。
     * 
     * 
     * @param  attributeSet  Underlying print job attribute set.
     *
     * @return  Synchronized view of <CODE>attributeSet</CODE>.
     *
     * @exception  NullPointerException
     *     Thrown if <CODE>attributeSet</CODE> is null.
     */
    public static PrintJobAttributeSet
        synchronizedView(PrintJobAttributeSet attributeSet) {
        if (attributeSet == null) {
            throw new NullPointerException();
        }
        return new SynchronizedPrintJobAttributeSet(attributeSet);
    }

    /**
     * Creates a synchronized view of the given print service attribute set.
     *
     * <p>
     *  创建给定打印服务属性集的同步视图。
     * 
     * 
     * @param  attributeSet  Underlying print service attribute set.
     *
     * @return  Synchronized view of <CODE>attributeSet</CODE>.
     */
    public static PrintServiceAttributeSet
        synchronizedView(PrintServiceAttributeSet attributeSet) {
        if (attributeSet == null) {
            throw new NullPointerException();
        }
        return new SynchronizedPrintServiceAttributeSet(attributeSet);
    }


    /**
     * Verify that the given object is a {@link java.lang.Class Class} that
     * implements the given interface, which is assumed to be interface {@link
     * Attribute Attribute} or a subinterface thereof.
     *
     * <p>
     *  验证给定对象是实现给定接口的{@link java.lang.Class Class},它假定为interface {@link Attribute Attribute}或其子接口。
     * 
     * 
     * @param  object     Object to test.
     * @param  interfaceName  Interface the object must implement.
     *
     * @return  If <CODE>object</CODE> is a {@link java.lang.Class Class}
     *          that implements <CODE>interfaceName</CODE>,
     *          <CODE>object</CODE> is returned downcast to type {@link
     *          java.lang.Class Class}; otherwise an exception is thrown.
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <CODE>object</CODE> is null.
     * @exception  ClassCastException
     *     (unchecked exception) Thrown if <CODE>object</CODE> is not a
     *     {@link java.lang.Class Class} that implements
     *     <CODE>interfaceName</CODE>.
     */
    public static Class<?>
        verifyAttributeCategory(Object object, Class<?> interfaceName) {

        Class result = (Class) object;
        if (interfaceName.isAssignableFrom (result)) {
            return result;
        }
        else {
            throw new ClassCastException();
        }
    }

    /**
     * Verify that the given object is an instance of the given interface, which
     * is assumed to be interface {@link Attribute Attribute} or a subinterface
     * thereof.
     *
     * <p>
     *  验证给定对象是给定接口的一个实例,它假定为interface {@link Attribute Attribute}或其子接口。
     * 
     * 
     * @param  object     Object to test.
     * @param  interfaceName  Interface of which the object must be an instance.
     *
     * @return  If <CODE>object</CODE> is an instance of
     *          <CODE>interfaceName</CODE>, <CODE>object</CODE> is returned
     *          downcast to type {@link Attribute Attribute}; otherwise an
     *          exception is thrown.
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <CODE>object</CODE> is null.
     * @exception  ClassCastException
     *     (unchecked exception) Thrown if <CODE>object</CODE> is not an
     *     instance of <CODE>interfaceName</CODE>.
     */
    public static Attribute
        verifyAttributeValue(Object object, Class<?> interfaceName) {

        if (object == null) {
            throw new NullPointerException();
        }
        else if (interfaceName.isInstance (object)) {
            return (Attribute) object;
        } else {
            throw new ClassCastException();
        }
    }

    /**
     * Verify that the given attribute category object is equal to the
     * category of the given attribute value object. If so, this method
     * returns doing nothing. If not, this method throws an exception.
     *
     * <p>
     * 验证给定的属性类别对象是否等于给定属性值对象的类别。如果是这样,此方法返回什么也不做。如果不是,此方法抛出异常。
     * 
     * @param  category   Attribute category to test.
     * @param  attribute  Attribute value to test.
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if the <CODE>category</CODE> is
     *     null or if the <CODE>attribute</CODE> is null.
     * @exception  IllegalArgumentException
     *     (unchecked exception) Thrown if the <CODE>category</CODE> is not
     *     equal to the category of the <CODE>attribute</CODE>.
     */
    public static void
        verifyCategoryForValue(Class<?> category, Attribute attribute) {

        if (!category.equals (attribute.getCategory())) {
            throw new IllegalArgumentException();
        }
    }
}
