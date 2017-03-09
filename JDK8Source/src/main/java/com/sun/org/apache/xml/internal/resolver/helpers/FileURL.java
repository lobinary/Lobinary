/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
// FileURL.java - Construct a file: scheme URL

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

import java.net.URL;
import java.net.MalformedURLException;
import java.io.File;

/**
 * Static method for dealing with file: URLs.
 *
 * <p>This class defines a static method that can be used to construct
 * an appropriate file: URL from parts. It's defined here so that it
 * can be reused throught the resolver.</p>
 *
 * <p>(Yes, I'd rather have called this class FileUR<b>I</b>, but
 * given that a jave.net.URL is returned, it seemed...even more
 * confusing.)</p>
 *
 * <p>
 *  处理文件的静态方法：URL。
 * 
 *  <p>此类定义了一个静态方法,可用于构建适当的文件：来自部件的URL。它在这里定义,以便它可以通过解析器重用。</p>
 * 
 *  <p>(是的,我宁愿调用这个类FileUR <b> I </b>,但考虑到一个jave.net.URL被返回,它似乎...更令人困惑。)</p>
 * 
 * 
 * @author Norman Walsh
 * <a href="mailto:Norman.Walsh@Sun.COM">Norman.Walsh@Sun.COM</a>
 *
 * @version 1.0
 */
public abstract class FileURL {
  protected FileURL() { }

  /**
   * Construct a file: URL for a path name.
   *
   * <p>URLs in the file: scheme can be constructed for paths on
   * the local file system. Several possibilities need to be considered:
   * </p>
   *
   * <ul>
   * <li>If the path does not begin with a slash, then it is assumed
   * to reside in the users current working directory
   * (System.getProperty("user.dir")).</li>
   * <li>On Windows machines, the current working directory uses
   * backslashes (\\, instead of /).</li>
   * <li>If the current working directory is "/", don't add an extra
   * slash before the base name.</li>
   * </ul>
   *
   * <p>This method is declared static so that other classes
   * can use it directly.</p>
   *
   * <p>
   *  构造文件：路径名的URL。
   * 
   *  <p>文件：scheme中的URL可以为本地文件系统上的路径构建。需要考虑几种可能性：
   * </p>
   * 
   * <ul>
   * <li>如果路径不以斜杠开头,则假定它驻留在用户当前工作目录(System.getProperty("user.dir"))中。
   * </li> <li>在Windows计算机上,当前工作目录使用反斜杠(\\,而不是/)。</li> <li>如果当前工作目录是"/",请不要在基本名称前添加一个斜杠。
   * </ul>
   * 
   * 
   * @param pathname The path name component for which to construct a URL.
   *
   * @return The appropriate file: URL.
   *
   * @throws MalformedURLException if the pathname can't be turned into
   *         a proper URL.
   */
  public static URL makeURL(String pathname) throws MalformedURLException {
    /*if (pathname.startsWith("/")) {
      return new URL("file://" + pathname);
    }

    String userdir = System.getProperty("user.dir");
    userdir.replace('\\', '/');

    if (userdir.endsWith("/")) {
      return new URL("file:///" + userdir + pathname);
    } else {
      return new URL("file:///" + userdir + "/" + pathname);
    }
    /* <p>
    /*  <p>此方法声明为静态,以便其他类可以直接使用它。</p>
    /* 
     */
      File file = new File(pathname);
      return file.toURI().toURL();
  }
}
