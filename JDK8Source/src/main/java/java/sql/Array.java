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
 * The mapping in the Java programming language for the SQL type
 * <code>ARRAY</code>.
 * By default, an <code>Array</code> value is a transaction-duration
 * reference to an SQL <code>ARRAY</code> value.  By default, an <code>Array</code>
 * object is implemented using an SQL LOCATOR(array) internally, which
 * means that an <code>Array</code> object contains a logical pointer
 * to the data in the SQL <code>ARRAY</code> value rather
 * than containing the <code>ARRAY</code> value's data.
 * <p>
 * The <code>Array</code> interface provides methods for bringing an SQL
 * <code>ARRAY</code> value's data to the client as either an array or a
 * <code>ResultSet</code> object.
 * If the elements of the SQL <code>ARRAY</code>
 * are a UDT, they may be custom mapped.  To create a custom mapping,
 * a programmer must do two things:
 * <ul>
 * <li>create a class that implements the {@link SQLData}
 * interface for the UDT to be custom mapped.
 * <li>make an entry in a type map that contains
 *   <ul>
 *   <li>the fully-qualified SQL type name of the UDT
 *   <li>the <code>Class</code> object for the class implementing
 *       <code>SQLData</code>
 *   </ul>
 * </ul>
 * <p>
 * When a type map with an entry for
 * the base type is supplied to the methods <code>getArray</code>
 * and <code>getResultSet</code>, the mapping
 * it contains will be used to map the elements of the <code>ARRAY</code> value.
 * If no type map is supplied, which would typically be the case,
 * the connection's type map is used by default.
 * If the connection's type map or a type map supplied to a method has no entry
 * for the base type, the elements are mapped according to the standard mapping.
 * <p>
 * All methods on the <code>Array</code> interface must be fully implemented if the
 * JDBC driver supports the data type.
 *
 * <p>
 *  用于SQL类型的Java编程语言中的映射<code> ARRAY </code>默认情况下,<code> Array </code>值是对SQL <code> ARRAY </code>值的事务持续时
 * 间引用默认情况下,在内部使用SQL LOCATOR(array)实现<code> Array </code>对象,这意味着一个<code> Array </code>对象包含一个指向SQL < ARRAY </code>
 * 值,而不是包含<code> ARRAY </code>值的数据。
 * <p>
 * <code> Array </code>接口提供了用于将SQL <code> ARRAY </code>值的数据作为数组或<code> ResultSet </code>对象提供给客户端的方法。
 * 如果SQL <code> ARRAY </code>是一个UDT,它们可以是自定义映射要创建自定义映射,程序员必须做两件事：。
 * <ul>
 *  <li>创建一个实现{@link SQLData}接口的类,以便自定义映射<li>在类型映射中创建一个条目,该类型映射包含
 * <ul>
 *  <li> UDT的完全限定SQL类型名称<li>实施<code> SQLData </code>类的<code>类</code>
 * </ul>
 * </ul>
 * <p>
 * 当向方法<code> getArray </code>和<code> getResultSet </code>提供具有基本类型条目的类型映射时,它包含的映射将用于映射<code> ARRAY </code>
 *  value如果没有提供类型映射(通常是这种情况),则默认使用连接的类型映射如果连接的类型映射或提供给方法的类型映射没有基本类型的条目,元素根据标准映射进行映射。
 * <p>
 *  如果JDBC驱动程序支持数据类型,则必须完全实现<code> Array </code>接口上的所有方法
 * 
 * 
 * @since 1.2
 */

public interface Array {

  /**
   * Retrieves the SQL type name of the elements in
   * the array designated by this <code>Array</code> object.
   * If the elements are a built-in type, it returns
   * the database-specific type name of the elements.
   * If the elements are a user-defined type (UDT),
   * this method returns the fully-qualified SQL type name.
   *
   * <p>
   * 检索由此<code> Array </code>对象指定的数组中的元素的SQL类型名称如果元素是内置类型,则返回元素的数据库特定类型名称如果元素是用户定义类型(UDT),此方法返回完全限定的SQL类型名
   * 称。
   * 
   * 
   * @return a <code>String</code> that is the database-specific
   * name for a built-in base type; or the fully-qualified SQL type
   * name for a base type that is a UDT
   * @exception SQLException if an error occurs while attempting
   * to access the type name
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  String getBaseTypeName() throws SQLException;

  /**
   * Retrieves the JDBC type of the elements in the array designated
   * by this <code>Array</code> object.
   *
   * <p>
   *  检索由此<code> Array </code>对象指定的数组中的元素的JDBC类型
   * 
   * 
   * @return a constant from the class {@link java.sql.Types} that is
   * the type code for the elements in the array designated by this
   * <code>Array</code> object
   * @exception SQLException if an error occurs while attempting
   * to access the base type
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  int getBaseType() throws SQLException;

  /**
   * Retrieves the contents of the SQL <code>ARRAY</code> value designated
   * by this
   * <code>Array</code> object in the form of an array in the Java
   * programming language. This version of the method <code>getArray</code>
   * uses the type map associated with the connection for customizations of
   * the type mappings.
   * <p>
   * <strong>Note:</strong> When <code>getArray</code> is used to materialize
   * a base type that maps to a primitive data type, then it is
   * implementation-defined whether the array returned is an array of
   * that primitive data type or an array of <code>Object</code>.
   *
   * <p>
   *  以Java编程语言中的数组形式检索由此<code> Array </code>对象指定的SQL <code> ARRAY </code>值的内容此方法版本<code> getArray </code >
   * 使用与连接关联的类型映射来进行类型映射的自定义。
   * <p>
   * <strong>注意</strong>：<code> getArray </code>用于实现映射到基本数据类型的基本类型时,无论返回的数组是基元数据的数组,都是由实现定义的类型或<code> Obje
   * ct </code>的数组。
   * 
   * 
   * @return an array in the Java programming language that contains
   * the ordered elements of the SQL <code>ARRAY</code> value
   * designated by this <code>Array</code> object
   * @exception SQLException if an error occurs while attempting to
   * access the array
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  Object getArray() throws SQLException;

  /**
   * Retrieves the contents of the SQL <code>ARRAY</code> value designated by this
   * <code>Array</code> object.
   * This method uses
   * the specified <code>map</code> for type map customizations
   * unless the base type of the array does not match a user-defined
   * type in <code>map</code>, in which case it
   * uses the standard mapping. This version of the method
   * <code>getArray</code> uses either the given type map or the standard mapping;
   * it never uses the type map associated with the connection.
   * <p>
   * <strong>Note:</strong> When <code>getArray</code> is used to materialize
   * a base type that maps to a primitive data type, then it is
   * implementation-defined whether the array returned is an array of
   * that primitive data type or an array of <code>Object</code>.
   *
   * <p>
   *  检索由此<code> Array </code>对象指定的SQL <code> ARRAY </code>值的内容此方法对类型映射自定义使用指定的<code> map </code>,除非数组不匹配<code>
   *  map </code>中的用户定义类型,在这种情况下它使用标准映射此方法<code> getArray </code>的版本使用给定类型映射或标准映射;它从不使用与连接相关联的类型映射。
   * <p>
   * <strong>注意</strong>：<code> getArray </code>用于实现映射到基本数据类型的基本类型时,无论返回的数组是基元数据的数组,都是由实现定义的类型或<code> Obje
   * ct </code>的数组。
   * 
   * 
   * @param map a <code>java.util.Map</code> object that contains mappings
   *            of SQL type names to classes in the Java programming language
   * @return an array in the Java programming language that contains the ordered
   *         elements of the SQL array designated by this object
   * @exception SQLException if an error occurs while attempting to
   *                         access the array
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  Object getArray(java.util.Map<String,Class<?>> map) throws SQLException;

  /**
   * Retrieves a slice of the SQL <code>ARRAY</code>
   * value designated by this <code>Array</code> object, beginning with the
   * specified <code>index</code> and containing up to <code>count</code>
   * successive elements of the SQL array.  This method uses the type map
   * associated with the connection for customizations of the type mappings.
   * <p>
   * <strong>Note:</strong> When <code>getArray</code> is used to materialize
   * a base type that maps to a primitive data type, then it is
   * implementation-defined whether the array returned is an array of
   * that primitive data type or an array of <code>Object</code>.
   *
   * <p>
   *  从指定的<code> index </code>开始,检索由此<code> Array </code>对象指定的SQL <code> ARRAY </code>值,并且包含<code> count < code>
   *  SQL数组的连续元素此方法使用与连接关联的类型映射来进行类型映射的自定义。
   * <p>
   * <strong>注意</strong>：<code> getArray </code>用于实现映射到基本数据类型的基本类型时,无论返回的数组是基元数据的数组,都是由实现定义的类型或<code> Obje
   * ct </code>的数组。
   * 
   * 
   * @param index the array index of the first element to retrieve;
   *              the first element is at index 1
   * @param count the number of successive SQL array elements to retrieve
   * @return an array containing up to <code>count</code> consecutive elements
   * of the SQL array, beginning with element <code>index</code>
   * @exception SQLException if an error occurs while attempting to
   * access the array
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  Object getArray(long index, int count) throws SQLException;

  /**
   * Retreives a slice of the SQL <code>ARRAY</code> value
   * designated by this <code>Array</code> object, beginning with the specified
   * <code>index</code> and containing up to <code>count</code>
   * successive elements of the SQL array.
   * <P>
   * This method uses
   * the specified <code>map</code> for type map customizations
   * unless the base type of the array does not match a user-defined
   * type in <code>map</code>, in which case it
   * uses the standard mapping. This version of the method
   * <code>getArray</code> uses either the given type map or the standard mapping;
   * it never uses the type map associated with the connection.
   * <p>
   * <strong>Note:</strong> When <code>getArray</code> is used to materialize
   * a base type that maps to a primitive data type, then it is
   * implementation-defined whether the array returned is an array of
   * that primitive data type or an array of <code>Object</code>.
   *
   * <p>
   *  检索由<code> Array </code>对象指定的SQL <code> ARRAY </code>值的切片,从指定的<code> index </code>开始,最多包含<code> count
   *  <代码> SQL数组的连续元素。
   * <P>
   * 此方法使用指定的<code> map </code>进行类型映射自定义,除非数组的基本类型与<code> map </code>中的用户定义类型不匹配,在这种情况下,它使用标准映射方法<code> ge
   * tArray </code>的此版本使用给定类型映射或标准映射;它从不使用与连接相关联的类型映射。
   * <p>
   *  <strong>注意</strong>：<code> getArray </code>用于实现映射到基本数据类型的基本类型时,无论返回的数组是基元数据的数组,都是由实现定义的类型或<code> Obj
   * ect </code>的数组。
   * 
   * 
   * @param index the array index of the first element to retrieve;
   *              the first element is at index 1
   * @param count the number of successive SQL array elements to
   * retrieve
   * @param map a <code>java.util.Map</code> object
   * that contains SQL type names and the classes in
   * the Java programming language to which they are mapped
   * @return an array containing up to <code>count</code>
   * consecutive elements of the SQL <code>ARRAY</code> value designated by this
   * <code>Array</code> object, beginning with element
   * <code>index</code>
   * @exception SQLException if an error occurs while attempting to
   * access the array
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  Object getArray(long index, int count, java.util.Map<String,Class<?>> map)
    throws SQLException;

  /**
   * Retrieves a result set that contains the elements of the SQL
   * <code>ARRAY</code> value
   * designated by this <code>Array</code> object.  If appropriate,
   * the elements of the array are mapped using the connection's type
   * map; otherwise, the standard mapping is used.
   * <p>
   * The result set contains one row for each array element, with
   * two columns in each row.  The second column stores the element
   * value; the first column stores the index into the array for
   * that element (with the first array element being at index 1).
   * The rows are in ascending order corresponding to
   * the order of the indices.
   *
   * <p>
   * 检索包含由此<code> Array </code>对象指定的SQL <code> ARRAY </code>值的元素的结果集如果适当,使用连接的类型映射映射数组的元素;否则,使用标准映射
   * <p>
   *  结果集包含每个数组元素的一行,每行中有两列第二列存储元素值;第一列将索引存储到该元素的数组中(第一个数组元素的索引为1)。这些行按照与索引的顺序对应的升序排列
   * 
   * 
   * @return a {@link ResultSet} object containing one row for each
   * of the elements in the array designated by this <code>Array</code>
   * object, with the rows in ascending order based on the indices.
   * @exception SQLException if an error occurs while attempting to
   * access the array
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  ResultSet getResultSet () throws SQLException;

  /**
   * Retrieves a result set that contains the elements of the SQL
   * <code>ARRAY</code> value designated by this <code>Array</code> object.
   * This method uses
   * the specified <code>map</code> for type map customizations
   * unless the base type of the array does not match a user-defined
   * type in <code>map</code>, in which case it
   * uses the standard mapping. This version of the method
   * <code>getResultSet</code> uses either the given type map or the standard mapping;
   * it never uses the type map associated with the connection.
   * <p>
   * The result set contains one row for each array element, with
   * two columns in each row.  The second column stores the element
   * value; the first column stores the index into the array for
   * that element (with the first array element being at index 1).
   * The rows are in ascending order corresponding to
   * the order of the indices.
   *
   * <p>
   * 检索包含由此<code> Array </code>对象指定的SQL <code> ARRAY </code>值的元素的结果集此方法对类型映射自定义使用指定的<code> map </code>,除非数
   * 组的基类型与<code> map </code>中的用户定义类型不匹配,在这种情况下,它使用标准映射此方法<code> getResultSet </code>的版本使用给定类型映射或标准映射;它从不使
   * 用与连接相关联的类型映射。
   * <p>
   * 结果集包含每个数组元素的一行,每行中有两列第二列存储元素值;第一列将索引存储到该元素的数组中(第一个数组元素的索引为1)。这些行按照与索引的顺序对应的升序排列
   * 
   * 
   * @param map contains the mapping of SQL user-defined types to
   * classes in the Java programming language
   * @return a <code>ResultSet</code> object containing one row for each
   * of the elements in the array designated by this <code>Array</code>
   * object, with the rows in ascending order based on the indices.
   * @exception SQLException if an error occurs while attempting to
   * access the array
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  ResultSet getResultSet (java.util.Map<String,Class<?>> map) throws SQLException;

  /**
   * Retrieves a result set holding the elements of the subarray that
   * starts at index <code>index</code> and contains up to
   * <code>count</code> successive elements.  This method uses
   * the connection's type map to map the elements of the array if
   * the map contains an entry for the base type. Otherwise, the
   * standard mapping is used.
   * <P>
   * The result set has one row for each element of the SQL array
   * designated by this object, with the first row containing the
   * element at index <code>index</code>.  The result set has
   * up to <code>count</code> rows in ascending order based on the
   * indices.  Each row has two columns:  The second column stores
   * the element value; the first column stores the index into the
   * array for that element.
   *
   * <p>
   *  检索包含起始于索引<code> index </code>并包含<code> count </code>个连续元素的子数组元素的结果集此方法使用连接的类型映射来映射数组的元素如果映射包含基本类型的条目
   * ,否则使用标准映射。
   * <P>
   * 对于由此对象指定的SQL数组的每个元素,结果集都有一行,第一行包含索引处的元素<code> index </code>结果集最多具有<code> count </code>行按升序排列基于索引每行有两列
   * ：第二列存储元素值;第一列将索引存储到该元素的数组中。
   * 
   * 
   * @param index the array index of the first element to retrieve;
   *              the first element is at index 1
   * @param count the number of successive SQL array elements to retrieve
   * @return a <code>ResultSet</code> object containing up to
   * <code>count</code> consecutive elements of the SQL array
   * designated by this <code>Array</code> object, starting at
   * index <code>index</code>.
   * @exception SQLException if an error occurs while attempting to
   * access the array
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  ResultSet getResultSet(long index, int count) throws SQLException;

  /**
   * Retrieves a result set holding the elements of the subarray that
   * starts at index <code>index</code> and contains up to
   * <code>count</code> successive elements.
   * This method uses
   * the specified <code>map</code> for type map customizations
   * unless the base type of the array does not match a user-defined
   * type in <code>map</code>, in which case it
   * uses the standard mapping. This version of the method
   * <code>getResultSet</code> uses either the given type map or the standard mapping;
   * it never uses the type map associated with the connection.
   * <P>
   * The result set has one row for each element of the SQL array
   * designated by this object, with the first row containing the
   * element at index <code>index</code>.  The result set has
   * up to <code>count</code> rows in ascending order based on the
   * indices.  Each row has two columns:  The second column stores
   * the element value; the first column stores the index into the
   * array for that element.
   *
   * <p>
   * 检索包含起始于索引<code> index </code>并包含<code> count </code>个连续元素的子数组元素的结果集此方法使用指定的<code> map </code>类型映射自定义,
   * 除非数组的基本类型与<code> map </code>中的用户定义类型不匹配,在这种情况下它使用标准映射此版本的方法<code> getResultSet </code>给定类型映射或标准映射;它从不
   * 使用与连接相关联的类型映射。
   * <P>
   * 对于由此对象指定的SQL数组的每个元素,结果集都有一行,第一行包含索引处的元素<code> index </code>结果集最多具有<code> count </code>行按升序排列基于索引每行有两列
   * ：第二列存储元素值;第一列将索引存储到该元素的数组中。
   * 
   * 
   * @param index the array index of the first element to retrieve;
   *              the first element is at index 1
   * @param count the number of successive SQL array elements to retrieve
   * @param map the <code>Map</code> object that contains the mapping
   * of SQL type names to classes in the Java(tm) programming language
   * @return a <code>ResultSet</code> object containing up to
   * <code>count</code> consecutive elements of the SQL array
   * designated by this <code>Array</code> object, starting at
   * index <code>index</code>.
   * @exception SQLException if an error occurs while attempting to
   * access the array
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  ResultSet getResultSet (long index, int count,
                          java.util.Map<String,Class<?>> map)
    throws SQLException;
    /**
     * This method frees the <code>Array</code> object and releases the resources that
     * it holds. The object is invalid once the <code>free</code>
     * method is called.
     *<p>
     * After <code>free</code> has been called, any attempt to invoke a
     * method other than <code>free</code> will result in a <code>SQLException</code>
     * being thrown.  If <code>free</code> is called multiple times, the subsequent
     * calls to <code>free</code> are treated as a no-op.
     *<p>
     *
     * <p>
     * 
     * @throws SQLException if an error occurs releasing
     * the Array's resources
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.6
     */
    void free() throws SQLException;

}
