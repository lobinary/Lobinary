/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2003,2004 The Apache Software Foundation.
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
 *  版权所有2003,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.xs;

import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.ls.LSInput;

/**
 * An interface that provides a method to load XML Schema documents. This
 * interface uses the DOM Level 3 Core and Load and Save interfaces.
 * <p>
 *  提供加载XML模式文档的方法的接口。此接口使用DOM Level 3 Core和加载和保存接口。
 * 
 */
public interface XSLoader {
    /**
     *  The configuration of a document. It maintains a table of recognized
     * parameters. Using the configuration, it is possible to change the
     * behavior of the load methods. The configuration may support the
     * setting of and the retrieval of the following non-boolean parameters
     * defined on the <code>DOMConfiguration</code> interface:
     * <code>error-handler</code> (<code>DOMErrorHandler</code>) and
     * <code>resource-resolver</code> (<code>LSResourceResolver</code>).
     * <br> The following list of boolean parameters is defined:
     * <dl>
     * <dt>
     * <code>"validate"</code></dt>
     * <dd>
     * <dl>
     * <dt><code>true</code></dt>
     * <dd>[required] (default) Validate an XML
     * Schema during loading. If validation errors are found, the error
     * handler is notified. </dd>
     * <dt><code>false</code></dt>
     * <dd>[optional] Do not
     * report errors during the loading of an XML Schema document. </dd>
     * </dl></dd>
     * </dl>
     * <p>
     *  文档的配置。它维护一个公认的参数表。使用配置,可以更改加载方法的行为。
     * 配置可以支持在<code> DOMConfiguration </code>接口上定义的以下非布尔参数的设置和检索：<code> error-handler </code>(<code> DOMErro
     * rHandler </code>)和<code> resource-resolver </code>(<code> LSResourceResolver </code>)。
     *  文档的配置。它维护一个公认的参数表。使用配置,可以更改加载方法的行为。 <br>定义了以下布尔参数列表：。
     * <dl>
     * <dt>
     *  <code>"validate"</code> </dt>
     * <dd>
     * <dl>
     * <dt> <code> true </code> </dt> <dd> [必需](默认)在加载期间验证XML模式。如果发现验证错误,则通知错误处理程序。
     *  </dd> <dt> <code> false </code> </dt> <dd> [可选]在加载XML模式文档期间不报告错误。 </dd> </dl> </dd>。
     * </dl>
     */
    public DOMConfiguration getConfig();

    /**
     * Parses the content of XML Schema documents specified as the list of URI
     * references. If the URI contains a fragment identifier, the behavior
     * is not defined by this specification.
     * <p>
     * 
     * @param uriList The list of URI locations.
     * @return An XSModel representing the schema documents.
     */
    public XSModel loadURIList(StringList uriList);

    /**
     *  Parses the content of XML Schema documents specified as a list of
     * <code>LSInput</code>s.
     * <p>
     *  解析指定为URI引用列表的XML模式文档的内容。如果URI包含片段标识符,则此规范未定义行为。
     * 
     * 
     * @param is  The list of <code>LSInput</code>s from which the XML
     *   Schema documents are to be read.
     * @return An XSModel representing the schema documents.
     */
    public XSModel loadInputList(LSInputList is);

    /**
     * Parse an XML Schema document from a location identified by a URI
     * reference. If the URI contains a fragment identifier, the behavior is
     * not defined by this specification.
     * <p>
     *  解析指定为<code> LSInput </code>列表的XML模式文档的内容。
     * 
     * 
     * @param uri The location of the XML Schema document to be read.
     * @return An XSModel representing this schema.
     */
    public XSModel loadURI(String uri);

    /**
     *  Parse an XML Schema document from a resource identified by a
     * <code>LSInput</code> .
     * <p>
     *  从由URI引用标识的位置解析XML模式文档。如果URI包含片段标识符,则此规范未定义行为。
     * 
     * 
     * @param is  The <code>LSInput</code> from which the source
     *   document is to be read.
     * @return An XSModel representing this schema.
     */
    public XSModel load(LSInput is);

}
