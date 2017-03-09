/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.accessibility;

/**
 * Interface Accessible is the main interface for the accessibility package.
 * All components that support
 * the accessibility package must implement this interface.
 * It contains a single method, {@link #getAccessibleContext}, which
 * returns an instance of the class {@link AccessibleContext}.
 *
 * <p>
 *  Interface Accessible是辅助功能包的主要接口。支持辅助功能包的所有组件都必须实现此接口。
 * 它包含一个方法{@link #getAccessibleContext},它返回类{@link AccessibleContext}的一个实例。
 * 
 * 
 * @author      Peter Korn
 * @author      Hans Muller
 * @author      Willie Walker
 */
public interface Accessible {

    /**
     * Returns the AccessibleContext associated with this object.  In most
     * cases, the return value should not be null if the object implements
     * interface Accessible.  If a component developer creates a subclass
     * of an object that implements Accessible, and that subclass
     * is not Accessible, the developer should override the
     * getAccessibleContext method to return null.
     * <p>
     *  返回与此对象关联的AccessibleContext。在大多数情况下,如果对象实现接口Accessible,则返回值不应为null。
     * 如果组件开发人员创建实现Accessible的对象的子类,并且该子类不可访问,则开发人员应覆盖getAccessibleContext方法以返回null。
     * 
     * @return the AccessibleContext associated with this object
     */
    public AccessibleContext getAccessibleContext();
}
