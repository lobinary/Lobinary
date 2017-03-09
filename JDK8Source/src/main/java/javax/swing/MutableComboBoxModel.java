/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved.
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
 * A mutable version of <code>ComboBoxModel</code>.
 *
 * <p>
 *  <code> ComboBoxModel </code>的可变版本。
 * 
 * 
 * @param <E> the type of the elements of this model
 *
 * @author Tom Santos
 */

public interface MutableComboBoxModel<E> extends ComboBoxModel<E> {

    /**
     * Adds an item at the end of the model. The implementation of this method
     * should notify all registered <code>ListDataListener</code>s that the
     * item has been added.
     *
     * <p>
     *  在模型末尾添加项目。该方法的实现应该通知所有注册的<code> ListDataListener </code>,说明该项已经被添加。
     * 
     * 
     * @param item the item to be added
     */
    public void addElement( E item );

    /**
     * Removes an item from the model. The implementation of this method should
     * should notify all registered <code>ListDataListener</code>s that the
     * item has been removed.
     *
     * <p>
     *  从模型中删除项目。这个方法的实现应该通知所有注册的<code> ListDataListener </code>,项目已经被删除。
     * 
     * 
     * @param obj the <code>Object</code> to be removed
     */
    public void removeElement( Object obj );

    /**
     * Adds an item at a specific index.  The implementation of this method
     * should notify all registered <code>ListDataListener</code>s that the
     * item has been added.
     *
     * <p>
     *  在特定索引处添加项目。该方法的实现应该通知所有注册的<code> ListDataListener </code>,说明该项已经被添加。
     * 
     * 
     * @param item  the item to be added
     * @param index  location to add the object
     */
    public void insertElementAt( E item, int index );

    /**
     * Removes an item at a specific index. The implementation of this method
     * should notify all registered <code>ListDataListener</code>s that the
     * item has been removed.
     *
     * <p>
     *  删除特定索引处的项目。该方法的实现应该通知所有注册的<code> ListDataListener </code>,该项目已被删除。
     * 
     * @param index  location of the item to be removed
     */
    public void removeElementAt( int index );
}
