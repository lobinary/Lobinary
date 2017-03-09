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
 */

package com.sun.org.apache.xerces.internal.jaxp.validation;

import java.lang.ref.WeakReference;

import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarPool;

/**
 * <p>An implementation of Schema for W3C XML Schemas
 * that keeps a weak reference to its grammar pool. If
 * no validators currently have a reference to the
 * grammar pool, the garbage collector is free to reclaim
 * its memory.</p>
 *
 * <p>
 * 
 * 
 * @author Michael Glavassevich, IBM
 */
final class WeakReferenceXMLSchema extends AbstractXMLSchema {

    /** Weak reference to grammar pool. */
    private WeakReference fGrammarPool = new WeakReference(null);

    public WeakReferenceXMLSchema() {}

    /*
     * XSGrammarPoolContainer methods
     * <p>
     *  <p> W3C XML模式的Schema实现,它保留对其语法池的弱引用。如果没有验证器当前有对语法池的引用,垃圾回收器可以自由回收其内存。</p>
     * 
     */

    public synchronized XMLGrammarPool getGrammarPool() {
        XMLGrammarPool grammarPool = (XMLGrammarPool) fGrammarPool.get();
        // If there's no grammar pool then either we haven't created one
        // yet or the garbage collector has already cleaned out the previous one.
        if (grammarPool == null) {
            grammarPool = new SoftReferenceGrammarPool();
            fGrammarPool = new WeakReference(grammarPool);
        }
        return grammarPool;
    }

    public boolean isFullyComposed() {
        return false;
    }

} // WeakReferenceXMLSchema
