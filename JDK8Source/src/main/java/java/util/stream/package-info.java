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

/**
 * Classes to support functional-style operations on streams of elements, such
 * as map-reduce transformations on collections.  For example:
 *
 * <pre>{@code
 *     int sum = widgets.stream()
 *                      .filter(b -> b.getColor() == RED)
 *                      .mapToInt(b -> b.getWeight())
 *                      .sum();
 * }</pre>
 *
 * <p>Here we use {@code widgets}, a {@code Collection<Widget>},
 * as a source for a stream, and then perform a filter-map-reduce on the stream
 * to obtain the sum of the weights of the red widgets.  (Summation is an
 * example of a <a href="package-summary.html#Reduction">reduction</a>
 * operation.)
 *
 * <p>The key abstraction introduced in this package is <em>stream</em>.  The
 * classes {@link java.util.stream.Stream}, {@link java.util.stream.IntStream},
 * {@link java.util.stream.LongStream}, and {@link java.util.stream.DoubleStream}
 * are streams over objects and the primitive {@code int}, {@code long} and
 * {@code double} types.  Streams differ from collections in several ways:
 *
 * <ul>
 *     <li>No storage.  A stream is not a data structure that stores elements;
 *     instead, it conveys elements from a source such as a data structure,
 *     an array, a generator function, or an I/O channel, through a pipeline of
 *     computational operations.</li>
 *     <li>Functional in nature.  An operation on a stream produces a result,
 *     but does not modify its source.  For example, filtering a {@code Stream}
 *     obtained from a collection produces a new {@code Stream} without the
 *     filtered elements, rather than removing elements from the source
 *     collection.</li>
 *     <li>Laziness-seeking.  Many stream operations, such as filtering, mapping,
 *     or duplicate removal, can be implemented lazily, exposing opportunities
 *     for optimization.  For example, "find the first {@code String} with
 *     three consecutive vowels" need not examine all the input strings.
 *     Stream operations are divided into intermediate ({@code Stream}-producing)
 *     operations and terminal (value- or side-effect-producing) operations.
 *     Intermediate operations are always lazy.</li>
 *     <li>Possibly unbounded.  While collections have a finite size, streams
 *     need not.  Short-circuiting operations such as {@code limit(n)} or
 *     {@code findFirst()} can allow computations on infinite streams to
 *     complete in finite time.</li>
 *     <li>Consumable. The elements of a stream are only visited once during
 *     the life of a stream. Like an {@link java.util.Iterator}, a new stream
 *     must be generated to revisit the same elements of the source.
 *     </li>
 * </ul>
 *
 * Streams can be obtained in a number of ways. Some examples include:
 * <ul>
 *     <li>From a {@link java.util.Collection} via the {@code stream()} and
 *     {@code parallelStream()} methods;</li>
 *     <li>From an array via {@link java.util.Arrays#stream(Object[])};</li>
 *     <li>From static factory methods on the stream classes, such as
 *     {@link java.util.stream.Stream#of(Object[])},
 *     {@link java.util.stream.IntStream#range(int, int)}
 *     or {@link java.util.stream.Stream#iterate(Object, UnaryOperator)};</li>
 *     <li>The lines of a file can be obtained from {@link java.io.BufferedReader#lines()};</li>
 *     <li>Streams of file paths can be obtained from methods in {@link java.nio.file.Files};</li>
 *     <li>Streams of random numbers can be obtained from {@link java.util.Random#ints()};</li>
 *     <li>Numerous other stream-bearing methods in the JDK, including
 *     {@link java.util.BitSet#stream()},
 *     {@link java.util.regex.Pattern#splitAsStream(java.lang.CharSequence)},
 *     and {@link java.util.jar.JarFile#stream()}.</li>
 * </ul>
 *
 * <p>Additional stream sources can be provided by third-party libraries using
 * <a href="package-summary.html#StreamSources">these techniques</a>.
 *
 * <h2><a name="StreamOps">Stream operations and pipelines</a></h2>
 *
 * <p>Stream operations are divided into <em>intermediate</em> and
 * <em>terminal</em> operations, and are combined to form <em>stream
 * pipelines</em>.  A stream pipeline consists of a source (such as a
 * {@code Collection}, an array, a generator function, or an I/O channel);
 * followed by zero or more intermediate operations such as
 * {@code Stream.filter} or {@code Stream.map}; and a terminal operation such
 * as {@code Stream.forEach} or {@code Stream.reduce}.
 *
 * <p>Intermediate operations return a new stream.  They are always
 * <em>lazy</em>; executing an intermediate operation such as
 * {@code filter()} does not actually perform any filtering, but instead
 * creates a new stream that, when traversed, contains the elements of
 * the initial stream that match the given predicate.  Traversal
 * of the pipeline source does not begin until the terminal operation of the
 * pipeline is executed.
 *
 * <p>Terminal operations, such as {@code Stream.forEach} or
 * {@code IntStream.sum}, may traverse the stream to produce a result or a
 * side-effect. After the terminal operation is performed, the stream pipeline
 * is considered consumed, and can no longer be used; if you need to traverse
 * the same data source again, you must return to the data source to get a new
 * stream.  In almost all cases, terminal operations are <em>eager</em>,
 * completing their traversal of the data source and processing of the pipeline
 * before returning.  Only the terminal operations {@code iterator()} and
 * {@code spliterator()} are not; these are provided as an "escape hatch" to enable
 * arbitrary client-controlled pipeline traversals in the event that the
 * existing operations are not sufficient to the task.
 *
 * <p> Processing streams lazily allows for significant efficiencies; in a
 * pipeline such as the filter-map-sum example above, filtering, mapping, and
 * summing can be fused into a single pass on the data, with minimal
 * intermediate state. Laziness also allows avoiding examining all the data
 * when it is not necessary; for operations such as "find the first string
 * longer than 1000 characters", it is only necessary to examine just enough
 * strings to find one that has the desired characteristics without examining
 * all of the strings available from the source. (This behavior becomes even
 * more important when the input stream is infinite and not merely large.)
 *
 * <p>Intermediate operations are further divided into <em>stateless</em>
 * and <em>stateful</em> operations. Stateless operations, such as {@code filter}
 * and {@code map}, retain no state from previously seen element when processing
 * a new element -- each element can be processed
 * independently of operations on other elements.  Stateful operations, such as
 * {@code distinct} and {@code sorted}, may incorporate state from previously
 * seen elements when processing new elements.
 *
 * <p>Stateful operations may need to process the entire input
 * before producing a result.  For example, one cannot produce any results from
 * sorting a stream until one has seen all elements of the stream.  As a result,
 * under parallel computation, some pipelines containing stateful intermediate
 * operations may require multiple passes on the data or may need to buffer
 * significant data.  Pipelines containing exclusively stateless intermediate
 * operations can be processed in a single pass, whether sequential or parallel,
 * with minimal data buffering.
 *
 * <p>Further, some operations are deemed <em>short-circuiting</em> operations.
 * An intermediate operation is short-circuiting if, when presented with
 * infinite input, it may produce a finite stream as a result.  A terminal
 * operation is short-circuiting if, when presented with infinite input, it may
 * terminate in finite time.  Having a short-circuiting operation in the pipeline
 * is a necessary, but not sufficient, condition for the processing of an infinite
 * stream to terminate normally in finite time.
 *
 * <h3>Parallelism</h3>
 *
 * <p>Processing elements with an explicit {@code for-}loop is inherently serial.
 * Streams facilitate parallel execution by reframing the computation as a pipeline of
 * aggregate operations, rather than as imperative operations on each individual
 * element.  All streams operations can execute either in serial or in parallel.
 * The stream implementations in the JDK create serial streams unless parallelism is
 * explicitly requested.  For example, {@code Collection} has methods
 * {@link java.util.Collection#stream} and {@link java.util.Collection#parallelStream},
 * which produce sequential and parallel streams respectively; other
 * stream-bearing methods such as {@link java.util.stream.IntStream#range(int, int)}
 * produce sequential streams but these streams can be efficiently parallelized by
 * invoking their {@link java.util.stream.BaseStream#parallel()} method.
 * To execute the prior "sum of weights of widgets" query in parallel, we would
 * do:
 *
 * <pre>{@code
 *     int sumOfWeights = widgets.}<code><b>parallelStream()</b></code>{@code
 *                               .filter(b -> b.getColor() == RED)
 *                               .mapToInt(b -> b.getWeight())
 *                               .sum();
 * }</pre>
 *
 * <p>The only difference between the serial and parallel versions of this
 * example is the creation of the initial stream, using "{@code parallelStream()}"
 * instead of "{@code stream()}".  When the terminal operation is initiated,
 * the stream pipeline is executed sequentially or in parallel depending on the
 * orientation of the stream on which it is invoked.  Whether a stream will execute in serial or
 * parallel can be determined with the {@code isParallel()} method, and the
 * orientation of a stream can be modified with the
 * {@link java.util.stream.BaseStream#sequential()} and
 * {@link java.util.stream.BaseStream#parallel()} operations.  When the terminal
 * operation is initiated, the stream pipeline is executed sequentially or in
 * parallel depending on the mode of the stream on which it is invoked.
 *
 * <p>Except for operations identified as explicitly nondeterministic, such
 * as {@code findAny()}, whether a stream executes sequentially or in parallel
 * should not change the result of the computation.
 *
 * <p>Most stream operations accept parameters that describe user-specified
 * behavior, which are often lambda expressions.  To preserve correct behavior,
 * these <em>behavioral parameters</em> must be <em>non-interfering</em>, and in
 * most cases must be <em>stateless</em>.  Such parameters are always instances
 * of a <a href="../function/package-summary.html">functional interface</a> such
 * as {@link java.util.function.Function}, and are often lambda expressions or
 * method references.
 *
 * <h3><a name="NonInterference">Non-interference</a></h3>
 *
 * Streams enable you to execute possibly-parallel aggregate operations over a
 * variety of data sources, including even non-thread-safe collections such as
 * {@code ArrayList}. This is possible only if we can prevent
 * <em>interference</em> with the data source during the execution of a stream
 * pipeline.  Except for the escape-hatch operations {@code iterator()} and
 * {@code spliterator()}, execution begins when the terminal operation is
 * invoked, and ends when the terminal operation completes.  For most data
 * sources, preventing interference means ensuring that the data source is
 * <em>not modified at all</em> during the execution of the stream pipeline.
 * The notable exception to this are streams whose sources are concurrent
 * collections, which are specifically designed to handle concurrent modification.
 * Concurrent stream sources are those whose {@code Spliterator} reports the
 * {@code CONCURRENT} characteristic.
 *
 * <p>Accordingly, behavioral parameters in stream pipelines whose source might
 * not be concurrent should never modify the stream's data source.
 * A behavioral parameter is said to <em>interfere</em> with a non-concurrent
 * data source if it modifies, or causes to be
 * modified, the stream's data source.  The need for non-interference applies
 * to all pipelines, not just parallel ones.  Unless the stream source is
 * concurrent, modifying a stream's data source during execution of a stream
 * pipeline can cause exceptions, incorrect answers, or nonconformant behavior.
 *
 * For well-behaved stream sources, the source can be modified before the
 * terminal operation commences and those modifications will be reflected in
 * the covered elements.  For example, consider the following code:
 *
 * <pre>{@code
 *     List<String> l = new ArrayList(Arrays.asList("one", "two"));
 *     Stream<String> sl = l.stream();
 *     l.add("three");
 *     String s = sl.collect(joining(" "));
 * }</pre>
 *
 * First a list is created consisting of two strings: "one"; and "two". Then a
 * stream is created from that list. Next the list is modified by adding a third
 * string: "three". Finally the elements of the stream are collected and joined
 * together. Since the list was modified before the terminal {@code collect}
 * operation commenced the result will be a string of "one two three". All the
 * streams returned from JDK collections, and most other JDK classes,
 * are well-behaved in this manner; for streams generated by other libraries, see
 * <a href="package-summary.html#StreamSources">Low-level stream
 * construction</a> for requirements for building well-behaved streams.
 *
 * <h3><a name="Statelessness">Stateless behaviors</a></h3>
 *
 * Stream pipeline results may be nondeterministic or incorrect if the behavioral
 * parameters to the stream operations are <em>stateful</em>.  A stateful lambda
 * (or other object implementing the appropriate functional interface) is one
 * whose result depends on any state which might change during the execution
 * of the stream pipeline.  An example of a stateful lambda is the parameter
 * to {@code map()} in:
 *
 * <pre>{@code
 *     Set<Integer> seen = Collections.synchronizedSet(new HashSet<>());
 *     stream.parallel().map(e -> { if (seen.add(e)) return 0; else return e; })...
 * }</pre>
 *
 * Here, if the mapping operation is performed in parallel, the results for the
 * same input could vary from run to run, due to thread scheduling differences,
 * whereas, with a stateless lambda expression the results would always be the
 * same.
 *
 * <p>Note also that attempting to access mutable state from behavioral parameters
 * presents you with a bad choice with respect to safety and performance; if
 * you do not synchronize access to that state, you have a data race and
 * therefore your code is broken, but if you do synchronize access to that
 * state, you risk having contention undermine the parallelism you are seeking
 * to benefit from.  The best approach is to avoid stateful behavioral
 * parameters to stream operations entirely; there is usually a way to
 * restructure the stream pipeline to avoid statefulness.
 *
 * <h3>Side-effects</h3>
 *
 * Side-effects in behavioral parameters to stream operations are, in general,
 * discouraged, as they can often lead to unwitting violations of the
 * statelessness requirement, as well as other thread-safety hazards.
 *
 * <p>If the behavioral parameters do have side-effects, unless explicitly
 * stated, there are no guarantees as to the
 * <a href="../concurrent/package-summary.html#MemoryVisibility"><i>visibility</i></a>
 * of those side-effects to other threads, nor are there any guarantees that
 * different operations on the "same" element within the same stream pipeline
 * are executed in the same thread.  Further, the ordering of those effects
 * may be surprising.  Even when a pipeline is constrained to produce a
 * <em>result</em> that is consistent with the encounter order of the stream
 * source (for example, {@code IntStream.range(0,5).parallel().map(x -> x*2).toArray()}
 * must produce {@code [0, 2, 4, 6, 8]}), no guarantees are made as to the order
 * in which the mapper function is applied to individual elements, or in what
 * thread any behavioral parameter is executed for a given element.
 *
 * <p>Many computations where one might be tempted to use side effects can be more
 * safely and efficiently expressed without side-effects, such as using
 * <a href="package-summary.html#Reduction">reduction</a> instead of mutable
 * accumulators. However, side-effects such as using {@code println()} for debugging
 * purposes are usually harmless.  A small number of stream operations, such as
 * {@code forEach()} and {@code peek()}, can operate only via side-effects;
 * these should be used with care.
 *
 * <p>As an example of how to transform a stream pipeline that inappropriately
 * uses side-effects to one that does not, the following code searches a stream
 * of strings for those matching a given regular expression, and puts the
 * matches in a list.
 *
 * <pre>{@code
 *     ArrayList<String> results = new ArrayList<>();
 *     stream.filter(s -> pattern.matcher(s).matches())
 *           .forEach(s -> results.add(s));  // Unnecessary use of side-effects!
 * }</pre>
 *
 * This code unnecessarily uses side-effects.  If executed in parallel, the
 * non-thread-safety of {@code ArrayList} would cause incorrect results, and
 * adding needed synchronization would cause contention, undermining the
 * benefit of parallelism.  Furthermore, using side-effects here is completely
 * unnecessary; the {@code forEach()} can simply be replaced with a reduction
 * operation that is safer, more efficient, and more amenable to
 * parallelization:
 *
 * <pre>{@code
 *     List<String>results =
 *         stream.filter(s -> pattern.matcher(s).matches())
 *               .collect(Collectors.toList());  // No side-effects!
 * }</pre>
 *
 * <h3><a name="Ordering">Ordering</a></h3>
 *
 * <p>Streams may or may not have a defined <em>encounter order</em>.  Whether
 * or not a stream has an encounter order depends on the source and the
 * intermediate operations.  Certain stream sources (such as {@code List} or
 * arrays) are intrinsically ordered, whereas others (such as {@code HashSet})
 * are not.  Some intermediate operations, such as {@code sorted()}, may impose
 * an encounter order on an otherwise unordered stream, and others may render an
 * ordered stream unordered, such as {@link java.util.stream.BaseStream#unordered()}.
 * Further, some terminal operations may ignore encounter order, such as
 * {@code forEach()}.
 *
 * <p>If a stream is ordered, most operations are constrained to operate on the
 * elements in their encounter order; if the source of a stream is a {@code List}
 * containing {@code [1, 2, 3]}, then the result of executing {@code map(x -> x*2)}
 * must be {@code [2, 4, 6]}.  However, if the source has no defined encounter
 * order, then any permutation of the values {@code [2, 4, 6]} would be a valid
 * result.
 *
 * <p>For sequential streams, the presence or absence of an encounter order does
 * not affect performance, only determinism.  If a stream is ordered, repeated
 * execution of identical stream pipelines on an identical source will produce
 * an identical result; if it is not ordered, repeated execution might produce
 * different results.
 *
 * <p>For parallel streams, relaxing the ordering constraint can sometimes enable
 * more efficient execution.  Certain aggregate operations,
 * such as filtering duplicates ({@code distinct()}) or grouped reductions
 * ({@code Collectors.groupingBy()}) can be implemented more efficiently if ordering of elements
 * is not relevant.  Similarly, operations that are intrinsically tied to encounter order,
 * such as {@code limit()}, may require
 * buffering to ensure proper ordering, undermining the benefit of parallelism.
 * In cases where the stream has an encounter order, but the user does not
 * particularly <em>care</em> about that encounter order, explicitly de-ordering
 * the stream with {@link java.util.stream.BaseStream#unordered() unordered()} may
 * improve parallel performance for some stateful or terminal operations.
 * However, most stream pipelines, such as the "sum of weight of blocks" example
 * above, still parallelize efficiently even under ordering constraints.
 *
 * <h2><a name="Reduction">Reduction operations</a></h2>
 *
 * A <em>reduction</em> operation (also called a <em>fold</em>) takes a sequence
 * of input elements and combines them into a single summary result by repeated
 * application of a combining operation, such as finding the sum or maximum of
 * a set of numbers, or accumulating elements into a list.  The streams classes have
 * multiple forms of general reduction operations, called
 * {@link java.util.stream.Stream#reduce(java.util.function.BinaryOperator) reduce()}
 * and {@link java.util.stream.Stream#collect(java.util.stream.Collector) collect()},
 * as well as multiple specialized reduction forms such as
 * {@link java.util.stream.IntStream#sum() sum()}, {@link java.util.stream.IntStream#max() max()},
 * or {@link java.util.stream.IntStream#count() count()}.
 *
 * <p>Of course, such operations can be readily implemented as simple sequential
 * loops, as in:
 * <pre>{@code
 *    int sum = 0;
 *    for (int x : numbers) {
 *       sum += x;
 *    }
 * }</pre>
 * However, there are good reasons to prefer a reduce operation
 * over a mutative accumulation such as the above.  Not only is a reduction
 * "more abstract" -- it operates on the stream as a whole rather than individual
 * elements -- but a properly constructed reduce operation is inherently
 * parallelizable, so long as the function(s) used to process the elements
 * are <a href="package-summary.html#Associativity">associative</a> and
 * <a href="package-summary.html#NonInterfering">stateless</a>.
 * For example, given a stream of numbers for which we want to find the sum, we
 * can write:
 * <pre>{@code
 *    int sum = numbers.stream().reduce(0, (x,y) -> x+y);
 * }</pre>
 * or:
 * <pre>{@code
 *    int sum = numbers.stream().reduce(0, Integer::sum);
 * }</pre>
 *
 * <p>These reduction operations can run safely in parallel with almost no
 * modification:
 * <pre>{@code
 *    int sum = numbers.parallelStream().reduce(0, Integer::sum);
 * }</pre>
 *
 * <p>Reduction parallellizes well because the implementation
 * can operate on subsets of the data in parallel, and then combine the
 * intermediate results to get the final correct answer.  (Even if the language
 * had a "parallel for-each" construct, the mutative accumulation approach would
 * still required the developer to provide
 * thread-safe updates to the shared accumulating variable {@code sum}, and
 * the required synchronization would then likely eliminate any performance gain from
 * parallelism.)  Using {@code reduce()} instead removes all of the
 * burden of parallelizing the reduction operation, and the library can provide
 * an efficient parallel implementation with no additional synchronization
 * required.
 *
 * <p>The "widgets" examples shown earlier shows how reduction combines with
 * other operations to replace for loops with bulk operations.  If {@code widgets}
 * is a collection of {@code Widget} objects, which have a {@code getWeight} method,
 * we can find the heaviest widget with:
 * <pre>{@code
 *     OptionalInt heaviest = widgets.parallelStream()
 *                                   .mapToInt(Widget::getWeight)
 *                                   .max();
 * }</pre>
 *
 * <p>In its more general form, a {@code reduce} operation on elements of type
 * {@code <T>} yielding a result of type {@code <U>} requires three parameters:
 * <pre>{@code
 * <U> U reduce(U identity,
 *              BiFunction<U, ? super T, U> accumulator,
 *              BinaryOperator<U> combiner);
 * }</pre>
 * Here, the <em>identity</em> element is both an initial seed value for the reduction
 * and a default result if there are no input elements. The <em>accumulator</em>
 * function takes a partial result and the next element, and produces a new
 * partial result. The <em>combiner</em> function combines two partial results
 * to produce a new partial result.  (The combiner is necessary in parallel
 * reductions, where the input is partitioned, a partial accumulation computed
 * for each partition, and then the partial results are combined to produce a
 * final result.)
 *
 * <p>More formally, the {@code identity} value must be an <em>identity</em> for
 * the combiner function. This means that for all {@code u},
 * {@code combiner.apply(identity, u)} is equal to {@code u}. Additionally, the
 * {@code combiner} function must be <a href="package-summary.html#Associativity">associative</a> and
 * must be compatible with the {@code accumulator} function: for all {@code u}
 * and {@code t}, {@code combiner.apply(u, accumulator.apply(identity, t))} must
 * be {@code equals()} to {@code accumulator.apply(u, t)}.
 *
 * <p>The three-argument form is a generalization of the two-argument form,
 * incorporating a mapping step into the accumulation step.  We could
 * re-cast the simple sum-of-weights example using the more general form as
 * follows:
 * <pre>{@code
 *     int sumOfWeights = widgets.stream()
 *                               .reduce(0,
 *                                       (sum, b) -> sum + b.getWeight())
 *                                       Integer::sum);
 * }</pre>
 * though the explicit map-reduce form is more readable and therefore should
 * usually be preferred. The generalized form is provided for cases where
 * significant work can be optimized away by combining mapping and reducing
 * into a single function.
 *
 * <h3><a name="MutableReduction">Mutable reduction</a></h3>
 *
 * A <em>mutable reduction operation</em> accumulates input elements into a
 * mutable result container, such as a {@code Collection} or {@code StringBuilder},
 * as it processes the elements in the stream.
 *
 * <p>If we wanted to take a stream of strings and concatenate them into a
 * single long string, we <em>could</em> achieve this with ordinary reduction:
 * <pre>{@code
 *     String concatenated = strings.reduce("", String::concat)
 * }</pre>
 *
 * <p>We would get the desired result, and it would even work in parallel.  However,
 * we might not be happy about the performance!  Such an implementation would do
 * a great deal of string copying, and the run time would be <em>O(n^2)</em> in
 * the number of characters.  A more performant approach would be to accumulate
 * the results into a {@link java.lang.StringBuilder}, which is a mutable
 * container for accumulating strings.  We can use the same technique to
 * parallelize mutable reduction as we do with ordinary reduction.
 *
 * <p>The mutable reduction operation is called
 * {@link java.util.stream.Stream#collect(Collector) collect()},
 * as it collects together the desired results into a result container such
 * as a {@code Collection}.
 * A {@code collect} operation requires three functions:
 * a supplier function to construct new instances of the result container, an
 * accumulator function to incorporate an input element into a result
 * container, and a combining function to merge the contents of one result
 * container into another.  The form of this is very similar to the general
 * form of ordinary reduction:
 * <pre>{@code
 * <R> R collect(Supplier<R> supplier,
 *               BiConsumer<R, ? super T> accumulator,
 *               BiConsumer<R, R> combiner);
 * }</pre>
 * <p>As with {@code reduce()}, a benefit of expressing {@code collect} in this
 * abstract way is that it is directly amenable to parallelization: we can
 * accumulate partial results in parallel and then combine them, so long as the
 * accumulation and combining functions satisfy the appropriate requirements.
 * For example, to collect the String representations of the elements in a
 * stream into an {@code ArrayList}, we could write the obvious sequential
 * for-each form:
 * <pre>{@code
 *     ArrayList<String> strings = new ArrayList<>();
 *     for (T element : stream) {
 *         strings.add(element.toString());
 *     }
 * }</pre>
 * Or we could use a parallelizable collect form:
 * <pre>{@code
 *     ArrayList<String> strings = stream.collect(() -> new ArrayList<>(),
 *                                                (c, e) -> c.add(e.toString()),
 *                                                (c1, c2) -> c1.addAll(c2));
 * }</pre>
 * or, pulling the mapping operation out of the accumulator function, we could
 * express it more succinctly as:
 * <pre>{@code
 *     List<String> strings = stream.map(Object::toString)
 *                                  .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
 * }</pre>
 * Here, our supplier is just the {@link java.util.ArrayList#ArrayList()
 * ArrayList constructor}, the accumulator adds the stringified element to an
 * {@code ArrayList}, and the combiner simply uses {@link java.util.ArrayList#addAll addAll}
 * to copy the strings from one container into the other.
 *
 * <p>The three aspects of {@code collect} -- supplier, accumulator, and
 * combiner -- are tightly coupled.  We can use the abstraction of a
 * {@link java.util.stream.Collector} to capture all three aspects.  The
 * above example for collecting strings into a {@code List} can be rewritten
 * using a standard {@code Collector} as:
 * <pre>{@code
 *     List<String> strings = stream.map(Object::toString)
 *                                  .collect(Collectors.toList());
 * }</pre>
 *
 * <p>Packaging mutable reductions into a Collector has another advantage:
 * composability.  The class {@link java.util.stream.Collectors} contains a
 * number of predefined factories for collectors, including combinators
 * that transform one collector into another.  For example, suppose we have a
 * collector that computes the sum of the salaries of a stream of
 * employees, as follows:
 *
 * <pre>{@code
 *     Collector<Employee, ?, Integer> summingSalaries
 *         = Collectors.summingInt(Employee::getSalary);
 * }</pre>
 *
 * (The {@code ?} for the second type parameter merely indicates that we don't
 * care about the intermediate representation used by this collector.)
 * If we wanted to create a collector to tabulate the sum of salaries by
 * department, we could reuse {@code summingSalaries} using
 * {@link java.util.stream.Collectors#groupingBy(java.util.function.Function, java.util.stream.Collector) groupingBy}:
 *
 * <pre>{@code
 *     Map<Department, Integer> salariesByDept
 *         = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,
 *                                                            summingSalaries));
 * }</pre>
 *
 * <p>As with the regular reduction operation, {@code collect()} operations can
 * only be parallelized if appropriate conditions are met.  For any partially
 * accumulated result, combining it with an empty result container must
 * produce an equivalent result.  That is, for a partially accumulated result
 * {@code p} that is the result of any series of accumulator and combiner
 * invocations, {@code p} must be equivalent to
 * {@code combiner.apply(p, supplier.get())}.
 *
 * <p>Further, however the computation is split, it must produce an equivalent
 * result.  For any input elements {@code t1} and {@code t2}, the results
 * {@code r1} and {@code r2} in the computation below must be equivalent:
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
 * }</pre>
 *
 * <p>Here, equivalence generally means according to {@link java.lang.Object#equals(Object)}.
 * but in some cases equivalence may be relaxed to account for differences in
 * order.
 *
 * <h3><a name="ConcurrentReduction">Reduction, concurrency, and ordering</a></h3>
 *
 * With some complex reduction operations, for example a {@code collect()} that
 * produces a {@code Map}, such as:
 * <pre>{@code
 *     Map<Buyer, List<Transaction>> salesByBuyer
 *         = txns.parallelStream()
 *               .collect(Collectors.groupingBy(Transaction::getBuyer));
 * }</pre>
 * it may actually be counterproductive to perform the operation in parallel.
 * This is because the combining step (merging one {@code Map} into another by
 * key) can be expensive for some {@code Map} implementations.
 *
 * <p>Suppose, however, that the result container used in this reduction
 * was a concurrently modifiable collection -- such as a
 * {@link java.util.concurrent.ConcurrentHashMap}. In that case, the parallel
 * invocations of the accumulator could actually deposit their results
 * concurrently into the same shared result container, eliminating the need for
 * the combiner to merge distinct result containers. This potentially provides
 * a boost to the parallel execution performance. We call this a
 * <em>concurrent</em> reduction.
 *
 * <p>A {@link java.util.stream.Collector} that supports concurrent reduction is
 * marked with the {@link java.util.stream.Collector.Characteristics#CONCURRENT}
 * characteristic.  However, a concurrent collection also has a downside.  If
 * multiple threads are depositing results concurrently into a shared container,
 * the order in which results are deposited is non-deterministic. Consequently,
 * a concurrent reduction is only possible if ordering is not important for the
 * stream being processed. The {@link java.util.stream.Stream#collect(Collector)}
 * implementation will only perform a concurrent reduction if
 * <ul>
 * <li>The stream is parallel;</li>
 * <li>The collector has the
 * {@link java.util.stream.Collector.Characteristics#CONCURRENT} characteristic,
 * and;</li>
 * <li>Either the stream is unordered, or the collector has the
 * {@link java.util.stream.Collector.Characteristics#UNORDERED} characteristic.
 * </ul>
 * You can ensure the stream is unordered by using the
 * {@link java.util.stream.BaseStream#unordered()} method.  For example:
 * <pre>{@code
 *     Map<Buyer, List<Transaction>> salesByBuyer
 *         = txns.parallelStream()
 *               .unordered()
 *               .collect(groupingByConcurrent(Transaction::getBuyer));
 * }</pre>
 * (where {@link java.util.stream.Collectors#groupingByConcurrent} is the
 * concurrent equivalent of {@code groupingBy}).
 *
 * <p>Note that if it is important that the elements for a given key appear in
 * the order they appear in the source, then we cannot use a concurrent
 * reduction, as ordering is one of the casualties of concurrent insertion.
 * We would then be constrained to implement either a sequential reduction or
 * a merge-based parallel reduction.
 *
 * <h3><a name="Associativity">Associativity</a></h3>
 *
 * An operator or function {@code op} is <em>associative</em> if the following
 * holds:
 * <pre>{@code
 *     (a op b) op c == a op (b op c)
 * }</pre>
 * The importance of this to parallel evaluation can be seen if we expand this
 * to four terms:
 * <pre>{@code
 *     a op b op c op d == (a op b) op (c op d)
 * }</pre>
 * So we can evaluate {@code (a op b)} in parallel with {@code (c op d)}, and
 * then invoke {@code op} on the results.
 *
 * <p>Examples of associative operations include numeric addition, min, and
 * max, and string concatenation.
 *
 * <h2><a name="StreamSources">Low-level stream construction</a></h2>
 *
 * So far, all the stream examples have used methods like
 * {@link java.util.Collection#stream()} or {@link java.util.Arrays#stream(Object[])}
 * to obtain a stream.  How are those stream-bearing methods implemented?
 *
 * <p>The class {@link java.util.stream.StreamSupport} has a number of
 * low-level methods for creating a stream, all using some form of a
 * {@link java.util.Spliterator}. A spliterator is the parallel analogue of an
 * {@link java.util.Iterator}; it describes a (possibly infinite) collection of
 * elements, with support for sequentially advancing, bulk traversal, and
 * splitting off some portion of the input into another spliterator which can
 * be processed in parallel.  At the lowest level, all streams are driven by a
 * spliterator.
 *
 * <p>There are a number of implementation choices in implementing a
 * spliterator, nearly all of which are tradeoffs between simplicity of
 * implementation and runtime performance of streams using that spliterator.
 * The simplest, but least performant, way to create a spliterator is to
 * create one from an iterator using
 * {@link java.util.Spliterators#spliteratorUnknownSize(java.util.Iterator, int)}.
 * While such a spliterator will work, it will likely offer poor parallel
 * performance, since we have lost sizing information (how big is the
 * underlying data set), as well as being constrained to a simplistic
 * splitting algorithm.
 *
 * <p>A higher-quality spliterator will provide balanced and known-size
 * splits, accurate sizing information, and a number of other
 * {@link java.util.Spliterator#characteristics() characteristics} of the
 * spliterator or data that can be used by implementations to optimize
 * execution.
 *
 * <p>Spliterators for mutable data sources have an additional challenge;
 * timing of binding to the data, since the data could change between the time
 * the spliterator is created and the time the stream pipeline is executed.
 * Ideally, a spliterator for a stream would report a characteristic of

 * {@code IMMUTABLE} or {@code CONCURRENT}; if not it should be
 * <a href="../Spliterator.html#binding"><em>late-binding</em></a>. If a source
 * cannot directly supply a recommended spliterator, it may indirectly supply
 * a spliterator using a {@code Supplier}, and construct a stream via the
 * {@code Supplier}-accepting versions of
 * {@link java.util.stream.StreamSupport#stream(Supplier, int, boolean) stream()}.
 * The spliterator is obtained from the supplier only after the terminal
 * operation of the stream pipeline commences.
 *
 * <p>These requirements significantly reduce the scope of potential
 * interference between mutations of the stream source and execution of stream
 * pipelines. Streams based on spliterators with the desired characteristics,
 * or those using the Supplier-based factory forms, are immune to
 * modifications of the data source prior to commencement of the terminal
 * operation (provided the behavioral parameters to the stream operations meet
 * the required criteria for non-interference and statelessness).  See
 * <a href="package-summary.html#NonInterference">Non-Interference</a>
 * for more details.
 *
 * <p>
 *  用于支持对元素流进行函数式操作的类,例如对集合的映射缩减转换例如：
 * 
 *  <pre> {@ code int sum = widgetsstream()filter(b  - > bgetColor()== RED)mapToInt(b  - > bgetWeight())sum }
 *  </pre>。
 * 
 *  <p>在这里,我们使用{@code widgets}(一个{@code Collection <Widget>})作为流的源,然后在流上执行filter-map-reduce以获取红色小部件(Summ
 * ation是<a href=\"package-summaryhtml#Reduction\">缩小</a>操作的示例)。
 * 
 * <p>此套件中引入的关键抽象是<em> </em>类{@link javautilstreamStream},{@link javautilstreamIntStream},{@link javautilstreamLongStream}
 * 和{@link javautilstreamDoubleStream} Stream {@code int},{@code long}和{@code double}类型与集合有以下几种不同：。
 * 
 * <ul>
 * <li>无存储流流不是存储元素的数据结构; </li> </li> <li>实际上是一个对流进行的操作产生一个数据结构,一个数组,一个生成函数或一个I / O通道,一个结果,但不修改其源例如,过滤从集合
 * 获得的{@code Stream}会生成一个新的{@code Stream}而不包含过滤的元素,而不是从源集合中删除元素</li> <li > Laziness-seek许多流操作,如过滤,映射或重复删
 * 除,可以延迟实现,暴露优化机会例如,"找到具有三个连续元音的第一个{@code String}"不需要检查所有输入字符串。
 * 流操作分为中间({@code Stream}  - 生产)操作和终端(值或副作用 - </li> <li>可能无界在集合具有有限大小的情况下,流不需要诸如{@code limit(n)}或{@code findFirst()}
 * 之类的短路操作。
 * 允许无限流上的计算在有限时间内完成</li> <li>可消耗流的元素在流的生命期间只被访问一次像{@link javautilIterator},必须生成一个新流来重访问相同的元素的源。
 * </li>
 * </ul>
 * 
 * 流可以通过多种方式获得一些示例包括：
 * <ul>
 * <li>通过{@code stream()}和{@code parallelStream()}方法从{@link javautilCollection}; </li> <li>通过{@link javautilArrays#stream )}
 * ; </li> <li>从流类上的静态工厂方法,例如{@link javautilstreamStream#of(Object [])},{@link javautilstreamIntStream#range(int,int)}
 * 或{@link javumeilstreamStream#iterate(Object,UnaryOperator)}; </li> <li>文件的行可以从{@link javaioBufferedReader#lines()}
 * 获取; </li> <li> {@link javaniofileFiles}中的方法; </li> <li>随机数字流可以从{@link javautilRandom#ints()}; </li> <li>
 *  JDK中的众多其他流方法,包括{@link javautilBitSet#stream()},{@link javautilregex模式#splitAsStream(javalangCharSequence)}
 * 和{@link javautiljarJarFile#stream()} </li>。
 * </ul>
 * 
 * <p>其他流源可以使用<a href=\"package-summaryhtml#StreamSources\">这些技术</a>由第三方库提供。
 * 
 *  <h2> <a name=\"StreamOps\">流操作和管道</a> </h2>
 * 
 *  <p>流操作被分为<em>中间</em>和<em>终端</em>操作,并且被组合以形成流管线</em>流管道包括源作为{@code Collection},数组,生成函数或I / O通道);之后是零个
 * 或多个中间操作,例如{@code Streamfilter}或{@code Streammap};和诸如{@code StreamforEach}或{@code Streamreduce}的终端操作,。
 * 
 * <p>中间操作返回一个新流它们总是<l>延迟</em>;执行诸如{@code filter()}之类的中间操作实际上不执行任何过滤,而是创建一个新流,当遍历时,它包含与给定谓词匹配的初始流的元素。
 * 流水线源的遍历不会开始直到执行流水线的终端操作。
 * 
 * <p>终端操作,例如{@code StreamforEach}或{@code IntStreamsum}可能会遍历流以产生结果或副作用。
 * 执行终端操作后,流管道被视为已消耗,并且不能更长时间使用;如果需要再次遍历相同的数据源,则必须返回到数据源以获得新流。
 * 在几乎所有情况下,终端操作都是<em> eager </em>,完成它们对数据源的遍历和处理返回的流水线只有终端操作{@code iterator()}和{@code spliterator()}不是;
 * 这些被提供作为"逃逸舱口"以在现有操作不足以满足任务的情况下实现任意客户机控制的流水线遍历。
 * 执行终端操作后,流管道被视为已消耗,并且不能更长时间使用;如果需要再次遍历相同的数据源,则必须返回到数据源以获得新流。
 * 
 * <p>处理流延迟允许显着的效率;在诸如上面的filter-map-sum示例的管线中,过滤,映射和求和可以被融合到具有最小中间状态的数据上的单次通过中,也允许避免在不必要时检查所有数据;对于诸如"找到长
 * 度超过1000个字符的第一个字符串"这样的操作,只需要检查足够多的字符串以找到具有所需特性的字符串,而不必检查源中的所有可用字符串(此行为在以下情况下变得更加重要：输入流是无限的,而不仅仅是大的)。
 * 
 * <p>中间操作进一步划分为无状态操作和有状态操作等无状态操作,例如{@code filter}和{@code map},保留了以前看不到的状态元素 - 每个元素可以独立于其他元素上的操作进行处理有状态操
 * 作(例如{@code distinct}和{@code sorted})可以合并来自先前看到的元素的状态,当处理新元素时。
 * 
 * <p>状态操作可能需要在产生结果之前处理整个输入。例如,在已经看到流的所有元素之前,不能产生来自对流进行排序的任何结果。
 * 作为结果,在并行计算下,包含状态中间体操作可能需要对数据进行多次传递,或者可能需要缓冲有效数据。仅包含无状态中间操作的流水线可以在单次传递中处理,无论是顺序传输还是并行传输,只需最少的数据缓冲。
 * 
 * <p>此外,一些操作被认为是短路操作中间操作是短路的,如果当呈现无限输入时,其可以产生有限流作为结果A终端操作是短暂的,如果当呈现无限输入时,它可以在有限时间内终止。
 * 在流水线中具有短路操作是用于处理无限流以在有限时间内正常终止的必要但不充分的条件。
 * 
 *  <h3>并行性</h3>
 * 
 * 具有显式{@code for-}循环的处理元素本质上是串行流通过将计算重构为聚合操作的管道而不是作为每个单独元素上的命令操作来促进并行执行。
 * 所有流操作可以以串行或并行除非显式请求并行性,否则JDK中的流实现创建串行流例如,{@code Collection}具有{@link javautilCollection#stream}和{@link javautilCollection#parallelStream}
 * 方法,它们分别产生顺序流和并行流;其他流方法如{@link javautilstreamIntStream#range(int,int)}产生顺序流,但是这些流可以通过调用它们的{@link javautilstreamBaseStream#parallel()}
 * 方法要并行执行先前的"小部件的权重和"查询,我们将：。
 * 具有显式{@code for-}循环的处理元素本质上是串行流通过将计算重构为聚合操作的管道而不是作为每个单独元素上的命令操作来促进并行执行。
 * 
 * <pre> {@ code int sumOfWeights = widgets} <code> <b> parallelStream()</b> </code> {@ code filter(b  - > bgetColor()== RED)mapToInt(b  - > bgetWeight ))sum(); }
 *  </pre>。
 * 
 * <p>此示例的串行和并行版本之间的唯一区别是使用"{@code parallelStream()}"而不是"{@code stream()}"创建初始流。
 * 当终端操作启动时,流管线根据其被调用的流的方向顺序地或并行地执行。
 * 流是否将以串行或并行方式执行可以用{@code isParallel()}方法来确定,并且a可以使用{@link javautilstreamBaseStream#sequential()}和{@link javautilstreamBaseStream#parallel()}
 * 操作修改流。
 * 当终端操作启动时,流管线根据其被调用的流的方向顺序地或并行地执行。当终端操作被启动时,流管道根据流的模式被顺序地或并行地执行它被调用。
 * 
 * <p>除了标识为显式非确定性的操作(如{@code findAny()}),流是顺序执行还是并行执行不应改变计算结果
 * 
 *  <p>大多数流操作接受描述用户指定行为(通常是lambda表达式)的参数。为了保持正确的行为,这些行为参数必须是<em>无干扰的<em> </em>大多数情况下必须是<em>无状态的</em>。
 * 此类参数始终是<a href=\"/function/package-summaryhtml\">功能界面</a>的实例,例如{@link javautilfunctionFunction},通常是la
 * mbda表达式或方法引用。
 *  <p>大多数流操作接受描述用户指定行为(通常是lambda表达式)的参数。为了保持正确的行为,这些行为参数必须是<em>无干扰的<em> </em>大多数情况下必须是<em>无状态的</em>。
 * 
 *  <h3> <a name=\"NonInterference\">无干扰</a> </h3>
 * 
 * Streams使您能够在各种数据源上执行可能并行的聚合操作,甚至包括非线程安全的收集,例如{@code ArrayList}。
 * 这是可能的,只有当我们可以防止<em>干扰</em>数据源在执行流流水线期间除了escape-hatch操作{@code iterator()}和{@code spliterator()},执行在终端操
 * 作被调用时开始,并在终端操作完成时结束。
 * Streams使您能够在各种数据源上执行可能并行的聚合操作,甚至包括非线程安全的收集,例如{@code ArrayList}。对于大多数数据源,防止干扰意味着确保在流管线的执行期间数据源根本不被修改。
 * 值得注意的异常是其源是并发收集的流,其被专门设计为处理并发修改并发流源是{@code Spliterator}报告{@code CONCURRENT}特性的流。
 * 
 * 因此,源流可能不并发的流管线中的行为参数应该永不修改流的数据源。
 * 如果行为参数修改或导致非并发数据源干扰,则该行为参数被称为干扰</em>待修改,流的数据源对非干扰的需要适用于所有流水线,而不仅仅是并行流。
 * 除非流源是并发的,否则在流流水线的执行期间修改流的数据源可能导致异常,不正确的答案或不一致行为。
 * 
 *  对于行为良好的流源,可以在终端操作开始之前修改源,并且这些修改将反映在覆盖的元素中例如,考虑以下代码：
 * 
 * <pre> {@ code List <String> l = new ArrayList(ArraysasList("one","two")); Stream <String> sl = lstream(); ladd("three"); String s = slcollect(join("")); }
 *  </pre>。
 * 
 * 首先创建一个由两个字符串组成的列表："one";和"two"然后从该列表创建流接下来,通过添加第三个字符串修改列表："three"最后,流的元素被收集并连接在一起因为列表在终端之前被修改{@code collect}
 * 操作开始的结果将是一个字符串"一二三"所有从JDK集合返回的流和大多数其他JDK类,以这种方式表现良好;对于由其他库生成的流,请参阅<a href=\"package-summaryhtml#StreamSources\">
 * 低级流构造</a>了解构建良性流的要求。
 * 
 *  <h3> <a name=\"Statelessness\">无状态行为</a> </h3>
 * 
 * 如果流操作的行为参数是有状态的,则流管道结果可能是非确定性的或不正确的。
 * 有状态的lambda(或实现适当的功能接口的其他对象)是其结果取决于可能在任何状态流管道的执行有状态lambda的一个例子是{@code map()}中的参数：。
 * 
 *  <pre> {@ code Set <Integer> seen = CollectionssynchronizedSet(new HashSet <>()); streamparallel()map(e  - > {if(seenadd(e))return 0; else return e;}
 * )} </pre>。
 * 
 * 这里,如果并行执行映射操作,则由于线程调度差异,相同输入的结果可能随运行而变化,而对于无状态lambda表达式,结果将总是相同的
 * 
 *  <p>请注意,尝试从行为参数访问可变状态在安全性和性能方面给您带来不好的选择;如果你不同步访问该状态,你有一个数据竞争,因此你的代码被打破了,但如果你同步访问该状态,你有冒险争夺破坏你想要受益的并行性
 * 最好的方法是避免状态行为参数完全流化操作;通常有一种方法来重构流管道以避免状态。
 * 
 * <h3>副作用</h3>
 * 
 *  通常,不鼓励流操作的行为参数的副作用,因为它们通常会导致无意识地违反无状态要求以及其他线程安全危害
 * 
 * <p>如果行为参数有副作用,除非明确说明,否则不能保证<a href=\"/concurrent/package-summaryhtml#MemoryVisibility\"> <i>公开程度</i> 
 * </a >那些副作用到其他线程,也没有任何保证在同一流水线内的"相同"元素上的不同操作在同一线程中执行。
 * 另外,这些效果的排序可能是令人惊讶的即使当管道是约束以产生与流源的遭遇顺序一致的<em>结果</em>(例如,{@code IntStreamrange(0,5)parallel()map(x→x * 2)toArray()}
 * 必须产生{@code [0,2,4,6,8]}),不保证映射器函数应用于单个元素的顺序,或在什么线程中任何行为参数对给定元素执行。
 * 
 * <p>许多计算可能会更倾向于使用副作用,而不会产生副作用,例如使用<a href=\"package-summaryhtml#Reduction\">缩减</a>,而不是可变累加器但是,使用{@code println()}
 * 进行调试的副作用通常是无害的。
 * 少量的流操作,例如{@code forEach()}和{@code peek()}只能通过副作用;这些应该小心使用。
 * 
 *  <p>作为如何将不适当使用副作用的流管道转换为不具有副作用的流管道的示例,以下代码搜索字符串流以查找与给定正则表达式匹配的字符串,并将匹配项放在列表中
 * 
 * <pre> {@ code ArrayList <String> results = new ArrayList <>(); streamfilter(s  - > patternmatcher(s)matches())forEach(s  - > resultsadd(s)); //不必要的使用副作用！ }
 *  </pre>。
 * 
 *  此代码不必要地使用副作用如果并行执行,{@code ArrayList}的非线程安全性将导致不正确的结果,并且添加所需的同步会导致争用,破坏并行性的好处此外,使用副作用这里是完全不必要{@code forEach()}
 * 可以简单地替换为更安全,更高效,更适合并行化的简化操作：。
 * 
 *  <pre> {@ code List <String> results = streamfilter(s  - > patternmatcher(s)matches())collect(CollectorstoList()); // 无副作用！ }
 *  </pre>。
 * 
 * <h3> <a name=\"Ordering\">订购</a> </h3>
 * 
 *  <p>流可能有也可能没有定义的<em>遭遇顺序</em>流是否有遭遇顺序取决于源和中间操作某些流源(例如{@code List}或数组)是固有排序的,而其他的(例如{@code HashSet})不是
 * 某些中间操作,例如{@code sorted()},可以在其他未排序的流上施加遇到命令,而其他可以渲染有序流无序的,例如{@link javautilstreamBaseStream#unordered()}
 * 此外,一些终端操作可以忽略遇到顺序,例如{@code forEach()}。
 * 
 * <p>如果流是有序的,大多数操作都被限制为对它们遇到顺序中的元素进行操作;如果流的源是包含{@code [1,2,3]}的{@code List},则执行{@code map(x-> x * 2)}的结
 * 果必须是{@code [ 2,4,6]}但是,如果源没有定义的遭遇顺序,那么值{@code [2,4,6]}的任何置换都将是有效的结果。
 * 
 * @since 1.8
 */
package java.util.stream;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;
