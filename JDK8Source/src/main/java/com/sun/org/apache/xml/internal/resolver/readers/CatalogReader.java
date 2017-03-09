/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
// CatalogReader.java - An interface for reading catalog files

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

import java.io.IOException;
import java.net.MalformedURLException;
import com.sun.org.apache.xml.internal.resolver.CatalogException;

import java.io.InputStream;
import com.sun.org.apache.xml.internal.resolver.Catalog;

/**
 * The CatalogReader interface.
 *
 * <p>The Catalog class requires that classes implement this interface
 * in order to be used to read catalogs. Examples of CatalogReaders
 * include the TextCatalogReader, the SAXCatalogReader, and the
 * DOMCatalogReader.</p>
 *
 * <p>
 *  CatalogReader接口。
 * 
 *  <p> Catalog类要求类实现此接口,以便用于读取目录。
 *  CatalogReaders的示例包括TextCatalogReader,SAXCatalogReader和DOMCatalogReader。</p>。
 * 
 * 
 * @see Catalog
 *
 * @author Norman Walsh
 * <a href="mailto:Norman.Walsh@Sun.COM">Norman.Walsh@Sun.COM</a>
 *
 */
public interface CatalogReader {
    /**
     * Read a catalog from a file.
     *
     * <p>This class reads a catalog from a URL.</p>
     *
     * <p>
     *  从文件读取目录。
     * 
     *  <p>此类从网址中读取目录。</p>
     * 
     * 
     * @param catalog The catalog for which this reader is called.
     * @param fileUrl The URL of a document to be read.
     * @throws MalformedURLException if the specified URL cannot be
     * turned into a URL object.
     * @throws IOException if the URL cannot be read.
     * @throws UnknownCatalogFormatException if the catalog format is
     * not recognized.
     * @throws UnparseableCatalogException if the catalog cannot be parsed.
     * (For example, if it is supposed to be XML and isn't well-formed.)
     */
    public void readCatalog(Catalog catalog, String fileUrl)
      throws MalformedURLException, IOException, CatalogException;

    /**
     * Read a catalog from an input stream.
     *
     * <p>This class reads a catalog from an input stream.</p>
     *
     * <p>
     *  从输入流读取目录。
     * 
     * 
     * @param catalog The catalog for which this reader is called.
     * @param is The input stream that is to be read.
     * @throws IOException if the URL cannot be read.
     * @throws UnknownCatalogFormatException if the catalog format is
     * not recognized.
     * @throws UnparseableCatalogException if the catalog cannot be parsed.
     * (For example, if it is supposed to be XML and isn't well-formed.)
     */
    public void readCatalog(Catalog catalog, InputStream is)
        throws IOException, CatalogException;
}
