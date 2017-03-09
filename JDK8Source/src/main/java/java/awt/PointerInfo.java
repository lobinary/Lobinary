/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2014, Oracle and/or its affiliates. All rights reserved.
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

/**
 * A class that describes the pointer position.
 * It provides the {@code GraphicsDevice} where the pointer is and
 * the {@code Point} that represents the coordinates of the pointer.
 * <p>
 * Instances of this class should be obtained via
 * {@link MouseInfo#getPointerInfo}.
 * The {@code PointerInfo} instance is not updated dynamically as the mouse
 * moves. To get the updated location, you must call
 * {@link MouseInfo#getPointerInfo} again.
 *
 * <p>
 *  描述指针位置的类。它提供了指针所在的{@code GraphicsDevice}和表示指针坐标的{@code Point}。
 * <p>
 *  此类的实例应通过{@link MouseInfo#getPointerInfo}获取。 {@code PointerInfo}实例不会随着鼠标移动而动态更新。
 * 要获取更新的位置,您必须再次调用{@link MouseInfo#getPointerInfo}。
 * 
 * 
 * @see MouseInfo#getPointerInfo
 * @author Roman Poborchiy
 * @since 1.5
 */
public class PointerInfo {

    private final GraphicsDevice device;
    private final Point location;

    /**
     * Package-private constructor to prevent instantiation.
     * <p>
     *  Package-private构造函数以防止实例化。
     * 
     */
    PointerInfo(final GraphicsDevice device, final Point location) {
        this.device = device;
        this.location = location;
    }

    /**
     * Returns the {@code GraphicsDevice} where the mouse pointer was at the
     * moment this {@code PointerInfo} was created.
     *
     * <p>
     *  返回{@code GraphicsDevice},其中鼠标指针在创建此{@code PointerInfo}的时刻。
     * 
     * 
     * @return {@code GraphicsDevice} corresponding to the pointer
     * @since 1.5
     */
    public GraphicsDevice getDevice() {
        return device;
    }

    /**
     * Returns the {@code Point} that represents the coordinates of the pointer
     * on the screen. See {@link MouseInfo#getPointerInfo} for more information
     * about coordinate calculation for multiscreen systems.
     *
     * <p>
     *  返回表示指针在屏幕上的坐标的{@code Point}。有关多屏幕系统的坐标计算的更多信息,请参阅{@link MouseInfo#getPointerInfo}。
     * 
     * @return coordinates of mouse pointer
     * @see MouseInfo
     * @see MouseInfo#getPointerInfo
     * @since 1.5
     */
    public Point getLocation() {
        return location;
    }
}
