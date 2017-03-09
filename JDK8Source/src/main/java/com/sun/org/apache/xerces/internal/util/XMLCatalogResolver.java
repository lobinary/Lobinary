/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2004,2005 The Apache Software Foundation.
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
 *  版权所有2004,2005 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.util;

import java.io.IOException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ext.EntityResolver2;

import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

import javax.xml.parsers.SAXParserFactory;

import com.sun.org.apache.xerces.internal.dom.DOMInputImpl;
import com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl;

import com.sun.org.apache.xerces.internal.xni.XNIException;
import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;

import com.sun.org.apache.xerces.internal.xni.parser.XMLEntityResolver;
import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;

import com.sun.org.apache.xml.internal.resolver.Catalog;
import com.sun.org.apache.xml.internal.resolver.CatalogManager;
import com.sun.org.apache.xml.internal.resolver.readers.OASISXMLCatalogReader;
import com.sun.org.apache.xml.internal.resolver.readers.SAXCatalogReader;

/**
 * <p>The catalog resolver handles the resolution of external
 * identifiers and URI references through XML catalogs. This
 * component supports XML catalogs defined by the
 * <a href="http://www.oasis-open.org/committees/entity/spec.html">
 * OASIS XML Catalogs Specification</a>. It encapsulates the
 * <a href="http://xml.apache.org/commons/">XML Commons</a> resolver.
 * An instance of this class may be registered on the parser
 * as a SAX entity resolver, as a DOM LSResourceResolver or
 * as an XNI entity resolver by setting the property
 * (http://apache.org/xml/properties/internal/entity-resolver).</p>
 *
 * <p>It is intended that this class may be used standalone to perform
 * catalog resolution outside of a parsing context. It may be shared
 * between several parsers and the application.</p>
 *
 * <p>
 *  <p>目录解析器通过XML目录处理外部标识符和URI引用的解析。此组件支持由。定义的XML目录
 * <a href="http://www.oasis-open.org/committees/entity/spec.html">
 *  OASIS XML目录规范</a>。它封装了<a href="http://xml.apache.org/commons/"> XML Commons </a>解析器。
 * 此类的实例可以作为SAX实体解析器,作为DOM LSResourceResolver或作为XNI实体解析器在解析器上注册(通过设置属性(http://apache.org/xml/properties/
 * internal/entity-resolver) 。
 *  OASIS XML目录规范</a>。它封装了<a href="http://xml.apache.org/commons/"> XML Commons </a>解析器。</p>。
 * 
 *  <p>这个类可以单独用于在解析上下文之外执行目录解析。它可以在几个解析器和应用程序之间共享。</p>
 * 
 * 
 * @author Michael Glavassevich, IBM
 *
 */
public class XMLCatalogResolver
    implements XMLEntityResolver, EntityResolver2, LSResourceResolver {

    /** Internal catalog manager for Apache catalogs. **/
    private CatalogManager fResolverCatalogManager = null;

    /** Internal catalog structure. **/
    private Catalog fCatalog = null;

    /** An array of catalog URIs. **/
    private String [] fCatalogsList = null;

    /**
     * Indicates whether the list of catalogs has
     * changed since it was processed.
     * <p>
     *  指示目录列表自处理后是否已更改。
     * 
     */
    private boolean fCatalogsChanged = true;

    /** Application specified prefer public setting. **/
    private boolean fPreferPublic = true;

    /**
     * Indicates whether the application desires that
     * the parser or some other component performing catalog
     * resolution should use the literal system identifier
     * instead of the expanded system identifier.
     * <p>
     * 指示应用程序是否希望解析器或执行目录解析的其他组件应使用文字系统标识符而不是扩展系统标识符。
     * 
     */
    private boolean fUseLiteralSystemId = true;

    /**
     * <p>Constructs a catalog resolver with a default configuration.</p>
     * <p>
     *  <p>使用默认配置构造目录解析器。</p>
     * 
     */
    public XMLCatalogResolver () {
        this(null, true);
    }

    /**
     * <p>Constructs a catalog resolver with the given
     * list of entry files.</p>
     *
     * <p>
     *  <p>使用给定的条目文件列表构造目录解析器。</p>
     * 
     * 
     * @param catalogs an ordered array list of absolute URIs
     */
    public XMLCatalogResolver (String [] catalogs) {
        this(catalogs, true);
    }

    /**
     * <p>Constructs a catalog resolver with the given
     * list of entry files and the preference for whether
     * system or public matches are preferred.</p>
     *
     * <p>
     *  <p>使用给定的条目文件列表构建目录解析器,并优先选择系统或公共匹配。</p>
     * 
     * 
     * @param catalogs an ordered array list of absolute URIs
     * @param preferPublic the prefer public setting
     */
    public XMLCatalogResolver (String [] catalogs, boolean preferPublic) {
        init(catalogs, preferPublic);
    }

    /**
     * <p>Returns the initial list of catalog entry files.</p>
     *
     * <p>
     *  <p>返回商品文件的初始列表。</p>
     * 
     * 
     * @return the initial list of catalog entry files
     */
    public final synchronized String [] getCatalogList () {
        return (fCatalogsList != null)
            ? (String[]) fCatalogsList.clone() : null;
    }

    /**
     * <p>Sets the initial list of catalog entry files.
     * If there were any catalog mappings cached from
     * the previous list they will be replaced by catalog
     * mappings from the new list the next time the catalog
     * is queried.</p>
     *
     * <p>
     *  <p>设置商品文件的初始列表。如果存在从上一个列表缓存的任何目录映射,则下次查询目录时,它们将被来自新列表的目录映射替换。</p>
     * 
     * 
     * @param catalogs an ordered array list of absolute URIs
     */
    public final synchronized void setCatalogList (String [] catalogs) {
        fCatalogsChanged = true;
        fCatalogsList = (catalogs != null)
            ? (String[]) catalogs.clone() : null;
    }

    /**
     * <p>Forces the cache of catalog mappings to be cleared.</p>
     * <p>
     *  <p>强制清除目录映射的高速缓存。</p>
     * 
     */
    public final synchronized void clear () {
        fCatalog = null;
    }

    /**
     * <p>Returns the preference for whether system or public
     * matches are preferred. This is used in the absence
     * of any occurence of the <code>prefer</code> attribute
     * on the <code>catalog</code> entry of a catalog. If this
     * property has not yet been explicitly set its value is
     * <code>true</code>.</p>
     *
     * <p>
     *  <p>返回是否首选系统或公共匹配的首选项。这是在目录的<code>目录</code>条目上没有出现任何<code> prefer </code>属性的情况下使用的。
     * 如果此属性尚未显式设置,其值为<code> true </code>。</p>。
     * 
     * 
     * @return the prefer public setting
     */
    public final boolean getPreferPublic () {
        return fPreferPublic;
    }

    /**
     * <p>Sets the preference for whether system or public
     * matches are preferred. This is used in the absence
     * of any occurence of the <code>prefer</code> attribute
     * on the <code>catalog</code> entry of a catalog.</p>
     *
     * <p>
     *  <p>设置是否首选系统或公共匹配。这是在目录的<code>目录</code>条目中没有出现<code> prefer </code>属性的情况下使用的。</p>
     * 
     * 
     * @param preferPublic the prefer public setting
     */
    public final void setPreferPublic (boolean preferPublic) {
        fPreferPublic = preferPublic;
        fResolverCatalogManager.setPreferPublic(preferPublic);
    }

    /**
     * <p>Returns the preference for whether the literal system
     * identifier should be used when resolving system
     * identifiers when both it and the expanded system
     * identifier are available. If this property has not yet
     * been explicitly set its value is <code>true</code>.</p>
     *
     * <p>
     * <p>返回在系统标识符和扩展系统标识符都可用时解析系统标识符时是否应使用文字系统标识符的首选项。如果此属性尚未显式设置,其值为<code> true </code>。</p>
     * 
     * 
     * @return the preference for using literal system identifers
     * for catalog resolution
     *
     * @see #setUseLiteralSystemId
     */
    public final boolean getUseLiteralSystemId () {
        return fUseLiteralSystemId;
    }

    /**
     * <p>Sets the preference for whether the literal system
     * identifier should be used when resolving system
     * identifiers when both it and the expanded system
     * identifier are available.</p>
     *
     * <p>The literal system identifier is the URI as it was
     * provided before absolutization. It may be embedded within
     * an entity. It may be provided externally or it may be the
     * result of redirection. For example, redirection may
     * have come from the protocol level through HTTP or from
     * an application's entity resolver.</p>
     *
     * <p>The expanded system identifier is an absolute URI
     * which is the result of resolving the literal system
     * identifier against a base URI.</p>
     *
     * <p>
     *  <p>设置在系统标识符和扩展系统标识符都可用时解析系统标识符时是否应使用文字系统标识符的首选项。</p>
     * 
     *  <p>文字系统标识符是在绝对化之前提供的URI。它可以嵌入在实体中。它可以在外部提供,或者可以是重定向的结果。例如,重定向可能来自协议级别,通过HTTP或应用程序的实体解析器。</p>
     * 
     *  <p>展开的系统标识符是绝对URI,它是根据基本URI解析字面系统标识符的结果。</p>
     * 
     * 
     * @param useLiteralSystemId the preference for using
     * literal system identifers for catalog resolution
     */
    public final void setUseLiteralSystemId (boolean useLiteralSystemId) {
        fUseLiteralSystemId = useLiteralSystemId;
    }

    /**
     * <p>Resolves an external entity. If the entity cannot be
     * resolved, this method should return <code>null</code>. This
     * method returns an input source if an entry was found in the
     * catalog for the given external identifier. It should be
     * overrided if other behaviour is required.</p>
     *
     * <p>
     *  <p>解析外部实体。如果实体无法解析,此方法应返回<code> null </code>。如果在目录中找到给定外部标识符的条目,此方法将返回输入源。如果需要其他行为,应该覆盖它。</p>
     * 
     * 
     * @param publicId the public identifier, or <code>null</code> if none was supplied
     * @param systemId the system identifier
     *
     * @throws SAXException any SAX exception, possibly wrapping another exception
     * @throws IOException thrown if some i/o error occurs
     */
    public InputSource resolveEntity(String publicId, String systemId)
         throws SAXException, IOException {

        String resolvedId = null;
        if (publicId != null && systemId != null) {
            resolvedId = resolvePublic(publicId, systemId);
        }
        else if (systemId != null) {
            resolvedId = resolveSystem(systemId);
        }

        if (resolvedId != null) {
            InputSource source = new InputSource(resolvedId);
            source.setPublicId(publicId);
            return source;
        }
        return null;
    }

     /**
      * <p>Resolves an external entity. If the entity cannot be
      * resolved, this method should return <code>null</code>. This
      * method returns an input source if an entry was found in the
      * catalog for the given external identifier. It should be
      * overrided if other behaviour is required.</p>
      *
      * <p>
      *  <p>解析外部实体。如果实体无法解析,此方法应返回<code> null </code>。如果在目录中找到给定外部标识符的条目,此方法将返回输入源。如果需要其他行为,应该覆盖它。</p>
      * 
      * 
      * @param name the identifier of the external entity
      * @param publicId the public identifier, or <code>null</code> if none was supplied
      * @param baseURI the URI with respect to which relative systemIDs are interpreted.
      * @param systemId the system identifier
      *
      * @throws SAXException any SAX exception, possibly wrapping another exception
      * @throws IOException thrown if some i/o error occurs
      */
     public InputSource resolveEntity(String name, String publicId,
         String baseURI, String systemId) throws SAXException, IOException {

         String resolvedId = null;

         if (!getUseLiteralSystemId() && baseURI != null) {
             // Attempt to resolve the system identifier against the base URI.
             try {
                 URI uri = new URI(new URI(baseURI), systemId);
                 systemId = uri.toString();
             }
             // Ignore the exception. Fallback to the literal system identifier.
             catch (URI.MalformedURIException ex) {}
         }

         if (publicId != null && systemId != null) {
             resolvedId = resolvePublic(publicId, systemId);
         }
         else if (systemId != null) {
             resolvedId = resolveSystem(systemId);
         }

         if (resolvedId != null) {
             InputSource source = new InputSource(resolvedId);
             source.setPublicId(publicId);
             return source;
         }
         return null;
    }

     /**
      * <p>Locates an external subset for documents which do not explicitly
      * provide one. This method always returns <code>null</code>. It
      * should be overrided if other behaviour is required.</p>
      *
      * <p>
      * <p>为未明确提供文档的文档找到外部子集。此方法总是返回<code> null </code>。如果需要其他行为,应该覆盖它。</p>
      * 
      * 
      * @param name the identifier of the document root element
      * @param baseURI the document's base URI
      *
      * @throws SAXException any SAX exception, possibly wrapping another exception
      * @throws IOException thrown if some i/o error occurs
      */
     public InputSource getExternalSubset(String name, String baseURI)
         throws SAXException, IOException {
         return null;
     }

    /**
     * <p>Resolves a resource using the catalog. This method interprets that
     * the namespace URI corresponds to uri entries in the catalog.
     * Where both a namespace and an external identifier exist, the namespace
     * takes precedence.</p>
     *
     * <p>
     *  <p>使用目录解析资源。此方法解释名称空间URI对应于目录中的uri条目。如果存在命名空间和外部标识符,则命名空间优先。</p>
     * 
     * 
     * @param type the type of the resource being resolved
     * @param namespaceURI the namespace of the resource being resolved,
     * or <code>null</code> if none was supplied
     * @param publicId the public identifier of the resource being resolved,
     * or <code>null</code> if none was supplied
     * @param systemId the system identifier of the resource being resolved,
     * or <code>null</code> if none was supplied
     * @param baseURI the absolute base URI of the resource being parsed,
     * or <code>null</code> if there is no base URI
     */
    public LSInput resolveResource(String type, String namespaceURI,
        String publicId, String systemId, String baseURI) {

        String resolvedId = null;

        try {
            // The namespace is useful for resolving namespace aware
            // grammars such as XML schema. Let it take precedence over
            // the external identifier if one exists.
            if (namespaceURI != null) {
                resolvedId = resolveURI(namespaceURI);
            }

            if (!getUseLiteralSystemId() && baseURI != null) {
                // Attempt to resolve the system identifier against the base URI.
                try {
                    URI uri = new URI(new URI(baseURI), systemId);
                    systemId = uri.toString();
                }
                // Ignore the exception. Fallback to the literal system identifier.
                catch (URI.MalformedURIException ex) {}
            }

            // Resolve against an external identifier if one exists. This
            // is useful for resolving DTD external subsets and other
            // external entities. For XML schemas if there was no namespace
            // mapping we might be able to resolve a system identifier
            // specified as a location hint.
            if (resolvedId == null) {
                if (publicId != null && systemId != null) {
                    resolvedId = resolvePublic(publicId, systemId);
                }
                else if (systemId != null) {
                    resolvedId = resolveSystem(systemId);
                }
            }
        }
        // Ignore IOException. It cannot be thrown from this method.
        catch (IOException ex) {}

        if (resolvedId != null) {
            return new DOMInputImpl(publicId, resolvedId, baseURI);
        }
        return null;
    }


    /**
     * <p>Resolves an external entity. If the entity cannot be
     * resolved, this method should return <code>null</code>. This
     * method only calls <code>resolveIdentifier</code> and returns
     * an input source if an entry was found in the catalog. It
     * should be overrided if other behaviour is required.</p>
     *
     * <p>
     *  <p>解析外部实体。如果实体无法解析,此方法应返回<code> null </code>。
     * 此方法仅调用<code> resolveIdentifier </code>,并在目录中找到条目时返回输入源。如果需要其他行为,应该覆盖它。</p>。
     * 
     * 
     * @param resourceIdentifier location of the XML resource to resolve
     *
     * @throws XNIException thrown on general error
     * @throws IOException thrown if some i/o error occurs
     */
    public XMLInputSource resolveEntity(XMLResourceIdentifier resourceIdentifier)
        throws XNIException, IOException {

        String resolvedId = resolveIdentifier(resourceIdentifier);
        if (resolvedId != null) {
            return new XMLInputSource(resourceIdentifier.getPublicId(),
                                      resolvedId,
                                      resourceIdentifier.getBaseSystemId());
        }
        return null;
    }

    /**
     * <p>Resolves an identifier using the catalog. This method interprets that
     * the namespace of the identifier corresponds to uri entries in the catalog.
     * Where both a namespace and an external identifier exist, the namespace
     * takes precedence.</p>
     *
     * <p>
     *  <p>使用目录解析标识符。此方法解释标识符的命名空间对应于目录中的uri条目。如果存在命名空间和外部标识符,则命名空间优先。</p>
     * 
     * 
     * @param resourceIdentifier the identifier to resolve
     *
     * @throws XNIException thrown on general error
     * @throws IOException thrown if some i/o error occurs
     */
    public String resolveIdentifier(XMLResourceIdentifier resourceIdentifier)
        throws IOException, XNIException {

        String resolvedId = null;

        // The namespace is useful for resolving namespace aware
        // grammars such as XML schema. Let it take precedence over
        // the external identifier if one exists.
        String namespace = resourceIdentifier.getNamespace();
        if (namespace != null) {
            resolvedId = resolveURI(namespace);
        }

        // Resolve against an external identifier if one exists. This
        // is useful for resolving DTD external subsets and other
        // external entities. For XML schemas if there was no namespace
        // mapping we might be able to resolve a system identifier
        // specified as a location hint.
        if (resolvedId == null) {
            String publicId = resourceIdentifier.getPublicId();
            String systemId = getUseLiteralSystemId()
                ? resourceIdentifier.getLiteralSystemId()
                : resourceIdentifier.getExpandedSystemId();
            if (publicId != null && systemId != null) {
                resolvedId = resolvePublic(publicId, systemId);
            }
            else if (systemId != null) {
                resolvedId = resolveSystem(systemId);
            }
        }
        return resolvedId;
    }

    /**
     * <p>Returns the URI mapping in the catalog for the given
     * external identifier or <code>null</code> if no mapping
     * exists. If the system identifier is an URN in the
     * <code>publicid</code> namespace it is converted into
     * a public identifier by URN "unwrapping" as specified
     * in the XML Catalogs specification.</p>
     *
     * <p>
     *  <p>如果没有映射,则返回给定外部标识符的目录中的URI映射或<code> null </code>。
     * 如果系统标识符是<code> publicid </code>命名空间中的URN,则它将通过XML目录规范中规定的URN"unwrapping"转换为公共标识符。</p>。
     * 
     * 
     * @param systemId the system identifier to locate in the catalog
     *
     * @return the mapped URI or <code>null</code> if no mapping
     * was found in the catalog
     *
     * @throws IOException if an i/o error occurred while reading
     * the catalog
     */
    public final synchronized String resolveSystem (String systemId)
        throws IOException {

        if (fCatalogsChanged) {
            parseCatalogs();
            fCatalogsChanged = false;
        }
        return (fCatalog != null)
            ? fCatalog.resolveSystem(systemId) : null;
    }

    /**
     * <p>Returns the URI mapping in the catalog for the given
     * external identifier or <code>null</code> if no mapping
     * exists. Public identifiers are normalized before
     * comparison.</p>
     *
     * <p>
     *  <p>如果没有映射,则返回给定外部标识符的目录中的URI映射或<code> null </code>。公共标识符在比较之前已标准化。</p>
     * 
     * 
     * @param publicId the public identifier to locate in the catalog
     * @param systemId the system identifier to locate in the catalog
     *
     * @return the mapped URI or <code>null</code> if no mapping
     * was found in the catalog
     *
     * @throws IOException if an i/o error occurred while reading
     * the catalog
     */
    public final synchronized String resolvePublic (String publicId, String systemId)
        throws IOException {

        if (fCatalogsChanged) {
            parseCatalogs();
            fCatalogsChanged = false;
        }
        return (fCatalog != null)
            ? fCatalog.resolvePublic(publicId, systemId) : null;
    }

    /**
     * <p>Returns the URI mapping in the catalog for the given URI
     * reference or <code>null</code> if no mapping exists.
     * URI comparison is case sensitive. If the URI reference
     * is an URN in the <code>publicid</code> namespace
     * it is converted into a public identifier by URN "unwrapping"
     * as specified in the XML Catalogs specification and then
     * resolution is performed following the semantics of
     * external identifier resolution.</p>
     *
     * <p>
     * <p>如果没有映射,则返回给定URI引用的目录中的URI映射或<code> null </code>。 URI比较区分大小写。
     * 如果URI引用是<code> publicid </code>命名空间中的URN,则通过URN"解包"将其转换为XML目录规范中指定的公共标识符,然后根据外部标识符解析的语义执行解析。 </p>。
     * 
     * 
     * @param uri the URI to locate in the catalog
     *
     * @return the mapped URI or <code>null</code> if no mapping
     * was found in the catalog
     *
     * @throws IOException if an i/o error occurred while reading
     * the catalog
     */
    public final synchronized String resolveURI (String uri)
        throws IOException {

        if (fCatalogsChanged) {
            parseCatalogs();
            fCatalogsChanged = false;
        }
        return (fCatalog != null)
            ? fCatalog.resolveURI(uri) : null;
    }

    /**
     * Initialization. Create a CatalogManager and set all
     * the properties upfront. This prevents JVM wide system properties
     * or a property file somewhere in the environment from affecting
     * the behaviour of this catalog resolver.
     * <p>
     *  初始化。创建CatalogManager并预先设置所有属性。这会阻止JVM宽系统属性或环境中某处的属性文件影响此目录解析器的行为。
     * 
     */
    private void init (String [] catalogs, boolean preferPublic) {
        fCatalogsList = (catalogs != null) ? (String[]) catalogs.clone() : null;
        fPreferPublic = preferPublic;
        fResolverCatalogManager = new CatalogManager();
        fResolverCatalogManager.setAllowOasisXMLCatalogPI(false);
        fResolverCatalogManager.setCatalogClassName("com.sun.org.apache.xml.internal.resolver.Catalog");
        fResolverCatalogManager.setCatalogFiles("");
        fResolverCatalogManager.setIgnoreMissingProperties(true);
        fResolverCatalogManager.setPreferPublic(fPreferPublic);
        fResolverCatalogManager.setRelativeCatalogs(false);
        fResolverCatalogManager.setUseStaticCatalog(false);
        fResolverCatalogManager.setVerbosity(0);
    }

    /**
     * Instruct the <code>Catalog</code> to parse each of the
     * catalogs in the list. Only the first catalog will actually be
     * parsed immediately. The others will be queued and read if
     * they are needed later.
     * <p>
     *  指示<code>目录</code>来解析列表中的每个目录。只有第一个目录实际上会立即解析。其他人将被排队和阅读,如果他们以后需要。
     * 
     */
    private void parseCatalogs () throws IOException {
        if (fCatalogsList != null) {
            fCatalog = new Catalog(fResolverCatalogManager);
            attachReaderToCatalog(fCatalog);
            for (int i = 0; i < fCatalogsList.length; ++i) {
                String catalog = fCatalogsList[i];
                if (catalog != null && catalog.length() > 0) {
                    fCatalog.parseCatalog(catalog);
                }
            }
        }
        else {
            fCatalog = null;
        }
    }

    /**
     * Attaches the reader to the catalog.
     * <p>
     *  将读者附在目录上。
     */
    private void attachReaderToCatalog (Catalog catalog) {

        SAXParserFactory spf = new SAXParserFactoryImpl();
        spf.setNamespaceAware(true);
        spf.setValidating(false);

        SAXCatalogReader saxReader = new SAXCatalogReader(spf);
        saxReader.setCatalogParser(OASISXMLCatalogReader.namespaceName, "catalog",
            "com.sun.org.apache.xml.internal.resolver.readers.OASISXMLCatalogReader");
        catalog.addReader("application/xml", saxReader);
    }
}
