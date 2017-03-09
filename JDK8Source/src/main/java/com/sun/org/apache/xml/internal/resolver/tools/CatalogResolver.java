/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
// CatalogResolver.java - A SAX EntityResolver/JAXP URI Resolver

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

import com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.MalformedURLException;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.InputSource;
import org.xml.sax.EntityResolver;

import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.Source;
import javax.xml.transform.URIResolver;
import javax.xml.transform.TransformerException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import com.sun.org.apache.xml.internal.resolver.Catalog;
import com.sun.org.apache.xml.internal.resolver.CatalogManager;
import com.sun.org.apache.xml.internal.resolver.helpers.FileURL;

/**
 * A SAX EntityResolver/JAXP URIResolver that uses catalogs.
 *
 * <p>This class implements both a SAX EntityResolver and a JAXP URIResolver.
 * </p>
 *
 * <p>This resolver understands OASIS TR9401 catalogs, XCatalogs, and the
 * current working draft of the OASIS Entity Resolution Technical
 * Committee specification.</p>
 *
 * <p>
 *  使用目录的SAX EntityResolver / JAXP URIResolver。
 * 
 *  <p>此类实现了SAX EntityResolver和JAXP URIResolver。
 * </p>
 * 
 *  <p>此解析器理解OASIS TR9401目录,XCatalog以及OASIS实体解析技术委员会规范的当前工作草案。</p>
 * 
 * 
 * @see Catalog
 * @see org.xml.sax.EntityResolver
 * @see javax.xml.transform.URIResolver
 *
 * @author Norman Walsh
 * <a href="mailto:Norman.Walsh@Sun.COM">Norman.Walsh@Sun.COM</a>
 *
 * @version 1.0
 */
public class CatalogResolver implements EntityResolver, URIResolver {
  /** Make the parser Namespace aware? */
  public boolean namespaceAware = true;

  /** Make the parser validating? */
  public boolean validating = false;

  /** The underlying catalog */
  private Catalog catalog = null;

  /** The catalog manager */
  private CatalogManager catalogManager = CatalogManager.getStaticManager();

  /** Constructor */
  public CatalogResolver() {
    initializeCatalogs(false);
  }

  /** Constructor */
  public CatalogResolver(boolean privateCatalog) {
    initializeCatalogs(privateCatalog);
  }

  /** Constructor */
  public CatalogResolver(CatalogManager manager) {
    catalogManager = manager;
    initializeCatalogs(!catalogManager.getUseStaticCatalog());
  }

  /** Initialize catalog */
  private void initializeCatalogs(boolean privateCatalog) {
    catalog = catalogManager.getCatalog();
  }

  /** Return the underlying catalog */
  public Catalog getCatalog() {
    return catalog;
  }

  /**
   * Implements the guts of the <code>resolveEntity</code> method
   * for the SAX interface.
   *
   * <p>Presented with an optional public identifier and a system
   * identifier, this function attempts to locate a mapping in the
   * catalogs.</p>
   *
   * <p>If such a mapping is found, it is returned.  If no mapping is
   * found, null is returned.</p>
   *
   * <p>
   *  实现SAX接口的<code> resolveEntity </code>方法的内容。
   * 
   *  <p>提供可选的公共标识符和系统标识符,此函数尝试查找目录中的映射。</p>
   * 
   *  <p>如果找到这样的映射,则返回。如果找不到映射,则返回null。</p>
   * 
   * 
   * @param publicId  The public identifier for the entity in question.
   * This may be null.
   *
   * @param systemId  The system identifier for the entity in question.
   * XML requires a system identifier on all external entities, so this
   * value is always specified.
   *
   * @return The resolved identifier (a URI reference).
   */
  public String getResolvedEntity (String publicId, String systemId) {
    String resolved = null;

    if (catalog == null) {
      catalogManager.debug.message(1, "Catalog resolution attempted with null catalog; ignored");
      return null;
    }

    if (systemId != null) {
      try {
        resolved = catalog.resolveSystem(systemId);
      } catch (MalformedURLException me) {
        catalogManager.debug.message(1, "Malformed URL exception trying to resolve",
                      publicId);
        resolved = null;
      } catch (IOException ie) {
        catalogManager.debug.message(1, "I/O exception trying to resolve", publicId);
        resolved = null;
      }
    }

    if (resolved == null) {
      if (publicId != null) {
        try {
          resolved = catalog.resolvePublic(publicId, systemId);
        } catch (MalformedURLException me) {
          catalogManager.debug.message(1, "Malformed URL exception trying to resolve",
                        publicId);
        } catch (IOException ie) {
          catalogManager.debug.message(1, "I/O exception trying to resolve", publicId);
        }
      }

      if (resolved != null) {
        catalogManager.debug.message(2, "Resolved public", publicId, resolved);
      }
    } else {
      catalogManager.debug.message(2, "Resolved system", systemId, resolved);
    }

    return resolved;
  }

  /**
   * Implements the <code>resolveEntity</code> method
   * for the SAX interface.
   *
   * <p>Presented with an optional public identifier and a system
   * identifier, this function attempts to locate a mapping in the
   * catalogs.</p>
   *
   * <p>If such a mapping is found, the resolver attempts to open
   * the mapped value as an InputSource and return it. Exceptions are
   * ignored and null is returned if the mapped value cannot be opened
   * as an input source.</p>
   *
   * <p>If no mapping is found (or an error occurs attempting to open
   * the mapped value as an input source), null is returned and the system
   * will use the specified system identifier as if no entityResolver
   * was specified.</p>
   *
   * <p>
   *  实现SAX接口的<code> resolveEntity </code>方法。
   * 
   * <p>提供可选的公共标识符和系统标识符,此函数尝试查找目录中的映射。</p>
   * 
   *  <p>如果找到这样的映射,解析器会尝试将映射的值作为InputSource打开并返回。如果映射值无法作为输入源打开,则会忽略异常,并返回null。</p>
   * 
   *  <p>如果未找到映射(或尝试打开作为输入源的映射值时发生错误),则返回null,并且系统将使用指定的系统标识符,如同没有指定entityResolver。</p>
   * 
   * 
   * @param publicId  The public identifier for the entity in question.
   * This may be null.
   *
   * @param systemId  The system identifier for the entity in question.
   * XML requires a system identifier on all external entities, so this
   * value is always specified.
   *
   * @return An InputSource for the mapped identifier, or null.
   */
  public InputSource resolveEntity (String publicId, String systemId) {
    String resolved = getResolvedEntity(publicId, systemId);

    if (resolved != null) {
      try {
        InputSource iSource = new InputSource(resolved);
        iSource.setPublicId(publicId);

        // Ideally this method would not attempt to open the
        // InputStream, but there is a bug (in Xerces, at least)
        // that causes the parser to mistakenly open the wrong
        // system identifier if the returned InputSource does
        // not have a byteStream.
        //
        // It could be argued that we still shouldn't do this here,
        // but since the purpose of calling the entityResolver is
        // almost certainly to open the input stream, it seems to
        // do little harm.
        //
        URL url = new URL(resolved);
        InputStream iStream = url.openStream();
        iSource.setByteStream(iStream);

        return iSource;
      } catch (Exception e) {
        catalogManager.debug.message(1, "Failed to create InputSource", resolved);
        return null;
      }
    }

    return null;
  }

  /** JAXP URIResolver API */
  public Source resolve(String href, String base)
    throws TransformerException {

    String uri = href;
    String fragment = null;
    int hashPos = href.indexOf("#");
    if (hashPos >= 0) {
      uri = href.substring(0, hashPos);
      fragment = href.substring(hashPos+1);
    }

    String result = null;

    try {
      result = catalog.resolveURI(href);
    } catch (Exception e) {
      // nop;
    }

    if (result == null) {
      try {
        URL url = null;

        if (base==null) {
          url = new URL(uri);
          result = url.toString();
        } else {
          URL baseURL = new URL(base);
          url = (href.length()==0 ? baseURL : new URL(baseURL, uri));
          result = url.toString();
        }
      } catch (java.net.MalformedURLException mue) {
        // try to make an absolute URI from the current base
        String absBase = makeAbsolute(base);
        if (!absBase.equals(base)) {
          // don't bother if the absBase isn't different!
          return resolve(href, absBase);
        } else {
          throw new TransformerException("Malformed URL "
                                         + href + "(base " + base + ")",
                                         mue);
        }
      }
    }

    catalogManager.debug.message(2, "Resolved URI", href, result);

    SAXSource source = new SAXSource();
    source.setInputSource(new InputSource(result));
    setEntityResolver(source);
    return source;
  }

  /**
   * <p>Establish an entityResolver for newly resolved URIs.</p>
   *
   * <p>This is called from the URIResolver to set an EntityResolver
   * on the SAX parser to be used for new XML documents that are
   * encountered as a result of the document() function, xsl:import,
   * or xsl:include.  This is done because the XSLT processor calls
   * out to the SAXParserFactory itself to create a new SAXParser to
   * parse the new document.  The new parser does not automatically
   * inherit the EntityResolver of the original (although arguably
   * it should).  See below:</p>
   *
   * <tt>"If an application wants to set the ErrorHandler or
   * EntityResolver for an XMLReader used during a transformation,
   * it should use a URIResolver to return the SAXSource which
   * provides (with getXMLReader) a reference to the XMLReader"</tt>
   *
   * <p>...quoted from page 118 of the Java API for XML
   * Processing 1.1 specification</p>
   *
   * <p>
   *  <p>为新解析的URI建立entityResolver。</p>
   * 
   *  <p>这是从URIResolver中调用以在SAX解析器上设置一个EntityResolver,以用于document()函数xsl：import或xsl：include所遇到的新XML文档。
   * 这是因为XSLT处理器调用SAXParserFactory本身创建一个新的SAXParser来解析新文档。新的解析器不会自动继承原始的EntityResolver(尽管可以说它应该)。
   * 见下文：</p>。
   * 
   */
  private void setEntityResolver(SAXSource source) throws TransformerException {
    XMLReader reader = source.getXMLReader();
    if (reader == null) {
      SAXParserFactory spFactory = catalogManager.useServicesMechanism() ?
                    SAXParserFactory.newInstance() : new SAXParserFactoryImpl();
      spFactory.setNamespaceAware(true);
      try {
        reader = spFactory.newSAXParser().getXMLReader();
      }
      catch (ParserConfigurationException ex) {
        throw new TransformerException(ex);
      }
      catch (SAXException ex) {
        throw new TransformerException(ex);
      }
    }
    reader.setEntityResolver(this);
    source.setXMLReader(reader);
  }

  /** Attempt to construct an absolute URI */
  private String makeAbsolute(String uri) {
    if (uri == null) {
      uri = "";
    }

    try {
      URL url = new URL(uri);
      return url.toString();
    } catch (MalformedURLException mue) {
      try {
        URL fileURL = FileURL.makeURL(uri);
        return fileURL.toString();
      } catch (MalformedURLException mue2) {
        // bail
        return uri;
      }
    }
  }
}
