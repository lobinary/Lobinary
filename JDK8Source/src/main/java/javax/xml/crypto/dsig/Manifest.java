/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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
/*
 * $Id: Manifest.java,v 1.7 2005/05/10 16:03:46 mullan Exp $
 * <p>
 *  $ Id：Manifest.java,v 1.7 2005/05/10 16:03:46 mullan Exp $
 * 
 */
package javax.xml.crypto.dsig;

import javax.xml.crypto.XMLStructure;
import java.util.List;

/**
 * A representation of the XML <code>Manifest</code> element as defined in
 * the <a href="http://www.w3.org/TR/xmldsig-core/">
 * W3C Recommendation for XML-Signature Syntax and Processing</a>.
 * The XML Schema Definition is defined as:
 * <pre>{@code
 * <element name="Manifest" type="ds:ManifestType"/>
 *   <complexType name="ManifestType">
 *     <sequence>
 *       <element ref="ds:Reference" maxOccurs="unbounded"/>
 *     </sequence>
 *     <attribute name="Id" type="ID" use="optional"/>
 *   </complexType>
 * }</pre>
 *
 * A <code>Manifest</code> instance may be created by invoking
 * one of the {@link XMLSignatureFactory#newManifest newManifest}
 * methods of the {@link XMLSignatureFactory} class; for example:
 *
 * <pre>
 *   XMLSignatureFactory factory = XMLSignatureFactory.getInstance("DOM");
 *   List references = Collections.singletonList(factory.newReference
 *       ("#reference-1", DigestMethod.SHA1));
 *   Manifest manifest = factory.newManifest(references, "manifest-1");
 * </pre>
 *
 * <p>
 *  <a href="http://www.w3.org/TR/xmldsig-core/"> XML签名语法和处理的W3C建议书中定义的XML <code> Manifest </code>元素的表示形
 * 式</a>。
 *  XML模式定义定义为：<pre> {@ code。
 * <element name="Manifest" type="ds:ManifestType"/>
 * <complexType name="ManifestType">
 * <sequence>
 * <element ref="ds:Reference" maxOccurs="unbounded"/>
 * </sequence>
 * <attribute name="Id" type="ID" use="optional"/>
 * </complexType>
 *  } </pre>
 * 
 *  可以通过调用{@link XMLSignatureFactory}类的{@link XMLSignatureFactory#newManifest newManifest}方法之一创建<code> M
 * anifest </code>实例;例如：。
 * 
 * <pre>
 * 
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 * @since 1.6
 * @see XMLSignatureFactory#newManifest(List)
 * @see XMLSignatureFactory#newManifest(List, String)
 */
public interface Manifest extends XMLStructure {

    /**
     * URI that identifies the <code>Manifest</code> element (this can be
     * specified as the value of the <code>type</code> parameter of the
     * {@link Reference} class to identify the referent's type).
     * <p>
     *  XMLSignatureFactory factory = XMLSignatureFactory.getInstance("DOM"); List references = Collections.
     * singletonList(factory.newReference("#reference-1",DigestMethod.SHA1));清单manifest = factory.newManifes
     * t(引用,"manifest-1");。
     * </pre>
     * 
     */
    final static String TYPE = "http://www.w3.org/2000/09/xmldsig#Manifest";

    /**
     * Returns the Id of this <code>Manifest</code>.
     *
     * <p>
     *  标识<code> Manifest </code>元素的URI(可以将其指定为{@link Reference}类的<code> type </code>参数的值,以标识引用对象的类型)。
     * 
     * 
     * @return the Id  of this <code>Manifest</code> (or <code>null</code>
     *    if not specified)
     */
    String getId();

    /**
     * Returns an {@link java.util.Collections#unmodifiableList unmodifiable
     * list} of one or more {@link Reference}s that are contained in this
     * <code>Manifest</code>.
     *
     * <p>
     *  返回此<code> Manifest </code>的Id。
     * 
     * 
     * @return an unmodifiable list of one or more <code>Reference</code>s
     */
    @SuppressWarnings("rawtypes")
    List getReferences();
}
