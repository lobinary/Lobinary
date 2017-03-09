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
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.RenderedImage;
import java.awt.RenderingHints;
import java.util.Hashtable;
import java.util.Vector;

/**
 * This class handles the renderable aspects of an operation with help
 * from its associated instance of a ContextualRenderedImageFactory.
 * <p>
 *  该类通过其关联的ContextualRenderedImageFactory的实例的帮助处理操作的可呈现方面。
 * 
 */
public class RenderableImageOp implements RenderableImage {

    /** A ParameterBlock containing source and parameters. */
    ParameterBlock paramBlock;

    /** The associated ContextualRenderedImageFactory. */
    ContextualRenderedImageFactory myCRIF;

    /** The bounding box of the results of this RenderableImageOp. */
    Rectangle2D boundingBox;


    /**
     * Constructs a RenderedImageOp given a
     * ContextualRenderedImageFactory object, and
     * a ParameterBlock containing RenderableImage sources and other
     * parameters.  Any RenderedImage sources referenced by the
     * ParameterBlock will be ignored.
     *
     * <p>
     *  构造一个RenderedImageOp给定一个ContextualRenderedImageFactory对象,以及一个ParameterBlock包含RenderableImage源和其他参数。
     * 将忽略ParameterBlock引用的任何RenderedImage源。
     * 
     * 
     * @param CRIF a ContextualRenderedImageFactory object
     * @param paramBlock a ParameterBlock containing this operation's source
     *        images and other parameters necessary for the operation
     *        to run.
     */
    public RenderableImageOp(ContextualRenderedImageFactory CRIF,
                             ParameterBlock paramBlock) {
        this.myCRIF = CRIF;
        this.paramBlock = (ParameterBlock) paramBlock.clone();
    }

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
    public Vector<RenderableImage> getSources() {
        return getRenderableSources();
    }

    private Vector getRenderableSources() {
        Vector sources = null;

        if (paramBlock.getNumSources() > 0) {
            sources = new Vector();
            int i = 0;
            while (i < paramBlock.getNumSources()) {
                Object o = paramBlock.getSource(i);
                if (o instanceof RenderableImage) {
                    sources.add((RenderableImage)o);
                    i++;
                } else {
                    break;
                }
            }
        }
        return sources;
    }

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
    public Object getProperty(String name) {
        return myCRIF.getProperty(paramBlock, name);
    }

    /**
     * Return a list of names recognized by getProperty.
     * <p>
     *  返回由getProperty识别的名称列表。
     * 
     * 
     * @return a list of property names.
     */
    public String[] getPropertyNames() {
        return myCRIF.getPropertyNames();
    }

    /**
     * Returns true if successive renderings (that is, calls to
     * createRendering() or createScaledRendering()) with the same arguments
     * may produce different results.  This method may be used to
     * determine whether an existing rendering may be cached and
     * reused.  The CRIF's isDynamic method will be called.
     * <p>
     * 如果连续的渲染(即使用相同的参数调用createRendering()或createScaledRendering())可能会产生不同的结果,则返回true。
     * 该方法可以用于确定现有的渲染是否可以被高速缓存和重用。 CRIF的isDynamic方法将被调用。
     * 
     * 
     * @return <code>true</code> if successive renderings with the
     *         same arguments might produce different results;
     *         <code>false</code> otherwise.
     */
    public boolean isDynamic() {
        return myCRIF.isDynamic();
    }

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
    public float getWidth() {
        if (boundingBox == null) {
            boundingBox = myCRIF.getBounds2D(paramBlock);
        }
        return (float)boundingBox.getWidth();
    }

    /**
     * Gets the height in user coordinate space.  By convention, the
     * usual height of a RenderedImage is equal to 1.0F.
     *
     * <p>
     *  获取用户坐标空间中的高度。按照惯例,RenderedImage的通常高度等于1.0F。
     * 
     * 
     * @return the height of the image in user coordinates.
     */
    public float getHeight() {
        if (boundingBox == null) {
            boundingBox = myCRIF.getBounds2D(paramBlock);
        }
        return (float)boundingBox.getHeight();
    }

    /**
     * Gets the minimum X coordinate of the rendering-independent image data.
     * <p>
     *  获取渲染无关图像数据的最小X坐标。
     * 
     */
    public float getMinX() {
        if (boundingBox == null) {
            boundingBox = myCRIF.getBounds2D(paramBlock);
        }
        return (float)boundingBox.getMinX();
    }

    /**
     * Gets the minimum Y coordinate of the rendering-independent image data.
     * <p>
     *  获取渲染无关图像数据的最小Y坐标。
     * 
     */
    public float getMinY() {
        if (boundingBox == null) {
            boundingBox = myCRIF.getBounds2D(paramBlock);
        }
        return (float)boundingBox.getMinY();
    }

    /**
     * Change the current ParameterBlock of the operation, allowing
     * editing of image rendering chains.  The effects of such a
     * change will be visible when a new rendering is created from
     * this RenderableImageOp or any dependent RenderableImageOp.
     *
     * <p>
     *  更改操作的当前ParameterBlock,允许编辑图像渲染链。当从此RenderableImageOp或任何依赖的RenderableImageOp创建新的渲染时,这种更改的效果将可见。
     * 
     * 
     * @param paramBlock the new ParameterBlock.
     * @return the old ParameterBlock.
     * @see #getParameterBlock
     */
    public ParameterBlock setParameterBlock(ParameterBlock paramBlock) {
        ParameterBlock oldParamBlock = this.paramBlock;
        this.paramBlock = (ParameterBlock)paramBlock.clone();
        return oldParamBlock;
    }

    /**
     * Returns a reference to the current parameter block.
     * <p>
     *  返回当前参数块的引用。
     * 
     * 
     * @return the <code>ParameterBlock</code> of this
     *         <code>RenderableImageOp</code>.
     * @see #setParameterBlock(ParameterBlock)
     */
    public ParameterBlock getParameterBlock() {
        return paramBlock;
    }

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
     * <p>如果w == 0,它将等于Math.round(h *(getWidth()/ getHeight()))。
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
    public RenderedImage createScaledRendering(int w, int h,
                                               RenderingHints hints) {
        // DSR -- code to try to get a unit scale
        double sx = (double)w/getWidth();
        double sy = (double)h/getHeight();
        if (Math.abs(sx/sy - 1.0) < 0.01) {
            sx = sy;
        }
        AffineTransform usr2dev = AffineTransform.getScaleInstance(sx, sy);
        RenderContext newRC = new RenderContext(usr2dev, hints);
        return createRendering(newRC);
    }

    /**
     * Gets a RenderedImage instance of this image with a default
     * width and height in pixels.  The RenderContext is built
     * automatically with an appropriate usr2dev transform and an area
     * of interest of the full image.  All the rendering hints come
     * from hints passed in.  Implementors of this interface must be
     * sure that there is a defined default width and height.
     *
     * <p>
     *  获取此图片的RenderedImage实例,默认宽度和高度(以像素为单位)。 RenderContext是使用适当的usr2dev变换和完整图像的感兴趣区域自动构建的。
     * 所有的渲染提示都来自传递的提示。此接口的实现者必须确保有一个定义的默认宽度和高度。
     * 
     * 
     * @return a RenderedImage containing the rendered data.
     */
    public RenderedImage createDefaultRendering() {
        AffineTransform usr2dev = new AffineTransform(); // Identity
        RenderContext newRC = new RenderContext(usr2dev);
        return createRendering(newRC);
    }

    /**
     * Creates a RenderedImage which represents this
     * RenderableImageOp (including its Renderable sources) rendered
     * according to the given RenderContext.
     *
     * <p> This method supports chaining of either Renderable or
     * RenderedImage operations.  If sources in
     * the ParameterBlock used to construct the RenderableImageOp are
     * RenderableImages, then a three step process is followed:
     *
     * <ol>
     * <li> mapRenderContext() is called on the associated CRIF for
     * each RenderableImage source;
     * <li> createRendering() is called on each of the RenderableImage sources
     * using the backwards-mapped RenderContexts obtained in step 1,
     * resulting in a rendering of each source;
     * <li> ContextualRenderedImageFactory.create() is called
     * with a new ParameterBlock containing the parameters of
     * the RenderableImageOp and the RenderedImages that were created by the
     * createRendering() calls.
     * </ol>
     *
     * <p> If the elements of the source Vector of
     * the ParameterBlock used to construct the RenderableImageOp are
     * instances of RenderedImage, then the CRIF.create() method is
     * called immediately using the original ParameterBlock.
     * This provides a basis case for the recursion.
     *
     * <p> The created RenderedImage may have a property identified
     * by the String HINTS_OBSERVED to indicate which RenderingHints
     * (from the RenderContext) were used to create the image.
     * In addition any RenderedImages
     * that are obtained via the getSources() method on the created
     * RenderedImage may have such a property.
     *
     * <p>
     *  创建一个RenderedImage,它表示根据给定的RenderContext渲染的这个RenderableImageOp(包括它的Renderable源)。
     * 
     *  <p>此方法支持Renderable或RenderedImage操作的链接。
     * 如果用于构造RenderableImageOp的ParameterBlock中的源是RenderableImages,那么将执行三个步骤：。
     * 
     * <ol>
     * <li>在每个RenderableImage源的关联CRIF上调用mapRenderContext(); <li>使用在步骤1中获得的向后映射的RenderContexts在每个RenderableIm
     * age源上调用createRendering(),从而导致每个源的呈现; <li>使用包含由createRendering()调用创建的RenderableImageOp和RenderedImages的
     * 参数的新ParameterBlock调用ContextualRenderedImageFactory.create()。
     * 
     * @param renderContext The RenderContext to use to perform the rendering.
     * @return a RenderedImage containing the desired output image.
     */
    public RenderedImage createRendering(RenderContext renderContext) {
        RenderedImage image = null;
        RenderContext rcOut = null;

        // Clone the original ParameterBlock; if the ParameterBlock
        // contains RenderableImage sources, they will be replaced by
        // RenderedImages.
        ParameterBlock renderedParamBlock = (ParameterBlock)paramBlock.clone();
        Vector sources = getRenderableSources();

        try {
            // This assumes that if there is no renderable source, that there
            // is a rendered source in paramBlock

            if (sources != null) {
                Vector renderedSources = new Vector();
                for (int i = 0; i < sources.size(); i++) {
                    rcOut = myCRIF.mapRenderContext(i, renderContext,
                                                    paramBlock, this);
                    RenderedImage rdrdImage =
                       ((RenderableImage)sources.elementAt(i)).createRendering(rcOut);
                    if (rdrdImage == null) {
                        return null;
                    }

                    // Add this rendered image to the ParameterBlock's
                    // list of RenderedImages.
                    renderedSources.addElement(rdrdImage);
                }

                if (renderedSources.size() > 0) {
                    renderedParamBlock.setSources(renderedSources);
                }
            }

            return myCRIF.create(renderContext, renderedParamBlock);
        } catch (ArrayIndexOutOfBoundsException e) {
            // This should never happen
            return null;
        }
    }
}
