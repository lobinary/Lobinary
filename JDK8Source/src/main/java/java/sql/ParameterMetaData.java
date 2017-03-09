/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2006, Oracle and/or its affiliates. All rights reserved.
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
 * and properties for each parameter marker in a
 * <code>PreparedStatement</code> object. For some queries and driver
 * implementations, the data that would be returned by a <code>ParameterMetaData</code>
 * object may not be available until the <code>PreparedStatement</code> has
 * been executed.
 *<p>
 *Some driver implementations may not be able to provide information about the
 *types and properties for each parameter marker in a <code>CallableStatement</code>
 *object.
 *
 * <p>
 *  一个对象,可用于获取有关<code> PreparedStatement </code>对象中每个参数标记的类型和属性的信息。
 * 对于某些查询和驱动程序实现,由<code> ParameterMetaData </code>对象返回的数据在执行<code> PreparedStatement </code>之前可能不可用。
 * p>
 *  ome驱动程序实现可能无法为<code> CallableStatement </code>对象中的每个参数标记提供有关ypes和属性的信息。
 * 
 * 
 * @since 1.4
 */

public interface ParameterMetaData extends Wrapper {

    /**
     * Retrieves the number of parameters in the <code>PreparedStatement</code>
     * object for which this <code>ParameterMetaData</code> object contains
     * information.
     *
     * <p>
     *  检索此<code> ParameterMetaData </code>对象包含信息的<code> PreparedStatement </code>对象中的参数数。
     * 
     * 
     * @return the number of parameters
     * @exception SQLException if a database access error occurs
     * @since 1.4
     */
    int getParameterCount() throws SQLException;

    /**
     * Retrieves whether null values are allowed in the designated parameter.
     *
     * <p>
     *  检索指定参数中是否允许空值。
     * 
     * 
     * @param param the first parameter is 1, the second is 2, ...
     * @return the nullability status of the given parameter; one of
     *        <code>ParameterMetaData.parameterNoNulls</code>,
     *        <code>ParameterMetaData.parameterNullable</code>, or
     *        <code>ParameterMetaData.parameterNullableUnknown</code>
     * @exception SQLException if a database access error occurs
     * @since 1.4
     */
    int isNullable(int param) throws SQLException;

    /**
     * The constant indicating that a
     * parameter will not allow <code>NULL</code> values.
     * <p>
     *  常量,表示参数不允许<code> NULL </code>值。
     * 
     */
    int parameterNoNulls = 0;

    /**
     * The constant indicating that a
     * parameter will allow <code>NULL</code> values.
     * <p>
     *  常量指示参数将允许<code> NULL </code>值。
     * 
     */
    int parameterNullable = 1;

    /**
     * The constant indicating that the
     * nullability of a parameter is unknown.
     * <p>
     *  指示参数的可空性未知的常数。
     * 
     */
    int parameterNullableUnknown = 2;

    /**
     * Retrieves whether values for the designated parameter can be signed numbers.
     *
     * <p>
     *  检索指定参数的值是否可以是有符号数字。
     * 
     * 
     * @param param the first parameter is 1, the second is 2, ...
     * @return <code>true</code> if so; <code>false</code> otherwise
     * @exception SQLException if a database access error occurs
     * @since 1.4
     */
    boolean isSigned(int param) throws SQLException;

    /**
     * Retrieves the designated parameter's specified column size.
     *
     * <P>The returned value represents the maximum column size for the given parameter.
     * For numeric data, this is the maximum precision.  For character data, this is the length in characters.
     * For datetime datatypes, this is the length in characters of the String representation (assuming the
     * maximum allowed precision of the fractional seconds component). For binary data, this is the length in bytes.  For the ROWID datatype,
     * this is the length in bytes. 0 is returned for data types where the
     * column size is not applicable.
     *
     * <p>
     *  检索指定参数的指定列大小。
     * 
     * <P>返回的值表示给定参数的最大列大小。对于数字数据,这是最大精度。对于字符数据,这是以字符为单位的长度。对于datetime数据类型,这是字符串表示形式的长度(假设小数秒分量的最大允许精度)。
     * 对于二进制数据,这是以字节为单位的长度。对于ROWID数据类型,这是以字节为单位的长度。对于列大小不适用的数据类型,返回0。
     * 
     * 
     * @param param the first parameter is 1, the second is 2, ...
     * @return precision
     * @exception SQLException if a database access error occurs
     * @since 1.4
     */
    int getPrecision(int param) throws SQLException;

    /**
     * Retrieves the designated parameter's number of digits to right of the decimal point.
     * 0 is returned for data types where the scale is not applicable.
     *
     * <p>
     *  检索指定参数的小数点右边的位数。对于不适用比例的数据类型,返回0。
     * 
     * 
     * @param param the first parameter is 1, the second is 2, ...
     * @return scale
     * @exception SQLException if a database access error occurs
     * @since 1.4
     */
    int getScale(int param) throws SQLException;

    /**
     * Retrieves the designated parameter's SQL type.
     *
     * <p>
     *  检索指定的参数的SQL类型。
     * 
     * 
     * @param param the first parameter is 1, the second is 2, ...
     * @return SQL type from <code>java.sql.Types</code>
     * @exception SQLException if a database access error occurs
     * @since 1.4
     * @see Types
     */
    int getParameterType(int param) throws SQLException;

    /**
     * Retrieves the designated parameter's database-specific type name.
     *
     * <p>
     *  检索指定参数的特定于数据库的类型名称。
     * 
     * 
     * @param param the first parameter is 1, the second is 2, ...
     * @return type the name used by the database. If the parameter type is
     * a user-defined type, then a fully-qualified type name is returned.
     * @exception SQLException if a database access error occurs
     * @since 1.4
     */
    String getParameterTypeName(int param) throws SQLException;


    /**
     * Retrieves the fully-qualified name of the Java class whose instances
     * should be passed to the method <code>PreparedStatement.setObject</code>.
     *
     * <p>
     *  检索Java类的完全限定名称,其实例应传递到方法<code> PreparedStatement.setObject </code>。
     * 
     * 
     * @param param the first parameter is 1, the second is 2, ...
     * @return the fully-qualified name of the class in the Java programming
     *         language that would be used by the method
     *         <code>PreparedStatement.setObject</code> to set the value
     *         in the specified parameter. This is the class name used
     *         for custom mapping.
     * @exception SQLException if a database access error occurs
     * @since 1.4
     */
    String getParameterClassName(int param) throws SQLException;

    /**
     * The constant indicating that the mode of the parameter is unknown.
     * <p>
     *  常数,表示参数的模式未知。
     * 
     */
    int parameterModeUnknown = 0;

    /**
     * The constant indicating that the parameter's mode is IN.
     * <p>
     *  常数,表示参数的模式为IN。
     * 
     */
    int parameterModeIn = 1;

    /**
     * The constant indicating that the parameter's mode is INOUT.
     * <p>
     *  常数,表示参数的模式为INOUT。
     * 
     */
    int parameterModeInOut = 2;

    /**
     * The constant indicating that the parameter's mode is  OUT.
     * <p>
     *  常数,表示参数的模式为OUT。
     * 
     */
    int parameterModeOut = 4;

    /**
     * Retrieves the designated parameter's mode.
     *
     * <p>
     *  检索指定参数的模式。
     * 
     * @param param the first parameter is 1, the second is 2, ...
     * @return mode of the parameter; one of
     *        <code>ParameterMetaData.parameterModeIn</code>,
     *        <code>ParameterMetaData.parameterModeOut</code>, or
     *        <code>ParameterMetaData.parameterModeInOut</code>
     *        <code>ParameterMetaData.parameterModeUnknown</code>.
     * @exception SQLException if a database access error occurs
     * @since 1.4
     */
    int getParameterMode(int param) throws SQLException;
}
