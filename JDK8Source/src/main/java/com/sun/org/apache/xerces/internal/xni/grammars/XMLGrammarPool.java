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
 *  版权所有2000-2002,2004 Apache软件基金会
 * 
 *  根据Apache许可证第20版("许可证")授权;您不得使用此文件,除非符合许可证您可以在获取许可证的副本
 * 
 *  http：// wwwapacheorg / licenses / LICENSE-20
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件将按"原样"基础分发,无任何明示或暗示的保证或条件。请参阅许可证管理权限和限制许可证
 * 
 */
package com.sun.org.apache.xerces.internal.xni.grammars;

/**
 * <p> This interface specifies how the parser and the application
 * interact with respect to Grammar objects that the application
 * possesses--either by having precompiled them or by having stored them
 * from a previous validation of an instance document.  It makes no
 * assumptions about the kind of Grammar involved, or about how the
 * application's storage mechanism works.</p>
 *
 * <p>The interaction works as follows:
 * <ul>
 * <li>When a validator considers a document, it is expected to request
 * grammars of the type it can handle from this object using the
 * <code>retrieveInitialGrammarSet</code> method. </li>
 * <li>If it requires a grammar
 * not in this set, it will request it from this Object using the
 * <code>retrieveGrammar</code> method.  </li>
 * <li> After successfully validating an
 * instance, the validator should make any new grammars it has compiled
 * available to this object using the <code>cacheGrammars</code>
 * method; for ease of implementation it may make other Grammars it holds references to as well (i.e.,
 * it may return some grammars that were retrieved from the GrammarPool in earlier operations). </li> </ul> </p>
 *
 * <p>
 * <p>此接口指定解析器和应用程序如何与应用程序拥有的语法对象进行交互 - 无论是通过预编译它们还是通过从实例文档的先前验证中存储它们。它不会对类型语法涉及,或关于应用程序的存储机制如何工作</p>
 * 
 *  <p>互动的工作原理如下：
 * <ul>
 * <li>验证器考虑文档时,应使用<code> retrieveInitialGrammarSet </code>方法请求该文档可以处理的类型的语法</li> <li>如果需要语法不在这个集合将使用<code>
 *  retrieveGrammar </code>方法从此对象请求它</li> <li>成功验证实例后,验证器应该使用任何新的语法, <code> cacheGrammars </code>方法;为了易于
 * 实现,它可以使其他语法也保持引用(即,它可以返回在早期操作中从GrammarPool检索的一些语法)。
 * </li> </ul> </p>。
 * 
 * 
 * @author Neil Graham, IBM
 */

public interface XMLGrammarPool {

    // <p>we are trying to make this XMLGrammarPool work for all kinds of
    // grammars, so we have a parameter "grammarType" for each of the
    // methods. </p>

    /**
     * <p> retrieve the initial known set of grammars. this method is
     * called by a validator before the validation starts. the application
     * can provide an initial set of grammars available to the current
     * validation attempt. </p>
     * <p>
     * <p>检索语法的初始已知集合在验证开始之前验证器调用此方法应用程序可以提供当前验证尝试可用的一组初始语法</p>
     * 
     * 
     * @param grammarType the type of the grammar, from the
     *  <code>com.sun.org.apache.xerces.internal.xni.grammars.Grammar</code> interface.
     * @return the set of grammars the validator may put in its "bucket"
     */
    public Grammar[] retrieveInitialGrammarSet(String grammarType);

    /**
     * <p>return the final set of grammars that the validator ended up
     * with.
     * This method is called after the
     * validation finishes. The application may then choose to cache some
     * of the returned grammars. </p>
     * <p>
     *  <p>返回验证器结束的最后一组语法此方法在验证完成后调用应用程序可能会选择缓存一些返回的语法</p>
     * 
     * 
     * @param grammarType the type of the grammars being returned;
     * @param grammars an array containing the set of grammars being
     *  returned; order is not significant.
     */
    public void cacheGrammars(String grammarType, Grammar[] grammars);

    /**
     * <p> This method requests that the application retrieve a grammar
     * corresponding to the given GrammarIdentifier from its cache.
     * If it cannot do so it must return null; the parser will then
     * call the EntityResolver.  <strong>An application must not call its
     * EntityResolver itself from this method; this may result in infinite
     * recursions.</strong>
     * <p>
     *  <p>此方法请求应用程序从其高速缓存检索与给定GrammarIdentifier对应的语法如果它不能这样做,它必须返回null;解析器将调用EntityResolver <strong>应用程序不能从
     * 此方法调用其EntityResolver本身;这可能会导致无限递归</strong>。
     * 
     * 
     * @param desc The description of the Grammar being requested.
     * @return the Grammar corresponding to this description or null if
     *  no such Grammar is known.
     */
    public Grammar retrieveGrammar(XMLGrammarDescription desc);

    /**
     * Causes the XMLGrammarPool not to store any grammars when
     * the cacheGrammars(String, Grammar[[]) method is called.
     * <p>
     * 当调用cacheGrammars(String,Grammar [[])方法时,导致XMLGrammarPool不存储任何语法
     * 
     */
    public void lockPool();

    /**
     * Allows the XMLGrammarPool to store grammars when its cacheGrammars(String, Grammar[])
     * method is called.  This is the default state of the object.
     * <p>
     *  允许XMLGrammarPool在调用cacheGrammars(String,Grammar [])方法时存储语法这是对象的默认状态
     * 
     */
    public void unlockPool();

    /**
     * Removes all grammars from the pool.
     * <p>
     *  从池中删除所有语法
     */
    public void clear();
} // XMLGrammarPool
