/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2007, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.image.Raster;
import java.awt.image.ColorModel;

/**
 * The <code>PaintContext</code> interface defines the encapsulated
 * and optimized environment to generate color patterns in device
 * space for fill or stroke operations on a
 * {@link Graphics2D}.  The <code>PaintContext</code> provides
 * the necessary colors for <code>Graphics2D</code> operations in the
 * form of a {@link Raster} associated with a {@link ColorModel}.
 * The <code>PaintContext</code> maintains state for a particular paint
 * operation.  In a multi-threaded environment, several
 * contexts can exist simultaneously for a single {@link Paint} object.
 * <p>
 *  <code> PaintContext </code>接口定义了封装和优化的环境,以在设备空间中为{@link Graphics2D}生成填充或描边操作的颜色模式。
 *  <code> PaintContext </code>以与{@link ColorModel}相关联的{@link Raster}形式提供<code> Graphics2D </code>操作所需的颜
 * 色。
 *  <code> PaintContext </code>接口定义了封装和优化的环境,以在设备空间中为{@link Graphics2D}生成填充或描边操作的颜色模式。
 *  <code> PaintContext </code>维护特定绘图操作的状态。在多线程环境中,单个{@link Paint}对象可以同时存在多个上下文。
 * 
 * 
 * @see Paint
 */

public interface PaintContext {
    /**
     * Releases the resources allocated for the operation.
     * <p>
     *  释放为操作分配的资源。
     * 
     */
    public void dispose();

    /**
     * Returns the <code>ColorModel</code> of the output.  Note that
     * this <code>ColorModel</code> might be different from the hint
     * specified in the
     * {@link Paint#createContext(ColorModel, Rectangle, Rectangle2D,
AffineTransform, RenderingHints) createContext} method of
     * <code>Paint</code>.  Not all <code>PaintContext</code> objects are
     * capable of generating color patterns in an arbitrary
     * <code>ColorModel</code>.
     * <p>
     *  返回输出的<code> ColorModel </code>。
     * 注意这个<code> ColorModel </code>可能不同于<code> Paint </code>的{@link Paint#createContext(ColorModel,Rectangle,Rectangle2D,AffineTransform,RenderingHints)createContext}
     * 方法中指定的提示。
     *  返回输出的<code> ColorModel </code>。
     * 并非所有<code> PaintContext </code>对象都能够在任意的<code> ColorModel </code>中生成颜色模式。
     * 
     * @return the <code>ColorModel</code> of the output.
     */
    ColorModel getColorModel();

    /**
     * Returns a <code>Raster</code> containing the colors generated for
     * the graphics operation.
     * <p>
     * 
     * 
     * @param x the x coordinate of the area in device space
     * for which colors are generated.
     * @param y the y coordinate of the area in device space
     * for which colors are generated.
     * @param w the width of the area in device space
     * @param h the height of the area in device space
     * @return a <code>Raster</code> representing the specified
     * rectangular area and containing the colors generated for
     * the graphics operation.
     */
    Raster getRaster(int x,
                     int y,
                     int w,
                     int h);

}
