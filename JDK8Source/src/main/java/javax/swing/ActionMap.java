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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

/**
 * <code>ActionMap</code> provides mappings from
 * <code>Object</code>s
 * (called <em>keys</em> or <em><code>Action</code> names</em>)
 * to <code>Action</code>s.
 * An <code>ActionMap</code> is usually used with an <code>InputMap</code>
 * to locate a particular action
 * when a key is pressed. As with <code>InputMap</code>,
 * an <code>ActionMap</code> can have a parent
 * that is searched for keys not defined in the <code>ActionMap</code>.
 * <p>As with <code>InputMap</code> if you create a cycle, eg:
 * <pre>
 *   ActionMap am = new ActionMap();
 *   ActionMap bm = new ActionMap():
 *   am.setParent(bm);
 *   bm.setParent(am);
 * </pre>
 * some of the methods will cause a StackOverflowError to be thrown.
 *
 * <p>
 *  <code> ActionMap </code>提供从<code> Object </code>(称为<em>键</em>或<em> <code> Action </code>名称<代码>操作</code>
 * 。
 *  <code> ActionMap </code>通常与<code> InputMap </code>一起使用,以在按下某个键时查找特定操作。
 * 与<code> InputMap </code>一样,<code> ActionMap </code>可以具有搜索未在<code> ActionMap </code>中定义的键的父级。
 *  <p>与<code> InputMap </code>一样,如果创建一个循环,例如：。
 * <pre>
 *  ActionMap am = new ActionMap(); ActionMap bm = new ActionMap()：am.setParent(bm); bm.setParent(am);
 * </pre>
 *  一些方法将导致StackOverflowError被抛出。
 * 
 * 
 * @see InputMap
 *
 * @author Scott Violet
 * @since 1.3
 */
@SuppressWarnings("serial")
public class ActionMap implements Serializable {
    /** Handles the mapping between Action name and Action. */
    private transient ArrayTable     arrayTable;
    /** Parent that handles any bindings we don't contain. */
    private ActionMap                               parent;


    /**
     * Creates an <code>ActionMap</code> with no parent and no mappings.
     * <p>
     *  创建一个不带父级和无映射的<code> ActionMap </code>。
     * 
     */
    public ActionMap() {
    }

    /**
     * Sets this <code>ActionMap</code>'s parent.
     *
     * <p>
     *  设置此<code> ActionMap </code>的父级。
     * 
     * 
     * @param map  the <code>ActionMap</code> that is the parent of this one
     */
    public void setParent(ActionMap map) {
        this.parent = map;
    }

    /**
     * Returns this <code>ActionMap</code>'s parent.
     *
     * <p>
     *  返回此<code> ActionMap </code>的父级。
     * 
     * 
     * @return the <code>ActionMap</code> that is the parent of this one,
     *         or null if this <code>ActionMap</code> has no parent
     */
    public ActionMap getParent() {
        return parent;
    }

    /**
     * Adds a binding for <code>key</code> to <code>action</code>.
     * If <code>action</code> is null, this removes the current binding
     * for <code>key</code>.
     * <p>In most instances, <code>key</code> will be
     * <code>action.getValue(NAME)</code>.
     * <p>
     *  为<code> key </code>添加<code> action </code>的绑定。
     * 如果<code> action </code>为null,则会删除<code> key </code>的当前绑定。
     *  <p>在大多数情况下,<code>键</code>将是<code> action.getValue(NAME)</code>。
     * 
     */
    public void put(Object key, Action action) {
        if (key == null) {
            return;
        }
        if (action == null) {
            remove(key);
        }
        else {
            if (arrayTable == null) {
                arrayTable = new ArrayTable();
            }
            arrayTable.put(key, action);
        }
    }

    /**
     * Returns the binding for <code>key</code>, messaging the
     * parent <code>ActionMap</code> if the binding is not locally defined.
     * <p>
     *  返回<code> key </code>的绑定,如果绑定未在本地定义,则向父<code> ActionMap </code>发送消息。
     * 
     */
    public Action get(Object key) {
        Action value = (arrayTable == null) ? null :
                       (Action)arrayTable.get(key);

        if (value == null) {
            ActionMap    parent = getParent();

            if (parent != null) {
                return parent.get(key);
            }
        }
        return value;
    }

    /**
     * Removes the binding for <code>key</code> from this <code>ActionMap</code>.
     * <p>
     *  从此<code> ActionMap </code>中删除<code>键</code>的绑定。
     * 
     */
    public void remove(Object key) {
        if (arrayTable != null) {
            arrayTable.remove(key);
        }
    }

    /**
     * Removes all the mappings from this <code>ActionMap</code>.
     * <p>
     *  从此<code> ActionMap </code>中删除所有映射。
     * 
     */
    public void clear() {
        if (arrayTable != null) {
            arrayTable.clear();
        }
    }

    /**
     * Returns the <code>Action</code> names that are bound in this <code>ActionMap</code>.
     * <p>
     * 返回在<code> ActionMap </code>中绑定的<code> Action </code>名称。
     * 
     */
    public Object[] keys() {
        if (arrayTable == null) {
            return null;
        }
        return arrayTable.getKeys(null);
    }

    /**
     * Returns the number of bindings in this {@code ActionMap}.
     *
     * <p>
     *  返回此{@code ActionMap}中的绑定数。
     * 
     * 
     * @return the number of bindings in this {@code ActionMap}
     */
    public int size() {
        if (arrayTable == null) {
            return 0;
        }
        return arrayTable.size();
    }

    /**
     * Returns an array of the keys defined in this <code>ActionMap</code> and
     * its parent. This method differs from <code>keys()</code> in that
     * this method includes the keys defined in the parent.
     * <p>
     *  返回在此<code> ActionMap </code>及其父代中定义的键的数组。此方法不同于<code> keys()</code>,因为此方法包括在父代中定义的键。
     */
    public Object[] allKeys() {
        int           count = size();
        ActionMap     parent = getParent();

        if (count == 0) {
            if (parent != null) {
                return parent.allKeys();
            }
            return keys();
        }
        if (parent == null) {
            return keys();
        }
        Object[]    keys = keys();
        Object[]    pKeys =  parent.allKeys();

        if (pKeys == null) {
            return keys;
        }
        if (keys == null) {
            // Should only happen if size() != keys.length, which should only
            // happen if mutated from multiple threads (or a bogus subclass).
            return pKeys;
        }

        HashMap<Object, Object> keyMap = new HashMap<Object, Object>();
        int            counter;

        for (counter = keys.length - 1; counter >= 0; counter--) {
            keyMap.put(keys[counter], keys[counter]);
        }
        for (counter = pKeys.length - 1; counter >= 0; counter--) {
            keyMap.put(pKeys[counter], pKeys[counter]);
        }
        return keyMap.keySet().toArray();
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();

        ArrayTable.writeArrayTable(s, arrayTable);
    }

    private void readObject(ObjectInputStream s) throws ClassNotFoundException,
                                                 IOException {
        s.defaultReadObject();
        for (int counter = s.readInt() - 1; counter >= 0; counter--) {
            put(s.readObject(), (Action)s.readObject());
        }
    }
}
