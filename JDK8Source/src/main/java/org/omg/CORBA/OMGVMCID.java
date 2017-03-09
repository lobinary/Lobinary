/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, Oracle and/or its affiliates. All rights reserved.
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

package org.omg.CORBA;

/**
 * The vendor minor code ID reserved for OMG. Minor codes for the standard
 * exceptions are prefaced by the VMCID assigned to OMG, defined as the
 * constant OMGVMCID, which, like all VMCIDs, occupies the high order 20 bits.
 * <p>
 *  为OMG保留的供应商次要代码ID。用于标准异常的小代码由分配给OMG的VMCID开始,定义为常量OMGVMCID,其与所有VMCID一样占据高位20位。
 * 
 */

public interface OMGVMCID {

    /**
     * The vendor minor code ID reserved for OMG. This value is or'd with
     * the high order 20 bits of the minor code to produce the minor value
     * in a system exception.
     * <p>
     *  为OMG保留的供应商次要代码ID。该值与次要代码的高阶20位进行或运算,以在系统异常中产生次要值。
     */
    static final int value = 0x4f4d0000;
}
