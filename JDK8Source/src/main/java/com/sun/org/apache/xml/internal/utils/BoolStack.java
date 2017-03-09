/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有1999-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: BoolStack.java,v 1.2.4.1 2005/09/15 08:15:35 suresh_emailid Exp $
 * <p>
 *  $ Id：BoolStack.java,v 1.2.4.1 2005/09/15 08:15:35 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.utils;


/**
 * Simple stack for boolean values.
 * @xsl.usage internal
 * <p>
 *  布尔值的简单堆栈。 @ xsl.usage internal
 * 
 */
public final class BoolStack implements Cloneable
{

  /** Array of boolean values          */
  private boolean m_values[];

  /** Array size allocated           */
  private int m_allocatedSize;

  /** Index into the array of booleans          */
  private int m_index;

  /**
   * Default constructor.  Note that the default
   * block size is very small, for small lists.
   * <p>
   *  默认构造函数。请注意,对于小列表,默认块大小非常小。
   * 
   */
  public BoolStack()
  {
    this(32);
  }

  /**
   * Construct a IntVector, using the given block size.
   *
   * <p>
   *  使用给定的块大小构造IntVector。
   * 
   * 
   * @param size array size to allocate
   */
  public BoolStack(int size)
  {

    m_allocatedSize = size;
    m_values = new boolean[size];
    m_index = -1;
  }

  /**
   * Get the length of the list.
   *
   * <p>
   *  获取列表的长度。
   * 
   * 
   * @return Current length of the list
   */
  public final int size()
  {
    return m_index + 1;
  }

  /**
   * Clears the stack.
   *
   * <p>
   *  清除堆栈。
   * 
   */
  public final void clear()
  {
        m_index = -1;
  }

  /**
   * Pushes an item onto the top of this stack.
   *
   *
   * <p>
   *  将项目推到此堆栈的顶部。
   * 
   * 
   * @param val the boolean to be pushed onto this stack.
   * @return  the <code>item</code> argument.
   */
  public final boolean push(boolean val)
  {

    if (m_index == m_allocatedSize - 1)
      grow();

    return (m_values[++m_index] = val);
  }

  /**
   * Removes the object at the top of this stack and returns that
   * object as the value of this function.
   *
   * <p>
   *  删除该堆栈顶部的对象,并返回该对象作为此函数的值。
   * 
   * 
   * @return     The object at the top of this stack.
   * @throws  EmptyStackException  if this stack is empty.
   */
  public final boolean pop()
  {
    return m_values[m_index--];
  }

  /**
   * Removes the object at the top of this stack and returns the
   * next object at the top as the value of this function.
   *
   *
   * <p>
   *  删除该堆栈顶部的对象,并返回顶部的下一个对象作为此函数的值。
   * 
   * 
   * @return Next object to the top or false if none there
   */
  public final boolean popAndTop()
  {

    m_index--;

    return (m_index >= 0) ? m_values[m_index] : false;
  }

  /**
   * Set the item at the top of this stack
   *
   *
   * <p>
   *  设置此堆栈顶部的项目
   * 
   * 
   * @param b Object to set at the top of this stack
   */
  public final void setTop(boolean b)
  {
    m_values[m_index] = b;
  }

  /**
   * Looks at the object at the top of this stack without removing it
   * from the stack.
   *
   * <p>
   *  查看该堆栈顶部的对象,而不从堆栈中删除它。
   * 
   * 
   * @return     the object at the top of this stack.
   * @throws  EmptyStackException  if this stack is empty.
   */
  public final boolean peek()
  {
    return m_values[m_index];
  }

  /**
   * Looks at the object at the top of this stack without removing it
   * from the stack.  If the stack is empty, it returns false.
   *
   * <p>
   * 查看该堆栈顶部的对象,而不从堆栈中删除它。如果堆栈为空,它返回false。
   * 
   * 
   * @return     the object at the top of this stack.
   */
  public final boolean peekOrFalse()
  {
    return (m_index > -1) ? m_values[m_index] : false;
  }

  /**
   * Looks at the object at the top of this stack without removing it
   * from the stack.  If the stack is empty, it returns true.
   *
   * <p>
   *  查看该堆栈顶部的对象,而不从堆栈中删除它。如果堆栈为空,它返回true。
   * 
   * 
   * @return     the object at the top of this stack.
   */
  public final boolean peekOrTrue()
  {
    return (m_index > -1) ? m_values[m_index] : true;
  }

  /**
   * Tests if this stack is empty.
   *
   * <p>
   *  测试此堆栈是否为空。
   * 
   * 
   * @return  <code>true</code> if this stack is empty;
   *          <code>false</code> otherwise.
   */
  public boolean isEmpty()
  {
    return (m_index == -1);
  }

  /**
   * Grows the size of the stack
   *
   * <p>
   *  增大堆栈的大小
   */
  private void grow()
  {

    m_allocatedSize *= 2;

    boolean newVector[] = new boolean[m_allocatedSize];

    System.arraycopy(m_values, 0, newVector, 0, m_index + 1);

    m_values = newVector;
  }

  public Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }

}
