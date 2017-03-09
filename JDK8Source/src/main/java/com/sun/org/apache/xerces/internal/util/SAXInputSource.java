/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2005 The Apache Software Foundation.
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
 *  版权所有2005 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.util;

import java.io.InputStream;
import java.io.Reader;

import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/**
 * <p>An <code>XMLInputSource</code> analogue to <code>javax.xml.transform.sax.SAXSource</code>.</p>
 *
 * <p>
 *  <p>与<code> javax.xml.transform.sax.SAXSource </code>类似的<code> XMLInputSource </code>。</p>
 * 
 */
public final class SAXInputSource extends XMLInputSource {

    private XMLReader fXMLReader;
    private InputSource fInputSource;

    public SAXInputSource() {
        this(null);
    }

    public SAXInputSource(InputSource inputSource) {
        this(null, inputSource);
    }

    public SAXInputSource(XMLReader reader, InputSource inputSource) {
        super(inputSource != null ? inputSource.getPublicId() : null,
                inputSource != null ? inputSource.getSystemId() : null, null);
        if (inputSource != null) {
            setByteStream(inputSource.getByteStream());
            setCharacterStream(inputSource.getCharacterStream());
            setEncoding(inputSource.getEncoding());
        }
        fInputSource = inputSource;
        fXMLReader = reader;
    }

    public void setXMLReader(XMLReader reader) {
        fXMLReader = reader;
    }

    public XMLReader getXMLReader() {
        return fXMLReader;
    }

    public void setInputSource(InputSource inputSource) {
        if (inputSource != null) {
            setPublicId(inputSource.getPublicId());
            setSystemId(inputSource.getSystemId());
            setByteStream(inputSource.getByteStream());
            setCharacterStream(inputSource.getCharacterStream());
            setEncoding(inputSource.getEncoding());
        }
        else {
            setPublicId(null);
            setSystemId(null);
            setByteStream(null);
            setCharacterStream(null);
            setEncoding(null);
        }
        fInputSource = inputSource;
    }

    public InputSource getInputSource() {
        return fInputSource;
    }

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
        super.setPublicId(publicId);
        if (fInputSource == null) {
            fInputSource = new InputSource();
        }
        fInputSource.setPublicId(publicId);
    } // setPublicId(String)

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
        super.setSystemId(systemId);
        if (fInputSource == null) {
            fInputSource = new InputSource();
        }
        fInputSource.setSystemId(systemId);
    } // setSystemId(String)

    /**
     * Sets the byte stream. If the byte stream is not already opened
     * when this object is instantiated, then the code that opens the
     * stream should also set the byte stream on this object. Also, if
     * the encoding is auto-detected, then the encoding should also be
     * set on this object.
     *
     * <p>
     *  设置字节流。如果在实例化该对象时字节流尚未打开,则打开流的代码也应该在该对象上设置字节流。此外,如果编码是自动检测的,则还应该在此对象上设置编码。
     * 
     * 
     * @param byteStream The new byte stream.
     */
    public void setByteStream(InputStream byteStream) {
        super.setByteStream(byteStream);
        if (fInputSource == null) {
            fInputSource = new InputSource();
        }
        fInputSource.setByteStream(byteStream);
    } // setByteStream(InputStream)

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
        super.setCharacterStream(charStream);
        if (fInputSource == null) {
            fInputSource = new InputSource();
        }
        fInputSource.setCharacterStream(charStream);
    } // setCharacterStream(Reader)

    /**
     * Sets the encoding of the stream.
     *
     * <p>
     *  设置流的编码。
     * 
     * @param encoding The new encoding.
     */
    public void setEncoding(String encoding) {
        super.setEncoding(encoding);
        if (fInputSource == null) {
            fInputSource = new InputSource();
        }
        fInputSource.setEncoding(encoding);
    } // setEncoding(String)

} // SAXInputSource
