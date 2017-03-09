/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2003-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有2003-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: Utils.java,v 1.2.4.1 2005/09/15 08:15:30 suresh_emailid Exp $
 * <p>
 *  $ Id：Utils.java,v 1.2.4.1 2005/09/15 08:15:30 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.serializer;

import java.util.Hashtable;

/**
 * This class contains utilities used by the serializer
 * <p>
 *  此类包含序列化程序使用的实用程序
 * 
 */
class Utils
{

    /**
     * This nested class acts as a way to lazy load the hashtable
     * in a thread safe way.
     * <p>
     *  这个嵌套类充当一种以线程安全的方式延迟加载散列表的方法。
     * 
     */
    static private class CacheHolder
    {
        static final Hashtable cache;
        static {
            cache = new Hashtable();
        }
    }
    /**
     * Load the class by name.
     *
     * This implementation, for performance reasons,
     * caches all classes loaded by name and
     * returns the cached Class object if it can previously
     * loaded classes that were load by name.  If not previously loaded
     * an attempt is made to load with Class.forName(classname)
     * <p>
     *  按名称加载类。
     * 
     * 
     * @param classname the name of the class to be loaded
     * @return the loaded class, never null. If the class could not be
     * loaded a ClassNotFound exception is thrown.
     * @throws ClassNotFoundException if the class was not loaded
     */
    static Class ClassForName(String classname) throws ClassNotFoundException
    {
        Class c;
        // the first time the next line runs will reference
        // CacheHolder, causing the class to load and create the
        // Hashtable.
        Object o = CacheHolder.cache.get(classname);
        if (o == null)
        {
            // class was not in the cache, so try to load it
            c = Class.forName(classname);
            // if the class is not found we will have thrown a
            // ClassNotFoundException on the statement above

            // if we get here c is not null
            CacheHolder.cache.put(classname, c);
        }
        else
        {
            c = (Class)o;
        }
        return c;
    }
}
