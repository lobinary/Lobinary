/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2000-2002,2004 The Apache Software Foundation.
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
 *  版权所有2000-2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.xni.grammars;

import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;

/**
 * <p> This interface describes basic attributes of XML grammars--their
 * physical location and their type. </p>
 *
 * <p>
 *  <p>此接口描述XML语法的基本属性 - 它们的物理位置和类型。 </p>
 * 
 * 
 * @author Neil Graham, IBM
 */
public interface XMLGrammarDescription extends XMLResourceIdentifier {

    // initial set of grammar constants that some configurations will recognize;user
    // components which create and/or recognize other types of grammars may
    // certainly use their own constants in place of these (so long as
    // their Grammar objects implement this interface).

    /**
     * The grammar type constant for XML Schema grammars. When getGrammarType()
     * method returns this constant, the object should be an instance of
     * the XMLSchemaDescription interface.
     * <p>
     *  XML模式语法的语法类型常量。当getGrammarType()方法返回此常量时,对象应为XMLSchemaDescription接口的实例。
     * 
     */
    public static final String XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";

    /**
     * The grammar type constant for DTD grammars. When getGrammarType()
     * method returns this constant, the object should be an instance of
     * the XMLDTDDescription interface.
     * <p>
     *  DTD语法的语法类型常量。当getGrammarType()方法返回此常量时,对象应为XMLDTDDescription接口的实例。
     * 
     */
    public static final String XML_DTD = "http://www.w3.org/TR/REC-xml";

    /**
     * Return the type of this grammar.
     *
     * <p>
     *  返回此语法的类型。
     * 
     * @return  the type of this grammar
     */
    public String getGrammarType();

} // interface XMLGrammarDescription
