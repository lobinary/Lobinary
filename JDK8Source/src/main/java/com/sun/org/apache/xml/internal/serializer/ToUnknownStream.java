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
 * $Id: ToUnknownStream.java,v 1.3 2005/09/28 13:49:08 pvedula Exp $
 * <p>
 *  $ Id：ToUnknownStream.java,v 1.3 2005/09/28 13:49:08 pvedula Exp $
 * 
 */
package com.sun.org.apache.xml.internal.serializer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Properties;
import java.util.Vector;

import javax.xml.transform.SourceLocator;
import javax.xml.transform.Transformer;

import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;


/**
 *This class wraps another SerializationHandler. The wrapped object will either
 * handler XML or HTML, which is not known until a little later when the first XML
 * tag is seen.  If the first tag is <html> then the wrapped object is an HTML
 * handler, otherwise it is an XML handler.
 *
 * This class effectively caches the first few calls to it then passes them
 * on to the wrapped handler (once it exists).  After that subsequent calls a
 * simply passed directly to the wrapped handler.
 *
 * The user of this class doesn't know if the output is ultimatley XML or HTML.
 *
 * This class is not a public API, it is public because it is used within Xalan.
 * @xsl.usage internal
 * <p>
 *  他的类包装另一个SerializationHandler。被包装的对象将处理XML或HTML,直到稍后当第一个XML标签被看到时才知道。
 * 如果第一个标签是<html>,则被包装的对象是一个HTML处理程序,否则它是一个XML处理程序。
 * 
 *  这个类有效地缓存了前几个调用,然后将它们传递到包装的处理程序(一旦存在)。之后,后续调用简单地直接传递到包装的处理程序。
 * 
 *  此类的用户不知道输出是ultimatley XML还是HTML。
 * 
 *  这个类不是公共API,它是公共的,因为它在Xalan内使用。 @ xsl.usage internal
 * 
 */
public final class ToUnknownStream extends SerializerBase
{

    /**
     * The wrapped handler, initially XML but possibly switched to HTML
     * <p>
     *  包装的处理程序,最初是XML,但可能切换到HTML
     * 
     */
    private SerializationHandler m_handler;

    /**
     * A String with no characters
     * <p>
     * 没有字符的字符串
     * 
     */
    private static final String EMPTYSTRING = "";

    /**
     * true if the underlying handler (XML or HTML) is fully initialized
     * <p>
     *  如果底层处理程序(XML或HTML)完全初始化,则为true
     * 
     */
    private boolean m_wrapped_handler_not_initialized = false;


    /**
     * the prefix of the very first tag in the document
     * <p>
     *  文档中第一个标记的前缀
     * 
     */
    private String m_firstElementPrefix;
    /**
     * the element name (including any prefix) of the very first tag in the document
     * <p>
     *  文档中第一个标记的元素名称(包括任何前缀)
     * 
     */
    private String m_firstElementName;

    /**
     * the namespace URI associated with the first element
     * <p>
     *  与第一个元素相关联的命名空间URI
     * 
     */
    private String m_firstElementURI;

    /**
     * the local name (no prefix) associated with the first element
     * <p>
     *  与第一个元素相关联的本地名称(无前缀)
     * 
     */
    private String m_firstElementLocalName = null;

    /**
     * true if the first tag has been emitted to the wrapped handler
     * <p>
     *  如果第一个标签已发送到包装的处理程序,则为true
     * 
     */
    private boolean m_firstTagNotEmitted = true;

    /**
     * A collection of namespace URI's (only for first element).
     * _namespacePrefix has the matching prefix for these URI's
     * <p>
     *  命名空间URI的集合(仅适用于第一个元素)。 _namespacePrefix具有这些URI的匹配前缀
     * 
     */
    private Vector m_namespaceURI = null;
    /**
     * A collection of namespace Prefix (only for first element)
     * _namespaceURI has the matching URIs for these prefix'
     * <p>
     *  命名空间前缀(仅适用于第一个元素)的集合_namespaceURI具有匹配的URIs这些前缀'
     * 
     */
    private Vector m_namespacePrefix = null;

    /**
     * true if startDocument() was called before the underlying handler
     * was initialized
     * <p>
     *  true如果在初始化底层处理程序之前调用startDocument()
     * 
     */
    private boolean m_needToCallStartDocument = false;
    /**
     * true if setVersion() was called before the underlying handler
     * was initialized
     * <p>
     *  true如果setVersion()在底层处理程序初始化之前调用
     * 
     */
    private boolean m_setVersion_called = false;
    /**
     * true if setDoctypeSystem() was called before the underlying handler
     * was initialized
     * <p>
     *  true如果setDoctypeSystem()在底层处理程序初始化之前调用
     * 
     */
    private boolean m_setDoctypeSystem_called = false;
    /**
     * true if setDoctypePublic() was called before the underlying handler
     * was initialized
     * <p>
     *  true如果setDoctypePublic()在底层处理程序初始化之前调用
     * 
     */
    private boolean m_setDoctypePublic_called = false;
    /**
     * true if setMediaType() was called before the underlying handler
     * was initialized
     * <p>
     *  true如果setMediaType()在底层处理程序初始化之前调用
     * 
     */
    private boolean m_setMediaType_called = false;

    /**
     * Default constructor.
     * Initially this object wraps an XML Stream object, so _handler is never null.
     * That may change later to an HTML Stream object.
     * <p>
     *  默认构造函数。最初,此对象包装一个XML Stream对象,因此_handler永远不为null。这可能稍后更改为HTML Stream对象。
     * 
     */
    public ToUnknownStream()
    {
        m_handler = new ToXMLStream();
    }

    /**
    /* <p>
    /* 
     * @see Serializer#asContentHandler()
     * @return the wrapped XML or HTML handler
     */
    public ContentHandler asContentHandler() throws IOException
    {
        /* don't return the real handler ( m_handler ) because
         * that would expose the real handler to the outside.
         * Keep m_handler private so it can be internally swapped
         * to an HTML handler.
         * <p>
         *  这将暴露真正的处理程序到外面。保持m_handler的私有,所以它可以在内部交换到一个HTML处理程序。
         * 
         */
        return this;
    }

    /**
    /* <p>
    /* 
     * @see SerializationHandler#close()
     */
    public void close()
    {
        m_handler.close();
    }

    /**
    /* <p>
    /* 
     * @see Serializer#getOutputFormat()
     * @return the properties of the underlying handler
     */
    public Properties getOutputFormat()
    {
        return m_handler.getOutputFormat();
    }

    /**
    /* <p>
    /* 
     * @see Serializer#getOutputStream()
     * @return the OutputStream of the underlying XML or HTML handler
     */
    public OutputStream getOutputStream()
    {
        return m_handler.getOutputStream();
    }

    /**
    /* <p>
    /* 
     * @see Serializer#getWriter()
     * @return the Writer of the underlying XML or HTML handler
     */
    public Writer getWriter()
    {
        return m_handler.getWriter();
    }

    /**
     * passes the call on to the underlying HTML or XML handler
     * <p>
     *  将调用传递到底层的HTML或XML处理程序
     * 
     * 
     * @see Serializer#reset()
     * @return ???
     */
    public boolean reset()
    {
        return m_handler.reset();
    }

    /**
     * Converts the DOM node to output
     * <p>
     *  将DOM节点转换为输出
     * 
     * 
     * @param node the DOM node to transform to output
     * @see DOMSerializer#serialize(Node)
     *
     */
    public void serialize(Node node) throws IOException
    {
        if (m_firstTagNotEmitted)
        {
            flush();
        }
        m_handler.serialize(node);
    }

    /**
    /* <p>
    /* 
     * @see SerializationHandler#setEscaping(boolean)
     */
    public boolean setEscaping(boolean escape) throws SAXException
    {
        return m_handler.setEscaping(escape);
    }

    /**
     * Set the properties of the handler
     * <p>
     *  设置处理程序的属性
     * 
     * 
     * @param format the output properties to set
     * @see Serializer#setOutputFormat(Properties)
     */
    public void setOutputFormat(Properties format)
    {
        m_handler.setOutputFormat(format);
    }

    /**
     * Sets the output stream to write to
     * <p>
     *  设置要写入的输出流
     * 
     * 
     * @param output the OutputStream to write to
     * @see Serializer#setOutputStream(OutputStream)
     */
    public void setOutputStream(OutputStream output)
    {
        m_handler.setOutputStream(output);
    }

    /**
     * Sets the writer to write to
     * <p>
     * 将写入设置为写入
     * 
     * 
     * @param writer the writer to write to
     * @see Serializer#setWriter(Writer)
     */
    public void setWriter(Writer writer)
    {
        m_handler.setWriter(writer);
    }

    /**
     * Adds an attribute to the currenly open tag
     * <p>
     *  向currenly打开的标签添加属性
     * 
     * 
     * @param uri the URI of a namespace
     * @param localName the attribute name, without prefix
     * @param rawName the attribute name, with prefix (if any)
     * @param type the type of the attribute, typically "CDATA"
     * @param value the value of the parameter
     * @param XSLAttribute true if this attribute is coming from an xsl:attribute element
     * @see ExtendedContentHandler#addAttribute(String, String, String, String, String)
     */
    public void addAttribute(
        String uri,
        String localName,
        String rawName,
        String type,
        String value)
        throws SAXException
    {
        addAttribute(uri, localName, rawName, type, value, false);
    }

    /**
     * Adds an attribute to the currenly open tag
     * <p>
     *  向currenly打开的标签添加属性
     * 
     * 
     * @param uri the URI of a namespace
     * @param localName the attribute name, without prefix
     * @param rawName the attribute name, with prefix (if any)
     * @param type the type of the attribute, typically "CDATA"
     * @param value the value of the parameter
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
        if (m_firstTagNotEmitted)
        {
            flush();
        }
        m_handler.addAttribute(uri, localName, rawName, type, value, XSLAttribute);
    }
    /**
     * Adds an attribute to the currenly open tag
     * <p>
     *  向currenly打开的标签添加属性
     * 
     * 
     * @param rawName the attribute name, with prefix (if any)
     * @param value the value of the parameter
     * @see ExtendedContentHandler#addAttribute(String, String)
     */
    public void addAttribute(String rawName, String value)
    {
        if (m_firstTagNotEmitted)
        {
            flush();
        }
        m_handler.addAttribute(rawName, value);

    }

    /**
     * Adds a unique attribute to the currenly open tag
     * <p>
     *  向currenly打开的标记添加唯一属性
     * 
     */
    public void addUniqueAttribute(String rawName, String value, int flags)
        throws SAXException
    {
        if (m_firstTagNotEmitted)
        {
            flush();
        }
        m_handler.addUniqueAttribute(rawName, value, flags);

    }

    /**
     * Converts the String to a character array and calls the SAX method
     * characters(char[],int,int);
     *
     * <p>
     *  将字符串转换为字符数组,并调用SAX方法字符(char [],int,int);
     * 
     * 
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

    /**
     * Pass the call on to the underlying handler
     * <p>
     *  将调用传递给底层处理程序
     * 
     * 
     * @see ExtendedContentHandler#endElement(String)
     */
    public void endElement(String elementName) throws SAXException
    {
        if (m_firstTagNotEmitted)
        {
            flush();
        }
        m_handler.endElement(elementName);
    }


    /**
    /* <p>
    /* 
     * @see org.xml.sax.ContentHandler#startPrefixMapping(String, String)
     * @param prefix The prefix that maps to the URI
     * @param uri The URI for the namespace
     */
    public void startPrefixMapping(String prefix, String uri) throws SAXException
    {
        this.startPrefixMapping(prefix,uri, true);
    }

    /**
     * This method is used when a prefix/uri namespace mapping
     * is indicated after the element was started with a
     * startElement() and before and endElement().
     * startPrefixMapping(prefix,uri) would be used before the
     * startElement() call.
     * <p>
     *  在使用startElement()和before和endElement()启动元素之后指示前缀/ uri命名空间映射时,将使用此方法。
     *  startPrefixMapping(prefix,uri)将在startElement()调用之前使用。
     * 
     * 
     * @param uri the URI of the namespace
     * @param prefix the prefix associated with the given URI.
     *
     * @see ExtendedContentHandler#namespaceAfterStartElement(String, String)
     */
    public void namespaceAfterStartElement(String prefix, String uri)
        throws SAXException
    {
        // hack for XSLTC with finding URI for default namespace
        if (m_firstTagNotEmitted && m_firstElementURI == null && m_firstElementName != null)
        {
            String prefix1 = getPrefixPart(m_firstElementName);
            if (prefix1 == null && EMPTYSTRING.equals(prefix))
            {
                // the elements URI is not known yet, and it
                // doesn't have a prefix, and we are currently
                // setting the uri for prefix "", so we have
                // the uri for the element... lets remember it
                m_firstElementURI = uri;
            }
        }
        startPrefixMapping(prefix,uri, false);
    }

    public boolean startPrefixMapping(String prefix, String uri, boolean shouldFlush)
        throws SAXException
    {
        boolean pushed = false;
        if (m_firstTagNotEmitted)
        {
            if (m_firstElementName != null && shouldFlush)
            {
                /* we've already seen a startElement, and this is a prefix mapping
                 * for the up coming element, so flush the old element
                 * then send this event on its way.
                 * <p>
                 *  对于上来的元素,所以刷新旧元素,然后发送这个事件的方式。
                 * 
                 */
                flush();
                pushed = m_handler.startPrefixMapping(prefix, uri, shouldFlush);
            }
            else
            {
                if (m_namespacePrefix == null)
                {
                    m_namespacePrefix = new Vector();
                    m_namespaceURI = new Vector();
                }
                m_namespacePrefix.addElement(prefix);
                m_namespaceURI.addElement(uri);

                if (m_firstElementURI == null)
                {
                    if (prefix.equals(m_firstElementPrefix))
                        m_firstElementURI = uri;
                }
            }

        }
        else
        {
           pushed = m_handler.startPrefixMapping(prefix, uri, shouldFlush);
        }
        return pushed;
    }

    /**
      * This method cannot be cached because default is different in
      * HTML and XML (we need more than a boolean).
      * <p>
      *  此方法无法缓存,因为默认值在HTML和XML中不同(我们需要一个布尔值)。
      * 
      */

    public void setVersion(String version)
    {
        m_handler.setVersion(version);

        // Cache call to setVersion()
        //       super.setVersion(version);
        m_setVersion_called = true;
    }

    /**
    /* <p>
    /* 
     * @see org.xml.sax.ContentHandler#startDocument()
     */
    public void startDocument() throws SAXException
    {
        m_needToCallStartDocument = true;
    }



    public void startElement(String qName) throws SAXException
    {
        this.startElement(null, null, qName, null);
    }

    public void startElement(String namespaceURI, String localName, String qName) throws SAXException
    {
        this.startElement(namespaceURI, localName, qName, null);
    }

    public void startElement(
        String namespaceURI,
        String localName,
        String elementName,
        Attributes atts) throws SAXException
    {

        if (m_needToCallSetDocumentInfo){
            super.setDocumentInfo();
            m_needToCallSetDocumentInfo = false;
        }

        /* we are notified of the start of an element */
        if (m_firstTagNotEmitted)
        {
            /* we have not yet sent the first element on its way */
            if (m_firstElementName != null)
            {
                /* this is not the first element, but a later one.
                 * But we have the old element pending, so flush it out,
                 * then send this one on its way.
                 * <p>
                 *  但是我们有旧元素待处理,所以冲洗出来,然后发送这个在它的路上。
                 * 
                 */
                flush();
                m_handler.startElement(namespaceURI, localName, elementName,  atts);
            }
            else
            {
                /* this is the very first element that we have seen,
                 * so save it for flushing later.  We may yet get to know its
                 * URI due to added attributes.
                 * <p>
                 *  所以保存为稍后刷新。由于添加的属性,我们可能还是知道它的URI。
                 * 
                 */

                m_wrapped_handler_not_initialized = true;
                m_firstElementName = elementName;

                // null if not known
                m_firstElementPrefix = getPrefixPartUnknown(elementName);

                // null if not known
                m_firstElementURI = namespaceURI;

                // null if not known
                m_firstElementLocalName = localName;

                if (m_tracer != null)
                    firePseudoElement(elementName);

                /* we don't want to call our own addAttributes, which
                 * merely delegates to the wrapped handler, but we want to
                 * add these attributes to m_attributes. So me must call super.
                 * addAttributes() In this case m_attributes is only used for the
                 * first element, after that this class totally delegates to the
                 * wrapped handler which is either XML or HTML.
                 * <p>
                 *  只是委托给包装的处理程序,但我们要将这些属性添加到m_attributes。所以我必须调用super。
                 *  addAttributes()在这种情况下,m_attributes只用于第一个元素,之后这个类完全委托给包装处理程序,它是XML或HTML。
                 * 
                 */
                if (atts != null)
                    super.addAttributes(atts);

                // if there are attributes, then lets make the flush()
                // call the startElement on the handler and send the
                // attributes on their way.
                if (atts != null)
                    flush();

            }
        }
        else
        {
            // this is not the first element, but a later one, so just
            // send it on its way.
            m_handler.startElement(namespaceURI, localName, elementName,  atts);
        }
    }

    /**
     * Pass the call on to the underlying handler
     * <p>
     *  将调用传递给底层处理程序
     * 
     * 
     * @see ExtendedLexicalHandler#comment(String)
     */
    public void comment(String comment) throws SAXException
    {
        if (m_firstTagNotEmitted && m_firstElementName != null)
        {
            emitFirstTag();
        }
        else if (m_needToCallStartDocument)
        {
            m_handler.startDocument();
            m_needToCallStartDocument = false;
        }

        m_handler.comment(comment);
    }

    /**
     * Pass the call on to the underlying handler
     * <p>
     *  将调用传递给底层处理程序
     * 
     * 
     * @see XSLOutputAttributes#getDoctypePublic()
     */
    public String getDoctypePublic()
    {

        return m_handler.getDoctypePublic();
    }

    /**
     * Pass the call on to the underlying handler
     * <p>
     *  将调用传递给底层处理程序
     * 
     * 
     * @see XSLOutputAttributes#getDoctypeSystem()
     */
    public String getDoctypeSystem()
    {
        return m_handler.getDoctypeSystem();
    }

    /**
     * Pass the call on to the underlying handler
     * <p>
     *  将调用传递给底层处理程序
     * 
     * 
     * @see XSLOutputAttributes#getEncoding()
     */
    public String getEncoding()
    {
        return m_handler.getEncoding();
    }

    /**
     * Pass the call on to the underlying handler
     * <p>
     *  将调用传递给底层处理程序
     * 
     * 
     * @see XSLOutputAttributes#getIndent()
     */
    public boolean getIndent()
    {
        return m_handler.getIndent();
    }

    /**
     * Pass the call on to the underlying handler
     * <p>
     *  将调用传递给底层处理程序
     * 
     * 
     * @see XSLOutputAttributes#getIndentAmount()
     */
    public int getIndentAmount()
    {
        return m_handler.getIndentAmount();
    }

    /**
     * Pass the call on to the underlying handler
     * <p>
     * 将调用传递给底层处理程序
     * 
     * 
     * @see XSLOutputAttributes#getMediaType()
     */
    public String getMediaType()
    {
        return m_handler.getMediaType();
    }

    /**
     * Pass the call on to the underlying handler
     * <p>
     *  将调用传递给底层处理程序
     * 
     * 
     * @see XSLOutputAttributes#getOmitXMLDeclaration()
     */
    public boolean getOmitXMLDeclaration()
    {
        return m_handler.getOmitXMLDeclaration();
    }

    /**
     * Pass the call on to the underlying handler
     * <p>
     *  将调用传递给底层处理程序
     * 
     * 
     * @see XSLOutputAttributes#getStandalone()
     */
    public String getStandalone()
    {
        return m_handler.getStandalone();
    }

    /**
     * Pass the call on to the underlying handler
     * <p>
     *  将调用传递给底层处理程序
     * 
     * 
     * @see XSLOutputAttributes#getVersion()
     */
    public String getVersion()
    {
        return m_handler.getVersion();
    }

    /**
    /* <p>
    /* 
     * @see XSLOutputAttributes#setDoctype(String, String)
     */
    public void setDoctype(String system, String pub)
    {
        m_handler.setDoctypePublic(pub);
        m_handler.setDoctypeSystem(system);
    }

    /**
     * Set the doctype in the underlying XML handler. Remember that this method
     * was called, just in case we need to transfer this doctype to an HTML handler
     * <p>
     *  在底层XML处理程序中设置doctype。记住这个方法被调用,只是为了防止我们需要将这个doctype传递给HTML处理程序
     * 
     * 
     * @param doctype the public doctype to set
     * @see XSLOutputAttributes#setDoctypePublic(String)
     */
    public void setDoctypePublic(String doctype)
    {
        m_handler.setDoctypePublic(doctype);
        m_setDoctypePublic_called = true;
    }

    /**
     * Set the doctype in the underlying XML handler. Remember that this method
     * was called, just in case we need to transfer this doctype to an HTML handler
     * <p>
     *  在底层XML处理程序中设置doctype。记住这个方法被调用,只是为了防止我们需要将这个doctype传递给HTML处理程序
     * 
     * 
     * @param doctype the system doctype to set
     * @see XSLOutputAttributes#setDoctypeSystem(String)
     */
    public void setDoctypeSystem(String doctype)
    {
        m_handler.setDoctypeSystem(doctype);
        m_setDoctypeSystem_called = true;
    }

    /**
     * Pass the call on to the underlying handler
     * <p>
     *  将调用传递给底层处理程序
     * 
     * 
     * @see XSLOutputAttributes#setEncoding(String)
     */
    public void setEncoding(String encoding)
    {
        m_handler.setEncoding(encoding);
    }

    /**
     * Pass the call on to the underlying handler
     * <p>
     *  将调用传递给底层处理程序
     * 
     * 
     * @see XSLOutputAttributes#setIndent(boolean)
     */
    public void setIndent(boolean indent)
    {
        m_handler.setIndent(indent);
    }

    /**
     * Pass the call on to the underlying handler
     * <p>
     *  将调用传递给底层处理程序
     * 
     */
    public void setIndentAmount(int value)
    {
        m_handler.setIndentAmount(value);
    }

    /**
    /* <p>
    /* 
     * @see XSLOutputAttributes#setMediaType(String)
     */
    public void setMediaType(String mediaType)
    {
        m_handler.setMediaType(mediaType);
        m_setMediaType_called = true;
    }

    /**
     * Pass the call on to the underlying handler
     * <p>
     *  将调用传递给底层处理程序
     * 
     * 
     * @see XSLOutputAttributes#setOmitXMLDeclaration(boolean)
     */
    public void setOmitXMLDeclaration(boolean b)
    {
        m_handler.setOmitXMLDeclaration(b);
    }

    /**
     * Pass the call on to the underlying handler
     * <p>
     *  将调用传递给底层处理程序
     * 
     * 
     * @see XSLOutputAttributes#setStandalone(String)
     */
    public void setStandalone(String standalone)
    {
        m_handler.setStandalone(standalone);
    }

    /**
    /* <p>
    /* 
     * @see XSLOutputAttributes#setVersion(String)
     */

    /**
     * Pass the call on to the underlying handler
     * <p>
     *  将调用传递给底层处理程序
     * 
     * 
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
        m_handler.attributeDecl(arg0, arg1, arg2, arg3, arg4);
    }

    /**
     * Pass the call on to the underlying handler
     * <p>
     *  将调用传递给底层处理程序
     * 
     * 
     * @see org.xml.sax.ext.DeclHandler#elementDecl(String, String)
     */
    public void elementDecl(String arg0, String arg1) throws SAXException
    {
        if (m_firstTagNotEmitted)
        {
            emitFirstTag();
        }
        m_handler.elementDecl(arg0, arg1);
    }

    /**
     * Pass the call on to the underlying handler
     * <p>
     *  将调用传递给底层处理程序
     * 
     * 
     * @see org.xml.sax.ext.DeclHandler#externalEntityDecl(String, String, String)
     */
    public void externalEntityDecl(
        String name,
        String publicId,
        String systemId)
        throws SAXException
    {
        if (m_firstTagNotEmitted)
        {
            flush();
        }
        m_handler.externalEntityDecl(name, publicId, systemId);
    }

    /**
     * Pass the call on to the underlying handler
     * <p>
     *  将调用传递给底层处理程序
     * 
     * 
     * @see org.xml.sax.ext.DeclHandler#internalEntityDecl(String, String)
     */
    public void internalEntityDecl(String arg0, String arg1)
        throws SAXException
    {
        if (m_firstTagNotEmitted)
        {
            flush();
        }
        m_handler.internalEntityDecl(arg0, arg1);
    }

    /**
     * Pass the call on to the underlying handler
     * <p>
     *  将调用传递给底层处理程序
     * 
     * 
     * @see org.xml.sax.ContentHandler#characters(char[], int, int)
     */
    public void characters(char[] characters, int offset, int length)
        throws SAXException
    {
        if (m_firstTagNotEmitted)
        {
            flush();
        }

        m_handler.characters(characters, offset, length);

    }

    /**
     * Pass the call on to the underlying handler
     * <p>
     *  将调用传递给底层处理程序
     * 
     * 
     * @see org.xml.sax.ContentHandler#endDocument()
     */
    public void endDocument() throws SAXException
    {
        if (m_firstTagNotEmitted)
        {
            flush();
        }

        m_handler.endDocument();


    }

    /**
     * Pass the call on to the underlying handler
     * <p>
     *  将调用传递给底层处理程序
     * 
     * 
     * @see org.xml.sax.ContentHandler#endElement(String, String, String)
     */
    public void endElement(String namespaceURI, String localName, String qName)
        throws SAXException
    {
        if (m_firstTagNotEmitted)
        {
            flush();
            if (namespaceURI == null && m_firstElementURI != null)
                namespaceURI = m_firstElementURI;


            if (localName == null && m_firstElementLocalName != null)
                localName = m_firstElementLocalName;
        }

        m_handler.endElement(namespaceURI, localName, qName);
    }

    /**
     * Pass the call on to the underlying handler
     * <p>
     *  将调用传递给底层处理程序
     * 
     * 
     * @see org.xml.sax.ContentHandler#endPrefixMapping(String)
     */
    public void endPrefixMapping(String prefix) throws SAXException
    {
        m_handler.endPrefixMapping(prefix);
    }

    /**
     * Pass the call on to the underlying handler
     * <p>
     *  将调用传递给底层处理程序
     * 
     * 
     * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
     */
    public void ignorableWhitespace(char[] ch, int start, int length)
        throws SAXException
    {
        if (m_firstTagNotEmitted)
        {
            flush();
        }
        m_handler.ignorableWhitespace(ch, start, length);
    }

    /**
     * Pass the call on to the underlying handler
     * <p>
     *  将调用传递给底层处理程序
     * 
     * 
     * @see org.xml.sax.ContentHandler#processingInstruction(String, String)
     */
    public void processingInstruction(String target, String data)
        throws SAXException
    {
          if (m_firstTagNotEmitted)
        {
            flush();
        }

        m_handler.processingInstruction(target, data);
    }

    /**
     * Pass the call on to the underlying handler
     * <p>
     *  将调用传递给底层处理程序
     * 
     * 
     * @see org.xml.sax.ContentHandler#setDocumentLocator(Locator)
     */
    public void setDocumentLocator(Locator locator)
    {
        super.setDocumentLocator(locator);
        m_handler.setDocumentLocator(locator);
    }

    /**
     * Pass the call on to the underlying handler
     * <p>
     *  将调用传递给底层处理程序
     * 
     * 
     * @see org.xml.sax.ContentHandler#skippedEntity(String)
     */
    public void skippedEntity(String name) throws SAXException
    {
        m_handler.skippedEntity(name);
    }



    /**
     * Pass the call on to the underlying handler
     * <p>
     *  将调用传递给底层处理程序
     * 
     * 
     * @see org.xml.sax.ext.LexicalHandler#comment(char[], int, int)
     */
    public void comment(char[] ch, int start, int length) throws SAXException
    {
        if (m_firstTagNotEmitted)
        {
            flush();
        }

        m_handler.comment(ch, start, length);
    }

    /**
     * Pass the call on to the underlying handler
     * <p>
     *  将调用传递给底层处理程序
     * 
     * 
     * @see org.xml.sax.ext.LexicalHandler#endCDATA()
     */
    public void endCDATA() throws SAXException
    {

        m_handler.endCDATA();
    }

    /**
     * Pass the call on to the underlying handler
     * <p>
     *  将调用传递给底层处理程序
     * 
     * 
     * @see org.xml.sax.ext.LexicalHandler#endDTD()
     */
    public void endDTD() throws SAXException
    {

        m_handler.endDTD();
    }

    /**
     * Pass the call on to the underlying handler
     * <p>
     *  将调用传递给底层处理程序
     * 
     * 
     * @see org.xml.sax.ext.LexicalHandler#endEntity(String)
     */
    public void endEntity(String name) throws SAXException
    {
        if (m_firstTagNotEmitted)
        {
            emitFirstTag();
        }
        m_handler.endEntity(name);
    }

    /**
     * Pass the call on to the underlying handler
     * <p>
     *  将调用传递给底层处理程序
     * 
     * 
     * @see org.xml.sax.ext.LexicalHandler#startCDATA()
     */
    public void startCDATA() throws SAXException
    {
        m_handler.startCDATA();
    }

    /**
     * Pass the call on to the underlying handler
     * <p>
     *  将调用传递给底层处理程序
     * 
     * 
     * @see org.xml.sax.ext.LexicalHandler#startDTD(String, String, String)
     */
    public void startDTD(String name, String publicId, String systemId)
        throws SAXException
    {
        m_handler.startDTD(name, publicId, systemId);
    }

    /**
     * Pass the call on to the underlying handler
     * <p>
     * 将调用传递给底层处理程序
     * 
     * 
     * @see org.xml.sax.ext.LexicalHandler#startEntity(String)
     */
    public void startEntity(String name) throws SAXException
    {
        m_handler.startEntity(name);
    }

    /**
     * Initialize the wrapped output stream (XML or HTML).
     * If the stream handler should be HTML, then replace the XML handler with
     * an HTML handler. After than send the starting method calls that were cached
     * to the wrapped handler.
     *
     * <p>
     *  初始化包装的输出流(XML或HTML)。如果流处理程序应该是HTML,则用HTML处理程序替换XML处理程序。之后发送启动方法调用被缓存到被包装的处理程序。
     * 
     */
    private void initStreamOutput() throws SAXException
    {

        // Try to rule out if this is an not to be an HTML document based on prefix
        boolean firstElementIsHTML = isFirstElemHTML();

        if (firstElementIsHTML)
        {
            // create an HTML output handler, and initialize it

            // keep a reference to the old handler, ... it will soon be gone
            SerializationHandler oldHandler = m_handler;

            /* We have to make sure we get an output properties with the proper
             * defaults for the HTML method.  The easiest way to do this is to
             * have the OutputProperties class do it.
             * <p>
             *  HTML方法的默认值。最简单的方法是使OutputProperties类实现它。
             * 
             */

            Properties htmlProperties =
                OutputPropertiesFactory.getDefaultMethodProperties(Method.HTML);
            Serializer serializer =
                SerializerFactory.getSerializer(htmlProperties);

            // The factory should be returning a ToStream
            // Don't know what to do if it doesn't
            // i.e. the user has over-ridden the content-handler property
            // for html
            m_handler = (SerializationHandler) serializer;
            //m_handler = new ToHTMLStream();

            Writer writer = oldHandler.getWriter();

            if (null != writer)
                m_handler.setWriter(writer);
            else
            {
                OutputStream os = oldHandler.getOutputStream();

                if (null != os)
                    m_handler.setOutputStream(os);
            }

            // need to copy things from the old handler to the new one here

            //            if (_setVersion_called)
            //            {
            m_handler.setVersion(oldHandler.getVersion());
            //            }
            //            if (_setDoctypeSystem_called)
            //            {
            m_handler.setDoctypeSystem(oldHandler.getDoctypeSystem());
            //            }
            //            if (_setDoctypePublic_called)
            //            {
            m_handler.setDoctypePublic(oldHandler.getDoctypePublic());
            //            }
            //            if (_setMediaType_called)
            //            {
            m_handler.setMediaType(oldHandler.getMediaType());
            //            }

            m_handler.setTransformer(oldHandler.getTransformer());
        }

        /* Now that we have a real wrapped handler (XML or HTML) lets
         * pass any cached calls to it
         * <p>
         *  将任何缓存的调用传递给它
         * 
         */
        // Call startDocument() if necessary
        if (m_needToCallStartDocument)
        {
            m_handler.startDocument();
            m_needToCallStartDocument = false;
        }

        // the wrapped handler is now fully initialized
        m_wrapped_handler_not_initialized = false;
    }

    private void emitFirstTag() throws SAXException
    {
        if (m_firstElementName != null)
        {
            if (m_wrapped_handler_not_initialized)
            {
                initStreamOutput();
                m_wrapped_handler_not_initialized = false;
            }
            // Output first tag
            m_handler.startElement(m_firstElementURI, null, m_firstElementName, m_attributes);
            // don't need the collected attributes of the first element anymore.
            m_attributes = null;

            // Output namespaces of first tag
            if (m_namespacePrefix != null)
            {
                final int n = m_namespacePrefix.size();
                for (int i = 0; i < n; i++)
                {
                    final String prefix =
                        (String) m_namespacePrefix.elementAt(i);
                    final String uri = (String) m_namespaceURI.elementAt(i);
                    m_handler.startPrefixMapping(prefix, uri, false);
                }
                m_namespacePrefix = null;
                m_namespaceURI = null;
            }
            m_firstTagNotEmitted = false;
        }
    }

    /**
     * Utility function for calls to local-name().
     *
     * Don't want to override static function on SerializerBase
     * So added Unknown suffix to method name.
     * <p>
     *  用于对local-name()的调用的实用程序函数。
     * 
     *  不想覆盖SerializerBase上的静态函数所以向方法名添加了未知后缀。
     * 
     */
    private String getLocalNameUnknown(String value)
    {
        int idx = value.lastIndexOf(':');
        if (idx >= 0)
            value = value.substring(idx + 1);
        idx = value.lastIndexOf('@');
        if (idx >= 0)
            value = value.substring(idx + 1);
        return (value);
    }

    /**
         * Utility function to return prefix
         *
         * Don't want to override static function on SerializerBase
         * So added Unknown suffix to method name.
         * <p>
         *  效用函数返回前缀
         * 
         *  不想覆盖SerializerBase上的静态函数所以向方法名添加了未知后缀。
         * 
         */
    private String getPrefixPartUnknown(String qname)
    {
        final int index = qname.indexOf(':');
        return (index > 0) ? qname.substring(0, index) : EMPTYSTRING;
    }

    /**
     * Determine if the firts element in the document is <html> or <HTML>
     * This uses the cached first element name, first element prefix and the
     * cached namespaces from previous method calls
     *
     * <p>
     *  确定文档中的firts元素是<html>还是<HTML>这使用缓存的第一个元素名称,第一个元素前缀和来自以前的方法调用的缓存名称空间
     * 
     * 
     * @return true if the first element is an opening <html> tag
     */
    private boolean isFirstElemHTML()
    {
        boolean isHTML;

        // is the first tag html, not considering the prefix ?
        isHTML =
            getLocalNameUnknown(m_firstElementName).equalsIgnoreCase("html");

        // Try to rule out if this is not to be an HTML document based on URI
        if (isHTML
            && m_firstElementURI != null
            && !EMPTYSTRING.equals(m_firstElementURI))
        {
            // the <html> element has a non-trivial namespace
            isHTML = false;
        }
        // Try to rule out if this is an not to be an HTML document based on prefix
        if (isHTML && m_namespacePrefix != null)
        {
            /* the first element has a name of "html", but lets check the prefix.
             * If the prefix points to a namespace with a URL that is not ""
             * then the doecument doesn't start with an <html> tag, and isn't html
             * <p>
             *  如果前缀指向具有不是""的URL的命名空间,则该doecument不以<html>标签开始,并且不是html
             * 
             */
            final int max = m_namespacePrefix.size();
            for (int i = 0; i < max; i++)
            {
                final String prefix = (String) m_namespacePrefix.elementAt(i);
                final String uri = (String) m_namespaceURI.elementAt(i);

                if (m_firstElementPrefix != null
                    && m_firstElementPrefix.equals(prefix)
                    && !EMPTYSTRING.equals(uri))
                {
                    // The first element has a prefix, so it can't be <html>
                    isHTML = false;
                    break;
                }
            }

        }
        return isHTML;
    }
    /**
    /* <p>
    /* 
     * @see Serializer#asDOMSerializer()
     */
    public DOMSerializer asDOMSerializer() throws IOException
    {
        return m_handler.asDOMSerializer();
    }

    /**
    /* <p>
    /* 
     * @param URI_and_localNames Vector a list of pairs of URI/localName
     * specified in the cdata-section-elements attribute.
     * @see SerializationHandler#setCdataSectionElements(java.util.Vector)
     */
    public void setCdataSectionElements(Vector URI_and_localNames)
    {
        m_handler.setCdataSectionElements(URI_and_localNames);
    }
    /**
    /* <p>
    /* 
     * @see ExtendedContentHandler#addAttributes(org.xml.sax.Attributes)
     */
    public void addAttributes(Attributes atts) throws SAXException
    {
        m_handler.addAttributes(atts);
    }

    /**
     * Get the current namespace mappings.
     * Simply returns the mappings of the wrapped handler.
     * <p>
     *  获取当前的命名空间映射。简单地返回包装的处理程序的映射。
     * 
     * 
     * @see ExtendedContentHandler#getNamespaceMappings()
     */
    public NamespaceMappings getNamespaceMappings()
    {
        NamespaceMappings mappings = null;
        if (m_handler != null)
        {
            mappings = m_handler.getNamespaceMappings();
        }
        return mappings;
    }
    /**
    /* <p>
    /* 
     * @see SerializationHandler#flushPending()
     */
    public void flushPending() throws SAXException
    {

        flush();

        m_handler.flushPending();
    }

    private void flush()
    {
        try
        {
        if (m_firstTagNotEmitted)
        {
            emitFirstTag();
        }
        if (m_needToCallStartDocument)
        {
            m_handler.startDocument();
            m_needToCallStartDocument = false;
        }
        }
        catch(SAXException e)
        {
            throw new RuntimeException(e.toString());
        }


    }

    /**
    /* <p>
    /* 
     * @see ExtendedContentHandler#getPrefix
     */
    public String getPrefix(String namespaceURI)
    {
        return m_handler.getPrefix(namespaceURI);
    }
    /**
    /* <p>
    /* 
     * @see ExtendedContentHandler#entityReference(java.lang.String)
     */
    public void entityReference(String entityName) throws SAXException
    {
        m_handler.entityReference(entityName);
    }

    /**
    /* <p>
    /* 
     * @see ExtendedContentHandler#getNamespaceURI(java.lang.String, boolean)
     */
    public String getNamespaceURI(String qname, boolean isElement)
    {
        return m_handler.getNamespaceURI(qname, isElement);
    }

    public String getNamespaceURIFromPrefix(String prefix)
    {
        return m_handler.getNamespaceURIFromPrefix(prefix);
    }

    public void setTransformer(Transformer t)
    {
        m_handler.setTransformer(t);
        if ((t instanceof SerializerTrace) &&
            (((SerializerTrace) t).hasTraceListeners())) {
           m_tracer = (SerializerTrace) t;
        } else {
           m_tracer = null;
        }
    }
    public Transformer getTransformer()
    {
        return m_handler.getTransformer();
    }

    /**
    /* <p>
    /* 
     * @see SerializationHandler#setContentHandler(org.xml.sax.ContentHandler)
     */
    public void setContentHandler(ContentHandler ch)
    {
        m_handler.setContentHandler(ch);
    }
    /**
     * This method is used to set the source locator, which might be used to
     * generated an error message.
     * <p>
     *  此方法用于设置源定位符,可能用于生成错误消息。
     * 
     * @param locator the source locator
     *
     * @see ExtendedContentHandler#setSourceLocator(javax.xml.transform.SourceLocator)
     */
    public void setSourceLocator(SourceLocator locator)
    {
        m_handler.setSourceLocator(locator);
    }

    protected void firePseudoElement(String elementName)
    {

        if (m_tracer != null) {
            StringBuffer sb = new StringBuffer();

            sb.append('<');
            sb.append(elementName);

            // convert the StringBuffer to a char array and
            // emit the trace event that these characters "might"
            // be written
            char ch[] = sb.toString().toCharArray();
            m_tracer.fireGenerateEvent(
                SerializerTrace.EVENTTYPE_OUTPUT_PSEUDO_CHARACTERS,
                ch,
                0,
                ch.length);
        }
    }
}
