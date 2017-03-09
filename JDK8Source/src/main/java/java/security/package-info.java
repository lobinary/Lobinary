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
 * Provides the classes and interfaces for the security framework.
 * This includes classes that implement an easily configurable,
 * fine-grained access control security architecture.
 * This package also supports
 * the generation and storage of cryptographic public key pairs,
 * as well as a number of exportable cryptographic operations
 * including those for message digest and signature generation.  Finally,
 * this package provides classes that support signed/guarded objects
 * and secure random number generation.
 *
 * Many of the classes provided in this package (the cryptographic
 * and secure random number generator classes in particular) are
 * provider-based.  The class itself defines a programming interface
 * to which applications may write.  The implementations themselves may
 * then be written by independent third-party vendors and plugged
 * in seamlessly as needed.  Therefore application developers may
 * take advantage of any number of provider-based implementations
 * without having to add or rewrite code.
 *
 * <h2>Package Specification</h2>
 *
 * <ul>
 *   <li><a href="{@docRoot}/../technotes/guides/security/crypto/CryptoSpec.html">
 *     <b>Java&trade;
 *     Cryptography Architecture (JCA) Reference Guide</b></a></li>
 *
 *   <li>PKCS #8: Private-Key Information Syntax Standard, Version 1.2,
 *     November 1993</li>
 *
 *   <li><a href="{@docRoot}/../technotes/guides/security/StandardNames.html">
 *     <b>Java&trade;
 *     Cryptography Architecture Standard Algorithm Name
 *     Documentation</b></a></li>
 * </ul>
 *
 * <h2>Related Documentation</h2>
 *
 * For further documentation, please see:
 * <ul>
 *   <li><a href=
 *     "{@docRoot}/../technotes/guides/security/spec/security-spec.doc.html">
 *     <b>Java&trade;
 *     SE Platform Security Architecture</b></a></li>
 *
 *   <li><a href=
 *     "{@docRoot}/../technotes/guides/security/crypto/HowToImplAProvider.html">
 *     <b>How to Implement a Provider in the
 *     Java&trade; Cryptography Architecture
 *     </b></a></li>
 *
 *   <li><a href=
 *     "{@docRoot}/../technotes/guides/security/PolicyFiles.html"><b>
 *     Default Policy Implementation and Policy File Syntax
 *     </b></a></li>
 *
 *   <li><a href=
 *     "{@docRoot}/../technotes/guides/security/permissions.html"><b>
 *     Permissions in the
 *     Java&trade; SE Development Kit (JDK)
 *     </b></a></li>
 *
 *   <li><a href=
 *     "{@docRoot}/../technotes/guides/security/SecurityToolsSummary.html"><b>
 *     Summary of Tools for
 *     Java&trade; Platform Security
 *     </b></a></li>
 *
 *   <li><b>keytool</b>
 *     (<a href="{@docRoot}/../technotes/tools/unix/keytool.html">
 *       for Solaris/Linux</a>)
 *     (<a href="{@docRoot}/../technotes/tools/windows/keytool.html">
 *       for Windows</a>)
 *     </li>
 *
 *   <li><b>jarsigner</b>
 *     (<a href="{@docRoot}/../technotes/tools/unix/jarsigner.html">
 *       for Solaris/Linux</a>)
 *     (<a href="{@docRoot}/../technotes/tools/windows/jarsigner.html">
 *       for Windows</a>)
 *     </li>
 *
 * </ul>
 *
 * <p>
 *  提供安全框架的类和接口。这包括实现易于配置的细粒度访问控制安全体系结构的类。此包还支持加密公钥对的生成和存储,以及多个可导出的加密操作,包括用于消息摘要和签名生成的加密操作。
 * 最后,这个包提供了支持签名/守卫对象和安全随机数生成的类。
 * 
 *  此包中提供的许多类(特别是加密和安全随机数生成器类)是基于提供者的。类本身定义了应用程序可以编写的编程接口。实现本身可以由独立的第三方供应商编写,并根据需要无缝插入。
 * 因此,应用程序开发人员可以利用任何数量的基于提供程序的实现,而无需添加或重写代码。
 * 
 *  <h2>包规格</h2>
 * 
 * <ul>
 *  <li> <a href="{@docRoot}/../technotes/guides/security/crypto/CryptoSpec.html"> <b> Java&trade;加密架构(J
 * CA)参考指南</b> </a> </li>。
 * 
 *  <li> PKCS#8：私钥信息语法标准,版本1.2,1993年11月</li>
 * 
 * <li> <a href="{@docRoot}/../technotes/guides/security/StandardNames.html"> <b> Java与贸易;加密架构标准算法名称文档</b>
 *  </a> </li>。
 * </ul>
 * 
 *  <h2>相关文档</h2>
 * 
 *  有关进一步的文档,请参阅：
 * <ul>
 *  <li> <a href =
 * "{@docRoot}/../technotes/guides/security/spec/security-spec.doc.html">
 *  <b> Java&trade; SE平台安全体系结构</b> </a> </li>
 * 
 * @since 1.1
 */
package java.security;
