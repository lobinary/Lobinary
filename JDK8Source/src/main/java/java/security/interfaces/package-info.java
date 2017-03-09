/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

/**
 * Provides interfaces for generating RSA (Rivest, Shamir and
 * Adleman AsymmetricCipher algorithm)
 * keys as defined in the RSA Laboratory Technical Note
 * PKCS#1, and DSA (Digital Signature
 * Algorithm) keys as defined in NIST's FIPS-186.
 * <P>
 * Note that these interfaces are intended only for key
 * implementations whose key material is accessible and
 * available. These interfaces are not intended for key
 * implementations whose key material resides in
 * inaccessible, protected storage (such as in a
 * hardware device).
 * <P>
 * For more developer information on how to use these
 * interfaces, including information on how to design
 * {@code Key} classes for hardware devices, please refer
 * to these cryptographic provider developer guides:
 * <ul>
 *   <li><a href=
 *     "{@docRoot}/../technotes/guides/security/crypto/HowToImplAProvider.html">
 *     <b>How to Implement a Provider for the
 *     Java&trade; Cryptography Architecture
 *     </b></a></li>
 * </ul>
 *
 * <h2>Package Specification</h2>
 *
 * <ul>
 *   <li>PKCS #1: RSA Encryption Standard, Version 1.5, November 1993 </li>
 *   <li>Federal Information Processing Standards Publication (FIPS PUB) 186:
 *     Digital Signature Standard (DSS) </li>
 * </ul>
 *
 * <h2>Related Documentation</h2>
 *
 * For further documentation, please see:
 * <ul>
 *   <li>
 *     <a href=
 *       "{@docRoot}/../technotes/guides/security/crypto/CryptoSpec.html">
 *       <b>Java&trade;
 *       Cryptography Architecture API Specification and Reference
 *       </b></a></li>
 * </ul>
 *
 * <p>
 *  提供用于生成RSA实验室技术说明PKCS#1中定义的RSA(Rivest,Shamir和Adleman非对称密码算法)密钥的接口,以及NIST的FIPS-186中定义的DSA(数字签名算法)密钥。
 * <P>
 *  注意,这些接口仅用于其密钥材料可访问和可用的关键实现。这些接口不适用于其密钥材料驻留在不可访问的受保护存储器(例如在硬件设备中)的关键实现。
 * <P>
 *  有关如何使用这些接口的更多开发者信息,包括有关如何为硬件设备设计{@code Key}类的信息,请参阅这些加密提供程序开发人员指南：
 * <ul>
 *  <li> <a href =
 * "{@docRoot}/../technotes/guides/security/crypto/HowToImplAProvider.html">
 *  <b>如何为Java和贸易实现提供程序;加密架构</b> </a> </li>
 * </ul>
 * 
 *  <h2>包规格</h2>
 * 
 * <ul>
 * 
 * @since JDK1.1
 */
package java.security.interfaces;
