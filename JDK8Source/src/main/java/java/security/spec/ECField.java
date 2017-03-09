/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
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
 * This interface represents an elliptic curve (EC) finite field.
 * All specialized EC fields must implements this interface.
 *
 * <p>
 *  该界面表示椭圆曲线(EC)有限域。所有专用的EC字段必须实现此接口。
 * 
 * 
 * @see ECFieldFp
 * @see ECFieldF2m
 *
 * @author Valerie Peng
 *
 * @since 1.5
 */
public interface ECField {
    /**
     * Returns the field size in bits. Note: For prime finite
     * field ECFieldFp, size of prime p in bits is returned.
     * For characteristic 2 finite field ECFieldF2m, m is returned.
     * <p>
     *  以位为单位返回字段大小。注意：对于素数有限域ECFieldFp,返回以位为单位的素数p的大小。对于特征2的有限域ECFieldF2m,返回m。
     * 
     * @return the field size in bits.
     */
    int getFieldSize();
}
