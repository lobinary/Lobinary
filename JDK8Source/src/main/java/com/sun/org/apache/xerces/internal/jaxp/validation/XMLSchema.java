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

package com.sun.org.apache.xerces.internal.jaxp.validation;

import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarPool;

/**
 * <p>Implementation of Schema for W3C XML Schemas.</p>
 *
 * <p>
 *  <p> W3C XML模式的模式实现。</p>
 * 
 * 
 * @author Michael Glavassevich, IBM
 */
final class XMLSchema extends AbstractXMLSchema {

    /** The grammar pool is immutable */
    private final XMLGrammarPool fGrammarPool;

    /** Constructor */
    public XMLSchema(XMLGrammarPool grammarPool) {
        fGrammarPool = grammarPool;
    }

    /*
     * XSGrammarPoolContainer methods
     * <p>
     *  XSGrammarPoolContainer方法
     * 
     */

    /**
     * <p>Returns the grammar pool contained inside the container.</p>
     *
     * <p>
     *  <p>返回容器中包含的语法池。</p>
     * 
     * 
     * @return the grammar pool contained inside the container
     */
    public XMLGrammarPool getGrammarPool() {
        return fGrammarPool;
    }

    /**
     * <p>Returns whether the schema components contained in this object
     * can be considered to be a fully composed schema and should be
     * used to exclusion of other schema components which may be
     * present elsewhere.</p>
     *
     * <p>
     *  <p>返回此对象中包含的模式组件是否可以被视为完全组合的模式,并应用于排除其他可能存在的其他模式组件。</p>
     * 
     * @return whether the schema components contained in this object
     * can be considered to be a fully composed schema
     */
    public boolean isFullyComposed() {
        return true;
    }

} // XMLSchema
