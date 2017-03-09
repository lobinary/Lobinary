/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2006, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.geom;

import java.awt.Shape;

/**
 * The {@code GeneralPath} class represents a geometric path
 * constructed from straight lines, and quadratic and cubic
 * (B&eacute;zier) curves.  It can contain multiple subpaths.
 * <p>
 * {@code GeneralPath} is a legacy final class which exactly
 * implements the behavior of its superclass {@link Path2D.Float}.
 * Together with {@link Path2D.Double}, the {@link Path2D} classes
 * provide full implementations of a general geometric path that
 * support all of the functionality of the {@link Shape} and
 * {@link PathIterator} interfaces with the ability to explicitly
 * select different levels of internal coordinate precision.
 * <p>
 * Use {@code Path2D.Float} (or this legacy {@code GeneralPath}
 * subclass) when dealing with data that can be represented
 * and used with floating point precision.  Use {@code Path2D.Double}
 * for data that requires the accuracy or range of double precision.
 *
 * <p>
 *  {@code GeneralPath}类表示从直线,二次和三次(B&eacute; zier)曲线构成的几何路径。它可以包含多个子路径。
 * <p>
 *  {@code GeneralPath}是一个传统的final类,它完全实现了其超类{@link Path2D.Float}的行为。
 * 与{@link Path2D.Double}一起,{@link Path2D}类提供了支持{@link Shape}和{@link PathIterator}接口的所有功能的一般几何路径的完整实现,具有
 * 显式选择不同级别的内部坐标精度。
 *  {@code GeneralPath}是一个传统的final类,它完全实现了其超类{@link Path2D.Float}的行为。
 * <p>
 *  在处理可以使用浮点精度表示和使用的数据时,使用{@code Path2D.Float}(或此旧版{@code GeneralPath}子类)。
 * 对需要精度或双精度范围的数据使用{@code Path2D.Double}。
 * 
 * 
 * @author Jim Graham
 * @since 1.2
 */
public final class GeneralPath extends Path2D.Float {
    /**
     * Constructs a new empty single precision {@code GeneralPath} object
     * with a default winding rule of {@link #WIND_NON_ZERO}.
     *
     * <p>
     *  使用默认绕组规则{@link #WIND_NON_ZERO}构造新的空单精度{@code GeneralPath}对象。
     * 
     * 
     * @since 1.2
     */
    public GeneralPath() {
        super(WIND_NON_ZERO, INIT_SIZE);
    }

    /**
     * Constructs a new <code>GeneralPath</code> object with the specified
     * winding rule to control operations that require the interior of the
     * path to be defined.
     *
     * <p>
     *  使用指定的绕组规则构造新的<code> GeneralPath </code>对象,以控制需要定义路径内部的操作。
     * 
     * 
     * @param rule the winding rule
     * @see #WIND_EVEN_ODD
     * @see #WIND_NON_ZERO
     * @since 1.2
     */
    public GeneralPath(int rule) {
        super(rule, INIT_SIZE);
    }

    /**
     * Constructs a new <code>GeneralPath</code> object with the specified
     * winding rule and the specified initial capacity to store path
     * coordinates.
     * This number is an initial guess as to how many path segments
     * will be added to the path, but the storage is expanded as
     * needed to store whatever path segments are added.
     *
     * <p>
     * 使用指定的绕组规则和指定的初始容量构造一个新的<code> GeneralPath </code>对象,以存储路径坐标。
     * 这个数字是关于将多少路径段添加到路径的初始猜测,但是根据需要扩展存储器以存储添加的任何路径段。
     * 
     * 
     * @param rule the winding rule
     * @param initialCapacity the estimate for the number of path segments
     *                        in the path
     * @see #WIND_EVEN_ODD
     * @see #WIND_NON_ZERO
     * @since 1.2
     */
    public GeneralPath(int rule, int initialCapacity) {
        super(rule, initialCapacity);
    }

    /**
     * Constructs a new <code>GeneralPath</code> object from an arbitrary
     * {@link Shape} object.
     * All of the initial geometry and the winding rule for this path are
     * taken from the specified <code>Shape</code> object.
     *
     * <p>
     *  从任意的{@link Shape}对象构造一个新的<code> GeneralPath </code>对象。这个路径的所有初始几何和绕组规则都取自指定的<code> Shape </code>对象。
     * 
     * 
     * @param s the specified <code>Shape</code> object
     * @since 1.2
     */
    public GeneralPath(Shape s) {
        super(s, null);
    }

    GeneralPath(int windingRule,
                byte[] pointTypes,
                int numTypes,
                float[] pointCoords,
                int numCoords)
    {
        // used to construct from native

        this.windingRule = windingRule;
        this.pointTypes = pointTypes;
        this.numTypes = numTypes;
        this.floatCoords = pointCoords;
        this.numCoords = numCoords;
    }

    /*
     * JDK 1.6 serialVersionUID
     * <p>
     *  JDK 1.6 serialVersionUID
     */
    private static final long serialVersionUID = -8327096662768731142L;
}
