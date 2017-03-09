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
 * Used for scheme specific parsing and evaluation of an XPointer expression.
 * This interface applies to both ShortHand and SchemeBased XPointer
 * expressions.
 * </p>
 *
 * @xerces.internal
 *
 * <p>
 * <p>
 *  用于方案特定的XPointer表达式解析和评估。此接口适用于ShortHand和SchemeBased XPointer表达式。
 * </p>
 * 
 *  @ xerces.internal
 * 
 */
public interface XPointerPart {

    // The start element event
    public static final int EVENT_ELEMENT_START = 0;

    // The end element event
    public static final int EVENT_ELEMENT_END = 1;

    // The empty element event
    public static final int EVENT_ELEMENT_EMPTY = 2;

    /**
     * Provides scheme specific parsing of a XPointer expression i.e.
     * the PointerPart or ShortHandPointer.
     *
     * <p>
     *  提供XPointer表达式(即PointerPart或ShortHandPointer)的方案特定解析。
     * 
     * 
     * @param  xpointer A String representing the PointerPart or ShortHandPointer.
     * @throws XNIException Thrown if the PointerPart string does not conform to
     *         the syntax defined by its scheme.
     *
     */
    public void parseXPointer(String part) throws XNIException;

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
     * @throws XNIException Thrown to signal an error
     *
     */
    public boolean resolveXPointer(QName element, XMLAttributes attributes,
            Augmentations augs, int event) throws XNIException;

    /**
     * Returns true if the XPointer expression resolves to a resource fragment
     * specified as input else returns false.
     *
     * <p>
     *  如果XPointer表达式解析为指定为input else的返回false的资源片段,则返回true。
     * 
     * 
     * @return True if the xpointer expression matches a fragment in the resource
     *         else returns false.
     * @throws XNIException Thrown to signal an error
     *
     */
    public boolean isFragmentResolved() throws XNIException;

    /**
     * Returns true if the XPointer expression resolves to a non-element child
     * of the current resource fragment.
     *
     * <p>
     *  如果XPointer表达式解析为当前资源片段的非元素子元素,则返回true。
     * 
     * 
     * @return True if the XPointer expression resolves to a non-element child
     *         of the current resource fragment.
     * @throws XNIException Thrown to signal an error
     *
     */
    public boolean isChildFragmentResolved() throws XNIException;

    /**
     * Returns a String containing the scheme name of the PointerPart
     * or the name of the ShortHand Pointer.
     *
     * <p>
     *  返回一个字符串,其中包含PointerPart的方案名称或ShortHand指针的名称。
     * 
     * 
     * @return A String containing the scheme name of the PointerPart.
     *
     */
    public String getSchemeName();

    /**
     * Returns a String containing the scheme data of the PointerPart.
     *
     * <p>
     * 返回一个包含PointerPart的方案数据的字符串。
     * 
     * 
     * @return A String containing the scheme data of the PointerPart.
     *
     */
    public String getSchemeData();

    /**
     * Sets the scheme name of the PointerPart or the ShortHand Pointer name.
     *
     * <p>
     *  设置PointerPart的方案名称或ShortHand指针名称。
     * 
     * 
     * @param schemeName A String containing the scheme name of the PointerPart.
     *
     */
    public void setSchemeName(String schemeName);

    /**
     * Sets the scheme data of the PointerPart.
     *
     * <p>
     *  设置PointerPart的方案数据。
     * 
     * @param schemeData A String containing the scheme data of the PointerPart.
     *
     */
    public void setSchemeData(String schemeData);

}
