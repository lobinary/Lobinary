/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
// DOMCatalogParser.java - An interface for reading catalog files

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

import com.sun.org.apache.xml.internal.resolver.Catalog;
import org.w3c.dom.Node;

/**
 * The DOMCatalogParser interface.
 *
 * <p>This interface must be implemented in order for a class to
 * participate as a parser for the DOMCatalogReader.
 *
 * <p>
 *  DOMCatalogParser接口。
 * 
 *  <p>必须实现此接口,以使类作为DOMCatalogReader的解析器加入。
 * 
 * 
 * @see Catalog
 * @see DOMCatalogReader
 *
 * @author Norman Walsh
 * <a href="mailto:Norman.Walsh@Sun.COM">Norman.Walsh@Sun.COM</a>
 *
 */
public interface DOMCatalogParser {
    /**
     * Parse a DOM node as a catalog entry.
     *
     * <p>This method is expected to analyze the specified node and
     * construct appropriate catalog entry(ies) from it.</p>
     *
     * <p>
     *  将DOM节点解析为商品。
     * 
     * 
     * @param catalog The catalog for which this node is being considered.
     * @param node The DOM Node from the catalog.
     */
    public void parseCatalogEntry(Catalog catalog, Node node);
}
