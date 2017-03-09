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

package com.sun.corba.se.pept.protocol;

import com.sun.corba.se.pept.protocol.MessageMediator;

/**
 * <code>ServerRequestDispatcher</code> coordinates the request (and possible
 * response) processing for a specific <em>protocol</em>.

 * <p>
 *  <code> ServerRequestDispatcher </code>协调特定<em>协议的请求(和可能的响应)处理</em>。
 * 
 * 
 * @author Harold Carr
 */
public interface ServerRequestDispatcher
{
    /**
     * This method coordinates the processing of a message received
     * on the server side.
     *
     * For example, this may involve finding an "object adapter" which
     * would return Ties/Servants to handle the request.
     * <p>
     *  该方法协调在服务器侧接收的消息的处理。
     * 
     */
    public void dispatch(MessageMediator messageMediator);
}

// End of file.
