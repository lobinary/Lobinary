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
 * An interface that is implemented by objects that perform match operations on
 * paths.
 *
 * <p>
 *  由在路径上执行匹配操作的对象实现的接口。
 * 
 * 
 * @since 1.7
 *
 * @see FileSystem#getPathMatcher
 * @see Files#newDirectoryStream(Path,String)
 */
@FunctionalInterface
public interface PathMatcher {
    /**
     * Tells if given path matches this matcher's pattern.
     *
     * <p>
     *  告诉给定的路径是否匹配这个匹配器的模式。
     * 
     * @param   path
     *          the path to match
     *
     * @return  {@code true} if, and only if, the path matches this
     *          matcher's pattern
     */
    boolean matches(Path path);
}
