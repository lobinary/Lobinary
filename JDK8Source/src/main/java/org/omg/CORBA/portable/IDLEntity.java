/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 1999, Oracle and/or its affiliates. All rights reserved.
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
 * An interface with no members whose only purpose is to serve as a marker
 * indicating  that an implementing class is a
 * Java value type from IDL that has a corresponding Helper class.
 * RMI IIOP serialization looks for such a marker to perform
 * marshalling/unmarshalling.
 * <p>
 *  没有成员的接口,其唯一目的是用作指示实现类是来自具有对应的辅助类的IDL的Java值类型的标记。 RMI IIOP序列化寻找这样的标记来执行编组/解组。
 * 
 **/
public interface IDLEntity extends java.io.Serializable {

}
