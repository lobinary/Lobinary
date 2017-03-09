/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * A watch service that <em>watches</em> registered objects for changes and
 * events. For example a file manager may use a watch service to monitor a
 * directory for changes so that it can update its display of the list of files
 * when files are created or deleted.
 *
 * <p> A {@link Watchable} object is registered with a watch service by invoking
 * its {@link Watchable#register register} method, returning a {@link WatchKey}
 * to represent the registration. When an event for an object is detected the
 * key is <em>signalled</em>, and if not currently signalled, it is queued to
 * the watch service so that it can be retrieved by consumers that invoke the
 * {@link #poll() poll} or {@link #take() take} methods to retrieve keys
 * and process events. Once the events have been processed the consumer
 * invokes the key's {@link WatchKey#reset reset} method to reset the key which
 * allows the key to be signalled and re-queued with further events.
 *
 * <p> Registration with a watch service is cancelled by invoking the key's
 * {@link WatchKey#cancel cancel} method. A key that is queued at the time that
 * it is cancelled remains in the queue until it is retrieved. Depending on the
 * object, a key may be cancelled automatically. For example, suppose a
 * directory is watched and the watch service detects that it has been deleted
 * or its file system is no longer accessible. When a key is cancelled in this
 * manner it is signalled and queued, if not currently signalled. To ensure
 * that the consumer is notified the return value from the {@code reset}
 * method indicates if the key is valid.
 *
 * <p> A watch service is safe for use by multiple concurrent consumers. To
 * ensure that only one consumer processes the events for a particular object at
 * any time then care should be taken to ensure that the key's {@code reset}
 * method is only invoked after its events have been processed. The {@link
 * #close close} method may be invoked at any time to close the service causing
 * any threads waiting to retrieve keys, to throw {@code
 * ClosedWatchServiceException}.
 *
 * <p> File systems may report events faster than they can be retrieved or
 * processed and an implementation may impose an unspecified limit on the number
 * of events that it may accumulate. Where an implementation <em>knowingly</em>
 * discards events then it arranges for the key's {@link WatchKey#pollEvents
 * pollEvents} method to return an element with an event type of {@link
 * StandardWatchEventKinds#OVERFLOW OVERFLOW}. This event can be used by the
 * consumer as a trigger to re-examine the state of the object.
 *
 * <p> When an event is reported to indicate that a file in a watched directory
 * has been modified then there is no guarantee that the program (or programs)
 * that have modified the file have completed. Care should be taken to coordinate
 * access with other programs that may be updating the file.
 * The {@link java.nio.channels.FileChannel FileChannel} class defines methods
 * to lock regions of a file against access by other programs.
 *
 * <h2>Platform dependencies</h2>
 *
 * <p> The implementation that observes events from the file system is intended
 * to map directly on to the native file event notification facility where
 * available, or to use a primitive mechanism, such as polling, when a native
 * facility is not available. Consequently, many of the details on how events
 * are detected, their timeliness, and whether their ordering is preserved are
 * highly implementation specific. For example, when a file in a watched
 * directory is modified then it may result in a single {@link
 * StandardWatchEventKinds#ENTRY_MODIFY ENTRY_MODIFY} event in some
 * implementations but several events in other implementations. Short-lived
 * files (meaning files that are deleted very quickly after they are created)
 * may not be detected by primitive implementations that periodically poll the
 * file system to detect changes.
 *
 * <p> If a watched file is not located on a local storage device then it is
 * implementation specific if changes to the file can be detected. In particular,
 * it is not required that changes to files carried out on remote systems be
 * detected.
 *
 * <p>
 *  针对更改和事件<em>监视</em>注册对象的监视服务。例如,文件管理器可以使用监视服务来监视目录以进行改变,使得当文件被创建或删除时,它可以更新其对文件列表的显示。
 * 
 *  <p>通过调用其{@link Watchable#register register}方法,返回一个{@link WatchKey}来表示注册,向注册服务注册{@link Watchable}对象。
 * 当检测到对象的事件时,用信号通知该键,如果没有当前发出信号,则将其排队到监视服务,使得它可以被消费者检索,该消费者调用{@link #poll )poll}或{@link #take()take}方法来
 * 检索键和处理事件。
 * 一旦事件已经被处理,消费者调用密钥的{@link WatchKey#reset reset}方法来重置密钥,该密钥允许通过信号发送密钥并且用其它事件重新排队。
 * 
 * <p>通过调用键的{@link WatchKey#cancel cancel}方法,可以取消通过手表服务进行注册。在取消它时排队的键将保留在队列中,直到它被检索。根据对象,可以自动取消键。
 * 例如,假设监视目录并且监视服务检测到它已被删除或其文件系统不再可访问。当以这种方式取消键时,如果没有当前发信号通知,则发信号通知和排队。
 * 为了确保通知消费者,{@code reset}方法的返回值表示密钥是否有效。
 * 
 *  <p>手表服务可安全地供多个并发消费者使用。为了确保只有一个消费者在任何时候处理特定对象的事件,那么应该注意确保键的{@code reset}方法仅在其事件被处理后才被调用。
 * 可以随时调用{@link #close close}方法关闭服务,导致任何等待检索键的线程抛出{@code ClosedWatchServiceException}。
 * 
 * <p>文件系统可以报告事件的速度比可以检索或处理的速度快,实施可能会对可能累积的事件数施加未指定的限制。
 * 如果实现<em>有意地丢弃事件,那么它会安排密钥的{@link WatchKey#pollEvents pollEvents}方法返回事件类型为{@link StandardWatchEventKinds#OVERFLOW OVERFLOW}
 * 的元素。
 * <p>文件系统可以报告事件的速度比可以检索或处理的速度快,实施可能会对可能累积的事件数施加未指定的限制。此事件可由消费者用作重新检查对象状态的触发器。
 * 
 *  <p>当报告一个事件以指示被监视目录中的文件已被修改时,不能保证已修改该文件的一个或多个程序已经完成。应注意协调与可能正在更新文件的其他程序的访问。
 *  {@link java.nio.channels.FileChannel FileChannel}类定义了用于锁定文件的区域以防其他程序访问的方法。
 * 
 *  <h2>平台相关性</h2>
 * 
 * <p>观察来自文件系统的事件的实现旨在直接映射到可用的本地文件事件通知工具,或者在本机工具不可用时使用原始机制,例如轮询。
 * 
 * @since 1.7
 *
 * @see FileSystem#newWatchService
 */

public interface WatchService
    extends Closeable
{

    /**
     * Closes this watch service.
     *
     * <p> If a thread is currently blocked in the {@link #take take} or {@link
     * #poll(long,TimeUnit) poll} methods waiting for a key to be queued then
     * it immediately receives a {@link ClosedWatchServiceException}. Any
     * valid keys associated with this watch service are {@link WatchKey#isValid
     * invalidated}.
     *
     * <p> After a watch service is closed, any further attempt to invoke
     * operations upon it will throw {@link ClosedWatchServiceException}.
     * If this watch service is already closed then invoking this method
     * has no effect.
     *
     * <p>
     * 因此,关于如何检测事件,它们的时间性以及它们的排序是否被保留的许多细节是高度实现特定的。
     * 例如,当监视目录中的文件被修改时,在一些实现中可能导致单个{@link StandardWatchEventKinds#ENTRY_MODIFY ENTRY_MODIFY}事件,而在其他实现中可能导致几
     * 个事件。
     * 因此,关于如何检测事件,它们的时间性以及它们的排序是否被保留的许多细节是高度实现特定的。短期文件(意味着文件在创建之后很快被删除)可能无法被周期性轮询文件系统以检测更改的原始实现检测到。
     * 
     *  <p>如果观看的文件不位于本地存储设备上,则它是实现特定的,如果可以检测到文件的更改。特别地,不需要检测对在远程系统上执行的文件的改变。
     * 
     * 
     * @throws  IOException
     *          if an I/O error occurs
     */
    @Override
    void close() throws IOException;

    /**
     * Retrieves and removes the next watch key, or {@code null} if none are
     * present.
     *
     * <p>
     *  关闭此手表服务。
     * 
     *  <p>如果某个线程当前在{@link #take take}或{@link #poll(long,TimeUnit)poll}方法中被阻塞,等待一个键排队,那么它立即会收到{@link ClosedWatchServiceException}
     * 。
     * 与此手表服务相关联的任何有效密钥都是{@link WatchKey#isValid invalidated}。
     * 
     * <p>关闭观察服务后,任何进一步尝试调用操作的操作都会抛出{@link ClosedWatchServiceException}。如果这个监视服务已经关闭,那么调用这个方法没有效果。
     * 
     * 
     * @return  the next watch key, or {@code null}
     *
     * @throws  ClosedWatchServiceException
     *          if this watch service is closed
     */
    WatchKey poll();

    /**
     * Retrieves and removes the next watch key, waiting if necessary up to the
     * specified wait time if none are yet present.
     *
     * <p>
     *  检索并删除下一个观看键,或{@code null}(如果没有)。
     * 
     * 
     * @param   timeout
     *          how to wait before giving up, in units of unit
     * @param   unit
     *          a {@code TimeUnit} determining how to interpret the timeout
     *          parameter
     *
     * @return  the next watch key, or {@code null}
     *
     * @throws  ClosedWatchServiceException
     *          if this watch service is closed, or it is closed while waiting
     *          for the next key
     * @throws  InterruptedException
     *          if interrupted while waiting
     */
    WatchKey poll(long timeout, TimeUnit unit)
        throws InterruptedException;

    /**
     * Retrieves and removes next watch key, waiting if none are yet present.
     *
     * <p>
     *  检索并删除下一个监视键,如果必要,等待指定的等待时间(如果还没有)。
     * 
     * 
     * @return  the next watch key
     *
     * @throws  ClosedWatchServiceException
     *          if this watch service is closed, or it is closed while waiting
     *          for the next key
     * @throws  InterruptedException
     *          if interrupted while waiting
     */
    WatchKey take() throws InterruptedException;
}
