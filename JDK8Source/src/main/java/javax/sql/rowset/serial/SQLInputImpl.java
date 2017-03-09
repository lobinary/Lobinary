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
import java.util.Arrays;
import java.util.Map;
import sun.reflect.misc.ReflectUtil;

/**
 * An input stream used for custom mapping user-defined types (UDTs).
 * An <code>SQLInputImpl</code> object is an input stream that contains a
 * stream of values that are the attributes of a UDT.
 * <p>
 * This class is used by the driver behind the scenes when the method
 * <code>getObject</code> is called on an SQL structured or distinct type
 * that has a custom mapping; a programmer never invokes
 * <code>SQLInputImpl</code> methods directly. They are provided here as a
 * convenience for those who write <code>RowSet</code> implementations.
 * <P>
 * The <code>SQLInputImpl</code> class provides a set of
 * reader methods analogous to the <code>ResultSet</code> getter
 * methods.  These methods make it possible to read the values in an
 * <code>SQLInputImpl</code> object.
 * <P>
 * The method <code>wasNull</code> is used to determine whether the
 * the last value read was SQL <code>NULL</code>.
 * <P>When the method <code>getObject</code> is called with an
 * object of a class implementing the interface <code>SQLData</code>,
 * the JDBC driver calls the method <code>SQLData.getSQLType</code>
 * to determine the SQL type of the UDT being custom mapped. The driver
 * creates an instance of <code>SQLInputImpl</code>, populating it with the
 * attributes of the UDT.  The driver then passes the input
 * stream to the method <code>SQLData.readSQL</code>, which in turn
 * calls the <code>SQLInputImpl</code> reader methods
 * to read the attributes from the input stream.
 * <p>
 *  用于自定义映射用户定义类型(UDT)的输入流。 <code> SQLInputImpl </code>对象是一个输入流,它包含作为UDT属性的值流。
 * <p>
 *  当在具有自定义映射的SQL结构化或不同类型上调用方法<code> getObject </code>时,此类由驱动程序在幕后使用;程序员从不直接调用<code> SQLInputImpl </code>
 * 方法。
 * 在这里提供它们是为了方便那些写<code> RowSet </code>实现的人。
 * <P>
 *  <code> SQLInputImpl </code>类提供了一组类似于<code> ResultSet </code> getter方法的读者方法。
 * 这些方法可以读取<code> SQLInputImpl </code>对象中的值。
 * <P>
 * 方法<code> wasNull </code>用于确定读取的最后一个值是否为SQL <code> NULL </code>。
 * 当使用实现接口<code> SQLData </code>的类的对象调用方法<code> getObject </code>时,JDBC驱动程序调用<code> SQLData.getSQLType </code>
 * 以确定自定义映射的UDT的SQL类型。
 * 方法<code> wasNull </code>用于确定读取的最后一个值是否为SQL <code> NULL </code>。
 * 驱动程序创建一个<code> SQLInputImpl </code>的实例,用UDT的属性填充它。
 * 然后驱动程序将输入流传递给方法<code> SQLData.readSQL </code>,它会调用<code> SQLInputImpl </code>阅读器方法从输入流读取属性。
 * 
 * 
 * @since 1.5
 * @see java.sql.SQLData
 */
public class SQLInputImpl implements SQLInput {

    /**
     * <code>true</code> if the last value returned was <code>SQL NULL</code>;
     * <code>false</code> otherwise.
     * <p>
     *  <code> true </code>如果返回的最后一个值是<code> SQL NULL </code>; <code> false </code>。
     * 
     */
    private boolean lastValueWasNull;

    /**
     * The current index into the array of SQL structured type attributes
     * that will be read from this <code>SQLInputImpl</code> object and
     * mapped to the fields of a class in the Java programming language.
     * <p>
     *  当前索引到SQL结构类型属性数组中,将从此<code> SQLInputImpl </code>对象读取,并映射到Java编程语言中的类的字段。
     * 
     */
    private int idx;

    /**
     * The array of attributes to be read from this stream.  The order
     * of the attributes is the same as the order in which they were
     * listed in the SQL definition of the UDT.
     * <p>
     *  要从此流读取的属性数组。属性的顺序与它们在UDT的SQL定义中列出的顺序相同。
     * 
     */
    private Object attrib[];

    /**
     * The type map to use when the method <code>readObject</code>
     * is invoked. This is a <code>java.util.Map</code> object in which
     * there may be zero or more entries.  Each entry consists of the
     * fully qualified name of a UDT (the value to be mapped) and the
     * <code>Class</code> object for a class that implements
     * <code>SQLData</code> (the Java class that defines how the UDT
     * will be mapped).
     * <p>
     * 当调用方法<code> readObject </code>时使用的类型映射。这是一个<code> java.util.Map </code>对象,其中可能有零个或多个条目。
     * 每个条目由一个实现<code> SQLData </code>的类的一个UDT(要映射的值)和<code> Class </code>对象的完全限定名组成, UDT将被映射)。
     * 
     */
    private Map<String,Class<?>> map;


    /**
     * Creates an <code>SQLInputImpl</code> object initialized with the
     * given array of attributes and the given type map. If any of the
     * attributes is a UDT whose name is in an entry in the type map,
     * the attribute will be mapped according to the corresponding
     * <code>SQLData</code> implementation.
     *
     * <p>
     *  创建使用给定的属性数组和给定类型映射初始化的<code> SQLInputImpl </code>对象。
     * 如果任何属性是其名称在类型映射中的条目中的UDT,则将根据相应的<code> SQLData </code>实现映射该属性。
     * 
     * 
     * @param attributes an array of <code>Object</code> instances in which
     *        each element is an attribute of a UDT. The order of the
     *        attributes in the array is the same order in which
     *        the attributes were defined in the UDT definition.
     * @param map a <code>java.util.Map</code> object containing zero or more
     *        entries, with each entry consisting of 1) a <code>String</code>
     *        giving the fully
     *        qualified name of the UDT and 2) the <code>Class</code> object
     *        for the <code>SQLData</code> implementation that defines how
     *        the UDT is to be mapped
     * @throws SQLException if the <code>attributes</code> or the <code>map</code>
     *        is a <code>null</code> value
     */

    public SQLInputImpl(Object[] attributes, Map<String,Class<?>> map)
        throws SQLException
    {
        if ((attributes == null) || (map == null)) {
            throw new SQLException("Cannot instantiate a SQLInputImpl " +
            "object with null parameters");
        }
        // assign our local reference to the attribute stream
        attrib = Arrays.copyOf(attributes, attributes.length);
        // init the index point before the head of the stream
        idx = -1;
        // set the map
        this.map = map;
    }


    /**
     * Retrieves the next attribute in this <code>SQLInputImpl</code> object
     * as an <code>Object</code> in the Java programming language.
     *
     * <p>
     *  在Java程序设计语言中将<code> SQLInputImpl </code>对象中的下一个属性检索为<code> Object </code>。
     * 
     * 
     * @return the next value in the input stream
     *         as an <code>Object</code> in the Java programming language
     * @throws SQLException if the read position is located at an invalid
     *         position or if there are no further values in the stream
     */
    private Object getNextAttribute() throws SQLException {
        if (++idx >= attrib.length) {
            throw new SQLException("SQLInputImpl exception: Invalid read " +
                                   "position");
        } else {
            lastValueWasNull = attrib[idx] == null;
            return attrib[idx];
        }
    }


    //================================================================
    // Methods for reading attributes from the stream of SQL data.
    // These methods correspond to the column-accessor methods of
    // java.sql.ResultSet.
    //================================================================

    /**
     * Retrieves the next attribute in this <code>SQLInputImpl</code> object as
     * a <code>String</code> in the Java programming language.
     * <p>
     * This method does not perform type-safe checking to determine if the
     * returned type is the expected type; this responsibility is delegated
     * to the UDT mapping as defined by a <code>SQLData</code>
     * implementation.
     * <p>
     * <p>
     *  在Java编程语言中将<code> SQLInputImpl </code>对象中的下一个属性检索为<code> String </code>。
     * <p>
     *  此方法不执行类型安全检查以确定返回的类型是否是预期类型;此职责委托给由<code> SQLData </code>实现定义的UDT映射。
     * <p>
     * 
     * @return the next attribute in this <code>SQLInputImpl</code> object;
     *     if the value is <code>SQL NULL</code>, return <code>null</code>
     * @throws SQLException if the read position is located at an invalid
     *     position or if there are no further values in the stream.
     */
    public String readString() throws SQLException {
        return  (String)getNextAttribute();
    }

    /**
     * Retrieves the next attribute in this <code>SQLInputImpl</code> object as
     * a <code>boolean</code> in the Java programming language.
     * <p>
     * This method does not perform type-safe checking to determine if the
     * returned type is the expected type; this responsibility is delegated
     * to the UDT mapping as defined by a <code>SQLData</code>
     * implementation.
     * <p>
     * <p>
     *  在Java编程语言中将<code> SQLInputImpl </code>对象中的下一个属性检索为<code> boolean </code>。
     * <p>
     *  此方法不执行类型安全检查以确定返回的类型是否是预期类型;此职责委托给由<code> SQLData </code>实现定义的UDT映射。
     * <p>
     * 
     * @return the next attribute in this <code>SQLInputImpl</code> object;
     *     if the value is <code>SQL NULL</code>, return <code>null</code>
     * @throws SQLException if the read position is located at an invalid
     *     position or if there are no further values in the stream.
     */
    public boolean readBoolean() throws SQLException {
        Boolean attrib = (Boolean)getNextAttribute();
        return  (attrib == null) ? false : attrib.booleanValue();
    }

    /**
     * Retrieves the next attribute in this <code>SQLInputImpl</code> object as
     * a <code>byte</code> in the Java programming language.
     * <p>
     * This method does not perform type-safe checking to determine if the
     * returned type is the expected type; this responsibility is delegated
     * to the UDT mapping as defined by a <code>SQLData</code>
     * implementation.
     * <p>
     * <p>
     * 在Java编程语言中将<code> SQLInputImpl </code>对象中的下一个属性检索为<code> byte </code>。
     * <p>
     *  此方法不执行类型安全检查以确定返回的类型是否是预期类型;此职责委托给由<code> SQLData </code>实现定义的UDT映射。
     * <p>
     * 
     * @return the next attribute in this <code>SQLInputImpl</code> object;
     *     if the value is <code>SQL NULL</code>, return <code>null</code>
     * @throws SQLException if the read position is located at an invalid
     *     position or if there are no further values in the stream
     */
    public byte readByte() throws SQLException {
        Byte attrib = (Byte)getNextAttribute();
        return  (attrib == null) ? 0 : attrib.byteValue();
    }

    /**
     * Retrieves the next attribute in this <code>SQLInputImpl</code> object
     * as a <code>short</code> in the Java programming language.
     * <P>
     * This method does not perform type-safe checking to determine if the
     * returned type is the expected type; this responsibility is delegated
     * to the UDT mapping as defined by a <code>SQLData</code> implementation.
     * <P>
     * <p>
     *  在Java编程语言中将<code> SQLInputImpl </code>对象中的下一个属性检索为<code> short </code>。
     * <P>
     *  此方法不执行类型安全检查以确定返回的类型是否是预期类型;此职责委托给由<code> SQLData </code>实现定义的UDT映射。
     * <P>
     * 
     * @return the next attribute in this <code>SQLInputImpl</code> object;
     *       if the value is <code>SQL NULL</code>, return <code>null</code>
     * @throws SQLException if the read position is located at an invalid
     *       position or if there are no more values in the stream
     */
    public short readShort() throws SQLException {
        Short attrib = (Short)getNextAttribute();
        return (attrib == null) ? 0 : attrib.shortValue();
    }

    /**
     * Retrieves the next attribute in this <code>SQLInputImpl</code> object
     * as an <code>int</code> in the Java programming language.
     * <P>
     * This method does not perform type-safe checking to determine if the
     * returned type is the expected type; this responsibility is delegated
     * to the UDT mapping as defined by a <code>SQLData</code> implementation.
     * <P>
     * <p>
     *  在Java编程语言中将<code> SQLInputImpl </code>对象中的下一个属性检索为<code> int </code>。
     * <P>
     *  此方法不执行类型安全检查以确定返回的类型是否是预期类型;此职责委托给由<code> SQLData </code>实现定义的UDT映射。
     * <P>
     * 
     * @return the next attribute in this <code>SQLInputImpl</code> object;
     *       if the value is <code>SQL NULL</code>, return <code>null</code>
     * @throws SQLException if the read position is located at an invalid
     *       position or if there are no more values in the stream
     */
    public int readInt() throws SQLException {
        Integer attrib = (Integer)getNextAttribute();
        return (attrib == null) ? 0 : attrib.intValue();
    }

    /**
     * Retrieves the next attribute in this <code>SQLInputImpl</code> object
     * as a <code>long</code> in the Java programming language.
     * <P>
     * This method does not perform type-safe checking to determine if the
     * returned type is the expected type; this responsibility is delegated
     * to the UDT mapping as defined by a <code>SQLData</code> implementation.
     * <P>
     * <p>
     *  在Java编程语言中将<code> SQLInputImpl </code>对象中的下一个属性检索为<code> long </code>。
     * <P>
     *  此方法不执行类型安全检查以确定返回的类型是否是预期类型;此职责委托给由<code> SQLData </code>实现定义的UDT映射。
     * <P>
     * 
     * @return the next attribute in this <code>SQLInputImpl</code> object;
     *       if the value is <code>SQL NULL</code>, return <code>null</code>
     * @throws SQLException if the read position is located at an invalid
     *       position or if there are no more values in the stream
     */
    public long readLong() throws SQLException {
        Long attrib = (Long)getNextAttribute();
        return (attrib == null) ? 0 : attrib.longValue();
    }

    /**
     * Retrieves the next attribute in this <code>SQLInputImpl</code> object
     * as a <code>float</code> in the Java programming language.
     * <P>
     * This method does not perform type-safe checking to determine if the
     * returned type is the expected type; this responsibility is delegated
     * to the UDT mapping as defined by a <code>SQLData</code> implementation.
     * <P>
     * <p>
     *  在Java编程语言中将<code> SQLInputImpl </code>对象中的下一个属性检索为<code> float </code>。
     * <P>
     * 此方法不执行类型安全检查以确定返回的类型是否是预期类型;此职责委托给由<code> SQLData </code>实现定义的UDT映射。
     * <P>
     * 
     * @return the next attribute in this <code>SQLInputImpl</code> object;
     *       if the value is <code>SQL NULL</code>, return <code>null</code>
     * @throws SQLException if the read position is located at an invalid
     *       position or if there are no more values in the stream
     */
    public float readFloat() throws SQLException {
        Float attrib = (Float)getNextAttribute();
        return (attrib == null) ? 0 : attrib.floatValue();
    }

    /**
     * Retrieves the next attribute in this <code>SQLInputImpl</code> object
     * as a <code>double</code> in the Java programming language.
     * <P>
     * This method does not perform type-safe checking to determine if the
     * returned type is the expected type; this responsibility is delegated
     * to the UDT mapping as defined by a <code>SQLData</code> implementation.
     * <P>
     * <p>
     *  在Java编程语言中将<code> SQLInputImpl </code>对象中的下一个属性检索为<code> double </code>。
     * <P>
     *  此方法不执行类型安全检查以确定返回的类型是否是预期类型;此职责委托给由<code> SQLData </code>实现定义的UDT映射。
     * <P>
     * 
     * @return the next attribute in this <code>SQLInputImpl</code> object;
     *       if the value is <code>SQL NULL</code>, return <code>null</code>
     * @throws SQLException if the read position is located at an invalid
     *       position or if there are no more values in the stream
     */
    public double readDouble() throws SQLException {
        Double attrib = (Double)getNextAttribute();
        return (attrib == null)  ? 0 :  attrib.doubleValue();
    }

    /**
     * Retrieves the next attribute in this <code>SQLInputImpl</code> object
     * as a <code>java.math.BigDecimal</code>.
     * <P>
     * This method does not perform type-safe checking to determine if the
     * returned type is the expected type; this responsibility is delegated
     * to the UDT mapping as defined by a <code>SQLData</code> implementation.
     * <P>
     * <p>
     *  将<code> SQLInputImpl </code>对象中的下一个属性检索为<code> java.math.BigDecimal </code>。
     * <P>
     *  此方法不执行类型安全检查以确定返回的类型是否是预期类型;此职责委托给由<code> SQLData </code>实现定义的UDT映射。
     * <P>
     * 
     * @return the next attribute in this <code>SQLInputImpl</code> object;
     *       if the value is <code>SQL NULL</code>, return <code>null</code>
     * @throws SQLException if the read position is located at an invalid
     *       position or if there are no more values in the stream
     */
    public java.math.BigDecimal readBigDecimal() throws SQLException {
        return (java.math.BigDecimal)getNextAttribute();
    }

    /**
     * Retrieves the next attribute in this <code>SQLInputImpl</code> object
     * as an array of bytes.
     * <p>
     * This method does not perform type-safe checking to determine if the
     * returned type is the expected type; this responsibility is delegated
     * to the UDT mapping as defined by a <code>SQLData</code> implementation.
     * <P>
     * <p>
     *  以字节数组的形式获取此<code> SQLInputImpl </code>对象中的下一个属性。
     * <p>
     *  此方法不执行类型安全检查以确定返回的类型是否是预期类型;此职责委托给由<code> SQLData </code>实现定义的UDT映射。
     * <P>
     * 
     * @return the next attribute in this <code>SQLInputImpl</code> object;
     *       if the value is <code>SQL NULL</code>, return <code>null</code>
     * @throws SQLException if the read position is located at an invalid
     *       position or if there are no more values in the stream
     */
    public byte[] readBytes() throws SQLException {
        return (byte[])getNextAttribute();
    }

    /**
     * Retrieves the next attribute in this <code>SQLInputImpl</code> as
     * a <code>java.sql.Date</code> object.
     * <P>
     * This method does not perform type-safe checking to determine if the
     * returned type is the expected type; this responsibility is delegated
     * to the UDT mapping as defined by a <code>SQLData</code> implementation.
     * <P>
     * <p>
     *  将<code> SQLInputImpl </code>中的下一个属性作为<code> java.sql.Date </code>对象检索。
     * <P>
     *  此方法不执行类型安全检查以确定返回的类型是否是预期类型;此职责委托给由<code> SQLData </code>实现定义的UDT映射。
     * <P>
     * 
     * @return the next attribute in this <code>SQLInputImpl</code> object;
     *       if the value is <code>SQL NULL</code>, return <code>null</code>
     * @throws SQLException if the read position is located at an invalid
     *       position or if there are no more values in the stream
     */
    public java.sql.Date readDate() throws SQLException {
        return (java.sql.Date)getNextAttribute();
    }

    /**
     * Retrieves the next attribute in this <code>SQLInputImpl</code> object as
     * a <code>java.sql.Time</code> object.
     * <P>
     * This method does not perform type-safe checking to determine if the
     * returned type is the expected type as this responsibility is delegated
     * to the UDT mapping as implemented by a <code>SQLData</code>
     * implementation.
     *
     * <p>
     * 将<code> SQLInputImpl </code>对象中的下一个属性作为<code> java.sql.Time </code>对象检索。
     * <P>
     *  此方法不执行类型安全检查以确定返回的类型是否为预期类型,因为此职责被委派给由<code> SQLData </code>实现实现的UDT映射。
     * 
     * 
     * @return the attribute; if the value is <code>SQL NULL</code>, return
     * <code>null</code>
     * @throws SQLException if the read position is located at an invalid
     * position; or if there are no further values in the stream.
     */
    public java.sql.Time readTime() throws SQLException {
        return (java.sql.Time)getNextAttribute();
    }

    /**
     * Retrieves the next attribute in this <code>SQLInputImpl</code> object as
     * a <code>java.sql.Timestamp</code> object.
     *
     * <p>
     *  将<code> SQLInputImpl </code>对象中的下一个属性作为<code> java.sql.Timestamp </code>对象检索。
     * 
     * 
     * @return the attribute; if the value is <code>SQL NULL</code>, return
     * <code>null</code>
     * @throws SQLException if the read position is located at an invalid
     * position; or if there are no further values in the stream.
     */
    public java.sql.Timestamp readTimestamp() throws SQLException {
        return (java.sql.Timestamp)getNextAttribute();
    }

    /**
     * Retrieves the next attribute in this <code>SQLInputImpl</code> object
     * as a stream of Unicode characters.
     * <P>
     * This method does not perform type-safe checking to determine if the
     * returned type is the expected type as this responsibility is delegated
     * to the UDT mapping as implemented by a <code>SQLData</code>
     * implementation.
     *
     * <p>
     *  在此<c>> SQLInputImpl </code>对象中将下一个属性检索为Unicode字符流。
     * <P>
     *  此方法不执行类型安全检查以确定返回的类型是否为预期类型,因为此职责被委派给由<code> SQLData </code>实现实现的UDT映射。
     * 
     * 
     * @return the attribute; if the value is <code>SQL NULL</code>, return <code>null</code>
     * @throws SQLException if the read position is located at an invalid
     * position; or if there are no further values in the stream.
     */
    public java.io.Reader readCharacterStream() throws SQLException {
        return (java.io.Reader)getNextAttribute();
    }

    /**
     * Returns the next attribute in this <code>SQLInputImpl</code> object
     * as a stream of ASCII characters.
     * <P>
     * This method does not perform type-safe checking to determine if the
     * returned type is the expected type as this responsibility is delegated
     * to the UDT mapping as implemented by a <code>SQLData</code>
     * implementation.
     *
     * <p>
     *  以ASCII字符流形式返回此<c> <b> </b> </b> </b>对象中的下一个属性。
     * <P>
     *  此方法不执行类型安全检查以确定返回的类型是否为预期类型,因为此职责被委派给由<code> SQLData </code>实现实现的UDT映射。
     * 
     * 
     * @return the attribute; if the value is <code>SQL NULL</code>,
     * return <code>null</code>
     * @throws SQLException if the read position is located at an invalid
     * position; or if there are no further values in the stream.
     */
    public java.io.InputStream readAsciiStream() throws SQLException {
        return (java.io.InputStream)getNextAttribute();
    }

    /**
     * Returns the next attribute in this <code>SQLInputImpl</code> object
     * as a stream of uninterpreted bytes.
     * <P>
     * This method does not perform type-safe checking to determine if the
     * returned type is the expected type as this responsibility is delegated
     * to the UDT mapping as implemented by a <code>SQLData</code>
     * implementation.
     *
     * <p>
     *  以非解释字节流的形式返回此<code> SQLInputImpl </code>对象中的下一个属性。
     * <P>
     *  此方法不执行类型安全检查以确定返回的类型是否为预期类型,因为此职责被委派给由<code> SQLData </code>实现实现的UDT映射。
     * 
     * 
     * @return the attribute; if the value is <code>SQL NULL</code>, return
     * <code>null</code>
     * @throws SQLException if the read position is located at an invalid
     * position; or if there are no further values in the stream.
     */
    public java.io.InputStream readBinaryStream() throws SQLException {
        return (java.io.InputStream)getNextAttribute();
    }

    //================================================================
    // Methods for reading items of SQL user-defined types from the stream.
    //================================================================

    /**
     * Retrieves the value at the head of this <code>SQLInputImpl</code>
     * object as an <code>Object</code> in the Java programming language.  The
     * actual type of the object returned is determined by the default
     * mapping of SQL types to types in the Java programming language unless
     * there is a custom mapping, in which case the type of the object
     * returned is determined by this stream's type map.
     * <P>
     * The JDBC technology-enabled driver registers a type map with the stream
     * before passing the stream to the application.
     * <P>
     * When the datum at the head of the stream is an SQL <code>NULL</code>,
     * this method returns <code>null</code>.  If the datum is an SQL
     * structured or distinct type with a custom mapping, this method
     * determines the SQL type of the datum at the head of the stream,
     * constructs an object of the appropriate class, and calls the method
     * <code>SQLData.readSQL</code> on that object. The <code>readSQL</code>
     * method then calls the appropriate <code>SQLInputImpl.readXXX</code>
     * methods to retrieve the attribute values from the stream.
     *
     * <p>
     * 以Java程序设计语言中的<code> Object </code>检索此<code> SQLInputImpl </code>对象头部的值。
     * 返回的对象的实际类型由Java编程语言中的SQL类型到类型的默认映射确定,除非有自定义映射,在这种情况下,返回的对象的类型由此流的类型映射确定。
     * <P>
     *  启用JDBC技术的驱动程序在将流传递到应用程序之前向流注册类型映射。
     * <P>
     *  当流头部的数据是SQL <code> NULL </code>时,此方法返回<code> null </code>。
     * 如果数据是具有自定义映射的SQL结构化或不同类型,则此方法确定流头处的数据的SQL类型,构造适当类的对象,并调用方法<code> SQLData.readSQL < / code>。
     * 然后,<code> readSQL </code>方法调用相应的<code> SQLInputImpl.readXXX </code>方法从流中检索属性值。
     * 
     * 
     * @return the value at the head of the stream as an <code>Object</code>
     *         in the Java programming language; <code>null</code> if
     *         the value is SQL <code>NULL</code>
     * @throws SQLException if the read position is located at an invalid
     * position; or if there are no further values in the stream.
     */
    public Object readObject() throws SQLException {
        Object attrib = getNextAttribute();
        if (attrib instanceof Struct) {
            Struct s = (Struct)attrib;
            // look up the class in the map
            Class<?> c = map.get(s.getSQLTypeName());
            if (c != null) {
                // create new instance of the class
                SQLData obj = null;
                try {
                    obj = (SQLData)ReflectUtil.newInstance(c);
                } catch (Exception ex) {
                    throw new SQLException("Unable to Instantiate: ", ex);
                }
                // get the attributes from the struct
                Object attribs[] = s.getAttributes(map);
                // create the SQLInput "stream"
                SQLInputImpl sqlInput = new SQLInputImpl(attribs, map);
                // read the values...
                obj.readSQL(sqlInput, s.getSQLTypeName());
                return obj;
            }
        }
        return attrib;
    }

    /**
     * Retrieves the value at the head of this <code>SQLInputImpl</code> object
     * as a <code>Ref</code> object in the Java programming language.
     *
     * <p>
     *  将此<code> SQLInputImpl </code>对象的头部的值作为Java编程语言中的<code> Ref </code>对象检索。
     * 
     * 
     * @return a <code>Ref</code> object representing the SQL
     *         <code>REF</code> value at the head of the stream; if the value
     *         is <code>SQL NULL</code> return <code>null</code>
     * @throws SQLException if the read position is located at an invalid
     *         position; or if there are no further values in the stream.
     */
    public Ref readRef() throws SQLException {
        return (Ref)getNextAttribute();
    }

    /**
     * Retrieves the <code>BLOB</code> value at the head of this
     * <code>SQLInputImpl</code> object as a <code>Blob</code> object
     * in the Java programming language.
     * <P>
     * This method does not perform type-safe checking to determine if the
     * returned type is the expected type as this responsibility is delegated
     * to the UDT mapping as implemented by a <code>SQLData</code>
     * implementation.
     *
     * <p>
     *  将此代码<code> SQLInputImpl </code>顶部的<code> BLOB </code>值作为Java编程语言中的<code> Blob </code>对象检索。
     * <P>
     * 此方法不执行类型安全检查以确定返回的类型是否为预期类型,因为此职责被委派给由<code> SQLData </code>实现实现的UDT映射。
     * 
     * 
     * @return a <code>Blob</code> object representing the SQL
     *         <code>BLOB</code> value at the head of this stream;
     *         if the value is <code>SQL NULL</code>, return
     *         <code>null</code>
     * @throws SQLException if the read position is located at an invalid
     * position; or if there are no further values in the stream.
     */
    public Blob readBlob() throws SQLException {
        return (Blob)getNextAttribute();
    }

    /**
     * Retrieves the <code>CLOB</code> value at the head of this
     * <code>SQLInputImpl</code> object as a <code>Clob</code> object
     * in the Java programming language.
     * <P>
     * This method does not perform type-safe checking to determine if the
     * returned type is the expected type as this responsibility is delegated
     * to the UDT mapping as implemented by a <code>SQLData</code>
     * implementation.
     *
     * <p>
     *  在Java编程语言中,将<code> SQLInputImpl </code>对象前面的<code> CLOB </code>值作为<code> Clob </code>对象检索。
     * <P>
     *  此方法不执行类型安全检查以确定返回的类型是否为预期类型,因为此职责被委派给由<code> SQLData </code>实现实现的UDT映射。
     * 
     * 
     * @return a <code>Clob</code> object representing the SQL
     *         <code>CLOB</code> value at the head of the stream;
     *         if the value is <code>SQL NULL</code>, return
     *         <code>null</code>
     * @throws SQLException if the read position is located at an invalid
     * position; or if there are no further values in the stream.
     */
    public Clob readClob() throws SQLException {
        return (Clob)getNextAttribute();
    }

    /**
     * Reads an SQL <code>ARRAY</code> value from the stream and
     * returns it as an <code>Array</code> object in the Java programming
     * language.
     * <P>
     * This method does not perform type-safe checking to determine if the
     * returned type is the expected type as this responsibility is delegated
     * to the UDT mapping as implemented by a <code>SQLData</code>
     * implementation.
     *
     * <p>
     *  从流中读取SQL <code> ARRAY </code>值,并将其作为Java编程语言中的<code> Array </code>对象返回。
     * <P>
     *  此方法不执行类型安全检查以确定返回的类型是否为预期类型,因为此职责被委派给由<code> SQLData </code>实现实现的UDT映射。
     * 
     * 
     * @return an <code>Array</code> object representing the SQL
     *         <code>ARRAY</code> value at the head of the stream; *
     *         if the value is <code>SQL NULL</code>, return
     *         <code>null</code>
     * @throws SQLException if the read position is located at an invalid
     * position; or if there are no further values in the stream.

     */
    public Array readArray() throws SQLException {
        return (Array)getNextAttribute();
    }

    /**
     * Ascertains whether the last value read from this
     * <code>SQLInputImpl</code> object was <code>null</code>.
     *
     * <p>
     *  确定从此<code> SQLInputImpl </code>对象读取的最后一个值是<code> null </code>。
     * 
     * 
     * @return <code>true</code> if the SQL value read most recently was
     *         <code>null</code>; otherwise, <code>false</code>; by default it
     *         will return false
     * @throws SQLException if an error occurs determining the last value
     *         read was a <code>null</code> value or not;
     */
    public boolean wasNull() throws SQLException {
        return lastValueWasNull;
    }

    /**
     * Reads an SQL <code>DATALINK</code> value from the stream and
     * returns it as an <code>URL</code> object in the Java programming
     * language.
     * <P>
     * This method does not perform type-safe checking to determine if the
     * returned type is the expected type as this responsibility is delegated
     * to the UDT mapping as implemented by a <code>SQLData</code>
     * implementation.
     *
     * <p>
     *  从流中读取SQL <code> DATALINK </code>值,并将其作为Java编程语言中的<code> URL </code>对象返回。
     * <P>
     *  此方法不执行类型安全检查以确定返回的类型是否为预期类型,因为此职责被委派给由<code> SQLData </code>实现实现的UDT映射。
     * 
     * 
     * @return an <code>URL</code> object representing the SQL
     *         <code>DATALINK</code> value at the head of the stream; *
     *         if the value is <code>SQL NULL</code>, return
     *         <code>null</code>
     * @throws SQLException if the read position is located at an invalid
     * position; or if there are no further values in the stream.
     */
    public java.net.URL readURL() throws SQLException {
        return (java.net.URL)getNextAttribute();
    }

    //---------------------------- JDBC 4.0 -------------------------

    /**
     * Reads an SQL <code>NCLOB</code> value from the stream and returns it as a
     * <code>Clob</code> object in the Java programming language.
     *
     * <p>
     * 从流中读取SQL <code> NCLOB </code>值,并将其作为Java编程语言中的<code> Clob </code>对象返回。
     * 
     * 
     * @return a <code>NClob</code> object representing data of the SQL <code>NCLOB</code> value
     * at the head of the stream; <code>null</code> if the value read is
     * SQL <code>NULL</code>
     * @exception SQLException if a database access error occurs
     * @since 1.6
     */
     public NClob readNClob() throws SQLException {
        return (NClob)getNextAttribute();
     }

    /**
     * Reads the next attribute in the stream and returns it as a <code>String</code>
     * in the Java programming language. It is intended for use when
     * accessing  <code>NCHAR</code>,<code>NVARCHAR</code>
     * and <code>LONGNVARCHAR</code> columns.
     *
     * <p>
     *  读取流中的下一个属性,并以Java编程语言中的<code> String </code>返回它。
     * 它用于访问<code> NCHAR </code>,<code> NVARCHAR </code>和<code> LONGNVARCHAR </code>列。
     * 
     * 
     * @return the attribute; if the value is SQL <code>NULL</code>, returns <code>null</code>
     * @exception SQLException if a database access error occurs
     * @since 1.6
     */
    public String readNString() throws SQLException {
        return (String)getNextAttribute();
    }

    /**
     * Reads an SQL <code>XML</code> value from the stream and returns it as a
     * <code>SQLXML</code> object in the Java programming language.
     *
     * <p>
     *  从流中读取SQL <code> XML </code>值,并将其作为Java编程语言中的<code> SQLXML </code>对象返回。
     * 
     * 
     * @return a <code>SQLXML</code> object representing data of the SQL <code>XML</code> value
     * at the head of the stream; <code>null</code> if the value read is
     * SQL <code>NULL</code>
     * @exception SQLException if a database access error occurs
     * @since 1.6
     */
    public SQLXML readSQLXML() throws SQLException {
        return (SQLXML)getNextAttribute();
    }

    /**
     * Reads an SQL <code>ROWID</code> value from the stream and returns it as a
     * <code>RowId</code> object in the Java programming language.
     *
     * <p>
     *  从流中读取SQL <code> ROWID </code>值,并将其作为Java编程语言中的<code> RowId </code>对象返回。
     * 
     * @return a <code>RowId</code> object representing data of the SQL <code>ROWID</code> value
     * at the head of the stream; <code>null</code> if the value read is
     * SQL <code>NULL</code>
     * @exception SQLException if a database access error occurs
     * @since 1.6
     */
    public RowId readRowId() throws SQLException {
        return  (RowId)getNextAttribute();
    }


}
