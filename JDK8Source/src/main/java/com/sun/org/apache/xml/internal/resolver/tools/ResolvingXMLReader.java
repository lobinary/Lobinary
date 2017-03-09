/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
// ResolvingXMLReader.java - An XMLReader that performs catalog resolution

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

package com.sun.org.apache.xml.internal.resolver.tools;

import org.xml.sax.*;

import javax.xml.parsers.*;

import com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl;
import com.sun.org.apache.xml.internal.resolver.*;

/**
 * A SAX XMLReader that performs catalog-based entity resolution.
 *
 * <p>This class implements a SAX XMLReader that performs entity resolution
 * using the CatalogResolver. The actual, underlying parser is obtained
 * from a SAXParserFactory.</p>
 * </p>
 *
 * <p>
 *  执行基于目录的实体解析的SAX XMLReader。
 * 
 *  <p>此类实现了使用CatalogResolver执行实体解析的SAX XMLReader。实际的底层解析器是从SAXParserFactory获取的。</p>
 * </p>
 * 
 * 
 * @see CatalogResolver
 * @see org.xml.sax.XMLReader
 *
 * @author Norman Walsh
 * <a href="mailto:Norman.Walsh@Sun.COM">Norman.Walsh@Sun.COM</a>
 *
 * @version 1.0
 */
public class ResolvingXMLReader extends ResolvingXMLFilter {
  /** Make the parser Namespace aware? */
  public static boolean namespaceAware = true;

  /** Make the parser validating? */
  public static boolean validating = false;

  /**
   * Construct a new reader from the JAXP factory.
   *
   * <p>In order to do its job, a ResolvingXMLReader must in fact be
   * a filter. So the only difference between this code and the filter
   * code is that the constructor builds a new reader.</p>
   * <p>
   *  从JAXP工厂构建一个新的读取器。
   * 
   *  <p>为了完成它的工作,ResolvingXMLReader实际上必须是一个过滤器。因此,此代码和过滤器代码之间的唯一区别是构造函数构建了一个新的读者。</p>
   * 
   */
  public ResolvingXMLReader() {
    super();
    SAXParserFactory spf = catalogManager.useServicesMechanism() ?
                    SAXParserFactory.newInstance() : new SAXParserFactoryImpl();
    spf.setNamespaceAware(namespaceAware);
    spf.setValidating(validating);
    try {
      SAXParser parser = spf.newSAXParser();
      setParent(parser.getXMLReader());
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Construct a new reader from the JAXP factory.
   *
   * <p>In order to do its job, a ResolvingXMLReader must in fact be
   * a filter. So the only difference between this code and the filter
   * code is that the constructor builds a new reader.</p>
   * <p>
   *  从JAXP工厂构建一个新的读取器。
   * 
   */
  public ResolvingXMLReader(CatalogManager manager) {
    super(manager);
    SAXParserFactory spf = catalogManager.useServicesMechanism() ?
                    SAXParserFactory.newInstance() : new SAXParserFactoryImpl();
    spf.setNamespaceAware(namespaceAware);
    spf.setValidating(validating);
    try {
      SAXParser parser = spf.newSAXParser();
      setParent(parser.getXMLReader());
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
