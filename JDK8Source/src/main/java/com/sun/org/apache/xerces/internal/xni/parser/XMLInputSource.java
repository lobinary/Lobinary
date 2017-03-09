/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2000-2002,2004 The Apache Software Foundation.
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
 *  版权所有2000-2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.xni.parser;

import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;

import java.io.InputStream;
import java.io.Reader;

/**
 * This class represents an input source for an XML document. The
 * basic properties of an input source are the following:
 * <ul>
 *  <li>public identifier</li>
 *  <li>system identifier</li>
 *  <li>byte stream or character stream</li>
 *  <li>
 * </ul>
 *
 * <p>
 *  此类表示XML文档的输入源。输入源的基本属性如下：
 * <ul>
 *  <li>公开标识符</li> <li>系统标识符</li> <li>字节流或字符流</li>
 * <li>
 * </ul>
 * 
 * 
 * @author Andy Clark, IBM
 *
 */
public class XMLInputSource {

    //
    // Data
    //

    /** Public identifier. */
    protected String fPublicId;

    /** System identifier. */
    protected String fSystemId;

    /** Base system identifier. */
    protected String fBaseSystemId;

    /** Byte stream. */
    protected InputStream fByteStream;

    /** Character stream. */
    protected Reader fCharStream;

    /** Encoding. */
    protected String fEncoding;

    //
    // Constructors
    //

    /**
     * Constructs an input source from just the public and system
     * identifiers, leaving resolution of the entity and opening of
     * the input stream up to the caller.
     *
     * <p>
     *  从公共和系统标识符构造输入源,留下实体的解析度和打开输入流直到调用者。
     * 
     * 
     * @param publicId     The public identifier, if known.
     * @param systemId     The system identifier. This value should
     *                     always be set, if possible, and can be
     *                     relative or absolute. If the system identifier
     *                     is relative, then the base system identifier
     *                     should be set.
     * @param baseSystemId The base system identifier. This value should
     *                     always be set to the fully expanded URI of the
     *                     base system identifier, if possible.
     */
    public XMLInputSource(String publicId, String systemId,
                          String baseSystemId) {
        fPublicId = publicId;
        fSystemId = systemId;
        fBaseSystemId = baseSystemId;
    } // <init>(String,String,String)

    /**
     * Constructs an input source from a XMLResourceIdentifier
     * object, leaving resolution of the entity and opening of
     * the input stream up to the caller.
     *
     * <p>
     *  从XMLResourceIdentifier对象构造输入源,留下实体的解析度和打开输入流直到调用者。
     * 
     * 
     * @param resourceIdentifier    the XMLResourceIdentifier containing the information
     */
    public XMLInputSource(XMLResourceIdentifier resourceIdentifier) {

        fPublicId = resourceIdentifier.getPublicId();
        fSystemId = resourceIdentifier.getLiteralSystemId();
        fBaseSystemId = resourceIdentifier.getBaseSystemId();
    } // <init>(XMLResourceIdentifier)

    /**
     * Constructs an input source from a byte stream.
     *
     * <p>
     *  从字节流构造输入源。
     * 
     * 
     * @param publicId     The public identifier, if known.
     * @param systemId     The system identifier. This value should
     *                     always be set, if possible, and can be
     *                     relative or absolute. If the system identifier
     *                     is relative, then the base system identifier
     *                     should be set.
     * @param baseSystemId The base system identifier. This value should
     *                     always be set to the fully expanded URI of the
     *                     base system identifier, if possible.
     * @param byteStream   The byte stream.
     * @param encoding     The encoding of the byte stream, if known.
     */
    public XMLInputSource(String publicId, String systemId,
                          String baseSystemId, InputStream byteStream,
                          String encoding) {
        fPublicId = publicId;
        fSystemId = systemId;
        fBaseSystemId = baseSystemId;
        fByteStream = byteStream;
        fEncoding = encoding;
    } // <init>(String,String,String,InputStream,String)

    /**
     * Constructs an input source from a character stream.
     *
     * <p>
     *  从字符流构造输入源。
     * 
     * 
     * @param publicId     The public identifier, if known.
     * @param systemId     The system identifier. This value should
     *                     always be set, if possible, and can be
     *                     relative or absolute. If the system identifier
     *                     is relative, then the base system identifier
     *                     should be set.
     * @param baseSystemId The base system identifier. This value should
     *                     always be set to the fully expanded URI of the
     *                     base system identifier, if possible.
     * @param charStream   The character stream.
     * @param encoding     The original encoding of the byte stream
     *                     used by the reader, if known.
     */
    public XMLInputSource(String publicId, String systemId,
                          String baseSystemId, Reader charStream,
                          String encoding) {
        fPublicId = publicId;
        fSystemId = systemId;
        fBaseSystemId = baseSystemId;
        fCharStream = charStream;
        fEncoding = encoding;
    } // <init>(String,String,String,Reader,String)

    //
    // Public methods
    //

    /**
     * Sets the public identifier.
     *
     * <p>
     *  设置公共标识符。
     * 
     * 
     * @param publicId The new public identifier.
     */
    public void setPublicId(String publicId) {
        fPublicId = publicId;
    } // setPublicId(String)

    /** Returns the public identifier. */
    public String getPublicId() {
        return fPublicId;
    } // getPublicId():String

    /**
     * Sets the system identifier.
     *
     * <p>
     *  设置系统标识符。
     * 
     * 
     * @param systemId The new system identifier.
     */
    public void setSystemId(String systemId) {
        fSystemId = systemId;
    } // setSystemId(String)

    /** Returns the system identifier. */
    public String getSystemId() {
        return fSystemId;
    } // getSystemId():String

    /**
     * Sets the base system identifier.
     *
     * <p>
     *  设置基本系统标识符。
     * 
     * 
     * @param baseSystemId The new base system identifier.
     */
    public void setBaseSystemId(String baseSystemId) {
        fBaseSystemId = baseSystemId;
    } // setBaseSystemId(String)

    /** Returns the base system identifier. */
    public String getBaseSystemId() {
        return fBaseSystemId;
    } // getBaseSystemId():String

    /**
     * Sets the byte stream. If the byte stream is not already opened
     * when this object is instantiated, then the code that opens the
     * stream should also set the byte stream on this object. Also, if
     * the encoding is auto-detected, then the encoding should also be
     * set on this object.
     *
     * <p>
     * 设置字节流。如果在实例化该对象时字节流尚未打开,则打开流的代码也应该在该对象上设置字节流。此外,如果编码是自动检测的,则还应该在此对象上设置编码。
     * 
     * 
     * @param byteStream The new byte stream.
     */
    public void setByteStream(InputStream byteStream) {
        fByteStream = byteStream;
    } // setByteStream(InputSource)

    /** Returns the byte stream. */
    public InputStream getByteStream() {
        return fByteStream;
    } // getByteStream():InputStream

    /**
     * Sets the character stream. If the character stream is not already
     * opened when this object is instantiated, then the code that opens
     * the stream should also set the character stream on this object.
     * Also, the encoding of the byte stream used by the reader should
     * also be set on this object, if known.
     *
     * <p>
     *  设置字符流。如果在实例化此对象时字符流尚未打开,则打开流的代码还应在此对象上设置字符流。此外,如果已知,读取器使用的字节流的编码也应该设置在该对象上。
     * 
     * 
     * @param charStream The new character stream.
     *
     * @see #setEncoding
     */
    public void setCharacterStream(Reader charStream) {
        fCharStream = charStream;
    } // setCharacterStream(Reader)

    /** Returns the character stream. */
    public Reader getCharacterStream() {
        return fCharStream;
    } // getCharacterStream():Reader

    /**
     * Sets the encoding of the stream.
     *
     * <p>
     *  设置流的编码。
     * 
     * @param encoding The new encoding.
     */
    public void setEncoding(String encoding) {
        fEncoding = encoding;
    } // setEncoding(String)

    /** Returns the encoding of the stream, or null if not known. */
    public String getEncoding() {
        return fEncoding;
    } // getEncoding():String

} // class XMLInputSource
