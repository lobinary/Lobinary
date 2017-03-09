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
import java.awt.image.RenderedImage;
import java.awt.RenderingHints;

/**
 * The RenderedImageFactory interface (often abbreviated RIF) is
 * intended to be implemented by classes that wish to act as factories
 * to produce different renderings, for example by executing a series
 * of BufferedImageOps on a set of sources, depending on a specific
 * set of parameters, properties, and rendering hints.
 * <p>
 *  RenderedImageFactory接口(通常缩写为RIF)旨在由希望作为工厂来产生不同渲染的类实现,例如通过在一组源上执行一系列BufferedImageOps,这取决于特定的一组参数,属性,和
 * 渲染提示。
 * 
 */
public interface RenderedImageFactory {

  /**
   * Creates a RenderedImage representing the results of an imaging
   * operation (or chain of operations) for a given ParameterBlock and
   * RenderingHints.  The RIF may also query any source images
   * referenced by the ParameterBlock for their dimensions,
   * SampleModels, properties, etc., as necessary.
   *
   * <p> The create() method can return null if the
   * RenderedImageFactory is not capable of producing output for the
   * given set of source images and parameters.  For example, if a
   * RenderedImageFactory is only capable of performing a 3x3
   * convolution on single-banded image data, and the source image has
   * multiple bands or the convolution Kernel is 5x5, null should be
   * returned.
   *
   * <p> Hints should be taken into account, but can be ignored.
   * The created RenderedImage may have a property identified
   * by the String HINTS_OBSERVED to indicate which RenderingHints
   * were used to create the image.  In addition any RenderedImages
   * that are obtained via the getSources() method on the created
   * RenderedImage may have such a property.
   *
   * <p>
   *  创建表示给定ParameterBlock和RenderingHints的成像操作(或操作链)的结果的RenderedImage。
   *  RIF还可以根据需要查询由ParameterBlock引用的任何源图像的尺寸,SampleModels,属性等。
   * 
   * <p>如果RenderedImageFactory不能为给定的源图像和参数集生成输出,则create()方法可以返回null。
   * 例如,如果RenderedImageFactory仅能够对单带图像数据执行3x3卷积,并且源图像具有多个带或卷积内核为5x5,则应返回null。
   * 
   * @param paramBlock a ParameterBlock containing sources and parameters
   *        for the RenderedImage to be created.
   * @param hints a RenderingHints object containing hints.
   * @return A RenderedImage containing the desired output.
   */
  RenderedImage create(ParameterBlock paramBlock,
                       RenderingHints hints);
}
