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
import java.io.OutputStream;
import java.util.Set;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Base class which all Canonicalization algorithms extend.
 *
 * <p>
 *  所有规范化算法扩展的基类。
 * 
 * 
 * @author Christian Geuer-Pollmann
 */
public abstract class CanonicalizerSpi {

    /** Reset the writer after a c14n */
    protected boolean reset = false;

    /**
     * Method canonicalize
     *
     * <p>
     *  方法规范化
     * 
     * 
     * @param inputBytes
     * @return the c14n bytes.
     *
     * @throws CanonicalizationException
     * @throws java.io.IOException
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws org.xml.sax.SAXException
     */
    public byte[] engineCanonicalize(byte[] inputBytes)
        throws javax.xml.parsers.ParserConfigurationException, java.io.IOException,
        org.xml.sax.SAXException, CanonicalizationException {

        java.io.InputStream bais = new ByteArrayInputStream(inputBytes);
        InputSource in = new InputSource(bais);
        DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
        dfactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, Boolean.TRUE);

        // needs to validate for ID attribute normalization
        dfactory.setNamespaceAware(true);

        DocumentBuilder db = dfactory.newDocumentBuilder();

        Document document = db.parse(in);
        return this.engineCanonicalizeSubTree(document);
    }

    /**
     * Method engineCanonicalizeXPathNodeSet
     *
     * <p>
     *  方法engineCanonicalizeXPathNodeSet
     * 
     * 
     * @param xpathNodeSet
     * @return the c14n bytes
     * @throws CanonicalizationException
     */
    public byte[] engineCanonicalizeXPathNodeSet(NodeList xpathNodeSet)
        throws CanonicalizationException {
        return this.engineCanonicalizeXPathNodeSet(
            XMLUtils.convertNodelistToSet(xpathNodeSet)
        );
    }

    /**
     * Method engineCanonicalizeXPathNodeSet
     *
     * <p>
     *  方法engineCanonicalizeXPathNodeSet
     * 
     * 
     * @param xpathNodeSet
     * @param inclusiveNamespaces
     * @return the c14n bytes
     * @throws CanonicalizationException
     */
    public byte[] engineCanonicalizeXPathNodeSet(NodeList xpathNodeSet, String inclusiveNamespaces)
        throws CanonicalizationException {
        return this.engineCanonicalizeXPathNodeSet(
            XMLUtils.convertNodelistToSet(xpathNodeSet), inclusiveNamespaces
        );
    }

    /**
     * Returns the URI of this engine.
     * <p>
     *  返回此引擎的URI。
     * 
     * 
     * @return the URI
     */
    public abstract String engineGetURI();

    /**
     * Returns true if comments are included
     * <p>
     *  如果包含注释,则返回true
     * 
     * 
     * @return true if comments are included
     */
    public abstract boolean engineGetIncludeComments();

    /**
     * C14n a nodeset
     *
     * <p>
     *  C14n节点集
     * 
     * 
     * @param xpathNodeSet
     * @return the c14n bytes
     * @throws CanonicalizationException
     */
    public abstract byte[] engineCanonicalizeXPathNodeSet(Set<Node> xpathNodeSet)
        throws CanonicalizationException;

    /**
     * C14n a nodeset
     *
     * <p>
     *  C14n节点集
     * 
     * 
     * @param xpathNodeSet
     * @param inclusiveNamespaces
     * @return the c14n bytes
     * @throws CanonicalizationException
     */
    public abstract byte[] engineCanonicalizeXPathNodeSet(
        Set<Node> xpathNodeSet, String inclusiveNamespaces
    ) throws CanonicalizationException;

    /**
     * C14n a node tree.
     *
     * <p>
     *  C14n是节点树。
     * 
     * 
     * @param rootNode
     * @return the c14n bytes
     * @throws CanonicalizationException
     */
    public abstract byte[] engineCanonicalizeSubTree(Node rootNode)
        throws CanonicalizationException;

    /**
     * C14n a node tree.
     *
     * <p>
     *  C14n是节点树。
     * 
     * 
     * @param rootNode
     * @param inclusiveNamespaces
     * @return the c14n bytes
     * @throws CanonicalizationException
     */
    public abstract byte[] engineCanonicalizeSubTree(Node rootNode, String inclusiveNamespaces)
        throws CanonicalizationException;

    /**
     * Sets the writer where the canonicalization ends. ByteArrayOutputStream if
     * none is set.
     * <p>
     *  设置规范化结束的刻录机。 ByteArrayOutputStream如果没有设置。
     * 
     * @param os
     */
    public abstract void setWriter(OutputStream os);

}
