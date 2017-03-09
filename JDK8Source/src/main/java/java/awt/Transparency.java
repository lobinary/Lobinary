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

package java.awt;

import java.lang.annotation.Native;

/**
 * The <code>Transparency</code> interface defines the common transparency
 * modes for implementing classes.
 * <p>
 *  <code> Transparency </code>接口定义了实现类的通用透明模式。
 * 
 */
public interface Transparency {

    /**
     * Represents image data that is guaranteed to be completely opaque,
     * meaning that all pixels have an alpha value of 1.0.
     * <p>
     *  表示保证完全不透明的图像数据,表示所有像素的alpha值为1.0。
     * 
     */
    @Native public final static int OPAQUE            = 1;

    /**
     * Represents image data that is guaranteed to be either completely
     * opaque, with an alpha value of 1.0, or completely transparent,
     * with an alpha value of 0.0.
     * <p>
     *  表示确保为完全不透明,alpha值为1.0或完全透明,且alpha值为0.0的图像数据。
     * 
     */
    @Native public final static int BITMASK = 2;

    /**
     * Represents image data that contains or might contain arbitrary
     * alpha values between and including 0.0 and 1.0.
     * <p>
     *  表示包含或可能包含0.0和1.0之间且包括0.0和1.0的任意α值的图像数据。
     * 
     */
    @Native public final static int TRANSLUCENT        = 3;

    /**
     * Returns the type of this <code>Transparency</code>.
     * <p>
     *  返回此<code> Transparency </code>的类型。
     * 
     * @return the field type of this <code>Transparency</code>, which is
     *          either OPAQUE, BITMASK or TRANSLUCENT.
     */
    public int getTransparency();
}
