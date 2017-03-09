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
 *  Represents a PSVI item for one element information item.
 * <p>
 *  表示一个元素信息项的PSVI项。
 * 
 */
public interface ElementPSVI extends ItemPSVI {
    /**
     * [element declaration]: an item isomorphic to the element declaration
     * used to validate this element.
     * <p>
     *  [元素声明]：与用于验证此元素的元素声明同构的项。
     * 
     */
    public XSElementDeclaration getElementDeclaration();

    /**
     *  [notation]: the notation declaration.
     * <p>
     *  [符号]：符号声明。
     * 
     */
    public XSNotationDeclaration getNotation();

    /**
     * [nil]: true if clause 3.2 of Element Locally Valid (Element) (3.3.4) is
     * satisfied, otherwise false.
     * <p>
     *  [nil]：true如果满足Element Locally Valid(Element)(3.3.4)的第3.2条,否则为false。
     * 
     */
    public boolean getNil();

    /**
     * schema information: the schema information property if it is the
     * validation root, <code>null</code> otherwise.
     * <p>
     *  模式信息：如果它是验证根,则为模式信息属性,否则为<code> null </code>。
     */
    public XSModel getSchemaInformation();

}
