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
import java.util.*;

import javax.sql.rowset.spi.*;

/**
 * The interface that all standard implementations of
 * <code>CachedRowSet</code> must implement.
 * <P>
 * The reference implementation of the <code>CachedRowSet</code> interface provided
 * by Oracle Corporation is a standard implementation. Developers may use this implementation
 * just as it is, they may extend it, or they may choose to write their own implementations
 * of this interface.
 * <P>
 * A <code>CachedRowSet</code> object is a container for rows of data
 * that caches its rows in memory, which makes it possible to operate without always being
 * connected to its data source. Further, it is a
 * JavaBeans&trade; component and is scrollable,
 * updatable, and serializable. A <code>CachedRowSet</code> object typically
 * contains rows from a result set, but it can also contain rows from any file
 * with a tabular format, such as a spread sheet.  The reference implementation
 * supports getting data only from a <code>ResultSet</code> object, but
 * developers can extend the <code>SyncProvider</code> implementations to provide
 * access to other tabular data sources.
 * <P>
 * An application can modify the data in a <code>CachedRowSet</code> object, and
 * those modifications can then be propagated back to the source of the data.
 * <P>
 * A <code>CachedRowSet</code> object is a <i>disconnected</i> rowset, which means
 * that it makes use of a connection to its data source only briefly. It connects to its
 * data source while it is reading data to populate itself with rows and again
 * while it is propagating changes back to its underlying data source. The rest
 * of the time, a <code>CachedRowSet</code> object is disconnected, including
 * while its data is being modified. Being disconnected makes a <code>RowSet</code>
 * object much leaner and therefore much easier to pass to another component.  For
 * example, a disconnected <code>RowSet</code> object can be serialized and passed
 * over the wire to a thin client such as a personal digital assistant (PDA).
 *
 *
 * <h3>1.0 Creating a <code>CachedRowSet</code> Object</h3>
 * The following line of code uses the default constructor for
 * <code>CachedRowSet</code>
 * supplied in the reference implementation (RI) to create a default
 * <code>CachedRowSet</code> object.
 * <PRE>
 *     CachedRowSetImpl crs = new CachedRowSetImpl();
 * </PRE>
 * This new <code>CachedRowSet</code> object will have its properties set to the
 * default properties of a <code>BaseRowSet</code> object, and, in addition, it will
 * have an <code>RIOptimisticProvider</code> object as its synchronization provider.
 * <code>RIOptimisticProvider</code>, one of two <code>SyncProvider</code>
 * implementations included in the RI, is the default provider that the
 * <code>SyncFactory</code> singleton will supply when no synchronization
 * provider is specified.
 * <P>
 * A <code>SyncProvider</code> object provides a <code>CachedRowSet</code> object
 * with a reader (a <code>RowSetReader</code> object) for reading data from a
 * data source to populate itself with data. A reader can be implemented to read
 * data from a <code>ResultSet</code> object or from a file with a tabular format.
 * A <code>SyncProvider</code> object also provides
 * a writer (a <code>RowSetWriter</code> object) for synchronizing any
 * modifications to the <code>CachedRowSet</code> object's data made while it was
 * disconnected with the data in the underlying data source.
 * <P>
 * A writer can be implemented to exercise various degrees of care in checking
 * for conflicts and in avoiding them.
 * (A conflict occurs when a value in the data source has been changed after
 * the rowset populated itself with that value.)
 * The <code>RIOptimisticProvider</code> implementation assumes there will be
 * few or no conflicts and therefore sets no locks. It updates the data source
 * with values from the <code>CachedRowSet</code> object only if there are no
 * conflicts.
 * Other writers can be implemented so that they always write modified data to
 * the data source, which can be accomplished either by not checking for conflicts
 * or, on the other end of the spectrum, by setting locks sufficient to prevent data
 * in the data source from being changed. Still other writer implementations can be
 * somewhere in between.
 * <P>
 * A <code>CachedRowSet</code> object may use any
 * <code>SyncProvider</code> implementation that has been registered
 * with the <code>SyncFactory</code> singleton. An application
 * can find out which <code>SyncProvider</code> implementations have been
 * registered by calling the following line of code.
 * <PRE>
 *      java.util.Enumeration providers = SyncFactory.getRegisteredProviders();
 * </PRE>
 * <P>
 * There are two ways for a <code>CachedRowSet</code> object to specify which
 * <code>SyncProvider</code> object it will use.
 * <UL>
 *     <LI>Supplying the name of the implementation to the constructor<BR>
 *     The following line of code creates the <code>CachedRowSet</code>
 *     object <i>crs2</i> that is initialized with default values except that its
 *     <code>SyncProvider</code> object is the one specified.
 *     <PRE>
 *          CachedRowSetImpl crs2 = new CachedRowSetImpl(
 *                                 "com.fred.providers.HighAvailabilityProvider");
 *     </PRE>
 *     <LI>Setting the <code>SyncProvider</code> using the <code>CachedRowSet</code>
 *         method <code>setSyncProvider</code><BR>
 *      The following line of code resets the <code>SyncProvider</code> object
 *      for <i>crs</i>, the <code>CachedRowSet</code> object created with the
 *      default constructor.
 *      <PRE>
 *           crs.setSyncProvider("com.fred.providers.HighAvailabilityProvider");
 *      </PRE>
 * </UL>
 * See the comments for <code>SyncFactory</code> and <code>SyncProvider</code> for
 * more details.
 *
 *
 * <h3>2.0 Retrieving Data from a <code>CachedRowSet</code> Object</h3>
 * Data is retrieved from a <code>CachedRowSet</code> object by using the
 * getter methods inherited from the <code>ResultSet</code>
 * interface.  The following examples, in which <code>crs</code> is a
 * <code>CachedRowSet</code>
 * object, demonstrate how to iterate through the rows, retrieving the column
 * values in each row.  The first example uses the version of the
 * getter methods that take a column number; the second example
 * uses the version that takes a column name. Column numbers are generally
 * used when the <code>RowSet</code> object's command
 * is of the form <code>SELECT * FROM TABLENAME</code>; column names are most
 * commonly used when the command specifies columns by name.
 * <PRE>
 *    while (crs.next()) {
 *        String name = crs.getString(1);
 *        int id = crs.getInt(2);
 *        Clob comment = crs.getClob(3);
 *        short dept = crs.getShort(4);
 *        System.out.println(name + "  " + id + "  " + comment + "  " + dept);
 *    }
 * </PRE>
 *
 * <PRE>
 *    while (crs.next()) {
 *        String name = crs.getString("NAME");
 *        int id = crs.getInt("ID");
 *        Clob comment = crs.getClob("COM");
 *        short dept = crs.getShort("DEPT");
 *        System.out.println(name + "  " + id + "  " + comment + "  " + dept);
 *    }
 * </PRE>
 * <h4>2.1 Retrieving <code>RowSetMetaData</code></h4>
 * An application can get information about the columns in a <code>CachedRowSet</code>
 * object by calling <code>ResultSetMetaData</code> and <code>RowSetMetaData</code>
 * methods on a <code>RowSetMetaData</code> object. The following code fragment,
 * in which <i>crs</i> is a <code>CachedRowSet</code> object, illustrates the process.
 * The first line creates a <code>RowSetMetaData</code> object with information
 * about the columns in <i>crs</i>.  The method <code>getMetaData</code>,
 * inherited from the <code>ResultSet</code> interface, returns a
 * <code>ResultSetMetaData</code> object, which is cast to a
 * <code>RowSetMetaData</code> object before being assigned to the variable
 * <i>rsmd</i>.  The second line finds out how many columns <i>jrs</i> has, and
 * the third line gets the JDBC type of values stored in the second column of
 * <code>jrs</code>.
 * <PRE>
 *     RowSetMetaData rsmd = (RowSetMetaData)crs.getMetaData();
 *     int count = rsmd.getColumnCount();
 *     int type = rsmd.getColumnType(2);
 * </PRE>
 * The <code>RowSetMetaData</code> interface differs from the
 * <code>ResultSetMetaData</code> interface in two ways.
 * <UL>
 *   <LI><i>It includes <code>setter</code> methods:</i> A <code>RowSet</code>
 *   object uses these methods internally when it is populated with data from a
 *   different <code>ResultSet</code> object.
 *
 *   <LI><i>It contains fewer <code>getter</code> methods:</i> Some
 *   <code>ResultSetMetaData</code> methods to not apply to a <code>RowSet</code>
 *   object. For example, methods retrieving whether a column value is writable
 *   or read only do not apply because all of a <code>RowSet</code> object's
 *   columns will be writable or read only, depending on whether the rowset is
 *   updatable or not.
 * </UL>
 * NOTE: In order to return a <code>RowSetMetaData</code> object, implementations must
 * override the <code>getMetaData()</code> method defined in
 * <code>java.sql.ResultSet</code> and return a <code>RowSetMetaData</code> object.
 *
 * <h3>3.0 Updating a <code>CachedRowSet</code> Object</h3>
 * Updating a <code>CachedRowSet</code> object is similar to updating a
 * <code>ResultSet</code> object, but because the rowset is not connected to
 * its data source while it is being updated, it must take an additional step
 * to effect changes in its underlying data source. After calling the method
 * <code>updateRow</code> or <code>insertRow</code>, a
 * <code>CachedRowSet</code>
 * object must also call the method <code>acceptChanges</code> to have updates
 * written to the data source. The following example, in which the cursor is
 * on a row in the <code>CachedRowSet</code> object <i>crs</i>, shows
 * the code required to update two column values in the current row and also
 * update the <code>RowSet</code> object's underlying data source.
 * <PRE>
 *     crs.updateShort(3, 58);
 *     crs.updateInt(4, 150000);
 *     crs.updateRow();
 *     crs.acceptChanges();
 * </PRE>
 * <P>
 * The next example demonstrates moving to the insert row, building a new
 * row on the insert row, inserting it into the rowset, and then calling the
 * method <code>acceptChanges</code> to add the new row to the underlying data
 * source.  Note that as with the getter methods, the  updater methods may take
 * either a column index or a column name to designate the column being acted upon.
 * <PRE>
 *     crs.moveToInsertRow();
 *     crs.updateString("Name", "Shakespeare");
 *     crs.updateInt("ID", 10098347);
 *     crs.updateShort("Age", 58);
 *     crs.updateInt("Sal", 150000);
 *     crs.insertRow();
 *     crs.moveToCurrentRow();
 *     crs.acceptChanges();
 * </PRE>
 * <P>
 * NOTE: Where the <code>insertRow()</code> method inserts the contents of a
 * <code>CachedRowSet</code> object's insert row is implementation-defined.
 * The reference implementation for the <code>CachedRowSet</code> interface
 * inserts a new row immediately following the current row, but it could be
 * implemented to insert new rows in any number of other places.
 * <P>
 * Another thing to note about these examples is how they use the method
 * <code>acceptChanges</code>.  It is this method that propagates changes in
 * a <code>CachedRowSet</code> object back to the underlying data source,
 * calling on the <code>RowSet</code> object's writer internally to write
 * changes to the data source. To do this, the writer has to incur the expense
 * of establishing a connection with that data source. The
 * preceding two code fragments call the method <code>acceptChanges</code>
 * immediately after calling <code>updateRow</code> or <code>insertRow</code>.
 * However, when there are multiple rows being changed, it is more efficient to call
 * <code>acceptChanges</code> after all calls to <code>updateRow</code>
 * and <code>insertRow</code> have been made.  If <code>acceptChanges</code>
 * is called only once, only one connection needs to be established.
 *
 * <h3>4.0 Updating the Underlying Data Source</h3>
 * When the method <code>acceptChanges</code> is executed, the
 * <code>CachedRowSet</code> object's writer, a <code>RowSetWriterImpl</code>
 * object, is called behind the scenes to write the changes made to the
 * rowset to the underlying data source. The writer is implemented to make a
 * connection to the data source and write updates to it.
 * <P>
 * A writer is made available through an implementation of the
 * <code>SyncProvider</code> interface, as discussed in section 1,
 * "Creating a <code>CachedRowSet</code> Object."
 * The default reference implementation provider, <code>RIOptimisticProvider</code>,
 * has its writer implemented to use an optimistic concurrency control
 * mechanism. That is, it maintains no locks in the underlying database while
 * the rowset is disconnected from the database and simply checks to see if there
 * are any conflicts before writing data to the data source.  If there are any
 * conflicts, it does not write anything to the data source.
 * <P>
 * The reader/writer facility
 * provided by the <code>SyncProvider</code> class is pluggable, allowing for the
 * customization of data retrieval and updating. If a different concurrency
 * control mechanism is desired, a different implementation of
 * <code>SyncProvider</code> can be plugged in using the method
 * <code>setSyncProvider</code>.
 * <P>
 * In order to use the optimistic concurrency control routine, the
 * <code>RIOptismisticProvider</code> maintains both its current
 * value and its original value (the value it had immediately preceding the
 * current value). Note that if no changes have been made to the data in a
 * <code>RowSet</code> object, its current values and its original values are the same,
 * both being the values with which the <code>RowSet</code> object was initially
 * populated.  However, once any values in the <code>RowSet</code> object have been
 * changed, the current values and the original values will be different, though at
 * this stage, the original values are still the initial values. With any subsequent
 * changes to data in a <code>RowSet</code> object, its original values and current
 * values will still differ, but its original values will be the values that
 * were previously the current values.
 * <P>
 * Keeping track of original values allows the writer to compare the <code>RowSet</code>
 * object's original value with the value in the database. If the values in
 * the database differ from the <code>RowSet</code> object's original values, which means that
 * the values in the database have been changed, there is a conflict.
 * Whether a writer checks for conflicts, what degree of checking it does, and how
 * it handles conflicts all depend on how it is implemented.
 *
 * <h3>5.0 Registering and Notifying Listeners</h3>
 * Being JavaBeans components, all rowsets participate in the JavaBeans event
 * model, inheriting methods for registering listeners and notifying them of
 * changes from the <code>BaseRowSet</code> class.  A listener for a
 * <code>CachedRowSet</code> object is a component that wants to be notified
 * whenever there is a change in the rowset.  For example, if a
 * <code>CachedRowSet</code> object contains the results of a query and
 * those
 * results are being displayed in, say, a table and a bar graph, the table and
 * bar graph could be registered as listeners with the rowset so that they can
 * update themselves to reflect changes. To become listeners, the table and
 * bar graph classes must implement the <code>RowSetListener</code> interface.
 * Then they can be added to the <Code>CachedRowSet</code> object's list of
 * listeners, as is illustrated in the following lines of code.
 * <PRE>
 *    crs.addRowSetListener(table);
 *    crs.addRowSetListener(barGraph);
 * </PRE>
 * Each <code>CachedRowSet</code> method that moves the cursor or changes
 * data also notifies registered listeners of the changes, so
 * <code>table</code> and <code>barGraph</code> will be notified when there is
 * a change in <code>crs</code>.
 *
 * <h3>6.0 Passing Data to Thin Clients</h3>
 * One of the main reasons to use a <code>CachedRowSet</code> object is to
 * pass data between different components of an application. Because it is
 * serializable, a <code>CachedRowSet</code> object can be used, for example,
 * to send the result of a query executed by an enterprise JavaBeans component
 * running in a server environment over a network to a client running in a
 * web browser.
 * <P>
 * While a <code>CachedRowSet</code> object is disconnected, it can be much
 * leaner than a <code>ResultSet</code> object with the same data.
 * As a result, it can be especially suitable for sending data to a thin client
 * such as a PDA, where it would be inappropriate to use a JDBC driver
 * due to resource limitations or security considerations.
 * Thus, a <code>CachedRowSet</code> object provides a means to "get rows in"
 * without the need to implement the full JDBC API.
 *
 * <h3>7.0 Scrolling and Updating</h3>
 * A second major use for <code>CachedRowSet</code> objects is to provide
 * scrolling and updating for <code>ResultSet</code> objects that
 * do not provide these capabilities themselves.  In other words, a
 * <code>CachedRowSet</code> object can be used to augment the
 * capabilities of a JDBC technology-enabled driver (hereafter called a
 * "JDBC driver") when the DBMS does not provide full support for scrolling and
 * updating. To achieve the effect of making a non-scrollble and read-only
 * <code>ResultSet</code> object scrollable and updatable, a programmer
 * simply needs to create a <code>CachedRowSet</code> object populated
 * with that <code>ResultSet</code> object's data.  This is demonstrated
 * in the following code fragment, where <code>stmt</code> is a
 * <code>Statement</code> object.
 * <PRE>
 *    ResultSet rs = stmt.executeQuery("SELECT * FROM EMPLOYEES");
 *    CachedRowSetImpl crs = new CachedRowSetImpl();
 *    crs.populate(rs);
 * </PRE>
 * <P>
 * The object <code>crs</code> now contains the data from the table
 * <code>EMPLOYEES</code>, just as the object <code>rs</code> does.
 * The difference is that the cursor for <code>crs</code> can be moved
 * forward, backward, or to a particular row even if the cursor for
 * <code>rs</code> can move only forward.  In addition, <code>crs</code> is
 * updatable even if <code>rs</code> is not because by default, a
 * <code>CachedRowSet</code> object is both scrollable and updatable.
 * <P>
 * In summary, a <code>CachedRowSet</code> object can be thought of as simply
 * a disconnected set of rows that are being cached outside of a data source.
 * Being thin and serializable, it can easily be sent across a wire,
 * and it is well suited to sending data to a thin client. However, a
 * <code>CachedRowSet</code> object does have a limitation: It is limited in
 * size by the amount of data it can store in memory at one time.
 *
 * <h3>8.0 Getting Universal Data Access</h3>
 * Another advantage of the <code>CachedRowSet</code> class is that it makes it
 * possible to retrieve and store data from sources other than a relational
 * database. The reader for a rowset can be implemented to read and populate
 * its rowset with data from any tabular data source, including a spreadsheet
 * or flat file.
 * Because both a <code>CachedRowSet</code> object and its metadata can be
 * created from scratch, a component that acts as a factory for rowsets
 * can use this capability to create a rowset containing data from
 * non-SQL data sources. Nevertheless, it is expected that most of the time,
 * <code>CachedRowSet</code> objects will contain data that was fetched
 * from an SQL database using the JDBC API.
 *
 * <h3>9.0 Setting Properties</h3>
 * All rowsets maintain a set of properties, which will usually be set using
 * a tool.  The number and kinds of properties a rowset has will vary,
 * depending on what the rowset does and how it gets its data.  For example,
 * rowsets that get their data from a <code>ResultSet</code> object need to
 * set the properties that are required for making a database connection.
 * If a rowset uses the <code>DriverManager</code> facility to make a
 * connection, it needs to set a property for the JDBC URL that identifies
 * the appropriate driver, and it needs to set the properties that give the
 * user name and password.
 * If, on the other hand, the rowset uses a <code>DataSource</code> object
 * to make the connection, which is the preferred method, it does not need to
 * set the property for the JDBC URL.  Instead, it needs to set
 * properties for the logical name of the data source, for the user name,
 * and for the password.
 * <P>
 * NOTE:  In order to use a <code>DataSource</code> object for making a
 * connection, the <code>DataSource</code> object must have been registered
 * with a naming service that uses the Java Naming and Directory
 * Interface&trade; (JNDI) API.  This registration
 * is usually done by a person acting in the capacity of a system
 * administrator.
 * <P>
 * In order to be able to populate itself with data from a database, a rowset
 * needs to set a command property.  This property is a query that is a
 * <code>PreparedStatement</code> object, which allows the query to have
 * parameter placeholders that are set at run time, as opposed to design time.
 * To set these placeholder parameters with values, a rowset provides
 * setter methods for setting values of each data type,
 * similar to the setter methods provided by the <code>PreparedStatement</code>
 * interface.
 * <P>
 * The following code fragment illustrates how the <code>CachedRowSet</code>
 * object <code>crs</code> might have its command property set.  Note that if a
 * tool is used to set properties, this is the code that the tool would use.
 * <PRE>{@code
 *    crs.setCommand("SELECT FIRST_NAME, LAST_NAME, ADDRESS FROM CUSTOMERS " +
 *                   "WHERE CREDIT_LIMIT > ? AND REGION = ?");
 * } </PRE>
 * <P>
 * The values that will be used to set the command's placeholder parameters are
 * contained in the <code>RowSet</code> object's <code>params</code> field, which is a
 * <code>Vector</code> object.
 * The <code>CachedRowSet</code> class provides a set of setter
 * methods for setting the elements in its <code>params</code> field.  The
 * following code fragment demonstrates setting the two parameters in the
 * query from the previous example.
 * <PRE>
 *    crs.setInt(1, 5000);
 *    crs.setString(2, "West");
 * </PRE>
 * <P>
 * The <code>params</code> field now contains two elements, each of which is
 * an array two elements long.  The first element is the parameter number;
 * the second is the value to be set.
 * In this case, the first element of <code>params</code> is
 * <code>1</code>, <code>5000</code>, and the second element is <code>2</code>,
 * <code>"West"</code>.  When an application calls the method
 * <code>execute</code>, it will in turn call on this <code>RowSet</code> object's reader,
 * which will in turn invoke its <code>readData</code> method. As part of
 * its implementation, <code>readData</code> will get the values in
 * <code>params</code> and use them to set the command's placeholder
 * parameters.
 * The following code fragment gives an idea of how the reader
 * does this, after obtaining the <code>Connection</code> object
 * <code>con</code>.
 * <PRE>{@code
 *    PreparedStatement pstmt = con.prepareStatement(crs.getCommand());
 *    reader.decodeParams();
 *    // decodeParams figures out which setter methods to use and does something
 *    // like the following:
 *    //    for (i = 0; i < params.length; i++) {
 *    //        pstmt.setObject(i + 1, params[i]);
 *    //    }
 * }</PRE>
 * <P>
 * At this point, the command for <code>crs</code> is the query {@code "SELECT
 * FIRST_NAME, LAST_NAME, ADDRESS FROM CUSTOMERS WHERE CREDIT_LIMIT > 5000
 * AND REGION = "West"}.  After the <code>readData</code> method executes
 * this command with the following line of code, it will have the data from
 * <code>rs</code> with which to populate <code>crs</code>.
 * <PRE>{@code
 *     ResultSet rs = pstmt.executeQuery();
 * }</PRE>
 * <P>
 * The preceding code fragments give an idea of what goes on behind the
 * scenes; they would not appear in an application, which would not invoke
 * methods like <code>readData</code> and <code>decodeParams</code>.
 * In contrast, the following code fragment shows what an application might do.
 * It sets the rowset's command, sets the command's parameters, and executes
 * the command. Simply by calling the <code>execute</code> method,
 * <code>crs</code> populates itself with the requested data from the
 * table <code>CUSTOMERS</code>.
 * <PRE>{@code
 *    crs.setCommand("SELECT FIRST_NAME, LAST_NAME, ADDRESS FROM CUSTOMERS" +
 *                   "WHERE CREDIT_LIMIT > ? AND REGION = ?");
 *    crs.setInt(1, 5000);
 *    crs.setString(2, "West");
 *    crs.execute();
 * }</PRE>
 *
 * <h3>10.0 Paging Data</h3>
 * Because a <code>CachedRowSet</code> object stores data in memory,
 * the amount of data that it can contain at any one
 * time is determined by the amount of memory available. To get around this limitation,
 * a <code>CachedRowSet</code> object can retrieve data from a <code>ResultSet</code>
 * object in chunks of data, called <i>pages</i>. To take advantage of this mechanism,
 * an application sets the number of rows to be included in a page using the method
 * <code>setPageSize</code>. In other words, if the page size is set to five, a chunk
 * of five rows of
 * data will be fetched from the data source at one time. An application can also
 * optionally set the maximum number of rows that may be fetched at one time.  If the
 * maximum number of rows is set to zero, or no maximum number of rows is set, there is
 * no limit to the number of rows that may be fetched at a time.
 * <P>
 * After properties have been set,
 * the <code>CachedRowSet</code> object must be populated with data
 * using either the method <code>populate</code> or the method <code>execute</code>.
 * The following lines of code demonstrate using the method <code>populate</code>.
 * Note that this version of the method takes two parameters, a <code>ResultSet</code>
 * handle and the row in the <code>ResultSet</code> object from which to start
 * retrieving rows.
 * <PRE>
 *     CachedRowSet crs = new CachedRowSetImpl();
 *     crs.setMaxRows(20);
 *     crs.setPageSize(4);
 *     crs.populate(rsHandle, 10);
 * </PRE>
 * When this code runs, <i>crs</i> will be populated with four rows from
 * <i>rsHandle</i> starting with the tenth row.
 * <P>
 * The next code fragment shows populating a <code>CachedRowSet</code> object using the
 * method <code>execute</code>, which may or may not take a <code>Connection</code>
 * object as a parameter.  This code passes <code>execute</code> the <code>Connection</code>
 * object <i>conHandle</i>.
 * <P>
 * Note that there are two differences between the following code
 * fragment and the previous one. First, the method <code>setMaxRows</code> is not
 * called, so there is no limit set for the number of rows that <i>crs</i> may contain.
 * (Remember that <i>crs</i> always has the overriding limit of how much data it can
 * store in memory.) The second difference is that the you cannot pass the method
 * <code>execute</code> the number of the row in the <code>ResultSet</code> object
 * from which to start retrieving rows. This method always starts with the first row.
 * <PRE>
 *     CachedRowSet crs = new CachedRowSetImpl();
 *     crs.setPageSize(5);
 *     crs.execute(conHandle);
 * </PRE>
 * After this code has run, <i>crs</i> will contain five rows of data from the
 * <code>ResultSet</code> object produced by the command for <i>crs</i>. The writer
 * for <i>crs</i> will use <i>conHandle</i> to connect to the data source and
 * execute the command for <i>crs</i>. An application is then able to operate on the
 * data in <i>crs</i> in the same way that it would operate on data in any other
 * <code>CachedRowSet</code> object.
 * <P>
 * To access the next page (chunk of data), an application calls the method
 * <code>nextPage</code>.  This method creates a new <code>CachedRowSet</code> object
 * and fills it with the next page of data.  For example, assume that the
 * <code>CachedRowSet</code> object's command returns a <code>ResultSet</code> object
 * <i>rs</i> with 1000 rows of data.  If the page size has been set to 100, the first
 *  call to the method <code>nextPage</code> will create a <code>CachedRowSet</code> object
 * containing the first 100 rows of <i>rs</i>. After doing what it needs to do with the
 * data in these first 100 rows, the application can again call the method
 * <code>nextPage</code> to create another <code>CachedRowSet</code> object
 * with the second 100 rows from <i>rs</i>. The data from the first <code>CachedRowSet</code>
 * object will no longer be in memory because it is replaced with the data from the
 * second <code>CachedRowSet</code> object. After the tenth call to the method <code>nextPage</code>,
 * the tenth <code>CachedRowSet</code> object will contain the last 100 rows of data from
 * <i>rs</i>, which are stored in memory. At any given time, the data from only one
 * <code>CachedRowSet</code> object is stored in memory.
 * <P>
 * The method <code>nextPage</code> returns <code>true</code> as long as the current
 * page is not the last page of rows and <code>false</code> when there are no more pages.
 * It can therefore be used in a <code>while</code> loop to retrieve all of the pages,
 * as is demonstrated in the following lines of code.
 * <PRE>
 *     CachedRowSet crs = CachedRowSetImpl();
 *     crs.setPageSize(100);
 *     crs.execute(conHandle);
 *
 *     while(crs.nextPage()) {
 *         while(crs.next()) {
 *             . . . // operate on chunks (of 100 rows each) in crs,
 *                   // row by row
 *         }
 *     }
 * </PRE>
 * After this code fragment has been run, the application will have traversed all
 * 1000 rows, but it will have had no more than 100 rows in memory at a time.
 * <P>
 * The <code>CachedRowSet</code> interface also defines the method <code>previousPage</code>.
 * Just as the method <code>nextPage</code> is analogous to the <code>ResultSet</code>
 * method <code>next</code>, the method <code>previousPage</code> is analogous to
 * the <code>ResultSet</code> method <code>previous</code>.  Similar to the method
 * <code>nextPage</code>, <code>previousPage</code> creates a <code>CachedRowSet</code>
 * object containing the number of rows set as the page size.  So, for instance, the
 * method <code>previousPage</code> could be used in a <code>while</code> loop at
 * the end of the preceding code fragment to navigate back through the pages from the last
 * page to the first page.
 * The method <code>previousPage</code> is also similar to <code>nextPage</code>
 * in that it can be used in a <code>while</code>
 * loop, except that it returns <code>true</code> as long as there is another page
 * preceding it and <code>false</code> when there are no more pages ahead of it.
 * <P>
 * By positioning the cursor after the last row for each page,
 * as is done in the following code fragment, the method <code>previous</code>
 * navigates from the last row to the first row in each page.
 * The code could also have left the cursor before the first row on each page and then
 * used the method <code>next</code> in a <code>while</code> loop to navigate each page
 * from the first row to the last row.
 * <P>
 * The following code fragment assumes a continuation from the previous code fragment,
 * meaning that the cursor for the tenth <code>CachedRowSet</code> object is on the
 * last row.  The code moves the cursor to after the last row so that the first
 * call to the method <code>previous</code> will put the cursor back on the last row.
 * After going through all of the rows in the last page (the <code>CachedRowSet</code>
 * object <i>crs</i>), the code then enters
 * the <code>while</code> loop to get to the ninth page, go through the rows backwards,
 * go to the eighth page, go through the rows backwards, and so on to the first row
 * of the first page.
 *
 * <PRE>
 *     crs.afterLast();
 *     while(crs.previous())  {
 *         . . . // navigate through the rows, last to first
 *     {
 *     while(crs.previousPage())  {
 *         crs.afterLast();
 *         while(crs.previous())  {
 *             . . . // go from the last row to the first row of each page
 *         }
 *     }
 * </PRE>
 *
 * <p>
 *  <code> CachedRowSet </code>的所有标准实现必须实现的接口。
 * <P>
 *  由Oracle公司提供的<code> CachedRowSet </code>接口的参考实现是标准实现。开发人员可以使用这个实现,它可以扩展它,或者他们可以选择编写自己的这个接口的实现。
 * <P>
 *  <code> CachedRowSet </code>对象是用于在内存中缓存其行的数据行的容器,这使得可以在不总是连接到其数据源的情况下进行操作。
 * 此外,它是一个JavaBeans&trade;组件,可滚动,可更新和可序列化。
 *  <code> CachedRowSet </code>对象通常包含来自结果集的行,但它也可以包含来自具有表格格式的任何文件的行,例如电子表格。
 * 参考实现支持仅从<code> ResultSet </code>对象获取数据,但开发人员可以扩展<code> SyncProvider </code>实现以提供对其他表格数据源的访问。
 * <P>
 *  应用程序可以修改<code> CachedRowSet </code>对象中的数据,然后可以将这些修改传播回数据源。
 * <P>
 * <code> CachedRowSet </code>对象是一个<i>断开的</i>行集,这意味着它仅使用与其数据源的连接。
 * 它连接到它的数据源,而它正在读取数据,以便在将更改传播回其底层数据源时再次用行填充。在其余时间,一个<code> CachedRowSet </code>对象被断开,包括在它的数据被修改时。
 * 断开连接使得一个<code> RowSet </code>对象更精简,因此更容易传递给另一个组件。
 * 例如,断开的<code> RowSet </code>对象可以被串行化并通过线路传递到诸如个人数字助理(PDA)的瘦客户端。
 * 
 *  <h3> 1.0创建<code> CachedRowSet </code>对象</h3>下面的代码行使用参考实现(RI)中提供的<code> CachedRowSet </code>的默认构造函数创建
 * 一个默认<代码> CachedRowSet </code>对象。
 * <PRE>
 *  CachedRowSetImpl crs = new CachedRowSetImpl();
 * </PRE>
 *  这个新的<code> CachedRowSet </code>对象将其属性设置为<code> BaseRowSet </code>对象的默认属性,此外,它将有一个<code> RIOptimistic
 * Provider </code>对象作为其同步提供者。
 *  <code> RIOptimisticProvider </code>是包含在RI中的两个<code> SyncProvider </code>实现之一,是未指定同步提供程序时,<code> Sync
 * Factory </code>单例提供的默认提供程序。
 * <P>
 * 一个<code> SyncProvider </code>对象提供了一个读取器(一个<code> RowSetReader </code>对象)用于从数据源读取数据来填充数据的<code> Cached
 * RowSet </code>对象。
 * 读取器可以实现为从<code> ResultSet </code>对象或从具有表格格式的文件读取数据。
 * 一个<code> SyncProvider </code>对象还提供了一个写入器(一个<code> RowSetWriter </code>对象),用于同步任何修改到<code> CachedRowSe
 * t </code>在底层数据源。
 * 读取器可以实现为从<code> ResultSet </code>对象或从具有表格格式的文件读取数据。
 * <P>
 *  作者可以被实施以在检查冲突和避免冲突时采取各种程度的谨慎。 (当行集用该值填充数据源后,如果数据源中的值已更改,则会发生冲突。
 * )<code> RIOptimisticProvider </code>实现假设存在很少或没有冲突,因此不设置锁定。
 * 只有在没有冲突的情况下,它才使用来自<code> CachedRowSet </code>对象的值更新数据源。
 * 可以实现其他写入器,使得它们总是将修改的数据写入数据源,这可以通过不检查冲突来完成,或者在频谱的另一端,通过设置足以防止数据源中的数据改变。其他写入器实现可以在其间的某处。
 * <P>
 * <code> CachedRowSet </code>对象可以使用已经向<code> SyncFactory </code>单例注册的任何<code> SyncProvider </code>实现。
 * 应用程序可以通过调用以下代码行来找出已注册的<code> SyncProvider </code>实现。
 * <PRE>
 *  java.util.Enumeration providers = SyncFactory.getRegisteredProviders();
 * </PRE>
 * <P>
 *  对于<code> CachedRowSet </code>对象,有两种方法来指定它将使用的<code> SyncProvider </code>对象。
 * <UL>
 *  <LI>向构造函数提供实现的名称<BR>下面的代码行创建了使用默认值初始化的<code> CachedRowSet </code>对象<i> crs2 </i> > SyncProvider </code>
 * 对象是指定的对象。
 * <PRE>
 *  CachedRowSetImpl crs2 = new CachedRowSetImpl("com.fred.providers.HighAvailabilityProvider");
 * </PRE>
 *  <LI>使用<code> CachedRowSet </code>方法设置<code> SyncProvider </code> <code> setSyncProvider </code> <BR>
 * 下面的代码行重置了<code> SyncProvider </code>对象</i>,使用默认构造函数创建的<code> CachedRowSet </code>对象。
 * <PRE>
 *  crs.setSyncProvider("com.fred.providers.HighAvailabilityProvider");
 * </PRE>
 * </UL>
 *  有关更多详细信息,请参阅<code> SyncFactory </code>和<code> SyncProvider </code>的注释。
 * 
 * <h3> 2.0从<code> CachedRowSet </code>对象检索数据</h3>通过使用继承自<code> ResultSet </code>的getter方法从<code> Cached
 * RowSet </code>对象检索数据>接口。
 * 以下示例中<code> crs </code>是<code> CachedRowSet </code>对象,演示了如何遍历行,检索每行中的列值。
 * 第一个示例使用获取列方法的getter方法的版本;第二个示例使用采用列名的版本。
 * 当<code> RowSet </code>对象的命令的格式为<code> SELECT * FROM TABLENAME </code>时,通常使用列号;当命令按名称指定列时,最常使用列名称。
 * <PRE>
 *  while(crs.next()){String name = crs.getString(1); int id = crs.getInt(2); Clob comment = crs.getClob(3); short dept = crs.getShort(4); System.out.println(name +""+ id +""+ comment +""+ dept); }
 * }。
 * </PRE>
 * 
 * <PRE>
 *  while(crs.next()){String name = crs.getString("NAME"); int id = crs.getInt("ID"); Clob comment = crs.getClob("COM"); short dept = crs.getShort("DEPT"); System.out.println(name +""+ id +""+ comment +""+ dept); }
 * }。
 * </PRE>
 * <h4> 2.1检索<code> RowSetMetaData </code> </h4>应用程序可以通过调用<code> ResultSetMetaData </code>和<code>获得<code>
 *  CachedRowSet </code> RowSetMetaData </code>对象上的RowSetMetaData </code>方法。
 * 以下代码片段(其中<i> crs </i>是<code> CachedRowSet </code>对象)说明了该过程。
 * 第一行创建一个<code> RowSetMetaData </code>对象,其中包含有关<i> crs </i>中列的信息。
 * 继承自<code> ResultSet </code>接口的方法<code> getMetaData </code>返回一个<code> ResultSetMetaData </code>对象,该对象被
 * 转换为一个<code> RowSetMetaData </code>对象在被分配给变量<i> rsmd </i>之前。
 * 第一行创建一个<code> RowSetMetaData </code>对象,其中包含有关<i> crs </i>中列的信息。
 * 第二行发现有多少列<i> jrs </i>,第三行获得存储在<code> jrs </code>的第二列中的JDBC类型的值。
 * <PRE>
 *  RowSetMetaData rsmd =(RowSetMetaData)crs.getMetaData(); int count = rsmd.getColumnCount(); int type 
 * = rsmd.getColumnType(2);。
 * </PRE>
 *  <code> RowSetMetaData </code>接口有两种方式不同于<code> ResultSetMetaData </code>接口。
 * <UL>
 *  <li> <i> </code>方法</i>：<code> RowSet </code>对象在使用不同<code> ResultSet </code> / code>对象。
 * 
 * <LI> <i>它包含较少的<code> getter </code>方法：</i>一些<code> ResultSetMetaData </code>方法不适用于<code> RowSet </code>
 * 例如,检索列值是可写还是只读的方法不适用,因为所有<code> RowSet </code>对象的列将是可写的或只读的,这取决于行集是否是可更新的。
 * </UL>
 *  注意：为了返回<code> RowSetMetaData </code>对象,实现必须重写<code> java.sql.ResultSet </code>中定义的<code> getMetaData
 * ()</code>方法,代码> RowSetMetaData </code>对象。
 * 
 *  <h3> 3.0更新<code> CachedRowSet </code>对象</h3>更新<code> CachedRowSet </code>对象与更新<code> ResultSet </code>
 * 对象类似,在更新时未连接到其数据源,则必须采取额外的步骤来更改其基础数据源。
 * 调用<code> updateRow </code>或<code> insertRow </code>方法后,<code> CachedRowSet </code>对象还必须调用<code> accep
 * tChanges </code>数据源。
 * 下面的示例(其中游标位于<code> CachedRowSet </code>对象<i> crs </i>中的一行)显示了更新当前行中的两个列值所需的代码,代码> RowSet </code>对象的基础
 * 数据源。
 * <PRE>
 *  crs.updateShort(3,58); crs.updateInt(4,150000); crs.updateRow(); crs.acceptChanges();
 * </PRE>
 * <P>
 * 下一个示例演示移动到插入行,在插入行上构建新行,将其插入行集,然后调用<code> acceptChanges </code>方法将新行添加到底层数据源。
 * 注意,与getter方法一样,updater方法可以采用列索引或列名来指定要执行的列。
 * <PRE>
 *  crs.moveToInsertRow(); crs.updateString("Name","Shakespeare"); crs.updateInt("ID",10098347); crs.upd
 * ateShort("Age",58); crs.updateInt("Sal",150000); crs.insertRow(); crs.moveToCurrentRow(); crs.acceptC
 * hanges();。
 * </PRE>
 * <P>
 *  注意：<code> insertRow()</code>方法插入<code> CachedRowSet </code>对象的插入行的内容是实现定义的。
 * 对于<code> CachedRowSet </code>接口的引用实现插入紧接当前行之后的新行,但是可以实现为在任何数量的其他位置插入新行。
 * <P>
 * 注意这些例子的另一件事是他们如何使用方法<code> acceptChanges </code>。
 * 正是这种方法将<code> CachedRowSet </code>对象中的更改传播回底层数据源,在内部调用<code> RowSet </code>对象的写入器来写入对数据源的更改。
 * 为此,作者必须承担与该数据源建立连接的费用。
 * 前面的两个代码片段在调用<code> updateRow </code>或<code> insertRow </code>之后立即调用<code> acceptChanges </code>方法。
 * 然而,当有多行被改变时,在对<code> updateRow </code>和<code> insertRow </code>的所有调用之后调用<code> acceptChanges </code>如
 * 果<code> acceptChanges </code>只被调用一次,则只需要建立一个连接。
 * 前面的两个代码片段在调用<code> updateRow </code>或<code> insertRow </code>之后立即调用<code> acceptChanges </code>方法。
 * 
 *  <h3> 4.0更新底层数据源</h3>当方法<code> acceptChanges </code>被执行时,<code> CachedRowSet </code>对象的作者,一个<code> Ro
 * wSetWriterImpl </code>在幕后被调用以将对行集的更改写入基础数据源。
 * 编写器实现为连接到数据源并向其写入更新。
 * <P>
 * 通过实现<code> SyncProvider </code>接口,可以使用写入器,如第1节"创建<code> CachedRowSet </code>对象"中所述。
 * 默认引用实现提供程序<code> RIOptimisticProvider </code>使其编写器实现为使用乐观并发控制机制。
 * 也就是说,当行集与数据库断开连接时,它在底层数据库中不维护锁,并且在将数据写入数据源之前简单地检查是否存在任何冲突。如果存在任何冲突,它不会向数据源写入任何内容。
 * <P>
 *  由<code> SyncProvider </code>类提供的读取器/写入器设施是可插入的,允许定制数据检索和更新。
 * 如果需要不同的并发控制机制,可以使用方法<code> setSyncProvider </code>插入<code> SyncProvider </code>的不同实现。
 * <P>
 * 为了使用乐观并发控制例程,<code> RIOptismisticProvider </code>维护它的当前值和它的原始值(它在当前值之前的值)。
 * 请注意,如果没有对<code> RowSet </code>对象中的数据进行任何更改,则其当前值及其原始值是相同的,这两个值都是<code> RowSet </code>对象最初是填充。
 * 然而,一旦<code> RowSet </code>对象中的任何值被改变,当前值和原始值将是不同的,虽然在这个阶段,原始值仍然是初始值。
 * 随着对<code> RowSet </code>对象中的数据的任何后续更改,其原始值和当前值仍将不同,但其原始值将是之前为当前值的值。
 * <P>
 *  保持原始值的跟踪允许作者将<code> RowSet </code>对象的原始值与数据库中的值进行比较。
 * 如果数据库中的值与<code> RowSet </code>对象的原始值不同,这意味着数据库中的值已更改,则存在冲突。作者是否检查冲突,检查它的程度以及如何处理冲突都取决于它是如何实现的。
 * 
 * <h3> 5.0注册和通知侦听器</h3>作为JavaBeans组件,所有行集都参与JavaBeans事件模型,继承用于注册侦听器并通知来自<code> BaseRowSet </code>类的更改的方
 * 法。
 * 对于<code> CachedRowSet </code>对象的监听器是一个组件,当行集中有更改时,它会被通知。
 * 例如,如果<code> CachedRowSet </code>对象包含查询的结果,并且这些结果显示在例如表和条形图中,则表和条形图可以被注册为具有行集的监听器以便他们可以更新自己以反映更改。
 * 要成为侦听器,表和条形图类必须实现<code> RowSetListener </code>接口。
 * 然后可以将它们添加到<Code> CachedRowSet </code>对象的侦听器列表中,如下面的代码行所示。
 * <PRE>
 *  crs.addRowSetListener(table); crs.addRowSetListener(barGraph);
 * </PRE>
 *  每个移动光标或改变数据的<code> CachedRowSet </code>方法都会通知注册的监听器这些更改,因此,当存在<code> table </code>和<code> barGraph </code>
 * 更改<code> crs </code>。
 * 
 * <h3> 6.0将数据传递到瘦客户端</h3>使用<code> CachedRowSet </code>对象的主要原因之一是在应用程序的不同组件之间传递数据。
 * 因为它是可序列化的,所以可以使用<code> CachedRowSet </code>对象,例如,将通过网络在服务器环境中运行的企业JavaBeans组件执行的查询的结果发送到在web中运行的客户端浏览
 * 器。
 * <h3> 6.0将数据传递到瘦客户端</h3>使用<code> CachedRowSet </code>对象的主要原因之一是在应用程序的不同组件之间传递数据。
 * <P>
 *  当一个<code> CachedRowSet </code>对象断开连接时,它可能比具有相同数据的<code> ResultSet </code>对象更精简。
 * 因此,它可以特别适合于向诸如PDA的瘦客户端发送数据,其中由于资源限制或安全考虑,使用JDBC驱动器是不合适的。
 * 因此,<code> CachedRowSet </code>对象提供了一种"获取行"的方法,而不需要实现完整的JDBC API。
 * 
 * <h3> 7.0滚动和更新</h3> <code> CachedRowSet </code>对象的第二个主要用途是提供不提供这些功能的<code> ResultSet </code>对象的滚动和更新。
 * 换句话说,当DBMS不提供对滚动和更新的完全支持时,可以使用<code> CachedRowSet </code>对象来增强启用JDBC技术的驱动程序(以下称为"JDBC驱动程序")的功能。
 * 为了实现使非滚动和只读<code> ResultSet </code>对象可滚动和可更新的效果,程序员只需创建一个<code> CachedRowSet </code>对象,该对象填充有<code> R
 * esultSet </code>对象的数据。
 * 换句话说,当DBMS不提供对滚动和更新的完全支持时,可以使用<code> CachedRowSet </code>对象来增强启用JDBC技术的驱动程序(以下称为"JDBC驱动程序")的功能。
 * 这在下面的代码片段中演示,其中<code> stmt </code>是一个<code> Statement </code>对象。
 * <PRE>
 *  ResultSet rs = stmt.executeQuery("SELECT * FROM EMPLOYEES"); CachedRowSetImpl crs = new CachedRowSet
 * Impl(); (rs);。
 * </PRE>
 * <P>
 *  对象<code> crs </code>现在包含来自表<code> EMPLOYEES </code>的数据,就像对象<code> rs </code>那样。
 * 区别在于<code> crs </code>的游标可以向前,向后移动或移动到特定的行,即使<code> rs </code>的游标只能向前移动。
 * 此外,<code> crs </code>是可更新的,即使<code> rs </code>不是因为默认情况下,一个<code> CachedRowSet </code>对象是可滚动和可更新的。
 * <P>
 * 总之,一个<code> CachedRowSet </code>对象可以被认为是一个断开的行,它被缓存在数据源之外。它很薄并且可串行化,可以很容易地通过电线发送,非常适合发送数据到瘦客户端。
 * 然而,一个<code> CachedRowSet </code>对象有一个限制：它的大小限制了它可以一次存储在内存中的数据量。
 * 
 *  <h3> 8.0获取通用数据访问</h3> <code> CachedRowSet </code>类的另一个优点是可以从关系数据库之外的源检索和存储数据。
 * 可以实现行集的读取器以用来自任何表格数据源的数据(包括电子表格或平面文件)读取和填充其行集。
 * 因为可以从头创建<code> CachedRowSet </code>对象及其元数据,所以用作行集的工厂的组件可以使用此功能创建包含来自非SQL数据源的数据的行集。
 * 然而,大多数时候,<code> CachedRowSet </code>对象将包含使用JDBC API从SQL数据库获取的数据。
 * 
 * <h3> 9.0设置属性</h3>所有行集都保留一组属性,这些属性通常使用工具设置。行集的属性的数量和种类将有所不同,具体取决于行集的行为以及如何获取其数据。
 * 例如,从<code> ResultSet </code>对象获取数据的行集需要设置进行数据库连接所需的属性。
 * 如果行集使用<code> DriverManager </code>工具建立连接,它需要为标识相应驱动程序的JDBC URL设置属性,并且需要设置提供用户名和密码的属性。
 * 另一方面,如果行集使用<code> DataSource </code>对象来建立连接(这是首选方法),则不需要为JDBC URL设置属性。相反,它需要为数据源的逻辑名,用户名和密码设置属性。
 * <P>
 *  注意：为了使用<code> DataSource </code>对象进行连接,<code> DataSource </code>对象必须已使用命名服务注册,该服务使用Java命名和目录接口& (JND
 * I)API。
 * 此注册通常由以系统管理员身份行事的人员进行。
 * <P>
 * 为了能够使用数据库中的数据填充自身,行集需要设置命令属性。
 * 此属性是一个查询,它是一个<code> PreparedStatement </code>对象,它允许查询具有在运行时设置的参数占位符,而不是设计时间。
 * 要使用值设置这些占位符参数,行集提供了设置每个数据类型的值的设置方法,类似于由<code> PreparedStatement </code>接口提供的setter方法。
 * <P>
 *  以下代码片段说明了<code> CachedRowSet </code>对象<code> crs </code>如何设置其命令属性。请注意,如果使用工具设置属性,则这是工具将使用的代码。
 *  <PRE> {@ code crs.setCommand("SELECT FIRST_NAME,LAST_NAME,ADDRESS FROM CUSTOMERS"+"WHERE CREDIT_LIMIT>?AND REGION =?"); }
 *  </PRE>。
 *  以下代码片段说明了<code> CachedRowSet </code>对象<code> crs </code>如何设置其命令属性。请注意,如果使用工具设置属性,则这是工具将使用的代码。
 * <P>
 *  用于设置命令占位符参数的值包含在<code> RowSet </code>对象的<code> params </code>字段中,它是一个<code> Vector </code>对象。
 *  <code> CachedRowSet </code>类提供了一组setter方法,用于在其<code> params </code>字段中设置元素。
 * 以下代码片段演示了设置上一个示例的查询中的两个参数。
 * <PRE>
 *  crs.setInt(1,5000); crs.setString(2,"West");
 * </PRE>
 * <P>
 * <code> params </code>字段现在包含两个元素,每个元素是一个数组两个元素long。第一个元素是参数号;第二个是要设置的值。
 * 在这种情况下,<code> params </code>的第一个元素是<code> 1 </code>,<code> 5000 </code>,第二个元素是<code> 2 </code>代码>"西"</code>
 * 。
 * <code> params </code>字段现在包含两个元素,每个元素是一个数组两个元素long。第一个元素是参数号;第二个是要设置的值。
 * 当应用程序调用<code> execute </code>方法时,它将依次调用<code> RowSet </code>对象的读取器,这将调用其<code> readData </code>方法。
 * 作为其实现的一部分,<code> readData </code>将获得<code> params </code>中的值,并使用它们设置命令的占位符参数。
 * 下面的代码片段给出了读者在获得<code> Connection </code>对象<code> con </code>之后如何做到这一点的想法。
 *  <PRE> {@ code PreparedStatement pstmt = con.prepareStatement(crs.getCommand()); reader.decodeParams(); // decodeParams确定要使用的setter方法,并执行以下操作：// for(i = 0; i <params.length; i ++){// pstmt.setObject(i + 1,params [i] ); //}} </PRE>
 * 。
 * 下面的代码片段给出了读者在获得<code> Connection </code>对象<code> con </code>之后如何做到这一点的想法。
 * <P>
 * 此时,<code> crs </code>的命令是查询{@code"SELECT FIRST_NAME,LAST_NAME,ADDRESS FROM CUSTOMERS WHERE CREDIT_LIMIT> 5000 AND REGION ="West"}
 * 。
 * <code> readData < code>方法用下面的代码行执行这个命令,它将拥有来自<code> rs </code>的数据,用它来填充<code> crs </code>。
 * <PRE> {@ code ResultSet rs = pstmt .executeQuery();} </PRE>。
 * <P>
 *  前面的代码片段给出了在后台进行什么的想法;它们不会出现在应用程序中,不会调用<code> readData </code>和<code> decodeParams </code>等方法。
 * 相反,以下代码片段显示了应用程序可能做什么。它设置行集的命令,设置命令的参数,并执行命令。
 * 只需调用<code> execute </code>方法,<code> crs </code>就会使用<code> CUSTOMERS </code>表中的请求数据填充它自己。
 *  <PRE> {@ code crs.setCommand("SELECT FIRST_NAME,LAST_NAME,ADDRESS FROM CUSTOMERS"+"WHERE CREDIT_LIMIT>?AND REGION =?"); crs.setInt(1,5000); crs.setString(2,"West"); crs.execute(); }
 *  </PRE>。
 * 只需调用<code> execute </code>方法,<code> crs </code>就会使用<code> CUSTOMERS </code>表中的请求数据填充它自己。
 * 
 * <h3> 10.0分页数据</h3>由于<code> CachedRowSet </code>对象将数据存储在内存中,因此可以在任何时间包含的数据量由可用内存量决定。
 * 为了克服这个限制,一个<code> CachedRowSet </code>对象可以从一个叫做<i> pages </i>的数据块中的<code> ResultSet </code>对象检索数据。
 * 为了利用这种机制,应用程序使用方法<code> setPageSize </code>设置要包括在页面中的行数。换句话说,如果页面大小被设置为5,则一次将从数据源获取五行数据的块。
 * 应用程序还可以可选地设置一次可以提取的最大行数。如果最大行数设置为零,或没有设置最大行数,则一次可以提取的行数没有限制。
 * <P>
 *  设置属性后,必须使用方法<code> populate </code>或方法<code> execute </code>,用数据填充<code> CachedRowSet </code>对象。
 * 下面的代码演示使用方法<code> populate </code>。
 * 请注意,此版本的方法有两个参数,一个<code> ResultSet </code>句柄和<code> ResultSet </code>对象中的行,从中开始检索行。
 * <PRE>
 *  CachedRowSet crs = new CachedRowSetImpl(); crs.setMaxRows(20); crs.setPageSize(4); crs.populate(rsHa
 * ndle,10);。
 * </PRE>
 * 当此代码运行时,<i> crs </i>将从第i行开始,从<i> rsHandle </i>中的四行填充。
 * <P>
 *  下一个代码片段示出使用方法<code> execute </code>填充<code> CachedRowSet </code>对象,该方法可以或可以不将<code> Connection </code>
 * 对象作为参数。
 * 此代码通过<code> execute </code> <code> Connection </code>对象<i> conHandle </i>。
 * <P>
 *  请注意,以下代码段与上一个代码段之间有两个差异。首先,不会调用<code> setMaxRows </code>方法,因此<i> crs </i>可能包含的行数没有限制。
 *  (请记住,<i> crs </i>始终具有可以在内存中存储多少数据的上限限制。
 * )第二个区别是,您无法通过<code> execute </code>在<code> ResultSet </code>对象中开始检索行。此方法始终从第一行开始。
 * <PRE>
 *  CachedRowSet crs = new CachedRowSetImpl(); crs.setPageSize(5); crs.execute(conHandle);
 * </PRE>
 *  运行此代码后,<i> crs </i>将包含来自<i> crs </i>命令生成的<code> ResultSet </code>对象的五行数据。
 *  <i> crs </i>的作者将使用<i> conHandle </i>连接到数据源并执行<i> crs </i>的命令。
 * 然后,应用程序能够以对它在任何其他<code> CachedRowSet </code>对象中的数据进行操作的相同方式对<i> crs </i>中的数据进行操作。
 * <P>
 * 为了访问下一页(数据块),应用程序调用<code> nextPage </code>方法。此方法创建一个新的<code> CachedRowSet </code>对象,并用下一页数据填充它。
 * 例如,假设<code> CachedRowSet </code>对象的命令返回一个具有1000行数据的<code> ResultSet </code>对象<i> rs </i>。
 * 如果页面大小已设置为100,则首次调用<code> nextPage </code>方法将创建一个<code> CachedRowSet </code>对象,其中包含<i> rs </i> 。
 * 在完成对前100行中的数据的操作之后,应用程序可以再次调用<code> nextPage </code>方法来创建另一个<code> CachedRowSet </code>对象, i> rs </i>
 * 。
 * 如果页面大小已设置为100,则首次调用<code> nextPage </code>方法将创建一个<code> CachedRowSet </code>对象,其中包含<i> rs </i> 。
 * 来自第一个<code> CachedRowSet </code>对象的数据将不再在内存中,因为它被第二个<code> CachedRowSet </code>对象的数据替换。
 * 在第十次调用方法<code> nextPage </code>之后,第十个<code> CachedRowSet </code>对象将包含来自<i> rs </i>的最后100行数据, 。
 * 在任何给定时间,来自仅一个<code> CachedRowSet </code>对象的数据存储在存储器中。
 * <P>
 * 方法<code> nextPage </code>返回<code> true </code>,只要当前页面不是最后一页的行和<code> false </code>。
 * 因此,可以在<code> while </code>循环中使用以检索所有页面,如以下代码行所示。
 * <PRE>
 *  CachedRowSet crs = CachedRowSetImpl(); crs.setPageSize(100); crs.execute(conHandle);
 * 
 *  while(crs.nextPage()){while(crs.next()){。 。 。 //对crs中的块(每行100行),//逐行操作}}
 * </PRE>
 *  运行此代码片段后,应用程序将遍历所有1000行,但它在内存中一次不超过100行。
 * <P>
 * <code> CachedRowSet </code>接口也定义了方法<code> previousPage </code>。
 * 正如方法<code> nextPage </code>类似于<code> ResultSet </code>方法<code> next </code>,方法<code> previousPage </code>
 *  > ResultSet </code>方法<code>上一个</code>。
 * <code> CachedRowSet </code>接口也定义了方法<code> previousPage </code>。
 * 与方法<code> nextPage </code>类似,<code> previousPage </code>创建一个<code> CachedRowSet </code>对象,其中包含设置为页面大小
 * 的行数。
 * <code> CachedRowSet </code>接口也定义了方法<code> previousPage </code>。
 * 因此,例如,方法<code> previousPage </code>可以用于<code> while </code>循环在前面的代码片段的末尾,以通过页面从最后一页返回到第一页页。
 * 方法<code> previousPage </code>也类似于<code> nextPage </code>,因为它可以在<code> while </code>循环中使用,除了返回<code> t
 * rue <代码>,只要前面有另一个页面,并且当前面没有更多的页面时,还有<code> false </code>。
 * 因此,例如,方法<code> previousPage </code>可以用于<code> while </code>循环在前面的代码片段的末尾,以通过页面从最后一页返回到第一页页。
 * <P>
 *  通过将光标放在每一页的最后一行之后,如下面代码片段中所做的,方法<code> previous </code>从每一页的最后一行导航到第一行。
 * 代码还可以将光标留在每页上的第一行之前,然后在<code>循环中使用<code> next </code>的方法来将每一页从第一行导航到最后一行行。
 * <P>
 * 下面的代码片段假定是从上一个代码片段继承,意味着第十个<code> CachedRowSet </code>对象的游标在最后一行。
 * 代码将光标移动到最后一行之后,以便第一次调用<code> previous </code>方法将光标放回最后一行。
 * 在遍历最后一页中的所有行(<code> CachedRowSet </code> object <i> crs </i>)之后,代码然后进入<code> while </code>第九页,向后浏览行,转
 * 到第八页,向后浏览行,依此类推到第一页的第一行。
 * 代码将光标移动到最后一行之后,以便第一次调用<code> previous </code>方法将光标放回最后一行。
 * 
 * <PRE>
 *  crs.afterLast(); while(crs.previous()){。 。 。
 *  //遍历行,最后到第一行{while(crs.previousPage()){crs.afterLast(); while(crs.previous()){。 。 。
 *  //从最后一行到每页的第一行}}。
 * </PRE>
 * 
 * 
 * @author Jonathan Bruce
 */

public interface CachedRowSet extends RowSet, Joinable {

   /**
    * Populates this <code>CachedRowSet</code> object with data from
    * the given <code>ResultSet</code> object.
    * <P>
    * This method can be used as an alternative to the <code>execute</code> method when an
    * application has a connection to an open <code>ResultSet</code> object.
    * Using the method <code>populate</code> can be more efficient than using
    * the version of the <code>execute</code> method that takes no parameters
    * because it does not open a new connection and re-execute this
    * <code>CachedRowSet</code> object's command. Using the <code>populate</code>
    * method is more a matter of convenience when compared to using the version
    * of <code>execute</code> that takes a <code>ResultSet</code> object.
    *
    * <p>
    *  使用给定的<code> ResultSet </code>对象中的数据填充此<c> cachedRowSet </code>对象。
    * <P>
    * 当应用程序具有与打开的<code> ResultSet </code>对象的连接时,此方法可以用作<code> execute </code>方法的替代方法。
    * 使用方法<code> populate </code>比使用没有参数的<code> execute </code>方法的版本更高效,因为它不打开一个新的连接并重新执行这个<code> CachedRow
    * Set </code>对象的命令。
    * 当应用程序具有与打开的<code> ResultSet </code>对象的连接时,此方法可以用作<code> execute </code>方法的替代方法。
    * 与使用采用<code> ResultSet </code>对象的<code> execute </code>版本相比,使用<code> populate </code>方法更为方便。
    * 
    * 
    * @param data the <code>ResultSet</code> object containing the data
    * to be read into this <code>CachedRowSet</code> object
    * @throws SQLException if a null <code>ResultSet</code> object is supplied
    * or this <code>CachedRowSet</code> object cannot
    * retrieve the associated <code>ResultSetMetaData</code> object
    * @see #execute
    * @see java.sql.ResultSet
    * @see java.sql.ResultSetMetaData
    */
    public void populate(ResultSet data) throws SQLException;

   /**
    * Populates this <code>CachedRowSet</code> object with data, using the
    * given connection to produce the result set from which the data will be read.
    * This method should close any database connections that it creates to
    * ensure that this <code>CachedRowSet</code> object is disconnected except when
    * it is reading data from its data source or writing data to its data source.
    * <P>
    * The reader for this <code>CachedRowSet</code> object
    * will use <i>conn</i> to establish a connection to the data source
    * so that it can execute the rowset's command and read data from the
    * the resulting <code>ResultSet</code> object into this
    * <code>CachedRowSet</code> object. This method also closes <i>conn</i>
    * after it has populated this <code>CachedRowSet</code> object.
    * <P>
    * If this method is called when an implementation has already been
    * populated, the contents and the metadata are (re)set. Also, if this method is
    * called before the method <code>acceptChanges</code> has been called
    * to commit outstanding updates, those updates are lost.
    *
    * <p>
    *  用数据填充这个<code> CachedRowSet </code>对象,使用给定的连接来产生读取数据的结果集。
    * 此方法应关闭其创建的任何数据库连接,以确保此<c> cachedRowSet </code>对象断开连接,除非它从其数据源读取数据或将数据写入其数据源。
    * <P>
    *  这个<code> CachedRowSet </code>对象的读取器将使用<i> conn </i>建立到数据源的连接,以便它可以执行行集的命令并从结果<Result> ResultSet </code>
    * 对象插入到这个<code> CachedRowSet </code>对象中。
    * 此方法也会在填充此<c> cachedRowSet </code>对象后关闭<i> conn </i>。
    * <P>
    * 如果在已经填充实现时调用此方法,则(重新)设置内容和元数据。此外,如果在调用<code> acceptChanges </code>方法提交未完成更新之前调用此方法,那些更新将丢失。
    * 
    * 
    * @param conn a standard JDBC <code>Connection</code> object with valid
    * properties
    * @throws SQLException if an invalid <code>Connection</code> object is supplied
    * or an error occurs in establishing the connection to the
    * data source
    * @see #populate
    * @see java.sql.Connection
    */
    public void execute(Connection conn) throws SQLException;

   /**
    * Propagates row update, insert and delete changes made to this
    * <code>CachedRowSet</code> object to the underlying data source.
    * <P>
    * This method calls on this <code>CachedRowSet</code> object's writer
    * to do the work behind the scenes.
    * Standard <code>CachedRowSet</code> implementations should use the
    * <code>SyncFactory</code> singleton
    * to obtain a <code>SyncProvider</code> instance providing a
    * <code>RowSetWriter</code> object (writer).  The writer will attempt
    * to propagate changes made in this <code>CachedRowSet</code> object
    * back to the data source.
    * <P>
    * When the method <code>acceptChanges</code> executes successfully, in
    * addition to writing changes to the data source, it
    * makes the values in the current row be the values in the original row.
    * <P>
    * Depending on the synchronization level of the <code>SyncProvider</code>
    * implementation being used, the writer will compare the original values
    * with those in the data source to check for conflicts. When there is a conflict,
    * the <code>RIOptimisticProvider</code> implementation, for example, throws a
    * <code>SyncProviderException</code> and does not write anything to the
    * data source.
    * <P>
    * An application may choose to catch the <code>SyncProviderException</code>
    * object and retrieve the <code>SyncResolver</code> object it contains.
    * The <code>SyncResolver</code> object lists the conflicts row by row and
    * sets a lock on the data source to avoid further conflicts while the
    * current conflicts are being resolved.
    * Further, for each conflict, it provides methods for examining the conflict
    * and setting the value that should be persisted in the data source.
    * After all conflicts have been resolved, an application must call the
    * <code>acceptChanges</code> method again to write resolved values to the
    * data source.  If all of the values in the data source are already the
    * values to be persisted, the method <code>acceptChanges</code> does nothing.
    * <P>
    * Some provider implementations may use locks to ensure that there are no
    * conflicts.  In such cases, it is guaranteed that the writer will succeed in
    * writing changes to the data source when the method <code>acceptChanges</code>
    * is called.  This method may be called immediately after the methods
    * <code>updateRow</code>, <code>insertRow</code>, or <code>deleteRow</code>
    * have been called, but it is more efficient to call it only once after
    * all changes have been made so that only one connection needs to be
    * established.
    * <P>
    * Note: The <code>acceptChanges()</code> method will determine if the
    * <code>COMMIT_ON_ACCEPT_CHANGES</code> is set to true or not. If it is set
    * to true, all updates in the synchronization are committed to the data
    * source. Otherwise, the application <b>must</b> explicity call the
    * <code>commit()</code> or <code>rollback()</code> methods as appropriate.
    *
    * <p>
    *  传播行更新,将对此<code> CachedRowSet </code>对象所做的更改插入和删除到底层数据源。
    * <P>
    *  这个方法调用这个<code> CachedRowSet </code>对象的作者做幕后的工作。
    * 标准<code> CachedRowSet </code>实现应该使用<code> SyncFactory </code>单例获取提供<code> RowSetWriter </code>对象(writ
    * er)的<code> SyncProvider </code>实例。
    *  这个方法调用这个<code> CachedRowSet </code>对象的作者做幕后的工作。写入器将尝试将在此<c> cachedRowSet </code>对象中所做的更改传播回数据源。
    * <P>
    *  当方法<code> acceptChanges </code>成功执行时,除了对数据源进行更改之外,还会使当前行中的值为原始行中的值。
    * <P>
    *  根据正在使用的<code> SyncProvider </code>实现的同步级别,写入程序将比较原始值与数据源中的值,以检查冲突。
    * 当存在冲突时,<code> RIOptimisticProvider </code>实现会抛出一个<code> SyncProviderException </code>,并且不会向数据源写入任何内容。
    *  根据正在使用的<code> SyncProvider </code>实现的同步级别,写入程序将比较原始值与数据源中的值,以检查冲突。
    * <P>
    * 应用程序可以选择捕获<code> SyncProviderException </code>对象并检索其包含的<code> SyncResolver </code>对象。
    *  <code> SyncResolver </code>对象逐行列出冲突,并在数据源上设置锁定,以避免在解决当前冲突时发生进一步冲突。
    * 此外,对于每个冲突,它提供了用于检查冲突和设置应该在数据源中持久化的值的方法。所有冲突解决后,应用程序必须再次调用<code> acceptChanges </code>方法将解析的值写入数据源。
    * 如果数据源中的所有值都是要持久化的值,则方法<code> acceptChanges </code>不会执行任何操作。
    * <P>
    *  一些提供程序实现可以使用锁来确保没有冲突。在这种情况下,保证当调用方法<code> acceptChanges </code>时,写入器将成功地写入对数据源的更改。
    * 在方法<code> updateRow </code>,<code> insertRow </code>或<code> deleteRow </code>被调用之后,可以立即调用此方法,但调用方法更有效
    * 在完成所有改变之后,使得仅需要建立一个连接。
    *  一些提供程序实现可以使用锁来确保没有冲突。在这种情况下,保证当调用方法<code> acceptChanges </code>时,写入器将成功地写入对数据源的更改。
    * <P>
    * 注意：<code> acceptChanges()</code>方法将确定<code> COMMIT_ON_ACCEPT_CHANGES </code>是否设置为true。
    * 如果设置为true,那么同步中的所有更新都将提交到数据源。否则,应用程序<b>必须</b>显式调用<code> commit()</code>或<code> rollback()</code>方法。
    * 
    * 
    * @throws SyncProviderException if the underlying
    * synchronization provider's writer fails to write the updates
    * back to the data source
    * @see #acceptChanges(java.sql.Connection)
    * @see javax.sql.RowSetWriter
    * @see javax.sql.rowset.spi.SyncFactory
    * @see javax.sql.rowset.spi.SyncProvider
    * @see javax.sql.rowset.spi.SyncProviderException
    * @see javax.sql.rowset.spi.SyncResolver
    */
    public void acceptChanges() throws SyncProviderException;

   /**
    * Propagates all row update, insert and delete changes to the
    * data source backing this <code>CachedRowSet</code> object
    * using the specified <code>Connection</code> object to establish a
    * connection to the data source.
    * <P>
    * The other version of the <code>acceptChanges</code> method is not passed
    * a connection because it uses
    * the <code>Connection</code> object already defined within the <code>RowSet</code>
    * object, which is the connection used for populating it initially.
    * <P>
    * This form of the method <code>acceptChanges</code> is similar to the
    * form that takes no arguments; however, unlike the other form, this form
    * can be used only when the underlying data source is a JDBC data source.
    * The updated <code>Connection</code> properties must be used by the
    * <code>SyncProvider</code> to reset the <code>RowSetWriter</code>
    * configuration to ensure that the contents of the <code>CachedRowSet</code>
    * object are synchronized correctly.
    * <P>
    * When the method <code>acceptChanges</code> executes successfully, in
    * addition to writing changes to the data source, it
    * makes the values in the current row be the values in the original row.
    * <P>
    * Depending on the synchronization level of the <code>SyncProvider</code>
    * implementation being used, the writer will compare the original values
    * with those in the data source to check for conflicts. When there is a conflict,
    * the <code>RIOptimisticProvider</code> implementation, for example, throws a
    * <code>SyncProviderException</code> and does not write anything to the
    * data source.
    * <P>
    * An application may choose to catch the <code>SyncProviderException</code>
    * object and retrieve the <code>SyncResolver</code> object it contains.
    * The <code>SyncResolver</code> object lists the conflicts row by row and
    * sets a lock on the data source to avoid further conflicts while the
    * current conflicts are being resolved.
    * Further, for each conflict, it provides methods for examining the conflict
    * and setting the value that should be persisted in the data source.
    * After all conflicts have been resolved, an application must call the
    * <code>acceptChanges</code> method again to write resolved values to the
    * data source.  If all of the values in the data source are already the
    * values to be persisted, the method <code>acceptChanges</code> does nothing.
    * <P>
    * Some provider implementations may use locks to ensure that there are no
    * conflicts.  In such cases, it is guaranteed that the writer will succeed in
    * writing changes to the data source when the method <code>acceptChanges</code>
    * is called.  This method may be called immediately after the methods
    * <code>updateRow</code>, <code>insertRow</code>, or <code>deleteRow</code>
    * have been called, but it is more efficient to call it only once after
    * all changes have been made so that only one connection needs to be
    * established.
    * <P>
    * Note: The <code>acceptChanges()</code> method will determine if the
    * <code>COMMIT_ON_ACCEPT_CHANGES</code> is set to true or not. If it is set
    * to true, all updates in the synchronization are committed to the data
    * source. Otherwise, the application <b>must</b> explicity call the
    * <code>commit</code> or <code>rollback</code> methods as appropriate.
    *
    * <p>
    *  使用指定的<code> Connection </code>对象来传播所有行更新,插入和删除对数据源的更改以支持此<code> CachedRowSet </code>对象,以建立与数据源的连接。
    * <P>
    *  <code> acceptChanges </code>方法的另一个版本不传递连接,因为它使用<code> RowSet </code>对象中定义的<code> Connection </code>对
    * 象,用于最初填充它。
    * <P>
    *  这种形式的方法<code> acceptChanges </code>类似于不带参数的形式;但是,与其他形式不同,此表单只能在底层数据源是JDBC数据源时使用。
    * 更新的<code> Connection </code>属性必须由<code> SyncProvider </code>使用以重置<code> RowSetWriter </code>配置,以确保<code>
    *  CachedRowSet </code >对象正确同步。
    *  这种形式的方法<code> acceptChanges </code>类似于不带参数的形式;但是,与其他形式不同,此表单只能在底层数据源是JDBC数据源时使用。
    * <P>
    *  当方法<code> acceptChanges </code>成功执行时,除了对数据源进行更改之外,还会使当前行中的值为原始行中的值。
    * <P>
    * 根据正在使用的<code> SyncProvider </code>实现的同步级别,写入程序将比较原始值与数据源中的值,以检查冲突。
    * 当存在冲突时,<code> RIOptimisticProvider </code>实现会抛出一个<code> SyncProviderException </code>,并且不会向数据源写入任何内容。
    * 根据正在使用的<code> SyncProvider </code>实现的同步级别,写入程序将比较原始值与数据源中的值,以检查冲突。
    * <P>
    *  应用程序可以选择捕获<code> SyncProviderException </code>对象并检索其包含的<code> SyncResolver </code>对象。
    *  <code> SyncResolver </code>对象逐行列出冲突,并在数据源上设置锁定,以避免在解决当前冲突时发生进一步冲突。
    * 此外,对于每个冲突,它提供了用于检查冲突和设置应该在数据源中持久化的值的方法。所有冲突解决后,应用程序必须再次调用<code> acceptChanges </code>方法将解析的值写入数据源。
    * 如果数据源中的所有值都是要持久化的值,则方法<code> acceptChanges </code>不会执行任何操作。
    * <P>
    * 一些提供程序实现可以使用锁来确保没有冲突。在这种情况下,保证当调用方法<code> acceptChanges </code>时,写入器将成功地写入对数据源的更改。
    * 在方法<code> updateRow </code>,<code> insertRow </code>或<code> deleteRow </code>被调用之后,可以立即调用此方法,但调用方法更有效
    * 在完成所有改变之后,使得仅需要建立一个连接。
    * 一些提供程序实现可以使用锁来确保没有冲突。在这种情况下,保证当调用方法<code> acceptChanges </code>时,写入器将成功地写入对数据源的更改。
    * <P>
    *  注意：<code> acceptChanges()</code>方法将确定<code> COMMIT_ON_ACCEPT_CHANGES </code>是否设置为true。
    * 如果设置为true,那么同步中的所有更新都将提交到数据源。否则,应用程序<b>必须</b>显式调用<code> commit </code>或<code> rollback </code>方法。
    * 
    * 
    * @param con a standard JDBC <code>Connection</code> object
    * @throws SyncProviderException if the underlying
    * synchronization provider's writer fails to write the updates
    * back to the data source
    * @see #acceptChanges()
    * @see javax.sql.RowSetWriter
    * @see javax.sql.rowset.spi.SyncFactory
    * @see javax.sql.rowset.spi.SyncProvider
    * @see javax.sql.rowset.spi.SyncProviderException
    * @see javax.sql.rowset.spi.SyncResolver
    */
    public void acceptChanges(Connection con) throws SyncProviderException;

   /**
    * Restores this <code>CachedRowSet</code> object to its original
    * value, that is, its value before the last set of changes. If there
    * have been no changes to the rowset or only one set of changes,
    * the original value is the value with which this <code>CachedRowSet</code> object
    * was populated; otherwise, the original value is
    * the value it had immediately before its current value.
    * <P>
    * When this method is called, a <code>CachedRowSet</code> implementation
    * must ensure that all updates, inserts, and deletes to the current
    * rowset instance are replaced by the previous values. In addition,
    * the cursor should be
    * reset to the first row and a <code>rowSetChanged</code> event
    * should be fired to notify all registered listeners.
    *
    * <p>
    *  将<code> CachedRowSet </code>对象恢复为其原始值,即其在最后一组更改之前的值。
    * 如果没有更改行集或只有一组更改,原始值是填充此<c> cachedRowSet </code>对象的值;否则,原始值是其在当前值之前的值。
    * <P>
    * 调用此方法时,<code> CachedRowSet </code>实现必须确保将当前行集实例的所有更新,插入和删除都替换为以前的值。
    * 此外,游标应该重置为第一行,并且应该触发<code> rowSetChanged </code>事件以通知所有已注册的侦听器。
    * 
    * 
    * @throws SQLException if an error occurs rolling back the current value of
    *       this <code>CachedRowSet</code> object to its previous value
    * @see javax.sql.RowSetListener#rowSetChanged
    */
    public void restoreOriginal() throws SQLException;

   /**
    * Releases the current contents of this <code>CachedRowSet</code>
    * object and sends a <code>rowSetChanged</code> event to all
    * registered listeners. Any outstanding updates are discarded and
    * the rowset contains no rows after this method is called. There
    * are no interactions with the underlying data source, and any rowset
    * content, metadata, and content updates should be non-recoverable.
    * <P>
    * This <code>CachedRowSet</code> object should lock until its contents and
    * associated updates are fully cleared, thus preventing 'dirty' reads by
    * other components that hold a reference to this <code>RowSet</code> object.
    * In addition, the contents cannot be released
    * until all all components reading this <code>CachedRowSet</code> object
    * have completed their reads. This <code>CachedRowSet</code> object
    * should be returned to normal behavior after firing the
    * <code>rowSetChanged</code> event.
    * <P>
    * The metadata, including JDBC properties and Synchronization SPI
    * properties, are maintained for future use. It is important that
    * properties such as the <code>command</code> property be
    * relevant to the originating data source from which this <code>CachedRowSet</code>
    * object was originally established.
    * <P>
    * This method empties a rowset, as opposed to the <code>close</code> method,
    * which marks the entire rowset as recoverable to allow the garbage collector
    * the rowset's Java VM resources.
    *
    * <p>
    *  释放此<c> cachedRowSet </code>对象的当前内容,并向所有注册的侦听器发送一个<code> rowSetChanged </code>事件。
    * 在调用此方法后,将舍弃任何未完成的更新,并且行集不包含行。没有与底层数据源的交互,任何行集内容,元数据和内容更新都应该是不可恢复的。
    * <P>
    *  这个<code> CachedRowSet </code>对象应该锁定,直到它的内容和关联的更新被完全清除,从而防止其他保存对这个<code> RowSet </code>对象的引用的"脏"读取。
    * 此外,在读取此<code> CachedRowSet </code>对象的所有组件都完成其读取之前,内容不能被释放。
    * 这个<code> CachedRowSet </code>对象应该在激发<code> rowSetChanged </code>事件后返回正常行为。
    * <P>
    * 维护元数据(包括JDBC属性和同步SPI属性)以供将来使用。
    * 重要的是,诸如<code> command </code>属性的属性与最初建立该<code> CachedRowSet </code>对象的源数据源相关。
    * <P>
    *  此方法清空行集,而不是<code> close </code>方法,它将整个行集标记为可恢复,以允许垃圾回收器行集的Java VM资源。
    * 
    * 
    * @throws SQLException if an error occurs flushing the contents of this
    * <code>CachedRowSet</code> object
    * @see javax.sql.RowSetListener#rowSetChanged
    * @see java.sql.ResultSet#close
    */
    public void release() throws SQLException;

   /**
    * Cancels the deletion of the current row and notifies listeners that
    * a row has changed. After this method is called, the current row is
    * no longer marked for deletion. This method can be called at any
    * time during the lifetime of the rowset.
    * <P>
    * In addition, multiple cancellations of row deletions can be made
    * by adjusting the position of the cursor using any of the cursor
    * position control methods such as:
    * <ul>
    * <li><code>CachedRowSet.absolute</code>
    * <li><code>CachedRowSet.first</code>
    * <li><code>CachedRowSet.last</code>
    * </ul>
    *
    * <p>
    *  取消删除当前行,并通知侦听器行已更改。调用此方法后,当前行不再标记为要删除。此方法可以在行集的生存期内的任何时间调用。
    * <P>
    *  此外,可以通过使用任何光标位置控制方法来调整光标的位置来进行行删除的多次取消,诸如：
    * <ul>
    *  <li> <code> CachedRowSet.absolute </code> <li> <code> CachedRowSet.first </code> <li> <code> CachedR
    * owSet.last </code>。
    * </ul>
    * 
    * 
    * @throws SQLException if (1) the current row has not been deleted or
    * (2) the cursor is on the insert row, before the first row, or
    * after the last row
    * @see javax.sql.rowset.CachedRowSet#undoInsert
    * @see java.sql.ResultSet#cancelRowUpdates
    */
    public void undoDelete() throws SQLException;

   /**
    * Immediately removes the current row from this <code>CachedRowSet</code>
    * object if the row has been inserted, and also notifies listeners that a
    * row has changed. This method can be called at any time during the
    * lifetime of a rowset and assuming the current row is within
    * the exception limitations (see below), it cancels the row insertion
    * of the current row.
    * <P>
    * In addition, multiple cancellations of row insertions can be made
    * by adjusting the position of the cursor using any of the cursor
    * position control methods such as:
    * <ul>
    * <li><code>CachedRowSet.absolute</code>
    * <li><code>CachedRowSet.first</code>
    * <li><code>CachedRowSet.last</code>
    * </ul>
    *
    * <p>
    *  如果已插入该行,则立即从此<c> cachedRowSet </code>对象中删除当前行,并且还通知侦听器行已更改。
    * 此方法可以在行集的生存期内的任何时间调用,并且假设当前行在异常限制(见下文)中,它将取消当前行的行插入。
    * <P>
    * 此外,可以通过使用任何光标位置控制方法来调整光标的位置来进行行插入的多次取消,例如：
    * <ul>
    *  <li> <code> CachedRowSet.absolute </code> <li> <code> CachedRowSet.first </code> <li> <code> CachedR
    * owSet.last </code>。
    * </ul>
    * 
    * 
    * @throws SQLException if (1) the current row has not been inserted or (2)
    * the cursor is before the first row, after the last row, or on the
    * insert row
    * @see javax.sql.rowset.CachedRowSet#undoDelete
    * @see java.sql.ResultSet#cancelRowUpdates
    */
    public void undoInsert() throws SQLException;


   /**
    * Immediately reverses the last update operation if the
    * row has been modified. This method can be
    * called to reverse updates on all columns until all updates in a row have
    * been rolled back to their state just prior to the last synchronization
    * (<code>acceptChanges</code>) or population. This method may also be called
    * while performing updates to the insert row.
    * <P>
    * <code>undoUpdate</code> may be called at any time during the lifetime of a
    * rowset; however, after a synchronization has occurred, this method has no
    * effect until further modification to the rowset data has occurred.
    *
    * <p>
    *  如果行已被修改,则立即反转上次更新操作。可以调用此方法以反转对所有列的更新,直到行中的所有更新已回滚到上次同步(<code> acceptChanges </code>)或填充之前的状态。
    * 在执行插入行的更新时,也可以调用此方法。
    * <P>
    *  <code> undoUpdate </code>可以在行集的生存期内的任何时间被调用;然而,在同步已经发生之后,该方法没有效果,直到发生对行集数据的进一步修改。
    * 
    * 
    * @throws SQLException if the cursor is before the first row or after the last
    *     row in in this <code>CachedRowSet</code> object
    * @see #undoDelete
    * @see #undoInsert
    * @see java.sql.ResultSet#cancelRowUpdates
    */
    public void undoUpdate() throws SQLException;

   /**
    * Indicates whether the designated column in the current row of this
    * <code>CachedRowSet</code> object has been updated.
    *
    * <p>
    *  指示此<code> CachedRowSet </code>对象的当前行中的指定列是否已更新。
    * 
    * 
    * @param idx an <code>int</code> identifying the column to be checked for updates
    * @return <code>true</code> if the designated column has been visibly updated;
    * <code>false</code> otherwise
    * @throws SQLException if the cursor is on the insert row, before the first row,
    *     or after the last row
    * @see java.sql.DatabaseMetaData#updatesAreDetected
    */
    public boolean columnUpdated(int idx) throws SQLException;


   /**
    * Indicates whether the designated column in the current row of this
    * <code>CachedRowSet</code> object has been updated.
    *
    * <p>
    *  指示此<code> CachedRowSet </code>对象的当前行中的指定列是否已更新。
    * 
    * 
    * @param columnName a <code>String</code> object giving the name of the
    *        column to be checked for updates
    * @return <code>true</code> if the column has been visibly updated;
    * <code>false</code> otherwise
    * @throws SQLException if the cursor is on the insert row, before the first row,
    *      or after the last row
    * @see java.sql.DatabaseMetaData#updatesAreDetected
    */
    public boolean columnUpdated(String columnName) throws SQLException;

   /**
    * Converts this <code>CachedRowSet</code> object to a <code>Collection</code>
    * object that contains all of this <code>CachedRowSet</code> object's data.
    * Implementations have some latitude in
    * how they can represent this <code>Collection</code> object because of the
    * abstract nature of the <code>Collection</code> framework.
    * Each row must be fully represented in either a
    * general purpose <code>Collection</code> implementation or a specialized
    * <code>Collection</code> implementation, such as a <code>TreeMap</code>
    * object or a <code>Vector</code> object.
    * An SQL <code>NULL</code> column value must be represented as a <code>null</code>
    * in the Java programming language.
    * <P>
    * The standard reference implementation for the <code>CachedRowSet</code>
    * interface uses a <code>TreeMap</code> object for the rowset, with the
    * values in each row being contained in  <code>Vector</code> objects. It is
    * expected that most implementations will do the same.
    * <P>
    * The <code>TreeMap</code> type of collection guarantees that the map will be in
    * ascending key order, sorted according to the natural order for the
    * key's class.
    * Each key references a <code>Vector</code> object that corresponds to one
    * row of a <code>RowSet</code> object. Therefore, the size of each
    * <code>Vector</code> object  must be exactly equal to the number of
    * columns in the <code>RowSet</code> object.
    * The key used by the <code>TreeMap</code> collection is determined by the
    * implementation, which may choose to leverage a set key that is
    * available within the internal <code>RowSet</code> tabular structure by
    * virtue of a key already set either on the <code>RowSet</code> object
    * itself or on the underlying SQL data.
    * <P>
    *
    * <p>
    * 将这个<code> CachedRowSet </code>对象转换为包含所有这些<code> CachedRowSet </code>对象数据的<code> Collection </code>对象。
    * 由于<code> Collection </code>框架的抽象本质,实现在如何表示这个<code> Collection </code>对象方面有一些自由。
    * 每一行必须在通用<code>集合</code>实现或专用<code>集合</code>实现中完全表示,例如<code> TreeMap </code>对象或<code> Vector </code>对象
    * 。
    * 由于<code> Collection </code>框架的抽象本质,实现在如何表示这个<code> Collection </code>对象方面有一些自由。
    * 在Java编程语言中,SQL <code> NULL </code>列值必须表示为<code> null </code>。
    * <P>
    *  <code> CachedRowSet </code>接口的标准引用实现使用行集的<code> TreeMap </code>对象,每行中的值包含在<code> Vector </code>对象中。
    * 预期大多数实现将执行相同的操作。
    * <P>
    * <code> TreeMap </code>类型的集合保证映射将按照键的升序排序,根据键的类的自然顺序排序。
    * 每个键引用对应于<code> RowSet </code>对象的一行的<code> Vector </code>对象。
    * 因此,每个<code> Vector </code>对象的大小必须完全等于<code> RowSet </code>对象中的列数。
    *  <code> TreeMap </code>集合使用的密钥由实现决定,该实现可以选择利用内部<code> RowSet </code>表格结构中可用的集合键,设置在<code> RowSet </code>
    * 对象本身或底层SQL数据上。
    * 因此,每个<code> Vector </code>对象的大小必须完全等于<code> RowSet </code>对象中的列数。
    * <P>
    * 
    * 
    * @return a <code>Collection</code> object that contains the values in
    * each row in this <code>CachedRowSet</code> object
    * @throws SQLException if an error occurs generating the collection
    * @see #toCollection(int)
    * @see #toCollection(String)
    */
    public Collection<?> toCollection() throws SQLException;

   /**
    * Converts the designated column in this <code>CachedRowSet</code> object
    * to a <code>Collection</code> object. Implementations have some latitude in
    * how they can represent this <code>Collection</code> object because of the
    * abstract nature of the <code>Collection</code> framework.
    * Each column value should be fully represented in either a
    * general purpose <code>Collection</code> implementation or a specialized
    * <code>Collection</code> implementation, such as a <code>Vector</code> object.
    * An SQL <code>NULL</code> column value must be represented as a <code>null</code>
    * in the Java programming language.
    * <P>
    * The standard reference implementation uses a <code>Vector</code> object
    * to contain the column values, and it is expected
    * that most implementations will do the same. If a <code>Vector</code> object
    * is used, it size must be exactly equal to the number of rows
    * in this <code>CachedRowSet</code> object.
    *
    * <p>
    *  将<code> CachedRowSet </code>对象中的指定列转换为<code> Collection </code>对象。
    * 由于<code> Collection </code>框架的抽象本质,实现在如何表示这个<code> Collection </code>对象方面有一些自由。
    * 每个列值应在通用<code> Collection </code>实现或专用<code> Collection </code>实现(如<code> Vector </code>对象)中完全表示。
    * 在Java编程语言中,SQL <code> NULL </code>列值必须表示为<code> null </code>。
    * <P>
    * 标准引用实现使用<code> Vector </code>对象来包含列值,并且预期大多数实现将执行相同操作。
    * 如果使用了一个<code> Vector </code>对象,它的大小必须与这个<code> CachedRowSet </code>对象中的行数完全相等。
    * 
    * 
    * @param column an <code>int</code> indicating the column whose values
    *        are to be represented in a <code>Collection</code> object
    * @return a <code>Collection</code> object that contains the values
    * stored in the specified column of this <code>CachedRowSet</code>
    * object
    * @throws SQLException if an error occurs generating the collection or
    * an invalid column id is provided
    * @see #toCollection
    * @see #toCollection(String)
    */
    public Collection<?> toCollection(int column) throws SQLException;

   /**
    * Converts the designated column in this <code>CachedRowSet</code> object
    * to a <code>Collection</code> object. Implementations have some latitude in
    * how they can represent this <code>Collection</code> object because of the
    * abstract nature of the <code>Collection</code> framework.
    * Each column value should be fully represented in either a
    * general purpose <code>Collection</code> implementation or a specialized
    * <code>Collection</code> implementation, such as a <code>Vector</code> object.
    * An SQL <code>NULL</code> column value must be represented as a <code>null</code>
    * in the Java programming language.
    * <P>
    * The standard reference implementation uses a <code>Vector</code> object
    * to contain the column values, and it is expected
    * that most implementations will do the same. If a <code>Vector</code> object
    * is used, it size must be exactly equal to the number of rows
    * in this <code>CachedRowSet</code> object.
    *
    * <p>
    *  将<code> CachedRowSet </code>对象中的指定列转换为<code> Collection </code>对象。
    * 由于<code> Collection </code>框架的抽象本质,实现在如何表示这个<code> Collection </code>对象方面有一些自由。
    * 每个列值应在通用<code> Collection </code>实现或专用<code> Collection </code>实现(如<code> Vector </code>对象)中完全表示。
    * 在Java编程语言中,SQL <code> NULL </code>列值必须表示为<code> null </code>。
    * <P>
    *  标准引用实现使用<code> Vector </code>对象来包含列值,并且预期大多数实现将执行相同操作。
    * 如果使用了一个<code> Vector </code>对象,它的大小必须完全等于这个<code> CachedRowSet </code>对象中的行数。
    * 
    * 
    * @param column a <code>String</code> object giving the name of the
    *        column whose values are to be represented in a collection
    * @return a <code>Collection</code> object that contains the values
    * stored in the specified column of this <code>CachedRowSet</code>
    * object
    * @throws SQLException if an error occurs generating the collection or
    * an invalid column id is provided
    * @see #toCollection
    * @see #toCollection(int)
    */
    public Collection<?> toCollection(String column) throws SQLException;

   /**
    * Retrieves the <code>SyncProvider</code> implementation for this
    * <code>CachedRowSet</code> object. Internally, this method is used by a rowset
    * to trigger read or write actions between the rowset
    * and the data source. For example, a rowset may need to get a handle
    * on the the rowset reader (<code>RowSetReader</code> object) from the
    * <code>SyncProvider</code> to allow the rowset to be populated.
    * <pre>
    *     RowSetReader rowsetReader = null;
    *     SyncProvider provider =
    *         SyncFactory.getInstance("javax.sql.rowset.provider.RIOptimisticProvider");
    *         if (provider instanceof RIOptimisticProvider) {
    *             rowsetReader = provider.getRowSetReader();
    *         }
    * </pre>
    * Assuming <i>rowsetReader</i> is a private, accessible field within
    * the rowset implementation, when an application calls the <code>execute</code>
    * method, it in turn calls on the reader's <code>readData</code> method
    * to populate the <code>RowSet</code> object.
    *<pre>
    *     rowsetReader.readData((RowSetInternal)this);
    * </pre>
    * <P>
    * In addition, an application can use the <code>SyncProvider</code> object
    * returned by this method to call methods that return information about the
    * <code>SyncProvider</code> object, including information about the
    * vendor, version, provider identification, synchronization grade, and locks
    * it currently has set.
    *
    * <p>
    * 检索此代码<code> CachedRowSet </code>对象的<code> SyncProvider </code>实现。在内部,行方法使用此方法来触发行集和数据源之间的读取或写入操作。
    * 例如,行集可能需要从<code> SyncProvider </code>中的行集读取器(<code> RowSetReader </code>对象)获取句柄,以允许填充行集。
    * <pre>
    *  RowSetReader rowsetReader = null; SyncProvider provider = SyncFactory.getInstance("javax.sql.rowset.
    * provider.RIOptimisticProvider"); if(provider instanceof RIOptimisticProvider){rowsetReader = provider.getRowSetReader(); }
    * }。
    * </pre>
    *  假设<i> rowsetReader </i>是行集实现中的一个私有的可访问字段,当应用程序调用<code> execute </code>方法时,它会依次调用读取器的<code> readData 
    * </code>方法来填充<code> RowSet </code>对象。
    * pre>
    *  rowsetReader.readData((RowSetInternal)this);
    * </pre>
    * <P>
    *  此外,应用程序可以使用此方法返回的<code> SyncProvider </code>对象调用返回有关<code> SyncProvider </code>对象的信息的方法,包括有关供应商,版本,同
    * 步等级和当前设置的锁定。
    * 
    * 
    * @return the <code>SyncProvider</code> object that was set when the rowset
    *      was instantiated, or if none was was set, the default provider
    * @throws SQLException if an error occurs while returning the
    * <code>SyncProvider</code> object
    * @see #setSyncProvider
    */
    public SyncProvider getSyncProvider() throws SQLException;

   /**
    * Sets the <code>SyncProvider</code> object for this <code>CachedRowSet</code>
    * object to the one specified.  This method
    * allows the <code>SyncProvider</code> object to be reset.
    * <P>
    * A <code>CachedRowSet</code> implementation should always be instantiated
    * with an available <code>SyncProvider</code> mechanism, but there are
    * cases where resetting the <code>SyncProvider</code> object is desirable
    * or necessary. For example, an application might want to use the default
    * <code>SyncProvider</code> object for a time and then choose to use a provider
    * that has more recently become available and better fits its needs.
    * <P>
    * Resetting the <code>SyncProvider</code> object causes the
    * <code>RowSet</code> object to request a new <code>SyncProvider</code> implementation
    * from the <code>SyncFactory</code>. This has the effect of resetting
    * all previous connections and relationships with the originating
    * data source and can potentially drastically change the synchronization
    * behavior of a disconnected rowset.
    *
    * <p>
    *  将<code> CachedRowSet </code>对象的<code> SyncProvider </code>对象设置为指定的对象。
    * 此方法允许重置<code> SyncProvider </code>对象。
    * <P>
    * 一个<code> CachedRowSet </code>实现应该始终使用一个可用的<code> SyncProvider </code>机制来实例化,但是有一些情况下重置<code> SyncProv
    * ider </code>对象是可取的或必要的。
    * 例如,应用程序可能希望使用默认的<code> SyncProvider </code>对象一段时间,然后选择使用最近更新的提供程序,并更好地满足其需求。
    * <P>
    *  重置<code> SyncProvider </code>对象会使<code> RowSet </code>对象从<code> SyncFactory </code>请求一个新的<code> Sync
    * Provider </code>实现。
    * 这具有重置所有先前的连接和与始发数据源的关系的效果,并且可能潜在地大幅度地改变断开的行集合的同步行为。
    * 
    * 
    * @param provider a <code>String</code> object giving the fully qualified class
    *        name of a <code>SyncProvider</code> implementation
    * @throws SQLException if an error occurs while attempting to reset the
    * <code>SyncProvider</code> implementation
    * @see #getSyncProvider
    */
    public void setSyncProvider(String provider) throws SQLException;

   /**
    * Returns the number of rows in this <code>CachedRowSet</code>
    * object.
    *
    * <p>
    *  返回此<c> CachedRowSet </code>对象中的行数。
    * 
    * 
    * @return number of rows in the rowset
    */
    public int size();

   /**
    * Sets the metadata for this <code>CachedRowSet</code> object with
    * the given <code>RowSetMetaData</code> object. When a
    * <code>RowSetReader</code> object is reading the contents of a rowset,
    * it creates a <code>RowSetMetaData</code> object and initializes
    * it using the methods in the <code>RowSetMetaData</code> implementation.
    * The reference implementation uses the <code>RowSetMetaDataImpl</code>
    * class. When the reader has completed reading the rowset contents,
    * this method is called internally to pass the <code>RowSetMetaData</code>
    * object to the rowset.
    *
    * <p>
    *  使用给定的<code> RowSetMetaData </code>对象设置此<c> cachedRowSet </code>对象的元数据。
    * 当一个<code> RowSetReader </code>对象读取一个行集的内容时,它创建一个<code> RowSetMetaData </code>对象,并使用<code> RowSetMetaD
    * ata </code>参考实现使用<code> RowSetMetaDataImpl </code>类。
    *  使用给定的<code> RowSetMetaData </code>对象设置此<c> cachedRowSet </code>对象的元数据。
    * 当读取器已经完成读取行集内容时,内部调用此方法以将<code> RowSetMetaData </code>对象传递到行集。
    * 
    * 
    * @param md a <code>RowSetMetaData</code> object containing
    * metadata about the columns in this <code>CachedRowSet</code> object
    * @throws SQLException if invalid metadata is supplied to the
    * rowset
    */
    public void setMetaData(RowSetMetaData md) throws SQLException;

   /**
    * Returns a <code>ResultSet</code> object containing the original value of this
    * <code>CachedRowSet</code> object.
    * <P>
    * The cursor for the <code>ResultSet</code>
    * object should be positioned before the first row.
    * In addition, the returned <code>ResultSet</code> object should have the following
    * properties:
    * <UL>
    * <LI>ResultSet.TYPE_SCROLL_INSENSITIVE
    * <LI>ResultSet.CONCUR_UPDATABLE
    * </UL>
    * <P>
    * The original value for a <code>RowSet</code> object is the value it had before
    * the last synchronization with the underlying data source.  If there have been
    * no synchronizations, the original value will be the value with which the
    * <code>RowSet</code> object was populated.  This method is called internally
    * when an application calls the method <code>acceptChanges</code> and the
    * <code>SyncProvider</code> object has been implemented to check for conflicts.
    * If this is the case, the writer compares the original value with the value
    * currently in the data source to check for conflicts.
    *
    * <p>
    * 返回一个包含此<c> cachedRowSet </code>对象的原始值的<code> ResultSet </code>对象。
    * <P>
    *  <code> ResultSet </code>对象的游标应位于第一行之前。此外,返回的<code> ResultSet </code>对象应该具有以下属性：
    * <UL>
    *  <LI> ResultSet.TYPE_SCROLL_INSENSITIVE <LI> ResultSet.CONCUR_UPDATABLE
    * </UL>
    * <P>
    *  <code> RowSet </code>对象的原始值是它与上一次与底层数据源同步之前的值。如果没有同步,原始值将是填充<code> RowSet </code>对象的值。
    * 当应用程序调用<code> acceptChanges </code>方法并且已实现<code> SyncProvider </code>对象以检查冲突时,会在内部调用此方法。
    * 如果是这种情况,编写器将原始值与数据源中当前的值进行比较,以检查冲突。
    * 
    * 
    * @return a <code>ResultSet</code> object that contains the original value for
    *         this <code>CachedRowSet</code> object
    * @throws SQLException if an error occurs producing the
    * <code>ResultSet</code> object
    */
   public ResultSet getOriginal() throws SQLException;

   /**
    * Returns a <code>ResultSet</code> object containing the original value for the
    * current row only of this <code>CachedRowSet</code> object.
    * <P>
    * The cursor for the <code>ResultSet</code>
    * object should be positioned before the first row.
    * In addition, the returned <code>ResultSet</code> object should have the following
    * properties:
    * <UL>
    * <LI>ResultSet.TYPE_SCROLL_INSENSITIVE
    * <LI>ResultSet.CONCUR_UPDATABLE
    * </UL>
    *
    * <p>
    *  返回一个<code> ResultSet </code>对象,该对象只包含此<c> cachedRowSet </code>对象的当前行的原始值。
    * <P>
    *  <code> ResultSet </code>对象的游标应位于第一行之前。此外,返回的<code> ResultSet </code>对象应该具有以下属性：
    * <UL>
    *  <LI> ResultSet.TYPE_SCROLL_INSENSITIVE <LI> ResultSet.CONCUR_UPDATABLE
    * </UL>
    * 
    * 
    * @return the original result set of the row
    * @throws SQLException if there is no current row
    * @see #setOriginalRow
    */
    public ResultSet getOriginalRow() throws SQLException;

   /**
    * Sets the current row in this <code>CachedRowSet</code> object as the original
    * row.
    * <P>
    * This method is called internally after the any modified values in the current
    * row have been synchronized with the data source. The current row must be tagged
    * as no longer inserted, deleted or updated.
    * <P>
    * A call to <code>setOriginalRow</code> is irreversible.
    *
    * <p>
    *  将此<c> cachedRowSet </code>对象中的当前行设置为原始行。
    * <P>
    * 在当前行中的任何修改值已与数据源同步之后,将内部调用此方法。当前行必须标记为不再插入,删除或更新。
    * <P>
    *  对<code> setOriginalRow </code>的调用是不可逆的。
    * 
    * 
    * @throws SQLException if there is no current row or an error is
    * encountered resetting the contents of the original row
    * @see #getOriginalRow
    */
    public void setOriginalRow() throws SQLException;

   /**
    * Returns an identifier for the object (table) that was used to
    * create this <code>CachedRowSet</code> object. This name may be set on multiple occasions,
    * and the specification imposes no limits on how many times this
    * may occur or whether standard implementations should keep track
    * of previous table names.
    *
    * <p>
    *  返回用于创建此<c> cachedRowSet </code>对象的对象(表)的标识符。该名称可以在多个场合设置,并且该规范对这可能发生多少次没有限制,或者标准实现是否应该跟踪以前的表名称。
    * 
    * 
    * @return a <code>String</code> object giving the name of the table that is the
    *         source of data for this <code>CachedRowSet</code> object or <code>null</code>
    *         if no name has been set for the table
    * @throws SQLException if an error is encountered returning the table name
    * @see javax.sql.RowSetMetaData#getTableName
    */
    public String getTableName() throws SQLException;

   /**
    * Sets the identifier for the table from which this <code>CachedRowSet</code>
    * object was derived to the given table name. The writer uses this name to
    * determine which table to use when comparing the values in the data source with the
    * <code>CachedRowSet</code> object's values during a synchronization attempt.
    * The table identifier also indicates where modified values from this
    * <code>CachedRowSet</code> object should be written.
    * <P>
    * The implementation of this <code>CachedRowSet</code> object may obtain the
    * the name internally from the <code>RowSetMetaDataImpl</code> object.
    *
    * <p>
    *  设置从其中导出此<code> CachedRowSet </code>对象的表的标识符到给定的表名。
    * 在同步尝试期间,当将数据源中的值与<code> CachedRowSet </code>对象的值进行比较时,写入程序使用此名称来确定要使用哪个表。
    * 表标识符还指示应该从此<code> CachedRowSet </code>对象修改的值。
    * <P>
    *  这个<code> CachedRowSet </code>对象的实现可以从<code> RowSetMetaDataImpl </code>对象中获得内部名称。
    * 
    * 
    * @param tabName a <code>String</code> object identifying the table from which this
             <code>CachedRowSet</code> object was derived; cannot be <code>null</code>
    *         but may be an empty string
    * @throws SQLException if an error is encountered naming the table or
    *     <i>tabName</i> is <code>null</code>
    * @see javax.sql.RowSetMetaData#setTableName
    * @see javax.sql.RowSetWriter
    * @see javax.sql.rowset.spi.SyncProvider
    */
   public void setTableName(String tabName) throws SQLException;

   /**
    * Returns an array containing one or more column numbers indicating the columns
    * that form a key that uniquely
    * identifies a row in this <code>CachedRowSet</code> object.
    *
    * <p>
    *  返回一个包含一个或多个列号的数组,这些列号指示形成一个键的列,该键唯一地标识此<c> cachedRowSet </code>对象中的一行。
    * 
    * 
    * @return an array containing the column number or numbers that indicate which columns
    *       constitute a primary key
    *       for a row in this <code>CachedRowSet</code> object. This array should be
    *       empty if no columns are representative of a primary key.
    * @throws SQLException if this <code>CachedRowSet</code> object is empty
    * @see #setKeyColumns
    * @see Joinable#getMatchColumnIndexes
    * @see Joinable#getMatchColumnNames
    */
    public int[] getKeyColumns() throws SQLException;

   /**
    * Sets this <code>CachedRowSet</code> object's <code>keyCols</code>
    * field with the given array of column numbers, which forms a key
    * for uniquely identifying a row in this <code>CachedRowSet</code> object.
    * <p>
    * If a <code>CachedRowSet</code> object becomes part of a <code>JoinRowSet</code>
    * object, the keys defined by this method and the resulting constraints are
    * maintained if the columns designated as key columns also become match
    * columns.
    *
    * <p>
    * 用给定的列数数组设置这个<code> CachedRowSet </code>对象的<code> keyCols </code>字段,它形成一个键,用于唯一地标识这个<code> CachedRowSe
    * t </code>对象中的一行。
    * <p>
    *  如果<code> CachedRowSet </code>对象成为<code> JoinRowSet </code>对象的一部分,则如果指定为键列的列也成为匹配列,则维护此方法定义的键和结果约束。
    * 
    * 
    * @param keys an array of <code>int</code> indicating the columns that form
    *        a primary key for this <code>CachedRowSet</code> object; every
    *        element in the array must be greater than <code>0</code> and
    *        less than or equal to the number of columns in this rowset
    * @throws SQLException if any of the numbers in the given array
    *            are not valid for this rowset
    * @see #getKeyColumns
    * @see Joinable#setMatchColumn(String)
    * @see Joinable#setMatchColumn(int)

    */
    public void setKeyColumns(int[] keys) throws SQLException;


   /**
    * Returns a new <code>RowSet</code> object backed by the same data as
    * that of this <code>CachedRowSet</code> object. In effect, both
    * <code>CachedRowSet</code> objects have a cursor over the same data.
    * As a result, any changes made by a duplicate are visible to the original
    * and to any other duplicates, just as a change made by the original is visible
    * to all of its duplicates. If a duplicate calls a method that changes the
    * underlying data, the method it calls notifies all registered listeners
    * just as it would when it is called by the original <code>CachedRowSet</code>
    * object.
    * <P>
    * In addition, any <code>RowSet</code> object
    * created by this method will have the same properties as this
    * <code>CachedRowSet</code> object. For example, if this <code>CachedRowSet</code>
    * object is read-only, all of its duplicates will also be read-only. If it is
    * changed to be updatable, the duplicates also become updatable.
    * <P>
    * NOTE: If multiple threads access <code>RowSet</code> objects created from
    * the <code>createShared()</code> method, the following behavior is specified
    * to preserve shared data integrity: reads and writes of all
    * shared <code>RowSet</code> objects should be made serially between each
    * object and the single underlying tabular structure.
    *
    * <p>
    *  返回一个新的<code> RowSet </code>对象,该对象由与此<code> CachedRowSet </code>对象相同的数据支持。
    * 实际上,<code> CachedRowSet </code>对象都有一个光标在同一个数据上。因此,重复项所做的任何更改对原始项和任何其他重复项都是可见的,就像原始项所做的更改对其所有重复项都可见。
    * 如果重复调用一个改变底层数据的方法,它所调用的方法会通知所有注册的侦听器,就像它被原来的<code> CachedRowSet </code>对象调用时。
    * <P>
    *  此外,由此方法创建的任何<code> RowSet </code>对象将具有与此<c> cachedRowSet </code>对象相同的属性。
    * 例如,如果这个<code> CachedRowSet </code>对象是只读的,它的所有重复项也将是只读的。如果它被更改为可更新,则重复项也可更新。
    * <P>
    * 注意：如果多个线程访问从<code> createShared()</code>方法创建的<code> RowSet </code>对象,则指定以下行为来保留共享数据完整性：读取和写入所有共享< RowSet </code>
    * 对象应该在每个对象和单个下层表格结构之间连续进行。
    * 
    * 
    * @return a new shared <code>RowSet</code> object that has the same properties
    *         as this <code>CachedRowSet</code> object and that has a cursor over
    *         the same data
    * @throws SQLException if an error occurs or cloning is not
    * supported in the underlying platform
    * @see javax.sql.RowSetEvent
    * @see javax.sql.RowSetListener
    */
    public RowSet createShared() throws SQLException;

   /**
    * Creates a <code>RowSet</code> object that is a deep copy of the data in
    * this <code>CachedRowSet</code> object. In contrast to
    * the <code>RowSet</code> object generated from a <code>createShared</code>
    * call, updates made to the copy of the original <code>RowSet</code> object
    * must not be visible to the original <code>RowSet</code> object. Also, any
    * event listeners that are registered with the original
    * <code>RowSet</code> must not have scope over the new
    * <code>RowSet</code> copies. In addition, any constraint restrictions
    * established must be maintained.
    *
    * <p>
    *  创建一个<code> RowSet </code>对象,它是这个<code> CachedRowSet </code>对象中的数据的深度副本。
    * 与从<code> createShared </code>调用生成的<code> RowSet </code>对象相反,对原始<code> RowSet </code>对象的副本所做的更新不能对原始<code>
    *  RowSet </code>对象。
    *  创建一个<code> RowSet </code>对象,它是这个<code> CachedRowSet </code>对象中的数据的深度副本。
    * 此外,注册到原始<code> RowSet </code>的任何事件侦听器都不能超过新的<code> RowSet </code>副本。此外,必须保持所建立的任何约束限制。
    * 
    * 
    * @return a new <code>RowSet</code> object that is a deep copy
    * of this <code>CachedRowSet</code> object and is
    * completely independent of this <code>CachedRowSet</code> object
    * @throws SQLException if an error occurs in generating the copy of
    * the of this <code>CachedRowSet</code> object
    * @see #createShared
    * @see #createCopySchema
    * @see #createCopyNoConstraints
    * @see javax.sql.RowSetEvent
    * @see javax.sql.RowSetListener
    */
    public CachedRowSet createCopy() throws SQLException;

    /**
     * Creates a <code>CachedRowSet</code> object that is an empty copy of this
     * <code>CachedRowSet</code> object.  The copy
     * must not contain any contents but only represent the table
     * structure of the original <code>CachedRowSet</code> object. In addition, primary
     * or foreign key constraints set in the originating <code>CachedRowSet</code> object must
     * be equally enforced in the new empty <code>CachedRowSet</code> object.
     * In contrast to
     * the <code>RowSet</code> object generated from a <code>createShared</code> method
     * call, updates made to a copy of this <code>CachedRowSet</code> object with the
     * <code>createCopySchema</code> method must not be visible to it.
     * <P>
     * Applications can form a <code>WebRowSet</code> object from the <code>CachedRowSet</code>
     * object returned by this method in order
     * to export the <code>RowSet</code> schema definition to XML for future use.
     * <p>
     * 创建一个<code> CachedRowSet </code>对象,它是此<c> cachedRowSet </code>对象的空副本。
     * 副本不能包含任何内容,而只能表示原始<code> CachedRowSet </code>对象的表结构。
     * 此外,在原始<code> CachedRowSet </code>对象中设置的主键或外键约束必须在新的空的<code> CachedRowSet </code>对象中同样强制执行。
     * 与从<code> createShared </code>方法调用生成的<code> RowSet </code>对象相比,对<code> CachedRowSet </code>对象的副本使用<code>
     *  createCopySchema < / code>方法不可见。
     * 此外,在原始<code> CachedRowSet </code>对象中设置的主键或外键约束必须在新的空的<code> CachedRowSet </code>对象中同样强制执行。
     * <P>
     *  应用程序可以从此方法返回的<code> CachedRowSet </code>对象中形成<code> WebRowSet </code>对象,以便将<code> RowSet </code>模式定义
     * 导出到XML以供将来使用。
     * 
     * 
     * @return An empty copy of this {@code CachedRowSet} object
     * @throws SQLException if an error occurs in cloning the structure of this
     *         <code>CachedRowSet</code> object
     * @see #createShared
     * @see #createCopySchema
     * @see #createCopyNoConstraints
     * @see javax.sql.RowSetEvent
     * @see javax.sql.RowSetListener
     */
    public CachedRowSet createCopySchema() throws SQLException;

    /**
     * Creates a <code>CachedRowSet</code> object that is a deep copy of
     * this <code>CachedRowSet</code> object's data but is independent of it.
     * In contrast to
     * the <code>RowSet</code> object generated from a <code>createShared</code>
     * method call, updates made to a copy of this <code>CachedRowSet</code> object
     * must not be visible to it. Also, any
     * event listeners that are registered with this
     * <code>CachedRowSet</code> object must not have scope over the new
     * <code>RowSet</code> object. In addition, any constraint restrictions
     * established for this <code>CachedRowSet</code> object must <b>not</b> be maintained
     * in the copy.
     *
     * <p>
     *  创建一个<code> CachedRowSet </code>对象,它是这个<code> CachedRowSet </code>对象数据的深层副本,但与其无关。
     * 与从<code> createShared </code>方法调用生成的<code> RowSet </code>对象相反,对此<code> CachedRowSet </code>对象的副本所做的更新
     * 必须不可见。
     *  创建一个<code> CachedRowSet </code>对象,它是这个<code> CachedRowSet </code>对象数据的深层副本,但与其无关。
     * 此外,向此<code> CachedRowSet </code>对象注册的任何事件侦听器都不能超过新的<code> RowSet </code>对象。
     * 此外,为此<code> CachedRowSet </code>对象建立的任何约束限制必须<b> </b>在副本中维护。
     * 
     * 
     * @return a new <code>CachedRowSet</code> object that is a deep copy
     *     of this <code>CachedRowSet</code> object and is
     *     completely independent of this  <code>CachedRowSet</code> object
     * @throws SQLException if an error occurs in generating the copy of
     *     the of this <code>CachedRowSet</code> object
     * @see #createCopy
     * @see #createShared
     * @see #createCopySchema
     * @see javax.sql.RowSetEvent
     * @see javax.sql.RowSetListener
     */
    public CachedRowSet createCopyNoConstraints() throws SQLException;

    /**
     * Retrieves the first warning reported by calls on this <code>RowSet</code> object.
     * Subsequent warnings on this <code>RowSet</code> object will be chained to the
     * <code>RowSetWarning</code> object that this method returns.
     *
     * The warning chain is automatically cleared each time a new row is read.
     * This method may not be called on a RowSet object that has been closed;
     * doing so will cause a <code>SQLException</code> to be thrown.
     *
     * <p>
     * 检索此<code> RowSet </code>对象上调用报告的第一个警告。
     * 此<> RowSet </code>对象上的后续警告将链接到此方法返回的<code> RowSetWarning </code>对象。
     * 
     *  每次读取新行时,警告链都会自动清除。对于已关闭的RowSet对象,可能不会调用此方法;这样做会导致抛出<code> SQLException </code>。
     * 
     * 
     * @return RowSetWarning the first <code>RowSetWarning</code>
     * object reported or null if there are none
     * @throws SQLException if this method is called on a closed RowSet
     * @see RowSetWarning
     */
    public RowSetWarning getRowSetWarnings() throws SQLException;

    /**
     * Retrieves a <code>boolean</code> indicating whether rows marked
     * for deletion appear in the set of current rows. If <code>true</code> is
     * returned, deleted rows are visible with the current rows. If
     * <code>false</code> is returned, rows are not visible with the set of
     * current rows. The default value is <code>false</code>.
     * <P>
     * Standard rowset implementations may choose to restrict this behavior
     * due to security considerations or to better fit certain deployment
     * scenarios. This is left as implementation defined and does not
     * represent standard behavior.
     * <P>
     * Note: Allowing deleted rows to remain visible complicates the behavior
     * of some standard JDBC <code>RowSet</code> Implementations methods.
     * However, most rowset users can simply ignore this extra detail because
     * only very specialized applications will likely want to take advantage of
     * this feature.
     *
     * <p>
     *  检索<code> boolean </code>,指示标记为删除的行是否显示在当前行集中。如果返回<code> true </code>,则删除的行对当前行可见。
     * 如果返回<code> false </code>,则行不会与当前行集合一起显示。默认值为<code> false </code>。
     * <P>
     *  标准行集实现可以选择由于安全考虑限制此行为或者更好地适合某些部署场景。这留作实现定义,不代表标准行为。
     * <P>
     *  注意：允许删除的行保持可见会使某些标准JDBC <code> RowSet </code>实现方法的行为复杂化。
     * 然而,大多数行集用户可以忽略这个额外的细节,因为只有非常专业的应用程序可能想利用这个功能。
     * 
     * 
     * @return <code>true</code> if deleted rows are visible;
     *         <code>false</code> otherwise
     * @throws SQLException if a rowset implementation is unable to
     * to determine whether rows marked for deletion are visible
     * @see #setShowDeleted
     */
    public boolean getShowDeleted() throws SQLException;

    /**
     * Sets the property <code>showDeleted</code> to the given
     * <code>boolean</code> value, which determines whether
     * rows marked for deletion appear in the set of current rows.
     * If the value is set to <code>true</code>, deleted rows are immediately
     * visible with the set of current rows. If the value is set to
     * <code>false</code>, the deleted rows are set as invisible with the
     * current set of rows.
     * <P>
     * Standard rowset implementations may choose to restrict this behavior
     * due to security considerations or to better fit certain deployment
     * scenarios. This is left as implementations defined and does not
     * represent standard behavior.
     *
     * <p>
     * 将<code> showDeleted </code>属性设置为给定的<code> boolean </code>值,该值确定标记为删除的行是否显示在当前行集中。
     * 如果值设置为<code> true </code>,则删除的行将立即与当前行集合一起显示。如果值设置为<code> false </code>,则删除的行将设置为与当前行不可见。
     * <P>
     *  标准行集实现可以选择由于安全考虑限制此行为或者更好地适合某些部署场景。这留作实现定义,不代表标准行为。
     * 
     * 
     * @param b <code>true</code> if deleted rows should be shown;
     *              <code>false</code> otherwise
     * @exception SQLException if a rowset implementation is unable to
     * to reset whether deleted rows should be visible
     * @see #getShowDeleted
     */
    public void setShowDeleted(boolean b) throws SQLException;

    /**
     * Each <code>CachedRowSet</code> object's <code>SyncProvider</code> contains
     * a <code>Connection</code> object from the <code>ResultSet</code> or JDBC
     * properties passed to it's constructors. This method wraps the
     * <code>Connection</code> commit method to allow flexible
     * auto commit or non auto commit transactional control support.
     * <p>
     * Makes all changes that are performed by the <code>acceptChanges()</code>
     * method since the previous commit/rollback permanent. This method should
     * be used only when auto-commit mode has been disabled.
     *
     * <p>
     *  每个<code> CachedRowSet </code>对象的<code> SyncProvider </code>包含来自<code> ResultSet </code>或传递给它的构造函数的JD
     * BC属性的<code> Connection </code>对象。
     * 此方法包装<code> Connection </code>提交方法以允许灵活的自动提交或非自动提交事务控制支持。
     * <p>
     *  进行自上次提交/回滚后由<code> acceptChanges()</code>方法执行的所有更改。仅当禁用自动提交模式时,才应使用此方法。
     * 
     * 
     * @throws SQLException if a database access error occurs or this
     * Connection object within this <code>CachedRowSet</code> is in auto-commit mode
     * @see java.sql.Connection#setAutoCommit
     */
    public void commit() throws SQLException;

    /**
     * Each <code>CachedRowSet</code> object's <code>SyncProvider</code> contains
     * a <code>Connection</code> object from the original <code>ResultSet</code>
     * or JDBC properties passed to it.
     * <p>
     * Undoes all changes made in the current transaction.  This method
     * should be used only when auto-commit mode has been disabled.
     *
     * <p>
     *  每个<code> CachedRowSet </code>对象的<code> SyncProvider </code>包含来自原始<code> ResultSet </code>或传递给它的JDBC属
     * 性的<code> Connection </code>对象。
     * <p>
     *  撤销当前事务中所做的所有更改。仅当禁用自动提交模式时,才应使用此方法。
     * 
     * 
     * @throws SQLException if a database access error occurs or this Connection
     * object within this <code>CachedRowSet</code> is in auto-commit mode.
     */
    public void rollback() throws SQLException;

    /**
     * Each <code>CachedRowSet</code> object's <code>SyncProvider</code> contains
     * a <code>Connection</code> object from the original <code>ResultSet</code>
     * or JDBC properties passed to it.
     * <p>
     * Undoes all changes made in the current transaction back to the last
     * <code>Savepoint</code> transaction marker. This method should be used only
     * when auto-commit mode has been disabled.
     *
     * <p>
     * 每个<code> CachedRowSet </code>对象的<code> SyncProvider </code>包含来自原始<code> ResultSet </code>或传递给它的JDBC属性
     * 的<code> Connection </code>对象。
     * <p>
     *  将当前事务中所做的所有更改撤销到最后一个<code> Savepoint </code>事务标记。仅当禁用自动提交模式时,才应使用此方法。
     * 
     * 
     * @param s A <code>Savepoint</code> transaction marker
     * @throws SQLException if a database access error occurs or this Connection
     * object within this <code>CachedRowSet</code> is in auto-commit mode.
     */
    public void rollback(Savepoint s) throws SQLException;

    /**
     * Causes the <code>CachedRowSet</code> object's <code>SyncProvider</code>
     * to commit the changes when <code>acceptChanges()</code> is called. If
     * set to false, the changes will <b>not</b> be committed until one of the
     * <code>CachedRowSet</code> interface transaction methods is called.
     *
     * <p>
     *  当调用<code> acceptChanges()</code>时,使<code> CachedRowSet </code>对象的<code> SyncProvider </code>提交更改。
     * 如果设置为false,则在调用<code> CachedRowSet </code>接口事务方法之前,将会更改<b>不</b>。
     * 
     * 
     * @deprecated Because this field is final (it is part of an interface),
     *  its value cannot be changed.
     * @see #commit
     * @see #rollback
     */
    @Deprecated
    public static final boolean COMMIT_ON_ACCEPT_CHANGES = true;

    /**
     * Notifies registered listeners that a RowSet object in the given RowSetEvent
     * object has populated a number of additional rows. The <code>numRows</code> parameter
     * ensures that this event will only be fired every <code>numRow</code>.
     * <p>
     * The source of the event can be retrieved with the method event.getSource.
     *
     * <p>
     *  通知注册的侦听器给定RowSetEvent对象中的RowSet对象已填充了多个其他行。
     *  <code> numRows </code>参数确保此事件只会在每个<code> numRow </code>时触发。
     * <p>
     *  可以使用方法event.getSource检索事件的来源。
     * 
     * 
     * @param event a <code>RowSetEvent</code> object that contains the
     *     <code>RowSet</code> object that is the source of the events
     * @param numRows when populating, the number of rows interval on which the
     *     <code>CachedRowSet</code> populated should fire; the default value
     *     is zero; cannot be less than <code>fetchSize</code> or zero
     * @throws SQLException {@code numRows < 0 or numRows < getFetchSize() }
     */
    public void rowSetPopulated(RowSetEvent event, int numRows) throws SQLException;

    /**
     * Populates this <code>CachedRowSet</code> object with data from
     * the given <code>ResultSet</code> object. While related to the <code>populate(ResultSet)</code>
     * method, an additional parameter is provided to allow starting position within
     * the <code>ResultSet</code> from where to populate the CachedRowSet
     * instance.
     * <P>
     * This method can be used as an alternative to the <code>execute</code> method when an
     * application has a connection to an open <code>ResultSet</code> object.
     * Using the method <code>populate</code> can be more efficient than using
     * the version of the <code>execute</code> method that takes no parameters
     * because it does not open a new connection and re-execute this
     * <code>CachedRowSet</code> object's command. Using the <code>populate</code>
     *  method is more a matter of convenience when compared to using the version
     * of <code>execute</code> that takes a <code>ResultSet</code> object.
     *
     * <p>
     *  使用给定的<code> ResultSet </code>对象中的数据填充此<c> cachedRowSet </code>对象。
     * 虽然与<code> populate(ResultSet)</code>方法相关,但提供了一个附加参数,以允许在<code> ResultSet </code>中的起始位置填充CachedRowSet实
     * 例。
     *  使用给定的<code> ResultSet </code>对象中的数据填充此<c> cachedRowSet </code>对象。
     * <P>
     * 当应用程序具有与打开的<code> ResultSet </code>对象的连接时,此方法可以用作<code> execute </code>方法的替代方法。
     * 使用方法<code> populate </code>比使用没有参数的<code> execute </code>方法的版本更高效,因为它不打开一个新的连接并重新执行这个<code> CachedRow
     * Set </code>对象的命令。
     * 当应用程序具有与打开的<code> ResultSet </code>对象的连接时,此方法可以用作<code> execute </code>方法的替代方法。
     * 与使用采用<code> ResultSet </code>对象的<code> execute </code>版本相比,使用<code> populate </code>方法更为方便。
     * 
     * 
     * @param startRow the position in the <code>ResultSet</code> from where to start
     *                populating the records in this <code>CachedRowSet</code>
     * @param rs the <code>ResultSet</code> object containing the data
     * to be read into this <code>CachedRowSet</code> object
     * @throws SQLException if a null <code>ResultSet</code> object is supplied
     * or this <code>CachedRowSet</code> object cannot
     * retrieve the associated <code>ResultSetMetaData</code> object
     * @see #execute
     * @see #populate(ResultSet)
     * @see java.sql.ResultSet
     * @see java.sql.ResultSetMetaData
    */
    public void populate(ResultSet rs, int startRow) throws SQLException;

    /**
     * Sets the <code>CachedRowSet</code> object's page-size. A <code>CachedRowSet</code>
     * may be configured to populate itself in page-size sized batches of rows. When
     * either <code>populate()</code> or <code>execute()</code> are called, the
     * <code>CachedRowSet</code> fetches an additional page according to the
     * original SQL query used to populate the RowSet.
     *
     * <p>
     *  设置<code> CachedRowSet </code>对象的页面大小。可以将<code> CachedRowSet </code>配置为以页大小批量的行填充自身。
     * 当调用<code> populate()</code>或<code> execute()</code>时,<code> CachedRowSet </code>根据用于填充RowSet的原始SQL查询提
     * 取一个附加页。
     *  设置<code> CachedRowSet </code>对象的页面大小。可以将<code> CachedRowSet </code>配置为以页大小批量的行填充自身。
     * 
     * 
     * @param size the page-size of the <code>CachedRowSet</code>
     * @throws SQLException if an error occurs setting the <code>CachedRowSet</code>
     *      page size or if the page size is less than 0.
     */
    public void setPageSize(int size) throws SQLException;

    /**
     * Returns the page-size for the <code>CachedRowSet</code> object
     *
     * <p>
     *  返回<code> CachedRowSet </code>对象的页面大小
     * 
     * 
     * @return an <code>int</code> page size
     */
    public int getPageSize();

    /**
     * Increments the current page of the <code>CachedRowSet</code>. This causes
     * the <code>CachedRowSet</code> implementation to fetch the next page-size
     * rows and populate the RowSet, if remaining rows remain within scope of the
     * original SQL query used to populated the RowSet.
     *
     * <p>
     *  递增<code> CachedRowSet </code>的当前页。
     * 这导致<code> CachedRowSet </code>实现获取下一页大小的行并填充RowSet,如果剩余的行保持在用于填充RowSet的原始SQL查询的范围内。
     * 
     * 
     * @return true if more pages exist; false if this is the last page
     * @throws SQLException if an error occurs fetching the next page, or if this
     *     method is called prematurely before populate or execute.
     */
    public boolean nextPage() throws SQLException;

    /**
     * Decrements the current page of the <code>CachedRowSet</code>. This causes
     * the <code>CachedRowSet</code> implementation to fetch the previous page-size
     * rows and populate the RowSet. The amount of rows returned in the previous
     * page must always remain within scope of the original SQL query used to
     * populate the RowSet.
     *
     * <p>
     * 递减<code> CachedRowSet </code>的当前页面。这导致<code> CachedRowSet </code>实现获取先前的页大小行并填充RowSet。
     * 上一页中返回的行数必须始终保留在用于填充RowSet的原始SQL查询的范围内。
     * 
     * @return true if the previous page is successfully retrieved; false if this
     *     is the first page.
     * @throws SQLException if an error occurs fetching the previous page, or if
     *     this method is called prematurely before populate or execute.
     */
    public boolean previousPage() throws SQLException;

}
