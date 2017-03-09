/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2009, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.util.function.Supplier;

/**
 * This class consists of {@code static} utility methods for operating
 * on objects.  These utilities include {@code null}-safe or {@code
 * null}-tolerant methods for computing the hash code of an object,
 * returning a string for an object, and comparing two objects.
 *
 * <p>
 *  这个类包括用于操作对象的{@code static}实用程序方法。
 * 这些实用程序包括{@code null} -safe或{@code null}  - 容忍方法,用于计算对象的哈希码,返回对象的字符串以及比较两个对象。
 * 
 * 
 * @since 1.7
 */
public final class Objects {
    private Objects() {
        throw new AssertionError("No java.util.Objects instances for you!");
    }

    /**
     * Returns {@code true} if the arguments are equal to each other
     * and {@code false} otherwise.
     * Consequently, if both arguments are {@code null}, {@code true}
     * is returned and if exactly one argument is {@code null}, {@code
     * false} is returned.  Otherwise, equality is determined by using
     * the {@link Object#equals equals} method of the first
     * argument.
     *
     * <p>
     *  如果参数彼此相等,则返回{@code true},否则返回{@code false}。
     * 因此,如果两个参数都是{@code null},则返回{@code true},如果只有一个参数是{@code null},则返回{@code false}。
     * 否则,通过使用第一个参数的{@link Object#equals equals}方法确定等式。
     * 
     * 
     * @param a an object
     * @param b an object to be compared with {@code a} for equality
     * @return {@code true} if the arguments are equal to each other
     * and {@code false} otherwise
     * @see Object#equals(Object)
     */
    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

   /**
    * Returns {@code true} if the arguments are deeply equal to each other
    * and {@code false} otherwise.
    *
    * Two {@code null} values are deeply equal.  If both arguments are
    * arrays, the algorithm in {@link Arrays#deepEquals(Object[],
    * Object[]) Arrays.deepEquals} is used to determine equality.
    * Otherwise, equality is determined by using the {@link
    * Object#equals equals} method of the first argument.
    *
    * <p>
    *  如果参数彼此深度相等,则返回{@code true},否则返回{@code false}。
    * 
    *  两个{@code null}值是相等的。
    * 如果两个参数都是数组,{@link Arrays#deepEquals(Object [],Object [])Arrays.deepEquals}中的算法用于确定相等性。
    * 否则,通过使用第一个参数的{@link Object#equals equals}方法确定等式。
    * 
    * 
    * @param a an object
    * @param b an object to be compared with {@code a} for deep equality
    * @return {@code true} if the arguments are deeply equal to each other
    * and {@code false} otherwise
    * @see Arrays#deepEquals(Object[], Object[])
    * @see Objects#equals(Object, Object)
    */
    public static boolean deepEquals(Object a, Object b) {
        if (a == b)
            return true;
        else if (a == null || b == null)
            return false;
        else
            return Arrays.deepEquals0(a, b);
    }

    /**
     * Returns the hash code of a non-{@code null} argument and 0 for
     * a {@code null} argument.
     *
     * <p>
     *  返回非 -  {@ code null}参数的哈希码,并为{@code null}参数返回0。
     * 
     * 
     * @param o an object
     * @return the hash code of a non-{@code null} argument and 0 for
     * a {@code null} argument
     * @see Object#hashCode
     */
    public static int hashCode(Object o) {
        return o != null ? o.hashCode() : 0;
    }

   /**
    * Generates a hash code for a sequence of input values. The hash
    * code is generated as if all the input values were placed into an
    * array, and that array were hashed by calling {@link
    * Arrays#hashCode(Object[])}.
    *
    * <p>This method is useful for implementing {@link
    * Object#hashCode()} on objects containing multiple fields. For
    * example, if an object that has three fields, {@code x}, {@code
    * y}, and {@code z}, one could write:
    *
    * <blockquote><pre>
    * &#064;Override public int hashCode() {
    *     return Objects.hash(x, y, z);
    * }
    * </pre></blockquote>
    *
    * <b>Warning: When a single object reference is supplied, the returned
    * value does not equal the hash code of that object reference.</b> This
    * value can be computed by calling {@link #hashCode(Object)}.
    *
    * <p>
    *  为输入值序列生成哈希码。生成哈希码,好像所有输入值都放在一个数组中,并且该数组通过调用{@link Arrays#hashCode(Object [])}进行哈希。
    * 
    * <p>此方法对于在包含多个字段的对象上实现{@link Object#hashCode()}非常有用。
    * 例如,如果具有三个字段{@code x},{@code y}和{@code z}的对象,可以写为：。
    * 
    *  <blockquote> <pre> @Override public int hashCode(){return Objects.hash(x,y,z); } </pre> </blockquote>
    * 。
    * 
    *  <b>警告：当提供单个对象引用时,返回的值不等于该对象引用的哈希码。</b>此值可以通过调用{@link #hashCode(Object)}来计算。
    * 
    * 
    * @param values the values to be hashed
    * @return a hash value of the sequence of input values
    * @see Arrays#hashCode(Object[])
    * @see List#hashCode
    */
    public static int hash(Object... values) {
        return Arrays.hashCode(values);
    }

    /**
     * Returns the result of calling {@code toString} for a non-{@code
     * null} argument and {@code "null"} for a {@code null} argument.
     *
     * <p>
     *  返回对于{@code null}参数调用{@code toString}和对于{@code null}参数调用{@code"null"}的结果。
     * 
     * 
     * @param o an object
     * @return the result of calling {@code toString} for a non-{@code
     * null} argument and {@code "null"} for a {@code null} argument
     * @see Object#toString
     * @see String#valueOf(Object)
     */
    public static String toString(Object o) {
        return String.valueOf(o);
    }

    /**
     * Returns the result of calling {@code toString} on the first
     * argument if the first argument is not {@code null} and returns
     * the second argument otherwise.
     *
     * <p>
     *  返回对第一个参数调用{@code toString}的结果,如果第一个参数不是{@code null},否则返回第二个参数。
     * 
     * 
     * @param o an object
     * @param nullDefault string to return if the first argument is
     *        {@code null}
     * @return the result of calling {@code toString} on the first
     * argument if it is not {@code null} and the second argument
     * otherwise.
     * @see Objects#toString(Object)
     */
    public static String toString(Object o, String nullDefault) {
        return (o != null) ? o.toString() : nullDefault;
    }

    /**
     * Returns 0 if the arguments are identical and {@code
     * c.compare(a, b)} otherwise.
     * Consequently, if both arguments are {@code null} 0
     * is returned.
     *
     * <p>Note that if one of the arguments is {@code null}, a {@code
     * NullPointerException} may or may not be thrown depending on
     * what ordering policy, if any, the {@link Comparator Comparator}
     * chooses to have for {@code null} values.
     *
     * <p>
     *  如果参数相同,则返回0,否则返回{@code c.compare(a,b)}。因此,如果两个参数都是{@code null} 0被返回。
     * 
     *  <p>请注意,如果其中一个参数是{@code null},{@code NullPointerException}可能会或可能不会被抛出,具体取决于{@link比较器比较器}选择的排序策略(如果有) 
     * @code null}值。
     * 
     * 
     * @param <T> the type of the objects being compared
     * @param a an object
     * @param b an object to be compared with {@code a}
     * @param c the {@code Comparator} to compare the first two arguments
     * @return 0 if the arguments are identical and {@code
     * c.compare(a, b)} otherwise.
     * @see Comparable
     * @see Comparator
     */
    public static <T> int compare(T a, T b, Comparator<? super T> c) {
        return (a == b) ? 0 :  c.compare(a, b);
    }

    /**
     * Checks that the specified object reference is not {@code null}. This
     * method is designed primarily for doing parameter validation in methods
     * and constructors, as demonstrated below:
     * <blockquote><pre>
     * public Foo(Bar bar) {
     *     this.bar = Objects.requireNonNull(bar);
     * }
     * </pre></blockquote>
     *
     * <p>
     *  检查指定的对象引用不是{@code null}。
     * 这个方法主要用于在方法和构造函数中进行参数验证,如下所示：<blockquote> <pre> public Foo(Bar bar){this.bar = Objects.requireNonNull(bar); }
     *  </pre> </blockquote>。
     *  检查指定的对象引用不是{@code null}。
     * 
     * 
     * @param obj the object reference to check for nullity
     * @param <T> the type of the reference
     * @return {@code obj} if not {@code null}
     * @throws NullPointerException if {@code obj} is {@code null}
     */
    public static <T> T requireNonNull(T obj) {
        if (obj == null)
            throw new NullPointerException();
        return obj;
    }

    /**
     * Checks that the specified object reference is not {@code null} and
     * throws a customized {@link NullPointerException} if it is. This method
     * is designed primarily for doing parameter validation in methods and
     * constructors with multiple parameters, as demonstrated below:
     * <blockquote><pre>
     * public Foo(Bar bar, Baz baz) {
     *     this.bar = Objects.requireNonNull(bar, "bar must not be null");
     *     this.baz = Objects.requireNonNull(baz, "baz must not be null");
     * }
     * </pre></blockquote>
     *
     * <p>
     * 检查指定的对象引用不是{@code null},如果是,则抛出自定义的{@link NullPointerException}。
     * 此方法主要用于在具有多个参数的方法和构造函数中进行参数验证,如下所示：<block> <new> public Foo(Bar bar,Baz baz){this.bar = Objects.requireNonNull not null"); this.baz = Objects.requireNonNull(baz,"baz must not be null"); }
     *  </pre> </blockquote>。
     * 检查指定的对象引用不是{@code null},如果是,则抛出自定义的{@link NullPointerException}。
     * 
     * 
     * @param obj     the object reference to check for nullity
     * @param message detail message to be used in the event that a {@code
     *                NullPointerException} is thrown
     * @param <T> the type of the reference
     * @return {@code obj} if not {@code null}
     * @throws NullPointerException if {@code obj} is {@code null}
     */
    public static <T> T requireNonNull(T obj, String message) {
        if (obj == null)
            throw new NullPointerException(message);
        return obj;
    }

    /**
     * Returns {@code true} if the provided reference is {@code null} otherwise
     * returns {@code false}.
     *
     * @apiNote This method exists to be used as a
     * {@link java.util.function.Predicate}, {@code filter(Objects::isNull)}
     *
     * <p>
     *  如果提供的引用是{@code null},则返回{@code true},否则返回{@code false}。
     * 
     *  @apiNote此方法存在用作{@link java.util.function.Predicate},{@code filter(Objects :: isNull)}
     * 
     * 
     * @param obj a reference to be checked against {@code null}
     * @return {@code true} if the provided reference is {@code null} otherwise
     * {@code false}
     *
     * @see java.util.function.Predicate
     * @since 1.8
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * Returns {@code true} if the provided reference is non-{@code null}
     * otherwise returns {@code false}.
     *
     * @apiNote This method exists to be used as a
     * {@link java.util.function.Predicate}, {@code filter(Objects::nonNull)}
     *
     * <p>
     *  如果提供的引用为非 -  {@ code null},则返回{@code true}否则返回{@code false}。
     * 
     *  @apiNote此方法存在用作{@link java.util.function.Predicate},{@code filter(Objects :: nonNull)}
     * 
     * 
     * @param obj a reference to be checked against {@code null}
     * @return {@code true} if the provided reference is non-{@code null}
     * otherwise {@code false}
     *
     * @see java.util.function.Predicate
     * @since 1.8
     */
    public static boolean nonNull(Object obj) {
        return obj != null;
    }

    /**
     * Checks that the specified object reference is not {@code null} and
     * throws a customized {@link NullPointerException} if it is.
     *
     * <p>Unlike the method {@link #requireNonNull(Object, String)},
     * this method allows creation of the message to be deferred until
     * after the null check is made. While this may confer a
     * performance advantage in the non-null case, when deciding to
     * call this method care should be taken that the costs of
     * creating the message supplier are less than the cost of just
     * creating the string message directly.
     *
     * <p>
     *  检查指定的对象引用不是{@code null},如果是,则抛出自定义的{@link NullPointerException}。
     * 
     *  <p>与方法{@link #requireNonNull(Object,String)}不同,此方法允许创建要延迟的消息,直到进行空检查。
     * 
     * @param obj     the object reference to check for nullity
     * @param messageSupplier supplier of the detail message to be
     * used in the event that a {@code NullPointerException} is thrown
     * @param <T> the type of the reference
     * @return {@code obj} if not {@code null}
     * @throws NullPointerException if {@code obj} is {@code null}
     * @since 1.8
     */
    public static <T> T requireNonNull(T obj, Supplier<String> messageSupplier) {
        if (obj == null)
            throw new NullPointerException(messageSupplier.get());
        return obj;
    }
}
