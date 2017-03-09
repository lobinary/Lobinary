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
 * $Id: Method.java,v 1.2.4.1 2005/09/15 08:15:19 suresh_emailid Exp $
 * <p>
 *  $ Id：Method.java,v 1.2.4.1 2005/09/15 08:15:19 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.serializer;

/**
 * This class defines the constants which are the names of the four default
 * output methods.
 * <p>
 * Three default output methods are defined: XML, HTML, and TEXT.
 * These constants can be used as an argument to the
 * OutputPropertiesFactory.getDefaultMethodProperties() method to get
 * the properties to create a serializer.
 *
 * This class is a public API.
 *
 * <p>
 *  这个类定义了四个默认输出方法的名称的常量。
 * <p>
 *  定义了三种默认输出方法：XML,HTML和TEXT。
 * 这些常量可以用作OutputPropertiesFactory.getDefaultMethodProperties()方法的参数,以获取属性来创建序列化程序。
 * 
 *  这个类是一个公共API。
 * 
 * 
 * @see OutputPropertiesFactory
 * @see Serializer
 *
 * @xsl.usage general
 */
public final class Method
{
    /**
     * A private constructor to prevent the creation of such a class.
     * <p>
     *  一个私有的构造函数来阻止创建这样的类。
     * 
     */
    private Method() {

    }

  /**
   * The output method type for XML documents: <tt>xml</tt>.
   * <p>
   *  XML文档的输出方法类型：<tt> xml </tt>。
   * 
   */
  public static final String XML = "xml";

  /**
   * The output method type for HTML documents: <tt>html</tt>.
   * <p>
   *  HTML文档的输出方法类型：<tt> html </tt>。
   * 
   */
  public static final String HTML = "html";

  /**
   * The output method for XHTML documents,
   * this method type is not currently supported: <tt>xhtml</tt>.
   * <p>
   *  XHTML文档的输出方法,此方法类型当前不受支持：<tt> xhtml </tt>。
   * 
   */
  public static final String XHTML = "xhtml";

  /**
   * The output method type for text documents: <tt>text</tt>.
   * <p>
   *  文本文档的输出方法类型：<tt>文本</tt>。
   * 
   */
  public static final String TEXT = "text";

  /**
   * The "internal" method, just used when no method is
   * specified in the style sheet, and a serializer of this type wraps either an
   * XML or HTML type (depending on the first tag in the output being html or
   * not)
   * <p>
   * "内部"方法,仅当样式表中未指定方法时使用,并且此类型的序列化程序包装XML或HTML类型(取决于输出中的第一个标记是否为html)
   */
  public static final String UNKNOWN = "";
}
