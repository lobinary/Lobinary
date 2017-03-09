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
 * Constants written into the Object Serialization Stream.
 *
 * <p>
 *  写入对象序列化流的常量。
 * 
 * 
 * @author  unascribed
 * @since JDK 1.1
 */
public interface ObjectStreamConstants {

    /**
     * Magic number that is written to the stream header.
     * <p>
     *  写入流标题的幻数。
     * 
     */
    final static short STREAM_MAGIC = (short)0xaced;

    /**
     * Version number that is written to the stream header.
     * <p>
     *  写入流头的版本号。
     * 
     */
    final static short STREAM_VERSION = 5;

    /* Each item in the stream is preceded by a tag
    /* <p>
     */

    /**
     * First tag value.
     * <p>
     *  第一个标记值。
     * 
     */
    final static byte TC_BASE = 0x70;

    /**
     * Null object reference.
     * <p>
     *  空对象引用。
     * 
     */
    final static byte TC_NULL =         (byte)0x70;

    /**
     * Reference to an object already written into the stream.
     * <p>
     *  引用已写入流中的对象。
     * 
     */
    final static byte TC_REFERENCE =    (byte)0x71;

    /**
     * new Class Descriptor.
     * <p>
     *  新类描述符。
     * 
     */
    final static byte TC_CLASSDESC =    (byte)0x72;

    /**
     * new Object.
     * <p>
     *  新对象。
     * 
     */
    final static byte TC_OBJECT =       (byte)0x73;

    /**
     * new String.
     * <p>
     *  新字符串。
     * 
     */
    final static byte TC_STRING =       (byte)0x74;

    /**
     * new Array.
     * <p>
     *  新阵列。
     * 
     */
    final static byte TC_ARRAY =        (byte)0x75;

    /**
     * Reference to Class.
     * <p>
     *  引用类。
     * 
     */
    final static byte TC_CLASS =        (byte)0x76;

    /**
     * Block of optional data. Byte following tag indicates number
     * of bytes in this block data.
     * <p>
     *  可选数据块。字节跟随标记指示此块数据中的字节数。
     * 
     */
    final static byte TC_BLOCKDATA =    (byte)0x77;

    /**
     * End of optional block data blocks for an object.
     * <p>
     *  对象的可选块数据块的结束。
     * 
     */
    final static byte TC_ENDBLOCKDATA = (byte)0x78;

    /**
     * Reset stream context. All handles written into stream are reset.
     * <p>
     *  重置流上下文。写入流中的所有句柄都将重置。
     * 
     */
    final static byte TC_RESET =        (byte)0x79;

    /**
     * long Block data. The long following the tag indicates the
     * number of bytes in this block data.
     * <p>
     *  长块数据。标记后面的长整数指示此块数据中的字节数。
     * 
     */
    final static byte TC_BLOCKDATALONG= (byte)0x7A;

    /**
     * Exception during write.
     * <p>
     *  写期间异常。
     * 
     */
    final static byte TC_EXCEPTION =    (byte)0x7B;

    /**
     * Long string.
     * <p>
     *  长字符串。
     * 
     */
    final static byte TC_LONGSTRING =   (byte)0x7C;

    /**
     * new Proxy Class Descriptor.
     * <p>
     *  新的代理类描述符。
     * 
     */
    final static byte TC_PROXYCLASSDESC =       (byte)0x7D;

    /**
     * new Enum constant.
     * <p>
     *  新枚举常量。
     * 
     * 
     * @since 1.5
     */
    final static byte TC_ENUM =         (byte)0x7E;

    /**
     * Last tag value.
     * <p>
     *  最后一个标记值。
     * 
     */
    final static byte TC_MAX =          (byte)0x7E;

    /**
     * First wire handle to be assigned.
     * <p>
     *  第一个钢丝手柄要分配。
     * 
     */
    final static int baseWireHandle = 0x7e0000;


    /******************************************************/
    /* Bit masks for ObjectStreamClass flag.*/

    /**
     * Bit mask for ObjectStreamClass flag. Indicates a Serializable class
     * defines its own writeObject method.
     * <p>
     *  ObjectStreamClass标志的位掩码。表示一个Serializable类定义了自己的writeObject方法。
     * 
     */
    final static byte SC_WRITE_METHOD = 0x01;

    /**
     * Bit mask for ObjectStreamClass flag. Indicates Externalizable data
     * written in Block Data mode.
     * Added for PROTOCOL_VERSION_2.
     *
     * <p>
     *  ObjectStreamClass标志的位掩码。表示以块数据模式写入的可外部化数据。已添加到PROTOCOL_VERSION_2。
     * 
     * 
     * @see #PROTOCOL_VERSION_2
     * @since 1.2
     */
    final static byte SC_BLOCK_DATA = 0x08;

    /**
     * Bit mask for ObjectStreamClass flag. Indicates class is Serializable.
     * <p>
     *  ObjectStreamClass标志的位掩码。表示类是Serializable。
     * 
     */
    final static byte SC_SERIALIZABLE = 0x02;

    /**
     * Bit mask for ObjectStreamClass flag. Indicates class is Externalizable.
     * <p>
     *  ObjectStreamClass标志的位掩码。指示类是Externalizable。
     * 
     */
    final static byte SC_EXTERNALIZABLE = 0x04;

    /**
     * Bit mask for ObjectStreamClass flag. Indicates class is an enum type.
     * <p>
     *  ObjectStreamClass标志的位掩码。表示类是枚举类型。
     * 
     * 
     * @since 1.5
     */
    final static byte SC_ENUM = 0x10;


    /* *******************************************************************/
    /* Security permissions */

    /**
     * Enable substitution of one object for another during
     * serialization/deserialization.
     *
     * <p>
     *  在序列化/反序列化期间启用将一个对象替换为另一个对象。
     * 
     * 
     * @see java.io.ObjectOutputStream#enableReplaceObject(boolean)
     * @see java.io.ObjectInputStream#enableResolveObject(boolean)
     * @since 1.2
     */
    final static SerializablePermission SUBSTITUTION_PERMISSION =
                           new SerializablePermission("enableSubstitution");

    /**
     * Enable overriding of readObject and writeObject.
     *
     * <p>
     * 启用覆盖readObject和writeObject。
     * 
     * 
     * @see java.io.ObjectOutputStream#writeObjectOverride(Object)
     * @see java.io.ObjectInputStream#readObjectOverride()
     * @since 1.2
     */
    final static SerializablePermission SUBCLASS_IMPLEMENTATION_PERMISSION =
                    new SerializablePermission("enableSubclassImplementation");
   /**
    * A Stream Protocol Version. <p>
    *
    * All externalizable data is written in JDK 1.1 external data
    * format after calling this method. This version is needed to write
    * streams containing Externalizable data that can be read by
    * pre-JDK 1.1.6 JVMs.
    *
    * <p>
    *  流协议版本。 <p>
    * 
    *  调用此方法后,所有可外化数据都以JDK 1.1外部数据格式写入。需要此版本来编写包含可由JDK 1.1.6 JVM预读的Externalizable数据的流。
    * 
    * 
    * @see java.io.ObjectOutputStream#useProtocolVersion(int)
    * @since 1.2
    */
    public final static int PROTOCOL_VERSION_1 = 1;


   /**
    * A Stream Protocol Version. <p>
    *
    * This protocol is written by JVM 1.2.
    *
    * Externalizable data is written in block data mode and is
    * terminated with TC_ENDBLOCKDATA. Externalizable class descriptor
    * flags has SC_BLOCK_DATA enabled. JVM 1.1.6 and greater can
    * read this format change.
    *
    * Enables writing a nonSerializable class descriptor into the
    * stream. The serialVersionUID of a nonSerializable class is
    * set to 0L.
    *
    * <p>
    *  流协议版本。 <p>
    * 
    *  此协议由JVM 1.2编写。
    * 
    * 
    * @see java.io.ObjectOutputStream#useProtocolVersion(int)
    * @see #SC_BLOCK_DATA
    * @since 1.2
    */
    public final static int PROTOCOL_VERSION_2 = 2;
}
