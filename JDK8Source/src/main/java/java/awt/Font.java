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

import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.LineMetrics;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.peer.FontPeer;
import java.io.*;
import java.lang.ref.SoftReference;
import java.nio.file.Files;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.text.AttributedCharacterIterator.Attribute;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import sun.font.StandardGlyphVector;

import sun.font.AttributeMap;
import sun.font.AttributeValues;
import sun.font.CompositeFont;
import sun.font.CreatedFontTracker;
import sun.font.Font2D;
import sun.font.Font2DHandle;
import sun.font.FontAccess;
import sun.font.FontManager;
import sun.font.FontManagerFactory;
import sun.font.FontUtilities;
import sun.font.GlyphLayout;
import sun.font.FontLineMetrics;
import sun.font.CoreMetrics;

import static sun.font.EAttribute.*;

/**
 * The <code>Font</code> class represents fonts, which are used to
 * render text in a visible way.
 * A font provides the information needed to map sequences of
 * <em>characters</em> to sequences of <em>glyphs</em>
 * and to render sequences of glyphs on <code>Graphics</code> and
 * <code>Component</code> objects.
 *
 * <h3>Characters and Glyphs</h3>
 *
 * A <em>character</em> is a symbol that represents an item such as a letter,
 * a digit, or punctuation in an abstract way. For example, <code>'g'</code>,
 * LATIN SMALL LETTER G, is a character.
 * <p>
 * A <em>glyph</em> is a shape used to render a character or a sequence of
 * characters. In simple writing systems, such as Latin, typically one glyph
 * represents one character. In general, however, characters and glyphs do not
 * have one-to-one correspondence. For example, the character '&aacute;'
 * LATIN SMALL LETTER A WITH ACUTE, can be represented by
 * two glyphs: one for 'a' and one for '&acute;'. On the other hand, the
 * two-character string "fi" can be represented by a single glyph, an
 * "fi" ligature. In complex writing systems, such as Arabic or the South
 * and South-East Asian writing systems, the relationship between characters
 * and glyphs can be more complicated and involve context-dependent selection
 * of glyphs as well as glyph reordering.
 *
 * A font encapsulates the collection of glyphs needed to render a selected set
 * of characters as well as the tables needed to map sequences of characters to
 * corresponding sequences of glyphs.
 *
 * <h3>Physical and Logical Fonts</h3>
 *
 * The Java Platform distinguishes between two kinds of fonts:
 * <em>physical</em> fonts and <em>logical</em> fonts.
 * <p>
 * <em>Physical</em> fonts are the actual font libraries containing glyph data
 * and tables to map from character sequences to glyph sequences, using a font
 * technology such as TrueType or PostScript Type 1.
 * All implementations of the Java Platform must support TrueType fonts;
 * support for other font technologies is implementation dependent.
 * Physical fonts may use names such as Helvetica, Palatino, HonMincho, or
 * any number of other font names.
 * Typically, each physical font supports only a limited set of writing
 * systems, for example, only Latin characters or only Japanese and Basic
 * Latin.
 * The set of available physical fonts varies between configurations.
 * Applications that require specific fonts can bundle them and instantiate
 * them using the {@link #createFont createFont} method.
 * <p>
 * <em>Logical</em> fonts are the five font families defined by the Java
 * platform which must be supported by any Java runtime environment:
 * Serif, SansSerif, Monospaced, Dialog, and DialogInput.
 * These logical fonts are not actual font libraries. Instead, the logical
 * font names are mapped to physical fonts by the Java runtime environment.
 * The mapping is implementation and usually locale dependent, so the look
 * and the metrics provided by them vary.
 * Typically, each logical font name maps to several physical fonts in order to
 * cover a large range of characters.
 * <p>
 * Peered AWT components, such as {@link Label Label} and
 * {@link TextField TextField}, can only use logical fonts.
 * <p>
 * For a discussion of the relative advantages and disadvantages of using
 * physical or logical fonts, see the
 * <a href="http://www.oracle.com/technetwork/java/javase/tech/faq-jsp-138165.html">Internationalization FAQ</a>
 * document.
 *
 * <h3>Font Faces and Names</h3>
 *
 * A <code>Font</code>
 * can have many faces, such as heavy, medium, oblique, gothic and
 * regular. All of these faces have similar typographic design.
 * <p>
 * There are three different names that you can get from a
 * <code>Font</code> object.  The <em>logical font name</em> is simply the
 * name that was used to construct the font.
 * The <em>font face name</em>, or just <em>font name</em> for
 * short, is the name of a particular font face, like Helvetica Bold. The
 * <em>family name</em> is the name of the font family that determines the
 * typographic design across several faces, like Helvetica.
 * <p>
 * The <code>Font</code> class represents an instance of a font face from
 * a collection of  font faces that are present in the system resources
 * of the host system.  As examples, Arial Bold and Courier Bold Italic
 * are font faces.  There can be several <code>Font</code> objects
 * associated with a font face, each differing in size, style, transform
 * and font features.
 * <p>
 * The {@link GraphicsEnvironment#getAllFonts() getAllFonts} method
 * of the <code>GraphicsEnvironment</code> class returns an
 * array of all font faces available in the system. These font faces are
 * returned as <code>Font</code> objects with a size of 1, identity
 * transform and default font features. These
 * base fonts can then be used to derive new <code>Font</code> objects
 * with varying sizes, styles, transforms and font features via the
 * <code>deriveFont</code> methods in this class.
 *
 * <h3>Font and TextAttribute</h3>
 *
 * <p><code>Font</code> supports most
 * <code>TextAttribute</code>s.  This makes some operations, such as
 * rendering underlined text, convenient since it is not
 * necessary to explicitly construct a <code>TextLayout</code> object.
 * Attributes can be set on a Font by constructing or deriving it
 * using a <code>Map</code> of <code>TextAttribute</code> values.
 *
 * <p>The values of some <code>TextAttributes</code> are not
 * serializable, and therefore attempting to serialize an instance of
 * <code>Font</code> that has such values will not serialize them.
 * This means a Font deserialized from such a stream will not compare
 * equal to the original Font that contained the non-serializable
 * attributes.  This should very rarely pose a problem
 * since these attributes are typically used only in special
 * circumstances and are unlikely to be serialized.
 *
 * <ul>
 * <li><code>FOREGROUND</code> and <code>BACKGROUND</code> use
 * <code>Paint</code> values. The subclass <code>Color</code> is
 * serializable, while <code>GradientPaint</code> and
 * <code>TexturePaint</code> are not.</li>
 * <li><code>CHAR_REPLACEMENT</code> uses
 * <code>GraphicAttribute</code> values.  The subclasses
 * <code>ShapeGraphicAttribute</code> and
 * <code>ImageGraphicAttribute</code> are not serializable.</li>
 * <li><code>INPUT_METHOD_HIGHLIGHT</code> uses
 * <code>InputMethodHighlight</code> values, which are
 * not serializable.  See {@link java.awt.im.InputMethodHighlight}.</li>
 * </ul>
 *
 * <p>Clients who create custom subclasses of <code>Paint</code> and
 * <code>GraphicAttribute</code> can make them serializable and
 * avoid this problem.  Clients who use input method highlights can
 * convert these to the platform-specific attributes for that
 * highlight on the current platform and set them on the Font as
 * a workaround.
 *
 * <p>The <code>Map</code>-based constructor and
 * <code>deriveFont</code> APIs ignore the FONT attribute, and it is
 * not retained by the Font; the static {@link #getFont} method should
 * be used if the FONT attribute might be present.  See {@link
 * java.awt.font.TextAttribute#FONT} for more information.</p>
 *
 * <p>Several attributes will cause additional rendering overhead
 * and potentially invoke layout.  If a <code>Font</code> has such
 * attributes, the <code>{@link #hasLayoutAttributes()}</code> method
 * will return true.</p>
 *
 * <p>Note: Font rotations can cause text baselines to be rotated.  In
 * order to account for this (rare) possibility, font APIs are
 * specified to return metrics and take parameters 'in
 * baseline-relative coordinates'.  This maps the 'x' coordinate to
 * the advance along the baseline, (positive x is forward along the
 * baseline), and the 'y' coordinate to a distance along the
 * perpendicular to the baseline at 'x' (positive y is 90 degrees
 * clockwise from the baseline vector).  APIs for which this is
 * especially important are called out as having 'baseline-relative
 * coordinates.'
 * <p>
 *  <code> Font </code>类表示用于以可见方式呈现文本的字体。
 * 字体提供将<em>字符</em>序列映射到<em>字形</em>并且在<code> Graphics </code>和<code> Component </code>对象上渲染字形序列。
 * 
 *  <h3>字符和字形</h3>
 * 
 *  一个<em>字符</em>是以抽象方式表示诸如字母,数字或标点符号的项。例如,<code>'g'</code>,拉丁小写字母G一个人物
 * <p>
 * </em> <em> </em> <em> </em> </em>是用于渲染字符或字符序列的形状。在简单的书写系统中,例如拉丁文,通常一个字形代表一个字符。
 * 一对一对应例如,字符"&aacute;"拉丁小写字母A与ACUTE,可以用两个字形表示：一个用于'a',一个用于'&acute;'另一方面,双字符串"fi"可以由单个字形,"fi"连字表示。
 * 在复杂的书写系统中,例如阿拉伯语或南亚和东南亚书写系统,字符之间的关系和字形可以更复杂并且涉及字形的上下文相关选择以及字形重排序。
 * 
 * 字体封装了呈现所选字符集所需的字形集合以及将字符序列映射到对应的字形序列所需的表
 * 
 *  <h3>物理和逻辑字体</h3>
 * 
 *  Java平台区分两种字体：<em>物理</em>字体和<em>逻辑</em>字体
 * <p>
 * <em>物理</em>字体是包含使用诸如TrueType或PostScript Type 1之类的字体技术从字符序列到字形序列映射的字形数据和表的实际字体库。
 * Java平台的所有实现必须支持TrueType字体;对其他字体技术的支持是与实现相关的物理字体可以使用诸如Helvetica,Palatino,HonMincho或任何数量的其他字体名称的名称通常,每种
 * 物理字体仅支持有限的一组写入系统,例如,仅拉丁字符或仅日语和基本拉丁语可用物理字体集在配置之间各不相同需要特定字体的应用程序可以捆绑它们并使用{@link #createFont createFont}
 * 方法实例化它们。
 * <em>物理</em>字体是包含使用诸如TrueType或PostScript Type 1之类的字体技术从字符序列到字形序列映射的字形数据和表的实际字体库。
 * <p>
 * <em>逻辑</em>字体是Java平台定义的五种字体,必须由任何Java运行时环境支持：Serif,SansSerif,Monospaced,Dialog和DialogInput这些逻辑字体不是实际的
 * 字体库。
 * 逻辑字体名称通过Java运行时环境映射到物理字体映射是实现并且通常依赖于区域设置,因此它们提供的外观和度量标准通常,每个逻辑字体名称映射到几个物理字体,以覆盖大范围的字符。
 * <p>
 *  对等AWT组件(例如{@link Label Label}和{@link TextField TextField})只能使用逻辑字体
 * <p>
 * 有关使用物理或逻辑字体的相对优缺点的讨论,请参见<a href=\"http://wwworaclecom/technetwork/java/javase/tech/faq-jsp-138165html\">
 * 国际化常见问题解答</a>文件。
 * 
 *  <h3>字体和名称</h3>
 * 
 *  <code>字体</code>可以有多个面,如重,中,斜,哥特和正则所有这些面都有类似的排版设计
 * <p>
 * 从<code> Font </code>对象中可以获得三个不同的名称<em>​​逻辑字体名称</em>只是用于构造字体的名称<em>​​ font face name < / em>,或简称为<em>字
 * 体名称</em>是特定字体的名称,例如Helvetica Bold </em>是字体家族的名称,印刷设计跨几个面孔,如Helvetica。
 * <p>
 *  <code> Font </code>类表示来自存在于主机系统的系统资源中的字体表面的集合的字体表面的实例。
 * 例如,Arial Bold和Courier Bold Italic是字体表面可以存在几个与字体相关联的<code>字体</code>对象,每个字体在大小,样式,变换和字体特征方面各不相同。
 * <p>
 * <code> GraphicsEnvironment </code>类的{@link GraphicsEnvironment#getAllFonts()getAllFonts}方法返回系统中可用的所有字
 * 体面的数组这些字体面作为<code> Font </code>大小为1,身份转换和默认字体特征这些基本字体然后可以用于通过<code> derivFont </code>导出具有不同大小,样式,变换和字
 * 体特征的新<code> Font </code>方法。
 * 
 *  <h3> Font和TextAttribute </h3>
 * 
 * <p> <code> Font </code>支持大多数<code> TextAttribute </code> s这使得一些操作,如渲染下划线文本,方便,因为没有必要显式构造一个<code> Text
 * Layout </code > object可以通过使用<code> TextAttribute </code>值的<code> Map </code>构造或派生来在Font上设置属性。
 * 
 * <p>某些<code> TextAttributes </code>的值不可序列化,因此尝试序列化具有此类值的<code> Font </code>实例将不会序列化它们这意味着一个字体流将不会等于包含非
 * 可序列化属性的原始字体。
 * 这应该很少引起问题,因为这些属性通常仅在特殊情况下使用,并且不可能被序列化。
 * 
 * <ul>
 * <code> </code>使用<code> Paint </code>值子类<code> Color </code>是可序列化的,而<code> GradientPaint </code>代码>和<code>
 *  TexturePaint </code>不是</li> <li> <code> CHAR_REPLACEMENT </code>使用<code> GraphicAttribute </code>值子类
 * <code> ShapeGraphicAttribute <代码> ImageGraphicAttribute </code>不可序列化</li> </li> <li> <code> INPUT_MET
 * HOD_HIGHLIGHT </code>使用<code> InputMethodHighlight </code>。
 * </ul>
 * 
 * <p>创建<code> Paint </code>和<code> GraphicAttribute </code>的自定义子类的客户端可以使它们可序列化,并避免此问题使用输入法高亮的客户端可以将这些转换
 * 为平台特定的属性在当前平台上突出显示,并将其设置为字体作为解决方法。
 * 
 *  <p> <code>基于地图</code>的构造函数和<code> derivFont </code> API忽略FONT属性,并且不会被Font保留;如果FONT属性可能存在,则应使用静态{@link #getFont}
 * 方法有关详细信息,请参阅{@link javaawtfontTextAttribute#FONT}。
 * </p>。
 * 
 * <p>几个属性将导致额外的渲染开销并潜在地调用布局如果<code> Font </code>有这样的属性,<code> {@ link #hasLayoutAttributes()} </code>方法
 * 将返回true < p>。
 * 
 *  <p>注意：字体旋转可以导致文本基线被旋转为了解决这种(罕见的)可能性,字体API被指定返回度量并采用参数'在基线相对坐标'这将"x"坐标映射到沿着基线的前进(正x沿着基线向前),并且"y"坐标到沿"
 * x"的基线的垂线的距离(正y是从基线矢量顺时针旋转90度)这是特别重要的,被称为具有"基线相对坐标"。
 * 
 */
public class Font implements java.io.Serializable
{
    private static class FontAccessImpl extends FontAccess {
        public Font2D getFont2D(Font font) {
            return font.getFont2D();
        }

        public void setFont2D(Font font, Font2DHandle handle) {
            font.font2DHandle = handle;
        }

        public void setCreatedFont(Font font) {
            font.createdFont = true;
        }

        public boolean isCreatedFont(Font font) {
            return font.createdFont;
        }
    }

    static {
        /* ensure that the necessary native libraries are loaded */
        Toolkit.loadLibraries();
        initIDs();
        FontAccess.setFontAccess(new FontAccessImpl());
    }

    /**
     * This is now only used during serialization.  Typically
     * it is null.
     *
     * <p>
     * 现在只在序列化期间使用它通常为null
     * 
     * 
     * @serial
     * @see #getAttributes()
     */
    private Hashtable<Object, Object> fRequestedAttributes;

    /*
     * Constants to be used for logical font family names.
     * <p>
     *  用于逻辑字体系列名称的常量
     * 
     */

    /**
     * A String constant for the canonical family name of the
     * logical font "Dialog". It is useful in Font construction
     * to provide compile-time verification of the name.
     * <p>
     *  逻辑字体"Dialog"的规范族名称的字符串常量在Font构造中提供名称的编译时验证非常有用
     * 
     * 
     * @since 1.6
     */
    public static final String DIALOG = "Dialog";

    /**
     * A String constant for the canonical family name of the
     * logical font "DialogInput". It is useful in Font construction
     * to provide compile-time verification of the name.
     * <p>
     *  逻辑字体"DialogInput"的规范系列名称的字符串常量在Font构造中提供名称的编译时验证非常有用
     * 
     * 
     * @since 1.6
     */
    public static final String DIALOG_INPUT = "DialogInput";

    /**
     * A String constant for the canonical family name of the
     * logical font "SansSerif". It is useful in Font construction
     * to provide compile-time verification of the name.
     * <p>
     *  用于逻辑字体"SansSerif"的规范族名称的字符串常量在字体构造中提供名称的编译时验证非常有用
     * 
     * 
     * @since 1.6
     */
    public static final String SANS_SERIF = "SansSerif";

    /**
     * A String constant for the canonical family name of the
     * logical font "Serif". It is useful in Font construction
     * to provide compile-time verification of the name.
     * <p>
     *  逻辑字体"Serif"的规范族名称的字符串常量在字体构造中提供名称的编译时验证非常有用
     * 
     * 
     * @since 1.6
     */
    public static final String SERIF = "Serif";

    /**
     * A String constant for the canonical family name of the
     * logical font "Monospaced". It is useful in Font construction
     * to provide compile-time verification of the name.
     * <p>
     * 逻辑字体"Monospaced"的规范族名称的字符串常量在Font构造中提供名称的编译时验证非常有用
     * 
     * 
     * @since 1.6
     */
    public static final String MONOSPACED = "Monospaced";

    /*
     * Constants to be used for styles. Can be combined to mix
     * styles.
     * <p>
     *  用于样式的常量可以组合为混合样式
     * 
     */

    /**
     * The plain style constant.
     * <p>
     *  平原风格常量
     * 
     */
    public static final int PLAIN       = 0;

    /**
     * The bold style constant.  This can be combined with the other style
     * constants (except PLAIN) for mixed styles.
     * <p>
     *  粗体样式常量可以与其他样式常量(除了PLAIN)组合用于混合样式
     * 
     */
    public static final int BOLD        = 1;

    /**
     * The italicized style constant.  This can be combined with the other
     * style constants (except PLAIN) for mixed styles.
     * <p>
     *  斜体样式常量这可以与其他样式常量(除了PLAIN)组合用于混合样式
     * 
     */
    public static final int ITALIC      = 2;

    /**
     * The baseline used in most Roman scripts when laying out text.
     * <p>
     *  在布局文本时大多数罗马语言中使用的基线
     * 
     */
    public static final int ROMAN_BASELINE = 0;

    /**
     * The baseline used in ideographic scripts like Chinese, Japanese,
     * and Korean when laying out text.
     * <p>
     *  布局文本时使用的表意文字脚本中的基线,如中文,日语和韩语
     * 
     */
    public static final int CENTER_BASELINE = 1;

    /**
     * The baseline used in Devanigiri and similar scripts when laying
     * out text.
     * <p>
     *  在布局文本时,Devanigiri和类似脚本中使用的基线
     * 
     */
    public static final int HANGING_BASELINE = 2;

    /**
     * Identify a font resource of type TRUETYPE.
     * Used to specify a TrueType font resource to the
     * {@link #createFont} method.
     * The TrueType format was extended to become the OpenType
     * format, which adds support for fonts with Postscript outlines,
     * this tag therefore references these fonts, as well as those
     * with TrueType outlines.
     * <p>
     * 标识类型为TRUETYPE的字体资源用于为{@link #createFont}方法指定TrueType字体资源TrueType格式已扩展为OpenType格式,这增加了对PostScript脚本字体的
     * 支持,因此该标签引用这些字体,以及具有TrueType轮廓的那些。
     * 
     * 
     * @since 1.3
     */

    public static final int TRUETYPE_FONT = 0;

    /**
     * Identify a font resource of type TYPE1.
     * Used to specify a Type1 font resource to the
     * {@link #createFont} method.
     * <p>
     *  标识TYPE1类型的字体资源用于指定{@link #createFont}方法的Type1字体资源
     * 
     * 
     * @since 1.5
     */
    public static final int TYPE1_FONT = 1;

    /**
     * The logical name of this <code>Font</code>, as passed to the
     * constructor.
     * <p>
     *  传递给构造函数的<code> Font </code>的逻辑名
     * 
     * 
     * @since JDK1.0
     *
     * @serial
     * @see #getName
     */
    protected String name;

    /**
     * The style of this <code>Font</code>, as passed to the constructor.
     * This style can be PLAIN, BOLD, ITALIC, or BOLD+ITALIC.
     * <p>
     *  传递给构造函数的<code> Font </code>的样式此样式可以是PLAIN,BOLD,ITALIC或BOLD + ITALIC
     * 
     * 
     * @since JDK1.0
     *
     * @serial
     * @see #getStyle()
     */
    protected int style;

    /**
     * The point size of this <code>Font</code>, rounded to integer.
     * <p>
     *  此<code> Font </code>的点大小,四舍五入为整数
     * 
     * 
     * @since JDK1.0
     *
     * @serial
     * @see #getSize()
     */
    protected int size;

    /**
     * The point size of this <code>Font</code> in <code>float</code>.
     *
     * <p>
     *  <code> float </code>中的<code> Font </code>的点大小
     * 
     * 
     * @serial
     * @see #getSize()
     * @see #getSize2D()
     */
    protected float pointSize;

    /**
     * The platform specific font information.
     * <p>
     * 平台特定的字体信息
     * 
     */
    private transient FontPeer peer;
    private transient long pData;       // native JDK1.1 font pointer
    private transient Font2DHandle font2DHandle;

    private transient AttributeValues values;
    private transient boolean hasLayoutAttributes;

    /*
     * If the origin of a Font is a created font then this attribute
     * must be set on all derived fonts too.
     * <p>
     *  如果字体的原点是创建的字体,那么该属性也必须在所有派生字体上设置
     * 
     */
    private transient boolean createdFont = false;

    /*
     * This is true if the font transform is not identity.  It
     * is used to avoid unnecessary instantiation of an AffineTransform.
     * <p>
     *  这是真的如果字体变换不是identity它用于避免不必要的实例化AffineTransform
     * 
     */
    private transient boolean nonIdentityTx;

    /*
     * A cached value used when a transform is required for internal
     * use.  This must not be exposed to callers since AffineTransform
     * is mutable.
     * <p>
     *  当内部使用需要转换时使用的高速缓存值由于AffineTransform是可变的,因此不能暴露给调用者
     * 
     */
    private static final AffineTransform identityTx = new AffineTransform();

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 11 serialVersionUID
     * 
     */
    private static final long serialVersionUID = -4206021311591459213L;

    /**
     * Gets the peer of this <code>Font</code>.
     * <p>
     *  获取此<code> Font </code>的对等体
     * 
     * 
     * @return  the peer of the <code>Font</code>.
     * @since JDK1.1
     * @deprecated Font rendering is now platform independent.
     */
    @Deprecated
    public FontPeer getPeer(){
        return getPeer_NoClientCode();
    }
    // NOTE: This method is called by privileged threads.
    //       We implement this functionality in a package-private method
    //       to insure that it cannot be overridden by client subclasses.
    //       DO NOT INVOKE CLIENT CODE ON THIS THREAD!
    @SuppressWarnings("deprecation")
    final FontPeer getPeer_NoClientCode() {
        if(peer == null) {
            Toolkit tk = Toolkit.getDefaultToolkit();
            this.peer = tk.getFontPeer(name, style);
        }
        return peer;
    }

    /**
     * Return the AttributeValues object associated with this
     * font.  Most of the time, the internal object is null.
     * If required, it will be created from the 'standard'
     * state on the font.  Only non-default values will be
     * set in the AttributeValues object.
     *
     * <p>Since the AttributeValues object is mutable, and it
     * is cached in the font, care must be taken to ensure that
     * it is not mutated.
     * <p>
     *  返回与此字体关联的AttributeValues对象大多数时候,内部对象是null如果需要,它将从字体的"标准"状态创建只有非默认值将在AttributeValues对象中设置
     * 
     * <p>由于AttributeValues对象是可变的,并且它以字体缓存,因此必须注意确保它不会改变
     * 
     */
    private AttributeValues getAttributeValues() {
        if (values == null) {
            AttributeValues valuesTmp = new AttributeValues();
            valuesTmp.setFamily(name);
            valuesTmp.setSize(pointSize); // expects the float value.

            if ((style & BOLD) != 0) {
                valuesTmp.setWeight(2); // WEIGHT_BOLD
            }

            if ((style & ITALIC) != 0) {
                valuesTmp.setPosture(.2f); // POSTURE_OBLIQUE
            }
            valuesTmp.defineAll(PRIMARY_MASK); // for streaming compatibility
            values = valuesTmp;
        }

        return values;
    }

    private Font2D getFont2D() {
        FontManager fm = FontManagerFactory.getInstance();
        if (fm.usingPerAppContextComposites() &&
            font2DHandle != null &&
            font2DHandle.font2D instanceof CompositeFont &&
            ((CompositeFont)(font2DHandle.font2D)).isStdComposite()) {
            return fm.findFont2D(name, style,
                                          FontManager.LOGICAL_FALLBACK);
        } else if (font2DHandle == null) {
            font2DHandle =
                fm.findFont2D(name, style,
                              FontManager.LOGICAL_FALLBACK).handle;
        }
        /* Do not cache the de-referenced font2D. It must be explicitly
         * de-referenced to pick up a valid font in the event that the
         * original one is marked invalid
         * <p>
         *  取消引用以在原始标记无效的情况下选取有效字体
         * 
         */
        return font2DHandle.font2D;
    }

    /**
     * Creates a new <code>Font</code> from the specified name, style and
     * point size.
     * <p>
     * The font name can be a font face name or a font family name.
     * It is used together with the style to find an appropriate font face.
     * When a font family name is specified, the style argument is used to
     * select the most appropriate face from the family. When a font face
     * name is specified, the face's style and the style argument are
     * merged to locate the best matching font from the same family.
     * For example if face name "Arial Bold" is specified with style
     * <code>Font.ITALIC</code>, the font system looks for a face in the
     * "Arial" family that is bold and italic, and may associate the font
     * instance with the physical font face "Arial Bold Italic".
     * The style argument is merged with the specified face's style, not
     * added or subtracted.
     * This means, specifying a bold face and a bold style does not
     * double-embolden the font, and specifying a bold face and a plain
     * style does not lighten the font.
     * <p>
     * If no face for the requested style can be found, the font system
     * may apply algorithmic styling to achieve the desired style.
     * For example, if <code>ITALIC</code> is requested, but no italic
     * face is available, glyphs from the plain face may be algorithmically
     * obliqued (slanted).
     * <p>
     * Font name lookup is case insensitive, using the case folding
     * rules of the US locale.
     * <p>
     * If the <code>name</code> parameter represents something other than a
     * logical font, i.e. is interpreted as a physical font face or family, and
     * this cannot be mapped by the implementation to a physical font or a
     * compatible alternative, then the font system will map the Font
     * instance to "Dialog", such that for example, the family as reported
     * by {@link #getFamily() getFamily} will be "Dialog".
     * <p>
     *
     * <p>
     *  从指定的名称,样式和点大小创建新的<code> Font </code>
     * <p>
     * 字体名称可以是字体名称或字体系列名称它与样式一起使用以找到合适的字体面当指定字体系列名称时,style参数用于从家族中选择最合适的面孔指定字体面名称,面部样式和样式参数被合并以定位来自相同族的最佳匹配字
     * 体例如,如果面部名称"Arial Bold"以样式<code> FontITALIC </code>指定,则字体系统在"Arial"系列中查找粗体和斜体的面部,并且可以将字体实例与物理字体面"Arial
     *  Bold Italic"相关联。
     * 样式参数与指定面部样式合并,不添加或减去这意味着,指定粗体字和粗体样式不会使字体双重加粗,并且指定粗体字和平滑样式不会减小字体。
     * <p>
     * 如果没有找到所请求风格的面部,则字体系统可以应用算法风格以实现期望的风格。例如,如果请求<code> ITALIC </code>,但没有斜体面,则来自平面的字形可以在算法上倾斜(倾斜)
     * <p>
     *  字体名称查找不区分大小写,使用美国语言环境的大小写折叠规则
     * <p>
     *  如果<code> name </code>参数表示除逻辑字体以外的其他东西,即被解释为物理字体面或族,并且这不能通过实现映射到物理字体或兼容的替代,系统会将Font实例映射到"Dialog",例如,{@link #getFamily()getFamily}
     * 报告的族将是"Dialog"。
     * <p>
     * 
     * 
     * @param name the font name.  This can be a font face name or a font
     * family name, and may represent either a logical font or a physical
     * font found in this {@code GraphicsEnvironment}.
     * The family names for logical fonts are: Dialog, DialogInput,
     * Monospaced, Serif, or SansSerif. Pre-defined String constants exist
     * for all of these names, for example, {@code DIALOG}. If {@code name} is
     * {@code null}, the <em>logical font name</em> of the new
     * {@code Font} as returned by {@code getName()} is set to
     * the name "Default".
     * @param style the style constant for the {@code Font}
     * The style argument is an integer bitmask that may
     * be {@code PLAIN}, or a bitwise union of {@code BOLD} and/or
     * {@code ITALIC} (for example, {@code ITALIC} or {@code BOLD|ITALIC}).
     * If the style argument does not conform to one of the expected
     * integer bitmasks then the style is set to {@code PLAIN}.
     * @param size the point size of the {@code Font}
     * @see GraphicsEnvironment#getAllFonts
     * @see GraphicsEnvironment#getAvailableFontFamilyNames
     * @since JDK1.0
     */
    public Font(String name, int style, int size) {
        this.name = (name != null) ? name : "Default";
        this.style = (style & ~0x03) == 0 ? style : 0;
        this.size = size;
        this.pointSize = size;
    }

    private Font(String name, int style, float sizePts) {
        this.name = (name != null) ? name : "Default";
        this.style = (style & ~0x03) == 0 ? style : 0;
        this.size = (int)(sizePts + 0.5);
        this.pointSize = sizePts;
    }

    /* This constructor is used by deriveFont when attributes is null */
    private Font(String name, int style, float sizePts,
                 boolean created, Font2DHandle handle) {
        this(name, style, sizePts);
        this.createdFont = created;
        /* Fonts created from a stream will use the same font2D instance
         * as the parent.
         * One exception is that if the derived font is requested to be
         * in a different style, then also check if its a CompositeFont
         * and if so build a new CompositeFont from components of that style.
         * CompositeFonts can only be marked as "created" if they are used
         * to add fall backs to a physical font. And non-composites are
         * always from "Font.createFont()" and shouldn't get this treatment.
         * <p>
         * 作为父的一个例外是,如果派生的字体被要求在一个不同的样式,那么也检查它的一个CompositeFont,如果是这样从该样式的组件构建一个新的CompositeFont CompositeFonts只能被
         * 标记为"创建"如果他们用于添加后退到一个物理字体和非复合材料总是从"FontcreateFont()",不应该得到这种处理。
         * 
         */
        if (created) {
            if (handle.font2D instanceof CompositeFont &&
                handle.font2D.getStyle() != style) {
                FontManager fm = FontManagerFactory.getInstance();
                this.font2DHandle = fm.getNewComposite(null, style, handle);
            } else {
                this.font2DHandle = handle;
            }
        }
    }

    /* used to implement Font.createFont */
    private Font(File fontFile, int fontFormat,
                 boolean isCopy, CreatedFontTracker tracker)
        throws FontFormatException {
        this.createdFont = true;
        /* Font2D instances created by this method track their font file
         * so that when the Font2D is GC'd it can also remove the file.
         * <p>
         *  所以当Font2D是GC'd它也可以删除文件
         * 
         */
        FontManager fm = FontManagerFactory.getInstance();
        this.font2DHandle = fm.createFont2D(fontFile, fontFormat, isCopy,
                                            tracker).handle;
        this.name = this.font2DHandle.font2D.getFontName(Locale.getDefault());
        this.style = Font.PLAIN;
        this.size = 1;
        this.pointSize = 1f;
    }

    /* This constructor is used when one font is derived from another.
     * Fonts created from a stream will use the same font2D instance as the
     * parent. They can be distinguished because the "created" argument
     * will be "true". Since there is no way to recreate these fonts they
     * need to have the handle to the underlying font2D passed in.
     * "created" is also true when a special composite is referenced by the
     * handle for essentially the same reasons.
     * But when deriving a font in these cases two particular attributes
     * need special attention: family/face and style.
     * The "composites" in these cases need to be recreated with optimal
     * fonts for the new values of family and style.
     * For fonts created with createFont() these are treated differently.
     * JDK can often synthesise a different style (bold from plain
     * for example). For fonts created with "createFont" this is a reasonable
     * solution but its also possible (although rare) to derive a font with a
     * different family attribute. In this case JDK needs
     * to break the tie with the original Font2D and find a new Font.
     * The oldName and oldStyle are supplied so they can be compared with
     * what the Font2D and the values. To speed things along :
     * oldName == null will be interpreted as the name is unchanged.
     * oldStyle = -1 will be interpreted as the style is unchanged.
     * In these cases there is no need to interrogate "values".
     * <p>
     * 从流创建的字体将使用与父相同的font2D实例他们可以被区分,因为"created"参数将是"true"由于没有办法重新创建这些字体,他们需要有传递的底层font2D的句柄"创建"也是真的当一个特殊的复
     * 合材料被句柄引用基本上相同的原因但是,当在这些情况下导出字体两个特殊的属性需要特别注意：家庭/脸和风格在这些情况下,"复合材料"需要使用最佳字体为家庭和样式的新值重新创建对于使用createFont()
     * 创建的字体,这些处理不同.JDK通常可以合成不同的样式(例如粗体)对于使用"createFont"创建的字体,这是一个合理的解决方案,但它也可能(虽然很少)导出具有不同系列属性的字体在这种情况下,JDK
     * 需要打破与原始Font2D的关系,并找到一个新的字体oldName和oldStyle被提供,所以他们可以与什么的Font2D和值比较：oldName == null将被解释为名称不变oldStyle =
     *  -1将被解释为样式不变在这些情况下,没有需要询问"价值观"。
     * 
     */
    private Font(AttributeValues values, String oldName, int oldStyle,
                 boolean created, Font2DHandle handle) {

        this.createdFont = created;
        if (created) {
            this.font2DHandle = handle;

            String newName = null;
            if (oldName != null) {
                newName = values.getFamily();
                if (oldName.equals(newName)) newName = null;
            }
            int newStyle = 0;
            if (oldStyle == -1) {
                newStyle = -1;
            } else {
                if (values.getWeight() >= 2f)   newStyle  = BOLD;
                if (values.getPosture() >= .2f) newStyle |= ITALIC;
                if (oldStyle == newStyle)       newStyle  = -1;
            }
            if (handle.font2D instanceof CompositeFont) {
                if (newStyle != -1 || newName != null) {
                    FontManager fm = FontManagerFactory.getInstance();
                    this.font2DHandle =
                        fm.getNewComposite(newName, newStyle, handle);
                }
            } else if (newName != null) {
                this.createdFont = false;
                this.font2DHandle = null;
            }
        }
        initFromValues(values);
    }

    /**
     * Creates a new <code>Font</code> with the specified attributes.
     * Only keys defined in {@link java.awt.font.TextAttribute TextAttribute}
     * are recognized.  In addition the FONT attribute is
     *  not recognized by this constructor
     * (see {@link #getAvailableAttributes}). Only attributes that have
     * values of valid types will affect the new <code>Font</code>.
     * <p>
     * If <code>attributes</code> is <code>null</code>, a new
     * <code>Font</code> is initialized with default values.
     * <p>
     * 使用指定的属性创建新的<code> Font </code>只有在{@link javaawtfontTextAttribute TextAttribute}中定义的键才被识别。
     * 此外,此构造函数不能识别FONT属性(请参阅{@link #getAvailableAttributes}有效类型的值将影响新的<code> Font </code>。
     * <p>
     *  如果<code> attributes </code>是<code> null </code>,一个新的<code> Font </code>
     * 
     * 
     * @see java.awt.font.TextAttribute
     * @param attributes the attributes to assign to the new
     *          <code>Font</code>, or <code>null</code>
     */
    public Font(Map<? extends Attribute, ?> attributes) {
        initFromValues(AttributeValues.fromMap(attributes, RECOGNIZED_MASK));
    }

    /**
     * Creates a new <code>Font</code> from the specified <code>font</code>.
     * This constructor is intended for use by subclasses.
     * <p>
     *  从指定的<code>字体</code>创建一个新的<code> Font </code>此构造函数供子类使用
     * 
     * 
     * @param font from which to create this <code>Font</code>.
     * @throws NullPointerException if <code>font</code> is null
     * @since 1.6
     */
    protected Font(Font font) {
        if (font.values != null) {
            initFromValues(font.getAttributeValues().clone());
        } else {
            this.name = font.name;
            this.style = font.style;
            this.size = font.size;
            this.pointSize = font.pointSize;
        }
        this.font2DHandle = font.font2DHandle;
        this.createdFont = font.createdFont;
    }

    /**
     * Font recognizes all attributes except FONT.
     * <p>
     *  字体识别除FONT之外的所有属性
     * 
     */
    private static final int RECOGNIZED_MASK = AttributeValues.MASK_ALL
        & ~AttributeValues.getMask(EFONT);

    /**
     * These attributes are considered primary by the FONT attribute.
     * <p>
     *  这些属性被FONT属性视为主要属性
     * 
     */
    private static final int PRIMARY_MASK =
        AttributeValues.getMask(EFAMILY, EWEIGHT, EWIDTH, EPOSTURE, ESIZE,
                                ETRANSFORM, ESUPERSCRIPT, ETRACKING);

    /**
     * These attributes are considered secondary by the FONT attribute.
     * <p>
     *  这些属性被FONT属性视为次要的
     * 
     */
    private static final int SECONDARY_MASK =
        RECOGNIZED_MASK & ~PRIMARY_MASK;

    /**
     * These attributes are handled by layout.
     * <p>
     *  这些属性由布局处理
     * 
     */
    private static final int LAYOUT_MASK =
        AttributeValues.getMask(ECHAR_REPLACEMENT, EFOREGROUND, EBACKGROUND,
                                EUNDERLINE, ESTRIKETHROUGH, ERUN_DIRECTION,
                                EBIDI_EMBEDDING, EJUSTIFICATION,
                                EINPUT_METHOD_HIGHLIGHT, EINPUT_METHOD_UNDERLINE,
                                ESWAP_COLORS, ENUMERIC_SHAPING, EKERNING,
                                ELIGATURES, ETRACKING, ESUPERSCRIPT);

    private static final int EXTRA_MASK =
            AttributeValues.getMask(ETRANSFORM, ESUPERSCRIPT, EWIDTH);

    /**
     * Initialize the standard Font fields from the values object.
     * <p>
     * 从值对象初始化标准字体字段
     * 
     */
    private void initFromValues(AttributeValues values) {
        this.values = values;
        values.defineAll(PRIMARY_MASK); // for 1.5 streaming compatibility

        this.name = values.getFamily();
        this.pointSize = values.getSize();
        this.size = (int)(values.getSize() + 0.5);
        if (values.getWeight() >= 2f) this.style |= BOLD; // not == 2f
        if (values.getPosture() >= .2f) this.style |= ITALIC; // not  == .2f

        this.nonIdentityTx = values.anyNonDefault(EXTRA_MASK);
        this.hasLayoutAttributes =  values.anyNonDefault(LAYOUT_MASK);
    }

    /**
     * Returns a <code>Font</code> appropriate to the attributes.
     * If <code>attributes</code>contains a <code>FONT</code> attribute
     * with a valid <code>Font</code> as its value, it will be
     * merged with any remaining attributes.  See
     * {@link java.awt.font.TextAttribute#FONT} for more
     * information.
     *
     * <p>
     *  返回适用于属性的<code> Font </code>如果<code> attributes </code>包含一个<code> FONT </code>属性,其有效的<code> Font </code>
     * 将与任何其余属性合并请参阅{@link javaawtfontTextAttribute#FONT}了解更多信息。
     * 
     * 
     * @param attributes the attributes to assign to the new
     *          <code>Font</code>
     * @return a new <code>Font</code> created with the specified
     *          attributes
     * @throws NullPointerException if <code>attributes</code> is null.
     * @since 1.2
     * @see java.awt.font.TextAttribute
     */
    public static Font getFont(Map<? extends Attribute, ?> attributes) {
        // optimize for two cases:
        // 1) FONT attribute, and nothing else
        // 2) attributes, but no FONT

        // avoid turning the attributemap into a regular map for no reason
        if (attributes instanceof AttributeMap &&
            ((AttributeMap)attributes).getValues() != null) {
            AttributeValues values = ((AttributeMap)attributes).getValues();
            if (values.isNonDefault(EFONT)) {
                Font font = values.getFont();
                if (!values.anyDefined(SECONDARY_MASK)) {
                    return font;
                }
                // merge
                values = font.getAttributeValues().clone();
                values.merge(attributes, SECONDARY_MASK);
                return new Font(values, font.name, font.style,
                                font.createdFont, font.font2DHandle);
            }
            return new Font(attributes);
        }

        Font font = (Font)attributes.get(TextAttribute.FONT);
        if (font != null) {
            if (attributes.size() > 1) { // oh well, check for anything else
                AttributeValues values = font.getAttributeValues().clone();
                values.merge(attributes, SECONDARY_MASK);
                return new Font(values, font.name, font.style,
                                font.createdFont, font.font2DHandle);
            }

            return font;
        }

        return new Font(attributes);
    }

    /**
     * Used with the byte count tracker for fonts created from streams.
     * If a thread can create temp files anyway, no point in counting
     * font bytes.
     * <p>
     *  与字节计数跟踪器用于从流创建的字体如果一个线程可以创建临时文件无论如何,计数字体字节没有意义
     * 
     */
    private static boolean hasTempPermission() {

        if (System.getSecurityManager() == null) {
            return true;
        }
        File f = null;
        boolean hasPerm = false;
        try {
            f = Files.createTempFile("+~JT", ".tmp").toFile();
            f.delete();
            f = null;
            hasPerm = true;
        } catch (Throwable t) {
            /* inc. any kind of SecurityException */
        }
        return hasPerm;
    }

    /**
     * Returns a new <code>Font</code> using the specified font type
     * and input data.  The new <code>Font</code> is
     * created with a point size of 1 and style {@link #PLAIN PLAIN}.
     * This base font can then be used with the <code>deriveFont</code>
     * methods in this class to derive new <code>Font</code> objects with
     * varying sizes, styles, transforms and font features.  This
     * method does not close the {@link InputStream}.
     * <p>
     * To make the <code>Font</code> available to Font constructors the
     * returned <code>Font</code> must be registered in the
     * <code>GraphicsEnviroment</code> by calling
     * {@link GraphicsEnvironment#registerFont(Font) registerFont(Font)}.
     * <p>
     * 使用指定的字体类型和输入数据返回新的<code> Font </code>新的<code> Font </code>创建为点大小为1和样式{@link #PLAIN PLAIN}然后与此类中的<code>
     *  derivFont </code>方法一起使用,以派生具有不同大小,样式,变形和字体特征的新<code> Font </code>对象此方法不关闭{@link InputStream}。
     * <p>
     *  为了使Font构造函数提供<code> Font </code>,返回的<code> Font </code>必须通过调用{@link GraphicsEnvironment#registerFont(Font)registerFont注册在<code> GraphicsEnviroment </code> (Font)}
     * 。
     * 
     * 
     * @param fontFormat the type of the <code>Font</code>, which is
     * {@link #TRUETYPE_FONT TRUETYPE_FONT} if a TrueType resource is specified.
     * or {@link #TYPE1_FONT TYPE1_FONT} if a Type 1 resource is specified.
     * @param fontStream an <code>InputStream</code> object representing the
     * input data for the font.
     * @return a new <code>Font</code> created with the specified font type.
     * @throws IllegalArgumentException if <code>fontFormat</code> is not
     *     <code>TRUETYPE_FONT</code>or<code>TYPE1_FONT</code>.
     * @throws FontFormatException if the <code>fontStream</code> data does
     *     not contain the required font tables for the specified format.
     * @throws IOException if the <code>fontStream</code>
     *     cannot be completely read.
     * @see GraphicsEnvironment#registerFont(Font)
     * @since 1.3
     */
    public static Font createFont(int fontFormat, InputStream fontStream)
        throws java.awt.FontFormatException, java.io.IOException {

        if (hasTempPermission()) {
            return createFont0(fontFormat, fontStream, null);
        }

        // Otherwise, be extra conscious of pending temp file creation and
        // resourcefully handle the temp file resources, among other things.
        CreatedFontTracker tracker = CreatedFontTracker.getTracker();
        boolean acquired = false;
        try {
            acquired = tracker.acquirePermit();
            if (!acquired) {
                throw new IOException("Timed out waiting for resources.");
            }
            return createFont0(fontFormat, fontStream, tracker);
        } catch (InterruptedException e) {
            throw new IOException("Problem reading font data.");
        } finally {
            if (acquired) {
                tracker.releasePermit();
            }
        }
    }

    private static Font createFont0(int fontFormat, InputStream fontStream,
                                    CreatedFontTracker tracker)
        throws java.awt.FontFormatException, java.io.IOException {

        if (fontFormat != Font.TRUETYPE_FONT &&
            fontFormat != Font.TYPE1_FONT) {
            throw new IllegalArgumentException ("font format not recognized");
        }
        boolean copiedFontData = false;
        try {
            final File tFile = AccessController.doPrivileged(
                new PrivilegedExceptionAction<File>() {
                    public File run() throws IOException {
                        return Files.createTempFile("+~JF", ".tmp").toFile();
                    }
                }
            );
            if (tracker != null) {
                tracker.add(tFile);
            }

            int totalSize = 0;
            try {
                final OutputStream outStream =
                    AccessController.doPrivileged(
                        new PrivilegedExceptionAction<OutputStream>() {
                            public OutputStream run() throws IOException {
                                return new FileOutputStream(tFile);
                            }
                        }
                    );
                if (tracker != null) {
                    tracker.set(tFile, outStream);
                }
                try {
                    byte[] buf = new byte[8192];
                    for (;;) {
                        int bytesRead = fontStream.read(buf);
                        if (bytesRead < 0) {
                            break;
                        }
                        if (tracker != null) {
                            if (totalSize+bytesRead > CreatedFontTracker.MAX_FILE_SIZE) {
                                throw new IOException("File too big.");
                            }
                            if (totalSize+tracker.getNumBytes() >
                                CreatedFontTracker.MAX_TOTAL_BYTES)
                              {
                                throw new IOException("Total files too big.");
                            }
                            totalSize += bytesRead;
                            tracker.addBytes(bytesRead);
                        }
                        outStream.write(buf, 0, bytesRead);
                    }
                    /* don't close the input stream */
                } finally {
                    outStream.close();
                }
                /* After all references to a Font2D are dropped, the file
                 * will be removed. To support long-lived AppContexts,
                 * we need to then decrement the byte count by the size
                 * of the file.
                 * If the data isn't a valid font, the implementation will
                 * delete the tmp file and decrement the byte count
                 * in the tracker object before returning from the
                 * constructor, so we can set 'copiedFontData' to true here
                 * without waiting for the results of that constructor.
                 * <p>
                 * 将被删除为了支持长寿命的AppContext,我们需要减少字节数量的文件的大小如果数据不是一个有效的字体,实现将删除tmp文件和减少字节计数在跟踪器对象在从构造函数返回之前,所以我们可以将'copied
                 * FontData'设置为true,而不必等待该构造函数的结果。
                 * 
                 */
                copiedFontData = true;
                Font font = new Font(tFile, fontFormat, true, tracker);
                return font;
            } finally {
                if (tracker != null) {
                    tracker.remove(tFile);
                }
                if (!copiedFontData) {
                    if (tracker != null) {
                        tracker.subBytes(totalSize);
                    }
                    AccessController.doPrivileged(
                        new PrivilegedExceptionAction<Void>() {
                            public Void run() {
                                tFile.delete();
                                return null;
                            }
                        }
                    );
                }
            }
        } catch (Throwable t) {
            if (t instanceof FontFormatException) {
                throw (FontFormatException)t;
            }
            if (t instanceof IOException) {
                throw (IOException)t;
            }
            Throwable cause = t.getCause();
            if (cause instanceof FontFormatException) {
                throw (FontFormatException)cause;
            }
            throw new IOException("Problem reading font data.");
        }
    }

    /**
     * Returns a new <code>Font</code> using the specified font type
     * and the specified font file.  The new <code>Font</code> is
     * created with a point size of 1 and style {@link #PLAIN PLAIN}.
     * This base font can then be used with the <code>deriveFont</code>
     * methods in this class to derive new <code>Font</code> objects with
     * varying sizes, styles, transforms and font features.
     * <p>
     *  使用指定的字体类型和指定的字体文件返回新的<code> Font </code>新的<code> Font </code>创建为点大小为1和样式{@link #PLAIN PLAIN}字体可以与该类中
     * 的<code> derivFont </code>方法一起使用,以导出具有不同大小,样式,变换和字体特征的新<code> Font </code>对象。
     * 
     * 
     * @param fontFormat the type of the <code>Font</code>, which is
     * {@link #TRUETYPE_FONT TRUETYPE_FONT} if a TrueType resource is
     * specified or {@link #TYPE1_FONT TYPE1_FONT} if a Type 1 resource is
     * specified.
     * So long as the returned font, or its derived fonts are referenced
     * the implementation may continue to access <code>fontFile</code>
     * to retrieve font data. Thus the results are undefined if the file
     * is changed, or becomes inaccessible.
     * <p>
     * To make the <code>Font</code> available to Font constructors the
     * returned <code>Font</code> must be registered in the
     * <code>GraphicsEnviroment</code> by calling
     * {@link GraphicsEnvironment#registerFont(Font) registerFont(Font)}.
     * @param fontFile a <code>File</code> object representing the
     * input data for the font.
     * @return a new <code>Font</code> created with the specified font type.
     * @throws IllegalArgumentException if <code>fontFormat</code> is not
     *     <code>TRUETYPE_FONT</code>or<code>TYPE1_FONT</code>.
     * @throws NullPointerException if <code>fontFile</code> is null.
     * @throws IOException if the <code>fontFile</code> cannot be read.
     * @throws FontFormatException if <code>fontFile</code> does
     *     not contain the required font tables for the specified format.
     * @throws SecurityException if the executing code does not have
     * permission to read from the file.
     * @see GraphicsEnvironment#registerFont(Font)
     * @since 1.5
     */
    public static Font createFont(int fontFormat, File fontFile)
        throws java.awt.FontFormatException, java.io.IOException {

        fontFile = new File(fontFile.getPath());

        if (fontFormat != Font.TRUETYPE_FONT &&
            fontFormat != Font.TYPE1_FONT) {
            throw new IllegalArgumentException ("font format not recognized");
        }
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            FilePermission filePermission =
                new FilePermission(fontFile.getPath(), "read");
            sm.checkPermission(filePermission);
        }
        if (!fontFile.canRead()) {
            throw new IOException("Can't read " + fontFile);
        }
        return new Font(fontFile, fontFormat, false, null);
    }

    /**
     * Returns a copy of the transform associated with this
     * <code>Font</code>.  This transform is not necessarily the one
     * used to construct the font.  If the font has algorithmic
     * superscripting or width adjustment, this will be incorporated
     * into the returned <code>AffineTransform</code>.
     * <p>
     * Typically, fonts will not be transformed.  Clients generally
     * should call {@link #isTransformed} first, and only call this
     * method if <code>isTransformed</code> returns true.
     *
     * <p>
     * 返回与此<code> Font </code>关联的变换的副本。此变换不一定是用于构造字体的变换。
     * 如果字体具有算法上标或宽度调整,则将合并到返回的<code> AffineTransform </code>。
     * <p>
     *  通常,字体不会被转换客户端通常应首先调用{@link #isTransformed},并且只有当<code> isTransformed </code>返回true时才调用此方法
     * 
     * 
     * @return an {@link AffineTransform} object representing the
     *          transform attribute of this <code>Font</code> object.
     */
    public AffineTransform getTransform() {
        /* The most common case is the identity transform.  Most callers
         * should call isTransformed() first, to decide if they need to
         * get the transform, but some may not.  Here we check to see
         * if we have a nonidentity transform, and only do the work to
         * fetch and/or compute it if so, otherwise we return a new
         * identity transform.
         *
         * Note that the transform is _not_ necessarily the same as
         * the transform passed in as an Attribute in a Map, as the
         * transform returned will also reflect the effects of WIDTH and
         * SUPERSCRIPT attributes.  Clients who want the actual transform
         * need to call getRequestedAttributes.
         * <p>
         *  应该首先调用isTransformed(),以决定是否需要获取变换,但有些可能不在这里我们检查看看是否有一个非同一性变换,并且只做工作取得和/或计算它,如果是这样,否则我们返回一个新的身份变换
         * 
         * 请注意,变换不一定与作为地图中的属性传递的变换相同,因为返回的变换也将反映WIDTH和SUPERSCRIPT属性的效果需要实际变换的客户需要调用getRequestedAttributes
         * 
         */
        if (nonIdentityTx) {
            AttributeValues values = getAttributeValues();

            AffineTransform at = values.isNonDefault(ETRANSFORM)
                ? new AffineTransform(values.getTransform())
                : new AffineTransform();

            if (values.getSuperscript() != 0) {
                // can't get ascent and descent here, recursive call to this fn,
                // so use pointsize
                // let users combine super- and sub-scripting

                int superscript = values.getSuperscript();

                double trans = 0;
                int n = 0;
                boolean up = superscript > 0;
                int sign = up ? -1 : 1;
                int ss = up ? superscript : -superscript;

                while ((ss & 7) > n) {
                    int newn = ss & 7;
                    trans += sign * (ssinfo[newn] - ssinfo[n]);
                    ss >>= 3;
                    sign = -sign;
                    n = newn;
                }
                trans *= pointSize;
                double scale = Math.pow(2./3., n);

                at.preConcatenate(AffineTransform.getTranslateInstance(0, trans));
                at.scale(scale, scale);

                // note on placement and italics
                // We preconcatenate the transform because we don't want to translate along
                // the italic angle, but purely perpendicular to the baseline.  While this
                // looks ok for superscripts, it can lead subscripts to stack on each other
                // and bring the following text too close.  The way we deal with potential
                // collisions that can occur in the case of italics is by adjusting the
                // horizontal spacing of the adjacent glyphvectors.  Examine the italic
                // angle of both vectors, if one is non-zero, compute the minimum ascent
                // and descent, and then the x position at each for each vector along its
                // italic angle starting from its (offset) baseline.  Compute the difference
                // between the x positions and use the maximum difference to adjust the
                // position of the right gv.
            }

            if (values.isNonDefault(EWIDTH)) {
                at.scale(values.getWidth(), 1f);
            }

            return at;
        }

        return new AffineTransform();
    }

    // x = r^0 + r^1 + r^2... r^n
    // rx = r^1 + r^2 + r^3... r^(n+1)
    // x - rx = r^0 - r^(n+1)
    // x (1 - r) = r^0 - r^(n+1)
    // x = (r^0 - r^(n+1)) / (1 - r)
    // x = (1 - r^(n+1)) / (1 - r)

    // scale ratio is 2/3
    // trans = 1/2 of ascent * x
    // assume ascent is 3/4 of point size

    private static final float[] ssinfo = {
        0.0f,
        0.375f,
        0.625f,
        0.7916667f,
        0.9027778f,
        0.9768519f,
        1.0262346f,
        1.0591564f,
    };

    /**
     * Returns the family name of this <code>Font</code>.
     *
     * <p>The family name of a font is font specific. Two fonts such as
     * Helvetica Italic and Helvetica Bold have the same family name,
     * <i>Helvetica</i>, whereas their font face names are
     * <i>Helvetica Bold</i> and <i>Helvetica Italic</i>. The list of
     * available family names may be obtained by using the
     * {@link GraphicsEnvironment#getAvailableFontFamilyNames()} method.
     *
     * <p>Use <code>getName</code> to get the logical name of the font.
     * Use <code>getFontName</code> to get the font face name of the font.
     * <p>
     *  返回此<code> Font </code>的家族名称
     * 
     *  <p>字体的家族名称是字体特定的两种字体如Helvetica Italic和Helvetica Bold有相同的姓氏,<Hel> Helvetica </i>,而他们的字体名称是<i> Helveti
     * ca Bold </i >和<i> Helvetica Italic </i>可用的家族名称列表可以通过使用{@link GraphicsEnvironment#getAvailableFontFamilyNames()}
     * 方法获得。
     * 
     * <p>使用<code> getName </code>获取字体的逻辑名称使用<code> getFontName </code>获取字体的字体名称
     * 
     * 
     * @return a <code>String</code> that is the family name of this
     *          <code>Font</code>.
     *
     * @see #getName
     * @see #getFontName
     * @since JDK1.1
     */
    public String getFamily() {
        return getFamily_NoClientCode();
    }
    // NOTE: This method is called by privileged threads.
    //       We implement this functionality in a package-private
    //       method to insure that it cannot be overridden by client
    //       subclasses.
    //       DO NOT INVOKE CLIENT CODE ON THIS THREAD!
    final String getFamily_NoClientCode() {
        return getFamily(Locale.getDefault());
    }

    /**
     * Returns the family name of this <code>Font</code>, localized for
     * the specified locale.
     *
     * <p>The family name of a font is font specific. Two fonts such as
     * Helvetica Italic and Helvetica Bold have the same family name,
     * <i>Helvetica</i>, whereas their font face names are
     * <i>Helvetica Bold</i> and <i>Helvetica Italic</i>. The list of
     * available family names may be obtained by using the
     * {@link GraphicsEnvironment#getAvailableFontFamilyNames()} method.
     *
     * <p>Use <code>getFontName</code> to get the font face name of the font.
     * <p>
     *  返回为指定区域设置本地化的<code> Font </code>的系列名称
     * 
     *  <p>字体的家族名称是字体特定的两种字体如Helvetica Italic和Helvetica Bold有相同的姓氏,<Hel> Helvetica </i>,而他们的字体名称是<i> Helveti
     * ca Bold </i >和<i> Helvetica Italic </i>可用的家族名称列表可以通过使用{@link GraphicsEnvironment#getAvailableFontFamilyNames()}
     * 方法获得。
     * 
     *  <p>使用<code> getFontName </code>获取字体的字体名称
     * 
     * 
     * @param l locale for which to get the family name
     * @return a <code>String</code> representing the family name of the
     *          font, localized for the specified locale.
     * @see #getFontName
     * @see java.util.Locale
     * @since 1.2
     */
    public String getFamily(Locale l) {
        if (l == null) {
            throw new NullPointerException("null locale doesn't mean default");
        }
        return getFont2D().getFamilyName(l);
    }

    /**
     * Returns the postscript name of this <code>Font</code>.
     * Use <code>getFamily</code> to get the family name of the font.
     * Use <code>getFontName</code> to get the font face name of the font.
     * <p>
     * 返回此<code> Font </code>的后缀名称使用<code> getFamily </code>获取字体的家族名称使用<code> getFontName </code>获取字体的字体名称
     * 
     * 
     * @return a <code>String</code> representing the postscript name of
     *          this <code>Font</code>.
     * @since 1.2
     */
    public String getPSName() {
        return getFont2D().getPostscriptName();
    }

    /**
     * Returns the logical name of this <code>Font</code>.
     * Use <code>getFamily</code> to get the family name of the font.
     * Use <code>getFontName</code> to get the font face name of the font.
     * <p>
     *  返回此<code> Font </code>的逻辑名称使用<code> getFamily </code>获取字体的家族名称使用<code> getFontName </code>获取字体的字体名称。
     * 
     * 
     * @return a <code>String</code> representing the logical name of
     *          this <code>Font</code>.
     * @see #getFamily
     * @see #getFontName
     * @since JDK1.0
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the font face name of this <code>Font</code>.  For example,
     * Helvetica Bold could be returned as a font face name.
     * Use <code>getFamily</code> to get the family name of the font.
     * Use <code>getName</code> to get the logical name of the font.
     * <p>
     *  返回此<code> Font </code>的字体面名称例如,Helvetica Bold可以作为字体名称返回使用<code> getFamily </code>获取字体的家族名称使用<code> g
     * etName </code>以获取字体的逻辑名称。
     * 
     * 
     * @return a <code>String</code> representing the font face name of
     *          this <code>Font</code>.
     * @see #getFamily
     * @see #getName
     * @since 1.2
     */
    public String getFontName() {
      return getFontName(Locale.getDefault());
    }

    /**
     * Returns the font face name of the <code>Font</code>, localized
     * for the specified locale. For example, Helvetica Fett could be
     * returned as the font face name.
     * Use <code>getFamily</code> to get the family name of the font.
     * <p>
     * 返回为指定语言环境本地化的<code> Font </code>的字体面名称例如,可以返回Helvetica Fett作为字体面名称使用<code> getFamily </code>获取字体
     * 
     * 
     * @param l a locale for which to get the font face name
     * @return a <code>String</code> representing the font face name,
     *          localized for the specified locale.
     * @see #getFamily
     * @see java.util.Locale
     */
    public String getFontName(Locale l) {
        if (l == null) {
            throw new NullPointerException("null locale doesn't mean default");
        }
        return getFont2D().getFontName(l);
    }

    /**
     * Returns the style of this <code>Font</code>.  The style can be
     * PLAIN, BOLD, ITALIC, or BOLD+ITALIC.
     * <p>
     *  返回此<code> Font </code>的样式。样式可以是PLAIN,BOLD,ITALIC或BOLD + ITALIC
     * 
     * 
     * @return the style of this <code>Font</code>
     * @see #isPlain
     * @see #isBold
     * @see #isItalic
     * @since JDK1.0
     */
    public int getStyle() {
        return style;
    }

    /**
     * Returns the point size of this <code>Font</code>, rounded to
     * an integer.
     * Most users are familiar with the idea of using <i>point size</i> to
     * specify the size of glyphs in a font. This point size defines a
     * measurement between the baseline of one line to the baseline of the
     * following line in a single spaced text document. The point size is
     * based on <i>typographic points</i>, approximately 1/72 of an inch.
     * <p>
     * The Java(tm)2D API adopts the convention that one point is
     * equivalent to one unit in user coordinates.  When using a
     * normalized transform for converting user space coordinates to
     * device space coordinates 72 user
     * space units equal 1 inch in device space.  In this case one point
     * is 1/72 of an inch.
     * <p>
     *  返回这个<code> Font </code>的点大小,舍入为整数大多数用户都熟悉使用<i>点大小</i>来指定字体中字形的大小的想法。
     * 此点大小定义在单个间隔文本文档中一条线的基线与下一条线的基线之间的测量点尺寸基于<i>印刷点</i>,大约1/72英寸。
     * <p>
     * Java(tm)2D API采用约定,一个点等效于用户坐标中的一个单位当使用归一化变换将用户空间坐标转换为设备空间坐标72个用户空间单位在设备空间中等于1英寸在这种情况下,一个点是1/72英寸
     * 
     * 
     * @return the point size of this <code>Font</code> in 1/72 of an
     *          inch units.
     * @see #getSize2D
     * @see GraphicsConfiguration#getDefaultTransform
     * @see GraphicsConfiguration#getNormalizingTransform
     * @since JDK1.0
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the point size of this <code>Font</code> in
     * <code>float</code> value.
     * <p>
     *  返回<code> float </code>值中<code> Font </code>的点大小
     * 
     * 
     * @return the point size of this <code>Font</code> as a
     * <code>float</code> value.
     * @see #getSize
     * @since 1.2
     */
    public float getSize2D() {
        return pointSize;
    }

    /**
     * Indicates whether or not this <code>Font</code> object's style is
     * PLAIN.
     * <p>
     *  指示此<code> Font </code>对象的样式是否为PLAIN
     * 
     * 
     * @return    <code>true</code> if this <code>Font</code> has a
     *            PLAIN style;
     *            <code>false</code> otherwise.
     * @see       java.awt.Font#getStyle
     * @since     JDK1.0
     */
    public boolean isPlain() {
        return style == 0;
    }

    /**
     * Indicates whether or not this <code>Font</code> object's style is
     * BOLD.
     * <p>
     *  指示此<code> Font </code>对象的样式是否为BOLD
     * 
     * 
     * @return    <code>true</code> if this <code>Font</code> object's
     *            style is BOLD;
     *            <code>false</code> otherwise.
     * @see       java.awt.Font#getStyle
     * @since     JDK1.0
     */
    public boolean isBold() {
        return (style & BOLD) != 0;
    }

    /**
     * Indicates whether or not this <code>Font</code> object's style is
     * ITALIC.
     * <p>
     *  指示此<code> Font </code>对象的样式是否为ITALIC
     * 
     * 
     * @return    <code>true</code> if this <code>Font</code> object's
     *            style is ITALIC;
     *            <code>false</code> otherwise.
     * @see       java.awt.Font#getStyle
     * @since     JDK1.0
     */
    public boolean isItalic() {
        return (style & ITALIC) != 0;
    }

    /**
     * Indicates whether or not this <code>Font</code> object has a
     * transform that affects its size in addition to the Size
     * attribute.
     * <p>
     *  指示除了Size属性之外,这个<code> Font </code>对象是否有影响其大小的变换
     * 
     * 
     * @return  <code>true</code> if this <code>Font</code> object
     *          has a non-identity AffineTransform attribute.
     *          <code>false</code> otherwise.
     * @see     java.awt.Font#getTransform
     * @since   1.4
     */
    public boolean isTransformed() {
        return nonIdentityTx;
    }

    /**
     * Return true if this Font contains attributes that require extra
     * layout processing.
     * <p>
     * 如果此字体包含需要额外布局处理的属性,则返回true
     * 
     * 
     * @return true if the font has layout attributes
     * @since 1.6
     */
    public boolean hasLayoutAttributes() {
        return hasLayoutAttributes;
    }

    /**
     * Returns a <code>Font</code> object from the system properties list.
     * <code>nm</code> is treated as the name of a system property to be
     * obtained.  The <code>String</code> value of this property is then
     * interpreted as a <code>Font</code> object according to the
     * specification of <code>Font.decode(String)</code>
     * If the specified property is not found, or the executing code does
     * not have permission to read the property, null is returned instead.
     *
     * <p>
     *  从系统属性列表中返回<code> Font </code>对象<code> nm </code>被视为要获取的系统属性的名称此属性的<code> String </code>值然后根据<code> F
     * ontdecode(String)</code>的规定解释为<code> Font </code>对象如果未找到指定的属性,或执行代码没有读取属性的权限,返回null。
     * 
     * 
     * @param nm the property name
     * @return a <code>Font</code> object that the property name
     *          describes, or null if no such property exists.
     * @throws NullPointerException if nm is null.
     * @since 1.2
     * @see #decode(String)
     */
    public static Font getFont(String nm) {
        return getFont(nm, null);
    }

    /**
     * Returns the <code>Font</code> that the <code>str</code>
     * argument describes.
     * To ensure that this method returns the desired Font,
     * format the <code>str</code> parameter in
     * one of these ways
     *
     * <ul>
     * <li><em>fontname-style-pointsize</em>
     * <li><em>fontname-pointsize</em>
     * <li><em>fontname-style</em>
     * <li><em>fontname</em>
     * <li><em>fontname style pointsize</em>
     * <li><em>fontname pointsize</em>
     * <li><em>fontname style</em>
     * <li><em>fontname</em>
     * </ul>
     * in which <i>style</i> is one of the four
     * case-insensitive strings:
     * <code>"PLAIN"</code>, <code>"BOLD"</code>, <code>"BOLDITALIC"</code>, or
     * <code>"ITALIC"</code>, and pointsize is a positive decimal integer
     * representation of the point size.
     * For example, if you want a font that is Arial, bold, with
     * a point size of 18, you would call this method with:
     * "Arial-BOLD-18".
     * This is equivalent to calling the Font constructor :
     * <code>new Font("Arial", Font.BOLD, 18);</code>
     * and the values are interpreted as specified by that constructor.
     * <p>
     * A valid trailing decimal field is always interpreted as the pointsize.
     * Therefore a fontname containing a trailing decimal value should not
     * be used in the fontname only form.
     * <p>
     * If a style name field is not one of the valid style strings, it is
     * interpreted as part of the font name, and the default style is used.
     * <p>
     * Only one of ' ' or '-' may be used to separate fields in the input.
     * The identified separator is the one closest to the end of the string
     * which separates a valid pointsize, or a valid style name from
     * the rest of the string.
     * Null (empty) pointsize and style fields are treated
     * as valid fields with the default value for that field.
     *<p>
     * Some font names may include the separator characters ' ' or '-'.
     * If <code>str</code> is not formed with 3 components, e.g. such that
     * <code>style</code> or <code>pointsize</code> fields are not present in
     * <code>str</code>, and <code>fontname</code> also contains a
     * character determined to be the separator character
     * then these characters where they appear as intended to be part of
     * <code>fontname</code> may instead be interpreted as separators
     * so the font name may not be properly recognised.
     *
     * <p>
     * The default size is 12 and the default style is PLAIN.
     * If <code>str</code> does not specify a valid size, the returned
     * <code>Font</code> has a size of 12.  If <code>str</code> does not
     * specify a valid style, the returned Font has a style of PLAIN.
     * If you do not specify a valid font name in
     * the <code>str</code> argument, this method will return
     * a font with the family name "Dialog".
     * To determine what font family names are available on
     * your system, use the
     * {@link GraphicsEnvironment#getAvailableFontFamilyNames()} method.
     * If <code>str</code> is <code>null</code>, a new <code>Font</code>
     * is returned with the family name "Dialog", a size of 12 and a
     * PLAIN style.
     * <p>
     *  返回<code> str </code>参数描述的<code> Font </code>要确保此方法返回所需的字体,请以这些方式之一格式化<code> str </code>参数
     * 
     * <ul>
     * <LI>的<em>字体名 - 风格的pointsize </em>的<LI>的<em>字体名-的pointsize </em>的<LI>的<em>字体名风格</em>的<LI>的<em>字体名</EM>
     *  <LI>的<em>字体名风格的pointsize </em>的<LI>的<em>的pointsize字体名</em>的<LI>的<em>字体名风格</em>的<LI>的<em>字体名</em>的。
     * </ul>
     *  在<I>风格</I>是不区分大小写的四串之一：<代码>"普通"</代码>,<代码>"大胆"</代码>,<代码>"BOLDITALIC"</码>或<代码>"斜体"</code>的,并且是的pointsi
     * ze点大小。
     * 例如十进制正整数表示,如果你想要的字体阙是宋体,黑体,18点大小,你会调用此方法："宋体-BOLD-18"这相当于调用字体构造器：<代码>新字体("宋体",FontBold,18); </code>的和
     * 值被解释由阙构造指定。
     * <p>
     * 有效的尾随十进制字段总是被解释为pointsize因此,包含尾随十进制值的字体名称不应该用于仅字体名形式
     * <p>
     *  如果样式名称字段不是有效的样式字符串之一,它将被解释为字体名称的一部分,并使用默认样式
     * <p>
     *  只能使用''或' - '中的一个来分隔输入中的字段。
     * 标识的分隔符是最接近字符串末尾的分隔符,它将有效的pointize或有效的样式名称与字符串的其余部分分隔Null空)pointize和style字段被视为具有该字段的默认值的有效字段。
     * p>
     * 某些字体名称可能包含分隔符"'或' - '如果<code> str </code>没有用3个组件组成,例如<code> style </code>或<code> pointsize </code>字段不
     * 存在于<code> str </code>中,并且<code> fontname </code>也包含确定为分隔符字符的字符,然后这些字符显示为<code> fontname < / code>可能会被
     * 解释为分隔符,因此可能无法正确识别字体名称。
     * 
     * <p>
     * 默认大小为12,默认样式为PLAIN如果<code> str </code>没有指定有效的大小,返回的<code> Font </code>的大小为12如果<code> str </code >未指定有
     * 效的样式,返回的字体具有PLAIN的样式如果未在<code> str </code>参数中指定有效的字体名称,此方法将返回一个字体,其名称为"Dialog"要确定系统上可用的字体系列名称,请使用{@link GraphicsEnvironment#getAvailableFontFamilyNames()}
     * 方法如果<code> str </code>是<code> null </code> / code>返回的名称为"Dialog",大小为12和PLAIN样式。
     * 
     * 
     * @param str the name of the font, or <code>null</code>
     * @return the <code>Font</code> object that <code>str</code>
     *          describes, or a new default <code>Font</code> if
     *          <code>str</code> is <code>null</code>.
     * @see #getFamily
     * @since JDK1.1
     */
    public static Font decode(String str) {
        String fontName = str;
        String styleName = "";
        int fontSize = 12;
        int fontStyle = Font.PLAIN;

        if (str == null) {
            return new Font(DIALOG, fontStyle, fontSize);
        }

        int lastHyphen = str.lastIndexOf('-');
        int lastSpace = str.lastIndexOf(' ');
        char sepChar = (lastHyphen > lastSpace) ? '-' : ' ';
        int sizeIndex = str.lastIndexOf(sepChar);
        int styleIndex = str.lastIndexOf(sepChar, sizeIndex-1);
        int strlen = str.length();

        if (sizeIndex > 0 && sizeIndex+1 < strlen) {
            try {
                fontSize =
                    Integer.valueOf(str.substring(sizeIndex+1)).intValue();
                if (fontSize <= 0) {
                    fontSize = 12;
                }
            } catch (NumberFormatException e) {
                /* It wasn't a valid size, if we didn't also find the
                /* <p>
                /* 
                 * start of the style string perhaps this is the style */
                styleIndex = sizeIndex;
                sizeIndex = strlen;
                if (str.charAt(sizeIndex-1) == sepChar) {
                    sizeIndex--;
                }
            }
        }

        if (styleIndex >= 0 && styleIndex+1 < strlen) {
            styleName = str.substring(styleIndex+1, sizeIndex);
            styleName = styleName.toLowerCase(Locale.ENGLISH);
            if (styleName.equals("bolditalic")) {
                fontStyle = Font.BOLD | Font.ITALIC;
            } else if (styleName.equals("italic")) {
                fontStyle = Font.ITALIC;
            } else if (styleName.equals("bold")) {
                fontStyle = Font.BOLD;
            } else if (styleName.equals("plain")) {
                fontStyle = Font.PLAIN;
            } else {
                /* this string isn't any of the expected styles, so
                 * assume its part of the font name
                 * <p>
                 *  假设其部分字体名称
                 * 
                 */
                styleIndex = sizeIndex;
                if (str.charAt(styleIndex-1) == sepChar) {
                    styleIndex--;
                }
            }
            fontName = str.substring(0, styleIndex);

        } else {
            int fontEnd = strlen;
            if (styleIndex > 0) {
                fontEnd = styleIndex;
            } else if (sizeIndex > 0) {
                fontEnd = sizeIndex;
            }
            if (fontEnd > 0 && str.charAt(fontEnd-1) == sepChar) {
                fontEnd--;
            }
            fontName = str.substring(0, fontEnd);
        }

        return new Font(fontName, fontStyle, fontSize);
    }

    /**
     * Gets the specified <code>Font</code> from the system properties
     * list.  As in the <code>getProperty</code> method of
     * <code>System</code>, the first
     * argument is treated as the name of a system property to be
     * obtained.  The <code>String</code> value of this property is then
     * interpreted as a <code>Font</code> object.
     * <p>
     * The property value should be one of the forms accepted by
     * <code>Font.decode(String)</code>
     * If the specified property is not found, or the executing code does not
     * have permission to read the property, the <code>font</code>
     * argument is returned instead.
     * <p>
     * 从系统属性列表获取指定的<code> Font </code>与<code> System </code>中的<code> getProperty </code>方法一样,第一个参数被视为系统属性的名称
     * 要获取此属性的<code> String </code>值将解释为<code> Font </code>对象。
     * <p>
     *  属性值应该是<code> Fontdecode(String)</code>接受的形式之一。如果未找到指定的属性,或者执行代码没有读取属性的权限,则<code> font < code>参数
     * 
     * 
     * @param nm the case-insensitive property name
     * @param font a default <code>Font</code> to return if property
     *          <code>nm</code> is not defined
     * @return    the <code>Font</code> value of the property.
     * @throws NullPointerException if nm is null.
     * @see #decode(String)
     */
    public static Font getFont(String nm, Font font) {
        String str = null;
        try {
            str =System.getProperty(nm);
        } catch(SecurityException e) {
        }
        if (str == null) {
            return font;
        }
        return decode ( str );
    }

    transient int hash;
    /**
     * Returns a hashcode for this <code>Font</code>.
     * <p>
     *  返回此<code> Font </code>的哈希码
     * 
     * 
     * @return     a hashcode value for this <code>Font</code>.
     * @since      JDK1.0
     */
    public int hashCode() {
        if (hash == 0) {
            hash = name.hashCode() ^ style ^ size;
            /* It is possible many fonts differ only in transform.
             * So include the transform in the hash calculation.
             * nonIdentityTx is set whenever there is a transform in
             * 'values'. The tests for null are required because it can
             * also be set for other reasons.
             * <p>
             * 所以包括在哈希计算中的变换nonIdentityTx在"values"中有变换时设置为null的测试是必需的,因为它也可以设置为其他原因
             * 
             */
            if (nonIdentityTx &&
                values != null && values.getTransform() != null) {
                hash ^= values.getTransform().hashCode();
            }
        }
        return hash;
    }

    /**
     * Compares this <code>Font</code> object to the specified
     * <code>Object</code>.
     * <p>
     *  将此<code> Font </code>对象与指定的<code> Object </code>进行比较
     * 
     * 
     * @param obj the <code>Object</code> to compare
     * @return <code>true</code> if the objects are the same
     *          or if the argument is a <code>Font</code> object
     *          describing the same font as this object;
     *          <code>false</code> otherwise.
     * @since JDK1.0
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj != null) {
            try {
                Font font = (Font)obj;
                if (size == font.size &&
                    style == font.style &&
                    nonIdentityTx == font.nonIdentityTx &&
                    hasLayoutAttributes == font.hasLayoutAttributes &&
                    pointSize == font.pointSize &&
                    name.equals(font.name)) {

                    /* 'values' is usually initialized lazily, except when
                     * the font is constructed from a Map, or derived using
                     * a Map or other values. So if only one font has
                     * the field initialized we need to initialize it in
                     * the other instance and compare.
                     * <p>
                     *  该字体是从一个Map构造的,或者使用一个Map或其他值派生的。所以如果只有一个字体已经初始化了字段,我们需要在另一个实例中初始化它,然后比较
                     * 
                     */
                    if (values == null) {
                        if (font.values == null) {
                            return true;
                        } else {
                            return getAttributeValues().equals(font.values);
                        }
                    } else {
                        return values.equals(font.getAttributeValues());
                    }
                }
            }
            catch (ClassCastException e) {
            }
        }
        return false;
    }

    /**
     * Converts this <code>Font</code> object to a <code>String</code>
     * representation.
     * <p>
     *  将此<code> Font </code>对象转换为<code> String </code>表示形式
     * 
     * 
     * @return     a <code>String</code> representation of this
     *          <code>Font</code> object.
     * @since      JDK1.0
     */
    // NOTE: This method may be called by privileged threads.
    //       DO NOT INVOKE CLIENT CODE ON THIS THREAD!
    public String toString() {
        String  strStyle;

        if (isBold()) {
            strStyle = isItalic() ? "bolditalic" : "bold";
        } else {
            strStyle = isItalic() ? "italic" : "plain";
        }

        return getClass().getName() + "[family=" + getFamily() + ",name=" + name + ",style=" +
            strStyle + ",size=" + size + "]";
    } // toString()


    /** Serialization support.  A <code>readObject</code>
     *  method is neccessary because the constructor creates
     *  the font's peer, and we can't serialize the peer.
     *  Similarly the computed font "family" may be different
     *  at <code>readObject</code> time than at
     *  <code>writeObject</code> time.  An integer version is
     *  written so that future versions of this class will be
     *  able to recognize serialized output from this one.
     * <p>
     * 方法是必要的,因为构造函数创建字体的对等体,并且我们不能序列化对等体类似地,计算的字体"family"在<code> readObject </code>时可能与在<code> writeObject 
     * </code>编写整数版本,以便此类的未来版本能够识别此类的序列化输出。
     * 
     */
    /**
     * The <code>Font</code> Serializable Data Form.
     *
     * <p>
     *  <code>字体</code>可序列化数据表单
     * 
     * 
     * @serial
     */
    private int fontSerializedDataVersion = 1;

    /**
     * Writes default serializable fields to a stream.
     *
     * <p>
     *  将缺省可序列化字段写入流
     * 
     * 
     * @param s the <code>ObjectOutputStream</code> to write
     * @see AWTEventMulticaster#save(ObjectOutputStream, String, EventListener)
     * @see #readObject(java.io.ObjectInputStream)
     */
    private void writeObject(java.io.ObjectOutputStream s)
      throws java.lang.ClassNotFoundException,
             java.io.IOException
    {
        if (values != null) {
          synchronized(values) {
            // transient
            fRequestedAttributes = values.toSerializableHashtable();
            s.defaultWriteObject();
            fRequestedAttributes = null;
          }
        } else {
          s.defaultWriteObject();
        }
    }

    /**
     * Reads the <code>ObjectInputStream</code>.
     * Unrecognized keys or values will be ignored.
     *
     * <p>
     *  读取<code> ObjectInputStream </code>无法识别的键或值将被忽略
     * 
     * 
     * @param s the <code>ObjectInputStream</code> to read
     * @serial
     * @see #writeObject(java.io.ObjectOutputStream)
     */
    private void readObject(java.io.ObjectInputStream s)
      throws java.lang.ClassNotFoundException,
             java.io.IOException
    {
        s.defaultReadObject();
        if (pointSize == 0) {
            pointSize = (float)size;
        }

        // Handle fRequestedAttributes.
        // in 1.5, we always streamed out the font values plus
        // TRANSFORM, SUPERSCRIPT, and WIDTH, regardless of whether the
        // values were default or not.  In 1.6 we only stream out
        // defined values.  So, 1.6 streams in from a 1.5 stream,
        // it check each of these values and 'undefines' it if the
        // value is the default.

        if (fRequestedAttributes != null) {
            values = getAttributeValues(); // init
            AttributeValues extras =
                AttributeValues.fromSerializableHashtable(fRequestedAttributes);
            if (!AttributeValues.is16Hashtable(fRequestedAttributes)) {
                extras.unsetDefault(); // if legacy stream, undefine these
            }
            values = getAttributeValues().merge(extras);
            this.nonIdentityTx = values.anyNonDefault(EXTRA_MASK);
            this.hasLayoutAttributes =  values.anyNonDefault(LAYOUT_MASK);

            fRequestedAttributes = null; // don't need it any more
        }
    }

    /**
     * Returns the number of glyphs in this <code>Font</code>. Glyph codes
     * for this <code>Font</code> range from 0 to
     * <code>getNumGlyphs()</code> - 1.
     * <p>
     *  返回此<code> Font </code>字形代码中此字符</code>范围从0到<code>的字形数量getNumGlyphs()</code>  -  1
     * 
     * 
     * @return the number of glyphs in this <code>Font</code>.
     * @since 1.2
     */
    public int getNumGlyphs() {
        return  getFont2D().getNumGlyphs();
    }

    /**
     * Returns the glyphCode which is used when this <code>Font</code>
     * does not have a glyph for a specified unicode code point.
     * <p>
     * 返回当此<code> Font </code>没有指定Unicode代码点的字形时使用的glyphCode
     * 
     * 
     * @return the glyphCode of this <code>Font</code>.
     * @since 1.2
     */
    public int getMissingGlyphCode() {
        return getFont2D().getMissingGlyphCode();
    }

    /**
     * Returns the baseline appropriate for displaying this character.
     * <p>
     * Large fonts can support different writing systems, and each system can
     * use a different baseline.
     * The character argument determines the writing system to use. Clients
     * should not assume all characters use the same baseline.
     *
     * <p>
     *  返回适合显示此字符的基线
     * <p>
     *  大字体可以支持不同的写入系统,每个系统可以使用不同的基线字符参数决定要使用的写入系统客户端不应该假定所有字符使用相同的基线
     * 
     * 
     * @param c a character used to identify the writing system
     * @return the baseline appropriate for the specified character.
     * @see LineMetrics#getBaselineOffsets
     * @see #ROMAN_BASELINE
     * @see #CENTER_BASELINE
     * @see #HANGING_BASELINE
     * @since 1.2
     */
    public byte getBaselineFor(char c) {
        return getFont2D().getBaselineFor(c);
    }

    /**
     * Returns a map of font attributes available in this
     * <code>Font</code>.  Attributes include things like ligatures and
     * glyph substitution.
     * <p>
     *  返回此<code> Font </code>中可用的字体属性的地图属性包括连字和字形替换等
     * 
     * 
     * @return the attributes map of this <code>Font</code>.
     */
    public Map<TextAttribute,?> getAttributes(){
        return new AttributeMap(getAttributeValues());
    }

    /**
     * Returns the keys of all the attributes supported by this
     * <code>Font</code>.  These attributes can be used to derive other
     * fonts.
     * <p>
     *  返回此<code> Font </code>支持的所有属性的键。这些属性可用于导出其他字体
     * 
     * 
     * @return an array containing the keys of all the attributes
     *          supported by this <code>Font</code>.
     * @since 1.2
     */
    public Attribute[] getAvailableAttributes() {
        // FONT is not supported by Font

        Attribute attributes[] = {
            TextAttribute.FAMILY,
            TextAttribute.WEIGHT,
            TextAttribute.WIDTH,
            TextAttribute.POSTURE,
            TextAttribute.SIZE,
            TextAttribute.TRANSFORM,
            TextAttribute.SUPERSCRIPT,
            TextAttribute.CHAR_REPLACEMENT,
            TextAttribute.FOREGROUND,
            TextAttribute.BACKGROUND,
            TextAttribute.UNDERLINE,
            TextAttribute.STRIKETHROUGH,
            TextAttribute.RUN_DIRECTION,
            TextAttribute.BIDI_EMBEDDING,
            TextAttribute.JUSTIFICATION,
            TextAttribute.INPUT_METHOD_HIGHLIGHT,
            TextAttribute.INPUT_METHOD_UNDERLINE,
            TextAttribute.SWAP_COLORS,
            TextAttribute.NUMERIC_SHAPING,
            TextAttribute.KERNING,
            TextAttribute.LIGATURES,
            TextAttribute.TRACKING,
        };

        return attributes;
    }

    /**
     * Creates a new <code>Font</code> object by replicating this
     * <code>Font</code> object and applying a new style and size.
     * <p>
     *  通过复制此<code> Font </code>对象并应用新的样式和大小创建一个新的<code> Font </code>
     * 
     * 
     * @param style the style for the new <code>Font</code>
     * @param size the size for the new <code>Font</code>
     * @return a new <code>Font</code> object.
     * @since 1.2
     */
    public Font deriveFont(int style, float size){
        if (values == null) {
            return new Font(name, style, size, createdFont, font2DHandle);
        }
        AttributeValues newValues = getAttributeValues().clone();
        int oldStyle = (this.style != style) ? this.style : -1;
        applyStyle(style, newValues);
        newValues.setSize(size);
        return new Font(newValues, null, oldStyle, createdFont, font2DHandle);
    }

    /**
     * Creates a new <code>Font</code> object by replicating this
     * <code>Font</code> object and applying a new style and transform.
     * <p>
     * 通过复制此<code> Font </code>对象并应用新的样式和变换来创建新的<code> Font </code>
     * 
     * 
     * @param style the style for the new <code>Font</code>
     * @param trans the <code>AffineTransform</code> associated with the
     * new <code>Font</code>
     * @return a new <code>Font</code> object.
     * @throws IllegalArgumentException if <code>trans</code> is
     *         <code>null</code>
     * @since 1.2
     */
    public Font deriveFont(int style, AffineTransform trans){
        AttributeValues newValues = getAttributeValues().clone();
        int oldStyle = (this.style != style) ? this.style : -1;
        applyStyle(style, newValues);
        applyTransform(trans, newValues);
        return new Font(newValues, null, oldStyle, createdFont, font2DHandle);
    }

    /**
     * Creates a new <code>Font</code> object by replicating the current
     * <code>Font</code> object and applying a new size to it.
     * <p>
     *  通过复制当前的<code> Font </code>对象并应用新的大小创建一个新的<code> Font </code>对象
     * 
     * 
     * @param size the size for the new <code>Font</code>.
     * @return a new <code>Font</code> object.
     * @since 1.2
     */
    public Font deriveFont(float size){
        if (values == null) {
            return new Font(name, style, size, createdFont, font2DHandle);
        }
        AttributeValues newValues = getAttributeValues().clone();
        newValues.setSize(size);
        return new Font(newValues, null, -1, createdFont, font2DHandle);
    }

    /**
     * Creates a new <code>Font</code> object by replicating the current
     * <code>Font</code> object and applying a new transform to it.
     * <p>
     *  通过复制当前<code> Font </code>对象并对其应用新的变换来创建新的<code> Font </code>对象
     * 
     * 
     * @param trans the <code>AffineTransform</code> associated with the
     * new <code>Font</code>
     * @return a new <code>Font</code> object.
     * @throws IllegalArgumentException if <code>trans</code> is
     *         <code>null</code>
     * @since 1.2
     */
    public Font deriveFont(AffineTransform trans){
        AttributeValues newValues = getAttributeValues().clone();
        applyTransform(trans, newValues);
        return new Font(newValues, null, -1, createdFont, font2DHandle);
    }

    /**
     * Creates a new <code>Font</code> object by replicating the current
     * <code>Font</code> object and applying a new style to it.
     * <p>
     *  通过复制当前<code> Font </code>对象并对其应用新样式来创建新的<code> Font </code>对象
     * 
     * 
     * @param style the style for the new <code>Font</code>
     * @return a new <code>Font</code> object.
     * @since 1.2
     */
    public Font deriveFont(int style){
        if (values == null) {
           return new Font(name, style, size, createdFont, font2DHandle);
        }
        AttributeValues newValues = getAttributeValues().clone();
        int oldStyle = (this.style != style) ? this.style : -1;
        applyStyle(style, newValues);
        return new Font(newValues, null, oldStyle, createdFont, font2DHandle);
    }

    /**
     * Creates a new <code>Font</code> object by replicating the current
     * <code>Font</code> object and applying a new set of font attributes
     * to it.
     *
     * <p>
     *  通过复制当前<code> Font </code>对象并应用一组新的字体属性来创建一个新的<code> Font </code>对象
     * 
     * 
     * @param attributes a map of attributes enabled for the new
     * <code>Font</code>
     * @return a new <code>Font</code> object.
     * @since 1.2
     */
    public Font deriveFont(Map<? extends Attribute, ?> attributes) {
        if (attributes == null) {
            return this;
        }
        AttributeValues newValues = getAttributeValues().clone();
        newValues.merge(attributes, RECOGNIZED_MASK);

        return new Font(newValues, name, style, createdFont, font2DHandle);
    }

    /**
     * Checks if this <code>Font</code> has a glyph for the specified
     * character.
     *
     * <p> <b>Note:</b> This method cannot handle <a
     * href="../../java/lang/Character.html#supplementary"> supplementary
     * characters</a>. To support all Unicode characters, including
     * supplementary characters, use the {@link #canDisplay(int)}
     * method or <code>canDisplayUpTo</code> methods.
     *
     * <p>
     *  检查此<code> Font </code>是否具有指定字符的字形
     * 
     * <p> <b>注意：</b>此方法无法处理<a href=\"//java/lang/Characterhtml#supplementary\">补充字符</a>要支持所有Unicode字符(包括补充字
     * 符),请使用{@link #canDisplay(int)}方法或<code> canDisplayUpTo </code>方法。
     * 
     * 
     * @param c the character for which a glyph is needed
     * @return <code>true</code> if this <code>Font</code> has a glyph for this
     *          character; <code>false</code> otherwise.
     * @since 1.2
     */
    public boolean canDisplay(char c){
        return getFont2D().canDisplay(c);
    }

    /**
     * Checks if this <code>Font</code> has a glyph for the specified
     * character.
     *
     * <p>
     *  检查此<code> Font </code>是否具有指定字符的字形
     * 
     * 
     * @param codePoint the character (Unicode code point) for which a glyph
     *        is needed.
     * @return <code>true</code> if this <code>Font</code> has a glyph for the
     *          character; <code>false</code> otherwise.
     * @throws IllegalArgumentException if the code point is not a valid Unicode
     *          code point.
     * @see Character#isValidCodePoint(int)
     * @since 1.5
     */
    public boolean canDisplay(int codePoint) {
        if (!Character.isValidCodePoint(codePoint)) {
            throw new IllegalArgumentException("invalid code point: " +
                                               Integer.toHexString(codePoint));
        }
        return getFont2D().canDisplay(codePoint);
    }

    /**
     * Indicates whether or not this <code>Font</code> can display a
     * specified <code>String</code>.  For strings with Unicode encoding,
     * it is important to know if a particular font can display the
     * string. This method returns an offset into the <code>String</code>
     * <code>str</code> which is the first character this
     * <code>Font</code> cannot display without using the missing glyph
     * code. If the <code>Font</code> can display all characters, -1 is
     * returned.
     * <p>
     * 指示此<code> Font </code>是否可以显示指定的<code> String </code>对于具有Unicode编码的字符串,重要的是要知道特定字体是否可以显示字符串This方法返回一个偏
     * 移量<code> Font </code>在不使用缺少的字形代码的情况下无法显示的第一个字符</code> <code> str </code>如果<code> Font </code>显示所有字符,返
     * 回-1。
     * 
     * 
     * @param str a <code>String</code> object
     * @return an offset into <code>str</code> that points
     *          to the first character in <code>str</code> that this
     *          <code>Font</code> cannot display; or <code>-1</code> if
     *          this <code>Font</code> can display all characters in
     *          <code>str</code>.
     * @since 1.2
     */
    public int canDisplayUpTo(String str) {
        Font2D font2d = getFont2D();
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (font2d.canDisplay(c)) {
                continue;
            }
            if (!Character.isHighSurrogate(c)) {
                return i;
            }
            if (!font2d.canDisplay(str.codePointAt(i))) {
                return i;
            }
            i++;
        }
        return -1;
    }

    /**
     * Indicates whether or not this <code>Font</code> can display
     * the characters in the specified <code>text</code>
     * starting at <code>start</code> and ending at
     * <code>limit</code>.  This method is a convenience overload.
     * <p>
     *  指示此<code> Font </code>是否可以显示从<code>开始</code>开始并以<code> limit </code>结束的指定<code>文本</code>中的字符。
     * 方法是一种方便的重载。
     * 
     * 
     * @param text the specified array of <code>char</code> values
     * @param start the specified starting offset (in
     *              <code>char</code>s) into the specified array of
     *              <code>char</code> values
     * @param limit the specified ending offset (in
     *              <code>char</code>s) into the specified array of
     *              <code>char</code> values
     * @return an offset into <code>text</code> that points
     *          to the first character in <code>text</code> that this
     *          <code>Font</code> cannot display; or <code>-1</code> if
     *          this <code>Font</code> can display all characters in
     *          <code>text</code>.
     * @since 1.2
     */
    public int canDisplayUpTo(char[] text, int start, int limit) {
        Font2D font2d = getFont2D();
        for (int i = start; i < limit; i++) {
            char c = text[i];
            if (font2d.canDisplay(c)) {
                continue;
            }
            if (!Character.isHighSurrogate(c)) {
                return i;
            }
            if (!font2d.canDisplay(Character.codePointAt(text, i, limit))) {
                return i;
            }
            i++;
        }
        return -1;
    }

    /**
     * Indicates whether or not this <code>Font</code> can display the
     * text specified by the <code>iter</code> starting at
     * <code>start</code> and ending at <code>limit</code>.
     *
     * <p>
     * 指示此<code> Font </code>是否可以显示从<code>开始</code>开始并以<code> limit </code>结束的<code> iter </code>
     * 
     * 
     * @param iter  a {@link CharacterIterator} object
     * @param start the specified starting offset into the specified
     *              <code>CharacterIterator</code>.
     * @param limit the specified ending offset into the specified
     *              <code>CharacterIterator</code>.
     * @return an offset into <code>iter</code> that points
     *          to the first character in <code>iter</code> that this
     *          <code>Font</code> cannot display; or <code>-1</code> if
     *          this <code>Font</code> can display all characters in
     *          <code>iter</code>.
     * @since 1.2
     */
    public int canDisplayUpTo(CharacterIterator iter, int start, int limit) {
        Font2D font2d = getFont2D();
        char c = iter.setIndex(start);
        for (int i = start; i < limit; i++, c = iter.next()) {
            if (font2d.canDisplay(c)) {
                continue;
            }
            if (!Character.isHighSurrogate(c)) {
                return i;
            }
            char c2 = iter.next();
            // c2 could be CharacterIterator.DONE which is not a low surrogate.
            if (!Character.isLowSurrogate(c2)) {
                return i;
            }
            if (!font2d.canDisplay(Character.toCodePoint(c, c2))) {
                return i;
            }
            i++;
        }
        return -1;
    }

    /**
     * Returns the italic angle of this <code>Font</code>.  The italic angle
     * is the inverse slope of the caret which best matches the posture of this
     * <code>Font</code>.
     * <p>
     *  返回此<code> Font </code>的斜体角。斜体角是与此<code> Font </code>的姿势最匹配的插入符号的反斜率。
     * 
     * 
     * @see TextAttribute#POSTURE
     * @return the angle of the ITALIC style of this <code>Font</code>.
     */
    public float getItalicAngle() {
        return getItalicAngle(null);
    }

    /* The FRC hints don't affect the value of the italic angle but
     * we need to pass them in to look up a strike.
     * If we can pass in ones already being used it can prevent an extra
     * strike from being allocated. Note that since italic angle is
     * a property of the font, the font transform is needed not the
     * device transform. Finally, this is private but the only caller of this
     * in the JDK - and the only likely caller - is in this same class.
     * <p>
     *  我们需要传递它们以查找警告如果我们可以传递已经被使用的它可以防止额外的警告被分配注意,由于斜体角是字体的属性,字体变换不需要设备变换最后,这是私有的,但在JDK中唯一的调用者 - 和唯一可能的调用者 
     * - 在同一个类。
     * 
     */
    private float getItalicAngle(FontRenderContext frc) {
        Object aa, fm;
        if (frc == null) {
            aa = RenderingHints.VALUE_TEXT_ANTIALIAS_OFF;
            fm = RenderingHints.VALUE_FRACTIONALMETRICS_OFF;
        } else {
            aa = frc.getAntiAliasingHint();
            fm = frc.getFractionalMetricsHint();
        }
        return getFont2D().getItalicAngle(this, identityTx, aa, fm);
    }

    /**
     * Checks whether or not this <code>Font</code> has uniform
     * line metrics.  A logical <code>Font</code> might be a
     * composite font, which means that it is composed of different
     * physical fonts to cover different code ranges.  Each of these
     * fonts might have different <code>LineMetrics</code>.  If the
     * logical <code>Font</code> is a single
     * font then the metrics would be uniform.
     * <p>
     * 检查此<code> Font </code>是否具有统一的线度量逻辑<code> Font </code>可能是一个复合字体,这意味着它由不同的物理字体组成,这些字体可能有不同的<code> LineM
     * etrics </code>如果逻辑<code> Font </code>是一个单一的字体,那么度量将是统一的。
     * 
     * 
     * @return <code>true</code> if this <code>Font</code> has
     * uniform line metrics; <code>false</code> otherwise.
     */
    public boolean hasUniformLineMetrics() {
        return false;   // REMIND always safe, but prevents caller optimize
    }

    private transient SoftReference<FontLineMetrics> flmref;
    private FontLineMetrics defaultLineMetrics(FontRenderContext frc) {
        FontLineMetrics flm = null;
        if (flmref == null
            || (flm = flmref.get()) == null
            || !flm.frc.equals(frc)) {

            /* The device transform in the frc is not used in obtaining line
             * metrics, although it probably should be: REMIND find why not?
             * The font transform is used but its applied in getFontMetrics, so
             * just pass identity here
             * <p>
             *  指标,虽然可能应该是：REMIND找到为什么不?使用字体变换,但它应用于getFontMetrics,所以只是在这里传递身份
             * 
             */
            float [] metrics = new float[8];
            getFont2D().getFontMetrics(this, identityTx,
                                       frc.getAntiAliasingHint(),
                                       frc.getFractionalMetricsHint(),
                                       metrics);
            float ascent  = metrics[0];
            float descent = metrics[1];
            float leading = metrics[2];
            float ssOffset = 0;
            if (values != null && values.getSuperscript() != 0) {
                ssOffset = (float)getTransform().getTranslateY();
                ascent -= ssOffset;
                descent += ssOffset;
            }
            float height = ascent + descent + leading;

            int baselineIndex = 0; // need real index, assumes roman for everything
            // need real baselines eventually
            float[] baselineOffsets = { 0, (descent/2f - ascent) / 2f, -ascent };

            float strikethroughOffset = metrics[4];
            float strikethroughThickness = metrics[5];

            float underlineOffset = metrics[6];
            float underlineThickness = metrics[7];

            float italicAngle = getItalicAngle(frc);

            if (isTransformed()) {
                AffineTransform ctx = values.getCharTransform(); // extract rotation
                if (ctx != null) {
                    Point2D.Float pt = new Point2D.Float();
                    pt.setLocation(0, strikethroughOffset);
                    ctx.deltaTransform(pt, pt);
                    strikethroughOffset = pt.y;
                    pt.setLocation(0, strikethroughThickness);
                    ctx.deltaTransform(pt, pt);
                    strikethroughThickness = pt.y;
                    pt.setLocation(0, underlineOffset);
                    ctx.deltaTransform(pt, pt);
                    underlineOffset = pt.y;
                    pt.setLocation(0, underlineThickness);
                    ctx.deltaTransform(pt, pt);
                    underlineThickness = pt.y;
                }
            }
            strikethroughOffset += ssOffset;
            underlineOffset += ssOffset;

            CoreMetrics cm = new CoreMetrics(ascent, descent, leading, height,
                                             baselineIndex, baselineOffsets,
                                             strikethroughOffset, strikethroughThickness,
                                             underlineOffset, underlineThickness,
                                             ssOffset, italicAngle);

            flm = new FontLineMetrics(0, cm, frc);
            flmref = new SoftReference<FontLineMetrics>(flm);
        }

        return (FontLineMetrics)flm.clone();
    }

    /**
     * Returns a {@link LineMetrics} object created with the specified
     * <code>String</code> and {@link FontRenderContext}.
     * <p>
     *  返回使用指定的<code> String </code>和{@link FontRenderContext}创建的{@link LineMetrics}
     * 
     * 
     * @param str the specified <code>String</code>
     * @param frc the specified <code>FontRenderContext</code>
     * @return a <code>LineMetrics</code> object created with the
     * specified <code>String</code> and {@link FontRenderContext}.
     */
    public LineMetrics getLineMetrics( String str, FontRenderContext frc) {
        FontLineMetrics flm = defaultLineMetrics(frc);
        flm.numchars = str.length();
        return flm;
    }

    /**
     * Returns a <code>LineMetrics</code> object created with the
     * specified arguments.
     * <p>
     *  返回使用指定参数创建的<code> LineMetrics </code>对象
     * 
     * 
     * @param str the specified <code>String</code>
     * @param beginIndex the initial offset of <code>str</code>
     * @param limit the end offset of <code>str</code>
     * @param frc the specified <code>FontRenderContext</code>
     * @return a <code>LineMetrics</code> object created with the
     * specified arguments.
     */
    public LineMetrics getLineMetrics( String str,
                                    int beginIndex, int limit,
                                    FontRenderContext frc) {
        FontLineMetrics flm = defaultLineMetrics(frc);
        int numChars = limit - beginIndex;
        flm.numchars = (numChars < 0)? 0: numChars;
        return flm;
    }

    /**
     * Returns a <code>LineMetrics</code> object created with the
     * specified arguments.
     * <p>
     * 返回使用指定参数创建的<code> LineMetrics </code>对象
     * 
     * 
     * @param chars an array of characters
     * @param beginIndex the initial offset of <code>chars</code>
     * @param limit the end offset of <code>chars</code>
     * @param frc the specified <code>FontRenderContext</code>
     * @return a <code>LineMetrics</code> object created with the
     * specified arguments.
     */
    public LineMetrics getLineMetrics(char [] chars,
                                    int beginIndex, int limit,
                                    FontRenderContext frc) {
        FontLineMetrics flm = defaultLineMetrics(frc);
        int numChars = limit - beginIndex;
        flm.numchars = (numChars < 0)? 0: numChars;
        return flm;
    }

    /**
     * Returns a <code>LineMetrics</code> object created with the
     * specified arguments.
     * <p>
     *  返回使用指定参数创建的<code> LineMetrics </code>对象
     * 
     * 
     * @param ci the specified <code>CharacterIterator</code>
     * @param beginIndex the initial offset in <code>ci</code>
     * @param limit the end offset of <code>ci</code>
     * @param frc the specified <code>FontRenderContext</code>
     * @return a <code>LineMetrics</code> object created with the
     * specified arguments.
     */
    public LineMetrics getLineMetrics(CharacterIterator ci,
                                    int beginIndex, int limit,
                                    FontRenderContext frc) {
        FontLineMetrics flm = defaultLineMetrics(frc);
        int numChars = limit - beginIndex;
        flm.numchars = (numChars < 0)? 0: numChars;
        return flm;
    }

    /**
     * Returns the logical bounds of the specified <code>String</code> in
     * the specified <code>FontRenderContext</code>.  The logical bounds
     * contains the origin, ascent, advance, and height, which includes
     * the leading.  The logical bounds does not always enclose all the
     * text.  For example, in some languages and in some fonts, accent
     * marks can be positioned above the ascent or below the descent.
     * To obtain a visual bounding box, which encloses all the text,
     * use the {@link TextLayout#getBounds() getBounds} method of
     * <code>TextLayout</code>.
     * <p>Note: The returned bounds is in baseline-relative coordinates
     * (see {@link java.awt.Font class notes}).
     * <p>
     * 返回指定<code> FontRenderContext </code>中指定<code> String </code>的逻辑边界逻辑边界包含origin,ascent,advance和height,其
     * 中包括前导逻辑边界不总是包含所有文本例如,在某些语言和某些字体中,重音标记可以位于上升或下降下方。
     * 要获得一个包含所有文本的可视边界框,请使用{@link TextLayout#getBounds()注意：返回的边界在基线相对坐标中(参见{@link javaawtFont类注释})。
     * 
     * 
     * @param str the specified <code>String</code>
     * @param frc the specified <code>FontRenderContext</code>
     * @return a {@link Rectangle2D} that is the bounding box of the
     * specified <code>String</code> in the specified
     * <code>FontRenderContext</code>.
     * @see FontRenderContext
     * @see Font#createGlyphVector
     * @since 1.2
     */
    public Rectangle2D getStringBounds( String str, FontRenderContext frc) {
        char[] array = str.toCharArray();
        return getStringBounds(array, 0, array.length, frc);
    }

   /**
     * Returns the logical bounds of the specified <code>String</code> in
     * the specified <code>FontRenderContext</code>.  The logical bounds
     * contains the origin, ascent, advance, and height, which includes
     * the leading.  The logical bounds does not always enclose all the
     * text.  For example, in some languages and in some fonts, accent
     * marks can be positioned above the ascent or below the descent.
     * To obtain a visual bounding box, which encloses all the text,
     * use the {@link TextLayout#getBounds() getBounds} method of
     * <code>TextLayout</code>.
     * <p>Note: The returned bounds is in baseline-relative coordinates
     * (see {@link java.awt.Font class notes}).
     * <p>
     * 返回指定<code> FontRenderContext </code>中指定<code> String </code>的逻辑边界逻辑边界包含origin,ascent,advance和height,其
     * 中包括前导逻辑边界不总是包含所有文本例如,在某些语言和某些字体中,重音标记可以位于上升或下降下方。
     * 要获得一个包含所有文本的可视边界框,请使用{@link TextLayout#getBounds()注意：返回的边界在基线相对坐标中(参见{@link javaawtFont类注释})。
     * 
     * 
     * @param str the specified <code>String</code>
     * @param beginIndex the initial offset of <code>str</code>
     * @param limit the end offset of <code>str</code>
     * @param frc the specified <code>FontRenderContext</code>
     * @return a <code>Rectangle2D</code> that is the bounding box of the
     * specified <code>String</code> in the specified
     * <code>FontRenderContext</code>.
     * @throws IndexOutOfBoundsException if <code>beginIndex</code> is
     *         less than zero, or <code>limit</code> is greater than the
     *         length of <code>str</code>, or <code>beginIndex</code>
     *         is greater than <code>limit</code>.
     * @see FontRenderContext
     * @see Font#createGlyphVector
     * @since 1.2
     */
    public Rectangle2D getStringBounds( String str,
                                    int beginIndex, int limit,
                                        FontRenderContext frc) {
        String substr = str.substring(beginIndex, limit);
        return getStringBounds(substr, frc);
    }

   /**
     * Returns the logical bounds of the specified array of characters
     * in the specified <code>FontRenderContext</code>.  The logical
     * bounds contains the origin, ascent, advance, and height, which
     * includes the leading.  The logical bounds does not always enclose
     * all the text.  For example, in some languages and in some fonts,
     * accent marks can be positioned above the ascent or below the
     * descent.  To obtain a visual bounding box, which encloses all the
     * text, use the {@link TextLayout#getBounds() getBounds} method of
     * <code>TextLayout</code>.
     * <p>Note: The returned bounds is in baseline-relative coordinates
     * (see {@link java.awt.Font class notes}).
     * <p>
     * 返回指定的<code> FontRenderContext </code>中指定的字符数组的逻辑边界逻辑边界包含原点,上升,前进和高度,其中包括前导逻辑边界不会始终包含所有文本。
     * 例如,在某些语言和一些字体中,重音标记可以位于上升或下降下方。
     * 要获得一个包含所有文本的视觉边界框,请使用{@link TextLayout#getBounds()getBounds}代码> TextLayout </code> <p>注意：返回的边界在基线相对坐标
     * 中(参见{@link javaawtFont类注释})。
     * 例如,在某些语言和一些字体中,重音标记可以位于上升或下降下方。
     * 
     * 
     * @param chars an array of characters
     * @param beginIndex the initial offset in the array of
     * characters
     * @param limit the end offset in the array of characters
     * @param frc the specified <code>FontRenderContext</code>
     * @return a <code>Rectangle2D</code> that is the bounding box of the
     * specified array of characters in the specified
     * <code>FontRenderContext</code>.
     * @throws IndexOutOfBoundsException if <code>beginIndex</code> is
     *         less than zero, or <code>limit</code> is greater than the
     *         length of <code>chars</code>, or <code>beginIndex</code>
     *         is greater than <code>limit</code>.
     * @see FontRenderContext
     * @see Font#createGlyphVector
     * @since 1.2
     */
    public Rectangle2D getStringBounds(char [] chars,
                                    int beginIndex, int limit,
                                       FontRenderContext frc) {
        if (beginIndex < 0) {
            throw new IndexOutOfBoundsException("beginIndex: " + beginIndex);
        }
        if (limit > chars.length) {
            throw new IndexOutOfBoundsException("limit: " + limit);
        }
        if (beginIndex > limit) {
            throw new IndexOutOfBoundsException("range length: " +
                                                (limit - beginIndex));
        }

        // this code should be in textlayout
        // quick check for simple text, assume GV ok to use if simple

        boolean simple = values == null ||
            (values.getKerning() == 0 && values.getLigatures() == 0 &&
              values.getBaselineTransform() == null);
        if (simple) {
            simple = ! FontUtilities.isComplexText(chars, beginIndex, limit);
        }

        if (simple) {
            GlyphVector gv = new StandardGlyphVector(this, chars, beginIndex,
                                                     limit - beginIndex, frc);
            return gv.getLogicalBounds();
        } else {
            // need char array constructor on textlayout
            String str = new String(chars, beginIndex, limit - beginIndex);
            TextLayout tl = new TextLayout(str, this, frc);
            return new Rectangle2D.Float(0, -tl.getAscent(), tl.getAdvance(),
                                         tl.getAscent() + tl.getDescent() +
                                         tl.getLeading());
        }
    }

   /**
     * Returns the logical bounds of the characters indexed in the
     * specified {@link CharacterIterator} in the
     * specified <code>FontRenderContext</code>.  The logical bounds
     * contains the origin, ascent, advance, and height, which includes
     * the leading.  The logical bounds does not always enclose all the
     * text.  For example, in some languages and in some fonts, accent
     * marks can be positioned above the ascent or below the descent.
     * To obtain a visual bounding box, which encloses all the text,
     * use the {@link TextLayout#getBounds() getBounds} method of
     * <code>TextLayout</code>.
     * <p>Note: The returned bounds is in baseline-relative coordinates
     * (see {@link java.awt.Font class notes}).
     * <p>
     * 返回在指定的<code> FontRenderContext </code>中指定的{@link CharacterIterator}中索引的字符的逻辑边界逻辑边界包含起点,上升,前进和高度,其中包括前
     * 导逻辑边界不始终包含所有文本例如,在某些语言和某些字体中,重音标记可以位于上升或下降下方要获得一个包含所有文本的视觉边界框,请使用{@link TextLayout#getBounds )getBounds}
     * 方法<code> TextLayout </code> <p>注意：返回的边界在基线相对坐标中(参见{@link javaawtFont类注释})。
     * 
     * 
     * @param ci the specified <code>CharacterIterator</code>
     * @param beginIndex the initial offset in <code>ci</code>
     * @param limit the end offset in <code>ci</code>
     * @param frc the specified <code>FontRenderContext</code>
     * @return a <code>Rectangle2D</code> that is the bounding box of the
     * characters indexed in the specified <code>CharacterIterator</code>
     * in the specified <code>FontRenderContext</code>.
     * @see FontRenderContext
     * @see Font#createGlyphVector
     * @since 1.2
     * @throws IndexOutOfBoundsException if <code>beginIndex</code> is
     *         less than the start index of <code>ci</code>, or
     *         <code>limit</code> is greater than the end index of
     *         <code>ci</code>, or <code>beginIndex</code> is greater
     *         than <code>limit</code>
     */
    public Rectangle2D getStringBounds(CharacterIterator ci,
                                    int beginIndex, int limit,
                                       FontRenderContext frc) {
        int start = ci.getBeginIndex();
        int end = ci.getEndIndex();

        if (beginIndex < start) {
            throw new IndexOutOfBoundsException("beginIndex: " + beginIndex);
        }
        if (limit > end) {
            throw new IndexOutOfBoundsException("limit: " + limit);
        }
        if (beginIndex > limit) {
            throw new IndexOutOfBoundsException("range length: " +
                                                (limit - beginIndex));
        }

        char[]  arr = new char[limit - beginIndex];

        ci.setIndex(beginIndex);
        for(int idx = 0; idx < arr.length; idx++) {
            arr[idx] = ci.current();
            ci.next();
        }

        return getStringBounds(arr,0,arr.length,frc);
    }

    /**
     * Returns the bounds for the character with the maximum
     * bounds as defined in the specified <code>FontRenderContext</code>.
     * <p>Note: The returned bounds is in baseline-relative coordinates
     * (see {@link java.awt.Font class notes}).
     * <p>
     * 返回具有指定的<code> FontRenderContext </code> <p>中定义的最大边界的字符的边界注意：返回的边界在基线相对坐标中(参见{@link javaawtFont类注释})
     * 
     * 
     * @param frc the specified <code>FontRenderContext</code>
     * @return a <code>Rectangle2D</code> that is the bounding box
     * for the character with the maximum bounds.
     */
    public Rectangle2D getMaxCharBounds(FontRenderContext frc) {
        float [] metrics = new float[4];

        getFont2D().getFontMetrics(this, frc, metrics);

        return new Rectangle2D.Float(0, -metrics[0],
                                metrics[3],
                                metrics[0] + metrics[1] + metrics[2]);
    }

    /**
     * Creates a {@link java.awt.font.GlyphVector GlyphVector} by
     * mapping characters to glyphs one-to-one based on the
     * Unicode cmap in this <code>Font</code>.  This method does no other
     * processing besides the mapping of glyphs to characters.  This
     * means that this method is not useful for some scripts, such
     * as Arabic, Hebrew, Thai, and Indic, that require reordering,
     * shaping, or ligature substitution.
     * <p>
     *  通过在<code> Font </code>中基于Unicode cmap将字符映射到字形,创建一个{@link javaawtfontGlyphVector GlyphVector}此方法除了将字形
     * 映射到字符之外不进行其他处理这意味着方法对于需要重新排序,整形或连字替代的一些脚本(如阿拉伯语,希伯来语,泰语和印度语)不是有用的。
     * 
     * 
     * @param frc the specified <code>FontRenderContext</code>
     * @param str the specified <code>String</code>
     * @return a new <code>GlyphVector</code> created with the
     * specified <code>String</code> and the specified
     * <code>FontRenderContext</code>.
     */
    public GlyphVector createGlyphVector(FontRenderContext frc, String str)
    {
        return (GlyphVector)new StandardGlyphVector(this, str, frc);
    }

    /**
     * Creates a {@link java.awt.font.GlyphVector GlyphVector} by
     * mapping characters to glyphs one-to-one based on the
     * Unicode cmap in this <code>Font</code>.  This method does no other
     * processing besides the mapping of glyphs to characters.  This
     * means that this method is not useful for some scripts, such
     * as Arabic, Hebrew, Thai, and Indic, that require reordering,
     * shaping, or ligature substitution.
     * <p>
     * 通过在<code> Font </code>中基于Unicode cmap将字符映射到字形,创建一个{@link javaawtfontGlyphVector GlyphVector}此方法除了将字形映
     * 射到字符之外不进行其他处理这意味着方法对于需要重新排序,整形或连字替代的一些脚本(如阿拉伯语,希伯来语,泰语和印度语)不是有用的。
     * 
     * 
     * @param frc the specified <code>FontRenderContext</code>
     * @param chars the specified array of characters
     * @return a new <code>GlyphVector</code> created with the
     * specified array of characters and the specified
     * <code>FontRenderContext</code>.
     */
    public GlyphVector createGlyphVector(FontRenderContext frc, char[] chars)
    {
        return (GlyphVector)new StandardGlyphVector(this, chars, frc);
    }

    /**
     * Creates a {@link java.awt.font.GlyphVector GlyphVector} by
     * mapping the specified characters to glyphs one-to-one based on the
     * Unicode cmap in this <code>Font</code>.  This method does no other
     * processing besides the mapping of glyphs to characters.  This
     * means that this method is not useful for some scripts, such
     * as Arabic, Hebrew, Thai, and Indic, that require reordering,
     * shaping, or ligature substitution.
     * <p>
     * 创建一个{@link javaawtfontGlyphVector GlyphVector}通过映射指定的字符到glyphs一对一基于Unicode cmap在此<code>字体</code>此方法除了
     * 字形映射到字符之外没有其他处理这意味着该方法对于需要重新排序,整形或连字替换的一些脚本(如阿拉伯语,希伯来语,泰语和印度语)不是有用的。
     * 
     * 
     * @param frc the specified <code>FontRenderContext</code>
     * @param ci the specified <code>CharacterIterator</code>
     * @return a new <code>GlyphVector</code> created with the
     * specified <code>CharacterIterator</code> and the specified
     * <code>FontRenderContext</code>.
     */
    public GlyphVector createGlyphVector(   FontRenderContext frc,
                                            CharacterIterator ci)
    {
        return (GlyphVector)new StandardGlyphVector(this, ci, frc);
    }

    /**
     * Creates a {@link java.awt.font.GlyphVector GlyphVector} by
     * mapping characters to glyphs one-to-one based on the
     * Unicode cmap in this <code>Font</code>.  This method does no other
     * processing besides the mapping of glyphs to characters.  This
     * means that this method is not useful for some scripts, such
     * as Arabic, Hebrew, Thai, and Indic, that require reordering,
     * shaping, or ligature substitution.
     * <p>
     * 通过在<code> Font </code>中基于Unicode cmap将字符映射到字形,创建一个{@link javaawtfontGlyphVector GlyphVector}此方法除了将字形映
     * 射到字符之外不进行其他处理这意味着方法对于需要重新排序,整形或连字替代的一些脚本(如阿拉伯语,希伯来语,泰语和印度语)不是有用的。
     * 
     * 
     * @param frc the specified <code>FontRenderContext</code>
     * @param glyphCodes the specified integer array
     * @return a new <code>GlyphVector</code> created with the
     * specified integer array and the specified
     * <code>FontRenderContext</code>.
     */
    public GlyphVector createGlyphVector(   FontRenderContext frc,
                                            int [] glyphCodes)
    {
        return (GlyphVector)new StandardGlyphVector(this, glyphCodes, frc);
    }

    /**
     * Returns a new <code>GlyphVector</code> object, performing full
     * layout of the text if possible.  Full layout is required for
     * complex text, such as Arabic or Hindi.  Support for different
     * scripts depends on the font and implementation.
     * <p>
     * Layout requires bidi analysis, as performed by
     * <code>Bidi</code>, and should only be performed on text that
     * has a uniform direction.  The direction is indicated in the
     * flags parameter,by using LAYOUT_RIGHT_TO_LEFT to indicate a
     * right-to-left (Arabic and Hebrew) run direction, or
     * LAYOUT_LEFT_TO_RIGHT to indicate a left-to-right (English)
     * run direction.
     * <p>
     * In addition, some operations, such as Arabic shaping, require
     * context, so that the characters at the start and limit can have
     * the proper shapes.  Sometimes the data in the buffer outside
     * the provided range does not have valid data.  The values
     * LAYOUT_NO_START_CONTEXT and LAYOUT_NO_LIMIT_CONTEXT can be
     * added to the flags parameter to indicate that the text before
     * start, or after limit, respectively, should not be examined
     * for context.
     * <p>
     * All other values for the flags parameter are reserved.
     *
     * <p>
     *  返回一个新的<code> GlyphVector </code>对象,如果可能,执行完全布局的文本复杂的文本,如阿拉伯语或印地语需要完全布局支持不同的脚本取决于字体和实现
     * <p>
     * 布局需要由<code> Bidi </code>执行的双向分析,并且只应在具有统一方向的文本上执行。
     * 方向在flags参数中指示,方法是使用LAYOUT_RIGHT_TO_LEFT表示从右到左阿拉伯语和希伯来语)运行方向,或LAYOUT_LEFT_TO_RIGHT,表示从左到右(英语)运行方向。
     * <p>
     *  此外,某些操作(如阿拉伯语整形)需要上下文,以便开始和限制处的字符可以具有正确的形状。
     * 有时,缓冲区中超出提供的范围的数据不具有有效数据值LAYOUT_NO_START_CONTEXT和LAYOUT_NO_LIMIT_CONTEXT可以是添加到flags参数中,以指示文本在开始之前,或者在
     * 限制之后,分别不应该检查上下文。
     *  此外,某些操作(如阿拉伯语整形)需要上下文,以便开始和限制处的字符可以具有正确的形状。
     * <p>
     * flags参数的所有其他值都保留
     * 
     * 
     * @param frc the specified <code>FontRenderContext</code>
     * @param text the text to layout
     * @param start the start of the text to use for the <code>GlyphVector</code>
     * @param limit the limit of the text to use for the <code>GlyphVector</code>
     * @param flags control flags as described above
     * @return a new <code>GlyphVector</code> representing the text between
     * start and limit, with glyphs chosen and positioned so as to best represent
     * the text
     * @throws ArrayIndexOutOfBoundsException if start or limit is
     * out of bounds
     * @see java.text.Bidi
     * @see #LAYOUT_LEFT_TO_RIGHT
     * @see #LAYOUT_RIGHT_TO_LEFT
     * @see #LAYOUT_NO_START_CONTEXT
     * @see #LAYOUT_NO_LIMIT_CONTEXT
     * @since 1.4
     */
    public GlyphVector layoutGlyphVector(FontRenderContext frc,
                                         char[] text,
                                         int start,
                                         int limit,
                                         int flags) {

        GlyphLayout gl = GlyphLayout.get(null); // !!! no custom layout engines
        StandardGlyphVector gv = gl.layout(this, frc, text,
                                           start, limit-start, flags, null);
        GlyphLayout.done(gl);
        return gv;
    }

    /**
     * A flag to layoutGlyphVector indicating that text is left-to-right as
     * determined by Bidi analysis.
     * <p>
     *  layoutGlyphVector的一个标志,指示文本是由Bidi分析确定的从左到右
     * 
     */
    public static final int LAYOUT_LEFT_TO_RIGHT = 0;

    /**
     * A flag to layoutGlyphVector indicating that text is right-to-left as
     * determined by Bidi analysis.
     * <p>
     *  layoutGlyphVector的标志,指示由Bidi分析确定的文本是从右到左
     * 
     */
    public static final int LAYOUT_RIGHT_TO_LEFT = 1;

    /**
     * A flag to layoutGlyphVector indicating that text in the char array
     * before the indicated start should not be examined.
     * <p>
     *  layoutGlyphVector的标志,指示在指定的开始之前的char数组中的文本不应该被检查
     * 
     */
    public static final int LAYOUT_NO_START_CONTEXT = 2;

    /**
     * A flag to layoutGlyphVector indicating that text in the char array
     * after the indicated limit should not be examined.
     * <p>
     *  layoutGlyphVector的标志,指示在指定的限制后的char数组中的文本不应该被检查
     * 
     */
    public static final int LAYOUT_NO_LIMIT_CONTEXT = 4;


    private static void applyTransform(AffineTransform trans, AttributeValues values) {
        if (trans == null) {
            throw new IllegalArgumentException("transform must not be null");
        }
        values.setTransform(trans);
    }

    private static void applyStyle(int style, AttributeValues values) {
        // WEIGHT_BOLD, WEIGHT_REGULAR
        values.setWeight((style & BOLD) != 0 ? 2f : 1f);
        // POSTURE_OBLIQUE, POSTURE_REGULAR
        values.setPosture((style & ITALIC) != 0 ? .2f : 0f);
    }

    /*
     * Initialize JNI field and method IDs
     * <p>
     *  初始化JNI字段和方法ID
     */
    private static native void initIDs();
}
