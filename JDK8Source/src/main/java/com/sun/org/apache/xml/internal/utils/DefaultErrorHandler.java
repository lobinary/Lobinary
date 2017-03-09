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
 * $Id: DefaultErrorHandler.java,v 1.2.4.1 2005/09/15 08:15:43 suresh_emailid Exp $
 * <p>
 *  $ Id：DefaultErrorHandler.java,v 1.2.4.1 2005/09/15 08:15:43 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.utils;

import java.io.PrintStream;
import java.io.PrintWriter;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;

import com.sun.org.apache.xml.internal.res.XMLErrorResources;
import com.sun.org.apache.xml.internal.res.XMLMessages;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


/**
 * Implement SAX error handler for default reporting.
 * @xsl.usage general
 * <p>
 *  为默认报告实现SAX错误处理程序。 @ xsl.usage general
 * 
 */
public class DefaultErrorHandler implements ErrorHandler, ErrorListener
{
  PrintWriter m_pw;

  /**
   * if this flag is set to true, we will rethrow the exception on
   * the error() and fatalError() methods. If it is false, the errors
   * are reported to System.err.
   * <p>
   *  如果这个标志设置为true,我们将在error()和fatalError()方法上重新抛出异常。如果为false,则将错误报告给System.err。
   * 
   */
  boolean m_throwExceptionOnError = true;

  /**
   * Constructor DefaultErrorHandler
   * <p>
   *  构造方法DefaultErrorHandler
   * 
   */
  public DefaultErrorHandler(PrintWriter pw)
  {
    m_pw = pw;
  }

  /**
   * Constructor DefaultErrorHandler
   * <p>
   *  构造方法DefaultErrorHandler
   * 
   */
  public DefaultErrorHandler(PrintStream pw)
  {
    m_pw = new PrintWriter(pw, true);
  }

  /**
   * Constructor DefaultErrorHandler
   * <p>
   *  构造方法DefaultErrorHandler
   * 
   */
  public DefaultErrorHandler()
  {
    this(true);
  }

  /**
   * Constructor DefaultErrorHandler
   * <p>
   *  构造方法DefaultErrorHandler
   * 
   */
  public DefaultErrorHandler(boolean throwExceptionOnError)
  {
    m_pw = new PrintWriter(System.err, true);
    m_throwExceptionOnError = throwExceptionOnError;
  }


  /**
   * Receive notification of a warning.
   *
   * <p>SAX parsers will use this method to report conditions that
   * are not errors or fatal errors as defined by the XML 1.0
   * recommendation.  The default behaviour is to take no action.</p>
   *
   * <p>The SAX parser must continue to provide normal parsing events
   * after invoking this method: it should still be possible for the
   * application to process the document through to the end.</p>
   *
   * <p>
   *  接收警告通知。
   * 
   *  <p> SAX解析器将使用此方法报告不是由XML 1.0建议定义的错误或致命错误的条件。默认行为是不采取任何操作。</p>
   * 
   * <p>在调用此方法后,SAX解析器必须继续提供正常的解析事件：应用程序仍然可以处理文档,直到结束。</p>
   * 
   * 
   * @param exception The warning information encapsulated in a
   *                  SAX parse exception.
   * @throws SAXException Any SAX exception, possibly
   *            wrapping another exception.
   */
  public void warning(SAXParseException exception) throws SAXException
  {
    printLocation(m_pw, exception);
    m_pw.println("Parser warning: " + exception.getMessage());
  }

  /**
   * Receive notification of a recoverable error.
   *
   * <p>This corresponds to the definition of "error" in section 1.2
   * of the W3C XML 1.0 Recommendation.  For example, a validating
   * parser would use this callback to report the violation of a
   * validity constraint.  The default behaviour is to take no
   * action.</p>
   *
   * <p>The SAX parser must continue to provide normal parsing events
   * after invoking this method: it should still be possible for the
   * application to process the document through to the end.  If the
   * application cannot do so, then the parser should report a fatal
   * error even if the XML 1.0 recommendation does not require it to
   * do so.</p>
   *
   * <p>
   *  接收可恢复错误的通知。
   * 
   *  <p>这对应于W3C XML 1.0建议书第1.2节中"错误"的定义。例如,验证解析器将使用此回调来报告有效性约束的违反。默认行为是不采取任何操作。</p>
   * 
   *  <p>在调用此方法后,SAX解析器必须继续提供正常的解析事件：应用程序仍然可能处理文档直到结束。如果应用程序不能这样做,那么解析器应该报告致命错误,即使XML 1.0建议不要求它这样做。</p>
   * 
   * 
   * @param exception The error information encapsulated in a
   *                  SAX parse exception.
   * @throws SAXException Any SAX exception, possibly
   *            wrapping another exception.
   */
  public void error(SAXParseException exception) throws SAXException
  {
    //printLocation(exception);
    // m_pw.println(exception.getMessage());

    throw exception;
  }

  /**
   * Receive notification of a non-recoverable error.
   *
   * <p>This corresponds to the definition of "fatal error" in
   * section 1.2 of the W3C XML 1.0 Recommendation.  For example, a
   * parser would use this callback to report the violation of a
   * well-formedness constraint.</p>
   *
   * <p>The application must assume that the document is unusable
   * after the parser has invoked this method, and should continue
   * (if at all) only for the sake of collecting addition error
   * messages: in fact, SAX parsers are free to stop reporting any
   * other events once this method has been invoked.</p>
   *
   * <p>
   *  接收不可恢复错误的通知。
   * 
   *  <p>这对应于W3C XML 1.0建议书第1.2节中"致命错误"的定义。例如,解析器将使用此回调来报告良好形式约束的违规。</p>
   * 
   *  <p>应用程序必须假定文档在解析器调用此方法后不可用,并且应该继续(如果有的话)仅为了收集添加错误消息：实际上,SAX解析器可以自由停止报告任何其他该方法被调用后的事件。</p>
   * 
   * 
   * @param exception The error information encapsulated in a
   *                  SAX parse exception.
   * @throws SAXException Any SAX exception, possibly
   *            wrapping another exception.
   */
  public void fatalError(SAXParseException exception) throws SAXException
  {
    // printLocation(exception);
    // m_pw.println(exception.getMessage());

    throw exception;
  }

  /**
   * Receive notification of a warning.
   *
   * <p>SAX parsers will use this method to report conditions that
   * are not errors or fatal errors as defined by the XML 1.0
   * recommendation.  The default behaviour is to take no action.</p>
   *
   * <p>The SAX parser must continue to provide normal parsing events
   * after invoking this method: it should still be possible for the
   * application to process the document through to the end.</p>
   *
   * <p>
   *  接收警告通知。
   * 
   * <p> SAX解析器将使用此方法报告不是由XML 1.0建议定义的错误或致命错误的条件。默认行为是不采取任何操作。</p>
   * 
   *  <p>在调用此方法后,SAX解析器必须继续提供正常的解析事件：应用程序仍然可以处理文档,直到结束。</p>
   * 
   * 
   * @param exception The warning information encapsulated in a
   *                  SAX parse exception.
   * @throws javax.xml.transform.TransformerException Any SAX exception, possibly
   *            wrapping another exception.
   * @see javax.xml.transform.TransformerException
   */
  public void warning(TransformerException exception) throws TransformerException
  {
    printLocation(m_pw, exception);

    m_pw.println(exception.getMessage());
  }

  /**
   * Receive notification of a recoverable error.
   *
   * <p>This corresponds to the definition of "error" in section 1.2
   * of the W3C XML 1.0 Recommendation.  For example, a validating
   * parser would use this callback to report the violation of a
   * validity constraint.  The default behaviour is to take no
   * action.</p>
   *
   * <p>The SAX parser must continue to provide normal parsing events
   * after invoking this method: it should still be possible for the
   * application to process the document through to the end.  If the
   * application cannot do so, then the parser should report a fatal
   * error even if the XML 1.0 recommendation does not require it to
   * do so.</p>
   *
   * <p>
   *  接收可恢复错误的通知。
   * 
   *  <p>这对应于W3C XML 1.0建议书第1.2节中"错误"的定义。例如,验证解析器将使用此回调来报告有效性约束的违反。默认行为是不采取任何操作。</p>
   * 
   *  <p>在调用此方法后,SAX解析器必须继续提供正常的解析事件：应用程序仍然可能处理文档直到结束。如果应用程序不能这样做,那么解析器应该报告致命错误,即使XML 1.0建议不要求它这样做。</p>
   * 
   * 
   * @param exception The error information encapsulated in a
   *                  SAX parse exception.
   * @throws javax.xml.transform.TransformerException Any SAX exception, possibly
   *            wrapping another exception.
   * @see javax.xml.transform.TransformerException
   */
  public void error(TransformerException exception) throws TransformerException
  {
    // If the m_throwExceptionOnError flag is true, rethrow the exception.
    // Otherwise report the error to System.err.
    if (m_throwExceptionOnError)
      throw exception;
    else
    {
      printLocation(m_pw, exception);
      m_pw.println(exception.getMessage());
    }
  }

  /**
   * Receive notification of a non-recoverable error.
   *
   * <p>This corresponds to the definition of "fatal error" in
   * section 1.2 of the W3C XML 1.0 Recommendation.  For example, a
   * parser would use this callback to report the violation of a
   * well-formedness constraint.</p>
   *
   * <p>The application must assume that the document is unusable
   * after the parser has invoked this method, and should continue
   * (if at all) only for the sake of collecting addition error
   * messages: in fact, SAX parsers are free to stop reporting any
   * other events once this method has been invoked.</p>
   *
   * <p>
   *  接收不可恢复错误的通知。
   * 
   *  <p>这对应于W3C XML 1.0建议书第1.2节中"致命错误"的定义。例如,解析器将使用此回调来报告良好形式约束的违规。</p>
   * 
   * @param exception The error information encapsulated in a
   *                  SAX parse exception.
   * @throws javax.xml.transform.TransformerException Any SAX exception, possibly
   *            wrapping another exception.
   * @see javax.xml.transform.TransformerException
   */
  public void fatalError(TransformerException exception) throws TransformerException
  {
    // If the m_throwExceptionOnError flag is true, rethrow the exception.
    // Otherwise report the error to System.err.
    if (m_throwExceptionOnError)
      throw exception;
    else
    {
      printLocation(m_pw, exception);
      m_pw.println(exception.getMessage());
    }
  }

  public static void ensureLocationSet(TransformerException exception)
  {
    // SourceLocator locator = exception.getLocator();
    SourceLocator locator = null;
    Throwable cause = exception;

    // Try to find the locator closest to the cause.
    do
    {
      if(cause instanceof SAXParseException)
      {
        locator = new SAXSourceLocator((SAXParseException)cause);
      }
      else if (cause instanceof TransformerException)
      {
        SourceLocator causeLocator = ((TransformerException)cause).getLocator();
        if(null != causeLocator)
          locator = causeLocator;
      }

      if(cause instanceof TransformerException)
        cause = ((TransformerException)cause).getCause();
      else if(cause instanceof SAXException)
        cause = ((SAXException)cause).getException();
      else
        cause = null;
    }
    while(null != cause);

    exception.setLocator(locator);
  }

  public static void printLocation(PrintStream pw, TransformerException exception)
  {
    printLocation(new PrintWriter(pw), exception);
  }

  public static void printLocation(java.io.PrintStream pw, org.xml.sax.SAXParseException exception)
  {
    printLocation(new PrintWriter(pw), exception);
  }

  public static void printLocation(PrintWriter pw, Throwable exception)
  {
    SourceLocator locator = null;
    Throwable cause = exception;

    // Try to find the locator closest to the cause.
    do
    {
      if(cause instanceof SAXParseException)
      {
        locator = new SAXSourceLocator((SAXParseException)cause);
      }
      else if (cause instanceof TransformerException)
      {
        SourceLocator causeLocator = ((TransformerException)cause).getLocator();
        if(null != causeLocator)
          locator = causeLocator;
      }
      if(cause instanceof TransformerException)
        cause = ((TransformerException)cause).getCause();
      else if(cause instanceof WrappedRuntimeException)
        cause = ((WrappedRuntimeException)cause).getException();
      else if(cause instanceof SAXException)
        cause = ((SAXException)cause).getException();
      else
        cause = null;
    }
    while(null != cause);

    if(null != locator)
    {
      // m_pw.println("Parser fatal error: "+exception.getMessage());
      String id = (null != locator.getPublicId() )
                  ? locator.getPublicId()
                    : (null != locator.getSystemId())
                      ? locator.getSystemId() : XMLMessages.createXMLMessage(XMLErrorResources.ER_SYSTEMID_UNKNOWN, null); //"SystemId Unknown";

      pw.print(id + "; " +XMLMessages.createXMLMessage("line", null) + locator.getLineNumber()
                         + "; " +XMLMessages.createXMLMessage("column", null) + locator.getColumnNumber()+"; ");
    }
    else
      pw.print("("+XMLMessages.createXMLMessage(XMLErrorResources.ER_LOCATION_UNKNOWN, null)+")");
  }
}
