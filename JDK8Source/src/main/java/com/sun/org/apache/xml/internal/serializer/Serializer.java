/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有1999-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: Serializer.java,v 1.2.4.1 2005/09/15 08:15:22 suresh_emailid Exp $
 * <p>
 *  $ Id：Serializer.java,v 1.2.4.1 2005/09/15 08:15:22 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.serializer;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Properties;

import org.xml.sax.ContentHandler;

/**
 * The Serializer interface is implemented by a serializer to enable users to:
 * <ul>
 * <li>get and set streams or writers
 * <li>configure the serializer with key/value properties
 * <li>get an org.xml.sax.ContentHandler or a DOMSerializer to provide input to
 * </ul>
 *
 * <p>
 * Here is an example using the asContentHandler() method:
 * <pre>
 * java.util.Properties props =
 *   OutputPropertiesFactory.getDefaultMethodProperties(Method.TEXT);
 * Serializer ser = SerializerFactory.getSerializer(props);
 * java.io.PrintStream ostream = System.out;
 * ser.setOutputStream(ostream);
 *
 * // Provide the SAX input events
 * ContentHandler handler = ser.asContentHandler();
 * handler.startDocument();
 * char[] chars = { 'a', 'b', 'c' };
 * handler.characters(chars, 0, chars.length);
 * handler.endDocument();
 *
 * ser.reset(); // get ready to use the serializer for another document
 *              // of the same output method (TEXT).
 * </pre>
 *
 * <p>
 * As an alternate to supplying a series of SAX events as input through the
 * ContentHandler interface, the input to serialize may be given as a DOM.
 * <p>
 * For example:
 * <pre>
 * org.w3c.dom.Document     inputDoc;
 * com.sun.org.apache.xml.internal.serializer.Serializer   ser;
 * java.io.Writer owriter;
 *
 * java.util.Properties props =
 *   OutputPropertiesFactory.getDefaultMethodProperties(Method.XML);
 * Serializer ser = SerializerFactory.getSerializer(props);
 * owriter = ...;  // create a writer to serialize the document to
 * ser.setWriter( owriter );
 *
 * inputDoc = ...; // create the DOM document to be serialized
 * DOMSerializer dser = ser.asDOMSerializer(); // a DOM will be serialized
 * dser.serialize(inputDoc); // serialize the DOM, sending output to owriter
 *
 * ser.reset(); // get ready to use the serializer for another document
 *              // of the same output method.
 * </pre>
 *
 * This interface is a public API.
 *
 * <p>
 *  串行器接口由串行器实现,以使用户能够：
 * <ul>
 *  <li>获取和设置流或作者<li>使用键/值属性配置序列化<li>获取org.xml.sax.ContentHandler或DOMSerializer以提供输入
 * </ul>
 * 
 * <p>
 *  这里是一个使用asContentHandler()方法的例子：
 * <pre>
 *  java.util.Properties props = OutputPropertiesFactory.getDefaultMethodProperties(Method.TEXT); Serial
 * izer ser = SerializerFactory.getSerializer(props); java.io.PrintStream ostream = System.out; ser.setO
 * utputStream(ostream);。
 * 
 * //提供SAX输入事件ContentHandler handler = ser.asContentHandler(); handler.startDocument(); char [] chars = 
 * {'a','b','c'}; handler.characters(chars,0,chars.length); handler.endDocument();。
 * 
 *  ser.reset(); //准备好对同一输出方法(TEXT)的另一个文档使用序列化。
 * </pre>
 * 
 * <p>
 *  作为通过ContentHandler接口提供一系列SAX事件作为输入的替代方案,序列化的输入可以作为DOM给出。
 * <p>
 *  例如：
 * <pre>
 *  org.w3c.dom.Document inputDoc; com.sun.org.apache.xml.internal.serializer.Serializer ser; java.io.Wr
 * iter owriter;。
 * 
 *  java.util.Properties props = OutputPropertiesFactory.getDefaultMethodProperties(Method.XML); Seriali
 * zer ser = SerializerFactory.getSerializer(props); owriter = ...; //创建一个作者以将文档序列化为ser.setWriter(owrite
 * r);。
 * 
 *  inputDoc = ...; //创建要序列化的DOM文档DOMSerializer dser = ser.asDOMSerializer(); //一个DOM将被序列化dser.serialize
 * (inputDoc); //序列化DOM,将输出发送到owriter。
 * 
 *  ser.reset(); //准备好对同一输出方法的另一个文档使用序列化。
 * </pre>
 * 
 *  此接口是一个公共API。
 * 
 * 
 * @see Method
 * @see OutputPropertiesFactory
 * @see SerializerFactory
 * @see DOMSerializer
 * @see ContentHandler
 *
 * @xsl.usage general
 */
public interface Serializer {

    /**
     * Specifies an output stream to which the document should be
     * serialized. This method should not be called while the
     * serializer is in the process of serializing a document.
     * <p>
     * The encoding specified in the output {@link Properties} is used, or
     * if no encoding was specified, the default for the selected
     * output method.
     * <p>
     * Only one of setWriter() or setOutputStream() should be called.
     *
     * <p>
     *  指定应将文档序列化的输出流。在序列化程序正在序列化文档的过程中,不应调用此方法。
     * <p>
     *  使用输出{@link属性}中指定的编码,或者如果未指定编码,则为所选输出方法的默认值。
     * <p>
     * 只应调用setWriter()或setOutputStream()中的一个。
     * 
     * 
     * @param output The output stream
     */
    public void setOutputStream(OutputStream output);

    /**
     * Get the output stream where the events will be serialized to.
     *
     * <p>
     *  获取事件将序列化到的输出流。
     * 
     * 
     * @return reference to the result stream, or null if only a writer was
     * set.
     */
    public OutputStream getOutputStream();

    /**
     * Specifies a writer to which the document should be serialized.
     * This method should not be called while the serializer is in
     * the process of serializing a document.
     * <p>
     * The encoding specified for the output {@link Properties} must be
     * identical to the output format used with the writer.
     *
     * <p>
     * Only one of setWriter() or setOutputStream() should be called.
     *
     * <p>
     *  指定应将文档序列化的写入程序。在序列化程序正在序列化文档的过程中,不应调用此方法。
     * <p>
     *  为输出{@link属性}指定的编码必须与编写器使用的输出格式相同。
     * 
     * <p>
     *  只应调用setWriter()或setOutputStream()中的一个。
     * 
     * 
     * @param writer The output writer stream
     */
    public void setWriter(Writer writer);

    /**
     * Get the character stream where the events will be serialized to.
     *
     * <p>
     *  获取字符流,其中事件将被序列化到。
     * 
     * 
     * @return Reference to the result Writer, or null.
     */
    public Writer getWriter();

    /**
     * Specifies an output format for this serializer. It the
     * serializer has already been associated with an output format,
     * it will switch to the new format. This method should not be
     * called while the serializer is in the process of serializing
     * a document.
     * <p>
     * The standard property keys supported are: "method", "version", "encoding",
     * "omit-xml-declaration", "standalone", doctype-public",
     * "doctype-system", "cdata-section-elements", "indent", "media-type".
     * These property keys and their values are described in the XSLT recommendation,
     * see {@link <a href="http://www.w3.org/TR/1999/REC-xslt-19991116"> XSLT 1.0 recommendation</a>}
     * <p>
     * The non-standard property keys supported are defined in {@link OutputPropertiesFactory}.
     *
     * <p>
     * This method can be called multiple times before a document is serialized. Each
     * time it is called more, or over-riding property values, can be specified. One
     * property value that can not be changed is that of the "method" property key.
     * <p>
     * The value of the "cdata-section-elements" property key is a whitespace
     * separated list of elements. If the element is in a namespace then
     * value is passed in this format: {uri}localName
     * <p>
     * If the "cdata-section-elements" key is specified on multiple calls
     * to this method the set of elements specified in the value
     * is not replaced from one call to the
     * next, but it is cumulative across the calls.
     *
     * <p>
     *  指定此序列化程序的输出格式。它已经与一个输出格式相关联,它将切换到新的格式。在序列化程序正在序列化文档的过程中,不应调用此方法。
     * <p>
     *  支持的标准属性键包括："method","version","encoding","omit-xml-declaration","standalone",doctype-public","doctyp
     * e-system","cdata-section- "indent","media-type"。
     * 这些属性键及其值在XSLT建议中描述,参见{@link <a href ="http://www.w3.org/TR/1999/REC-xslt- 19991116"> XSLT 1.0推荐</a>}。
     * <p>
     *  支持的非标准属性键在{@link OutputPropertiesFactory}中定义。
     * 
     * <p>
     *  在文档序列化之前,可以多次调用此方法。每次它被称为更多,或over-riding属性值,可以指定。无法更改的一个属性值是"方法"属性键的值。
     * <p>
     * "cdata-section-elements"属性键的值是空格分隔的元素列表。如果元素在命名空间中,则以下面的格式传递值：{uri} localName
     * <p>
     *  如果在对此方法的多个调用中指定了"cdata-section-elements"键,则该值中指定的元素集不会从一个调用替换到下一个调用,而是在调用中累积。
     * 
     * 
     * @param format The output format to use, as a set of key/value pairs.
     */
    public void setOutputFormat(Properties format);

    /**
     * Returns the output format properties for this serializer.
     *
     * <p>
     *  返回此序列化程序的输出格式属性。
     * 
     * 
     * @return The output format key/value pairs in use.
     */
    public Properties getOutputFormat();

    /**
     * Return a {@link ContentHandler} interface to provide SAX input to.
     * Through the returned object the document to be serailized,
     * as a series of SAX events, can be provided to the serialzier.
     * If the serializer does not support the {@link ContentHandler}
     * interface, it will return null.
     * <p>
     * In principle only one of asDOMSerializer() or asContentHander()
     * should be called.
     *
     * <p>
     *  返回一个{@link ContentHandler}接口以向其提供SAX输入。通过返回的对象,要被服务的文档,作为一系列SAX事件,可以提供给serialzier。
     * 如果序列化器不支持{@link ContentHandler}接口,它将返回null。
     * <p>
     *  原则上,应该调用asDOMSerializer()或asContentHander()中的一个。
     * 
     * 
     * @return A {@link ContentHandler} interface into this serializer,
     *  or null if the serializer is not SAX 2 capable
     * @throws IOException An I/O exception occured
     */
    public ContentHandler asContentHandler() throws IOException;

    /**
     * Return a {@link DOMSerializer} interface into this serializer.
     * Through the returned object the document to be serialized,
     * a DOM, can be provided to the serializer.
     * If the serializer does not support the {@link DOMSerializer}
     * interface, it should return null.
     * <p>
     * In principle only one of asDOMSerializer() or asContentHander()
     * should be called.
     *
     * <p>
     *  将{@link DOMSerializer}接口返回到此序列化程序中。通过返回的对象,要序列化的文档,一个DOM,可以提供给序列化器。
     * 如果序列化器不支持{@link DOMSerializer}接口,它应该返回null。
     * <p>
     *  原则上,应该调用asDOMSerializer()或asContentHander()中的一个。
     * 
     * @return A {@link DOMSerializer} interface into this serializer,
     *  or null if the serializer is not DOM capable
     * @throws IOException An I/O exception occured
     */
    public DOMSerializer asDOMSerializer() throws IOException;

    /**
     * This method resets the serializer.
     * If this method returns true, the
     * serializer may be used for subsequent serialization of new
     * documents. It is possible to change the output format and
     * output stream prior to serializing, or to reuse the existing
     * output format and output stream or writer.
     *
     * <p>
     * 
     * 
     * @return True if serializer has been reset and can be reused
     */
    public boolean reset();
}
