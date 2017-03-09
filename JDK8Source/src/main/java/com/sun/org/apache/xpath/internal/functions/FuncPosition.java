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
 * $Id: FuncPosition.java,v 1.2.4.1 2005/09/14 20:18:45 jeffsuttor Exp $
 * <p>
 *  $ Id：FuncPosition.java,v 1.2.4.1 2005/09/14 20:18:45 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal.functions;

import com.sun.org.apache.xml.internal.dtm.DTM;
import com.sun.org.apache.xml.internal.dtm.DTMIterator;
import com.sun.org.apache.xpath.internal.XPathContext;
import com.sun.org.apache.xpath.internal.axes.SubContextList;
import com.sun.org.apache.xpath.internal.compiler.Compiler;
import com.sun.org.apache.xpath.internal.objects.XNumber;
import com.sun.org.apache.xpath.internal.objects.XObject;

/**
 * Execute the Position() function.
 * @xsl.usage advanced
 * <p>
 *  执行Position()函数。 @ xsl.usage advanced
 * 
 */
public class FuncPosition extends Function
{
    static final long serialVersionUID = -9092846348197271582L;
  private boolean m_isTopLevel;

  /**
   * Figure out if we're executing a toplevel expression.
   * If so, we can't be inside of a predicate.
   * <p>
   *  找出我们是否正在执行toplevel表达式。如果是这样,我们不能在谓词内。
   * 
   */
  public void postCompileStep(Compiler compiler)
  {
    m_isTopLevel = compiler.getLocationPathDepth() == -1;
  }

  /**
   * Get the position in the current context node list.
   *
   * <p>
   *  获取当前上下文节点列表中的位置。
   * 
   * 
   * @param xctxt Runtime XPath context.
   *
   * @return The current position of the itteration in the context node list,
   *         or -1 if there is no active context node list.
   */
  public int getPositionInContextNodeList(XPathContext xctxt)
  {

    // System.out.println("FuncPosition- entry");
    // If we're in a predicate, then this will return non-null.
    SubContextList iter = m_isTopLevel ? null : xctxt.getSubContextList();

    if (null != iter)
    {
      int prox = iter.getProximityPosition(xctxt);

      // System.out.println("FuncPosition- prox: "+prox);
      return prox;
    }

    DTMIterator cnl = xctxt.getContextNodeList();

    if (null != cnl)
    {
      int n = cnl.getCurrentNode();
      if(n == DTM.NULL)
      {
        if(cnl.getCurrentPos() == 0)
          return 0;

        // Then I think we're in a sort.  See sort21.xsl. So the iterator has
        // already been spent, and is not on the node we're processing.
        // It's highly possible that this is an issue for other context-list
        // functions.  Shouldn't be a problem for last(), and it shouldn't be
        // a problem for current().
        try
        {
          cnl = cnl.cloneWithReset();
        }
        catch(CloneNotSupportedException cnse)
        {
          throw new com.sun.org.apache.xml.internal.utils.WrappedRuntimeException(cnse);
        }
        int currentNode = xctxt.getContextNode();
        // System.out.println("currentNode: "+currentNode);
        while(DTM.NULL != (n = cnl.nextNode()))
        {
          if(n == currentNode)
            break;
        }
      }
      // System.out.println("n: "+n);
      // System.out.println("FuncPosition- cnl.getCurrentPos(): "+cnl.getCurrentPos());
      return cnl.getCurrentPos();
    }

    // System.out.println("FuncPosition - out of guesses: -1");
    return -1;
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
  public XObject execute(XPathContext xctxt) throws javax.xml.transform.TransformerException
  {
    double pos = (double) getPositionInContextNodeList(xctxt);

    return new XNumber(pos);
  }

  /**
   * No arguments to process, so this does nothing.
   * <p>
   *  没有要处理的参数,因此这什么也不做。
   */
  public void fixupVariables(java.util.Vector vars, int globalsSize)
  {
    // no-op
  }
}
