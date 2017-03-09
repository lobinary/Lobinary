/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1994, 2005, Oracle and/or its affiliates. All rights reserved.
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
 * An object that implements the Enumeration interface generates a
 * series of elements, one at a time. Successive calls to the
 * <code>nextElement</code> method return successive elements of the
 * series.
 * <p>
 * For example, to print all elements of a <tt>Vector&lt;E&gt;</tt> <i>v</i>:
 * <pre>
 *   for (Enumeration&lt;E&gt; e = v.elements(); e.hasMoreElements();)
 *       System.out.println(e.nextElement());</pre>
 * <p>
 * Methods are provided to enumerate through the elements of a
 * vector, the keys of a hashtable, and the values in a hashtable.
 * Enumerations are also used to specify the input streams to a
 * <code>SequenceInputStream</code>.
 * <p>
 * NOTE: The functionality of this interface is duplicated by the Iterator
 * interface.  In addition, Iterator adds an optional remove operation, and
 * has shorter method names.  New implementations should consider using
 * Iterator in preference to Enumeration.
 *
 * <p>
 *  实现枚举接口的对象会生成一系列元素,一次一个。对<code> nextElement </code>方法的连续调用返回该系列的连续元素。
 * <p>
 *  例如,要打印<tt>向量&lt; E&gt; </tt> <i> v </i>的所有元素：
 * <pre>
 *  for(Enumeration E = v.elements(); e.hasMoreElements();)System.out.println(e.nextElement()); </pre>
 * <p>
 *  提供了通过向量的元素,散列表的键以及散列表中的值来枚举的方法。枚举也用于指定到<code> SequenceInputStream </code>的输入流。
 * <p>
 * 
 * @see     java.util.Iterator
 * @see     java.io.SequenceInputStream
 * @see     java.util.Enumeration#nextElement()
 * @see     java.util.Hashtable
 * @see     java.util.Hashtable#elements()
 * @see     java.util.Hashtable#keys()
 * @see     java.util.Vector
 * @see     java.util.Vector#elements()
 *
 * @author  Lee Boynton
 * @since   JDK1.0
 */
public interface Enumeration<E> {
    /**
     * Tests if this enumeration contains more elements.
     *
     * <p>
     *  注意：此接口的功能由Iterator接口复制。此外,Iterator添加了一个可选的删除操作,并且具有较短的方法名称。新的实现应该考虑使用优先于枚举的Iterator。
     * 
     * 
     * @return  <code>true</code> if and only if this enumeration object
     *           contains at least one more element to provide;
     *          <code>false</code> otherwise.
     */
    boolean hasMoreElements();

    /**
     * Returns the next element of this enumeration if this enumeration
     * object has at least one more element to provide.
     *
     * <p>
     *  测试此枚举是否包含更多元素。
     * 
     * 
     * @return     the next element of this enumeration.
     * @exception  NoSuchElementException  if no more elements exist.
     */
    E nextElement();
}
