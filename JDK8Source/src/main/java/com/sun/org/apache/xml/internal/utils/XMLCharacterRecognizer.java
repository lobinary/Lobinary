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
 * $Id: XMLCharacterRecognizer.java,v 1.2.4.1 2005/09/15 08:16:01 suresh_emailid Exp $
 * <p>
 *  $ Id：XMLCharacterRecognizer.java,v 1.2.4.1 2005/09/15 08:16:01 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.utils;

/**
 * Class used to verify whether the specified <var>ch</var>
 * conforms to the XML 1.0 definition of whitespace.
 * @xsl.usage internal
 * <p>
 *  用于验证指定的<var> ch </var>是否符合空格的XML 1.0定义的类。 @ xsl.usage internal
 * 
 */
public class XMLCharacterRecognizer
{

  /**
   * Returns whether the specified <var>ch</var> conforms to the XML 1.0 definition
   * of whitespace.  Refer to <A href="http://www.w3.org/TR/1998/REC-xml-19980210#NT-S">
   * the definition of <CODE>S</CODE></A> for details.
   * <p>
   *  返回指定的<var> ch </var>是否符合空格的XML 1.0定义。
   * 有关详细信息,请参阅<A href="http://www.w3.org/TR/1998/REC-xml-19980210#NT-S"> <CODE> S </CODE> </A>的定义。
   * 
   * 
   * @param ch Character to check as XML whitespace.
   * @return =true if <var>ch</var> is XML whitespace; otherwise =false.
   */
  public static boolean isWhiteSpace(char ch)
  {
    return (ch == 0x20) || (ch == 0x09) || (ch == 0xD) || (ch == 0xA);
  }

  /**
   * Tell if the string is whitespace.
   *
   * <p>
   *  判断字符串是否为空格。
   * 
   * 
   * @param ch Character array to check as XML whitespace.
   * @param start Start index of characters in the array
   * @param length Number of characters in the array
   * @return True if the characters in the array are
   * XML whitespace; otherwise, false.
   */
  public static boolean isWhiteSpace(char ch[], int start, int length)
  {

    int end = start + length;

    for (int s = start; s < end; s++)
    {
      if (!isWhiteSpace(ch[s]))
        return false;
    }

    return true;
  }

  /**
   * Tell if the string is whitespace.
   *
   * <p>
   *  判断字符串是否为空格。
   * 
   * 
   * @param buf StringBuffer to check as XML whitespace.
   * @return True if characters in buffer are XML whitespace, false otherwise
   */
  public static boolean isWhiteSpace(StringBuffer buf)
  {

    int n = buf.length();

    for (int i = 0; i < n; i++)
    {
      if (!isWhiteSpace(buf.charAt(i)))
        return false;
    }

    return true;
  }

  /**
   * Tell if the string is whitespace.
   *
   * <p>
   *  判断字符串是否为空格。
   * 
   * @param s String to check as XML whitespace.
   * @return True if characters in buffer are XML whitespace, false otherwise
   */
  public static boolean isWhiteSpace(String s)
  {

    if(null != s)
    {
      int n = s.length();

      for (int i = 0; i < n; i++)
      {
        if (!isWhiteSpace(s.charAt(i)))
          return false;
      }
    }

    return true;
  }

}
