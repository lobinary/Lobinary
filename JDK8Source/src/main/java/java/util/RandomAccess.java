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

package java.util;

/**
 * Marker interface used by <tt>List</tt> implementations to indicate that
 * they support fast (generally constant time) random access.  The primary
 * purpose of this interface is to allow generic algorithms to alter their
 * behavior to provide good performance when applied to either random or
 * sequential access lists.
 *
 * <p>The best algorithms for manipulating random access lists (such as
 * <tt>ArrayList</tt>) can produce quadratic behavior when applied to
 * sequential access lists (such as <tt>LinkedList</tt>).  Generic list
 * algorithms are encouraged to check whether the given list is an
 * <tt>instanceof</tt> this interface before applying an algorithm that would
 * provide poor performance if it were applied to a sequential access list,
 * and to alter their behavior if necessary to guarantee acceptable
 * performance.
 *
 * <p>It is recognized that the distinction between random and sequential
 * access is often fuzzy.  For example, some <tt>List</tt> implementations
 * provide asymptotically linear access times if they get huge, but constant
 * access times in practice.  Such a <tt>List</tt> implementation
 * should generally implement this interface.  As a rule of thumb, a
 * <tt>List</tt> implementation should implement this interface if,
 * for typical instances of the class, this loop:
 * <pre>
 *     for (int i=0, n=list.size(); i &lt; n; i++)
 *         list.get(i);
 * </pre>
 * runs faster than this loop:
 * <pre>
 *     for (Iterator i=list.iterator(); i.hasNext(); )
 *         i.next();
 * </pre>
 *
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * <p>
 *  <tt> List </tt>实现所使用的标记界面,表示它们支持快速(通常是恒定时间)的随机访问。此接口的主要目的是允许通用算法更改其行为,以在应用于随机或顺序访问列表时提供良好的性能。
 * 
 *  <p>用于操作随机访问列表(例如<tt> ArrayList </tt>)的最佳算法在应用于顺序访问列表(例如<tt> LinkedList </tt>)时可以产生二次行为。
 * 鼓励通用列表算法在应用算法之前检查给定列表是否是<tt> instance of </tt>此接口,如果应用于顺序访问列表,则提供较差的性能,并且如果必要,改变它们的行为保证可接受的性能。
 * 
 *  认识到随机和顺序访问之间的区别通常是模糊的。例如,一些<tt> List </tt>实现提供渐近线性访问时间,如果他们获得巨大的,但实际上恒定的访问时间。
 * 这样的<tt> List </tt>实现通常应该实现这个接口。根据经验,如果对于类的典型实例,这个循环,<tt> List </tt>实现应该实现此接口：。
 * <pre>
 *  for(int i = 0,n = list.size(); i <n; i ++)list.get(i);
 * </pre>
 * 
 * @since 1.4
 */
public interface RandomAccess {
}
