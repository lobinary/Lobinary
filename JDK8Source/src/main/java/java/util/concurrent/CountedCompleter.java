/***** Lobxxx Translate Finished ******/
/*
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

/*
 *
 *
 *
 *
 *
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 * <p>
 *  由Doug Lea在JCP JSR-166专家组成员的帮助下撰写,并发布到公共领域,如http：// creativecommonsorg / publicdomain / zero / 10 /
 * 
 */

package java.util.concurrent;

/**
 * A {@link ForkJoinTask} with a completion action performed when
 * triggered and there are no remaining pending actions.
 * CountedCompleters are in general more robust in the
 * presence of subtask stalls and blockage than are other forms of
 * ForkJoinTasks, but are less intuitive to program.  Uses of
 * CountedCompleter are similar to those of other completion based
 * components (such as {@link java.nio.channels.CompletionHandler})
 * except that multiple <em>pending</em> completions may be necessary
 * to trigger the completion action {@link #onCompletion(CountedCompleter)},
 * not just one.
 * Unless initialized otherwise, the {@linkplain #getPendingCount pending
 * count} starts at zero, but may be (atomically) changed using
 * methods {@link #setPendingCount}, {@link #addToPendingCount}, and
 * {@link #compareAndSetPendingCount}. Upon invocation of {@link
 * #tryComplete}, if the pending action count is nonzero, it is
 * decremented; otherwise, the completion action is performed, and if
 * this completer itself has a completer, the process is continued
 * with its completer.  As is the case with related synchronization
 * components such as {@link java.util.concurrent.Phaser Phaser} and
 * {@link java.util.concurrent.Semaphore Semaphore}, these methods
 * affect only internal counts; they do not establish any further
 * internal bookkeeping. In particular, the identities of pending
 * tasks are not maintained. As illustrated below, you can create
 * subclasses that do record some or all pending tasks or their
 * results when needed.  As illustrated below, utility methods
 * supporting customization of completion traversals are also
 * provided. However, because CountedCompleters provide only basic
 * synchronization mechanisms, it may be useful to create further
 * abstract subclasses that maintain linkages, fields, and additional
 * support methods appropriate for a set of related usages.
 *
 * <p>A concrete CountedCompleter class must define method {@link
 * #compute}, that should in most cases (as illustrated below), invoke
 * {@code tryComplete()} once before returning. The class may also
 * optionally override method {@link #onCompletion(CountedCompleter)}
 * to perform an action upon normal completion, and method
 * {@link #onExceptionalCompletion(Throwable, CountedCompleter)} to
 * perform an action upon any exception.
 *
 * <p>CountedCompleters most often do not bear results, in which case
 * they are normally declared as {@code CountedCompleter<Void>}, and
 * will always return {@code null} as a result value.  In other cases,
 * you should override method {@link #getRawResult} to provide a
 * result from {@code join(), invoke()}, and related methods.  In
 * general, this method should return the value of a field (or a
 * function of one or more fields) of the CountedCompleter object that
 * holds the result upon completion. Method {@link #setRawResult} by
 * default plays no role in CountedCompleters.  It is possible, but
 * rarely applicable, to override this method to maintain other
 * objects or fields holding result data.
 *
 * <p>A CountedCompleter that does not itself have a completer (i.e.,
 * one for which {@link #getCompleter} returns {@code null}) can be
 * used as a regular ForkJoinTask with this added functionality.
 * However, any completer that in turn has another completer serves
 * only as an internal helper for other computations, so its own task
 * status (as reported in methods such as {@link ForkJoinTask#isDone})
 * is arbitrary; this status changes only upon explicit invocations of
 * {@link #complete}, {@link ForkJoinTask#cancel},
 * {@link ForkJoinTask#completeExceptionally(Throwable)} or upon
 * exceptional completion of method {@code compute}. Upon any
 * exceptional completion, the exception may be relayed to a task's
 * completer (and its completer, and so on), if one exists and it has
 * not otherwise already completed. Similarly, cancelling an internal
 * CountedCompleter has only a local effect on that completer, so is
 * not often useful.
 *
 * <p><b>Sample Usages.</b>
 *
 * <p><b>Parallel recursive decomposition.</b> CountedCompleters may
 * be arranged in trees similar to those often used with {@link
 * RecursiveAction}s, although the constructions involved in setting
 * them up typically vary. Here, the completer of each task is its
 * parent in the computation tree. Even though they entail a bit more
 * bookkeeping, CountedCompleters may be better choices when applying
 * a possibly time-consuming operation (that cannot be further
 * subdivided) to each element of an array or collection; especially
 * when the operation takes a significantly different amount of time
 * to complete for some elements than others, either because of
 * intrinsic variation (for example I/O) or auxiliary effects such as
 * garbage collection.  Because CountedCompleters provide their own
 * continuations, other threads need not block waiting to perform
 * them.
 *
 * <p>For example, here is an initial version of a class that uses
 * divide-by-two recursive decomposition to divide work into single
 * pieces (leaf tasks). Even when work is split into individual calls,
 * tree-based techniques are usually preferable to directly forking
 * leaf tasks, because they reduce inter-thread communication and
 * improve load balancing. In the recursive case, the second of each
 * pair of subtasks to finish triggers completion of its parent
 * (because no result combination is performed, the default no-op
 * implementation of method {@code onCompletion} is not overridden).
 * A static utility method sets up the base task and invokes it
 * (here, implicitly using the {@link ForkJoinPool#commonPool()}).
 *
 * <pre> {@code
 * class MyOperation<E> { void apply(E e) { ... }  }
 *
 * class ForEach<E> extends CountedCompleter<Void> {
 *
 *   public static <E> void forEach(E[] array, MyOperation<E> op) {
 *     new ForEach<E>(null, array, op, 0, array.length).invoke();
 *   }
 *
 *   final E[] array; final MyOperation<E> op; final int lo, hi;
 *   ForEach(CountedCompleter<?> p, E[] array, MyOperation<E> op, int lo, int hi) {
 *     super(p);
 *     this.array = array; this.op = op; this.lo = lo; this.hi = hi;
 *   }
 *
 *   public void compute() { // version 1
 *     if (hi - lo >= 2) {
 *       int mid = (lo + hi) >>> 1;
 *       setPendingCount(2); // must set pending count before fork
 *       new ForEach(this, array, op, mid, hi).fork(); // right child
 *       new ForEach(this, array, op, lo, mid).fork(); // left child
 *     }
 *     else if (hi > lo)
 *       op.apply(array[lo]);
 *     tryComplete();
 *   }
 * }}</pre>
 *
 * This design can be improved by noticing that in the recursive case,
 * the task has nothing to do after forking its right task, so can
 * directly invoke its left task before returning. (This is an analog
 * of tail recursion removal.)  Also, because the task returns upon
 * executing its left task (rather than falling through to invoke
 * {@code tryComplete}) the pending count is set to one:
 *
 * <pre> {@code
 * class ForEach<E> ...
 *   public void compute() { // version 2
 *     if (hi - lo >= 2) {
 *       int mid = (lo + hi) >>> 1;
 *       setPendingCount(1); // only one pending
 *       new ForEach(this, array, op, mid, hi).fork(); // right child
 *       new ForEach(this, array, op, lo, mid).compute(); // direct invoke
 *     }
 *     else {
 *       if (hi > lo)
 *         op.apply(array[lo]);
 *       tryComplete();
 *     }
 *   }
 * }</pre>
 *
 * As a further improvement, notice that the left task need not even exist.
 * Instead of creating a new one, we can iterate using the original task,
 * and add a pending count for each fork.  Additionally, because no task
 * in this tree implements an {@link #onCompletion(CountedCompleter)} method,
 * {@code tryComplete()} can be replaced with {@link #propagateCompletion}.
 *
 * <pre> {@code
 * class ForEach<E> ...
 *   public void compute() { // version 3
 *     int l = lo,  h = hi;
 *     while (h - l >= 2) {
 *       int mid = (l + h) >>> 1;
 *       addToPendingCount(1);
 *       new ForEach(this, array, op, mid, h).fork(); // right child
 *       h = mid;
 *     }
 *     if (h > l)
 *       op.apply(array[l]);
 *     propagateCompletion();
 *   }
 * }</pre>
 *
 * Additional improvements of such classes might entail precomputing
 * pending counts so that they can be established in constructors,
 * specializing classes for leaf steps, subdividing by say, four,
 * instead of two per iteration, and using an adaptive threshold
 * instead of always subdividing down to single elements.
 *
 * <p><b>Searching.</b> A tree of CountedCompleters can search for a
 * value or property in different parts of a data structure, and
 * report a result in an {@link
 * java.util.concurrent.atomic.AtomicReference AtomicReference} as
 * soon as one is found. The others can poll the result to avoid
 * unnecessary work. (You could additionally {@linkplain #cancel
 * cancel} other tasks, but it is usually simpler and more efficient
 * to just let them notice that the result is set and if so skip
 * further processing.)  Illustrating again with an array using full
 * partitioning (again, in practice, leaf tasks will almost always
 * process more than one element):
 *
 * <pre> {@code
 * class Searcher<E> extends CountedCompleter<E> {
 *   final E[] array; final AtomicReference<E> result; final int lo, hi;
 *   Searcher(CountedCompleter<?> p, E[] array, AtomicReference<E> result, int lo, int hi) {
 *     super(p);
 *     this.array = array; this.result = result; this.lo = lo; this.hi = hi;
 *   }
 *   public E getRawResult() { return result.get(); }
 *   public void compute() { // similar to ForEach version 3
 *     int l = lo,  h = hi;
 *     while (result.get() == null && h >= l) {
 *       if (h - l >= 2) {
 *         int mid = (l + h) >>> 1;
 *         addToPendingCount(1);
 *         new Searcher(this, array, result, mid, h).fork();
 *         h = mid;
 *       }
 *       else {
 *         E x = array[l];
 *         if (matches(x) && result.compareAndSet(null, x))
 *           quietlyCompleteRoot(); // root task is now joinable
 *         break;
 *       }
 *     }
 *     tryComplete(); // normally complete whether or not found
 *   }
 *   boolean matches(E e) { ... } // return true if found
 *
 *   public static <E> E search(E[] array) {
 *       return new Searcher<E>(null, array, new AtomicReference<E>(), 0, array.length).invoke();
 *   }
 * }}</pre>
 *
 * In this example, as well as others in which tasks have no other
 * effects except to compareAndSet a common result, the trailing
 * unconditional invocation of {@code tryComplete} could be made
 * conditional ({@code if (result.get() == null) tryComplete();})
 * because no further bookkeeping is required to manage completions
 * once the root task completes.
 *
 * <p><b>Recording subtasks.</b> CountedCompleter tasks that combine
 * results of multiple subtasks usually need to access these results
 * in method {@link #onCompletion(CountedCompleter)}. As illustrated in the following
 * class (that performs a simplified form of map-reduce where mappings
 * and reductions are all of type {@code E}), one way to do this in
 * divide and conquer designs is to have each subtask record its
 * sibling, so that it can be accessed in method {@code onCompletion}.
 * This technique applies to reductions in which the order of
 * combining left and right results does not matter; ordered
 * reductions require explicit left/right designations.  Variants of
 * other streamlinings seen in the above examples may also apply.
 *
 * <pre> {@code
 * class MyMapper<E> { E apply(E v) {  ...  } }
 * class MyReducer<E> { E apply(E x, E y) {  ...  } }
 * class MapReducer<E> extends CountedCompleter<E> {
 *   final E[] array; final MyMapper<E> mapper;
 *   final MyReducer<E> reducer; final int lo, hi;
 *   MapReducer<E> sibling;
 *   E result;
 *   MapReducer(CountedCompleter<?> p, E[] array, MyMapper<E> mapper,
 *              MyReducer<E> reducer, int lo, int hi) {
 *     super(p);
 *     this.array = array; this.mapper = mapper;
 *     this.reducer = reducer; this.lo = lo; this.hi = hi;
 *   }
 *   public void compute() {
 *     if (hi - lo >= 2) {
 *       int mid = (lo + hi) >>> 1;
 *       MapReducer<E> left = new MapReducer(this, array, mapper, reducer, lo, mid);
 *       MapReducer<E> right = new MapReducer(this, array, mapper, reducer, mid, hi);
 *       left.sibling = right;
 *       right.sibling = left;
 *       setPendingCount(1); // only right is pending
 *       right.fork();
 *       left.compute();     // directly execute left
 *     }
 *     else {
 *       if (hi > lo)
 *           result = mapper.apply(array[lo]);
 *       tryComplete();
 *     }
 *   }
 *   public void onCompletion(CountedCompleter<?> caller) {
 *     if (caller != this) {
 *       MapReducer<E> child = (MapReducer<E>)caller;
 *       MapReducer<E> sib = child.sibling;
 *       if (sib == null || sib.result == null)
 *         result = child.result;
 *       else
 *         result = reducer.apply(child.result, sib.result);
 *     }
 *   }
 *   public E getRawResult() { return result; }
 *
 *   public static <E> E mapReduce(E[] array, MyMapper<E> mapper, MyReducer<E> reducer) {
 *     return new MapReducer<E>(null, array, mapper, reducer,
 *                              0, array.length).invoke();
 *   }
 * }}</pre>
 *
 * Here, method {@code onCompletion} takes a form common to many
 * completion designs that combine results. This callback-style method
 * is triggered once per task, in either of the two different contexts
 * in which the pending count is, or becomes, zero: (1) by a task
 * itself, if its pending count is zero upon invocation of {@code
 * tryComplete}, or (2) by any of its subtasks when they complete and
 * decrement the pending count to zero. The {@code caller} argument
 * distinguishes cases.  Most often, when the caller is {@code this},
 * no action is necessary. Otherwise the caller argument can be used
 * (usually via a cast) to supply a value (and/or links to other
 * values) to be combined.  Assuming proper use of pending counts, the
 * actions inside {@code onCompletion} occur (once) upon completion of
 * a task and its subtasks. No additional synchronization is required
 * within this method to ensure thread safety of accesses to fields of
 * this task or other completed tasks.
 *
 * <p><b>Completion Traversals</b>. If using {@code onCompletion} to
 * process completions is inapplicable or inconvenient, you can use
 * methods {@link #firstComplete} and {@link #nextComplete} to create
 * custom traversals.  For example, to define a MapReducer that only
 * splits out right-hand tasks in the form of the third ForEach
 * example, the completions must cooperatively reduce along
 * unexhausted subtask links, which can be done as follows:
 *
 * <pre> {@code
 * class MapReducer<E> extends CountedCompleter<E> { // version 2
 *   final E[] array; final MyMapper<E> mapper;
 *   final MyReducer<E> reducer; final int lo, hi;
 *   MapReducer<E> forks, next; // record subtask forks in list
 *   E result;
 *   MapReducer(CountedCompleter<?> p, E[] array, MyMapper<E> mapper,
 *              MyReducer<E> reducer, int lo, int hi, MapReducer<E> next) {
 *     super(p);
 *     this.array = array; this.mapper = mapper;
 *     this.reducer = reducer; this.lo = lo; this.hi = hi;
 *     this.next = next;
 *   }
 *   public void compute() {
 *     int l = lo,  h = hi;
 *     while (h - l >= 2) {
 *       int mid = (l + h) >>> 1;
 *       addToPendingCount(1);
 *       (forks = new MapReducer(this, array, mapper, reducer, mid, h, forks)).fork();
 *       h = mid;
 *     }
 *     if (h > l)
 *       result = mapper.apply(array[l]);
 *     // process completions by reducing along and advancing subtask links
 *     for (CountedCompleter<?> c = firstComplete(); c != null; c = c.nextComplete()) {
 *       for (MapReducer t = (MapReducer)c, s = t.forks;  s != null; s = t.forks = s.next)
 *         t.result = reducer.apply(t.result, s.result);
 *     }
 *   }
 *   public E getRawResult() { return result; }
 *
 *   public static <E> E mapReduce(E[] array, MyMapper<E> mapper, MyReducer<E> reducer) {
 *     return new MapReducer<E>(null, array, mapper, reducer,
 *                              0, array.length, null).invoke();
 *   }
 * }}</pre>
 *
 * <p><b>Triggers.</b> Some CountedCompleters are themselves never
 * forked, but instead serve as bits of plumbing in other designs;
 * including those in which the completion of one or more async tasks
 * triggers another async task. For example:
 *
 * <pre> {@code
 * class HeaderBuilder extends CountedCompleter<...> { ... }
 * class BodyBuilder extends CountedCompleter<...> { ... }
 * class PacketSender extends CountedCompleter<...> {
 *   PacketSender(...) { super(null, 1); ... } // trigger on second completion
 *   public void compute() { } // never called
 *   public void onCompletion(CountedCompleter<?> caller) { sendPacket(); }
 * }
 * // sample use:
 * PacketSender p = new PacketSender();
 * new HeaderBuilder(p, ...).fork();
 * new BodyBuilder(p, ...).fork();
 * }</pre>
 *
 * <p>
 * 一个{@link ForkJoinTask}在触发时执行完成操作,没有剩余的待处理操作CountedCompleters在存在子任务停顿和阻塞时比其他形式的ForkJoinTasks更加健壮,但编程Co
 * untedCompleter的用法不够直观类似于其他基于完成的组件(例如{@link javaniochannelsCompletionHandler}),除了可能需要多个<em> pending </em>
 * 完成触发完成操作{@link #onCompletion(CountedCompleter)},而不仅仅是一除非初始化否则{@linkplain #getPendingCount pending count}
 * 从零开始,但可以使用{@link #setPendingCount},{@link #addToPendingCount}和{@link #compareAndSetPendingCount}方法更改(
 * 原子地) {@link #tryComplete},如果待处理操作计数为非零,则减少;如果该完成者本身具有完成者,则该过程继续其完成者与相关同步组件(例如{@link javautilconcurrentPhaser Phaser}
 * 和{@link javautilconcurrentSemaphore Semaphore})的情况一样,这些方法仅影响内部计数;它们不建立任何进一步的内部记账。
 * 特别地,未维持待决任务的身份如下图所示,您可以创建子类,在需要时记录一些或所有挂起的任务或其结果如下所示,还提供了支持完成遍历的定制的实用程序方法。
 * 但是,由于CountedCompleters仅提供基本的同步机制,因此可能有用创建进一步的抽象子类,保持链接,字段和适用于一组相关用法的附加支持方法。
 * 
 * <p>一个具体的CountedCompleter类必须定义方法{@link #compute},在大多数情况下(如下图所示)在返回之前调用{@code tryComplete()}一次类也可以选择重载方
 * 法{@link# onCompletion(CountedCompleter)}在正常完成时执行操作,而方法{@link #onExceptionalCompletion(Throwable,CountedCompleter)}
 * 对任何异常执行操作。
 * 
 * <p> CountedCompleters通常不承担结果,在这种情况下,它们通常被声明为{@code CountedCompleter <Void>},并且总是返回{@code null}作为结果值在其
 * 他情况下,您应该覆盖方法{ @link #getRawResult}提供来自{@code join(),invoke()}和相关方法的结果通常,此方法应返回CountedCompleter的字段(或一个
 * 或多个字段的函数)的值在完成时保存结果的对象方法{@link #setRawResult}默认情况下在CountedCompleters中不起作用可能但很少适用于覆盖此方法以维护其他对象或保存结果数据的
 * 字段。
 * 
 * <p>一个CountedCompleter本身没有一个完成者(即{@link #getCompleter}返回{@code null})可以作为一个普通的ForkJoinTask使用这个添加的功能然而,
 * 任何完成者,反过来另一个完成器仅用作其他计算的内部辅助程序,因此其自身的任务状态(如{@link ForkJoinTask#isDone}等方法中报告的)是任意的;此状态仅在显式调用{@link #complete}
 * ,{@link ForkJoinTask#cancel},{@link ForkJoinTask#completeExceptionally(Throwable)}或特殊完成方法时更改{@code compute}
 * 在任何异常完成后,异常可以被中继到任务的完成者(及其完成者,等等),如果存在并且它还没有完成类似地,取消内部CountedCompleter对该完成者只有本地效应,因此通常不是有用的。
 * 
 * <p> <b>示例用法</b>
 * 
 * <p> <b>并行递归分解</b> CountedCompleters可以排列在类似于{@link RecursiveAction}常用的树中,尽管设置它们涉及的结构通常不同。
 * 这里,每个任务的完成者它的计算树中的父元素即使它们需要更多的簿记,当向数组或集合的每个元素应用可能耗时的操作(无法进一步细分)时,CountedCompleters可能是更好的选择;特别是当操作对于某些
 * 元件而言完成的时间量明显不同于其它元件时,或者是由于固有变化(例如I / O)或者诸如垃圾收集的辅助效应因为CountedCompleters提供了自己的继续,所以其他线程不需要阻塞等待执行它们。
 * <p> <b>并行递归分解</b> CountedCompleters可以排列在类似于{@link RecursiveAction}常用的树中,尽管设置它们涉及的结构通常不同。
 * 
 * <p>例如,这里是一个类的初始版本,它使用除以二递归分解将工作分割为单件(叶子任务)即使工作被分成单独的调用,基于树的技术通常比直接分枝叶任务,因为它们减少了线程间通信并提高了负载平衡。
 * 在递归情况下,每对子任务中的第二个子任务完成触发其父对象的完成(因为没有执行结果组合,默认的无操作实现方法{@code onCompletion}不被覆盖)静态实用程序方法设置基本任务并调用它(这里,隐
 * 式地使用{@link ForkJoinPool#commonPool()})。
 * <p>例如,这里是一个类的初始版本,它使用除以二递归分解将工作分割为单件(叶子任务)即使工作被分成单独的调用,基于树的技术通常比直接分枝叶任务,因为它们减少了线程间通信并提高了负载平衡。
 * 
 *  <pre> {@code class MyOperation <E> {void apply(E e){}}
 * 
 * ForEach类扩展CountedCompleter <Void> {
 * 
 *  public static <E> void forEach(E [] array,MyOperation <E> op){new ForEach <E>(null,array,op,0,arraylength)invoke }
 * }。
 * 
 *  final E [] array;最后MyOperation <E> op; final int lo,hi; ForEach(CountedCompleter <p>,E [] array,MyOp
 * eration <E> op,int lo,int hi){super(p); thisarray = array; thisop = op; thislo = lo; thishi = hi; }}。
 * 
 *  public void compute(){// version 1 if(hi-lo> = 2){int mid =(lo + hi)>>> 1; setPendingCount(2); //必须在fork前设置挂起计数new ForEach(this,array,op,mid,hi)fork(); // right child new ForEach(this,array,op,lo,mid)fork(); // left child}
 *  else if(hi> lo)opapply(array [lo]); tryComplete(); }}} </pre>。
 * 
 * 这个设计可以通过注意在递归的情况下,任务在分叉其正确的任务后没有做任何事情,所以可以直接在返回之前调用它的左侧任务(这是一个类似于尾递归删除)改进。
 * 此外,因为任务返回在执行它的左边任务(而不是通过调用{@code tryComplete})挂起计数设置为一：。
 * 
 *  <pre> {@code class ForEach <E> public void compute(){// version 2 if(hi-lo> = 2){int mid =(lo + hi)>>> 1; setPendingCount(1); //只有一个未决的ForEach(this,array,op,mid,hi)fork(); // right child new ForEach(this,array,op,lo,mid)compute(); // direct invoke}
 *  else {if(hi> lo)opapply(array [lo]); tryComplete(); }}} </pre>。
 * 
 * 作为进一步的改进,注意左侧任务不需要存在而不是创建一个新的,我们可以使用原始任务进行迭代,并为每个叉添加一个挂起计数另外,因为这个树中没有任何任务实现{@link #onCompletion(CountedCompleter)}
 * 方法,{@code tryComplete()}可以替换为{@link #propagateCompletion}。
 * 
 *  <pre> {@code class ForEach <E> public void compute(){// version 3 int l = lo,h = hi;而(h-1> = 2){int mid =(1 + h)>>> 1; addToPendingCount(1) new ForEach(this,array,op,mid,h)fork(); // right child h = mid; }
 *  if(h> l)opapply(array [1]); propagateCompletion(); }} </pre>。
 * 
 * 对这些类的额外改进可能需要预先计算未决计数,使得它们可以在构造函数中建立,专门用于叶步骤的类,细分为例如四,而不是每次迭代两次,并且使用自适应阈值而不是总是细分为单个元素
 * 
 * <p> <b>搜索</b> CountedCompleters的树可以在数据结构的不同部分中搜索值或属性,并在找到一个时立即在{@link javautilconcurrentatomicAtomicReference AtomicReference}
 * 中报告结果。
 * 可以轮询结果以避免不必要的工作(您可以另外{@linkplain #cancel cancel}其他任务,但是通常更简单,更有效,让他们注意到结果已设置,如果是这样,则跳过进一步处理)再次说明使用完全分
 * 区的数组(再次,在实践中,叶子任务几乎总是处理多个元素)：。
 * 
 * <pre> {@code class Searcher <E> extends CountedCompleter <E> {final E [] array; final AtomicReference <E> result; final int lo,hi;搜索器(CountedCompleter <p>,E [] array,AtomicReference <E> result,int lo,int hi){super(p); thisarray = array; thisresult = result; thislo = lo; thishi = hi; }
 *  public E getRawResult(){return resultget(); } public void compute(){//类似ForEach版本3 int l = lo,h = hi; if(h-1> = 2){int mid =(1 + h)>>> 1; while(resultget()== null && h> = 1){if addToPendingCount(1) new Searcher(this,array,result,mid,h)fork(); h = mid; }
 *  else {E x = array [l]; if(matches(x)&& resultcompareAndSet(null,x))quietlyCompleteRoot(); // root task is now joinable break; }
 * } tryComplete(); //正常完成是否找到} boolean matches(E e){} //如果找到则返回true。
 * 
 * public static <E> E search(E [] array){return new Searcher <E>(null,array,new AtomicReference <E>(),0,arraylength)invoke }
 * }} </pre>。
 * 
 *  在这个例子中,以及其他任务没有其他效果,除了compareAndSet一个共同的结果,{@code tryComplete}的尾随无条件调用可以使条件({@code if(resultget()== null)tryComplete ();}
 * ),因为一旦根任务完成,就不需要进一步的记账来管理完成。
 * 
 * <p> <b>记录子任务</b> CountedCompleter任务结合多个子任务的结果通常需要在方法{@link #onCompletion(CountedCompleter)}中访问这些结果如下面
 * 的类(执行一个简化形式map-reduce其中映射和缩减都是{@code E}类型),在分割和征服设计中这样做的一个方法是让每个子任务记录其兄弟,以便它可以在方法{@code onCompletion}
 * 这种技术适用于其中左和右结果的组合顺序无关的缩减;有序减少需要明确的左/右指定在上面的例子中看到的其他流束的变体也可能适用。
 * 
 * </span> </span> </span> </span> </span> </span> </span> </span> <class> final E [] array;最终MyMapper <E>
 *  mapper;最终MyReducer <E>减速器; final int lo,hi; MapReducer <E>同胞; E结果; MapReducer(CountedCompleter <p> E
 *  [] array,MyMapper <E> mapper,MyReducer <E> reducer,int lo,int hi){super(p); thisarray = array; thismapper = mapper; thisreducer = reducer; thislo = lo; thishi = hi; }
 *  public void compute(){if(hi-lo> = 2){int mid =(lo + hi)>>> 1; MapReducer <E> left = new MapReducer(this,array,mapper,reducer,lo,mid); MapReducer <E> right = new MapReducer(this,array,mapper,reducer,mid,hi); leftsibling = right; rightsibling = left; setPendingCount(1); //只有权利是悬而未决的叉子(); leftcompute(); //直接执行left}
 *  else {if(hi> lo)result = mapperapply(array [lo]); tryComplete(); }} public void onCompletion(Counted
 * Completer <?> caller){if(caller！= this){MapReducer <E> child =(MapReducer <E>)caller; MapReducer <E> sib = childsibling; if(sib == null || sibresult == null)result = childresult; else result = reducerapply(childresult,sibresult); }
 * } public E getRawResult(){return result; }}。
 * 
 * public static <E> E mapReduce(E [] array,MyMapper <E> mapper,MyReducer <E> reducer){return new MapReducer <E>(null,array,mapper,reducer,0,arraylength)invoke }
 * }} </pre>。
 * 
 * 这里,方法{@code onCompletion}采用一种结合结果的许多完成设计的通用形式。
 * 这个回调风格方法在每个任务被触发一次,在两个不同的上下文中,挂起计数是或变为零： 1)任务本身,如果它的挂起计数在调用{@code tryComplete}时为零,或者(2)当它们完成并将挂起的计数减少
 * 到0时,它的任何子任务。
 * 这里,方法{@code onCompletion}采用一种结合结果的许多完成设计的通用形式。{@code caller}通常,当调用者是{@code this}时,不需要执行任何操作。
 * 否则,可以使用调用者参数(通常通过强制转换)提供要组合的值(和/或指向其他值的链接)假设正确使用挂起计数,{@code onCompletion}中的操作在任务及其子任务完成时发生(一次)在此方法中不需
 * 要额外的同步,以确保访问此任务或其他已完成任务的字段的线程安全性。
 * 这里,方法{@code onCompletion}采用一种结合结果的许多完成设计的通用形式。{@code caller}通常,当调用者是{@code this}时,不需要执行任何操作。
 * 
 * <p> <b>完成遍历</b>如果使用{@code onCompletion}处理完成操作不适用或不方便,您可以使用{@link #firstComplete}和{@link #nextComplete}
 * 方法创建自定义遍历例如,为了定义仅以第三个ForEach示例的形式分割右手任务的MapReducer,完成必须沿着未用尽的子任务链接协作地减少,这可以如下进行：。
 * 
 * 
 * @since 1.8
 * @author Doug Lea
 */
public abstract class CountedCompleter<T> extends ForkJoinTask<T> {
    private static final long serialVersionUID = 5232453752276485070L;

    /** This task's completer, or null if none */
    final CountedCompleter<?> completer;
    /** The number of pending tasks until completion */
    volatile int pending;

    /**
     * Creates a new CountedCompleter with the given completer
     * and initial pending count.
     *
     * <p>
     * <pre> {@code class MapReducer <E> extends CountedCompleter </> {// version 2 final E [] array;最终MyMapper <E> mapper;最终MyReducer <E>减速器; final int lo,hi; MapReducer <E> forks,next; //在列表E中记录子任务fork MapReducer(CountedCompleter <p> E [] array,MyMapper <E> mapper,MyReducer <E> reducer,int lo,int hi,MapReducer <E> next){super thisarray = array; thismapper = mapper; thisreducer = reducer; thislo = lo; thishi = hi; thisnext = next; }
     *  public void compute(){int l = lo,h = hi;而(h-1> = 2){int mid =(1 + h)>>> 1; addToPendingCount(1) (forks = new MapReducer(this,array,mapper,reducer,mid,h,forks))fork(); h = mid; }
     *  if(h> l)result = mapperapply(array [l]); //通过减少子任务链接来处理完成(CountedCompleter <c> c = firstComplete(); 
     * c！= null; c = cnextComplete()){for(MapReducer t =(MapReducer)c,s = tforks; s ！= null; s = tforks = snext)tresult = reducerapply(tresult,sresult); }
     * } public E getRawResult(){return result; }}。
     * 
     * public static <E> E mapReduce(E []数组,MyMapper <E> mapper,MyReducer <E> reducer){return new MapReducer(null,array,mapper,reducer,0,arraylength,null)invoke ; }
     * }} </pre>。
     * 
     *  <p> <b>触发</b>一些CountedCompleters本身从不被分叉,而是在其他设计中作为管道的一部分;包括其中完成一个或多个异步任务触发另一个异步任务的那些例如：
     * 
     * <> {@code class HeaderBuilder extends CountedCompleter <> {} class BodyBuilder extends CountedComplet
     * er <> {} class PacketSender extends CountedCompleter <> {PacketSender(){super(null,1); } //第二次完成时触发pu
     * blic void compute(){} //从不调用public void onCompletion(CountedCompleter <?> caller){sendPacket }} //样例使
     * 用：PacketSender p = new PacketSender(); new HeaderBuilder(p,)fork(); new BodyBuilder(p,)fork(); } </pre>
     * 。
     * 
     * 
     * @param completer this task's completer, or {@code null} if none
     * @param initialPendingCount the initial pending count
     */
    protected CountedCompleter(CountedCompleter<?> completer,
                               int initialPendingCount) {
        this.completer = completer;
        this.pending = initialPendingCount;
    }

    /**
     * Creates a new CountedCompleter with the given completer
     * and an initial pending count of zero.
     *
     * <p>
     *  使用给定的完成者和初始暂挂计数创建一个新的CountedCompleter
     * 
     * 
     * @param completer this task's completer, or {@code null} if none
     */
    protected CountedCompleter(CountedCompleter<?> completer) {
        this.completer = completer;
    }

    /**
     * Creates a new CountedCompleter with no completer
     * and an initial pending count of zero.
     * <p>
     *  使用给定的完成者创建一个新的CountedCompleter,初始挂起计数为零
     * 
     */
    protected CountedCompleter() {
        this.completer = null;
    }

    /**
     * The main computation performed by this task.
     * <p>
     *  创建一个没有完成者和初始挂起计数为零的新CountedCompleter
     * 
     */
    public abstract void compute();

    /**
     * Performs an action when method {@link #tryComplete} is invoked
     * and the pending count is zero, or when the unconditional
     * method {@link #complete} is invoked.  By default, this method
     * does nothing. You can distinguish cases by checking the
     * identity of the given caller argument. If not equal to {@code
     * this}, then it is typically a subtask that may contain results
     * (and/or links to other results) to combine.
     *
     * <p>
     * 这个任务执行的主要计算
     * 
     * 
     * @param caller the task invoking this method (which may
     * be this task itself)
     */
    public void onCompletion(CountedCompleter<?> caller) {
    }

    /**
     * Performs an action when method {@link
     * #completeExceptionally(Throwable)} is invoked or method {@link
     * #compute} throws an exception, and this task has not already
     * otherwise completed normally. On entry to this method, this task
     * {@link ForkJoinTask#isCompletedAbnormally}.  The return value
     * of this method controls further propagation: If {@code true}
     * and this task has a completer that has not completed, then that
     * completer is also completed exceptionally, with the same
     * exception as this completer.  The default implementation of
     * this method does nothing except return {@code true}.
     *
     * <p>
     *  在调用方法{@link #tryComplete}并且挂起计数为零时,或者调用无条件方法{@link #complete}时执行操作默认情况下,此方法不执行任何操作您可以通过检查给定caller参数如
     * 果不等于{@code this},则它通常是可以包含结果(和/或到其他结果的链接)的子任务以组合。
     * 
     * 
     * @param ex the exception
     * @param caller the task invoking this method (which may
     * be this task itself)
     * @return {@code true} if this exception should be propagated to this
     * task's completer, if one exists
     */
    public boolean onExceptionalCompletion(Throwable ex, CountedCompleter<?> caller) {
        return true;
    }

    /**
     * Returns the completer established in this task's constructor,
     * or {@code null} if none.
     *
     * <p>
     * 在调用方法{@link #completeExceptionally(Throwable)}或方法{@link #compute}抛出异常时执行操作,并且此任务尚未正常完成。
     * 在此方法的入口,此任务{@link ForkJoinTask# isCompletedAbnormally}此方法的返回值控制进一步传播：如果{@code true}并且此任务具有尚未完成的完成者,那么
     * 完成者也将异常完成,与此完成者具有相同的异常此方法的默认实现除了返回{@code true}。
     * 在调用方法{@link #completeExceptionally(Throwable)}或方法{@link #compute}抛出异常时执行操作,并且此任务尚未正常完成。
     * 
     * 
     * @return the completer
     */
    public final CountedCompleter<?> getCompleter() {
        return completer;
    }

    /**
     * Returns the current pending count.
     *
     * <p>
     *  返回在此任务的构造函数中建立的完成者,如果没有则返回{@code null}
     * 
     * 
     * @return the current pending count
     */
    public final int getPendingCount() {
        return pending;
    }

    /**
     * Sets the pending count to the given value.
     *
     * <p>
     *  返回当前的挂起计数
     * 
     * 
     * @param count the count
     */
    public final void setPendingCount(int count) {
        pending = count;
    }

    /**
     * Adds (atomically) the given value to the pending count.
     *
     * <p>
     *  将挂起计数设置为给定值
     * 
     * 
     * @param delta the value to add
     */
    public final void addToPendingCount(int delta) {
        U.getAndAddInt(this, PENDING, delta);
    }

    /**
     * Sets (atomically) the pending count to the given count only if
     * it currently holds the given expected value.
     *
     * <p>
     * 将(给定值)添加(以原子方式)到挂起计数
     * 
     * 
     * @param expected the expected value
     * @param count the new value
     * @return {@code true} if successful
     */
    public final boolean compareAndSetPendingCount(int expected, int count) {
        return U.compareAndSwapInt(this, PENDING, expected, count);
    }

    /**
     * If the pending count is nonzero, (atomically) decrements it.
     *
     * <p>
     *  仅当当前持有给定的期望值时,将挂起计数设置(以原子方式)为给定计数
     * 
     * 
     * @return the initial (undecremented) pending count holding on entry
     * to this method
     */
    public final int decrementPendingCountUnlessZero() {
        int c;
        do {} while ((c = pending) != 0 &&
                     !U.compareAndSwapInt(this, PENDING, c, c - 1));
        return c;
    }

    /**
     * Returns the root of the current computation; i.e., this
     * task if it has no completer, else its completer's root.
     *
     * <p>
     *  如果挂起计数是非零,(原子)减少它
     * 
     * 
     * @return the root of the current computation
     */
    public final CountedCompleter<?> getRoot() {
        CountedCompleter<?> a = this, p;
        while ((p = a.completer) != null)
            a = p;
        return a;
    }

    /**
     * If the pending count is nonzero, decrements the count;
     * otherwise invokes {@link #onCompletion(CountedCompleter)}
     * and then similarly tries to complete this task's completer,
     * if one exists, else marks this task as complete.
     * <p>
     *  返回当前计算的根;即,如果任务没有完成者,那么它的完成者的根
     * 
     */
    public final void tryComplete() {
        CountedCompleter<?> a = this, s = a;
        for (int c;;) {
            if ((c = a.pending) == 0) {
                a.onCompletion(s);
                if ((a = (s = a).completer) == null) {
                    s.quietlyComplete();
                    return;
                }
            }
            else if (U.compareAndSwapInt(a, PENDING, c, c - 1))
                return;
        }
    }

    /**
     * Equivalent to {@link #tryComplete} but does not invoke {@link
     * #onCompletion(CountedCompleter)} along the completion path:
     * If the pending count is nonzero, decrements the count;
     * otherwise, similarly tries to complete this task's completer, if
     * one exists, else marks this task as complete. This method may be
     * useful in cases where {@code onCompletion} should not, or need
     * not, be invoked for each completer in a computation.
     * <p>
     *  如果挂起计数为非零,则递减计数;否则调用{@link #onCompletion(CountedCompleter)},然后类似地尝试完成此任务的完成者(如果存在),否则将此任务标记为完成
     * 
     */
    public final void propagateCompletion() {
        CountedCompleter<?> a = this, s = a;
        for (int c;;) {
            if ((c = a.pending) == 0) {
                if ((a = (s = a).completer) == null) {
                    s.quietlyComplete();
                    return;
                }
            }
            else if (U.compareAndSwapInt(a, PENDING, c, c - 1))
                return;
        }
    }

    /**
     * Regardless of pending count, invokes
     * {@link #onCompletion(CountedCompleter)}, marks this task as
     * complete and further triggers {@link #tryComplete} on this
     * task's completer, if one exists.  The given rawResult is
     * used as an argument to {@link #setRawResult} before invoking
     * {@link #onCompletion(CountedCompleter)} or marking this task
     * as complete; its value is meaningful only for classes
     * overriding {@code setRawResult}.  This method does not modify
     * the pending count.
     *
     * <p>This method may be useful when forcing completion as soon as
     * any one (versus all) of several subtask results are obtained.
     * However, in the common (and recommended) case in which {@code
     * setRawResult} is not overridden, this effect can be obtained
     * more simply using {@code quietlyCompleteRoot();}.
     *
     * <p>
     * 相当于{@link #tryComplete},但不沿着完成路径调用{@link #onCompletion(CountedCompleter)}：如果挂起计数不为零,则递减计数;否则,类似地尝试完成此
     * 任务的完成者,如果存在,否则将此任务标记为完成此方法可能在{@code onCompletion}不应该或不需要在计算中的每个完成者被调用的情况下是有用的。
     * 
     * 
     * @param rawResult the raw result
     */
    public void complete(T rawResult) {
        CountedCompleter<?> p;
        setRawResult(rawResult);
        onCompletion(this);
        quietlyComplete();
        if ((p = completer) != null)
            p.tryComplete();
    }

    /**
     * If this task's pending count is zero, returns this task;
     * otherwise decrements its pending count and returns {@code
     * null}. This method is designed to be used with {@link
     * #nextComplete} in completion traversal loops.
     *
     * <p>
     * 无论挂起计数,调用{@link #onCompletion(CountedCompleter)},将此任务标记为完成,并进一步触发{@link #tryComplete}此任务的完成者(如果存在)给定的
     * rawResult用作{@link #setRawResult},然后调用{@link #onCompletion(CountedCompleter)}或将此任务标记为完成;其值仅对覆盖{@code setRawResult}
     * 的类有意义此方法不修改挂起计数。
     * 
     *  <p>当获得几个子任务结果中的任何一个(相对于所有)时强制完成此方法可能很有用但是,在未覆盖{@code setRawResult}的公共(和推荐)情况下,此效果可以使用{@code quietlyCompleteRoot();}
     * 。
     * 
     * 
     * @return this task, if pending count was zero, else {@code null}
     */
    public final CountedCompleter<?> firstComplete() {
        for (int c;;) {
            if ((c = pending) == 0)
                return this;
            else if (U.compareAndSwapInt(this, PENDING, c, c - 1))
                return null;
        }
    }

    /**
     * If this task does not have a completer, invokes {@link
     * ForkJoinTask#quietlyComplete} and returns {@code null}.  Or, if
     * the completer's pending count is non-zero, decrements that
     * pending count and returns {@code null}.  Otherwise, returns the
     * completer.  This method can be used as part of a completion
     * traversal loop for homogeneous task hierarchies:
     *
     * <pre> {@code
     * for (CountedCompleter<?> c = firstComplete();
     *      c != null;
     *      c = c.nextComplete()) {
     *   // ... process c ...
     * }}</pre>
     *
     * <p>
     * 如果此任务的挂起计数为零,则返回此任务;否则递减其未决计数并返回{@code null}此方法设计为与完成遍历循环中的{@link #nextComplete}一起使用
     * 
     * 
     * @return the completer, or {@code null} if none
     */
    public final CountedCompleter<?> nextComplete() {
        CountedCompleter<?> p;
        if ((p = completer) != null)
            return p.firstComplete();
        else {
            quietlyComplete();
            return null;
        }
    }

    /**
     * Equivalent to {@code getRoot().quietlyComplete()}.
     * <p>
     *  如果此任务没有完成者,调用{@link ForkJoinTask#quietlyComplete}并返回{@code null}或者,如果完成者的挂起计数不为零,则递减挂起计数并返回{@code null}
     * 否则返回完成者此方法可以用作同质任务层次结构的完成遍历循环的一部分：。
     * 
     *  <pre> {@code for(CountedCompleter <?> c = firstComplete(); c！= null; c = cnextComplete()){// process c}
     * } </pre>。
     * 
     */
    public final void quietlyCompleteRoot() {
        for (CountedCompleter<?> a = this, p;;) {
            if ((p = a.completer) == null) {
                a.quietlyComplete();
                return;
            }
            a = p;
        }
    }

    /**
     * If this task has not completed, attempts to process at most the
     * given number of other unprocessed tasks for which this task is
     * on the completion path, if any are known to exist.
     *
     * <p>
     *  相当于{@code getRoot()quietlyComplete()}
     * 
     * 
     * @param maxTasks the maximum number of tasks to process.  If
     *                 less than or equal to zero, then no tasks are
     *                 processed.
     */
    public final void helpComplete(int maxTasks) {
        Thread t; ForkJoinWorkerThread wt;
        if (maxTasks > 0 && status >= 0) {
            if ((t = Thread.currentThread()) instanceof ForkJoinWorkerThread)
                (wt = (ForkJoinWorkerThread)t).pool.
                    helpComplete(wt.workQueue, this, maxTasks);
            else
                ForkJoinPool.common.externalHelpComplete(this, maxTasks);
        }
    }

    /**
     * Supports ForkJoinTask exception propagation.
     * <p>
     * 如果此任务尚未完成,则尝试处理最多给定数量的其他未处理任务,如果有任务已经存在于此完成路径上,则该任务已存在
     * 
     */
    void internalPropagateException(Throwable ex) {
        CountedCompleter<?> a = this, s = a;
        while (a.onExceptionalCompletion(ex, s) &&
               (a = (s = a).completer) != null && a.status >= 0 &&
               a.recordExceptionalCompletion(ex) == EXCEPTIONAL)
            ;
    }

    /**
     * Implements execution conventions for CountedCompleters.
     * <p>
     *  支持ForkJoinTask异常传播
     * 
     */
    protected final boolean exec() {
        compute();
        return false;
    }

    /**
     * Returns the result of the computation. By default
     * returns {@code null}, which is appropriate for {@code Void}
     * actions, but in other cases should be overridden, almost
     * always to return a field or function of a field that
     * holds the result upon completion.
     *
     * <p>
     *  实现CountedCompleters的执行约定
     * 
     * 
     * @return the result of the computation
     */
    public T getRawResult() { return null; }

    /**
     * A method that result-bearing CountedCompleters may optionally
     * use to help maintain result data.  By default, does nothing.
     * Overrides are not recommended. However, if this method is
     * overridden to update existing objects or fields, then it must
     * in general be defined to be thread-safe.
     * <p>
     *  返回计算结果默认返回{@code null},这适用于{@code Void}操作,但在其他情况下应该被覆盖,几乎总是返回一个字段或函数的字段,完成
     * 
     */
    protected void setRawResult(T t) { }

    // Unsafe mechanics
    private static final sun.misc.Unsafe U;
    private static final long PENDING;
    static {
        try {
            U = sun.misc.Unsafe.getUnsafe();
            PENDING = U.objectFieldOffset
                (CountedCompleter.class.getDeclaredField("pending"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}
