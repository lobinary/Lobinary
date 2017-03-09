/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001, 2002,2004 The Apache Software Foundation.
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
 *  版权所有2001,2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.xni.parser;

import java.io.IOException;
import com.sun.org.apache.xerces.internal.xni.XNIException;

/**
 * This interface defines a generic document scanner. This interface
 * allows a scanner to be used interchangably in existing parser
 * configurations.
 * <p>
 * If the parser configuration uses a document scanner that implements
 * this interface, components should be able to query the scanner
 * instance from the component manager using the following property
 * identifier:
 * <blockquote>
 *  "http://apache.org/xml/properties/internal/document-scanner"
 * </blockquote>
 *
 * <p>
 *  此接口定义通用文档扫描器。此接口允许扫描仪在现有的解析器配置中可互换使用。
 * <p>
 *  如果解析器配置使用实现此接口的文档扫描程序,组件应该能够使用以下属性标识符从组件管理器查询扫描程序实例：
 * <blockquote>
 *  "http://apache.org/xml/properties/internal/document-scanner"
 * </blockquote>
 * 
 * 
 * @author Andy Clark, IBM
 *
 */
public interface XMLDocumentScanner
    extends XMLDocumentSource {

    //
    // XMLDocumentScanner methods
    //

    /**
     * Sets the input source.
     *
     * <p>
     * 
     * @param inputSource The input source.
     *
     * @throws IOException Thrown on i/o error.
     */
    public void setInputSource(XMLInputSource inputSource) throws IOException;

    /**
     * Scans a document.
     *
     * <p>
     *  设置输入源。
     * 
     * 
     * @param complete True if the scanner should scan the document
     *                 completely, pushing all events to the registered
     *                 document handler. A value of false indicates that
     *                 that the scanner should only scan the next portion
     *                 of the document and return. A scanner instance is
     *                 permitted to completely scan a document if it does
     *                 not support this "pull" scanning model.
     *
     * @return True if there is more to scan, false otherwise.
     */
    public boolean scanDocument(boolean complete)
        throws IOException, XNIException;

    public int next() throws XNIException, IOException;
} // interface XMLDocumentScanner
