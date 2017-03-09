/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
// CatalogException.java - Catalog exception

/*
 * Copyright 2001-2004 The Apache Software Foundation or its licensors,
 * as applicable.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有2001-2004 Apache软件基金会或其许可方(如适用)。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xml.internal.resolver;

/**
 * Signal Catalog exception.
 *
 * <p>This exception is thrown if an error occurs loading a
 * catalog file.</p>
 *
 * <p>
 *  信号目录异常。
 * 
 *  <p>如果加载目录文件时出错,则抛出此异常。</p>
 * 
 * 
 * @see Catalog
 *
 * @author Norman Walsh
 * <a href="mailto:Norman.Walsh@Sun.COM">Norman.Walsh@Sun.COM</a>
 *
 */
public class CatalogException extends Exception {
  /** A wrapper around another exception */
  public static final int WRAPPER = 1;
  /** An invalid entry */
  public static final int INVALID_ENTRY = 2;
  /** An invalid entry type */
  public static final int INVALID_ENTRY_TYPE = 3;
  /** Could not instantiate an XML parser */
  public static final int NO_XML_PARSER = 4;
  /** Unknown XML format */
  public static final int UNKNOWN_FORMAT = 5;
  /** Unparseable XML catalog (not XML)*/
  public static final int UNPARSEABLE = 6;
  /** XML but parse failed */
  public static final int PARSE_FAILED = 7;
  /** Text catalog ended in mid-comment */
  public static final int UNENDED_COMMENT = 8;

  /**
   * The embedded exception if tunnelling, or null.
   * <p>
   *  嵌入式异常如果隧道,或null。
   * 
   */
  private Exception exception = null;
  private int exceptionType = 0;

  /**
   * Create a new CatalogException.
   *
   * <p>
   *  创建一个新的CatalogException。
   * 
   * 
   * @param type The exception type
   * @param message The error or warning message.
   */
  public CatalogException (int type, String message) {
    super(message);
    this.exceptionType = type;
    this.exception = null;
  }

  /**
   * Create a new CatalogException.
   *
   * <p>
   *  创建一个新的CatalogException。
   * 
   * 
   * @param type The exception type
   */
  public CatalogException (int type) {
    super("Catalog Exception " + type);
    this.exceptionType = type;
    this.exception = null;
  }

  /**
   * Create a new CatalogException wrapping an existing exception.
   *
   * <p>The existing exception will be embedded in the new
   * one, and its message will become the default message for
   * the CatalogException.</p>
   *
   * <p>
   *  创建一个新的CatalogException包装一个现有的异常。
   * 
   *  <p>现有异常将嵌入到新的异常中,其消息将成为CatalogException的默认消息。</p>
   * 
   * 
   * @param e The exception to be wrapped in a CatalogException.
   */
  public CatalogException (Exception e) {
    super();
    this.exceptionType = WRAPPER;
    this.exception = e;
  }

  /**
   * Create a new CatalogException from an existing exception.
   *
   * <p>The existing exception will be embedded in the new
   * one, but the new exception will have its own message.</p>
   *
   * <p>
   *  从现有异常创建新的CatalogException。
   * 
   *  <p>现有的异常将嵌入新的异常,但新的异常将有自己的消息。</p>
   * 
   * 
   * @param message The detail message.
   * @param e The exception to be wrapped in a CatalogException.
   */
  public CatalogException (String message, Exception e) {
    super(message);
    this.exceptionType = WRAPPER;
    this.exception = e;
  }

  /**
   * Return a detail message for this exception.
   *
   * <p>If there is an embedded exception, and if the CatalogException
   * has no detail message of its own, this method will return
   * the detail message from the embedded exception.</p>
   *
   * <p>
   *  返回此异常的详细信息。
   * 
   * <p>如果存在嵌入的异常,并且CatalogException没有自己的详细信息,则此方法将返回嵌入异常的详细信息。</p>
   * 
   * 
   * @return The error or warning message.
   */
  public String getMessage ()
  {
    String message = super.getMessage();

    if (message == null && exception != null) {
      return exception.getMessage();
    } else {
      return message;
    }
  }

  /**
   * Return the embedded exception, if any.
   *
   * <p>
   *  返回嵌入的异常(如果有)。
   * 
   * 
   * @return The embedded exception, or null if there is none.
   */
  public Exception getException ()
  {
    return exception;
  }

  /**
   * Return the exception type
   *
   * <p>
   *  返回异常类型
   * 
   * 
   * @return The exception type
   */
  public int getExceptionType ()
  {
    return exceptionType;
  }

  /**
   * Override toString to pick up any embedded exception.
   *
   * <p>
   *  覆盖toString以选取任何嵌入的异常。
   * 
   * @return A string representation of this exception.
   */
  public String toString ()
  {
    if (exception != null) {
      return exception.toString();
    } else {
      return super.toString();
    }
  }
}
