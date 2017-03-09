/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2009, 2013, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.security.jgss;

/**
 * Kerberos 5 AuthorizationData entry.
 * <p>
 *  Kerberos 5授权数据条目。
 * 
 */
@jdk.Exported
public final class AuthorizationDataEntry {

    private final int type;
    private final byte[] data;

    /**
     * Create an AuthorizationDataEntry object.
     * <p>
     *  创建一个AuthorizationDataEntry对象。
     * 
     * 
     * @param type the ad-type
     * @param data the ad-data, a copy of the data will be saved
     * inside the object.
     */
    public AuthorizationDataEntry(int type, byte[] data) {
        this.type = type;
        this.data = data.clone();
    }

    /**
     * Get the ad-type field.
     * <p>
     *  获取广告类型字段。
     * 
     * 
     * @return ad-type
     */
    public int getType() {
        return type;
    }

    /**
     * Get a copy of the ad-data field.
     * <p>
     *  获取广告数据字段的副本。
     * 
     * @return ad-data
     */
    public byte[] getData() {
        return data.clone();
    }

    public String toString() {
        return "AuthorizationDataEntry: type="+type+", data=" +
                data.length + " bytes:\n" +
                new sun.misc.HexDumpEncoder().encodeBuffer(data);
    }
}
