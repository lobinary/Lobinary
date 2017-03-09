/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
// SAXCatalogReader.java - Read XML Catalog files

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
import com.sun.org.apache.xml.internal.resolver.CatalogManager;
import com.sun.org.apache.xml.internal.resolver.helpers.Debug;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Hashtable;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.AttributeList;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DocumentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import sun.reflect.misc.ReflectUtil;

/**
 * A SAX-based CatalogReader.
 *
 * <p>This class is used to read XML Catalogs using the SAX. This reader
 * has an advantage over the DOM-based reader in that it functions on
 * the stream of SAX events. It has the disadvantage
 * that it cannot look around in the tree.</p>
 *
 * <p>Since the choice of CatalogReaders (in the InputStream case) can only
 * be made on the basis of MIME type, the following problem occurs: only
 * one CatalogReader can exist for all XML mime types. In order to get
 * around this problem, the SAXCatalogReader relies on a set of external
 * CatalogParsers to actually build the catalog.</p>
 *
 * <p>The selection of CatalogParsers is made on the basis of the QName
 * of the root element of the document.</p>
 *
 * <p>
 *  基于SAX的CatalogReader。
 * 
 *  <p>此类用于使用SAX读取XML目录。该读取器具有优于基于DOM的读取器的优点,因为其在SAX事件流上起作用。它的缺点是它不能在树中查找。</p>
 * 
 *  <p>由于CatalogReaders(在InputStream情况下)的选择只能基于MIME类型,所以会出现以下问题：对于所有XML MIME类型,只能存在一个CatalogReader。
 * 为了解决这个问题,SAXCatalogReader依赖一组外部CatalogParsers来实际构建目录。</p>。
 * 
 *  <p> CatalogParsers的选择基于文档根元素的QName。</p>
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
public class SAXCatalogReader implements CatalogReader, ContentHandler, DocumentHandler {
  /** The SAX Parser Factory */
  protected SAXParserFactory parserFactory = null;

  /** The SAX Parser Class */
  protected String parserClass = null;

  /**
     * Mapping table from QNames to CatalogParser classes.
     *
     * <p>Each key in this hash table has the form "elementname"
     * or "{namespaceuri}elementname". The former is used if the
     * namespace URI is null.</p>
     * <p>
     * 从QNames到CatalogParser类的映射表。
     * 
     *  <p>此哈希表中的每个键都具有"elementname"或"{namespaceuri} elementname"的形式。如果命名空间URI为空,则使用前者。</p>
     * 
     */
  protected Hashtable namespaceMap = new Hashtable();

  /** The parser in use for the current catalog. */
  private SAXCatalogParser saxParser = null;

  /** Set if something goes horribly wrong. It allows the class to
     * ignore the rest of the events that are received.
     * <p>
     *  忽略接收的其余事件。
     * 
     */
  private boolean abandonHope = false;

  /** The Catalog that we're working for. */
  private Catalog catalog;

  /** Set the XML SAX Parser Factory.
  /* <p>
   */
  public void setParserFactory(SAXParserFactory parserFactory) {
    this.parserFactory = parserFactory;
  }

  /** Set the XML SAX Parser Class
  /* <p>
   */
  public void setParserClass(String parserClass) {
    this.parserClass = parserClass;
  }

  /** Get the parser factory currently in use. */
  public SAXParserFactory getParserFactory() {
    return parserFactory;
  }

  /** Get the parser class currently in use. */
  public String getParserClass() {
    return parserClass;
  }

  /** The debug class to use for this reader.
   *
   * This is a bit of a hack. Anyway, whenever we read for a catalog,
   * we extract the debug object
   * from the catalog's manager so that we can use it to print messages.
   *
   * In production, we don't really expect any messages so it doesn't
   * really matter. But it's still a bit of a hack.
   * <p>
   *  这是一个黑客。无论如何,每当我们读一个目录,我们从目录的管理器中提取调试对象,以便我们可以使用它来打印消息。
   * 
   *  在生产中,我们并不真的期望任何消息,所以它并不重要。但它仍然有点黑客。
   * 
   */
  protected Debug debug = CatalogManager.getStaticManager().debug;

  /** The constructor */
  public SAXCatalogReader() {
    parserFactory = null;
    parserClass = null;
  }

  /** The constructor */
  public SAXCatalogReader(SAXParserFactory parserFactory) {
    this.parserFactory = parserFactory;
  }

  /** The constructor */
  public SAXCatalogReader(String parserClass) {
    this.parserClass = parserClass;
  }

  /** Set the SAXCatalogParser class for the given namespace/root
     * element type.
     * <p>
     *  元素类型。
     * 
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

  /** Get the SAXCatalogParser class for the given namespace/root
     * element type.
     * <p>
     *  元素类型。
     * 
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
   * Parse an XML Catalog file.
   *
   * <p>
   *  解析XML目录文件。
   * 
   * 
   * @param catalog The catalog to which this catalog file belongs
   * @param fileUrl The URL or filename of the catalog file to process
   *
   * @throws MalformedURLException Improper fileUrl
   * @throws IOException Error reading catalog file
   */
  public void readCatalog(Catalog catalog, String fileUrl)
    throws MalformedURLException, IOException,
           CatalogException {

    URL url = null;

    try {
      url = new URL(fileUrl);
    } catch (MalformedURLException e) {
      url = new URL("file:///" + fileUrl);
    }

    debug = catalog.getCatalogManager().debug;

    try {
      URLConnection urlCon = url.openConnection();
      readCatalog(catalog, urlCon.getInputStream());
    } catch (FileNotFoundException e) {
      catalog.getCatalogManager().debug.message(1, "Failed to load catalog, file not found",
                    url.toString());
    }
  }

  /**
   * Parse an XML Catalog stream.
   *
   * <p>
   *  解析XML目录流。
   * 
   * 
   * @param catalog The catalog to which this catalog file belongs
   * @param is The input stream from which the catalog will be read
   *
   * @throws MalformedURLException Improper fileUrl
   * @throws IOException Error reading catalog file
   * @throws CatalogException A Catalog exception
   */
  public void readCatalog(Catalog catalog, InputStream is)
    throws IOException, CatalogException {

    // Create an instance of the parser
    if (parserFactory == null && parserClass == null) {
      debug.message(1, "Cannot read SAX catalog without a parser");
      throw new CatalogException(CatalogException.UNPARSEABLE);
    }

    debug = catalog.getCatalogManager().debug;
    EntityResolver bResolver = catalog.getCatalogManager().getBootstrapResolver();

    this.catalog = catalog;

    try {
      if (parserFactory != null) {
        SAXParser parser = parserFactory.newSAXParser();
        SAXParserHandler spHandler = new SAXParserHandler();
        spHandler.setContentHandler(this);
        if (bResolver != null) {
          spHandler.setEntityResolver(bResolver);
        }
        parser.parse(new InputSource(is), spHandler);
      } else {
        Parser parser = (Parser) ReflectUtil.forName(parserClass).newInstance();
        parser.setDocumentHandler(this);
        if (bResolver != null) {
          parser.setEntityResolver(bResolver);
        }
        parser.parse(new InputSource(is));
      }
    } catch (ClassNotFoundException cnfe) {
      throw new CatalogException(CatalogException.UNPARSEABLE);
    } catch (IllegalAccessException iae) {
      throw new CatalogException(CatalogException.UNPARSEABLE);
    } catch (InstantiationException ie) {
      throw new CatalogException(CatalogException.UNPARSEABLE);
    } catch (ParserConfigurationException pce) {
      throw new CatalogException(CatalogException.UNKNOWN_FORMAT);
    } catch (SAXException se) {
      Exception e = se.getException();
      // FIXME: there must be a better way
      UnknownHostException uhe = new UnknownHostException();
      FileNotFoundException fnfe = new FileNotFoundException();
      if (e != null) {
        if (e.getClass() == uhe.getClass()) {
          throw new CatalogException(CatalogException.PARSE_FAILED,
                                     e.toString());
        } else if (e.getClass() == fnfe.getClass()) {
          throw new CatalogException(CatalogException.PARSE_FAILED,
                                     e.toString());
        }
      }
      throw new CatalogException(se);
    }
  }

  // ----------------------------------------------------------------------
  // Implement the SAX ContentHandler interface

  /** The SAX <code>setDocumentLocator</code> method. Does nothing. */
  public void setDocumentLocator (Locator locator) {
    if (saxParser != null) {
      saxParser.setDocumentLocator(locator);
    }
  }

  /** The SAX <code>startDocument</code> method. Does nothing. */
  public void startDocument () throws SAXException {
    saxParser = null;
    abandonHope = false;
    return;
  }

  /** The SAX <code>endDocument</code> method. Does nothing. */
  public void endDocument ()throws SAXException {
    if (saxParser != null) {
      saxParser.endDocument();
    }
  }

  /**
   * The SAX <code>startElement</code> method.
   *
   * <p>The catalog parser is selected based on the namespace of the
   * first element encountered in the catalog.</p>
   * <p>
   *  SAX <code> startElement </code>方法。
   * 
   *  <p>目录解析器是根据目录中遇到的第一个元素的命名空间选择的。</p>
   * 
   */
  public void startElement (String name,
                            AttributeList atts)
    throws SAXException {

    if (abandonHope) {
      return;
    }

    if (saxParser == null) {
      String prefix = "";
      if (name.indexOf(':') > 0) {
        prefix = name.substring(0, name.indexOf(':'));
      }

      String localName = name;
      if (localName.indexOf(':') > 0) {
        localName = localName.substring(localName.indexOf(':')+1);
      }

      String namespaceURI = null;
      if (prefix.equals("")) {
        namespaceURI = atts.getValue("xmlns");
      } else {
        namespaceURI = atts.getValue("xmlns:" + prefix);
      }

      String saxParserClass = getCatalogParser(namespaceURI,
                                               localName);

      if (saxParserClass == null) {
        abandonHope = true;
        if (namespaceURI == null) {
          debug.message(2, "No Catalog parser for " + name);
        } else {
          debug.message(2, "No Catalog parser for "
                        + "{" + namespaceURI + "}"
                        + name);
        }
        return;
      }

      try {
        saxParser = (SAXCatalogParser)
          ReflectUtil.forName(saxParserClass).newInstance();

        saxParser.setCatalog(catalog);
        saxParser.startDocument();
        saxParser.startElement(name, atts);
      } catch (ClassNotFoundException cnfe) {
        saxParser = null;
        abandonHope = true;
        debug.message(2, cnfe.toString());
      } catch (InstantiationException ie) {
        saxParser = null;
        abandonHope = true;
        debug.message(2, ie.toString());
      } catch (IllegalAccessException iae) {
        saxParser = null;
        abandonHope = true;
        debug.message(2, iae.toString());
      } catch (ClassCastException cce ) {
        saxParser = null;
        abandonHope = true;
        debug.message(2, cce.toString());
      }
    } else {
      saxParser.startElement(name, atts);
    }
  }

  /**
   * The SAX2 <code>startElement</code> method.
   *
   * <p>The catalog parser is selected based on the namespace of the
   * first element encountered in the catalog.</p>
   * <p>
   *  SAX2 <code> startElement </code>方法。
   * 
   */
  public void startElement (String namespaceURI,
                            String localName,
                            String qName,
                            Attributes atts)
    throws SAXException {

    if (abandonHope) {
      return;
    }

    if (saxParser == null) {
      String saxParserClass = getCatalogParser(namespaceURI,
                                               localName);

      if (saxParserClass == null) {
        abandonHope = true;
        if (namespaceURI == null) {
          debug.message(2, "No Catalog parser for " + localName);
        } else {
          debug.message(2, "No Catalog parser for "
                        + "{" + namespaceURI + "}"
                        + localName);
        }
        return;
      }

      try {
        saxParser = (SAXCatalogParser)
          ReflectUtil.forName(saxParserClass).newInstance();

        saxParser.setCatalog(catalog);
        saxParser.startDocument();
        saxParser.startElement(namespaceURI, localName, qName, atts);
      } catch (ClassNotFoundException cnfe) {
        saxParser = null;
        abandonHope = true;
        debug.message(2, cnfe.toString());
      } catch (InstantiationException ie) {
        saxParser = null;
        abandonHope = true;
        debug.message(2, ie.toString());
      } catch (IllegalAccessException iae) {
        saxParser = null;
        abandonHope = true;
        debug.message(2, iae.toString());
      } catch (ClassCastException cce ) {
        saxParser = null;
        abandonHope = true;
        debug.message(2, cce.toString());
      }
    } else {
      saxParser.startElement(namespaceURI, localName, qName, atts);
    }
  }

  /** The SAX <code>endElement</code> method. Does nothing. */
  public void endElement (String name) throws SAXException {
    if (saxParser != null) {
      saxParser.endElement(name);
    }
  }

  /** The SAX2 <code>endElement</code> method. Does nothing. */
  public void endElement (String namespaceURI,
                          String localName,
                          String qName) throws SAXException {
    if (saxParser != null) {
      saxParser.endElement(namespaceURI, localName, qName);
    }
  }

  /** The SAX <code>characters</code> method. Does nothing. */
  public void characters (char ch[], int start, int length)
    throws SAXException {
    if (saxParser != null) {
      saxParser.characters(ch, start, length);
    }
  }

  /** The SAX <code>ignorableWhitespace</code> method. Does nothing. */
  public void ignorableWhitespace (char ch[], int start, int length)
    throws SAXException {
    if (saxParser != null) {
      saxParser.ignorableWhitespace(ch, start, length);
    }
  }

  /** The SAX <code>processingInstruction</code> method. Does nothing. */
  public void processingInstruction (String target, String data)
    throws SAXException {
    if (saxParser != null) {
      saxParser.processingInstruction(target, data);
    }
  }

  /** The SAX <code>startPrefixMapping</code> method. Does nothing. */
  public void startPrefixMapping (String prefix, String uri)
    throws SAXException {
    if (saxParser != null) {
      saxParser.startPrefixMapping (prefix, uri);
    }
  }

  /** The SAX <code>endPrefixMapping</code> method. Does nothing. */
  public void endPrefixMapping (String prefix)
    throws SAXException {
    if (saxParser != null) {
      saxParser.endPrefixMapping (prefix);
    }
  }

  /** The SAX <code>skippedentity</code> method. Does nothing. */
  public void skippedEntity (String name)
    throws SAXException {
    if (saxParser != null) {
      saxParser.skippedEntity(name);
    }
  }
}
