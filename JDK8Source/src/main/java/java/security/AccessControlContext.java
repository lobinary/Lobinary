/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.util.ArrayList;
import java.util.List;
import sun.security.util.Debug;
import sun.security.util.SecurityConstants;


/**
 * An AccessControlContext is used to make system resource access decisions
 * based on the context it encapsulates.
 *
 * <p>More specifically, it encapsulates a context and
 * has a single method, {@code checkPermission},
 * that is equivalent to the {@code checkPermission} method
 * in the AccessController class, with one difference: The AccessControlContext
 * {@code checkPermission} method makes access decisions based on the
 * context it encapsulates,
 * rather than that of the current execution thread.
 *
 * <p>Thus, the purpose of AccessControlContext is for those situations where
 * a security check that should be made within a given context
 * actually needs to be done from within a
 * <i>different</i> context (for example, from within a worker thread).
 *
 * <p> An AccessControlContext is created by calling the
 * {@code AccessController.getContext} method.
 * The {@code getContext} method takes a "snapshot"
 * of the current calling context, and places
 * it in an AccessControlContext object, which it returns. A sample call is
 * the following:
 *
 * <pre>
 *   AccessControlContext acc = AccessController.getContext()
 * </pre>
 *
 * <p>
 * Code within a different context can subsequently call the
 * {@code checkPermission} method on the
 * previously-saved AccessControlContext object. A sample call is the
 * following:
 *
 * <pre>
 *   acc.checkPermission(permission)
 * </pre>
 *
 * <p>
 *  AccessControlContext用于根据其封装的上下文进行系统资源访问决策。
 * 
 *  <p>更具体地说,它封装了一个上下文,并且有一个方法{@code checkPermission},它等同于AccessController类中的{@code checkPermission}方法,有
 * 一个区别：AccessControlContext {@code checkPermission}基于它封装的上下文而不是当前执行线程的上下文来作出访问决策。
 * 
 *  因此,AccessControlContext的目的是用于那些应当在给定上下文内进行的安全检查实际上需要在不同的上下文内完成的情况(例如,从工作者线)。
 * 
 *  <p> AccessControlContext是通过调用{@code AccessController.getContext}方法创建的。
 *  {@code getContext}方法获取当前调用上下文的"快照",并将其放在AccessControlContext对象中,它返回。示例调用如下：。
 * 
 * <pre>
 *  AccessControlContext acc = AccessController.getContext()
 * </pre>
 * 
 * <p>
 *  不同上下文中的代码随后可以调用先前保存的AccessControlContext对象上的{@code checkPermission}方法。示例调用如下：
 * 
 * <pre>
 *  acc.checkPermission(permission)
 * </pre>
 * 
 * 
 * @see AccessController
 *
 * @author Roland Schemers
 */

public final class AccessControlContext {

    private ProtectionDomain context[];
    // isPrivileged and isAuthorized are referenced by the VM - do not remove
    // or change their names
    private boolean isPrivileged;
    private boolean isAuthorized = false;

    // Note: This field is directly used by the virtual machine
    // native codes. Don't touch it.
    private AccessControlContext privilegedContext;

    private DomainCombiner combiner = null;

    // limited privilege scope
    private Permission permissions[];
    private AccessControlContext parent;
    private boolean isWrapped;

    // is constrained by limited privilege scope?
    private boolean isLimited;
    private ProtectionDomain limitedContext[];

    private static boolean debugInit = false;
    private static Debug debug = null;

    static Debug getDebug()
    {
        if (debugInit)
            return debug;
        else {
            if (Policy.isSet()) {
                debug = Debug.getInstance("access");
                debugInit = true;
            }
            return debug;
        }
    }

    /**
     * Create an AccessControlContext with the given array of ProtectionDomains.
     * Context must not be null. Duplicate domains will be removed from the
     * context.
     *
     * <p>
     * 使用给定的ProtectionDomains数组创建一个AccessControlContext。上下文不能为null。重复的域将从上下文中删除。
     * 
     * 
     * @param context the ProtectionDomains associated with this context.
     * The non-duplicate domains are copied from the array. Subsequent
     * changes to the array will not affect this AccessControlContext.
     * @throws NullPointerException if {@code context} is {@code null}
     */
    public AccessControlContext(ProtectionDomain context[])
    {
        if (context.length == 0) {
            this.context = null;
        } else if (context.length == 1) {
            if (context[0] != null) {
                this.context = context.clone();
            } else {
                this.context = null;
            }
        } else {
            List<ProtectionDomain> v = new ArrayList<>(context.length);
            for (int i =0; i< context.length; i++) {
                if ((context[i] != null) &&  (!v.contains(context[i])))
                    v.add(context[i]);
            }
            if (!v.isEmpty()) {
                this.context = new ProtectionDomain[v.size()];
                this.context = v.toArray(this.context);
            }
        }
    }

    /**
     * Create a new {@code AccessControlContext} with the given
     * {@code AccessControlContext} and {@code DomainCombiner}.
     * This constructor associates the provided
     * {@code DomainCombiner} with the provided
     * {@code AccessControlContext}.
     *
     * <p>
     *
     * <p>
     *  使用给定的{@code AccessControlContext}和{@code DomainCombiner}创建一个新的{@code AccessControlContext}。
     * 此构造函数将提供的{@code DomainCombiner}与提供的{@code AccessControlContext}相关联。
     * 
     * <p>
     * 
     * 
     * @param acc the {@code AccessControlContext} associated
     *          with the provided {@code DomainCombiner}.
     *
     * @param combiner the {@code DomainCombiner} to be associated
     *          with the provided {@code AccessControlContext}.
     *
     * @exception NullPointerException if the provided
     *          {@code context} is {@code null}.
     *
     * @exception SecurityException if a security manager is installed and the
     *          caller does not have the "createAccessControlContext"
     *          {@link SecurityPermission}
     * @since 1.3
     */
    public AccessControlContext(AccessControlContext acc,
                                DomainCombiner combiner) {

        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(SecurityConstants.CREATE_ACC_PERMISSION);
            this.isAuthorized = true;
        }

        this.context = acc.context;

        // we do not need to run the combine method on the
        // provided ACC.  it was already "combined" when the
        // context was originally retrieved.
        //
        // at this point in time, we simply throw away the old
        // combiner and use the newly provided one.
        this.combiner = combiner;
    }

    /**
     * package private for AccessController
     *
     * This "argument wrapper" context will be passed as the actual context
     * parameter on an internal doPrivileged() call used in the implementation.
     * <p>
     *  AccessController的包私有
     * 
     *  这个"参数包装器"上下文将作为实现中使用的内部doPrivileged()调用的实际上下文参数传递。
     * 
     */
    AccessControlContext(ProtectionDomain caller, DomainCombiner combiner,
        AccessControlContext parent, AccessControlContext context,
        Permission[] perms)
    {
        /*
         * Combine the domains from the doPrivileged() context into our
         * wrapper context, if necessary.
         * <p>
         *  如果需要,将来自doPrivileged()上下文的域合并到我们的包装器上下文中。
         * 
         */
        ProtectionDomain[] callerPDs = null;
        if (caller != null) {
             callerPDs = new ProtectionDomain[] { caller };
        }
        if (context != null) {
            if (combiner != null) {
                this.context = combiner.combine(callerPDs, context.context);
            } else {
                this.context = combine(callerPDs, context.context);
            }
        } else {
            /*
             * Call combiner even if there is seemingly nothing to combine.
             * <p>
             *  呼叫组合器即使看似没有什么要组合。
             * 
             */
            if (combiner != null) {
                this.context = combiner.combine(callerPDs, null);
            } else {
                this.context = combine(callerPDs, null);
            }
        }
        this.combiner = combiner;

        Permission[] tmp = null;
        if (perms != null) {
            tmp = new Permission[perms.length];
            for (int i=0; i < perms.length; i++) {
                if (perms[i] == null) {
                    throw new NullPointerException("permission can't be null");
                }

                /*
                 * An AllPermission argument is equivalent to calling
                 * doPrivileged() without any limit permissions.
                 * <p>
                 *  AllPermission参数相当于调用doPrivileged(),没有任何限制权限。
                 * 
                 */
                if (perms[i].getClass() == AllPermission.class) {
                    parent = null;
                }
                tmp[i] = perms[i];
            }
        }

        /*
         * For a doPrivileged() with limited privilege scope, initialize
         * the relevant fields.
         *
         * The limitedContext field contains the union of all domains which
         * are enclosed by this limited privilege scope. In other words,
         * it contains all of the domains which could potentially be checked
         * if none of the limiting permissions implied a requested permission.
         * <p>
         *  对于具有有限权限范围的doPrivileged(),初始化相关字段。
         * 
         *  restrictedContext字段包含由此有限权限范围包含的所有域的并集。换句话说,它包含所有可能被检查的域,如果没有一个限制权限暗示一个请求的权限。
         * 
         */
        if (parent != null) {
            this.limitedContext = combine(parent.context, parent.limitedContext);
            this.isLimited = true;
            this.isWrapped = true;
            this.permissions = tmp;
            this.parent = parent;
            this.privilegedContext = context; // used in checkPermission2()
        }
        this.isAuthorized = true;
    }


    /**
     * package private constructor for AccessController.getContext()
     * <p>
     *  包私有构造函数AccessController.getContext()
     * 
     */

    AccessControlContext(ProtectionDomain context[],
                         boolean isPrivileged)
    {
        this.context = context;
        this.isPrivileged = isPrivileged;
        this.isAuthorized = true;
    }

    /**
     * Constructor for JavaSecurityAccess.doIntersectionPrivilege()
     * <p>
     *  JavaSecurityAccess.doIntersectionPrivilege()的构造方法
     * 
     */
    AccessControlContext(ProtectionDomain[] context,
                         AccessControlContext privilegedContext)
    {
        this.context = context;
        this.privilegedContext = privilegedContext;
        this.isPrivileged = true;
    }

    /**
     * Returns this context's context.
     * <p>
     *  返回此上下文的上下文。
     * 
     */
    ProtectionDomain[] getContext() {
        return context;
    }

    /**
     * Returns true if this context is privileged.
     * <p>
     *  如果此上下文具有特权,则返回true。
     * 
     */
    boolean isPrivileged()
    {
        return isPrivileged;
    }

    /**
     * get the assigned combiner from the privileged or inherited context
     * <p>
     *  从特权或继承的上下文中获取分配的组合器
     * 
     */
    DomainCombiner getAssignedCombiner() {
        AccessControlContext acc;
        if (isPrivileged) {
            acc = privilegedContext;
        } else {
            acc = AccessController.getInheritedAccessControlContext();
        }
        if (acc != null) {
            return acc.combiner;
        }
        return null;
    }

    /**
     * Get the {@code DomainCombiner} associated with this
     * {@code AccessControlContext}.
     *
     * <p>
     *
     * <p>
     * 获取与此{@code AccessControlContext}相关联的{@code DomainCombiner}。
     * 
     * <p>
     * 
     * 
     * @return the {@code DomainCombiner} associated with this
     *          {@code AccessControlContext}, or {@code null}
     *          if there is none.
     *
     * @exception SecurityException if a security manager is installed and
     *          the caller does not have the "getDomainCombiner"
     *          {@link SecurityPermission}
     * @since 1.3
     */
    public DomainCombiner getDomainCombiner() {

        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(SecurityConstants.GET_COMBINER_PERMISSION);
        }
        return getCombiner();
    }

    /**
     * package private for AccessController
     * <p>
     *  AccessController的包私有
     * 
     */
    DomainCombiner getCombiner() {
        return combiner;
    }

    boolean isAuthorized() {
        return isAuthorized;
    }

    /**
     * Determines whether the access request indicated by the
     * specified permission should be allowed or denied, based on
     * the security policy currently in effect, and the context in
     * this object. The request is allowed only if every ProtectionDomain
     * in the context implies the permission. Otherwise the request is
     * denied.
     *
     * <p>
     * This method quietly returns if the access request
     * is permitted, or throws a suitable AccessControlException otherwise.
     *
     * <p>
     *  根据当前有效的安全策略和此对象中的上下文,确定是否应允许或拒绝由指定的权限指示的访问请求。仅当上下文中的每个ProtectionDomain都包含该权限时,才允许该请求。否则,请求被拒绝。
     * 
     * <p>
     *  如果允许访问请求,此方法静默返回,否则抛出合适的AccessControlException。
     * 
     * 
     * @param perm the requested permission.
     *
     * @exception AccessControlException if the specified permission
     * is not permitted, based on the current security policy and the
     * context encapsulated by this object.
     * @exception NullPointerException if the permission to check for is null.
     */
    public void checkPermission(Permission perm)
        throws AccessControlException
    {
        boolean dumpDebug = false;

        if (perm == null) {
            throw new NullPointerException("permission can't be null");
        }
        if (getDebug() != null) {
            // If "codebase" is not specified, we dump the info by default.
            dumpDebug = !Debug.isOn("codebase=");
            if (!dumpDebug) {
                // If "codebase" is specified, only dump if the specified code
                // value is in the stack.
                for (int i = 0; context != null && i < context.length; i++) {
                    if (context[i].getCodeSource() != null &&
                        context[i].getCodeSource().getLocation() != null &&
                        Debug.isOn("codebase=" + context[i].getCodeSource().getLocation().toString())) {
                        dumpDebug = true;
                        break;
                    }
                }
            }

            dumpDebug &= !Debug.isOn("permission=") ||
                Debug.isOn("permission=" + perm.getClass().getCanonicalName());

            if (dumpDebug && Debug.isOn("stack")) {
                Thread.dumpStack();
            }

            if (dumpDebug && Debug.isOn("domain")) {
                if (context == null) {
                    debug.println("domain (context is null)");
                } else {
                    for (int i=0; i< context.length; i++) {
                        debug.println("domain "+i+" "+context[i]);
                    }
                }
            }
        }

        /*
         * iterate through the ProtectionDomains in the context.
         * Stop at the first one that doesn't allow the
         * requested permission (throwing an exception).
         *
         * <p>
         *  在上下文中迭代ProtectionDomains。停止在第一个不允许所请求的权限(抛出异常)。
         * 
         */

        /* if ctxt is null, all we had on the stack were system domains,
           or the first domain was a Privileged system domain. This
        /* <p>
        /*  或第一个域是特权系统域。这个
        /* 
        /* 
           is to make the common case for system code very fast */

        if (context == null) {
            checkPermission2(perm);
            return;
        }

        for (int i=0; i< context.length; i++) {
            if (context[i] != null &&  !context[i].implies(perm)) {
                if (dumpDebug) {
                    debug.println("access denied " + perm);
                }

                if (Debug.isOn("failure") && debug != null) {
                    // Want to make sure this is always displayed for failure,
                    // but do not want to display again if already displayed
                    // above.
                    if (!dumpDebug) {
                        debug.println("access denied " + perm);
                    }
                    Thread.dumpStack();
                    final ProtectionDomain pd = context[i];
                    final Debug db = debug;
                    AccessController.doPrivileged (new PrivilegedAction<Void>() {
                        public Void run() {
                            db.println("domain that failed "+pd);
                            return null;
                        }
                    });
                }
                throw new AccessControlException("access denied "+perm, perm);
            }
        }

        // allow if all of them allowed access
        if (dumpDebug) {
            debug.println("access allowed "+perm);
        }

        checkPermission2(perm);
    }

    /*
     * Check the domains associated with the limited privilege scope.
     * <p>
     *  检查与受限权限范围关联的域。
     * 
     */
    private void checkPermission2(Permission perm) {
        if (!isLimited) {
            return;
        }

        /*
         * Check the doPrivileged() context parameter, if present.
         * <p>
         *  检查doPrivileged()上下文参数(如果存在)。
         * 
         */
        if (privilegedContext != null) {
            privilegedContext.checkPermission2(perm);
        }

        /*
         * Ignore the limited permissions and parent fields of a wrapper
         * context since they were already carried down into the unwrapped
         * context.
         * <p>
         *  忽略包装器上下文的有限权限和父字段,因为它们已被传递到解包的上下文中。
         * 
         */
        if (isWrapped) {
            return;
        }

        /*
         * Try to match any limited privilege scope.
         * <p>
         *  尝试匹配任何有限权限范围。
         * 
         */
        if (permissions != null) {
            Class<?> permClass = perm.getClass();
            for (int i=0; i < permissions.length; i++) {
                Permission limit = permissions[i];
                if (limit.getClass().equals(permClass) && limit.implies(perm)) {
                    return;
                }
            }
        }

        /*
         * Check the limited privilege scope up the call stack or the inherited
         * parent thread call stack of this ACC.
         * <p>
         *  检查调用堆栈或此ACC的继承父线程调用堆栈的有限权限范围。
         * 
         */
        if (parent != null) {
            /*
             * As an optimization, if the parent context is the inherited call
             * stack context from a parent thread then checking the protection
             * domains of the parent context is redundant since they have
             * already been merged into the child thread's context by
             * optimize(). When parent is set to an inherited context this
             * context was not directly created by a limited scope
             * doPrivileged() and it does not have its own limited permissions.
             * <p>
             * 作为优化,如果父上下文是从父线程继承的调用堆栈上下文,则检查父上下文的保护域是多余的,因为它们已经通过optimize()被合并到子线程的上下文中。
             * 当parent设置为继承上下文时,此上下文不是由有限范围的doPrivileged()直接创建的,并且它没有自己的有限权限。
             * 
             */
            if (permissions == null) {
                parent.checkPermission2(perm);
            } else {
                parent.checkPermission(perm);
            }
        }
    }

    /**
     * Take the stack-based context (this) and combine it with the
     * privileged or inherited context, if need be. Any limited
     * privilege scope is flagged regardless of whether the assigned
     * context comes from an immediately enclosing limited doPrivileged().
     * The limited privilege scope can indirectly flow from the inherited
     * parent thread or an assigned context previously captured by getContext().
     * <p>
     *  取基于堆栈的上下文(this),并将其与特权或继承的上下文,如果需要的话。无论所分配的上下文是否来自直接包含的有限的doPrivileged(),都会标记任何受限权限范围。
     * 有限权限范围可以从继承的父线程或先前由getContext()捕获的分配的上下文间接地流动。
     * 
     */
    AccessControlContext optimize() {
        // the assigned (privileged or inherited) context
        AccessControlContext acc;
        DomainCombiner combiner = null;
        AccessControlContext parent = null;
        Permission[] permissions = null;

        if (isPrivileged) {
            acc = privilegedContext;
            if (acc != null) {
                /*
                 * If the context is from a limited scope doPrivileged() then
                 * copy the permissions and parent fields out of the wrapper
                 * context that was created to hold them.
                 * <p>
                 *  如果上下文来自有限范围的doPrivileged(),则将权限和父字段复制到创建用于保存它们的包装器上下文中。
                 * 
                 */
                if (acc.isWrapped) {
                    permissions = acc.permissions;
                    parent = acc.parent;
                }
            }
        } else {
            acc = AccessController.getInheritedAccessControlContext();
            if (acc != null) {
                /*
                 * If the inherited context is constrained by a limited scope
                 * doPrivileged() then set it as our parent so we will process
                 * the non-domain-related state.
                 * <p>
                 *  如果继承的上下文受限于有限范围的doPrivileged(),那么将其设置为我们的父,所以我们将处理非域相关的状态。
                 * 
                 */
                if (acc.isLimited) {
                    parent = acc;
                }
            }
        }

        // this.context could be null if only system code is on the stack;
        // in that case, ignore the stack context
        boolean skipStack = (context == null);

        // acc.context could be null if only system code was involved;
        // in that case, ignore the assigned context
        boolean skipAssigned = (acc == null || acc.context == null);
        ProtectionDomain[] assigned = (skipAssigned) ? null : acc.context;
        ProtectionDomain[] pd;

        // if there is no enclosing limited privilege scope on the stack or
        // inherited from a parent thread
        boolean skipLimited = ((acc == null || !acc.isWrapped) && parent == null);

        if (acc != null && acc.combiner != null) {
            // let the assigned acc's combiner do its thing
            if (getDebug() != null) {
                debug.println("AccessControlContext invoking the Combiner");
            }

            // No need to clone current and assigned.context
            // combine() will not update them
            combiner = acc.combiner;
            pd = combiner.combine(context, assigned);
        } else {
            if (skipStack) {
                if (skipAssigned) {
                    calculateFields(acc, parent, permissions);
                    return this;
                } else if (skipLimited) {
                    return acc;
                }
            } else if (assigned != null) {
                if (skipLimited) {
                    // optimization: if there is a single stack domain and
                    // that domain is already in the assigned context; no
                    // need to combine
                    if (context.length == 1 && context[0] == assigned[0]) {
                        return acc;
                    }
                }
            }

            pd = combine(context, assigned);
            if (skipLimited && !skipAssigned && pd == assigned) {
                return acc;
            } else if (skipAssigned && pd == context) {
                calculateFields(acc, parent, permissions);
                return this;
            }
        }

        // Reuse existing ACC
        this.context = pd;
        this.combiner = combiner;
        this.isPrivileged = false;

        calculateFields(acc, parent, permissions);
        return this;
    }


    /*
     * Combine the current (stack) and assigned domains.
     * <p>
     *  组合当前(堆栈)和分配的域。
     * 
     */
    private static ProtectionDomain[] combine(ProtectionDomain[]current,
        ProtectionDomain[] assigned) {

        // current could be null if only system code is on the stack;
        // in that case, ignore the stack context
        boolean skipStack = (current == null);

        // assigned could be null if only system code was involved;
        // in that case, ignore the assigned context
        boolean skipAssigned = (assigned == null);

        int slen = (skipStack) ? 0 : current.length;

        // optimization: if there is no assigned context and the stack length
        // is less then or equal to two; there is no reason to compress the
        // stack context, it already is
        if (skipAssigned && slen <= 2) {
            return current;
        }

        int n = (skipAssigned) ? 0 : assigned.length;

        // now we combine both of them, and create a new context
        ProtectionDomain pd[] = new ProtectionDomain[slen + n];

        // first copy in the assigned context domains, no need to compress
        if (!skipAssigned) {
            System.arraycopy(assigned, 0, pd, 0, n);
        }

        // now add the stack context domains, discarding nulls and duplicates
    outer:
        for (int i = 0; i < slen; i++) {
            ProtectionDomain sd = current[i];
            if (sd != null) {
                for (int j = 0; j < n; j++) {
                    if (sd == pd[j]) {
                        continue outer;
                    }
                }
                pd[n++] = sd;
            }
        }

        // if length isn't equal, we need to shorten the array
        if (n != pd.length) {
            // optimization: if we didn't really combine anything
            if (!skipAssigned && n == assigned.length) {
                return assigned;
            } else if (skipAssigned && n == slen) {
                return current;
            }
            ProtectionDomain tmp[] = new ProtectionDomain[n];
            System.arraycopy(pd, 0, tmp, 0, n);
            pd = tmp;
        }

        return pd;
    }


    /*
     * Calculate the additional domains that could potentially be reached via
     * limited privilege scope. Mark the context as being subject to limited
     * privilege scope unless the reachable domains (if any) are already
     * contained in this domain context (in which case any limited
     * privilege scope checking would be redundant).
     * <p>
     *  计算可能通过有限权限范围访问的其他域。将上下文标记为受限特权范围,除非可达域(如果有)已经包含在此域上下文中(在这种情况下,任何有限特权范围检查都将是多余的)。
     * 
     */
    private void calculateFields(AccessControlContext assigned,
        AccessControlContext parent, Permission[] permissions)
    {
        ProtectionDomain[] parentLimit = null;
        ProtectionDomain[] assignedLimit = null;
        ProtectionDomain[] newLimit;

        parentLimit = (parent != null)? parent.limitedContext: null;
        assignedLimit = (assigned != null)? assigned.limitedContext: null;
        newLimit = combine(parentLimit, assignedLimit);
        if (newLimit != null) {
            if (context == null || !containsAllPDs(newLimit, context)) {
                this.limitedContext = newLimit;
                this.permissions = permissions;
                this.parent = parent;
                this.isLimited = true;
            }
        }
    }


    /**
     * Checks two AccessControlContext objects for equality.
     * Checks that <i>obj</i> is
     * an AccessControlContext and has the same set of ProtectionDomains
     * as this context.
     * <P>
     * <p>
     * 检查两个AccessControlContext对象是否相等。
     * 检查<i> obj </i>是否为AccessControlContext,并且具有与此上下文相同的ProtectionDomains集合。
     * <P>
     * 
     * @param obj the object we are testing for equality with this object.
     * @return true if <i>obj</i> is an AccessControlContext, and has the
     * same set of ProtectionDomains as this context, false otherwise.
     */
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (! (obj instanceof AccessControlContext))
            return false;

        AccessControlContext that = (AccessControlContext) obj;

        if (!equalContext(that))
            return false;

        if (!equalLimitedContext(that))
            return false;

        return true;
    }

    /*
     * Compare for equality based on state that is free of limited
     * privilege complications.
     * <p>
     *  基于没有有限特权并发症的状态比较平等。
     * 
     */
    private boolean equalContext(AccessControlContext that) {
        if (!equalPDs(this.context, that.context))
            return false;

        if (this.combiner == null && that.combiner != null)
            return false;

        if (this.combiner != null && !this.combiner.equals(that.combiner))
            return false;

        return true;
    }

    private boolean equalPDs(ProtectionDomain[] a, ProtectionDomain[] b) {
        if (a == null) {
            return (b == null);
        }

        if (b == null)
            return false;

        if (!(containsAllPDs(a, b) && containsAllPDs(b, a)))
            return false;

        return true;
    }

    /*
     * Compare for equality based on state that is captured during a
     * call to AccessController.getContext() when a limited privilege
     * scope is in effect.
     * <p>
     *  基于在受限权限范围生效时调用AccessController.getContext()期间捕获的状态进行比较。
     * 
     */
    private boolean equalLimitedContext(AccessControlContext that) {
        if (that == null)
            return false;

        /*
         * If neither instance has limited privilege scope then we're done.
         * <p>
         *  如果两个实例都没有有限的权限范围,我们就完成了。
         * 
         */
        if (!this.isLimited && !that.isLimited)
            return true;

        /*
         * If only one instance has limited privilege scope then we're done.
         * <p>
         *  如果只有一个实例具有有限的权限范围,那么我们就完成了。
         * 
         */
         if (!(this.isLimited && that.isLimited))
             return false;

        /*
         * Wrapped instances should never escape outside the implementation
         * this class and AccessController so this will probably never happen
         * but it only makes any sense to compare if they both have the same
         * isWrapped state.
         * <p>
         *  包装的实例应该永远不会逃避实现这个类和AccessController外部,所以这可能永远不会发生,但它只有任何意义,比较,如果他们都有相同的isWrapped状态。
         * 
         */
        if ((this.isWrapped && !that.isWrapped) ||
            (!this.isWrapped && that.isWrapped)) {
            return false;
        }

        if (this.permissions == null && that.permissions != null)
            return false;

        if (this.permissions != null && that.permissions == null)
            return false;

        if (!(this.containsAllLimits(that) && that.containsAllLimits(this)))
            return false;

        /*
         * Skip through any wrapped contexts.
         * <p>
         *  跳过任何包装的上下文。
         * 
         */
        AccessControlContext thisNextPC = getNextPC(this);
        AccessControlContext thatNextPC = getNextPC(that);

        /*
         * The protection domains and combiner of a privilegedContext are
         * not relevant because they have already been included in the context
         * of this instance by optimize() so we only care about any limited
         * privilege state they may have.
         * <p>
         *  保护域和privilegedContext的组合器是不相关的,因为它们已经通过optimize()被包括在这个实例的上下文中,所以我们只关心它们可能具有的任何有限的特权状态。
         * 
         */
        if (thisNextPC == null && thatNextPC != null && thatNextPC.isLimited)
            return false;

        if (thisNextPC != null && !thisNextPC.equalLimitedContext(thatNextPC))
            return false;

        if (this.parent == null && that.parent != null)
            return false;

        if (this.parent != null && !this.parent.equals(that.parent))
            return false;

        return true;
    }

    /*
     * Follow the privilegedContext link making our best effort to skip
     * through any wrapper contexts.
     * <p>
     *  遵循privilegedContext链接,尽最大努力跳过任何包装器上下文。
     * 
     */
    private static AccessControlContext getNextPC(AccessControlContext acc) {
        while (acc != null && acc.privilegedContext != null) {
            acc = acc.privilegedContext;
            if (!acc.isWrapped)
                return acc;
        }
        return null;
    }

    private static boolean containsAllPDs(ProtectionDomain[] thisContext,
        ProtectionDomain[] thatContext) {
        boolean match = false;

        //
        // ProtectionDomains within an ACC currently cannot be null
        // and this is enforced by the constructor and the various
        // optimize methods. However, historically this logic made attempts
        // to support the notion of a null PD and therefore this logic continues
        // to support that notion.
        ProtectionDomain thisPd;
        for (int i = 0; i < thisContext.length; i++) {
            match = false;
            if ((thisPd = thisContext[i]) == null) {
                for (int j = 0; (j < thatContext.length) && !match; j++) {
                    match = (thatContext[j] == null);
                }
            } else {
                Class<?> thisPdClass = thisPd.getClass();
                ProtectionDomain thatPd;
                for (int j = 0; (j < thatContext.length) && !match; j++) {
                    thatPd = thatContext[j];

                    // Class check required to avoid PD exposure (4285406)
                    match = (thatPd != null &&
                        thisPdClass == thatPd.getClass() && thisPd.equals(thatPd));
                }
            }
            if (!match) return false;
        }
        return match;
    }

    private boolean containsAllLimits(AccessControlContext that) {
        boolean match = false;
        Permission thisPerm;

        if (this.permissions == null && that.permissions == null)
            return true;

        for (int i = 0; i < this.permissions.length; i++) {
            Permission limit = this.permissions[i];
            Class <?> limitClass = limit.getClass();
            match = false;
            for (int j = 0; (j < that.permissions.length) && !match; j++) {
                Permission perm = that.permissions[j];
                match = (limitClass.equals(perm.getClass()) &&
                    limit.equals(perm));
            }
            if (!match) return false;
        }
        return match;
    }


    /**
     * Returns the hash code value for this context. The hash code
     * is computed by exclusive or-ing the hash code of all the protection
     * domains in the context together.
     *
     * <p>
     *  返回此上下文的哈希码值。通过将上下文中的所有保护域的哈希码一起排除或计算来计算哈希码。
     * 
     * @return a hash code value for this context.
     */

    public int hashCode() {
        int hashCode = 0;

        if (context == null)
            return hashCode;

        for (int i =0; i < context.length; i++) {
            if (context[i] != null)
                hashCode ^= context[i].hashCode();
        }

        return hashCode;
    }
}
