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

package com.sun.org.apache.xerces.internal.xni;

/**
 * This handler interface contains methods necessary to receive
 * information about document elements and content.
 * <p>
 * <strong>Note:</strong> Some of these methods overlap methods
 * found in the XMLDocumentHandler interface.
 *
 * <p>
 *  此处理程序接口包含接收有关文档元素和内容的信息所必需的方法。
 * <p>
 *  <strong>注意</strong>：其中一些方法与XMLDocumentHandler界面中的方法重叠。
 * 
 * 
 * @see XMLDocumentHandler
 *
 * @author Andy Clark, IBM
 */
public interface XMLDocumentFragmentHandler {

    //
    // XMLDocumentFragmentHandler methods
    //

    /**
     * The start of the document fragment.
     *
     * <p>
     *  文档片段的开始。
     * 
     * 
     * @param locator          The document locator, or null if the
     *                         document location cannot be reported
     *                         during the parsing of this fragment.
     *                         However, it is <em>strongly</em>
     *                         recommended that a locator be supplied
     *                         that can at least report the base
     *                         system identifier.
     * @param namespaceContext The namespace context in effect at the
     *                         start of this document fragment. This
     *                         object only represents the current context.
     *                         Implementors of this class are responsible
     *                         for copying the namespace bindings from the
     *                         the current context (and its parent contexts)
     *                         if that information is important.
     * @param augmentations    Additional information that may include infoset
     *                         augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void startDocumentFragment(XMLLocator locator,
                                      NamespaceContext namespaceContext,
                                      Augmentations augmentations)
        throws XNIException;

    /**
     * This method notifies the start of a general entity.
     * <p>
     * <strong>Note:</strong> This method is not called for entity references
     * appearing as part of attribute values.
     *
     * <p>
     *  此方法通知一般实体的开始。
     * <p>
     *  <strong>注意</strong>：对于作为属性值一部分显示的实体引用,不调用此方法。
     * 
     * 
     * @param name     The name of the general entity.
     * @param identifier The resource identifier.
     * @param encoding The auto-detected IANA encoding name of the entity
     *                 stream. This value will be null in those situations
     *                 where the entity encoding is not auto-detected (e.g.
     *                 internal entities or a document entity that is
     *                 parsed from a java.io.Reader).
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void startGeneralEntity(String name,
                                   XMLResourceIdentifier identifier,
                                   String encoding,
                                   Augmentations augmentations) throws XNIException;

    /**
     * Notifies of the presence of a TextDecl line in an entity. If present,
     * this method will be called immediately following the startEntity call.
     * <p>
     * <strong>Note:</strong> This method will never be called for the
     * document entity; it is only called for external general entities
     * referenced in document content.
     * <p>
     * <strong>Note:</strong> This method is not called for entity references
     * appearing as part of attribute values.
     *
     * <p>
     *  通知实体中存在TextDecl行。如果存在,此方法将在startEntity调用之后立即调用。
     * <p>
     *  <strong>注意：</strong>此方法将永远不会为文档实体调用;它只被要求在文档内容中引用的外部通用实体。
     * <p>
     * <strong>注意</strong>：对于作为属性值一部分显示的实体引用,不调用此方法。
     * 
     * 
     * @param version  The XML version, or null if not specified.
     * @param encoding The IANA encoding name of the entity.
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void textDecl(String version, String encoding,
                         Augmentations augmentations) throws XNIException;

    /**
     * This method notifies the end of a general entity.
     * <p>
     * <strong>Note:</strong> This method is not called for entity references
     * appearing as part of attribute values.
     *
     * <p>
     *  此方法通知一般实体的结束。
     * <p>
     *  <strong>注意</strong>：对于作为属性值一部分显示的实体引用,不调用此方法。
     * 
     * 
     * @param name The name of the general entity.
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void endGeneralEntity(String name, Augmentations augmentations)
        throws XNIException;

    /**
     * A comment.
     *
     * <p>
     *  评论。
     * 
     * 
     * @param text The text in the comment.
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by application to signal an error.
     */
    public void comment(XMLString text, Augmentations augmentations)
        throws XNIException;

    /**
     * A processing instruction. Processing instructions consist of a
     * target name and, optionally, text data. The data is only meaningful
     * to the application.
     * <p>
     * Typically, a processing instruction's data will contain a series
     * of pseudo-attributes. These pseudo-attributes follow the form of
     * element attributes but are <strong>not</strong> parsed or presented
     * to the application as anything other than text. The application is
     * responsible for parsing the data.
     *
     * <p>
     *  一个处理指令。处理指令由目标名称和可选的文本数据组成。数据只对应用程序有意义。
     * <p>
     *  通常,处理指令的数据将包含一系列伪属性。这些伪属性遵循元素属性的形式,但<strong>不</strong>作为除文本之外的任何东西解析或呈现给应用程序。应用程序负责解析数据。
     * 
     * 
     * @param target The target.
     * @param data   The data or null if none specified.
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void processingInstruction(String target, XMLString data,
                                      Augmentations augmentations)
        throws XNIException;

    /**
     * The start of an element.
     *
     * <p>
     *  元素的开始。
     * 
     * 
     * @param element    The name of the element.
     * @param attributes The element attributes.
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void startElement(QName element, XMLAttributes attributes,
                             Augmentations augmentations) throws XNIException;

    /**
     * An empty element.
     *
     * <p>
     *  空元素。
     * 
     * 
     * @param element    The name of the element.
     * @param attributes The element attributes.
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void emptyElement(QName element, XMLAttributes attributes,
                             Augmentations augmentations) throws XNIException;

    /**
     * Character content.
     *
     * <p>
     *  字符内容。
     * 
     * 
     * @param text The content.
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void characters(XMLString text, Augmentations augmentations)
        throws XNIException;

    /**
     * Ignorable whitespace. For this method to be called, the document
     * source must have some way of determining that the text containing
     * only whitespace characters should be considered ignorable. For
     * example, the validator can determine if a length of whitespace
     * characters in the document are ignorable based on the element
     * content model.
     *
     * <p>
     *  可怕的空格。对于要调用的此方法,文档源必须具有某种方式确定仅包含空格字符的文本应该被视为可忽略。例如,验证器可以基于元素内容模型来确定文档中的空白字符的长度是否可忽略。
     * 
     * 
     * @param text The ignorable whitespace.
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void ignorableWhitespace(XMLString text,
                                    Augmentations augmentations)
        throws XNIException;

    /**
     * The end of an element.
     *
     * <p>
     *  元素的结尾。
     * 
     * 
     * @param element The name of the element.
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void endElement(QName element, Augmentations augmentations)
        throws XNIException;

    /**
     * The start of a CDATA section.
     *
     * <p>
     *  CDATA节的开始。
     * 
     * 
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void startCDATA(Augmentations augmentations) throws XNIException;

    /**
     * The end of a CDATA section.
     *
     * <p>
     *  CDATA段的结尾。
     * 
     * 
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void endCDATA(Augmentations augmentations) throws XNIException;

    /**
     * The end of the document fragment.
     *
     * <p>
     *  文档片段的结尾。
     * 
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void endDocumentFragment(Augmentations augmentations)
        throws XNIException;

} // interface XMLDocumentFragmentHandler
