/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2001, Oracle and/or its affiliates. All rights reserved.
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
 * A <tt>PolicyErrorCode</tt> which would be filled if the value
 * requested for the <tt>Policy</tt> is of a
 * valid type and within the valid range for that type, but this valid value
 * is not currently supported.
 *
 * <p>
 *  <tt> PolicyErrorCode </tt>,如果<tt> Policy </tt>所请求的值是有效类型并且在该类型的有效范围内,则该值将被填充,但此有效值当前不受支持。
 * 
 * 
 * @author rip-dev
 */
public interface UNSUPPORTED_POLICY_VALUE {
    /**
     *  The Error code for PolicyError exception.
     * <p>
     *  PolicyError异常的错误代码。
     */
    final short value = (short) (4L);
};
