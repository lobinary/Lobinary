/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 1999, Oracle and/or its affiliates. All rights reserved.
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

package org.omg.CORBA;

/**
 * Signifies an argument used for both input and output in an invocation,
 * meaning that the argument is being passed from the client to
 * the server and then back from the server to the client.
 * <code>ARG_INOUT.value</code> is one of the possible values used to
 * indicate the direction in
 * which a parameter is being passed during a dynamic invocation
 * using the Dynamic Invocation Interface (DII).
 * <P>
 * The code fragment below shows a typical usage:
 * <PRE>
 *  ORB orb = ORB.init(args, null);
 *  org.omg.CORBA.NamedValue nv = orb.create_named_value(
 *        "argumentIdentifier", myAny, org.omg.CORBA.ARG_INOUT.value);
 * </PRE>
 *
 * <p>
 *  表示在调用中用于输入和输出的参数,意味着该参数正在从客户端传递到服务器,然后从服务器传回到客户端。
 *  <code> ARG_INOUT.value </code>是用于指示在使用动态调用接口(DII)的动态调用期间传递参数的方向的可能值之一。
 * <P>
 *  下面的代码片段显示了一个典型的用法：
 * <PRE>
 *  ORB orb = ORB.init(args,null); org.omg.CORBA.NamedValue nv = orb.create_named_value("argumentIdentif
 * 
 * @see     org.omg.CORBA.NamedValue
 * @since   JDK1.2
 */
public interface ARG_INOUT {

/**
 * The constant value indicating an argument used for both
 * input and output.
 * <p>
 * ier",myAny,org.omg.CORBA.ARG_INOUT.value);。
 * </PRE>
 * 
 */
  int value = 3;
}
