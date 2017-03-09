/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2006, Oracle and/or its affiliates. All rights reserved.
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
 *
 * The representation (mapping) in the Java programming language of an SQL ROWID
 * value. An SQL ROWID is a built-in type, a value of which can be thought of as
 * an address  for its identified row in a database table. Whether that address
 * is logical or, in any  respects, physical is determined by its originating data
 * source.
 * <p>
 * Methods in the interfaces <code>ResultSet</code>, <code>CallableStatement</code>,
 * and <code>PreparedStatement</code>, such as <code>getRowId</code> and <code>setRowId</code>
 * allow a programmer to access a SQL <code>ROWID</code>  value. The <code>RowId</code>
 * interface provides a method
 * for representing the value of the <code>ROWID</code> as a byte array or as a
 * <code>String</code>.
 * <p>
 * The method <code>getRowIdLifetime</code> in the interface <code>DatabaseMetaData</code>,
 * can be used
 * to determine if a <code>RowId</code> object remains valid for the duration of the transaction in
 * which  the <code>RowId</code> was created, the duration of the session in which
 * the <code>RowId</code> was created,
 * or, effectively, for as long as its identified row is not deleted. In addition
 * to specifying the duration of its valid lifetime outside its originating data
 * source, <code>getRowIdLifetime</code> specifies the duration of a <code>ROWID</code>
 * value's valid lifetime
 * within its originating data source. In this, it differs from a large object,
 * because there is no limit on the valid lifetime of a large  object within its
 * originating data source.
 * <p>
 * All methods on the <code>RowId</code> interface must be fully implemented if the
 * JDBC driver supports the data type.
 *
 * <p>
 *  在Java编程语言中表示(映射)SQL ROWID值。 SQL ROWID是一种内置类型,其值可以被认为是数据库表中其标识行的地址。该地址是逻辑的还是在任何方面都是物理的由其始发数据源确定。
 * <p>
 *  接口<code> ResultSet </code>,<code> CallableStatement </code>和<code> PreparedStatement </code>中的方法,如<code>
 *  getRowId </code>和<code> setRowId </code >允许程序员访问SQL <code> ROWID </code>值。
 *  <code> RowId </code>接口提供了一种将<code> ROWID </code>的值表示为字节数组或<code> String </code>的方法。
 * <p>
 * 接口<code> DatabaseMetaData </code>中的<code> getRowIdLifetime </code>方法可用于确定<code> RowId </code>对象在事务持续期
 * 间是否保持有效,代码> RowId </code>,创建<code> RowId </code>的会话持续时间,或者有效地,只要其标识的行不被删除。
 * 除了指定其起始数据源之外的有效生命周期的持续时间,<code> getRowIdLifetime </code>指定<code> ROWID </code>值在其原始数据源内的有效生命周期的持续时间。
 * 在此,它与大对象不同,因为对其原始数据源中的大对象的有效生命周期没有限制。
 * <p>
 *  如果JDBC驱动程序支持数据类型,则必须完全实现<code> RowId </code>接口上的所有方法。
 * 
 * 
 * @see java.sql.DatabaseMetaData
 * @since 1.6
 */

public interface RowId {
    /**
     * Compares this <code>RowId</code> to the specified object. The result is
     * <code>true</code> if and only if the argument is not null and is a RowId
     * object that represents the same ROWID as  this object.
     * <p>
     * It is important
     * to consider both the origin and the valid lifetime of a <code>RowId</code>
     * when comparing it to another <code>RowId</code>. If both are valid, and
     * both are from the same table on the same data source, then if they are equal
     * they identify
     * the same row; if one or more is no longer guaranteed to be valid, or if
     * they originate from different data sources, or different tables on the
     * same data source, they  may be equal but still
     * not identify the same row.
     *
     * <p>
     *  将此<code> RowId </code>与指定的对象进行比较。如果且仅当参数不为null并且是代表与此对象相同的ROWID的RowId对象,结果是<code> true </code>。
     * <p>
     * 当与另一个<code> RowId </code>进行比较时,考虑<code> RowId </code>的起源和有效生命周期是很重要的。
     * 如果两者都是有效的,并且两者都来自同一数据源上的同一个表,那么如果它们相等,则它们标识相同的行;如果一个或多个不再保证是有效的,或者如果它们源自不同的数据源或相同数据源上的不同表,则它们可以相等但仍然不
     * 标识相同的行。
     * 当与另一个<code> RowId </code>进行比较时,考虑<code> RowId </code>的起源和有效生命周期是很重要的。
     * 
     * 
     * @param obj the <code>Object</code> to compare this <code>RowId</code> object
     *     against.
     * @return true if the <code>RowId</code>s are equal; false otherwise
     * @since 1.6
     */
    boolean equals(Object obj);

    /**
     * Returns an array of bytes representing the value of the SQL <code>ROWID</code>
     * designated by this <code>java.sql.RowId</code> object.
     *
     * <p>
     *  返回一个字节数组,表示由此<code> java.sql.RowId </code>对象指定的SQL <code> ROWID </code>的值。
     * 
     * 
     * @return an array of bytes, whose length is determined by the driver supplying
     *     the connection, representing the value of the ROWID designated by this
     *     java.sql.RowId object.
     */
     byte[] getBytes();

     /**
      * Returns a String representing the value of the SQL ROWID designated by this
      * <code>java.sql.RowId</code> object.
      * <p>
      *Like <code>java.sql.Date.toString()</code>
      * returns the contents of its DATE as the <code>String</code> "2004-03-17"
      * rather than as  DATE literal in SQL (which would have been the <code>String</code>
      * DATE "2004-03-17"), toString()
      * returns the contents of its ROWID in a form specific to the driver supplying
      * the connection, and possibly not as a <code>ROWID</code> literal.
      *
      * <p>
      *  返回一个字符串,表示由此<code> java.sql.RowId </code>对象指定的SQL ROWID的值。
      * <p>
      *  ike <code> java.sql.Date.toString()</code>返回其DATE的内容为<code> String </code>"2004-03-17",而不是SQL中的DATE文
      * 字已经是<code> String </code> DATE"2004-03-17"),toString()以特定于提供连接的驱动程序的形式返回其ROWID的内容,并且可能不作为<code> ROWID
      *  </code>文字。
      * 
      * @return a String whose format is determined by the driver supplying the
      *     connection, representing the value of the <code>ROWID</code> designated
      *     by this <code>java.sql.RowId</code>  object.
      */
     String toString();

     /**
      * Returns a hash code value of this <code>RowId</code> object.
      *
      * <p>
      * 
      * 
      * @return a hash code for the <code>RowId</code>
      */
     int hashCode();

}
