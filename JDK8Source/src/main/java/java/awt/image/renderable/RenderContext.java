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
import java.util.*;
import java.awt.geom.*;
import java.awt.*;
import java.awt.image.*;

/**
 * A RenderContext encapsulates the information needed to produce a
 * specific rendering from a RenderableImage.  It contains the area to
 * be rendered specified in rendering-independent terms, the
 * resolution at which the rendering is to be performed, and hints
 * used to control the rendering process.
 *
 * <p> Users create RenderContexts and pass them to the
 * RenderableImage via the createRendering method.  Most of the methods of
 * RenderContexts are not meant to be used directly by applications,
 * but by the RenderableImage and operator classes to which it is
 * passed.
 *
 * <p> The AffineTransform parameter passed into and out of this class
 * are cloned.  The RenderingHints and Shape parameters are not
 * necessarily cloneable and are therefore only reference copied.
 * Altering RenderingHints or Shape instances that are in use by
 * instances of RenderContext may have undesired side effects.
 * <p>
 *  RenderContext封装了从RenderableImage产生特定渲染所需的信息。它包含在渲染无关术语中指定的要渲染的区域,要执行渲染的分辨率,以及用于控制渲染过程的提示。
 * 
 *  <p>用户创建RenderContext并通过createRendering方法将它们传递到RenderableImage。
 *  RenderContext的大多数方法不是直接由应用程序使用,而是由RenderableImage和传递给它的操作符类来使用。
 * 
 *  <p>克隆传入和传出此类的AffineTransform参数。 RenderingHints和Shape参数不一定是可克隆的,因此只能被引用复制。
 * 改变RenderContext实例正在使用的RenderingHints或Shape实例可能会产生不良的副作用。
 * 
 */
public class RenderContext implements Cloneable {

    /** Table of hints. May be null. */
    RenderingHints hints;

    /** Transform to convert user coordinates to device coordinates.  */
    AffineTransform usr2dev;

    /** The area of interest.  May be null. */
    Shape aoi;

    // Various constructors that allow different levels of
    // specificity. If the Shape is missing the whole renderable area
    // is assumed. If hints is missing no hints are assumed.

    /**
     * Constructs a RenderContext with a given transform.
     * The area of interest is supplied as a Shape,
     * and the rendering hints are supplied as a RenderingHints object.
     *
     * <p>
     * 构造具有给定变换的RenderContext。感兴趣的区域作为Shape提供,并且呈现提示作为RenderingHints对象提供。
     * 
     * 
     * @param usr2dev an AffineTransform.
     * @param aoi a Shape representing the area of interest.
     * @param hints a RenderingHints object containing rendering hints.
     */
    public RenderContext(AffineTransform usr2dev,
                         Shape aoi,
                         RenderingHints hints) {
        this.hints = hints;
        this.aoi = aoi;
        this.usr2dev = (AffineTransform)usr2dev.clone();
    }

    /**
     * Constructs a RenderContext with a given transform.
     * The area of interest is taken to be the entire renderable area.
     * No rendering hints are used.
     *
     * <p>
     *  构造具有给定变换的RenderContext。感兴趣的区域被认为是整个可绘制区域。不使用渲染提示。
     * 
     * 
     * @param usr2dev an AffineTransform.
     */
    public RenderContext(AffineTransform usr2dev) {
        this(usr2dev, null, null);
    }

    /**
     * Constructs a RenderContext with a given transform and rendering hints.
     * The area of interest is taken to be the entire renderable area.
     *
     * <p>
     *  构造具有给定变换和渲染提示的RenderContext。感兴趣的区域被认为是整个可绘制区域。
     * 
     * 
     * @param usr2dev an AffineTransform.
     * @param hints a RenderingHints object containing rendering hints.
     */
    public RenderContext(AffineTransform usr2dev, RenderingHints hints) {
        this(usr2dev, null, hints);
    }

    /**
     * Constructs a RenderContext with a given transform and area of interest.
     * The area of interest is supplied as a Shape.
     * No rendering hints are used.
     *
     * <p>
     *  构造具有给定变换和感兴趣区域的RenderContext。感兴趣的区域作为形状提供。不使用渲染提示。
     * 
     * 
     * @param usr2dev an AffineTransform.
     * @param aoi a Shape representing the area of interest.
     */
    public RenderContext(AffineTransform usr2dev, Shape aoi) {
        this(usr2dev, aoi, null);
    }

    /**
     * Gets the rendering hints of this <code>RenderContext</code>.
     * <p>
     *  获取此<code> RenderContext </code>的呈现提示。
     * 
     * 
     * @return a <code>RenderingHints</code> object that represents
     * the rendering hints of this <code>RenderContext</code>.
     * @see #setRenderingHints(RenderingHints)
     */
    public RenderingHints getRenderingHints() {
        return hints;
    }

    /**
     * Sets the rendering hints of this <code>RenderContext</code>.
     * <p>
     *  设置此<code> RenderContext </code>的呈现提示。
     * 
     * 
     * @param hints a <code>RenderingHints</code> object that represents
     * the rendering hints to assign to this <code>RenderContext</code>.
     * @see #getRenderingHints
     */
    public void setRenderingHints(RenderingHints hints) {
        this.hints = hints;
    }

    /**
     * Sets the current user-to-device AffineTransform contained
     * in the RenderContext to a given transform.
     *
     * <p>
     *  将RenderContext中包含的当前用户到设备AffineTransform设置为给定的变换。
     * 
     * 
     * @param newTransform the new AffineTransform.
     * @see #getTransform
     */
    public void setTransform(AffineTransform newTransform) {
        usr2dev = (AffineTransform)newTransform.clone();
    }

    /**
     * Modifies the current user-to-device transform by prepending another
     * transform.  In matrix notation the operation is:
     * <pre>
     * [this] = [modTransform] x [this]
     * </pre>
     *
     * <p>
     *  通过添加另一个变换来修改当前的用户到设备变换。在矩阵符号中的操作是：
     * <pre>
     *  [this] = [modTransform] x [this]
     * </pre>
     * 
     * 
     * @param modTransform the AffineTransform to prepend to the
     *        current usr2dev transform.
     * @since 1.3
     */
    public void preConcatenateTransform(AffineTransform modTransform) {
        this.preConcetenateTransform(modTransform);
    }

    /**
     * Modifies the current user-to-device transform by prepending another
     * transform.  In matrix notation the operation is:
     * <pre>
     * [this] = [modTransform] x [this]
     * </pre>
     * This method does the same thing as the preConcatenateTransform
     * method.  It is here for backward compatibility with previous releases
     * which misspelled the method name.
     *
     * <p>
     *  通过添加另一个变换来修改当前的用户到设备变换。在矩阵符号中的操作是：
     * <pre>
     *  [this] = [modTransform] x [this]
     * </pre>
     *  这个方法和preConcatenateTransform方法做同样的事情。这是为了向后兼容以前的版本,拼写方法名称。
     * 
     * 
     * @param modTransform the AffineTransform to prepend to the
     *        current usr2dev transform.
     * @deprecated     replaced by
     *                 <code>preConcatenateTransform(AffineTransform)</code>.
     */
    @Deprecated
    public void preConcetenateTransform(AffineTransform modTransform) {
        usr2dev.preConcatenate(modTransform);
    }

    /**
     * Modifies the current user-to-device transform by appending another
     * transform.  In matrix notation the operation is:
     * <pre>
     * [this] = [this] x [modTransform]
     * </pre>
     *
     * <p>
     *  通过附加另一个变换来修改当前的用户到设备变换。在矩阵符号中的操作是：
     * <pre>
     *  [this] = [this] x [modTransform]
     * </pre>
     * 
     * 
     * @param modTransform the AffineTransform to append to the
     *        current usr2dev transform.
     * @since 1.3
     */
    public void concatenateTransform(AffineTransform modTransform) {
        this.concetenateTransform(modTransform);
    }

    /**
     * Modifies the current user-to-device transform by appending another
     * transform.  In matrix notation the operation is:
     * <pre>
     * [this] = [this] x [modTransform]
     * </pre>
     * This method does the same thing as the concatenateTransform
     * method.  It is here for backward compatibility with previous releases
     * which misspelled the method name.
     *
     * <p>
     * 通过附加另一个变换来修改当前的用户到设备变换。在矩阵符号中的操作是：
     * <pre>
     *  [this] = [this] x [modTransform]
     * </pre>
     *  这个方法和concatenateTransform方法做同样的事情。这是为了向后兼容以前的版本,拼写方法名称。
     * 
     * 
     * @param modTransform the AffineTransform to append to the
     *        current usr2dev transform.
     * @deprecated     replaced by
     *                 <code>concatenateTransform(AffineTransform)</code>.
     */
    @Deprecated
    public void concetenateTransform(AffineTransform modTransform) {
        usr2dev.concatenate(modTransform);
    }

    /**
     * Gets the current user-to-device AffineTransform.
     *
     * <p>
     *  获取当前用户到设备AffineTransform。
     * 
     * 
     * @return a reference to the current AffineTransform.
     * @see #setTransform(AffineTransform)
     */
    public AffineTransform getTransform() {
        return (AffineTransform)usr2dev.clone();
    }

    /**
     * Sets the current area of interest.  The old area is discarded.
     *
     * <p>
     *  设置当前感兴趣的区域。旧区域被丢弃。
     * 
     * 
     * @param newAoi The new area of interest.
     * @see #getAreaOfInterest
     */
    public void setAreaOfInterest(Shape newAoi) {
        aoi = newAoi;
    }

    /**
     * Gets the ares of interest currently contained in the
     * RenderContext.
     *
     * <p>
     *  获取当前包含在RenderContext中的兴趣集。
     * 
     * 
     * @return a reference to the area of interest of the RenderContext,
     *         or null if none is specified.
     * @see #setAreaOfInterest(Shape)
     */
    public Shape getAreaOfInterest() {
        return aoi;
    }

    /**
     * Makes a copy of a RenderContext. The area of interest is copied
     * by reference.  The usr2dev AffineTransform and hints are cloned,
     * while the area of interest is copied by reference.
     *
     * <p>
     *  创建RenderContext的副本。感兴趣的区域通过引用复制。 usr2dev AffineTransform和提示被克隆,而感兴趣的区域通过引用被复制。
     * 
     * @return the new cloned RenderContext.
     */
    public Object clone() {
        RenderContext newRenderContext = new RenderContext(usr2dev,
                                                           aoi, hints);
        return newRenderContext;
    }
}
