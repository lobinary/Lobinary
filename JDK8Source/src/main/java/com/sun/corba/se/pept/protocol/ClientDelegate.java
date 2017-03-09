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

package com.sun.corba.se.pept.protocol;

import com.sun.corba.se.pept.broker.Broker;
import com.sun.corba.se.pept.transport.ContactInfoList;

/**
 * <p>The presentation block interacts with the PEPt architecture
 * via the <code>ClientDelegate</code>.</p>
 *
 * <p>
 *  <p>表示块通过<code> ClientDelegate </code>与PEPt体系结构进行交互。</p>
 * 
 * 
 * @author Harold Carr
 */
public interface ClientDelegate
{
    /**
     * The {@link com.sun.corba.se.pept.broker.Broker Broker} associated
     * with an invocation.
     *
     * <p>
     *  与调用相关联的{@link com.sun.corba.se.pept.broker.Broker Broker}。
     * 
     * 
     * @return {@link com.sun.corba.se.pept.broker.Broker Broker}
     */
    public Broker getBroker();

    /**
     * Get the
     * {@link com.sun.corba.se.pept.transport.ContactInfoList ContactInfoList}
     * which represents they encoding/protocol/transport combinations that
     * may be used to contact the service.
     *
     * <p>
     *  获取{@link com.sun.corba.se.pept.transport.ContactInfoList ContactInfoList},它表示可用于联系服务的编码/协议/传输组合。
     * 
     * @return
     * {@link com.sun.corba.se.pept.transport.ContactInfoList ContactInfoList}
     */
    public ContactInfoList getContactInfoList();
}

// End of file.
