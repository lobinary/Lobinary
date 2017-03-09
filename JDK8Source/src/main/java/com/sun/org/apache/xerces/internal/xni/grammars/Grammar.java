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

/**
 * A generic grammar for use in validating XML documents. The Grammar
 * object stores the validation information in a compiled form. Specific
 * subclasses extend this class and "populate" the grammar by compiling
 * the specific syntax (DTD, Schema, etc) into the data structures used
 * by this object.
 * <p>
 * <strong>Note:</strong> The Grammar object is not useful as a generic
 * grammar access or query object. In other words, you cannot round-trip
 * specific grammar syntaxes with the compiled grammar information in
 * the Grammar object. You <em>can</em> create equivalent validation
 * rules in your choice of grammar syntax but there is no guarantee that
 * the input and output will be the same.
 *
 * <p> Right now, this class is largely a shell; eventually,
 * it will be enriched by having more expressive methods added. </p>
 * will be moved from dtd.Grammar here.
 *
 * <p>
 *  用于验证XML文档的通用语法。语法对象以编译的形式存储验证信息。特定的子类扩展这个类,并通过将特定语法(DTD,Schema等)编译到此对象使用的数据结构中来"填充"语法。
 * <p>
 *  <strong>注意</strong>：语法对象作为通用语法访问或查询对象不是有用的。换句话说,你不能使用语法对象中编译的语法信息来循环特定的语法语法。
 * 您可以</em>在您选择的语法语法中创建等效的验证规则,但不能保证输入和输出将是相同的。
 * 
 * 
 * @author Jeffrey Rodriguez, IBM
 * @author Eric Ye, IBM
 * @author Andy Clark, IBM
 * @author Neil Graham, IBM
 *
 */

public interface Grammar {

    /**
     * get the <code>XMLGrammarDescription</code> associated with this
     * object
     * <p>
     * <p>现在,这个类主要是一个shell;最终,它将通过添加更多的表达方法来丰富。 </p>将从dtd.Grammar这里移动。
     * 
     */
    public XMLGrammarDescription getGrammarDescription ();
} // interface Grammar
