/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001-2004 The Apache Software Foundation.
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
 *  版权所有2001-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: ToXMLSAXHandler.java,v 1.3 2005/09/28 13:49:08 pvedula Exp $
 * <p>
 *  $ Id：ToXMLSAXHandler.java,v 1.3 2005/09/28 13:49:08 pvedula Exp $
 * 
 */
 package com.sun.org.apache.xml.internal.serializer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Properties;

import javax.xml.transform.Result;

import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;

/**
 * This class receives notification of SAX-like events, and with gathered
 * information over these calls it will invoke the equivalent SAX methods
 * on a handler, the ultimate xsl:output method is known to be "xml".
 *
 * This class is not a public API, it is only public because it is used by Xalan.
 * @xsl.usage internal
 * <p>
 *  此类接收类SAX事件的通知,并且通过这些调用收集的信息,它将在处理程序上调用等效的SAX方法,最终的xsl：output方法被称为"xml"。
 * 
 *  这个类不是公共API,它只是公共的,因为它被Xalan使用。 @ xsl.usage internal
 * 
 */
public final class ToXMLSAXHandler extends ToSAXHandler
{

    /**
     * Keeps track of whether output escaping is currently enabled
     * <p>
     *  跟踪当前是否启用输出转义
     * 
     */
    protected boolean m_escapeSetting = true;

    public ToXMLSAXHandler()
    {
        // default constructor (need to set content handler ASAP !)
        m_prefixMap = new NamespaceMappings();
        initCDATA();
    }

    /**
    /* <p>
    /* 
     * @see Serializer#getOutputFormat()
     */
    public Properties getOutputFormat()
    {
        return null;
    }

    /**
    /* <p>
    /* 
     * @see Serializer#getOutputStream()
     */
    public OutputStream getOutputStream()
    {
        return null;
    }

    /**
    /* <p>
    /* 
     * @see Serializer#getWriter()
     */
    public Writer getWriter()
    {
        return null;
    }

    /**
     * Do nothing for SAX.
     * <p>
     *  对SAX不执行任何操作。
     * 
     */
    public void indent(int n) throws SAXException
    {
    }


    /**
    /* <p>
    /* 
     * @see DOMSerializer#serialize(Node)
     */
    public void serialize(Node node) throws IOException
    {
    }

    /**
    /* <p>
    /* 
     * @see SerializationHandler#setEscaping(boolean)
     */
    public boolean setEscaping(boolean escape) throws SAXException
    {
        boolean oldEscapeSetting = m_escapeSetting;
        m_escapeSetting = escape;

        if (escape) {
            processingInstruction(Result.PI_ENABLE_OUTPUT_ESCAPING, "");
        } else {
            processingInstruction(Result.PI_DISABLE_OUTPUT_ESCAPING, "");
        }

        return oldEscapeSetting;
    }

    /**
    /* <p>
    /* 
     * @see Serializer#setOutputFormat(Properties)
     */
    public void setOutputFormat(Properties format)
    {
    }

    /**
    /* <p>
    /* 
     * @see Serializer#setOutputStream(OutputStream)
     */
    public void setOutputStream(OutputStream output)
    {
    }

    /**
    /* <p>
    /* 
     * @see Serializer#setWriter(Writer)
     */
    public void setWriter(Writer writer)
    {
    }

    /**
    /* <p>
    /* 
     * @see org.xml.sax.ext.DeclHandler#attributeDecl(String, String, String, String, String)
     */
    public void attributeDecl(
        String arg0,
        String arg1,
        String arg2,
        String arg3,
        String arg4)
        throws SAXException
    {
    }

    /**
    /* <p>
    /* 
     * @see org.xml.sax.ext.DeclHandler#elementDecl(String, String)
     */
    public void elementDecl(String arg0, String arg1) throws SAXException
    {
    }

    /**
    /* <p>
    /* 
     * @see org.xml.sax.ext.DeclHandler#externalEntityDecl(String, String, String)
     */
    public void externalEntityDecl(String arg0, String arg1, String arg2)
        throws SAXException
    {
    }

    /**
    /* <p>
    /* 
     * @see org.xml.sax.ext.DeclHandler#internalEntityDecl(String, String)
     */
    public void internalEntityDecl(String arg0, String arg1)
        throws SAXException
    {
    }

    /**
     * Receives notification of the end of the document.
     * <p>
     *  接收文档结束的通知。
     * 
     * 
     * @see org.xml.sax.ContentHandler#endDocument()
     */
    public void endDocument() throws SAXException
    {

        flushPending();

        // Close output document
        m_saxHandler.endDocument();

        if (m_tracer != null)
            super.fireEndDoc();
    }

    /**
     * This method is called when all the data needed for a call to the
     * SAX handler's startElement() method has been gathered.
     * <p>
     *  当调用SAX处理程序的startElement()方法所需的所有数据都已收集时,调用此方法。
     * 
     */
    protected void closeStartTag() throws SAXException
    {

        m_elemContext.m_startTagOpen = false;

        final String localName = getLocalName(m_elemContext.m_elementName);
        final String uri = getNamespaceURI(m_elemContext.m_elementName, true);

        // Now is time to send the startElement event
        if (m_needToCallStartDocument)
        {
            startDocumentInternal();
        }
        m_saxHandler.startElement(uri, localName, m_elemContext.m_elementName, m_attributes);
        // we've sent the official SAX attributes on their way,
        // now we don't need them anymore.
        m_attributes.clear();

        if(m_state != null)
          m_state.setCurrentNode(null);
    }

    /**
     * Closes ane open cdata tag, and
     * unlike the this.endCDATA() method (from the LexicalHandler) interface,
     * this "internal" method will send the endCDATA() call to the wrapped
     * handler.
     *
     * <p>
     * 关闭ane打开cdata标签,不像this.endCDATA()方法(从LexicalHandler)接口,这个"内部"方法将发送endCDATA()调用到被包装的处理程序。
     * 
     */
    public void closeCDATA() throws SAXException
    {

        // Output closing bracket - "]]>"
        if (m_lexHandler != null && m_cdataTagOpen) {
            m_lexHandler.endCDATA();
        }


        // There are no longer any calls made to
        // m_lexHandler.startCDATA() without a balancing call to
        // m_lexHandler.endCDATA()
        // so we set m_cdataTagOpen to false to remember this.
        m_cdataTagOpen = false;
    }

    /**
    /* <p>
    /* 
     * @see org.xml.sax.ContentHandler#endElement(String, String, String)
     */
    public void endElement(String namespaceURI, String localName, String qName)
        throws SAXException
    {
        // Close any open elements etc.
        flushPending();

        if (namespaceURI == null)
        {
            if (m_elemContext.m_elementURI != null)
                namespaceURI = m_elemContext.m_elementURI;
            else
                namespaceURI = getNamespaceURI(qName, true);
        }

        if (localName == null)
        {
            if (m_elemContext.m_elementLocalName != null)
                localName = m_elemContext.m_elementLocalName;
            else
                localName = getLocalName(qName);
        }

        m_saxHandler.endElement(namespaceURI, localName, qName);

        if (m_tracer != null)
            super.fireEndElem(qName);

        /* Pop all namespaces at the current element depth.
         * We are not waiting for official endPrefixMapping() calls.
         * <p>
         *  我们不等待官方的endPrefixMapping()调用。
         * 
         */
        m_prefixMap.popNamespaces(m_elemContext.m_currentElemDepth,
            m_saxHandler);
        m_elemContext = m_elemContext.m_prev;
    }

    /**
    /* <p>
    /* 
     * @see org.xml.sax.ContentHandler#endPrefixMapping(String)
     */
    public void endPrefixMapping(String prefix) throws SAXException
    {
        /* poping all prefix mappings should have been done
         * in endElement() already
         * <p>
         *  在endElement()已经
         * 
         */
         return;
    }

    /**
    /* <p>
    /* 
     * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
     */
    public void ignorableWhitespace(char[] arg0, int arg1, int arg2)
        throws SAXException
    {
        m_saxHandler.ignorableWhitespace(arg0,arg1,arg2);
    }

    /**
    /* <p>
    /* 
     * @see org.xml.sax.ContentHandler#setDocumentLocator(Locator)
     */
    public void setDocumentLocator(Locator arg0)
    {
        super.setDocumentLocator(arg0);
        m_saxHandler.setDocumentLocator(arg0);
    }

    /**
    /* <p>
    /* 
     * @see org.xml.sax.ContentHandler#skippedEntity(String)
     */
    public void skippedEntity(String arg0) throws SAXException
    {
        m_saxHandler.skippedEntity(arg0);
    }

    /**
    /* <p>
    /* 
     * @see org.xml.sax.ContentHandler#startPrefixMapping(String, String)
     * @param prefix The prefix that maps to the URI
     * @param uri The URI for the namespace
     */
    public void startPrefixMapping(String prefix, String uri)
        throws SAXException
    {
       startPrefixMapping(prefix, uri, true);
    }

    /**
     * Remember the prefix/uri mapping at the current nested element depth.
     *
     * <p>
     *  记住在当前嵌套元素深度的前缀/ uri映射。
     * 
     * 
     * @see org.xml.sax.ContentHandler#startPrefixMapping(String, String)
     * @param prefix The prefix that maps to the URI
     * @param uri The URI for the namespace
     * @param shouldFlush a flag indicating if the mapping applies to the
     * current element or an up coming child (not used).
     */

    public boolean startPrefixMapping(
        String prefix,
        String uri,
        boolean shouldFlush)
        throws org.xml.sax.SAXException
    {

        /* Remember the mapping, and at what depth it was declared
         * This is one greater than the current depth because these
         * mappings will apply to the next depth. This is in
         * consideration that startElement() will soon be called
         * <p>
         *  这是大于当前深度的一个,因为这些映射将应用于下一个深度。这是考虑到startElement()将很快被调用
         * 
         */

        boolean pushed;
        int pushDepth;
        if (shouldFlush)
        {
            flushPending();
            // the prefix mapping applies to the child element (one deeper)
            pushDepth = m_elemContext.m_currentElemDepth + 1;
        }
        else
        {
            // the prefix mapping applies to the current element
            pushDepth = m_elemContext.m_currentElemDepth;
        }
        pushed = m_prefixMap.pushNamespace(prefix, uri, pushDepth);

        if (pushed)
        {
            m_saxHandler.startPrefixMapping(prefix,uri);

            if (getShouldOutputNSAttr())
            {

                      /* Brian M.: don't know if we really needto do this. The
                       * callers of this object should have injected both
                       * startPrefixMapping and the attributes.  We are
                       * just covering our butt here.
                       * <p>
                       *  这个对象的调用者应该注入startPrefixMapping和属性。我们只是在这里覆盖我们的屁股。
                       * 
                       */
                      String name;
                    if (EMPTYSTRING.equals(prefix))
                    {
                        name = "xmlns";
                        addAttributeAlways(XMLNS_URI, name, name,"CDATA",uri, false);
                    }
                    else
                {
                        if (!EMPTYSTRING.equals(uri)) // hack for XSLTC attribset16 test
                        {                             // that maps ns1 prefix to "" URI
                            name = "xmlns:" + prefix;

                            /* for something like xmlns:abc="w3.pretend.org"
                                     *  the uri is the value, that is why we pass it in the
                                     * value, or 5th slot of addAttributeAlways()
                                     * <p>
                                     *  uri是值,这就是为什么我们在值中传递它,或第五个插槽的addAttributeAlways()
                                     * 
                                   */
                            addAttributeAlways(XMLNS_URI, prefix, name,"CDATA",uri, false );
                        }
                    }
            }
        }
        return pushed;
    }


    /**
    /* <p>
    /* 
     * @see org.xml.sax.ext.LexicalHandler#comment(char[], int, int)
     */
    public void comment(char[] arg0, int arg1, int arg2) throws SAXException
    {
        flushPending();
        if (m_lexHandler != null)
            m_lexHandler.comment(arg0, arg1, arg2);

        if (m_tracer != null)
            super.fireCommentEvent(arg0, arg1, arg2);
    }

    /**
    /* <p>
    /* 
     * @see org.xml.sax.ext.LexicalHandler#endCDATA()
     */
    public void endCDATA() throws SAXException
    {
        /* Normally we would do somthing with this but we ignore it.
         * The neccessary call to m_lexHandler.endCDATA() will be made
         * in flushPending().
         *
         * This is so that if we get calls like these:
         *   this.startCDATA();
         *   this.characters(chars1, off1, len1);
         *   this.endCDATA();
         *   this.startCDATA();
         *   this.characters(chars2, off2, len2);
         *   this.endCDATA();
         *
         * that we will only make these calls to the wrapped handlers:
         *
         *   m_lexHandler.startCDATA();
         *   m_saxHandler.characters(chars1, off1, len1);
         *   m_saxHandler.characters(chars1, off2, len2);
         *   m_lexHandler.endCDATA();
         *
         * We will merge adjacent CDATA blocks.
         * <p>
         *  对m_lexHandler.endCDATA()的必要调用将在flushPending()中进行。
         * 
         *  这是如此,如果我们得到这样的调用：this.startCDATA(); this.characters(chars1,off1,len1); this.endCDATA(); this.startCD
         * ATA(); this.characters(chars2,off2,len2); this.endCDATA();。
         * 
         *  我们将只对包装的处理程序进行这些调用：
         * 
         *  m_lexHandler.startCDATA(); m_saxHandler.characters(chars1,off1,len1); m_saxHandler.characters(chars1
         * ,off2,len2); m_lexHandler.endCDATA();。
         * 
         *  我们将合并相邻的CDATA块。
         * 
         */
    }

    /**
    /* <p>
    /* 
     * @see org.xml.sax.ext.LexicalHandler#endDTD()
     */
    public void endDTD() throws SAXException
    {
        if (m_lexHandler != null)
            m_lexHandler.endDTD();
    }

    /**
    /* <p>
    /* 
     * @see org.xml.sax.ext.LexicalHandler#startEntity(String)
     */
    public void startEntity(String arg0) throws SAXException
    {
        if (m_lexHandler != null)
            m_lexHandler.startEntity(arg0);
    }

    /**
    /* <p>
    /* 
     * @see ExtendedContentHandler#characters(String)
     */
    public void characters(String chars) throws SAXException
    {
        final int length = chars.length();
        if (length > m_charsBuff.length)
        {
            m_charsBuff = new char[length*2 + 1];
        }
        chars.getChars(0, length, m_charsBuff, 0);
        this.characters(m_charsBuff, 0, length);
    }

    /////////////////// from XSLTC //////////////
    public ToXMLSAXHandler(ContentHandler handler, String encoding)
    {
        super(handler, encoding);

        initCDATA();
        // initNamespaces();
        m_prefixMap = new NamespaceMappings();
    }

    public ToXMLSAXHandler(
        ContentHandler handler,
        LexicalHandler lex,
        String encoding)
    {
        super(handler, lex, encoding);

        initCDATA();
        //      initNamespaces();
        m_prefixMap = new NamespaceMappings();
    }

    /**
     * Start an element in the output document. This might be an XML element
     * (<elem>data</elem> type) or a CDATA section.
     * <p>
     *  在输出文档中启动元素。这可能是一个XML元素(<elem> data </elem> type)或一个CDATA节。
     * 
     */
    public void startElement(
    String elementNamespaceURI,
    String elementLocalName,
    String elementName) throws SAXException
    {
        startElement(
            elementNamespaceURI,elementLocalName,elementName, null);


    }
    public void startElement(String elementName) throws SAXException
    {
        startElement(null, null, elementName, null);
    }


    public void characters(char[] ch, int off, int len) throws SAXException
    {
        // We do the first two things in flushPending() but we don't
        // close any open CDATA calls.
        if (m_needToCallStartDocument)
        {
            startDocumentInternal();
            m_needToCallStartDocument = false;
        }

        if (m_elemContext.m_startTagOpen)
        {
            closeStartTag();
            m_elemContext.m_startTagOpen = false;
        }

        if (m_elemContext.m_isCdataSection && !m_cdataTagOpen
        && m_lexHandler != null)
        {
            m_lexHandler.startCDATA();
            // We have made a call to m_lexHandler.startCDATA() with
            // no balancing call to m_lexHandler.endCDATA()
            // so we set m_cdataTagOpen true to remember this.
            m_cdataTagOpen = true;
        }

        /* If there are any occurances of "]]>" in the character data
         * let m_saxHandler worry about it, we've already warned them with
         * the previous call of m_lexHandler.startCDATA();
         * <p>
         *  让m_saxHandler担心它,我们已经警告他们以前的调用m_lexHandler.startCDATA();
         * 
         */
        m_saxHandler.characters(ch, off, len);

        // time to generate characters event
        if (m_tracer != null)
            fireCharEvent(ch, off, len);
    }


    /**
    /* <p>
    /* 
     * @see ExtendedContentHandler#endElement(String)
     */
    public void endElement(String elemName) throws SAXException
    {
        endElement(null, null, elemName);
    }


    /**
     * Send a namespace declaration in the output document. The namespace
     * declaration will not be include if the namespace is already in scope
     * with the same prefix.
     * <p>
     * 在输出文档中发送命名空间声明。如果命名空间已经在具有相同前缀的作用域中,那么命名空间声明将不会被包含。
     * 
     */
    public void namespaceAfterStartElement(
        final String prefix,
        final String uri)
        throws SAXException
    {
        startPrefixMapping(prefix,uri,false);
    }

    /**
     *
     * <p>
     * 
     * @see org.xml.sax.ContentHandler#processingInstruction(String, String)
     * Send a processing instruction to the output document
     */
    public void processingInstruction(String target, String data)
        throws SAXException
    {
        flushPending();

        // Pass the processing instruction to the SAX handler
        m_saxHandler.processingInstruction(target, data);

        // we don't want to leave serializer to fire off this event,
        // so do it here.
        if (m_tracer != null)
            super.fireEscapingEvent(target, data);
    }

    /**
     * Undeclare the namespace that is currently pointed to by a given
     * prefix. Inform SAX handler if prefix was previously mapped.
     * <p>
     *  Undeclare由给定前缀当前指向的命名空间。如果先前映射了前缀,则通知SAX处理程序。
     * 
     */
    protected boolean popNamespace(String prefix)
    {
        try
        {
            if (m_prefixMap.popNamespace(prefix))
            {
                m_saxHandler.endPrefixMapping(prefix);
                return true;
            }
        }
        catch (SAXException e)
        {
            // falls through
        }
        return false;
    }

    public void startCDATA() throws SAXException
    {
        /* m_cdataTagOpen can only be true here if we have ignored the
         * previous call to this.endCDATA() and the previous call
         * this.startCDATA() before that is still "open". In this way
         * we merge adjacent CDATA. If anything else happened after the
         * ignored call to this.endCDATA() and this call then a call to
         * flushPending() would have been made which would have
         * closed the CDATA and set m_cdataTagOpen to false.
         * <p>
         *  先前调用this.endCDATA()和之前调用this.startCDATA()之前仍然是"打开"。以这种方式,我们合并相邻的CDATA。
         * 如果在忽略对this.endCDATA()的调用之后发生了其他事情,则将调用flushPending(),这将关闭CDATA并将m_cdataTagOpen设置为false。
         * 
         */
        if (!m_cdataTagOpen )
        {
            flushPending();
            if (m_lexHandler != null) {
                m_lexHandler.startCDATA();

                // We have made a call to m_lexHandler.startCDATA() with
                // no balancing call to m_lexHandler.endCDATA()
                // so we set m_cdataTagOpen true to remember this.
                m_cdataTagOpen = true;
            }
        }
    }

    /**
    /* <p>
    /* 
     * @see org.xml.sax.ContentHandler#startElement(String, String, String, Attributes)
     */
    public void startElement(
    String namespaceURI,
    String localName,
    String name,
    Attributes atts)
        throws SAXException
    {
        flushPending();
        super.startElement(namespaceURI, localName, name, atts);

        // Handle document type declaration (for first element only)
         if (m_needToOutputDocTypeDecl)
         {
             String doctypeSystem = getDoctypeSystem();
             if (doctypeSystem != null && m_lexHandler != null)
             {
                 String doctypePublic = getDoctypePublic();
                 if (doctypeSystem != null)
                     m_lexHandler.startDTD(
                         name,
                         doctypePublic,
                         doctypeSystem);
             }
             m_needToOutputDocTypeDecl = false;
         }
        m_elemContext = m_elemContext.push(namespaceURI, localName, name);

        // ensurePrefixIsDeclared depends on the current depth, so
        // the previous increment is necessary where it is.
        if (namespaceURI != null)
            ensurePrefixIsDeclared(namespaceURI, name);

        // add the attributes to the collected ones
        if (atts != null)
            addAttributes(atts);


        // do we really need this CDATA section state?
        m_elemContext.m_isCdataSection = isCdataSection();

    }

    private void ensurePrefixIsDeclared(String ns, String rawName)
        throws org.xml.sax.SAXException
    {

        if (ns != null && ns.length() > 0)
        {
            int index;
            final boolean no_prefix = ((index = rawName.indexOf(":")) < 0);
            String prefix = (no_prefix) ? "" : rawName.substring(0, index);


            if (null != prefix)
            {
                String foundURI = m_prefixMap.lookupNamespace(prefix);

                if ((null == foundURI) || !foundURI.equals(ns))
                {
                    this.startPrefixMapping(prefix, ns, false);

                    if (getShouldOutputNSAttr()) {
                        // Bugzilla1133: Generate attribute as well as namespace event.
                        // SAX does expect both.
                        this.addAttributeAlways(
                            "http://www.w3.org/2000/xmlns/",
                            no_prefix ? "xmlns" : prefix,  // local name
                            no_prefix ? "xmlns" : ("xmlns:"+ prefix), // qname
                            "CDATA",
                            ns,
                            false);
                    }
                }

            }
        }
    }
    /**
     * Adds the given attribute to the set of attributes, and also makes sure
     * that the needed prefix/uri mapping is declared, but only if there is a
     * currently open element.
     *
     * <p>
     *  将给定属性添加到属性集,并且还确保声明所需的前缀/ uri映射,但仅当存在当前打开的元素时。
     * 
     * 
     * @param uri the URI of the attribute
     * @param localName the local name of the attribute
     * @param rawName    the qualified name of the attribute
     * @param type the type of the attribute (probably CDATA)
     * @param value the value of the attribute
     * @param XSLAttribute true if this attribute is coming from an xsl:attribute element
     * @see ExtendedContentHandler#addAttribute(String, String, String, String, String)
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
        if (m_elemContext.m_startTagOpen)
        {
            ensurePrefixIsDeclared(uri, rawName);
            addAttributeAlways(uri, localName, rawName, type, value, false);
        }

    }

    /**
     * Try's to reset the super class and reset this class for
     * re-use, so that you don't need to create a new serializer
     * (mostly for performance reasons).
     *
     * <p>
     *  尝试重置超类并重置此类以供重用,以便您不需要创建新的序列化程序(主要是出于性能原因)。
     * 
     * 
     * @return true if the class was successfuly reset.
     * @see Serializer#reset()
     */
    public boolean reset()
    {
        boolean wasReset = false;
        if (super.reset())
        {
            resetToXMLSAXHandler();
            wasReset = true;
        }
        return wasReset;
    }

    /**
     * Reset all of the fields owned by ToXMLSAXHandler class
     *
     * <p>
     *  重置ToXMLSAXHandler类拥有的所有字段
     */
    private void resetToXMLSAXHandler()
    {
        this.m_escapeSetting = true;
    }

}
