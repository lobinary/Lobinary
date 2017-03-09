/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 1999, Oracle and/or its affiliates. All rights reserved.
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
package org.omg.CORBA.portable;

/**
This class is used for reporting locate forward exceptions and object forward
GIOP messages back to the ORB. In this case the ORB must remarshal the request
before trying again.
Stubs which use the stream-based model shall catch the <code>RemarshalException</code>
which is potentially thrown from the <code>_invoke()</code> method of <code>ObjectImpl</code>.
Upon catching the exception, the stub shall immediately remarshal the request by calling
<code>_request()</code>, marshalling the arguments (if any), and then calling
<code>_invoke()</code>. The stub shall repeat this process until <code>_invoke()</code>
returns normally or raises some exception other than <code>RemarshalException</code>.
/* <p>
/*  这个类用于将定位转发异常和对象转发GIOP消息报告回ORB。在这种情况下,ORB必须重新映射请求,然后再次尝试。
/* 使用基于流的模型的存根将捕获可能从<code> ObjectImpl </code>的<code> _invoke()</code>方法抛出的<code> RemarshalException </code>
/* 。
/*  这个类用于将定位转发异常和对象转发GIOP消息报告回ORB。在这种情况下,ORB必须重新映射请求,然后再次尝试。
/* 捕获异常时,存根应立即通过调用<code> _request()</code>重组请求,编组参数(如果有),然后调用<code> _invoke()</code>。
*/

public final class RemarshalException extends Exception {
    /**
     * Constructs a RemarshalException.
     * <p>
     * 存根应重复此过程,直到<code> _invoke()</code>正常返回或引发除<code> RemarshalException </code>之外的一些异常。
     * 
     */
    public RemarshalException() {
        super();
    }
}
