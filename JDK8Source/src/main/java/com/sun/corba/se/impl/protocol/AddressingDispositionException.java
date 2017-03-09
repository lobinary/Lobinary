/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2003, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.impl.protocol;

import com.sun.corba.se.impl.protocol.giopmsgheaders.KeyAddr;

/**
 * This exception is thrown while reading GIOP 1.2 Request, LocateRequest
 * to indicate that a TargetAddress disposition is unacceptable.
 * If this exception is caught explicitly, this need to be rethrown. This
 * is eventually handled within RequestPRocessor and an appropriate reply
 * is sent back to the client.
 *
 * GIOP 1.2 allows three dispositions : KeyAddr (ObjectKey), ProfileAddr (ior
 * profile), IORAddressingInfo (IOR). If the ORB does not support the
 * disposition contained in the GIOP Request / LocateRequest 1.2 message,
 * then it sends a Reply / LocateReply indicating the correct disposition,
 * which the client ORB shall use to transparently retry the request
 * with the correct disposition.
 *
 * <p>
 *  读取GIOP 1.2 Request,LocateRequest以指示TargetAddress处置是不可接受的时抛出此异常。如果明确捕获此异常,则需要重新引用。
 * 这最终在RequestPRocessor中处理,并且适当的答复被发送回客户端。
 * 
 *  GIOP 1.2允许三种配置：KeyAddr(ObjectKey),ProfileAddr(ior profile),IORAddressingInfo(IOR)。
 */
public class AddressingDispositionException extends RuntimeException {

    private short expectedAddrDisp = KeyAddr.value;

    public AddressingDispositionException(short expectedAddrDisp) {
        this.expectedAddrDisp = expectedAddrDisp;
    }

    public short expectedAddrDisp() {
        return this.expectedAddrDisp;
    }
}
