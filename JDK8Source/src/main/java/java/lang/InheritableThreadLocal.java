/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2012, Oracle and/or its affiliates. All rights reserved.
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

package java.lang;
import java.lang.ref.*;

/**
 * This class extends <tt>ThreadLocal</tt> to provide inheritance of values
 * from parent thread to child thread: when a child thread is created, the
 * child receives initial values for all inheritable thread-local variables
 * for which the parent has values.  Normally the child's values will be
 * identical to the parent's; however, the child's value can be made an
 * arbitrary function of the parent's by overriding the <tt>childValue</tt>
 * method in this class.
 *
 * <p>Inheritable thread-local variables are used in preference to
 * ordinary thread-local variables when the per-thread-attribute being
 * maintained in the variable (e.g., User ID, Transaction ID) must be
 * automatically transmitted to any child threads that are created.
 *
 * <p>
 *  这个类扩展了<tt> ThreadLocal </tt>以提供从父线程到子线程的值的继承：当子线程被创建时,子级接收父级具有值的所有可继承线程局部变量的初始值。
 * 通常孩子的值将与父母的值相同;然而,通过覆盖此类中的<tt> childValue </tt>方法,可以将子值的值设置为父值的任意函数。
 * 
 *  <p>当在变量(例如,用户ID,事务ID)中维护的每个线程属性必须被自动传输到创建的任何子线程时,可继承的线程局部变量优先于普通线程局部变量。
 * 
 * 
 * @author  Josh Bloch and Doug Lea
 * @see     ThreadLocal
 * @since   1.2
 */

public class InheritableThreadLocal<T> extends ThreadLocal<T> {
    /**
     * Computes the child's initial value for this inheritable thread-local
     * variable as a function of the parent's value at the time the child
     * thread is created.  This method is called from within the parent
     * thread before the child is started.
     * <p>
     * This method merely returns its input argument, and should be overridden
     * if a different behavior is desired.
     *
     * <p>
     *  在创建子线程时,计算子对象的可继承线程局部变量的初始值,作为父值的函数。这个方法在子线程启动之前从父线程中调用。
     * <p>
     *  此方法仅返回其输入参数,如果需要不同的行为,应该覆盖。
     * 
     * 
     * @param parentValue the parent thread's value
     * @return the child thread's initial value
     */
    protected T childValue(T parentValue) {
        return parentValue;
    }

    /**
     * Get the map associated with a ThreadLocal.
     *
     * <p>
     *  获取与ThreadLocal相关联的地图。
     * 
     * 
     * @param t the current thread
     */
    ThreadLocalMap getMap(Thread t) {
       return t.inheritableThreadLocals;
    }

    /**
     * Create the map associated with a ThreadLocal.
     *
     * <p>
     *  创建与ThreadLocal关联的地图。
     * 
     * @param t the current thread
     * @param firstValue value for the initial entry of the table.
     */
    void createMap(Thread t, T firstValue) {
        t.inheritableThreadLocals = new ThreadLocalMap(this, firstValue);
    }
}
