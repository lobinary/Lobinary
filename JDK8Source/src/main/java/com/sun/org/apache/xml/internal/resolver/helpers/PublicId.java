/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
// PublicId.java - Information about public identifiers

/*
 * Copyright 2001-2004 The Apache Software Foundation or its licensors,
 * as applicable.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有2001-2004 Apache软件基金会或其许可方(如适用)。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xml.internal.resolver.helpers;

/**
 * Static methods for dealing with public identifiers.
 *
 * <p>This class defines a set of static methods that can be called
 * to handle public identifiers.</p>
 *
 * <p>
 *  处理公共标识符的静态方法。
 * 
 *  <p>此类定义了一组可以调用以处理公共标识符的静态方法。</p>
 * 
 * 
 * @author Norman Walsh
 * <a href="mailto:Norman.Walsh@Sun.COM">Norman.Walsh@Sun.COM</a>
 *
 */
public abstract class PublicId {
  protected PublicId() { }

  /**
   * Normalize a public identifier.
   *
   * <p>Public identifiers must be normalized according to the following
   * rules before comparisons between them can be made:</p>
   *
   * <ul>
   * <li>Whitespace characters are normalized to spaces (e.g., line feeds,
   * tabs, etc. become spaces).</li>
   * <li>Leading and trailing whitespace is removed.</li>
   * <li>Multiple internal whitespaces are normalized to a single
   * space.</li>
   * </ul>
   *
   * <p>This method is declared static so that other classes
   * can use it directly.</p>
   *
   * <p>
   *  规范化公共标识符。
   * 
   *  <p>公开标识符必须根据以下规则进行标准化,然后才能进行比较：</p>
   * 
   * <ul>
   *  <li>空白字符已标准化为空格(例如,换行符,制表符等将成为空格)。</li> <li>删除前导和尾随空格。</li> <li>一个空格。</li>
   * </ul>
   * 
   *  <p>此方法声明为静态,以便其他类可以直接使用它。</p>
   * 
   * 
   * @param publicId The unnormalized public identifier.
   *
   * @return The normalized identifier.
   */
  public static String normalize(String publicId) {
    String normal = publicId.replace('\t', ' ');
    normal = normal.replace('\r', ' ');
    normal = normal.replace('\n', ' ');
    normal = normal.trim();

    int pos;

    while ((pos = normal.indexOf("  ")) >= 0) {
      normal = normal.substring(0, pos) + normal.substring(pos+1);
    }

    return normal;
  }

  /**
   * Encode a public identifier as a "publicid" URN.
   *
   * <p>This method is declared static so that other classes
   * can use it directly.</p>
   *
   * <p>
   *  将公共标识符编码为"publicid"URN。
   * 
   *  <p>此方法声明为静态,以便其他类可以直接使用它。</p>
   * 
   * 
   * @param publicId The unnormalized public identifier.
   *
   * @return The normalized identifier.
   */
  public static String encodeURN(String publicId) {
    String urn = PublicId.normalize(publicId);

    urn = PublicId.stringReplace(urn, "%", "%25");
    urn = PublicId.stringReplace(urn, ";", "%3B");
    urn = PublicId.stringReplace(urn, "'", "%27");
    urn = PublicId.stringReplace(urn, "?", "%3F");
    urn = PublicId.stringReplace(urn, "#", "%23");
    urn = PublicId.stringReplace(urn, "+", "%2B");
    urn = PublicId.stringReplace(urn, " ", "+");
    urn = PublicId.stringReplace(urn, "::", ";");
    urn = PublicId.stringReplace(urn, ":", "%3A");
    urn = PublicId.stringReplace(urn, "//", ":");
    urn = PublicId.stringReplace(urn, "/", "%2F");

    return "urn:publicid:" + urn;
  }

  /**
   * Decode a "publicid" URN into a public identifier.
   *
   * <p>This method is declared static so that other classes
   * can use it directly.</p>
   *
   * <p>
   * 将"publicid"URN解码为公共标识符。
   * 
   *  <p>此方法声明为静态,以便其他类可以直接使用它。</p>
   * 
   * 
   * @param urn The urn:publicid: URN
   *
   * @return The normalized identifier.
   */
  public static String decodeURN(String urn) {
    String publicId = "";

    if (urn.startsWith("urn:publicid:")) {
      publicId = urn.substring(13);
    } else {
      return urn;
    }

    publicId = PublicId.stringReplace(publicId, "%2F", "/");
    publicId = PublicId.stringReplace(publicId, ":", "//");
    publicId = PublicId.stringReplace(publicId, "%3A", ":");
    publicId = PublicId.stringReplace(publicId, ";", "::");
    publicId = PublicId.stringReplace(publicId, "+", " ");
    publicId = PublicId.stringReplace(publicId, "%2B", "+");
    publicId = PublicId.stringReplace(publicId, "%23", "#");
    publicId = PublicId.stringReplace(publicId, "%3F", "?");
    publicId = PublicId.stringReplace(publicId, "%27", "'");
    publicId = PublicId.stringReplace(publicId, "%3B", ";");
    publicId = PublicId.stringReplace(publicId, "%25", "%");

    return publicId;
    }

  /**
   * Replace one string with another.
   *
   * <p>
   */
  private static String stringReplace(String str,
                                      String oldStr,
                                      String newStr) {

    String result = "";
    int pos = str.indexOf(oldStr);

    //    System.out.println(str + ": " + oldStr + " => " + newStr);

    while (pos >= 0) {
      //      System.out.println(str + " (" + pos + ")");
      result += str.substring(0, pos);
      result += newStr;
      str = str.substring(pos+1);

      pos = str.indexOf(oldStr);
    }

    return result + str;
  }
}
