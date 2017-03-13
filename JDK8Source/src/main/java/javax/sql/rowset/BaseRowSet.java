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
import java.util.*;
import java.io.*;
import java.math.*;
import java.io.Serializable;

import javax.sql.rowset.serial.*;

/**
 * An abstract class providing a <code>RowSet</code> object with its basic functionality.
 * The basic functions include having properties and sending event notifications,
 * which all JavaBeans&trade; components must implement.
 *
 * <h3>1.0 Overview</h3>
 * The <code>BaseRowSet</code> class provides the core functionality
 * for all <code>RowSet</code> implementations,
 * and all standard implementations <b>may</b> use this class in combination with
 * one or more <code>RowSet</code> interfaces in order to provide a standard
 * vendor-specific implementation.  To clarify, all implementations must implement
 * at least one of the <code>RowSet</code> interfaces (<code>JdbcRowSet</code>,
 * <code>CachedRowSet</code>, <code>JoinRowSet</code>, <code>FilteredRowSet</code>,
 * or <code>WebRowSet</code>). This means that any implementation that extends
 * the <code>BaseRowSet</code> class must also implement one of the <code>RowSet</code>
 * interfaces.
 * <p>
 * The <code>BaseRowSet</code> class provides the following:
 *
 * <UL>
 * <LI><b>Properties</b>
 *     <ul>
 *     <li>Fields for storing current properties
 *     <li>Methods for getting and setting properties
 *     </ul>
 *
 * <LI><b>Event notification</b>
 *
 * <LI><b>A complete set of setter methods</b> for setting the parameters in a
 *      <code>RowSet</code> object's command
 *
 * <LI> <b>Streams</b>
 *  <ul>
 *  <li>Fields for storing stream instances
 *  <li>Constants for indicating the type of a stream
 *  </ul>
 *  <p>
 * </UL>
 *
 * <h3>2.0 Setting Properties</h3>
 * All rowsets maintain a set of properties, which will usually be set using
 * a tool.  The number and kinds of properties a rowset has will vary,
 * depending on what the <code>RowSet</code> implementation does and how it gets
 * its data.  For example,
 * rowsets that get their data from a <code>ResultSet</code> object need to
 * set the properties that are required for making a database connection.
 * If a <code>RowSet</code> object uses the <code>DriverManager</code> facility to make a
 * connection, it needs to set a property for the JDBC URL that identifies the
 * appropriate driver, and it needs to set the properties that give the
 * user name and password.
 * If, on the other hand, the rowset uses a <code>DataSource</code> object
 * to make the connection, which is the preferred method, it does not need to
 * set the property for the JDBC URL.  Instead, it needs to set the property
 * for the logical name of the data source along with the properties for
 * the user name and password.
 * <P>
 * NOTE:  In order to use a <code>DataSource</code> object for making a
 * connection, the <code>DataSource</code> object must have been registered
 * with a naming service that uses the Java Naming and Directory
 * Interface&trade; (JNDI) API.  This registration
 * is usually done by a person acting in the capacity of a system administrator.
 *
 * <h3>3.0 Setting the Command and Its Parameters</h3>
 * When a rowset gets its data from a relational database, it executes a command (a query)
 * that produces a <code>ResultSet</code> object.  This query is the command that is set
 * for the <code>RowSet</code> object's command property.  The rowset populates itself with data by reading the
 * data from the <code>ResultSet</code> object into itself. If the query
 * contains placeholders for values to be set, the <code>BaseRowSet</code> setter methods
 * are used to set these values. All setter methods allow these values to be set
 * to <code>null</code> if required.
 * <P>
 * The following code fragment illustrates how the
 * <code>CachedRowSet</code>&trade;
 * object <code>crs</code> might have its command property set.  Note that if a
 * tool is used to set properties, this is the code that the tool would use.
 * <PRE>{@code
 *    crs.setCommand("SELECT FIRST_NAME, LAST_NAME, ADDRESS FROM CUSTOMERS" +
 *                   "WHERE CREDIT_LIMIT > ? AND REGION = ?");
 * }</PRE>
 * <P>
 * In this example, the values for <code>CREDIT_LIMIT</code> and
 * <code>REGION</code> are placeholder parameters, which are indicated with a
 * question mark (?).  The first question mark is placeholder parameter number
 * <code>1</code>, the second question mark is placeholder parameter number
 * <code>2</code>, and so on.  Any placeholder parameters must be set with
 * values before the query can be executed. To set these
 * placeholder parameters, the <code>BaseRowSet</code> class provides a set of setter
 * methods, similar to those provided by the <code>PreparedStatement</code>
 * interface, for setting values of each data type.  A <code>RowSet</code> object stores the
 * parameter values internally, and its <code>execute</code> method uses them internally
 * to set values for the placeholder parameters
 * before it sends the command to the DBMS to be executed.
 * <P>
 * The following code fragment demonstrates
 * setting the two parameters in the query from the previous example.
 * <PRE>{@code
 *    crs.setInt(1, 5000);
 *    crs.setString(2, "West");
 * }</PRE>
 * If the <code>execute</code> method is called at this point, the query
 * sent to the DBMS will be:
 * <PRE>{@code
 *    "SELECT FIRST_NAME, LAST_NAME, ADDRESS FROM CUSTOMERS" +
 *                   "WHERE CREDIT_LIMIT > 5000 AND REGION = 'West'"
 * }</PRE>
 * NOTE: Setting <code>Array</code>, <code>Clob</code>, <code>Blob</code> and
 * <code>Ref</code> objects as a command parameter, stores these values as
 * <code>SerialArray</code>, <code>SerialClob</code>, <code>SerialBlob</code>
 * and <code>SerialRef</code> objects respectively.
 *
 * <h3>4.0 Handling of Parameters Behind the Scenes</h3>
 *
 * NOTE: The <code>BaseRowSet</code> class provides two kinds of setter methods,
 * those that set properties and those that set placeholder parameters. The setter
 * methods discussed in this section are those that set placeholder parameters.
 * <P>
 * The placeholder parameters set with the <code>BaseRowSet</code> setter methods
 * are stored as objects in an internal <code>Hashtable</code> object.
 * Primitives are stored as their <code>Object</code> type. For example, <code>byte</code>
 * is stored as <code>Byte</code> object, and <code>int</code> is stored as
 * an <code>Integer</code> object.
 * When the method <code>execute</code> is called, the values in the
 * <code>Hashtable</code> object are substituted for the appropriate placeholder
 * parameters in the command.
 * <P>
 * A call to the method <code>getParams</code> returns the values stored in the
 * <code>Hashtable</code> object as an array of <code>Object</code> instances.
 * An element in this array may be a simple <code>Object</code> instance or an
 * array (which is a type of <code>Object</code>). The particular setter method used
 * determines whether an element in this array is an <code>Object</code> or an array.
 * <P>
 * The majority of methods for setting placeholder parameters take two parameters,
 *  with the first parameter
 * indicating which placeholder parameter is to be set, and the second parameter
 * giving the value to be set.  Methods such as <code>setInt</code>,
 * <code>setString</code>, <code>setBoolean</code>, and <code>setLong</code> fall into
 * this category.  After these methods have been called, a call to the method
 * <code>getParams</code> will return an array with the values that have been set. Each
 * element in the array is an <code>Object</code> instance representing the
 * values that have been set. The order of these values in the array is determined by the
 * <code>int</code> (the first parameter) passed to the setter method. The values in the
 * array are the values (the second parameter) passed to the setter method.
 * In other words, the first element in the array is the value
 * to be set for the first placeholder parameter in the <code>RowSet</code> object's
 * command. The second element is the value to
 * be set for the second placeholder parameter, and so on.
 * <P>
 * Several setter methods send the driver and DBMS information beyond the value to be set.
 * When the method <code>getParams</code> is called after one of these setter methods has
 * been used, the elements in the array will themselves be arrays to accommodate the
 * additional information. In this category, the method <code>setNull</code> is a special case
 * because one version takes only
 * two parameters (<code>setNull(int parameterIndex, int SqlType)</code>). Nevertheless,
 * it requires
 * an array to contain the information that will be passed to the driver and DBMS.  The first
 * element in this array is the value to be set, which is <code>null</code>, and the
 * second element is the <code>int</code> supplied for <i>sqlType</i>, which
 * indicates the type of SQL value that is being set to <code>null</code>. This information
 * is needed by some DBMSs and is therefore required in order to ensure that applications
 * are portable.
 * The other version is intended to be used when the value to be set to <code>null</code>
 * is a user-defined type. It takes three parameters
 * (<code>setNull(int parameterIndex, int sqlType, String typeName)</code>) and also
 * requires an array to contain the information to be passed to the driver and DBMS.
 * The first two elements in this array are the same as for the first version of
 * <code>setNull</code>.  The third element, <i>typeName</i>, gives the SQL name of
 * the user-defined type. As is true with the other setter methods, the number of the
 * placeholder parameter to be set is indicated by an element's position in the array
 * returned by <code>getParams</code>.  So, for example, if the parameter
 * supplied to <code>setNull</code> is <code>2</code>, the second element in the array
 * returned by <code>getParams</code> will be an array of two or three elements.
 * <P>
 * Some methods, such as <code>setObject</code> and <code>setDate</code> have versions
 * that take more than two parameters, with the extra parameters giving information
 * to the driver or the DBMS. For example, the methods <code>setDate</code>,
 * <code>setTime</code>, and <code>setTimestamp</code> can take a <code>Calendar</code>
 * object as their third parameter.  If the DBMS does not store time zone information,
 * the driver uses the <code>Calendar</code> object to construct the <code>Date</code>,
 * <code>Time</code>, or <code>Timestamp</code> object being set. As is true with other
 * methods that provide additional information, the element in the array returned
 * by <code>getParams</code> is an array instead of a simple <code>Object</code> instance.
 * <P>
 * The methods <code>setAsciiStream</code>, <code>setBinaryStream</code>,
 * <code>setCharacterStream</code>, and <code>setUnicodeStream</code> (which is
 * deprecated, so applications should use <code>getCharacterStream</code> instead)
 * take three parameters, so for them, the element in the array returned by
 * <code>getParams</code> is also an array.  What is different about these setter
 * methods is that in addition to the information provided by parameters, the array contains
 * one of the <code>BaseRowSet</code> constants indicating the type of stream being set.
* <p>
* NOTE: The method <code>getParams</code> is called internally by
* <code>RowSet</code> implementations extending this class; it is not normally called by an
* application programmer directly.
*
* <h3>5.0 Event Notification</h3>
* The <code>BaseRowSet</code> class provides the event notification
* mechanism for rowsets.  It contains the field
* <code>listeners</code>, methods for adding and removing listeners, and
* methods for notifying listeners of changes.
* <P>
* A listener is an object that has implemented the <code>RowSetListener</code> interface.
* If it has been added to a <code>RowSet</code> object's list of listeners, it will be notified
*  when an event occurs on that <code>RowSet</code> object.  Each listener's
* implementation of the <code>RowSetListener</code> methods defines what that object
* will do when it is notified that an event has occurred.
* <P>
* There are three possible events for a <code>RowSet</code> object:
* <OL>
* <LI>the cursor moves
* <LI>an individual row is changed (updated, deleted, or inserted)
* <LI>the contents of the entire <code>RowSet</code> object  are changed
* </OL>
* <P>
* The <code>BaseRowSet</code> method used for the notification indicates the
* type of event that has occurred.  For example, the method
* <code>notifyRowChanged</code> indicates that a row has been updated,
* deleted, or inserted.  Each of the notification methods creates a
* <code>RowSetEvent</code> object, which is supplied to the listener in order to
* identify the <code>RowSet</code> object on which the event occurred.
* What the listener does with this information, which may be nothing, depends on how it was
* implemented.
*
* <h3>6.0 Default Behavior</h3>
* A default <code>BaseRowSet</code> object is initialized with many starting values.
*
* The following is true of a default <code>RowSet</code> instance that extends
* the <code>BaseRowSet</code> class:
* <UL>
*   <LI>Has a scrollable cursor and does not show changes
*       made by others.
*   <LI>Is updatable.
*   <LI>Does not show rows that have been deleted.
*   <LI>Has no time limit for how long a driver may take to
*       execute the <code>RowSet</code> object's command.
*   <LI>Has no limit for the number of rows it may contain.
*   <LI>Has no limit for the number of bytes a column may contain. NOTE: This
*   limit applies only to columns that hold values of the
*   following types:  <code>BINARY</code>, <code>VARBINARY</code>,
*   <code>LONGVARBINARY</code>, <code>CHAR</code>, <code>VARCHAR</code>,
*   and <code>LONGVARCHAR</code>.
*   <LI>Will not see uncommitted data (make "dirty" reads).
*   <LI>Has escape processing turned on.
*   <LI>Has its connection's type map set to <code>null</code>.
*   <LI>Has an empty <code>Vector</code> object for storing the values set
*       for the placeholder parameters in the <code>RowSet</code> object's command.
* </UL>
* <p>
* If other values are desired, an application must set the property values
* explicitly. For example, the following line of code sets the maximum number
* of rows for the <code>CachedRowSet</code> object <i>crs</i> to 500.
* <PRE>
*    crs.setMaxRows(500);
* </PRE>
* Methods implemented in extensions of this <code>BaseRowSet</code> class <b>must</b> throw an
* <code>SQLException</code> object for any violation of the defined assertions.  Also, if the
* extending class overrides and reimplements any <code>BaseRowSet</code> method and encounters
* connectivity or underlying data source issues, that method <b>may</b> in addition throw an
* <code>SQLException</code> object for that reason.
* <p>
*  一个抽象类,提供一个具有基本功能的<code> RowSet </code>对象基本功能包括具有属性和发送事件通知,所有JavaBeans&组件必须实现
* 
* <h3> 10概述</b> <code> BaseRowSet </code>类为所有<code> RowSet </code>实现提供核心功能,所有标准实现</b>与一个或多个<code> RowSe
* t </code>接口结合以提供标准的供应商特定实现。
* 为了阐明,所有实现必须实现<code> RowSet </code>接口JdbcRowSet </code>,<code> CachedRowSet </code>,<code> JoinRowSet 
* </code>,<code> FilteredRowSet </code>或<code> WebRowSet </code>)这意味着任何实现扩展<code> BaseRowSet </code>类也必
* 须实现<code> RowSet </code>接口之一。
* <p>
*  <code> BaseRowSet </code>类提供以下内容：
* 
* <UL>
*  <LI> <b>属性</b>
* <ul>
* <li>用于存储当前属性的字段<li>获取和设置属性的方法
* </ul>
* 
*  <LI> <b>活动通知</b>
* 
*  <li> <b>一组完整的设置方法</b>用于设置<code> RowSet </code>对象的命令中的参数
* 
*  <LI> <b>流</b>
* <ul>
*  <li>用于存储流实例的字段<li>用于指示流类型的常量
* </ul>
* <p>
* </UL>
* 
* <h3> 20设置属性</h3>所有行集都保留一组属性,通常使用工具设置行集的属性数量和种类将有所不同,取决于<code> RowSet </code>实现以及如何获取其数据例如,从<code> Res
* ultSet </code>对象获取数据的行集需要设置进行数据库连接所需的属性如果<code> RowSet </code>对象使用<code> DriverManager </code>工具建立连接,
* 它需要为标识相应驱动程序的JDBC URL设置属性,并且需要设置提供用户名和密码的属性另一方面,如果行集使用<code> DataSource </code>对象来建立连接(这是首选方法),则它不需要为
* JDBC URL设置属性。
* 相反,它需要设置属性,以及数据源的逻辑名称以及用户名和密码的属性。
* <P>
* 注意：为了使用<code> DataSource </code>对象进行连接,<code> DataSource </code>对象必须已使用命名服务注册,该服务使用Java命名和目录接口& (JNDI
* )API此注册通常由以系统管理员身份操作的人员完成。
* 
* <h3> 30设置命令及其参数</h3>当行集从关系数据库获取其数据时,它会执行产生<code> ResultSet </code>对象的命令(查询)为<code> RowSet </code>对象的命
* 令属性设置行集通过从<code> ResultSet </code>对象中读取数据将数据填充到自身中如果查询包含要设置的值的占位符, <code> BaseRowSet </code> setter方法
* 用于设置这些值所有setter方法允许将这些值设置为<code> null </code>。
* <P>
* 下面的代码片段说明了如何<code> CachedRowSet </code>&trade;对象<code> crs </code>可能具有其命令属性set注意,如果使用工具设置属性,那么工具将使用<PRE>
* 代码{@ code crssetCommand("SELECT FIRST_NAME,LAST_NAME,ADDRESS FROM CUSTOMERS"+"WHERE CREDIT_LIMIT>?AND REGION =?"); }
*  </PRE>。
* <P>
* 在此示例中,<code> CREDIT_LIMIT </code>和<code> REGION </code>的值是占位符参数,用问号(?)表示。
* 第一个问号是占位符参数号<code> 1 </code>,第二个问号是占位符参数号<code> 2 </code>等等任何占位符参数必须在执行查询之前设置值。
* 要设置这些占位符参数,<code> BaseRowSet </code>类提供了一组setter方法,类似于由<code> PreparedStatement </code>接口提供的方法,用于设置每个
* 数据类型的值<code> RowSet </code>对象在内部存储参数值,其<code> execute </code>方法在将命令发送到要执行的DBMS之前,在内部使用它们设置占位符参数的值。
* 第一个问号是占位符参数号<code> 1 </code>,第二个问号是占位符参数号<code> 2 </code>等等任何占位符参数必须在执行查询之前设置值。
* <P>
* 下面的代码片段演示了在上一个示例<PRE> {@ code crssetInt(1,5000);}中查询的两个参数的设置。
*  crssetString(2,"West"); } </PRE>如果此时调用<code> execute </code>方法,则发送到DBMS的查询将是：<PRE> {@ code"SELECT FIRST_NAME,LAST_NAME,ADDRESS FROM CUSTOMERS"+"WHERE CREDIT_LIMIT> 5000 AND REGION ='West'"}
*  </PRE>注意：设置<code> Array </code>,<code> Clob </code> / code>对象作为命令参数,将这些值分别存储为<code> SerialArray </code>
* ,<code> SerialClob </code>,<code> SerialBlob </code>和<code> SerialRef </code>。
* 下面的代码片段演示了在上一个示例<PRE> {@ code crssetInt(1,5000);}中查询的两个参数的设置。
* 
*  <h3> 40处理场景后面的参数</h3>
* 
* 注意：<code> BaseRowSet </code>类提供了两种设置方法,即设置属性的方法和设置占位符参数的方法。本节讨论的设置器方法是设置占位符参数
* <P>
*  使用<code> BaseRowSet </code> setter方法设置的占位符参数作为对象存储在内部<code> Hashtable </code>对象中基元存储为<code> Object </code>
* 类型例如,代码>字节</code>存储为<code> Byte </code>对象,<code> int </code>存储为<code> Integer </code> / code>,那么<code>
*  Hashtable </code>对象中的值将替换为命令中的相应占位符参数。
* <P>
* 对方法<code> getParams </code>的调用将<code> Hashtable </code>对象中存储的值作为<code> Object </code>实例的数组返回。
* 此数组中的元素可能是简单的<code> Object </code>实例或数组(这是一种<code> Object </code>)所使用的特定setter方法决定了该数组中的一个元素是<code> O
* bject </code>数组。
* 对方法<code> getParams </code>的调用将<code> Hashtable </code>对象中存储的值作为<code> Object </code>实例的数组返回。
* <P>
* 大多数设置占位符参数的方法有两个参数,第一个参数指示要设置哪个占位符参数,第二个参数给出要设置的值。
* <code> setInt </code>,<code> setString </code>,<code> setBoolean </code>和<code> setLong </code>属于这个类别
* 调用这些方法后,调用<code> getParams </code>具有已设置的值的数组数组中的每个元素都是表示已设置的值的<code> Object </code>实例。
* 大多数设置占位符参数的方法有两个参数,第一个参数指示要设置哪个占位符参数,第二个参数给出要设置的值。
* 数组中这些值的顺序由<code> int <代码>(第一个参数)传递给setter方法数组中的值是传递给setter方法的值(第二个参数)。
* 换句话说,数组中的第一个元素是要为<code> RowSet </code>对象的第一个占位符参数设置的值命令第二个元素是要为第二个占位符参数设置的值,以此类推。
* <P>
* 几个setter方法发送驱动程序和DBMS信息超出要设置的值当在使用这些setter方法之一后调用<code> getParams </code>方法时,数组中的元素本身将是容纳附加信息在这个类别中,方
* 法<code> setNull </code>是一种特殊情况,因为一个版本只需要两个参数(<code> setNull(int parameterIndex,int SqlType)</code>以包含
* 将传递给驱动程序和DBMS的信息。
* 此数组中的第一个元素是要设置的值,即<code> null </code>,第二个元素是<code> int </code >为<i> sqlType </i>提供,它指示正在设置为<code> nul
* l </code>的SQL值的类型一些DBMS需要这些信息,因此需要这些信息以确保应用程序是可移植的其他版本用于当要设置为<code> null </code>的值是用户定义的类型时它需要三个参数(<code>
*  setNull(int parameterIndex,int sqlType,String typeName)</code>),并且还需要一个数组来包含要传递给驱动程序和DBMS的信息。
* 此数组中的前两个元素与对于<code>的第一个版本setNull </code>第三个元素<i> typeName </i>给出了用户定义类型的SQL名称与其他setter方法一样,占位符的数量要设置的
* 参数由<code> getParams </code>返回的数组中元素的位置指示因此,例如,如果提供给<code> setNull </code>的参数是<code> 2 </code>,则<code>
*  getParams </code>返回的数组中的第二个元素将是一个两或三个元件。
* <P>
* 一些方法,如<code> setObject </code>和<code> setDate </code>具有多于两个参数的版本,额外的参数给驱动程序或DBMS提供信息。
* 例如,方法<code > setDate </code>,<code> setTime </code>和<code> setTimestamp </code>可以将<code> Calendar </code>
* 对象作为第三个参数。
* 一些方法,如<code> setObject </code>和<code> setDate </code>具有多于两个参数的版本,额外的参数给驱动程序或DBMS提供信息。
* 如果DBMS不存储时区信息,驱动程序使用<code> Calendar </code>对象来构造设置为<code> Date </code>,<code> Time </code>或<code> Tim
* estamp </code>与提供附加信息的其他方法,<code> getParams </code>返回的数组中的元素是一个数组,而不是一个简单的<code> Object </code>实例。
* 一些方法,如<code> setObject </code>和<code> setDate </code>具有多于两个参数的版本,额外的参数给驱动程序或DBMS提供信息。
* <P>
* 方法<code> setAsciiStream </code>,<code> setBinaryStream </code>,<code> setCharacterStream </code>和<code>
*  setUnicodeStream </code>(不推荐使用, getCharacterStream </code>改为)有三个参数,因此对于他们,由<code> getParams </code>返
* 回的数组中的元素也是一个数组这些setter方法的不同之处是,除了提供的信息参数,数组包含指示正在设置的流的类型的<code> BaseRowSet </code>常量之一。
* <p>
*  注意：<code> getParams </code>的方法由<code> RowSet </code>实现扩展这个类在内部调用;它通常不由应用程序员直接调用
* 
* <h3> 50事件通知</h3> <code> BaseRowSet </code>类为rowsets提供事件通知机制它包含<code> listeners </code>字段,添加和删除侦听器的方法,
* 通知监听器的变化。
* <P>
*  侦听器是实现了<code> RowSetListener </code>接口的对象如果它已经添加到<code> RowSet </code>对象的侦听器列表中,当<code> > RowSet </code>
*  object每个监听器的<code> RowSetListener </code>方法的实现定义了当通知事件发生时对象将做什么。
* <P>
*  一个<code> RowSet </code>对象有三个可能的事件：
* <OL>
* <LI>光标移动<LI>单个行被更改(更新,删除或插入)<LI>整个<code> RowSet </code>对象的内容被更改
* </OL>
* <P>
*  用于通知的<code> BaseRowSet </code>方法指示已经发生的事件的类型例如,方法<code> notifyRowChanged </code>指示行已被更新,删除或插入。
* 通知方法创建一个<code> RowSetEvent </code>对象,它被提供给侦听器以便识别事件发生的<code> RowSet </code>对象侦听器对此信息使用哪些没有什么,取决于如何实现。
*  用于通知的<code> BaseRowSet </code>方法指示已经发生的事件的类型例如,方法<code> notifyRowChanged </code>指示行已被更新,删除或插入。
* 
*  <h3> 60默认行为</h3>默认的<code> BaseRowSet </code>对象用许多初始值初始化
* 
* 下面是对扩展<code> BaseRowSet </code>类的默认<code> RowSet </code>实例的说法：
* <UL>
* <LI>具有可滚动的光标,并且不显示其他人所做的更改<LI>可更新<LI>不显示已删除的行<LI>驱动程序可能需要多长时间才能执行<code> > RowSet </code>对象的命令<LI>对其可以
* 包含的行数没有限制<LI>对列可以包含的字节数没有限制注意：此限制仅适用于包含以下类型：<code> BINARY </code>,<code> VARBINARY </code>,<code> LON
* GVARBINARY </code>,<code> CHAR </code>,<code> VARCHAR </code>代码> LONGVARCHAR </code> <LI>不会看到未提交的数据(使
* "脏"读取)<LI>已启用转义处理<LI>其连接类型映射设置为<code> null </code><LI>有一个空的<code> Vector </code>对象用于存储在<code> RowSet 
* </code>对象的命令中为占位符参数设置的值。
* </UL>
* <p>
* 如果需要其他值,应用程序必须显式设置属性值例如,以下代码行将<code> CachedRowSet </code>对象<i> crs </i>的最大行数设置为500
* <PRE>
*  crssetMaxRows(500);
* </PRE>
*  在<code> BaseRowSet </code>类<b>的扩展中实现的方法必须</b>为任何违反定义的断言抛出<code> SQLException </code>对象。
* 另外,如果扩展类覆盖和重新实现任何<code> BaseRowSet </code>方法并遇到连接或底层数据源问题,该方法<b>可能</b>另外抛出一个<code> SQLException </code>
* 对象。
*  在<code> BaseRowSet </code>类<b>的扩展中实现的方法必须</b>为任何违反定义的断言抛出<code> SQLException </code>对象。
* 
*/

public abstract class BaseRowSet implements Serializable, Cloneable {

    /**
     * A constant indicating to a <code>RowSetReaderImpl</code> object
     * that a given parameter is a Unicode stream. This
     * <code>RowSetReaderImpl</code> object is provided as an extension of the
     * <code>SyncProvider</code> abstract class defined in the
     * <code>SyncFactory</code> static factory SPI mechanism.
     * <p>
     * 给一个<code> RowSetReaderImpl </code>对象指示给定参数是Unicode流的常量这个<code> RowSetReaderImpl </code>对象是作为定义的<code>
     *  SyncProvider </code>抽象类的扩展提​​供的在<code> SyncFactory </code>静态工厂SPI机制中。
     * 
     */
    public static final int UNICODE_STREAM_PARAM = 0;

    /**
     * A constant indicating to a <code>RowSetReaderImpl</code> object
     * that a given parameter is a binary stream. A
     * <code>RowSetReaderImpl</code> object is provided as an extension of the
     * <code>SyncProvider</code> abstract class defined in the
     * <code>SyncFactory</code> static factory SPI mechanism.
     * <p>
     *  一个常量指示给一个<code> RowSetReaderImpl </code>对象一个给定的参数是一个二进制流一个<code> RowSetReaderImpl </code>对象提供作为<code>
     *  SyncProvider </code>在<code> SyncFactory </code>静态工厂SPI机制中。
     * 
     */
    public static final int BINARY_STREAM_PARAM = 1;

    /**
     * A constant indicating to a <code>RowSetReaderImpl</code> object
     * that a given parameter is an ASCII stream. A
     * <code>RowSetReaderImpl</code> object is provided as an extension of the
     * <code>SyncProvider</code> abstract class defined in the
     * <code>SyncFactory</code> static factory SPI mechanism.
     * <p>
     * 指示给一个<code> RowSetReaderImpl </code>对象的常量,给定的参数是一个ASCII码流。
     * 一个<code> RowSetReaderImpl </code>对象是作为定义的<code> SyncProvider </code>在<code> SyncFactory </code>静态工厂SP
     * I机制中。
     * 指示给一个<code> RowSetReaderImpl </code>对象的常量,给定的参数是一个ASCII码流。
     * 
     */
    public static final int ASCII_STREAM_PARAM = 2;

    /**
     * The <code>InputStream</code> object that will be
     * returned by the method <code>getBinaryStream</code>, which is
     * specified in the <code>ResultSet</code> interface.
     * <p>
     *  将由<code> ResultSet </code>接口中指定的<code> getBinaryStream </code>方法返回的<code> InputStream </code>
     * 
     * 
     * @serial
     */
    protected java.io.InputStream binaryStream;

    /**
     * The <code>InputStream</code> object that will be
     * returned by the method <code>getUnicodeStream</code>,
     * which is specified in the <code>ResultSet</code> interface.
     * <p>
     *  将由<code> ResultSet </code>接口中指定的<code> getUnicodeStream </code>方法返回的<code> InputStream </code>
     * 
     * 
     * @serial
     */
    protected java.io.InputStream unicodeStream;

    /**
     * The <code>InputStream</code> object that will be
     * returned by the method <code>getAsciiStream</code>,
     * which is specified in the <code>ResultSet</code> interface.
     * <p>
     *  将由<code> ResultSet </code>接口中指定的<code> getAsciiStream </code>方法返回的<code> InputStream </code>
     * 
     * 
     * @serial
     */
    protected java.io.InputStream asciiStream;

    /**
     * The <code>Reader</code> object that will be
     * returned by the method <code>getCharacterStream</code>,
     * which is specified in the <code>ResultSet</code> interface.
     * <p>
     * 将由<code> ResultSet </code>接口中指定的<code> getCharacterStream </code>方法返回的<code> Reader </code>
     * 
     * 
     * @serial
     */
    protected java.io.Reader charStream;

    /**
     * The query that will be sent to the DBMS for execution when the
     * method <code>execute</code> is called.
     * <p>
     *  当方法<code> execute </code>被调用时,将发送到DBMS执行的查询
     * 
     * 
     * @serial
     */
    private String command;

    /**
     * The JDBC URL the reader, writer, or both supply to the method
     * <code>DriverManager.getConnection</code> when the
     * <code>DriverManager</code> is used to get a connection.
     * <P>
     * The JDBC URL identifies the driver to be used to make the conndection.
     * This URL can be found in the documentation supplied by the driver
     * vendor.
     * <p>
     *  JDBC URL,当<code> DriverManager </code>用于获取连接时,reader,writer或两者都提供给方法<code> DriverManagergetConnectio
     * n </code>。
     * <P>
     *  JDBC URL标识用于建立连接的驱动程序。此URL可在驱动程序供应商提供的文档中找到
     * 
     * 
     * @serial
     */
    private String URL;

    /**
     * The logical name of the data source that the reader/writer should use
     * in order to retrieve a <code>DataSource</code> object from a Java
     * Directory and Naming Interface (JNDI) naming service.
     * <p>
     *  读取器/写入器应该使用以从Java目录和命名接口(JNDI)命名服务检索<code> DataSource </code>对象的数据源的逻辑名称
     * 
     * 
     * @serial
     */
    private String dataSource;

    /**
     * The user name the reader, writer, or both supply to the method
     * <code>DriverManager.getConnection</code> when the
     * <code>DriverManager</code> is used to get a connection.
     * <p>
     * 当<code> DriverManager </code>用于获取连接时,用户名读取器,写入器或两者都提供给方法<code> DriverManagergetConnection </code>
     * 
     * 
     * @serial
     */
    private transient String username;

    /**
     * The password the reader, writer, or both supply to the method
     * <code>DriverManager.getConnection</code> when the
     * <code>DriverManager</code> is used to get a connection.
     * <p>
     *  当<code> DriverManager </code>用于获取连接时,读取器,写入器或两者都提供给方法<code> DriverManagergetConnection </code>的密码
     * 
     * 
     * @serial
     */
    private transient String password;

    /**
     * A constant indicating the type of this JDBC <code>RowSet</code>
     * object. It must be one of the following <code>ResultSet</code>
     * constants:  <code>TYPE_FORWARD_ONLY</code>,
     * <code>TYPE_SCROLL_INSENSITIVE</code>, or
     * <code>TYPE_SCROLL_SENSITIVE</code>.
     * <p>
     *  指示此JDBC <type> RowSet </code>对象类型的常量它必须是以下<code> ResultSet </code>常量之一：<code> TYPE_FORWARD_ONLY </code>
     * ,<code> TYPE_SCROLL_INSENSITIVE </code >或<code> TYPE_SCROLL_SENSITIVE </code>。
     * 
     * 
     * @serial
     */
    private int rowSetType = ResultSet.TYPE_SCROLL_INSENSITIVE;

    /**
     * A <code>boolean</code> indicating whether deleted rows are visible in this
     * JDBC <code>RowSet</code> object .
     * <p>
     *  指示在此JDBC <code> RowSet </code>对象中是否显示已删除的行的<code> boolean </code>
     * 
     * 
     * @serial
     */
    private boolean showDeleted = false; // default is false

    /**
     * The maximum number of seconds the driver
     * will wait for a command to execute.  This limit applies while
     * this JDBC <code>RowSet</code> object is connected to its data
     * source, that is, while it is populating itself with
     * data and while it is writing data back to the data source.
     * <p>
     * 驱动程序将等待执行命令的最大秒数此限制适用于此JDBC <code> RowSet </code>对象连接到其数据源时,即它正在使用数据填充自身,而它将数据写回数据源
     * 
     * 
     * @serial
     */
    private int queryTimeout = 0; // default is no timeout

    /**
     * The maximum number of rows the reader should read.
     * <p>
     *  读取器应读取的最大行数
     * 
     * 
     * @serial
     */
    private int maxRows = 0; // default is no limit

    /**
     * The maximum field size the reader should read.
     * <p>
     *  读取器应读取的最大字段大小
     * 
     * 
     * @serial
     */
    private int maxFieldSize = 0; // default is no limit

    /**
     * A constant indicating the concurrency of this JDBC <code>RowSet</code>
     * object. It must be one of the following <code>ResultSet</code>
     * constants: <code>CONCUR_READ_ONLY</code> or
     * <code>CONCUR_UPDATABLE</code>.
     * <p>
     *  指示此JDBC <code> RowSet </code>对象的并发性的常量它必须是以下<code> ResultSet </code>常量之一：<code> CONCUR_READ_ONLY </code>
     * 或<code> CONCUR_UPDATABLE </code >。
     * 
     * 
     * @serial
     */
    private int concurrency = ResultSet.CONCUR_UPDATABLE;

    /**
     * A <code>boolean</code> indicating whether this JDBC <code>RowSet</code>
     * object is read-only.  <code>true</code> indicates that it is read-only;
     * <code>false</code> that it is writable.
     * <p>
     *  指示此JDBC <code> RowSet </code>对象是否为只读<code> true </code>的<code> boolean </code>表示它是只读的; <code> false 
     * </code>它是可写的。
     * 
     * 
     * @serial
     */
    private boolean readOnly;

    /**
     * A <code>boolean</code> indicating whether the reader for this
     * JDBC <code>RowSet</code> object should perform escape processing.
     * <code>true</code> means that escape processing is turned on;
     * <code>false</code> that it is not. The default is <code>true</code>.
     * <p>
     * 指示该JDBC <code> RowSet </code>对象的读者是否应该执行转义处理<code> true </code>的<code> boolean </code>表示转义处理被打开; <code>
     *  false </code>,它不是默认的<code> true </code>。
     * 
     * 
     * @serial
     */
    private boolean escapeProcessing;

    /**
     * A constant indicating the isolation level of the connection
     * for this JDBC <code>RowSet</code> object . It must be one of
     * the following <code>Connection</code> constants:
     * <code>TRANSACTION_NONE</code>,
     * <code>TRANSACTION_READ_UNCOMMITTED</code>,
     * <code>TRANSACTION_READ_COMMITTED</code>,
     * <code>TRANSACTION_REPEATABLE_READ</code> or
     * <code>TRANSACTION_SERIALIZABLE</code>.
     * <p>
     *  指示此JDBC <code> RowSet </code>对象的连接的隔离级别的常量它必须是以下<code> Connection </code>常量之一：<code> TRANSACTION_NON
     * E </code>,<code> TRANSACTION_READ_UNCOMMITTED </code>,<code> TRANSACTION_READ_COMMITTED </code>,<code>
     *  TRANSACTION_REPEATABLE_READ </code>或<code> TRANSACTION_SERIALIZABLE </code>。
     * 
     * 
     * @serial
     */
    private int isolation;

    /**
     * A constant used as a hint to the driver that indicates the direction in
     * which data from this JDBC <code>RowSet</code> object  is going
     * to be fetched. The following <code>ResultSet</code> constants are
     * possible values:
     * <code>FETCH_FORWARD</code>,
     * <code>FETCH_REVERSE</code>,
     * <code>FETCH_UNKNOWN</code>.
     * <P>
     * Unused at this time.
     * <p>
     * 用作提示驱动程序的常量,指示要从此JDBC <code> RowSet </code>对象获取数据的方向以下<code> ResultSet </code>常量是可能的值：<code > FETCH_F
     * ORWARD </code>,<code> FETCH_REVERSE </code>,<code> FETCH_UNKNOWN </code>。
     * <P>
     *  此时未使用
     * 
     * 
     * @serial
     */
    private int fetchDir = ResultSet.FETCH_FORWARD; // default fetch direction

    /**
     * A hint to the driver that indicates the expected number of rows
     * in this JDBC <code>RowSet</code> object .
     * <P>
     * Unused at this time.
     * <p>
     *  对驱动程序的提示,指示此JDBC <code> RowSet </code>对象中的预期行数
     * <P>
     *  此时未使用
     * 
     * 
     * @serial
     */
    private int fetchSize = 0; // default fetchSize

    /**
     * The <code>java.util.Map</code> object that contains entries mapping
     * SQL type names to classes in the Java programming language for the
     * custom mapping of user-defined types.
     * <p>
     *  <code> javautilMap </code>对象,包含将SQL类型名称映射到Java编程语言中用于自定义映射用户定义类型的类的条目
     * 
     * 
     * @serial
     */
    private Map<String, Class<?>> map;

    /**
     * A <code>Vector</code> object that holds the list of listeners
     * that have registered with this <code>RowSet</code> object.
     * <p>
     *  一个<code> Vector </code>对象,它保存已注册到此<code> RowSet </code>对象的监听器列表
     * 
     * 
     * @serial
     */
    private Vector<RowSetListener> listeners;

    /**
     * A <code>Vector</code> object that holds the parameters set
     * for this <code>RowSet</code> object's current command.
     * <p>
     * 一个<code> Vector </code>对象,它保存为<code> RowSet </code>对象的当前命令设置的参数
     * 
     * 
     * @serial
     */
    private Hashtable<Integer, Object> params; // could be transient?

    /**
     * Constructs a new <code>BaseRowSet</code> object initialized with
     * a default <code>Vector</code> object for its <code>listeners</code>
     * field. The other default values with which it is initialized are listed
     * in Section 6.0 of the class comment for this class.
     * <p>
     *  构造一个新的<code> BaseRowSet </code>对象,它的<code> listeners </code>字段使用默认的<code> Vector </code>对象初始化其初始化的其他
     * 默认值列于第60节这个类的类注释。
     * 
     */
    public BaseRowSet() {
        // allocate the listeners collection
        listeners = new Vector<RowSetListener>();
    }

    /**
     * Performs the necessary internal configurations and initializations
     * to allow any JDBC <code>RowSet</code> implementation to start using
     * the standard facilities provided by a <code>BaseRowSet</code>
     * instance. This method <b>should</b> be called after the <code>RowSet</code> object
     * has been instantiated to correctly initialize all parameters. This method
     * <b>should</b> never be called by an application, but is called from with
     * a <code>RowSet</code> implementation extending this class.
     * <p>
     * 执行必要的内部配置和初始化,以允许任何JDBC <code> RowSet </code>实现开始使用<code> BaseRowSet </code>实例提供的标准功能。
     * <b>应该</b>在<code> RowSet </code>对象被实例化以正确初始化所有参数之后<b>应该</b>从不被应用程序调用,但是从<code> RowSet </code>实现扩展这个类。
     * 
     */
    protected void initParams() {
        params = new Hashtable<Integer, Object>();
    }

    //--------------------------------------------------------------------
    // Events
    //--------------------------------------------------------------------

    /**
    * The listener will be notified whenever an event occurs on this <code>RowSet</code>
    * object.
    * <P>
    * A listener might, for example, be a table or graph that needs to
    * be updated in order to accurately reflect the current state of
    * the <code>RowSet</code> object.
    * <p>
    * <b>Note</b>: if the <code>RowSetListener</code> object is
    * <code>null</code>, this method silently discards the <code>null</code>
    * value and does not add a null reference to the set of listeners.
    * <p>
    * <b>Note</b>: if the listener is already set, and the new <code>RowSetListerner</code>
    * instance is added to the set of listeners already registered to receive
    * event notifications from this <code>RowSet</code>.
    *
    * <p>
    *  每当在<code> RowSet </code>对象上发生事件时,将通知侦听器
    * <P>
    *  例如,侦听器可能是需要更新的表或图形,以便准确地反映<code> RowSet </code>对象的当前状态
    * <p>
    * <b>注意</b>：如果<code> RowSetListener </code>对象是<code> null </code>,此方法会默认丢弃<code> null </code>值,引用侦听器集合。
    * <p>
    *  <b>注意</b>：如果侦听器已经设置,并且新的<code> RowSetListerner </code>实例被添加到已经注册的侦听器集合,以接收来自<code> RowSet </code >
    * 
    * 
    * @param listener an object that has implemented the
    *     <code>javax.sql.RowSetListener</code> interface and wants to be notified
    *     of any events that occur on this <code>RowSet</code> object; May be
    *     null.
    * @see #removeRowSetListener
    */
    public void addRowSetListener(RowSetListener listener) {
        listeners.add(listener);
    }

    /**
    * Removes the designated object from this <code>RowSet</code> object's list of listeners.
    * If the given argument is not a registered listener, this method
    * does nothing.
    *
    *  <b>Note</b>: if the <code>RowSetListener</code> object is
    * <code>null</code>, this method silently discards the <code>null</code>
    * value.
    *
    * <p>
    *  从此<code> RowSet </code>对象的侦听器列表中删除指定的对象如果给定的参数不是注册的侦听器,则此方法不执行任何操作
    * 
    *  <b>注意</b>：如果<code> RowSetListener </code>对象是<code> null </code>,此方法会默认丢弃<code> null </code>
    * 
    * 
    * @param listener a <code>RowSetListener</code> object that is on the list
    *        of listeners for this <code>RowSet</code> object
    * @see #addRowSetListener
    */
    public void removeRowSetListener(RowSetListener listener) {
        listeners.remove(listener);
    }

    /**
     * Determine if instance of this class extends the RowSet interface.
     * <p>
     *  确定此类的实例是否扩展了RowSet接口
     * 
     */
    private void checkforRowSetInterface() throws SQLException {
        if ((this instanceof javax.sql.RowSet) == false) {
            throw new SQLException("The class extending abstract class BaseRowSet " +
                "must implement javax.sql.RowSet or one of it's sub-interfaces.");
        }
    }

    /**
    * Notifies all of the listeners registered with this
    * <code>RowSet</code> object that its cursor has moved.
    * <P>
    * When an application calls a method to move the cursor,
    * that method moves the cursor and then calls this method
    * internally. An application <b>should</b> never invoke
    * this method directly.
    *
    * <p>
    * 通知注册到此<> RowSet </code>对象的所有侦听器,其游标已移动
    * <P>
    *  当应用程序调用移动游标的方法时,该方法移动游标,然后在内部调用此方法应用程序<b>应</b>不要直接调用此方法
    * 
    * 
    * @throws SQLException if the class extending the <code>BaseRowSet</code>
    *     abstract class does not implement the <code>RowSet</code> interface or
    *     one of it's sub-interfaces.
    */
    protected void notifyCursorMoved() throws SQLException {
        checkforRowSetInterface();
        if (listeners.isEmpty() == false) {
            RowSetEvent event = new RowSetEvent((RowSet)this);
            for (RowSetListener rsl : listeners) {
                rsl.cursorMoved(event);
            }
        }
    }

    /**
    * Notifies all of the listeners registered with this <code>RowSet</code> object that
    * one of its rows has changed.
    * <P>
    * When an application calls a method that changes a row, such as
    * the <code>CachedRowSet</code> methods <code>insertRow</code>,
    * <code>updateRow</code>, or <code>deleteRow</code>,
    * that method calls <code>notifyRowChanged</code>
    * internally. An application <b>should</b> never invoke
    * this method directly.
    *
    * <p>
    *  通知注册到此<code> RowSet </code>对象的所有侦听器,其中一行已更改
    * <P>
    *  当应用程序调用改变行的方法,例如<code> CachedRowSet </code>方法<code> insertRow </code>,<code> updateRow </code>或<code>
    *  deleteRow </code> ,该方法内部调用<code> notifyRowChanged </code>应用程序<b>应</b>不要直接调用此方法。
    * 
    * 
    * @throws SQLException if the class extending the <code>BaseRowSet</code>
    *     abstract class does not implement the <code>RowSet</code> interface or
    *     one of it's sub-interfaces.
    */
    protected void notifyRowChanged() throws SQLException {
        checkforRowSetInterface();
        if (listeners.isEmpty() == false) {
                RowSetEvent event = new RowSetEvent((RowSet)this);
                for (RowSetListener rsl : listeners) {
                    rsl.rowChanged(event);
                }
        }
    }

   /**
    * Notifies all of the listeners registered with this <code>RowSet</code>
    * object that its entire contents have changed.
    * <P>
    * When an application calls methods that change the entire contents
    * of the <code>RowSet</code> object, such as the <code>CachedRowSet</code> methods
    * <code>execute</code>, <code>populate</code>, <code>restoreOriginal</code>,
    * or <code>release</code>, that method calls <code>notifyRowSetChanged</code>
    * internally (either directly or indirectly). An application <b>should</b>
    * never invoke this method directly.
    *
    * <p>
    * 通知注册到此<code> RowSet </code>对象的所有监听器,其全部内容已更改
    * <P>
    *  当应用程序调用改变<code> RowSet </code>对象(例如<code> CachedRowSet </code>方法<code> execute </code>,<code> popula
    * te </code >,<code> restoreOriginal </code>或<code> release </code>,该方法在内部调用<code> notifyRowSetChanged 
    * </code>直接调用此方法。
    * 
    * 
    * @throws SQLException if the class extending the <code>BaseRowSet</code>
    *     abstract class does not implement the <code>RowSet</code> interface or
    *     one of it's sub-interfaces.
    */
    protected void notifyRowSetChanged() throws SQLException {
        checkforRowSetInterface();
        if (listeners.isEmpty() == false) {
                RowSetEvent event = new RowSetEvent((RowSet)this);
                for (RowSetListener rsl : listeners) {
                    rsl.rowSetChanged(event);
                }
        }
}

    /**
     * Retrieves the SQL query that is the command for this
     * <code>RowSet</code> object. The command property contains the query that
     * will be executed to populate this <code>RowSet</code> object.
     * <P>
     * The SQL query returned by this method is used by <code>RowSet</code> methods
     * such as <code>execute</code> and <code>populate</code>, which may be implemented
     * by any class that extends the <code>BaseRowSet</code> abstract class and
     * implements one or more of the standard JSR-114 <code>RowSet</code>
     * interfaces.
     * <P>
     * The command is used by the <code>RowSet</code> object's
     * reader to obtain a <code>ResultSet</code> object.  The reader then
     * reads the data from the <code>ResultSet</code> object and uses it to
     * to populate this <code>RowSet</code> object.
     * <P>
     * The default value for the <code>command</code> property is <code>null</code>.
     *
     * <p>
     *  检索作为此<code> RowSet </code>对象的命令的SQL查询命令属性包含将执行以填充此<code> RowSet </code>对象的查询
     * <P>
     * 此方法返回的SQL查询由<code> RowSet </code>方法(如<code> execute </code>和<code> populate </code>)使用,可以由任何类扩展<代码> B
     * aseRowSet </code>抽象类,并实现一个或多个标准JSR-114 <code> RowSet </code>接口。
     * <P>
     *  该命令由<code> RowSet </code>对象的读取器使用以获得<code> ResultSet </code>对象。
     * 然后,读取器从<code> ResultSet </code>对象读取数据,填充这个<code> RowSet </code>对象。
     * <P>
     *  <code>命令</code>属性的默认值为<code> null </code>
     * 
     * 
     * @return the <code>String</code> that is the value for this
     *         <code>RowSet</code> object's <code>command</code> property;
     *         may be <code>null</code>
     * @see #setCommand
     */
    public String getCommand() {
        return command;
    }

    /**
     * Sets this <code>RowSet</code> object's <code>command</code> property to
     * the given <code>String</code> object and clears the parameters, if any,
     * that were set for the previous command.
     * <P>
     * The <code>command</code> property may not be needed if the <code>RowSet</code>
     * object gets its data from a source that does not support commands,
     * such as a spreadsheet or other tabular file.
     * Thus, this property is optional and may be <code>null</code>.
     *
     * <p>
     * 将<code> RowSet </code>对象的<code>命令</code>属性设置为给定的<code> String </code>对象,并清除为上一个命令设置的参数
     * <P>
     *  如果<code> RowSet </code>对象从不支持命令的源(如电子表格或其他表格文件)获取其数据,则可能不需要<code>命令</code>属性。
     * 因此,此属性可选并且可以是<code> null </code>。
     * 
     * 
     * @param cmd a <code>String</code> object containing an SQL query
     *            that will be set as this <code>RowSet</code> object's command
     *            property; may be <code>null</code> but may not be an empty string
     * @throws SQLException if an empty string is provided as the command value
     * @see #getCommand
     */
    public void setCommand(String cmd) throws SQLException {
        // cmd equal to null or
        // cmd with length 0 (implies url =="")
        // are not independent events.

        if(cmd == null) {
           command = null;
        } else if (cmd.length() == 0) {
            throw new SQLException("Invalid command string detected. " +
            "Cannot be of length less than 0");
        } else {
            // "unbind" any parameters from any previous command.
            if(params == null){
                 throw new SQLException("Set initParams() before setCommand");
            }
            params.clear();
            command = cmd;
        }

    }

    /**
     * Retrieves the JDBC URL that this <code>RowSet</code> object's
     * <code>javax.sql.Reader</code> object uses to make a connection
     * with a relational database using a JDBC technology-enabled driver.
     *<P>
     * The <code>Url</code> property will be <code>null</code> if the underlying data
     * source is a non-SQL data source, such as a spreadsheet or an XML
     * data source.
     *
     * <p>
     *  检索此<> RowSet </code>对象的<code> javaxsqlReader </code>对象用于使用JDBC技术启用的驱动程序与关系数据库建立连接的JDBC URL
     * P>
     *  如果基础数据源是非SQL数据源(如电子表格或XML数据源),则<code> Url </code>属性将为<code> null </code>
     * 
     * 
     * @return a <code>String</code> object that contains the JDBC URL
     *         used to establish the connection for this <code>RowSet</code>
     *         object; may be <code>null</code> (default value) if not set
     * @throws SQLException if an error occurs retrieving the URL value
     * @see #setUrl
     */
    public String getUrl() throws SQLException {
        return URL;
    }

    /**
     * Sets the Url property for this <code>RowSet</code> object
     * to the given <code>String</code> object and sets the dataSource name
     * property to <code>null</code>. The Url property is a
     * JDBC URL that is used when
     * the connection is created using a JDBC technology-enabled driver
     * ("JDBC driver") and the <code>DriverManager</code>.
     * The correct JDBC URL for the specific driver to be used can be found
     * in the driver documentation.  Although there are guidelines for for how
     * a JDBC URL is formed,
     * a driver vendor can specify any <code>String</code> object except
     * one with a length of <code>0</code> (an empty string).
     * <P>
     * Setting the Url property is optional if connections are established using
     * a <code>DataSource</code> object instead of the <code>DriverManager</code>.
     * The driver will use either the URL property or the
     * dataSourceName property to create a connection, whichever was
     * specified most recently. If an application uses a JDBC URL, it
     * must load a JDBC driver that accepts the JDBC URL before it uses the
     * <code>RowSet</code> object to connect to a database.  The <code>RowSet</code>
     * object will use the URL internally to create a database connection in order
     * to read or write data.
     *
     * <p>
     * 将<code> RowSet </code>对象的Url属性设置为给定的<code> String </code>对象,并将dataSource name属性设置为<code> null </code>
     *  Url属性是一个JDBC URL当使用支持JDBC技术的驱动程序("JDBC驱动程序")和<code> DriverManager </code>创建连接时使用。
     * 在驱动程序文档中可以找到要使用的特定驱动程序的正确JDBC URL尽管是如何形成JDBC URL的准则,驱动程序供应商可以指定任何<code> String </code>对象,除了长度为<code> 
     * 0 </code>的对象(空字符串)。
     * <P>
     * 如果使用<code> DataSource </code>对象而不是<code> DriverManager </code>建立连接,则设置Url属性是可选的。
     * 驱动程序将使用URL属性或dataSourceName属性创建连接如果应用程序使用JDBC URL,它必须在使用<code> RowSet </code>对象连接到数据库之前加载接受JDBC URL的J
     * DBC驱动程序。
     * 如果使用<code> DataSource </code>对象而不是<code> DriverManager </code>建立连接,则设置Url属性是可选的。
     * <code> RowSet </code>对象将在内部使用URL来创建数据库连接,以便读取或写入数据。
     * 
     * 
     * @param url a <code>String</code> object that contains the JDBC URL
     *     that will be used to establish the connection to a database for this
     *     <code>RowSet</code> object; may be <code>null</code> but must not
     *     be an empty string
     * @throws SQLException if an error occurs setting the Url property or the
     *     parameter supplied is a string with a length of <code>0</code> (an
     *     empty string)
     * @see #getUrl
     */
    public void setUrl(String url) throws SQLException {
        if(url == null) {
           url = null;
        } else if (url.length() < 1) {
            throw new SQLException("Invalid url string detected. " +
            "Cannot be of length less than 1");
        } else {
            URL = url;
        }

        dataSource = null;

    }

    /**
     * Returns the logical name that when supplied to a naming service
     * that uses the Java Naming and Directory Interface (JNDI) API, will
     * retrieve a <code>javax.sql.DataSource</code> object. This
     * <code>DataSource</code> object can be used to establish a connection
     * to the data source that it represents.
     * <P>
     * Users should set either the url or the data source name property.
     * The driver will use the property set most recently to establish a
     * connection.
     *
     * <p>
     * 返回提供给使用Java命名和目录接口(JNDI)API的命名服务时将检索<code> javaxsqlDataSource </code>对象的逻辑名称此<code> DataSource </code>
     * 对象可用于建立与其表示的数据源的连接。
     * <P>
     *  用户应设置url或数据源名称属性驱动程序将使用最近设置的属性建立连接
     * 
     * 
     * @return a <code>String</code> object that identifies the
     *         <code>DataSource</code> object to be used for making a
     *         connection; if no logical name has been set, <code>null</code>
     *         is returned.
     * @see #setDataSourceName
     */
    public String getDataSourceName() {
        return dataSource;
    }


    /**
     * Sets the <code>DataSource</code> name property for this <code>RowSet</code>
     * object to the given logical name and sets this <code>RowSet</code> object's
     * Url property to <code>null</code>. The name must have been bound to a
     * <code>DataSource</code> object in a JNDI naming service so that an
     * application can do a lookup using that name to retrieve the
     * <code>DataSource</code> object bound to it. The <code>DataSource</code>
     * object can then be used to establish a connection to the data source it
     * represents.
     * <P>
     * Users should set either the Url property or the dataSourceName property.
     * If both properties are set, the driver will use the property set most recently.
     *
     * <p>
     * 将<code> RowSet </code>对象的<code> DataSource </code> name属性设置为给定的逻辑名称,并将此<code> RowSet </code>对象的Url属性设
     * 置为<code> null </code >该名称必须已绑定到JNDI命名服务中的<code> DataSource </code>对象,以便应用程序可以使用该名称来检索绑定到它的<code> Data
     * Source </code>对象进行查找。
     * 然后可以使用<code> DataSource </code>对象建立到它所代表的数据源的连接。
     * <P>
     *  用户应该设置Url属性或dataSourceName属性如果两个属性都设置,驱动程序将使用最近设置的属性
     * 
     * 
     * @param name a <code>String</code> object with the name that can be supplied
     *     to a naming service based on JNDI technology to retrieve the
     *     <code>DataSource</code> object that can be used to get a connection;
     *     may be <code>null</code> but must not be an empty string
     * @throws SQLException if an empty string is provided as the <code>DataSource</code>
     *    name
     * @see #getDataSourceName
     */
    public void setDataSourceName(String name) throws SQLException {

        if (name == null) {
            dataSource = null;
        } else if (name.equals("")) {
           throw new SQLException("DataSource name cannot be empty string");
        } else {
           dataSource = name;
        }

        URL = null;
    }

    /**
     * Returns the user name used to create a database connection.  Because it
     * is not serialized, the username property is set at runtime before
     * calling the method <code>execute</code>.
     *
     * <p>
     * 返回用于创建数据库连接的用户名因为它没有序列化,所以在调用方法之前在运行时设置username属性<code> execute </code>
     * 
     * 
     * @return the <code>String</code> object containing the user name that
     *         is supplied to the data source to create a connection; may be
     *         <code>null</code> (default value) if not set
     * @see #setUsername
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username property for this <code>RowSet</code> object
     * to the given user name. Because it
     * is not serialized, the username property is set at run time before
     * calling the method <code>execute</code>.
     *
     * <p>
     *  将<code> RowSet </code>对象的username属性设置为给定的用户名因为它没有被序列化,所以在调用方法之前的运行时设置username属性<code> execute </code>
     * 。
     * 
     * 
     * @param name the <code>String</code> object containing the user name that
     *     is supplied to the data source to create a connection. It may be null.
     * @see #getUsername
     */
    public void setUsername(String name) {
        if(name == null)
        {
           username = null;
        } else {
           username = name;
        }
    }

    /**
     * Returns the password used to create a database connection for this
     * <code>RowSet</code> object.  Because the password property is not
     * serialized, it is set at run time before calling the method
     * <code>execute</code>. The default value is <code>null</code>
     *
     * <p>
     *  返回用于为此<code> RowSet </code>对象创建数据库连接的密码由于password属性未序列化,因此在调用方法之前在运行时设置<code> execute </code>默认值为<code>
     *  null </code>。
     * 
     * 
     * @return the <code>String</code> object that represents the password
     *         that must be supplied to the database to create a connection
     * @see #setPassword
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password used to create a database connection for this
     * <code>RowSet</code> object to the given <code>String</code>
     * object.  Because the password property is not
     * serialized, it is set at run time before calling the method
     * <code>execute</code>.
     *
     * <p>
     * 设置用于为此<code> RowSet </code>对象创建与给定<code> String </code>对象的数据库连接的密码因为password属性未序列化,所以在调用方法之前的运行时设置<code>
     *  execute </code>。
     * 
     * 
     * @param pass the <code>String</code> object that represents the password
     *     that is supplied to the database to create a connection. It may be
     *     null.
     * @see #getPassword
     */
    public void setPassword(String pass) {
        if(pass == null)
        {
           password = null;
        } else {
           password = pass;
        }
    }

    /**
     * Sets the type for this <code>RowSet</code> object to the specified type.
     * The default type is <code>ResultSet.TYPE_SCROLL_INSENSITIVE</code>.
     *
     * <p>
     *  将<code> RowSet </code>对象的类型设置为指定的类型默认类型为<code> ResultSetTYPE_SCROLL_INSENSITIVE </code>
     * 
     * 
     * @param type one of the following constants:
     *             <code>ResultSet.TYPE_FORWARD_ONLY</code>,
     *             <code>ResultSet.TYPE_SCROLL_INSENSITIVE</code>, or
     *             <code>ResultSet.TYPE_SCROLL_SENSITIVE</code>
     * @throws SQLException if the parameter supplied is not one of the
     *         following constants:
     *          <code>ResultSet.TYPE_FORWARD_ONLY</code> or
     *          <code>ResultSet.TYPE_SCROLL_INSENSITIVE</code>
     *          <code>ResultSet.TYPE_SCROLL_SENSITIVE</code>
     * @see #getConcurrency
     * @see #getType
     */
    public void setType(int type) throws SQLException {

        if ((type != ResultSet.TYPE_FORWARD_ONLY) &&
           (type != ResultSet.TYPE_SCROLL_INSENSITIVE) &&
           (type != ResultSet.TYPE_SCROLL_SENSITIVE)) {
                throw new SQLException("Invalid type of RowSet set. Must be either " +
                "ResultSet.TYPE_FORWARD_ONLY or ResultSet.TYPE_SCROLL_INSENSITIVE " +
                "or ResultSet.TYPE_SCROLL_SENSITIVE.");
        }
        this.rowSetType = type;
    }

    /**
     * Returns the type of this <code>RowSet</code> object. The type is initially
     * determined by the statement that created the <code>RowSet</code> object.
     * The <code>RowSet</code> object can call the method
     * <code>setType</code> at any time to change its
     * type.  The default is <code>TYPE_SCROLL_INSENSITIVE</code>.
     *
     * <p>
     *  返回此<code> RowSet </code>对象的类型该类型最初由创建<code> RowSet </code>对象的语句确定</code> <code> RowSet </code>对象可以调用
     * < > setType </code>可以随时更改其类型默认为<code> TYPE_SCROLL_INSENSITIVE </code>。
     * 
     * 
     * @return the type of this JDBC <code>RowSet</code>
     *         object, which must be one of the following:
     *         <code>ResultSet.TYPE_FORWARD_ONLY</code>,
     *         <code>ResultSet.TYPE_SCROLL_INSENSITIVE</code>, or
     *         <code>ResultSet.TYPE_SCROLL_SENSITIVE</code>
     * @throws SQLException if an error occurs getting the type of
     *     of this <code>RowSet</code> object
     * @see #setType
     */
    public int getType() throws SQLException {
        return rowSetType;
    }

    /**
     * Sets the concurrency for this <code>RowSet</code> object to
     * the specified concurrency. The default concurrency for any <code>RowSet</code>
     * object (connected or disconnected) is <code>ResultSet.CONCUR_UPDATABLE</code>,
     * but this method may be called at any time to change the concurrency.
     * <P>
     * <p>
     * 将此<code> RowSet </code>对象的并发设置为指定并发任何<code> RowSet </code>对象(连接或断开连接)的默认并发是<code> ResultSetCONCUR_UPD
     * ATABLE </code>,但此方法可以在任何时候调用来改变并发性。
     * <P>
     * 
     * @param concurrency one of the following constants:
     *                    <code>ResultSet.CONCUR_READ_ONLY</code> or
     *                    <code>ResultSet.CONCUR_UPDATABLE</code>
     * @throws SQLException if the parameter supplied is not one of the
     *         following constants:
     *          <code>ResultSet.CONCUR_UPDATABLE</code> or
     *          <code>ResultSet.CONCUR_READ_ONLY</code>
     * @see #getConcurrency
     * @see #isReadOnly
     */
    public void setConcurrency(int concurrency) throws SQLException {

        if((concurrency != ResultSet.CONCUR_READ_ONLY) &&
           (concurrency != ResultSet.CONCUR_UPDATABLE)) {
                throw new SQLException("Invalid concurrency set. Must be either " +
                "ResultSet.CONCUR_READ_ONLY or ResultSet.CONCUR_UPDATABLE.");
        }
        this.concurrency = concurrency;
    }

    /**
     * Returns a <code>boolean</code> indicating whether this
     * <code>RowSet</code> object is read-only.
     * Any attempts to update a read-only <code>RowSet</code> object will result in an
     * <code>SQLException</code> being thrown. By default,
     * rowsets are updatable if updates are possible.
     *
     * <p>
     *  返回一个指示这个<code> RowSet </code>对象是否为只读的<code> boolean </code>任何更新只读<code> RowSet </code>对象的尝试都会产生一个<code>
     *  SQLException </code>被抛出默认情况下,如果更新可能,行集是可更新的。
     * 
     * 
     * @return <code>true</code> if this <code>RowSet</code> object
     *         cannot be updated; <code>false</code> otherwise
     * @see #setConcurrency
     * @see #setReadOnly
     */
    public boolean isReadOnly() {
        return readOnly;
    };

    /**
     * Sets this <code>RowSet</code> object's readOnly  property to the given <code>boolean</code>.
     *
     * <p>
     *  将<code> RowSet </code>对象的readOnly属性设置为给定的<code> boolean </code>
     * 
     * 
     * @param value <code>true</code> to indicate that this
     *              <code>RowSet</code> object is read-only;
     *              <code>false</code> to indicate that it is updatable
     */
    public void setReadOnly(boolean value) {
        readOnly = value;
    }

    /**
     * Returns the transaction isolation property for this
     * <code>RowSet</code> object's connection. This property represents
     * the transaction isolation level requested for use in transactions.
     * <P>
     * For <code>RowSet</code> implementations such as
     * the <code>CachedRowSet</code> that operate in a disconnected environment,
     * the <code>SyncProvider</code> object
     * offers complementary locking and data integrity options. The
     * options described below are pertinent only to connected <code>RowSet</code>
     * objects (<code>JdbcRowSet</code> objects).
     *
     * <p>
     * 返回此<<code> RowSet </code>对象的连接的事务隔离属性此属性表示在事务中请求使用的事务隔离级别
     * <P>
     *  对于在断开的环境中操作的<code> RowSet </code>实现(例如<code> CachedRowSet </code>),<code> SyncProvider </code>对象提供了互
     * 补的锁定和数据完整性选项。
     * 仅适用于连接的<code> RowSet </code>对象(<code> JdbcRowSet </code>对象)。
     * 
     * 
     * @return one of the following constants:
     *         <code>Connection.TRANSACTION_NONE</code>,
     *         <code>Connection.TRANSACTION_READ_UNCOMMITTED</code>,
     *         <code>Connection.TRANSACTION_READ_COMMITTED</code>,
     *         <code>Connection.TRANSACTION_REPEATABLE_READ</code>, or
     *         <code>Connection.TRANSACTION_SERIALIZABLE</code>
     * @see javax.sql.rowset.spi.SyncFactory
     * @see javax.sql.rowset.spi.SyncProvider
     * @see #setTransactionIsolation

     */
    public int getTransactionIsolation() {
        return isolation;
    };

    /**
     * Sets the transaction isolation property for this JDBC <code>RowSet</code> object to the given
     * constant. The DBMS will use this transaction isolation level for
     * transactions if it can.
     * <p>
     * For <code>RowSet</code> implementations such as
     * the <code>CachedRowSet</code> that operate in a disconnected environment,
     * the <code>SyncProvider</code> object being used
     * offers complementary locking and data integrity options. The
     * options described below are pertinent only to connected <code>RowSet</code>
     * objects (<code>JdbcRowSet</code> objects).
     *
     * <p>
     *  将此JDBC <code> RowSet </code>对象的事务隔离属性设置为给定常量如果DBMS可以
     * <p>
     * 对于在断开的环境中操作的<code> RowSet </code>实现(例如<code> CachedRowSet </code>),所使用的<code> SyncProvider </code>对象提
     * 供了互补的锁定和数据完整性选项。
     * 下面仅与连接的<code> RowSet </code>对象(<code> JdbcRowSet </code>对象)。
     * 
     * 
     * @param level one of the following constants, listed in ascending order:
     *              <code>Connection.TRANSACTION_NONE</code>,
     *              <code>Connection.TRANSACTION_READ_UNCOMMITTED</code>,
     *              <code>Connection.TRANSACTION_READ_COMMITTED</code>,
     *              <code>Connection.TRANSACTION_REPEATABLE_READ</code>, or
     *              <code>Connection.TRANSACTION_SERIALIZABLE</code>
     * @throws SQLException if the given parameter is not one of the Connection
     *          constants
     * @see javax.sql.rowset.spi.SyncFactory
     * @see javax.sql.rowset.spi.SyncProvider
     * @see #getTransactionIsolation
     */
    public void setTransactionIsolation(int level) throws SQLException {
        if ((level != Connection.TRANSACTION_NONE) &&
           (level != Connection.TRANSACTION_READ_COMMITTED) &&
           (level != Connection.TRANSACTION_READ_UNCOMMITTED) &&
           (level != Connection.TRANSACTION_REPEATABLE_READ) &&
           (level != Connection.TRANSACTION_SERIALIZABLE))
            {
                throw new SQLException("Invalid transaction isolation set. Must " +
                "be either " +
                "Connection.TRANSACTION_NONE or " +
                "Connection.TRANSACTION_READ_UNCOMMITTED or " +
                "Connection.TRANSACTION_READ_COMMITTED or " +
                "Connection.RRANSACTION_REPEATABLE_READ or " +
                "Connection.TRANSACTION_SERIALIZABLE");
            }
        this.isolation = level;
    }

    /**
     * Retrieves the type map associated with the <code>Connection</code>
     * object for this <code>RowSet</code> object.
     * <P>
     * Drivers that support the JDBC 3.0 API will create
     * <code>Connection</code> objects with an associated type map.
     * This type map, which is initially empty, can contain one or more
     * fully-qualified SQL names and <code>Class</code> objects indicating
     * the class to which the named SQL value will be mapped. The type mapping
     * specified in the connection's type map is used for custom type mapping
     * when no other type map supersedes it.
     * <p>
     * If a type map is explicitly supplied to a method that can perform
     * custom mapping, that type map supersedes the connection's type map.
     *
     * <p>
     *  检索与<code> RowSet </code>对象的<code> Connection </code>对象相关联的类型映射
     * <P>
     * 支持JDBC 30 API的驱动程序将创建具有关联类型映射的<code> Connection </code>对象此类型映射(最初为空)可以包含一个或多个完全限定的SQL名称和<code> Class 
     * </code >指示将命名的SQL值将映射到的类的对象在没有其他类型的映射取代它时,在连接类型映射中指定的类型映射用于自定义类型映射。
     * <p>
     *  如果类型映射显式提供给可以执行自定义映射的方法,那么该类型映射将取代连接的类型映射
     * 
     * 
     * @return the <code>java.util.Map</code> object that is the type map
     *         for this <code>RowSet</code> object's connection
     */
    public java.util.Map<String,Class<?>> getTypeMap() {
        return map;
    }

    /**
     * Installs the given <code>java.util.Map</code> object as the type map
     * associated with the <code>Connection</code> object for this
     * <code>RowSet</code> object.  The custom mapping indicated in
     * this type map will be used unless a different type map is explicitly
     * supplied to a method, in which case the type map supplied will be used.
     *
     * <p>
     * 安装给定的<code> javautilMap </code>对象作为与<code> RowSet </code>对象的<code> Connection </code>对象相关联的类型映射将使用此类型
     * 映射中指示的自定义映射除非将不同类型的映射明确地提供给方法,在这种情况下将使用所提供的类型映射。
     * 
     * 
     * @param map a <code>java.util.Map</code> object that contains the
     *     mapping from SQL type names for user defined types (UDT) to classes in
     *     the Java programming language.  Each entry in the <code>Map</code>
     *     object consists of the fully qualified SQL name of a UDT and the
     *     <code>Class</code> object for the <code>SQLData</code> implementation
     *     of that UDT. May be <code>null</code>.
     */
    public void setTypeMap(java.util.Map<String,Class<?>> map) {
        this.map = map;
    }

    /**
     * Retrieves the maximum number of bytes that can be used for a column
     * value in this <code>RowSet</code> object.
     * This limit applies only to columns that hold values of the
     * following types:  <code>BINARY</code>, <code>VARBINARY</code>,
     * <code>LONGVARBINARY</code>, <code>CHAR</code>, <code>VARCHAR</code>,
     * and <code>LONGVARCHAR</code>.  If the limit is exceeded, the excess
     * data is silently discarded.
     *
     * <p>
     *  检索可用于此<code> RowSet </code>对象中的列值的最大字节数此限制仅适用于包含以下类型的值的列：<code> BINARY </code>,<code > VARBINARY </code>
     * ,<code> LONGVARBINARY </code>,<code> CHAR </code>,<code> VARCHAR </code>和<code> LONGVARCHAR </code>超额
     * 数据被静默丢弃。
     * 
     * 
     * @return an <code>int</code> indicating the current maximum column size
     *     limit; zero means that there is no limit
     * @throws SQLException if an error occurs internally determining the
     *    maximum limit of the column size
     */
    public int getMaxFieldSize() throws SQLException {
        return maxFieldSize;
    }

    /**
     * Sets the maximum number of bytes that can be used for a column
     * value in this <code>RowSet</code> object to the given number.
     * This limit applies only to columns that hold values of the
     * following types:  <code>BINARY</code>, <code>VARBINARY</code>,
     * <code>LONGVARBINARY</code>, <code>CHAR</code>, <code>VARCHAR</code>,
     * and <code>LONGVARCHAR</code>.  If the limit is exceeded, the excess
     * data is silently discarded. For maximum portability, it is advisable to
     * use values greater than 256.
     *
     * <p>
     * 将可用于该<code> RowSet </code>对象中的列值的最大字节数设置为给定数字此限制仅适用于包含以下类型的值的列：<code> BINARY </code >,<code> VARBINAR
     * Y </code>,<code> LONGVARBINARY </code>,<code> CHAR </code>,<code> VARCHAR </code>和<code> LONGVARCHAR 
     * </code>超出了超出的数据,为了最大的可移植性,建议使用大于256的值。
     * 
     * 
     * @param max an <code>int</code> indicating the new maximum column size
     *     limit; zero means that there is no limit
     * @throws SQLException if (1) an error occurs internally setting the
     *     maximum limit of the column size or (2) a size of less than 0 is set
     */
    public void setMaxFieldSize(int max) throws SQLException {
        if (max < 0) {
            throw new SQLException("Invalid max field size set. Cannot be of " +
            "value: " + max);
        }
        maxFieldSize = max;
    }

    /**
     * Retrieves the maximum number of rows that this <code>RowSet</code> object may contain. If
     * this limit is exceeded, the excess rows are silently dropped.
     *
     * <p>
     *  检索此<code> RowSet </code>对象可能包含的最大行数如果超出此限制,则超出的行将被静默删除
     * 
     * 
     * @return an <code>int</code> indicating the current maximum number of
     *     rows; zero means that there is no limit
     * @throws SQLException if an error occurs internally determining the
     *     maximum limit of rows that a <code>Rowset</code> object can contain
     */
    public int getMaxRows() throws SQLException {
        return maxRows;
    }

    /**
     * Sets the maximum number of rows that this <code>RowSet</code> object may contain to
     * the given number. If this limit is exceeded, the excess rows are
     * silently dropped.
     *
     * <p>
     * 设置此<> RowSet </code>对象可能包含给给定数字的最大行数如果超过此限制,超出的行将被静默删除
     * 
     * 
     * @param max an <code>int</code> indicating the current maximum number
     *     of rows; zero means that there is no limit
     * @throws SQLException if an error occurs internally setting the
     *     maximum limit on the number of rows that a JDBC <code>RowSet</code> object
     *     can contain; or if <i>max</i> is less than <code>0</code>; or
     *     if <i>max</i> is less than the <code>fetchSize</code> of the
     *     <code>RowSet</code>
     */
    public void setMaxRows(int max) throws SQLException {
        if (max < 0) {
            throw new SQLException("Invalid max row size set. Cannot be of " +
                "value: " + max);
        } else if (max < this.getFetchSize()) {
            throw new SQLException("Invalid max row size set. Cannot be less " +
                "than the fetchSize.");
        }
        this.maxRows = max;
    }

    /**
     * Sets to the given <code>boolean</code> whether or not the driver will
     * scan for escape syntax and do escape substitution before sending SQL
     * statements to the database. The default is for the driver to do escape
     * processing.
     * <P>
     * Note: Since <code>PreparedStatement</code> objects have usually been
     * parsed prior to making this call, disabling escape processing for
     * prepared statements will likely have no effect.
     *
     * <p>
     *  设置为给定的<code> boolean </code>是否在将SQL语句发送到数据库之前驱动程序是否将扫描转义语法并执行转义替换默认为驱动程序执行转义处理
     * <P>
     *  注意：因为<code> PreparedStatement </code>对象通常在进行调用之前被解析,所以禁止准备语句的转义处理可能没有效果
     * 
     * 
     * @param enable <code>true</code> to enable escape processing;
     *     <code>false</code> to disable it
     * @throws SQLException if an error occurs setting the underlying JDBC
     * technology-enabled driver to process the escape syntax
     */
    public void setEscapeProcessing(boolean enable) throws SQLException {
        escapeProcessing = enable;
    }

    /**
     * Retrieves the maximum number of seconds the driver will wait for a
     * query to execute. If the limit is exceeded, an <code>SQLException</code>
     * is thrown.
     *
     * <p>
     *  检索驱动程序等待执行查询的最大秒数如果超出限制,则抛出<code> SQLException </code>
     * 
     * 
     * @return the current query timeout limit in seconds; zero means that
     *     there is no limit
     * @throws SQLException if an error occurs in determining the query
     *     time-out value
     */
    public int getQueryTimeout() throws SQLException {
        return queryTimeout;
    }

    /**
     * Sets to the given number the maximum number of seconds the driver will
     * wait for a query to execute. If the limit is exceeded, an
     * <code>SQLException</code> is thrown.
     *
     * <p>
     * 设置给定的数字驱动程序将等待执行查询的最大秒数如果超出限制,则抛出<code> SQLException </code>
     * 
     * 
     * @param seconds the new query time-out limit in seconds; zero means that
     *     there is no limit; must not be less than zero
     * @throws SQLException if an error occurs setting the query
     *     time-out or if the query time-out value is less than 0
     */
    public void setQueryTimeout(int seconds) throws SQLException {
        if (seconds < 0) {
            throw new SQLException("Invalid query timeout value set. Cannot be " +
            "of value: " + seconds);
        }
        this.queryTimeout = seconds;
    }

    /**
     * Retrieves a <code>boolean</code> indicating whether rows marked
     * for deletion appear in the set of current rows.
     * The default value is <code>false</code>.
     * <P>
     * Note: Allowing deleted rows to remain visible complicates the behavior
     * of some of the methods.  However, most <code>RowSet</code> object users
     * can simply ignore this extra detail because only sophisticated
     * applications will likely want to take advantage of this feature.
     *
     * <p>
     *  检索<code> boolean </code>,指示标记为删除的行是否出现在当前行集中默认值为<code> false </code>
     * <P>
     *  注意：允许删除的行保持可见复杂的一些方法的行为但是,大多数<code> RowSet </code>对象用户可以简单地忽略这个额外的细节,因为只有复杂的应用程序可能想利用这个功能
     * 
     * 
     * @return <code>true</code> if deleted rows are visible;
     *         <code>false</code> otherwise
     * @throws SQLException if an error occurs determining if deleted rows
     * are visible or not
     * @see #setShowDeleted
     */
    public boolean getShowDeleted() throws SQLException {
        return showDeleted;
    }

    /**
     * Sets the property <code>showDeleted</code> to the given
     * <code>boolean</code> value, which determines whether
     * rows marked for deletion appear in the set of current rows.
     *
     * <p>
     *  将<code> showDeleted </code>属性设置为给定的<code> boolean </code>值,该值确定标记为删除的行是否显示在当前行集中
     * 
     * 
     * @param value <code>true</code> if deleted rows should be shown;
     *     <code>false</code> otherwise
     * @throws SQLException if an error occurs setting whether deleted
     *     rows are visible or not
     * @see #getShowDeleted
     */
    public void setShowDeleted(boolean value) throws SQLException {
        showDeleted = value;
    }

    /**
     * Ascertains whether escape processing is enabled for this
     * <code>RowSet</code> object.
     *
     * <p>
     * 确定是否为此<code> RowSet </code>对象启用了转义处理
     * 
     * 
     * @return <code>true</code> if escape processing is turned on;
     *         <code>false</code> otherwise
     * @throws SQLException if an error occurs determining if escape
     *     processing is enabled or not or if the internal escape
     *     processing trigger has not been enabled
     */
    public boolean getEscapeProcessing() throws SQLException {
        return escapeProcessing;
    }

    /**
     * Gives the driver a performance hint as to the direction in
     * which the rows in this <code>RowSet</code> object will be
     * processed.  The driver may ignore this hint.
     * <P>
     * A <code>RowSet</code> object inherits the default properties of the
     * <code>ResultSet</code> object from which it got its data.  That
     * <code>ResultSet</code> object's default fetch direction is set by
     * the <code>Statement</code> object that created it.
     * <P>
     * This method applies to a <code>RowSet</code> object only while it is
     * connected to a database using a JDBC driver.
     * <p>
     * A <code>RowSet</code> object may use this method at any time to change
     * its setting for the fetch direction.
     *
     * <p>
     *  向驱动程序提供关于此<code> RowSet </code>对象中的行将被处理的方向的性能提示驱动程序可以忽略此提示
     * <P>
     *  <code> RowSet </code>对象继承了从中获得其数据的<code> ResultSet </code>对象的默认属性<code> ResultSet </code>对象的默认提取方向由<code>
     *  > Statement </code>对象。
     * <P>
     *  仅当使用JDBC驱动程序将其连接到数据库时,此方法才适用于<code> RowSet </code>对象
     * <p>
     *  <code> RowSet </code>对象可以随时使用此方法来更改其提取方向的设置
     * 
     * 
     * @param direction one of <code>ResultSet.FETCH_FORWARD</code>,
     *                  <code>ResultSet.FETCH_REVERSE</code>, or
     *                  <code>ResultSet.FETCH_UNKNOWN</code>
     * @throws SQLException if (1) the <code>RowSet</code> type is
     *     <code>TYPE_FORWARD_ONLY</code> and the given fetch direction is not
     *     <code>FETCH_FORWARD</code> or (2) the given fetch direction is not
     *     one of the following:
     *        ResultSet.FETCH_FORWARD,
     *        ResultSet.FETCH_REVERSE, or
     *        ResultSet.FETCH_UNKNOWN
     * @see #getFetchDirection
     */
    public void setFetchDirection(int direction) throws SQLException {
        // Changed the condition checking to the below as there were two
        // conditions that had to be checked
        // 1. RowSet is TYPE_FORWARD_ONLY and direction is not FETCH_FORWARD
        // 2. Direction is not one of the valid values

        if (((getType() == ResultSet.TYPE_FORWARD_ONLY) && (direction != ResultSet.FETCH_FORWARD)) ||
            ((direction != ResultSet.FETCH_FORWARD) &&
            (direction != ResultSet.FETCH_REVERSE) &&
            (direction != ResultSet.FETCH_UNKNOWN))) {
            throw new SQLException("Invalid Fetch Direction");
        }
        fetchDir = direction;
    }

    /**
     * Retrieves this <code>RowSet</code> object's current setting for the
     * fetch direction. The default type is <code>ResultSet.FETCH_FORWARD</code>
     *
     * <p>
     * 检索这个<code> RowSet </code>对象的获取方向的当前设置默认类型是<code> ResultSetFETCH_FORWARD </code>
     * 
     * 
     * @return one of <code>ResultSet.FETCH_FORWARD</code>,
     *                  <code>ResultSet.FETCH_REVERSE</code>, or
     *                  <code>ResultSet.FETCH_UNKNOWN</code>
     * @throws SQLException if an error occurs in determining the
     *     current fetch direction for fetching rows
     * @see #setFetchDirection
     */
    public int getFetchDirection() throws SQLException {

        //Added the following code to throw a
        //SQL Exception if the fetchDir is not
        //set properly.Bug id:4914155

        // This checking is not necessary!

        /*
         if((fetchDir != ResultSet.FETCH_FORWARD) &&
           (fetchDir != ResultSet.FETCH_REVERSE) &&
           (fetchDir != ResultSet.FETCH_UNKNOWN)) {
            throw new SQLException("Fetch Direction Invalid");
         }
        /* <p>
        /*  if((fetchDir！= ResultSetFETCH_FORWARD)&&(fetchDir！= ResultSetFETCH_REVERSE)&&(fetchDir！= ResultSetFE
        /* TCH_UNKNOWN)){throw new SQLException("Fetch Direction Invalid"); }}。
        /* 
         */
        return (fetchDir);
    }

    /**
     * Sets the fetch size for this <code>RowSet</code> object to the given number of
     * rows.  The fetch size gives a JDBC technology-enabled driver ("JDBC driver")
     * a hint as to the
     * number of rows that should be fetched from the database when more rows
     * are needed for this <code>RowSet</code> object. If the fetch size specified
     * is zero, the driver ignores the value and is free to make its own best guess
     * as to what the fetch size should be.
     * <P>
     * A <code>RowSet</code> object inherits the default properties of the
     * <code>ResultSet</code> object from which it got its data.  That
     * <code>ResultSet</code> object's default fetch size is set by
     * the <code>Statement</code> object that created it.
     * <P>
     * This method applies to a <code>RowSet</code> object only while it is
     * connected to a database using a JDBC driver.
     * For connected <code>RowSet</code> implementations such as
     * <code>JdbcRowSet</code>, this method has a direct and immediate effect
     * on the underlying JDBC driver.
     * <P>
     * A <code>RowSet</code> object may use this method at any time to change
     * its setting for the fetch size.
     * <p>
     * For <code>RowSet</code> implementations such as
     * <code>CachedRowSet</code>, which operate in a disconnected environment,
     * the <code>SyncProvider</code> object being used
     * may leverage the fetch size to poll the data source and
     * retrieve a number of rows that do not exceed the fetch size and that may
     * form a subset of the actual rows returned by the original query. This is
     * an implementation variance determined by the specific <code>SyncProvider</code>
     * object employed by the disconnected <code>RowSet</code> object.
     * <P>
     *
     * <p>
     *  将此<code> RowSet </code>对象的提取大小设置为给定的行数提取大小为启用JDBC技术的驱动程序("JDBC驱动程序")提供了应提取的行数的提示数据库当此<code> RowSet </code>
     * 对象需要更多行时如果指定的提取大小为零,驱动程序将忽略该值,并且可以自由最大程度地猜测获取大小应该是什么。
     * <P>
     * <code> RowSet </code>对象继承了从中获取其数据的<code> ResultSet </code>对象的默认属性<code> ResultSet </code>对象的默认抓取大小由<code>
     *  > Statement </code>对象。
     * <P>
     *  此方法仅在使用JDBC驱动程序连接到数据库时适用于<code> RowSet </code>对象对于连接的<code> RowSet </code>实现(如<code> JdbcRowSet </code>
     * ),此方法对底层JDBC驱动程序有直接和即时的影响。
     * <P>
     *  <code> RowSet </code>对象可以随时使用此方法更改其获取大小的设置
     * <p>
     * 对于在断开的环境中操作的<code> RowSet </code>实现(例如<code> CachedRowSet </code>),正在使用的<code> SyncProvider </code>对象
     * 可以利用提取大小轮询数据源并检索不超过提取大小的多个行,并且可以形成原始查询返回的实际行的子集这是由断开连接所使用的特定<code> SyncProvider </code>对象确定的实现方差<code>
     *  RowSet </code>对象。
     * <P>
     * 
     * 
     * @param rows the number of rows to fetch; <code>0</code> to let the
     *        driver decide what the best fetch size is; must not be less
     *        than <code>0</code> or more than the maximum number of rows
     *        allowed for this <code>RowSet</code> object (the number returned
     *        by a call to the method {@link #getMaxRows})
     * @throws SQLException if the specified fetch size is less than <code>0</code>
     *        or more than the limit for the maximum number of rows
     * @see #getFetchSize
     */
    public void setFetchSize(int rows) throws SQLException {
        //Added this checking as maxRows can be 0 when this function is called
        //maxRows = 0 means rowset can hold any number of rows, os this checking
        // is needed to take care of this condition.
        if (getMaxRows() == 0 && rows >= 0)  {
            fetchSize = rows;
            return;
        }
        if ((rows < 0) || (rows > getMaxRows())) {
            throw new SQLException("Invalid fetch size set. Cannot be of " +
            "value: " + rows);
        }
        fetchSize = rows;
    }

    /**
     * Returns the fetch size for this <code>RowSet</code> object. The default
     * value is zero.
     *
     * <p>
     *  返回此<> RowSet </code>对象的提取大小默认值为零
     * 
     * 
     * @return the number of rows suggested as the fetch size when this <code>RowSet</code> object
     *     needs more rows from the database
     * @throws SQLException if an error occurs determining the number of rows in the
     *     current fetch size
     * @see #setFetchSize
     */
    public int getFetchSize() throws SQLException {
        return fetchSize;
    }

    /**
     * Returns the concurrency for this <code>RowSet</code> object.
     * The default is <code>CONCUR_UPDATABLE</code> for both connected and
     * disconnected <code>RowSet</code> objects.
     * <P>
     * An application can call the method <code>setConcurrency</code> at any time
     * to change a <code>RowSet</code> object's concurrency.
     * <p>
     * <p>
     *  返回此<> RowSet </code>对象的并发性对于连接和断开的<code> RowSet </code>对象,默认值为<code> CONCUR_UPDATABLE </code>
     * <P>
     * 应用程序可以随时调用方法<code> setConcurrency </code>来更改<code> RowSet </code>对象的并发
     * <p>
     * 
     * @return the concurrency type for this <code>RowSet</code>
     *     object, which must be one of the following:
     *     <code>ResultSet.CONCUR_READ_ONLY</code> or
     *     <code>ResultSet.CONCUR_UPDATABLE</code>
     * @throws SQLException if an error occurs getting the concurrency
     *     of this <code>RowSet</code> object
     * @see #setConcurrency
     * @see #isReadOnly
     */
    public int getConcurrency() throws SQLException {
        return concurrency;
    }

    //-----------------------------------------------------------------------
    // Parameters
    //-----------------------------------------------------------------------

    /**
     * Checks the given index to see whether it is less than <code>1</code> and
     * throws an <code>SQLException</code> object if it is.
     * <P>
     * This method is called by many methods internally; it is never
     * called by an application directly.
     *
     * <p>
     *  检查给定的索引,以查看它是否小于<code> 1 </code>并抛出一个<code> SQLException </code>对象,如果它是
     * <P>
     *  这个方法在内部被许多方法调用;它从不被应用程序直接调用
     * 
     * 
     * @param idx an <code>int</code> indicating which parameter is to be
     *     checked; the first parameter is <code>1</code>
     * @throws SQLException if the parameter is less than <code>1</code>
     */
    private void checkParamIndex(int idx) throws SQLException {
        if ((idx < 1)) {
            throw new SQLException("Invalid Parameter Index");
        }
    }

    //---------------------------------------------------------------------
    // setter methods for setting the parameters in a <code>RowSet</code> object's command
    //---------------------------------------------------------------------

    /**
     * Sets the designated parameter to SQL <code>NULL</code>.
     * Note that the parameter's SQL type must be specified using one of the
         * type codes defined in <code>java.sql.Types</code>.  This SQL type is
     * specified in the second parameter.
     * <p>
     * Note that the second parameter tells the DBMS the data type of the value being
     * set to <code>NULL</code>. Some DBMSs require this information, so it is required
     * in order to make code more portable.
     * <P>
     * The parameter value set by this method is stored internally and
     * will be supplied as the appropriate parameter in this <code>RowSet</code>
     * object's command when the method <code>execute</code> is called.
     * Methods such as <code>execute</code> and <code>populate</code> must be
     * provided in any class that extends this class and implements one or
     * more of the standard JSR-114 <code>RowSet</code> interfaces.
     * <P>
     * NOTE: <code>JdbcRowSet</code> does not require the <code>populate</code> method
     * as it is undefined in this class.
     * <P>
     * Calls made to the method <code>getParams</code> after this version of
     * <code>setNull</code>
     * has been called will return an <code>Object</code> array containing the parameter values that
     * have been set.  In that array, the element that represents the values
     * set with this method will itself be an array. The first element of that array
     * is <code>null</code>.
     * The second element is the value set for <i>sqlType</i>.
     * The parameter number is indicated by an element's position in the array
     * returned by the method <code>getParams</code>,
     * with the first element being the value for the first placeholder parameter, the
     * second element being the value for the second placeholder parameter, and so on.
     * In other words, if the second placeholder parameter is being set to
     * <code>null</code>, the array containing it will be the second element in
     * the array returned by <code>getParams</code>.
     * <P>
     * Note that because the numbering of elements in an array starts at zero,
     * the array element that corresponds to placeholder parameter number
     * <i>parameterIndex</i> is <i>parameterIndex</i> -1.
     *
     * <p>
     *  将指定的参数设置为SQL <code> NULL </code>请注意,参数的SQL类型必须使用<code> javasqlTypes </code>中定义的类型代码之一指定。
     * 此SQL类型在第二个参数中指定。
     * <p>
     *  注意,第二个参数告诉DBMS设置为<code> NULL </code>的值的数据类型有些DBMS需要这些信息,因此它是为了使代码更便携
     * <P>
     * 当方法<code> execute </code>被调用时,此方法设置的参数值被内部存储,并将作为<code> RowSet </code>对象的命令中的适当参数提供。
     * 必须在扩展此类并实现一个或多个标准JSR-114 <code> RowSet </code>接口的任何类中提供</code>和<code> populate </code>。
     * <P>
     *  注意：<code> JdbcRowSet </code>不需要<code> populate </code>方法,因为它在此类中未定义
     * <P>
     * 在<code> setNull </code>的此版本被调用后,对方法<code> getParams </code>的调用将返回一个包含已设置的参数值的<code> Object </code>数组数
     * 组,表示使用此方法设置的值的元素本身将是一个数组该数组的第一个元素是<code> null </code>第二个元素是<i> sqlType </i>数字由元素在由<code> getParams </code>
     * 方法返回的数组中的位置指示,第一个元素是第一个占位符参数的值,第二个元素是第二个占位符参数的值,因此上换句话说,如果第二个占位符参数设置为<code> null </code>,包含它的数组将是<code>
     *  getParams </code>返回的数组中的第二个元素。
     * <P>
     * 请注意,由于数组中元素的编号从零开始,与占位符参数编号<parameter> </i>对应的数组元素为<i> parameterIndex </i> -1
     * 
     * 
     * @param parameterIndex the ordinal number of the placeholder parameter
     *        in this <code>RowSet</code> object's command that is to be set.
     *        The first parameter is 1, the second is 2, and so on; must be
     *        <code>1</code> or greater
     * @param sqlType an <code>int</code> that is one of the SQL type codes
     *        defined in the class {@link java.sql.Types}. If a non-standard
     *        <i>sqlType</i> is supplied, this method will not throw a
     *        <code>SQLException</code>. This allows implicit support for
     *        non-standard SQL types.
     * @throws SQLException if a database access error occurs or the given
     *        parameter index is out of bounds
     * @see #getParams
     */
    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        Object nullVal[];
        checkParamIndex(parameterIndex);

        nullVal = new Object[2];
        nullVal[0] = null;
        nullVal[1] = Integer.valueOf(sqlType);

       if (params == null){
            throw new SQLException("Set initParams() before setNull");
       }

        params.put(Integer.valueOf(parameterIndex - 1), nullVal);
    }

    /**
     * Sets the designated parameter to SQL <code>NULL</code>.
     *
     * Although this version of the  method <code>setNull</code> is intended
     * for user-defined
     * and <code>REF</code> parameters, this method may be used to set a null
     * parameter for any JDBC type. The following are user-defined types:
     * <code>STRUCT</code>, <code>DISTINCT</code>, and <code>JAVA_OBJECT</code>,
     * and named array types.
     *
     * <P><B>Note:</B> To be portable, applications must give the
     * SQL type code and the fully qualified SQL type name when specifying
     * a <code>NULL</code> user-defined or <code>REF</code> parameter.
     * In the case of a user-defined type, the name is the type name of
     * the parameter itself.  For a <code>REF</code> parameter, the name is
     * the type name of the referenced type.  If a JDBC technology-enabled
     * driver does not need the type code or type name information,
     * it may ignore it.
     * <P>
     * If the parameter does not have a user-defined or <code>REF</code> type,
     * the given <code>typeName</code> parameter is ignored.
     * <P>
     * The parameter value set by this method is stored internally and
     * will be supplied as the appropriate parameter in this <code>RowSet</code>
     * object's command when the method <code>execute</code> is called.
     * Methods such as <code>execute</code> and <code>populate</code> must be
     * provided in any class that extends this class and implements one or
     * more of the standard JSR-114 <code>RowSet</code> interfaces.
     * <P>
     * NOTE: <code>JdbcRowSet</code> does not require the <code>populate</code> method
     * as it is undefined in this class.
     * <P>
     * Calls made to the method <code>getParams</code> after this version of
     * <code>setNull</code>
     * has been called will return an <code>Object</code> array containing the parameter values that
     * have been set.  In that array, the element that represents the values
     * set with this method will itself be an array. The first element of that array
     * is <code>null</code>.
     * The second element is the value set for <i>sqlType</i>, and the third
     * element is the value set for <i>typeName</i>.
     * The parameter number is indicated by an element's position in the array
     * returned by the method <code>getParams</code>,
     * with the first element being the value for the first placeholder parameter, the
     * second element being the value for the second placeholder parameter, and so on.
     * In other words, if the second placeholder parameter is being set to
     * <code>null</code>, the array containing it will be the second element in
     * the array returned by <code>getParams</code>.
     * <P>
     * Note that because the numbering of elements in an array starts at zero,
     * the array element that corresponds to placeholder parameter number
     * <i>parameterIndex</i> is <i>parameterIndex</i> -1.
     *
     * <p>
     *  将指定的参数设置为SQL <code> NULL </code>
     * 
     *  虽然此版本的方法<code> setNull </code>适用于用户定义和<code> REF </code>参数,但此方法可用于为任何JDBC类型设置空参数以下是用户定义的定义的类型：<code>
     *  STRUCT </code>,<code> DISTINCT </code>和<code> JAVA_OBJECT </code>。
     * 
     * <P> <B>注意：</B>为了便于移植,应用程序在指定<code> NULL </code>用户定义或<code> REF <时,必须提供SQL类型代码和完全限定的SQL类型名称。
     *  / code>参数在用户定义类型的情况下,名称是参数本身的类型名称对于<code> REF </code>参数,名称是引用类型的类型名称如果使用JDBC技术-enabled驱动程序不需要类型代码或类型
     * 名称信息,它可能会忽略它。
     * <P> <B>注意：</B>为了便于移植,应用程序在指定<code> NULL </code>用户定义或<code> REF <时,必须提供SQL类型代码和完全限定的SQL类型名称。
     * <P>
     *  如果参数没有用户定义或<code> REF </code>类型,则会忽略给定的<code> typeName </code>参数
     * <P>
     * 当方法<code> execute </code>被调用时,此方法设置的参数值被内部存储,并将作为<code> RowSet </code>对象的命令中的适当参数提供。
     * 必须在扩展此类并实现一个或多个标准JSR-114 <code> RowSet </code>接口的任何类中提供</code>和<code> populate </code>。
     * <P>
     *  注意：<code> JdbcRowSet </code>不需要<code> populate </code>方法,因为它在此类中未定义
     * <P>
     * 在<code> setNull </code>的此版本被调用后,对方法<code> getParams </code>的调用将返回一个包含已设置的参数值的<code> Object </code>数组数
     * 组,表示使用此方法设置的值的元素本身将是一个数组该数组的第一个元素是<code> null </code>第二个元素是为<i> sqlType </i>设置的值,第三个元素是为<i> typeName 
     * </i>设置的值。
     * 参数号由方法<code> getParams </code>返回的数组中元素的位置指示,第一个元素是第一占位符参数,第二元素是第二占位符参数的值,等等换句话说,如果第二个占位符参数设置为<code> n
     * ull </code>,包含它的数组将是<code> getParams </code>返回的数组中的第二个元素。
     * <P>
     * 请注意,由于数组中元素的编号从零开始,与占位符参数编号<parameter> </i>对应的数组元素为<i> parameterIndex </i> -1
     * 
     * 
     * @param parameterIndex the ordinal number of the placeholder parameter
     *        in this <code>RowSet</code> object's command that is to be set.
     *        The first parameter is 1, the second is 2, and so on; must be
     *        <code>1</code> or greater
     * @param sqlType a value from <code>java.sql.Types</code>
     * @param typeName the fully qualified name of an SQL user-defined type,
     *                 which is ignored if the parameter is not a user-defined
     *                 type or <code>REF</code> value
     * @throws SQLException if an error occurs or the given parameter index
     *            is out of bounds
     * @see #getParams
     */
    public void setNull(int parameterIndex, int sqlType, String typeName)
        throws SQLException {

        Object nullVal[];
        checkParamIndex(parameterIndex);

        nullVal = new Object[3];
        nullVal[0] = null;
        nullVal[1] = Integer.valueOf(sqlType);
        nullVal[2] = typeName;

       if(params == null){
            throw new SQLException("Set initParams() before setNull");
       }

        params.put(Integer.valueOf(parameterIndex - 1), nullVal);
    }


    /**
     * Sets the designated parameter to the given <code>boolean</code> in the
     * Java programming language.  The driver converts this to an SQL
     * <code>BIT</code> value when it sends it to the database.
     * <P>
     * The parameter value set by this method is stored internally and
     * will be supplied as the appropriate parameter in this <code>RowSet</code>
     * object's command when the method <code>execute</code> is called.
     * Methods such as <code>execute</code>, <code>populate</code> must be
     * provided in any class that extends this class and implements one or
     * more of the standard JSR-114 <code>RowSet</code> interfaces.
     * <p>
     * NOTE: <code>JdbcRowSet</code> does not require the <code>populate</code> method
     * as it is undefined in this class.
     *
     * <p>
     *  将指定的参数设置为Java编程语言中给定的<code> boolean </code>驱动程序将其发送到数据库时将其转换为SQL <code> BIT </code>
     * <P>
     *  当方法<code> execute </code>被调用时,此方法设置的参数值被内部存储,并将作为<code> RowSet </code>对象的命令中的适当参数提供。
     * 必须在扩展此类并实现一个或多个标准JSR-114 <code> RowSet </code>接口的任何类中提供</code>,<code> populate </code>。
     * <p>
     * 注意：<code> JdbcRowSet </code>不需要<code> populate </code>方法,因为它在此类中未定义
     * 
     * 
     * @param parameterIndex the ordinal number of the placeholder parameter
     *        in this <code>RowSet</code> object's command that is to be set.
     *        The first parameter is 1, the second is 2, and so on; must be
     *        <code>1</code> or greater
     * @param x the parameter value
     * @throws SQLException if an error occurs or the
     *                         parameter index is out of bounds
     * @see #getParams
     */
    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        checkParamIndex(parameterIndex);

       if(params == null){
            throw new SQLException("Set initParams() before setNull");
       }

        params.put(Integer.valueOf(parameterIndex - 1), Boolean.valueOf(x));
    }

    /**
     * Sets the designated parameter to the given <code>byte</code> in the Java
     * programming language.  The driver converts this to an SQL
     * <code>TINYINT</code> value when it sends it to the database.
     * <P>
     * The parameter value set by this method is stored internally and
     * will be supplied as the appropriate parameter in this <code>RowSet</code>
     * object's command when the method <code>execute</code> is called.
     * Methods such as <code>execute</code> and <code>populate</code> must be
     * provided in any class that extends this class and implements one or
     * more of the standard JSR-114 <code>RowSet</code> interfaces.
     * <p>
     * NOTE: <code>JdbcRowSet</code> does not require the <code>populate</code> method
     * as it is undefined in this class.
     *
     * <p>
     *  将指定的参数设置为Java编程语言中给定的<code> byte </code>。驱动程序在将其发送到数据库时将其转换为SQL <code> TINYINT </code>
     * <P>
     *  当方法<code> execute </code>被调用时,此方法设置的参数值被内部存储,并将作为<code> RowSet </code>对象的命令中的适当参数提供。
     * 必须在扩展此类并实现一个或多个标准JSR-114 <code> RowSet </code>接口的任何类中提供</code>和<code> populate </code>。
     * <p>
     * 注意：<code> JdbcRowSet </code>不需要<code> populate </code>方法,因为它在此类中未定义
     * 
     * 
     * @param parameterIndex the ordinal number of the placeholder parameter
     *        in this <code>RowSet</code> object's command that is to be set.
     *        The first parameter is 1, the second is 2, and so on; must be
     *        <code>1</code> or greater
     * @param x the parameter value
     * @throws SQLException if an error occurs or the
     *                         parameter index is out of bounds
     * @see #getParams
     */
    public void setByte(int parameterIndex, byte x) throws SQLException {
        checkParamIndex(parameterIndex);

       if(params == null){
            throw new SQLException("Set initParams() before setByte");
       }

        params.put(Integer.valueOf(parameterIndex - 1), Byte.valueOf(x));
    }

    /**
     * Sets the designated parameter to the given <code>short</code> in the
     * Java programming language.  The driver converts this to an SQL
     * <code>SMALLINT</code> value when it sends it to the database.
     * <P>
     * The parameter value set by this method is stored internally and
     * will be supplied as the appropriate parameter in this <code>RowSet</code>
     * object's command when the method <code>execute</code> is called.
     * Methods such as <code>execute</code> and <code>populate</code> must be
     * provided in any class that extends this class and implements one or
     * more of the standard JSR-114 <code>RowSet</code> interfaces.
     * <p>
     * NOTE: <code>JdbcRowSet</code> does not require the <code>populate</code> method
     * as it is undefined in this class.
     * <p>
     * <p>
     *  将指定的参数设置为Java编程语言中给定的<code> short </code>。当它发送到数据库时,驱动程序将其转换为SQL <code> SMALLINT </code>
     * <P>
     *  当方法<code> execute </code>被调用时,此方法设置的参数值被内部存储,并将作为<code> RowSet </code>对象的命令中的适当参数提供。
     * 必须在扩展此类并实现一个或多个标准JSR-114 <code> RowSet </code>接口的任何类中提供</code>和<code> populate </code>。
     * <p>
     * 注意：<code> JdbcRowSet </code>不需要<code> populate </code>方法,因为它在此类中未定义
     * <p>
     * 
     * @param parameterIndex the ordinal number of the placeholder parameter
     *        in this <code>RowSet</code> object's command that is to be set.
     *        The first parameter is 1, the second is 2, and so on; must be
     *        <code>1</code> or greater
     * @param x the parameter value
     * @throws SQLException if an error occurs or the
     *                         parameter index is out of bounds
     * @see #getParams
     */
    public void setShort(int parameterIndex, short x) throws SQLException {
        checkParamIndex(parameterIndex);

        if(params == null){
             throw new SQLException("Set initParams() before setShort");
        }

        params.put(Integer.valueOf(parameterIndex - 1), Short.valueOf(x));
    }

    /**
     * Sets the designated parameter to an <code>int</code> in the Java
     * programming language.  The driver converts this to an SQL
     * <code>INTEGER</code> value when it sends it to the database.
     * <P>
     * The parameter value set by this method is stored internally and
     * will be supplied as the appropriate parameter in this <code>RowSet</code>
     * object's command when the method <code>execute</code> is called.
     * Methods such as <code>execute</code> and <code>populate</code> must be
     * provided in any class that extends this class and implements one or
     * more of the standard JSR-114 <code>RowSet</code> interfaces.
     * <P>
     * NOTE: <code>JdbcRowSet</code> does not require the <code>populate</code> method
     * as it is undefined in this class.
     *
     * <p>
     *  将指定的参数设置为Java编程语言中的<code> int </code>。驱动程序将其发送到数据库时将其转换为SQL <code> INTEGER </code>
     * <P>
     *  当方法<code> execute </code>被调用时,此方法设置的参数值被内部存储,并将作为<code> RowSet </code>对象的命令中的适当参数提供。
     * 必须在扩展此类并实现一个或多个标准JSR-114 <code> RowSet </code>接口的任何类中提供</code>和<code> populate </code>。
     * <P>
     * 注意：<code> JdbcRowSet </code>不需要<code> populate </code>方法,因为它在此类中未定义
     * 
     * 
     * @param parameterIndex the ordinal number of the placeholder parameter
     *        in this <code>RowSet</code> object's command that is to be set.
     *        The first parameter is 1, the second is 2, and so on; must be
     *        <code>1</code> or greater
     * @param x the parameter value
     * @throws SQLException if an error occurs or the
     *                         parameter index is out of bounds
     * @see #getParams
     */
    public void setInt(int parameterIndex, int x) throws SQLException {
        checkParamIndex(parameterIndex);
        if(params == null){
             throw new SQLException("Set initParams() before setInt");
        }
        params.put(Integer.valueOf(parameterIndex - 1), Integer.valueOf(x));
    }

    /**
     * Sets the designated parameter to the given <code>long</code> in the Java
     * programming language.  The driver converts this to an SQL
     * <code>BIGINT</code> value when it sends it to the database.
     * <P>
     * The parameter value set by this method is stored internally and
     * will be supplied as the appropriate parameter in this <code>RowSet</code>
     * object's command when the method <code>execute</code> is called.
     * Methods such as <code>execute</code> and <code>populate</code> must be
     * provided in any class that extends this class and implements one or
     * more of the standard JSR-114 <code>RowSet</code> interfaces.
     * <P>
     * NOTE: <code>JdbcRowSet</code> does not require the <code>populate</code> method
     * as it is undefined in this class.
     *
     * <p>
     *  将指定的参数设置为Java编程语言中给定的<code> long </code>。驱动程序将其发送到数据库时将其转换为SQL <code> BIGINT </code>
     * <P>
     *  当方法<code> execute </code>被调用时,此方法设置的参数值被内部存储,并将作为<code> RowSet </code>对象的命令中的适当参数提供。
     * 必须在扩展此类并实现一个或多个标准JSR-114 <code> RowSet </code>接口的任何类中提供</code>和<code> populate </code>。
     * <P>
     * 注意：<code> JdbcRowSet </code>不需要<code> populate </code>方法,因为它在此类中未定义
     * 
     * 
     * @param parameterIndex the ordinal number of the placeholder parameter
     *        in this <code>RowSet</code> object's command that is to be set.
     *        The first parameter is 1, the second is 2, and so on; must be
     *        <code>1</code> or greater
     * @param x the parameter value
     * @throws SQLException if an error occurs or the
     *                         parameter index is out of bounds
     * @see #getParams
     */
    public void setLong(int parameterIndex, long x) throws SQLException {
        checkParamIndex(parameterIndex);
        if(params == null){
             throw new SQLException("Set initParams() before setLong");
        }
        params.put(Integer.valueOf(parameterIndex - 1), Long.valueOf(x));
    }

    /**
     * Sets the designated parameter to the given <code>float</code> in the
     * Java programming language.  The driver converts this to an SQL
     * <code>FLOAT</code> value when it sends it to the database.
     * <P>
     * The parameter value set by this method is stored internally and
     * will be supplied as the appropriate parameter in this <code>RowSet</code>
     * object's command when the method <code>execute</code> is called.
     * Methods such as <code>execute</code> and <code>populate</code> must be
     * provided in any class that extends this class and implements one or
     * more of the standard JSR-114 <code>RowSet</code> interfaces.
     * <P>
     * NOTE: <code>JdbcRowSet</code> does not require the <code>populate</code> method
     * as it is undefined in this class.
     *
     * <p>
     *  将指定的参数设置为Java编程语言中的给定<code> float </code>当驱动程序将其发送到数据库时将其转换为SQL <code> FLOAT </code>值
     * <P>
     *  当方法<code> execute </code>被调用时,此方法设置的参数值被内部存储,并将作为<code> RowSet </code>对象的命令中的适当参数提供。
     * 必须在扩展此类并实现一个或多个标准JSR-114 <code> RowSet </code>接口的任何类中提供</code>和<code> populate </code>。
     * <P>
     * 注意：<code> JdbcRowSet </code>不需要<code> populate </code>方法,因为它在此类中未定义
     * 
     * 
     * @param parameterIndex the ordinal number of the placeholder parameter
     *        in this <code>RowSet</code> object's command that is to be set.
     *        The first parameter is 1, the second is 2, and so on; must be
     *        <code>1</code> or greater
     * @param x the parameter value
     * @throws SQLException if an error occurs or the
     *                         parameter index is out of bounds
     * @see #getParams
     */
    public void setFloat(int parameterIndex, float x) throws SQLException {
        checkParamIndex(parameterIndex);
        if(params == null){
             throw new SQLException("Set initParams() before setFloat");
        }
        params.put(Integer.valueOf(parameterIndex - 1), Float.valueOf(x));
    }

    /**
     * Sets the designated parameter to the given <code>double</code> in the
     * Java programming language.  The driver converts this to an SQL
     * <code>DOUBLE</code> value when it sends it to the database.
     * <P>
     * The parameter value set by this method is stored internally and
     * will be supplied as the appropriate parameter in this <code>RowSet</code>
     * object's command when the method <code>execute</code> is called.
     * Methods such as <code>execute</code> and <code>populate</code> must be
     * provided in any class that extends this class and implements one or
     * more of the standard JSR-114 <code>RowSet</code> interfaces.
     * <P>
     * NOTE: <code>JdbcRowSet</code> does not require the <code>populate</code> method
     * as it is undefined in this class.
     * S
     * <p>
     *  将指定的参数设置为Java编程语言中给定的<code> double </code>。驱动程序将其发送到数据库时将其转换为SQL <code> DOUBLE </code>
     * <P>
     *  当方法<code> execute </code>被调用时,此方法设置的参数值被内部存储,并将作为<code> RowSet </code>对象的命令中的适当参数提供。
     * 必须在扩展此类并实现一个或多个标准JSR-114 <code> RowSet </code>接口的任何类中提供</code>和<code> populate </code>。
     * <P>
     * 注意：<code> JdbcRowSet </code>不需要<code> populate </code>方法,因为它在此类S中未定义
     * 
     * 
     * @param parameterIndex the ordinal number of the placeholder parameter
     *        in this <code>RowSet</code> object's command that is to be set.
     *        The first parameter is 1, the second is 2, and so on; must be
     *        <code>1</code> or greater
     * @param x the parameter value
     * @throws SQLException if an error occurs or the
     *                         parameter index is out of bounds
     * @see #getParams
     */
    public void setDouble(int parameterIndex, double x) throws SQLException {
        checkParamIndex(parameterIndex);
        if(params == null){
             throw new SQLException("Set initParams() before setDouble");
        }
        params.put(Integer.valueOf(parameterIndex - 1), Double.valueOf(x));
    }

    /**
     * Sets the designated parameter to the given
     * <code>java.lang.BigDecimal</code> value.  The driver converts this to
     * an SQL <code>NUMERIC</code> value when it sends it to the database.
     * <P>
     * The parameter value set by this method is stored internally and
     * will be supplied as the appropriate parameter in this <code>RowSet</code>
     * object's command when the method <code>execute</code> is called.
     * Methods such as <code>execute</code> and <code>populate</code> must be
     * provided in any class that extends this class and implements one or
     * more of the standard JSR-114 <code>RowSet</code> interfaces.
     * <P>
     * Note: <code>JdbcRowSet</code> does not require the <code>populate</code> method
     * as it is undefined in this class.
     *
     * <p>
     *  将指定的参数设置为给定的<code> javalangBigDecimal </code>值驱动程序在将其发送到数据库时将其转换为SQL <code> NUMERIC </code>
     * <P>
     *  当方法<code> execute </code>被调用时,此方法设置的参数值被内部存储,并将作为<code> RowSet </code>对象的命令中的适当参数提供。
     * 必须在扩展此类并实现一个或多个标准JSR-114 <code> RowSet </code>接口的任何类中提供</code>和<code> populate </code>。
     * <P>
     * 注意：<code> JdbcRowSet </code>不需要<code> populate </code>方法,因为它在此类中未定义
     * 
     * 
     * @param parameterIndex the ordinal number of the placeholder parameter
     *        in this <code>RowSet</code> object's command that is to be set.
     *        The first parameter is 1, the second is 2, and so on; must be
     *        <code>1</code> or greater
     * @param x the parameter value
     * @throws SQLException if an error occurs or the
     *                         parameter index is out of bounds
     * @see #getParams
     */
    public void setBigDecimal(int parameterIndex, java.math.BigDecimal x) throws SQLException {
        checkParamIndex(parameterIndex);
        if(params == null){
             throw new SQLException("Set initParams() before setBigDecimal");
        }
        params.put(Integer.valueOf(parameterIndex - 1), x);
    }

    /**
     * Sets the designated parameter to the given <code>String</code>
     * value.  The driver converts this to an SQL
     * <code>VARCHAR</code> or <code>LONGVARCHAR</code> value
     * (depending on the argument's size relative to the driver's limits
     * on <code>VARCHAR</code> values) when it sends it to the database.
     * <P>
     * The parameter value set by this method is stored internally and
     * will be supplied as the appropriate parameter in this <code>RowSet</code>
     * object's command when the method <code>execute</code> is called.
     * Methods such as <code>execute</code> and <code>populate</code> must be
     * provided in any class that extends this class and implements one or
     * more of the standard JSR-114 <code>RowSet</code> interfaces.
     * <p>
     * NOTE: <code>JdbcRowSet</code> does not require the <code>populate</code> method
     * as it is undefined in this class.
     * <p>
     * <p>
     *  将指定的参数设置为给定的<code> String </code>值驱动程序将其转换为SQL <code> VARCHAR </code>或<code> LONGVARCHAR </code>值(取决
     * 于参数的大小驱动程序对<code> VARCHAR </code>值的限制),当它发送到数据库。
     * <P>
     * 当方法<code> execute </code>被调用时,此方法设置的参数值被内部存储,并将作为<code> RowSet </code>对象的命令中的适当参数提供。
     * 必须在扩展此类并实现一个或多个标准JSR-114 <code> RowSet </code>接口的任何类中提供</code>和<code> populate </code>。
     * <p>
     *  注意：<code> JdbcRowSet </code>不需要<code> populate </code>方法,因为它在此类中未定义
     * <p>
     * 
     * @param parameterIndex the ordinal number of the placeholder parameter
     *        in this <code>RowSet</code> object's command that is to be set.
     *        The first parameter is 1, the second is 2, and so on; must be
     *        <code>1</code> or greater
     * @param x the parameter value
     * @throws SQLException if an error occurs or the
     *                         parameter index is out of bounds
     * @see #getParams
     */
    public void setString(int parameterIndex, String x) throws SQLException {
        checkParamIndex(parameterIndex);
        if(params == null){
             throw new SQLException("Set initParams() before setString");
        }
        params.put(Integer.valueOf(parameterIndex - 1), x);
    }

    /**
     * Sets the designated parameter to the given array of bytes.
     * The driver converts this to an SQL
     * <code>VARBINARY</code> or <code>LONGVARBINARY</code> value
     * (depending on the argument's size relative to the driver's limits
     * on <code>VARBINARY</code> values) when it sends it to the database.
     * <P>
     * The parameter value set by this method is stored internally and
     * will be supplied as the appropriate parameter in this <code>RowSet</code>
     * object's command when the method <code>execute</code> is called.
     * Methods such as <code>execute</code> and <code>populate</code> must be
     * provided in any class that extends this class and implements one or
     * more of the standard JSR-114 <code>RowSet</code> interfaces.
     * <p>
     * NOTE: <code>JdbcRowSet</code> does not require the <code>populate</code> method
     * as it is undefined in this class.
     *
     * <p>
     * 将指定的参数设置为给定的字节数字驱动程序将其转换为SQL <code> VARBINARY </code>或<code> LONGVARBINARY </code>值(取决于参数的大小相对于驱动程序在<code>
     *  VARBINARY </code>值),当它发送到数据库。
     * <P>
     *  当方法<code> execute </code>被调用时,此方法设置的参数值被内部存储,并将作为<code> RowSet </code>对象的命令中的适当参数提供。
     * 必须在扩展此类并实现一个或多个标准JSR-114 <code> RowSet </code>接口的任何类中提供</code>和<code> populate </code>。
     * <p>
     * 注意：<code> JdbcRowSet </code>不需要<code> populate </code>方法,因为它在此类中未定义
     * 
     * 
     * @param parameterIndex the ordinal number of the placeholder parameter
     *        in this <code>RowSet</code> object's command that is to be set.
     *        The first parameter is 1, the second is 2, and so on; must be
     *        <code>1</code> or greater
     * @param x the parameter value
     * @throws SQLException if an error occurs or the
     *                         parameter index is out of bounds
     * @see #getParams
     */
    public void setBytes(int parameterIndex, byte x[]) throws SQLException {
        checkParamIndex(parameterIndex);
        if(params == null){
             throw new SQLException("Set initParams() before setBytes");
        }
        params.put(Integer.valueOf(parameterIndex - 1), x);
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.Date</code>
     * value. The driver converts this to an SQL
     * <code>DATE</code> value when it sends it to the database.
     * <P>
     * The parameter value set by this method is stored internally and
     * will be supplied as the appropriate parameter in this <code>RowSet</code>
     * object's command when the method <code>execute</code> is called.
     * Methods such as <code>execute</code> and <code>populate</code> must be
     * provided in any class that extends this class and implements one or
     * more of the standard JSR-114 <code>RowSet</code> interfaces.
     * <P>
     * NOTE: <code>JdbcRowSet</code> does not require the <code>populate</code> method
     * as it is undefined in this class.
     * <P>
     * Calls made to the method <code>getParams</code> after this version
     * of <code>setDate</code>
     * has been called will return an array with the value to be set for
     * placeholder parameter number <i>parameterIndex</i> being the <code>Date</code>
     * object supplied as the second parameter.
     * Note that because the numbering of elements in an array starts at zero,
     * the array element that corresponds to placeholder parameter number
     * <i>parameterIndex</i> is <i>parameterIndex</i> -1.
     *
     * <p>
     *  将指定的参数设置为给定的<code> javasqlDate </code>值驱动程序将其发送到数据库时将其转换为SQL <code> DATE </code>
     * <P>
     *  当方法<code> execute </code>被调用时,此方法设置的参数值被内部存储,并将作为<code> RowSet </code>对象的命令中的适当参数提供。
     * 必须在扩展此类并实现一个或多个标准JSR-114 <code> RowSet </code>接口的任何类中提供</code>和<code> populate </code>。
     * <P>
     * 注意：<code> JdbcRowSet </code>不需要<code> populate </code>方法,因为它在此类中未定义
     * <P>
     *  在<code> setDate </code>的此版本被调用后,对方法<code> getParams </code>的调用将返回一个数组,其值将为占位符参数number <i> parameterI
     * ndex </i>作为第二个参数提供的<code> Date </code>对象请注意,因为数组中元素的编号从零开始,所以与占位符参数number <i> parameterIndex </i>对应的数
     * 组元素为<i > parameterIndex </i> -1。
     * 
     * 
     * @param parameterIndex the ordinal number of the placeholder parameter
     *        in this <code>RowSet</code> object's command that is to be set.
     *        The first parameter is 1, the second is 2, and so on; must be
     *        <code>1</code> or greater
     * @param x the parameter value
     * @throws SQLException if an error occurs or the
     *                         parameter index is out of bounds
     * @see #getParams
     */
    public void setDate(int parameterIndex, java.sql.Date x) throws SQLException {
        checkParamIndex(parameterIndex);

        if(params == null){
             throw new SQLException("Set initParams() before setDate");
        }
        params.put(Integer.valueOf(parameterIndex - 1), x);
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.Time</code>
     * value.  The driver converts this to an SQL <code>TIME</code> value
     * when it sends it to the database.
     * <P>
     * The parameter value set by this method is stored internally and
     * will be supplied as the appropriate parameter in this <code>RowSet</code>
     * object's command when the method <code>execute</code> is called.
     * Methods such as <code>execute</code> and <code>populate</code> must be
     * provided in any class that extends this class and implements one or
     * more of the standard JSR-114 <code>RowSet</code> interfaces.
     * <P>
     * NOTE: <code>JdbcRowSet</code> does not require the <code>populate</code> method
     * as it is undefined in this class.
     * <P>
     * Calls made to the method <code>getParams</code> after this version
     * of the method <code>setTime</code>
     * has been called will return an array of the parameters that have been set.
     * The parameter to be set for parameter placeholder number <i>parameterIndex</i>
     * will be the <code>Time</code> object that was set as the second parameter
     * to this method.
     * <P>
     * Note that because the numbering of elements in an array starts at zero,
     * the array element that corresponds to placeholder parameter number
     * <i>parameterIndex</i> is <i>parameterIndex</i> -1.
     *
     * <p>
     *  将指定的参数设置为给定的<code> javasqlTime </code>值当驱动程序将其发送到数据库时,将其转换为SQL <code> TIME </code>
     * <P>
     * 当方法<code> execute </code>被调用时,此方法设置的参数值被内部存储,并将作为<code> RowSet </code>对象的命令中的适当参数提供。
     * 必须在扩展此类并实现一个或多个标准JSR-114 <code> RowSet </code>接口的任何类中提供</code>和<code> populate </code>。
     * <P>
     *  注意：<code> JdbcRowSet </code>不需要<code> populate </code>方法,因为它在此类中未定义
     * <P>
     * 已调用此方法<code> setTime </code>的方法后,对方法<code> getParams </code>的调用将返回已设置的参数数组要为参数占位符数<i> parameterIndex 
     * </i>将是设置为此方法的第二个参数的<code> Time </code>对象。
     * <P>
     *  请注意,由于数组中元素的编号从零开始,与占位符参数编号<parameter> </i>对应的数组元素为<i> parameterIndex </i> -1
     * 
     * 
     * @param parameterIndex the ordinal number of the placeholder parameter
     *        in this <code>RowSet</code> object's command that is to be set.
     *        The first parameter is 1, the second is 2, and so on; must be
     *        <code>1</code> or greater
     * @param x a <code>java.sql.Time</code> object, which is to be set as the value
     *              for placeholder parameter <i>parameterIndex</i>
     * @throws SQLException if an error occurs or the
     *                         parameter index is out of bounds
     * @see #getParams
     */
    public void setTime(int parameterIndex, java.sql.Time x) throws SQLException {
        checkParamIndex(parameterIndex);
        if(params == null){
             throw new SQLException("Set initParams() before setTime");
        }

        params.put(Integer.valueOf(parameterIndex - 1), x);
    }

    /**
     * Sets the designated parameter to the given
     * <code>java.sql.Timestamp</code> value.
     * The driver converts this to an SQL <code>TIMESTAMP</code> value when it
     * sends it to the database.
     * <P>
     * The parameter value set by this method is stored internally and
     * will be supplied as the appropriate parameter in this <code>RowSet</code>
     * object's command when the method <code>execute</code> is called.
     * Methods such as <code>execute</code> and <code>populate</code> must be
     * provided in any class that extends this class and implements one or
     * more of the standard JSR-114 <code>RowSet</code> interfaces.
     * <P>
     * NOTE: <code>JdbcRowSet</code> does not require the <code>populate</code> method
     * as it is undefined in this class.
     * <P>
     * Calls made to the method <code>getParams</code> after this version of
     * <code>setTimestamp</code>
     * has been called will return an array with the value for parameter placeholder
     * number <i>parameterIndex</i> being the <code>Timestamp</code> object that was
     * supplied as the second parameter to this method.
     * Note that because the numbering of elements in an array starts at zero,
     * the array element that corresponds to placeholder parameter number
     * <i>parameterIndex</i> is <i>parameterIndex</i> -1.
     *
     * <p>
     *  将指定的参数设置为给定的<code> javasqlTimestamp </code>值当驱动程序将其发送到数据库时,将其转换为SQL <code> TIMESTAMP </code>
     * <P>
     * 当方法<code> execute </code>被调用时,此方法设置的参数值被内部存储,并将作为<code> RowSet </code>对象的命令中的适当参数提供。
     * 必须在扩展此类并实现一个或多个标准JSR-114 <code> RowSet </code>接口的任何类中提供</code>和<code> populate </code>。
     * <P>
     *  注意：<code> JdbcRowSet </code>不需要<code> populate </code>方法,因为它在此类中未定义
     * <P>
     * 已调用此版本的<code> setTimestamp </code>之后对<code> getParams </code>方法的调用将返回一个数组,其参数占位符号<i> parameterIndex </i>
     * 代码>作为此方法的第二个参数提供的Timestamp </code>对象请注意,由于数组中元素的编号从零开始,与占位符参数number <parameter> </i>对应的数组元素为<i> param
     * eterIndex </i> -1。
     * 
     * 
     * @param parameterIndex the ordinal number of the placeholder parameter
     *        in this <code>RowSet</code> object's command that is to be set.
     *        The first parameter is 1, the second is 2, and so on; must be
     *        <code>1</code> or greater
     * @param x a <code>java.sql.Timestamp</code> object
     * @throws SQLException if an error occurs or the
     *                         parameter index is out of bounds
     * @see #getParams
     */
    public void setTimestamp(int parameterIndex, java.sql.Timestamp x) throws SQLException {
        checkParamIndex(parameterIndex);
        if(params == null){
             throw new SQLException("Set initParams() before setTimestamp");
        }

        params.put(Integer.valueOf(parameterIndex - 1), x);
    }

    /**
     * Sets the designated parameter to the given
     * <code>java.io.InputStream</code> object,
     * which will have the specified number of bytes.
     * The contents of the stream will be read and sent to the database.
     * This method throws an <code>SQLException</code> object if the number of bytes
     * read and sent to the database is not equal to <i>length</i>.
     * <P>
     * When a very large ASCII value is input to a <code>LONGVARCHAR</code>
     * parameter, it may be more practical to send it via a
     * <code>java.io.InputStream</code> object. A JDBC technology-enabled
     * driver will read the data from the stream as needed until it reaches
     * end-of-file. The driver will do any necessary conversion from ASCII to
     * the database <code>CHAR</code> format.
     *
     * <P><B>Note:</B> This stream object can be either a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     * <P>
     * The parameter value set by this method is stored internally and
     * will be supplied as the appropriate parameter in this <code>RowSet</code>
     * object's command when the method <code>execute</code> is called.
     * Methods such as <code>execute</code> and <code>populate</code> must be
     * provided in any class that extends this class and implements one or
     * more of the standard JSR-114 <code>RowSet</code> interfaces.
     * <P>
     * Note: <code>JdbcRowSet</code> does not require the <code>populate</code> method
     * as it is undefined in this class.
     * <P>
     * Calls made to the method <code>getParams</code> after <code>setAsciiStream</code>
     * has been called will return an array containing the parameter values that
     * have been set.  The element in the array that represents the values
     * set with this method will itself be an array. The first element of that array
     * is the given <code>java.io.InputStream</code> object.
     * The second element is the value set for <i>length</i>.
     * The third element is an internal <code>BaseRowSet</code> constant
     * specifying that the stream passed to this method is an ASCII stream.
     * The parameter number is indicated by an element's position in the array
     * returned by the method <code>getParams</code>,
     * with the first element being the value for the first placeholder parameter, the
     * second element being the value for the second placeholder parameter, and so on.
     * In other words, if the input stream being set is the value for the second
     * placeholder parameter, the array containing it will be the second element in
     * the array returned by <code>getParams</code>.
     * <P>
     * Note that because the numbering of elements in an array starts at zero,
     * the array element that corresponds to placeholder parameter number
     * <i>parameterIndex</i> is element number <i>parameterIndex</i> -1.
     *
     * <p>
     * 将指定的参数设置为给定的<code> javaioInputStream </code>对象,它将具有指定的字节数。
     * 流的内容将被读取并发送到数据库此方法会抛出一个<code> SQLException </code>对象,如果读取和发送到数据库的字节数不等于<i> length </i>。
     * <P>
     *  当一个非常大的ASCII值被输入到一个<code> LONGVARCHAR </code>参数时,通过一个<code> javaioInputStream </code>对象发送它可能更为实用一个支持
     * JDBC技术的驱动程序将从直到到达文件结束的驱动程序将执行从ASCII到数据库<code> CHAR </code>格式的任何必要的转换。
     * 
     * <P> <B>注意：</B>此流对象可以是标准Java流对象或实现标准接口的自己的子类
     * <P>
     *  当方法<code> execute </code>被调用时,此方法设置的参数值被内部存储,并将作为<code> RowSet </code>对象的命令中的适当参数提供。
     * 必须在扩展此类并实现一个或多个标准JSR-114 <code> RowSet </code>接口的任何类中提供</code>和<code> populate </code>。
     * <P>
     *  注意：<code> JdbcRowSet </code>不需要<code> populate </code>方法,因为它在此类中未定义
     * <P>
     * 在调用<code> setAsciiStream </code>之后对方法<code> getParams </code>的调用将返回包含已设置的参数值的数组。
     * 数组中表示使用此方法设置的值的元素它本身就是一个数组该数组的第一个元素是给定的<code> javaioInputStream </code>对象第二个元素是为<i> length </i>设置的值第三
     * 个元素是一个内部<code> BaseRowSet < / code>常量,指定传递给此方法的流是ASCII流参数号由方法<code> getParams </code>返回的数组中元素的位置表示,第一
     * 个元素是第一个占位符参数的值,第二个元素是第二个占位符参数的值,换句话说,如果被设置的输入流是第二个占位符参数的值,包含它的数组将是由<code> getParams </code>返回的数组中的第二个
     * 元素。
     * 在调用<code> setAsciiStream </code>之后对方法<code> getParams </code>的调用将返回包含已设置的参数值的数组。
     * <P>
     * 注意,因为数组中元素的编号从零开始,所以对应于占位符参数号<parameter> </i>的数组元素是元素编号<i> parameterIndex </i> -1
     * 
     * 
     * @param parameterIndex the ordinal number of the placeholder parameter
     *        in this <code>RowSet</code> object's command that is to be set.
     *        The first parameter is 1, the second is 2, and so on; must be
     *        <code>1</code> or greater
     * @param x the Java input stream that contains the ASCII parameter value
     * @param length the number of bytes in the stream. This is the number of bytes
     *       the driver will send to the DBMS; lengths of 0 or less are
     *       are undefined but will cause an invalid length exception to be
     *       thrown in the underlying JDBC driver.
     * @throws SQLException if an error occurs, the parameter index is out of bounds,
     *       or when connected to a data source, the number of bytes the driver reads
     *       and sends to the database is not equal to the number of bytes specified
     *       in <i>length</i>
     * @see #getParams
     */
    public void setAsciiStream(int parameterIndex, java.io.InputStream x, int length) throws SQLException {
        Object asciiStream[];
        checkParamIndex(parameterIndex);

        asciiStream = new Object[3];
        asciiStream[0] = x;
        asciiStream[1] = Integer.valueOf(length);
        asciiStream[2] = Integer.valueOf(ASCII_STREAM_PARAM);

        if(params == null){
             throw new SQLException("Set initParams() before setAsciiStream");
        }

        params.put(Integer.valueOf(parameterIndex - 1), asciiStream);
    }

  /**
   * Sets the designated parameter in this <code>RowSet</code> object's command
   * to the given input stream.
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
   *  将这个<code> RowSet </code>对象的命令中的指定参数设置为给定的输入流当将非常大的ASCII值输入到<code> LONGVARCHAR </code>参数时,通过a <code> 
   * javaioInputStream </code>将根据需要从流中读取数据,直到达到文件结束JDBC驱动程序将执行从ASCII到数据库字符格式的任何必要转换。
   * 
   * <P> <B>注意：</B>此流对象可以是标准Java流对象或实现标准接口<P> <B>的自己的子类注意：</B>请参阅您的JDBC驱动程序文档确定是否可能更有效地使用一个版本的<code> setAs
   * ciiStream </code>,它需要一个长度参数。
   * 
   * 
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param x the Java input stream that contains the ASCII parameter value
   * @exception SQLException if a database access error occurs or
   * this method is called on a closed <code>PreparedStatement</code>
   * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
   * @since 1.6
   */
  public void setAsciiStream(int parameterIndex, java.io.InputStream x)
                      throws SQLException {
      throw new SQLFeatureNotSupportedException("Feature not supported");
  }

    /**
     * Sets the designated parameter to the given <code>java.io.InputStream</code>
     * object, which will have the specified number of bytes.
     * The contents of the stream will be read and sent to the database.
     * This method throws an <code>SQLException</code> object if the number of bytes
     * read and sent to the database is not equal to <i>length</i>.
     * <P>
     * When a very large binary value is input to a
     * <code>LONGVARBINARY</code> parameter, it may be more practical
     * to send it via a <code>java.io.InputStream</code> object.
     * A JDBC technology-enabled driver will read the data from the
     * stream as needed until it reaches end-of-file.
     *
     * <P><B>Note:</B> This stream object can be either a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     * <P>
     * The parameter value set by this method is stored internally and
     * will be supplied as the appropriate parameter in this <code>RowSet</code>
     * object's command when the method <code>execute</code> is called.
     * Methods such as <code>execute</code> and <code>populate</code> must be
     * provided in any class that extends this class and implements one or
     * more of the standard JSR-114 <code>RowSet</code> interfaces.
     *<P>
     * NOTE: <code>JdbcRowSet</code> does not require the <code>populate</code> method
     * as it is undefined in this class.
     * <P>
     * Calls made to the method <code>getParams</code> after <code>setBinaryStream</code>
     * has been called will return an array containing the parameter values that
     * have been set.  In that array, the element that represents the values
     * set with this method will itself be an array. The first element of that array
     * is the given <code>java.io.InputStream</code> object.
     * The second element is the value set for <i>length</i>.
     * The third element is an internal <code>BaseRowSet</code> constant
     * specifying that the stream passed to this method is a binary stream.
     * The parameter number is indicated by an element's position in the array
     * returned by the method <code>getParams</code>,
     * with the first element being the value for the first placeholder parameter, the
     * second element being the value for the second placeholder parameter, and so on.
     * In other words, if the input stream being set is the value for the second
     * placeholder parameter, the array containing it will be the second element in
     * the array returned by <code>getParams</code>.
     * <P>
     * Note that because the numbering of elements in an array starts at zero,
     * the array element that corresponds to placeholder parameter number
     * <i>parameterIndex</i> is element number <i>parameterIndex</i> -1.
     *
     * <p>
     *  将指定的参数设置为给定的<code> javaioInputStream </code>对象,它将具有指定的字节数。
     * 流的内容将被读取并发送到数据库此方法会抛出一个<code> SQLException </code>对象,如果读取和发送到数据库的字节数不等于<i> length </i>。
     * <P>
     * 当一个非常大的二进制值被输入到一个<code> LONGVARBINARY </code>参数时,通过一个<code> javaioInputStream </code>对象发送它是更实际的一个支持JD
     * BC技术的驱动程序将读取数据流,直到它到达文件结束。
     * 
     *  <P> <B>注意：</B>此流对象可以是标准Java流对象或实现标准接口的自己的子类
     * <P>
     * 当方法<code> execute </code>被调用时,此方法设置的参数值被内部存储,并将作为<code> RowSet </code>对象的命令中的适当参数提供。
     * 必须在扩展此类并实现一个或多个标准JSR-114 <code> RowSet </code>接口的任何类中提供</code>和<code> populate </code>。
     * P>
     *  注意：<code> JdbcRowSet </code>不需要<code> populate </code>方法,因为它在此类中未定义
     * <P>
     * 在调用<code> setBinaryStream </code>之后对<code> getParams </code>方法的调用将返回一个包含已设置的参数值的数组。
     * 方法本身将是一个数组该数组的第一个元素是给定的<code> javaioInputStream </code>对象第二个元素是为<i> length </i>设置的值第三个元素是一个内部<code> B
     * aseRowSet </code>常量,指定传递给此方法的流是二进制流参数号由方法<code> getParams </code>返回的数组中元素的位置表示,第一个元素是第一个占位符参数的值,第二个元素
     * 是第二个占位符参数的值,换句话说,如果被设置的输入流是第二个占位符参数的值,包含它的数组将是由<code> getParams </code>返回的数组中的第二个元素。
     * 在调用<code> setBinaryStream </code>之后对<code> getParams </code>方法的调用将返回一个包含已设置的参数值的数组。
     * <P>
     * 注意,因为数组中元素的编号从零开始,所以对应于占位符参数号<parameter> </i>的数组元素是元素编号<i> parameterIndex </i> -1
     * 
     * 
     * @param parameterIndex the ordinal number of the placeholder parameter
     *        in this <code>RowSet</code> object's command that is to be set.
     *        The first parameter is 1, the second is 2, and so on; must be
     *        <code>1</code> or greater
     * @param x the input stream that contains the binary value to be set
     * @param length the number of bytes in the stream; lengths of 0 or less are
     *         are undefined but will cause an invalid length exception to be
     *         thrown in the underlying JDBC driver.
     * @throws SQLException if an error occurs, the parameter index is out of bounds,
     *         or when connected to a data source, the number of bytes the driver
     *         reads and sends to the database is not equal to the number of bytes
     *         specified in <i>length</i>
     * @see #getParams
     */
    public void setBinaryStream(int parameterIndex, java.io.InputStream x, int length) throws SQLException {
        Object binaryStream[];
        checkParamIndex(parameterIndex);

        binaryStream = new Object[3];
        binaryStream[0] = x;
        binaryStream[1] = Integer.valueOf(length);
        binaryStream[2] = Integer.valueOf(BINARY_STREAM_PARAM);
        if(params == null){
             throw new SQLException("Set initParams() before setBinaryStream");
        }

        params.put(Integer.valueOf(parameterIndex - 1), binaryStream);
    }


   /**
   * Sets the designated parameter in this <code>RowSet</code> object's command
   * to the given input stream.
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
   *  将这个<code> RowSet </code>对象的命令中的指定参数设置为给定的输入流当一个非常大的二进制值输入到<code> LONGVARBINARY </code>参数时, a <code> 
   * javaioInputStream </code>对象将根据需要从流中读取数据,直到达到文件结束。
   * 
   * <P> <B>注意：</B>此流对象可以是标准Java流对象或实现标准接口<P> <B>的自己的子类注意：</B>请参阅您的JDBC驱动程序文档确定是否使用一个带有长度参数的<code> setBina
   * ryStream </code>版本可能更有效。
   * 
   * 
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param x the java input stream which contains the binary parameter value
   * @exception SQLException if a database access error occurs or
   * this method is called on a closed <code>PreparedStatement</code>
   * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
   * @since 1.6
   */
  public void setBinaryStream(int parameterIndex, java.io.InputStream x)
                              throws SQLException {
      throw new SQLFeatureNotSupportedException("Feature not supported");
  }


    /**
     * Sets the designated parameter to the given
     * <code>java.io.InputStream</code> object, which will have the specified
     * number of bytes. The contents of the stream will be read and sent
     * to the database.
     * This method throws an <code>SQLException</code> if the number of bytes
     * read and sent to the database is not equal to <i>length</i>.
     * <P>
     * When a very large Unicode value is input to a
     * <code>LONGVARCHAR</code> parameter, it may be more practical
     * to send it via a <code>java.io.InputStream</code> object.
     * A JDBC technology-enabled driver will read the data from the
     * stream as needed, until it reaches end-of-file.
     * The driver will do any necessary conversion from Unicode to the
     * database <code>CHAR</code> format.
     * The byte format of the Unicode stream must be Java UTF-8, as
     * defined in the Java Virtual Machine Specification.
     *
     * <P><B>Note:</B> This stream object can be either a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     * <P>
     * This method is deprecated; the method <code>getCharacterStream</code>
     * should be used in its place.
     * <P>
     * The parameter value set by this method is stored internally and
     * will be supplied as the appropriate parameter in this <code>RowSet</code>
     * object's command when the method <code>execute</code> is called.
     * Calls made to the method <code>getParams</code> after <code>setUnicodeStream</code>
     * has been called will return an array containing the parameter values that
     * have been set.  In that array, the element that represents the values
     * set with this method will itself be an array. The first element of that array
     * is the given <code>java.io.InputStream</code> object.
     * The second element is the value set for <i>length</i>.
     * The third element is an internal <code>BaseRowSet</code> constant
     * specifying that the stream passed to this method is a Unicode stream.
     * The parameter number is indicated by an element's position in the array
     * returned by the method <code>getParams</code>,
     * with the first element being the value for the first placeholder parameter, the
     * second element being the value for the second placeholder parameter, and so on.
     * In other words, if the input stream being set is the value for the second
     * placeholder parameter, the array containing it will be the second element in
     * the array returned by <code>getParams</code>.
     * <P>
     * Note that because the numbering of elements in an array starts at zero,
     * the array element that corresponds to placeholder parameter number
     * <i>parameterIndex</i> is element number <i>parameterIndex</i> -1.
     *
     * <p>
     *  将指定的参数设置为给定的<code> javaioInputStream </code>对象,它将具有指定的字节数。
     * 流的内容将被读取并发送到数据库此方法会抛出一个<code> SQLException </code>如果读取和发送到数据库的字节数不等于<length> </i>。
     * <P>
     * 当将非常大的Unicode值输入到<code> LONGVARCHAR </code>参数时,通过<code> javaioInputStream </code>对象发送它可能更加实用。
     * 启用JDBC技术的驱动程序将从直到到达文件结尾驱动程序将执行从Unicode到数据库的所有必要转换<code> CHAR </code>格式Unicode流的字节格式必须是Java UTF-8,如定义的
     * 在Java虚拟机规范。
     * 当将非常大的Unicode值输入到<code> LONGVARCHAR </code>参数时,通过<code> javaioInputStream </code>对象发送它可能更加实用。
     * 
     *  <P> <B>注意：</B>此流对象可以是标准Java流对象或实现标准接口的自己的子类
     * <P>
     *  此方法已弃用;应该在其位置使用方法<code> getCharacterStream </code>
     * <P>
     * 通过此方法设置的参数值在内部存储,并且将作为<code> RowSet </code>对象的命令中的适当参数,当方法<code> execute </code>被调用时调用方法<code > getPa
     * rams </code>后,将返回一个包含已设置的参数值的数组在该数组中,表示使用此方法设置的值的元素本身将是一个数组该数组的第一个元素是给定的<code> javaioInputStream </code>
     * 对象第二个元素是为<i> length </i>设置的值第三个元素是内部的<code> BaseRowSet </code>常量,传递到此方法的流是Unicode流参数号由方法<code> getPar
     * ams </code>返回的数组中元素的位置表示,第一个元素是第一个占位符参数的值,第二个元素是第二个占位符参数的值,换句话说,如果被设置的输入流是第二个占位符参数的值,包含它的数组将是由<code> 
     * getParams </code>返回的数组中的第二个元素。
     * <P>
     * 注意,因为数组中元素的编号从零开始,所以对应于占位符参数号<parameter> </i>的数组元素是元素编号<i> parameterIndex </i> -1
     * 
     * 
     * @param parameterIndex the ordinal number of the placeholder parameter
     *        in this <code>RowSet</code> object's command that is to be set.
     *        The first parameter is 1, the second is 2, and so on; must be
     *        <code>1</code> or greater
     * @param x the <code>java.io.InputStream</code> object that contains the
     *          UNICODE parameter value
     * @param length the number of bytes in the input stream
     * @throws SQLException if an error occurs, the parameter index is out of bounds,
     *         or the number of bytes the driver reads and sends to the database is
     *         not equal to the number of bytes specified in <i>length</i>
     * @deprecated getCharacterStream should be used in its place
     * @see #getParams
     */
    @Deprecated
    public void setUnicodeStream(int parameterIndex, java.io.InputStream x, int length) throws SQLException {
        Object unicodeStream[];
        checkParamIndex(parameterIndex);

        unicodeStream = new Object[3];
        unicodeStream[0] = x;
        unicodeStream[1] = Integer.valueOf(length);
        unicodeStream[2] = Integer.valueOf(UNICODE_STREAM_PARAM);
        if(params == null){
             throw new SQLException("Set initParams() before setUnicodeStream");
        }
        params.put(Integer.valueOf(parameterIndex - 1), unicodeStream);
    }

    /**
     * Sets the designated parameter to the given <code>java.io.Reader</code>
     * object, which will have the specified number of characters. The
     * contents of the reader will be read and sent to the database.
     * This method throws an <code>SQLException</code> if the number of bytes
     * read and sent to the database is not equal to <i>length</i>.
     * <P>
     * When a very large Unicode value is input to a
     * <code>LONGVARCHAR</code> parameter, it may be more practical
     * to send it via a <code>Reader</code> object.
     * A JDBC technology-enabled driver will read the data from the
     * stream as needed until it reaches end-of-file.
     * The driver will do any necessary conversion from Unicode to the
     * database <code>CHAR</code> format.
     * The byte format of the Unicode stream must be Java UTF-8, as
     * defined in the Java Virtual Machine Specification.
     *
     * <P><B>Note:</B> This stream object can be either a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     * <P>
     * The parameter value set by this method is stored internally and
     * will be supplied as the appropriate parameter in this <code>RowSet</code>
     * object's command when the method <code>execute</code> is called.
     * Methods such as <code>execute</code> and <code>populate</code> must be
     * provided in any class that extends this class and implements one or
     * more of the standard JSR-114 <code>RowSet</code> interfaces.
     * <P>
     * NOTE: <code>JdbcRowSet</code> does not require the <code>populate</code> method
     * as it is undefined in this class.
     * <P>
     * Calls made to the method <code>getParams</code> after
     * <code>setCharacterStream</code>
     * has been called will return an array containing the parameter values that
     * have been set.  In that array, the element that represents the values
     * set with this method will itself be an array. The first element of that array
     * is the given <code>java.io.Reader</code> object.
     * The second element is the value set for <i>length</i>.
     * The parameter number is indicated by an element's position in the array
     * returned by the method <code>getParams</code>,
     * with the first element being the value for the first placeholder parameter, the
     * second element being the value for the second placeholder parameter, and so on.
     * In other words, if the reader being set is the value for the second
     * placeholder parameter, the array containing it will be the second element in
     * the array returned by <code>getParams</code>.
     * <P>
     * Note that because the numbering of elements in an array starts at zero,
     * the array element that corresponds to placeholder parameter number
     * <i>parameterIndex</i> is element number <i>parameterIndex</i> -1.
     *
     * <p>
     *  将指定的参数设置为给定的<code> javaioReader </code>对象,它将具有指定的字符数读取器的内容将被读取并发送到数据库此方法抛出一个<code> SQLException </code>
     * 如果读取和发送到数据库的字节数不等于<length> </i>。
     * <P>
     * 当将非常大的Unicode值输入到<code> LONGVARCHAR </code>参数时,通过<code> Reader </code>对象发送它可能更实用。
     * 启用JDBC技术的驱动程序将从流到需要的时间,直到它到达文件结束驱动程序将执行从Unicode到数据库的任何必要的转换<code> CHAR </code>格式Unicode流的字节格式必须是Java 
     * UTF-8, Java虚拟机规范"。
     * 当将非常大的Unicode值输入到<code> LONGVARCHAR </code>参数时,通过<code> Reader </code>对象发送它可能更实用。
     * 
     *  <P> <B>注意：</B>此流对象可以是标准Java流对象或实现标准接口的自己的子类
     * <P>
     * 当方法<code> execute </code>被调用时,此方法设置的参数值被内部存储,并将作为<code> RowSet </code>对象的命令中的适当参数提供。
     * 必须在扩展此类并实现一个或多个标准JSR-114 <code> RowSet </code>接口的任何类中提供</code>和<code> populate </code>。
     * <P>
     *  注意：<code> JdbcRowSet </code>不需要<code> populate </code>方法,因为它在此类中未定义
     * <P>
     * 在调用<code> setCharacterStream </code>之后对<code> getParams </code>方法的调用将返回一个包含已设置的参数值的数组。
     * 方法本身将是一个数组该数组的第一个元素是给定的<code> javaioReader </code>对象第二个元素是为<i> length </i>设置的值参数号由元素的位置由方法<code> getP
     * arams </code>返回的数组,第一个元素是第一个占位符参数的值,第二个元素是第二个占位符参数的值,等等换句话说,如果设置的读取器是第二个占位符参数的值,包含它的数组将是由<code> getPa
     * rams </code>返回的数组中的第二个元素。
     * 在调用<code> setCharacterStream </code>之后对<code> getParams </code>方法的调用将返回一个包含已设置的参数值的数组。
     * <P>
     * 注意,因为数组中元素的编号从零开始,所以对应于占位符参数号<parameter> </i>的数组元素是元素编号<i> parameterIndex </i> -1
     * 
     * 
     * @param parameterIndex the ordinal number of the placeholder parameter
     *        in this <code>RowSet</code> object's command that is to be set.
     *        The first parameter is 1, the second is 2, and so on; must be
     *        <code>1</code> or greater
     * @param reader the <code>Reader</code> object that contains the
     *        Unicode data
     * @param length the number of characters in the stream; lengths of 0 or
     *        less are undefined but will cause an invalid length exception to
     *        be thrown in the underlying JDBC driver.
     * @throws SQLException if an error occurs, the parameter index is out of bounds,
     *        or when connected to a data source, the number of bytes the driver
     *        reads and sends to the database is not equal to the number of bytes
     *        specified in <i>length</i>
     * @see #getParams
     */
    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
        Object charStream[];
        checkParamIndex(parameterIndex);

        charStream = new Object[2];
        charStream[0] = reader;
        charStream[1] = Integer.valueOf(length);
        if(params == null){
             throw new SQLException("Set initParams() before setCharacterStream");
        }
        params.put(Integer.valueOf(parameterIndex - 1), charStream);
    }

   /**
   * Sets the designated parameter in this <code>RowSet</code> object's command
   * to the given <code>Reader</code>
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
   *  将<code> RowSet </code>对象的命令中的指定参数设置为给定的<code> Reader </code>对象当将非常大的UNICODE值输入到<code> LONGVARCHAR </code>
   * 参数时,更实际的是通过一个<code> javaioReader </code>对象发送它。
   * 数据将根据需要从流中读取,直到达到文件结束JDBC驱动程序将执行从UNICODE到数据库字符格式的任何必要的转换。
   * 
   * <P> <B>注意：</B>此流对象可以是标准Java流对象或实现标准接口<P> <B>的自己的子类注意：</B>请参阅您的JDBC驱动程序文档确定是否可能更有效地使用<code> setCharact
   * erStream </code>的版本,该方法需要一个长度参数。
   * 
   * 
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param reader the <code>java.io.Reader</code> object that contains the
   *        Unicode data
   * @exception SQLException if a database access error occurs or
   * this method is called on a closed <code>PreparedStatement</code>
   * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
   * @since 1.6
   */
  public void setCharacterStream(int parameterIndex,
                          java.io.Reader reader) throws SQLException {
      throw new SQLFeatureNotSupportedException("Feature not supported");
  }

    /**
     * Sets the designated parameter to an <code>Object</code> in the Java
     * programming language. The second parameter must be an
     * <code>Object</code> type.  For integral values, the
     * <code>java.lang</code> equivalent
     * objects should be used. For example, use the class <code>Integer</code>
     * for an <code>int</code>.
     * <P>
     * The driver converts this object to the specified
     * target SQL type before sending it to the database.
     * If the object has a custom mapping (is of a class implementing
     * <code>SQLData</code>), the driver should call the method
     * <code>SQLData.writeSQL</code> to write the object to the SQL
     * data stream. If, on the other hand, the object is of a class
     * implementing <code>Ref</code>, <code>Blob</code>, <code>Clob</code>,
     * <code>Struct</code>, or <code>Array</code>,
     * the driver should pass it to the database as a value of the
     * corresponding SQL type.
     *
     * <p>Note that this method may be used to pass database-
     * specific abstract data types.
     * <P>
     * The parameter value set by this method is stored internally and
     * will be supplied as the appropriate parameter in this <code>RowSet</code>
     * object's command when the method <code>execute</code> is called.
     * Methods such as <code>execute</code> and <code>populate</code> must be
     * provided in any class that extends this class and implements one or
     * more of the standard JSR-114 <code>RowSet</code> interfaces.
     * <P>
     * NOTE: <code>JdbcRowSet</code> does not require the <code>populate</code> method
     * as it is undefined in this class.
     * <P>
     * Calls made to the method <code>getParams</code> after this version of
     * <code>setObject</code>
     * has been called will return an array containing the parameter values that
     * have been set.  In that array, the element that represents the values
     * set with this method will itself be an array. The first element of that array
     * is the given <code>Object</code> instance, and the
     * second element is the value set for <i>targetSqlType</i>.  The
     * third element is the value set for <i>scale</i>, which the driver will
     * ignore if the type of the object being set is not
     * <code>java.sql.Types.NUMERIC</code> or <code>java.sql.Types.DECIMAL</code>.
     * The parameter number is indicated by an element's position in the array
     * returned by the method <code>getParams</code>,
     * with the first element being the value for the first placeholder parameter, the
     * second element being the value for the second placeholder parameter, and so on.
     * In other words, if the object being set is the value for the second
     * placeholder parameter, the array containing it will be the second element in
     * the array returned by <code>getParams</code>.
     *<P>
     * Note that because the numbering of elements in an array starts at zero,
     * the array element that corresponds to placeholder parameter number
     * <i>parameterIndex</i> is element number <i>parameterIndex</i> -1.
     *
     *
     * <p>
     *  将指定的参数设置为Java编程语言中的<code> Object </code>第二个参数必须是<code> Object </code>类型对于整数值,<code> javalang </code>
     * 例如,对<code> int </code>使用<code> Integer </code>类。
     * <P>
     * 驱动程序在将此对象发送到数据库之前将此对象转换为指定的目标SQL类型如果对象具有自定义映射(是实现<code> SQLData </code>的类),驱动程序应调用<code> SQLDatawrite
     * SQL </code>将对象写入SQL数据流。
     * 另一方面,对象是实现<code> Ref </code>,<code> Blob </code>,<code> Clob < / code>,<code> Struct </code>或<code> A
     * rray </code>,驱动程序应将其作为相应SQL类型的值传递到数据库。
     * 
     *  <p>请注意,此方法可用于传递特定于数据库的抽象数据类型
     * <P>
     * 当方法<code> execute </code>被调用时,此方法设置的参数值被内部存储,并将作为<code> RowSet </code>对象的命令中的适当参数提供。
     * 必须在扩展此类并实现一个或多个标准JSR-114 <code> RowSet </code>接口的任何类中提供</code>和<code> populate </code>。
     * <P>
     *  注意：<code> JdbcRowSet </code>不需要<code> populate </code>方法,因为它在此类中未定义
     * <P>
     * 在调用<code> setObject </code>之后,对<code> getParams </code>方法的调用将返回一个包含已设置的参数值的数组,使用此方法设置本身将是一个数组该数组的第一个元
     * 素是给定的<code> Object </code>实例,第二个元素是为<i> targetSqlType </i>设置的值第三个元素是如果要设置的对象的类型不是<code> <java.sqlTypesNUMERIC </code>
     * 或<code> javasqlTypesDECIMAL </code>,则驱动程序将忽略<参数号由方法<code> getParams </code>返回的数组中元素的位置表示,第一个元素是第一个占位符
     * 参数的值,第二个元素是第二个占位符参数的值,等等换句话说,如果被设置的对象是第二个placeholder参数的值,包含它的数组将是由<code> getParams </code>返回的数组中的第二个元
     * 素。
     * P>
     * 注意,因为数组中元素的编号从零开始,所以对应于占位符参数号<parameter> </i>的数组元素是元素编号<i> parameterIndex </i> -1
     * 
     * 
     * @param parameterIndex the ordinal number of the placeholder parameter
     *        in this <code>RowSet</code> object's command that is to be set.
     *        The first parameter is 1, the second is 2, and so on; must be
     *        <code>1</code> or greater
     * @param x the <code>Object</code> containing the input parameter value;
     *        must be an <code>Object</code> type
     * @param targetSqlType the SQL type (as defined in <code>java.sql.Types</code>)
     *        to be sent to the database. The <code>scale</code> argument may
     *        further qualify this type. If a non-standard <i>targetSqlType</i>
     *        is supplied, this method will not throw a <code>SQLException</code>.
     *        This allows implicit support for non-standard SQL types.
     * @param scale for the types <code>java.sql.Types.DECIMAL</code> and
     *        <code>java.sql.Types.NUMERIC</code>, this is the number
     *        of digits after the decimal point.  For all other types, this
     *        value will be ignored.
     * @throws SQLException if an error occurs or the parameter index is out of bounds
     * @see #getParams
     */
    public void setObject(int parameterIndex, Object x, int targetSqlType, int scale) throws SQLException {
        Object obj[];
        checkParamIndex(parameterIndex);

        obj = new Object[3];
        obj[0] = x;
        obj[1] = Integer.valueOf(targetSqlType);
        obj[2] = Integer.valueOf(scale);
        if(params == null){
             throw new SQLException("Set initParams() before setObject");
        }
        params.put(Integer.valueOf(parameterIndex - 1), obj);
    }

    /**
     * Sets the value of the designated parameter with the given
     * <code>Object</code> value.
     * This method is like <code>setObject(int parameterIndex, Object x, int
     * targetSqlType, int scale)</code> except that it assumes a scale of zero.
     * <P>
     * The parameter value set by this method is stored internally and
     * will be supplied as the appropriate parameter in this <code>RowSet</code>
     * object's command when the method <code>execute</code> is called.
     * Methods such as <code>execute</code> and <code>populate</code> must be
     * provided in any class that extends this class and implements one or
     * more of the standard JSR-114 <code>RowSet</code> interfaces.
     * <P>
     * NOTE: <code>JdbcRowSet</code> does not require the <code>populate</code> method
     * as it is undefined in this class.
     * <P>
     * Calls made to the method <code>getParams</code> after this version of
     * <code>setObject</code>
     * has been called will return an array containing the parameter values that
     * have been set.  In that array, the element that represents the values
     * set with this method will itself be an array. The first element of that array
     * is the given <code>Object</code> instance.
     * The second element is the value set for <i>targetSqlType</i>.
     * The parameter number is indicated by an element's position in the array
     * returned by the method <code>getParams</code>,
     * with the first element being the value for the first placeholder parameter, the
     * second element being the value for the second placeholder parameter, and so on.
     * In other words, if the object being set is the value for the second
     * placeholder parameter, the array containing it will be the second element in
     * the array returned by <code>getParams</code>.
     * <P>
     * Note that because the numbering of elements in an array starts at zero,
     * the array element that corresponds to placeholder parameter number
     * <i>parameterIndex</i> is element number <i>parameterIndex</i> -1.
     *
     * <p>
     *  使用给定的<code> Object </code>值设置指定参数的值此方法类似于<code> setObject(int parameterIndex,Object x,int targetSqlT
     * ype,int scale)</code>为零。
     * <P>
     * 当方法<code> execute </code>被调用时,此方法设置的参数值被内部存储,并将作为<code> RowSet </code>对象的命令中的适当参数提供。
     * 必须在扩展此类并实现一个或多个标准JSR-114 <code> RowSet </code>接口的任何类中提供</code>和<code> populate </code>。
     * <P>
     *  注意：<code> JdbcRowSet </code>不需要<code> populate </code>方法,因为它在此类中未定义
     * <P>
     * 在调用<code> setObject </code>之后,对<code> getParams </code>方法的调用将返回一个包含已设置的参数值的数组,表示值的元素使用此方法设置本身将是一个数组该数
     * 组的第一个元素是给定的<code> Object </code>实例第二个元素是为<i> targetSqlType </i>设置的值参数号由元素在由方法<code> getParams </code>
     * 返回的数组中的位置,第一个元素是第一个占位符参数的值,第二个元素是第二个占位符参数的值,等等换句话说,如果被设置的对象是第二个占位符参数的值,包含它的数组将是由<code> getParams </code>
     * 返回的数组中的第二个元素。
     * <P>
     * 注意,因为数组中元素的编号从零开始,所以对应于占位符参数号<parameter> </i>的数组元素是元素编号<i> parameterIndex </i> -1
     * 
     * 
     * @param parameterIndex the ordinal number of the placeholder parameter
     *        in this <code>RowSet</code> object's command that is to be set.
     *        The first parameter is 1, the second is 2, and so on; must be
     *        <code>1</code> or greater
     * @param x the <code>Object</code> containing the input parameter value;
     *        must be an <code>Object</code> type
     * @param targetSqlType the SQL type (as defined in <code>java.sql.Types</code>)
     *        to be sent to the database. If a non-standard <i>targetSqlType</i>
     *        is supplied, this method will not throw a <code>SQLException</code>.
     *        This allows implicit support for non-standard SQL types.
     * @throws SQLException if an error occurs or the parameter index
     *        is out of bounds
     * @see #getParams
     */
    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
        Object obj[];
        checkParamIndex(parameterIndex);

        obj = new Object[2];
        obj[0] = x;
        obj[1] = Integer.valueOf(targetSqlType);
        if (params == null){
             throw new SQLException("Set initParams() before setObject");
        }
        params.put(Integer.valueOf(parameterIndex - 1), obj);
    }

    /**
     * Sets the designated parameter to an <code>Object</code> in the Java
     * programming language. The second parameter must be an
     * <code>Object</code>
     * type.  For integral values, the <code>java.lang</code> equivalent
     * objects should be used. For example, use the class <code>Integer</code>
     * for an <code>int</code>.
     * <P>
     * The JDBC specification defines a standard mapping from
     * Java <code>Object</code> types to SQL types.  The driver will
     * use this standard mapping to  convert the given object
     * to its corresponding SQL type before sending it to the database.
     * If the object has a custom mapping (is of a class implementing
     * <code>SQLData</code>), the driver should call the method
     * <code>SQLData.writeSQL</code> to write the object to the SQL
     * data stream.
     * <P>
     * If, on the other hand, the object is of a class
     * implementing <code>Ref</code>, <code>Blob</code>, <code>Clob</code>,
     * <code>Struct</code>, or <code>Array</code>,
     * the driver should pass it to the database as a value of the
     * corresponding SQL type.
     * <P>
     * This method throws an exception if there
     * is an ambiguity, for example, if the object is of a class
     * implementing more than one interface.
     * <P>
     * Note that this method may be used to pass database-specific
     * abstract data types.
     * <P>
     * The parameter value set by this method is stored internally and
     * will be supplied as the appropriate parameter in this <code>RowSet</code>
     * object's command when the method <code>execute</code> is called.
     * Methods such as <code>execute</code> and <code>populate</code> must be
     * provided in any class that extends this class and implements one or
     * more of the standard JSR-114 <code>RowSet</code> interfaces.
     * <p>
     * NOTE: <code>JdbcRowSet</code> does not require the <code>populate</code> method
     * as it is undefined in this class.
     * <P>
     * After this method has been called, a call to the
     * method <code>getParams</code>
     * will return an object array of the current command parameters, which will
     * include the <code>Object</code> set for placeholder parameter number
     * <code>parameterIndex</code>.
     * Note that because the numbering of elements in an array starts at zero,
     * the array element that corresponds to placeholder parameter number
     * <i>parameterIndex</i> is element number <i>parameterIndex</i> -1.
     *
     * <p>
     *  将指定的参数设置为Java编程语言中的<code> Object </code>第二个参数必须是<code> Object </code>类型对于整数值,<code> javalang </code>
     * 例如,对<code> int </code>使用<code> Integer </code>类。
     * <P>
     * JDBC规范定义了从Java <code> Object </code>类型到SQL类型的标准映射驱动程序在将其发送到数据库之前,将使用此标准映射将给定对象转换为相应的SQL类型如果对象具有自定义映射(
     * 是实现<code> SQLData </code>的类),驱动程序应调用<code> SQLDatawriteSQL </code>方法将对象写入SQL数据流。
     * <P>
     *  另一方面,如果对象是实现<code> Ref </code>,<code> Blob </code>,<code> Clob </code>,<code> Struct </code>或<code> 
     * Array </code>,驱动程序应将其作为相应SQL类型的值传递到数据库。
     * <P>
     * 此方法在存在歧义时抛出异常,例如,如果对象是实现多个接口的类
     * <P>
     *  请注意,此方法可用于传递特定于数据库的抽象数据类型
     * <P>
     *  当方法<code> execute </code>被调用时,此方法设置的参数值被内部存储,并将作为<code> RowSet </code>对象的命令中的适当参数提供。
     * 必须在扩展此类并实现一个或多个标准JSR-114 <code> RowSet </code>接口的任何类中提供</code>和<code> populate </code>。
     * <p>
     *  注意：<code> JdbcRowSet </code>不需要<code> populate </code>方法,因为它在此类中未定义
     * <P>
     * 调用此方法后,对<code> getParams </code>方法的调用将返回当前命令参数的对象数组,其中将包括为占位符参数号设置的<code> Object </code> <code > para
     * meterIndex </code>请注意,因为数组中元素的编号从零开始,所以与占位符参数number <i> parameterIndex </i>对应的数组元素是元素编号<i> parameterI
     * ndex </i> -1。
     * 
     * 
     * @param parameterIndex the ordinal number of the placeholder parameter
     *        in this <code>RowSet</code> object's command that is to be set.
     *        The first parameter is 1, the second is 2, and so on; must be
     *        <code>1</code> or greater
     * @param x the object containing the input parameter value
     * @throws SQLException if an error occurs the
     *                         parameter index is out of bounds, or there
     *                         is ambiguity in the implementation of the
     *                         object being set
     * @see #getParams
     */
    public void setObject(int parameterIndex, Object x) throws SQLException {
        checkParamIndex(parameterIndex);
        if (params == null) {
             throw new SQLException("Set initParams() before setObject");
        }
        params.put(Integer.valueOf(parameterIndex - 1), x);
    }

    /**
     * Sets the designated parameter to the given <code>Ref</code> object in
     * the Java programming language.  The driver converts this to an SQL
     * <code>REF</code> value when it sends it to the database. Internally, the
     * <code>Ref</code> is represented as a <code>SerialRef</code> to ensure
     * serializability.
     * <P>
     * The parameter value set by this method is stored internally and
     * will be supplied as the appropriate parameter in this <code>RowSet</code>
     * object's command when the method <code>execute</code> is called.
     * Methods such as <code>execute</code> and <code>populate</code> must be
     * provided in any class that extends this class and implements one or
     * more of the standard JSR-114 <code>RowSet</code> interfaces.
     * <p>
     * NOTE: <code>JdbcRowSet</code> does not require the <code>populate</code> method
     * as it is undefined in this class.
     * <p>
     * After this method has been called, a call to the
     * method <code>getParams</code>
     * will return an object array of the current command parameters, which will
     * include the <code>Ref</code> object set for placeholder parameter number
     * <code>parameterIndex</code>.
     * Note that because the numbering of elements in an array starts at zero,
     * the array element that corresponds to placeholder parameter number
     * <i>parameterIndex</i> is element number <i>parameterIndex</i> -1.
     *
     * <p>
     *  将指定的参数设置为Java编程语言中给定的<code> Ref </code>对象当驱动程序将其发送到数据库时将其转换为SQL <code> REF </code>值在内部,<code> Ref </code>
     * 表示为<code> SerialRef </code>以确保可串行化。
     * <P>
     * 当方法<code> execute </code>被调用时,此方法设置的参数值被内部存储,并将作为<code> RowSet </code>对象的命令中的适当参数提供。
     * 必须在扩展此类并实现一个或多个标准JSR-114 <code> RowSet </code>接口的任何类中提供</code>和<code> populate </code>。
     * <p>
     *  注意：<code> JdbcRowSet </code>不需要<code> populate </code>方法,因为它在此类中未定义
     * <p>
     * 调用此方法后,调用<code> getParams </code>方法将返回当前命令参数的对象数组,其中包括<code> Ref </code>对象占位符参数编号< code> parameterInd
     * ex </code>请注意,因为数组中元素的编号从零开始,所以与占位符参数number <i> parameterIndex </i>对应的数组元素是元素编号<i> parameterIndex </i>
     *  1。
     * 
     * 
     * @param parameterIndex the ordinal number of the placeholder parameter
     *        in this <code>RowSet</code> object's command that is to be set.
     *        The first parameter is 1, the second is 2, and so on; must be
     *        <code>1</code> or greater
     * @param ref a <code>Ref</code> object representing an SQL <code>REF</code>
     *         value; cannot be null
     * @throws SQLException if an error occurs; the parameter index is out of
     *         bounds or the <code>Ref</code> object is <code>null</code>; or
     *         the <code>Ref</code> object returns a <code>null</code> base type
     *         name.
     * @see #getParams
     * @see javax.sql.rowset.serial.SerialRef
     */
    public void setRef (int parameterIndex, Ref ref) throws SQLException {
        checkParamIndex(parameterIndex);
        if (params == null) {
             throw new SQLException("Set initParams() before setRef");
        }
        params.put(Integer.valueOf(parameterIndex - 1), new SerialRef(ref));
    }

    /**
     * Sets the designated parameter to the given <code>Blob</code> object in
     * the Java programming language.  The driver converts this to an SQL
     * <code>BLOB</code> value when it sends it to the database. Internally,
     * the <code>Blob</code> is represented as a <code>SerialBlob</code>
     * to ensure serializability.
     * <P>
     * The parameter value set by this method is stored internally and
     * will be supplied as the appropriate parameter in this <code>RowSet</code>
     * object's command when the method <code>execute</code> is called.
     * Methods such as <code>execute</code> and <code>populate</code> must be
     * provided in any class that extends this class and implements one or
     * more of the standard JSR-114 <code>RowSet</code> interfaces.
     * NOTE: <code>JdbcRowSet</code> does not require the <code>populate</code> method
     * as it is undefined in this class.
     * <p>
     * After this method has been called, a call to the
     * method <code>getParams</code>
     * will return an object array of the current command parameters, which will
     * include the <code>Blob</code> object set for placeholder parameter number
     * <code>parameterIndex</code>.
     * Note that because the numbering of elements in an array starts at zero,
     * the array element that corresponds to placeholder parameter number
     * <i>parameterIndex</i> is element number <i>parameterIndex</i> -1.
     *
     * <p>
     *  将指定的参数设置为Java编程语言中给定的<code> Blob </code>对象当驱动程序将其发送到数据库时,将其转换为SQL <code> BLOB </code>值在内部,<code> Blo
     * b </code>表示为<code> SerialBlob </code>以确保序列化。
     * <P>
     * 当方法<code> execute </code>被调用时,此方法设置的参数值被内部存储,并将作为<code> RowSet </code>对象的命令中的适当参数提供。
     * 必须在扩展此类并实现一个或多个标准JSR-114 <code> RowSet </code>接口的任何类中提供</code>和<code> populate </code>注：<code> JdbcRo
     * wSet < / code>不需要<code> populate </code>方法,因为它在此类中未定义。
     * 当方法<code> execute </code>被调用时,此方法设置的参数值被内部存储,并将作为<code> RowSet </code>对象的命令中的适当参数提供。
     * <p>
     * 调用此方法之后,调用<code> getParams </code>方法将返回当前命令参数的对象数组,其中包括<place> <b> <> code> parameterIndex </code>请注意
     * ,由于数组中元素的编号从零开始,与占位符参数number <i> parameterIndex </i>对应的数组元素是元素编号<i> parameterIndex </i> 1。
     * 
     * 
     * @param parameterIndex the ordinal number of the placeholder parameter
     *        in this <code>RowSet</code> object's command that is to be set.
     *        The first parameter is 1, the second is 2, and so on; must be
     *        <code>1</code> or greater
     * @param x a <code>Blob</code> object representing an SQL
     *          <code>BLOB</code> value
     * @throws SQLException if an error occurs or the
     *                         parameter index is out of bounds
     * @see #getParams
     * @see javax.sql.rowset.serial.SerialBlob
     */
    public void setBlob (int parameterIndex, Blob x) throws SQLException {
        checkParamIndex(parameterIndex);
        if(params == null){
             throw new SQLException("Set initParams() before setBlob");
        }
        params.put(Integer.valueOf(parameterIndex - 1), new SerialBlob(x));
    }

    /**
     * Sets the designated parameter to the given <code>Clob</code> object in
     * the Java programming language.  The driver converts this to an SQL
     * <code>CLOB</code> value when it sends it to the database. Internally, the
     * <code>Clob</code> is represented as a <code>SerialClob</code> to ensure
     * serializability.
     * <P>
     * The parameter value set by this method is stored internally and
     * will be supplied as the appropriate parameter in this <code>RowSet</code>
     * object's command when the method <code>execute</code> is called.
     * Methods such as <code>execute</code> and <code>populate</code> must be
     * provided in any class that extends this class and implements one or
     * more of the standard JSR-114 <code>RowSet</code> interfaces.
     * <p>
     * NOTE: <code>JdbcRowSet</code> does not require the <code>populate</code> method
     * as it is undefined in this class.
     * <p>
     * After this method has been called, a call to the
     * method <code>getParams</code>
     * will return an object array of the current command parameters, which will
     * include the <code>Clob</code> object set for placeholder parameter number
     * <code>parameterIndex</code>.
     * Note that because the numbering of elements in an array starts at zero,
     * the array element that corresponds to placeholder parameter number
     * <i>parameterIndex</i> is element number <i>parameterIndex</i> -1.
     *
     * <p>
     *  将指定的参数设置为Java编程语言中给定的<code> Clob </code>对象驱动程序在将其发送到数据库时将其转换为SQL <code> CLOB </code>值在内部,<code> Clob
     *  </code>表示为<code> SerialClob </code>以确保序列化。
     * <P>
     * 当方法<code> execute </code>被调用时,此方法设置的参数值被内部存储,并将作为<code> RowSet </code>对象的命令中的适当参数提供。
     * 必须在扩展此类并实现一个或多个标准JSR-114 <code> RowSet </code>接口的任何类中提供</code>和<code> populate </code>。
     * <p>
     *  注意：<code> JdbcRowSet </code>不需要<code> populate </code>方法,因为它在此类中未定义
     * <p>
     * 调用此方法后,对方法<code> getParams </code>的调用将返回当前命令参数的对象数组,其中将包括为占位符参数设置的<code> Clob </code> code> parameter
     * Index </code>请注意,因为数组中元素的编号从零开始,所以与占位符参数number <i> parameterIndex </i>对应的数组元素是元素编号<i> parameterIndex 
     * </i> 1。
     * 
     * 
     * @param parameterIndex the ordinal number of the placeholder parameter
     *     in this <code>RowSet</code> object's command that is to be set.
     *     The first parameter is 1, the second is 2, and so on; must be
     *     <code>1</code> or greater
     * @param x a <code>Clob</code> object representing an SQL
     *     <code>CLOB</code> value; cannot be null
     * @throws SQLException if an error occurs; the parameter index is out of
     *     bounds or the <code>Clob</code> is null
     * @see #getParams
     * @see javax.sql.rowset.serial.SerialBlob
     */
    public void setClob (int parameterIndex, Clob x) throws SQLException {
        checkParamIndex(parameterIndex);
        if(params == null){
             throw new SQLException("Set initParams() before setClob");
        }
        params.put(Integer.valueOf(parameterIndex - 1), new SerialClob(x));
    }

    /**
     * Sets the designated parameter to an <code>Array</code> object in the
     * Java programming language.  The driver converts this to an SQL
     * <code>ARRAY</code> value when it sends it to the database. Internally,
     * the <code>Array</code> is represented as a <code>SerialArray</code>
     * to ensure serializability.
     * <P>
     * The parameter value set by this method is stored internally and
     * will be supplied as the appropriate parameter in this <code>RowSet</code>
     * object's command when the method <code>execute</code> is called.
     * Methods such as <code>execute</code> and <code>populate</code> must be
     * provided in any class that extends this class and implements one or
     * more of the standard JSR-114 <code>RowSet</code> interfaces.
     * <P>
     * Note: <code>JdbcRowSet</code> does not require the <code>populate</code> method
     * as it is undefined in this class.
     * <p>
     * After this method has been called, a call to the
     * method <code>getParams</code>
     * will return an object array of the current command parameters, which will
     * include the <code>Array</code> object set for placeholder parameter number
     * <code>parameterIndex</code>.
     * Note that because the numbering of elements in an array starts at zero,
     * the array element that corresponds to placeholder parameter number
     * <i>parameterIndex</i> is element number <i>parameterIndex</i> -1.
     *
     * <p>
     *  将指定的参数设置为Java编程语言中的<code> Array </code>对象当驱动程序将其发送到数据库时,将其转换为SQL <code> ARRAY </code>值在内部,<code> Arr
     * ay </code>表示为<code> SerialArray </code>以确保可串行化。
     * <P>
     * 当方法<code> execute </code>被调用时,此方法设置的参数值被内部存储,并将作为<code> RowSet </code>对象的命令中的适当参数提供。
     * 必须在扩展此类并实现一个或多个标准JSR-114 <code> RowSet </code>接口的任何类中提供</code>和<code> populate </code>。
     * <P>
     *  注意：<code> JdbcRowSet </code>不需要<code> populate </code>方法,因为它在此类中未定义
     * <p>
     * 调用此方法后,调用<code> getParams </code>方法将返回当前命令参数的对象数组,其中将包括为占位符参数设置的<code> Array </code> code> parameterI
     * ndex </code>请注意,由于数组中元素的编号从零开始,与占位符参数number <i> parameterIndex </i>对应的数组元素是元素编号<i> parameterIndex </i>
     *  1。
     * 
     * 
     * @param parameterIndex the ordinal number of the placeholder parameter
     *        in this <code>RowSet</code> object's command that is to be set.
     *        The first parameter is 1, the second is 2, and so on; must be
     *        <code>1</code> or greater
     * @param array an <code>Array</code> object representing an SQL
     *        <code>ARRAY</code> value; cannot be null. The <code>Array</code> object
     *        passed to this method must return a non-null Object for all
     *        <code>getArray()</code> method calls. A null value will cause a
     *        <code>SQLException</code> to be thrown.
     * @throws SQLException if an error occurs; the parameter index is out of
     *        bounds or the <code>ARRAY</code> is null
     * @see #getParams
     * @see javax.sql.rowset.serial.SerialArray
     */
    public void setArray (int parameterIndex, Array array) throws SQLException {
        checkParamIndex(parameterIndex);
        if (params == null){
             throw new SQLException("Set initParams() before setArray");
        }
        params.put(Integer.valueOf(parameterIndex - 1), new SerialArray(array));
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.Date</code>
     * object.
     * When the DBMS does not store time zone information, the driver will use
     * the given <code>Calendar</code> object to construct the SQL <code>DATE</code>
     * value to send to the database. With a
     * <code>Calendar</code> object, the driver can calculate the date
     * taking into account a custom time zone.  If no <code>Calendar</code>
     * object is specified, the driver uses the time zone of the Virtual Machine
     * that is running the application.
     * <P>
     * The parameter value set by this method is stored internally and
     * will be supplied as the appropriate parameter in this <code>RowSet</code>
     * object's command when the method <code>execute</code> is called.
     * Methods such as <code>execute</code> and <code>populate</code> must be
     * provided in any class that extends this class and implements one or
     * more of the standard JSR-114 <code>RowSet</code> interfaces.
     * <P>
     * NOTE: <code>JdbcRowSet</code> does not require the <code>populate</code> method
     * as it is undefined in this class.
     * <P>
     * Calls made to the method <code>getParams</code> after this version of
     * <code>setDate</code>
     * has been called will return an array containing the parameter values that
     * have been set.  In that array, the element that represents the values
     * set with this method will itself be an array. The first element of that array
     * is the given <code>java.sql.Date</code> object.
     * The second element is the value set for <i>cal</i>.
     * The parameter number is indicated by an element's position in the array
     * returned by the method <code>getParams</code>,
     * with the first element being the value for the first placeholder parameter, the
     * second element being the value for the second placeholder parameter, and so on.
     * In other words, if the date being set is the value for the second
     * placeholder parameter, the array containing it will be the second element in
     * the array returned by <code>getParams</code>.
     * <P>
     * Note that because the numbering of elements in an array starts at zero,
     * the array element that corresponds to placeholder parameter number
     * <i>parameterIndex</i> is <i>parameterIndex</i> -1.
     *
     * <p>
     * 将指定的参数设置为给定的<code> javasqlDate </code>对象当DBMS不存储时区信息时,驱动程序将使用给定的<code> Calendar </code>对象来构造SQL <code>
     *  DATE < / code>值发送到数据库使用<code> Calendar </code>对象,驱动程序可以计算考虑自定义时区的日期如果未指定<code> Calendar </code>对象,则驱
     * 动程序使用运行应用程序的虚拟机的时区。
     * <P>
     * 当方法<code> execute </code>被调用时,此方法设置的参数值被内部存储,并将作为<code> RowSet </code>对象的命令中的适当参数提供。
     * 必须在扩展此类并实现一个或多个标准JSR-114 <code> RowSet </code>接口的任何类中提供</code>和<code> populate </code>。
     * <P>
     *  注意：<code> JdbcRowSet </code>不需要<code> populate </code>方法,因为它在此类中未定义
     * <P>
     * 在调用<code> setDate </code>的此版本之后对<code> getParams </code>方法的调用将返回一个包含已设置的参数值的数组,使用此方法设置本身将是一个数组该数组的第一个
     * 元素是给定的<code> javasqlDate </code>对象第二个元素是为<i> cal </i>设置的值参数号由元素在由方法<code> getParams </code>返回的数组中的位置,
     * 第一个元素是第一个占位符参数的值,第二个元素是第二个占位符参数的值,等等换句话说,如果设置的日期是第二个占位符参数的值,包含它的数组将是由<code> getParams </code>返回的数组中的第
     * 二个元素。
     * <P>
     * 请注意,由于数组中元素的编号从零开始,与占位符参数编号<parameter> </i>对应的数组元素为<i> parameterIndex </i> -1
     * 
     * 
     * @param parameterIndex the ordinal number of the placeholder parameter
     *        in this <code>RowSet</code> object's command that is to be set.
     *        The first parameter is 1, the second is 2, and so on; must be
     *        <code>1</code> or greater
     * @param x a <code>java.sql.Date</code> object representing an SQL
     *        <code>DATE</code> value
     * @param cal a <code>java.util.Calendar</code> object to use when
     *        when constructing the date
     * @throws SQLException if an error occurs or the
     *                         parameter index is out of bounds
     * @see #getParams
     */
    public void setDate(int parameterIndex, java.sql.Date x, Calendar cal) throws SQLException {
        Object date[];
        checkParamIndex(parameterIndex);

        date = new Object[2];
        date[0] = x;
        date[1] = cal;
        if(params == null){
             throw new SQLException("Set initParams() before setDate");
        }
        params.put(Integer.valueOf(parameterIndex - 1), date);
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.Time</code>
     * object.  The driver converts this
     * to an SQL <code>TIME</code> value when it sends it to the database.
     * <P>
     * When the DBMS does not store time zone information, the driver will use
     * the given <code>Calendar</code> object to construct the SQL <code>TIME</code>
     * value to send to the database. With a
     * <code>Calendar</code> object, the driver can calculate the date
     * taking into account a custom time zone.  If no <code>Calendar</code>
     * object is specified, the driver uses the time zone of the Virtual Machine
     * that is running the application.
     * <P>
     * The parameter value set by this method is stored internally and
     * will be supplied as the appropriate parameter in this <code>RowSet</code>
     * object's command when the method <code>execute</code> is called.
     * Methods such as <code>execute</code> and <code>populate</code> must be
     * provided in any class that extends this class and implements one or
     * more of the standard JSR-114 <code>RowSet</code> interfaces.
     * <P>
     * NOTE: <code>JdbcRowSet</code> does not require the <code>populate</code> method
     * as it is undefined in this class.
     * <P>
     * Calls made to the method <code>getParams</code> after this version of
     * <code>setTime</code>
     * has been called will return an array containing the parameter values that
     * have been set.  In that array, the element that represents the values
     * set with this method will itself be an array. The first element of that array
     * is the given <code>java.sql.Time</code> object.
     * The second element is the value set for <i>cal</i>.
     * The parameter number is indicated by an element's position in the array
     * returned by the method <code>getParams</code>,
     * with the first element being the value for the first placeholder parameter, the
     * second element being the value for the second placeholder parameter, and so on.
     * In other words, if the time being set is the value for the second
     * placeholder parameter, the array containing it will be the second element in
     * the array returned by <code>getParams</code>.
     * <P>
     * Note that because the numbering of elements in an array starts at zero,
     * the array element that corresponds to placeholder parameter number
     * <i>parameterIndex</i> is <i>parameterIndex</i> -1.
     *
     * <p>
     *  将指定的参数设置为给定的<code> javasqlTime </code>对象当驱动程序将它发送到数据库时,将其转换为SQL <code> TIME </code>
     * <P>
     *  当DBMS不存储时区信息时,驱动程序将使用给定的<code> Calendar </code>对象来构造要发送到数据库的SQL <code> TIME </code> / code>对象,驱动程序可以
     * 计算考虑自定义时区的日期如果未指定<code> Calendar </code>对象,则驱动程序将使用运行应用程序的虚拟机的时区。
     * <P>
     * 当方法<code> execute </code>被调用时,此方法设置的参数值被内部存储,并将作为<code> RowSet </code>对象的命令中的适当参数提供。
     * 必须在扩展此类并实现一个或多个标准JSR-114 <code> RowSet </code>接口的任何类中提供</code>和<code> populate </code>。
     * <P>
     *  注意：<code> JdbcRowSet </code>不需要<code> populate </code>方法,因为它在此类中未定义
     * <P>
     * 在此版本的<code> setTime </code>被调用后,对方法<code> getParams </code>的调用将返回一个包含已设置的参数值的数组,表示值的元素使用此方法设置本身将是一个数组
     * 该数组的第一个元素是给定的<code> javasqlTime </code>对象第二个元素是为<i> cal </i>设置的值参数号由元素在由<code> getParams </code>方法返回的
     * 数组中的位置,第一个元素是第一个占位符参数的值,第二个元素是第二个占位符参数的值,等等换句话说,如果设置的时间是第二个占位符参数的值,包含它的数组将是由<code> getParams </code>返
     * 回的数组中的第二个元素。
     * <P>
     * 请注意,由于数组中元素的编号从零开始,与占位符参数编号<parameter> </i>对应的数组元素为<i> parameterIndex </i> -1
     * 
     * 
     * @param parameterIndex the ordinal number of the placeholder parameter
     *        in this <code>RowSet</code> object's command that is to be set.
     *        The first parameter is 1, the second is 2, and so on; must be
     *        <code>1</code> or greater
     * @param x a <code>java.sql.Time</code> object
     * @param cal the <code>java.util.Calendar</code> object the driver can use to
     *         construct the time
     * @throws SQLException if an error occurs or the
     *                         parameter index is out of bounds
     * @see #getParams
     */
    public void setTime(int parameterIndex, java.sql.Time x, Calendar cal) throws SQLException {
        Object time[];
        checkParamIndex(parameterIndex);

        time = new Object[2];
        time[0] = x;
        time[1] = cal;
        if(params == null){
             throw new SQLException("Set initParams() before setTime");
        }
        params.put(Integer.valueOf(parameterIndex - 1), time);
    }

    /**
     * Sets the designated parameter to the given
     * <code>java.sql.Timestamp</code> object.  The driver converts this
     * to an SQL <code>TIMESTAMP</code> value when it sends it to the database.
     * <P>
     * When the DBMS does not store time zone information, the driver will use
     * the given <code>Calendar</code> object to construct the SQL <code>TIMESTAMP</code>
     * value to send to the database. With a
     * <code>Calendar</code> object, the driver can calculate the timestamp
     * taking into account a custom time zone.  If no <code>Calendar</code>
     * object is specified, the driver uses the time zone of the Virtual Machine
     * that is running the application.
     * <P>
     * The parameter value set by this method is stored internally and
     * will be supplied as the appropriate parameter in this <code>RowSet</code>
     * object's command when the method <code>execute</code> is called.
     * Methods such as <code>execute</code> and <code>populate</code> must be
     * provided in any class that extends this class and implements one or
     * more of the standard JSR-114 <code>RowSet</code> interfaces.
     * <P>
     * NOTE: <code>JdbcRowSet</code> does not require the <code>populate</code> method
     * as it is undefined in this class.
     * <P>
     * Calls made to the method <code>getParams</code> after this version of
     * <code>setTimestamp</code>
     * has been called will return an array containing the parameter values that
     * have been set.  In that array, the element that represents the values
     * set with this method will itself be an array. The first element of that array
     * is the given <code>java.sql.Timestamp</code> object.
     * The second element is the value set for <i>cal</i>.
     * The parameter number is indicated by an element's position in the array
     * returned by the method <code>getParams</code>,
     * with the first element being the value for the first placeholder parameter, the
     * second element being the value for the second placeholder parameter, and so on.
     * In other words, if the timestamp being set is the value for the second
     * placeholder parameter, the array containing it will be the second element in
     * the array returned by <code>getParams</code>.
     * <P>
     * Note that because the numbering of elements in an array starts at zero,
     * the array element that corresponds to placeholder parameter number
     * <i>parameterIndex</i> is <i>parameterIndex</i> -1.
     *
     * <p>
     *  将指定的参数设置为给定的<code> javasqlTimestamp </code>对象当驱动程序将其发送到数据库时,将其转换为SQL <code> TIMESTAMP </code>
     * <P>
     * 当DBMS不存储时区信息时,驱动程序将使用给定的<code> Calendar </code>对象来构造要发送到数据库的SQL <code> TIMESTAMP </code>值。
     *  / code>对象,驱动程序可以计算考虑自定义时区的时间戳如果未指定<code> Calendar </code>对象,则驱动程序将使用运行应用程序的虚拟机的时区。
     * <P>
     * 当方法<code> execute </code>被调用时,此方法设置的参数值被内部存储,并将作为<code> RowSet </code>对象的命令中的适当参数提供。
     * 必须在扩展此类并实现一个或多个标准JSR-114 <code> RowSet </code>接口的任何类中提供</code>和<code> populate </code>。
     * <P>
     *  注意：<code> JdbcRowSet </code>不需要<code> populate </code>方法,因为它在此类中未定义
     * <P>
     * 在调用此代码之后,对<code> getParams </code>方法的调用将返回一个包含已设置的参数值的数组,表示值的元素使用此方法设置本身将是一个数组该数组的第一个元素是给定的<code> jav
     * asqlTimestamp </code>对象第二个元素是为<i> cal </i>设置的值参数号由元素在由<code> getParams </code>方法返回的数组中的位置,第一个元素是第一个占位
     * 符参数的值,第二个元素是第二个占位符参数的值,等等换句话说,如果被设置的时间戳是第二个占位符参数的值,包含它的数组将是由<code> getParams </code>返回的数组中的第二个元素。
     * <P>
     * 请注意,由于数组中元素的编号从零开始,与占位符参数编号<parameter> </i>对应的数组元素为<i> parameterIndex </i> -1
     * 
     * 
     * @param parameterIndex the ordinal number of the placeholder parameter
     *        in this <code>RowSet</code> object's command that is to be set.
     *        The first parameter is 1, the second is 2, and so on; must be
     *        <code>1</code> or greater
     * @param x a <code>java.sql.Timestamp</code> object
     * @param cal the <code>java.util.Calendar</code> object the driver can use to
     *         construct the timestamp
     * @throws SQLException if an error occurs or the
     *                         parameter index is out of bounds
     * @see #getParams
     */
    public void setTimestamp(int parameterIndex, java.sql.Timestamp x, Calendar cal) throws SQLException {
        Object timestamp[];
        checkParamIndex(parameterIndex);

        timestamp = new Object[2];
        timestamp[0] = x;
        timestamp[1] = cal;
        if(params == null){
             throw new SQLException("Set initParams() before setTimestamp");
        }
        params.put(Integer.valueOf(parameterIndex - 1), timestamp);
    }

    /**
     * Clears all of the current parameter values in this <code>RowSet</code>
     * object's internal representation of the parameters to be set in
     * this <code>RowSet</code> object's command when it is executed.
     * <P>
     * In general, parameter values remain in force for repeated use in
     * this <code>RowSet</code> object's command. Setting a parameter value with the
     * setter methods automatically clears the value of the
     * designated parameter and replaces it with the new specified value.
     * <P>
     * This method is called internally by the <code>setCommand</code>
     * method to clear all of the parameters set for the previous command.
     * <P>
     * Furthermore, this method differs from the <code>initParams</code>
     * method in that it maintains the schema of the <code>RowSet</code> object.
     *
     * <p>
     *  清除此<> RowSet </code>对象的内部表示中的所有当前参数值,以便在执行该<code> RowSet </code>对象的命令时设置
     * <P>
     *  通常,参数值在此<code> RowSet </code>对象的命令中重复使用时仍然有效。使用setter方法设置参数值会自动清除指定参数的值,并将其替换为新的指定值
     * <P>
     *  此方法由<code> setCommand </code>方法内部调用,以清除为上一个命令设置的所有参数
     * <P>
     * 此外,此方法与<code> initParams </code>方法的不同之处在于它维护<code> RowSet </code>对象的模式
     * 
     * 
     * @throws SQLException if an error occurs clearing the parameters
     */
    public void clearParameters() throws SQLException {
        params.clear();
    }

    /**
     * Retrieves an array containing the parameter values (both Objects and
     * primitives) that have been set for this
     * <code>RowSet</code> object's command and throws an <code>SQLException</code> object
     * if all parameters have not been set.   Before the command is sent to the
     * DBMS to be executed, these parameters will be substituted
     * for placeholder parameters in the  <code>PreparedStatement</code> object
     * that is the command for a <code>RowSet</code> implementation extending
     * the <code>BaseRowSet</code> class.
     * <P>
     * Each element in the array that is returned is an <code>Object</code> instance
     * that contains the values of the parameters supplied to a setter method.
     * The order of the elements is determined by the value supplied for
     * <i>parameterIndex</i>.  If the setter method takes only the parameter index
     * and the value to be set (possibly null), the array element will contain the value to be set
     * (which will be expressed as an <code>Object</code>).  If there are additional
     * parameters, the array element will itself be an array containing the value to be set
     * plus any additional parameter values supplied to the setter method. If the method
     * sets a stream, the array element includes the type of stream being supplied to the
     * method. These additional parameters are for the use of the driver or the DBMS and may or
     * may not be used.
     * <P>
     * NOTE: Stored parameter values of types <code>Array</code>, <code>Blob</code>,
     * <code>Clob</code> and <code>Ref</code> are returned as <code>SerialArray</code>,
     * <code>SerialBlob</code>, <code>SerialClob</code> and <code>SerialRef</code>
     * respectively.
     *
     * <p>
     *  检索包含为此<code> RowSet </code>对象的命令设置的参数值(对象和原语)的数组,并在未设置所有参数的情况下抛出<code> SQLException </code>命令发送到要执行的
     * DBMS,这些参数将替换<code> PreparedStatement </code>对象中的占位符参数,该对象是<code> RowSet </code>实现的命令,扩展<code> BaseRow
     * Set </code>类。
     * <P>
     * 返回的数组中的每个元素都是一个包含提供给setter方法的参数值的<code> Object </code>实例。
     * 元素的顺序由为<i> parameterIndex </i >如果setter方法仅使用参数索引和要设置的值(可能为null),则数组元素将包含要设置的值(将被表示为<code> Object </code>
     * )。
     * 返回的数组中的每个元素都是一个包含提供给setter方法的参数值的<code> Object </code>实例。
     * 如果有附加参数,数组元素本身将是包含要设置的值以及提供给setter方法的任何附加参数值的数组。
     * 如果方法设置流,则数组元素包括提供给方法的流的类型这些附加参数用于驱动程序或DBMS的使用,并且可以或可以不使用。
     * <P>
     * 注意：类型<code> Array </code>,<code> Blob </code>,<code> Clob </code>和<code> Ref </code>的存储参数值返回为<code> S
     * erialArray < / code>,<code> SerialBlob </code>,<code> SerialClob </code>和<code>。
     * 
     * 
     * @return an array of <code>Object</code> instances that includes the
     *         parameter values that may be set in this <code>RowSet</code> object's
     *         command; an empty array if no parameters have been set
     * @throws SQLException if an error occurs retrieving the object array of
     *         parameters of this <code>RowSet</code> object or if not all parameters have
     *         been set
     */
    public Object[] getParams() throws SQLException {
        if (params == null) {

            initParams();
            Object [] paramsArray = new Object[params.size()];
            return paramsArray;

        } else {
            // The parameters may be set in random order
            // but all must be set, check to verify all
            // have been set till the last parameter
            // else throw exception.

            Object[] paramsArray = new Object[params.size()];
            for (int i = 0; i < params.size(); i++) {
               paramsArray[i] = params.get(Integer.valueOf(i));
               if (paramsArray[i] == null) {
                 throw new SQLException("missing parameter: " + (i + 1));
               } //end if
            } //end for
            return paramsArray;

        } //end if

    } //end getParams


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
    * @exception SQLException if a database access error occurs or
    * this method is called on a closed <code>CallableStatement</code>
    * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
    * this method
    * @since 1.4
    */
   public void setNull(String parameterName, int sqlType) throws SQLException {
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }


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
    * parameter, the name is the type name of the referenced type.  If
    * a JDBC driver does not need the type code or type name information,
    * it may ignore it.
    *
    * Although it is intended for user-defined and Ref parameters,
    * this method may be used to set a null parameter of any JDBC type.
    * If the parameter does not have a user-defined or REF type, the given
    * typeName is ignored.
    *
    *
    * <p>
    *  将指定的参数设置为SQL <code> NULL </code>此方法的版本<code> setNull </code>应用于用户定义的类型和REF类型参数用户定义类型的示例包括：STRUCT,DIS
    * TINCT ,JAVA_OBJECT和命名的数组类型。
    * 
    * <P> <B>注意：</B>为了便于移植,当指定NULL用户定义或REF参数时,应用程序必须给出SQL类型代码和完全限定的SQL类型名称。
    * 在用户定义类型名称是参数本身的类型名称对于REF参数,名称是引用类型的类型名称如果JDBC驱动程序不需要类型代码或类型名称信息,它可能会忽略它。
    * 
    *  虽然它用于用户定义和参考参数,但此方法可用于设置任何JDBC类型的空参数。如果参数没有用户定义或REF类型,则忽略给定的typeName
    * 
    * 
    * @param parameterName the name of the parameter
    * @param sqlType a value from <code>java.sql.Types</code>
    * @param typeName the fully-qualified name of an SQL user-defined type;
    *        ignored if the parameter is not a user-defined type or
    *        SQL <code>REF</code> value
    * @exception SQLException if a database access error occurs or
    * this method is called on a closed <code>CallableStatement</code>
    * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
    * this method
    * @since 1.4
    */
   public void setNull (String parameterName, int sqlType, String typeName)
       throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }



 /**
    * Sets the designated parameter to the given Java <code>boolean</code> value.
    * The driver converts this
    * to an SQL <code>BIT</code> or <code>BOOLEAN</code> value when it sends it to the database.
    *
    * <p>
    * 将指定的参数设置为给定的Java <code> boolean </code>值驱动程序将其发送到数据库时将其转换为SQL <code> BIT </code>或<code> BOOLEAN </code>
    * 。
    * 
    * 
    * @param parameterName the name of the parameter
    * @param x the parameter value
    * @exception SQLException if a database access error occurs or
    * this method is called on a closed <code>CallableStatement</code>
    * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
    * this method
    * @see #getParams
    * @since 1.4
    */
   public void setBoolean(String parameterName, boolean x) throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }



 /**
    * Sets the designated parameter to the given Java <code>byte</code> value.
    * The driver converts this
    * to an SQL <code>TINYINT</code> value when it sends it to the database.
    *
    * <p>
    *  将指定的参数设置为给定的Java <code> byte </code>值驱动程序在将其发送到数据库时将其转换为SQL <code> TINYINT </code>
    * 
    * 
    * @param parameterName the name of the parameter
    * @param x the parameter value
    * @exception SQLException if a database access error occurs or
    * this method is called on a closed <code>CallableStatement</code>
    * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
    * this method
    * @see #getParams
    * @since 1.4
    */
   public void setByte(String parameterName, byte x) throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }



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
    * @exception SQLException if a database access error occurs or
    * this method is called on a closed <code>CallableStatement</code>
    * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
    * this method
    * @see #getParams
    * @since 1.4
    */
   public void setShort(String parameterName, short x) throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }


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
    * @exception SQLException if a database access error occurs or
    * this method is called on a closed <code>CallableStatement</code>
    * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
    * this method
    * @see #getParams
    * @since 1.4
    */
   public void setInt(String parameterName, int x) throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }


 /**
    * Sets the designated parameter to the given Java <code>long</code> value.
    * The driver converts this
    * to an SQL <code>BIGINT</code> value when it sends it to the database.
    *
    * <p>
    * 将指定的参数设置为给定的Java <code> long </code>值驱动程序在将其发送到数据库时将其转换为SQL <code> BIGINT </code>
    * 
    * 
    * @param parameterName the name of the parameter
    * @param x the parameter value
    * @exception SQLException if a database access error occurs or
    * this method is called on a closed <code>CallableStatement</code>
    * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
    * this method
    * @see #getParams
    * @since 1.4
    */
   public void setLong(String parameterName, long x) throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }


 /**
    * Sets the designated parameter to the given Java <code>float</code> value.
    * The driver converts this
    * to an SQL <code>FLOAT</code> value when it sends it to the database.
    *
    * <p>
    *  将指定的参数设置为给定的Java <code> float </code>值当驱动程序将它发送到数据库时将其转换为SQL <code> FLOAT </code>
    * 
    * 
    * @param parameterName the name of the parameter
    * @param x the parameter value
    * @exception SQLException if a database access error occurs or
    * this method is called on a closed <code>CallableStatement</code>
    * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
    * this method
    * @see #getParams
    * @since 1.4
    */
   public void setFloat(String parameterName, float x) throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }


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
    * @exception SQLException if a database access error occurs or
    * this method is called on a closed <code>CallableStatement</code>
    * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
    * this method
    * @see #getParams
    * @since 1.4
    */
   public void setDouble(String parameterName, double x) throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }



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
    * @exception SQLException if a database access error occurs or
    * this method is called on a closed <code>CallableStatement</code>
    * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
    * this method
    * @see #getParams
    * @since 1.4
    */
   public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }



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
    * @exception SQLException if a database access error occurs or
    * this method is called on a closed <code>CallableStatement</code>
    * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
    * this method
    * @see #getParams
    * @since 1.4
    */
   public void setString(String parameterName, String x) throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }



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
    * @exception SQLException if a database access error occurs or
    * this method is called on a closed <code>CallableStatement</code>
    * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
    * this method
    * @see #getParams
    * @since 1.4
    */
   public void setBytes(String parameterName, byte x[]) throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }



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
    * @exception SQLException if a database access error occurs or
    * this method is called on a closed <code>CallableStatement</code>
    * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
    * this method
    * @see #getParams
    * @since 1.4
    */
   public void setTimestamp(String parameterName, java.sql.Timestamp x)
       throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }



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
    * @exception SQLException if a database access error occurs or
    * this method is called on a closed <code>CallableStatement</code>
    * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
    * this method
    * @since 1.4
    */
   public void setAsciiStream(String parameterName, java.io.InputStream x, int length)
       throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }


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
    * @exception SQLException if a database access error occurs or
    * this method is called on a closed <code>CallableStatement</code>
    * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
    * this method
    * @since 1.4
    */
   public void setBinaryStream(String parameterName, java.io.InputStream x,
                        int length) throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }


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
    * @exception SQLException if a database access error occurs or
    * this method is called on a closed <code>CallableStatement</code>
    * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
    * this method
    * @since 1.4
    */
   public void setCharacterStream(String parameterName,
                           java.io.Reader reader,
                           int length) throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }


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
   * @exception SQLException if a database access error occurs or
   * this method is called on a closed <code>CallableStatement</code>
   * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
     * @since 1.6
  */
  public void setAsciiStream(String parameterName, java.io.InputStream x)
          throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }


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
    * @exception SQLException if a database access error occurs or
    * this method is called on a closed <code>CallableStatement</code>
    * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
    * @since 1.6
    */
   public void setBinaryStream(String parameterName, java.io.InputStream x)
   throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }



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
    * @exception SQLException if a database access error occurs or
    * this method is called on a closed <code>CallableStatement</code>
    * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
    * @since 1.6
    */
   public void setCharacterStream(String parameterName,
                         java.io.Reader reader) throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }


 /**
  * Sets the designated parameter in this <code>RowSet</code> object's command
  * to a <code>Reader</code> object. The
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
  * 将<code> RowSet </code>对象的命令中的指定参数设置为<code> Reader </code>对象<code> Reader </code>读取数据,直到文件结束驱动程序从Java字
  * 符格式到数据库中的国家字符集的必要转换。
  * 
  *  <P> <B>注意：</B>此流对象可以是标准Java流对象或实现标准接口<P> <B>的自己的子类注意：</B>请参阅您的JDBC驱动程序文档确定是否可能更有效地使用一个版本的<code> setN
  * CharacterStream </code>,它需要一个长度参数。
  * 
  * 
  * @param parameterIndex of the first parameter is 1, the second is 2, ...
  * @param value the parameter value
  * @throws SQLException if the driver does not support national
  *         character sets;  if the driver can detect that a data conversion
  *  error could occur ; if a database access error occurs; or
  * this method is called on a closed <code>PreparedStatement</code>
  * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
  * @since 1.6
  */
  public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }



 /**
    * Sets the value of the designated parameter with the given object. The second
    * argument must be an object type; for integral values, the
    * <code>java.lang</code> equivalent objects should be used.
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
    * 使用给定对象设置指定参数的值第二个参数必须是对象类型;对于整数值,应该使用<code> javalang </code>等效对象
    * 
    *  <p>给定的Java对象在发送到数据库之前将被转换为给定的targetSqlType
    * 
    *  如果对象具有自定义映射(是实现接口<code> SQLData </code>的类),JDBC驱动程序应调用<code> SQLDatawriteSQL </code>方法将其写入SQL数据流If,另
    * 一方面,对象是实现<code> Ref </code>,<code> Blob </code>,<code> Clob </code>,<code> NClob </code>,<code > Stru
    * ct </code>,<code> javanetURL </code>或<code> Array </code>,驱动程序应将其作为相应SQL类型的值传递到数据库。
    * <P>
    * 请注意,此方法可用于传递特定于数据库的抽象数据类型
    * 
    * 
    * @param parameterName the name of the parameter
    * @param x the object containing the input parameter value
    * @param targetSqlType the SQL type (as defined in java.sql.Types) to be
    * sent to the database. The scale argument may further qualify this type.
    * @param scale for java.sql.Types.DECIMAL or java.sql.Types.NUMERIC types,
    *          this is the number of digits after the decimal point.  For all other
    *          types, this value will be ignored.
    * @exception SQLException if a database access error occurs or
    * this method is called on a closed <code>CallableStatement</code>
    * @exception SQLFeatureNotSupportedException if <code>targetSqlType</code> is
    * a <code>ARRAY</code>, <code>BLOB</code>, <code>CLOB</code>,
    * <code>DATALINK</code>, <code>JAVA_OBJECT</code>, <code>NCHAR</code>,
    * <code>NCLOB</code>, <code>NVARCHAR</code>, <code>LONGNVARCHAR</code>,
    *  <code>REF</code>, <code>ROWID</code>, <code>SQLXML</code>
    * or  <code>STRUCT</code> data type and the JDBC driver does not support
    * this data type
    * @see Types
    * @see #getParams
    * @since 1.4
    */
   public void setObject(String parameterName, Object x, int targetSqlType, int scale)
       throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }



 /**
    * Sets the value of the designated parameter with the given object.
    * This method is like the method <code>setObject</code>
    * above, except that it assumes a scale of zero.
    *
    * <p>
    *  使用给定对象设置指定参数的值此方法类似于上面的方法<code> setObject </code>,除了它假定为零
    * 
    * 
    * @param parameterName the name of the parameter
    * @param x the object containing the input parameter value
    * @param targetSqlType the SQL type (as defined in java.sql.Types) to be
    *                      sent to the database
    * @exception SQLException if a database access error occurs or
    * this method is called on a closed <code>CallableStatement</code>
    * @exception SQLFeatureNotSupportedException if <code>targetSqlType</code> is
    * a <code>ARRAY</code>, <code>BLOB</code>, <code>CLOB</code>,
    * <code>DATALINK</code>, <code>JAVA_OBJECT</code>, <code>NCHAR</code>,
    * <code>NCLOB</code>, <code>NVARCHAR</code>, <code>LONGNVARCHAR</code>,
    *  <code>REF</code>, <code>ROWID</code>, <code>SQLXML</code>
    * or  <code>STRUCT</code> data type and the JDBC driver does not support
    * this data type
    * @see #getParams
    * @since 1.4
    */
   public void setObject(String parameterName, Object x, int targetSqlType)
       throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }


 /**
   * Sets the value of the designated parameter with the given object.
   * The second parameter must be of type <code>Object</code>; therefore, the
   * <code>java.lang</code> equivalent objects should be used for built-in types.
   *
   * <p>The JDBC specification specifies a standard mapping from
   * Java <code>Object</code> types to SQL types.  The given argument
   * will be converted to the corresponding SQL type before being
   * sent to the database.
   *
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
   *
   * <p>
   *  使用给定对象设置指定参数的值第二个参数必须是类型<code> Object </code>;因此,<code> javalang </code>等效对象应该用于内置类型
   * 
   *  <p> JDBC规范指定从Java <code> Object </code>类型到SQL类型的标准映射给定参数在发送到数据库之前将转换为相应的SQL类型
   * 
   * <p>请注意,此方法可用于传递特定于数据库的抽象数据类型,通过使用驱动程序特定的Java类型
   * 
   *  如果对象是实现接口<code> SQLData </code>的类,JDBC驱动程序应调用<code> SQLDatawriteSQL </code>方法将其写入SQL数据流。
   * 另一方面,对象是实现<code> Ref </code>,<code> Blob </code>,<code> Clob </code>,<code> NClob </code>,<code> Stru
   * ct </code> ,<code> javanetURL </code>或<code> Array </code>,驱动程序应将其作为相应SQL类型的值传递到数据库。
   *  如果对象是实现接口<code> SQLData </code>的类,JDBC驱动程序应调用<code> SQLDatawriteSQL </code>方法将其写入SQL数据流。
   * <P>
   *  如果存在歧义,此方法将抛出异常,例如,如果对象是实现多个上面命名的接口的类
   * 
   * 
   * @param parameterName the name of the parameter
   * @param x the object containing the input parameter value
   * @exception SQLException if a database access error occurs,
   * this method is called on a closed <code>CallableStatement</code> or if the given
   *            <code>Object</code> parameter is ambiguous
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @see #getParams
   * @since 1.4
   */
  public void setObject(String parameterName, Object x) throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }



 /**
    * Sets the designated parameter to a <code>InputStream</code> object.  The inputstream must contain  the number
    * of characters specified by length otherwise a <code>SQLException</code> will be
    * generated when the <code>PreparedStatement</code> is executed.
    * This method differs from the <code>setBinaryStream (int, InputStream, int)</code>
    * method because it informs the driver that the parameter value should be
    * sent to the server as a <code>BLOB</code>.  When the <code>setBinaryStream</code> method is used,
    * the driver may have to do extra work to determine whether the parameter
    * data should be sent to the server as a <code>LONGVARBINARY</code> or a <code>BLOB</code>
    * <p>
    * 将指定的参数设置为<code> InputStream </code>对象输入流必须包含由长度指定的字符数,否则在<code> PreparedStatement </code>为时将生成<code> 
    * SQLException </code> execution此方法与<code> setBinaryStream(int,InputStream,int)</code>方法不同,因为它通知驱动程序应将参
    * 数值作为<code> BLOB </code>发送到服务器。
    * 代码> setBinaryStream </code>方法,驱动程序可能必须做额外的工作来确定参数数据是否应作为<code> LONGVARBINARY </code>或<code> BLOB </code >
    * 。
    * 
    * 
    * @param parameterIndex index of the first parameter is 1,
    * the second is 2, ...
    * @param inputStream An object that contains the data to set the parameter
    * value to.
    * @param length the number of bytes in the parameter data.
    * @throws SQLException if a database access error occurs,
    * this method is called on a closed <code>PreparedStatement</code>,
    * if parameterIndex does not correspond
    * to a parameter marker in the SQL statement,  if the length specified
    * is less than zero or if the number of bytes in the inputstream does not match
    * the specified length.
    * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
    *
    * @since 1.6
    */
    public void setBlob(int parameterIndex, InputStream inputStream, long length)
       throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }


 /**
    * Sets the designated parameter to a <code>InputStream</code> object.
    * This method differs from the <code>setBinaryStream (int, InputStream)</code>
    * method because it informs the driver that the parameter value should be
    * sent to the server as a <code>BLOB</code>.  When the <code>setBinaryStream</code> method is used,
    * the driver may have to do extra work to determine whether the parameter
    * data should be sent to the server as a <code>LONGVARBINARY</code> or a <code>BLOB</code>
    *
    * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
    * it might be more efficient to use a version of
    * <code>setBlob</code> which takes a length parameter.
    *
    * <p>
    * 将指定的参数设置为<code> InputStream </code>对象此方法与<code> setBinaryStream(int,InputStream)</code>方法不同,因为它通知驱动程序
    * 应将参数值发送到服务器a <code> BLOB </code>当使用<code> setBinaryStream </code>方法时,驱动程序可能需要做额外的工作来确定参数数据是否应当作为<code>
    *  LONGVARBINARY < / code>或<code> BLOB </code>。
    * 
    *  <P> <B>注意：</B>请查看您的JDBC驱动程序文档,以确定是否使用一个版本的<code> setBlob </code>
    * 
    * 
    * @param parameterIndex index of the first parameter is 1,
    * the second is 2, ...
    * @param inputStream An object that contains the data to set the parameter
    * value to.
    * @throws SQLException if a database access error occurs,
    * this method is called on a closed <code>PreparedStatement</code> or
    * if parameterIndex does not correspond
    * to a parameter marker in the SQL statement,
    * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
    *
    * @since 1.6
    */
    public void setBlob(int parameterIndex, InputStream inputStream)
       throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }


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
     * @throws SQLException  if parameterIndex does not correspond
     * to a parameter marker in the SQL statement,  or if the length specified
     * is less than zero; if the number of bytes in the inputstream does not match
     * the specified length; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     *
     * @since 1.6
     */
     public void setBlob(String parameterName, InputStream inputStream, long length)
        throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }


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
    * @exception SQLException if a database access error occurs or
    * this method is called on a closed <code>CallableStatement</code>
    * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
    * this method
    * @since 1.6
    */
   public void setBlob (String parameterName, Blob x) throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }


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
    *  将指定的参数设置为<code> InputStream </code>对象此方法与<code> setBinaryStream(int,InputStream)</code>方法不同,因为它通知驱动程
    * 序应将参数值发送到服务器a <code> BLOB </code>当使用<code> setBinaryStream </code>方法时,驱动程序可能需要做额外的工作来确定参数数据是否应该作为<code>
    *  LONGVARBINARY < / code>或<code> BLOB </code>。
    * 
    * <P> <B>注意：</B>请查看您的JDBC驱动程序文档,以确定是否使用一个版本的<code> setBlob </code>
    * 
    * 
    * @param parameterName the name of the parameter
    * @param inputStream An object that contains the data to set the parameter
    * value to.
    * @throws SQLException if a database access error occurs or
    * this method is called on a closed <code>CallableStatement</code>
    * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
    *
    * @since 1.6
    */
    public void setBlob(String parameterName, InputStream inputStream)
       throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }


 /**
   * Sets the designated parameter to a <code>Reader</code> object.  The reader must contain  the number
   * of characters specified by length otherwise a <code>SQLException</code> will be
   * generated when the <code>PreparedStatement</code> is executed.
   *This method differs from the <code>setCharacterStream (int, Reader, int)</code> method
   * because it informs the driver that the parameter value should be sent to
   * the server as a <code>CLOB</code>.  When the <code>setCharacterStream</code> method is used, the
   * driver may have to do extra work to determine whether the parameter
   * data should be sent to the server as a <code>LONGVARCHAR</code> or a <code>CLOB</code>
   * <p>
   * 将指定的参数设置为<code> Reader </code>对象读取器必须包含由长度指定的字符数,否则<code> PreparedStatement </code>为时将生成<code> SQLExc
   * eption </code>执行的方法不同于<code> setCharacterStream(int,Reader,int)</code>方法,因为它通知驱动程序应将参数值作为<code> CLOB 
   * </code>发送到服务器。
   * 代码> setCharacterStream </code>方法,驱动程序可能需要做额外的工作来确定参数数据是否应作为<code> LONGVARCHAR </code>或<code> CLOB </code>
   * 发送到服务器>。
   * 
   * 
   * @param parameterIndex index of the first parameter is 1, the second is 2, ...
   * @param reader An object that contains the data to set the parameter value to.
   * @param length the number of characters in the parameter data.
   * @throws SQLException if a database access error occurs, this method is called on
   * a closed <code>PreparedStatement</code>, if parameterIndex does not correspond to a parameter
   * marker in the SQL statement, or if the length specified is less than zero.
   *
   * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
   * @since 1.6
   */
   public void setClob(int parameterIndex, Reader reader, long length)
     throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }


/**
   * Sets the designated parameter to a <code>Reader</code> object.
   * This method differs from the <code>setCharacterStream (int, Reader)</code> method
   * because it informs the driver that the parameter value should be sent to
   * the server as a <code>CLOB</code>.  When the <code>setCharacterStream</code> method is used, the
   * driver may have to do extra work to determine whether the parameter
   * data should be sent to the server as a <code>LONGVARCHAR</code> or a <code>CLOB</code>
   *
   * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
   * it might be more efficient to use a version of
   * <code>setClob</code> which takes a length parameter.
   *
   * <p>
   * 将指定的参数设置为<code> Reader </code>对象此方法不同于<code> setCharacterStream(int,Reader)</code>方法,因为它通知驱动程序应将参数值发送
   * 到服务器a <code> CLOB </code>当使用<code> setCharacterStream </code>方法时,驱动程序可能需要做额外的工作来确定参数数据是否应该作为<code> LO
   * NGVARCHAR < / code>或<code> CLOB </code>。
   * 
   *  <P> <B>注意：</B>请查看您的JDBC驱动程序文档,以确定是否使用一个版本的<code> setClob </code>
   * 
   * 
   * @param parameterIndex index of the first parameter is 1, the second is 2, ...
   * @param reader An object that contains the data to set the parameter value to.
   * @throws SQLException if a database access error occurs, this method is called on
   * a closed <code>PreparedStatement</code>or if parameterIndex does not correspond to a parameter
   * marker in the SQL statement
   *
   * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
   * @since 1.6
   */
   public void setClob(int parameterIndex, Reader reader)
     throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }


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
              * @throws SQLException if parameterIndex does not correspond to a parameter
              * marker in the SQL statement; if the length specified is less than zero;
              * a database access error occurs or
              * this method is called on a closed <code>CallableStatement</code>
              * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
              * this method
              *
              * @since 1.6
              */
              public void setClob(String parameterName, Reader reader, long length)
      throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }


  /**
    * Sets the designated parameter to the given <code>java.sql.Clob</code> object.
    * The driver converts this to an SQL <code>CLOB</code> value when it
    * sends it to the database.
    *
    * <p>
    * 将指定的参数设置为给定的<code> javasqlClob </code>对象当驱动程序将它发送到数据库时,将其转换为SQL <code> CLOB </code>
    * 
    * 
    * @param parameterName the name of the parameter
    * @param x a <code>Clob</code> object that maps an SQL <code>CLOB</code> value
    * @exception SQLException if a database access error occurs or
    * this method is called on a closed <code>CallableStatement</code>
    * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
    * this method
    * @since 1.6
    */
   public void setClob (String parameterName, Clob x) throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }


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
    *  将指定的参数设置为<code> Reader </code>对象此方法与<code> setCharacterStream(int,Reader)</code>方法不同,因为它通知驱动程序应将参数值发
    * 送到服务器a <code> CLOB </code>当使用<code> setCharacterStream </code>方法时,驱动程序可能需要做额外的工作来确定参数数据是否应该作为<code> L
    * ONGVARCHAR < / code>或<code> CLOB </code>。
    * 
    * <P> <B>注意：</B>请查看您的JDBC驱动程序文档,以确定是否使用一个版本的<code> setClob </code>
    * 
    * 
    * @param parameterName the name of the parameter
    * @param reader An object that contains the data to set the parameter value to.
    * @throws SQLException if a database access error occurs or this method is called on
    * a closed <code>CallableStatement</code>
    *
    * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
    * @since 1.6
    */
    public void setClob(String parameterName, Reader reader)
      throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }


 /**
    * Sets the designated parameter to the given <code>java.sql.Date</code> value
    * using the default time zone of the virtual machine that is running
    * the application.
    * The driver converts this
    * to an SQL <code>DATE</code> value when it sends it to the database.
    *
    * <p>
    *  使用运行应用程序的虚拟机的默认时区将指定的参数设置为给定的<code> javasqlDate </code>值驱动程序在发送时将其转换为SQL <code> DATE </code>到数据库
    * 
    * 
    * @param parameterName the name of the parameter
    * @param x the parameter value
    * @exception SQLException if a database access error occurs or
    * this method is called on a closed <code>CallableStatement</code>
    * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
    * this method
    * @see #getParams
    * @since 1.4
    */
   public void setDate(String parameterName, java.sql.Date x)
       throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }


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
    * 使用给定的<code> Calendar </code>对象将指定的参数设置为给定的<code> javasqlDate </code>值驱动程序使用<code> Calendar </code>对象构
    * 造SQL <code> DATE </code>对象,驱动程序可以计算考虑自定义时区的日期。
    * 如果没有<code> Calendar </code>对象,则驱动程序会发送到数据库。指定,驱动程序使用默认时区,这是运行应用程序的虚拟机的时区。
    * 
    * 
    * @param parameterName the name of the parameter
    * @param x the parameter value
    * @param cal the <code>Calendar</code> object the driver will use
    *            to construct the date
    * @exception SQLException if a database access error occurs or
    * this method is called on a closed <code>CallableStatement</code>
    * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
    * this method
    * @see #getParams
    * @since 1.4
    */
   public void setDate(String parameterName, java.sql.Date x, Calendar cal)
       throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }


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
    * @exception SQLException if a database access error occurs or
    * this method is called on a closed <code>CallableStatement</code>
    * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
    * this method
    * @see #getParams
    * @since 1.4
    */
   public void setTime(String parameterName, java.sql.Time x)
       throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }


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
    * @exception SQLException if a database access error occurs or
    * this method is called on a closed <code>CallableStatement</code>
    * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
    * this method
    * @see #getParams
    * @since 1.4
    */
   public void setTime(String parameterName, java.sql.Time x, Calendar cal)
       throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }


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
    * @exception SQLException if a database access error occurs or
    * this method is called on a closed <code>CallableStatement</code>
    * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
    * this method
    * @see #getParams
    * @since 1.4
    */
   public void setTimestamp(String parameterName, java.sql.Timestamp x, Calendar cal)
       throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }


 /**
  * Sets the designated parameter to the given <code>java.sql.SQLXML</code> object. The driver converts this to an
  * SQL <code>XML</code> value when it sends it to the database.
  * <p>
  *  将指定的参数设置为给定的<code> javasqlSQLXML </code>对象当驱动程序将它发送到数据库时,将其转换为SQL <code> XML </code>
  * 
  * 
  * @param parameterIndex index of the first parameter is 1, the second is 2, ...
  * @param xmlObject a <code>SQLXML</code> object that maps an SQL <code>XML</code> value
  * @throws SQLException if a database access error occurs, this method
  *  is called on a closed result set,
  * the <code>java.xml.transform.Result</code>,
  *  <code>Writer</code> or <code>OutputStream</code> has not been closed
  * for the <code>SQLXML</code> object  or
  *  if there is an error processing the XML value.  The <code>getCause</code> method
  *  of the exception may provide a more detailed exception, for example, if the
  *  stream does not contain valid XML.
  * @throws SQLFeatureNotSupportedException if the JDBC driver does not
  * support this method
  * @since 1.6
  */
 public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException{
     throw new SQLFeatureNotSupportedException("Feature not supported");
 }


 /**
  * Sets the designated parameter to the given <code>java.sql.SQLXML</code> object. The driver converts this to an
  * <code>SQL XML</code> value when it sends it to the database.
  * <p>
  * 将指定的参数设置为给定的<code> javasqlSQLXML </code>对象当驱动程序将其发送到数据库时将其转换为<code> SQL XML </code>
  * 
  * 
  * @param parameterName the name of the parameter
  * @param xmlObject a <code>SQLXML</code> object that maps an <code>SQL XML</code> value
  * @throws SQLException if a database access error occurs, this method
  *  is called on a closed result set,
  * the <code>java.xml.transform.Result</code>,
  *  <code>Writer</code> or <code>OutputStream</code> has not been closed
  * for the <code>SQLXML</code> object  or
  *  if there is an error processing the XML value.  The <code>getCause</code> method
  *  of the exception may provide a more detailed exception, for example, if the
  *  stream does not contain valid XML.
  * @throws SQLFeatureNotSupportedException if the JDBC driver does not
  * support this method
  * @since 1.6
  */
 public void setSQLXML(String parameterName, SQLXML xmlObject) throws SQLException{
     throw new SQLFeatureNotSupportedException("Feature not supported");
 }


 /**
  * Sets the designated parameter to the given <code>java.sql.RowId</code> object. The
  * driver converts this to a SQL <code>ROWID</code> value when it sends it
  * to the database
  *
  * <p>
  *  将指定的参数设置为给定的<code> javasqlRowId </code>对象当驱动程序将它发送到数据库时,将其转换为SQL <code> ROWID </code>
  * 
  * 
  * @param parameterIndex the first parameter is 1, the second is 2, ...
  * @param x the parameter value
  * @throws SQLException if a database access error occurs
  * @throws SQLFeatureNotSupportedException if the JDBC driver does not
  * support this method
  *
  * @since 1.6
  */
 public void setRowId(int parameterIndex, RowId x) throws SQLException{
     throw new SQLFeatureNotSupportedException("Feature not supported");
 }


 /**
  * Sets the designated parameter to the given <code>java.sql.RowId</code> object. The
  * driver converts this to a SQL <code>ROWID</code> when it sends it to the
  * database.
  *
  * <p>
  *  将指定的参数设置为给定的<code> javasqlRowId </code>对象当驱动程序将其发送到数据库时将其转换为SQL <code> ROWID </code>
  * 
  * 
  * @param parameterName the name of the parameter
  * @param x the parameter value
  * @throws SQLException if a database access error occurs
  * @throws SQLFeatureNotSupportedException if the JDBC driver does not
  * support this method
  * @since 1.6
  */
 public void setRowId(String parameterName, RowId x) throws SQLException{
     throw new SQLFeatureNotSupportedException("Feature not supported");
 }

 /**
  * Sets the designated parameter to the given <code>String</code> object.
  * The driver converts this to a SQL <code>NCHAR</code> or
  * <code>NVARCHAR</code> or <code>LONGNVARCHAR</code> value
  * (depending on the argument's
  * size relative to the driver's limits on <code>NVARCHAR</code> values)
  * when it sends it to the database.
  *
  * <p>
  * 将指定的参数设置为给定的<code> String </code>对象驱动程序将其转换为SQL <code> NCHAR </code>或<code> NVARCHAR </code>或<code> L
  * ONGNVARCHAR </code> (取决于参数的大小相对于驱动程序对<code> NVARCHAR </code>值的限制),当它发送到数据库。
  * 
  * 
  * @param parameterIndex of the first parameter is 1, the second is 2, ...
  * @param value the parameter value
  * @throws SQLException if the driver does not support national
  *         character sets;  if the driver can detect that a data conversion
  *  error could occur ; or if a database access error occurs
  * @throws SQLFeatureNotSupportedException if the JDBC driver does not
  * support this method
  * @since 1.6
  */
 public void setNString(int parameterIndex, String value) throws SQLException{
     throw new SQLFeatureNotSupportedException("Feature not supported");
 }


 /**
  * Sets the designated parameter to the given <code>String</code> object.
  * The driver converts this to a SQL <code>NCHAR</code> or
  * <code>NVARCHAR</code> or <code>LONGNVARCHAR</code>
  * <p>
  *  将指定的参数设置为给定的<code> String </code>对象驱动程序将其转换为SQL <code> NCHAR </code>或<code> NVARCHAR </code>或<code> 
  * LONGNVARCHAR </code>。
  * 
  * 
  * @param parameterName the name of the column to be set
  * @param value the parameter value
  * @throws SQLException if the driver does not support national
  *         character sets;  if the driver can detect that a data conversion
  *  error could occur; or if a database access error occurs
  * @throws SQLFeatureNotSupportedException if the JDBC driver does not
  * support this method
  * @since 1.6
  */
 public void setNString(String parameterName, String value)
         throws SQLException{
     throw new SQLFeatureNotSupportedException("Feature not supported");
 }


 /**
  * Sets the designated parameter to a <code>Reader</code> object. The
  * <code>Reader</code> reads the data till end-of-file is reached. The
  * driver does the necessary conversion from Java character format to
  * the national character set in the database.
  * <p>
  *  将指定的参数设置为<code> Reader </code>对象<code> Reader </code>读取数据,直到达到文件结束驱动程序执行必要的从Java字符格式到国家字符集的转换在数据库中
  * 
  * 
  * @param parameterIndex of the first parameter is 1, the second is 2, ...
  * @param value the parameter value
  * @param length the number of characters in the parameter data.
  * @throws SQLException if the driver does not support national
  *         character sets;  if the driver can detect that a data conversion
  *  error could occur ; or if a database access error occurs
  * @throws SQLFeatureNotSupportedException if the JDBC driver does not
  * support this method
  * @since 1.6
  */
 public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException{
     throw new SQLFeatureNotSupportedException("Feature not supported");
 }


 /**
  * Sets the designated parameter to a <code>Reader</code> object. The
  * <code>Reader</code> reads the data till end-of-file is reached. The
  * driver does the necessary conversion from Java character format to
  * the national character set in the database.
  * <p>
  * 将指定的参数设置为<code> Reader </code>对象<code> Reader </code>读取数据,直到达到文件结束驱动程序执行必要的从Java字符格式到国家字符集的转换在数据库中
  * 
  * 
  * @param parameterName the name of the column to be set
  * @param value the parameter value
  * @param length the number of characters in the parameter data.
  * @throws SQLException if the driver does not support national
  *         character sets;  if the driver can detect that a data conversion
  *  error could occur; or if a database access error occurs
  * @throws SQLFeatureNotSupportedException  if the JDBC driver does not
  * support this method
  * @since 1.6
  */
 public void setNCharacterStream(String parameterName, Reader value, long length)
         throws SQLException{
     throw new SQLFeatureNotSupportedException("Feature not supported");
 }


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
  *  将指定的参数设置为<code> Reader </code>对象<code> Reader </code>读取数据,直到达到文件结束驱动程序执行必要的从Java字符格式到国家字符集的转换在数据库中
  * 
  * <P> <B>注意：</B>此流对象可以是标准Java流对象或实现标准接口<P> <B>的自己的子类注意：</B>请参阅您的JDBC驱动程序文档确定是否可能更有效地使用一个版本的<code> setNC
  * haracterStream </code>,它需要一个长度参数。
  * 
  * 
  * @param parameterName the name of the parameter
  * @param value the parameter value
  * @throws SQLException if the driver does not support national
  *         character sets;  if the driver can detect that a data conversion
  *  error could occur ; if a database access error occurs; or
  * this method is called on a closed <code>CallableStatement</code>
  * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
  * @since 1.6
  */
  public void setNCharacterStream(String parameterName, Reader value) throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
   }


  /**
   * Sets the designated parameter to a <code>java.sql.NClob</code> object. The object
   * implements the <code>java.sql.NClob</code> interface. This <code>NClob</code>
   * object maps to a SQL <code>NCLOB</code>.
   * <p>
   *  将指定的参数设置为<code> javasqlNClob </code>对象该对象实现<code> javasqlNClob </code>接口此<code> NClob </code>对象映射到SQ
   * L <code> NCLOB </code>。
   * 
   * 
   * @param parameterName the name of the column to be set
   * @param value the parameter value
   * @throws SQLException if the driver does not support national
   *         character sets;  if the driver can detect that a data conversion
   *  error could occur; or if a database access error occurs
   * @throws SQLFeatureNotSupportedException  if the JDBC driver does not
   * support this method
   * @since 1.6
   */
  public void setNClob(String parameterName, NClob value) throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
  }


  /**
   * Sets the designated parameter to a <code>Reader</code> object.  The <code>reader</code> must contain
   * the number
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
   * @throws SQLException if parameterIndex does not correspond to a parameter
   * marker in the SQL statement; if the length specified is less than zero;
   * if the driver does not support national
   *         character sets;  if the driver can detect that a data conversion
   *  error could occur; if a database access error occurs or
   * this method is called on a closed <code>CallableStatement</code>
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.6
   */
  public void setNClob(String parameterName, Reader reader, long length)
           throws SQLException{
       throw new SQLFeatureNotSupportedException("Feature not supported");
  }


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
   * @throws SQLException if the driver does not support national character sets;
   * if the driver can detect that a data conversion
   *  error could occur;  if a database access error occurs or
   * this method is called on a closed <code>CallableStatement</code>
   * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
   *
   * @since 1.6
   */
  public void setNClob(String parameterName, Reader reader)
    throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
  }


  /**
   * Sets the designated parameter to a <code>Reader</code> object.  The reader must contain  the number
   * of characters specified by length otherwise a <code>SQLException</code> will be
   * generated when the <code>PreparedStatement</code> is executed.
   * This method differs from the <code>setCharacterStream (int, Reader, int)</code> method
   * because it informs the driver that the parameter value should be sent to
   * the server as a <code>NCLOB</code>.  When the <code>setCharacterStream</code> method is used, the
   * driver may have to do extra work to determine whether the parameter
   * data should be sent to the server as a <code>LONGNVARCHAR</code> or a <code>NCLOB</code>
   * <p>
   * 将指定的参数设置为<code> Reader </code>对象读取器必须包含由长度指定的字符数,否则<code> PreparedStatement </code>为时将生成<code> SQLExc
   * eption </code> execution此方法与<code> setCharacterStream(int,Reader,int)</code>方法不同,因为它通知驱动程序参数值应作为<code>
   *  NCLOB </code>发送到服务器。
   * 代码> setCharacterStream </code>方法时,驱动程序可能必须做额外的工作来确定参数数据是否应作为<code> LONGNVARCHAR </code>或<code> NCLOB 
   * </code >。
   * 
   * 
   * @param parameterIndex index of the first parameter is 1, the second is 2, ...
   * @param reader An object that contains the data to set the parameter value to.
   * @param length the number of characters in the parameter data.
   * @throws SQLException if parameterIndex does not correspond to a parameter
   * marker in the SQL statement; if the length specified is less than zero;
   * if the driver does not support national character sets;
   * if the driver can detect that a data conversion
   *  error could occur;  if a database access error occurs or
   * this method is called on a closed <code>PreparedStatement</code>
   * @throws SQLFeatureNotSupportedException  if the JDBC driver does not
   * support this method
   *
   * @since 1.6
   */
  public void setNClob(int parameterIndex, Reader reader, long length)
       throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
  }


  /**
   * Sets the designated parameter to a <code>java.sql.NClob</code> object. The driver converts this oa
   * SQL <code>NCLOB</code> value when it sends it to the database.
   * <p>
   * 将指定的参数设置为<code> javasqlNClob </code>对象驱动程序在将其发送到数据库时转换此oa SQL <code> NCLOB </code>
   * 
   * 
   * @param parameterIndex of the first parameter is 1, the second is 2, ...
   * @param value the parameter value
   * @throws SQLException if the driver does not support national
   *         character sets;  if the driver can detect that a data conversion
   *  error could occur ; or if a database access error occurs
   * @throws SQLFeatureNotSupportedException  if the JDBC driver does not
   * support this method
   * @since 1.6
   */
 public void setNClob(int parameterIndex, NClob value) throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
 }


 /**
  * Sets the designated parameter to a <code>Reader</code> object.
  * This method differs from the <code>setCharacterStream (int, Reader)</code> method
  * because it informs the driver that the parameter value should be sent to
  * the server as a <code>NCLOB</code>.  When the <code>setCharacterStream</code> method is used, the
  * driver may have to do extra work to determine whether the parameter
  * data should be sent to the server as a <code>LONGNVARCHAR</code> or a <code>NCLOB</code>
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
  * @param parameterIndex index of the first parameter is 1, the second is 2, ...
  * @param reader An object that contains the data to set the parameter value to.
  * @throws SQLException if parameterIndex does not correspond to a parameter
  * marker in the SQL statement;
  * if the driver does not support national character sets;
  * if the driver can detect that a data conversion
  *  error could occur;  if a database access error occurs or
  * this method is called on a closed <code>PreparedStatement</code>
  * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
  *
  * @since 1.6
  */
  public void setNClob(int parameterIndex, Reader reader)
    throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
  }


  /**
   * Sets the designated parameter to the given <code>java.net.URL</code> value.
   * The driver converts this to an SQL <code>DATALINK</code> value
   * when it sends it to the database.
   *
   * <p>
   * 
   * 
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param x the <code>java.net.URL</code> object to be set
   * @exception SQLException if a database access error occurs or
   * this method is called on a closed <code>PreparedStatement</code>
   * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
   * @since 1.4
   */
  public void setURL(int parameterIndex, java.net.URL x) throws SQLException{
        throw new SQLFeatureNotSupportedException("Feature not supported");
  }



  static final long serialVersionUID = 4886719666485113312L;

} //end class
