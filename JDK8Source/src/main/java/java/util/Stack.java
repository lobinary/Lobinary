/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1994, 2010, Oracle and/or its affiliates. All rights reserved.
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
 * The <code>Stack</code> class represents a last-in-first-out
 * (LIFO) stack of objects. It extends class <tt>Vector</tt> with five
 * operations that allow a vector to be treated as a stack. The usual
 * <tt>push</tt> and <tt>pop</tt> operations are provided, as well as a
 * method to <tt>peek</tt> at the top item on the stack, a method to test
 * for whether the stack is <tt>empty</tt>, and a method to <tt>search</tt>
 * the stack for an item and discover how far it is from the top.
 * <p>
 * When a stack is first created, it contains no items.
 *
 * <p>A more complete and consistent set of LIFO stack operations is
 * provided by the {@link Deque} interface and its implementations, which
 * should be used in preference to this class.  For example:
 * <pre>   {@code
 *   Deque<Integer> stack = new ArrayDeque<Integer>();}</pre>
 *
 * <p>
 *  <code> Stack </code>类表示对象的最后进先出(LIFO)堆栈。它通过五个操作来扩展类<tt> Vector </tt>,允许将向量视为堆栈。
 * 提供了通常的<tt> push </tt>和<tt> pop </tt>操作,以及在堆栈顶部项目<tt> peek </tt>的方法,不管堆栈是<tt>空</tt>,还是一个方法来<tt>搜索</tt>
 * 一个项目的堆栈,并发现它从顶部有多远。
 *  <code> Stack </code>类表示对象的最后进先出(LIFO)堆栈。它通过五个操作来扩展类<tt> Vector </tt>,允许将向量视为堆栈。
 * <p>
 *  首次创建堆栈时,它不包含任何项目。
 * 
 *  <p> {@link Deque}接口及其实现提供了更完整和一致的LIFO堆栈操作集,应优先使用此类。
 * 例如：<pre> {@code Deque <Integer> stack = new ArrayDeque <Integer>();} </pre>。
 * 
 * 
 * @author  Jonathan Payne
 * @since   JDK1.0
 */
public
class Stack<E> extends Vector<E> {
    /**
     * Creates an empty Stack.
     * <p>
     *  创建一个空堆栈。
     * 
     */
    public Stack() {
    }

    /**
     * Pushes an item onto the top of this stack. This has exactly
     * the same effect as:
     * <blockquote><pre>
     * addElement(item)</pre></blockquote>
     *
     * <p>
     *  将项目推到此堆栈的顶部。这具有完全相同的效果：<blockquote> <pre> addElement(item)</pre> </blockquote>
     * 
     * 
     * @param   item   the item to be pushed onto this stack.
     * @return  the <code>item</code> argument.
     * @see     java.util.Vector#addElement
     */
    public E push(E item) {
        addElement(item);

        return item;
    }

    /**
     * Removes the object at the top of this stack and returns that
     * object as the value of this function.
     *
     * <p>
     *  删除该堆栈顶部的对象,并返回该对象作为此函数的值。
     * 
     * 
     * @return  The object at the top of this stack (the last item
     *          of the <tt>Vector</tt> object).
     * @throws  EmptyStackException  if this stack is empty.
     */
    public synchronized E pop() {
        E       obj;
        int     len = size();

        obj = peek();
        removeElementAt(len - 1);

        return obj;
    }

    /**
     * Looks at the object at the top of this stack without removing it
     * from the stack.
     *
     * <p>
     *  查看该堆栈顶部的对象,而不从堆栈中删除它。
     * 
     * 
     * @return  the object at the top of this stack (the last item
     *          of the <tt>Vector</tt> object).
     * @throws  EmptyStackException  if this stack is empty.
     */
    public synchronized E peek() {
        int     len = size();

        if (len == 0)
            throw new EmptyStackException();
        return elementAt(len - 1);
    }

    /**
     * Tests if this stack is empty.
     *
     * <p>
     *  测试此堆栈是否为空。
     * 
     * 
     * @return  <code>true</code> if and only if this stack contains
     *          no items; <code>false</code> otherwise.
     */
    public boolean empty() {
        return size() == 0;
    }

    /**
     * Returns the 1-based position where an object is on this stack.
     * If the object <tt>o</tt> occurs as an item in this stack, this
     * method returns the distance from the top of the stack of the
     * occurrence nearest the top of the stack; the topmost item on the
     * stack is considered to be at distance <tt>1</tt>. The <tt>equals</tt>
     * method is used to compare <tt>o</tt> to the
     * items in this stack.
     *
     * <p>
     * 返回对象在此堆栈上的1位置。如果对象<tt> o </tt>作为该堆栈中的项目出现,则此方法返回距堆栈顶部最近的堆栈顶部的距离;堆栈上的最上面的项目被认为在距离<tt> 1 </tt>。
     *  <tt> equals </tt>方法用于将<tt> o </tt>与此堆栈中的项目进行比较。
     * 
     * @param   o   the desired object.
     * @return  the 1-based position from the top of the stack where
     *          the object is located; the return value <code>-1</code>
     *          indicates that the object is not on the stack.
     */
    public synchronized int search(Object o) {
        int i = lastIndexOf(o);

        if (i >= 0) {
            return size() - i;
        }
        return -1;
    }

    /** use serialVersionUID from JDK 1.0.2 for interoperability */
    private static final long serialVersionUID = 1224463164541339165L;
}
