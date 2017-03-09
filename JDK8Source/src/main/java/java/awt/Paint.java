/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2008, Oracle and/or its affiliates. All rights reserved.
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
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/**
 * This <code>Paint</code> interface defines how color patterns
 * can be generated for {@link Graphics2D} operations.  A class
 * implementing the <code>Paint</code> interface is added to the
 * <code>Graphics2D</code> context in order to define the color
 * pattern used by the <code>draw</code> and <code>fill</code> methods.
 * <p>
 * Instances of classes implementing <code>Paint</code> must be
 * read-only because the <code>Graphics2D</code> does not clone
 * these objects when they are set as an attribute with the
 * <code>setPaint</code> method or when the <code>Graphics2D</code>
 * object is itself cloned.
 * <p>
 *  这个<code> Paint </code>接口定义了如何为{@link Graphics2D}操作生成颜色模式。
 * 实现<code> Paint </code>接口的类被添加到<code> Graphics2D </code>上下文中,以便定义<code> draw </code>和<code> / code>方法。
 *  这个<code> Paint </code>接口定义了如何为{@link Graphics2D}操作生成颜色模式。
 * <p>
 *  实现<code> Paint </code>的类实例必须是只读的,因为<code> Graphics2D </code>在使用<code> setPaint </code>方法将这些对象设置为属性时,
 * 或者当<code> Graphics2D </code>对象本身被克隆时。
 * 
 * 
 * @see PaintContext
 * @see Color
 * @see GradientPaint
 * @see TexturePaint
 * @see Graphics2D#setPaint
 * @version 1.36, 06/05/07
 */

public interface Paint extends Transparency {
    /**
     * Creates and returns a {@link PaintContext} used to
     * generate the color pattern.
     * The arguments to this method convey additional information
     * about the rendering operation that may be
     * used or ignored on various implementations of the {@code Paint} interface.
     * A caller must pass non-{@code null} values for all of the arguments
     * except for the {@code ColorModel} argument which may be {@code null} to
     * indicate that no specific {@code ColorModel} type is preferred.
     * Implementations of the {@code Paint} interface are allowed to use or ignore
     * any of the arguments as makes sense for their function, and are
     * not constrained to use the specified {@code ColorModel} for the returned
     * {@code PaintContext}, even if it is not {@code null}.
     * Implementations are allowed to throw {@code NullPointerException} for
     * any {@code null} argument other than the {@code ColorModel} argument,
     * but are not required to do so.
     *
     * <p>
     * 创建并返回用于生成颜色模式的{@link PaintContext}。此方法的参数传递关于在{@code Paint}接口的各种实现上可以使用或忽略的呈现操作的附加信息。
     * 调用程序必须对除{@code ColorModel}参数之外的所有参数传递非 -  {@ code null}值,该参数可能是{@code null},表示没有特定的{@code ColorModel}
     * 类型是首选。
     * 创建并返回用于生成颜色模式的{@link PaintContext}。此方法的参数传递关于在{@code Paint}接口的各种实现上可以使用或忽略的呈现操作的附加信息。
     *  {@code Paint}接口的实现允许使用或忽略任何参数对其功能有意义,并且不限制为返回的{@code PaintContext}使用指定的{@code ColorModel},即使它不是{@code null}
     * 
     * @param cm the preferred {@link ColorModel} which represents the most convenient
     *           format for the caller to receive the pixel data, or {@code null}
     *           if there is no preference.
     * @param deviceBounds the device space bounding box
     *                     of the graphics primitive being rendered.
     *                     Implementations of the {@code Paint} interface
     *                     are allowed to throw {@code NullPointerException}
     *                     for a {@code null} {@code deviceBounds}.
     * @param userBounds the user space bounding box
     *                   of the graphics primitive being rendered.
     *                     Implementations of the {@code Paint} interface
     *                     are allowed to throw {@code NullPointerException}
     *                     for a {@code null} {@code userBounds}.
     * @param xform the {@link AffineTransform} from user
     *              space into device space.
     *                     Implementations of the {@code Paint} interface
     *                     are allowed to throw {@code NullPointerException}
     *                     for a {@code null} {@code xform}.
     * @param hints the set of hints that the context object can use to
     *              choose between rendering alternatives.
     *                     Implementations of the {@code Paint} interface
     *                     are allowed to throw {@code NullPointerException}
     *                     for a {@code null} {@code hints}.
     * @return the {@code PaintContext} for
     *         generating color patterns.
     * @see PaintContext
     * @see ColorModel
     * @see Rectangle
     * @see Rectangle2D
     * @see AffineTransform
     * @see RenderingHints
     */
    public PaintContext createContext(ColorModel cm,
                                      Rectangle deviceBounds,
                                      Rectangle2D userBounds,
                                      AffineTransform xform,
                                      RenderingHints hints);

}
