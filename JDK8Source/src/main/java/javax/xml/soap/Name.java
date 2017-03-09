/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2012, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.soap;

/**
 * A representation of an XML name.  This interface provides methods for
 * getting the local and namespace-qualified names and also for getting the
 * prefix associated with the namespace for the name. It is also possible
 * to get the URI of the namespace.
 * <P>
 * The following is an example of a namespace declaration in an element.
 * <PRE>
 *   &lt;wombat:GetLastTradePrice xmlns:wombat="http://www.wombat.org/trader"&gt;
 * </PRE>
 * ("xmlns" stands for "XML namespace".)
 * The following
 * shows what the methods in the <code>Name</code> interface will return.
 * <UL>
 *  <LI><code>getQualifiedName</code> will return "prefix:LocalName" =
 *      "WOMBAT:GetLastTradePrice"
 *  <LI><code>getURI</code> will return "http://www.wombat.org/trader"
 *  <LI><code>getLocalName</code> will return "GetLastTracePrice"
 *  <LI><code>getPrefix</code> will return "WOMBAT"
 * </UL>
 * <P>
 * XML namespaces are used to disambiguate SOAP identifiers from
 * application-specific identifiers.
 * <P>
 * <code>Name</code> objects are created using the method
 * <code>SOAPEnvelope.createName</code>, which has two versions.
 * One method creates <code>Name</code> objects with
 * a local name, a namespace prefix, and a namespace URI.
 *  and the second creates <code>Name</code> objects with just a local name.
 * The following line of
 * code, in which <i>se</i> is a <code>SOAPEnvelope</code> object, creates a new
 * <code>Name</code> object with all three.
 * <PRE>
 *     Name name = se.createName("GetLastTradePrice", "WOMBAT",
 *                                "http://www.wombat.org/trader");
 * </PRE>
 * The following line of code gives an example of how a <code>Name</code> object
 * can be used. The variable <i>element</i> is a <code>SOAPElement</code> object.
 * This code creates a new <code>SOAPElement</code> object with the given name and
 * adds it to <i>element</i>.
 * <PRE>
 *     element.addChildElement(name);
 * </PRE>
 * <P>
 * The <code>Name</code> interface may be deprecated in a future release of SAAJ
 * in favor of <code>javax.xml.namespace.QName<code>
 * <p>
 *  XML名称的表示形式。此接口提供了获取本地和命名空间限定名称以及获取与名称的命名空间相关联的前缀的方法。也可以获取命名空间的URI。
 * <P>
 *  以下是元素中的命名空间声明的示例。
 * <PRE>
 *  &lt; wombat：GetLastTradePrice xmlns：wombat ="http://www.wombat.org/trader"&gt;
 * </PRE>
 *  ("xmlns"表示"XML命名空间"。)以下显示了<code> Name </code>接口中的方法将返回的方法。
 * <UL>
 *  <LI> <code> getQualifiedName </code>会返回"prefix：LocalName"="WOMBAT：GetLastTradePrice"<LI> <code> getU
 * RI </code>会返回"http://www.wombat.org/trader" <LI> <code> getLocalName </code>将返回"GetLastTracePrice"<LI>
 *  <code> getPrefix </code>会返回"WOMBAT"。
 * </UL>
 * <P>
 *  XML命名空间用于从应用程序特定标识符中消除SOAP标识符的歧义。
 * <P>
 *  <code> Name </code>对象是使用方法<code> SOAPEnvelope.createName </code>创建的,它有两个版本。
 * 一种方法使用本地名称,命名空间前缀和命名空间URI创建<code> Name </code>对象。第二个只创建一个本地名称的<code> Name </code>对象。
 * 下面的代码行,其中<i> </i>是<code> SOAPEnvelope </code>对象,创建一个新的<code> Name </code>对象。
 * <PRE>
 * 名称= se.createName("GetLastTradePrice","WOMBAT","http://www.wombat.org/trader");
 * </PRE>
 * 
 * @see SOAPEnvelope#createName(String, String, String) SOAPEnvelope.createName
 * @see SOAPFactory#createName(String, String, String) SOAPFactory.createName
 */
public interface Name {
    /**
     * Gets the local name part of the XML name that this <code>Name</code>
     * object represents.
     *
     * <p>
     *  以下代码行给出了如何使用<code> Name </code>对象的示例。变量<i>元素</i>是一个<code> SOAPElement </code>对象。
     * 此代码使用给定的名称创建一个新的<code> SOAPElement </code>对象,并将其添加到<i>元素</i>。
     * <PRE>
     *  element.addChildElement(name);
     * </PRE>
     * <P>
     *  <code> Name </code>接口在SAAJ的将来版本中可能会被弃用,赞成<code> javax.xml.namespace.QName <code>
     * 
     * 
     * @return a string giving the local name
     */
    String getLocalName();

    /**
     * Gets the namespace-qualified name of the XML name that this
     * <code>Name</code> object represents.
     *
     * <p>
     *  获取此<code> Name </code>对象表示的XML名称的本地名称部分。
     * 
     * 
     * @return the namespace-qualified name as a string
     */
    String getQualifiedName();

    /**
     * Returns the prefix that was specified when this <code>Name</code> object
     * was initialized. This prefix is associated with the namespace for the XML
     * name that this <code>Name</code> object represents.
     *
     * <p>
     *  获取此<code> Name </code>对象表示的XML名称的命名空间限定名称。
     * 
     * 
     * @return the prefix as a string
     */
    String getPrefix();

    /**
     * Returns the URI of the namespace for the XML
     * name that this <code>Name</code> object represents.
     *
     * <p>
     *  返回在初始化此<code> Name </code>对象时指定的前缀。此前缀与此<code> Name </code>对象表示的XML名称的命名空间相关联。
     * 
     * 
     * @return the URI as a string
     */
    String getURI();
}
