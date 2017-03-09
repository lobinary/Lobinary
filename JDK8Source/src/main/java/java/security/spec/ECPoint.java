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

/**
 * This immutable class represents a point on an elliptic curve (EC)
 * in affine coordinates. Other coordinate systems can
 * extend this class to represent this point in other
 * coordinates.
 *
 * <p>
 *  这个不可变类在仿射坐标中表示椭圆曲线(EC)上的点。其他坐标系可以扩展这个类,以在其他坐标中表示这一点。
 * 
 * 
 * @author Valerie Peng
 *
 * @since 1.5
 */
public class ECPoint {

    private final BigInteger x;
    private final BigInteger y;

    /**
     * This defines the point at infinity.
     * <p>
     *  这定义了无穷远处的点。
     * 
     */
    public static final ECPoint POINT_INFINITY = new ECPoint();

    // private constructor for constructing point at infinity
    private ECPoint() {
        this.x = null;
        this.y = null;
    }

    /**
     * Creates an ECPoint from the specified affine x-coordinate
     * {@code x} and affine y-coordinate {@code y}.
     * <p>
     *  从指定的仿射x坐标{@code x}和仿射y坐标{@code y}创建一个ECPoint。
     * 
     * 
     * @param x the affine x-coordinate.
     * @param y the affine y-coordinate.
     * @exception NullPointerException if {@code x} or
     * {@code y} is null.
     */
    public ECPoint(BigInteger x, BigInteger y) {
        if ((x==null) || (y==null)) {
            throw new NullPointerException("affine coordinate x or y is null");
        }
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the affine x-coordinate {@code x}.
     * Note: POINT_INFINITY has a null affine x-coordinate.
     * <p>
     *  返回仿射x坐标{@code x}。注意：POINT_INFINITY有一个零仿射x坐标。
     * 
     * 
     * @return the affine x-coordinate.
     */
    public BigInteger getAffineX() {
        return x;
    }

    /**
     * Returns the affine y-coordinate {@code y}.
     * Note: POINT_INFINITY has a null affine y-coordinate.
     * <p>
     *  返回仿射y坐标{@code y}。注意：POINT_INFINITY有一个零仿射y坐标。
     * 
     * 
     * @return the affine y-coordinate.
     */
    public BigInteger getAffineY() {
        return y;
    }

    /**
     * Compares this elliptic curve point for equality with
     * the specified object.
     * <p>
     *  将此椭圆曲线点与指定对象进行比较。
     * 
     * 
     * @param obj the object to be compared.
     * @return true if {@code obj} is an instance of
     * ECPoint and the affine coordinates match, false otherwise.
     */
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (this == POINT_INFINITY) return false;
        if (obj instanceof ECPoint) {
            return ((x.equals(((ECPoint)obj).x)) &&
                    (y.equals(((ECPoint)obj).y)));
        }
        return false;
    }

    /**
     * Returns a hash code value for this elliptic curve point.
     * <p>
     *  返回此椭圆曲线点的哈希码值。
     * 
     * @return a hash code value.
     */
    public int hashCode() {
        if (this == POINT_INFINITY) return 0;
        return x.hashCode() << 5 + y.hashCode();
    }
}
