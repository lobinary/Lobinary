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
 * Provides classes and interfaces for key specifications and algorithm
 * parameter specifications.
 *
 * <p>A key specification is a transparent representation of the key material
 * that constitutes a key. A key may be specified in an algorithm-specific
 * way, or in an algorithm-independent encoding format (such as ASN.1).
 * This package contains key specifications for DSA public and private keys,
 * RSA public and private keys, PKCS #8 private keys in DER-encoded format,
 * and X.509 public and private keys in DER-encoded format.
 *
 * <p>An algorithm parameter specification is a transparent representation
 * of the sets of parameters used with an algorithm. This package contains
 * an algorithm parameter specification for parameters used with the
 * DSA algorithm.
 *
 * <h2>Package Specification</h2>
 *
 * <ul>
 *   <li>PKCS #1: RSA Encryption Standard, Version 1.5, November 1993</li>
 *   <li>PKCS #8: Private-Key Information Syntax Standard,
 *     Version 1.2, November 1993</li>
 *   <li>Federal Information Processing Standards Publication (FIPS PUB) 186:
 *     Digital Signature Standard (DSS)</li>
 * </ul>
 *
 * <h2>Related Documentation</h2>
 *
 * For documentation that includes information about algorithm parameter
 * and key specifications, please see:
 * <ul>
 *   <li>
 *     <a href=
 *       "{@docRoot}/../technotes/guides/security/crypto/CryptoSpec.html">
 *       <b>Java&trade;
 *       Cryptography Architecture API Specification and Reference
 *       </b></a></li>
 *   <li>
 *     <a href=
 *       "{@docRoot}/../technotes/guides/security/crypto/HowToImplAProvider.html">
 *       <b>How to Implement a Provider for the
 *       Java&trade; Cryptography Architecture
 *       </b></a></li>
 * </ul>
 *
 * <p>
 *  提供关键规范和算法参数规范的类和接口。
 * 
 *  <p>密钥规范是构成密钥的密钥材料的透明表示。可以以算法特定的方式或者以与算法无关的编码格式(例如ASN.1)来指定密钥。
 * 此包包含DSA公钥和私钥,RSA公钥和私钥,DER编码格式的PKCS#8私钥和DER编码格式的X.509公钥和私钥的关键规范。
 * 
 *  <p>算法参数规范是与算法一起使用的参数集合的透明表示。此包包含与DSA算法一起使用的参数的算法参数规范。
 * 
 *  <h2>包规格</h2>
 * 
 * <ul>
 *  <li> PKCS#1：RSA加密标准版本1.5,1993年11月</li> <li> PKCS#8：私钥信息语法标准版本1.2,1993年11月</li> <li>联邦信息处理标准出版物(FIPS 
 * PUB)186：数字签名标准(DSS)</li>。
 * </ul>
 * 
 * 
 * @since 1.2
 */
package java.security.spec;
