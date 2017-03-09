/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2004 The Apache Software Foundation.
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
 *  版权所有2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.impl;

import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;

/**
 * <p>This interface describes the properties of entities--their
 * physical location and their name.</p>
 *
 * @xerces.internal
 *
 * <p>
 *  <p>此介面会说明实体的属性 - 实体位置和名称。</p>
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Michael Glavassevich, IBM
 *
 */
public interface XMLEntityDescription extends XMLResourceIdentifier {

    /**
     * Sets the name of the entity.
     *
     * <p>
     *  设置实体的名称。
     * 
     * 
     * @param name the name of the entity
     */
    public void setEntityName(String name);

    /**
     * Returns the name of the entity.
     *
     * <p>
     *  返回实体的名称。
     * 
     * @return the name of the entity
     */
    public String getEntityName();

} // XMLEntityDescription
