/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.security;

import java.io.*;
import java.security.cert.CertPath;

/**
 * This class encapsulates information about a code signer.
 * It is immutable.
 *
 * <p>
 *  这个类封装了有关代码签名者的信息。它是不可变的。
 * 
 * 
 * @since 1.5
 * @author Vincent Ryan
 */

public final class CodeSigner implements Serializable {

    private static final long serialVersionUID = 6819288105193937581L;

    /**
     * The signer's certificate path.
     *
     * <p>
     *  签名者的证书路径。
     * 
     * 
     * @serial
     */
    private CertPath signerCertPath;

    /*
     * The signature timestamp.
     *
     * <p>
     *  签名时间戳。
     * 
     * 
     * @serial
     */
    private Timestamp timestamp;

    /*
     * Hash code for this code signer.
     * <p>
     *  此代码签名者的哈希代码。
     * 
     */
    private transient int myhash = -1;

    /**
     * Constructs a CodeSigner object.
     *
     * <p>
     *  构造一个CodeSigner对象。
     * 
     * 
     * @param signerCertPath The signer's certificate path.
     *                       It must not be {@code null}.
     * @param timestamp A signature timestamp.
     *                  If {@code null} then no timestamp was generated
     *                  for the signature.
     * @throws NullPointerException if {@code signerCertPath} is
     *                              {@code null}.
     */
    public CodeSigner(CertPath signerCertPath, Timestamp timestamp) {
        if (signerCertPath == null) {
            throw new NullPointerException();
        }
        this.signerCertPath = signerCertPath;
        this.timestamp = timestamp;
    }

    /**
     * Returns the signer's certificate path.
     *
     * <p>
     *  返回签名者的证书路径。
     * 
     * 
     * @return A certificate path.
     */
    public CertPath getSignerCertPath() {
        return signerCertPath;
    }

    /**
     * Returns the signature timestamp.
     *
     * <p>
     *  返回签名时间戳。
     * 
     * 
     * @return The timestamp or {@code null} if none is present.
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the hash code value for this code signer.
     * The hash code is generated using the signer's certificate path and the
     * timestamp, if present.
     *
     * <p>
     *  返回此代码签名者的哈希码值。散列码是使用签名者的证书路径和时间戳(如果存在)生成的。
     * 
     * 
     * @return a hash code value for this code signer.
     */
    public int hashCode() {
        if (myhash == -1) {
            if (timestamp == null) {
                myhash = signerCertPath.hashCode();
            } else {
                myhash = signerCertPath.hashCode() + timestamp.hashCode();
            }
        }
        return myhash;
    }

    /**
     * Tests for equality between the specified object and this
     * code signer. Two code signers are considered equal if their
     * signer certificate paths are equal and if their timestamps are equal,
     * if present in both.
     *
     * <p>
     *  测试指定对象与此代码签名者之间的等同性。如果两个代码签名者的签名者证书路径相等,并且如果它们的时间戳相等,则两个代码签名者被认为是相等的。
     * 
     * 
     * @param obj the object to test for equality with this object.
     *
     * @return true if the objects are considered equal, false otherwise.
     */
    public boolean equals(Object obj) {
        if (obj == null || (!(obj instanceof CodeSigner))) {
            return false;
        }
        CodeSigner that = (CodeSigner)obj;

        if (this == that) {
            return true;
        }
        Timestamp thatTimestamp = that.getTimestamp();
        if (timestamp == null) {
            if (thatTimestamp != null) {
                return false;
            }
        } else {
            if (thatTimestamp == null ||
                (! timestamp.equals(thatTimestamp))) {
                return false;
            }
        }
        return signerCertPath.equals(that.getSignerCertPath());
    }

    /**
     * Returns a string describing this code signer.
     *
     * <p>
     *  返回描述此代码签名者的字符串。
     * 
     * @return A string comprising the signer's certificate and a timestamp,
     *         if present.
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("(");
        sb.append("Signer: " + signerCertPath.getCertificates().get(0));
        if (timestamp != null) {
            sb.append("timestamp: " + timestamp);
        }
        sb.append(")");
        return sb.toString();
    }

    // Explicitly reset hash code value to -1
    private void readObject(ObjectInputStream ois)
        throws IOException, ClassNotFoundException {
     ois.defaultReadObject();
     myhash = -1;
    }
}
