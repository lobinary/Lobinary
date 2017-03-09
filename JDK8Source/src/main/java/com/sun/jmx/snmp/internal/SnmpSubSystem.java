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

import com.sun.jmx.snmp.SnmpEngine;
import com.sun.jmx.snmp.SnmpUnknownModelException;
import java.util.Hashtable;
/**
 * SNMP sub system interface. To allow engine framework integration, a sub system must implement this interface. A sub system is a model manager. Every model is identified by an ID. A sub system can retrieve a previously registered model using this ID.
 * <P> Every sub system is associated to its SNMP engine.
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  SNMP子系统接口。为了允许引擎框架集成,子系统必须实现此接口。子系统是模型管理器。每个模型都由ID标识。子系统可以使用该ID检索先前注册的模型。 <P>每个子系统都与其SNMP引擎相关联。
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>。
 * 
 */
public interface SnmpSubSystem {
    /**
     * Returns the associated engine.
     * <p>
     *  返回关联的引擎。
     * 
     * 
     * @return The engine.
     */
    public SnmpEngine getEngine();

    /**
     * Adds a model to this sub system.
     * <p>
     *  将模型添加到此子系统。
     * 
     * 
     * @param id The model ID.
     * @param model The model to add.
     */
    public void addModel(int id, SnmpModel model);

    /**
     * Removes a model from this sub system.
     * <p>
     *  从此子系统中删除模型。
     * 
     * 
     * @param id The model ID to remove.
     * @return The removed model.
     */
    public SnmpModel removeModel(int id) throws SnmpUnknownModelException;

    /**
     * Gets a model from this sub system.
     * <p>
     *  从此子系统获取模型。
     * 
     * 
     * @param id The model ID to get.
     * @return The model.
     */
    public SnmpModel getModel(int id) throws SnmpUnknownModelException;

    /**
     * Returns the set of model Ids that have been registered within the sub system.
     * <p>
     *  返回已在子系统中注册的模型标识的集合。
     * 
     */
    public int[] getModelIds();

    /**
     * Returns the set of model names that have been registered within the sub system.
     * <p>
     *  返回已在子系统中注册的模型名称集。
     */
    public String[] getModelNames();
}
