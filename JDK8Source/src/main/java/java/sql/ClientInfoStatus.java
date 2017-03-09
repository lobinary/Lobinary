/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
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

package java.sql;

import java.util.*;

/**
 * Enumeration for status of the reason that a property could not be set
 * via a call to <code>Connection.setClientInfo</code>
 * <p>
 *  通过调用<code>无法设置属性的原因的状态枚举Connection.setClientInfo </code>
 * 
 * 
 * @since 1.6
 */

public enum ClientInfoStatus {

    /**
     * The client info property could not be set for some unknown reason
     * <p>
     *  由于某种未知原因,无法设置客户端信息属性
     * 
     * 
     * @since 1.6
     */
    REASON_UNKNOWN,

    /**
     * The client info property name specified was not a recognized property
     * name.
     * <p>
     *  指定的客户端信息属性名称不是可识别的属性名称。
     * 
     * 
     * @since 1.6
     */
    REASON_UNKNOWN_PROPERTY,

    /**
     * The value specified for the client info property was not valid.
     * <p>
     *  为客户端info属性指定的值无效。
     * 
     * 
     * @since 1.6
     */
    REASON_VALUE_INVALID,

    /**
     * The value specified for the client info property was too large.
     * <p>
     *  为客户端info属性指定的值太大。
     * 
     * @since 1.6
     */
    REASON_VALUE_TRUNCATED
}
