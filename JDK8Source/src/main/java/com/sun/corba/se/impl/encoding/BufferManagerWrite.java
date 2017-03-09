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

import com.sun.corba.se.spi.logging.CORBALogDomains;

import com.sun.corba.se.spi.orb.ORB;

import com.sun.corba.se.impl.encoding.ByteBufferWithInfo;

import com.sun.corba.se.impl.logging.ORBUtilSystemException;

/**
 * Defines the contract between the BufferManager and
 * CDR stream on the writing side.  The CDR stream
 * calls back to the BufferManagerWrite when it needs
 * more room in the output buffer to continue.  The
 * BufferManager can then grow the output buffer or
 * use some kind of fragmentation technique.
 * <p>
 *  定义写入端的BufferManager和CDR流之间的合同。当它需要更多的空间在输出缓冲区继续时,CDR流调​​用回BufferManagerWrite。
 *  BufferManager然后可以增加输出缓冲区或使用某种分段技术。
 * 
 */
public abstract class BufferManagerWrite
{
    protected ORB orb ;
    protected ORBUtilSystemException wrapper ;

    BufferManagerWrite( ORB orb )
    {
        this.orb = orb ;
        this.wrapper = ORBUtilSystemException.get( orb,
            CORBALogDomains.RPC_ENCODING ) ;
    }

    /**
     * Has the stream sent out any fragments so far?
     * <p>
     *  有没有流发出任何碎片到目前为止?
     * 
     */
    public abstract boolean sentFragment();

    /**
     * Has the entire message been sent?  (Has
     * sendMessage been called?)
     * <p>
     *  整个消息是否已发送? (有sendMessage被调用吗?)
     * 
     */
    public boolean sentFullMessage() {
        return sentFullMessage;
    }

    /**
     * Returns the correct buffer size for this type of
     * buffer manager as set in the ORB.
     * <p>
     *  返回在ORB中设置的此类型缓冲区管理器的正确缓冲区大小。
     * 
     */
    public abstract int getBufferSize();

    /*
     * Called from CDROutputStream.grow.
     *
     * bbwi.buf contains a byte array which needs to grow by bbwi.needed bytes.
     *
     * This can be handled in several ways:
     *
     * 1. Resize the bbwi.buf like the current implementation of
     *    CDROutputStream.grow.
     *
     * 2. Collect the buffer for a later send:
     *    this.bufQ.put(bbwi);
     *    return new ByteBufferWithInfo(bbwi.length);
     *
     * 3. Send buffer as fragment:
     *    Backpatch fragment size field in bbwi.buf.
     *    Set more fragments bit in bbwi.buf.
     *    this.connection.send(bbwi);
     *    return reinitialized bbwi.buf with fragment header
     *
     * All cases should adjust the returned bbwi.* appropriately.
     *
     * Should set the bbwi.fragmented flag to true only in cases 2 and 3.
     * <p>
     *  从CDROutputStream.grow调用。
     * 
     *  bbwi.buf包含一个需要通过bbwi.needed字节增长的字节数组。
     * 
     *  这可以通过几种方式处理：
     * 
     *  1.像当前CDROutputStream.grow的实现一样调整bbwi.buf的大小。
     * 
     *  2.收集缓冲区以供稍后发送：this.bufQ.put(bbwi); return new ByteBufferWithInfo(bbwi.length);
     * 
     *  3.将缓冲区作为片段发送：bbwi.buf中的Backpatch片段大小字段。在bbwi.buf中设置更多分段位。
     *  this.connection.send(bbwi);返回重新初始化bbwi.buf与片段头。
     * 
     *  所有情况应适当调整返回的bbwi。*。
     * 
     *  应该将bbwi.fragmented标志设置为true仅在情况2和3。
     * 
     */

    public abstract void overflow (ByteBufferWithInfo bbwi);

    /**
     * Called after Stub._invoke (i.e., before complete message has been sent).
     *
     * IIOPOutputStream.writeTo called from IIOPOutputStream.invoke
     *
     * Case: overflow was never called (bbwi.buf contains complete message).
     *       Backpatch size field.
     *       If growing or collecting:
     *          this.bufQ.put(bbwi).
     *          this.bufQ.iterate // However, see comment in getBufferQ
     *             this.connection.send(fragment)
     *       If streaming:
     *          this.connection.send(bbwi).
     *
     * Case: overflow was called N times (bbwi.buf contains last buffer).
     *       If growing or collecting:
     *          this.bufQ.put(bbwi).
     *          backpatch size field in first buffer.
     *          this.bufQ.iterate // However, see comment in getBufferQ
     *             this.connection.send(fragment)
     *       If streaming:
     *          backpatch fragment size field in bbwi.buf.
     *          Set no more fragments bit.
     *          this.connection.send(bbwi).
     * <p>
     *  在Stub._invoke之后调用(即,在完成消息发送之前)。
     * 
     *  IIOPOutputStream.writeTo从IIOPOutputStream.invoke调用
     * 
     * case：overflow从未被调用(bbwi.buf包含完整的消息)。备份大小字段。如果增长或收集：this.bufQ.put(bbwi)。
     *  this.bufQ.iterate //但是,请参阅getBufferQ中的注释this.connection.send(fragment)如果streaming：this.connection.se
     * nd(bbwi)。
     * case：overflow从未被调用(bbwi.buf包含完整的消息)。备份大小字段。如果增长或收集：this.bufQ.put(bbwi)。
     * 
     *  情况：溢出被调用了N次(bbwi.buf包含最后一个缓冲区)。如果增长或收集：this.bufQ.put(bbwi)。 backpatch大小字段在第一个缓冲区。
     *  this.bufQ.iterate //但是,请参阅getBufferQ中的注释this.connection.send(fragment)如果streaming：backpatch fragment
     */

    public abstract void sendMessage ();

    /**
     * A reference to the connection level stream will be required when
     * sending fragments.
     * <p>
     *  size字段在bbwi.buf中。
     *  情况：溢出被调用了N次(bbwi.buf包含最后一个缓冲区)。如果增长或收集：this.bufQ.put(bbwi)。 backpatch大小字段在第一个缓冲区。不再设置碎片位。
     *  this.connection.send(bbwi)。
     * 
     */
    public void setOutputObject(Object outputObject) {
        this.outputObject = outputObject;
    }

    /**
     * Close the BufferManagerWrite and do any outstanding cleanup.
     * <p>
     *  发送片段时将需要对连接级别流的引用。
     * 
     */
     abstract public void close();


    // XREVISIT - Currently a java.lang.Object during
    // the rip-int-generic transition.  Should eventually
    // become a GIOPOutputObject.
    protected Object outputObject;

    protected boolean sentFullMessage = false;
}
