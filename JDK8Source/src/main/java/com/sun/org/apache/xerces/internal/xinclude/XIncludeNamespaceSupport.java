/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2003-2005 The Apache Software Foundation.
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
 *  版权所有2003-2005 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
package com.sun.org.apache.xerces.internal.xinclude;

import com.sun.org.apache.xerces.internal.xni.NamespaceContext;

/**
 * This is an implementation of NamespaceContext which is intended to be used for
 * XInclude processing.  It enables each context to be marked as invalid, if necessary,
 * to indicate that the namespaces recorded on those contexts won't be apparent in the
 * resulting infoset.
 *
 * <p>
 *  这是一个用于XInclude处理的NamespaceContext的实现。它使每个上下文被标记为无效,如果需要,表明记录在这些上下文中的命名空间在生成的信息集中不会显现。
 * 
 * 
 * @author Peter McCracken, IBM
 *
 */
public class XIncludeNamespaceSupport extends MultipleScopeNamespaceSupport {

    /**
     * This stores whether or not the context at the matching depth was valid.
     * <p>
     *  这存储匹配深度处的上下文是否有效。
     * 
     */
    private boolean[] fValidContext = new boolean[8];

    /**
     *
     * <p>
     */
    public XIncludeNamespaceSupport() {
        super();
    }

    /**
    /* <p>
    /* 
     * @param context
     */
    public XIncludeNamespaceSupport(NamespaceContext context) {
        super(context);
    }

    /**
     * Pushes a new context onto the stack.
     * <p>
     *  将新的上下文推送到堆栈。
     * 
     */
    public void pushContext() {
        super.pushContext();
        if (fCurrentContext + 1 == fValidContext.length) {
            boolean[] contextarray = new boolean[fValidContext.length * 2];
            System.arraycopy(fValidContext, 0, contextarray, 0, fValidContext.length);
            fValidContext = contextarray;
        }

        fValidContext[fCurrentContext] = true;
    }

    /**
     * This method is used to set a context invalid for XInclude namespace processing.
     * Any context defined by an &lt;include&gt; or &lt;fallback&gt; element is not
     * valid for processing the include parent's [in-scope namespaces]. Thus, contexts
     * defined by these elements are set to invalid by the XInclude processor using
     * this method.
     * <p>
     *  此方法用于设置上下文无效的XInclude命名空间处理。由&lt; include&gt;定义的任何上下文或&lt; fallback&gt;元素对于处理包含父级的[范围内命名空间]无效。
     * 因此,由这些元素定义的上下文被XInclude处理器使用该方法设置为无效。
     * 
     */
    public void setContextInvalid() {
        fValidContext[fCurrentContext] = false;
    }

    /**
     * This returns the namespace URI which was associated with the given pretext, in
     * the context that existed at the include parent of the current element.  The
     * include parent is the last element, before the current one, which was not set
     * to an invalid context using setContextInvalid()
     *
     * <p>
     * 这将返回与存在于当前元素的include父上下文中的给定前缀相关联的命名空间URI。
     *  include parent是最后一个元素,在当前的元素之前,它没有使用setContextInvalid()设置为无效的上下文,。
     * 
     * @param prefix the prefix of the desired URI
     * @return the URI corresponding to the prefix in the context of the include parent
     */
    public String getURIFromIncludeParent(String prefix) {
        int lastValidContext = fCurrentContext - 1;
        while (lastValidContext > 0 && !fValidContext[lastValidContext]) {
            lastValidContext--;
        }
        return getURI(prefix, lastValidContext);
    }
}
