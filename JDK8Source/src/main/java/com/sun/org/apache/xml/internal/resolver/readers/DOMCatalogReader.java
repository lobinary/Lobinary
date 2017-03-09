/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
// DOMCatalogReader.java - Read XML Catalog files

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

package com.sun.org.apache.xml.internal.resolver.readers;

import com.sun.org.apache.xml.internal.resolver.Catalog;
import com.sun.org.apache.xml.internal.resolver.CatalogException;
import com.sun.org.apache.xml.internal.resolver.helpers.Namespaces;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Hashtable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import sun.reflect.misc.ReflectUtil;

/**
 * A DOM-based CatalogReader.
 *
 * <p>This class is used to read XML Catalogs using the DOM. This reader
 * has an advantage over the SAX-based reader that it can analyze the
 * DOM tree rather than simply a series of SAX events. It has the disadvantage
 * that it requires all of the code necessary to build and walk a DOM
 * tree.</p>
 *
 * <p>Since the choice of CatalogReaders (in the InputStream case) can only
 * be made on the basis of MIME type, the following problem occurs: only
 * one CatalogReader can exist for all XML mime types. In order to get
 * around this problem, the DOMCatalogReader relies on a set of external
 * CatalogParsers to actually build the catalog.</p>
 *
 * <p>The selection of CatalogParsers is made on the basis of the QName
 * of the root element of the document.</p>
 *
 *
 * <p>
 *  基于DOM的CatalogReader。
 * 
 *  <p>此类用于使用DOM读取XML目录。此读取器具有优于基于SAX的读取器的优点,其可以分析DOM树而不是简单地一系列SAX事件。它的缺点是它需要构建和遍历DOM树所需的所有代码。</p>
 * 
 *  <p>由于CatalogReaders(在InputStream情况下)的选择只能基于MIME类型,所以会出现以下问题：对于所有XML MIME类型,只能存在一个CatalogReader。
 * 为了解决这个问题,DOMCatalogReader依赖一组外部CatalogParsers来实际构建目录。</p>。
 * 
 * <p> CatalogParsers的选择基于文档根元素的QName。</p>
 * 
 * 
 * @see Catalog
 * @see CatalogReader
 * @see SAXCatalogReader
 * @see TextCatalogReader
 * @see DOMCatalogParser
 *
 * @author Norman Walsh
 * <a href="mailto:Norman.Walsh@Sun.COM">Norman.Walsh@Sun.COM</a>
 *
 */
public class DOMCatalogReader implements CatalogReader {
  /**
   * Mapping table from QNames to CatalogParser classes.
   *
   * <p>Each key in this hash table has the form "elementname"
   * or "{namespaceuri}elementname". The former is used if the
   * namespace URI is null.</p>
   * <p>
   *  从QNames到CatalogParser类的映射表。
   * 
   *  <p>此哈希表中的每个键都具有"elementname"或"{namespaceuri} elementname"的形式。如果命名空间URI为空,则使用前者。</p>
   * 
   */
  protected Hashtable namespaceMap = new Hashtable();

  /**
   * Add a new parser to the reader.
   *
   * <p>This method associates the specified parserClass with the
   * namespaceURI/rootElement names specified.</p>
   *
   * <p>
   *  向阅读器添加一个新的解析器。
   * 
   *  <p>此方法将指定的parserClass与指定的namespaceURI / rootElement名称相关联。</p>
   * 
   * 
   * @param namespaceURI The namespace URI. <em>Not</em> the prefix.
   * @param rootElement The name of the root element.
   * @param parserClass The name of the parserClass to instantiate
   * for this kind of catalog.
   */
  public void setCatalogParser(String namespaceURI,
                               String rootElement,
                               String parserClass) {
    if (namespaceURI == null) {
      namespaceMap.put(rootElement, parserClass);
    } else {
      namespaceMap.put("{"+namespaceURI+"}"+rootElement, parserClass);
    }
  }

  /**
   * Get the name of the parser class for a given catalog type.
   *
   * <p>This method returns the parserClass associated with the
   * namespaceURI/rootElement names specified.</p>
   *
   * <p>
   *  获取给定目录类型的解析器类的名称。
   * 
   *  <p>此方法返回与指定的namespaceURI / rootElement名称关联的parserClass。</p>
   * 
   * 
   * @param namespaceURI The namespace URI. <em>Not</em> the prefix.
   * @param rootElement The name of the root element.
   * @return The parser class.
   */
  public String getCatalogParser(String namespaceURI,
                                 String rootElement) {
    if (namespaceURI == null) {
      return (String) namespaceMap.get(rootElement);
    } else {
      return (String) namespaceMap.get("{"+namespaceURI+"}"+rootElement);
    }
  }

  /**
   * Null constructor; something for subclasses to call.
   * <p>
   *  空构造函数;东西为子类调用。
   * 
   */
  public DOMCatalogReader() { }

  /**
   * Read a catalog from an input stream.
   *
   * <p>This class reads a catalog from an input stream:</p>
   *
   * <ul>
   * <li>Based on the QName of the root element, it determines which
   * parser to instantiate for this catalog.</li>
   * <li>It constructs a DOM Document from the catalog and</li>
   * <li>For each child of the root node, it calls the parser's
   * parseCatalogEntry method. This method is expected to make
   * appropriate calls back into the catalog to add entries for the
   * entries in the catalog. It is free to do this in whatever manner
   * is appropriate (perhaps using just the node passed in, perhaps
   * wandering arbitrarily throughout the tree).</li>
   * </ul>
   *
   * <p>
   *  从输入流读取目录。
   * 
   *  <p>此类从输入流中读取目录：</p>
   * 
   * <ul>
   *  <li>根据根元素的QName,它会确定要为此目录实例化的解析器。</li> <li>从目录中构建DOM文档,</li> <li>根节点,它调用解析器的parseCatalogEntry方法。
   * 
   * @param catalog The catalog for which this reader is called.
   * @param is The input stream that is to be read.
   * @throws IOException if the URL cannot be read.
   * @throws UnknownCatalogFormatException if the catalog format is
   * not recognized.
   * @throws UnparseableCatalogException if the catalog cannot be parsed.
   * (For example, if it is supposed to be XML and isn't well-formed or
   * if the parser class cannot be instantiated.)
   */
  public void readCatalog(Catalog catalog, InputStream is)
    throws IOException, CatalogException {

    DocumentBuilderFactory factory = null;
    DocumentBuilder builder = null;

    factory = DocumentBuilderFactory.newInstance();
    factory.setNamespaceAware(false);
    factory.setValidating(false);
    try {
      builder = factory.newDocumentBuilder();
    } catch (ParserConfigurationException pce) {
      throw new CatalogException(CatalogException.UNPARSEABLE);
    }

    Document doc = null;

    try {
      doc = builder.parse(is);
    } catch (SAXException se) {
      throw new CatalogException(CatalogException.UNKNOWN_FORMAT);
    }

    Element root = doc.getDocumentElement();

    String namespaceURI = Namespaces.getNamespaceURI(root);
    String localName    = Namespaces.getLocalName(root);

    String domParserClass = getCatalogParser(namespaceURI,
                                             localName);

    if (domParserClass == null) {
      if (namespaceURI == null) {
        catalog.getCatalogManager().debug.message(1, "No Catalog parser for "
                                                  + localName);
      } else {
        catalog.getCatalogManager().debug.message(1, "No Catalog parser for "
                                                  + "{" + namespaceURI + "}"
                                                  + localName);
      }
      return;
    }

    DOMCatalogParser domParser = null;

    try {
      domParser = (DOMCatalogParser) ReflectUtil.forName(domParserClass).newInstance();
    } catch (ClassNotFoundException cnfe) {
      catalog.getCatalogManager().debug.message(1, "Cannot load XML Catalog Parser class", domParserClass);
      throw new CatalogException(CatalogException.UNPARSEABLE);
    } catch (InstantiationException ie) {
      catalog.getCatalogManager().debug.message(1, "Cannot instantiate XML Catalog Parser class", domParserClass);
      throw new CatalogException(CatalogException.UNPARSEABLE);
    } catch (IllegalAccessException iae) {
      catalog.getCatalogManager().debug.message(1, "Cannot access XML Catalog Parser class", domParserClass);
      throw new CatalogException(CatalogException.UNPARSEABLE);
    } catch (ClassCastException cce ) {
      catalog.getCatalogManager().debug.message(1, "Cannot cast XML Catalog Parser class", domParserClass);
      throw new CatalogException(CatalogException.UNPARSEABLE);
    }

    Node node = root.getFirstChild();
    while (node != null) {
      domParser.parseCatalogEntry(catalog, node);
      node = node.getNextSibling();
    }
  }

  /**
   * Read the catalog behind the specified URL.
   *
   * <p>
   * 此方法需要适当回调到目录中以为目录中的条目添加条目。它可以自由地以任何适当的方式(或许只使用传递的节点,也许在整个树中随意漫游)。</li>。
   * </ul>
   * 
   * 
   * @see #readCatalog(Catalog, InputStream)
   *
   * @param catalog The catalog for which we are reading.
   * @param fileUrl The URL of the document that should be read.
   *
   * @throws MalformedURLException if the specified URL cannot be
   * turned into a URL object.
   * @throws IOException if the URL cannot be read.
   * @throws UnknownCatalogFormatException if the catalog format is
   * not recognized.
   * @throws UnparseableCatalogException if the catalog cannot be parsed.
   * (For example, if it is supposed to be XML and isn't well-formed.)
   */
  public void readCatalog(Catalog catalog, String fileUrl)
    throws MalformedURLException, IOException, CatalogException {
    URL url = new URL(fileUrl);
    URLConnection urlCon = url.openConnection();
    readCatalog(catalog, urlCon.getInputStream());
  }
}
