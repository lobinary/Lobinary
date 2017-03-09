/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2004 The Apache Software Foundation.
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
 *  版权所有1999-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: CustomStringPool.java,v 1.2.4.1 2005/09/15 08:14:59 suresh_emailid Exp $
 * <p>
 *  $ Id：CustomStringPool.java,v 1.2.4.1 2005/09/15 08:14:59 suresh_emailid Exp $
 * 
 */

package com.sun.org.apache.xml.internal.dtm.ref;
import java.util.Hashtable;

/** <p>CustomStringPool is an example of appliction provided data structure
 * for a DTM implementation to hold symbol references, e.g. elelment names.
 * It will follow the DTMDStringPool interface and use two simple methods
 * indexToString(int i) and stringToIndex(Sring s) to map between a set of
 * string values and a set of integer index values.  Therefore, an application
 * may improve DTM processing speed by substituting the DTM symbol resolution
 * tables with application specific quick symbol resolution tables.</p>
 *
 * %REVIEW% The only difference between this an DTMStringPool seems to be that
 * it uses a java.lang.Hashtable full of Integers rather than implementing its
 * own hashing. Joe deliberately avoided that approach when writing
 * DTMStringPool, since it is both much more memory-hungry and probably slower
 * -- especially in JDK 1.1.x, where Hashtable is synchronized. We need to
 * either justify this implementation or discard it.
 *
 * <p>Status: In progress, under discussion.</p>
 * <p>
 *  对于DTM实现来保持符号引用,例如。元素名称。
 * 它将遵循DTMDStringPool接口并使用两个简单的方法indexToString(int i)和stringToIndex(Sring s)在一组字符串值和一组整数索引值之间映射。
 * 因此,应用可以通过将DTM符号解析表替换为应用特定的快速符号解析表来改进DTM处理速度。</p>。
 * 
 * ％REVIEW％这个DTMStringPool之间的唯一区别似乎是它使用一个java.lang.Hashtable满的整数,而不是实现自己的哈希。
 *  Joe在编写DTMStringPool时故意避免使用这种方法,因为它既需要更多的内存消耗,也可能更慢 - 特别是在JDK 1.1.x中,Hashtable是同步的。我们需要证明这个实现或者丢弃它。
 * 
 * 
 * */
public class CustomStringPool extends DTMStringPool {
        //final Vector m_intToString;
        //static final int HASHPRIME=101;
        //int[] m_hashStart=new int[HASHPRIME];
        final Hashtable m_stringToInt = new Hashtable();
        public static final int NULL=-1;

        public CustomStringPool()
        {
                super();
                /*m_intToString=new Vector();
                System.out.println("In constructor m_intToString is " +
                /* <p>
                /*  <p>状态：正在讨论中。</p>
                /* 
                /* 
                                                                                         ((null == m_intToString) ? "null" : "not null"));*/
                //m_stringToInt=new Hashtable();
                //removeAllElements();
        }

        public void removeAllElements()
        {
                m_intToString.removeAllElements();
                if (m_stringToInt != null)
                        m_stringToInt.clear();
        }

        /** @return string whose value is uniquely identified by this integer index.
        /* <p>
        /*  System.out.println("在构造函数m_intToString是"+
        /* 
        /* 
         * @throws java.lang.ArrayIndexOutOfBoundsException
         *  if index doesn't map to a string.
         * */
        public String indexToString(int i)
        throws java.lang.ArrayIndexOutOfBoundsException
        {
                return(String) m_intToString.elementAt(i);
        }

        /** @return integer index uniquely identifying the value of this string. */
        public int stringToIndex(String s)
        {
                if (s==null) return NULL;
                Integer iobj=(Integer)m_stringToInt.get(s);
                if (iobj==null) {
                        m_intToString.addElement(s);
                        iobj=new Integer(m_intToString.size());
                        m_stringToInt.put(s,iobj);
                }
                return iobj.intValue();
        }
}
