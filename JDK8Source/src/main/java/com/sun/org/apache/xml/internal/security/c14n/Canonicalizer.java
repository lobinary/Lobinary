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
package com.sun.org.apache.xml.internal.security.c14n;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer11_OmitComments;
import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer11_WithComments;
import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer20010315ExclOmitComments;
import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer20010315ExclWithComments;
import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer20010315OmitComments;
import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer20010315WithComments;
import com.sun.org.apache.xml.internal.security.c14n.implementations.CanonicalizerPhysical;
import com.sun.org.apache.xml.internal.security.exceptions.AlgorithmAlreadyRegisteredException;
import com.sun.org.apache.xml.internal.security.utils.JavaUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * <p>
 * 
 * @author Christian Geuer-Pollmann
 */
public class Canonicalizer {

    /** The output encoding of canonicalized data */
    public static final String ENCODING = "UTF8";

    /**
     * XPath Expression for selecting every node and continuous comments joined
     * in only one node
     * <p>
     *  XPath表达式,用于选择只在一个节点中连接的每个节点和连续注释
     * 
     */
    public static final String XPATH_C14N_WITH_COMMENTS_SINGLE_NODE =
        "(.//. | .//@* | .//namespace::*)";

    /**
     * The URL defined in XML-SEC Rec for inclusive c14n <b>without</b> comments.
     * <p>
     *  在XML-SEC Rec中为包含c14n <b>而不包含</b>注释定义的URL。
     * 
     */
    public static final String ALGO_ID_C14N_OMIT_COMMENTS =
        "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";
    /**
     * The URL defined in XML-SEC Rec for inclusive c14n <b>with</b> comments.
     * <p>
     *  在XML-SEC Rec中为包含c14n <b>和</b>注释定义的URL。
     * 
     */
    public static final String ALGO_ID_C14N_WITH_COMMENTS =
        ALGO_ID_C14N_OMIT_COMMENTS + "#WithComments";
    /**
     * The URL defined in XML-SEC Rec for exclusive c14n <b>without</b> comments.
     * <p>
     *  在XML-SEC Rec中为排除c14n <b>而不包含</b>注释定义的URL。
     * 
     */
    public static final String ALGO_ID_C14N_EXCL_OMIT_COMMENTS =
        "http://www.w3.org/2001/10/xml-exc-c14n#";
    /**
     * The URL defined in XML-SEC Rec for exclusive c14n <b>with</b> comments.
     * <p>
     *  在XML-SEC Rec中为独占c14n <b>和</b>注释定义的URL。
     * 
     */
    public static final String ALGO_ID_C14N_EXCL_WITH_COMMENTS =
        ALGO_ID_C14N_EXCL_OMIT_COMMENTS + "WithComments";
    /**
     * The URI for inclusive c14n 1.1 <b>without</b> comments.
     * <p>
     *  包含c14n 1.1 <b>的URI,不含</b>注释。
     * 
     */
    public static final String ALGO_ID_C14N11_OMIT_COMMENTS =
        "http://www.w3.org/2006/12/xml-c14n11";
    /**
     * The URI for inclusive c14n 1.1 <b>with</b> comments.
     * <p>
     *  包含c14n 1.1 <b>与</b>注释的URI。
     * 
     */
    public static final String ALGO_ID_C14N11_WITH_COMMENTS =
        ALGO_ID_C14N11_OMIT_COMMENTS + "#WithComments";
    /**
     * Non-standard algorithm to serialize the physical representation for XML Encryption
     * <p>
     *  非标准算法来序列化XML加密的物理表示
     * 
     */
    public static final String ALGO_ID_C14N_PHYSICAL =
        "http://santuario.apache.org/c14n/physical";

    private static Map<String, Class<? extends CanonicalizerSpi>> canonicalizerHash =
        new ConcurrentHashMap<String, Class<? extends CanonicalizerSpi>>();

    private final CanonicalizerSpi canonicalizerSpi;

    /**
     * Constructor Canonicalizer
     *
     * <p>
     * 构造函数
     * 
     * 
     * @param algorithmURI
     * @throws InvalidCanonicalizerException
     */
    private Canonicalizer(String algorithmURI) throws InvalidCanonicalizerException {
        try {
            Class<? extends CanonicalizerSpi> implementingClass =
                canonicalizerHash.get(algorithmURI);

            canonicalizerSpi = implementingClass.newInstance();
            canonicalizerSpi.reset = true;
        } catch (Exception e) {
            Object exArgs[] = { algorithmURI };
            throw new InvalidCanonicalizerException(
                "signature.Canonicalizer.UnknownCanonicalizer", exArgs, e
            );
        }
    }

    /**
     * Method getInstance
     *
     * <p>
     *  方法getInstance
     * 
     * 
     * @param algorithmURI
     * @return a Canonicalizer instance ready for the job
     * @throws InvalidCanonicalizerException
     */
    public static final Canonicalizer getInstance(String algorithmURI)
        throws InvalidCanonicalizerException {
        return new Canonicalizer(algorithmURI);
    }

    /**
     * Method register
     *
     * <p>
     *  方法寄存器
     * 
     * 
     * @param algorithmURI
     * @param implementingClass
     * @throws AlgorithmAlreadyRegisteredException
     * @throws SecurityException if a security manager is installed and the
     *    caller does not have permission to register the canonicalizer
     */
    @SuppressWarnings("unchecked")
    public static void register(String algorithmURI, String implementingClass)
        throws AlgorithmAlreadyRegisteredException, ClassNotFoundException {
        JavaUtils.checkRegisterPermission();
        // check whether URI is already registered
        Class<? extends CanonicalizerSpi> registeredClass =
            canonicalizerHash.get(algorithmURI);

        if (registeredClass != null)  {
            Object exArgs[] = { algorithmURI, registeredClass };
            throw new AlgorithmAlreadyRegisteredException("algorithm.alreadyRegistered", exArgs);
        }

        canonicalizerHash.put(
            algorithmURI, (Class<? extends CanonicalizerSpi>)Class.forName(implementingClass)
        );
    }

    /**
     * Method register
     *
     * <p>
     *  方法寄存器
     * 
     * 
     * @param algorithmURI
     * @param implementingClass
     * @throws AlgorithmAlreadyRegisteredException
     * @throws SecurityException if a security manager is installed and the
     *    caller does not have permission to register the canonicalizer
     */
    public static void register(String algorithmURI, Class<? extends CanonicalizerSpi> implementingClass)
        throws AlgorithmAlreadyRegisteredException, ClassNotFoundException {
        JavaUtils.checkRegisterPermission();
        // check whether URI is already registered
        Class<? extends CanonicalizerSpi> registeredClass = canonicalizerHash.get(algorithmURI);

        if (registeredClass != null)  {
            Object exArgs[] = { algorithmURI, registeredClass };
            throw new AlgorithmAlreadyRegisteredException("algorithm.alreadyRegistered", exArgs);
        }

        canonicalizerHash.put(algorithmURI, implementingClass);
    }

    /**
     * This method registers the default algorithms.
     * <p>
     *  此方法注册默认算法。
     * 
     */
    public static void registerDefaultAlgorithms() {
        canonicalizerHash.put(
            Canonicalizer.ALGO_ID_C14N_OMIT_COMMENTS,
            Canonicalizer20010315OmitComments.class
        );
        canonicalizerHash.put(
            Canonicalizer.ALGO_ID_C14N_WITH_COMMENTS,
            Canonicalizer20010315WithComments.class
        );
        canonicalizerHash.put(
            Canonicalizer.ALGO_ID_C14N_EXCL_OMIT_COMMENTS,
            Canonicalizer20010315ExclOmitComments.class
        );
        canonicalizerHash.put(
            Canonicalizer.ALGO_ID_C14N_EXCL_WITH_COMMENTS,
            Canonicalizer20010315ExclWithComments.class
        );
        canonicalizerHash.put(
            Canonicalizer.ALGO_ID_C14N11_OMIT_COMMENTS,
            Canonicalizer11_OmitComments.class
        );
        canonicalizerHash.put(
            Canonicalizer.ALGO_ID_C14N11_WITH_COMMENTS,
            Canonicalizer11_WithComments.class
        );
        canonicalizerHash.put(
            Canonicalizer.ALGO_ID_C14N_PHYSICAL,
            CanonicalizerPhysical.class
        );
    }

    /**
     * Method getURI
     *
     * <p>
     *  方法getURI
     * 
     * 
     * @return the URI defined for this c14n instance.
     */
    public final String getURI() {
        return canonicalizerSpi.engineGetURI();
    }

    /**
     * Method getIncludeComments
     *
     * <p>
     *  方法getIncludeComments
     * 
     * 
     * @return true if the c14n respect the comments.
     */
    public boolean getIncludeComments() {
        return canonicalizerSpi.engineGetIncludeComments();
    }

    /**
     * This method tries to canonicalize the given bytes. It's possible to even
     * canonicalize non-wellformed sequences if they are well-formed after being
     * wrapped with a <CODE>&gt;a&lt;...&gt;/a&lt;</CODE>.
     *
     * <p>
     *  此方法尝试规范化给定的字节。如果它们在用<CODE>&gt; a&lt; ...&gt; / a&lt;&lt; / CODE&gt;包装之后形成良好,则可以甚至规范非成形序列。
     * 
     * 
     * @param inputBytes
     * @return the result of the canonicalization.
     * @throws CanonicalizationException
     * @throws java.io.IOException
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws org.xml.sax.SAXException
     */
    public byte[] canonicalize(byte[] inputBytes)
        throws javax.xml.parsers.ParserConfigurationException,
        java.io.IOException, org.xml.sax.SAXException, CanonicalizationException {
        InputStream bais = new ByteArrayInputStream(inputBytes);
        InputSource in = new InputSource(bais);
        DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
        dfactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, Boolean.TRUE);

        dfactory.setNamespaceAware(true);

        // needs to validate for ID attribute normalization
        dfactory.setValidating(true);

        DocumentBuilder db = dfactory.newDocumentBuilder();

        /*
         * for some of the test vectors from the specification,
         * there has to be a validating parser for ID attributes, default
         * attribute values, NMTOKENS, etc.
         * Unfortunately, the test vectors do use different DTDs or
         * even no DTD. So Xerces 1.3.1 fires many warnings about using
         * ErrorHandlers.
         *
         * Text from the spec:
         *
         * The input octet stream MUST contain a well-formed XML document,
         * but the input need not be validated. However, the attribute
         * value normalization and entity reference resolution MUST be
         * performed in accordance with the behaviors of a validating
         * XML processor. As well, nodes for default attributes (declared
         * in the ATTLIST with an AttValue but not specified) are created
         * in each element. Thus, the declarations in the document type
         * declaration are used to help create the canonical form, even
         * though the document type declaration is not retained in the
         * canonical form.
         * <p>
         *  对于来自规范的一些测试向量,必须有用于ID属性,默认属性值,NMTOKENS等的验证解析器。不幸的是,测试向量确实使用不同的DTD或甚至没有DTD。
         * 所以Xerces 1.3.1对使用ErrorHandlers触发了很多警告。
         * 
         *  规格文字：
         * 
         *  输入八位字节流必须包含格式良好的XML文档,但输入不需要验证。然而,属性值归一化和实体参考解析必须根据验证XML处理器的行为来执行。
         * 此外,在每个元素中创建默认属性的节点(在ATTLIST中用AttValue声明但未指定)。因此,文档类型声明中的声明用于帮助创建规范形式,即使文档类型声明没有保留在规范形式中。
         * 
         */
        db.setErrorHandler(new com.sun.org.apache.xml.internal.security.utils.IgnoreAllErrorHandler());

        Document document = db.parse(in);
        return this.canonicalizeSubtree(document);
    }

    /**
     * Canonicalizes the subtree rooted by <CODE>node</CODE>.
     *
     * <p>
     *  规范化以<CODE>节点</CODE>为根的子树。
     * 
     * 
     * @param node The node to canonicalize
     * @return the result of the c14n.
     *
     * @throws CanonicalizationException
     */
    public byte[] canonicalizeSubtree(Node node) throws CanonicalizationException {
        return canonicalizerSpi.engineCanonicalizeSubTree(node);
    }

    /**
     * Canonicalizes the subtree rooted by <CODE>node</CODE>.
     *
     * <p>
     *  规范化以<CODE>节点</CODE>为根的子树。
     * 
     * 
     * @param node
     * @param inclusiveNamespaces
     * @return the result of the c14n.
     * @throws CanonicalizationException
     */
    public byte[] canonicalizeSubtree(Node node, String inclusiveNamespaces)
        throws CanonicalizationException {
        return canonicalizerSpi.engineCanonicalizeSubTree(node, inclusiveNamespaces);
    }

    /**
     * Canonicalizes an XPath node set. The <CODE>xpathNodeSet</CODE> is treated
     * as a list of XPath nodes, not as a list of subtrees.
     *
     * <p>
     *  规范化XPath节点集。 <CODE> xpathNodeSet </CODE>被视为XPath节点列表,而不是子树列表。
     * 
     * 
     * @param xpathNodeSet
     * @return the result of the c14n.
     * @throws CanonicalizationException
     */
    public byte[] canonicalizeXPathNodeSet(NodeList xpathNodeSet)
        throws CanonicalizationException {
        return canonicalizerSpi.engineCanonicalizeXPathNodeSet(xpathNodeSet);
    }

    /**
     * Canonicalizes an XPath node set. The <CODE>xpathNodeSet</CODE> is treated
     * as a list of XPath nodes, not as a list of subtrees.
     *
     * <p>
     * 规范化XPath节点集。 <CODE> xpathNodeSet </CODE>被视为XPath节点列表,而不是子树列表。
     * 
     * 
     * @param xpathNodeSet
     * @param inclusiveNamespaces
     * @return the result of the c14n.
     * @throws CanonicalizationException
     */
    public byte[] canonicalizeXPathNodeSet(
        NodeList xpathNodeSet, String inclusiveNamespaces
    ) throws CanonicalizationException {
        return
            canonicalizerSpi.engineCanonicalizeXPathNodeSet(xpathNodeSet, inclusiveNamespaces);
    }

    /**
     * Canonicalizes an XPath node set.
     *
     * <p>
     *  规范化XPath节点集。
     * 
     * 
     * @param xpathNodeSet
     * @return the result of the c14n.
     * @throws CanonicalizationException
     */
    public byte[] canonicalizeXPathNodeSet(Set<Node> xpathNodeSet)
        throws CanonicalizationException {
        return canonicalizerSpi.engineCanonicalizeXPathNodeSet(xpathNodeSet);
    }

    /**
     * Canonicalizes an XPath node set.
     *
     * <p>
     *  规范化XPath节点集。
     * 
     * 
     * @param xpathNodeSet
     * @param inclusiveNamespaces
     * @return the result of the c14n.
     * @throws CanonicalizationException
     */
    public byte[] canonicalizeXPathNodeSet(
        Set<Node> xpathNodeSet, String inclusiveNamespaces
    ) throws CanonicalizationException {
        return
            canonicalizerSpi.engineCanonicalizeXPathNodeSet(xpathNodeSet, inclusiveNamespaces);
    }

    /**
     * Sets the writer where the canonicalization ends.  ByteArrayOutputStream
     * if none is set.
     * <p>
     *  设置规范化结束的刻录机。 ByteArrayOutputStream如果没有设置。
     * 
     * 
     * @param os
     */
    public void setWriter(OutputStream os) {
        canonicalizerSpi.setWriter(os);
    }

    /**
     * Returns the name of the implementing {@link CanonicalizerSpi} class
     *
     * <p>
     *  返回实现的{@link CanonicalizerSpi}类的名称
     * 
     * 
     * @return the name of the implementing {@link CanonicalizerSpi} class
     */
    public String getImplementingCanonicalizerClass() {
        return canonicalizerSpi.getClass().getName();
    }

    /**
     * Set the canonicalizer behaviour to not reset.
     * <p>
     *  将规范器行为设置为不重置。
     */
    public void notReset() {
        canonicalizerSpi.reset = false;
    }

}
