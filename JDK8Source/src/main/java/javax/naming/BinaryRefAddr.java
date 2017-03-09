/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.naming;

/**
  * This class represents the binary form of the address of
  * a communications end-point.
  *<p>
  * A BinaryRefAddr consists of a type that describes the communication mechanism
  * and an opaque buffer containing the address description
  * specific to that communication mechanism. The format and interpretation of
  * the address type and the contents of the opaque buffer are based on
  * the agreement of three parties: the client that uses the address,
  * the object/server that can be reached using the address,
  * and the administrator or program that creates the address.
  *<p>
  * An example of a binary reference address is an BER X.500 presentation address.
  * Another example of a binary reference address is a serialized form of
  * a service's object handle.
  *<p>
  * A binary reference address is immutable in the sense that its fields
  * once created, cannot be replaced. However, it is possible to access
  * the byte array used to hold the opaque buffer. Programs are strongly
  * recommended against changing this byte array. Changes to this
  * byte array need to be explicitly synchronized.
  *
  * <p>
  *  该类表示通信端点的地址的二进制形式。
  * p>
  *  BinaryRefAddr包括描述通信机制的类型和包含特定于该通信机制的地址描述的不透明缓冲器。
  * 地址类型和不透明缓冲区的内容的格式和解释基于三方的协议：使用地址的客户端,使用地址可以到达的对象/服务器,以及创建的管理员或程序地址。
  * p>
  *  二进制参考地址的示例是BER X.500呈现地址。二进制引用地址的另一个例子是服务的对象句柄的序列化形式。
  * p>
  *  二进制引用地址在其字段曾经创建的意义上是不可变的,不能被替换。但是,可以访问用于保存不透明缓冲区的字节数组。强烈建议程序不要更改此字节数组。对此字节数组的更改需要显式同步。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  *
  * @see RefAddr
  * @see StringRefAddr
  * @since 1.3
  */

  /*
  * The serialized form of a BinaryRefAddr object consists of its type
  * name String and a byte array containing its "contents".
  * <p>
  *  BinaryRefAddr对象的序列化形式由其类型名称String和包含其"contents"的字节数组组成。
  * 
  */

public class BinaryRefAddr extends RefAddr {
    /**
     * Contains the bytes of the address.
     * This field is initialized by the constructor and returned
     * using getAddressBytes() and getAddressContents().
     * <p>
     *  包含地址的字节。此字段由构造函数初始化,并使用getAddressBytes()和getAddressContents()返回。
     * 
     * 
     * @serial
     */
    private byte[] buf = null;

    /**
      * Constructs a new instance of BinaryRefAddr using its address type and a byte
      * array for contents.
      *
      * <p>
      * 使用其地址类型和内容的字节数组构造BinaryRefAddr的新实例。
      * 
      * 
      * @param addrType A non-null string describing the type of the address.
      * @param src      The non-null contents of the address as a byte array.
      *                 The contents of src is copied into the new BinaryRefAddr.
      */
    public BinaryRefAddr(String addrType, byte[] src) {
        this(addrType, src, 0, src.length);
    }

    /**
      * Constructs a new instance of BinaryRefAddr using its address type and
      * a region of a byte array for contents.
      *
      * <p>
      *  使用其地址类型和内容的字节数组的区域构造BinaryRefAddr的新实例。
      * 
      * 
      * @param addrType A non-null string describing the type of the address.
      * @param src      The non-null contents of the address as a byte array.
      *                 The contents of src is copied into the new BinaryRefAddr.
      * @param offset   The starting index in src to get the bytes.
      *                 {@code 0 <= offset <= src.length}.
      * @param count    The number of bytes to extract from src.
      *                 {@code 0 <= count <= src.length-offset}.
      */
    public BinaryRefAddr(String addrType, byte[] src, int offset, int count) {
        super(addrType);
        buf = new byte[count];
        System.arraycopy(src, offset, buf, 0, count);
    }

    /**
      * Retrieves the contents of this address as an Object.
      * The result is a byte array.
      * Changes to this array will affect this BinaryRefAddr's contents.
      * Programs are recommended against changing this array's contents
      * and to lock the buffer if they need to change it.
      *
      * <p>
      *  作为对象检索此地址的内容。结果是一个字节数组。对此数组的更改将影响此BinaryRefAddr的内容。建议程序不要更改此数组的内容,如果需要更改缓冲区,可以锁定缓冲区。
      * 
      * 
      * @return The non-null buffer containing this address's contents.
      */
    public Object getContent() {
        return buf;
    }


    /**
      * Determines whether obj is equal to this address.  It is equal if
      * it contains the same address type and their contents are byte-wise
      * equivalent.
      * <p>
      *  确定obj是否等于此地址。如果它包含相同的地址类型并且它们的内容是逐字节等效的,则它是相等的。
      * 
      * 
      * @param obj      The possibly null object to check.
      * @return true if the object is equal; false otherwise.
      */
    public boolean equals(Object obj) {
        if ((obj != null) && (obj instanceof BinaryRefAddr)) {
            BinaryRefAddr target = (BinaryRefAddr)obj;
            if (addrType.compareTo(target.addrType) == 0) {
                if (buf == null && target.buf == null)
                    return true;
                if (buf == null || target.buf == null ||
                    buf.length != target.buf.length)
                    return false;
                for (int i = 0; i < buf.length; i++)
                    if (buf[i] != target.buf[i])
                        return false;
                return true;
            }
        }
        return false;
    }

    /**
      * Computes the hash code of this address using its address type and contents.
      * Two BinaryRefAddrs have the same hash code if they have
      * the same address type and the same contents.
      * It is also possible for different BinaryRefAddrs to have
      * the same hash code.
      *
      * <p>
      *  使用其地址类型和内容计算此地址的哈希码。如果两个BinaryRefAddrs具有相同的地址类型和相同的内容,则它们具有相同的哈希码。不同的BinaryRefAddrs也可以具有相同的哈希码。
      * 
      * 
      * @return The hash code of this address as an int.
      */
    public int hashCode() {
        int hash = addrType.hashCode();
        for (int i = 0; i < buf.length; i++) {
            hash += buf[i];     // %%% improve later
        }
        return hash;
    }

    /**
      * Generates the string representation of this address.
      * The string consists of the address's type and contents with labels.
      * The first 32 bytes of contents are displayed (in hexadecimal).
      * If there are more than 32 bytes, "..." is used to indicate more.
      * This string is meant to used for debugging purposes and not
      * meant to be interpreted programmatically.
      * <p>
      *  生成此地址的字符串表示形式。字符串由地址的类型和带标签的内容组成。显示前32个字节的内容(十六进制)。如果有超过32个字节,"..."用于表示更多。这个字符串意在用于调试目的,而不是以编程方式解释。
      * 
      * 
      * @return The non-null string representation of this address.
      */
    public String toString(){
        StringBuffer str = new StringBuffer("Address Type: " + addrType + "\n");

        str.append("AddressContents: ");
        for (int i = 0; i<buf.length && i < 32; i++) {
            str.append(Integer.toHexString(buf[i]) +" ");
        }
        if (buf.length >= 32)
            str.append(" ...\n");
        return (str.toString());
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = -3415254970957330361L;
}
