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
 * This interface represents the Annotation schema component.
 * <p>
 *  此接口代表注释模式组件。
 * 
 */
public interface XSAnnotation extends XSObject {
    // TargetType
    /**
     * The object type is <code>org.w3c.dom.Element</code>.
     * <p>
     *  对象类型是<code> org.w3c.dom.Element </code>。
     * 
     */
    public static final short W3C_DOM_ELEMENT           = 1;
    /**
     * The object type is <code>org.xml.sax.ContentHandler</code>.
     * <p>
     *  对象类型是<code> org.xml.sax.ContentHandler </code>。
     * 
     */
    public static final short SAX_CONTENTHANDLER        = 2;
    /**
     * The object type is <code>org.w3c.dom.Document</code>.
     * <p>
     *  对象类型是<code> org.w3c.dom.Document </code>。
     * 
     */
    public static final short W3C_DOM_DOCUMENT          = 3;

    /**
     *  Write contents of the annotation to the specified object. If the
     * specified <code>target</code> is a DOM object, in-scope namespace
     * declarations for <code>annotation</code> element are added as
     * attribute nodes of the serialized <code>annotation</code>, otherwise
     * the corresponding events for all in-scope namespace declarations are
     * sent via the specified document handler.
     * <p>
     *  将注释的内容写入指定的对象。
     * 如果指定的<code> target </code>是DOM对象,则将<code>注释</code>元素的范围内命名空间声明添加为序列化<code>注释</code>的属性节点,否则所有范围内命名空间声
     * 明的相应事件通过指定的文档处理程序发送。
     *  将注释的内容写入指定的对象。
     * 
     * @param target  A target pointer to the annotation target object, i.e.
     *   <code>org.w3c.dom.Document</code>, <code>org.w3c.dom.Element</code>
     *   , <code>org.xml.sax.ContentHandler</code>.
     * @param targetType  A target type.
     * @return  True if the <code>target</code> is a recognized type and
     *   supported by this implementation, otherwise false.
     */
    public boolean writeAnnotation(Object target,
                                   short targetType);

    /**
     * A text representation of the annotation.
     * <p>
     * 
     */
    public String getAnnotationString();

}
