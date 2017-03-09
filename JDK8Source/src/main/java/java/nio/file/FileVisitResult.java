/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2009, Oracle and/or its affiliates. All rights reserved.
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
 * The result type of a {@link FileVisitor FileVisitor}.
 *
 * <p>
 *  {@link FileVisitor FileVisitor}的结果类型。
 * 
 * 
 * @since 1.7
 *
 * @see Files#walkFileTree
 */

public enum FileVisitResult {
    /**
     * Continue. When returned from a {@link FileVisitor#preVisitDirectory
     * preVisitDirectory} method then the entries in the directory should also
     * be visited.
     * <p>
     *  继续。当从{@link FileVisitor#preVisitDirectory preVisitDirectory}方法返回时,目录中的条目也应该被访问。
     * 
     */
    CONTINUE,
    /**
     * Terminate.
     * <p>
     *  终止。
     * 
     */
    TERMINATE,
    /**
     * Continue without visiting the entries in this directory. This result
     * is only meaningful when returned from the {@link
     * FileVisitor#preVisitDirectory preVisitDirectory} method; otherwise
     * this result type is the same as returning {@link #CONTINUE}.
     * <p>
     *  继续而不访问此目录中的条目。
     * 此结果仅在从{@link FileVisitor#preVisitDirectory preVisitDirectory}方法返回时才有意义;否则此结果类型与返回{@link #CONTINUE}相同。
     *  继续而不访问此目录中的条目。
     * 
     */
    SKIP_SUBTREE,
    /**
     * Continue without visiting the <em>siblings</em> of this file or directory.
     * If returned from the {@link FileVisitor#preVisitDirectory
     * preVisitDirectory} method then the entries in the directory are also
     * skipped and the {@link FileVisitor#postVisitDirectory postVisitDirectory}
     * method is not invoked.
     * <p>
     *  继续而不访问此文件或目录的<em> siblings </em>。
     * 如果从{@link FileVisitor#preVisitDirectory preVisitDirectory}方法返回,则目录中的条目也会被跳过,并且不会调用{@link FileVisitor#postVisitDirectory postVisitDirectory}
     * 方法。
     */
    SKIP_SIBLINGS;
}
