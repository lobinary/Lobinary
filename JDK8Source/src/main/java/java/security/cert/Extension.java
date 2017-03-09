/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.security.cert;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * This interface represents an X.509 extension.
 *
 * <p>
 * Extensions provide a means of associating additional attributes with users
 * or public keys and for managing a certification hierarchy.  The extension
 * format also allows communities to define private extensions to carry
 * information unique to those communities.
 *
 * <p>
 * Each extension contains an object identifier, a criticality setting
 * indicating whether it is a critical or a non-critical extension, and
 * and an ASN.1 DER-encoded value. Its ASN.1 definition is:
 *
 * <pre>
 *
 *     Extension ::= SEQUENCE {
 *         extnId        OBJECT IDENTIFIER,
 *         critical      BOOLEAN DEFAULT FALSE,
 *         extnValue     OCTET STRING
 *                 -- contains a DER encoding of a value
 *                 -- of the type registered for use with
 *                 -- the extnId object identifier value
 *     }
 *
 * </pre>
 *
 * <p>
 * This interface is designed to provide access to a single extension,
 * unlike {@link java.security.cert.X509Extension} which is more suitable
 * for accessing a set of extensions.
 *
 * <p>
 *  此接口表示X.509扩展名。
 * 
 * <p>
 *  扩展提供了将附加属性与用户或公钥相关联以及用于管理认证层次结构的手段。扩展格式还允许社区定义私有扩展来携带这些社区特有的信息。
 * 
 * <p>
 *  每个扩展包含对象标识符,指示其是关键扩展还是非关键扩展的关键性设置,以及ASN.1 DER编码值。其ASN.1定义是：
 * 
 * <pre>
 * 
 *  Extension :: = SEQUENCE {extnId OBJECT IDENTIFIER,critical BOOLEAN DEFAULT FALSE,extnValue OCTET STRING  - 包含注册使用的类型的值的DER编码 -  extnId对象标识符值}
 * 。
 * 
 * </pre>
 * 
 * 
 * @since 1.7
 */
public interface Extension {

    /**
     * Gets the extensions's object identifier.
     *
     * <p>
     * <p>
     *  此接口旨在提供对单个扩展的访问,与{@link java.security.cert.X509Extension}不同,后者更适合访问一组扩展。
     * 
     * 
     * @return the object identifier as a String
     */
    String getId();

    /**
     * Gets the extension's criticality setting.
     *
     * <p>
     *  获取扩展的对象标识符。
     * 
     * 
     * @return true if this is a critical extension.
     */
    boolean isCritical();

    /**
     * Gets the extensions's DER-encoded value. Note, this is the bytes
     * that are encoded as an OCTET STRING. It does not include the OCTET
     * STRING tag and length.
     *
     * <p>
     *  获取扩展程序的关键性设置。
     * 
     * 
     * @return a copy of the extension's value, or {@code null} if no
     *    extension value is present.
     */
    byte[] getValue();

    /**
     * Generates the extension's DER encoding and writes it to the output
     * stream.
     *
     * <p>
     *  获取扩展程序的DER编码值。注意,这是编码为OCTET STRING的字节。它不包括OCTET STRING标记和长度。
     * 
     * 
     * @param out the output stream
     * @exception IOException on encoding or output error.
     * @exception NullPointerException if {@code out} is {@code null}.
     */
    void encode(OutputStream out) throws IOException;
}
