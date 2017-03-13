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

import java.util.Collections;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A <a href="package-summary.html#Reduction">mutable reduction operation</a> that
 * accumulates input elements into a mutable result container, optionally transforming
 * the accumulated result into a final representation after all input elements
 * have been processed.  Reduction operations can be performed either sequentially
 * or in parallel.
 *
 * <p>Examples of mutable reduction operations include:
 * accumulating elements into a {@code Collection}; concatenating
 * strings using a {@code StringBuilder}; computing summary information about
 * elements such as sum, min, max, or average; computing "pivot table" summaries
 * such as "maximum valued transaction by seller", etc.  The class {@link Collectors}
 * provides implementations of many common mutable reductions.
 *
 * <p>A {@code Collector} is specified by four functions that work together to
 * accumulate entries into a mutable result container, and optionally perform
 * a final transform on the result.  They are: <ul>
 *     <li>creation of a new result container ({@link #supplier()})</li>
 *     <li>incorporating a new data element into a result container ({@link #accumulator()})</li>
 *     <li>combining two result containers into one ({@link #combiner()})</li>
 *     <li>performing an optional final transform on the container ({@link #finisher()})</li>
 * </ul>
 *
 * <p>Collectors also have a set of characteristics, such as
 * {@link Characteristics#CONCURRENT}, that provide hints that can be used by a
 * reduction implementation to provide better performance.
 *
 * <p>A sequential implementation of a reduction using a collector would
 * create a single result container using the supplier function, and invoke the
 * accumulator function once for each input element.  A parallel implementation
 * would partition the input, create a result container for each partition,
 * accumulate the contents of each partition into a subresult for that partition,
 * and then use the combiner function to merge the subresults into a combined
 * result.
 *
 * <p>To ensure that sequential and parallel executions produce equivalent
 * results, the collector functions must satisfy an <em>identity</em> and an
 * <a href="package-summary.html#Associativity">associativity</a> constraints.
 *
 * <p>The identity constraint says that for any partially accumulated result,
 * combining it with an empty result container must produce an equivalent
 * result.  That is, for a partially accumulated result {@code a} that is the
 * result of any series of accumulator and combiner invocations, {@code a} must
 * be equivalent to {@code combiner.apply(a, supplier.get())}.
 *
 * <p>The associativity constraint says that splitting the computation must
 * produce an equivalent result.  That is, for any input elements {@code t1}
 * and {@code t2}, the results {@code r1} and {@code r2} in the computation
 * below must be equivalent:
 * <pre>{@code
 *     A a1 = supplier.get();
 *     accumulator.accept(a1, t1);
 *     accumulator.accept(a1, t2);
 *     R r1 = finisher.apply(a1);  // result without splitting
 *
 *     A a2 = supplier.get();
 *     accumulator.accept(a2, t1);
 *     A a3 = supplier.get();
 *     accumulator.accept(a3, t2);
 *     R r2 = finisher.apply(combiner.apply(a2, a3));  // result with splitting
 * } </pre>
 *
 * <p>For collectors that do not have the {@code UNORDERED} characteristic,
 * two accumulated results {@code a1} and {@code a2} are equivalent if
 * {@code finisher.apply(a1).equals(finisher.apply(a2))}.  For unordered
 * collectors, equivalence is relaxed to allow for non-equality related to
 * differences in order.  (For example, an unordered collector that accumulated
 * elements to a {@code List} would consider two lists equivalent if they
 * contained the same elements, ignoring order.)
 *
 * <p>Libraries that implement reduction based on {@code Collector}, such as
 * {@link Stream#collect(Collector)}, must adhere to the following constraints:
 * <ul>
 *     <li>The first argument passed to the accumulator function, both
 *     arguments passed to the combiner function, and the argument passed to the
 *     finisher function must be the result of a previous invocation of the
 *     result supplier, accumulator, or combiner functions.</li>
 *     <li>The implementation should not do anything with the result of any of
 *     the result supplier, accumulator, or combiner functions other than to
 *     pass them again to the accumulator, combiner, or finisher functions,
 *     or return them to the caller of the reduction operation.</li>
 *     <li>If a result is passed to the combiner or finisher
 *     function, and the same object is not returned from that function, it is
 *     never used again.</li>
 *     <li>Once a result is passed to the combiner or finisher function, it
 *     is never passed to the accumulator function again.</li>
 *     <li>For non-concurrent collectors, any result returned from the result
 *     supplier, accumulator, or combiner functions must be serially
 *     thread-confined.  This enables collection to occur in parallel without
 *     the {@code Collector} needing to implement any additional synchronization.
 *     The reduction implementation must manage that the input is properly
 *     partitioned, that partitions are processed in isolation, and combining
 *     happens only after accumulation is complete.</li>
 *     <li>For concurrent collectors, an implementation is free to (but not
 *     required to) implement reduction concurrently.  A concurrent reduction
 *     is one where the accumulator function is called concurrently from
 *     multiple threads, using the same concurrently-modifiable result container,
 *     rather than keeping the result isolated during accumulation.
 *     A concurrent reduction should only be applied if the collector has the
 *     {@link Characteristics#UNORDERED} characteristics or if the
 *     originating data is unordered.</li>
 * </ul>
 *
 * <p>In addition to the predefined implementations in {@link Collectors}, the
 * static factory methods {@link #of(Supplier, BiConsumer, BinaryOperator, Characteristics...)}
 * can be used to construct collectors.  For example, you could create a collector
 * that accumulates widgets into a {@code TreeSet} with:
 *
 * <pre>{@code
 *     Collector<Widget, ?, TreeSet<Widget>> intoSet =
 *         Collector.of(TreeSet::new, TreeSet::add,
 *                      (left, right) -> { left.addAll(right); return left; });
 * }</pre>
 *
 * (This behavior is also implemented by the predefined collector
 * {@link Collectors#toCollection(Supplier)}).
 *
 * @apiNote
 * Performing a reduction operation with a {@code Collector} should produce a
 * result equivalent to:
 * <pre>{@code
 *     R container = collector.supplier().get();
 *     for (T t : data)
 *         collector.accumulator().accept(container, t);
 *     return collector.finisher().apply(container);
 * }</pre>
 *
 * <p>However, the library is free to partition the input, perform the reduction
 * on the partitions, and then use the combiner function to combine the partial
 * results to achieve a parallel reduction.  (Depending on the specific reduction
 * operation, this may perform better or worse, depending on the relative cost
 * of the accumulator and combiner functions.)
 *
 * <p>Collectors are designed to be <em>composed</em>; many of the methods
 * in {@link Collectors} are functions that take a collector and produce
 * a new collector.  For example, given the following collector that computes
 * the sum of the salaries of a stream of employees:
 *
 * <pre>{@code
 *     Collector<Employee, ?, Integer> summingSalaries
 *         = Collectors.summingInt(Employee::getSalary))
 * }</pre>
 *
 * If we wanted to create a collector to tabulate the sum of salaries by
 * department, we could reuse the "sum of salaries" logic using
 * {@link Collectors#groupingBy(Function, Collector)}:
 *
 * <pre>{@code
 *     Collector<Employee, ?, Map<Department, Integer>> summingSalariesByDept
 *         = Collectors.groupingBy(Employee::getDepartment, summingSalaries);
 * }</pre>
 *
 * <p>
 *  将输入元素累积到可变结果容器中的<a href=\"package-summaryhtml#Reduction\">可变缩减操作</a>,可选地在所有输入元素都被处理之后将累积结果转换为最终表示缩减操
 * 作可以是顺序地或并行地执行。
 * 
 * <p>可变缩减操作的示例包括：将元素累积到{@code Collection};使用{@code StringBuilder}连接字符串;计算关于元素的摘要信息,例如sum,min,max或averag
 * e;计算"枢轴表"摘要,如"卖方的最大价值交易"等。
 * {@link Collectors}提供许多常见可变减少的实现。
 * 
 * <p> {@code Collector}由四个函数指定,这四个函数一起工作以将条目累积到可变结果容器中,并且可选地对结果执行最终变换。
 * 它们是：<ul> <li>创建新的结果容器({@link #supplier()})</li> <li>将新数据元素合并到结果容器({@link #accumulator()})</li> <li>将两
 * 个结果容器合并为一个{@link #combiner()})</li> <li>对容器执行可选的最终转换({@link #finisher()})</li>。
 * <p> {@code Collector}由四个函数指定,这四个函数一起工作以将条目累积到可变结果容器中,并且可选地对结果执行最终变换。
 * </ul>
 * 
 *  <p>收集器还具有一组特性,例如{@link Characteristics#CONCURRENT},它们提供可以由缩减实施使用的提示,以提供更好的性能
 * 
 * <p>使用收集器的减少的顺序实现将使用供应商函数创建单个结果容器,并为每个输入元素调用一次累加器函数。
 * 并行实现将分区输入,为每个分区创建结果容器,累积将每个分区的内容转换成用于该分区的子结果,然后使用组合器函数将子结果合并成组合结果。
 * 
 *  <p>为了确保顺序和并行执行产生相同的结果,收集器函数必须满足<em>标识</em>和<a href=\"package-summaryhtml#Associativity\">关联性</a>约束
 * 
 * <p>身份约束说,对于任何部分累加结果,将其与空结果容器组合必须产生等效结果。
 * 对于部分累加结果{@code a},它是任何系列累加器和组合器的结果调用,{@code a}必须等同于{@code combinerapply(a,supplierget())}。
 * 
 *  <p>相关性约束说,分割计算必须产生等效的结果。
 * 对于任何输入元素{@code t1}和{@code t2},结果{@code r1}和{@code r2}下面的计算必须是等效的：<pre> {@ code A a1 = supplierget(); accumulatoraccept(a1,t1); accumulatoraccept(a1,t2); R r1 = finisherapply(a1); // result without splitting。
 *  <p>相关性约束说,分割计算必须产生等效的结果。
 * 
 * A a2 = supplierget(); accumulatoraccept(a2,t1); A a3 = supplierget(); accumulatoraccept(a3,t2); R r2 
 * = finisherapply(combinerapply(a2,a3)); // result with splitting} </pre>。
 * 
 *  <p>对于没有{@code UNORDERED}特征的收集器,如果{@code finisherapply(a1)equals(finisherapply(a2))},则两个累积结果{@code a1}
 * 和{@code a2}无序收集器,放宽等价以允许与顺序不同相关的不相等(例如,如果一个{@code List}累积元素的无序收集器如果它们包含相同的元素,忽略顺序则认为两个列表是等价的)。
 * 
 * <p>基于{@code Collector}实现缩减的库,例如{@link Stream#collect(Collector)},必须遵守以下约束：
 * <ul>
 * <li>传递给累加器函数的第一个参数,传递给组合器函数的参数和传递给finisher函数的参数必须是之前调用结果提供者,累加器或组合器函数的结果。
 * </li> <li>实现不应对结果提供者,累加器或组合器函数的任何结果做任何事情,除非将它们再次传递到累加器,组合器或修整器函数,或将它们返回到简化操作的调用者</li> <li>如果结果传递到组合器或
 * 修整器函数,并且不从该函数返回相同的对象,则不会再次使用</li> <li>一旦结果传递到组合器或修整器函数,它就不会再次传递到累加器函数。
 * <li>传递给累加器函数的第一个参数,传递给组合器函数的参数和传递给finisher函数的参数必须是之前调用结果提供者,累加器或组合器函数的结果。
 * </li> <li>对于非并发收集器,从结果提供者返回的任何结果,或者组合器函数必须是串行线程限制的这使得集合可以并行发生,而{@code Collector}不需要实现任何额外的同步。
 * 减少实现必须管理输入被正确分区,分区被隔离处理,以及</li> <li>对于并发收集器,实现可以自由地(但不是必须)同时实现缩减并发还原是指使用相同的可并发更改的结果容器,从多个线程并发调用累加器函数,
 * 而不是在累积期间保持结果隔离。
 * </li> <li>对于非并发收集器,从结果提供者返回的任何结果,或者组合器函数必须是串行线程限制的这使得集合可以并行发生,而{@code Collector}不需要实现任何额外的同步。
 * 只有收集器具有{@link Characteristics# UNORDERED}特性,或者如果始发数据是无序的</li>。
 * </ul>
 * 
 * <p>除了{@link Collectors}中的预定义实现之外,静态工厂方法{@link #of(Supplier,BiConsumer,BinaryOperator,Characteristics)}
 * 可用于构造收集器。
 * 例如,您可以创建一个收集器将窗口小部件累积到{@code TreeSet}中：。
 * 
 *  <pre> {@ code Collector <Widget,?,TreeSet <Widget >> intoSet = Collectorof(TreeSet :: new,TreeSet :: add,(left,right) - > {leftaddAll(right); return left;}
 * ); } </pre>。
 * 
 *  (此行为也由预定义收集器{@link Collectors#toCollection(Supplier)}实现)
 * 
 * @see Stream#collect(Collector)
 * @see Collectors
 *
 * @param <T> the type of input elements to the reduction operation
 * @param <A> the mutable accumulation type of the reduction operation (often
 *            hidden as an implementation detail)
 * @param <R> the result type of the reduction operation
 * @since 1.8
 */
public interface Collector<T, A, R> {
    /**
     * A function that creates and returns a new mutable result container.
     *
     * <p>
     * 
     * @apiNote使用{@code Collector}执行缩减操作应该产生等效于以下结果的结果：<pre> {@ code R container = collectorsupplier()get(); for(T t：data)collectoraccumulator()accept(container,t); return collectorfinisher()apply(container); }
     *  </pre>。
     * 
     *  <p>然而,库可以自由分区输入,对分区执行缩减,然后使用组合器函数组合部分结果以实现并行缩减(根据具体的缩减操作,这可能执行更好或更糟的是,取决于累加器和组合器功能的相对成本)
     * 
     * <p>收集器设计为<em>组成</em>; {@link Collectors}中的许多方法都是需要收集器并生成新收集器的函数。例如,假定有以下收集器计算员工流的工资总和：
     * 
     *  <pre> {@ code Collector <Employee,?,Integer> summingSalaries = CollectorssummingInt(Employee :: getSalary))}
     *  </pre>。
     * 
     *  如果我们想创建一个收集器来按部门列出工资的总和,我们可以使用{@link Collectors#groupingBy(Function,Collector)}重用"工资总和"逻辑：
     * 
     *  <pre> {@ code Collector <Employee,?,Map <Department,Integer >> summingSalariesByDept = CollectorsgroupingBy(Employee :: getDepartment,summingSalaries); }
     *  </pre>。
     * 
     * 
     * @return a function which returns a new, mutable result container
     */
    Supplier<A> supplier();

    /**
     * A function that folds a value into a mutable result container.
     *
     * <p>
     *  创建并返回新的可变结果容器的函数
     * 
     * 
     * @return a function which folds a value into a mutable result container
     */
    BiConsumer<A, T> accumulator();

    /**
     * A function that accepts two partial results and merges them.  The
     * combiner function may fold state from one argument into the other and
     * return that, or may return a new result container.
     *
     * <p>
     * 将值折叠到可变结果容器中的函数
     * 
     * 
     * @return a function which combines two partial results into a combined
     * result
     */
    BinaryOperator<A> combiner();

    /**
     * Perform the final transformation from the intermediate accumulation type
     * {@code A} to the final result type {@code R}.
     *
     * <p>If the characteristic {@code IDENTITY_TRANSFORM} is
     * set, this function may be presumed to be an identity transform with an
     * unchecked cast from {@code A} to {@code R}.
     *
     * <p>
     *  接受两个部分结果并合并它们的函数组合器函数可以将状态从一个参数折叠到另一个参数并返回,或者可以返回一个新的结果容器
     * 
     * 
     * @return a function which transforms the intermediate result to the final
     * result
     */
    Function<A, R> finisher();

    /**
     * Returns a {@code Set} of {@code Collector.Characteristics} indicating
     * the characteristics of this Collector.  This set should be immutable.
     *
     * <p>
     *  执行从中间累加类型{@code A}到最终结果类型{@code R}的最终转换
     * 
     *  <p>如果设置了特征{@code IDENTITY_TRANSFORM},则此函数可以被认为是从{@code A}到{@code R}的未经检查的转换的身份转换,
     * 
     * 
     * @return an immutable set of collector characteristics
     */
    Set<Characteristics> characteristics();

    /**
     * Returns a new {@code Collector} described by the given {@code supplier},
     * {@code accumulator}, and {@code combiner} functions.  The resulting
     * {@code Collector} has the {@code Collector.Characteristics.IDENTITY_FINISH}
     * characteristic.
     *
     * <p>
     *  返回{@code集合}的{@code CollectorCharacteristics},指示此Collector的特性此集合应该是不可变的
     * 
     * 
     * @param supplier The supplier function for the new collector
     * @param accumulator The accumulator function for the new collector
     * @param combiner The combiner function for the new collector
     * @param characteristics The collector characteristics for the new
     *                        collector
     * @param <T> The type of input elements for the new collector
     * @param <R> The type of intermediate accumulation result, and final result,
     *           for the new collector
     * @throws NullPointerException if any argument is null
     * @return the new {@code Collector}
     */
    public static<T, R> Collector<T, R, R> of(Supplier<R> supplier,
                                              BiConsumer<R, T> accumulator,
                                              BinaryOperator<R> combiner,
                                              Characteristics... characteristics) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(accumulator);
        Objects.requireNonNull(combiner);
        Objects.requireNonNull(characteristics);
        Set<Characteristics> cs = (characteristics.length == 0)
                                  ? Collectors.CH_ID
                                  : Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH,
                                                                           characteristics));
        return new Collectors.CollectorImpl<>(supplier, accumulator, combiner, cs);
    }

    /**
     * Returns a new {@code Collector} described by the given {@code supplier},
     * {@code accumulator}, {@code combiner}, and {@code finisher} functions.
     *
     * <p>
     * 返回由给定的{@code supplier},{@code accuminer}和{@code combiner}函数描述的新{@code Collector}所产生的{@code Collector}
     * 具有{@code CollectorCharacteristicsIDENTITY_FINISH}特性。
     * 
     * 
     * @param supplier The supplier function for the new collector
     * @param accumulator The accumulator function for the new collector
     * @param combiner The combiner function for the new collector
     * @param finisher The finisher function for the new collector
     * @param characteristics The collector characteristics for the new
     *                        collector
     * @param <T> The type of input elements for the new collector
     * @param <A> The intermediate accumulation type of the new collector
     * @param <R> The final result type of the new collector
     * @throws NullPointerException if any argument is null
     * @return the new {@code Collector}
     */
    public static<T, A, R> Collector<T, A, R> of(Supplier<A> supplier,
                                                 BiConsumer<A, T> accumulator,
                                                 BinaryOperator<A> combiner,
                                                 Function<A, R> finisher,
                                                 Characteristics... characteristics) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(accumulator);
        Objects.requireNonNull(combiner);
        Objects.requireNonNull(finisher);
        Objects.requireNonNull(characteristics);
        Set<Characteristics> cs = Collectors.CH_NOID;
        if (characteristics.length > 0) {
            cs = EnumSet.noneOf(Characteristics.class);
            Collections.addAll(cs, characteristics);
            cs = Collections.unmodifiableSet(cs);
        }
        return new Collectors.CollectorImpl<>(supplier, accumulator, combiner, finisher, cs);
    }

    /**
     * Characteristics indicating properties of a {@code Collector}, which can
     * be used to optimize reduction implementations.
     * <p>
     *  返回由给定{@code供应商},{@code accumume},{@code combiner}和{@code finisher}函数描述的新{@code Collector}
     * 
     */
    enum Characteristics {
        /**
         * Indicates that this collector is <em>concurrent</em>, meaning that
         * the result container can support the accumulator function being
         * called concurrently with the same result container from multiple
         * threads.
         *
         * <p>If a {@code CONCURRENT} collector is not also {@code UNORDERED},
         * then it should only be evaluated concurrently if applied to an
         * unordered data source.
         * <p>
         *  指示{@code Collector}的属性的特性,可用于优化缩减实现
         * 
         */
        CONCURRENT,

        /**
         * Indicates that the collection operation does not commit to preserving
         * the encounter order of input elements.  (This might be true if the
         * result container has no intrinsic order, such as a {@link Set}.)
         * <p>
         *  表示此收集器<em>并发<em> </em>,意味着结果容器可以支持从多个线程与同一结果容器并发调用的累加器函数
         * 
         * <p>如果{@code CONCURRENT}收集器不是{@code UNORDERED},那么它应该仅在应用于无序数据源时才同时进行评估
         * 
         */
        UNORDERED,

        /**
         * Indicates that the finisher function is the identity function and
         * can be elided.  If set, it must be the case that an unchecked cast
         * from A to R will succeed.
         * <p>
         *  表示集合操作不提交保留输入元素的碰撞顺序(如果结果容器没有内在顺序,则可能为true,例如{@link Set})
         * 
         */
        IDENTITY_FINISH
    }
}
