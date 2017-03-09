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
package com.sun.org.apache.xml.internal.security.algorithms;

import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import com.sun.org.apache.xml.internal.security.signature.XMLSignatureException;
import org.w3c.dom.Element;

public abstract class SignatureAlgorithmSpi {

    /**
     * Returns the URI representation of <code>Transformation algorithm</code>
     *
     * <p>
     *  返回<code> Transformation algorithm </code>的URI表示形式
     * 
     * 
     * @return the URI representation of <code>Transformation algorithm</code>
     */
    protected abstract String engineGetURI();

    /**
     * Proxy method for {@link java.security.Signature#getAlgorithm}
     * which is executed on the internal {@link java.security.Signature} object.
     *
     * <p>
     *  对在内部{@link java.security.Signature}对象上执行的{@link java.security.Signature#getAlgorithm}的代理方法。
     * 
     * 
     * @return the result of the {@link java.security.Signature#getAlgorithm} method
     */
    protected abstract String engineGetJCEAlgorithmString();

    /**
     * Method engineGetJCEProviderName
     *
     * <p>
     *  方法engineGetJCEProviderName
     * 
     * 
     * @return the JCE ProviderName
     */
    protected abstract String engineGetJCEProviderName();

    /**
     * Proxy method for {@link java.security.Signature#update(byte[])}
     * which is executed on the internal {@link java.security.Signature} object.
     *
     * <p>
     *  对在内部{@link java.security.Signature}对象上执行的{@link java.security.Signature#update(byte [])}的代理方法。
     * 
     * 
     * @param input
     * @throws XMLSignatureException
     */
    protected abstract void engineUpdate(byte[] input) throws XMLSignatureException;

    /**
     * Proxy method for {@link java.security.Signature#update(byte[])}
     * which is executed on the internal {@link java.security.Signature} object.
     *
     * <p>
     *  对在内部{@link java.security.Signature}对象上执行的{@link java.security.Signature#update(byte [])}的代理方法。
     * 
     * 
     * @param input
     * @throws XMLSignatureException
     */
    protected abstract void engineUpdate(byte input) throws XMLSignatureException;

    /**
     * Proxy method for {@link java.security.Signature#update(byte[], int, int)}
     * which is executed on the internal {@link java.security.Signature} object.
     *
     * <p>
     * 在内部{@link java.security.Signature}对象上执行的{@link java.security.Signature#update(byte [],int,int)的代理方法。
     * 
     * 
     * @param buf
     * @param offset
     * @param len
     * @throws XMLSignatureException
     */
    protected abstract void engineUpdate(byte buf[], int offset, int len)
        throws XMLSignatureException;

    /**
     * Proxy method for {@link java.security.Signature#initSign(java.security.PrivateKey)}
     * which is executed on the internal {@link java.security.Signature} object.
     *
     * <p>
     *  对在内部{@link java.security.Signature}对象上执行的{@link java.security.Signature#initSign(java.security.PrivateKey)}
     * 的代理方法。
     * 
     * 
     * @param signingKey
     * @throws XMLSignatureException if this method is called on a MAC
     */
    protected abstract void engineInitSign(Key signingKey) throws XMLSignatureException;

    /**
     * Proxy method for {@link java.security.Signature#initSign(java.security.PrivateKey,
     * java.security.SecureRandom)}
     * which is executed on the internal {@link java.security.Signature} object.
     *
     * <p>
     *  对于在内部{@link java.security.Signature}对象上执行的{@link java.security.Signature#initSign(java.security.PrivateKey,java.security.SecureRandom)}
     * 的代理方法。
     * 
     * 
     * @param signingKey
     * @param secureRandom
     * @throws XMLSignatureException if this method is called on a MAC
     */
    protected abstract void engineInitSign(Key signingKey, SecureRandom secureRandom)
        throws XMLSignatureException;

    /**
     * Proxy method for {@link javax.crypto.Mac}
     * which is executed on the internal {@link javax.crypto.Mac#init(Key)} object.
     *
     * <p>
     *  {@link javax.crypto.Mac}的代理方法,它在内部{@link javax.crypto.Mac#init(Key)}对象上执行。
     * 
     * 
     * @param signingKey
     * @param algorithmParameterSpec
     * @throws XMLSignatureException if this method is called on a Signature
     */
    protected abstract void engineInitSign(
        Key signingKey, AlgorithmParameterSpec algorithmParameterSpec
    ) throws XMLSignatureException;

    /**
     * Proxy method for {@link java.security.Signature#sign()}
     * which is executed on the internal {@link java.security.Signature} object.
     *
     * <p>
     *  对在内部{@link java.security.Signature}对象上执行的{@link java.security.Signature#sign()}的代理方法。
     * 
     * 
     * @return the result of the {@link java.security.Signature#sign()} method
     * @throws XMLSignatureException
     */
    protected abstract byte[] engineSign() throws XMLSignatureException;

    /**
     * Method engineInitVerify
     *
     * <p>
     *  方法engineInitVerify
     * 
     * 
     * @param verificationKey
     * @throws XMLSignatureException
     */
    protected abstract void engineInitVerify(Key verificationKey) throws XMLSignatureException;

    /**
     * Proxy method for {@link java.security.Signature#verify(byte[])}
     * which is executed on the internal {@link java.security.Signature} object.
     *
     * <p>
     *  对在内部{@link java.security.Signature}对象上执行的{@link java.security.Signature#verify(byte [])}的代理方法。
     * 
     * 
     * @param signature
     * @return true if the signature is correct
     * @throws XMLSignatureException
     */
    protected abstract boolean engineVerify(byte[] signature) throws XMLSignatureException;

    /**
     * Proxy method for {@link java.security.Signature#setParameter(
     * java.security.spec.AlgorithmParameterSpec)}
     * which is executed on the internal {@link java.security.Signature} object.
     *
     * <p>
     *  在内部{@link java.security.Signature}对象上执行的{@link java.security.Signature#setParameter(java.security.spec.AlgorithmParameterSpec)}
     * 的代理方法。
     * 
     * 
     * @param params
     * @throws XMLSignatureException
     */
    protected abstract void engineSetParameter(AlgorithmParameterSpec params)
        throws XMLSignatureException;


    /**
     * Method engineGetContextFromElement
     *
     * <p>
     *  方法engineGetContextFromElement
     * 
     * 
     * @param element
     */
    protected void engineGetContextFromElement(Element element) {
    }

    /**
     * Method engineSetHMACOutputLength
     *
     * <p>
     *  方法engineSetHMACOutputLength
     * 
     * @param HMACOutputLength
     * @throws XMLSignatureException
     */
    protected abstract void engineSetHMACOutputLength(int HMACOutputLength)
        throws XMLSignatureException;

    public void reset() {
    }
}
