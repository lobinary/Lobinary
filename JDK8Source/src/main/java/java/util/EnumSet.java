/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

import sun.misc.SharedSecrets;

/**
 * A specialized {@link Set} implementation for use with enum types.  All of
 * the elements in an enum set must come from a single enum type that is
 * specified, explicitly or implicitly, when the set is created.  Enum sets
 * are represented internally as bit vectors.  This representation is
 * extremely compact and efficient. The space and time performance of this
 * class should be good enough to allow its use as a high-quality, typesafe
 * alternative to traditional <tt>int</tt>-based "bit flags."  Even bulk
 * operations (such as <tt>containsAll</tt> and <tt>retainAll</tt>) should
 * run very quickly if their argument is also an enum set.
 *
 * <p>The iterator returned by the <tt>iterator</tt> method traverses the
 * elements in their <i>natural order</i> (the order in which the enum
 * constants are declared).  The returned iterator is <i>weakly
 * consistent</i>: it will never throw {@link ConcurrentModificationException}
 * and it may or may not show the effects of any modifications to the set that
 * occur while the iteration is in progress.
 *
 * <p>Null elements are not permitted.  Attempts to insert a null element
 * will throw {@link NullPointerException}.  Attempts to test for the
 * presence of a null element or to remove one will, however, function
 * properly.
 *
 * <P>Like most collection implementations, <tt>EnumSet</tt> is not
 * synchronized.  If multiple threads access an enum set concurrently, and at
 * least one of the threads modifies the set, it should be synchronized
 * externally.  This is typically accomplished by synchronizing on some
 * object that naturally encapsulates the enum set.  If no such object exists,
 * the set should be "wrapped" using the {@link Collections#synchronizedSet}
 * method.  This is best done at creation time, to prevent accidental
 * unsynchronized access:
 *
 * <pre>
 * Set&lt;MyEnum&gt; s = Collections.synchronizedSet(EnumSet.noneOf(MyEnum.class));
 * </pre>
 *
 * <p>Implementation note: All basic operations execute in constant time.
 * They are likely (though not guaranteed) to be much faster than their
 * {@link HashSet} counterparts.  Even bulk operations execute in
 * constant time if their argument is also an enum set.
 *
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * <p>
 *  专用的{@link Set}实现,用于枚举类型。枚举集中的所有元素必须来自在创建集时显式或隐式指定的单个枚举类型。枚举集在内部表示为位向量。这种表示是非常紧凑和有效的。
 * 这个类的空间和时间性能应该足够好,以允许它作为传统的基于<tt> int </tt>的"位标志"的高质量,类型安全的替代。
 * 即使批量操作(例如<tt> containsAll </tt>和<tt> retainAll </tt>),如果它们的参数也是枚举集合,它们应该运行得非常快。
 * 
 *  <p> <tt> iterator </tt>方法返回的迭代器以它们的<i>自然顺序</i>(枚举常量的声明顺序)遍历元素。
 * 返回的迭代器<i>弱一致</i>：它永远不会抛出{@link ConcurrentModificationException},它可能或可能不显示迭代正在进行时发生的任何对集合的修改的影响。
 * 
 *  <p>不允许使用零元素。尝试插入空元素将抛出{@link NullPointerException}。然而,尝试测试空元素的存在或删除一个将正常工作。
 * 
 * <P>与大多数集合实现一样,<tt> EnumSet </tt>不同步。如果多个线程并发访问枚举集,并且至少有一个线程修改了集合,那么它应该在外部同步。
 * 这通常通过在自然地封装枚举集合的某个对象上同步来实现。如果不存在这样的对象,则应该使用{@link Collections#synchronizeSet}方法来"包装"该集合。
 * 这最好在创建时完成,以防止意外的不同步访问：。
 * 
 * <pre>
 *  设置&lt; MyEnum&gt; s = Collections.synchronizedSet(EnumSet.noneOf(MyEnum.class));
 * </pre>
 * 
 *  <p>实现注释：所有基本操作在常量时间执行。他们很可能(虽然不能保证)比他们的{@link HashSet}同行。即使批量操作在常量时间执行,如果它们的参数也是枚举集合。
 * 
 *  <p>此类是的成员
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 *  Java集合框架</a>。
 * 
 * 
 * @author Josh Bloch
 * @since 1.5
 * @see EnumMap
 * @serial exclude
 */
public abstract class EnumSet<E extends Enum<E>> extends AbstractSet<E>
    implements Cloneable, java.io.Serializable
{
    /**
     * The class of all the elements of this set.
     * <p>
     *  这个集合的所有元素的类。
     * 
     */
    final Class<E> elementType;

    /**
     * All of the values comprising T.  (Cached for performance.)
     * <p>
     *  所有的值包括T.(Cached for performance。)
     * 
     */
    final Enum<?>[] universe;

    private static Enum<?>[] ZERO_LENGTH_ENUM_ARRAY = new Enum<?>[0];

    EnumSet(Class<E>elementType, Enum<?>[] universe) {
        this.elementType = elementType;
        this.universe    = universe;
    }

    /**
     * Creates an empty enum set with the specified element type.
     *
     * <p>
     *  使用指定的元素类型创建空枚举集。
     * 
     * 
     * @param <E> The class of the elements in the set
     * @param elementType the class object of the element type for this enum
     *     set
     * @return An empty enum set of the specified type.
     * @throws NullPointerException if <tt>elementType</tt> is null
     */
    public static <E extends Enum<E>> EnumSet<E> noneOf(Class<E> elementType) {
        Enum<?>[] universe = getUniverse(elementType);
        if (universe == null)
            throw new ClassCastException(elementType + " not an enum");

        if (universe.length <= 64)
            return new RegularEnumSet<>(elementType, universe);
        else
            return new JumboEnumSet<>(elementType, universe);
    }

    /**
     * Creates an enum set containing all of the elements in the specified
     * element type.
     *
     * <p>
     *  创建包含指定元素类型中所有元素的枚举集。
     * 
     * 
     * @param <E> The class of the elements in the set
     * @param elementType the class object of the element type for this enum
     *     set
     * @return An enum set containing all the elements in the specified type.
     * @throws NullPointerException if <tt>elementType</tt> is null
     */
    public static <E extends Enum<E>> EnumSet<E> allOf(Class<E> elementType) {
        EnumSet<E> result = noneOf(elementType);
        result.addAll();
        return result;
    }

    /**
     * Adds all of the elements from the appropriate enum type to this enum
     * set, which is empty prior to the call.
     * <p>
     *  将相应枚举类型的所有元素添加到此枚举集合,该枚举集合在调用之前为空。
     * 
     */
    abstract void addAll();

    /**
     * Creates an enum set with the same element type as the specified enum
     * set, initially containing the same elements (if any).
     *
     * <p>
     *  创建与指定的枚举集相同的元素类型的枚举集,最初包含相同的元素(如果有)。
     * 
     * 
     * @param <E> The class of the elements in the set
     * @param s the enum set from which to initialize this enum set
     * @return A copy of the specified enum set.
     * @throws NullPointerException if <tt>s</tt> is null
     */
    public static <E extends Enum<E>> EnumSet<E> copyOf(EnumSet<E> s) {
        return s.clone();
    }

    /**
     * Creates an enum set initialized from the specified collection.  If
     * the specified collection is an <tt>EnumSet</tt> instance, this static
     * factory method behaves identically to {@link #copyOf(EnumSet)}.
     * Otherwise, the specified collection must contain at least one element
     * (in order to determine the new enum set's element type).
     *
     * <p>
     * 创建从指定集合初始化的枚举集。如果指定的集合是一个<tt> EnumSet </tt>实例,此静态工厂方法的行为与{@link #copyOf(EnumSet)}相同。
     * 否则,指定的集合必须包含至少一个元素(以便确定新的枚举集的元素类型)。
     * 
     * 
     * @param <E> The class of the elements in the collection
     * @param c the collection from which to initialize this enum set
     * @return An enum set initialized from the given collection.
     * @throws IllegalArgumentException if <tt>c</tt> is not an
     *     <tt>EnumSet</tt> instance and contains no elements
     * @throws NullPointerException if <tt>c</tt> is null
     */
    public static <E extends Enum<E>> EnumSet<E> copyOf(Collection<E> c) {
        if (c instanceof EnumSet) {
            return ((EnumSet<E>)c).clone();
        } else {
            if (c.isEmpty())
                throw new IllegalArgumentException("Collection is empty");
            Iterator<E> i = c.iterator();
            E first = i.next();
            EnumSet<E> result = EnumSet.of(first);
            while (i.hasNext())
                result.add(i.next());
            return result;
        }
    }

    /**
     * Creates an enum set with the same element type as the specified enum
     * set, initially containing all the elements of this type that are
     * <i>not</i> contained in the specified set.
     *
     * <p>
     *  创建与指定枚举集相同的元素类型的枚举集,最初包含指定集中<i>不</i>的所有此类型的元素。
     * 
     * 
     * @param <E> The class of the elements in the enum set
     * @param s the enum set from whose complement to initialize this enum set
     * @return The complement of the specified set in this set
     * @throws NullPointerException if <tt>s</tt> is null
     */
    public static <E extends Enum<E>> EnumSet<E> complementOf(EnumSet<E> s) {
        EnumSet<E> result = copyOf(s);
        result.complement();
        return result;
    }

    /**
     * Creates an enum set initially containing the specified element.
     *
     * Overloadings of this method exist to initialize an enum set with
     * one through five elements.  A sixth overloading is provided that
     * uses the varargs feature.  This overloading may be used to create
     * an enum set initially containing an arbitrary number of elements, but
     * is likely to run slower than the overloadings that do not use varargs.
     *
     * <p>
     *  创建最初包含指定元素的枚举集。
     * 
     *  此方法的重载存在以初始化具有一到五个元素的枚举集合。提供了使用varargs功能的第六个重载。此重载可用于创建最初包含任意数量的元素的枚举集合,但是可能比不使用varargs的重载运行得慢。
     * 
     * 
     * @param <E> The class of the specified element and of the set
     * @param e the element that this set is to contain initially
     * @throws NullPointerException if <tt>e</tt> is null
     * @return an enum set initially containing the specified element
     */
    public static <E extends Enum<E>> EnumSet<E> of(E e) {
        EnumSet<E> result = noneOf(e.getDeclaringClass());
        result.add(e);
        return result;
    }

    /**
     * Creates an enum set initially containing the specified elements.
     *
     * Overloadings of this method exist to initialize an enum set with
     * one through five elements.  A sixth overloading is provided that
     * uses the varargs feature.  This overloading may be used to create
     * an enum set initially containing an arbitrary number of elements, but
     * is likely to run slower than the overloadings that do not use varargs.
     *
     * <p>
     *  创建最初包含指定元素的枚举集。
     * 
     *  此方法的重载存在以初始化具有一到五个元素的枚举集合。提供了使用varargs功能的第六个重载。此重载可用于创建最初包含任意数量的元素的枚举集合,但是可能比不使用varargs的重载运行得慢。
     * 
     * 
     * @param <E> The class of the parameter elements and of the set
     * @param e1 an element that this set is to contain initially
     * @param e2 another element that this set is to contain initially
     * @throws NullPointerException if any parameters are null
     * @return an enum set initially containing the specified elements
     */
    public static <E extends Enum<E>> EnumSet<E> of(E e1, E e2) {
        EnumSet<E> result = noneOf(e1.getDeclaringClass());
        result.add(e1);
        result.add(e2);
        return result;
    }

    /**
     * Creates an enum set initially containing the specified elements.
     *
     * Overloadings of this method exist to initialize an enum set with
     * one through five elements.  A sixth overloading is provided that
     * uses the varargs feature.  This overloading may be used to create
     * an enum set initially containing an arbitrary number of elements, but
     * is likely to run slower than the overloadings that do not use varargs.
     *
     * <p>
     *  创建最初包含指定元素的枚举集。
     * 
     * 此方法的重载存在以初始化具有一到五个元素的枚举集合。提供了使用varargs功能的第六个重载。此重载可用于创建最初包含任意数量的元素的枚举集合,但是可能比不使用varargs的重载运行得慢。
     * 
     * 
     * @param <E> The class of the parameter elements and of the set
     * @param e1 an element that this set is to contain initially
     * @param e2 another element that this set is to contain initially
     * @param e3 another element that this set is to contain initially
     * @throws NullPointerException if any parameters are null
     * @return an enum set initially containing the specified elements
     */
    public static <E extends Enum<E>> EnumSet<E> of(E e1, E e2, E e3) {
        EnumSet<E> result = noneOf(e1.getDeclaringClass());
        result.add(e1);
        result.add(e2);
        result.add(e3);
        return result;
    }

    /**
     * Creates an enum set initially containing the specified elements.
     *
     * Overloadings of this method exist to initialize an enum set with
     * one through five elements.  A sixth overloading is provided that
     * uses the varargs feature.  This overloading may be used to create
     * an enum set initially containing an arbitrary number of elements, but
     * is likely to run slower than the overloadings that do not use varargs.
     *
     * <p>
     *  创建最初包含指定元素的枚举集。
     * 
     *  此方法的重载存在以初始化具有一到五个元素的枚举集合。提供了使用varargs功能的第六个重载。此重载可用于创建最初包含任意数量的元素的枚举集合,但是可能比不使用varargs的重载运行得慢。
     * 
     * 
     * @param <E> The class of the parameter elements and of the set
     * @param e1 an element that this set is to contain initially
     * @param e2 another element that this set is to contain initially
     * @param e3 another element that this set is to contain initially
     * @param e4 another element that this set is to contain initially
     * @throws NullPointerException if any parameters are null
     * @return an enum set initially containing the specified elements
     */
    public static <E extends Enum<E>> EnumSet<E> of(E e1, E e2, E e3, E e4) {
        EnumSet<E> result = noneOf(e1.getDeclaringClass());
        result.add(e1);
        result.add(e2);
        result.add(e3);
        result.add(e4);
        return result;
    }

    /**
     * Creates an enum set initially containing the specified elements.
     *
     * Overloadings of this method exist to initialize an enum set with
     * one through five elements.  A sixth overloading is provided that
     * uses the varargs feature.  This overloading may be used to create
     * an enum set initially containing an arbitrary number of elements, but
     * is likely to run slower than the overloadings that do not use varargs.
     *
     * <p>
     *  创建最初包含指定元素的枚举集。
     * 
     *  此方法的重载存在以初始化具有一到五个元素的枚举集合。提供了使用varargs功能的第六个重载。此重载可用于创建最初包含任意数量的元素的枚举集合,但是可能比不使用varargs的重载运行得慢。
     * 
     * 
     * @param <E> The class of the parameter elements and of the set
     * @param e1 an element that this set is to contain initially
     * @param e2 another element that this set is to contain initially
     * @param e3 another element that this set is to contain initially
     * @param e4 another element that this set is to contain initially
     * @param e5 another element that this set is to contain initially
     * @throws NullPointerException if any parameters are null
     * @return an enum set initially containing the specified elements
     */
    public static <E extends Enum<E>> EnumSet<E> of(E e1, E e2, E e3, E e4,
                                                    E e5)
    {
        EnumSet<E> result = noneOf(e1.getDeclaringClass());
        result.add(e1);
        result.add(e2);
        result.add(e3);
        result.add(e4);
        result.add(e5);
        return result;
    }

    /**
     * Creates an enum set initially containing the specified elements.
     * This factory, whose parameter list uses the varargs feature, may
     * be used to create an enum set initially containing an arbitrary
     * number of elements, but it is likely to run slower than the overloadings
     * that do not use varargs.
     *
     * <p>
     *  创建最初包含指定元素的枚举集。此工厂的参数列表使用varargs功能,可用于创建最初包含任意数量元素的枚举集,但它可能比不使用varargs的重载运行得慢。
     * 
     * 
     * @param <E> The class of the parameter elements and of the set
     * @param first an element that the set is to contain initially
     * @param rest the remaining elements the set is to contain initially
     * @throws NullPointerException if any of the specified elements are null,
     *     or if <tt>rest</tt> is null
     * @return an enum set initially containing the specified elements
     */
    @SafeVarargs
    public static <E extends Enum<E>> EnumSet<E> of(E first, E... rest) {
        EnumSet<E> result = noneOf(first.getDeclaringClass());
        result.add(first);
        for (E e : rest)
            result.add(e);
        return result;
    }

    /**
     * Creates an enum set initially containing all of the elements in the
     * range defined by the two specified endpoints.  The returned set will
     * contain the endpoints themselves, which may be identical but must not
     * be out of order.
     *
     * <p>
     * 创建枚举集,最初包含由两个指定端点定义的范围内的所有元素。返回的集合将包含端点本身,它们可以是相同的,但不能是无序的。
     * 
     * 
     * @param <E> The class of the parameter elements and of the set
     * @param from the first element in the range
     * @param to the last element in the range
     * @throws NullPointerException if {@code from} or {@code to} are null
     * @throws IllegalArgumentException if {@code from.compareTo(to) > 0}
     * @return an enum set initially containing all of the elements in the
     *         range defined by the two specified endpoints
     */
    public static <E extends Enum<E>> EnumSet<E> range(E from, E to) {
        if (from.compareTo(to) > 0)
            throw new IllegalArgumentException(from + " > " + to);
        EnumSet<E> result = noneOf(from.getDeclaringClass());
        result.addRange(from, to);
        return result;
    }

    /**
     * Adds the specified range to this enum set, which is empty prior
     * to the call.
     * <p>
     *  将指定范围添加到此枚举集,在调用之前为空。
     * 
     */
    abstract void addRange(E from, E to);

    /**
     * Returns a copy of this set.
     *
     * <p>
     *  返回此集合的副本。
     * 
     * 
     * @return a copy of this set
     */
    @SuppressWarnings("unchecked")
    public EnumSet<E> clone() {
        try {
            return (EnumSet<E>) super.clone();
        } catch(CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Complements the contents of this enum set.
     * <p>
     *  补充此枚举集的内容。
     * 
     */
    abstract void complement();

    /**
     * Throws an exception if e is not of the correct type for this enum set.
     * <p>
     *  如果e不是此枚举集合的正确类型,则抛出异常。
     * 
     */
    final void typeCheck(E e) {
        Class<?> eClass = e.getClass();
        if (eClass != elementType && eClass.getSuperclass() != elementType)
            throw new ClassCastException(eClass + " != " + elementType);
    }

    /**
     * Returns all of the values comprising E.
     * The result is uncloned, cached, and shared by all callers.
     * <p>
     *  返回包含E的所有值。结果未克隆,缓存,并由所有调用者共享。
     * 
     */
    private static <E extends Enum<E>> E[] getUniverse(Class<E> elementType) {
        return SharedSecrets.getJavaLangAccess()
                                        .getEnumConstantsShared(elementType);
    }

    /**
     * This class is used to serialize all EnumSet instances, regardless of
     * implementation type.  It captures their "logical contents" and they
     * are reconstructed using public static factories.  This is necessary
     * to ensure that the existence of a particular implementation type is
     * an implementation detail.
     *
     * <p>
     *  此类用于序列化所有EnumSet实例,而不考虑实现类型。它捕获他们的"逻辑内容",并使用公共静态工厂重构。这是必要的,以确保特定实现类型的存在是实现细节。
     * 
     * 
     * @serial include
     */
    private static class SerializationProxy <E extends Enum<E>>
        implements java.io.Serializable
    {
        /**
         * The element type of this enum set.
         *
         * <p>
         *  此枚举的元素类型集。
         * 
         * 
         * @serial
         */
        private final Class<E> elementType;

        /**
         * The elements contained in this enum set.
         *
         * <p>
         *  此枚举中包含的元素。
         * 
         * @serial
         */
        private final Enum<?>[] elements;

        SerializationProxy(EnumSet<E> set) {
            elementType = set.elementType;
            elements = set.toArray(ZERO_LENGTH_ENUM_ARRAY);
        }

        // instead of cast to E, we should perhaps use elementType.cast()
        // to avoid injection of forged stream, but it will slow the implementation
        @SuppressWarnings("unchecked")
        private Object readResolve() {
            EnumSet<E> result = EnumSet.noneOf(elementType);
            for (Enum<?> e : elements)
                result.add((E)e);
            return result;
        }

        private static final long serialVersionUID = 362491234563181265L;
    }

    Object writeReplace() {
        return new SerializationProxy<>(this);
    }

    // readObject method for the serialization proxy pattern
    // See Effective Java, Second Ed., Item 78.
    private void readObject(java.io.ObjectInputStream stream)
        throws java.io.InvalidObjectException {
        throw new java.io.InvalidObjectException("Proxy required");
    }
}
