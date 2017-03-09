/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2003, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.ref;


/**
 * Soft reference objects, which are cleared at the discretion of the garbage
 * collector in response to memory demand.  Soft references are most often used
 * to implement memory-sensitive caches.
 *
 * <p> Suppose that the garbage collector determines at a certain point in time
 * that an object is <a href="package-summary.html#reachability">softly
 * reachable</a>.  At that time it may choose to clear atomically all soft
 * references to that object and all soft references to any other
 * softly-reachable objects from which that object is reachable through a chain
 * of strong references.  At the same time or at some later time it will
 * enqueue those newly-cleared soft references that are registered with
 * reference queues.
 *
 * <p> All soft references to softly-reachable objects are guaranteed to have
 * been cleared before the virtual machine throws an
 * <code>OutOfMemoryError</code>.  Otherwise no constraints are placed upon the
 * time at which a soft reference will be cleared or the order in which a set
 * of such references to different objects will be cleared.  Virtual machine
 * implementations are, however, encouraged to bias against clearing
 * recently-created or recently-used soft references.
 *
 * <p> Direct instances of this class may be used to implement simple caches;
 * this class or derived subclasses may also be used in larger data structures
 * to implement more sophisticated caches.  As long as the referent of a soft
 * reference is strongly reachable, that is, is actually in use, the soft
 * reference will not be cleared.  Thus a sophisticated cache can, for example,
 * prevent its most recently used entries from being discarded by keeping
 * strong referents to those entries, leaving the remaining entries to be
 * discarded at the discretion of the garbage collector.
 *
 * <p>
 *  软参考对象,根据内存需求由垃圾收集器自行决定清除。软引用最常用于实现对内存敏感的缓存。
 * 
 *  <p>假设垃圾回收器在某个时间点确定对象为<a href="package-summary.html#reachability">可轻松访问</a>。
 * 在那时,它可以选择原子地清除对该对象的所有软引用以及对通过强引用链可到达的任何其他可轻易访问的对象的所有软引用。在同时或在稍后的时间,它将入队那些被注册在参考队列中的新被清除的软引用。
 * 
 *  <p>保证在虚拟机抛出<code> OutOfMemoryError </code>之前清除所有对软可达对象的软引用。
 * 否则,对软参考将被清除的时间或者对一组对不同对象的这种引用将被清除的顺序没有施加约束。然而,鼓励虚拟机实现偏向于清除最近创建的或最近使用的软引用。
 * 
 * <p>此类的直接实例可用于实现简单缓存;这个类或派生的子类也可以在更大的数据结构中使用以实现更复杂的高速缓存。只要软参考的指示强烈可达,即实际上正在使用,则软参考不会被清除。
 * 因此,复杂的高速缓存可以例如通过保持对这些条目的强指示来防止其最近使用的条目被丢弃,留下剩余条目在垃圾收集器的判断下被丢弃。
 * 
 * 
 * @author   Mark Reinhold
 * @since    1.2
 */

public class SoftReference<T> extends Reference<T> {

    /**
     * Timestamp clock, updated by the garbage collector
     * <p>
     *  时间戳时钟,由垃圾收集器更新
     * 
     */
    static private long clock;

    /**
     * Timestamp updated by each invocation of the get method.  The VM may use
     * this field when selecting soft references to be cleared, but it is not
     * required to do so.
     * <p>
     *  通过每次调用get方法更新时间戳。当选择要清除的软引用时,VM可以使用此字段,但不需要这样做。
     * 
     */
    private long timestamp;

    /**
     * Creates a new soft reference that refers to the given object.  The new
     * reference is not registered with any queue.
     *
     * <p>
     *  创建引用给定对象的新软引用。新引用未向任何队列注册。
     * 
     * 
     * @param referent object the new soft reference will refer to
     */
    public SoftReference(T referent) {
        super(referent);
        this.timestamp = clock;
    }

    /**
     * Creates a new soft reference that refers to the given object and is
     * registered with the given queue.
     *
     * <p>
     *  创建一个新的软引用,引用给定的对象并且使用给定的队列注册。
     * 
     * 
     * @param referent object the new soft reference will refer to
     * @param q the queue with which the reference is to be registered,
     *          or <tt>null</tt> if registration is not required
     *
     */
    public SoftReference(T referent, ReferenceQueue<? super T> q) {
        super(referent, q);
        this.timestamp = clock;
    }

    /**
     * Returns this reference object's referent.  If this reference object has
     * been cleared, either by the program or by the garbage collector, then
     * this method returns <code>null</code>.
     *
     * <p>
     *  返回此引用对象的引用。如果此引用对象已被程序或垃圾收集器清除,则此方法返回<code> null </code>。
     * 
     * @return   The object to which this reference refers, or
     *           <code>null</code> if this reference object has been cleared
     */
    public T get() {
        T o = super.get();
        if (o != null && this.timestamp != clock)
            this.timestamp = clock;
        return o;
    }

}
