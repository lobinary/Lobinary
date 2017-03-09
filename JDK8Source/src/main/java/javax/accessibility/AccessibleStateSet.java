/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2004, Oracle and/or its affiliates. All rights reserved.
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

import java.util.Vector;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Class AccessibleStateSet determines a component's state set.  The state set
 * of a component is a set of AccessibleState objects and descriptions. E.G., The
 * current overall state of the object, such as whether it is enabled,
 * has focus, etc.
 *
 * <p>
 *  AccessibleStateSet类确定组件的状态集。组件的状态集是一组AccessibleState对象和描述。 E.G.,对象的当前总体状态,例如它是否被启用,具有焦点等。
 * 
 * 
 * @see AccessibleState
 *
 * @author      Willie Walker
 */
public class AccessibleStateSet {

    /**
     * Each entry in the Vector represents an AccessibleState.
     * <p>
     *  向量中的每个条目表示一个AccessibleState。
     * 
     * 
     * @see #add
     * @see #addAll
     * @see #remove
     * @see #contains
     * @see #toArray
     * @see #clear
     */
    protected Vector<AccessibleState> states = null;

    /**
     * Creates a new empty state set.
     * <p>
     *  创建新的空状态集。
     * 
     */
    public AccessibleStateSet() {
        states = null;
    }

    /**
     * Creates a new state with the initial set of states contained in
     * the array of states passed in.  Duplicate entries are ignored.
     *
     * <p>
     *  使用传入的状态数组中包含的初始状态集创建新状态。重复条目将被忽略。
     * 
     * 
     * @param states an array of AccessibleState describing the state set.
     */
    public AccessibleStateSet(AccessibleState[] states) {
        if (states.length != 0) {
            this.states = new Vector(states.length);
            for (int i = 0; i < states.length; i++) {
                if (!this.states.contains(states[i])) {
                    this.states.addElement(states[i]);
                }
            }
        }
    }

    /**
     * Adds a new state to the current state set if it is not already
     * present.  If the state is already in the state set, the state
     * set is unchanged and the return value is false.  Otherwise,
     * the state is added to the state set and the return value is
     * true.
     * <p>
     *  如果当前状态集尚未存在,则将新状态添加到当前状态集。如果状态已经处于设置的状态,则状态集不变,返回值为false。否则,状态将添加到状态集,返回值为true。
     * 
     * 
     * @param state the state to add to the state set
     * @return true if state is added to the state set; false if the state set
     * is unchanged
     */
    public boolean add(AccessibleState state) {
        // [[[ PENDING:  WDW - the implementation of this does not need
        // to always use a vector of states.  It could be improved by
        // caching the states as a bit set.]]]
        if (states == null) {
            states = new Vector();
        }

        if (!states.contains(state)) {
            states.addElement(state);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds all of the states to the existing state set.  Duplicate entries
     * are ignored.
     * <p>
     *  将所有状态添加到现有状态集。重复的条目将被忽略。
     * 
     * 
     * @param states  AccessibleState array describing the state set.
     */
    public void addAll(AccessibleState[] states) {
        if (states.length != 0) {
            if (this.states == null) {
                this.states = new Vector(states.length);
            }
            for (int i = 0; i < states.length; i++) {
                if (!this.states.contains(states[i])) {
                    this.states.addElement(states[i]);
                }
            }
        }
    }

    /**
     * Removes a state from the current state set.  If the state is not
     * in the set, the state set will be unchanged and the return value
     * will be false.  If the state is in the state set, it will be removed
     * from the set and the return value will be true.
     *
     * <p>
     *  从当前状态集中删除状态。如果状态不在集合中,则状态集将保持不变,返回值为false。如果状态处于设置的状态,它将从集合中移除,返回值将为true。
     * 
     * 
     * @param state the state to remove from the state set
     * @return true if the state is in the state set; false if the state set
     * will be unchanged
     */
    public boolean remove(AccessibleState state) {
        if (states == null) {
            return false;
        } else {
            return states.removeElement(state);
        }
    }

    /**
     * Removes all the states from the current state set.
     * <p>
     *  从当前状态集中删除所有状态。
     * 
     */
    public void clear() {
        if (states != null) {
            states.removeAllElements();
        }
    }

    /**
     * Checks if the current state is in the state set.
     * <p>
     *  检查当前状态是否处于设置的状态。
     * 
     * 
     * @param state the state
     * @return true if the state is in the state set; otherwise false
     */
    public boolean contains(AccessibleState state) {
        if (states == null) {
            return false;
        } else {
            return states.contains(state);
        }
    }

    /**
     * Returns the current state set as an array of AccessibleState
     * <p>
     *  返回设置为AccessibleState数组的当前状态
     * 
     * 
     * @return AccessibleState array containing the current state.
     */
    public AccessibleState[] toArray() {
        if (states == null) {
            return new AccessibleState[0];
        } else {
            AccessibleState[] stateArray = new AccessibleState[states.size()];
            for (int i = 0; i < stateArray.length; i++) {
                stateArray[i] = (AccessibleState) states.elementAt(i);
            }
            return stateArray;
        }
    }

    /**
     * Creates a localized String representing all the states in the set
     * using the default locale.
     *
     * <p>
     *  使用默认语言环境创建表示集合中所有状态的本地化字符串。
     * 
     * @return comma separated localized String
     * @see AccessibleBundle#toDisplayString
     */
    public String toString() {
        String ret = null;
        if ((states != null) && (states.size() > 0)) {
            ret = ((AccessibleState) (states.elementAt(0))).toDisplayString();
            for (int i = 1; i < states.size(); i++) {
                ret = ret + ","
                        + ((AccessibleState) (states.elementAt(i))).
                                              toDisplayString();
            }
        }
        return ret;
    }
}
