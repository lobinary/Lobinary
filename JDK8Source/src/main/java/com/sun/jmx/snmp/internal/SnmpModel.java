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
package com.sun.jmx.snmp.internal;
/**
 * Interface that every SNMP model must implement in order to be integrated in the engine framework.
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  每个SNMP模型必须实现的接口,以便集成到引擎框架中。 <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 * 
 * @since 1.5
 */
public interface SnmpModel {

    /**
     * Returns the sub system that manages this model.
     * <p>
     *  返回管理此模型的子系统。
     * 
     * 
     * @return The sub system.
     */
    public SnmpSubSystem getSubSystem();
    /**
     * A human readable model name.
     * <p>
     *  人类可读的模型名称。
     * 
     * @return The model name.
     */
    public String getName();
}
