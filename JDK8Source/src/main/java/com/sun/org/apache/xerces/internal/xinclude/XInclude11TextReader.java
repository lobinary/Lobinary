/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2003-2005 The Apache Software Foundation.
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
 *  版权所有2003-2005 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
package com.sun.org.apache.xerces.internal.xinclude;

import java.io.IOException;

import com.sun.org.apache.xerces.internal.util.XML11Char;
import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;

/**
 * This class is used for reading resources requested in &lt;include&gt; elements in
 * XML 1.1 entities, when the parse attribute of the &lt;include&gt; element is "text".
 * Using this class will open the location, detect the encoding, and discard the
 * byte order mark, if applicable.
 *
 * <p>
 *  该类用于读取&lt; include&gt;中请求的资源。当包括&lt; include&gt;元素的parse属性时,元素是"文本"。使用此类将打开位置,检测编码,并丢弃字节顺序标记(如果适用)。
 * 
 * 
 * @author Michael Glavassevich, IBM
 *
 *
 * @see XIncludeHandler
 */
public class XInclude11TextReader
    extends XIncludeTextReader {

    /**
     * Construct the XIncludeReader using the XMLInputSource and XIncludeHandler.
     *
     * <p>
     *  使用XMLInputSource和XIncludeHandler构造XIncludeReader。
     * 
     * 
     * @param source The XMLInputSource to use.
     * @param handler The XIncludeHandler to use.
     * @param bufferSize The size of this text reader's buffer.
     */
    public XInclude11TextReader(XMLInputSource source, XIncludeHandler handler, int bufferSize)
        throws IOException {
        super(source, handler, bufferSize);
    }

    /**
     * Returns true if the specified character is a valid XML character
     * as per the rules of XML 1.1.
     *
     * <p>
     *  如果指定的字符是根据XML 1.1的规则的有效XML字符,则返回true。
     * 
     * @param ch The character to check.
     */
    protected boolean isValid(int ch) {
        return XML11Char.isXML11Valid(ch);
    }
}
