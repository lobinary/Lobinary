/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2008, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.invoke;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * A {@code MutableCallSite} is a {@link CallSite} whose target variable
 * behaves like an ordinary field.
 * An {@code invokedynamic} instruction linked to a {@code MutableCallSite} delegates
 * all calls to the site's current target.
 * The {@linkplain CallSite#dynamicInvoker dynamic invoker} of a mutable call site
 * also delegates each call to the site's current target.
 * <p>
 * Here is an example of a mutable call site which introduces a
 * state variable into a method handle chain.
 * <!-- JavaDocExamplesTest.testMutableCallSite -->
 * <blockquote><pre>{@code
MutableCallSite name = new MutableCallSite(MethodType.methodType(String.class));
MethodHandle MH_name = name.dynamicInvoker();
MethodType MT_str1 = MethodType.methodType(String.class);
MethodHandle MH_upcase = MethodHandles.lookup()
    .findVirtual(String.class, "toUpperCase", MT_str1);
MethodHandle worker1 = MethodHandles.filterReturnValue(MH_name, MH_upcase);
name.setTarget(MethodHandles.constant(String.class, "Rocky"));
assertEquals("ROCKY", (String) worker1.invokeExact());
name.setTarget(MethodHandles.constant(String.class, "Fred"));
assertEquals("FRED", (String) worker1.invokeExact());
// (mutation can be continued indefinitely)
 * }</pre></blockquote>
 * <p>
 * The same call site may be used in several places at once.
 * <blockquote><pre>{@code
MethodType MT_str2 = MethodType.methodType(String.class, String.class);
MethodHandle MH_cat = lookup().findVirtual(String.class,
  "concat", methodType(String.class, String.class));
MethodHandle MH_dear = MethodHandles.insertArguments(MH_cat, 1, ", dear?");
MethodHandle worker2 = MethodHandles.filterReturnValue(MH_name, MH_dear);
assertEquals("Fred, dear?", (String) worker2.invokeExact());
name.setTarget(MethodHandles.constant(String.class, "Wilma"));
assertEquals("WILMA", (String) worker1.invokeExact());
assertEquals("Wilma, dear?", (String) worker2.invokeExact());
 * }</pre></blockquote>
 * <p>
 * <em>Non-synchronization of target values:</em>
 * A write to a mutable call site's target does not force other threads
 * to become aware of the updated value.  Threads which do not perform
 * suitable synchronization actions relative to the updated call site
 * may cache the old target value and delay their use of the new target
 * value indefinitely.
 * (This is a normal consequence of the Java Memory Model as applied
 * to object fields.)
 * <p>
 * The {@link #syncAll syncAll} operation provides a way to force threads
 * to accept a new target value, even if there is no other synchronization.
 * <p>
 * For target values which will be frequently updated, consider using
 * a {@linkplain VolatileCallSite volatile call site} instead.
 * <p>
 *  {@code MutableCallSite}是一个{@link CallSite},其目标变量的行为类似于普通字段。
 * 链接到{@code MutableCallSite}的{@code invokedynamic}指令会将所有调用委派给网站的当前目标。
 * 可变调用网站的{@linkplain CallSite#dynamicInvoker动态调用器}还将每个调用委派给网站的当前目标。
 * <p>
 *  这里有一个可变调用站点的例子,它将状态变量引入到方法句柄链中。
 * <!-- JavaDocExamplesTest.testMutableCallSite -->
 *  <blockquote> <pre> {@ code MutableCallSite name = new MutableCallSite(MethodType.methodType(String.class)); MethodHandle MH_name = name.dynamicInvoker(); MethodType MT_str1 = MethodType.methodType(String.class); MethodHandle MH_upcase = MethodHandles.lookup().findVirtual(String.class,"toUpperCase",MT_str1); MethodHandle worker1 = MethodHandles.filterReturnValue(MH_name,MH_upcase); name.setTarget(MethodHandles.constant(String.class,"Rocky")); assertEquals("ROCKY",(String)worker1.invokeExact()); name.setTarget(MethodHandles.constant(String.class,"Fred")); assertEquals("FRED",(String)worker1.invokeExact()); //(突变可以无限期地继续)}
 *  </pre> </blockquote>。
 * <p>
 * 同一呼叫站点可以在多个地方同时使用。
 *  <blockquote> <pre> {@ code MethodType MT_str2 = MethodType.methodType(String.class,String.class); MethodHandle MH_cat = lookup()。
 * 同一呼叫站点可以在多个地方同时使用。
 * findVirtual(String.class,"concat",methodType(String.class,String.class)); MethodHandle MH_dear = Meth
 * odHandles.insertArguments(MH_cat,1,",dear?"); MethodHandle worker2 = MethodHandles.filterReturnValue(
 * MH_name,MH_dear); assertEquals("Fred,dear?",(String)worker2.invokeExact()); name.setTarget(MethodHand
 * les.constant(String.class,"Wilma")); assertEquals("WILMA",(String)worker1.invokeExact()); assertEqual
 * s("Wilma,dear?",(String)worker2.invokeExact()); } </pre> </blockquote>。
 * 同一呼叫站点可以在多个地方同时使用。
 * <p>
 *  <em>目标值的不同步：</em>对可变调用调用网站的目标的写入不会强制其他线程意识到更新的值。不相对于更新的调用点执行合适的同步动作的线程可以缓存旧的目标值并且无限期地延迟它们对新目标值的使用。
 *  (这是Java内存模型应用于对象字段的正常结果。)。
 * <p>
 *  {@link #syncAll syncAll}操作提供了一种强制线程接受新目标值的方法,即使没有其他同步也是如此。
 * <p>
 *  对于将频繁更新的目标值,请考虑使用{@linkplain VolatileCallSite volatile调用站点}。
 * 
 * 
 * @author John Rose, JSR 292 EG
 */
public class MutableCallSite extends CallSite {
    /**
     * Creates a blank call site object with the given method type.
     * The initial target is set to a method handle of the given type
     * which will throw an {@link IllegalStateException} if called.
     * <p>
     * The type of the call site is permanently set to the given type.
     * <p>
     * Before this {@code CallSite} object is returned from a bootstrap method,
     * or invoked in some other manner,
     * it is usually provided with a more useful target method,
     * via a call to {@link CallSite#setTarget(MethodHandle) setTarget}.
     * <p>
     * 使用给定方法类型创建空白调用站点对象。初始目标被设置为给定类型的方法句柄,如果被调用,它将抛出一个{@link IllegalStateException}。
     * <p>
     *  呼叫站点的类型永久设置为给定类型。
     * <p>
     *  在此{@code CallSite}对象从引导方法返回或以其他方式调用之前,通常通过调用{@link CallSite#setTarget(MethodHandle)setTarget}来提供更有用的
     * 目标方法。
     * 
     * 
     * @param type the method type that this call site will have
     * @throws NullPointerException if the proposed type is null
     */
    public MutableCallSite(MethodType type) {
        super(type);
    }

    /**
     * Creates a call site object with an initial target method handle.
     * The type of the call site is permanently set to the initial target's type.
     * <p>
     *  使用初始目标方法句柄创建调用站点对象。呼叫站点的类型永久设置为初始目标的类型。
     * 
     * 
     * @param target the method handle that will be the initial target of the call site
     * @throws NullPointerException if the proposed target is null
     */
    public MutableCallSite(MethodHandle target) {
        super(target);
    }

    /**
     * Returns the target method of the call site, which behaves
     * like a normal field of the {@code MutableCallSite}.
     * <p>
     * The interactions of {@code getTarget} with memory are the same
     * as of a read from an ordinary variable, such as an array element or a
     * non-volatile, non-final field.
     * <p>
     * In particular, the current thread may choose to reuse the result
     * of a previous read of the target from memory, and may fail to see
     * a recent update to the target by another thread.
     *
     * <p>
     *  返回调用网站的目标方法,其行为类似于{@code MutableCallSite}的常规字段。
     * <p>
     *  {@code getTarget}与存储器的交互与从普通变量(例如数组元素或非易失性,非最终字段)的读取相同。
     * <p>
     *  具体地,当前线程可以选择重用来自存储器的目标的先前读取的结果,并且可能未能看到另一线程对目标的最近更新。
     * 
     * 
     * @return the linkage state of this call site, a method handle which can change over time
     * @see #setTarget
     */
    @Override public final MethodHandle getTarget() {
        return target;
    }

    /**
     * Updates the target method of this call site, as a normal variable.
     * The type of the new target must agree with the type of the old target.
     * <p>
     * The interactions with memory are the same
     * as of a write to an ordinary variable, such as an array element or a
     * non-volatile, non-final field.
     * <p>
     * In particular, unrelated threads may fail to see the updated target
     * until they perform a read from memory.
     * Stronger guarantees can be created by putting appropriate operations
     * into the bootstrap method and/or the target methods used
     * at any given call site.
     *
     * <p>
     *  将此调用网站的目标方法更新为正常变量。新目标的类型必须与旧目标的类型一致。
     * <p>
     *  与对存储器的交互与对普通变量(例如数组元素或非易失性,非最终字段)的写入相同。
     * <p>
     * 特别地,不相关的线程可能无法看到更新的目标,直到它们执行从存储器的读取。可以通过将适当的操作放入引导方法和/或在任何给定调用站点使用的目标方法来创建更强的保证。
     * 
     * 
     * @param newTarget the new target
     * @throws NullPointerException if the proposed new target is null
     * @throws WrongMethodTypeException if the proposed new target
     *         has a method type that differs from the previous target
     * @see #getTarget
     */
    @Override public void setTarget(MethodHandle newTarget) {
        checkTargetChange(this.target, newTarget);
        setTargetNormal(newTarget);
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    public final MethodHandle dynamicInvoker() {
        return makeDynamicInvoker();
    }

    /**
     * Performs a synchronization operation on each call site in the given array,
     * forcing all other threads to throw away any cached values previously
     * loaded from the target of any of the call sites.
     * <p>
     * This operation does not reverse any calls that have already started
     * on an old target value.
     * (Java supports {@linkplain java.lang.Object#wait() forward time travel} only.)
     * <p>
     * The overall effect is to force all future readers of each call site's target
     * to accept the most recently stored value.
     * ("Most recently" is reckoned relative to the {@code syncAll} itself.)
     * Conversely, the {@code syncAll} call may block until all readers have
     * (somehow) decached all previous versions of each call site's target.
     * <p>
     * To avoid race conditions, calls to {@code setTarget} and {@code syncAll}
     * should generally be performed under some sort of mutual exclusion.
     * Note that reader threads may observe an updated target as early
     * as the {@code setTarget} call that install the value
     * (and before the {@code syncAll} that confirms the value).
     * On the other hand, reader threads may observe previous versions of
     * the target until the {@code syncAll} call returns
     * (and after the {@code setTarget} that attempts to convey the updated version).
     * <p>
     * This operation is likely to be expensive and should be used sparingly.
     * If possible, it should be buffered for batch processing on sets of call sites.
     * <p>
     * If {@code sites} contains a null element,
     * a {@code NullPointerException} will be raised.
     * In this case, some non-null elements in the array may be
     * processed before the method returns abnormally.
     * Which elements these are (if any) is implementation-dependent.
     *
     * <h1>Java Memory Model details</h1>
     * In terms of the Java Memory Model, this operation performs a synchronization
     * action which is comparable in effect to the writing of a volatile variable
     * by the current thread, and an eventual volatile read by every other thread
     * that may access one of the affected call sites.
     * <p>
     * The following effects are apparent, for each individual call site {@code S}:
     * <ul>
     * <li>A new volatile variable {@code V} is created, and written by the current thread.
     *     As defined by the JMM, this write is a global synchronization event.
     * <li>As is normal with thread-local ordering of write events,
     *     every action already performed by the current thread is
     *     taken to happen before the volatile write to {@code V}.
     *     (In some implementations, this means that the current thread
     *     performs a global release operation.)
     * <li>Specifically, the write to the current target of {@code S} is
     *     taken to happen before the volatile write to {@code V}.
     * <li>The volatile write to {@code V} is placed
     *     (in an implementation specific manner)
     *     in the global synchronization order.
     * <li>Consider an arbitrary thread {@code T} (other than the current thread).
     *     If {@code T} executes a synchronization action {@code A}
     *     after the volatile write to {@code V} (in the global synchronization order),
     *     it is therefore required to see either the current target
     *     of {@code S}, or a later write to that target,
     *     if it executes a read on the target of {@code S}.
     *     (This constraint is called "synchronization-order consistency".)
     * <li>The JMM specifically allows optimizing compilers to elide
     *     reads or writes of variables that are known to be useless.
     *     Such elided reads and writes have no effect on the happens-before
     *     relation.  Regardless of this fact, the volatile {@code V}
     *     will not be elided, even though its written value is
     *     indeterminate and its read value is not used.
     * </ul>
     * Because of the last point, the implementation behaves as if a
     * volatile read of {@code V} were performed by {@code T}
     * immediately after its action {@code A}.  In the local ordering
     * of actions in {@code T}, this read happens before any future
     * read of the target of {@code S}.  It is as if the
     * implementation arbitrarily picked a read of {@code S}'s target
     * by {@code T}, and forced a read of {@code V} to precede it,
     * thereby ensuring communication of the new target value.
     * <p>
     * As long as the constraints of the Java Memory Model are obeyed,
     * implementations may delay the completion of a {@code syncAll}
     * operation while other threads ({@code T} above) continue to
     * use previous values of {@code S}'s target.
     * However, implementations are (as always) encouraged to avoid
     * livelock, and to eventually require all threads to take account
     * of the updated target.
     *
     * <p style="font-size:smaller;">
     * <em>Discussion:</em>
     * For performance reasons, {@code syncAll} is not a virtual method
     * on a single call site, but rather applies to a set of call sites.
     * Some implementations may incur a large fixed overhead cost
     * for processing one or more synchronization operations,
     * but a small incremental cost for each additional call site.
     * In any case, this operation is likely to be costly, since
     * other threads may have to be somehow interrupted
     * in order to make them notice the updated target value.
     * However, it may be observed that a single call to synchronize
     * several sites has the same formal effect as many calls,
     * each on just one of the sites.
     *
     * <p style="font-size:smaller;">
     * <em>Implementation Note:</em>
     * Simple implementations of {@code MutableCallSite} may use
     * a volatile variable for the target of a mutable call site.
     * In such an implementation, the {@code syncAll} method can be a no-op,
     * and yet it will conform to the JMM behavior documented above.
     *
     * <p>
     *  对给定数组中的每个调用站点执行同步操作,强制所有其他线程丢弃先前从任何调用站点的目标加载的任何缓存值。
     * <p>
     *  此操作不会反转已在旧目标值上启动的任何呼叫。 (Java仅支持{@linkplain java.lang.Object#wait()forward time travel}。)
     * <p>
     *  总体效果是强制每个调用点的目标的所有未来读者接受最近存储的值。 ("最近"相对于{@code syncAll}本身。
     * )相反,{@code syncAll}调用可能会阻塞,直到所有读者都以某种方式解析了每个调用网站的目标的所有先前版本。
     * <p>
     *  为了避免竞争条件,对{@code setTarget}和{@code syncAll}的调用通常应在某种互斥的情况下执行。
     * 请注意,读取器线程可以在安装该值的{@code setTarget}调用(以及确认该值的{@code syncAll}之前)观察更新的目标。
     * 另一方面,读取器线程可以观察目标的先前版本,直到{@code syncAll}调用返回(并且在尝试传送更新版本的{@code setTarget}之后)。
     * <p>
     * 这种操作可能是昂贵的,应该谨慎使用。如果可能,应缓冲它用于批次处理一组调用站点。
     * <p>
     *  如果{@code sites}包含一个空元素,将会引发{@code NullPointerException}。在这种情况下,可以在该方法异常返回之前处理数组中的一些非空元素。
     * 这些元素(如果有的话)是与实现相关的。
     * 
     *  <h1> Java内存模型详细信息</h1>在Java内存模型方面,此操作执行的同步操作在效果上与当前线程写入volatile变量的效果相当,并且每隔一个线程,可以访问受影响的调用站点之一。
     * <p>
     *  对于每个个别电话网站{@code S},以下效果是显而易见的：
     * <ul>
     * <li>创建了一个新的易失性变量{@code V},由当前线程写入。如JMM定义的,这个写是一个全局同步事件。
     *  <li>与写事件的线程本地排序一样,当前线程已经执行的每个操作都发生在对{@code V}的易失性写操作之前。 (在某些实现中,这意味着当前线程执行全局释放操作。
     * )<li>具体来说,对{@code S}的当前目标的写入在对{@code V}的易失性写入之前发生。 <li>对{@code V}的易失性写入(以实现特定的方式)放置在全局同步顺序中。
     * 
     * @param sites an array of call sites to be synchronized
     * @throws NullPointerException if the {@code sites} array reference is null
     *                              or the array contains a null
     */
    public static void syncAll(MutableCallSite[] sites) {
        if (sites.length == 0)  return;
        STORE_BARRIER.lazySet(0);
        for (int i = 0; i < sites.length; i++) {
            sites[i].getClass();  // trigger NPE on first null
        }
        // FIXME: NYI
    }
    private static final AtomicInteger STORE_BARRIER = new AtomicInteger();
}
