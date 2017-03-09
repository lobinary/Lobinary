/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2011, Oracle and/or its affiliates. All rights reserved.
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

package java.nio.file;

import java.util.List;

/**
 * A token representing the registration of a {@link Watchable watchable} object
 * with a {@link WatchService}.
 *
 * <p> A watch key is created when a watchable object is registered with a watch
 * service. The key remains {@link #isValid valid} until:
 * <ol>
 *   <li> It is cancelled, explicitly, by invoking its {@link #cancel cancel}
 *     method, or</li>
 *   <li> Cancelled implicitly, because the object is no longer accessible,
 *     or </li>
 *   <li> By {@link WatchService#close closing} the watch service. </li>
 * </ol>
 *
 * <p> A watch key has a state. When initially created the key is said to be
 * <em>ready</em>. When an event is detected then the key is <em>signalled</em>
 * and queued so that it can be retrieved by invoking the watch service's {@link
 * WatchService#poll() poll} or {@link WatchService#take() take} methods. Once
 * signalled, a key remains in this state until its {@link #reset reset} method
 * is invoked to return the key to the ready state. Events detected while the
 * key is in the signalled state are queued but do not cause the key to be
 * re-queued for retrieval from the watch service. Events are retrieved by
 * invoking the key's {@link #pollEvents pollEvents} method. This method
 * retrieves and removes all events accumulated for the object. When initially
 * created, a watch key has no pending events. Typically events are retrieved
 * when the key is in the signalled state leading to the following idiom:
 *
 * <pre>
 *     for (;;) {
 *         // retrieve key
 *         WatchKey key = watcher.take();
 *
 *         // process events
 *         for (WatchEvent&lt;?&gt; event: key.pollEvents()) {
 *             :
 *         }
 *
 *         // reset the key
 *         boolean valid = key.reset();
 *         if (!valid) {
 *             // object no longer registered
 *         }
 *     }
 * </pre>
 *
 * <p> Watch keys are safe for use by multiple concurrent threads. Where there
 * are several threads retrieving signalled keys from a watch service then care
 * should be taken to ensure that the {@code reset} method is only invoked after
 * the events for the object have been processed. This ensures that one thread
 * is processing the events for an object at any time.
 *
 * <p>
 *  表示使用{@link WatchService}注册{@link Watchable}对象的令牌。
 * 
 *  <p>当可观看对象在观看服务中注册时,创建观看键。关键字保持{@link #isValid valid},直到：
 * <ol>
 *  <li>通过调用其{@link #cancel cancel}方法或</li> <li>显式取消,因为对象不再可访问,或</li> <li> @link WatchService#close关闭}手表
 * 服务。
 *  </li>。
 * </ol>
 * 
 * <p>手表键有状态。最初创建时,该键称为<em>准备好</em>。
 * 当检测到某个事件时,系统会发出<em> </em>信号并将其排队,以便通过调用watch服务的{@link WatchService#poll()poll}或{@link WatchService#take }
 *  方法。
 * <p>手表键有状态。最初创建时,该键称为<em>准备好</em>。一旦发出信号,键将保持此状态,直到调用{@link #reset reset}方法将键返回到就绪状态。
 * 当密钥处于信号状态时检测到的事件被排队,但不会导致密钥被重新排队以便从监视服务检索。通过调用键的{@link #pollEvents pollEvents}方法来检索事件。
 * 此方法检索并删除为对象累积的所有事件。初始创建时,手表键没有待处理事件。通常,当密钥处于导致以下习语的信令状态时检索事件：。
 * 
 * <pre>
 *  for(;;){// retrieve key WatchKey key = watcher.take();
 * 
 *  //处理事件(WatchEvent&lt;?&gt; event：key.pollEvents()){：}
 * 
 *  // reset the key boolean valid = key.reset(); if(！valid){//对象不再注册}}
 * </pre>
 * 
 *  <p>监视密钥可安全地用于多个并发线程。如果有几个线程从一个监视服务检索信号的密钥,那么应注意确保{@code reset}方法只在对象的事件被处理后被调用。这确保一个线程在任何时间处理对象的事件。
 * 
 * 
 * @since 1.7
 */

public interface WatchKey {

    /**
     * Tells whether or not this watch key is valid.
     *
     * <p> A watch key is valid upon creation and remains until it is cancelled,
     * or its watch service is closed.
     *
     * <p>
     * 指示此手表键是否有效。
     * 
     *  <p>手表钥匙在创建时有效,并保留,直到取消或关闭手表服务。
     * 
     * 
     * @return  {@code true} if, and only if, this watch key is valid
     */
    boolean isValid();

    /**
     * Retrieves and removes all pending events for this watch key, returning
     * a {@code List} of the events that were retrieved.
     *
     * <p> Note that this method does not wait if there are no events pending.
     *
     * <p>
     *  检索并删除此观察键的所有待处理事件,返回检索到的事件的{@code List}。
     * 
     *  <p>请注意,如果没有待处理的事件,此方法不会等待。
     * 
     * 
     * @return  the list of the events retrieved; may be empty
     */
    List<WatchEvent<?>> pollEvents();

    /**
     * Resets this watch key.
     *
     * <p> If this watch key has been cancelled or this watch key is already in
     * the ready state then invoking this method has no effect. Otherwise
     * if there are pending events for the object then this watch key is
     * immediately re-queued to the watch service. If there are no pending
     * events then the watch key is put into the ready state and will remain in
     * that state until an event is detected or the watch key is cancelled.
     *
     * <p>
     *  重置此手表键。
     * 
     *  <p>如果此手表键已取消或此手表键已处于就绪状态,则调用此方法无效。否则,如果有对象的挂起事件,那么这个监视键立即重新排队到监视服务。
     * 如果没有未决事件,则将手表键置于就绪状态,并保持在该状态,直到检测到事件或取消了手表键。
     * 
     * 
     * @return  {@code true} if the watch key is valid and has been reset, and
     *          {@code false} if the watch key could not be reset because it is
     *          no longer {@link #isValid valid}
     */
    boolean reset();

    /**
     * Cancels the registration with the watch service. Upon return the watch key
     * will be invalid. If the watch key is enqueued, waiting to be retrieved
     * from the watch service, then it will remain in the queue until it is
     * removed. Pending events, if any, remain pending and may be retrieved by
     * invoking the {@link #pollEvents pollEvents} method after the key is
     * cancelled.
     *
     * <p> If this watch key has already been cancelled then invoking this
     * method has no effect.  Once cancelled, a watch key remains forever invalid.
     * <p>
     *  取消向手表服务注册。返回时,手表键将无效。如果watch键入队,等待从watch服务检索,则它将保留在队列中,直到它被删除。
     * 挂起的事件(如果有)保持挂起,并且可以在取消键之后通过调用{@link #pollEvents pollEvents}方法检索。
     * 
     *  <p>如果此watch键已取消,则调用此方法不起作用。取消后,手表键永远无效。
     * 
     */
    void cancel();

    /**
     * Returns the object for which this watch key was created. This method will
     * continue to return the object even after the key is cancelled.
     *
     * <p> As the {@code WatchService} is intended to map directly on to the
     * native file event notification facility (where available) then many of
     * details on how registered objects are watched is highly implementation
     * specific. When watching a directory for changes for example, and the
     * directory is moved or renamed in the file system, there is no guarantee
     * that the watch key will be cancelled and so the object returned by this
     * method may no longer be a valid path to the directory.
     *
     * <p>
     *  返回此watch键的创建对象。即使在取消键之后,此方法仍将继续返回对象。
     * 
     * <p>由于{@code WatchService}旨在直接映射到本机文件事件通知工具(如果可用),因此有关注册对象如何被监视的许多细节是高度实施特定的。
     * 
     * @return the object for which this watch key was created
     */
    Watchable watchable();
}
