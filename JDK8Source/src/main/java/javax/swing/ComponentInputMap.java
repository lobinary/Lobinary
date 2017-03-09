/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2011, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing;

/**
 * A <code>ComponentInputMap</code> is an <code>InputMap</code>
 * associated with a particular <code>JComponent</code>.
 * The component is automatically notified whenever
 * the <code>ComponentInputMap</code> changes.
 * <code>ComponentInputMap</code>s are used for
 * <code>WHEN_IN_FOCUSED_WINDOW</code> bindings.
 *
 * <p>
 *  <code> ComponentInputMap </code>是与特定<code> JComponent </code>相关联的<code> InputMap </code>。
 * 当<code> ComponentInputMap </code>更改时,将自动通知组件。
 *  <code> ComponentInputMap </code> s用于<code> WHEN_IN_FOCUSED_WINDOW </code>绑定。
 * 
 * 
 * @author Scott Violet
 * @since 1.3
 */
@SuppressWarnings("serial")
public class ComponentInputMap extends InputMap {
    /** Component binding is created for. */
    private JComponent          component;

    /**
     * Creates a <code>ComponentInputMap</code> associated with the
     * specified component.
     *
     * <p>
     *  创建与指定组件关联的<code> ComponentInputMap </code>。
     * 
     * 
     * @param component  a non-null <code>JComponent</code>
     * @throws IllegalArgumentException  if <code>component</code> is null
     */
    public ComponentInputMap(JComponent component) {
        this.component = component;
        if (component == null) {
            throw new IllegalArgumentException("ComponentInputMaps must be associated with a non-null JComponent");
        }
    }

    /**
     * Sets the parent, which must be a <code>ComponentInputMap</code>
     * associated with the same component as this
     * <code>ComponentInputMap</code>.
     *
     * <p>
     *  设置父代,它必须是与<code> ComponentInputMap </code>相同的组件相关联的<code> ComponentInputMap </code>。
     * 
     * 
     * @param map  a <code>ComponentInputMap</code>
     *
     * @throws IllegalArgumentException  if <code>map</code>
     *         is not a <code>ComponentInputMap</code>
     *         or is not associated with the same component
     */
    public void setParent(InputMap map) {
        if (getParent() == map) {
            return;
        }
        if (map != null && (!(map instanceof ComponentInputMap) ||
                 ((ComponentInputMap)map).getComponent() != getComponent())) {
            throw new IllegalArgumentException("ComponentInputMaps must have a parent ComponentInputMap associated with the same component");
        }
        super.setParent(map);
        getComponent().componentInputMapChanged(this);
    }

    /**
     * Returns the component the <code>InputMap</code> was created for.
     * <p>
     *  返回为其创建的<code> InputMap </code>组件。
     * 
     */
    public JComponent getComponent() {
        return component;
    }

    /**
     * Adds a binding for <code>keyStroke</code> to <code>actionMapKey</code>.
     * If <code>actionMapKey</code> is null, this removes the current binding
     * for <code>keyStroke</code>.
     * <p>
     *  将<code> keyStroke </code>的绑定添加到<code> actionMapKey </code>。
     * 如果<code> actionMapKey </code>为null,则会删除<code> keyStroke </code>的当前绑定。
     * 
     */
    public void put(KeyStroke keyStroke, Object actionMapKey) {
        super.put(keyStroke, actionMapKey);
        if (getComponent() != null) {
            getComponent().componentInputMapChanged(this);
        }
    }

    /**
     * Removes the binding for <code>key</code> from this object.
     * <p>
     *  从此对象中删除<code>键</code>的绑定。
     * 
     */
    public void remove(KeyStroke key) {
        super.remove(key);
        if (getComponent() != null) {
            getComponent().componentInputMapChanged(this);
        }
    }

    /**
     * Removes all the mappings from this object.
     * <p>
     *  从此对象中删除所有映射。
     */
    public void clear() {
        int oldSize = size();
        super.clear();
        if (oldSize > 0 && getComponent() != null) {
            getComponent().componentInputMapChanged(this);
        }
    }
}
