/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2002, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.impl.util;

/**
 * The vendor minor code ID reserved for Sun by the OMG.
 * All VMCIDs occupy the high order 20 bits.
 * <p>
 *  OMG为Sun预留的供应商次要代码ID。所有VMCID占用高阶20位。
 * 
 */

public interface SUNVMCID {

    /**
     * The vendor minor code ID reserved for Sun. This value is or'd with
     * the high order 20 bits of the minor code to produce the minor value
     * in a system exception.
     * <p>
     *  为Sun预留的供应商次要代码ID。该值与次要代码的高阶20位进行或运算,以在系统异常中产生次要值。
     */
    static final int value = 0x53550000;
}
