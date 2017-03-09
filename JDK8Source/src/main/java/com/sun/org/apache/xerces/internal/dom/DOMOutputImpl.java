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
 *  版权所有2001,2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.dom;

import org.w3c.dom.ls.LSOutput;

import java.io.Writer;
import java.io.OutputStream;

/**
 * This class represents an output destination for data.
 * This interface allows an application to encapsulate information about an
 * output destination in a single object, which may include a URI, a byte stream
 * (possibly with a specifiedencoding), a base URI, and/or a character stream.
 * The exact definitions of a byte stream and a character stream are binding
 * dependent.
 * The application is expected to provide objects that implement this interface
 * whenever such objects are needed. The application can either provide its
 * own objects that implement this interface, or it can use the generic factory
 * method DOMImplementationLS.createLSOutput() to create objects that
 * implement this interface.
 * The DOMSerializer will use the LSOutput object to determine where to
 * serialize the output to. The DOMSerializer will look at the different
 * outputs specified in the LSOutput in the following order to know which one
 * to output to, the first one that data can be output to will be used:
 * 1.LSOutput.characterStream
 * 2.LSOutput.byteStream
 * 3.LSOutput.systemId
 * LSOutput objects belong to the application. The DOM implementation will
 * never modify them (though it may make copies and modify the copies,
 * if necessary).
 *
 * @xerces.internal
 *
 * <p>
 * 此类表示数据的输出目标。该接口允许应用将关于输出目的地的信息封装在单个对象中,其可以包括URI,字节流(可能具有指定的编码),基本URI和/或字符流。字节流和字符流的确切定义是依赖于绑定的。
 * 当需要这样的对象时,期望应用程序提供实现该接口的对象。
 * 应用程序可以提供其自己的实现此接口的对象,也可以使用通用工厂方法DOMImplementationLS.createLSOutput()创建实现此接口的对象。
 *  DOMSerializer将使用LSOutput对象来确定将输出序列化到的位置。
 *  DOMSerializer将按照以下顺序查看LSOutput中指定的不同输出,以了解要输出到哪个输出,将使用可以输出数据的第一个输出：1.LSOutput.characterStream 2.LSOu
 * tput.byteStream 3.LSOutput .systemId LSOutput对象属于应用程序。
 *  DOMSerializer将使用LSOutput对象来确定将输出序列化到的位置。 DOM实现将永远不会修改它们(虽然它可以复制和修改副本,如果必要)。
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Arun Yadav, Sun Microsytems
 * @author Gopal Sharma, Sun Microsystems
 **/

public class DOMOutputImpl implements LSOutput {

        protected Writer fCharStream = null;
        protected OutputStream fByteStream = null;
        protected String fSystemId = null;
        protected String fEncoding = null;

   /**
    * Default Constructor
    * <p>
    *  默认构造函数
    * 
    */
    public DOMOutputImpl() {}

   /**
    * An attribute of a language and binding dependent type that represents a
    * writable stream of bytes. If the application knows the character encoding
    * of the byte stream, it should set the encoding attribute. Setting the
    * encoding in this way will override any encoding specified in an XML
    * declaration in the data.
    * <p>
    * 表示字节的可写流的语言的属性和绑定依赖类型。如果应用程序知道字节流的字符编码,则应设置encoding属性。以这种方式设置编码将覆盖数据中XML声明中指定的任何编码。
    * 
    */

    public Writer getCharacterStream(){
        return fCharStream;
     };

   /**
    * An attribute of a language and binding dependent type that represents a
    * writable stream of bytes. If the application knows the character encoding
    * of the byte stream, it should set the encoding attribute. Setting the
    * encoding in this way will override any encoding specified in an XML
    * declaration in the data.
    * <p>
    *  表示字节的可写流的语言的属性和绑定依赖类型。如果应用程序知道字节流的字符编码,则应设置encoding属性。以这种方式设置编码将覆盖数据中XML声明中指定的任何编码。
    * 
    */

    public void setCharacterStream(Writer characterStream){
        fCharStream = characterStream;
    };

   /**
    * Depending on the language binding in use, this attribute may not be
    * available. An attribute of a language and binding dependent type that
    * represents a writable stream to which 16-bit units can be output. The
    * application must encode the stream using UTF-16 (defined in [Unicode] and
    *  Amendment 1 of [ISO/IEC 10646]).
    * <p>
    *  根据使用的语言绑定,此属性可能不可用。表示可以输出16位单位的可写流的语言属性和绑定依赖类型。
    * 应用程序必须使用UTF-16([Unicode]和[ISO / IEC 10646]的修正案1中定义的)对流进行编码。
    * 
    */

    public OutputStream getByteStream(){
        return fByteStream;
    };

   /**
    * Depending on the language binding in use, this attribute may not be
    * available. An attribute of a language and binding dependent type that
    * represents a writable stream to which 16-bit units can be output. The
    * application must encode the stream using UTF-16 (defined in [Unicode] and
    *  Amendment 1 of [ISO/IEC 10646]).
    * <p>
    *  根据使用的语言绑定,此属性可能不可用。表示可以输出16位单位的可写流的语言属性和绑定依赖类型。
    * 应用程序必须使用UTF-16([Unicode]和[ISO / IEC 10646]的修正案1中定义的)对流进行编码。
    * 
    */

    public void setByteStream(OutputStream byteStream){
        fByteStream = byteStream;
    };

   /**
    * The system identifier, a URI reference [IETF RFC 2396], for this output
    *  destination. If the application knows the character encoding of the
    *  object pointed to by the system identifier, it can set the encoding
    *  using the encoding attribute. If the system ID is a relative URI
    *  reference (see section 5 in [IETF RFC 2396]), the behavior is
    *  implementation dependent.
    * <p>
    * 系统标识符,用于此输出目标的URI引用[IETF RFC 2396]。如果应用知道系统标识符指向的对象的字符编码,则它可以使用编码属性来设置编码。
    * 如果系统ID是相对URI引用(参见[IETF RFC 2396]中的第5节),则该行为取决于实现。
    * 
    */

    public String getSystemId(){
        return fSystemId;
    };

   /**
    * The system identifier, a URI reference [IETF RFC 2396], for this output
    *  destination. If the application knows the character encoding of the
    *  object pointed to by the system identifier, it can set the encoding
    *  using the encoding attribute. If the system ID is a relative URI
    *  reference (see section 5 in [IETF RFC 2396]), the behavior is
    *  implementation dependent.
    * <p>
    *  系统标识符,用于此输出目标的URI引用[IETF RFC 2396]。如果应用知道系统标识符指向的对象的字符编码,则它可以使用编码属性来设置编码。
    * 如果系统ID是相对URI引用(参见[IETF RFC 2396]中的第5节),则该行为取决于实现。
    * 
    */

    public void setSystemId(String systemId){
        fSystemId = systemId;
    };

   /**
    * The character encoding, if known. The encoding must be a string
    * acceptable for an XML encoding declaration ([XML 1.0] section 4.3.3
    * "Character Encoding in Entities"). This attribute has no effect when the
    * application provides a character stream or string data. For other sources
    * of input, an encoding specified by means of this attribute will override
    * any encoding specified in the XML declaration or the Text declaration, or
    * an encoding obtained from a higher level protocol, such as HTTP
    * [IETF RFC 2616].
    * <p>
    *  字符编码,如果已知。编码必须是XML编码声明可接受的字符串([XML 1.0]第4.3.3节"实体中的字符编码")。当应用程序提供字符流或字符串数​​据时,此属性无效。
    * 对于其他输入源,通过此属性指定的编码将覆盖XML声明或Text声明中指定的任何编码,或从更高级协议(如HTTP [IETF RFC 2616])获取的编码。
    * 
    */

    public String getEncoding(){
        return fEncoding;
    };

   /**
    * The character encoding, if known. The encoding must be a string
    * acceptable for an XML encoding declaration ([XML 1.0] section 4.3.3
    * "Character Encoding in Entities"). This attribute has no effect when the
    * application provides a character stream or string data. For other sources
    * of input, an encoding specified by means of this attribute will override
    * any encoding specified in the XML declaration or the Text declaration, or
    * an encoding obtained from a higher level protocol, such as HTTP
    * [IETF RFC 2616].
    * <p>
    * 字符编码,如果已知。编码必须是XML编码声明可接受的字符串([XML 1.0]第4.3.3节"实体中的字符编码")。当应用程序提供字符流或字符串数​​据时,此属性无效。
    * 对于其他输入源,通过此属性指定的编码将覆盖XML声明或Text声明中指定的任何编码,或从更高级协议(如HTTP [IETF RFC 2616])获取的编码。
    */

    public void setEncoding(String encoding){
        fEncoding = encoding;
    };

}//DOMOutputImpl
