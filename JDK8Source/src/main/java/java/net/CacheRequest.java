/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2004, Oracle and/or its affiliates. All rights reserved.
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

package java.net;

import java.io.OutputStream;
import java.io.IOException;

/**
 * Represents channels for storing resources in the
 * ResponseCache. Instances of such a class provide an
 * OutputStream object which is called by protocol handlers to
 * store the resource data into the cache, and also an abort() method
 * which allows a cache store operation to be interrupted and
 * abandoned. If an IOException is encountered while reading the
 * response or writing to the cache, the current cache store operation
 * will be aborted.
 *
 * <p>
 *  表示用于在ResponseCache中存储资源的通道。
 * 这种类的实例提供了由协议处理程序调用以将资源数据存储到高速缓存中的OutputStream对象,以及允许中断和放弃高速缓存存储操作的abort()方法。
 * 如果在读取响应或写入高速缓存时遇到IOException,则当前高速缓存存储操作将中止。
 * 
 * 
 * @author Yingxian Wang
 * @since 1.5
 */
public abstract class CacheRequest {

    /**
     * Returns an OutputStream to which the response body can be
     * written.
     *
     * <p>
     *  返回可以写入响应主体的OutputStream。
     * 
     * 
     * @return an OutputStream to which the response body can
     *         be written
     * @throws IOException if an I/O error occurs while
     *         writing the response body
     */
    public abstract OutputStream getBody() throws IOException;

    /**
     * Aborts the attempt to cache the response. If an IOException is
     * encountered while reading the response or writing to the cache,
     * the current cache store operation will be abandoned.
     * <p>
     *  中止尝试缓存响应。如果在读取响应或写入高速缓存时遇到IOException,则当前高速缓存存储操作将被放弃。
     */
    public abstract void abort();
}
