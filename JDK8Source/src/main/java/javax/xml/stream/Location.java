/***** Lobxxx Translate Finished ******/
/*
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

/*
 * Copyright (c) 2009 by Oracle Corporation. All Rights Reserved.
 * <p>
 *  Oracle Corporation的版权所有(c)2009。版权所有。
 * 
 */

package javax.xml.stream;

/**
 * Provides information on the location of an event.
 *
 * All the information provided by a Location is optional.  For example
 * an application may only report line numbers.
 *
 * <p>
 *  提供有关事件位置的信息。
 * 
 *  由位置提供的所有信息是可选的。例如,应用程序只能报告行号。
 * 
 * 
 * @version 1.0
 * @author Copyright (c) 2009 by Oracle Corporation. All Rights Reserved.
 * @since 1.6
 */
public interface Location {
  /**
   * Return the line number where the current event ends,
   * returns -1 if none is available.
   * <p>
   *  返回当前事件结束的行号,如果没有可用则返回-1。
   * 
   * 
   * @return the current line number
   */
  int getLineNumber();

  /**
   * Return the column number where the current event ends,
   * returns -1 if none is available.
   * <p>
   *  返回当前事件结束的列号,如果没有可用则返回-1。
   * 
   * 
   * @return the current column number
   */
  int getColumnNumber();

  /**
   * Return the byte or character offset into the input source this location
   * is pointing to. If the input source is a file or a byte stream then
   * this is the byte offset into that stream, but if the input source is
   * a character media then the offset is the character offset.
   * Returns -1 if there is no offset available.
   * <p>
   *  将字节或字符偏移量返回此位置指向的输入源。如果输入源是文件或字节流,则这是到该流的字节偏移,但是如果输入源是字符媒体,则偏移是字符偏移。如果没有可用的偏移量,则返回-1。
   * 
   * 
   * @return the current offset
   */
  int getCharacterOffset();

  /**
   * Returns the public ID of the XML
   * <p>
   *  返回XML的公共ID
   * 
   * 
   * @return the public ID, or null if not available
   */
  public String getPublicId();

  /**
   * Returns the system ID of the XML
   * <p>
   *  返回XML的系统ID
   * 
   * @return the system ID, or null if not available
   */
  public String getSystemId();
}
