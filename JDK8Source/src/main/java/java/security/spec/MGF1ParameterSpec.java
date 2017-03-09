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

package java.security.spec;

import java.security.spec.AlgorithmParameterSpec;

/**
 * This class specifies the set of parameters used with mask generation
 * function MGF1 in OAEP Padding and RSA-PSS signature scheme, as
 * defined in the
 * <a href="http://www.ietf.org/rfc/rfc3447.txt">PKCS #1 v2.1</a>
 * standard.
 *
 * <p>Its ASN.1 definition in PKCS#1 standard is described below:
 * <pre>
 * MGF1Parameters ::= OAEP-PSSDigestAlgorthms
 * </pre>
 * where
 * <pre>
 * OAEP-PSSDigestAlgorithms    ALGORITHM-IDENTIFIER ::= {
 *   { OID id-sha1 PARAMETERS NULL   }|
 *   { OID id-sha224 PARAMETERS NULL   }|
 *   { OID id-sha256 PARAMETERS NULL }|
 *   { OID id-sha384 PARAMETERS NULL }|
 *   { OID id-sha512 PARAMETERS NULL },
 *   ...  -- Allows for future expansion --
 * }
 * </pre>
 * <p>
 *  此类指定在OAEP Padding和RSA-PSS签名方案中使用掩码生成函数MGF1使用的参数集,如<a href="http://www.ietf.org/rfc/rfc3447.txt"> PKC
 * S中定义#1 v2.1 </a> standard。
 * 
 *  <p>其在PKCS#1标准中的ASN.1定义描述如下：
 * <pre>
 *  MGF1Parameters :: = OAEP-PSSDigestAlgorthms
 * </pre>
 *  哪里
 * <pre>
 *  OAEP-PSSDigestAlgorithms ALGORITHM-IDENTIFIER :: = {{OID id-sha1 PARAMETERS NULL} | {OID id-sha224 PARAMETERS NULL}
 *  | {OID id-sha256 PARAMETERS NULL} | {OID id-sha384 PARAMETERS NULL} | {OID id-sha512 PARAMETERS NULL}
 * ,...  - 允许以后扩展 - }。
 * </pre>
 * 
 * @see PSSParameterSpec
 * @see javax.crypto.spec.OAEPParameterSpec
 *
 * @author Valerie Peng
 *
 * @since 1.5
 */
public class MGF1ParameterSpec implements AlgorithmParameterSpec {

    /**
     * The MGF1ParameterSpec which uses "SHA-1" message digest.
     * <p>
     *  使用"SHA-1"消息摘要的MGF1ParameterSpec。
     * 
     */
    public static final MGF1ParameterSpec SHA1 =
        new MGF1ParameterSpec("SHA-1");
    /**
     * The MGF1ParameterSpec which uses "SHA-224" message digest.
     * <p>
     *  使用"SHA-224"消息摘要的MGF1ParameterSpec。
     * 
     */
    public static final MGF1ParameterSpec SHA224 =
        new MGF1ParameterSpec("SHA-224");
    /**
     * The MGF1ParameterSpec which uses "SHA-256" message digest.
     * <p>
     *  MGF1ParameterSpec使用"SHA-256"消息摘要。
     * 
     */
    public static final MGF1ParameterSpec SHA256 =
        new MGF1ParameterSpec("SHA-256");
    /**
     * The MGF1ParameterSpec which uses "SHA-384" message digest.
     * <p>
     *  MGF1ParameterSpec使用"SHA-384"消息摘要。
     * 
     */
    public static final MGF1ParameterSpec SHA384 =
        new MGF1ParameterSpec("SHA-384");
    /**
     * The MGF1ParameterSpec which uses SHA-512 message digest.
     * <p>
     *  MGF1ParameterSpec使用SHA-512消息摘要。
     * 
     */
    public static final MGF1ParameterSpec SHA512 =
        new MGF1ParameterSpec("SHA-512");

    private String mdName;

    /**
     * Constructs a parameter set for mask generation function MGF1
     * as defined in the PKCS #1 standard.
     *
     * <p>
     *  构造如PKCS#1标准中定义的掩码生成函数MGF1的参数集。
     * 
     * 
     * @param mdName the algorithm name for the message digest
     * used in this mask generation function MGF1.
     * @exception NullPointerException if {@code mdName} is null.
     */
    public MGF1ParameterSpec(String mdName) {
        if (mdName == null) {
            throw new NullPointerException("digest algorithm is null");
        }
        this.mdName = mdName;
    }

    /**
     * Returns the algorithm name of the message digest used by the mask
     * generation function.
     *
     * <p>
     *  返回掩码生成函数使用的消息摘要的算法名称。
     * 
     * @return the algorithm name of the message digest.
     */
    public String getDigestAlgorithm() {
        return mdName;
    }
}
