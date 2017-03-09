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
import javax.xml.namespace.NamespaceContext;

import java.util.Map;
import java.util.Iterator;

/**
 * The StartElement interface provides access to information about
 * start elements.  A StartElement is reported for each Start Tag
 * in the document.
 *
 * <p>
 *  StartElement接口提供对start元素信息的访问。将为文档中的每个开始标记报告StartElement。
 * 
 * 
 * @version 1.0
 * @author Copyright (c) 2009 by Oracle Corporation. All Rights Reserved.
 * @since 1.6
 */
public interface StartElement extends XMLEvent {

  /**
   * Get the name of this event
   * <p>
   *  获取此事件的名称
   * 
   * 
   * @return the qualified name of this event
   */
  public QName getName();

  /**
   * Returns an Iterator of non-namespace declared attributes declared on
   * this START_ELEMENT,
   * returns an empty iterator if there are no attributes.  The
   * iterator must contain only implementations of the javax.xml.stream.Attribute
   * interface.   Attributes are fundamentally unordered and may not be reported
   * in any order.
   *
   * <p>
   *  返回在此START_ELEMENT上声明的非命名空间声明属性的迭代器,如果没有属性,则返回空的迭代器。迭代器必须只包含javax.xml.stream.Attribute接口的实现。
   * 属性基本上是无序的,可能不会以任何顺序报告。
   * 
   * 
   * @return a readonly Iterator over Attribute interfaces, or an
   * empty iterator
   */
  public Iterator getAttributes();

  /**
   * Returns an Iterator of namespaces declared on this element.
   * This Iterator does not contain previously declared namespaces
   * unless they appear on the current START_ELEMENT.
   * Therefore this list may contain redeclared namespaces and duplicate namespace
   * declarations. Use the getNamespaceContext() method to get the
   * current context of namespace declarations.
   *
   * <p>The iterator must contain only implementations of the
   * javax.xml.stream.Namespace interface.
   *
   * <p>A Namespace isA Attribute.  One
   * can iterate over a list of namespaces as a list of attributes.
   * However this method returns only the list of namespaces
   * declared on this START_ELEMENT and does not
   * include the attributes declared on this START_ELEMENT.
   *
   * Returns an empty iterator if there are no namespaces.
   *
   * <p>
   *  返回在此元素上声明的命名空间的迭代器。此Iterator不包含以前声明的命名空间,除非它们出现在当前START_ELEMENT。因此,此列表可能包含重新声明的命名空间和重复的命名空间声明。
   * 使用getNamespaceContext()方法获取命名空间声明的当前上下文。
   * 
   *  <p>迭代器必须只包含javax.xml.stream.Namespace接口的实现。
   * 
   *  <p>命名空间是属性。可以将命名空间列表作为属性列表进行迭代。但是,此方法仅返回此START_ELEMENT上声明的命名空间列表,并不包含此START_ELEMENT上声明的属性。
   * 
   *  如果没有命名空间,则返回空的迭代器。
   * 
   * 
   * @return a readonly Iterator over Namespace interfaces, or an
   * empty iterator
   *
   */
  public Iterator getNamespaces();

  /**
   * Returns the attribute referred to by this name
   * <p>
   * 
   * @param name the qname of the desired name
   * @return the attribute corresponding to the name value or null
   */
  public Attribute getAttributeByName(QName name);

  /**
   * Gets a read-only namespace context. If no context is
   * available this method will return an empty namespace context.
   * The NamespaceContext contains information about all namespaces
   * in scope for this StartElement.
   *
   * <p>
   *  返回此名称引用的属性
   * 
   * 
   * @return the current namespace context
   */
  public NamespaceContext getNamespaceContext();

  /**
   * Gets the value that the prefix is bound to in the
   * context of this element.  Returns null if
   * the prefix is not bound in this context
   * <p>
   * 获取只读命名空间上下文。如果没有上下文可用,此方法将返回一个空的命名空间上下文。 NamespaceContext包含此StartElement范围内所有命名空间的信息。
   * 
   * 
   * @param prefix the prefix to lookup
   * @return the uri bound to the prefix or null
   */
  public String getNamespaceURI(String prefix);
}
