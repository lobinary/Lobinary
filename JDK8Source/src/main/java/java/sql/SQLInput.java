/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.sql;

/**
 * An input stream that contains a stream of values representing an
 * instance of an SQL structured type or an SQL distinct type.
 * This interface, used only for custom mapping, is used by the driver
 * behind the scenes, and a programmer never directly invokes
 * <code>SQLInput</code> methods. The <i>reader</i> methods
 * (<code>readLong</code>, <code>readBytes</code>, and so on)
 * provide a way  for an implementation of the <code>SQLData</code>
 *  interface to read the values in an <code>SQLInput</code> object.
 *  And as described in <code>SQLData</code>, calls to reader methods must
 * be made in the order that their corresponding attributes appear in the
 * SQL definition of the type.
 * The method <code>wasNull</code> is used to determine whether
 * the last value read was SQL <code>NULL</code>.
 * <P>When the method <code>getObject</code> is called with an
 * object of a class implementing the interface <code>SQLData</code>,
 * the JDBC driver calls the method <code>SQLData.getSQLType</code>
 * to determine the SQL type of the user-defined type (UDT)
 * being custom mapped. The driver
 * creates an instance of <code>SQLInput</code>, populating it with the
 * attributes of the UDT.  The driver then passes the input
 * stream to the method <code>SQLData.readSQL</code>, which in turn
 * calls the <code>SQLInput</code> reader methods
 * in its implementation for reading the
 * attributes from the input stream.
 * <p>
 * 包含表示SQL结构类型或SQL distinct类型的实例的值流的输入流。此接口仅用于自定义映射,由后台驱动程序使用,并且程序员从不直接调用<code> SQLInput </code>方法。
 *  <i> reader </i>方法(<code> readLong </code>,<code> readBytes </code>等)为实现<code> SQLData </code>接口以读取<code>
 *  SQLInput </code>对象中的值。
 * 包含表示SQL结构类型或SQL distinct类型的实例的值流的输入流。此接口仅用于自定义映射,由后台驱动程序使用,并且程序员从不直接调用<code> SQLInput </code>方法。
 * 并且如<code> SQLData </code>中所述,对读取器方法的调用必须按照其相应属性出现在类型的SQL定义中的顺序进行。
 * 方法<code> wasNull </code>用于确定最后读取的值是否为SQL <code> NULL </code>。
 * 当使用实现接口<code> SQLData </code>的类的对象调用方法<code> getObject </code>时,JDBC驱动程序调用<code> SQLData.getSQLType </code>
 * 以确定自定义映射的用户定义类型(UDT)的SQL类型。
 * 方法<code> wasNull </code>用于确定最后读取的值是否为SQL <code> NULL </code>。
 * 驱动程序创建一个<code> SQLInput </code>的实例,用UDT的属性填充它。
 * 然后,驱动程序将输入流传递给方法<code> SQLData.readSQL </code>,从而在其实现中调用<code> SQLInput </code>读取器方法以从输入流读取属性。
 * 
 * 
 * @since 1.2
 */

public interface SQLInput {


    //================================================================
    // Methods for reading attributes from the stream of SQL data.
    // These methods correspond to the column-accessor methods of
    // java.sql.ResultSet.
    //================================================================

    /**
     * Reads the next attribute in the stream and returns it as a <code>String</code>
     * in the Java programming language.
     *
     * <p>
     *  读取流中的下一个属性,并以Java编程语言中的<code> String </code>返回它。
     * 
     * 
     * @return the attribute; if the value is SQL <code>NULL</code>, returns <code>null</code>
     * @exception SQLException if a database access error occurs
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.2
     */
    String readString() throws SQLException;

    /**
     * Reads the next attribute in the stream and returns it as a <code>boolean</code>
     * in the Java programming language.
     *
     * <p>
     * 读取流中的下一个属性,并以Java编程语言中的<code> boolean </code>返回它。
     * 
     * 
     * @return the attribute; if the value is SQL <code>NULL</code>, returns <code>false</code>
     * @exception SQLException if a database access error occurs
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.2
     */
    boolean readBoolean() throws SQLException;

    /**
     * Reads the next attribute in the stream and returns it as a <code>byte</code>
     * in the Java programming language.
     *
     * <p>
     *  读取流中的下一个属性,并将其作为Java编程语言中的<code> byte </code>返回。
     * 
     * 
     * @return the attribute; if the value is SQL <code>NULL</code>, returns <code>0</code>
     * @exception SQLException if a database access error occurs
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.2
     */
    byte readByte() throws SQLException;

    /**
     * Reads the next attribute in the stream and returns it as a <code>short</code>
     * in the Java programming language.
     *
     * <p>
     *  读取流中的下一个属性,并以Java编程语言中的<code> short </code>返回它。
     * 
     * 
     * @return the attribute; if the value is SQL <code>NULL</code>, returns <code>0</code>
     * @exception SQLException if a database access error occurs
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.2
     */
    short readShort() throws SQLException;

    /**
     * Reads the next attribute in the stream and returns it as an <code>int</code>
     * in the Java programming language.
     *
     * <p>
     *  读取流中的下一个属性,并以Java编程语言中的<code> int </code>返回它。
     * 
     * 
     * @return the attribute; if the value is SQL <code>NULL</code>, returns <code>0</code>
     * @exception SQLException if a database access error occurs
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.2
     */
    int readInt() throws SQLException;

    /**
     * Reads the next attribute in the stream and returns it as a <code>long</code>
     * in the Java programming language.
     *
     * <p>
     *  读取流中的下一个属性,并以Java编程语言中的<code> long </code>返回它。
     * 
     * 
     * @return the attribute; if the value is SQL <code>NULL</code>, returns <code>0</code>
     * @exception SQLException if a database access error occurs
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.2
     */
    long readLong() throws SQLException;

    /**
     * Reads the next attribute in the stream and returns it as a <code>float</code>
     * in the Java programming language.
     *
     * <p>
     *  读取流中的下一个属性,并以Java编程语言中的<code> float </code>返回它。
     * 
     * 
     * @return the attribute; if the value is SQL <code>NULL</code>, returns <code>0</code>
     * @exception SQLException if a database access error occurs
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.2
     */
    float readFloat() throws SQLException;

    /**
     * Reads the next attribute in the stream and returns it as a <code>double</code>
     * in the Java programming language.
     *
     * <p>
     *  读取流中的下一个属性,并将其作为Java编程语言中的<code> double </code>返回。
     * 
     * 
     * @return the attribute; if the value is SQL <code>NULL</code>, returns <code>0</code>
     * @exception SQLException if a database access error occurs
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.2
     */
    double readDouble() throws SQLException;

    /**
     * Reads the next attribute in the stream and returns it as a <code>java.math.BigDecimal</code>
     * object in the Java programming language.
     *
     * <p>
     *  读取流中的下一个属性,并将其作为Java编程语言中的<code> java.math.BigDecimal </code>对象返回。
     * 
     * 
     * @return the attribute; if the value is SQL <code>NULL</code>, returns <code>null</code>
     * @exception SQLException if a database access error occurs
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.2
     */
    java.math.BigDecimal readBigDecimal() throws SQLException;

    /**
     * Reads the next attribute in the stream and returns it as an array of bytes
     * in the Java programming language.
     *
     * <p>
     *  读取流中的下一个属性,并以Java编程语言中的字节数组形式返回。
     * 
     * 
     * @return the attribute; if the value is SQL <code>NULL</code>, returns <code>null</code>
     * @exception SQLException if a database access error occurs
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.2
     */
    byte[] readBytes() throws SQLException;

    /**
     * Reads the next attribute in the stream and returns it as a <code>java.sql.Date</code> object.
     *
     * <p>
     *  读取流中的下一个属性,并将其作为<code> java.sql.Date </code>对象返回。
     * 
     * 
     * @return the attribute; if the value is SQL <code>NULL</code>, returns <code>null</code>
     * @exception SQLException if a database access error occurs
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.2
     */
    java.sql.Date readDate() throws SQLException;

    /**
     * Reads the next attribute in the stream and returns it as a <code>java.sql.Time</code> object.
     *
     * <p>
     *  读取流中的下一个属性,并将其作为<code> java.sql.Time </code>对象返回。
     * 
     * 
     * @return the attribute; if the value is SQL <code>NULL</code>, returns <code>null</code>
     * @exception SQLException if a database access error occurs
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.2
     */
    java.sql.Time readTime() throws SQLException;

    /**
     * Reads the next attribute in the stream and returns it as a <code>java.sql.Timestamp</code> object.
     *
     * <p>
     *  读取流中的下一个属性,并将其作为<code> java.sql.Timestamp </code>对象返回。
     * 
     * 
     * @return the attribute; if the value is SQL <code>NULL</code>, returns <code>null</code>
     * @exception SQLException if a database access error occurs
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.2
     */
    java.sql.Timestamp readTimestamp() throws SQLException;

    /**
     * Reads the next attribute in the stream and returns it as a stream of Unicode characters.
     *
     * <p>
     *  读取流中的下一个属性,并将其作为Unicode字符流返回。
     * 
     * 
     * @return the attribute; if the value is SQL <code>NULL</code>, returns <code>null</code>
     * @exception SQLException if a database access error occurs
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.2
     */
    java.io.Reader readCharacterStream() throws SQLException;

    /**
     * Reads the next attribute in the stream and returns it as a stream of ASCII characters.
     *
     * <p>
     * 读取流中的下一个属性,并将其作为ASCII字符流返回。
     * 
     * 
     * @return the attribute; if the value is SQL <code>NULL</code>, returns <code>null</code>
     * @exception SQLException if a database access error occurs
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.2
     */
    java.io.InputStream readAsciiStream() throws SQLException;

    /**
     * Reads the next attribute in the stream and returns it as a stream of uninterpreted
     * bytes.
     *
     * <p>
     *  读取流中的下一个属性,并将其作为未解释字节流返回。
     * 
     * 
     * @return the attribute; if the value is SQL <code>NULL</code>, returns <code>null</code>
     * @exception SQLException if a database access error occurs
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.2
     */
    java.io.InputStream readBinaryStream() throws SQLException;

    //================================================================
    // Methods for reading items of SQL user-defined types from the stream.
    //================================================================

    /**
     * Reads the datum at the head of the stream and returns it as an
     * <code>Object</code> in the Java programming language.  The
     * actual type of the object returned is determined by the default type
     * mapping, and any customizations present in this stream's type map.
     *
     * <P>A type map is registered with the stream by the JDBC driver before the
     * stream is passed to the application.
     *
     * <P>When the datum at the head of the stream is an SQL <code>NULL</code>,
     * the method returns <code>null</code>.  If the datum is an SQL structured or distinct
     * type, it determines the SQL type of the datum at the head of the stream.
     * If the stream's type map has an entry for that SQL type, the driver
     * constructs an object of the appropriate class and calls the method
     * <code>SQLData.readSQL</code> on that object, which reads additional data from the
     * stream, using the protocol described for that method.
     *
     * <p>
     *  读取流头部的数据,并将其作为Java编程语言中的<code> Object </code>返回。返回的对象的实际类型由默认类型映射以及此流的类型映射中存在的任何自定义来确定。
     * 
     *  <P>在流被传递到应用程序之前,JDBC驱动程序向流注册类型映射。
     * 
     *  <P>当流头部的数据是SQL <code> NULL </code>时,该方法返回<code> null </code>。如果数据是SQL结构化或不同类型,则它确定流头部处的数据的SQL类型。
     * 如果流的类型映射具有该SQL类型的条目,则驱动程序构造适当类的对象并且调用该对象上的方法<code> SQLData.readSQL </code>,该方法从流中读取附加数据,使用协议。
     * 
     * 
     * @return the datum at the head of the stream as an <code>Object</code> in the
     * Java programming language;<code>null</code> if the datum is SQL <code>NULL</code>
     * @exception SQLException if a database access error occurs
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.2
     */
    Object readObject() throws SQLException;

    /**
     * Reads an SQL <code>REF</code> value from the stream and returns it as a
     * <code>Ref</code> object in the Java programming language.
     *
     * <p>
     *  从流中读取SQL <code> REF </code>值,并将其作为Java编程语言中的<code> Ref </code>对象返回。
     * 
     * 
     * @return a <code>Ref</code> object representing the SQL <code>REF</code> value
     * at the head of the stream; <code>null</code> if the value read is
     * SQL <code>NULL</code>
     * @exception SQLException if a database access error occurs
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.2
     */
    Ref readRef() throws SQLException;

    /**
     * Reads an SQL <code>BLOB</code> value from the stream and returns it as a
     * <code>Blob</code> object in the Java programming language.
     *
     * <p>
     *  从流中读取SQL <code> BLOB </code>值,并将其作为Java编程语言中的<code> Blob </code>对象返回。
     * 
     * 
     * @return a <code>Blob</code> object representing data of the SQL <code>BLOB</code> value
     * at the head of the stream; <code>null</code> if the value read is
     * SQL <code>NULL</code>
     * @exception SQLException if a database access error occurs
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.2
     */
    Blob readBlob() throws SQLException;

    /**
     * Reads an SQL <code>CLOB</code> value from the stream and returns it as a
     * <code>Clob</code> object in the Java programming language.
     *
     * <p>
     *  从流中读取SQL <code> CLOB </code>值,并将其作为Java编程语言中的<code> Clob </code>对象返回。
     * 
     * 
     * @return a <code>Clob</code> object representing data of the SQL <code>CLOB</code> value
     * at the head of the stream; <code>null</code> if the value read is
     * SQL <code>NULL</code>
     * @exception SQLException if a database access error occurs
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.2
     */
    Clob readClob() throws SQLException;

    /**
     * Reads an SQL <code>ARRAY</code> value from the stream and returns it as an
     * <code>Array</code> object in the Java programming language.
     *
     * <p>
     * 从流中读取SQL <code> ARRAY </code>值,并将其作为Java编程语言中的<code> Array </code>对象返回。
     * 
     * 
     * @return an <code>Array</code> object representing data of the SQL
     * <code>ARRAY</code> value at the head of the stream; <code>null</code>
     * if the value read is SQL <code>NULL</code>
     * @exception SQLException if a database access error occurs
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.2
     */
    Array readArray() throws SQLException;

    /**
     * Retrieves whether the last value read was SQL <code>NULL</code>.
     *
     * <p>
     *  检索最后读取的值是否为SQL <code> NULL </code>。
     * 
     * 
     * @return <code>true</code> if the most recently read SQL value was SQL
     * <code>NULL</code>; <code>false</code> otherwise
     * @exception SQLException if a database access error occurs
     *
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.2
     */
    boolean wasNull() throws SQLException;

    //---------------------------- JDBC 3.0 -------------------------

    /**
     * Reads an SQL <code>DATALINK</code> value from the stream and returns it as a
     * <code>java.net.URL</code> object in the Java programming language.
     *
     * <p>
     *  从流中读取SQL <code> DATALINK </code>值,并将其作为Java编程语言中的<code> java.net.URL </code>对象返回。
     * 
     * 
     * @return a <code>java.net.URL</code> object.
     * @exception SQLException if a database access error occurs,
     *            or if a URL is malformed
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.4
     */
    java.net.URL readURL() throws SQLException;

     //---------------------------- JDBC 4.0 -------------------------

    /**
     * Reads an SQL <code>NCLOB</code> value from the stream and returns it as a
     * <code>NClob</code> object in the Java programming language.
     *
     * <p>
     *  从流中读取SQL <code> NCLOB </code>值,并将其作为Java编程语言中的<code> NClob </code>对象返回。
     * 
     * 
     * @return a <code>NClob</code> object representing data of the SQL <code>NCLOB</code> value
     * at the head of the stream; <code>null</code> if the value read is
     * SQL <code>NULL</code>
     * @exception SQLException if a database access error occurs
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.6
     */
    NClob readNClob() throws SQLException;

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
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.6
     */
    String readNString() throws SQLException;

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
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.6
     */
    SQLXML readSQLXML() throws SQLException;

    /**
     * Reads an SQL <code>ROWID</code> value from the stream and returns it as a
     * <code>RowId</code> object in the Java programming language.
     *
     * <p>
     *  从流中读取SQL <code> ROWID </code>值,并将其作为Java编程语言中的<code> RowId </code>对象返回。
     * 
     * 
     * @return a <code>RowId</code> object representing data of the SQL <code>ROWID</code> value
     * at the head of the stream; <code>null</code> if the value read is
     * SQL <code>NULL</code>
     * @exception SQLException if a database access error occurs
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.6
     */
    RowId readRowId() throws SQLException;

    //--------------------------JDBC 4.2 -----------------------------

    /**
     * Reads the next attribute in the stream and returns it as an
     * {@code Object} in the Java programming language. The
     * actual type of the object returned is determined by the specified
     * Java data type, and any customizations present in this
     * stream's type map.
     *
     * <P>A type map is registered with the stream by the JDBC driver before the
     * stream is passed to the application.
     *
     * <P>When the attribute at the head of the stream is an SQL {@code NULL}
     * the method returns {@code null}. If the attribute is an SQL
     * structured or distinct
     * type, it determines the SQL type of the attribute at the head of the stream.
     * If the stream's type map has an entry for that SQL type, the driver
     * constructs an object of the appropriate class and calls the method
     * {@code SQLData.readSQL} on that object, which reads additional data from the
     * stream, using the protocol described for that method.
     *<p>
     * The default implementation will throw {@code SQLFeatureNotSupportedException}
     *
     * <p>
     *  读取流中的下一个属性,并将其作为Java编程语言中的{@code Object}返回。返回的对象的实际类型由指定的Java数据类型以及此流的类型映射中存在的任何自定义决定。
     * 
     *  <P>在流被传递到应用程序之前,JDBC驱动程序向流注册类型映射。
     * 
     * <P>当流头部的属性是一个SQL {@code NULL}时,该方法返回{@code null}。如果属性是SQL结构化或不同类型,则它确定流头部的属性的SQL类型。
     * 
     * @param <T> the type of the class modeled by this Class object
     * @param type Class representing the Java data type to convert the attribute to.
     * @return the attribute at the head of the stream as an {@code Object} in the
     * Java programming language;{@code null} if the attribute is SQL {@code NULL}
     * @exception SQLException if a database access error occurs
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.8
     */
    default <T> T readObject(Class<T> type) throws SQLException {
       throw new SQLFeatureNotSupportedException();
    }
}
