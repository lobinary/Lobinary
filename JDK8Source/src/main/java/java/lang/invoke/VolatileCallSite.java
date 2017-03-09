/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2010, 2011, Oracle and/or its affiliates. All rights reserved.
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
 * A {@code VolatileCallSite} is a {@link CallSite} whose target acts like a volatile variable.
 * An {@code invokedynamic} instruction linked to a {@code VolatileCallSite} sees updates
 * to its call site target immediately, even if the update occurs in another thread.
 * There may be a performance penalty for such tight coupling between threads.
 * <p>
 * Unlike {@code MutableCallSite}, there is no
 * {@linkplain MutableCallSite#syncAll syncAll operation} on volatile
 * call sites, since every write to a volatile variable is implicitly
 * synchronized with reader threads.
 * <p>
 * In other respects, a {@code VolatileCallSite} is interchangeable
 * with {@code MutableCallSite}.
 * <p>
 *  {@code VolatileCallSite}是一个{@link CallSite},其目标的行为就像一个volatile变量。
 * 与{@code VolatileCallSite}链接的{@code invokedynamic}指令会立即看到其调用网站目标的更新,即使更新发生在另一个线程中。
 * 对于线程之间的这种紧耦合可能存在性能损失。
 * <p>
 *  与{@code MutableCallSite}不同,在易失性调用网站上没有{@linkplain MutableCallSite#syncAll syncAll操作},因为每次对volatile变量
 * 的写入都会与reader线程同步。
 * <p>
 *  在其他方面,{@code VolatileCallSite}可与{@code MutableCallSite}互换。
 * 
 * 
 * @see MutableCallSite
 * @author John Rose, JSR 292 EG
 */
public class VolatileCallSite extends CallSite {
    /**
     * Creates a call site with a volatile binding to its target.
     * The initial target is set to a method handle
     * of the given type which will throw an {@code IllegalStateException} if called.
     * <p>
     *  创建具有与其目标的不稳定绑定的调用站点。初始目标被设置为给定类型的方法句柄,如果被调用,它将抛出{@code IllegalStateException}。
     * 
     * 
     * @param type the method type that this call site will have
     * @throws NullPointerException if the proposed type is null
     */
    public VolatileCallSite(MethodType type) {
        super(type);
    }

    /**
     * Creates a call site with a volatile binding to its target.
     * The target is set to the given value.
     * <p>
     *  创建具有与其目标的不稳定绑定的调用站点。目标设置为给定值。
     * 
     * 
     * @param target the method handle that will be the initial target of the call site
     * @throws NullPointerException if the proposed target is null
     */
    public VolatileCallSite(MethodHandle target) {
        super(target);
    }

    /**
     * Returns the target method of the call site, which behaves
     * like a {@code volatile} field of the {@code VolatileCallSite}.
     * <p>
     * The interactions of {@code getTarget} with memory are the same
     * as of a read from a {@code volatile} field.
     * <p>
     * In particular, the current thread is required to issue a fresh
     * read of the target from memory, and must not fail to see
     * a recent update to the target by another thread.
     *
     * <p>
     *  返回调用网站的目标方法,其行为类似于{@code VolatileCallSite}的{@code volatile}字段。
     * <p>
     *  {@code getTarget}与内存的交互与从{@code volatile}字段读取的内容相同。
     * <p>
     *  特别地,当前线程需要从存储器发出目标的新读取,并且不能看不到另一线程对目标的最近更新。
     * 
     * 
     * @return the linkage state of this call site, a method handle which can change over time
     * @see #setTarget
     */
    @Override public final MethodHandle getTarget() {
        return getTargetVolatile();
    }

    /**
     * Updates the target method of this call site, as a volatile variable.
     * The type of the new target must agree with the type of the old target.
     * <p>
     * The interactions with memory are the same as of a write to a volatile field.
     * In particular, any threads is guaranteed to see the updated target
     * the next time it calls {@code getTarget}.
     * <p>
     * 将此调用网站的目标方法更新为volatile变量。新目标的类型必须与旧目标的类型一致。
     * <p>
     *  与存储器的交互与对易失性字段的写入相同。特别是,任何线程都保证在下次调用{@code getTarget}时看到更新的目标。
     * 
     * 
     * @param newTarget the new target
     * @throws NullPointerException if the proposed new target is null
     * @throws WrongMethodTypeException if the proposed new target
     *         has a method type that differs from the previous target
     * @see #getTarget
     */
    @Override public void setTarget(MethodHandle newTarget) {
        checkTargetChange(getTargetVolatile(), newTarget);
        setTargetVolatile(newTarget);
    }

    /**
     * {@inheritDoc}
     * <p>
     */
    @Override
    public final MethodHandle dynamicInvoker() {
        return makeDynamicInvoker();
    }
}
