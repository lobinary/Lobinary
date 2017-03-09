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

import java.sql.*;
import java.io.*;
import java.net.URL;


/**
 * A serialized mapping in the Java programming language of an SQL
 * <code>DATALINK</code> value. A <code>DATALINK</code> value
 * references a file outside of the underlying data source that the
 * data source manages.
 * <P>
 * <code>RowSet</code> implementations can use the method <code>RowSet.getURL</code>
 * to retrieve a <code>java.net.URL</code> object, which can be used
 * to manipulate the external data.
 * <pre>
 *      java.net.URL url = rowset.getURL(1);
 * </pre>
 *
 * <h3> Thread safety </h3>
 *
 * A SerialDatalink is not safe for use by multiple concurrent threads.  If a
 * SerialDatalink is to be used by more than one thread then access to the
 * SerialDatalink should be controlled by appropriate synchronization.
 * <p>
 *  值为Java <code> DATALINK </code>值的Java编程语言中的序列化映射。 <code> DATALINK </code>值引用数据源管理的基础数据源之外的文件。
 * <P>
 *  <code> RowSet </code>实现可以使用<code> RowSet.getURL </code>方法来检索一个<code> java.net.URL </code>对象,它可以用来处理外
 * 部数据。
 * <pre>
 *  java.net.URL url = rowset.getURL(1);
 * </pre>
 * 
 *  <h3>线程安全</h3>
 * 
 *  SerialDatalink不能安全地用于多个并发线程。如果一个SerialDatalink由多个线程使用,则应通过适当的同步来控制对SerialDatalink的访问。
 * 
 */
public class SerialDatalink implements Serializable, Cloneable {

    /**
     * The extracted URL field retrieved from the DATALINK field.
     * <p>
     *  从DATALINK字段检索的提取的URL字段。
     * 
     * 
     * @serial
     */
    private URL url;

    /**
     * The SQL type of the elements in this <code>SerialDatalink</code>
     * object.  The type is expressed as one of the contants from the
     * class <code>java.sql.Types</code>.
     * <p>
     *  此<> SerialDatalink </code>对象中的元素的SQL类型。类型表示为<code> java.sql.Types </code>类中的一个属性。
     * 
     * 
     * @serial
     */
    private int baseType;

    /**
     * The type name used by the DBMS for the elements in the SQL
     * <code>DATALINK</code> value that this SerialDatalink object
     * represents.
     * <p>
     *  DBMS用于此SerialDatalink对象表示的SQL <code> DATALINK </code>值中的元素的类型名称。
     * 
     * 
     * @serial
     */
    private String baseTypeName;

    /**
      * Constructs a new <code>SerialDatalink</code> object from the given
      * <code>java.net.URL</code> object.
      * <P>
      * <p>
      *  从给定的<code> java.net.URL </code>对象构造一个新的<code> SerialDatalink </code>对象。
      * <P>
      * 
      * @param url the {@code URL} to create the {@code SerialDataLink} from
      * @throws SerialException if url parameter is a null
      */
    public SerialDatalink(URL url) throws SerialException {
        if (url == null) {
            throw new SerialException("Cannot serialize empty URL instance");
        }
        this.url = url;
    }

    /**
     * Returns a new URL that is a copy of this <code>SerialDatalink</code>
     * object.
     *
     * <p>
     *  返回一个新的URL,它是这个<code> SerialDatalink </code>对象的副本。
     * 
     * 
     * @return a copy of this <code>SerialDatalink</code> object as a
     * <code>URL</code> object in the Java programming language.
     * @throws SerialException if the <code>URL</code> object cannot be de-serialized
     */
    public URL getDatalink() throws SerialException {

        URL aURL = null;

        try {
            aURL = new URL((this.url).toString());
        } catch (java.net.MalformedURLException e) {
            throw new SerialException("MalformedURLException: " + e.getMessage());
        }
        return aURL;
    }

    /**
     * Compares this {@code SerialDatalink} to the specified object.
     * The result is {@code true} if and only if the argument is not
     * {@code null} and is a {@code SerialDatalink} object whose URL is
     * identical to this object's URL
     *
     * <p>
     * 将此{@code SerialDatalink}与指定的对象进行比较。
     * 如果且仅当参数不是{@code null}并且是与此对象的网址相同的{@code SerialDatalink}对象时,结果是{@code true}。
     * 
     * 
     * @param  obj The object to compare this {@code SerialDatalink} against
     *
     * @return  {@code true} if the given object represents a {@code SerialDatalink}
     *          equivalent to this SerialDatalink, {@code false} otherwise
     *
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SerialDatalink) {
            SerialDatalink sdl = (SerialDatalink) obj;
            return url.equals(sdl.url);
        }
        return false;
    }

    /**
     * Returns a hash code for this {@code SerialDatalink}. The hash code for a
     * {@code SerialDatalink} object is taken as the hash code of
     * the {@code URL} it stores
     *
     * <p>
     *  返回此{@code SerialDatalink}的哈希代码。 {@code SerialDatalink}对象的哈希码被作为其存储的{@code URL}的哈希码
     * 
     * 
     * @return  a hash code value for this object.
     */
    public int hashCode() {
        return 31 + url.hashCode();
    }

    /**
     * Returns a clone of this {@code SerialDatalink}.
     *
     * <p>
     *  返回此{@code SerialDatalink}的克隆。
     * 
     * 
     * @return  a clone of this SerialDatalink
     */
    public Object clone() {
        try {
            SerialDatalink sdl = (SerialDatalink) super.clone();
            return sdl;
        } catch (CloneNotSupportedException ex) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError();
        }
    }

    /**
     * readObject and writeObject are called to restore the state
     * of the {@code SerialDatalink}
     * from a stream. Note: we leverage the default Serialized form
     * <p>
     *  readObject和writeObject被调用以从流中恢复{@code SerialDatalink}的状态。注意：我们利用默认的序列化形式
     * 
     */

    /**
     * The identifier that assists in the serialization of this
     *  {@code SerialDatalink} object.
     * <p>
     *  有助于序列化此{@code SerialDatalink}对象的标识符。
     */
    static final long serialVersionUID = 2826907821828733626L;
}
