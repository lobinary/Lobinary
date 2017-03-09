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
package java.util.stream;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/**
 * Implementations of {@link Collector} that implement various useful reduction
 * operations, such as accumulating elements into collections, summarizing
 * elements according to various criteria, etc.
 *
 * <p>The following are examples of using the predefined collectors to perform
 * common mutable reduction tasks:
 *
 * <pre>{@code
 *     // Accumulate names into a List
 *     List<String> list = people.stream().map(Person::getName).collect(Collectors.toList());
 *
 *     // Accumulate names into a TreeSet
 *     Set<String> set = people.stream().map(Person::getName).collect(Collectors.toCollection(TreeSet::new));
 *
 *     // Convert elements to strings and concatenate them, separated by commas
 *     String joined = things.stream()
 *                           .map(Object::toString)
 *                           .collect(Collectors.joining(", "));
 *
 *     // Compute sum of salaries of employee
 *     int total = employees.stream()
 *                          .collect(Collectors.summingInt(Employee::getSalary)));
 *
 *     // Group employees by department
 *     Map<Department, List<Employee>> byDept
 *         = employees.stream()
 *                    .collect(Collectors.groupingBy(Employee::getDepartment));
 *
 *     // Compute sum of salaries by department
 *     Map<Department, Integer> totalByDept
 *         = employees.stream()
 *                    .collect(Collectors.groupingBy(Employee::getDepartment,
 *                                                   Collectors.summingInt(Employee::getSalary)));
 *
 *     // Partition students into passing and failing
 *     Map<Boolean, List<Student>> passingFailing =
 *         students.stream()
 *                 .collect(Collectors.partitioningBy(s -> s.getGrade() >= PASS_THRESHOLD));
 *
 * }</pre>
 *
 * <p>
 *  {@link Collector}的实现,实现各种有用的简化操作,例如将元素累积到集合中,根据各种标准汇总元素等。
 * 
 *  <p>以下是使用预定义收集器执行常见可变减少任务的示例：
 * 
 *  <pre> {@ code //将名称累加到列表列表中<String> list = people.stream()。
 * map(Person :: getName).collect(Collectors.toList());。
 * 
 *  //将名称累加到TreeSet集中<String> set = people.stream()。
 * map(Person :: getName).collect(Collectors.toCollection(TreeSet :: new));。
 * 
 *  //将元素转换为字符串并连接它们,用逗号分隔String join = things.stream().map(Object :: toString).collect(Collectors.joini
 * ng(","));。
 * 
 *  //计算雇员的工资总和int total = employees.stream().collect(Collectors.summingInt(Employee :: getSalary)));
 * 
 *  //按部门分组员工地图<部门,列表<员工>> byDept = employees.stream().collect(Collectors.groupingBy(Employee :: getDepa
 * rtment));。
 * 
 *  //计算部门的工资总和Map <Department,Integer> totalByDept = employees.stream().collect(Collectors.groupingBy(E
 * mployee :: getDepartment,Collectors.summingInt(Employee :: getSalary)));。
 * 
 * //分区学生传递和失败Map <Boolean,List <Student >> passingFailing = students.stream().collect(Collectors.partit
 * ioningBy(s  - > s.getGrade()> = PASS_THRESHOLD)。
 * 
 *  } </pre>
 * 
 * 
 * @since 1.8
 */
public final class Collectors {

    static final Set<Collector.Characteristics> CH_CONCURRENT_ID
            = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.CONCURRENT,
                                                     Collector.Characteristics.UNORDERED,
                                                     Collector.Characteristics.IDENTITY_FINISH));
    static final Set<Collector.Characteristics> CH_CONCURRENT_NOID
            = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.CONCURRENT,
                                                     Collector.Characteristics.UNORDERED));
    static final Set<Collector.Characteristics> CH_ID
            = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
    static final Set<Collector.Characteristics> CH_UNORDERED_ID
            = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.UNORDERED,
                                                     Collector.Characteristics.IDENTITY_FINISH));
    static final Set<Collector.Characteristics> CH_NOID = Collections.emptySet();

    private Collectors() { }

    /**
     * Returns a merge function, suitable for use in
     * {@link Map#merge(Object, Object, BiFunction) Map.merge()} or
     * {@link #toMap(Function, Function, BinaryOperator) toMap()}, which always
     * throws {@code IllegalStateException}.  This can be used to enforce the
     * assumption that the elements being collected are distinct.
     *
     * <p>
     *  返回一个合并函数,适用于{@link Map#merge(Object,Object,BiFunction)Map.merge()}或{@link #toMap(Function,Function,BinaryOperator)toMap @code IllegalStateException}
     * 。
     * 这可以用于强制所收集的元素是不同的假设。
     * 
     * 
     * @param <T> the type of input arguments to the merge function
     * @return a merge function which always throw {@code IllegalStateException}
     */
    private static <T> BinaryOperator<T> throwingMerger() {
        return (u,v) -> { throw new IllegalStateException(String.format("Duplicate key %s", u)); };
    }

    @SuppressWarnings("unchecked")
    private static <I, R> Function<I, R> castingIdentity() {
        return i -> (R) i;
    }

    /**
     * Simple implementation class for {@code Collector}.
     *
     * <p>
     *  {@code Collector}的简单实现类。
     * 
     * 
     * @param <T> the type of elements to be collected
     * @param <R> the type of the result
     */
    static class CollectorImpl<T, A, R> implements Collector<T, A, R> {
        private final Supplier<A> supplier;
        private final BiConsumer<A, T> accumulator;
        private final BinaryOperator<A> combiner;
        private final Function<A, R> finisher;
        private final Set<Characteristics> characteristics;

        CollectorImpl(Supplier<A> supplier,
                      BiConsumer<A, T> accumulator,
                      BinaryOperator<A> combiner,
                      Function<A,R> finisher,
                      Set<Characteristics> characteristics) {
            this.supplier = supplier;
            this.accumulator = accumulator;
            this.combiner = combiner;
            this.finisher = finisher;
            this.characteristics = characteristics;
        }

        CollectorImpl(Supplier<A> supplier,
                      BiConsumer<A, T> accumulator,
                      BinaryOperator<A> combiner,
                      Set<Characteristics> characteristics) {
            this(supplier, accumulator, combiner, castingIdentity(), characteristics);
        }

        @Override
        public BiConsumer<A, T> accumulator() {
            return accumulator;
        }

        @Override
        public Supplier<A> supplier() {
            return supplier;
        }

        @Override
        public BinaryOperator<A> combiner() {
            return combiner;
        }

        @Override
        public Function<A, R> finisher() {
            return finisher;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return characteristics;
        }
    }

    /**
     * Returns a {@code Collector} that accumulates the input elements into a
     * new {@code Collection}, in encounter order.  The {@code Collection} is
     * created by the provided factory.
     *
     * <p>
     *  返回一个{@code Collector},它按照遇到顺序将输入元素累积到新的{@code Collection}中。 {@code Collection}由提供的工厂创建。
     * 
     * 
     * @param <T> the type of the input elements
     * @param <C> the type of the resulting {@code Collection}
     * @param collectionFactory a {@code Supplier} which returns a new, empty
     * {@code Collection} of the appropriate type
     * @return a {@code Collector} which collects all the input elements into a
     * {@code Collection}, in encounter order
     */
    public static <T, C extends Collection<T>>
    Collector<T, ?, C> toCollection(Supplier<C> collectionFactory) {
        return new CollectorImpl<>(collectionFactory, Collection<T>::add,
                                   (r1, r2) -> { r1.addAll(r2); return r1; },
                                   CH_ID);
    }

    /**
     * Returns a {@code Collector} that accumulates the input elements into a
     * new {@code List}. There are no guarantees on the type, mutability,
     * serializability, or thread-safety of the {@code List} returned; if more
     * control over the returned {@code List} is required, use {@link #toCollection(Supplier)}.
     *
     * <p>
     *  返回一个{@code Collector},它将输入元素累积到一个新的{@code List}中。
     * 对返回的{@code List}的类型,可变性,可串行化性或线程安全性没有保证;如果需要更多地控制返回的{@code List},请使用{@link #toCollection(Supplier)}。
     * 
     * 
     * @param <T> the type of the input elements
     * @return a {@code Collector} which collects all the input elements into a
     * {@code List}, in encounter order
     */
    public static <T>
    Collector<T, ?, List<T>> toList() {
        return new CollectorImpl<>((Supplier<List<T>>) ArrayList::new, List::add,
                                   (left, right) -> { left.addAll(right); return left; },
                                   CH_ID);
    }

    /**
     * Returns a {@code Collector} that accumulates the input elements into a
     * new {@code Set}. There are no guarantees on the type, mutability,
     * serializability, or thread-safety of the {@code Set} returned; if more
     * control over the returned {@code Set} is required, use
     * {@link #toCollection(Supplier)}.
     *
     * <p>This is an {@link Collector.Characteristics#UNORDERED unordered}
     * Collector.
     *
     * <p>
     *  返回一个{@code Collector},它将输入元素累积到一个新的{@code Set}中。
     * 没有对返回的{@code Set}的类型,可变性,可串行化或线程安全性的保证;如果需要更多地控制返回的{@code Set},请使用{@link #toCollection(Supplier)}。
     * 
     *  <p>这是一个{@link Collector.Characteristics#UNORDERED unordered}收集器。
     * 
     * 
     * @param <T> the type of the input elements
     * @return a {@code Collector} which collects all the input elements into a
     * {@code Set}
     */
    public static <T>
    Collector<T, ?, Set<T>> toSet() {
        return new CollectorImpl<>((Supplier<Set<T>>) HashSet::new, Set::add,
                                   (left, right) -> { left.addAll(right); return left; },
                                   CH_UNORDERED_ID);
    }

    /**
     * Returns a {@code Collector} that concatenates the input elements into a
     * {@code String}, in encounter order.
     *
     * <p>
     * 返回一个{@code Collector},按照遇到顺序将输入元素连接到{@code String}。
     * 
     * 
     * @return a {@code Collector} that concatenates the input elements into a
     * {@code String}, in encounter order
     */
    public static Collector<CharSequence, ?, String> joining() {
        return new CollectorImpl<CharSequence, StringBuilder, String>(
                StringBuilder::new, StringBuilder::append,
                (r1, r2) -> { r1.append(r2); return r1; },
                StringBuilder::toString, CH_NOID);
    }

    /**
     * Returns a {@code Collector} that concatenates the input elements,
     * separated by the specified delimiter, in encounter order.
     *
     * <p>
     *  返回一个{@code Collector},它按照遇到顺序连接由指定分隔符分隔的输入元素。
     * 
     * 
     * @param delimiter the delimiter to be used between each element
     * @return A {@code Collector} which concatenates CharSequence elements,
     * separated by the specified delimiter, in encounter order
     */
    public static Collector<CharSequence, ?, String> joining(CharSequence delimiter) {
        return joining(delimiter, "", "");
    }

    /**
     * Returns a {@code Collector} that concatenates the input elements,
     * separated by the specified delimiter, with the specified prefix and
     * suffix, in encounter order.
     *
     * <p>
     *  返回一个{@code Collector},它按照遇到顺序连接由指定分隔符分隔的输入元素与指定的前缀和后缀。
     * 
     * 
     * @param delimiter the delimiter to be used between each element
     * @param  prefix the sequence of characters to be used at the beginning
     *                of the joined result
     * @param  suffix the sequence of characters to be used at the end
     *                of the joined result
     * @return A {@code Collector} which concatenates CharSequence elements,
     * separated by the specified delimiter, in encounter order
     */
    public static Collector<CharSequence, ?, String> joining(CharSequence delimiter,
                                                             CharSequence prefix,
                                                             CharSequence suffix) {
        return new CollectorImpl<>(
                () -> new StringJoiner(delimiter, prefix, suffix),
                StringJoiner::add, StringJoiner::merge,
                StringJoiner::toString, CH_NOID);
    }

    /**
     * {@code BinaryOperator<Map>} that merges the contents of its right
     * argument into its left argument, using the provided merge function to
     * handle duplicate keys.
     *
     * <p>
     *  {@code BinaryOperator <Map>}将其右参数的内容合并到其左参数中,使用提供的合并函数处理重复键。
     * 
     * 
     * @param <K> type of the map keys
     * @param <V> type of the map values
     * @param <M> type of the map
     * @param mergeFunction A merge function suitable for
     * {@link Map#merge(Object, Object, BiFunction) Map.merge()}
     * @return a merge function for two maps
     */
    private static <K, V, M extends Map<K,V>>
    BinaryOperator<M> mapMerger(BinaryOperator<V> mergeFunction) {
        return (m1, m2) -> {
            for (Map.Entry<K,V> e : m2.entrySet())
                m1.merge(e.getKey(), e.getValue(), mergeFunction);
            return m1;
        };
    }

    /**
     * Adapts a {@code Collector} accepting elements of type {@code U} to one
     * accepting elements of type {@code T} by applying a mapping function to
     * each input element before accumulation.
     *
     * @apiNote
     * The {@code mapping()} collectors are most useful when used in a
     * multi-level reduction, such as downstream of a {@code groupingBy} or
     * {@code partitioningBy}.  For example, given a stream of
     * {@code Person}, to accumulate the set of last names in each city:
     * <pre>{@code
     *     Map<City, Set<String>> lastNamesByCity
     *         = people.stream().collect(groupingBy(Person::getCity,
     *                                              mapping(Person::getLastName, toSet())));
     * }</pre>
     *
     * <p>
     *  通过在累积之前对每个输入元素应用映射函数,使{@code Collector}接受类型为{@code U}的元素为一个接受类型为{@code T}的元素。
     * 
     *  @apiNote {@code mapping()}收集器在多级缩减中最有用,例如{@code groupingBy}或{@code partitioningBy}的下游。
     * 例如,给定{@code Person}流,累积每个城市中的姓氏集合：<pre> {@ code Map <City,Set <String >> lastNamesByCity = people.stream()。
     *  @apiNote {@code mapping()}收集器在多级缩减中最有用,例如{@code groupingBy}或{@code partitioningBy}的下游。
     * collect(groupingBy (Person :: getCity,mapping(Person :: getLastName,toSet()))); } </pre>。
     * 
     * 
     * @param <T> the type of the input elements
     * @param <U> type of elements accepted by downstream collector
     * @param <A> intermediate accumulation type of the downstream collector
     * @param <R> result type of collector
     * @param mapper a function to be applied to the input elements
     * @param downstream a collector which will accept mapped values
     * @return a collector which applies the mapping function to the input
     * elements and provides the mapped results to the downstream collector
     */
    public static <T, U, A, R>
    Collector<T, ?, R> mapping(Function<? super T, ? extends U> mapper,
                               Collector<? super U, A, R> downstream) {
        BiConsumer<A, ? super U> downstreamAccumulator = downstream.accumulator();
        return new CollectorImpl<>(downstream.supplier(),
                                   (r, t) -> downstreamAccumulator.accept(r, mapper.apply(t)),
                                   downstream.combiner(), downstream.finisher(),
                                   downstream.characteristics());
    }

    /**
     * Adapts a {@code Collector} to perform an additional finishing
     * transformation.  For example, one could adapt the {@link #toList()}
     * collector to always produce an immutable list with:
     * <pre>{@code
     *     List<String> people
     *         = people.stream().collect(collectingAndThen(toList(), Collections::unmodifiableList));
     * }</pre>
     *
     * <p>
     *  调整{@code Collector}以执行额外的完成转换。
     * 例如,可以调整{@link #toList()}收集器总是产生一个不可变的列表：<pre> {@ code List <String> people = people.stream()。
     * collect(collectingAndThen(toList Collections :: unmodifiableList)); } </pre>。
     * 
     * 
     * @param <T> the type of the input elements
     * @param <A> intermediate accumulation type of the downstream collector
     * @param <R> result type of the downstream collector
     * @param <RR> result type of the resulting collector
     * @param downstream a collector
     * @param finisher a function to be applied to the final result of the downstream collector
     * @return a collector which performs the action of the downstream collector,
     * followed by an additional finishing step
     */
    public static<T,A,R,RR> Collector<T,A,RR> collectingAndThen(Collector<T,A,R> downstream,
                                                                Function<R,RR> finisher) {
        Set<Collector.Characteristics> characteristics = downstream.characteristics();
        if (characteristics.contains(Collector.Characteristics.IDENTITY_FINISH)) {
            if (characteristics.size() == 1)
                characteristics = Collectors.CH_NOID;
            else {
                characteristics = EnumSet.copyOf(characteristics);
                characteristics.remove(Collector.Characteristics.IDENTITY_FINISH);
                characteristics = Collections.unmodifiableSet(characteristics);
            }
        }
        return new CollectorImpl<>(downstream.supplier(),
                                   downstream.accumulator(),
                                   downstream.combiner(),
                                   downstream.finisher().andThen(finisher),
                                   characteristics);
    }

    /**
     * Returns a {@code Collector} accepting elements of type {@code T} that
     * counts the number of input elements.  If no elements are present, the
     * result is 0.
     *
     * @implSpec
     * This produces a result equivalent to:
     * <pre>{@code
     *     reducing(0L, e -> 1L, Long::sum)
     * }</pre>
     *
     * <p>
     * 返回{@code Collector}接受{@code T}类型的计数输入元素数的元素。如果没有元素,结果为0。
     * 
     *  @implSpec这产生等效的结果：<pre> {@ code reduction(0L,e  - > 1L,Long :: sum)} </pre>
     * 
     * 
     * @param <T> the type of the input elements
     * @return a {@code Collector} that counts the input elements
     */
    public static <T> Collector<T, ?, Long>
    counting() {
        return reducing(0L, e -> 1L, Long::sum);
    }

    /**
     * Returns a {@code Collector} that produces the minimal element according
     * to a given {@code Comparator}, described as an {@code Optional<T>}.
     *
     * @implSpec
     * This produces a result equivalent to:
     * <pre>{@code
     *     reducing(BinaryOperator.minBy(comparator))
     * }</pre>
     *
     * <p>
     *  返回{@code Collector},根据给定的{@code Comparator}生成最小元素,描述为{@code可选<T>}。
     * 
     *  @implSpec这产生等效的结果：<pre> {@ code reducing(BinaryOperator.minBy(comparator))} </pre>
     * 
     * 
     * @param <T> the type of the input elements
     * @param comparator a {@code Comparator} for comparing elements
     * @return a {@code Collector} that produces the minimal value
     */
    public static <T> Collector<T, ?, Optional<T>>
    minBy(Comparator<? super T> comparator) {
        return reducing(BinaryOperator.minBy(comparator));
    }

    /**
     * Returns a {@code Collector} that produces the maximal element according
     * to a given {@code Comparator}, described as an {@code Optional<T>}.
     *
     * @implSpec
     * This produces a result equivalent to:
     * <pre>{@code
     *     reducing(BinaryOperator.maxBy(comparator))
     * }</pre>
     *
     * <p>
     *  返回{@code Collector},根据给定的{@code Comparator}生成最大元素,描述为{@code可选<T>}。
     * 
     *  @implSpec这产生一个等效的结果：<pre> {@ code reducing(BinaryOperator.maxBy(comparator))} </pre>
     * 
     * 
     * @param <T> the type of the input elements
     * @param comparator a {@code Comparator} for comparing elements
     * @return a {@code Collector} that produces the maximal value
     */
    public static <T> Collector<T, ?, Optional<T>>
    maxBy(Comparator<? super T> comparator) {
        return reducing(BinaryOperator.maxBy(comparator));
    }

    /**
     * Returns a {@code Collector} that produces the sum of a integer-valued
     * function applied to the input elements.  If no elements are present,
     * the result is 0.
     *
     * <p>
     *  返回一个{@code Collector},它产生应用于输入元素的整数值函数的和。如果没有元素,结果为0。
     * 
     * 
     * @param <T> the type of the input elements
     * @param mapper a function extracting the property to be summed
     * @return a {@code Collector} that produces the sum of a derived property
     */
    public static <T> Collector<T, ?, Integer>
    summingInt(ToIntFunction<? super T> mapper) {
        return new CollectorImpl<>(
                () -> new int[1],
                (a, t) -> { a[0] += mapper.applyAsInt(t); },
                (a, b) -> { a[0] += b[0]; return a; },
                a -> a[0], CH_NOID);
    }

    /**
     * Returns a {@code Collector} that produces the sum of a long-valued
     * function applied to the input elements.  If no elements are present,
     * the result is 0.
     *
     * <p>
     *  返回{@code Collector},它生成应用于输入元素的长值函数的总和。如果没有元素,结果为0。
     * 
     * 
     * @param <T> the type of the input elements
     * @param mapper a function extracting the property to be summed
     * @return a {@code Collector} that produces the sum of a derived property
     */
    public static <T> Collector<T, ?, Long>
    summingLong(ToLongFunction<? super T> mapper) {
        return new CollectorImpl<>(
                () -> new long[1],
                (a, t) -> { a[0] += mapper.applyAsLong(t); },
                (a, b) -> { a[0] += b[0]; return a; },
                a -> a[0], CH_NOID);
    }

    /**
     * Returns a {@code Collector} that produces the sum of a double-valued
     * function applied to the input elements.  If no elements are present,
     * the result is 0.
     *
     * <p>The sum returned can vary depending upon the order in which
     * values are recorded, due to accumulated rounding error in
     * addition of values of differing magnitudes. Values sorted by increasing
     * absolute magnitude tend to yield more accurate results.  If any recorded
     * value is a {@code NaN} or the sum is at any point a {@code NaN} then the
     * sum will be {@code NaN}.
     *
     * <p>
     *  返回一个{@code Collector},它生成应用于输入元素的双值函数的和。如果没有元素,结果为0。
     * 
     * <p>由于除了不同大小的值之外的累积舍入误差,所返回的和可以根据记录值的顺序而变化。通过增加绝对量值排序的值往往产生更准确的结果。
     * 如果任何记录值是{@code NaN}或总和在任何点{@code NaN},那么和将是{@code NaN}。
     * 
     * 
     * @param <T> the type of the input elements
     * @param mapper a function extracting the property to be summed
     * @return a {@code Collector} that produces the sum of a derived property
     */
    public static <T> Collector<T, ?, Double>
    summingDouble(ToDoubleFunction<? super T> mapper) {
        /*
         * In the arrays allocated for the collect operation, index 0
         * holds the high-order bits of the running sum, index 1 holds
         * the low-order bits of the sum computed via compensated
         * summation, and index 2 holds the simple sum used to compute
         * the proper result if the stream contains infinite values of
         * the same sign.
         * <p>
         *  在为收集操作分配的数组中,索引0保存运行总和的高阶位,索引1保存通过补偿求和计算出的和的低阶位,索引2保存用于计算正确如果流包含相同符号的无限值,则返回结果。
         * 
         */
        return new CollectorImpl<>(
                () -> new double[3],
                (a, t) -> { sumWithCompensation(a, mapper.applyAsDouble(t));
                            a[2] += mapper.applyAsDouble(t);},
                (a, b) -> { sumWithCompensation(a, b[0]);
                            a[2] += b[2];
                            return sumWithCompensation(a, b[1]); },
                a -> computeFinalSum(a),
                CH_NOID);
    }

    /**
     * Incorporate a new double value using Kahan summation /
     * compensation summation.
     *
     * High-order bits of the sum are in intermediateSum[0], low-order
     * bits of the sum are in intermediateSum[1], any additional
     * elements are application-specific.
     *
     * <p>
     *  使用Kahan求和/补偿求和合并新的双精度值。
     * 
     *  和的高阶位在中间Sum [0]中,和的低阶位在中间Sum [1]中,任何附加元素是应用特定的。
     * 
     * 
     * @param intermediateSum the high-order and low-order words of the intermediate sum
     * @param value the name value to be included in the running sum
     */
    static double[] sumWithCompensation(double[] intermediateSum, double value) {
        double tmp = value - intermediateSum[1];
        double sum = intermediateSum[0];
        double velvel = sum + tmp; // Little wolf of rounding error
        intermediateSum[1] = (velvel - sum) - tmp;
        intermediateSum[0] = velvel;
        return intermediateSum;
    }

    /**
     * If the compensated sum is spuriously NaN from accumulating one
     * or more same-signed infinite values, return the
     * correctly-signed infinity stored in the simple sum.
     * <p>
     *  如果补偿的和是通过累加一个或多个相同有符号无穷大值而产生的假设NaN,则返回存储在简单和中的正确标记的无穷大。
     * 
     */
    static double computeFinalSum(double[] summands) {
        // Better error bounds to add both terms as the final sum
        double tmp = summands[0] + summands[1];
        double simpleSum = summands[summands.length - 1];
        if (Double.isNaN(tmp) && Double.isInfinite(simpleSum))
            return simpleSum;
        else
            return tmp;
    }

    /**
     * Returns a {@code Collector} that produces the arithmetic mean of an integer-valued
     * function applied to the input elements.  If no elements are present,
     * the result is 0.
     *
     * <p>
     *  返回一个{@code Collector},它产生应用于输入元素的整数值函数的算术平均值。如果没有元素,结果为0。
     * 
     * 
     * @param <T> the type of the input elements
     * @param mapper a function extracting the property to be summed
     * @return a {@code Collector} that produces the sum of a derived property
     */
    public static <T> Collector<T, ?, Double>
    averagingInt(ToIntFunction<? super T> mapper) {
        return new CollectorImpl<>(
                () -> new long[2],
                (a, t) -> { a[0] += mapper.applyAsInt(t); a[1]++; },
                (a, b) -> { a[0] += b[0]; a[1] += b[1]; return a; },
                a -> (a[1] == 0) ? 0.0d : (double) a[0] / a[1], CH_NOID);
    }

    /**
     * Returns a {@code Collector} that produces the arithmetic mean of a long-valued
     * function applied to the input elements.  If no elements are present,
     * the result is 0.
     *
     * <p>
     *  返回{@code Collector},它产生应用于输入元素的长值函数的算术平均值。如果没有元素,结果为0。
     * 
     * 
     * @param <T> the type of the input elements
     * @param mapper a function extracting the property to be summed
     * @return a {@code Collector} that produces the sum of a derived property
     */
    public static <T> Collector<T, ?, Double>
    averagingLong(ToLongFunction<? super T> mapper) {
        return new CollectorImpl<>(
                () -> new long[2],
                (a, t) -> { a[0] += mapper.applyAsLong(t); a[1]++; },
                (a, b) -> { a[0] += b[0]; a[1] += b[1]; return a; },
                a -> (a[1] == 0) ? 0.0d : (double) a[0] / a[1], CH_NOID);
    }

    /**
     * Returns a {@code Collector} that produces the arithmetic mean of a double-valued
     * function applied to the input elements.  If no elements are present,
     * the result is 0.
     *
     * <p>The average returned can vary depending upon the order in which
     * values are recorded, due to accumulated rounding error in
     * addition of values of differing magnitudes. Values sorted by increasing
     * absolute magnitude tend to yield more accurate results.  If any recorded
     * value is a {@code NaN} or the sum is at any point a {@code NaN} then the
     * average will be {@code NaN}.
     *
     * @implNote The {@code double} format can represent all
     * consecutive integers in the range -2<sup>53</sup> to
     * 2<sup>53</sup>. If the pipeline has more than 2<sup>53</sup>
     * values, the divisor in the average computation will saturate at
     * 2<sup>53</sup>, leading to additional numerical errors.
     *
     * <p>
     * 返回{@code Collector},它会生成应用于输入元素的双值函数的算术平均值。如果没有元素,结果为0。
     * 
     *  <p>由于除了不同大小的值之外的累积舍入误差,返回的平均值可以根据记录值的顺序而变化。通过增加绝对量值排序的值往往产生更准确的结果。
     * 如果任何记录的值是{@code NaN}或总和在任何点{@code NaN},那么平均值将是{@code NaN}。
     * 
     *  @implNote {@code double}格式可以表示-2 <sup> 53 </sup>到2 <sup> 53 </sup>范围内的所有连续整数。
     * 如果流水线具有多于2个</sup>值,则平均计算中的除数将在2 <53> </sup>饱和,导致附加的数字误差。
     * 
     * 
     * @param <T> the type of the input elements
     * @param mapper a function extracting the property to be summed
     * @return a {@code Collector} that produces the sum of a derived property
     */
    public static <T> Collector<T, ?, Double>
    averagingDouble(ToDoubleFunction<? super T> mapper) {
        /*
         * In the arrays allocated for the collect operation, index 0
         * holds the high-order bits of the running sum, index 1 holds
         * the low-order bits of the sum computed via compensated
         * summation, and index 2 holds the number of values seen.
         * <p>
         *  在为收集操作分配的数组中,索引0保存运行总和的高阶位,索引1保存通过补偿求和计算的和的低阶位,索引2保存看到的值的数量。
         * 
         */
        return new CollectorImpl<>(
                () -> new double[4],
                (a, t) -> { sumWithCompensation(a, mapper.applyAsDouble(t)); a[2]++; a[3]+= mapper.applyAsDouble(t);},
                (a, b) -> { sumWithCompensation(a, b[0]); sumWithCompensation(a, b[1]); a[2] += b[2]; a[3] += b[3]; return a; },
                a -> (a[2] == 0) ? 0.0d : (computeFinalSum(a) / a[2]),
                CH_NOID);
    }

    /**
     * Returns a {@code Collector} which performs a reduction of its
     * input elements under a specified {@code BinaryOperator} using the
     * provided identity.
     *
     * @apiNote
     * The {@code reducing()} collectors are most useful when used in a
     * multi-level reduction, downstream of {@code groupingBy} or
     * {@code partitioningBy}.  To perform a simple reduction on a stream,
     * use {@link Stream#reduce(Object, BinaryOperator)}} instead.
     *
     * <p>
     *  返回{@code Collector},它使用提供的身份在指定的{@code BinaryOperator}下执行其输入元素的减少。
     * 
     *  @apiNote {@code reducing()}收集器在{@code groupingBy}或{@code partitioningBy}下游的多级缩减中使用时最为有用。
     * 要对流执行简单的减少,请改用{@link Stream#reduce(Object,BinaryOperator)}}。
     * 
     * 
     * @param <T> element type for the input and output of the reduction
     * @param identity the identity value for the reduction (also, the value
     *                 that is returned when there are no input elements)
     * @param op a {@code BinaryOperator<T>} used to reduce the input elements
     * @return a {@code Collector} which implements the reduction operation
     *
     * @see #reducing(BinaryOperator)
     * @see #reducing(Object, Function, BinaryOperator)
     */
    public static <T> Collector<T, ?, T>
    reducing(T identity, BinaryOperator<T> op) {
        return new CollectorImpl<>(
                boxSupplier(identity),
                (a, t) -> { a[0] = op.apply(a[0], t); },
                (a, b) -> { a[0] = op.apply(a[0], b[0]); return a; },
                a -> a[0],
                CH_NOID);
    }

    @SuppressWarnings("unchecked")
    private static <T> Supplier<T[]> boxSupplier(T identity) {
        return () -> (T[]) new Object[] { identity };
    }

    /**
     * Returns a {@code Collector} which performs a reduction of its
     * input elements under a specified {@code BinaryOperator}.  The result
     * is described as an {@code Optional<T>}.
     *
     * @apiNote
     * The {@code reducing()} collectors are most useful when used in a
     * multi-level reduction, downstream of {@code groupingBy} or
     * {@code partitioningBy}.  To perform a simple reduction on a stream,
     * use {@link Stream#reduce(BinaryOperator)} instead.
     *
     * <p>For example, given a stream of {@code Person}, to calculate tallest
     * person in each city:
     * <pre>{@code
     *     Comparator<Person> byHeight = Comparator.comparing(Person::getHeight);
     *     Map<City, Person> tallestByCity
     *         = people.stream().collect(groupingBy(Person::getCity, reducing(BinaryOperator.maxBy(byHeight))));
     * }</pre>
     *
     * <p>
     * 返回{@code Collector},它在指定的{@code BinaryOperator}下执行其输入元素的减少。结果描述为{@code可选<T>}。
     * 
     *  @apiNote {@code reducing()}收集器在{@code groupingBy}或{@code partitioningBy}下游的多级缩减中使用时最为有用。
     * 要对流执行简单的减少,请改用{@link Stream#reduce(BinaryOperator)}。
     * 
     *  <p>例如,给定{@code Person}流,计算每个城市中最高的人：<pre> {@ code Comparator <Person> byHeight = Comparator.comparing(Person :: getHeight);地图<City,Person> tallestByCity = people.stream()。
     * collect(groupingBy(Person :: getCity,reducing(BinaryOperator.maxBy(byHeight))))); } </pre>。
     * 
     * 
     * @param <T> element type for the input and output of the reduction
     * @param op a {@code BinaryOperator<T>} used to reduce the input elements
     * @return a {@code Collector} which implements the reduction operation
     *
     * @see #reducing(Object, BinaryOperator)
     * @see #reducing(Object, Function, BinaryOperator)
     */
    public static <T> Collector<T, ?, Optional<T>>
    reducing(BinaryOperator<T> op) {
        class OptionalBox implements Consumer<T> {
            T value = null;
            boolean present = false;

            @Override
            public void accept(T t) {
                if (present) {
                    value = op.apply(value, t);
                }
                else {
                    value = t;
                    present = true;
                }
            }
        }

        return new CollectorImpl<T, OptionalBox, Optional<T>>(
                OptionalBox::new, OptionalBox::accept,
                (a, b) -> { if (b.present) a.accept(b.value); return a; },
                a -> Optional.ofNullable(a.value), CH_NOID);
    }

    /**
     * Returns a {@code Collector} which performs a reduction of its
     * input elements under a specified mapping function and
     * {@code BinaryOperator}. This is a generalization of
     * {@link #reducing(Object, BinaryOperator)} which allows a transformation
     * of the elements before reduction.
     *
     * @apiNote
     * The {@code reducing()} collectors are most useful when used in a
     * multi-level reduction, downstream of {@code groupingBy} or
     * {@code partitioningBy}.  To perform a simple map-reduce on a stream,
     * use {@link Stream#map(Function)} and {@link Stream#reduce(Object, BinaryOperator)}
     * instead.
     *
     * <p>For example, given a stream of {@code Person}, to calculate the longest
     * last name of residents in each city:
     * <pre>{@code
     *     Comparator<String> byLength = Comparator.comparing(String::length);
     *     Map<City, String> longestLastNameByCity
     *         = people.stream().collect(groupingBy(Person::getCity,
     *                                              reducing(Person::getLastName, BinaryOperator.maxBy(byLength))));
     * }</pre>
     *
     * <p>
     *  返回{@code Collector},它在指定的映射函数和{@code BinaryOperator}下执行其输入元素的减少。
     * 这是{@link #reducing(Object,BinaryOperator)}的泛化,它允许在缩减之前对元素进行变换。
     * 
     *  @apiNote {@code reducing()}收集器在{@code groupingBy}或{@code partitioningBy}下游的多级缩减中使用时最为有用。
     * 要对流执行简单的map-reduce,请改用{@link Stream#map(Function)}和{@link Stream#reduce(Object,BinaryOperator)}。
     * 
     * <p>例如,给定{@code Person}流,计算每个城市居民的最长姓氏：<pre> {@ code Comparator <String> byLength = Comparator.comparing(String :: length); Map <City,String> longestLastNameByCity = people.stream()。
     * collect(groupingBy(Person :: getCity,reduction(Person :: getLastName,BinaryOperator.maxBy(byLength)))
     * ); } </pre>。
     * 
     * 
     * @param <T> the type of the input elements
     * @param <U> the type of the mapped values
     * @param identity the identity value for the reduction (also, the value
     *                 that is returned when there are no input elements)
     * @param mapper a mapping function to apply to each input value
     * @param op a {@code BinaryOperator<U>} used to reduce the mapped values
     * @return a {@code Collector} implementing the map-reduce operation
     *
     * @see #reducing(Object, BinaryOperator)
     * @see #reducing(BinaryOperator)
     */
    public static <T, U>
    Collector<T, ?, U> reducing(U identity,
                                Function<? super T, ? extends U> mapper,
                                BinaryOperator<U> op) {
        return new CollectorImpl<>(
                boxSupplier(identity),
                (a, t) -> { a[0] = op.apply(a[0], mapper.apply(t)); },
                (a, b) -> { a[0] = op.apply(a[0], b[0]); return a; },
                a -> a[0], CH_NOID);
    }

    /**
     * Returns a {@code Collector} implementing a "group by" operation on
     * input elements of type {@code T}, grouping elements according to a
     * classification function, and returning the results in a {@code Map}.
     *
     * <p>The classification function maps elements to some key type {@code K}.
     * The collector produces a {@code Map<K, List<T>>} whose keys are the
     * values resulting from applying the classification function to the input
     * elements, and whose corresponding values are {@code List}s containing the
     * input elements which map to the associated key under the classification
     * function.
     *
     * <p>There are no guarantees on the type, mutability, serializability, or
     * thread-safety of the {@code Map} or {@code List} objects returned.
     * @implSpec
     * This produces a result similar to:
     * <pre>{@code
     *     groupingBy(classifier, toList());
     * }</pre>
     *
     * @implNote
     * The returned {@code Collector} is not concurrent.  For parallel stream
     * pipelines, the {@code combiner} function operates by merging the keys
     * from one map into another, which can be an expensive operation.  If
     * preservation of the order in which elements appear in the resulting {@code Map}
     * collector is not required, using {@link #groupingByConcurrent(Function)}
     * may offer better parallel performance.
     *
     * <p>
     *  返回{@code Collector}对{@code T}类型的输入元素实施"group by"操作,根据分类函数对元素进行分组,并将结果返回到{@code Map}。
     * 
     *  <p>分类函数将元素映射到某些键类型{@code K}。
     * 收集器产生{@code Map <K,List <T >>},其键是将分类函数应用于输入元素所得到的值,并且其对应值是包含输入元素的{@code List}到分类功能下的相关键。
     * 
     *  <p>对于返回的{@code Map}或{@code List}对象的类型,可变性,可序列化或线程安全性,不提供任何保证。
     *  @implSpec这产生类似的结果：<pre> {@ code groupingBy(classifier,toList()); } </pre>。
     * 
     * @implNote返回的{@code Collector}不是并发的。对于并行流流水线,{@code combiner}函数通过将来自一个映射的密钥合并到另一个映射来操作,这可能是昂贵的操作。
     * 如果不需要保留元素在生成的{@code Map}收集器中出现的顺序,则使用{@link #groupingByConcurrent(Function)}可以提供更好的并行性能。
     * 
     * 
     * @param <T> the type of the input elements
     * @param <K> the type of the keys
     * @param classifier the classifier function mapping input elements to keys
     * @return a {@code Collector} implementing the group-by operation
     *
     * @see #groupingBy(Function, Collector)
     * @see #groupingBy(Function, Supplier, Collector)
     * @see #groupingByConcurrent(Function)
     */
    public static <T, K> Collector<T, ?, Map<K, List<T>>>
    groupingBy(Function<? super T, ? extends K> classifier) {
        return groupingBy(classifier, toList());
    }

    /**
     * Returns a {@code Collector} implementing a cascaded "group by" operation
     * on input elements of type {@code T}, grouping elements according to a
     * classification function, and then performing a reduction operation on
     * the values associated with a given key using the specified downstream
     * {@code Collector}.
     *
     * <p>The classification function maps elements to some key type {@code K}.
     * The downstream collector operates on elements of type {@code T} and
     * produces a result of type {@code D}. The resulting collector produces a
     * {@code Map<K, D>}.
     *
     * <p>There are no guarantees on the type, mutability,
     * serializability, or thread-safety of the {@code Map} returned.
     *
     * <p>For example, to compute the set of last names of people in each city:
     * <pre>{@code
     *     Map<City, Set<String>> namesByCity
     *         = people.stream().collect(groupingBy(Person::getCity,
     *                                              mapping(Person::getLastName, toSet())));
     * }</pre>
     *
     * @implNote
     * The returned {@code Collector} is not concurrent.  For parallel stream
     * pipelines, the {@code combiner} function operates by merging the keys
     * from one map into another, which can be an expensive operation.  If
     * preservation of the order in which elements are presented to the downstream
     * collector is not required, using {@link #groupingByConcurrent(Function, Collector)}
     * may offer better parallel performance.
     *
     * <p>
     *  返回{@code Collector},对类型为{@code T}的输入元素实施级联的"分组依据"操作,根据分类函数对元素进行分组,然后对与给定键相关联的值使用指定下游{@code Collector}
     * 。
     * 
     *  <p>分类函数将元素映射到某些键类型{@code K}。下游收集器对类型为{@code T}的元素进行操作,并生成类型为{@code D}的结果。
     * 所得到的收集器产生{@code Map <K,D>}。
     * 
     *  <p>对于返回的{@code Map}的类型,可变性,可串行化性或线程安全性没有保证。
     * 
     *  <p>例如,计算每个城市的人名的集合：<pre> {@ code Map <City,Set <String >> namesByCity = people.stream()。
     * collect(groupingBy(Person :: getCity ,映射(Person :: getLastName,toSet()))); } </pre>。
     * 
     * @implNote返回的{@code Collector}不是并发的。对于并行流流水线,{@code combiner}函数通过将来自一个映射的密钥合并到另一个映射来操作,这可能是昂贵的操作。
     * 如果不需要保存向下游收集器呈现元素的顺序,则使用{@link #groupingByConcurrent(Function,Collector)}可以提供更好的并行性能。
     * 
     * 
     * @param <T> the type of the input elements
     * @param <K> the type of the keys
     * @param <A> the intermediate accumulation type of the downstream collector
     * @param <D> the result type of the downstream reduction
     * @param classifier a classifier function mapping input elements to keys
     * @param downstream a {@code Collector} implementing the downstream reduction
     * @return a {@code Collector} implementing the cascaded group-by operation
     * @see #groupingBy(Function)
     *
     * @see #groupingBy(Function, Supplier, Collector)
     * @see #groupingByConcurrent(Function, Collector)
     */
    public static <T, K, A, D>
    Collector<T, ?, Map<K, D>> groupingBy(Function<? super T, ? extends K> classifier,
                                          Collector<? super T, A, D> downstream) {
        return groupingBy(classifier, HashMap::new, downstream);
    }

    /**
     * Returns a {@code Collector} implementing a cascaded "group by" operation
     * on input elements of type {@code T}, grouping elements according to a
     * classification function, and then performing a reduction operation on
     * the values associated with a given key using the specified downstream
     * {@code Collector}.  The {@code Map} produced by the Collector is created
     * with the supplied factory function.
     *
     * <p>The classification function maps elements to some key type {@code K}.
     * The downstream collector operates on elements of type {@code T} and
     * produces a result of type {@code D}. The resulting collector produces a
     * {@code Map<K, D>}.
     *
     * <p>For example, to compute the set of last names of people in each city,
     * where the city names are sorted:
     * <pre>{@code
     *     Map<City, Set<String>> namesByCity
     *         = people.stream().collect(groupingBy(Person::getCity, TreeMap::new,
     *                                              mapping(Person::getLastName, toSet())));
     * }</pre>
     *
     * @implNote
     * The returned {@code Collector} is not concurrent.  For parallel stream
     * pipelines, the {@code combiner} function operates by merging the keys
     * from one map into another, which can be an expensive operation.  If
     * preservation of the order in which elements are presented to the downstream
     * collector is not required, using {@link #groupingByConcurrent(Function, Supplier, Collector)}
     * may offer better parallel performance.
     *
     * <p>
     *  返回{@code Collector},对类型为{@code T}的输入元素实施级联的"分组依据"操作,根据分类函数对元素进行分组,然后对与给定键相关联的值使用指定下游{@code Collector}
     * 。
     *  Collector生成的{@code Map}是使用提供的工厂函数创建的。
     * 
     *  <p>分类函数将元素映射到某些键类型{@code K}。下游收集器对类型为{@code T}的元素进行操作,并生成类型为{@code D}的结果。
     * 所得到的收集器产生{@code Map <K,D>}。
     * 
     *  <p>例如,要计算每个城市中的城市名称排序的人名的集合：<pre> {@ code Map <City,Set <String >> namesByCity = people.stream()。
     * collect (groupingBy(Person :: getCity,TreeMap :: new,mapping(Person :: getLastName,toSet()))); } </pre>
     * 。
     *  <p>例如,要计算每个城市中的城市名称排序的人名的集合：<pre> {@ code Map <City,Set <String >> namesByCity = people.stream()。
     * 
     * @implNote返回的{@code Collector}不是并发的。对于并行流流水线,{@code combiner}函数通过将来自一个映射的密钥合并到另一个映射来操作,这可能是昂贵的操作。
     * 如果不需要保存向下游收集器呈现元素的顺序,则使用{@link #groupingByConcurrent(Function,Supplier,Collector)}可以提供更好的并行性能。
     * 
     * 
     * @param <T> the type of the input elements
     * @param <K> the type of the keys
     * @param <A> the intermediate accumulation type of the downstream collector
     * @param <D> the result type of the downstream reduction
     * @param <M> the type of the resulting {@code Map}
     * @param classifier a classifier function mapping input elements to keys
     * @param downstream a {@code Collector} implementing the downstream reduction
     * @param mapFactory a function which, when called, produces a new empty
     *                   {@code Map} of the desired type
     * @return a {@code Collector} implementing the cascaded group-by operation
     *
     * @see #groupingBy(Function, Collector)
     * @see #groupingBy(Function)
     * @see #groupingByConcurrent(Function, Supplier, Collector)
     */
    public static <T, K, D, A, M extends Map<K, D>>
    Collector<T, ?, M> groupingBy(Function<? super T, ? extends K> classifier,
                                  Supplier<M> mapFactory,
                                  Collector<? super T, A, D> downstream) {
        Supplier<A> downstreamSupplier = downstream.supplier();
        BiConsumer<A, ? super T> downstreamAccumulator = downstream.accumulator();
        BiConsumer<Map<K, A>, T> accumulator = (m, t) -> {
            K key = Objects.requireNonNull(classifier.apply(t), "element cannot be mapped to a null key");
            A container = m.computeIfAbsent(key, k -> downstreamSupplier.get());
            downstreamAccumulator.accept(container, t);
        };
        BinaryOperator<Map<K, A>> merger = Collectors.<K, A, Map<K, A>>mapMerger(downstream.combiner());
        @SuppressWarnings("unchecked")
        Supplier<Map<K, A>> mangledFactory = (Supplier<Map<K, A>>) mapFactory;

        if (downstream.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH)) {
            return new CollectorImpl<>(mangledFactory, accumulator, merger, CH_ID);
        }
        else {
            @SuppressWarnings("unchecked")
            Function<A, A> downstreamFinisher = (Function<A, A>) downstream.finisher();
            Function<Map<K, A>, M> finisher = intermediate -> {
                intermediate.replaceAll((k, v) -> downstreamFinisher.apply(v));
                @SuppressWarnings("unchecked")
                M castResult = (M) intermediate;
                return castResult;
            };
            return new CollectorImpl<>(mangledFactory, accumulator, merger, finisher, CH_NOID);
        }
    }

    /**
     * Returns a concurrent {@code Collector} implementing a "group by"
     * operation on input elements of type {@code T}, grouping elements
     * according to a classification function.
     *
     * <p>This is a {@link Collector.Characteristics#CONCURRENT concurrent} and
     * {@link Collector.Characteristics#UNORDERED unordered} Collector.
     *
     * <p>The classification function maps elements to some key type {@code K}.
     * The collector produces a {@code ConcurrentMap<K, List<T>>} whose keys are the
     * values resulting from applying the classification function to the input
     * elements, and whose corresponding values are {@code List}s containing the
     * input elements which map to the associated key under the classification
     * function.
     *
     * <p>There are no guarantees on the type, mutability, or serializability
     * of the {@code Map} or {@code List} objects returned, or of the
     * thread-safety of the {@code List} objects returned.
     * @implSpec
     * This produces a result similar to:
     * <pre>{@code
     *     groupingByConcurrent(classifier, toList());
     * }</pre>
     *
     * <p>
     *  返回对类型为{@code T}的输入元素实施"group by"操作的并发{@code Collector},根据分类函数对元素进行分组。
     * 
     *  <p>这是一个{@link Collector.Characteristics#CONCURRENT concurrent}和{@link Collector.Characteristics#UNORDERED unordered}
     * 收集器。
     * 
     *  <p>分类函数将元素映射到某些键类型{@code K}。
     * 收集器产生{@code ConcurrentMap <K,List <T >>},其键是通过将分类函数应用于输入元素而产生的值,并且其对应值是包含输入元素的{@code List}到分类功能下的相关键。
     *  <p>分类函数将元素映射到某些键类型{@code K}。
     * 
     *  <p>对所返回的{@code Map}或{@code List}对象或返回的{@code List}对象的线程安全性的类型,可变性或可序列性没有保证。
     *  @implSpec这产生类似的结果：<pre> {@ code groupingByConcurrent(classifier,toList()); } </pre>。
     * 
     * 
     * @param <T> the type of the input elements
     * @param <K> the type of the keys
     * @param classifier a classifier function mapping input elements to keys
     * @return a concurrent, unordered {@code Collector} implementing the group-by operation
     *
     * @see #groupingBy(Function)
     * @see #groupingByConcurrent(Function, Collector)
     * @see #groupingByConcurrent(Function, Supplier, Collector)
     */
    public static <T, K>
    Collector<T, ?, ConcurrentMap<K, List<T>>>
    groupingByConcurrent(Function<? super T, ? extends K> classifier) {
        return groupingByConcurrent(classifier, ConcurrentHashMap::new, toList());
    }

    /**
     * Returns a concurrent {@code Collector} implementing a cascaded "group by"
     * operation on input elements of type {@code T}, grouping elements
     * according to a classification function, and then performing a reduction
     * operation on the values associated with a given key using the specified
     * downstream {@code Collector}.
     *
     * <p>This is a {@link Collector.Characteristics#CONCURRENT concurrent} and
     * {@link Collector.Characteristics#UNORDERED unordered} Collector.
     *
     * <p>The classification function maps elements to some key type {@code K}.
     * The downstream collector operates on elements of type {@code T} and
     * produces a result of type {@code D}. The resulting collector produces a
     * {@code Map<K, D>}.
     *
     * <p>For example, to compute the set of last names of people in each city,
     * where the city names are sorted:
     * <pre>{@code
     *     ConcurrentMap<City, Set<String>> namesByCity
     *         = people.stream().collect(groupingByConcurrent(Person::getCity,
     *                                                        mapping(Person::getLastName, toSet())));
     * }</pre>
     *
     * <p>
     * 返回在类型为{@code T}的输入元素上实现级联"分组"操作的并发{@code Collector},根据分类函数对元素进行分组,然后对与给定键相关联的值执行简化操作指定的下游{@code Collector}
     * 。
     * 
     *  <p>这是一个{@link Collector.Characteristics#CONCURRENT concurrent}和{@link Collector.Characteristics#UNORDERED unordered}
     * 收集器。
     * 
     *  <p>分类函数将元素映射到某些键类型{@code K}。下游收集器对类型为{@code T}的元素进行操作,并生成类型为{@code D}的结果。
     * 所得到的收集器产生{@code Map <K,D>}。
     * 
     *  <p>例如,要计算每个城市的人名的集合,其中城市名称排序：<pre> {@ code ConcurrentMap <City,Set <String >> namesByCity = people.stream()。
     * collect (groupingByConcurrent(Person :: getCity,mapping(Person :: getLastName,toSet()))); } </pre>。
     * 
     * 
     * @param <T> the type of the input elements
     * @param <K> the type of the keys
     * @param <A> the intermediate accumulation type of the downstream collector
     * @param <D> the result type of the downstream reduction
     * @param classifier a classifier function mapping input elements to keys
     * @param downstream a {@code Collector} implementing the downstream reduction
     * @return a concurrent, unordered {@code Collector} implementing the cascaded group-by operation
     *
     * @see #groupingBy(Function, Collector)
     * @see #groupingByConcurrent(Function)
     * @see #groupingByConcurrent(Function, Supplier, Collector)
     */
    public static <T, K, A, D>
    Collector<T, ?, ConcurrentMap<K, D>> groupingByConcurrent(Function<? super T, ? extends K> classifier,
                                                              Collector<? super T, A, D> downstream) {
        return groupingByConcurrent(classifier, ConcurrentHashMap::new, downstream);
    }

    /**
     * Returns a concurrent {@code Collector} implementing a cascaded "group by"
     * operation on input elements of type {@code T}, grouping elements
     * according to a classification function, and then performing a reduction
     * operation on the values associated with a given key using the specified
     * downstream {@code Collector}.  The {@code ConcurrentMap} produced by the
     * Collector is created with the supplied factory function.
     *
     * <p>This is a {@link Collector.Characteristics#CONCURRENT concurrent} and
     * {@link Collector.Characteristics#UNORDERED unordered} Collector.
     *
     * <p>The classification function maps elements to some key type {@code K}.
     * The downstream collector operates on elements of type {@code T} and
     * produces a result of type {@code D}. The resulting collector produces a
     * {@code Map<K, D>}.
     *
     * <p>For example, to compute the set of last names of people in each city,
     * where the city names are sorted:
     * <pre>{@code
     *     ConcurrentMap<City, Set<String>> namesByCity
     *         = people.stream().collect(groupingBy(Person::getCity, ConcurrentSkipListMap::new,
     *                                              mapping(Person::getLastName, toSet())));
     * }</pre>
     *
     *
     * <p>
     *  返回在类型为{@code T}的输入元素上实现级联"分组"操作的并发{@code Collector},根据分类函数对元素进行分组,然后对与给定键相关联的值执行简化操作指定的下游{@code Collector}
     * 。
     * 由收集器生成的{@code ConcurrentMap}是使用提供的工厂函数创建的。
     * 
     * <p>这是一个{@link Collector.Characteristics#CONCURRENT concurrent}和{@link Collector.Characteristics#UNORDERED unordered}
     * 收集器。
     * 
     *  <p>分类函数将元素映射到某些键类型{@code K}。下游收集器对类型为{@code T}的元素进行操作,并生成类型为{@code D}的结果。
     * 所得到的收集器产生{@code Map <K,D>}。
     * 
     *  <p>例如,要计算每个城市的人名的集合,其中城市名称排序：<pre> {@ code ConcurrentMap <City,Set <String >> namesByCity = people.stream()。
     * collect (groupingBy(Person :: getCity,ConcurrentSkipListMap :: new,mapping(Person :: getLastName,toSe
     * t()))); } </pre>。
     * 
     * 
     * @param <T> the type of the input elements
     * @param <K> the type of the keys
     * @param <A> the intermediate accumulation type of the downstream collector
     * @param <D> the result type of the downstream reduction
     * @param <M> the type of the resulting {@code ConcurrentMap}
     * @param classifier a classifier function mapping input elements to keys
     * @param downstream a {@code Collector} implementing the downstream reduction
     * @param mapFactory a function which, when called, produces a new empty
     *                   {@code ConcurrentMap} of the desired type
     * @return a concurrent, unordered {@code Collector} implementing the cascaded group-by operation
     *
     * @see #groupingByConcurrent(Function)
     * @see #groupingByConcurrent(Function, Collector)
     * @see #groupingBy(Function, Supplier, Collector)
     */
    public static <T, K, A, D, M extends ConcurrentMap<K, D>>
    Collector<T, ?, M> groupingByConcurrent(Function<? super T, ? extends K> classifier,
                                            Supplier<M> mapFactory,
                                            Collector<? super T, A, D> downstream) {
        Supplier<A> downstreamSupplier = downstream.supplier();
        BiConsumer<A, ? super T> downstreamAccumulator = downstream.accumulator();
        BinaryOperator<ConcurrentMap<K, A>> merger = Collectors.<K, A, ConcurrentMap<K, A>>mapMerger(downstream.combiner());
        @SuppressWarnings("unchecked")
        Supplier<ConcurrentMap<K, A>> mangledFactory = (Supplier<ConcurrentMap<K, A>>) mapFactory;
        BiConsumer<ConcurrentMap<K, A>, T> accumulator;
        if (downstream.characteristics().contains(Collector.Characteristics.CONCURRENT)) {
            accumulator = (m, t) -> {
                K key = Objects.requireNonNull(classifier.apply(t), "element cannot be mapped to a null key");
                A resultContainer = m.computeIfAbsent(key, k -> downstreamSupplier.get());
                downstreamAccumulator.accept(resultContainer, t);
            };
        }
        else {
            accumulator = (m, t) -> {
                K key = Objects.requireNonNull(classifier.apply(t), "element cannot be mapped to a null key");
                A resultContainer = m.computeIfAbsent(key, k -> downstreamSupplier.get());
                synchronized (resultContainer) {
                    downstreamAccumulator.accept(resultContainer, t);
                }
            };
        }

        if (downstream.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH)) {
            return new CollectorImpl<>(mangledFactory, accumulator, merger, CH_CONCURRENT_ID);
        }
        else {
            @SuppressWarnings("unchecked")
            Function<A, A> downstreamFinisher = (Function<A, A>) downstream.finisher();
            Function<ConcurrentMap<K, A>, M> finisher = intermediate -> {
                intermediate.replaceAll((k, v) -> downstreamFinisher.apply(v));
                @SuppressWarnings("unchecked")
                M castResult = (M) intermediate;
                return castResult;
            };
            return new CollectorImpl<>(mangledFactory, accumulator, merger, finisher, CH_CONCURRENT_NOID);
        }
    }

    /**
     * Returns a {@code Collector} which partitions the input elements according
     * to a {@code Predicate}, and organizes them into a
     * {@code Map<Boolean, List<T>>}.
     *
     * There are no guarantees on the type, mutability,
     * serializability, or thread-safety of the {@code Map} returned.
     *
     * <p>
     *  返回一个{@code Collector},它根据{@code Predicate}对输入元素进行分区,并将它们组织成一个{@code Map <Boolean,List <T >>}。
     * 
     *  对返回的{@code Map}的类型,可变性,可串行化性或线程安全性没有保证。
     * 
     * 
     * @param <T> the type of the input elements
     * @param predicate a predicate used for classifying input elements
     * @return a {@code Collector} implementing the partitioning operation
     *
     * @see #partitioningBy(Predicate, Collector)
     */
    public static <T>
    Collector<T, ?, Map<Boolean, List<T>>> partitioningBy(Predicate<? super T> predicate) {
        return partitioningBy(predicate, toList());
    }

    /**
     * Returns a {@code Collector} which partitions the input elements according
     * to a {@code Predicate}, reduces the values in each partition according to
     * another {@code Collector}, and organizes them into a
     * {@code Map<Boolean, D>} whose values are the result of the downstream
     * reduction.
     *
     * <p>There are no guarantees on the type, mutability,
     * serializability, or thread-safety of the {@code Map} returned.
     *
     * <p>
     *  返回一个根据{@code Predicate}分区输入元素的{@code Collector},根据另一个{@code Collector}减少每个分区中的值,并将它们组织成一个{@code Map <Boolean,D> }
     * ,其值是下游减少的结果。
     * 
     *  <p>对于返回的{@code Map}的类型,可变性,可串行化性或线程安全性没有保证。
     * 
     * 
     * @param <T> the type of the input elements
     * @param <A> the intermediate accumulation type of the downstream collector
     * @param <D> the result type of the downstream reduction
     * @param predicate a predicate used for classifying input elements
     * @param downstream a {@code Collector} implementing the downstream
     *                   reduction
     * @return a {@code Collector} implementing the cascaded partitioning
     *         operation
     *
     * @see #partitioningBy(Predicate)
     */
    public static <T, D, A>
    Collector<T, ?, Map<Boolean, D>> partitioningBy(Predicate<? super T> predicate,
                                                    Collector<? super T, A, D> downstream) {
        BiConsumer<A, ? super T> downstreamAccumulator = downstream.accumulator();
        BiConsumer<Partition<A>, T> accumulator = (result, t) ->
                downstreamAccumulator.accept(predicate.test(t) ? result.forTrue : result.forFalse, t);
        BinaryOperator<A> op = downstream.combiner();
        BinaryOperator<Partition<A>> merger = (left, right) ->
                new Partition<>(op.apply(left.forTrue, right.forTrue),
                                op.apply(left.forFalse, right.forFalse));
        Supplier<Partition<A>> supplier = () ->
                new Partition<>(downstream.supplier().get(),
                                downstream.supplier().get());
        if (downstream.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH)) {
            return new CollectorImpl<>(supplier, accumulator, merger, CH_ID);
        }
        else {
            Function<Partition<A>, Map<Boolean, D>> finisher = par ->
                    new Partition<>(downstream.finisher().apply(par.forTrue),
                                    downstream.finisher().apply(par.forFalse));
            return new CollectorImpl<>(supplier, accumulator, merger, finisher, CH_NOID);
        }
    }

    /**
     * Returns a {@code Collector} that accumulates elements into a
     * {@code Map} whose keys and values are the result of applying the provided
     * mapping functions to the input elements.
     *
     * <p>If the mapped keys contains duplicates (according to
     * {@link Object#equals(Object)}), an {@code IllegalStateException} is
     * thrown when the collection operation is performed.  If the mapped keys
     * may have duplicates, use {@link #toMap(Function, Function, BinaryOperator)}
     * instead.
     *
     * @apiNote
     * It is common for either the key or the value to be the input elements.
     * In this case, the utility method
     * {@link java.util.function.Function#identity()} may be helpful.
     * For example, the following produces a {@code Map} mapping
     * students to their grade point average:
     * <pre>{@code
     *     Map<Student, Double> studentToGPA
     *         students.stream().collect(toMap(Functions.identity(),
     *                                         student -> computeGPA(student)));
     * }</pre>
     * And the following produces a {@code Map} mapping a unique identifier to
     * students:
     * <pre>{@code
     *     Map<String, Student> studentIdToStudent
     *         students.stream().collect(toMap(Student::getId,
     *                                         Functions.identity());
     * }</pre>
     *
     * @implNote
     * The returned {@code Collector} is not concurrent.  For parallel stream
     * pipelines, the {@code combiner} function operates by merging the keys
     * from one map into another, which can be an expensive operation.  If it is
     * not required that results are inserted into the {@code Map} in encounter
     * order, using {@link #toConcurrentMap(Function, Function)}
     * may offer better parallel performance.
     *
     * <p>
     * 返回一个{@code Collector},它将元素累积到{@code Map}中,其键和值是将提供的映射函数应用于输入元素的结果。
     * 
     *  <p>如果映射键包含重复项(根据{@link Object#equals(Object)}),则在执行收集操作时会抛出{@code IllegalStateException}。
     * 如果映射的键可能有重复,请改用{@link #toMap(Function,Function,BinaryOperator)}。
     * 
     *  @apiNote键或值通常是输入元素。在这种情况下,实用程序方法{@link java.util.function.Function#identity()}可能会有帮助。
     * 例如,以下产生{@code Map}将学生映射到他们的成绩点平均值：<pre> {@ code Map <student,Double> studentToGPA students.stream()。
     * collect(toMap(Functions.identity - > computeGPA(student))); } </pre>并且下面产生一个{@code Map}映射一个唯一标识符给学生：<pre>
     *  {@ code Map <String,Student> studentIdToStudent students.stream()。
     * 例如,以下产生{@code Map}将学生映射到他们的成绩点平均值：<pre> {@ code Map <student,Double> studentToGPA students.stream()。
     * collect(toMap(Student :: getId, Functions.identity());} </pre>。
     * 
     *  @implNote返回的{@code Collector}不是并发的。对于并行流流水线,{@code combiner}函数通过将来自一个映射的密钥合并到另一个映射来操作,这可能是昂贵的操作。
     * 如果不需要将结果插入到遇到顺序的{@code Map}中,则使用{@link #toConcurrentMap(Function,Function)}可以提供更好的并行性能。
     * 
     * 
     * @param <T> the type of the input elements
     * @param <K> the output type of the key mapping function
     * @param <U> the output type of the value mapping function
     * @param keyMapper a mapping function to produce keys
     * @param valueMapper a mapping function to produce values
     * @return a {@code Collector} which collects elements into a {@code Map}
     * whose keys and values are the result of applying mapping functions to
     * the input elements
     *
     * @see #toMap(Function, Function, BinaryOperator)
     * @see #toMap(Function, Function, BinaryOperator, Supplier)
     * @see #toConcurrentMap(Function, Function)
     */
    public static <T, K, U>
    Collector<T, ?, Map<K,U>> toMap(Function<? super T, ? extends K> keyMapper,
                                    Function<? super T, ? extends U> valueMapper) {
        return toMap(keyMapper, valueMapper, throwingMerger(), HashMap::new);
    }

    /**
     * Returns a {@code Collector} that accumulates elements into a
     * {@code Map} whose keys and values are the result of applying the provided
     * mapping functions to the input elements.
     *
     * <p>If the mapped
     * keys contains duplicates (according to {@link Object#equals(Object)}),
     * the value mapping function is applied to each equal element, and the
     * results are merged using the provided merging function.
     *
     * @apiNote
     * There are multiple ways to deal with collisions between multiple elements
     * mapping to the same key.  The other forms of {@code toMap} simply use
     * a merge function that throws unconditionally, but you can easily write
     * more flexible merge policies.  For example, if you have a stream
     * of {@code Person}, and you want to produce a "phone book" mapping name to
     * address, but it is possible that two persons have the same name, you can
     * do as follows to gracefully deals with these collisions, and produce a
     * {@code Map} mapping names to a concatenated list of addresses:
     * <pre>{@code
     *     Map<String, String> phoneBook
     *         people.stream().collect(toMap(Person::getName,
     *                                       Person::getAddress,
     *                                       (s, a) -> s + ", " + a));
     * }</pre>
     *
     * @implNote
     * The returned {@code Collector} is not concurrent.  For parallel stream
     * pipelines, the {@code combiner} function operates by merging the keys
     * from one map into another, which can be an expensive operation.  If it is
     * not required that results are merged into the {@code Map} in encounter
     * order, using {@link #toConcurrentMap(Function, Function, BinaryOperator)}
     * may offer better parallel performance.
     *
     * <p>
     * 返回一个{@code Collector},它将元素累积到{@code Map}中,其键和值是将提供的映射函数应用于输入元素的结果。
     * 
     *  <p>如果映射键包含重复项(根据{@link Object#equals(Object)}),则值映射函数应用于每个等于元素,并使用提供的合并函数合并结果。
     * 
     *  @apiNote有多种方法来处理映射到同一个键的多个元素之间的冲突。 {@code toMap}的其他形式只是使用无条件抛出的合并函数,但您可以轻松编写更灵活的合并策略。
     * 例如,如果您有一个{@code Person}流,并且想要生成一个"电话簿"映射名称来处理,但可能两个人具有相同的名称,您可以按照以下方式进行优雅的交易并且产生一个{@code Map}映射名称到一个连
     * 接的地址列表：<pre> {@ code Map <String,String> phoneBook people.stream()。
     *  @apiNote有多种方法来处理映射到同一个键的多个元素之间的冲突。 {@code toMap}的其他形式只是使用无条件抛出的合并函数,但您可以轻松编写更灵活的合并策略。
     * collect(toMap(Person :: getName,Person :: getAddress,(s,a) - > s +","+ a)); } </pre>。
     * 
     * @implNote返回的{@code Collector}不是并发的。对于并行流流水线,{@code combiner}函数通过将来自一个映射的密钥合并到另一个映射来操作,这可能是昂贵的操作。
     * 如果不要求结果以遭遇顺序合并到{@code Map}中,则使用{@link #toConcurrentMap(Function,Function,BinaryOperator)}可以提供更好的并行性能。
     * @implNote返回的{@code Collector}不是并发的。对于并行流流水线,{@code combiner}函数通过将来自一个映射的密钥合并到另一个映射来操作,这可能是昂贵的操作。
     * 
     * 
     * @param <T> the type of the input elements
     * @param <K> the output type of the key mapping function
     * @param <U> the output type of the value mapping function
     * @param keyMapper a mapping function to produce keys
     * @param valueMapper a mapping function to produce values
     * @param mergeFunction a merge function, used to resolve collisions between
     *                      values associated with the same key, as supplied
     *                      to {@link Map#merge(Object, Object, BiFunction)}
     * @return a {@code Collector} which collects elements into a {@code Map}
     * whose keys are the result of applying a key mapping function to the input
     * elements, and whose values are the result of applying a value mapping
     * function to all input elements equal to the key and combining them
     * using the merge function
     *
     * @see #toMap(Function, Function)
     * @see #toMap(Function, Function, BinaryOperator, Supplier)
     * @see #toConcurrentMap(Function, Function, BinaryOperator)
     */
    public static <T, K, U>
    Collector<T, ?, Map<K,U>> toMap(Function<? super T, ? extends K> keyMapper,
                                    Function<? super T, ? extends U> valueMapper,
                                    BinaryOperator<U> mergeFunction) {
        return toMap(keyMapper, valueMapper, mergeFunction, HashMap::new);
    }

    /**
     * Returns a {@code Collector} that accumulates elements into a
     * {@code Map} whose keys and values are the result of applying the provided
     * mapping functions to the input elements.
     *
     * <p>If the mapped
     * keys contains duplicates (according to {@link Object#equals(Object)}),
     * the value mapping function is applied to each equal element, and the
     * results are merged using the provided merging function.  The {@code Map}
     * is created by a provided supplier function.
     *
     * @implNote
     * The returned {@code Collector} is not concurrent.  For parallel stream
     * pipelines, the {@code combiner} function operates by merging the keys
     * from one map into another, which can be an expensive operation.  If it is
     * not required that results are merged into the {@code Map} in encounter
     * order, using {@link #toConcurrentMap(Function, Function, BinaryOperator, Supplier)}
     * may offer better parallel performance.
     *
     * <p>
     *  返回一个{@code Collector},它将元素累积到{@code Map}中,其键和值是将提供的映射函数应用于输入元素的结果。
     * 
     *  <p>如果映射键包含重复项(根据{@link Object#equals(Object)}),则值映射函数应用于每个等于元素,并使用提供的合并函数合并结果。
     *  {@code Map}由提供的供应商函数创建。
     * 
     *  @implNote返回的{@code Collector}不是并发的。对于并行流流水线,{@code combiner}函数通过将来自一个映射的密钥合并到另一个映射来操作,这可能是昂贵的操作。
     * 如果不要求结果以遭遇顺序合并到{@code Map}中,则使用{@link #toConcurrentMap(Function,Function,BinaryOperator,Supplier)}可以提
     * 供更好的并行性能。
     *  @implNote返回的{@code Collector}不是并发的。对于并行流流水线,{@code combiner}函数通过将来自一个映射的密钥合并到另一个映射来操作,这可能是昂贵的操作。
     * 
     * 
     * @param <T> the type of the input elements
     * @param <K> the output type of the key mapping function
     * @param <U> the output type of the value mapping function
     * @param <M> the type of the resulting {@code Map}
     * @param keyMapper a mapping function to produce keys
     * @param valueMapper a mapping function to produce values
     * @param mergeFunction a merge function, used to resolve collisions between
     *                      values associated with the same key, as supplied
     *                      to {@link Map#merge(Object, Object, BiFunction)}
     * @param mapSupplier a function which returns a new, empty {@code Map} into
     *                    which the results will be inserted
     * @return a {@code Collector} which collects elements into a {@code Map}
     * whose keys are the result of applying a key mapping function to the input
     * elements, and whose values are the result of applying a value mapping
     * function to all input elements equal to the key and combining them
     * using the merge function
     *
     * @see #toMap(Function, Function)
     * @see #toMap(Function, Function, BinaryOperator)
     * @see #toConcurrentMap(Function, Function, BinaryOperator, Supplier)
     */
    public static <T, K, U, M extends Map<K, U>>
    Collector<T, ?, M> toMap(Function<? super T, ? extends K> keyMapper,
                                Function<? super T, ? extends U> valueMapper,
                                BinaryOperator<U> mergeFunction,
                                Supplier<M> mapSupplier) {
        BiConsumer<M, T> accumulator
                = (map, element) -> map.merge(keyMapper.apply(element),
                                              valueMapper.apply(element), mergeFunction);
        return new CollectorImpl<>(mapSupplier, accumulator, mapMerger(mergeFunction), CH_ID);
    }

    /**
     * Returns a concurrent {@code Collector} that accumulates elements into a
     * {@code ConcurrentMap} whose keys and values are the result of applying
     * the provided mapping functions to the input elements.
     *
     * <p>If the mapped keys contains duplicates (according to
     * {@link Object#equals(Object)}), an {@code IllegalStateException} is
     * thrown when the collection operation is performed.  If the mapped keys
     * may have duplicates, use
     * {@link #toConcurrentMap(Function, Function, BinaryOperator)} instead.
     *
     * @apiNote
     * It is common for either the key or the value to be the input elements.
     * In this case, the utility method
     * {@link java.util.function.Function#identity()} may be helpful.
     * For example, the following produces a {@code Map} mapping
     * students to their grade point average:
     * <pre>{@code
     *     Map<Student, Double> studentToGPA
     *         students.stream().collect(toMap(Functions.identity(),
     *                                         student -> computeGPA(student)));
     * }</pre>
     * And the following produces a {@code Map} mapping a unique identifier to
     * students:
     * <pre>{@code
     *     Map<String, Student> studentIdToStudent
     *         students.stream().collect(toConcurrentMap(Student::getId,
     *                                                   Functions.identity());
     * }</pre>
     *
     * <p>This is a {@link Collector.Characteristics#CONCURRENT concurrent} and
     * {@link Collector.Characteristics#UNORDERED unordered} Collector.
     *
     * <p>
     *  返回一个并发{@code Collector},它将元素累积到{@code ConcurrentMap}中,其键和值是将提供的映射函数应用于输入元素的结果。
     * 
     * <p>如果映射键包含重复项(根据{@link Object#equals(Object)}),则在执行收集操作时会抛出{@code IllegalStateException}。
     * 如果映射的键可能有重复,请改用{@link #toConcurrentMap(Function,Function,BinaryOperator)}。
     * 
     *  @apiNote键或值通常是输入元素。在这种情况下,实用程序方法{@link java.util.function.Function#identity()}可能会有帮助。
     * 例如,以下产生{@code Map}将学生映射到他们的成绩点平均值：<pre> {@ code Map <student,Double> studentToGPA students.stream()。
     * collect(toMap(Functions.identity - > computeGPA(student))); } </pre>并且下面产生一个{@code Map}映射一个唯一标识符给学生：<pre>
     *  {@ code Map <String,Student> studentIdToStudent students.stream()。
     * 例如,以下产生{@code Map}将学生映射到他们的成绩点平均值：<pre> {@ code Map <student,Double> studentToGPA students.stream()。
     * collect(toConcurrentMap(Student :: getId, Functions.identity());} </pre>。
     * 
     *  <p>这是一个{@link Collector.Characteristics#CONCURRENT concurrent}和{@link Collector.Characteristics#UNORDERED unordered}
     * 收集器。
     * 
     * 
     * @param <T> the type of the input elements
     * @param <K> the output type of the key mapping function
     * @param <U> the output type of the value mapping function
     * @param keyMapper the mapping function to produce keys
     * @param valueMapper the mapping function to produce values
     * @return a concurrent, unordered {@code Collector} which collects elements into a
     * {@code ConcurrentMap} whose keys are the result of applying a key mapping
     * function to the input elements, and whose values are the result of
     * applying a value mapping function to the input elements
     *
     * @see #toMap(Function, Function)
     * @see #toConcurrentMap(Function, Function, BinaryOperator)
     * @see #toConcurrentMap(Function, Function, BinaryOperator, Supplier)
     */
    public static <T, K, U>
    Collector<T, ?, ConcurrentMap<K,U>> toConcurrentMap(Function<? super T, ? extends K> keyMapper,
                                                        Function<? super T, ? extends U> valueMapper) {
        return toConcurrentMap(keyMapper, valueMapper, throwingMerger(), ConcurrentHashMap::new);
    }

    /**
     * Returns a concurrent {@code Collector} that accumulates elements into a
     * {@code ConcurrentMap} whose keys and values are the result of applying
     * the provided mapping functions to the input elements.
     *
     * <p>If the mapped keys contains duplicates (according to {@link Object#equals(Object)}),
     * the value mapping function is applied to each equal element, and the
     * results are merged using the provided merging function.
     *
     * @apiNote
     * There are multiple ways to deal with collisions between multiple elements
     * mapping to the same key.  The other forms of {@code toConcurrentMap} simply use
     * a merge function that throws unconditionally, but you can easily write
     * more flexible merge policies.  For example, if you have a stream
     * of {@code Person}, and you want to produce a "phone book" mapping name to
     * address, but it is possible that two persons have the same name, you can
     * do as follows to gracefully deals with these collisions, and produce a
     * {@code Map} mapping names to a concatenated list of addresses:
     * <pre>{@code
     *     Map<String, String> phoneBook
     *         people.stream().collect(toConcurrentMap(Person::getName,
     *                                                 Person::getAddress,
     *                                                 (s, a) -> s + ", " + a));
     * }</pre>
     *
     * <p>This is a {@link Collector.Characteristics#CONCURRENT concurrent} and
     * {@link Collector.Characteristics#UNORDERED unordered} Collector.
     *
     * <p>
     *  返回一个并发{@code Collector},它将元素累积到{@code ConcurrentMap}中,其键和值是将提供的映射函数应用于输入元素的结果。
     * 
     *  <p>如果映射键包含重复项(根据{@link Object#equals(Object)}),则值映射函数应用于每个等于元素,并使用提供的合并函数合并结果。
     * 
     * @apiNote有多种方法来处理映射到同一个键的多个元素之间的冲突。 {@code toConcurrentMap}的其他形式只使用无条件抛出的合并函数,但您可以轻松编写更灵活的合并策略。
     * 例如,如果您有一个{@code Person}流,并且想要生成一个"电话簿"映射名称来处理,但可能两个人具有相同的名称,您可以按照以下方式进行优雅的交易并且生成一个{@code Map}映射名称到一个连
     * 接的地址列表：<pre> {@ code Map <String,String> phoneBook people.stream()。
     * @apiNote有多种方法来处理映射到同一个键的多个元素之间的冲突。 {@code toConcurrentMap}的其他形式只使用无条件抛出的合并函数,但您可以轻松编写更灵活的合并策略。
     * collect(toConcurrentMap(Person :: getName,Person :: getAddress,(s,a) - > s +","+ a)); } </pre>。
     * 
     *  <p>这是一个{@link Collector.Characteristics#CONCURRENT concurrent}和{@link Collector.Characteristics#UNORDERED unordered}
     * 收集器。
     * 
     * 
     * @param <T> the type of the input elements
     * @param <K> the output type of the key mapping function
     * @param <U> the output type of the value mapping function
     * @param keyMapper a mapping function to produce keys
     * @param valueMapper a mapping function to produce values
     * @param mergeFunction a merge function, used to resolve collisions between
     *                      values associated with the same key, as supplied
     *                      to {@link Map#merge(Object, Object, BiFunction)}
     * @return a concurrent, unordered {@code Collector} which collects elements into a
     * {@code ConcurrentMap} whose keys are the result of applying a key mapping
     * function to the input elements, and whose values are the result of
     * applying a value mapping function to all input elements equal to the key
     * and combining them using the merge function
     *
     * @see #toConcurrentMap(Function, Function)
     * @see #toConcurrentMap(Function, Function, BinaryOperator, Supplier)
     * @see #toMap(Function, Function, BinaryOperator)
     */
    public static <T, K, U>
    Collector<T, ?, ConcurrentMap<K,U>>
    toConcurrentMap(Function<? super T, ? extends K> keyMapper,
                    Function<? super T, ? extends U> valueMapper,
                    BinaryOperator<U> mergeFunction) {
        return toConcurrentMap(keyMapper, valueMapper, mergeFunction, ConcurrentHashMap::new);
    }

    /**
     * Returns a concurrent {@code Collector} that accumulates elements into a
     * {@code ConcurrentMap} whose keys and values are the result of applying
     * the provided mapping functions to the input elements.
     *
     * <p>If the mapped keys contains duplicates (according to {@link Object#equals(Object)}),
     * the value mapping function is applied to each equal element, and the
     * results are merged using the provided merging function.  The
     * {@code ConcurrentMap} is created by a provided supplier function.
     *
     * <p>This is a {@link Collector.Characteristics#CONCURRENT concurrent} and
     * {@link Collector.Characteristics#UNORDERED unordered} Collector.
     *
     * <p>
     *  返回一个并发{@code Collector},它将元素累积到{@code ConcurrentMap}中,其键和值是将提供的映射函数应用于输入元素的结果。
     * 
     *  <p>如果映射键包含重复项(根据{@link Object#equals(Object)}),则值映射函数应用于每个等于元素,并使用提供的合并函数合并结果。
     *  {@code ConcurrentMap}由提供的供应商函数创建。
     * 
     *  <p>这是一个{@link Collector.Characteristics#CONCURRENT concurrent}和{@link Collector.Characteristics#UNORDERED unordered}
     * 收集器。
     * 
     * 
     * @param <T> the type of the input elements
     * @param <K> the output type of the key mapping function
     * @param <U> the output type of the value mapping function
     * @param <M> the type of the resulting {@code ConcurrentMap}
     * @param keyMapper a mapping function to produce keys
     * @param valueMapper a mapping function to produce values
     * @param mergeFunction a merge function, used to resolve collisions between
     *                      values associated with the same key, as supplied
     *                      to {@link Map#merge(Object, Object, BiFunction)}
     * @param mapSupplier a function which returns a new, empty {@code Map} into
     *                    which the results will be inserted
     * @return a concurrent, unordered {@code Collector} which collects elements into a
     * {@code ConcurrentMap} whose keys are the result of applying a key mapping
     * function to the input elements, and whose values are the result of
     * applying a value mapping function to all input elements equal to the key
     * and combining them using the merge function
     *
     * @see #toConcurrentMap(Function, Function)
     * @see #toConcurrentMap(Function, Function, BinaryOperator)
     * @see #toMap(Function, Function, BinaryOperator, Supplier)
     */
    public static <T, K, U, M extends ConcurrentMap<K, U>>
    Collector<T, ?, M> toConcurrentMap(Function<? super T, ? extends K> keyMapper,
                                       Function<? super T, ? extends U> valueMapper,
                                       BinaryOperator<U> mergeFunction,
                                       Supplier<M> mapSupplier) {
        BiConsumer<M, T> accumulator
                = (map, element) -> map.merge(keyMapper.apply(element),
                                              valueMapper.apply(element), mergeFunction);
        return new CollectorImpl<>(mapSupplier, accumulator, mapMerger(mergeFunction), CH_CONCURRENT_ID);
    }

    /**
     * Returns a {@code Collector} which applies an {@code int}-producing
     * mapping function to each input element, and returns summary statistics
     * for the resulting values.
     *
     * <p>
     * 返回{@code Collector},其将{@code int}生成映射函数应用于每个输入元素,并返回结果值的摘要统计信息。
     * 
     * 
     * @param <T> the type of the input elements
     * @param mapper a mapping function to apply to each element
     * @return a {@code Collector} implementing the summary-statistics reduction
     *
     * @see #summarizingDouble(ToDoubleFunction)
     * @see #summarizingLong(ToLongFunction)
     */
    public static <T>
    Collector<T, ?, IntSummaryStatistics> summarizingInt(ToIntFunction<? super T> mapper) {
        return new CollectorImpl<T, IntSummaryStatistics, IntSummaryStatistics>(
                IntSummaryStatistics::new,
                (r, t) -> r.accept(mapper.applyAsInt(t)),
                (l, r) -> { l.combine(r); return l; }, CH_ID);
    }

    /**
     * Returns a {@code Collector} which applies an {@code long}-producing
     * mapping function to each input element, and returns summary statistics
     * for the resulting values.
     *
     * <p>
     *  返回{@code Collector},其对每个输入元素应用{@code long}生成映射函数,并返回结果值的摘要统计信息。
     * 
     * 
     * @param <T> the type of the input elements
     * @param mapper the mapping function to apply to each element
     * @return a {@code Collector} implementing the summary-statistics reduction
     *
     * @see #summarizingDouble(ToDoubleFunction)
     * @see #summarizingInt(ToIntFunction)
     */
    public static <T>
    Collector<T, ?, LongSummaryStatistics> summarizingLong(ToLongFunction<? super T> mapper) {
        return new CollectorImpl<T, LongSummaryStatistics, LongSummaryStatistics>(
                LongSummaryStatistics::new,
                (r, t) -> r.accept(mapper.applyAsLong(t)),
                (l, r) -> { l.combine(r); return l; }, CH_ID);
    }

    /**
     * Returns a {@code Collector} which applies an {@code double}-producing
     * mapping function to each input element, and returns summary statistics
     * for the resulting values.
     *
     * <p>
     *  返回{@code Collector},它对每个输入元素应用{@code double}生成映射函数,并返回结果值的摘要统计信息。
     * 
     * 
     * @param <T> the type of the input elements
     * @param mapper a mapping function to apply to each element
     * @return a {@code Collector} implementing the summary-statistics reduction
     *
     * @see #summarizingLong(ToLongFunction)
     * @see #summarizingInt(ToIntFunction)
     */
    public static <T>
    Collector<T, ?, DoubleSummaryStatistics> summarizingDouble(ToDoubleFunction<? super T> mapper) {
        return new CollectorImpl<T, DoubleSummaryStatistics, DoubleSummaryStatistics>(
                DoubleSummaryStatistics::new,
                (r, t) -> r.accept(mapper.applyAsDouble(t)),
                (l, r) -> { l.combine(r); return l; }, CH_ID);
    }

    /**
     * Implementation class used by partitioningBy.
     * <p>
     *  由partitioningBy使用的实现类。
     */
    private static final class Partition<T>
            extends AbstractMap<Boolean, T>
            implements Map<Boolean, T> {
        final T forTrue;
        final T forFalse;

        Partition(T forTrue, T forFalse) {
            this.forTrue = forTrue;
            this.forFalse = forFalse;
        }

        @Override
        public Set<Map.Entry<Boolean, T>> entrySet() {
            return new AbstractSet<Map.Entry<Boolean, T>>() {
                @Override
                public Iterator<Map.Entry<Boolean, T>> iterator() {
                    Map.Entry<Boolean, T> falseEntry = new SimpleImmutableEntry<>(false, forFalse);
                    Map.Entry<Boolean, T> trueEntry = new SimpleImmutableEntry<>(true, forTrue);
                    return Arrays.asList(falseEntry, trueEntry).iterator();
                }

                @Override
                public int size() {
                    return 2;
                }
            };
        }
    }
}
