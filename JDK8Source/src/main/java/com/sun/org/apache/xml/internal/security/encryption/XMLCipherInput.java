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

import java.io.IOException;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolver;
import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverException;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import com.sun.org.apache.xml.internal.security.transforms.TransformationException;
import org.w3c.dom.Attr;
import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * <code>XMLCipherInput</code> is used to wrap input passed into the
 * XMLCipher encryption operations.
 *
 * In decryption mode, it takes a <code>CipherData</code> object and allows
 * callers to dereference the CipherData into the encrypted bytes that it
 * actually represents.  This takes care of all base64 encoding etc.
 *
 * While primarily an internal class, this can be used by applications to
 * quickly and easily retrieve the encrypted bytes from an EncryptedType
 * object
 *
 * <p>
 *  <code> XMLCipherInput </code>用于包装传递到XMLCipher加密操作的输入。
 * 
 *  在解密模式下,它需要一个<code> CipherData </code>对象,并允许调用者将CipherData解引用为它实际表示的加密字节。这照顾所有base64编码等。
 * 
 *  虽然主要是一个内部类,这可以由应用程序快速和轻松地从加密类型对象检索加密字节
 * 
 * 
 * @author Berin Lautenbach
 */
public class XMLCipherInput {

    private static java.util.logging.Logger logger =
        java.util.logging.Logger.getLogger(XMLCipherInput.class.getName());

    /** The data we are working with */
    private CipherData cipherData;

    /** MODES */
    private int mode;

    private boolean secureValidation;

    /**
     * Constructor for processing encrypted octets
     *
     * <p>
     *  用于处理加密的八位字节的构造函数
     * 
     * 
     * @param data The <code>CipherData</code> object to read the bytes from
     * @throws XMLEncryptionException {@link XMLEncryptionException}
     */
    public XMLCipherInput(CipherData data) throws XMLEncryptionException {
        cipherData = data;
        mode = XMLCipher.DECRYPT_MODE;
        if (cipherData == null) {
            throw new XMLEncryptionException("CipherData is null");
        }
    }

    /**
     * Constructor for processing encrypted octets
     *
     * <p>
     *  用于处理加密的八位字节的构造函数
     * 
     * 
     * @param input The <code>EncryptedType</code> object to read
     * the bytes from.
     * @throws XMLEncryptionException {@link XMLEncryptionException}
     */
    public XMLCipherInput(EncryptedType input) throws XMLEncryptionException {
        cipherData = ((input == null) ? null : input.getCipherData());
        mode = XMLCipher.DECRYPT_MODE;
        if (cipherData == null) {
            throw new XMLEncryptionException("CipherData is null");
        }
    }

    /**
     * Set whether secure validation is enabled or not. The default is false.
     * <p>
     * 设置是否启用安全验证。默认值为false。
     * 
     */
    public void setSecureValidation(boolean secureValidation) {
        this.secureValidation = secureValidation;
    }

    /**
     * Dereferences the input and returns it as a single byte array.
     *
     * <p>
     *  解除输入并将其作为单个字节数组返回。
     * 
     * 
     * @throws XMLEncryptionException
     * @return The decripted bytes.
     */
    public byte[] getBytes() throws XMLEncryptionException {
        if (mode == XMLCipher.DECRYPT_MODE) {
            return getDecryptBytes();
        }
        return null;
    }

    /**
     * Internal method to get bytes in decryption mode
     * <p>
     *  在解密模式下获取字节的内部方法
     * 
     * @return the decrypted bytes
     * @throws XMLEncryptionException
     */
    private byte[] getDecryptBytes() throws XMLEncryptionException {
        String base64EncodedEncryptedOctets = null;

        if (cipherData.getDataType() == CipherData.REFERENCE_TYPE) {
            // Fun time!
            if (logger.isLoggable(java.util.logging.Level.FINE)) {
                logger.log(java.util.logging.Level.FINE, "Found a reference type CipherData");
            }
            CipherReference cr = cipherData.getCipherReference();

            // Need to wrap the uri in an Attribute node so that we can
            // Pass to the resource resolvers

            Attr uriAttr = cr.getURIAsAttr();
            XMLSignatureInput input = null;

            try {
                ResourceResolver resolver =
                    ResourceResolver.getInstance(uriAttr, null, secureValidation);
                input = resolver.resolve(uriAttr, null, secureValidation);
            } catch (ResourceResolverException ex) {
                throw new XMLEncryptionException("empty", ex);
            }

            if (input != null) {
                if (logger.isLoggable(java.util.logging.Level.FINE)) {
                    logger.log(java.util.logging.Level.FINE, "Managed to resolve URI \"" + cr.getURI() + "\"");
                }
            } else {
                if (logger.isLoggable(java.util.logging.Level.FINE)) {
                    logger.log(java.util.logging.Level.FINE, "Failed to resolve URI \"" + cr.getURI() + "\"");
                }
            }

            // Lets see if there are any transforms
            Transforms transforms = cr.getTransforms();
            if (transforms != null) {
                if (logger.isLoggable(java.util.logging.Level.FINE)) {
                    logger.log(java.util.logging.Level.FINE, "Have transforms in cipher reference");
                }
                try {
                    com.sun.org.apache.xml.internal.security.transforms.Transforms dsTransforms =
                        transforms.getDSTransforms();
                    dsTransforms.setSecureValidation(secureValidation);
                    input = dsTransforms.performTransforms(input);
                } catch (TransformationException ex) {
                    throw new XMLEncryptionException("empty", ex);
                }
            }

            try {
                return input.getBytes();
            } catch (IOException ex) {
                throw new XMLEncryptionException("empty", ex);
            } catch (CanonicalizationException ex) {
                throw new XMLEncryptionException("empty", ex);
            }

            // retrieve the cipher text
        } else if (cipherData.getDataType() == CipherData.VALUE_TYPE) {
            base64EncodedEncryptedOctets = cipherData.getCipherValue().getValue();
        } else {
            throw new XMLEncryptionException("CipherData.getDataType() returned unexpected value");
        }

        if (logger.isLoggable(java.util.logging.Level.FINE)) {
            logger.log(java.util.logging.Level.FINE, "Encrypted octets:\n" + base64EncodedEncryptedOctets);
        }

        try {
            return Base64.decode(base64EncodedEncryptedOctets);
        } catch (Base64DecodingException bde) {
            throw new XMLEncryptionException("empty", bde);
        }
    }
}
