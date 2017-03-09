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

package com.sun.corba.se.spi.legacy.interceptor;

import com.sun.corba.se.spi.orb.ORB ;

/** The interface defines an extension to the standard ORBInitInfo
 * that gives access to the ORB being initialized.  Interceptors run
 * as the last stage of initialization of the ORB, so the ORB
 * instance returned by getORB is fully initialized.  Note that
 * this facility eventually shows up post-CORBA 3.0 as a result
 * of the resolution of OMG core issue on accessing the ORB from
 * local objects.
 * <p>
 *  它允许访问被初始化的ORB。拦截器作为ORB的初始化的最后阶段运行,因此由getORB返回的ORB实例被完全初始化。
 * 注意,这个工具最终显示在CORBA 3.0之后,作为解决从本地对象访问ORB的OMG核心问题的结果。
 */
public interface ORBInitInfoExt
{
    ORB getORB() ;
}
