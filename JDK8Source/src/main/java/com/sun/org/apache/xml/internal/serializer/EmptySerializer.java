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
 *  版权所有2003-2004 Apache软件基金会
 * 
 *  根据Apache许可证第20版("许可证")授权;您不得使用此文件,除非符合许可证您可以在获取许可证的副本
 * 
 *  http：// wwwapacheorg / licenses / LICENSE-20
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件将按"原样"基础分发,无任何明示或暗示的保证或条件。请参阅许可证管理权限和限制许可证
 * 
 */
/*
 * $Id: EmptySerializer.java,v 1.2.4.1 2005/09/15 08:15:16 suresh_emailid Exp $
 * <p>
 *  $ Id：EmptySerializerjava,v 1241 2005/09/15 08:15:16 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.serializer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

import javax.xml.transform.SourceLocator;
import javax.xml.transform.Transformer;

import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * This class is an adapter class. Its only purpose is to be extended and
 * for that extended class to over-ride all methods that are to be used.
 *
 * This class is not a public API, it is only public because it is used
 * across package boundaries.
 *
 * @xsl.usage internal
 * <p>
 * 这个类是一个适配器类它的唯一目的是扩展和扩展类覆盖所有使用的方法
 * 
 *  这个类不是一个公共的API,它只是公共的,因为它是跨包裹边界使用
 * 
 *  @xslusage内部
 * 
 */
public class EmptySerializer implements SerializationHandler
{
    protected static final String ERR = "EmptySerializer method not over-ridden";
    /**
    /* <p>
    /* 
     * @see SerializationHandler#asContentHandler()
     */

    protected void couldThrowIOException() throws IOException
    {
        return; // don't do anything.
    }

    protected void couldThrowSAXException() throws SAXException
    {
        return; // don't do anything.
    }

    protected void couldThrowSAXException(char[] chars, int off, int len) throws SAXException
    {
        return; // don't do anything.
    }

    protected void couldThrowSAXException(String elemQName) throws SAXException
    {
        return; // don't do anything.
    }

    protected void couldThrowException() throws Exception
    {
        return; // don't do anything.
    }

    void aMethodIsCalled()
    {

        // throw new RuntimeException(err);
        return;
    }


    /**
    /* <p>
    /* 
     * @see SerializationHandler#asContentHandler()
     */
    public ContentHandler asContentHandler() throws IOException
    {
        couldThrowIOException();
        return null;
    }
    /**
    /* <p>
    /* 
     * @see SerializationHandler#setContentHandler(org.xml.sax.ContentHandler)
     */
    public void setContentHandler(ContentHandler ch)
    {
        aMethodIsCalled();
    }
    /**
    /* <p>
    /* 
     * @see SerializationHandler#close()
     */
    public void close()
    {
        aMethodIsCalled();
    }
    /**
    /* <p>
    /* 
     * @see SerializationHandler#getOutputFormat()
     */
    public Properties getOutputFormat()
    {
        aMethodIsCalled();
        return null;
    }
    /**
    /* <p>
    /* 
     * @see SerializationHandler#getOutputStream()
     */
    public OutputStream getOutputStream()
    {
        aMethodIsCalled();
        return null;
    }
    /**
    /* <p>
    /* 
     * @see SerializationHandler#getWriter()
     */
    public Writer getWriter()
    {
        aMethodIsCalled();
        return null;
    }
    /**
    /* <p>
    /* 
     * @see SerializationHandler#reset()
     */
    public boolean reset()
    {
        aMethodIsCalled();
        return false;
    }
    /**
    /* <p>
    /* 
     * @see SerializationHandler#serialize(org.w3c.dom.Node)
     */
    public void serialize(Node node) throws IOException
    {
        couldThrowIOException();
    }
    /**
    /* <p>
    /* 
     * @see SerializationHandler#setCdataSectionElements(java.util.Vector)
     */
    public void setCdataSectionElements(Vector URI_and_localNames)
    {
        aMethodIsCalled();
    }
    /**
    /* <p>
    /* 
     * @see SerializationHandler#setEscaping(boolean)
     */
    public boolean setEscaping(boolean escape) throws SAXException
    {
        couldThrowSAXException();
        return false;
    }
    /**
    /* <p>
    /* 
     * @see SerializationHandler#setIndent(boolean)
     */
    public void setIndent(boolean indent)
    {
        aMethodIsCalled();
    }
    /**
    /* <p>
    /* 
     * @see SerializationHandler#setIndentAmount(int)
     */
    public void setIndentAmount(int spaces)
    {
        aMethodIsCalled();
    }
    /**
    /* <p>
    /* 
     * @see SerializationHandler#setIsStandalone(boolean)
     */
    public void setIsStandalone(boolean isStandalone)
    {
        aMethodIsCalled();
    }
    /**
    /* <p>
    /* 
     * @see SerializationHandler#setOutputFormat(java.util.Properties)
     */
    public void setOutputFormat(Properties format)
    {
        aMethodIsCalled();
    }
    /**
    /* <p>
    /* 
     * @see SerializationHandler#setOutputStream(java.io.OutputStream)
     */
    public void setOutputStream(OutputStream output)
    {
        aMethodIsCalled();
    }
    /**
    /* <p>
    /* 
     * @see SerializationHandler#setVersion(java.lang.String)
     */
    public void setVersion(String version)
    {
        aMethodIsCalled();
    }
    /**
    /* <p>
    /* 
     * @see SerializationHandler#setWriter(java.io.Writer)
     */
    public void setWriter(Writer writer)
    {
        aMethodIsCalled();
    }
    /**
    /* <p>
    /* 
     * @see SerializationHandler#setTransformer(javax.xml.transform.Transformer)
     */
    public void setTransformer(Transformer transformer)
    {
        aMethodIsCalled();
    }
    /**
    /* <p>
    /* 
     * @see SerializationHandler#getTransformer()
     */
    public Transformer getTransformer()
    {
        aMethodIsCalled();
        return null;
    }
    /**
    /* <p>
    /* 
     * @see SerializationHandler#flushPending()
     */
    public void flushPending() throws SAXException
    {
        couldThrowSAXException();
    }
    /**
    /* <p>
    /* 
     * @see ExtendedContentHandler#addAttribute(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public void addAttribute(
        String uri,
        String localName,
        String rawName,
        String type,
        String value,
        boolean XSLAttribute)
        throws SAXException
    {
        couldThrowSAXException();
    }
    /**
    /* <p>
    /* 
     * @see ExtendedContentHandler#addAttributes(org.xml.sax.Attributes)
     */
    public void addAttributes(Attributes atts) throws SAXException
    {
        couldThrowSAXException();
    }
    /**
    /* <p>
    /* 
     * @see ExtendedContentHandler#addAttribute(java.lang.String, java.lang.String)
     */
    public void addAttribute(String name, String value)
    {
        aMethodIsCalled();
    }

    /**
    /* <p>
    /* 
     * @see ExtendedContentHandler#characters(java.lang.String)
     */
    public void characters(String chars) throws SAXException
    {
        couldThrowSAXException();
    }
    /**
    /* <p>
    /* 
     * @see ExtendedContentHandler#endElement(java.lang.String)
     */
    public void endElement(String elemName) throws SAXException
    {
        couldThrowSAXException();
    }
    /**
    /* <p>
    /* 
     * @see ExtendedContentHandler#startDocument()
     */
    public void startDocument() throws SAXException
    {
        couldThrowSAXException();
    }
    /**
    /* <p>
    /* 
     * @see ExtendedContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String)
     */
    public void startElement(String uri, String localName, String qName)
        throws SAXException
    {
        couldThrowSAXException(qName);
    }
    /**
    /* <p>
    /* 
     * @see ExtendedContentHandler#startElement(java.lang.String)
     */
    public void startElement(String qName) throws SAXException
    {
        couldThrowSAXException(qName);
    }
    /**
    /* <p>
    /* 
     * @see ExtendedContentHandler#namespaceAfterStartElement(java.lang.String, java.lang.String)
     */
    public void namespaceAfterStartElement(String uri, String prefix)
        throws SAXException
    {
        couldThrowSAXException();
    }
    /**
    /* <p>
    /* 
     * @see ExtendedContentHandler#startPrefixMapping(java.lang.String, java.lang.String, boolean)
     */
    public boolean startPrefixMapping(
        String prefix,
        String uri,
        boolean shouldFlush)
        throws SAXException
    {
        couldThrowSAXException();
        return false;
    }
    /**
    /* <p>
    /* 
     * @see ExtendedContentHandler#entityReference(java.lang.String)
     */
    public void entityReference(String entityName) throws SAXException
    {
        couldThrowSAXException();
    }
    /**
    /* <p>
    /* 
     * @see ExtendedContentHandler#getNamespaceMappings()
     */
    public NamespaceMappings getNamespaceMappings()
    {
        aMethodIsCalled();
        return null;
    }
    /**
    /* <p>
    /* 
     * @see ExtendedContentHandler#getPrefix(java.lang.String)
     */
    public String getPrefix(String uri)
    {
        aMethodIsCalled();
        return null;
    }
    /**
    /* <p>
    /* 
     * @see ExtendedContentHandler#getNamespaceURI(java.lang.String, boolean)
     */
    public String getNamespaceURI(String name, boolean isElement)
    {
        aMethodIsCalled();
        return null;
    }
    /**
    /* <p>
    /* 
     * @see ExtendedContentHandler#getNamespaceURIFromPrefix(java.lang.String)
     */
    public String getNamespaceURIFromPrefix(String prefix)
    {
        aMethodIsCalled();
        return null;
    }
    /**
    /* <p>
    /* 
     * @see org.xml.sax.ContentHandler#setDocumentLocator(org.xml.sax.Locator)
     */
    public void setDocumentLocator(Locator arg0)
    {
        aMethodIsCalled();
    }
    /**
    /* <p>
    /* 
     * @see org.xml.sax.ContentHandler#endDocument()
     */
    public void endDocument() throws SAXException
    {
        couldThrowSAXException();
    }
    /**
    /* <p>
    /* 
     * @see org.xml.sax.ContentHandler#startPrefixMapping(java.lang.String, java.lang.String)
     */
    public void startPrefixMapping(String arg0, String arg1)
        throws SAXException
    {
        couldThrowSAXException();
    }
    /**
    /* <p>
    /* 
     * @see org.xml.sax.ContentHandler#endPrefixMapping(java.lang.String)
     */
    public void endPrefixMapping(String arg0) throws SAXException
    {
        couldThrowSAXException();
    }
    /**
    /* <p>
    /* 
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement(
        String arg0,
        String arg1,
        String arg2,
        Attributes arg3)
        throws SAXException
    {
        couldThrowSAXException();
    }
    /**
    /* <p>
    /* 
     * @see org.xml.sax.ContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    public void endElement(String arg0, String arg1, String arg2)
        throws SAXException
    {
        couldThrowSAXException();
    }
    /**
    /* <p>
    /* 
     * @see org.xml.sax.ContentHandler#characters(char[], int, int)
     */
    public void characters(char[] arg0, int arg1, int arg2) throws SAXException
    {
        couldThrowSAXException(arg0, arg1, arg2);
    }
    /**
    /* <p>
    /* 
     * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
     */
    public void ignorableWhitespace(char[] arg0, int arg1, int arg2)
        throws SAXException
    {
        couldThrowSAXException();
    }
    /**
    /* <p>
    /* 
     * @see org.xml.sax.ContentHandler#processingInstruction(java.lang.String, java.lang.String)
     */
    public void processingInstruction(String arg0, String arg1)
        throws SAXException
    {
        couldThrowSAXException();
    }
    /**
    /* <p>
    /* 
     * @see org.xml.sax.ContentHandler#skippedEntity(java.lang.String)
     */
    public void skippedEntity(String arg0) throws SAXException
    {
        couldThrowSAXException();
    }
    /**
    /* <p>
    /* 
     * @see ExtendedLexicalHandler#comment(java.lang.String)
     */
    public void comment(String comment) throws SAXException
    {
        couldThrowSAXException();
    }
    /**
    /* <p>
    /* 
     * @see org.xml.sax.ext.LexicalHandler#startDTD(java.lang.String, java.lang.String, java.lang.String)
     */
    public void startDTD(String arg0, String arg1, String arg2)
        throws SAXException
    {
        couldThrowSAXException();
    }
    /**
    /* <p>
    /* 
     * @see org.xml.sax.ext.LexicalHandler#endDTD()
     */
    public void endDTD() throws SAXException
    {
        couldThrowSAXException();
    }
    /**
    /* <p>
    /* 
     * @see org.xml.sax.ext.LexicalHandler#startEntity(java.lang.String)
     */
    public void startEntity(String arg0) throws SAXException
    {
        couldThrowSAXException();
    }
    /**
    /* <p>
    /* 
     * @see org.xml.sax.ext.LexicalHandler#endEntity(java.lang.String)
     */
    public void endEntity(String arg0) throws SAXException
    {
        couldThrowSAXException();
    }
    /**
    /* <p>
    /* 
     * @see org.xml.sax.ext.LexicalHandler#startCDATA()
     */
    public void startCDATA() throws SAXException
    {
        couldThrowSAXException();
    }
    /**
    /* <p>
    /* 
     * @see org.xml.sax.ext.LexicalHandler#endCDATA()
     */
    public void endCDATA() throws SAXException
    {
        couldThrowSAXException();
    }
    /**
    /* <p>
    /* 
     * @see org.xml.sax.ext.LexicalHandler#comment(char[], int, int)
     */
    public void comment(char[] arg0, int arg1, int arg2) throws SAXException
    {
        couldThrowSAXException();
    }
    /**
    /* <p>
    /* 
     * @see XSLOutputAttributes#getDoctypePublic()
     */
    public String getDoctypePublic()
    {
        aMethodIsCalled();
        return null;
    }
    /**
    /* <p>
    /* 
     * @see XSLOutputAttributes#getDoctypeSystem()
     */
    public String getDoctypeSystem()
    {
        aMethodIsCalled();
        return null;
    }
    /**
    /* <p>
    /* 
     * @see XSLOutputAttributes#getEncoding()
     */
    public String getEncoding()
    {
        aMethodIsCalled();
        return null;
    }
    /**
    /* <p>
    /* 
     * @see XSLOutputAttributes#getIndent()
     */
    public boolean getIndent()
    {
        aMethodIsCalled();
        return false;
    }
    /**
    /* <p>
    /* 
     * @see XSLOutputAttributes#getIndentAmount()
     */
    public int getIndentAmount()
    {
        aMethodIsCalled();
        return 0;
    }
    /**
    /* <p>
    /* 
     * @see XSLOutputAttributes#getMediaType()
     */
    public String getMediaType()
    {
        aMethodIsCalled();
        return null;
    }
    /**
    /* <p>
    /* 
     * @see XSLOutputAttributes#getOmitXMLDeclaration()
     */
    public boolean getOmitXMLDeclaration()
    {
        aMethodIsCalled();
        return false;
    }
    /**
    /* <p>
    /* 
     * @see XSLOutputAttributes#getStandalone()
     */
    public String getStandalone()
    {
        aMethodIsCalled();
        return null;
    }
    /**
    /* <p>
    /* 
     * @see XSLOutputAttributes#getVersion()
     */
    public String getVersion()
    {
        aMethodIsCalled();
        return null;
    }
    /**
    /* <p>
    /* 
     * @see XSLOutputAttributes#setCdataSectionElements
     */
    public void setCdataSectionElements(Hashtable h) throws Exception
    {
        couldThrowException();
    }
    /**
    /* <p>
    /* 
     * @see XSLOutputAttributes#setDoctype(java.lang.String, java.lang.String)
     */
    public void setDoctype(String system, String pub)
    {
        aMethodIsCalled();
    }
    /**
    /* <p>
    /* 
     * @see XSLOutputAttributes#setDoctypePublic(java.lang.String)
     */
    public void setDoctypePublic(String doctype)
    {
        aMethodIsCalled();
    }
    /**
    /* <p>
    /* 
     * @see XSLOutputAttributes#setDoctypeSystem(java.lang.String)
     */
    public void setDoctypeSystem(String doctype)
    {
        aMethodIsCalled();
    }
    /**
    /* <p>
    /* 
     * @see XSLOutputAttributes#setEncoding(java.lang.String)
     */
    public void setEncoding(String encoding)
    {
        aMethodIsCalled();
    }
    /**
    /* <p>
    /* 
     * @see XSLOutputAttributes#setMediaType(java.lang.String)
     */
    public void setMediaType(String mediatype)
    {
        aMethodIsCalled();
    }
    /**
    /* <p>
    /* 
     * @see XSLOutputAttributes#setOmitXMLDeclaration(boolean)
     */
    public void setOmitXMLDeclaration(boolean b)
    {
        aMethodIsCalled();
    }
    /**
    /* <p>
    /* 
     * @see XSLOutputAttributes#setStandalone(java.lang.String)
     */
    public void setStandalone(String standalone)
    {
        aMethodIsCalled();
    }
    /**
    /* <p>
    /* 
     * @see org.xml.sax.ext.DeclHandler#elementDecl(java.lang.String, java.lang.String)
     */
    public void elementDecl(String arg0, String arg1) throws SAXException
    {
        couldThrowSAXException();
    }
    /**
    /* <p>
    /* 
     * @see org.xml.sax.ext.DeclHandler#attributeDecl(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public void attributeDecl(
        String arg0,
        String arg1,
        String arg2,
        String arg3,
        String arg4)
        throws SAXException
    {
        couldThrowSAXException();
    }
    /**
    /* <p>
    /* 
     * @see org.xml.sax.ext.DeclHandler#internalEntityDecl(java.lang.String, java.lang.String)
     */
    public void internalEntityDecl(String arg0, String arg1)
        throws SAXException
    {
        couldThrowSAXException();
    }
    /**
    /* <p>
    /* 
     * @see org.xml.sax.ext.DeclHandler#externalEntityDecl(java.lang.String, java.lang.String, java.lang.String)
     */
    public void externalEntityDecl(String arg0, String arg1, String arg2)
        throws SAXException
    {
        couldThrowSAXException();
    }
    /**
    /* <p>
    /* 
     * @see org.xml.sax.ErrorHandler#warning(org.xml.sax.SAXParseException)
     */
    public void warning(SAXParseException arg0) throws SAXException
    {
        couldThrowSAXException();
    }
    /**
    /* <p>
    /* 
     * @see org.xml.sax.ErrorHandler#error(org.xml.sax.SAXParseException)
     */
    public void error(SAXParseException arg0) throws SAXException
    {
        couldThrowSAXException();
    }
    /**
    /* <p>
    /* 
     * @see org.xml.sax.ErrorHandler#fatalError(org.xml.sax.SAXParseException)
     */
    public void fatalError(SAXParseException arg0) throws SAXException
    {
        couldThrowSAXException();
    }
    /**
    /* <p>
    /* 
     * @see Serializer#asDOMSerializer()
     */
    public DOMSerializer asDOMSerializer() throws IOException
    {
        couldThrowIOException();
        return null;
    }

    /**
    /* <p>
    /* 
     * @see SerializationHandler#setNamespaceMappings(NamespaceMappings)
     */
    public void setNamespaceMappings(NamespaceMappings mappings) {
        aMethodIsCalled();
    }

    /**
    /* <p>
    /* 
     * @see ExtendedContentHandler#setSourceLocator(javax.xml.transform.SourceLocator)
     */
    public void setSourceLocator(SourceLocator locator)
    {
        aMethodIsCalled();
    }

    /**
    /* <p>
    /* 
     * @see ExtendedContentHandler#addUniqueAttribute(java.lang.String, java.lang.String, int)
     */
    public void addUniqueAttribute(String name, String value, int flags)
        throws SAXException
    {
        couldThrowSAXException();
    }

    /**
    /* <p>
    /* 
     * @see ExtendedContentHandler#characters(org.w3c.dom.Node)
     */
    public void characters(Node node) throws SAXException
    {
        couldThrowSAXException();
    }

    /**
    /* <p>
    /* 
     * @see ExtendedContentHandler#addXSLAttribute(java.lang.String, java.lang.String, java.lang.String)
     */
    public void addXSLAttribute(String qName, String value, String uri)
    {
        aMethodIsCalled();
    }

    /**
    /* <p>
    /* 
     * @see ExtendedContentHandler#addAttribute(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public void addAttribute(String uri, String localName, String rawName, String type, String value) throws SAXException
    {
        couldThrowSAXException();
    }
    /**
    /* <p>
    /* 
     * @see org.xml.sax.DTDHandler#notationDecl(java.lang.String, java.lang.String, java.lang.String)
     */
    public void notationDecl(String arg0, String arg1, String arg2) throws SAXException
    {
        couldThrowSAXException();
    }

    /**
    /* <p>
    /* 
     * @see org.xml.sax.DTDHandler#unparsedEntityDecl(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public void unparsedEntityDecl(
        String arg0,
        String arg1,
        String arg2,
        String arg3)
        throws SAXException {
        couldThrowSAXException();
    }

    /**
    /* <p>
    /* 
     * @see SerializationHandler#setDTDEntityExpansion(boolean)
     */
    public void setDTDEntityExpansion(boolean expand) {
        aMethodIsCalled();

    }
}
