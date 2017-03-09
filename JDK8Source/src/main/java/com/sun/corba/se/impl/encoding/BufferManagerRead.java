/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2003, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.impl.encoding;

import java.nio.ByteBuffer;
import com.sun.corba.se.impl.encoding.ByteBufferWithInfo;
import com.sun.corba.se.impl.protocol.giopmsgheaders.FragmentMessage;
import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

public interface BufferManagerRead
{
    /**
     * Case: Called from ReaderThread on complete message or fragments.
     *       The given buf may be entire message or a fragment.
     *
     *  The ReaderThread finds the ReadBufferManager instance either in
     *  in a fragment map (when collecting - GIOP 1.2 phase 1) or
     *  in an active server requests map (when streaming - GIOP 1.2 phase 2).
     *
     *  As a model for implementation see IIOPInputStream's
     *  constructor of the same name. There are going to be some variations.
     *
     * <p>
     *  情况：从ReaderThread调用完整的消息或片段。给定的buf可以是整个消息或片段。
     * 
     *  ReaderThread在片段映射(当收集 -  GIOP 1.2阶段1时)或在活动服务器请求映射(当流 -  GIOP 1.2阶段2时)中找到ReadBufferManager实例。
     * 
     *  作为实现的模型,参见IIOPInputStream的同名的构造函数。会有一些变化。
     * 
     */

    public void processFragment ( ByteBuffer byteBuffer,
        FragmentMessage header);


    /**
     * Case: called from CDRInputStream constructor before unmarshaling.
     *
     * Does:
     *
     *  this.bufQ.get()
     *
     *  If streaming then sync on bufQ and wait if empty.
     * <p>
     *  情况：在取消编组之前从CDRInputStream构造函数调用。
     * 
     *  是否：
     * 
     *  this.bufQ.get()
     * 
     *  如果流式传输然后同步bufQ和等待,如果空。
     * 
     */


    /**
     * Case: called from CDRInputStream.grow.
     *
     * Does:
     *
     *  this.bufQ.get()
     *
     *  If streaming then sync on bufQ and wait if empty.
     * <p>
     *  案例：从CDRInputStream.grow调用。
     * 
     *  是否：
     * 
     *  this.bufQ.get()
     * 
     *  如果流式传输然后同步bufQ和等待,如果空。
     * 
     */

    public ByteBufferWithInfo underflow (ByteBufferWithInfo bbwi);

    /**
     * Called once after creating this buffer manager and before
     * it begins processing.
     * <p>
     *  在创建此缓冲区管理器之后并在开始处理之前调用一次。
     * 
     */
    public void init(Message header);

    /**
     * Returns the mark/reset handler for this stream.
     * <p>
     *  返回此流的标记/重置处理程序。
     * 
     */
    public MarkAndResetHandler getMarkAndResetHandler();

    /*
     * Signals that the processing be cancelled.
     * <p>
     *  表示处理被取消。
     * 
     */
    public void cancelProcessing(int requestId);

    /*
     * Close BufferManagerRead and perform any oustanding cleanup.
     * <p>
     *  关闭BufferManager读取并执行任何清理。
     */
    public void close(ByteBufferWithInfo bbwi);
}
