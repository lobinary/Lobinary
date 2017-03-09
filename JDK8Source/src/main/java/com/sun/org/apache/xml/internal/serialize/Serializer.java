/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2002,2004 The Apache Software Foundation.
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
 *  版权所有1999-2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */


package com.sun.org.apache.xml.internal.serialize;


import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import org.xml.sax.ContentHandler;
import org.xml.sax.DocumentHandler;


/**
 * Interface for a DOM serializer implementation, factory for DOM and SAX
 * serializers, and static methods for serializing DOM documents.
 * <p>
 * To serialize a document using SAX events, create a compatible serializer
 * and pass it around as a {@link
 * org.xml.sax.DocumentHandler}. If an I/O error occurs while serializing, it will
 * be thrown by {@link DocumentHandler#endDocument}. The SAX serializer
 * may also be used as {@link org.xml.sax.DTDHandler}, {@link org.xml.sax.ext.DeclHandler} and
 * {@link org.xml.sax.ext.LexicalHandler}.
 * <p>
 * To serialize a DOM document or DOM element, create a compatible
 * serializer and call it's {@link
 * DOMSerializer#serialize(Document)} or {@link DOMSerializer#serialize(Element)} methods.
 * Both methods would produce a full XML document, to serizlie only
 * the portion of the document use {@link OutputFormat#setOmitXMLDeclaration}
 * and specify no document type.
 * <p>
 * The {@link OutputFormat} dictates what underlying serialized is used
 * to serialize the document based on the specified method. If the output
 * format or method are missing, the default is an XML serializer with
 * UTF-8 encoding and now indentation.
 *
 *
 * <p>
 *  DOM序列化器实现的接口,DOM和SAX序列化器的工厂,以及用于序列化DOM文档的静态方法。
 * <p>
 *  要使用SAX事件序列化文档,请创建兼容的序列化程序,并将其作为{@link org.xml.sax.DocumentHandler}传递。
 * 如果在序列化时发生I / O错误,它将由{@link DocumentHandler#endDocument}抛出。
 *  SAX序列化程序也可以用作{@link org.xml.sax.DTDHandler},{@link org.xml.sax.ext.DeclHandler}和{@link org.xml.sax.ext.LexicalHandler}
 * 。
 * 如果在序列化时发生I / O错误,它将由{@link DocumentHandler#endDocument}抛出。
 * <p>
 * 要序列化DOM文档或DOM元素,请创建兼容的序列化程序,并调用其{@link DOMSerializer#serialize(Document)}或{@link DOMSerializer#serialize(Element)}
 * 方法。
 * 这两种方法都会生成一个完整的XML文档,以便只使用{@link OutputFormat#setOmitXMLDeclaration}文档的一部分,并且不指定任何文档类型。
 * <p>
 *  {@link OutputFormat}指示基于序列化的基础,用于基于指定的方法序列化文档。如果缺少输出格式或方法,则缺省值为具有UTF-8编码的XML序列化程序,现在缩进。
 * 
 * 
 * @author <a href="mailto:arkin@intalio.com">Assaf Arkin</a>
 * @author <a href="mailto:Scott_Boag/CAM/Lotus@lotus.com">Scott Boag</a>
 * @see DocumentHandler
 * @see ContentHandler
 * @see OutputFormat
 * @see DOMSerializer
 */
public interface Serializer
{


    /**
     * Specifies an output stream to which the document should be
     * serialized. This method should not be called while the
     * serializer is in the process of serializing a document.
     * <p>
     *  指定应将文档序列化的输出流。在序列化程序正在序列化文档的过程中,不应调用此方法。
     * 
     */
    public void setOutputByteStream(OutputStream output);


    /**
     * Specifies a writer to which the document should be serialized.
     * This method should not be called while the serializer is in
     * the process of serializing a document.
     * <p>
     *  指定应将文档序列化的写入程序。在序列化程序正在序列化文档的过程中,不应调用此方法。
     * 
     */
    public void setOutputCharStream( Writer output );


    /**
     * Specifies an output format for this serializer. It the
     * serializer has already been associated with an output format,
     * it will switch to the new format. This method should not be
     * called while the serializer is in the process of serializing
     * a document.
     *
     * <p>
     *  指定此序列化程序的输出格式。它已经与一个输出格式相关联,它将切换到新的格式。在序列化程序正在序列化文档的过程中,不应调用此方法。
     * 
     * 
     * @param format The output format to use
     */
    public void setOutputFormat( OutputFormat format );


    /**
     * Return a {@link DocumentHandler} interface into this serializer.
     * If the serializer does not support the {@link DocumentHandler}
     * interface, it should return null.
     * <p>
     *  将{@link DocumentHandler}接口返回到此序列化程序。如果序列化器不支持{@link DocumentHandler}接口,它应该返回null。
     * 
     */
    public DocumentHandler asDocumentHandler()
        throws IOException;


    /**
     * Return a {@link ContentHandler} interface into this serializer.
     * If the serializer does not support the {@link ContentHandler}
     * interface, it should return null.
     * <p>
     * 将{@link ContentHandler}接口返回到此序列化程序。如果序列化器不支持{@link ContentHandler}接口,它应该返回null。
     * 
     */
    public ContentHandler asContentHandler()
        throws IOException;


    /**
     * Return a {@link DOMSerializer} interface into this serializer.
     * If the serializer does not support the {@link DOMSerializer}
     * interface, it should return null.
     * <p>
     *  将{@link DOMSerializer}接口返回到此序列化程序中。如果序列化器不支持{@link DOMSerializer}接口,它应该返回null。
     */
    public DOMSerializer asDOMSerializer()
        throws IOException;


}
