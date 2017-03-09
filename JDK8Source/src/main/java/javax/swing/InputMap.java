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
 * <code>InputMap</code> provides a binding between an input event
 * (currently only <code>KeyStroke</code>s are used)
 * and an <code>Object</code>. <code>InputMap</code>s
 * are usually used with an <code>ActionMap</code>,
 * to determine an <code>Action</code> to perform
 * when a key is pressed.
 * An <code>InputMap</code> can have a parent
 * that is searched for bindings not defined in the <code>InputMap</code>.
 * <p>As with <code>ActionMap</code> if you create a cycle, eg:
 * <pre>
 *   InputMap am = new InputMap();
 *   InputMap bm = new InputMap():
 *   am.setParent(bm);
 *   bm.setParent(am);
 * </pre>
 * some of the methods will cause a StackOverflowError to be thrown.
 *
 * <p>
 *  <code> InputMap </code>提供了输入事件(当前只使用<code> KeyStroke </code>)和<code> Object </code>之间的绑定。
 *  <code> InputMap </code>通常与<code> ActionMap </code>一起使用,以确定在按下键时执行的<code> Action </code>。
 *  <code> InputMap </code>可以有一个父对象,用于搜索未在<code> InputMap </code>中定义的绑定。
 *  <p>与<code> ActionMap </code>一样,如果创建一个循环,例如：。
 * <pre>
 *  InputMap am = new InputMap(); InputMap bm = new InputMap()：am.setParent(bm); bm.setParent(am);
 * </pre>
 *  一些方法将导致StackOverflowError被抛出。
 * 
 * 
 * @author Scott Violet
 * @since 1.3
 */
@SuppressWarnings("serial")
public class InputMap implements Serializable {
    /** Handles the mapping between KeyStroke and Action name. */
    private transient ArrayTable     arrayTable;
    /** Parent that handles any bindings we don't contain. */
    private InputMap                                parent;


    /**
     * Creates an <code>InputMap</code> with no parent and no mappings.
     * <p>
     *  创建一个不带父级和无映射的<code> InputMap </code>。
     * 
     */
    public InputMap() {
    }

    /**
     * Sets this <code>InputMap</code>'s parent.
     *
     * <p>
     *  设置此<code> InputMap </code>的父级。
     * 
     * 
     * @param map  the <code>InputMap</code> that is the parent of this one
     */
    public void setParent(InputMap map) {
        this.parent = map;
    }

    /**
     * Gets this <code>InputMap</code>'s parent.
     *
     * <p>
     *  获取此<code> InputMap </code>的父级。
     * 
     * 
     * @return map  the <code>InputMap</code> that is the parent of this one,
     *              or null if this <code>InputMap</code> has no parent
     */
    public InputMap getParent() {
        return parent;
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
        if (keyStroke == null) {
            return;
        }
        if (actionMapKey == null) {
            remove(keyStroke);
        }
        else {
            if (arrayTable == null) {
                arrayTable = new ArrayTable();
            }
            arrayTable.put(keyStroke, actionMapKey);
        }
    }

    /**
     * Returns the binding for <code>keyStroke</code>, messaging the
     * parent <code>InputMap</code> if the binding is not locally defined.
     * <p>
     *  返回<code> keyStroke </code>的绑定,如果绑定未在本地定义,则传递父<code> InputMap </code>。
     * 
     */
    public Object get(KeyStroke keyStroke) {
        if (arrayTable == null) {
            InputMap    parent = getParent();

            if (parent != null) {
                return parent.get(keyStroke);
            }
            return null;
        }
        Object value = arrayTable.get(keyStroke);

        if (value == null) {
            InputMap    parent = getParent();

            if (parent != null) {
                return parent.get(keyStroke);
            }
        }
        return value;
    }

    /**
     * Removes the binding for <code>key</code> from this
     * <code>InputMap</code>.
     * <p>
     *  从<code> InputMap </code>中删除<code>键</code>的绑定。
     * 
     */
    public void remove(KeyStroke key) {
        if (arrayTable != null) {
            arrayTable.remove(key);
        }
    }

    /**
     * Removes all the mappings from this <code>InputMap</code>.
     * <p>
     *  从此<code> InputMap </code>中删除所有映射。
     * 
     */
    public void clear() {
        if (arrayTable != null) {
            arrayTable.clear();
        }
    }

    /**
     * Returns the <code>KeyStroke</code>s that are bound in this <code>InputMap</code>.
     * <p>
     *  返回绑定在此<code> InputMap </code>中的<code> KeyStroke </code>。
     * 
     */
    public KeyStroke[] keys() {
        if (arrayTable == null) {
            return null;
        }
        KeyStroke[] keys = new KeyStroke[arrayTable.size()];
        arrayTable.getKeys(keys);
        return keys;
    }

    /**
     * Returns the number of <code>KeyStroke</code> bindings.
     * <p>
     *  返回<code> KeyStroke </code>绑定的数量。
     * 
     */
    public int size() {
        if (arrayTable == null) {
            return 0;
        }
        return arrayTable.size();
    }

    /**
     * Returns an array of the <code>KeyStroke</code>s defined in this
     * <code>InputMap</code> and its parent. This differs from <code>keys()</code> in that
     * this method includes the keys defined in the parent.
     * <p>
     * 返回在此<code> InputMap </code>及其父代中定义的<code> KeyStroke </code>数组。
     * 这不同于<code> keys()</code>,因为此方法包括在父级中定义的键。
     */
    public KeyStroke[] allKeys() {
        int             count = size();
        InputMap        parent = getParent();

        if (count == 0) {
            if (parent != null) {
                return parent.allKeys();
            }
            return keys();
        }
        if (parent == null) {
            return keys();
        }
        KeyStroke[]    keys = keys();
        KeyStroke[]    pKeys =  parent.allKeys();

        if (pKeys == null) {
            return keys;
        }
        if (keys == null) {
            // Should only happen if size() != keys.length, which should only
            // happen if mutated from multiple threads (or a bogus subclass).
            return pKeys;
        }

        HashMap<KeyStroke, KeyStroke> keyMap = new HashMap<KeyStroke, KeyStroke>();
        int            counter;

        for (counter = keys.length - 1; counter >= 0; counter--) {
            keyMap.put(keys[counter], keys[counter]);
        }
        for (counter = pKeys.length - 1; counter >= 0; counter--) {
            keyMap.put(pKeys[counter], pKeys[counter]);
        }

        KeyStroke[]    allKeys = new KeyStroke[keyMap.size()];

        return keyMap.keySet().toArray(allKeys);
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();

        ArrayTable.writeArrayTable(s, arrayTable);
    }

    private void readObject(ObjectInputStream s) throws ClassNotFoundException,
                                                 IOException {
        s.defaultReadObject();
        for (int counter = s.readInt() - 1; counter >= 0; counter--) {
            put((KeyStroke)s.readObject(), s.readObject());
        }
    }
}
