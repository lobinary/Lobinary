/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2003, Oracle and/or its affiliates. All rights reserved.
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
/*
 * Licensed Materials - Property of IBM
 * RMI-IIOP v1.0
 * Copyright IBM Corp. 1998 1999  All Rights Reserved
 *
 * <p>
 *  许可的材料 -  IBM RMI-IIOP v1.0的属性版权所有IBM Corp. 1998 1999保留所有权利
 * 
 */

package com.sun.corba.se.impl.corba;

import com.sun.corba.se.spi.orb.ORB ;

///////////////////////////////////////////////////////////////////////////
// helper class for deferred invocations

/*
 * The AsynchInvoke class allows for the support of asynchronous
 * invocations. Instances of these are created with a request object,
 * and when run, perform an invocation. The instance is also
 * responsible for waking up a client that might be waiting on the
 * 'get_response' method.
 * <p>
 *  AsynchInvoke类允许支持异步调用。这些实例使用请求对象创建,并且在运行时执行调用。实例还负责唤醒可能正在等待"get_response"方法的客户端。
 * 
 */

public class AsynchInvoke implements Runnable {

    private RequestImpl _req;
    private ORB         _orb;
    private boolean     _notifyORB;

    public AsynchInvoke (ORB o, RequestImpl reqToInvokeOn, boolean n)
    {
        _orb = o;
        _req = reqToInvokeOn;
        _notifyORB = n;
    };


    /*
     * The run operation calls the invocation on the request object,
     * updates the RequestImpl state to indicate that the asynchronous
     * invocation is complete, and wakes up any client that might be
     * waiting on a 'get_response' call.
     *
     * <p>
     *  运行操作调用请求对象上的调用,更新RequestImpl状态以指示异步调用完成,并唤醒可能正在等待"get_response"调用的任何客户端。
     */

    public void run()
    {
        // do the actual invocation
        _req.doInvocation();

        // for the asynchronous case, note that the response has been
        // received.
        synchronized (_req)
            {
                // update local boolean indicator
                _req.gotResponse = true;

                // notify any client waiting on a 'get_response'
                _req.notify();
            }

        if (_notifyORB == true) {
            _orb.notifyORB() ;
        }
    }

};

///////////////////////////////////////////////////////////////////////////
