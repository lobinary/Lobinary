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

/* ********************************************************************
 **********************************************************************
 **********************************************************************
 *** COPYRIGHT (c) Eastman Kodak Company, 1997                      ***
 *** As  an unpublished  work pursuant to Title 17 of the United    ***
 *** States Code.  All rights reserved.                             ***
 **********************************************************************
 **********************************************************************
 * <p>
 *  **************************************************** ****************** ****************************
 * **** ************************************ * COPYRIGHT(c)Eastman Kodak Company,1997 *** *根据United *** 
 * *国家法典第17章的未公布的工作。
 * 版权所有。
 *  *** *********************************************** ********************* **************************
 * *** ***************************************。
 * 版权所有。
 * 
 * 
 **********************************************************************/

package java.awt.image.renderable;
import java.util.Vector;
import java.awt.RenderingHints;
import java.awt.image.*;

/**
 * A RenderableImage is a common interface for rendering-independent
 * images (a notion which subsumes resolution independence).  That is,
 * images which are described and have operations applied to them
 * independent of any specific rendering of the image.  For example, a
 * RenderableImage can be rotated and cropped in
 * resolution-independent terms.  Then, it can be rendered for various
 * specific contexts, such as a draft preview, a high-quality screen
 * display, or a printer, each in an optimal fashion.
 *
 * <p> A RenderedImage is returned from a RenderableImage via the
 * createRendering() method, which takes a RenderContext.  The
 * RenderContext specifies how the RenderedImage should be
 * constructed.  Note that it is not possible to extract pixels
 * directly from a RenderableImage.
 *
 * <p> The createDefaultRendering() and createScaledRendering() methods are
 * convenience methods that construct an appropriate RenderContext
 * internally.  All of the rendering methods may return a reference to a
 * previously produced rendering.
 * <p>
 *  RenderableImage是一个用于渲染独立图像的公共接口(一种包含分辨率独立性的概念)。也就是说,独立于图像的任何特定渲染,描述的图像和具有应用于它们的操作。
 * 例如,可以在与分辨率无关的术语中旋转和裁剪RenderableImage。然后,可以以最佳方式为诸如草图预览,高质量屏幕显示或打印机的各种特定上下文呈现它。
 * 
 *  <p> RenderedImage是通过createRendering()方法从RenderableImage返回的,它需要一个RenderContext。
 *  RenderContext指定如何构造RenderedImage。请注意,不可能直接从RenderableImage中提取像素。
 * 
 * <p> createDefaultRendering()和createScaledRendering()方法是在内部构建合适的RenderContext的简便方法。
 * 所有的渲染方法可以返回对先前产生的渲染的引用。
 * 
 */
public interface RenderableImage {

    /**
     * String constant that can be used to identify a property on
     * a RenderedImage obtained via the createRendering or
     * createScaledRendering methods.  If such a property exists,
     * the value of the property will be a RenderingHints object
     * specifying which hints were observed in creating the rendering.
     * <p>
     *  字符串常量,可用于标识通过createRendering或createScaledRendering方法获取的RenderedImage上的属性。
     * 如果这样的属性存在,属性的值将是RenderingHints对象,指定在创建渲染中观察到的提示。
     * 
     */
     static final String HINTS_OBSERVED = "HINTS_OBSERVED";

    /**
     * Returns a vector of RenderableImages that are the sources of
     * image data for this RenderableImage. Note that this method may
     * return an empty vector, to indicate that the image has no sources,
     * or null, to indicate that no information is available.
     *
     * <p>
     *  返回一个RenderableImages的向量,它是这个RenderableImage的图像数据的来源。注意,该方法可以返回空向量,以指示图像没有源或空,以指示没有可用的信息。
     * 
     * 
     * @return a (possibly empty) Vector of RenderableImages, or null.
     */
    Vector<RenderableImage> getSources();

    /**
     * Gets a property from the property set of this image.
     * If the property name is not recognized, java.awt.Image.UndefinedProperty
     * will be returned.
     *
     * <p>
     *  从此图像的属性集获取属性。如果属性名不被识别,将返回java.awt.Image.UndefinedProperty。
     * 
     * 
     * @param name the name of the property to get, as a String.
     * @return a reference to the property Object, or the value
     *         java.awt.Image.UndefinedProperty.
     */
    Object getProperty(String name);

    /**
     * Returns a list of names recognized by getProperty.
     * <p>
     *  返回由getProperty识别的名称列表。
     * 
     * 
     * @return a list of property names.
     */
    String[] getPropertyNames();

    /**
     * Returns true if successive renderings (that is, calls to
     * createRendering() or createScaledRendering()) with the same arguments
     * may produce different results.  This method may be used to
     * determine whether an existing rendering may be cached and
     * reused.  It is always safe to return true.
     * <p>
     *  如果连续的渲染(即使用相同的参数调用createRendering()或createScaledRendering())可能会产生不同的结果,则返回true。
     * 该方法可以用于确定现有的渲染是否可以被高速缓存和重用。返回true总是安全的。
     * 
     * 
     * @return <code>true</code> if successive renderings with the
     *         same arguments might produce different results;
     *         <code>false</code> otherwise.
     */
    boolean isDynamic();

    /**
     * Gets the width in user coordinate space.  By convention, the
     * usual width of a RenderableImage is equal to the image's aspect
     * ratio (width divided by height).
     *
     * <p>
     *  获取用户坐标空间中的宽度。按照惯例,RenderableImage的通常宽度等于图片的宽高比(宽度除以高度)。
     * 
     * 
     * @return the width of the image in user coordinates.
     */
    float getWidth();

    /**
     * Gets the height in user coordinate space.  By convention, the
     * usual height of a RenderedImage is equal to 1.0F.
     *
     * <p>
     * 获取用户坐标空间中的高度。按照惯例,RenderedImage的通常高度等于1.0F。
     * 
     * 
     * @return the height of the image in user coordinates.
     */
    float getHeight();

    /**
     * Gets the minimum X coordinate of the rendering-independent image data.
     * <p>
     *  获取渲染无关图像数据的最小X坐标。
     * 
     * 
     * @return the minimum X coordinate of the rendering-independent image
     * data.
     */
    float getMinX();

    /**
     * Gets the minimum Y coordinate of the rendering-independent image data.
     * <p>
     *  获取渲染无关图像数据的最小Y坐标。
     * 
     * 
     * @return the minimum Y coordinate of the rendering-independent image
     * data.
     */
    float getMinY();

    /**
     * Creates a RenderedImage instance of this image with width w, and
     * height h in pixels.  The RenderContext is built automatically
     * with an appropriate usr2dev transform and an area of interest
     * of the full image.  All the rendering hints come from hints
     * passed in.
     *
     * <p> If w == 0, it will be taken to equal
     * Math.round(h*(getWidth()/getHeight())).
     * Similarly, if h == 0, it will be taken to equal
     * Math.round(w*(getHeight()/getWidth())).  One of
     * w or h must be non-zero or else an IllegalArgumentException
     * will be thrown.
     *
     * <p> The created RenderedImage may have a property identified
     * by the String HINTS_OBSERVED to indicate which RenderingHints
     * were used to create the image.  In addition any RenderedImages
     * that are obtained via the getSources() method on the created
     * RenderedImage may have such a property.
     *
     * <p>
     *  使用宽度w和高度h(以像素为单位)创建此图像的RenderedImage实例。 RenderContext是使用适当的usr2dev变换和完整图像的感兴趣区域自动构建的。
     * 所有的渲染提示都来自传入的提示。
     * 
     *  <p>如果w == 0,它将等于Math.round(h *(getWidth()/ getHeight()))。
     * 类似地,如果h == 0,它将取等于Math.round(w *(getHeight()/ getWidth()))。
     *  w或h之一必须是非零或否则将抛出IllegalArgumentException。
     * 
     *  <p>所创建的RenderedImage可能具有由字符串HINTS_OBSERVED标识的属性,以指示哪个RenderingHint用于创建图像。
     * 此外,通过在创建的RenderedImage上的getSources()方法获得的任何RenderedImages可能有这样的属性。
     * 
     * 
     * @param w the width of rendered image in pixels, or 0.
     * @param h the height of rendered image in pixels, or 0.
     * @param hints a RenderingHints object containing hints.
     * @return a RenderedImage containing the rendered data.
     */
    RenderedImage createScaledRendering(int w, int h, RenderingHints hints);

    /**
     * Returnd a RenderedImage instance of this image with a default
     * width and height in pixels.  The RenderContext is built
     * automatically with an appropriate usr2dev transform and an area
     * of interest of the full image.  The rendering hints are
     * empty.  createDefaultRendering may make use of a stored
     * rendering for speed.
     *
     * <p>
     * 
     * @return a RenderedImage containing the rendered data.
     */
    RenderedImage createDefaultRendering();

    /**
     * Creates a RenderedImage that represented a rendering of this image
     * using a given RenderContext.  This is the most general way to obtain a
     * rendering of a RenderableImage.
     *
     * <p> The created RenderedImage may have a property identified
     * by the String HINTS_OBSERVED to indicate which RenderingHints
     * (from the RenderContext) were used to create the image.
     * In addition any RenderedImages
     * that are obtained via the getSources() method on the created
     * RenderedImage may have such a property.
     *
     * <p>
     *  返回此图片的RenderedImage实例,默认宽度和高度(以像素为单位)。 RenderContext是使用适当的usr2dev变换和完整图像的感兴趣区域自动构建的。呈现提示为空。
     *  createDefaultRendering可以使用存储的呈现速度。
     * 
     * 
     * @param renderContext the RenderContext to use to produce the rendering.
     * @return a RenderedImage containing the rendered data.
     */
    RenderedImage createRendering(RenderContext renderContext);
}
