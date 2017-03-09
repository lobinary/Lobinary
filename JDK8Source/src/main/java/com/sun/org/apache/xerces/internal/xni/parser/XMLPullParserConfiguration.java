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
 * Represents a parser configuration that can be used as the
 * configuration for a "pull" parser. A pull parser allows the
 * application to drive the parser instead of having document
 * information events "pushed" to the registered handlers.
 * <p>
 * A pull parser using this type of configuration first calls
 * the <code>setInputSource</code> method. After the input
 * source is set, the pull parser repeatedly calls the
 * <code>parse(boolean):boolean</code> method. This method
 * returns a value of true if there is more to parse in the
 * document.
 * <p>
 * Calling the <code>parse(XMLInputSource)</code> is equivalent
 * to setting the input source and calling the
 * <code>parse(boolean):boolean</code> method with a "complete"
 * value of <code>true</code>.
 *
 * <p>
 *  表示可用作"拉"解析器的配置的解析器配置。拉解析器允许应用程序驱动解析器,而不是将文档信息事件"推送"到注册的处理程序。
 * <p>
 *  使用这种类型配置的pull解析器首先调用<code> setInputSource </code>方法。
 * 在设置输入源之后,pull解析器重复调用<code> parse(boolean)：boolean </code>方法。如果文档中有更多要解析,此方法将返回值true。
 * <p>
 *  调用<code> parse(XMLInputSource)</code>等效于设置输入源并调用<code> parse(boolean)：boolean </code>方法,代码>。
 * 
 * 
 * @author Andy Clark, IBM
 *
 */
public interface XMLPullParserConfiguration
    extends XMLParserConfiguration {

    //
    // XMLPullParserConfiguration methods
    //

    // parsing

    /**
     * Sets the input source for the document to parse.
     *
     * <p>
     *  设置文档解析的输入源。
     * 
     * 
     * @param inputSource The document's input source.
     *
     * @exception XMLConfigurationException Thrown if there is a
     *                        configuration error when initializing the
     *                        parser.
     * @exception IOException Thrown on I/O error.
     *
     * @see #parse(boolean)
     */
    public void setInputSource(XMLInputSource inputSource)
        throws XMLConfigurationException, IOException;

    /**
     * Parses the document in a pull parsing fashion.
     *
     * <p>
     * 以拉解析方式解析文档。
     * 
     * 
     * @param complete True if the pull parser should parse the
     *                 remaining document completely.
     *
     * @return True if there is more document to parse.
     *
     * @exception XNIException Any XNI exception, possibly wrapping
     *                         another exception.
     * @exception IOException  An IO exception from the parser, possibly
     *                         from a byte stream or character stream
     *                         supplied by the parser.
     *
     * @see #setInputSource
     */
    public boolean parse(boolean complete) throws XNIException, IOException;

    /**
     * If the application decides to terminate parsing before the xml document
     * is fully parsed, the application should call this method to free any
     * resource allocated during parsing. For example, close all opened streams.
     * <p>
     *  如果应用程序决定在xml文档完全解析之前终止解析,则应用程序应调用此方法以释放在解析期间分配的任何资源。例如,关闭所有打开的流。
     */
    public void cleanup();

} // interface XMLPullParserConfiguration
