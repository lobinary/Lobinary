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

import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Polygon;        // remind - need a floating point version
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;
import java.awt.Shape;
import java.awt.font.GlyphMetrics;
import java.awt.font.GlyphJustificationInfo;

/**
 * A <code>GlyphVector</code> object is a collection of glyphs
 * containing geometric information for the placement of each glyph
 * in a transformed coordinate space which corresponds to the
 * device on which the <code>GlyphVector</code> is ultimately
 * displayed.
 * <p>
 * The <code>GlyphVector</code> does not attempt any interpretation of
 * the sequence of glyphs it contains.  Relationships between adjacent
 * glyphs in sequence are solely used to determine the placement of
 * the glyphs in the visual coordinate space.
 * <p>
 * Instances of <code>GlyphVector</code> are created by a {@link Font}.
 * <p>
 * In a text processing application that can cache intermediate
 * representations of text, creation and subsequent caching of a
 * <code>GlyphVector</code> for use during rendering is the fastest
 * method to present the visual representation of characters to a user.
 * <p>
 * A <code>GlyphVector</code> is associated with exactly one
 * <code>Font</code>, and can provide data useful only in relation to
 * this <code>Font</code>.  In addition, metrics obtained from a
 * <code>GlyphVector</code> are not generally geometrically scaleable
 * since the pixelization and spacing are dependent on grid-fitting
 * algorithms within a <code>Font</code>.  To facilitate accurate
 * measurement of a <code>GlyphVector</code> and its component
 * glyphs, you must specify a scaling transform, anti-alias mode, and
 * fractional metrics mode when creating the <code>GlyphVector</code>.
 * These characteristics can be derived from the destination device.
 * <p>
 * For each glyph in the <code>GlyphVector</code>, you can obtain:
 * <ul>
 * <li>the position of the glyph
 * <li>the transform associated with the glyph
 * <li>the metrics of the glyph in the context of the
 *   <code>GlyphVector</code>.  The metrics of the glyph may be
 *   different under different transforms, application specified
 *   rendering hints, and the specific instance of the glyph within
 *   the <code>GlyphVector</code>.
 * </ul>
 * <p>
 * Altering the data used to create the <code>GlyphVector</code> does not
 * alter the state of the <code>GlyphVector</code>.
 * <p>
 * Methods are provided to adjust the positions of the glyphs
 * within the <code>GlyphVector</code>.  These methods are most
 * appropriate for applications that are performing justification
 * operations for the presentation of the glyphs.
 * <p>
 * Methods are provided to transform individual glyphs within the
 * <code>GlyphVector</code>.  These methods are primarily useful for
 * special effects.
 * <p>
 * Methods are provided to return both the visual, logical, and pixel bounds
 * of the entire <code>GlyphVector</code> or of individual glyphs within
 * the <code>GlyphVector</code>.
 * <p>
 * Methods are provided to return a {@link Shape} for the
 * <code>GlyphVector</code>, and for individual glyphs within the
 * <code>GlyphVector</code>.
 * <p>
 *  <code> GlyphVector </code>对象是包含用于将每个字形放置在变换的坐标空间中的几何信息的字形的集合,其对应于最终显示<code> GlyphVector </code>的设备。
 * <p>
 *  <code> GlyphVector </code>不会尝试对其包含的字形序列的任何解释。序列中相邻字形之间的关系仅用于确定字形在视觉坐标空间中的位置。
 * <p>
 *  <code> GlyphVector </code>的实例由{@link Font}创建。
 * <p>
 *  在可以高速缓存文本的中间表示的文本处理应用中,用于在呈现期间使用的<code> GlyphVector </code>的创建和后续高速缓存是将字符的视觉表示呈现给用户的最快方法。
 * <p>
 * <code> GlyphVector </code>只与一个<code> Font </code>关联,并且可以提供仅与此<code> Font </code>有关的数据。
 * 另外,从<code> GlyphVector </code>获得的度量通常不是几何可缩放的,因为像素化和间隔依赖于<code> Font </code>中的网格拟合算法。
 * 为了便于准确测量<code> GlyphVector </code>及其组件字形,在创建<code> GlyphVector </code>时,必须指定缩放变换,反锯齿模式和小数度量模式。
 * 这些特性可以从目标设​​备派生。
 * <p>
 *  对于<code> GlyphVector </code>中的每个字形,您可以获得：
 * <ul>
 *  <li>字形的位置<li>与字形相关联的变换<li> <code> GlyphVector </code>的上下文中的字形的度量。
 * 字形的度量在不同变换,应用程序指定的呈现提示以及<code> GlyphVector </code>中的字形的特定实例可以是不同的。
 * </ul>
 * <p>
 *  改变用于创建<code> GlyphVector </code>的数据不会改变<code> GlyphVector </code>的状态。
 * <p>
 *  提供了用于调整<code> GlyphVector </code>中的字形位置的方法。这些方法最适合于为字形的呈现执行调整操作的应用程序。
 * <p>
 * 提供了用于变换<code> GlyphVector </code>中的各个字形的方法。这些方法主要用于特殊效果。
 * <p>
 *  提供了返回整个<code> GlyphVector </code>或<code> GlyphVector </code>中的单个字形的视觉,逻辑和像素边界的方法。
 * <p>
 *  提供了为<code> GlyphVector </code>返回一个{@link Shape},以及为<code> GlyphVector </code>中的单个字形返回的方法。
 * 
 * 
 * @see Font
 * @see GlyphMetrics
 * @see TextLayout
 * @author Charlton Innovations, Inc.
 */

public abstract class GlyphVector implements Cloneable {

    //
    // methods associated with creation-time state
    //

    /**
     * Returns the <code>Font</code> associated with this
     * <code>GlyphVector</code>.
     * <p>
     *  返回与此<code> GlyphVector </code>关联的<code> Font </code>。
     * 
     * 
     * @return <code>Font</code> used to create this
     * <code>GlyphVector</code>.
     * @see Font
     */
    public abstract Font getFont();

    /**
     * Returns the {@link FontRenderContext} associated with this
     * <code>GlyphVector</code>.
     * <p>
     *  返回与此<code> GlyphVector </code>关联的{@link FontRenderContext}。
     * 
     * 
     * @return <code>FontRenderContext</code> used to create this
     * <code>GlyphVector</code>.
     * @see FontRenderContext
     * @see Font
     */
    public abstract FontRenderContext getFontRenderContext();

    //
    // methods associated with the GlyphVector as a whole
    //

    /**
     * Assigns default positions to each glyph in this
     * <code>GlyphVector</code>. This can destroy information
     * generated during initial layout of this <code>GlyphVector</code>.
     * <p>
     *  在此<code> GlyphVector </code>中为每个字形分配默认位置。这可以破坏在此<code> GlyphVector </code>的初始布局期间生成的信息。
     * 
     */
    public abstract void performDefaultLayout();

    /**
     * Returns the number of glyphs in this <code>GlyphVector</code>.
     * <p>
     *  返回此<code> GlyphVector </code>中的字形数。
     * 
     * 
     * @return number of glyphs in this <code>GlyphVector</code>.
     */
    public abstract int getNumGlyphs();

    /**
     * Returns the glyphcode of the specified glyph.
     * This return value is meaningless to anything other
     * than the <code>Font</code> object that created this
     * <code>GlyphVector</code>.
     * <p>
     *  返回指定字形的glyphcode。对于创建此<code> GlyphVector </code>的<code> Font </code>对象以外的任何内容,此返回值都是无意义的。
     * 
     * 
     * @param glyphIndex the index into this <code>GlyphVector</code>
     * that corresponds to the glyph from which to retrieve the
     * glyphcode.
     * @return the glyphcode of the glyph at the specified
     * <code>glyphIndex</code>.
     * @throws IndexOutOfBoundsException if <code>glyphIndex</code>
     * is less than 0 or greater than or equal to the
     * number of glyphs in this <code>GlyphVector</code>
     */
    public abstract int getGlyphCode(int glyphIndex);

    /**
     * Returns an array of glyphcodes for the specified glyphs.
     * The contents of this return value are meaningless to anything other
     * than the <code>Font</code> used to create this
     * <code>GlyphVector</code>.  This method is used
     * for convenience and performance when processing glyphcodes.
     * If no array is passed in, a new array is created.
     * <p>
     *  返回指定字形的字形数组。此返回值的内容对用于创建此<code> GlyphVector </code>的<code> Font </code>之外的任何内容均无意义。
     * 此方法用于方便和处理字形码时的性能。如果没有传入数组,则创建一个新数组。
     * 
     * 
     * @param beginGlyphIndex the index into this
     *   <code>GlyphVector</code> at which to start retrieving glyphcodes
     * @param numEntries the number of glyphcodes to retrieve
     * @param codeReturn the array that receives the glyphcodes and is
     *   then returned
     * @return an array of glyphcodes for the specified glyphs.
     * @throws IllegalArgumentException if <code>numEntries</code> is
     *   less than 0
     * @throws IndexOutOfBoundsException if <code>beginGlyphIndex</code>
     *   is less than 0
     * @throws IndexOutOfBoundsException if the sum of
     *   <code>beginGlyphIndex</code> and <code>numEntries</code> is
     *   greater than the number of glyphs in this
     *   <code>GlyphVector</code>
     */
    public abstract int[] getGlyphCodes(int beginGlyphIndex, int numEntries,
                                        int[] codeReturn);

    /**
     * Returns the character index of the specified glyph.
     * The character index is the index of the first logical
     * character represented by the glyph.  The default
     * implementation assumes a one-to-one, left-to-right mapping
     * of glyphs to characters.
     * <p>
     * 返回指定字形的字符索引。字符索引是由字形表示的第一逻辑字符的索引。默认实现假设从字形到字符的一对一,从左到右的映射。
     * 
     * 
     * @param glyphIndex the index of the glyph
     * @return the index of the first character represented by the glyph
     * @since 1.4
     */
    public int getGlyphCharIndex(int glyphIndex) {
        return glyphIndex;
    }

    /**
     * Returns the character indices of the specified glyphs.
     * The character index is the index of the first logical
     * character represented by the glyph.  Indices are returned
     * in glyph order.  The default implementation invokes
     * getGlyphCharIndex for each glyph, and subclassers will probably
     * want to override this implementation for performance reasons.
     * Use this method for convenience and performance
     * in processing of glyphcodes. If no array is passed in,
     * a new array is created.
     * <p>
     *  返回指定字形的字符索引。字符索引是由字形表示的第一逻辑字符的索引。以字形顺序返回索引。默认实现为每个字形调用getGlyphCharIndex,为了性能原因,子类可能想要覆盖此实现。
     * 使用此方法为方便和性能处理字形。如果没有传入数组,则创建一个新数组。
     * 
     * 
     * @param beginGlyphIndex the index of the first glyph
     * @param numEntries the number of glyph indices
     * @param codeReturn the array into which to return the character indices
     * @return an array of character indices, one per glyph.
     * @since 1.4
     */
    public int[] getGlyphCharIndices(int beginGlyphIndex, int numEntries,
                                     int[] codeReturn) {
        if (codeReturn == null) {
            codeReturn = new int[numEntries];
        }
        for (int i = 0, j = beginGlyphIndex; i < numEntries; ++i, ++j) {
            codeReturn[i] = getGlyphCharIndex(j);
        }
        return codeReturn;
     }

    /**
     * Returns the logical bounds of this <code>GlyphVector</code>.
     * This method is used when positioning this <code>GlyphVector</code>
     * in relation to visually adjacent <code>GlyphVector</code> objects.
     * <p>
     *  返回此<code> GlyphVector </code>的逻辑边界。
     * 这种方法用于定位<code> GlyphVector </code>与视觉上相邻的<code> GlyphVector </code>对象。
     * 
     * 
     * @return a {@link Rectangle2D} that is the logical bounds of this
     * <code>GlyphVector</code>.
     */
    public abstract Rectangle2D getLogicalBounds();

    /**
     * Returns the visual bounds of this <code>GlyphVector</code>
     * The visual bounds is the bounding box of the outline of this
     * <code>GlyphVector</code>.  Because of rasterization and
     * alignment of pixels, it is possible that this box does not
     * enclose all pixels affected by rendering this <code>GlyphVector</code>.
     * <p>
     *  返回此<code> GlyphVector </code>的视觉边界。视觉边界是此<code> GlyphVector </code>大纲的边界框。
     * 由于像素的光栅化和对齐,这个框可能不会包含所有受渲染此<code> GlyphVector </code>影响的像素。
     * 
     * 
     * @return a <code>Rectangle2D</code> that is the bounding box
     * of this <code>GlyphVector</code>.
     */
    public abstract Rectangle2D getVisualBounds();

    /**
     * Returns the pixel bounds of this <code>GlyphVector</code> when
     * rendered in a graphics with the given
     * <code>FontRenderContext</code> at the given location.  The
     * renderFRC need not be the same as the
     * <code>FontRenderContext</code> of this
     * <code>GlyphVector</code>, and can be null.  If it is null, the
     * <code>FontRenderContext</code> of this <code>GlyphVector</code>
     * is used.  The default implementation returns the visual bounds,
     * offset to x, y and rounded out to the next integer value (i.e. returns an
     * integer rectangle which encloses the visual bounds) and
     * ignores the FRC.  Subclassers should override this method.
     * <p>
     * 当在给定位置使用给定的<code> FontRenderContext </code>在图形中呈现时,返回此<code> GlyphVector </code>的像素边界。
     *  renderFRC不需要与<code> GlyphVector </code>的<code> FontRenderContext </code>相同,并且可以为null。
     * 如果它为null,则使用此<code> GlyphVector </code>的<code> FontRenderContext </code>。
     * 默认实现返回视觉边界,偏移到x,y并且舍入到下一个整数值(即返回包围视觉边界的整数矩形),并忽略FRC。子类应该覆盖此方法。
     * 
     * 
     * @param renderFRC the <code>FontRenderContext</code> of the <code>Graphics</code>.
     * @param x the x-coordinate at which to render this <code>GlyphVector</code>.
     * @param y the y-coordinate at which to render this <code>GlyphVector</code>.
     * @return a <code>Rectangle</code> bounding the pixels that would be affected.
     * @since 1.4
     */
    public Rectangle getPixelBounds(FontRenderContext renderFRC, float x, float y) {
                Rectangle2D rect = getVisualBounds();
                int l = (int)Math.floor(rect.getX() + x);
                int t = (int)Math.floor(rect.getY() + y);
                int r = (int)Math.ceil(rect.getMaxX() + x);
                int b = (int)Math.ceil(rect.getMaxY() + y);
                return new Rectangle(l, t, r - l, b - t);
        }


    /**
     * Returns a <code>Shape</code> whose interior corresponds to the
     * visual representation of this <code>GlyphVector</code>.
     * <p>
     *  返回一个<code> Shape </code>,其内部对应于此<code> GlyphVector </code>的可视化表示。
     * 
     * 
     * @return a <code>Shape</code> that is the outline of this
     * <code>GlyphVector</code>.
     */
    public abstract Shape getOutline();

    /**
     * Returns a <code>Shape</code> whose interior corresponds to the
     * visual representation of this <code>GlyphVector</code> when
     * rendered at x,&nbsp;y.
     * <p>
     *  返回一个<code> Shape </code>,其内部对应于<code> GlyphVector </code>的可视化表示,当在x,y呈现时。
     * 
     * 
     * @param x the X coordinate of this <code>GlyphVector</code>.
     * @param y the Y coordinate of this <code>GlyphVector</code>.
     * @return a <code>Shape</code> that is the outline of this
     *   <code>GlyphVector</code> when rendered at the specified
     *   coordinates.
     */
    public abstract Shape getOutline(float x, float y);

    /**
     * Returns a <code>Shape</code> whose interior corresponds to the
     * visual representation of the specified glyph
     * within this <code>GlyphVector</code>.
     * The outline returned by this method is positioned around the
     * origin of each individual glyph.
     * <p>
     *  返回一个<code> Shape </code>,其内部对应于<code> GlyphVector </code>中指定字形的可视化表示。此方法返回的轮廓位于每个单个字形的原点周围。
     * 
     * 
     * @param glyphIndex the index into this <code>GlyphVector</code>
     * @return a <code>Shape</code> that is the outline of the glyph
     *   at the specified <code>glyphIndex</code> of this
     *   <code>GlyphVector</code>.
     * @throws IndexOutOfBoundsException if <code>glyphIndex</code>
     *   is less than 0 or greater than or equal to the number
     *   of glyphs in this <code>GlyphVector</code>
     */
    public abstract Shape getGlyphOutline(int glyphIndex);

    /**
     * Returns a <code>Shape</code> whose interior corresponds to the
     * visual representation of the specified glyph
     * within this <code>GlyphVector</code>, offset to x,&nbsp;y.
     * The outline returned by this method is positioned around the
     * origin of each individual glyph.
     * <p>
     *  返回一个<code> Shape </code>,其内部对应于<code> GlyphVector </code>中指定字形的视觉表示,偏移为x,y。此方法返回的轮廓位于每个单个字形的原点周围。
     * 
     * 
     * @param glyphIndex the index into this <code>GlyphVector</code>
     * @param x the X coordinate of the location of this {@code GlyphVector}
     * @param y the Y coordinate of the location of this {@code GlyphVector}
     * @return a <code>Shape</code> that is the outline of the glyph
     *   at the specified <code>glyphIndex</code> of this
     *   <code>GlyphVector</code> when rendered at the specified
     *   coordinates.
     * @throws IndexOutOfBoundsException if <code>glyphIndex</code>
     *   is less than 0 or greater than or equal to the number
     *   of glyphs in this <code>GlyphVector</code>
     * @since 1.4
     */
    public Shape getGlyphOutline(int glyphIndex, float x, float y) {
        Shape s = getGlyphOutline(glyphIndex);
        AffineTransform at = AffineTransform.getTranslateInstance(x,y);
        return at.createTransformedShape(s);
        }

    /**
     * Returns the position of the specified glyph relative to the
     * origin of this <code>GlyphVector</code>.
     * If <code>glyphIndex</code> equals the number of of glyphs in
     * this <code>GlyphVector</code>, this method returns the position after
     * the last glyph. This position is used to define the advance of
     * the entire <code>GlyphVector</code>.
     * <p>
     * 返回相对于此<code> GlyphVector </code>的原点的指定字形的位置。
     * 如果<code> glyphIndex </code>等于此<code> GlyphVector </code>中的字形数,此方法将返回最后一个字形之后的位置。
     * 这个位置用于定义整个<code> GlyphVector </code>的前进。
     * 
     * 
     * @param glyphIndex the index into this <code>GlyphVector</code>
     * @return a {@link Point2D} object that is the position of the glyph
     *   at the specified <code>glyphIndex</code>.
     * @throws IndexOutOfBoundsException if <code>glyphIndex</code>
     *   is less than 0 or greater than the number of glyphs
     *   in this <code>GlyphVector</code>
     * @see #setGlyphPosition
     */
    public abstract Point2D getGlyphPosition(int glyphIndex);

    /**
     * Sets the position of the specified glyph within this
     * <code>GlyphVector</code>.
     * If <code>glyphIndex</code> equals the number of of glyphs in
     * this <code>GlyphVector</code>, this method sets the position after
     * the last glyph. This position is used to define the advance of
     * the entire <code>GlyphVector</code>.
     * <p>
     *  在此<code> GlyphVector </code>中设置指定字形的位置。
     * 如果<code> glyphIndex </code>等于此<code> GlyphVector </code>中的字形数量,则此方法将设置最后一个字形之后的位置。
     * 这个位置用于定义整个<code> GlyphVector </code>的前进。
     * 
     * 
     * @param glyphIndex the index into this <code>GlyphVector</code>
     * @param newPos the <code>Point2D</code> at which to position the
     *   glyph at the specified <code>glyphIndex</code>
     * @throws IndexOutOfBoundsException if <code>glyphIndex</code>
     *   is less than 0 or greater than the number of glyphs
     *   in this <code>GlyphVector</code>
     * @see #getGlyphPosition
     */
    public abstract void setGlyphPosition(int glyphIndex, Point2D newPos);

    /**
     * Returns the transform of the specified glyph within this
     * <code>GlyphVector</code>.  The transform is relative to the
     * glyph position.  If no special transform has been applied,
     * <code>null</code> can be returned.  A null return indicates
     * an identity transform.
     * <p>
     *  返回此<code> GlyphVector </code>中指定字形的变换。变换相对于字形位置。如果未应用特殊变换,则可以返回<code> null </code>。空返回表示身份转换。
     * 
     * 
     * @param glyphIndex the index into this <code>GlyphVector</code>
     * @return an {@link AffineTransform} that is the transform of
     *   the glyph at the specified <code>glyphIndex</code>.
     * @throws IndexOutOfBoundsException if <code>glyphIndex</code>
     *   is less than 0 or greater than or equal to the number
     *   of glyphs in this <code>GlyphVector</code>
     * @see #setGlyphTransform
     */
    public abstract AffineTransform getGlyphTransform(int glyphIndex);

    /**
     * Sets the transform of the specified glyph within this
     * <code>GlyphVector</code>.  The transform is relative to the glyph
     * position.  A <code>null</code> argument for <code>newTX</code>
     * indicates that no special transform is applied for the specified
     * glyph.
     * This method can be used to rotate, mirror, translate and scale the
     * glyph.  Adding a transform can result in significant performance changes.
     * <p>
     *  设置此<code> GlyphVector </code>中指定字形的转换。变换相对于字形位置。
     *  <code> newTX </code>的<code> null </code>参数表示没有为指定的字形应用特殊的变换。此方法可用于旋转,镜像,平移和缩放字形。添加变换可能会导致显着的性能更改。
     * 
     * 
     * @param glyphIndex the index into this <code>GlyphVector</code>
     * @param newTX the new transform of the glyph at <code>glyphIndex</code>
     * @throws IndexOutOfBoundsException if <code>glyphIndex</code>
     *   is less than 0 or greater than or equal to the number
     *   of glyphs in this <code>GlyphVector</code>
     * @see #getGlyphTransform
     */
    public abstract void setGlyphTransform(int glyphIndex, AffineTransform newTX);

    /**
     * Returns flags describing the global state of the GlyphVector.
     * Flags not described below are reserved.  The default
     * implementation returns 0 (meaning false) for the position adjustments,
     * transforms, rtl, and complex flags.
     * Subclassers should override this method, and make sure
     * it correctly describes the GlyphVector and corresponds
     * to the results of related calls.
     * <p>
     * 返回描述GlyphVector的全局状态的标志。下面不描述的标志被保留。默认实现对位置调整,transforms,rtl和复杂标志返回0(表示false)。
     * 子类应该重写这个方法,并确保它正确地描述GlyphVector并且对应于相关调用的结果。
     * 
     * 
     * @return an int containing the flags describing the state
     * @see #FLAG_HAS_POSITION_ADJUSTMENTS
     * @see #FLAG_HAS_TRANSFORMS
     * @see #FLAG_RUN_RTL
     * @see #FLAG_COMPLEX_GLYPHS
     * @see #FLAG_MASK
     * @since 1.4
     */
    public int getLayoutFlags() {
                return 0;
        }

    /**
     * A flag used with getLayoutFlags that indicates that this <code>GlyphVector</code> has
     * per-glyph transforms.
     * <p>
     *  与getLayoutFlags一起使用的标志,指示此<code> GlyphVector </code>具有每字形转换。
     * 
     * 
     * @since 1.4
     */
    public static final int FLAG_HAS_TRANSFORMS = 1;

    /**
     * A flag used with getLayoutFlags that indicates that this <code>GlyphVector</code> has
     * position adjustments.  When this is true, the glyph positions don't match the
     * accumulated default advances of the glyphs (for example, if kerning has been done).
     * <p>
     *  与getLayoutFlags一起使用的标志,指示此<code> GlyphVector </code>具有位置调整。当这是真的时,字形位置与字形的累积默认前进不匹配(例如,如果已完成字距调整)。
     * 
     * 
     * @since 1.4
     */
    public static final int FLAG_HAS_POSITION_ADJUSTMENTS = 2;

    /**
     * A flag used with getLayoutFlags that indicates that this <code>GlyphVector</code> has
     * a right-to-left run direction.  This refers to the glyph-to-char mapping and does
     * not imply that the visual locations of the glyphs are necessarily in this order,
     * although generally they will be.
     * <p>
     *  与getLayoutFlags一起使用的标志,指示此<code> GlyphVector </code>具有从右到左的运行方向。
     * 这指的是字形到字符的映射,并不意味着字形的视觉位置必然是这个顺序,虽然通常他们会。
     * 
     * 
     * @since 1.4
     */
    public static final int FLAG_RUN_RTL = 4;

    /**
     * A flag used with getLayoutFlags that indicates that this <code>GlyphVector</code> has
     * a complex glyph-to-char mapping (one that does not map glyphs to chars one-to-one in
     * strictly ascending or descending order matching the run direction).
     * <p>
     *  与getLayoutFlags一起使用的标志,指示此<code> GlyphVector </code>具有复杂的字形到字符映射(不将字形按照与运行方向匹配的严格升序或降序一对一映射到字符) )。
     * 
     * 
     * @since 1.4
     */
    public static final int FLAG_COMPLEX_GLYPHS = 8;

    /**
     * A mask for supported flags from getLayoutFlags.  Only bits covered by the mask
     * should be tested.
     * <p>
     *  getLayoutFlags支持的标志的掩码。只有被掩码覆盖的位应该被测试。
     * 
     * 
     * @since 1.4
     */
    public static final int FLAG_MASK =
        FLAG_HAS_TRANSFORMS |
        FLAG_HAS_POSITION_ADJUSTMENTS |
        FLAG_RUN_RTL |
        FLAG_COMPLEX_GLYPHS;

    /**
     * Returns an array of glyph positions for the specified glyphs.
     * This method is used for convenience and performance when
     * processing glyph positions.
     * If no array is passed in, a new array is created.
     * Even numbered array entries beginning with position zero are the X
     * coordinates of the glyph numbered <code>beginGlyphIndex + position/2</code>.
     * Odd numbered array entries beginning with position one are the Y
     * coordinates of the glyph numbered <code>beginGlyphIndex + (position-1)/2</code>.
     * If <code>beginGlyphIndex</code> equals the number of of glyphs in
     * this <code>GlyphVector</code>, this method gets the position after
     * the last glyph and this position is used to define the advance of
     * the entire <code>GlyphVector</code>.
     * <p>
     * 返回指定字形的字形位置数组。此方法用于方便和处理字形位置时的性能。如果没有传入数组,则创建一个新数组。
     * 从位置零开始的偶数编号的数组项是编号为<code> beginGlyphIndex + position / 2 </code>的字形的X坐标。
     * 从位置一开始的奇数编号的阵列条目是编号为<code> beginGlyphIndex +(position-1)/ 2 </code>的字形的Y坐标。
     * 如果<code> beginGlyphIndex </code>等于此<code> GlyphVector </code>中的字形数量,则此方法将获取最后一个字形之后的位置,此位置用于定义整个<code>
     *  GlyphVector </code>。
     * 从位置一开始的奇数编号的阵列条目是编号为<code> beginGlyphIndex +(position-1)/ 2 </code>的字形的Y坐标。
     * 
     * 
     * @param beginGlyphIndex the index at which to begin retrieving
     *   glyph positions
     * @param numEntries the number of glyphs to retrieve
     * @param positionReturn the array that receives the glyph positions
     *   and is then returned.
     * @return an array of glyph positions specified by
     *  <code>beginGlyphIndex</code> and <code>numEntries</code>.
     * @throws IllegalArgumentException if <code>numEntries</code> is
     *   less than 0
     * @throws IndexOutOfBoundsException if <code>beginGlyphIndex</code>
     *   is less than 0
     * @throws IndexOutOfBoundsException if the sum of
     *   <code>beginGlyphIndex</code> and <code>numEntries</code>
     *   is greater than the number of glyphs in this
     *   <code>GlyphVector</code> plus one
     */
    public abstract float[] getGlyphPositions(int beginGlyphIndex, int numEntries,
                                              float[] positionReturn);

    /**
     * Returns the logical bounds of the specified glyph within this
     * <code>GlyphVector</code>.
     * These logical bounds have a total of four edges, with two edges
     * parallel to the baseline under the glyph's transform and the other two
     * edges are shared with adjacent glyphs if they are present.  This
     * method is useful for hit-testing of the specified glyph,
     * positioning of a caret at the leading or trailing edge of a glyph,
     * and for drawing a highlight region around the specified glyph.
     * <p>
     *  返回此<code> GlyphVector </code>中指定字形的逻辑边界。这些逻辑边界具有总共四个边,其中两个边在字形的变换下平行于基线,并且如果它们存在,则另两个边与相邻字形共享。
     * 此方法对于指定字形的命中测试,在字形的前端或后端定位插入符号以及在指定字形周围绘制突出显示区域非常有用。
     * 
     * 
     * @param glyphIndex the index into this <code>GlyphVector</code>
     *   that corresponds to the glyph from which to retrieve its logical
     *   bounds
     * @return  a <code>Shape</code> that is the logical bounds of the
     *   glyph at the specified <code>glyphIndex</code>.
     * @throws IndexOutOfBoundsException if <code>glyphIndex</code>
     *   is less than 0 or greater than or equal to the number
     *   of glyphs in this <code>GlyphVector</code>
     * @see #getGlyphVisualBounds
     */
    public abstract Shape getGlyphLogicalBounds(int glyphIndex);

    /**
     * Returns the visual bounds of the specified glyph within the
     * <code>GlyphVector</code>.
     * The bounds returned by this method is positioned around the
     * origin of each individual glyph.
     * <p>
     *  返回<code> GlyphVector </code>中指定字形的视觉边界。此方法返回的边界位于每个单个字形的原点周围。
     * 
     * 
     * @param glyphIndex the index into this <code>GlyphVector</code>
     *   that corresponds to the glyph from which to retrieve its visual
     *   bounds
     * @return a <code>Shape</code> that is the visual bounds of the
     *   glyph at the specified <code>glyphIndex</code>.
     * @throws IndexOutOfBoundsException if <code>glyphIndex</code>
     *   is less than 0 or greater than or equal to the number
     *   of glyphs in this <code>GlyphVector</code>
     * @see #getGlyphLogicalBounds
     */
    public abstract Shape getGlyphVisualBounds(int glyphIndex);

    /**
     * Returns the pixel bounds of the glyph at index when this
     * <code>GlyphVector</code> is rendered in a <code>Graphics</code> with the
     * given <code>FontRenderContext</code> at the given location. The
     * renderFRC need not be the same as the
     * <code>FontRenderContext</code> of this
     * <code>GlyphVector</code>, and can be null.  If it is null, the
     * <code>FontRenderContext</code> of this <code>GlyphVector</code>
     * is used.  The default implementation returns the visual bounds of the glyph,
     * offset to x, y and rounded out to the next integer value, and
     * ignores the FRC.  Subclassers should override this method.
     * <p>
     * 当在给定位置使用给定的<code> FontRenderContext </code>在<code> Graphics </code>中呈现<code> GlyphVector </code>时,返回g
     * lyph索引处的像素边界。
     *  renderFRC不需要与<code> GlyphVector </code>的<code> FontRenderContext </code>相同,并且可以为null。
     * 如果它为null,则使用此<code> GlyphVector </code>的<code> FontRenderContext </code>。
     * 默认实现返回字形的视觉边界,偏移为x,y并舍入到下一个整数值,并忽略FRC。子类应该覆盖此方法。
     * 
     * 
     * @param index the index of the glyph.
     * @param renderFRC the <code>FontRenderContext</code> of the <code>Graphics</code>.
     * @param x the X position at which to render this <code>GlyphVector</code>.
     * @param y the Y position at which to render this <code>GlyphVector</code>.
     * @return a <code>Rectangle</code> bounding the pixels that would be affected.
     * @since 1.4
     */
    public Rectangle getGlyphPixelBounds(int index, FontRenderContext renderFRC, float x, float y) {
                Rectangle2D rect = getGlyphVisualBounds(index).getBounds2D();
                int l = (int)Math.floor(rect.getX() + x);
                int t = (int)Math.floor(rect.getY() + y);
                int r = (int)Math.ceil(rect.getMaxX() + x);
                int b = (int)Math.ceil(rect.getMaxY() + y);
                return new Rectangle(l, t, r - l, b - t);
        }

    /**
     * Returns the metrics of the glyph at the specified index into
     * this <code>GlyphVector</code>.
     * <p>
     *  将指定索引处的字形的度量返回到此<code> GlyphVector </code>中。
     * 
     * 
     * @param glyphIndex the index into this <code>GlyphVector</code>
     *   that corresponds to the glyph from which to retrieve its metrics
     * @return a {@link GlyphMetrics} object that represents the
     *   metrics of the glyph at the specified <code>glyphIndex</code>
     *   into this <code>GlyphVector</code>.
     * @throws IndexOutOfBoundsException if <code>glyphIndex</code>
     *   is less than 0 or greater than or equal to the number
     *   of glyphs in this <code>GlyphVector</code>
     */
    public abstract GlyphMetrics getGlyphMetrics(int glyphIndex);

    /**
     * Returns the justification information for the glyph at
     * the specified index into this <code>GlyphVector</code>.
     * <p>
     *  将指定索引处的字形的调整信息返回到此<code> GlyphVector </code>中。
     * 
     * 
     * @param glyphIndex the index into this <code>GlyphVector</code>
     *   that corresponds to the glyph from which to retrieve its
     *   justification properties
     * @return a {@link GlyphJustificationInfo} object that
     *   represents the justification properties of the glyph at the
     *   specified <code>glyphIndex</code> into this
     *   <code>GlyphVector</code>.
     * @throws IndexOutOfBoundsException if <code>glyphIndex</code>
     *   is less than 0 or greater than or equal to the number
     *   of glyphs in this <code>GlyphVector</code>
     */
    public abstract GlyphJustificationInfo getGlyphJustificationInfo(int glyphIndex);

    //
    // general utility methods
    //

    /**
     * Tests if the specified <code>GlyphVector</code> exactly
     * equals this <code>GlyphVector</code>.
     * <p>
     *  测试指定的<code> GlyphVector </code>是否等于此<code> GlyphVector </code>。
     * 
     * @param set the specified <code>GlyphVector</code> to test
     * @return <code>true</code> if the specified
     *   <code>GlyphVector</code> equals this <code>GlyphVector</code>;
     *   <code>false</code> otherwise.
     */
    public abstract boolean equals(GlyphVector set);
}
