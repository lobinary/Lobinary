/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2006, Oracle and/or its affiliates. All rights reserved.
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

/**
 * This class is an abstraction of certificate revocation lists (CRLs) that
 * have different formats but important common uses. For example, all CRLs
 * share the functionality of listing revoked certificates, and can be queried
 * on whether or not they list a given certificate.
 * <p>
 * Specialized CRL types can be defined by subclassing off of this abstract
 * class.
 *
 * <p>
 *  此类是具有不同格式但重要的常见用途的证书吊销列表(CRL)的抽象。例如,所有CRL共享列出撤销的证书的功能,并且可以查询他们是否列出给定的证书。
 * <p>
 *  专用CRL类型可以通过对这个抽象类进行子类化来定义。
 * 
 * 
 * @author Hemma Prafullchandra
 *
 *
 * @see X509CRL
 * @see CertificateFactory
 *
 * @since 1.2
 */

public abstract class CRL {

    // the CRL type
    private String type;

    /**
     * Creates a CRL of the specified type.
     *
     * <p>
     *  创建指定类型的CRL。
     * 
     * 
     * @param type the standard name of the CRL type.
     * See Appendix A in the <a href=
     * "../../../../technotes/guides/security/crypto/CryptoSpec.html#AppA">
     * Java Cryptography Architecture API Specification &amp; Reference </a>
     * for information about standard CRL types.
     */
    protected CRL(String type) {
        this.type = type;
    }

    /**
     * Returns the type of this CRL.
     *
     * <p>
     *  返回此CRL的类型。
     * 
     * 
     * @return the type of this CRL.
     */
    public final String getType() {
        return this.type;
    }

    /**
     * Returns a string representation of this CRL.
     *
     * <p>
     *  返回此CRL的字符串表示形式。
     * 
     * 
     * @return a string representation of this CRL.
     */
    public abstract String toString();

    /**
     * Checks whether the given certificate is on this CRL.
     *
     * <p>
     *  检查给定的证书是否在此CRL上。
     * 
     * @param cert the certificate to check for.
     * @return true if the given certificate is on this CRL,
     * false otherwise.
     */
    public abstract boolean isRevoked(Certificate cert);
}
