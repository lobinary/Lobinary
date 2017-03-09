/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.spi.oa ;

import org.omg.CORBA.SystemException ;

/** NullServant is used to represent a null servant returned
 * OAInvocationInfo after a
 * ObjectAdapter.getInvocationServant( OAInvocationInfo ) call.
 * If the getInvocationServant call could not locate a servant
 * for the ObjectId in the OAInvocationInfo, getServantContainer
 * will contain a NullServant.  Later stages of the request
 * dispatch may choose either to throw the exception or perform
 * some other action in response to the NullServant result.
 * <p>
 *  OAInvocationInfo(OAInvocationInfo)调用后的OAInvocationInfo。
 * 如果getInvocationServant调用找不到OAInvocationInfo中ObjectId的servant,getServantContainer将包含NullServant。
 * 请求分派的稍后阶段可以选择抛出异常或执行一些其他动作以响应NullServant结果。
 * 
 */
public interface NullServant
{
    /** Obtain the exception that is associated with this
     * NullServant instance.
     * <p>
     */
    SystemException getException() ;
}
