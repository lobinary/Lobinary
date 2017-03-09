/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2003-2004 The Apache Software Foundation.
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
 *  版权所有2003-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: SerializationHandler.java,v 1.2.4.1 2005/09/15 08:15:22 suresh_emailid Exp $
 * <p>
 *  $ Id：SerializationHandler.java,v 1.2.4.1 2005/09/15 08:15:22 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.serializer;

import java.io.IOException;

import javax.xml.transform.Transformer;

import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DeclHandler;

/**
 * This interface is the one that a serializer implements. It is a group of
 * other interfaces, such as ExtendedContentHandler, ExtendedLexicalHandler etc.
 * In addition there are other methods, such as reset().
 *
 * This class is public only because it is used in another package,
 * it is not a public API.
 *
 * @xsl.usage internal
 * <p>
 *  这个接口是串行器实现的接口。它是一组其他接口,如ExtendedContentHandler,ExtendedLexicalHandler等。此外还有其他方法,如reset()。
 * 
 *  这个类只是公共的,因为它在另一个包中使用,它不是一个公共API。
 * 
 *  @ xsl.usage internal
 * 
 */
public interface SerializationHandler
    extends
        ExtendedContentHandler,
        ExtendedLexicalHandler,
        XSLOutputAttributes,
        DeclHandler,
        org.xml.sax.DTDHandler,
        ErrorHandler,
        DOMSerializer,
        Serializer
{
    /**
     * Set the SAX Content handler that the serializer sends its output to. This
     * method only applies to a ToSAXHandler, not to a ToStream serializer.
     *
     * <p>
     *  设置串行器发送其输出的SAX内容处理程序。此方法仅适用于ToSAXHandler,而不适用于ToStream序列化程序。
     * 
     * 
     * @see Serializer#asContentHandler()
     * @see ToSAXHandler
     */
    public void setContentHandler(ContentHandler ch);

    public void close();

    /**
     * Notify that the serializer should take this DOM node as input to be
     * serialized.
     *
     * <p>
     *  通知序列化器应将此DOM节点作为要序列化的输入。
     * 
     * 
     * @param node the DOM node to be serialized.
     * @throws IOException
     */
    public void serialize(Node node) throws IOException;
    /**
     * Turns special character escaping on/off.
     *
     * Note that characters will
     * never, even if this option is set to 'true', be escaped within
     * CDATA sections in output XML documents.
     *
     * <p>
     *  打开/关闭特殊字符转义。
     * 
     * 请注意,即使此选项设置为"true",字符也不会在输出XML文档的CDATA节中转义。
     * 
     * 
     * @param escape true if escaping is to be set on.
     */
    public boolean setEscaping(boolean escape) throws SAXException;

    /**
     * Set the number of spaces to indent for each indentation level.
     * <p>
     *  将每个缩进级别的空格数设置为缩进。
     * 
     * 
     * @param spaces the number of spaces to indent for each indentation level.
     */
    public void setIndentAmount(int spaces);

    /**
     * Set the transformer associated with the serializer.
     * <p>
     *  设置与串行器关联的变压器。
     * 
     * 
     * @param transformer the transformer associated with the serializer.
     */
    public void setTransformer(Transformer transformer);

    /**
     * Get the transformer associated with the serializer.
     * <p>
     *  获取与序列化器相关联的变压器。
     * 
     * 
     * @return Transformer the transformer associated with the serializer.
     */
    public Transformer getTransformer();

    /**
     * Used only by TransformerSnapshotImpl to restore the serialization
     * to a previous state.
     *
     * <p>
     *  仅由TransformerSnapshotImpl使用以将序列化恢复到先前的状态。
     * 
     * 
     * @param mappings NamespaceMappings
     */
    public void setNamespaceMappings(NamespaceMappings mappings);

    /**
     * Flush any pending events currently queued up in the serializer. This will
     * flush any input that the serializer has which it has not yet sent as
     * output.
     * <p>
     *  清除当前在序列化器中排队的任何挂起事件。这将刷新串行器尚未发送为输出的任何输入。
     * 
     */
    public void flushPending() throws SAXException;

    /**
     * Default behavior is to expand DTD entities,
     * that is the initall default value is true.
     * <p>
     *  默认行为是扩展DTD实体,即initall默认值为true。
     * 
     * 
     * @param expand true if DTD entities are to be expanded,
     * false if they are to be left as DTD entity references.
     */
    public void setDTDEntityExpansion(boolean expand);

    /**
     * Specify if the output will be treated as a standalone  property
     * <p>
     *  指定输出是否将被视为独立属性
     * 
     * @param isStandalone true if the http://www.oracle.com/xml/is-standalone is set to yes
     * @see OutputPropertiesFactory ORACLE_IS_STANDALONE
     */
    public void setIsStandalone(boolean b);

}
