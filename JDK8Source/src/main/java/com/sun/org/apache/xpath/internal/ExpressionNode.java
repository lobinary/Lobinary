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
 * $Id: ExpressionNode.java,v 1.1.2.1 2005/08/01 01:30:15 jeffsuttor Exp $
 * <p>
 *  $ Id：ExpressionNode.java,v 1.1.2.1 2005/08/01 01:30:15 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal;

import javax.xml.transform.SourceLocator;

/**
 * A class that implements this interface can construct expressions,
 * give information about child and parent expressions,
 * and give the originating source information.  A class that implements
 * this interface does not lay any claim to being directly executable.
 *
 * <p>Note: This interface should not be considered stable.  Only exprSetParent
 * and exprGetParent can be counted on to work reliably.  Work in progress.</p>
 * <p>
 *  实现此接口的类可以构造表达式,提供有关子表达式和父表达式的信息,并提供源代码信息。实现此接口的类不声明直接可执行。
 * 
 *  <p>注意：此界面不应视为稳定。只有exprSetParent和exprGetParent可以被计数以可靠地工作。正在进行中。</p>
 * 
 */
public interface ExpressionNode extends SourceLocator
{
  /** This pair of methods are used to inform the node of its
  /* <p>
  /* 
    parent. */
  public void exprSetParent(ExpressionNode n);
  public ExpressionNode exprGetParent();

  /** This method tells the node to add its argument to the node's
  /* <p>
  /* 
    list of children.  */
  public void exprAddChild(ExpressionNode n, int i);

  /** This method returns a child node.  The children are numbered
  /* <p>
  /* 
     from zero, left to right. */
  public ExpressionNode exprGetChild(int i);

  /** Return the number of children the node has. */
  public int exprGetNumChildren();
}
