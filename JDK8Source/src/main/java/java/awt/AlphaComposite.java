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

import java.awt.image.ColorModel;
import java.lang.annotation.Native;
import sun.java2d.SunCompositeContext;

/**
 * The <code>AlphaComposite</code> class implements basic alpha
 * compositing rules for combining source and destination colors
 * to achieve blending and transparency effects with graphics and
 * images.
 * The specific rules implemented by this class are the basic set
 * of 12 rules described in
 * T. Porter and T. Duff, "Compositing Digital Images", SIGGRAPH 84,
 * 253-259.
 * The rest of this documentation assumes some familiarity with the
 * definitions and concepts outlined in that paper.
 *
 * <p>
 * This class extends the standard equations defined by Porter and
 * Duff to include one additional factor.
 * An instance of the <code>AlphaComposite</code> class can contain
 * an alpha value that is used to modify the opacity or coverage of
 * every source pixel before it is used in the blending equations.
 *
 * <p>
 * It is important to note that the equations defined by the Porter
 * and Duff paper are all defined to operate on color components
 * that are premultiplied by their corresponding alpha components.
 * Since the <code>ColorModel</code> and <code>Raster</code> classes
 * allow the storage of pixel data in either premultiplied or
 * non-premultiplied form, all input data must be normalized into
 * premultiplied form before applying the equations and all results
 * might need to be adjusted back to the form required by the destination
 * before the pixel values are stored.
 *
 * <p>
 * Also note that this class defines only the equations
 * for combining color and alpha values in a purely mathematical
 * sense. The accurate application of its equations depends
 * on the way the data is retrieved from its sources and stored
 * in its destinations.
 * See <a href="#caveats">Implementation Caveats</a>
 * for further information.
 *
 * <p>
 * The following factors are used in the description of the blending
 * equation in the Porter and Duff paper:
 *
 * <blockquote>
 * <table summary="layout">
 * <tr><th align=left>Factor&nbsp;&nbsp;<th align=left>Definition
 * <tr><td><em>A<sub>s</sub></em><td>the alpha component of the source pixel
 * <tr><td><em>C<sub>s</sub></em><td>a color component of the source pixel in premultiplied form
 * <tr><td><em>A<sub>d</sub></em><td>the alpha component of the destination pixel
 * <tr><td><em>C<sub>d</sub></em><td>a color component of the destination pixel in premultiplied form
 * <tr><td><em>F<sub>s</sub></em><td>the fraction of the source pixel that contributes to the output
 * <tr><td><em>F<sub>d</sub></em><td>the fraction of the destination pixel that contributes
 * to the output
 * <tr><td><em>A<sub>r</sub></em><td>the alpha component of the result
 * <tr><td><em>C<sub>r</sub></em><td>a color component of the result in premultiplied form
 * </table>
 * </blockquote>
 *
 * <p>
 * Using these factors, Porter and Duff define 12 ways of choosing
 * the blending factors <em>F<sub>s</sub></em> and <em>F<sub>d</sub></em> to
 * produce each of 12 desirable visual effects.
 * The equations for determining <em>F<sub>s</sub></em> and <em>F<sub>d</sub></em>
 * are given in the descriptions of the 12 static fields
 * that specify visual effects.
 * For example,
 * the description for
 * <a href="#SRC_OVER"><code>SRC_OVER</code></a>
 * specifies that <em>F<sub>s</sub></em> = 1 and <em>F<sub>d</sub></em> = (1-<em>A<sub>s</sub></em>).
 * Once a set of equations for determining the blending factors is
 * known they can then be applied to each pixel to produce a result
 * using the following set of equations:
 *
 * <pre>
 *      <em>F<sub>s</sub></em> = <em>f</em>(<em>A<sub>d</sub></em>)
 *      <em>F<sub>d</sub></em> = <em>f</em>(<em>A<sub>s</sub></em>)
 *      <em>A<sub>r</sub></em> = <em>A<sub>s</sub></em>*<em>F<sub>s</sub></em> + <em>A<sub>d</sub></em>*<em>F<sub>d</sub></em>
 *      <em>C<sub>r</sub></em> = <em>C<sub>s</sub></em>*<em>F<sub>s</sub></em> + <em>C<sub>d</sub></em>*<em>F<sub>d</sub></em></pre>
 *
 * <p>
 * The following factors will be used to discuss our extensions to
 * the blending equation in the Porter and Duff paper:
 *
 * <blockquote>
 * <table summary="layout">
 * <tr><th align=left>Factor&nbsp;&nbsp;<th align=left>Definition
 * <tr><td><em>C<sub>sr</sub></em> <td>one of the raw color components of the source pixel
 * <tr><td><em>C<sub>dr</sub></em> <td>one of the raw color components of the destination pixel
 * <tr><td><em>A<sub>ac</sub></em>  <td>the "extra" alpha component from the AlphaComposite instance
 * <tr><td><em>A<sub>sr</sub></em> <td>the raw alpha component of the source pixel
 * <tr><td><em>A<sub>dr</sub></em><td>the raw alpha component of the destination pixel
 * <tr><td><em>A<sub>df</sub></em> <td>the final alpha component stored in the destination
 * <tr><td><em>C<sub>df</sub></em> <td>the final raw color component stored in the destination
 * </table>
 *</blockquote>
 *
 * <h3>Preparing Inputs</h3>
 *
 * <p>
 * The <code>AlphaComposite</code> class defines an additional alpha
 * value that is applied to the source alpha.
 * This value is applied as if an implicit SRC_IN rule were first
 * applied to the source pixel against a pixel with the indicated
 * alpha by multiplying both the raw source alpha and the raw
 * source colors by the alpha in the <code>AlphaComposite</code>.
 * This leads to the following equation for producing the alpha
 * used in the Porter and Duff blending equation:
 *
 * <pre>
 *      <em>A<sub>s</sub></em> = <em>A<sub>sr</sub></em> * <em>A<sub>ac</sub></em> </pre>
 *
 * All of the raw source color components need to be multiplied
 * by the alpha in the <code>AlphaComposite</code> instance.
 * Additionally, if the source was not in premultiplied form
 * then the color components also need to be multiplied by the
 * source alpha.
 * Thus, the equation for producing the source color components
 * for the Porter and Duff equation depends on whether the source
 * pixels are premultiplied or not:
 *
 * <pre>
 *      <em>C<sub>s</sub></em> = <em>C<sub>sr</sub></em> * <em>A<sub>sr</sub></em> * <em>A<sub>ac</sub></em>     (if source is not premultiplied)
 *      <em>C<sub>s</sub></em> = <em>C<sub>sr</sub></em> * <em>A<sub>ac</sub></em>           (if source is premultiplied) </pre>
 *
 * No adjustment needs to be made to the destination alpha:
 *
 * <pre>
 *      <em>A<sub>d</sub></em> = <em>A<sub>dr</sub></em> </pre>
 *
 * <p>
 * The destination color components need to be adjusted only if
 * they are not in premultiplied form:
 *
 * <pre>
 *      <em>C<sub>d</sub></em> = <em>C<sub>dr</sub></em> * <em>A<sub>d</sub></em>    (if destination is not premultiplied)
 *      <em>C<sub>d</sub></em> = <em>C<sub>dr</sub></em>         (if destination is premultiplied) </pre>
 *
 * <h3>Applying the Blending Equation</h3>
 *
 * <p>
 * The adjusted <em>A<sub>s</sub></em>, <em>A<sub>d</sub></em>,
 * <em>C<sub>s</sub></em>, and <em>C<sub>d</sub></em> are used in the standard
 * Porter and Duff equations to calculate the blending factors
 * <em>F<sub>s</sub></em> and <em>F<sub>d</sub></em> and then the resulting
 * premultiplied components <em>A<sub>r</sub></em> and <em>C<sub>r</sub></em>.
 *
 * <h3>Preparing Results</h3>
 *
 * <p>
 * The results only need to be adjusted if they are to be stored
 * back into a destination buffer that holds data that is not
 * premultiplied, using the following equations:
 *
 * <pre>
 *      <em>A<sub>df</sub></em> = <em>A<sub>r</sub></em>
 *      <em>C<sub>df</sub></em> = <em>C<sub>r</sub></em>                 (if dest is premultiplied)
 *      <em>C<sub>df</sub></em> = <em>C<sub>r</sub></em> / <em>A<sub>r</sub></em>            (if dest is not premultiplied) </pre>
 *
 * Note that since the division is undefined if the resulting alpha
 * is zero, the division in that case is omitted to avoid the "divide
 * by zero" and the color components are left as
 * all zeros.
 *
 * <h3>Performance Considerations</h3>
 *
 * <p>
 * For performance reasons, it is preferable that
 * <code>Raster</code> objects passed to the <code>compose</code>
 * method of a {@link CompositeContext} object created by the
 * <code>AlphaComposite</code> class have premultiplied data.
 * If either the source <code>Raster</code>
 * or the destination <code>Raster</code>
 * is not premultiplied, however,
 * appropriate conversions are performed before and after the compositing
 * operation.
 *
 * <h3><a name="caveats">Implementation Caveats</a></h3>
 *
 * <ul>
 * <li>
 * Many sources, such as some of the opaque image types listed
 * in the <code>BufferedImage</code> class, do not store alpha values
 * for their pixels.  Such sources supply an alpha of 1.0 for
 * all of their pixels.
 *
 * <li>
 * Many destinations also have no place to store the alpha values
 * that result from the blending calculations performed by this class.
 * Such destinations thus implicitly discard the resulting
 * alpha values that this class produces.
 * It is recommended that such destinations should treat their stored
 * color values as non-premultiplied and divide the resulting color
 * values by the resulting alpha value before storing the color
 * values and discarding the alpha value.
 *
 * <li>
 * The accuracy of the results depends on the manner in which pixels
 * are stored in the destination.
 * An image format that provides at least 8 bits of storage per color
 * and alpha component is at least adequate for use as a destination
 * for a sequence of a few to a dozen compositing operations.
 * An image format with fewer than 8 bits of storage per component
 * is of limited use for just one or two compositing operations
 * before the rounding errors dominate the results.
 * An image format
 * that does not separately store
 * color components is not a
 * good candidate for any type of translucent blending.
 * For example, <code>BufferedImage.TYPE_BYTE_INDEXED</code>
 * should not be used as a destination for a blending operation
 * because every operation
 * can introduce large errors, due to
 * the need to choose a pixel from a limited palette to match the
 * results of the blending equations.
 *
 * <li>
 * Nearly all formats store pixels as discrete integers rather than
 * the floating point values used in the reference equations above.
 * The implementation can either scale the integer pixel
 * values into floating point values in the range 0.0 to 1.0 or
 * use slightly modified versions of the equations
 * that operate entirely in the integer domain and yet produce
 * analogous results to the reference equations.
 *
 * <p>
 * Typically the integer values are related to the floating point
 * values in such a way that the integer 0 is equated
 * to the floating point value 0.0 and the integer
 * 2^<em>n</em>-1 (where <em>n</em> is the number of bits
 * in the representation) is equated to 1.0.
 * For 8-bit representations, this means that 0x00
 * represents 0.0 and 0xff represents
 * 1.0.
 *
 * <li>
 * The internal implementation can approximate some of the equations
 * and it can also eliminate some steps to avoid unnecessary operations.
 * For example, consider a discrete integer image with non-premultiplied
 * alpha values that uses 8 bits per component for storage.
 * The stored values for a
 * nearly transparent darkened red might be:
 *
 * <pre>
 *    (A, R, G, B) = (0x01, 0xb0, 0x00, 0x00)</pre>
 *
 * <p>
 * If integer math were being used and this value were being
 * composited in
 * <a href="#SRC"><code>SRC</code></a>
 * mode with no extra alpha, then the math would
 * indicate that the results were (in integer format):
 *
 * <pre>
 *    (A, R, G, B) = (0x01, 0x01, 0x00, 0x00)</pre>
 *
 * <p>
 * Note that the intermediate values, which are always in premultiplied
 * form, would only allow the integer red component to be either 0x00
 * or 0x01.  When we try to store this result back into a destination
 * that is not premultiplied, dividing out the alpha will give us
 * very few choices for the non-premultiplied red value.
 * In this case an implementation that performs the math in integer
 * space without shortcuts is likely to end up with the final pixel
 * values of:
 *
 * <pre>
 *    (A, R, G, B) = (0x01, 0xff, 0x00, 0x00)</pre>
 *
 * <p>
 * (Note that 0x01 divided by 0x01 gives you 1.0, which is equivalent
 * to the value 0xff in an 8-bit storage format.)
 *
 * <p>
 * Alternately, an implementation that uses floating point math
 * might produce more accurate results and end up returning to the
 * original pixel value with little, if any, roundoff error.
 * Or, an implementation using integer math might decide that since
 * the equations boil down to a virtual NOP on the color values
 * if performed in a floating point space, it can transfer the
 * pixel untouched to the destination and avoid all the math entirely.
 *
 * <p>
 * These implementations all attempt to honor the
 * same equations, but use different tradeoffs of integer and
 * floating point math and reduced or full equations.
 * To account for such differences, it is probably best to
 * expect only that the premultiplied form of the results to
 * match between implementations and image formats.  In this
 * case both answers, expressed in premultiplied form would
 * equate to:
 *
 * <pre>
 *    (A, R, G, B) = (0x01, 0x01, 0x00, 0x00)</pre>
 *
 * <p>
 * and thus they would all match.
 *
 * <li>
 * Because of the technique of simplifying the equations for
 * calculation efficiency, some implementations might perform
 * differently when encountering result alpha values of 0.0
 * on a non-premultiplied destination.
 * Note that the simplification of removing the divide by alpha
 * in the case of the SRC rule is technically not valid if the
 * denominator (alpha) is 0.
 * But, since the results should only be expected to be accurate
 * when viewed in premultiplied form, a resulting alpha of 0
 * essentially renders the resulting color components irrelevant
 * and so exact behavior in this case should not be expected.
 * </ul>
 * <p>
 *  <code> AlphaComposite </code>类实现了基本的alpha合成规则,用于组合源和目标颜色,以实现图形和图像的混合和透明效果此类实现的特定规则是T Porter中描述的12个规则
 * 的基本集合, T Duff,"Compositing Digital Images",SIGGRAPH 84,253-259本文档的其余部分假定对该文章中概述的定义和概念有所了解。
 * 
 * <p>
 * 这个类扩展了由Porter和Duff定义的标准方程以包括一个附加因子<code> AlphaComposite </code>类的实例可以包含一个α值,用于在它之前修改每个源像素的不透明度或覆盖率用于混
 * 合方程。
 * 
 * <p>
 * 重要的是注意,由Porter和Duff论文定义的方程式都被定义为对由相应的α分量预乘的颜色分量进行操作。
 * 由于<code> ColorModel </code>和<code> Raster </code>类允许以预乘或非预乘形式存储像素数据,所有输入数据必须在应用等式之前被归一化为预乘形式,并且在像素值之前
 * 所有结果可能需要被调整回目的地所需的形式存储。
 * 重要的是注意,由Porter和Duff论文定义的方程式都被定义为对由相应的α分量预乘的颜色分量进行操作。
 * 
 * <p>
 * 还要注意,这个类只定义了在纯数学意义上组合颜色和alpha值的方程。其方程的准确应用取决于从其来源检索数据并存储在其目的地的方式。
 * 参见<a href ="#caveats ">实施注意事项</a>了解更多信息。
 * 
 * <p>
 *  在Porter和Duff纸中的混合方程的描述中使用以下因素：
 * 
 * <blockquote>
 * <table summary="layout">
 * <tr> <th align = left>因子&nbsp;&nbsp; <th align = left>定义<tr> <td> <em> </span> </em> <td>源像素<tr> <td>
 *  <em> C </sub> </em> <td>源像素的颜色分量<tr> <td> <sub> d </cf> <td>目的地像素的alpha分量<tr> <td> </em> </em> </em>
 *  <td>预乘的形式的像素<tr> <td> <f> </f> </em> <td>有助于输出的源像素的分数<tr> <td> <sub> d </sub> </em> <td>有助于输出的目标像素的分
 * 数<tr> <td> <em> A <sub> r </sub> </em> <td >结果的α分量<tr> <td> <em> C <sub> r </sub> </em> <td>预乘结果的颜色分量
 * 。
 * </table>
 * </blockquote>
 * 
 * <p>
 * 使用这些因子,Porter和Duff定义了选择混合因子的方法：<sub> </em> <em> </em> <em> </em> 12个期望的视觉效果中的每一个。
 * 用于确定f subs </em>和<em> f </sub> </em>的方程式在12个指定视觉效果的静态字段例如,<a href=\"#SRC_OVER\"> <code> SRC_OVER </code>
 *  </a>的说明指定<em> F <sub> </sub> </em> = 1和<em> F <sub> d </sub> </em> =(1- <em> A <sub> s </sub>混合因子是已知
 * 的,然后可以将它们应用于每个像素以使用以下方程组来产生结果：。
 * 使用这些因子,Porter和Duff定义了选择混合因子的方法：<sub> </em> <em> </em> <em> </em> 12个期望的视觉效果中的每一个。
 * 
 * <pre>
 * <EM>˚F<子>取值</sub> </em>的=的<em>˚F</em>的(<EM> <子> D </sub> </em>的)的<em>˚F<子> ð</sub> </em>的=的<em>˚F</em>
 * 的(<EM> <子>取值</sub> </em>的)的<em> <子>研究</sub> </在> = <EM> <子>取值</sub> </em>的*的<em>˚F<子>取值</sub> </em>的+
 * 的<em> <子> D </sub> < / EM> *的<em>˚F<子> D </sub> </em>的<EM> C <子>研究</sub> </em>的=的<em> C <子>取值</sub> < / EM>
 *  *的<em>˚F<子>取值</sub> </em>的+的<em> C <子> D </sub> </em>的*的<em>˚F<子> D </sub> </em>的</PRE>。
 * 
 * <p>
 *  以下因素将用来讨论我们扩展了Porter和Duff论文混合方程：
 * 
 * <blockquote>
 * <table summary="layout">
 * <tr> <th align = left>因子&nbsp;&nbsp; <th align = left>定义<tr> <td> <em> C </sub> </em> <td>目的像素的原色分量之一
 * 的源像素的元素<tr> <td> </b> </em> <td> A <strong> </span> </em> </span> </span> </span> </span> </span>源像素的
 * 原始阿尔法分量<tr> <td> </em> </b> <td>目标像素的原始阿尔法分量<tr> <td> <em> A <sub> df </sub> </em> <td>存储在目标中的最终Alpha
 * 组件<tr> <td> <em> C <sub> df </sub> </em> <td>最终原始颜色分量存储在目的地中。
 * </table>
 * /blockquote>
 * 
 *  <h3>准备输入</h3>
 * 
 * <p>
 * <code> AlphaComposite </code>类定义了一个应用于源alpha的额外的alpha值。
 * 应用此值的方式与将一个隐含的SRC_IN规则首先应用于具有指定alpha的像素的源像素相同,原始源α和原始源颜色通过<code> AlphaComposite </code>中的α进行。
 * 这导致用于产生在Porter和Duff混合方程中使用的α的以下等式：。
 * 
 * <pre>
 *  <em> </em> </em> </em> </em> </em> </pre>
 * 
 * 所有原始源颜色分量需要乘以<code> AlphaComposite </code>实例中的alpha。此外,如果源不是预乘形式,那么颜色分量也需要乘以源alpha。
 * 因此,用于产生Porter和Duff方程的源颜色分量的等式取决于源像素是否被预乘：。
 * 
 * <pre>
 *  <em> </em> </em> </em> </em> </em> * <em> </em> </em> </em> </em> </em> </em> / sub> </em> <em> </em>
 *  </em>(如果源被预乘)</。
 * 
 *  无需对目标alpha进行调整：
 * 
 * <pre>
 *  <em> A <sub> d </sub> </em> = <em> A <sub> dr </sub> </em> </
 * 
 * <p>
 * 仅当目标颜色分量不是预乘形式时才需要调整它们：
 * 
 * <pre>
 *  <em> </em> </em> <em> </em> </em> </em> (如果目的地未被预乘)</em> </em> </em> </em>
 * 
 *  <h3>应用混合方程</h3>
 * 
 * <p>
 *  调整后的<em> </em> </em>,<em> </em> </em>在标准Porter和Duff方程中使用标准的E em和C em来计算混合因子f sub </em> <em> </em>和<em>
 *  </em> </em> <em> </em>,然后将得到的预乘的组件<em> A </sub> sub> </em>。
 * 
 *  <h3>准备结果</h3>
 * 
 * <p>
 * 如果要使用以下等式将结果存储回保存未预乘数据的目标缓冲区,则结果只需要进行调整：
 * 
 * <pre>
 *  <em> </em> </em> </em> </em> </em> <em> C <sub> r </sub> </em>(如果dest是预乘的)<em> > </em> / <em> </em>(
 * 如果dest不是预乘的)</。
 * 
 *  注意,由于如果所得到的α是零,则除法是未定义的,所以省略在这种情况下的除法以避免"除以零",并且颜色分量作为全零留下
 * 
 *  <h3>效能注意事项</h3>
 * 
 * <p>
 * 出于性能原因,优选的是传递到由<code> AlphaComposite </code>类创建的{@link CompositeContext}对象的<code> compose </code>方法的<code>
 *  Raster </code>对象预乘数据如果源<code> Raster </code>或目标<code> Raster </code>未预乘,则在合成操作之前和之后执行适当的转换。
 * 
 *  <h3> <a name=\"caveats\">实施警告</a> </h3>
 * 
 * <ul>
 * <li>
 *  许多源,例如在<code> BufferedImage </code>类中列出的一些不透明图像类型,不存储它们的像素的α值。这样的源为它们的所有像素提供10的α
 * 
 * <li>
 * 许多目的地也没有地方存储由该类执行的混合计算产生的α值。这样的目的地因此隐含地丢弃该类产生的结果α值。
 * 建议这样的目的地应将它们存储的颜色值视为非预乘以及在存储所述颜色值并丢弃所述α值之前,将所得到的颜色值除以所得的α值。
 * 
 * <li>
 * 结果的准确性取决于像素存储在目的地中的方式。
 * 每种颜色和alpha分量提供至少8位存储的图像格式至少足够用作几个到a的序列的目的地十几个合成操作每个组件少于8位存储的图像格式对于仅一个或两个合成操作的使用有限,在舍入误差支配结果之前不单独存储颜色分
 * 量的图像格式不是任何透明混合类型例如,<code> BufferedImageTYPE_BYTE_INDEXED </code>不应该用作混合操作的目标,因为每个操作都可能引入大的错误,因为需要从有限的
 * 调色板中选择一个像素,以匹配混合方程的结果。
 * 结果的准确性取决于像素存储在目的地中的方式。
 * 
 * <li>
 * 几乎所有格式都将像素存储为离散整数,而不是上面的参考方程中使用的浮点值。实现可以将整数像素值缩放为范围在00到10之间的浮点值,或者使用稍微修改版本的方程,并且仍然产生与参考方程类似的结果
 * 
 * <p>
 *  通常,整数值与浮点值相关,使得整数0等于浮点值00和整数2 ^ em> n <-1(其中<em> n < em>是表示中的位数)等于10对于8位表示,这意味着0x00表示00,0xff表示10
 * 
 * <li>
 * 内部实现可以逼近一些方程,并且它还可以消除一些步骤以避免不必要的操作。例如,考虑具有非预乘α值的离散整数图像,其使用每个组件8位来存储存储的值近似透明变暗红色可能是：
 * 
 * <pre>
 *  (A,R,G,B)=(0x01,0xb0,0x00,0x00)</pre>
 * 
 * <p>
 *  如果正在使用整数数学,并且此值正在<a href=\"#SRC\"> <code> SRC </code> </a>模式中合成,没有额外的alpha,则数学将指示结果为(在整数格式)：
 * 
 * <pre>
 *  (A,R,G,B)=(0x01,0x01,0x00,0x00)</pre>
 * 
 * <p>
 * 注意,总是以预乘形式的中间值将仅允许整数红色分量为0x00或0x01当我们尝试将该结果存储回未预乘的目的地时,分出α将给予我们非常非预乘红色值的几个选择在这种情况下,在没有快捷方式的整数空间中执行数学的
 * 实现可能最终得到以下的最终像素值：。
 * 
 * <pre>
 *  (A,R,G,B)=(0x01,0xff,0x00,0x00)</pre>
 * 
 * <p>
 *  (注意,0x01除以0x01给出10,这相当于8位存储格式中的值0xff)
 * 
 * <p>
 * 或者,使用浮点数学的实现可以产生更准确的结果,并且最终返回到具有很少(如果有的话)舍入误差的原始像素值。
 * 或者,使用整数数学的实现可以决定,由于方程式归纳为虚拟NOP对于在浮点空间中执行的颜色值,它可以将未触摸的像素传送到目的地,并且完全避免所有数学。
 * 
 * <p>
 * 这些实现都试图遵守相同的方程,但是使用整数和浮点数学和减少的或完全方程的不同权衡为了解决这样的差异,最好预期结果的预乘形式在实现之间匹配和图像格式在这种情况下,以预乘形式表示的两个答案将等于：
 * 
 * <pre>
 *  (A,R,G,B)=(0x01,0x01,0x00,0x00)</pre>
 * 
 * <p>
 *  因此他们都会匹配
 * 
 * <li>
 * 
 * @see Composite
 * @see CompositeContext
 */

public final class AlphaComposite implements Composite {
    /**
     * Both the color and the alpha of the destination are cleared
     * (Porter-Duff Clear rule).
     * Neither the source nor the destination is used as input.
     *<p>
     * <em>F<sub>s</sub></em> = 0 and <em>F<sub>d</sub></em> = 0, thus:
     *<pre>
     *  <em>A<sub>r</sub></em> = 0
     *  <em>C<sub>r</sub></em> = 0
     *</pre>
     * <p>
     * 因为简化计算效率的方程的技术,当在非预乘目的地上遇到结果α值00时,一些实现可能执行不同。注意,在SRC规则的情况下,去除除以α的简化在技术上不是如果分母(α)为0,则有效。
     * 但是,由于结果应该仅在以预乘形式查看时才是准确的,因此生成的0的α本质上使得到的颜色分量不相关,因此不应该预期在这种情况下的精确行为。
     * </ul>
     */
    @Native public static final int     CLEAR           = 1;

    /**
     * The source is copied to the destination
     * (Porter-Duff Source rule).
     * The destination is not used as input.
     *<p>
     * <em>F<sub>s</sub></em> = 1 and <em>F<sub>d</sub></em> = 0, thus:
     *<pre>
     *  <em>A<sub>r</sub></em> = <em>A<sub>s</sub></em>
     *  <em>C<sub>r</sub></em> = <em>C<sub>s</sub></em>
     *</pre>
     * <p>
     *  目的地的颜色和alpha都被清除(Porter-Duff清除规则)源和目的地都不用作输入
     * p>
     * <em> F <sub> </sub> </em> = 0和<em> F <sub> d </sub> </em>
     * pre>
     *  <em> A <sub> r </sub> </em> = 0 <em> C <sub> r </sub> </em> = 0
     * /pre>
     */
    @Native public static final int     SRC             = 2;

    /**
     * The destination is left untouched
     * (Porter-Duff Destination rule).
     *<p>
     * <em>F<sub>s</sub></em> = 0 and <em>F<sub>d</sub></em> = 1, thus:
     *<pre>
     *  <em>A<sub>r</sub></em> = <em>A<sub>d</sub></em>
     *  <em>C<sub>r</sub></em> = <em>C<sub>d</sub></em>
     *</pre>
     * <p>
     *  源复制到目标(Porter-Duff源规则)目标不用作输入
     * p>
     *  <em> F <sub> </sub> </em> = 1和<em> F <sub> d </sub> </em>
     * pre>
     *  <em> </em> </em> </em> </em> </em> </em> <em> C <sub> </em> </em>
     * /pre>
     * 
     * @since 1.4
     */
    @Native public static final int     DST             = 9;
    // Note that DST was added in 1.4 so it is numbered out of order...

    /**
     * The source is composited over the destination
     * (Porter-Duff Source Over Destination rule).
     *<p>
     * <em>F<sub>s</sub></em> = 1 and <em>F<sub>d</sub></em> = (1-<em>A<sub>s</sub></em>), thus:
     *<pre>
     *  <em>A<sub>r</sub></em> = <em>A<sub>s</sub></em> + <em>A<sub>d</sub></em>*(1-<em>A<sub>s</sub></em>)
     *  <em>C<sub>r</sub></em> = <em>C<sub>s</sub></em> + <em>C<sub>d</sub></em>*(1-<em>A<sub>s</sub></em>)
     *</pre>
     * <p>
     *  目的地保持不变(Porter-Duff目的地规则)
     * p>
     *  <em> F <sub> </sub> </em> = 0和<em> F <sub> d </sub> </em>
     * pre>
     *  <em> </em> </em> </em> </em> </em> </em> <em> C <sub> d </sub> </em>
     * /pre>
     */
    @Native public static final int     SRC_OVER        = 3;

    /**
     * The destination is composited over the source and
     * the result replaces the destination
     * (Porter-Duff Destination Over Source rule).
     *<p>
     * <em>F<sub>s</sub></em> = (1-<em>A<sub>d</sub></em>) and <em>F<sub>d</sub></em> = 1, thus:
     *<pre>
     *  <em>A<sub>r</sub></em> = <em>A<sub>s</sub></em>*(1-<em>A<sub>d</sub></em>) + <em>A<sub>d</sub></em>
     *  <em>C<sub>r</sub></em> = <em>C<sub>s</sub></em>*(1-<em>A<sub>d</sub></em>) + <em>C<sub>d</sub></em>
     *</pre>
     * <p>
     *  源在目的地上合成(Porter-Duff Source Over Destination rule)
     * p>
     * <EM>˚F<子>取值</sub> </em>的= 1和的<em>˚F<子> D </sub> </em>的=(1 <EM> <子>取值</分> </em>的)因此：
     * pre>
     *  <EM> <子>研究</sub> </em>的=的<em> <子>取值</sub> </em>的+的<em> <子> D </sub> </em>的*(1-的<em> <子>取值</sub> </em>
     * 的)的<em> C <子>研究</sub> </em>的=的<em> C <子>取值</sub> </EM> + <EM> C <子> D </sub> </em>的*(1-的<em> <子>取值</sub>
     *  </em>的)。
     * /pre>
     */
    @Native public static final int     DST_OVER        = 4;

    /**
     * The part of the source lying inside of the destination replaces
     * the destination
     * (Porter-Duff Source In Destination rule).
     *<p>
     * <em>F<sub>s</sub></em> = <em>A<sub>d</sub></em> and <em>F<sub>d</sub></em> = 0, thus:
     *<pre>
     *  <em>A<sub>r</sub></em> = <em>A<sub>s</sub></em>*<em>A<sub>d</sub></em>
     *  <em>C<sub>r</sub></em> = <em>C<sub>s</sub></em>*<em>A<sub>d</sub></em>
     *</pre>
     * <p>
     *  目的地被合成到源和结果替换目的地(波特 - 达夫目的地超过源规则)
     * p>
     *  <EM>˚F<子>取值</sub> </em>的=(1 <EM> <子> D </sub> </em>的)和<EM>˚F<子> D </sub> </em>的= 1这样：
     * pre>
     *  <EM> <子>研究</sub> </em>的=的<em> <子>取值</sub> </em>的*(1-的<em> <子> D </sub> < / EM>)+ <EM> <子> D </sub> </em>
     * 的<EM> C <子>研究</sub> </em>的=的<em> C <子>取值</sub> </em>的*(1-的<em> <子> D </sub> </em>的)+的<em> C <子> D </sub>
     *  </em>的。
     * /pre>
     */
    @Native public static final int     SRC_IN          = 5;

    /**
     * The part of the destination lying inside of the source
     * replaces the destination
     * (Porter-Duff Destination In Source rule).
     *<p>
     * <em>F<sub>s</sub></em> = 0 and <em>F<sub>d</sub></em> = <em>A<sub>s</sub></em>, thus:
     *<pre>
     *  <em>A<sub>r</sub></em> = <em>A<sub>d</sub></em>*<em>A<sub>s</sub></em>
     *  <em>C<sub>r</sub></em> = <em>C<sub>d</sub></em>*<em>A<sub>s</sub></em>
     *</pre>
     * <p>
     * 源位于目的地内部的部分替换目的地(Porter-Duff Source In Destination rule)
     * p>
     *  <em> </em> <em> </em> </em> <em> </em> = 0,因此：
     * pre>
     *  <em> </em> </em> </em> </em> </em> <em> </em> </em> </em> <em> </em> <em> </em>
     * /pre>
     */
    @Native public static final int     DST_IN          = 6;

    /**
     * The part of the source lying outside of the destination
     * replaces the destination
     * (Porter-Duff Source Held Out By Destination rule).
     *<p>
     * <em>F<sub>s</sub></em> = (1-<em>A<sub>d</sub></em>) and <em>F<sub>d</sub></em> = 0, thus:
     *<pre>
     *  <em>A<sub>r</sub></em> = <em>A<sub>s</sub></em>*(1-<em>A<sub>d</sub></em>)
     *  <em>C<sub>r</sub></em> = <em>C<sub>s</sub></em>*(1-<em>A<sub>d</sub></em>)
     *</pre>
     * <p>
     *  位于源内部的目的地部分替换目的地(Porter-Duff Destination In Source规则)
     * p>
     *  <em> F <sub> </sub> </em> = 0和<em> F <sub> d </sub> </em> em,因此：
     * pre>
     *  <em> </em> </em> </em> </em> </em> <em> </em> </em> </em> </em> <em> </em>
     * /pre>
     */
    @Native public static final int     SRC_OUT         = 7;

    /**
     * The part of the destination lying outside of the source
     * replaces the destination
     * (Porter-Duff Destination Held Out By Source rule).
     *<p>
     * <em>F<sub>s</sub></em> = 0 and <em>F<sub>d</sub></em> = (1-<em>A<sub>s</sub></em>), thus:
     *<pre>
     *  <em>A<sub>r</sub></em> = <em>A<sub>d</sub></em>*(1-<em>A<sub>s</sub></em>)
     *  <em>C<sub>r</sub></em> = <em>C<sub>d</sub></em>*(1-<em>A<sub>s</sub></em>)
     *</pre>
     * <p>
     * 位于目的地外部的源的部分替换目的地(Porter-Duff Source Held Out By Destination rule)
     * p>
     *  <em> </em> </em> </em> <em> </em> </em> = 0,因此：
     * pre>
     *  <em> </em> </em> </em> </em> </em> / em>)<em> </em> <em> </em> <em> </em> / sub> </em>)
     * /pre>
     */
    @Native public static final int     DST_OUT         = 8;

    // Rule 9 is DST which is defined above where it fits into the
    // list logically, rather than numerically
    //
    // public static final int  DST             = 9;

    /**
     * The part of the source lying inside of the destination
     * is composited onto the destination
     * (Porter-Duff Source Atop Destination rule).
     *<p>
     * <em>F<sub>s</sub></em> = <em>A<sub>d</sub></em> and <em>F<sub>d</sub></em> = (1-<em>A<sub>s</sub></em>), thus:
     *<pre>
     *  <em>A<sub>r</sub></em> = <em>A<sub>s</sub></em>*<em>A<sub>d</sub></em> + <em>A<sub>d</sub></em>*(1-<em>A<sub>s</sub></em>) = <em>A<sub>d</sub></em>
     *  <em>C<sub>r</sub></em> = <em>C<sub>s</sub></em>*<em>A<sub>d</sub></em> + <em>C<sub>d</sub></em>*(1-<em>A<sub>s</sub></em>)
     *</pre>
     * <p>
     *  位于源外部的目的地的一部分替换目的地(Porter-Duff Destination Held Out By Source规则)
     * p>
     *  <em> </em> </em> = <em> </em> </em> > </em>),因此：
     * pre>
     *  <em> A <sub> r </sub> </em> = <em> A <sub> d </sub> </em> *(1- <em> / em>)<em> </em> <em> </em> </em>
     *  / sub> </em>)。
     * /pre>
     * 
     * @since 1.4
     */
    @Native public static final int     SRC_ATOP        = 10;

    /**
     * The part of the destination lying inside of the source
     * is composited over the source and replaces the destination
     * (Porter-Duff Destination Atop Source rule).
     *<p>
     * <em>F<sub>s</sub></em> = (1-<em>A<sub>d</sub></em>) and <em>F<sub>d</sub></em> = <em>A<sub>s</sub></em>, thus:
     *<pre>
     *  <em>A<sub>r</sub></em> = <em>A<sub>s</sub></em>*(1-<em>A<sub>d</sub></em>) + <em>A<sub>d</sub></em>*<em>A<sub>s</sub></em> = <em>A<sub>s</sub></em>
     *  <em>C<sub>r</sub></em> = <em>C<sub>s</sub></em>*(1-<em>A<sub>d</sub></em>) + <em>C<sub>d</sub></em>*<em>A<sub>s</sub></em>
     *</pre>
     * <p>
     * 将位于目的地内部的源的部分合成到目的地(Porter-Duff Source Atop Destination rule)
     * p>
     *  <em> </em> <em> </em> </em> <em> </em> =(1- <sub> s </sub> </em>),因此：
     * pre>
     *  <em> </em> </em> </em> </em> </em> + <em> </em> </em> </em> A </sub> </em> > </em> </em> <em> </em> 
     * <em> </em> > </em> + <em> </em> </em> </em>。
     * /pre>
     * 
     * @since 1.4
     */
    @Native public static final int     DST_ATOP        = 11;

    /**
     * The part of the source that lies outside of the destination
     * is combined with the part of the destination that lies outside
     * of the source
     * (Porter-Duff Source Xor Destination rule).
     *<p>
     * <em>F<sub>s</sub></em> = (1-<em>A<sub>d</sub></em>) and <em>F<sub>d</sub></em> = (1-<em>A<sub>s</sub></em>), thus:
     *<pre>
     *  <em>A<sub>r</sub></em> = <em>A<sub>s</sub></em>*(1-<em>A<sub>d</sub></em>) + <em>A<sub>d</sub></em>*(1-<em>A<sub>s</sub></em>)
     *  <em>C<sub>r</sub></em> = <em>C<sub>s</sub></em>*(1-<em>A<sub>d</sub></em>) + <em>C<sub>d</sub></em>*(1-<em>A<sub>s</sub></em>)
     *</pre>
     * <p>
     *  目的地在源内部的部分在源上合成,并且替换目的地(Porter-Duff Destination Atop Source规则)
     * p>
     *  <em> </em> </em> </em> <em> </em> </em> = <em> </em> </em>,因此：
     * pre>
     * <EM> <子>研究</sub> </em>的=的<em> <子>取值</sub> </em>的*(1-的<em> <子> D </sub> < / EM>)+ <EM> <子> D </sub> </em>
     * 的*的<em> <子>取值</sub> </em>的=的<em> <子>取值</分> </em>的<EM> C <子>研究</sub> </em>的=的<em> C <子>取值</sub> </em>的
     * *(1-的<em> <子>ð </sub> </em>的)+的<em> C <子> D </sub> </em>的*的<em> <子>取值</sub> </em>的。
     * /pre>
     * 
     * @since 1.4
     */
    @Native public static final int     XOR             = 12;

    /**
     * <code>AlphaComposite</code> object that implements the opaque CLEAR rule
     * with an alpha of 1.0f.
     * <p>
     *  源阙的部分在于目标与目的阙部分组合之外位于源之外(波特 - 达夫源异或目的地原则)
     * p>
     *  <EM>˚F<子>取值</sub> </em>的=(1 <EM> <子> D </sub> </em>的)和<EM>˚F<子> D </sub> </em>的=(1 <EM> <子>取值</sub> 
     * </em>的)因此：。
     * pre>
     * <EM> <子>研究</sub> </em>的=的<em> <子>取值</sub> </em>的*(1-的<em> <子> D </sub> < / EM>)+ <EM> <子> D </sub> </em>
     * 的*(1-的<em> <子>取值</sub> </em>的)的<em> C <子>研究</sub> </em>的=的<em> C <子>取值</sub> </em>的*(1-的<em> <子> D </sub>
     *  </em>的)+的<em> ç<子> D </sub> </em>的*(1-的<em> <子>取值</sub> </em>的)。
     * /pre>
     * 
     * @see #CLEAR
     */
    public static final AlphaComposite Clear    = new AlphaComposite(CLEAR);

    /**
     * <code>AlphaComposite</code> object that implements the opaque SRC rule
     * with an alpha of 1.0f.
     * <p>
     *  <编码>的AlphaComposite </code>对象阙实现与10F的alpha不透明CLEAR规则
     * 
     * 
     * @see #SRC
     */
    public static final AlphaComposite Src      = new AlphaComposite(SRC);

    /**
     * <code>AlphaComposite</code> object that implements the opaque DST rule
     * with an alpha of 1.0f.
     * <p>
     *  <编码>的AlphaComposite </code>对象阙实现与10F的alpha不透明SRC规则
     * 
     * 
     * @see #DST
     * @since 1.4
     */
    public static final AlphaComposite Dst      = new AlphaComposite(DST);

    /**
     * <code>AlphaComposite</code> object that implements the opaque SRC_OVER rule
     * with an alpha of 1.0f.
     * <p>
     *  <编码>的AlphaComposite </code>对象阙实现与10F的alpha不透明DST规则的
     * 
     * 
     * @see #SRC_OVER
     */
    public static final AlphaComposite SrcOver  = new AlphaComposite(SRC_OVER);

    /**
     * <code>AlphaComposite</code> object that implements the opaque DST_OVER rule
     * with an alpha of 1.0f.
     * <p>
     *  <编码>的AlphaComposite </code>对象阙实现与10F的alpha不透明SRC_OVER规则
     * 
     * 
     * @see #DST_OVER
     */
    public static final AlphaComposite DstOver  = new AlphaComposite(DST_OVER);

    /**
     * <code>AlphaComposite</code> object that implements the opaque SRC_IN rule
     * with an alpha of 1.0f.
     * <p>
     *  <编码>的AlphaComposite </code>对象阙实现与10F的alpha不透明DST_OVER规则
     * 
     * 
     * @see #SRC_IN
     */
    public static final AlphaComposite SrcIn    = new AlphaComposite(SRC_IN);

    /**
     * <code>AlphaComposite</code> object that implements the opaque DST_IN rule
     * with an alpha of 1.0f.
     * <p>
     * <code> AlphaComposite </code>对象,实现具有alpha值为10f的不透明SRC_IN规则
     * 
     * 
     * @see #DST_IN
     */
    public static final AlphaComposite DstIn    = new AlphaComposite(DST_IN);

    /**
     * <code>AlphaComposite</code> object that implements the opaque SRC_OUT rule
     * with an alpha of 1.0f.
     * <p>
     *  <code> AlphaComposite </code>对象,它实现了alpha值为10f的不透明DST_IN规则
     * 
     * 
     * @see #SRC_OUT
     */
    public static final AlphaComposite SrcOut   = new AlphaComposite(SRC_OUT);

    /**
     * <code>AlphaComposite</code> object that implements the opaque DST_OUT rule
     * with an alpha of 1.0f.
     * <p>
     *  <code> AlphaComposite </code>对象,实现具有alpha的10f的不透明SRC_OUT规则
     * 
     * 
     * @see #DST_OUT
     */
    public static final AlphaComposite DstOut   = new AlphaComposite(DST_OUT);

    /**
     * <code>AlphaComposite</code> object that implements the opaque SRC_ATOP rule
     * with an alpha of 1.0f.
     * <p>
     *  <code> AlphaComposite </code>对象,它实现了alpha值为10f的不透明DST_OUT规则
     * 
     * 
     * @see #SRC_ATOP
     * @since 1.4
     */
    public static final AlphaComposite SrcAtop  = new AlphaComposite(SRC_ATOP);

    /**
     * <code>AlphaComposite</code> object that implements the opaque DST_ATOP rule
     * with an alpha of 1.0f.
     * <p>
     *  <code> AlphaComposite </code>对象,实现具有alpha的10f的不透明SRC_ATOP规则
     * 
     * 
     * @see #DST_ATOP
     * @since 1.4
     */
    public static final AlphaComposite DstAtop  = new AlphaComposite(DST_ATOP);

    /**
     * <code>AlphaComposite</code> object that implements the opaque XOR rule
     * with an alpha of 1.0f.
     * <p>
     *  <code> AlphaComposite </code>对象,它实现了alpha值为10f的不透明DST_ATOP规则
     * 
     * 
     * @see #XOR
     * @since 1.4
     */
    public static final AlphaComposite Xor      = new AlphaComposite(XOR);

    @Native private static final int MIN_RULE = CLEAR;
    @Native private static final int MAX_RULE = XOR;

    float extraAlpha;
    int rule;

    private AlphaComposite(int rule) {
        this(rule, 1.0f);
    }

    private AlphaComposite(int rule, float alpha) {
        if (rule < MIN_RULE || rule > MAX_RULE) {
            throw new IllegalArgumentException("unknown composite rule");
        }
        if (alpha >= 0.0f && alpha <= 1.0f) {
            this.rule = rule;
            this.extraAlpha = alpha;
        } else {
            throw new IllegalArgumentException("alpha value out of range");
        }
    }

    /**
     * Creates an <code>AlphaComposite</code> object with the specified rule.
     * <p>
     *  <code> AlphaComposite </code>对象,实现具有10f的alpha的不透明XOR规则
     * 
     * 
     * @param rule the compositing rule
     * @throws IllegalArgumentException if <code>rule</code> is not one of
     *         the following:  {@link #CLEAR}, {@link #SRC}, {@link #DST},
     *         {@link #SRC_OVER}, {@link #DST_OVER}, {@link #SRC_IN},
     *         {@link #DST_IN}, {@link #SRC_OUT}, {@link #DST_OUT},
     *         {@link #SRC_ATOP}, {@link #DST_ATOP}, or {@link #XOR}
     */
    public static AlphaComposite getInstance(int rule) {
        switch (rule) {
        case CLEAR:
            return Clear;
        case SRC:
            return Src;
        case DST:
            return Dst;
        case SRC_OVER:
            return SrcOver;
        case DST_OVER:
            return DstOver;
        case SRC_IN:
            return SrcIn;
        case DST_IN:
            return DstIn;
        case SRC_OUT:
            return SrcOut;
        case DST_OUT:
            return DstOut;
        case SRC_ATOP:
            return SrcAtop;
        case DST_ATOP:
            return DstAtop;
        case XOR:
            return Xor;
        default:
            throw new IllegalArgumentException("unknown composite rule");
        }
    }

    /**
     * Creates an <code>AlphaComposite</code> object with the specified rule and
     * the constant alpha to multiply with the alpha of the source.
     * The source is multiplied with the specified alpha before being composited
     * with the destination.
     * <p>
     *  使用指定的规则创建<code> AlphaComposite </code>对象
     * 
     * 
     * @param rule the compositing rule
     * @param alpha the constant alpha to be multiplied with the alpha of
     * the source. <code>alpha</code> must be a floating point number in the
     * inclusive range [0.0,&nbsp;1.0].
     * @throws IllegalArgumentException if
     *         <code>alpha</code> is less than 0.0 or greater than 1.0, or if
     *         <code>rule</code> is not one of
     *         the following:  {@link #CLEAR}, {@link #SRC}, {@link #DST},
     *         {@link #SRC_OVER}, {@link #DST_OVER}, {@link #SRC_IN},
     *         {@link #DST_IN}, {@link #SRC_OUT}, {@link #DST_OUT},
     *         {@link #SRC_ATOP}, {@link #DST_ATOP}, or {@link #XOR}
     */
    public static AlphaComposite getInstance(int rule, float alpha) {
        if (alpha == 1.0f) {
            return getInstance(rule);
        }
        return new AlphaComposite(rule, alpha);
    }

    /**
     * Creates a context for the compositing operation.
     * The context contains state that is used in performing
     * the compositing operation.
     * <p>
     * 使用指定的规则和常量alpha创建<code> AlphaComposite </code>对象与源的alpha值相乘在与目标合成之前,源与指定的alpha值相乘
     * 
     * 
     * @param srcColorModel  the {@link ColorModel} of the source
     * @param dstColorModel  the <code>ColorModel</code> of the destination
     * @return the <code>CompositeContext</code> object to be used to perform
     * compositing operations.
     */
    public CompositeContext createContext(ColorModel srcColorModel,
                                          ColorModel dstColorModel,
                                          RenderingHints hints) {
        return new SunCompositeContext(this, srcColorModel, dstColorModel);
    }

    /**
     * Returns the alpha value of this <code>AlphaComposite</code>.  If this
     * <code>AlphaComposite</code> does not have an alpha value, 1.0 is returned.
     * <p>
     *  为合成操作创建上下文上下文包含用于执行合成操作的状态
     * 
     * 
     * @return the alpha value of this <code>AlphaComposite</code>.
     */
    public float getAlpha() {
        return extraAlpha;
    }

    /**
     * Returns the compositing rule of this <code>AlphaComposite</code>.
     * <p>
     *  返回此<code> AlphaComposite </code>的Alpha值如果此<code> AlphaComposite </code>没有Alpha值,则返回10
     * 
     * 
     * @return the compositing rule of this <code>AlphaComposite</code>.
     */
    public int getRule() {
        return rule;
    }

    /**
     * Returns a similar <code>AlphaComposite</code> object that uses
     * the specified compositing rule.
     * If this object already uses the specified compositing rule,
     * this object is returned.
     * <p>
     *  返回此<code> AlphaComposite </code>的合成规则
     * 
     * 
     * @return an <code>AlphaComposite</code> object derived from
     * this object that uses the specified compositing rule.
     * @param rule the compositing rule
     * @throws IllegalArgumentException if
     *         <code>rule</code> is not one of
     *         the following:  {@link #CLEAR}, {@link #SRC}, {@link #DST},
     *         {@link #SRC_OVER}, {@link #DST_OVER}, {@link #SRC_IN},
     *         {@link #DST_IN}, {@link #SRC_OUT}, {@link #DST_OUT},
     *         {@link #SRC_ATOP}, {@link #DST_ATOP}, or {@link #XOR}
     * @since 1.6
     */
    public AlphaComposite derive(int rule) {
        return (this.rule == rule)
            ? this
            : getInstance(rule, this.extraAlpha);
    }

    /**
     * Returns a similar <code>AlphaComposite</code> object that uses
     * the specified alpha value.
     * If this object already has the specified alpha value,
     * this object is returned.
     * <p>
     *  返回使用指定的合成规则的类似的<code> AlphaComposite </code>对象如果此对象已使用指定的合成规则,则返回此对象
     * 
     * 
     * @return an <code>AlphaComposite</code> object derived from
     * this object that uses the specified alpha value.
     * @param alpha the constant alpha to be multiplied with the alpha of
     * the source. <code>alpha</code> must be a floating point number in the
     * inclusive range [0.0,&nbsp;1.0].
     * @throws IllegalArgumentException if
     *         <code>alpha</code> is less than 0.0 or greater than 1.0
     * @since 1.6
     */
    public AlphaComposite derive(float alpha) {
        return (this.extraAlpha == alpha)
            ? this
            : getInstance(this.rule, alpha);
    }

    /**
     * Returns the hashcode for this composite.
     * <p>
     * 返回使用指定的alpha值的类似的<code> AlphaComposite </code>对象如果此对象已具有指定的alpha值,则返回此对象
     * 
     * 
     * @return      a hash code for this composite.
     */
    public int hashCode() {
        return (Float.floatToIntBits(extraAlpha) * 31 + rule);
    }

    /**
     * Determines whether the specified object is equal to this
     * <code>AlphaComposite</code>.
     * <p>
     * The result is <code>true</code> if and only if
     * the argument is not <code>null</code> and is an
     * <code>AlphaComposite</code> object that has the same
     * compositing rule and alpha value as this object.
     *
     * <p>
     *  返回此组合的哈希码
     * 
     * 
     * @param obj the <code>Object</code> to test for equality
     * @return <code>true</code> if <code>obj</code> equals this
     * <code>AlphaComposite</code>; <code>false</code> otherwise.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof AlphaComposite)) {
            return false;
        }

        AlphaComposite ac = (AlphaComposite) obj;

        if (rule != ac.rule) {
            return false;
        }

        if (extraAlpha != ac.extraAlpha) {
            return false;
        }

        return true;
    }

}
