/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.security;

/**
 * A {@code DomainCombiner} provides a means to dynamically
 * update the ProtectionDomains associated with the current
 * {@code AccessControlContext}.
 *
 * <p> A {@code DomainCombiner} is passed as a parameter to the
 * appropriate constructor for {@code AccessControlContext}.
 * The newly constructed context is then passed to the
 * {@code AccessController.doPrivileged(..., context)} method
 * to bind the provided context (and associated {@code DomainCombiner})
 * with the current execution Thread.  Subsequent calls to
 * {@code AccessController.getContext} or
 * {@code AccessController.checkPermission}
 * cause the {@code DomainCombiner.combine} to get invoked.
 *
 * <p> The combine method takes two arguments.  The first argument represents
 * an array of ProtectionDomains from the current execution Thread,
 * since the most recent call to {@code AccessController.doPrivileged}.
 * If no call to doPrivileged was made, then the first argument will contain
 * all the ProtectionDomains from the current execution Thread.
 * The second argument represents an array of inherited ProtectionDomains,
 * which may be {@code null}.  ProtectionDomains may be inherited
 * from a parent Thread, or from a privileged context.  If no call to
 * doPrivileged was made, then the second argument will contain the
 * ProtectionDomains inherited from the parent Thread.  If one or more calls
 * to doPrivileged were made, and the most recent call was to
 * doPrivileged(action, context), then the second argument will contain the
 * ProtectionDomains from the privileged context.  If the most recent call
 * was to doPrivileged(action), then there is no privileged context,
 * and the second argument will be {@code null}.
 *
 * <p> The {@code combine} method investigates the two input arrays
 * of ProtectionDomains and returns a single array containing the updated
 * ProtectionDomains.  In the simplest case, the {@code combine}
 * method merges the two stacks into one.  In more complex cases,
 * the {@code combine} method returns a modified
 * stack of ProtectionDomains.  The modification may have added new
 * ProtectionDomains, removed certain ProtectionDomains, or simply
 * updated existing ProtectionDomains.  Re-ordering and other optimizations
 * to the ProtectionDomains are also permitted.  Typically the
 * {@code combine} method bases its updates on the information
 * encapsulated in the {@code DomainCombiner}.
 *
 * <p> After the {@code AccessController.getContext} method
 * receives the combined stack of ProtectionDomains back from
 * the {@code DomainCombiner}, it returns a new
 * AccessControlContext that has both the combined ProtectionDomains
 * as well as the {@code DomainCombiner}.
 *
 * <p>
 *  {@code DomainCombiner}提供了一种动态更新与当前{@code AccessControlContext}相关联的ProtectionDomains的方法。
 * 
 *  <p> {@code DomainCombiner}作为参数传递给{@code AccessControlContext}的相应构造函数。
 * 然后将新构造的上下文传递给{@code AccessController.doPrivileged(...,context)}方法,以将提供的上下文(和关联的{@code DomainCombiner}
 * )与当前执行的Thread绑定。
 *  <p> {@code DomainCombiner}作为参数传递给{@code AccessControlContext}的相应构造函数。
 * 随后调用{@code AccessController.getContext}或{@code AccessController.checkPermission}会导致调用{@code DomainCombiner.combine}
 * 。
 *  <p> {@code DomainCombiner}作为参数传递给{@code AccessControlContext}的相应构造函数。
 * 
 * <p> combine方法有两个参数。
 * 第一个参数表示来自当前执行Thread的ProtectionDomains数组,因为最近一次调用{@code AccessController.doPrivileged}。
 * 如果没有调用doPrivileged,那么第一个参数将包含当前执行Thread的所有ProtectionDomains。
 * 第二个参数表示一个继承的ProtectionDomains数组,它可以是{@code null}。 ProtectionDomains可以从父线程或从特权上下文继承。
 * 如果没有调用doPrivileged,那么第二个参数将包含从父Thread继承的ProtectionDomains。
 * 如果对doPrivileged进行了一次或多次调用,并且最近的调用是doPrivileged(action,context),则第二个参数将包含来自特权上下文的ProtectionDomains。
 * 
 * @see AccessController
 * @see AccessControlContext
 * @since 1.3
 */
public interface DomainCombiner {

    /**
     * Modify or update the provided ProtectionDomains.
     * ProtectionDomains may be added to or removed from the given
     * ProtectionDomains.  The ProtectionDomains may be re-ordered.
     * Individual ProtectionDomains may be modified (with a new
     * set of Permissions, for example).
     *
     * <p>
     *
     * <p>
     * 如果最近的调用是doPrivileged(action),那么没有特权上下文,第二个参数将是{@code null}。
     * 
     * <p> {@code combine}方法调查ProtectionDomains的两个输入数组,并返回包含更新后的ProtectionDomains的单个数组。
     * 在最简单的情况下,{@code combine}方法将两个堆栈合并为一个。在更复杂的情况下,{@code combine}方法返回一个修改的ProtectionDomains堆栈。
     * 修改可能已添加了新的ProtectionDomains,删除了某些ProtectionDomains或仅更新了现有的ProtectionDomains。
     * 也允许对ProtectionDomain进行重新排序和其他优化。通常,{@code combine}方法的更新基于封装在{@code DomainCombiner}中的信息。
     * 
     *  <p>在{@code AccessController.getContext}方法从{@code DomainCombiner}接收到ProtectionDomains的组合堆栈后,它返回一个新的Ac
     * 
     * @param currentDomains the ProtectionDomains associated with the
     *          current execution Thread, up to the most recent
     *          privileged {@code ProtectionDomain}.
     *          The ProtectionDomains are are listed in order of execution,
     *          with the most recently executing {@code ProtectionDomain}
     *          residing at the beginning of the array. This parameter may
     *          be {@code null} if the current execution Thread
     *          has no associated ProtectionDomains.<p>
     *
     * @param assignedDomains an array of inherited ProtectionDomains.
     *          ProtectionDomains may be inherited from a parent Thread,
     *          or from a privileged {@code AccessControlContext}.
     *          This parameter may be {@code null}
     *          if there are no inherited ProtectionDomains.
     *
     * @return a new array consisting of the updated ProtectionDomains,
     *          or {@code null}.
     */
    ProtectionDomain[] combine(ProtectionDomain[] currentDomains,
                                ProtectionDomain[] assignedDomains);
}
