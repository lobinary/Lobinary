/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2010, Oracle and/or its affiliates. All rights reserved.
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


package org.omg.CORBA.portable;

/**
 This class is part of the local stub API, the purpose of which is to provide
 high performance calls for collocated clients and servers
 (i.e. clients and servers residing in the same Java VM).
 The local stub API is supported via three additional methods on
 <code>ObjectImpl</code> and <code>Delegate</code>.
 ORB vendors may subclass this class to return additional
 request state that may be required by their implementations.
/* <p>
/*  此类是本地存根API的一部分,其目的是为并置的客户端和服务器(即驻留在同一Java VM中的客户端和服务器)提供高性能调用。
/* 通过<code> ObjectImpl </code>和<code> Delegate </code>上的三个附加方法支持本地存根API。
/*  ORB供应商可以对这个类进行子类化以返回它们的实现可能需要的附加请求状态。
/* 
/* 
 @see ObjectImpl
 @see Delegate
*/

public class ServantObject
{
    /** The real servant. The local stub may cast this field to the expected type, and then
     * invoke the operation directly. Note, the object may or may not be the actual servant
     * instance.
     * <p>
     */
    public java.lang.Object servant;
}
