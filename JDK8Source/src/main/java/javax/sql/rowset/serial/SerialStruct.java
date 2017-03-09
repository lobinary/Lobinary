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
import javax.sql.*;
import java.io.*;
import java.math.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Vector;

import javax.sql.rowset.*;

/**
 * A serialized mapping in the Java programming language of an SQL
 * structured type. Each attribute that is not already serialized
 * is mapped to a serialized form, and if an attribute is itself
 * a structured type, each of its attributes that is not already
 * serialized is mapped to a serialized form.
 * <P>
 * In addition, the structured type is custom mapped to a class in the
 * Java programming language if there is such a mapping, as are
 * its attributes, if appropriate.
 * <P>
 * The <code>SerialStruct</code> class provides a constructor for creating
 * an instance from a <code>Struct</code> object, a method for retrieving
 * the SQL type name of the SQL structured type in the database, and methods
 * for retrieving its attribute values.
 *
 * <h3> Thread safety </h3>
 *
 * A SerialStruct is not safe for use by multiple concurrent threads.  If a
 * SerialStruct is to be used by more than one thread then access to the
 * SerialStruct should be controlled by appropriate synchronization.
 *
 * <p>
 *  Java结构化类型的Java编程语言中的序列化映射。尚未序列化的每个属性都将映射到序列化表单,如果属性本身是结构化类型,则尚未序列化的每个属性都将映射到序列化表单。
 * <P>
 *  另外,如果存在这样的映射,则结构化类型被定制地映射到Java编程语言中的类,如果适当的话,它的属性也是如此。
 * <P>
 *  <code> SerialStruct </code>类提供了从<code> Struct </code>对象创建实例的构造函数,用于检索数据库中SQL结构类型的SQL类型名称的方法,以及检索其属性值
 * 。
 * 
 *  <h3>线程安全</h3>
 * 
 *  SerialStruct对于多个并发线程是不安全的。如果一个SerialStruct要被多个线程使用,则应该通过适当的同步来控制对SerialStruct的访问。
 * 
 */
public class SerialStruct implements Struct, Serializable, Cloneable {


    /**
     * The SQL type name for the structured type that this
     * <code>SerialStruct</code> object represents.  This is the name
     * used in the SQL definition of the SQL structured type.
     *
     * <p>
     *  SerialStruct </code>对象表示的结构化类型的SQL类型名称。这是在SQL结构化类型的SQL定义中使用的名称。
     * 
     * 
     * @serial
     */
    private String SQLTypeName;

    /**
     * An array of <code>Object</code> instances in  which each
     * element is an attribute of the SQL structured type that this
     * <code>SerialStruct</code> object represents.  The attributes are
     * ordered according to their order in the definition of the
     * SQL structured type.
     *
     * <p>
     * 一个<code> Object </code>实例的数组,其中每个元素都是这个<code> SerialStruct </code>对象表示的SQL结构类型的属性。
     * 属性根据它们在SQL结构化类型的定义中的顺序排序。
     * 
     * 
     * @serial
     */
    private Object attribs[];

    /**
     * Constructs a <code>SerialStruct</code> object from the given
     * <code>Struct</code> object, using the given <code>java.util.Map</code>
     * object for custom mapping the SQL structured type or any of its
     * attributes that are SQL structured types.
     *
     * <p>
     *  从给定的<code> Struct </code>对象构造一个<code> SerialStruct </code>对象,使用给定的<code> java.util.Map </code>对象自定义映
     * 射SQL结构类型或任何其属性是SQL结构化类型。
     * 
     * 
     * @param in an instance of {@code Struct}
     * @param map a <code>java.util.Map</code> object in which
     *        each entry consists of 1) a <code>String</code> object
     *        giving the fully qualified name of a UDT and 2) the
     *        <code>Class</code> object for the <code>SQLData</code> implementation
     *        that defines how the UDT is to be mapped
     * @throws SerialException if an error occurs
     * @see java.sql.Struct
     */
     public SerialStruct(Struct in, Map<String,Class<?>> map)
         throws SerialException
     {

        try {

        // get the type name
        SQLTypeName = in.getSQLTypeName();
        System.out.println("SQLTypeName: " + SQLTypeName);

        // get the attributes of the struct
        attribs = in.getAttributes(map);

        /*
         * the array may contain further Structs
         * and/or classes that have been mapped,
         * other types that we have to serialize
         * <p>
         *  该数组可以包含已经映射的另外的结构和/或类,我们必须序列化的其他类型
         * 
         */
        mapToSerial(map);

        } catch (SQLException e) {
            throw new SerialException(e.getMessage());
        }
    }

     /**
      * Constructs a <code>SerialStruct</code> object from the
      * given <code>SQLData</code> object, using the given type
      * map to custom map it to a class in the Java programming
      * language.  The type map gives the SQL type and the class
      * to which it is mapped.  The <code>SQLData</code> object
      * defines the class to which the SQL type will be mapped.
      *
      * <p>
      *  从给定的<code> SQLData </code>对象构造一个<code> SerialStruct </code>对象,使用给定类型的映射将其定制映射到Java编程语言中的类。
      * 类型映射提供SQL类型及其映射到的类。 <code> SQLData </code>对象定义SQL类型将映射到的类。
      * 
      * 
      * @param in an instance of the <code>SQLData</code> class
      *           that defines the mapping of the SQL structured
      *           type to one or more objects in the Java programming language
      * @param map a <code>java.util.Map</code> object in which
      *        each entry consists of 1) a <code>String</code> object
      *        giving the fully qualified name of a UDT and 2) the
      *        <code>Class</code> object for the <code>SQLData</code> implementation
      *        that defines how the UDT is to be mapped
      * @throws SerialException if an error occurs
      */
    public SerialStruct(SQLData in, Map<String,Class<?>> map)
        throws SerialException
    {

        try {

        //set the type name
        SQLTypeName = in.getSQLTypeName();

        Vector<Object> tmp = new Vector<>();
        in.writeSQL(new SQLOutputImpl(tmp, map));
        attribs = tmp.toArray();

        } catch (SQLException e) {
            throw new SerialException(e.getMessage());
        }
    }


    /**
     * Retrieves the SQL type name for this <code>SerialStruct</code>
     * object. This is the name used in the SQL definition of the
     * structured type
     *
     * <p>
     *  检索此<code> SerialStruct </code>对象的SQL类型名称。这是结构化类型的SQL定义中使用的名称
     * 
     * 
     * @return a <code>String</code> object representing the SQL
     *         type name for the SQL structured type that this
     *         <code>SerialStruct</code> object represents
     * @throws SerialException if an error occurs
     */
    public String getSQLTypeName() throws SerialException {
        return SQLTypeName;
    }

    /**
     * Retrieves an array of <code>Object</code> values containing the
     * attributes of the SQL structured type that this
     * <code>SerialStruct</code> object represents.
     *
     * <p>
     *  检索包含此<Serial> SerialStruct </code>对象表示的SQL结构类型的属性的<code> Object </code>值的数组。
     * 
     * 
     * @return an array of <code>Object</code> values, with each
     *         element being an attribute of the SQL structured type
     *         that this <code>SerialStruct</code> object represents
     * @throws SerialException if an error occurs
     */
    public Object[]  getAttributes() throws SerialException {
        Object[] val = this.attribs;
        return (val == null) ? null : Arrays.copyOf(val, val.length);
    }

    /**
     * Retrieves the attributes for the SQL structured type that
     * this <code>SerialStruct</code> represents as an array of
     * <code>Object</code> values, using the given type map for
     * custom mapping if appropriate.
     *
     * <p>
     *  检索此<cnt> SerialStruct </code>表示为<code> Object </code>值的数组的SQL结构类型的属性,如果适用,使用给定类型的映射进行自定义映射。
     * 
     * 
     * @param map a <code>java.util.Map</code> object in which
     *        each entry consists of 1) a <code>String</code> object
     *        giving the fully qualified name of a UDT and 2) the
     *        <code>Class</code> object for the <code>SQLData</code> implementation
     *        that defines how the UDT is to be mapped
     * @return an array of <code>Object</code> values, with each
     *         element being an attribute of the SQL structured
     *         type that this <code>SerialStruct</code> object
     *         represents
     * @throws SerialException if an error occurs
     */
    public Object[] getAttributes(Map<String,Class<?>> map)
        throws SerialException
    {
        Object[] val = this.attribs;
        return (val == null) ? null : Arrays.copyOf(val, val.length);
    }


    /**
     * Maps attributes of an SQL structured type that are not
     * serialized to a serialized form, using the given type map
     * for custom mapping when appropriate.  The following types
     * in the Java programming language are mapped to their
     * serialized forms:  <code>Struct</code>, <code>SQLData</code>,
     * <code>Ref</code>, <code>Blob</code>, <code>Clob</code>, and
     * <code>Array</code>.
     * <P>
     * This method is called internally and is not used by an
     * application programmer.
     *
     * <p>
     * 映射未序列化为序列化形式的SQL结构化类型的属性,在适当时使用给定类型的映射进行自定义映射。
     *  Java编程语言中的以下类型映射为其序列化形式：<code> Struct </code>,<code> SQLData </code>,<code> Ref </code>,<code> Blob 
     * </code> ,<code> Clob </code>和<code> Array </code>。
     * 映射未序列化为序列化形式的SQL结构化类型的属性,在适当时使用给定类型的映射进行自定义映射。
     * <P>
     *  此方法在内部被调用,并且不被应用程序员使用。
     * 
     * 
     * @param map a <code>java.util.Map</code> object in which
     *        each entry consists of 1) a <code>String</code> object
     *        giving the fully qualified name of a UDT and 2) the
     *        <code>Class</code> object for the <code>SQLData</code> implementation
     *        that defines how the UDT is to be mapped
     * @throws SerialException if an error occurs
     */
    private void mapToSerial(Map<String,Class<?>> map) throws SerialException {

        try {

        for (int i = 0; i < attribs.length; i++) {
            if (attribs[i] instanceof Struct) {
                attribs[i] = new SerialStruct((Struct)attribs[i], map);
            } else if (attribs[i] instanceof SQLData) {
                attribs[i] = new SerialStruct((SQLData)attribs[i], map);
            } else if (attribs[i] instanceof Blob) {
                attribs[i] = new SerialBlob((Blob)attribs[i]);
            } else if (attribs[i] instanceof Clob) {
                attribs[i] = new SerialClob((Clob)attribs[i]);
            } else if (attribs[i] instanceof Ref) {
                attribs[i] = new SerialRef((Ref)attribs[i]);
            } else if (attribs[i] instanceof java.sql.Array) {
                attribs[i] = new SerialArray((java.sql.Array)attribs[i], map);
            }
        }

        } catch (SQLException e) {
            throw new SerialException(e.getMessage());
        }
        return;
    }

    /**
     * Compares this SerialStruct to the specified object.  The result is
     * {@code true} if and only if the argument is not {@code null} and is a
     * {@code SerialStruct} object whose attributes are identical to this
     * object's attributes
     *
     * <p>
     *  将此SerialStruct与指定的对象进行比较。
     * 结果是{@code true}当且仅当参数不是{@code null},并且是一个{@code SerialStruct}对象,其属性与此对象的属性相同。
     * 
     * 
     * @param  obj The object to compare this {@code SerialStruct} against
     *
     * @return {@code true} if the given object represents a {@code SerialStruct}
     *          equivalent to this SerialStruct, {@code false} otherwise
     *
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SerialStruct) {
            SerialStruct ss = (SerialStruct)obj;
            return SQLTypeName.equals(ss.SQLTypeName) &&
                    Arrays.equals(attribs, ss.attribs);
        }
        return false;
    }

    /**
     * Returns a hash code for this {@code SerialStruct}. The hash code for a
     * {@code SerialStruct} object is computed using the hash codes
     * of the attributes of the {@code SerialStruct} object and its
     * {@code SQLTypeName}
     *
     * <p>
     *  返回此{@code SerialStruct}的哈希码。
     *  {@code SerialStruct}对象的哈希码是使用{@code SerialStruct}对象及其{@code SQLTypeName}对象的属性的哈希码计算的,。
     * 
     * 
     * @return  a hash code value for this object.
     */
    public int hashCode() {
        return ((31 + Arrays.hashCode(attribs)) * 31) * 31
                + SQLTypeName.hashCode();
    }

    /**
     * Returns a clone of this {@code SerialStruct}. The copy will contain a
     * reference to a clone of the underlying attribs array, not a reference
     * to the original underlying attribs array of this {@code SerialStruct} object.
     *
     * <p>
     *  返回此{@code SerialStruct}的克隆。副本将包含对基础attribs数组的克隆的引用,而不是对此{@code SerialStruct}对象的原始潜在attribs数组的引用。
     * 
     * 
     * @return  a clone of this SerialStruct
     */
    public Object clone() {
        try {
            SerialStruct ss = (SerialStruct) super.clone();
            ss.attribs = Arrays.copyOf(attribs, attribs.length);
            return ss;
        } catch (CloneNotSupportedException ex) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError();
        }

    }

    /**
     * readObject is called to restore the state of the {@code SerialStruct} from
     * a stream.
     * <p>
     *  readObject被调用来从流中恢复{@code SerialStruct}的状态。
     * 
     */
    private void readObject(ObjectInputStream s)
            throws IOException, ClassNotFoundException {

       ObjectInputStream.GetField fields = s.readFields();
       Object[] tmp = (Object[])fields.get("attribs", null);
       attribs = tmp == null ? null : tmp.clone();
       SQLTypeName = (String)fields.get("SQLTypeName", null);
    }

    /**
     * writeObject is called to save the state of the {@code SerialStruct}
     * to a stream.
     * <p>
     *  writeObject被调用来将{@code SerialStruct}的状态保存到流。
     * 
     */
    private void writeObject(ObjectOutputStream s)
            throws IOException, ClassNotFoundException {

        ObjectOutputStream.PutField fields = s.putFields();
        fields.put("attribs", attribs);
        fields.put("SQLTypeName", SQLTypeName);
        s.writeFields();
    }

    /**
     * The identifier that assists in the serialization of this
     * <code>SerialStruct</code> object.
     * <p>
     *  有助于序列化<code> SerialStruct </code>对象的标识符。
     */
    static final long serialVersionUID = -8322445504027483372L;
}
