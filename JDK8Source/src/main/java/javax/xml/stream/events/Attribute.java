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

import javax.xml.namespace.QName;

/**
 * An interface that contains information about an attribute.  Attributes are reported
 * as a set of events accessible from a StartElement.  Other applications may report
 * Attributes as first-order events, for example as the results of an XPath expression.
 *
 * <p>
 *  包含属性信息的接口。属性被报告为可从StartElement访问的一组事件。其他应用程序可能会将属性报告为一阶事件,例如作为XPath表达式的结果。
 * 
 * 
 * @version 1.0
 * @author Copyright (c) 2009 by Oracle Corporation. All Rights Reserved.
 * @see StartElement
 * @since 1.6
 */
public interface Attribute extends XMLEvent {

  /**
   * Returns the QName for this attribute
   * <p>
   *  返回此属性的QName
   * 
   */
  QName getName();

  /**
   * Gets the normalized value of this attribute
   * <p>
   *  获取此属性的规范化值
   * 
   */
  public String getValue();

  /**
   * Gets the type of this attribute, default is
   * the String "CDATA"
   * <p>
   *  获取此属性的类型,默认是字符串"CDATA"
   * 
   * 
   * @return the type as a String, default is "CDATA"
   */
  public String getDTDType();

  /**
   * A flag indicating whether this attribute was actually
   * specified in the start-tag of its element, or was defaulted from the schema.
   * <p>
   *  指示此属性是否实际在其元素的start-tag中指定的标志,或者是来自模式的默认值。
   * 
   * @return returns true if this was specified in the start element
   */
  public boolean isSpecified();

}
