/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2006, Oracle and/or its affiliates. All rights reserved.
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

// DefaultHandler.java - default implementation of the core handlers.
// http://www.saxproject.org
// Written by David Megginson
// NO WARRANTY!  This class is in the public domain.
// $Id: DefaultHandler.java,v 1.3 2006/04/13 02:06:32 jeffsuttor Exp $

package org.xml.sax.helpers;

import java.io.IOException;

import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.Attributes;
import org.xml.sax.EntityResolver;
import org.xml.sax.DTDHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


/**
 * Default base class for SAX2 event handlers.
 *
 * <blockquote>
 * <em>This module, both source code and documentation, is in the
 * Public Domain, and comes with <strong>NO WARRANTY</strong>.</em>
 * See <a href='http://www.saxproject.org'>http://www.saxproject.org</a>
 * for further information.
 * </blockquote>
 *
 * <p>This class is available as a convenience base class for SAX2
 * applications: it provides default implementations for all of the
 * callbacks in the four core SAX2 handler classes:</p>
 *
 * <ul>
 * <li>{@link org.xml.sax.EntityResolver EntityResolver}</li>
 * <li>{@link org.xml.sax.DTDHandler DTDHandler}</li>
 * <li>{@link org.xml.sax.ContentHandler ContentHandler}</li>
 * <li>{@link org.xml.sax.ErrorHandler ErrorHandler}</li>
 * </ul>
 *
 * <p>Application writers can extend this class when they need to
 * implement only part of an interface; parser writers can
 * instantiate this class to provide default handlers when the
 * application has not supplied its own.</p>
 *
 * <p>This class replaces the deprecated SAX1
 * {@link org.xml.sax.HandlerBase HandlerBase} class.</p>
 *
 * <p>
 *  SAX2事件处理程序的默认基类。
 * 
 * <blockquote>
 *  <em>此模块(源代码和文档)都位于公共域中,并且随附<strong>无保证</strong>。
 * </em>请参阅<a href ='http：//www.saxproject.org '> http://www.saxproject.org </a>了解更多信息。
 * </blockquote>
 * 
 *  <p>此类可用作SAX2应用程序的便利基类：它为四个核心SAX2处理程序类中的所有回调提供默认实现：</p>
 * 
 * <ul>
 *  <li> {@ link org.xml.sax.EntityResolver EntityResolver} </li> <li> {@ link org.xml.sax.DTDHandler DTDHandler}
 *  </li> <li> {@ link org.xml.sax。
 *  ContentHandler ContentHandler} </li> <li> {@ link org.xml.sax.ErrorHandlerErrorHandler} </li>。
 * </ul>
 * 
 *  <p>应用程序编写者可以在需要仅实现部分接口时扩展此类;解析器写入程序可以实例化此类,以在应用程序未提供自己的处理程序时提供默认处理程序。</p>
 * 
 *  <p>此类取代了已弃用的SAX1 {@link org.xml.sax.HandlerBase HandlerBase}类。</p>
 * 
 * 
 * @since SAX 2.0
 * @author David Megginson,
 * @see org.xml.sax.EntityResolver
 * @see org.xml.sax.DTDHandler
 * @see org.xml.sax.ContentHandler
 * @see org.xml.sax.ErrorHandler
 */
public class DefaultHandler
    implements EntityResolver, DTDHandler, ContentHandler, ErrorHandler
{


    ////////////////////////////////////////////////////////////////////
    // Default implementation of the EntityResolver interface.
    ////////////////////////////////////////////////////////////////////

    /**
     * Resolve an external entity.
     *
     * <p>Always return null, so that the parser will use the system
     * identifier provided in the XML document.  This method implements
     * the SAX default behaviour: application writers can override it
     * in a subclass to do special translations such as catalog lookups
     * or URI redirection.</p>
     *
     * <p>
     *  解析外部实体。
     * 
     *  <p>始终返回null,以便解析器将使用XML文档中提供的系统标识符。此方法实现SAX默认行为：应用程序编写者可以在子类中覆盖它以执行特殊翻译,例如目录查找或URI重定向。</p>
     * 
     * 
     * @param publicId The public identifier, or null if none is
     *                 available.
     * @param systemId The system identifier provided in the XML
     *                 document.
     * @return The new input source, or null to require the
     *         default behaviour.
     * @exception java.io.IOException If there is an error setting
     *            up the new input source.
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     * @see org.xml.sax.EntityResolver#resolveEntity
     */
    public InputSource resolveEntity (String publicId, String systemId)
        throws IOException, SAXException
    {
        return null;
    }



    ////////////////////////////////////////////////////////////////////
    // Default implementation of DTDHandler interface.
    ////////////////////////////////////////////////////////////////////


    /**
     * Receive notification of a notation declaration.
     *
     * <p>By default, do nothing.  Application writers may override this
     * method in a subclass if they wish to keep track of the notations
     * declared in a document.</p>
     *
     * <p>
     *  接收符号声明的通知。
     * 
     * <p>默认情况下,不执行任何操作。如果希望跟踪文档中声明的符号,应用程序编写器可以在子类中覆盖此方法。</p>
     * 
     * 
     * @param name The notation name.
     * @param publicId The notation public identifier, or null if not
     *                 available.
     * @param systemId The notation system identifier.
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     * @see org.xml.sax.DTDHandler#notationDecl
     */
    public void notationDecl (String name, String publicId, String systemId)
        throws SAXException
    {
        // no op
    }


    /**
     * Receive notification of an unparsed entity declaration.
     *
     * <p>By default, do nothing.  Application writers may override this
     * method in a subclass to keep track of the unparsed entities
     * declared in a document.</p>
     *
     * <p>
     *  接收未分析实体声明的通知。
     * 
     *  <p>默认情况下,不执行任何操作。应用程序编写器可以在子类中覆盖此方法,以跟踪文档中声明的未解析实体。</p>
     * 
     * 
     * @param name The entity name.
     * @param publicId The entity public identifier, or null if not
     *                 available.
     * @param systemId The entity system identifier.
     * @param notationName The name of the associated notation.
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     * @see org.xml.sax.DTDHandler#unparsedEntityDecl
     */
    public void unparsedEntityDecl (String name, String publicId,
                                    String systemId, String notationName)
        throws SAXException
    {
        // no op
    }



    ////////////////////////////////////////////////////////////////////
    // Default implementation of ContentHandler interface.
    ////////////////////////////////////////////////////////////////////


    /**
     * Receive a Locator object for document events.
     *
     * <p>By default, do nothing.  Application writers may override this
     * method in a subclass if they wish to store the locator for use
     * with other document events.</p>
     *
     * <p>
     *  接收文档事件的定位器对象。
     * 
     *  <p>默认情况下,不执行任何操作。如果应用程序编写者希望存储定位器以与其他文档事件一起使用,则可以在子类中覆盖此方法。</p>
     * 
     * 
     * @param locator A locator for all SAX document events.
     * @see org.xml.sax.ContentHandler#setDocumentLocator
     * @see org.xml.sax.Locator
     */
    public void setDocumentLocator (Locator locator)
    {
        // no op
    }


    /**
     * Receive notification of the beginning of the document.
     *
     * <p>By default, do nothing.  Application writers may override this
     * method in a subclass to take specific actions at the beginning
     * of a document (such as allocating the root node of a tree or
     * creating an output file).</p>
     *
     * <p>
     *  接收文档开头的通知。
     * 
     *  <p>默认情况下,不执行任何操作。应用程序编写器可以在子类中覆盖此方法,以在文档开头执行特定操作(例如分配树的根节点或创建输出文件)。</p>
     * 
     * 
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     * @see org.xml.sax.ContentHandler#startDocument
     */
    public void startDocument ()
        throws SAXException
    {
        // no op
    }


    /**
     * Receive notification of the end of the document.
     *
     * <p>By default, do nothing.  Application writers may override this
     * method in a subclass to take specific actions at the end
     * of a document (such as finalising a tree or closing an output
     * file).</p>
     *
     * <p>
     *  接收文档结束的通知。
     * 
     *  <p>默认情况下,不执行任何操作。应用程序编写器可以在子类中覆盖此方法,以在文档结尾处执行特定操作(例如,完成树或关闭输出文件)。</p>
     * 
     * 
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     * @see org.xml.sax.ContentHandler#endDocument
     */
    public void endDocument ()
        throws SAXException
    {
        // no op
    }


    /**
     * Receive notification of the start of a Namespace mapping.
     *
     * <p>By default, do nothing.  Application writers may override this
     * method in a subclass to take specific actions at the start of
     * each Namespace prefix scope (such as storing the prefix mapping).</p>
     *
     * <p>
     *  接收命名空间映射开始的通知。
     * 
     *  <p>默认情况下,不执行任何操作。应用程序编写器可以在子类中覆盖此方法,以在每个Namespace前缀作用域开始时执行特定操作(例如存储前缀映射)。</p>
     * 
     * 
     * @param prefix The Namespace prefix being declared.
     * @param uri The Namespace URI mapped to the prefix.
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     * @see org.xml.sax.ContentHandler#startPrefixMapping
     */
    public void startPrefixMapping (String prefix, String uri)
        throws SAXException
    {
        // no op
    }


    /**
     * Receive notification of the end of a Namespace mapping.
     *
     * <p>By default, do nothing.  Application writers may override this
     * method in a subclass to take specific actions at the end of
     * each prefix mapping.</p>
     *
     * <p>
     *  接收命名空间映射结束的通知。
     * 
     * <p>默认情况下,不执行任何操作。应用程序编写器可以在子类中覆盖此方法,以在每个前缀映射结束时采取特定操作。</p>
     * 
     * 
     * @param prefix The Namespace prefix being declared.
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     * @see org.xml.sax.ContentHandler#endPrefixMapping
     */
    public void endPrefixMapping (String prefix)
        throws SAXException
    {
        // no op
    }


    /**
     * Receive notification of the start of an element.
     *
     * <p>By default, do nothing.  Application writers may override this
     * method in a subclass to take specific actions at the start of
     * each element (such as allocating a new tree node or writing
     * output to a file).</p>
     *
     * <p>
     *  接收元素开始的通知。
     * 
     *  <p>默认情况下,不执行任何操作。应用程序编写器可以在子类中覆盖此方法,以在每个元素的开头执行特定操作(例如分配新的树节点或将输出写入文件)。</p>
     * 
     * 
     * @param uri The Namespace URI, or the empty string if the
     *        element has no Namespace URI or if Namespace
     *        processing is not being performed.
     * @param localName The local name (without prefix), or the
     *        empty string if Namespace processing is not being
     *        performed.
     * @param qName The qualified name (with prefix), or the
     *        empty string if qualified names are not available.
     * @param attributes The attributes attached to the element.  If
     *        there are no attributes, it shall be an empty
     *        Attributes object.
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     * @see org.xml.sax.ContentHandler#startElement
     */
    public void startElement (String uri, String localName,
                              String qName, Attributes attributes)
        throws SAXException
    {
        // no op
    }


    /**
     * Receive notification of the end of an element.
     *
     * <p>By default, do nothing.  Application writers may override this
     * method in a subclass to take specific actions at the end of
     * each element (such as finalising a tree node or writing
     * output to a file).</p>
     *
     * <p>
     *  接收元素结束的通知。
     * 
     *  <p>默认情况下,不执行任何操作。应用程序编写器可以在子类中覆盖此方法,以在每个元素结尾处执行特定操作(例如完成树节点或将输出写入文件)。</p>
     * 
     * 
     * @param uri The Namespace URI, or the empty string if the
     *        element has no Namespace URI or if Namespace
     *        processing is not being performed.
     * @param localName The local name (without prefix), or the
     *        empty string if Namespace processing is not being
     *        performed.
     * @param qName The qualified name (with prefix), or the
     *        empty string if qualified names are not available.
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     * @see org.xml.sax.ContentHandler#endElement
     */
    public void endElement (String uri, String localName, String qName)
        throws SAXException
    {
        // no op
    }


    /**
     * Receive notification of character data inside an element.
     *
     * <p>By default, do nothing.  Application writers may override this
     * method to take specific actions for each chunk of character data
     * (such as adding the data to a node or buffer, or printing it to
     * a file).</p>
     *
     * <p>
     *  接收元素内的字符数据的通知。
     * 
     *  <p>默认情况下,不执行任何操作。应用程序写入程序可以覆盖此方法,以对字符数据的每个块执行特定操作(例如将数据添加到节点或缓冲区,或将其打印到文件)。</p>
     * 
     * 
     * @param ch The characters.
     * @param start The start position in the character array.
     * @param length The number of characters to use from the
     *               character array.
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     * @see org.xml.sax.ContentHandler#characters
     */
    public void characters (char ch[], int start, int length)
        throws SAXException
    {
        // no op
    }


    /**
     * Receive notification of ignorable whitespace in element content.
     *
     * <p>By default, do nothing.  Application writers may override this
     * method to take specific actions for each chunk of ignorable
     * whitespace (such as adding data to a node or buffer, or printing
     * it to a file).</p>
     *
     * <p>
     *  在元素内容中接收可忽略的空格的通知。
     * 
     *  <p>默认情况下,不执行任何操作。应用程序编写者可以覆盖此方法,以对每个可忽略的空格(例如向节点或缓冲区添加数据或将其打印到文件)采取特定操作。</p>
     * 
     * 
     * @param ch The whitespace characters.
     * @param start The start position in the character array.
     * @param length The number of characters to use from the
     *               character array.
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     * @see org.xml.sax.ContentHandler#ignorableWhitespace
     */
    public void ignorableWhitespace (char ch[], int start, int length)
        throws SAXException
    {
        // no op
    }


    /**
     * Receive notification of a processing instruction.
     *
     * <p>By default, do nothing.  Application writers may override this
     * method in a subclass to take specific actions for each
     * processing instruction, such as setting status variables or
     * invoking other methods.</p>
     *
     * <p>
     *  接收处理指令的通知。
     * 
     *  <p>默认情况下,不执行任何操作。应用程序编写器可以在子类中覆盖此方法,以对每个处理指令采取特定操作,例如设置状态变量或调用其他方法。</p>
     * 
     * 
     * @param target The processing instruction target.
     * @param data The processing instruction data, or null if
     *             none is supplied.
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     * @see org.xml.sax.ContentHandler#processingInstruction
     */
    public void processingInstruction (String target, String data)
        throws SAXException
    {
        // no op
    }


    /**
     * Receive notification of a skipped entity.
     *
     * <p>By default, do nothing.  Application writers may override this
     * method in a subclass to take specific actions for each
     * processing instruction, such as setting status variables or
     * invoking other methods.</p>
     *
     * <p>
     * 接收跳过的实体的通知。
     * 
     *  <p>默认情况下,不执行任何操作。应用程序编写器可以在子类中覆盖此方法,以对每个处理指令采取特定操作,例如设置状态变量或调用其他方法。</p>
     * 
     * 
     * @param name The name of the skipped entity.
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     * @see org.xml.sax.ContentHandler#processingInstruction
     */
    public void skippedEntity (String name)
        throws SAXException
    {
        // no op
    }



    ////////////////////////////////////////////////////////////////////
    // Default implementation of the ErrorHandler interface.
    ////////////////////////////////////////////////////////////////////


    /**
     * Receive notification of a parser warning.
     *
     * <p>The default implementation does nothing.  Application writers
     * may override this method in a subclass to take specific actions
     * for each warning, such as inserting the message in a log file or
     * printing it to the console.</p>
     *
     * <p>
     *  接收解析器警告的通知。
     * 
     *  <p>默认实现什么也不做。应用程序编写器可以在子类中覆盖此方法,以对每个警告采取特定操作,例如将消息插入日志文件或将其打印到控制台。</p>
     * 
     * 
     * @param e The warning information encoded as an exception.
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     * @see org.xml.sax.ErrorHandler#warning
     * @see org.xml.sax.SAXParseException
     */
    public void warning (SAXParseException e)
        throws SAXException
    {
        // no op
    }


    /**
     * Receive notification of a recoverable parser error.
     *
     * <p>The default implementation does nothing.  Application writers
     * may override this method in a subclass to take specific actions
     * for each error, such as inserting the message in a log file or
     * printing it to the console.</p>
     *
     * <p>
     *  接收可恢复的解析器错误的通知。
     * 
     *  <p>默认实现什么也不做。应用程序编写器可以在子类中覆盖此方法,以对每个错误采取特定操作,例如将消息插入日志文件或将其打印到控制台。</p>
     * 
     * 
     * @param e The error information encoded as an exception.
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     * @see org.xml.sax.ErrorHandler#warning
     * @see org.xml.sax.SAXParseException
     */
    public void error (SAXParseException e)
        throws SAXException
    {
        // no op
    }


    /**
     * Report a fatal XML parsing error.
     *
     * <p>The default implementation throws a SAXParseException.
     * Application writers may override this method in a subclass if
     * they need to take specific actions for each fatal error (such as
     * collecting all of the errors into a single report): in any case,
     * the application must stop all regular processing when this
     * method is invoked, since the document is no longer reliable, and
     * the parser may no longer report parsing events.</p>
     *
     * <p>
     *  报告致命的XML解析错误。
     * 
     *  <p>默认实现会抛出SAXParseException。
     * 如果应用程序编写器需要对每个致命错误采取特定操作(例如将所有错误收集到单个报告中),则应用程序编写器可以在子类中覆盖此方法：在任何情况下,应用程序必须在调用此方法时停止所有常规处理,因为文档不再可靠,并
     * 
     * @param e The error information encoded as an exception.
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     * @see org.xml.sax.ErrorHandler#fatalError
     * @see org.xml.sax.SAXParseException
     */
    public void fatalError (SAXParseException e)
        throws SAXException
    {
        throw e;
    }

}

// end of DefaultHandler.java
