/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.sql.rowset.serial;

import java.sql.*;
import java.io.*;
import java.util.*;

/**
 * A serialized mapping of a <code>Ref</code> object, which is the mapping in the
 * Java programming language of an SQL <code>REF</code> value.
 * <p>
 * The <code>SerialRef</code> class provides a constructor  for
 * creating a <code>SerialRef</code> instance from a <code>Ref</code>
 * object and provides methods for getting and setting the <code>Ref</code> object.
 *
 * <h3> Thread safety </h3>
 *
 * A SerialRef is not safe for use by multiple concurrent threads.  If a
 * SerialRef is to be used by more than one thread then access to the SerialRef
 * should be controlled by appropriate synchronization.
 *
 * <p>
 *  <code> Ref </code>对象的序列化映射,它是Java <code> REF </code>值的Java编程语言中的映射。
 * <p>
 *  <code> SerialRef </code>类提供了从<code> Ref </code>对象创建<code> SerialRef </code>实例的构造函数,并提供了获取和设置<code> R
 * ef <代码>对象。
 * 
 *  <h3>线程安全</h3>
 * 
 *  SerialRef不能安全地用于多个并发线程。如果SerialRef要由多个线程使用,则应通过适当的同步来控制对SerialRef的访问。
 * 
 */
public class SerialRef implements Ref, Serializable, Cloneable {

    /**
     * String containing the base type name.
     * <p>
     *  包含基本类型名称的字符串。
     * 
     * 
     * @serial
     */
    private String baseTypeName;

    /**
     * This will store the type <code>Ref</code> as an <code>Object</code>.
     * <p>
     *  这将把类型<code> Ref </code>存储为<code> Object </code>。
     * 
     */
    private Object object;

    /**
     * Private copy of the Ref reference.
     * <p>
     *  参考参考的私人副本。
     * 
     */
    private Ref reference;

    /**
     * Constructs a <code>SerialRef</code> object from the given <code>Ref</code>
     * object.
     *
     * <p>
     *  从给定的<code> Ref </code>对象构造一个<code> SerialRef </code>对象。
     * 
     * 
     * @param ref a Ref object; cannot be <code>null</code>
     * @throws SQLException if a database access occurs; if <code>ref</code>
     *     is <code>null</code>; or if the <code>Ref</code> object returns a
     *     <code>null</code> value base type name.
     * @throws SerialException if an error occurs serializing the <code>Ref</code>
     *     object
     */
    public SerialRef(Ref ref) throws SerialException, SQLException {
        if (ref == null) {
            throw new SQLException("Cannot instantiate a SerialRef object " +
                "with a null Ref object");
        }
        reference = ref;
        object = ref;
        if (ref.getBaseTypeName() == null) {
            throw new SQLException("Cannot instantiate a SerialRef object " +
                "that returns a null base type name");
        } else {
            baseTypeName = ref.getBaseTypeName();
        }
    }

    /**
     * Returns a string describing the base type name of the <code>Ref</code>.
     *
     * <p>
     *  返回描述<code> Ref </code>的基本类型名称的字符串。
     * 
     * 
     * @return a string of the base type name of the Ref
     * @throws SerialException in no Ref object has been set
     */
    public String getBaseTypeName() throws SerialException {
        return baseTypeName;
    }

    /**
     * Returns an <code>Object</code> representing the SQL structured type
     * to which this <code>SerialRef</code> object refers.  The attributes
     * of the structured type are mapped according to the given type map.
     *
     * <p>
     *  返回一个表示此<> SerialRef </code>对象引用的SQL结构类型的<code> Object </code>。根据给定的类型映射映射结构化类型的属性。
     * 
     * 
     * @param map a <code>java.util.Map</code> object containing zero or
     *        more entries, with each entry consisting of 1) a <code>String</code>
     *        giving the fully qualified name of a UDT and 2) the
     *        <code>Class</code> object for the <code>SQLData</code> implementation
     *        that defines how the UDT is to be mapped
     * @return an object instance resolved from the Ref reference and mapped
     *        according to the supplied type map
     * @throws SerialException if an error is encountered in the reference
     *        resolution
     */
    public Object getObject(java.util.Map<String,Class<?>> map)
        throws SerialException
    {
        map = new Hashtable<String, Class<?>>(map);
        if (object != null) {
            return map.get(object);
        } else {
            throw new SerialException("The object is not set");
        }
    }

    /**
     * Returns an <code>Object</code> representing the SQL structured type
     * to which this <code>SerialRef</code> object refers.
     *
     * <p>
     *  返回一个表示此<> SerialRef </code>对象引用的SQL结构类型的<code> Object </code>。
     * 
     * 
     * @return an object instance resolved from the Ref reference
     * @throws SerialException if an error is encountered in the reference
     *         resolution
     */
    public Object getObject() throws SerialException {

        if (reference != null) {
            try {
                return reference.getObject();
            } catch (SQLException e) {
                throw new SerialException("SQLException: " + e.getMessage());
            }
        }

        if (object != null) {
            return object;
        }


        throw new SerialException("The object is not set");

    }

    /**
     * Sets the SQL structured type that this <code>SerialRef</code> object
     * references to the given <code>Object</code> object.
     *
     * <p>
     *  设置此<code> SerialRef </code>对象引用给定的<code> Object </code>对象的SQL结构类型。
     * 
     * 
     * @param obj an <code>Object</code> representing the SQL structured type
     *        to be referenced
     * @throws SerialException if an error is encountered generating the
     * the structured type referenced by this <code>SerialRef</code> object
     */
    public void setObject(Object obj) throws SerialException {
        try {
            reference.setObject(obj);
        } catch (SQLException e) {
            throw new SerialException("SQLException: " + e.getMessage());
        }
        object = obj;
    }

    /**
     * Compares this SerialRef to the specified object.  The result is {@code
     * true} if and only if the argument is not {@code null} and is a {@code
     * SerialRef} object that represents the same object as this
     * object.
     *
     * <p>
     * 将此SerialRef与指定的对象进行比较。结果是{@code true}当且仅当参数不是{@code null},并且是一个代表与此对象相同的对象的{@code SerialRef}对象。
     * 
     * 
     * @param  obj The object to compare this {@code SerialRef} against
     *
     * @return  {@code true} if the given object represents a {@code SerialRef}
     *          equivalent to this SerialRef, {@code false} otherwise
     *
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if(obj instanceof SerialRef) {
            SerialRef ref = (SerialRef)obj;
            return baseTypeName.equals(ref.baseTypeName) &&
                    object.equals(ref.object);
        }
        return false;
    }

    /**
     * Returns a hash code for this {@code SerialRef}.
     * <p>
     *  返回此{@code SerialRef}的哈希码。
     * 
     * 
     * @return  a hash code value for this object.
     */
    public int hashCode() {
        return (31 + object.hashCode()) * 31 + baseTypeName.hashCode();
    }

    /**
     * Returns a clone of this {@code SerialRef}.
     * The underlying {@code Ref} object will be set to null.
     *
     * <p>
     *  返回此{@code SerialRef}的克隆。底层的{@code Ref}对象将被设置为null。
     * 
     * 
     * @return  a clone of this SerialRef
     */
    public Object clone() {
        try {
            SerialRef ref = (SerialRef) super.clone();
            ref.reference = null;
            return ref;
        } catch (CloneNotSupportedException ex) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError();
        }

    }

    /**
     * readObject is called to restore the state of the SerialRef from
     * a stream.
     * <p>
     *  readObject被调用以从流中恢复SerialRef的状态。
     * 
     */
    private void readObject(ObjectInputStream s)
            throws IOException, ClassNotFoundException {
        ObjectInputStream.GetField fields = s.readFields();
        object = fields.get("object", null);
        baseTypeName = (String) fields.get("baseTypeName", null);
        reference = (Ref) fields.get("reference", null);
    }

    /**
     * writeObject is called to save the state of the SerialRef
     * to a stream.
     * <p>
     *  writeObject被调用以将SerialRef的状态保存到流。
     * 
     */
    private void writeObject(ObjectOutputStream s)
            throws IOException, ClassNotFoundException {

        ObjectOutputStream.PutField fields = s.putFields();
        fields.put("baseTypeName", baseTypeName);
        fields.put("object", object);
        // Note: this check to see if it is an instance of Serializable
        // is for backwards compatibiity
        fields.put("reference", reference instanceof Serializable ? reference : null);
        s.writeFields();
    }

    /**
     * The identifier that assists in the serialization of this <code>SerialRef</code>
     * object.
     * <p>
     *  有助于序列化此<code> SerialRef </code>对象的标识符。
     */
    static final long serialVersionUID = -4727123500609662274L;


}
