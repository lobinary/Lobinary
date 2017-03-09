/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有1999-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: XPath.java,v 1.2.4.1 2005/09/15 01:41:57 jeffsuttor Exp $
 * <p>
 *  $ Id：XPath.java,v 1.2.4.1 2005/09/15 01:41:57 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal;

import java.io.Serializable;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;

import com.sun.org.apache.xalan.internal.res.XSLMessages;
import com.sun.org.apache.xml.internal.dtm.DTM;
import com.sun.org.apache.xml.internal.utils.PrefixResolver;
import com.sun.org.apache.xml.internal.utils.SAXSourceLocator;
import com.sun.org.apache.xpath.internal.compiler.Compiler;
import com.sun.org.apache.xpath.internal.compiler.FunctionTable;
import com.sun.org.apache.xpath.internal.compiler.XPathParser;
import com.sun.org.apache.xpath.internal.functions.Function;
import com.sun.org.apache.xpath.internal.objects.XObject;
import com.sun.org.apache.xpath.internal.res.XPATHErrorResources;

/**
 * The XPath class wraps an expression object and provides general services
 * for execution of that expression.
 * @xsl.usage advanced
 * <p>
 *  XPath类封装了一个表达式对象,并提供了执行该表达式的一般服务。 @ xsl.usage advanced
 * 
 */
public class XPath implements Serializable, ExpressionOwner
{
    static final long serialVersionUID = 3976493477939110553L;

  /** The top of the expression tree.
  /* <p>
  /* 
   *  @serial */
  private Expression m_mainExp;

  /**
   * The function table for xpath build-in functions
   * <p>
   *  xpath内置函数的函数表
   * 
   */
  private transient FunctionTable m_funcTable = null;

  /**
   * initial the function table
   * <p>
   *  初始化函数表
   * 
   */
  private void initFunctionTable(){
              m_funcTable = new FunctionTable();
  }

  /**
   * Get the raw Expression object that this class wraps.
   *
   *
   * <p>
   *  获取此类包装的原始Expression对象。
   * 
   * 
   * @return the raw Expression object, which should not normally be null.
   */
  public Expression getExpression()
  {
    return m_mainExp;
  }

  /**
   * This function is used to fixup variables from QNames to stack frame
   * indexes at stylesheet build time.
   * <p>
   *  此函数用于在样式表构建时将QNames中的变量固定到堆栈帧索引。
   * 
   * 
   * @param vars List of QNames that correspond to variables.  This list
   * should be searched backwards for the first qualified name that
   * corresponds to the variable reference qname.  The position of the
   * QName in the vector from the start of the vector will be its position
   * in the stack frame (but variables above the globalsTop value will need
   * to be offset to the current stack frame).
   */
  public void fixupVariables(java.util.Vector vars, int globalsSize)
  {
    m_mainExp.fixupVariables(vars, globalsSize);
  }

  /**
   * Set the raw expression object for this object.
   *
   *
   * <p>
   *  设置此对象的原始表达式对象。
   * 
   * 
   * @param exp the raw Expression object, which should not normally be null.
   */
  public void setExpression(Expression exp)
  {
        if(null != m_mainExp)
        exp.exprSetParent(m_mainExp.exprGetParent()); // a bit bogus
    m_mainExp = exp;
  }

  /**
   * Get the SourceLocator on the expression object.
   *
   *
   * <p>
   *  在表达式对象上获取SourceLocator。
   * 
   * 
   * @return the SourceLocator on the expression object, which may be null.
   */
  public SourceLocator getLocator()
  {
    return m_mainExp;
  }

//  /**
//   * Set the SourceLocator on the expression object.
//   *
//   *
//   * <p>
//   *  // *在表达式对象上设置SourceLocator。 // * // *
//   * 
//   * 
//   * @param l the SourceLocator on the expression object, which may be null.
//   */
//  public void setLocator(SourceLocator l)
//  {
//    // Note potential hazards -- l may not be serializable, or may be changed
//      // after being assigned here.
//    m_mainExp.setSourceLocator(l);
//  }

  /** The pattern string, mainly kept around for diagnostic purposes.
  /* <p>
  /* 
   *  @serial  */
  String m_patternString;

  /**
   * Return the XPath string associated with this object.
   *
   *
   * <p>
   *  返回与此对象关联的XPath字符串。
   * 
   * 
   * @return the XPath string associated with this object.
   */
  public String getPatternString()
  {
    return m_patternString;
  }

  /** Represents a select type expression. */
  public static final int SELECT = 0;

  /** Represents a match type expression.  */
  public static final int MATCH = 1;

  /**
   * Construct an XPath object.
   *
   * (Needs review -sc) This method initializes an XPathParser/
   * Compiler and compiles the expression.
   * <p>
   *  构造XPath对象。
   * 
   *  (Needs review -sc)此方法初始化XPathParser / Compiler并编译表达式。
   * 
   * 
   * @param exprString The XPath expression.
   * @param locator The location of the expression, may be null.
   * @param prefixResolver A prefix resolver to use to resolve prefixes to
   *                       namespace URIs.
   * @param type one of {@link #SELECT} or {@link #MATCH}.
   * @param errorListener The error listener, or null if default should be used.
   *
   * @throws javax.xml.transform.TransformerException if syntax or other error.
   */
  public XPath(
          String exprString, SourceLocator locator, PrefixResolver prefixResolver, int type,
          ErrorListener errorListener)
            throws javax.xml.transform.TransformerException
  {
    initFunctionTable();
    if(null == errorListener)
      errorListener = new com.sun.org.apache.xml.internal.utils.DefaultErrorHandler();

    m_patternString = exprString;

    XPathParser parser = new XPathParser(errorListener, locator);
    Compiler compiler = new Compiler(errorListener, locator, m_funcTable);

    if (SELECT == type)
      parser.initXPath(compiler, exprString, prefixResolver);
    else if (MATCH == type)
      parser.initMatchPattern(compiler, exprString, prefixResolver);
    else
      throw new RuntimeException(XSLMessages.createXPATHMessage(XPATHErrorResources.ER_CANNOT_DEAL_XPATH_TYPE, new Object[]{Integer.toString(type)})); //"Can not deal with XPath type: " + type);

    // System.out.println("----------------");
    Expression expr = compiler.compile(0);

    // System.out.println("expr: "+expr);
    this.setExpression(expr);

    if((null != locator) && locator instanceof ExpressionNode)
    {
        expr.exprSetParent((ExpressionNode)locator);
    }

  }

  /**
   * Construct an XPath object.
   *
   * (Needs review -sc) This method initializes an XPathParser/
   * Compiler and compiles the expression.
   * <p>
   * 构造XPath对象。
   * 
   *  (Needs review -sc)此方法初始化XPathParser / Compiler并编译表达式。
   * 
   * 
   * @param exprString The XPath expression.
   * @param locator The location of the expression, may be null.
   * @param prefixResolver A prefix resolver to use to resolve prefixes to
   *                       namespace URIs.
   * @param type one of {@link #SELECT} or {@link #MATCH}.
   * @param errorListener The error listener, or null if default should be used.
   *
   * @throws javax.xml.transform.TransformerException if syntax or other error.
   */
  public XPath(
          String exprString, SourceLocator locator,
          PrefixResolver prefixResolver, int type,
          ErrorListener errorListener, FunctionTable aTable)
            throws javax.xml.transform.TransformerException
  {
    m_funcTable = aTable;
    if(null == errorListener)
      errorListener = new com.sun.org.apache.xml.internal.utils.DefaultErrorHandler();

    m_patternString = exprString;

    XPathParser parser = new XPathParser(errorListener, locator);
    Compiler compiler = new Compiler(errorListener, locator, m_funcTable);

    if (SELECT == type)
      parser.initXPath(compiler, exprString, prefixResolver);
    else if (MATCH == type)
      parser.initMatchPattern(compiler, exprString, prefixResolver);
    else
      throw new RuntimeException(XSLMessages.createXPATHMessage(
            XPATHErrorResources.ER_CANNOT_DEAL_XPATH_TYPE,
            new Object[]{Integer.toString(type)}));
            //"Can not deal with XPath type: " + type);

    // System.out.println("----------------");
    Expression expr = compiler.compile(0);

    // System.out.println("expr: "+expr);
    this.setExpression(expr);

    if((null != locator) && locator instanceof ExpressionNode)
    {
        expr.exprSetParent((ExpressionNode)locator);
    }

  }

  /**
   * Construct an XPath object.
   *
   * (Needs review -sc) This method initializes an XPathParser/
   * Compiler and compiles the expression.
   * <p>
   *  构造XPath对象。
   * 
   *  (Needs review -sc)此方法初始化XPathParser / Compiler并编译表达式。
   * 
   * 
   * @param exprString The XPath expression.
   * @param locator The location of the expression, may be null.
   * @param prefixResolver A prefix resolver to use to resolve prefixes to
   *                       namespace URIs.
   * @param type one of {@link #SELECT} or {@link #MATCH}.
   *
   * @throws javax.xml.transform.TransformerException if syntax or other error.
   */
  public XPath(
          String exprString, SourceLocator locator, PrefixResolver prefixResolver, int type)
            throws javax.xml.transform.TransformerException
  {
    this(exprString, locator, prefixResolver, type, null);
  }

  /**
   * Construct an XPath object.
   *
   * <p>
   *  构造XPath对象。
   * 
   * 
   * @param expr The Expression object.
   *
   * @throws javax.xml.transform.TransformerException if syntax or other error.
   */
  public XPath(Expression expr)
  {
    this.setExpression(expr);
    initFunctionTable();
  }

  /**
   * Given an expression and a context, evaluate the XPath
   * and return the result.
   *
   * <p>
   *  给定表达式和上下文,评估XPath并返回结果。
   * 
   * 
   * @param xctxt The execution context.
   * @param contextNode The node that "." expresses.
   * @param namespaceContext The context in which namespaces in the
   * XPath are supposed to be expanded.
   *
   * @return The result of the XPath or null if callbacks are used.
   * @throws TransformerException thrown if
   * the error condition is severe enough to halt processing.
   *
   * @throws javax.xml.transform.TransformerException
   * @xsl.usage experimental
   */
  public XObject execute(
          XPathContext xctxt, org.w3c.dom.Node contextNode,
          PrefixResolver namespaceContext)
            throws javax.xml.transform.TransformerException
  {
    return execute(
          xctxt, xctxt.getDTMHandleFromNode(contextNode),
          namespaceContext);
  }


  /**
   * Given an expression and a context, evaluate the XPath
   * and return the result.
   *
   * <p>
   *  给定表达式和上下文,评估XPath并返回结果。
   * 
   * 
   * @param xctxt The execution context.
   * @param contextNode The node that "." expresses.
   * @param namespaceContext The context in which namespaces in the
   * XPath are supposed to be expanded.
   *
   * @throws TransformerException thrown if the active ProblemListener decides
   * the error condition is severe enough to halt processing.
   *
   * @throws javax.xml.transform.TransformerException
   * @xsl.usage experimental
   */
  public XObject execute(
          XPathContext xctxt, int contextNode, PrefixResolver namespaceContext)
            throws javax.xml.transform.TransformerException
  {

    xctxt.pushNamespaceContext(namespaceContext);

    xctxt.pushCurrentNodeAndExpression(contextNode, contextNode);

    XObject xobj = null;

    try
    {
      xobj = m_mainExp.execute(xctxt);
    }
    catch (TransformerException te)
    {
      te.setLocator(this.getLocator());
      ErrorListener el = xctxt.getErrorListener();
      if(null != el) // defensive, should never happen.
      {
        el.error(te);
      }
      else
        throw te;
    }
    catch (Exception e)
    {
      while (e instanceof com.sun.org.apache.xml.internal.utils.WrappedRuntimeException)
      {
        e = ((com.sun.org.apache.xml.internal.utils.WrappedRuntimeException) e).getException();
      }
      // e.printStackTrace();

      String msg = e.getMessage();

      if (msg == null || msg.length() == 0) {
           msg = XSLMessages.createXPATHMessage(
               XPATHErrorResources.ER_XPATH_ERROR, null);

      }
      TransformerException te = new TransformerException(msg,
              getLocator(), e);
      ErrorListener el = xctxt.getErrorListener();
      // te.printStackTrace();
      if(null != el) // defensive, should never happen.
      {
        el.fatalError(te);
      }
      else
        throw te;
    }
    finally
    {
      xctxt.popNamespaceContext();

      xctxt.popCurrentNodeAndExpression();
    }

    return xobj;
  }

  /**
   * Given an expression and a context, evaluate the XPath
   * and return the result.
   *
   * <p>
   *  给定表达式和上下文,评估XPath并返回结果。
   * 
   * 
   * @param xctxt The execution context.
   * @param contextNode The node that "." expresses.
   * @param namespaceContext The context in which namespaces in the
   * XPath are supposed to be expanded.
   *
   * @throws TransformerException thrown if the active ProblemListener decides
   * the error condition is severe enough to halt processing.
   *
   * @throws javax.xml.transform.TransformerException
   * @xsl.usage experimental
   */
  public boolean bool(
          XPathContext xctxt, int contextNode, PrefixResolver namespaceContext)
            throws javax.xml.transform.TransformerException
  {

    xctxt.pushNamespaceContext(namespaceContext);

    xctxt.pushCurrentNodeAndExpression(contextNode, contextNode);

    try
    {
      return m_mainExp.bool(xctxt);
    }
    catch (TransformerException te)
    {
      te.setLocator(this.getLocator());
      ErrorListener el = xctxt.getErrorListener();
      if(null != el) // defensive, should never happen.
      {
        el.error(te);
      }
      else
        throw te;
    }
    catch (Exception e)
    {
      while (e instanceof com.sun.org.apache.xml.internal.utils.WrappedRuntimeException)
      {
        e = ((com.sun.org.apache.xml.internal.utils.WrappedRuntimeException) e).getException();
      }
      // e.printStackTrace();

      String msg = e.getMessage();

      if (msg == null || msg.length() == 0) {
           msg = XSLMessages.createXPATHMessage(
               XPATHErrorResources.ER_XPATH_ERROR, null);

      }

      TransformerException te = new TransformerException(msg,
              getLocator(), e);
      ErrorListener el = xctxt.getErrorListener();
      // te.printStackTrace();
      if(null != el) // defensive, should never happen.
      {
        el.fatalError(te);
      }
      else
        throw te;
    }
    finally
    {
      xctxt.popNamespaceContext();

      xctxt.popCurrentNodeAndExpression();
    }

    return false;
  }

  /** Set to true to get diagnostic messages about the result of
  /* <p>
  /* 
   *  match pattern testing.  */
  private static final boolean DEBUG_MATCHES = false;

  /**
   * Get the match score of the given node.
   *
   * <p>
   *  获取给定节点的匹配分数。
   * 
   * 
   * @param xctxt XPath runtime context.
   * @param context The current source tree context node.
   *
   * @return score, one of {@link #MATCH_SCORE_NODETEST},
   * {@link #MATCH_SCORE_NONE}, {@link #MATCH_SCORE_OTHER},
   * or {@link #MATCH_SCORE_QNAME}.
   *
   * @throws javax.xml.transform.TransformerException
   */
  public double getMatchScore(XPathContext xctxt, int context)
          throws javax.xml.transform.TransformerException
  {

    xctxt.pushCurrentNode(context);
    xctxt.pushCurrentExpressionNode(context);

    try
    {
      XObject score = m_mainExp.execute(xctxt);

      if (DEBUG_MATCHES)
      {
        DTM dtm = xctxt.getDTM(context);
        System.out.println("score: " + score.num() + " for "
                           + dtm.getNodeName(context) + " for xpath "
                           + this.getPatternString());
      }

      return score.num();
    }
    finally
    {
      xctxt.popCurrentNode();
      xctxt.popCurrentExpressionNode();
    }

    // return XPath.MATCH_SCORE_NONE;
  }


  /**
   * Warn the user of an problem.
   *
   * <p>
   *  警告用户有问题。
   * 
   * 
   * @param xctxt The XPath runtime context.
   * @param sourceNode Not used.
   * @param msg An error msgkey that corresponds to one of the constants found
   *            in {@link com.sun.org.apache.xpath.internal.res.XPATHErrorResources}, which is
   *            a key for a format string.
   * @param args An array of arguments represented in the format string, which
   *             may be null.
   *
   * @throws TransformerException if the current ErrorListoner determines to
   *                              throw an exception.
   */
  public void warn(
          XPathContext xctxt, int sourceNode, String msg, Object[] args)
            throws javax.xml.transform.TransformerException
  {

    String fmsg = XSLMessages.createXPATHWarning(msg, args);
    ErrorListener ehandler = xctxt.getErrorListener();

    if (null != ehandler)
    {

      // TO DO: Need to get stylesheet Locator from here.
      ehandler.warning(new TransformerException(fmsg, (SAXSourceLocator)xctxt.getSAXLocator()));
    }
  }

  /**
   * Tell the user of an assertion error, and probably throw an
   * exception.
   *
   * <p>
   *  告诉用户断言错误,并可能抛出异常。
   * 
   * 
   * @param b  If false, a runtime exception will be thrown.
   * @param msg The assertion message, which should be informative.
   *
   * @throws RuntimeException if the b argument is false.
   */
  public void assertion(boolean b, String msg)
  {

    if (!b)
    {
      String fMsg = XSLMessages.createXPATHMessage(
        XPATHErrorResources.ER_INCORRECT_PROGRAMMER_ASSERTION,
        new Object[]{ msg });

      throw new RuntimeException(fMsg);
    }
  }

  /**
   * Tell the user of an error, and probably throw an
   * exception.
   *
   * <p>
   *  告诉用户一个错误,并可能抛出异常。
   * 
   * 
   * @param xctxt The XPath runtime context.
   * @param sourceNode Not used.
   * @param msg An error msgkey that corresponds to one of the constants found
   *            in {@link com.sun.org.apache.xpath.internal.res.XPATHErrorResources}, which is
   *            a key for a format string.
   * @param args An array of arguments represented in the format string, which
   *             may be null.
   *
   * @throws TransformerException if the current ErrorListoner determines to
   *                              throw an exception.
   */
  public void error(
          XPathContext xctxt, int sourceNode, String msg, Object[] args)
            throws javax.xml.transform.TransformerException
  {

    String fmsg = XSLMessages.createXPATHMessage(msg, args);
    ErrorListener ehandler = xctxt.getErrorListener();

    if (null != ehandler)
    {
      ehandler.fatalError(new TransformerException(fmsg,
                              (SAXSourceLocator)xctxt.getSAXLocator()));
    }
    else
    {
      SourceLocator slocator = xctxt.getSAXLocator();
      System.out.println(fmsg + "; file " + slocator.getSystemId()
                         + "; line " + slocator.getLineNumber() + "; column "
                         + slocator.getColumnNumber());
    }
  }

  /**
   * This will traverse the heararchy, calling the visitor for
   * each member.  If the called visitor method returns
   * false, the subtree should not be called.
   *
   * <p>
   *  这将遍历听证,调用每个成员的访问者。如果被调用的visitor方法返回false,则不应该调用子树。
   * 
   * 
   * @param owner The owner of the visitor, where that path may be
   *              rewritten if needed.
   * @param visitor The visitor whose appropriate method will be called.
   */
  public void callVisitors(ExpressionOwner owner, XPathVisitor visitor)
  {
        m_mainExp.callVisitors(this, visitor);
  }

  /**
   * The match score if no match is made.
   * @xsl.usage advanced
   * <p>
   *  如果没有匹配的匹配分数。 @ xsl.usage advanced
   * 
   */
  public static final double MATCH_SCORE_NONE = Double.NEGATIVE_INFINITY;

  /**
   * The match score if the pattern has the form
   * of a QName optionally preceded by an @ character.
   * @xsl.usage advanced
   * <p>
   *  如果模式具有QName的形式(可选地前面带有@字符),则匹配分数。 @ xsl.usage advanced
   * 
   */
  public static final double MATCH_SCORE_QNAME = 0.0;

  /**
   * The match score if the pattern pattern has the form NCName:*.
   * @xsl.usage advanced
   * <p>
   *  如果模式模式具有NCName：*形式,则匹配分数。 @ xsl.usage advanced
   * 
   */
  public static final double MATCH_SCORE_NSWILD = -0.25;

  /**
   * The match score if the pattern consists of just a NodeTest.
   * @xsl.usage advanced
   * <p>
   *  如果模式仅由NodeTest组成,则匹配分数。 @ xsl.usage advanced
   * 
   */
  public static final double MATCH_SCORE_NODETEST = -0.5;

  /**
   * The match score if the pattern consists of something
   * other than just a NodeTest or just a qname.
   * @xsl.usage advanced
   * <p>
   *  如果模式由除NodeTest之外的其他内容或仅由qname组成的匹配分数。 @ xsl.usage advanced
   */
  public static final double MATCH_SCORE_OTHER = 0.5;
}
