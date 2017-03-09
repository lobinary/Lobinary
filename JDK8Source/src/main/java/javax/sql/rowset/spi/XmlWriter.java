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
import java.io.Writer;

import javax.sql.RowSetWriter;
import javax.sql.rowset.*;

/**
 * A specialized interface that facilitates an extension of the
 * <code>SyncProvider</code> abstract class for XML orientated
 * synchronization providers.
 * <p>
 * <code>SyncProvider</code>  implementations that supply XML data writer
 * capabilities such as output XML stream capabilities can implement this
 * interface to provide standard <code>XmlWriter</code> objects to
 * <code>WebRowSet</code> implementations.
 * <P>
 * Writing a <code>WebRowSet</code> object includes printing the
 * rowset's data, metadata, and properties, all with the
 * appropriate XML tags.
 * <p>
 *  一个专用接口,用于为面向XML的同步提供程序扩展<code> SyncProvider </code>抽象类。
 * <p>
 *  提供XML数据写入器功能(如输出XML流功能)的<code> SyncProvider </code>实现可以实现此接口,以向<code> WebRowSet </code>实现提供标准<code> 
 * XmlWriter </code>对象。
 * <P>
 *  编写<code> WebRowSet </code>对象包括打印行集的数据,元数据和属性,所有这些都使用适当的XML标记。
 * 
 */
public interface XmlWriter extends RowSetWriter {

  /**
   * Writes the given <code>WebRowSet</code> object to the specified
   * <code>java.io.Writer</code> output stream as an XML document.
   * This document includes the rowset's data, metadata, and properties
   * plus the appropriate XML tags.
   * <P>
   * The <code>caller</code> parameter must be a <code>WebRowSet</code>
   * object whose <code>XmlWriter</code> field contains a reference to
   * this <code>XmlWriter</code> object.
   *
   * <p>
   * 
   * @param caller the <code>WebRowSet</code> instance to be written,
   *        for which this <code>XmlWriter</code> object is the writer
   * @param writer the <code>java.io.Writer</code> object that serves
   *        as the output stream for writing <code>caller</code> as
   *        an XML document
   * @throws SQLException if a database access error occurs or
   *            this <code>XmlWriter</code> object is not the writer
   *            for the given <code>WebRowSet</code> object
   */
  public void writeXML(WebRowSet caller, java.io.Writer writer)
    throws SQLException;



}
