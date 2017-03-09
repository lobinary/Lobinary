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

package javax.xml.stream.events;
/**
 * An interface for the start document event
 *
 * <p>
 *  用于启动文档事件的接口
 * 
 * 
 * @version 1.0
 * @author Copyright (c) 2009 by Oracle Corporation. All Rights Reserved.
 * @since 1.6
 */
public interface StartDocument extends XMLEvent {

  /**
   * Returns the system ID of the XML data
   * <p>
   *  返回XML数据的系统ID
   * 
   * 
   * @return the system ID, defaults to ""
   */
  public String getSystemId();

  /**
   * Returns the encoding style of the XML data
   * <p>
   *  返回XML数据的编码样式
   * 
   * 
   * @return the character encoding, defaults to "UTF-8"
   */
  public String getCharacterEncodingScheme();

  /**
   * Returns true if CharacterEncodingScheme was set in
   * the encoding declaration of the document
   * <p>
   *  如果在文档的编码声明中设置了CharacterEncodingScheme,则返回true
   * 
   */
  public boolean encodingSet();

  /**
   * Returns if this XML is standalone
   * <p>
   *  如果此XML是独立的,则返回
   * 
   * 
   * @return the standalone state of XML, defaults to "no"
   */
  public boolean isStandalone();

  /**
   * Returns true if the standalone attribute was set in
   * the encoding declaration of the document.
   * <p>
   *  如果在文档的编码声明中设置了独立属性,则返回true。
   * 
   */
  public boolean standaloneSet();

  /**
   * Returns the version of XML of this XML stream
   * <p>
   *  返回此XML流的XML版本
   * 
   * @return the version of XML, defaults to "1.0"
   */
  public String getVersion();
}
