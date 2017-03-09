/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.text.CharacterIterator;

/**
 * The <code>FontMetrics</code> class defines a font metrics object, which
 * encapsulates information about the rendering of a particular font on a
 * particular screen.
 * <p>
 * <b>Note to subclassers</b>: Since many of these methods form closed,
 * mutually recursive loops, you must take care that you implement
 * at least one of the methods in each such loop to prevent
 * infinite recursion when your subclass is used.
 * In particular, the following is the minimal suggested set of methods
 * to override in order to ensure correctness and prevent infinite
 * recursion (though other subsets are equally feasible):
 * <ul>
 * <li>{@link #getAscent()}
 * <li>{@link #getLeading()}
 * <li>{@link #getMaxAdvance()}
 * <li>{@link #charWidth(char)}
 * <li>{@link #charsWidth(char[], int, int)}
 * </ul>
 * <p>
 * <img src="doc-files/FontMetrics-1.gif" alt="The letter 'p' showing its 'reference point'"
 * style="border:15px; float:right; margin: 7px 10px;">
 * Note that the implementations of these methods are
 * inefficient, so they are usually overridden with more efficient
 * toolkit-specific implementations.
 * <p>
 * When an application asks to place a character at the position
 * (<i>x</i>,&nbsp;<i>y</i>), the character is placed so that its
 * reference point (shown as the dot in the accompanying image) is
 * put at that position. The reference point specifies a horizontal
 * line called the <i>baseline</i> of the character. In normal
 * printing, the baselines of characters should align.
 * <p>
 * In addition, every character in a font has an <i>ascent</i>, a
 * <i>descent</i>, and an <i>advance width</i>. The ascent is the
 * amount by which the character ascends above the baseline. The
 * descent is the amount by which the character descends below the
 * baseline. The advance width indicates the position at which AWT
 * should place the next character.
 * <p>
 * An array of characters or a string can also have an ascent, a
 * descent, and an advance width. The ascent of the array is the
 * maximum ascent of any character in the array. The descent is the
 * maximum descent of any character in the array. The advance width
 * is the sum of the advance widths of each of the characters in the
 * character array.  The advance of a <code>String</code> is the
 * distance along the baseline of the <code>String</code>.  This
 * distance is the width that should be used for centering or
 * right-aligning the <code>String</code>.
 * <p>Note that the advance of a <code>String</code> is not necessarily
 * the sum of the advances of its characters measured in isolation
 * because the width of a character can vary depending on its context.
 * For example, in Arabic text, the shape of a character can change
 * in order to connect to other characters.  Also, in some scripts,
 * certain character sequences can be represented by a single shape,
 * called a <em>ligature</em>.  Measuring characters individually does
 * not account for these transformations.
 * <p>Font metrics are baseline-relative, meaning that they are
 * generally independent of the rotation applied to the font (modulo
 * possible grid hinting effects).  See {@link java.awt.Font Font}.
 *
 * <p>
 *  <code> FontMetrics </code>类定义了一个字体度量对象,它封装了特定字体在特定屏幕上的呈现信息。
 * <p>
 *  <b>子类注意事项</b>：由于许多这些方法形成闭合的,相互递归的循环,因此您必须注意在每个这样的循环中至少实现一个方法,以防止在使用子类时发生无限递归。
 * 特别是,以下是为了确保正确性和防止无限递归而覆盖的最小建议方法集(尽管其他子集同样可行)：。
 * <ul>
 *  <li> {@ link #getLeading()} <li> {@ link #getMaxAdvance()} <li> {@ link #charWidth(char)} <li> {@ link #charsWidth(char [],int,int)}
 * 。
 * </ul>
 * <p>
 *  <img src ="doc-files / FontMetrics-1.gif"alt ="字母'p'显示其'参考点'"
 * style="border:15px; float:right; margin: 7px 10px;">
 *  请注意,这些方法的实现是低效的,因此它们通常被更有效的工具包特定实现覆盖。
 * <p>
 *  当应用程序要求在字符位置(<i> x </i>,<y> y </i>)放置字符时,字符的放置使其参考点图像)放在该位置。参考点指定称为字符的<i>基线</i>的水平线。
 * 在正常打印中,字符的基线应对齐。
 * <p>
 * 此外,字体中的每个字符都有<i>上升</i>,<i>下降</i>和<i>前进宽度</i>。上升是人物在基线之上上升的量。下降是字符下降到基线以下的量。前进宽度表示AWT应放置下一个字符的位置。
 * <p>
 * 字符数组或字符串也可以具有上升,下降和前进宽度。数组的上升是数组中任意字符的最大上升。下降是数组中任何字符的最大下降。前进宽度是字符阵列中每个字符的前进宽度的总和。
 *  <code> String </code>的前进是沿着<code> String </code>的基线的距离。此距离是用于定位或右对齐<code> String </code>的宽度。
 *  <p>请注意,<code> String </code>的进度不一定是隔离测量的字符的进度的总和,因为字符的宽度可以根据其上下文而变化。例如,在阿拉伯语文本中,字符的形状可以改变以便连接到其他字符。
 * 此外,在一些脚本中,某些字符序列可以由单个形状表示,称为<em> ligature </em>。单独测量字符不考虑这些变换。
 *  <p>字体度量是相对的,意味着它们通常独立于应用于字体的旋转(模式可能的网格提示效果)。请参阅{@link java.awt.Font Font}。
 * 
 * 
 * @author      Jim Graham
 * @see         java.awt.Font
 * @since       JDK1.0
 */
public abstract class FontMetrics implements java.io.Serializable {

    static {
        /* ensure that the necessary native libraries are loaded */
        Toolkit.loadLibraries();
        if (!GraphicsEnvironment.isHeadless()) {
            initIDs();
        }
    }

    private static final FontRenderContext
        DEFAULT_FRC = new FontRenderContext(null, false, false);

    /**
     * The actual {@link Font} from which the font metrics are
     * created.
     * This cannot be null.
     *
     * <p>
     *  创建字体指标的实际{@link Font}。此值不能为空。
     * 
     * 
     * @serial
     * @see #getFont()
     */
    protected Font font;

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
    private static final long serialVersionUID = 1681126225205050147L;

    /**
     * Creates a new <code>FontMetrics</code> object for finding out
     * height and width information about the specified <code>Font</code>
     * and specific character glyphs in that <code>Font</code>.
     * <p>
     * 创建一个新的<code> FontMetrics </code>对象,以查找<code> Font </code>和<code> Font </code>中指定字符字形的高度和宽度信息。
     * 
     * 
     * @param     font the <code>Font</code>
     * @see       java.awt.Font
     */
    protected FontMetrics(Font font) {
        this.font = font;
    }

    /**
     * Gets the <code>Font</code> described by this
     * <code>FontMetrics</code> object.
     * <p>
     *  获取由此<code> FontMetrics </code>对象描述的<code> Font </code>。
     * 
     * 
     * @return    the <code>Font</code> described by this
     * <code>FontMetrics</code> object.
     */
    public Font getFont() {
        return font;
    }

    /**
     * Gets the <code>FontRenderContext</code> used by this
     * <code>FontMetrics</code> object to measure text.
     * <p>
     * Note that methods in this class which take a <code>Graphics</code>
     * parameter measure text using the <code>FontRenderContext</code>
     * of that <code>Graphics</code> object, and not this
     * <code>FontRenderContext</code>
     * <p>
     *  获取由此<code> FontMetrics </code>对象使用的<code> FontRenderContext </code>来测量文本。
     * <p>
     *  注意,这个类中使用<code> Graphics </code>参数测量文本的方法是使用<code> Graphics </code>对象的<code> FontRenderContext </code>
     * ,而不是这个<code> FontRenderContext < / code>。
     * 
     * 
     * @return    the <code>FontRenderContext</code> used by this
     * <code>FontMetrics</code> object.
     * @since 1.6
     */
    public FontRenderContext getFontRenderContext() {
        return DEFAULT_FRC;
    }

    /**
     * Determines the <em>standard leading</em> of the
     * <code>Font</code> described by this <code>FontMetrics</code>
     * object.  The standard leading, or
     * interline spacing, is the logical amount of space to be reserved
     * between the descent of one line of text and the ascent of the next
     * line. The height metric is calculated to include this extra space.
     * <p>
     *  确定由此<code> FontMetrics </code>对象描述的<code> Font </code>的<em>标准领先</em>标准前导或行间距是在一行文本的下降和下一行的上升之间保留的空间的
     * 逻辑量。
     * 计算高度度量以包括此额外空间。
     * 
     * 
     * @return    the standard leading of the <code>Font</code>.
     * @see   #getHeight()
     * @see   #getAscent()
     * @see   #getDescent()
     */
    public int getLeading() {
        return 0;
    }

    /**
     * Determines the <em>font ascent</em> of the <code>Font</code>
     * described by this <code>FontMetrics</code> object. The font ascent
     * is the distance from the font's baseline to the top of most
     * alphanumeric characters. Some characters in the <code>Font</code>
     * might extend above the font ascent line.
     * <p>
     *  确定由此<code> FontMetrics </code>对象描述的<code> Font </code>的<em>字体上升</em>字体上升是字体的基线到大多数字母数字字符顶部的距离。
     *  <code> Font </code>中的某些字符可能会延伸到字体上边界。
     * 
     * 
     * @return     the font ascent of the <code>Font</code>.
     * @see        #getMaxAscent()
     */
    public int getAscent() {
        return font.getSize();
    }

    /**
     * Determines the <em>font descent</em> of the <code>Font</code>
     * described by this
     * <code>FontMetrics</code> object. The font descent is the distance
     * from the font's baseline to the bottom of most alphanumeric
     * characters with descenders. Some characters in the
     * <code>Font</code> might extend
     * below the font descent line.
     * <p>
     * 确定由此<code> FontMetrics </code>对象描述的<code> Font </code>的<em>字体下降</em>字体下降是字体的基线到具有下降的大多数字母数字字符底部的距离。
     *  <code> Font </code>中的某些字符可能会延伸到字体下降线以下。
     * 
     * 
     * @return     the font descent of the <code>Font</code>.
     * @see        #getMaxDescent()
     */
    public int getDescent() {
        return 0;
    }

    /**
     * Gets the standard height of a line of text in this font.  This
     * is the distance between the baseline of adjacent lines of text.
     * It is the sum of the leading + ascent + descent. Due to rounding
     * this may not be the same as getAscent() + getDescent() + getLeading().
     * There is no guarantee that lines of text spaced at this distance are
     * disjoint; such lines may overlap if some characters overshoot
     * either the standard ascent or the standard descent metric.
     * <p>
     *  获取此字体中一行文本的标准高度。这是相邻文本行的基线之间的距离。它是领先+上升+下降的总和。由于四舍五入,这可能不同于getAscent()+ getDescent()+ getLeading()。
     * 不能保证在该距离处间隔开的文本行是不相交的;如果一些字符超过标准上升或标准下降度量,这些线可能重叠。
     * 
     * 
     * @return    the standard height of the font.
     * @see       #getLeading()
     * @see       #getAscent()
     * @see       #getDescent()
     */
    public int getHeight() {
        return getLeading() + getAscent() + getDescent();
    }

    /**
     * Determines the maximum ascent of the <code>Font</code>
     * described by this <code>FontMetrics</code> object.  No character
     * extends further above the font's baseline than this height.
     * <p>
     *  确定由此<code> FontMetrics </code>对象描述的<code> Font </code>的最大上升。字体的基线上没有比此高度更高的字符。
     * 
     * 
     * @return    the maximum ascent of any character in the
     * <code>Font</code>.
     * @see       #getAscent()
     */
    public int getMaxAscent() {
        return getAscent();
    }

    /**
     * Determines the maximum descent of the <code>Font</code>
     * described by this <code>FontMetrics</code> object.  No character
     * extends further below the font's baseline than this height.
     * <p>
     *  确定由此<code> FontMetrics </code>对象描述的<code> Font </code>的最大下降。没有字符在字体的基线下方比此高度更远。
     * 
     * 
     * @return    the maximum descent of any character in the
     * <code>Font</code>.
     * @see       #getDescent()
     */
    public int getMaxDescent() {
        return getDescent();
    }

    /**
     * For backward compatibility only.
     * <p>
     *  仅用于向后兼容。
     * 
     * 
     * @return    the maximum descent of any character in the
     * <code>Font</code>.
     * @see #getMaxDescent()
     * @deprecated As of JDK version 1.1.1,
     * replaced by <code>getMaxDescent()</code>.
     */
    @Deprecated
    public int getMaxDecent() {
        return getMaxDescent();
    }

    /**
     * Gets the maximum advance width of any character in this
     * <code>Font</code>.  The advance is the
     * distance from the leftmost point to the rightmost point on the
     * string's baseline.  The advance of a <code>String</code> is
     * not necessarily the sum of the advances of its characters.
     * <p>
     *  获取此<code> Font </code>中任意字符的最大前进宽度。 advance是从最左点到字符串基线上最右点的距离。
     *  <code> String </code>的前进不一定是其字符前进的总和。
     * 
     * 
     * @return    the maximum advance width of any character
     *            in the <code>Font</code>, or <code>-1</code> if the
     *            maximum advance width is not known.
     */
    public int getMaxAdvance() {
        return -1;
    }

    /**
     * Returns the advance width of the specified character in this
     * <code>Font</code>.  The advance is the
     * distance from the leftmost point to the rightmost point on the
     * character's baseline.  Note that the advance of a
     * <code>String</code> is not necessarily the sum of the advances
     * of its characters.
     *
     * <p>This method doesn't validate the specified character to be a
     * valid Unicode code point. The caller must validate the
     * character value using {@link
     * java.lang.Character#isValidCodePoint(int)
     * Character.isValidCodePoint} if necessary.
     *
     * <p>
     * 返回此<code> Font </code>中指定字符的前进宽度。 advance是从最左点到字符基线上最右点的距离。注意,<code> String </code>的前进不一定是其字符前进的总和。
     * 
     *  <p>此方法不会将指定的字符验证为有效的Unicode代码点。
     * 调用者必须使用{@link java.lang.Character#isValidCodePoint(int)Character.isValidCodePoint}来验证字符值。
     * 
     * 
     * @param codePoint the character (Unicode code point) to be measured
     * @return    the advance width of the specified character
     *            in the <code>Font</code> described by this
     *            <code>FontMetrics</code> object.
     * @see   #charsWidth(char[], int, int)
     * @see   #stringWidth(String)
     */
    public int charWidth(int codePoint) {
        if (!Character.isValidCodePoint(codePoint)) {
            codePoint = 0xffff; // substitute missing glyph width
        }

        if (codePoint < 256) {
            return getWidths()[codePoint];
        } else {
            char[] buffer = new char[2];
            int len = Character.toChars(codePoint, buffer, 0);
            return charsWidth(buffer, 0, len);
        }
    }

    /**
     * Returns the advance width of the specified character in this
     * <code>Font</code>.  The advance is the
     * distance from the leftmost point to the rightmost point on the
     * character's baseline.  Note that the advance of a
     * <code>String</code> is not necessarily the sum of the advances
     * of its characters.
     *
     * <p><b>Note:</b> This method cannot handle <a
     * href="../lang/Character.html#supplementary"> supplementary
     * characters</a>. To support all Unicode characters, including
     * supplementary characters, use the {@link #charWidth(int)} method.
     *
     * <p>
     *  返回此<code> Font </code>中指定字符的前进宽度。 advance是从最左点到字符基线上最右点的距离。注意,<code> String </code>的前进不一定是其字符前进的总和。
     * 
     *  <p> <b>注意：</b>此方法无法处理<a href="../lang/Character.html#supplementary">补充字符</a>。
     * 要支持所有Unicode字符(包括补充字符),请使用{@link #charWidth(int)}方法。
     * 
     * 
     * @param ch the character to be measured
     * @return     the advance width of the specified character
     *                  in the <code>Font</code> described by this
     *                  <code>FontMetrics</code> object.
     * @see        #charsWidth(char[], int, int)
     * @see        #stringWidth(String)
     */
    public int charWidth(char ch) {
        if (ch < 256) {
            return getWidths()[ch];
        }
        char data[] = {ch};
        return charsWidth(data, 0, 1);
    }

    /**
     * Returns the total advance width for showing the specified
     * <code>String</code> in this <code>Font</code>.  The advance
     * is the distance from the leftmost point to the rightmost point
     * on the string's baseline.
     * <p>
     * Note that the advance of a <code>String</code> is
     * not necessarily the sum of the advances of its characters.
     * <p>
     *  返回显示此<code> Font </code>中指定的<code> String </code>的总前进宽度。 advance是从最左点到字符串基线上最右点的距离。
     * <p>
     *  注意,<code> String </code>的前进不一定是其字符前进的总和。
     * 
     * 
     * @param str the <code>String</code> to be measured
     * @return    the advance width of the specified <code>String</code>
     *                  in the <code>Font</code> described by this
     *                  <code>FontMetrics</code>.
     * @throws NullPointerException if str is null.
     * @see       #bytesWidth(byte[], int, int)
     * @see       #charsWidth(char[], int, int)
     * @see       #getStringBounds(String, Graphics)
     */
    public int stringWidth(String str) {
        int len = str.length();
        char data[] = new char[len];
        str.getChars(0, len, data, 0);
        return charsWidth(data, 0, len);
    }

    /**
     * Returns the total advance width for showing the specified array
     * of characters in this <code>Font</code>.  The advance is the
     * distance from the leftmost point to the rightmost point on the
     * string's baseline.  The advance of a <code>String</code>
     * is not necessarily the sum of the advances of its characters.
     * This is equivalent to measuring a <code>String</code> of the
     * characters in the specified range.
     * <p>
     * 返回在此<code> Font </code>中显示指定字符数组的总预处理宽度。 advance是从最左点到字符串基线上最右点的距离。
     *  <code> String </code>的前进不一定是其字符前进的总和。这相当于测量指定范围内的字符的<code> String </code>。
     * 
     * 
     * @param data the array of characters to be measured
     * @param off the start offset of the characters in the array
     * @param len the number of characters to be measured from the array
     * @return    the advance width of the subarray of the specified
     *               <code>char</code> array in the font described by
     *               this <code>FontMetrics</code> object.
     * @throws    NullPointerException if <code>data</code> is null.
     * @throws    IndexOutOfBoundsException if the <code>off</code>
     *            and <code>len</code> arguments index characters outside
     *            the bounds of the <code>data</code> array.
     * @see       #charWidth(int)
     * @see       #charWidth(char)
     * @see       #bytesWidth(byte[], int, int)
     * @see       #stringWidth(String)
     */
    public int charsWidth(char data[], int off, int len) {
        return stringWidth(new String(data, off, len));
    }

    /**
     * Returns the total advance width for showing the specified array
     * of bytes in this <code>Font</code>.  The advance is the
     * distance from the leftmost point to the rightmost point on the
     * string's baseline.  The advance of a <code>String</code>
     * is not necessarily the sum of the advances of its characters.
     * This is equivalent to measuring a <code>String</code> of the
     * characters in the specified range.
     * <p>
     *  返回在此<code> Font </code>中显示指定的字节数组的总前进宽度。 advance是从最左点到字符串基线上最右点的距离。
     *  <code> String </code>的前进不一定是其字符前进的总和。这相当于测量指定范围内的字符的<code> String </code>。
     * 
     * 
     * @param data the array of bytes to be measured
     * @param off the start offset of the bytes in the array
     * @param len the number of bytes to be measured from the array
     * @return    the advance width of the subarray of the specified
     *               <code>byte</code> array in the <code>Font</code>
     *                  described by
     *               this <code>FontMetrics</code> object.
     * @throws    NullPointerException if <code>data</code> is null.
     * @throws    IndexOutOfBoundsException if the <code>off</code>
     *            and <code>len</code> arguments index bytes outside
     *            the bounds of the <code>data</code> array.
     * @see       #charsWidth(char[], int, int)
     * @see       #stringWidth(String)
     */
    public int bytesWidth(byte data[], int off, int len) {
        return stringWidth(new String(data, 0, off, len));
    }

    /**
     * Gets the advance widths of the first 256 characters in the
     * <code>Font</code>.  The advance is the
     * distance from the leftmost point to the rightmost point on the
     * character's baseline.  Note that the advance of a
     * <code>String</code> is not necessarily the sum of the advances
     * of its characters.
     * <p>
     *  获取<code> Font </code>中前256个字符的前进宽度。 advance是从最左点到字符基线上最右点的距离。
     * 注意,<code> String </code>的前进不一定是其字符前进的总和。
     * 
     * 
     * @return    an array storing the advance widths of the
     *                 characters in the <code>Font</code>
     *                 described by this <code>FontMetrics</code> object.
     */
    public int[] getWidths() {
        int widths[] = new int[256];
        for (char ch = 0 ; ch < 256 ; ch++) {
            widths[ch] = charWidth(ch);
        }
        return widths;
    }

    /**
     * Checks to see if the <code>Font</code> has uniform line metrics.  A
     * composite font may consist of several different fonts to cover
     * various character sets.  In such cases, the
     * <code>FontLineMetrics</code> objects are not uniform.
     * Different fonts may have a different ascent, descent, metrics and
     * so on.  This information is sometimes necessary for line
     * measuring and line breaking.
     * <p>
     *  检查<code> Font </code>是否有统一的线条指标。复合字体可以由几种不同的字体组成,以覆盖各种字符集。
     * 在这种情况下,<code> FontLineMetrics </code>对象不统一。不同的字体可能有不同的上升,下降,度量等。此信息有时对于线路测量和线路断开是必需的。
     * 
     * 
     * @return <code>true</code> if the font has uniform line metrics;
     * <code>false</code> otherwise.
     * @see java.awt.Font#hasUniformLineMetrics()
     */
    public boolean hasUniformLineMetrics() {
        return font.hasUniformLineMetrics();
    }

    /**
     * Returns the {@link LineMetrics} object for the specified
     * <code>String</code> in the specified {@link Graphics} context.
     * <p>
     * 返回指定的{@link Graphics}上下文中指定的<code> String </code>的{@link LineMetrics}对象。
     * 
     * 
     * @param str the specified <code>String</code>
     * @param context the specified <code>Graphics</code> context
     * @return a <code>LineMetrics</code> object created with the
     * specified <code>String</code> and <code>Graphics</code> context.
     * @see java.awt.Font#getLineMetrics(String, FontRenderContext)
     */
    public LineMetrics getLineMetrics( String str, Graphics context) {
        return font.getLineMetrics(str, myFRC(context));
    }

    /**
     * Returns the {@link LineMetrics} object for the specified
     * <code>String</code> in the specified {@link Graphics} context.
     * <p>
     *  返回指定的{@link Graphics}上下文中指定的<code> String </code>的{@link LineMetrics}对象。
     * 
     * 
     * @param str the specified <code>String</code>
     * @param beginIndex the initial offset of <code>str</code>
     * @param limit the end offset of <code>str</code>
     * @param context the specified <code>Graphics</code> context
     * @return a <code>LineMetrics</code> object created with the
     * specified <code>String</code> and <code>Graphics</code> context.
     * @see java.awt.Font#getLineMetrics(String, int, int, FontRenderContext)
     */
    public LineMetrics getLineMetrics( String str,
                                            int beginIndex, int limit,
                                            Graphics context) {
        return font.getLineMetrics(str, beginIndex, limit, myFRC(context));
    }

    /**
     * Returns the {@link LineMetrics} object for the specified
     * character array in the specified {@link Graphics} context.
     * <p>
     *  返回指定的{@link Graphics}上下文中指定字符数组的{@link LineMetrics}对象。
     * 
     * 
     * @param chars the specified character array
     * @param beginIndex the initial offset of <code>chars</code>
     * @param limit the end offset of <code>chars</code>
     * @param context the specified <code>Graphics</code> context
     * @return a <code>LineMetrics</code> object created with the
     * specified character array and <code>Graphics</code> context.
     * @see java.awt.Font#getLineMetrics(char[], int, int, FontRenderContext)
     */
    public LineMetrics getLineMetrics(char [] chars,
                                            int beginIndex, int limit,
                                            Graphics context) {
        return font.getLineMetrics(
                                chars, beginIndex, limit, myFRC(context));
    }

    /**
     * Returns the {@link LineMetrics} object for the specified
     * {@link CharacterIterator} in the specified {@link Graphics}
     * context.
     * <p>
     *  返回指定{@link Graphics}上下文中指定的{@link CharacterIterator}的{@link LineMetrics}对象。
     * 
     * 
     * @param ci the specified <code>CharacterIterator</code>
     * @param beginIndex the initial offset in <code>ci</code>
     * @param limit the end index of <code>ci</code>
     * @param context the specified <code>Graphics</code> context
     * @return a <code>LineMetrics</code> object created with the
     * specified arguments.
     * @see java.awt.Font#getLineMetrics(CharacterIterator, int, int, FontRenderContext)
     */
    public LineMetrics getLineMetrics(CharacterIterator ci,
                                            int beginIndex, int limit,
                                            Graphics context) {
        return font.getLineMetrics(ci, beginIndex, limit, myFRC(context));
    }

    /**
     * Returns the bounds of the specified <code>String</code> in the
     * specified <code>Graphics</code> context.  The bounds is used
     * to layout the <code>String</code>.
     * <p>Note: The returned bounds is in baseline-relative coordinates
     * (see {@link java.awt.FontMetrics class notes}).
     * <p>
     *  返回指定的<code> Graphics </code>上下文中指定的<code> String </code>的边界。 bounds用于布局<code> String </code>。
     *  <p>注意：返回的边界在基线相对坐标中(参见{@link java.awt.FontMetrics课堂笔记})。
     * 
     * 
     * @param str the specified <code>String</code>
     * @param context the specified <code>Graphics</code> context
     * @return a {@link Rectangle2D} that is the bounding box of the
     * specified <code>String</code> in the specified
     * <code>Graphics</code> context.
     * @see java.awt.Font#getStringBounds(String, FontRenderContext)
     */
    public Rectangle2D getStringBounds( String str, Graphics context) {
        return font.getStringBounds(str, myFRC(context));
    }

    /**
     * Returns the bounds of the specified <code>String</code> in the
     * specified <code>Graphics</code> context.  The bounds is used
     * to layout the <code>String</code>.
     * <p>Note: The returned bounds is in baseline-relative coordinates
     * (see {@link java.awt.FontMetrics class notes}).
     * <p>
     *  返回指定的<code> Graphics </code>上下文中指定的<code> String </code>的边界。 bounds用于布局<code> String </code>。
     *  <p>注意：返回的边界在基线相对坐标中(参见{@link java.awt.FontMetrics课堂笔记})。
     * 
     * 
     * @param str the specified <code>String</code>
     * @param beginIndex the offset of the beginning of <code>str</code>
     * @param limit the end offset of <code>str</code>
     * @param context the specified <code>Graphics</code> context
     * @return a <code>Rectangle2D</code> that is the bounding box of the
     * specified <code>String</code> in the specified
     * <code>Graphics</code> context.
     * @see java.awt.Font#getStringBounds(String, int, int, FontRenderContext)
     */
    public Rectangle2D getStringBounds( String str,
                                        int beginIndex, int limit,
                                        Graphics context) {
        return font.getStringBounds(str, beginIndex, limit,
                                        myFRC(context));
    }

   /**
     * Returns the bounds of the specified array of characters
     * in the specified <code>Graphics</code> context.
     * The bounds is used to layout the <code>String</code>
     * created with the specified array of characters,
     * <code>beginIndex</code> and <code>limit</code>.
     * <p>Note: The returned bounds is in baseline-relative coordinates
     * (see {@link java.awt.FontMetrics class notes}).
     * <p>
     *  返回指定的<code> Graphics </code>上下文中指定的字符数组的边界。
     *  bounds用于布局使用指定的字符数组<code> beginIndex </code>和<code> limit </code>创建的<code> String </code>。
     *  <p>注意：返回的边界在基线相对坐标中(参见{@link java.awt.FontMetrics课堂笔记})。
     * 
     * 
     * @param chars an array of characters
     * @param beginIndex the initial offset of the array of
     * characters
     * @param limit the end offset of the array of characters
     * @param context the specified <code>Graphics</code> context
     * @return a <code>Rectangle2D</code> that is the bounding box of the
     * specified character array in the specified
     * <code>Graphics</code> context.
     * @see java.awt.Font#getStringBounds(char[], int, int, FontRenderContext)
     */
    public Rectangle2D getStringBounds( char [] chars,
                                        int beginIndex, int limit,
                                        Graphics context) {
        return font.getStringBounds(chars, beginIndex, limit,
                                        myFRC(context));
    }

   /**
     * Returns the bounds of the characters indexed in the specified
     * <code>CharacterIterator</code> in the
     * specified <code>Graphics</code> context.
     * <p>Note: The returned bounds is in baseline-relative coordinates
     * (see {@link java.awt.FontMetrics class notes}).
     * <p>
     * 返回在指定的<code> Graphics </code>上下文中的指定<code> CharacterIterator </code>中索引的字符的边界。
     *  <p>注意：返回的边界在基线相对坐标中(参见{@link java.awt.FontMetrics课堂笔记})。
     * 
     * 
     * @param ci the specified <code>CharacterIterator</code>
     * @param beginIndex the initial offset in <code>ci</code>
     * @param limit the end index of <code>ci</code>
     * @param context the specified <code>Graphics</code> context
     * @return a <code>Rectangle2D</code> that is the bounding box of the
     * characters indexed in the specified <code>CharacterIterator</code>
     * in the specified <code>Graphics</code> context.
     * @see java.awt.Font#getStringBounds(CharacterIterator, int, int, FontRenderContext)
     */
    public Rectangle2D getStringBounds(CharacterIterator ci,
                                        int beginIndex, int limit,
                                        Graphics context) {
        return font.getStringBounds(ci, beginIndex, limit,
                                        myFRC(context));
    }

    /**
     * Returns the bounds for the character with the maximum bounds
     * in the specified <code>Graphics</code> context.
     * <p>
     *  返回在指定的<code> Graphics </code>上下文中具有最大边界的字符的边界。
     * 
     * 
     * @param context the specified <code>Graphics</code> context
     * @return a <code>Rectangle2D</code> that is the
     * bounding box for the character with the maximum bounds.
     * @see java.awt.Font#getMaxCharBounds(FontRenderContext)
     */
    public Rectangle2D getMaxCharBounds(Graphics context) {
        return font.getMaxCharBounds(myFRC(context));
    }

    private FontRenderContext myFRC(Graphics context) {
        if (context instanceof Graphics2D) {
            return ((Graphics2D)context).getFontRenderContext();
        }
        return DEFAULT_FRC;
    }


    /**
     * Returns a representation of this <code>FontMetrics</code>
     * object's values as a <code>String</code>.
     * <p>
     *  将<code> FontMetrics </code>对象的值的表示形式返回为<code> String </code>。
     * 
     * 
     * @return    a <code>String</code> representation of this
     * <code>FontMetrics</code> object.
     * @since     JDK1.0.
     */
    public String toString() {
        return getClass().getName() +
            "[font=" + getFont() +
            "ascent=" + getAscent() +
            ", descent=" + getDescent() +
            ", height=" + getHeight() + "]";
    }

    /**
     * Initialize JNI field and method IDs
     * <p>
     *  初始化JNI字段和方法ID
     */
    private static native void initIDs();
}
