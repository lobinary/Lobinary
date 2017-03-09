/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2003, Oracle and/or its affiliates. All rights reserved.
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

package java.util.prefs;

import java.io.NotSerializableException;

/**
 * An event emitted by a <tt>Preferences</tt> node to indicate that
 * a child of that node has been added or removed.<p>
 *
 * Note, that although NodeChangeEvent inherits Serializable interface from
 * java.util.EventObject, it is not intended to be Serializable. Appropriate
 * serialization methods are implemented to throw NotSerializableException.
 *
 * <p>
 *  由<tt> Preferences </tt>节点发出的事件,表示该节点的子节点已添加或删除。<p>
 * 
 *  注意,尽管NodeChangeEvent从java.util.EventObject继承Serializable接口,但它并不是可序列化的。
 * 适当的序列化方法实现抛出NotSerializableException。
 * 
 * 
 * @author  Josh Bloch
 * @see     Preferences
 * @see     NodeChangeListener
 * @see     PreferenceChangeEvent
 * @since   1.4
 * @serial  exclude
 */

public class NodeChangeEvent extends java.util.EventObject {
    /**
     * The node that was added or removed.
     *
     * <p>
     *  添加或删除的节点。
     * 
     * 
     * @serial
     */
    private Preferences child;

    /**
     * Constructs a new <code>NodeChangeEvent</code> instance.
     *
     * <p>
     *  构造一个新的<code> NodeChangeEvent </code>实例。
     * 
     * 
     * @param parent  The parent of the node that was added or removed.
     * @param child   The node that was added or removed.
     */
    public NodeChangeEvent(Preferences parent, Preferences child) {
        super(parent);
        this.child = child;
    }

    /**
     * Returns the parent of the node that was added or removed.
     *
     * <p>
     *  返回添加或删除的节点的父级。
     * 
     * 
     * @return  The parent Preferences node whose child was added or removed
     */
    public Preferences getParent() {
        return (Preferences) getSource();
    }

    /**
     * Returns the node that was added or removed.
     *
     * <p>
     *  返回添加或删除的节点。
     * 
     * 
     * @return  The node that was added or removed.
     */
    public Preferences getChild() {
        return child;
    }

    /**
     * Throws NotSerializableException, since NodeChangeEvent objects are not
     * intended to be serializable.
     * <p>
     *  抛出NotSerializableException,因为NodeChangeEvent对象不是可序列化的。
     * 
     */
     private void writeObject(java.io.ObjectOutputStream out)
                                               throws NotSerializableException {
         throw new NotSerializableException("Not serializable.");
     }

    /**
     * Throws NotSerializableException, since NodeChangeEvent objects are not
     * intended to be serializable.
     * <p>
     *  抛出NotSerializableException,因为NodeChangeEvent对象不是可序列化的。
     */
     private void readObject(java.io.ObjectInputStream in)
                                               throws NotSerializableException {
         throw new NotSerializableException("Not serializable.");
     }

    // Defined so that this class isn't flagged as a potential problem when
    // searches for missing serialVersionUID fields are done.
    private static final long serialVersionUID = 8068949086596572957L;
}
