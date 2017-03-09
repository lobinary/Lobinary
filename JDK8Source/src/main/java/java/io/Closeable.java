/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.io;

import java.io.IOException;

/**
 * A {@code Closeable} is a source or destination of data that can be closed.
 * The close method is invoked to release resources that the object is
 * holding (such as open files).
 *
 * <p>
 *  {@code Closeable}是可以关闭的数据源或目标。调用close方法以释放对象持有的资源(例如打开的文件)。
 * 
 * 
 * @since 1.5
 */
public interface Closeable extends AutoCloseable {

    /**
     * Closes this stream and releases any system resources associated
     * with it. If the stream is already closed then invoking this
     * method has no effect.
     *
     * <p> As noted in {@link AutoCloseable#close()}, cases where the
     * close may fail require careful attention. It is strongly advised
     * to relinquish the underlying resources and to internally
     * <em>mark</em> the {@code Closeable} as closed, prior to throwing
     * the {@code IOException}.
     *
     * <p>
     *  关闭此流并释放与其关联的任何系统资源。如果流已经关闭,则调用此方法不起作用。
     * 
     *  <p>如{@link AutoCloseable#close()}中所述,关闭可能失败的情况需要仔细注意。
     * 
     * @throws IOException if an I/O error occurs
     */
    public void close() throws IOException;
}
