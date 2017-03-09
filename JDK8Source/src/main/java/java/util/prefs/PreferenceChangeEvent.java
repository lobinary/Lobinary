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
 * a preference has been added, removed or has had its value changed.<p>
 *
 * Note, that although PreferenceChangeEvent inherits Serializable interface
 * from EventObject, it is not intended to be Serializable. Appropriate
 * serialization methods are implemented to throw NotSerializableException.
 *
 * <p>
 *  由<tt>偏好设置</tt>节点发出的事件,表示已添加,删除或已更改其值。<p>
 * 
 *  注意,尽管PreferenceChangeEvent从EventObject继承Serializable接口,但它并不是可序列化的。
 * 适当的序列化方法实现抛出NotSerializableException。
 * 
 * 
 * @author  Josh Bloch
 * @see Preferences
 * @see PreferenceChangeListener
 * @see NodeChangeEvent
 * @since   1.4
 * @serial exclude
 */
public class PreferenceChangeEvent extends java.util.EventObject {

    /**
     * Key of the preference that changed.
     *
     * <p>
     *  更改的首选项的键。
     * 
     * 
     * @serial
     */
    private String key;

    /**
     * New value for preference, or <tt>null</tt> if it was removed.
     *
     * <p>
     *  新偏好设定值,或<tt> null </tt>(如果已移除)。
     * 
     * 
     * @serial
     */
    private String newValue;

    /**
     * Constructs a new <code>PreferenceChangeEvent</code> instance.
     *
     * <p>
     *  构造一个新的<code> PreferenceChangeEvent </code>实例。
     * 
     * 
     * @param node  The Preferences node that emitted the event.
     * @param key  The key of the preference that was changed.
     * @param newValue  The new value of the preference, or <tt>null</tt>
     *                  if the preference is being removed.
     */
    public PreferenceChangeEvent(Preferences node, String key,
                                 String newValue) {
        super(node);
        this.key = key;
        this.newValue = newValue;
    }

    /**
     * Returns the preference node that emitted the event.
     *
     * <p>
     *  返回发出事件的首选项节点。
     * 
     * 
     * @return  The preference node that emitted the event.
     */
    public Preferences getNode() {
        return (Preferences) getSource();
    }

    /**
     * Returns the key of the preference that was changed.
     *
     * <p>
     *  返回已更改的首选项的键。
     * 
     * 
     * @return  The key of the preference that was changed.
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the new value for the preference.
     *
     * <p>
     *  返回首选项的新值。
     * 
     * 
     * @return  The new value for the preference, or <tt>null</tt> if the
     *          preference was removed.
     */
    public String getNewValue() {
        return newValue;
    }

    /**
     * Throws NotSerializableException, since NodeChangeEvent objects
     * are not intended to be serializable.
     * <p>
     *  抛出NotSerializableException,因为NodeChangeEvent对象不是可序列化的。
     * 
     */
     private void writeObject(java.io.ObjectOutputStream out)
                                               throws NotSerializableException {
         throw new NotSerializableException("Not serializable.");
     }

    /**
     * Throws NotSerializableException, since PreferenceChangeEvent objects
     * are not intended to be serializable.
     * <p>
     *  抛出NotSerializableException,因为PreferenceChangeEvent对象不是可序列化的。
     */
     private void readObject(java.io.ObjectInputStream in)
                                               throws NotSerializableException {
         throw new NotSerializableException("Not serializable.");
     }

    // Defined so that this class isn't flagged as a potential problem when
    // searches for missing serialVersionUID fields are done.
    private static final long serialVersionUID = 793724513368024975L;
}
