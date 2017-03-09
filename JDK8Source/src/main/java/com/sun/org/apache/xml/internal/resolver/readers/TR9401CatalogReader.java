/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
// TR9401CatalogReader.java - Read OASIS Catalog files

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

package com.sun.org.apache.xml.internal.resolver.readers;

import java.io.InputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Vector;
import com.sun.org.apache.xml.internal.resolver.Catalog;
import com.sun.org.apache.xml.internal.resolver.CatalogEntry;
import com.sun.org.apache.xml.internal.resolver.CatalogException;

/**
 * Parses OASIS Open Catalog files.
 *
 * <p>This class reads OASIS Open Catalog files, returning a stream
 * of tokens.</p>
 *
 * <p>This code interrogates the following non-standard system properties:</p>
 *
 * <dl>
 * <dt><b>xml.catalog.debug</b></dt>
 * <dd><p>Sets the debug level. A value of 0 is assumed if the
 * property is not set or is not a number.</p></dd>
 * </dl>
 *
 * <p>
 *  解析OASIS打开目录文件。
 * 
 *  <p>此类读取OASIS打开目录文件,返回一个令牌流。</p>
 * 
 *  <p>此代码查询以下非标准系统属性：</p>
 * 
 * <dl>
 *  <dt> <b> xml.catalog.debug </b> </dt> <dd> <p>设置调试级别。如果属性未设置或不是数字,则假定值为0. </p> </dd>
 * 
 * @see Catalog
 *
 * @author Norman Walsh
 * <a href="mailto:Norman.Walsh@Sun.COM">Norman.Walsh@Sun.COM</a>
 *
 */
public class TR9401CatalogReader extends TextCatalogReader {

  /**
   * Start parsing an OASIS TR9401 Open Catalog file. The file is
   * actually read and parsed
   * as needed by <code>nextEntry</code>.
   *
   * <p>In a TR9401 Catalog the 'DELEGATE' entry delegates public
   * identifiers. There is no delegate entry for system identifiers
   * or URIs.</p>
   *
   * <p>
   * </dl>
   * 
   * 
   * @param catalog The Catalog to populate
   * @param is The input stream from which to read the TR9401 Catalog
   *
   * @throws MalformedURLException Improper fileUrl
   * @throws IOException Error reading catalog file
   */
  public void readCatalog(Catalog catalog, InputStream is)
    throws MalformedURLException, IOException {

    catfile = is;

    if (catfile == null) {
      return;
    }

    Vector unknownEntry = null;

    try {
      while (true) {
        String token = nextToken();

        if (token == null) {
          if (unknownEntry != null) {
            catalog.unknownEntry(unknownEntry);
            unknownEntry = null;
          }
          catfile.close();
          catfile = null;
          return;
        }

        String entryToken = null;
        if (caseSensitive) {
          entryToken = token;
        } else {
          entryToken = token.toUpperCase();
        }

        if (entryToken.equals("DELEGATE")) {
          entryToken = "DELEGATE_PUBLIC";
        }

        try {
          int type = CatalogEntry.getEntryType(entryToken);
          int numArgs = CatalogEntry.getEntryArgCount(type);
          Vector args = new Vector();

          if (unknownEntry != null) {
            catalog.unknownEntry(unknownEntry);
            unknownEntry = null;
          }

          for (int count = 0; count < numArgs; count++) {
            args.addElement(nextToken());
          }

          catalog.addEntry(new CatalogEntry(entryToken, args));
        } catch (CatalogException cex) {
          if (cex.getExceptionType() == CatalogException.INVALID_ENTRY_TYPE) {
            if (unknownEntry == null) {
              unknownEntry = new Vector();
            }
            unknownEntry.addElement(token);
          } else if (cex.getExceptionType() == CatalogException.INVALID_ENTRY) {
            catalog.getCatalogManager().debug.message(1, "Invalid catalog entry", token);
            unknownEntry = null;
          } else if (cex.getExceptionType() == CatalogException.UNENDED_COMMENT) {
            catalog.getCatalogManager().debug.message(1, cex.getMessage());
          }
        }
      }
    } catch (CatalogException cex2) {
      if (cex2.getExceptionType() == CatalogException.UNENDED_COMMENT) {
        catalog.getCatalogManager().debug.message(1, cex2.getMessage());
      }
    }

  }
}
