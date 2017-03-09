/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1994, 2012, Oracle and/or its affiliates. All rights reserved.
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

package java.util;

/**
 * This class represents an observable object, or "data"
 * in the model-view paradigm. It can be subclassed to represent an
 * object that the application wants to have observed.
 * <p>
 * An observable object can have one or more observers. An observer
 * may be any object that implements interface <tt>Observer</tt>. After an
 * observable instance changes, an application calling the
 * <code>Observable</code>'s <code>notifyObservers</code> method
 * causes all of its observers to be notified of the change by a call
 * to their <code>update</code> method.
 * <p>
 * The order in which notifications will be delivered is unspecified.
 * The default implementation provided in the Observable class will
 * notify Observers in the order in which they registered interest, but
 * subclasses may change this order, use no guaranteed order, deliver
 * notifications on separate threads, or may guarantee that their
 * subclass follows this order, as they choose.
 * <p>
 * Note that this notification mechanism has nothing to do with threads
 * and is completely separate from the <tt>wait</tt> and <tt>notify</tt>
 * mechanism of class <tt>Object</tt>.
 * <p>
 * When an observable object is newly created, its set of observers is
 * empty. Two observers are considered the same if and only if the
 * <tt>equals</tt> method returns true for them.
 *
 * <p>
 *  这个类表示一个可观察的对象,或者在模型视图范例中的"数据"。它可以被子类化以表示应用程序想要观察的对象。
 * <p>
 *  可观察对象可以有一个或多个观察者。观察者可以是实现接口<tt> Observer </tt>的任何对象。
 * 在可观察的实例改变之后,调用<code> Observable </code>的<code> notifyObservers </code>方法的应用程序通过调用其<code> update < / code>
 * 方法。
 *  可观察对象可以有一个或多个观察者。观察者可以是实现接口<tt> Observer </tt>的任何对象。
 * <p>
 *  未指定通知的发送顺序。
 *  Observable类中提供的默认实现将按照它们注册感兴趣的顺序通知Observers,但是子类可以更改此顺序,不使用有保证的顺序,在单独的线程上传递通知,或者可以保证其子类遵循此顺序,因为它们选择。
 *  未指定通知的发送顺序。
 * <p>
 *  请注意,此通知机制与线程无关,并且完全独立于类<tt>对象</tt>的<tt> wait </tt>和<tt>通知</tt>机制。
 * <p>
 *  当新创建一个observable对象时,它的一组观察者是空的。当且仅当<tt> equals </tt>方法为它们返回true时,两个观察者被认为是相同的。
 * 
 * 
 * @author  Chris Warth
 * @see     java.util.Observable#notifyObservers()
 * @see     java.util.Observable#notifyObservers(java.lang.Object)
 * @see     java.util.Observer
 * @see     java.util.Observer#update(java.util.Observable, java.lang.Object)
 * @since   JDK1.0
 */
public class Observable {
    private boolean changed = false;
    private Vector<Observer> obs;

    /** Construct an Observable with zero Observers. */

    public Observable() {
        obs = new Vector<>();
    }

    /**
     * Adds an observer to the set of observers for this object, provided
     * that it is not the same as some observer already in the set.
     * The order in which notifications will be delivered to multiple
     * observers is not specified. See the class comment.
     *
     * <p>
     * 向此对象的观察者集合添加一个观察者,前提是它与集合中的某个观察者不同。未指定将通知发送给多个观察者的顺序。参见班级评论。
     * 
     * 
     * @param   o   an observer to be added.
     * @throws NullPointerException   if the parameter o is null.
     */
    public synchronized void addObserver(Observer o) {
        if (o == null)
            throw new NullPointerException();
        if (!obs.contains(o)) {
            obs.addElement(o);
        }
    }

    /**
     * Deletes an observer from the set of observers of this object.
     * Passing <CODE>null</CODE> to this method will have no effect.
     * <p>
     *  从此对象的观察者集合中删除观察者。将<CODE> null </CODE>传递给此方法将不起作用。
     * 
     * 
     * @param   o   the observer to be deleted.
     */
    public synchronized void deleteObserver(Observer o) {
        obs.removeElement(o);
    }

    /**
     * If this object has changed, as indicated by the
     * <code>hasChanged</code> method, then notify all of its observers
     * and then call the <code>clearChanged</code> method to
     * indicate that this object has no longer changed.
     * <p>
     * Each observer has its <code>update</code> method called with two
     * arguments: this observable object and <code>null</code>. In other
     * words, this method is equivalent to:
     * <blockquote><tt>
     * notifyObservers(null)</tt></blockquote>
     *
     * <p>
     *  如果该对象已经改变,如<code> hasChanged </code>方法所示,然后通知所有的观察者,然后调用<code> clearChanged </code>方法来指示这个对象不再改变。
     * <p>
     *  每个观察者的<code> update </code>方法用两个参数调用：this observable对象和<code> null </code>。
     * 换句话说,此方法等效于：<blockquote> <tt> notifyObservers(null)</tt> </blockquote>。
     * 
     * 
     * @see     java.util.Observable#clearChanged()
     * @see     java.util.Observable#hasChanged()
     * @see     java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    public void notifyObservers() {
        notifyObservers(null);
    }

    /**
     * If this object has changed, as indicated by the
     * <code>hasChanged</code> method, then notify all of its observers
     * and then call the <code>clearChanged</code> method to indicate
     * that this object has no longer changed.
     * <p>
     * Each observer has its <code>update</code> method called with two
     * arguments: this observable object and the <code>arg</code> argument.
     *
     * <p>
     *  如果该对象已经改变,如<code> hasChanged </code>方法所示,然后通知所有的观察者,然后调用<code> clearChanged </code>方法来指示这个对象不再改变。
     * <p>
     *  每个观察者的<code> update </code>方法用两个参数调用：this observable对象和<code> arg </code>参数。
     * 
     * 
     * @param   arg   any object.
     * @see     java.util.Observable#clearChanged()
     * @see     java.util.Observable#hasChanged()
     * @see     java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    public void notifyObservers(Object arg) {
        /*
         * a temporary array buffer, used as a snapshot of the state of
         * current Observers.
         * <p>
         *  一个临时数组缓冲区,用作当前Observers状态的快照。
         * 
         */
        Object[] arrLocal;

        synchronized (this) {
            /* We don't want the Observer doing callbacks into
             * arbitrary code while holding its own Monitor.
             * The code where we extract each Observable from
             * the Vector and store the state of the Observer
             * needs synchronization, but notifying observers
             * does not (should not).  The worst result of any
             * potential race-condition here is that:
             * 1) a newly-added Observer will miss a
             *   notification in progress
             * 2) a recently unregistered Observer will be
             *   wrongly notified when it doesn't care
             * <p>
             * 任意代码同时持有自己的Monitor。我们从Vector中提取每个Observable并存储Observer的状态的代码需要同步,但是通知观察者不会(不应该)。
             * 这里任何潜在的竞争条件的最坏结果是：1)新添加的观察者将错过正在进行的通知2)最近未注册的观察者将被错误地通知,当它不在乎。
             * 
             */
            if (!changed)
                return;
            arrLocal = obs.toArray();
            clearChanged();
        }

        for (int i = arrLocal.length-1; i>=0; i--)
            ((Observer)arrLocal[i]).update(this, arg);
    }

    /**
     * Clears the observer list so that this object no longer has any observers.
     * <p>
     *  清除观察者列表,以使此对象不再有任何观察者。
     * 
     */
    public synchronized void deleteObservers() {
        obs.removeAllElements();
    }

    /**
     * Marks this <tt>Observable</tt> object as having been changed; the
     * <tt>hasChanged</tt> method will now return <tt>true</tt>.
     * <p>
     *  将此<tt>可观察</tt>对象标记为已更改; <tt> hasChanged </tt>方法现在将返回<tt> true </tt>。
     * 
     */
    protected synchronized void setChanged() {
        changed = true;
    }

    /**
     * Indicates that this object has no longer changed, or that it has
     * already notified all of its observers of its most recent change,
     * so that the <tt>hasChanged</tt> method will now return <tt>false</tt>.
     * This method is called automatically by the
     * <code>notifyObservers</code> methods.
     *
     * <p>
     *  表示此对象已不再更改,或已通知其所有观察者其最近的更改,以便<tt> hasChanged </tt>方法现在返回<tt> false </tt>。
     * 此方法由<code> notifyObservers </code>方法自动调用。
     * 
     * 
     * @see     java.util.Observable#notifyObservers()
     * @see     java.util.Observable#notifyObservers(java.lang.Object)
     */
    protected synchronized void clearChanged() {
        changed = false;
    }

    /**
     * Tests if this object has changed.
     *
     * <p>
     *  测试此对象是否已更改。
     * 
     * 
     * @return  <code>true</code> if and only if the <code>setChanged</code>
     *          method has been called more recently than the
     *          <code>clearChanged</code> method on this object;
     *          <code>false</code> otherwise.
     * @see     java.util.Observable#clearChanged()
     * @see     java.util.Observable#setChanged()
     */
    public synchronized boolean hasChanged() {
        return changed;
    }

    /**
     * Returns the number of observers of this <tt>Observable</tt> object.
     *
     * <p>
     *  返回此<tt> Observable </tt>对象的观察者数。
     * 
     * @return  the number of observers of this object.
     */
    public synchronized int countObservers() {
        return obs.size();
    }
}
