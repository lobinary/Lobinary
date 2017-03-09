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
import com.sun.corba.se.pept.encoding.InputObject;
import com.sun.corba.se.pept.encoding.OutputObject;
import com.sun.corba.se.pept.transport.ContactInfo;

/**
 * <code>ClientRequestDispatcher</code> coordinates the request (and possible
 * response) processing for a specific <em>protocol</em>.
 *
 * <p>
 *  <code> ClientRequestDispatcher </code>协调特定<em>协议的请求(以及可能的响应)处理</em>。
 * 
 * 
 * @author Harold Carr
 */
public interface ClientRequestDispatcher
{
    /**
     * At the beginning of a request the presentation block uses this
     * to obtain an
     * {@link com.sun.corba.se.pept.encoding.OutputObject OutputObject}
     * to set data to be sent on a message.
     *
     * <p>
     *  在请求开始时,表示块使用它来获得{@link com.sun.corba.se.pept.encoding.OutputObject OutputObject}以设置要在消息上发送的数据。
     * 
     * 
     * @param self -
     * @param methodName - the remote method name
     * @param isOneWay - <code>true</code> if the message is asynchronous
     * @param contactInfo - the
     * {@link com.sun.corba.se.pept.transport.ContactInfo ContactInfo}
     * which which created/chose this <code>ClientRequestDispatcher</code>
     *
     * @return
     * {@link com.sun.corba.se.pept.encoding.OutputObject OutputObject}
     */
    public OutputObject beginRequest(Object self,
                                     String methodName,
                                     boolean isOneWay,
                                     ContactInfo contactInfo);

    /**
     * After the presentation block has set data on the
     * {@link com.sun.corba.se.pept.encoding.OutputObject OutputObject}
     * it signals the PEPt runtime to send the encoded data by calling this
     * method.
     *
     * <p>
     *  表示块在{@link com.sun.corba.se.pept.encoding.OutputObject OutputObject}上设置数据后,它通过调用此方法向PEPt运行时发送编码数据。
     * 
     * 
     * @param self -
     * @param outputObject
     *
     * @return
     * {@link com.sun.corba.se.pept.encoding.InputObject InputObject}
     * if the message is synchronous.
     *
     * @throws
     * {@link org.omg.CORBA.portable.ApplicationException ApplicationException}
     * if the remote side raises an exception declared in the remote interface.
     *
     * @throws
     * {@link org.omg.CORBA.portable.RemarshalException RemarshalException}
     * if the PEPt runtime would like the presentation block to start over.
     */
    public InputObject marshalingComplete(java.lang.Object self,
                                          OutputObject outputObject)
    // REVISIT EXCEPTIONS
        throws
            org.omg.CORBA.portable.ApplicationException,
            org.omg.CORBA.portable.RemarshalException;

    /**
     * After the presentation block completes a request it signals
     * the PEPt runtime by calling this method.
     *
     * This method may release resources.  In some cases it may cause
     * control or error messages to be sent.
     *
     * <p>
     *  在呈现块完成请求之后,它通过调用此方法向PEPt运行时发出信号。
     * 
     * 
     * @param broker -
     * @param inputObject -
     */
    public void endRequest(Broker broker,
                           java.lang.Object self,
                           InputObject inputObject);
}

// End of file.
