/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2005, Oracle and/or its affiliates. All rights reserved.
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
 * An object that can be used to get information about the types
 * and properties of the columns in a <code>ResultSet</code> object.
 * The following code fragment creates the <code>ResultSet</code> object rs,
 * creates the <code>ResultSetMetaData</code> object rsmd, and uses rsmd
 * to find out how many columns rs has and whether the first column in rs
 * can be used in a <code>WHERE</code> clause.
 * <PRE>
 *
 *     ResultSet rs = stmt.executeQuery("SELECT a, b, c FROM TABLE2");
 *     ResultSetMetaData rsmd = rs.getMetaData();
 *     int numberOfColumns = rsmd.getColumnCount();
 *     boolean b = rsmd.isSearchable(1);
 *
 * </PRE>
 * <p>
 *  可用于获取有关<code> ResultSet </code>对象中的列的类型和属性的信息的对象。
 * 以下代码片段创建<code> ResultSet </code>对象rs,创建<code> ResultSetMetaData </code>对象rsmd,并使用rsmd来查找rs有多少列以及rs中的第
 * 一列是否可以用在<code> WHERE </code>子句中。
 *  可用于获取有关<code> ResultSet </code>对象中的列的类型和属性的信息的对象。
 * <PRE>
 * 
 *  ResultSet rs = stmt.executeQuery("SELECT a,b,c FROM TABLE2"); ResultSetMetaData rsmd = rs.getMetaDat
 * a(); int numberOfColumns = rsmd.getColumnCount(); boolean b = rsmd.isSearchable(1);。
 * 
 * </PRE>
 */

public interface ResultSetMetaData extends Wrapper {

    /**
     * Returns the number of columns in this <code>ResultSet</code> object.
     *
     * <p>
     *  返回此<code> ResultSet </code>对象中的列数。
     * 
     * 
     * @return the number of columns
     * @exception SQLException if a database access error occurs
     */
    int getColumnCount() throws SQLException;

    /**
     * Indicates whether the designated column is automatically numbered.
     *
     * <p>
     *  指示指定的列是否自动编号。
     * 
     * 
     * @param column the first column is 1, the second is 2, ...
     * @return <code>true</code> if so; <code>false</code> otherwise
     * @exception SQLException if a database access error occurs
     */
    boolean isAutoIncrement(int column) throws SQLException;

    /**
     * Indicates whether a column's case matters.
     *
     * <p>
     *  指示列的大小写是否重要。
     * 
     * 
     * @param column the first column is 1, the second is 2, ...
     * @return <code>true</code> if so; <code>false</code> otherwise
     * @exception SQLException if a database access error occurs
     */
    boolean isCaseSensitive(int column) throws SQLException;

    /**
     * Indicates whether the designated column can be used in a where clause.
     *
     * <p>
     *  指示是否可以在where子句中使用指定的列。
     * 
     * 
     * @param column the first column is 1, the second is 2, ...
     * @return <code>true</code> if so; <code>false</code> otherwise
     * @exception SQLException if a database access error occurs
     */
    boolean isSearchable(int column) throws SQLException;

    /**
     * Indicates whether the designated column is a cash value.
     *
     * <p>
     *  指示指定的列是否为现金值。
     * 
     * 
     * @param column the first column is 1, the second is 2, ...
     * @return <code>true</code> if so; <code>false</code> otherwise
     * @exception SQLException if a database access error occurs
     */
    boolean isCurrency(int column) throws SQLException;

    /**
     * Indicates the nullability of values in the designated column.
     *
     * <p>
     *  指示指定列中的值的可空性。
     * 
     * 
     * @param column the first column is 1, the second is 2, ...
     * @return the nullability status of the given column; one of <code>columnNoNulls</code>,
     *          <code>columnNullable</code> or <code>columnNullableUnknown</code>
     * @exception SQLException if a database access error occurs
     */
    int isNullable(int column) throws SQLException;

    /**
     * The constant indicating that a
     * column does not allow <code>NULL</code> values.
     * <p>
     *  常数,指示列不允许<code> NULL </code>值。
     * 
     */
    int columnNoNulls = 0;

    /**
     * The constant indicating that a
     * column allows <code>NULL</code> values.
     * <p>
     *  常数,指示列允许<code> NULL </code>值。
     * 
     */
    int columnNullable = 1;

    /**
     * The constant indicating that the
     * nullability of a column's values is unknown.
     * <p>
     *  指示列值的可空性未知的常量。
     * 
     */
    int columnNullableUnknown = 2;

    /**
     * Indicates whether values in the designated column are signed numbers.
     *
     * <p>
     *  指示指定列中的值是否为有符号数字。
     * 
     * 
     * @param column the first column is 1, the second is 2, ...
     * @return <code>true</code> if so; <code>false</code> otherwise
     * @exception SQLException if a database access error occurs
     */
    boolean isSigned(int column) throws SQLException;

    /**
     * Indicates the designated column's normal maximum width in characters.
     *
     * <p>
     *  表示指定列的正常最大宽度(以字符为单位)。
     * 
     * 
     * @param column the first column is 1, the second is 2, ...
     * @return the normal maximum number of characters allowed as the width
     *          of the designated column
     * @exception SQLException if a database access error occurs
     */
    int getColumnDisplaySize(int column) throws SQLException;

    /**
     * Gets the designated column's suggested title for use in printouts and
     * displays. The suggested title is usually specified by the SQL <code>AS</code>
     * clause.  If a SQL <code>AS</code> is not specified, the value returned from
     * <code>getColumnLabel</code> will be the same as the value returned by the
     * <code>getColumnName</code> method.
     *
     * <p>
     * 获取指定列的建议标题以用于打印输出和显示。建议的标题通常由SQL <code> AS </code>子句指定。
     * 如果未指定SQL <code> AS </code>,则从<code> getColumnLabel </code>返回的值将与<code> getColumnName </code>方法返回的值相同。
     * 获取指定列的建议标题以用于打印输出和显示。建议的标题通常由SQL <code> AS </code>子句指定。
     * 
     * 
     * @param column the first column is 1, the second is 2, ...
     * @return the suggested column title
     * @exception SQLException if a database access error occurs
     */
    String getColumnLabel(int column) throws SQLException;

    /**
     * Get the designated column's name.
     *
     * <p>
     *  获取指定列的名称。
     * 
     * 
     * @param column the first column is 1, the second is 2, ...
     * @return column name
     * @exception SQLException if a database access error occurs
     */
    String getColumnName(int column) throws SQLException;

    /**
     * Get the designated column's table's schema.
     *
     * <p>
     *  获取指定列的表的模式。
     * 
     * 
     * @param column the first column is 1, the second is 2, ...
     * @return schema name or "" if not applicable
     * @exception SQLException if a database access error occurs
     */
    String getSchemaName(int column) throws SQLException;

    /**
     * Get the designated column's specified column size.
     * For numeric data, this is the maximum precision.  For character data, this is the length in characters.
     * For datetime datatypes, this is the length in characters of the String representation (assuming the
     * maximum allowed precision of the fractional seconds component). For binary data, this is the length in bytes.  For the ROWID datatype,
     * this is the length in bytes. 0 is returned for data types where the
     * column size is not applicable.
     *
     * <p>
     *  获取指定列的指定列大小。对于数字数据,这是最大精度。对于字符数据,这是以字符为单位的长度。对于datetime数据类型,这是字符串表示形式的长度(假设小数秒分量的最大允许精度)。
     * 对于二进制数据,这是以字节为单位的长度。对于ROWID数据类型,这是以字节为单位的长度。对于列大小不适用的数据类型,返回0。
     * 
     * 
     * @param column the first column is 1, the second is 2, ...
     * @return precision
     * @exception SQLException if a database access error occurs
     */
    int getPrecision(int column) throws SQLException;

    /**
     * Gets the designated column's number of digits to right of the decimal point.
     * 0 is returned for data types where the scale is not applicable.
     *
     * <p>
     *  获取指定列的小数点右边的位数。对于不适用比例的数据类型,返回0。
     * 
     * 
     * @param column the first column is 1, the second is 2, ...
     * @return scale
     * @exception SQLException if a database access error occurs
     */
    int getScale(int column) throws SQLException;

    /**
     * Gets the designated column's table name.
     *
     * <p>
     *  获取指定列的表名。
     * 
     * 
     * @param column the first column is 1, the second is 2, ...
     * @return table name or "" if not applicable
     * @exception SQLException if a database access error occurs
     */
    String getTableName(int column) throws SQLException;

    /**
     * Gets the designated column's table's catalog name.
     *
     * <p>
     *  获取指定列的表的目录名称。
     * 
     * 
     * @param column the first column is 1, the second is 2, ...
     * @return the name of the catalog for the table in which the given column
     *          appears or "" if not applicable
     * @exception SQLException if a database access error occurs
     */
    String getCatalogName(int column) throws SQLException;

    /**
     * Retrieves the designated column's SQL type.
     *
     * <p>
     *  检索指定列的SQL类型。
     * 
     * 
     * @param column the first column is 1, the second is 2, ...
     * @return SQL type from java.sql.Types
     * @exception SQLException if a database access error occurs
     * @see Types
     */
    int getColumnType(int column) throws SQLException;

    /**
     * Retrieves the designated column's database-specific type name.
     *
     * <p>
     *  检索指定列的特定于数据库的类型名称。
     * 
     * 
     * @param column the first column is 1, the second is 2, ...
     * @return type name used by the database. If the column type is
     * a user-defined type, then a fully-qualified type name is returned.
     * @exception SQLException if a database access error occurs
     */
    String getColumnTypeName(int column) throws SQLException;

    /**
     * Indicates whether the designated column is definitely not writable.
     *
     * <p>
     *  指示指定的列是否绝对不可写。
     * 
     * 
     * @param column the first column is 1, the second is 2, ...
     * @return <code>true</code> if so; <code>false</code> otherwise
     * @exception SQLException if a database access error occurs
     */
    boolean isReadOnly(int column) throws SQLException;

    /**
     * Indicates whether it is possible for a write on the designated column to succeed.
     *
     * <p>
     *  指示指定列上的写入是否可以成功。
     * 
     * 
     * @param column the first column is 1, the second is 2, ...
     * @return <code>true</code> if so; <code>false</code> otherwise
     * @exception SQLException if a database access error occurs
     */
    boolean isWritable(int column) throws SQLException;

    /**
     * Indicates whether a write on the designated column will definitely succeed.
     *
     * <p>
     *  指示在指定列上的写入是否一定成功。
     * 
     * 
     * @param column the first column is 1, the second is 2, ...
     * @return <code>true</code> if so; <code>false</code> otherwise
     * @exception SQLException if a database access error occurs
     */
    boolean isDefinitelyWritable(int column) throws SQLException;

    //--------------------------JDBC 2.0-----------------------------------

    /**
     * <p>Returns the fully-qualified name of the Java class whose instances
     * are manufactured if the method <code>ResultSet.getObject</code>
     * is called to retrieve a value
     * from the column.  <code>ResultSet.getObject</code> may return a subclass of the
     * class returned by this method.
     *
     * <p>
     * <p>如果调用方法<code> ResultSet.getObject </code>从列中检索值,则返回生成实例的Java类的完全限定名。
     *  <code> ResultSet.getObject </code>可能返回此方法返回的类的子类。
     * 
     * @param column the first column is 1, the second is 2, ...
     * @return the fully-qualified name of the class in the Java programming
     *         language that would be used by the method
     * <code>ResultSet.getObject</code> to retrieve the value in the specified
     * column. This is the class name used for custom mapping.
     * @exception SQLException if a database access error occurs
     * @since 1.2
     */
    String getColumnClassName(int column) throws SQLException;
}
