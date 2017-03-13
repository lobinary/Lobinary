/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.math.BigDecimal;
import java.util.Calendar;
import java.io.Reader;
import java.io.InputStream;

/**
 * The interface used to execute SQL stored procedures.  The JDBC API
 * provides a stored procedure SQL escape syntax that allows stored procedures
 * to be called in a standard way for all RDBMSs. This escape syntax has one
 * form that includes a result parameter and one that does not. If used, the result
 * parameter must be registered as an OUT parameter. The other parameters
 * can be used for input, output or both. Parameters are referred to
 * sequentially, by number, with the first parameter being 1.
 * <PRE>
 *   {?= call &lt;procedure-name&gt;[(&lt;arg1&gt;,&lt;arg2&gt;, ...)]}
 *   {call &lt;procedure-name&gt;[(&lt;arg1&gt;,&lt;arg2&gt;, ...)]}
 * </PRE>
 * <P>
 * IN parameter values are set using the <code>set</code> methods inherited from
 * {@link PreparedStatement}.  The type of all OUT parameters must be
 * registered prior to executing the stored procedure; their values
 * are retrieved after execution via the <code>get</code> methods provided here.
 * <P>
 * A <code>CallableStatement</code> can return one {@link ResultSet} object or
 * multiple <code>ResultSet</code> objects.  Multiple
 * <code>ResultSet</code> objects are handled using operations
 * inherited from {@link Statement}.
 * <P>
 * For maximum portability, a call's <code>ResultSet</code> objects and
 * update counts should be processed prior to getting the values of output
 * parameters.
 *
 *
 * <p>
 *  用于执行SQL存储过程的接口JDBC API提供了一个存储过程SQL转义语法,允许以标准方式为所有RDBMS调用存储过程此转义语法有一种形式,其中包含一个result参数, ,结果参数必须注册为OUT
 * 参数。
 * 其他参数可用于输入,输出或两者。参数按顺序依次引用,第一个参数为1。
 * <PRE>
 *  {?= call&lt; procedure-name&gt; [(&lt; arg1&gt;,&lt; arg2&gt ;,)]} {call&lt; procedure-name&gt; [(&lt; arg1&gt;,&lt; arg2&gt;。
 * </PRE>
 * <P>
 * IN参数值使用继承自{@link PreparedStatement}的<code> set </code>方法设置所有OUT参数的类型必须在执行存储过程之前注册;它们的值在执行后通过此处提供的<code>
 *  get </code>方法检索。
 * <P>
 *  <code> CallableStatement </code>可以返回一个{@link ResultSet}对象或多个<code> ResultSet </code>对象多个<code> Resul
 * tSet </code>对象使用从{@link Statement}。
 * <P>
 *  为了最大的可移植性,在获得输出参数的值之前应该处理调用的<code> ResultSet </code>对象和更新计数
 * 
 * 
 * @see Connection#prepareCall
 * @see ResultSet
 */

public interface CallableStatement extends PreparedStatement {

    /**
     * Registers the OUT parameter in ordinal position
     * <code>parameterIndex</code> to the JDBC type
     * <code>sqlType</code>.  All OUT parameters must be registered
     * before a stored procedure is executed.
     * <p>
     * The JDBC type specified by <code>sqlType</code> for an OUT
     * parameter determines the Java type that must be used
     * in the <code>get</code> method to read the value of that parameter.
     * <p>
     * If the JDBC type expected to be returned to this output parameter
     * is specific to this particular database, <code>sqlType</code>
     * should be <code>java.sql.Types.OTHER</code>.  The method
     * {@link #getObject} retrieves the value.
     *
     * <p>
     * 将顺序位置</code>中的OUT参数注册到JDBC类型<code> sqlType </code>所有OUT参数必须在执行存储过程之前注册
     * <p>
     *  由<code> sqlType </code>为OUT参数指定的JDBC类型确定在<code> get </code>方法中必须使用的Java类型,以读取该参数的值
     * <p>
     *  如果希望返回到此输出参数的JDBC类型特定于此特定数据库,则<code> sqlType </code>应为<code> javasqlTypesOTHER </code>方法{@link #getObject}
     * 。
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2,
     *        and so on
     * @param sqlType the JDBC type code defined by <code>java.sql.Types</code>.
     *        If the parameter is of JDBC type <code>NUMERIC</code>
     *        or <code>DECIMAL</code>, the version of
     *        <code>registerOutParameter</code> that accepts a scale value
     *        should be used.
     *
     * @exception SQLException if the parameterIndex is not valid;
     * if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if <code>sqlType</code> is
     * a <code>ARRAY</code>, <code>BLOB</code>, <code>CLOB</code>,
     * <code>DATALINK</code>, <code>JAVA_OBJECT</code>, <code>NCHAR</code>,
     * <code>NCLOB</code>, <code>NVARCHAR</code>, <code>LONGNVARCHAR</code>,
     *  <code>REF</code>, <code>ROWID</code>, <code>SQLXML</code>
     * or  <code>STRUCT</code> data type and the JDBC driver does not support
     * this data type
     * @see Types
     */
    void registerOutParameter(int parameterIndex, int sqlType)
        throws SQLException;

    /**
     * Registers the parameter in ordinal position
     * <code>parameterIndex</code> to be of JDBC type
     * <code>sqlType</code>. All OUT parameters must be registered
     * before a stored procedure is executed.
     * <p>
     * The JDBC type specified by <code>sqlType</code> for an OUT
     * parameter determines the Java type that must be used
     * in the <code>get</code> method to read the value of that parameter.
     * <p>
     * This version of <code>registerOutParameter</code> should be
     * used when the parameter is of JDBC type <code>NUMERIC</code>
     * or <code>DECIMAL</code>.
     *
     * <p>
     *  将顺序位置中的参数<code> parameterIndex </code>注册为JDBC类型<code> sqlType </code>所有OUT参数必须在执行存储过程之前注册
     * <p>
     * 由<code> sqlType </code>为OUT参数指定的JDBC类型确定在<code> get </code>方法中必须使用的Java类型,以读取该参数的值
     * <p>
     *  当参数是JDBC类型<code> NUMERIC </code>或<code> DECIMAL </code>时,应使用此版本的<code> registerOutParameter </code>。
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2,
     * and so on
     * @param sqlType the SQL type code defined by <code>java.sql.Types</code>.
     * @param scale the desired number of digits to the right of the
     * decimal point.  It must be greater than or equal to zero.
     * @exception SQLException if the parameterIndex is not valid;
     * if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if <code>sqlType</code> is
     * a <code>ARRAY</code>, <code>BLOB</code>, <code>CLOB</code>,
     * <code>DATALINK</code>, <code>JAVA_OBJECT</code>, <code>NCHAR</code>,
     * <code>NCLOB</code>, <code>NVARCHAR</code>, <code>LONGNVARCHAR</code>,
     *  <code>REF</code>, <code>ROWID</code>, <code>SQLXML</code>
     * or  <code>STRUCT</code> data type and the JDBC driver does not support
     * this data type
     * @see Types
     */
    void registerOutParameter(int parameterIndex, int sqlType, int scale)
        throws SQLException;

    /**
     * Retrieves whether the last OUT parameter read had the value of
     * SQL <code>NULL</code>.  Note that this method should be called only after
     * calling a getter method; otherwise, there is no value to use in
     * determining whether it is <code>null</code> or not.
     *
     * <p>
     *  检索最后一个OUT参数是否具有SQL <code> NULL </code>的值注意,这个方法只有在调用getter方法之后才被调用;否则,没有值用于确定是否<code> null </code>
     * 
     * 
     * @return <code>true</code> if the last parameter read was SQL
     * <code>NULL</code>; <code>false</code> otherwise
     * @exception SQLException if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     */
    boolean wasNull() throws SQLException;

    /**
     * Retrieves the value of the designated JDBC <code>CHAR</code>,
     * <code>VARCHAR</code>, or <code>LONGVARCHAR</code> parameter as a
     * <code>String</code> in the Java programming language.
     * <p>
     * For the fixed-length type JDBC <code>CHAR</code>,
     * the <code>String</code> object
     * returned has exactly the same value the SQL
     * <code>CHAR</code> value had in the
     * database, including any padding added by the database.
     *
     * <p>
     *  在Java编程语言中将<code> CHAR </code>,<code> VARCHAR </code>或<code> LONGVARCHAR </code>参数的值检索为<code> String
     *  </code>。
     * <p>
     * 对于固定长度类型JDBC <code> CHAR </code>,返回的<code> String </code>对象与数据库中的SQL <code> CHAR </code>值完全相同,填充由数据库添
     * 加。
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2,
     * and so on
     * @return the parameter value. If the value is SQL <code>NULL</code>,
     *         the result
     *         is <code>null</code>.
     * @exception SQLException if the parameterIndex is not valid;
     * if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @see #setString
     */
    String getString(int parameterIndex) throws SQLException;

    /**
     * Retrieves the value of the designated JDBC <code>BIT</code>
     * or <code>BOOLEAN</code> parameter as a
     * <code>boolean</code> in the Java programming language.
     *
     * <p>
     *  在Java编程语言中将指定的JDBC <code> BIT </code>或<code> BOOLEAN </code>参数的值检索为<code> boolean </code>
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2,
     *        and so on
     * @return the parameter value.  If the value is SQL <code>NULL</code>,
     *         the result is <code>false</code>.
     * @exception SQLException if the parameterIndex is not valid;
     * if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @see #setBoolean
     */
    boolean getBoolean(int parameterIndex) throws SQLException;

    /**
     * Retrieves the value of the designated JDBC <code>TINYINT</code> parameter
     * as a <code>byte</code> in the Java programming language.
     *
     * <p>
     *  在Java编程语言中将指定的JDBC <code> TINYINT </code>参数的值检索为<code> byte </code>
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2,
     * and so on
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     * is <code>0</code>.
     * @exception SQLException if the parameterIndex is not valid;
     * if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @see #setByte
     */
    byte getByte(int parameterIndex) throws SQLException;

    /**
     * Retrieves the value of the designated JDBC <code>SMALLINT</code> parameter
     * as a <code>short</code> in the Java programming language.
     *
     * <p>
     *  在Java编程语言中将指定的JDBC <code> SMALLINT </code>参数的值作为<code> short </code>
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2,
     * and so on
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     * is <code>0</code>.
     * @exception SQLException if the parameterIndex is not valid;
     * if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @see #setShort
     */
    short getShort(int parameterIndex) throws SQLException;

    /**
     * Retrieves the value of the designated JDBC <code>INTEGER</code> parameter
     * as an <code>int</code> in the Java programming language.
     *
     * <p>
     *  在Java编程语言中将指定的JDBC <code> INTEGER </code>参数的值作为<code> int </code>
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2,
     * and so on
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     * is <code>0</code>.
     * @exception SQLException if the parameterIndex is not valid;
     * if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @see #setInt
     */
    int getInt(int parameterIndex) throws SQLException;

    /**
     * Retrieves the value of the designated JDBC <code>BIGINT</code> parameter
     * as a <code>long</code> in the Java programming language.
     *
     * <p>
     * 在Java编程语言中将指定的JDBC <code> BIGINT </code>参数的值作为<code> long </code>
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2,
     * and so on
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     * is <code>0</code>.
     * @exception SQLException if the parameterIndex is not valid;
     * if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @see #setLong
     */
    long getLong(int parameterIndex) throws SQLException;

    /**
     * Retrieves the value of the designated JDBC <code>FLOAT</code> parameter
     * as a <code>float</code> in the Java programming language.
     *
     * <p>
     *  在Java编程语言中将指定的JDBC <code> FLOAT </code>参数的值检索为<code> float </code>
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2,
     *        and so on
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     *         is <code>0</code>.
     * @exception SQLException if the parameterIndex is not valid;
     * if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @see #setFloat
     */
    float getFloat(int parameterIndex) throws SQLException;

    /**
     * Retrieves the value of the designated JDBC <code>DOUBLE</code> parameter as a <code>double</code>
     * in the Java programming language.
     * <p>
     *  在Java编程语言中将指定的JDBC <code> DOUBLE </code>参数的值检索为<code> double </code>
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2,
     *        and so on
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     *         is <code>0</code>.
     * @exception SQLException if the parameterIndex is not valid;
     * if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @see #setDouble
     */
    double getDouble(int parameterIndex) throws SQLException;

    /**
     * Retrieves the value of the designated JDBC <code>NUMERIC</code> parameter as a
     * <code>java.math.BigDecimal</code> object with <i>scale</i> digits to
     * the right of the decimal point.
     * <p>
     *  将指定的JDBC <code> NUMERIC </code>参数的值作为<code> javamathBigDecimal </code>对象检索,小数点右侧的<i> scale </i>
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2,
     *        and so on
     * @param scale the number of digits to the right of the decimal point
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     *         is <code>null</code>.
     * @exception SQLException if the parameterIndex is not valid;
     * if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @deprecated use <code>getBigDecimal(int parameterIndex)</code>
     *             or <code>getBigDecimal(String parameterName)</code>
     * @see #setBigDecimal
     */
    @Deprecated
    BigDecimal getBigDecimal(int parameterIndex, int scale)
        throws SQLException;

    /**
     * Retrieves the value of the designated JDBC <code>BINARY</code> or
     * <code>VARBINARY</code> parameter as an array of <code>byte</code>
     * values in the Java programming language.
     * <p>
     *  在Java编程语言中将指定的JDBC <code> BINARY </code>或<code> VARBINARY </code>参数的值检索为<code> byte </code>值的数组
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2,
     *        and so on
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     *         is <code>null</code>.
     * @exception SQLException if the parameterIndex is not valid;
     * if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @see #setBytes
     */
    byte[] getBytes(int parameterIndex) throws SQLException;

    /**
     * Retrieves the value of the designated JDBC <code>DATE</code> parameter as a
     * <code>java.sql.Date</code> object.
     * <p>
     * 将指定的JDBC <code> DATE </code>参数的值作为<code> javasqlDate </code>对象
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2,
     *        and so on
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     *         is <code>null</code>.
     * @exception SQLException if the parameterIndex is not valid;
     * if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @see #setDate
     */
    java.sql.Date getDate(int parameterIndex) throws SQLException;

    /**
     * Retrieves the value of the designated JDBC <code>TIME</code> parameter as a
     * <code>java.sql.Time</code> object.
     *
     * <p>
     *  将指定的JDBC <code> TIME </code>参数的值作为<code> javasqlTime </code>对象
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2,
     *        and so on
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     *         is <code>null</code>.
     * @exception SQLException if the parameterIndex is not valid;
     * if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @see #setTime
     */
    java.sql.Time getTime(int parameterIndex) throws SQLException;

    /**
     * Retrieves the value of the designated JDBC <code>TIMESTAMP</code> parameter as a
     * <code>java.sql.Timestamp</code> object.
     *
     * <p>
     *  将指定的JDBC <code> TIMESTAMP </code>参数的值作为<code> javasqlTimestamp </code>对象
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2,
     *        and so on
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     *         is <code>null</code>.
     * @exception SQLException if the parameterIndex is not valid;
     * if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @see #setTimestamp
     */
    java.sql.Timestamp getTimestamp(int parameterIndex)
        throws SQLException;

    //----------------------------------------------------------------------
    // Advanced features:


    /**
     * Retrieves the value of the designated parameter as an <code>Object</code>
     * in the Java programming language. If the value is an SQL <code>NULL</code>,
     * the driver returns a Java <code>null</code>.
     * <p>
     * This method returns a Java object whose type corresponds to the JDBC
     * type that was registered for this parameter using the method
     * <code>registerOutParameter</code>.  By registering the target JDBC
     * type as <code>java.sql.Types.OTHER</code>, this method can be used
     * to read database-specific abstract data types.
     *
     * <p>
     *  在Java编程语言中将指定参数的值检索为<code> Object </code>如果值为SQL <code> NULL </code>,则驱动程序会返回Java <code> null </code>
     * 。
     * <p>
     * 此方法使用方法<code> registerOutParameter </code>返回其类型对应于为此参数注册的JDBC类型的Java对象。
     * 通过将目标JDBC类型注册为<code> javasqlTypesOTHER </code>,此方法可以用于读取特定于数据库的抽象数据类型。
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2,
     *        and so on
     * @return A <code>java.lang.Object</code> holding the OUT parameter value
     * @exception SQLException if the parameterIndex is not valid;
     * if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @see Types
     * @see #setObject
     */
    Object getObject(int parameterIndex) throws SQLException;


    //--------------------------JDBC 2.0-----------------------------

    /**
     * Retrieves the value of the designated JDBC <code>NUMERIC</code> parameter as a
     * <code>java.math.BigDecimal</code> object with as many digits to the
     * right of the decimal point as the value contains.
     * <p>
     *  将指定的JDBC <code> NUMERIC </code>参数的值作为一个<code> javamathBigDecimal </code>对象检索,该对象的小数点右侧的位数与值包含
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2,
     * and so on
     * @return the parameter value in full precision.  If the value is
     * SQL <code>NULL</code>, the result is <code>null</code>.
     * @exception SQLException if the parameterIndex is not valid;
     * if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @see #setBigDecimal
     * @since 1.2
     */
    BigDecimal getBigDecimal(int parameterIndex) throws SQLException;

    /**
     * Returns an object representing the value of OUT parameter
     * <code>parameterIndex</code> and uses <code>map</code> for the custom
     * mapping of the parameter value.
     * <p>
     * This method returns a Java object whose type corresponds to the
     * JDBC type that was registered for this parameter using the method
     * <code>registerOutParameter</code>.  By registering the target
     * JDBC type as <code>java.sql.Types.OTHER</code>, this method can
     * be used to read database-specific abstract data types.
     * <p>
     *  返回表示OUT参数<code> parameterIndex </code>的值的对象,并使用<code> map </code>进行参数值的自定义映射
     * <p>
     * 此方法使用方法<code> registerOutParameter </code>返回其类型对应于为此参数注册的JDBC类型的Java对象。
     * 通过将目标JDBC类型注册为<code> javasqlTypesOTHER </code>,此方法可以用于读取特定于数据库的抽象数据类型。
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2, and so on
     * @param map the mapping from SQL type names to Java classes
     * @return a <code>java.lang.Object</code> holding the OUT parameter value
     * @exception SQLException if the parameterIndex is not valid;
     * if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #setObject
     * @since 1.2
     */
    Object getObject(int parameterIndex, java.util.Map<String,Class<?>> map)
        throws SQLException;

    /**
     * Retrieves the value of the designated JDBC <code>REF(&lt;structured-type&gt;)</code>
     * parameter as a {@link java.sql.Ref} object in the Java programming language.
     * <p>
     *  检索指定的JDBC <code> REF(&lt; structured-type&gt;)</code>参数的值作为Java编程语言中的{@link javasqlRef}
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2,
     * and so on
     * @return the parameter value as a <code>Ref</code> object in the
     * Java programming language.  If the value was SQL <code>NULL</code>, the value
     * <code>null</code> is returned.
     * @exception SQLException if the parameterIndex is not valid;
     * if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.2
     */
    Ref getRef (int parameterIndex) throws SQLException;

    /**
     * Retrieves the value of the designated JDBC <code>BLOB</code> parameter as a
     * {@link java.sql.Blob} object in the Java programming language.
     * <p>
     *  将指定的JDBC <code> BLOB </code>参数的值作为Java编程语言中的{@link javasqlBlob}对象检索
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2, and so on
     * @return the parameter value as a <code>Blob</code> object in the
     * Java programming language.  If the value was SQL <code>NULL</code>, the value
     * <code>null</code> is returned.
     * @exception SQLException if the parameterIndex is not valid;
     * if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.2
     */
    Blob getBlob (int parameterIndex) throws SQLException;

    /**
     * Retrieves the value of the designated JDBC <code>CLOB</code> parameter as a
     * <code>java.sql.Clob</code> object in the Java programming language.
     * <p>
     *  将指定的JDBC <code> CLOB </code>参数的值作为Java编程语言中的<code> javasqlClob </code>对象
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2, and
     * so on
     * @return the parameter value as a <code>Clob</code> object in the
     * Java programming language.  If the value was SQL <code>NULL</code>, the
     * value <code>null</code> is returned.
     * @exception SQLException if the parameterIndex is not valid;
     * if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.2
     */
    Clob getClob (int parameterIndex) throws SQLException;

    /**
     *
     * Retrieves the value of the designated JDBC <code>ARRAY</code> parameter as an
     * {@link java.sql.Array} object in the Java programming language.
     * <p>
     * 将指定的JDBC <code> ARRAY </code>参数的值作为Java编程语言中的{@link javasqlArray}对象
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2, and
     * so on
     * @return the parameter value as an <code>Array</code> object in
     * the Java programming language.  If the value was SQL <code>NULL</code>, the
     * value <code>null</code> is returned.
     * @exception SQLException if the parameterIndex is not valid;
     * if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.2
     */
    Array getArray (int parameterIndex) throws SQLException;

    /**
     * Retrieves the value of the designated JDBC <code>DATE</code> parameter as a
     * <code>java.sql.Date</code> object, using
     * the given <code>Calendar</code> object
     * to construct the date.
     * With a <code>Calendar</code> object, the driver
     * can calculate the date taking into account a custom timezone and locale.
     * If no <code>Calendar</code> object is specified, the driver uses the
     * default timezone and locale.
     *
     * <p>
     *  使用给定的<code> Calendar </code>对象使用<code>日历构建日期,将指定的JDBC <code> DATE </code>参数的值检索为<code> javasqlDate </code>
     *  </code>对象,驱动程序可以计算考虑自定义时区和区域设置的日期如果未指定<code> Calendar </code>对象,则驱动程序将使用默认时区和区域设置。
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2,
     * and so on
     * @param cal the <code>Calendar</code> object the driver will use
     *            to construct the date
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     *         is <code>null</code>.
     * @exception SQLException if the parameterIndex is not valid;
     * if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @see #setDate
     * @since 1.2
     */
    java.sql.Date getDate(int parameterIndex, Calendar cal)
        throws SQLException;

    /**
     * Retrieves the value of the designated JDBC <code>TIME</code> parameter as a
     * <code>java.sql.Time</code> object, using
     * the given <code>Calendar</code> object
     * to construct the time.
     * With a <code>Calendar</code> object, the driver
     * can calculate the time taking into account a custom timezone and locale.
     * If no <code>Calendar</code> object is specified, the driver uses the
     * default timezone and locale.
     *
     * <p>
     * 使用给定的<code> Calendar </code>对象构建时间,使用<code>日历来检索指定的JDBC <code> TIME </code>参数的值为<code> javasqlTime </code>
     *  </code>对象,驱动程序可以计算考虑自定义时区和区域设置的时间如果未指定<code> Calendar </code>对象,驱动程序将使用默认时区和区域设置。
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2,
     * and so on
     * @param cal the <code>Calendar</code> object the driver will use
     *            to construct the time
     * @return the parameter value; if the value is SQL <code>NULL</code>, the result
     *         is <code>null</code>.
     * @exception SQLException if the parameterIndex is not valid;
     * if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @see #setTime
     * @since 1.2
     */
    java.sql.Time getTime(int parameterIndex, Calendar cal)
        throws SQLException;

    /**
     * Retrieves the value of the designated JDBC <code>TIMESTAMP</code> parameter as a
     * <code>java.sql.Timestamp</code> object, using
     * the given <code>Calendar</code> object to construct
     * the <code>Timestamp</code> object.
     * With a <code>Calendar</code> object, the driver
     * can calculate the timestamp taking into account a custom timezone and locale.
     * If no <code>Calendar</code> object is specified, the driver uses the
     * default timezone and locale.
     *
     *
     * <p>
     * 使用给定的<code> Calendar </code>对象构造<code> Timestamp </code>对象,将指定的JDBC <code> TIMESTAMP </code>参数的值作为<code>
     *  javasqlTimestamp </code> > object使用<code> Calendar </code>对象,驱动程序可以计算考虑自定义时区和区域设置的时间戳如果未指定<code> Cal
     * endar </code>对象,驱动程序将使用默认时区和区域设置。
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2,
     * and so on
     * @param cal the <code>Calendar</code> object the driver will use
     *            to construct the timestamp
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     *         is <code>null</code>.
     * @exception SQLException if the parameterIndex is not valid;
     * if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @see #setTimestamp
     * @since 1.2
     */
    java.sql.Timestamp getTimestamp(int parameterIndex, Calendar cal)
        throws SQLException;


    /**
     * Registers the designated output parameter.
     * This version of
     * the method <code>registerOutParameter</code>
     * should be used for a user-defined or <code>REF</code> output parameter.  Examples
     * of user-defined types include: <code>STRUCT</code>, <code>DISTINCT</code>,
     * <code>JAVA_OBJECT</code>, and named array types.
     *<p>
     * All OUT parameters must be registered
     * before a stored procedure is executed.
     * <p>  For a user-defined parameter, the fully-qualified SQL
     * type name of the parameter should also be given, while a <code>REF</code>
     * parameter requires that the fully-qualified type name of the
     * referenced type be given.  A JDBC driver that does not need the
     * type code and type name information may ignore it.   To be portable,
     * however, applications should always provide these values for
     * user-defined and <code>REF</code> parameters.
     *
     * Although it is intended for user-defined and <code>REF</code> parameters,
     * this method may be used to register a parameter of any JDBC type.
     * If the parameter does not have a user-defined or <code>REF</code> type, the
     * <i>typeName</i> parameter is ignored.
     *
     * <P><B>Note:</B> When reading the value of an out parameter, you
     * must use the getter method whose Java type corresponds to the
     * parameter's registered SQL type.
     *
     * <p>
     *  注册指定的输出参数用户定义或<code> REF </code>输出参数应使用方法<code> registerOutParameter </code>的此版本。
     * 用户定义类型的示例包括：<code> STRUCT < / code>,<code> DISTINCT </code>,<code> JAVA_OBJECT </code>和命名的数组类型。
     * p>
     * 所有OUT参数必须在执行存储过程之前注册<p>对于用户定义的参数,还应指定参数的完全限定的SQL类型名称,而<code> REF </code>参数要求给定引用类型的完全限定类型名称不需要类型代码和类型
     * 名称信息的JDBC驱动程序可以忽略它为了便于移植,应用程序应始终为用户定义和<code> REF提供这些值</code>参数。
     * 
     *  虽然它用于用户定义和<code> REF </code>参数,但此方法可用于注册任何JDBC类型的参数。
     * 如果参数没有用户定义或<code> REF </code >类型,将忽略<i> typeName </i>参数。
     * 
     * <P> <B>注意：</B>读取out参数的值时,必须使用getter方法,其Java类型对应于参数的注册SQL类型
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2,...
     * @param sqlType a value from {@link java.sql.Types}
     * @param typeName the fully-qualified name of an SQL structured type
     * @exception SQLException if the parameterIndex is not valid;
     * if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if <code>sqlType</code> is
     * a <code>ARRAY</code>, <code>BLOB</code>, <code>CLOB</code>,
     * <code>DATALINK</code>, <code>JAVA_OBJECT</code>, <code>NCHAR</code>,
     * <code>NCLOB</code>, <code>NVARCHAR</code>, <code>LONGNVARCHAR</code>,
     *  <code>REF</code>, <code>ROWID</code>, <code>SQLXML</code>
     * or  <code>STRUCT</code> data type and the JDBC driver does not support
     * this data type
     * @see Types
     * @since 1.2
     */
    void registerOutParameter (int parameterIndex, int sqlType, String typeName)
        throws SQLException;

  //--------------------------JDBC 3.0-----------------------------

    /**
     * Registers the OUT parameter named
     * <code>parameterName</code> to the JDBC type
     * <code>sqlType</code>.  All OUT parameters must be registered
     * before a stored procedure is executed.
     * <p>
     * The JDBC type specified by <code>sqlType</code> for an OUT
     * parameter determines the Java type that must be used
     * in the <code>get</code> method to read the value of that parameter.
     * <p>
     * If the JDBC type expected to be returned to this output parameter
     * is specific to this particular database, <code>sqlType</code>
     * should be <code>java.sql.Types.OTHER</code>.  The method
     * {@link #getObject} retrieves the value.
     * <p>
     *  将名为<code> parameterName </code>的OUT参数注册到JDBC类型<code> sqlType </code>所有OUT参数必须在执行存储过程之前注册
     * <p>
     *  由<code> sqlType </code>为OUT参数指定的JDBC类型确定在<code> get </code>方法中必须使用的Java类型,以读取该参数的值
     * <p>
     *  如果希望返回到此输出参数的JDBC类型特定于此特定数据库,则<code> sqlType </code>应为<code> javasqlTypesOTHER </code>方法{@link #getObject}
     * 。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param sqlType the JDBC type code defined by <code>java.sql.Types</code>.
     * If the parameter is of JDBC type <code>NUMERIC</code>
     * or <code>DECIMAL</code>, the version of
     * <code>registerOutParameter</code> that accepts a scale value
     * should be used.
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if <code>sqlType</code> is
     * a <code>ARRAY</code>, <code>BLOB</code>, <code>CLOB</code>,
     * <code>DATALINK</code>, <code>JAVA_OBJECT</code>, <code>NCHAR</code>,
     * <code>NCLOB</code>, <code>NVARCHAR</code>, <code>LONGNVARCHAR</code>,
     *  <code>REF</code>, <code>ROWID</code>, <code>SQLXML</code>
     * or  <code>STRUCT</code> data type and the JDBC driver does not support
     * this data type or if the JDBC driver does not support
     * this method
     * @since 1.4
     * @see Types
     */
    void registerOutParameter(String parameterName, int sqlType)
        throws SQLException;

    /**
     * Registers the parameter named
     * <code>parameterName</code> to be of JDBC type
     * <code>sqlType</code>.  All OUT parameters must be registered
     * before a stored procedure is executed.
     * <p>
     * The JDBC type specified by <code>sqlType</code> for an OUT
     * parameter determines the Java type that must be used
     * in the <code>get</code> method to read the value of that parameter.
     * <p>
     * This version of <code>registerOutParameter</code> should be
     * used when the parameter is of JDBC type <code>NUMERIC</code>
     * or <code>DECIMAL</code>.
     *
     * <p>
     * 将名为<code> parameterName </code>的参数注册为JDBC类型<code> sqlType </code>所有OUT参数必须在执行存储过程之前注册
     * <p>
     *  由<code> sqlType </code>为OUT参数指定的JDBC类型确定在<code> get </code>方法中必须使用的Java类型,以读取该参数的值
     * <p>
     *  当参数是JDBC类型<code> NUMERIC </code>或<code> DECIMAL </code>时,应使用此版本的<code> registerOutParameter </code>。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param sqlType SQL type code defined by <code>java.sql.Types</code>.
     * @param scale the desired number of digits to the right of the
     * decimal point.  It must be greater than or equal to zero.
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if <code>sqlType</code> is
     * a <code>ARRAY</code>, <code>BLOB</code>, <code>CLOB</code>,
     * <code>DATALINK</code>, <code>JAVA_OBJECT</code>, <code>NCHAR</code>,
     * <code>NCLOB</code>, <code>NVARCHAR</code>, <code>LONGNVARCHAR</code>,
     *  <code>REF</code>, <code>ROWID</code>, <code>SQLXML</code>
     * or  <code>STRUCT</code> data type and the JDBC driver does not support
     * this data type or if the JDBC driver does not support
     * this method
     * @since 1.4
     * @see Types
     */
    void registerOutParameter(String parameterName, int sqlType, int scale)
        throws SQLException;

    /**
     * Registers the designated output parameter.  This version of
     * the method <code>registerOutParameter</code>
     * should be used for a user-named or REF output parameter.  Examples
     * of user-named types include: STRUCT, DISTINCT, JAVA_OBJECT, and
     * named array types.
     *<p>
     * All OUT parameters must be registered
     * before a stored procedure is executed.
     * <p>
     * For a user-named parameter the fully-qualified SQL
     * type name of the parameter should also be given, while a REF
     * parameter requires that the fully-qualified type name of the
     * referenced type be given.  A JDBC driver that does not need the
     * type code and type name information may ignore it.   To be portable,
     * however, applications should always provide these values for
     * user-named and REF parameters.
     *
     * Although it is intended for user-named and REF parameters,
     * this method may be used to register a parameter of any JDBC type.
     * If the parameter does not have a user-named or REF type, the
     * typeName parameter is ignored.
     *
     * <P><B>Note:</B> When reading the value of an out parameter, you
     * must use the <code>getXXX</code> method whose Java type XXX corresponds to the
     * parameter's registered SQL type.
     *
     * <p>
     *  注册指定的输出参数此版本的方法<code> registerOutParameter </code>应用于用户命名或REF输出参数用户命名类型的示例包括：STRUCT,DISTINCT,JAVA_OB
     * JECT和命名的数组类型。
     * p>
     * 所有OUT参数必须在执行存储过程之前注册
     * <p>
     *  对于用户命名的参数,还应给出参数的完全限定的SQL类型名称,而REF参数要求给定引用类型的完全限定类型名称。
     * 不需要类型代码的JDBC驱动程序,类型名称信息可能会忽略它为了便于移植,应用程序应始终为用户命名和REF参数提供这些值。
     * 
     *  虽然它用于用户命名和REF参数,但此方法可用于注册任何JDBC类型的参数。如果参数没有用户名或REF类型,则忽略typeName参数
     * 
     * <P> <B>注意：</B>读取out参数的值时,必须使用<code> getXXX </code>方法,其Java类型XXX对应于参数的注册SQL类型
     * 
     * 
     * @param parameterName the name of the parameter
     * @param sqlType a value from {@link java.sql.Types}
     * @param typeName the fully-qualified name of an SQL structured type
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if <code>sqlType</code> is
     * a <code>ARRAY</code>, <code>BLOB</code>, <code>CLOB</code>,
     * <code>DATALINK</code>, <code>JAVA_OBJECT</code>, <code>NCHAR</code>,
     * <code>NCLOB</code>, <code>NVARCHAR</code>, <code>LONGNVARCHAR</code>,
     *  <code>REF</code>, <code>ROWID</code>, <code>SQLXML</code>
     * or  <code>STRUCT</code> data type and the JDBC driver does not support
     * this data type or if the JDBC driver does not support
     * this method
     * @see Types
     * @since 1.4
     */
    void registerOutParameter (String parameterName, int sqlType, String typeName)
        throws SQLException;

    /**
     * Retrieves the value of the designated JDBC <code>DATALINK</code> parameter as a
     * <code>java.net.URL</code> object.
     *
     * <p>
     *  将指定的JDBC <code> DATALINK </code>参数的值作为<code> javanetURL </code>对象检索
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2,...
     * @return a <code>java.net.URL</code> object that represents the
     *         JDBC <code>DATALINK</code> value used as the designated
     *         parameter
     * @exception SQLException if the parameterIndex is not valid;
     * if a database access error occurs,
     * this method is called on a closed <code>CallableStatement</code>,
     *            or if the URL being returned is
     *            not a valid URL on the Java platform
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #setURL
     * @since 1.4
     */
    java.net.URL getURL(int parameterIndex) throws SQLException;

    /**
     * Sets the designated parameter to the given <code>java.net.URL</code> object.
     * The driver converts this to an SQL <code>DATALINK</code> value when
     * it sends it to the database.
     *
     * <p>
     *  将指定的参数设置为给定的<code> javanetURL </code>对象当驱动程序将其发送到数据库时,将其转换为SQL <code> DATALINK </code>
     * 
     * 
     * @param parameterName the name of the parameter
     * @param val the parameter value
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs;
     * this method is called on a closed <code>CallableStatement</code>
     *            or if a URL is malformed
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getURL
     * @since 1.4
     */
    void setURL(String parameterName, java.net.URL val) throws SQLException;

    /**
     * Sets the designated parameter to SQL <code>NULL</code>.
     *
     * <P><B>Note:</B> You must specify the parameter's SQL type.
     *
     * <p>
     *  将指定的参数设置为SQL <code> NULL </code>
     * 
     *  <P> <B>注意：</B>您必须指定参数的SQL类型
     * 
     * 
     * @param parameterName the name of the parameter
     * @param sqlType the SQL type code defined in <code>java.sql.Types</code>
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.4
     */
    void setNull(String parameterName, int sqlType) throws SQLException;

    /**
     * Sets the designated parameter to the given Java <code>boolean</code> value.
     * The driver converts this
     * to an SQL <code>BIT</code> or <code>BOOLEAN</code> value when it sends it to the database.
     *
     * <p>
     *  将指定的参数设置为给定的Java <code> boolean </code>值驱动程序将其发送到数据库时将其转换为SQL <code> BIT </code>或<code> BOOLEAN </code>
     * 。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the parameter value
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @see #getBoolean
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.4
     */
    void setBoolean(String parameterName, boolean x) throws SQLException;

    /**
     * Sets the designated parameter to the given Java <code>byte</code> value.
     * The driver converts this
     * to an SQL <code>TINYINT</code> value when it sends it to the database.
     *
     * <p>
     * 将指定的参数设置为给定的Java <code> byte </code>值驱动程序在将其发送到数据库时将其转换为SQL <code> TINYINT </code>
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the parameter value
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getByte
     * @since 1.4
     */
    void setByte(String parameterName, byte x) throws SQLException;

    /**
     * Sets the designated parameter to the given Java <code>short</code> value.
     * The driver converts this
     * to an SQL <code>SMALLINT</code> value when it sends it to the database.
     *
     * <p>
     *  将指定的参数设置为给定的Java <code> short </code>值驱动程序在将其发送到数据库时将其转换为SQL <code> SMALLINT </code>
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the parameter value
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getShort
     * @since 1.4
     */
    void setShort(String parameterName, short x) throws SQLException;

    /**
     * Sets the designated parameter to the given Java <code>int</code> value.
     * The driver converts this
     * to an SQL <code>INTEGER</code> value when it sends it to the database.
     *
     * <p>
     *  将指定的参数设置为给定的Java <code> int </code>值当驱动程序将其发送到数据库时,将其转换为SQL <code> INTEGER </code>
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the parameter value
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getInt
     * @since 1.4
     */
    void setInt(String parameterName, int x) throws SQLException;

    /**
     * Sets the designated parameter to the given Java <code>long</code> value.
     * The driver converts this
     * to an SQL <code>BIGINT</code> value when it sends it to the database.
     *
     * <p>
     *  将指定的参数设置为给定的Java <code> long </code>值驱动程序在将其发送到数据库时将其转换为SQL <code> BIGINT </code>
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the parameter value
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getLong
     * @since 1.4
     */
    void setLong(String parameterName, long x) throws SQLException;

    /**
     * Sets the designated parameter to the given Java <code>float</code> value.
     * The driver converts this
     * to an SQL <code>FLOAT</code> value when it sends it to the database.
     *
     * <p>
     * 将指定的参数设置为给定的Java <code> float </code>值当驱动程序将它发送到数据库时将其转换为SQL <code> FLOAT </code>
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the parameter value
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getFloat
     * @since 1.4
     */
    void setFloat(String parameterName, float x) throws SQLException;

    /**
     * Sets the designated parameter to the given Java <code>double</code> value.
     * The driver converts this
     * to an SQL <code>DOUBLE</code> value when it sends it to the database.
     *
     * <p>
     *  将指定的参数设置为给定的Java <code> double </code>值驱动程序将其发送到数据库时将其转换为SQL <code> DOUBLE </code>
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the parameter value
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getDouble
     * @since 1.4
     */
    void setDouble(String parameterName, double x) throws SQLException;

    /**
     * Sets the designated parameter to the given
     * <code>java.math.BigDecimal</code> value.
     * The driver converts this to an SQL <code>NUMERIC</code> value when
     * it sends it to the database.
     *
     * <p>
     *  将指定的参数设置为给定的<code> javamathBigDecimal </code>值驱动程序将其发送到数据库时将其转换为SQL <code> NUMERIC </code>
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the parameter value
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getBigDecimal
     * @since 1.4
     */
    void setBigDecimal(String parameterName, BigDecimal x) throws SQLException;

    /**
     * Sets the designated parameter to the given Java <code>String</code> value.
     * The driver converts this
     * to an SQL <code>VARCHAR</code> or <code>LONGVARCHAR</code> value
     * (depending on the argument's
     * size relative to the driver's limits on <code>VARCHAR</code> values)
     * when it sends it to the database.
     *
     * <p>
     * 将指定的参数设置为给定的Java <code> String </code>值驱动程序将其转换为SQL <code> VARCHAR </code>或<code> LONGVARCHAR </code>
     * 值(取决于参数的大小驱动程序对<code> VARCHAR </code>值的限制),当它发送到数据库。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the parameter value
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getString
     * @since 1.4
     */
    void setString(String parameterName, String x) throws SQLException;

    /**
     * Sets the designated parameter to the given Java array of bytes.
     * The driver converts this to an SQL <code>VARBINARY</code> or
     * <code>LONGVARBINARY</code> (depending on the argument's size relative
     * to the driver's limits on <code>VARBINARY</code> values) when it sends
     * it to the database.
     *
     * <p>
     *  将指定的参数设置为给定的Java字节数组驱动程序将其转换为SQL <code> VARBINARY </code>或<code> LONGVARBINARY </code>(取决于参数的大小相对于驱动
     * 程序在<code> VARBINARY </code>值),当它发送到数据库。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the parameter value
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getBytes
     * @since 1.4
     */
    void setBytes(String parameterName, byte x[]) throws SQLException;

    /**
     * Sets the designated parameter to the given <code>java.sql.Date</code> value
     * using the default time zone of the virtual machine that is running
     * the application.
     * The driver converts this
     * to an SQL <code>DATE</code> value when it sends it to the database.
     *
     * <p>
     * 使用运行应用程序的虚拟机的默认时区将指定的参数设置为给定的<code> javasqlDate </code>值驱动程序在发送时将其转换为SQL <code> DATE </code>到数据库
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the parameter value
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getDate
     * @since 1.4
     */
    void setDate(String parameterName, java.sql.Date x)
        throws SQLException;

    /**
     * Sets the designated parameter to the given <code>java.sql.Time</code> value.
     * The driver converts this
     * to an SQL <code>TIME</code> value when it sends it to the database.
     *
     * <p>
     *  将指定的参数设置为给定的<code> javasqlTime </code>值驱动程序在将其发送到数据库时将其转换为SQL <code> TIME </code>
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the parameter value
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getTime
     * @since 1.4
     */
    void setTime(String parameterName, java.sql.Time x)
        throws SQLException;

    /**
     * Sets the designated parameter to the given <code>java.sql.Timestamp</code> value.
     * The driver
     * converts this to an SQL <code>TIMESTAMP</code> value when it sends it to the
     * database.
     *
     * <p>
     *  将指定的参数设置为给定的<code> javasqlTimestamp </code>值当驱动程序将其发送到数据库时,将其转换为SQL <code> TIMESTAMP </code>
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the parameter value
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getTimestamp
     * @since 1.4
     */
    void setTimestamp(String parameterName, java.sql.Timestamp x)
        throws SQLException;

    /**
     * Sets the designated parameter to the given input stream, which will have
     * the specified number of bytes.
     * When a very large ASCII value is input to a <code>LONGVARCHAR</code>
     * parameter, it may be more practical to send it via a
     * <code>java.io.InputStream</code>. Data will be read from the stream
     * as needed until end-of-file is reached.  The JDBC driver will
     * do any necessary conversion from ASCII to the database char format.
     *
     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     *
     * <p>
     * 将指定的参数设置为给定的输入流,它将具有指定的字节数当非常大的ASCII值输入到<code> LONGVARCHAR </code>参数时,通过<code> > javaioInputStream </code>
     * 将根据需要从流中读取数据,直到达到文件结束JDBC驱动程序将执行从ASCII到数据库字符格式的必要转换。
     * 
     *  <P> <B>注意：</B>此流对象可以是标准Java流对象或您自己的子类,实现标准接口
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the Java input stream that contains the ASCII parameter value
     * @param length the number of bytes in the stream
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.4
     */
    void setAsciiStream(String parameterName, java.io.InputStream x, int length)
        throws SQLException;

    /**
     * Sets the designated parameter to the given input stream, which will have
     * the specified number of bytes.
     * When a very large binary value is input to a <code>LONGVARBINARY</code>
     * parameter, it may be more practical to send it via a
     * <code>java.io.InputStream</code> object. The data will be read from the stream
     * as needed until end-of-file is reached.
     *
     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     *
     * <p>
     * 将指定的参数设置为给定的输入流,其将具有指定的字节数当非常大的二进制值被输入到<code> LONGVARBINARY </code>参数时,通过<code> > javaioInputStream </code>
     *  object数据将根据需要从流中读取,直到达到文件结束。
     * 
     *  <P> <B>注意：</B>此流对象可以是标准Java流对象或您自己的子类,实现标准接口
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the java input stream which contains the binary parameter value
     * @param length the number of bytes in the stream
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.4
     */
    void setBinaryStream(String parameterName, java.io.InputStream x,
                         int length) throws SQLException;

    /**
     * Sets the value of the designated parameter with the given object.
     *
     * <p>The given Java object will be converted to the given targetSqlType
     * before being sent to the database.
     *
     * If the object has a custom mapping (is of a class implementing the
     * interface <code>SQLData</code>),
     * the JDBC driver should call the method <code>SQLData.writeSQL</code> to write it
     * to the SQL data stream.
     * If, on the other hand, the object is of a class implementing
     * <code>Ref</code>, <code>Blob</code>, <code>Clob</code>,  <code>NClob</code>,
     *  <code>Struct</code>, <code>java.net.URL</code>,
     * or <code>Array</code>, the driver should pass it to the database as a
     * value of the corresponding SQL type.
     * <P>
     * Note that this method may be used to pass datatabase-
     * specific abstract data types.
     *
     * <p>
     *  使用给定对象设置指定参数的值
     * 
     *  <p>给定的Java对象在发送到数据库之前将被转换为给定的targetSqlType
     * 
     * 如果对象具有自定义映射(是实现接口<code> SQLData </code>的类),JDBC驱动程序应调用<code> SQLDatawriteSQL </code>方法将其写入SQL数据流If,另一
     * 方面,对象是实现<code> Ref </code>,<code> Blob </code>,<code> Clob </code>,<code> NClob </code>,<code > Struc
     * t </code>,<code> javanetURL </code>或<code> Array </code>,驱动程序应将其作为相应SQL类型的值传递到数据库。
     * <P>
     *  请注意,此方法可用于传递特定于数据库的抽象数据类型
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the object containing the input parameter value
     * @param targetSqlType the SQL type (as defined in java.sql.Types) to be
     * sent to the database. The scale argument may further qualify this type.
     * @param scale for java.sql.Types.DECIMAL or java.sql.Types.NUMERIC types,
     *          this is the number of digits after the decimal point.  For all other
     *          types, this value will be ignored.
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if
     * the JDBC driver does not support the specified targetSqlType
     * @see Types
     * @see #getObject
     * @since 1.4
     */
    void setObject(String parameterName, Object x, int targetSqlType, int scale)
        throws SQLException;

    /**
     * Sets the value of the designated parameter with the given object.
     *
     * This method is similar to {@link #setObject(String parameterName,
     * Object x, int targetSqlType, int scaleOrLength)},
     * except that it assumes a scale of zero.
     *
     * <p>
     *  使用给定对象设置指定参数的值
     * 
     * 这个方法类似于{@link #setObject(String parameterName,Object x,int targetSqlType,int scaleOrLength)},除了它假设一个零
     * 比例。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the object containing the input parameter value
     * @param targetSqlType the SQL type (as defined in java.sql.Types) to be
     *                      sent to the database
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if
     * the JDBC driver does not support the specified targetSqlType
     * @see #getObject
     * @since 1.4
     */
    void setObject(String parameterName, Object x, int targetSqlType)
        throws SQLException;

    /**
     * Sets the value of the designated parameter with the given object.
     *
     * <p>The JDBC specification specifies a standard mapping from
     * Java <code>Object</code> types to SQL types.  The given argument
     * will be converted to the corresponding SQL type before being
     * sent to the database.
     * <p>Note that this method may be used to pass datatabase-
     * specific abstract data types, by using a driver-specific Java
     * type.
     *
     * If the object is of a class implementing the interface <code>SQLData</code>,
     * the JDBC driver should call the method <code>SQLData.writeSQL</code>
     * to write it to the SQL data stream.
     * If, on the other hand, the object is of a class implementing
     * <code>Ref</code>, <code>Blob</code>, <code>Clob</code>,  <code>NClob</code>,
     *  <code>Struct</code>, <code>java.net.URL</code>,
     * or <code>Array</code>, the driver should pass it to the database as a
     * value of the corresponding SQL type.
     * <P>
     * This method throws an exception if there is an ambiguity, for example, if the
     * object is of a class implementing more than one of the interfaces named above.
     *<p>
     *<b>Note:</b> Not all databases allow for a non-typed Null to be sent to
     * the backend. For maximum portability, the <code>setNull</code> or the
     * <code>setObject(String parameterName, Object x, int sqlType)</code>
     * method should be used
     * instead of <code>setObject(String parameterName, Object x)</code>.
     *<p>
     * <p>
     *  使用给定对象设置指定参数的值
     * 
     *  <p> JDBC规范指定从Java <code> Object </code>类型到SQL类型的标准映射给定的参数在发送到数据库之前将转换为相应的SQL类型<p>请注意,此方法可能用于通过使用驱动程序
     * 特定的Java类型传递特定于数据库的抽象数据类型。
     * 
     * 如果对象是实现接口<code> SQLData </code>的类,JDBC驱动程序应调用<code> SQLDatawriteSQL </code>方法将其写入SQL数据流。
     * 另一方面,对象是实现<code> Ref </code>,<code> Blob </code>,<code> Clob </code>,<code> NClob </code>,<code> Stru
     * ct </code> ,<code> javanetURL </code>或<code> Array </code>,驱动程序应将其作为相应SQL类型的值传递到数据库。
     * 如果对象是实现接口<code> SQLData </code>的类,JDBC驱动程序应调用<code> SQLDatawriteSQL </code>方法将其写入SQL数据流。
     * <P>
     *  如果存在歧义,此方法将抛出异常,例如,如果对象是实现多个上面命名的接口的类
     * p>
     * 注意：</b>并非所有的数据库都允许将非类型的Null发送到后端。
     * 为了最大的可移植性,<code> setNull </code>或<code> setObject(String parameterName,Object x, int sqlType)</code>方
     * 法,而不是<code> setObject(String parameterName,Object x)</code>。
     * 注意：</b>并非所有的数据库都允许将非类型的Null发送到后端。
     * p>
     * 
     * @param parameterName the name of the parameter
     * @param x the object containing the input parameter value
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs,
     * this method is called on a closed <code>CallableStatement</code> or if the given
     *            <code>Object</code> parameter is ambiguous
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getObject
     * @since 1.4
     */
    void setObject(String parameterName, Object x) throws SQLException;


    /**
     * Sets the designated parameter to the given <code>Reader</code>
     * object, which is the given number of characters long.
     * When a very large UNICODE value is input to a <code>LONGVARCHAR</code>
     * parameter, it may be more practical to send it via a
     * <code>java.io.Reader</code> object. The data will be read from the stream
     * as needed until end-of-file is reached.  The JDBC driver will
     * do any necessary conversion from UNICODE to the database char format.
     *
     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     *
     * <p>
     *  将指定的参数设置为给定的<code> Reader </code>对象,这是给定的字符数长当将非常大的UNICODE值输入到<code> LONGVARCHAR </code>参数时,通过<code>
     *  javaioReader </code>对象发送它。
     * 数据将根据需要从流中读取,直到达到文件结束JDBC驱动程序将执行从UNICODE到数据库字符格式的任何必要的转换。
     * 
     * <P> <B>注意：</B>此流对象可以是标准Java流对象或您自己的子类,实现标准接口
     * 
     * 
     * @param parameterName the name of the parameter
     * @param reader the <code>java.io.Reader</code> object that
     *        contains the UNICODE data used as the designated parameter
     * @param length the number of characters in the stream
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.4
     */
    void setCharacterStream(String parameterName,
                            java.io.Reader reader,
                            int length) throws SQLException;

    /**
     * Sets the designated parameter to the given <code>java.sql.Date</code> value,
     * using the given <code>Calendar</code> object.  The driver uses
     * the <code>Calendar</code> object to construct an SQL <code>DATE</code> value,
     * which the driver then sends to the database.  With a
     * a <code>Calendar</code> object, the driver can calculate the date
     * taking into account a custom timezone.  If no
     * <code>Calendar</code> object is specified, the driver uses the default
     * timezone, which is that of the virtual machine running the application.
     *
     * <p>
     *  使用给定的<code> Calendar </code>对象将指定的参数设置为给定的<code> javasqlDate </code>值驱动程序使用<code> Calendar </code>对象
     * 构造SQL <code> DATE </code>对象,驱动程序可以计算考虑自定义时区的日期。
     * 如果没有<code> Calendar </code>对象,则驱动程序会发送到数据库。指定,驱动程序使用默认时区,这是运行应用程序的虚拟机的时区。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the parameter value
     * @param cal the <code>Calendar</code> object the driver will use
     *            to construct the date
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getDate
     * @since 1.4
     */
    void setDate(String parameterName, java.sql.Date x, Calendar cal)
        throws SQLException;

    /**
     * Sets the designated parameter to the given <code>java.sql.Time</code> value,
     * using the given <code>Calendar</code> object.  The driver uses
     * the <code>Calendar</code> object to construct an SQL <code>TIME</code> value,
     * which the driver then sends to the database.  With a
     * a <code>Calendar</code> object, the driver can calculate the time
     * taking into account a custom timezone.  If no
     * <code>Calendar</code> object is specified, the driver uses the default
     * timezone, which is that of the virtual machine running the application.
     *
     * <p>
     * 使用给定的<code> Calendar </code>对象将指定的参数设置为给定的<code> javasqlTime </code>值驱动程序使用<code> Calendar </code>对象构
     * 造SQL <code> TIME </code>对象,驱动程序可以计算考虑自定义时区的时间。
     * 如果没有<code> Calendar </code>对象指定,驱动程序使用默认时区,这是运行应用程序的虚拟机的时区。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the parameter value
     * @param cal the <code>Calendar</code> object the driver will use
     *            to construct the time
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getTime
     * @since 1.4
     */
    void setTime(String parameterName, java.sql.Time x, Calendar cal)
        throws SQLException;

    /**
     * Sets the designated parameter to the given <code>java.sql.Timestamp</code> value,
     * using the given <code>Calendar</code> object.  The driver uses
     * the <code>Calendar</code> object to construct an SQL <code>TIMESTAMP</code> value,
     * which the driver then sends to the database.  With a
     * a <code>Calendar</code> object, the driver can calculate the timestamp
     * taking into account a custom timezone.  If no
     * <code>Calendar</code> object is specified, the driver uses the default
     * timezone, which is that of the virtual machine running the application.
     *
     * <p>
     * 使用给定的<code> Calendar </code>对象将指定的参数设置为给定的<code> javasqlTimestamp </code>值驱动程序使用<code> Calendar </code>
     * 对象构造SQL <code> TIMESTAMP </code>对象,驱动程序可以计算考虑自定义时区的时间戳如果没有<code> Calendar </code>对象指定,驱动程序使用默认时区,这是运行
     * 应用程序的虚拟机的时区。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the parameter value
     * @param cal the <code>Calendar</code> object the driver will use
     *            to construct the timestamp
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getTimestamp
     * @since 1.4
     */
    void setTimestamp(String parameterName, java.sql.Timestamp x, Calendar cal)
        throws SQLException;

    /**
     * Sets the designated parameter to SQL <code>NULL</code>.
     * This version of the method <code>setNull</code> should
     * be used for user-defined types and REF type parameters.  Examples
     * of user-defined types include: STRUCT, DISTINCT, JAVA_OBJECT, and
     * named array types.
     *
     * <P><B>Note:</B> To be portable, applications must give the
     * SQL type code and the fully-qualified SQL type name when specifying
     * a NULL user-defined or REF parameter.  In the case of a user-defined type
     * the name is the type name of the parameter itself.  For a REF
     * parameter, the name is the type name of the referenced type.
     * <p>
     * Although it is intended for user-defined and Ref parameters,
     * this method may be used to set a null parameter of any JDBC type.
     * If the parameter does not have a user-defined or REF type, the given
     * typeName is ignored.
     *
     *
     * <p>
     * 将指定的参数设置为SQL <code> NULL </code>此方法的版本<code> setNull </code>应用于用户定义的类型和REF类型参数用户定义类型的示例包括：STRUCT,DIST
     * INCT ,JAVA_OBJECT和命名的数组类型。
     * 
     *  <P> <B>注意：</B>为了便于移植,当指定NULL用户定义或REF参数时,应用程序必须给出SQL类型代码和完全限定的SQL类型名称。
     * 在用户定义类型该名称是参数本身的类型名称对于REF参数,名称是引用类型的类型名称。
     * <p>
     * 虽然它用于用户定义和参考参数,但此方法可用于设置任何JDBC类型的空参数。如果参数没有用户定义或REF类型,则忽略给定的typeName
     * 
     * 
     * @param parameterName the name of the parameter
     * @param sqlType a value from <code>java.sql.Types</code>
     * @param typeName the fully-qualified name of an SQL user-defined type;
     *        ignored if the parameter is not a user-defined type or
     *        SQL <code>REF</code> value
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.4
     */
    void setNull (String parameterName, int sqlType, String typeName)
        throws SQLException;

    /**
     * Retrieves the value of a JDBC <code>CHAR</code>, <code>VARCHAR</code>,
     * or <code>LONGVARCHAR</code> parameter as a <code>String</code> in
     * the Java programming language.
     * <p>
     * For the fixed-length type JDBC <code>CHAR</code>,
     * the <code>String</code> object
     * returned has exactly the same value the SQL
     * <code>CHAR</code> value had in the
     * database, including any padding added by the database.
     * <p>
     *  在Java编程语言中将<code> CHAR </code>,<code> VARCHAR </code>或<code> LONGVARCHAR </code>参数的值检索为<code> String
     *  </code>。
     * <p>
     *  对于固定长度类型JDBC <code> CHAR </code>,返回的<code> String </code>对象与数据库中的SQL <code> CHAR </code>值完全相同,填充由数据库
     * 添加。
     * 
     * 
     * @param parameterName the name of the parameter
     * @return the parameter value. If the value is SQL <code>NULL</code>, the result
     * is <code>null</code>.
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #setString
     * @since 1.4
     */
    String getString(String parameterName) throws SQLException;

    /**
     * Retrieves the value of a JDBC <code>BIT</code> or <code>BOOLEAN</code>
     * parameter as a
     * <code>boolean</code> in the Java programming language.
     * <p>
     *  在Java编程语言中检索JDBC <code> BIT </code>或<code> BOOLEAN </code>参数的值为<code> boolean </code>
     * 
     * 
     * @param parameterName the name of the parameter
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     * is <code>false</code>.
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #setBoolean
     * @since 1.4
     */
    boolean getBoolean(String parameterName) throws SQLException;

    /**
     * Retrieves the value of a JDBC <code>TINYINT</code> parameter as a <code>byte</code>
     * in the Java programming language.
     * <p>
     * 在Java编程语言中将JDBC <code> TINYINT </code>参数的值检索为<code> byte </code>
     * 
     * 
     * @param parameterName the name of the parameter
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     * is <code>0</code>.
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #setByte
     * @since 1.4
     */
    byte getByte(String parameterName) throws SQLException;

    /**
     * Retrieves the value of a JDBC <code>SMALLINT</code> parameter as a <code>short</code>
     * in the Java programming language.
     * <p>
     *  在Java编程语言中将JDBC <code> SMALLINT </code>参数的值作为<code> short </code>
     * 
     * 
     * @param parameterName the name of the parameter
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     * is <code>0</code>.
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #setShort
     * @since 1.4
     */
    short getShort(String parameterName) throws SQLException;

    /**
     * Retrieves the value of a JDBC <code>INTEGER</code> parameter as an <code>int</code>
     * in the Java programming language.
     *
     * <p>
     *  在Java编程语言中将JDBC <code> INTEGER </code>参数的值检索为<code> int </code>
     * 
     * 
     * @param parameterName the name of the parameter
     * @return the parameter value.  If the value is SQL <code>NULL</code>,
     *         the result is <code>0</code>.
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #setInt
     * @since 1.4
     */
    int getInt(String parameterName) throws SQLException;

    /**
     * Retrieves the value of a JDBC <code>BIGINT</code> parameter as a <code>long</code>
     * in the Java programming language.
     *
     * <p>
     *  在Java编程语言中将JDBC <code> BIGINT </code>参数的值作为<code> long </code>
     * 
     * 
     * @param parameterName the name of the parameter
     * @return the parameter value.  If the value is SQL <code>NULL</code>,
     *         the result is <code>0</code>.
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #setLong
     * @since 1.4
     */
    long getLong(String parameterName) throws SQLException;

    /**
     * Retrieves the value of a JDBC <code>FLOAT</code> parameter as a <code>float</code>
     * in the Java programming language.
     * <p>
     *  在Java编程语言中将JDBC <code> FLOAT </code>参数的值检索为<code> float </code>
     * 
     * 
     * @param parameterName the name of the parameter
     * @return the parameter value.  If the value is SQL <code>NULL</code>,
     *         the result is <code>0</code>.
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #setFloat
     * @since 1.4
     */
    float getFloat(String parameterName) throws SQLException;

    /**
     * Retrieves the value of a JDBC <code>DOUBLE</code> parameter as a <code>double</code>
     * in the Java programming language.
     * <p>
     *  在Java编程语言中将JDBC <code> DOUBLE </code>参数的值检索为<code> double </code>
     * 
     * 
     * @param parameterName the name of the parameter
     * @return the parameter value.  If the value is SQL <code>NULL</code>,
     *         the result is <code>0</code>.
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #setDouble
     * @since 1.4
     */
    double getDouble(String parameterName) throws SQLException;

    /**
     * Retrieves the value of a JDBC <code>BINARY</code> or <code>VARBINARY</code>
     * parameter as an array of <code>byte</code> values in the Java
     * programming language.
     * <p>
     * 将Java代码中的<code> BINARY </code>或<code> VARBINARY </code>参数的值检索为<code> byte </code>
     * 
     * 
     * @param parameterName the name of the parameter
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result is
     *  <code>null</code>.
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #setBytes
     * @since 1.4
     */
    byte[] getBytes(String parameterName) throws SQLException;

    /**
     * Retrieves the value of a JDBC <code>DATE</code> parameter as a
     * <code>java.sql.Date</code> object.
     * <p>
     *  将JDBC <code> DATE </code>参数的值作为<code> javasqlDate </code>对象检索
     * 
     * 
     * @param parameterName the name of the parameter
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     * is <code>null</code>.
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #setDate
     * @since 1.4
     */
    java.sql.Date getDate(String parameterName) throws SQLException;

    /**
     * Retrieves the value of a JDBC <code>TIME</code> parameter as a
     * <code>java.sql.Time</code> object.
     * <p>
     *  以<code> javasqlTime </code>对象的形式获取JDBC <code> TIME </code>参数的值
     * 
     * 
     * @param parameterName the name of the parameter
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     * is <code>null</code>.
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #setTime
     * @since 1.4
     */
    java.sql.Time getTime(String parameterName) throws SQLException;

    /**
     * Retrieves the value of a JDBC <code>TIMESTAMP</code> parameter as a
     * <code>java.sql.Timestamp</code> object.
     * <p>
     *  以<code> javasqlTimestamp </code>对象的形式获取JDBC <code> TIMESTAMP </code>参数的值
     * 
     * 
     * @param parameterName the name of the parameter
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     * is <code>null</code>.
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #setTimestamp
     * @since 1.4
     */
    java.sql.Timestamp getTimestamp(String parameterName) throws SQLException;

    /**
     * Retrieves the value of a parameter as an <code>Object</code> in the Java
     * programming language. If the value is an SQL <code>NULL</code>, the
     * driver returns a Java <code>null</code>.
     * <p>
     * This method returns a Java object whose type corresponds to the JDBC
     * type that was registered for this parameter using the method
     * <code>registerOutParameter</code>.  By registering the target JDBC
     * type as <code>java.sql.Types.OTHER</code>, this method can be used
     * to read database-specific abstract data types.
     * <p>
     *  在Java编程语言中将参数的值检索为<code> Object </code>如果值为SQL <code> NULL </code>,则驱动程序会返回Java <code> null </code>。
     * <p>
     * 此方法使用方法<code> registerOutParameter </code>返回其类型对应于为此参数注册的JDBC类型的Java对象。
     * 通过将目标JDBC类型注册为<code> javasqlTypesOTHER </code>,此方法可以用于读取特定于数据库的抽象数据类型。
     * 
     * 
     * @param parameterName the name of the parameter
     * @return A <code>java.lang.Object</code> holding the OUT parameter value.
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see Types
     * @see #setObject
     * @since 1.4
     */
    Object getObject(String parameterName) throws SQLException;

    /**
     * Retrieves the value of a JDBC <code>NUMERIC</code> parameter as a
     * <code>java.math.BigDecimal</code> object with as many digits to the
     * right of the decimal point as the value contains.
     * <p>
     *  将JDBC <code> NUMERIC </code>参数的值作为<code> javamathBigDecimal </code>对象检索,其值小数点右侧的位数
     * 
     * 
     * @param parameterName the name of the parameter
     * @return the parameter value in full precision.  If the value is
     * SQL <code>NULL</code>, the result is <code>null</code>.
     * @exception SQLException if parameterName does not correspond to a named
     * parameter;  if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #setBigDecimal
     * @since 1.4
     */
    BigDecimal getBigDecimal(String parameterName) throws SQLException;

    /**
     * Returns an object representing the value of OUT parameter
     * <code>parameterName</code> and uses <code>map</code> for the custom
     * mapping of the parameter value.
     * <p>
     * This method returns a Java object whose type corresponds to the
     * JDBC type that was registered for this parameter using the method
     * <code>registerOutParameter</code>.  By registering the target
     * JDBC type as <code>java.sql.Types.OTHER</code>, this method can
     * be used to read database-specific abstract data types.
     * <p>
     *  返回表示OUT参数<code> parameterName </code>的值的对象,并使用<code> map </code>进行参数值的自定义映射
     * <p>
     * 此方法使用方法<code> registerOutParameter </code>返回其类型对应于为此参数注册的JDBC类型的Java对象。
     * 通过将目标JDBC类型注册为<code> javasqlTypesOTHER </code>,此方法可以用于读取特定于数据库的抽象数据类型。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param map the mapping from SQL type names to Java classes
     * @return a <code>java.lang.Object</code> holding the OUT parameter value
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #setObject
     * @since 1.4
     */
    Object getObject(String parameterName, java.util.Map<String,Class<?>> map)
      throws SQLException;

    /**
     * Retrieves the value of a JDBC <code>REF(&lt;structured-type&gt;)</code>
     * parameter as a {@link java.sql.Ref} object in the Java programming language.
     *
     * <p>
     *  检索JDBC <code> REF(&lt; structured-type&gt;)</code>参数的值作为Java编程语言中的{@link javasqlRef}对象
     * 
     * 
     * @param parameterName the name of the parameter
     * @return the parameter value as a <code>Ref</code> object in the
     *         Java programming language.  If the value was SQL <code>NULL</code>,
     *         the value <code>null</code> is returned.
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.4
     */
    Ref getRef (String parameterName) throws SQLException;

    /**
     * Retrieves the value of a JDBC <code>BLOB</code> parameter as a
     * {@link java.sql.Blob} object in the Java programming language.
     *
     * <p>
     *  检索JDBC <code> BLOB </code>参数的值作为Java编程语言中的{@link javasqlBlob}对象
     * 
     * 
     * @param parameterName the name of the parameter
     * @return the parameter value as a <code>Blob</code> object in the
     *         Java programming language.  If the value was SQL <code>NULL</code>,
     *         the value <code>null</code> is returned.
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.4
     */
    Blob getBlob (String parameterName) throws SQLException;

    /**
     * Retrieves the value of a JDBC <code>CLOB</code> parameter as a
     * <code>java.sql.Clob</code> object in the Java programming language.
     * <p>
     *  在Java编程语言中将JDBC <code> CLOB </code>参数的值作为<code> javasqlClob </code>对象检索
     * 
     * 
     * @param parameterName the name of the parameter
     * @return the parameter value as a <code>Clob</code> object in the
     *         Java programming language.  If the value was SQL <code>NULL</code>,
     *         the value <code>null</code> is returned.
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.4
     */
    Clob getClob (String parameterName) throws SQLException;

    /**
     * Retrieves the value of a JDBC <code>ARRAY</code> parameter as an
     * {@link java.sql.Array} object in the Java programming language.
     *
     * <p>
     * 检索JDBC <code> ARRAY </code>参数的值作为Java编程语言中的{@link javasqlArray}对象
     * 
     * 
     * @param parameterName the name of the parameter
     * @return the parameter value as an <code>Array</code> object in
     *         Java programming language.  If the value was SQL <code>NULL</code>,
     *         the value <code>null</code> is returned.
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.4
     */
    Array getArray (String parameterName) throws SQLException;

    /**
     * Retrieves the value of a JDBC <code>DATE</code> parameter as a
     * <code>java.sql.Date</code> object, using
     * the given <code>Calendar</code> object
     * to construct the date.
     * With a <code>Calendar</code> object, the driver
     * can calculate the date taking into account a custom timezone and locale.
     * If no <code>Calendar</code> object is specified, the driver uses the
     * default timezone and locale.
     *
     * <p>
     *  使用给定的<code> Calendar </code>对象使用<code> Calendar </code>对象构造日期,将JDBC <code> DATE </code>参数的值作为<code> 
     * javasqlDate < / code>对象,驱动程序可以计算考虑到自定义时区和区域设置的日期如果没有指定<code> Calendar </code>对象,驱动程序将使用默认时区和区域设置。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param cal the <code>Calendar</code> object the driver will use
     *            to construct the date
     * @return the parameter value.  If the value is SQL <code>NULL</code>,
     * the result is <code>null</code>.
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #setDate
     * @since 1.4
     */
    java.sql.Date getDate(String parameterName, Calendar cal)
        throws SQLException;

    /**
     * Retrieves the value of a JDBC <code>TIME</code> parameter as a
     * <code>java.sql.Time</code> object, using
     * the given <code>Calendar</code> object
     * to construct the time.
     * With a <code>Calendar</code> object, the driver
     * can calculate the time taking into account a custom timezone and locale.
     * If no <code>Calendar</code> object is specified, the driver uses the
     * default timezone and locale.
     *
     * <p>
     * 使用给定的<code> Calendar </code>对象来构造时间,使用<code> Calendar </code>对象来检索JDBC <code> TIME </code> / code>对象,
     * 驱动程序可以计算考虑自定义时区和区域设置的时间如果未指定<code> Calendar </code>对象,则驱动程序将使用默认时区和区域设置。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param cal the <code>Calendar</code> object the driver will use
     *            to construct the time
     * @return the parameter value; if the value is SQL <code>NULL</code>, the result is
     * <code>null</code>.
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #setTime
     * @since 1.4
     */
    java.sql.Time getTime(String parameterName, Calendar cal)
        throws SQLException;

    /**
     * Retrieves the value of a JDBC <code>TIMESTAMP</code> parameter as a
     * <code>java.sql.Timestamp</code> object, using
     * the given <code>Calendar</code> object to construct
     * the <code>Timestamp</code> object.
     * With a <code>Calendar</code> object, the driver
     * can calculate the timestamp taking into account a custom timezone and locale.
     * If no <code>Calendar</code> object is specified, the driver uses the
     * default timezone and locale.
     *
     *
     * <p>
     * 使用给定的<code> Calendar </code>对象构造<code> Timestamp </code>对象,将JDBC <code> TIMESTAMP </code>参数的值作为<code>
     *  javasqlTimestamp </code>对象使用<code> Calendar </code>对象,驱动程序可以计算考虑自定义时区和区域设置的时间戳如果未指定<code> Calendar </code>
     * 对象,驱动程序将使用默认时区和区域设置。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param cal the <code>Calendar</code> object the driver will use
     *            to construct the timestamp
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result is
     * <code>null</code>.
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #setTimestamp
     * @since 1.4
     */
    java.sql.Timestamp getTimestamp(String parameterName, Calendar cal)
        throws SQLException;

    /**
     * Retrieves the value of a JDBC <code>DATALINK</code> parameter as a
     * <code>java.net.URL</code> object.
     *
     * <p>
     *  将<code> DATALINK </code>参数的值作为<code> javanetURL </code>对象检索
     * 
     * 
     * @param parameterName the name of the parameter
     * @return the parameter value as a <code>java.net.URL</code> object in the
     * Java programming language.  If the value was SQL <code>NULL</code>, the
     * value <code>null</code> is returned.
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs,
     * this method is called on a closed <code>CallableStatement</code>,
     *            or if there is a problem with the URL
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #setURL
     * @since 1.4
     */
    java.net.URL getURL(String parameterName) throws SQLException;

    //------------------------- JDBC 4.0 -----------------------------------

    /**
     * Retrieves the value of the designated JDBC <code>ROWID</code> parameter as a
     * <code>java.sql.RowId</code> object.
     *
     * <p>
     *  将指定的JDBC <code> ROWID </code>参数的值作为<code> javasqlRowId </code>对象检索
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2,...
     * @return a <code>RowId</code> object that represents the JDBC <code>ROWID</code>
     *     value is used as the designated parameter. If the parameter contains
     * a SQL <code>NULL</code>, then a <code>null</code> value is returned.
     * @throws SQLException if the parameterIndex is not valid;
     * if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.6
     */
    RowId getRowId(int parameterIndex) throws SQLException;

    /**
     * Retrieves the value of the designated JDBC <code>ROWID</code> parameter as a
     * <code>java.sql.RowId</code> object.
     *
     * <p>
     *  将指定的JDBC <code> ROWID </code>参数的值作为<code> javasqlRowId </code>对象检索
     * 
     * 
     * @param parameterName the name of the parameter
     * @return a <code>RowId</code> object that represents the JDBC <code>ROWID</code>
     *     value is used as the designated parameter. If the parameter contains
     * a SQL <code>NULL</code>, then a <code>null</code> value is returned.
     * @throws SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.6
     */
    RowId getRowId(String parameterName) throws SQLException;

     /**
     * Sets the designated parameter to the given <code>java.sql.RowId</code> object. The
     * driver converts this to a SQL <code>ROWID</code> when it sends it to the
     * database.
     *
     * <p>
     * 将指定的参数设置为给定的<code> javasqlRowId </code>对象当驱动程序将其发送到数据库时将其转换为SQL <code> ROWID </code>
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the parameter value
     * @throws SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.6
     */
    void setRowId(String parameterName, RowId x) throws SQLException;

    /**
     * Sets the designated parameter to the given <code>String</code> object.
     * The driver converts this to a SQL <code>NCHAR</code> or
     * <code>NVARCHAR</code> or <code>LONGNVARCHAR</code>
     * <p>
     *  将指定的参数设置为给定的<code> String </code>对象驱动程序将其转换为SQL <code> NCHAR </code>或<code> NVARCHAR </code>或<code> 
     * LONGNVARCHAR </code>。
     * 
     * 
     * @param parameterName the name of the parameter to be set
     * @param value the parameter value
     * @throws SQLException if parameterName does not correspond to a named
     * parameter; if the driver does not support national
     *         character sets;  if the driver can detect that a data conversion
     *  error could occur; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.6
     */
    void setNString(String parameterName, String value)
            throws SQLException;

    /**
     * Sets the designated parameter to a <code>Reader</code> object. The
     * <code>Reader</code> reads the data till end-of-file is reached. The
     * driver does the necessary conversion from Java character format to
     * the national character set in the database.
     * <p>
     *  将指定的参数设置为<code> Reader </code>对象<code> Reader </code>读取数据,直到达到文件结束驱动程序执行必要的从Java字符格式到国家字符集的转换在数据库中
     * 
     * 
     * @param parameterName the name of the parameter to be set
     * @param value the parameter value
     * @param length the number of characters in the parameter data.
     * @throws SQLException if parameterName does not correspond to a named
     * parameter; if the driver does not support national
     *         character sets;  if the driver can detect that a data conversion
     *  error could occur; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.6
     */
    void setNCharacterStream(String parameterName, Reader value, long length)
            throws SQLException;

     /**
     * Sets the designated parameter to a <code>java.sql.NClob</code> object. The object
     * implements the <code>java.sql.NClob</code> interface. This <code>NClob</code>
     * object maps to a SQL <code>NCLOB</code>.
     * <p>
     *  将指定的参数设置为<code> javasqlNClob </code>对象该对象实现<code> javasqlNClob </code>接口此<code> NClob </code>对象映射到SQ
     * L <code> NCLOB </code>。
     * 
     * 
     * @param parameterName the name of the parameter to be set
     * @param value the parameter value
     * @throws SQLException if parameterName does not correspond to a named
     * parameter; if the driver does not support national
     *         character sets;  if the driver can detect that a data conversion
     *  error could occur; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.6
     */
     void setNClob(String parameterName, NClob value) throws SQLException;

    /**
     * Sets the designated parameter to a <code>Reader</code> object.  The <code>reader</code> must contain  the number
     * of characters specified by length otherwise a <code>SQLException</code> will be
     * generated when the <code>CallableStatement</code> is executed.
     * This method differs from the <code>setCharacterStream (int, Reader, int)</code> method
     * because it informs the driver that the parameter value should be sent to
     * the server as a <code>CLOB</code>.  When the <code>setCharacterStream</code> method is used, the
     * driver may have to do extra work to determine whether the parameter
     * data should be send to the server as a <code>LONGVARCHAR</code> or a <code>CLOB</code>
     * <p>
     * 将指定的参数设置为<code> Reader </code>对象<code> reader </code>必须包含由长度指定的字符数,否则将生成<code> SQLException </code> >
     *  CallableStatement </code>执行此方法与<code> setCharacterStream(int,Reader,int)</code>方法不同,因为它通知驱动程序应将参数值作为
     * <code> CLOB </code>当使用<code> setCharacterStream </code>方法时,驱动程序可能需要做额外的工作来确定参数数据是否应作为<code> LONGVARCH
     * AR </code> <code> CLOB </code>。
     * 
     * 
     * @param parameterName the name of the parameter to be set
     * @param reader An object that contains the data to set the parameter value to.
     * @param length the number of characters in the parameter data.
     * @throws SQLException if parameterName does not correspond to a named
     * parameter; if the length specified is less than zero;
     * a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     *
     * @since 1.6
     */
     void setClob(String parameterName, Reader reader, long length)
       throws SQLException;

    /**
     * Sets the designated parameter to a <code>InputStream</code> object.  The <code>inputstream</code> must contain  the number
     * of characters specified by length, otherwise a <code>SQLException</code> will be
     * generated when the <code>CallableStatement</code> is executed.
     * This method differs from the <code>setBinaryStream (int, InputStream, int)</code>
     * method because it informs the driver that the parameter value should be
     * sent to the server as a <code>BLOB</code>.  When the <code>setBinaryStream</code> method is used,
     * the driver may have to do extra work to determine whether the parameter
     * data should be sent to the server as a <code>LONGVARBINARY</code> or a <code>BLOB</code>
     *
     * <p>
     * 将指定的参数设置为<code> InputStream </code>对象<code> inputstream </code>必须包含由length指定的字符数,否则将生成<code> SQLExcep
     * tion </code> code> CallableStatement </code>执行此方法与<code> setBinaryStream(int,InputStream,int)</code>方
     * 法不同,因为它通知驱动程序应将参数值作为<code> BLOB </code>当使用<code> setBinaryStream </code>方法时,驱动程序可能需要做额外的工作来确定参数数据是否应作
     * 为<code> LONGVARBINARY </code>发送到服务器a <code> BLOB </code>。
     * 
     * 
     * @param parameterName the name of the parameter to be set
     * the second is 2, ...
     *
     * @param inputStream An object that contains the data to set the parameter
     * value to.
     * @param length the number of bytes in the parameter data.
     * @throws SQLException  if parameterName does not correspond to a named
     * parameter; if the length specified
     * is less than zero; if the number of bytes in the inputstream does not match
     * the specified length; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     *
     * @since 1.6
     */
     void setBlob(String parameterName, InputStream inputStream, long length)
        throws SQLException;
    /**
     * Sets the designated parameter to a <code>Reader</code> object.  The <code>reader</code> must contain  the number
     * of characters specified by length otherwise a <code>SQLException</code> will be
     * generated when the <code>CallableStatement</code> is executed.
     * This method differs from the <code>setCharacterStream (int, Reader, int)</code> method
     * because it informs the driver that the parameter value should be sent to
     * the server as a <code>NCLOB</code>.  When the <code>setCharacterStream</code> method is used, the
     * driver may have to do extra work to determine whether the parameter
     * data should be send to the server as a <code>LONGNVARCHAR</code> or a <code>NCLOB</code>
     *
     * <p>
     * 将指定的参数设置为<code> Reader </code>对象<code> reader </code>必须包含由长度指定的字符数,否则将生成<code> SQLException </code> >
     *  CallableStatement </code>执行此方法与<code> setCharacterStream(int,Reader,int)</code>方法不同,因为它通知驱动程序参数值应作为<code>
     *  NCLOB发送到服务器</code>当使用<code> setCharacterStream </code>方法时,驱动程序可能需要做额外的工作来确定参数数据是否应作为<code> LONGNVARC
     * HAR </code> <code> NCLOB </code>。
     * 
     * 
     * @param parameterName the name of the parameter to be set
     * @param reader An object that contains the data to set the parameter value to.
     * @param length the number of characters in the parameter data.
     * @throws SQLException if parameterName does not correspond to a named
     * parameter; if the length specified is less than zero;
     * if the driver does not support national
     *         character sets;  if the driver can detect that a data conversion
     *  error could occur; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.6
     */
     void setNClob(String parameterName, Reader reader, long length)
       throws SQLException;

    /**
     * Retrieves the value of the designated JDBC <code>NCLOB</code> parameter as a
     * <code>java.sql.NClob</code> object in the Java programming language.
     *
     * <p>
     * 将指定的JDBC <code> NCLOB </code>参数的值作为Java编程语言中的<code> javasqlNClob </code>对象
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2, and
     * so on
     * @return the parameter value as a <code>NClob</code> object in the
     * Java programming language.  If the value was SQL <code>NULL</code>, the
     * value <code>null</code> is returned.
     * @exception SQLException if the parameterIndex is not valid;
     * if the driver does not support national
     *         character sets;  if the driver can detect that a data conversion
     *  error could occur; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.6
     */
    NClob getNClob (int parameterIndex) throws SQLException;


    /**
     * Retrieves the value of a JDBC <code>NCLOB</code> parameter as a
     * <code>java.sql.NClob</code> object in the Java programming language.
     * <p>
     *  在Java编程语言中将JDBC <code> NCLOB </code>参数的值作为<code> javasqlNClob </code>对象检索
     * 
     * 
     * @param parameterName the name of the parameter
     * @return the parameter value as a <code>NClob</code> object in the
     *         Java programming language.  If the value was SQL <code>NULL</code>,
     *         the value <code>null</code> is returned.
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if the driver does not support national
     *         character sets;  if the driver can detect that a data conversion
     *  error could occur; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.6
     */
    NClob getNClob (String parameterName) throws SQLException;

    /**
     * Sets the designated parameter to the given <code>java.sql.SQLXML</code> object. The driver converts this to an
     * <code>SQL XML</code> value when it sends it to the database.
     *
     * <p>
     *  将指定的参数设置为给定的<code> javasqlSQLXML </code>对象当驱动程序将其发送到数据库时将其转换为<code> SQL XML </code>
     * 
     * 
     * @param parameterName the name of the parameter
     * @param xmlObject a <code>SQLXML</code> object that maps an <code>SQL XML</code> value
     * @throws SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs;
     * this method is called on a closed <code>CallableStatement</code> or
     * the <code>java.xml.transform.Result</code>,
   *  <code>Writer</code> or <code>OutputStream</code> has not been closed for the <code>SQLXML</code> object
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     *
     * @since 1.6
     */
    void setSQLXML(String parameterName, SQLXML xmlObject) throws SQLException;

    /**
     * Retrieves the value of the designated <code>SQL XML</code> parameter as a
     * <code>java.sql.SQLXML</code> object in the Java programming language.
     * <p>
     *  将指定的<code> SQL XML </code>参数的值作为Java编程语言中的<code> javasqlSQLXML </code>对象
     * 
     * 
     * @param parameterIndex index of the first parameter is 1, the second is 2, ...
     * @return a <code>SQLXML</code> object that maps an <code>SQL XML</code> value
     * @throws SQLException if the parameterIndex is not valid;
     * if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.6
     */
    SQLXML getSQLXML(int parameterIndex) throws SQLException;

    /**
     * Retrieves the value of the designated <code>SQL XML</code> parameter as a
     * <code>java.sql.SQLXML</code> object in the Java programming language.
     * <p>
     *  将指定的<code> SQL XML </code>参数的值作为Java编程语言中的<code> javasqlSQLXML </code>对象
     * 
     * 
     * @param parameterName the name of the parameter
     * @return a <code>SQLXML</code> object that maps an <code>SQL XML</code> value
     * @throws SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.6
     */
    SQLXML getSQLXML(String parameterName) throws SQLException;

    /**
     * Retrieves the value of the designated <code>NCHAR</code>,
     * <code>NVARCHAR</code>
     * or <code>LONGNVARCHAR</code> parameter as
     * a <code>String</code> in the Java programming language.
     *  <p>
     * For the fixed-length type JDBC <code>NCHAR</code>,
     * the <code>String</code> object
     * returned has exactly the same value the SQL
     * <code>NCHAR</code> value had in the
     * database, including any padding added by the database.
     *
     * <p>
     * 在Java编程语言中将<code> NCHAR </code>,<code> NVARCHAR </code>或<code> LONGNVARCHAR </code>参数的值检索为<code> Stri
     * ng </code>。
     * <p>
     *  对于固定长度类型JDBC <code> NCHAR </code>,返回的<code> String </code>对象与数据库中的SQL <code> NCHAR </code>值完全相同,填充由数
     * 据库添加。
     * 
     * 
     * @param parameterIndex index of the first parameter is 1, the second is 2, ...
     * @return a <code>String</code> object that maps an
     * <code>NCHAR</code>, <code>NVARCHAR</code> or <code>LONGNVARCHAR</code> value
     * @exception SQLException if the parameterIndex is not valid;
     * if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.6
     * @see #setNString
     */
    String getNString(int parameterIndex) throws SQLException;


    /**
     *  Retrieves the value of the designated <code>NCHAR</code>,
     * <code>NVARCHAR</code>
     * or <code>LONGNVARCHAR</code> parameter as
     * a <code>String</code> in the Java programming language.
     * <p>
     * For the fixed-length type JDBC <code>NCHAR</code>,
     * the <code>String</code> object
     * returned has exactly the same value the SQL
     * <code>NCHAR</code> value had in the
     * database, including any padding added by the database.
     *
     * <p>
     *  在Java编程语言中将<code> NCHAR </code>,<code> NVARCHAR </code>或<code> LONGNVARCHAR </code>参数的值检索为<code> Str
     * ing </code>。
     * <p>
     *  对于固定长度类型JDBC <code> NCHAR </code>,返回的<code> String </code>对象与数据库中的SQL <code> NCHAR </code>值完全相同,填充由数
     * 据库添加。
     * 
     * 
     * @param parameterName the name of the parameter
     * @return a <code>String</code> object that maps an
     * <code>NCHAR</code>, <code>NVARCHAR</code> or <code>LONGNVARCHAR</code> value
     * @exception SQLException if parameterName does not correspond to a named
     * parameter;
     * if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.6
     * @see #setNString
     */
    String getNString(String parameterName) throws SQLException;

    /**
     * Retrieves the value of the designated parameter as a
     * <code>java.io.Reader</code> object in the Java programming language.
     * It is intended for use when
     * accessing  <code>NCHAR</code>,<code>NVARCHAR</code>
     * and <code>LONGNVARCHAR</code> parameters.
     *
     * <p>
     * 在Java编程语言中将指定参数的值检索为<code> javaioReader </code>对象它适用于访问<code> NCHAR </code>,<code> NVARCHAR </code>和<code >
     *  LONGNVARCHAR </code>参数。
     * 
     * 
     * @return a <code>java.io.Reader</code> object that contains the parameter
     * value; if the value is SQL <code>NULL</code>, the value returned is
     * <code>null</code> in the Java programming language.
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @exception SQLException if the parameterIndex is not valid;
     * if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.6
     */
    java.io.Reader getNCharacterStream(int parameterIndex) throws SQLException;

    /**
     * Retrieves the value of the designated parameter as a
     * <code>java.io.Reader</code> object in the Java programming language.
     * It is intended for use when
     * accessing  <code>NCHAR</code>,<code>NVARCHAR</code>
     * and <code>LONGNVARCHAR</code> parameters.
     *
     * <p>
     *  在Java编程语言中将指定参数的值检索为<code> javaioReader </code>对象它适用于访问<code> NCHAR </code>,<code> NVARCHAR </code>和
     * <code > LONGNVARCHAR </code>参数。
     * 
     * 
     * @param parameterName the name of the parameter
     * @return a <code>java.io.Reader</code> object that contains the parameter
     * value; if the value is SQL <code>NULL</code>, the value returned is
     * <code>null</code> in the Java programming language
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.6
     */
    java.io.Reader getNCharacterStream(String parameterName) throws SQLException;

    /**
     * Retrieves the value of the designated parameter as a
     * <code>java.io.Reader</code> object in the Java programming language.
     *
     * <p>
     *  将指定参数的值作为Java编程语言中的<code> javaioReader </code>对象检索
     * 
     * 
     * @return a <code>java.io.Reader</code> object that contains the parameter
     * value; if the value is SQL <code>NULL</code>, the value returned is
     * <code>null</code> in the Java programming language.
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @exception SQLException if the parameterIndex is not valid; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @since 1.6
     */
    java.io.Reader getCharacterStream(int parameterIndex) throws SQLException;

    /**
     * Retrieves the value of the designated parameter as a
     * <code>java.io.Reader</code> object in the Java programming language.
     *
     * <p>
     *  将指定参数的值作为Java编程语言中的<code> javaioReader </code>对象检索
     * 
     * 
     * @param parameterName the name of the parameter
     * @return a <code>java.io.Reader</code> object that contains the parameter
     * value; if the value is SQL <code>NULL</code>, the value returned is
     * <code>null</code> in the Java programming language
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.6
     */
    java.io.Reader getCharacterStream(String parameterName) throws SQLException;

    /**
     * Sets the designated parameter to the given <code>java.sql.Blob</code> object.
     * The driver converts this to an SQL <code>BLOB</code> value when it
     * sends it to the database.
     *
     * <p>
     * 将指定的参数设置为给定的<code> javasqlBlob </code>对象当驱动程序将它发送到数据库时,将其转换为SQL <code> BLOB </code>
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x a <code>Blob</code> object that maps an SQL <code>BLOB</code> value
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.6
     */
    void setBlob (String parameterName, Blob x) throws SQLException;

    /**
     * Sets the designated parameter to the given <code>java.sql.Clob</code> object.
     * The driver converts this to an SQL <code>CLOB</code> value when it
     * sends it to the database.
     *
     * <p>
     *  将指定的参数设置为给定的<code> javasqlClob </code>对象当驱动程序将它发送到数据库时,将其转换为SQL <code> CLOB </code>
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x a <code>Clob</code> object that maps an SQL <code>CLOB</code> value
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.6
     */
    void setClob (String parameterName, Clob x) throws SQLException;
    /**
     * Sets the designated parameter to the given input stream, which will have
     * the specified number of bytes.
     * When a very large ASCII value is input to a <code>LONGVARCHAR</code>
     * parameter, it may be more practical to send it via a
     * <code>java.io.InputStream</code>. Data will be read from the stream
     * as needed until end-of-file is reached.  The JDBC driver will
     * do any necessary conversion from ASCII to the database char format.
     *
     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     *
     * <p>
     *  将指定的参数设置为给定的输入流,它将具有指定的字节数当非常大的ASCII值输入到<code> LONGVARCHAR </code>参数时,通过<code> > javaioInputStream </code>
     * 将根据需要从流中读取数据,直到达到文件结束JDBC驱动程序将执行从ASCII到数据库字符格式的必要转换。
     * 
     * <P> <B>注意：</B>此流对象可以是标准Java流对象或您自己的子类,实现标准接口
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the Java input stream that contains the ASCII parameter value
     * @param length the number of bytes in the stream
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.6
     */
    void setAsciiStream(String parameterName, java.io.InputStream x, long length)
        throws SQLException;

    /**
     * Sets the designated parameter to the given input stream, which will have
     * the specified number of bytes.
     * When a very large binary value is input to a <code>LONGVARBINARY</code>
     * parameter, it may be more practical to send it via a
     * <code>java.io.InputStream</code> object. The data will be read from the stream
     * as needed until end-of-file is reached.
     *
     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     *
     * <p>
     *  将指定的参数设置为给定的输入流,其将具有指定的字节数当非常大的二进制值被输入到<code> LONGVARBINARY </code>参数时,通过<code> > javaioInputStream 
     * </code> object数据将根据需要从流中读取,直到达到文件结束。
     * 
     *  <P> <B>注意：</B>此流对象可以是标准Java流对象或您自己的子类,实现标准接口
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the java input stream which contains the binary parameter value
     * @param length the number of bytes in the stream
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.6
     */
    void setBinaryStream(String parameterName, java.io.InputStream x,
                         long length) throws SQLException;
        /**
     * Sets the designated parameter to the given <code>Reader</code>
     * object, which is the given number of characters long.
     * When a very large UNICODE value is input to a <code>LONGVARCHAR</code>
     * parameter, it may be more practical to send it via a
     * <code>java.io.Reader</code> object. The data will be read from the stream
     * as needed until end-of-file is reached.  The JDBC driver will
     * do any necessary conversion from UNICODE to the database char format.
     *
     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     *
     * <p>
     * 将指定的参数设置为给定的<code> Reader </code>对象,这是给定的字符数长当将非常大的UNICODE值输入到<code> LONGVARCHAR </code>参数时,通过<code> 
     * javaioReader </code>对象发送它。
     * 数据将根据需要从流中读取,直到达到文件结束JDBC驱动程序将执行从UNICODE到数据库字符格式的任何必要的转换。
     * 
     *  <P> <B>注意：</B>此流对象可以是标准Java流对象或您自己的子类,实现标准接口
     * 
     * 
     * @param parameterName the name of the parameter
     * @param reader the <code>java.io.Reader</code> object that
     *        contains the UNICODE data used as the designated parameter
     * @param length the number of characters in the stream
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.6
     */
    void setCharacterStream(String parameterName,
                            java.io.Reader reader,
                            long length) throws SQLException;
     //--
    /**
     * Sets the designated parameter to the given input stream.
     * When a very large ASCII value is input to a <code>LONGVARCHAR</code>
     * parameter, it may be more practical to send it via a
     * <code>java.io.InputStream</code>. Data will be read from the stream
     * as needed until end-of-file is reached.  The JDBC driver will
     * do any necessary conversion from ASCII to the database char format.
     *
     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * <code>setAsciiStream</code> which takes a length parameter.
     *
     * <p>
     * 将指定的参数设置为给定的输入流当一个非常大的ASCII值输入到<code> LONGVARCHAR </code>参数时,通过<code> javaioInputStream </code>从流中读取,
     * 直到达到文件结束JDBC驱动程序将执行从ASCII到数据库字符格式的任何必要的转换。
     * 
     *  <P> <B>注意：</B>此流对象可以是标准Java流对象或实现标准接口<P> <B>的自己的子类注意：</B>请参阅您的JDBC驱动程序文档确定是否可能更有效地使用一个版本的<code> setA
     * sciiStream </code>,它需要一个长度参数。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the Java input stream that contains the ASCII parameter value
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
       * @since 1.6
    */
    void setAsciiStream(String parameterName, java.io.InputStream x)
            throws SQLException;
    /**
     * Sets the designated parameter to the given input stream.
     * When a very large binary value is input to a <code>LONGVARBINARY</code>
     * parameter, it may be more practical to send it via a
     * <code>java.io.InputStream</code> object. The data will be read from the
     * stream as needed until end-of-file is reached.
     *
     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * <code>setBinaryStream</code> which takes a length parameter.
     *
     * <p>
     * 将指定的参数设置为给定的输入流当非常大的二进制值被输入到<code> LONGVARBINARY </code>参数时,通过<code> javaioInputStream </code>将根据需要从流
     * 中读取,直到达到文件结束。
     * 
     *  <P> <B>注意：</B>此流对象可以是标准Java流对象或实现标准接口<P> <B>的自己的子类注意：</B>请参阅您的JDBC驱动程序文档确定是否使用一个带有长度参数的<code> setBin
     * aryStream </code>版本可能更有效。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the java input stream which contains the binary parameter value
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
     * @since 1.6
     */
    void setBinaryStream(String parameterName, java.io.InputStream x)
    throws SQLException;
    /**
     * Sets the designated parameter to the given <code>Reader</code>
     * object.
     * When a very large UNICODE value is input to a <code>LONGVARCHAR</code>
     * parameter, it may be more practical to send it via a
     * <code>java.io.Reader</code> object. The data will be read from the stream
     * as needed until end-of-file is reached.  The JDBC driver will
     * do any necessary conversion from UNICODE to the database char format.
     *
     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * <code>setCharacterStream</code> which takes a length parameter.
     *
     * <p>
     * 将指定的参数设置为给定的<code> Reader </code>对象当将一个非常大的UNICODE值输入到<code> LONGVARCHAR </code>参数时,通过<code> javaioRe
     * ader </code>对象将根据需要从流中读取数据,直到达到文件结束JDBC驱动程序将执行从UNICODE到数据库字符格式的任何必要的转换。
     * 
     *  <P> <B>注意：</B>此流对象可以是标准Java流对象或实现标准接口<P> <B>的自己的子类注意：</B>请参阅您的JDBC驱动程序文档确定是否可能更有效地使用<code> setCharac
     * terStream </code>的版本,该方法需要一个长度参数。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param reader the <code>java.io.Reader</code> object that contains the
     *        Unicode data
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
     * @since 1.6
     */
    void setCharacterStream(String parameterName,
                          java.io.Reader reader) throws SQLException;
  /**
     * Sets the designated parameter to a <code>Reader</code> object. The
     * <code>Reader</code> reads the data till end-of-file is reached. The
     * driver does the necessary conversion from Java character format to
     * the national character set in the database.

     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * <code>setNCharacterStream</code> which takes a length parameter.
     *
     * <p>
     * 将指定的参数设置为<code> Reader </code>对象<code> Reader </code>读取数据,直到达到文件结束驱动程序执行必要的从Java字符格式到国家字符集的转换在数据库中
     * 
     *  <P> <B>注意：</B>此流对象可以是标准Java流对象或实现标准接口<P> <B>的自己的子类注意：</B>请参阅您的JDBC驱动程序文档确定是否可能更有效地使用一个版本的<code> setN
     * CharacterStream </code>,它需要一个长度参数。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param value the parameter value
     * @throws SQLException if parameterName does not correspond to a named
     * parameter; if the driver does not support national
     *         character sets;  if the driver can detect that a data conversion
     *  error could occur; if a database access error occurs; or
     * this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
     * @since 1.6
     */
     void setNCharacterStream(String parameterName, Reader value) throws SQLException;

    /**
     * Sets the designated parameter to a <code>Reader</code> object.
     * This method differs from the <code>setCharacterStream (int, Reader)</code> method
     * because it informs the driver that the parameter value should be sent to
     * the server as a <code>CLOB</code>.  When the <code>setCharacterStream</code> method is used, the
     * driver may have to do extra work to determine whether the parameter
     * data should be send to the server as a <code>LONGVARCHAR</code> or a <code>CLOB</code>
     *
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * <code>setClob</code> which takes a length parameter.
     *
     * <p>
     * 将指定的参数设置为<code> Reader </code>对象此方法与<code> setCharacterStream(int,Reader)</code>方法不同,因为它通知驱动程序应将参数值发送
     * 到服务器a <code> CLOB </code>当使用<code> setCharacterStream </code>方法时,驱动程序可能需要做额外的工作来确定参数数据是否应该作为<code> LO
     * NGVARCHAR < / code>或<code> CLOB </code>。
     * 
     *  <P> <B>注意：</B>请查看您的JDBC驱动程序文档,以确定是否使用一个版本的<code> setClob </code>
     * 
     * 
     * @param parameterName the name of the parameter
     * @param reader An object that contains the data to set the parameter value to.
     * @throws SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or this method is called on
     * a closed <code>CallableStatement</code>
     *
     * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
     * @since 1.6
     */
     void setClob(String parameterName, Reader reader)
       throws SQLException;

    /**
     * Sets the designated parameter to a <code>InputStream</code> object.
     * This method differs from the <code>setBinaryStream (int, InputStream)</code>
     * method because it informs the driver that the parameter value should be
     * sent to the server as a <code>BLOB</code>.  When the <code>setBinaryStream</code> method is used,
     * the driver may have to do extra work to determine whether the parameter
     * data should be send to the server as a <code>LONGVARBINARY</code> or a <code>BLOB</code>
     *
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * <code>setBlob</code> which takes a length parameter.
     *
     * <p>
     * 将指定的参数设置为<code> InputStream </code>对象此方法与<code> setBinaryStream(int,InputStream)</code>方法不同,因为它通知驱动程序
     * 应将参数值发送到服务器a <code> BLOB </code>当使用<code> setBinaryStream </code>方法时,驱动程序可能需要做额外的工作来确定参数数据是否应该作为<code>
     *  LONGVARBINARY < / code>或<code> BLOB </code>。
     * 
     *  <P> <B>注意：</B>请查看您的JDBC驱动程序文档,以确定是否使用一个版本的<code> setBlob </code>
     * 
     * 
     * @param parameterName the name of the parameter
     * @param inputStream An object that contains the data to set the parameter
     * value to.
     * @throws SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
     *
     * @since 1.6
     */
     void setBlob(String parameterName, InputStream inputStream)
        throws SQLException;
    /**
     * Sets the designated parameter to a <code>Reader</code> object.
     * This method differs from the <code>setCharacterStream (int, Reader)</code> method
     * because it informs the driver that the parameter value should be sent to
     * the server as a <code>NCLOB</code>.  When the <code>setCharacterStream</code> method is used, the
     * driver may have to do extra work to determine whether the parameter
     * data should be send to the server as a <code>LONGNVARCHAR</code> or a <code>NCLOB</code>
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * <code>setNClob</code> which takes a length parameter.
     *
     * <p>
     * 将指定的参数设置为<code> Reader </code>对象此方法与<code> setCharacterStream(int,Reader)</code>方法不同,因为它通知驱动程序应将参数值发送
     * 到服务器a <code> NCLOB </code>当使用<code> setCharacterStream </code>方法时,驱动程序可能需要做额外的工作来确定参数数据是否应该作为<code> L
     * ONGNVARCHAR < / code>或<code> NCLOB </code> <P> <B>注意：</B>请参阅您的JDBC驱动程序文档,以确定是否使用<code> setNClob </code >
     * 它采用长度参数。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param reader An object that contains the data to set the parameter value to.
     * @throws SQLException if parameterName does not correspond to a named
     * parameter; if the driver does not support national character sets;
     * if the driver can detect that a data conversion
     *  error could occur;  if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
     *
     * @since 1.6
     */
     void setNClob(String parameterName, Reader reader)
       throws SQLException;

    //------------------------- JDBC 4.1 -----------------------------------


    /**
     *<p>Returns an object representing the value of OUT parameter
     * {@code parameterIndex} and will convert from the
     * SQL type of the parameter to the requested Java data type, if the
     * conversion is supported. If the conversion is not
     * supported or null is specified for the type, a
     * <code>SQLException</code> is thrown.
     *<p>
     * At a minimum, an implementation must support the conversions defined in
     * Appendix B, Table B-3 and conversion of appropriate user defined SQL
     * types to a Java type which implements {@code SQLData}, or {@code Struct}.
     * Additional conversions may be supported and are vendor defined.
     *
     * <p>
     * p>返回表示OUT参数{@code parameterIndex}的值的对象,如果支持转换,则将从参数的SQL类型转换为请求的Java数据类型如果不支持转换或为类型,则抛出<code> SQLExcep
     * tion </code>。
     * p>
     *  实现必须至少支持附录B,表B-3中定义的转换,并将适当的用户定义的SQL类型转换为实现{@code SQLData}或{@code Struct}的Java类型。可能支持其他转换并且是供应商定义的
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2, and so on
     * @param type Class representing the Java data type to convert the
     * designated parameter to.
     * @param <T> the type of the class modeled by this Class object
     * @return an instance of {@code type} holding the OUT parameter value
     * @throws SQLException if conversion is not supported, type is null or
     *         another error occurs. The getCause() method of the
     * exception may provide a more detailed exception, for example, if
     * a conversion error occurs
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.7
     */
     public <T> T getObject(int parameterIndex, Class<T> type) throws SQLException;


    /**
     *<p>Returns an object representing the value of OUT parameter
     * {@code parameterName} and will convert from the
     * SQL type of the parameter to the requested Java data type, if the
     * conversion is supported. If the conversion is not
     * supported  or null is specified for the type, a
     * <code>SQLException</code> is thrown.
     *<p>
     * At a minimum, an implementation must support the conversions defined in
     * Appendix B, Table B-3 and conversion of appropriate user defined SQL
     * types to a Java type which implements {@code SQLData}, or {@code Struct}.
     * Additional conversions may be supported and are vendor defined.
     *
     * <p>
     * p>返回表示OUT参数{@code parameterName}的值的对象,如果支持转换,则将从参数的SQL类型转换为请求的Java数据类型如果转换不受支持或为类型,则抛出<code> SQLExcep
     * tion </code>。
     * p>
     *  实现必须至少支持附录B,表B-3中定义的转换,并将适当的用户定义的SQL类型转换为实现{@code SQLData}或{@code Struct}的Java类型。可能支持其他转换并且是供应商定义的
     * 
     * 
     * @param parameterName the name of the parameter
     * @param type Class representing the Java data type to convert
     * the designated parameter to.
     * @param <T> the type of the class modeled by this Class object
     * @return an instance of {@code type} holding the OUT parameter
     * value
     * @throws SQLException if conversion is not supported, type is null or
     *         another error occurs. The getCause() method of the
     * exception may provide a more detailed exception, for example, if
     * a conversion error occurs
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.7
     */
     public <T> T getObject(String parameterName, Class<T> type) throws SQLException;

     //------------------------- JDBC 4.2 -----------------------------------

     /**
     * <p>Sets the value of the designated parameter with the given object.
     *
     * If the second argument is an {@code InputStream} then the stream
     * must contain the number of bytes specified by scaleOrLength.
     * If the second argument is a {@code Reader} then the reader must
     * contain the number of characters specified
     * by scaleOrLength. If these conditions are not true the driver
     * will generate a
     * {@code SQLException} when the prepared statement is executed.
     *
     * <p>The given Java object will be converted to the given targetSqlType
     * before being sent to the database.
     *
     * If the object has a custom mapping (is of a class implementing the
     * interface {@code SQLData}),
     * the JDBC driver should call the method {@code SQLData.writeSQL} to
     * write it to the SQL data stream.
     * If, on the other hand, the object is of a class implementing
     * {@code Ref}, {@code Blob}, {@code Clob},  {@code NClob},
     *  {@code Struct}, {@code java.net.URL},
     * or {@code Array}, the driver should pass it to the database as a
     * value of the corresponding SQL type.
     *
     * <p>Note that this method may be used to pass database-specific
     * abstract data types.
     *<P>
     * The default implementation will throw {@code SQLFeatureNotSupportedException}
     *
     * <p>
     *  <p>使用给定对象设置指定参数的值
     * 
     * 如果第二个参数是{@code InputStream},那么流必须包含由scaleOrLength指定的字节数。
     * 如果第二个参数是{@code Reader},则读者必须包含由scaleOrLength指定的字符数。如果这些条件不是真的,驱动程序将在执行预准备语句时生成{@code SQLException}。
     * 
     *  <p>给定的Java对象在发送到数据库之前将被转换为给定的targetSqlType
     * 
     * 如果对象具有自定义映射(是实现接口{@code SQLData}的类),JDBC驱动程序应调用{@code SQLDatawriteSQL}方法将其写入SQL数据流。
     * 另一方面,如果对象是实现{@code Ref},{@code Blob},{@code Clob},{@code NClob},{@code Struct},{@code javanetURL}或{@code Array}
     * 驱动程序应将其作为相应SQL类型的值传递到数据库。
     * 如果对象具有自定义映射(是实现接口{@code SQLData}的类),JDBC驱动程序应调用{@code SQLDatawriteSQL}方法将其写入SQL数据流。
     * 
     *  <p>请注意,此方法可用于传递特定于数据库的抽象数据类型
     * P>
     *  默认实现会抛出{@code SQLFeatureNotSupportedException}
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the object containing the input parameter value
     * @param targetSqlType the SQL type to be
     * sent to the database. The scale argument may further qualify this type.
     * @param scaleOrLength for {@code java.sql.JDBCType.DECIMAL}
     *          or {@code java.sql.JDBCType.NUMERIC types},
     *          this is the number of digits after the decimal point. For
     *          Java Object types {@code InputStream} and {@code Reader},
     *          this is the length
     *          of the data in the stream or reader.  For all other types,
     *          this value will be ignored.
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs
     * or this method is called on a closed {@code CallableStatement}  or
     *            if the Java Object specified by x is an InputStream
     *            or Reader object and the value of the scale parameter is less
     *            than zero
     * @exception SQLFeatureNotSupportedException if
     * the JDBC driver does not support the specified targetSqlType
     * @see JDBCType
     * @see SQLType
     *
     * @since 1.8
     */
     default void setObject(String parameterName, Object x, SQLType targetSqlType,
             int scaleOrLength) throws SQLException {
        throw new SQLFeatureNotSupportedException("setObject not implemented");
    }
    /**
     * Sets the value of the designated parameter with the given object.
     *
     * This method is similar to {@link #setObject(String parameterName,
     * Object x, SQLType targetSqlType, int scaleOrLength)},
     * except that it assumes a scale of zero.
     *<P>
     * The default implementation will throw {@code SQLFeatureNotSupportedException}
     *
     * <p>
     *  使用给定对象设置指定参数的值
     * 
     * 此方法类似于{@link #setObject(String parameterName,Object x,SQLType targetSqlType,int scaleOrLength)},除了它假设
     * 一个零比例。
     * P>
     *  默认实现会抛出{@code SQLFeatureNotSupportedException}
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the object containing the input parameter value
     * @param targetSqlType the SQL type to be sent to the database
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs
     * or this method is called on a closed {@code CallableStatement}
     * @exception SQLFeatureNotSupportedException if
     * the JDBC driver does not support the specified targetSqlType
     * @see JDBCType
     * @see SQLType
     * @since 1.8
     */
     default void setObject(String parameterName, Object x, SQLType targetSqlType)
        throws SQLException {
        throw new SQLFeatureNotSupportedException("setObject not implemented");
    }

    /**
     * Registers the OUT parameter in ordinal position
     * {@code parameterIndex} to the JDBC type
     * {@code sqlType}.  All OUT parameters must be registered
     * before a stored procedure is executed.
     * <p>
     * The JDBC type specified by {@code sqlType} for an OUT
     * parameter determines the Java type that must be used
     * in the {@code get} method to read the value of that parameter.
     * <p>
     * If the JDBC type expected to be returned to this output parameter
     * is specific to this particular database, {@code sqlType}
     * may be {@code JDBCType.OTHER} or a {@code SQLType} that is supported by
     * the JDBC driver.  The method
     * {@link #getObject} retrieves the value.
     *<P>
     * The default implementation will throw {@code SQLFeatureNotSupportedException}
     *
     * <p>
     *  将顺序位置{@code parameterIndex}中的OUT参数注册到JDBC类型{@code sqlType}所有OUT参数必须在执行存储过程之前注册
     * <p>
     *  由{@code sqlType}为OUT参数指定的JDBC类型确定在{@code get}方法中必须使用的Java类型,以读取该参数的值
     * <p>
     * 如果希望返回到此输出参数的JDBC类型特定于此特定数据库,则{@code sqlType}可能是{@code JDBCTypeOTHER}或JDBC驱动程序支持的{@code SQLType}方法{@link #getObject}
     * 检索值。
     * P>
     *  默认实现会抛出{@code SQLFeatureNotSupportedException}
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2,
     *        and so on
     * @param sqlType the JDBC type code defined by {@code SQLType} to use to
     * register the OUT Parameter.
     *        If the parameter is of JDBC type {@code JDBCType.NUMERIC}
     *        or {@code JDBCType.DECIMAL}, the version of
     *        {@code registerOutParameter} that accepts a scale value
     *        should be used.
     *
     * @exception SQLException if the parameterIndex is not valid;
     * if a database access error occurs or
     * this method is called on a closed {@code CallableStatement}
     * @exception SQLFeatureNotSupportedException if
     * the JDBC driver does not support the specified sqlType
     * @see JDBCType
     * @see SQLType
     * @since 1.8
     */
    default void registerOutParameter(int parameterIndex, SQLType sqlType)
        throws SQLException {
        throw new SQLFeatureNotSupportedException("registerOutParameter not implemented");
    }

    /**
     * Registers the parameter in ordinal position
     * {@code parameterIndex} to be of JDBC type
     * {@code sqlType}. All OUT parameters must be registered
     * before a stored procedure is executed.
     * <p>
     * The JDBC type specified by {@code sqlType} for an OUT
     * parameter determines the Java type that must be used
     * in the {@code get} method to read the value of that parameter.
     * <p>
     * This version of {@code  registerOutParameter} should be
     * used when the parameter is of JDBC type {@code JDBCType.NUMERIC}
     * or {@code JDBCType.DECIMAL}.
     *<P>
     * The default implementation will throw {@code SQLFeatureNotSupportedException}
     *
     * <p>
     *  将顺序位置的参数{@code parameterIndex}注册为JDBC类型{@code sqlType}所有OUT参数必须在执行存储过程之前注册
     * <p>
     *  由{@code sqlType}为OUT参数指定的JDBC类型确定在{@code get}方法中必须使用的Java类型,以读取该参数的值
     * <p>
     * 当参数是JDBC类型{@code JDBCTypeNUMERIC}或{@code JDBCTypeDECIMAL}时,应使用此版本的{@code registerOutParameter}
     * P>
     *  默认实现会抛出{@code SQLFeatureNotSupportedException}
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2,
     * and so on
     * @param sqlType the JDBC type code defined by {@code SQLType} to use to
     * register the OUT Parameter.
     * @param scale the desired number of digits to the right of the
     * decimal point.  It must be greater than or equal to zero.
     * @exception SQLException if the parameterIndex is not valid;
     * if a database access error occurs or
     * this method is called on a closed {@code CallableStatement}
     * @exception SQLFeatureNotSupportedException if
     * the JDBC driver does not support the specified sqlType
     * @see JDBCType
     * @see SQLType
     * @since 1.8
     */
    default void registerOutParameter(int parameterIndex, SQLType sqlType,
            int scale) throws SQLException {
        throw new SQLFeatureNotSupportedException("registerOutParameter not implemented");
    }
    /**
     * Registers the designated output parameter.
     * This version of
     * the method {@code  registerOutParameter}
     * should be used for a user-defined or {@code REF} output parameter.
     * Examples
     * of user-defined types include: {@code STRUCT}, {@code DISTINCT},
     * {@code JAVA_OBJECT}, and named array types.
     *<p>
     * All OUT parameters must be registered
     * before a stored procedure is executed.
     * <p>  For a user-defined parameter, the fully-qualified SQL
     * type name of the parameter should also be given, while a {@code REF}
     * parameter requires that the fully-qualified type name of the
     * referenced type be given.  A JDBC driver that does not need the
     * type code and type name information may ignore it.   To be portable,
     * however, applications should always provide these values for
     * user-defined and {@code REF} parameters.
     *
     * Although it is intended for user-defined and {@code REF} parameters,
     * this method may be used to register a parameter of any JDBC type.
     * If the parameter does not have a user-defined or {@code REF} type, the
     * <i>typeName</i> parameter is ignored.
     *
     * <P><B>Note:</B> When reading the value of an out parameter, you
     * must use the getter method whose Java type corresponds to the
     * parameter's registered SQL type.
     *<P>
     * The default implementation will throw {@code SQLFeatureNotSupportedException}
     *
     * <p>
     *  注册指定的输出参数此版本的方法{@code registerOutParameter}应用于用户定义或{@code REF}输出参数用户定义类型的示例包括：{@code STRUCT},{@code DISTINCT}
     *  ,{@code JAVA_OBJECT}和命名的数组类型。
     * p>
     * 所有OUT参数必须在执行存储过程之前注册<p>对于用户定义的参数,还应指定参数的完全限定的SQL类型名称,而{@code REF}给定引用类型的限定类型名称不需要类型代码和类型名称信息的JDBC驱动程序
     * 可能会忽略它为了便于移植,应用程序应始终为用户定义和{@code REF}参数提供这些值。
     * 
     *  虽然它用于用户定义和{@code REF}参数,但此方法可用于注册任何JDBC类型的参数。如果参数没有用户定义或{@code REF}类型,则<i > typeName </i>参数被忽略
     * 
     * <P> <B>注意：</B>读取out参数的值时,必须使用getter方法,其Java类型对应于参数的注册SQL类型
     * P>
     *  默认实现会抛出{@code SQLFeatureNotSupportedException}
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2,...
     * @param sqlType the JDBC type code defined by {@code SQLType} to use to
     * register the OUT Parameter.
     * @param typeName the fully-qualified name of an SQL structured type
     * @exception SQLException if the parameterIndex is not valid;
     * if a database access error occurs or
     * this method is called on a closed {@code CallableStatement}
     * @exception SQLFeatureNotSupportedException if
     * the JDBC driver does not support the specified sqlType
     * @see JDBCType
     * @see SQLType
     * @since 1.8
     */
    default void registerOutParameter (int parameterIndex, SQLType sqlType,
            String typeName) throws SQLException {
        throw new SQLFeatureNotSupportedException("registerOutParameter not implemented");
    }

    /**
     * Registers the OUT parameter named
     * <code>parameterName</code> to the JDBC type
     * {@code sqlType}.  All OUT parameters must be registered
     * before a stored procedure is executed.
     * <p>
     * The JDBC type specified by {@code sqlType} for an OUT
     * parameter determines the Java type that must be used
     * in the {@code get} method to read the value of that parameter.
     * <p>
     * If the JDBC type expected to be returned to this output parameter
     * is specific to this particular database, {@code sqlType}
     * should be {@code JDBCType.OTHER} or a {@code SQLType} that is supported
     * by the JDBC driver..  The method
     * {@link #getObject} retrieves the value.
     *<P>
     * The default implementation will throw {@code SQLFeatureNotSupportedException}
     *
     * <p>
     *  将名为<code> parameterName </code>的OUT参数注册到JDBC类型{@code sqlType}必须在执行存储过程之前注册所有OUT参数
     * <p>
     *  由{@code sqlType}为OUT参数指定的JDBC类型确定在{@code get}方法中必须使用的Java类型,以读取该参数的值
     * <p>
     * 如果希望返回到此输出参数的JDBC类型特定于此特定数据库,则{@code sqlType}应为{@code JDBCTypeOTHER}或JDBC驱动程序支持的{@code SQLType}方法{@link #getObject}
     * 检索值。
     * P>
     *  默认实现会抛出{@code SQLFeatureNotSupportedException}
     * 
     * 
     * @param parameterName the name of the parameter
     * @param sqlType the JDBC type code defined by {@code SQLType} to use to
     * register the OUT Parameter.
     * If the parameter is of JDBC type {@code JDBCType.NUMERIC}
     * or {@code JDBCType.DECIMAL}, the version of
     * {@code  registerOutParameter} that accepts a scale value
     * should be used.
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed {@code CallableStatement}
     * @exception SQLFeatureNotSupportedException if
     * the JDBC driver does not support the specified sqlType
     * or if the JDBC driver does not support
     * this method
     * @since 1.8
     * @see JDBCType
     * @see SQLType
     */
    default void registerOutParameter(String parameterName, SQLType sqlType)
        throws SQLException {
        throw new SQLFeatureNotSupportedException("registerOutParameter not implemented");
    }

    /**
     * Registers the parameter named
     * <code>parameterName</code> to be of JDBC type
     * {@code sqlType}.  All OUT parameters must be registered
     * before a stored procedure is executed.
     * <p>
     * The JDBC type specified by {@code sqlType} for an OUT
     * parameter determines the Java type that must be used
     * in the {@code get} method to read the value of that parameter.
     * <p>
     * This version of {@code  registerOutParameter} should be
     * used when the parameter is of JDBC type {@code JDBCType.NUMERIC}
     * or {@code JDBCType.DECIMAL}.
     *<P>
     * The default implementation will throw {@code SQLFeatureNotSupportedException}
     *
     * <p>
     *  将名为<code> parameterName </code>的参数注册为JDBC类型{@code sqlType}必须在执行存储过程之前注册所有OUT参数
     * <p>
     *  由{@code sqlType}为OUT参数指定的JDBC类型确定在{@code get}方法中必须使用的Java类型,以读取该参数的值
     * <p>
     * 当参数是JDBC类型{@code JDBCTypeNUMERIC}或{@code JDBCTypeDECIMAL}时,应使用此版本的{@code registerOutParameter}
     * P>
     *  默认实现会抛出{@code SQLFeatureNotSupportedException}
     * 
     * 
     * @param parameterName the name of the parameter
     * @param sqlType the JDBC type code defined by {@code SQLType} to use to
     * register the OUT Parameter.
     * @param scale the desired number of digits to the right of the
     * decimal point.  It must be greater than or equal to zero.
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed {@code CallableStatement}
     * @exception SQLFeatureNotSupportedException if
     * the JDBC driver does not support the specified sqlType
     * or if the JDBC driver does not support
     * this method
     * @since 1.8
     * @see JDBCType
     * @see SQLType
     */
    default void registerOutParameter(String parameterName, SQLType sqlType,
            int scale) throws SQLException {
        throw new SQLFeatureNotSupportedException("registerOutParameter not implemented");
    }

    /**
     * Registers the designated output parameter.  This version of
     * the method {@code  registerOutParameter}
     * should be used for a user-named or REF output parameter.  Examples
     * of user-named types include: STRUCT, DISTINCT, JAVA_OBJECT, and
     * named array types.
     *<p>
     * All OUT parameters must be registered
     * before a stored procedure is executed.
     * </p>
     * For a user-named parameter the fully-qualified SQL
     * type name of the parameter should also be given, while a REF
     * parameter requires that the fully-qualified type name of the
     * referenced type be given.  A JDBC driver that does not need the
     * type code and type name information may ignore it.   To be portable,
     * however, applications should always provide these values for
     * user-named and REF parameters.
     *
     * Although it is intended for user-named and REF parameters,
     * this method may be used to register a parameter of any JDBC type.
     * If the parameter does not have a user-named or REF type, the
     * typeName parameter is ignored.
     *
     * <P><B>Note:</B> When reading the value of an out parameter, you
     * must use the {@code getXXX} method whose Java type XXX corresponds to the
     * parameter's registered SQL type.
     *<P>
     * The default implementation will throw {@code SQLFeatureNotSupportedException}
     *
     * <p>
     *  注册指定的输出参数此版本的方法{@code registerOutParameter}应用于用户命名或REF输出参数用户命名类型的示例包括：STRUCT,DISTINCT,JAVA_OBJECT和命名
     * 的数组类型。
     * p>
     *  所有OUT参数必须在执行存储过程之前注册
     * </p>
     * 对于用户命名的参数,还应给出参数的完全限定的SQL类型名称,而REF参数要求给定引用类型的完全限定类型名称。
     * 不需要类型代码的JDBC驱动程序,类型名称信息可能会忽略它为了便于移植,应用程序应始终为用户命名和REF参数提供这些值。
     * 
     * @param parameterName the name of the parameter
     * @param sqlType the JDBC type code defined by {@code SQLType} to use to
     * register the OUT Parameter.
     * @param typeName the fully-qualified name of an SQL structured type
     * @exception SQLException if parameterName does not correspond to a named
     * parameter; if a database access error occurs or
     * this method is called on a closed {@code CallableStatement}
     * @exception SQLFeatureNotSupportedException if
     * the JDBC driver does not support the specified sqlType
     * or if the JDBC driver does not support this method
     * @see JDBCType
     * @see SQLType
     * @since 1.8
     */
    default void registerOutParameter (String parameterName, SQLType sqlType,
            String typeName) throws SQLException {
        throw new SQLFeatureNotSupportedException("registerOutParameter not implemented");
    }
}
