/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2008, Oracle and/or its affiliates. All rights reserved.
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
import java.awt.geom.Rectangle2D;
import java.awt.image.RenderedImage;

/**
 * ContextualRenderedImageFactory provides an interface for the
 * functionality that may differ between instances of
 * RenderableImageOp.  Thus different operations on RenderableImages
 * may be performed by a single class such as RenderedImageOp through
 * the use of multiple instances of ContextualRenderedImageFactory.
 * The name ContextualRenderedImageFactory is commonly shortened to
 * "CRIF."
 *
 * <p> All operations that are to be used in a rendering-independent
 * chain must implement ContextualRenderedImageFactory.
 *
 * <p> Classes that implement this interface must provide a
 * constructor with no arguments.
 * <p>
 *  ContextualRenderedImageFactory为RenderableImageOp实例之间可能不同的功能提供了一个接口。
 * 因此,对RenderableImages的不同操作可以通过使用ContextualRenderedImageFactory的多个实例由诸如RenderedImageOp的单个类来执行。
 * 名称ContextualRenderedImageFactory通常缩写为"CRIF"。
 * 
 *  <p>在渲染独立链中使用的所有操作都必须实现ContextualRenderedImageFactory。
 * 
 *  <p>实现此接口的类必须提供一个没有参数的构造函数。
 * 
 */
public interface ContextualRenderedImageFactory extends RenderedImageFactory {

    /**
     * Maps the operation's output RenderContext into a RenderContext
     * for each of the operation's sources.  This is useful for
     * operations that can be expressed in whole or in part simply as
     * alterations in the RenderContext, such as an affine mapping, or
     * operations that wish to obtain lower quality renderings of
     * their sources in order to save processing effort or
     * transmission bandwith.  Some operations, such as blur, can also
     * use this mechanism to avoid obtaining sources of higher quality
     * than necessary.
     *
     * <p>
     * 将操作的输出RenderContext映射到每个操作源的RenderContext。
     * 这对于可以完全或部分简单地表示为RenderContext中的改变(例如仿射映射)的操作或者希望获得其源的较低质量渲染以便节省处理努力或传输带宽的操作是有用的。
     * 一些操作,例如模糊,也可以使用这种机制避免获得比必要的更高质量的源。
     * 
     * 
     * @param i the index of the source image.
     * @param renderContext the RenderContext being applied to the operation.
     * @param paramBlock a ParameterBlock containing the operation's
     *        sources and parameters.
     * @param image the RenderableImage being rendered.
     * @return a <code>RenderContext</code> for
     *         the source at the specified index of the parameters
     *         Vector contained in the specified ParameterBlock.
     */
    RenderContext mapRenderContext(int i,
                                   RenderContext renderContext,
                                   ParameterBlock paramBlock,
                                   RenderableImage image);

    /**
     * Creates a rendering, given a RenderContext and a ParameterBlock
     * containing the operation's sources and parameters.  The output
     * is a RenderedImage that takes the RenderContext into account to
     * determine its dimensions and placement on the image plane.
     * This method houses the "intelligence" that allows a
     * rendering-independent operation to adapt to a specific
     * RenderContext.
     *
     * <p>
     *  创建一个渲染,给定一个RenderContext和一个ParameterBlock包含操作的源和参数。
     * 输出是RenderedImage,它考虑RenderContext以确定其在图像平面上的尺寸和位置。这种方法拥有"智能",允许渲染独立操作适应特定的RenderContext。
     * 
     * 
     * @param renderContext The RenderContext specifying the rendering
     * @param paramBlock a ParameterBlock containing the operation's
     *        sources and parameters
     * @return a <code>RenderedImage</code> from the sources and parameters
     *         in the specified ParameterBlock and according to the
     *         rendering instructions in the specified RenderContext.
     */
    RenderedImage create(RenderContext renderContext,
                         ParameterBlock paramBlock);

    /**
     * Returns the bounding box for the output of the operation,
     * performed on a given set of sources, in rendering-independent
     * space.  The bounds are returned as a Rectangle2D, that is, an
     * axis-aligned rectangle with floating-point corner coordinates.
     *
     * <p>
     *  返回在独立于渲染的空间中对给定源集合执行的操作输出的边界框。边界作为Rectangle2D返回,即具有浮点角坐标的轴对齐矩形。
     * 
     * 
     * @param paramBlock a ParameterBlock containing the operation's
     *        sources and parameters.
     * @return a Rectangle2D specifying the rendering-independent
     *         bounding box of the output.
     */
    Rectangle2D getBounds2D(ParameterBlock paramBlock);

    /**
     * Gets the appropriate instance of the property specified by the name
     * parameter.  This method must determine which instance of a property to
     * return when there are multiple sources that each specify the property.
     *
     * <p>
     *  获取由name参数指定的属性的适当实例。当有多个源指定属性时,此方法必须确定要返回的属性的哪个实例。
     * 
     * 
     * @param paramBlock a ParameterBlock containing the operation's
     *        sources and parameters.
     * @param name a String naming the desired property.
     * @return an object reference to the value of the property requested.
     */
    Object getProperty(ParameterBlock paramBlock, String name);

    /**
     * Returns a list of names recognized by getProperty.
     * <p>
     *  返回由getProperty识别的名称列表。
     * 
     * 
     * @return the list of property names.
     */
    String[] getPropertyNames();

    /**
     * Returns true if successive renderings (that is, calls to
     * create(RenderContext, ParameterBlock)) with the same arguments
     * may produce different results.  This method may be used to
     * determine whether an existing rendering may be cached and
     * reused.  It is always safe to return true.
     * <p>
     * 如果使用相同参数的连续渲染(即调用create(RenderContext,ParameterBlock))可能会产生不同的结果,则返回true。该方法可以用于确定现有的渲染是否可以被高速缓存和重用。
     * 返回true总是安全的。
     * 
     * @return <code>true</code> if successive renderings with the
     *         same arguments might produce different results;
     *         <code>false</code> otherwise.
     */
    boolean isDynamic();
}
