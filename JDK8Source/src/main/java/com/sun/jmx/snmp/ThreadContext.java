/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2007, Oracle and/or its affiliates. All rights reserved.
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


package com.sun.jmx.snmp;

import java.util.Stack;
import java.util.EmptyStackException;

/**
 * <p><b>Warning: The interface of this class is subject to change.
 * Use at your own risk.</b></p>
 *
 * <p>This class associates a context with each thread that
 * references it.  The context is a set of mappings between Strings
 * and Objects.  It is managed as a stack, typically with code like
 * this:</p>
 *
 * <pre>
 * ThreadContext oldContext = ThreadContext.push(myKey, myObject);
 * // plus possibly further calls to ThreadContext.push...
 * try {
 *      doSomeOperation();
 * } finally {
 *      ThreadContext.restore(oldContext);
 * }
 * </pre>
 *
 * <p>The <code>try</code>...<code>finally</code> block ensures that
 * the <code>restore</code> is done even if
 * <code>doSomeOperation</code> terminates abnormally (with an
 * exception).</p>
 *
 * <p>A thread can consult its own context using
 * <code>ThreadContext.get(myKey)</code>.  The result is the
 * value that was most recently pushed with the given key.</p>
 *
 * <p>A thread cannot read or modify the context of another thread.</p>
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  <p> <b>警告：此类的界面可能会更改。使用风险自负。</b> </p>
 * 
 *  <p>此类将上下文与引用它的每个线程相关联。上下文是字符串和对象之间的一组映射。它作为堆栈管理,通常使用如下代码：</p>
 * 
 * <pre>
 *  ThreadContext oldContext = ThreadContext.push(myKey,myObject); //加上可能的进一步调用ThreadContext.push ... tr
 * y {doSomeOperation(); } finally {ThreadContext.restore(oldContext); }}。
 * </pre>
 * 
 *  <p> <code> try </code> ... <code> finally </code>可确保即使<code> doSomeOperation </code>异常终止,<code> rest
 * ore </code>但有一个例外)。
 * </p>。
 * 
 *  <p>线程可以使用<code> ThreadContext.get(myKey)</code>来查询自己的上下文。结果是最近使用给定键推送的值。</p>
 * 
 *  <p>线程无法读取或修改另一个线程的上下文。</p>
 * 
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 */
public class ThreadContext implements Cloneable {

    /* The context of a thread is stored as a linked list.  At the
       head of the list is the value returned by localContext.get().
       At the tail of the list is a sentinel ThreadContext value with
       "previous" and "key" both null.  There is a different sentinel
       object for each thread.

       Because a null key indicates the sentinel, we reject attempts to
       push context entries with a null key.

       The reason for using a sentinel rather than just terminating
       the list with a null reference is to protect against incorrect
       or even malicious code.  If you have a reference to the
       sentinel value, you can erase the context stack.  Only the
       caller of the first "push" that put something on the stack can
       get such a reference, so if that caller does not give this
       reference away, no one else can erase the stack.

       If the restore method took a null reference to mean an empty
       stack, anyone could erase the stack, since anyone can make a
       null reference.

       When the stack is empty, we discard the sentinel object and
       have localContext.get() return null.  Then we recreate the
       sentinel object on the first subsequent push.

       ThreadContext objects are immutable.  As a consequence, you can
       give a ThreadContext object to setInitialContext that is no
       longer current.  But the interface says this can be rejected,
    /* <p>
    /*  头的头是由localContext.get()返回的值。在列表的尾部是一个哨兵ThreadContext值,"previous"和"key"都为null。每个线程有一个不同的sentinel对象。
    /* 
    /*  因为空键表示前哨,所以我们拒绝用空键推送上下文条目的尝试。
    /* 
    /* 使用sentinel而不是仅仅使用null引用来终止列表的原因是为了防止不正确的甚至是恶意代码。如果您有对sentinel值的引用,则可以删除上下文堆栈。
    /* 只有第一个"push"的调用者把一些东西放在栈上可以得到这样的引用,所以如果调用者不给这个引用,没有人可以擦除堆栈。
    /* 
    /*  如果恢复方法使用空引用意味着一个空堆栈,任何人都可以擦除堆栈,因为任何人都可以做一个空引用。
    /* 
    /*  当堆栈为空时,我们丢弃sentinel对象,并使localContext.get()返回null。然后我们在第一次后续push时重新创建sentinel对象。
    /* 
    /*  ThreadContext对象是不可变的。因此,您可以将一个ThreadContext对象赋给不再为当前的setInitialContext。但接口说这可以拒绝,
    /* 
    /* 
       in case we remove immutability later.  */

    /* We have to comment out "final" here because of a bug in the JDK1.1
    /* <p>
    /* 
       compiler.  Uncomment it when we discard 1.1 compatibility.  */
    private /*final*/ ThreadContext previous;
    private /* <p>
    private /* 
    private /*final*/ String key;
    private /* <p>
    private /* 
    private /*final*/ Object value;

    private ThreadContext(ThreadContext previous, String key, Object value) {
        this.previous = previous;
        this.key = key;
        this.value = value;
    }

    /**
     * <p>Get the Object that was most recently pushed with the given key.</p>
     *
     * <p>
     *  private ThreadContext(ThreadContext previous,String key,Object value){this.previous = previous; this.key = key; this.value = value; }
     * }。
     * 
     *  / ** <p>获取最近使用给定键推送的对象。</p>
     * 
     * 
     * @param key the key of interest.
     *
     * @return the last Object that was pushed (using
     * <code>push</code>) with that key and not subsequently canceled
     * by a <code>restore</code>; or null if there is no such object.
     * A null return value may also indicate that the last Object
     * pushed was the value <code>null</code>.  Use the
     * <code>contains</code> method to distinguish this case from the
     * case where there is no Object.
     *
     * @exception IllegalArgumentException if <code>key</code> is null.
     */
    public static Object get(String key) throws IllegalArgumentException {
        ThreadContext context = contextContaining(key);
        if (context == null)
            return null;
        else
            return context.value;
    }

    /**
     * <p>Check whether a value with the given key exists in the stack.
     * This means that the <code>push</code> method was called with
     * this key and it was not cancelled by a subsequent
     * <code>restore</code>.  This method is useful when the
     * <code>get</code> method returns null, to distinguish between
     * the case where the key exists in the stack but is associated
     * with a null value, and the case where the key does not exist in
     * the stack.</p>
     *
     * <p>
     * <p>检查堆栈中是否存在具有给定键的值。这意味着使用此键调用了<code> push </code>方法,并且它不会被后续的<code> restore </code>取消。
     * 当<code> get </code>方法返回null时,此方法很有用,以区分键存在于堆栈中但与空值相关联的情况以及键不存在于堆栈中的情况。</p>。
     * 
     * 
     * @return true if the key exists in the stack.
     *
     * @exception IllegalArgumentException if <code>key</code> is null.
     */
    public static boolean contains(String key)
            throws IllegalArgumentException {
        return (contextContaining(key) != null);
    }

    /**
     * <p>Find the ThreadContext in the stack that contains the given key,
     * or return null if there is none.</p>
     *
     * <p>
     *  <p>在堆栈中找到包含给定键的ThreadContext,如果没有则返回null。</p>
     * 
     * 
     * @exception IllegalArgumentException if <code>key</code> is null.
     */
    private static ThreadContext contextContaining(String key)
            throws IllegalArgumentException {
        if (key == null)
            throw new IllegalArgumentException("null key");
        for (ThreadContext context = getContext();
             context != null;
             context = context.previous) {
            if (key.equals(context.key))
                return context;
            /* Note that "context.key" may be null if "context" is the
            /* <p>
            /* 
               sentinel, so don't write "if (context.key.equals(key))"!  */
        }
        return null;
    }

//  /**
//   * Change the value that was most recently associated with the given key
//   * in a <code>push</code> operation not cancelled by a subsequent
//   * <code>restore</code>.  If there is no such association, nothing happens
//   * and the return value is null.
//   *
//   * <p>
//   *  // *在<code> push </code>操作中更改最近与给定键// *关联的值,而不会被后续的// * <code> restore </code>取消。
//   * 如果没有这样的关联,没有发生// *,返回值为null。 // *。
//   * 
//   * 
//   * @param key the key of interest.
//   * @param value the new value to associate with that key.
//   *
//   * @return the value that was previously associated with the key, or null
//   * if the key does not exist in the stack.
//   *
//   * @exception IllegalArgumentException if <code>key</code> is null.
//   */
//  public static Object set(String key, Object value)
//          throws IllegalArgumentException {
//      ThreadContext context = contextContaining(key);
//      if (context == null)
//          return null;
//      Object old = context.value;
//      context.value = value;
//      return old;
//  }

    /**
     * <p>Push an object on the context stack with the given key.
     * This operation can subsequently be undone by calling
     * <code>restore</code> with the ThreadContext value returned
     * here.</p>
     *
     * <p>
     *  <p>使用给定的键在上下文堆栈上推送一个对象。此操作随后可以通过调用<code> restore </code>与此处返回的ThreadContext值撤消。</p>
     * 
     * 
     * @param key the key that will be used to find the object while it is
     * on the stack.
     * @param value the value to be associated with that key.  It may be null.
     *
     * @return a ThreadContext that can be given to <code>restore</code> to
     * restore the stack to its state before the <code>push</code>.
     *
     * @exception IllegalArgumentException if <code>key</code> is null.
     */
    public static ThreadContext push(String key, Object value)
            throws IllegalArgumentException {
        if (key == null)
            throw new IllegalArgumentException("null key");

        ThreadContext oldContext = getContext();
        if (oldContext == null)
            oldContext = new ThreadContext(null, null, null);  // make sentinel
        ThreadContext newContext = new ThreadContext(oldContext, key, value);
        setContext(newContext);
        return oldContext;
    }

    /**
     * <p>Return an object that can later be supplied to <code>restore</code>
     * to restore the context stack to its current state.  The object can
     * also be given to <code>setInitialContext</code>.</p>
     *
     * <p>
     *  <p>返回一个稍后可以提供给<code> restore </code>的对象,以将上下文堆栈恢复到其当前状态。该对象也可以赋给<code> setInitialContext </code>。
     * </p>。
     * 
     * 
     * @return a ThreadContext that represents the current context stack.
     */
    public static ThreadContext getThreadContext() {
        return getContext();
    }

    /**
     * <p>Restore the context stack to an earlier state.  This typically
     * undoes the effect of one or more <code>push</code> calls.</p>
     *
     * <p>
     *  <p>将上下文堆栈恢复到更早的状态。这通常会撤消一个或多个<code> push </code>调用的效果。</p>
     * 
     * 
     * @param oldContext the state to return.  This is usually the return
     * value of an earlier <code>push</code> operation.
     *
     * @exception NullPointerException if <code>oldContext</code> is null.
     * @exception IllegalArgumentException if <code>oldContext</code>
     * does not represent a context from this thread, or if that
     * context was undone by an earlier <code>restore</code>.
     */
    public static void restore(ThreadContext oldContext)
            throws NullPointerException, IllegalArgumentException {
        /* The following test is not strictly necessary in the code as it
           stands today, since the reference to "oldContext.key" would
           generate a NullPointerException anyway.  But if someone
           didn't notice that during subsequent changes, they could
           accidentally permit restore(null) with the semantics of
        /* <p>
        /* 因为对"oldContext.key"的引用将会生成一个NullPointerException。但是如果有人没有注意到在随后的更改,他们可能会意外允许恢复(null)的语义
        /* 
        /* 
           trashing the context stack.  */
        if (oldContext == null)
            throw new NullPointerException();

        /* Check that the restored context is in the stack.  */
        for (ThreadContext context = getContext();
             context != oldContext;
             context = context.previous) {
            if (context == null) {
                throw new IllegalArgumentException("Restored context is not " +
                                                   "contained in current " +
                                                   "context");
            }
        }

        /* Discard the sentinel if the stack is empty.  This means that it
           is an error to call "restore" a second time with the
           ThreadContext value that means an empty stack.  That's why we
           don't say that it is all right to restore the stack to the
        /* <p>
        /*  是一个错误,用ThreadContext值第二次调用"还原",这意味着一个空堆栈。这就是为什么我们不说这是完全正确的恢复堆栈
        /* 
        /* 
           state it was already in.  */
        if (oldContext.key == null)
            oldContext = null;

        setContext(oldContext);
    }

    /**
     * <p>Set the initial context of the calling thread to a context obtained
     * from another thread.  After this call, the calling thread will see
     * the same results from the <code>get</code> method as the thread
     * from which the <code>context</code> argument was obtained, at the
     * time it was obtained.</p>
     *
     * <p>The <code>context</code> argument must be the result of an earlier
     * <code>push</code> or <code>getThreadContext</code> call.  It is an
     * error (which may or may not be detected) if this context has been
     * undone by a <code>restore</code>.</p>
     *
     * <p>The context stack of the calling thread must be empty before this
     * call, i.e., there must not have been a <code>push</code> not undone
     * by a subsequent <code>restore</code>.</p>
     *
     * <p>
     *  <p>将调用线程的初始上下文设置为从另一个线程获取的上下文。
     * 在该调用之后,调用线程将从<code> get </code>方法看到与获得<code> context </code>参数时获得的<code> get </code>方法相同的结果。 p>。
     * 
     *  <p> <code> context </code>参数必须是早期<code> push </code>或<code> getThreadContext </code>调用的结果。
     * 如果此上下文已被<code> restore </code>撤消,则这是一个错误(可能检测到也可能未检测到)。</p>。
     * 
     * 
     * @exception IllegalArgumentException if the context stack was
     * not empty before the call.  An implementation may also throw this
     * exception if <code>context</code> is no longer current in the
     * thread from which it was obtained.
     */
    /* We rely on the fact that ThreadContext objects are immutable.
       This means that we don't have to check that the "context"
       argument is valid.  It necessarily represents the head of a
       valid chain of ThreadContext objects, even if the thread from
       which it was obtained has subsequently been set to a point
    /* <p>
    /*  <p>在调用之前,调用线程的上下文堆栈必须为空,也就是说,一个<code> push </code>不能被后续的<code> restore </p> >
    /* 
    /* 
       later in that chain using "restore".  */
    public void setInitialContext(ThreadContext context)
            throws IllegalArgumentException {
        /* The following test assumes that we discard sentinels when the
        /* <p>
        /*  这意味着我们不必检查"上下文"参数是否有效。它必然代表一个有效的ThreadContext对象链的头,即使从它获得它的线程随后被设置为一个点
        /* 
        /* 
           stack is empty.  */
        if (getContext() != null)
            throw new IllegalArgumentException("previous context not empty");
        setContext(context);
    }

    private static ThreadContext getContext() {
        return localContext.get();
    }

    private static void setContext(ThreadContext context) {
        localContext.set(context);
    }

    private static ThreadLocal<ThreadContext> localContext =
            new ThreadLocal<ThreadContext>();
}
