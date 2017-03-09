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
package com.sun.org.apache.xml.internal.security.encryption;

import com.sun.org.apache.xml.internal.security.c14n.Canonicalizer;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Converts <code>String</code>s into <code>Node</code>s and visa versa.
 * <p>
 *  将<code> String </code>转换为<code> Node </code>,反之亦然。
 * 
 */
public interface Serializer {

    /**
     * Set the Canonicalizer object to use.
     * <p>
     *  设置要使用的Canonicalizer对象。
     * 
     */
    void setCanonicalizer(Canonicalizer canon);

    /**
     * Returns a <code>byte[]</code> representation of the specified
     * <code>Element</code>.
     *
     * <p>
     *  返回指定的<code> Element </code>的<code> byte [] </code>表示。
     * 
     * 
     * @param element the <code>Element</code> to serialize.
     * @return the <code>byte[]</code> representation of the serilaized
     *   <code>Element</code>.
     * @throws Exception
     */
    byte[] serializeToByteArray(Element element) throws Exception;

    /**
     * Returns a <code>byte[]</code> representation of the specified
     * <code>NodeList</code>.
     *
     * <p>
     *  返回指定的<code> NodeList </code>的<code> byte [] </code>表示。
     * 
     * 
     * @param content the <code>NodeList</code> to serialize.
     * @return the <code>byte[]</code> representation of the serialized
     *   <code>NodeList</code>.
     * @throws Exception
     */
    byte[] serializeToByteArray(NodeList content) throws Exception;

    /**
     * Use the Canonicalizer to serialize the node
     * <p>
     *  使用Canonicalizer来序列化节点
     * 
     * 
     * @param node
     * @return the (byte[]) canonicalization of the node
     * @throws Exception
     */
    byte[] canonSerializeToByteArray(Node node) throws Exception;

    /**
    /* <p>
    /* 
     * @param source
     * @param ctx
     * @return the Node resulting from the parse of the source
     * @throws XMLEncryptionException
     */
    Node deserialize(byte[] source, Node ctx) throws XMLEncryptionException;
}
