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
 * The mapping in the Java programming language of an SQL <code>REF</code>
 * value, which is a reference to an SQL structured type value in the database.
 * <P>
 * SQL <code>REF</code> values are stored in a table that contains
 * instances of a referenceable SQL structured type, and each <code>REF</code>
 * value is a unique identifier for one instance in that table.
 * An SQL <code>REF</code> value may be used in place of the
 * SQL structured type it references, either as a column value in a
 * table or an attribute value in a structured type.
 * <P>
 * Because an SQL <code>REF</code> value is a logical pointer to an
 * SQL structured type, a <code>Ref</code> object is by default also a logical
 * pointer. Thus, retrieving an SQL <code>REF</code> value as
 * a <code>Ref</code> object does not materialize
 * the attributes of the structured type on the client.
 * <P>
 * A <code>Ref</code> object can be stored in the database using the
 * <code>PreparedStatement.setRef</code> method.
  * <p>
 * All methods on the <code>Ref</code> interface must be fully implemented if the
 * JDBC driver supports the data type.
 *
 * <p>
 *  在Java编程语言中的SQL <code> REF </code>值的映射,它是对数据库中的SQL结构化类型值的引用。
 * <P>
 *  SQL <code> REF </code>值存储在包含可引用SQL结构类型的实例的表中,每个<code> REF </code>值是该表中一个实例的唯一标识符。
 * 可以使用SQL <code> REF </code>值来代替它引用的SQL结构化类型,作为表中的列值或结构化类型中的属性值。
 * <P>
 *  因为SQL <code> REF </code>值是指向SQL结构化类型的逻辑指针,所以<code> Ref </code>对象默认也是逻辑指针。
 * 因此,将SQL <code> REF </code>值检索为<code> Ref </code>对象不会实现客户端上结构化类型的属性。
 * <P>
 *  可以使用<code> PreparedStatement.setRef </code>方法将<code> Ref </code>对象存储在数据库中。
 * <p>
 *  如果JDBC驱动程序支持数据类型,则必须完全实现<code> Ref </code>接口上的所有方法。
 * 
 * 
 * @see Struct
 * @since 1.2
 */
public interface Ref {

    /**
     * Retrieves the fully-qualified SQL name of the SQL structured type that
     * this <code>Ref</code> object references.
     *
     * <p>
     *  检索此<code> Ref </code>对象引用的SQL结构类型的完全限定SQL名称。
     * 
     * 
     * @return the fully-qualified SQL name of the referenced SQL structured type
     * @exception SQLException if a database access error occurs
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.2
     */
    String getBaseTypeName() throws SQLException;

    /**
     * Retrieves the referenced object and maps it to a Java type
     * using the given type map.
     *
     * <p>
     *  检索引用的对象,并使用给定的类型映射将其映射到Java类型。
     * 
     * 
     * @param map a <code>java.util.Map</code> object that contains
     *        the mapping to use (the fully-qualified name of the SQL
     *        structured type being referenced and the class object for
     *        <code>SQLData</code> implementation to which the SQL
     *        structured type will be mapped)
     * @return  a Java <code>Object</code> that is the custom mapping for
     *          the SQL structured type to which this <code>Ref</code>
     *          object refers
     * @exception SQLException if a database access error occurs
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.4
     * @see #setObject
     */
    Object getObject(java.util.Map<String,Class<?>> map) throws SQLException;


    /**
     * Retrieves the SQL structured type instance referenced by
     * this <code>Ref</code> object.  If the connection's type map has an entry
     * for the structured type, the instance will be custom mapped to
     * the Java class indicated in the type map.  Otherwise, the
     * structured type instance will be mapped to a <code>Struct</code> object.
     *
     * <p>
     * 检索此<code> Ref </code>对象引用的SQL结构类型实例。如果连接的类型映射具有结构化类型的条目,则实例将被定制映射到类型映射中指示的Java类。
     * 否则,结构化类型实例将映射到<code> Struct </code>对象。
     * 
     * 
     * @return  a Java <code>Object</code> that is the mapping for
     *          the SQL structured type to which this <code>Ref</code>
     *          object refers
     * @exception SQLException if a database access error occurs
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.4
     * @see #setObject
     */
    Object getObject() throws SQLException;

    /**
     * Sets the structured type value that this <code>Ref</code>
     * object references to the given instance of <code>Object</code>.
     * The driver converts this to an SQL structured type when it
     * sends it to the database.
     *
     * <p>
     * 
     * @param value an <code>Object</code> representing the SQL
     *        structured type instance that this
     *        <code>Ref</code> object will reference
     * @exception SQLException if a database access error occurs
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.4
     * @see #getObject()
     * @see #getObject(Map)
     * @see PreparedStatement#setObject(int, Object)
     * @see CallableStatement#setObject(String, Object)
     */
    void setObject(Object value) throws SQLException;

}
