/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.spi.transport;

import java.util.List;

import com.sun.corba.se.pept.transport.ContactInfo;

/**
 * This interface is the "sticky manager" for IIOP failover.  The default
 * ORB does NOT contain a sticky manager.  One is registered by supplying
 * a class via the com.sun.CORBA.transport.ORBIIOPPrimaryToContactInfoClass.
 *
 * It uses the IIOP primary host/port (with a SocketInfo.IIOP_CLEAR_TEXT type)
 * as a key to map to the last ContactInfo that resulted in successful'
 * communication.
 *
 * It mainly prevents "fallback" - if a previously failed replica comes
 * back up we do NOT want to switch back to using it - particularly in the
 * case of statefull session beans.
 *
 * Note: This assumes static lists of replicas (e.g., AS 8.1 EE).
 * This does NOT work well with LOCATION_FORWARD.
 *
 * <p>
 *  此接口是IIOP故障转移的"粘性管理器"。默认的ORB不包含粘性管理器。
 * 一个通过com.sun.CORBA.transport.ORBIIOPPrimaryToContactInfoClass提供一个类来注册。
 * 
 *  它使用IIOP主要主机/端口(使用SocketInfo.IIOP_CLEAR_TEXT类型)作为键映射到最后一个ContactInfo,导致成功的通信。
 * 
 *  它主要防止"fallback" - 如果以前失败的副本恢复,我们不想切换回使用它 - 特别是在statefull会话bean的情况下。
 * 
 * @author Harold Carr
 */
public interface IIOPPrimaryToContactInfo
{
    /**
    /* <p>
    /* 
    /*  注意：这假设复制品的静态列表(例如,AS 8.1 EE)。这不适用于LOCATION_FORWARD。
    /* 
    /* 
     * @param primary - clear any state relating to primary.
     */
    public void reset(ContactInfo primary);

    /**
    /* <p>
    /* 
     * @param primary - the key.
     * @param previous - if null return true.  Otherwise, find previous in
     * <code>contactInfos</code> and if another <code>ContactInfo</code>
     * follows it in the list then return true.  Otherwise false.
     * @param contactInfos - the list of replicas associated with the
     * primary.
     */
    public boolean hasNext(ContactInfo primary,
                           ContactInfo previous,
                           List contactInfos);

    /**
    /* <p>
    /* 
     * @param primary - the key.
     * @param previous - if null then map primary to failover.  If failover is
     * empty then map primary to primary and return primary.  If failover is
     * non-empty then return failover.  If previous is non-null that
     * indicates that the previous failed.  Therefore, find previous in
     * contactInfos.  Map the <code>ContactInfo</code> following
     * previous to primary and return that <code>ContactInfo</code>.
     */
    public ContactInfo next(ContactInfo primary,
                            ContactInfo previous,
                            List contactInfos);

}

// End of file.
