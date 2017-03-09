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

package java.security.cert;

import java.math.BigInteger;
import java.util.Date;
import javax.security.auth.x500.X500Principal;

import sun.security.x509.X509CRLEntryImpl;

/**
 * <p>Abstract class for a revoked certificate in a CRL (Certificate
 * Revocation List).
 *
 * The ASN.1 definition for <em>revokedCertificates</em> is:
 * <pre>
 * revokedCertificates    SEQUENCE OF SEQUENCE  {
 *     userCertificate    CertificateSerialNumber,
 *     revocationDate     ChoiceOfTime,
 *     crlEntryExtensions Extensions OPTIONAL
 *                        -- if present, must be v2
 * }  OPTIONAL
 *
 * CertificateSerialNumber  ::=  INTEGER
 *
 * Extensions  ::=  SEQUENCE SIZE (1..MAX) OF Extension
 *
 * Extension  ::=  SEQUENCE  {
 *     extnId        OBJECT IDENTIFIER,
 *     critical      BOOLEAN DEFAULT FALSE,
 *     extnValue     OCTET STRING
 *                   -- contains a DER encoding of a value
 *                   -- of the type registered for use with
 *                   -- the extnId object identifier value
 * }
 * </pre>
 *
 * <p>
 *  <p> CRL中的撤销证书的抽象类(证书吊销列表)。
 * 
 *  <em> revokedCertificates </em>的ASN.1定义为：
 * <pre>
 *  revokedCertificates SEQUENCE OF SEQUENCE {userCertificate CertificateSerialNumber,revocationDate ChoiceOfTime,crlEntryExtensions Extensions可选 - 如果存在,必须是v2}
 * 可选。
 * 
 *  CertificateSerialNumber :: = INTEGER
 * 
 *  Extensions :: = SEQUENCE SIZE(1..MAX)OF扩展名
 * 
 *  Extension :: = SEQUENCE {extnId OBJECT IDENTIFIER,critical BOOLEAN DEFAULT FALSE,extnValue OCTET STRING  - 包含注册使用的类型的值的DER编码 -  extnId对象标识符值}
 * 。
 * </pre>
 * 
 * 
 * @see X509CRL
 * @see X509Extension
 *
 * @author Hemma Prafullchandra
 */

public abstract class X509CRLEntry implements X509Extension {

    /**
     * Compares this CRL entry for equality with the given
     * object. If the {@code other} object is an
     * {@code instanceof} {@code X509CRLEntry}, then
     * its encoded form (the inner SEQUENCE) is retrieved and compared
     * with the encoded form of this CRL entry.
     *
     * <p>
     *  比较此CRL条目与给定对象的相等性。
     * 如果{@code other}对象是{@code instanceof} {@code X509CRLEntry},那么将检索其编码形式(内部SEQUENCE),并与此CRL条目的编码形式进行比较。
     * 
     * 
     * @param other the object to test for equality with this CRL entry.
     * @return true iff the encoded forms of the two CRL entries
     * match, false otherwise.
     */
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof X509CRLEntry))
            return false;
        try {
            byte[] thisCRLEntry = this.getEncoded();
            byte[] otherCRLEntry = ((X509CRLEntry)other).getEncoded();

            if (thisCRLEntry.length != otherCRLEntry.length)
                return false;
            for (int i = 0; i < thisCRLEntry.length; i++)
                 if (thisCRLEntry[i] != otherCRLEntry[i])
                     return false;
        } catch (CRLException ce) {
            return false;
        }
        return true;
    }

    /**
     * Returns a hashcode value for this CRL entry from its
     * encoded form.
     *
     * <p>
     *  从其编码形式返回此CRL条目的哈希码值。
     * 
     * 
     * @return the hashcode value.
     */
    public int hashCode() {
        int     retval = 0;
        try {
            byte[] entryData = this.getEncoded();
            for (int i = 1; i < entryData.length; i++)
                 retval += entryData[i] * i;

        } catch (CRLException ce) {
            return(retval);
        }
        return(retval);
    }

    /**
     * Returns the ASN.1 DER-encoded form of this CRL Entry,
     * that is the inner SEQUENCE.
     *
     * <p>
     *  返回此CRL条目的ASN.1 DER编码形式,即内部SEQUENCE。
     * 
     * 
     * @return the encoded form of this certificate
     * @exception CRLException if an encoding error occurs.
     */
    public abstract byte[] getEncoded() throws CRLException;

    /**
     * Gets the serial number from this X509CRLEntry,
     * the <em>userCertificate</em>.
     *
     * <p>
     *  从此X509CRLEntry获取序列号,即<em> userCertificate </em>。
     * 
     * 
     * @return the serial number.
     */
    public abstract BigInteger getSerialNumber();

    /**
     * Get the issuer of the X509Certificate described by this entry. If
     * the certificate issuer is also the CRL issuer, this method returns
     * null.
     *
     * <p>This method is used with indirect CRLs. The default implementation
     * always returns null. Subclasses that wish to support indirect CRLs
     * should override it.
     *
     * <p>
     *  获取此条目描述的X509Certificate的颁发者。如果证书颁发者也是CRL颁发者,则此方法返回null。
     * 
     * <p>此方法用于间接CRL。默认实现始终返回null。希望支持间接CRL的子类应该覆盖它。
     * 
     * 
     * @return the issuer of the X509Certificate described by this entry
     * or null if it is issued by the CRL issuer.
     *
     * @since 1.5
     */
    public X500Principal getCertificateIssuer() {
        return null;
    }

    /**
     * Gets the revocation date from this X509CRLEntry,
     * the <em>revocationDate</em>.
     *
     * <p>
     *  从此X509CRLEntry获取撤销日期,即<em> revocationDate </em>。
     * 
     * 
     * @return the revocation date.
     */
    public abstract Date getRevocationDate();

    /**
     * Returns true if this CRL entry has extensions.
     *
     * <p>
     *  如果此CRL条目具有扩展,则返回true。
     * 
     * 
     * @return true if this entry has extensions, false otherwise.
     */
    public abstract boolean hasExtensions();

    /**
     * Returns a string representation of this CRL entry.
     *
     * <p>
     *  返回此CRL条目的字符串表示形式。
     * 
     * 
     * @return a string representation of this CRL entry.
     */
    public abstract String toString();

    /**
     * Returns the reason the certificate has been revoked, as specified
     * in the Reason Code extension of this CRL entry.
     *
     * <p>
     *  返回证书已被撤销的原因,如本CRL条目的原因代码扩展中指定。
     * 
     * @return the reason the certificate has been revoked, or
     *    {@code null} if this CRL entry does not have
     *    a Reason Code extension
     * @since 1.7
     */
    public CRLReason getRevocationReason() {
        if (!hasExtensions()) {
            return null;
        }
        return X509CRLEntryImpl.getRevocationReason(this);
    }
}
