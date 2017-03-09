/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2004, Oracle and/or its affiliates. All rights reserved.
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

package java.io;

import java.io.ObjectOutput;
import java.io.ObjectInput;

/**
 * Only the identity of the class of an Externalizable instance is
 * written in the serialization stream and it is the responsibility
 * of the class to save and restore the contents of its instances.
 *
 * The writeExternal and readExternal methods of the Externalizable
 * interface are implemented by a class to give the class complete
 * control over the format and contents of the stream for an object
 * and its supertypes. These methods must explicitly
 * coordinate with the supertype to save its state. These methods supersede
 * customized implementations of writeObject and readObject methods.<br>
 *
 * Object Serialization uses the Serializable and Externalizable
 * interfaces.  Object persistence mechanisms can use them as well.  Each
 * object to be stored is tested for the Externalizable interface. If
 * the object supports Externalizable, the writeExternal method is called. If the
 * object does not support Externalizable and does implement
 * Serializable, the object is saved using
 * ObjectOutputStream. <br> When an Externalizable object is
 * reconstructed, an instance is created using the public no-arg
 * constructor, then the readExternal method called.  Serializable
 * objects are restored by reading them from an ObjectInputStream.<br>
 *
 * An Externalizable instance can designate a substitution object via
 * the writeReplace and readResolve methods documented in the Serializable
 * interface.<br>
 *
 * <p>
 *  只有Externalizable实例的类的身份被写入序列化流中,并且类的责任是保存和恢复其实例的内容。
 * 
 *  Externalizable接口的writeExternal和readExternal方法是通过一个类来实现的,以使该类完全控制对象及其超类型的流的格式和内容。
 * 这些方法必须明确地与超类型协调以保存其状态。这些方法取代了writeObject和readObject方法的自定义实现。
 * 
 *  对象序列化使用Serializable和Externalizable接口。对象持久机制也可以使用它们。要存储的每个对象都针对Externalizable接口进行测试。
 * 如果对象支持Externalizable,则调用writeExternal方法。
 * 如果对象不支持Externalizable并且实现Serializable,则使用ObjectOutputStream保存对象。
 *  <br>当重构可外化对象时,将使用public no-arg构造函数创建一个实例,然后调用readExternal方法。可序列化对象通过从ObjectInputStream读取来恢复。<br>。
 * 
 * 
 * @author  unascribed
 * @see java.io.ObjectOutputStream
 * @see java.io.ObjectInputStream
 * @see java.io.ObjectOutput
 * @see java.io.ObjectInput
 * @see java.io.Serializable
 * @since   JDK1.1
 */
public interface Externalizable extends java.io.Serializable {
    /**
     * The object implements the writeExternal method to save its contents
     * by calling the methods of DataOutput for its primitive values or
     * calling the writeObject method of ObjectOutput for objects, strings,
     * and arrays.
     *
     * <p>
     * Externalizable实例可以通过Serializable接口中记录的writeReplace和readResolve方法指定替换对象。<br>
     * 
     * 
     * @serialData Overriding methods should use this tag to describe
     *             the data layout of this Externalizable object.
     *             List the sequence of element types and, if possible,
     *             relate the element to a public/protected field and/or
     *             method of this Externalizable class.
     *
     * @param out the stream to write the object to
     * @exception IOException Includes any I/O exceptions that may occur
     */
    void writeExternal(ObjectOutput out) throws IOException;

    /**
     * The object implements the readExternal method to restore its
     * contents by calling the methods of DataInput for primitive
     * types and readObject for objects, strings and arrays.  The
     * readExternal method must read the values in the same sequence
     * and with the same types as were written by writeExternal.
     *
     * <p>
     *  该对象实现writeExternal方法,通过调用DataOutput的原始值方法或调用ObjectOutput的writeObject方法来保存对象,字符串和数组的内容。
     * 
     * 
     * @param in the stream to read data from in order to restore the object
     * @exception IOException if I/O errors occur
     * @exception ClassNotFoundException If the class for an object being
     *              restored cannot be found.
     */
    void readExternal(ObjectInput in) throws IOException, ClassNotFoundException;
}
