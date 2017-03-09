/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.security.spec;

/**
 * A (transparent) specification of the key material
 * that constitutes a cryptographic key.
 *
 * <p>If the key is stored on a hardware device, its
 * specification may contain information that helps identify the key on the
 * device.
 *
 * <P> A key may be specified in an algorithm-specific way, or in an
 * algorithm-independent encoding format (such as ASN.1).
 * For example, a DSA private key may be specified by its components
 * {@code x}, {@code p}, {@code q}, and {@code g}
 * (see {@link DSAPrivateKeySpec}), or it may be
 * specified using its DER encoding
 * (see {@link PKCS8EncodedKeySpec}).
 *
 * <P> This interface contains no methods or constants. Its only purpose
 * is to group (and provide type safety for) all key specifications.
 * All key specifications must implement this interface.
 *
 * <p>
 *  构成密钥的密钥材料的(透明)规范。
 * 
 *  <p>如果密钥存储在硬件设备上,则其规范可能包含有助于标识设备上密钥的信息。
 * 
 *  可以以算法特定的方式或以与算法无关的编码格式(例如ASN.1)来指定密钥。
 * 例如,DSA私钥可以由其组件{@code x},{@code p},{@code q}和{@code g}(请参阅{@link DSAPrivateKeySpec})指定,或者可以是使用其DER编码指定
 * 
 * @author Jan Luehe
 *
 *
 * @see java.security.Key
 * @see java.security.KeyFactory
 * @see EncodedKeySpec
 * @see X509EncodedKeySpec
 * @see PKCS8EncodedKeySpec
 * @see DSAPrivateKeySpec
 * @see DSAPublicKeySpec
 *
 * @since 1.2
 */

public interface KeySpec { }
