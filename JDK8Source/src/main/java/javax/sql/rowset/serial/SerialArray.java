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
import java.util.Map;
import java.net.URL;
import java.util.Arrays;


/**
 * A serialized version of an <code>Array</code>
 * object, which is the mapping in the Java programming language of an SQL
 * <code>ARRAY</code> value.
 * <P>
 * The <code>SerialArray</code> class provides a constructor for creating
 * a <code>SerialArray</code> instance from an <code>Array</code> object,
 * methods for getting the base type and the SQL name for the base type, and
 * methods for copying all or part of a <code>SerialArray</code> object.
 * <P>
 *
 * Note: In order for this class to function correctly, a connection to the
 * data source
 * must be available in order for the SQL <code>Array</code> object to be
 * materialized (have all of its elements brought to the client server)
 * if necessary. At this time, logical pointers to the data in the data source,
 * such as locators, are not currently supported.
 *
 * <h3> Thread safety </h3>
 *
 * A SerialArray is not safe for use by multiple concurrent threads.  If a
 * SerialArray is to be used by more than one thread then access to the
 * SerialArray should be controlled by appropriate synchronization.
 *
 * <p>
 *  <code> Array </code>对象的序列化版本,它是SQL <code> ARRAY </code>值的Java编程语言中的映射。
 * <P>
 *  <code> SerialArray </code>类提供了一个构造函数,用于从<code> Array </code>对象创建一个<code> SerialArray </code>实例,获取基类型
 * 和基类的SQL名称的方法类型和复制全部或部分<code> SerialArray </code>对象的方法。
 * <P>
 * 
 *  注意：为了使此类正常运行,必须提供与数据源的连接,以便将SQL <code> Array </code>对象实现(将其所有元素提供给客户端服务器)if必要。
 * 此时,目前不支持数据源中的数据(如定位符)的逻辑指针。
 * 
 *  <h3>线程安全</h3>
 * 
 *  SerialArray不能安全地用于多个并发线程。如果一个SerialArray要被多个线程使用,那么对SerialArray的访问应该通过适当的同步来控制。
 * 
 */
public class SerialArray implements Array, Serializable, Cloneable {

    /**
     * A serialized array in which each element is an <code>Object</code>
     * in the Java programming language that represents an element
     * in the SQL <code>ARRAY</code> value.
     * <p>
     *  一个序列化数组,其中每个元素是Java编程语言中的一个<code> Object </code>,表示SQL <code> ARRAY </code>值中的一个元素。
     * 
     * 
     * @serial
     */
    private Object[] elements;

    /**
     * The SQL type of the elements in this <code>SerialArray</code> object.  The
     * type is expressed as one of the constants from the class
     * <code>java.sql.Types</code>.
     * <p>
     *  这个<code> SerialArray </code>对象中的元素的SQL类型。类型表示为<code> java.sql.Types </code>类中的常量之一。
     * 
     * 
     * @serial
     */
    private int baseType;

    /**
     * The type name used by the DBMS for the elements in the SQL <code>ARRAY</code>
     * value that this <code>SerialArray</code> object represents.
     * <p>
     * DBMS为该<code> SerialArray </code>对象表示的SQL <code> ARRAY </code>值中的元素使用的类型名称。
     * 
     * 
     * @serial
     */
    private String baseTypeName;

    /**
     * The number of elements in this <code>SerialArray</code> object, which
     * is also the number of elements in the SQL <code>ARRAY</code> value
     * that this <code>SerialArray</code> object represents.
     * <p>
     *  这个<code> SerialArray </code>对象中的元素数量,也是此<code> SerialArray </code>对象所代表的SQL <code> ARRAY </code>值中的元
     * 素数量。
     * 
     * 
     * @serial
     */
    private int len;

    /**
     * Constructs a new <code>SerialArray</code> object from the given
     * <code>Array</code> object, using the given type map for the custom
     * mapping of each element when the elements are SQL UDTs.
     * <P>
     * This method does custom mapping if the array elements are a UDT
     * and the given type map has an entry for that UDT.
     * Custom mapping is recursive,
     * meaning that if, for instance, an element of an SQL structured type
     * is an SQL structured type that itself has an element that is an SQL
     * structured type, each structured type that has a custom mapping will be
     * mapped according to the given type map.
     * <P>
     * The new <code>SerialArray</code>
     * object contains the same elements as the <code>Array</code> object
     * from which it is built, except when the base type is the SQL type
     * <code>STRUCT</code>, <code>ARRAY</code>, <code>BLOB</code>,
     * <code>CLOB</code>, <code>DATALINK</code> or <code>JAVA_OBJECT</code>.
     * In this case, each element in the new
     * <code>SerialArray</code> object is the appropriate serialized form,
     * that is, a <code>SerialStruct</code>, <code>SerialArray</code>,
     * <code>SerialBlob</code>, <code>SerialClob</code>,
     * <code>SerialDatalink</code>, or <code>SerialJavaObject</code> object.
     * <P>
     * Note: (1) The <code>Array</code> object from which a <code>SerialArray</code>
     * object is created must have materialized the SQL <code>ARRAY</code> value's
     * data on the client before it is passed to the constructor.  Otherwise,
     * the new <code>SerialArray</code> object will contain no data.
     * <p>
     * Note: (2) If the <code>Array</code> contains <code>java.sql.Types.JAVA_OBJECT</code>
     * types, the <code>SerialJavaObject</code> constructor is called where checks
     * are made to ensure this object is serializable.
     * <p>
     * Note: (3) The <code>Array</code> object supplied to this constructor cannot
     * return <code>null</code> for any <code>Array.getArray()</code> methods.
     * <code>SerialArray</code> cannot serialize null array values.
     *
     *
     * <p>
     *  从给定的<code> Array </code>对象构造一个新的<code> SerialArray </code>对象,当元素是SQL UDT时,使用给定类型的映射来定义每个元素的映射。
     * <P>
     *  如果数组元素是UDT,并且给定类型映射具有该UDT的条目,则此方法执行自定义映射。
     * 自定义映射是递归的,这意味着例如,如果一个SQL结构化类型的一个元素是一个SQL结构化类型,它本身具有一个SQL结构化类型的元素,那么每个具有定制映射的结构化类型都将根据给定类型映射。
     * <P>
     *  新的<code> SerialArray </code>对象包含与构建它的<code> Array </code>对象相同的元素,除非基本类型是SQL类型<code> STRUCT </code> <code>
     *  ARRAY </code>,<code> BLOB </code>,<code> CLOB </code>,<code> DATALINK </code>或<code> JAVA_OBJECT </code>
     * 。
     * 在这种情况下,新的<code> SerialArray </code>对象中的每个元素是适当的序列化形式,即<code> SerialStruct </code>,<code> SerialArray 
     * </code> </code>,<code> SerialClob </code>,<code> SerialDatalink </code>或<code> SerialJavaObject </code>
     * 对象。
     * <P>
     * 注意：(1)创建一个<code> SerialArray </code>对象的<code> Array </code>对象必须在客户端上实现SQL <code> ARRAY </code>传递给构造函数
     * 。
     * 否则,新的<code> SerialArray </code>对象将不包含数据。
     * <p>
     *  注意：(2)如果<code> Array </code>包含<code> java.sql.Types.JAVA_OBJECT </code>类型,则会调用<code> SerialJavaObjec
     * t </code>构造函数,这个对象是可序列化的。
     * <p>
     *  注意：(3)提供给此构造函数的<code> Array </code>对象不能为任何<code> Array.getArray()</code>方法返回<code> null </code>。
     *  <code> SerialArray </code>不能序列化空数组值。
     * 
     * 
     * @param array the <code>Array</code> object to be serialized
     * @param map a <code>java.util.Map</code> object in which
     *        each entry consists of 1) a <code>String</code> object
     *        giving the fully qualified name of a UDT (an SQL structured type or
     *        distinct type) and 2) the
     *        <code>Class</code> object for the <code>SQLData</code> implementation
     *        that defines how the UDT is to be mapped. The <i>map</i>
     *        parameter does not have any effect for <code>Blob</code>,
     *        <code>Clob</code>, <code>DATALINK</code>, or
     *        <code>JAVA_OBJECT</code> types.
     * @throws SerialException if an error occurs serializing the
     *        <code>Array</code> object
     * @throws SQLException if a database access error occurs or if the
     *        <i>array</i> or the <i>map</i> values are <code>null</code>
     */
     public SerialArray(Array array, Map<String,Class<?>> map)
         throws SerialException, SQLException
     {

        if ((array == null) || (map == null)) {
            throw new SQLException("Cannot instantiate a SerialArray " +
            "object with null parameters");
        }

        if ((elements = (Object[])array.getArray()) == null) {
             throw new SQLException("Invalid Array object. Calls to Array.getArray() " +
                 "return null value which cannot be serialized");
         }

        elements = (Object[])array.getArray(map);
        baseType = array.getBaseType();
        baseTypeName = array.getBaseTypeName();
        len = elements.length;

        switch (baseType) {
            case java.sql.Types.STRUCT:
                for (int i = 0; i < len; i++) {
                    elements[i] = new SerialStruct((Struct)elements[i], map);
                }
            break;

            case java.sql.Types.ARRAY:
                for (int i = 0; i < len; i++) {
                    elements[i] = new SerialArray((Array)elements[i], map);
                }
            break;

            case java.sql.Types.BLOB:
            for (int i = 0; i < len; i++) {
                elements[i] = new SerialBlob((Blob)elements[i]);
            }
            break;

            case java.sql.Types.CLOB:
                for (int i = 0; i < len; i++) {
                    elements[i] = new SerialClob((Clob)elements[i]);
                }
            break;

            case java.sql.Types.DATALINK:
                for (int i = 0; i < len; i++) {
                    elements[i] = new SerialDatalink((URL)elements[i]);
                }
            break;

            case java.sql.Types.JAVA_OBJECT:
                for (int i = 0; i < len; i++) {
                elements[i] = new SerialJavaObject(elements[i]);
            }
        }
  }

    /**
     * This method frees the {@code SeriableArray} object and releases the
     * resources that it holds. The object is invalid once the {@code free}
     * method is called. <p> If {@code free} is called multiple times, the
     * subsequent calls to {@code free} are treated as a no-op. </P>
     *
     * <p>
     *  这个方法释放了{@code SeriableArray}对象并释放了它所拥有的资源。调用{@code free}方法后,对象无效。
     *  <p>如果{@code free}被多次调用,则对{@code free}的后续调用将被视为无操作。 </P>。
     * 
     * 
     * @throws SQLException if an error occurs releasing the SerialArray's resources
     * @since 1.6
     */
    public void free() throws SQLException {
        if (elements != null) {
            elements = null;
            baseTypeName= null;
        }
    }

    /**
     * Constructs a new <code>SerialArray</code> object from the given
     * <code>Array</code> object.
     * <P>
     * This constructor does not do custom mapping.  If the base type of the array
     * is an SQL structured type and custom mapping is desired, the constructor
     * <code>SerialArray(Array array, Map map)</code> should be used.
     * <P>
     * The new <code>SerialArray</code>
     * object contains the same elements as the <code>Array</code> object
     * from which it is built, except when the base type is the SQL type
     * <code>BLOB</code>,
     * <code>CLOB</code>, <code>DATALINK</code> or <code>JAVA_OBJECT</code>.
     * In this case, each element in the new
     * <code>SerialArray</code> object is the appropriate serialized form,
     * that is, a <code>SerialBlob</code>, <code>SerialClob</code>,
     * <code>SerialDatalink</code>, or <code>SerialJavaObject</code> object.
     * <P>
     * Note: (1) The <code>Array</code> object from which a <code>SerialArray</code>
     * object is created must have materialized the SQL <code>ARRAY</code> value's
     * data on the client before it is passed to the constructor.  Otherwise,
     * the new <code>SerialArray</code> object will contain no data.
     * <p>
     * Note: (2) The <code>Array</code> object supplied to this constructor cannot
     * return <code>null</code> for any <code>Array.getArray()</code> methods.
     * <code>SerialArray</code> cannot serialize <code>null</code> array values.
     *
     * <p>
     *  从给定的<code> Array </code>对象构造一个新的<code> SerialArray </code>对象。
     * <P>
     *  此构造函数不执行自定义映射。如果数组的基本类型是SQL结构化类型,并且需要自定义映射,则应使用构造函数<code> SerialArray(Array array,Map map)</code>。
     * <P>
     * 新的<code> SerialArray </code>对象包含与构建它的<code> Array </code>对象相同的元素,除非基本类型是SQL类型<code> BLOB </code> <code>
     *  CLOB </code>,<code> DATALINK </code>或<code> JAVA_OBJECT </code>。
     * 在这种情况下,新的<code> SerialArray </code>对象中的每个元素是适当的序列化形式,即<code> SerialBlob </code>,<code> SerialClob </code>
     *  </code>或<code> SerialJavaObject </code>对象。
     * <P>
     *  注意：(1)创建一个<code> SerialArray </code>对象的<code> Array </code>对象必须在客户端上实现SQL <code> ARRAY </code>传递给构造函
     * 数。
     * 否则,新的<code> SerialArray </code>对象将不包含数据。
     * <p>
     *  注意：(2)提供给此构造函数的<code> Array </code>对象不能返回任何<code> Array.getArray()</code>方法的<code> null </code>。
     *  <code> SerialArray </code>不能序列化<code> null </code>数组值。
     * 
     * 
     * @param array the <code>Array</code> object to be serialized
     * @throws SerialException if an error occurs serializing the
     *     <code>Array</code> object
     * @throws SQLException if a database access error occurs or the
     *     <i>array</i> parameter is <code>null</code>.
     */
     public SerialArray(Array array) throws SerialException, SQLException {
         if (array == null) {
             throw new SQLException("Cannot instantiate a SerialArray " +
                 "object with a null Array object");
         }

         if ((elements = (Object[])array.getArray()) == null) {
             throw new SQLException("Invalid Array object. Calls to Array.getArray() " +
                 "return null value which cannot be serialized");
         }

         //elements = (Object[])array.getArray();
         baseType = array.getBaseType();
         baseTypeName = array.getBaseTypeName();
         len = elements.length;

        switch (baseType) {

        case java.sql.Types.BLOB:
            for (int i = 0; i < len; i++) {
                elements[i] = new SerialBlob((Blob)elements[i]);
            }
            break;

        case java.sql.Types.CLOB:
            for (int i = 0; i < len; i++) {
                elements[i] = new SerialClob((Clob)elements[i]);
            }
            break;

        case java.sql.Types.DATALINK:
            for (int i = 0; i < len; i++) {
                elements[i] = new SerialDatalink((URL)elements[i]);
            }
            break;

        case java.sql.Types.JAVA_OBJECT:
            for (int i = 0; i < len; i++) {
                elements[i] = new SerialJavaObject(elements[i]);
            }
            break;

        }


    }

    /**
     * Returns a new array that is a copy of this <code>SerialArray</code>
     * object.
     *
     * <p>
     *  返回一个新的数组,它是这个<code> SerialArray </code>对象的副本。
     * 
     * 
     * @return a copy of this <code>SerialArray</code> object as an
     *         <code>Object</code> in the Java programming language
     * @throws SerialException if an error occurs;
     * if {@code free} had previously been called on this object
     */
    public Object getArray() throws SerialException {
        isValid();
        Object dst = new Object[len];
        System.arraycopy((Object)elements, 0, dst, 0, len);
        return dst;
    }

 //[if an error occurstype map used??]
    /**
     * Returns a new array that is a copy of this <code>SerialArray</code>
     * object, using the given type map for the custom
     * mapping of each element when the elements are SQL UDTs.
     * <P>
     * This method does custom mapping if the array elements are a UDT
     * and the given type map has an entry for that UDT.
     * Custom mapping is recursive,
     * meaning that if, for instance, an element of an SQL structured type
     * is an SQL structured type that itself has an element that is an SQL
     * structured type, each structured type that has a custom mapping will be
     * mapped according to the given type map.
     *
     * <p>
     *  返回一个新的数组,它是这个<code> SerialArray </code>对象的副本,当元素是SQL UDT时,使用给定类型的映射作为每个元素的自定义映射。
     * <P>
     * 如果数组元素是UDT,并且给定类型映射具有该UDT的条目,则此方法执行自定义映射。
     * 自定义映射是递归的,这意味着例如,如果一个SQL结构化类型的一个元素是一个SQL结构化类型,它本身具有一个SQL结构化类型的元素,那么每个具有定制映射的结构化类型都将根据给定类型映射。
     * 
     * 
     * @param map a <code>java.util.Map</code> object in which
     *        each entry consists of 1) a <code>String</code> object
     *        giving the fully qualified name of a UDT and 2) the
     *        <code>Class</code> object for the <code>SQLData</code> implementation
     *        that defines how the UDT is to be mapped
     * @return a copy of this <code>SerialArray</code> object as an
     *         <code>Object</code> in the Java programming language
     * @throws SerialException if an error occurs;
     * if {@code free} had previously been called on this object
     */
    public Object getArray(Map<String, Class<?>> map) throws SerialException {
        isValid();
        Object dst[] = new Object[len];
        System.arraycopy((Object)elements, 0, dst, 0, len);
        return dst;
    }

    /**
     * Returns a new array that is a copy of a slice
     * of this <code>SerialArray</code> object, starting with the
     * element at the given index and containing the given number
     * of consecutive elements.
     *
     * <p>
     *  返回一个新数组,它是这个<code> SerialArray </code>对象的一个​​切片的副本,从给定索引处的元素开始,包含给定数量的连续元素。
     * 
     * 
     * @param index the index into this <code>SerialArray</code> object
     *              of the first element to be copied;
     *              the index of the first element is <code>0</code>
     * @param count the number of consecutive elements to be copied, starting
     *              at the given index
     * @return a copy of the designated elements in this <code>SerialArray</code>
     *         object as an <code>Object</code> in the Java programming language
     * @throws SerialException if an error occurs;
     * if {@code free} had previously been called on this object
     */
    public Object getArray(long index, int count) throws SerialException {
        isValid();
        Object dst = new Object[count];
        System.arraycopy((Object)elements, (int)index, dst, 0, count);
        return dst;
    }

    /**
     * Returns a new array that is a copy of a slice
     * of this <code>SerialArray</code> object, starting with the
     * element at the given index and containing the given number
     * of consecutive elements.
     * <P>
     * This method does custom mapping if the array elements are a UDT
     * and the given type map has an entry for that UDT.
     * Custom mapping is recursive,
     * meaning that if, for instance, an element of an SQL structured type
     * is an SQL structured type that itself has an element that is an SQL
     * structured type, each structured type that has a custom mapping will be
     * mapped according to the given type map.
     *
     * <p>
     *  返回一个新数组,它是这个<code> SerialArray </code>对象的一个​​切片的副本,从给定索引处的元素开始,包含给定数量的连续元素。
     * <P>
     *  如果数组元素是UDT,并且给定类型映射具有该UDT的条目,则此方法执行自定义映射。
     * 自定义映射是递归的,这意味着例如,如果一个SQL结构化类型的一个元素是一个SQL结构化类型,它本身具有一个SQL结构化类型的元素,那么每个具有定制映射的结构化类型都将根据给定类型映射。
     * 
     * 
     * @param index the index into this <code>SerialArray</code> object
     *              of the first element to be copied; the index of the
     *              first element in the array is <code>0</code>
     * @param count the number of consecutive elements to be copied, starting
     *              at the given index
     * @param map a <code>java.util.Map</code> object in which
     *        each entry consists of 1) a <code>String</code> object
     *        giving the fully qualified name of a UDT and 2) the
     *        <code>Class</code> object for the <code>SQLData</code> implementation
     *        that defines how the UDT is to be mapped
     * @return a copy of the designated elements in this <code>SerialArray</code>
     *         object as an <code>Object</code> in the Java programming language
     * @throws SerialException if an error occurs;
     * if {@code free} had previously been called on this object
     */
    public Object getArray(long index, int count, Map<String,Class<?>> map)
        throws SerialException
    {
        isValid();
        Object dst = new Object[count];
        System.arraycopy((Object)elements, (int)index, dst, 0, count);
        return dst;
    }

    /**
     * Retrieves the SQL type of the elements in this <code>SerialArray</code>
     * object.  The <code>int</code> returned is one of the constants in the class
     * <code>java.sql.Types</code>.
     *
     * <p>
     *  检索此<c> SerialArray </code>对象中的元素的SQL类型。返回的<code> int </code>是<code> java.sql.Types </code>类中的常量之一。
     * 
     * 
     * @return one of the constants in <code>java.sql.Types</code>, indicating
     *         the SQL type of the elements in this <code>SerialArray</code> object
     * @throws SerialException if an error occurs;
     * if {@code free} had previously been called on this object
     */
    public int getBaseType() throws SerialException {
        isValid();
        return baseType;
    }

    /**
     * Retrieves the DBMS-specific type name for the elements in this
     * <code>SerialArray</code> object.
     *
     * <p>
     *  检索此<serial> SerialArray </code>对象中的元素的特定于DBMS的类型名称。
     * 
     * 
     * @return the SQL type name used by the DBMS for the base type of this
     *         <code>SerialArray</code> object
     * @throws SerialException if an error occurs;
     * if {@code free} had previously been called on this object
     */
    public String getBaseTypeName() throws SerialException {
        isValid();
        return baseTypeName;
    }

    /**
     * Retrieves a <code>ResultSet</code> object holding the elements of
     * the subarray that starts at
     * index <i>index</i> and contains up to <i>count</i> successive elements.
     * This method uses the connection's type map to map the elements of
     * the array if the map contains
     * an entry for the base type. Otherwise, the standard mapping is used.
     *
     * <p>
     * 检索保存从索引<i> index </i>开始的子阵列的元素并包含最多<i> count </i>个连续元素的<code> ResultSet </code>对象。
     * 如果映射包含基本类型的条目,则此方法使用连接的类型映射来映射数组的元素。否则,使用标准映射。
     * 
     * 
     * @param index the index into this <code>SerialArray</code> object
     *         of the first element to be copied; the index of the
     *         first element in the array is <code>0</code>
     * @param count the number of consecutive elements to be copied, starting
     *         at the given index
     * @return a <code>ResultSet</code> object containing the designated
     *         elements in this <code>SerialArray</code> object, with a
     *         separate row for each element
     * @throws SerialException if called with the cause set to
     *         {@code UnsupportedOperationException}
     */
    public ResultSet getResultSet(long index, int count) throws SerialException {
        SerialException se = new SerialException();
        se.initCause(new UnsupportedOperationException());
        throw  se;
    }

    /**
     *
     * Retrieves a <code>ResultSet</code> object that contains all of
     * the elements of the SQL <code>ARRAY</code>
     * value represented by this <code>SerialArray</code> object. This method uses
     * the specified map for type map customizations unless the base type of the
     * array does not match a user-defined type (UDT) in <i>map</i>, in
     * which case it uses the
     * standard mapping. This version of the method <code>getResultSet</code>
     * uses either the given type map or the standard mapping; it never uses the
     * type map associated with the connection.
     *
     * <p>
     *  检索包含由此<code> SerialArray </code>对象表示的SQL <code> ARRAY </code>值的所有元素的<code> ResultSet </code>对象。
     * 此方法使用指定的地图进行类型映射自定义,除非数组的基本类型与<i> map </i>中的用户定义类型(UDT)不匹配,在这种情况下它使用标准映射。
     * 方法<code> getResultSet </code>的此版本使用给定类型映射或标准映射;它从不使用与连接关联的类型映射。
     * 
     * 
     * @param map a <code>java.util.Map</code> object in which
     *        each entry consists of 1) a <code>String</code> object
     *        giving the fully qualified name of a UDT and 2) the
     *        <code>Class</code> object for the <code>SQLData</code> implementation
     *        that defines how the UDT is to be mapped
     * @return a <code>ResultSet</code> object containing all of the
     *         elements in this <code>SerialArray</code> object, with a
     *         separate row for each element
     * @throws SerialException if called with the cause set to
     *         {@code UnsupportedOperationException}
     */
    public ResultSet getResultSet(Map<String, Class<?>> map)
        throws SerialException
    {
        SerialException se = new SerialException();
        se.initCause(new UnsupportedOperationException());
        throw  se;
    }

    /**
     * Retrieves a <code>ResultSet</code> object that contains all of
     * the elements in the <code>ARRAY</code> value that this
     * <code>SerialArray</code> object represents.
     * If appropriate, the elements of the array are mapped using the connection's
     * type map; otherwise, the standard mapping is used.
     *
     * <p>
     *  检索包含此<serial> SerialArray </code>对象表示的<code> ARRAY </code>值中所有元素的<code> ResultSet </code>对象。
     * 如果适当,使用连接的类型映射来映射数组的元素;否则,使用标准映射。
     * 
     * 
     * @return a <code>ResultSet</code> object containing all of the
     *         elements in this <code>SerialArray</code> object, with a
     *         separate row for each element
     * @throws SerialException if called with the cause set to
     *         {@code UnsupportedOperationException}
     */
    public ResultSet getResultSet() throws SerialException {
        SerialException se = new SerialException();
        se.initCause(new UnsupportedOperationException());
        throw  se;
    }


    /**
     * Retrieves a result set holding the elements of the subarray that starts at
     * Retrieves a <code>ResultSet</code> object that contains a subarray of the
     * elements in this <code>SerialArray</code> object, starting at
     * index <i>index</i> and containing up to <i>count</i> successive
     * elements. This method uses
     * the specified map for type map customizations unless the base type of the
     * array does not match a user-defined type (UDT) in <i>map</i>, in
     * which case it uses the
     * standard mapping. This version of the method <code>getResultSet</code> uses
     * either the given type map or the standard mapping; it never uses the type
     * map associated with the connection.
     *
     * <p>
     * 检索包含以检索包含此<code> SerialArray </code>对象中元素的子数组的<code> ResultSet </code>对象开始的子数组元素的结果集,从索引<i> </i>并且包含最
     * 多<i>个</i>个连续元素。
     * 此方法使用指定的地图进行类型映射自定义,除非数组的基本类型与<i> map </i>中的用户定义类型(UDT)不匹配,在这种情况下它使用标准映射。
     * 方法<code> getResultSet </code>的此版本使用给定类型映射或标准映射;它从不使用与连接关联的类型映射。
     * 
     * 
     * @param index the index into this <code>SerialArray</code> object
     *              of the first element to be copied; the index of the
     *              first element in the array is <code>0</code>
     * @param count the number of consecutive elements to be copied, starting
     *              at the given index
     * @param map a <code>java.util.Map</code> object in which
     *        each entry consists of 1) a <code>String</code> object
     *        giving the fully qualified name of a UDT and 2) the
     *        <code>Class</code> object for the <code>SQLData</code> implementation
     *        that defines how the UDT is to be mapped
     * @return a <code>ResultSet</code> object containing the designated
     *         elements in this <code>SerialArray</code> object, with a
     *         separate row for each element
     * @throws SerialException if called with the cause set to
     *         {@code UnsupportedOperationException}
     */
    public ResultSet getResultSet(long index, int count,
                                  Map<String,Class<?>> map)
        throws SerialException
    {
        SerialException se = new SerialException();
        se.initCause(new UnsupportedOperationException());
        throw  se;
    }


    /**
     * Compares this SerialArray to the specified object.  The result is {@code
     * true} if and only if the argument is not {@code null} and is a {@code
     * SerialArray} object whose elements are identical to this object's elements
     *
     * <p>
     *  将此SerialArray与指定的对象进行比较。
     * 结果是{@code true}当且仅当参数不是{@code null},并且是一个{@code SerialArray}对象,其元素与此对象的元素相同。
     * 
     * 
     * @param  obj The object to compare this {@code SerialArray} against
     *
     * @return  {@code true} if the given object represents a {@code SerialArray}
     *          equivalent to this SerialArray, {@code false} otherwise
     *
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof SerialArray) {
            SerialArray sa = (SerialArray)obj;
            return baseType == sa.baseType &&
                    baseTypeName.equals(sa.baseTypeName) &&
                    Arrays.equals(elements, sa.elements);
        }
        return false;
    }

    /**
     * Returns a hash code for this SerialArray. The hash code for a
     * {@code SerialArray} object is computed using the hash codes
     * of the elements of the  {@code SerialArray} object
     *
     * <p>
     *  返回此SerialArray的哈希代码。 {@code SerialArray}对象的哈希码是使用{@code SerialArray}对象的元素的哈希码计算的
     * 
     * 
     * @return  a hash code value for this object.
     */
    public int hashCode() {
        return (((31 + Arrays.hashCode(elements)) * 31 + len)  * 31 +
                baseType) * 31 + baseTypeName.hashCode();
    }

    /**
     * Returns a clone of this {@code SerialArray}. The copy will contain a
     * reference to a clone of the underlying objects array, not a reference
     * to the original underlying object array of this {@code SerialArray} object.
     *
     * <p>
     *  返回此{@code SerialArray}的克隆。副本将包含对底层对象数组的克隆的引用,而不是对此{@code SerialArray}对象的原始底层对象数组的引用。
     * 
     * 
     * @return a clone of this SerialArray
     */
    public Object clone() {
        try {
            SerialArray sa = (SerialArray) super.clone();
            sa.elements = (elements != null) ? Arrays.copyOf(elements, len) : null;
            return sa;
        } catch (CloneNotSupportedException ex) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError();
        }

    }

    /**
     * readObject is called to restore the state of the {@code SerialArray} from
     * a stream.
     * <p>
     *  readObject被调用以从流中恢复{@code SerialArray}的状态。
     * 
     */
    private void readObject(ObjectInputStream s)
            throws IOException, ClassNotFoundException {

       ObjectInputStream.GetField fields = s.readFields();
       Object[] tmp = (Object[])fields.get("elements", null);
       if (tmp == null)
           throw new InvalidObjectException("elements is null and should not be!");
       elements = tmp.clone();
       len = fields.get("len", 0);
       if(elements.length != len)
           throw new InvalidObjectException("elements is not the expected size");

       baseType = fields.get("baseType", 0);
       baseTypeName = (String)fields.get("baseTypeName", null);
    }

    /**
     * writeObject is called to save the state of the {@code SerialArray}
     * to a stream.
     * <p>
     *  writeObject被调用来将{@code SerialArray}的状态保存到流。
     * 
     */
    private void writeObject(ObjectOutputStream s)
            throws IOException, ClassNotFoundException {

        ObjectOutputStream.PutField fields = s.putFields();
        fields.put("elements", elements);
        fields.put("len", len);
        fields.put("baseType", baseType);
        fields.put("baseTypeName", baseTypeName);
        s.writeFields();
    }

    /**
     * Check to see if this object had previously had its {@code free} method
     * called
     *
     * <p>
     * 检查此对象之前是否已调用其{@code free}方法
     * 
     * 
     * @throws SerialException
     */
    private void isValid() throws SerialException {
        if (elements == null) {
            throw new SerialException("Error: You cannot call a method on a "
                    + "SerialArray instance once free() has been called.");
        }
    }

    /**
     * The identifier that assists in the serialization of this <code>SerialArray</code>
     * object.
     * <p>
     *  有助于序列化<code> SerialArray </code>对象的标识符。
     */
    static final long serialVersionUID = -8466174297270688520L;
}
