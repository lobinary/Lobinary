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
 * $Id: WrappedRuntimeException.java,v 1.2.4.1 2005/09/15 08:16:00 suresh_emailid Exp $
 * <p>
 *  $ Id：WrappedRuntimeException.java,v 1.2.4.1 2005/09/15 08:16:00 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.utils;

/**
 * This class is for throwing important checked exceptions
 * over non-checked methods.  It should be used with care,
 * and in limited circumstances.
 * <p>
 *  这个类用于在非检查方法上抛出重要的检查异常。它应该小心使用,并在有限的情况下使用。
 * 
 */
public class WrappedRuntimeException extends RuntimeException
{
    static final long serialVersionUID = 7140414456714658073L;

  /** Primary checked exception.
  /* <p>
  /* 
   *  @serial          */
  private Exception m_exception;

  /**
   * Construct a WrappedRuntimeException from a
   * checked exception.
   *
   * <p>
   *  从检查的异常构造WrappedRuntimeException。
   * 
   * 
   * @param e Primary checked exception
   */
  public WrappedRuntimeException(Exception e)
  {

    super(e.getMessage());

    m_exception = e;
  }

  /**
   * Constructor WrappedRuntimeException
   *
   *
   * <p>
   *  构造函数WrappedRuntimeException
   * 
   * 
   * @param msg Exception information.
   * @param e Primary checked exception
   */
  public WrappedRuntimeException(String msg, Exception e)
  {

    super(msg);

    m_exception = e;
  }

  /**
   * Get the checked exception that this runtime exception wraps.
   *
   * <p>
   *  获取此运行时异常包装的已检查异常。
   * 
   * @return The primary checked exception
   */
  public Exception getException()
  {
    return m_exception;
  }
}
