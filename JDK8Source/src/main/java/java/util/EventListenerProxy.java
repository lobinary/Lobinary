/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2004, Oracle and/or its affiliates. All rights reserved.
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

package java.util;

/**
 * An abstract wrapper class for an {@code EventListener} class
 * which associates a set of additional parameters with the listener.
 * Subclasses must provide the storage and accessor methods
 * for the additional arguments or parameters.
 * <p>
 * For example, a bean which supports named properties
 * would have a two argument method signature for adding
 * a {@code PropertyChangeListener} for a property:
 * <pre>
 * public void addPropertyChangeListener(String propertyName,
 *                                       PropertyChangeListener listener)
 * </pre>
 * If the bean also implemented the zero argument get listener method:
 * <pre>
 * public PropertyChangeListener[] getPropertyChangeListeners()
 * </pre>
 * then the array may contain inner {@code PropertyChangeListeners}
 * which are also {@code PropertyChangeListenerProxy} objects.
 * <p>
 * If the calling method is interested in retrieving the named property
 * then it would have to test the element to see if it is a proxy class.
 *
 * <p>
 *  {@code EventListener}类的抽象包装类,它将一组附加参数与侦听器关联。子类必须为附加参数或参数提供存储和访问器方法。
 * <p>
 *  例如,支持命名属性的bean将具有两个参数方法签名,用于为属性添加{@code PropertyChangeListener}：
 * <pre>
 *  public void addPropertyChangeListener(String propertyName,PropertyChangeListener listener)
 * </pre>
 *  如果bean也实现了零参数get listener方法：
 * <pre>
 *  public PropertyChangeListener [] getPropertyChangeListeners()
 * </pre>
 * 
 * @since 1.4
 */
public abstract class EventListenerProxy<T extends EventListener>
        implements EventListener {

    private final T listener;

    /**
     * Creates a proxy for the specified listener.
     *
     * <p>
     *  那么数组可能包含内部{@code PropertyChangeListeners},这也是{@code PropertyChangeListenerProxy}对象。
     * <p>
     *  如果调用方法有兴趣检索命名的属性,那么它将必须测试元素,以查看它是否是一个代理类。
     * 
     * 
     * @param listener  the listener object
     */
    public EventListenerProxy(T listener) {
        this.listener = listener;
    }

    /**
     * Returns the listener associated with the proxy.
     *
     * <p>
     *  为指定的侦听器创建代理。
     * 
     * 
     * @return  the listener associated with the proxy
     */
    public T getListener() {
        return this.listener;
    }
}
