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
 * $Id: IntStack.java,v 1.2.4.1 2005/09/15 08:15:45 suresh_emailid Exp $
 * <p>
 *  $ Id：IntStack.java,v 1.2.4.1 2005/09/15 08:15:45 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.utils;

import java.util.EmptyStackException;

/**
 * Implement a stack of simple integers.
 *
 * %OPT%
 * This is currently based on IntVector, which permits fast acess but pays a
 * heavy recopying penalty if/when its size is increased. If we expect deep
 * stacks, we should consider a version based on ChunkedIntVector.
 * @xsl.usage internal
 * <p>
 *  实现一堆简单的整数。
 * 
 *  ％OPT％这是目前基于IntVector,它允许快速访问,但如果/当其大小增加时支付严重的重复惩罚惩罚。如果我们期望深层栈,我们应该考虑一个基于ChunkedIntVector的版本。
 *  @ xsl.usage internal。
 * 
 */
public class IntStack extends IntVector
{

  /**
   * Default constructor.  Note that the default
   * block size is very small, for small lists.
   * <p>
   *  默认构造函数。请注意,对于小列表,默认块大小非常小。
   * 
   */
  public IntStack()
  {
    super();
  }

  /**
   * Construct a IntVector, using the given block size.
   *
   * <p>
   *  使用给定的块大小构造IntVector。
   * 
   * 
   * @param blocksize Size of block to allocate
   */
  public IntStack(int blocksize)
  {
    super(blocksize);
  }

  /**
   * Copy constructor for IntStack
   *
   * <p>
   *  复制IntStack的构造函数
   * 
   * 
   * @param v IntStack to copy
   */
  public IntStack (IntStack v)
  {
        super(v);
  }

  /**
   * Pushes an item onto the top of this stack.
   *
   * <p>
   *  将项目推到此堆栈的顶部。
   * 
   * 
   * @param   i   the int to be pushed onto this stack.
   * @return  the <code>item</code> argument.
   */
  public int push(int i)
  {

    if ((m_firstFree + 1) >= m_mapSize)
    {
      m_mapSize += m_blocksize;

      int newMap[] = new int[m_mapSize];

      System.arraycopy(m_map, 0, newMap, 0, m_firstFree + 1);

      m_map = newMap;
    }

    m_map[m_firstFree] = i;

    m_firstFree++;

    return i;
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
   */
  public final int pop()
  {
    return m_map[--m_firstFree];
  }

  /**
   * Quickly pops a number of items from the stack.
   * <p>
   *  快速从堆栈中弹出一些项目。
   * 
   */

  public final void quickPop(int n)
  {
    m_firstFree -= n;
  }

  /**
   * Looks at the object at the top of this stack without removing it
   * from the stack.
   *
   * <p>
   * 查看该堆栈顶部的对象,而不从堆栈中删除它。
   * 
   * 
   * @return     the object at the top of this stack.
   * @throws  EmptyStackException  if this stack is empty.
   */
  public final int peek()
  {
    try {
      return m_map[m_firstFree - 1];
    }
    catch (ArrayIndexOutOfBoundsException e)
    {
      throw new EmptyStackException();
    }
  }

  /**
   * Looks at the object at the position the stack counting down n items.
   *
   * <p>
   *  查看对象在堆栈倒数n个项目的位置。
   * 
   * 
   * @param n The number of items down, indexed from zero.
   * @return     the object at n items down.
   * @throws  EmptyStackException  if this stack is empty.
   */
  public int peek(int n)
  {
    try {
      return m_map[m_firstFree-(1+n)];
    }
    catch (ArrayIndexOutOfBoundsException e)
    {
      throw new EmptyStackException();
    }
  }

  /**
   * Sets an object at a the top of the statck
   *
   *
   * <p>
   *  在statck的顶部设置一个对象
   * 
   * 
   * @param val object to set at the top
   * @throws  EmptyStackException  if this stack is empty.
   */
  public void setTop(int val)
  {
    try {
      m_map[m_firstFree - 1] = val;
    }
    catch (ArrayIndexOutOfBoundsException e)
    {
      throw new EmptyStackException();
    }
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
   * @since   JDK1.0
   */
  public boolean empty()
  {
    return m_firstFree == 0;
  }

  /**
   * Returns where an object is on this stack.
   *
   * <p>
   *  返回对象在此堆栈上的位置。
   * 
   * 
   * @param   o   the desired object.
   * @return  the distance from the top of the stack where the object is]
   *          located; the return value <code>-1</code> indicates that the
   *          object is not on the stack.
   * @since   JDK1.0
   */
  public int search(int o)
  {

    int i = lastIndexOf(o);

    if (i >= 0)
    {
      return size() - i;
    }

    return -1;
  }

  /**
   * Returns clone of current IntStack
   *
   * <p>
   *  返回当前IntStack的克隆
   * 
   * @return clone of current IntStack
   */
  public Object clone()
    throws CloneNotSupportedException
  {
        return (IntStack) super.clone();
  }
}
