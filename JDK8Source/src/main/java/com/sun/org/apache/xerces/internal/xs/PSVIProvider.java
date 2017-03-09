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

/**
 * This interface provides access to the post schema validation infoset for an
 * API that provides a streaming document infoset, such as SAX, XNI, and
 * others.
 * <p>For implementations that would like to provide access to the PSVI in a
 * streaming model, a parser object should also implement the
 * <code>PSVIProvider</code> interface. Within the scope of the methods
 * handling the start and end of an element, applications may use the
 * <code>PSVIProvider</code> to retrieve the PSVI related to the element and
 * its attributes.
 * <p>
 *  此接口提供对提供流文档信息集(例如SAX,XNI和其他)的API的后模式验证信息集的访问。
 *  <p>对于想要在流模型中提供对PSVI的访问的实现,解析器对象还应该实现<code> PSVIProvider </code>接口。
 * 在处理元素的开始和结束的方法的范围内,应用可以使用<code> PSVIProvider </code>来检索与元素及其属性相关的PSVI。
 * 
 */
public interface PSVIProvider {
    /**
     *  Provides the post schema validation item for the current element
     * information item. The method must be called by an application while
     * in the scope of the methods which report the start and end of an
     * element. For example, for SAX the method must be called within the
     * scope of the document handler's <code>startElement</code> or
     * <code>endElement</code> call. If the method is called outside of the
     * specified scope, the return value is undefined.
     * <p>
     * 提供当前元素信息项的后模式验证项。该方法必须由应用程序调用,而在报告元​​素的开始和结束的方法的范围内。
     * 例如,对于SAX,必须在文档处理程序的<code> startElement </code>或<code> endElement </code>调用范围内调用该方法。
     * 如果在指定范围之外调用该方法,则返回值未定义。
     * 
     * 
     * @return The post schema validation infoset for the current element. If
     *   an element information item is valid, then in the
     *   post-schema-validation infoset the following properties must be
     *   available for the element information item: The following
     *   properties are available in the scope of the method that reports
     *   the start of an element: {element declaration}, {validation
     *   context}, {notation}. The {schema information} property is
     *   available for the validation root. The {error codes} property is
     *   available if any errors occured during validation.  The following
     *   properties are available in the scope of the method that reports
     *   the end of an element: {nil}, {schema specified}, {normalized
     *   value},{ member type definition}, {validity}, {validation attempted}
     *   . If the declaration has a value constraint, the property {schema
     *   default} is available. The {error codes} property is available if
     *   any errors occured during validation. Note: some processors may
     *   choose to provide all the PSVI properties in the scope of the
     *   method that reports the end of an element.
     */
    public ElementPSVI getElementPSVI();

    /**
     * Provides <code>AttributePSVI</code> given the index of an attribute
     * information item in the current element's attribute list. The method
     * must be called by an application while in the scope of the methods
     * which report the start and end of an element at a point where the
     * attribute list is available. For example, for SAX the method must be
     * called while in the scope of the document handler's
     * <code>startElement</code> call. If the method is called outside of
     * the specified scope, the return value is undefined.
     * <p>
     *  在给定当前元素的属性列表中的属性信息项的索引的情况下提供<code> AttributePSVI </code>。该方法必须由应用程序调用,而在属性列表可用的点处报告元素的开始和结束的方法的范围内。
     * 例如,对于SAX,必须在文档处理程序的<code> startElement </code>调用范围内调用该方法。如果在指定范围之外调用该方法,则返回值未定义。
     * 
     * 
     * @param index The attribute index.
     * @return The post schema validation properties of the attribute.
     */
    public AttributePSVI getAttributePSVI(int index);

    /**
     * Provides <code>AttributePSVI</code> given the namespace name and the
     * local name of an attribute information item in the current element's
     * attribute list. The method must be called by an application while in
     * the scope of the methods which report the start and end of an element
     * at a point where the attribute list is available. For example, for
     * SAX the method must be called while in the scope of the document
     * handler's <code>startElement</code> call. If the method is called
     * outside of the specified scope, the return value is undefined.
     * <p>
     *  提供<code> AttributePSVI </code>,给出当前元素的属性列表中的属性信息项的命名空间名称和本地名称。
     * 该方法必须由应用程序调用,而在属性列表可用的点处报告元素的开始和结束的方法的范围内。例如,对于SAX,必须在文档处理程序的<code> startElement </code>调用范围内调用该方法。
     * 
     * @param uri The namespace name of an attribute.
     * @param localname The local name of an attribute.
     * @return The post schema validation properties of the attribute.
     */
    public AttributePSVI getAttributePSVIByName(String uri,
                                                String localname);

}
