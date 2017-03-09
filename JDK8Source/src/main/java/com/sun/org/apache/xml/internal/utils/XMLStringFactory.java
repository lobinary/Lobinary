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
 * $Id: XMLStringFactory.java,v 1.2.4.1 2005/09/15 08:16:03 suresh_emailid Exp $
 * <p>
 *  $ Id：XMLStringFactory.java,v 1.2.4.1 2005/09/15 08:16:03 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.utils;

/**
 * A concrete class that implements this interface creates XMLString objects.
 * <p>
 *  实现此接口的具体类创建XMLString对象。
 * 
 */
public abstract class XMLStringFactory
{

  /**
   * Create a new XMLString from a Java string.
   *
   *
   * <p>
   *  从Java字符串创建一个新的XMLString。
   * 
   * 
   * @param string Java String reference, which must be non-null.
   *
   * @return An XMLString object that wraps the String reference.
   */
  public abstract XMLString newstr(String string);

  /**
   * Create a XMLString from a FastStringBuffer.
   *
   *
   * <p>
   *  从FastStringBuffer创建XMLString。
   * 
   * 
   * @param string FastStringBuffer reference, which must be non-null.
   * @param start The start position in the array.
   * @param length The number of characters to read from the array.
   *
   * @return An XMLString object that wraps the FastStringBuffer reference.
   */
  public abstract XMLString newstr(FastStringBuffer string, int start,
                                   int length);

  /**
   * Create a XMLString from a FastStringBuffer.
   *
   *
   * <p>
   *  从FastStringBuffer创建XMLString。
   * 
   * 
   * @param string FastStringBuffer reference, which must be non-null.
   * @param start The start position in the array.
   * @param length The number of characters to read from the array.
   *
   * @return An XMLString object that wraps the FastStringBuffer reference.
   */
  public abstract XMLString newstr(char[] string, int start,
                                   int length);

  /**
   * Get a cheap representation of an empty string.
   *
   * <p>
   *  获取空字符串的廉价表示。
   * 
   * @return An non-null reference to an XMLString that represents "".
   */
  public abstract XMLString emptystr();
}
