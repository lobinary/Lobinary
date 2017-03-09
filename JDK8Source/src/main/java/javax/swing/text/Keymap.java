/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.text;

import javax.swing.Action;
import javax.swing.KeyStroke;

/**
 * A collection of bindings of KeyStrokes to actions.  The
 * bindings are basically name-value pairs that potentially
 * resolve in a hierarchy.
 *
 * <p>
 *  KeyStrokes对操作的绑定集合。绑定基本上是可能在层次结构中解析的名称/值对。
 * 
 * 
 * @author  Timothy Prinzing
 */
public interface Keymap {

    /**
     * Fetches the name of the set of key-bindings.
     *
     * <p>
     *  获取键绑定集的名称。
     * 
     * 
     * @return the name
     */
    public String getName();

    /**
     * Fetches the default action to fire if a
     * key is typed (i.e. a KEY_TYPED KeyEvent is received)
     * and there is no binding for it.  Typically this
     * would be some action that inserts text so that
     * the keymap doesn't require an action for each
     * possible key.
     *
     * <p>
     *  如果键入键(即接收到KEY_TYPED KeyEvent)并且没有绑定,则提取默认操作来触发。通常,这将是一些插入文本的操作,以便键映射不需要为每个可能的键执行操作。
     * 
     * 
     * @return the default action
     */
    public Action getDefaultAction();

    /**
     * Set the default action to fire if a key is typed.
     *
     * <p>
     *  如果键入键,请将默认操作设置为触发。
     * 
     * 
     * @param a the action
     */
    public void setDefaultAction(Action a);

    /**
     * Fetches the action appropriate for the given symbolic
     * event sequence.  This is used by JTextController to
     * determine how to interpret key sequences.  If the
     * binding is not resolved locally, an attempt is made
     * to resolve through the parent keymap, if one is set.
     *
     * <p>
     *  获取适用于给定符号事件序列的操作。 JTextController使用它来确定如何解释键序列。如果绑定未在本地解析,则尝试通过父键映射解析(如果设置了一个键映射)。
     * 
     * 
     * @param key the key sequence
     * @return  the action associated with the key
     *  sequence if one is defined, otherwise <code>null</code>
     */
    public Action getAction(KeyStroke key);

    /**
     * Fetches all of the keystrokes in this map that
     * are bound to some action.
     *
     * <p>
     *  获取此地图中绑定到某些操作的所有击键。
     * 
     * 
     * @return the list of keystrokes
     */
    public KeyStroke[] getBoundKeyStrokes();

    /**
     * Fetches all of the actions defined in this keymap.
     *
     * <p>
     *  获取此键映射中定义的所有操作。
     * 
     * 
     * @return the list of actions
     */
    public Action[] getBoundActions();

    /**
     * Fetches the keystrokes that will result in
     * the given action.
     *
     * <p>
     *  获取将导致给定操作的键击。
     * 
     * 
     * @param a the action
     * @return the list of keystrokes
     */
    public KeyStroke[] getKeyStrokesForAction(Action a);

    /**
     * Determines if the given key sequence is locally defined.
     *
     * <p>
     *  确定给定的键序列是否在本地定义。
     * 
     * 
     * @param key the key sequence
     * @return true if the key sequence is locally defined else false
     */
    public boolean isLocallyDefined(KeyStroke key);

    /**
     * Adds a binding to the keymap.
     *
     * <p>
     *  向键盘映射添加绑定。
     * 
     * 
     * @param key the key sequence
     * @param a the action
     */
    public void addActionForKeyStroke(KeyStroke key, Action a);

    /**
     * Removes a binding from the keymap.
     *
     * <p>
     *  从键映射中删除绑定。
     * 
     * 
     * @param keys the key sequence
     */
    public void removeKeyStrokeBinding(KeyStroke keys);

    /**
     * Removes all bindings from the keymap.
     * <p>
     *  从键映射中删除所有绑定。
     * 
     */
    public void removeBindings();

    /**
     * Fetches the parent keymap used to resolve key-bindings.
     *
     * <p>
     *  获取用于解析键绑定的父键映射。
     * 
     * 
     * @return the keymap
     */
    public Keymap getResolveParent();

    /**
     * Sets the parent keymap, which will be used to
     * resolve key-bindings.
     * The behavior is unspecified if a {@code Keymap} has itself
     * as one of its resolve parents.
     *
     * <p>
     *  设置父键映射,将用于解析键绑定。如果{@code Keymap}本身是其解析父对象之一,则该行为是未指定的。
     * 
     * @param parent the parent keymap
     */
    public void setResolveParent(Keymap parent);

}
