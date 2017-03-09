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

/*
/* <p>
/* 
 * @author Charlton Innovations, Inc.
 */

package java.awt.font;

import java.awt.RenderingHints;
import static java.awt.RenderingHints.*;
import java.awt.geom.AffineTransform;

/**
*   The <code>FontRenderContext</code> class is a container for the
*   information needed to correctly measure text.  The measurement of text
*   can vary because of rules that map outlines to pixels, and rendering
*   hints provided by an application.
*   <p>
*   One such piece of information is a transform that scales
*   typographical points to pixels. (A point is defined to be exactly 1/72
*   of an inch, which is slightly different than
*   the traditional mechanical measurement of a point.)  A character that
*   is rendered at 12pt on a 600dpi device might have a different size
*   than the same character rendered at 12pt on a 72dpi device because of
*   such factors as rounding to pixel boundaries and hints that the font
*   designer may have specified.
*   <p>
*   Anti-aliasing and Fractional-metrics specified by an application can also
*   affect the size of a character because of rounding to pixel
*   boundaries.
*   <p>
*   Typically, instances of <code>FontRenderContext</code> are
*   obtained from a {@link java.awt.Graphics2D Graphics2D} object.  A
*   <code>FontRenderContext</code> which is directly constructed will
*   most likely not represent any actual graphics device, and may lead
*   to unexpected or incorrect results.
* <p>
*  <code> FontRenderContext </code>类是正确测量文本所需信息的容器。文本的测量可以变化,因为将轮廓映射到像素的规则以及由应用提供的呈现提示。
* <p>
*  一个这样的信息是将排版点缩放到像素的变换。 (点被定义为精确的1/72英寸,这与点的传统机械测量略有不同。
* )在600dpi设备上以12pt渲染的字符可能具有与渲染的相同字符不同的大小在72dpi设备上的12pt,因为诸如舍入到像素边界和提示字体设计者可能已经指定的因素。
* <p>
*  由应用程序指定的消除锯齿和小数度量也可能影响字符的大小,因为四舍五入到像素边界。
* <p>
*  通常,从{@link java.awt.Graphics2D Graphics2D}对象获取<code> FontRenderContext </code>的实例。
* 直接构造的<code> FontRenderContext </code>很可能不代表任何实际的图形设备,并且可能导致意外或不正确的结果。
* 
* 
*   @see java.awt.RenderingHints#KEY_TEXT_ANTIALIASING
*   @see java.awt.RenderingHints#KEY_FRACTIONALMETRICS
*   @see java.awt.Graphics2D#getFontRenderContext()
*   @see java.awt.font.LineMetrics
*/

public class FontRenderContext {
    private transient AffineTransform tx;
    private transient Object aaHintValue;
    private transient Object fmHintValue;
    private transient boolean defaulting;

    /**
     * Constructs a new <code>FontRenderContext</code>
     * object.
     *
     * <p>
     *  构造一个新的<code> FontRenderContext </code>对象。
     * 
     */
    protected FontRenderContext() {
        aaHintValue = VALUE_TEXT_ANTIALIAS_DEFAULT;
        fmHintValue = VALUE_FRACTIONALMETRICS_DEFAULT;
        defaulting = true;
    }

    /**
     * Constructs a <code>FontRenderContext</code> object from an
     * optional {@link AffineTransform} and two <code>boolean</code>
     * values that determine if the newly constructed object has
     * anti-aliasing or fractional metrics.
     * In each case the boolean values <CODE>true</CODE> and <CODE>false</CODE>
     * correspond to the rendering hint values <CODE>ON</CODE> and
     * <CODE>OFF</CODE> respectively.
     * <p>
     * To specify other hint values, use the constructor which
     * specifies the rendering hint values as parameters :
     * {@link #FontRenderContext(AffineTransform, Object, Object)}.
     * <p>
     * 从可选的{@link AffineTransform}和两个<code>布尔</code>值构造一个<code> FontRenderContext </code>对象,用于确定新构造的对象是否具有抗锯
     * 齿或分数度量。
     * 在每种情况下,布尔值<CODE> true </CODE>和<CODE> false </CODE>分别对应于渲染提示值<CODE> ON </CODE>和<CODE> OFF </CODE>。
     * <p>
     *  要指定其他提示值,请使用指定渲染提示值作为参数的构造函数：{@link #FontRenderContext(AffineTransform,Object,Object)}。
     * 
     * 
     * @param tx the transform which is used to scale typographical points
     * to pixels in this <code>FontRenderContext</code>.  If null, an
     * identity transform is used.
     * @param isAntiAliased determines if the newly constructed object
     * has anti-aliasing.
     * @param usesFractionalMetrics determines if the newly constructed
     * object has fractional metrics.
     */
    public FontRenderContext(AffineTransform tx,
                            boolean isAntiAliased,
                            boolean usesFractionalMetrics) {
        if (tx != null && !tx.isIdentity()) {
            this.tx = new AffineTransform(tx);
        }
        if (isAntiAliased) {
            aaHintValue = VALUE_TEXT_ANTIALIAS_ON;
        } else {
            aaHintValue = VALUE_TEXT_ANTIALIAS_OFF;
        }
        if (usesFractionalMetrics) {
            fmHintValue = VALUE_FRACTIONALMETRICS_ON;
        } else {
            fmHintValue = VALUE_FRACTIONALMETRICS_OFF;
        }
    }

    /**
     * Constructs a <code>FontRenderContext</code> object from an
     * optional {@link AffineTransform} and two <code>Object</code>
     * values that determine if the newly constructed object has
     * anti-aliasing or fractional metrics.
     * <p>
     *  从可选的{@link AffineTransform}和两个<code> Object </code>值构造一个<code> FontRenderContext </code>对象,以确定新构造的对象
     * 是否具有抗锯齿或分数度量。
     * 
     * 
     * @param tx the transform which is used to scale typographical points
     * to pixels in this <code>FontRenderContext</code>.  If null, an
     * identity transform is used.
     * @param aaHint - one of the text antialiasing rendering hint values
     * defined in {@link java.awt.RenderingHints java.awt.RenderingHints}.
     * Any other value will throw <code>IllegalArgumentException</code>.
     * {@link java.awt.RenderingHints#VALUE_TEXT_ANTIALIAS_DEFAULT VALUE_TEXT_ANTIALIAS_DEFAULT}
     * may be specified, in which case the mode used is implementation
     * dependent.
     * @param fmHint - one of the text fractional rendering hint values defined
     * in {@link java.awt.RenderingHints java.awt.RenderingHints}.
     * {@link java.awt.RenderingHints#VALUE_FRACTIONALMETRICS_DEFAULT VALUE_FRACTIONALMETRICS_DEFAULT}
     * may be specified, in which case the mode used is implementation
     * dependent.
     * Any other value will throw <code>IllegalArgumentException</code>
     * @throws IllegalArgumentException if the hints are not one of the
     * legal values.
     * @since 1.6
     */
    public FontRenderContext(AffineTransform tx, Object aaHint, Object fmHint){
        if (tx != null && !tx.isIdentity()) {
            this.tx = new AffineTransform(tx);
        }
        try {
            if (KEY_TEXT_ANTIALIASING.isCompatibleValue(aaHint)) {
                aaHintValue = aaHint;
            } else {
                throw new IllegalArgumentException("AA hint:" + aaHint);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("AA hint:" +aaHint);
        }
        try {
            if (KEY_FRACTIONALMETRICS.isCompatibleValue(fmHint)) {
                fmHintValue = fmHint;
            } else {
                throw new IllegalArgumentException("FM hint:" + fmHint);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("FM hint:" +fmHint);
        }
    }

    /**
     * Indicates whether or not this <code>FontRenderContext</code> object
     * measures text in a transformed render context.
     * <p>
     *  表示这个<code> FontRenderContext </code>对象是否在转换的渲染上下文中测量文本。
     * 
     * 
     * @return  <code>true</code> if this <code>FontRenderContext</code>
     *          object has a non-identity AffineTransform attribute.
     *          <code>false</code> otherwise.
     * @see     java.awt.font.FontRenderContext#getTransform
     * @since   1.6
     */
    public boolean isTransformed() {
        if (!defaulting) {
            return tx != null;
        } else {
            return !getTransform().isIdentity();
        }
    }

    /**
     * Returns the integer type of the affine transform for this
     * <code>FontRenderContext</code> as specified by
     * {@link java.awt.geom.AffineTransform#getType()}
     * <p>
     *  返回由{@link java.awt.geom.AffineTransform#getType()}指定的<code> FontRenderContext </code>的仿射变换的整数类型
     * 
     * 
     * @return the type of the transform.
     * @see AffineTransform
     * @since 1.6
     */
    public int getTransformType() {
        if (!defaulting) {
            if (tx == null) {
                return AffineTransform.TYPE_IDENTITY;
            } else {
                return tx.getType();
            }
        } else {
            return getTransform().getType();
        }
    }

    /**
    *   Gets the transform that is used to scale typographical points
    *   to pixels in this <code>FontRenderContext</code>.
    * <p>
    *  获取用于将排版点缩放到此<code> FontRenderContext </code>中的像素的变换。
    * 
    * 
    *   @return the <code>AffineTransform</code> of this
    *    <code>FontRenderContext</code>.
    *   @see AffineTransform
    */
    public AffineTransform getTransform() {
        return (tx == null) ? new AffineTransform() : new AffineTransform(tx);
    }

    /**
    * Returns a boolean which indicates whether or not some form of
    * antialiasing is specified by this <code>FontRenderContext</code>.
    * Call {@link #getAntiAliasingHint() getAntiAliasingHint()}
    * for the specific rendering hint value.
    * <p>
    *  返回一个布尔值,表示是否由此<code> FontRenderContext </code>指定某种形式的抗锯齿。
    * 为特定的渲染提示值调用{@link #getAntiAliasingHint()getAntiAliasingHint()}。
    * 
    * 
    *   @return    <code>true</code>, if text is anti-aliased in this
    *   <code>FontRenderContext</code>; <code>false</code> otherwise.
    *   @see        java.awt.RenderingHints#KEY_TEXT_ANTIALIASING
    *   @see #FontRenderContext(AffineTransform,boolean,boolean)
    *   @see #FontRenderContext(AffineTransform,Object,Object)
    */
    public boolean isAntiAliased() {
        return !(aaHintValue == VALUE_TEXT_ANTIALIAS_OFF ||
                 aaHintValue == VALUE_TEXT_ANTIALIAS_DEFAULT);
    }

    /**
    * Returns a boolean which whether text fractional metrics mode
    * is used in this <code>FontRenderContext</code>.
    * Call {@link #getFractionalMetricsHint() getFractionalMetricsHint()}
    * to obtain the corresponding rendering hint value.
    * <p>
    * 返回一个布尔值,它是否在此<code> FontRenderContext </code>中使用文本小数度量模式。
    * 调用{@link #getFractionalMetricsHint()getFractionalMetricsHint()}以获取相应的呈现提示值。
    * 
    * 
    *   @return    <code>true</code>, if layout should be performed with
    *   fractional metrics; <code>false</code> otherwise.
    *               in this <code>FontRenderContext</code>.
    *   @see java.awt.RenderingHints#KEY_FRACTIONALMETRICS
    *   @see #FontRenderContext(AffineTransform,boolean,boolean)
    *   @see #FontRenderContext(AffineTransform,Object,Object)
    */
    public boolean usesFractionalMetrics() {
        return !(fmHintValue == VALUE_FRACTIONALMETRICS_OFF ||
                 fmHintValue == VALUE_FRACTIONALMETRICS_DEFAULT);
    }

    /**
     * Return the text anti-aliasing rendering mode hint used in this
     * <code>FontRenderContext</code>.
     * This will be one of the text antialiasing rendering hint values
     * defined in {@link java.awt.RenderingHints java.awt.RenderingHints}.
     * <p>
     *  返回在此<code> FontRenderContext </code>中使用的文本反锯齿渲染模式提示。
     * 这将是{@link java.awt.RenderingHints java.awt.RenderingHints}中定义的文本抗锯齿渲染提示值之一。
     * 
     * 
     * @return  text anti-aliasing rendering mode hint used in this
     * <code>FontRenderContext</code>.
     * @since 1.6
     */
    public Object getAntiAliasingHint() {
        if (defaulting) {
            if (isAntiAliased()) {
                 return VALUE_TEXT_ANTIALIAS_ON;
            } else {
                return VALUE_TEXT_ANTIALIAS_OFF;
            }
        }
        return aaHintValue;
    }

    /**
     * Return the text fractional metrics rendering mode hint used in this
     * <code>FontRenderContext</code>.
     * This will be one of the text fractional metrics rendering hint values
     * defined in {@link java.awt.RenderingHints java.awt.RenderingHints}.
     * <p>
     *  返回在此<code> FontRenderContext </code>中使用的文本小数度量呈现模式提示。
     * 这将是在{@link java.awt.RenderingHints java.awt.RenderingHints}中定义的文本分数度量值之一。
     * 
     * 
     * @return the text fractional metrics rendering mode hint used in this
     * <code>FontRenderContext</code>.
     * @since 1.6
     */
    public Object getFractionalMetricsHint() {
        if (defaulting) {
            if (usesFractionalMetrics()) {
                 return VALUE_FRACTIONALMETRICS_ON;
            } else {
                return VALUE_FRACTIONALMETRICS_OFF;
            }
        }
        return fmHintValue;
    }

    /**
     * Return true if obj is an instance of FontRenderContext and has the same
     * transform, antialiasing, and fractional metrics values as this.
     * <p>
     *  如果obj是FontRenderContext的实例,并且具有与此相同的变换,抗锯齿和分数度量值,则返回true。
     * 
     * 
     * @param obj the object to test for equality
     * @return <code>true</code> if the specified object is equal to
     *         this <code>FontRenderContext</code>; <code>false</code>
     *         otherwise.
     */
    public boolean equals(Object obj) {
        try {
            return equals((FontRenderContext)obj);
        }
        catch (ClassCastException e) {
            return false;
        }
    }

    /**
     * Return true if rhs has the same transform, antialiasing,
     * and fractional metrics values as this.
     * <p>
     *  如果rhs具有与此相同的变换,抗锯齿和小数度量值,则返回true。
     * 
     * 
     * @param rhs the <code>FontRenderContext</code> to test for equality
     * @return <code>true</code> if <code>rhs</code> is equal to
     *         this <code>FontRenderContext</code>; <code>false</code>
     *         otherwise.
     * @since 1.4
     */
    public boolean equals(FontRenderContext rhs) {
        if (this == rhs) {
            return true;
        }
        if (rhs == null) {
            return false;
        }

        /* if neither instance is a subclass, reference values directly. */
        if (!rhs.defaulting && !defaulting) {
            if (rhs.aaHintValue == aaHintValue &&
                rhs.fmHintValue == fmHintValue) {

                return tx == null ? rhs.tx == null : tx.equals(rhs.tx);
            }
            return false;
        } else {
            return
                rhs.getAntiAliasingHint() == getAntiAliasingHint() &&
                rhs.getFractionalMetricsHint() == getFractionalMetricsHint() &&
                rhs.getTransform().equals(getTransform());
        }
    }

    /**
     * Return a hashcode for this FontRenderContext.
     * <p>
     *  返回此FontRenderContext的哈希码。
     * 
     */
    public int hashCode() {
        int hash = tx == null ? 0 : tx.hashCode();
        /* SunHints value objects have identity hashcode, so we can rely on
         * this to ensure that two equal FRC's have the same hashcode.
         * <p>
         *  这确保两个相等的FRC具有相同的哈希码。
         */
        if (defaulting) {
            hash += getAntiAliasingHint().hashCode();
            hash += getFractionalMetricsHint().hashCode();
        } else {
            hash += aaHintValue.hashCode();
            hash += fmHintValue.hashCode();
        }
        return hash;
    }
}
