/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2006, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.plaf.nimbus;

import sun.awt.AppContext;

import java.awt.image.BufferedImage;
import java.lang.ref.SoftReference;

/**
 * Effect
 *
 * <p>
 *  影响
 * 
 * 
 * @author Created by Jasper Potts (Jun 18, 2007)
 */
abstract class Effect {
    enum EffectType {
        UNDER, BLENDED, OVER
    }

    // =================================================================================================================
    // Abstract Methods

    /**
     * Get the type of this effect, one of UNDER,BLENDED,OVER. UNDER means the result of apply effect should be painted
     * under the src image. BLENDED means the result of apply sffect contains a modified src image so just it should be
     * painted. OVER means the result of apply effect should be painted over the src image.
     *
     * <p>
     *  获得这种效果的类型,UNDER,BLENDED,OVER之一。 UNDER表示应用效果的结果应该在src图像下。
     *  BLENDED表示apply sffect的结果包含一个修改后的src映像,所以只是它应该被绘制。 OVER表示应用效果的结果应该被绘制在src图像上。
     * 
     * 
     * @return The effect type
     */
    abstract EffectType getEffectType();

    /**
     * Get the opacity to use to paint the result effected image if the EffectType is UNDER or OVER.
     *
     * <p>
     *  如果EffectType为UNDER或OVER,请获取用于绘制结果受影响图像的不透明度。
     * 
     * 
     * @return The opactity for the effect, 0.0f -> 1.0f
     */
    abstract float getOpacity();

    /**
     * Apply the effect to the src image generating the result . The result image may or may not contain the source
     * image depending on what the effect type is.
     *
     * <p>
     *  将效果应用于生成结果的src图像。结果图像可能包含或可能不包含源图像,具体取决于效果类型。
     * 
     * @param src The source image for applying the effect to
     * @param dst The dstination image to paint effect result into. If this is null then a new image will be created
     * @param w   The width of the src image to apply effect to, this allow the src and dst buffers to be bigger than
     *            the area the need effect applied to it
     * @param h   The height of the src image to apply effect to, this allow the src and dst buffers to be bigger than
     *            the area the need effect applied to it
     * @return The result of appl
     */
    abstract BufferedImage applyEffect(BufferedImage src, BufferedImage dst, int w, int h);

    // =================================================================================================================
    // Static data cache

    protected static ArrayCache getArrayCache() {
        ArrayCache cache = (ArrayCache)AppContext.getAppContext().get(ArrayCache.class);
        if (cache == null){
            cache = new ArrayCache();
            AppContext.getAppContext().put(ArrayCache.class,cache);
        }
        return cache;
    }

    protected static class ArrayCache {
        private SoftReference<int[]> tmpIntArray = null;
        private SoftReference<byte[]> tmpByteArray1 = null;
        private SoftReference<byte[]> tmpByteArray2 = null;
        private SoftReference<byte[]> tmpByteArray3 = null;

        protected int[] getTmpIntArray(int size) {
            int[] tmp;
            if (tmpIntArray == null || (tmp = tmpIntArray.get()) == null || tmp.length < size) {
                // create new array
                tmp = new int[size];
                tmpIntArray = new SoftReference<int[]>(tmp);
            }
            return tmp;
        }

        protected byte[] getTmpByteArray1(int size) {
            byte[] tmp;
            if (tmpByteArray1 == null || (tmp = tmpByteArray1.get()) == null || tmp.length < size) {
                // create new array
                tmp = new byte[size];
                tmpByteArray1 = new SoftReference<byte[]>(tmp);
            }
            return tmp;
        }

        protected byte[] getTmpByteArray2(int size) {
            byte[] tmp;
            if (tmpByteArray2 == null || (tmp = tmpByteArray2.get()) == null || tmp.length < size) {
                // create new array
                tmp = new byte[size];
                tmpByteArray2 = new SoftReference<byte[]>(tmp);
            }
            return tmp;
        }

        protected byte[] getTmpByteArray3(int size) {
            byte[] tmp;
            if (tmpByteArray3 == null || (tmp = tmpByteArray3.get()) == null || tmp.length < size) {
                // create new array
                tmp = new byte[size];
                tmpByteArray3 = new SoftReference<byte[]>(tmp);
            }
            return tmp;
        }
    }
}
