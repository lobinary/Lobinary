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

package com.sun.org.apache.xerces.internal.parsers;

import com.sun.org.apache.xerces.internal.xni.Augmentations;
import com.sun.org.apache.xerces.internal.xni.NamespaceContext;
import com.sun.org.apache.xerces.internal.xni.QName;
import com.sun.org.apache.xerces.internal.xni.XMLAttributes;
import com.sun.org.apache.xerces.internal.xni.XMLDTDContentModelHandler;
import com.sun.org.apache.xerces.internal.xni.XMLDTDHandler;
import com.sun.org.apache.xerces.internal.xni.XMLDocumentHandler;
import com.sun.org.apache.xerces.internal.xni.XMLLocator;
import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;
import com.sun.org.apache.xerces.internal.xni.XMLString;
import com.sun.org.apache.xerces.internal.xni.XNIException;
import com.sun.org.apache.xerces.internal.xni.parser.XMLDTDContentModelSource;
import com.sun.org.apache.xerces.internal.xni.parser.XMLDTDSource;
import com.sun.org.apache.xerces.internal.xni.parser.XMLDocumentSource;
import com.sun.org.apache.xerces.internal.xni.parser.XMLParserConfiguration;

/**
 * This is the base class for all XML document parsers. XMLDocumentParser
 * provides a common implementation shared by the various document parsers
 * in the Xerces package. While this class is provided for convenience, it
 * does not prevent other kinds of parsers to be constructed using the XNI
 * interfaces.
 *
 * <p>
 *  这是所有XML文档解析器的基类。 XMLDocumentParser提供了由Xerces软件包中的各种文档解析器共享的公共实现。
 * 虽然这个类是为了方便而提供的,但它不会阻止使用XNI接口构造其他类型的解析器。
 * 
 * 
 * @author Arnaud  Le Hors, IBM
 * @author Andy Clark, IBM
 *
 */
public abstract class AbstractXMLDocumentParser
    extends XMLParser
    implements XMLDocumentHandler, XMLDTDHandler, XMLDTDContentModelHandler {

    //
    // Data
    //

    // state

    /** True if inside DTD. */
    protected boolean fInDTD;

    /** Document source*/
    protected XMLDocumentSource fDocumentSource;

    /** DTD source*/
    protected XMLDTDSource fDTDSource;

    /** DTD content model source*/
    protected XMLDTDContentModelSource fDTDContentModelSource;

    //
    // Constructors
    //

    /**
     * Constructs a document parser using the default symbol table
     * and grammar pool.
     * <p>
     *  使用默认符号表和语法池构造文档解析器。
     * 
     */
    protected AbstractXMLDocumentParser(XMLParserConfiguration config) {
        super(config);

        // set handlers
        config.setDocumentHandler(this);
        config.setDTDHandler(this);
        config.setDTDContentModelHandler(this);

    } // <init>(XMLParserConfiguration)

    //
    // XMLDocumentHandler methods
    //

    /**
     * The start of the document.
     *
     * <p>
     *  文档的开始。
     * 
     * 
     * @param locator The system identifier of the entity if the entity
     *                 is external, null otherwise.
     * @param encoding The auto-detected IANA encoding name of the entity
     *                 stream. This value will be null in those situations
     *                 where the entity encoding is not auto-detected (e.g.
     *                 internal entities or a document entity that is
     *                 parsed from a java.io.Reader).
     * @param namespaceContext
     *                 The namespace context in effect at the
     *                 start of this document.
     *                 This object represents the current context.
     *                 Implementors of this class are responsible
     *                 for copying the namespace bindings from the
     *                 the current context (and its parent contexts)
     *                 if that information is important.
     * @param augs   Additional information that may include infoset augmentations
     *
     * @throws XNIException Thrown by handler to signal an error.
     */

    public void startDocument(XMLLocator locator, String encoding,
                              NamespaceContext namespaceContext, Augmentations augs)
        throws XNIException {
    } // startDocument(XMLLocator,String)

    /**
     * Notifies of the presence of an XMLDecl line in the document. If
     * present, this method will be called immediately following the
     * startDocument call.
     *
     * <p>
     *  通知文档中存在XMLDecl行。如果存在,此方法将在startDocument调用后立即调用。
     * 
     * 
     * @param version    The XML version.
     * @param encoding   The IANA encoding name of the document, or null if
     *                   not specified.
     * @param standalone The standalone value, or null if not specified.
     * @param augs   Additional information that may include infoset augmentations
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void xmlDecl(String version, String encoding, String standalone, Augmentations augs)
        throws XNIException {
    } // xmlDecl(String,String,String)

    /**
     * Notifies of the presence of the DOCTYPE line in the document.
     *
     * <p>
     *  通知文档中DOCTYPE行的存在。
     * 
     * 
     * @param rootElement The name of the root element.
     * @param publicId    The public identifier if an external DTD or null
     *                    if the external DTD is specified using SYSTEM.
     * @param systemId    The system identifier if an external DTD, null
     * @param augs   Additional information that may include infoset augmentations
     *                    otherwise.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void doctypeDecl(String rootElement, String publicId, String systemId, Augmentations augs)
        throws XNIException {
    } // doctypeDecl(String,String,String)

    /**
     * The start of an element. If the document specifies the start element
     * by using an empty tag, then the startElement method will immediately
     * be followed by the endElement method, with no intervening methods.
     *
     * <p>
     * 元素的开始。如果文档通过使用空标签指定start元素,那么startElement方法将立即跟随endElement方法,没有中间方法。
     * 
     * 
     * @param element    The name of the element.
     * @param attributes The element attributes.
     * @param augs   Additional information that may include infoset augmentations
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void startElement(QName element, XMLAttributes attributes, Augmentations augs)
        throws XNIException {
    } // startElement(QName,XMLAttributes)

    /**
     * An empty element.
     *
     * <p>
     *  空元素。
     * 
     * 
     * @param element    The name of the element.
     * @param attributes The element attributes.
     * @param augs   Additional information that may include infoset augmentations
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void emptyElement(QName element, XMLAttributes attributes, Augmentations augs)
        throws XNIException {

        startElement(element, attributes, augs);
        endElement(element, augs);

    } // emptyElement(QName,XMLAttributes)

    /**
     * Character content.
     *
     * <p>
     *  字符内容。
     * 
     * 
     * @param text The content.
     * @param augs   Additional information that may include infoset augmentations
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void characters(XMLString text, Augmentations augs) throws XNIException {
    } // characters(XMLString)

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
     * @param augs   Additional information that may include infoset augmentations
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void ignorableWhitespace(XMLString text, Augmentations augs) throws XNIException {
    } // ignorableWhitespace(XMLString)

    /**
     * The end of an element.
     *
     * <p>
     *  元素的结尾。
     * 
     * 
     * @param element The name of the element.
     * @param augs   Additional information that may include infoset augmentations
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void endElement(QName element, Augmentations augs) throws XNIException {
    } // endElement(QName)

    /**
     * The start of a CDATA section.
     * <p>
     *  CDATA节的开始。
     * 
     * 
     * @param augs   Additional information that may include infoset augmentations
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void startCDATA(Augmentations augs) throws XNIException {
    } // startCDATA()

    /**
     * The end of a CDATA section.
     * <p>
     *  CDATA段的结尾。
     * 
     * 
     * @param augs   Additional information that may include infoset augmentations
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void endCDATA(Augmentations augs) throws XNIException {
    } // endCDATA()

    /**
     * The end of the document.
     * <p>
     *  文档的结尾。
     * 
     * 
     * @param augs   Additional information that may include infoset augmentations
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void endDocument(Augmentations augs) throws XNIException {
    } // endDocument()


    /**
     * This method notifies the start of an entity.
     * <p>
     * <strong>Note:</strong> This method is not called for entity references
     * appearing as part of attribute values.
     *
     * <p>
     *  此方法通知实体的开始。
     * <p>
     *  <strong>注意</strong>：对于作为属性值一部分显示的实体引用,不调用此方法。
     * 
     * 
     * @param name     The name of the entity.
     * @param identifier The resource identifier.
     * @param encoding The auto-detected IANA encoding name of the entity
     *                 stream. This value will be null in those situations
     *                 where the entity encoding is not auto-detected (e.g.
     *                 internal entities or a document entity that is
     *                 parsed from a java.io.Reader).
     * @param augs     Additional information that may include infoset augmentations
     *
     * @exception XNIException Thrown by handler to signal an error.
     */
    public void startGeneralEntity(String name,
                                   XMLResourceIdentifier identifier,
                                   String encoding,
                                   Augmentations augs) throws XNIException {
    } // startGeneralEntity(String,XMLResourceIdentifier,String,Augmentations)

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
     *  <strong>注意</strong>：对于作为属性值一部分显示的实体引用,不调用此方法。
     * 
     * 
     * @param version  The XML version, or null if not specified.
     * @param encoding The IANA encoding name of the entity.
     * @param augs     Additional information that may include infoset augmentations
     *
     * @exception XNIException
     *                   Thrown by handler to signal an error.
     */
    public void textDecl(String version, String encoding, Augmentations augs) throws XNIException {
    } // textDecl(String, String, Augmentations)

    /**
     * This method notifies the end of an entity.
     * <p>
     * <strong>Note:</strong> This method is not called for entity references
     * appearing as part of attribute values.
     *
     * <p>
     *  此方法通知实体的结束。
     * <p>
     *  <strong>注意</strong>：对于作为属性值一部分显示的实体引用,不调用此方法。
     * 
     * 
     * @param name   The name of the entity.
     * @param augs   Additional information that may include infoset augmentations
     *
     * @exception XNIException
     *                   Thrown by handler to signal an error.
     */
    public void endGeneralEntity(String name, Augmentations augs)
        throws XNIException {
    } // endGeneralEntity(String,Augmentations)

    /**
     * A comment.
     *
     * <p>
     *  评论。
     * 
     * 
     * @param text   The text in the comment.
     * @param augs   Additional information that may include infoset augmentations
     *
     * @exception XNIException
     *                   Thrown by application to signal an error.
     */
    public void comment(XMLString text, Augmentations augs) throws XNIException {
    } // comment (XMLString, Augmentations)

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
     * 一个处理指令。处理指令由目标名称和可选的文本数据组成。数据只对应用程序有意义。
     * <p>
     *  通常,处理指令的数据将包含一系列伪属性。这些伪属性遵循元素属性的形式,但<strong>不</strong>作为除文本之外的任何东西解析或呈现给应用程序。应用程序负责解析数据。
     * 
     * 
     * @param target The target.
     * @param data   The data or null if none specified.
     * @param augs   Additional information that may include infoset augmentations
     *
     * @exception XNIException
     *                   Thrown by handler to signal an error.
     */
    public void processingInstruction(String target, XMLString data, Augmentations augs)
        throws XNIException {
    } // processingInstruction(String, XMLString, Augmentations)


    /** Sets the document source */
    public void setDocumentSource(XMLDocumentSource source){
        fDocumentSource = source;
    } // setDocumentSource

    /** Returns the document source */
    public XMLDocumentSource getDocumentSource (){
        return fDocumentSource;
    } // getDocumentSource
    //
    // XMLDTDHandler methods
    //

    /**
     * The start of the DTD.
     *
     * <p>
     *  DTD的开始。
     * 
     * 
     * @param locator  The document locator, or null if the document
     *                 location cannot be reported during the parsing of
     *                 the document DTD. However, it is <em>strongly</em>
     *                 recommended that a locator be supplied that can
     *                 at least report the base system identifier of the
     *                 DTD.
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void startDTD(XMLLocator locator, Augmentations augs) throws XNIException {
        fInDTD = true;
    } // startDTD(XMLLocator)


    /**
     * The start of the DTD external subset.
     *
     * <p>
     *  DTD外部子集的开始。
     * 
     * 
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void startExternalSubset(XMLResourceIdentifier identifier, Augmentations augmentations)
        throws XNIException {
    } // startExternalSubset(Augmentations)

    /**
     * The end of the DTD external subset.
     *
     * <p>
     *  DTD外部子集的结束。
     * 
     * 
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void endExternalSubset(Augmentations augmentations)
        throws XNIException {
    } // endExternalSubset(Augmentations)

    /**
     * This method notifies the start of an entity.
     * <p>
     * <strong>Note:</strong> This method is not called for entity references
     * appearing as part of attribute values.
     *
     * <p>
     *  此方法通知实体的开始。
     * <p>
     *  <strong>注意</strong>：对于作为属性值一部分显示的实体引用,不调用此方法。
     * 
     * 
     * @param name     The name of the entity.
     * @param identifier The resource identifier.
     * @param encoding The auto-detected IANA encoding name of the entity
     *                 stream. This value will be null in those situations
     *                 where the entity encoding is not auto-detected (e.g.
     *                 internal entities or a document entity that is
     *                 parsed from a java.io.Reader).
     * @param augs     Additional information that may include infoset augmentations
     *
     * @exception XNIException Thrown by handler to signal an error.
     */
    public void startParameterEntity(String name,
                                     XMLResourceIdentifier identifier,
                                     String encoding,
                                     Augmentations augs) throws XNIException {
    } // startParameterEntity(String,XMLResourceIdentifier,String,Augmentations)

    /**
     * This method notifies the end of an entity.
     * <p>
     * <strong>Note:</strong> This method is not called for entity references
     * appearing as part of attribute values.
     *
     * <p>
     *  此方法通知实体的结束。
     * <p>
     *  <strong>注意</strong>：对于作为属性值一部分显示的实体引用,不调用此方法。
     * 
     * 
     * @param name   The name of the entity.
     * @param augs   Additional information that may include infoset augmentations
     *
     * @exception XNIException
     *                   Thrown by handler to signal an error.
     */
    public void endParameterEntity(String name, Augmentations augs)
        throws XNIException {
    } // endParameterEntity(String,Augmentations)

    /**
     * Characters within an IGNORE conditional section.
     *
     * <p>
     *  IGNORE条件部分中的字符。
     * 
     * 
     * @param text The ignored text.
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
     public void ignoredCharacters(XMLString text, Augmentations augs) throws XNIException {
     } // ignoredCharacters(XMLString, Augmentations)

    /**
     * An element declaration.
     *
     * <p>
     *  元素声明。
     * 
     * 
     * @param name         The name of the element.
     * @param contentModel The element content model.
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void elementDecl(String name, String contentModel, Augmentations augs)
        throws XNIException {
    } // elementDecl(String,String)

    /**
     * The start of an attribute list.
     *
     * <p>
     *  属性列表的开始。
     * 
     * 
     * @param elementName The name of the element that this attribute
     *                    list is associated with.
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void startAttlist(String elementName, Augmentations augs) throws XNIException {
    } // startAttlist(String)

    /**
     * An attribute declaration.
     *
     * <p>
     *  属性声明。
     * 
     * 
     * @param elementName   The name of the element that this attribute
     *                      is associated with.
     * @param attributeName The name of the attribute.
     * @param type          The attribute type. This value will be one of
     *                      the following: "CDATA", "ENTITY", "ENTITIES",
     *                      "ENUMERATION", "ID", "IDREF", "IDREFS",
     *                      "NMTOKEN", "NMTOKENS", or "NOTATION".
     * @param enumeration   If the type has the value "ENUMERATION" or
     *                      "NOTATION", this array holds the allowed attribute
     *                      values; otherwise, this array is null.
     * @param defaultType   The attribute default type. This value will be
     *                      one of the following: "#FIXED", "#IMPLIED",
     *                      "#REQUIRED", or null.
     * @param defaultValue  The attribute default value, or null if no
     *                      default value is specified.
     * @param nonNormalizedDefaultValue  The attribute default value with no normalization
     *                      performed, or null if no default value is specified.
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void attributeDecl(String elementName, String attributeName,
                              String type, String[] enumeration,
                              String defaultType, XMLString defaultValue,
                              XMLString nonNormalizedDefaultValue, Augmentations augs)
        throws XNIException {
    } // attributeDecl(String,String,String,String[],String,XMLString, XMLString, Augmentations)

    /**
     * The end of an attribute list.
     *
     * <p>
     *  属性列表的结尾。
     * 
     * 
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void endAttlist(Augmentations augs) throws XNIException {
    } // endAttlist()

    /**
     * An internal entity declaration.
     *
     * <p>
     *  内部实体声明。
     * 
     * 
     * @param name The name of the entity. Parameter entity names start with
     *             '%', whereas the name of a general entity is just the
     *             entity name.
     * @param text The value of the entity.
     * @param nonNormalizedText The non-normalized value of the entity. This
     *             value contains the same sequence of characters that was in
     *             the internal entity declaration, without any entity
     *             references expanded.
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void internalEntityDecl(String name, XMLString text,
                                   XMLString nonNormalizedText, Augmentations augs)
        throws XNIException {
    } // internalEntityDecl(String,XMLString,XMLString)

    /**
     * An external entity declaration.
     *
     * <p>
     *  外部实体声明。
     * 
     * 
     * @param name     The name of the entity. Parameter entity names start
     *                 with '%', whereas the name of a general entity is just
     *                 the entity name.
     * @param identifier    An object containing all location information
     *                      pertinent to this entity.
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void externalEntityDecl(String name, XMLResourceIdentifier identifier,
                                   Augmentations augs) throws XNIException {
    } // externalEntityDecl(String,XMLResourceIdentifier, Augmentations)

    /**
     * An unparsed entity declaration.
     *
     * <p>
     *  未解析的实体声明。
     * 
     * 
     * @param name     The name of the entity.
     * @param identifier    An object containing all location information
     *                      pertinent to this entity.
     * @param notation The name of the notation.
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void unparsedEntityDecl(String name, XMLResourceIdentifier identifier,
                                   String notation, Augmentations augs) throws XNIException {
    } // unparsedEntityDecl(String,XMLResourceIdentifier, String, Augmentations)

    /**
     * A notation declaration
     *
     * <p>
     *  符号声明
     * 
     * 
     * @param name     The name of the notation.
     * @param identifier    An object containing all location information
     *                      pertinent to this notation.
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void notationDecl(String name, XMLResourceIdentifier identifier,
        Augmentations augs)
        throws XNIException {
    } // notationDecl(String,XMLResourceIdentifier, Augmentations)

    /**
     * The start of a conditional section.
     *
     * <p>
     *  条件段的开始。
     * 
     * 
     * @param type The type of the conditional section. This value will
     *             either be CONDITIONAL_INCLUDE or CONDITIONAL_IGNORE.
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     *
     * @see #CONDITIONAL_INCLUDE
     * @see #CONDITIONAL_IGNORE
     */
    public void startConditional(short type, Augmentations augs) throws XNIException  {
    } // startConditional(short)

    /**
     * The end of a conditional section.
     *
     * <p>
     *  条件段的结束。
     * 
     * 
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void endConditional(Augmentations augs) throws XNIException {
    } // endConditional()

    /**
     * The end of the DTD.
     *
     * <p>
     *  DTD的结束。
     * 
     * 
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void endDTD(Augmentations augs) throws XNIException {
        fInDTD = false;
    } // endDTD()

    // set the source of this handler
    public void setDTDSource(XMLDTDSource source) {
        fDTDSource = source;
    }

    // return the source from which this handler derives its events
    public XMLDTDSource getDTDSource() {
        return fDTDSource;
    }

    //
    // XMLDTDContentModelHandler methods
    //

    /**
     * The start of a content model. Depending on the type of the content
     * model, specific methods may be called between the call to the
     * startContentModel method and the call to the endContentModel method.
     *
     * <p>
     *  内容模型的开始。根据内容模型的类型,可以在调用startContentModel方法和调用endContentModel方法之间调用特定方法。
     * 
     * 
     * @param elementName The name of the element.
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void startContentModel(String elementName, Augmentations augs) throws XNIException {
    } // startContentModel(String, Augmentations)

    /**
     * A content model of ANY.
     *
     * <p>
     *  ANY的内容模型。
     * 
     * 
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     *
     * @see #empty
     * @see #startGroup
     */
    public void any(Augmentations augs) throws XNIException {
    } // any(Augmentations)

    /**
     * A content model of EMPTY.
     *
     * <p>
     *  EMPTY的内容模型。
     * 
     * 
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     *
     * @see #any
     * @see #startGroup
     */
    public void empty(Augmentations augs) throws XNIException {
    } // empty(Augmentations)

    /**
     * A start of either a mixed or children content model. A mixed
     * content model will immediately be followed by a call to the
     * <code>pcdata()</code> method. A children content model will
     * contain additional groups and/or elements.
     *
     * <p>
     * 混合或儿童内容模型的开始。混合内容模型将立即调用<code> pcdata()</code>方法。子内容模型将包含其他组和/或元素。
     * 
     * 
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     *
     * @see #any
     * @see #empty
     */
    public void startGroup(Augmentations augs) throws XNIException {
    } // stargGroup(Augmentations)

    /**
     * The appearance of "#PCDATA" within a group signifying a
     * mixed content model. This method will be the first called
     * following the content model's <code>startGroup()</code>.
     *
     * <p>
     *  表示混合内容模型的组中的"#PCDATA"的外观。这个方法将首先被调用遵循内容模型的<code> startGroup()</code>。
     * 
     * 
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     *
     * @see #startGroup
     */
    public void pcdata(Augmentations augs) throws XNIException {
    } // pcdata(Augmentations)

    /**
     * A referenced element in a mixed or children content model.
     *
     * <p>
     *  混合或子内容模型中引用的元素。
     * 
     * 
     * @param elementName The name of the referenced element.
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void element(String elementName, Augmentations augs) throws XNIException {
    } // element(String, Augmentations)

    /**
     * The separator between choices or sequences of a mixed or children
     * content model.
     *
     * <p>
     *  混合或子内容模型的选择或序列之间的分隔符。
     * 
     * 
     * @param separator The type of children separator.
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     *
     * @see #SEPARATOR_CHOICE
     * @see #SEPARATOR_SEQUENCE
     */
    public void separator(short separator, Augmentations augs) throws XNIException {
    } // separator(short, Augmentations)

    /**
     * The occurrence count for a child in a children content model or
     * for the mixed content model group.
     *
     * <p>
     *  子内容模型中的子级或混合内容模型组的子级的发生计数。
     * 
     * 
     * @param occurrence The occurrence count for the last element
     *                   or group.
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     *
     * @see #OCCURS_ZERO_OR_ONE
     * @see #OCCURS_ZERO_OR_MORE
     * @see #OCCURS_ONE_OR_MORE
     */
    public void occurrence(short occurrence, Augmentations augs) throws XNIException {
    } // occurence(short, Augmentations)

    /**
     * The end of a group for mixed or children content models.
     *
     * <p>
     *  一个组的结尾的混合或儿童内容模型。
     * 
     * 
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void endGroup(Augmentations augs) throws XNIException {
    } // endGroup(Augmentations)

    /**
     * The end of a content model.
     *
     * <p>
     *  内容模型的结束。
     * 
     * 
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void endContentModel(Augmentations augs) throws XNIException {
    } // endContentModel(Augmentations)

    // set content model source
    public void setDTDContentModelSource(XMLDTDContentModelSource source) {
        fDTDContentModelSource = source;
    }

    // get content model source
    public XMLDTDContentModelSource getDTDContentModelSource() {
        return fDTDContentModelSource;
    }

    //
    // Protected methods
    //

    /**
     * reset all components before parsing
     * <p>
     *  在解析之前重置所有组件
     */
    protected void reset() throws XNIException {
        super.reset();
        fInDTD = false;
    } // reset()

} // class AbstractXMLDocumentParser
