/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2002,2004,2005 The Apache Software Foundation.
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
 *  版权所有2002,2004,2005 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.xni.grammars;

import com.sun.org.apache.xerces.internal.xni.QName;
import com.sun.org.apache.xerces.internal.xni.XMLAttributes;

/**
 * All information specific to XML Schema grammars.
 *
 * <p>
 *  所有特定于XML模式语法的信息。
 * 
 * 
 * @author Sandy Gao, IBM
 *
 */
public interface XMLSchemaDescription extends XMLGrammarDescription {

    // used to indicate what triggered the call
    /**
     * Indicate that the current schema document is &lt;include&gt;d by another
     * schema document.
     * <p>
     *  表示当前模式文档是由另一个模式文档&lt; include&gt; d。
     * 
     */
    public final static short CONTEXT_INCLUDE   = 0;
    /**
     * Indicate that the current schema document is &lt;redefine&gt;d by another
     * schema document.
     * <p>
     *  指示当前模式文档由另一个模式文档进行&lt; redefine&gt; d。
     * 
     */
    public final static short CONTEXT_REDEFINE  = 1;
    /**
     * Indicate that the current schema document is &lt;import&gt;ed by another
     * schema document.
     * <p>
     *  指示当前模式文档由另一个模式文档进行&lt; import&gt; ed。
     * 
     */
    public final static short CONTEXT_IMPORT    = 2;
    /**
     * Indicate that the current schema document is being preparsed.
     * <p>
     *  指示正在初始化当前模式文档。
     * 
     */
    public final static short CONTEXT_PREPARSE  = 3;
    /**
     * Indicate that the parse of the current schema document is triggered
     * by xsi:schemaLocation/noNamespaceSchemaLocation attribute(s) in the
     * instance document. This value is only used if we don't defer the loading
     * of schema documents.
     * <p>
     *  指示当前模式文档的解析是由实例文档中的xsi：schemaLocation / noNamespaceSchemaLocation属性触发的。仅当我们不推迟加载模式文档时,才使用此值。
     * 
     */
    public final static short CONTEXT_INSTANCE  = 4;
    /**
     * Indicate that the parse of the current schema document is triggered by
     * the occurrence of an element whose namespace is the target namespace
     * of this schema document. This value is only used if we do defer the
     * loading of schema documents until a component from that namespace is
     * referenced from the instance.
     * <p>
     * 指示当前模式文档的解析是由其命名空间是此模式文档的目标命名空间的元素的发生触发的。仅当我们延迟加载模式文档,直到从该实例引用来自该命名空间的组件时,才使用此值。
     * 
     */
    public final static short CONTEXT_ELEMENT   = 5;
    /**
     * Indicate that the parse of the current schema document is triggered by
     * the occurrence of an attribute whose namespace is the target namespace
     * of this schema document. This value is only used if we do defer the
     * loading of schema documents until a component from that namespace is
     * referenced from the instance.
     * <p>
     *  指示当前模式文档的解析是由其命名空间是此模式文档的目标命名空间的属性的出现触发的。仅当我们延迟加载模式文档,直到从该实例引用来自该命名空间的组件时,才使用此值。
     * 
     */
    public final static short CONTEXT_ATTRIBUTE = 6;
    /**
     * Indicate that the parse of the current schema document is triggered by
     * the occurrence of an "xsi:type" attribute, whose value (a QName) has
     * the target namespace of this schema document as its namespace.
     * This value is only used if we do defer the loading of schema documents
     * until a component from that namespace is referenced from the instance.
     * <p>
     *  指示当前模式文档的解析是由"xsi：type"属性(其值(QName)具有此模式文档的目标命名空间作为其命名空间)的出现触发的。
     * 仅当我们延迟加载模式文档,直到从该实例引用来自该命名空间的组件时,才使用此值。
     * 
     */
    public final static short CONTEXT_XSITYPE   = 7;

    /**
     * Get the context. The returned value is one of the pre-defined
     * CONTEXT_xxx constants.
     *
     * <p>
     *  获取上下文。返回的值是预定义的CONTEXT_xxx常量之一。
     * 
     * 
     * @return  the value indicating the context
     */
    public short getContextType();

    /**
     * If the context is "include" or "redefine", then return the target
     * namespace of the enclosing schema document; otherwise, the expected
     * target namespace of this document.
     *
     * <p>
     *  如果上下文是"include"或"redefine",则返回包含模式文档的目标命名空间;否则,此文档的预期目标命名空间。
     * 
     * 
     * @return  the expected/enclosing target namespace
     */
    public String getTargetNamespace();

    /**
     * For import and references from the instance document, it's possible to
     * have multiple hints for one namespace. So this method returns an array,
     * which contains all location hints.
     *
     * <p>
     *  对于从实例文档的导入和引用,可能有一个命名空间的多个提示。因此,此方法返回一个数组,其中包含所有位置提示。
     * 
     * 
     * @return  an array of all location hints associated to the expected
     *          target namespace
     */
    public String[] getLocationHints();

    /**
     * If a call is triggered by an element/attribute/xsi:type in the instance,
     * this call returns the name of such triggering component: the name of
     * the element/attribute, or the value of the xsi:type.
     *
     * <p>
     * 如果调用由实例中的元素/属性/ xsi：type触发,则此调用将返回此触发组件的名称：元素/属性的名称或xsi：type的值。
     * 
     * 
     * @return  the name of the triggering component
     */
    public QName getTriggeringComponent();

    /**
     * If a call is triggered by an attribute or xsi:type, then this method
     * returns the enclosing element of such element.
     *
     * <p>
     *  如果调用由属性或xsi：type触发,则此方法返回此元素的包围元素。
     * 
     * 
     * @return  the name of the enclosing element
     */
    public QName getEnclosingElementName();

    /**
     * If a call is triggered by an element/attribute/xsi:type in the instance,
     * this call returns all attribute of such element (or enclosing element).
     *
     * <p>
     *  如果调用由实例中的元素/属性/ xsi：type触发,则此调用返回此元素(或包含元素)的所有属性。
     * 
     * @return  all attributes of the tiggering/enclosing element
     */
    public XMLAttributes getAttributes();

} // XSDDescription
