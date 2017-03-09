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

package com.sun.org.apache.xerces.internal.xni;

import com.sun.org.apache.xerces.internal.xni.parser.XMLDTDSource;

/**
 * The DTD handler interface defines callback methods to report
 * information items in the DTD of an XML document. Parser components
 * interested in DTD information implement this interface and are
 * registered as the DTD handler on the DTD source.
 *
 * <p>
 *  DTD处理程序接口定义回调方法以报告XML文档的DTD中的信息项。对DTD信息感兴趣的解析器组件实现此接口,并在DTD源上注册为DTD处理程序。
 * 
 * 
 * @see XMLDTDContentModelHandler
 *
 * @author Andy Clark, IBM
 *
 */
public interface XMLDTDHandler {

    //
    // Constants
    //

    /**
     * Conditional section: INCLUDE.
     *
     * <p>
     *  条件部分：INCLUDE。
     * 
     * 
     * @see #CONDITIONAL_IGNORE
     */
    public static final short CONDITIONAL_INCLUDE = 0;

    /**
     * Conditional section: IGNORE.
     *
     * <p>
     *  条件部分：IGNORE。
     * 
     * 
     * @see #CONDITIONAL_INCLUDE
     */
    public static final short CONDITIONAL_IGNORE = 1;

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
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void startDTD(XMLLocator locator, Augmentations augmentations)
        throws XNIException;

    /**
     * This method notifies of the start of a parameter entity. The parameter
     * entity name start with a '%' character.
     *
     * <p>
     *  该方法通知参数实体的开始。参数实体名称以"％"字符开头。
     * 
     * 
     * @param name     The name of the parameter entity.
     * @param identifier The resource identifier.
     * @param encoding The auto-detected IANA encoding name of the entity
     *                 stream. This value will be null in those situations
     *                 where the entity encoding is not auto-detected (e.g.
     *                 internal parameter entities).
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void startParameterEntity(String name,
                                     XMLResourceIdentifier identifier,
                                     String encoding,
                                     Augmentations augmentations) throws XNIException;

    /**
     * Notifies of the presence of a TextDecl line in an entity. If present,
     * this method will be called immediately following the startEntity call.
     * <p>
     * <strong>Note:</strong> This method is only called for external
     * parameter entities referenced in the DTD.
     *
     * <p>
     *  通知实体中存在TextDecl行。如果存在,此方法将在startEntity调用之后立即调用。
     * <p>
     *  <strong>注意</strong>：仅在DTD中引用的外部参数实体才调用此方法。
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
     * This method notifies the end of a parameter entity. Parameter entity
     * names begin with a '%' character.
     *
     * <p>
     * 此方法通知参数实体的结束。参数实体名称以"％"字符开头。
     * 
     * 
     * @param name The name of the parameter entity.
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void endParameterEntity(String name, Augmentations augmentations)
        throws XNIException;

    /**
     * The start of the DTD external subset.
     *
     * <p>
     *  DTD外部子集的开始。
     * 
     * 
     * @param identifier The resource identifier.
     * @param augmentations
     *                   Additional information that may include infoset
     *                   augmentations.
     * @exception XNIException
     *                   Thrown by handler to signal an error.
     */
    public void startExternalSubset(XMLResourceIdentifier identifier,
                                    Augmentations augmentations)
        throws XNIException;

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
     * An element declaration.
     *
     * <p>
     *  元素声明。
     * 
     * 
     * @param name         The name of the element.
     * @param contentModel The element content model.
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void elementDecl(String name, String contentModel,
                            Augmentations augmentations)
        throws XNIException;

    /**
     * The start of an attribute list.
     *
     * <p>
     *  属性列表的开始。
     * 
     * 
     * @param elementName The name of the element that this attribute
     *                    list is associated with.
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void startAttlist(String elementName,
                             Augmentations augmentations) throws XNIException;

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
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void attributeDecl(String elementName, String attributeName,
                              String type, String[] enumeration,
                              String defaultType, XMLString defaultValue,
                              XMLString nonNormalizedDefaultValue, Augmentations augmentations)
        throws XNIException;

    /**
     * The end of an attribute list.
     *
     * <p>
     *  属性列表的结尾。
     * 
     * 
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void endAttlist(Augmentations augmentations) throws XNIException;

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
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void internalEntityDecl(String name, XMLString text,
                                   XMLString nonNormalizedText,
                                   Augmentations augmentations)
        throws XNIException;

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
     *                      pertinent to this external entity.
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void externalEntityDecl(String name,
                                   XMLResourceIdentifier identifier,
                                   Augmentations augmentations)
        throws XNIException;

    /**
     * An unparsed entity declaration.
     *
     * <p>
     *  未解析的实体声明。
     * 
     * 
     * @param name     The name of the entity.
     * @param identifier    An object containing all location information
     *                      pertinent to this unparsed entity declaration.
     * @param notation The name of the notation.
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void unparsedEntityDecl(String name,
                                   XMLResourceIdentifier identifier,
                                   String notation, Augmentations augmentations)
        throws XNIException;

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
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void notationDecl(String name, XMLResourceIdentifier identifier,
                             Augmentations augmentations) throws XNIException;

    /**
     * The start of a conditional section.
     *
     * <p>
     *  条件段的开始。
     * 
     * 
     * @param type The type of the conditional section. This value will
     *             either be CONDITIONAL_INCLUDE or CONDITIONAL_IGNORE.
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     *
     * @see #CONDITIONAL_INCLUDE
     * @see #CONDITIONAL_IGNORE
     */
    public void startConditional(short type, Augmentations augmentations)
        throws XNIException;

    /**
     * Characters within an IGNORE conditional section.
     *
     * <p>
     *  IGNORE条件部分中的字符。
     * 
     * 
     * @param text The ignored text.
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void ignoredCharacters(XMLString text, Augmentations augmentations)
        throws XNIException;

    /**
     * The end of a conditional section.
     *
     * <p>
     *  条件段的结束。
     * 
     * 
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void endConditional(Augmentations augmentations) throws XNIException;

    /**
     * The end of the DTD.
     *
     * <p>
     *  DTD的结束。
     * 
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void endDTD(Augmentations augmentations) throws XNIException;

    // set the source of this handler
    public void setDTDSource(XMLDTDSource source);

    // return the source from which this handler derives its events
    public XMLDTDSource getDTDSource();

} // interface XMLDTDHandler
