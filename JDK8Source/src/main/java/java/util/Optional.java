/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * A container object which may or may not contain a non-null value.
 * If a value is present, {@code isPresent()} will return {@code true} and
 * {@code get()} will return the value.
 *
 * <p>Additional methods that depend on the presence or absence of a contained
 * value are provided, such as {@link #orElse(java.lang.Object) orElse()}
 * (return a default value if value not present) and
 * {@link #ifPresent(java.util.function.Consumer) ifPresent()} (execute a block
 * of code if the value is present).
 *
 * <p>This is a <a href="../lang/doc-files/ValueBased.html">value-based</a>
 * class; use of identity-sensitive operations (including reference equality
 * ({@code ==}), identity hash code, or synchronization) on instances of
 * {@code Optional} may have unpredictable results and should be avoided.
 *
 * <p>
 *  可能包含或可能不包含非空值的容器对象。如果存在一个值,{@code isPresent()}将返回{@code true},而{@code get()}将返回值。
 * 
 *  <p>提供了取决于是否存在包含值的其他方法,例如{@link #orElse(java.lang.Object)或Else()}(如果值不存在则返回默认值)和{@链接#ifPresent(java.util.function.Consumer)ifPresent()}
 * (如果值存在,执行一个代码块)。
 * 
 *  <p>这是<a href="../lang/doc-files/ValueBased.html">以价值为基础的</a>类;在{@code Optional}实例上使用身份敏感操作(包括引用相等({@code ==}
 * ),身份哈希码或同步)可能会产生不可预测的结果,应该避免使用。
 * 
 * 
 * @since 1.8
 */
public final class Optional<T> {
    /**
     * Common instance for {@code empty()}.
     * <p>
     *  {@code empty()}的常见实例。
     * 
     */
    private static final Optional<?> EMPTY = new Optional<>();

    /**
     * If non-null, the value; if null, indicates no value is present
     * <p>
     *  如果非空,则值;如果为null,则表示不存在值
     * 
     */
    private final T value;

    /**
     * Constructs an empty instance.
     *
     * @implNote Generally only one empty instance, {@link Optional#EMPTY},
     * should exist per VM.
     * <p>
     *  构造一个空实例。
     * 
     *  @implNote通常每个VM只应有一个空实例{@link Optional#EMPTY}。
     * 
     */
    private Optional() {
        this.value = null;
    }

    /**
     * Returns an empty {@code Optional} instance.  No value is present for this
     * Optional.
     *
     * @apiNote Though it may be tempting to do so, avoid testing if an object
     * is empty by comparing with {@code ==} against instances returned by
     * {@code Option.empty()}. There is no guarantee that it is a singleton.
     * Instead, use {@link #isPresent()}.
     *
     * <p>
     *  返回一个空的{@code Optional}实例。此可选项不存在值。
     * 
     *  @apiNote虽然这样做可能是诱人的,但是通过将{@code ==}与{@code Option.empty()}返回的实例进行比较,可以避免测试对象是否为空。不能保证它是一个单身人士。
     * 而应使用{@link #isPresent()}。
     * 
     * 
     * @param <T> Type of the non-existent value
     * @return an empty {@code Optional}
     */
    public static<T> Optional<T> empty() {
        @SuppressWarnings("unchecked")
        Optional<T> t = (Optional<T>) EMPTY;
        return t;
    }

    /**
     * Constructs an instance with the value present.
     *
     * <p>
     * 构造具有值的实例。
     * 
     * 
     * @param value the non-null value to be present
     * @throws NullPointerException if value is null
     */
    private Optional(T value) {
        this.value = Objects.requireNonNull(value);
    }

    /**
     * Returns an {@code Optional} with the specified present non-null value.
     *
     * <p>
     *  返回具有指定的当前非空值的{@code Optional}。
     * 
     * 
     * @param <T> the class of the value
     * @param value the value to be present, which must be non-null
     * @return an {@code Optional} with the value present
     * @throws NullPointerException if value is null
     */
    public static <T> Optional<T> of(T value) {
        return new Optional<>(value);
    }

    /**
     * Returns an {@code Optional} describing the specified value, if non-null,
     * otherwise returns an empty {@code Optional}.
     *
     * <p>
     *  返回描述指定值的{@code Optional}(如果非空),否则返回一个空{@code Optional}。
     * 
     * 
     * @param <T> the class of the value
     * @param value the possibly-null value to describe
     * @return an {@code Optional} with a present value if the specified value
     * is non-null, otherwise an empty {@code Optional}
     */
    public static <T> Optional<T> ofNullable(T value) {
        return value == null ? empty() : of(value);
    }

    /**
     * If a value is present in this {@code Optional}, returns the value,
     * otherwise throws {@code NoSuchElementException}.
     *
     * <p>
     *  如果此{@code可选}中存在值,则返回该值,否则抛出{@code NoSuchElementException}。
     * 
     * 
     * @return the non-null value held by this {@code Optional}
     * @throws NoSuchElementException if there is no value present
     *
     * @see Optional#isPresent()
     */
    public T get() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    /**
     * Return {@code true} if there is a value present, otherwise {@code false}.
     *
     * <p>
     *  如果存在值,则返回{@code true},否则返回{@code false}。
     * 
     * 
     * @return {@code true} if there is a value present, otherwise {@code false}
     */
    public boolean isPresent() {
        return value != null;
    }

    /**
     * If a value is present, invoke the specified consumer with the value,
     * otherwise do nothing.
     *
     * <p>
     *  如果存在值,则使用值调用指定的使用方,否则不做任何操作。
     * 
     * 
     * @param consumer block to be executed if a value is present
     * @throws NullPointerException if value is present and {@code consumer} is
     * null
     */
    public void ifPresent(Consumer<? super T> consumer) {
        if (value != null)
            consumer.accept(value);
    }

    /**
     * If a value is present, and the value matches the given predicate,
     * return an {@code Optional} describing the value, otherwise return an
     * empty {@code Optional}.
     *
     * <p>
     *  如果值存在,并且值与给定的谓词匹配,则返回描述该值的{@code Optional},否则返回一个空的{@code Optional}。
     * 
     * 
     * @param predicate a predicate to apply to the value, if present
     * @return an {@code Optional} describing the value of this {@code Optional}
     * if a value is present and the value matches the given predicate,
     * otherwise an empty {@code Optional}
     * @throws NullPointerException if the predicate is null
     */
    public Optional<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (!isPresent())
            return this;
        else
            return predicate.test(value) ? this : empty();
    }

    /**
     * If a value is present, apply the provided mapping function to it,
     * and if the result is non-null, return an {@code Optional} describing the
     * result.  Otherwise return an empty {@code Optional}.
     *
     * @apiNote This method supports post-processing on optional values, without
     * the need to explicitly check for a return status.  For example, the
     * following code traverses a stream of file names, selects one that has
     * not yet been processed, and then opens that file, returning an
     * {@code Optional<FileInputStream>}:
     *
     * <pre>{@code
     *     Optional<FileInputStream> fis =
     *         names.stream().filter(name -> !isProcessedYet(name))
     *                       .findFirst()
     *                       .map(name -> new FileInputStream(name));
     * }</pre>
     *
     * Here, {@code findFirst} returns an {@code Optional<String>}, and then
     * {@code map} returns an {@code Optional<FileInputStream>} for the desired
     * file if one exists.
     *
     * <p>
     *  如果存在值,请将提供的映射函数应用于该值,如果结果为非空,则返回描述结果的{@code Optional}。否则返回一个空的{@code可选}。
     * 
     *  @apiNote此方法支持对可选值进行后处理,无需显式检查返回状态。
     * 例如,以下代码遍历文件名流,选择一个尚未处理的文件,然后打开该文件,返回{@code Optional <FileInputStream>}：。
     * 
     *  <pre> {@ code可选<FileInputStream> fis = names.stream()。
     * filter(name  - >！isProcessedYet(name)).findFirst().map(name  - > new FileInputStream(name)); } </pre>
     * 。
     *  <pre> {@ code可选<FileInputStream> fis = names.stream()。
     * 
     * 这里,{@code findFirst}返回一个{@code可选<String>},然后{@code map}返回所需文件的{@code可选<FileInputStream>}(如果存在)。
     * 
     * 
     * @param <U> The type of the result of the mapping function
     * @param mapper a mapping function to apply to the value, if present
     * @return an {@code Optional} describing the result of applying a mapping
     * function to the value of this {@code Optional}, if a value is present,
     * otherwise an empty {@code Optional}
     * @throws NullPointerException if the mapping function is null
     */
    public<U> Optional<U> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent())
            return empty();
        else {
            return Optional.ofNullable(mapper.apply(value));
        }
    }

    /**
     * If a value is present, apply the provided {@code Optional}-bearing
     * mapping function to it, return that result, otherwise return an empty
     * {@code Optional}.  This method is similar to {@link #map(Function)},
     * but the provided mapper is one whose result is already an {@code Optional},
     * and if invoked, {@code flatMap} does not wrap it with an additional
     * {@code Optional}.
     *
     * <p>
     *  如果值存在,则应用所提供的{@code Optional}  - 映射函数,返回该结果,否则返回一个空的{@code Optional}。
     * 此方法类似于{@link #map(Function)},但提供的映射器的结果已经是{@code可选},如果调用,{@code flatMap}不会使用额外的{ code可选}。
     * 
     * 
     * @param <U> The type parameter to the {@code Optional} returned by
     * @param mapper a mapping function to apply to the value, if present
     *           the mapping function
     * @return the result of applying an {@code Optional}-bearing mapping
     * function to the value of this {@code Optional}, if a value is present,
     * otherwise an empty {@code Optional}
     * @throws NullPointerException if the mapping function is null or returns
     * a null result
     */
    public<U> Optional<U> flatMap(Function<? super T, Optional<U>> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent())
            return empty();
        else {
            return Objects.requireNonNull(mapper.apply(value));
        }
    }

    /**
     * Return the value if present, otherwise return {@code other}.
     *
     * <p>
     *  返回值(如果存在),否则返回{@code other}。
     * 
     * 
     * @param other the value to be returned if there is no value present, may
     * be null
     * @return the value, if present, otherwise {@code other}
     */
    public T orElse(T other) {
        return value != null ? value : other;
    }

    /**
     * Return the value if present, otherwise invoke {@code other} and return
     * the result of that invocation.
     *
     * <p>
     *  返回值(如果存在),否则调用{@code other}并返回该调用的结果。
     * 
     * 
     * @param other a {@code Supplier} whose result is returned if no value
     * is present
     * @return the value if present otherwise the result of {@code other.get()}
     * @throws NullPointerException if value is not present and {@code other} is
     * null
     */
    public T orElseGet(Supplier<? extends T> other) {
        return value != null ? value : other.get();
    }

    /**
     * Return the contained value, if present, otherwise throw an exception
     * to be created by the provided supplier.
     *
     * @apiNote A method reference to the exception constructor with an empty
     * argument list can be used as the supplier. For example,
     * {@code IllegalStateException::new}
     *
     * <p>
     *  返回包含的值(如果存在),否则抛出要由提供的供应商创建的异常。
     * 
     *  @apiNote对具有空参数列表的异常构造函数的方法引用可以用作供应商。例如,{@code IllegalStateException :: new}
     * 
     * 
     * @param <X> Type of the exception to be thrown
     * @param exceptionSupplier The supplier which will return the exception to
     * be thrown
     * @return the present value
     * @throws X if there is no value present
     * @throws NullPointerException if no value is present and
     * {@code exceptionSupplier} is null
     */
    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (value != null) {
            return value;
        } else {
            throw exceptionSupplier.get();
        }
    }

    /**
     * Indicates whether some other object is "equal to" this Optional. The
     * other object is considered equal if:
     * <ul>
     * <li>it is also an {@code Optional} and;
     * <li>both instances have no value present or;
     * <li>the present values are "equal to" each other via {@code equals()}.
     * </ul>
     *
     * <p>
     *  指示某个其他对象是否"等于"此可选。另一个对象被认为是等于：
     * <ul>
     *  <li>它也是一个{@code可选}和; <li>两个实例都没有值; <li>当前值通过{@code equals()}彼此"相等"。
     * </ul>
     * 
     * 
     * @param obj an object to be tested for equality
     * @return {code true} if the other object is "equal to" this object
     * otherwise {@code false}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Optional)) {
            return false;
        }

        Optional<?> other = (Optional<?>) obj;
        return Objects.equals(value, other.value);
    }

    /**
     * Returns the hash code value of the present value, if any, or 0 (zero) if
     * no value is present.
     *
     * <p>
     *  返回当前值的哈希码值(如果有)或0(零)(如果没有值)。
     * 
     * 
     * @return hash code value of the present value or 0 if no value is present
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    /**
     * Returns a non-empty string representation of this Optional suitable for
     * debugging. The exact presentation format is unspecified and may vary
     * between implementations and versions.
     *
     * @implSpec If a value is present the result must include its string
     * representation in the result. Empty and present Optionals must be
     * unambiguously differentiable.
     *
     * <p>
     * 返回此可选的非空字符串表示,适用于调试。确切的呈现格式是未指定的,并且可以在实现和版本之间变化。
     * 
     * 
     * @return the string representation of this instance
     */
    @Override
    public String toString() {
        return value != null
            ? String.format("Optional[%s]", value)
            : "Optional.empty";
    }
}
