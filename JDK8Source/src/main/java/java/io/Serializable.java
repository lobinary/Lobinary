/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

/**
 * Serializability of a class is enabled by the class implementing the
 * java.io.Serializable interface. Classes that do not implement this
 * interface will not have any of their state serialized or
 * deserialized.  All subtypes of a serializable class are themselves
 * serializable.  The serialization interface has no methods or fields
 * and serves only to identify the semantics of being serializable. <p>
 *
 * To allow subtypes of non-serializable classes to be serialized, the
 * subtype may assume responsibility for saving and restoring the
 * state of the supertype's public, protected, and (if accessible)
 * package fields.  The subtype may assume this responsibility only if
 * the class it extends has an accessible no-arg constructor to
 * initialize the class's state.  It is an error to declare a class
 * Serializable if this is not the case.  The error will be detected at
 * runtime. <p>
 *
 * During deserialization, the fields of non-serializable classes will
 * be initialized using the public or protected no-arg constructor of
 * the class.  A no-arg constructor must be accessible to the subclass
 * that is serializable.  The fields of serializable subclasses will
 * be restored from the stream. <p>
 *
 * When traversing a graph, an object may be encountered that does not
 * support the Serializable interface. In this case the
 * NotSerializableException will be thrown and will identify the class
 * of the non-serializable object. <p>
 *
 * Classes that require special handling during the serialization and
 * deserialization process must implement special methods with these exact
 * signatures:
 *
 * <PRE>
 * private void writeObject(java.io.ObjectOutputStream out)
 *     throws IOException
 * private void readObject(java.io.ObjectInputStream in)
 *     throws IOException, ClassNotFoundException;
 * private void readObjectNoData()
 *     throws ObjectStreamException;
 * </PRE>
 *
 * <p>The writeObject method is responsible for writing the state of the
 * object for its particular class so that the corresponding
 * readObject method can restore it.  The default mechanism for saving
 * the Object's fields can be invoked by calling
 * out.defaultWriteObject. The method does not need to concern
 * itself with the state belonging to its superclasses or subclasses.
 * State is saved by writing the individual fields to the
 * ObjectOutputStream using the writeObject method or by using the
 * methods for primitive data types supported by DataOutput.
 *
 * <p>The readObject method is responsible for reading from the stream and
 * restoring the classes fields. It may call in.defaultReadObject to invoke
 * the default mechanism for restoring the object's non-static and
 * non-transient fields.  The defaultReadObject method uses information in
 * the stream to assign the fields of the object saved in the stream with the
 * correspondingly named fields in the current object.  This handles the case
 * when the class has evolved to add new fields. The method does not need to
 * concern itself with the state belonging to its superclasses or subclasses.
 * State is saved by writing the individual fields to the
 * ObjectOutputStream using the writeObject method or by using the
 * methods for primitive data types supported by DataOutput.
 *
 * <p>The readObjectNoData method is responsible for initializing the state of
 * the object for its particular class in the event that the serialization
 * stream does not list the given class as a superclass of the object being
 * deserialized.  This may occur in cases where the receiving party uses a
 * different version of the deserialized instance's class than the sending
 * party, and the receiver's version extends classes that are not extended by
 * the sender's version.  This may also occur if the serialization stream has
 * been tampered; hence, readObjectNoData is useful for initializing
 * deserialized objects properly despite a "hostile" or incomplete source
 * stream.
 *
 * <p>Serializable classes that need to designate an alternative object to be
 * used when writing an object to the stream should implement this
 * special method with the exact signature:
 *
 * <PRE>
 * ANY-ACCESS-MODIFIER Object writeReplace() throws ObjectStreamException;
 * </PRE><p>
 *
 * This writeReplace method is invoked by serialization if the method
 * exists and it would be accessible from a method defined within the
 * class of the object being serialized. Thus, the method can have private,
 * protected and package-private access. Subclass access to this method
 * follows java accessibility rules. <p>
 *
 * Classes that need to designate a replacement when an instance of it
 * is read from the stream should implement this special method with the
 * exact signature.
 *
 * <PRE>
 * ANY-ACCESS-MODIFIER Object readResolve() throws ObjectStreamException;
 * </PRE><p>
 *
 * This readResolve method follows the same invocation rules and
 * accessibility rules as writeReplace.<p>
 *
 * The serialization runtime associates with each serializable class a version
 * number, called a serialVersionUID, which is used during deserialization to
 * verify that the sender and receiver of a serialized object have loaded
 * classes for that object that are compatible with respect to serialization.
 * If the receiver has loaded a class for the object that has a different
 * serialVersionUID than that of the corresponding sender's class, then
 * deserialization will result in an {@link InvalidClassException}.  A
 * serializable class can declare its own serialVersionUID explicitly by
 * declaring a field named <code>"serialVersionUID"</code> that must be static,
 * final, and of type <code>long</code>:
 *
 * <PRE>
 * ANY-ACCESS-MODIFIER static final long serialVersionUID = 42L;
 * </PRE>
 *
 * If a serializable class does not explicitly declare a serialVersionUID, then
 * the serialization runtime will calculate a default serialVersionUID value
 * for that class based on various aspects of the class, as described in the
 * Java(TM) Object Serialization Specification.  However, it is <em>strongly
 * recommended</em> that all serializable classes explicitly declare
 * serialVersionUID values, since the default serialVersionUID computation is
 * highly sensitive to class details that may vary depending on compiler
 * implementations, and can thus result in unexpected
 * <code>InvalidClassException</code>s during deserialization.  Therefore, to
 * guarantee a consistent serialVersionUID value across different java compiler
 * implementations, a serializable class must declare an explicit
 * serialVersionUID value.  It is also strongly advised that explicit
 * serialVersionUID declarations use the <code>private</code> modifier where
 * possible, since such declarations apply only to the immediately declaring
 * class--serialVersionUID fields are not useful as inherited members. Array
 * classes cannot declare an explicit serialVersionUID, so they always have
 * the default computed value, but the requirement for matching
 * serialVersionUID values is waived for array classes.
 *
 * <p>
 *  类的可串行化由实现java.io.Serializable接口的类启用。没有实现这个接口的类不会有任何他们的状态序列化或反序列化。可序列化类的所有子类都是可序列化的。
 * 序列化接口没有方法或字段,并且仅用于标识可序列化的语义。 <p>。
 * 
 *  为了允许序列化非可序列化类的子类型,子类型可以承担保存和恢复超类型的公共,保护和(如果可访问的)包字段的状态的责任。
 * 只有当它扩展的类具有可访问的无参数构造函数来初始化类的状态时,子类型才可以承担这种责任。如果不是这样,声明一个类Serializable是一个错误。在运行时检测到错误。 <p>。
 * 
 *  在反序列化期间,不可序列化类的字段将使用该类的公共或受保护的无参数构造函数来初始化。无参数构造函数必须可以被可序列化的子类访问。可序列化子类的字段将从流中恢复。 <p>
 * 
 * 当遍历图形时,可能会遇到不支持Serializable接口的对象。在这种情况下,NotSerializableException将被抛出,并且将标识不可序列化对象的类。 <p>
 * 
 *  在序列化和反序列化过程中需要特殊处理的类必须实现具有这些确切签名的特殊方法：
 * 
 * <PRE>
 *  private void writeObject(java.io.ObjectOutputStream out)throws IOException private void readObject(j
 * ava.io.ObjectInputStream in)throws IOException,ClassNotFoundException; private void readObjectNoData(
 * )throws ObjectStreamException;。
 * </PRE>
 * 
 *  <p> writeObject方法负责为其特定类编写对象的状态,以便相应的readObject方法可以恢复它。保存对象的字段的默认机制可以通过调用out.defaultWriteObject来调用。
 * 该方法不需要关心属于其超类或子类的状态。通过使用writeObject方法或通过使用DataOutput支持的原始数据类型的方法将各个字段写入ObjectOutputStream来保存状态。
 * 
 * <p> readObject方法负责从流中读取和恢复类字段。它可以调用in.defaultReadObject来调用恢复对象的非静态和非瞬态字段的默认机制。
 *  defaultReadObject方法使用流中的信息来将流中保存的对象的字段分配给当前对象中相应命名的字段。这处理当类已经进化以添加新字段时的情况。该方法不需要关心属于其超类或子类的状态。
 * 通过使用writeObject方法或通过使用DataOutput支持的原始数据类型的方法将各个字段写入ObjectOutputStream来保存状态。
 * 
 *  <p> readObjectNoData方法负责在序列化流未将给定类作为要反序列化的对象的超类列出的情况下初始化其特定类的对象的状态。
 * 这可能发生在接收方使用与发送方不同的反序列化实例的类的版本,并且接收方的版本扩展了未被发送方的版本扩展的类的情况下。
 * 如果串行化流已经被篡改,这也可能发生;因此,readObjectNoData对于正确初始化反序列化对象非常有用,尽管有"敌意"或不完整的源流。
 * 
 * <p>当将对象写入流时需要指定替代对象的可序列化类应该使用具有完全相同签名的特殊方法：
 * 
 * <PRE>
 *  ANY-ACCESS-MODIFIER对象writeReplace()throws ObjectStreamException; </PRE> <p>
 * 
 * @author  unascribed
 * @see java.io.ObjectOutputStream
 * @see java.io.ObjectInputStream
 * @see java.io.ObjectOutput
 * @see java.io.ObjectInput
 * @see java.io.Externalizable
 * @since   JDK1.1
 */
public interface Serializable {
}
