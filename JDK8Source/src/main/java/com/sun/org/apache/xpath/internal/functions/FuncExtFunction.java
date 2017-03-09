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
 * $Id: FuncExtFunction.java,v 1.2.4.2 2005/09/14 20:18:43 jeffsuttor Exp $
 * <p>
 *  $ Id：FuncExtFunction.java,v 1.2.4.2 2005/09/14 20:18:43 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal.functions;

import java.util.Vector;

import com.sun.org.apache.xalan.internal.res.XSLMessages;
import com.sun.org.apache.xpath.internal.Expression;
import com.sun.org.apache.xpath.internal.ExpressionNode;
import com.sun.org.apache.xpath.internal.ExpressionOwner;
import com.sun.org.apache.xpath.internal.ExtensionsProvider;
import com.sun.org.apache.xpath.internal.XPathContext;
import com.sun.org.apache.xpath.internal.XPathVisitor;
import com.sun.org.apache.xpath.internal.objects.XNull;
import com.sun.org.apache.xpath.internal.objects.XObject;
import com.sun.org.apache.xpath.internal.res.XPATHErrorResources;
import com.sun.org.apache.xpath.internal.res.XPATHMessages;

/**
 * An object of this class represents an extension call expression.  When
 * the expression executes, it calls ExtensionsTable#extFunction, and then
 * converts the result to the appropriate XObject.
 * @xsl.usage advanced
 * <p>
 *  此类的一个对象表示一个扩展调用表达式。当表达式执行时,它调用ExtensionsTable#extFunction,然后将结果转换为适当的XObject。 @ xsl.usage advanced
 * 
 */
public class FuncExtFunction extends Function
{
    static final long serialVersionUID = 5196115554693708718L;

  /**
   * The namespace for the extension function, which should not normally
   *  be null or empty.
   * <p>
   *  扩展函数的命名空间,通常不应为null或空。
   * 
   * 
   *  @serial
   */
  String m_namespace;

  /**
   * The local name of the extension.
   * <p>
   *  扩展的本地名称。
   * 
   * 
   *  @serial
   */
  String m_extensionName;

  /**
   * Unique method key, which is passed to ExtensionsTable#extFunction in
   *  order to allow caching of the method.
   * <p>
   *  唯一方法键,它被传递到ExtensionsTable#extFunction以允许缓存方法。
   * 
   * 
   *  @serial
   */
  Object m_methodKey;

  /**
   * Array of static expressions which represent the parameters to the
   *  function.
   * <p>
   *  表示函数的参数的静态表达式数组。
   * 
   * 
   *  @serial
   */
  Vector m_argVec = new Vector();

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
   * NEEDSDOC @param globalsSize
   */
  public void fixupVariables(java.util.Vector vars, int globalsSize)
  {

    if (null != m_argVec)
    {
      int nArgs = m_argVec.size();

      for (int i = 0; i < nArgs; i++)
      {
        Expression arg = (Expression) m_argVec.elementAt(i);

        arg.fixupVariables(vars, globalsSize);
      }
    }
  }

  /**
   * Return the namespace of the extension function.
   *
   * <p>
   *  返回扩展函数的命名空间。
   * 
   * 
   * @return The namespace of the extension function.
   */
  public String getNamespace()
  {
    return m_namespace;
  }

  /**
   * Return the name of the extension function.
   *
   * <p>
   * 返回扩展函数的名称。
   * 
   * 
   * @return The name of the extension function.
   */
  public String getFunctionName()
  {
    return m_extensionName;
  }

  /**
   * Return the method key of the extension function.
   *
   * <p>
   *  返回扩展函数的方法键。
   * 
   * 
   * @return The method key of the extension function.
   */
  public Object getMethodKey()
  {
    return m_methodKey;
  }

  /**
   * Return the nth argument passed to the extension function.
   *
   * <p>
   *  返回传递给扩展函数的第n个参数。
   * 
   * 
   * @param n The argument number index.
   * @return The Expression object at the given index.
   */
  public Expression getArg(int n) {
    if (n >= 0 && n < m_argVec.size())
      return (Expression) m_argVec.elementAt(n);
    else
      return null;
  }

  /**
   * Return the number of arguments that were passed
   * into this extension function.
   *
   * <p>
   *  返回传递到此扩展函数的参数数。
   * 
   * 
   * @return The number of arguments.
   */
  public int getArgCount() {
    return m_argVec.size();
  }

  /**
   * Create a new FuncExtFunction based on the qualified name of the extension,
   * and a unique method key.
   *
   * <p>
   *  基于扩展的限定名称和唯一方法键创建一个新的FuncExtFunction。
   * 
   * 
   * @param namespace The namespace for the extension function, which should
   *                  not normally be null or empty.
   * @param extensionName The local name of the extension.
   * @param methodKey Unique method key, which is passed to
   *                  ExtensionsTable#extFunction in order to allow caching
   *                  of the method.
   */
  public FuncExtFunction(java.lang.String namespace,
                         java.lang.String extensionName, Object methodKey)
  {
    //try{throw new Exception("FuncExtFunction() " + namespace + " " + extensionName);} catch (Exception e){e.printStackTrace();}
    m_namespace = namespace;
    m_extensionName = extensionName;
    m_methodKey = methodKey;
  }

  /**
   * Execute the function.  The function must return
   * a valid object.
   * <p>
   *  执行该功能。函数必须返回有效的对象。
   * 
   * 
   * @param xctxt The current execution context.
   * @return A valid XObject.
   *
   * @throws javax.xml.transform.TransformerException
   */
  public XObject execute(XPathContext xctxt)
          throws javax.xml.transform.TransformerException
  {
    if (xctxt.isSecureProcessing())
      throw new javax.xml.transform.TransformerException(
        XPATHMessages.createXPATHMessage(
          XPATHErrorResources.ER_EXTENSION_FUNCTION_CANNOT_BE_INVOKED,
          new Object[] {toString()}));

    XObject result;
    Vector argVec = new Vector();
    int nArgs = m_argVec.size();

    for (int i = 0; i < nArgs; i++)
    {
      Expression arg = (Expression) m_argVec.elementAt(i);

      XObject xobj = arg.execute(xctxt);
      /*
       * Should cache the arguments for func:function
       * <p>
       *  应缓存func：函数的参数
       * 
       */
      xobj.allowDetachToRelease(false);
      argVec.addElement(xobj);
    }
    //dml
    ExtensionsProvider extProvider = (ExtensionsProvider)xctxt.getOwnerObject();
    Object val = extProvider.extFunction(this, argVec);

    if (null != val)
    {
      result = XObject.create(val, xctxt);
    }
    else
    {
      result = new XNull();
    }

    return result;
  }

  /**
   * Set an argument expression for a function.  This method is called by the
   * XPath compiler.
   *
   * <p>
   *  设置函数的参数表达式。此方法由XPath编译器调用。
   * 
   * 
   * @param arg non-null expression that represents the argument.
   * @param argNum The argument number index.
   *
   * @throws WrongNumberArgsException If the argNum parameter is beyond what
   * is specified for this function.
   */
  public void setArg(Expression arg, int argNum)
          throws WrongNumberArgsException
  {
    m_argVec.addElement(arg);
    arg.exprSetParent(this);
  }

  /**
   * Check that the number of arguments passed to this function is correct.
   *
   *
   * <p>
   *  检查传递给此函数的参数数是否正确。
   * 
   * 
   * @param argNum The number of arguments that is being passed to the function.
   *
   * @throws WrongNumberArgsException
   */
  public void checkNumberArgs(int argNum) throws WrongNumberArgsException{}


  class ArgExtOwner implements ExpressionOwner
  {

    Expression m_exp;

        ArgExtOwner(Expression exp)
        {
                m_exp = exp;
        }

    /**
    /* <p>
    /* 
     * @see ExpressionOwner#getExpression()
     */
    public Expression getExpression()
    {
      return m_exp;
    }


    /**
    /* <p>
    /* 
     * @see ExpressionOwner#setExpression(Expression)
     */
    public void setExpression(Expression exp)
    {
        exp.exprSetParent(FuncExtFunction.this);
        m_exp = exp;
    }
  }


  /**
   * Call the visitors for the function arguments.
   * <p>
   *  调用函数参数的访问者。
   * 
   */
  public void callArgVisitors(XPathVisitor visitor)
  {
      for (int i = 0; i < m_argVec.size(); i++)
      {
         Expression exp = (Expression)m_argVec.elementAt(i);
         exp.callVisitors(new ArgExtOwner(exp), visitor);
      }

  }

  /**
   * Set the parent node.
   * For an extension function, we also need to set the parent
   * node for all argument expressions.
   *
   * <p>
   *  设置父节点。对于扩展函数,我们还需要为所有参数表达式设置父节点。
   * 
   * 
   * @param n The parent node
   */
  public void exprSetParent(ExpressionNode n)
  {

    super.exprSetParent(n);

    int nArgs = m_argVec.size();

    for (int i = 0; i < nArgs; i++)
    {
      Expression arg = (Expression) m_argVec.elementAt(i);

      arg.exprSetParent(n);
    }
  }

  /**
   * Constructs and throws a WrongNumberArgException with the appropriate
   * message for this function object.  This class supports an arbitrary
   * number of arguments, so this method must never be called.
   *
   * <p>
   *  构造并抛出一个WrongNumberArgException与此函数对象的相应消息。这个类支持任意数量的参数,所以这个方法不能被调用。
   * 
   * 
   * @throws WrongNumberArgsException
   */
  protected void reportWrongNumberArgs() throws WrongNumberArgsException {
    String fMsg = XSLMessages.createXPATHMessage(
        XPATHErrorResources.ER_INCORRECT_PROGRAMMER_ASSERTION,
        new Object[]{ "Programmer's assertion:  the method FunctionMultiArgs.reportWrongNumberArgs() should never be called." });

    throw new RuntimeException(fMsg);
  }

  /**
   * Return the name of the extesion function in string format
   * <p>
   *  以字符串格式返回extesion函数的名称
   */
  public String toString()
  {
    if (m_namespace != null && m_namespace.length() > 0)
      return "{" + m_namespace + "}" + m_extensionName;
    else
      return m_extensionName;
  }
}
