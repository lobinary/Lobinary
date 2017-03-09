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

package javax.sql.rowset;

import java.sql.*;
import javax.sql.*;
import java.io.*;

import java.lang.reflect.*;

/**
 * Provides implementations for the methods that set and get
 * metadata information about a <code>RowSet</code> object's columns.
 * A <code>RowSetMetaDataImpl</code> object keeps track of the
 * number of columns in the rowset and maintains an internal array
 * of column attributes for each column.
 * <P>
 * A <code>RowSet</code> object creates a <code>RowSetMetaDataImpl</code>
 * object internally in order to set and retrieve information about
 * its columns.
 * <P>
 * NOTE: All metadata in a <code>RowSetMetaDataImpl</code> object
 * should be considered as unavailable until the <code>RowSet</code> object
 * that it describes is populated.
 * Therefore, any <code>RowSetMetaDataImpl</code> method that retrieves information
 * is defined as having unspecified behavior when it is called
 * before the <code>RowSet</code> object contains data.
 * <p>
 *  提供设置和获取有关<code> RowSet </code>对象的列的元数据信息的方法的实现。
 *  <code> RowSetMetaDataImpl </code>对象跟踪行集中的列数,并为每个列维护一个内部列属性数组。
 * <P>
 *  <code> RowSet </code>对象在内部创建一个<code> RowSetMetaDataImpl </code>对象,以设置和检索有关其列的信息。
 * <P>
 *  注意：在<code> RowSetMetaDataImpl </code>对象中的所有元数据应该被视为不可用,直到它描述的<code> RowSet </code>对象被填充。
 * 因此,在<code> RowSet </code>对象包含数据之前调用任何检索信息的<code> RowSetMetaDataImpl </code>方法被定义为具有未指定的行为。
 * 
 */
public class RowSetMetaDataImpl implements RowSetMetaData,  Serializable {

    /**
     * The number of columns in the <code>RowSet</code> object that created
     * this <code>RowSetMetaDataImpl</code> object.
     * <p>
     *  在创建此<code> RowSetMetaDataImpl </code>对象的<code> RowSet </code>对象中的列数。
     * 
     * 
     * @serial
     */
    private int colCount;

    /**
     * An array of <code>ColInfo</code> objects used to store information
     * about each column in the <code>RowSet</code> object for which
     * this <code>RowSetMetaDataImpl</code> object was created. The first
     * <code>ColInfo</code> object in this array contains information about
     * the first column in the <code>RowSet</code> object, the second element
     * contains information about the second column, and so on.
     * <p>
     *  用于存储有关创建此<Code> RowSetMetaDataImpl </code>对象的<code> RowSet </code>对象中每个列的信息的<code> ColInfo </code>对象
     * 数组。
     * 此数组中的第一个<code> ColInfo </code>对象包含有关<code> RowSet </code>对象中第一列的信息,第二个元素包含有关第二列的信息,等等。
     * 
     * 
     * @serial
     */
    private ColInfo[] colInfo;

    /**
     * Checks to see that the designated column is a valid column number for
     * the <code>RowSet</code> object for which this <code>RowSetMetaDataImpl</code>
     * was created. To be valid, a column number must be greater than
     * <code>0</code> and less than or equal to the number of columns in a row.
     * <p>
     * 检查指定的列是否为<code> RowSetMetaDataImpl </code>创建的<code> RowSet </code>对象的有效列号。
     * 为了有效,列号必须大于<code> 0 </code>且小于或等于行中的列数。
     * 
     * 
     * @throws <code>SQLException</code> with the message "Invalid column index"
     *        if the given column number is out of the range of valid column
     *        numbers for the <code>RowSet</code> object
     */
    private void checkColRange(int col) throws SQLException {
        if (col <= 0 || col > colCount) {
            throw new SQLException("Invalid column index :"+col);
        }
    }

    /**
     * Checks to see that the given SQL type is a valid column type and throws an
     * <code>SQLException</code> object if it is not.
     * To be valid, a SQL type must be one of the constant values
     * in the <code><a href="../../sql/Types.html">java.sql.Types</a></code>
     * class.
     *
     * <p>
     *  检查给定的SQL类型是否为有效的列类型,如果没有,则抛出<code> SQLException </code>对象。
     * 要有效,SQL类型必须是<code> <a href="../../sql/Types.html"> java.sql.Types </a> </code>中的常量值之一,类。
     * 
     * 
     * @param SQLType an <code>int</code> defined in the class <code>java.sql.Types</code>
     * @throws SQLException if the given <code>int</code> is not a constant defined in the
     *         class <code>java.sql.Types</code>
     */
    private void checkColType(int SQLType) throws SQLException {
        try {
            Class<?> c = java.sql.Types.class;
            Field[] publicFields = c.getFields();
            int fieldValue = 0;
            for (int i = 0; i < publicFields.length; i++) {
                fieldValue = publicFields[i].getInt(c);
                if (fieldValue == SQLType) {
                    return;
                 }
            }
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
        throw new SQLException("Invalid SQL type for column");
    }

    /**
     * Sets to the given number the number of columns in the <code>RowSet</code>
     * object for which this <code>RowSetMetaDataImpl</code> object was created.
     *
     * <p>
     *  将给定数字设置为<code> RowSet </code>对象中创建此<Data> RowSetMetaDataImpl </code>对象的列数。
     * 
     * 
     * @param columnCount an <code>int</code> giving the number of columns in the
     *        <code>RowSet</code> object
     * @throws SQLException if the given number is equal to or less than zero
     */
    public void setColumnCount(int columnCount) throws SQLException {

        if (columnCount <= 0) {
            throw new SQLException("Invalid column count. Cannot be less " +
                "or equal to zero");
            }

       colCount = columnCount;

       // If the colCount is Integer.MAX_VALUE,
       // we do not initialize the colInfo object.
       // even if we try to initialize the colCount with
       // colCount = Integer.MAx_VALUE-1, the colInfo
       // initialization fails throwing an ERROR
       // OutOfMemory Exception. So we do not initialize
       // colInfo at Integer.MAX_VALUE. This is to pass TCK.

       if(!(colCount == Integer.MAX_VALUE)) {
            colInfo = new ColInfo[colCount + 1];

           for (int i=1; i <= colCount; i++) {
                 colInfo[i] = new ColInfo();
           }
       }


    }

    /**
     * Sets whether the designated column is automatically
     * numbered, thus read-only, to the given <code>boolean</code>
     * value.
     *
     * <p>
     *  设置指定列是否自动编号,从而为只读,到给定的<code> boolean </code>值。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     *        must be between <code>1</code> and the number of columns
     *        in the rowset, inclusive
     * @param property <code>true</code> if the given column is
     *                 automatically incremented; <code>false</code>
     *                 otherwise
     * @throws SQLException if a database access error occurs or
     *         the given index is out of bounds
     */
    public void setAutoIncrement(int columnIndex, boolean property) throws SQLException {
        checkColRange(columnIndex);
        colInfo[columnIndex].autoIncrement = property;
    }

    /**
     * Sets whether the name of the designated column is case sensitive to
     * the given <code>boolean</code>.
     *
     * <p>
     *  设置指定列的名称是否对给定的<code> boolean </code>区分大小写。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     *        must be between <code>1</code> and the number of columns
     *        in the rowset, inclusive
     * @param property <code>true</code> to indicate that the column
     *                 name is case sensitive; <code>false</code> otherwise
     * @throws SQLException if a database access error occurs or
     *         the given column number is out of bounds
     */
    public void setCaseSensitive(int columnIndex, boolean property) throws SQLException {
        checkColRange(columnIndex);
        colInfo[columnIndex].caseSensitive = property;
    }

    /**
     * Sets whether a value stored in the designated column can be used
     * in a <code>WHERE</code> clause to the given <code>boolean</code> value.
     *
     * <p>
     *  设置存储在指定列中的值是否可以在给定的<code> boolean </code>值的<code> WHERE </code>子句中使用。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     *                    must be between <code>1</code> and the number
     *                    of columns in the rowset, inclusive
     * @param property <code>true</code> to indicate that a column
     *                 value can be used in a <code>WHERE</code> clause;
     *                 <code>false</code> otherwise
     *
     * @throws SQLException if a database access error occurs or
     *         the given column number is out of bounds
     */
    public void setSearchable(int columnIndex, boolean property)
        throws SQLException {
        checkColRange(columnIndex);
        colInfo[columnIndex].searchable = property;
    }

    /**
     * Sets whether a value stored in the designated column is a cash
     * value to the given <code>boolean</code>.
     *
     * <p>
     *  设置存储在指定列中的值是否为给定<code> boolean </code>的现金值。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     * must be between <code>1</code> and the number of columns,
     * inclusive between <code>1</code> and the number of columns, inclusive
     * @param property true if the value is a cash value; false otherwise.
     * @throws SQLException if a database access error occurs
     *         or the given column number is out of bounds
     */
    public void setCurrency(int columnIndex, boolean property)
        throws SQLException {
        checkColRange(columnIndex);
        colInfo[columnIndex].currency = property;
    }

    /**
     * Sets whether a value stored in the designated column can be set
     * to <code>NULL</code> to the given constant from the interface
     * <code>ResultSetMetaData</code>.
     *
     * <p>
     *  设置存储在指定列中的值是否可以从接口<code> ResultSetMetaData </code>中设置为给定常量的<code> NULL </code>。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     *        must be between <code>1</code> and the number of columns, inclusive
     * @param property one of the following <code>ResultSetMetaData</code> constants:
     *                 <code>columnNoNulls</code>,
     *                 <code>columnNullable</code>, or
     *                 <code>columnNullableUnknown</code>
     *
     * @throws SQLException if a database access error occurs,
     *         the given column number is out of bounds, or the value supplied
     *         for the <i>property</i> parameter is not one of the following
     *         constants:
     *           <code>ResultSetMetaData.columnNoNulls</code>,
     *           <code>ResultSetMetaData.columnNullable</code>, or
     *           <code>ResultSetMetaData.columnNullableUnknown</code>
     */
    public void setNullable(int columnIndex, int property) throws SQLException {
        if ((property < ResultSetMetaData.columnNoNulls) ||
            property > ResultSetMetaData.columnNullableUnknown) {
                throw new SQLException("Invalid nullable constant set. Must be " +
                    "either columnNoNulls, columnNullable or columnNullableUnknown");
        }
        checkColRange(columnIndex);
        colInfo[columnIndex].nullable = property;
    }

    /**
     * Sets whether a value stored in the designated column is a signed
     * number to the given <code>boolean</code>.
     *
     * <p>
     *  设置指定列中存储的值是给定<code> boolean </code>的有符号数字。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     *        must be between <code>1</code> and the number of columns, inclusive
     * @param property <code>true</code> to indicate that a column
     *                 value is a signed number;
     *                 <code>false</code> to indicate that it is not
     * @throws SQLException if a database access error occurs
     *         or the given column number is out of bounds
     */
    public void setSigned(int columnIndex, boolean property) throws SQLException {
        checkColRange(columnIndex);
        colInfo[columnIndex].signed = property;
    }

    /**
     * Sets the normal maximum number of chars in the designated column
     * to the given number.
     *
     * <p>
     * 将指定列中正常的最大字符数设置为给定数。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     *        must be between <code>1</code> and the number of columns, inclusive
     * @param size the maximum size of the column in chars; must be
     *        <code>0</code> or more
     * @throws SQLException if a database access error occurs,
     *        the given column number is out of bounds, or <i>size</i> is
     *        less than <code>0</code>
     */
    public void setColumnDisplaySize(int columnIndex, int size) throws SQLException {
        if (size < 0) {
            throw new SQLException("Invalid column display size. Cannot be less " +
                "than zero");
        }
        checkColRange(columnIndex);
        colInfo[columnIndex].columnDisplaySize = size;
    }

    /**
     * Sets the suggested column label for use in printouts and
     * displays, if any, to <i>label</i>. If <i>label</i> is
     * <code>null</code>, the column label is set to an empty string
     * ("").
     *
     * <p>
     *  将用于打印输出和显示(如果有)的建议列标签设置为<i>标签</i>。如果<i> label </i>是<code> null </code>,则列标签设置为空字符串("")。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     *        must be between <code>1</code> and the number of columns, inclusive
     * @param label the column label to be used in printouts and displays; if the
     *        column label is <code>null</code>, an empty <code>String</code> is
     *        set
     * @throws SQLException if a database access error occurs
     *         or the given column index is out of bounds
     */
    public void setColumnLabel(int columnIndex, String label) throws SQLException {
        checkColRange(columnIndex);
        if (label != null) {
            colInfo[columnIndex].columnLabel = label;
        } else {
            colInfo[columnIndex].columnLabel = "";
        }
    }

    /**
     * Sets the column name of the designated column to the given name.
     *
     * <p>
     *  将指定列的列名设置为给定名称。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     *      must be between <code>1</code> and the number of columns, inclusive
     * @param columnName a <code>String</code> object indicating the column name;
     *      if the given name is <code>null</code>, an empty <code>String</code>
     *      is set
     * @throws SQLException if a database access error occurs or the given column
     *      index is out of bounds
     */
    public void setColumnName(int columnIndex, String columnName) throws SQLException {
        checkColRange(columnIndex);
        if (columnName != null) {
            colInfo[columnIndex].columnName = columnName;
        } else {
            colInfo[columnIndex].columnName = "";
        }
    }

    /**
     * Sets the designated column's table's schema name, if any, to
     * <i>schemaName</i>. If <i>schemaName</i> is <code>null</code>,
     * the schema name is set to an empty string ("").
     *
     * <p>
     *  将指定列的表的模式名称(如果有)设置为<i> schemaName </i>。如果<i> schemaName </i>是<code> null </code>,则模式名称将设置为空字符串("")。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     *        must be between <code>1</code> and the number of columns, inclusive
     * @param schemaName the schema name for the table from which a value in the
     *        designated column was derived; may be an empty <code>String</code>
     *        or <code>null</code>
     * @throws SQLException if a database access error occurs
     *        or the given column number is out of bounds
     */
    public void setSchemaName(int columnIndex, String schemaName) throws SQLException {
        checkColRange(columnIndex);
        if (schemaName != null ) {
            colInfo[columnIndex].schemaName = schemaName;
        } else {
            colInfo[columnIndex].schemaName = "";
        }
    }

    /**
     * Sets the total number of decimal digits in a value stored in the
     * designated column to the given number.
     *
     * <p>
     *  将存储在指定列中的值中的小数位数总和设置为给定数。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     *        must be between <code>1</code> and the number of columns, inclusive
     * @param precision the total number of decimal digits; must be <code>0</code>
     *        or more
     * @throws SQLException if a database access error occurs,
     *         <i>columnIndex</i> is out of bounds, or <i>precision</i>
     *         is less than <code>0</code>
     */
    public void setPrecision(int columnIndex, int precision) throws SQLException {

        if (precision < 0) {
            throw new SQLException("Invalid precision value. Cannot be less " +
                "than zero");
        }
        checkColRange(columnIndex);
        colInfo[columnIndex].colPrecision = precision;
    }

    /**
     * Sets the number of digits to the right of the decimal point in a value
     * stored in the designated column to the given number.
     *
     * <p>
     *  将存储在指定列中的值中的小数点右边的位数设置为给定数。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     *        must be between <code>1</code> and the number of columns, inclusive
     * @param scale the number of digits to the right of the decimal point; must be
     *        zero or greater
     * @throws SQLException if a database access error occurs,
     *         <i>columnIndex</i> is out of bounds, or <i>scale</i>
     *         is less than <code>0</code>
     */
    public void setScale(int columnIndex, int scale) throws SQLException {
        if (scale < 0) {
            throw new SQLException("Invalid scale size. Cannot be less " +
                "than zero");
        }
        checkColRange(columnIndex);
        colInfo[columnIndex].colScale = scale;
    }

    /**
     * Sets the name of the table from which the designated column
     * was derived to the given table name.
     *
     * <p>
     *  将指定列从中派生的表的名称设置为给定的表名。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     *        must be between <code>1</code> and the number of columns, inclusive
     * @param tableName the column's table name; may be <code>null</code> or an
     *        empty string
     * @throws SQLException if a database access error occurs
     *         or the given column number is out of bounds
     */
    public void setTableName(int columnIndex, String tableName) throws SQLException {
        checkColRange(columnIndex);
        if (tableName != null) {
            colInfo[columnIndex].tableName = tableName;
        } else {
            colInfo[columnIndex].tableName = "";
        }
    }

    /**
     * Sets the catalog name of the table from which the designated
     * column was derived to <i>catalogName</i>. If <i>catalogName</i>
     * is <code>null</code>, the catalog name is set to an empty string.
     *
     * <p>
     *  将导出指定列的表的目录名称设置为<i> catalogName </i>。如果<i> catalogName </i>是<code> null </code>,则目录名称设置为空字符串。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     *        must be between <code>1</code> and the number of columns, inclusive
     * @param catalogName the column's table's catalog name; if the catalogName
     *        is <code>null</code>, an empty <code>String</code> is set
     * @throws SQLException if a database access error occurs
     *         or the given column number is out of bounds
     */
    public void setCatalogName(int columnIndex, String catalogName) throws SQLException {
        checkColRange(columnIndex);
        if (catalogName != null)
            colInfo[columnIndex].catName = catalogName;
        else
            colInfo[columnIndex].catName = "";
    }

    /**
     * Sets the SQL type code for values stored in the designated column
     * to the given type code from the class <code>java.sql.Types</code>.
     *
     * <p>
     *  将存储在指定列中的值的SQL类型代码从类<code> java.sql.Types </code>中设置为给定类型代码。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     *        must be between <code>1</code> and the number of columns, inclusive
     * @param SQLType the designated column's SQL type, which must be one of the
     *                constants in the class <code>java.sql.Types</code>
     * @throws SQLException if a database access error occurs,
     *         the given column number is out of bounds, or the column type
     *         specified is not one of the constants in
     *         <code>java.sql.Types</code>
     * @see java.sql.Types
     */
    public void setColumnType(int columnIndex, int SQLType) throws SQLException {
        // examine java.sql.Type reflectively, loop on the fields and check
        // this. Separate out into a private method
        checkColType(SQLType);
        checkColRange(columnIndex);
        colInfo[columnIndex].colType = SQLType;
    }

    /**
     * Sets the type name used by the data source for values stored in the
     * designated column to the given type name.
     *
     * <p>
     *  将存储在指定列中的值的数据源使用的类型名设置为给定类型名称。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     * must be between <code>1</code> and the number of columns, inclusive
     * @param typeName the data source-specific type name; if <i>typeName</i> is
     *        <code>null</code>, an empty <code>String</code> is set
     * @throws SQLException if a database access error occurs
     *         or the given column number is out of bounds
     */
    public void setColumnTypeName(int columnIndex, String typeName)
        throws SQLException {
        checkColRange(columnIndex);
        if (typeName != null) {
            colInfo[columnIndex].colTypeName = typeName;
        } else {
            colInfo[columnIndex].colTypeName = "";
        }
    }

    /**
     * Retrieves the number of columns in the <code>RowSet</code> object
     * for which this <code>RowSetMetaDataImpl</code> object was created.
     *
     * <p>
     *  检索在创建此<Code> RowSetMetaDataImpl </code>对象的<code> RowSet </code>对象中的列数。
     * 
     * 
     * @return the number of columns
     * @throws SQLException if an error occurs determining the column count
     */
    public int getColumnCount() throws SQLException {
        return colCount;
    }

    /**
     * Retrieves whether a value stored in the designated column is
     * automatically numbered, and thus readonly.
     *
     * <p>
     * 检索存储在指定列中的值是否自动编号,因此只读。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     *         must be between <code>1</code> and the number of columns, inclusive
     * @return <code>true</code> if the column is automatically numbered;
     *         <code>false</code> otherwise
     * @throws SQLException if a database access error occurs
     * or the given column number is out of bounds
     */
    public boolean isAutoIncrement(int columnIndex) throws SQLException {
        checkColRange(columnIndex);
        return colInfo[columnIndex].autoIncrement;
    }

    /**
     * Indicates whether the case of the designated column's name
     * matters.
     *
     * <p>
     *  指示指定列的名称的大小写是否重要。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     * must be between <code>1</code> and the number of columns, inclusive
     * @return <code>true</code> if the column name is case sensitive;
     *          <code>false</code> otherwise
     * @throws SQLException if a database access error occurs
     * or the given column number is out of bounds
     */
    public boolean isCaseSensitive(int columnIndex) throws SQLException {
        checkColRange(columnIndex);
        return colInfo[columnIndex].caseSensitive;
    }

    /**
     * Indicates whether a value stored in the designated column
     * can be used in a <code>WHERE</code> clause.
     *
     * <p>
     *  指示存储在指定列中的值是否可以在<code> WHERE </code>子句中使用。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     *        must be between <code>1</code> and the number of columns, inclusive
     * @return <code>true</code> if a value in the designated column can be used in a
     *         <code>WHERE</code> clause; <code>false</code> otherwise
     * @throws SQLException if a database access error occurs
     *         or the given column number is out of bounds
     */
    public boolean isSearchable(int columnIndex) throws SQLException {
        checkColRange(columnIndex);
        return colInfo[columnIndex].searchable;
    }

    /**
     * Indicates whether a value stored in the designated column
     * is a cash value.
     *
     * <p>
     *  指示存储在指定列中的值是否为现金值。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     *        must be between <code>1</code> and the number of columns, inclusive
     * @return <code>true</code> if a value in the designated column is a cash value;
     *         <code>false</code> otherwise
     * @throws SQLException if a database access error occurs
     *         or the given column number is out of bounds
     */
    public boolean isCurrency(int columnIndex) throws SQLException {
        checkColRange(columnIndex);
        return colInfo[columnIndex].currency;
    }

    /**
     * Retrieves a constant indicating whether it is possible
     * to store a <code>NULL</code> value in the designated column.
     *
     * <p>
     *  检索指示是否可能在指定列中存储<code> NULL </code>值的常量。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     *        must be between <code>1</code> and the number of columns, inclusive
     * @return a constant from the <code>ResultSetMetaData</code> interface;
     *         either <code>columnNoNulls</code>,
     *         <code>columnNullable</code>, or
     *         <code>columnNullableUnknown</code>
     * @throws SQLException if a database access error occurs
     *         or the given column number is out of bounds
     */
    public int isNullable(int columnIndex) throws SQLException {
        checkColRange(columnIndex);
        return colInfo[columnIndex].nullable;
    }

    /**
     * Indicates whether a value stored in the designated column is
     * a signed number.
     *
     * <p>
     *  指示存储在指定列中的值是否为有符号数字。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     *        must be between <code>1</code> and the number of columns, inclusive
     * @return <code>true</code> if if a value in the designated column is a signed
     *         number; <code>false</code> otherwise
     * @throws SQLException if a database access error occurs
     *         or the given column number is out of bounds
     */
    public boolean isSigned(int columnIndex) throws SQLException {
        checkColRange(columnIndex);
        return colInfo[columnIndex].signed;
    }

    /**
     * Retrieves the normal maximum width in chars of the designated column.
     *
     * <p>
     *  检索指定列的字符的正常最大宽度。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     *        must be between <code>1</code> and the number of columns, inclusive
     * @return the maximum number of chars that can be displayed in the designated
     *         column
     * @throws SQLException if a database access error occurs
     *         or the given column number is out of bounds
     */
    public int getColumnDisplaySize(int columnIndex) throws SQLException {
        checkColRange(columnIndex);
        return colInfo[columnIndex].columnDisplaySize;
    }

    /**
     * Retrieves the the suggested column title for the designated
     * column for use in printouts and displays.
     *
     * <p>
     *  检索用于打印输出和显示的指定列的建议列标题。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     *        must be between <code>1</code> and the number of columns, inclusive
     * @return the suggested column name to use in printouts and displays
     * @throws SQLException if a database access error occurs
     *         or the given column number is out of bounds
     */
    public String getColumnLabel(int columnIndex) throws SQLException {
        checkColRange(columnIndex);
        return colInfo[columnIndex].columnLabel;
    }

    /**
     * Retrieves the name of the designated column.
     *
     * <p>
     *  检索指定列的名称。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     * must be between <code>1</code> and the number of columns, inclusive
     * @return the column name of the designated column
     * @throws SQLException if a database access error occurs
     * or the given column number is out of bounds
     */
    public String getColumnName(int columnIndex) throws SQLException {
        checkColRange(columnIndex);
        return colInfo[columnIndex].columnName;
    }

    /**
     * Retrieves the schema name of the table from which the value
     * in the designated column was derived.
     *
     * <p>
     *  检索从中导出指定列中的值的表的模式名称。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     *         must be between <code>1</code> and the number of columns,
     *         inclusive
     * @return the schema name or an empty <code>String</code> if no schema
     *         name is available
     * @throws SQLException if a database access error occurs
     * or the given column number is out of bounds
     */
    public String getSchemaName(int columnIndex) throws SQLException {
        checkColRange(columnIndex);
        String str ="";
        if(colInfo[columnIndex].schemaName == null){
        } else {
              str = colInfo[columnIndex].schemaName;
        }
        return str;
    }

    /**
     * Retrieves the total number of digits for values stored in
     * the designated column.
     *
     * <p>
     *  检索存储在指定列中的值的总位数。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     * must be between <code>1</code> and the number of columns, inclusive
     * @return the precision for values stored in the designated column
     * @throws SQLException if a database access error occurs
     * or the given column number is out of bounds
     */
    public int getPrecision(int columnIndex) throws SQLException {
        checkColRange(columnIndex);
        return colInfo[columnIndex].colPrecision;
    }

    /**
     * Retrieves the number of digits to the right of the decimal point
     * for values stored in the designated column.
     *
     * <p>
     *  检索存储在指定列中的值的小数点右边的位数。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     * must be between <code>1</code> and the number of columns, inclusive
     * @return the scale for values stored in the designated column
     * @throws SQLException if a database access error occurs
     * or the given column number is out of bounds
     */
    public int getScale(int columnIndex) throws SQLException {
        checkColRange(columnIndex);
        return colInfo[columnIndex].colScale;
    }

    /**
     * Retrieves the name of the table from which the value
     * in the designated column was derived.
     *
     * <p>
     *  检索从中导出指定列中的值的表的名称。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     * must be between <code>1</code> and the number of columns, inclusive
     * @return the table name or an empty <code>String</code> if no table name
     *         is available
     * @throws SQLException if a database access error occurs
     * or the given column number is out of bounds
     */
    public String getTableName(int columnIndex) throws SQLException {
        checkColRange(columnIndex);
        return colInfo[columnIndex].tableName;
    }

    /**
     * Retrieves the catalog name of the table from which the value
     * in the designated column was derived.
     *
     * <p>
     *  检索从中导出指定列中的值的表的目录名称。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     * must be between <code>1</code> and the number of columns, inclusive
     * @return the catalog name of the column's table or an empty
     *         <code>String</code> if no catalog name is available
     * @throws SQLException if a database access error occurs
     * or the given column number is out of bounds
     */
    public String getCatalogName(int columnIndex) throws SQLException {
        checkColRange(columnIndex);
        String str ="";
        if(colInfo[columnIndex].catName == null){
        } else {
           str = colInfo[columnIndex].catName;
        }
        return str;
    }

    /**
     * Retrieves the type code (one of the <code>java.sql.Types</code>
     * constants) for the SQL type of the value stored in the
     * designated column.
     *
     * <p>
     *  检索存储在指定列中的值的SQL类型的类型代码(<code> java.sql.Types </code>常量之一)。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     * must be between <code>1</code> and the number of columns, inclusive
     * @return an <code>int</code> representing the SQL type of values
     * stored in the designated column
     * @throws SQLException if a database access error occurs
     * or the given column number is out of bounds
     * @see java.sql.Types
     */
    public int getColumnType(int columnIndex) throws SQLException {
        checkColRange(columnIndex);
        return colInfo[columnIndex].colType;
    }

    /**
     * Retrieves the DBMS-specific type name for values stored in the
     * designated column.
     *
     * <p>
     *  检索存储在指定列中的值的DBMS特定类型名称。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     * must be between <code>1</code> and the number of columns, inclusive
     * @return the type name used by the data source
     * @throws SQLException if a database access error occurs
     * or the given column number is out of bounds
     */
    public String getColumnTypeName(int columnIndex) throws SQLException {
        checkColRange(columnIndex);
        return colInfo[columnIndex].colTypeName;
    }


    /**
     * Indicates whether the designated column is definitely
     * not writable, thus readonly.
     *
     * <p>
     * 指示指定的列是否绝对不可写,因此只读。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     * must be between <code>1</code> and the number of columns, inclusive
     * @return <code>true</code> if this <code>RowSet</code> object is read-Only
     * and thus not updatable; <code>false</code> otherwise
     * @throws SQLException if a database access error occurs
     * or the given column number is out of bounds
     */
    public boolean isReadOnly(int columnIndex) throws SQLException {
        checkColRange(columnIndex);
        return colInfo[columnIndex].readOnly;
    }

    /**
     * Indicates whether it is possible for a write operation on
     * the designated column to succeed. A return value of
     * <code>true</code> means that a write operation may or may
     * not succeed.
     *
     * <p>
     *  指示指定列上的写操作是否可能成功。返回值<code> true </code>表示写操作可能成功,也可能不成功。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     *         must be between <code>1</code> and the number of columns, inclusive
     * @return <code>true</code> if a write operation on the designated column may
     *          will succeed; <code>false</code> otherwise
     * @throws SQLException if a database access error occurs
     *         or the given column number is out of bounds
     */
    public boolean isWritable(int columnIndex) throws SQLException {
        checkColRange(columnIndex);
        return colInfo[columnIndex].writable;
    }

    /**
     * Indicates whether a write operation on the designated column
     * will definitely succeed.
     *
     * <p>
     *  指示指定列上的写操作是否一定成功。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     * must be between <code>1</code> and the number of columns, inclusive
     * @return <code>true</code> if a write operation on the designated column will
     *         definitely succeed; <code>false</code> otherwise
     * @throws SQLException if a database access error occurs
     * or the given column number is out of bounds
     */
    public  boolean isDefinitelyWritable(int columnIndex)
        throws SQLException { return true;}

    /**
     * Retrieves the fully-qualified name of the class in the Java
     * programming language to which a value in the designated column
     * will be mapped.  For example, if the value is an <code>int</code>,
     * the class name returned by this method will be
     * <code>java.lang.Integer</code>.
     * <P>
     * If the value in the designated column has a custom mapping,
     * this method returns the name of the class that implements
     * <code>SQLData</code>. When the method <code>ResultSet.getObject</code>
     * is called to retrieve a value from the designated column, it will
     * create an instance of this class or one of its subclasses.
     *
     * <p>
     *  以指定列中的值将映射到的Java编程语言检索类的完全限定名称。
     * 例如,如果值是<code> int </code>,则此方法返回的类名将为<code> java.lang.Integer </code>。
     * <P>
     *  如果指定列中的值具有自定义映射,则此方法将返回实现<code> SQLData </code>的类的名称。
     * 当方法<code> ResultSet.getObject </code>被调用以从指定列检索值时,它将创建此类或其子类之一的实例。
     * 
     * 
     * @param columnIndex the first column is 1, the second is 2, and so on;
     *        must be between <code>1</code> and the number of columns, inclusive
     * @return the fully-qualified name of the class in the Java programming
     *        language that would be used by the method <code>RowSet.getObject</code> to
     *        retrieve the value in the specified column. This is the class
     *        name used for custom mapping when there is a custom mapping.
     * @throws SQLException if a database access error occurs
     *         or the given column number is out of bounds
     */
    public String getColumnClassName(int columnIndex) throws SQLException {
        String className = String.class.getName();

        int sqlType = getColumnType(columnIndex);

        switch (sqlType) {

        case Types.NUMERIC:
        case Types.DECIMAL:
            className = java.math.BigDecimal.class.getName();
            break;

        case Types.BIT:
            className = java.lang.Boolean.class.getName();
            break;

        case Types.TINYINT:
            className = java.lang.Byte.class.getName();
            break;

        case Types.SMALLINT:
            className = java.lang.Short.class.getName();
            break;

        case Types.INTEGER:
            className = java.lang.Integer.class.getName();
            break;

        case Types.BIGINT:
            className = java.lang.Long.class.getName();
            break;

        case Types.REAL:
            className = java.lang.Float.class.getName();
            break;

        case Types.FLOAT:
        case Types.DOUBLE:
            className = java.lang.Double.class.getName();
            break;

        case Types.BINARY:
        case Types.VARBINARY:
        case Types.LONGVARBINARY:
            className = "byte[]";
            break;

        case Types.DATE:
            className = java.sql.Date.class.getName();
            break;

        case Types.TIME:
            className = java.sql.Time.class.getName();
            break;

        case Types.TIMESTAMP:
            className = java.sql.Timestamp.class.getName();
            break;

        case Types.BLOB:
            className = java.sql.Blob.class.getName();
            break;

        case Types.CLOB:
            className = java.sql.Clob.class.getName();
            break;
        }

        return className;
    }

    /**
     * Returns an object that implements the given interface to allow access to non-standard methods,
     * or standard methods not exposed by the proxy.
     * The result may be either the object found to implement the interface or a proxy for that object.
     * If the receiver implements the interface then that is the object. If the receiver is a wrapper
     * and the wrapped object implements the interface then that is the object. Otherwise the object is
     *  the result of calling <code>unwrap</code> recursively on the wrapped object. If the receiver is not a
     * wrapper and does not implement the interface, then an <code>SQLException</code> is thrown.
     *
     * <p>
     * 返回实现给定接口的对象,以允许访问非标准方法或代理未公开的标准方法。结果可能是找到的实现接口的对象或该对象的代理。如果接收器实现接口,那么就是对象。
     * 如果接收器是包装器,并且包装的对象实现接口,那么它就是对象。否则,该对象是在包装对象上递归调用<code> unwrap </code>的结果。
     * 如果接收者不是包装器并且不实现接口,则抛出<code> SQLException </code>。
     * 
     * 
     * @param iface A Class defining an interface that the result must implement.
     * @return an object that implements the interface. May be a proxy for the actual implementing object.
     * @throws java.sql.SQLException If no object found that implements the interface
     * @since 1.6
     */
    public <T> T unwrap(java.lang.Class<T> iface) throws java.sql.SQLException {

        if(isWrapperFor(iface)) {
            return iface.cast(this);
        } else {
            throw new SQLException("unwrap failed for:"+ iface);
        }
    }

    /**
     * Returns true if this either implements the interface argument or is directly or indirectly a wrapper
     * for an object that does. Returns false otherwise. If this implements the interface then return true,
     * else if this is a wrapper then return the result of recursively calling <code>isWrapperFor</code> on the wrapped
     * object. If this does not implement the interface and is not a wrapper, return false.
     * This method should be implemented as a low-cost operation compared to <code>unwrap</code> so that
     * callers can use this method to avoid expensive <code>unwrap</code> calls that may fail. If this method
     * returns true then calling <code>unwrap</code> with the same argument should succeed.
     *
     * <p>
     *  如果这或者实现了接口参数,或者直接或间接地是一个对象的包装器,则返回true。否则返回false。
     * 如果这实现了接口然后返回true,否则如果这是一个包装,然后返回递归调用包装对象上的<code> isWrapperFor </code>的结果。如果这不实现接口并且不是包装器,返回false。
     * 与<code> unwrap </code>相比,该方法应该作为一种低成本操作来实现,以便调用者可以使用此方法避免可能失败的昂贵的<code> unwrap </code>调用。
     * 如果这个方法返回true,那么调用具有相同参数的<code> unwrap </code>应该会成功。
     * 
     * 
     * @param interfaces a Class defining an interface.
     * @return true if this implements the interface or directly or indirectly wraps an object that does.
     * @throws java.sql.SQLException  if an error occurs while determining whether this is a wrapper
     * for an object with the given interface.
     * @since 1.6
     */
    public boolean isWrapperFor(Class<?> interfaces) throws SQLException {
        return interfaces.isInstance(this);
    }

    static final long serialVersionUID = 6893806403181801867L;

    private class ColInfo implements Serializable {
        /**
         * The field that indicates whether the value in this column is a number
         * that is incremented automatically, which makes the value read-only.
         * <code>true</code> means that the value in this column
         * is automatically numbered; <code>false</code> means that it is not.
         *
         * <p>
         * 指示此列中的值是否为自动递增的数字的字段,使该值为只读。 <code> true </code>表示此列中的值自动编号; <code> false </code>意味着它不是。
         * 
         * 
         * @serial
         */
        public boolean autoIncrement;

        /**
         * The field that indicates whether the value in this column is case sensitive.
         * <code>true</code> means that it is; <code>false</code> that it is not.
         *
         * <p>
         *  指示此列中的值是区分大小写的字段。 <code> true </code>表示它是; <code> false </code>它不是。
         * 
         * 
         * @serial
         */
        public boolean caseSensitive;

        /**
         * The field that indicates whether the value in this column is a cash value
         * <code>true</code> means that it is; <code>false</code> that it is not.
         *
         * <p>
         *  指示此列中的值是否为现金值<code> true </code>的字段表示它是; <code> false </code>它不是。
         * 
         * 
         * @serial
         */
        public boolean currency;

        /**
         * The field that indicates whether the value in this column is nullable.
         * The possible values are the <code>ResultSet</code> constants
         * <code>columnNoNulls</code>, <code>columnNullable</code>, and
         * <code>columnNullableUnknown</code>.
         *
         * <p>
         *  指示此列中的值是否可为空的字段。
         * 可能的值是<code> ResultSet </code>常量<code> columnNoNulls </code>,<code> columnNullable </code>和<code> colu
         * mnNullableUnknown </code>。
         *  指示此列中的值是否可为空的字段。
         * 
         * 
         * @serial
         */
        public int nullable;

        /**
         * The field that indicates whether the value in this column is a signed number.
         * <code>true</code> means that it is; <code>false</code> that it is not.
         *
         * <p>
         *  指示此列中的值是否为有符号数字的字段。 <code> true </code>表示它是; <code> false </code>它不是。
         * 
         * 
         * @serial
         */
        public boolean signed;

        /**
         * The field that indicates whether the value in this column can be used in
         * a <code>WHERE</code> clause.
         * <code>true</code> means that it can; <code>false</code> that it cannot.
         *
         * <p>
         *  指示此列中的值是否可在<code> WHERE </code>子句中使用的字段。 <code> true </code>意味着它可以; <code> false </code>,它不能。
         * 
         * 
         * @serial
         */
        public boolean searchable;

        /**
         * The field that indicates the normal maximum width in characters for
         * this column.
         *
         * <p>
         *  指示此列的正常最大宽度(以字符为单位)的字段。
         * 
         * 
         * @serial
         */
        public int columnDisplaySize;

        /**
         * The field that holds the suggested column title for this column, to be
         * used in printing and displays.
         *
         * <p>
         *  包含此列的建议列标题的字段,用于打印和显示。
         * 
         * 
         * @serial
         */
        public String columnLabel;

        /**
         * The field that holds the name of this column.
         *
         * <p>
         *  包含此列的名称的字段。
         * 
         * 
         * @serial
         */
        public  String columnName;

        /**
         * The field that holds the schema name for the table from which this column
         * was derived.
         *
         * <p>
         *  保存导出此列的表的模式名称的字段。
         * 
         * 
         * @serial
         */
        public String schemaName;

        /**
         * The field that holds the precision of the value in this column.  For number
         * types, the precision is the total number of decimal digits; for character types,
         * it is the maximum number of characters; for binary types, it is the maximum
         * length in bytes.
         *
         * <p>
         * 保存此列中的值的精度的字段。对于数字类型,精度是十进制数的总数;对于字符类型,它是最大字符数;对于二进制类型,它是最大长度(以字节为单位)。
         * 
         * 
         * @serial
         */
        public int colPrecision;

        /**
         * The field that holds the scale (number of digits to the right of the decimal
         * point) of the value in this column.
         *
         * <p>
         *  保存此列中的值的刻度(小数点右侧的位数)的字段。
         * 
         * 
         * @serial
         */
        public int colScale;

        /**
         * The field that holds the name of the table from which this column
         * was derived.  This value may be the empty string if there is no
         * table name, such as when this column is produced by a join.
         *
         * <p>
         *  包含导出此列的表的名称的字段。如果没有表名称,则此值可以是空字符串,例如,当此列由连接生成时。
         * 
         * 
         * @serial
         */
        public String tableName ="";

        /**
         * The field that holds the catalog name for the table from which this column
         * was derived.  If the DBMS does not support catalogs, the value may be the
         * empty string.
         *
         * <p>
         *  保存导出此列的表的目录名称的字段。如果DBMS不支持目录,则该值可以是空字符串。
         * 
         * 
         * @serial
         */
        public String catName;

        /**
         * The field that holds the type code from the class <code>java.sql.Types</code>
         * indicating the type of the value in this column.
         *
         * <p>
         *  保存来自类<code> java.sql.Types </code>的类型代码的字段,表示此列中的值的类型。
         * 
         * 
         * @serial
         */
        public int colType;

        /**
         * The field that holds the the type name used by this particular data source
         * for the value stored in this column.
         *
         * <p>
         *  保存此特定数据源对存储在此列中的值使用的类型名称的字段。
         * 
         * 
         * @serial
         */
        public String colTypeName;

        /**
         * The field that holds the updatablity boolean per column of a RowSet
         *
         * <p>
         *  保存RowSet的每个列的updatablity布尔的字段
         * 
         * 
         * @serial
         */
        public boolean readOnly = false;

        /**
         * The field that hold the writable boolean per column of a RowSet
         *
         * <p>
         *  包含RowSet的每列可写布尔的字段
         * 
         *@serial
         */
        public boolean writable = true;

        static final long serialVersionUID = 5490834817919311283L;
    }
}
