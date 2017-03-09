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
import javax.naming.*;
import java.io.*;
import java.math.*;
import org.xml.sax.*;

/**
 * The standard interface that all implementations of a {@code WebRowSet}
 * must implement.
 *
 * <h3>1.0 Overview</h3>
 * The {@code WebRowSetImpl} provides the standard
 * reference implementation, which may be extended if required.
 * <P>
 * The standard WebRowSet XML Schema definition is available at the following
 * URI:
 * <ul>
 * <li>
 * <a href="http://java.sun.com/xml/ns/jdbc/webrowset.xsd">http://java.sun.com/xml/ns/jdbc/webrowset.xsd</a>
 * </li>
 * </ul>
 * It describes the standard XML document format required when describing a
 * {@code RowSet} object in XML and must be used be all standard implementations
 * of the {@code WebRowSet} interface to ensure interoperability. In addition,
 * the {@code WebRowSet} schema uses specific SQL/XML Schema annotations,
 * thus ensuring greater cross
 * platform inter-operability. This is an effort currently under way at the ISO
 * organization. The SQL/XML definition is available at the following URI:
 * <ul>
 * <li>
 * <a href="http://standards.iso.org/iso/9075/2002/12/sqlxml">http://standards.iso.org/iso/9075/2002/12/sqlxml</a>
 * </li>
 * </ul>
 * The schema definition describes the internal data of a {@code RowSet} object
 * in three distinct areas:
 * <UL>
 * <li>properties - These properties describe the standard synchronization
 * provider properties in addition to the more general {@code RowSet} properties.
 * </li>
 * <li>metadata - This describes the metadata associated with the tabular structure governed by a
 * {@code WebRowSet} object. The metadata described is closely aligned with the
 * metadata accessible in the underlying {@code java.sql.ResultSet} interface.
 * </li>
 * <li>data - This describes the original data (the state of data since the
 * last population
 * or last synchronization of the {@code WebRowSet} object) and the current
 * data. By keeping track of the delta between the original data and the current data,
 * a {@code WebRowSet} maintains the ability to synchronize changes
 * in its data back to the originating data source.
 * </li>
 * </ul>
 *
 * <h3>2.0 WebRowSet States</h3>
 * The following sections demonstrates how a {@code WebRowSet} implementation
 * should use the XML Schema to describe update, insert, and delete operations
 * and to describe the state of a {@code WebRowSet} object in XML.
 *
 * <h4>2.1 State 1 - Outputting a {@code WebRowSet} Object to XML</h4>
 * In this example, a {@code WebRowSet} object is created and populated with a simple 2 column,
 * 5 row table from a data source. Having the 5 rows in a {@code WebRowSet} object
 * makes it possible to describe them in XML. The
 * metadata describing the various standard JavaBeans properties as defined
 * in the RowSet interface plus the standard properties defined in
 * the {@code CachedRowSet}&trade; interface
 * provide key details that describe WebRowSet
 * properties. Outputting the WebRowSet object to XML using the standard
 * {@code writeXml} methods describes the internal properties as follows:
 * <PRE>
 * {@code
 * <properties>
 *       <command>select co1, col2 from test_table</command>
 *      <concurrency>1</concurrency>
 *      <datasource/>
 *      <escape-processing>true</escape-processing>
 *      <fetch-direction>0</fetch-direction>
 *      <fetch-size>0</fetch-size>
 *      <isolation-level>1</isolation-level>
 *      <key-columns/>
 *      <map/>
 *      <max-field-size>0</max-field-size>
 *      <max-rows>0</max-rows>
 *      <query-timeout>0</query-timeout>
 *      <read-only>false</read-only>
 *      <rowset-type>TRANSACTION_READ_UNCOMMITED</rowset-type>
 *      <show-deleted>false</show-deleted>
 *      <table-name/>
 *      <url>jdbc:thin:oracle</url>
 *      <sync-provider>
 *              <sync-provider-name>.com.rowset.provider.RIOptimisticProvider</sync-provider-name>
 *              <sync-provider-vendor>Oracle Corporation</sync-provider-vendor>
 *              <sync-provider-version>1.0</sync-provider-name>
 *              <sync-provider-grade>LOW</sync-provider-grade>
 *              <data-source-lock>NONE</data-source-lock>
 *      </sync-provider>
 * </properties>
 * } </PRE>
 * The meta-data describing the make up of the WebRowSet is described
 * in XML as detailed below. Note both columns are described between the
 * {@code column-definition} tags.
 * <PRE>
 * {@code
 * <metadata>
 *      <column-count>2</column-count>
 *      <column-definition>
 *              <column-index>1</column-index>
 *              <auto-increment>false</auto-increment>
 *              <case-sensitive>true</case-sensitive>
 *              <currency>false</currency>
 *              <nullable>1</nullable>
 *              <signed>false</signed>
 *              <searchable>true</searchable>
 *              <column-display-size>10</column-display-size>
 *              <column-label>COL1</column-label>
 *              <column-name>COL1</column-name>
 *              <schema-name/>
 *              <column-precision>10</column-precision>
 *              <column-scale>0</column-scale>
 *              <table-name/>
 *              <catalog-name/>
 *              <column-type>1</column-type>
 *              <column-type-name>CHAR</column-type-name>
 *      </column-definition>
 *      <column-definition>
 *              <column-index>2</column-index>
 *              <auto-increment>false</auto-increment>
 *              <case-sensitive>false</case-sensitive>
 *              <currency>false</currency>
 *              <nullable>1</nullable>
 *              <signed>true</signed>
 *              <searchable>true</searchable>
 *              <column-display-size>39</column-display-size>
 *              <column-label>COL2</column-label>
 *              <column-name>COL2</column-name>
 *              <schema-name/>
 *              <column-precision>38</column-precision>
 *              <column-scale>0</column-scale>
 *              <table-name/>
 *              <catalog-name/>
 *              <column-type>3</column-type>
 *              <column-type-name>NUMBER</column-type-name>
 *      </column-definition>
 * </metadata>
 * }</PRE>
 * Having detailed how the properties and metadata are described, the following details
 * how the contents of a {@code WebRowSet} object is described in XML. Note, that
 * this describes a {@code WebRowSet} object that has not undergone any
 * modifications since its instantiation.
 * A {@code currentRow} tag is mapped to each row of the table structure that the
 * {@code WebRowSet} object provides. A {@code columnValue} tag may contain
 * either the {@code stringData} or {@code binaryData} tag, according to
 * the SQL type that
 * the XML value is mapping back to. The {@code binaryData} tag contains data in the
 * Base64 encoding and is typically used for {@code BLOB} and {@code CLOB} type data.
 * <PRE>
 * {@code
 * <data>
 *      <currentRow>
 *              <columnValue>
 *                      firstrow
 *              </columnValue>
 *              <columnValue>
 *                      1
 *              </columnValue>
 *      </currentRow>
 *      <currentRow>
 *              <columnValue>
 *                      secondrow
 *              </columnValue>
 *              <columnValue>
 *                      2
 *              </columnValue>
 *      </currentRow>
 *      <currentRow>
 *              <columnValue>
 *                      thirdrow
 *              </columnValue>
 *              <columnValue>
 *                      3
 *              </columnValue>
 *      </currentRow>
 *      <currentRow>
 *              <columnValue>
 *                      fourthrow
 *              </columnValue>
 *              <columnValue>
 *                      4
 *              </columnValue>
 *      </currentRow>
 * </data>
 * }</PRE>
 * <h4>2.2 State 2 - Deleting a Row</h4>
 * Deleting a row in a {@code WebRowSet} object involves simply moving to the row
 * to be deleted and then calling the method {@code deleteRow}, as in any other
 * {@code RowSet} object.  The following
 * two lines of code, in which <i>wrs</i> is a {@code WebRowSet} object, delete
 * the third row.
 * <PRE>
 *     wrs.absolute(3);
 *     wrs.deleteRow();
 * </PRE>
 * The XML description shows the third row is marked as a {@code deleteRow},
 *  which eliminates the third row in the {@code WebRowSet} object.
 * <PRE>
 * {@code
 * <data>
 *      <currentRow>
 *              <columnValue>
 *                      firstrow
 *              </columnValue>
 *              <columnValue>
 *                      1
 *              </columnValue>
 *      </currentRow>
 *      <currentRow>
 *              <columnValue>
 *                      secondrow
 *              </columnValue>
 *              <columnValue>
 *                      2
 *              </columnValue>
 *      </currentRow>
 *      <deleteRow>
 *              <columnValue>
 *                      thirdrow
 *              </columnValue>
 *              <columnValue>
 *                      3
 *              </columnValue>
 *      </deleteRow>
 *      <currentRow>
 *              <columnValue>
 *                      fourthrow
 *              </columnValue>
 *              <columnValue>
 *                      4
 *              </columnValue>
 *      </currentRow>
 * </data>
 *} </PRE>
 * <h4>2.3 State 3 - Inserting a Row</h4>
 * A {@code WebRowSet} object can insert a new row by moving to the insert row,
 * calling the appropriate updater methods for each column in the row, and then
 * calling the method {@code insertRow}.
 * <PRE>
 * {@code
 * wrs.moveToInsertRow();
 * wrs.updateString(1, "fifththrow");
 * wrs.updateString(2, "5");
 * wrs.insertRow();
 * }</PRE>
 * The following code fragment changes the second column value in the row just inserted.
 * Note that this code applies when new rows are inserted right after the current row,
 * which is why the method {@code next} moves the cursor to the correct row.
 * Calling the method {@code acceptChanges} writes the change to the data source.
 *
 * <PRE>
 * {@code wrs.moveToCurrentRow();
 * wrs.next();
 * wrs.updateString(2, "V");
 * wrs.acceptChanges();
 * }</PRE>
 * Describing this in XML demonstrates where the Java code inserts a new row and then
 * performs an update on the newly inserted row on an individual field.
 * <PRE>
 * {@code
 * <data>
 *      <currentRow>
 *              <columnValue>
 *                      firstrow
 *              </columnValue>
 *              <columnValue>
 *                      1
 *              </columnValue>
 *      </currentRow>
 *      <currentRow>
 *              <columnValue>
 *                      secondrow
 *              </columnValue>
 *              <columnValue>
 *                      2
 *              </columnValue>
 *      </currentRow>
 *      <currentRow>
 *              <columnValue>
 *                      newthirdrow
 *              </columnValue>
 *              <columnValue>
 *                      III
 *              </columnValue>
 *      </currentRow>
 *      <insertRow>
 *              <columnValue>
 *                      fifthrow
 *              </columnValue>
 *              <columnValue>
 *                      5
 *              </columnValue>
 *              <updateValue>
 *                      V
 *              </updateValue>
 *      </insertRow>
 *      <currentRow>
 *              <columnValue>
 *                      fourthrow
 *              </columnValue>
 *              <columnValue>
 *                      4
 *              </columnValue>
 *      </currentRow>
 * </date>
 *} </PRE>
 * <h4>2.4 State 4 - Modifying a Row</h4>
 * Modifying a row produces specific XML that records both the new value and the
 * value that was replaced.  The value that was replaced becomes the original value,
 * and the new value becomes the current value. The following
 * code moves the cursor to a specific row, performs some modifications, and updates
 * the row when complete.
 * <PRE>
 *{@code
 * wrs.absolute(5);
 * wrs.updateString(1, "new4thRow");
 * wrs.updateString(2, "IV");
 * wrs.updateRow();
 * }</PRE>
 * In XML, this is described by the {@code modifyRow} tag. Both the original and new
 * values are contained within the tag for original row tracking purposes.
 * <PRE>
 * {@code
 * <data>
 *      <currentRow>
 *              <columnValue>
 *                      firstrow
 *              </columnValue>
 *              <columnValue>
 *                      1
 *              </columnValue>
 *      </currentRow>
 *      <currentRow>
 *              <columnValue>
 *                      secondrow
 *              </columnValue>
 *              <columnValue>
 *                      2
 *              </columnValue>
 *      </currentRow>
 *      <currentRow>
 *              <columnValue>
 *                      newthirdrow
 *              </columnValue>
 *              <columnValue>
 *                      III
 *              </columnValue>
 *      </currentRow>
 *      <currentRow>
 *              <columnValue>
 *                      fifthrow
 *              </columnValue>
 *              <columnValue>
 *                      5
 *              </columnValue>
 *      </currentRow>
 *      <modifyRow>
 *              <columnValue>
 *                      fourthrow
 *              </columnValue>
 *              <updateValue>
 *                      new4thRow
 *              </updateValue>
 *              <columnValue>
 *                      4
 *              </columnValue>
 *              <updateValue>
 *                      IV
 *              </updateValue>
 *      </modifyRow>
 * </data>
 * }</PRE>
 *
 * <p>
 *  {@code WebRowSet}的所有实现必须实现的标准接口。
 * 
 *  <h3> 1.0概述</h3> {@code WebRowSetImpl}提供了标准参考实现,如果需要可以扩展。
 * <P>
 *  标准WebRowSet XML模式定义在以下URI处可用：
 * <ul>
 * <li>
 *  <a href="http://java.sun.com/xml/ns/jdbc/webrowset.xsd"> http://java.sun.com/xml/ns/jdbc/webrowset.x
 * sd </a>。
 * </li>
 * </ul>
 *  它描述了在用XML描述{@code RowSet}对象时所需的标准XML文档格式,并且必须使用{@code WebRowSet}接口的所有标准实现来确保互操作性。
 * 此外,{@code WebRowSet}模式使用特定的SQL / XML模式注释,从而确保更好的跨平台互操作性。这是ISO组织目前正在进行的努力。 SQL / XML定义可从以下URI获得：。
 * <ul>
 * <li>
 *  <a href="http://standards.iso.org/iso/9075/2002/12/sqlxml"> http://standards.iso.org/iso/9075/2002/1
 * 2/sqlxml </a>。
 * </li>
 * </ul>
 *  模式定义描述了{@code RowSet}对象在三个不同区域的内部数据：
 * <UL>
 *  <li> properties  - 除了更常用的{@code RowSet}属性之外,这些属性还描述标准同步提供程序属性。
 * </li>
 * <li> metadata  - 描述与由{@code WebRowSet}对象管理的表格结构相关联的元数据。
 * 所描述的元数据与在底层{@code java.sql.ResultSet}接口中可访问的元数据紧密一致。
 * </li>
 *  <li> data  - 描述原始数据(自{@code WebRowSet}对象的上次填充或上次同步后的数据状态)和当前数据。
 * 通过跟踪原始数据和当前数据之间的增量,{@code WebRowSet}维护将其数据中的更改同步回原始数据源的能力。
 * </li>
 * </ul>
 * 
 *  <h3> 2.0 WebRowSet States </h3>以下部分演示了{@code WebRowSet}实现应如何使用XML模式来描述更新,插入和删除操作,并描述{@code WebRowSet}
 * 对象的状态XML。
 * 
 * <h4> 2.1状态1  - 将{@code WebRowSet}对象输出到XML </h4>在此示例中,将创建一个{@code WebRowSet}对象,并使用来自数据源的简单的2列,5行表格填充。
 * 在{@code WebRowSet}对象中有5行可以用XML来描述它们。
 * 描述在RowSet接口中定义的各种标准JavaBeans属性的元数据加上在{@code CachedRowSet}&trade中定义的标准属性;接口提供描述WebRowSet属性的关键详细信息。
 * 使用标准{@code writeXml}方法将WebRowSet对象输出为XML,将描述内部属性如下：。
 * <PRE>
 *  {@码
 * <properties>
 *  <command>从test_table </command> <concurrency> 1 </concurrency>中选择co1,col2
 * <datasource/>
 *  <escape-processing> true </escape-processing> <fetch-direction> 0 </fetch-direction> <fetch-size>
 * <key-columns/>
 * <map/>
 *  <max-field-size> 0 </query-timeout> 0 </max-field-size> <max-rows>只有> <rowset-type> TRANSACTION_READ
 * _UNCOMMITED </rowset-type> <show-deleted> false </show-deleted>。
 * <table-name/>
 *  <url> jdbc：thin：oracle </url>
 * <sync-provider>
 *  <sync-provider-name> .com.rowset.provider.RIOptimisticProvider </sync-provider-name> <sync-provider-vendor>
 *  Oracle Corporation </sync-provider-vendor> <sync-provider-version> 1.0 < sync-provider-name> <sync-provider-grade>
 *  LOW </sync-provider-grade> <data-source-lock>。
 * </sync-provider>
 * </properties>
 * } </PRE>描述WebRowSet组成的元数据在XML中描述,如下所述。注意,两个列都在{@code column-definition}标签之间进行描述。
 * <PRE>
 *  {@码
 * <metadata>
 *  <column-count> 2 </column-count>
 * <column-definition>
 *  <column-index> 1 </column-index> <auto-increment> false </auto-increment> <case- sensitive> true </case-sensitive>
 *  <currency> nullable> <signed> false </signed> <searchable> true </searchable> <column-display-size> 
 * 10 </column-display-size> <column-label> COL1 </column-label> <column-name > COL1 </column-name>。
 * <schema-name/>
 *  <column-precision> 10 </column-precision> <column-scale> 0 </column-scale>
 * <table-name/>
 * <catalog-name/>
 *  <column-type> 1 </column-type> <column-type-name> CHAR </column-type-name>
 * </column-definition>
 * <column-definition>
 *  <column-index> 2 </column-index> <auto-increment> false </auto-increment> <case- sensitive> false </case-sensitive>
 *  <currency> false </currency> <nullable> <column-display> <column-label> <column-display> <column-label>
 *  </columnable> <column-label> > COL2 </column-name>。
 * <schema-name/>
 *  <column-precision> 38 </column-precision> <column-scale> 0 </column-scale>
 * <table-name/>
 * <catalog-name/>
 *  <column-type> 3 </column-type> <column-type-name> NUMBER </column-type-name>
 * </column-definition>
 * </metadata>
 * } </PRE>下面详细介绍了如何描述属性和元数据,下面详细介绍了如何在XML中描述{@code WebRowSet}对象的内容。
 * 注意,这描述了一个{@code WebRowSet}对象,因为它的实例化没有经历任何修改。 {@code currentRow}标签映射到{@code WebRowSet}对象提供的表结构的每一行。
 * 根据XML值映射回的SQL类型,{@code columnValue}标记可能包含{@code stringData}或{@code binaryData}标记。
 *  {@code binaryData}标记包含Base64编码中的数据,通常用于{@code BLOB}和{@code CLOB}类型数据。
 * <PRE>
 *  {@码
 * <data>
 * <currentRow>
 * <columnValue>
 *  第一排
 * </columnValue>
 * <columnValue>
 *  1
 * </columnValue>
 * </currentRow>
 * <currentRow>
 * <columnValue>
 *  第二行
 * </columnValue>
 * <columnValue>
 *  2
 * </columnValue>
 * </currentRow>
 * <currentRow>
 * <columnValue>
 *  第三行
 * </columnValue>
 * <columnValue>
 *  3
 * </columnValue>
 * </currentRow>
 * <currentRow>
 * <columnValue>
 *  第四行
 * </columnValue>
 * <columnValue>
 *  4
 * </columnValue>
 * </currentRow>
 * </data>
 *  } </PRE> <h4> 2.2状态2  - 删除行</h4>删除{@code WebRowSet}对象中的行包括简单地移动到要删除的行,然后调用{@code deleteRow}与任何其他{@code RowSet}
 * 对象一样。
 * 以下两行代码,其中<i> wrs </i>是一个{@code WebRowSet}对象,删除第三行。
 * <PRE>
 *  wrs.absolute(3); wrs.deleteRow();
 * </PRE>
 *  XML描述显示第三行标记为{@code deleteRow},这消除了{@code WebRowSet}对象中的第三行。
 * <PRE>
 *  {@码
 * <data>
 * <currentRow>
 * <columnValue>
 *  第一排
 * </columnValue>
 * <columnValue>
 *  1
 * </columnValue>
 * </currentRow>
 * <currentRow>
 * <columnValue>
 *  第二行
 * </columnValue>
 * <columnValue>
 *  2
 * </columnValue>
 * </currentRow>
 * <deleteRow>
 * <columnValue>
 *  第三行
 * </columnValue>
 * <columnValue>
 *  3
 * </columnValue>
 * 
 * @see javax.sql.rowset.JdbcRowSet
 * @see javax.sql.rowset.CachedRowSet
 * @see javax.sql.rowset.FilteredRowSet
 * @see javax.sql.rowset.JoinRowSet
 */

public interface WebRowSet extends CachedRowSet {

   /**
    * Reads a {@code WebRowSet} object in its XML format from the given
    * {@code Reader} object.
    *
    * <p>
    * </deleteRow>
    * <currentRow>
    * <columnValue>
    *  第四行
    * </columnValue>
    * <columnValue>
    *  4
    * </columnValue>
    * </currentRow>
    * </data>
    * </PRE>
    * <h4> 2.3状态3  - 插入行</h4>通过移动到插入行,{@code WebRowSet}对象可以插入新行,为行中的每个列调用相应的updater方法,然后调用方法{@code insertRow}
    * 。
    * <PRE>
    *  {@code wrs.moveToInsertRow(); wrs.updateString(1,"fifththrow"); wrs.updateString(2,"5"); wrs.insertRow(); }
    *  </PRE>以下代码片段更改刚插入的行中的第二列值。
    * 注意,当在当前行之后插入新行时,此代码适用,这就是为什么{@code next}方法将光标移动到正确的行。调用方法{@code acceptChanges}将更改写入数据源。
    * 
    * <PRE>
    *  {@code wrs.moveToCurrentRow(); wrs.next(); wrs.updateString(2,"V"); wrs.acceptChanges(); } </PRE>在XM
    * L中描述这一点,演示了Java代码插入新行的位置,然后对单个字段上新插入的行执行更新。
    * <PRE>
    *  {@码
    * <data>
    * <currentRow>
    * <columnValue>
    *  第一排
    * </columnValue>
    * <columnValue>
    *  1
    * </columnValue>
    * </currentRow>
    * <currentRow>
    * <columnValue>
    *  第二行
    * </columnValue>
    * <columnValue>
    *  2
    * </columnValue>
    * </currentRow>
    * <currentRow>
    * <columnValue>
    *  newthirdrow
    * </columnValue>
    * <columnValue>
    *  III
    * </columnValue>
    * </currentRow>
    * <insertRow>
    * <columnValue>
    *  第五行
    * </columnValue>
    * <columnValue>
    *  5
    * </columnValue>
    * <updateValue>
    *  V
    * </updateValue>
    * </insertRow>
    * <currentRow>
    * <columnValue>
    *  第四行
    * </columnValue>
    * <columnValue>
    *  4
    * </columnValue>
    * </currentRow>
    * </date>
    * </PRE>
    *  <h4> 2.4状态4  - 修改行</h4>修改行会生成记录新值和替换值的特定XML。替换的值将成为原始值,新值将成为当前值。以下代码将光标移动到特定行,执行一些修改,并在完成时更新行。
    * <PRE>
    * @code wrs.absolute(5); wrs.updateString(1,"new4thRow"); wrs.updateString(2,"IV"); wrs.updateRow(); } 
    * </PRE>在XML中,这由{@code modifyRow}标记描述。
    * 原始行和新值都包含在标记中,用于原始行跟踪目的。
    * <PRE>
    *  {@码
    * <data>
    * 
    * @param reader the {@code java.io.Reader} stream from which this
    *        {@code WebRowSet} object will be populated

    * @throws SQLException if a database access error occurs
    */
    public void readXml(java.io.Reader reader) throws SQLException;

    /**
     * Reads a stream based XML input to populate this {@code WebRowSet}
     * object.
     *
     * <p>
     * <currentRow>
     * <columnValue>
     *  第一排
     * </columnValue>
     * <columnValue>
     *  1
     * </columnValue>
     * </currentRow>
     * <currentRow>
     * <columnValue>
     *  第二行
     * </columnValue>
     * <columnValue>
     *  2
     * </columnValue>
     * </currentRow>
     * <currentRow>
     * <columnValue>
     *  newthirdrow
     * </columnValue>
     * <columnValue>
     *  III
     * </columnValue>
     * </currentRow>
     * <currentRow>
     * <columnValue>
     *  第五行
     * </columnValue>
     * <columnValue>
     *  5
     * </columnValue>
     * </currentRow>
     * <modifyRow>
     * <columnValue>
     *  第四行
     * </columnValue>
     * <updateValue>
     *  new4thRow
     * </updateValue>
     * 
     * @param iStream the {@code java.io.InputStream} from which this
     *        {@code WebRowSet} object will be populated
     * @throws SQLException if a data source access error occurs
     * @throws IOException if an IO exception occurs
     */
    public void readXml(java.io.InputStream iStream) throws SQLException, IOException;

   /**
    * Populates this {@code WebRowSet} object with
    * the contents of the given {@code ResultSet} object and writes its
    * data, properties, and metadata
    * to the given {@code Writer} object in XML format.
    * <p>
    * NOTE: The {@code WebRowSet} cursor may be moved to write out the
    * contents to the XML data source. If implemented in this way, the cursor <b>must</b>
    * be returned to its position just prior to the {@code writeXml()} call.
    *
    * <p>
    * <columnValue>
    *  4
    * </columnValue>
    * <updateValue>
    *  IV
    * </updateValue>
    * </modifyRow>
    * </data>
    *  } </PRE>
    * 
    * 
    * @param rs the {@code ResultSet} object with which to populate this
    *        {@code WebRowSet} object
    * @param writer the {@code java.io.Writer} object to write to.
    * @throws SQLException if an error occurs writing out the rowset
    *          contents in XML format
    */
    public void writeXml(ResultSet rs, java.io.Writer writer) throws SQLException;

   /**
    * Populates this {@code WebRowSet} object with
    * the contents of the given {@code ResultSet} object and writes its
    * data, properties, and metadata
    * to the given {@code OutputStream} object in XML format.
    * <p>
    * NOTE: The {@code WebRowSet} cursor may be moved to write out the
    * contents to the XML data source. If implemented in this way, the cursor <b>must</b>
    * be returned to its position just prior to the {@code writeXml()} call.
    *
    * <p>
    *  从给定的{@code Reader}对象中读取其XML格式的{@code WebRowSet}对象。
    * 
    * 
    * @param rs the {@code ResultSet} object with which to populate this
    *        {@code WebRowSet} object
    * @param oStream the {@code java.io.OutputStream} to write to
    * @throws SQLException if a data source access error occurs
    * @throws IOException if a IO exception occurs
    */
    public void writeXml(ResultSet rs, java.io.OutputStream oStream) throws SQLException, IOException;

   /**
    * Writes the data, properties, and metadata for this {@code WebRowSet} object
    * to the given {@code Writer} object in XML format.
    *
    * <p>
    *  读取基于流的XML输入以填充此{@code WebRowSet}对象。
    * 
    * 
    * @param writer the {@code java.io.Writer} stream to write to
    * @throws SQLException if an error occurs writing out the rowset
    *          contents to XML
    */
    public void writeXml(java.io.Writer writer) throws SQLException;

    /**
     * Writes the data, properties, and metadata for this {@code WebRowSet} object
     * to the given {@code OutputStream} object in XML format.
     *
     * <p>
     *  使用给定的{@code ResultSet}对象的内容填充此{@code WebRowSet}对象,并将其数据,属性和元数据以XML格式写入给定的{@code Writer}对象。
     * <p>
     *  注意：可以移动{@code WebRowSet}游标以将内容写出到XML数据源。如果以这种方式实现,光标<b>必须</b>返回到{@code writeXml()}调用之前的位置。
     * 
     * 
     * @param oStream the {@code java.io.OutputStream} stream to write to
     * @throws SQLException if a data source access error occurs
     * @throws IOException if a IO exception occurs
     */
    public void writeXml(java.io.OutputStream oStream) throws SQLException, IOException;

    /**
     * The public identifier for the XML Schema definition that defines the XML
     * tags and their valid values for a {@code WebRowSet} implementation.
     * <p>
     *  使用给定的{@code ResultSet}对象的内容填充此{@code WebRowSet}对象,并将其数据,属性和元数据以XML格式写入给定的{@code OutputStream}对象。
     * <p>
     *  注意：可以移动{@code WebRowSet}游标以将内容写出到XML数据源。如果以这种方式实现,光标<b>必须</b>返回到{@code writeXml()}调用之前的位置。
     * 
     */
    public static String PUBLIC_XML_SCHEMA =
        "--//Oracle Corporation//XSD Schema//EN";

    /**
     * The URL for the XML Schema definition file that defines the XML tags and
     * their valid values for a {@code WebRowSet} implementation.
     * <p>
     *  将此{@code WebRowSet}对象的数据,属性和元数据以XML格式写入给定的{@code Writer}对象。
     * 
     */
    public static String SCHEMA_SYSTEM_ID = "http://java.sun.com/xml/ns/jdbc/webrowset.xsd";
}
