/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2005 The Apache Software Foundation.
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
 *  版权所有2005 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.xpointer;

import com.sun.org.apache.xerces.internal.xni.Augmentations;
import com.sun.org.apache.xerces.internal.xni.QName;
import com.sun.org.apache.xerces.internal.xni.XMLAttributes;
import com.sun.org.apache.xerces.internal.xni.XNIException;

/**
 * <p>
 * The XPointerProcessor is responsible for parsing an XPointer
 * expression and and providing scheme specific resolution of
 * the document fragment pointed to be the pointer.
 * </p>
 *
 * @xerces.internal
 *
 *
 * <p>
 * <p>
 *  XPointerProcessor负责解析XPointer表达式并提供指定为指针的文档片段的方案特定解析。
 * </p>
 * 
 *  @ xerces.internal
 * 
 */
public interface XPointerProcessor {

    // The start element event
    public static final int EVENT_ELEMENT_START = 0;

    // The end element event
    public static final int EVENT_ELEMENT_END = 1;

    // The empty element event
    public static final int EVENT_ELEMENT_EMPTY = 2;

    /**
     * Parses an XPointer expression.  It performs scheme specific processing
     * depending on the pointer parts and sets up a Vector of XPointerParts
     * in the order (left-to-right) they appear in the XPointer expression.
     *
     * <p>
     *  解析XPointer表达式。它根据指针部分执行方案特定的处理,并按照XPointer表达式中出现的顺序(从左到右)设置XPointerParts的向量。
     * 
     * 
     * @param  xpointer A String representing the xpointer expression.
     * @throws XNIException Thrown if the xpointer string does not conform to
     *         the XPointer Framework syntax or the syntax of the pointer part does
     *         not conform to its definition for its scheme.
     *
     */
    public void parseXPointer(String xpointer) throws XNIException;

    /**
     * Evaluates an XML resource with respect to an XPointer expressions
     * by checking if it's element and attributes parameters match the
     * criteria specified in the xpointer expression.
     *
     * <p>
     *  通过检查XPointer表达式的元素和属性参数是否与xpointer表达式中指定的条件匹配,来评估XPointer表达式的XML资源。
     * 
     * 
     * @param element - The name of the element.
     * @param attributes - The element attributes.
     * @param augs - Additional information that may include infoset augmentations
     * @param event - An integer indicating
     *                0 - The start of an element
     *                1 - The end of an element
     *                2 - An empty element call
     * @return true if the element was resolved by the xpointer
     * @throws XNIException Thrown to signal an error
     *
     */
    public boolean resolveXPointer(QName element, XMLAttributes attributes,
            Augmentations augs, int event) throws XNIException;

    /**
     * Returns true if the XPointer expression resolves to the current resource fragment
     * or Node which is part of the input resource being streamed else returns false.
     *
     * <p>
     *  如果XPointer表达式解析为当前资源片段,则返回true,否则作为正在流式传输的输入资源的一部分的节点返回false。
     * 
     * 
     * @return True if the xpointer expression matches a node/fragment in the resource
     *         else returns false.
     * @throws XNIException Thrown to signal an error
     *
     */
    public boolean isFragmentResolved() throws XNIException;

    /**
     * Returns true if the XPointer expression resolves any subresource of the
     * input resource.
     *
     * <p>
     * 如果XPointer表达式解析输入资源的任何子资源,则返回true。
     * 
     * @return True if the xpointer expression matches a fragment in the resource
     *         else returns false.
     * @throws XNIException Thrown to signal an error
     *
     */
    public boolean isXPointerResolved() throws XNIException;

}
