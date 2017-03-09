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
 * $Id: Function.java,v 1.2.4.1 2005/09/14 20:18:43 jeffsuttor Exp $
 * <p>
 *  $ Id：Function.java,v 1.2.4.1 2005/09/14 20:18:43 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal.functions;

import com.sun.org.apache.xalan.internal.res.XSLMessages;
import com.sun.org.apache.xpath.internal.Expression;
import com.sun.org.apache.xpath.internal.ExpressionOwner;
import com.sun.org.apache.xpath.internal.XPathContext;
import com.sun.org.apache.xpath.internal.XPathVisitor;
import com.sun.org.apache.xpath.internal.compiler.Compiler;
import com.sun.org.apache.xpath.internal.objects.XObject;

/**
 * This is a superclass of all XPath functions.  This allows two
 * ways for the class to be called. One method is that the
 * super class processes the arguments and hands the results to
 * the derived class, the other method is that the derived
 * class may process it's own arguments, which is faster since
 * the arguments don't have to be added to an array, but causes
 * a larger code footprint.
 * @xsl.usage advanced
 * <p>
 *  这是所有XPath函数的超类。这允许调用类的两种方式。一个方法是超类处理参数并将结果传递给派生类,另一个方法是派生类可以处理它自己的参数,这更快,因为参数不必添加到数组,但是导致更大的代码占用。
 *  @ xsl.usage advanced。
 * 
 */
public abstract class Function extends Expression
{
    static final long serialVersionUID = 6927661240854599768L;

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
                        // throw new WrongNumberArgsException(XSLMessages.createXPATHMessage("zero", null));
      reportWrongNumberArgs();
  }

  /**
   * Check that the number of arguments passed to this function is correct.
   * This method is meant to be overloaded by derived classes, to check for
   * the number of arguments for a specific function type.  This method is
   * called by the compiler for static number of arguments checking.
   *
   * <p>
   * 检查传递给此函数的参数数是否正确。此方法意味着被派生类重载,以检查特定函数类型的参数数。该方法由编译器调用,用于静态参数检查数。
   * 
   * 
   * @param argNum The number of arguments that is being passed to the function.
   *
   * @throws WrongNumberArgsException
   */
  public void checkNumberArgs(int argNum) throws WrongNumberArgsException
  {
    if (argNum != 0)
      reportWrongNumberArgs();
  }

  /**
   * Constructs and throws a WrongNumberArgException with the appropriate
   * message for this function object.  This method is meant to be overloaded
   * by derived classes so that the message will be as specific as possible.
   *
   * <p>
   *  构造并抛出一个WrongNumberArgException与此函数对象的相应消息。这个方法意味着被派生类重载,以使消息尽可能具体。
   * 
   * 
   * @throws WrongNumberArgsException
   */
  protected void reportWrongNumberArgs() throws WrongNumberArgsException {
      throw new WrongNumberArgsException(XSLMessages.createXPATHMessage("zero", null));
  }

  /**
   * Execute an XPath function object.  The function must return
   * a valid object.
   * <p>
   *  执行XPath函数对象。函数必须返回有效的对象。
   * 
   * 
   * @param xctxt The execution current context.
   * @return A valid XObject.
   *
   * @throws javax.xml.transform.TransformerException
   */
  public XObject execute(XPathContext xctxt) throws javax.xml.transform.TransformerException
  {

    // Programmer's assert.  (And, no, I don't want the method to be abstract).
    System.out.println("Error! Function.execute should not be called!");

    return null;
  }

  /**
   * Call the visitors for the function arguments.
   * <p>
   *  调用函数参数的访问者。
   * 
   */
  public void callArgVisitors(XPathVisitor visitor)
  {
  }


  /**
  /* <p>
  /* 
   * @see com.sun.org.apache.xpath.internal.XPathVisitable#callVisitors(ExpressionOwner, XPathVisitor)
   */
  public void callVisitors(ExpressionOwner owner, XPathVisitor visitor)
  {
        if(visitor.visitFunction(owner, this))
        {
                callArgVisitors(visitor);
        }
  }

  /**
  /* <p>
  /* 
   * @see Expression#deepEquals(Expression)
   */
  public boolean deepEquals(Expression expr)
  {
        if(!isSameClass(expr))
                return false;

        return true;
  }

  /**
   * This function is currently only being used by Position()
   * and Last(). See respective functions for more detail.
   * <p>
   *  此函数目前仅由Position()和Last()使用。有关更多详细信息,请参阅相应功能。
   */
  public void postCompileStep(Compiler compiler)
  {
    // no default action
  }
}
