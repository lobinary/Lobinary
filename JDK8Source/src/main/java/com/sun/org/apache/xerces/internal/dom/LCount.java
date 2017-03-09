/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2002,2004 The Apache Software Foundation.
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
 *  版权所有1999-2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
package com.sun.org.apache.xerces.internal.dom;


/** Internal class LCount is used to track the number of
    listeners registered for a given event name, as an entry
    in a global hashtable. This should allow us to avoid generating,
    or discard, events for which no listeners are registered.

    ***** There should undoubtedly be methods here to manipulate
    this table. At the moment that code's residing in NodeImpl.
    Move it when we have a chance to do so. Sorry; we were
    rushed.

    ???? CONCERN: Hashtables are known to be "overserialized" in
    current versions of Java. That may impact performance.

    ???? CONCERN: The hashtable should probably be a per-document object.
    Finer granularity would be even better, but would cost more cycles to
    resolve and might not save enough event traffic to be worth the investment.
    * <p>
    *  为给定事件名称注册的侦听器,作为全局散列表中的条目。这应该允许我们避免生成或丢弃没有注册监听器的事件。
    * 
    *  ***应该无疑是在这里操纵这个表的方法。此时代码驻留在NodeImpl中。移动它,当我们有机会这样做。抱歉;我们冲了。
    * 
    *  ?注意：Hashtables在当前版本的Java中被称为"超级化"。这可能会影响性能。
    * 
*/
/**
 * @xerces.internal
 *
 * <p>
 *  ? CONCERN：散列表应该是一个每个文档的对象。更精细的粒度会更好,但会花费更多的周期来解决,并可能无法保存足够的事件流量值得投资。
 * 
 */

class LCount
{
    static java.util.Hashtable lCounts=new java.util.Hashtable();
    public int captures=0,bubbles=0,defaults, total=0;

    static LCount lookup(String evtName)
    {
        LCount lc=(LCount)lCounts.get(evtName);
        if(lc==null)
            lCounts.put(evtName,(lc=new LCount()));
        return lc;
    }
} // class LCount
