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

package com.sun.org.apache.xerces.internal.impl.validation;


/**
 * The entity state interface defines methods that must be implemented
 * by components that store information about entity declarations, as well as by
 * entity validator that will need to validate attributes of type entity.
 *
 * @xerces.internal
 *
 * <p>
 *  实体状态接口定义必须由存储关于实体声明的信息的组件以及将需要验证类型实体的属性的实体验证器实现的方法。
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Elena Litani, IBM
 */
public interface EntityState {
    /**
     * Query method to check if entity with this name was declared.
     *
     * <p>
     *  查询方法以检查是否已声明具有此名称的实体。
     * 
     * 
     * @param name
     * @return true if name is a declared entity
     */
    public boolean isEntityDeclared (String name);

    /**
     * Query method to check if entity is unparsed.
     *
     * <p>
     *  查询方法,用于检查实体是否未解析。
     * 
     * @param name
     * @return true if name is an unparsed entity
     */
    public boolean isEntityUnparsed (String name);
}
