/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.util.Map;
import java.util.Set;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import sun.awt.SunHints;
import java.lang.ref.WeakReference;

/**
 * The {@code RenderingHints} class defines and manages collections of
 * keys and associated values which allow an application to provide input
 * into the choice of algorithms used by other classes which perform
 * rendering and image manipulation services.
 * The {@link java.awt.Graphics2D} class, and classes that implement
 * {@link java.awt.image.BufferedImageOp} and
 * {@link java.awt.image.RasterOp} all provide methods to get and
 * possibly to set individual or groups of {@code RenderingHints}
 * keys and their associated values.
 * When those implementations perform any rendering or image manipulation
 * operations they should examine the values of any {@code RenderingHints}
 * that were requested by the caller and tailor the algorithms used
 * accordingly and to the best of their ability.
 * <p>
 * Note that since these keys and values are <i>hints</i>, there is
 * no requirement that a given implementation supports all possible
 * choices indicated below or that it can respond to requests to
 * modify its choice of algorithm.
 * The values of the various hint keys may also interact such that
 * while all variants of a given key are supported in one situation,
 * the implementation may be more restricted when the values associated
 * with other keys are modified.
 * For example, some implementations may be able to provide several
 * types of dithering when the antialiasing hint is turned off, but
 * have little control over dithering when antialiasing is on.
 * The full set of supported keys and hints may also vary by destination
 * since runtimes may use different underlying modules to render to
 * the screen, or to {@link java.awt.image.BufferedImage} objects,
 * or while printing.
 * <p>
 * Implementations are free to ignore the hints completely, but should
 * try to use an implementation algorithm that is as close as possible
 * to the request.
 * If an implementation supports a given algorithm when any value is used
 * for an associated hint key, then minimally it must do so when the
 * value for that key is the exact value that specifies the algorithm.
 * <p>
 * The keys used to control the hints are all special values that
 * subclass the associated {@link RenderingHints.Key} class.
 * Many common hints are expressed below as static constants in this
 * class, but the list is not meant to be exhaustive.
 * Other hints may be created by other packages by defining new objects
 * which subclass the {@code Key} class and defining the associated values.
 * <p>
 *  {@code RenderingHints}类定义和管理键和相关值的集合,允许应用程序为执行呈现和图像处理服务的其他类使用的算法选择提供输入。
 *  {@link java.awt.Graphics2D}类和实现{@link java.awt.image.BufferedImageOp}和{@link java.awt.image.RasterOp}
 * 的类都提供了获取和可能设置个人或{@code RenderingHints}键及其关联值的组。
 *  {@code RenderingHints}类定义和管理键和相关值的集合,允许应用程序为执行呈现和图像处理服务的其他类使用的算法选择提供输入。
 * 当这些实现执行任何渲染或图像处理操作时,它们应该检查调用者所请求的任何{@code RenderingHints}的值,并且相应地调整所使用的算法并最大限度地利用它们的能力。
 * <p>
 * 注意,由于这些键和值是<h> </i>,所以不需要给定的实现支持下面指示的所有可能的选择,或者它可以响应于修改其选择的算法的请求。
 * 各种提示键的值也可以交互,使得虽然在一种情况下支持给定键的所有变体,但是当与其他键相关联的值被修改时,实现可能更受限制。
 * 例如,当抗锯齿提示关闭时,一些实施方式可能能够提供若干类型的抖动,但是当抗锯齿开启时对抖动几乎没有控制。
 * 完整的受支持的键和提示也可能因目标而异,因为运行时可能使用不同的底层模块来渲染到屏幕,{@link java.awt.image.BufferedImage}对象或打印时。
 * <p>
 *  实现可以完全忽略这些提示,但应尽量使用尽可能接近请求的实现算法。如果实现支持给定算法,当任何值用于相关联的提示键时,则最低限度地当该键的值是指定该算法的确切值时必须这样做。
 * <p>
 * 用于控制提示的键都是属于相关{@link RenderingHints.Key}类的特殊值。许多常见的提示在下面表示为这个类中的静态常量,但是列表并不意味着是穷举的。
 * 其他提示可以通过定义新对象来创建其他包,子类{@code Key}类并定义相关的值。
 * 
 */
public class RenderingHints
    implements Map<Object,Object>, Cloneable
{
    /**
     * Defines the base type of all keys used along with the
     * {@link RenderingHints} class to control various
     * algorithm choices in the rendering and imaging pipelines.
     * Instances of this class are immutable and unique which
     * means that tests for matches can be made using the
     * {@code ==} operator instead of the more expensive
     * {@code equals()} method.
     * <p>
     *  定义与{@link RenderingHints}类一起使用的所有键的基本类型,以控制渲染和映像管道中的各种算法选择。
     * 这个类的实例是不可变的和唯一的,这意味着可以使用{@code ==}操作符而不是更昂贵的{@code equals()}方法来进行匹配的测试。
     * 
     */
    public abstract static class Key {
        private static HashMap<Object,Object> identitymap = new HashMap<>(17);

        private String getIdentity() {
            // Note that the identity string is dependent on 3 variables:
            //     - the name of the subclass of Key
            //     - the identityHashCode of the subclass of Key
            //     - the integer key of the Key
            // It is theoretically possible for 2 distinct keys to collide
            // along all 3 of those attributes in the context of multiple
            // class loaders, but that occurrence will be extremely rare and
            // we account for that possibility below in the recordIdentity
            // method by slightly relaxing our uniqueness guarantees if we
            // end up in that situation.
            return getClass().getName()+"@"+
                Integer.toHexString(System.identityHashCode(getClass()))+":"+
                Integer.toHexString(privatekey);
        }

        private synchronized static void recordIdentity(Key k) {
            Object identity = k.getIdentity();
            Object otherref = identitymap.get(identity);
            if (otherref != null) {
                Key otherkey = (Key) ((WeakReference) otherref).get();
                if (otherkey != null && otherkey.getClass() == k.getClass()) {
                    throw new IllegalArgumentException(identity+
                                                       " already registered");
                }
                // Note that this system can fail in a mostly harmless
                // way.  If we end up generating the same identity
                // String for 2 different classes (a very rare case)
                // then we correctly avoid throwing the exception above,
                // but we are about to drop through to a statement that
                // will replace the entry for the old Key subclass with
                // an entry for the new Key subclass.  At that time the
                // old subclass will be vulnerable to someone generating
                // a duplicate Key instance for it.  We could bail out
                // of the method here and let the old identity keep its
                // record in the map, but we are more likely to see a
                // duplicate key go by for the new class than the old
                // one since the new one is probably still in the
                // initialization stage.  In either case, the probability
                // of loading 2 classes in the same VM with the same name
                // and identityHashCode should be nearly impossible.
            }
            // Note: Use a weak reference to avoid holding on to extra
            // objects and classes after they should be unloaded.
            identitymap.put(identity, new WeakReference<Key>(k));
        }

        private int privatekey;

        /**
         * Construct a key using the indicated private key.  Each
         * subclass of Key maintains its own unique domain of integer
         * keys.  No two objects with the same integer key and of the
         * same specific subclass can be constructed.  An exception
         * will be thrown if an attempt is made to construct another
         * object of a given class with the same integer key as a
         * pre-existing instance of that subclass of Key.
         * <p>
         *  使用指定的私钥构造密钥。 Key的每个子类维护其自己的唯一域的整数键。不能构造具有相同整数键和相同特定子类的两个对象。
         * 如果尝试使用与该Key子类的预先存在的实例相同的整数键来构造给定类的另一个对象,则会抛出异常。
         * 
         * 
         * @param privatekey the specified key
         */
        protected Key(int privatekey) {
            this.privatekey = privatekey;
            recordIdentity(this);
        }

        /**
         * Returns true if the specified object is a valid value
         * for this Key.
         * <p>
         *  如果指定的对象是此密钥的有效值,则返回true。
         * 
         * 
         * @param val the <code>Object</code> to test for validity
         * @return <code>true</code> if <code>val</code> is valid;
         *         <code>false</code> otherwise.
         */
        public abstract boolean isCompatibleValue(Object val);

        /**
         * Returns the private integer key that the subclass
         * instantiated this Key with.
         * <p>
         *  返回子类实例化此Key的私有整数键。
         * 
         * 
         * @return the private integer key that the subclass
         * instantiated this Key with.
         */
        protected final int intKey() {
            return privatekey;
        }

        /**
         * The hash code for all Key objects will be the same as the
         * system identity code of the object as defined by the
         * System.identityHashCode() method.
         * <p>
         *  所有Key对象的哈希码将与System.identityHashCode()方法定义的对象的系统标识代码相同。
         * 
         */
        public final int hashCode() {
            return super.hashCode();
        }

        /**
         * The equals method for all Key objects will return the same
         * result as the equality operator '=='.
         * <p>
         * 所有Key对象的equals方法将返回与相等运算符"=="相同的结果。
         * 
         */
        public final boolean equals(Object o) {
            return this == o;
        }
    }

    HashMap<Object,Object> hintmap = new HashMap<>(7);

    /**
     * Antialiasing hint key.
     * The {@code ANTIALIASING} hint controls whether or not the
     * geometry rendering methods of a {@link Graphics2D} object
     * will attempt to reduce aliasing artifacts along the edges
     * of shapes.
     * <p>
     * A typical antialiasing algorithm works by blending the existing
     * colors of the pixels along the boundary of a shape with the
     * requested fill paint according to the estimated partial pixel
     * coverage of the shape.
     * <p>
     * The allowable values for this hint are
     * <ul>
     * <li>{@link #VALUE_ANTIALIAS_ON}
     * <li>{@link #VALUE_ANTIALIAS_OFF}
     * <li>{@link #VALUE_ANTIALIAS_DEFAULT}
     * </ul>
     * <p>
     *  抗锯齿提示键。 {@code ANTIALIASING}提示控制{@link Graphics2D}对象的几何渲染方法是否会尝试减少沿形状边缘的混叠伪影。
     * <p>
     *  典型的抗锯齿算法通过根据所估计的形状的部分像素覆盖,沿着形状的边界将所述像素的现有颜色与所请求的填充颜色混合来工作。
     * <p>
     *  此提示的允许值为
     * <ul>
     *  <li> {@ link #VALUE_ANTIALIAS_ON} <li> {@ link #VALUE_ANTIALIAS_OFF} <li> {@ link #VALUE_ANTIALIAS_DEFAULT}
     * 。
     * </ul>
     */
    public static final Key KEY_ANTIALIASING =
        SunHints.KEY_ANTIALIASING;

    /**
     * Antialiasing hint value -- rendering is done with antialiasing.
     * <p>
     *  抗锯齿提示值 - 使用抗锯齿完成渲染。
     * 
     * 
     * @see #KEY_ANTIALIASING
     */
    public static final Object VALUE_ANTIALIAS_ON =
        SunHints.VALUE_ANTIALIAS_ON;

    /**
     * Antialiasing hint value -- rendering is done without antialiasing.
     * <p>
     *  抗锯齿提示值 - 渲染是在没有抗锯齿的情况下完成的。
     * 
     * 
     * @see #KEY_ANTIALIASING
     */
    public static final Object VALUE_ANTIALIAS_OFF =
        SunHints.VALUE_ANTIALIAS_OFF;

    /**
     * Antialiasing hint value -- rendering is done with a default
     * antialiasing mode chosen by the implementation.
     * <p>
     *  抗锯齿提示值 - 使用由实现选择的默认抗锯齿模式进行渲染。
     * 
     * 
     * @see #KEY_ANTIALIASING
     */
    public static final Object VALUE_ANTIALIAS_DEFAULT =
         SunHints.VALUE_ANTIALIAS_DEFAULT;

    /**
     * Rendering hint key.
     * The {@code RENDERING} hint is a general hint that provides
     * a high level recommendation as to whether to bias algorithm
     * choices more for speed or quality when evaluating tradeoffs.
     * This hint could be consulted for any rendering or image
     * manipulation operation, but decisions will usually honor
     * other, more specific hints in preference to this hint.
     * <p>
     * The allowable values for this hint are
     * <ul>
     * <li>{@link #VALUE_RENDER_SPEED}
     * <li>{@link #VALUE_RENDER_QUALITY}
     * <li>{@link #VALUE_RENDER_DEFAULT}
     * </ul>
     * <p>
     *  渲染提示键。 {@code RENDERING}提示是一个一般提示,提供了关于在评估权衡时是否偏好速度或质量的算法选择的高级别建议。
     * 对于任何渲染或图像操纵操作,可以参考这个提示,但是决策通常会优先于该提示尊重其他更具体的提示。
     * <p>
     *  此提示的允许值为
     * <ul>
     *  <li> {@ link #VALUE_RENDER_SPEED} <li> {@ link #VALUE_RENDER_QUALITY} <li> {@ link #VALUE_RENDER_DEFAULT}
     * 。
     * </ul>
     */
    public static final Key KEY_RENDERING =
         SunHints.KEY_RENDERING;

    /**
     * Rendering hint value -- rendering algorithms are chosen
     * with a preference for output speed.
     * <p>
     * 渲染提示值 - 渲染算法是根据输出速度的偏好选择的。
     * 
     * 
     * @see #KEY_RENDERING
     */
    public static final Object VALUE_RENDER_SPEED =
         SunHints.VALUE_RENDER_SPEED;

    /**
     * Rendering hint value -- rendering algorithms are chosen
     * with a preference for output quality.
     * <p>
     *  渲染提示值 - 渲染算法是根据输出质量的偏好选择的。
     * 
     * 
     * @see #KEY_RENDERING
     */
    public static final Object VALUE_RENDER_QUALITY =
         SunHints.VALUE_RENDER_QUALITY;

    /**
     * Rendering hint value -- rendering algorithms are chosen
     * by the implementation for a good tradeoff of performance
     * vs. quality.
     * <p>
     *  渲染提示值 - 渲染算法通过实现来选择,以实现性能与质量的良好折衷。
     * 
     * 
     * @see #KEY_RENDERING
     */
    public static final Object VALUE_RENDER_DEFAULT =
         SunHints.VALUE_RENDER_DEFAULT;

    /**
     * Dithering hint key.
     * The {@code DITHERING} hint controls how closely to approximate
     * a color when storing into a destination with limited color
     * resolution.
     * <p>
     * Some rendering destinations may support a limited number of
     * color choices which may not be able to accurately represent
     * the full spectrum of colors that can result during rendering
     * operations.
     * For such a destination the {@code DITHERING} hint controls
     * whether rendering is done with a flat solid fill of a single
     * pixel value which is the closest supported color to what was
     * requested, or whether shapes will be filled with a pattern of
     * colors which combine to better approximate that color.
     * <p>
     * The allowable values for this hint are
     * <ul>
     * <li>{@link #VALUE_DITHER_DISABLE}
     * <li>{@link #VALUE_DITHER_ENABLE}
     * <li>{@link #VALUE_DITHER_DEFAULT}
     * </ul>
     * <p>
     *  抖动提示键。 {@code DITHERING}提示控制在将颜色存储到具有有限颜色分辨率的目标时,对颜色的近似程度。
     * <p>
     *  一些渲染目的地可以支持有限数量的颜色选择,其可能不能精确地表示在渲染操作期间可能导致的全部颜色光谱。
     * 对于这样的目的地,{@code DITHERING}提示控制是否使用单个像素值的平坦实心填充来完成渲染,该单个像素值是所请求的最接近的支持颜色,或者形状是否将填充有组合的颜色模式以更好地近似该颜色。
     * <p>
     *  此提示的允许值为
     * <ul>
     *  <li> {@ link #VALUE_DITHER_DISABLE} <li> {@ link #VALUE_DITHER_ENABLE} <li> {@ link #VALUE_DITHER_DEFAULT}
     * 。
     * </ul>
     */
    public static final Key KEY_DITHERING =
         SunHints.KEY_DITHERING;

    /**
     * Dithering hint value -- do not dither when rendering geometry.
     * <p>
     *  抖动提示值 - 在渲染几何时不进行抖动。
     * 
     * 
     * @see #KEY_DITHERING
     */
    public static final Object VALUE_DITHER_DISABLE =
         SunHints.VALUE_DITHER_DISABLE;

    /**
     * Dithering hint value -- dither when rendering geometry, if needed.
     * <p>
     *  抖动提示值 - 如果需要,在渲染几何体时抖动。
     * 
     * 
     * @see #KEY_DITHERING
     */
    public static final Object VALUE_DITHER_ENABLE =
         SunHints.VALUE_DITHER_ENABLE;

    /**
     * Dithering hint value -- use a default for dithering chosen by
     * the implementation.
     * <p>
     *  抖动提示值 - 对实现选择的抖动使用默认值。
     * 
     * 
     * @see #KEY_DITHERING
     */
    public static final Object VALUE_DITHER_DEFAULT =
         SunHints.VALUE_DITHER_DEFAULT;

    /**
     * Text antialiasing hint key.
     * The {@code TEXT_ANTIALIASING} hint can control the use of
     * antialiasing algorithms for text independently of the
     * choice used for shape rendering.
     * Often an application may want to use antialiasing for text
     * only and not for other shapes.
     * Additionally, the algorithms for reducing the aliasing
     * artifacts for text are often more sophisticated than those
     * that have been developed for general rendering so this
     * hint key provides additional values which can control
     * the choices of some of those text-specific algorithms.
     * If left in the {@code DEFAULT} state, this hint will
     * generally defer to the value of the regular
     * {@link #KEY_ANTIALIASING} hint key.
     * <p>
     * The allowable values for this hint are
     * <ul>
     * <li>{@link #VALUE_TEXT_ANTIALIAS_ON}
     * <li>{@link #VALUE_TEXT_ANTIALIAS_OFF}
     * <li>{@link #VALUE_TEXT_ANTIALIAS_DEFAULT}
     * <li>{@link #VALUE_TEXT_ANTIALIAS_GASP}
     * <li>{@link #VALUE_TEXT_ANTIALIAS_LCD_HRGB}
     * <li>{@link #VALUE_TEXT_ANTIALIAS_LCD_HBGR}
     * <li>{@link #VALUE_TEXT_ANTIALIAS_LCD_VRGB}
     * <li>{@link #VALUE_TEXT_ANTIALIAS_LCD_VBGR}
     * </ul>
     * <p>
     * 文本抗锯齿提示键。 {@code TEXT_ANTIALIASING}提示可以独立于用于形状渲染的选择来控制对文本的抗锯齿算法的使用。通常应用程序可能希望仅对文本使用抗锯齿,而不对其他形状使用抗锯齿。
     * 另外,用于减少文本的混叠伪像的算法通常比为了一般渲染而开发的算法更复杂,因此该提示键提供可以控制这些文本特定算法中的一些的选择的附加值。
     * 如果处于{@code DEFAULT}状态,此提示通常会转到常规{@link #KEY_ANTIALIASING}提示键的值。
     * <p>
     *  此提示的允许值为
     * <ul>
     *  <li> {@ link #VALUE_TEXT_ANTIALIAS_OFF} <li> {@ link #VALUE_TEXT_ANTIALIAS_DEFAULT} <li> {@ link #VALUE_TEXT_ANTIALIAS_GASP}
     *  <li> {@ link #VALUE_TEXT_ANTIALIAS_LCD_HRGB} <li> {@ link #VALUE_TEXT_ANTIALIAS_LCD_HBGR} <li> {@ link #VALUE_TEXT_ANTIALIAS_LCD_VRGB}
     *  <li> {@ link #VALUE_TEXT_ANTIALIAS_LCD_VBGR}。
     * </ul>
     */
    public static final Key KEY_TEXT_ANTIALIASING =
         SunHints.KEY_TEXT_ANTIALIASING;

    /**
     * Text antialiasing hint value -- text rendering is done with
     * some form of antialiasing.
     * <p>
     *  文本抗锯齿提示值 - 文本渲染使用某种形式的抗锯齿。
     * 
     * 
     * @see #KEY_TEXT_ANTIALIASING
     */
    public static final Object VALUE_TEXT_ANTIALIAS_ON =
         SunHints.VALUE_TEXT_ANTIALIAS_ON;

    /**
     * Text antialiasing hint value -- text rendering is done without
     * any form of antialiasing.
     * <p>
     *  文本抗锯齿提示值 - 文本呈现无任何形式的抗锯齿。
     * 
     * 
     * @see #KEY_TEXT_ANTIALIASING
     */
    public static final Object VALUE_TEXT_ANTIALIAS_OFF =
         SunHints.VALUE_TEXT_ANTIALIAS_OFF;

    /**
     * Text antialiasing hint value -- text rendering is done according
     * to the {@link #KEY_ANTIALIASING} hint or a default chosen by the
     * implementation.
     * <p>
     *  文本抗锯齿提示值 - 文本呈现根据{@link #KEY_ANTIALIASING}提示或实施选择的默认设置完成。
     * 
     * 
     * @see #KEY_TEXT_ANTIALIASING
     */
    public static final Object VALUE_TEXT_ANTIALIAS_DEFAULT =
         SunHints.VALUE_TEXT_ANTIALIAS_DEFAULT;

    /**
     * Text antialiasing hint value -- text rendering is requested to
     * use information in the font resource which specifies for each point
     * size whether to apply {@link #VALUE_TEXT_ANTIALIAS_ON} or
     * {@link #VALUE_TEXT_ANTIALIAS_OFF}.
     * <p>
     * TrueType fonts typically provide this information in the 'gasp' table.
     * In the absence of this information, the behaviour for a particular
     * font and size is determined by implementation defaults.
     * <p>
     * <i>Note:</i>A font designer will typically carefully hint a font for
     * the most common user interface point sizes. Consequently the 'gasp'
     * table will likely specify to use only hinting at those sizes and not
     * "smoothing". So in many cases the resulting text display is
     * equivalent to {@code VALUE_TEXT_ANTIALIAS_OFF}.
     * This may be unexpected but is correct.
     * <p>
     * Logical fonts which are composed of multiple physical fonts will for
     * consistency will use the setting most appropriate for the overall
     * composite font.
     *
     * <p>
     * 文字抗锯齿提示值 - 请求文字呈现,以使用字体资源中的信息,该字体资源为每个点大小指定是应用{@link #VALUE_TEXT_ANTIALIAS_ON}还是{@link #VALUE_TEXT_ANTIALIAS_OFF}
     * 。
     * <p>
     *  TrueType字体通常在"gasp"表中提供此信息。在没有此信息的情况下,特定字体和大小的行为由实现缺省值确定。
     * <p>
     *  <i>注意：</i>字体设计师通常会仔细地提示最常见的用户界面点大小的字体。因此,'gasp'表可能指定仅使用这些大小的提示,而不是"平滑"。
     * 因此,在许多情况下,生成的文本显示等效于{@code VALUE_TEXT_ANTIALIAS_OFF}。这可能是意外的,但是是正确的。
     * <p>
     *  由多种物理字体组成的逻辑字体将保持一致性,使用最适合于整体复合字体的设置。
     * 
     * 
     * @see #KEY_TEXT_ANTIALIASING
     * @since 1.6
     */
    public static final Object VALUE_TEXT_ANTIALIAS_GASP =
         SunHints.VALUE_TEXT_ANTIALIAS_GASP;

    /**
     * Text antialiasing hint value -- request that text be displayed
     * optimised for an LCD display with subpixels in order from display
     * left to right of R,G,B such that the horizontal subpixel resolution
     * is three times that of the full pixel horizontal resolution (HRGB).
     * This is the most common configuration.
     * Selecting this hint for displays with one of the other LCD subpixel
     * configurations will likely result in unfocused text.
     * <p>
     * <i>Notes:</i><br>
     * An implementation when choosing whether to apply any of the
     * LCD text hint values may take into account factors including requiring
     * color depth of the destination to be at least 15 bits per pixel
     * (ie 5 bits per color component),
     * characteristics of a font such as whether embedded bitmaps may
     * produce better results, or when displaying to a non-local networked
     * display device enabling it only if suitable protocols are available,
     * or ignoring the hint if performing very high resolution rendering
     * or the target device is not appropriate: eg when printing.
     * <p>
     * These hints can equally be applied when rendering to software images,
     * but these images may not then be suitable for general export, as the
     * text will have been rendered appropriately for a specific subpixel
     * organisation. Also lossy images are not a good choice, nor image
     * formats such as GIF which have limited colors.
     * So unless the image is destined solely for rendering on a
     * display device with the same configuration, some other text
     * anti-aliasing hint such as
     * {@link #VALUE_TEXT_ANTIALIAS_ON}
     * may be a better choice.
     * <p>Selecting a value which does not match the LCD display in use
     * will likely lead to a degradation in text quality.
     * On display devices (ie CRTs) which do not have the same characteristics
     * as LCD displays, the overall effect may appear similar to standard text
     * anti-aliasing, but the quality may be degraded by color distortion.
     * Analog connected LCD displays may also show little advantage over
     * standard text-antialiasing and be similar to CRTs.
     * <p>
     * In other words for the best results use an LCD display with a digital
     * display connector and specify the appropriate sub-pixel configuration.
     *
     * <p>
     *  文本抗锯齿提示值 - 请求针对具有子像素的LCD显示器按照从R,G,B的显示器左到右的顺序显示优化的文本,使得水平子像素分辨率是全像素水平分辨率(HRGB)的三倍, 。这是最常见的配置。
     * 为其他LCD子像素配置之一的显示选择此提示将可能导致未聚焦的文本。
     * <p>
     * <i>注意：</i> <br>选择是否应用任何LCD文本提示值时的实现可能会考虑到以下因素,包括要求目标的颜色深度至少为每像素15位(即5位每个颜色分量),字体的特性,例如嵌入位图是否可以产生更好的结果
     * ,或者当向非本地网络显示设备显示时仅在适当的协议可用时才启用它,或者如果执行非常高分辨率渲染则忽略该提示,目标设备不合适：例如,当打印时。
     * <p>
     * 这些提示同样可以应用于渲染到软件图像时,但这些图像可能不适合一般导出,因为文本将为特定的子像素组织适当渲染。有损图像也不是一个好的选择,也不是图像格式,如GIF有限的颜色。
     * 因此,除非图片专用于在具有相同配置的显示设备上呈现,否则一些其他文字抗锯齿提示(例如{@link #VALUE_TEXT_ANTIALIAS_ON})可能是更好的选择。
     *  <p>选择与使用中的LCD显示不匹配的值可能会导致文本质量下降。在不具有与LCD显示器相同特性的显示设备(即CRT)上,整体效果可能看起来类似于标准文本抗混叠,但是质量可能由于颜色失真而劣化。
     * 模拟连接的LCD显示器也可以显示出比标准文本抗锯齿小的优点并且类似于CRT。
     * <p>
     *  换句话说,为了获得最佳结果,使用具有数字显示连接器的LCD显示器,并指定适当的子像素配置。
     * 
     * 
     * @see #KEY_TEXT_ANTIALIASING
     * @since 1.6
     */
    public static final Object VALUE_TEXT_ANTIALIAS_LCD_HRGB =
         SunHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB;

    /**
     * Text antialiasing hint value -- request that text be displayed
     * optimised for an LCD display with subpixels in order from display
     * left to right of B,G,R such that the horizontal subpixel resolution
     * is three times that of the full pixel horizontal resolution (HBGR).
     * This is a much less common configuration than HRGB.
     * Selecting this hint for displays with one of the other LCD subpixel
     * configurations will likely result in unfocused text.
     * See {@link #VALUE_TEXT_ANTIALIAS_LCD_HRGB},
     * for more information on when this hint is applied.
     *
     * <p>
     * 文本抗锯齿提示值 - 请求针对具有子像素的LCD显示器按照从B,G,R的显示器从左到右的顺序显示优化的文本,使得水平子像素分辨率是全像素水平分辨率(HBGR)的三倍, 。这是比HRGB少得多的配置。
     * 为其他LCD子像素配置之一的显示选择此提示将可能导致未聚焦的文本。有关应用此提示的详情,请参见{@link #VALUE_TEXT_ANTIALIAS_LCD_HRGB}。
     * 
     * 
     * @see #KEY_TEXT_ANTIALIASING
     * @since 1.6
     */
    public static final Object VALUE_TEXT_ANTIALIAS_LCD_HBGR =
         SunHints.VALUE_TEXT_ANTIALIAS_LCD_HBGR;

    /**
     * Text antialiasing hint value -- request that text be displayed
     * optimised for an LCD display with subpixel organisation from display
     * top to bottom of R,G,B such that the vertical subpixel resolution is
     * three times that of the full pixel vertical resolution (VRGB).
     * Vertical orientation is very uncommon and probably mainly useful
     * for a physically rotated display.
     * Selecting this hint for displays with one of the other LCD subpixel
     * configurations will likely result in unfocused text.
     * See {@link #VALUE_TEXT_ANTIALIAS_LCD_HRGB},
     * for more information on when this hint is applied.
     *
     * <p>
     *  文本抗锯齿提示值 - 请求针对具有从R,G,B的显示器顶部到底部的子像素组织的LCD显示器优化显示文本,使得垂直子像素分辨率是全像素垂直分辨率(VRGB)的三倍。
     * 垂直方向是非常罕见的,并且可能主要用于物理旋转的显示器。为其他LCD子像素配置之一的显示选择此提示将可能导致未聚焦的文本。
     * 有关应用此提示的详情,请参见{@link #VALUE_TEXT_ANTIALIAS_LCD_HRGB}。
     * 
     * 
     * @see #KEY_TEXT_ANTIALIASING
     * @since 1.6
     */
    public static final Object VALUE_TEXT_ANTIALIAS_LCD_VRGB =
         SunHints.VALUE_TEXT_ANTIALIAS_LCD_VRGB;

    /**
     * Text antialiasing hint value -- request that text be displayed
     * optimised for an LCD display with subpixel organisation from display
     * top to bottom of B,G,R such that the vertical subpixel resolution is
     * three times that of the full pixel vertical resolution (VBGR).
     * Vertical orientation is very uncommon and probably mainly useful
     * for a physically rotated display.
     * Selecting this hint for displays with one of the other LCD subpixel
     * configurations will likely result in unfocused text.
     * See {@link #VALUE_TEXT_ANTIALIAS_LCD_HRGB},
     * for more information on when this hint is applied.
     *
     * <p>
     * 文本抗锯齿提示值 - 请求针对具有从B,G,R的显示器顶部到底部的子像素组织的LCD显示器优化显示文本,使得垂直子像素分辨率是全像素垂直分辨率(VBGR)的三倍。
     * 垂直方向是非常罕见的,并且可能主要用于物理旋转的显示器。为其他LCD子像素配置之一的显示选择此提示将可能导致未聚焦的文本。
     * 有关应用此提示的详情,请参见{@link #VALUE_TEXT_ANTIALIAS_LCD_HRGB}。
     * 
     * 
     * @see #KEY_TEXT_ANTIALIASING
     * @since 1.6
     */
    public static final Object VALUE_TEXT_ANTIALIAS_LCD_VBGR =
         SunHints.VALUE_TEXT_ANTIALIAS_LCD_VBGR;


    /**
     * LCD text contrast rendering hint key.
     * The value is an <code>Integer</code> object which is used as a text
     * contrast adjustment when used in conjunction with an LCD text
     * anti-aliasing hint such as
     * {@link #VALUE_TEXT_ANTIALIAS_LCD_HRGB}.
     * <ul>
     * <li>Values should be a positive integer in the range 100 to 250.
     * <li>A lower value (eg 100) corresponds to higher contrast text when
     * displaying dark text on a light background.
     * <li>A higher value (eg 200) corresponds to lower contrast text when
     * displaying dark text on a light background.
     * <li>A typical useful value is in the narrow range 140-180.
     * <li>If no value is specified, a system or implementation default value
     * will be applied.
     * </ul>
     * The default value can be expected to be adequate for most purposes,
     * so clients should rarely need to specify a value for this hint unless
     * they have concrete information as to an appropriate value.
     * A higher value does not mean a higher contrast, in fact the opposite
     * is true.
     * The correction is applied in a similar manner to a gamma adjustment
     * for non-linear perceptual luminance response of display systems, but
     * does not indicate a full correction for this.
     *
     * <p>
     *  LCD文本对比度渲染提示键。
     * 该值是一个<code> Integer </code>对象,当与LCD文本抗锯齿提示(如{@link #VALUE_TEXT_ANTIALIAS_LCD_HRGB})结合使用时,用作文本对比度调整。
     * <ul>
     *  <li>值应为100到250之间的正整数。<li>较低的值(例如100)对应于在浅色背景上显示深色文本时较高对比度的文本。
     *  <li>较高的值(例如200)对应于在浅色背景上显示深色文本时较低对比度的文本。 <li>典型的有用值在窄范围140-180。 <li>如果未指定值,系统或实施默认值将应用。
     * </ul>
     * 默认值可以预期足够用于大多数用途,因此客户端很少需要为此提示指定值,除非它们具有关于适当值的具体信息。更高的值不意味着更高的对比度,事实上相反的是真实的。
     * 以与用于显示系统的非线性感知亮度响应的伽马调整类似的方式应用校正,但是不针对显示系统的完全校正。
     * 
     * 
     * @see #KEY_TEXT_ANTIALIASING
     * @since 1.6
     */
    public static final Key KEY_TEXT_LCD_CONTRAST =
        SunHints.KEY_TEXT_ANTIALIAS_LCD_CONTRAST;

    /**
     * Font fractional metrics hint key.
     * The {@code FRACTIONALMETRICS} hint controls whether the positioning
     * of individual character glyphs takes into account the sub-pixel
     * accuracy of the scaled character advances of the font or whether
     * such advance vectors are rounded to an integer number of whole
     * device pixels.
     * This hint only recommends how much accuracy should be used to
     * position the glyphs and does not specify or recommend whether or
     * not the actual rasterization or pixel bounds of the glyph should
     * be modified to match.
     * <p>
     * Rendering text to a low resolution device like a screen will
     * necessarily involve a number of rounding operations as the
     * high quality and very precise definition of the shape and
     * metrics of the character glyphs must be matched to discrete
     * device pixels.
     * Ideally the positioning of glyphs during text layout would be
     * calculated by scaling the design metrics in the font according
     * to the point size, but then the scaled advance width will not
     * necessarily be an integer number of pixels.
     * If the glyphs are positioned with sub-pixel accuracy according
     * to these scaled design metrics then the rasterization would
     * ideally need to be adjusted for each possible sub-pixel origin.
     * <p>
     * Unfortunately, scaling each glyph customized to its exact
     * subpixel origin during text layout would be prohibitively
     * expensive so a simplified system based on integer device
     * positions is typically used to lay out the text.
     * The rasterization of the glyph and the scaled advance width
     * are both adjusted together to yield text that looks good at
     * device resolution and has consistent integer pixel distances
     * between glyphs that help the glyphs look uniformly and
     * consistently spaced and readable.
     * <p>
     * This process of rounding advance widths for rasterized glyphs
     * to integer distances means that the character density and the
     * overall length of a string of text will be different from the
     * theoretical design measurements due to the accumulation of
     * a series of small differences in the adjusted widths of
     * each glyph.
     * The specific differences will be different for each glyph,
     * some being wider and some being narrower than their theoretical
     * design measurements.
     * Thus the overall difference in character density and length
     * will vary by a number of factors including the font, the
     * specific device resolution being targeted, and the glyphs
     * chosen to represent the string being rendered.
     * As a result, rendering the same string at multiple device
     * resolutions can yield widely varying metrics for whole strings.
     * <p>
     * When {@code FRACTIONALMETRICS} are enabled, the true font design
     * metrics are scaled by the point size and used for layout with
     * sub-pixel accuracy.
     * The average density of glyphs and total length of a long
     * string of characters will therefore more closely match the
     * theoretical design of the font, but readability may be affected
     * since individual pairs of characters may not always appear to
     * be consistent distances apart depending on how the sub-pixel
     * accumulation of the glyph origins meshes with the device pixel
     * grid.
     * Enabling this hint may be desirable when text layout is being
     * performed that must be consistent across a wide variety of
     * output resolutions.
     * Specifically, this hint may be desirable in situations where
     * the layout of text is being previewed on a low resolution
     * device like a screen for output that will eventually be
     * rendered on a high resolution printer or typesetting device.
     * <p>
     * When disabled, the scaled design metrics are rounded or adjusted
     * to integer distances for layout.
     * The distances between any specific pair of glyphs will be more
     * uniform on the device, but the density and total length of long
     * strings may no longer match the theoretical intentions of the
     * font designer.
     * Disabling this hint will typically produce more readable results
     * on low resolution devices like computer monitors.
     * <p>
     * The allowable values for this key are
     * <ul>
     * <li>{@link #VALUE_FRACTIONALMETRICS_OFF}
     * <li>{@link #VALUE_FRACTIONALMETRICS_ON}
     * <li>{@link #VALUE_FRACTIONALMETRICS_DEFAULT}
     * </ul>
     * <p>
     *  字体小数度量提示键。 {@code FRACTIONALMETRICS}提示控制单个字符字形的定位是否考虑字体的缩放字符前进的子像素精度或者这样的前进向量是否舍入为整数个整个设备像素。
     * 此提示仅推荐应使用多少精确度来定位字形,并且不指定或建议是否应修改字形的实际栅格化或像素边界以进行匹配。
     * <p>
     * 将文本呈现到像屏幕的低分辨率设备将必然涉及多个舍入操作,因为字符字形的形状和度量的高质量和非常精确的定义必须与离散设备像素匹配。
     * 理想地,在文本布局期间字形的定位将通过根据点大小缩放字体中的设计度量来计算,但是缩放的前进宽度将不一定是整数个像素。
     * 如果根据这些缩放的设计度量以亚像素精度定位图示符,则光栅化将理想地需要针对每个可能的子像素起点进行调整。
     * <p>
     *  不幸的是,在文本布局期间缩放定制到其确切子像素原点的每个字形将是非常昂贵的,因此基于整数设备位置的简化系统通常用于布局文本。
     * 字形的光栅化和缩放的前进宽度都被一起调整以产生看起来好的设备分辨率的文本,并且在字形之间具有一致的整数像素距离,这有助于字形看起来均匀和一致地间隔和可读。
     * <p>
     * 将光栅化图像字符的提前宽度舍入为整数距离的这个过程意味着字符密度和文本字符串的总长度将不同于理论设计测量,这是由于在每个调整宽度的一系列小差异的累积字形。
     * 对于每个字形,具体差异将是不同的,一些更宽,一些比它们的理论设计测量更窄。
     * 因此,字符密度和长度的总体差异将随着许多因素而变化,这些因素包括字体,作为目标的特定设备分辨率以及选择来表示正在被呈现的字符串的字形。
     * 因此,在多个设备分辨率下渲染相同的字符串可以产生整个字符串的广泛变化的度量。
     * <p>
     * 启用{@code FRACTIONALMETRICS}时,真正的字体设计指标按点大小缩放,并用于具有亚像素精度的布局。
     * 字符的平均密度和长字符串的总长度因此将更接近地匹配字体的理论设计,但是可读性可能受到影响,因为单独的字符对可能不总是看起来间隔一致的距离,取决于子像素原点的像素累积与设备像素网格相交。
     * 当正在执行必须在各种各样的输出分辨率上一致的文本布局时,可能希望启用该提示。
     * 具体地,这种提示在文本的布局在低分辨率设备上预览的情况下可能是期望的,所述低分辨率设备例如最终将在高分辨率打印机或排版设备上呈现的用于输出的屏幕。
     * <p>
     *  禁用时,缩放的设计指标将四舍五入或调整为整数距离以进行布局。在设备上任何特定的字形对之间的距离将更均匀,但长字符串的密度和总长度可能不再匹配字体设计器的理论意图。
     * 禁用此提示通常会在低分辨率设备(如计算机显示器)上产生更多可读结果。
     * <p>
     *  此键的允许值为
     * <ul>
     *  <li> {@ link #VALUE_FRACTIONALMETRICS_OFF} <li> {@ link #VALUE_FRACTIONALMETRICS_ON} <li> {@ link #VALUE_FRACTIONALMETRICS_DEFAULT}
     * 。
     * </ul>
     */
    public static final Key KEY_FRACTIONALMETRICS =
         SunHints.KEY_FRACTIONALMETRICS;

    /**
     * Font fractional metrics hint value -- character glyphs are
     * positioned with advance widths rounded to pixel boundaries.
     * <p>
     * 字体小数度量提示值 - 字符字形使用四舍五入到像素边界的前进宽度定位。
     * 
     * 
     * @see #KEY_FRACTIONALMETRICS
     */
    public static final Object VALUE_FRACTIONALMETRICS_OFF =
         SunHints.VALUE_FRACTIONALMETRICS_OFF;

    /**
     * Font fractional metrics hint value -- character glyphs are
     * positioned with sub-pixel accuracy.
     * <p>
     *  字体小数度量提示值 - 字符字形以子像素精度定位。
     * 
     * 
     * @see #KEY_FRACTIONALMETRICS
     */
    public static final Object VALUE_FRACTIONALMETRICS_ON =
         SunHints.VALUE_FRACTIONALMETRICS_ON;

    /**
     * Font fractional metrics hint value -- character glyphs are
     * positioned with accuracy chosen by the implementation.
     * <p>
     *  字体小数度量提示值 - 字符字形按照实现选择的精度进行定位。
     * 
     * 
     * @see #KEY_FRACTIONALMETRICS
     */
    public static final Object VALUE_FRACTIONALMETRICS_DEFAULT =
         SunHints.VALUE_FRACTIONALMETRICS_DEFAULT;

    /**
     * Interpolation hint key.
     * The {@code INTERPOLATION} hint controls how image pixels are
     * filtered or resampled during an image rendering operation.
     * <p>
     * Implicitly images are defined to provide color samples at
     * integer coordinate locations.
     * When images are rendered upright with no scaling onto a
     * destination, the choice of which image pixels map to which
     * device pixels is obvious and the samples at the integer
     * coordinate locations in the image are transfered to the
     * pixels at the corresponding integer locations on the device
     * pixel grid one for one.
     * When images are rendered in a scaled, rotated, or otherwise
     * transformed coordinate system, then the mapping of device
     * pixel coordinates back to the image can raise the question
     * of what color sample to use for the continuous coordinates
     * that lie between the integer locations of the provided image
     * samples.
     * Interpolation algorithms define functions which provide a
     * color sample for any continuous coordinate in an image based
     * on the color samples at the surrounding integer coordinates.
     * <p>
     * The allowable values for this hint are
     * <ul>
     * <li>{@link #VALUE_INTERPOLATION_NEAREST_NEIGHBOR}
     * <li>{@link #VALUE_INTERPOLATION_BILINEAR}
     * <li>{@link #VALUE_INTERPOLATION_BICUBIC}
     * </ul>
     * <p>
     *  插值提示键。 {@code INTERPOLATION}提示控制在图像呈现操作期间对图像像素进行过滤或重新采样的方式。
     * <p>
     *  隐式地定义图像以在整数坐标位置处提供颜色样本。
     * 当图像在没有缩放到目的地上被直接渲染时,选择哪些图像像素映射到哪些设备像素是显而易见的,并且图像中的整数坐标位置处的采样被转移到设备像素上的对应整数位置处的像素网格一个。
     * 当在缩放,旋转或以其他方式变换的坐标系统中呈现图像时,则设备像素坐标映射回图像可以提出对于位于所​​提供的整数位置之间的连续坐标使用什么颜色样本的问题图像样本。
     * 插值算法定义基于周围整数坐标处的颜色样本为图像中的任何连续坐标提供颜色样本的函数。
     * <p>
     *  此提示的允许值为
     * <ul>
     * <li> {@ link #VALUE_INTERPOLATION_NEAREST_NEIGHBOR} <li> {@ link #VALUE_INTERPOLATION_BILINEAR} <li> 
     * {@ link #VALUE_INTERPOLATION_BICUBIC}。
     * </ul>
     */
    public static final Key KEY_INTERPOLATION =
         SunHints.KEY_INTERPOLATION;

    /**
     * Interpolation hint value -- the color sample of the nearest
     * neighboring integer coordinate sample in the image is used.
     * Conceptually the image is viewed as a grid of unit-sized
     * square regions of color centered around the center of each
     * image pixel.
     * <p>
     * As the image is scaled up, it will look correspondingly blocky.
     * As the image is scaled down, the colors for source pixels will
     * be either used unmodified, or skipped entirely in the output
     * representation.
     *
     * <p>
     *  插值提示值 - 使用图像中最近邻整数坐标样本的颜色样本。概念上,图像被视为以每个图像像素的中心为中心的单位尺寸的颜色的正方形区域的网格。
     * <p>
     *  当图像放大时,它将看起来相应地块状。当图像缩小时,源像素的颜色将被未修改地使用,或者在输出表示中完全跳过。
     * 
     * 
     * @see #KEY_INTERPOLATION
     */
    public static final Object VALUE_INTERPOLATION_NEAREST_NEIGHBOR =
         SunHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;

    /**
     * Interpolation hint value -- the color samples of the 4 nearest
     * neighboring integer coordinate samples in the image are
     * interpolated linearly to produce a color sample.
     * Conceptually the image is viewed as a set of infinitely small
     * point color samples which have value only at the centers of
     * integer coordinate pixels and the space between those pixel
     * centers is filled with linear ramps of colors that connect
     * adjacent discrete samples in a straight line.
     * <p>
     * As the image is scaled up, there are no blocky edges between
     * the colors in the image as there are with
     * {@link #VALUE_INTERPOLATION_NEAREST_NEIGHBOR NEAREST_NEIGHBOR},
     * but the blending may show some subtle discontinuities along the
     * horizontal and vertical edges that line up with the samples
     * caused by a sudden change in the slope of the interpolation
     * from one side of a sample to the other.
     * As the image is scaled down, more image pixels have their
     * color samples represented in the resulting output since each
     * output pixel receives color information from up to 4 image
     * pixels.
     *
     * <p>
     *  插值提示值 - 图像中4个最接近的相邻整数坐标样本的颜色样本被线性内插以产生颜色样本。
     * 概念上,图像被视为一组无限小点颜色样本,其仅在整数坐标像素的中心处具有值,并且那些像素中心之间的空间由以直线连接相邻离散样本的线性斜坡填充。
     * <p>
     * 当图像放大时,图像中的颜色之间没有块状边缘,因为{@link #VALUE_INTERPOLATION_NEAREST_NEIGHBOR NEAREST_NEIGHBOR},但是混合可能沿着与样本对齐的
     * 水平和垂直边缘显示一些细微的不连续这是由于从样本的一侧到另一侧的内插的斜率的突然变化引起的。
     * 当图像按比例缩小时,由于每个输出像素从多达4个图像像素接收颜色信息,所以更多的图像像素具有在所得到的输出中表示的它们的颜色样本。
     * 
     * 
     * @see #KEY_INTERPOLATION
     */
    public static final Object VALUE_INTERPOLATION_BILINEAR =
         SunHints.VALUE_INTERPOLATION_BILINEAR;

    /**
     * Interpolation hint value -- the color samples of 9 nearby
     * integer coordinate samples in the image are interpolated using
     * a cubic function in both {@code X} and {@code Y} to produce
     * a color sample.
     * Conceptually the view of the image is very similar to the view
     * used in the {@link #VALUE_INTERPOLATION_BILINEAR BILINEAR}
     * algorithm except that the ramps of colors that connect between
     * the samples are curved and have better continuity of slope
     * as they cross over between sample boundaries.
     * <p>
     * As the image is scaled up, there are no blocky edges and the
     * interpolation should appear smoother and with better depictions
     * of any edges in the original image than with {@code BILINEAR}.
     * As the image is scaled down, even more of the original color
     * samples from the original image will have their color information
     * carried through and represented.
     *
     * <p>
     *  插值提示值 - 图像中9个附近的整数坐标样本的颜色样本使用{@code X}和{@code Y}中的三次函数进行插值,以产生颜色样本。
     * 在概念上,图像的视图与{@link #VALUE_INTERPOLATION_BILINEAR BILINEAR}算法中使用的视图非常相似,除了连接在样本之间的颜色的斜坡是弯曲的,并且当它们在样本边界之
     * 间交叉时具有更好的斜率连续性。
     *  插值提示值 - 图像中9个附近的整数坐标样本的颜色样本使用{@code X}和{@code Y}中的三次函数进行插值,以产生颜色样本。
     * <p>
     *  当图像被放大时,没有块状边缘,并且与{@code BILINEAR}相比,插值应该看起来更平滑并且更好地描绘原始图像中的任何边缘。
     * 随着图像按比例缩小,来自原始图像的更多原始颜色样本将通过并表示其颜色信息。
     * 
     * 
     * @see #KEY_INTERPOLATION
     */
    public static final Object VALUE_INTERPOLATION_BICUBIC =
         SunHints.VALUE_INTERPOLATION_BICUBIC;

    /**
     * Alpha interpolation hint key.
     * The {@code ALPHA_INTERPOLATION} hint is a general hint that
     * provides a high level recommendation as to whether to bias
     * alpha blending algorithm choices more for speed or quality
     * when evaluating tradeoffs.
     * <p>
     * This hint could control the choice of alpha blending
     * calculations that sacrifice some precision to use fast
     * lookup tables or lower precision SIMD instructions.
     * This hint could also control whether or not the color
     * and alpha values are converted into a linear color space
     * during the calculations for a more linear visual effect
     * at the expense of additional per-pixel calculations.
     * <p>
     * The allowable values for this hint are
     * <ul>
     * <li>{@link #VALUE_ALPHA_INTERPOLATION_SPEED}
     * <li>{@link #VALUE_ALPHA_INTERPOLATION_QUALITY}
     * <li>{@link #VALUE_ALPHA_INTERPOLATION_DEFAULT}
     * </ul>
     * <p>
     * Alpha插值提示键。 {@code ALPHA_INTERPOLATION}提示是一个通用提示,提供了一个高级别的建议,即在评估折中时是否对速度或质量更多地偏置Alpha混合算法选择。
     * <p>
     *  这个提示可以控制α混合计算的选择,牺牲一些精度以使用快速查找表或较低精度SIMD指令。
     * 这个提示还可以控制在计算期间颜色和阿尔法值是否被转换成线性颜色空间以获得更加线性的视觉效果,而牺牲额外的每像素计算。
     * <p>
     *  此提示的允许值为
     * <ul>
     *  <li> {@ link #VALUE_ALPHA_INTERPOLATION_SPEED} <li> {@ link #VALUE_ALPHA_INTERPOLATION_QUALITY} <li>
     *  {@ link #VALUE_ALPHA_INTERPOLATION_DEFAULT}。
     * </ul>
     */
    public static final Key KEY_ALPHA_INTERPOLATION =
         SunHints.KEY_ALPHA_INTERPOLATION;

    /**
     * Alpha interpolation hint value -- alpha blending algorithms
     * are chosen with a preference for calculation speed.
     * <p>
     *  选择阿尔法内插提示值 - 阿尔法混合算法,优先选择计算速度。
     * 
     * 
     * @see #KEY_ALPHA_INTERPOLATION
     */
    public static final Object VALUE_ALPHA_INTERPOLATION_SPEED =
         SunHints.VALUE_ALPHA_INTERPOLATION_SPEED;

    /**
     * Alpha interpolation hint value -- alpha blending algorithms
     * are chosen with a preference for precision and visual quality.
     * <p>
     *  选择阿尔法内插提示值 - 阿尔法混合算法,优先选择精度和视觉质量。
     * 
     * 
     * @see #KEY_ALPHA_INTERPOLATION
     */
    public static final Object VALUE_ALPHA_INTERPOLATION_QUALITY =
         SunHints.VALUE_ALPHA_INTERPOLATION_QUALITY;

    /**
     * Alpha interpolation hint value -- alpha blending algorithms
     * are chosen by the implementation for a good tradeoff of
     * performance vs. quality.
     * <p>
     *  通过实施来选择阿尔法内插提示值 - 阿尔法混合算法,以实现性能与质量的良好折衷。
     * 
     * 
     * @see #KEY_ALPHA_INTERPOLATION
     */
    public static final Object VALUE_ALPHA_INTERPOLATION_DEFAULT =
         SunHints.VALUE_ALPHA_INTERPOLATION_DEFAULT;

    /**
     * Color rendering hint key.
     * The {@code COLOR_RENDERING} hint controls the accuracy of
     * approximation and conversion when storing colors into a
     * destination image or surface.
     * <p>
     * When a rendering or image manipulation operation produces
     * a color value that must be stored into a destination, it
     * must first convert that color into a form suitable for
     * storing into the destination image or surface.
     * Minimally, the color components must be converted to bit
     * representations and ordered in the correct order or an
     * index into a color lookup table must be chosen before
     * the data can be stored into the destination memory.
     * Without this minimal conversion, the data in the destination
     * would likely represent random, incorrect or possibly even
     * unsupported values.
     * Algorithms to quickly convert the results of rendering
     * operations into the color format of most common destinations
     * are well known and fairly optimal to execute.
     * <p>
     * Simply performing the most basic color format conversion to
     * store colors into a destination can potentially ignore a
     * difference in the calibration of the
     * {@link java.awt.color.ColorSpace}
     * of the source and destination or other factors such as the
     * linearity of the gamma correction.
     * Unless the source and destination {@code ColorSpace} are
     * identical, to correctly perform a rendering operation with
     * the most care taken for the accuracy of the colors being
     * represented, the source colors should be converted to a
     * device independent {@code ColorSpace} and the results then
     * converted back to the destination {@code ColorSpace}.
     * Furthermore, if calculations such as the blending of multiple
     * source colors are to be performed during the rendering
     * operation, greater visual clarity can be achieved if the
     * intermediate device independent {@code ColorSpace} is
     * chosen to have a linear relationship between the values
     * being calculated and the perception of the human eye to
     * the response curves of the output device.
     * <p>
     * The allowable values for this hint are
     * <ul>
     * <li>{@link #VALUE_COLOR_RENDER_SPEED}
     * <li>{@link #VALUE_COLOR_RENDER_QUALITY}
     * <li>{@link #VALUE_COLOR_RENDER_DEFAULT}
     * </ul>
     * <p>
     *  颜色渲染提示键。 {@code COLOR_RENDERING}提示控制在将颜色存储到目标图像或曲面时的近似和转换的准确性。
     * <p>
     * 当渲染或图像操纵操作产生必须存储到目的地中的颜色值时,它必须首先将该颜色转换成适于存储到目的图像或表面中的形式。
     * 最低限度地,必须将颜色分量转换为位表示并以正确的顺序排序,或者在将数据存储到目的地存储器之前必须选择颜色查找表中的索引。没有这个最小转换,目的地中的数据可能表示随机,不正确或可能甚至不支持的值。
     * 快速将渲染操作的结果转换为最常见目的地的颜色格式的算法是众所周知的并且是相当优选的执行。
     * <p>
     * 简单地执行最基本的颜色格式转换以将颜色存储到目的地可以潜在地忽略源和目的的{@link java.awt.color.ColorSpace}或其他因素的校准的差异,例如伽马的线性更正。
     * 除非源和目标{@code ColorSpace}是相同的,为了正确执行呈现操作,最关心所表示的颜色的准确性,源颜色应转换为独立设备{@code ColorSpace}和然后将结果转换回目标{@code ColorSpace}
     * 。
     * 简单地执行最基本的颜色格式转换以将颜色存储到目的地可以潜在地忽略源和目的的{@link java.awt.color.ColorSpace}或其他因素的校准的差异,例如伽马的线性更正。
     * 此外,如果要在渲染操作期间执行诸如多个源颜色的混合的计算,则可以实现更大的视觉清晰度,如果选择中间设备独立的{@code ColorSpace}以具有所计算的值之间的线性关系,人眼对输出设备的响应曲线的
     * 感知。
     * 简单地执行最基本的颜色格式转换以将颜色存储到目的地可以潜在地忽略源和目的的{@link java.awt.color.ColorSpace}或其他因素的校准的差异,例如伽马的线性更正。
     * <p>
     *  此提示的允许值为
     * <ul>
     *  <li> {@ link #VALUE_COLOR_RENDER_SPEED} <li> {@ link #VALUE_COLOR_RENDER_QUALITY} <li> {@ link #VALUE_COLOR_RENDER_DEFAULT}
     * 。
     * </ul>
     */
    public static final Key KEY_COLOR_RENDERING =
         SunHints.KEY_COLOR_RENDERING;

    /**
     * Color rendering hint value -- perform the fastest color
     * conversion to the format of the output device.
     * <p>
     *  颜色渲染提示值 - 执行最快的颜色转换为输出设备的格式。
     * 
     * 
     * @see #KEY_COLOR_RENDERING
     */
    public static final Object VALUE_COLOR_RENDER_SPEED =
         SunHints.VALUE_COLOR_RENDER_SPEED;

    /**
     * Color rendering hint value -- perform the color conversion
     * calculations with the highest accuracy and visual quality.
     * <p>
     *  显色提示值 - 以最高的准确度和视觉质量执行颜色转换计算。
     * 
     * 
     * @see #KEY_COLOR_RENDERING
     */
    public static final Object VALUE_COLOR_RENDER_QUALITY =
         SunHints.VALUE_COLOR_RENDER_QUALITY;

    /**
     * Color rendering hint value -- perform color conversion
     * calculations as chosen by the implementation to represent
     * the best available tradeoff between performance and
     * accuracy.
     * <p>
     * 颜色渲染提示值 - 执行由实现选择的颜色转换计算,以表示性能和精度之间的最佳可用权衡。
     * 
     * 
     * @see #KEY_COLOR_RENDERING
     */
    public static final Object VALUE_COLOR_RENDER_DEFAULT =
         SunHints.VALUE_COLOR_RENDER_DEFAULT;

    /**
     * Stroke normalization control hint key.
     * The {@code STROKE_CONTROL} hint controls whether a rendering
     * implementation should or is allowed to modify the geometry
     * of rendered shapes for various purposes.
     * <p>
     * Some implementations may be able to use an optimized platform
     * rendering library which may be faster than traditional software
     * rendering algorithms on a given platform, but which may also
     * not support floating point coordinates.
     * Some implementations may also have sophisticated algorithms
     * which perturb the coordinates of a path so that wide lines
     * appear more uniform in width and spacing.
     * <p>
     * If an implementation performs any type of modification or
     * "normalization" of a path, it should never move the coordinates
     * by more than half a pixel in any direction.
     * <p>
     * The allowable values for this hint are
     * <ul>
     * <li>{@link #VALUE_STROKE_NORMALIZE}
     * <li>{@link #VALUE_STROKE_PURE}
     * <li>{@link #VALUE_STROKE_DEFAULT}
     * </ul>
     * <p>
     *  行程归一化控制提示键。 {@code STROKE_CONTROL}提示控制渲染实现是否应该或被允许为各种目的修改渲染形状的几何。
     * <p>
     *  一些实现可以能够使用优化的平台呈现库,其可以比给定平台上的传统软件呈现算法更快,但是其也可以不支持浮点坐标。一些实施方式还可以具有扰动路径的坐标的复杂算法,使得宽线在宽度和间距上显得更均匀。
     * <p>
     *  如果实现对路径执行任何类型的修改或"标准化",则它不应在任何方向上将坐标移动超过半个像素。
     * <p>
     *  此提示的允许值为
     * <ul>
     *  <li> {@ link #VALUE_STROKE_NORMALIZE} <li> {@ link #VALUE_STROKE_PURE} <li> {@ link #VALUE_STROKE_DEFAULT}
     * 。
     * </ul>
     * 
     * @since 1.3
     */
    public static final Key KEY_STROKE_CONTROL =
        SunHints.KEY_STROKE_CONTROL;

    /**
     * Stroke normalization control hint value -- geometry may be
     * modified or left pure depending on the tradeoffs in a given
     * implementation.
     * Typically this setting allows an implementation to use a fast
     * integer coordinate based platform rendering library, but does
     * not specifically request normalization for uniformity or
     * aesthetics.
     *
     * <p>
     *  行程归一化控制提示值 - 几何可以被修改或保持纯净,这取决于给定实现中的折衷。通常,此设置允许实现使用基于快速整数坐标的平台渲染库,但不特别请求用于均匀性或美观性的归一化。
     * 
     * 
     * @see #KEY_STROKE_CONTROL
     * @since 1.3
     */
    public static final Object VALUE_STROKE_DEFAULT =
        SunHints.VALUE_STROKE_DEFAULT;

    /**
     * Stroke normalization control hint value -- geometry should
     * be normalized to improve uniformity or spacing of lines and
     * overall aesthetics.
     * Note that different normalization algorithms may be more
     * successful than others for given input paths.
     *
     * <p>
     * 行程标准化控制提示值 - 几何应被标准化以改善线条的均匀性或间距以及整体美观。请注意,对于给定的输入路径,不同的归一化算法可能比其他归一化算法更成功。
     * 
     * 
     * @see #KEY_STROKE_CONTROL
     * @since 1.3
     */
    public static final Object VALUE_STROKE_NORMALIZE =
        SunHints.VALUE_STROKE_NORMALIZE;

    /**
     * Stroke normalization control hint value -- geometry should
     * be left unmodified and rendered with sub-pixel accuracy.
     *
     * <p>
     *  描边标准化控制提示值 - 几何图形应保持不变,并以子像素精度渲染。
     * 
     * 
     * @see #KEY_STROKE_CONTROL
     * @since 1.3
     */
    public static final Object VALUE_STROKE_PURE =
        SunHints.VALUE_STROKE_PURE;

    /**
     * Constructs a new object with keys and values initialized
     * from the specified Map object which may be null.
     * <p>
     *  使用从指定的Map对象初始化的键和值构造新对象,该对象可以为null。
     * 
     * 
     * @param init a map of key/value pairs to initialize the hints
     *          or null if the object should be empty
     */
    public RenderingHints(Map<Key,?> init) {
        if (init != null) {
            hintmap.putAll(init);
        }
    }

    /**
     * Constructs a new object with the specified key/value pair.
     * <p>
     *  构造具有指定键/值对的新对象。
     * 
     * 
     * @param key the key of the particular hint property
     * @param value the value of the hint property specified with
     * <code>key</code>
     */
    public RenderingHints(Key key, Object value) {
        hintmap.put(key, value);
    }

    /**
     * Returns the number of key-value mappings in this
     * <code>RenderingHints</code>.
     *
     * <p>
     *  返回此<c> RenderingHints </code>中的键值映射的数量。
     * 
     * 
     * @return the number of key-value mappings in this
     * <code>RenderingHints</code>.
     */
    public int size() {
        return hintmap.size();
    }

    /**
     * Returns <code>true</code> if this
     * <code>RenderingHints</code> contains no key-value mappings.
     *
     * <p>
     *  如果此<code> RenderingHints </code>不包含键值映射,则返回<code> true </code>。
     * 
     * 
     * @return <code>true</code> if this
     * <code>RenderingHints</code> contains no key-value mappings.
     */
    public boolean isEmpty() {
        return hintmap.isEmpty();
    }

    /**
     * Returns {@code true} if this {@code RenderingHints}
     *  contains a mapping for the specified key.
     *
     * <p>
     *  如果此{@code RenderingHints}包含指定键的映射,则返回{@code true}。
     * 
     * 
     * @param key key whose presence in this
     * {@code RenderingHints} is to be tested.
     * @return {@code true} if this {@code RenderingHints}
     *          contains a mapping for the specified key.
     * @exception ClassCastException if the key can not
     *            be cast to {@code RenderingHints.Key}
     */
    public boolean containsKey(Object key) {
        return hintmap.containsKey((Key) key);
    }

    /**
     * Returns true if this RenderingHints maps one or more keys to the
     * specified value.
     * More formally, returns <code>true</code> if and only
     * if this <code>RenderingHints</code>
     * contains at least one mapping to a value <code>v</code> such that
     * <pre>
     * (value==null ? v==null : value.equals(v))
     * </pre>.
     * This operation will probably require time linear in the
     * <code>RenderingHints</code> size for most implementations
     * of <code>RenderingHints</code>.
     *
     * <p>
     *  如果此RenderingHints将一个或多个键映射到指定的值,则返回true。
     * 更正式地,如果且仅当此<code> RenderingHints </code>包含至少一个映射到<code> v </code>的值时,返回<code> true </code>。
     * <pre>
     *  (value == null?v == null：value.equals(v))</pre>。
     * 对于<code> RenderingHints </code>的大多数实现,此操作可能需要<code> RenderingHints </code>大小的时间线性。
     * 
     * 
     * @param value value whose presence in this
     *          <code>RenderingHints</code> is to be tested.
     * @return <code>true</code> if this <code>RenderingHints</code>
     *           maps one or more keys to the specified value.
     */
    public boolean containsValue(Object value) {
        return hintmap.containsValue(value);
    }

    /**
     * Returns the value to which the specified key is mapped.
     * <p>
     *  返回指定键映射到的值。
     * 
     * 
     * @param   key   a rendering hint key
     * @return  the value to which the key is mapped in this object or
     *          {@code null} if the key is not mapped to any value in
     *          this object.
     * @exception ClassCastException if the key can not
     *            be cast to {@code RenderingHints.Key}
     * @see     #put(Object, Object)
     */
    public Object get(Object key) {
        return hintmap.get((Key) key);
    }

    /**
     * Maps the specified {@code key} to the specified
     * {@code value} in this {@code RenderingHints} object.
     * Neither the key nor the value can be {@code null}.
     * The value can be retrieved by calling the {@code get} method
     * with a key that is equal to the original key.
     * <p>
     * 将指定的{@code key}映射到此{@code RenderingHints}对象中指定的{@code value}。键和值都不能为{@code null}。
     * 可以通过使用等于原始键的键调用{@code get}方法来检索该值。
     * 
     * 
     * @param      key     the rendering hint key.
     * @param      value   the rendering hint value.
     * @return     the previous value of the specified key in this object
     *             or {@code null} if it did not have one.
     * @exception NullPointerException if the key is
     *            {@code null}.
     * @exception ClassCastException if the key can not
     *            be cast to {@code RenderingHints.Key}
     * @exception IllegalArgumentException if the
     *            {@link Key#isCompatibleValue(java.lang.Object)
     *                   Key.isCompatibleValue()}
     *            method of the specified key returns false for the
     *            specified value
     * @see     #get(Object)
     */
    public Object put(Object key, Object value) {
        if (!((Key) key).isCompatibleValue(value)) {
            throw new IllegalArgumentException(value+
                                               " incompatible with "+
                                               key);
        }
        return hintmap.put((Key) key, value);
    }

    /**
     * Adds all of the keys and corresponding values from the specified
     * <code>RenderingHints</code> object to this
     * <code>RenderingHints</code> object. Keys that are present in
     * this <code>RenderingHints</code> object, but not in the specified
     * <code>RenderingHints</code> object are not affected.
     * <p>
     *  将指定<code> RenderingHints </code>对象中的所有键和相应的值添加到此<code> RenderingHints </code>对象。
     * 存在于此<code> RenderingHints </code>对象中但不在指定的<code> RenderingHints </code>对象中的键不受影响。
     * 
     * 
     * @param hints the set of key/value pairs to be added to this
     * <code>RenderingHints</code> object
     */
    public void add(RenderingHints hints) {
        hintmap.putAll(hints.hintmap);
    }

    /**
     * Clears this <code>RenderingHints</code> object of all key/value
     * pairs.
     * <p>
     *  清除所有键/值对的<code> RenderingHints </code>对象。
     * 
     */
    public void clear() {
        hintmap.clear();
    }

    /**
     * Removes the key and its corresponding value from this
     * {@code RenderingHints} object. This method does nothing if the
     * key is not in this {@code RenderingHints} object.
     * <p>
     *  从此{@code RenderingHints}对象中删除键及其相应的值。如果键不在此{@code RenderingHints}对象中,此方法不会执行任何操作。
     * 
     * 
     * @param   key   the rendering hints key that needs to be removed
     * @exception ClassCastException if the key can not
     *            be cast to {@code RenderingHints.Key}
     * @return  the value to which the key had previously been mapped in this
     *          {@code RenderingHints} object, or {@code null}
     *          if the key did not have a mapping.
     */
    public Object remove(Object key) {
        return hintmap.remove((Key) key);
    }

    /**
     * Copies all of the mappings from the specified {@code Map}
     * to this {@code RenderingHints}.  These mappings replace
     * any mappings that this {@code RenderingHints} had for any
     * of the keys currently in the specified {@code Map}.
     * <p>
     *  将指定的{@code Map}的所有映射复制到此{@code RenderingHints}。
     * 这些映射会替换此{@code RenderingHints}对当前在指定的{@code Map}中的任何键的任何映射。
     * 
     * 
     * @param m the specified {@code Map}
     * @exception ClassCastException class of a key or value
     *          in the specified {@code Map} prevents it from being
     *          stored in this {@code RenderingHints}.
     * @exception IllegalArgumentException some aspect
     *          of a key or value in the specified {@code Map}
     *           prevents it from being stored in
     *            this {@code RenderingHints}.
     */
    public void putAll(Map<?,?> m) {
        // ## javac bug?
        //if (m instanceof RenderingHints) {
        if (RenderingHints.class.isInstance(m)) {
            //hintmap.putAll(((RenderingHints) m).hintmap);
            for (Map.Entry<?,?> entry : m.entrySet())
                hintmap.put(entry.getKey(), entry.getValue());
        } else {
            // Funnel each key/value pair through our protected put method
            for (Map.Entry<?,?> entry : m.entrySet())
                put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Returns a <code>Set</code> view of the Keys contained in this
     * <code>RenderingHints</code>.  The Set is backed by the
     * <code>RenderingHints</code>, so changes to the
     * <code>RenderingHints</code> are reflected in the <code>Set</code>,
     * and vice-versa.  If the <code>RenderingHints</code> is modified
     * while an iteration over the <code>Set</code> is in progress,
     * the results of the iteration are undefined.  The <code>Set</code>
     * supports element removal, which removes the corresponding
     * mapping from the <code>RenderingHints</code>, via the
     * <code>Iterator.remove</code>, <code>Set.remove</code>,
     * <code>removeAll</code> <code>retainAll</code>, and
     * <code>clear</code> operations.  It does not support
     * the <code>add</code> or <code>addAll</code> operations.
     *
     * <p>
     * 返回<code> RenderingHints </code>中包含的键的<code> Set </code>视图。
     * 集合由<code> RenderingHints </code>支持,因此对<code> RenderingHints </code>的更改反映在<code> Set </code>中,反之亦然。
     * 如果在对<code> Set </code>进行迭代时修改<code> RenderingHints </code>,迭代的结果是未定义的。
     *  <code> Set </code>支持元素删除,通过<code> Iterator.remove </code>,<code> Set.remove </code>删除<code> Renderin
     * gHints </code>代码>,<code> removeAll </code> <code> retainAll </code>和<code>清除</code>操作。
     * 如果在对<code> Set </code>进行迭代时修改<code> RenderingHints </code>,迭代的结果是未定义的。
     * 它不支持<code> add </code>或<code> addAll </code>操作。
     * 
     * 
     * @return a <code>Set</code> view of the keys contained
     * in this <code>RenderingHints</code>.
     */
    public Set<Object> keySet() {
        return hintmap.keySet();
    }

    /**
     * Returns a <code>Collection</code> view of the values
     * contained in this <code>RenderinHints</code>.
     * The <code>Collection</code> is backed by the
     * <code>RenderingHints</code>, so changes to
     * the <code>RenderingHints</code> are reflected in
     * the <code>Collection</code>, and vice-versa.
     * If the <code>RenderingHints</code> is modified while
     * an iteration over the <code>Collection</code> is
     * in progress, the results of the iteration are undefined.
     * The <code>Collection</code> supports element removal,
     * which removes the corresponding mapping from the
     * <code>RenderingHints</code>, via the
     * <code>Iterator.remove</code>,
     * <code>Collection.remove</code>, <code>removeAll</code>,
     * <code>retainAll</code> and <code>clear</code> operations.
     * It does not support the <code>add</code> or
     * <code>addAll</code> operations.
     *
     * <p>
     * 返回<code> RenderinHints </code>中包含的值的<code> Collection </code>视图。
     *  <code> Collection </code>由<code> RenderingHints </code>支持,因此对<code> RenderingHints </code>的更改反映在<code>
     *  Collection </code>反之亦然。
     * 返回<code> RenderinHints </code>中包含的值的<code> Collection </code>视图。
     * 如果在对<code> Collection </code>进行迭代时修改<code> RenderingHints </code>,则迭代的结果是未定义的。
     *  <code> Collection </code>支持元素删除,通过<code> Iterator.remove </code>,<code> Collection.remove </code>从<code>
     *  RenderingHints </code> code>,<code> removeAll </code>,<code> retainAll </code>和<code>清除</code>操作。
     * 如果在对<code> Collection </code>进行迭代时修改<code> RenderingHints </code>,则迭代的结果是未定义的。
     * 它不支持<code> add </code>或<code> addAll </code>操作。
     * 
     * 
     * @return a <code>Collection</code> view of the values
     *          contained in this <code>RenderingHints</code>.
     */
    public Collection<Object> values() {
        return hintmap.values();
    }

    /**
     * Returns a <code>Set</code> view of the mappings contained
     * in this <code>RenderingHints</code>.  Each element in the
     * returned <code>Set</code> is a <code>Map.Entry</code>.
     * The <code>Set</code> is backed by the <code>RenderingHints</code>,
     * so changes to the <code>RenderingHints</code> are reflected
     * in the <code>Set</code>, and vice-versa.  If the
     * <code>RenderingHints</code> is modified while
     * while an iteration over the <code>Set</code> is in progress,
     * the results of the iteration are undefined.
     * <p>
     * The entrySet returned from a <code>RenderingHints</code> object
     * is not modifiable.
     *
     * <p>
     *  返回<code> RenderingHints </code>中包含的映射的<code> Set </code>视图。
     * 返回的<code> Set </code>中的每个元素都是一个<code> Map.Entry </code>。
     *  <code> Set </code>由<code> RenderingHints </code>支持,因此对<code> RenderingHints </code>的更改会反映在<code> Set
     *  </code>反之亦然。
     * 返回的<code> Set </code>中的每个元素都是一个<code> Map.Entry </code>。
     * 如果在对<code> Set </code>进行迭代时修改<code> RenderingHints </code>,迭代的结果是未定义的。
     * <p>
     *  从<code> RenderingHints </code>对象返回的entrySet不可修改。
     * 
     * 
     * @return a <code>Set</code> view of the mappings contained in
     * this <code>RenderingHints</code>.
     */
    public Set<Map.Entry<Object,Object>> entrySet() {
        return Collections.unmodifiableMap(hintmap).entrySet();
    }

    /**
     * Compares the specified <code>Object</code> with this
     * <code>RenderingHints</code> for equality.
     * Returns <code>true</code> if the specified object is also a
     * <code>Map</code> and the two <code>Map</code> objects represent
     * the same mappings.  More formally, two <code>Map</code> objects
     * <code>t1</code> and <code>t2</code> represent the same mappings
     * if <code>t1.keySet().equals(t2.keySet())</code> and for every
     * key <code>k</code> in <code>t1.keySet()</code>,
     * <pre>
     * (t1.get(k)==null ? t2.get(k)==null : t1.get(k).equals(t2.get(k)))
     * </pre>.
     * This ensures that the <code>equals</code> method works properly across
     * different implementations of the <code>Map</code> interface.
     *
     * <p>
     * 将指定的<code> Object </code>与此<code> RenderingHints </code>进行比较以确保相等。
     * 如果指定的对象也是<code> Map </code>,并且两个<code> Map </code>对象表示相同的映射,则返回<code> true </code>。
     * 更正式地,如果<code> t1.keySet()。
     * equals(t2.keySet()),两个<code> Map </code>对象<code> t1 </code>和<code> t2 </code> ))</code>和<code> t1.key
     * Set()</code>中的每个键<code> k </code>。
     * 更正式地,如果<code> t1.keySet()。
     * <pre>
     *  (t1.get(k)== null?t2.get(k)== null：t1.get(k).equals(t2.get(k)))</pre>。
     * 这确保<code> equals </code>方法在<code> Map </code>接口的不同实现中正常工作。
     * 
     * 
     * @param o <code>Object</code> to be compared for equality with
     * this <code>RenderingHints</code>.
     * @return <code>true</code> if the specified <code>Object</code>
     * is equal to this <code>RenderingHints</code>.
     */
    public boolean equals(Object o) {
        if (o instanceof RenderingHints) {
            return hintmap.equals(((RenderingHints) o).hintmap);
        } else if (o instanceof Map) {
            return hintmap.equals(o);
        }
        return false;
    }

    /**
     * Returns the hash code value for this <code>RenderingHints</code>.
     * The hash code of a <code>RenderingHints</code> is defined to be
     * the sum of the hashCodes of each <code>Entry</code> in the
     * <code>RenderingHints</code> object's entrySet view.  This ensures that
     * <code>t1.equals(t2)</code> implies that
     * <code>t1.hashCode()==t2.hashCode()</code> for any two <code>Map</code>
     * objects <code>t1</code> and <code>t2</code>, as required by the general
     * contract of <code>Object.hashCode</code>.
     *
     * <p>
     *  返回此<code> RenderingHints </code>的哈希码值。
     *  <code> RenderingHints </code>的哈希码被定义为<code> RenderingHints </code>对象的entrySet视图中每个<code> Entry </code>
     * 的哈希码的总和。
     *  返回此<code> RenderingHints </code>的哈希码值。
     * 这确保<code> t1.equals(t2)</code>意味着任何两个<code> Map </code>对象的<code> t1.hashCode()== t2.hashCode()</代码> t
     * 1 </code>和<code> t2 </code>,按照<code> Object.hashCode </code>的一般合同的要求。
     *  返回此<code> RenderingHints </code>的哈希码值。
     * 
     * @return the hash code value for this <code>RenderingHints</code>.
     * @see java.util.Map.Entry#hashCode()
     * @see Object#hashCode()
     * @see Object#equals(Object)
     * @see #equals(Object)
     */
    public int hashCode() {
        return hintmap.hashCode();
    }

    /**
     * Creates a clone of this <code>RenderingHints</code> object
     * that has the same contents as this <code>RenderingHints</code>
     * object.
     * <p>
     * 
     * 
     * @return a clone of this instance.
     */
    @SuppressWarnings("unchecked")
    public Object clone() {
        RenderingHints rh;
        try {
            rh = (RenderingHints) super.clone();
            if (hintmap != null) {
                rh.hintmap = (HashMap<Object,Object>) hintmap.clone();
            }
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }

        return rh;
    }

    /**
     * Returns a rather long string representation of the hashmap
     * which contains the mappings of keys to values for this
     * <code>RenderingHints</code> object.
     * <p>
     *  创建与此<code> RenderingHints </code>对象具有相同内容的<code> RenderingHints </code>对象的克隆。
     * 
     * 
     * @return  a string representation of this object.
     */
    public String toString() {
        if (hintmap == null) {
            return getClass().getName() + "@" +
                Integer.toHexString(hashCode()) +
                " (0 hints)";
        }

        return hintmap.toString();
    }
}
