/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2005, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

// SAX document handler.
// http://www.saxproject.org
// No warranty; no copyright -- use this as you will.
// $Id: DocumentHandler.java,v 1.2 2004/11/03 22:44:51 jsuttor Exp $

package org.xml.sax;

/**
 * Receive notification of general document events.
 *
 * <blockquote>
 * <em>This module, both source code and documentation, is in the
 * Public Domain, and comes with <strong>NO WARRANTY</strong>.</em>
 * See <a href='http://www.saxproject.org'>http://www.saxproject.org</a>
 * for further information.
 * </blockquote>
 *
 * <p>This was the main event-handling interface for SAX1; in
 * SAX2, it has been replaced by {@link org.xml.sax.ContentHandler
 * ContentHandler}, which provides Namespace support and reporting
 * of skipped entities.  This interface is included in SAX2 only
 * to support legacy SAX1 applications.</p>
 *
 * <p>The order of events in this interface is very important, and
 * mirrors the order of information in the document itself.  For
 * example, all of an element's content (character data, processing
 * instructions, and/or subelements) will appear, in order, between
 * the startElement event and the corresponding endElement event.</p>
 *
 * <p>Application writers who do not want to implement the entire
 * interface can derive a class from HandlerBase, which implements
 * the default functionality; parser writers can instantiate
 * HandlerBase to obtain a default handler.  The application can find
 * the location of any document event using the Locator interface
 * supplied by the Parser through the setDocumentLocator method.</p>
 *
 * <p>
 *  接收一般文件事件的通知。
 * 
 * <blockquote>
 *  <em>此模块(源代码和文档)都位于公共域中,并且随附<strong>无保证</strong>。
 * </em>请参阅<a href ='http：//www.saxproject.org '> http://www.saxproject.org </a>了解更多信息。
 * </blockquote>
 * 
 *  <p>这是SAX1的主要事件处理接口;在SAX2中,它被{@link org.xml.sax.ContentHandler ContentHandler}替代,它提供了命名空间支持和报告跳过的实体。
 * 此接口包含在SAX2中,仅用于支持旧版SAX1应用程序。</p>。
 * 
 *  <p>此界面中的事件顺序非常重要,并且反映文档本身中的信息顺序。例如,元素的所有内容(字符数据,处理指令和/或子元素)将按顺序出现在startElement事件和相应的endElement事件之间。
 * </p>。
 * 
 *  <p>不想实现整个接口的应用程序编写者可以从HandlerBase派生一个类,该类实现默认功能;解析器写程序可以实例化HandlerBase以获取默认处理程序。
 * 应用程序可以使用由Parser通过setDocumentLocator方法提供的定位器接口找到任何文档事件的位置。</p>。
 * 
 * 
 * @deprecated This interface has been replaced by the SAX2
 *             {@link org.xml.sax.ContentHandler ContentHandler}
 *             interface, which includes Namespace support.
 * @since SAX 1.0
 * @author David Megginson
 * @see org.xml.sax.Parser#setDocumentHandler
 * @see org.xml.sax.Locator
 * @see org.xml.sax.HandlerBase
 */
public interface DocumentHandler {


    /**
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
     *
     * <p>
     *  接收用于查找SAX文档事件的原点的对象。
     * 
     * <p>强烈鼓励(虽然不是绝对需要)SAX解析器提供一个定位器：如果它这样做,它必须通过调用这个方法提供定位器到应用程序,然后调用DocumentHandler接口中的任何其他方法。 p>
     * 
     *  <p>定位器允许应用程序确定任何文档相关事件的结束位置,即使解析器未报告错误。通常,应用程序将使用此信息来报告其自身的错误(例如,字符内容与应用程序的业务规则不匹配)。
     * 定位器返回的信息可能不足以用于搜索引擎。</p>。
     * 
     *  <p>请注意,只有在此界面中调用事件时,定位器才会返回正确的信息。该应用程序不应尝试在任何其他时间使用它。</p>
     * 
     * 
     * @param locator An object that can return the location of
     *                any SAX document event.
     * @see org.xml.sax.Locator
     */
    public abstract void setDocumentLocator (Locator locator);


    /**
     * Receive notification of the beginning of a document.
     *
     * <p>The SAX parser will invoke this method only once, before any
     * other methods in this interface or in DTDHandler (except for
     * setDocumentLocator).</p>
     *
     * <p>
     *  接收文档开头的通知。
     * 
     *  <p> SAX解析器只会在此接口或DTDHandler中的任何其他方法(setDocumentLocator除外)之前调用此方法一次。</p>
     * 
     * 
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     */
    public abstract void startDocument ()
        throws SAXException;


    /**
     * Receive notification of the end of a document.
     *
     * <p>The SAX parser will invoke this method only once, and it will
     * be the last method invoked during the parse.  The parser shall
     * not invoke this method until it has either abandoned parsing
     * (because of an unrecoverable error) or reached the end of
     * input.</p>
     *
     * <p>
     *  接收文档结束的通知。
     * 
     *  <p> SAX解析器将仅调用此方法一次,它将是解析期间调用的最后一个方法。解析器不应该调用此方法,直到它放弃了解析(因为一个不可恢复的错误)或到达输入的结束。</p>
     * 
     * 
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     */
    public abstract void endDocument ()
        throws SAXException;


    /**
     * Receive notification of the beginning of an element.
     *
     * <p>The Parser will invoke this method at the beginning of every
     * element in the XML document; there will be a corresponding
     * endElement() event for every startElement() event (even when the
     * element is empty). All of the element's content will be
     * reported, in order, before the corresponding endElement()
     * event.</p>
     *
     * <p>If the element name has a namespace prefix, the prefix will
     * still be attached.  Note that the attribute list provided will
     * contain only attributes with explicit values (specified or
     * defaulted): #IMPLIED attributes will be omitted.</p>
     *
     * <p>
     *  接收元素开头的通知。
     * 
     * <p>解析器将在XML文档中的每个元素的开头调用此方法;每个startElement()事件都会有一个相应的endElement()事件(即使元素为空)。
     * 将在相应的endElement()事件之前按顺序报告所有元素的内容。</p>。
     * 
     *  <p>如果元素名称有命名空间前缀,则前缀仍将被附加。请注意,提供的属性列表将仅包含具有显式值(指定或默认值)的属性：#IMPLIED属性将被省略。</p>
     * 
     * 
     * @param name The element type name.
     * @param atts The attributes attached to the element, if any.
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     * @see #endElement
     * @see org.xml.sax.AttributeList
     */
    public abstract void startElement (String name, AttributeList atts)
        throws SAXException;


    /**
     * Receive notification of the end of an element.
     *
     * <p>The SAX parser will invoke this method at the end of every
     * element in the XML document; there will be a corresponding
     * startElement() event for every endElement() event (even when the
     * element is empty).</p>
     *
     * <p>If the element name has a namespace prefix, the prefix will
     * still be attached to the name.</p>
     *
     * <p>
     *  接收元素结束的通知。
     * 
     *  <p> SAX解析器将在XML文档中每个元素的末尾调用此方法;每个endElement()事件都会有一个相应的startElement()事件(即使元素为空)。</p>
     * 
     *  <p>如果元素名称有名称空间前缀,则前缀仍会附加到名称。</p>
     * 
     * 
     * @param name The element type name
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     */
    public abstract void endElement (String name)
        throws SAXException;


    /**
     * Receive notification of character data.
     *
     * <p>The Parser will call this method to report each chunk of
     * character data.  SAX parsers may return all contiguous character
     * data in a single chunk, or they may split it into several
     * chunks; however, all of the characters in any single event
     * must come from the same external entity, so that the Locator
     * provides useful information.</p>
     *
     * <p>The application must not attempt to read from the array
     * outside of the specified range.</p>
     *
     * <p>Note that some parsers will report whitespace using the
     * ignorableWhitespace() method rather than this one (validating
     * parsers must do so).</p>
     *
     * <p>
     *  接收字符数据的通知。
     * 
     *  <p>解析器将调用此方法来报告每个字符数据块。
     *  SAX解析器可以返回单个块中的所有连续字符数据,或者它们可以将其拆分成几个块;然而,任何单个事件中的所有字符必须来自同一外部实体,以便定位器提供有用的信息。</p>。
     * 
     *  <p>应用程序不得尝试从指定范围之外的数组读取。</p>
     * 
     * <p>请注意,一些解析器将使用ignorableWhitespace()方法而不是这一个(验证解析器必须这样做)报告空格。</p>
     * 
     * 
     * @param ch The characters from the XML document.
     * @param start The start position in the array.
     * @param length The number of characters to read from the array.
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     * @see #ignorableWhitespace
     * @see org.xml.sax.Locator
     */
    public abstract void characters (char ch[], int start, int length)
        throws SAXException;


    /**
     * Receive notification of ignorable whitespace in element content.
     *
     * <p>Validating Parsers must use this method to report each chunk
     * of ignorable whitespace (see the W3C XML 1.0 recommendation,
     * section 2.10): non-validating parsers may also use this method
     * if they are capable of parsing and using content models.</p>
     *
     * <p>SAX parsers may return all contiguous whitespace in a single
     * chunk, or they may split it into several chunks; however, all of
     * the characters in any single event must come from the same
     * external entity, so that the Locator provides useful
     * information.</p>
     *
     * <p>The application must not attempt to read from the array
     * outside of the specified range.</p>
     *
     * <p>
     *  在元素内容中接收可忽略的空格的通知。
     * 
     *  <p>验证Parsers必须使用此方法来报告每个可忽略的空格(请参阅W3C XML 1.0建议,第2.10节)：如果非验证解析器能够解析和使用内容模型,那么它们也可以使用此方法。 p>
     * 
     *  <p> SAX解析器可以返回单个块中的所有连续空格,或者它们可以将其拆分成几个块;然而,任何单个事件中的所有字符必须来自同一外部实体,以便定位器提供有用的信息。</p>
     * 
     *  <p>应用程序不得尝试从指定范围之外的数组读取。</p>
     * 
     * 
     * @param ch The characters from the XML document.
     * @param start The start position in the array.
     * @param length The number of characters to read from the array.
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     * @see #characters
     */
    public abstract void ignorableWhitespace (char ch[], int start, int length)
        throws SAXException;


    /**
     * Receive notification of a processing instruction.
     *
     * <p>The Parser will invoke this method once for each processing
     * instruction found: note that processing instructions may occur
     * before or after the main document element.</p>
     *
     * <p>A SAX parser should never report an XML declaration (XML 1.0,
     * section 2.8) or a text declaration (XML 1.0, section 4.3.1)
     * using this method.</p>
     *
     * <p>
     * 
     * @param target The processing instruction target.
     * @param data The processing instruction data, or null if
     *        none was supplied.
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     */
    public abstract void processingInstruction (String target, String data)
        throws SAXException;

}

// end of DocumentHandler.java
