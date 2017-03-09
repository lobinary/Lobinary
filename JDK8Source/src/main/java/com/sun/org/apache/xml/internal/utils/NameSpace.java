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
 * $Id: NameSpace.java,v 1.2.4.1 2005/09/15 08:15:49 suresh_emailid Exp $
 * <p>
 *  $ Id：NameSpace.java,v 1.2.4.1 2005/09/15 08:15:49 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.utils;

import java.io.Serializable;

/**
 * A representation of a namespace.  One of these will
 * be pushed on the namespace stack for each
 * element.
 * @xsl.usage advanced
 * <p>
 *  命名空间的表示。其中一个将被推送到每个元素的命名空间堆栈。 @ xsl.usage advanced
 * 
 */
public class NameSpace implements Serializable
{
    static final long serialVersionUID = 1471232939184881839L;

  /** Next NameSpace element on the stack.
  /* <p>
  /* 
   *  @serial             */
  public NameSpace m_next = null;

  /** Prefix of this NameSpace element.
  /* <p>
  /* 
   *  @serial          */
  public String m_prefix;

  /** Namespace URI of this NameSpace element.
  /* <p>
  /* 
   *  @serial           */
  public String m_uri;  // if null, then Element namespace is empty.

  /**
   * Construct a namespace for placement on the
   * result tree namespace stack.
   *
   * <p>
   *  在结果树命名空间堆栈上构造用于放置的命名空间。
   * 
   * @param prefix Prefix of this element
   * @param uri URI of  this element
   */
  public NameSpace(String prefix, String uri)
  {
    m_prefix = prefix;
    m_uri = uri;
  }
}
