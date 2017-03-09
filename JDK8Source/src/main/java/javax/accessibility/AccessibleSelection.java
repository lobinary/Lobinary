/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 1999, Oracle and/or its affiliates. All rights reserved.
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
 * This AccessibleSelection interface
 * provides the standard mechanism for an assistive technology to determine
 * what the current selected children are, as well as modify the selection set.
 * Any object that has children that can be selected should support
 * the AccessibleSelection interface.  Applications can determine if an object supports the
 * AccessibleSelection interface by first obtaining its AccessibleContext (see
 * {@link Accessible}) and then calling the
 * {@link AccessibleContext#getAccessibleSelection} method.
 * If the return value is not null, the object supports this interface.
 *
 * <p>
 *  此AccessibleSelection接口为辅助技术提供了标准机制,以确定当前选定的子项是什么,以及修改选择集。任何具有可以选择的子对象的对象都应支持AccessibleSelection接口。
 * 应用程序可以通过首先获取其AccessibleContext(参见{@link Accessible})然后调用{@link AccessibleContext#getAccessibleSelection}
 * 方法来确定对象是否支持AccessibleSelection接口。
 *  此AccessibleSelection接口为辅助技术提供了标准机制,以确定当前选定的子项是什么,以及修改选择集。任何具有可以选择的子对象的对象都应支持AccessibleSelection接口。
 * 如果返回值不为null,则对象支持此接口。
 * 
 * 
 * @see Accessible
 * @see Accessible#getAccessibleContext
 * @see AccessibleContext
 * @see AccessibleContext#getAccessibleSelection
 *
 * @author      Peter Korn
 * @author      Hans Muller
 * @author      Willie Walker
 */
public interface AccessibleSelection {

    /**
     * Returns the number of Accessible children currently selected.
     * If no children are selected, the return value will be 0.
     *
     * <p>
     *  返回当前选定的可访问子项数。如果未选择任何子项,则返回值为0。
     * 
     * 
     * @return the number of items currently selected.
     */
     public int getAccessibleSelectionCount();

    /**
     * Returns an Accessible representing the specified selected child
     * of the object.  If there isn't a selection, or there are
     * fewer children selected than the integer passed in, the return
     * value will be null.
     * <p>Note that the index represents the i-th selected child, which
     * is different from the i-th child.
     *
     * <p>
     *  返回表示对象的指定选定子项的Accessible。如果没有选择,或者选择的子选择比传递的整数少,返回值将为null。 <p>请注意,索引表示第i个选定子项,它与第i个子项不同。
     * 
     * 
     * @param i the zero-based index of selected children
     * @return the i-th selected child
     * @see #getAccessibleSelectionCount
     */
     public Accessible getAccessibleSelection(int i);

    /**
     * Determines if the current child of this object is selected.
     *
     * <p>
     *  确定是否选择此对象的当前子项。
     * 
     * 
     * @return true if the current child of this object is selected; else false.
     * @param i the zero-based index of the child in this Accessible object.
     * @see AccessibleContext#getAccessibleChild
     */
     public boolean isAccessibleChildSelected(int i);

    /**
     * Adds the specified Accessible child of the object to the object's
     * selection.  If the object supports multiple selections,
     * the specified child is added to any existing selection, otherwise
     * it replaces any existing selection in the object.  If the
     * specified child is already selected, this method has no effect.
     *
     * <p>
     * 将对象的指定Accessible子项添加到对象的选择。如果对象支持多个选择,则将指定的子项添加到任何现有选择,否则将替换对象中的任何现有选择。如果已选择指定的子项,则此方法无效。
     * 
     * 
     * @param i the zero-based index of the child
     * @see AccessibleContext#getAccessibleChild
     */
     public void addAccessibleSelection(int i);

    /**
     * Removes the specified child of the object from the object's
     * selection.  If the specified item isn't currently selected, this
     * method has no effect.
     *
     * <p>
     *  从对象的选择中删除对象的指定子项。如果当前未选择指定的项目,则此方法无效。
     * 
     * 
     * @param i the zero-based index of the child
     * @see AccessibleContext#getAccessibleChild
     */
     public void removeAccessibleSelection(int i);

    /**
     * Clears the selection in the object, so that no children in the
     * object are selected.
     * <p>
     *  清除对象中的选择,以便不会选择对象中的任何子对象。
     * 
     */
     public void clearAccessibleSelection();

    /**
     * Causes every child of the object to be selected
     * if the object supports multiple selections.
     * <p>
     *  如果对象支持多个选择,则导致选择对象的每个子项。
     */
     public void selectAllAccessibleSelection();
}
