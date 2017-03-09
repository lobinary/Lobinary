/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2006, Oracle and/or its affiliates. All rights reserved.
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
package com.sun.jmx.snmp.agent;

import com.sun.jmx.snmp.SnmpOid;

/**
 * This class only adds a new constructor to SnmpOid...
 *
 * <p>
 *  此类只向SnmpOid添加一个新构造函数...
 * 
 * 
 **/
class SnmpEntryOid extends SnmpOid {
    private static final long serialVersionUID = 9212653887791059564L;

    /**
     * Constructs a new <CODE>SnmpOid</CODE> from the specified
     * component array, starting at given position.
     *
     * <p>
     *  从指定的元素数组构造一个新的<CODE> SnmpOid </CODE>,从给定位置开始。
     * 
     * @param oid   The original OID array
     * @param start The position at which to begin.
     *
     **/
    public SnmpEntryOid(long[] oid, int start) {
        final int subLength = oid.length - start;
        final long[] subOid = new long[subLength];
        java.lang.System.arraycopy(oid, start, subOid, 0, subLength) ;
        components = subOid;
        componentCount = subLength;
    }
}
