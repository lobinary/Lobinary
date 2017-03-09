/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 1998, Oracle and/or its affiliates. All rights reserved.
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

package java.security.interfaces;

/**
 * The interface to a DSA public or private key. DSA (Digital Signature
 * Algorithm) is defined in NIST's FIPS-186.
 *
 * <p>
 *  到DSA公共或私有密钥的接口。 DSA(数字签名算法)在NIST的FIPS-186中定义。
 * 
 * 
 * @see DSAParams
 * @see java.security.Key
 * @see java.security.Signature
 *
 * @author Benjamin Renaud
 * @author Josh Bloch
 */
public interface DSAKey {

    /**
     * Returns the DSA-specific key parameters. These parameters are
     * never secret.
     *
     * <p>
     *  返回DSA特定的关键参数。这些参数从不是秘密。
     * 
     * @return the DSA-specific key parameters.
     *
     * @see DSAParams
     */
    public DSAParams getParams();
}
