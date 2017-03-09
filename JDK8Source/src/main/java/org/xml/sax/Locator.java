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

// SAX locator interface for document events.
// http://www.saxproject.org
// No warranty; no copyright -- use this as you will.
// $Id: Locator.java,v 1.2 2004/11/03 22:55:32 jsuttor Exp $

package org.xml.sax;


/**
 * Interface for associating a SAX event with a document location.
 *
 * <blockquote>
 * <em>This module, both source code and documentation, is in the
 * Public Domain, and comes with <strong>NO WARRANTY</strong>.</em>
 * See <a href='http://www.saxproject.org'>http://www.saxproject.org</a>
 * for further information.
 * </blockquote>
 *
 * <p>If a SAX parser provides location information to the SAX
 * application, it does so by implementing this interface and then
 * passing an instance to the application using the content
 * handler's {@link org.xml.sax.ContentHandler#setDocumentLocator
 * setDocumentLocator} method.  The application can use the
 * object to obtain the location of any other SAX event
 * in the XML source document.</p>
 *
 * <p>Note that the results returned by the object will be valid only
 * during the scope of each callback method: the application
 * will receive unpredictable results if it attempts to use the
 * locator at any other time, or after parsing completes.</p>
 *
 * <p>SAX parsers are not required to supply a locator, but they are
 * very strongly encouraged to do so.  If the parser supplies a
 * locator, it must do so before reporting any other document events.
 * If no locator has been set by the time the application receives
 * the {@link org.xml.sax.ContentHandler#startDocument startDocument}
 * event, the application should assume that a locator is not
 * available.</p>
 *
 * <p>
 *  将SAX事件与文档位置相关联的接口。
 * 
 * <blockquote>
 *  <em>此模块(源代码和文档)都位于公共域中,并且随附<strong>无保证</strong>。
 * </em>请参阅<a href ='http：//www.saxproject.org '> http://www.saxproject.org </a>了解更多信息。
 * </blockquote>
 * 
 *  <p>如果SAX解析器向SAX应用程序提供位置信息,则通过实现此接口,然后使用内容处理程序的{@link org.xml.sax.ContentHandler#setDocumentLocator setDocumentLocator}
 * 方法将实例传递给应用程序。
 * 应用程序可以使用该对象来获取XML源文档中任何其他SAX事件的位置。</p>。
 * 
 *  <p>请注意,对象返回的结果仅在每个回调方法的范围内有效：如果在任何其他时间尝试使用定位器或在解析完成后,应用程序将收到不可预测的结果。</p>
 * 
 *  <p> SAX解析器不需要提供定位器,但是他们非常鼓励这样做。如果解析器提供了定位器,它必须在报告任何其他文档事件之前这样做。
 * 如果在应用程序接收到{@link org.xml.sax.ContentHandler#startDocument startDocument}事件时没有设置定位器,应用程序应假定定位器不可用。
 * </p>。
 * 
 * 
 * @since SAX 1.0
 * @author David Megginson
 * @see org.xml.sax.ContentHandler#setDocumentLocator
 */
public interface Locator {


    /**
     * Return the public identifier for the current document event.
     *
     * <p>The return value is the public identifier of the document
     * entity or of the external parsed entity in which the markup
     * triggering the event appears.</p>
     *
     * <p>
     * 返回当前文档事件的公共标识符。
     * 
     *  <p>返回值是文档实体或外部解析实体的公共标识符,其中触发事件的标记出现。</p>
     * 
     * 
     * @return A string containing the public identifier, or
     *         null if none is available.
     * @see #getSystemId
     */
    public abstract String getPublicId ();


    /**
     * Return the system identifier for the current document event.
     *
     * <p>The return value is the system identifier of the document
     * entity or of the external parsed entity in which the markup
     * triggering the event appears.</p>
     *
     * <p>If the system identifier is a URL, the parser must resolve it
     * fully before passing it to the application.  For example, a file
     * name must always be provided as a <em>file:...</em> URL, and other
     * kinds of relative URI are also resolved against their bases.</p>
     *
     * <p>
     *  返回当前文档事件的系统标识符。
     * 
     *  <p>返回值是触发事件的标记出现的文档实体或外部解析实体的系统标识符。</p>
     * 
     *  <p>如果系统标识符是URL,则解析器在将其传递给应用程序之前必须完全解析它。例如,文件名必须始终作为<em>文件提供：... </em> URL,以及其他类型的相对URI也会根据其基址解析。
     * </p>。
     * 
     * 
     * @return A string containing the system identifier, or null
     *         if none is available.
     * @see #getPublicId
     */
    public abstract String getSystemId ();


    /**
     * Return the line number where the current document event ends.
     * Lines are delimited by line ends, which are defined in
     * the XML specification.
     *
     * <p><strong>Warning:</strong> The return value from the method
     * is intended only as an approximation for the sake of diagnostics;
     * it is not intended to provide sufficient information
     * to edit the character content of the original XML document.
     * In some cases, these "line" numbers match what would be displayed
     * as columns, and in others they may not match the source text
     * due to internal entity expansion.  </p>
     *
     * <p>The return value is an approximation of the line number
     * in the document entity or external parsed entity where the
     * markup triggering the event appears.</p>
     *
     * <p>If possible, the SAX driver should provide the line position
     * of the first character after the text associated with the document
     * event.  The first line is line 1.</p>
     *
     * <p>
     *  返回当前文档事件结束的行号。行由XML终端定义,在XML规范中定义。
     * 
     *  <p> <strong>警告：</strong>方法的返回值仅用于诊断的近似值;它不旨在提供足够的信息来编辑原始XML文档的字符内容。
     * 在某些情况下,这些"行"数字匹配将作为列显示,在其他情况下,由于内部实体扩展,它们可能不匹配源文本。 </p>。
     * 
     *  <p>返回值是文档实体或外部解析实体中出现触发事件的标记的行号的近似值。</p>
     * 
     * <p>如果可能,SAX驱动程序应在与文档事件相关联的文本之后提供第一个字符的行位置。第一行是第1行。</p>
     * 
     * 
     * @return The line number, or -1 if none is available.
     * @see #getColumnNumber
     */
    public abstract int getLineNumber ();


    /**
     * Return the column number where the current document event ends.
     * This is one-based number of Java <code>char</code> values since
     * the last line end.
     *
     * <p><strong>Warning:</strong> The return value from the method
     * is intended only as an approximation for the sake of diagnostics;
     * it is not intended to provide sufficient information
     * to edit the character content of the original XML document.
     * For example, when lines contain combining character sequences, wide
     * characters, surrogate pairs, or bi-directional text, the value may
     * not correspond to the column in a text editor's display. </p>
     *
     * <p>The return value is an approximation of the column number
     * in the document entity or external parsed entity where the
     * markup triggering the event appears.</p>
     *
     * <p>If possible, the SAX driver should provide the line position
     * of the first character after the text associated with the document
     * event.  The first column in each line is column 1.</p>
     *
     * <p>
     *  返回当前文档事件结束的列号。这是自从最后一行结束以来基于1的Java <code> char </code>值。
     * 
     *  <p> <strong>警告：</strong>方法的返回值仅用于诊断的近似值;它不旨在提供足够的信息来编辑原始XML文档的字符内容。
     * 例如,当行包含组合字符序列,宽字符,替代对或双向文本时,该值可能不对应于文本编辑器显示中的列。 </p>。
     * 
     * 
     * @return The column number, or -1 if none is available.
     * @see #getLineNumber
     */
    public abstract int getColumnNumber ();

}

// end of Locator.java
