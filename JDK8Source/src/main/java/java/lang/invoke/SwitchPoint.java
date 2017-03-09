/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
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

/**
 * <p>
 * A {@code SwitchPoint} is an object which can publish state transitions to other threads.
 * A switch point is initially in the <em>valid</em> state, but may at any time be
 * changed to the <em>invalid</em> state.  Invalidation cannot be reversed.
 * A switch point can combine a <em>guarded pair</em> of method handles into a
 * <em>guarded delegator</em>.
 * The guarded delegator is a method handle which delegates to one of the old method handles.
 * The state of the switch point determines which of the two gets the delegation.
 * <p>
 * A single switch point may be used to control any number of method handles.
 * (Indirectly, therefore, it can control any number of call sites.)
 * This is done by using the single switch point as a factory for combining
 * any number of guarded method handle pairs into guarded delegators.
 * <p>
 * When a guarded delegator is created from a guarded pair, the pair
 * is wrapped in a new method handle {@code M},
 * which is permanently associated with the switch point that created it.
 * Each pair consists of a target {@code T} and a fallback {@code F}.
 * While the switch point is valid, invocations to {@code M} are delegated to {@code T}.
 * After it is invalidated, invocations are delegated to {@code F}.
 * <p>
 * Invalidation is global and immediate, as if the switch point contained a
 * volatile boolean variable consulted on every call to {@code M}.
 * The invalidation is also permanent, which means the switch point
 * can change state only once.
 * The switch point will always delegate to {@code F} after being invalidated.
 * At that point {@code guardWithTest} may ignore {@code T} and return {@code F}.
 * <p>
 * Here is an example of a switch point in action:
 * <blockquote><pre>{@code
MethodHandle MH_strcat = MethodHandles.lookup()
    .findVirtual(String.class, "concat", MethodType.methodType(String.class, String.class));
SwitchPoint spt = new SwitchPoint();
assert(!spt.hasBeenInvalidated());
// the following steps may be repeated to re-use the same switch point:
MethodHandle worker1 = MH_strcat;
MethodHandle worker2 = MethodHandles.permuteArguments(MH_strcat, MH_strcat.type(), 1, 0);
MethodHandle worker = spt.guardWithTest(worker1, worker2);
assertEquals("method", (String) worker.invokeExact("met", "hod"));
SwitchPoint.invalidateAll(new SwitchPoint[]{ spt });
assert(spt.hasBeenInvalidated());
assertEquals("hodmet", (String) worker.invokeExact("met", "hod"));
 * }</pre></blockquote>
 * <p style="font-size:smaller;">
 * <em>Discussion:</em>
 * Switch points are useful without subclassing.  They may also be subclassed.
 * This may be useful in order to associate application-specific invalidation logic
 * with the switch point.
 * Notice that there is no permanent association between a switch point and
 * the method handles it produces and consumes.
 * The garbage collector may collect method handles produced or consumed
 * by a switch point independently of the lifetime of the switch point itself.
 * <p style="font-size:smaller;">
 * <em>Implementation Note:</em>
 * A switch point behaves as if implemented on top of {@link MutableCallSite},
 * approximately as follows:
 * <blockquote><pre>{@code
public class SwitchPoint {
  private static final MethodHandle
    K_true  = MethodHandles.constant(boolean.class, true),
    K_false = MethodHandles.constant(boolean.class, false);
  private final MutableCallSite mcs;
  private final MethodHandle mcsInvoker;
  public SwitchPoint() {
    this.mcs = new MutableCallSite(K_true);
    this.mcsInvoker = mcs.dynamicInvoker();
  }
  public MethodHandle guardWithTest(
                MethodHandle target, MethodHandle fallback) {
    // Note:  mcsInvoker is of type ()boolean.
    // Target and fallback may take any arguments, but must have the same type.
    return MethodHandles.guardWithTest(this.mcsInvoker, target, fallback);
  }
  public static void invalidateAll(SwitchPoint[] spts) {
    List&lt;MutableCallSite&gt; mcss = new ArrayList&lt;&gt;();
    for (SwitchPoint spt : spts)  mcss.add(spt.mcs);
    for (MutableCallSite mcs : mcss)  mcs.setTarget(K_false);
    MutableCallSite.syncAll(mcss.toArray(new MutableCallSite[0]));
  }
}
 * }</pre></blockquote>
 * <p>
 * <p>
 *  {@code SwitchPoint}是一个可以将状态转换发布到其他线程的对象。开关点最初处于<em>有效</em>状态,但可以随时更改为<em>无效</em>状态。无效无效。
 * 切换点可以将方法句柄的<em>保护对</em>组合到<em>守卫的委托者</em>中。受保护的委托器是一个方法句柄,它委托给其中一个旧的方法句柄。切换点的状态确定两个中的哪一个获得委派。
 * <p>
 *  单个开关点可以用于控制任何数量的方法句柄。 (间接地,因此,它可以控制任何数量的调用站点。)这是通过使用单个切换点作为工厂来组合任何数量的守卫方法句柄对到守护委托者。
 * <p>
 *  当受保护的委托者从保护对创建时,该对被包裹在一个新的方法句柄{@code M}中,它与创建它的交换点永久相关联。每个对由目标{@code T}和后备{@code F}组成。
 * 当切换点有效时,对{@code M}的调用将委派给{@code T}。在它失效后,调用委托给{@code F}。
 * <p>
 * 无效是全局的和立即的,就像切换点包含一个易失的布尔变量,在每次调用{@code M}时都会查询。无效也是永久的,这意味着切换点只能改变状态一次。切换点在被无效后总是委托给{@code F}。
 * 此时{@code guardWithTest}可能会忽略{@code T}并返回{@code F}。
 * <p>
 *  下面是一个切换点的示例：<blockquote> <pre> {@ code MethodHandle MH_strcat = MethodHandles.lookup().findVirtual(String.class,"concat",MethodType.methodType(String.class,String.class )); SwitchPoint spt = new SwitchPoint(); assert(！spt.hasBeenInvalidated()); //以下步骤可以重复使用同一个切换点：MethodHandle worker1 = MH_strcat; MethodHandle worker2 = MethodHandles.permuteArguments(MH_strcat,MH_strcat.type(),1,0); MethodHandle worker = spt.guardWithTest(worker1,worker2); assertEquals("method",(String)worker.invokeExact("met","hod")); SwitchPoint.invalidateAll(new SwitchPoint [] {spt}
 * ); assert(spt.hasBeenInvalidated()); assertEquals("hodmet",(String)worker.invokeExact("met","hod")); 
 * } </pre> </blockquote>。
 * <p style="font-size:smaller;">
 * <em>讨论：</em>切换点在没有子类化的情况下很有用。它们也可以是子类。这可以有用于将应用特定的无效逻辑与切换点相关联。请注意,在切换点和方法处理它产生和消耗之间没有永久关联。
 * 垃圾收集器可以独立于切换点本身的寿命而收集由切换点产生或消耗的方法句柄。
 * <p style="font-size:smaller;">
 * <em>实现注意：</em>切换点的行为好像是在{@link MutableCallSite}上实现的,大致如下：<blockquote> <pre> {@ code public class SwitchPoint {private static MethodHandle K_true = MethodHandles .constant(boolean.class,true),K_false = MethodHandles.constant(boolean.class,false); private final MutableCallSite mcs; private final MethodHandle mcsInvoker; public SwitchPoint(){this.mcs = new MutableCallSite(K_true); this.mcsInvoker = mcs.dynamicInvoker(); }
 *  public MethodHandle guardWithTest(MethodHandle target,MethodHandle fallback){//注意：mcsInvoker是type()boolean。
 *  //目标和回退可以接受任何参数,但必须具有相同的类型。
 *  return MethodHandles.guardWithTest(this.mcsInvoker,target,fallback); } public static void invalidate
 * All(SwitchPoint [] spts){List&lt; MutableCallSite&gt; mcss = new ArrayList&lt;&gt;(); for(SwitchPoint spt：spts)mcss.add(spt.mcs); for(MutableCallSite mcs：mcss)mcs.setTarget(K_false); MutableCallSite.syncAll(mcss.toArray(new MutableCallSite [0])); }
 * }} </pre> </blockquote>。
 *  //目标和回退可以接受任何参数,但必须具有相同的类型。
 * 
 * 
 * @author Remi Forax, JSR 292 EG
 */
public class SwitchPoint {
    private static final MethodHandle
        K_true  = MethodHandles.constant(boolean.class, true),
        K_false = MethodHandles.constant(boolean.class, false);

    private final MutableCallSite mcs;
    private final MethodHandle mcsInvoker;

    /**
     * Creates a new switch point.
     * <p>
     *  创建一个新的切换点。
     * 
     */
    public SwitchPoint() {
        this.mcs = new MutableCallSite(K_true);
        this.mcsInvoker = mcs.dynamicInvoker();
    }

    /**
     * Determines if this switch point has been invalidated yet.
     *
     * <p style="font-size:smaller;">
     * <em>Discussion:</em>
     * Because of the one-way nature of invalidation, once a switch point begins
     * to return true for {@code hasBeenInvalidated},
     * it will always do so in the future.
     * On the other hand, a valid switch point visible to other threads may
     * be invalidated at any moment, due to a request by another thread.
     * <p style="font-size:smaller;">
     * Since invalidation is a global and immediate operation,
     * the execution of this query, on a valid switchpoint,
     * must be internally sequenced with any
     * other threads that could cause invalidation.
     * This query may therefore be expensive.
     * The recommended way to build a boolean-valued method handle
     * which queries the invalidation state of a switch point {@code s} is
     * to call {@code s.guardWithTest} on
     * {@link MethodHandles#constant constant} true and false method handles.
     *
     * <p>
     *  确定此切换点是否已失效。
     * 
     * <p style="font-size:smaller;">
     *  <em>讨论：</em>由于无效的单向性质,一旦切换点开始为{@code hasBeenInvalidated}返回true,它将来总是这样做。
     * 另一方面,由于另一个线程的请求,在其他线程可见的有效切换点可能在任何时刻被无效。
     * <p style="font-size:smaller;">
     * 由于无效是全局和立即操作,因此在有效交换点上执行此查询必须与可能导致无效的任何其他线程内部排序。因此,该查询可能是昂贵的。
     * 建立一个查询切换点{@code s}的无效状态的布尔值方法句柄的建议方法是在{@link MethodHandles#constant constant} true和false方法句柄上调用{@code s.guardWithTest}
     * 。
     * 由于无效是全局和立即操作,因此在有效交换点上执行此查询必须与可能导致无效的任何其他线程内部排序。因此,该查询可能是昂贵的。
     * 
     * 
     * @return true if this switch point has been invalidated
     */
    public boolean hasBeenInvalidated() {
        return (mcs.getTarget() != K_true);
    }

    /**
     * Returns a method handle which always delegates either to the target or the fallback.
     * The method handle will delegate to the target exactly as long as the switch point is valid.
     * After that, it will permanently delegate to the fallback.
     * <p>
     * The target and fallback must be of exactly the same method type,
     * and the resulting combined method handle will also be of this type.
     *
     * <p>
     *  返回一个方法句柄,它总是委派给目标或回退。只要切换点有效,方法句柄就会完全委派给目标。之后,它将永久委派给后备。
     * <p>
     *  目标和后备必须具有完全相同的方法类型,并且生成的组合方法句柄也将是此类型。
     * 
     * 
     * @param target the method handle selected by the switch point as long as it is valid
     * @param fallback the method handle selected by the switch point after it is invalidated
     * @return a combined method handle which always calls either the target or fallback
     * @throws NullPointerException if either argument is null
     * @throws IllegalArgumentException if the two method types do not match
     * @see MethodHandles#guardWithTest
     */
    public MethodHandle guardWithTest(MethodHandle target, MethodHandle fallback) {
        if (mcs.getTarget() == K_false)
            return fallback;  // already invalid
        return MethodHandles.guardWithTest(mcsInvoker, target, fallback);
    }

    /**
     * Sets all of the given switch points into the invalid state.
     * After this call executes, no thread will observe any of the
     * switch points to be in a valid state.
     * <p>
     * This operation is likely to be expensive and should be used sparingly.
     * If possible, it should be buffered for batch processing on sets of switch points.
     * <p>
     * If {@code switchPoints} contains a null element,
     * a {@code NullPointerException} will be raised.
     * In this case, some non-null elements in the array may be
     * processed before the method returns abnormally.
     * Which elements these are (if any) is implementation-dependent.
     *
     * <p style="font-size:smaller;">
     * <em>Discussion:</em>
     * For performance reasons, {@code invalidateAll} is not a virtual method
     * on a single switch point, but rather applies to a set of switch points.
     * Some implementations may incur a large fixed overhead cost
     * for processing one or more invalidation operations,
     * but a small incremental cost for each additional invalidation.
     * In any case, this operation is likely to be costly, since
     * other threads may have to be somehow interrupted
     * in order to make them notice the updated switch point state.
     * However, it may be observed that a single call to invalidate
     * several switch points has the same formal effect as many calls,
     * each on just one of the switch points.
     *
     * <p style="font-size:smaller;">
     * <em>Implementation Note:</em>
     * Simple implementations of {@code SwitchPoint} may use
     * a private {@link MutableCallSite} to publish the state of a switch point.
     * In such an implementation, the {@code invalidateAll} method can
     * simply change the call site's target, and issue one call to
     * {@linkplain MutableCallSite#syncAll synchronize} all the
     * private call sites.
     *
     * <p>
     *  将所有给定的切换点设置为无效状态。在该调用执行之后,没有线程将观察到任何切换点处于有效状态。
     * <p>
     *  这种操作可能是昂贵的,应该谨慎使用。如果可能,应缓冲它用于对多组切换点进行批处理。
     * <p>
     *  如果{@code switchPoints}包含一个空元素,将会引发{@code NullPointerException}。在这种情况下,可以在该方法异常返回之前处理数组中的一些非空元素。
     * 这些元素(如果有的话)是与实现相关的。
     * 
     * <p style="font-size:smaller;">
     * <em>讨论：</em>出于性能原因,{@code invalidateAll}不是单个切换点上的虚拟方法,而是适用于一组切换点。
     * 
     * @param switchPoints an array of call sites to be synchronized
     * @throws NullPointerException if the {@code switchPoints} array reference is null
     *                              or the array contains a null
     */
    public static void invalidateAll(SwitchPoint[] switchPoints) {
        if (switchPoints.length == 0)  return;
        MutableCallSite[] sites = new MutableCallSite[switchPoints.length];
        for (int i = 0; i < switchPoints.length; i++) {
            SwitchPoint spt = switchPoints[i];
            if (spt == null)  break;  // MSC.syncAll will trigger a NPE
            sites[i] = spt.mcs;
            spt.mcs.setTarget(K_false);
        }
        MutableCallSite.syncAll(sites);
    }
}
