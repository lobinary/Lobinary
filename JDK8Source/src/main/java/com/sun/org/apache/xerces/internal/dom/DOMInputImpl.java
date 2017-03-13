/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001, 2002,2004 The Apache Software Foundation.
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
 *  版权所有2001,2002,2004 Apache软件基金会
 * 
 *  根据Apache许可证第20版("许可证")授权;您不得使用此文件,除非符合许可证您可以在获取许可证的副本
 * 
 *  http：// wwwapacheorg / licenses / LICENSE-20
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件将按"原样"基础分发,无任何明示或暗示的保证或条件。请参阅许可证管理权限和限制许可证
 * 
 */

package com.sun.org.apache.xerces.internal.dom;

import org.w3c.dom.ls.LSInput;

import java.io.Reader;
import java.io.InputStream;

/**
 * This Class <code>DOMInputImpl</code> represents a single input source for an XML entity.
 * <p> This Class allows an application to encapsulate information about
 * an input source in a single object, which may include a public
 * identifier, a system identifier, a byte stream (possibly with a specified
 * encoding), and/or a character stream.
 * <p> The exact definitions of a byte stream and a character stream are
 * binding dependent.
 * <p> There are two places that the application will deliver this input
 * source to the parser: as the argument to the <code>parse</code> method,
 * or as the return value of the <code>DOMResourceResolver.resolveEntity</code>
 *  method.
 * <p> The <code>DOMParser</code> will use the <code>LSInput</code>
 * object to determine how to read XML input. If there is a character stream
 * available, the parser will read that stream directly; if not, the parser
 * will use a byte stream, if available; if neither a character stream nor a
 * byte stream is available, the parser will attempt to open a URI
 * connection to the resource identified by the system identifier.
 * <p> An <code>LSInput</code> object belongs to the application: the
 * parser shall never modify it in any way (it may modify a copy if
 * necessary).  Eventhough all attributes in this interface are writable the
 * DOM implementation is expected to never mutate a LSInput.
 * <p>See also the <a href='http://www.w3.org/TR/2001/WD-DOM-Level-3-ASLS-20011025'>Document Object Model (DOM) Level 3 Abstract Schemas and Load
and Save Specification</a>.
 *
 * @xerces.internal
 *
 * <p>
 * 此类<code> DOMInputImpl </code>表示XML实体的单个输入源。
 * 此类允许应用程序将关于输入源的信息封装在单个对象中,其可以包括公共标识符,系统标识符,字节流(可能具有指定的编码)和/或字符流<p>字节流和字符流的确切定义是依赖绑定的<p>应用程序将传递此输入源的两个
 * 位置解析器：作为<code> parse </code>方法的参数,或者作为<code> DOMResourceResolverresolveEntity </code>方法的返回值<p> <code>
 *  DOMParser </code>代码> LSInput </code>对象,以确定如何读取XML输入如果有一个字符流可用,解析器将直接读取该流;如果没有,解析器将使用字节流(如果可用)如果字符流和字
 * 节流都不可用,则解析器将尝试打开到由系统标识符<p>所标识的资源的URI连接。
 * 此类<code> DOMInputImpl </code>表示XML实体的单个输入源。
 * </p> <code> LSInput </code>对象属于应用程序：解析器应尽管这个接口中的所有属性都是可写的,但DOM实现不会改变LSInput <p>另请参见<a href ='http：// wwww3org / TR / 2001 / WD-DOM-Level-3-ASLS-20011025'>
 * 文档对象模型(DOM)3级抽象模式和加载和保存规范</a>。
 * 此类<code> DOMInputImpl </code>表示XML实体的单个输入源。
 * 
 * @xercesinternal
 * 
 * 
 * @author Gopal Sharma, SUN Microsystems Inc.
 */

// REVISIT:
// 1. it should be possible to do the following
// DOMInputImpl extends XMLInputSource implements LSInput
// 2. we probably need only the default constructor.  -- el

public class DOMInputImpl implements LSInput {

        //
        // Data
        //

        protected String fPublicId = null;
        protected String fSystemId = null;
        protected String fBaseSystemId = null;

        protected InputStream fByteStream = null;
        protected Reader fCharStream    = null;
        protected String fData = null;

        protected String fEncoding = null;

        protected boolean fCertifiedText = false;

   /**
     * Default Constructor, constructs an input source
     *
     *
     * <p>
     *  默认构造函数,构造输入源
     * 
     */
     public DOMInputImpl() {}

   /**
     * Constructs an input source from just the public and system
     * identifiers, leaving resolution of the entity and opening of
     * the input stream up to the caller.
     *
     * <p>
     *  从公共和系统标识符构造输入源,留下实体的解析度和打开输入流直到调用者
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

    public DOMInputImpl(String publicId, String systemId,
                          String baseSystemId) {

                fPublicId = publicId;
                fSystemId = systemId;
                fBaseSystemId = baseSystemId;

    } // DOMInputImpl(String,String,String)

    /**
     * Constructs an input source from a byte stream.
     *
     * <p>
     *  从字节流构造输入源
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

    public DOMInputImpl(String publicId, String systemId,
                          String baseSystemId, InputStream byteStream,
                          String encoding) {

                fPublicId = publicId;
                fSystemId = systemId;
                fBaseSystemId = baseSystemId;
                fByteStream = byteStream;
                fEncoding = encoding;

    } // DOMInputImpl(String,String,String,InputStream,String)

   /**
     * Constructs an input source from a character stream.
     *
     * <p>
     *  从字符流构造输入源
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

     public DOMInputImpl(String publicId, String systemId,
                          String baseSystemId, Reader charStream,
                          String encoding) {

                fPublicId = publicId;
                fSystemId = systemId;
                fBaseSystemId = baseSystemId;
                fCharStream = charStream;
                fEncoding = encoding;

     } // DOMInputImpl(String,String,String,Reader,String)

   /**
     * Constructs an input source from a String.
     *
     * <p>
     *  从字符串构造输入源
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
     * @param data                 The String Data.
     * @param encoding     The original encoding of the byte stream
     *                     used by the reader, if known.
     */

     public DOMInputImpl(String publicId, String systemId,
                          String baseSystemId, String data,
                          String encoding) {
                fPublicId = publicId;
                fSystemId = systemId;
                fBaseSystemId = baseSystemId;
                fData = data;
                fEncoding = encoding;
     } // DOMInputImpl(String,String,String,String,String)

   /**
     * An attribute of a language-binding dependent type that represents a
     * stream of bytes.
     * <br>The parser will ignore this if there is also a character stream
     * specified, but it will use a byte stream in preference to opening a
     * URI connection itself.
     * <br>If the application knows the character encoding of the byte stream,
     * it should set the encoding property. Setting the encoding in this way
     * will override any encoding specified in the XML declaration itself.
     * <p>
     * 表示字节流的语言绑定依赖类型的属性<br>如果还指定了字符流,则解析器将忽略此属性,但它将使用字节流优先于打开URI连接本身<br>如果应用程序知道字节流的字符编码,那么应设置编码属性。
     * 以这种方式设置编码将覆盖XML声明中指定的任何编码。
     * 
     */

    public InputStream getByteStream(){
        return fByteStream;
    }

    /**
     * An attribute of a language-binding dependent type that represents a
     * stream of bytes.
     * <br>The parser will ignore this if there is also a character stream
     * specified, but it will use a byte stream in preference to opening a
     * URI connection itself.
     * <br>If the application knows the character encoding of the byte stream,
     * it should set the encoding property. Setting the encoding in this way
     * will override any encoding specified in the XML declaration itself.
     * <p>
     * 表示字节流的语言绑定依赖类型的属性<br>如果还指定了字符流,则解析器将忽略此属性,但它将使用字节流优先于打开URI连接本身<br>如果应用程序知道字节流的字符编码,那么应设置编码属性。
     * 以这种方式设置编码将覆盖XML声明中指定的任何编码。
     * 
     */

     public void setByteStream(InputStream byteStream){
        fByteStream = byteStream;
     }

    /**
     *  An attribute of a language-binding dependent type that represents a
     * stream of 16-bit units. Application must encode the stream using
     * UTF-16 (defined in  and Amendment 1 of ).
     * <br>If a character stream is specified, the parser will ignore any byte
     * stream and will not attempt to open a URI connection to the system
     * identifier.
     * <p>
     *  表示16位单位流的语言绑定依赖类型的属性应用程序必须使用UTF-16(在其中定义和修订1)对流进行编码<br>如果指定了字符流,则解析器将忽略任何字节流,并且不会尝试打开到系统标识符的URI连接
     * 
     */
    public Reader getCharacterStream(){
        return fCharStream;
    }
    /**
     *  An attribute of a language-binding dependent type that represents a
     * stream of 16-bit units. Application must encode the stream using
     * UTF-16 (defined in  and Amendment 1 of ).
     * <br>If a character stream is specified, the parser will ignore any byte
     * stream and will not attempt to open a URI connection to the system
     * identifier.
     * <p>
     * 表示16位单位流的语言绑定依赖类型的属性应用程序必须使用UTF-16(在其中定义和修订1)对流进行编码<br>如果指定了字符流,则解析器将忽略任何字节流,并且不会尝试打开到系统标识符的URI连接
     * 
     */

     public void setCharacterStream(Reader characterStream){
        fCharStream = characterStream;
     }

    /**
     * A string attribute that represents a sequence of 16 bit units (utf-16
     * encoded characters).
     * <br>If string data is available in the input source, the parser will
     * ignore the character stream and the byte stream and will not attempt
     * to open a URI connection to the system identifier.
     * <p>
     *  表示16位单位(utf-16编码字符)序列的字符串属性<br>如果输入源中有字符串数据,解析器将忽略字符流和字节流,并且不会尝试打开URI连接到系统标识符
     * 
     */
    public String getStringData(){
        return fData;
    }

   /**
     * A string attribute that represents a sequence of 16 bit units (utf-16
     * encoded characters).
     * <br>If string data is available in the input source, the parser will
     * ignore the character stream and the byte stream and will not attempt
     * to open a URI connection to the system identifier.
     * <p>
     * 表示16位单位(utf-16编码字符)序列的字符串属性<br>如果输入源中有字符串数据,解析器将忽略字符流和字节流,并且不会尝试打开URI连接到系统标识符
     * 
     */

     public void setStringData(String stringData){
                fData = stringData;
     }

    /**
     *  The character encoding, if known. The encoding must be a string
     * acceptable for an XML encoding declaration ( section 4.3.3 "Character
     * Encoding in Entities").
     * <br>This attribute has no effect when the application provides a
     * character stream. For other sources of input, an encoding specified
     * by means of this attribute will override any encoding specified in
     * the XML claration or the Text Declaration, or an encoding obtained
     * from a higher level protocol, such as HTTP .
     * <p>
     *  字符编码(如果已知)编码必须是XML编码声明可接受的字符串(第433节"实体中的字符编码")<br>当应用程序提供字符流时,此属性无效。
     * 对于其他输入源,通过此属性指定的编码将覆盖在XML克隆或文本声明中指定的任何编码,或者从更高级协议获得的编码,例如HTTP。
     * 
     */

    public String getEncoding(){
        return fEncoding;
    }

    /**
     *  The character encoding, if known. The encoding must be a string
     * acceptable for an XML encoding declaration ( section 4.3.3 "Character
     * Encoding in Entities").
     * <br>This attribute has no effect when the application provides a
     * character stream. For other sources of input, an encoding specified
     * by means of this attribute will override any encoding specified in
     * the XML claration or the Text Declaration, or an encoding obtained
     * from a higher level protocol, such as HTTP .
     * <p>
     * 字符编码(如果已知)编码必须是XML编码声明可接受的字符串(第433节"实体中的字符编码")<br>当应用程序提供字符流时,此属性无效。
     * 对于其他输入源,通过此属性指定的编码将覆盖在XML克隆或文本声明中指定的任何编码,或者从更高级协议获得的编码,例如HTTP。
     * 
     */
    public void setEncoding(String encoding){
        fEncoding = encoding;
    }

    /**
     * The public identifier for this input source. The public identifier is
     * always optional: if the application writer includes one, it will be
     * provided as part of the location information.
     * <p>
     *  此输入源的公共标识符公共标识符始终是可选的：如果应用程序编写器包括一个,它将作为位置信息的一部分提供
     * 
     */
    public String getPublicId(){
        return fPublicId;
    }
    /**
     * The public identifier for this input source. The public identifier is
     * always optional: if the application writer includes one, it will be
     * provided as part of the location information.
     * <p>
     * 此输入源的公共标识符公共标识符始终是可选的：如果应用程序编写器包括一个,它将作为位置信息的一部分提供
     * 
     */
    public void setPublicId(String publicId){
        fPublicId = publicId;
    }

    /**
     * The system identifier, a URI reference , for this input source. The
     * system identifier is optional if there is a byte stream or a
     * character stream, but it is still useful to provide one, since the
     * application can use it to resolve relative URIs and can include it in
     * error messages and warnings (the parser will attempt to fetch the
     * ressource identifier by the URI reference only if there is no byte
     * stream or character stream specified).
     * <br>If the application knows the character encoding of the object
     * pointed to by the system identifier, it can register the encoding by
     * setting the encoding attribute.
     * <br>If the system ID is a relative URI reference (see section 5 in ),
     * the behavior is implementation dependent.
     * <p>
     * 用于此输入源的系统标识符(一个URI引用)如果有字节流或字符流,则系统标识符是可选的,但是仍然可以提供一个,因为应用程序可以使用它来解析相对URI,并且可以包括它在错误消息和警告中(只有当没有指定字节流
     * 或字符流时,解析器才会尝试通过URI引用获取ressource标识符)<br>如果应用程序知道系统指向的对象的字符编码标识符,它可以通过设置编码属性来注册编码<br>如果系统ID是相对URI引用(参见第
     * 5节),则行为是实现相关的。
     * 
     */
    public String getSystemId(){
        return fSystemId;
    }
    /**
     * The system identifier, a URI reference , for this input source. The
     * system identifier is optional if there is a byte stream or a
     * character stream, but it is still useful to provide one, since the
     * application can use it to resolve relative URIs and can include it in
     * error messages and warnings (the parser will attempt to fetch the
     * ressource identifier by the URI reference only if there is no byte
     * stream or character stream specified).
     * <br>If the application knows the character encoding of the object
     * pointed to by the system identifier, it can register the encoding by
     * setting the encoding attribute.
     * <br>If the system ID is a relative URI reference (see section 5 in ),
     * the behavior is implementation dependent.
     * <p>
     * 用于此输入源的系统标识符(一个URI引用)如果有字节流或字符流,则系统标识符是可选的,但是仍然可以提供一个,因为应用程序可以使用它来解析相对URI,并且可以包括它在错误消息和警告中(只有当没有指定字节流
     * 或字符流时,解析器才会尝试通过URI引用获取ressource标识符)<br>如果应用程序知道系统指向的对象的字符编码标识符,它可以通过设置编码属性来注册编码<br>如果系统ID是相对URI引用(参见第
     * 5节),则行为是实现相关的。
     * 
     */
    public void setSystemId(String systemId){
        fSystemId = systemId;
    }

    /**
     *  The base URI to be used (see section 5.1.4 in ) for resolving relative
     * URIs to absolute URIs. If the baseURI is itself a relative URI, the
     * behavior is implementation dependent.
     * <p>
     * 用于将相对URI解析为绝对URI的基本URI(请参见第514节)如果baseURI本身是相对URI,则行为是实现相关的
     * 
     */
    public String getBaseURI(){
        return fBaseSystemId;
    }
    /**
     *  The base URI to be used (see section 5.1.4 in ) for resolving relative
     * URIs to absolute URIs. If the baseURI is itself a relative URI, the
     * behavior is implementation dependent.
     * <p>
     *  用于将相对URI解析为绝对URI的基本URI(请参见第514节)如果baseURI本身是相对URI,则行为是实现相关的
     * 
     */
    public void setBaseURI(String baseURI){
        fBaseSystemId = baseURI;
    }

    /**
      *  If set to true, assume that the input is certified (see section 2.13
      * in [<a href='http://www.w3.org/TR/2002/CR-xml11-20021015/'>XML 1.1</a>]) when
      * parsing [<a href='http://www.w3.org/TR/2002/CR-xml11-20021015/'>XML 1.1</a>].
      * <p>
      *  如果设置为true,则假设输入是经过验证的(请参阅[<a href='http://wwww3org/TR/2002/CR-xml11-20021015/'> XML 11 </a>]中的第213节)
      * ,然后解析[<a href='http://wwww3org/TR/2002/CR-xml11-20021015/'> XML 11 </a>]。
      * 
      */
    public boolean getCertifiedText(){
      return fCertifiedText;
    }

    /**
      *  If set to true, assume that the input is certified (see section 2.13
      * in [<a href='http://www.w3.org/TR/2002/CR-xml11-20021015/'>XML 1.1</a>]) when
      * parsing [<a href='http://www.w3.org/TR/2002/CR-xml11-20021015/'>XML 1.1</a>].
      * <p>
      * 如果设置为true,则假设输入是经过验证的(请参阅[<a href='http://wwww3org/TR/2002/CR-xml11-20021015/'> XML 11 </a>]中的第213节),
      * 然后解析[<a href='http://wwww3org/TR/2002/CR-xml11-20021015/'> XML 11 </a>]。
      */

    public void setCertifiedText(boolean certifiedText){
      fCertifiedText = certifiedText;
    }

}// class DOMInputImpl
