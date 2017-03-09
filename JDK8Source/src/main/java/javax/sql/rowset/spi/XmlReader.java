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

package javax.sql.rowset.spi;

import java.sql.SQLException;
import java.io.Reader;

import javax.sql.RowSetReader;
import javax.sql.rowset.*;

/**
 * A specialized interface that facilitates an extension of the
 * <code>SyncProvider</code> abstract class for XML orientated
 * synchronization providers.
 * <P>
 * <code>SyncProvider</code>  implementations that supply XML data reader
 * capabilities such as output XML stream capabilities can implement this
 * interface to provide standard <code>XmlReader</code> objects to
 * <code>WebRowSet</code> implementations.
 * <p>
 * An <code>XmlReader</code> object is registered as the
 * XML reader for a <code>WebRowSet</code> by being assigned to the
 * rowset's <code>xmlReader</code> field. When the <code>WebRowSet</code>
 * object's <code>readXml</code> method is invoked, it in turn invokes
 * its XML reader's <code>readXML</code> method.
 * <p>
 *  一个专用接口,用于为面向XML的同步提供程序扩展<code> SyncProvider </code>抽象类。
 * <P>
 *  提供诸如输出XML流能力的XML数据读取器能力的<code> SyncProvider </code>实现可以实现该接口,以向<code> WebRowSet </code>实现提供标准<code> 
 * XmlReader </code>对象。
 * <p>
 *  通过分配给行集的<code> xmlReader </code>字段,将<code> XmlReader </code>对象注册为<code> WebRowSet </code>的XML读取器。
 * 当调用<code> WebRowSet </code>对象的<code> readXml </code>方法时,它反过来调用其XML读取器的<code> readXML </code>方法。
 */
public interface XmlReader extends RowSetReader {

  /**
   * Reads and parses the given <code>WebRowSet</code> object from the given
   * input stream in XML format. The <code>xmlReader</code> field of the
   * given <code>WebRowSet</code> object must contain this
   * <code>XmlReader</code> object.
   * <P>
   * If a parsing error occurs, the exception that is thrown will
   * include information about the location of the error in the
   * original XML document.
   *
   * <p>
   * 
   * 
   * @param caller the <code>WebRowSet</code> object to be parsed, whose
   *        <code>xmlReader</code> field must contain a reference to
   *        this <code>XmlReader</code> object
   * @param reader the <code>java.io.Reader</code> object from which
   *        <code>caller</code> will be read
   * @throws SQLException if a database access error occurs or
   *            this <code>XmlReader</code> object is not the reader
   *            for the given rowset
   */
  public void readXML(WebRowSet caller, java.io.Reader reader)
    throws SQLException;

}
