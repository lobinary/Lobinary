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

// SAXNotSupportedException.java - unsupported feature or value.
// http://www.saxproject.org
// Written by David Megginson
// NO WARRANTY!  This class is in the Public Domain.
// $Id: SAXNotSupportedException.java,v 1.4 2004/11/03 22:55:32 jsuttor Exp $

package org.xml.sax;

/**
 * Exception class for an unsupported operation.
 *
 * <blockquote>
 * <em>This module, both source code and documentation, is in the
 * Public Domain, and comes with <strong>NO WARRANTY</strong>.</em>
 * See <a href='http://www.saxproject.org'>http://www.saxproject.org</a>
 * for further information.
 * </blockquote>
 *
 * <p>An XMLReader will throw this exception when it recognizes a
 * feature or property identifier, but cannot perform the requested
 * operation (setting a state or value).  Other SAX2 applications and
 * extensions may use this class for similar purposes.</p>
 *
 * <p>
 *  不支持的操作的异常类。
 * 
 * <blockquote>
 *  <em>此模块(源代码和文档)都位于公共域中,并且随附<strong>无保修</strong>。
 * </em>请参阅<a href ='http：//www.saxproject.org '> http://www.saxproject.org </a>了解更多信息。
 * </blockquote>
 * 
 *  <p> XMLReader在识别功能或属性标识符但无法执行请求的操作(设置状态或值)时将抛出此异常。其他SAX2应用程序和扩展程序可能会将此类用于类似目的。</p>
 * 
 * @since SAX 2.0
 * @author David Megginson
 * @see org.xml.sax.SAXNotRecognizedException
 */
public class SAXNotSupportedException extends SAXException
{

    /**
     * Construct a new exception with no message.
     * <p>
     * 
     */
    public SAXNotSupportedException ()
    {
        super();
    }


    /**
     * Construct a new exception with the given message.
     *
     * <p>
     *  构造一个没有消息的新异常。
     * 
     * 
     * @param message The text message of the exception.
     */
    public SAXNotSupportedException (String message)
    {
        super(message);
    }

    // Added serialVersionUID to preserve binary compatibility
    static final long serialVersionUID = -1422818934641823846L;
}

// end of SAXNotSupportedException.java
