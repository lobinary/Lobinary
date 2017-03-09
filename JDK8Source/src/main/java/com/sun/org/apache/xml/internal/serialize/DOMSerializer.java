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


package com.sun.org.apache.xml.internal.serialize;


import java.io.IOException;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;



/**
 * Interface for a DOM serializer implementation.
 *
 *
 * <p>
 *  DOM序列化器实现的接口。
 * 
 * 
 * @author <a href="mailto:Scott_Boag/CAM/Lotus@lotus.com">Scott Boag</a>
 * @author <a href="mailto:arkin@intalio.com">Assaf Arkin</a>
 */
public interface DOMSerializer
{


    /**
     * Serialized the DOM element. Throws an exception only if
     * an I/O exception occured while serializing.
     *
     * <p>
     *  序列化DOM元素。仅当序列化时发生I / O异常时才抛出异常。
     * 
     * 
     * @param elem The element to serialize
     * @throws IOException An I/O exception occured while
     *   serializing
     */
    public void serialize( Element elem )
        throws IOException;


    /**
     * Serializes the DOM document. Throws an exception only if
     * an I/O exception occured while serializing.
     *
     * <p>
     *  序列化DOM文档。仅当序列化时发生I / O异常时才抛出异常。
     * 
     * 
     * @param doc The document to serialize
     * @throws IOException An I/O exception occured while
     *   serializing
     */
    public void serialize( Document doc )
        throws IOException;


    /**
     * Serializes the DOM document fragment. Throws an exception
     * only if an I/O exception occured while serializing.
     *
     * <p>
     *  序列化DOM文档片段。仅当序列化时发生I / O异常时才抛出异常。
     * 
     * @param frag The document fragment to serialize
     * @throws IOException An I/O exception occured while
     *   serializing
     */
    public void serialize( DocumentFragment frag )
        throws IOException;


}
