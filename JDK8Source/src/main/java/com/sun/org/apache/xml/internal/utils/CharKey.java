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
 * $Id: CharKey.java,v 1.3 2005/09/28 13:49:18 pvedula Exp $
 * <p>
 *  $ Id：CharKey.java,v 1.3 2005/09/28 13:49:18 pvedula Exp $
 * 
 */
package com.sun.org.apache.xml.internal.utils;

/**
 * Simple class for fast lookup of char values, when used with
 * hashtables.  You can set the char, then use it as a key.
 * @xsl.usage internal
 * <p>
 *  简单类,用于快速查找char值,当与哈希表一起使用时。您可以设置字符,然后将其用作键。 @ xsl.usage internal
 * 
 */
public class CharKey extends Object
{

  /** String value          */
  private char m_char;

  /**
   * Constructor CharKey
   *
   * <p>
   *  构造函数CharKey
   * 
   * 
   * @param key char value of this object.
   */
  public CharKey(char key)
  {
    m_char = key;
  }

  /**
   * Default constructor for a CharKey.
   * <p>
   *  CharKey的默认构造函数。
   * 
   */
  public CharKey()
  {
  }

  /**
   * Get the hash value of the character.
   *
   * <p>
   *  获取字符的哈希值。
   * 
   * 
   * @return hash value of the character.
   */
  public final void setChar(char c)
  {
    m_char = c;
  }



  /**
   * Get the hash value of the character.
   *
   * <p>
   *  获取字符的哈希值。
   * 
   * 
   * @return hash value of the character.
   */
  public final int hashCode()
  {
    return (int)m_char;
  }

  /**
   * Override of equals() for this object
   *
   * <p>
   *  覆盖此对象的equals()
   * 
   * @param obj to compare to
   *
   * @return True if this object equals this string value
   */
  public final boolean equals(Object obj)
  {
    return ((CharKey)obj).m_char == m_char;
  }
}
