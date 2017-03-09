/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2006, Oracle and/or its affiliates. All rights reserved.
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
 * The interface used for the custom mapping of an SQL user-defined type (UDT) to
 * a class in the Java programming language. The class object for a class
 * implementing the <code>SQLData</code> interface will be entered in the
 * appropriate <code>Connection</code> object's type map along with the SQL
 * name of the UDT for which it is a custom mapping.
 * <P>
 * Typically, a <code>SQLData</code> implementation
 * will define a field for each attribute of an SQL structured type or a
 * single field for an SQL <code>DISTINCT</code> type. When the UDT is
 * retrieved from a data source with the <code>ResultSet.getObject</code>
 * method, it will be mapped as an instance of this class.  A programmer
 * can operate on this class instance just as on any other object in the
 * Java programming language and then store any changes made to it by
 * calling the <code>PreparedStatement.setObject</code> method,
 * which will map it back to the SQL type.
 * <p>
 * It is expected that the implementation of the class for a custom
 * mapping will be done by a tool.  In a typical implementation, the
 * programmer would simply supply the name of the SQL UDT, the name of
 * the class to which it is being mapped, and the names of the fields to
 * which each of the attributes of the UDT is to be mapped.  The tool will use
 * this information to implement the <code>SQLData.readSQL</code> and
 * <code>SQLData.writeSQL</code> methods.  The <code>readSQL</code> method
 * calls the appropriate <code>SQLInput</code> methods to read
 * each attribute from an <code>SQLInput</code> object, and the
 * <code>writeSQL</code> method calls <code>SQLOutput</code> methods
 * to write each attribute back to the data source via an
 * <code>SQLOutput</code> object.
 * <P>
 * An application programmer will not normally call <code>SQLData</code> methods
 * directly, and the <code>SQLInput</code> and <code>SQLOutput</code> methods
 * are called internally by <code>SQLData</code> methods, not by application code.
 *
 * <p>
 *  用于将SQL用户定义类型(UDT)定制映射到Java编程语言中的类的接口。
 * 实现<code> SQLData </code>接口的类的类对象将与适用于其自定义映射的UDT的SQL名称一起输入到相应的<code> Connection </code>对象的类型映射中。
 * <P>
 *  通常,<code> SQLData </code>实现将为SQL结构类型的每个属性或SQL <code> DISTINCT </code>类型的单个字段定义一个字段。
 * 当使用<code> ResultSet.getObject </code>方法从数据源检索UDT时,它将被映射为此类的实例。
 * 程序员可以像在Java编程语言中的任何其他对象一样对这个类实例进行操作,然后通过调用<code> PreparedStatement.setObject </code>方法存储对它所做的任何更改,这将把
 * 它映射回SQL类型。
 * 当使用<code> ResultSet.getObject </code>方法从数据源检索UDT时,它将被映射为此类的实例。
 * <p>
 * 期望自定义映射的类的实现将由工具完成。在典型的实现中,程序员将简单地提供SQL UDT的名称,它被映射到的类的名称,以及要映射UDT的每个属性的字段的名称。
 * 该工具将使用此信息来实现<code> SQLData.readSQL </code>和<code> SQLData.writeSQL </code>方法。
 *  <code> readSQL </code>方法调用适当的<code> SQLInput </code>方法从<code> SQLInput </code>对象读取每个属性,并且<code> writ
 * eSQL </code> <code> SQLOutput </code>方法通过<code> SQLOutput </code>对象将每个属性写回数据源。
 * 该工具将使用此信息来实现<code> SQLData.readSQL </code>和<code> SQLData.writeSQL </code>方法。
 * <P>
 *  应用程序员通常不会直接调用<code> SQLData </code>方法,<code> SQLInput </code>和<code> SQLOutput </code>方法,而不是应用程序代码。
 * 
 * 
 * @since 1.2
 */
public interface SQLData {

 /**
  * Returns the fully-qualified
  * name of the SQL user-defined type that this object represents.
  * This method is called by the JDBC driver to get the name of the
  * UDT instance that is being mapped to this instance of
  * <code>SQLData</code>.
  *
  * <p>
  *  返回此对象表示的SQL用户定义类型的完全限定名称。此方法由JDBC驱动程序调用,以获取要映射到<code> SQLData </code>的此实例的UDT实例的名称。
  * 
  * 
  * @return the type name that was passed to the method <code>readSQL</code>
  *            when this object was constructed and populated
  * @exception SQLException if there is a database access error
  * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
  * this method
  * @since 1.2
  */
  String getSQLTypeName() throws SQLException;

 /**
  * Populates this object with data read from the database.
  * The implementation of the method must follow this protocol:
  * <UL>
  * <LI>It must read each of the attributes or elements of the SQL
  * type  from the given input stream.  This is done
  * by calling a method of the input stream to read each
  * item, in the order that they appear in the SQL definition
  * of the type.
  * <LI>The method <code>readSQL</code> then
  * assigns the data to appropriate fields or
  * elements (of this or other objects).
  * Specifically, it must call the appropriate <i>reader</i> method
  * (<code>SQLInput.readString</code>, <code>SQLInput.readBigDecimal</code>,
  * and so on) method(s) to do the following:
  * for a distinct type, read its single data element;
  * for a structured type, read a value for each attribute of the SQL type.
  * </UL>
  * The JDBC driver initializes the input stream with a type map
  * before calling this method, which is used by the appropriate
  * <code>SQLInput</code> reader method on the stream.
  *
  * <p>
  *  使用从数据库读取的数据填充此对象。该方法的实现必须遵循此协议：
  * <UL>
  * <LI>它必须从给定的输入流读取SQL类型的每个属性或元素。这是通过调用输入流的方法来读取每个项,按它们出现在类型的SQL定义中的顺序来完成的。
  *  <LI>然后,方法<code> readSQL </code>将数据分配给相应的字段或元素(此对象或其他对象)。
  * 具体来说,它必须调用适当的<i> reader </i>方法(<code> SQLInput.readString </code>,<code> SQLInput.readBigDecimal </code>
  * ,等等)如下：对于不同类型,读取其单个数据元素;对于结构化类型,请读取SQL类型的每个属性的值。
  *  <LI>然后,方法<code> readSQL </code>将数据分配给相应的字段或元素(此对象或其他对象)。
  * </UL>
  *  JDBC驱动程序在调用此方法之前使用类型映射初始化输入流,该方法由流上的相应<code> SQLInput </code>读取器方法使用。
  * 
  * @param stream the <code>SQLInput</code> object from which to read the data for
  * the value that is being custom mapped
  * @param typeName the SQL type name of the value on the data stream
  * @exception SQLException if there is a database access error
  * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
  * this method
  * @see SQLInput
  * @since 1.2
  */
  void readSQL (SQLInput stream, String typeName) throws SQLException;

  /**
  * Writes this object to the given SQL data stream, converting it back to
  * its SQL value in the data source.
  * The implementation of the method must follow this protocol:<BR>
  * It must write each of the attributes of the SQL type
  * to the given output stream.  This is done by calling a
  * method of the output stream to write each item, in the order that
  * they appear in the SQL definition of the type.
  * Specifically, it must call the appropriate <code>SQLOutput</code> writer
  * method(s) (<code>writeInt</code>, <code>writeString</code>, and so on)
  * to do the following: for a Distinct Type, write its single data element;
  * for a Structured Type, write a value for each attribute of the SQL type.
  *
  * <p>
  * 
  * 
  * @param stream the <code>SQLOutput</code> object to which to write the data for
  * the value that was custom mapped
  * @exception SQLException if there is a database access error
  * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
  * this method
  * @see SQLOutput
  * @since 1.2
  */
  void writeSQL (SQLOutput stream) throws SQLException;
}
