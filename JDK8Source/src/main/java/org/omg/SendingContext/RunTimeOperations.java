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
/*
 * Licensed Materials - Property of IBM
 * RMI-IIOP v1.0
 * Copyright IBM Corp. 1998 1999  All Rights Reserved
 *
 * <p>
 *  许可的材料 -  IBM RMI-IIOP v1.0的属性版权所有IBM Corp. 1998 1999保留所有权利
 * 
 */

package org.omg.SendingContext;

/** Defines operations on the base class that represents the Sending Context of a
* request.  The sending context provides access to information about
* the runtime environment of the originator of a GIOP message.  For example,
* when a value type is marshalled on a GIOP Request message, the receiver
* of the value type may need to ask the sender about the CodeBase for the
* implementation of the value type.
* <p>
*  请求。发送上下文提供对关于GIOP消息的发起者的运行时环境的信息的访问。例如,当值类型在GIOP请求消息上编组时,值类型的接收者可能需要向发送者询问用于实现值类型的CodeBase。
*/
public interface RunTimeOperations
{
}
