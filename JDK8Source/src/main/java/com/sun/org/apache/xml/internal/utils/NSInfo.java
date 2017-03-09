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
 * $Id: NSInfo.java,v 1.2.4.1 2005/09/15 08:15:48 suresh_emailid Exp $
 * <p>
 *  $ Id：NSInfo.java,v 1.2.4.1 2005/09/15 08:15:48 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.utils;

/**
 * This class holds information about the namespace info
 * of a node.  It is used to optimize namespace lookup in
 * a generic DOM.
 * @xsl.usage internal
 * <p>
 *  此类保存有关节点的命名空间信息的信息。它用于优化通用DOM中的命名空间查找。 @ xsl.usage internal
 * 
 */
public class NSInfo
{

  /**
   * Constructor NSInfo
   *
   *
   * <p>
   *  构造函数NSInfo
   * 
   * 
   * @param hasProcessedNS Flag indicating whether namespaces
   * have been processed for this node
   * @param hasXMLNSAttrs Flag indicating whether this node
   * has XMLNS attributes.
   */
  public NSInfo(boolean hasProcessedNS, boolean hasXMLNSAttrs)
  {

    m_hasProcessedNS = hasProcessedNS;
    m_hasXMLNSAttrs = hasXMLNSAttrs;
    m_namespace = null;
    m_ancestorHasXMLNSAttrs = ANCESTORXMLNSUNPROCESSED;
  }

  // Unused at the moment

  /**
   * Constructor NSInfo
   *
   *
   * <p>
   *  构造函数NSInfo
   * 
   * 
   * @param hasProcessedNS Flag indicating whether namespaces
   * have been processed for this node
   * @param hasXMLNSAttrs Flag indicating whether this node
   * has XMLNS attributes.
   * @param ancestorHasXMLNSAttrs Flag indicating whether one of this node's
   * ancestor has XMLNS attributes.
   */
  public NSInfo(boolean hasProcessedNS, boolean hasXMLNSAttrs,
                int ancestorHasXMLNSAttrs)
  {

    m_hasProcessedNS = hasProcessedNS;
    m_hasXMLNSAttrs = hasXMLNSAttrs;
    m_ancestorHasXMLNSAttrs = ancestorHasXMLNSAttrs;
    m_namespace = null;
  }

  /**
   * Constructor NSInfo
   *
   *
   * <p>
   *  构造函数NSInfo
   * 
   * @param namespace The namespace URI
   * @param hasXMLNSAttrs Flag indicating whether this node
   * has XMLNS attributes.
   */
  public NSInfo(String namespace, boolean hasXMLNSAttrs)
  {

    m_hasProcessedNS = true;
    m_hasXMLNSAttrs = hasXMLNSAttrs;
    m_namespace = namespace;
    m_ancestorHasXMLNSAttrs = ANCESTORXMLNSUNPROCESSED;
  }

  /** The namespace URI          */
  public String m_namespace;

  /** Flag indicating whether this node has an XMLNS attribute          */
  public boolean m_hasXMLNSAttrs;

  /** Flag indicating whether namespaces have been processed for this node */
  public boolean m_hasProcessedNS;

  /** Flag indicating whether one of this node's ancestor has an XMLNS attribute          */
  public int m_ancestorHasXMLNSAttrs;

  /** Constant for ancestors XMLNS atributes not processed          */
  public static final int ANCESTORXMLNSUNPROCESSED = 0;

  /** Constant indicating an ancestor has an XMLNS attribute           */
  public static final int ANCESTORHASXMLNS = 1;

  /** Constant indicating ancestors don't have an XMLNS attribute           */
  public static final int ANCESTORNOXMLNS = 2;
}
