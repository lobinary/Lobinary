/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  根据一个或多个贡献者许可协议授予Apache软件基金会(ASF)。有关版权所有权的其他信息,请参阅随此作品分发的NOTICE文件。
 *  ASF根据Apache许可证2.0版("许可证")将此文件授予您;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本。
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.impl.dv.xs;

import com.sun.org.apache.xerces.internal.impl.dv.XSSimpleType;
import com.sun.org.apache.xerces.internal.util.SymbolHash;

/**
 * A special factory to create/return built-in schema DVs and create user-defined DVs
 * that includes anyAtomicType, yearMonthDuration and dayTimeDuration
 *
 * @xerces.internal
 *
 * <p>
 *  特殊工厂创建/返回内置模式DV并创建用户定义的DV,包括anyAtomicType,yearMonthDuration和dayTimeDuration
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Khaled Noaman, IBM
 *
 * @version $Id: ExtendedSchemaDVFactoryImpl.java,v 1.2 2010-10-26 23:01:03 joehw Exp $
 */
public class ExtendedSchemaDVFactoryImpl extends BaseSchemaDVFactory {

    static SymbolHash fBuiltInTypes = new SymbolHash();
    static {
        createBuiltInTypes();
    }

    // create all built-in types
    static void createBuiltInTypes() {
        final String ANYATOMICTYPE     = "anyAtomicType";
        final String DURATION          = "duration";
        final String YEARMONTHDURATION = "yearMonthDuration";
        final String DAYTIMEDURATION   = "dayTimeDuration";

        createBuiltInTypes(fBuiltInTypes, XSSimpleTypeDecl.fAnyAtomicType);

        // add anyAtomicType
        fBuiltInTypes.put(ANYATOMICTYPE, XSSimpleTypeDecl.fAnyAtomicType);

        // add 2 duration types
        XSSimpleTypeDecl durationDV = (XSSimpleTypeDecl)fBuiltInTypes.get(DURATION);
        fBuiltInTypes.put(YEARMONTHDURATION, new XSSimpleTypeDecl(durationDV, YEARMONTHDURATION, XSSimpleTypeDecl.DV_YEARMONTHDURATION, XSSimpleType.ORDERED_PARTIAL, false, false, false, true, XSSimpleTypeDecl.YEARMONTHDURATION_DT));
        fBuiltInTypes.put(DAYTIMEDURATION, new XSSimpleTypeDecl(durationDV, DAYTIMEDURATION, XSSimpleTypeDecl.DV_DAYTIMEDURATION, XSSimpleType.ORDERED_PARTIAL, false, false, false, true, XSSimpleTypeDecl.DAYTIMEDURATION_DT));
    } //createBuiltInTypes()

    /**
     * Get a built-in simple type of the given name
     * REVISIT: its still not decided within the Schema WG how to define the
     *          ur-types and if all simple types should be derived from a
     *          complex type, so as of now we ignore the fact that anySimpleType
     *          is derived from anyType, and pass 'null' as the base of
     *          anySimpleType. It needs to be changed as per the decision taken.
     *
     * <p>
     *  获取给定名称的内置简单类型REVISIT：它仍然没有在Schema WG中决定如何定义ur-types和如果所有简单类型都应该从复杂类型派生,所以现在我们忽略这样的事实： anySimpleType派
     * 生自anyType,并传递'null'作为anySimpleType的基础。
     * 它需要根据所做的决定进行更改。
     * 
     * 
     * @param name  the name of the datatype
     * @return      the datatype validator of the given name
     */
    public XSSimpleType getBuiltInType(String name) {
        return (XSSimpleType)fBuiltInTypes.get(name);
    }

    /**
     * get all built-in simple types, which are stored in a hashtable keyed by
     * the name
     *
     * <p>
     * 
     * @return      a hashtable which contains all built-in simple types
     */
    public SymbolHash getBuiltInTypes() {
        return (SymbolHash)fBuiltInTypes.makeClone();
    }
}
