/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2002,2004 The Apache Software Foundation.
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
 *  版权所有2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.xni.grammars;

import com.sun.org.apache.xerces.internal.xs.XSModel;

/**
 * Representing a schema grammar. It contains declaratoin/definitions from
 * a certain namespace. When a grammar is preparsed, and its grammar type is
 * XML Schema, it can be casted to this interface. Objects of this interface
 * can be converted to XSModel, from which further information about components
 * in this grammar can be obtained.
 *
 * <p>
 *  表示模式语法。它包含来自某个命名空间的声明/定义。当语法被预编译,并且它的语法类型是XML Schema时,它可以被转换到这个接口。
 * 此接口的对象可以转换为XSModel,从中可以获取有关该语法中的组件的进一步信息。
 * 
 * 
 * @author Sandy Gao, IBM
 *
 */
public interface XSGrammar extends Grammar {

    /**
     * Return an <code>XSModel</code> that represents components in this schema
     * grammar and any schema grammars that are imported by this grammar
     * directly or indirectly.
     *
     * <p>
     *  返回表示此模式语法中的组件以及由此语法直接或间接导入的任何模式语法的<code> XSModel </code>。
     * 
     * 
     * @return  an <code>XSModel</code> representing this schema grammar
     */
    public XSModel toXSModel();

    /**
     * Return an <code>XSModel</code> that represents components in this schema
     * grammar and the grammars in the <code>grammars</code>parameter,
     * any schema grammars that are imported by them directly or indirectly.
     *
     * <p>
     *  返回表示此模式语法中的组件的<code> XSModel </code>和<code>语法</code>参数中的语法,直接或间接导入的任何模式语法。
     * 
     * @return  an <code>XSModel</code> representing these schema grammars
     */
    public XSModel toXSModel(XSGrammar[] grammars);

} // interface XSGrammar
