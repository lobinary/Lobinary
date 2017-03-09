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
 * $Id: DOMOrder.java,v 1.2.4.1 2005/09/15 08:15:41 suresh_emailid Exp $
 * <p>
 */
package com.sun.org.apache.xml.internal.utils;

/**
/* <p>
/*  $ Id：DOMOrder.java,v 1.2.4.1 2005/09/15 08:15:41 suresh_emailid Exp $
/* 
/* 
 * @deprecated Since the introduction of the DTM, this class will be removed.
 * Nodes that implement this index can return a document order index.
 * Eventually, this will be replaced by DOM 3 methods.
 * (compareDocumentOrder and/or compareTreePosition.)
 */
public interface DOMOrder
{

  /**
   * Get the UID (document order index).
   *
   * <p>
   * 
   * @return integer whose relative value corresponds to document order
   * -- that is, if node1.getUid()<node2.getUid(), node1 comes before
   * node2, and if they're equal node1 and node2 are the same node. No
   * promises are made beyond that.
   */
  public int getUid();
}
