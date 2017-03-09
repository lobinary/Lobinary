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

/**
 * Defines the <em>standard</em> event kinds.
 *
 * <p>
 *  定义<em>标准</em>事件类型。
 * 
 * 
 * @since 1.7
 */

public final class StandardWatchEventKinds {
    private StandardWatchEventKinds() { }

    /**
     * A special event to indicate that events may have been lost or
     * discarded.
     *
     * <p> The {@link WatchEvent#context context} for this event is
     * implementation specific and may be {@code null}. The event {@link
     * WatchEvent#count count} may be greater than {@code 1}.
     *
     * <p>
     *  指示事件可能已丢失或丢弃的特殊事件。
     * 
     *  <p>此事件的{@link WatchEvent#context context}是实施特定的,可能是{@code null}。
     * 事件{@link WatchEvent#count count}可能大于{@code 1}。
     * 
     * 
     * @see WatchService
     */
    public static final WatchEvent.Kind<Object> OVERFLOW =
        new StdWatchEventKind<Object>("OVERFLOW", Object.class);

    /**
     * Directory entry created.
     *
     * <p> When a directory is registered for this event then the {@link WatchKey}
     * is queued when it is observed that an entry is created in the directory
     * or renamed into the directory. The event {@link WatchEvent#count count}
     * for this event is always {@code 1}.
     * <p>
     *  创建目录条目。
     * 
     *  <p>当为此事件注册目录时,当观察到在目录中创建了一个条目或将其重命名到目录时,{@link WatchKey}会排队。
     * 此活动的{@link WatchEvent#count count}事件始终为{@code 1}。
     * 
     */
    public static final WatchEvent.Kind<Path> ENTRY_CREATE =
        new StdWatchEventKind<Path>("ENTRY_CREATE", Path.class);

    /**
     * Directory entry deleted.
     *
     * <p> When a directory is registered for this event then the {@link WatchKey}
     * is queued when it is observed that an entry is deleted or renamed out of
     * the directory. The event {@link WatchEvent#count count} for this event
     * is always {@code 1}.
     * <p>
     *  目录条目已删除。
     * 
     *  <p>当为此事件注册目录时,当观察到某个条目从目录中删除或重命名时,{@link WatchKey}会排队。
     * 此活动的{@link WatchEvent#count count}事件始终为{@code 1}。
     * 
     */
    public static final WatchEvent.Kind<Path> ENTRY_DELETE =
        new StdWatchEventKind<Path>("ENTRY_DELETE", Path.class);

    /**
     * Directory entry modified.
     *
     * <p> When a directory is registered for this event then the {@link WatchKey}
     * is queued when it is observed that an entry in the directory has been
     * modified. The event {@link WatchEvent#count count} for this event is
     * {@code 1} or greater.
     * <p>
     */
    public static final WatchEvent.Kind<Path> ENTRY_MODIFY =
        new StdWatchEventKind<Path>("ENTRY_MODIFY", Path.class);

    private static class StdWatchEventKind<T> implements WatchEvent.Kind<T> {
        private final String name;
        private final Class<T> type;
        StdWatchEventKind(String name, Class<T> type) {
            this.name = name;
            this.type = type;
        }
        @Override public String name() { return name; }
        @Override public Class<T> type() { return type; }
        @Override public String toString() { return name; }
    }
}
