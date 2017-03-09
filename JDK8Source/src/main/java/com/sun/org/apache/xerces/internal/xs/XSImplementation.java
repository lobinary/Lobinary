/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2003,2004 The Apache Software Foundation.
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
 *  版权所有2003,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.xs;

/**
 * This interface allows one to retrieve an instance of <code>XSLoader</code>.
 * This interface should be implemented on the same object that implements
 * DOMImplementation.
 * <p>
 *  此接口允许检索<code> XSLoader </code>的实例。这个接口应该实现在实现DOMImplementation的同一个对象上。
 * 
 */
public interface XSImplementation {
    /**
     * A list containing the versions of XML Schema documents recognized by
     * this <code>XSImplemenation</code>.
     * <p>
     *  包含由此<code> XSImplemenation </code>识别的XML模式文档的版本的列表。
     * 
     */
    public StringList getRecognizedVersions();


    /**
     * Creates a new XSLoader. The newly constructed loader may then be
     * configured and used to load XML Schemas.
     * <p>
     *  创建新的XSLoader。然后可以配置新构建的加载器并用于加载XML模式。
     * 
     * @param versions  A list containing the versions of XML Schema
     *   documents which can be loaded by the <code>XSLoader</code> or
     *   <code>null</code> to permit XML Schema documents of any recognized
     *   version to be loaded by the XSLoader.
     * @return  An XML Schema loader.
     * @exception XSException
     *   NOT_SUPPORTED_ERR: Raised if the implementation does not support one
     *   of the specified versions.
     */
    public XSLoader createXSLoader(StringList versions)
                                   throws XSException;

}
