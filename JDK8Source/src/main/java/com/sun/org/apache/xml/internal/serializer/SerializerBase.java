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
 * $Id: SerializerBase.java,v 1.5 2006/04/14 12:09:19 sunithareddy Exp $
 * <p>
 *  $ Id：SerializerBase.java,v 1.5 2006/04/14 12:09:19 sunithareddy Exp $
 * 
 */
package com.sun.org.apache.xml.internal.serializer;

import java.io.IOException;
import java.util.Vector;

import javax.xml.transform.SourceLocator;
import javax.xml.transform.Transformer;

import com.sun.org.apache.xml.internal.serializer.utils.MsgKey;
import com.sun.org.apache.xml.internal.serializer.utils.Utils;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ext.Locator2;


/**
 * This class acts as a base class for the XML "serializers"
 * and the stream serializers.
 * It contains a number of common fields and methods.
 *
 * @xsl.usage internal
 * <p>
 *  这个类充当XML"serializers"和流串行器的基类。它包含许多常见的字段和方法。
 * 
 *  @ xsl.usage internal
 * 
 */
public abstract class SerializerBase
    implements SerializationHandler, SerializerConstants
{


    /**
     * To fire off the end element trace event
     * <p>
     *  触发结束元素跟踪事件
     * 
     * 
     * @param name Name of element
     */
    protected void fireEndElem(String name)
        throws org.xml.sax.SAXException
    {
        if (m_tracer != null)
        {
            flushMyWriter();
            m_tracer.fireGenerateEvent(SerializerTrace.EVENTTYPE_ENDELEMENT,name, (Attributes)null);
        }
    }

    /**
     * Report the characters trace event
     * <p>
     *  报告字符跟踪事件
     * 
     * 
     * @param chars  content of characters
     * @param start  starting index of characters to output
     * @param length  number of characters to output
     */
    protected void fireCharEvent(char[] chars, int start, int length)
        throws org.xml.sax.SAXException
    {
        if (m_tracer != null)
        {
            flushMyWriter();
            m_tracer.fireGenerateEvent(SerializerTrace.EVENTTYPE_CHARACTERS, chars, start,length);
        }
    }

    /**
     * true if we still need to call startDocumentInternal()
     * <p>
     *  true如果我们仍然需要调用startDocumentInternal()
     * 
         */
    protected boolean m_needToCallStartDocument = true;

    /** True if a trailing "]]>" still needs to be written to be
     * written out. Used to merge adjacent CDATA sections
     * <p>
     *  写出来。用于合并相邻的CDATA节
     * 
     */
    protected boolean m_cdataTagOpen = false;

    /**
     * All the attributes of the current element, collected from
     * startPrefixMapping() calls, or addAddtribute() calls, or
     * from the SAX attributes in a startElement() call.
     * <p>
     *  当前元素的所有属性,从startPrefixMapping()调用或addAddtribute()调用收集,或者从startElement()调用中的SAX属性收集。
     * 
     */
    protected AttributesImplSerializer m_attributes = new AttributesImplSerializer();

    /**
     * Tells if we're in an EntityRef event.
     * <p>
     *  告诉我们是否在EntityRef事件中。
     * 
     */
    protected boolean m_inEntityRef = false;

    /** This flag is set while receiving events from the external DTD */
    protected boolean m_inExternalDTD = false;

    /**
     * The System ID for the doc type.
     * <p>
     *  文档类型的系统ID。
     * 
     */
    private String m_doctypeSystem;

    /**
     * The public ID for the doc type.
     * <p>
     *  文档类型的公共ID。
     * 
     */
    private String m_doctypePublic;

    /**
     * Flag to tell that we need to add the doctype decl, which we can't do
     * until the first element is encountered.
     * <p>
     * 标志告诉我们需要添加doctype声明,我们不能做,直到遇到第一个元素。
     * 
     */
    boolean m_needToOutputDocTypeDecl = true;

    /**
     * The character encoding.  Must match the encoding used for the
     * printWriter.
     * <p>
     *  字符编码。必须与printWriter使用的编码相匹配。
     * 
     */
    private String m_encoding = null;

    /**
     * Tells if we should write the XML declaration.
     * <p>
     *  告诉我们是否应该写XML声明。
     * 
     */
    private boolean m_shouldNotWriteXMLHeader = false;

    /**
     * The standalone value for the doctype.
     * <p>
     *  doctype的独立值。
     * 
     */
    private String m_standalone;

    /**
     * True if standalone was specified.
     * <p>
     *  如果指定了独立,则为True。
     * 
     */
    protected boolean m_standaloneWasSpecified = false;

    /**
     * Determine if the output is a standalone.
     * <p>
     *  确定输出是否是独立的。
     * 
     */
    protected boolean m_isStandalone = false;

    /**
     * Flag to tell if indenting (pretty-printing) is on.
     * <p>
     *  标志,如果缩进(漂亮打印)是否打开。
     * 
     */
    protected boolean m_doIndent = false;
    /**
     * Amount to indent.
     * <p>
     *  缩进量。
     * 
     */
    protected int m_indentAmount = 0;

    /**
     * Tells the XML version, for writing out to the XML decl.
     * <p>
     *  告诉XML版本,写出XML声明。
     * 
     */
    private String m_version = null;

    /**
     * The mediatype.  Not used right now.
     * <p>
     *  媒体类型。目前不使用。
     * 
     */
    private String m_mediatype;

    /**
     * The transformer that was around when this output handler was created (if
     * any).
     * <p>
     *  创建此输出处理程序时(如果有)的变压器。
     * 
     */
    private Transformer m_transformer;

    /**
     * Pairs of local names and corresponding URIs of CDATA sections. This list
     * comes from the cdata-section-elements attribute. Every second one is a
     * local name, and every other second one is the URI for the local name.
     * <p>
     *  CDAT段的本地名称和相应的URI对。此列表来自cdata-section-elements属性。每隔一个是一个本地名称,每隔一个第二个是本地名称的URI。
     * 
     */
    protected Vector m_cdataSectionElements = null;

    /**
     * Namespace support, that keeps track of currently defined
     * prefix/uri mappings. As processed elements come and go, so do
     * the associated mappings for that element.
     * <p>
     *  命名空间支持,跟踪当前定义的前缀/ uri映射。由于处理的元素来来去去,所以对于该元素的相关映射也是如此。
     * 
     */
    protected NamespaceMappings m_prefixMap;

    /**
     * Handle for firing generate events.  This interface may be implemented
     * by the referenced transformer object.
     * <p>
     *  用于触发的句柄生成事件。该接口可以由引用的变换器对象实现。
     * 
     */
    protected SerializerTrace m_tracer;

    protected SourceLocator m_sourceLocator;


    /**
     * The writer to send output to. This field is only used in the ToStream
     * serializers, but exists here just so that the fireStartDoc() and
     * other fire... methods can flush this writer when tracing.
     * <p>
     *  发送输出的作者。此字段仅用于ToStream序列化程序,但在此处仅存在,以便fireStartDoc()和其他fire ...方法可以在跟踪时刷新此写入程序。
     * 
     */
    protected java.io.Writer m_writer = null;

    /**
     * A reference to "stack frame" corresponding to
     * the current element. Such a frame is pushed at a startElement()
     * and popped at an endElement(). This frame contains information about
     * the element, such as its namespace URI.
     * <p>
     *  对应于当前元素的"堆栈帧"的引用。这样的框架在startElement()处被推送并且在endElement()处被弹出。此框架包含有关元素的信息,例如其名称空间URI。
     * 
     */
    protected ElemContext m_elemContext = new ElemContext();

    /**
     * A utility buffer for converting Strings passed to
     * character() methods to character arrays.
     * Reusing this buffer means not creating a new character array
     * everytime and it runs faster.
     * <p>
     * 用于将传递给character()方法的字符串转换为字符数组的实用程序缓冲区。重用此缓冲区意味着不会每次都创建一个新的字符数组,并且运行速度更快。
     * 
     */
    protected char[] m_charsBuff = new char[60];

    /**
     * A utility buffer for converting Strings passed to
     * attribute methods to character arrays.
     * Reusing this buffer means not creating a new character array
     * everytime and it runs faster.
     * <p>
     *  用于将传递给属性方法的字符串转换为字符数组的实用程序缓冲区。重用此缓冲区意味着不会每次都创建一个新的字符数组,并且运行速度更快。
     * 
     */
    protected char[] m_attrBuff = new char[30];

    private Locator m_locator = null;

    protected boolean m_needToCallSetDocumentInfo = true;

    /**
     * Receive notification of a comment.
     *
     * <p>
     *  接收评论通知。
     * 
     * 
     * @see ExtendedLexicalHandler#comment(String)
     */
    public void comment(String data) throws SAXException
    {
        final int length = data.length();
        if (length > m_charsBuff.length)
        {
            m_charsBuff = new char[length * 2 + 1];
        }
        data.getChars(0, length, m_charsBuff, 0);
        comment(m_charsBuff, 0, length);
    }

    /**
     * If at runtime, when the qname of the attribute is
     * known, another prefix is specified for the attribute, then we can
     * patch or hack the name with this method. For
     * a qname of the form "ns?:otherprefix:name", this function patches the
     * qname by simply ignoring "otherprefix".
     * TODO: This method is a HACK! We do not have access to the
     * XML file, it sometimes generates a NS prefix of the form "ns?" for
     * an attribute.
     * <p>
     *  如果在运行时,当属性的qname已知时,为属性指定另一个前缀,那么我们可以使用此方法修补或破解名称。
     * 对于形式为"ns?：otherprefix：name"的qname,此函数通过简单忽略"otherprefix"来修补qname。
     *  TODO：这个方法是一个HACK！我们没有访问XML文件,它有时会生成一个NS前缀的形式"ns?属性。
     * 
     */
    protected String patchName(String qname)
    {


        final int lastColon = qname.lastIndexOf(':');

        if (lastColon > 0) {
            final int firstColon = qname.indexOf(':');
            final String prefix = qname.substring(0, firstColon);
            final String localName = qname.substring(lastColon + 1);

        // If uri is "" then ignore prefix
            final String uri = m_prefixMap.lookupNamespace(prefix);
            if (uri != null && uri.length() == 0) {
                return localName;
            }
            else if (firstColon != lastColon) {
                return prefix + ':' + localName;
            }
        }
        return qname;
    }

    /**
     * Returns the local name of a qualified name. If the name has no prefix,
     * then it works as the identity (SAX2).
     * <p>
     *  返回限定名称的本地名称。如果名称没有前缀,则它用作身份(SAX2)。
     * 
     * 
     * @param qname the qualified name
     * @return the name, but excluding any prefix and colon.
     */
    protected static String getLocalName(String qname)
    {
        final int col = qname.lastIndexOf(':');
        return (col > 0) ? qname.substring(col + 1) : qname;
    }

    /**
     * Receive an object for locating the origin of SAX document events.
     *
     * <p>
     *  接收用于查找SAX文档事件的原点的对象。
     * 
     * 
     * @param locator An object that can return the location of any SAX document
     * event.
     *
     * Receive an object for locating the origin of SAX document events.
     *
     * <p>SAX parsers are strongly encouraged (though not absolutely
     * required) to supply a locator: if it does so, it must supply
     * the locator to the application by invoking this method before
     * invoking any of the other methods in the DocumentHandler
     * interface.</p>
     *
     * <p>The locator allows the application to determine the end
     * position of any document-related event, even if the parser is
     * not reporting an error.  Typically, the application will
     * use this information for reporting its own errors (such as
     * character content that does not match an application's
     * business rules).  The information returned by the locator
     * is probably not sufficient for use with a search engine.</p>
     *
     * <p>Note that the locator will return correct information only
     * during the invocation of the events in this interface.  The
     * application should not attempt to use it at any other time.</p>
     */
    public void setDocumentLocator(Locator locator)
    {
        m_locator = locator;
    }

    /**
     * Adds the given attribute to the set of collected attributes , but only if
     * there is a currently open element.
     *
     * An element is currently open if a startElement() notification has
     * occured but the start of the element has not yet been written to the
     * output.  In the stream case this means that we have not yet been forced
     * to close the elements opening tag by another notification, such as a
     * character notification.
     *
     * <p>
     *  将给定的属性添加到收集的属性集中,但仅当存在当前打开的元素时。
     * 
     *  如果发生startElement()通知,但元素的开头尚未写入输出,则元素当前处于打开状态。在流的情况下,这意味着我们还没有被迫通过另一个通知(如字符通知)关闭元素开始标记。
     * 
     * 
     * @param uri the URI of the attribute
     * @param localName the local name of the attribute
     * @param rawName    the qualified name of the attribute
     * @param type the type of the attribute (probably CDATA)
     * @param value the value of the attribute
     * @param XSLAttribute true if this attribute is coming from an xsl:attriute element
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
            addAttributeAlways(uri, localName, rawName, type, value, XSLAttribute);
        }

    }

    /**
     * Adds the given attribute to the set of attributes, even if there is
     * no currently open element. This is useful if a SAX startPrefixMapping()
     * should need to add an attribute before the element name is seen.
     *
     * <p>
     * 将给定属性添加到属性集,即使没有当前打开的元素。如果SAX startPrefixMapping()需要在看到元素名称之前添加属性,这将非常有用。
     * 
     * 
     * @param uri the URI of the attribute
     * @param localName the local name of the attribute
     * @param rawName   the qualified name of the attribute
     * @param type the type of the attribute (probably CDATA)
     * @param value the value of the attribute
     * @param XSLAttribute true if this attribute is coming from an xsl:attribute element
     * @return true if the attribute was added,
     * false if an existing value was replaced.
     */
    public boolean addAttributeAlways(
        String uri,
        String localName,
        String rawName,
        String type,
        String value,
        boolean XSLAttribute)
    {
        boolean was_added;
//            final int index =
//                (localName == null || uri == null) ?
//                m_attributes.getIndex(rawName):m_attributes.getIndex(uri, localName);
            int index;
//            if (localName == null || uri == null){
//                index = m_attributes.getIndex(rawName);
//            }
//            else {
//                index = m_attributes.getIndex(uri, localName);
//            }

            if (localName == null || uri == null || uri.length() == 0)
                index = m_attributes.getIndex(rawName);
            else {
                index = m_attributes.getIndex(uri,localName);
            }
            if (index >= 0)
            {
                /* We've seen the attribute before.
                 * We may have a null uri or localName, but all
                 * we really want to re-set is the value anyway.
                 * <p>
                 *  我们可能有一个空uri或localName,但我们真正想重新设置的是值。
                 * 
                 */
                m_attributes.setValue(index,value);
                was_added = false;
            }
            else
            {
                // the attribute doesn't exist yet, create it
                m_attributes.addAttribute(uri, localName, rawName, type, value);
                was_added = true;
            }
            return was_added;

    }


    /**
     *  Adds  the given attribute to the set of collected attributes,
     * but only if there is a currently open element.
     *
     * <p>
     *  将给定的属性添加到收集的属性集中,但仅当存在当前打开的元素时。
     * 
     * 
     * @param name the attribute's qualified name
     * @param value the value of the attribute
     */
    public void addAttribute(String name, final String value)
    {
        if (m_elemContext.m_startTagOpen)
        {
            final String patchedName = patchName(name);
            final String localName = getLocalName(patchedName);
            final String uri = getNamespaceURI(patchedName, false);

            addAttributeAlways(uri,localName, patchedName, "CDATA", value, false);
         }
    }

    /**
     * Adds the given xsl:attribute to the set of collected attributes,
     * but only if there is a currently open element.
     *
     * <p>
     *  将给定的xsl：属性添加到收集的属性集,但仅当存在当前打开的元素时。
     * 
     * 
     * @param name the attribute's qualified name (prefix:localName)
     * @param value the value of the attribute
     * @param uri the URI that the prefix of the name points to
     */
    public void addXSLAttribute(String name, final String value, final String uri)
    {
        if (m_elemContext.m_startTagOpen)
        {
            final String patchedName = patchName(name);
            final String localName = getLocalName(patchedName);

            addAttributeAlways(uri,localName, patchedName, "CDATA", value, true);
         }
    }

    /**
     * Add the given attributes to the currently collected ones. These
     * attributes are always added, regardless of whether on not an element
     * is currently open.
     * <p>
     *  将给定的属性添加到当前收集的属性。无论是否当前未打开元素,都始终添加这些属性。
     * 
     * 
     * @param atts List of attributes to add to this list
     */
    public void addAttributes(Attributes atts) throws SAXException
    {

        int nAtts = atts.getLength();
        for (int i = 0; i < nAtts; i++)
        {
            String uri = atts.getURI(i);

            if (null == uri)
                uri = "";

            addAttributeAlways(
                uri,
                atts.getLocalName(i),
                atts.getQName(i),
                atts.getType(i),
                atts.getValue(i),
                false);

        }
    }

    /**
     * Return a {@link ContentHandler} interface into this serializer.
     * If the serializer does not support the {@link ContentHandler}
     * interface, it should return null.
     *
     * <p>
     *  将{@link ContentHandler}接口返回到此序列化程序。如果序列化器不支持{@link ContentHandler}接口,它应该返回null。
     * 
     * 
     * @return A {@link ContentHandler} interface into this serializer,
     *  or null if the serializer is not SAX 2 capable
     * @throws IOException An I/O exception occured
     */
    public ContentHandler asContentHandler() throws IOException
    {
        return this;
    }

    /**
     * Report the end of an entity.
     *
     * <p>
     *  报告实体的结束。
     * 
     * 
     * @param name The name of the entity that is ending.
     * @throws org.xml.sax.SAXException The application may raise an exception.
     * @see #startEntity
     */
    public void endEntity(String name) throws org.xml.sax.SAXException
    {
        if (name.equals("[dtd]"))
            m_inExternalDTD = false;
        m_inEntityRef = false;

        if (m_tracer != null)
            this.fireEndEntity(name);
    }

    /**
     * Flush and close the underlying java.io.Writer. This method applies to
     * ToStream serializers, not ToSAXHandler serializers.
     * <p>
     *  刷新并关闭底层的java.io.Writer。此方法适用于ToStream序列化程序,而不适用于ToSAXHandler序列化程序。
     * 
     * 
     * @see ToStream
     */
    public void close()
    {
        // do nothing (base behavior)
    }

    /**
     * Initialize global variables
     * <p>
     *  初始化全局变量
     * 
     */
    protected void initCDATA()
    {
        // CDATA stack
        //        _cdataStack = new Stack();
        //        _cdataStack.push(new Integer(-1)); // push dummy value
    }

    /**
     * Returns the character encoding to be used in the output document.
     * <p>
     *  返回要在输出文档中使用的字符编码。
     * 
     * 
     * @return the character encoding to be used in the output document.
     */
    public String getEncoding()
    {
        return m_encoding;
    }

   /**
     * Sets the character encoding coming from the xsl:output encoding stylesheet attribute.
     * <p>
     *  设置来自xsl：output编码样式表属性的字符编码。
     * 
     * 
     * @param m_encoding the character encoding
     */
    public void setEncoding(String m_encoding)
    {
        this.m_encoding = m_encoding;
    }

    /**
     * Sets the value coming from the xsl:output omit-xml-declaration stylesheet attribute
     * <p>
     *  设置来自xsl：output omit-xml-declaration stylesheet属性的值
     * 
     * 
     * @param b true if the XML declaration is to be omitted from the output
     * document.
     */
    public void setOmitXMLDeclaration(boolean b)
    {
        this.m_shouldNotWriteXMLHeader = b;
    }


    /**
    /* <p>
    /* 
     * @return true if the XML declaration is to be omitted from the output
     * document.
     */
    public boolean getOmitXMLDeclaration()
    {
        return m_shouldNotWriteXMLHeader;
    }

    /**
     * Returns the previously set value of the value to be used as the public
     * identifier in the document type declaration (DTD).
     *
     * <p>
     *  返回在文档类型声明(DTD)中用作公共标识符的值的先前设置值。
     * 
     * 
     *@return the public identifier to be used in the DOCTYPE declaration in the
     * output document.
     */
    public String getDoctypePublic()
    {
        return m_doctypePublic;
    }

    /** Set the value coming from the xsl:output doctype-public stylesheet attribute.
    /* <p>
    /* 
      * @param doctypePublic the public identifier to be used in the DOCTYPE
      * declaration in the output document.
      */
    public void setDoctypePublic(String doctypePublic)
    {
        this.m_doctypePublic = doctypePublic;
    }


    /**
     * Returns the previously set value of the value to be used
     * as the system identifier in the document type declaration (DTD).
     * <p>
     * 返回在文档类型声明(DTD)中用作系统标识符的值的先前设置值。
     * 
     * 
         * @return the system identifier to be used in the DOCTYPE declaration in
         * the output document.
     *
     */
    public String getDoctypeSystem()
    {
        return m_doctypeSystem;
    }

    /** Set the value coming from the xsl:output doctype-system stylesheet attribute.
    /* <p>
    /* 
      * @param doctypeSystem the system identifier to be used in the DOCTYPE
      * declaration in the output document.
      */
    public void setDoctypeSystem(String doctypeSystem)
    {
        this.m_doctypeSystem = doctypeSystem;
    }

    /** Set the value coming from the xsl:output doctype-public and doctype-system stylesheet properties
    /* <p>
    /* 
     * @param doctypeSystem the system identifier to be used in the DOCTYPE
     * declaration in the output document.
     * @param doctypePublic the public identifier to be used in the DOCTYPE
     * declaration in the output document.
     */
    public void setDoctype(String doctypeSystem, String doctypePublic)
    {
        this.m_doctypeSystem = doctypeSystem;
        this.m_doctypePublic = doctypePublic;
    }

    /**
     * Sets the value coming from the xsl:output standalone stylesheet attribute.
     * <p>
     *  设置来自xsl：output standalone stylesheet属性的值。
     * 
     * 
     * @param standalone a value of "yes" indicates that the
     * <code>standalone</code> delaration is to be included in the output
     * document. This method remembers if the value was explicitly set using
     * this method, verses if the value is the default value.
     */
    public void setStandalone(String standalone)
    {
        if (standalone != null)
        {
            m_standaloneWasSpecified = true;
            setStandaloneInternal(standalone);
        }
    }
    /**
     * Sets the XSL standalone attribute, but does not remember if this is a
     * default or explicite setting.
     * <p>
     *  设置XSL独立属性,但不记得这是一个默认设置还是显式设置。
     * 
     * 
     * @param standalone "yes" | "no"
     */
    protected void setStandaloneInternal(String standalone)
    {
        if ("yes".equals(standalone))
            m_standalone = "yes";
        else
            m_standalone = "no";

    }

    /**
     * Gets the XSL standalone attribute
     * <p>
     *  获取XSL独立属性
     * 
     * 
     * @return a value of "yes" if the <code>standalone</code> delaration is to
     * be included in the output document.
     *  @see XSLOutputAttributes#getStandalone()
     */
    public String getStandalone()
    {
        return m_standalone;
    }

    /**
    /* <p>
    /* 
     * @return true if the output document should be indented to visually
     * indicate its structure.
     */
    public boolean getIndent()
    {
        return m_doIndent;
    }
    /**
     * Gets the mediatype the media-type or MIME type associated with the output
     * document.
     * <p>
     *  获取mediatype与输出文档关联的媒体类型或MIME类型。
     * 
     * 
     * @return the mediatype the media-type or MIME type associated with the
     * output document.
     */
    public String getMediaType()
    {
        return m_mediatype;
    }

    /**
     * Gets the version of the output format.
     * <p>
     *  获取输出格式的版本。
     * 
     * 
     * @return the version of the output format.
     */
    public String getVersion()
    {
        return m_version;
    }

    /**
     * Sets the value coming from the xsl:output version attribute.
     * <p>
     *  设置来自xsl：output version属性的值。
     * 
     * 
     * @param version the version of the output format.
     * @see SerializationHandler#setVersion(String)
     */
    public void setVersion(String version)
    {
        m_version = version;
    }

    /**
     * Sets the value coming from the xsl:output media-type stylesheet attribute.
     * <p>
     *  设置来自xsl：output media-type stylesheet属性的值。
     * 
     * 
     * @param mediaType the non-null media-type or MIME type associated with the
     * output document.
     * @see javax.xml.transform.OutputKeys#MEDIA_TYPE
     * @see SerializationHandler#setMediaType(String)
     */
    public void setMediaType(String mediaType)
    {
        m_mediatype = mediaType;
    }

    /**
    /* <p>
    /* 
     * @return the number of spaces to indent for each indentation level.
     */
    public int getIndentAmount()
    {
        return m_indentAmount;
    }

    /**
     * Sets the indentation amount.
     * <p>
     *  设置缩进量。
     * 
     * 
     * @param m_indentAmount The m_indentAmount to set
     */
    public void setIndentAmount(int m_indentAmount)
    {
        this.m_indentAmount = m_indentAmount;
    }

    /**
     * Sets the value coming from the xsl:output indent stylesheet
     * attribute.
     * <p>
     *  设置来自xsl：output indent stylesheet属性的值。
     * 
     * 
     * @param doIndent true if the output document should be indented to
     * visually indicate its structure.
     * @see XSLOutputAttributes#setIndent(boolean)
     */
    public void setIndent(boolean doIndent)
    {
        m_doIndent = doIndent;
    }

    /**
     * Sets the isStandalone property
     * <p>
     *  设置isStandalone属性
     * 
     * 
     * @param isStandalone true if the ORACLE_IS_STANDALONE is set to yes
     * @see OutputPropertiesFactory ORACLE_IS_STANDALONE
     */
    public void setIsStandalone(boolean isStandalone)
    {
       m_isStandalone = isStandalone;
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
    public void namespaceAfterStartElement(String uri, String prefix)
        throws SAXException
    {
        // default behavior is to do nothing
    }

    /**
     * Return a {@link DOMSerializer} interface into this serializer. If the
     * serializer does not support the {@link DOMSerializer} interface, it should
     * return null.
     *
     * <p>
     *  将{@link DOMSerializer}接口返回到此序列化程序中。如果序列化器不支持{@link DOMSerializer}接口,它应该返回null。
     * 
     * 
     * @return A {@link DOMSerializer} interface into this serializer,  or null
     * if the serializer is not DOM capable
     * @throws IOException An I/O exception occured
     * @see Serializer#asDOMSerializer()
     */
    public DOMSerializer asDOMSerializer() throws IOException
    {
        return this;
    }

    /**
     * Push a boolean state based on if the name of the current element
     * is found in the list of qnames.  A state is only pushed if
     * there were some cdata-section-names were specified.
     * <p>
     * Hidden parameters are the vector of qualified elements specified in
     * cdata-section-names attribute, and the m_cdataSectionStates stack
     * onto which whether the current element is in the list is pushed (true or
     * false). Other hidden parameters are the current elements namespaceURI,
     * localName and qName
     * <p>
     *  基于是否在qnames列表中找到当前元素的名称来推送布尔状态。只有在指定了一些cdata-section-name时才推送状态。
     * <p>
     * 隐藏的参数是cdata-section-names属性中指定的限定元素的向量,以及m_cdataSectionStates栈,当前元素是否在列表中是推送(true或false)。
     * 其他隐藏参数是当前元素的namespaceURI,localName和qName。
     * 
     */
    protected boolean isCdataSection()
    {

        boolean b = false;

        if (null != m_cdataSectionElements)
        {
            if (m_elemContext.m_elementLocalName == null)
                m_elemContext.m_elementLocalName =
                    getLocalName(m_elemContext.m_elementName);
            if (m_elemContext.m_elementURI == null)
            {
                String prefix = getPrefixPart(m_elemContext.m_elementName);
                if (prefix != null)
                    m_elemContext.m_elementURI =
                        m_prefixMap.lookupNamespace(prefix);

            }

            if ((null != m_elemContext.m_elementURI)
                && m_elemContext.m_elementURI.length() == 0)
                m_elemContext.m_elementURI = null;

            int nElems = m_cdataSectionElements.size();

            // loop through 2 at a time, as these are pairs of URI and localName
            for (int i = 0; i < nElems; i += 2)
            {
                String uri = (String) m_cdataSectionElements.elementAt(i);
                String loc = (String) m_cdataSectionElements.elementAt(i + 1);
                if (loc.equals(m_elemContext.m_elementLocalName)
                    && subPartMatch(m_elemContext.m_elementURI, uri))
                {
                    b = true;

                    break;
                }
            }
        }
        return b;
    }

    /**
     * Tell if two strings are equal, without worry if the first string is null.
     *
     * <p>
     *  判断两个字符串是否相等,如果第一个字符串为null,则不必担心。
     * 
     * 
     * @param p String reference, which may be null.
     * @param t String reference, which may be null.
     *
     * @return true if strings are equal.
     */
    private static final boolean subPartMatch(String p, String t)
    {
        return (p == t) || ((null != p) && (p.equals(t)));
    }

    /**
     * Returns the local name of a qualified name.
     * If the name has no prefix,
     * then it works as the identity (SAX2).
     *
     * <p>
     *  返回限定名称的本地名称。如果名称没有前缀,则它用作身份(SAX2)。
     * 
     * 
     * @param qname a qualified name
     * @return returns the prefix of the qualified name,
     * or null if there is no prefix.
     */
    protected static final String getPrefixPart(String qname)
    {
        final int col = qname.indexOf(':');
        return (col > 0) ? qname.substring(0, col) : null;
        //return (col > 0) ? qname.substring(0,col) : "";
    }

    /**
     * Some users of the serializer may need the current namespace mappings
     * <p>
     *  序列化程序的某些用户可能需要当前的命名空间映射
     * 
     * 
     * @return the current namespace mappings (prefix/uri)
     * @see ExtendedContentHandler#getNamespaceMappings()
     */
    public NamespaceMappings getNamespaceMappings()
    {
        return m_prefixMap;
    }

    /**
     * Returns the prefix currently pointing to the given URI (if any).
     * <p>
     *  返回当前指向给定URI的前缀(如果有)。
     * 
     * 
     * @param namespaceURI the uri of the namespace in question
     * @return a prefix pointing to the given URI (if any).
     * @see ExtendedContentHandler#getPrefix(String)
     */
    public String getPrefix(String namespaceURI)
    {
        String prefix = m_prefixMap.lookupPrefix(namespaceURI);
        return prefix;
    }

    /**
     * Returns the URI of an element or attribute. Note that default namespaces
     * do not apply directly to attributes.
     * <p>
     *  返回元素或属性的URI。请注意,默认名称空间不直接应用于属性。
     * 
     * 
     * @param qname a qualified name
     * @param isElement true if the qualified name is the name of
     * an element.
     * @return returns the namespace URI associated with the qualified name.
     */
    public String getNamespaceURI(String qname, boolean isElement)
    {
        String uri = EMPTYSTRING;
        int col = qname.lastIndexOf(':');
        final String prefix = (col > 0) ? qname.substring(0, col) : EMPTYSTRING;

        if (!EMPTYSTRING.equals(prefix) || isElement)
        {
            if (m_prefixMap != null)
            {
                uri = m_prefixMap.lookupNamespace(prefix);
                if (uri == null && !prefix.equals(XMLNS_PREFIX))
                {
                    throw new RuntimeException(
                        Utils.messages.createMessage(
                            MsgKey.ER_NAMESPACE_PREFIX,
                            new Object[] { qname.substring(0, col) }  ));
                }
            }
        }
        return uri;
    }

    /**
     * Returns the URI of prefix (if any)
     *
     * <p>
     *  返回前缀的URI(如果有)
     * 
     * 
         * @param prefix the prefix whose URI is searched for
     * @return the namespace URI currently associated with the
     * prefix, null if the prefix is undefined.
     */
    public String getNamespaceURIFromPrefix(String prefix)
    {
        String uri = null;
        if (m_prefixMap != null)
            uri = m_prefixMap.lookupNamespace(prefix);
        return uri;
    }

    /**
     * Entity reference event.
     *
     * <p>
     *  实体引用事件。
     * 
     * 
     * @param name Name of entity
     *
     * @throws org.xml.sax.SAXException
     */
    public void entityReference(String name) throws org.xml.sax.SAXException
    {

        flushPending();

        startEntity(name);
        endEntity(name);

        if (m_tracer != null)
                    fireEntityReference(name);
    }

    /**
     * Sets the transformer associated with this serializer
     * <p>
     *  设置与此序列化器关联的变压器
     * 
     * 
     * @param t the transformer associated with this serializer.
     * @see SerializationHandler#setTransformer(Transformer)
     */
    public void setTransformer(Transformer t)
    {
        m_transformer = t;

        // If this transformer object implements the SerializerTrace interface
        // then assign m_tracer to the transformer object so it can be used
        // to fire trace events.
        if ((m_transformer instanceof SerializerTrace) &&
            (((SerializerTrace) m_transformer).hasTraceListeners())) {
           m_tracer = (SerializerTrace) m_transformer;
        } else {
           m_tracer = null;
        }
    }
    /**
     * Gets the transformer associated with this serializer
     * <p>
     *  获取与此序列化器相关联的变压器
     * 
     * 
     * @return returns the transformer associated with this serializer.
     * @see SerializationHandler#getTransformer()
     */
    public Transformer getTransformer()
    {
        return m_transformer;
    }

    /**
     * This method gets the nodes value as a String and uses that String as if
     * it were an input character notification.
     * <p>
     *  此方法将节点值作为字符串获取,并将该字符串用作输入字符通知。
     * 
     * 
     * @param node the Node to serialize
     * @throws org.xml.sax.SAXException
     */
    public void characters(org.w3c.dom.Node node)
        throws org.xml.sax.SAXException
    {
        flushPending();
        String data = node.getNodeValue();
        if (data != null)
        {
            final int length = data.length();
            if (length > m_charsBuff.length)
            {
                m_charsBuff = new char[length * 2 + 1];
            }
            data.getChars(0, length, m_charsBuff, 0);
            characters(m_charsBuff, 0, length);
        }
    }


    /**
    /* <p>
    /* 
     * @see org.xml.sax.ErrorHandler#error(SAXParseException)
     */
    public void error(SAXParseException exc) throws SAXException {
    }

    /**
    /* <p>
    /* 
     * @see org.xml.sax.ErrorHandler#fatalError(SAXParseException)
     */
    public void fatalError(SAXParseException exc) throws SAXException {

      m_elemContext.m_startTagOpen = false;

    }

    /**
    /* <p>
    /* 
     * @see org.xml.sax.ErrorHandler#warning(SAXParseException)
     */
    public void warning(SAXParseException exc) throws SAXException
    {
    }

    /**
     * To fire off start entity trace event
     * <p>
     *  触发启动实体跟踪事件
     * 
     * 
     * @param name Name of entity
     */
    protected void fireStartEntity(String name)
        throws org.xml.sax.SAXException
    {
        if (m_tracer != null)
        {
            flushMyWriter();
            m_tracer.fireGenerateEvent(SerializerTrace.EVENTTYPE_ENTITYREF, name);
        }
    }

    /**
     * Report the characters event
     * <p>
     *  报告字符事件
     * 
     * 
     * @param chars  content of characters
     * @param start  starting index of characters to output
     * @param length  number of characters to output
     */
//    protected void fireCharEvent(char[] chars, int start, int length)
//        throws org.xml.sax.SAXException
//    {
//        if (m_tracer != null)
//            m_tracer.fireGenerateEvent(SerializerTrace.EVENTTYPE_CHARACTERS, chars, start,length);
//    }
//

    /**
     * This method is only used internally when flushing the writer from the
     * various fire...() trace events.  Due to the writer being wrapped with
     * SerializerTraceWriter it may cause the flush of these trace events:
     * EVENTTYPE_OUTPUT_PSEUDO_CHARACTERS
     * EVENTTYPE_OUTPUT_CHARACTERS
     * which trace the output written to the output stream.
     *
     * <p>
     *  此方法仅在从各种fire ...()跟踪事件刷新写入程序时在内部使用。
     * 由于写程序被包装在SerializerTraceWriter中,它可能导致这些跟踪事件的刷新：EVENTTYPE_OUTPUT_PSEUDO_CHARACTERS EVENTTYPE_OUTPUT_CH
     * ARACTERS跟踪写入输出流的输出。
     *  此方法仅在从各种fire ...()跟踪事件刷新写入程序时在内部使用。
     * 
     */
    private void flushMyWriter()
    {
        if (m_writer != null)
        {
            try
            {
                m_writer.flush();
            }
            catch(IOException ioe)
            {

            }
        }
    }
    /**
     * Report the CDATA trace event
     * <p>
     *  报告CDATA跟踪事件
     * 
     * 
     * @param chars  content of CDATA
     * @param start  starting index of characters to output
     * @param length  number of characters to output
     */
    protected void fireCDATAEvent(char[] chars, int start, int length)
        throws org.xml.sax.SAXException
    {
                if (m_tracer != null)
        {
            flushMyWriter();
                        m_tracer.fireGenerateEvent(SerializerTrace.EVENTTYPE_CDATA, chars, start,length);
        }
    }

    /**
     * Report the comment trace event
     * <p>
     *  报告注释跟踪事件
     * 
     * 
     * @param chars  content of comment
     * @param start  starting index of comment to output
     * @param length  number of characters to output
     */
    protected void fireCommentEvent(char[] chars, int start, int length)
        throws org.xml.sax.SAXException
    {
                if (m_tracer != null)
        {
            flushMyWriter();
                        m_tracer.fireGenerateEvent(SerializerTrace.EVENTTYPE_COMMENT, new String(chars, start, length));
        }
    }


    /**
     * To fire off end entity trace event
     * <p>
     * 触发结束实体跟踪事件
     * 
     * 
     * @param name Name of entity
     */
    public void fireEndEntity(String name)
        throws org.xml.sax.SAXException
    {
        if (m_tracer != null)
            flushMyWriter();
        // we do not need to handle this.
    }

    /**
     * To fire off start document trace  event
     * <p>
     *  触发启动文档跟踪事件
     * 
     */
     protected void fireStartDoc()
        throws org.xml.sax.SAXException
    {
        if (m_tracer != null)
        {
            flushMyWriter();
            m_tracer.fireGenerateEvent(SerializerTrace.EVENTTYPE_STARTDOCUMENT);
        }
    }


    /**
     * To fire off end document trace event
     * <p>
     *  触发结束文档跟踪事件
     * 
     */
    protected void fireEndDoc()
        throws org.xml.sax.SAXException
    {
        if (m_tracer != null)
        {
            flushMyWriter();
            m_tracer.fireGenerateEvent(SerializerTrace.EVENTTYPE_ENDDOCUMENT);
        }
    }

    /**
     * Report the start element trace event. This trace method needs to be
     * called just before the attributes are cleared.
     *
     * <p>
     *  报告启动元素跟踪事件。此跟踪方法需要在属性清除之前调用。
     * 
     * 
     * @param elemName the qualified name of the element
     *
     */
    protected void fireStartElem(String elemName)
        throws org.xml.sax.SAXException
    {
        if (m_tracer != null)
        {
            flushMyWriter();
            m_tracer.fireGenerateEvent(SerializerTrace.EVENTTYPE_STARTELEMENT,
                elemName, m_attributes);
        }
    }


    /**
     * To fire off the end element event
     * <p>
     *  触发结束元素事件
     * 
     * 
     * @param name Name of element
     */
//    protected void fireEndElem(String name)
//        throws org.xml.sax.SAXException
//    {
//        if (m_tracer != null)
//            m_tracer.fireGenerateEvent(SerializerTrace.EVENTTYPE_ENDELEMENT,name, (Attributes)null);
//    }


    /**
     * To fire off the PI trace event
     * <p>
     *  触发PI跟踪事件
     * 
     * 
     * @param name Name of PI
     */
    protected void fireEscapingEvent(String name, String data)
        throws org.xml.sax.SAXException
    {

        if (m_tracer != null)
        {
            flushMyWriter();
            m_tracer.fireGenerateEvent(SerializerTrace.EVENTTYPE_PI,name, data);
        }
    }


    /**
     * To fire off the entity reference trace event
     * <p>
     *  触发实体引用跟踪事件
     * 
     * 
     * @param name Name of entity reference
     */
    protected void fireEntityReference(String name)
        throws org.xml.sax.SAXException
    {
        if (m_tracer != null)
        {
            flushMyWriter();
            m_tracer.fireGenerateEvent(SerializerTrace.EVENTTYPE_ENTITYREF,name, (Attributes)null);
        }
    }

    /**
     * Receive notification of the beginning of a document.
     * This method is never a self generated call,
     * but only called externally.
     *
     * <p>The SAX parser will invoke this method only once, before any
     * other methods in this interface or in DTDHandler (except for
     * setDocumentLocator).</p>
     *
     * <p>
     *  接收文档开头的通知。此方法从不是自生成调用,而是仅在外部调用。
     * 
     *  <p> SAX解析器只会在此接口或DTDHandler中的任何其他方法(setDocumentLocator除外)之前调用此方法一次。</p>
     * 
     * 
     * @throws org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     *
     * @throws org.xml.sax.SAXException
     */
    public void startDocument() throws org.xml.sax.SAXException
    {

        // if we do get called with startDocument(), handle it right away
        startDocumentInternal();
        m_needToCallStartDocument = false;
        return;
    }

    /**
     * This method handles what needs to be done at a startDocument() call,
     * whether from an external caller, or internally called in the
     * serializer.  For historical reasons the serializer is flexible to
     * startDocument() not always being called.
     * Even if no external call is
     * made into startDocument() this method will always be called as a self
     * generated internal startDocument, it handles what needs to be done at a
     * startDocument() call.
     *
     * This method exists just to make sure that startDocument() is only ever
     * called from an external caller, which in principle is just a matter of
     * style.
     *
     * <p>
     *  此方法处理在startDocument()调用时需要完成的操作,无论是从外部调用者调用还是在串行器中调用。由于历史原因,序列化器是灵活的startDocument()不总是被调用。
     * 即使没有对startDocument()进行外部调用,该方法将始终作为自生成的内部startDocument调用,它处理在startDocument()调用中需要完成的操作。
     * 
     *  这个方法的存在只是为了确保startDocument()只能从外部调用者调用,这在原则上只是一个风格问题。
     * 
     * 
     * @throws SAXException
     */
    protected void startDocumentInternal() throws org.xml.sax.SAXException
    {
        if (m_tracer != null)
            this.fireStartDoc();

    }

    /* This method extracts version and encoding information from SAX events.
    /* <p>
     */
    protected void setDocumentInfo() {
        if (m_locator == null)
                return;
        try{
            String strVersion = ((Locator2)m_locator).getXMLVersion();
            if (strVersion != null)
                setVersion(strVersion);
            /*String strEncoding = ((Locator2)m_locator).getEncoding();
            if (strEncoding != null)
            /* <p>
            /*  如果(strEncoding！= null)
            /* 
            /* 
                setEncoding(strEncoding); */

        }catch(ClassCastException e){}
    }

    /**
     * This method is used to set the source locator, which might be used to
     * generated an error message.
     * <p>
     *  此方法用于设置源定位符,可能用于生成错误消息。
     * 
     * 
     * @param locator the source locator
     *
     * @see ExtendedContentHandler#setSourceLocator(javax.xml.transform.SourceLocator)
     */
    public void setSourceLocator(SourceLocator locator)
    {
        m_sourceLocator = locator;
    }


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
    public void setNamespaceMappings(NamespaceMappings mappings) {
        m_prefixMap = mappings;
    }

    public boolean reset()
    {
        resetSerializerBase();
        return true;
    }

    /**
     * Reset all of the fields owned by SerializerBase
     *
     * <p>
     *  重置SerializerBase拥有的所有字段
     * 
     */
    private void resetSerializerBase()
    {
        this.m_attributes.clear();
        this.m_cdataSectionElements = null;
        this.m_elemContext = new ElemContext();
        this.m_doctypePublic = null;
        this.m_doctypeSystem = null;
        this.m_doIndent = false;
        this.m_encoding = null;
        this.m_indentAmount = 0;
        this.m_inEntityRef = false;
        this.m_inExternalDTD = false;
        this.m_mediatype = null;
        this.m_needToCallStartDocument = true;
        this.m_needToOutputDocTypeDecl = false;
        if (this.m_prefixMap != null)
            this.m_prefixMap.reset();
        this.m_shouldNotWriteXMLHeader = false;
        this.m_sourceLocator = null;
        this.m_standalone = null;
        this.m_standaloneWasSpecified = false;
        this.m_tracer = null;
        this.m_transformer = null;
        this.m_version = null;
        // don't set writer to null, so that it might be re-used
        //this.m_writer = null;
    }

    /**
     * Returns true if the serializer is used for temporary output rather than
     * final output.
     *
     * This concept is made clear in the XSLT 2.0 draft.
     * <p>
     * 如果序列化器用于临时输出而不是最终输出,则返回true。
     * 
     *  这个概念在XSLT 2.0草案中明确。
     * 
     */
    final boolean inTemporaryOutputState()
    {
        /* This is a hack. We should really be letting the serializer know
         * that it is in temporary output state with an explicit call, but
         * from a pragmatic point of view (for now anyways) having no output
         * encoding at all, not even the default UTF-8 indicates that the serializer
         * is being used for temporary RTF.
         * <p>
         *  它处于具有显式调用的临时输出状态,但是从实际的角度来看(根本没有输出编码),甚至缺省的UTF-8指示序列化器用于临时RTF。
         * 
         */
        return (getEncoding() == null);

    }

    /**
     * This method adds an attribute the the current element,
     * but should not be used for an xsl:attribute child.
     * <p>
     *  此方法向当前元素添加属性,但不应用于xsl：attribute子项。
     * 
     * 
     * @see ExtendedContentHandler#addAttribute(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public void addAttribute(String uri, String localName, String rawName, String type, String value) throws SAXException
    {
        if (m_elemContext.m_startTagOpen)
        {
            addAttributeAlways(uri, localName, rawName, type, value, false);
        }
    }

    /**
    /* <p>
    /* 
     * @see org.xml.sax.DTDHandler#notationDecl(java.lang.String, java.lang.String, java.lang.String)
     */
    public void notationDecl(String arg0, String arg1, String arg2)
        throws SAXException {
        // This method just provides a definition to satisfy the interface
        // A particular sub-class of SerializerBase provides the implementation (if desired)
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
        // This method just provides a definition to satisfy the interface
        // A particular sub-class of SerializerBase provides the implementation (if desired)
    }

    /**
     * If set to false the serializer does not expand DTD entities,
     * but leaves them as is, the default value is true.
     * <p>
     *  如果设置为false,则序列化器不会展开DTD实体,但保留原样,默认值为true。
     */
    public void setDTDEntityExpansion(boolean expand) {
        // This method just provides a definition to satisfy the interface
        // A particular sub-class of SerializerBase provides the implementation (if desired)
    }

}
