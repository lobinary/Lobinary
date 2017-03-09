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
  * This class represents the address of a communications end-point.
  * It consists of a type that describes the communication mechanism
  * and an address contents determined by an RefAddr subclass.
  *<p>
  * For example, an address type could be "BSD Printer Address",
  * which specifies that it is an address to be used with the BSD printing
  * protocol. Its contents could be the machine name identifying the
  * location of the printer server that understands this protocol.
  *<p>
  * A RefAddr is contained within a Reference.
  *<p>
  * RefAddr is an abstract class. Concrete implementations of it
  * determine its synchronization properties.
  *
  * <p>
  *  该类表示通信端点的地址。它由描述通信机制的类型和由RefAddr子类确定的地址内容组成。
  * p>
  *  例如,地址类型可以是"BSD打印机地址",其指定它是要与BSD打印协议一起使用的地址。其内容可以是标识理解此协议的打印机服务器的位置的机器名称。
  * p>
  *  RefAddr包含在引用中。
  * p>
  *  RefAddr是一个抽象类。它的具体实现确定其同步属性。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  *
  * @see Reference
  * @see LinkRef
  * @see StringRefAddr
  * @see BinaryRefAddr
  * @since 1.3
  */

  /*<p>
  * The serialized form of a RefAddr object consists of only its type name
  * String.
  * <p>
  *  RefAddr对象的序列化形式仅由其类型名称String组成。
  * 
  */

public abstract class RefAddr implements java.io.Serializable {
    /**
     * Contains the type of this address.
     * <p>
     *  包含此地址的类型。
     * 
     * 
     * @serial
     */
    protected String addrType;

    /**
      * Constructs a new instance of RefAddr using its address type.
      *
      * <p>
      *  使用其地址类型构造RefAddr的新实例。
      * 
      * 
      * @param addrType A non-null string describing the type of the address.
      */
    protected RefAddr(String addrType) {
        this.addrType = addrType;
    }

    /**
      * Retrieves the address type of this address.
      *
      * <p>
      *  检索此地址的地址类型。
      * 
      * 
      * @return The non-null address type of this address.
      */
    public String getType() {
        return addrType;
    }

    /**
      * Retrieves the contents of this address.
      *
      * <p>
      *  检索此地址的内容。
      * 
      * 
      * @return The possibly null address contents.
      */
    public abstract Object getContent();

    /**
      * Determines whether obj is equal to this RefAddr.
      *<p>
      * obj is equal to this RefAddr all of these conditions are true
      *<ul>
      *<li> non-null
      *<li> instance of RefAddr
      *<li> obj has the same address type as this RefAddr (using String.compareTo())
      *<li> both obj and this RefAddr's contents are null or they are equal
      *         (using the equals() test).
      *</ul>
      * <p>
      *  确定obj是否等于此RefAddr。
      * p>
      *  obj等于这个RefAddr,所有这些条件都为真
      * ul>
      *  li> obj与RefAddr的地址类型相同(使用String.compareTo())li> both obj和RefAddr的内容都是null或者它们相等(使用equals()测试)。
      * /ul>
      * 
      * @param obj possibly null obj to check.
      * @return true if obj is equal to this refaddr; false otherwise.
      * @see #getContent
      * @see #getType
      */
    public boolean equals(Object obj) {
        if ((obj != null) && (obj instanceof RefAddr)) {
            RefAddr target = (RefAddr)obj;
            if (addrType.compareTo(target.addrType) == 0) {
                Object thisobj = this.getContent();
                Object thatobj = target.getContent();
                if (thisobj == thatobj)
                    return true;
                if (thisobj != null)
                    return thisobj.equals(thatobj);
            }
        }
        return false;
    }

    /**
      * Computes the hash code of this address using its address type and contents.
      * The hash code is the sum of the hash code of the address type and
      * the hash code of the address contents.
      *
      * <p>
      * 使用其地址类型和内容计算此地址的哈希码。哈希码是地址类型的哈希码和地址内容的哈希码的和。
      * 
      * 
      * @return The hash code of this address as an int.
      * @see java.lang.Object#hashCode
      */
    public int hashCode() {
        return (getContent() == null)
                ? addrType.hashCode()
                : addrType.hashCode() + getContent().hashCode();
    }

    /**
      * Generates the string representation of this address.
      * The string consists of the address's type and contents with labels.
      * This representation is intended for display only and not to be parsed.
      * <p>
      *  生成此地址的字符串表示形式。字符串由地址的类型和带标签的内容组成。此表示仅用于显示,不用于解析。
      * 
      * 
      * @return The non-null string representation of this address.
      */
    public String toString(){
        StringBuffer str = new StringBuffer("Type: " + addrType + "\n");

        str.append("Content: " + getContent() + "\n");
        return (str.toString());
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = -1468165120479154358L;
}
