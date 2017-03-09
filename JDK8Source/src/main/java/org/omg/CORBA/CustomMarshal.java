/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2003, Oracle and/or its affiliates. All rights reserved.
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

import org.omg.CORBA.DataOutputStream;
import org.omg.CORBA.DataInputStream;

/**
 * An abstract value type that is meant to
 * be used by the ORB, not the user. Semantically it is treated
 * as a custom value type's implicit base class, although the custom
 * valuetype does not actually inherit it in IDL. The implementer
 * of a custom value type shall provide an implementation of the
 * <tt>CustomMarshal</tt> operations. The manner in which this is done is
 * specified in the IDL to Java langauge mapping. Each custom
 * marshaled value type shall have its own implementation.
 * <p>
 *  抽象值类型,意味着被ORB使用,而不是用户。语义上它被视为自定义值类型的隐式基类,尽管自定义值类型实际上不在IDL中继承它。
 * 自定义值类型的实现者应提供<tt> CustomMarshal </tt>操作的实现。执行此操作的方式在IDL到Java langauge映射中指定。每个自定义的封送值类型都有自己的实现。
 * 
 * 
 * @see DataInputStream
 */
public interface CustomMarshal {
    /**
     * Marshal method has to be implemented by the Customized Marshal class.
     * This is the method invoked for Marshalling.
     *
     * <p>
     *  Marshal方法必须由Customized Marshal类实现。这是为编组调用的方法。
     * 
     * 
     * @param os a DataOutputStream
     */
    void marshal(DataOutputStream os);
    /**
     * Unmarshal method has to be implemented by the Customized Marshal class.
     * This is the method invoked for Unmarshalling.
     *
     * <p>
     *  Unmarshal方法必须由Customized Marshal类实现。这是为取消编组调用的方法。
     * 
     * @param is a DataInputStream
     */
    void unmarshal(DataInputStream is);
}
