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
 * The output stream for writing the attributes of a user-defined
 * type back to the database.  This interface, used
 * only for custom mapping, is used by the driver, and its
 * methods are never directly invoked by a programmer.
 * <p>When an object of a class implementing the interface
 * <code>SQLData</code> is passed as an argument to an SQL statement, the
 * JDBC driver calls the method <code>SQLData.getSQLType</code> to
 * determine the  kind of SQL
 * datum being passed to the database.
 * The driver then creates an instance of <code>SQLOutput</code> and
 * passes it to the method <code>SQLData.writeSQL</code>.
 * The method <code>writeSQL</code> in turn calls the
 * appropriate <code>SQLOutput</code> <i>writer</i> methods
 * <code>writeBoolean</code>, <code>writeCharacterStream</code>, and so on)
 * to write data from the <code>SQLData</code> object to
 * the <code>SQLOutput</code> output stream as the
 * representation of an SQL user-defined type.
 * <p>
 *  用于将用户定义类型的属性写回数据库的输出流。此接口仅用于自定义映射,由驱动程序使用,并且其方法不会直接由编程人员调用。
 *  <p>当实现接口<code> SQLData </code>的类的对象作为参数传递给SQL语句时,JDBC驱动程序会调用<code> SQLData.getSQLType </code>方法来确定类型
 * 的SQL数据传递到数据库。
 *  用于将用户定义类型的属性写回数据库的输出流。此接口仅用于自定义映射,由驱动程序使用,并且其方法不会直接由编程人员调用。
 * 然后,驱动程序创建一个<code> SQLOutput </code>的实例,并将其传递给方法<code> SQLData.writeSQL </code>。
 * 方法<code> writeSQL </code>依次调用<code> SQLOutput </code> <i> writer </i>方法<code> writeBoolean </code>,<code>
 *  writeCharacterStream </code>等等)将数据从<code> SQLData </code>对象写入到<code> SQLOutput </code>输出流中,作为SQL用户定义
 * 类型的表示。
 * 然后,驱动程序创建一个<code> SQLOutput </code>的实例,并将其传递给方法<code> SQLData.writeSQL </code>。
 * 
 * 
 * @since 1.2
 */

 public interface SQLOutput {

  //================================================================
  // Methods for writing attributes to the stream of SQL data.
  // These methods correspond to the column-accessor methods of
  // java.sql.ResultSet.
  //================================================================

  /**
   * Writes the next attribute to the stream as a <code>String</code>
   * in the Java programming language.
   *
   * <p>
   *  将下一个属性作为Java编程语言中的<code> String </code>写入流中。
   * 
   * 
   * @param x the value to pass to the database
   * @exception SQLException if a database access error occurs
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  void writeString(String x) throws SQLException;

  /**
   * Writes the next attribute to the stream as a Java boolean.
   * Writes the next attribute to the stream as a <code>String</code>
   * in the Java programming language.
   *
   * <p>
   *  将下一个属性作为Java布尔值写入流。将下一个属性作为Java编程语言中的<code> String </code>写入流。
   * 
   * 
   * @param x the value to pass to the database
   * @exception SQLException if a database access error occurs
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  void writeBoolean(boolean x) throws SQLException;

  /**
   * Writes the next attribute to the stream as a Java byte.
   * Writes the next attribute to the stream as a <code>String</code>
   * in the Java programming language.
   *
   * <p>
   *  将下一个属性作为Java字节写入流。将下一个属性作为Java编程语言中的<code> String </code>写入流。
   * 
   * 
   * @param x the value to pass to the database
   * @exception SQLException if a database access error occurs
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  void writeByte(byte x) throws SQLException;

  /**
   * Writes the next attribute to the stream as a Java short.
   * Writes the next attribute to the stream as a <code>String</code>
   * in the Java programming language.
   *
   * <p>
   * 将下一个属性作为Java短语写入流。将下一个属性作为Java编程语言中的<code> String </code>写入流。
   * 
   * 
   * @param x the value to pass to the database
   * @exception SQLException if a database access error occurs
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  void writeShort(short x) throws SQLException;

  /**
   * Writes the next attribute to the stream as a Java int.
   * Writes the next attribute to the stream as a <code>String</code>
   * in the Java programming language.
   *
   * <p>
   *  将下一个属性作为Java int写入流。将下一个属性作为Java编程语言中的<code> String </code>写入流。
   * 
   * 
   * @param x the value to pass to the database
   * @exception SQLException if a database access error occurs
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  void writeInt(int x) throws SQLException;

  /**
   * Writes the next attribute to the stream as a Java long.
   * Writes the next attribute to the stream as a <code>String</code>
   * in the Java programming language.
   *
   * <p>
   *  将下一个属性作为Java长度写入流。将下一个属性作为Java编程语言中的<code> String </code>写入流。
   * 
   * 
   * @param x the value to pass to the database
   * @exception SQLException if a database access error occurs
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  void writeLong(long x) throws SQLException;

  /**
   * Writes the next attribute to the stream as a Java float.
   * Writes the next attribute to the stream as a <code>String</code>
   * in the Java programming language.
   *
   * <p>
   *  将下一个属性作为Java浮动写入流。将下一个属性作为Java编程语言中的<code> String </code>写入流。
   * 
   * 
   * @param x the value to pass to the database
   * @exception SQLException if a database access error occurs
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  void writeFloat(float x) throws SQLException;

  /**
   * Writes the next attribute to the stream as a Java double.
   * Writes the next attribute to the stream as a <code>String</code>
   * in the Java programming language.
   *
   * <p>
   *  将下一个属性作为Java double写入流。将下一个属性作为Java编程语言中的<code> String </code>写入流。
   * 
   * 
   * @param x the value to pass to the database
   * @exception SQLException if a database access error occurs
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  void writeDouble(double x) throws SQLException;

  /**
   * Writes the next attribute to the stream as a java.math.BigDecimal object.
   * Writes the next attribute to the stream as a <code>String</code>
   * in the Java programming language.
   *
   * <p>
   *  将下一个属性作为java.math.BigDecimal对象写入流。将下一个属性作为Java编程语言中的<code> String </code>写入流。
   * 
   * 
   * @param x the value to pass to the database
   * @exception SQLException if a database access error occurs
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  void writeBigDecimal(java.math.BigDecimal x) throws SQLException;

  /**
   * Writes the next attribute to the stream as an array of bytes.
   * Writes the next attribute to the stream as a <code>String</code>
   * in the Java programming language.
   *
   * <p>
   *  将下一个属性作为字节数组写入流。将下一个属性作为Java编程语言中的<code> String </code>写入流。
   * 
   * 
   * @param x the value to pass to the database
   * @exception SQLException if a database access error occurs
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  void writeBytes(byte[] x) throws SQLException;

  /**
   * Writes the next attribute to the stream as a java.sql.Date object.
   * Writes the next attribute to the stream as a <code>java.sql.Date</code> object
   * in the Java programming language.
   *
   * <p>
   *  将下一个属性作为java.sql.Date对象写入流。将下一个属性作为Java编程语言中的<code> java.sql.Date </code>对象写入流。
   * 
   * 
   * @param x the value to pass to the database
   * @exception SQLException if a database access error occurs
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  void writeDate(java.sql.Date x) throws SQLException;

  /**
   * Writes the next attribute to the stream as a java.sql.Time object.
   * Writes the next attribute to the stream as a <code>java.sql.Date</code> object
   * in the Java programming language.
   *
   * <p>
   *  将下一个属性作为java.sql.Time对象写入流。将下一个属性作为Java编程语言中的<code> java.sql.Date </code>对象写入流。
   * 
   * 
   * @param x the value to pass to the database
   * @exception SQLException if a database access error occurs
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  void writeTime(java.sql.Time x) throws SQLException;

  /**
   * Writes the next attribute to the stream as a java.sql.Timestamp object.
   * Writes the next attribute to the stream as a <code>java.sql.Date</code> object
   * in the Java programming language.
   *
   * <p>
   * 将下一个属性作为java.sql.Timestamp对象写入流。将下一个属性作为Java编程语言中的<code> java.sql.Date </code>对象写入流。
   * 
   * 
   * @param x the value to pass to the database
   * @exception SQLException if a database access error occurs
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  void writeTimestamp(java.sql.Timestamp x) throws SQLException;

  /**
   * Writes the next attribute to the stream as a stream of Unicode characters.
   *
   * <p>
   *  将下一个属性作为Unicode字符流写入流。
   * 
   * 
   * @param x the value to pass to the database
   * @exception SQLException if a database access error occurs
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  void writeCharacterStream(java.io.Reader x) throws SQLException;

  /**
   * Writes the next attribute to the stream as a stream of ASCII characters.
   *
   * <p>
   *  将下一个属性作为ASCII字符流写入流。
   * 
   * 
   * @param x the value to pass to the database
   * @exception SQLException if a database access error occurs
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  void writeAsciiStream(java.io.InputStream x) throws SQLException;

  /**
   * Writes the next attribute to the stream as a stream of uninterpreted
   * bytes.
   *
   * <p>
   *  将下一个属性作为未解释字节流写入流。
   * 
   * 
   * @param x the value to pass to the database
   * @exception SQLException if a database access error occurs
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  void writeBinaryStream(java.io.InputStream x) throws SQLException;

  //================================================================
  // Methods for writing items of SQL user-defined types to the stream.
  // These methods pass objects to the database as values of SQL
  // Structured Types, Distinct Types, Constructed Types, and Locator
  // Types.  They decompose the Java object(s) and write leaf data
  // items using the methods above.
  //================================================================

  /**
   * Writes to the stream the data contained in the given
   * <code>SQLData</code> object.
   * When the <code>SQLData</code> object is <code>null</code>, this
   * method writes an SQL <code>NULL</code> to the stream.
   * Otherwise, it calls the <code>SQLData.writeSQL</code>
   * method of the given object, which
   * writes the object's attributes to the stream.
   * The implementation of the method <code>SQLData.writeSQL</code>
   * calls the appropriate <code>SQLOutput</code> writer method(s)
   * for writing each of the object's attributes in order.
   * The attributes must be read from an <code>SQLInput</code>
   * input stream and written to an <code>SQLOutput</code>
   * output stream in the same order in which they were
   * listed in the SQL definition of the user-defined type.
   *
   * <p>
   *  将给定的<code> SQLData </code>对象中包含的数据写入流。
   * 当<code> SQLData </code>对象是<code> null </code>时,此方法将SQL <code> NULL </code>写入流。
   * 否则,它调用给定对象的<code> SQLData.writeSQL </code>方法,它将对象的属性写入流。
   * 方法<code> SQLData.writeSQL </code>的实现调用适当的<code> SQLOutput </code> writer方法,按顺序写入对象的每个属性。
   * 这些属性必须从<code> SQLInput </code>输入流读取,并以与在用户定义类型的SQL定义中列出的顺序相同的顺序写入<code> SQLOutput </code>输出流。
   * 
   * 
   * @param x the object representing data of an SQL structured or
   * distinct type
   * @exception SQLException if a database access error occurs
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  void writeObject(SQLData x) throws SQLException;

  /**
   * Writes an SQL <code>REF</code> value to the stream.
   *
   * <p>
   *  将SQL <code> REF </code>值写入流。
   * 
   * 
   * @param x a <code>Ref</code> object representing data of an SQL
   * <code>REF</code> value
   * @exception SQLException if a database access error occurs
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  void writeRef(Ref x) throws SQLException;

  /**
   * Writes an SQL <code>BLOB</code> value to the stream.
   *
   * <p>
   *  将SQL <code> BLOB </code>值写入流。
   * 
   * 
   * @param x a <code>Blob</code> object representing data of an SQL
   * <code>BLOB</code> value
   *
   * @exception SQLException if a database access error occurs
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  void writeBlob(Blob x) throws SQLException;

  /**
   * Writes an SQL <code>CLOB</code> value to the stream.
   *
   * <p>
   *  将SQL <code> CLOB </code>值写入流。
   * 
   * 
   * @param x a <code>Clob</code> object representing data of an SQL
   * <code>CLOB</code> value
   *
   * @exception SQLException if a database access error occurs
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  void writeClob(Clob x) throws SQLException;

  /**
   * Writes an SQL structured type value to the stream.
   *
   * <p>
   *  将SQL结构类型值写入流。
   * 
   * 
   * @param x a <code>Struct</code> object representing data of an SQL
   * structured type
   *
   * @exception SQLException if a database access error occurs
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  void writeStruct(Struct x) throws SQLException;

  /**
   * Writes an SQL <code>ARRAY</code> value to the stream.
   *
   * <p>
   *  向流中写入一个SQL <code> ARRAY </code>值。
   * 
   * 
   * @param x an <code>Array</code> object representing data of an SQL
   * <code>ARRAY</code> type
   *
   * @exception SQLException if a database access error occurs
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  void writeArray(Array x) throws SQLException;

     //--------------------------- JDBC 3.0 ------------------------

     /**
      * Writes a SQL <code>DATALINK</code> value to the stream.
      *
      * <p>
      *  将一个SQL <code> DATALINK </code>值写入流。
      * 
      * 
      * @param x a <code>java.net.URL</code> object representing the data
      * of SQL DATALINK type
      *
      * @exception SQLException if a database access error occurs
      * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
      * this method
      * @since 1.4
      */
     void writeURL(java.net.URL x) throws SQLException;

     //--------------------------- JDBC 4.0 ------------------------

  /**
   * Writes the next attribute to the stream as a <code>String</code>
   * in the Java programming language. The driver converts this to a
   * SQL <code>NCHAR</code> or
   * <code>NVARCHAR</code> or <code>LONGNVARCHAR</code> value
   * (depending on the argument's
   * size relative to the driver's limits on <code>NVARCHAR</code> values)
   * when it sends it to the stream.
   *
   * <p>
   * 将下一个属性作为Java编程语言中的<code> String </code>写入流。
   * 驱动程序将其转换为SQL <code> NCHAR </code>或<code> NVARCHAR </code>或<code> LONGNVARCHAR </code>值(取决于参数的大小相对于驱动程
   * 序在<code> </code>值),当它发送到流。
   * 将下一个属性作为Java编程语言中的<code> String </code>写入流。
   * 
   * 
   * @param x the value to pass to the database
   * @exception SQLException if a database access error occurs
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.6
   */
  void writeNString(String x) throws SQLException;

  /**
   * Writes an SQL <code>NCLOB</code> value to the stream.
   *
   * <p>
   *  将SQL <code> NCLOB </code>值写入流。
   * 
   * 
   * @param x a <code>NClob</code> object representing data of an SQL
   * <code>NCLOB</code> value
   *
   * @exception SQLException if a database access error occurs
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.6
   */
  void writeNClob(NClob x) throws SQLException;


  /**
   * Writes an SQL <code>ROWID</code> value to the stream.
   *
   * <p>
   *  将SQL <code> ROWID </code>值写入流。
   * 
   * 
   * @param x a <code>RowId</code> object representing data of an SQL
   * <code>ROWID</code> value
   *
   * @exception SQLException if a database access error occurs
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.6
   */
  void writeRowId(RowId x) throws SQLException;


  /**
   * Writes an SQL <code>XML</code> value to the stream.
   *
   * <p>
   *  将SQL <code> XML </code>值写入流。
   * 
   * 
   * @param x a <code>SQLXML</code> object representing data of an SQL
   * <code>XML</code> value
   *
   * @throws SQLException if a database access error occurs,
   * the <code>java.xml.transform.Result</code>,
   *  <code>Writer</code> or <code>OutputStream</code> has not been closed for the <code>SQLXML</code> object or
   *  if there is an error processing the XML value.  The <code>getCause</code> method
   *  of the exception may provide a more detailed exception, for example, if the
   *  stream does not contain valid XML.
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.6
   */
  void writeSQLXML(SQLXML x) throws SQLException;

  //--------------------------JDBC 4.2 -----------------------------

  /**
   * Writes to the stream the data contained in the given object. The
   * object will be converted to the specified targetSqlType
   * before being sent to the stream.
   *<p>
   * When the {@code object} is {@code null}, this
   * method writes an SQL {@code NULL} to the stream.
   * <p>
   * If the object has a custom mapping (is of a class implementing the
   * interface {@code SQLData}),
   * the JDBC driver should call the method {@code SQLData.writeSQL} to
   * write it to the SQL data stream.
   * If, on the other hand, the object is of a class implementing
   * {@code Ref}, {@code Blob}, {@code Clob},  {@code NClob},
   *  {@code Struct}, {@code java.net.URL},
   * or {@code Array}, the driver should pass it to the database as a
   * value of the corresponding SQL type.
   *<P>
   * The default implementation will throw {@code SQLFeatureNotSupportedException}
   *
   * <p>
   *  将包含在给定对象中的数据写入流。对象将在发送到流之前转换为指定的targetSqlType。
   * p>
   *  当{@code object}是{@code null}时,此方法将SQL {@code NULL}写入流。
   * <p>
   *  如果对象具有自定义映射(是实现接口{@code SQLData}的类),JDBC驱动程序应调用{@code SQLData.writeSQL}方法将其写入SQL数据流。
   * 另一方面,如果对象是实现{@code Ref},{@code Blob},{@code Clob},{@code NClob},{@code Struct},{@code java.net .URL}或
   * 
   * @param x the object containing the input parameter value
   * @param targetSqlType the SQL type to be sent to the database.
   * @exception SQLException if a database access error occurs  or
   *            if the Java Object specified by x is an InputStream
   *            or Reader object and the value of the scale parameter is less
   *            than zero
   * @exception SQLFeatureNotSupportedException if
   * the JDBC driver does not support this data type
   * @see JDBCType
   * @see SQLType
   * @since 1.8
   */
  default void writeObject(Object x, SQLType targetSqlType) throws SQLException {
        throw new SQLFeatureNotSupportedException();
  }

}

