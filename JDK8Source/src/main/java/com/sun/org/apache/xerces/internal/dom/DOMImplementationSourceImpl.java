/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001, 2002,2004 The Apache Software Foundation.
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
 *  版权所有2001,2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.dom;

import java.util.StringTokenizer;
import java.util.Vector;
import org.w3c.dom.DOMImplementationList;
import org.w3c.dom.DOMImplementationSource;
import org.w3c.dom.DOMImplementation;
import com.sun.org.apache.xerces.internal.dom.DOMImplementationListImpl;

/**
 * Supply one the right implementation, based upon requested features. Each
 * implemented <code>DOMImplementationSource</code> object is listed in the
 * binding-specific list of available sources so that its
 * <code>DOMImplementation</code> objects are made available.
 *
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407/core.html#DOMImplementationSource'>Document Object Model (DOM) Level 3 Core Specification</a>.
 *
 * @xerces.internal
 *
 * <p>
 *  根据所需的功能提供一个正确的实现。
 * 每个实现的<code> DOMImplementationSource </code>对象都列在可用源的特定于绑定的列表中,以使其<code> DOMImplementation </code>对象可用
 * 。
 *  根据所需的功能提供一个正确的实现。
 * 
 *  <p>另请参阅<a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407/core.html#DOMImplementationSource'>
 * 文档对象模型(DOM) 3级核心规范</a>。
 * 
 */
public class DOMImplementationSourceImpl
    implements DOMImplementationSource {

    /**
     * A method to request a DOM implementation.
     * <p>
     *  @ xerces.internal
     * 
     * 
     * @param features A string that specifies which features are required.
     *   This is a space separated list in which each feature is specified
     *   by its name optionally followed by a space and a version number.
     *   This is something like: "XML 1.0 Traversal Events 2.0"
     * @return An implementation that has the desired features, or
     *   <code>null</code> if this source has none.
     */
    public DOMImplementation getDOMImplementation(String features) {
        // first check whether the CoreDOMImplementation would do
        DOMImplementation impl =
            CoreDOMImplementationImpl.getDOMImplementation();
        if (testImpl(impl, features)) {
            return impl;
        }
        // if not try the DOMImplementation
        impl = DOMImplementationImpl.getDOMImplementation();
        if (testImpl(impl, features)) {
            return impl;
        }

        return null;
    }

    /**
     * A method to request a list of DOM implementations that support the
     * specified features and versions, as specified in .
     * <p>
     *  请求DOM实现的方法。
     * 
     * 
     * @param features A string that specifies which features and versions
     *   are required. This is a space separated list in which each feature
     *   is specified by its name optionally followed by a space and a
     *   version number. This is something like: "XML 3.0 Traversal +Events
     *   2.0"
     * @return A list of DOM implementations that support the desired
     *   features.
     */
    public DOMImplementationList getDOMImplementationList(String features) {
        // first check whether the CoreDOMImplementation would do
        DOMImplementation impl = CoreDOMImplementationImpl.getDOMImplementation();
                final Vector implementations = new Vector();
        if (testImpl(impl, features)) {
                        implementations.addElement(impl);
        }
        impl = DOMImplementationImpl.getDOMImplementation();
        if (testImpl(impl, features)) {
                        implementations.addElement(impl);
        }

        return new DOMImplementationListImpl(implementations);
    }

    boolean testImpl(DOMImplementation impl, String features) {

        StringTokenizer st = new StringTokenizer(features);
        String feature = null;
        String version = null;

        if (st.hasMoreTokens()) {
           feature = st.nextToken();
        }
        while (feature != null) {
           boolean isVersion = false;
           if (st.hasMoreTokens()) {
               char c;
               version = st.nextToken();
               c = version.charAt(0);
               switch (c) {
               case '0': case '1': case '2': case '3': case '4':
               case '5': case '6': case '7': case '8': case '9':
                   isVersion = true;
               }
           } else {
               version = null;
           }
           if (isVersion) {
               if (!impl.hasFeature(feature, version)) {
                   return false;
               }
               if (st.hasMoreTokens()) {
                   feature = st.nextToken();
               } else {
                   feature = null;
               }
           } else {
               if (!impl.hasFeature(feature, null)) {
                   return false;
               }
               feature = version;
           }
        }
        return true;
    }
}
