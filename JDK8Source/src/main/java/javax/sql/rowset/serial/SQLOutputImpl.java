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

package javax.sql.rowset.serial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Map;
import java.util.Vector;

/**
 * The output stream for writing the attributes of a
 * custom-mapped user-defined type (UDT) back to the database.
 * The driver uses this interface internally, and its
 * methods are never directly invoked by an application programmer.
 * <p>
 * When an application calls the
 * method <code>PreparedStatement.setObject</code>, the driver
 * checks to see whether the value to be written is a UDT with
 * a custom mapping.  If it is, there will be an entry in a
 * type map containing the <code>Class</code> object for the
 * class that implements <code>SQLData</code> for this UDT.
 * If the value to be written is an instance of <code>SQLData</code>,
 * the driver will create an instance of <code>SQLOutputImpl</code>
 * and pass it to the method <code>SQLData.writeSQL</code>.
 * The method <code>writeSQL</code> in turn calls the
 * appropriate <code>SQLOutputImpl.writeXXX</code> methods
 * to write data from the <code>SQLData</code> object to
 * the <code>SQLOutputImpl</code> output stream as the
 * representation of an SQL user-defined type.
 * <p>
 *  用于将自定义映射的用户定义类型(UDT)的属性写回数据库的输出流。驱动程序在内部使用此接口,并且它的方法不会被应用程序员直接调用。
 * <p>
 *  当应用程序调用<code> PreparedStatement.setObject </code>方法时,驱动程序将检查要写入的值是否为具有自定义映射的UDT。
 * 如果是,在包含用于实现这个UDT的<code> SQLData </code>的类的<code> Class </code>对象的类型映射中将有一个条目。
 * 如果要写入的值是<code> SQLData </code>的实例,驱动程序将创建一个<code> SQLOutputImpl </code>的实例,并将其传递给方法<code> SQLData.wri
 * teSQL </code> 。
 * 如果是,在包含用于实现这个UDT的<code> SQLData </code>的类的<code> Class </code>对象的类型映射中将有一个条目。
 * 方法<code> writeSQL </code>依次调用<code> SQLOutputImpl.writeXXX </code>方法将数据从<code> SQLData </code>对象写入<code>
 *  SQLOutputImpl </code>输出流作为SQL用户定义类型的表示。
 * 如果是,在包含用于实现这个UDT的<code> SQLData </code>的类的<code> Class </code>对象的类型映射中将有一个条目。
 * 
 */
public class SQLOutputImpl implements SQLOutput {

    /**
     * A reference to an existing vector that
     * contains the attributes of a <code>Struct</code> object.
     * <p>
     *  对包含<code> Struct </code>对象的属性的现有向量的引用。
     * 
     */
    @SuppressWarnings("rawtypes")
    private Vector attribs;

    /**
     * The type map the driver supplies to a newly created
     * <code>SQLOutputImpl</code> object.  This type map
     * indicates the <code>SQLData</code> class whose
     * <code>writeSQL</code> method will be called.  This
     * method will in turn call the appropriate
     * <code>SQLOutputImpl</code> writer methods.
     * <p>
     * 驱动程序提供给新创建的<code> SQLOutputImpl </code>对象的类型映射。
     * 此类型映射表示<code> SQLData </code>类,其<code> writeSQL </code>方法将被调用。
     * 该方法将调用适当的<code> SQLOutputImpl </code> writer方法。
     * 
     */
    @SuppressWarnings("rawtypes")
    private Map map;

    /**
     * Creates a new <code>SQLOutputImpl</code> object
     * initialized with the given vector of attributes and
     * type map.  The driver will use the type map to determine
     * which <code>SQLData.writeSQL</code> method to invoke.
     * This method will then call the appropriate
     * <code>SQLOutputImpl</code> writer methods in order and
     * thereby write the attributes to the new output stream.
     *
     * <p>
     *  创建一个新的<code> SQLOutputImpl </code>对象,使用给定的属性向量和类型map初始化。
     * 驱动程序将使用类型映射来确定要调用哪个<code> SQLData.writeSQL </code>方法。
     * 然后,该方法将按顺序调用相应的<code> SQLOutputImpl </code>写入器方法,从而将属性写入新的输出流。
     * 
     * 
     * @param attributes a <code>Vector</code> object containing the attributes of
     *        the UDT to be mapped to one or more objects in the Java
     *        programming language
     *
     * @param map a <code>java.util.Map</code> object containing zero or
     *        more entries, with each entry consisting of 1) a <code>String</code>
     *        giving the fully qualified name of a UDT and 2) the
     *        <code>Class</code> object for the <code>SQLData</code> implementation
     *        that defines how the UDT is to be mapped
     * @throws SQLException if the <code>attributes</code> or the <code>map</code>
     *        is a <code>null</code> value
     */
    public SQLOutputImpl(Vector<?> attributes, Map<String,?> map)
        throws SQLException
    {
        if ((attributes == null) || (map == null)) {
            throw new SQLException("Cannot instantiate a SQLOutputImpl " +
            "instance with null parameters");
        }
        this.attribs = attributes;
        this.map = map;
    }

    //================================================================
    // Methods for writing attributes to the stream of SQL data.
    // These methods correspond to the column-accessor methods of
    // java.sql.ResultSet.
    //================================================================

    /**
     * Writes a <code>String</code> in the Java programming language
     * to this <code>SQLOutputImpl</code> object. The driver converts
     * it to an SQL <code>CHAR</code>, <code>VARCHAR</code>, or
     * <code>LONGVARCHAR</code> before returning it to the database.
     *
     * <p>
     *  在Java编程语言中将<code> String </code>写入此<code> SQLOutputImpl </code>对象。
     * 在将其返回到数据库之前,驱动程序将其转换为SQL <code> CHAR </code>,<code> VARCHAR </code>或<code> LONGVARCHAR </code>。
     * 
     * 
     * @param x the value to pass to the database
     * @throws SQLException if the <code>SQLOutputImpl</code> object is in
     *        use by a <code>SQLData</code> object attempting to write the attribute
     *        values of a UDT to the database.
     */
    @SuppressWarnings("unchecked")
    public void writeString(String x) throws SQLException {
        //System.out.println("Adding :"+x);
        attribs.add(x);
    }

    /**
     * Writes a <code>boolean</code> in the Java programming language
     * to this <code>SQLOutputImpl</code> object. The driver converts
     * it to an SQL <code>BIT</code> before returning it to the database.
     *
     * <p>
     *  在Java编程语言中将<code> boolean </code>写入此<code> SQLOutputImpl </code>对象。
     * 驱动程序在将其返回到数据库之前将其转换为SQL <code> BIT </code>。
     * 
     * 
     * @param x the value to pass to the database
     * @throws SQLException if the <code>SQLOutputImpl</code> object is in
     *        use by a <code>SQLData</code> object attempting to write the attribute
     *        values of a UDT to the database.
     */
    @SuppressWarnings("unchecked")
    public void writeBoolean(boolean x) throws SQLException {
        attribs.add(Boolean.valueOf(x));
    }

    /**
     * Writes a <code>byte</code> in the Java programming language
     * to this <code>SQLOutputImpl</code> object. The driver converts
     * it to an SQL <code>BIT</code> before returning it to the database.
     *
     * <p>
     *  在Java编程语言中将<code> byte </code>写入此<code> SQLOutputImpl </code>对象。
     * 驱动程序在将其返回到数据库之前将其转换为SQL <code> BIT </code>。
     * 
     * 
     * @param x the value to pass to the database
     * @throws SQLException if the <code>SQLOutputImpl</code> object is in
     *        use by a <code>SQLData</code> object attempting to write the attribute
     *        values of a UDT to the database.
     */
    @SuppressWarnings("unchecked")
    public void writeByte(byte x) throws SQLException {
        attribs.add(Byte.valueOf(x));
    }

    /**
     * Writes a <code>short</code> in the Java programming language
     * to this <code>SQLOutputImpl</code> object. The driver converts
     * it to an SQL <code>SMALLINT</code> before returning it to the database.
     *
     * <p>
     *  在Java编程语言中将<code> short </code>写入此<code> SQLOutputImpl </code>对象。
     * 驱动程序在将其返回到数据库之前将其转换为SQL <code> SMALLINT </code>。
     * 
     * 
     * @param x the value to pass to the database
     * @throws SQLException if the <code>SQLOutputImpl</code> object is in
     *        use by a <code>SQLData</code> object attempting to write the attribute
     *        values of a UDT to the database.
     */
    @SuppressWarnings("unchecked")
    public void writeShort(short x) throws SQLException {
        attribs.add(Short.valueOf(x));
    }

    /**
     * Writes an <code>int</code> in the Java programming language
     * to this <code>SQLOutputImpl</code> object. The driver converts
     * it to an SQL <code>INTEGER</code> before returning it to the database.
     *
     * <p>
     * 将Java编程语言中的<code> int </code>写入此<code> SQLOutputImpl </code>对象。
     * 驱动程序在将其返回到数据库之前将其转换为SQL <code> INTEGER </code>。
     * 
     * 
     * @param x the value to pass to the database
     * @throws SQLException if the <code>SQLOutputImpl</code> object is in
     *        use by a <code>SQLData</code> object attempting to write the attribute
     *        values of a UDT to the database.
     */
    @SuppressWarnings("unchecked")
    public void writeInt(int x) throws SQLException {
        attribs.add(Integer.valueOf(x));
    }

    /**
     * Writes a <code>long</code> in the Java programming language
     * to this <code>SQLOutputImpl</code> object. The driver converts
     * it to an SQL <code>BIGINT</code> before returning it to the database.
     *
     * <p>
     *  在Java编程语言中将<code> long </code>写入此<code> SQLOutputImpl </code>对象。
     * 驱动程序在将其返回到数据库之前将其转换为SQL <code> BIGINT </code>。
     * 
     * 
     * @param x the value to pass to the database
     * @throws SQLException if the <code>SQLOutputImpl</code> object is in
     *        use by a <code>SQLData</code> object attempting to write the attribute
     *        values of a UDT to the database.
     */
    @SuppressWarnings("unchecked")
    public void writeLong(long x) throws SQLException {
        attribs.add(Long.valueOf(x));
    }

    /**
     * Writes a <code>float</code> in the Java programming language
     * to this <code>SQLOutputImpl</code> object. The driver converts
     * it to an SQL <code>REAL</code> before returning it to the database.
     *
     * <p>
     *  在Java编程语言中将<code> float </code>写入此<code> SQLOutputImpl </code>对象。
     * 驱动程序在将其返回到数据库之前将其转换为SQL <code> REAL </code>。
     * 
     * 
     * @param x the value to pass to the database
     * @throws SQLException if the <code>SQLOutputImpl</code> object is in
     *        use by a <code>SQLData</code> object attempting to write the attribute
     *        values of a UDT to the database.
     */
    @SuppressWarnings("unchecked")
    public void writeFloat(float x) throws SQLException {
        attribs.add(Float.valueOf(x));
    }

    /**
     * Writes a <code>double</code> in the Java programming language
     * to this <code>SQLOutputImpl</code> object. The driver converts
     * it to an SQL <code>DOUBLE</code> before returning it to the database.
     *
     * <p>
     *  在Java编程语言中将<code> double </code>写入此<code> SQLOutputImpl </code>对象。
     * 驱动程序在将其返回到数据库之前将其转换为SQL <code> DOUBLE </code>。
     * 
     * 
     * @param x the value to pass to the database
     * @throws SQLException if the <code>SQLOutputImpl</code> object is in
     *        use by a <code>SQLData</code> object attempting to write the attribute
     *        values of a UDT to the database.
     */
    @SuppressWarnings("unchecked")
    public void writeDouble(double x) throws SQLException{
        attribs.add(Double.valueOf(x));
    }

    /**
     * Writes a <code>java.math.BigDecimal</code> object in the Java programming
     * language to this <code>SQLOutputImpl</code> object. The driver converts
     * it to an SQL <code>NUMERIC</code> before returning it to the database.
     *
     * <p>
     *  在Java编程语言中将<code> java.math.BigDecimal </code>对象写入此<code> SQLOutputImpl </code>对象。
     * 驱动程序在将其返回到数据库之前将其转换为SQL <code> NUMERIC </code>。
     * 
     * 
     * @param x the value to pass to the database
     * @throws SQLException if the <code>SQLOutputImpl</code> object is in
     *        use by a <code>SQLData</code> object attempting to write the attribute
     *        values of a UDT to the database.
     */
    @SuppressWarnings("unchecked")
    public void writeBigDecimal(java.math.BigDecimal x) throws SQLException{
        attribs.add(x);
    }

    /**
     * Writes an array of <code>bytes</code> in the Java programming language
     * to this <code>SQLOutputImpl</code> object. The driver converts
     * it to an SQL <code>VARBINARY</code> or <code>LONGVARBINARY</code>
     * before returning it to the database.
     *
     * <p>
     *  将Java编程语言中的<code>字节</code>数组写入此<code> SQLOutputImpl </code>对象。
     * 在将其返回到数据库之前,驱动程序将其转换为SQL <code> VARBINARY </code>或<code> LONGVARBINARY </code>。
     * 
     * 
     * @param x the value to pass to the database
     * @throws SQLException if the <code>SQLOutputImpl</code> object is in
     *        use by a <code>SQLData</code> object attempting to write the attribute
     *        values of a UDT to the database.
     */
    @SuppressWarnings("unchecked")
    public void writeBytes(byte[] x) throws SQLException {
        attribs.add(x);
    }

    /**
     * Writes a <code>java.sql.Date</code> object in the Java programming
     * language to this <code>SQLOutputImpl</code> object. The driver converts
     * it to an SQL <code>DATE</code> before returning it to the database.
     *
     * <p>
     *  在Java编程语言中将<code> java.sql.Date </code>对象写入此<code> SQLOutputImpl </code>对象。
     * 驱动程序在将其返回到数据库之前将其转换为SQL <code> DATE </code>。
     * 
     * 
     * @param x the value to pass to the database
     * @throws SQLException if the <code>SQLOutputImpl</code> object is in
     *        use by a <code>SQLData</code> object attempting to write the attribute
     *        values of a UDT to the database.
     */
    @SuppressWarnings("unchecked")
    public void writeDate(java.sql.Date x) throws SQLException {
        attribs.add(x);
    }

    /**
     * Writes a <code>java.sql.Time</code> object in the Java programming
     * language to this <code>SQLOutputImpl</code> object. The driver converts
     * it to an SQL <code>TIME</code> before returning it to the database.
     *
     * <p>
     * 在Java编程语言中将<code> java.sql.Time </code>对象写入此<code> SQLOutputImpl </code>对象。
     * 驱动程序在将其返回到数据库之前将其转换为SQL <code> TIME </code>。
     * 
     * 
     * @param x the value to pass to the database
     * @throws SQLException if the <code>SQLOutputImpl</code> object is in
     *        use by a <code>SQLData</code> object attempting to write the attribute
     *        values of a UDT to the database.
     */
    @SuppressWarnings("unchecked")
    public void writeTime(java.sql.Time x) throws SQLException {
        attribs.add(x);
    }

    /**
     * Writes a <code>java.sql.Timestamp</code> object in the Java programming
     * language to this <code>SQLOutputImpl</code> object. The driver converts
     * it to an SQL <code>TIMESTAMP</code> before returning it to the database.
     *
     * <p>
     *  在Java编程语言中将<code> java.sql.Timestamp </code>对象写入此<code> SQLOutputImpl </code>对象。
     * 驱动程序在将其返回到数据库之前将其转换为SQL <code> TIMESTAMP </code>。
     * 
     * 
     * @param x the value to pass to the database
     * @throws SQLException if the <code>SQLOutputImpl</code> object is in
     *        use by a <code>SQLData</code> object attempting to write the attribute
     *        values of a UDT to the database.
     */
    @SuppressWarnings("unchecked")
    public void writeTimestamp(java.sql.Timestamp x) throws SQLException {
        attribs.add(x);
    }

    /**
     * Writes a stream of Unicode characters to this
     * <code>SQLOutputImpl</code> object. The driver will do any necessary
     * conversion from Unicode to the database <code>CHAR</code> format.
     *
     * <p>
     *  将Unicode字符流写入此<code> SQLOutputImpl </code>对象。驱动程序将执行从Unicode到数据库<code> CHAR </code>格式的任何必要的转换。
     * 
     * 
     * @param x the value to pass to the database
     * @throws SQLException if the <code>SQLOutputImpl</code> object is in
     *        use by a <code>SQLData</code> object attempting to write the attribute
     *        values of a UDT to the database.
     */
    @SuppressWarnings("unchecked")
    public void writeCharacterStream(java.io.Reader x) throws SQLException {
         BufferedReader bufReader = new BufferedReader(x);
         try {
             int i;
             while( (i = bufReader.read()) != -1 ) {
                char ch = (char)i;
                StringBuffer strBuf = new StringBuffer();
                strBuf.append(ch);

                String str = new String(strBuf);
                String strLine = bufReader.readLine();

                writeString(str.concat(strLine));
             }
         } catch(IOException ioe) {

         }
    }

    /**
     * Writes a stream of ASCII characters to this
     * <code>SQLOutputImpl</code> object. The driver will do any necessary
     * conversion from ASCII to the database <code>CHAR</code> format.
     *
     * <p>
     *  将ASCII字符流写入此<code> SQLOutputImpl </code>对象。驱动程序将执行从ASCII到数据库<code> CHAR </code>格式的必要转换。
     * 
     * 
     * @param x the value to pass to the database
     * @throws SQLException if the <code>SQLOutputImpl</code> object is in
     *        use by a <code>SQLData</code> object attempting to write the attribute
     *        values of a UDT to the database.
     */
    @SuppressWarnings("unchecked")
    public void writeAsciiStream(java.io.InputStream x) throws SQLException {
         BufferedReader bufReader = new BufferedReader(new InputStreamReader(x));
         try {
               int i;
               while( (i=bufReader.read()) != -1 ) {
                char ch = (char)i;

                StringBuffer strBuf = new StringBuffer();
                strBuf.append(ch);

                String str = new String(strBuf);
                String strLine = bufReader.readLine();

                writeString(str.concat(strLine));
            }
          }catch(IOException ioe) {
            throw new SQLException(ioe.getMessage());
        }
    }

    /**
     * Writes a stream of uninterpreted bytes to this <code>SQLOutputImpl</code>
     * object.
     *
     * <p>
     *  将未解释字节流写入此<code> SQLOutputImpl </code>对象。
     * 
     * 
     * @param x the value to pass to the database
     * @throws SQLException if the <code>SQLOutputImpl</code> object is in
     *        use by a <code>SQLData</code> object attempting to write the attribute
     *        values of a UDT to the database.
     */
    @SuppressWarnings("unchecked")
    public void writeBinaryStream(java.io.InputStream x) throws SQLException {
         BufferedReader bufReader = new BufferedReader(new InputStreamReader(x));
         try {
               int i;
             while( (i=bufReader.read()) != -1 ) {
                char ch = (char)i;

                StringBuffer strBuf = new StringBuffer();
                strBuf.append(ch);

                String str = new String(strBuf);
                String strLine = bufReader.readLine();

                writeString(str.concat(strLine));
             }
        } catch(IOException ioe) {
            throw new SQLException(ioe.getMessage());
        }
    }

    //================================================================
    // Methods for writing items of SQL user-defined types to the stream.
    // These methods pass objects to the database as values of SQL
    // Structured Types, Distinct Types, Constructed Types, and Locator
    // Types.  They decompose the Java object(s) and write leaf data
    // items using the methods above.
    //================================================================

    /**
     * Writes to the stream the data contained in the given
     * <code>SQLData</code> object.
     * When the <code>SQLData</code> object is <code>null</code>, this
     * method writes an SQL <code>NULL</code> to the stream.
     * Otherwise, it calls the <code>SQLData.writeSQL</code>
     * method of the given object, which
     * writes the object's attributes to the stream.
     * <P>
     * The implementation of the method <code>SQLData.writeSQ</code>
     * calls the appropriate <code>SQLOutputImpl.writeXXX</code> method(s)
     * for writing each of the object's attributes in order.
     * The attributes must be read from an <code>SQLInput</code>
     * input stream and written to an <code>SQLOutputImpl</code>
     * output stream in the same order in which they were
     * listed in the SQL definition of the user-defined type.
     *
     * <p>
     *  将给定的<code> SQLData </code>对象中包含的数据写入流。
     * 当<code> SQLData </code>对象是<code> null </code>时,此方法将SQL <code> NULL </code>写入流。
     * 否则,它调用给定对象的<code> SQLData.writeSQL </code>方法,它将对象的属性写入流。
     * <P>
     * 方法<code> SQLData.writeSQ </code>的实现调用适当的<code> SQLOutputImpl.writeXXX </code>方法,按顺序写入对象的每个属性。
     * 必须从<code> SQLInput </code>输入流读取属性,并以与在用户定义类型的SQL定义中列出的顺序相同的顺序写入到<code> SQLOutputImpl </code>输出流。
     * 
     * 
     * @param x the object representing data of an SQL structured or
     *          distinct type
     * @throws SQLException if the <code>SQLOutputImpl</code> object is in
     *        use by a <code>SQLData</code> object attempting to write the attribute
     *        values of a UDT to the database.
     */
    @SuppressWarnings("unchecked")
    public void writeObject(SQLData x) throws SQLException {

        /*
         * Except for the types that are passed as objects
         * this seems to be the only way for an object to
         * get a null value for a field in a structure.
         *
         * Note: this means that the class defining SQLData
         * will need to track if a field is SQL null for itself
         * <p>
         *  除了作为对象传递的类型,这似乎是对象为结构中的字段获取空值的唯一方法。
         * 
         *  注意：这意味着定义SQLData的类将需要跟踪一个字段是否为SQL null
         * 
         */
        if (x == null) {
            attribs.add(null);
        } else {
            /*
             * We have to write out a SerialStruct that contains
             * the name of this class otherwise we don't know
             * what to re-instantiate during readSQL()
             * <p>
             *  我们必须写出一个包含此类的名称的SerialStruct,否则我们不知道在readSQL()中重新实例化什么,
             * 
             */
            attribs.add(new SerialStruct(x, map));
        }
    }

    /**
     * Writes a <code>Ref</code> object in the Java programming language
     * to this <code>SQLOutputImpl</code> object.  The driver converts
     * it to a serializable <code>SerialRef</code> SQL <code>REF</code> value
     * before returning it to the database.
     *
     * <p>
     *  在Java编程语言中将<code> Ref </code>对象写入此<code> SQLOutputImpl </code>对象。
     * 驱动程序在将其返回到数据库之前将其转换为可序列化的<code> SerialRef </code> SQL <code> REF </code>值。
     * 
     * 
     * @param x an object representing an SQL <code>REF</code> value
     * @throws SQLException if the <code>SQLOutputImpl</code> object is in
     *        use by a <code>SQLData</code> object attempting to write the attribute
     *        values of a UDT to the database.
     */
    @SuppressWarnings("unchecked")
    public void writeRef(Ref x) throws SQLException {
        if (x == null) {
            attribs.add(null);
        } else {
            attribs.add(new SerialRef(x));
        }
    }

    /**
     * Writes a <code>Blob</code> object in the Java programming language
     * to this <code>SQLOutputImpl</code> object.  The driver converts
     * it to a serializable <code>SerialBlob</code> SQL <code>BLOB</code> value
     * before returning it to the database.
     *
     * <p>
     *  在Java编程语言中将<code> Blob </code>对象写入此<code> SQLOutputImpl </code>对象。
     * 驱动程序在将其返回到数据库之前将其转换为可序列化的<code> SerialBlob </code> SQL <code> BLOB </code>值。
     * 
     * 
     * @param x an object representing an SQL <code>BLOB</code> value
     * @throws SQLException if the <code>SQLOutputImpl</code> object is in
     *        use by a <code>SQLData</code> object attempting to write the attribute
     *        values of a UDT to the database.
     */
    @SuppressWarnings("unchecked")
    public void writeBlob(Blob x) throws SQLException {
        if (x == null) {
            attribs.add(null);
        } else {
            attribs.add(new SerialBlob(x));
        }
    }

    /**
     * Writes a <code>Clob</code> object in the Java programming language
     * to this <code>SQLOutputImpl</code> object.  The driver converts
     * it to a serializable <code>SerialClob</code> SQL <code>CLOB</code> value
     * before returning it to the database.
     *
     * <p>
     * 在Java编程语言中将<code> Clob </code>对象写入此<code> SQLOutputImpl </code>对象。
     * 在将其返回到数据库之前,驱动程序将其转换为可序列化的<code> SerialClob </code> SQL <code> CLOB </code>值。
     * 
     * 
     * @param x an object representing an SQL <code>CLOB</code> value
     * @throws SQLException if the <code>SQLOutputImpl</code> object is in
     *        use by a <code>SQLData</code> object attempting to write the attribute
     *        values of a UDT to the database.
     */
    @SuppressWarnings("unchecked")
    public void writeClob(Clob x) throws SQLException {
        if (x == null) {
            attribs.add(null);
        } else {
            attribs.add(new SerialClob(x));
        }
    }

    /**
     * Writes a <code>Struct</code> object in the Java
     * programming language to this <code>SQLOutputImpl</code>
     * object. The driver converts this value to an SQL structured type
     * before returning it to the database.
     * <P>
     * This method should be used when an SQL structured type has been
     * mapped to a <code>Struct</code> object in the Java programming
     * language (the standard mapping).  The method
     * <code>writeObject</code> should be used if an SQL structured type
     * has been custom mapped to a class in the Java programming language.
     *
     * <p>
     *  在Java编程语言中将<code> Struct </code>对象写入此<code> SQLOutputImpl </code>对象。驱动程序在将该值返回到数据库之前将其转换为SQL结构化类型。
     * <P>
     *  当SQL结构化类型已经映射到Java编程语言(标准映射)中的<code> Struct </code>对象时,应使用此方法。
     * 如果SQL结构化类型已定制映射到Java编程语言中的类,则应使用方法<code> writeObject </code>。
     * 
     * 
     * @param x an object representing the attributes of an SQL structured type
     * @throws SQLException if the <code>SQLOutputImpl</code> object is in
     *        use by a <code>SQLData</code> object attempting to write the attribute
     *        values of a UDT to the database.
     */
    @SuppressWarnings("unchecked")
    public void writeStruct(Struct x) throws SQLException {
        SerialStruct s = new SerialStruct(x,map);;
        attribs.add(s);
    }

    /**
     * Writes an <code>Array</code> object in the Java
     * programming language to this <code>SQLOutputImpl</code>
     * object. The driver converts this value to a serializable
     * <code>SerialArray</code> SQL <code>ARRAY</code>
     * value before returning it to the database.
     *
     * <p>
     *  在Java编程语言中将<code> Array </code>对象写入此<code> SQLOutputImpl </code>对象。
     * 在将其返回到数据库之前,驱动程序将此值转换为可序列化的<code> SerialArray </code> SQL <code> ARRAY </code>值。
     * 
     * 
     * @param x an object representing an SQL <code>ARRAY</code> value
     * @throws SQLException if the <code>SQLOutputImpl</code> object is in
     *        use by a <code>SQLData</code> object attempting to write the attribute
     *        values of a UDT to the database.
     */
    @SuppressWarnings("unchecked")
    public void writeArray(Array x) throws SQLException {
        if (x == null) {
            attribs.add(null);
        } else {
            attribs.add(new SerialArray(x, map));
        }
    }

    /**
     * Writes an <code>java.sql.Type.DATALINK</code> object in the Java
     * programming language to this <code>SQLOutputImpl</code> object. The
     * driver converts this value to a serializable <code>SerialDatalink</code>
     * SQL <code>DATALINK</code> value before return it to the database.
     *
     * <p>
     *  在Java编程语言中将<code> java.sql.Type.DATALINK </code>对象写入此<code> SQLOutputImpl </code>对象。
     * 驱动程序将此值转换为可序列化的<code> SerialDatalink </code> SQL <code> DATALINK </code>值,然后将其返回到数据库。
     * 
     * 
     * @param url an object representing a SQL <code>DATALINK</code> value
     * @throws SQLException if the <code>SQLOutputImpl</code> object is in
     *        use by a <code>SQLData</code> object attempting to write the attribute
     *        values of a UDT to the database.
     */
    @SuppressWarnings("unchecked")
    public void writeURL(java.net.URL url) throws SQLException {
        if (url == null) {
            attribs.add(null);
        } else {
            attribs.add(new SerialDatalink(url));
        }
    }


  /**
   * Writes the next attribute to the stream as a <code>String</code>
   * in the Java programming language. The driver converts this to a
   * SQL <code>NCHAR</code> or
   * <code>NVARCHAR</code> or <code>LONGNVARCHAR</code> value
   * (depending on the argument's
   * size relative to the driver's limits on <code>NVARCHAR</code> values)
   * when it sends it to the stream.
   *
   * <p>
   * 将下一个属性作为Java编程语言中的<code> String </code>写入流。
   * 驱动程序将此转换为SQL <code> NCHAR </code>或<code> NVARCHAR </code>或<code> LONGNVARCHAR </code>值(取决于参数的大小相对于驱动程
   * 序的限制< </code>值),当它发送到流。
   * 将下一个属性作为Java编程语言中的<code> String </code>写入流。
   * 
   * 
   * @param x the value to pass to the database
   * @exception SQLException if a database access error occurs
   * @since 1.6
   */
   @SuppressWarnings("unchecked")
   public void writeNString(String x) throws SQLException {
       attribs.add(x);
   }

  /**
   * Writes an SQL <code>NCLOB</code> value to the stream.
   *
   * <p>
   *  将SQL <code> NCLOB </code>值写入流。
   * 
   * 
   * @param x a <code>NClob</code> object representing data of an SQL
   * <code>NCLOB</code> value
   *
   * @exception SQLException if a database access error occurs
   * @since 1.6
   */
   @SuppressWarnings("unchecked")
   public void writeNClob(NClob x) throws SQLException {
           attribs.add(x);
   }


  /**
   * Writes an SQL <code>ROWID</code> value to the stream.
   *
   * <p>
   *  将SQL <code> ROWID </code>值写入流。
   * 
   * 
   * @param x a <code>RowId</code> object representing data of an SQL
   * <code>ROWID</code> value
   *
   * @exception SQLException if a database access error occurs
   * @since 1.6
   */
   @SuppressWarnings("unchecked")
   public void writeRowId(RowId x) throws SQLException {
        attribs.add(x);
   }


  /**
   * Writes an SQL <code>XML</code> value to the stream.
   *
   * <p>
   *  将SQL <code> XML </code>值写入流。
   * 
   * @param x a <code>SQLXML</code> object representing data of an SQL
   * <code>XML</code> value
   *
   * @exception SQLException if a database access error occurs
   * @since 1.6
   */
   @SuppressWarnings("unchecked")
   public void writeSQLXML(SQLXML x) throws SQLException {
        attribs.add(x);
    }

}
