/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2006, Oracle and/or its affiliates. All rights reserved.
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
 * An <code>Event</code> object generated when an event occurs to a
 * <code>RowSet</code> object.  A <code>RowSetEvent</code> object is
 * generated when a single row in a rowset is changed, the whole rowset
 * is changed, or the rowset cursor moves.
 * <P>
 * When an event occurs on a <code>RowSet</code> object, one of the
 * <code>RowSetListener</code> methods will be sent to all registered
 * listeners to notify them of the event.  An <code>Event</code> object
 * is supplied to the <code>RowSetListener</code> method so that the
 * listener can use it to find out which <code>RowSet</code> object is
 * the source of the event.
 *
 * <p>
 *  当事件发生到<code> RowSet </code>对象时生成的<code> Event </code>对象。
 * 当行集中的单个行发生更改,整个行集更改或行集游标移动时,会生成<code> RowSetEvent </code>对象。
 * <P>
 *  当在<code> RowSet </code>对象上发生事件时,<code> RowSetListener </code>方法之一将被发送到所有注册的侦听器,以通知它们事件。
 * 向<code> RowSetListener </code>方法提供<code> Event </code>对象,以便侦听器可以使用它来找出哪个<code> RowSet </code>对象是事件的来源
 * 。
 *  当在<code> RowSet </code>对象上发生事件时,<code> RowSetListener </code>方法之一将被发送到所有注册的侦听器,以通知它们事件。
 * 
 * @since 1.4
 */

public class RowSetEvent extends java.util.EventObject {

  /**
   * Constructs a <code>RowSetEvent</code> object initialized with the
   * given <code>RowSet</code> object.
   *
   * <p>
   * 
   * 
   * @param source the <code>RowSet</code> object whose data has changed or
   *        whose cursor has moved
   * @throws IllegalArgumentException if <code>source</code> is null.
   */
  public RowSetEvent(RowSet source)
    { super(source); }

  /**
   * Private serial version unique ID to ensure serialization
   * compatibility.
   * <p>
   *  构造使用给定的<code> RowSet </code>对象初始化的<code> RowSetEvent </code>对象。
   * 
   */
  static final long serialVersionUID = -1875450876546332005L;
}
