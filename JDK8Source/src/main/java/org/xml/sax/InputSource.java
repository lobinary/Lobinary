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

// SAX input source.
// http://www.saxproject.org
// No warranty; no copyright -- use this as you will.
// $Id: InputSource.java,v 1.2 2004/11/03 22:55:32 jsuttor Exp $

package org.xml.sax;

import java.io.Reader;
import java.io.InputStream;

/**
 * A single input source for an XML entity.
 *
 * <blockquote>
 * <em>This module, both source code and documentation, is in the
 * Public Domain, and comes with <strong>NO WARRANTY</strong>.</em>
 * See <a href='http://www.saxproject.org'>http://www.saxproject.org</a>
 * for further information.
 * </blockquote>
 *
 * <p>This class allows a SAX application to encapsulate information
 * about an input source in a single object, which may include
 * a public identifier, a system identifier, a byte stream (possibly
 * with a specified encoding), and/or a character stream.</p>
 *
 * <p>There are two places that the application can deliver an
 * input source to the parser: as the argument to the Parser.parse
 * method, or as the return value of the EntityResolver.resolveEntity
 * method.</p>
 *
 * <p>The SAX parser will use the InputSource object to determine how
 * to read XML input.  If there is a character stream available, the
 * parser will read that stream directly, disregarding any text
 * encoding declaration found in that stream.
 * If there is no character stream, but there is
 * a byte stream, the parser will use that byte stream, using the
 * encoding specified in the InputSource or else (if no encoding is
 * specified) autodetecting the character encoding using an algorithm
 * such as the one in the XML specification.  If neither a character
 * stream nor a
 * byte stream is available, the parser will attempt to open a URI
 * connection to the resource identified by the system
 * identifier.</p>
 *
 * <p>An InputSource object belongs to the application: the SAX parser
 * shall never modify it in any way (it may modify a copy if
 * necessary).  However, standard processing of both byte and
 * character streams is to close them on as part of end-of-parse cleanup,
 * so applications should not attempt to re-use such streams after they
 * have been handed to a parser.  </p>
 *
 * <p>
 *  XML实体的单个输入源。
 * 
 * <blockquote>
 *  <em>此模块(源代码和文档)都位于公共域中,并且随附<strong>无保修</strong>。
 * </em>请参阅<a href ='http：//www.saxproject.org '> http://www.saxproject.org </a>了解更多信息。
 * </blockquote>
 * 
 *  <p>该类允许SAX应用将关于输入源的信息封装在单个对象中,其可以包括公共标识符,系统标识符,字节流(可能具有指定的编码)和/或字符流。 </p>
 * 
 *  <p>应用程序可以向解析器传递输入源的两个位置：作为Parser.parse方法的参数,或作为EntityResolver.resolveEntity方法的返回值。</p>
 * 
 * <p> SAX解析器将使用InputSource对象来确定如何读取XML输入。如果有字符流可用,解析器将直接读取该流,而忽略该流中找到的任何文本编码声明。
 * 如果没有字符流,但有一个字节流,则解析器将使用该字节流,使用InputSource中指定的编码,否则(如果没有指定编码)使用一种算法自动检测字符编码,例如XML规范。
 * 如果字符流和字节流都不可用,则解析器将尝试打开与由系统标识符标识的资源的URI连接。</p>。
 * 
 *  <p> InputSource对象属于应用程序：SAX解析器不得以任何方式修改它(如果需要,它可以修改副本)。
 * 然而,字节和字符流的标准处理是将它们关闭作为解析结束清除的一部分,因此应用程序不应该尝试在它们被交给解析器之后重新使用这样的流。 </p>。
 * 
 * 
 * @since SAX 1.0
 * @author David Megginson
 * @see org.xml.sax.XMLReader#parse(org.xml.sax.InputSource)
 * @see org.xml.sax.EntityResolver#resolveEntity
 * @see java.io.InputStream
 * @see java.io.Reader
 */
public class InputSource {

    /**
     * Zero-argument default constructor.
     *
     * <p>
     *  零参数默认构造函数。
     * 
     * 
     * @see #setPublicId
     * @see #setSystemId
     * @see #setByteStream
     * @see #setCharacterStream
     * @see #setEncoding
     */
    public InputSource ()
    {
    }


    /**
     * Create a new input source with a system identifier.
     *
     * <p>Applications may use setPublicId to include a
     * public identifier as well, or setEncoding to specify
     * the character encoding, if known.</p>
     *
     * <p>If the system identifier is a URL, it must be fully
     * resolved (it may not be a relative URL).</p>
     *
     * <p>
     *  创建一个带有系统标识符的新输入源。
     * 
     *  <p>应用程序可以使用setPublicId来包含公共标识符,如果已知,也可以使用setEncoding来指定字符编码。</p>
     * 
     *  <p>如果系统标识符是网址,则必须完全解析(可能不是相对网址)。</p>
     * 
     * 
     * @param systemId The system identifier (URI).
     * @see #setPublicId
     * @see #setSystemId
     * @see #setByteStream
     * @see #setEncoding
     * @see #setCharacterStream
     */
    public InputSource (String systemId)
    {
        setSystemId(systemId);
    }


    /**
     * Create a new input source with a byte stream.
     *
     * <p>Application writers should use setSystemId() to provide a base
     * for resolving relative URIs, may use setPublicId to include a
     * public identifier, and may use setEncoding to specify the object's
     * character encoding.</p>
     *
     * <p>
     *  使用字节流创建新的输入源。
     * 
     * <p>应用程序编写器应该使用setSystemId()来提供用于解析相对URI的基础,可以使用setPublicId来包含公共标识符,并且可以使用setEncoding来指定对象的字符编码。</p>
     * 
     * 
     * @param byteStream The raw byte stream containing the document.
     * @see #setPublicId
     * @see #setSystemId
     * @see #setEncoding
     * @see #setByteStream
     * @see #setCharacterStream
     */
    public InputSource (InputStream byteStream)
    {
        setByteStream(byteStream);
    }


    /**
     * Create a new input source with a character stream.
     *
     * <p>Application writers should use setSystemId() to provide a base
     * for resolving relative URIs, and may use setPublicId to include a
     * public identifier.</p>
     *
     * <p>The character stream shall not include a byte order mark.</p>
     *
     * <p>
     *  使用字符流创建新的输入源。
     * 
     *  <p>应用程序编写者应该使用setSystemId()来提供一个用于解析相对URI的基础,并且可以使用setPublicId来包含一个公共标识符。</p>
     * 
     *  <p>字符流不应包含字节顺序标记。</p>
     * 
     * 
     * @see #setPublicId
     * @see #setSystemId
     * @see #setByteStream
     * @see #setCharacterStream
     */
    public InputSource (Reader characterStream)
    {
        setCharacterStream(characterStream);
    }


    /**
     * Set the public identifier for this input source.
     *
     * <p>The public identifier is always optional: if the application
     * writer includes one, it will be provided as part of the
     * location information.</p>
     *
     * <p>
     *  设置此输入源的公共标识符。
     * 
     *  <p>公共标识符始终是可选的：如果应用程序编写器包含一个,则它将作为位置信息的一部分提供。</p>
     * 
     * 
     * @param publicId The public identifier as a string.
     * @see #getPublicId
     * @see org.xml.sax.Locator#getPublicId
     * @see org.xml.sax.SAXParseException#getPublicId
     */
    public void setPublicId (String publicId)
    {
        this.publicId = publicId;
    }


    /**
     * Get the public identifier for this input source.
     *
     * <p>
     *  获取此输入源的公共标识符。
     * 
     * 
     * @return The public identifier, or null if none was supplied.
     * @see #setPublicId
     */
    public String getPublicId ()
    {
        return publicId;
    }


    /**
     * Set the system identifier for this input source.
     *
     * <p>The system identifier is optional if there is a byte stream
     * or a character stream, but it is still useful to provide one,
     * since the application can use it to resolve relative URIs
     * and can include it in error messages and warnings (the parser
     * will attempt to open a connection to the URI only if
     * there is no byte stream or character stream specified).</p>
     *
     * <p>If the application knows the character encoding of the
     * object pointed to by the system identifier, it can register
     * the encoding using the setEncoding method.</p>
     *
     * <p>If the system identifier is a URL, it must be fully
     * resolved (it may not be a relative URL).</p>
     *
     * <p>
     *  设置此输入源的系统标识符。
     * 
     *  <p>如果有字节流或字符流,则系统标识符是可选的,但是仍然可以提供一个,因为应用程序可以使用它来解析相对URI,并且可以将其包括在错误消息和警告中(解析器将尝试仅在没有指定字节流或字符流时打开与URI
     * 的连接)。
     * </p>。
     * 
     *  <p>如果应用程序知道系统标识符指向的对象的字符编码,则它可以使用setEncoding方法注册编码。</p>
     * 
     *  <p>如果系统标识符是网址,则必须完全解析(可能不是相对网址)。</p>
     * 
     * 
     * @param systemId The system identifier as a string.
     * @see #setEncoding
     * @see #getSystemId
     * @see org.xml.sax.Locator#getSystemId
     * @see org.xml.sax.SAXParseException#getSystemId
     */
    public void setSystemId (String systemId)
    {
        this.systemId = systemId;
    }


    /**
     * Get the system identifier for this input source.
     *
     * <p>The getEncoding method will return the character encoding
     * of the object pointed to, or null if unknown.</p>
     *
     * <p>If the system ID is a URL, it will be fully resolved.</p>
     *
     * <p>
     *  获取此输入源的系统标识符。
     * 
     * <p> getEncoding方法将返回指向的对象的字符编码,如果未知,则返回null。</p>
     * 
     *  <p>如果系统ID是网址,则会完全解析。</p>
     * 
     * 
     * @return The system identifier, or null if none was supplied.
     * @see #setSystemId
     * @see #getEncoding
     */
    public String getSystemId ()
    {
        return systemId;
    }


    /**
     * Set the byte stream for this input source.
     *
     * <p>The SAX parser will ignore this if there is also a character
     * stream specified, but it will use a byte stream in preference
     * to opening a URI connection itself.</p>
     *
     * <p>If the application knows the character encoding of the
     * byte stream, it should set it with the setEncoding method.</p>
     *
     * <p>
     *  设置此输入源的字节流。
     * 
     *  <p>如果还指定了字符流,则SAX解析器将忽略此操作,但它将使用字节流优先于打开URI连接本身。</p>
     * 
     *  <p>如果应用程序知道字节流的字符编码,则应使用setEncoding方法设置。</p>
     * 
     * 
     * @param byteStream A byte stream containing an XML document or
     *        other entity.
     * @see #setEncoding
     * @see #getByteStream
     * @see #getEncoding
     * @see java.io.InputStream
     */
    public void setByteStream (InputStream byteStream)
    {
        this.byteStream = byteStream;
    }


    /**
     * Get the byte stream for this input source.
     *
     * <p>The getEncoding method will return the character
     * encoding for this byte stream, or null if unknown.</p>
     *
     * <p>
     *  获取此输入源的字节流。
     * 
     *  <p> getEncoding方法将返回此字节流的字符编码,如果未知,则返回null。</p>
     * 
     * 
     * @return The byte stream, or null if none was supplied.
     * @see #getEncoding
     * @see #setByteStream
     */
    public InputStream getByteStream ()
    {
        return byteStream;
    }


    /**
     * Set the character encoding, if known.
     *
     * <p>The encoding must be a string acceptable for an
     * XML encoding declaration (see section 4.3.3 of the XML 1.0
     * recommendation).</p>
     *
     * <p>This method has no effect when the application provides a
     * character stream.</p>
     *
     * <p>
     *  设置字符编码,如果已知。
     * 
     *  <p>编码必须是XML编码声明可接受的字符串(请参阅XML 1.0建议的第4.3.3节)。</p>
     * 
     *  <p>此方法在应用程序提供字符流时无效。</p>
     * 
     * 
     * @param encoding A string describing the character encoding.
     * @see #setSystemId
     * @see #setByteStream
     * @see #getEncoding
     */
    public void setEncoding (String encoding)
    {
        this.encoding = encoding;
    }


    /**
     * Get the character encoding for a byte stream or URI.
     * This value will be ignored when the application provides a
     * character stream.
     *
     * <p>
     *  获取字节流或URI的字符编码。当应用程序提供字符流时,将忽略此值。
     * 
     * 
     * @return The encoding, or null if none was supplied.
     * @see #setByteStream
     * @see #getSystemId
     * @see #getByteStream
     */
    public String getEncoding ()
    {
        return encoding;
    }


    /**
     * Set the character stream for this input source.
     *
     * <p>If there is a character stream specified, the SAX parser
     * will ignore any byte stream and will not attempt to open
     * a URI connection to the system identifier.</p>
     *
     * <p>
     *  设置此输入源的字符流。
     * 
     *  <p>如果指定了字符流,则SAX解析器将忽略任何字节流,并且不会尝试打开与系统标识符的URI连接。</p>
     * 
     * 
     * @param characterStream The character stream containing the
     *        XML document or other entity.
     * @see #getCharacterStream
     * @see java.io.Reader
     */
    public void setCharacterStream (Reader characterStream)
    {
        this.characterStream = characterStream;
    }


    /**
     * Get the character stream for this input source.
     *
     * <p>
     * 
     * @return The character stream, or null if none was supplied.
     * @see #setCharacterStream
     */
    public Reader getCharacterStream ()
    {
        return characterStream;
    }



    ////////////////////////////////////////////////////////////////////
    // Internal state.
    ////////////////////////////////////////////////////////////////////

    private String publicId;
    private String systemId;
    private InputStream byteStream;
    private String encoding;
    private Reader characterStream;

}

// end of InputSource.java
