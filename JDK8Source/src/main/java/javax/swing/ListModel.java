/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2001, Oracle and/or its affiliates. All rights reserved.
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

import javax.swing.event.ListDataListener;

/**
 * This interface defines the methods components like JList use
 * to get the value of each cell in a list and the length of the list.
 * Logically the model is a vector, indices vary from 0 to
 * ListDataModel.getSize() - 1.  Any change to the contents or
 * length of the data model must be reported to all of the
 * ListDataListeners.
 *
 * <p>
 *  此接口定义方法组件,如JList用于获取列表中每个单元格的值和列表的长度。
 * 逻辑上,模型是向量,索引从0到ListDataModel.getSize() -  1.对数据模型的内容或长度的任何更改都必须报告给所有ListDataListeners。
 * 
 * 
 * @param <E> the type of the elements of this model
 *
 * @author Hans Muller
 * @see JList
 */
public interface ListModel<E>
{
  /**
   * Returns the length of the list.
   * <p>
   *  返回列表的长度。
   * 
   * 
   * @return the length of the list
   */
  int getSize();

  /**
   * Returns the value at the specified index.
   * <p>
   *  返回指定索引处的值。
   * 
   * 
   * @param index the requested index
   * @return the value at <code>index</code>
   */
  E getElementAt(int index);

  /**
   * Adds a listener to the list that's notified each time a change
   * to the data model occurs.
   * <p>
   *  将监听器添加到每当发生对数据模型的更改时通知的列表中。
   * 
   * 
   * @param l the <code>ListDataListener</code> to be added
   */
  void addListDataListener(ListDataListener l);

  /**
   * Removes a listener from the list that's notified each time a
   * change to the data model occurs.
   * <p>
   *  从每当发生对数据模型的更改时通知的列表中删除侦听器。
   * 
   * @param l the <code>ListDataListener</code> to be removed
   */
  void removeListDataListener(ListDataListener l);
}
