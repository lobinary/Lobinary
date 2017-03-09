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

import java.nio.file.attribute.BasicFileAttributes;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import sun.nio.fs.BasicFileAttributesHolder;

/**
 * Walks a file tree, generating a sequence of events corresponding to the files
 * in the tree.
 *
 * <pre>{@code
 *     Path top = ...
 *     Set<FileVisitOption> options = ...
 *     int maxDepth = ...
 *
 *     try (FileTreeWalker walker = new FileTreeWalker(options, maxDepth)) {
 *         FileTreeWalker.Event ev = walker.walk(top);
 *         do {
 *             process(ev);
 *             ev = walker.next();
 *         } while (ev != null);
 *     }
 * }</pre>
 *
 * <p>
 *  遍历文件树,生成对应于树中文件的事件序列。
 * 
 *  <pre> {@ code Path top = ...设置<FileVisitOption> options = ... int maxDepth = ...
 * 
 *  try(FileTreeWalker walker = new FileTreeWalker(options,maxDepth)){FileTreeWalker.Event ev = walker.walk(top); do {process(ev); ev = walker.next(); }
 *  while(ev！= null); }} </pre>。
 * 
 * 
 * @see Files#walkFileTree
 */

class FileTreeWalker implements Closeable {
    private final boolean followLinks;
    private final LinkOption[] linkOptions;
    private final int maxDepth;
    private final ArrayDeque<DirectoryNode> stack = new ArrayDeque<>();
    private boolean closed;

    /**
     * The element on the walking stack corresponding to a directory node.
     * <p>
     *  在步行堆栈上的元素对应于目录节点。
     * 
     */
    private static class DirectoryNode {
        private final Path dir;
        private final Object key;
        private final DirectoryStream<Path> stream;
        private final Iterator<Path> iterator;
        private boolean skipped;

        DirectoryNode(Path dir, Object key, DirectoryStream<Path> stream) {
            this.dir = dir;
            this.key = key;
            this.stream = stream;
            this.iterator = stream.iterator();
        }

        Path directory() {
            return dir;
        }

        Object key() {
            return key;
        }

        DirectoryStream<Path> stream() {
            return stream;
        }

        Iterator<Path> iterator() {
            return iterator;
        }

        void skip() {
            skipped = true;
        }

        boolean skipped() {
            return skipped;
        }
    }

    /**
     * The event types.
     * <p>
     *  事件类型。
     * 
     */
    static enum EventType {
        /**
         * Start of a directory
         * <p>
         *  开始目录
         * 
         */
        START_DIRECTORY,
        /**
         * End of a directory
         * <p>
         *  目录结束
         * 
         */
        END_DIRECTORY,
        /**
         * An entry in a directory
         * <p>
         *  目录中的条目
         * 
         */
        ENTRY;
    }

    /**
     * Events returned by the {@link #walk} and {@link #next} methods.
     * <p>
     *  由{@link #walk}和{@link #next}方法返回的事件。
     * 
     */
    static class Event {
        private final EventType type;
        private final Path file;
        private final BasicFileAttributes attrs;
        private final IOException ioe;

        private Event(EventType type, Path file, BasicFileAttributes attrs, IOException ioe) {
            this.type = type;
            this.file = file;
            this.attrs = attrs;
            this.ioe = ioe;
        }

        Event(EventType type, Path file, BasicFileAttributes attrs) {
            this(type, file, attrs, null);
        }

        Event(EventType type, Path file, IOException ioe) {
            this(type, file, null, ioe);
        }

        EventType type() {
            return type;
        }

        Path file() {
            return file;
        }

        BasicFileAttributes attributes() {
            return attrs;
        }

        IOException ioeException() {
            return ioe;
        }
    }

    /**
     * Creates a {@code FileTreeWalker}.
     *
     * <p>
     *  创建{@code FileTreeWalker}。
     * 
     * 
     * @throws  IllegalArgumentException
     *          if {@code maxDepth} is negative
     * @throws  ClassCastException
     *          if (@code options} contains an element that is not a
     *          {@code FileVisitOption}
     * @throws  NullPointerException
     *          if {@code options} is {@ocde null} or the options
     *          array contains a {@code null} element
     */
    FileTreeWalker(Collection<FileVisitOption> options, int maxDepth) {
        boolean fl = false;
        for (FileVisitOption option: options) {
            // will throw NPE if options contains null
            switch (option) {
                case FOLLOW_LINKS : fl = true; break;
                default:
                    throw new AssertionError("Should not get here");
            }
        }
        if (maxDepth < 0)
            throw new IllegalArgumentException("'maxDepth' is negative");

        this.followLinks = fl;
        this.linkOptions = (fl) ? new LinkOption[0] :
            new LinkOption[] { LinkOption.NOFOLLOW_LINKS };
        this.maxDepth = maxDepth;
    }

    /**
     * Returns the attributes of the given file, taking into account whether
     * the walk is following sym links is not. The {@code canUseCached}
     * argument determines whether this method can use cached attributes.
     * <p>
     *  返回给定文件的属性,考虑是否步行是在sym链接之后。 {@code canUseCached}参数确定此方法是否可以使用缓存的属性。
     * 
     */
    private BasicFileAttributes getAttributes(Path file, boolean canUseCached)
        throws IOException
    {
        // if attributes are cached then use them if possible
        if (canUseCached &&
            (file instanceof BasicFileAttributesHolder) &&
            (System.getSecurityManager() == null))
        {
            BasicFileAttributes cached = ((BasicFileAttributesHolder)file).get();
            if (cached != null && (!followLinks || !cached.isSymbolicLink())) {
                return cached;
            }
        }

        // attempt to get attributes of file. If fails and we are following
        // links then a link target might not exist so get attributes of link
        BasicFileAttributes attrs;
        try {
            attrs = Files.readAttributes(file, BasicFileAttributes.class, linkOptions);
        } catch (IOException ioe) {
            if (!followLinks)
                throw ioe;

            // attempt to get attrmptes without following links
            attrs = Files.readAttributes(file,
                                         BasicFileAttributes.class,
                                         LinkOption.NOFOLLOW_LINKS);
        }
        return attrs;
    }

    /**
     * Returns true if walking into the given directory would result in a
     * file system loop/cycle.
     * <p>
     *  返回true如果走进给定的目录将导致文件系统循环/循环。
     * 
     */
    private boolean wouldLoop(Path dir, Object key) {
        // if this directory and ancestor has a file key then we compare
        // them; otherwise we use less efficient isSameFile test.
        for (DirectoryNode ancestor: stack) {
            Object ancestorKey = ancestor.key();
            if (key != null && ancestorKey != null) {
                if (key.equals(ancestorKey)) {
                    // cycle detected
                    return true;
                }
            } else {
                try {
                    if (Files.isSameFile(dir, ancestor.directory())) {
                        // cycle detected
                        return true;
                    }
                } catch (IOException | SecurityException x) {
                    // ignore
                }
            }
        }
        return false;
    }

    /**
     * Visits the given file, returning the {@code Event} corresponding to that
     * visit.
     *
     * The {@code ignoreSecurityException} parameter determines whether
     * any SecurityException should be ignored or not. If a SecurityException
     * is thrown, and is ignored, then this method returns {@code null} to
     * mean that there is no event corresponding to a visit to the file.
     *
     * The {@code canUseCached} parameter determines whether cached attributes
     * for the file can be used or not.
     * <p>
     *  访问指定的文件,返回与访问对应的{@code Event}。
     * 
     *  {@code ignoreSecurityException}参数确定是否应忽略任何SecurityException。
     * 如果抛出SecurityException并被忽略,则此方法返回{@code null},表示没有与访问文件对应的事件。
     * 
     * {@code canUseCached}参数确定是否可以使用文件的缓存属性。
     * 
     */
    private Event visit(Path entry, boolean ignoreSecurityException, boolean canUseCached) {
        // need the file attributes
        BasicFileAttributes attrs;
        try {
            attrs = getAttributes(entry, canUseCached);
        } catch (IOException ioe) {
            return new Event(EventType.ENTRY, entry, ioe);
        } catch (SecurityException se) {
            if (ignoreSecurityException)
                return null;
            throw se;
        }

        // at maximum depth or file is not a directory
        int depth = stack.size();
        if (depth >= maxDepth || !attrs.isDirectory()) {
            return new Event(EventType.ENTRY, entry, attrs);
        }

        // check for cycles when following links
        if (followLinks && wouldLoop(entry, attrs.fileKey())) {
            return new Event(EventType.ENTRY, entry,
                             new FileSystemLoopException(entry.toString()));
        }

        // file is a directory, attempt to open it
        DirectoryStream<Path> stream = null;
        try {
            stream = Files.newDirectoryStream(entry);
        } catch (IOException ioe) {
            return new Event(EventType.ENTRY, entry, ioe);
        } catch (SecurityException se) {
            if (ignoreSecurityException)
                return null;
            throw se;
        }

        // push a directory node to the stack and return an event
        stack.push(new DirectoryNode(entry, attrs.fileKey(), stream));
        return new Event(EventType.START_DIRECTORY, entry, attrs);
    }


    /**
     * Start walking from the given file.
     * <p>
     *  从给定的文件开始走。
     * 
     */
    Event walk(Path file) {
        if (closed)
            throw new IllegalStateException("Closed");

        Event ev = visit(file,
                         false,   // ignoreSecurityException
                         false);  // canUseCached
        assert ev != null;
        return ev;
    }

    /**
     * Returns the next Event or {@code null} if there are no more events or
     * the walker is closed.
     * <p>
     *  如果没有更多事件或walker关闭,则返回下一个事件或{@code null}。
     * 
     */
    Event next() {
        DirectoryNode top = stack.peek();
        if (top == null)
            return null;      // stack is empty, we are done

        // continue iteration of the directory at the top of the stack
        Event ev;
        do {
            Path entry = null;
            IOException ioe = null;

            // get next entry in the directory
            if (!top.skipped()) {
                Iterator<Path> iterator = top.iterator();
                try {
                    if (iterator.hasNext()) {
                        entry = iterator.next();
                    }
                } catch (DirectoryIteratorException x) {
                    ioe = x.getCause();
                }
            }

            // no next entry so close and pop directory, creating corresponding event
            if (entry == null) {
                try {
                    top.stream().close();
                } catch (IOException e) {
                    if (ioe != null) {
                        ioe = e;
                    } else {
                        ioe.addSuppressed(e);
                    }
                }
                stack.pop();
                return new Event(EventType.END_DIRECTORY, top.directory(), ioe);
            }

            // visit the entry
            ev = visit(entry,
                       true,   // ignoreSecurityException
                       true);  // canUseCached

        } while (ev == null);

        return ev;
    }

    /**
     * Pops the directory node that is the current top of the stack so that
     * there are no more events for the directory (including no END_DIRECTORY)
     * event. This method is a no-op if the stack is empty or the walker is
     * closed.
     * <p>
     *  弹出是当前堆栈顶部的目录节点,以便没有更多的目录事件(包括没有END_DIRECTORY)事件。如果堆栈为空或者walker关闭,这个方法是无操作的。
     * 
     */
    void pop() {
        if (!stack.isEmpty()) {
            DirectoryNode node = stack.pop();
            try {
                node.stream().close();
            } catch (IOException ignore) { }
        }
    }

    /**
     * Skips the remaining entries in the directory at the top of the stack.
     * This method is a no-op if the stack is empty or the walker is closed.
     * <p>
     *  跳过堆栈顶部目录中的剩余条目。如果堆栈为空或者walker关闭,这个方法是无操作的。
     * 
     */
    void skipRemainingSiblings() {
        if (!stack.isEmpty()) {
            stack.peek().skip();
        }
    }

    /**
     * Returns {@code true} if the walker is open.
     * <p>
     *  如果步行者打开,返回{@code true}。
     * 
     */
    boolean isOpen() {
        return !closed;
    }

    /**
     * Closes/pops all directories on the stack.
     * <p>
     *  关闭/弹出堆栈中的所有目录。
     */
    @Override
    public void close() {
        if (!closed) {
            while (!stack.isEmpty()) {
                pop();
            }
            closed = true;
        }
    }
}
