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

package com.sun.corba.se.spi.copyobject ;

/** Provides an interface for a variety of means to copy an arbitrary
 * object.  Any implementation of this interface must return an exact
 * copy of obj, preserving all aliasing across all objects reachable
 * from obj.  ReflectiveCopyException must be thrown if the implementation
 * cannot copy obj for some reason.  Note that a trivial implementation
 * of this interface is possible (always return obj), but this is often
 * not the desired implementation.
 * <p>
 *  目的。此接口的任何实现都必须返回obj的精确副本,保留从obj到达的所有对象的所有别名。如果实现因为某种原因无法复制obj,则必须抛出ReflectiveCopyException。
 * 注意,这个接口的一个简单的实现是可能的(总是返回obj),但这通常不是所需的实现。
 */
public interface ObjectCopier {
    Object copy( Object obj ) throws ReflectiveCopyException ;
}
