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

import java.security.MessageDigest;
import java.security.NoSuchProviderException;

import com.sun.org.apache.xml.internal.security.signature.XMLSignatureException;
import com.sun.org.apache.xml.internal.security.utils.Constants;
import com.sun.org.apache.xml.internal.security.utils.EncryptionConstants;
import org.w3c.dom.Document;

/**
 * Digest Message wrapper & selector class.
 *
 * <pre>
 * MessageDigestAlgorithm.getInstance()
 * </pre>
 * <p>
 *  摘要消息包装器和选择器类。
 * 
 * <pre>
 *  MessageDigestAlgorithm.getInstance()
 * </pre>
 */
public class MessageDigestAlgorithm extends Algorithm {

    /** Message Digest - NOT RECOMMENDED MD5*/
    public static final String ALGO_ID_DIGEST_NOT_RECOMMENDED_MD5 =
        Constants.MoreAlgorithmsSpecNS + "md5";
    /** Digest - Required SHA1*/
    public static final String ALGO_ID_DIGEST_SHA1 = Constants.SignatureSpecNS + "sha1";
    /** Message Digest - RECOMMENDED SHA256*/
    public static final String ALGO_ID_DIGEST_SHA256 =
        EncryptionConstants.EncryptionSpecNS + "sha256";
    /** Message Digest - OPTIONAL SHA384*/
    public static final String ALGO_ID_DIGEST_SHA384 =
        Constants.MoreAlgorithmsSpecNS + "sha384";
    /** Message Digest - OPTIONAL SHA512*/
    public static final String ALGO_ID_DIGEST_SHA512 =
        EncryptionConstants.EncryptionSpecNS + "sha512";
    /** Message Digest - OPTIONAL RIPEMD-160*/
    public static final String ALGO_ID_DIGEST_RIPEMD160 =
        EncryptionConstants.EncryptionSpecNS + "ripemd160";

    /** Field algorithm stores the actual {@link java.security.MessageDigest} */
    private final MessageDigest algorithm;

    /**
     * Constructor for the brave who pass their own message digest algorithms and the
     * corresponding URI.
     * <p>
     *  构造者为勇敢者通过自己的消息摘要算法和相应的URI。
     * 
     * 
     * @param doc
     * @param algorithmURI
     */
    private MessageDigestAlgorithm(Document doc, String algorithmURI)
        throws XMLSignatureException {
        super(doc, algorithmURI);

        algorithm = getDigestInstance(algorithmURI);
    }

    /**
     * Factory method for constructing a message digest algorithm by name.
     *
     * <p>
     *  用名称构造消息摘要算法的工厂方法。
     * 
     * 
     * @param doc
     * @param algorithmURI
     * @return The MessageDigestAlgorithm element to attach in document and to digest
     * @throws XMLSignatureException
     */
    public static MessageDigestAlgorithm getInstance(
        Document doc, String algorithmURI
    ) throws XMLSignatureException {
        return new MessageDigestAlgorithm(doc, algorithmURI);
    }

    private static MessageDigest getDigestInstance(String algorithmURI) throws XMLSignatureException {
        String algorithmID = JCEMapper.translateURItoJCEID(algorithmURI);

        if (algorithmID == null) {
            Object[] exArgs = { algorithmURI };
            throw new XMLSignatureException("algorithms.NoSuchMap", exArgs);
        }

        MessageDigest md;
        String provider = JCEMapper.getProviderId();
        try {
            if (provider == null) {
                md = MessageDigest.getInstance(algorithmID);
            } else {
                md = MessageDigest.getInstance(algorithmID, provider);
            }
        } catch (java.security.NoSuchAlgorithmException ex) {
            Object[] exArgs = { algorithmID, ex.getLocalizedMessage() };

            throw new XMLSignatureException("algorithms.NoSuchAlgorithm", exArgs);
        } catch (NoSuchProviderException ex) {
            Object[] exArgs = { algorithmID, ex.getLocalizedMessage() };

            throw new XMLSignatureException("algorithms.NoSuchAlgorithm", exArgs);
        }

        return md;
    }

    /**
     * Returns the actual {@link java.security.MessageDigest} algorithm object
     *
     * <p>
     *  返回实际的{@link java.security.MessageDigest}算法对象
     * 
     * 
     * @return the actual {@link java.security.MessageDigest} algorithm object
     */
    public java.security.MessageDigest getAlgorithm() {
        return algorithm;
    }

    /**
     * Proxy method for {@link java.security.MessageDigest#isEqual}
     * which is executed on the internal {@link java.security.MessageDigest} object.
     *
     * <p>
     *  对于在内部{@link java.security.MessageDigest}对象上执行的{@link java.security.MessageDigest#isEqual}的代理方法。
     * 
     * 
     * @param digesta
     * @param digestb
     * @return the result of the {@link java.security.MessageDigest#isEqual} method
     */
    public static boolean isEqual(byte[] digesta, byte[] digestb) {
        return java.security.MessageDigest.isEqual(digesta, digestb);
    }

    /**
     * Proxy method for {@link java.security.MessageDigest#digest()}
     * which is executed on the internal {@link java.security.MessageDigest} object.
     *
     * <p>
     *  对在内部{@link java.security.MessageDigest}对象上执行的{@link java.security.MessageDigest#digest()}的代理方法。
     * 
     * 
     * @return the result of the {@link java.security.MessageDigest#digest()} method
     */
    public byte[] digest() {
        return algorithm.digest();
    }

    /**
     * Proxy method for {@link java.security.MessageDigest#digest(byte[])}
     * which is executed on the internal {@link java.security.MessageDigest} object.
     *
     * <p>
     * 对于在内部{@link java.security.MessageDigest}对象上执行的{@link java.security.MessageDigest#digest(byte [])}的代理方
     * 法。
     * 
     * 
     * @param input
     * @return the result of the {@link java.security.MessageDigest#digest(byte[])} method
     */
    public byte[] digest(byte input[]) {
        return algorithm.digest(input);
    }

    /**
     * Proxy method for {@link java.security.MessageDigest#digest(byte[], int, int)}
     * which is executed on the internal {@link java.security.MessageDigest} object.
     *
     * <p>
     *  对于在内部{@link java.security.MessageDigest}对象上执行的{@link java.security.MessageDigest#digest(byte [],int,int)}
     * 的代理方法。
     * 
     * 
     * @param buf
     * @param offset
     * @param len
     * @return the result of the {@link java.security.MessageDigest#digest(byte[], int, int)} method
     * @throws java.security.DigestException
     */
    public int digest(byte buf[], int offset, int len) throws java.security.DigestException {
        return algorithm.digest(buf, offset, len);
    }

    /**
     * Proxy method for {@link java.security.MessageDigest#getAlgorithm}
     * which is executed on the internal {@link java.security.MessageDigest} object.
     *
     * <p>
     *  对于在内部{@link java.security.MessageDigest}对象上执行的{@link java.security.MessageDigest#getAlgorithm}的代理方法。
     * 
     * 
     * @return the result of the {@link java.security.MessageDigest#getAlgorithm} method
     */
    public String getJCEAlgorithmString() {
        return algorithm.getAlgorithm();
    }

    /**
     * Proxy method for {@link java.security.MessageDigest#getProvider}
     * which is executed on the internal {@link java.security.MessageDigest} object.
     *
     * <p>
     *  对在内部{@link java.security.MessageDigest}对象上执行的{@link java.security.MessageDigest#getProvider}的代理方法。
     * 
     * 
     * @return the result of the {@link java.security.MessageDigest#getProvider} method
     */
    public java.security.Provider getJCEProvider() {
        return algorithm.getProvider();
    }

    /**
     * Proxy method for {@link java.security.MessageDigest#getDigestLength}
     * which is executed on the internal {@link java.security.MessageDigest} object.
     *
     * <p>
     *  对于在内部{@link java.security.MessageDigest}对象上执行的{@link java.security.MessageDigest#getDigestLength}的代理
     * 方法。
     * 
     * 
     * @return the result of the {@link java.security.MessageDigest#getDigestLength} method
     */
    public int getDigestLength() {
        return algorithm.getDigestLength();
    }

    /**
     * Proxy method for {@link java.security.MessageDigest#reset}
     * which is executed on the internal {@link java.security.MessageDigest} object.
     *
     * <p>
     *  对于在内部{@link java.security.MessageDigest}对象上执行的{@link java.security.MessageDigest#reset}的代理方法。
     * 
     */
    public void reset() {
        algorithm.reset();
    }

    /**
     * Proxy method for {@link java.security.MessageDigest#update(byte[])}
     * which is executed on the internal {@link java.security.MessageDigest} object.
     *
     * <p>
     *  对在内部{@link java.security.MessageDigest}对象上执行的{@link java.security.MessageDigest#update(byte [])的代理方法。
     * 
     * 
     * @param input
     */
    public void update(byte[] input) {
        algorithm.update(input);
    }

    /**
     * Proxy method for {@link java.security.MessageDigest#update(byte)}
     * which is executed on the internal {@link java.security.MessageDigest} object.
     *
     * <p>
     *  对于在内部{@link java.security.MessageDigest}对象上执行的{@link java.security.MessageDigest#update(byte)}的代理方法。
     * 
     * 
     * @param input
     */
    public void update(byte input) {
        algorithm.update(input);
    }

    /**
     * Proxy method for {@link java.security.MessageDigest#update(byte[], int, int)}
     * which is executed on the internal {@link java.security.MessageDigest} object.
     *
     * <p>
     *  对于在内部{@link java.security.MessageDigest}对象上执行的{@link java.security.MessageDigest#update(byte [],int,int)的代理方法。
     * 
     * @param buf
     * @param offset
     * @param len
     */
    public void update(byte buf[], int offset, int len) {
        algorithm.update(buf, offset, len);
    }

    /** @inheritDoc */
    public String getBaseNamespace() {
        return Constants.SignatureSpecNS;
    }

    /** @inheritDoc */
    public String getBaseLocalName() {
        return Constants._TAG_DIGESTMETHOD;
    }
}
