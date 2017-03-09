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
 * This immutable class holds the necessary values needed to represent
 * an elliptic curve.
 *
 * <p>
 *  这个不可变类保存表示椭圆曲线所需的必要值。
 * 
 * 
 * @see ECField
 * @see ECFieldFp
 * @see ECFieldF2m
 *
 * @author Valerie Peng
 *
 * @since 1.5
 */
public class EllipticCurve {

    private final ECField field;
    private final BigInteger a;
    private final BigInteger b;
    private final byte[] seed;

    // Check coefficient c is a valid element in ECField field.
    private static void checkValidity(ECField field, BigInteger c,
        String cName) {
        // can only perform check if field is ECFieldFp or ECFieldF2m.
        if (field instanceof ECFieldFp) {
            BigInteger p = ((ECFieldFp)field).getP();
            if (p.compareTo(c) != 1) {
                throw new IllegalArgumentException(cName + " is too large");
            } else if (c.signum() < 0) {
                throw new IllegalArgumentException(cName + " is negative");
            }
        } else if (field instanceof ECFieldF2m) {
            int m = ((ECFieldF2m)field).getM();
            if (c.bitLength() > m) {
                throw new IllegalArgumentException(cName + " is too large");
            }
        }
    }

    /**
     * Creates an elliptic curve with the specified elliptic field
     * {@code field} and the coefficients {@code a} and
     * {@code b}.
     * <p>
     *  使用指定的椭圆场{@code field}和系数{@code a}和{@code b}创建椭圆曲线。
     * 
     * 
     * @param field the finite field that this elliptic curve is over.
     * @param a the first coefficient of this elliptic curve.
     * @param b the second coefficient of this elliptic curve.
     * @exception NullPointerException if {@code field},
     * {@code a}, or {@code b} is null.
     * @exception IllegalArgumentException if {@code a}
     * or {@code b} is not null and not in {@code field}.
     */
    public EllipticCurve(ECField field, BigInteger a,
                         BigInteger b) {
        this(field, a, b, null);
    }

    /**
     * Creates an elliptic curve with the specified elliptic field
     * {@code field}, the coefficients {@code a} and
     * {@code b}, and the {@code seed} used for curve generation.
     * <p>
     *  使用指定的椭圆字段{@code field},系数{@code a}和{@code b}以及用于生成曲线的{@code seed}创建椭圆曲线。
     * 
     * 
     * @param field the finite field that this elliptic curve is over.
     * @param a the first coefficient of this elliptic curve.
     * @param b the second coefficient of this elliptic curve.
     * @param seed the bytes used during curve generation for later
     * validation. Contents of this array are copied to protect against
     * subsequent modification.
     * @exception NullPointerException if {@code field},
     * {@code a}, or {@code b} is null.
     * @exception IllegalArgumentException if {@code a}
     * or {@code b} is not null and not in {@code field}.
     */
    public EllipticCurve(ECField field, BigInteger a,
                         BigInteger b, byte[] seed) {
        if (field == null) {
            throw new NullPointerException("field is null");
        }
        if (a == null) {
            throw new NullPointerException("first coefficient is null");
        }
        if (b == null) {
            throw new NullPointerException("second coefficient is null");
        }
        checkValidity(field, a, "first coefficient");
        checkValidity(field, b, "second coefficient");
        this.field = field;
        this.a = a;
        this.b = b;
        if (seed != null) {
            this.seed = seed.clone();
        } else {
            this.seed = null;
        }
    }

    /**
     * Returns the finite field {@code field} that this
     * elliptic curve is over.
     * <p>
     *  返回此椭圆曲线结束的有限域{@code field}。
     * 
     * 
     * @return the field {@code field} that this curve
     * is over.
     */
    public ECField getField() {
        return field;
    }

    /**
     * Returns the first coefficient {@code a} of the
     * elliptic curve.
     * <p>
     *  返回椭圆曲线的第一个系数{@code a}。
     * 
     * 
     * @return the first coefficient {@code a}.
     */
    public BigInteger getA() {
        return a;
    }

    /**
     * Returns the second coefficient {@code b} of the
     * elliptic curve.
     * <p>
     *  返回椭圆曲线的第二个系数{@code b}。
     * 
     * 
     * @return the second coefficient {@code b}.
     */
    public BigInteger getB() {
        return b;
    }

    /**
     * Returns the seeding bytes {@code seed} used
     * during curve generation. May be null if not specified.
     * <p>
     *  返回在生成曲线期间使用的种子字节{@code seed}。如果未指定,可以为null。
     * 
     * 
     * @return the seeding bytes {@code seed}. A new
     * array is returned each time this method is called.
     */
    public byte[] getSeed() {
        if (seed == null) return null;
        else return seed.clone();
    }

    /**
     * Compares this elliptic curve for equality with the
     * specified object.
     * <p>
     *  比较此椭圆曲线与指定对象的相等性。
     * 
     * 
     * @param obj the object to be compared.
     * @return true if {@code obj} is an instance of
     * EllipticCurve and the field, A, and B match, false otherwise.
     */
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof EllipticCurve) {
            EllipticCurve curve = (EllipticCurve) obj;
            if ((field.equals(curve.field)) &&
                (a.equals(curve.a)) &&
                (b.equals(curve.b))) {
                    return true;
            }
        }
        return false;
    }

    /**
     * Returns a hash code value for this elliptic curve.
     * <p>
     *  返回此椭圆曲线的哈希码值。
     * 
     * @return a hash code value computed from the hash codes of the field, A,
     * and B, as follows:
     * <pre>{@code
     *     (field.hashCode() << 6) + (a.hashCode() << 4) + (b.hashCode() << 2)
     * }</pre>
     */
    public int hashCode() {
        return (field.hashCode() << 6 +
            (a.hashCode() << 4) +
            (b.hashCode() << 2));
    }
}
