/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.RenderingHints.Key;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.awt.image.BufferedImageOp;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.awt.font.GlyphVector;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator;
import java.util.Map;

/**
 * This <code>Graphics2D</code> class extends the
 * {@link Graphics} class to provide more sophisticated
 * control over geometry, coordinate transformations, color management,
 * and text layout.  This is the fundamental class for rendering
 * 2-dimensional shapes, text and images on the  Java(tm) platform.
 * <p>
 * <h2>Coordinate Spaces</h2>
 * All coordinates passed to a <code>Graphics2D</code> object are specified
 * in a device-independent coordinate system called User Space, which is
 * used by applications.  The <code>Graphics2D</code> object contains
 * an {@link AffineTransform} object as part of its rendering state
 * that defines how to convert coordinates from user space to
 * device-dependent coordinates in Device Space.
 * <p>
 * Coordinates in device space usually refer to individual device pixels
 * and are aligned on the infinitely thin gaps between these pixels.
 * Some <code>Graphics2D</code> objects can be used to capture rendering
 * operations for storage into a graphics metafile for playback on a
 * concrete device of unknown physical resolution at a later time.  Since
 * the resolution might not be known when the rendering operations are
 * captured, the <code>Graphics2D</code> <code>Transform</code> is set up
 * to transform user coordinates to a virtual device space that
 * approximates the expected resolution of the target device. Further
 * transformations might need to be applied at playback time if the
 * estimate is incorrect.
 * <p>
 * Some of the operations performed by the rendering attribute objects
 * occur in the device space, but all <code>Graphics2D</code> methods take
 * user space coordinates.
 * <p>
 * Every <code>Graphics2D</code> object is associated with a target that
 * defines where rendering takes place. A
 * {@link GraphicsConfiguration} object defines the characteristics
 * of the rendering target, such as pixel format and resolution.
 * The same rendering target is used throughout the life of a
 * <code>Graphics2D</code> object.
 * <p>
 * When creating a <code>Graphics2D</code> object,  the
 * <code>GraphicsConfiguration</code>
 * specifies the <a name="deftransform">default transform</a> for
 * the target of the <code>Graphics2D</code> (a
 * {@link Component} or {@link Image}).  This default transform maps the
 * user space coordinate system to screen and printer device coordinates
 * such that the origin maps to the upper left hand corner of the
 * target region of the device with increasing X coordinates extending
 * to the right and increasing Y coordinates extending downward.
 * The scaling of the default transform is set to identity for those devices
 * that are close to 72 dpi, such as screen devices.
 * The scaling of the default transform is set to approximately 72 user
 * space coordinates per square inch for high resolution devices, such as
 * printers.  For image buffers, the default transform is the
 * <code>Identity</code> transform.
 *
 * <h2>Rendering Process</h2>
 * The Rendering Process can be broken down into four phases that are
 * controlled by the <code>Graphics2D</code> rendering attributes.
 * The renderer can optimize many of these steps, either by caching the
 * results for future calls, by collapsing multiple virtual steps into
 * a single operation, or by recognizing various attributes as common
 * simple cases that can be eliminated by modifying other parts of the
 * operation.
 * <p>
 * The steps in the rendering process are:
 * <ol>
 * <li>
 * Determine what to render.
 * <li>
 * Constrain the rendering operation to the current <code>Clip</code>.
 * The <code>Clip</code> is specified by a {@link Shape} in user
 * space and is controlled by the program using the various clip
 * manipulation methods of <code>Graphics</code> and
 * <code>Graphics2D</code>.  This <i>user clip</i>
 * is transformed into device space by the current
 * <code>Transform</code> and combined with the
 * <i>device clip</i>, which is defined by the visibility of windows and
 * device extents.  The combination of the user clip and device clip
 * defines the <i>composite clip</i>, which determines the final clipping
 * region.  The user clip is not modified by the rendering
 * system to reflect the resulting composite clip.
 * <li>
 * Determine what colors to render.
 * <li>
 * Apply the colors to the destination drawing surface using the current
 * {@link Composite} attribute in the <code>Graphics2D</code> context.
 * </ol>
 * <br>
 * The three types of rendering operations, along with details of each
 * of their particular rendering processes are:
 * <ol>
 * <li>
 * <b><a name="rendershape"><code>Shape</code> operations</a></b>
 * <ol>
 * <li>
 * If the operation is a <code>draw(Shape)</code> operation, then
 * the  {@link Stroke#createStrokedShape(Shape) createStrokedShape}
 * method on the current {@link Stroke} attribute in the
 * <code>Graphics2D</code> context is used to construct a new
 * <code>Shape</code> object that contains the outline of the specified
 * <code>Shape</code>.
 * <li>
 * The <code>Shape</code> is transformed from user space to device space
 * using the current <code>Transform</code>
 * in the <code>Graphics2D</code> context.
 * <li>
 * The outline of the <code>Shape</code> is extracted using the
 * {@link Shape#getPathIterator(AffineTransform) getPathIterator} method of
 * <code>Shape</code>, which returns a
 * {@link java.awt.geom.PathIterator PathIterator}
 * object that iterates along the boundary of the <code>Shape</code>.
 * <li>
 * If the <code>Graphics2D</code> object cannot handle the curved segments
 * that the <code>PathIterator</code> object returns then it can call the
 * alternate
 * {@link Shape#getPathIterator(AffineTransform, double) getPathIterator}
 * method of <code>Shape</code>, which flattens the <code>Shape</code>.
 * <li>
 * The current {@link Paint} in the <code>Graphics2D</code> context
 * is queried for a {@link PaintContext}, which specifies the
 * colors to render in device space.
 * </ol>
 * <li>
 * <b><a name=rendertext>Text operations</a></b>
 * <ol>
 * <li>
 * The following steps are used to determine the set of glyphs required
 * to render the indicated <code>String</code>:
 * <ol>
 * <li>
 * If the argument is a <code>String</code>, then the current
 * <code>Font</code> in the <code>Graphics2D</code> context is asked to
 * convert the Unicode characters in the <code>String</code> into a set of
 * glyphs for presentation with whatever basic layout and shaping
 * algorithms the font implements.
 * <li>
 * If the argument is an
 * {@link AttributedCharacterIterator},
 * the iterator is asked to convert itself to a
 * {@link java.awt.font.TextLayout TextLayout}
 * using its embedded font attributes. The <code>TextLayout</code>
 * implements more sophisticated glyph layout algorithms that
 * perform Unicode bi-directional layout adjustments automatically
 * for multiple fonts of differing writing directions.
  * <li>
 * If the argument is a
 * {@link GlyphVector}, then the
 * <code>GlyphVector</code> object already contains the appropriate
 * font-specific glyph codes with explicit coordinates for the position of
 * each glyph.
 * </ol>
 * <li>
 * The current <code>Font</code> is queried to obtain outlines for the
 * indicated glyphs.  These outlines are treated as shapes in user space
 * relative to the position of each glyph that was determined in step 1.
 * <li>
 * The character outlines are filled as indicated above
 * under <a href="#rendershape"><code>Shape</code> operations</a>.
 * <li>
 * The current <code>Paint</code> is queried for a
 * <code>PaintContext</code>, which specifies
 * the colors to render in device space.
 * </ol>
 * <li>
 * <b><a name= renderingimage><code>Image</code> Operations</a></b>
 * <ol>
 * <li>
 * The region of interest is defined by the bounding box of the source
 * <code>Image</code>.
 * This bounding box is specified in Image Space, which is the
 * <code>Image</code> object's local coordinate system.
 * <li>
 * If an <code>AffineTransform</code> is passed to
 * {@link #drawImage(java.awt.Image, java.awt.geom.AffineTransform, java.awt.image.ImageObserver) drawImage(Image, AffineTransform, ImageObserver)},
 * the <code>AffineTransform</code> is used to transform the bounding
 * box from image space to user space. If no <code>AffineTransform</code>
 * is supplied, the bounding box is treated as if it is already in user space.
 * <li>
 * The bounding box of the source <code>Image</code> is transformed from user
 * space into device space using the current <code>Transform</code>.
 * Note that the result of transforming the bounding box does not
 * necessarily result in a rectangular region in device space.
 * <li>
 * The <code>Image</code> object determines what colors to render,
 * sampled according to the source to destination
 * coordinate mapping specified by the current <code>Transform</code> and the
 * optional image transform.
 * </ol>
 * </ol>
 *
 * <h2>Default Rendering Attributes</h2>
 * The default values for the <code>Graphics2D</code> rendering attributes are:
 * <dl compact>
 * <dt><i><code>Paint</code></i>
 * <dd>The color of the <code>Component</code>.
 * <dt><i><code>Font</code></i>
 * <dd>The <code>Font</code> of the <code>Component</code>.
 * <dt><i><code>Stroke</code></i>
 * <dd>A square pen with a linewidth of 1, no dashing, miter segment joins
 * and square end caps.
 * <dt><i><code>Transform</code></i>
 * <dd>The
 * {@link GraphicsConfiguration#getDefaultTransform() getDefaultTransform}
 * for the <code>GraphicsConfiguration</code> of the <code>Component</code>.
 * <dt><i><code>Composite</code></i>
 * <dd>The {@link AlphaComposite#SRC_OVER} rule.
 * <dt><i><code>Clip</code></i>
 * <dd>No rendering <code>Clip</code>, the output is clipped to the
 * <code>Component</code>.
 * </dl>
 *
 * <h2>Rendering Compatibility Issues</h2>
 * The JDK(tm) 1.1 rendering model is based on a pixelization model
 * that specifies that coordinates
 * are infinitely thin, lying between the pixels.  Drawing operations are
 * performed using a one-pixel wide pen that fills the
 * pixel below and to the right of the anchor point on the path.
 * The JDK 1.1 rendering model is consistent with the
 * capabilities of most of the existing class of platform
 * renderers that need  to resolve integer coordinates to a
 * discrete pen that must fall completely on a specified number of pixels.
 * <p>
 * The Java 2D(tm) (Java(tm) 2 platform) API supports antialiasing renderers.
 * A pen with a width of one pixel does not need to fall
 * completely on pixel N as opposed to pixel N+1.  The pen can fall
 * partially on both pixels. It is not necessary to choose a bias
 * direction for a wide pen since the blending that occurs along the
 * pen traversal edges makes the sub-pixel position of the pen
 * visible to the user.  On the other hand, when antialiasing is
 * turned off by setting the
 * {@link RenderingHints#KEY_ANTIALIASING KEY_ANTIALIASING} hint key
 * to the
 * {@link RenderingHints#VALUE_ANTIALIAS_OFF VALUE_ANTIALIAS_OFF}
 * hint value, the renderer might need
 * to apply a bias to determine which pixel to modify when the pen
 * is straddling a pixel boundary, such as when it is drawn
 * along an integer coordinate in device space.  While the capabilities
 * of an antialiasing renderer make it no longer necessary for the
 * rendering model to specify a bias for the pen, it is desirable for the
 * antialiasing and non-antialiasing renderers to perform similarly for
 * the common cases of drawing one-pixel wide horizontal and vertical
 * lines on the screen.  To ensure that turning on antialiasing by
 * setting the
 * {@link RenderingHints#KEY_ANTIALIASING KEY_ANTIALIASING} hint
 * key to
 * {@link RenderingHints#VALUE_ANTIALIAS_ON VALUE_ANTIALIAS_ON}
 * does not cause such lines to suddenly become twice as wide and half
 * as opaque, it is desirable to have the model specify a path for such
 * lines so that they completely cover a particular set of pixels to help
 * increase their crispness.
 * <p>
 * Java 2D API maintains compatibility with JDK 1.1 rendering
 * behavior, such that legacy operations and existing renderer
 * behavior is unchanged under Java 2D API.  Legacy
 * methods that map onto general <code>draw</code> and
 * <code>fill</code> methods are defined, which clearly indicates
 * how <code>Graphics2D</code> extends <code>Graphics</code> based
 * on settings of <code>Stroke</code> and <code>Transform</code>
 * attributes and rendering hints.  The definition
 * performs identically under default attribute settings.
 * For example, the default <code>Stroke</code> is a
 * <code>BasicStroke</code> with a width of 1 and no dashing and the
 * default Transform for screen drawing is an Identity transform.
 * <p>
 * The following two rules provide predictable rendering behavior whether
 * aliasing or antialiasing is being used.
 * <ul>
 * <li> Device coordinates are defined to be between device pixels which
 * avoids any inconsistent results between aliased and antialiased
 * rendering.  If coordinates were defined to be at a pixel's center, some
 * of the pixels covered by a shape, such as a rectangle, would only be
 * half covered.
 * With aliased rendering, the half covered pixels would either be
 * rendered inside the shape or outside the shape.  With anti-aliased
 * rendering, the pixels on the entire edge of the shape would be half
 * covered.  On the other hand, since coordinates are defined to be
 * between pixels, a shape like a rectangle would have no half covered
 * pixels, whether or not it is rendered using antialiasing.
 * <li> Lines and paths stroked using the <code>BasicStroke</code>
 * object may be "normalized" to provide consistent rendering of the
 * outlines when positioned at various points on the drawable and
 * whether drawn with aliased or antialiased rendering.  This
 * normalization process is controlled by the
 * {@link RenderingHints#KEY_STROKE_CONTROL KEY_STROKE_CONTROL} hint.
 * The exact normalization algorithm is not specified, but the goals
 * of this normalization are to ensure that lines are rendered with
 * consistent visual appearance regardless of how they fall on the
 * pixel grid and to promote more solid horizontal and vertical
 * lines in antialiased mode so that they resemble their non-antialiased
 * counterparts more closely.  A typical normalization step might
 * promote antialiased line endpoints to pixel centers to reduce the
 * amount of blending or adjust the subpixel positioning of
 * non-antialiased lines so that the floating point line widths
 * round to even or odd pixel counts with equal likelihood.  This
 * process can move endpoints by up to half a pixel (usually towards
 * positive infinity along both axes) to promote these consistent
 * results.
 * </ul>
 * <p>
 * The following definitions of general legacy methods
 * perform identically to previously specified behavior under default
 * attribute settings:
 * <ul>
 * <li>
 * For <code>fill</code> operations, including <code>fillRect</code>,
 * <code>fillRoundRect</code>, <code>fillOval</code>,
 * <code>fillArc</code>, <code>fillPolygon</code>, and
 * <code>clearRect</code>, {@link #fill(Shape) fill} can now be called
 * with the desired <code>Shape</code>.  For example, when filling a
 * rectangle:
 * <pre>
 * fill(new Rectangle(x, y, w, h));
 * </pre>
 * is called.
 * <p>
 * <li>
 * Similarly, for draw operations, including <code>drawLine</code>,
 * <code>drawRect</code>, <code>drawRoundRect</code>,
 * <code>drawOval</code>, <code>drawArc</code>, <code>drawPolyline</code>,
 * and <code>drawPolygon</code>, {@link #draw(Shape) draw} can now be
 * called with the desired <code>Shape</code>.  For example, when drawing a
 * rectangle:
 * <pre>
 * draw(new Rectangle(x, y, w, h));
 * </pre>
 * is called.
 * <p>
 * <li>
 * The <code>draw3DRect</code> and <code>fill3DRect</code> methods were
 * implemented in terms of the <code>drawLine</code> and
 * <code>fillRect</code> methods in the <code>Graphics</code> class which
 * would predicate their behavior upon the current <code>Stroke</code>
 * and <code>Paint</code> objects in a <code>Graphics2D</code> context.
 * This class overrides those implementations with versions that use
 * the current <code>Color</code> exclusively, overriding the current
 * <code>Paint</code> and which uses <code>fillRect</code> to describe
 * the exact same behavior as the preexisting methods regardless of the
 * setting of the current <code>Stroke</code>.
 * </ul>
 * The <code>Graphics</code> class defines only the <code>setColor</code>
 * method to control the color to be painted.  Since the Java 2D API extends
 * the <code>Color</code> object to implement the new <code>Paint</code>
 * interface, the existing
 * <code>setColor</code> method is now a convenience method for setting the
 * current <code>Paint</code> attribute to a <code>Color</code> object.
 * <code>setColor(c)</code> is equivalent to <code>setPaint(c)</code>.
 * <p>
 * The <code>Graphics</code> class defines two methods for controlling
 * how colors are applied to the destination.
 * <ol>
 * <li>
 * The <code>setPaintMode</code> method is implemented as a convenience
 * method to set the default <code>Composite</code>, equivalent to
 * <code>setComposite(new AlphaComposite.SrcOver)</code>.
 * <li>
 * The <code>setXORMode(Color xorcolor)</code> method is implemented
 * as a convenience method to set a special <code>Composite</code> object that
 * ignores the <code>Alpha</code> components of source colors and sets the
 * destination color to the value:
 * <pre>
 * dstpixel = (PixelOf(srccolor) ^ PixelOf(xorcolor) ^ dstpixel);
 * </pre>
 * </ol>
 *
 * <p>
 *  这个<code> Graphics2D </code>类扩展了{@link Graphics}类,以提供对几何,坐标变换,颜色管理和文本布局的更复杂的控制。
 * 这是在Java(tm)平台上呈现2维形状,文本和图像的基本类。
 * <p>
 *  <h2>坐标空间</h2>传递到<code> Graphics2D </code>对象的所有坐标在应用程序使用的称为用户空间的设备独立坐标系中指定。
 *  <code> Graphics2D </code>对象包含{@link AffineTransform}对象,作为其渲染状态的一部分,定义如何将坐标从用户空间转换为设备空间中的设备相关坐标。
 * <p>
 * 设备空间中的坐标通常指单个设备像素,并且在这些像素之间的无限薄间隙上对准。
 * 一些<code> Graphics2D </code>对象可以用于捕获渲染操作以存储到图形元文件中,以在稍后的时间在具有未知物理分辨率的具体设备上播放。
 * 由于在捕获渲染操作时可能不知道分辨率,因此设置<code> Graphics2D </code> <code> Transform </code>将用户坐标转换为虚拟设备空间,目标设备。
 * 如果估计不正确,则可能需要在回放时间应用进一步的变换。
 * <p>
 *  渲染属性对象执行的一些操作发生在设备空间中,但是所有<code> Graphics2D </code>方法都占用用户空间坐标。
 * <p>
 *  每个<code> Graphics2D </code>对象与定义渲染发生位置的目标相关联。 {@link GraphicsConfiguration}对象定义渲染目标的特性,例如像素格式和分辨率。
 * 在<code> Graphics2D </code>对象的整个生命周期中使用相同的渲染目标。
 * <p>
 * 当创建<code> Graphics2D </code>对象时,<code> GraphicsConfiguration </code>指定<code> Graphics2D </code目标的<a name="deftransform">
 * 默认变换</a> >({@link Component}或{@link Image})。
 * 该默认变换将用户空间坐标系映射到屏幕和打印机设备坐标,使得原点映射到设备的目标区域的左上角,其具有向右延伸的增加的X坐标和向下延伸的增加的Y坐标。
 * 默认变换的缩放设置为接近72 dpi的那些设备(如屏幕设备)的标识。对于诸如打印机的高分辨率设备,默认变换的缩放设置为大约每平方英寸72个用户空间坐标。
 * 对于图像缓冲区,默认变换是<code> Identity </code>变换。
 * 
 *  <h2>呈现过程</h2>呈现过程可以分解为由<code> Graphics2D </code>呈现属性控制的四个阶段。
 * 渲染器可以通过将多个虚拟步骤折叠到单个操作中,或者通过将各种属性识别为可以通过修改操作的其他部分来消除的常见简单情况,来优化这些步骤中的许多步骤。
 * <p>
 *  渲染过程中的步骤如下：
 * <ol>
 * <li>
 *  确定要呈现的内容。
 * <li>
 * 将渲染操作限制到当前的<code> Clip </code>。
 *  <code> Clip </code>由用户空间中的{@link Shape}指定,并由程序使用<code> Graphics </code>和<code> Graphics2D </code >。
 * 此<i>用户剪辑</i>通过当前<code> Transform </code>转换为设备空间,并与<i>设备剪辑</i>范围。
 * 用户剪辑和设备剪辑的组合定义了<i>复合剪辑</i>,其确定最终剪辑区域。渲染系统不会修改用户剪辑以反映生成的复合剪辑。
 * <li>
 *  确定要呈现的颜色。
 * <li>
 *  使用<code> Graphics2D </code>上下文中的当前{@link Composite}属性将颜色应用到目标绘图表面。
 * </ol>
 * <br>
 *  三种类型的渲染操作,以及它们各自的渲染过程的细节是：
 * <ol>
 * <li>
 *  <b> <a name="rendershape"> <code>形状</code>操作</a> </b>
 * <ol>
 * <li>
 *  如果操作是<code> draw(Shape)</code>操作,则对<code> Graphics2D </code中的当前{@link Stroke}属性使用{@link Stroke#createStrokedShape(Shape)createStrokedShape} >
 *  context用于构造一个包含指定的<code> Shape </code>的轮廓的新的<code> Shape </code>对象。
 * <li>
 * 使用<code> Graphics2D </code>上下文中的当前<code> Transform </code>将<code> Shape </code>从用户空间转换为设备空间。
 * <li>
 *  使用<code> Shape </code>的{@link Shape#getPathIterator(AffineTransform)getPathIterator}方法提取<code> Shape
 *  </code>的大纲,该方​​法返回{@link java.awt.geom。
 *  PathIterator PathIterator}对象,它沿着<code> Shape </code>的边界进行迭代。
 * <li>
 *  如果<code> Graphics2D </code>对象不能处理<code> PathIterator </code>对象返回的曲线段,那么它可以调用</object>对象的替代{@link Shape#getPathIterator(AffineTransform,double)getPathIterator}
 * 代码>形状</code>,它使<code> Shape </code>变平。
 * <li>
 *  查询<code> Graphics2D </code>上下文中的当前{@link Paint}查询{@link PaintContext},它指定要在设备空间中呈现的颜色。
 * </ol>
 * <li>
 *  <b> <a name=rendertext>文字操作</a> </b>
 * <ol>
 * <li>
 *  以下步骤用于确定呈现所指示的<code> String </code>所需的字形集：
 * <ol>
 * <li>
 *  如果参数是<code> String </code>,则要求在<code> Graphics2D </code>上下文中的当前<code> Font </code>转换<code> String < / code>
 * 转换为一组字形,以便用字体实现的任何基本布局和整形算法进行呈现。
 * <li>
 * 如果参数是{@link AttributedCharacterIterator},迭代器将要求使用其嵌入的字体属性将自身转换为{@link java.awt.font.TextLayout TextLayout}
 * 。
 *  <code> TextLayout </code>实现更复杂的字形布局算法,可以为不同写入方向的多种字体自动执行Unicode双向布局调整。
 * <li>
 *  如果参数是一个{@link GlyphVector},那么<code> GlyphVector </code>对象已经包含了适当的字体特定的字形代码,显示每个字形位置的坐标。
 * </ol>
 * <li>
 *  将查询当前<code> Font </code>以获取指示的字形的轮廓。相对于在步骤1中确定的每个字形的位置,这些轮廓被视为用户空间中的形状。
 * <li>
 *  字符轮廓如上所示在<a href="#rendershape"> <code>形状</code>操作</a>中填充。
 * <li>
 *  将向当前<code> Paint </code>查询一个<code> PaintContext </code>,它指定要在设备空间中呈现的颜色。
 * </ol>
 * <li>
 *  <b> <a name= renderingimage> <code>图像</code>操作</a> </b>
 * <ol>
 * <li>
 *  感兴趣的区域由源<code> Image </code>的边界框定义。这个边界框在Image Space中指定,它是<code> Image </code>对象的局部坐标系。
 * <li>
 * 如果将<code> AffineTransform </code>传递给{@link #drawImage(java.awt.Image,java.awt.geom.AffineTransform,java.awt.image.ImageObserver)drawImage(Image,AffineTransform,ImageObserver)}
 *  ,<code> AffineTransform </code>用于将边界框从图像空间变换到用户空间。
 * 如果未提供<code> AffineTransform </code>,则将边界框视为已在用户空间中。
 * <li>
 *  源<code> Image </code>的边界框使用当前<code> Transform </code>从用户空间变换到设备空间。注意,变换边界框的结果不一定导致设备空间中的矩形区域。
 * <li>
 *  <code> Image </code>对象根据当前<code> Transform </code>和可选图像转换指定的源到目标坐标映射来确定要呈现的颜色。
 * </ol>
 * </ol>
 * 
 *  <h2>默认呈现属性</h2> <code> Graphics2D </code>呈现属性的默认值为：
 * <dl compact>
 * <dt> <i> <code> Paint </code> </i> <dd> <code> Component </code>的颜色。
 *  <dt> <i> <code>字体</code> </i> <dd> <code> Component </code>的<code> Font </code> <dt> <i> <code>笔画</code>
 *  </i> <dd>线宽为1的方形笔,没有破折号,斜角线段连接和方形端盖。
 * <dt> <i> <code> Paint </code> </i> <dd> <code> Component </code>的颜色。
 *  <dt> <i> <code> Transform </code> </i> <dd> <code> GraphicsConfiguration </code>的{@link GraphicsConfiguration#getDefaultTransform()getDefaultTransform}
 * 代码>。
 * <dt> <i> <code> Paint </code> </i> <dd> <code> Component </code>的颜色。
 *  <dt> <i> <code>复合</code> </i> <dd> {@link AlphaComposite#SRC_OVER}规则。
 *  <dt> <i> <code> Clip </code> </i> <dd>无渲染<code> Clip </code>,输出被剪切到<code> Component </code>。
 * </dl>
 * 
 *  <h2>渲染兼容性问题</h2> JDK(tm)1.1渲染模型基于像素化模型,该模型指定坐标位于像素之间的无限薄。绘制操作使用填充路径上的锚点下方和右侧的像素的一像素宽的笔来执行。
 *  JDK 1.1渲染模型与需要将整数坐标解析为必须完全落在指定数量的像素上的离散笔的大多数现有平台渲染器类的能力一致。
 * <p>
 * Java 2D(tm)(Java(tm)2平台)API支持抗锯齿渲染器。与像素N + 1相对,具有一个像素的宽度的笔不需要完全落在像素N上。笔可以部分地落在两个像素上。
 * 不需要为宽笔选择偏置方向,因为沿着笔遍历边缘发生的混合使得笔的子像素位置对于用户可见。
 * 另一方面,当通过将{@link RenderingHints#KEY_ANTIALIASING KEY_ANTIALIASING}提示键设置为{@link RenderingHints#VALUE_ANTIALIAS_OFF VALUE_ANTIALIAS_OFF}
 * 提示值来关闭抗锯齿时,渲染器可能需要应用偏差来确定要修改的像素当笔跨越像素边界时,例如当其沿着设备空间中的整数坐标绘制时。
 * 不需要为宽笔选择偏置方向,因为沿着笔遍历边缘发生的混合使得笔的子像素位置对于用户可见。
 * 虽然抗锯齿渲染器的能力使得渲染模型不再需要为笔指定偏差,但是期望抗锯齿和非抗锯齿渲染器对于绘制一个像素宽的水平和垂直线在屏幕上。
 * 要确保通过将{@link RenderingHints#KEY_ANTIALIASING KEY_ANTIALIASING}提示键设置为{@link RenderingHints#VALUE_ANTIALIAS_ON VALUE_ANTIALIAS_ON}
 * 不会导致此类线突然变为两倍宽和一半不透明,从而打开抗锯齿,模型指定这样的线的路径,使得它们完全覆盖特定像素集合以帮助增加它们的脆度。
 * 虽然抗锯齿渲染器的能力使得渲染模型不再需要为笔指定偏差,但是期望抗锯齿和非抗锯齿渲染器对于绘制一个像素宽的水平和垂直线在屏幕上。
 * <p>
 * Java 2D API维护与JDK 1.1呈现行为的兼容性,使得传统操作和现有呈现器行为在Java 2D API下不变。
 * 定义映射到一般<code> draw </code>和<code>填充</code>方法的传统方法,其清楚地指示<code> Graphics2D </code>如何基于<code> Graphics 
 * </code>设置<code> Stroke </code>和<code> Transform </code>属性和渲染提示。
 * Java 2D API维护与JDK 1.1呈现行为的兼容性,使得传统操作和现有呈现器行为在Java 2D API下不变。在默认属性设置下,定义执行相同。
 * 例如,默认的<code> Stroke </code>是一个宽度为1且没有破折号的<code> BasicStroke </code>,默认的屏幕绘制变换是一个身份变换。
 * <p>
 *  以下两个规则提供可预测的渲染行为是否使用混叠或抗锯齿。
 * <ul>
 * <li>设备坐标定义在设备像素之间,避免了别名和反锯齿渲染之间的任何不一致的结果。如果坐标被定义为在像素的中心,由形状(例如矩形)覆盖的像素中的一些将仅被覆盖一半。
 * 使用混叠渲染,半覆盖的像素将被呈现在形状内部或外部。使用抗锯齿渲染,形状的整个边缘上的像素将被覆盖一半。
 * 另一方面,由于坐标被定义为在像素之间,所以像矩形的形状将不具有半覆盖的像素,而不管是否使用抗锯齿来呈现。
 *  <li>使用<code> BasicStroke </code>对象进行描边的线和路径可以"规范化",以便在位于绘图上的各个点时提供轮廓的一致渲染,以及是否使用别名或反锯齿绘制绘制。
 * 此标准化过程由{@link RenderingHints#KEY_STROKE_CONTROL KEY_STROKE_CONTROL}提示控制。
 * 没有指定精确的归一化算法,但是该归一化的目的是确保线被渲染为具有一致的视觉外观,而不管它们如何落在像素网格上,并且在抗锯齿模式中促进更加坚固的水平和垂直线,使得它们类似于它们的非抗锯齿对应物更密切。
 * 典型的归一化步骤可以促进到像素中心的抗锯齿线端点,以减少混合的量或调整非抗锯齿线的子像素定位,使得浮点线宽度以相等的可能性舍入到偶数或奇数像素计数。
 * 这个过程可以将端点移动高达半个像素(通常朝向两个轴的正无穷大),以促进这些一致的结果。
 * </ul>
 * <p>
 * 以下常规遗留方法的定义与默认属性设置下的先前指定行为完全相同：
 * <ul>
 * <li>
 *  对于<code> fill </code>操作,包括<code> fillRect </code>,<code> fillRoundRect </code>,<code> fillOval </code>
 * ,<code> fillArc </code> > fillPolygon </code>和<code> clearRect </code>,{@link #fill(Shape)fill}现在可以使用
 * 所需的<code> Shape </code>来调用。
 * 例如,当填充矩形时：。
 * <pre>
 *  fill(new Rectangle(x,y,w,h));
 * </pre>
 *  叫做。
 * <p>
 * <li>
 *  类似地,对于绘制操作,包括<code> drawLine </code>,<code> drawRect </code>,<code> drawRoundRect </code>,<code> dra
 * wOval </code> >,<code> drawPolyline </code>和<code> drawPolygon </code>,{@link #draw(Shape)draw}现在可以用所
 * 需的<code> Shape </code>来调用。
 * 例如,当绘制矩形时：。
 * <pre>
 *  draw(new Rectangle(x,y,w,h));
 * </pre>
 *  叫做。
 * <p>
 * <li>
 * <code> draw3DRect </code>和<code> fill3DRect </code>方法是根据<code> drawLine </code> / code>类,它将根据<code> G
 * raphics2D </code>上下文中的当前<code> Stroke </code>和<code> Paint </code>对象来判断它们的行为。
 * 这个类使用当前使用当前<code> Color </code>的版本覆盖那些实现,覆盖当前<code> Paint </code>,并使用<code> fillRect </code>来描述完全相同的行
 * 为预先存在的方法,而不考虑当前<code> Stroke </code>的设置。
 * </ul>
 *  <code> Graphics </code>类仅定义<code> setColor </code>方法来控制要绘制的颜色。
 * 由于Java 2D API扩展了<code> Color </code>对象来实现新的<code> Paint </code>接口,现有的<code> setColor </code>方法现在是一种方便
 * 的方法, <code>将</code>属性映射到<code> Color </code>对象。
 *  <code> Graphics </code>类仅定义<code> setColor </code>方法来控制要绘制的颜色。
 *  <code> setColor(c)</code>等效于<code> setPaint(c)</code>。
 * <p>
 *  <code> Graphics </code>类定义了两种方法来控制颜色如何应用到目标。
 * <ol>
 * <li>
 *  <code> setPaintMode </code>方法被实现为方便的方法来设置默认<code> Composite </code>,等效于<code> setComposite(new Alpha
 * Composite.SrcOver)</code>。
 * <li>
 * 实现<code> setXORMode(Color xorcolor)</code>方法作为方便的方法来设置特殊的<code> Composite </code>对象,忽略源颜色和集合的<code> A
 * lpha </code>目标颜色为值：。
 * <pre>
 *  dstpixel =(PixelOf(srccolor)^ PixelOf(xorcolor)^ dstpixel);
 * </pre>
 * </ol>
 * 
 * 
 * @author Jim Graham
 * @see java.awt.RenderingHints
 */
public abstract class Graphics2D extends Graphics {

    /**
     * Constructs a new <code>Graphics2D</code> object.  Since
     * <code>Graphics2D</code> is an abstract class, and since it must be
     * customized by subclasses for different output devices,
     * <code>Graphics2D</code> objects cannot be created directly.
     * Instead, <code>Graphics2D</code> objects must be obtained from another
     * <code>Graphics2D</code> object, created by a
     * <code>Component</code>, or obtained from images such as
     * {@link BufferedImage} objects.
     * <p>
     *  构造一个新的<code> Graphics2D </code>对象。
     * 由于<code> Graphics2D </code>是一个抽象类,因为它必须由不同输出设备的子类定制,所以不能直接创建<code> Graphics2D </code>对象。
     * 相反,必须从另一个<code> Graphics2D </code>对象中获取<code> Graphics2D </code>对象,该对象由<code> Component </code>创建,或者从
     * {@link BufferedImage}对象。
     * 由于<code> Graphics2D </code>是一个抽象类,因为它必须由不同输出设备的子类定制,所以不能直接创建<code> Graphics2D </code>对象。
     * 
     * 
     * @see java.awt.Component#getGraphics
     * @see java.awt.Graphics#create
     */
    protected Graphics2D() {
    }

    /**
     * Draws a 3-D highlighted outline of the specified rectangle.
     * The edges of the rectangle are highlighted so that they
     * appear to be beveled and lit from the upper left corner.
     * <p>
     * The colors used for the highlighting effect are determined
     * based on the current color.
     * The resulting rectangle covers an area that is
     * <code>width&nbsp;+&nbsp;1</code> pixels wide
     * by <code>height&nbsp;+&nbsp;1</code> pixels tall.  This method
     * uses the current <code>Color</code> exclusively and ignores
     * the current <code>Paint</code>.
     * <p>
     *  绘制指定矩形的3-D突出轮廓。矩形的边缘被突出显示,使得它们看起来是从左上角被斜切和点亮。
     * <p>
     *  基于当前颜色确定用于突出效果的颜色。
     * 生成的矩形覆盖了<code> width&nbsp; +&nbsp; 1&lt; / code&gt;像素宽&lt; code&gt;高度&nbsp; +&nbsp; 1 </code>像素高的区域。
     * 此方法独占使用当前<code> Color </code>,并忽略当前<code> Paint </code>。
     * 
     * 
     * @param x the x coordinate of the rectangle to be drawn.
     * @param y the y coordinate of the rectangle to be drawn.
     * @param width the width of the rectangle to be drawn.
     * @param height the height of the rectangle to be drawn.
     * @param raised a boolean that determines whether the rectangle
     *                      appears to be raised above the surface
     *                      or sunk into the surface.
     * @see         java.awt.Graphics#fill3DRect
     */
    public void draw3DRect(int x, int y, int width, int height,
                           boolean raised) {
        Paint p = getPaint();
        Color c = getColor();
        Color brighter = c.brighter();
        Color darker = c.darker();

        setColor(raised ? brighter : darker);
        //drawLine(x, y, x, y + height);
        fillRect(x, y, 1, height + 1);
        //drawLine(x + 1, y, x + width - 1, y);
        fillRect(x + 1, y, width - 1, 1);
        setColor(raised ? darker : brighter);
        //drawLine(x + 1, y + height, x + width, y + height);
        fillRect(x + 1, y + height, width, 1);
        //drawLine(x + width, y, x + width, y + height - 1);
        fillRect(x + width, y, 1, height);
        setPaint(p);
    }

    /**
     * Paints a 3-D highlighted rectangle filled with the current color.
     * The edges of the rectangle are highlighted so that it appears
     * as if the edges were beveled and lit from the upper left corner.
     * The colors used for the highlighting effect and for filling are
     * determined from the current <code>Color</code>.  This method uses
     * the current <code>Color</code> exclusively and ignores the current
     * <code>Paint</code>.
     * <p>
     * 绘制填充有当前颜色的三维突出显示的矩形。矩形的边缘被突出显示,使得它看起来好像边缘从左上角被斜切和点亮。用于突出效果和填充的颜色由当前<code> Color </code>确定。
     * 此方法独占使用当前<code> Color </code>,并忽略当前<code> Paint </code>。
     * 
     * 
     * @param x the x coordinate of the rectangle to be filled.
     * @param y the y coordinate of the rectangle to be filled.
     * @param       width the width of the rectangle to be filled.
     * @param       height the height of the rectangle to be filled.
     * @param       raised a boolean value that determines whether the
     *                      rectangle appears to be raised above the surface
     *                      or etched into the surface.
     * @see         java.awt.Graphics#draw3DRect
     */
    public void fill3DRect(int x, int y, int width, int height,
                           boolean raised) {
        Paint p = getPaint();
        Color c = getColor();
        Color brighter = c.brighter();
        Color darker = c.darker();

        if (!raised) {
            setColor(darker);
        } else if (p != c) {
            setColor(c);
        }
        fillRect(x+1, y+1, width-2, height-2);
        setColor(raised ? brighter : darker);
        //drawLine(x, y, x, y + height - 1);
        fillRect(x, y, 1, height);
        //drawLine(x + 1, y, x + width - 2, y);
        fillRect(x + 1, y, width - 2, 1);
        setColor(raised ? darker : brighter);
        //drawLine(x + 1, y + height - 1, x + width - 1, y + height - 1);
        fillRect(x + 1, y + height - 1, width - 1, 1);
        //drawLine(x + width - 1, y, x + width - 1, y + height - 2);
        fillRect(x + width - 1, y, 1, height - 1);
        setPaint(p);
    }

    /**
     * Strokes the outline of a <code>Shape</code> using the settings of the
     * current <code>Graphics2D</code> context.  The rendering attributes
     * applied include the <code>Clip</code>, <code>Transform</code>,
     * <code>Paint</code>, <code>Composite</code> and
     * <code>Stroke</code> attributes.
     * <p>
     *  使用当前<code> Graphics2D </code>上下文的设置描绘<code> Shape </code>的轮廓。
     * 应用的呈现属性包括<code> Clip </code>,<code> Transform </code>,<code> Paint </code>,<code> Composite </code>和<code>
     *  Stroke </code>属性。
     *  使用当前<code> Graphics2D </code>上下文的设置描绘<code> Shape </code>的轮廓。
     * 
     * 
     * @param s the <code>Shape</code> to be rendered
     * @see #setStroke
     * @see #setPaint
     * @see java.awt.Graphics#setColor
     * @see #transform
     * @see #setTransform
     * @see #clip
     * @see #setClip
     * @see #setComposite
     */
    public abstract void draw(Shape s);

    /**
     * Renders an image, applying a transform from image space into user space
     * before drawing.
     * The transformation from user space into device space is done with
     * the current <code>Transform</code> in the <code>Graphics2D</code>.
     * The specified transformation is applied to the image before the
     * transform attribute in the <code>Graphics2D</code> context is applied.
     * The rendering attributes applied include the <code>Clip</code>,
     * <code>Transform</code>, and <code>Composite</code> attributes.
     * Note that no rendering is done if the specified transform is
     * noninvertible.
     * <p>
     *  渲染图像,在绘制之前将图像空间的变换应用于用户空间。从用户空间到设备空间的转换是使用<code> Graphics2D </code>中的当前<code> Transform </code>完成的。
     * 在应用<code> Graphics2D </code>上下文中的transform属性之前,将指定的变换应用于图像。
     * 应用的呈现属性包括<code> Clip </code>,<code> Transform </code>和<code> Composite </code>属性。
     * 请注意,如果指定的转换不可逆,则不会执行呈现。
     * 
     * 
     * @param img the specified image to be rendered.
     *            This method does nothing if <code>img</code> is null.
     * @param xform the transformation from image space into user space
     * @param obs the {@link ImageObserver}
     * to be notified as more of the <code>Image</code>
     * is converted
     * @return <code>true</code> if the <code>Image</code> is
     * fully loaded and completely rendered, or if it's null;
     * <code>false</code> if the <code>Image</code> is still being loaded.
     * @see #transform
     * @see #setTransform
     * @see #setComposite
     * @see #clip
     * @see #setClip
     */
    public abstract boolean drawImage(Image img,
                                      AffineTransform xform,
                                      ImageObserver obs);

    /**
     * Renders a <code>BufferedImage</code> that is
     * filtered with a
     * {@link BufferedImageOp}.
     * The rendering attributes applied include the <code>Clip</code>,
     * <code>Transform</code>
     * and <code>Composite</code> attributes.  This is equivalent to:
     * <pre>
     * img1 = op.filter(img, null);
     * drawImage(img1, new AffineTransform(1f,0f,0f,1f,x,y), null);
     * </pre>
     * <p>
     *  呈现使用{@link BufferedImageOp}过滤的<code> BufferedImage </code>。
     * 应用的呈现属性包括<code> Clip </code>,<code> Transform </code>和<code> Composite </code>属性。这相当于：。
     * <pre>
     * img1 = op.filter(img,null); drawImage(img1,new AffineTransform(1f,0f,0f,1f,x,y),null);
     * </pre>
     * 
     * @param op the filter to be applied to the image before rendering
     * @param img the specified <code>BufferedImage</code> to be rendered.
     *            This method does nothing if <code>img</code> is null.
     * @param x the x coordinate of the location in user space where
     * the upper left corner of the image is rendered
     * @param y the y coordinate of the location in user space where
     * the upper left corner of the image is rendered
     *
     * @see #transform
     * @see #setTransform
     * @see #setComposite
     * @see #clip
     * @see #setClip
     */
    public abstract void drawImage(BufferedImage img,
                                   BufferedImageOp op,
                                   int x,
                                   int y);

    /**
     * Renders a {@link RenderedImage},
     * applying a transform from image
     * space into user space before drawing.
     * The transformation from user space into device space is done with
     * the current <code>Transform</code> in the <code>Graphics2D</code>.
     * The specified transformation is applied to the image before the
     * transform attribute in the <code>Graphics2D</code> context is applied.
     * The rendering attributes applied include the <code>Clip</code>,
     * <code>Transform</code>, and <code>Composite</code> attributes. Note
     * that no rendering is done if the specified transform is
     * noninvertible.
     * <p>
     *  渲染{@link RenderedImage},在绘制之前将图像空间的变换应用于用户空间。
     * 从用户空间到设备空间的转换是使用<code> Graphics2D </code>中的当前<code> Transform </code>完成的。
     * 在应用<code> Graphics2D </code>上下文中的transform属性之前,将指定的变换应用于图像。
     * 应用的呈现属性包括<code> Clip </code>,<code> Transform </code>和<code> Composite </code>属性。
     * 请注意,如果指定的转换不可逆,则不会执行呈现。
     * 
     * 
     * @param img the image to be rendered. This method does
     *            nothing if <code>img</code> is null.
     * @param xform the transformation from image space into user space
     * @see #transform
     * @see #setTransform
     * @see #setComposite
     * @see #clip
     * @see #setClip
     */
    public abstract void drawRenderedImage(RenderedImage img,
                                           AffineTransform xform);

    /**
     * Renders a
     * {@link RenderableImage},
     * applying a transform from image space into user space before drawing.
     * The transformation from user space into device space is done with
     * the current <code>Transform</code> in the <code>Graphics2D</code>.
     * The specified transformation is applied to the image before the
     * transform attribute in the <code>Graphics2D</code> context is applied.
     * The rendering attributes applied include the <code>Clip</code>,
     * <code>Transform</code>, and <code>Composite</code> attributes. Note
     * that no rendering is done if the specified transform is
     * noninvertible.
     *<p>
     * Rendering hints set on the <code>Graphics2D</code> object might
     * be used in rendering the <code>RenderableImage</code>.
     * If explicit control is required over specific hints recognized by a
     * specific <code>RenderableImage</code>, or if knowledge of which hints
     * are used is required, then a <code>RenderedImage</code> should be
     * obtained directly from the <code>RenderableImage</code>
     * and rendered using
     *{@link #drawRenderedImage(RenderedImage, AffineTransform) drawRenderedImage}.
     * <p>
     *  渲染{@link RenderableImage},在绘制之前将图像空间的变换应用于用户空间。
     * 从用户空间到设备空间的转换是使用<code> Graphics2D </code>中的当前<code> Transform </code>完成的。
     * 在应用<code> Graphics2D </code>上下文中的transform属性之前,将指定的变换应用于图像。
     * 应用的呈现属性包括<code> Clip </code>,<code> Transform </code>和<code> Composite </code>属性。
     * 请注意,如果指定的转换不可逆,则不会执行呈现。
     * p>
     * 在<code> Graphics2D </code>对象上设置的渲染提示可能用于渲染<code> RenderableImage </code>。
     * 如果需要对由特定<code> RenderableImage </code>识别的特定提示进行显式控制,或者如果需要知道使用哪个提示,则应当直接从<code> RenderedImage </code>
     *  > RenderableImage </code>并使用@link #drawRenderedImage(RenderedImage,AffineTransform)drawRenderedImage
     * }渲染。
     * 在<code> Graphics2D </code>对象上设置的渲染提示可能用于渲染<code> RenderableImage </code>。
     * 
     * 
     * @param img the image to be rendered. This method does
     *            nothing if <code>img</code> is null.
     * @param xform the transformation from image space into user space
     * @see #transform
     * @see #setTransform
     * @see #setComposite
     * @see #clip
     * @see #setClip
     * @see #drawRenderedImage
     */
    public abstract void drawRenderableImage(RenderableImage img,
                                             AffineTransform xform);

    /**
     * Renders the text of the specified <code>String</code>, using the
     * current text attribute state in the <code>Graphics2D</code> context.
     * The baseline of the
     * first character is at position (<i>x</i>,&nbsp;<i>y</i>) in
     * the User Space.
     * The rendering attributes applied include the <code>Clip</code>,
     * <code>Transform</code>, <code>Paint</code>, <code>Font</code> and
     * <code>Composite</code> attributes.  For characters in script
     * systems such as Hebrew and Arabic, the glyphs can be rendered from
     * right to left, in which case the coordinate supplied is the
     * location of the leftmost character on the baseline.
     * <p>
     *  使用<code> Graphics2D </code>上下文中的当前text属性状态,呈现指定的<code> String </code>的文本。
     * 第一个字符的基线位于用户空间中的位置(<i> x </i>,<y> y </i>)。
     * 应用的呈现属性包括<code> Clip </code>,<code> Transform </code>,<code> Paint </code>,<code> Font </code>属性。
     * 对于脚本系统中的字符(如希伯来语和阿拉伯语),字形可以从右到左呈现,在这种情况下,提供的坐标是基线上最左边字符的位置。
     * 
     * 
     * @param str the string to be rendered
     * @param x the x coordinate of the location where the
     * <code>String</code> should be rendered
     * @param y the y coordinate of the location where the
     * <code>String</code> should be rendered
     * @throws NullPointerException if <code>str</code> is
     *         <code>null</code>
     * @see         java.awt.Graphics#drawBytes
     * @see         java.awt.Graphics#drawChars
     * @since       JDK1.0
     */
    public abstract void drawString(String str, int x, int y);

    /**
     * Renders the text specified by the specified <code>String</code>,
     * using the current text attribute state in the <code>Graphics2D</code> context.
     * The baseline of the first character is at position
     * (<i>x</i>,&nbsp;<i>y</i>) in the User Space.
     * The rendering attributes applied include the <code>Clip</code>,
     * <code>Transform</code>, <code>Paint</code>, <code>Font</code> and
     * <code>Composite</code> attributes. For characters in script systems
     * such as Hebrew and Arabic, the glyphs can be rendered from right to
     * left, in which case the coordinate supplied is the location of the
     * leftmost character on the baseline.
     * <p>
     * 使用<code> Graphics2D </code>上下文中的当前text属性状态,呈现由指定的<code> String </code>指定的文本。
     * 第一个字符的基线位于用户空间中的位置(<i> x </i>,<y> y </i>)。
     * 应用的呈现属性包括<code> Clip </code>,<code> Transform </code>,<code> Paint </code>,<code> Font </code>属性。
     * 对于脚本系统中的字符(如希伯来语和阿拉伯语),字形可以从右到左呈现,在这种情况下,提供的坐标是基线上最左边字符的位置。
     * 
     * 
     * @param str the <code>String</code> to be rendered
     * @param x the x coordinate of the location where the
     * <code>String</code> should be rendered
     * @param y the y coordinate of the location where the
     * <code>String</code> should be rendered
     * @throws NullPointerException if <code>str</code> is
     *         <code>null</code>
     * @see #setPaint
     * @see java.awt.Graphics#setColor
     * @see java.awt.Graphics#setFont
     * @see #setTransform
     * @see #setComposite
     * @see #setClip
     */
    public abstract void drawString(String str, float x, float y);

    /**
     * Renders the text of the specified iterator applying its attributes
     * in accordance with the specification of the {@link TextAttribute} class.
     * <p>
     * The baseline of the first character is at position
     * (<i>x</i>,&nbsp;<i>y</i>) in User Space.
     * For characters in script systems such as Hebrew and Arabic,
     * the glyphs can be rendered from right to left, in which case the
     * coordinate supplied is the location of the leftmost character
     * on the baseline.
     * <p>
     *  根据{@link TextAttribute}类的规范,渲染指定的迭代器的文本,应用其属性。
     * <p>
     *  第一个字符的基线位于用户空间中的位置(<i> x </i>,<y> y </i>)。
     * 对于脚本系统中的字符(如希伯来语和阿拉伯语),字形可以从右到左呈现,在这种情况下,提供的坐标是基线上最左边字符的位置。
     * 
     * 
     * @param iterator the iterator whose text is to be rendered
     * @param x the x coordinate where the iterator's text is to be
     * rendered
     * @param y the y coordinate where the iterator's text is to be
     * rendered
     * @throws NullPointerException if <code>iterator</code> is
     *         <code>null</code>
     * @see #setPaint
     * @see java.awt.Graphics#setColor
     * @see #setTransform
     * @see #setComposite
     * @see #setClip
     */
    public abstract void drawString(AttributedCharacterIterator iterator,
                                    int x, int y);

    /**
     * Renders the text of the specified iterator applying its attributes
     * in accordance with the specification of the {@link TextAttribute} class.
     * <p>
     * The baseline of the first character is at position
     * (<i>x</i>,&nbsp;<i>y</i>) in User Space.
     * For characters in script systems such as Hebrew and Arabic,
     * the glyphs can be rendered from right to left, in which case the
     * coordinate supplied is the location of the leftmost character
     * on the baseline.
     * <p>
     *  根据{@link TextAttribute}类的规范,渲染指定的迭代器的文本,应用其属性。
     * <p>
     *  第一个字符的基线位于用户空间中的位置(<i> x </i>,<y> y </i>)。
     * 对于脚本系统中的字符(如希伯来语和阿拉伯语),字形可以从右到左呈现,在这种情况下,提供的坐标是基线上最左边字符的位置。
     * 
     * 
     * @param iterator the iterator whose text is to be rendered
     * @param x the x coordinate where the iterator's text is to be
     * rendered
     * @param y the y coordinate where the iterator's text is to be
     * rendered
     * @throws NullPointerException if <code>iterator</code> is
     *         <code>null</code>
     * @see #setPaint
     * @see java.awt.Graphics#setColor
     * @see #setTransform
     * @see #setComposite
     * @see #setClip
     */
    public abstract void drawString(AttributedCharacterIterator iterator,
                                    float x, float y);

    /**
     * Renders the text of the specified
     * {@link GlyphVector} using
     * the <code>Graphics2D</code> context's rendering attributes.
     * The rendering attributes applied include the <code>Clip</code>,
     * <code>Transform</code>, <code>Paint</code>, and
     * <code>Composite</code> attributes.  The <code>GlyphVector</code>
     * specifies individual glyphs from a {@link Font}.
     * The <code>GlyphVector</code> can also contain the glyph positions.
     * This is the fastest way to render a set of characters to the
     * screen.
     * <p>
     * 使用<code> Graphics2D </code>上下文的呈现属性呈现指定的{@link GlyphVector}的文本。
     * 应用的呈现属性包括<code> Clip </code>,<code> Transform </code>,<code> Paint </code>和<code> Composite </code>属性
     * 。
     * 使用<code> Graphics2D </code>上下文的呈现属性呈现指定的{@link GlyphVector}的文本。
     *  <code> GlyphVector </code>从{@link Font}中指定单个字形。 <code> GlyphVector </code>也可以包含字形位置。
     * 这是将一组字符渲染到屏幕的最快方式。
     * 
     * 
     * @param g the <code>GlyphVector</code> to be rendered
     * @param x the x position in User Space where the glyphs should
     * be rendered
     * @param y the y position in User Space where the glyphs should
     * be rendered
     * @throws NullPointerException if <code>g</code> is <code>null</code>.
     *
     * @see java.awt.Font#createGlyphVector
     * @see java.awt.font.GlyphVector
     * @see #setPaint
     * @see java.awt.Graphics#setColor
     * @see #setTransform
     * @see #setComposite
     * @see #setClip
     */
    public abstract void drawGlyphVector(GlyphVector g, float x, float y);

    /**
     * Fills the interior of a <code>Shape</code> using the settings of the
     * <code>Graphics2D</code> context. The rendering attributes applied
     * include the <code>Clip</code>, <code>Transform</code>,
     * <code>Paint</code>, and <code>Composite</code>.
     * <p>
     *  使用<code> Graphics2D </code>上下文的设置填充<code> Shape </code>的内部。
     * 应用的呈现属性包括<code> Clip </code>,<code> Transform </code>,<code> Paint </code>和<code> Composite </code>。
     * 
     * 
     * @param s the <code>Shape</code> to be filled
     * @see #setPaint
     * @see java.awt.Graphics#setColor
     * @see #transform
     * @see #setTransform
     * @see #setComposite
     * @see #clip
     * @see #setClip
     */
    public abstract void fill(Shape s);

    /**
     * Checks whether or not the specified <code>Shape</code> intersects
     * the specified {@link Rectangle}, which is in device
     * space. If <code>onStroke</code> is false, this method checks
     * whether or not the interior of the specified <code>Shape</code>
     * intersects the specified <code>Rectangle</code>.  If
     * <code>onStroke</code> is <code>true</code>, this method checks
     * whether or not the <code>Stroke</code> of the specified
     * <code>Shape</code> outline intersects the specified
     * <code>Rectangle</code>.
     * The rendering attributes taken into account include the
     * <code>Clip</code>, <code>Transform</code>, and <code>Stroke</code>
     * attributes.
     * <p>
     *  检查指定的<code> Shape </code>是否与设备空间中指定的{@link Rectangle}相交。
     * 如果<code> onStroke </code>为false,此方法检查指定的<code> Shape </code>内部是否与指定的<code> Rectangle </code>相交。
     * 如果<code> onStroke </code>是<code> true </code>,则此方法检查指定<code> Shape </code>大纲的<code> Stroke </code>代码>
     *  Rectangle </code>。
     * 如果<code> onStroke </code>为false,此方法检查指定的<code> Shape </code>内部是否与指定的<code> Rectangle </code>相交。
     * 考虑的渲染属性包括<code> Clip </code>,<code> Transform </code>和<code> Stroke </code>属性。
     * 
     * 
     * @param rect the area in device space to check for a hit
     * @param s the <code>Shape</code> to check for a hit
     * @param onStroke flag used to choose between testing the
     * stroked or the filled shape.  If the flag is <code>true</code>, the
     * <code>Stroke</code> outline is tested.  If the flag is
     * <code>false</code>, the filled <code>Shape</code> is tested.
     * @return <code>true</code> if there is a hit; <code>false</code>
     * otherwise.
     * @see #setStroke
     * @see #fill
     * @see #draw
     * @see #transform
     * @see #setTransform
     * @see #clip
     * @see #setClip
     */
    public abstract boolean hit(Rectangle rect,
                                Shape s,
                                boolean onStroke);

    /**
     * Returns the device configuration associated with this
     * <code>Graphics2D</code>.
     * <p>
     *  返回与此<code> Graphics2D </code>关联的设备配置。
     * 
     * 
     * @return the device configuration of this <code>Graphics2D</code>.
     */
    public abstract GraphicsConfiguration getDeviceConfiguration();

    /**
     * Sets the <code>Composite</code> for the <code>Graphics2D</code> context.
     * The <code>Composite</code> is used in all drawing methods such as
     * <code>drawImage</code>, <code>drawString</code>, <code>draw</code>,
     * and <code>fill</code>.  It specifies how new pixels are to be combined
     * with the existing pixels on the graphics device during the rendering
     * process.
     * <p>If this <code>Graphics2D</code> context is drawing to a
     * <code>Component</code> on the display screen and the
     * <code>Composite</code> is a custom object rather than an
     * instance of the <code>AlphaComposite</code> class, and if
     * there is a security manager, its <code>checkPermission</code>
     * method is called with an <code>AWTPermission("readDisplayPixels")</code>
     * permission.
     * <p>
     * 设置<code> Graphics2D </code>上下文的<code> Composite </code>。
     *  <code> Composite </code>用于所有绘图方法,例如<code> drawImage </code>,<code> drawString </code>,<code> draw </code>
     *  / code>。
     * 设置<code> Graphics2D </code>上下文的<code> Composite </code>。其指定在渲染过程期间新像素如何与图形装置上的现有像素组合。
     *  <p>如果这个<code> Graphics2D </code>上下文正在绘制到显示屏幕上的<code> Component </code>,并且<code> Composite </code>是自定
     * 义对象, <code> AlphaComposite </code>类,如果有安全管理器,则会使用<code> AWTPermission("readDisplayPixels")</code>权限调用
     * 其<code> checkPermission </code>方法。
     * 设置<code> Graphics2D </code>上下文的<code> Composite </code>。其指定在渲染过程期间新像素如何与图形装置上的现有像素组合。
     * 
     * 
     * @throws SecurityException
     *         if a custom <code>Composite</code> object is being
     *         used to render to the screen and a security manager
     *         is set and its <code>checkPermission</code> method
     *         does not allow the operation.
     * @param comp the <code>Composite</code> object to be used for rendering
     * @see java.awt.Graphics#setXORMode
     * @see java.awt.Graphics#setPaintMode
     * @see #getComposite
     * @see AlphaComposite
     * @see SecurityManager#checkPermission
     * @see java.awt.AWTPermission
     */
    public abstract void setComposite(Composite comp);

    /**
     * Sets the <code>Paint</code> attribute for the
     * <code>Graphics2D</code> context.  Calling this method
     * with a <code>null</code> <code>Paint</code> object does
     * not have any effect on the current <code>Paint</code> attribute
     * of this <code>Graphics2D</code>.
     * <p>
     *  设置<code> Graphics2D </code>上下文的<code> Paint </code>属性。
     * 使用<code> null </code> <code> Paint </code>对象调用此方法对此<code> Graphics2D </code>的当前<code> Paint </code>属性
     * 没有任何影响。
     *  设置<code> Graphics2D </code>上下文的<code> Paint </code>属性。
     * 
     * 
     * @param paint the <code>Paint</code> object to be used to generate
     * color during the rendering process, or <code>null</code>
     * @see java.awt.Graphics#setColor
     * @see #getPaint
     * @see GradientPaint
     * @see TexturePaint
     */
    public abstract void setPaint( Paint paint );

    /**
     * Sets the <code>Stroke</code> for the <code>Graphics2D</code> context.
     * <p>
     *  为<code> Graphics2D </code>上下文设置<code> Stroke </code>。
     * 
     * 
     * @param s the <code>Stroke</code> object to be used to stroke a
     * <code>Shape</code> during the rendering process
     * @see BasicStroke
     * @see #getStroke
     */
    public abstract void setStroke(Stroke s);

    /**
     * Sets the value of a single preference for the rendering algorithms.
     * Hint categories include controls for rendering quality and overall
     * time/quality trade-off in the rendering process.  Refer to the
     * <code>RenderingHints</code> class for definitions of some common
     * keys and values.
     * <p>
     *  设置渲染算法的单个首选项的值。提示类别包括渲染过程中渲染质量和总体时间/质量权衡的控制。有关一些常用键和值的定义,请参阅<code> RenderingHints </code>类。
     * 
     * 
     * @param hintKey the key of the hint to be set.
     * @param hintValue the value indicating preferences for the specified
     * hint category.
     * @see #getRenderingHint(RenderingHints.Key)
     * @see RenderingHints
     */
    public abstract void setRenderingHint(Key hintKey, Object hintValue);

    /**
     * Returns the value of a single preference for the rendering algorithms.
     * Hint categories include controls for rendering quality and overall
     * time/quality trade-off in the rendering process.  Refer to the
     * <code>RenderingHints</code> class for definitions of some common
     * keys and values.
     * <p>
     * 返回渲染算法的单个首选项的值。提示类别包括渲染过程中渲染质量和总体时间/质量权衡的控制。有关一些常用键和值的定义,请参阅<code> RenderingHints </code>类。
     * 
     * 
     * @param hintKey the key corresponding to the hint to get.
     * @return an object representing the value for the specified hint key.
     * Some of the keys and their associated values are defined in the
     * <code>RenderingHints</code> class.
     * @see RenderingHints
     * @see #setRenderingHint(RenderingHints.Key, Object)
     */
    public abstract Object getRenderingHint(Key hintKey);

    /**
     * Replaces the values of all preferences for the rendering
     * algorithms with the specified <code>hints</code>.
     * The existing values for all rendering hints are discarded and
     * the new set of known hints and values are initialized from the
     * specified {@link Map} object.
     * Hint categories include controls for rendering quality and
     * overall time/quality trade-off in the rendering process.
     * Refer to the <code>RenderingHints</code> class for definitions of
     * some common keys and values.
     * <p>
     *  用指定的<code> hints </code>替换呈现算法的所有首选项的值。将丢弃所有呈现提示的现有值,并从指定的{@link Map}对象初始化新的已知提示和值集。
     * 提示类别包括渲染过程中渲染质量和总体时间/质量权衡的控制。有关一些常用键和值的定义,请参阅<code> RenderingHints </code>类。
     * 
     * 
     * @param hints the rendering hints to be set
     * @see #getRenderingHints
     * @see RenderingHints
     */
    public abstract void setRenderingHints(Map<?,?> hints);

    /**
     * Sets the values of an arbitrary number of preferences for the
     * rendering algorithms.
     * Only values for the rendering hints that are present in the
     * specified <code>Map</code> object are modified.
     * All other preferences not present in the specified
     * object are left unmodified.
     * Hint categories include controls for rendering quality and
     * overall time/quality trade-off in the rendering process.
     * Refer to the <code>RenderingHints</code> class for definitions of
     * some common keys and values.
     * <p>
     *  为渲染算法设置任意数量的首选项的值。只有指定的<code> Map </code>对象中呈现提示的值被修改。指定对象中不存在的所有其他首选项保持不变。
     * 提示类别包括渲染过程中渲染质量和总体时间/质量权衡的控制。有关一些常用键和值的定义,请参阅<code> RenderingHints </code>类。
     * 
     * 
     * @param hints the rendering hints to be set
     * @see RenderingHints
     */
    public abstract void addRenderingHints(Map<?,?> hints);

    /**
     * Gets the preferences for the rendering algorithms.  Hint categories
     * include controls for rendering quality and overall time/quality
     * trade-off in the rendering process.
     * Returns all of the hint key/value pairs that were ever specified in
     * one operation.  Refer to the
     * <code>RenderingHints</code> class for definitions of some common
     * keys and values.
     * <p>
     * 获取渲染算法的首选项。提示类别包括渲染过程中渲染质量和总体时间/质量权衡的控制。返回在一个操作中曾指定的所有提示键/值对。
     * 有关一些常用键和值的定义,请参阅<code> RenderingHints </code>类。
     * 
     * 
     * @return a reference to an instance of <code>RenderingHints</code>
     * that contains the current preferences.
     * @see RenderingHints
     * @see #setRenderingHints(Map)
     */
    public abstract RenderingHints getRenderingHints();

    /**
     * Translates the origin of the <code>Graphics2D</code> context to the
     * point (<i>x</i>,&nbsp;<i>y</i>) in the current coordinate system.
     * Modifies the <code>Graphics2D</code> context so that its new origin
     * corresponds to the point (<i>x</i>,&nbsp;<i>y</i>) in the
     * <code>Graphics2D</code> context's former coordinate system.  All
     * coordinates used in subsequent rendering operations on this graphics
     * context are relative to this new origin.
     * <p>
     *  将<code> Graphics2D </code>上下文的原点转换为当前坐标系中的点(<i> x </i>,<y> y </i>)。
     * 修改<code> Graphics2D </code>上下文,使其新来源对应于<code> Graphics2D </code中的点(<i> x </i>,&nbsp; <i> y </i> >上下文的
     * 前坐标系。
     *  将<code> Graphics2D </code>上下文的原点转换为当前坐标系中的点(<i> x </i>,<y> y </i>)。
     * 在此图形上下文上的后续渲染操作中使用的所有坐标都是相对于这个新的原点的。
     * 
     * 
     * @param  x the specified x coordinate
     * @param  y the specified y coordinate
     * @since   JDK1.0
     */
    public abstract void translate(int x, int y);

    /**
     * Concatenates the current
     * <code>Graphics2D</code> <code>Transform</code>
     * with a translation transform.
     * Subsequent rendering is translated by the specified
     * distance relative to the previous position.
     * This is equivalent to calling transform(T), where T is an
     * <code>AffineTransform</code> represented by the following matrix:
     * <pre>
     *          [   1    0    tx  ]
     *          [   0    1    ty  ]
     *          [   0    0    1   ]
     * </pre>
     * <p>
     *  将当前<code> Graphics2D </code> <code> Transform </code>与翻译变换连接。后续渲染将相对于前一个位置平移指定的距离。
     * 这等同于调用transform(T),其中T是由以下矩阵表示的<code> AffineTransform </code>：。
     * <pre>
     *  [1 0 tx] [0 1 ty] [0 0 1]
     * </pre>
     * 
     * @param tx the distance to translate along the x-axis
     * @param ty the distance to translate along the y-axis
     */
    public abstract void translate(double tx, double ty);

    /**
     * Concatenates the current <code>Graphics2D</code>
     * <code>Transform</code> with a rotation transform.
     * Subsequent rendering is rotated by the specified radians relative
     * to the previous origin.
     * This is equivalent to calling <code>transform(R)</code>, where R is an
     * <code>AffineTransform</code> represented by the following matrix:
     * <pre>
     *          [   cos(theta)    -sin(theta)    0   ]
     *          [   sin(theta)     cos(theta)    0   ]
     *          [       0              0         1   ]
     * </pre>
     * Rotating with a positive angle theta rotates points on the positive
     * x axis toward the positive y axis.
     * <p>
     *  将当前<code> Graphics2D </code> <code> Transform </code>与旋转变换连接。随后的渲染以指定的弧度相对于先前的原点旋转。
     * 这等效于调用<code> transform(R)</code>,其中R是由以下矩阵表示的<code> AffineTransform </code>。
     * <pre>
     * [cos(theta)-sin(theta)0] [sin(theta)cos(theta)0]
     * </pre>
     *  以正角度θ旋转使正x轴上的点朝向正y轴旋转。
     * 
     * 
     * @param theta the angle of rotation in radians
     */
    public abstract void rotate(double theta);

    /**
     * Concatenates the current <code>Graphics2D</code>
     * <code>Transform</code> with a translated rotation
     * transform.  Subsequent rendering is transformed by a transform
     * which is constructed by translating to the specified location,
     * rotating by the specified radians, and translating back by the same
     * amount as the original translation.  This is equivalent to the
     * following sequence of calls:
     * <pre>
     *          translate(x, y);
     *          rotate(theta);
     *          translate(-x, -y);
     * </pre>
     * Rotating with a positive angle theta rotates points on the positive
     * x axis toward the positive y axis.
     * <p>
     *  将当前的<code> Graphics2D </code> <code> Transform </code>与转换的旋转变换连接。
     * 随后的渲染通过变换来变换,该变换通过平移到指定位置,旋转指定的弧度并且以与原始平移相同的量平移而构造。这相当于以下的调用序列：。
     * <pre>
     *  translate(x,y);旋转(θ); translate(-x,-y);
     * </pre>
     *  以正角度θ旋转使正x轴上的点朝向正y轴旋转。
     * 
     * 
     * @param theta the angle of rotation in radians
     * @param x the x coordinate of the origin of the rotation
     * @param y the y coordinate of the origin of the rotation
     */
    public abstract void rotate(double theta, double x, double y);

    /**
     * Concatenates the current <code>Graphics2D</code>
     * <code>Transform</code> with a scaling transformation
     * Subsequent rendering is resized according to the specified scaling
     * factors relative to the previous scaling.
     * This is equivalent to calling <code>transform(S)</code>, where S is an
     * <code>AffineTransform</code> represented by the following matrix:
     * <pre>
     *          [   sx   0    0   ]
     *          [   0    sy   0   ]
     *          [   0    0    1   ]
     * </pre>
     * <p>
     *  将当前<code> Graphics2D </code> <code> Transform </code>与缩放转换连接。根据相对于先前缩放的指定缩放因子,调整后续呈现的大小。
     * 这相当于调用<code> transform(S)</code>,其中S是由以下矩阵表示的<code> AffineTransform </code>：。
     * <pre>
     *  [sx 0 0] [0 sy 0] [0 0 1]
     * </pre>
     * 
     * @param sx the amount by which X coordinates in subsequent
     * rendering operations are multiplied relative to previous
     * rendering operations.
     * @param sy the amount by which Y coordinates in subsequent
     * rendering operations are multiplied relative to previous
     * rendering operations.
     */
    public abstract void scale(double sx, double sy);

    /**
     * Concatenates the current <code>Graphics2D</code>
     * <code>Transform</code> with a shearing transform.
     * Subsequent renderings are sheared by the specified
     * multiplier relative to the previous position.
     * This is equivalent to calling <code>transform(SH)</code>, where SH
     * is an <code>AffineTransform</code> represented by the following
     * matrix:
     * <pre>
     *          [   1   shx   0   ]
     *          [  shy   1    0   ]
     *          [   0    0    1   ]
     * </pre>
     * <p>
     * 使用剪切变换连接当前<code> Graphics2D </code> <code> Transform </code>。随后的渲染由相对于前一个位置的指定乘数剪切。
     * 这等同于调用<code> transform(SH)</code>,其中SH是由以下矩阵表示的<code> AffineTransform </code>。
     * <pre>
     *  [1 shx 0] [shy 1 0] [0 0 1]
     * </pre>
     * 
     * @param shx the multiplier by which coordinates are shifted in
     * the positive X axis direction as a function of their Y coordinate
     * @param shy the multiplier by which coordinates are shifted in
     * the positive Y axis direction as a function of their X coordinate
     */
    public abstract void shear(double shx, double shy);

    /**
     * Composes an <code>AffineTransform</code> object with the
     * <code>Transform</code> in this <code>Graphics2D</code> according
     * to the rule last-specified-first-applied.  If the current
     * <code>Transform</code> is Cx, the result of composition
     * with Tx is a new <code>Transform</code> Cx'.  Cx' becomes the
     * current <code>Transform</code> for this <code>Graphics2D</code>.
     * Transforming a point p by the updated <code>Transform</code> Cx' is
     * equivalent to first transforming p by Tx and then transforming
     * the result by the original <code>Transform</code> Cx.  In other
     * words, Cx'(p) = Cx(Tx(p)).  A copy of the Tx is made, if necessary,
     * so further modifications to Tx do not affect rendering.
     * <p>
     *  根据最后指定的第一次应用的规则,在<code> Graphics2D </code>中的<code> Transform </code>中构建<code> AffineTransform </code>
     * 对象。
     * 如果当前<code> Transform </code>是Cx,则与Tx组合的结果是新的<code> Transform </code> Cx'。
     *  Cx'成为这个<code> Graphics2D </code>的当前<code> Transform </code>。
     * 通过更新的<code> Transform </code> Cx'变换点p等效于首先通过Tx变换p,然后通过原始<code> Transform </code> Cx变换结果。
     * 换句话说,Cx'(p)= Cx(Tx(p))。如果需要,进行Tx的副本,因此对Tx的进一步修改不影响呈现。
     * 
     * 
     * @param Tx the <code>AffineTransform</code> object to be composed with
     * the current <code>Transform</code>
     * @see #setTransform
     * @see AffineTransform
     */
    public abstract void transform(AffineTransform Tx);

    /**
     * Overwrites the Transform in the <code>Graphics2D</code> context.
     * WARNING: This method should <b>never</b> be used to apply a new
     * coordinate transform on top of an existing transform because the
     * <code>Graphics2D</code> might already have a transform that is
     * needed for other purposes, such as rendering Swing
     * components or applying a scaling transformation to adjust for the
     * resolution of a printer.
     * <p>To add a coordinate transform, use the
     * <code>transform</code>, <code>rotate</code>, <code>scale</code>,
     * or <code>shear</code> methods.  The <code>setTransform</code>
     * method is intended only for restoring the original
     * <code>Graphics2D</code> transform after rendering, as shown in this
     * example:
     * <pre>
     * // Get the current transform
     * AffineTransform saveAT = g2.getTransform();
     * // Perform transformation
     * g2d.transform(...);
     * // Render
     * g2d.draw(...);
     * // Restore original transform
     * g2d.setTransform(saveAT);
     * </pre>
     *
     * <p>
     * 覆盖<code> Graphics2D </code>上下文中的变换。
     * 警告：此方法应<b>从不</b>用于在现有变换之上应用新的坐标变换,因为<code> Graphics2D </code>可能已有其他用途所需的变换,例如作为渲染Swing组件或应用缩放变换来调整打印机
     * 的分辨率。
     * 覆盖<code> Graphics2D </code>上下文中的变换。
     *  <p>要添加坐标变换,请使用<code> transform </code>,<code> rotate </code>,<code> scale </code>或<code> shear </code>
     * 方法。
     * 覆盖<code> Graphics2D </code>上下文中的变换。
     *  <code> setTransform </code>方法仅用于在渲染后恢复原始的<code> Graphics2D </code>变换,如下例所示：。
     * <pre>
     *  //获取当前变换AffineTransform saveAT = g2.getTransform(); // Perform transformation g2d.transform(...); //
     *  Render g2d.draw(...); // Restore original transform g2d.setTransform(saveAT);。
     * </pre>
     * 
     * 
     * @param Tx the <code>AffineTransform</code> that was retrieved
     *           from the <code>getTransform</code> method
     * @see #transform
     * @see #getTransform
     * @see AffineTransform
     */
    public abstract void setTransform(AffineTransform Tx);

    /**
     * Returns a copy of the current <code>Transform</code> in the
     * <code>Graphics2D</code> context.
     * <p>
     *  返回<code> Graphics2D </code>上下文中当前<code> Transform </code>的副本。
     * 
     * 
     * @return the current <code>AffineTransform</code> in the
     *             <code>Graphics2D</code> context.
     * @see #transform
     * @see #setTransform
     */
    public abstract AffineTransform getTransform();

    /**
     * Returns the current <code>Paint</code> of the
     * <code>Graphics2D</code> context.
     * <p>
     *  返回<code> Graphics2D </code>上下文的当前<code> Paint </code>。
     * 
     * 
     * @return the current <code>Graphics2D</code> <code>Paint</code>,
     * which defines a color or pattern.
     * @see #setPaint
     * @see java.awt.Graphics#setColor
     */
    public abstract Paint getPaint();

    /**
     * Returns the current <code>Composite</code> in the
     * <code>Graphics2D</code> context.
     * <p>
     *  返回<code> Graphics2D </code>上下文中的当前<code> Composite </code>。
     * 
     * 
     * @return the current <code>Graphics2D</code> <code>Composite</code>,
     *              which defines a compositing style.
     * @see #setComposite
     */
    public abstract Composite getComposite();

    /**
     * Sets the background color for the <code>Graphics2D</code> context.
     * The background color is used for clearing a region.
     * When a <code>Graphics2D</code> is constructed for a
     * <code>Component</code>, the background color is
     * inherited from the <code>Component</code>. Setting the background color
     * in the <code>Graphics2D</code> context only affects the subsequent
     * <code>clearRect</code> calls and not the background color of the
     * <code>Component</code>.  To change the background
     * of the <code>Component</code>, use appropriate methods of
     * the <code>Component</code>.
     * <p>
     * 设置<code> Graphics2D </code>上下文的背景颜色。背景颜色用于清除区域。
     * 当为<code> Component </code>构造<code> Graphics2D </code>时,背景颜色继承自<code> Component </code>。
     * 在<code> Graphics2D </code>上下文中设置背景颜色仅影响后续的<code> clearRect </code>调用,而不影响<code> Component </code>的背景颜
     * 色。
     * 当为<code> Component </code>构造<code> Graphics2D </code>时,背景颜色继承自<code> Component </code>。
     * 要更改<code> Component </code>的背景,请使用<code> Component </code>的适当方法。
     * 
     * 
     * @param color the background color that is used in
     * subsequent calls to <code>clearRect</code>
     * @see #getBackground
     * @see java.awt.Graphics#clearRect
     */
    public abstract void setBackground(Color color);

    /**
     * Returns the background color used for clearing a region.
     * <p>
     *  返回用于清除区域的背景颜色。
     * 
     * 
     * @return the current <code>Graphics2D</code> <code>Color</code>,
     * which defines the background color.
     * @see #setBackground
     */
    public abstract Color getBackground();

    /**
     * Returns the current <code>Stroke</code> in the
     * <code>Graphics2D</code> context.
     * <p>
     *  返回<code> Graphics2D </code>上下文中的当前<code> Stroke </code>。
     * 
     * 
     * @return the current <code>Graphics2D</code> <code>Stroke</code>,
     *                 which defines the line style.
     * @see #setStroke
     */
    public abstract Stroke getStroke();

    /**
     * Intersects the current <code>Clip</code> with the interior of the
     * specified <code>Shape</code> and sets the <code>Clip</code> to the
     * resulting intersection.  The specified <code>Shape</code> is
     * transformed with the current <code>Graphics2D</code>
     * <code>Transform</code> before being intersected with the current
     * <code>Clip</code>.  This method is used to make the current
     * <code>Clip</code> smaller.
     * To make the <code>Clip</code> larger, use <code>setClip</code>.
     * The <i>user clip</i> modified by this method is independent of the
     * clipping associated with device bounds and visibility.  If no clip has
     * previously been set, or if the clip has been cleared using
     * {@link Graphics#setClip(Shape) setClip} with a <code>null</code>
     * argument, the specified <code>Shape</code> becomes the new
     * user clip.
     * <p>
     *  将当前<code> Clip </code>与指定的<code> Shape </code>内部交叉,并将<code> Clip </code>设置为生成的交集。
     * 在与当前<code> Clip </code>相交之前,用当前<code> Graphics2D </code> <code> Transform </code>变换指定的<code> Shape </code>
     * 此方法用于使当前<code> Clip </code>更小。
     *  将当前<code> Clip </code>与指定的<code> Shape </code>内部交叉,并将<code> Clip </code>设置为生成的交集。
     * 要使<code> Clip </code>更大,请使用<code> setClip </code>。通过此方法修改的<i>用户剪辑</i>与与设备边界和可见性相关联的裁剪无关。
     * 如果先前没有设置剪辑,或者使用{@link Graphics#setClip(Shape)setClip}和<code> null </code>参数清除剪辑,则指定的<code> Shape </code>
     * 新用户剪辑。
     * 要使<code> Clip </code>更大,请使用<code> setClip </code>。通过此方法修改的<i>用户剪辑</i>与与设备边界和可见性相关联的裁剪无关。
     * 
     * @param s the <code>Shape</code> to be intersected with the current
     *          <code>Clip</code>.  If <code>s</code> is <code>null</code>,
     *          this method clears the current <code>Clip</code>.
     */
     public abstract void clip(Shape s);

     /**
     * Get the rendering context of the <code>Font</code> within this
     * <code>Graphics2D</code> context.
     * The {@link FontRenderContext}
     * encapsulates application hints such as anti-aliasing and
     * fractional metrics, as well as target device specific information
     * such as dots-per-inch.  This information should be provided by the
     * application when using objects that perform typographical
     * formatting, such as <code>Font</code> and
     * <code>TextLayout</code>.  This information should also be provided
     * by applications that perform their own layout and need accurate
     * measurements of various characteristics of glyphs such as advance
     * and line height when various rendering hints have been applied to
     * the text rendering.
     *
     * <p>
     * 
     * 
     * @return a reference to an instance of FontRenderContext.
     * @see java.awt.font.FontRenderContext
     * @see java.awt.Font#createGlyphVector
     * @see java.awt.font.TextLayout
     * @since     1.2
     */

    public abstract FontRenderContext getFontRenderContext();

}
