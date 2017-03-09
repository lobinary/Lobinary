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
 * An object that is used to identify a generic SQL type, called a JDBC type or
 * a vendor specific data type.
 *
 * <p>
 *  用于标识通用SQL类型(称为JDBC类型或供应商特定的数据类型)的对象。
 * 
 * 
 * @since 1.8
 */
public interface SQLType {

    /**
     * Returns the {@code SQLType} name that represents a SQL data type.
     *
     * <p>
     *  返回表示SQL数据类型的{@code SQLType}名称。
     * 
     * 
     * @return The name of this {@code SQLType}.
     */
    String getName();

    /**
     * Returns the name of the vendor that supports this data type. The value
     * returned typically is the package name for this vendor.
     *
     * <p>
     *  返回支持此数据类型的供应商的名称。返回的值通常是此供应商的包名称。
     * 
     * 
     * @return The name of the vendor for this data type
     */
    String getVendor();

    /**
     * Returns the vendor specific type number for the data type.
     *
     * <p>
     *  返回数据类型的供应商特定类型编号。
     * 
     * @return An Integer representing the vendor specific data type
     */
    Integer getVendorTypeNumber();
}
