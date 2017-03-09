/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2005, Oracle and/or its affiliates. All rights reserved.
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

// Locator2.java - extended Locator
// http://www.saxproject.org
// Public Domain: no warranty.
// $Id: Locator2.java,v 1.2 2004/11/03 22:49:08 jsuttor Exp $

package org.xml.sax.ext;

import org.xml.sax.Locator;


/**
 * SAX2 extension to augment the entity information provided
 * though a {@link Locator}.
 * If an implementation supports this extension, the Locator
 * provided in {@link org.xml.sax.ContentHandler#setDocumentLocator
 * ContentHandler.setDocumentLocator() } will implement this
 * interface, and the
 * <em>http://xml.org/sax/features/use-locator2</em> feature
 * flag will have the value <em>true</em>.
 *
 * <blockquote>
 * <em>This module, both source code and documentation, is in the
 * Public Domain, and comes with <strong>NO WARRANTY</strong>.</em>
 * </blockquote>
 *
 * <p> XMLReader implementations are not required to support this
 * information, and it is not part of core-only SAX2 distributions.</p>
 *
 * <p>
 *  SAX2扩展来扩充通过{@link Locator}提供的实体信息。
 * 如果实现支持此扩展,{@link org.xml.sax.ContentHandler#setDocumentLocator ContentHandler.setDocumentLocator()}中提
 * 供的Locator将实现此接口,并且<em> http://xml.org/sax/features / use-locator2 </em> feature标志的值将为<em> true </em>。
 *  SAX2扩展来扩充通过{@link Locator}提供的实体信息。
 * 
 * <blockquote>
 *  <em>此模块(源代码和文档)位于公共域中,并且随附<strong>无保修</strong>。</em>
 * </blockquote>
 * 
 *  <p> XMLReader实现不需要支持此信息,它不是仅核心SAX2分发的一部分。</p>
 * 
 * 
 * @since SAX 2.0 (extensions 1.1 alpha)
 * @author David Brownell
 */
public interface Locator2 extends Locator
{
    /**
     * Returns the version of XML used for the entity.  This will
     * normally be the identifier from the current entity's
     * <em>&lt;?xml&nbsp;version='...'&nbsp;...?&gt;</em> declaration,
     * or be defaulted by the parser.
     *
     * <p>
     *  返回用于实体的XML版本。这通常是来自当前实体的<em>&lt;?xml&nbsp; version ='...'&nbsp; ...?&gt; </em>声明的标识符,或者由解析器默认。
     * 
     * 
     * @return Identifier for the XML version being used to interpret
     * the entity's text, or null if that information is not yet
     * available in the current parsing state.
     */
    public String getXMLVersion ();

    /**
     * Returns the name of the character encoding for the entity.
     * If the encoding was declared externally (for example, in a MIME
     * Content-Type header), that will be the name returned.  Else if there
     * was an <em>&lt;?xml&nbsp;...encoding='...'?&gt;</em> declaration at
     * the start of the document, that encoding name will be returned.
     * Otherwise the encoding will been inferred (normally to be UTF-8, or
     * some UTF-16 variant), and that inferred name will be returned.
     *
     * <p>When an {@link org.xml.sax.InputSource InputSource} is used
     * to provide an entity's character stream, this method returns the
     * encoding provided in that input stream.
     *
     * <p> Note that some recent W3C specifications require that text
     * in some encodings be normalized, using Unicode Normalization
     * Form C, before processing.  Such normalization must be performed
     * by applications, and would normally be triggered based on the
     * value returned by this method.
     *
     * <p> Encoding names may be those used by the underlying JVM,
     * and comparisons should be case-insensitive.
     *
     * <p>
     *  返回实体的字符编码的名称。如果编码是外部声明的(例如,在MIME Con​​tent-Type头中),那将是返回的名称。
     * 否则,如果在文档开头有一个<em>&lt;?xml&nbsp; ... encoding ='...'?&gt; </em>声明,那么将返回该编码名称。
     * 否则,将推断编码(通常为UTF-8或某个​​UTF-16变体),并返回推断的名称。
     * 
     * <p>当{@link org.xml.sax.InputSource InputSource}用于提供实体的字符流时,此方法返回该输入流中提供的编码。
     * 
     * @return Name of the character encoding being used to interpret
     * * the entity's text, or null if this was not provided for a *
     * character stream passed through an InputSource or is otherwise
     * not yet available in the current parsing state.
     */
    public String getEncoding ();
}
