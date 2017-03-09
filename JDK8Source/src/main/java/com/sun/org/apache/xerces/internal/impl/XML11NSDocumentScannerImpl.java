/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * The Apache Software License, Version 1.1
 *
 *
 * Copyright (c) 1999-2003 The Apache Software Foundation.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Xerces" and "Apache Software Foundation" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    nor may "Apache" appear in their name, without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation and was
 * originally based on software copyright (c) 2002, International
 * Business Machines, Inc., http://www.apache.org.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 * <p>
 *  Apache软件许可证,版本1.1
 * 
 *  版权所有(c)1999-2003 Apache软件基金会。版权所有。
 * 
 *  如果满足以下条件,则允许重新分发和使用源代码和二进制形式(带或不带修改)：
 * 
 *  1.源代码的再分发必须保留上述版权声明,此条件列表和以下免责声明。
 * 
 *  2.二进制形式的再分发必须在分发所提供的文档和/或其他材料中复制上述版权声明,此条件列表和以下免责声明。
 * 
 *  3.包含在重新分发中的最终用户文档(如果有)必须包括以下声明："本产品包括由Apache Software Foundation(http://www.apache.org/)开发的软件。
 * 或者,如果此类第三方确认通常出现,则此确认可能出现在软件本身中。
 * 
 *  4.未经事先书面许可,不得将"Xerces"和"Apache Software Foundation"名称用于支持或推广从本软件衍生的产品。如需书面许可,请联系apache@apache.org。
 * 
 *  未经Apache软件基金会事先书面许可,从本软件派生的产品可能不会被称为"Apache",也不可能出现在他们的名字中。
 * 
 * 本软件按"原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 * 在任何情况下,APACHE软件基金会或其捐赠者均不对任何直接,间接,偶发,特殊,惩罚性或后果性损害(包括但不限于替代商品或服务的采购,使用,数据丢失或利润或业务中断),无论是由于任何责任推理原因,无论是
 * 在合同,严格责任或侵权(包括疏忽或其他方式)中,以任何方式使用本软件,即使已被告知此类软件的可能性损伤。
 * 本软件按"原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 *  ================================================== ==================。
 * 
 *  该软件由许多个人代表Apache软件基金会做出的自愿捐款组成,最初基于软件版权(c)2002,国际商业机器公司,http://www.apache.org。
 * 有关Apache Software Foundation的更多信息,请参阅<http://www.apache.org/>。
 * 
 */

package com.sun.org.apache.xerces.internal.impl;

import java.io.IOException;

import com.sun.org.apache.xerces.internal.impl.dtd.XMLDTDValidatorFilter;
import com.sun.org.apache.xerces.internal.impl.msg.XMLMessageFormatter;
import com.sun.org.apache.xerces.internal.util.XMLAttributesImpl;
import com.sun.org.apache.xerces.internal.util.XMLSymbols;
import com.sun.org.apache.xerces.internal.xni.NamespaceContext;
import com.sun.org.apache.xerces.internal.xni.QName;
import com.sun.org.apache.xerces.internal.xni.XMLDocumentHandler;
import com.sun.org.apache.xerces.internal.xni.XNIException;
import com.sun.org.apache.xerces.internal.xni.parser.XMLComponentManager;
import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
import com.sun.org.apache.xerces.internal.xni.parser.XMLDocumentSource;
import javax.xml.stream.events.XMLEvent;


/**
 * The scanner acts as the source for the document
 * information which is communicated to the document handler.
 *
 * This class scans an XML document, checks if document has a DTD, and if
 * DTD is not found the scanner will remove the DTD Validator from the pipeline and perform
 * namespace binding.
 *
 * Note: This scanner should only be used when the namespace processing is on!
 *
 * <p>
 * This component requires the following features and properties from the
 * component manager that uses it:
 * <ul>
 *  <li>http://xml.org/sax/features/namespaces {true} -- if the value of this
 *      feature is set to false this scanner must not be used.</li>
 *  <li>http://xml.org/sax/features/validation</li>
 *  <li>http://apache.org/xml/features/nonvalidating/load-external-dtd</li>
 *  <li>http://apache.org/xml/features/scanner/notify-char-refs</li>
 *  <li>http://apache.org/xml/features/scanner/notify-builtin-refs</li>
 *  <li>http://apache.org/xml/properties/internal/symbol-table</li>
 *  <li>http://apache.org/xml/properties/internal/error-reporter</li>
 *  <li>http://apache.org/xml/properties/internal/entity-manager</li>
 *  <li>http://apache.org/xml/properties/internal/dtd-scanner</li>
 * </ul>
 *
 * @xerces.internal
 *
 * <p>
 *  扫描器用作传送到文档处理器的文档信息的源。
 * 
 *  此类扫描XML文档,检查文档是否具有DTD,如果未找到DTD,则扫描程序将从管道中删除DTD验证程序并执行命名空间绑定。
 * 
 *  注意：此扫描程序应该只在命名空间处理打开时使用！
 * 
 * <p>
 * 此组件需要使用它的组件管理器中的以下功能和属性：
 * <ul>
 *  <li> http://xml.org/sax/features/namespaces {true}  - 如果此地图项的值设置为false,则不得使用此扫描程序。
 * </li> <li> http：// xml .org / sax / features / validation </li> <li> http://apache.org/xml/features/n
 * onvalidating/load-external-dtd </li> <li> http://apache.org/xml / features / scanner / notify-char-re
 * fs </li> <li> http://apache.org/xml/features/scanner/notify-builtin-refs </li> <li> http://apache.org
 *  / xml / properties / internal / symbol-table </li> <li> http://apache.org/xml/properties/internal/er
 * ror-reporter </li> <li> http://apache.org/xml / properties / internal / entity-manager </li> <li> htt
 * p://apache.org/xml/properties/internal/dtd-scanner </li>。
 *  <li> http://xml.org/sax/features/namespaces {true}  - 如果此地图项的值设置为false,则不得使用此扫描程序。
 * </ul>
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Elena Litani, IBM
 * @author Michael Glavassevich, IBM
 * @author Sunitha Reddy, Sun Microsystems
 * @version $Id: XML11NSDocumentScannerImpl.java,v 1.6 2010-11-01 04:39:40 joehw Exp $
 */
public class XML11NSDocumentScannerImpl extends XML11DocumentScannerImpl {

    /**
     * If is true, the dtd validator is no longer in the pipeline
     * and the scanner should bind namespaces
     * <p>
     *  如果为true,dtd验证器不再在管道中,并且扫描程序应该绑定命名空间
     * 
     */
    protected boolean fBindNamespaces;

    /**
     * If validating parser, make sure we report an error in the
     *  scanner if DTD grammar is missing.
     * <p>
     *  如果验证解析器,确保我们报告错误在扫描仪如果DTD语法缺失。
     * 
     */
    protected boolean fPerformValidation;

    // private data
    //

    /** DTD validator */
    private XMLDTDValidatorFilter fDTDValidator;

    /**
     * Saw spaces after element name or between attributes.
     *
     * This is reserved for the case where scanning of a start element spans
     * several methods, as is the case when scanning the start of a root element
     * where a DTD external subset may be read after scanning the element name.
     * <p>
     *  在元素名称之后或属性之间锯开空格。
     * 
     *  这保留用于开始元素的扫描跨越若干方法的情况,如扫描根元素的开始的情况,其中可以在扫描元素名称之后读取DTD外部子集。
     * 
     */
    private boolean fSawSpace;


    /**
     * The scanner is responsible for removing DTD validator
     * from the pipeline if it is not needed.
     *
     * <p>
     *  如果不需要,扫描器负责从流水线中删除DTD验证器。
     * 
     * 
     * @param validator the DTD validator from the pipeline
     */
    public void setDTDValidator(XMLDTDValidatorFilter validator) {
        fDTDValidator = validator;
    }

    /**
     * Scans a start element. This method will handle the binding of
     * namespace information and notifying the handler of the start
     * of the element.
     * <p>
     * <pre>
     * [44] EmptyElemTag ::= '&lt;' Name (S Attribute)* S? '/>'
     * [40] STag ::= '&lt;' Name (S Attribute)* S? '>'
     * </pre>
     * <p>
     * <strong>Note:</strong> This method assumes that the leading
     * '&lt;' character has been consumed.
     * <p>
     * <strong>Note:</strong> This method uses the fElementQName and
     * fAttributes variables. The contents of these variables will be
     * destroyed. The caller should copy important information out of
     * these variables before calling this method.
     *
     * <p>
     *  扫描start元素。此方法将处理命名空间信息的绑定,并通知处理程序元素的开始。
     * <p>
     * <pre>
     * [44] EmptyElemTag :: ='&lt;'名称(S属性)* S? '/>'[40] STag :: ='&lt;'名称(S属性)* S? '>'
     * </pre>
     * <p>
     *  <strong>注意：</strong>此方法假定前导'&lt;'字符已被消耗。
     * <p>
     *  <strong>注意：</strong>此方法使用fElementQName和fAttributes变量。这些变量的内容将被销毁。在调用此方法之前,调用者应该从这些变量中复制重要信息。
     * 
     * 
     * @return True if element is empty. (i.e. It matches
     *          production [44].
     */
    protected boolean scanStartElement() throws IOException, XNIException {

        if (DEBUG_START_END_ELEMENT)
            System.out.println(">>> scanStartElementNS()");
                // Note: namespace processing is on by default
        fEntityScanner.scanQName(fElementQName);
        // REVISIT - [Q] Why do we need this local variable? -- mrglavas
        String rawname = fElementQName.rawname;
        if (fBindNamespaces) {
            fNamespaceContext.pushContext();
            if (fScannerState == SCANNER_STATE_ROOT_ELEMENT) {
                if (fPerformValidation) {
                    fErrorReporter.reportError(
                        XMLMessageFormatter.XML_DOMAIN,
                        "MSG_GRAMMAR_NOT_FOUND",
                        new Object[] { rawname },
                        XMLErrorReporter.SEVERITY_ERROR);

                    if (fDoctypeName == null
                        || !fDoctypeName.equals(rawname)) {
                        fErrorReporter.reportError(
                            XMLMessageFormatter.XML_DOMAIN,
                            "RootElementTypeMustMatchDoctypedecl",
                            new Object[] { fDoctypeName, rawname },
                            XMLErrorReporter.SEVERITY_ERROR);
                    }
                }
            }
        }

        // push element stack
        fCurrentElement = fElementStack.pushElement(fElementQName);

        // attributes
        boolean empty = false;
        fAttributes.removeAllAttributes();
        do {
            // spaces
            boolean sawSpace = fEntityScanner.skipSpaces();

            // end tag?
            int c = fEntityScanner.peekChar();
            if (c == '>') {
                fEntityScanner.scanChar();
                break;
            } else if (c == '/') {
                fEntityScanner.scanChar();
                if (!fEntityScanner.skipChar('>')) {
                    reportFatalError(
                        "ElementUnterminated",
                        new Object[] { rawname });
                }
                empty = true;
                break;
            } else if (!isValidNameStartChar(c) || !sawSpace) {
                // Second chance. Check if this character is a high
                // surrogate of a valid name start character.
                if (!isValidNameStartHighSurrogate(c) || !sawSpace) {
                    reportFatalError(
                        "ElementUnterminated",
                        new Object[] { rawname });
                }
            }

            // attributes
            scanAttribute(fAttributes);
            if (fSecurityManager != null && (!fSecurityManager.isNoLimit(fElementAttributeLimit)) &&
                    fAttributes.getLength() > fElementAttributeLimit){
                fErrorReporter.reportError(XMLMessageFormatter.XML_DOMAIN,
                                             "ElementAttributeLimit",
                                             new Object[]{rawname, new Integer(fElementAttributeLimit) },
                                             XMLErrorReporter.SEVERITY_FATAL_ERROR );
            }

        } while (true);

        if (fBindNamespaces) {
            // REVISIT: is it required? forbit xmlns prefix for element
            if (fElementQName.prefix == XMLSymbols.PREFIX_XMLNS) {
                fErrorReporter.reportError(
                    XMLMessageFormatter.XMLNS_DOMAIN,
                    "ElementXMLNSPrefix",
                    new Object[] { fElementQName.rawname },
                    XMLErrorReporter.SEVERITY_FATAL_ERROR);
            }

            // bind the element
            String prefix =
                fElementQName.prefix != null
                    ? fElementQName.prefix
                    : XMLSymbols.EMPTY_STRING;
            // assign uri to the element
            fElementQName.uri = fNamespaceContext.getURI(prefix);
            // make sure that object in the element stack is updated as well
            fCurrentElement.uri = fElementQName.uri;

            if (fElementQName.prefix == null && fElementQName.uri != null) {
                fElementQName.prefix = XMLSymbols.EMPTY_STRING;
                // making sure that the object in the element stack is updated too.
                fCurrentElement.prefix = XMLSymbols.EMPTY_STRING;
            }
            if (fElementQName.prefix != null && fElementQName.uri == null) {
                fErrorReporter.reportError(
                    XMLMessageFormatter.XMLNS_DOMAIN,
                    "ElementPrefixUnbound",
                    new Object[] {
                        fElementQName.prefix,
                        fElementQName.rawname },
                    XMLErrorReporter.SEVERITY_FATAL_ERROR);
            }

            // bind attributes (xmlns are already bound bellow)
            int length = fAttributes.getLength();
            for (int i = 0; i < length; i++) {
                fAttributes.getName(i, fAttributeQName);

                String aprefix =
                    fAttributeQName.prefix != null
                        ? fAttributeQName.prefix
                        : XMLSymbols.EMPTY_STRING;
                String uri = fNamespaceContext.getURI(aprefix);
                // REVISIT: try removing the first "if" and see if it is faster.
                //
                if (fAttributeQName.uri != null
                    && fAttributeQName.uri == uri) {
                    continue;
                }
                if (aprefix != XMLSymbols.EMPTY_STRING) {
                    fAttributeQName.uri = uri;
                    if (uri == null) {
                        fErrorReporter.reportError(
                            XMLMessageFormatter.XMLNS_DOMAIN,
                            "AttributePrefixUnbound",
                            new Object[] {
                                fElementQName.rawname,
                                fAttributeQName.rawname,
                                aprefix },
                            XMLErrorReporter.SEVERITY_FATAL_ERROR);
                    }
                    fAttributes.setURI(i, uri);
                }
            }

            if (length > 1) {
                QName name = fAttributes.checkDuplicatesNS();
                if (name != null) {
                    if (name.uri != null) {
                        fErrorReporter.reportError(
                            XMLMessageFormatter.XMLNS_DOMAIN,
                            "AttributeNSNotUnique",
                            new Object[] {
                                fElementQName.rawname,
                                name.localpart,
                                name.uri },
                            XMLErrorReporter.SEVERITY_FATAL_ERROR);
                    } else {
                        fErrorReporter.reportError(
                            XMLMessageFormatter.XMLNS_DOMAIN,
                            "AttributeNotUnique",
                            new Object[] {
                                fElementQName.rawname,
                                name.rawname },
                            XMLErrorReporter.SEVERITY_FATAL_ERROR);
                    }
                }
            }
        }

        // call handler

            if (empty) {

                //decrease the markup depth..
                fMarkupDepth--;

                // check that this element was opened in the same entity
                if (fMarkupDepth < fEntityStack[fEntityDepth - 1]) {
                    reportFatalError(
                        "ElementEntityMismatch",
                        new Object[] { fCurrentElement.rawname });
                }

                fDocumentHandler.emptyElement(fElementQName, fAttributes, null);

                /*if (fBindNamespaces) {
                    fNamespaceContext.popContext();
                /* <p>
                /*  fNamespaceContext.popContext();
                /* 
                /* 
                }*/
                fScanEndElement = true;

                //pop the element off the stack..
                fElementStack.popElement();
            } else {

                if(dtdGrammarUtil != null)
                    dtdGrammarUtil.startElement(fElementQName, fAttributes);

                if (fDocumentHandler != null)
                fDocumentHandler.startElement(fElementQName, fAttributes, null);
            }

        if (DEBUG_START_END_ELEMENT)
            System.out.println("<<< scanStartElement(): " + empty);
        return empty;

    } // scanStartElement():boolean

    /**
     * Scans the name of an element in a start or empty tag.
     *
     * <p>
     *  扫描开始或空标记中元素的名称。
     * 
     * 
     * @see #scanStartElement()
     */
    protected void scanStartElementName ()
        throws IOException, XNIException {
        // Note: namespace processing is on by default
        fEntityScanner.scanQName(fElementQName);
        // Must skip spaces here because the DTD scanner
        // would consume them at the end of the external subset.
        fSawSpace = fEntityScanner.skipSpaces();
    } // scanStartElementName()

    /**
     * Scans the remainder of a start or empty tag after the element name.
     *
     * <p>
     *  扫描元素名称后面的开始或空标记的其余部分。
     * 
     * 
     * @see #scanStartElement
     * @return True if element is empty.
     */
    protected boolean scanStartElementAfterName()
        throws IOException, XNIException {

        // REVISIT - [Q] Why do we need this local variable? -- mrglavas
        String rawname = fElementQName.rawname;
        if (fBindNamespaces) {
            fNamespaceContext.pushContext();
            if (fScannerState == SCANNER_STATE_ROOT_ELEMENT) {
                if (fPerformValidation) {
                    fErrorReporter.reportError(
                        XMLMessageFormatter.XML_DOMAIN,
                        "MSG_GRAMMAR_NOT_FOUND",
                        new Object[] { rawname },
                        XMLErrorReporter.SEVERITY_ERROR);

                    if (fDoctypeName == null
                        || !fDoctypeName.equals(rawname)) {
                        fErrorReporter.reportError(
                            XMLMessageFormatter.XML_DOMAIN,
                            "RootElementTypeMustMatchDoctypedecl",
                            new Object[] { fDoctypeName, rawname },
                            XMLErrorReporter.SEVERITY_ERROR);
                    }
                }
            }
        }

        // push element stack
        fCurrentElement = fElementStack.pushElement(fElementQName);

        // attributes
        boolean empty = false;
        fAttributes.removeAllAttributes();
        do {

            // end tag?
            int c = fEntityScanner.peekChar();
            if (c == '>') {
                fEntityScanner.scanChar();
                break;
            } else if (c == '/') {
                fEntityScanner.scanChar();
                if (!fEntityScanner.skipChar('>')) {
                    reportFatalError(
                        "ElementUnterminated",
                        new Object[] { rawname });
                }
                empty = true;
                break;
            } else if (!isValidNameStartChar(c) || !fSawSpace) {
                // Second chance. Check if this character is a high
                // surrogate of a valid name start character.
                if (!isValidNameStartHighSurrogate(c) || !fSawSpace) {
                    reportFatalError(
                        "ElementUnterminated",
                        new Object[] { rawname });
                }
            }

            // attributes
            scanAttribute(fAttributes);

            // spaces
            fSawSpace = fEntityScanner.skipSpaces();

        } while (true);

        if (fBindNamespaces) {
            // REVISIT: is it required? forbit xmlns prefix for element
            if (fElementQName.prefix == XMLSymbols.PREFIX_XMLNS) {
                fErrorReporter.reportError(
                    XMLMessageFormatter.XMLNS_DOMAIN,
                    "ElementXMLNSPrefix",
                    new Object[] { fElementQName.rawname },
                    XMLErrorReporter.SEVERITY_FATAL_ERROR);
            }

            // bind the element
            String prefix =
                fElementQName.prefix != null
                    ? fElementQName.prefix
                    : XMLSymbols.EMPTY_STRING;
            // assign uri to the element
            fElementQName.uri = fNamespaceContext.getURI(prefix);
            // make sure that object in the element stack is updated as well
            fCurrentElement.uri = fElementQName.uri;

            if (fElementQName.prefix == null && fElementQName.uri != null) {
                fElementQName.prefix = XMLSymbols.EMPTY_STRING;
                // making sure that the object in the element stack is updated too.
                fCurrentElement.prefix = XMLSymbols.EMPTY_STRING;
            }
            if (fElementQName.prefix != null && fElementQName.uri == null) {
                fErrorReporter.reportError(
                    XMLMessageFormatter.XMLNS_DOMAIN,
                    "ElementPrefixUnbound",
                    new Object[] {
                        fElementQName.prefix,
                        fElementQName.rawname },
                    XMLErrorReporter.SEVERITY_FATAL_ERROR);
            }

            // bind attributes (xmlns are already bound bellow)
            int length = fAttributes.getLength();
            for (int i = 0; i < length; i++) {
                fAttributes.getName(i, fAttributeQName);

                String aprefix =
                    fAttributeQName.prefix != null
                        ? fAttributeQName.prefix
                        : XMLSymbols.EMPTY_STRING;
                String uri = fNamespaceContext.getURI(aprefix);
                // REVISIT: try removing the first "if" and see if it is faster.
                //
                if (fAttributeQName.uri != null
                    && fAttributeQName.uri == uri) {
                    continue;
                }
                if (aprefix != XMLSymbols.EMPTY_STRING) {
                    fAttributeQName.uri = uri;
                    if (uri == null) {
                        fErrorReporter.reportError(
                            XMLMessageFormatter.XMLNS_DOMAIN,
                            "AttributePrefixUnbound",
                            new Object[] {
                                fElementQName.rawname,
                                fAttributeQName.rawname,
                                aprefix },
                            XMLErrorReporter.SEVERITY_FATAL_ERROR);
                    }
                    fAttributes.setURI(i, uri);
                }
            }

            if (length > 1) {
                QName name = fAttributes.checkDuplicatesNS();
                if (name != null) {
                    if (name.uri != null) {
                        fErrorReporter.reportError(
                            XMLMessageFormatter.XMLNS_DOMAIN,
                            "AttributeNSNotUnique",
                            new Object[] {
                                fElementQName.rawname,
                                name.localpart,
                                name.uri },
                            XMLErrorReporter.SEVERITY_FATAL_ERROR);
                    } else {
                        fErrorReporter.reportError(
                            XMLMessageFormatter.XMLNS_DOMAIN,
                            "AttributeNotUnique",
                            new Object[] {
                                fElementQName.rawname,
                                name.rawname },
                            XMLErrorReporter.SEVERITY_FATAL_ERROR);
                    }
                }
            }
        }

        // call handler
        if (fDocumentHandler != null) {
            if (empty) {

                //decrease the markup depth..
                fMarkupDepth--;

                // check that this element was opened in the same entity
                if (fMarkupDepth < fEntityStack[fEntityDepth - 1]) {
                    reportFatalError(
                        "ElementEntityMismatch",
                        new Object[] { fCurrentElement.rawname });
                }

                fDocumentHandler.emptyElement(fElementQName, fAttributes, null);

                if (fBindNamespaces) {
                    fNamespaceContext.popContext();
                }
                //pop the element off the stack..
                fElementStack.popElement();
            } else {
                fDocumentHandler.startElement(fElementQName, fAttributes, null);
            }
        }

        if (DEBUG_START_END_ELEMENT)
            System.out.println("<<< scanStartElementAfterName(): " + empty);
        return empty;

    } // scanStartElementAfterName()

    /**
     * Scans an attribute.
     * <p>
     * <pre>
     * [41] Attribute ::= Name Eq AttValue
     * </pre>
     * <p>
     * <strong>Note:</strong> This method assumes that the next
     * character on the stream is the first character of the attribute
     * name.
     * <p>
     * <strong>Note:</strong> This method uses the fAttributeQName and
     * fQName variables. The contents of these variables will be
     * destroyed.
     *
     * <p>
     *  扫描属性。
     * <p>
     * <pre>
     *  Attribute :: =命名Eq AttValue
     * </pre>
     * <p>
     *  <strong>注意</strong>：此方法假定流上的下一个字符是属性名称的第一个字符。
     * <p>
     *  <strong>注意：</strong>此方法使用fAttributeQName和fQName变量。这些变量的内容将被销毁。
     * 
     * 
     * @param attributes The attributes list for the scanned attribute.
     */
    protected void scanAttribute(XMLAttributesImpl attributes)
        throws IOException, XNIException {
        if (DEBUG_START_END_ELEMENT)
            System.out.println(">>> scanAttribute()");

        // name
        fEntityScanner.scanQName(fAttributeQName);

        // equals
        fEntityScanner.skipSpaces();
        if (!fEntityScanner.skipChar('=')) {
            reportFatalError(
                "EqRequiredInAttribute",
                new Object[] {
                    fCurrentElement.rawname,
                    fAttributeQName.rawname });
        }
        fEntityScanner.skipSpaces();

        // content
        int attrIndex;

        if (fBindNamespaces) {
            attrIndex = attributes.getLength();
            attributes.addAttributeNS(
                fAttributeQName,
                XMLSymbols.fCDATASymbol,
                null);
        } else {
            int oldLen = attributes.getLength();
            attrIndex =
                attributes.addAttribute(
                    fAttributeQName,
                    XMLSymbols.fCDATASymbol,
                    null);

            // WFC: Unique Att Spec
            if (oldLen == attributes.getLength()) {
                reportFatalError(
                    "AttributeNotUnique",
                    new Object[] {
                        fCurrentElement.rawname,
                        fAttributeQName.rawname });
            }
        }

        //REVISIT: one more case needs to be included: external PE and standalone is no
        boolean isVC = fHasExternalDTD && !fStandalone;

        // REVISIT: it seems that this function should not take attributes, and length
        scanAttributeValue(
            this.fTempString,
            fTempString2,
            fAttributeQName.rawname,
            isVC,
            fCurrentElement.rawname);
        String value = fTempString.toString();
        attributes.setValue(attrIndex, value);
        attributes.setNonNormalizedValue(attrIndex, fTempString2.toString());
        attributes.setSpecified(attrIndex, true);

        // record namespace declarations if any.
        if (fBindNamespaces) {

            String localpart = fAttributeQName.localpart;
            String prefix =
                fAttributeQName.prefix != null
                    ? fAttributeQName.prefix
                    : XMLSymbols.EMPTY_STRING;
            // when it's of form xmlns="..." or xmlns:prefix="...",
            // it's a namespace declaration. but prefix:xmlns="..." isn't.
            if (prefix == XMLSymbols.PREFIX_XMLNS
                || prefix == XMLSymbols.EMPTY_STRING
                && localpart == XMLSymbols.PREFIX_XMLNS) {

                // get the internalized value of this attribute
                String uri = fSymbolTable.addSymbol(value);

                // 1. "xmlns" can't be bound to any namespace
                if (prefix == XMLSymbols.PREFIX_XMLNS
                    && localpart == XMLSymbols.PREFIX_XMLNS) {
                    fErrorReporter.reportError(
                        XMLMessageFormatter.XMLNS_DOMAIN,
                        "CantBindXMLNS",
                        new Object[] { fAttributeQName },
                        XMLErrorReporter.SEVERITY_FATAL_ERROR);
                }

                // 2. the namespace for "xmlns" can't be bound to any prefix
                if (uri == NamespaceContext.XMLNS_URI) {
                    fErrorReporter.reportError(
                        XMLMessageFormatter.XMLNS_DOMAIN,
                        "CantBindXMLNS",
                        new Object[] { fAttributeQName },
                        XMLErrorReporter.SEVERITY_FATAL_ERROR);
                }

                // 3. "xml" can't be bound to any other namespace than it's own
                if (localpart == XMLSymbols.PREFIX_XML) {
                    if (uri != NamespaceContext.XML_URI) {
                        fErrorReporter.reportError(
                            XMLMessageFormatter.XMLNS_DOMAIN,
                            "CantBindXML",
                            new Object[] { fAttributeQName },
                            XMLErrorReporter.SEVERITY_FATAL_ERROR);
                    }
                }
                // 4. the namespace for "xml" can't be bound to any other prefix
                else {
                    if (uri == NamespaceContext.XML_URI) {
                        fErrorReporter.reportError(
                            XMLMessageFormatter.XMLNS_DOMAIN,
                            "CantBindXML",
                            new Object[] { fAttributeQName },
                            XMLErrorReporter.SEVERITY_FATAL_ERROR);
                    }
                }

                prefix =
                    localpart != XMLSymbols.PREFIX_XMLNS
                        ? localpart
                        : XMLSymbols.EMPTY_STRING;

                // Declare prefix in context. Removing the association between a prefix and a
                // namespace name is permitted in XML 1.1, so if the uri value is the empty string,
                // the prefix is being unbound. -- mrglavas
                fNamespaceContext.declarePrefix(
                    prefix,
                    uri.length() != 0 ? uri : null);
                // bind namespace attribute to a namespace
                attributes.setURI(
                    attrIndex,
                    fNamespaceContext.getURI(XMLSymbols.PREFIX_XMLNS));

            } else {
                // attempt to bind attribute
                if (fAttributeQName.prefix != null) {
                    attributes.setURI(
                        attrIndex,
                        fNamespaceContext.getURI(fAttributeQName.prefix));
                }
            }
        }

        if (DEBUG_START_END_ELEMENT)
            System.out.println("<<< scanAttribute()");
    } // scanAttribute(XMLAttributes)

    /**
     * Scans an end element.
     * <p>
     * <pre>
     * [42] ETag ::= '&lt;/' Name S? '>'
     * </pre>
     * <p>
     * <strong>Note:</strong> This method uses the fElementQName variable.
     * The contents of this variable will be destroyed. The caller should
     * copy the needed information out of this variable before calling
     * this method.
     *
     * <p>
     *  扫描结束元素。
     * <p>
     * <pre>
     *  [42] ETag :: ='&lt; /'名称S? '>'
     * </pre>
     * <p>
     *  <strong>注意：</strong>此方法使用fElementQName变量。此变量的内容将被销毁。在调用此方法之前,调用者应该从这个变量中复制所需的信息。
     * 
     * 
     * @return The element depth.
     */
    protected int scanEndElement() throws IOException, XNIException {
        if (DEBUG_START_END_ELEMENT)
            System.out.println(">>> scanEndElement()");

        // pop context
        QName endElementName = fElementStack.popElement();

        // Take advantage of the fact that next string _should_ be "fElementQName.rawName",
        //In scanners most of the time is consumed on checks done for XML characters, we can
        // optimize on it and avoid the checks done for endElement,
        //we will also avoid symbol table lookup - neeraj.bajaj@sun.com

        // this should work both for namespace processing true or false...

        //REVISIT: if the string is not the same as expected.. we need to do better error handling..
        //We can skip this for now... In any case if the string doesn't match -- document is not well formed.

        if (!fEntityScanner.skipString(endElementName.rawname)) {
             reportFatalError(
                "ETagRequired",
                new Object[] { endElementName.rawname });
        }

        // end
        fEntityScanner.skipSpaces();
        if (!fEntityScanner.skipChar('>')) {
            reportFatalError(
                "ETagUnterminated",
                new Object[] { endElementName.rawname });
        }
        fMarkupDepth--;

        //we have increased the depth for two markup "<" characters
        fMarkupDepth--;

        // check that this element was opened in the same entity
        if (fMarkupDepth < fEntityStack[fEntityDepth - 1]) {
            reportFatalError(
                "ElementEntityMismatch",
                new Object[] { endElementName.rawname });
        }

        // call handler
        if (fDocumentHandler != null) {
            fDocumentHandler.endElement(endElementName, null);

            /*if (fBindNamespaces) {
                fNamespaceContext.popContext();
            /* <p>
            /*  fNamespaceContext.popContext();
            /* 
            /* 
            }*/

        }

        if(dtdGrammarUtil != null)
            dtdGrammarUtil.endElement(endElementName);

        return fMarkupDepth;

    } // scanEndElement():int

    public void reset(XMLComponentManager componentManager)
        throws XMLConfigurationException {

        super.reset(componentManager);
        fPerformValidation = false;
        fBindNamespaces = false;
    }

    /** Creates a content Driver. */
    protected Driver createContentDriver() {
        return new NS11ContentDriver();
    } // createContentDriver():Driver


    /** return the next state on the input
     *
     * <p>
     * 
     * @return int
     */

    public int next() throws IOException, XNIException {
        //since namespace context should still be valid when the parser is at the end element state therefore
        //we pop the context only when next() has been called after the end element state was encountered. - nb.

        if((fScannerLastState == XMLEvent.END_ELEMENT) && fBindNamespaces){
            fScannerLastState = -1;
            fNamespaceContext.popContext();
        }

        return fScannerLastState = super.next();
    }


    /**
     * Driver to handle content scanning.
     * <p>
     *  驱动程序处理内容扫描。
     * 
     */
    protected final class NS11ContentDriver extends ContentDriver {
        /**
         * Scan for root element hook. This method is a hook for
         * subclasses to add code that handles scanning for the root
         * element. This method will also attempt to remove DTD validator
         * from the pipeline, if there is no DTD grammar. If DTD validator
         * is no longer in the pipeline bind namespaces in the scanner.
         *
         *
         * <p>
         * 扫描根元素钩子。此方法是一个钩子,用于子类添加代码来处理根元素的扫描。如果没有DTD语法,此方法还将尝试从管道中删除DTD验证器。如果DTD验证器不再在管道中绑定命名空间在扫描器中。
         * 
         * 
         * @return True if the caller should stop and return true which
         *          allows the scanner to switch to a new scanning
         *          Driver. A return value of false indicates that
         *          the content Driver should continue as normal.
         */
        protected boolean scanRootElementHook()
            throws IOException, XNIException {

            if (fExternalSubsetResolver != null && !fSeenDoctypeDecl
                && !fDisallowDoctype && (fValidation || fLoadExternalDTD)) {
                scanStartElementName();
                resolveExternalSubsetAndRead();
                reconfigurePipeline();
                if (scanStartElementAfterName()) {
                    setScannerState(SCANNER_STATE_TRAILING_MISC);
                    setDriver(fTrailingMiscDriver);
                    return true;
                }
            }
            else {
                reconfigurePipeline();
                if (scanStartElement()) {
                    setScannerState(SCANNER_STATE_TRAILING_MISC);
                    setDriver(fTrailingMiscDriver);
                    return true;
                }
            }
            return false;

        } // scanRootElementHook():boolean

        /**
         * Re-configures pipeline by removing the DTD validator
         * if no DTD grammar exists. If no validator exists in the
         * pipeline or there is no DTD grammar, namespace binding
         * is performed by the scanner in the enclosing class.
         * <p>
         *  如果没有DTD语法存在,请通过删除DTD验证器来重新配置管道。如果管道中没有验证器,或没有DTD语法,则命名空间绑定由扫描器在封闭类中执行。
         */
        private void reconfigurePipeline() {
            if (fDTDValidator == null) {
                fBindNamespaces = true;
            }
            else if (!fDTDValidator.hasGrammar()) {
                fBindNamespaces = true;
                fPerformValidation = fDTDValidator.validate();
                // re-configure pipeline
                XMLDocumentSource source = fDTDValidator.getDocumentSource();
                XMLDocumentHandler handler = fDTDValidator.getDocumentHandler();
                source.setDocumentHandler(handler);
                if (handler != null)
                    handler.setDocumentSource(source);
                fDTDValidator.setDocumentSource(null);
                fDTDValidator.setDocumentHandler(null);
            }
        } // reconfigurePipeline()
    }
}
