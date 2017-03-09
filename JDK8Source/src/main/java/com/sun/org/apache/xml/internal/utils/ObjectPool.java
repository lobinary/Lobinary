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
 * $Id: ObjectPool.java,v 1.2.4.1 2005/09/15 08:15:50 suresh_emailid Exp $
 * <p>
 *  $ Id：ObjectPool.java,v 1.2.4.1 2005/09/15 08:15:50 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.utils;

import java.util.ArrayList;

import com.sun.org.apache.xml.internal.res.XMLErrorResources;
import com.sun.org.apache.xml.internal.res.XMLMessages;
import com.sun.org.apache.xalan.internal.utils.ObjectFactory;


/**
 * Pool of object of a given type to pick from to help memory usage
 * @xsl.usage internal
 * <p>
 *  给定类型的对象的池从中选择以帮助内存使用@ xsl.usage内部
 * 
 */
public class ObjectPool implements java.io.Serializable
{
    static final long serialVersionUID = -8519013691660936643L;

  /** Type of objects in this pool.
  /* <p>
  /* 
   *  @serial          */
  private final Class objectType;

  /** Stack of given objects this points to.
  /* <p>
  /* 
   *  @serial          */
  private final ArrayList freeStack;

  /**
   * Constructor ObjectPool
   *
   * <p>
   *  构造函数ObjectPool
   * 
   * 
   * @param type Type of objects for this pool
   */
  public ObjectPool(Class type)
  {
    objectType = type;
    freeStack = new ArrayList();
  }

  /**
   * Constructor ObjectPool
   *
   * <p>
   *  构造函数ObjectPool
   * 
   * 
   * @param className Fully qualified name of the type of objects for this pool.
   */
  public ObjectPool(String className)
  {
    try
    {
      objectType = ObjectFactory.findProviderClass(className, true);
    }
    catch(ClassNotFoundException cnfe)
    {
      throw new WrappedRuntimeException(cnfe);
    }
    freeStack = new ArrayList();
  }


  /**
   * Constructor ObjectPool
   *
   *
   * <p>
   *  构造函数ObjectPool
   * 
   * 
   * @param type Type of objects for this pool
   * @param size Size of vector to allocate
   */
  public ObjectPool(Class type, int size)
  {
    objectType = type;
    freeStack = new ArrayList(size);
  }

  /**
   * Constructor ObjectPool
   *
   * <p>
   *  构造函数ObjectPool
   * 
   */
  public ObjectPool()
  {
    objectType = null;
    freeStack = new ArrayList();
  }

  /**
   * Get an instance of the given object in this pool if available
   *
   *
   * <p>
   *  获取此池中给定对象的实例(如果可用)
   * 
   * 
   * @return an instance of the given object if available or null
   */
  public synchronized Object getInstanceIfFree()
  {

    // Check if the pool is empty.
    if (!freeStack.isEmpty())
    {

      // Remove object from end of free pool.
      Object result = freeStack.remove(freeStack.size() - 1);
      return result;
    }

    return null;
  }

  /**
   * Get an instance of the given object in this pool
   *
   *
   * <p>
   *  获取此池中给定对象的实例
   * 
   * 
   * @return An instance of the given object
   */
  public synchronized Object getInstance()
  {

    // Check if the pool is empty.
    if (freeStack.isEmpty())
    {

      // Create a new object if so.
      try
      {
        return objectType.newInstance();
      }
      catch (InstantiationException ex){}
      catch (IllegalAccessException ex){}

      // Throw unchecked exception for error in pool configuration.
      throw new RuntimeException(XMLMessages.createXMLMessage(XMLErrorResources.ER_EXCEPTION_CREATING_POOL, null)); //"exception creating new instance for pool");
    }
    else
    {

      // Remove object from end of free pool.
      Object result = freeStack.remove(freeStack.size() - 1);
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
  public synchronized void freeInstance(Object obj)
  {

    // Make sure the object is of the correct type.
    // Remove safety.  -sb
    // if (objectType.isInstance(obj))
    // {
    freeStack.add(obj);
    // }
    // else
    // {
    //  throw new IllegalArgumentException("argument type invalid for pool");
    // }
  }
}
