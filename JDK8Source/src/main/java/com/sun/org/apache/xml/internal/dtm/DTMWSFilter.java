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
 * $Id: DTMWSFilter.java,v 1.2.4.1 2005/09/15 08:14:55 suresh_emailid Exp $
 * <p>
 *  $ Id：DTMWSFilter.java,v 1.2.4.1 2005/09/15 08:14:55 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.dtm;

/**
 * This interface is meant to be implemented by a client of the DTM, and allows
 * stripping of whitespace nodes.
 * <p>
 *  该接口意在由DTM的客户端实现,并且允许剥离空白节点。
 * 
 */
public interface DTMWSFilter
{
  /**
   * Do not strip whitespace child nodes of this element.
   * <p>
   *  不要剥离此元素的空白子节点。
   * 
   */
  public static final short NOTSTRIP = 1;

  /**
   * Strip whitespace child nodes of this element.
   * <p>
   *  剥离此元素的空白子节点。
   * 
   */
  public static final short STRIP = 2;

  /**
   * Inherit whitespace stripping behavior of the parent node.
   * <p>
   *  继承父节点的空白剥离行为。
   * 
   */
  public static final short INHERIT = 3;

  /**
   * Test whether whitespace-only text nodes are visible in the logical
   * view of <code>DTM</code>. Normally, this function
   * will be called by the implementation of <code>DTM</code>;
   * it is not normally called directly from
   * user code.
   *
   * <p>
   *  测试在<code> DTM </code>的逻辑视图中是否可以看到仅有空格的文本节点。通常,该函数将通过实现<code> DTM </code>来调用;它通常不直接从用户代码调用。
   * 
   * @param elementHandle int Handle of the element.
   * @return one of NOTSTRIP, STRIP, or INHERIT.
   */
  public short getShouldStripSpace(int elementHandle, DTM dtm);

}
