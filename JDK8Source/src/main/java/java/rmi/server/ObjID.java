/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2006, Oracle and/or its affiliates. All rights reserved.
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
package java.rmi.server;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.security.AccessController;
import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicLong;
import sun.security.action.GetPropertyAction;

/**
 * An <code>ObjID</code> is used to identify a remote object exported
 * to an RMI runtime.  When a remote object is exported, it is assigned
 * an object identifier either implicitly or explicitly, depending on
 * the API used to export.
 *
 * <p>The {@link #ObjID()} constructor can be used to generate a unique
 * object identifier.  Such an <code>ObjID</code> is unique over time
 * with respect to the host it is generated on.
 *
 * The {@link #ObjID(int)} constructor can be used to create a
 * "well-known" object identifier.  The scope of a well-known
 * <code>ObjID</code> depends on the RMI runtime it is exported to.
 *
 * <p>An <code>ObjID</code> instance contains an object number (of type
 * <code>long</code>) and an address space identifier (of type
 * {@link UID}).  In a unique <code>ObjID</code>, the address space
 * identifier is unique with respect to a given host over time.  In a
 * well-known <code>ObjID</code>, the address space identifier is
 * equivalent to one returned by invoking the {@link UID#UID(short)}
 * constructor with the value zero.
 *
 * <p>If the system property <code>java.rmi.server.randomIDs</code>
 * is defined to equal the string <code>"true"</code> (case insensitive),
 * then the {@link #ObjID()} constructor will use a cryptographically
 * strong random number generator to choose the object number of the
 * returned <code>ObjID</code>.
 *
 * <p>
 *  <code> ObjID </code>用于标识导出到RMI运行时的远程对象。当导出远程对象时,根据用于导出的API,可以隐式或明确地为其分配对象标识符。
 * 
 *  <p> {@link #ObjID()}构造函数可用于生成唯一的对象标识符。这种<code> ObjID </code>相对于其生成的主机而言随时间是唯一的。
 * 
 *  {@link #ObjID(int)}构造函数可用于创建"众所周知的"对象标识符。众所周知的<code> ObjID </code>的范围取决于导出到的RMI运行时。
 * 
 *  <p> <code> ObjID </code>实例包含对象号(类型为<code> long </code>)和地址空间标识符(类型为{@link UID})。
 * 在唯一的<code> ObjID </code>中,地址空间标识符对于给定主机随时间是唯一的。
 * 在众所周知的<code> ObjID </code>中,地址空间标识符等于通过调用值为零的{@link UID#UID(short)}构造函数返回的地址空间标识符。
 * 
 *  <p>如果系统属性<code> java.rmi.server.randomIDs </code>定义为等于字符串<code>"true"</code>(不区分大小写),则{@link #ObjID )}
 * 构造函数将使用加密强的随机数生成器来选择返回的<code> ObjID </code>的对象编号。
 * 
 * 
 * @author      Ann Wollrath
 * @author      Peter Jones
 * @since       JDK1.1
 */
public final class ObjID implements Serializable {

    /** Object number for well-known <code>ObjID</code> of the registry. */
    public static final int REGISTRY_ID = 0;

    /** Object number for well-known <code>ObjID</code> of the activator. */
    public static final int ACTIVATOR_ID = 1;

    /**
     * Object number for well-known <code>ObjID</code> of
     * the distributed garbage collector.
     * <p>
     * 知名的<code> ObjID </code>的分布式垃圾回收器的对象号。
     * 
     */
    public static final int DGC_ID = 2;

    /** indicate compatibility with JDK 1.1.x version of class */
    private static final long serialVersionUID = -6386392263968365220L;

    private static final AtomicLong nextObjNum = new AtomicLong(0);
    private static final UID mySpace = new UID();
    private static final SecureRandom secureRandom = new SecureRandom();

    /**
    /* <p>
    /* 
     * @serial object number
     * @see #hashCode
     */
    private final long objNum;

    /**
    /* <p>
    /* 
     * @serial address space identifier (unique to host over time)
     */
    private final UID space;

    /**
     * Generates a unique object identifier.
     *
     * <p>If the system property <code>java.rmi.server.randomIDs</code>
     * is defined to equal the string <code>"true"</code> (case insensitive),
     * then this constructor will use a cryptographically
     * strong random number generator to choose the object number of the
     * returned <code>ObjID</code>.
     * <p>
     *  生成唯一的对象标识符。
     * 
     *  <p>如果系统属性<code> java.rmi.server.randomIDs </code>定义为等于<code>"true"</code>(不区分大小写),则此构造函数将使用加密强随机数生成器
     * 选择返回的<code> ObjID </code>的对象编号。
     * 
     */
    public ObjID() {
        /*
         * If generating random object numbers, create a new UID to
         * ensure uniqueness; otherwise, use a shared UID because
         * sequential object numbers already ensure uniqueness.
         * <p>
         *  如果生成随机对象号,请创建新的UID以确保唯一性;否则,使用共享UID,因为顺序对象号已经保证唯一性。
         * 
         */
        if (useRandomIDs()) {
            space = new UID();
            objNum = secureRandom.nextLong();
        } else {
            space = mySpace;
            objNum = nextObjNum.getAndIncrement();
        }
    }

    /**
     * Creates a "well-known" object identifier.
     *
     * <p>An <code>ObjID</code> created via this constructor will not
     * clash with any <code>ObjID</code>s generated via the no-arg
     * constructor.
     *
     * <p>
     *  创建一个"众所周知的"对象标识符。
     * 
     *  <p>通过此构造函数创建的<code> ObjID </code>不会与通过no-arg构造函数生成的任何<code> ObjID </code>冲突。
     * 
     * 
     * @param   objNum object number for well-known object identifier
     */
    public ObjID(int objNum) {
        space = new UID((short) 0);
        this.objNum = objNum;
    }

    /**
     * Constructs an object identifier given data read from a stream.
     * <p>
     *  构造一个给定从流读取的数据的对象标识符。
     * 
     */
    private ObjID(long objNum, UID space) {
        this.objNum = objNum;
        this.space = space;
    }

    /**
     * Marshals a binary representation of this <code>ObjID</code> to
     * an <code>ObjectOutput</code> instance.
     *
     * <p>Specifically, this method first invokes the given stream's
     * {@link ObjectOutput#writeLong(long)} method with this object
     * identifier's object number, and then it writes its address
     * space identifier by invoking its {@link UID#write(DataOutput)}
     * method with the stream.
     *
     * <p>
     *  将此<code> ObjID </code>的二进制表示封装到<code> ObjectOutput </code>实例中。
     * 
     *  具体来说,该方法首先使用该对象标识符的对象编号调用给定流的{@link ObjectOutput#writeLong(long)}方法,然后通过调用其{@link UID#write(DataOutput) }
     * 方法与流。
     * 
     * 
     * @param   out the <code>ObjectOutput</code> instance to write
     * this <code>ObjID</code> to
     *
     * @throws  IOException if an I/O error occurs while performing
     * this operation
     */
    public void write(ObjectOutput out) throws IOException {
        out.writeLong(objNum);
        space.write(out);
    }

    /**
     * Constructs and returns a new <code>ObjID</code> instance by
     * unmarshalling a binary representation from an
     * <code>ObjectInput</code> instance.
     *
     * <p>Specifically, this method first invokes the given stream's
     * {@link ObjectInput#readLong()} method to read an object number,
     * then it invokes {@link UID#read(DataInput)} with the
     * stream to read an address space identifier, and then it
     * creates and returns a new <code>ObjID</code> instance that
     * contains the object number and address space identifier that
     * were read from the stream.
     *
     * <p>
     *  通过从<code> ObjectInput </code>实例解组合二进制表示,构造并返回一个新的<code> ObjID </code>实例。
     * 
     * <p>具体来说,此方法首先调用给定流的{@link ObjectInput#readLong()}方法读取对象号,然后使用流调用{@link UID#read(DataInput)}以读取地址空间标识符
     * ,然后创建并返回一个新的<code> ObjID </code>实例,该实例包含从流中读取的对象编号和地址空间标识符。
     * 
     * 
     * @param   in the <code>ObjectInput</code> instance to read
     * <code>ObjID</code> from
     *
     * @return  unmarshalled <code>ObjID</code> instance
     *
     * @throws  IOException if an I/O error occurs while performing
     * this operation
     */
    public static ObjID read(ObjectInput in) throws IOException {
        long num = in.readLong();
        UID space = UID.read(in);
        return new ObjID(num, space);
    }

    /**
     * Returns the hash code value for this object identifier, the
     * object number.
     *
     * <p>
     *  返回此对象标识符的对象编号的哈希码值。
     * 
     * 
     * @return  the hash code value for this object identifier
     */
    public int hashCode() {
        return (int) objNum;
    }

    /**
     * Compares the specified object with this <code>ObjID</code> for
     * equality.
     *
     * This method returns <code>true</code> if and only if the
     * specified object is an <code>ObjID</code> instance with the same
     * object number and address space identifier as this one.
     *
     * <p>
     *  将指定的对象与此<code> ObjID </code>进行比较以实现相等。
     * 
     *  当且仅当指定的对象是具有与此对象号和地址空间标识符相同的对象号和地址空间标识符的<code> ObjID </code>实例时,此方法返回<code> true </code>。
     * 
     * 
     * @param   obj the object to compare this <code>ObjID</code> to
     *
     * @return  <code>true</code> if the given object is equivalent to
     * this one, and <code>false</code> otherwise
     */
    public boolean equals(Object obj) {
        if (obj instanceof ObjID) {
            ObjID id = (ObjID) obj;
            return objNum == id.objNum && space.equals(id.space);
        } else {
            return false;
        }
    }

    /**
     * Returns a string representation of this object identifier.
     *
     * <p>
     *  返回此对象标识符的字符串表示形式。
     * 
     * 
     * @return  a string representation of this object identifier
     */
    /*
     * The address space identifier is only included in the string
     * representation if it does not denote the local address space
     * (or if the randomIDs property was set).
     * <p>
     *  如果地址空间标识符不表示本地地址空间(或者如果设置了randomIDs属性),那么它只包含在字符串表示中。
     */
    public String toString() {
        return "[" + (space.equals(mySpace) ? "" : space + ", ") +
            objNum + "]";
    }

    private static boolean useRandomIDs() {
        String value = AccessController.doPrivileged(
            new GetPropertyAction("java.rmi.server.randomIDs"));
        return value == null ? true : Boolean.parseBoolean(value);
    }
}
