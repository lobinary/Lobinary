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
 * $Id: IteratorPool.java,v 1.2.4.1 2005/09/14 19:45:19 jeffsuttor Exp $
 * <p>
 *  $ Id：IteratorPool.java,v 1.2.4.1 2005/09/14 19:45:19 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal.axes;

import java.util.ArrayList;

import com.sun.org.apache.xml.internal.dtm.DTMIterator;
import com.sun.org.apache.xml.internal.utils.WrappedRuntimeException;

/**
 * Pool of object of a given type to pick from to help memory usage
 * @xsl.usage internal
 * <p>
 *  给定类型的对象的池从中选择以帮助内存使用@ xsl.usage内部
 * 
 */
public final class IteratorPool implements java.io.Serializable
{
    static final long serialVersionUID = -460927331149566998L;

  /**
   * Type of objects in this pool.
   * <p>
   *  此池中的对象类型。
   * 
   */
  private final DTMIterator m_orig;

  /**
   * Stack of given objects this points to.
   * <p>
   *  这个指向的给定对象的堆栈。
   * 
   */
  private final ArrayList m_freeStack;

  /**
   * Constructor IteratorPool
   *
   * <p>
   *  构造函数IteratorPool
   * 
   * 
   * @param original The original iterator from which all others will be cloned.
   */
  public IteratorPool(DTMIterator original)
  {
    m_orig = original;
    m_freeStack = new ArrayList();
  }

  /**
   * Get an instance of the given object in this pool
   *
   * <p>
   *  获取此池中给定对象的实例
   * 
   * 
   * @return An instance of the given object
   */
  public synchronized DTMIterator getInstanceOrThrow()
    throws CloneNotSupportedException
  {
    // Check if the pool is empty.
    if (m_freeStack.isEmpty())
    {

      // Create a new object if so.
      return (DTMIterator)m_orig.clone();
    }
    else
    {
      // Remove object from end of free pool.
      DTMIterator result = (DTMIterator)m_freeStack.remove(m_freeStack.size() - 1);
      return result;
    }
  }

  /**
   * Get an instance of the given object in this pool
   *
   * <p>
   *  获取此池中给定对象的实例
   * 
   * 
   * @return An instance of the given object
   */
  public synchronized DTMIterator getInstance()
  {
    // Check if the pool is empty.
    if (m_freeStack.isEmpty())
    {

      // Create a new object if so.
      try
      {
        return (DTMIterator)m_orig.clone();
      }
      catch (Exception ex)
      {
        throw new WrappedRuntimeException(ex);
      }
    }
    else
    {
      // Remove object from end of free pool.
      DTMIterator result = (DTMIterator)m_freeStack.remove(m_freeStack.size() - 1);
      return result;
    }
  }

  /**
   * Add an instance of the given object to the pool
   *
   *
   * <p>
   *  将给定对象的实例添加到池中
   * 
   * @param obj Object to add.
   */
  public synchronized void freeInstance(DTMIterator obj)
  {
    m_freeStack.add(obj);
  }
}
