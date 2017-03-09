/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
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

/**
 * Choose a network interface to be the default for
 * outgoing IPv6 traffic that does not specify a scope_id (and which needs one).
 *
 * Platforms that do not require a default interface may return null
 * which is what this implementation does.
 * <p>
 *  选择一个网络接口作为未指定scope_id(并且需要一个)的出站IPv6流量的默认网络接口。
 * 
 */

class DefaultInterface {

    static NetworkInterface getDefault() {
        return null;
    }
}
