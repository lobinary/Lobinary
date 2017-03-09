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

package org.omg.CORBA.portable;
import java.io.Serializable;

/**
 * The ValueFactory interface is the native mapping for the IDL
 * type CORBA::ValueFactory. The read_value() method is called by
 * the ORB runtime while in the process of unmarshaling a value type.
 * A user shall implement this method as part of implementing a type
 * specific value factory. In the implementation, the user shall call
 * is.read_value(java.io.Serializable) with a uninitialized valuetype
 * to use for unmarshaling. The value returned by the stream is
 * the same value passed in, with all the data unmarshaled.
 * <p>
 *  ValueFactory接口是IDL类型CORBA :: ValueFactory的本地映射。 read_value()方法在解组合值类型的过程中由ORB运行时调用。
 * 用户应该实现该方法作为实现类型特定值工厂的一部分。在实现中,用户应调用具有未初始化的值类型的is.read_value(java.io.Serializable)以用于取消加密。
 * 流返回的值是传入的相同值,所有数据都未分隔。
 * 
 * 
 * @see org.omg.CORBA_2_3.ORB
 */

public interface ValueFactory {
    /**
     * Is called by
     * the ORB runtime while in the process of unmarshaling a value type.
     * A user shall implement this method as part of implementing a type
     * specific value factory.
     * <p>
     * 
     * @param is an InputStream object--from which the value will be read.
     * @return a Serializable object--the value read off of "is" Input stream.
     */
    Serializable read_value(org.omg.CORBA_2_3.portable.InputStream is);
}
