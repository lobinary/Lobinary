/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1994, 2013, Oracle and/or its affiliates. All rights reserved.
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

/**
 * The Boolean class wraps a value of the primitive type
 * {@code boolean} in an object. An object of type
 * {@code Boolean} contains a single field whose type is
 * {@code boolean}.
 * <p>
 * In addition, this class provides many methods for
 * converting a {@code boolean} to a {@code String} and a
 * {@code String} to a {@code boolean}, as well as other
 * constants and methods useful when dealing with a
 * {@code boolean}.
 *
 * <p>
 *  布尔类将原始类型{@code boolean}的值封装在对象中。类型为{@code Boolean}的对象包含一个类型为{@code boolean}的单个字段。
 * <p>
 *  此外,此类提供了许多将{@code boolean}转换为{@code String}和{@code String}转换为{@code boolean}的方法,以及其他常量和方法,用于处理{@code boolean}
 * 。
 * 
 * 
 * @author  Arthur van Hoff
 * @since   JDK1.0
 */
public final class Boolean implements java.io.Serializable,
                                      Comparable<Boolean>
{
    /**
     * The {@code Boolean} object corresponding to the primitive
     * value {@code true}.
     * <p>
     *  对应于原始值{@code true}的{@code Boolean}对象。
     * 
     */
    public static final Boolean TRUE = new Boolean(true);

    /**
     * The {@code Boolean} object corresponding to the primitive
     * value {@code false}.
     * <p>
     *  对应于原始值{@code false}的{@code Boolean}对象。
     * 
     */
    public static final Boolean FALSE = new Boolean(false);

    /**
     * The Class object representing the primitive type boolean.
     *
     * <p>
     *  表示原始类型布尔的Class对象。
     * 
     * 
     * @since   JDK1.1
     */
    @SuppressWarnings("unchecked")
    public static final Class<Boolean> TYPE = (Class<Boolean>) Class.getPrimitiveClass("boolean");

    /**
     * The value of the Boolean.
     *
     * <p>
     *  布尔值。
     * 
     * 
     * @serial
     */
    private final boolean value;

    /** use serialVersionUID from JDK 1.0.2 for interoperability */
    private static final long serialVersionUID = -3665804199014368530L;

    /**
     * Allocates a {@code Boolean} object representing the
     * {@code value} argument.
     *
     * <p><b>Note: It is rarely appropriate to use this constructor.
     * Unless a <i>new</i> instance is required, the static factory
     * {@link #valueOf(boolean)} is generally a better choice. It is
     * likely to yield significantly better space and time performance.</b>
     *
     * <p>
     *  分配表示{@code value}参数的{@code Boolean}对象。
     * 
     *  <p> <b>注意：使用此构造函数很少适合。除非需要<i>新</i>实例,所以静态工厂{@link #valueOf(boolean)}通常是更好的选择。它可能会产生明显更好的空间和时间性能。
     * </b>。
     * 
     * 
     * @param   value   the value of the {@code Boolean}.
     */
    public Boolean(boolean value) {
        this.value = value;
    }

    /**
     * Allocates a {@code Boolean} object representing the value
     * {@code true} if the string argument is not {@code null}
     * and is equal, ignoring case, to the string {@code "true"}.
     * Otherwise, allocate a {@code Boolean} object representing the
     * value {@code false}. Examples:<p>
     * {@code new Boolean("True")} produces a {@code Boolean} object
     * that represents {@code true}.<br>
     * {@code new Boolean("yes")} produces a {@code Boolean} object
     * that represents {@code false}.
     *
     * <p>
     * 如果字符串参数不是{@code null},并且忽略大小写,则分配一个表示值{@code true}的{@code Boolean}对象到字符串{@code"true"}。
     * 否则,分配一个表示值{@code false}的{@code Boolean}对象。
     * 示例：<p> {@code new Boolean("True")}生成代表{@code true}的{@code Boolean}对象。
     * <br> {@code new Boolean("yes")}代码Boolean}对象,表示{@code false}。
     * 
     * 
     * @param   s   the string to be converted to a {@code Boolean}.
     */
    public Boolean(String s) {
        this(parseBoolean(s));
    }

    /**
     * Parses the string argument as a boolean.  The {@code boolean}
     * returned represents the value {@code true} if the string argument
     * is not {@code null} and is equal, ignoring case, to the string
     * {@code "true"}. <p>
     * Example: {@code Boolean.parseBoolean("True")} returns {@code true}.<br>
     * Example: {@code Boolean.parseBoolean("yes")} returns {@code false}.
     *
     * <p>
     *  将string参数解析为布尔值。如果字符串参数不是{@code null},并且等于字符串{@code"true"},则返回的{@code boolean}表示值{@code true}。
     *  <p>示例：{@code Boolean.parseBoolean("True")}返回{@code true}。
     * <br>示例：{@code Boolean.parseBoolean("yes")}返回{@code false}。
     * 
     * 
     * @param      s   the {@code String} containing the boolean
     *                 representation to be parsed
     * @return     the boolean represented by the string argument
     * @since 1.5
     */
    public static boolean parseBoolean(String s) {
        return ((s != null) && s.equalsIgnoreCase("true"));
    }

    /**
     * Returns the value of this {@code Boolean} object as a boolean
     * primitive.
     *
     * <p>
     *  将此{@code Boolean}对象的值作为布尔基元返回。
     * 
     * 
     * @return  the primitive {@code boolean} value of this object.
     */
    public boolean booleanValue() {
        return value;
    }

    /**
     * Returns a {@code Boolean} instance representing the specified
     * {@code boolean} value.  If the specified {@code boolean} value
     * is {@code true}, this method returns {@code Boolean.TRUE};
     * if it is {@code false}, this method returns {@code Boolean.FALSE}.
     * If a new {@code Boolean} instance is not required, this method
     * should generally be used in preference to the constructor
     * {@link #Boolean(boolean)}, as this method is likely to yield
     * significantly better space and time performance.
     *
     * <p>
     *  返回表示指定的{@code boolean}值的{@code Boolean}实例。
     * 如果指定的{@code boolean}值为{@code true},则此方法返回{@code Boolean.TRUE};如果是{@code false},此方法返回{@code Boolean.FALSE}
     * 。
     *  返回表示指定的{@code boolean}值的{@code Boolean}实例。
     * 如果不需要新的{@code Boolean}实例,通常应优先使用构造函数{@link #Boolean(boolean)},因为此方法可能会产生明显更好的空间和时间性能。
     * 
     * 
     * @param  b a boolean value.
     * @return a {@code Boolean} instance representing {@code b}.
     * @since  1.4
     */
    public static Boolean valueOf(boolean b) {
        return (b ? TRUE : FALSE);
    }

    /**
     * Returns a {@code Boolean} with a value represented by the
     * specified string.  The {@code Boolean} returned represents a
     * true value if the string argument is not {@code null}
     * and is equal, ignoring case, to the string {@code "true"}.
     *
     * <p>
     * 返回具有由指定字符串表示的值的{@code Boolean}。如果字符串参数不是{@code null},并且等于字符串{@code"true"},则返回的{@code Boolean}表示一个真值。
     * 
     * 
     * @param   s   a string.
     * @return  the {@code Boolean} value represented by the string.
     */
    public static Boolean valueOf(String s) {
        return parseBoolean(s) ? TRUE : FALSE;
    }

    /**
     * Returns a {@code String} object representing the specified
     * boolean.  If the specified boolean is {@code true}, then
     * the string {@code "true"} will be returned, otherwise the
     * string {@code "false"} will be returned.
     *
     * <p>
     *  返回表示指定布尔值的{@code String}对象。如果指定的布尔值是{@code true},那么将返回字符串{@code"true"},否则将返回字符串{@code"false"}。
     * 
     * 
     * @param b the boolean to be converted
     * @return the string representation of the specified {@code boolean}
     * @since 1.4
     */
    public static String toString(boolean b) {
        return b ? "true" : "false";
    }

    /**
     * Returns a {@code String} object representing this Boolean's
     * value.  If this object represents the value {@code true},
     * a string equal to {@code "true"} is returned. Otherwise, a
     * string equal to {@code "false"} is returned.
     *
     * <p>
     *  返回表示此布尔值的{@code String}对象。如果此对象表示值{@code true},则返回等于{@code"true"}的字符串。否则,返回一个等于{@code"false"}的字符串。
     * 
     * 
     * @return  a string representation of this object.
     */
    public String toString() {
        return value ? "true" : "false";
    }

    /**
     * Returns a hash code for this {@code Boolean} object.
     *
     * <p>
     *  返回此{@code Boolean}对象的哈希代码。
     * 
     * 
     * @return  the integer {@code 1231} if this object represents
     * {@code true}; returns the integer {@code 1237} if this
     * object represents {@code false}.
     */
    @Override
    public int hashCode() {
        return Boolean.hashCode(value);
    }

    /**
     * Returns a hash code for a {@code boolean} value; compatible with
     * {@code Boolean.hashCode()}.
     *
     * <p>
     *  返回{@code boolean}值的哈希码;兼容{@code Boolean.hashCode()}。
     * 
     * 
     * @param value the value to hash
     * @return a hash code value for a {@code boolean} value.
     * @since 1.8
     */
    public static int hashCode(boolean value) {
        return value ? 1231 : 1237;
    }

   /**
     * Returns {@code true} if and only if the argument is not
     * {@code null} and is a {@code Boolean} object that
     * represents the same {@code boolean} value as this object.
     *
     * <p>
     *  当且仅当参数不是{@code null}并且是一个代表与此对象相同的{@code boolean}值的{@code Boolean}对象时,返回{@code true}。
     * 
     * 
     * @param   obj   the object to compare with.
     * @return  {@code true} if the Boolean objects represent the
     *          same value; {@code false} otherwise.
     */
    public boolean equals(Object obj) {
        if (obj instanceof Boolean) {
            return value == ((Boolean)obj).booleanValue();
        }
        return false;
    }

    /**
     * Returns {@code true} if and only if the system property
     * named by the argument exists and is equal to the string
     * {@code "true"}. (Beginning with version 1.0.2 of the
     * Java<small><sup>TM</sup></small> platform, the test of
     * this string is case insensitive.) A system property is accessible
     * through {@code getProperty}, a method defined by the
     * {@code System} class.
     * <p>
     * If there is no property with the specified name, or if the specified
     * name is empty or null, then {@code false} is returned.
     *
     * <p>
     *  当且仅当由参数命名的系统属性存在且等于字符串{@code"true"}时,返回{@code true}。
     *  (从Java <small> <sup> TM </sup> </small>平台的版本1.0.2开始,此字符串的测试不区分大小写。
     * )系统属性可通过{@code getProperty}方法由{@code System}类定义。
     * <p>
     *  如果没有指定名称的属性,或者指定的名称为空或为null,则返回{@code false}。
     * 
     * 
     * @param   name   the system property name.
     * @return  the {@code boolean} value of the system property.
     * @throws  SecurityException for the same reasons as
     *          {@link System#getProperty(String) System.getProperty}
     * @see     java.lang.System#getProperty(java.lang.String)
     * @see     java.lang.System#getProperty(java.lang.String, java.lang.String)
     */
    public static boolean getBoolean(String name) {
        boolean result = false;
        try {
            result = parseBoolean(System.getProperty(name));
        } catch (IllegalArgumentException | NullPointerException e) {
        }
        return result;
    }

    /**
     * Compares this {@code Boolean} instance with another.
     *
     * <p>
     * 将此{@code Boolean}实例与另一个实例进行比较。
     * 
     * 
     * @param   b the {@code Boolean} instance to be compared
     * @return  zero if this object represents the same boolean value as the
     *          argument; a positive value if this object represents true
     *          and the argument represents false; and a negative value if
     *          this object represents false and the argument represents true
     * @throws  NullPointerException if the argument is {@code null}
     * @see     Comparable
     * @since  1.5
     */
    public int compareTo(Boolean b) {
        return compare(this.value, b.value);
    }

    /**
     * Compares two {@code boolean} values.
     * The value returned is identical to what would be returned by:
     * <pre>
     *    Boolean.valueOf(x).compareTo(Boolean.valueOf(y))
     * </pre>
     *
     * <p>
     *  比较两个{@code boolean}值。返回的值与由以下内容返回的值相同：
     * <pre>
     *  Boolean.valueOf(x).compareTo(Boolean.valueOf(y))
     * </pre>
     * 
     * 
     * @param  x the first {@code boolean} to compare
     * @param  y the second {@code boolean} to compare
     * @return the value {@code 0} if {@code x == y};
     *         a value less than {@code 0} if {@code !x && y}; and
     *         a value greater than {@code 0} if {@code x && !y}
     * @since 1.7
     */
    public static int compare(boolean x, boolean y) {
        return (x == y) ? 0 : (x ? 1 : -1);
    }

    /**
     * Returns the result of applying the logical AND operator to the
     * specified {@code boolean} operands.
     *
     * <p>
     *  返回将逻辑AND运算符应用于指定的{@code boolean}操作数的结果。
     * 
     * 
     * @param a the first operand
     * @param b the second operand
     * @return the logical AND of {@code a} and {@code b}
     * @see java.util.function.BinaryOperator
     * @since 1.8
     */
    public static boolean logicalAnd(boolean a, boolean b) {
        return a && b;
    }

    /**
     * Returns the result of applying the logical OR operator to the
     * specified {@code boolean} operands.
     *
     * <p>
     *  返回将逻辑OR运算符应用于指定的{@code boolean}操作数的结果。
     * 
     * 
     * @param a the first operand
     * @param b the second operand
     * @return the logical OR of {@code a} and {@code b}
     * @see java.util.function.BinaryOperator
     * @since 1.8
     */
    public static boolean logicalOr(boolean a, boolean b) {
        return a || b;
    }

    /**
     * Returns the result of applying the logical XOR operator to the
     * specified {@code boolean} operands.
     *
     * <p>
     *  返回将逻辑XOR运算符应用于指定的{@code boolean}操作数的结果。
     * 
     * @param a the first operand
     * @param b the second operand
     * @return  the logical XOR of {@code a} and {@code b}
     * @see java.util.function.BinaryOperator
     * @since 1.8
     */
    public static boolean logicalXor(boolean a, boolean b) {
        return a ^ b;
    }
}
