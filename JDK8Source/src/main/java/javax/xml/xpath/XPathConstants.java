/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2005, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.xpath;

import javax.xml.namespace.QName;

/**
 * <p>XPath constants.</p>
 *
 * <p>
 *  <p> XPath常数。</p>
 * 
 * 
 * @author <a href="mailto:Norman.Walsh@Sun.COM">Norman Walsh</a>
 * @author <a href="mailto:Jeff.Suttor@Sun.COM">Jeff Suttor</a>
 * @see <a href="http://www.w3.org/TR/xpath">XML Path Language (XPath) Version 1.0</a>
 * @since 1.5
 */
public class XPathConstants {

    /**
     * <p>Private constructor to prevent instantiation.</p>
     * <p>
     *  <p>私人构造函数阻止实例化。</p>
     * 
     */
    private XPathConstants() { }

    /**
     * <p>The XPath 1.0 number data type.</p>
     *
     * <p>Maps to Java {@link Double}.</p>
     * <p>
     *  <p> XPath 1.0号码数据类型。</p>
     * 
     *  <p>映射到Java {@link Double}。</p>
     * 
     */
    public static final QName NUMBER = new QName("http://www.w3.org/1999/XSL/Transform", "NUMBER");

    /**
     * <p>The XPath 1.0 string data type.</p>
     *
     * <p>Maps to Java {@link String}.</p>
     * <p>
     *  <p> XPath 1.0字符串数据类型。</p>
     * 
     *  <p>映射到Java {@link String}。</p>
     * 
     */
    public static final QName STRING = new QName("http://www.w3.org/1999/XSL/Transform", "STRING");

    /**
     * <p>The XPath 1.0 boolean data type.</p>
     *
     * <p>Maps to Java {@link Boolean}.</p>
     * <p>
     *  <p> XPath 1.0布林数据类型。</p>
     * 
     *  <p>映射到Java {@link Boolean}。</p>
     * 
     */
    public static final QName BOOLEAN = new QName("http://www.w3.org/1999/XSL/Transform", "BOOLEAN");

    /**
     * <p>The XPath 1.0 NodeSet data type.</p>
     *
     * <p>Maps to Java {@link org.w3c.dom.NodeList}.</p>
     * <p>
     *  <p> XPath 1.0 NodeSet数据类型。</p>
     * 
     *  <p>映射到Java {@link org.w3c.dom.NodeList}。</p>
     * 
     */
    public static final QName NODESET = new QName("http://www.w3.org/1999/XSL/Transform", "NODESET");

    /**
     * <p>The XPath 1.0 NodeSet data type.
     *
     * <p>Maps to Java {@link org.w3c.dom.Node}.</p>
     * <p>
     *  <p> XPath 1.0 NodeSet数据类型。
     * 
     *  <p>映射到Java {@link org.w3c.dom.Node}。</p>
     * 
     */
    public static final QName NODE = new QName("http://www.w3.org/1999/XSL/Transform", "NODE");

    /**
     * <p>The URI for the DOM object model, "http://java.sun.com/jaxp/xpath/dom".</p>
     * <p>
     */
    public static final String DOM_OBJECT_MODEL = "http://java.sun.com/jaxp/xpath/dom";
}
