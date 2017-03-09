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
 * $Id: FuncConcat.java,v 1.2.4.1 2005/09/14 19:53:44 jeffsuttor Exp $
 * <p>
 *  $ Id：FuncConcat.java,v 1.2.4.1 2005/09/14 19:53:44 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal.functions;

import com.sun.org.apache.xalan.internal.res.XSLMessages;
import com.sun.org.apache.xpath.internal.XPathContext;
import com.sun.org.apache.xpath.internal.objects.XObject;
import com.sun.org.apache.xpath.internal.objects.XString;

/**
 * Execute the Concat() function.
 * @xsl.usage advanced
 * <p>
 *  执行Concat()函数。 @ xsl.usage advanced
 * 
 */
public class FuncConcat extends FunctionMultiArgs
{
    static final long serialVersionUID = 1737228885202314413L;

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
  public XObject execute(XPathContext xctxt) throws javax.xml.transform.TransformerException
  {

    StringBuffer sb = new StringBuffer();

    // Compiler says we must have at least two arguments.
    sb.append(m_arg0.execute(xctxt).str());
    sb.append(m_arg1.execute(xctxt).str());

    if (null != m_arg2)
      sb.append(m_arg2.execute(xctxt).str());

    if (null != m_args)
    {
      for (int i = 0; i < m_args.length; i++)
      {
        sb.append(m_args[i].execute(xctxt).str());
      }
    }

    return new XString(sb.toString());
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
  public void checkNumberArgs(int argNum) throws WrongNumberArgsException
  {
    if (argNum < 2)
      reportWrongNumberArgs();
  }

  /**
   * Constructs and throws a WrongNumberArgException with the appropriate
   * message for this function object.
   *
   * <p>
   *  构造并抛出一个WrongNumberArgException与此函数对象的相应消息。
   * 
   * @throws WrongNumberArgsException
   */
  protected void reportWrongNumberArgs() throws WrongNumberArgsException {
      throw new WrongNumberArgsException(XSLMessages.createXPATHMessage("gtone", null));
  }
}
