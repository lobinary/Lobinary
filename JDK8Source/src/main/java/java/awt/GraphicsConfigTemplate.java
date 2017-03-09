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

import java.io.*;

/**
 * The <code>GraphicsConfigTemplate</code> class is used to obtain a valid
 * {@link GraphicsConfiguration}.  A user instantiates one of these
 * objects and then sets all non-default attributes as desired.  The
 * {@link GraphicsDevice#getBestConfiguration} method found in the
 * {@link GraphicsDevice} class is then called with this
 * <code>GraphicsConfigTemplate</code>.  A valid
 * <code>GraphicsConfiguration</code> is returned that meets or exceeds
 * what was requested in the <code>GraphicsConfigTemplate</code>.
 * <p>
 *  <code> GraphicsConfigTemplate </code>类用于获取有效的{@link GraphicsConfiguration}。
 * 用户实例化这些对象中的一个,然后根据需要设置所有非默认属性。
 * 然后,使用此<code> GraphicsConfigTemplate </code>来调用{@link GraphicsDevice}类中的{@link GraphicsDevice#getBestConfiguration}
 * 方法。
 * 用户实例化这些对象中的一个,然后根据需要设置所有非默认属性。
 * 返回满足或超过<code> GraphicsConfigTemplate </code>中请求的有效<code> GraphicsConfiguration </code>。
 * 
 * 
 * @see GraphicsDevice
 * @see GraphicsConfiguration
 *
 * @since       1.2
 */
public abstract class GraphicsConfigTemplate implements Serializable {
    /*
     * serialVersionUID
     * <p>
     *  serialVersionUID
     * 
     */
    private static final long serialVersionUID = -8061369279557787079L;

    /**
     * This class is an abstract class so only subclasses can be
     * instantiated.
     * <p>
     *  这个类是一个抽象类,所以只有子类可以实例化。
     * 
     */
    public GraphicsConfigTemplate() {
    }

    /**
     * Value used for "Enum" (Integer) type.  States that this
     * feature is required for the <code>GraphicsConfiguration</code>
     * object.  If this feature is not available, do not select the
     * <code>GraphicsConfiguration</code> object.
     * <p>
     *  用于"枚举"(整数)类型的值。表示<code> GraphicsConfiguration </code>对象需要此功能。
     * 如果此功能不可用,请不要选择<code> GraphicsConfiguration </code>对象。
     * 
     */
    public static final int REQUIRED    = 1;

    /**
     * Value used for "Enum" (Integer) type.  States that this
     * feature is desired for the <code>GraphicsConfiguration</code>
     * object.  A selection with this feature is preferred over a
     * selection that does not include this feature, although both
     * selections can be considered valid matches.
     * <p>
     *  用于"枚举"(整数)类型的值。表示<code> GraphicsConfiguration </code>对象需要此功能。具有此功能的选择优先于不包括此功能的选择,但两个选择都可视为有效匹配。
     * 
     */
    public static final int PREFERRED   = 2;

    /**
     * Value used for "Enum" (Integer) type.  States that this
     * feature is not necessary for the selection of the
     * <code>GraphicsConfiguration</code> object.  A selection
     * without this feature is preferred over a selection that
     * includes this feature since it is not used.
     * <p>
     * 用于"枚举"(整数)类型的值。表示此功能对于选择<code> GraphicsConfiguration </code>对象不是必需的。没有此功能的选择优于包含此功能的选择,因为它不使用。
     * 
     */
    public static final int UNNECESSARY = 3;

    /**
     * Returns the "best" configuration possible that passes the
     * criteria defined in the <code>GraphicsConfigTemplate</code>.
     * <p>
     *  返回通过<code> GraphicsConfigTemplate </code>中定义的条件的"最佳"配置。
     * 
     * 
     * @param gc the array of <code>GraphicsConfiguration</code>
     * objects to choose from.
     * @return a <code>GraphicsConfiguration</code> object that is
     * the best configuration possible.
     * @see GraphicsConfiguration
     */
    public abstract GraphicsConfiguration
      getBestConfiguration(GraphicsConfiguration[] gc);

    /**
     * Returns a <code>boolean</code> indicating whether or
     * not the specified <code>GraphicsConfiguration</code> can be
     * used to create a drawing surface that supports the indicated
     * features.
     * <p>
     *  返回<code> boolean </code>,指示指定的<code> GraphicsConfiguration </code>是否可用于创建支持指定要素的图面。
     * 
     * @param gc the <code>GraphicsConfiguration</code> object to test
     * @return <code>true</code> if this
     * <code>GraphicsConfiguration</code> object can be used to create
     * surfaces that support the indicated features;
     * <code>false</code> if the <code>GraphicsConfiguration</code> can
     * not be used to create a drawing surface usable by this Java(tm)
     * API.
     */
    public abstract boolean
      isGraphicsConfigSupported(GraphicsConfiguration gc);

}
