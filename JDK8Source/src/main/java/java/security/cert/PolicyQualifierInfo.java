/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

import sun.misc.HexDumpEncoder;
import sun.security.util.DerValue;

/**
 * An immutable policy qualifier represented by the ASN.1 PolicyQualifierInfo
 * structure.
 *
 * <p>The ASN.1 definition is as follows:
 * <pre>
 *   PolicyQualifierInfo ::= SEQUENCE {
 *        policyQualifierId       PolicyQualifierId,
 *        qualifier               ANY DEFINED BY policyQualifierId }
 * </pre>
 * <p>
 * A certificate policies extension, if present in an X.509 version 3
 * certificate, contains a sequence of one or more policy information terms,
 * each of which consists of an object identifier (OID) and optional
 * qualifiers. In an end-entity certificate, these policy information terms
 * indicate the policy under which the certificate has been issued and the
 * purposes for which the certificate may be used. In a CA certificate, these
 * policy information terms limit the set of policies for certification paths
 * which include this certificate.
 * <p>
 * A {@code Set} of {@code PolicyQualifierInfo} objects are returned
 * by the {@link PolicyNode#getPolicyQualifiers PolicyNode.getPolicyQualifiers}
 * method. This allows applications with specific policy requirements to
 * process and validate each policy qualifier. Applications that need to
 * process policy qualifiers should explicitly set the
 * {@code policyQualifiersRejected} flag to false (by calling the
 * {@link PKIXParameters#setPolicyQualifiersRejected
 * PKIXParameters.setPolicyQualifiersRejected} method) before validating
 * a certification path.
 *
 * <p>Note that the PKIX certification path validation algorithm specifies
 * that any policy qualifier in a certificate policies extension that is
 * marked critical must be processed and validated. Otherwise the
 * certification path must be rejected. If the
 * {@code policyQualifiersRejected} flag is set to false, it is up to
 * the application to validate all policy qualifiers in this manner in order
 * to be PKIX compliant.
 *
 * <p><b>Concurrent Access</b>
 *
 * <p>All {@code PolicyQualifierInfo} objects must be immutable and
 * thread-safe. That is, multiple threads may concurrently invoke the
 * methods defined in this class on a single {@code PolicyQualifierInfo}
 * object (or more than one) with no ill effects. Requiring
 * {@code PolicyQualifierInfo} objects to be immutable and thread-safe
 * allows them to be passed around to various pieces of code without
 * worrying about coordinating access.
 *
 * <p>
 *  由ASN.1 PolicyQualifierInfo结构表示的不可变策略限定符。
 * 
 *  <p> ASN.1定义如下：
 * <pre>
 *  PolicyQualifierInfo :: = SEQUENCE {policyQualifierId PolicyQualifierId,qualifier ANY DEFINED BY policyQualifierId}
 * 。
 * </pre>
 * <p>
 *  证书策略扩展(如果存在于X.509版本3证书中)包含一个或多个策略信息项的序列,每个策略信息项由对象标识符(OID)和可选的限定符组成。
 * 在终端实体证书中,这些策略信息术语指示已经发布证书的策略以及可以使用证书的目的。在CA证书中,这些策略信息术语限制包含此证书的认证路径的策略集。
 * <p>
 *  {@link PolicyNode#getPolicyQualifiers PolicyNode.getPolicyQualifiers}方法返回{@code Set} {@code PolicyQualifierInfo}
 * 对象。
 * 这允许具有特定策略要求的应用程序处理和验证每个策略限定符。
 * 需要处理策略限定符的应用程序应在验证证书路径之前,明确将{@code policyQualifiersRejected}标记设置为false(通过调用{@link PKIXParameters#setPolicyQualifiersRejected PKIXParameters.setPolicyQualifiersRejected}
 * 方法)。
 * 这允许具有特定策略要求的应用程序处理和验证每个策略限定符。
 * 
 * <p>请注意,PKIX认证路径验证算法指定必须处理和验证标记为关键的证书策略扩展中的任何策略限定符。否则,必须拒绝认证路径。
 * 如果{@code policyQualifiersRejected}标志设置为false,则由应用程序以此方式验证所有策略限定符以符合PKIX标准。
 * 
 *  <p> <b>并发访问</b>
 * 
 * @author      seth proctor
 * @author      Sean Mullan
 * @since       1.4
 */
public class PolicyQualifierInfo {

    private byte [] mEncoded;
    private String mId;
    private byte [] mData;
    private String pqiString;

    /**
     * Creates an instance of {@code PolicyQualifierInfo} from the
     * encoded bytes. The encoded byte array is copied on construction.
     *
     * <p>
     * 
     *  <p>所有{@code PolicyQualifierInfo}对象必须是不可变的和线程安全的。
     * 也就是说,多个线程可以同时在单个{@code PolicyQualifierInfo}对象(或多个线程)上调用此类中定义的方法,而没有不良影响。
     * 要求{@code PolicyQualifierInfo}对象不可变且线程安全允许将它们传递到各个代码段,而不必担心协调访问。
     * 
     * 
     * @param encoded a byte array containing the qualifier in DER encoding
     * @exception IOException thrown if the byte array does not represent a
     * valid and parsable policy qualifier
     */
    public PolicyQualifierInfo(byte[] encoded) throws IOException {
        mEncoded = encoded.clone();

        DerValue val = new DerValue(mEncoded);
        if (val.tag != DerValue.tag_Sequence)
            throw new IOException("Invalid encoding for PolicyQualifierInfo");

        mId = (val.data.getDerValue()).getOID().toString();
        byte [] tmp = val.data.toByteArray();
        if (tmp == null) {
            mData = null;
        } else {
            mData = new byte[tmp.length];
            System.arraycopy(tmp, 0, mData, 0, tmp.length);
        }
    }

    /**
     * Returns the {@code policyQualifierId} field of this
     * {@code PolicyQualifierInfo}. The {@code policyQualifierId}
     * is an Object Identifier (OID) represented by a set of nonnegative
     * integers separated by periods.
     *
     * <p>
     *  从编码字节创建{@code PolicyQualifierInfo}的实例。编译的字节数组在构造时被复制。
     * 
     * 
     * @return the OID (never {@code null})
     */
    public final String getPolicyQualifierId() {
        return mId;
    }

    /**
     * Returns the ASN.1 DER encoded form of this
     * {@code PolicyQualifierInfo}.
     *
     * <p>
     *  返回此{@code PolicyQualifierInfo}的{@code policyQualifierId}字段。
     *  {@code policyQualifierId}是由以句点分隔的一组非负整数表示的对象标识符(OID)。
     * 
     * 
     * @return the ASN.1 DER encoded bytes (never {@code null}).
     * Note that a copy is returned, so the data is cloned each time
     * this method is called.
     */
    public final byte[] getEncoded() {
        return mEncoded.clone();
    }

    /**
     * Returns the ASN.1 DER encoded form of the {@code qualifier}
     * field of this {@code PolicyQualifierInfo}.
     *
     * <p>
     *  返回此{@code PolicyQualifierInfo}的ASN.1 DER编码形式。
     * 
     * 
     * @return the ASN.1 DER encoded bytes of the {@code qualifier}
     * field. Note that a copy is returned, so the data is cloned each
     * time this method is called.
     */
    public final byte[] getPolicyQualifier() {
        return (mData == null ? null : mData.clone());
    }

    /**
     * Return a printable representation of this
     * {@code PolicyQualifierInfo}.
     *
     * <p>
     *  返回此{@code PolicyQualifierInfo}的{@code qualifier}字段的ASN.1 DER编码形式。
     * 
     * 
     * @return a {@code String} describing the contents of this
     *         {@code PolicyQualifierInfo}
     */
    public String toString() {
        if (pqiString != null)
            return pqiString;
        HexDumpEncoder enc = new HexDumpEncoder();
        StringBuffer sb = new StringBuffer();
        sb.append("PolicyQualifierInfo: [\n");
        sb.append("  qualifierID: " + mId + "\n");
        sb.append("  qualifier: " +
            (mData == null ? "null" : enc.encodeBuffer(mData)) + "\n");
        sb.append("]");
        pqiString = sb.toString();
        return pqiString;
    }
}
