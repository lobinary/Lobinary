/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2006, 2011, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.geom.AffineTransform;
import java.awt.image.ColorModel;
import java.lang.ref.SoftReference;
import java.util.Arrays;

/**
 * This is the superclass for Paints which use a multiple color
 * gradient to fill in their raster.  It provides storage for variables and
 * enumerated values common to
 * {@code LinearGradientPaint} and {@code RadialGradientPaint}.
 *
 * <p>
 *  这是油漆的超类,它使用多颜色渐变填充它们的栅格。它为{@code LinearGradientPaint}和{@code RadialGradientPaint}常用的变量和枚举值提供存储。
 * 
 * 
 * @author Nicholas Talian, Vincent Hardy, Jim Graham, Jerry Evans
 * @since 1.6
 */
public abstract class MultipleGradientPaint implements Paint {

    /** The method to use when painting outside the gradient bounds.
    /* <p>
    /* 
     * @since 1.6
     */
    public static enum CycleMethod {
        /**
         * Use the terminal colors to fill the remaining area.
         * <p>
         *  使用端子颜色填充剩余区域。
         * 
         */
        NO_CYCLE,

        /**
         * Cycle the gradient colors start-to-end, end-to-start
         * to fill the remaining area.
         * <p>
         *  循环渐变颜色的开始到结束,结束到开始填充剩余的区域。
         * 
         */
        REFLECT,

        /**
         * Cycle the gradient colors start-to-end, start-to-end
         * to fill the remaining area.
         * <p>
         *  循环渐变颜色的开始到结束,从头到尾填充剩余的区域。
         * 
         */
        REPEAT
    }

    /** The color space in which to perform the gradient interpolation.
    /* <p>
    /* 
     * @since 1.6
     */
    public static enum ColorSpaceType {
        /**
         * Indicates that the color interpolation should occur in sRGB space.
         * <p>
         *  表示颜色插值应发生在sRGB空间中。
         * 
         */
        SRGB,

        /**
         * Indicates that the color interpolation should occur in linearized
         * RGB space.
         * <p>
         *  表示颜色插值应在线性化的RGB空间中进行。
         * 
         */
        LINEAR_RGB
    }

    /** The transparency of this paint object. */
    final int transparency;

    /** Gradient keyframe values in the range 0 to 1. */
    final float[] fractions;

    /** Gradient colors. */
    final Color[] colors;

    /** Transform to apply to gradient. */
    final AffineTransform gradientTransform;

    /** The method to use when painting outside the gradient bounds. */
    final CycleMethod cycleMethod;

    /** The color space in which to perform the gradient interpolation. */
    final ColorSpaceType colorSpace;

    /**
     * The following fields are used only by MultipleGradientPaintContext
     * to cache certain values that remain constant and do not need to be
     * recalculated for each context created from this paint instance.
     * <p>
     *  以下字段仅由MultipleGradientPaintContext用于缓存某些值,这些值保持不变,并且不需要为从此绘制实例创建的每个上下文重新计算。
     * 
     */
    ColorModel model;
    float[] normalizedIntervals;
    boolean isSimpleLookup;
    SoftReference<int[][]> gradients;
    SoftReference<int[]> gradient;
    int fastGradientArraySize;

    /**
     * Package-private constructor.
     *
     * <p>
     *  包私有构造函数。
     * 
     * 
     * @param fractions numbers ranging from 0.0 to 1.0 specifying the
     *                  distribution of colors along the gradient
     * @param colors array of colors corresponding to each fractional value
     * @param cycleMethod either {@code NO_CYCLE}, {@code REFLECT},
     *                    or {@code REPEAT}
     * @param colorSpace which color space to use for interpolation,
     *                   either {@code SRGB} or {@code LINEAR_RGB}
     * @param gradientTransform transform to apply to the gradient
     *
     * @throws NullPointerException
     * if {@code fractions} array is null,
     * or {@code colors} array is null,
     * or {@code gradientTransform} is null,
     * or {@code cycleMethod} is null,
     * or {@code colorSpace} is null
     * @throws IllegalArgumentException
     * if {@code fractions.length != colors.length},
     * or {@code colors} is less than 2 in size,
     * or a {@code fractions} value is less than 0.0 or greater than 1.0,
     * or the {@code fractions} are not provided in strictly increasing order
     */
    MultipleGradientPaint(float[] fractions,
                          Color[] colors,
                          CycleMethod cycleMethod,
                          ColorSpaceType colorSpace,
                          AffineTransform gradientTransform)
    {
        if (fractions == null) {
            throw new NullPointerException("Fractions array cannot be null");
        }

        if (colors == null) {
            throw new NullPointerException("Colors array cannot be null");
        }

        if (cycleMethod == null) {
            throw new NullPointerException("Cycle method cannot be null");
        }

        if (colorSpace == null) {
            throw new NullPointerException("Color space cannot be null");
        }

        if (gradientTransform == null) {
            throw new NullPointerException("Gradient transform cannot be "+
                                           "null");
        }

        if (fractions.length != colors.length) {
            throw new IllegalArgumentException("Colors and fractions must " +
                                               "have equal size");
        }

        if (colors.length < 2) {
            throw new IllegalArgumentException("User must specify at least " +
                                               "2 colors");
        }

        // check that values are in the proper range and progress
        // in increasing order from 0 to 1
        float previousFraction = -1.0f;
        for (float currentFraction : fractions) {
            if (currentFraction < 0f || currentFraction > 1f) {
                throw new IllegalArgumentException("Fraction values must " +
                                                   "be in the range 0 to 1: " +
                                                   currentFraction);
            }

            if (currentFraction <= previousFraction) {
                throw new IllegalArgumentException("Keyframe fractions " +
                                                   "must be increasing: " +
                                                   currentFraction);
            }

            previousFraction = currentFraction;
        }

        // We have to deal with the cases where the first gradient stop is not
        // equal to 0 and/or the last gradient stop is not equal to 1.
        // In both cases, create a new point and replicate the previous
        // extreme point's color.
        boolean fixFirst = false;
        boolean fixLast = false;
        int len = fractions.length;
        int off = 0;

        if (fractions[0] != 0f) {
            // first stop is not equal to zero, fix this condition
            fixFirst = true;
            len++;
            off++;
        }
        if (fractions[fractions.length-1] != 1f) {
            // last stop is not equal to one, fix this condition
            fixLast = true;
            len++;
        }

        this.fractions = new float[len];
        System.arraycopy(fractions, 0, this.fractions, off, fractions.length);
        this.colors = new Color[len];
        System.arraycopy(colors, 0, this.colors, off, colors.length);

        if (fixFirst) {
            this.fractions[0] = 0f;
            this.colors[0] = colors[0];
        }
        if (fixLast) {
            this.fractions[len-1] = 1f;
            this.colors[len-1] = colors[colors.length - 1];
        }

        // copy some flags
        this.colorSpace = colorSpace;
        this.cycleMethod = cycleMethod;

        // copy the gradient transform
        this.gradientTransform = new AffineTransform(gradientTransform);

        // determine transparency
        boolean opaque = true;
        for (int i = 0; i < colors.length; i++){
            opaque = opaque && (colors[i].getAlpha() == 0xff);
        }
        this.transparency = opaque ? OPAQUE : TRANSLUCENT;
    }

    /**
     * Returns a copy of the array of floats used by this gradient
     * to calculate color distribution.
     * The returned array always has 0 as its first value and 1 as its
     * last value, with increasing values in between.
     *
     * <p>
     *  返回此渐变用于计算颜色分布的浮点数组的副本。返回的数组总是具有0作为其第一个值,1作为其最后一个值,两者之间的值增加。
     * 
     * 
     * @return a copy of the array of floats used by this gradient to
     * calculate color distribution
     */
    public final float[] getFractions() {
        return Arrays.copyOf(fractions, fractions.length);
    }

    /**
     * Returns a copy of the array of colors used by this gradient.
     * The first color maps to the first value in the fractions array,
     * and the last color maps to the last value in the fractions array.
     *
     * <p>
     *  返回此渐变使用的颜色数组的副本。第一种颜色映射到fractions数组中的第一个值,最后一个颜色映射到fractions数组中的最后一个值。
     * 
     * 
     * @return a copy of the array of colors used by this gradient
     */
    public final Color[] getColors() {
        return Arrays.copyOf(colors, colors.length);
    }

    /**
     * Returns the enumerated type which specifies cycling behavior.
     *
     * <p>
     *  返回指定循环行为的枚举类型。
     * 
     * 
     * @return the enumerated type which specifies cycling behavior
     */
    public final CycleMethod getCycleMethod() {
        return cycleMethod;
    }

    /**
     * Returns the enumerated type which specifies color space for
     * interpolation.
     *
     * <p>
     *  返回指定用于插值的颜色空间的枚举类型。
     * 
     * 
     * @return the enumerated type which specifies color space for
     * interpolation
     */
    public final ColorSpaceType getColorSpace() {
        return colorSpace;
    }

    /**
     * Returns a copy of the transform applied to the gradient.
     *
     * <p>
     * Note that if no transform is applied to the gradient
     * when it is created, the identity transform is used.
     *
     * <p>
     * 返回应用于渐变的变换的副本。
     * 
     * <p>
     *  请注意,如果在创建梯度时未对其应用变换,那么将使用标识转换。
     * 
     * @return a copy of the transform applied to the gradient
     */
    public final AffineTransform getTransform() {
        return new AffineTransform(gradientTransform);
    }

    /**
     * Returns the transparency mode for this {@code Paint} object.
     *
     * <p>
     * 
     * 
     * @return {@code OPAQUE} if all colors used by this
     *         {@code Paint} object are opaque,
     *         {@code TRANSLUCENT} if at least one of the
     *         colors used by this {@code Paint} object is not opaque.
     * @see java.awt.Transparency
     */
    public final int getTransparency() {
        return transparency;
    }
}
