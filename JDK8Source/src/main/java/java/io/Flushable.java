/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * A <tt>Flushable</tt> is a destination of data that can be flushed.  The
 * flush method is invoked to write any buffered output to the underlying
 * stream.
 *
 * <p>
 *  <tt> Flushable </tt>是可以刷新的数据的目标。调用flush方法将任何缓冲输出写入基础流。
 * 
 * 
 * @since 1.5
 */
public interface Flushable {

    /**
     * Flushes this stream by writing any buffered output to the underlying
     * stream.
     *
     * <p>
     *  通过将任何缓冲输出写入基础流来刷新此流。
     * 
     * @throws IOException If an I/O error occurs
     */
    void flush() throws IOException;
}
