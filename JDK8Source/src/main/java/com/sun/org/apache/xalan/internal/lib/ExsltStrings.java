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
 * $Id: ExsltStrings.java,v 1.1.2.1 2005/08/01 02:08:48 jeffsuttor Exp $
 * <p>
 *  $ Id：ExsltStrings.java,v 1.1.2.1 2005/08/01 02:08:48 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xalan.internal.lib;

import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import com.sun.org.apache.xpath.internal.NodeSet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * This class contains EXSLT strings extension functions.
 *
 * It is accessed by specifying a namespace URI as follows:
 * <pre>
 *    xmlns:str="http://exslt.org/strings"
 * </pre>
 * The documentation for each function has been copied from the relevant
 * EXSLT Implementer page.
 *
 * <p>
 *  这个类包含EXSLT字符串扩展函数。
 * 
 *  可通过指定名称空间URI来访问它,如下所示：
 * <pre>
 *  xmlns：str ="http://exslt.org/strings"
 * </pre>
 *  每个函数的文档已从相关的EXSLT实施者页面复制。
 * 
 * 
 * @see <a href="http://www.exslt.org/">EXSLT</a>

 * @xsl.usage general
 */
public class ExsltStrings extends ExsltBase
{
   static final String JDK_DEFAULT_DOM = "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl";

  /**
   * The str:align function aligns a string within another string.
   * <p>
   * The first argument gives the target string to be aligned. The second argument gives
   * the padding string within which it is to be aligned.
   * <p>
   * If the target string is shorter than the padding string then a range of characters
   * in the padding string are repaced with those in the target string. Which characters
   * are replaced depends on the value of the third argument, which gives the type of
   * alignment. It can be one of 'left', 'right' or 'center'. If no third argument is
   * given or if it is not one of these values, then it defaults to left alignment.
   * <p>
   * With left alignment, the range of characters replaced by the target string begins
   * with the first character in the padding string. With right alignment, the range of
   * characters replaced by the target string ends with the last character in the padding
   * string. With center alignment, the range of characters replaced by the target string
   * is in the middle of the padding string, such that either the number of unreplaced
   * characters on either side of the range is the same or there is one less on the left
   * than there is on the right.
   * <p>
   * If the target string is longer than the padding string, then it is truncated to be
   * the same length as the padding string and returned.
   *
   * <p>
   *  str：align函数将字符串与另一个字符串对齐。
   * <p>
   *  第一个参数给出要对齐的目标字符串。第二个参数给出了要在其中对齐的填充字符串。
   * <p>
   * 如果目标字符串比填充字符串短,那么填充字符串中的字符范围将与目标字符串中的字符范围重新排列。哪些字符被替换取决于第三个参数的值,它给出了对齐的类型。它可以是"左","右"或"中心"之一。
   * 如果没有给出第三个参数,或者它不是这些值之一,那么它默认为左对齐。
   * <p>
   *  使用左对齐,由目标字符串替换的字符范围以填充字符串中的第一个字符开始。使用右对齐,由目标字符串替换的字符范围以填充字符串中的最后一个字符结尾。
   * 使用中心对齐时,由目标字符串替换的字符范围位于填充字符串的中间,以使范围任一侧的未替换字符数相同,或左侧有一个少于在右边。
   * <p>
   *  如果目标字符串比填充字符串长,则它被截断为与填充字符串相同的长度并返回。
   * 
   * 
   * @param targetStr The target string
   * @param paddingStr The padding string
   * @param type The type of alignment
   *
   * @return The string after alignment
   */
  public static String align(String targetStr, String paddingStr, String type)
  {
    if (targetStr.length() >= paddingStr.length())
      return targetStr.substring(0, paddingStr.length());

    if (type.equals("right"))
    {
      return paddingStr.substring(0, paddingStr.length() - targetStr.length()) + targetStr;
    }
    else if (type.equals("center"))
    {
      int startIndex = (paddingStr.length() - targetStr.length()) / 2;
      return paddingStr.substring(0, startIndex) + targetStr + paddingStr.substring(startIndex + targetStr.length());
    }
    // Default is left
    else
    {
      return targetStr + paddingStr.substring(targetStr.length());
    }
  }

  /**
   * See above
   * <p>
   *  往上看
   * 
   */
  public static String align(String targetStr, String paddingStr)
  {
    return align(targetStr, paddingStr, "left");
  }

  /**
   * The str:concat function takes a node set and returns the concatenation of the
   * string values of the nodes in that node set. If the node set is empty, it returns
   * an empty string.
   *
   * <p>
   *  str：concat函数接受一个节点集,并返回该节点集中节点的字符串值的并置。如果节点集为空,它返回一个空字符串。
   * 
   * 
   * @param nl A node set
   * @return The concatenation of the string values of the nodes in that node set
   */
  public static String concat(NodeList nl)
  {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < nl.getLength(); i++)
    {
      Node node = nl.item(i);
      String value = toString(node);

      if (value != null && value.length() > 0)
        sb.append(value);
    }

    return sb.toString();
  }

  /**
   * The str:padding function creates a padding string of a certain length.
   * The first argument gives the length of the padding string to be created.
   * The second argument gives a string to be used to create the padding. This
   * string is repeated as many times as is necessary to create a string of the
   * length specified by the first argument; if the string is more than a character
   * long, it may have to be truncated to produce the required length. If no second
   * argument is specified, it defaults to a space (' '). If the second argument is
   * an empty string, str:padding returns an empty string.
   *
   * <p>
   * str：padding函数创建一定长度的填充字符串。第一个参数给出了要创建的填充字符串的长度。第二个参数给出了用于创建填充的字符串。
   * 此字符串重复所需的次数,以创建由第一个参数指定的长度的字符串;如果字符串超过一个字符长,它可能必须被截断以产生所需的长度。如果没有指定第二个参数,它将默认为空格('')。
   * 如果第二个参数是空字符串,str：padding返回一个空字符串。
   * 
   * 
   * @param length The length of the padding string to be created
   * @param pattern The string to be used as pattern
   *
   * @return A padding string of the given length
   */
  public static String padding(double length, String pattern)
  {
    if (pattern == null || pattern.length() == 0)
      return "";

    StringBuffer sb = new StringBuffer();
    int len = (int)length;
    int numAdded = 0;
    int index = 0;
    while (numAdded < len)
    {
      if (index == pattern.length())
        index = 0;

      sb.append(pattern.charAt(index));
      index++;
      numAdded++;
    }

    return sb.toString();
  }

  /**
   * See above
   * <p>
   *  往上看
   * 
   */
  public static String padding(double length)
  {
    return padding(length, " ");
  }

  /**
   * The str:split function splits up a string and returns a node set of token
   * elements, each containing one token from the string.
   * <p>
   * The first argument is the string to be split. The second argument is a pattern
   * string. The string given by the first argument is split at any occurrence of
   * this pattern. For example:
   * <pre>
   * str:split('a, simple, list', ', ') gives the node set consisting of:
   *
   * <token>a</token>
   * <token>simple</token>
   * <token>list</token>
   * </pre>
   * If the second argument is omitted, the default is the string '&#x20;' (i.e. a space).
   *
   * <p>
   *  str：split函数拆分一个字符串,并返回一组节点元素,每个节点包含一个来自字符串的令牌。
   * <p>
   *  第一个参数是要拆分的字符串。第二个参数是模式字符串。由第一个参数给定的字符串在此模式的任何出现时被拆分。例如：
   * <pre>
   *  str：split('a,simple,list',',')给出由以下组成的节点集：
   * 
   *  <token> a </token> <token> simple </token> <token> list </token>
   * </pre>
   *  如果省略第二个参数,默认值为字符串'&#x20;' (即空间)。
   * 
   * 
   * @param str The string to be split
   * @param pattern The pattern
   *
   * @return A node set of split tokens
   */
  public static NodeList split(String str, String pattern)
  {


    NodeSet resultSet = new NodeSet();
    resultSet.setShouldCacheNodes(true);

    boolean done = false;
    int fromIndex = 0;
    int matchIndex = 0;
    String token = null;

    while (!done && fromIndex < str.length())
    {
      matchIndex = str.indexOf(pattern, fromIndex);
      if (matchIndex >= 0)
      {
        token = str.substring(fromIndex, matchIndex);
        fromIndex = matchIndex + pattern.length();
      }
      else
      {
        done = true;
        token = str.substring(fromIndex);
      }

      Document doc = getDocument();
      synchronized (doc)
      {
        Element element = doc.createElement("token");
        Text text = doc.createTextNode(token);
        element.appendChild(text);
        resultSet.addNode(element);
      }
    }

    return resultSet;
  }

  /**
   * See above
   * <p>
   *  往上看
   * 
   */
  public static NodeList split(String str)
  {
    return split(str, " ");
  }

  /**
   * The str:tokenize function splits up a string and returns a node set of token
   * elements, each containing one token from the string.
   * <p>
   * The first argument is the string to be tokenized. The second argument is a
   * string consisting of a number of characters. Each character in this string is
   * taken as a delimiting character. The string given by the first argument is split
   * at any occurrence of any of these characters. For example:
   * <pre>
   * str:tokenize('2001-06-03T11:40:23', '-T:') gives the node set consisting of:
   *
   * <token>2001</token>
   * <token>06</token>
   * <token>03</token>
   * <token>11</token>
   * <token>40</token>
   * <token>23</token>
   * </pre>
   * If the second argument is omitted, the default is the string '&#x9;&#xA;&#xD;&#x20;'
   * (i.e. whitespace characters).
   * <p>
   * If the second argument is an empty string, the function returns a set of token
   * elements, each of which holds a single character.
   * <p>
   * Note: This one is different from the tokenize extension function in the Xalan
   * namespace. The one in Xalan returns a set of Text nodes, while this one wraps
   * the Text nodes inside the token Element nodes.
   *
   * <p>
   *  str：tokenize函数拆分一个字符串,并返回一组节点元素,每个节点包含一个来自字符串的令牌。
   * <p>
   * 第一个参数是要进行标记化的字符串。第二个参数是由多个字符组成的字符串。此字符串中的每个字符都将作为定界字符。第一个参数给出的字符串在任何这些字符出现时都会被拆分。例如：
   * <pre>
   *  str：tokenize('2001-06-03T11：40：23','-T：')给出由以下组成的节点集：
   * 
   *  <token> 2001 </token> <token> </token> </token> </token> </token>
   * </pre>
   *  如果省略第二个参数,默认值为字符串'&#x9;&#xA;&#xD;&#x20;' (即空白字符)。
   * 
   * @param toTokenize The string to be tokenized
   * @param delims The delimiter string
   *
   * @return A node set of split token elements
   */
  public static NodeList tokenize(String toTokenize, String delims)
  {


    NodeSet resultSet = new NodeSet();

    if (delims != null && delims.length() > 0)
    {
      StringTokenizer lTokenizer = new StringTokenizer(toTokenize, delims);

      Document doc = getDocument();
      synchronized (doc)
      {
        while (lTokenizer.hasMoreTokens())
        {
          Element element = doc.createElement("token");
          element.appendChild(doc.createTextNode(lTokenizer.nextToken()));
          resultSet.addNode(element);
        }
      }
    }
    // If the delimiter is an empty string, create one token Element for
    // every single character.
    else
    {

      Document doc = getDocument();
      synchronized (doc)
      {
        for (int i = 0; i < toTokenize.length(); i++)
        {
          Element element = doc.createElement("token");
          element.appendChild(doc.createTextNode(toTokenize.substring(i, i+1)));
          resultSet.addNode(element);
        }
      }
    }

    return resultSet;
  }

  /**
   * See above
   * <p>
   * <p>
   *  如果第二个参数是空字符串,则函数返回一组令牌元素,每个元素都保存单个字符。
   * <p>
   *  注意：这个不同于Xalan命名空间中的tokenize扩展函数。 Xalan中的一个返回一组Text节点,而这一个包裹了Token Element节点内的Text节点。
   * 
   */
  public static NodeList tokenize(String toTokenize)
  {
    return tokenize(toTokenize, " \t\n\r");
  }

    /**
    /* <p>
    /* 
   * @return an instance of DOM Document
     */
   private static Document getDocument()
   {
        try
        {
            if (System.getSecurityManager() == null) {
                return DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            } else {
                return DocumentBuilderFactory.newInstance(JDK_DEFAULT_DOM, null).newDocumentBuilder().newDocument();
            }
        }
        catch(ParserConfigurationException pce)
        {
            throw new com.sun.org.apache.xml.internal.utils.WrappedRuntimeException(pce);
        }
    }
}
