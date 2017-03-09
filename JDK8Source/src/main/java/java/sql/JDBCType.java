/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
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
 * <P>Defines the constants that are used to identify generic
 * SQL types, called JDBC types.
 * <p>
 * <p>
 *  <P>定义用于标识通用SQL类型(称为JDBC类型)的常量。
 * <p>
 * 
 * @see SQLType
 * @since 1.8
 */
public enum JDBCType implements SQLType {

    /**
     * Identifies the generic SQL type {@code BIT}.
     * <p>
     *  标识通用SQL类型{@code BIT}。
     * 
     */
    BIT(Types.BIT),
    /**
     * Identifies the generic SQL type {@code TINYINT}.
     * <p>
     *  标识通用SQL类型{@code TINYINT}。
     * 
     */
    TINYINT(Types.TINYINT),
    /**
     * Identifies the generic SQL type {@code SMALLINT}.
     * <p>
     *  标识通用SQL类型{@code SMALLINT}。
     * 
     */
    SMALLINT(Types.SMALLINT),
    /**
     * Identifies the generic SQL type {@code INTEGER}.
     * <p>
     *  标识通用SQL类型{@code INTEGER}。
     * 
     */
    INTEGER(Types.INTEGER),
    /**
     * Identifies the generic SQL type {@code BIGINT}.
     * <p>
     *  标识通用SQL类型{@code BIGINT}。
     * 
     */
    BIGINT(Types.BIGINT),
    /**
     * Identifies the generic SQL type {@code FLOAT}.
     * <p>
     *  标识通用SQL类型{@code FLOAT}。
     * 
     */
    FLOAT(Types.FLOAT),
    /**
     * Identifies the generic SQL type {@code REAL}.
     * <p>
     *  标识通用SQL类型{@code REAL}。
     * 
     */
    REAL(Types.REAL),
    /**
     * Identifies the generic SQL type {@code DOUBLE}.
     * <p>
     *  标识通用SQL类型{@code DOUBLE}。
     * 
     */
    DOUBLE(Types.DOUBLE),
    /**
     * Identifies the generic SQL type {@code NUMERIC}.
     * <p>
     *  标识通用SQL类型{@code NUMERIC}。
     * 
     */
    NUMERIC(Types.NUMERIC),
    /**
     * Identifies the generic SQL type {@code DECIMAL}.
     * <p>
     *  标识通用SQL类型{@code DECIMAL}。
     * 
     */
    DECIMAL(Types.DECIMAL),
    /**
     * Identifies the generic SQL type {@code CHAR}.
     * <p>
     *  标识通用SQL类型{@code CHAR}。
     * 
     */
    CHAR(Types.CHAR),
    /**
     * Identifies the generic SQL type {@code VARCHAR}.
     * <p>
     *  标识通用SQL类型{@code VARCHAR}。
     * 
     */
    VARCHAR(Types.VARCHAR),
    /**
     * Identifies the generic SQL type {@code LONGVARCHAR}.
     * <p>
     *  标识通用SQL类型{@code LONGVARCHAR}。
     * 
     */
    LONGVARCHAR(Types.LONGVARCHAR),
    /**
     * Identifies the generic SQL type {@code DATE}.
     * <p>
     *  标识通用SQL类型{@code DATE}。
     * 
     */
    DATE(Types.DATE),
    /**
     * Identifies the generic SQL type {@code TIME}.
     * <p>
     *  标识通用SQL类型{@code TIME}。
     * 
     */
    TIME(Types.TIME),
    /**
     * Identifies the generic SQL type {@code TIMESTAMP}.
     * <p>
     *  标识通用SQL类型{@code TIMESTAMP}。
     * 
     */
    TIMESTAMP(Types.TIMESTAMP),
    /**
     * Identifies the generic SQL type {@code BINARY}.
     * <p>
     *  标识通用SQL类型{@code BINARY}。
     * 
     */
    BINARY(Types.BINARY),
    /**
     * Identifies the generic SQL type {@code VARBINARY}.
     * <p>
     *  标识通用SQL类型{@code VARBINARY}。
     * 
     */
    VARBINARY(Types.VARBINARY),
    /**
     * Identifies the generic SQL type {@code LONGVARBINARY}.
     * <p>
     *  标识通用SQL类型{@code LONGVARBINARY}。
     * 
     */
    LONGVARBINARY(Types.LONGVARBINARY),
    /**
     * Identifies the generic SQL value {@code NULL}.
     * <p>
     *  标识通用SQL值{@code NULL}。
     * 
     */
    NULL(Types.NULL),
    /**
     * Indicates that the SQL type
     * is database-specific and gets mapped to a Java object that can be
     * accessed via the methods getObject and setObject.
     * <p>
     *  表示SQL类型是数据库特定的,并且映射到可以通过方法getObject和setObject访问的Java对象。
     * 
     */
    OTHER(Types.OTHER),
    /**
     * Indicates that the SQL type
     * is database-specific and gets mapped to a Java object that can be
     * accessed via the methods getObject and setObject.
     * <p>
     * 表示SQL类型是数据库特定的,并且映射到可以通过方法getObject和setObject访问的Java对象。
     * 
     */
    JAVA_OBJECT(Types.JAVA_OBJECT),
    /**
     * Identifies the generic SQL type {@code DISTINCT}.
     * <p>
     *  标识通用SQL类型{@code DISTINCT}。
     * 
     */
    DISTINCT(Types.DISTINCT),
    /**
     * Identifies the generic SQL type {@code STRUCT}.
     * <p>
     *  标识通用SQL类型{@code STRUCT}。
     * 
     */
    STRUCT(Types.STRUCT),
    /**
     * Identifies the generic SQL type {@code ARRAY}.
     * <p>
     *  标识通用SQL类型{@code ARRAY}。
     * 
     */
    ARRAY(Types.ARRAY),
    /**
     * Identifies the generic SQL type {@code BLOB}.
     * <p>
     *  标识通用SQL类型{@code BLOB}。
     * 
     */
    BLOB(Types.BLOB),
    /**
     * Identifies the generic SQL type {@code CLOB}.
     * <p>
     *  标识通用SQL类型{@code CLOB}。
     * 
     */
    CLOB(Types.CLOB),
    /**
     * Identifies the generic SQL type {@code REF}.
     * <p>
     *  标识通用SQL类型{@code REF}。
     * 
     */
    REF(Types.REF),
    /**
     * Identifies the generic SQL type {@code DATALINK}.
     * <p>
     *  标识通用SQL类型{@code DATALINK}。
     * 
     */
    DATALINK(Types.DATALINK),
    /**
     * Identifies the generic SQL type {@code BOOLEAN}.
     * <p>
     *  标识通用SQL类型{@code BOOLEAN}。
     * 
     */
    BOOLEAN(Types.BOOLEAN),

    /* JDBC 4.0 Types */

    /**
     * Identifies the SQL type {@code ROWID}.
     * <p>
     *  标识SQL类型{@code ROWID}。
     * 
     */
    ROWID(Types.ROWID),
    /**
     * Identifies the generic SQL type {@code NCHAR}.
     * <p>
     *  标识通用SQL类型{@code NCHAR}。
     * 
     */
    NCHAR(Types.NCHAR),
    /**
     * Identifies the generic SQL type {@code NVARCHAR}.
     * <p>
     *  标识通用SQL类型{@code NVARCHAR}。
     * 
     */
    NVARCHAR(Types.NVARCHAR),
    /**
     * Identifies the generic SQL type {@code LONGNVARCHAR}.
     * <p>
     *  标识通用SQL类型{@code LONGNVARCHAR}。
     * 
     */
    LONGNVARCHAR(Types.LONGNVARCHAR),
    /**
     * Identifies the generic SQL type {@code NCLOB}.
     * <p>
     *  标识通用SQL类型{@code NCLOB}。
     * 
     */
    NCLOB(Types.NCLOB),
    /**
     * Identifies the generic SQL type {@code SQLXML}.
     * <p>
     *  标识通用SQL类型{@code SQLXML}。
     * 
     */
    SQLXML(Types.SQLXML),

    /* JDBC 4.2 Types */

    /**
     * Identifies the generic SQL type {@code REF_CURSOR}.
     * <p>
     *  标识通用SQL类型{@code REF_CURSOR}。
     * 
     */
    REF_CURSOR(Types.REF_CURSOR),

    /**
     * Identifies the generic SQL type {@code TIME_WITH_TIMEZONE}.
     * <p>
     *  标识通用SQL类型{@code TIME_WITH_TIMEZONE}。
     * 
     */
    TIME_WITH_TIMEZONE(Types.TIME_WITH_TIMEZONE),

    /**
     * Identifies the generic SQL type {@code TIMESTAMP_WITH_TIMEZONE}.
     * <p>
     *  标识通用SQL类型{@code TIMESTAMP_WITH_TIMEZONE}。
     * 
     */
    TIMESTAMP_WITH_TIMEZONE(Types.TIMESTAMP_WITH_TIMEZONE);

    /**
     * The Integer value for the JDBCType.  It maps to a value in
     * {@code Types.java}
     * <p>
     *  JDBCType的Integer值。它映射到{@code Types.java}中的值
     * 
     */
    private Integer type;

    /**
     * Constructor to specify the data type value from {@code Types) for
     * this data type.
     * <p>
     *  构造函数用于从此数据类型的{@code Types)中指定数据类型值。
     * 
     * 
     * @param type The value from {@code Types) for this data type
     */
    JDBCType(final Integer type) {
        this.type = type;
    }

    /**
     *{@inheritDoc }
     * <p>
     *  @inheritDoc}
     * 
     * 
     * @return The name of this {@code SQLType}.
     */
    public String getName() {
        return name();
    }
    /**
     * Returns the name of the vendor that supports this data type.
     * <p>
     *  返回支持此数据类型的供应商的名称。
     * 
     * 
     * @return  The name of the vendor for this data type which is
     * {@literal java.sql} for JDBCType.
     */
    public String getVendor() {
        return "java.sql";
    }

    /**
     * Returns the vendor specific type number for the data type.
     * <p>
     *  返回数据类型的供应商特定类型编号。
     * 
     * 
     * @return  An Integer representing the data type. For {@code JDBCType},
     * the value will be the same value as in {@code Types} for the data type.
     */
    public Integer getVendorTypeNumber() {
        return type;
    }
    /**
     * Returns the {@code JDBCType} that corresponds to the specified
     * {@code Types} value
     * <p>
     *  返回与指定的{@code Types}值对应的{@code JDBCType}
     * 
     * @param type {@code Types} value
     * @return The {@code JDBCType} constant
     * @throws IllegalArgumentException if this enum type has no constant with
     * the specified {@code Types} value
     * @see Types
     */
    public static JDBCType valueOf(int type) {
        for( JDBCType sqlType : JDBCType.class.getEnumConstants()) {
            if(type == sqlType.type)
                return sqlType;
        }
        throw new IllegalArgumentException("Type:" + type + " is not a valid "
                + "Types.java value.");
    }
}
