/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2003, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.spi.orbutil.fsm;

/**
 * This interface must be implemented by any class that is used as
 * an input to a FSM.  The FSM only needs the identity of this
 * object, so all that is really needs is the default equals implementation.
 * The toString() method should also be overridden to give a concise
 * description or name of the input.
 *
 * <p>
 *  此接口必须由用作FSM的输入的任何类实现。 FSM只需要这个对象的身份,所以真正需要的是默认的equals实现。 toString()方法也应该被覆盖,以提供输入的简明描述或名称。
 * 
 * @author Ken Cavanaugh
 */
public interface Input
{
}

// end of Input.java
