/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2005, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

// SAX entity resolver.
// http://www.saxproject.org
// No warranty; no copyright -- use this as you will.
// $Id: EntityResolver.java,v 1.2 2004/11/03 22:44:52 jsuttor Exp $

package org.xml.sax;

import java.io.IOException;


/**
 * Basic interface for resolving entities.
 *
 * <blockquote>
 * <em>This module, both source code and documentation, is in the
 * Public Domain, and comes with <strong>NO WARRANTY</strong>.</em>
 * See <a href='http://www.saxproject.org'>http://www.saxproject.org</a>
 * for further information.
 * </blockquote>
 *
 * <p>If a SAX application needs to implement customized handling
 * for external entities, it must implement this interface and
 * register an instance with the SAX driver using the
 * {@link org.xml.sax.XMLReader#setEntityResolver setEntityResolver}
 * method.</p>
 *
 * <p>The XML reader will then allow the application to intercept any
 * external entities (including the external DTD subset and external
 * parameter entities, if any) before including them.</p>
 *
 * <p>Many SAX applications will not need to implement this interface,
 * but it will be especially useful for applications that build
 * XML documents from databases or other specialised input sources,
 * or for applications that use URI types other than URLs.</p>
 *
 * <p>The following resolver would provide the application
 * with a special character stream for the entity with the system
 * identifier "http://www.myhost.com/today":</p>
 *
 * <pre>
 * import org.xml.sax.EntityResolver;
 * import org.xml.sax.InputSource;
 *
 * public class MyResolver implements EntityResolver {
 *   public InputSource resolveEntity (String publicId, String systemId)
 *   {
 *     if (systemId.equals("http://www.myhost.com/today")) {
 *              // return a special input source
 *       MyReader reader = new MyReader();
 *       return new InputSource(reader);
 *     } else {
 *              // use the default behaviour
 *       return null;
 *     }
 *   }
 * }
 * </pre>
 *
 * <p>The application can also use this interface to redirect system
 * identifiers to local URIs or to look up replacements in a catalog
 * (possibly by using the public identifier).</p>
 *
 * <p>
 *  用于解析实体的基本接口。
 * 
 * <blockquote>
 *  <em>此模块(源代码和文档)都位于公共域中,并且随附<strong>无保证</strong>。
 * </em>请参阅<a href ='http：//www.saxproject.org '> http://www.saxproject.org </a>了解更多信息。
 * </blockquote>
 * 
 *  <p>如果SAX应用程序需要为外部实体实现自定义处理,则必须使用{@link org.xml.sax.XMLReader#setEntityResolver setEntityResolver}方法实
 * 现此接口并向SAX驱动程序注册实例。
 * </p >。
 * 
 *  <p>然后,XML读取器将允许应用程序在包含它们之前拦截任何外部实体(包括外部DTD子集和外部参数实体,如果有)。</p>
 * 
 *  <p>许多SAX应用程序不需要实现此接口,但它对于从数据库或其他专用输入源或使用除URL之外的URI类型的应用程序构建XML文档的应用程序尤其有用。</p>
 * 
 *  <p>以下解析器将为系统标识符为"http://www.myhost.com/today"的实体向应用程序提供特殊字符流：</p>
 * 
 * <pre>
 *  import org.xml.sax.EntityResolver; import org.xml.sax.InputSource;
 * 
 * @since SAX 1.0
 * @author David Megginson
 * @see org.xml.sax.XMLReader#setEntityResolver
 * @see org.xml.sax.InputSource
 */
public interface EntityResolver {


    /**
     * Allow the application to resolve external entities.
     *
     * <p>The parser will call this method before opening any external
     * entity except the top-level document entity.  Such entities include
     * the external DTD subset and external parameter entities referenced
     * within the DTD (in either case, only if the parser reads external
     * parameter entities), and external general entities referenced
     * within the document element (if the parser reads external general
     * entities).  The application may request that the parser locate
     * the entity itself, that it use an alternative URI, or that it
     * use data provided by the application (as a character or byte
     * input stream).</p>
     *
     * <p>Application writers can use this method to redirect external
     * system identifiers to secure and/or local URIs, to look up
     * public identifiers in a catalogue, or to read an entity from a
     * database or other input source (including, for example, a dialog
     * box).  Neither XML nor SAX specifies a preferred policy for using
     * public or system IDs to resolve resources.  However, SAX specifies
     * how to interpret any InputSource returned by this method, and that
     * if none is returned, then the system ID will be dereferenced as
     * a URL.  </p>
     *
     * <p>If the system identifier is a URL, the SAX parser must
     * resolve it fully before reporting it to the application.</p>
     *
     * <p>
     * 
     * public class MyResolver implements EntityResolver {public InputSource resolveEntity(String publicId,String systemId){if(systemId.equals("http://www.myhost.com/today")){//返回一个特殊的输入源MyReader reader = new MyReader(); return new InputSource(reader); }
     *  else {//使用默认行为return null; }}}。
     * </pre>
     * 
     *  <p>应用程序还可以使用此接口将系统标识符重定向到本地URI或在目录中查找替换(可能通过使用公共标识符)。</p>
     * 
     * 
     * @param publicId The public identifier of the external entity
     *        being referenced, or null if none was supplied.
     * @param systemId The system identifier of the external entity
     *        being referenced.
     * @return An InputSource object describing the new input source,
     *         or null to request that the parser open a regular
     *         URI connection to the system identifier.
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     * @exception java.io.IOException A Java-specific IO exception,
     *            possibly the result of creating a new InputStream
     *            or Reader for the InputSource.
     * @see org.xml.sax.InputSource
     */
    public abstract InputSource resolveEntity (String publicId,
                                               String systemId)
        throws SAXException, IOException;

}

// end of EntityResolver.java
