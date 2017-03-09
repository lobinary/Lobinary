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

// SAX exception class.
// http://www.saxproject.org
// No warranty; no copyright -- use this as you will.
// $Id: SAXException.java,v 1.3 2004/11/03 22:55:32 jsuttor Exp $

package org.xml.sax;

/**
 * Encapsulate a general SAX error or warning.
 *
 * <blockquote>
 * <em>This module, both source code and documentation, is in the
 * Public Domain, and comes with <strong>NO WARRANTY</strong>.</em>
 * See <a href='http://www.saxproject.org'>http://www.saxproject.org</a>
 * for further information.
 * </blockquote>
 *
 * <p>This class can contain basic error or warning information from
 * either the XML parser or the application: a parser writer or
 * application writer can subclass it to provide additional
 * functionality.  SAX handlers may throw this exception or
 * any exception subclassed from it.</p>
 *
 * <p>If the application needs to pass through other types of
 * exceptions, it must wrap those exceptions in a SAXException
 * or an exception derived from a SAXException.</p>
 *
 * <p>If the parser or application needs to include information about a
 * specific location in an XML document, it should use the
 * {@link org.xml.sax.SAXParseException SAXParseException} subclass.</p>
 *
 * <p>
 *  封装一般的SAX错误或警告。
 * 
 * <blockquote>
 *  <em>此模块(源代码和文档)都位于公共域中,并且随附<strong>无保证</strong>。
 * </em>请参阅<a href ='http：//www.saxproject.org '> http://www.saxproject.org </a>了解更多信息。
 * </blockquote>
 * 
 *  <p>此类可以包含来自XML解析器或应用程序的基本错误或警告信息：解析器写入程序或应用程序编写器可以对其进行子类化以提供其他功能。 SAX处理程序可能会抛出此异常或任何异常子类。</p>
 * 
 *  <p>如果应用程序需要通过其他类型的异常,它必须在SAXException中包装这些异常或从SAXException派生的异常。</p>
 * 
 *  <p>如果解析器或应用程序需要在XML文档中包含有关特定位置的信息,则应使用{@link org.xml.sax.SAXParseException SAXParseException}子类。
 * </p>。
 * 
 * 
 * @since SAX 1.0
 * @author David Megginson
 * @version 2.0.1 (sax2r2)
 * @see org.xml.sax.SAXParseException
 */
public class SAXException extends Exception {


    /**
     * Create a new SAXException.
     * <p>
     *  创建新的SAXException。
     * 
     */
    public SAXException ()
    {
        super();
        this.exception = null;
    }


    /**
     * Create a new SAXException.
     *
     * <p>
     *  创建新的SAXException。
     * 
     * 
     * @param message The error or warning message.
     */
    public SAXException (String message) {
        super(message);
        this.exception = null;
    }


    /**
     * Create a new SAXException wrapping an existing exception.
     *
     * <p>The existing exception will be embedded in the new
     * one, and its message will become the default message for
     * the SAXException.</p>
     *
     * <p>
     *  创建一个新的SAXException包装一个现有的异常。
     * 
     *  <p>现有异常将嵌入新的异常,其消息将成为SAXException的默认消息。</p>
     * 
     * 
     * @param e The exception to be wrapped in a SAXException.
     */
    public SAXException (Exception e)
    {
        super();
        this.exception = e;
    }


    /**
     * Create a new SAXException from an existing exception.
     *
     * <p>The existing exception will be embedded in the new
     * one, but the new exception will have its own message.</p>
     *
     * <p>
     *  从现有异常创建新的SAXException。
     * 
     *  <p>现有的异常将嵌入新的异常,但新的异常将有自己的消息。</p>
     * 
     * 
     * @param message The detail message.
     * @param e The exception to be wrapped in a SAXException.
     */
    public SAXException (String message, Exception e)
    {
        super(message);
        this.exception = e;
    }


    /**
     * Return a detail message for this exception.
     *
     * <p>If there is an embedded exception, and if the SAXException
     * has no detail message of its own, this method will return
     * the detail message from the embedded exception.</p>
     *
     * <p>
     * 返回此异常的详细信息。
     * 
     *  <p>如果存在嵌入异常,并且SAXException没有自己的详细信息,则此方法将返回嵌入异常的详细信息。</p>
     * 
     * 
     * @return The error or warning message.
     */
    public String getMessage ()
    {
        String message = super.getMessage();

        if (message == null && exception != null) {
            return exception.getMessage();
        } else {
            return message;
        }
    }


    /**
     * Return the embedded exception, if any.
     *
     * <p>
     *  返回嵌入的异常(如果有)。
     * 
     * 
     * @return The embedded exception, or null if there is none.
     */
    public Exception getException ()
    {
        return exception;
    }

    /**
     * Return the cause of the exception
     *
     * <p>
     *  返回异常的原因
     * 
     * 
     * @return Return the cause of the exception
     */
    public Throwable getCause() {
        return exception;
    }

    /**
     * Override toString to pick up any embedded exception.
     *
     * <p>
     *  覆盖toString以选取任何嵌入的异常。
     * 
     * 
     * @return A string representation of this exception.
     */
    public String toString ()
    {
        if (exception != null) {
            return super.toString() + "\n" + exception.toString();
        } else {
            return super.toString();
        }
    }



    //////////////////////////////////////////////////////////////////////
    // Internal state.
    //////////////////////////////////////////////////////////////////////


    /**
    /* <p>
    /* 
     * @serial The embedded exception if tunnelling, or null.
     */
    private Exception exception;

    // Added serialVersionUID to preserve binary compatibility
    static final long serialVersionUID = 583241635256073760L;
}

// end of SAXException.java
