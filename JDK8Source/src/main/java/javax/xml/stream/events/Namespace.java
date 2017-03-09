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
 * An interface that contains information about a namespace.
 * Namespaces are accessed from a StartElement.
 *
 * <p>
 *  包含有关命名空间信息的接口。可以从StartElement访问命名空间。
 * 
 * 
 * @version 1.0
 * @author Copyright (c) 2009 by Oracle Corporation. All Rights Reserved.
 * @see StartElement
 * @since 1.6
 */
public interface Namespace extends Attribute {

  /**
   * Gets the prefix, returns "" if this is a default
   * namespace declaration.
   * <p>
   *  获取前缀,如果这是一个默认的命名空间声明,则返回""。
   * 
   */
  public String getPrefix();

  /**
   * Gets the uri bound to the prefix of this namespace
   * <p>
   *  获取绑定到此命名空间前缀的uri
   * 
   */
  public String getNamespaceURI();

  /**
   * returns true if this attribute declares the default namespace
   * <p>
   *  如果此属性声明默认命名空间,则返回true
   */
  public boolean isDefaultNamespaceDeclaration();
}
