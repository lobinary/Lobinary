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
package com.sun.jmx.snmp;
/**
 * Interface implemented by classes modelizing bulk pdu.
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  通过建模批量pdu的类实现的接口。 <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 * 
 * @since 1.5
 */

public interface SnmpPduBulkType extends SnmpAckPdu {

    /**
     * The <CODE>max-repetitions</CODE> setter.
     * <p>
     *  <CODE> max-repetitions </CODE>设置器。
     * 
     * 
     * @param max Maximum repetition.
     */
    public void setMaxRepetitions(int max);

    /**
     * The <CODE>non-repeaters</CODE> setter.
     * <p>
     *  <CODE>非中继器</CODE>设置器。
     * 
     * 
     * @param nr Non repeaters.
     */
    public void setNonRepeaters(int nr);

    /**
     * The <CODE>max-repetitions</CODE> getter.
     * <p>
     *  <CODE> max-repeatedries </CODE> getter。
     * 
     * 
     * @return Maximum repetition.
     */
    public int getMaxRepetitions();

    /**
     * The <CODE>non-repeaters</CODE> getter.
     * <p>
     *  <CODE>非中继器</CODE> getter。
     * 
     * @return Non repeaters.
     */
    public int getNonRepeaters();
}
