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
package javax.swing;

/**
 * A data model for a combo box. This interface extends <code>ListDataModel</code>
 * and adds the concept of a <i>selected item</i>. The selected item is generally
 * the item which is visible in the combo box display area.
 * <p>
 * The selected item may not necessarily be managed by the underlying
 * <code>ListModel</code>. This disjoint behavior allows for the temporary
 * storage and retrieval of a selected item in the model.
 *
 * <p>
 *  组合框的数据模型。此接口扩展<code> ListDataModel </code>并添加<i>所选项</i>的概念。所选项通常是在组合框显示区域中可见的项。
 * <p>
 *  所选项目可能不一定由底层的<code> ListModel </code>管理。这种不相交的行为允许在模型中临时存储和检索所选择的项目。
 * 
 * 
 * @param <E> the type of the elements of this model
 *
 * @author Arnaud Weber
 */
public interface ComboBoxModel<E> extends ListModel<E> {

  /**
   * Set the selected item. The implementation of this  method should notify
   * all registered <code>ListDataListener</code>s that the contents
   * have changed.
   *
   * <p>
   *  设置所选项目。该方法的实现应该通知所有注册的<code> ListDataListener </code>,内容已经改变。
   * 
   * 
   * @param anItem the list object to select or <code>null</code>
   *        to clear the selection
   */
  void setSelectedItem(Object anItem);

  /**
   * Returns the selected item
   * <p>
   *  返回所选项目
   * 
   * @return The selected item or <code>null</code> if there is no selection
   */
  Object getSelectedItem();
}
