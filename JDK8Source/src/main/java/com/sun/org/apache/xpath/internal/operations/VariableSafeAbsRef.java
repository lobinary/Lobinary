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
 * $Id: VariableSafeAbsRef.java,v 1.2.4.1 2005/09/14 21:31:45 jeffsuttor Exp $
 * <p>
 *  $ id：VariableSafeAbsRef.java,v 1.2.4.1 2005/09/14 21:31:45 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal.operations;

import com.sun.org.apache.xml.internal.dtm.DTMManager;
import com.sun.org.apache.xpath.internal.Expression;
import com.sun.org.apache.xpath.internal.XPathContext;
import com.sun.org.apache.xpath.internal.objects.XNodeSet;
import com.sun.org.apache.xpath.internal.objects.XObject;


/**
 * This is a "smart" variable reference that is used in situations where
 * an absolute path is optimized into a variable reference, but may
 * be used in some situations where the document context may have changed.
 * For instance, in select="document(doc/@href)//name[//salary &gt; 7250]", the
 * root in the predicate will be different for each node in the set.  While
 * this is easy to detect statically in this case, in other cases static
 * detection would be very hard or impossible.  So, this class does a dynamic check
 * to make sure the document context of the referenced variable is the same as
 * the current document context, and, if it is not, execute the referenced variable's
 * expression with the current context instead.
 * <p>
 * 这是一个"智能"变量引用,用于将绝对路径优化为变量引用的情况,但可以在文档上下文可能已更改的某些情况下使用。
 * 例如,在select ="document(doc / @ href)// name [// salary&gt; 7250]"中,谓词中的根将对于集合中的每个节点是不同的。
 * 虽然在这种情况下这很容易静态检测,但在其他情况下,静态检测将是非常困难或不可能的。因此,此类执行动态检查以确保引用变量的文档上下文与当前文档上下文相同,如果不是,则使用当前上下文执行引用变量的表达式。
 * 
 */
public class VariableSafeAbsRef extends Variable
{
    static final long serialVersionUID = -9174661990819967452L;

  /**
   * Dereference the variable, and return the reference value.  Note that lazy
   * evaluation will occur.  If a variable within scope is not found, a warning
   * will be sent to the error listener, and an empty nodeset will be returned.
   *
   *
   * <p>
   * 
   * @param xctxt The runtime execution context.
   *
   * @return The evaluated variable, or an empty nodeset if not found.
   *
   * @throws javax.xml.transform.TransformerException
   */
  public XObject execute(XPathContext xctxt, boolean destructiveOK)
        throws javax.xml.transform.TransformerException
  {
        XNodeSet xns = (XNodeSet)super.execute(xctxt, destructiveOK);
        DTMManager dtmMgr = xctxt.getDTMManager();
        int context = xctxt.getContextNode();
        if(dtmMgr.getDTM(xns.getRoot()).getDocument() !=
           dtmMgr.getDTM(context).getDocument())
        {
                Expression expr = (Expression)xns.getContainedIter();
                xns = (XNodeSet)expr.asIterator(xctxt, context);
        }
        return xns;
  }

}
