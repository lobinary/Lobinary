/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2005, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.xpath;

import javax.xml.namespace.QName;

/**
 * <p><code>XPathFunctionResolver</code> provides access to the set of user defined <code>XPathFunction</code>s.</p>
 *
 * <p>XPath functions are resolved by name and arity.
 * The resolver is not needed for XPath built-in functions and the resolver
 * <strong><em>cannot</em></strong> be used to override those functions.</p>
 *
 * <p>In particular, the resolver is only called for functions in an another
 * namespace (functions with an explicit prefix). This means that you cannot
 * use the <code>XPathFunctionResolver</code> to implement specifications
 * like <a href="http://www.w3.org/TR/xmldsig-core/">XML-Signature Syntax
 * and Processing</a> which extend the function library of XPath 1.0 in the
 * same namespace. This is a consequence of the design of the resolver.</p>
 *
 * <p>If you wish to implement additional built-in functions, you will have to
 * extend the underlying implementation directly.</p>
 *
 * <p>
 *  <p> <code> XPathFunctionResolver </code>提供对用户定义的<code> XPathFunction </code>集合的访问。</p>
 * 
 *  <p> XPath函数按名称和arity解析。解析器不需要用于XPath内置函数,解析器<strong> <em> </em>不能用于覆盖这些函数。</p>
 * 
 *  <p>特别地,解析器仅在另一个命名空间中调用函数(具有显式前缀的函数)。
 * 这意味着您不能使用<code> XPathFunctionResolver </code>来实施<a href="http://www.w3.org/TR/xmldsig-core/"> XML签名语法
 * 和处理</code> a>它在同一个命名空间中扩展XPath 1.0的函数库。
 *  <p>特别地,解析器仅在另一个命名空间中调用函数(具有显式前缀的函数)。这是解算器的设计的结果。</p>。
 * 
 * 
 * @author  <a href="mailto:Norman.Walsh@Sun.com">Norman Walsh</a>
 * @author  <a href="mailto:Jeff.Suttor@Sun.com">Jeff Suttor</a>
 * @see <a href="http://www.w3.org/TR/xpath#corelib">XML Path Language (XPath) Version 1.0, Core Function Library</a>
 * @since 1.5
 */
public interface XPathFunctionResolver {
  /**
   * <p>Find a function in the set of available functions.</p>
   *
   * <p>If <code>functionName</code> or <code>arity</code> is <code>null</code>, then a <code>NullPointerException</code> is thrown.</p>
   *
   * <p>
   *  <p>如果您希望实现其他内置函数,则必须直接扩展底层实现。</p>
   * 
   * 
   * @param functionName The function name.
   * @param arity The number of arguments that the returned function must accept.
   *
   * @return The function or <code>null</code> if no function named <code>functionName</code> with <code>arity</code> arguments exists.
   *
   * @throws NullPointerException If <code>functionName</code> or <code>arity</code> is <code>null</code>.
   */
  public XPathFunction resolveFunction(QName functionName, int arity);
}
