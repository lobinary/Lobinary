/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * <p>
 *  根据一个或多个贡献者许可协议授予Apache软件基金会(ASF)。有关版权所有权的其他信息,请参阅随此作品分发的NOTICE文件。
 *  ASF根据Apache许可证2.0版("许可证")向您授予此文件;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本。
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
package com.sun.org.apache.xml.internal.security.utils;

import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * This is the base class to all Objects which have a direct 1:1 mapping to an
 * Element in a particular namespace.
 * <p>
 *  这是所有具有到特定命名空间中的元素的直接1：1映射的对象的基类。
 * 
 */
public abstract class ElementProxy {

    protected static final java.util.logging.Logger log =
        java.util.logging.Logger.getLogger(ElementProxy.class.getName());

    /** Field constructionElement */
    protected Element constructionElement = null;

    /** Field baseURI */
    protected String baseURI = null;

    /** Field doc */
    protected Document doc = null;

    /** Field prefixMappings */
    private static Map<String, String> prefixMappings = new ConcurrentHashMap<String, String>();

    /**
     * Constructor ElementProxy
     *
     * <p>
     *  构造函数ElementProxy
     * 
     */
    public ElementProxy() {
    }

    /**
     * Constructor ElementProxy
     *
     * <p>
     *  构造函数ElementProxy
     * 
     * 
     * @param doc
     */
    public ElementProxy(Document doc) {
        if (doc == null) {
            throw new RuntimeException("Document is null");
        }

        this.doc = doc;
        this.constructionElement =
            createElementForFamilyLocal(this.doc, this.getBaseNamespace(), this.getBaseLocalName());
    }

    /**
     * Constructor ElementProxy
     *
     * <p>
     *  构造函数ElementProxy
     * 
     * 
     * @param element
     * @param BaseURI
     * @throws XMLSecurityException
     */
    public ElementProxy(Element element, String BaseURI) throws XMLSecurityException {
        if (element == null) {
            throw new XMLSecurityException("ElementProxy.nullElement");
        }

        if (log.isLoggable(java.util.logging.Level.FINE)) {
            log.log(java.util.logging.Level.FINE, "setElement(\"" + element.getTagName() + "\", \"" + BaseURI + "\")");
        }

        this.doc = element.getOwnerDocument();
        this.constructionElement = element;
        this.baseURI = BaseURI;

        this.guaranteeThatElementInCorrectSpace();
    }

    /**
     * Returns the namespace of the Elements of the sub-class.
     *
     * <p>
     *  返回子类的Elements的命名空间。
     * 
     * 
     * @return the namespace of the Elements of the sub-class.
     */
    public abstract String getBaseNamespace();

    /**
     * Returns the localname of the Elements of the sub-class.
     *
     * <p>
     *  返回子类的Elements的localname。
     * 
     * 
     * @return the localname of the Elements of the sub-class.
     */
    public abstract String getBaseLocalName();


    protected Element createElementForFamilyLocal(
        Document doc, String namespace, String localName
    ) {
        Element result = null;
        if (namespace == null) {
            result = doc.createElementNS(null, localName);
        } else {
            String baseName = this.getBaseNamespace();
            String prefix = ElementProxy.getDefaultPrefix(baseName);
            if ((prefix == null) || (prefix.length() == 0)) {
                result = doc.createElementNS(namespace, localName);
                result.setAttributeNS(Constants.NamespaceSpecNS, "xmlns", namespace);
            } else {
                result = doc.createElementNS(namespace, prefix + ":" + localName);
                result.setAttributeNS(Constants.NamespaceSpecNS, "xmlns:" + prefix, namespace);
            }
        }
        return result;
    }


    /**
     * This method creates an Element in a given namespace with a given localname.
     * It uses the {@link ElementProxy#getDefaultPrefix} method to decide whether
     * a particular prefix is bound to that namespace.
     * <BR />
     * This method was refactored out of the constructor.
     *
     * <p>
     *  此方法在给定的命名空间中创建具有给定本地名称的元素。它使用{@link ElementProxy#getDefaultPrefix}方法来决定特定的前缀是否绑定到该命名空间。
     * <BR />
     *  这个方法从构造函数重构。
     * 
     * 
     * @param doc
     * @param namespace
     * @param localName
     * @return The element created.
     */
    public static Element createElementForFamily(Document doc, String namespace, String localName) {
        Element result = null;
        String prefix = ElementProxy.getDefaultPrefix(namespace);

        if (namespace == null) {
            result = doc.createElementNS(null, localName);
        } else {
            if ((prefix == null) || (prefix.length() == 0)) {
                result = doc.createElementNS(namespace, localName);
                result.setAttributeNS(Constants.NamespaceSpecNS, "xmlns", namespace);
            } else {
                result = doc.createElementNS(namespace, prefix + ":" + localName);
                result.setAttributeNS(Constants.NamespaceSpecNS, "xmlns:" + prefix, namespace);
            }
        }

        return result;
    }

    /**
     * Method setElement
     *
     * <p>
     *  方法setElement
     * 
     * 
     * @param element
     * @param BaseURI
     * @throws XMLSecurityException
     */
    public void setElement(Element element, String BaseURI) throws XMLSecurityException {
        if (element == null) {
            throw new XMLSecurityException("ElementProxy.nullElement");
        }

        if (log.isLoggable(java.util.logging.Level.FINE)) {
            log.log(java.util.logging.Level.FINE, "setElement(" + element.getTagName() + ", \"" + BaseURI + "\"");
        }

        this.doc = element.getOwnerDocument();
        this.constructionElement = element;
        this.baseURI = BaseURI;
    }


    /**
     * Returns the Element which was constructed by the Object.
     *
     * <p>
     * 返回由对象构造的元素。
     * 
     * 
     * @return the Element which was constructed by the Object.
     */
    public final Element getElement() {
        return this.constructionElement;
    }

    /**
     * Returns the Element plus a leading and a trailing CarriageReturn Text node.
     *
     * <p>
     *  返回元素加上前导和尾部CarriageReturn文本节点。
     * 
     * 
     * @return the Element which was constructed by the Object.
     */
    public final NodeList getElementPlusReturns() {

        HelperNodeList nl = new HelperNodeList();

        nl.appendChild(this.doc.createTextNode("\n"));
        nl.appendChild(this.getElement());
        nl.appendChild(this.doc.createTextNode("\n"));

        return nl;
    }

    /**
     * Method getDocument
     *
     * <p>
     *  方法getDocument
     * 
     * 
     * @return the Document where this element is contained.
     */
    public Document getDocument() {
        return this.doc;
    }

    /**
     * Method getBaseURI
     *
     * <p>
     *  方法getBaseURI
     * 
     * 
     * @return the base uri of the namespace of this element
     */
    public String getBaseURI() {
        return this.baseURI;
    }

    /**
     * Method guaranteeThatElementInCorrectSpace
     *
     * <p>
     *  方法guaranteeThatElementInCorrectSpace
     * 
     * 
     * @throws XMLSecurityException
     */
    void guaranteeThatElementInCorrectSpace() throws XMLSecurityException {

        String expectedLocalName = this.getBaseLocalName();
        String expectedNamespaceUri = this.getBaseNamespace();

        String actualLocalName = this.constructionElement.getLocalName();
        String actualNamespaceUri = this.constructionElement.getNamespaceURI();

        if(!expectedNamespaceUri.equals(actualNamespaceUri)
            && !expectedLocalName.equals(actualLocalName)) {
            Object exArgs[] = { actualNamespaceUri + ":" + actualLocalName,
                                expectedNamespaceUri + ":" + expectedLocalName};
            throw new XMLSecurityException("xml.WrongElement", exArgs);
        }
    }

    /**
     * Method addBigIntegerElement
     *
     * <p>
     *  方法addBigIntegerElement
     * 
     * 
     * @param bi
     * @param localname
     */
    public void addBigIntegerElement(BigInteger bi, String localname) {
        if (bi != null) {
            Element e = XMLUtils.createElementInSignatureSpace(this.doc, localname);

            Base64.fillElementWithBigInteger(e, bi);
            this.constructionElement.appendChild(e);
            XMLUtils.addReturnToElement(this.constructionElement);
        }
    }

    /**
     * Method addBase64Element
     *
     * <p>
     *  方法addBase64Element
     * 
     * 
     * @param bytes
     * @param localname
     */
    public void addBase64Element(byte[] bytes, String localname) {
        if (bytes != null) {
            Element e = Base64.encodeToElement(this.doc, localname, bytes);

            this.constructionElement.appendChild(e);
            if (!XMLUtils.ignoreLineBreaks()) {
                this.constructionElement.appendChild(this.doc.createTextNode("\n"));
            }
        }
    }

    /**
     * Method addTextElement
     *
     * <p>
     *  方法addTextElement
     * 
     * 
     * @param text
     * @param localname
     */
    public void addTextElement(String text, String localname) {
        Element e = XMLUtils.createElementInSignatureSpace(this.doc, localname);
        Text t = this.doc.createTextNode(text);

        e.appendChild(t);
        this.constructionElement.appendChild(e);
        XMLUtils.addReturnToElement(this.constructionElement);
    }

    /**
     * Method addBase64Text
     *
     * <p>
     *  方法addBase64Text
     * 
     * 
     * @param bytes
     */
    public void addBase64Text(byte[] bytes) {
        if (bytes != null) {
            Text t = XMLUtils.ignoreLineBreaks()
                ? this.doc.createTextNode(Base64.encode(bytes))
                : this.doc.createTextNode("\n" + Base64.encode(bytes) + "\n");
            this.constructionElement.appendChild(t);
        }
    }

    /**
     * Method addText
     *
     * <p>
     *  方法addText
     * 
     * 
     * @param text
     */
    public void addText(String text) {
        if (text != null) {
            Text t = this.doc.createTextNode(text);

            this.constructionElement.appendChild(t);
        }
    }

    /**
     * Method getVal
     *
     * <p>
     *  方法getVal
     * 
     * 
     * @param localname
     * @param namespace
     * @return The biginteger contained in the given element
     * @throws Base64DecodingException
     */
    public BigInteger getBigIntegerFromChildElement(
        String localname, String namespace
    ) throws Base64DecodingException {
        return Base64.decodeBigIntegerFromText(
            XMLUtils.selectNodeText(
                this.constructionElement.getFirstChild(), namespace, localname, 0
            )
        );
    }

    /**
     * Method getBytesFromChildElement
     * <p>
     *  方法getBytesFromChildElement
     * 
     * 
     * @deprecated
     * @param localname
     * @param namespace
     * @return the bytes
     * @throws XMLSecurityException
     */
    @Deprecated
    public byte[] getBytesFromChildElement(String localname, String namespace)
        throws XMLSecurityException {
        Element e =
            XMLUtils.selectNode(
                this.constructionElement.getFirstChild(), namespace, localname, 0
            );

        return Base64.decode(e);
    }

    /**
     * Method getTextFromChildElement
     *
     * <p>
     *  方法getTextFromChildElement
     * 
     * 
     * @param localname
     * @param namespace
     * @return the Text of the textNode
     */
    public String getTextFromChildElement(String localname, String namespace) {
        return XMLUtils.selectNode(
                this.constructionElement.getFirstChild(),
                namespace,
                localname,
                0).getTextContent();
    }

    /**
     * Method getBytesFromTextChild
     *
     * <p>
     *  方法getBytesFromTextChild
     * 
     * 
     * @return The base64 bytes from the text children of this element
     * @throws XMLSecurityException
     */
    public byte[] getBytesFromTextChild() throws XMLSecurityException {
        return Base64.decode(XMLUtils.getFullTextChildrenFromElement(this.constructionElement));
    }

    /**
     * Method getTextFromTextChild
     *
     * <p>
     *  方法getTextFromTextChild
     * 
     * 
     * @return the Text obtained by concatenating all the text nodes of this
     *    element
     */
    public String getTextFromTextChild() {
        return XMLUtils.getFullTextChildrenFromElement(this.constructionElement);
    }

    /**
     * Method length
     *
     * <p>
     *  方法长度
     * 
     * 
     * @param namespace
     * @param localname
     * @return the number of elements {namespace}:localname under this element
     */
    public int length(String namespace, String localname) {
        int number = 0;
        Node sibling = this.constructionElement.getFirstChild();
        while (sibling != null) {
            if (localname.equals(sibling.getLocalName())
                && namespace.equals(sibling.getNamespaceURI())) {
                number++;
            }
            sibling = sibling.getNextSibling();
        }
        return number;
    }

    /**
     * Adds an xmlns: definition to the Element. This can be called as follows:
     *
     * <PRE>
     * // set namespace with ds prefix
     * xpathContainer.setXPathNamespaceContext("ds", "http://www.w3.org/2000/09/xmldsig#");
     * xpathContainer.setXPathNamespaceContext("xmlns:ds", "http://www.w3.org/2000/09/xmldsig#");
     * </PRE>
     *
     * <p>
     *  向元素添加xmlns：定义。这可以被称为如下：
     * 
     * <PRE>
     *  // set namespace with ds prefix xpathContainer.setXPathNamespaceContext("ds","http://www.w3.org/2000
     * /09/xmldsig#"); xpathContainer.setXPathNamespaceContext("xmlns：ds","http://www.w3.org/2000/09/xmldsig
     * #");。
     * </PRE>
     * 
     * 
     * @param prefix
     * @param uri
     * @throws XMLSecurityException
     */
    public void setXPathNamespaceContext(String prefix, String uri)
        throws XMLSecurityException {
        String ns;

        if ((prefix == null) || (prefix.length() == 0)) {
            throw new XMLSecurityException("defaultNamespaceCannotBeSetHere");
        } else if (prefix.equals("xmlns")) {
            throw new XMLSecurityException("defaultNamespaceCannotBeSetHere");
        } else if (prefix.startsWith("xmlns:")) {
            ns = prefix;//"xmlns:" + prefix.substring("xmlns:".length());
        } else {
            ns = "xmlns:" + prefix;
        }

        Attr a = this.constructionElement.getAttributeNodeNS(Constants.NamespaceSpecNS, ns);

        if (a != null) {
            if (!a.getNodeValue().equals(uri)) {
                Object exArgs[] = { ns, this.constructionElement.getAttributeNS(null, ns) };

                throw new XMLSecurityException("namespacePrefixAlreadyUsedByOtherURI", exArgs);
            }
            return;
        }

        this.constructionElement.setAttributeNS(Constants.NamespaceSpecNS, ns, uri);
    }

    /**
     * Method setDefaultPrefix
     *
     * <p>
     *  方法setDefaultPrefix
     * 
     * 
     * @param namespace
     * @param prefix
     * @throws XMLSecurityException
     * @throws SecurityException if a security manager is installed and the
     *    caller does not have permission to set the default prefix
     */
    public static void setDefaultPrefix(String namespace, String prefix)
        throws XMLSecurityException {
        JavaUtils.checkRegisterPermission();
        if (prefixMappings.containsValue(prefix)) {
            String storedPrefix = prefixMappings.get(namespace);
            if (!storedPrefix.equals(prefix)) {
                Object exArgs[] = { prefix, namespace, storedPrefix };

                throw new XMLSecurityException("prefix.AlreadyAssigned", exArgs);
            }
        }

        if (Constants.SignatureSpecNS.equals(namespace)) {
            XMLUtils.setDsPrefix(prefix);
        }
        if (EncryptionConstants.EncryptionSpecNS.equals(namespace)) {
            XMLUtils.setXencPrefix(prefix);
        }
        prefixMappings.put(namespace, prefix);
    }

    /**
     * This method registers the default prefixes.
     * <p>
     *  此方法注册默认前缀。
     * 
     */
    public static void registerDefaultPrefixes() throws XMLSecurityException {
        setDefaultPrefix("http://www.w3.org/2000/09/xmldsig#", "ds");
        setDefaultPrefix("http://www.w3.org/2001/04/xmlenc#", "xenc");
        setDefaultPrefix("http://www.w3.org/2009/xmlenc11#", "xenc11");
        setDefaultPrefix("http://www.xmlsecurity.org/experimental#", "experimental");
        setDefaultPrefix("http://www.w3.org/2002/04/xmldsig-filter2", "dsig-xpath-old");
        setDefaultPrefix("http://www.w3.org/2002/06/xmldsig-filter2", "dsig-xpath");
        setDefaultPrefix("http://www.w3.org/2001/10/xml-exc-c14n#", "ec");
        setDefaultPrefix(
            "http://www.nue.et-inf.uni-siegen.de/~geuer-pollmann/#xpathFilter", "xx"
        );
    }

    /**
     * Method getDefaultPrefix
     *
     * <p>
     *  方法getDefaultPrefix
     * 
     * @param namespace
     * @return the default prefix bind to this element.
     */
    public static String getDefaultPrefix(String namespace) {
        return prefixMappings.get(namespace);
    }

}
