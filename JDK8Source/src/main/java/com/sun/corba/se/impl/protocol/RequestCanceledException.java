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

package com.sun.corba.se.impl.protocol;

/**
 * If this exception is caught explicitly, this need to be rethrown.
 * <p>
 *  如果明确捕获此异常,则需要重新引用。
 */
public class RequestCanceledException extends RuntimeException {

    private int requestId = 0;

    public RequestCanceledException(int requestId) {
        this.requestId = requestId;
    }

    public int getRequestId() {
        return this.requestId;
    }
}
