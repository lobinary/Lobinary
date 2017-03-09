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
 *  版权所有2000-2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.xni;

import java.util.Enumeration;

/**
 * The Augmentations interface defines a table of additional data that may
 * be passed along the document pipeline. The information can contain extra
 * arguments or infoset augmentations, for example PSVI. This additional
 * information is identified by a String key.
 * <p>
 * <strong>Note:</strong>
 * Methods that receive Augmentations are required to copy the information
 * if it is to be saved for use beyond the scope of the method.
 * The Augmentations content is volatile, and maybe modified by any method in
 * any component in the pipeline. Therefore, methods passed this structure
 * should not save any reference to the structure.
 *
 * <p>
 *  增强接口定义可以沿着文档管道传递的附加数据的表。该信息可以包含额外的参数或信息集扩展,例如PSVI。此附加信息由String键标识。
 * <p>
 *  <strong>注意</strong>：如果要在超出方法范围的情况下保存信息,则需要接收增强的方法来复制信息。增强内容是易失性的,并且可以通过管线中的任何组件中的任何方法修改。
 * 因此,传递此结构的方法不应保存对结构的任何引用。
 * 
 * 
 * @author Elena Litani, IBM
 */

public interface Augmentations {


    /**
     * Add additional information identified by a key to the Augmentations structure.
     *
     * <p>
     *  将通过键标识的其他信息添加到Augmentations结构。
     * 
     * 
     * @param key    Identifier, can't be <code>null</code>
     * @param item   Additional information
     *
     * @return the previous value of the specified key in the Augmentations structure,
     *         or <code>null</code> if it did not have one.
     */
    public Object putItem (String key, Object item);


    /**
     * Get information identified by a key from the Augmentations structure
     *
     * <p>
     *  从Augmentations结构获取由键标识的信息
     * 
     * 
     * @param key    Identifier, can't be <code>null</code>
     *
     * @return the value to which the key is mapped in the Augmentations structure;
     *         <code>null</code> if the key is not mapped to any value.
     */
    public Object getItem(String key);


    /**
     * Remove additional info from the Augmentations structure
     *
     * <p>
     * 从增强结构中删除其他信息
     * 
     * 
     * @param key    Identifier, can't be <code>null</code>
     * @return the previous value of the specified key in the Augmentations structure,
     *         or <code>null</code> if it did not have one.
     */
    public Object removeItem (String key);


    /**
     * Returns an enumeration of the keys in the Augmentations structure
     *
     * <p>
     *  返回Augmentations结构中键的枚举
     * 
     */
    public Enumeration keys ();


    /**
     * Remove all objects from the Augmentations structure.
     * <p>
     *  从Augmentations结构中删除所有对象。
     */
    public void removeAllItems ();

}
