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
 * <p><code>XPathVariableResolver</code> provides access to the set of user defined XPath variables.</p>
 *
 * <p>The <code>XPathVariableResolver</code> and the XPath evaluator must adhere to a contract that
 * cannot be directly enforced by the API.  Although variables may be mutable,
 * that is, an application may wish to evaluate the same XPath expression more
 * than once with different variable values, in the course of evaluating any
 * single XPath expression, a variable's value <strong><em>must</em></strong>
 * not change.</p>
 *
 * <p>
 *  <p> <code> XPathVariableResolver </code>提供对一组用户定义的XPath变量的访问。</p>
 * 
 *  <p> <code> XPathVariableResolver </code>和XPath评估程序必须遵守不能由API直接实施的合约。
 * 虽然变量可以是可变的,也就是说,应用程序可能希望使用不同的变量值多次评估同一个XPath表达式,但在评估任何单个XPath表达式的过程中,变量的值<strong>必须</em > </strong>不变
 * 。
 *  <p> <code> XPathVariableResolver </code>和XPath评估程序必须遵守不能由API直接实施的合约。</p>。
 * 
 * @author  <a href="mailto:Norman.Walsh@Sun.com">Norman Walsh</a>
 * @author  <a href="mailto:Jeff.Suttor@Sun.com">Jeff Suttor</a>
 * @since 1.5
 */
public interface XPathVariableResolver {
  /**
   * <p>Find a variable in the set of available variables.</p>
   *
   * <p>If <code>variableName</code> is <code>null</code>, then a <code>NullPointerException</code> is thrown.</p>
   *
   * <p>
   * 
   * 
   * @param variableName The <code>QName</code> of the variable name.
   *
   * @return The variables value, or <code>null</code> if no variable named <code>variableName</code>
   *   exists.  The value returned must be of a type appropriate for the underlying object model.
   *
   * @throws NullPointerException If <code>variableName</code> is <code>null</code>.
   */
  public Object resolveVariable(QName variableName);
}
