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

/*
 * (C) Copyright Taligent, Inc. 1996 - 1997, All Rights Reserved
 * (C) Copyright IBM Corp. 1996-2003, All Rights Reserved
 *
 * The original version of this source code and documentation is
 * copyrighted and owned by Taligent, Inc., a wholly-owned subsidiary
 * of IBM. These materials are provided under terms of a License
 * Agreement between Taligent and Sun. This technology is protected
 * by multiple US and International patents.
 *
 * This notice and attribution to Taligent may not be removed.
 * Taligent is a registered trademark of Taligent, Inc.
 *
 * <p>
 *  (C)版权所有Taligent,Inc 1996  -  1997,保留所有权利(C)版权所有IBM Corp 1996-2003,保留所有权利
 * 
 *  此源代码和文档的原始版本受版权保护,并由Taligent,Inc(IBM的全资子公司)拥有。这些资料根据Taligent与Sun之间的许可协议的条款提供此技术受多项美国和国际专利保护
 * 
 *  本通知和Taligent的归属不得删除Taligent是Taligent,Inc的注册商标
 * 
 */

package java.awt.font;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.font.NumericShaper;
import java.awt.font.TextLine.TextLineMetrics;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.text.AttributedString;
import java.text.AttributedCharacterIterator;
import java.text.AttributedCharacterIterator.Attribute;
import java.text.CharacterIterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Hashtable;
import sun.font.AttributeValues;
import sun.font.CoreMetrics;
import sun.font.Decoration;
import sun.font.FontLineMetrics;
import sun.font.FontResolver;
import sun.font.GraphicComponent;
import sun.font.LayoutPathImpl;
import sun.text.CodePointIterator;

/**
 *
 * <code>TextLayout</code> is an immutable graphical representation of styled
 * character data.
 * <p>
 * It provides the following capabilities:
 * <ul>
 * <li>implicit bidirectional analysis and reordering,
 * <li>cursor positioning and movement, including split cursors for
 * mixed directional text,
 * <li>highlighting, including both logical and visual highlighting
 * for mixed directional text,
 * <li>multiple baselines (roman, hanging, and centered),
 * <li>hit testing,
 * <li>justification,
 * <li>default font substitution,
 * <li>metric information such as ascent, descent, and advance, and
 * <li>rendering
 * </ul>
 * <p>
 * A <code>TextLayout</code> object can be rendered using
 * its <code>draw</code> method.
 * <p>
 * <code>TextLayout</code> can be constructed either directly or through
 * the use of a {@link LineBreakMeasurer}.  When constructed directly, the
 * source text represents a single paragraph.  <code>LineBreakMeasurer</code>
 * allows styled text to be broken into lines that fit within a particular
 * width.  See the <code>LineBreakMeasurer</code> documentation for more
 * information.
 * <p>
 * <code>TextLayout</code> construction logically proceeds as follows:
 * <ul>
 * <li>paragraph attributes are extracted and examined,
 * <li>text is analyzed for bidirectional reordering, and reordering
 * information is computed if needed,
 * <li>text is segmented into style runs
 * <li>fonts are chosen for style runs, first by using a font if the
 * attribute {@link TextAttribute#FONT} is present, otherwise by computing
 * a default font using the attributes that have been defined
 * <li>if text is on multiple baselines, the runs or subruns are further
 * broken into subruns sharing a common baseline,
 * <li>glyphvectors are generated for each run using the chosen font,
 * <li>final bidirectional reordering is performed on the glyphvectors
 * </ul>
 * <p>
 * All graphical information returned from a <code>TextLayout</code>
 * object's methods is relative to the origin of the
 * <code>TextLayout</code>, which is the intersection of the
 * <code>TextLayout</code> object's baseline with its left edge.  Also,
 * coordinates passed into a <code>TextLayout</code> object's methods
 * are assumed to be relative to the <code>TextLayout</code> object's
 * origin.  Clients usually need to translate between a
 * <code>TextLayout</code> object's coordinate system and the coordinate
 * system in another object (such as a
 * {@link java.awt.Graphics Graphics} object).
 * <p>
 * <code>TextLayout</code> objects are constructed from styled text,
 * but they do not retain a reference to their source text.  Thus,
 * changes in the text previously used to generate a <code>TextLayout</code>
 * do not affect the <code>TextLayout</code>.
 * <p>
 * Three methods on a <code>TextLayout</code> object
 * (<code>getNextRightHit</code>, <code>getNextLeftHit</code>, and
 * <code>hitTestChar</code>) return instances of {@link TextHitInfo}.
 * The offsets contained in these <code>TextHitInfo</code> objects
 * are relative to the start of the <code>TextLayout</code>, <b>not</b>
 * to the text used to create the <code>TextLayout</code>.  Similarly,
 * <code>TextLayout</code> methods that accept <code>TextHitInfo</code>
 * instances as parameters expect the <code>TextHitInfo</code> object's
 * offsets to be relative to the <code>TextLayout</code>, not to any
 * underlying text storage model.
 * <p>
 * <strong>Examples</strong>:<p>
 * Constructing and drawing a <code>TextLayout</code> and its bounding
 * rectangle:
 * <blockquote><pre>
 *   Graphics2D g = ...;
 *   Point2D loc = ...;
 *   Font font = Font.getFont("Helvetica-bold-italic");
 *   FontRenderContext frc = g.getFontRenderContext();
 *   TextLayout layout = new TextLayout("This is a string", font, frc);
 *   layout.draw(g, (float)loc.getX(), (float)loc.getY());
 *
 *   Rectangle2D bounds = layout.getBounds();
 *   bounds.setRect(bounds.getX()+loc.getX(),
 *                  bounds.getY()+loc.getY(),
 *                  bounds.getWidth(),
 *                  bounds.getHeight());
 *   g.draw(bounds);
 * </pre>
 * </blockquote>
 * <p>
 * Hit-testing a <code>TextLayout</code> (determining which character is at
 * a particular graphical location):
 * <blockquote><pre>
 *   Point2D click = ...;
 *   TextHitInfo hit = layout.hitTestChar(
 *                         (float) (click.getX() - loc.getX()),
 *                         (float) (click.getY() - loc.getY()));
 * </pre>
 * </blockquote>
 * <p>
 * Responding to a right-arrow key press:
 * <blockquote><pre>
 *   int insertionIndex = ...;
 *   TextHitInfo next = layout.getNextRightHit(insertionIndex);
 *   if (next != null) {
 *       // translate graphics to origin of layout on screen
 *       g.translate(loc.getX(), loc.getY());
 *       Shape[] carets = layout.getCaretShapes(next.getInsertionIndex());
 *       g.draw(carets[0]);
 *       if (carets[1] != null) {
 *           g.draw(carets[1]);
 *       }
 *   }
 * </pre></blockquote>
 * <p>
 * Drawing a selection range corresponding to a substring in the source text.
 * The selected area may not be visually contiguous:
 * <blockquote><pre>
 *   // selStart, selLimit should be relative to the layout,
 *   // not to the source text
 *
 *   int selStart = ..., selLimit = ...;
 *   Color selectionColor = ...;
 *   Shape selection = layout.getLogicalHighlightShape(selStart, selLimit);
 *   // selection may consist of disjoint areas
 *   // graphics is assumed to be tranlated to origin of layout
 *   g.setColor(selectionColor);
 *   g.fill(selection);
 * </pre></blockquote>
 * <p>
 * Drawing a visually contiguous selection range.  The selection range may
 * correspond to more than one substring in the source text.  The ranges of
 * the corresponding source text substrings can be obtained with
 * <code>getLogicalRangesForVisualSelection()</code>:
 * <blockquote><pre>
 *   TextHitInfo selStart = ..., selLimit = ...;
 *   Shape selection = layout.getVisualHighlightShape(selStart, selLimit);
 *   g.setColor(selectionColor);
 *   g.fill(selection);
 *   int[] ranges = getLogicalRangesForVisualSelection(selStart, selLimit);
 *   // ranges[0], ranges[1] is the first selection range,
 *   // ranges[2], ranges[3] is the second selection range, etc.
 * </pre></blockquote>
 * <p>
 * Note: Font rotations can cause text baselines to be rotated, and
 * multiple runs with different rotations can cause the baseline to
 * bend or zig-zag.  In order to account for this (rare) possibility,
 * some APIs are specified to return metrics and take parameters 'in
 * baseline-relative coordinates' (e.g. ascent, advance), and others
 * are in 'in standard coordinates' (e.g. getBounds).  Values in
 * baseline-relative coordinates map the 'x' coordinate to the
 * distance along the baseline, (positive x is forward along the
 * baseline), and the 'y' coordinate to a distance along the
 * perpendicular to the baseline at 'x' (positive y is 90 degrees
 * clockwise from the baseline vector).  Values in standard
 * coordinates are measured along the x and y axes, with 0,0 at the
 * origin of the TextLayout.  Documentation for each relevant API
 * indicates what values are in what coordinate system.  In general,
 * measurement-related APIs are in baseline-relative coordinates,
 * while display-related APIs are in standard coordinates.
 *
 * <p>
 *  <code> TextLayout </code>是样式字符数据的不可变图形表示
 * <p>
 * 它提供以下功能：
 * <ul>
 *  <li>隐式双向分析和重新排序,<li>光标定位和移动,包括混合方向文本的分割游标,<li>突出显示,包括混合方向文本的逻辑和视觉突出显示< ,中心),<li>点击测试,<li>对齐,<li>默认字体
 * 替换,<li>指标信息,例如上升,下降和提前,<li>渲染。
 * </ul>
 * <p>
 *  可以使用<code> draw </code>方法呈现<code> TextLayout </code>对象
 * <p>
 * <code> TextLayout </code>可以直接构造,也可以通过使用{@link LineBreakMeasurer}构造。
 * 当直接构造时,源文本代表一个段落<code> LineBreakMeasurer </code>允许将样式文本分成适合特定宽度的线条有关详细信息,请参阅<code> LineBreakMeasurer 
 * </code>文档。
 * <code> TextLayout </code>可以直接构造,也可以通过使用{@link LineBreakMeasurer}构造。
 * <p>
 *  <code> TextLayout </code>结构逻辑上进行如下：
 * <ul>
 * <li>提取和检查段落属性,<li>分析文本以进行双向重新排序,如果需要,计算重新排序信息,<li>将文本分段为样式运行<li>首先通过使用样式运行选择字体如果存在属性{@link TextAttribute#FONT}
 * ,则使用已定义的属性计算默认字体;如果文本在多个基线上,则运行或子臂进一步分为子臂,共享公共基线,<li>使用所选字体为每次运行生成glyphvectors,<li>对glyphvectors执行最终双
 * 向重新排序。
 * </ul>
 * <p>
 * 从<code> TextLayout </code>对象的方法返回的所有图形信息都是相对于<code> TextLayout </code>的原点,它是<code> TextLayout </code>
 * 对象的基线与左边缘此外,传递到<code> TextLayout </code>对象的方法中的坐标被认为是相对于<code> TextLayout </code>对象的原点客户端通常需要在<code> 
 * TextLayout </code>对象的坐标系统和另一个对象(例如{@link javaawtGraphics Graphics}对象)中的坐标系统,。
 * <p>
 * <code> TextLayout </code>对象是从样式文本构造的,但它们不保留对其源文本的引用。
 * 因此,以前用于生成<code> TextLayout </code>的文本中的更改不会影响<代码> TextLayout </code>。
 * <p>
 * <code> TextLayout </code>对象(<code> getNextRightHit </code>,<code> getNextLeftHit </code>和<code> hitTe
 * stChar </code>)中的三个方法返回{@link TextHitInfo}这些<code> TextHitInfo </code>对象中包含的偏移量相对于<code> TextLayout </code>
 * 的开始,<b>不是</b>到用来创建<code> TextLayout < / code>类似地,接受<code> TextHitInfo </code>实例作为参数的<code> TextLayout
 *  </code>方法期望<code> TextHitInfo </code>对象的偏移量相对于<code> TextLayout <代码>,而不是任何基础文本存储模型。
 * <p>
 * <strong>示例</strong>：<p>构造并绘制<code> TextLayout </code>及其边框矩形：<blockquote> <pre> Graphics2D g = Point2D
 *  loc =; Font font = FontgetFont("Helvetica-bold-italic"); FontRenderContext frc = ggetFontRenderConte
 * xt(); TextLayout layout = new TextLayout("This is a string",font,frc); layoutdraw(g,(float)locgetX(),
 * (float)locgetY());。
 * 
 *  Rectangle2D bounds = layoutgetBounds(); boundsetRect(boundsgetX()+ locgetX(),boundsgetY()+ locgetY()
 * ,boundsgetWidth(),boundsgetHeight()); gdraw(bounds);。
 * </pre>
 * </blockquote>
 * <p>
 * 点击测试<code> TextLayout </code>(确定哪个字符在特定图形位置)：<blockquote> <pre> Point2D click =; TextHitInfo hit = la
 * youthitTestChar((float)(clickgetX() -  locgetX()),(float)(clickgetY() -  locgetY()));。
 * </pre>
 * </blockquote>
 * <p>
 *  响应右箭头键：<blockquote> <pre> int insertionIndex =; TextHitInfo next = layoutgetNextRightHit(insertionIn
 * dex); if(next！= null){//将图形转换为屏幕上布局的原点gtranslate(locgetX(),locgetY()); Shape [] carets = layoutgetCaretShapes(nextgetInsertionIndex()); gdraw(carets [0]); if(carets [1]！= null){gdraw(carets [1]); }
 * } </pre> </blockquote>。
 * <p>
 * 在源文本中绘制对应于子字符串的选择范围所选区域可能在视觉上不连续：<blockquote> <pre> // selStart,selLimit应该相对于布局,//不是源文本
 * 
 *  int selStart =,selLimit =;颜色selectionColor =;形状选择= layoutgetLogicalHighlightShape(selStart,selLimit)
 * ; //选择可以包括不相交的区域//图形被假定为被转换到布局gsetColor(selectionColor)的原点; gfill(selection); </pre> </blockquote>。
 * <p>
 * 绘制视觉上连续的选择范围选择范围可以对应于源文本中的多于一个子串可以使用<code> getLogicalRangesForVisualSelection()</code>来获得相应源文本子串的范围：<blockquote>
 *  <pre> TextHitInfo selStart =,selLimit =; Shape selection = layoutgetVisualHighlightShape(selStart,se
 * lLimit); gsetColor(selectionColor); gfill(selection); int [] ranges = getLogicalRangesForVisualSelect
 * ion(selStart,selLimit); // ranges [0],ranges [1]是第一个选择范围,// ranges [2],ranges [3]是第二个选择范围,等等</pre> </blockquote>
 * 。
 * <p>
 * 注意：字体旋转可能会导致文本基线被旋转,并且具有不同旋转的多个运行可能导致基线弯曲或曲折为了解决这种(罕见的)可能性,指定一些API返回指标,在基线相对坐标中的值(例如,上升,提前),并且其他在"在标准
 * 坐标"(例如,getBounds)中。
 * 基线相对坐标中的值将"x"坐标映射到沿着基线的距离沿着基线),以及"y"坐标到在"x"处沿基线的垂线的距离(正y是从基线向量顺时针旋转90度)。
 * 沿着x和y轴测量标准坐标中的值,其中0,0在TextLayout的原点每个相关API的文档指示什么值在什么坐标系中。一般来说,与测量相关的API在基线相对坐标中,而与显示相关的API在标准坐标中。
 * 
 * 
 * @see LineBreakMeasurer
 * @see TextAttribute
 * @see TextHitInfo
 * @see LayoutPath
 */
public final class TextLayout implements Cloneable {

    private int characterCount;
    private boolean isVerticalLine = false;
    private byte baseline;
    private float[] baselineOffsets;  // why have these ?
    private TextLine textLine;

    // cached values computed from GlyphSets and set info:
    // all are recomputed from scratch in buildCache()
    private TextLine.TextLineMetrics lineMetrics = null;
    private float visibleAdvance;
    private int hashCodeCache;

    /*
     * TextLayouts are supposedly immutable.  If you mutate a TextLayout under
     * the covers (like the justification code does) you'll need to set this
     * back to false.  Could be replaced with textLine != null <--> cacheIsValid.
     * <p>
     * TextLayouts被认为是不可变的如果你改变一个TextLayout在覆盖下(像对齐代码),你需要将其设置为false可以替换为textLine！= null < - > cacheIsValid。
     * 
     */
    private boolean cacheIsValid = false;


    // This value is obtained from an attribute, and constrained to the
    // interval [0,1].  If 0, the layout cannot be justified.
    private float justifyRatio;

    // If a layout is produced by justification, then that layout
    // cannot be justified.  To enforce this constraint the
    // justifyRatio of the justified layout is set to this value.
    private static final float ALREADY_JUSTIFIED = -53.9f;

    // dx and dy specify the distance between the TextLayout's origin
    // and the origin of the leftmost GlyphSet (TextLayoutComponent,
    // actually).  They were used for hanging punctuation support,
    // which is no longer implemented.  Currently they are both always 0,
    // and TextLayout is not guaranteed to work with non-zero dx, dy
    // values right now.  They were left in as an aide and reminder to
    // anyone who implements hanging punctuation or other similar stuff.
    // They are static now so they don't take up space in TextLayout
    // instances.
    private static float dx;
    private static float dy;

    /*
     * Natural bounds is used internally.  It is built on demand in
     * getNaturalBounds.
     * <p>
     *  自然界在内部使用它是建立在getNaturalBounds的需求
     * 
     */
    private Rectangle2D naturalBounds = null;

    /*
     * boundsRect encloses all of the bits this TextLayout can draw.  It
     * is build on demand in getBounds.
     * <p>
     *  boundsRect包含这个TextLayout可以绘制的所有位它是根据需求在getBounds中构建的
     * 
     */
    private Rectangle2D boundsRect = null;

    /*
     * flag to supress/allow carets inside of ligatures when hit testing or
     * arrow-keying
     * <p>
     *  标志以在命中测试或箭头键时抑制/允许连字内的插入符号
     * 
     */
    private boolean caretsInLigaturesAreAllowed = false;

    /**
     * Defines a policy for determining the strong caret location.
     * This class contains one method, <code>getStrongCaret</code>, which
     * is used to specify the policy that determines the strong caret in
     * dual-caret text.  The strong caret is used to move the caret to the
     * left or right. Instances of this class can be passed to
     * <code>getCaretShapes</code>, <code>getNextLeftHit</code> and
     * <code>getNextRightHit</code> to customize strong caret
     * selection.
     * <p>
     * To specify alternate caret policies, subclass <code>CaretPolicy</code>
     * and override <code>getStrongCaret</code>.  <code>getStrongCaret</code>
     * should inspect the two <code>TextHitInfo</code> arguments and choose
     * one of them as the strong caret.
     * <p>
     * Most clients do not need to use this class.
     * <p>
     * 定义用于确定强插入符位置的策略此类包含一个方法<code> getStrongCaret </code>,用于指定确定双插入符文本中的强插入符的策略。
     * 强插入符用于移动插入符到左边或右边这个类的实例可以传递到<code> getCaretShapes </code>,<code> getNextLeftHit </code>和<code> getNex
     * tRightHit </code>。
     * 定义用于确定强插入符位置的策略此类包含一个方法<code> getStrongCaret </code>,用于指定确定双插入符文本中的强插入符的策略。
     * <p>
     *  要指定替换插入符策略,子类<code> CaretPolicy </code>并覆盖<code> getStrongCaret </code> <code> getStrongCaret </code>
     * 应检查两个<code> TextHitInfo </code>他们作为强的插入符号。
     * <p>
     *  大多数客户端不需要使用这个类
     * 
     */
    public static class CaretPolicy {

        /**
         * Constructs a <code>CaretPolicy</code>.
         * <p>
         *  构造一个<code> CaretPolicy </code>
         * 
         */
         public CaretPolicy() {
         }

        /**
         * Chooses one of the specified <code>TextHitInfo</code> instances as
         * a strong caret in the specified <code>TextLayout</code>.
         * <p>
         * 选择指定的<code> TextHitInfo </code>实例之一作为指定的<code> TextLayout </code>中的强脱字符
         * 
         * 
         * @param hit1 a valid hit in <code>layout</code>
         * @param hit2 a valid hit in <code>layout</code>
         * @param layout the <code>TextLayout</code> in which
         *        <code>hit1</code> and <code>hit2</code> are used
         * @return <code>hit1</code> or <code>hit2</code>
         *        (or an equivalent <code>TextHitInfo</code>), indicating the
         *        strong caret.
         */
        public TextHitInfo getStrongCaret(TextHitInfo hit1,
                                          TextHitInfo hit2,
                                          TextLayout layout) {

            // default implementation just calls private method on layout
            return layout.getStrongHit(hit1, hit2);
        }
    }

    /**
     * This <code>CaretPolicy</code> is used when a policy is not specified
     * by the client.  With this policy, a hit on a character whose direction
     * is the same as the line direction is stronger than a hit on a
     * counterdirectional character.  If the characters' directions are
     * the same, a hit on the leading edge of a character is stronger
     * than a hit on the trailing edge of a character.
     * <p>
     *  当客户端没有指定策略时,使用此<code> CaretPolicy </code>使用此策略,对与方向与行方向相同的字符的命中比对反向字符的命中更强如果字符'方向相同,在字符的前边缘上的命中比在字符的
     * 后边缘上的命中更强。
     * 
     */
    public static final CaretPolicy DEFAULT_CARET_POLICY = new CaretPolicy();

    /**
     * Constructs a <code>TextLayout</code> from a <code>String</code>
     * and a {@link Font}.  All the text is styled using the specified
     * <code>Font</code>.
     * <p>
     * The <code>String</code> must specify a single paragraph of text,
     * because an entire paragraph is required for the bidirectional
     * algorithm.
     * <p>
     *  从<code> String </code>和{@link Font}构造<code> TextLayout </code>所有文本都使用指定的<code> Font </code>
     * <p>
     *  <code> String </code>必须指定文本的单个段落,因为双向算法需要整个段落
     * 
     * 
     * @param string the text to display
     * @param font a <code>Font</code> used to style the text
     * @param frc contains information about a graphics device which is needed
     *       to measure the text correctly.
     *       Text measurements can vary slightly depending on the
     *       device resolution, and attributes such as antialiasing.  This
     *       parameter does not specify a translation between the
     *       <code>TextLayout</code> and user space.
     */
    public TextLayout(String string, Font font, FontRenderContext frc) {

        if (font == null) {
            throw new IllegalArgumentException("Null font passed to TextLayout constructor.");
        }

        if (string == null) {
            throw new IllegalArgumentException("Null string passed to TextLayout constructor.");
        }

        if (string.length() == 0) {
            throw new IllegalArgumentException("Zero length string passed to TextLayout constructor.");
        }

        Map<? extends Attribute, ?> attributes = null;
        if (font.hasLayoutAttributes()) {
            attributes = font.getAttributes();
        }

        char[] text = string.toCharArray();
        if (sameBaselineUpTo(font, text, 0, text.length) == text.length) {
            fastInit(text, font, attributes, frc);
        } else {
            AttributedString as = attributes == null
                ? new AttributedString(string)
                : new AttributedString(string, attributes);
            as.addAttribute(TextAttribute.FONT, font);
            standardInit(as.getIterator(), text, frc);
        }
    }

    /**
     * Constructs a <code>TextLayout</code> from a <code>String</code>
     * and an attribute set.
     * <p>
     * All the text is styled using the provided attributes.
     * <p>
     * <code>string</code> must specify a single paragraph of text because an
     * entire paragraph is required for the bidirectional algorithm.
     * <p>
     * 从<code> String </code>和属性集构造<code> TextLayout </code>
     * <p>
     *  所有文本都使用提供的属性设置样式
     * <p>
     *  <code> string </code>必须指定单个文本段落,因为双向算法需要整个段落
     * 
     * 
     * @param string the text to display
     * @param attributes the attributes used to style the text
     * @param frc contains information about a graphics device which is needed
     *       to measure the text correctly.
     *       Text measurements can vary slightly depending on the
     *       device resolution, and attributes such as antialiasing.  This
     *       parameter does not specify a translation between the
     *       <code>TextLayout</code> and user space.
     */
    public TextLayout(String string, Map<? extends Attribute,?> attributes,
                      FontRenderContext frc)
    {
        if (string == null) {
            throw new IllegalArgumentException("Null string passed to TextLayout constructor.");
        }

        if (attributes == null) {
            throw new IllegalArgumentException("Null map passed to TextLayout constructor.");
        }

        if (string.length() == 0) {
            throw new IllegalArgumentException("Zero length string passed to TextLayout constructor.");
        }

        char[] text = string.toCharArray();
        Font font = singleFont(text, 0, text.length, attributes);
        if (font != null) {
            fastInit(text, font, attributes, frc);
        } else {
            AttributedString as = new AttributedString(string, attributes);
            standardInit(as.getIterator(), text, frc);
        }
    }

    /*
     * Determines a font for the attributes, and if a single font can render
     * all the text on one baseline, return it, otherwise null.  If the
     * attributes specify a font, assume it can display all the text without
     * checking.
     * If the AttributeSet contains an embedded graphic, return null.
     * <p>
     *  确定属性的字体,如果单个字体可以渲染一个基线上的所有文本,则返回它,否则为null如果属性指定字体,则假定它可以显示所有文本,而不检查如果AttributeSet包含嵌入图形,返回null
     * 
     */
    private static Font singleFont(char[] text,
                                   int start,
                                   int limit,
                                   Map<? extends Attribute, ?> attributes) {

        if (attributes.get(TextAttribute.CHAR_REPLACEMENT) != null) {
            return null;
        }

        Font font = null;
        try {
            font = (Font)attributes.get(TextAttribute.FONT);
        }
        catch (ClassCastException e) {
        }
        if (font == null) {
            if (attributes.get(TextAttribute.FAMILY) != null) {
                font = Font.getFont(attributes);
                if (font.canDisplayUpTo(text, start, limit) != -1) {
                    return null;
                }
            } else {
                FontResolver resolver = FontResolver.getInstance();
                CodePointIterator iter = CodePointIterator.create(text, start, limit);
                int fontIndex = resolver.nextFontRunIndex(iter);
                if (iter.charIndex() == limit) {
                    font = resolver.getFont(fontIndex, attributes);
                }
            }
        }

        if (sameBaselineUpTo(font, text, start, limit) != limit) {
            return null;
        }

        return font;
    }

    /**
     * Constructs a <code>TextLayout</code> from an iterator over styled text.
     * <p>
     * The iterator must specify a single paragraph of text because an
     * entire paragraph is required for the bidirectional
     * algorithm.
     * <p>
     *  在迭代器的样式文本上构造一个<code> TextLayout </code>
     * <p>
     *  迭代器必须指定单个文本段落,因为双向算法需要整个段落
     * 
     * 
     * @param text the styled text to display
     * @param frc contains information about a graphics device which is needed
     *       to measure the text correctly.
     *       Text measurements can vary slightly depending on the
     *       device resolution, and attributes such as antialiasing.  This
     *       parameter does not specify a translation between the
     *       <code>TextLayout</code> and user space.
     */
    public TextLayout(AttributedCharacterIterator text, FontRenderContext frc) {

        if (text == null) {
            throw new IllegalArgumentException("Null iterator passed to TextLayout constructor.");
        }

        int start = text.getBeginIndex();
        int limit = text.getEndIndex();
        if (start == limit) {
            throw new IllegalArgumentException("Zero length iterator passed to TextLayout constructor.");
        }

        int len = limit - start;
        text.first();
        char[] chars = new char[len];
        int n = 0;
        for (char c = text.first();
             c != CharacterIterator.DONE;
             c = text.next())
        {
            chars[n++] = c;
        }

        text.first();
        if (text.getRunLimit() == limit) {

            Map<? extends Attribute, ?> attributes = text.getAttributes();
            Font font = singleFont(chars, 0, len, attributes);
            if (font != null) {
                fastInit(chars, font, attributes, frc);
                return;
            }
        }

        standardInit(text, chars, frc);
    }

    /**
     * Creates a <code>TextLayout</code> from a {@link TextLine} and
     * some paragraph data.  This method is used by {@link TextMeasurer}.
     * <p>
     * 从{@link TextLine}和一些段落数据创建<code> TextLayout </code>此方法由{@link TextMeasurer}
     * 
     * 
     * @param textLine the line measurement attributes to apply to the
     *       the resulting <code>TextLayout</code>
     * @param baseline the baseline of the text
     * @param baselineOffsets the baseline offsets for this
     * <code>TextLayout</code>.  This should already be normalized to
     * <code>baseline</code>
     * @param justifyRatio <code>0</code> if the <code>TextLayout</code>
     *     cannot be justified; <code>1</code> otherwise.
     */
    TextLayout(TextLine textLine,
               byte baseline,
               float[] baselineOffsets,
               float justifyRatio) {

        this.characterCount = textLine.characterCount();
        this.baseline = baseline;
        this.baselineOffsets = baselineOffsets;
        this.textLine = textLine;
        this.justifyRatio = justifyRatio;
    }

    /**
     * Initialize the paragraph-specific data.
     * <p>
     *  初始化段落特定的数据
     * 
     */
    private void paragraphInit(byte aBaseline, CoreMetrics lm,
                               Map<? extends Attribute, ?> paragraphAttrs,
                               char[] text) {

        baseline = aBaseline;

        // normalize to current baseline
        baselineOffsets = TextLine.getNormalizedOffsets(lm.baselineOffsets, baseline);

        justifyRatio = AttributeValues.getJustification(paragraphAttrs);
        NumericShaper shaper = AttributeValues.getNumericShaping(paragraphAttrs);
        if (shaper != null) {
            shaper.shape(text, 0, text.length);
        }
    }

    /*
     * the fast init generates a single glyph set.  This requires:
     * all one style
     * all renderable by one font (ie no embedded graphics)
     * all on one baseline
     * <p>
     *  快速初始化生成单个字形集这需要：所有一个样式都可以由一个字体(即没有嵌入图形)全部在一个基线上呈现
     * 
     */
    private void fastInit(char[] chars, Font font,
                          Map<? extends Attribute, ?> attrs,
                          FontRenderContext frc) {

        // Object vf = attrs.get(TextAttribute.ORIENTATION);
        // isVerticalLine = TextAttribute.ORIENTATION_VERTICAL.equals(vf);
        isVerticalLine = false;

        LineMetrics lm = font.getLineMetrics(chars, 0, chars.length, frc);
        CoreMetrics cm = CoreMetrics.get(lm);
        byte glyphBaseline = (byte) cm.baselineIndex;

        if (attrs == null) {
            baseline = glyphBaseline;
            baselineOffsets = cm.baselineOffsets;
            justifyRatio = 1.0f;
        } else {
            paragraphInit(glyphBaseline, cm, attrs, chars);
        }

        characterCount = chars.length;

        textLine = TextLine.fastCreateTextLine(frc, chars, font, cm, attrs);
    }

    /*
     * the standard init generates multiple glyph sets based on style,
     * renderable, and baseline runs.
     * <p>
     *  标准init基于样式,可呈现和基线运行生成多个字形集
     * 
     * 
     * @param chars the text in the iterator, extracted into a char array
     */
    private void standardInit(AttributedCharacterIterator text, char[] chars, FontRenderContext frc) {

        characterCount = chars.length;

        // set paragraph attributes
        {
            // If there's an embedded graphic at the start of the
            // paragraph, look for the first non-graphic character
            // and use it and its font to initialize the paragraph.
            // If not, use the first graphic to initialize.

            Map<? extends Attribute, ?> paragraphAttrs = text.getAttributes();

            boolean haveFont = TextLine.advanceToFirstFont(text);

            if (haveFont) {
                Font defaultFont = TextLine.getFontAtCurrentPos(text);
                int charsStart = text.getIndex() - text.getBeginIndex();
                LineMetrics lm = defaultFont.getLineMetrics(chars, charsStart, charsStart+1, frc);
                CoreMetrics cm = CoreMetrics.get(lm);
                paragraphInit((byte)cm.baselineIndex, cm, paragraphAttrs, chars);
            }
            else {
                // hmmm what to do here?  Just try to supply reasonable
                // values I guess.

                GraphicAttribute graphic = (GraphicAttribute)
                                paragraphAttrs.get(TextAttribute.CHAR_REPLACEMENT);
                byte defaultBaseline = getBaselineFromGraphic(graphic);
                CoreMetrics cm = GraphicComponent.createCoreMetrics(graphic);
                paragraphInit(defaultBaseline, cm, paragraphAttrs, chars);
            }
        }

        textLine = TextLine.standardCreateTextLine(frc, text, chars, baselineOffsets);
    }

    /*
     * A utility to rebuild the ascent/descent/leading/advance cache.
     * You'll need to call this if you clone and mutate (like justification,
     * editing methods do)
     * <p>
     *  重建上升/下降/前进/提前缓存的实用程序如果您克隆和变异(如调整,编辑方法),您将需要调用此方法,
     * 
     */
    private void ensureCache() {
        if (!cacheIsValid) {
            buildCache();
        }
    }

    private void buildCache() {
        lineMetrics = textLine.getMetrics();

        // compute visibleAdvance
        if (textLine.isDirectionLTR()) {

            int lastNonSpace = characterCount-1;
            while (lastNonSpace != -1) {
                int logIndex = textLine.visualToLogical(lastNonSpace);
                if (!textLine.isCharSpace(logIndex)) {
                    break;
                }
                else {
                    --lastNonSpace;
                }
            }
            if (lastNonSpace == characterCount-1) {
                visibleAdvance = lineMetrics.advance;
            }
            else if (lastNonSpace == -1) {
                visibleAdvance = 0;
            }
            else {
                int logIndex = textLine.visualToLogical(lastNonSpace);
                visibleAdvance = textLine.getCharLinePosition(logIndex)
                                        + textLine.getCharAdvance(logIndex);
            }
        }
        else {

            int leftmostNonSpace = 0;
            while (leftmostNonSpace != characterCount) {
                int logIndex = textLine.visualToLogical(leftmostNonSpace);
                if (!textLine.isCharSpace(logIndex)) {
                    break;
                }
                else {
                    ++leftmostNonSpace;
                }
            }
            if (leftmostNonSpace == characterCount) {
                visibleAdvance = 0;
            }
            else if (leftmostNonSpace == 0) {
                visibleAdvance = lineMetrics.advance;
            }
            else {
                int logIndex = textLine.visualToLogical(leftmostNonSpace);
                float pos = textLine.getCharLinePosition(logIndex);
                visibleAdvance = lineMetrics.advance - pos;
            }
        }

        // naturalBounds, boundsRect will be generated on demand
        naturalBounds = null;
        boundsRect = null;

        // hashCode will be regenerated on demand
        hashCodeCache = 0;

        cacheIsValid = true;
    }

    /**
     * The 'natural bounds' encloses all the carets the layout can draw.
     *
     * <p>
     *  "自然边界"包含布局可以绘制的所有插入符号
     * 
     */
    private Rectangle2D getNaturalBounds() {
        ensureCache();

        if (naturalBounds == null) {
            naturalBounds = textLine.getItalicBounds();
        }

        return naturalBounds;
    }

    /**
     * Creates a copy of this <code>TextLayout</code>.
     * <p>
     *  创建此<code> TextLayout </code>的副本
     * 
     */
    protected Object clone() {
        /*
         * !!! I think this is safe.  Once created, nothing mutates the
         * glyphvectors or arrays.  But we need to make sure.
         * {jbr} actually, that's not quite true.  The justification code
         * mutates after cloning.  It doesn't actually change the glyphvectors
         * (that's impossible) but it replaces them with justified sets.  This
         * is a problem for GlyphIterator creation, since new GlyphIterators
         * are created by cloning a prototype.  If the prototype has outdated
         * glyphvectors, so will the new ones.  A partial solution is to set the
         * prototypical GlyphIterator to null when the glyphvectors change.  If
         * you forget this one time, you're hosed.
         * <p>
         * 谢谢！我认为这是安全的一旦创建,没有突变的glyphvectors或数组但我们需要确保{jbr}实际上,这不是真的克隆代码mutated后克隆它实际上不改变glyphvectors(这是不可能的),但它
         * 取代他们与合理的集合这是一个问题的GlyphIterator创建,因为新的GlyphIterator是通过克隆原型创建的如果原型有过时的glyphvectors,那么新的一部分解决方案是设置原型Glyp
         * hIterator为null当glyphvectors改变时如果你忘记这一次,你是爱的。
         * 
         */
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    /*
     * Utility to throw an expection if an invalid TextHitInfo is passed
     * as a parameter.  Avoids code duplication.
     * <p>
     *  用于在无效的TextHitInfo作为参数传递时抛出异常的实用程序避免代码重复
     * 
     */
    private void checkTextHit(TextHitInfo hit) {
        if (hit == null) {
            throw new IllegalArgumentException("TextHitInfo is null.");
        }

        if (hit.getInsertionIndex() < 0 ||
            hit.getInsertionIndex() > characterCount) {
            throw new IllegalArgumentException("TextHitInfo is out of range");
        }
    }

    /**
     * Creates a copy of this <code>TextLayout</code> justified to the
     * specified width.
     * <p>
     * If this <code>TextLayout</code> has already been justified, an
     * exception is thrown.  If this <code>TextLayout</code> object's
     * justification ratio is zero, a <code>TextLayout</code> identical
     * to this <code>TextLayout</code> is returned.
     * <p>
     * 创建<code> TextLayout </code>的对齐到指定宽度的副本
     * <p>
     *  如果这个<code> TextLayout </code>已经被调整,抛出一个异常如果这个<code> TextLayout </code>对象的调整比例是零,一个<code> TextLayout 
     * </code> TextLayout </code>。
     * 
     * 
     * @param justificationWidth the width to use when justifying the line.
     * For best results, it should not be too different from the current
     * advance of the line.
     * @return a <code>TextLayout</code> justified to the specified width.
     * @exception Error if this layout has already been justified, an Error is
     * thrown.
     */
    public TextLayout getJustifiedLayout(float justificationWidth) {

        if (justificationWidth <= 0) {
            throw new IllegalArgumentException("justificationWidth <= 0 passed to TextLayout.getJustifiedLayout()");
        }

        if (justifyRatio == ALREADY_JUSTIFIED) {
            throw new Error("Can't justify again.");
        }

        ensureCache(); // make sure textLine is not null

        // default justification range to exclude trailing logical whitespace
        int limit = characterCount;
        while (limit > 0 && textLine.isCharWhitespace(limit-1)) {
            --limit;
        }

        TextLine newLine = textLine.getJustifiedLine(justificationWidth, justifyRatio, 0, limit);
        if (newLine != null) {
            return new TextLayout(newLine, baseline, baselineOffsets, ALREADY_JUSTIFIED);
        }

        return this;
    }

    /**
     * Justify this layout.  Overridden by subclassers to control justification
     * (if there were subclassers, that is...)
     *
     * The layout will only justify if the paragraph attributes (from the
     * source text, possibly defaulted by the layout attributes) indicate a
     * non-zero justification ratio.  The text will be justified to the
     * indicated width.  The current implementation also adjusts hanging
     * punctuation and trailing whitespace to overhang the justification width.
     * Once justified, the layout may not be rejustified.
     * <p>
     * Some code may rely on immutablity of layouts.  Subclassers should not
     * call this directly, but instead should call getJustifiedLayout, which
     * will call this method on a clone of this layout, preserving
     * the original.
     *
     * <p>
     *  调整此布局通过子类来覆盖以控制对齐(如果有子类,那是)
     * 
     * 布局只会证明段落属性(从源文本,可能由布局属性默认)指示非零对齐比率文本将被对齐到指示的宽度。当前实现还调整悬挂标点符号和尾部空格悬停对齐宽度一旦对齐,布局可能不会重新调整
     * <p>
     *  一些代码可能依赖于布局的不可变子类别不应该直接调用它,而是应该调用getJustifiedLayout,它将在此布局的克隆上调用此方法,保留原始
     * 
     * 
     * @param justificationWidth the width to use when justifying the line.
     * For best results, it should not be too different from the current
     * advance of the line.
     * @see #getJustifiedLayout(float)
     */
    protected void handleJustify(float justificationWidth) {
      // never called
    }


    /**
     * Returns the baseline for this <code>TextLayout</code>.
     * The baseline is one of the values defined in <code>Font</code>,
     * which are roman, centered and hanging.  Ascent and descent are
     * relative to this baseline.  The <code>baselineOffsets</code>
     * are also relative to this baseline.
     * <p>
     * 返回此<code> TextLayout </code>的基线。基线是<code> Font </code>中定义的值之一,它们是罗马语,居中和垂直的。
     * 上升和下降是相对于此基线的<code> baselineOffsets </code>也相对于此基线。
     * 
     * 
     * @return the baseline of this <code>TextLayout</code>.
     * @see #getBaselineOffsets()
     * @see Font
     */
    public byte getBaseline() {
        return baseline;
    }

    /**
     * Returns the offsets array for the baselines used for this
     * <code>TextLayout</code>.
     * <p>
     * The array is indexed by one of the values defined in
     * <code>Font</code>, which are roman, centered and hanging.  The
     * values are relative to this <code>TextLayout</code> object's
     * baseline, so that <code>getBaselineOffsets[getBaseline()] == 0</code>.
     * Offsets are added to the position of the <code>TextLayout</code>
     * object's baseline to get the position for the new baseline.
     * <p>
     *  返回用于此<code> TextLayout </code>的基线的offsets数组
     * <p>
     *  数组由<code> Font </code>中定义的值之一索引,这些值是罗马,居中和悬挂的。
     * 这些值是相对于这个<code> TextLayout </code>对象的基线,所以<code> getBaselineOffsets [getBaseline()] == 0 </code>偏移被添加
     * 到<code> TextLayout </code>对象的基线的位置,以获得新基线的位置。
     *  数组由<code> Font </code>中定义的值之一索引,这些值是罗马,居中和悬挂的。
     * 
     * 
     * @return the offsets array containing the baselines used for this
     *    <code>TextLayout</code>.
     * @see #getBaseline()
     * @see Font
     */
    public float[] getBaselineOffsets() {
        float[] offsets = new float[baselineOffsets.length];
        System.arraycopy(baselineOffsets, 0, offsets, 0, offsets.length);
        return offsets;
    }

    /**
     * Returns the advance of this <code>TextLayout</code>.
     * The advance is the distance from the origin to the advance of the
     * rightmost (bottommost) character.  This is in baseline-relative
     * coordinates.
     * <p>
     * 返回<code> TextLayout </code>的提前。advance是从原点到最右边(最底部)字符的前进距离。这是基线相对坐标
     * 
     * 
     * @return the advance of this <code>TextLayout</code>.
     */
    public float getAdvance() {
        ensureCache();
        return lineMetrics.advance;
    }

    /**
     * Returns the advance of this <code>TextLayout</code>, minus trailing
     * whitespace.  This is in baseline-relative coordinates.
     * <p>
     *  返回这个<code> TextLayout </code>的前进,减去尾部空格这是在基线相对坐标中
     * 
     * 
     * @return the advance of this <code>TextLayout</code> without the
     *      trailing whitespace.
     * @see #getAdvance()
     */
    public float getVisibleAdvance() {
        ensureCache();
        return visibleAdvance;
    }

    /**
     * Returns the ascent of this <code>TextLayout</code>.
     * The ascent is the distance from the top (right) of the
     * <code>TextLayout</code> to the baseline.  It is always either
     * positive or zero.  The ascent is sufficient to
     * accommodate superscripted text and is the maximum of the sum of the
     * ascent, offset, and baseline of each glyph.  The ascent is
     * the maximum ascent from the baseline of all the text in the
     * TextLayout.  It is in baseline-relative coordinates.
     * <p>
     *  返回这个<code> TextLayout </code>的上升Ascent是从<code> TextLayout </code>的顶部(右)到基线的距离。它总是为正或零。
     * 上升足以容纳上标文本,并且是每个字形的上升,偏移和基线的总和的上限上升是从TextLayout中的所有文本的基线的最大上升它是在基线相对坐标。
     * 
     * 
     * @return the ascent of this <code>TextLayout</code>.
     */
    public float getAscent() {
        ensureCache();
        return lineMetrics.ascent;
    }

    /**
     * Returns the descent of this <code>TextLayout</code>.
     * The descent is the distance from the baseline to the bottom (left) of
     * the <code>TextLayout</code>.  It is always either positive or zero.
     * The descent is sufficient to accommodate subscripted text and is the
     * maximum of the sum of the descent, offset, and baseline of each glyph.
     * This is the maximum descent from the baseline of all the text in
     * the TextLayout.  It is in baseline-relative coordinates.
     * <p>
     * 返回此<code> TextLayout </code>的下降。
     * 下降是从基线到<code> TextLayout </code>底部的距离(左)它总是正或零下降足以容纳下标文本,并且是每个字形的下降,偏移和基线的总和的最大值这是从TextLayout中的所有文本的基
     * 线的最大下降它是在基线相对坐标。
     * 返回此<code> TextLayout </code>的下降。
     * 
     * 
     * @return the descent of this <code>TextLayout</code>.
     */
    public float getDescent() {
        ensureCache();
        return lineMetrics.descent;
    }

    /**
     * Returns the leading of the <code>TextLayout</code>.
     * The leading is the suggested interline spacing for this
     * <code>TextLayout</code>.  This is in baseline-relative
     * coordinates.
     * <p>
     * The leading is computed from the leading, descent, and baseline
     * of all glyphvectors in the <code>TextLayout</code>.  The algorithm
     * is roughly as follows:
     * <blockquote><pre>
     * maxD = 0;
     * maxDL = 0;
     * for (GlyphVector g in all glyphvectors) {
     *    maxD = max(maxD, g.getDescent() + offsets[g.getBaseline()]);
     *    maxDL = max(maxDL, g.getDescent() + g.getLeading() +
     *                       offsets[g.getBaseline()]);
     * }
     * return maxDL - maxD;
     * </pre></blockquote>
     * <p>
     *  返回<code> TextLayout </code>的前导。前导是此<code> TextLayout </code>的建议行间距。这是基线相对坐标
     * <p>
     * 前导是从<code> TextLayout </code>中所有glyphvectors的前导,下降和基线计算的。
     * 该算法大致如下：<blockquote> <pre> maxD = 0; maxDL = 0; for(GlyphVector g in all glyphvectors){maxD = max(maxD,ggetDescent()+ offsets [ggetBaseline()]); maxDL = max(maxDL,ggetDescent()+ ggetLeading()+ offsets [ggetBaseline()]); }
     *  return maxDL  -  maxD; </pre> </blockquote>。
     * 前导是从<code> TextLayout </code>中所有glyphvectors的前导,下降和基线计算的。
     * 
     * 
     * @return the leading of this <code>TextLayout</code>.
     */
    public float getLeading() {
        ensureCache();
        return lineMetrics.leading;
    }

    /**
     * Returns the bounds of this <code>TextLayout</code>.
     * The bounds are in standard coordinates.
     * <p>Due to rasterization effects, this bounds might not enclose all of the
     * pixels rendered by the TextLayout.</p>
     * It might not coincide exactly with the ascent, descent,
     * origin or advance of the <code>TextLayout</code>.
     * <p>
     *  返回此<code> TextLayout </code>的边界。边界在标准坐标中<p>由于光栅化效果,此边界可能不会包含由TextLayout </p>呈现的所有像素。
     * 它可能不完全与TextLayout </code>的上升,下降,起点或前进。
     * 
     * 
     * @return a {@link Rectangle2D} that is the bounds of this
     *        <code>TextLayout</code>.
     */
    public Rectangle2D getBounds() {
        ensureCache();

        if (boundsRect == null) {
            Rectangle2D vb = textLine.getVisualBounds();
            if (dx != 0 || dy != 0) {
                vb.setRect(vb.getX() - dx,
                           vb.getY() - dy,
                           vb.getWidth(),
                           vb.getHeight());
            }
            boundsRect = vb;
        }

        Rectangle2D bounds = new Rectangle2D.Float();
        bounds.setRect(boundsRect);

        return bounds;
    }

    /**
     * Returns the pixel bounds of this <code>TextLayout</code> when
     * rendered in a graphics with the given
     * <code>FontRenderContext</code> at the given location.  The
     * graphics render context need not be the same as the
     * <code>FontRenderContext</code> used to create this
     * <code>TextLayout</code>, and can be null.  If it is null, the
     * <code>FontRenderContext</code> of this <code>TextLayout</code>
     * is used.
     * <p>
     * 当在给定位置以给定<code> FontRenderContext </code>的图形呈现时,返回此<code> TextLayout </code>的像素边界图形呈现上下文不需要与<code> Fo
     * ntRenderContext < / code>用于创建此<code> TextLayout </code>,并且可以为null如果为null,则使用此<code> TextLayout </code>
     * 的<code> FontRenderContext </code>。
     * 
     * 
     * @param frc the <code>FontRenderContext</code> of the <code>Graphics</code>.
     * @param x the x-coordinate at which to render this <code>TextLayout</code>.
     * @param y the y-coordinate at which to render this <code>TextLayout</code>.
     * @return a <code>Rectangle</code> bounding the pixels that would be affected.
     * @see GlyphVector#getPixelBounds
     * @since 1.6
     */
    public Rectangle getPixelBounds(FontRenderContext frc, float x, float y) {
        return textLine.getPixelBounds(frc, x, y);
    }

    /**
     * Returns <code>true</code> if this <code>TextLayout</code> has
     * a left-to-right base direction or <code>false</code> if it has
     * a right-to-left base direction.  The <code>TextLayout</code>
     * has a base direction of either left-to-right (LTR) or
     * right-to-left (RTL).  The base direction is independent of the
     * actual direction of text on the line, which may be either LTR,
     * RTL, or mixed. Left-to-right layouts by default should position
     * flush left.  If the layout is on a tabbed line, the
     * tabs run left to right, so that logically successive layouts position
     * left to right.  The opposite is true for RTL layouts. By default they
     * should position flush left, and tabs run right-to-left.
     * <p>
     * 如果此<code> TextLayout </code>具有从左到右的基本方向,则返回<code> true </code>,如果它具有从右到左的基本方向,则返回<code> true </code>代
     * 码> TextLayout </code>具有从左到右(LTR)或从右到左(RTL)的基本方向。
     * 基本方向与行上文本的实际方向无关,可以是LTR ,RTL或混合从左到右布局默认情况下应该位置向左如果布局在选项卡行上,选项卡从左到右运行,使逻辑上连续的布局位置从左到右相反是真实的RTL布局通过默认情况
     * 下它们应该左对齐,选项卡从右到左。
     * 
     * 
     * @return <code>true</code> if the base direction of this
     *         <code>TextLayout</code> is left-to-right; <code>false</code>
     *         otherwise.
     */
    public boolean isLeftToRight() {
        return textLine.isDirectionLTR();
    }

    /**
     * Returns <code>true</code> if this <code>TextLayout</code> is vertical.
     * <p>
     *  如果此<code> TextLayout </code>是垂直的,则返回<code> true </code>
     * 
     * 
     * @return <code>true</code> if this <code>TextLayout</code> is vertical;
     *      <code>false</code> otherwise.
     */
    public boolean isVertical() {
        return isVerticalLine;
    }

    /**
     * Returns the number of characters represented by this
     * <code>TextLayout</code>.
     * <p>
     * 返回由此<code> TextLayout </code>表示的字符数
     * 
     * 
     * @return the number of characters in this <code>TextLayout</code>.
     */
    public int getCharacterCount() {
        return characterCount;
    }

    /*
     * carets and hit testing
     *
     * Positions on a text line are represented by instances of TextHitInfo.
     * Any TextHitInfo with characterOffset between 0 and characterCount-1,
     * inclusive, represents a valid position on the line.  Additionally,
     * [-1, trailing] and [characterCount, leading] are valid positions, and
     * represent positions at the logical start and end of the line,
     * respectively.
     *
     * The characterOffsets in TextHitInfo's used and returned by TextLayout
     * are relative to the beginning of the text layout, not necessarily to
     * the beginning of the text storage the client is using.
     *
     *
     * Every valid TextHitInfo has either one or two carets associated with it.
     * A caret is a visual location in the TextLayout indicating where text at
     * the TextHitInfo will be displayed on screen.  If a TextHitInfo
     * represents a location on a directional boundary, then there are two
     * possible visible positions for newly inserted text.  Consider the
     * following example, in which capital letters indicate right-to-left text,
     * and the overall line direction is left-to-right:
     *
     * Text Storage: [ a, b, C, D, E, f ]
     * Display:        a b E D C f
     *
     * The text hit info (1, t) represents the trailing side of 'b'.  If 'q',
     * a left-to-right character is inserted into the text storage at this
     * location, it will be displayed between the 'b' and the 'E':
     *
     * Text Storage: [ a, b, q, C, D, E, f ]
     * Display:        a b q E D C f
     *
     * However, if a 'W', which is right-to-left, is inserted into the storage
     * after 'b', the storage and display will be:
     *
     * Text Storage: [ a, b, W, C, D, E, f ]
     * Display:        a b E D C W f
     *
     * So, for the original text storage, two carets should be displayed for
     * location (1, t): one visually between 'b' and 'E' and one visually
     * between 'C' and 'f'.
     *
     *
     * When two carets are displayed for a TextHitInfo, one caret is the
     * 'strong' caret and the other is the 'weak' caret.  The strong caret
     * indicates where an inserted character will be displayed when that
     * character's direction is the same as the direction of the TextLayout.
     * The weak caret shows where an character inserted character will be
     * displayed when the character's direction is opposite that of the
     * TextLayout.
     *
     *
     * Clients should not be overly concerned with the details of correct
     * caret display. TextLayout.getCaretShapes(TextHitInfo) will return an
     * array of two paths representing where carets should be displayed.
     * The first path in the array is the strong caret; the second element,
     * if non-null, is the weak caret.  If the second element is null,
     * then there is no weak caret for the given TextHitInfo.
     *
     *
     * Since text can be visually reordered, logically consecutive
     * TextHitInfo's may not be visually consecutive.  One implication of this
     * is that a client cannot tell from inspecting a TextHitInfo whether the
     * hit represents the first (or last) caret in the layout.  Clients
     * can call getVisualOtherHit();  if the visual companion is
     * (-1, TRAILING) or (characterCount, LEADING), then the hit is at the
     * first (last) caret position in the layout.
     * <p>
     *  脱模和打击测试
     * 
     *  文本行上的位置由TextHitInfo的实例表示任何具有characterOffset(介于0和characterCount-1之间)的TextHitInfo表示行上的有效位置。
     * 此外,[-1,尾随]和[characterCount,leading]分别表示在该行的逻辑开始和结束处的位置。
     * 
     *  由TextLayout使用和返回的TextHitInfo中的characterOffset是相对于文本布局的开始,而不一定是客户端正在使用的文本存储的开头
     * 
     * 每个有效的TextHitInfo都有一个或两个插入符与它相关联。插入符是TextLayout中的一个可视位置,指示TextHitInfo上的文本在屏幕上的显示位置。
     * 如果TextHitInfo表示方向边界上的位置,则有两个可能的可见位置对于新插入的文本请考虑以下示例,其中大写字母表示从右到左的文本,整个行方向为从左到右：。
     * 
     *  文本存储：[a,b,C,D,E,f]显示：a b E D C f
     * 
     *  文本命中信息(1,t)表示'b'的尾侧如果"q",从左到右字符插入到该位置的文本存储器中,它将显示在'b'和'E'：
     * 
     *  文本存储：[a,b,q,C,D,E,f]显示：a b q E D C f
     * 
     * 然而,如果从右到左的"W"被插入到'b'之后的存储器中,存储和显示将是：
     * 
     *  文本存储：[a,b,W,C,D,E,f]显示：a b E D C W f
     * 
     *  因此,对于原始文本存储,应该为位置(1,t)显示两个插入符号：一个在视觉上在'b'和'E'之间,一个视觉上在'C'和'f'
     * 
     *  当为TextHitInfo显示两个插入符号时,一个插入符号是"强"插入符号,另一个是"弱"插入符号。
     * 当插入字符的方向与TextLayout的方向相同时,插入符号将显示在哪里当字符的方向与TextLayout的方向相反时,弱插入符显示字符插入字符的显示位置。
     * 
     * 客户端不应该过分关注正确插入符号显示的细节TextLayoutgetCaretShapes(TextHitInfo)将返回一个两个路径的数组,表示应当显示插入符号数组中的第一个路径是强插入符号;第二个元
     * 素,如果非空,是弱插入符号如果第二个元素为null,那么对于给定的TextHitInfo没有弱插入符号。
     * 
     * 因为文本可以在视觉上重新排序,逻辑上连续的TextHitInfo可能不是视觉上连续的。
     * 这意味着客户端不能通过检查TextHitInfo告诉该命中是否表示布局中的第一个(或最后一个)插入符客户端可以调用getVisualOtherHit();如果可视伴随是(-1,TRAILING)或(ch
     * aracterCount,LEADING),则命中位于布局中的第一个(最后一个)插入位置。
     * 因为文本可以在视觉上重新排序,逻辑上连续的TextHitInfo可能不是视觉上连续的。
     * 
     */

    private float[] getCaretInfo(int caret,
                                 Rectangle2D bounds,
                                 float[] info) {

        float top1X, top2X;
        float bottom1X, bottom2X;

        if (caret == 0 || caret == characterCount) {

            float pos;
            int logIndex;
            if (caret == characterCount) {
                logIndex = textLine.visualToLogical(characterCount-1);
                pos = textLine.getCharLinePosition(logIndex)
                                        + textLine.getCharAdvance(logIndex);
            }
            else {
                logIndex = textLine.visualToLogical(caret);
                pos = textLine.getCharLinePosition(logIndex);
            }
            float angle = textLine.getCharAngle(logIndex);
            float shift = textLine.getCharShift(logIndex);
            pos += angle * shift;
            top1X = top2X = pos + angle*textLine.getCharAscent(logIndex);
            bottom1X = bottom2X = pos - angle*textLine.getCharDescent(logIndex);
        }
        else {

            {
                int logIndex = textLine.visualToLogical(caret-1);
                float angle1 = textLine.getCharAngle(logIndex);
                float pos1 = textLine.getCharLinePosition(logIndex)
                                    + textLine.getCharAdvance(logIndex);
                if (angle1 != 0) {
                    pos1 += angle1 * textLine.getCharShift(logIndex);
                    top1X = pos1 + angle1*textLine.getCharAscent(logIndex);
                    bottom1X = pos1 - angle1*textLine.getCharDescent(logIndex);
                }
                else {
                    top1X = bottom1X = pos1;
                }
            }
            {
                int logIndex = textLine.visualToLogical(caret);
                float angle2 = textLine.getCharAngle(logIndex);
                float pos2 = textLine.getCharLinePosition(logIndex);
                if (angle2 != 0) {
                    pos2 += angle2*textLine.getCharShift(logIndex);
                    top2X = pos2 + angle2*textLine.getCharAscent(logIndex);
                    bottom2X = pos2 - angle2*textLine.getCharDescent(logIndex);
                }
                else {
                    top2X = bottom2X = pos2;
                }
            }
        }

        float topX = (top1X + top2X) / 2;
        float bottomX = (bottom1X + bottom2X) / 2;

        if (info == null) {
            info = new float[2];
        }

        if (isVerticalLine) {
            info[1] = (float) ((topX - bottomX) / bounds.getWidth());
            info[0] = (float) (topX + (info[1]*bounds.getX()));
        }
        else {
            info[1] = (float) ((topX - bottomX) / bounds.getHeight());
            info[0] = (float) (bottomX + (info[1]*bounds.getMaxY()));
        }

        return info;
    }

    /**
     * Returns information about the caret corresponding to <code>hit</code>.
     * The first element of the array is the intersection of the caret with
     * the baseline, as a distance along the baseline. The second element
     * of the array is the inverse slope (run/rise) of the caret, measured
     * with respect to the baseline at that point.
     * <p>
     * This method is meant for informational use.  To display carets, it
     * is better to use <code>getCaretShapes</code>.
     * <p>
     *  返回与<code> hit </code>对应的插入符号的信息数组的第一个元素是插入符与基线的交集,作为沿着基线的距离数组的第二个元素是逆斜率(run /上升),相对于该点的基线测量
     * <p>
     * 此方法适用于信息使用要显示插入符号,最好使用<code> getCaretShapes </code>
     * 
     * 
     * @param hit a hit on a character in this <code>TextLayout</code>
     * @param bounds the bounds to which the caret info is constructed.
     *     The bounds is in baseline-relative coordinates.
     * @return a two-element array containing the position and slope of
     * the caret.  The returned caret info is in baseline-relative coordinates.
     * @see #getCaretShapes(int, Rectangle2D, TextLayout.CaretPolicy)
     * @see Font#getItalicAngle
     */
    public float[] getCaretInfo(TextHitInfo hit, Rectangle2D bounds) {
        ensureCache();
        checkTextHit(hit);

        return getCaretInfoTestInternal(hit, bounds);
    }

    // this version provides extra info in the float array
    // the first two values are as above
    // the next four values are the endpoints of the caret, as computed
    // using the hit character's offset (baseline + ssoffset) and
    // natural ascent and descent.
    // these  values are trimmed to the bounds where required to fit,
    // but otherwise independent of it.
    private float[] getCaretInfoTestInternal(TextHitInfo hit, Rectangle2D bounds) {
        ensureCache();
        checkTextHit(hit);

        float[] info = new float[6];

        // get old data first
        getCaretInfo(hitToCaret(hit), bounds, info);

        // then add our new data
        double iangle, ixbase, p1x, p1y, p2x, p2y;

        int charix = hit.getCharIndex();
        boolean lead = hit.isLeadingEdge();
        boolean ltr = textLine.isDirectionLTR();
        boolean horiz = !isVertical();

        if (charix == -1 || charix == characterCount) {
            // !!! note: want non-shifted, baseline ascent and descent here!
            // TextLine should return appropriate line metrics object for these values
            TextLineMetrics m = textLine.getMetrics();
            boolean low = ltr == (charix == -1);
            iangle = 0;
            if (horiz) {
                p1x = p2x = low ? 0 : m.advance;
                p1y = -m.ascent;
                p2y = m.descent;
            } else {
                p1y = p2y = low ? 0 : m.advance;
                p1x = m.descent;
                p2x = m.ascent;
            }
        } else {
            CoreMetrics thiscm = textLine.getCoreMetricsAt(charix);
            iangle = thiscm.italicAngle;
            ixbase = textLine.getCharLinePosition(charix, lead);
            if (thiscm.baselineIndex < 0) {
                // this is a graphic, no italics, use entire line height for caret
                TextLineMetrics m = textLine.getMetrics();
                if (horiz) {
                    p1x = p2x = ixbase;
                    if (thiscm.baselineIndex == GraphicAttribute.TOP_ALIGNMENT) {
                        p1y = -m.ascent;
                        p2y = p1y + thiscm.height;
                    } else {
                        p2y = m.descent;
                        p1y = p2y - thiscm.height;
                    }
                } else {
                    p1y = p2y = ixbase;
                    p1x = m.descent;
                    p2x = m.ascent;
                    // !!! top/bottom adjustment not implemented for vertical
                }
            } else {
                float bo = baselineOffsets[thiscm.baselineIndex];
                if (horiz) {
                    ixbase += iangle * thiscm.ssOffset;
                    p1x = ixbase + iangle * thiscm.ascent;
                    p2x = ixbase - iangle * thiscm.descent;
                    p1y = bo - thiscm.ascent;
                    p2y = bo + thiscm.descent;
                } else {
                    ixbase -= iangle * thiscm.ssOffset;
                    p1y = ixbase + iangle * thiscm.ascent;
                    p2y = ixbase - iangle * thiscm.descent;
                    p1x = bo + thiscm.ascent;
                    p2x = bo + thiscm.descent;
                }
            }
        }

        info[2] = (float)p1x;
        info[3] = (float)p1y;
        info[4] = (float)p2x;
        info[5] = (float)p2y;

        return info;
    }

    /**
     * Returns information about the caret corresponding to <code>hit</code>.
     * This method is a convenience overload of <code>getCaretInfo</code> and
     * uses the natural bounds of this <code>TextLayout</code>.
     * <p>
     *  返回有关<code> hit </code>对应的插入符的信息此方法是<code> getCaretInfo </code>的一个方便的重载,并使用此<code> TextLayout </code>
     * 。
     * 
     * 
     * @param hit a hit on a character in this <code>TextLayout</code>
     * @return the information about a caret corresponding to a hit.  The
     *     returned caret info is in baseline-relative coordinates.
     */
    public float[] getCaretInfo(TextHitInfo hit) {

        return getCaretInfo(hit, getNaturalBounds());
    }

    /**
     * Returns a caret index corresponding to <code>hit</code>.
     * Carets are numbered from left to right (top to bottom) starting from
     * zero. This always places carets next to the character hit, on the
     * indicated side of the character.
     * <p>
     *  返回与<code> hit </code>对应的脱字符索引</code>。从零开始,从左到右(从上到下)对光标进行编号。这总是在字符的指示侧将光标放在字符命中的旁边
     * 
     * 
     * @param hit a hit on a character in this <code>TextLayout</code>
     * @return a caret index corresponding to the specified hit.
     */
    private int hitToCaret(TextHitInfo hit) {

        int hitIndex = hit.getCharIndex();

        if (hitIndex < 0) {
            return textLine.isDirectionLTR() ? 0 : characterCount;
        } else if (hitIndex >= characterCount) {
            return textLine.isDirectionLTR() ? characterCount : 0;
        }

        int visIndex = textLine.logicalToVisual(hitIndex);

        if (hit.isLeadingEdge() != textLine.isCharLTR(hitIndex)) {
            ++visIndex;
        }

        return visIndex;
    }

    /**
     * Given a caret index, return a hit whose caret is at the index.
     * The hit is NOT guaranteed to be strong!!!
     *
     * <p>
     *  给定一个插入符索引,返回一个命中的插入符号在索引。命中不保证是强的！
     * 
     * 
     * @param caret a caret index.
     * @return a hit on this layout whose strong caret is at the requested
     * index.
     */
    private TextHitInfo caretToHit(int caret) {

        if (caret == 0 || caret == characterCount) {

            if ((caret == characterCount) == textLine.isDirectionLTR()) {
                return TextHitInfo.leading(characterCount);
            }
            else {
                return TextHitInfo.trailing(-1);
            }
        }
        else {

            int charIndex = textLine.visualToLogical(caret);
            boolean leading = textLine.isCharLTR(charIndex);

            return leading? TextHitInfo.leading(charIndex)
                            : TextHitInfo.trailing(charIndex);
        }
    }

    private boolean caretIsValid(int caret) {

        if (caret == characterCount || caret == 0) {
            return true;
        }

        int offset = textLine.visualToLogical(caret);

        if (!textLine.isCharLTR(offset)) {
            offset = textLine.visualToLogical(caret-1);
            if (textLine.isCharLTR(offset)) {
                return true;
            }
        }

        // At this point, the leading edge of the character
        // at offset is at the given caret.

        return textLine.caretAtOffsetIsValid(offset);
    }

    /**
     * Returns the hit for the next caret to the right (bottom); if there
     * is no such hit, returns <code>null</code>.
     * If the hit character index is out of bounds, an
     * {@link IllegalArgumentException} is thrown.
     * <p>
     * 向右(底部)返回下一个插入符的命中;如果没有这样的命中,返回<code> null </code>如果命中字符索引超出边界,则抛出{@link IllegalArgumentException}
     * 
     * 
     * @param hit a hit on a character in this layout
     * @return a hit whose caret appears at the next position to the
     * right (bottom) of the caret of the provided hit or <code>null</code>.
     */
    public TextHitInfo getNextRightHit(TextHitInfo hit) {
        ensureCache();
        checkTextHit(hit);

        int caret = hitToCaret(hit);

        if (caret == characterCount) {
            return null;
        }

        do {
            ++caret;
        } while (!caretIsValid(caret));

        return caretToHit(caret);
    }

    /**
     * Returns the hit for the next caret to the right (bottom); if no
     * such hit, returns <code>null</code>.  The hit is to the right of
     * the strong caret at the specified offset, as determined by the
     * specified policy.
     * The returned hit is the stronger of the two possible
     * hits, as determined by the specified policy.
     * <p>
     *  向右(底部)返回下一个插入符的命中;如果没有这样的命中,则返回<code> null </code>命中在指定偏移处的强插入符的右侧,由指定的策略确定返回的命中是两个可能的命中中的更强的,指定的策略。
     * 
     * 
     * @param offset an insertion offset in this <code>TextLayout</code>.
     * Cannot be less than 0 or greater than this <code>TextLayout</code>
     * object's character count.
     * @param policy the policy used to select the strong caret
     * @return a hit whose caret appears at the next position to the
     * right (bottom) of the caret of the provided hit, or <code>null</code>.
     */
    public TextHitInfo getNextRightHit(int offset, CaretPolicy policy) {

        if (offset < 0 || offset > characterCount) {
            throw new IllegalArgumentException("Offset out of bounds in TextLayout.getNextRightHit()");
        }

        if (policy == null) {
            throw new IllegalArgumentException("Null CaretPolicy passed to TextLayout.getNextRightHit()");
        }

        TextHitInfo hit1 = TextHitInfo.afterOffset(offset);
        TextHitInfo hit2 = hit1.getOtherHit();

        TextHitInfo nextHit = getNextRightHit(policy.getStrongCaret(hit1, hit2, this));

        if (nextHit != null) {
            TextHitInfo otherHit = getVisualOtherHit(nextHit);
            return policy.getStrongCaret(otherHit, nextHit, this);
        }
        else {
            return null;
        }
    }

    /**
     * Returns the hit for the next caret to the right (bottom); if no
     * such hit, returns <code>null</code>.  The hit is to the right of
     * the strong caret at the specified offset, as determined by the
     * default policy.
     * The returned hit is the stronger of the two possible
     * hits, as determined by the default policy.
     * <p>
     * 向右(底部)返回下一个插入符的命中;如果没有这样的命中,返回<code> null </code>命中位于指定偏移处的强插入符的右侧,由默认策略确定返回的命中是两个可能的命中中更强的,默认策略
     * 
     * 
     * @param offset an insertion offset in this <code>TextLayout</code>.
     * Cannot be less than 0 or greater than the <code>TextLayout</code>
     * object's character count.
     * @return a hit whose caret appears at the next position to the
     * right (bottom) of the caret of the provided hit, or <code>null</code>.
     */
    public TextHitInfo getNextRightHit(int offset) {

        return getNextRightHit(offset, DEFAULT_CARET_POLICY);
    }

    /**
     * Returns the hit for the next caret to the left (top); if no such
     * hit, returns <code>null</code>.
     * If the hit character index is out of bounds, an
     * <code>IllegalArgumentException</code> is thrown.
     * <p>
     *  向左(上)返回下一个插入符的命中;如果没有这样的命中,返回<code> null </code>如果命中字符索引超出边界,则抛出<code> IllegalArgumentException </code>
     * 。
     * 
     * 
     * @param hit a hit on a character in this <code>TextLayout</code>.
     * @return a hit whose caret appears at the next position to the
     * left (top) of the caret of the provided hit, or <code>null</code>.
     */
    public TextHitInfo getNextLeftHit(TextHitInfo hit) {
        ensureCache();
        checkTextHit(hit);

        int caret = hitToCaret(hit);

        if (caret == 0) {
            return null;
        }

        do {
            --caret;
        } while(!caretIsValid(caret));

        return caretToHit(caret);
    }

    /**
     * Returns the hit for the next caret to the left (top); if no
     * such hit, returns <code>null</code>.  The hit is to the left of
     * the strong caret at the specified offset, as determined by the
     * specified policy.
     * The returned hit is the stronger of the two possible
     * hits, as determined by the specified policy.
     * <p>
     * 向左(上)返回下一个插入符的命中;如果没有这样的命中,则返回<code> null </code>命中在指定偏移处的强插入符的左侧,由指定的策略确定返回的命中是两个可能的命中中更强的,指定的策略
     * 
     * 
     * @param offset an insertion offset in this <code>TextLayout</code>.
     * Cannot be less than 0 or greater than this <code>TextLayout</code>
     * object's character count.
     * @param policy the policy used to select the strong caret
     * @return a hit whose caret appears at the next position to the
     * left (top) of the caret of the provided hit, or <code>null</code>.
     */
    public TextHitInfo getNextLeftHit(int offset, CaretPolicy policy) {

        if (policy == null) {
            throw new IllegalArgumentException("Null CaretPolicy passed to TextLayout.getNextLeftHit()");
        }

        if (offset < 0 || offset > characterCount) {
            throw new IllegalArgumentException("Offset out of bounds in TextLayout.getNextLeftHit()");
        }

        TextHitInfo hit1 = TextHitInfo.afterOffset(offset);
        TextHitInfo hit2 = hit1.getOtherHit();

        TextHitInfo nextHit = getNextLeftHit(policy.getStrongCaret(hit1, hit2, this));

        if (nextHit != null) {
            TextHitInfo otherHit = getVisualOtherHit(nextHit);
            return policy.getStrongCaret(otherHit, nextHit, this);
        }
        else {
            return null;
        }
    }

    /**
     * Returns the hit for the next caret to the left (top); if no
     * such hit, returns <code>null</code>.  The hit is to the left of
     * the strong caret at the specified offset, as determined by the
     * default policy.
     * The returned hit is the stronger of the two possible
     * hits, as determined by the default policy.
     * <p>
     *  向左(上)返回下一个插入符的命中;如果没有这样的命中,返回<code> null </code>命中位于指定偏移处的强插入符号的左侧,由默认策略确定返回的命中是两个可能的命中中的更强,默认策略
     * 
     * 
     * @param offset an insertion offset in this <code>TextLayout</code>.
     * Cannot be less than 0 or greater than this <code>TextLayout</code>
     * object's character count.
     * @return a hit whose caret appears at the next position to the
     * left (top) of the caret of the provided hit, or <code>null</code>.
     */
    public TextHitInfo getNextLeftHit(int offset) {

        return getNextLeftHit(offset, DEFAULT_CARET_POLICY);
    }

    /**
     * Returns the hit on the opposite side of the specified hit's caret.
     * <p>
     *  返回指定的匹配的插入符号对面的匹配
     * 
     * 
     * @param hit the specified hit
     * @return a hit that is on the opposite side of the specified hit's
     *    caret.
     */
    public TextHitInfo getVisualOtherHit(TextHitInfo hit) {

        ensureCache();
        checkTextHit(hit);

        int hitCharIndex = hit.getCharIndex();

        int charIndex;
        boolean leading;

        if (hitCharIndex == -1 || hitCharIndex == characterCount) {

            int visIndex;
            if (textLine.isDirectionLTR() == (hitCharIndex == -1)) {
                visIndex = 0;
            }
            else {
                visIndex = characterCount-1;
            }

            charIndex = textLine.visualToLogical(visIndex);

            if (textLine.isDirectionLTR() == (hitCharIndex == -1)) {
                // at left end
                leading = textLine.isCharLTR(charIndex);
            }
            else {
                // at right end
                leading = !textLine.isCharLTR(charIndex);
            }
        }
        else {

            int visIndex = textLine.logicalToVisual(hitCharIndex);

            boolean movedToRight;
            if (textLine.isCharLTR(hitCharIndex) == hit.isLeadingEdge()) {
                --visIndex;
                movedToRight = false;
            }
            else {
                ++visIndex;
                movedToRight = true;
            }

            if (visIndex > -1 && visIndex < characterCount) {
                charIndex = textLine.visualToLogical(visIndex);
                leading = movedToRight == textLine.isCharLTR(charIndex);
            }
            else {
                charIndex =
                    (movedToRight == textLine.isDirectionLTR())? characterCount : -1;
                leading = charIndex == characterCount;
            }
        }

        return leading? TextHitInfo.leading(charIndex) :
                                TextHitInfo.trailing(charIndex);
    }

    private double[] getCaretPath(TextHitInfo hit, Rectangle2D bounds) {
        float[] info = getCaretInfo(hit, bounds);
        return new double[] { info[2], info[3], info[4], info[5] };
    }

    /**
     * Return an array of four floats corresponding the endpoints of the caret
     * x0, y0, x1, y1.
     *
     * This creates a line along the slope of the caret intersecting the
     * baseline at the caret
     * position, and extending from ascent above the baseline to descent below
     * it.
     * <p>
     *  返回对应于插入符x0,y0,x1,y1的端点的四个浮点数组
     * 
     * 这将创建一条沿着插入符号斜坡的线,该插入符号在插入符号位置与基线相交,并从基线上方的上升延伸到下方的下降
     * 
     */
    private double[] getCaretPath(int caret, Rectangle2D bounds,
                                  boolean clipToBounds) {

        float[] info = getCaretInfo(caret, bounds, null);

        double pos = info[0];
        double slope = info[1];

        double x0, y0, x1, y1;
        double x2 = -3141.59, y2 = -2.7; // values are there to make compiler happy

        double left = bounds.getX();
        double right = left + bounds.getWidth();
        double top = bounds.getY();
        double bottom = top + bounds.getHeight();

        boolean threePoints = false;

        if (isVerticalLine) {

            if (slope >= 0) {
                x0 = left;
                x1 = right;
            }
            else {
                x1 = left;
                x0 = right;
            }

            y0 = pos + x0 * slope;
            y1 = pos + x1 * slope;

            // y0 <= y1, always

            if (clipToBounds) {
                if (y0 < top) {
                    if (slope <= 0 || y1 <= top) {
                        y0 = y1 = top;
                    }
                    else {
                        threePoints = true;
                        y0 = top;
                        y2 = top;
                        x2 = x1 + (top-y1)/slope;
                        if (y1 > bottom) {
                            y1 = bottom;
                        }
                    }
                }
                else if (y1 > bottom) {
                    if (slope >= 0 || y0 >= bottom) {
                        y0 = y1 = bottom;
                    }
                    else {
                        threePoints = true;
                        y1 = bottom;
                        y2 = bottom;
                        x2 = x0 + (bottom-x1)/slope;
                    }
                }
            }

        }
        else {

            if (slope >= 0) {
                y0 = bottom;
                y1 = top;
            }
            else {
                y1 = bottom;
                y0 = top;
            }

            x0 = pos - y0 * slope;
            x1 = pos - y1 * slope;

            // x0 <= x1, always

            if (clipToBounds) {
                if (x0 < left) {
                    if (slope <= 0 || x1 <= left) {
                        x0 = x1 = left;
                    }
                    else {
                        threePoints = true;
                        x0 = left;
                        x2 = left;
                        y2 = y1 - (left-x1)/slope;
                        if (x1 > right) {
                            x1 = right;
                        }
                    }
                }
                else if (x1 > right) {
                    if (slope >= 0 || x0 >= right) {
                        x0 = x1 = right;
                    }
                    else {
                        threePoints = true;
                        x1 = right;
                        x2 = right;
                        y2 = y0 - (right-x0)/slope;
                    }
                }
            }
        }

        return threePoints?
                    new double[] { x0, y0, x2, y2, x1, y1 } :
                    new double[] { x0, y0, x1, y1 };
    }


    private static GeneralPath pathToShape(double[] path, boolean close, LayoutPathImpl lp) {
        GeneralPath result = new GeneralPath(GeneralPath.WIND_EVEN_ODD, path.length);
        result.moveTo((float)path[0], (float)path[1]);
        for (int i = 2; i < path.length; i += 2) {
            result.lineTo((float)path[i], (float)path[i+1]);
        }
        if (close) {
            result.closePath();
        }

        if (lp != null) {
            result = (GeneralPath)lp.mapShape(result);
        }
        return result;
    }

    /**
     * Returns a {@link Shape} representing the caret at the specified
     * hit inside the specified bounds.
     * <p>
     *  返回表示指定边界内指定点击处的插入符号的{@link Shape}
     * 
     * 
     * @param hit the hit at which to generate the caret
     * @param bounds the bounds of the <code>TextLayout</code> to use
     *    in generating the caret.  The bounds is in baseline-relative
     *    coordinates.
     * @return a <code>Shape</code> representing the caret.  The returned
     *    shape is in standard coordinates.
     */
    public Shape getCaretShape(TextHitInfo hit, Rectangle2D bounds) {
        ensureCache();
        checkTextHit(hit);

        if (bounds == null) {
            throw new IllegalArgumentException("Null Rectangle2D passed to TextLayout.getCaret()");
        }

        return pathToShape(getCaretPath(hit, bounds), false, textLine.getLayoutPath());
    }

    /**
     * Returns a <code>Shape</code> representing the caret at the specified
     * hit inside the natural bounds of this <code>TextLayout</code>.
     * <p>
     *  返回一个代表在<code> TextLayout </code>的自然边界内的指定点击处的插入符号的<code> Shape </code>
     * 
     * 
     * @param hit the hit at which to generate the caret
     * @return a <code>Shape</code> representing the caret.  The returned
     *     shape is in standard coordinates.
     */
    public Shape getCaretShape(TextHitInfo hit) {

        return getCaretShape(hit, getNaturalBounds());
    }

    /**
     * Return the "stronger" of the TextHitInfos.  The TextHitInfos
     * should be logical or visual counterparts.  They are not
     * checked for validity.
     * <p>
     *  返回TextHitInfos的"更强"TextHitInfos应该是逻辑或视觉对应它们不检查有效性
     * 
     */
    private final TextHitInfo getStrongHit(TextHitInfo hit1, TextHitInfo hit2) {

        // right now we're using the following rule for strong hits:
        // A hit on a character with a lower level
        // is stronger than one on a character with a higher level.
        // If this rule ties, the hit on the leading edge of a character wins.
        // If THIS rule ties, hit1 wins.  Both rules shouldn't tie, unless the
        // infos aren't counterparts of some sort.

        byte hit1Level = getCharacterLevel(hit1.getCharIndex());
        byte hit2Level = getCharacterLevel(hit2.getCharIndex());

        if (hit1Level == hit2Level) {
            if (hit2.isLeadingEdge() && !hit1.isLeadingEdge()) {
                return hit2;
            }
            else {
                return hit1;
            }
        }
        else {
            return (hit1Level < hit2Level)? hit1 : hit2;
        }
    }

    /**
     * Returns the level of the character at <code>index</code>.
     * Indices -1 and <code>characterCount</code> are assigned the base
     * level of this <code>TextLayout</code>.
     * <p>
     *  返回<code> index </code>索引-1和<code> characterCount </code>的字符等级分配给此<code> TextLayout </code>
     * 
     * 
     * @param index the index of the character from which to get the level
     * @return the level of the character at the specified index.
     */
    public byte getCharacterLevel(int index) {

        // hmm, allow indices at endpoints?  For now, yes.
        if (index < -1 || index > characterCount) {
            throw new IllegalArgumentException("Index is out of range in getCharacterLevel.");
        }

        ensureCache();
        if (index == -1 || index == characterCount) {
             return (byte) (textLine.isDirectionLTR()? 0 : 1);
        }

        return textLine.getCharLevel(index);
    }

    /**
     * Returns two paths corresponding to the strong and weak caret.
     * <p>
     *  返回对应于强和弱插入符的两个路径
     * 
     * 
     * @param offset an offset in this <code>TextLayout</code>
     * @param bounds the bounds to which to extend the carets.  The
     * bounds is in baseline-relative coordinates.
     * @param policy the specified <code>CaretPolicy</code>
     * @return an array of two paths.  Element zero is the strong
     * caret.  If there are two carets, element one is the weak caret,
     * otherwise it is <code>null</code>. The returned shapes
     * are in standard coordinates.
     */
    public Shape[] getCaretShapes(int offset, Rectangle2D bounds, CaretPolicy policy) {

        ensureCache();

        if (offset < 0 || offset > characterCount) {
            throw new IllegalArgumentException("Offset out of bounds in TextLayout.getCaretShapes()");
        }

        if (bounds == null) {
            throw new IllegalArgumentException("Null Rectangle2D passed to TextLayout.getCaretShapes()");
        }

        if (policy == null) {
            throw new IllegalArgumentException("Null CaretPolicy passed to TextLayout.getCaretShapes()");
        }

        Shape[] result = new Shape[2];

        TextHitInfo hit = TextHitInfo.afterOffset(offset);

        int hitCaret = hitToCaret(hit);

        LayoutPathImpl lp = textLine.getLayoutPath();
        Shape hitShape = pathToShape(getCaretPath(hit, bounds), false, lp);
        TextHitInfo otherHit = hit.getOtherHit();
        int otherCaret = hitToCaret(otherHit);

        if (hitCaret == otherCaret) {
            result[0] = hitShape;
        }
        else { // more than one caret
            Shape otherShape = pathToShape(getCaretPath(otherHit, bounds), false, lp);

            TextHitInfo strongHit = policy.getStrongCaret(hit, otherHit, this);
            boolean hitIsStrong = strongHit.equals(hit);

            if (hitIsStrong) {// then other is weak
                result[0] = hitShape;
                result[1] = otherShape;
            }
            else {
                result[0] = otherShape;
                result[1] = hitShape;
            }
        }

        return result;
    }

    /**
     * Returns two paths corresponding to the strong and weak caret.
     * This method is a convenience overload of <code>getCaretShapes</code>
     * that uses the default caret policy.
     * <p>
     * 返回对应于强和弱插入符的两个路径此方法是使用默认插入符策略的<code> getCaretShapes </code>的方便重载
     * 
     * 
     * @param offset an offset in this <code>TextLayout</code>
     * @param bounds the bounds to which to extend the carets.  This is
     *     in baseline-relative coordinates.
     * @return two paths corresponding to the strong and weak caret as
     *    defined by the <code>DEFAULT_CARET_POLICY</code>.  These are
     *    in standard coordinates.
     */
    public Shape[] getCaretShapes(int offset, Rectangle2D bounds) {
        // {sfb} parameter checking is done in overloaded version
        return getCaretShapes(offset, bounds, DEFAULT_CARET_POLICY);
    }

    /**
     * Returns two paths corresponding to the strong and weak caret.
     * This method is a convenience overload of <code>getCaretShapes</code>
     * that uses the default caret policy and this <code>TextLayout</code>
     * object's natural bounds.
     * <p>
     *  返回对应于强和弱插入符的两个路径此方法是使用默认插入符策略的<code> getCaretShapes </code>的方便重载,并且此<code> TextLayout </code>对象的自然边界
     * 。
     * 
     * 
     * @param offset an offset in this <code>TextLayout</code>
     * @return two paths corresponding to the strong and weak caret as
     *    defined by the <code>DEFAULT_CARET_POLICY</code>.  These are
     *    in standard coordinates.
     */
    public Shape[] getCaretShapes(int offset) {
        // {sfb} parameter checking is done in overloaded version
        return getCaretShapes(offset, getNaturalBounds(), DEFAULT_CARET_POLICY);
    }

    // A utility to return a path enclosing the given path
    // Path0 must be left or top of path1
    // {jbr} no assumptions about size of path0, path1 anymore.
    private GeneralPath boundingShape(double[] path0, double[] path1) {

        // Really, we want the path to be a convex hull around all of the
        // points in path0 and path1.  But we can get by with less than
        // that.  We do need to prevent the two segments which
        // join path0 to path1 from crossing each other.  So, if we
        // traverse path0 from top to bottom, we'll traverse path1 from
        // bottom to top (and vice versa).

        GeneralPath result = pathToShape(path0, false, null);

        boolean sameDirection;

        if (isVerticalLine) {
            sameDirection = (path0[1] > path0[path0.length-1]) ==
                            (path1[1] > path1[path1.length-1]);
        }
        else {
            sameDirection = (path0[0] > path0[path0.length-2]) ==
                            (path1[0] > path1[path1.length-2]);
        }

        int start;
        int limit;
        int increment;

        if (sameDirection) {
            start = path1.length-2;
            limit = -2;
            increment = -2;
        }
        else {
            start = 0;
            limit = path1.length;
            increment = 2;
        }

        for (int i = start; i != limit; i += increment) {
            result.lineTo((float)path1[i], (float)path1[i+1]);
        }

        result.closePath();

        return result;
    }

    // A utility to convert a pair of carets into a bounding path
    // {jbr} Shape is never outside of bounds.
    private GeneralPath caretBoundingShape(int caret0,
                                           int caret1,
                                           Rectangle2D bounds) {

        if (caret0 > caret1) {
            int temp = caret0;
            caret0 = caret1;
            caret1 = temp;
        }

        return boundingShape(getCaretPath(caret0, bounds, true),
                             getCaretPath(caret1, bounds, true));
    }

    /*
     * A utility to return the path bounding the area to the left (top) of the
     * layout.
     * Shape is never outside of bounds.
     * <p>
     *  将布局形状的左侧(顶部)返回到区域边界的路径的实用程序不会超出边界
     * 
     */
    private GeneralPath leftShape(Rectangle2D bounds) {

        double[] path0;
        if (isVerticalLine) {
            path0 = new double[] { bounds.getX(), bounds.getY(),
                                       bounds.getX() + bounds.getWidth(),
                                       bounds.getY() };
        } else {
            path0 = new double[] { bounds.getX(),
                                       bounds.getY() + bounds.getHeight(),
                                       bounds.getX(), bounds.getY() };
        }

        double[] path1 = getCaretPath(0, bounds, true);

        return boundingShape(path0, path1);
    }

    /*
     * A utility to return the path bounding the area to the right (bottom) of
     * the layout.
     * <p>
     *  用于将界定区域的路径返回到布局右侧(底部)的实用程序
     * 
     */
    private GeneralPath rightShape(Rectangle2D bounds) {
        double[] path1;
        if (isVerticalLine) {
            path1 = new double[] {
                bounds.getX(),
                bounds.getY() + bounds.getHeight(),
                bounds.getX() + bounds.getWidth(),
                bounds.getY() + bounds.getHeight()
            };
        } else {
            path1 = new double[] {
                bounds.getX() + bounds.getWidth(),
                bounds.getY() + bounds.getHeight(),
                bounds.getX() + bounds.getWidth(),
                bounds.getY()
            };
        }

        double[] path0 = getCaretPath(characterCount, bounds, true);

        return boundingShape(path0, path1);
    }

    /**
     * Returns the logical ranges of text corresponding to a visual selection.
     * <p>
     *  返回与视觉选择对应的文本的逻辑范围
     * 
     * 
     * @param firstEndpoint an endpoint of the visual range
     * @param secondEndpoint the other endpoint of the visual range.
     * This endpoint can be less than <code>firstEndpoint</code>.
     * @return an array of integers representing start/limit pairs for the
     * selected ranges.
     * @see #getVisualHighlightShape(TextHitInfo, TextHitInfo, Rectangle2D)
     */
    public int[] getLogicalRangesForVisualSelection(TextHitInfo firstEndpoint,
                                                    TextHitInfo secondEndpoint) {
        ensureCache();

        checkTextHit(firstEndpoint);
        checkTextHit(secondEndpoint);

        // !!! probably want to optimize for all LTR text

        boolean[] included = new boolean[characterCount];

        int startIndex = hitToCaret(firstEndpoint);
        int limitIndex = hitToCaret(secondEndpoint);

        if (startIndex > limitIndex) {
            int t = startIndex;
            startIndex = limitIndex;
            limitIndex = t;
        }

        /*
         * now we have the visual indexes of the glyphs at the start and limit
         * of the selection range walk through runs marking characters that
         * were included in the visual range there is probably a more efficient
         * way to do this, but this ought to work, so hey
         * <p>
         * 现在我们有视觉索引的字形在选择范围的开始和限制通过运行标记包含在视觉范围内的字符可能有一个更有效的方式做到这一点,但这应该工作,所以嘿
         * 
         */

        if (startIndex < limitIndex) {
            int visIndex = startIndex;
            while (visIndex < limitIndex) {
                included[textLine.visualToLogical(visIndex)] = true;
                ++visIndex;
            }
        }

        /*
         * count how many runs we have, ought to be one or two, but perhaps
         * things are especially weird
         * <p>
         *  计算我们有多少次运行,应该是一两次,但也许事情特别奇怪
         * 
         */
        int count = 0;
        boolean inrun = false;
        for (int i = 0; i < characterCount; i++) {
            if (included[i] != inrun) {
                inrun = !inrun;
                if (inrun) {
                    count++;
                }
            }
        }

        int[] ranges = new int[count * 2];
        count = 0;
        inrun = false;
        for (int i = 0; i < characterCount; i++) {
            if (included[i] != inrun) {
                ranges[count++] = i;
                inrun = !inrun;
            }
        }
        if (inrun) {
            ranges[count++] = characterCount;
        }

        return ranges;
    }

    /**
     * Returns a path enclosing the visual selection in the specified range,
     * extended to <code>bounds</code>.
     * <p>
     * If the selection includes the leftmost (topmost) position, the selection
     * is extended to the left (top) of <code>bounds</code>.  If the
     * selection includes the rightmost (bottommost) position, the selection
     * is extended to the right (bottom) of the bounds.  The height
     * (width on vertical lines) of the selection is always extended to
     * <code>bounds</code>.
     * <p>
     * Although the selection is always contiguous, the logically selected
     * text can be discontiguous on lines with mixed-direction text.  The
     * logical ranges of text selected can be retrieved using
     * <code>getLogicalRangesForVisualSelection</code>.  For example,
     * consider the text 'ABCdef' where capital letters indicate
     * right-to-left text, rendered on a right-to-left line, with a visual
     * selection from 0L (the leading edge of 'A') to 3T (the trailing edge
     * of 'd').  The text appears as follows, with bold underlined areas
     * representing the selection:
     * <br><pre>
     *    d<u><b>efCBA  </b></u>
     * </pre>
     * The logical selection ranges are 0-3, 4-6 (ABC, ef) because the
     * visually contiguous text is logically discontiguous.  Also note that
     * since the rightmost position on the layout (to the right of 'A') is
     * selected, the selection is extended to the right of the bounds.
     * <p>
     *  返回在指定范围内包含可视选择的路径,扩展到<code> bounds </code>
     * <p>
     *  如果选择包括最左(最高)位置,则选择扩展到<code> bounds的左(上)</code>如果选择包括最右(最低)位置,则选择扩展到右)的边界选择的高度(垂直线上的宽度)总是扩展到<code> bo
     * unds </code>。
     * <p>
     * 虽然选择总是连续的,但逻辑上选择的文本在具有混合方向文本的行上可以是不连续的。
     * 可以使用<code> getLogicalRangesForVisualSelection </code>来检索所选文本的逻辑范围。
     * 例如,考虑文本"ABCdef"字母表示从右到左的线上呈现的从左到右的文本,具有从0L('A'的前边缘)到3T('d'的后边缘)的视觉选择。
     * 文本显示如下,带有加粗下划线的区域表示选择：<br> <pre> d <u> <b> efCBA </b> </u>。
     * </pre>
     * 逻辑选择范围是0-3,4-6(ABC,ef),因为视觉上连续的文本在逻辑上是不连续的。还要注意,由于布局上最右边的位置("A"的右边)被选择,所以选择是扩展到边界的右边
     * 
     * 
     * @param firstEndpoint one end of the visual selection
     * @param secondEndpoint the other end of the visual selection
     * @param bounds the bounding rectangle to which to extend the selection.
     *     This is in baseline-relative coordinates.
     * @return a <code>Shape</code> enclosing the selection.  This is in
     *     standard coordinates.
     * @see #getLogicalRangesForVisualSelection(TextHitInfo, TextHitInfo)
     * @see #getLogicalHighlightShape(int, int, Rectangle2D)
     */
    public Shape getVisualHighlightShape(TextHitInfo firstEndpoint,
                                        TextHitInfo secondEndpoint,
                                        Rectangle2D bounds)
    {
        ensureCache();

        checkTextHit(firstEndpoint);
        checkTextHit(secondEndpoint);

        if(bounds == null) {
                throw new IllegalArgumentException("Null Rectangle2D passed to TextLayout.getVisualHighlightShape()");
        }

        GeneralPath result = new GeneralPath(GeneralPath.WIND_EVEN_ODD);

        int firstCaret = hitToCaret(firstEndpoint);
        int secondCaret = hitToCaret(secondEndpoint);

        result.append(caretBoundingShape(firstCaret, secondCaret, bounds),
                      false);

        if (firstCaret == 0 || secondCaret == 0) {
            GeneralPath ls = leftShape(bounds);
            if (!ls.getBounds().isEmpty())
                result.append(ls, false);
        }

        if (firstCaret == characterCount || secondCaret == characterCount) {
            GeneralPath rs = rightShape(bounds);
            if (!rs.getBounds().isEmpty()) {
                result.append(rs, false);
            }
        }

        LayoutPathImpl lp = textLine.getLayoutPath();
        if (lp != null) {
            result = (GeneralPath)lp.mapShape(result); // dlf cast safe?
        }

        return  result;
    }

    /**
     * Returns a <code>Shape</code> enclosing the visual selection in the
     * specified range, extended to the bounds.  This method is a
     * convenience overload of <code>getVisualHighlightShape</code> that
     * uses the natural bounds of this <code>TextLayout</code>.
     * <p>
     *  返回一个<code> Shape </code>,在指定范围内包含可视化选择,扩展到bounds这个方法是一个方便的重载<code> getVisualHighlightShape </code>,它
     * 使用了<code> TextLayout < / code>。
     * 
     * 
     * @param firstEndpoint one end of the visual selection
     * @param secondEndpoint the other end of the visual selection
     * @return a <code>Shape</code> enclosing the selection.  This is
     *     in standard coordinates.
     */
    public Shape getVisualHighlightShape(TextHitInfo firstEndpoint,
                                             TextHitInfo secondEndpoint) {
        return getVisualHighlightShape(firstEndpoint, secondEndpoint, getNaturalBounds());
    }

    /**
     * Returns a <code>Shape</code> enclosing the logical selection in the
     * specified range, extended to the specified <code>bounds</code>.
     * <p>
     * If the selection range includes the first logical character, the
     * selection is extended to the portion of <code>bounds</code> before
     * the start of this <code>TextLayout</code>.  If the range includes
     * the last logical character, the selection is extended to the portion
     * of <code>bounds</code> after the end of this <code>TextLayout</code>.
     * The height (width on vertical lines) of the selection is always
     * extended to <code>bounds</code>.
     * <p>
     * The selection can be discontiguous on lines with mixed-direction text.
     * Only those characters in the logical range between start and limit
     * appear selected.  For example, consider the text 'ABCdef' where capital
     * letters indicate right-to-left text, rendered on a right-to-left line,
     * with a logical selection from 0 to 4 ('ABCd').  The text appears as
     * follows, with bold standing in for the selection, and underlining for
     * the extension:
     * <br><pre>
     *    <u><b>d</b></u>ef<u><b>CBA  </b></u>
     * </pre>
     * The selection is discontiguous because the selected characters are
     * visually discontiguous. Also note that since the range includes the
     * first logical character (A), the selection is extended to the portion
     * of the <code>bounds</code> before the start of the layout, which in
     * this case (a right-to-left line) is the right portion of the
     * <code>bounds</code>.
     * <p>
     *  返回包含指定范围内的逻辑选择的<code> Shape </code>,扩展到指定的<code> bounds </code>
     * <p>
     * 如果选择范围包括第一逻辑字符,则在该<code> TextLayout </code>开始之前将选择扩展到<code> bounds </code>的部分。
     * 如果范围包括最后一个逻辑字符,在<code> TextLayout </code>结束后扩展到<code> bounds </code>的部分。
     * 选择的高度(垂直线上的宽度)总是扩展到<code> bounds </code>。
     * <p>
     * 在具有混合方向文本的行上,选择可以是不连续的只有在开始和限制之间的逻辑范围中的那些字符出现选择例如,考虑文本"ABCdef",其中大写字母表示从右到左文本,左边的行,逻辑选择从0到4('ABCd')文本
     * 显示如下,用粗体表示选择,并为扩展加下划线：<br> <pre> <u> <b> d </b> ef <u> <b> CBA </b> </u>。
     * </pre>
     * 选择是不连续的,因为所选字符在视觉上不连续。
     * 还要注意,由于范围包括第一个逻辑字符(A),所以选择扩展到布局开始之前的<code> bounds </code>部分,在这种情况下(从右到左的行)是<code> bounds </code>的右边部分
     * 。
     * 选择是不连续的,因为所选字符在视觉上不连续。
     * 
     * 
     * @param firstEndpoint an endpoint in the range of characters to select
     * @param secondEndpoint the other endpoint of the range of characters
     * to select. Can be less than <code>firstEndpoint</code>.  The range
     * includes the character at min(firstEndpoint, secondEndpoint), but
     * excludes max(firstEndpoint, secondEndpoint).
     * @param bounds the bounding rectangle to which to extend the selection.
     *     This is in baseline-relative coordinates.
     * @return an area enclosing the selection.  This is in standard
     *     coordinates.
     * @see #getVisualHighlightShape(TextHitInfo, TextHitInfo, Rectangle2D)
     */
    public Shape getLogicalHighlightShape(int firstEndpoint,
                                         int secondEndpoint,
                                         Rectangle2D bounds) {
        if (bounds == null) {
            throw new IllegalArgumentException("Null Rectangle2D passed to TextLayout.getLogicalHighlightShape()");
        }

        ensureCache();

        if (firstEndpoint > secondEndpoint) {
            int t = firstEndpoint;
            firstEndpoint = secondEndpoint;
            secondEndpoint = t;
        }

        if(firstEndpoint < 0 || secondEndpoint > characterCount) {
            throw new IllegalArgumentException("Range is invalid in TextLayout.getLogicalHighlightShape()");
        }

        GeneralPath result = new GeneralPath(GeneralPath.WIND_EVEN_ODD);

        int[] carets = new int[10]; // would this ever not handle all cases?
        int count = 0;

        if (firstEndpoint < secondEndpoint) {
            int logIndex = firstEndpoint;
            do {
                carets[count++] = hitToCaret(TextHitInfo.leading(logIndex));
                boolean ltr = textLine.isCharLTR(logIndex);

                do {
                    logIndex++;
                } while (logIndex < secondEndpoint && textLine.isCharLTR(logIndex) == ltr);

                int hitCh = logIndex;
                carets[count++] = hitToCaret(TextHitInfo.trailing(hitCh - 1));

                if (count == carets.length) {
                    int[] temp = new int[carets.length + 10];
                    System.arraycopy(carets, 0, temp, 0, count);
                    carets = temp;
                }
            } while (logIndex < secondEndpoint);
        }
        else {
            count = 2;
            carets[0] = carets[1] = hitToCaret(TextHitInfo.leading(firstEndpoint));
        }

        // now create paths for pairs of carets

        for (int i = 0; i < count; i += 2) {
            result.append(caretBoundingShape(carets[i], carets[i+1], bounds),
                          false);
        }

        if (firstEndpoint != secondEndpoint) {
            if ((textLine.isDirectionLTR() && firstEndpoint == 0) || (!textLine.isDirectionLTR() &&
                                                                      secondEndpoint == characterCount)) {
                GeneralPath ls = leftShape(bounds);
                if (!ls.getBounds().isEmpty()) {
                    result.append(ls, false);
                }
            }

            if ((textLine.isDirectionLTR() && secondEndpoint == characterCount) ||
                (!textLine.isDirectionLTR() && firstEndpoint == 0)) {

                GeneralPath rs = rightShape(bounds);
                if (!rs.getBounds().isEmpty()) {
                    result.append(rs, false);
                }
            }
        }

        LayoutPathImpl lp = textLine.getLayoutPath();
        if (lp != null) {
            result = (GeneralPath)lp.mapShape(result); // dlf cast safe?
        }
        return result;
    }

    /**
     * Returns a <code>Shape</code> enclosing the logical selection in the
     * specified range, extended to the natural bounds of this
     * <code>TextLayout</code>.  This method is a convenience overload of
     * <code>getLogicalHighlightShape</code> that uses the natural bounds of
     * this <code>TextLayout</code>.
     * <p>
     *  返回一个包含指定范围内的逻辑选择的<code> Shape </code>,扩展到这个<code> TextLayout </code>的自然边界这个方法是一个方便的重载<code> getLogic
     * alHighlightShape </code>使用这个<code> TextLayout </code>的自然界限。
     * 
     * 
     * @param firstEndpoint an endpoint in the range of characters to select
     * @param secondEndpoint the other endpoint of the range of characters
     * to select. Can be less than <code>firstEndpoint</code>.  The range
     * includes the character at min(firstEndpoint, secondEndpoint), but
     * excludes max(firstEndpoint, secondEndpoint).
     * @return a <code>Shape</code> enclosing the selection.  This is in
     *     standard coordinates.
     */
    public Shape getLogicalHighlightShape(int firstEndpoint, int secondEndpoint) {

        return getLogicalHighlightShape(firstEndpoint, secondEndpoint, getNaturalBounds());
    }

    /**
     * Returns the black box bounds of the characters in the specified range.
     * The black box bounds is an area consisting of the union of the bounding
     * boxes of all the glyphs corresponding to the characters between start
     * and limit.  This area can be disjoint.
     * <p>
     * 返回指定范围内的字符的黑盒边界黑盒边界是由所有字形的边界框的并集​​组成的区域,对应于开始和限制之间的字符。该区域可以是不相交的
     * 
     * 
     * @param firstEndpoint one end of the character range
     * @param secondEndpoint the other end of the character range.  Can be
     * less than <code>firstEndpoint</code>.
     * @return a <code>Shape</code> enclosing the black box bounds.  This is
     *     in standard coordinates.
     */
    public Shape getBlackBoxBounds(int firstEndpoint, int secondEndpoint) {
        ensureCache();

        if (firstEndpoint > secondEndpoint) {
            int t = firstEndpoint;
            firstEndpoint = secondEndpoint;
            secondEndpoint = t;
        }

        if (firstEndpoint < 0 || secondEndpoint > characterCount) {
            throw new IllegalArgumentException("Invalid range passed to TextLayout.getBlackBoxBounds()");
        }

        /*
         * return an area that consists of the bounding boxes of all the
         * characters from firstEndpoint to limit
         * <p>
         *  返回由firstEndpoint到limit的所有字符的边界框组成的区域
         * 
         */

        GeneralPath result = new GeneralPath(GeneralPath.WIND_NON_ZERO);

        if (firstEndpoint < characterCount) {
            for (int logIndex = firstEndpoint;
                        logIndex < secondEndpoint;
                        logIndex++) {

                Rectangle2D r = textLine.getCharBounds(logIndex);
                if (!r.isEmpty()) {
                    result.append(r, false);
                }
            }
        }

        if (dx != 0 || dy != 0) {
            AffineTransform tx = AffineTransform.getTranslateInstance(dx, dy);
            result = (GeneralPath)tx.createTransformedShape(result);
        }
        LayoutPathImpl lp = textLine.getLayoutPath();
        if (lp != null) {
            result = (GeneralPath)lp.mapShape(result);
        }

        //return new Highlight(result, false);
        return result;
    }

    /**
     * Returns the distance from the point (x,&nbsp;y) to the caret along
     * the line direction defined in <code>caretInfo</code>.  Distance is
     * negative if the point is to the left of the caret on a horizontal
     * line, or above the caret on a vertical line.
     * Utility for use by hitTestChar.
     * <p>
     *  返回沿着<code> caretInfo </code>中定义的线方向从点(x,y)到插入符号的距离。
     * 如果点在水平线或上方的插入符号的左侧,则距离为负在垂直线上的插入符号hitTestChar使用的实用程序。
     * 
     */
    private float caretToPointDistance(float[] caretInfo, float x, float y) {
        // distanceOffBaseline is negative if you're 'above' baseline

        float lineDistance = isVerticalLine? y : x;
        float distanceOffBaseline = isVerticalLine? -x : y;

        return lineDistance - caretInfo[0] +
            (distanceOffBaseline*caretInfo[1]);
    }

    /**
     * Returns a <code>TextHitInfo</code> corresponding to the
     * specified point.
     * Coordinates outside the bounds of the <code>TextLayout</code>
     * map to hits on the leading edge of the first logical character,
     * or the trailing edge of the last logical character, as appropriate,
     * regardless of the position of that character in the line.  Only the
     * direction along the baseline is used to make this evaluation.
     * <p>
     * 返回对应于指定点的<code> TextHitInfo </code> <code> TextLayout </code>映射的边界之外的坐标位于第一个逻辑字符的前沿或最后一个逻辑字符的后沿字符,无论该
     * 字符在行中的位置。
     * 只有沿着基线的方向用于进行此评估。
     * 
     * 
     * @param x the x offset from the origin of this
     *     <code>TextLayout</code>.  This is in standard coordinates.
     * @param y the y offset from the origin of this
     *     <code>TextLayout</code>.  This is in standard coordinates.
     * @param bounds the bounds of the <code>TextLayout</code>.  This
     *     is in baseline-relative coordinates.
     * @return a hit describing the character and edge (leading or trailing)
     *     under the specified point.
     */
    public TextHitInfo hitTestChar(float x, float y, Rectangle2D bounds) {
        // check boundary conditions

        LayoutPathImpl lp = textLine.getLayoutPath();
        boolean prev = false;
        if (lp != null) {
            Point2D.Float pt = new Point2D.Float(x, y);
            prev = lp.pointToPath(pt, pt);
            x = pt.x;
            y = pt.y;
        }

        if (isVertical()) {
            if (y < bounds.getMinY()) {
                return TextHitInfo.leading(0);
            } else if (y >= bounds.getMaxY()) {
                return TextHitInfo.trailing(characterCount-1);
            }
        } else {
            if (x < bounds.getMinX()) {
                return isLeftToRight() ? TextHitInfo.leading(0) : TextHitInfo.trailing(characterCount-1);
            } else if (x >= bounds.getMaxX()) {
                return isLeftToRight() ? TextHitInfo.trailing(characterCount-1) : TextHitInfo.leading(0);
            }
        }

        // revised hit test
        // the original seems too complex and fails miserably with italic offsets
        // the natural tendency is to move towards the character you want to hit
        // so we'll just measure distance to the center of each character's visual
        // bounds, pick the closest one, then see which side of the character's
        // center line (italic) the point is on.
        // this tends to make it easier to hit narrow characters, which can be a
        // bit odd if you're visually over an adjacent wide character. this makes
        // a difference with bidi, so perhaps i need to revisit this yet again.

        double distance = Double.MAX_VALUE;
        int index = 0;
        int trail = -1;
        CoreMetrics lcm = null;
        float icx = 0, icy = 0, ia = 0, cy = 0, dya = 0, ydsq = 0;

        for (int i = 0; i < characterCount; ++i) {
            if (!textLine.caretAtOffsetIsValid(i)) {
                continue;
            }
            if (trail == -1) {
                trail = i;
            }
            CoreMetrics cm = textLine.getCoreMetricsAt(i);
            if (cm != lcm) {
                lcm = cm;
                // just work around baseline mess for now
                if (cm.baselineIndex == GraphicAttribute.TOP_ALIGNMENT) {
                    cy = -(textLine.getMetrics().ascent - cm.ascent) + cm.ssOffset;
                } else if (cm.baselineIndex == GraphicAttribute.BOTTOM_ALIGNMENT) {
                    cy = textLine.getMetrics().descent - cm.descent + cm.ssOffset;
                } else {
                    cy = cm.effectiveBaselineOffset(baselineOffsets) + cm.ssOffset;
                }
                float dy = (cm.descent - cm.ascent) / 2 - cy;
                dya = dy * cm.italicAngle;
                cy += dy;
                ydsq = (cy - y)*(cy - y);
            }
            float cx = textLine.getCharXPosition(i);
            float ca = textLine.getCharAdvance(i);
            float dx = ca / 2;
            cx += dx - dya;

            // proximity in x (along baseline) is two times as important as proximity in y
            double nd = Math.sqrt(4*(cx - x)*(cx - x) + ydsq);
            if (nd < distance) {
                distance = nd;
                index = i;
                trail = -1;
                icx = cx; icy = cy; ia = cm.italicAngle;
            }
        }
        boolean left = x < icx - (y - icy) * ia;
        boolean leading = textLine.isCharLTR(index) == left;
        if (trail == -1) {
            trail = characterCount;
        }
        TextHitInfo result = leading ? TextHitInfo.leading(index) :
            TextHitInfo.trailing(trail-1);
        return result;
    }

    /**
     * Returns a <code>TextHitInfo</code> corresponding to the
     * specified point.  This method is a convenience overload of
     * <code>hitTestChar</code> that uses the natural bounds of this
     * <code>TextLayout</code>.
     * <p>
     *  返回对应于指定点的<code> TextHitInfo </code>此方法是<code> hitTestChar </code>的一个方便的重载,它使用此<code> TextLayout </code>
     * 。
     * 
     * 
     * @param x the x offset from the origin of this
     *     <code>TextLayout</code>.  This is in standard coordinates.
     * @param y the y offset from the origin of this
     *     <code>TextLayout</code>.  This is in standard coordinates.
     * @return a hit describing the character and edge (leading or trailing)
     * under the specified point.
     */
    public TextHitInfo hitTestChar(float x, float y) {

        return hitTestChar(x, y, getNaturalBounds());
    }

    /**
     * Returns the hash code of this <code>TextLayout</code>.
     * <p>
     *  返回此<code> TextLayout </code>的哈希码
     * 
     * 
     * @return the hash code of this <code>TextLayout</code>.
     */
    public int hashCode() {
        if (hashCodeCache == 0) {
            ensureCache();
            hashCodeCache = textLine.hashCode();
        }
        return hashCodeCache;
    }

    /**
     * Returns <code>true</code> if the specified <code>Object</code> is a
     * <code>TextLayout</code> object and if the specified <code>Object</code>
     * equals this <code>TextLayout</code>.
     * <p>
     * 如果指定的<code> Object </code>是<code> TextLayout </code>对象,并且指定的<code> Object </code>等于此<code> TextLayout
     *  < / code>。
     * 
     * 
     * @param obj an <code>Object</code> to test for equality
     * @return <code>true</code> if the specified <code>Object</code>
     *      equals this <code>TextLayout</code>; <code>false</code>
     *      otherwise.
     */
    public boolean equals(Object obj) {
        return (obj instanceof TextLayout) && equals((TextLayout)obj);
    }

    /**
     * Returns <code>true</code> if the two layouts are equal.
     * Two layouts are equal if they contain equal glyphvectors in the same order.
     * <p>
     *  如果两个布局相等,则返回<code> true </code>如果两个布局包含相同顺序的相等的glyphvectors,则它们相等
     * 
     * 
     * @param rhs the <code>TextLayout</code> to compare to this
     *       <code>TextLayout</code>
     * @return <code>true</code> if the specified <code>TextLayout</code>
     *      equals this <code>TextLayout</code>.
     *
     */
    public boolean equals(TextLayout rhs) {

        if (rhs == null) {
            return false;
        }
        if (rhs == this) {
            return true;
        }

        ensureCache();
        return textLine.equals(rhs.textLine);
    }

    /**
     * Returns debugging information for this <code>TextLayout</code>.
     * <p>
     *  返回此<code> TextLayout </code>的调试信息
     * 
     * 
     * @return the <code>textLine</code> of this <code>TextLayout</code>
     *        as a <code>String</code>.
     */
    public String toString() {
        ensureCache();
        return textLine.toString();
     }

    /**
     * Renders this <code>TextLayout</code> at the specified location in
     * the specified {@link java.awt.Graphics2D Graphics2D} context.
     * The origin of the layout is placed at x,&nbsp;y.  Rendering may touch
     * any point within <code>getBounds()</code> of this position.  This
     * leaves the <code>g2</code> unchanged.  Text is rendered along the
     * baseline path.
     * <p>
     *  在指定的{@link javaawtGraphics2D Graphics2D}上下文中的指定位置呈现此<code> TextLayout </code>布局的原点放在x,y渲染可以触及<code> 
     * getBounds / code>这使得<code> g2 </code>不变的文本沿着基线路径呈现。
     * 
     * 
     * @param g2 the <code>Graphics2D</code> context into which to render
     *         the layout
     * @param x the X coordinate of the origin of this <code>TextLayout</code>
     * @param y the Y coordinate of the origin of this <code>TextLayout</code>
     * @see #getBounds()
     */
    public void draw(Graphics2D g2, float x, float y) {

        if (g2 == null) {
            throw new IllegalArgumentException("Null Graphics2D passed to TextLayout.draw()");
        }

        textLine.draw(g2, x - dx, y - dy);
    }

    /**
     * Package-only method for testing ONLY.  Please don't abuse.
     * <p>
     *  仅用于测试的仅包装方法请不要滥用
     * 
     */
    TextLine getTextLineForTesting() {

        return textLine;
    }

    /**
     *
     * Return the index of the first character with a different baseline from the
     * character at start, or limit if all characters between start and limit have
     * the same baseline.
     * <p>
     * 返回与开始时字符不同的基线的第一个字符的索引,或者如果start和limit之间的所有字符具有相同的基线,则返回limit
     * 
     */
    private static int sameBaselineUpTo(Font font, char[] text,
                                        int start, int limit) {
        // current implementation doesn't support multiple baselines
        return limit;
        /*
        byte bl = font.getBaselineFor(text[start++]);
        while (start < limit && font.getBaselineFor(text[start]) == bl) {
            ++start;
        }
        return start;
        /* <p>
        /*  byte bl = fontgetBaselineFor(text [start ++]); while(start <limit && fontgetBaselineFor(text [start])== bl){++ start; } return start;。
        /* 
        */
    }

    static byte getBaselineFromGraphic(GraphicAttribute graphic) {

        byte alignment = (byte) graphic.getAlignment();

        if (alignment == GraphicAttribute.BOTTOM_ALIGNMENT ||
                alignment == GraphicAttribute.TOP_ALIGNMENT) {

            return (byte)GraphicAttribute.ROMAN_BASELINE;
        }
        else {
            return alignment;
        }
    }

    /**
     * Returns a <code>Shape</code> representing the outline of this
     * <code>TextLayout</code>.
     * <p>
     *  返回代表此<code> TextLayout </code>大纲的<code> Shape </code>
     * 
     * 
     * @param tx an optional {@link AffineTransform} to apply to the
     *     outline of this <code>TextLayout</code>.
     * @return a <code>Shape</code> that is the outline of this
     *     <code>TextLayout</code>.  This is in standard coordinates.
     */
    public Shape getOutline(AffineTransform tx) {
        ensureCache();
        Shape result = textLine.getOutline(tx);
        LayoutPathImpl lp = textLine.getLayoutPath();
        if (lp != null) {
            result = lp.mapShape(result);
        }
        return result;
    }

    /**
     * Return the LayoutPath, or null if the layout path is the
     * default path (x maps to advance, y maps to offset).
     * <p>
     *  返回LayoutPath,如果布局路径是默认路径,则返回null(x映射为advance,y映射为offset)
     * 
     * 
     * @return the layout path
     * @since 1.6
     */
    public LayoutPath getLayoutPath() {
        return textLine.getLayoutPath();
    }

   /**
     * Convert a hit to a point in standard coordinates.  The point is
     * on the baseline of the character at the leading or trailing
     * edge of the character, as appropriate.  If the path is
     * broken at the side of the character represented by the hit, the
     * point will be adjacent to the character.
     * <p>
     *  将点击转换为标准坐标中的点该点在字符的前边缘或后边缘处的字符的基线上,如果适当的话。如果路径在由点击表示的字符的一侧断开,点将是邻近字符
     * 
     * @param hit the hit to check.  This must be a valid hit on
     * the TextLayout.
     * @param point the returned point. The point is in standard
     *     coordinates.
     * @throws IllegalArgumentException if the hit is not valid for the
     * TextLayout.
     * @throws NullPointerException if hit or point is null.
     * @since 1.6
     */
    public void hitToPoint(TextHitInfo hit, Point2D point) {
        if (hit == null || point == null) {
            throw new NullPointerException((hit == null ? "hit" : "point") +
                                           " can't be null");
        }
        ensureCache();
        checkTextHit(hit);

        float adv = 0;
        float off = 0;

        int ix = hit.getCharIndex();
        boolean leading = hit.isLeadingEdge();
        boolean ltr;
        if (ix == -1 || ix == textLine.characterCount()) {
            ltr = textLine.isDirectionLTR();
            adv = (ltr == (ix == -1)) ? 0 : lineMetrics.advance;
        } else {
            ltr = textLine.isCharLTR(ix);
            adv = textLine.getCharLinePosition(ix, leading);
            off = textLine.getCharYPosition(ix);
        }
        point.setLocation(adv, off);
        LayoutPath lp = textLine.getLayoutPath();
        if (lp != null) {
            lp.pathToPoint(point, ltr != leading, point);
        }
    }
}
