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
 * $Id: SAXSourceLocator.java,v 1.2.4.1 2005/09/15 08:15:52 suresh_emailid Exp $
 * <p>
 *  $ Id：SAXSourceLocator.java,v 1.2.4.1 2005/09/15 08:15:52 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.utils;

import java.io.Serializable;

import javax.xml.transform.SourceLocator;

import org.xml.sax.Locator;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.LocatorImpl;

/**
 * Class SAXSourceLocator extends org.xml.sax.helpers.LocatorImpl
 * for the purpose of implementing the SourceLocator interface,
 * and thus can be both a SourceLocator and a SAX Locator.
 * <p>
 *  为了实现SourceLocator接口,SAXSourceLocator类扩展了org.xml.sax.helpers.LocatorImpl,因此可以同时是SourceLocator和SAX Loc
 * ator。
 * 
 */
public class SAXSourceLocator extends LocatorImpl
        implements SourceLocator, Serializable
{
    static final long serialVersionUID = 3181680946321164112L;
  /** The SAX Locator object.
  /* <p>
  /* 
   *  @serial
   */
  Locator m_locator;

  /**
   * Constructor SAXSourceLocator
   *
   * <p>
   *  构造函数SAXSourceLocator
   * 
   */
  public SAXSourceLocator(){}

  /**
   * Constructor SAXSourceLocator
   *
   *
   * <p>
   *  构造函数SAXSourceLocator
   * 
   * 
   * @param locator Source locator
   */
  public SAXSourceLocator(Locator locator)
  {
    m_locator = locator;
    this.setColumnNumber(locator.getColumnNumber());
    this.setLineNumber(locator.getLineNumber());
    this.setPublicId(locator.getPublicId());
    this.setSystemId(locator.getSystemId());
  }

  /**
   * Constructor SAXSourceLocator
   *
   *
   * <p>
   *  构造函数SAXSourceLocator
   * 
   * 
   * @param locator Source locator
   */
  public SAXSourceLocator(javax.xml.transform.SourceLocator locator)
  {
    m_locator = null;
    this.setColumnNumber(locator.getColumnNumber());
    this.setLineNumber(locator.getLineNumber());
    this.setPublicId(locator.getPublicId());
    this.setSystemId(locator.getSystemId());
  }


  /**
   * Constructor SAXSourceLocator
   *
   *
   * <p>
   *  构造函数SAXSourceLocator
   * 
   * 
   * @param spe SAXParseException exception.
   */
  public SAXSourceLocator(SAXParseException spe)
  {
    this.setLineNumber( spe.getLineNumber() );
    this.setColumnNumber( spe.getColumnNumber() );
    this.setPublicId( spe.getPublicId() );
    this.setSystemId( spe.getSystemId() );
  }

  /**
   * Return the public identifier for the current document event.
   *
   * <p>The return value is the public identifier of the document
   * entity or of the external parsed entity in which the markup
   * triggering the event appears.</p>
   *
   * <p>
   *  返回当前文档事件的公共标识符。
   * 
   *  <p>返回值是文档实体或外部解析实体的公共标识符,其中触发事件的标记出现。</p>
   * 
   * 
   * @return A string containing the public identifier, or
   *         null if none is available.
   * @see #getSystemId
   */
  public String getPublicId()
  {
    return (null == m_locator) ? super.getPublicId() : m_locator.getPublicId();
  }

  /**
   * Return the system identifier for the current document event.
   *
   * <p>The return value is the system identifier of the document
   * entity or of the external parsed entity in which the markup
   * triggering the event appears.</p>
   *
   * <p>If the system identifier is a URL, the parser must resolve it
   * fully before passing it to the application.</p>
   *
   * <p>
   *  返回当前文档事件的系统标识符。
   * 
   * <p>返回值是触发事件的标记出现的文档实体或外部解析实体的系统标识符。</p>
   * 
   *  <p>如果系统标识符是URL,则解析器必须在将其传递给应用程序之前完全解析。</p>
   * 
   * 
   * @return A string containing the system identifier, or null
   *         if none is available.
   * @see #getPublicId
   */
  public String getSystemId()
  {
    return (null == m_locator) ? super.getSystemId() : m_locator.getSystemId();
  }

  /**
   * Return the line number where the current document event ends.
   *
   * <p><strong>Warning:</strong> The return value from the method
   * is intended only as an approximation for the sake of error
   * reporting; it is not intended to provide sufficient information
   * to edit the character content of the original XML document.</p>
   *
   * <p>The return value is an approximation of the line number
   * in the document entity or external parsed entity where the
   * markup triggering the event appears.</p>
   *
   * <p>
   *  返回当前文档事件结束的行号。
   * 
   *  <p> <strong>警告：</strong>该方法的返回值仅用作误差报告的近似值;它不打算提供足够的信息来编辑原始XML文档的字符内容。</p>
   * 
   *  <p>返回值是文档实体或外部解析实体中出现触发事件的标记的行号的近似值。</p>
   * 
   * 
   * @return The line number, or -1 if none is available.
   * @see #getColumnNumber
   */
  public int getLineNumber()
  {
    return (null == m_locator) ? super.getLineNumber() : m_locator.getLineNumber();
  }

  /**
   * Return the column number where the current document event ends.
   *
   * <p><strong>Warning:</strong> The return value from the method
   * is intended only as an approximation for the sake of error
   * reporting; it is not intended to provide sufficient information
   * to edit the character content of the original XML document.</p>
   *
   * <p>The return value is an approximation of the column number
   * in the document entity or external parsed entity where the
   * markup triggering the event appears.</p>
   *
   * <p>
   *  返回当前文档事件结束的列号。
   * 
   *  <p> <strong>警告：</strong>该方法的返回值仅用作误差报告的近似值;它不打算提供足够的信息来编辑原始XML文档的字符内容。</p>
   * 
   * @return The column number, or -1 if none is available.
   * @see #getLineNumber
   */
  public int getColumnNumber()
  {
    return (null == m_locator) ? super.getColumnNumber() : m_locator.getColumnNumber();
  }
}
