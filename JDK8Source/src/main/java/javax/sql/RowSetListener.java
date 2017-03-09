/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2001, Oracle and/or its affiliates. All rights reserved.
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

package javax.sql;

/**
 * An interface that must be implemented by a
 * component that wants to be notified when a significant
 * event happens in the life of a <code>RowSet</code> object.
 * A component becomes a listener by being registered with a
 * <code>RowSet</code> object via the method <code>RowSet.addRowSetListener</code>.
 * How a registered component implements this interface determines what it does
 * when it is notified of an event.
 *
 * <p>
 *  一个接口,必须由一个组件实现,当一个重要的事件发生在一个<code> RowSet </code>对象的生命周期中,它需要被通知。
 * 一个组件通过使用<code> RowSet.addRowSetListener </code>方法注册到一个<code> RowSet </code>对象中,成为一个监听器。
 * 注册组件如何实现此接口,确定通知事件时它执行的操作。
 * 
 * 
 * @since 1.4
 */

public interface RowSetListener extends java.util.EventListener {

  /**
   * Notifies registered listeners that a <code>RowSet</code> object
   * in the given <code>RowSetEvent</code> object has changed its entire contents.
   * <P>
   * The source of the event can be retrieved with the method
   * <code>event.getSource</code>.
   *
   * <p>
   *  通知注册的侦听器给定的<code> RowSetEvent </code>对象中的<code> RowSet </code>对象已更改其整个内容。
   * <P>
   *  可以使用方法<code> event.getSource </code>检索事件的来源。
   * 
   * 
   * @param event a <code>RowSetEvent</code> object that contains
   *         the <code>RowSet</code> object that is the source of the event
   */
  void rowSetChanged(RowSetEvent event);

  /**
   * Notifies registered listeners that a <code>RowSet</code> object
   * has had a change in one of its rows.
   * <P>
   * The source of the event can be retrieved with the method
   * <code>event.getSource</code>.
   *
   * <p>
   *  通知注册的侦听器<code> RowSet </code>对象在其中一行中有更改。
   * <P>
   *  可以使用方法<code> event.getSource </code>检索事件的来源。
   * 
   * 
   * @param event a <code>RowSetEvent</code> object that contains
   *         the <code>RowSet</code> object that is the source of the event
   */
  void rowChanged(RowSetEvent event);

  /**
   * Notifies registered listeners that a <code>RowSet</code> object's
   * cursor has moved.
   * <P>
   * The source of the event can be retrieved with the method
   * <code>event.getSource</code>.
   *
   * <p>
   *  通知注册的侦听器<code> RowSet </code>对象的游标已移动。
   * <P>
   * 
   * @param event a <code>RowSetEvent</code> object that contains
   *         the <code>RowSet</code> object that is the source of the event
   */
  void cursorMoved(RowSetEvent event);
}
