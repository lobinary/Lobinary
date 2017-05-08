/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2011, Oracle and/or its affiliates. All rights reserved.
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

package java.lang;

import java.io.Serializable;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;

/**
 * This is the common base class of all Java language enumeration types.
 *
 * More information about enums, including descriptions of the
 * implicitly declared methods synthesized by the compiler, can be
 * found in section 8.9 of
 * <cite>The Java&trade; Language Specification</cite>.
 *
 * <p> Note that when using an enumeration type as the type of a set
 * or as the type of the keys in a map, specialized and efficient
 * {@linkplain java.util.EnumSet set} and {@linkplain
 * java.util.EnumMap map} implementations are available.
 *
 * <p>
 *  这是所有Java语言枚举类型的公共基类。
 * 
 *  有关枚举的更多信息,包括由编译器合成的隐式声明方法的描述,可以在<cite> Java&trade;语言规范</cite>。
 * 
 *  <p>请注意,当使用枚举类型作为集合的类型或作为地图中键的类型时,专门和高效{@linkplain java.util.EnumSet set}和{@linkplain java.util.EnumMap map }
 * 实现是可用的。
 * 
 * 
 * @param <E> The enum type subclass
 * @author  Josh Bloch
 * @author  Neal Gafter
 * @see     Class#getEnumConstants()
 * @see     java.util.EnumSet
 * @see     java.util.EnumMap
 * @since   1.5
 */
public abstract class Enum<E extends Enum<E>>
        implements Comparable<E>, Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * The name of this enum constant, as declared in the enum declaration.
     * Most programmers should use the {@link #toString} method rather than
     * accessing this field.
     * <p>
     *  此枚举常量的名称,如枚举声明中声明的。大多数程序员应该使用{@link #toString}方法,而不是访问此字段。
     * 
     */
    private final String name;

    /**
     * Returns the name of this enum constant, exactly as declared in its
     * enum declaration.
     *
     * <b>Most programmers should use the {@link #toString} method in
     * preference to this one, as the toString method may return
     * a more user-friendly name.</b>  This method is designed primarily for
     * use in specialized situations where correctness depends on getting the
     * exact name, which will not vary from release to release.
     *
     * <p>
     *  返回此枚举常量的名称,与枚举声明中声明的名称完全相同。
     * 
     *  <b>大多数程序员应该优先使用{@link #toString}方法,因为toString方法可能返回一个更加用户友好的名称。
     * </b>此方法主要设计用于正确性取决于获取确切的名称,这将不会因版本而异。
     * 
     * 
     * @return the name of this enum constant
     */
    public final String name() {
        return name;
    }

    /**
     * The ordinal of this enumeration constant (its position
     * in the enum declaration, where the initial constant is assigned
     * an ordinal of zero).
     *
     * Most programmers will have no use for this field.  It is designed
     * for use by sophisticated enum-based data structures, such as
     * {@link java.util.EnumSet} and {@link java.util.EnumMap}.
     * <p>
     *  这个枚举常量的序数(它在枚举声明中的位置,其中初始常量被赋值为零的序数)。
     * 
     * 大多数程序员都没有用于此字段。它设计用于复杂的基于枚举的数据结构,例如{@link java.util.EnumSet}和{@link java.util.EnumMap}。
     * 
     */
    private final int ordinal;

    /**
     * Returns the ordinal of this enumeration constant (its position
     * in its enum declaration, where the initial constant is assigned
     * an ordinal of zero).
     *
     * Most programmers will have no use for this method.  It is
     * designed for use by sophisticated enum-based data structures, such
     * as {@link java.util.EnumSet} and {@link java.util.EnumMap}.
     *
     * <p>
     *  返回此枚举常量的序数(它在枚举声明中的位置,其中初始常量赋值为零的序数)。
     * 
     *  大多数程序员对这种方法没有用处。它设计用于复杂的基于枚举的数据结构,例如{@link java.util.EnumSet}和{@link java.util.EnumMap}。
     * 
     * 
     * @return the ordinal of this enumeration constant
     */
    public final int ordinal() {
        return ordinal;
    }

    /**
     * Sole constructor.  Programmers cannot invoke this constructor.
     * It is for use by code emitted by the compiler in response to
     * enum type declarations.
     *
     * <p>
     *  唯一构造函数。程序员不能调用此构造函数。它由编译器响应枚举类型声明时发出的代码使用。
     * 
     * 
     * @param name - The name of this enum constant, which is the identifier
     *               used to declare it.
     * @param ordinal - The ordinal of this enumeration constant (its position
     *         in the enum declaration, where the initial constant is assigned
     *         an ordinal of zero).
     */
    protected Enum(String name, int ordinal) {
        this.name = name;
        this.ordinal = ordinal;
    }

    /**
     * Returns the name of this enum constant, as contained in the
     * declaration.  This method may be overridden, though it typically
     * isn't necessary or desirable.  An enum type should override this
     * method when a more "programmer-friendly" string form exists.
     *
     * <p>
     *  返回此枚举常量的名称,如声明中所包含。该方法可以被覆盖,尽管它通常不是必需或不期望的。当存在更多的"程序员友好"字符串形式时,枚举类型应覆盖此方法。
     * 
     * 
     * @return the name of this enum constant
     */
    public String toString() {
        return name;
    }

    /**
     * Returns true if the specified object is equal to this
     * enum constant.
     *
     * <p>
     *  如果指定的对象等于此枚举常量,则返回true。
     * 
     * 
     * @param other the object to be compared for equality with this object.
     * @return  true if the specified object is equal to this
     *          enum constant.
     */
    public final boolean equals(Object other) {
        return this==other;
    }

    /**
     * Returns a hash code for this enum constant.
     *
     * <p>
     *  返回此枚举常量的哈希代码。
     * 
     * 
     * @return a hash code for this enum constant.
     */
    public final int hashCode() {
        return super.hashCode();
    }

    /**
     * Throws CloneNotSupportedException.  This guarantees that enums
     * are never cloned, which is necessary to preserve their "singleton"
     * status.
     *
     * <p>
     *  引发CloneNotSupportedException。这保证枚举永远不被克隆,这是保持他们的"singleton"状态所必需的。
     * 
     * 
     * @return (never returns)
     */
    protected final Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    /**
     * Compares this enum with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * Enum constants are only comparable to other enum constants of the
     * same enum type.  The natural order implemented by this
     * method is the order in which the constants are declared.
     * <p>
     *  将此枚举与指定的对象进行比较以进行排序。返回负整数,零或正整数,因为此对象小于,等于或大于指定的对象。
     * 
     * 枚举常量仅与同一枚举类型的其他枚举常量相当。这种方法实现的自然顺序是常量的声明顺序。
     * 
     */
    public final int compareTo(E o) {
        Enum<?> other = (Enum<?>)o;
        Enum<E> self = this;
        if (self.getClass() != other.getClass() && // optimization
            self.getDeclaringClass() != other.getDeclaringClass())
            throw new ClassCastException();
        return self.ordinal - other.ordinal;
    }

    /**
     * Returns the Class object corresponding to this enum constant's
     * enum type.  Two enum constants e1 and  e2 are of the
     * same enum type if and only if
     *   e1.getDeclaringClass() == e2.getDeclaringClass().
     * (The value returned by this method may differ from the one returned
     * by the {@link Object#getClass} method for enum constants with
     * constant-specific class bodies.)
     *
     * <p>
     *  返回与此枚举常量的枚举类型对应的Class对象。当且仅当e1.getDeclaringClass()== e2.getDeclaringClass()时,两个枚举常量e1和e2是相同的枚举类型。
     *  (此方法返回的值可能与{@link Object#getClass}方法为带有常量特定类主体的枚举常量返回的值不同。)。
     * 
     * 
     * @return the Class object corresponding to this enum constant's
     *     enum type
     */
    @SuppressWarnings("unchecked")
    public final Class<E> getDeclaringClass() {
        Class<?> clazz = getClass();
        Class<?> zuper = clazz.getSuperclass();
        return (zuper == Enum.class) ? (Class<E>)clazz : (Class<E>)zuper;
    }

    /**
     * Returns the enum constant of the specified enum type with the
     * specified name.  The name must match exactly an identifier used
     * to declare an enum constant in this type.  (Extraneous whitespace
     * characters are not permitted.)
     *
     * <p>Note that for a particular enum type {@code T}, the
     * implicitly declared {@code public static T valueOf(String)}
     * method on that enum may be used instead of this method to map
     * from a name to the corresponding enum constant.  All the
     * constants of an enum type can be obtained by calling the
     * implicit {@code public static T[] values()} method of that
     * type.
     *
     * <p>
     *  返回具有指定名称的指定枚举类型的枚举常量。名称必须与用于在此类型中声明枚举常量的标识符完全匹配。 (不允许使用无关的空格字符。)
     * 
     *  <p>请注意,对于特定的枚举类型{@code T},可以使用该枚举上的隐式声明的{@code public static T valueOf(String)}方法,而不是此方法来从名称映射到相应的枚举
     * 不变。
     * 枚举类型的所有常量可以通过调用该类型的隐式{@code public static T [] values()}方法获得。
     * 
     * 
     * @param <T> The enum type whose constant is to be returned
     * @param enumType the {@code Class} object of the enum type from which
     *      to return a constant
     * @param name the name of the constant to return
     * @return the enum constant of the specified enum type with the
     *      specified name
     * @throws IllegalArgumentException if the specified enum type has
     *         no constant with the specified name, or the specified
     *         class object does not represent an enum type
     * @throws NullPointerException if {@code enumType} or {@code name}
     *         is null
     * @since 1.5
     */
    public static <T extends Enum<T>> T valueOf(Class<T> enumType,
                                                String name) {
        T result = enumType.enumConstantDirectory().get(name);
        if (result != null)
            return result;
        if (name == null)
            throw new NullPointerException("Name is null");
        throw new IllegalArgumentException(
            "No enum constant " + enumType.getCanonicalName() + "." + name);
    }

    /**
     * enum classes cannot have finalize methods.
     * <p>
     */
    protected final void finalize() { }

    /**
     * prevent default deserialization
     * <p>
     *  枚举类不能有finalize方法。
     * 
     */
    private void readObject(ObjectInputStream in) throws IOException,
        ClassNotFoundException {
        throw new InvalidObjectException("can't deserialize enum");
    }

    private void readObjectNoData() throws ObjectStreamException {
        throw new InvalidObjectException("can't deserialize enum");
    }
}
