/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
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

/**
 * This immutable class specifies the set of parameters used for
 * generating DSA parameters as specified in
 * <a href="http://csrc.nist.gov/publications/fips/fips186-3/fips_186-3.pdf">FIPS 186-3 Digital Signature Standard (DSS)</a>.
 *
 * <p>
 *  此不可变类指定了用于生成DSA参数的参数集,如<a href="http://csrc.nist.gov/publications/fips/fips186-3/fips_186-3.pdf"> FI
 * PS 186-3数字签名标准(DSS)</a>。
 * 
 * 
 * @see AlgorithmParameterSpec
 *
 * @since 8
 */
public final class DSAGenParameterSpec implements AlgorithmParameterSpec {

    private final int pLen;
    private final int qLen;
    private final int seedLen;

    /**
     * Creates a domain parameter specification for DSA parameter
     * generation using {@code primePLen} and {@code subprimeQLen}.
     * The value of {@code subprimeQLen} is also used as the default
     * length of the domain parameter seed in bits.
     * <p>
     *  使用{@code primePLen}和{@code subprimeQLen}创建用于DSA参数生成的域参数规范。 {@code subprimeQLen}的值也用作位参数种子的默认长度。
     * 
     * 
     * @param primePLen the desired length of the prime P in bits.
     * @param subprimeQLen the desired length of the sub-prime Q in bits.
     * @exception IllegalArgumentException if {@code primePLen}
     * or {@code subprimeQLen} is illegal per the specification of
     * FIPS 186-3.
     */
    public DSAGenParameterSpec(int primePLen, int subprimeQLen) {
        this(primePLen, subprimeQLen, subprimeQLen);
    }

    /**
     * Creates a domain parameter specification for DSA parameter
     * generation using {@code primePLen}, {@code subprimeQLen},
     * and {@code seedLen}.
     * <p>
     *  使用{@code primePLen},{@code subprimeQLen}和{@code seedLen}创建用于DSA参数生成的域参数规范。
     * 
     * 
     * @param primePLen the desired length of the prime P in bits.
     * @param subprimeQLen the desired length of the sub-prime Q in bits.
     * @param seedLen the desired length of the domain parameter seed in bits,
     * shall be equal to or greater than {@code subprimeQLen}.
     * @exception IllegalArgumentException if {@code primePLenLen},
     * {@code subprimeQLen}, or {@code seedLen} is illegal per the
     * specification of FIPS 186-3.
     */
    public DSAGenParameterSpec(int primePLen, int subprimeQLen, int seedLen) {
        switch (primePLen) {
        case 1024:
            if (subprimeQLen != 160) {
                throw new IllegalArgumentException
                    ("subprimeQLen must be 160 when primePLen=1024");
            }
            break;
        case 2048:
            if (subprimeQLen != 224 && subprimeQLen != 256) {
               throw new IllegalArgumentException
                   ("subprimeQLen must be 224 or 256 when primePLen=2048");
            }
            break;
        case 3072:
            if (subprimeQLen != 256) {
                throw new IllegalArgumentException
                    ("subprimeQLen must be 256 when primePLen=3072");
            }
            break;
        default:
            throw new IllegalArgumentException
                ("primePLen must be 1024, 2048, or 3072");
        }
        if (seedLen < subprimeQLen) {
            throw new IllegalArgumentException
                ("seedLen must be equal to or greater than subprimeQLen");
        }
        this.pLen = primePLen;
        this.qLen = subprimeQLen;
        this.seedLen = seedLen;
    }

    /**
     * Returns the desired length of the prime P of the
     * to-be-generated DSA domain parameters in bits.
     * <p>
     *  以比特形式返回要生成的DSA域参数的素数P的期望长度。
     * 
     * 
     * @return the length of the prime P.
     */
    public int getPrimePLength() {
        return pLen;
    }

    /**
     * Returns the desired length of the sub-prime Q of the
     * to-be-generated DSA domain parameters in bits.
     * <p>
     *  以比特形式返回要生成的DSA域参数的子素数Q的期望长度。
     * 
     * 
     * @return the length of the sub-prime Q.
     */
    public int getSubprimeQLength() {
        return qLen;
    }

    /**
     * Returns the desired length of the domain parameter seed in bits.
     * <p>
     *  返回域参数种子的所需长度(以位为单位)。
     * 
     * @return the length of the domain parameter seed.
     */
    public int getSeedLength() {
        return seedLen;
    }
}
