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

import java.math.BigInteger;
import java.util.Arrays;

/**
 * This immutable class defines an elliptic curve (EC) prime
 * finite field.
 *
 * <p>
 *  这个不可变类定义了椭圆曲线(EC)素数有限域。
 * 
 * 
 * @see ECField
 *
 * @author Valerie Peng
 *
 * @since 1.5
 */
public class ECFieldFp implements ECField {

    private BigInteger p;

    /**
     * Creates an elliptic curve prime finite field
     * with the specified prime {@code p}.
     * <p>
     *  创建具有指定素数{@code p}的椭圆曲线素数有限域。
     * 
     * 
     * @param p the prime.
     * @exception NullPointerException if {@code p} is null.
     * @exception IllegalArgumentException if {@code p}
     * is not positive.
     */
    public ECFieldFp(BigInteger p) {
        if (p.signum() != 1) {
            throw new IllegalArgumentException("p is not positive");
        }
        this.p = p;
    }

    /**
     * Returns the field size in bits which is size of prime p
     * for this prime finite field.
     * <p>
     *  返回对于该素数有限域,素数p的大小的字段大小。
     * 
     * 
     * @return the field size in bits.
     */
    public int getFieldSize() {
        return p.bitLength();
    };

    /**
     * Returns the prime {@code p} of this prime finite field.
     * <p>
     *  返回此素数有限域的素数{@code p}。
     * 
     * 
     * @return the prime.
     */
    public BigInteger getP() {
        return p;
    }

    /**
     * Compares this prime finite field for equality with the
     * specified object.
     * <p>
     *  将此质数有限域与指定对象的相等性进行比较。
     * 
     * 
     * @param obj the object to be compared.
     * @return true if {@code obj} is an instance
     * of ECFieldFp and the prime value match, false otherwise.
     */
    public boolean equals(Object obj) {
        if (this == obj)  return true;
        if (obj instanceof ECFieldFp) {
            return (p.equals(((ECFieldFp)obj).p));
        }
        return false;
    }

    /**
     * Returns a hash code value for this prime finite field.
     * <p>
     *  返回此素数有限域的哈希码值。
     * 
     * @return a hash code value.
     */
    public int hashCode() {
        return p.hashCode();
    }
}
