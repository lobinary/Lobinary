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

/**
 * An event or a repeated event for an object that is registered with a {@link
 * WatchService}.
 *
 * <p> An event is classified by its {@link #kind() kind} and has a {@link
 * #count() count} to indicate the number of times that the event has been
 * observed. This allows for efficient representation of repeated events. The
 * {@link #context() context} method returns any context associated with
 * the event. In the case of a repeated event then the context is the same for
 * all events.
 *
 * <p> Watch events are immutable and safe for use by multiple concurrent
 * threads.
 *
 * <p>
 *  已注册到{@link WatchService}的对象的事件或重复事件。
 * 
 *  <p>事件按其{@link #kind()kind}分类,并有一个{@link #count()count},用于指示事件被观察的次数。这允许重复事件的有效表示。
 *  {@link #context()context}方法返回与事件相关联的任何上下文。在重复事件的情况下,上下文对于所有事件是相同的。
 * 
 *  <p>观看事件是不可变的,并且安全地用于多个并发线程。
 * 
 * 
 * @param   <T>     The type of the context object associated with the event
 *
 * @since 1.7
 */

public interface WatchEvent<T> {

    /**
     * An event kind, for the purposes of identification.
     *
     * <p>
     *  事件种类,用于识别的目的。
     * 
     * 
     * @since 1.7
     * @see StandardWatchEventKinds
     */
    public static interface Kind<T> {
        /**
         * Returns the name of the event kind.
         *
         * <p>
         *  返回事件类型的名称。
         * 
         * 
         * @return the name of the event kind
         */
        String name();

        /**
         * Returns the type of the {@link WatchEvent#context context} value.
         *
         *
         * <p>
         *  返回{@link WatchEvent#context context}值的类型。
         * 
         * 
         * @return the type of the context value
         */
        Class<T> type();
    }

    /**
     * An event modifier that qualifies how a {@link Watchable} is registered
     * with a {@link WatchService}.
     *
     * <p> This release does not define any <em>standard</em> modifiers.
     *
     * <p>
     *  用于限定{@link Watchable}如何向{@link WatchService}注册的事件修改器。
     * 
     *  <p>此版本没有定义任何<em>标准</em>修饰符。
     * 
     * 
     * @since 1.7
     * @see Watchable#register
     */
    public static interface Modifier {
        /**
         * Returns the name of the modifier.
         *
         * <p>
         *  返回修饰符的名称。
         * 
         * 
         * @return the name of the modifier
         */
        String name();
    }

    /**
     * Returns the event kind.
     *
     * <p>
     *  返回事件类型。
     * 
     * 
     * @return  the event kind
     */
    Kind<T> kind();

    /**
     * Returns the event count. If the event count is greater than {@code 1}
     * then this is a repeated event.
     *
     * <p>
     *  返回事件计数。如果事件计数大于{@code 1},那么这是一个重复的事件。
     * 
     * 
     * @return  the event count
     */
    int count();

    /**
     * Returns the context for the event.
     *
     * <p> In the case of {@link StandardWatchEventKinds#ENTRY_CREATE ENTRY_CREATE},
     * {@link StandardWatchEventKinds#ENTRY_DELETE ENTRY_DELETE}, and {@link
     * StandardWatchEventKinds#ENTRY_MODIFY ENTRY_MODIFY} events the context is
     * a {@code Path} that is the {@link Path#relativize relative} path between
     * the directory registered with the watch service, and the entry that is
     * created, deleted, or modified.
     *
     * <p>
     *  返回事件的上下文。
     * 
     * <p>在{@link StandardWatchEventKinds#ENTRY_CREATE ENTRY_CREATE},{@link StandardWatchEventKinds#ENTRY_DELETE ENTRY_DELETE}
     * 
     * @return  the event context; may be {@code null}
     */
    T context();
}
