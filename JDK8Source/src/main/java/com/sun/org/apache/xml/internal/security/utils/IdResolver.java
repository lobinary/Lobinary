/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * <p>
 *  根据一个或多个贡献者许可协议授予Apache软件基金会(ASF)。有关版权所有权的其他信息,请参阅随此作品分发的NOTICE文件。
 *  ASF根据Apache许可证2.0版("许可证")向您授予此文件;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本。
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
package com.sun.org.apache.xml.internal.security.utils;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * Purpose of this class is to enable the XML Parser to keep track of ID
 * attributes. This is done by 'registering' attributes of type ID at the
 * IdResolver.
 * <p>
 *  此类的目的是使XML解析器能够跟踪ID属性。这是通过在IdResolver中注册类型ID的属性来完成的。
 * 
 * 
 * @deprecated
 */
@Deprecated
public class IdResolver {

    private IdResolver() {
        // we don't allow instantiation
    }

    /**
     * Method registerElementById
     *
     * <p>
     *  方法registerElementById
     * 
     * 
     * @param element the element to register
     * @param id the ID attribute
     */
    public static void registerElementById(Element element, Attr id) {
        element.setIdAttributeNode(id, true);
    }

    /**
     * Method getElementById
     *
     * <p>
     *  方法getElementById
     * 
     * @param doc the document
     * @param id the value of the ID
     * @return the element obtained by the id, or null if it is not found.
     */
    public static Element getElementById(Document doc, String id) {
        return doc.getElementById(id);
    }

}
