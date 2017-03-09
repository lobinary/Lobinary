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
 * $Id: NodeLocator.java,v 1.2.4.1 2005/09/15 08:15:08 suresh_emailid Exp $
 * <p>
 *  $ Id：NodeLocator.java,v 1.2.4.1 2005/09/15 08:15:08 suresh_emailid Exp $
 * 
 */

package com.sun.org.apache.xml.internal.dtm.ref;

import javax.xml.transform.SourceLocator;

/**
 * <code>NodeLocator</code> maintains information on an XML source
 * node.
 *
 * <p>
 *  <code> NodeLocator </code>维护有关XML源节点的信息。
 * 
 * 
 * @author <a href="mailto:ovidiu@cup.hp.com">Ovidiu Predescu</a>
 * @since May 23, 2001
 */
public class NodeLocator implements SourceLocator
{
  protected String m_publicId;
  protected String m_systemId;
  protected int m_lineNumber;
  protected int m_columnNumber;

  /**
   * Creates a new <code>NodeLocator</code> instance.
   *
   * <p>
   *  创建一个新的<code> NodeLocator </code>实例。
   * 
   * 
   * @param publicId a <code>String</code> value
   * @param systemId a <code>String</code> value
   * @param lineNumber an <code>int</code> value
   * @param columnNumber an <code>int</code> value
   */
  public NodeLocator(String publicId, String systemId,
                     int lineNumber, int columnNumber)
  {
    this.m_publicId = publicId;
    this.m_systemId = systemId;
    this.m_lineNumber = lineNumber;
    this.m_columnNumber = columnNumber;
  }

  /**
   * <code>getPublicId</code> returns the public ID of the node.
   *
   * <p>
   *  <code> getPublicId </code>返回节点的公共ID。
   * 
   * 
   * @return a <code>String</code> value
   */
  public String getPublicId()
  {
    return m_publicId;
  }

  /**
   * <code>getSystemId</code> returns the system ID of the node.
   *
   * <p>
   *  <code> getSystemId </code>返回节点的系统ID。
   * 
   * 
   * @return a <code>String</code> value
   */
  public String getSystemId()
  {
    return m_systemId;
  }

  /**
   * <code>getLineNumber</code> returns the line number of the node.
   *
   * <p>
   *  <code> getLineNumber </code>返回节点的行号。
   * 
   * 
   * @return an <code>int</code> value
   */
  public int getLineNumber()
  {
    return m_lineNumber;
  }

  /**
   * <code>getColumnNumber</code> returns the column number of the
   * node.
   *
   * <p>
   *  <code> getColumnNumber </code>返回节点的列号。
   * 
   * 
   * @return an <code>int</code> value
   */
  public int getColumnNumber()
  {
    return m_columnNumber;
  }

  /**
   * <code>toString</code> returns a string representation of this
   * NodeLocator instance.
   *
   * <p>
   *  <code> toString </code>返回此NodeLocator实例的字符串表示形式。
   * 
   * @return a <code>String</code> value
   */
  public String toString()
  {
    return "file '" + m_systemId
      + "', line #" + m_lineNumber
      + ", column #" + m_columnNumber;
  }
}
