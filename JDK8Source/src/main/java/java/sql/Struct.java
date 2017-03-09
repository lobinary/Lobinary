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
 * <p>The standard mapping in the Java programming language for an SQL
 * structured type. A <code>Struct</code> object contains a
 * value for each attribute of the SQL structured type that
 * it represents.
 * By default, an instance of<code>Struct</code> is valid as long as the
 * application has a reference to it.
 * <p>
 * All methods on the <code>Struct</code> interface must be fully implemented if the
 * JDBC driver supports the data type.
 * <p>
 *  <p> Java结构化类型的Java编程语言中的标准映射。 <code> Struct </code>对象包含它表示的SQL结构类型的每个属性的值。
 * 默认情况下,只要应用程序有对它的引用,<code> Struct </code>的实例就是有效的。
 * <p>
 *  如果JDBC驱动程序支持数据类型,则必须完全实现<code> Struct </code>接口上的所有方法。
 * 
 * 
 * @since 1.2
 */

public interface Struct {

  /**
   * Retrieves the SQL type name of the SQL structured type
   * that this <code>Struct</code> object represents.
   *
   * <p>
   *  检索此<code> Struct </code>对象表示的SQL结构类型的SQL类型名称。
   * 
   * 
   * @return the fully-qualified type name of the SQL structured
   *          type for which this <code>Struct</code> object
   *          is the generic representation
   * @exception SQLException if a database access error occurs
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  String getSQLTypeName() throws SQLException;

  /**
   * Produces the ordered values of the attributes of the SQL
   * structured type that this <code>Struct</code> object represents.
   * As individual attributes are processed, this method uses the type map
   * associated with the
   * connection for customizations of the type mappings.
   * If there is no
   * entry in the connection's type map that matches the structured
   * type that an attribute represents,
   * the driver uses the standard mapping.
   * <p>
   * Conceptually, this method calls the method
   * <code>getObject</code> on each attribute
   * of the structured type and returns a Java array containing
   * the result.
   *
   * <p>
   *  生成此<code> Struct </code>对象表示的SQL结构类型的属性的有序值。当处理单个属性时,此方法使用与连接关联的类型映射来进行类型映射的自定义。
   * 如果连接的类型映射中没有与属性表示的结构化类型匹配的条目,则驱动程序将使用标准映射。
   * <p>
   *  从概念上讲,此方法在结构化类型的每个属性上调用<code> getObject </code>方法,并返回包含结果的Java数组。
   * 
   * 
   * @return an array containing the ordered attribute values
   * @exception SQLException if a database access error occurs
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  Object[] getAttributes() throws SQLException;

  /**
   * Produces the ordered values of the attributes of the SQL
   * structured type that this <code>Struct</code> object represents.
   *  As individual attributes are processed, this method uses the given type map
   * for customizations of the type mappings.
   * If there is no
   * entry in the given type map that matches the structured
   * type that an attribute represents,
   * the driver uses the standard mapping. This method never
   * uses the type map associated with the connection.
   * <p>
   * Conceptually, this method calls the method
   * <code>getObject</code> on each attribute
   * of the structured type and returns a Java array containing
   * the result.
   *
   * <p>
   * 生成此<code> Struct </code>对象表示的SQL结构类型的属性的有序值。处理单个属性时,此方法使用给定类型映射来进行类型映射的自定义。
   * 如果给定类型映射中没有与属性表示的结构化类型匹配的条目,则驱动程序将使用标准映射。此方法从不使用与连接关联的类型映射。
   * <p>
   * 
   * @param map a mapping of SQL type names to Java classes
   * @return an array containing the ordered attribute values
   * @exception SQLException if a database access error occurs
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  Object[] getAttributes(java.util.Map<String,Class<?>> map)
      throws SQLException;
}
