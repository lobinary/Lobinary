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
 * $Id: SystemIDResolver.java,v 1.2.4.1 2005/09/15 08:15:58 suresh_emailid Exp $
 * <p>
 *  $ Id：SystemIDResolver.java,v 1.2.4.1 2005/09/15 08:15:58 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.utils;

import java.io.File;

import javax.xml.transform.TransformerException;

import com.sun.org.apache.xml.internal.utils.URI.MalformedURIException;

/**
 * This class is used to resolve relative URIs and SystemID
 * strings into absolute URIs.
 *
 * <p>This is a generic utility for resolving URIs, other than the
 * fact that it's declared to throw TransformerException.  Please
 * see code comments for details on how resolution is performed.</p>
 * @xsl.usage internal
 * <p>
 *  此类用于将相对URI和SystemID字符串解析为绝对URI。
 * 
 *  <p>这是一个用于解析URI的通用实用程序,而不是声明为抛出TransformerException的事实。有关如何执行分辨率的详细信息,请参阅代码注释。
 * </p> @ xsl.usage internal。
 * 
 */
public class SystemIDResolver
{

  /**
   * Get an absolute URI from a given relative URI (local path).
   *
   * <p>The relative URI is a local filesystem path. The path can be
   * absolute or relative. If it is a relative path, it is resolved relative
   * to the system property "user.dir" if it is available; if not (i.e. in an
   * Applet perhaps which throws SecurityException) then we just return the
   * relative path. The space and backslash characters are also replaced to
   * generate a good absolute URI.</p>
   *
   * <p>
   *  从给定的相对URI(本地路径)获取绝对URI。
   * 
   * <p>相对URI是本地文件系统路径。路径可以是绝对路径或相对路径。
   * 如果它是相对路径,则相对于系统属性"user.dir"(如果可用)进行解析;如果不是(即在Applet也许抛出SecurityException),那么我们只是返回相对路径。
   * 空格和反斜线字符也将替换为生成良好的绝对URI。</p>。
   * 
   * 
   * @param localPath The relative URI to resolve
   *
   * @return Resolved absolute URI
   */
  public static String getAbsoluteURIFromRelative(String localPath)
  {
    if (localPath == null || localPath.length() == 0)
      return "";

    // If the local path is a relative path, then it is resolved against
    // the "user.dir" system property.
    String absolutePath = localPath;
    if (!isAbsolutePath(localPath))
    {
      try
      {
        absolutePath = getAbsolutePathFromRelativePath(localPath);
      }
      // user.dir not accessible from applet
      catch (SecurityException se)
      {
        return "file:" + localPath;
      }
    }

    String urlString;
    if (null != absolutePath)
    {
      if (absolutePath.startsWith(File.separator))
        urlString = "file://" + absolutePath;
      else
        urlString = "file:///" + absolutePath;
    }
    else
      urlString = "file:" + localPath;

    return replaceChars(urlString);
  }

  /**
   * Return an absolute path from a relative path.
   *
   * <p>
   *  从相对路径返回绝对路径。
   * 
   * 
   * @param relativePath A relative path
   * @return The absolute path
   */
  private static String getAbsolutePathFromRelativePath(String relativePath)
  {
    return new File(relativePath).getAbsolutePath();
  }

  /**
   * Return true if the systemId denotes an absolute URI .
   *
   * <p>
   *  如果systemId表示绝对URI,则返回true。
   * 
   * 
   * @param systemId The systemId string
   * @return true if the systemId is an an absolute URI
   */
  public static boolean isAbsoluteURI(String systemId)
  {
     /** http://www.ietf.org/rfc/rfc2396.txt
      *   Authors should be aware that a path segment which contains a colon
      * character cannot be used as the first segment of a relative URI path
      * (e.g., "this:that"), because it would be mistaken for a scheme name.
      * <p>
      *  作者应该注意,包含冒号字符的路径段不能用作相对URI路径的第一个段(例如,"this：that"),因为它将被误认为是方案名称。
      * 
      * 
     **/
     /**
      * %REVIEW% Can we assume here that systemId is a valid URI?
      * It looks like we cannot ( See discussion of this common problem in
      * Bugzilla Bug 22777 ).
      * <p>
      *  ％REVIEW％我们可以假设systemId是一个有效的URI?看起来我们不能(在Bugzilla Bug 22777中看到这个常见问题的讨论)。
      * 
      * 
     **/
     //"fix" for Bugzilla Bug 22777
    if(isWindowsAbsolutePath(systemId)){
        return false;
     }

    final int fragmentIndex = systemId.indexOf('#');
    final int queryIndex = systemId.indexOf('?');
    final int slashIndex = systemId.indexOf('/');
    final int colonIndex = systemId.indexOf(':');

    //finding substring  before '#', '?', and '/'
    int index = systemId.length() -1;
    if(fragmentIndex > 0)
        index = fragmentIndex;
    if((queryIndex > 0) && (queryIndex <index))
        index = queryIndex;
    if((slashIndex > 0) && (slashIndex <index))
        index = slashIndex;
    // return true if there is ':' before '#', '?', and '/'
    return ((colonIndex >0) && (colonIndex<index));

  }

  /**
   * Return true if the local path is an absolute path.
   *
   * <p>
   *  如果本地路径是绝对路径,则返回true。
   * 
   * 
   * @param systemId The path string
   * @return true if the path is absolute
   */
  public static boolean isAbsolutePath(String systemId)
  {
    if(systemId == null)
        return false;
    final File file = new File(systemId);
    return file.isAbsolute();

  }

   /**
   * Return true if the local path is a Windows absolute path.
   *
   * <p>
   *  如果本地路径是Windows绝对路径,则返回true。
   * 
   * 
   * @param systemId The path string
   * @return true if the path is a Windows absolute path
   */
    private static boolean isWindowsAbsolutePath(String systemId)
  {
    if(!isAbsolutePath(systemId))
      return false;
    // On Windows, an absolute path starts with "[drive_letter]:\".
    if (systemId.length() > 2
        && systemId.charAt(1) == ':'
        && Character.isLetter(systemId.charAt(0))
        && (systemId.charAt(2) == '\\' || systemId.charAt(2) == '/'))
      return true;
    else
      return false;
  }

  /**
   * Replace spaces with "%20" and backslashes with forward slashes in
   * the input string to generate a well-formed URI string.
   *
   * <p>
   *  用"％20"替换空格,在输入字符串中使用正斜杠替换反斜杠,以生成格式正确的URI字符串。
   * 
   * 
   * @param str The input string
   * @return The string after conversion
   */
  private static String replaceChars(String str)
  {
    StringBuffer buf = new StringBuffer(str);
    int length = buf.length();
    for (int i = 0; i < length; i++)
    {
      char currentChar = buf.charAt(i);
      // Replace space with "%20"
      if (currentChar == ' ')
      {
        buf.setCharAt(i, '%');
        buf.insert(i+1, "20");
        length = length + 2;
        i = i + 2;
      }
      // Replace backslash with forward slash
      else if (currentChar == '\\')
      {
        buf.setCharAt(i, '/');
      }
    }

    return buf.toString();
  }

  /**
   * Take a SystemID string and try to turn it into a good absolute URI.
   *
   * <p>
   *  取一个SystemID字符串,并尝试将其变成一个良好的绝对URI。
   * 
   * 
   * @param systemId A URI string, which may be absolute or relative.
   *
   * @return The resolved absolute URI
   */
  public static String getAbsoluteURI(String systemId)
  {
    String absoluteURI = systemId;
    if (isAbsoluteURI(systemId))
    {
      // Only process the systemId if it starts with "file:".
      if (systemId.startsWith("file:"))
      {
        String str = systemId.substring(5);

        // Resolve the absolute path if the systemId starts with "file:///"
        // or "file:/". Don't do anything if it only starts with "file://".
        if (str != null && str.startsWith("/"))
        {
          if (str.startsWith("///") || !str.startsWith("//"))
          {
            // A Windows path containing a drive letter can be relative.
            // A Unix path starting with "file:/" is always absolute.
            int secondColonIndex = systemId.indexOf(':', 5);
            if (secondColonIndex > 0)
            {
              String localPath = systemId.substring(secondColonIndex-1);
              try {
                if (!isAbsolutePath(localPath))
                  absoluteURI = systemId.substring(0, secondColonIndex-1) +
                                getAbsolutePathFromRelativePath(localPath);
              }
              catch (SecurityException se) {
                return systemId;
              }
            }
          }
        }
        else
        {
          return getAbsoluteURIFromRelative(systemId.substring(5));
        }

        return replaceChars(absoluteURI);
      }
      else
        return systemId;
    }
    else
      return getAbsoluteURIFromRelative(systemId);

  }


  /**
   * Take a SystemID string and try to turn it into a good absolute URI.
   *
   * <p>
   *  取一个SystemID字符串,并尝试将其变成一个良好的绝对URI。
   * 
   * @param urlString SystemID string
   * @param base The URI string used as the base for resolving the systemID
   *
   * @return The resolved absolute URI
   * @throws TransformerException thrown if the string can't be turned into a URI.
   */
  public static String getAbsoluteURI(String urlString, String base)
          throws TransformerException
  {
    if (base == null)
      return getAbsoluteURI(urlString);

    String absoluteBase = getAbsoluteURI(base);
    URI uri = null;
    try
    {
      URI baseURI = new URI(absoluteBase);
      uri = new URI(baseURI, urlString);
    }
    catch (MalformedURIException mue)
    {
      throw new TransformerException(mue);
    }

    return replaceChars(uri.toString());
  }

}
