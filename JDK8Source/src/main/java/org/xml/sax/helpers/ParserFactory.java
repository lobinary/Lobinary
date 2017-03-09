/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

// SAX parser factory.
// http://www.saxproject.org
// No warranty; no copyright -- use this as you will.
// $Id: ParserFactory.java,v 1.2 2004/11/03 22:53:09 jsuttor Exp $

package org.xml.sax.helpers;

import org.xml.sax.Parser;


/**
 * Java-specific class for dynamically loading SAX parsers.
 *
 * <blockquote>
 * <em>This module, both source code and documentation, is in the
 * Public Domain, and comes with <strong>NO WARRANTY</strong>.</em>
 * See <a href='http://www.saxproject.org'>http://www.saxproject.org</a>
 * for further information.
 * </blockquote>
 *
 * <p><strong>Note:</strong> This class is designed to work with the now-deprecated
 * SAX1 {@link org.xml.sax.Parser Parser} class.  SAX2 applications should use
 * {@link org.xml.sax.helpers.XMLReaderFactory XMLReaderFactory} instead.</p>
 *
 * <p>ParserFactory is not part of the platform-independent definition
 * of SAX; it is an additional convenience class designed
 * specifically for Java XML application writers.  SAX applications
 * can use the static methods in this class to allocate a SAX parser
 * dynamically at run-time based either on the value of the
 * `org.xml.sax.parser' system property or on a string containing the class
 * name.</p>
 *
 * <p>Note that the application still requires an XML parser that
 * implements SAX1.</p>
 *
 * <p>
 *  用于动态加载SAX解析器的Java特定类。
 * 
 * <blockquote>
 *  <em>此模块(源代码和文档)都位于公共域中,并且随附<strong>无保证</strong>。
 * </em>请参阅<a href ='http：//www.saxproject.org '> http://www.saxproject.org </a>了解更多信息。
 * </blockquote>
 * 
 *  <p> <strong>注意</strong>：此类旨在与现已弃用的SAX1 {@link org.xml.sax.Parser Parser}类一起使用。
 *  SAX2应用程序应该使用{@link org.xml.sax.helpers.XMLReaderFactory XMLReaderFactory}。</p>。
 * 
 *  <p> ParserFactory不是SAX的平台独立定义的一部分;它是专门为Java XML应用程序编写器设计的一个附加便利类。
 *  SAX应用程序可以使用此类中的静态方法在运行时根据org.xml.sax.parser系统属性的值或包含类名的字符串动态分配SAX解析器。</p >。
 * 
 *  <p>请注意,应用程序仍需要一个实现SAX1的XML解析器。</p>
 * 
 * @deprecated This class works with the deprecated
 *             {@link org.xml.sax.Parser Parser}
 *             interface.
 * @since SAX 1.0
 * @author David Megginson
 * @version 2.0.1 (sax2r2)
 */
public class ParserFactory {
    private static SecuritySupport ss = new SecuritySupport();

    /**
     * Private null constructor.
     * <p>
     * 
     */
    private ParserFactory ()
    {
    }


    /**
     * Create a new SAX parser using the `org.xml.sax.parser' system property.
     *
     * <p>The named class must exist and must implement the
     * {@link org.xml.sax.Parser Parser} interface.</p>
     *
     * <p>
     *  私有null构造函数。
     * 
     * 
     * @exception java.lang.NullPointerException There is no value
     *            for the `org.xml.sax.parser' system property.
     * @exception java.lang.ClassNotFoundException The SAX parser
     *            class was not found (check your CLASSPATH).
     * @exception IllegalAccessException The SAX parser class was
     *            found, but you do not have permission to load
     *            it.
     * @exception InstantiationException The SAX parser class was
     *            found but could not be instantiated.
     * @exception java.lang.ClassCastException The SAX parser class
     *            was found and instantiated, but does not implement
     *            org.xml.sax.Parser.
     * @see #makeParser(java.lang.String)
     * @see org.xml.sax.Parser
     */
    public static Parser makeParser ()
        throws ClassNotFoundException,
        IllegalAccessException,
        InstantiationException,
        NullPointerException,
        ClassCastException
    {
        String className = ss.getSystemProperty("org.xml.sax.parser");
        if (className == null) {
            throw new NullPointerException("No value for sax.parser property");
        } else {
            return makeParser(className);
        }
    }


    /**
     * Create a new SAX parser object using the class name provided.
     *
     * <p>The named class must exist and must implement the
     * {@link org.xml.sax.Parser Parser} interface.</p>
     *
     * <p>
     *  使用`org.xml.sax.parser'系统属性创建一个新的SAX解析器。
     * 
     *  <p>命名类必须存在,并且必须实现{@link org.xml.sax.Parser Parser}接口。</p>
     * 
     * 
     * @param className A string containing the name of the
     *                  SAX parser class.
     * @exception java.lang.ClassNotFoundException The SAX parser
     *            class was not found (check your CLASSPATH).
     * @exception IllegalAccessException The SAX parser class was
     *            found, but you do not have permission to load
     *            it.
     * @exception InstantiationException The SAX parser class was
     *            found but could not be instantiated.
     * @exception java.lang.ClassCastException The SAX parser class
     *            was found and instantiated, but does not implement
     *            org.xml.sax.Parser.
     * @see #makeParser()
     * @see org.xml.sax.Parser
     */
    public static Parser makeParser (String className)
        throws ClassNotFoundException,
        IllegalAccessException,
        InstantiationException,
        ClassCastException
    {
        return (Parser) NewInstance.newInstance (
                ss.getContextClassLoader(), className);
    }

}
