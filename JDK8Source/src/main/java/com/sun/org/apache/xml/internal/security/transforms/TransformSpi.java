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
package com.sun.org.apache.xml.internal.security.transforms;

import java.io.IOException;
import java.io.OutputStream;
import javax.xml.parsers.ParserConfigurationException;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import org.xml.sax.SAXException;

/**
 * Base class which all Transform algorithms extend. The common methods that
 * have to be overridden are the
 * {@link #enginePerformTransform(XMLSignatureInput, Transform)} method.
 *
 * <p>
 *  所有变换算法扩展的基类。必须覆盖的常见方法是{@link #enginePerformTransform(XMLSignatureInput,Transform)}方法。
 * 
 * 
 * @author Christian Geuer-Pollmann
 */
public abstract class TransformSpi {

    /**
     * The mega method which MUST be implemented by the Transformation Algorithm.
     *
     * <p>
     *  必须通过变换算法实现的巨型方法。
     * 
     * 
     * @param input {@link XMLSignatureInput} as the input of transformation
     * @param os where to output this transformation.
     * @param transformObject the Transform object
     * @return {@link XMLSignatureInput} as the result of transformation
     * @throws CanonicalizationException
     * @throws IOException
     * @throws InvalidCanonicalizerException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws TransformationException
     */
    protected XMLSignatureInput enginePerformTransform(
        XMLSignatureInput input, OutputStream os, Transform transformObject
    ) throws IOException, CanonicalizationException, InvalidCanonicalizerException,
        TransformationException, ParserConfigurationException, SAXException {
        throw new UnsupportedOperationException();
    }

    /**
     * The mega method which MUST be implemented by the Transformation Algorithm.
     * In order to be compatible with preexisting Transform implementations,
     * by default this implementation invokes the deprecated, thread-unsafe
     * methods. Subclasses should override this with a thread-safe
     * implementation.
     *
     * <p>
     *  必须通过变换算法实现的巨型方法。为了与预先存在的Transform实现兼容,默认情况下,此实现调用已弃用的线程不安全方法。子类应该使用线程安全的实现来覆盖它。
     * 
     * 
     * @param input {@link XMLSignatureInput} as the input of transformation
     * @param transformObject the Transform object
     * @return {@link XMLSignatureInput} as the result of transformation
     * @throws CanonicalizationException
     * @throws IOException
     * @throws InvalidCanonicalizerException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws TransformationException
     */
    protected XMLSignatureInput enginePerformTransform(
        XMLSignatureInput input, Transform transformObject
    ) throws IOException, CanonicalizationException, InvalidCanonicalizerException,
        TransformationException, ParserConfigurationException, SAXException {
        return enginePerformTransform(input, null, transformObject);
    }

    /**
     * The mega method which MUST be implemented by the Transformation Algorithm.
     * <p>
     * 必须通过变换算法实现的巨型方法。
     * 
     * 
     * @param input {@link XMLSignatureInput} as the input of transformation
     * @return {@link XMLSignatureInput} as the result of transformation
     * @throws CanonicalizationException
     * @throws IOException
     * @throws InvalidCanonicalizerException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws TransformationException
     */
    protected XMLSignatureInput enginePerformTransform(
        XMLSignatureInput input
    ) throws IOException, CanonicalizationException, InvalidCanonicalizerException,
        TransformationException, ParserConfigurationException, SAXException {
        return enginePerformTransform(input, null);
    }

    /**
     * Returns the URI representation of <code>Transformation algorithm</code>
     *
     * <p>
     *  返回<code> Transformation algorithm </code>的URI表示形式
     * 
     * @return the URI representation of <code>Transformation algorithm</code>
     */
    protected abstract String engineGetURI();
}
